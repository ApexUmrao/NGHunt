
package com.newgen.ao.laps.calls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;

import org.apache.commons.io.FileUtils;

import ISPack.CPISDocumentTxn;
import ISPack.ISUtil.JPDBRecoverDocData;
import ISPack.ISUtil.JPISException;
import ISPack.ISUtil.JPISIsIndex;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.ElementList;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.callhandler.CallHandler;
import com.newgen.ao.laps.core.ICallExecutor;
import com.newgen.ao.laps.logger.LapsModifyDBLogger;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.ConnectSocket;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.aproj2.arabic.pdf.ConvertHtmlToPdf;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.wf.util.excp.NGException;
//DART 1132941 AYUSH
public class DocumentLinkingDHL implements ICallExecutor, Callable<String> {
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
	private String seqNo;
	private String itemIndex;
	private boolean prevStageNoGo;
	private Map<String, String> defaultMap;
	private CallEntity callEntity;
	private String auditCallName = "LD";
	private String mode = "";
	private String docFlag = "";
	private String currWS = "Initiator";
	private String documentChannelRefPath = "";
	private String DocFolderName = "";
	private String DocName = "";

	private String processName = "";
	public String sJtsIp;
	public int iJtsPort;
	public int volumeID;
	String cabinetName;

	public DocumentLinkingDHL(Map<String, String> defaultMap, String sessionId, String stageId, String WI_NAME, Boolean prevStageNoGo, CallEntity callEntity){
		this.WI_NAME = WI_NAME;
		stageID = Integer.parseInt(stageId);
		this.sessionId = sessionId;
		this.prevStageNoGo = prevStageNoGo;
		this.defaultMap = defaultMap;
		this.callEntity = callEntity;
		cabinetName = LapsConfigurations.getInstance().CabinetName;
		seqNo =defaultMap.get("CONTRACT_REF_NO");
		processName =defaultMap.get("REQUESTOR_PROCESS");
		DocFolderName = LapsConfigurations.getInstance().DHLDocFolderName;
		DocName = LapsConfigurations.getInstance().DHLDocName;		
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside DocumentLinkingDHL :" +DocFolderName );
//		try {   //ATP-256
//			fetchAdviceDetails();
//		} catch (JPISException e) {
//			callStatus="N";
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
//			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "fetchAdviceDetails", e.getMessage(), sessionId);
//		}
	}

