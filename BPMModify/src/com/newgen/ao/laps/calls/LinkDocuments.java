package com.newgen.ao.laps.calls;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import ISPack.CPISDocumentTxn;
import ISPack.ISUtil.JPDBRecoverDocData;
import ISPack.ISUtil.JPISException;
import ISPack.ISUtil.JPISIsIndex;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.callhandler.CallHandler;
import com.newgen.ao.laps.core.ICallExecutor;
import com.newgen.ao.laps.logger.LapsModifyDBLogger;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;

public class LinkDocuments implements ICallExecutor, Callable<String> {
	private String sessionId;
	private String WI_NAME;
	private String callStatus = "Y";
	private int stageID;
	private int returnCode = 0;
	private String errorDetail = "Success";
	private String errorDescription = "Documents linked successfully";	
	private String customerId;
	private String status;
	private String reason;
	private String channelRefNo;
	private String itemIndex;
	private boolean prevStageNoGo;
	private Map<String, String> defaultMap;
	private CallEntity callEntity;
	private String auditCallName = "LD";
	private String mode = "";
	private String currWS = "Initiator";
	private String finaloutputXml =""; //ATP-496 29-07-2024 REYAZ

	public LinkDocuments(Map<String, String> defaultMap, String sessionId, String stageId, String WI_NAME, Boolean prevStageNoGo, CallEntity callEntity){
		this.WI_NAME = WI_NAME;
		stageID = Integer.parseInt(stageId);
		this.sessionId = sessionId;
		this.prevStageNoGo = prevStageNoGo;
		this.defaultMap = defaultMap;
		this.callEntity = callEntity;
		if(defaultMap.containsKey("mode")){
			this.mode = defaultMap.get("mode");
		}
		String outputXML;		
		try {
			outputXML = APCallCreateXML.APSelect("SELECT ITEMINDEX,PRO_TRADE_REF_NO,CURR_WS FROM EXT_TFO WHERE WI_NAME = N'" + WI_NAME + "'");
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "LD001", "Started", sessionId);
			if(mainCode == 0){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"FetchDetails TotalRetrieved: "+Integer.parseInt(xp.getValueOf("TotalRetrieved")));
				if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
					itemIndex = xp.getValueOf("ITEMINDEX");		
					channelRefNo = xp.getValueOf("PRO_TRADE_REF_NO");
					if("R".equalsIgnoreCase(mode) || "DCR".equalsIgnoreCase(mode) || "DMR".equalsIgnoreCase(mode)){
						currWS = xp.getValueOf("CURR_WS");
					} else if("DM".equalsIgnoreCase(mode)){
						currWS = "CUSTOMER_REVIEW";
					} 
					LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "LD002", "Customer Id: "+customerId, sessionId);
				}
			}		
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "LD100", e.getMessage(), sessionId);
		}
	}

	@Override
	public String call() throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"FetchDetails call: inside" +prevStageNoGo);
		String inputXml = "" ;
		if(!prevStageNoGo){
			try {
				getDocPDFDtls(sessionId);
			} catch (JPISException e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
				LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "LD100", e.getMessage(), sessionId);
			}
			updateCallOutput(inputXml);
		} else {
			callStatus="N";
			updateCallOutput(inputXml);
		}
		finaloutputXml = "<returnCode>"+returnCode+"</returnCode><errorDescription>"+errorDescription+"</errorDescription>";
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXml: " +finaloutputXml);
		return finaloutputXml;
	}

	@Override
	public void executeDependencyCall() throws Exception {
		try {
			String outputXMLTemp = "";
			outputXMLTemp =CallHandler.getInstance().executeDependencyCall(callEntity, defaultMap, sessionId, WI_NAME, prevStageNoGo);
			if(!outputXMLTemp.isEmpty()){
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "LD004", "DependencyCall"+ callEntity.getDependencyCallID(), sessionId);
				finaloutputXml = outputXMLTemp;
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "LD100", e.getMessage(), sessionId);
		}
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		try {
			if(callStatus == "N"){
				returnCode = -1 ;
				errorDetail = "Failed";
				errorDescription = "Document Linking failed";
			}
			if("R".equalsIgnoreCase(mode) || "DCR".equalsIgnoreCase(mode) || "DMR".equalsIgnoreCase(mode)){
				stageID = -1 * stageID;
			}
			String valList = "'"+ WI_NAME +"',"+ stageID +", 'LinkDocuments', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"'";
			APCallCreateXML.APInsert("BPM_ORCHESTRATION_STATUS", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
					+ "ERROR_DETAIL, STATUS, REASON", valList, sessionId);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "LD100", e.getMessage(), sessionId);
		}	
	}

	@Override
	public String executeCall(HashMap<String, String> map) throws Exception {
		return call();
	}
	private void getDocPDFDtls(String sessionId) throws JPISException {
		XMLParser xp;
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"PDFdetails execution starts");
		String documentChannelRefPath = File.separatorChar+LapsConfigurations.getInstance().docFolderName
				+File.separatorChar+channelRefNo;
		File docPath = new File(documentChannelRefPath);
