
package com.newgen.ao.laps.calls;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.io.FileUtils;

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
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;
//DART 1132941 AYUSH
public class LinkTslmDoc implements ICallExecutor, Callable<String> {
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
	private String documentChannelRefPath = "";
	private String requestCategory; //ATP-284 Shivanshu
	private String hybridCustomer; //ATP-284 Shivanshu
	private String NASDocMovePath ="";
//DART 1129681 AYUSH
	public LinkTslmDoc(Map<String, String> defaultMap, String sessionId, String stageId, String WI_NAME, Boolean prevStageNoGo, CallEntity callEntity){
		this.WI_NAME = WI_NAME;
		stageID = Integer.parseInt(stageId);
		this.sessionId = sessionId;
		this.prevStageNoGo = prevStageNoGo;
		this.defaultMap = defaultMap;
		this.callEntity = callEntity;
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside LinkTslmDoc");
		if(defaultMap.containsKey("mode")){
			this.mode = defaultMap.get("mode");
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside LinkTslmDoc MODE : "+this.mode );
		String outputXML;		
		try {
			outputXML = APCallCreateXML.APSelect("SELECT ITEMINDEX,REQUEST_CATEGORY,HYBRID_CUSTOMER,CORRELATIONID FROM EXT_TFO WHERE WI_NAME = N'" + WI_NAME + "'");  //ATP-284 Shivanshu Added Column
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if(mainCode == 0){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"FetchDetails TotalRetrieved: "+Integer.parseInt(xp.getValueOf("TotalRetrieved")));
				if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
					itemIndex = xp.getValueOf("ITEMINDEX");//ATP-284 Shivanshu
					requestCategory = xp.getValueOf("REQUEST_CATEGORY"); 
					hybridCustomer = xp.getValueOf("HYBRID_CUSTOMER");		
					channelRefNo = xp.getValueOf("CORRELATIONID");	//ATP-479 REYAZ 10-06-2024
				}
			}		
			NASDocMovePath = String.valueOf(LapsConfigurations.getInstance().NASDataMovePath) + channelRefNo; //ATP-479 REYAZ 10-06-2024
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside LinkTslmDoc : "+channelRefNo);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
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
				callStatus = "N";
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
				LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "LTD100", e.getMessage(), sessionId);
			}
			updateCallOutput(inputXml);
		} else {
			callStatus="N";
			updateCallOutput(inputXml);
		}
		String outputXml = "<returnCode>"+returnCode+"</returnCode><errorDescription>"+errorDescription+"</errorDescription>";
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXml: " +outputXml);
		return outputXml;
	}

	@Override
	public void executeDependencyCall() throws Exception {
		try { //ATP-284 Shivanshu
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "LTD004", "DependencyCall"+ callEntity.getDependencyCallID(), sessionId);
			CallHandler.getInstance().executeDependencyCall(callEntity, defaultMap, sessionId, WI_NAME, prevStageNoGo);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "LTD100", e.getMessage(), sessionId);
		}
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside updateCallOutput");
		try {
			if(callStatus.trim().equalsIgnoreCase("Y")){ //ATP-284 Shivanshu
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside updateCallOutput Y ");
				Boolean result=moveFile();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Move Files result" +result);
				   
			} else {
				returnCode = -1 ;
				errorDetail = "Failed";
				errorDescription = "Document Linking failed";
			}
			//Added by Shivanshu ATP-479
			if("R".equalsIgnoreCase(mode) || "DCR".equalsIgnoreCase(mode) || "DMR".equalsIgnoreCase(mode)){
				stageID = -1 * stageID;
			}
			String valList = "'"+ WI_NAME +"',"+ stageID +", 'LinkTslmDoc', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"'";
			APCallCreateXML.APInsert("BPM_ORCHESTRATION_STATUS", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
					+ "ERROR_DETAIL, STATUS, REASON", valList, sessionId);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "LTD100", e.getMessage(), sessionId);
		}	
	}

	@Override
	public String executeCall(HashMap<String, String> map) throws Exception {
		return call();
	}
	private void getDocPDFDtls(String sessionId) throws JPISException {
		XMLParser xp;
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"PDFdetails execution starts");
		documentChannelRefPath = LapsConfigurations.getInstance().NASDocFolderName
				+File.separatorChar+channelRefNo+File.separatorChar;
		File docPath = new File(documentChannelRefPath);

		/*String documentPathEReceipt = File.separatorChar+LapsConfigurations.getInstance().NASDocFolderName
				+File.separatorChar+channelRefNo+File.separatorChar+LapsConfigurations.getInstance().TSLMReceiptDoc
				+File.separatorChar;

		String documentPathCustDocs = File.separatorChar+LapsConfigurations.getInstance().NASDocFolderName+File.separatorChar+channelRefNo
				+File.separatorChar+LapsConfigurations.getInstance().TSLMCustomerDoc
				+File.separatorChar;
		//Added by reyaz 23-01-2023
		String documentPathMiscDocs = File.separatorChar+LapsConfigurations.getInstance().NASDocFolderName+File.separatorChar+channelRefNo
				+File.separatorChar+LapsConfigurations.getInstance().TSLMMICSDoc
				+File.separatorChar;
		String documentPathApprovalDocs = File.separatorChar+LapsConfigurations.getInstance().NASDocFolderName+File.separatorChar+channelRefNo
				+File.separatorChar+LapsConfigurations.getInstance().TSLMApprovalDoc
				+File.separatorChar;
*/		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"documentChannelRefPath: "+documentChannelRefPath);
/*		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"documentPathEReceipt: "+documentPathEReceipt);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"documentPathCustDocs: "+documentPathCustDocs);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"documentPathMiscDocs: "+documentPathMiscDocs);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"documentPathApprovalDocs: "+documentPathApprovalDocs);
*/		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"TSLMCustomerDoc: "+LapsConfigurations.getInstance().TSLMCustomerDoc);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"mode: "+mode);
		
		
		if("DM".equalsIgnoreCase(mode) || "M".equalsIgnoreCase(mode)){ //ATP - 330 by Shivanshu
			modifyDocDetails();
		}
			String docIndex = "";
			JPISIsIndex ISINDEX = new JPISIsIndex();
			JPDBRecoverDocData JPISDEC = new JPDBRecoverDocData();
			try {
				List<String> fileNamesAll = new ArrayList<String>();
				File fileObj = new File(documentChannelRefPath);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"fileObj.exists(): "+fileObj.exists());
				if(fileObj.exists()){
					File[] allFiles = fileObj.listFiles(new FilenameFilter() {
						@Override
						public boolean accept(File dir, String name) {
							return true;
						}
					});
					for(File file : allFiles){
						if(file.isFile()){
							if(!file.getName().substring(file.getName().lastIndexOf(".")+1).equalsIgnoreCase("pgp"))
							fileNamesAll.add(file.getName());
						}
					}
					int allFileCount = fileNamesAll.size();
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "fileCount : "+ allFileCount);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "fileNames : "+ fileNamesAll.toString());
					for(File file:allFiles){
					long lFileLength = file.length();
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "lFileLength : "+ lFileLength);
					JPISDEC.m_cDocumentType = 'N';
					JPISDEC.m_nDocumentSize = (int) lFileLength;
					JPISDEC.m_sVolumeId = (short) (LapsConfigurations.getInstance().volumeID);
					String filePath = file.getPath();
					try {
						if (JPISDEC.m_nDocumentSize != 0) {
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "JTSIP : "
									+ LapsConfigurations.getInstance().JTSIP);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "JTSPort : "
									+ LapsConfigurations.getInstance().JTSPort);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "documentPath : "
									+ filePath);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "volumeID : "
									+ LapsConfigurations.getInstance().volumeID);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"JPISDEC.m_nDocumentSize : "
									+ JPISDEC.m_nDocumentSize);
							try {
							CPISDocumentTxn.AddDocument_MT(null, LapsConfigurations.getInstance().JTSIP,
									(short) LapsConfigurations.getInstance().JTSPort, 
									LapsConfigurations.getInstance().CabinetName, (short) (LapsConfigurations.getInstance()
											.volumeID),filePath, JPISDEC, "", ISINDEX);
							}catch (Exception e) {
								callStatus = "N";
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in AddDocument_MT: ");
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
							}
						}
					} catch (Exception e) {
						callStatus = "N";
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in add doc: ");
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
					}
					//Changes By Shivanshu ATP-405
					docIndex = JPISDEC.m_nDocIndex + "#" + LapsConfigurations.getInstance().volumeID;
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "fileNamesERcpt.get(0) : "
							+ file.getName().substring(0, file.getName().lastIndexOf(".")));
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "fileNamesERcpt.get(0).indexOf(.) : "
							+ file.getName().lastIndexOf("."));
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "fileNamesERcpt.get(0).length() : "
							+ file.getName().length());
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "fileNamesERcpt.get(0) extnsn : "
							+ file.getName().substring((file.getName().lastIndexOf(".")+1), file.getName().length()));
					
					String outputXml="";
					if(file.getName().substring(0, file.getName().lastIndexOf(".")).equalsIgnoreCase("E_Receipt")){
					outputXml = ProdCreateXML.AddDocument(sessionId, itemIndex, LapsConfigurations.getInstance().TSLMReceiptDoc,
							file.getName().substring((file.getName().lastIndexOf(".")+1), file.getName().length()),
							file.getName().substring(0, file.getName().lastIndexOf(".")),
							LapsConfigurations.getInstance().volumeID,
							docIndex, 1, "N", String.valueOf(lFileLength));
					}else if(file.getName().substring(0, file.getName().lastIndexOf(".")).equalsIgnoreCase("Customer_Docs")){
						outputXml = ProdCreateXML.AddDocument(sessionId, itemIndex, "Customer_Docs",
								file.getName().substring((file.getName().lastIndexOf(".")+1), file.getName().length()),
								file.getName().substring(0, file.getName().lastIndexOf(".")),
								LapsConfigurations.getInstance().volumeID,
								docIndex, 1, "N", String.valueOf(lFileLength));
					}else if(file.getName().substring(0, file.getName().lastIndexOf(".")).equalsIgnoreCase("Approval_Docs")){
						outputXml = ProdCreateXML.AddDocument(sessionId, itemIndex, "Approval_Docs",
								file.getName().substring((file.getName().lastIndexOf(".")+1), file.getName().length()),
								file.getName().substring(0, file.getName().lastIndexOf(".")),
								LapsConfigurations.getInstance().volumeID,
								docIndex, 1, "N", String.valueOf(lFileLength));
					}else{
						outputXml = ProdCreateXML.AddDocument(sessionId, itemIndex, "MICS_Docs",
								file.getName().substring((file.getName().lastIndexOf(".")+1), file.getName().length()),
								file.getName().substring(0, file.getName().lastIndexOf(".")),
								LapsConfigurations.getInstance().volumeID,
								docIndex, 1, "N", String.valueOf(lFileLength));
					}
					
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
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "outputXml ## "+ outputXml);
					if("0".equalsIgnoreCase(xp.getValueOf("Status"))) {
						insertDocDecisionHistory(file.getName()+" Linked Successfully");
						callStatus="Y"; //ATP -284 by Shivanshu
					}else{
						insertDocDecisionHistory(file.getName()+" Linking Failed");
						callStatus="N";
					}
				}
			} else {
					callStatus = "Y";
					errorDescription = "Document folder not found";
					insertDocDecisionHistory(errorDescription);
				}
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"callStatus: " + callStatus); //ATP-284 Shivanshu	
				if("Y".equalsIgnoreCase(callStatus)){
					//insertDocDecisionHistory("Documents Linked Successfully");
					// ATP-461 REYAZ 14-05-2024 START
					String outputXML = APCallCreateXML.APSelect("select WORKITEMID from WFINSTRUMENTTABLE "
							+ "where PROCESSINSTANCEID = '"+this.WI_NAME+"' and INTRODUCEDAT = 'Distribute1'"
									+ " and ACTIVITYNAME = 'ToDoList'");
					XMLParser xp3 = new XMLParser(outputXML);
					int wrkitmId = 0;
					int totalRetrievedCount = Integer.parseInt(xp3.getValueOf("TotalRetrieved"));
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "totalRetrievedCount " + totalRetrievedCount);
					if (Integer.parseInt(xp3.getValueOf("MainCode")) == 0 && totalRetrievedCount>0) {
						wrkitmId = Integer.parseInt(xp3.getValueOf("WORKITEMID"));
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "wrkitmId from WFINSTRUMENTTABLE: " + wrkitmId);
					}
					// ATP-461 REYAZ 14-05-2024 END
					String outputXML1 = APCallCreateXML.APSelect("SELECT Count(1) as Count FROM BPM_ORCHESTRATION_STATUS WHERE WI_NAME = N'" + this.WI_NAME + "' and CALL_STATUS IN ('N','F')"); //ATP-284 by shivanshu
					XMLParser xp1 = new XMLParser(outputXML1);
					int mainCode1 = Integer.parseInt(xp1.getValueOf("MainCode"));
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode: " + mainCode1);			
					if(mainCode1 == 0){
						if(!(Integer.parseInt(xp1.getValueOf("Count")) > 0)){
							// ATP-461 REYAZ 14-05-2024 START
							if("DM".equalsIgnoreCase(mode) || "M".equalsIgnoreCase(mode)) {
								if(wrkitmId != 0 && wrkitmId != 1){
									APCallCreateXML.APUpdate("EXT_TFO", "TARGET_WORKSTEP,ROUTE_TO_REPAIR", 
											"'Customer Referral Response',''", "WI_NAME = '"+this.WI_NAME+"'", sessionId);
									ProdCreateXML.WMCompleteWorkItem(sessionId, this.WI_NAME, wrkitmId);
									 executeDependencyCall();  //ATP 483 Reyaz 20-06-2024 
								}
							} // ATP-461 REYAZ 14-05-2024 END
							else {
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Count @@"+xp1.getValueOf("Count"));
							routeApplication(hybridCustomer); //ATP 212-213, ATP 126-128
							executeDependencyCall();  //ATP 126-128
						}
					}
					}