	@Override
	public String call() throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"call :" +prevStageNoGo);
		String inputXml = "";
		String outputXml = "<returnCode>0</returnCode>";
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Document Call STATUS :" + callStatus);
		try {
		if(!prevStageNoGo){
			fetchAdviceDetails(); //Calling Fetch Adivce ATP-256
			if("N".equalsIgnoreCase(callStatus)){
				outputXml= "<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>";
			}
			responseHandler(outputXml, inputXml);
			outputXml = outputXml + "<CallStatus>" + this.callStatus + "</CallStatus><errorDescription>" + this.errorDescription + 
			          "</errorDescription><Status>" + this.status + "</Status>";
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "DocumentLinkingDHL outputXml ===> " + outputXml);	
		} else {
			callStatus="N";
			updateCallOutput(inputXml);
			}
		} catch (Exception|JPISException e) {
			outputXml= "<returnCode>1</returnCode><errorDescription>PDF Document Generation Failed</errorDescription>";
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "Document", e.getMessage(), sessionId);
		}		
		return outputXml;
	}

	@Override
	public void executeDependencyCall() throws Exception {
		
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside updateCallOutput");
		try {
			if(callStatus.trim().equalsIgnoreCase("N")){
				returnCode = -1 ;
				errorDetail = "Failed";
				errorDescription = "Remiittance Letter Generation failed";
				docFlag = "No";
			} else {
				docFlag = "Yes";
				errorDescription = "Remiittance Letter Generated";//ATP-256
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside updateCallOutput else ");
				}
			

			String updateQuery = APCallCreateXML.APUpdate("EXT_TFO", "IS_DOCS_ATTACHED","'"+docFlag+"'", "WI_NAME = N'"+WI_NAME+"'", sessionId) ;
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO," Update Query " +updateQuery);
			XMLParser xmlDataParser1 = new XMLParser(updateQuery);
			int mainCode = Integer.parseInt(xmlDataParser1.getValueOf("MainCode"));
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[update ext]"  + " Main Code " +mainCode);
			
			String valList = "'"+ WI_NAME +"',"+ stageID +", 'LinkDHLDocuments', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
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
	
	private void fetchAdviceDetails() throws JPISException{
		try {
			int mainCode = -1;
			String sContractRefNo = "";	
			String customerID = "";	
			String accClassification = "";	

			int start = 0;
			int deadEnd;
			int noOfFields;
			String message = "";
			String media = "";
			String referenceNo = "";
			String DCN = "";
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"FCUBS Advice details populate");
			String outputXML = APCallCreateXML.APSelect("SELECT TRANSACTION_REFERENCE,CUSTOMER_ID,RELATIONSHIP_TYPE "
					+"from ext_tfo where wi_name='"+WI_NAME +"'");
			XMLParser xp1 = new XMLParser(outputXML);
			int mainCode1 = Integer.parseInt(xp1.getValueOf("MainCode"));
			if(mainCode1 == 0){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"TotalRetrieved: "+Integer.parseInt(xp1.getValueOf("TotalRetrieved")));
				if(Integer.parseInt(xp1.getValueOf("TotalRetrieved")) > 0){
					sContractRefNo = xp1.getValueOf("TRANSACTION_REFERENCE");	
					customerID = xp1.getValueOf("CUSTOMER_ID");					
					accClassification = xp1.getValueOf("RELATIONSHIP_TYPE");					
				}
			}
//			List<List<String>> sOutputlist = formObject.getDataFromDB("SELECT SEQ_WEBSERVICE.nextval as IDValue from DUAL");
			String pdfDetails="Document not generated";
//			String sContractRefNo = formObject.getValue(TRANSACTION_REFERENCE).toString();
			//String sContractRefNo = "299T3S4172083865";
			String sOperationName = "FetchAdviceDetails_Oper";
			//String seqNo = "1";
			String sOutputXML1 = fetchFCUBSAdvice(sContractRefNo,sOperationName,seqNo);
			XMLParser xmlparser1 = new XMLParser(sOutputXML1);

			mainCode = Integer.parseInt(xmlparser1.getValueOf("returnCode"));
			if (mainCode == 0) {
				start = xmlparser1.getStartIndex("messageDetails", 0, 0);
				deadEnd = xmlparser1.getEndIndex("messageDetails", start, 0);
				noOfFields = xmlparser1.getNoOfFields("messageDetail", start, deadEnd);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"No of Records in messagedetail: "+noOfFields);
				for (int i = 0; i < noOfFields; ++i){
					String messageDetail = xmlparser1.getNextValueOf("messageDetail");
//					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Message detail: "+messageDetail);
					XMLParser xp2 = new XMLParser(messageDetail); 
					String messageType = xp2.getValueOf("messageType");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"messageType : " +messageType);
					if ("REMITTANCE_LTR".equalsIgnoreCase(messageType)) {
						 message = xp2.getValueOf("message");
						 media = xp2.getValueOf("media");
						 referenceNo = xp2.getValueOf("referenceNumber");
						 DCN = xp2.getValueOf("DCN");
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"message: "+message);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"referenceNo: "+referenceNo);
					}
				}

			if (!message.isEmpty() && !referenceNo.isEmpty()) {
				String pdfFinalPath = generatePDF(message, referenceNo,accClassification,customerID, sessionId);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"pdfFinalPath : " +pdfFinalPath);
			}else{ 
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"FCUBS Advice details populate: message or referenceNo is empty");
				}
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"FCUBS Advice details populate maincode : "+ mainCode);
		} catch (Exception e) {
			callStatus="N";
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"FCUBS Advice details populate error : "+ e);
		}
	}
	