//		String documentPathEReceipt = System.getProperty("user.dir")+File.separatorChar
		String documentPathEReceipt = File.separatorChar+LapsConfigurations.getInstance().docFolderName
				+File.separatorChar+channelRefNo+File.separatorChar+LapsConfigurations.getInstance().ProTradeReceiptDoc
				+File.separatorChar;
//		String documentPathCustDocs = System.getProperty("user.dir")+File.separatorChar
		String documentPathCustDocs = File.separatorChar+LapsConfigurations.getInstance().docFolderName+File.separatorChar+channelRefNo
				+File.separatorChar+LapsConfigurations.getInstance().ProTradeCustomerDoc
				+File.separatorChar;
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"documentPathEReceipt: "+documentPathEReceipt);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"documentPathCustDocs: "+documentPathCustDocs);
		if(docPath.exists()){
			String docIndex = "";
			JPISIsIndex ISINDEX = new JPISIsIndex();
			JPDBRecoverDocData JPISDEC = new JPDBRecoverDocData();
			try {
				List<String> fileNamesERcpt = new ArrayList<String>();
				File fileObjERcpt = new File(documentPathEReceipt);
				if(fileObjERcpt.exists()){
					File[] filesERcpt = fileObjERcpt.listFiles(new FilenameFilter() {
						@Override
						public boolean accept(File dir, String name) {
//							return name.endsWith(".pdf");
							return true;
						}
					});
					for(File file : filesERcpt){
						if(file.isFile()){
							fileNamesERcpt.add(file.getName());
						}
					}
					int fileCountERcpt = fileNamesERcpt.size();
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "fileCount : "+ fileCountERcpt);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "fileNames : "+ fileNamesERcpt.toString());
					long lFileLength = filesERcpt[0].length();
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "lFileLength : "+ lFileLength);
					JPISDEC.m_cDocumentType = 'N';
					JPISDEC.m_nDocumentSize = (int) lFileLength;
					JPISDEC.m_sVolumeId = (short) (LapsConfigurations.getInstance().volumeID);
					String filePathEReceipt = documentPathEReceipt+fileNamesERcpt.get(0);
					try {
						if (JPISDEC.m_nDocumentSize != 0) {
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "JTSIP : "
									+ LapsConfigurations.getInstance().JTSIP);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "JTSPort : "
									+ LapsConfigurations.getInstance().JTSPort);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "documentPath : "
									+ filePathEReceipt);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "volumeID : "
									+ LapsConfigurations.getInstance().volumeID);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"JPISDEC.m_nDocumentSize : "
									+ JPISDEC.m_nDocumentSize);
							CPISDocumentTxn.AddDocument_MT(null, LapsConfigurations.getInstance().JTSIP,
									(short) LapsConfigurations.getInstance().JTSPort, 
									LapsConfigurations.getInstance().CabinetName, (short) (LapsConfigurations.getInstance()
											.volumeID),filePathEReceipt, JPISDEC, "", ISINDEX);
						}
					} catch (Exception e) {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in e receipt: ");
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
					}
					docIndex = JPISDEC.m_nDocIndex + "#" + LapsConfigurations.getInstance().volumeID;