//					String channelRefPath = System.getProperty("user.dir")+File.separatorChar+LapsConfigurations.getInstance().NASDocFolderName+File.separatorChar+channelRefNo;
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
	
	private void modifyDocDetails(){
		String sQuery;
		String outputXml;
		List<String> documentIndex; 
		String itemIndex = "";
		try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "modify Doc Details starts");
			sQuery = "SELECT DOC.DOCUMENTINDEX DOCUMENTINDEX, FOLDER.FOLDERINDEX ITEMINDEX FROM PDBDOCUMENT DOC, "
					+ "PDBDOCUMENTCONTENT DOCCONTENT , PDBFOLDER FOLDER "
					+ "WHERE DOC.DOCUMENTINDEX = DOCCONTENT.DOCUMENTINDEX AND "
					+ "DOCCONTENT.PARENTFOLDERINDEX = FOLDER.FOLDERINDEX  "
					+ "AND FOLDER.NAME  = '"+this.WI_NAME+"' AND DOC.NAME  IN ('"
					+LapsConfigurations.getInstance().TSLMCustomerDoc+"','"+LapsConfigurations.getInstance().TSLMReceiptDoc+"',"
							+ "'"+LapsConfigurations.getInstance().TSLMMICSDoc+"','"+LapsConfigurations.getInstance().TSLMApprovalDoc+"')";
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "sQuery to get docindex is :"+sQuery);
			outputXml = APCallCreateXML.APSelect(sQuery);		
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"docindex outputxml " + outputXml);
			XMLParser xp = new XMLParser(outputXml);
			int start = xp.getStartIndex("Records", 0, 0);
			int deadEnd = xp.getEndIndex("Records", start, 0);
			int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
			int end = 0;
			documentIndex = new ArrayList<String>();
			for (int i = 0; i < noOfFields; ++i) {
				start = xp.getStartIndex("Record", end, 0);
				end = xp.getEndIndex("Record", start, 0);
				String docIndex = xp.getValueOf("DOCUMENTINDEX", start, end);
				documentIndex.add(docIndex);
				itemIndex = xp.getValueOf("ITEMINDEX", start, end);
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "documentIndex.size(): "+documentIndex.size());
			if(documentIndex.size()>0){
				String outputXmlDeLink = ProdCreateXML.DeleteDocument(sessionId, itemIndex, documentIndex);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "modify Doc Details ends");
				xp = new XMLParser(outputXmlDeLink);
				if ("0".equalsIgnoreCase(xp.getValueOf("Status")) || noOfFields==0){
					insertDocDecisionHistory("Documents Delinked Successfully");
				} else{
					insertDocDecisionHistory("Documents Delinked Failed");
					callStatus = "N";
				}
			} else {
				insertDocDecisionHistory("No Document Found");
				callStatus = "Y";
			}
		}
		catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in modify document: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			callStatus = "N";
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
	private Boolean moveFile(){
		boolean filesMoved=false;
		String fromDocumentPath = LapsConfigurations.getInstance().NASDocFolderName+File.separatorChar
				+channelRefNo;
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "fromChannelRefPath: "+fromDocumentPath);
		String toChannelRefPath = LapsConfigurations.getInstance().NASDataMovePath+channelRefNo;
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "toChannelRefPath: "+toChannelRefPath);
		File file = new File(toChannelRefPath);
		if(!file.exists()){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "new inside if");
			file.mkdir();
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "new directory created");
		}
		
		File from = new File(fromDocumentPath);
		File to = new File(toChannelRefPath);
		try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "new inside try");
			Files.move(from.toPath(), to.toPath(), StandardCopyOption.REPLACE_EXISTING);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "directory moved ");
			filesMoved = true;
			Boolean deleteDirctry = LapsUtils.deleteDirectory(new File(fromDocumentPath));
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "original directory deleted ");
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in delete document: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		return filesMoved;
	}
	//JIRA ATP 212-213 , ATP 126-128 by shivanshu
	public void routeApplication(String hybridCustomer){
		
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside routeApplication: ");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Hybrid Customer: "+hybridCustomer);
	
		try{

	//Route workitem to PP Maker if Hybrid Customer is no
	if((hybridCustomer.equalsIgnoreCase("N") || hybridCustomer.equalsIgnoreCase("No"))){
		APCallCreateXML.APUpdate("EXT_TFO", "TARGET_WORKSTEP", "'PP_MAKER'", "WI_NAME = '" + this.WI_NAME + "'", this.sessionId);
		ProdCreateXML.WMCompleteWorkItem(sessionId, this.WI_NAME, 1);
		}	
	
	//Route workitem to PP Maker if Request Category is IFCPC
	if(requestCategory.equalsIgnoreCase("IFCPC")&&!hybridCustomer.equalsIgnoreCase("N")){
		APCallCreateXML.APUpdate("EXT_TFO", "TARGET_WORKSTEP", "'PP_MAKER'", "WI_NAME = '" + this.WI_NAME + "'", this.sessionId);
		ProdCreateXML.WMCompleteWorkItem(sessionId, this.WI_NAME, 1);
		}
		}catch(Exception e1){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e1);
		}
	}
}