private String fetchFCUBSAdvice(String adcbContractRefNo,String sOperationName,String sequenceNo) {
	LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside getFCUBSAdviceInputXML.");
	String sInput = "";
	String sOutput="";
	String sysRefNo="";
	String outputXML;
	try {
		outputXML = APCallCreateXML.APSelect("SELECT SEQ_WEBSERVICE.nextval as IDValue from DUAL ");
		XMLParser xp1 = new XMLParser(outputXML);
		int mainCode1 = Integer.parseInt(xp1.getValueOf("MainCode"));
		if(mainCode1 == 0){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"TotalRetrieved: "+Integer.parseInt(xp1.getValueOf("TotalRetrieved")));
			if(Integer.parseInt(xp1.getValueOf("TotalRetrieved")) > 0){
				sysRefNo = xp1.getValueOf("IDValue");		
			}
		}
		sInput = getFCUBSAdviceInputXML(sysRefNo,adcbContractRefNo, sOperationName ,sequenceNo);
		sOutput = LapsConfigurations.getInstance().socket.connectToSocket_New(sInput.toString());
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside getFCUBSAdviceInputXML.. sOutput: "+sOutput);
		} catch (NGException e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"fetchFCUBSAdvice : "+ e);
		}
		return sOutput;
	}


	private String getFCUBSAdviceInputXML(String sSeqNo,String contractRefNo, String sOperationName, String eventSeqNo) {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside getFCUBSAdviceInputXML. ");
		String sInputXML = "<?xml version=\"1.0\"?><APWebService_Input><Option>WebService</Option><Calltype>WS-2.0"
				+ "</Calltype><InnerCalltype>FCUBSTradeContractOps</InnerCalltype><Customer>"
				+ "<refNo>"
				+ sSeqNo
				+ "</refNo>"
				+ "<serviceAction>Inquiry</serviceAction>"
				+ "<serviceName>InqCustomerTradeJourney</serviceName>"
				+ "<senderID>WMS</senderID>"
				+ "<WiName>"
				+ WI_NAME
				+ "</WiName>"
				+ "<operationType>"
				+ sOperationName
				+ "</operationType>" 
				+"<contractRefNo>"
				+ contractRefNo
				+"</contractRefNo>"
				+"<eventSequenceNumber>"
				+ eventSeqNo
				+"</eventSequenceNumber>";
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"input XML: "+sInputXML);
		sInputXML+="<EngineName>" + cabinetName + "</EngineName>"
				+ "<SessionId>" + sessionId + "</SessionId>"
				+ "</APWebService_Input>";
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside getFCUBSAdviceInputXML sInputXML:");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"input XML: "+sInputXML);
		return sInputXML;
	}
	
	private String getDocPDFDtlsNEW(String pdfPath,String sessionId) throws JPISException {
		String docIndex = "";
		StringBuffer sBuffer = new StringBuffer();
		try {
			int i = 21;
			int j = 25;
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"pdfPath :: "+pdfPath);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"PDFdetails execution starts");
			JPISIsIndex ISINDEX = new JPISIsIndex();
			File fileObj = new File(pdfPath);
			long lFileLength = fileObj.length();
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "lFileLength : "+ lFileLength);
			JPDBRecoverDocData JPISDEC = new JPDBRecoverDocData();
			JPISDEC.m_cDocumentType = 'N';
			JPISDEC.m_nDocumentSize = (int) lFileLength;
			JPISDEC.m_sVolumeId = (short) (LapsConfigurations.getInstance().volumeID);;
			try {
				if (JPISDEC.m_nDocumentSize != 0) {
					sJtsIp = LapsConfigurations.getInstance().JTSIP;
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "sJtsIp : "
							+ sJtsIp);
					iJtsPort = LapsConfigurations.getInstance().JTSPort;
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "iJtsPort : "
							+ iJtsPort);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "pdfPath : "
							+ pdfPath);
					volumeID = LapsConfigurations.getInstance().volumeID;
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "volumeID : "
							+ volumeID);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"JPISDEC.m_nDocumentSize : "
									+ JPISDEC.m_nDocumentSize);
					CPISDocumentTxn.AddDocument_MT(null, sJtsIp,
							(short) iJtsPort, cabinetName, (short) (volumeID),
							pdfPath, JPISDEC, "", ISINDEX);
					docIndex = JPISDEC.m_nDocIndex + "#" + volumeID;
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Document_MT Addition: "+docIndex);
					String outputXML;	
					try {
						outputXML = APCallCreateXML.APSelect("SELECT ITEMINDEX,CURR_WS FROM EXT_TFO WHERE WI_NAME = N'"
								+ WI_NAME + "'");
						XMLParser xp = new XMLParser(outputXML);
						int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
						String itemIndex = xp.getValueOf("ITEMINDEX");	
						if(mainCode == 0){
							deleteDocDetails(itemIndex);
							String outputXml = ProdCreateXML.AddDocumentNew(sessionId, itemIndex,
									"Remittance_Letter",
									"pdf","pdf",LapsConfigurations.getInstance().volumeID,
									docIndex, 1, "N", String.valueOf(lFileLength));
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Document Addition: "+outputXml);
						
						}		
					} catch (Exception e) {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
					}
				}
			} catch (Exception e) {
				String inputXml = "";
				callStatus="N";
				try {
					updateCallOutput(inputXml);
				} catch (Exception e1) {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
				}
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "In Catch Statement of getDocPDFDtls Method");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			}
			
			sBuffer.append("Remittance_Letter");
			sBuffer.append((char) i);
			sBuffer.append(docIndex);
			sBuffer.append((char) i);
			sBuffer.append("1");
			sBuffer.append((char) i);
			sBuffer.append(lFileLength);
			sBuffer.append((char) i);
			sBuffer.append("pdf");
			sBuffer.append((char) i);
			sBuffer.append("");
			sBuffer.append((char) j);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"PDFdetails : " + sBuffer.toString());
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "PDFdetails execution ends");
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "Error "+e);
		}
		return sBuffer.toString();
	}
	

	public void insertDocDecisionHistory(String status) {
		try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside insertDocDecisionHistory");
			String valList = "'"+ WI_NAME +"','BPM-USER','"+currWS+"',SYSTIMESTAMP,'Remittance Document Added','"+status+"'";
			APCallCreateXML.APInsert("TFO_DJ_DEC_HIST", "WI_NAME, USERNAME, CURR_WS_NAME, CREATE_DATE, ACTION, REMARKS", valList, sessionId);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "Error "+e);
		}
	}
	
	//Added by shivanshu for pdf generation
	@SuppressWarnings("unused")
	public String generatePDF(String message, String DCN, String relType, String CID, String sessionId) throws Exception {
		 LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Process starts for generate pdf");
		 String title = "Advice Details";
		 String htmlContent = "";
		 String pdfFolderPath = "";
		 String pdfPath = "";
		 StringBuffer stringBuffer = null;
		 String htmlGenerated = "";
		 String passPdfPath = "";
//		 String accClassification = "";

		 try { 
			
//			 htmlContent = "<!DOCTYPE html><html><head><title>"+title+"</title></head><body><p>"+ message + "</p></body></html>";
//			 LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"generate pdf html content"+ htmlContent);
			 LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"dirctry path : " + System.getProperty("user.dir"));
			 LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"DHLDocFolderName : " + DocFolderName);
			 pdfFolderPath = DocFolderName+File.separatorChar +"PDF";
			 LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"pdfFolderPath : "+ pdfFolderPath);
			 File file = new File(pdfFolderPath);
			 if (!file.exists()) {
				 Boolean createFile = file.mkdir();
				 LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"main folder created : " + createFile);
			 }
			 pdfPath = pdfFolderPath + File.separatorChar + DCN;
			 LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"pdfPath : "+ pdfPath);
			 file = new File(pdfPath);
			 if (!file.exists()) {
				 Boolean createFile = file.mkdir();
				 LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"DCN folder created : "+ createFile);
			 }
				
				 htmlGenerated = pdfPath + File.separatorChar + ""+DocName+".Pdf";
					/*
					 * LapsModifyLogger.logMe(LapsModifyLogger.
					 * LOG_LEVEL_INFO,"Rexecute case html path : "+ htmlGenerated); Writer fw = new
					 * OutputStreamWriter(new FileOutputStream(htmlGenerated), "UTF-8");
					 * fw.write(htmlContent); fw.close(); LapsModifyLogger.logMe(LapsModifyLogger.
					 * LOG_LEVEL_INFO,"Rexecute case html write success : "+ htmlGenerated); File
					 * fileObj = new File(htmlGenerated); long lFileLength = fileObj.length();
					 * LapsModifyLogger.logMe(LapsModifyLogger.
					 * LOG_LEVEL_INFO,"Rexecute case generatePDF lFileLength : "+ lFileLength);
					 * LapsModifyLogger.logMe(LapsModifyLogger.
					 * LOG_LEVEL_INFO,"Rexecute case Pdf Path After html success : "+ pdfPath);
					 * stringBuffer = new StringBuffer(); stringBuffer.append(pdfPath);
					 * stringBuffer.append(File.separatorChar);
					 */
			 	//ConvertHtmlToPdf pdfconvertor = new ConvertHtmlToPdf();
			 	
			 	//Added for Islamic Header Footer
			 	//relType = formObject.getValue(RELATIONSHIP_TYPE).toString();
			 	if ("C".equalsIgnoreCase(relType)) {
			 		relType = "Conventional";
				} else if ("I".equalsIgnoreCase(relType)) {
					relType = "Islamic";
				}
			 	
			 	String Header = DocFolderName+File.separatorChar+ "Images"  + File.separatorChar + relType 
			 					+ File.separatorChar + "header.JPG";
			 			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Rexecute case Header : " + Header);
			 	String Footer = DocFolderName+File.separatorChar + "Images" + File.separatorChar + relType 
			 					+ File.separatorChar + "footer.JPG";
			 			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Rexecute case Footer : " + Footer);
		 try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Rexecute case pdf starts ");
			GeneratePDF pdfgenerate =new GeneratePDF(Header,Footer,relType);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"message "+message);
			pdfgenerate.createPdf(htmlGenerated,message);
			try {
				 passPdfPath = pdfFolderPath + File.separatorChar + DCN + File.separatorChar + "Protected";
				 file = new File(passPdfPath);
				 	if (!file.exists()) {
				 		Boolean createFile = file.mkdir();
				 		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Rexecute case passPdfPath folder created : "+ createFile);
				 		}
				 	LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Rexecute case passPdfPath folder destination : "
				 			+ passPdfPath + File.separatorChar + ""+DocName+".pdf");
				 	encryptPdf(htmlGenerated,passPdfPath + File.separatorChar + ""+DocName+".pdf",CID);
				 	LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Rexecute case process ends for generate pdf");
				String docPDF =	getDocPDFDtlsNEW(passPdfPath + File.separatorChar + ""+DocName+".pdf",sessionId);
				deleteFromDirectory(pdfFolderPath);
			} catch (JPISException e) {
				callStatus="N";
	 			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"generate pdf Exception : "+ e);
			}
			
			/*
			 * com.newgen.aproj2.arabic.pdf.ConvertHtmlToPdf.pdgGen(htmlGenerated, pdfPath,
			 * stringBuffer.toString() + ""+DocName+".pdf", false, false, "", "", "ABC",
			 * "ABC1", "ABC2", Header, Footer);
			 */
			 	LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Rexecute case pdf ends ");
		 	} catch (Exception e) {
		 			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"ConvertHtmlToPdf Exception : "+ e);
		 		}
		 
		 	} catch (Exception e ) {
				callStatus="N";
		 		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "Rexecute case process ends for generate pdf" + e);
		      } 
		return htmlGenerated;
	  }

	private void deleteDocDetails(String itemIndex){
		String sQuery;
		String outputXml;
		List<String> documentIndex; 
		try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "modify Doc Details starts");
			sQuery = "Select pd.documentindex AS DOCUMENTINDEX  FROM PdbDocument pd "
					+ "	 inner join pdbDocumentContent pdc on pd.documentindex = pdc.documentindex "
					+ "	 where pd.Name  IN ('Remittance_Letter') "
					+ "	 and pdc.parentfolderindex = (select itemindex from ext_tfo where wi_name =  '"+WI_NAME+"') ";
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
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "documentIndex.size(): "+documentIndex.size());
			if(documentIndex.size()>0){
				String outputXmlDeLink = ProdCreateXML.DeleteDocument(sessionId, itemIndex, documentIndex);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "modify Doc Details ends");
				xp = new XMLParser(outputXmlDeLink);
				if ("0".equalsIgnoreCase(xp.getValueOf("Status")) || noOfFields==0){
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "documentIndex.size(): "+documentIndex.size());
				} 
			}
		}
		catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in modify document: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}

	}

	public void encryptPdf(String src, String dest , String customerID) throws IOException,DocumentException {
		 try {
			 File file = new File(src);
			// String customerID = formObject.getValue(CUSTOMER_ID).toString();
			 Boolean canRead = file.canRead();
			 Boolean canWrite = file.canWrite();
			 LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"canRead :" + canRead);
			 LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"canWrite :" + canWrite);
			 PdfReader reader = new PdfReader(src);
			 PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
			 stamper.setEncryption(customerID.getBytes(), "UCHIHA".getBytes(),
					 PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128
					 | PdfWriter.DO_NOT_ENCRYPT_METADATA);
			 stamper.close();
			 reader.close();
		 	} catch (Exception e) {
		 		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "encryptPdf error "+e);
		 		}
		 }

	public void onEndPage(PdfWriter writer, Document document) {
		 Font ffont = new Font(Font.FontFamily.UNDEFINED, 5, Font.ITALIC);
		 PdfContentByte cb = writer.getDirectContent();
		 Phrase header = new Phrase("this is a header", ffont);
		 Phrase footer = new Phrase("this is a footer", ffont);
		 ColumnText.showTextAligned(cb,	 Element.ALIGN_CENTER, header, (document.right() - document.left()) / 2 + document.leftMargin(), document.top() + 10, 0);
		 ColumnText.showTextAligned( cb, Element.ALIGN_CENTER, footer, (document.right() - document.left()) / 2 + document.leftMargin(), document.bottom() - 10, 0);
		 }

	 public void onStartPage(PdfWriter writer, Document document) throws DocumentException, IOException {
		 final String HEADER = "<table width=\"100%\" border=\"0\"><tr><td>Header</td><td align=\"right\">Some title</td></tr></table>";
		 final String FOOTER = "<table width=\"100%\" border=\"0\"><tr><td>Footer</td><td align=\"right\">Some title</td></tr></table>";
		 ElementList header;
		 ElementList footer;
		 PdfContentByte cb = writer.getDirectContent();
		 header = XMLWorkerHelper.parseToElementList(HEADER, null);
		 footer = XMLWorkerHelper.parseToElementList(FOOTER, null);
		 ColumnText ct = new ColumnText(writer.getDirectContent());
		 com.itextpdf.text.Rectangle rect = new com.itextpdf.text.Rectangle(36, 10, 559, 32);
		 ct.setSimpleColumn(rect);
		 for (Element e : header) {
		 ct.addElement(e);
		 }
		 ct.go();
		 com.itextpdf.text.Rectangle rect1 = new com.itextpdf.text.Rectangle(36, 10, 559, 810);
		 ct.setSimpleColumn(rect1);
		 for (Element e : footer) {
		 ct.addElement(e);
		 }
		 ct.go();
		 }
	
	@Override
	public String createInputXML() throws Exception {
		return null;
	}

	@Override
	public void responseHandler(String outputXML, String inputXml)
			throws Exception {
		try {
			XMLParser xp = new XMLParser(outputXML);
			returnCode = Integer.parseInt(xp.getValueOf("returnCode", "1", true));
			errorDescription = xp.getValueOf("errorDescription", "", true);
			errorDetail = xp.getValueOf("errorDetail", "", true);
			status = xp.getValueOf("Status", "", true);
			reason = xp.getValueOf("Reason", "", true);
			if(returnCode == 0){
				callStatus = "Y";
				LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "SS090", "Document Generate Successfully", sessionId);
			} else {
				callStatus = "N";
			}		 
			updateCallOutput(inputXml);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "SS100", e.getMessage(), sessionId);
		}
	}
	
	private void deleteFromDirectory(String filePath){
		try {
			File dir = new File(filePath);			
			if(dir.isDirectory()){
				deleteDir(dir);
			}			
		} catch (Exception e) {
			//insertAuditTrail("deleteFromDirectory Exception");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"[deleteFromDirectory]" +"deleteException e:"+e);
		}

	}
	/**
	 * Delete file and folder 
	 * @param file
	 */
	private void deleteDir(File file) {
		try {
			File[] contents = file.listFiles();
			if (contents != null) {
				System.out.println("contents : " +contents);
				for (File f : contents) {	
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[deleteDir]" + "Delete start"+f.toString());

					file.delete();
					deleteDir(f);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[deleteDir]" + "Delete done"+f.toString());
				}
			}
			file.delete();
		} catch (Exception e) {
			//insertAuditTrail("Delete Directory Function Exception");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"[deleteDir]" + "deleteDirException e:"+e);
		}
	}
}