//					String outputXml = ProdCreateXML.AddDocument(sessionId, itemIndex, LapsConfigurations.getInstance().ProTradeReceiptDoc,
//							"pdf", fileNamesERcpt.get(0).substring(0, fileNamesERcpt.get(0).indexOf(".")),
//							LapsConfigurations.getInstance().volumeID,
//							docIndex, 1, "N", String.valueOf(lFileLength));
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "fileNamesERcpt.get(0) : "
							+ fileNamesERcpt.get(0));
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "fileNamesERcpt.get(0).indexOf(.) : "
							+ fileNamesERcpt.get(0).indexOf("."));
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "fileNamesERcpt.get(0).length() : "
							+ fileNamesERcpt.get(0).length());
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "fileNamesERcpt.get(0) extnsn : "
							+ fileNamesERcpt.get(0).substring((fileNamesERcpt.get(0).indexOf(".")+1), fileNamesERcpt.get(0).length()));
//					String[] fileNameExtension = fileNamesERcpt.get(0).split(".");
//					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "fileNamesERcpt.get(0) extnsn : "
//							+ fileNameExtension[1]);
					String outputXml = ProdCreateXML.AddDocument(sessionId, itemIndex, LapsConfigurations.getInstance().ProTradeReceiptDoc,
							fileNamesERcpt.get(0).substring((fileNamesERcpt.get(0).indexOf(".")+1), fileNamesERcpt.get(0).length()),
							fileNamesERcpt.get(0).substring(0, fileNamesERcpt.get(0).indexOf(".")),
							LapsConfigurations.getInstance().volumeID,
							docIndex, 1, "N", String.valueOf(lFileLength));
					try {
						if("R".equalsIgnoreCase(mode)){
							APCallCreateXML.APInsert("TFO_DJ_REFRESH_DOC", "INSERTIONDATETIME,WI_NAME,CHANNELREFNUMBER,"
									+ "NG_ADD_DOC_OUTPUT", "SYSDATE,'"+WI_NAME+"','"+channelRefNo+"','"+outputXml+"'", sessionId);
						}
					} catch (Exception e1) {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in TFO_DJ_REFRESH_DOC e-receipt insert: ");
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e1);
					}
					xp = new XMLParser(outputXml);
					if("0".equalsIgnoreCase(xp.getValueOf("Status"))) {
						insertDocDecisionHistory(fileNamesERcpt.get(0)+" Linked Successfully");
					}else{
						insertDocDecisionHistory(fileNamesERcpt.get(0)+" Linking Failed");
						callStatus="N";
					}
				} else {
					callStatus = "Y";
					errorDescription = "E-Receipt Document folder not found";
					insertDocDecisionHistory(errorDescription);
				}
				File fileObjCustDoc = new File(documentPathCustDocs);
				if(fileObjCustDoc.exists()){
					List<String> fileNamesCustDoc = new ArrayList<String>();
					File[] filesCustDoc = fileObjCustDoc.listFiles(new FilenameFilter() {
						@Override
						public boolean accept(File dir, String name) {
							return true;
						}
					});
					for(File file : filesCustDoc){
						if(file.isFile()){
							fileNamesCustDoc.add(file.getName());
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "cust doc file Name : "
									+ file.getName());
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "cust doc file Length : "
									+ file.length());
						}
					}
					int fileCountCustDoc = fileNamesCustDoc.size();
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "fileCount : "+ fileCountCustDoc);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "fileNames : "+ fileNamesCustDoc.toString());
					for(int k=0; k<fileCountCustDoc; k++){
						long lFileLengthCustDoc = filesCustDoc[k].length();
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "lFileLengthCustDoc : "+ lFileLengthCustDoc);
						JPISDEC = new JPDBRecoverDocData();
						ISINDEX = new JPISIsIndex();
						JPISDEC.m_cDocumentType = 'N';
						JPISDEC.m_nDocumentSize = (int) lFileLengthCustDoc;
						JPISDEC.m_sVolumeId = (short) (LapsConfigurations.getInstance().volumeID);
						String filePathCustDoc = documentPathCustDocs+fileNamesCustDoc.get(k);
						try {
							if (JPISDEC.m_nDocumentSize != 0) {
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "JTSIP : "
										+ LapsConfigurations.getInstance().JTSIP);
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "JTSPort : "
										+ LapsConfigurations.getInstance().JTSPort);
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "documentPath : "
										+ filePathCustDoc);
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "volumeID : "
										+ LapsConfigurations.getInstance().volumeID);
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"JPISDEC.m_nDocumentSize : "
										+ JPISDEC.m_nDocumentSize);
								CPISDocumentTxn.AddDocument_MT(null, LapsConfigurations.getInstance().JTSIP,
										(short) LapsConfigurations.getInstance().JTSPort, 
										LapsConfigurations.getInstance().CabinetName, (short) (LapsConfigurations.getInstance()
												.volumeID),filePathCustDoc, JPISDEC, "", ISINDEX);
							}
						} catch (Exception e) {
							//e.printStackTrace();
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
						}
						docIndex = JPISDEC.m_nDocIndex + "#" + LapsConfigurations.getInstance().volumeID;
						String outputXmlDocs = ProdCreateXML.AddDocument(sessionId, itemIndex, LapsConfigurations.getInstance().ProTradeCustomerDoc,
								fileNamesCustDoc.get(k).substring((fileNamesCustDoc.get(k).indexOf(".")+1), fileNamesCustDoc.get(k).length()),
								fileNamesCustDoc.get(k).substring(0, fileNamesCustDoc.get(k).indexOf(".")), 
								LapsConfigurations.getInstance().volumeID,
								docIndex, 1, "N", String.valueOf(lFileLengthCustDoc));
						try {
							if("R".equalsIgnoreCase(mode)){
								APCallCreateXML.APInsert("TFO_DJ_REFRESH_DOC", "INSERTIONDATETIME,WI_NAME,CHANNELREFNUMBER,"
										+ "NG_ADD_DOC_OUTPUT", "SYSDATE,'"+WI_NAME+"','"+channelRefNo+"','"+outputXmlDocs+"'", sessionId);
							}
						} catch (Exception e1) {
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in TFO_DJ_REFRESH_DOC cust-doc insert: ");
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e1);
						}
						xp = new XMLParser(outputXmlDocs);
						if("0".equalsIgnoreCase(xp.getValueOf("Status"))) {
							insertDocDecisionHistory(fileNamesCustDoc.get(k)+" Linked Successfully");
						}else{
							insertDocDecisionHistory(fileNamesCustDoc.get(k)+" Linking Failed");
							callStatus = "N";
						}
					}
				} else {
					callStatus = "Y";
					errorDescription = "Customer Documents folder not found";
					insertDocDecisionHistory(errorDescription);
				}
				if("Y".equalsIgnoreCase(callStatus)){
					//insertDocDecisionHistory("Documents Linked Successfully");
					executeDependencyCall();
//					String channelRefPath = System.getProperty("user.dir")+File.separatorChar+LapsConfigurations.getInstance().docFolderName+File.separatorChar+channelRefNo;
//					Boolean deleteDirctry = LapsUtils.deleteDirectory(new File(channelRefPath));
//					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Dirctry deleted  : "+deleteDirctry);
				}else{
					insertDocDecisionHistory("Documents Linking Failed");
				}
			} catch (Exception e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"exception in getDocPDFDtls: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
				callStatus = "N";
			}
		} else {
			callStatus = "Y";
			errorDescription = "Document folder not found";
			insertDocDecisionHistory(errorDescription);
		}
	}

	public void insertDocDecisionHistory(String status) {
		try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside insertDocDecisionHistory");
			String valList = "'"+ WI_NAME +"','BPM-USER','"+currWS+"',SYSTIMESTAMP,'Document Linking','"+status+"'";
			APCallCreateXML.APInsert("TFO_DJ_DEC_HIST", "WI_NAME, USERNAME, CURR_WS_NAME, CREATE_DATE, ACTION, REMARKS", valList, sessionId);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public String createInputXML() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void responseHandler(String outputXML, String inputXml)
			throws Exception {
		// TODO Auto-generated method stub

	}
}
