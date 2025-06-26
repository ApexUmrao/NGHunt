package com.newgen.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import ISPack.CPISDocumentTxn;
import ISPack.ISUtil.JPDBRecoverDocData;
import ISPack.ISUtil.JPISException;
import ISPack.ISUtil.JPISIsIndex;
import Jdts.DataObject.JPDBString; //07/01/2022

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSmartCopy;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.RandomAccessFileOrArray;
import com.itextpdf.text.pdf.codec.TiffImage;
import com.newgen.cps.cache.CPSMappingCache;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.wf.util.excp.NGException;


public class CPSPrepareDocument{
	
	private String WI_NAME;
	private int stageId;
	private String sessionId;
	private String callStatus;
	private int returnCode;
	private String errorDescription;
	private String errorDetail;
	private String status;
	private String reason;
	private String zipName;
	private String cid;
	private String pdfName;
	private String folderIndex;
	private String ISIndex;
	private String volumeID;
	private String docSize;	
	private String appName;
	private String folderLocation;
	private String documentPath;
	private String NOOFPages;
	private String CALL_NAME;
	private String refNo;
	private ConnectSocket socket;
	private static String socketIP;
	public int reqProcessDefId ;
	public String reqProcessName = "";
	public String reqProcessWS = "";
	public String outputString = "";
	public String cabinetName = "";
	public String volId_DD = "";
	public String JTSPort = "";
	public String JTSIP = "";
	public String CPSProcessName = "";
	public String initActivityName = "";
	public String EDBMSCabinet = "";
	public String colDesc = "";
	public String reInsertInterval = "";
	public String waterMarkText = "";
	public String waterMarkCount = "";
	public String rejectionFromID = "";
	public int indentation;
	public int noOfPages;
	public String finalOuptputXML = "";
	private Logger logger;
	private HashMap<String,ArrayList<String>> sortedDocs;
	static List<String> listTemp = new ArrayList<String>();
	static List<String> listTemp1 = new ArrayList<String>();
	static HashMap<String,ArrayList<String>> CPSDefaultMap ;
	static HashMap<String,String> CPSParentDecisionMap ;
	static HashMap<String,String> CPSCachedDecisionMasterMap ;
	static HashMap<String,String> CPSSourcingChannelMasterMap;
	List<File> filesToZip = new ArrayList<File>();
	String CPSWorkItemNumber = "";
	int CPSProcessDefId;
	int noOfFields;
	List<String> jointMinorDetails =new ArrayList<>();
	public String custSegement = "";

	
	public CPSPrepareDocument(String sessionId, String cabinetName, String WI_NAME,String refNo, 
			String processName, String sourcingChannel,String mode) {

		try {
        	StringBuilder log4jPropertyFile = new StringBuilder(System.getProperty("user.dir"))
        	.append(System.getProperty("file.separator"))
        	.append("log4j.properties");
        	System.out.println("Path of log4j file: "+ log4jPropertyFile);
        	InputStream stream = new FileInputStream(log4jPropertyFile.toString());        	
        	Properties p = new Properties();
        	p.load(stream);
        	stream.close();
        	PropertyConfigurator.configure(p);
        	logger = Logger.getLogger("CPSLogger");
        	logger.info("Logger is configured successfully");
        	logger.info("Logger is configured successfully from : " + log4jPropertyFile);
		} catch (Exception e) {
			insertAuditTrail("Logger Exception");
			logger.info("[Logger]" + " Exception: " + e);
		}
		this.sessionId=sessionId;
		this.WI_NAME = WI_NAME;
		this.cabinetName = cabinetName;
		this.refNo = refNo;
		CPSDefaultMap = CPSMappingCache.getInstance().getCachedStagecallMap(cabinetName,sessionId);
		CPSParentDecisionMap = CPSMappingCache.getInstance().getCachedParentDecisionMap(cabinetName,sessionId);
		CPSCachedDecisionMasterMap = CPSMappingCache.getInstance().getCachedDecisionMasterMap(cabinetName,sessionId);
		CPSSourcingChannelMasterMap = CPSMappingCache.getInstance().getSourcingChannelMasterMap(cabinetName,sessionId);
		logger.info("[CPSPrepareDocument]" + " CPSDefaultMap "+CPSDefaultMap);
		logger.info("[CPSPrepareDocument]" + " CPSParentDecisionMap "+CPSParentDecisionMap);



		try {
			readCPSConfig();
			logger.info("[CPSPrepareDocument]" + " readCPSConfig ");
		} catch (IOException e2) {
			logger.info("[CPSPrepareDocument]" + " readCPSConfig " + e2);

		}
		initializeSocket();
		String soutputXML = "";
		try {
			String sQuery1 = "Select processname, processdefid,activityname from wfinstrumenttable where "
					+ "processinstanceid = N'"+ WI_NAME + "'";
			soutputXML = ExecuteXML.executeXML(GenerateXml.APSelectWithColumnNames(cabinetName,sQuery1,sessionId));
			XMLParser sxp = new XMLParser(soutputXML);
			reqProcessDefId = Integer.parseInt(sxp.getValueOf("processdefid"));	
			reqProcessName = sxp.getValueOf("processname");	
			reqProcessWS = sxp.getValueOf("activityname");	

		}catch(Exception e){
			insertAuditTrail("Fetching WI Detail Exception");
			logger.info("[CPSPrepareDocument]" +"CPSAddDocument Exception"+e);
		}
		if(checkCPSWI()){
		String communicationType="";
		String finalDecision = getFinalDecision(); //Approve
		String reason = fetchFromCPSMapping(reqProcessName,"REASON");
		String reqSourcingChannel = fetchFromCPSMapping(reqProcessName,"SOURCING_CHANNEL"); //Branch
		String validcommunicationType = CPSSourcingChannelMasterMap.get(reqProcessName+"#"+reqSourcingChannel.toUpperCase()+"#"+finalDecision.toUpperCase()); //BRANCH
		logger.info("[CPSPrepareDocument]" +"CPSAddDocument validcommunicationType -> "+validcommunicationType);
		if(finalDecision.equalsIgnoreCase("Approve") && (validcommunicationType.equalsIgnoreCase("Y"))){
			communicationType="APPROVE";
			try {
				if(checkReqCategory()){
					if(!isReqWiForRetry()){
						createWI(communicationType);
					} else {
						deleteFromDirectory(folderLocation);
						getCPSWiDetails();
					}
				deleteFromAuditTable();
				String fetchedDocuments = executeOnWI(WI_NAME);
				downloadDocuments(fetchedDocuments);
				createDocument();
			 }
			} catch (Exception e) {
				insertAuditTrail("Approve Complete WI Exception");
				logger.info("[CPSPrepareDocument]" +"Exception"+e);
			}

		} else if(finalDecision.equalsIgnoreCase("Reject") && (validcommunicationType.equalsIgnoreCase("Y")) 
				&& (!reason.equalsIgnoreCase("") && !reason.isEmpty()) && checkRejectionReason()){
			try {
				communicationType="REJECT";
				if(checkReqCategory()){
					if(identifyprivateclients()){
						if(!isReqWiForRetry()){
							createWI(communicationType);
						} else {
							getCPSWiDetails();
						}
				deleteFromAuditTable();
				String errorDesc = "";
				String smsEmailProgress = "";
				int returnCode = -1;
				String mobile = "";
				String email = "";
				String fname = "";
				noOfFields = 1;	
				if(isAccMinorORJoint()){
					getJointMinorDetails();
				}
				for(int i=0 ; i < noOfFields ; i++){
					if(isAccMinorORJoint()){
						email = jointMinorDetails.get(i*5+2);
						mobile = jointMinorDetails.get(i*5+3);
						fname = jointMinorDetails.get(i*5+1);
					} else {
						mobile = fetchFromCPSMapping(reqProcessName,"MOBILE");
						email = fetchFromCPSMapping(reqProcessName,"EMAIL");
						fname = fetchFromCPSMapping(reqProcessName,"CUSTOMER_NAME");
					}
					if (email != null && !email.equalsIgnoreCase("")){
						String emailInputXML = createSMSAndEmailInputXML("B","0",email,mobile,fname);
						String webserviceOutput =  socket.connectToSocket(emailInputXML);
						XMLParser xmlDataParser = new XMLParser(webserviceOutput);
						returnCode = Integer.parseInt(xmlDataParser.getValueOf("returnCode"));
						errorDesc = xmlDataParser.getValueOf("errorDescription");
						logger.info("[CPSPrepareDocument]" +"The OutputXml for WEBSERVICE Workitem Call = " + webserviceOutput);
						logger.info("[CPSPrepareDocument]" +" Return Code Email = " + returnCode);
						logger.info("[CPSPrepareDocument]" +" Error Description = " + errorDesc);
						smsEmailProgress = "Email Alert, English SMS Sent Successfully to "+fname;

						if(returnCode == 0) {
							insertInAudit(smsEmailProgress);							
							emailInputXML = createSMSAndEmailInputXML("S","1",email,mobile,fname);
							webserviceOutput =  socket.connectToSocket(emailInputXML);
							xmlDataParser = new XMLParser(webserviceOutput);
							returnCode = Integer.parseInt(xmlDataParser.getValueOf("returnCode"));
							errorDesc = xmlDataParser.getValueOf("errorDescription");
							logger.info("[CPSPrepareDocument]" +"The OutputXml for WEBSERVICE Workitem Call = " + webserviceOutput);
							logger.info("[CPSPrepareDocument]" +" Return Code SMS = " + returnCode);
							smsEmailProgress = "Arabic SMS Alert Sent Successfully to "+fname;
							if (returnCode == 0) {
								insertInAudit(smsEmailProgress);
								if(i == noOfFields-1){
									GenerateXml.getGetWorkItemXML(CPSWorkItemNumber, "1", sessionId , cabinetName);
									String wiCompleteInputXML = GenerateXml.getWICompleteXML(CPSWorkItemNumber, "1", sessionId, cabinetName);
									logger.info("[createDocument]" + "wiCompleteInputXML 1234" + wiCompleteInputXML);
									xmlDataParser  = new XMLParser(wiCompleteInputXML);
									returnCode = Integer.parseInt(xmlDataParser.getValueOf("MainCode"));
									if (returnCode == 0) {
										updateWorkstep();
									}
									updateRetryCalls();
								}	
								//smsEmailProgress = "Workitem Completed Succesfully";
							}
							else{
								smsEmailProgress = "SMS Alert Failed";
								insertInAudit(smsEmailProgress);
							}
						}else{
							smsEmailProgress = "Email Alert Failed";
							insertInAudit(smsEmailProgress);
						} 
					}else{
						smsEmailProgress = "Email not Found";
						insertInAudit(smsEmailProgress);
					} 
				}
			  }
			 }
			deleteFromEventgenTable();
			} catch (Exception e) {
				//deleteFromEventgenTable();
				insertAuditTrail("Reject Complete WI Exception");
				logger.info("[CPSPrepareDocument]" +"RejectionException e:"+e);
			}
		 }else{
			 deleteFromEventgenTable();
			 insertAuditTrail("Req WI Decision is Null or Communication Type is N");
			 logger.info("[CPSPrepareDocument]" +" Req WI Decision is Null or Communication Type is N");
		 }
		}else{
			insertAuditTrail("Requestor Workitem is already Processed");
			logger.info("[CPSPrepareDocument]" +" Requestor Workitem is already Processed");
		}
	}
		
	public String callMailProcedure(int i){
		logger.info("[callMailProcedure]" +"callMailProcedure inside :");
		String sOutputXML ="";
		String toEmail = "";
		String customerName = "";

		try {
			if(isAccMinorORJoint()){
				toEmail = jointMinorDetails.get(i*5+2);
				customerName = jointMinorDetails.get(i*5+1);
			} else {
				toEmail = fetchFromCPSMapping(reqProcessName,"EMAIL");
				customerName = fetchFromCPSMapping(reqProcessName,"CUSTOMER_NAME");

				//toEmail = "sudhanshusingh.ext@adcb.com";
			}
			String accType = fetchFromCPSMapping(reqProcessName,"ACC_CLASS");
			String reqSourcingChannel = fetchFromCPSMapping(reqProcessName,"SOURCING_CHANNEL"); //Branch
			String customerSegment = getSegment();
	
			if(accType.equals("I")){
				accType = "Islamic";
			}
			else if(accType.equals("C")){
				accType = "Conventional";
			}
			
			
			sOutputXML = GenerateXml.APProcedure_new(sessionId,cabinetName,"CPS_EMAIL_NOTIFY","'"
					+WI_NAME+"','"+CPSWorkItemNumber+"','"+accType+"','"+toEmail+"',"+(i+1)+",'"+customerName+"','"+reqSourcingChannel+"','"+customerSegment+"'",2);
			logger.info("[callMailProcedure]" +"callMailProcedure sOutputXML :"+sOutputXML);
		} catch (Exception e) {
			//deleteFromEventgenTable();
			insertAuditTrail("Approve Mail Exception");
			logger.info("[callMailProcedure]" +"ProcedureException e:"+e);
		}
		return sOutputXML;
	}

	public String getFinalDecision() {
		String finalDecison = "";
		try{
			logger.info("[getFinalDecision]" +"fileinside getFinalDecision ");
			logger.info("[getFinalDecision]" + "getFinalDecision currWS"+reqProcessWS);

			String decMaster = "";
			decMaster = CPSCachedDecisionMasterMap.get(reqProcessName+"#"+reqProcessWS);
			logger.info("[getFinalDecision]" + "getFinalDecision decMaster "+decMaster);
			if(decMaster == null){
				logger.info("[getFinalDecision]" + "getFinalDecision decinside null ");

				String tableName = "bpm_eventgen_txn_data";
				String whereClause =  "WI_NAME='" + WI_NAME + "'";
				String colName = "insertiondatetime, wi_name, expiry_date,"
						+ "status_flag,PROCESS_NAME,SOURCING_CHANNEL, REQUESTMODE";
				String Values = "SYSDATE,'"+WI_NAME+"',(SYSDATE+("+reInsertInterval+"/24)),'N','CPS','CPS','C'";
				try {
					logger.info("[getFinalDecision]" + "getFinalDecision decinside null123 ");

					String soutputXML = GenerateXml.APDelete(cabinetName,tableName,whereClause,sessionId);
					logger.info("[getFinalDecision]" + "getFinalDecision decinside null delete ");

				} catch (Exception e) {
					logger.info("[getFinalDecision]" +"FinalDecisonException e:"+e);
				}			
				String soutputXML = GenerateXml.apInsertInputXml(cabinetName,sessionId,tableName,colName,Values);
				return "";
			} else if(decMaster.equalsIgnoreCase("Both")){
				logger.info("[getFinalDecision]" + "getFinalDecision inside both "+decMaster);
				String wiDecision = fetchFromCPSMapping(reqProcessName,"WI_DECISION").toUpperCase();
				String parentDecision = CPSParentDecisionMap.get(reqProcessName+"#"+wiDecision);
				logger.info("[getFinalDecision]" + "getFinalDecision final Decision "+finalDecison);
				if(parentDecision.equalsIgnoreCase("Approve") || parentDecision.equalsIgnoreCase("Reject")){
					logger.info("[getFinalDecision]" + "Inside If WBG Decision");
					finalDecison = parentDecision;  //default
				}else{
					logger.info("[getFinalDecision]" + "Inside Else WBG Decision");
					finalDecison = "null";
				}
			} else {
				logger.info("[getFinalDecision]" + "getFinalDecision else "+finalDecison);
				finalDecison = decMaster;
			}
		} catch (Exception e){
			insertAuditTrail("getFinalDecision Exception");
			deleteFromEventgenTable();
			logger.info("[getFinalDecision]" + "getFinalDecision exception "+e);
		}
		return finalDecison;
	}

	public String getRefNo() throws Exception{
		try{
		String seqQuery = "SELECT SEQ_WEBSERVICE.nextval as REFNO from DUAL";
		String outputXml = GenerateXml.APSelectWithColumnNames(cabinetName,seqQuery,sessionId);
		logger.info("[getRefNo]" + "The InputXml for SEQ Workitem Call = " + outputXml);
		String seqQueryOutputXML =  ExecuteXML.executeXML(outputXml);
		logger.info("[getRefNo]" + "The OutputXml for SEQ Workitem Call = " + seqQueryOutputXML);
		XMLParser xp1 = new XMLParser(seqQueryOutputXML);
		int mainCodeSeq = Integer.parseInt(xp1.getValueOf("MainCode"));
		if(mainCodeSeq == 0){
			refNo = xp1.getValueOf("REFNO");  // all ref stxn
		}
		}catch (Exception e) { 
			insertAuditTrail("getRefNo Exception");
			logger.error("[getRefNo]" + "Exception: " + e);
		}
		return refNo;
	}

	public String createDocument() throws Exception {
		String outputXml = "";
			String stichProgress = "Documents Merged Successfully";
			String zipProgress = "";
			String docAddProgress = "Zipped Document Added Successfully";
			String mailProgress = "Mail Sent Failure";

			try {
				int counter = 0;
				CALL_NAME = "CPSAddDocument";
				String sPath = "";
				noOfFields = 1;

				if(isAccMinorORJoint()){
					getJointMinorDetails();
				}
				
				for(int i=0 ; i < noOfFields ; i++){
					if(isAccMinorORJoint()){
//						cid = jointMinorDetails.get(i*4);   //custid
						cid = jointMinorDetails.get(i*5);
						logger.info("[createDocument]" + "cid "+cid);
					} else {
						cid = fetchFromCPSMapping(reqProcessName,"CID");
					}
					String[] docPagesAndDest = stichMultipleDocument(WI_NAME,i).split("#");
					NOOFPages = docPagesAndDest[0];//From stichMultipleDocument Function
					sPath = docPagesAndDest[1]; //From stichMultipleDocument Function
					logger.info("[createDocument]" + "filesToZip "+filesToZip.toString());
					if(filesToZip !=null && filesToZip.size()>0){
						//						zipName = zipName.replaceAll("CID",cid);
						String zipFileName = folderLocation+"/"+zipName+i+".zip";	
						//String zipFileName = "/appl/WMS/CPS/"+WI_NAME+"/"+WI_NAME+"StichedPdf.zip";
						String strPassword = "";
						if(isAccMinorORJoint()){
							strPassword = jointMinorDetails.get(i*5+4);   //For Customer ID as Password	
						} else {
							strPassword = fetchFromCPSMapping(reqProcessName,"PASSWORD");
						}
						String sourcingChannel = fetchFromCPSMapping(reqProcessName,"SOURCING_CHANNEL");
						logger.info("Inside itqan" + sourcingChannel);
						if(sourcingChannel.equalsIgnoreCase("ITQAN")){
							strPassword = cid;
						}
	//					 String strPassword = fetchFromCPSMapping(reqProcessName,"PASSWORD"); //For DOB as Password

						logger.info("[createDocument]" + "zipFileName "+zipFileName);
						logger.info("[createDocument]" + "strPassword "+strPassword);

						boolean isFilesZipped = zipFiles(zipFileName, filesToZip, strPassword);
						
						if(isFilesZipped){
							zipProgress = "Documents Zipped And Password Protected Successfully";
						} else {
							zipProgress = "Documents zipped Exception Occured.";
						}
						logger.info("[createDocument]" + "isFilesZipped "+isFilesZipped);
						String path = folderLocation+"/"+WI_NAME+"BusinessDocuments"+i+".pdf";

						//insertInAudit(docAddProgress);
						

						if(isFilesZipped){
							outputXml = uploadDocument(zipFileName);
							XMLParser xp  = new XMLParser(outputXml);
							int mainCode = Integer.parseInt(xp.getValueOf("Status"));
							logger.info("[call]" + "mainCode 1234" + mainCode);
							if (mainCode == 0) {
								logger.info("[createDocument]" + "docAddProgress 1234" + docAddProgress);
								docAddProgress = "Zipped Document Added Successfully to WorkItem";
								if(mailRequired()){
									logger.info("[callMailProcedure]" +"mailRequired: inside condition");		
									outputXml = callMailProcedure(i);
									XMLParser xp1  = new XMLParser(outputXml);
									mainCode = Integer.parseInt(xp1.getValueOf("MainCode"));
									if (mainCode == 0) {
										mailProgress = "Mail Sent Successfully";
										if(i == noOfFields-1){    //last customer mail is sent
											GenerateXml.getGetWorkItemXML(CPSWorkItemNumber, "1", sessionId , cabinetName);
											String wiCompleteInputXML = GenerateXml.getWICompleteXML(CPSWorkItemNumber, "1", sessionId, cabinetName);
											logger.info("[createDocument]" + "wiCompleteInputXML 1234" + wiCompleteInputXML);
											xp1  = new XMLParser(wiCompleteInputXML);
											mainCode = Integer.parseInt(xp1.getValueOf("MainCode"));
											if (mainCode == 0) {
												updateWorkstep();
												insertAuditTrail("Workitem Processed Successfully");
											}
											String filePath = folderLocation;
											logger.info("[createDocument]" + "filePath 1234" + filePath);		
											deleteFromDirectory(filePath);
										}

									}
								}else{
									mailProgress = "Email Skipped Successfully";
									GenerateXml.getGetWorkItemXML(CPSWorkItemNumber, "1", sessionId , cabinetName);
									String wiCompleteInputXML = GenerateXml.getWICompleteXML(CPSWorkItemNumber, "1", sessionId, cabinetName);
									logger.info("[createDocument]" + "wiCompleteInputXML 1234" + wiCompleteInputXML);
									XMLParser xp2  = new XMLParser(wiCompleteInputXML);
									mainCode = Integer.parseInt(xp2.getValueOf("MainCode"));
									if (mainCode == 0) {
										updateWorkstep();
										insertAuditTrail("Workitem Processed Successfully");
									}
								}
							} else {
								docAddProgress = "Zipped Document Addition to WorkItem Failed";
							}
							logger.info("[createDocument]" + "docAddProgress " + docAddProgress);
							logger.info("[createDocument]" + "MailProgress " + mailProgress);

						}
						logger.info("[createDocument]" + "callMailProcedure bfr");
					}
				}


			} catch (Exception e) {
				stichProgress = "Document Merged Exception Occured";
				insertAuditTrail("createDocument Exception");
				deleteFromEventgenTable();
				logger.info("[createDocument]" +"callExcepton"+e );
				logger.error("[createDocument]" + "Error Occured" +e.toString());
				logger.error("[createDocument]" + "CPSDOC100");
			}
			insertInAudit(stichProgress);
			insertInAudit(zipProgress);
			insertInAudit(docAddProgress);	
			insertInAudit(mailProgress);
		return outputXml;
	}

	private void deleteFromDirectory(String filePath){
		try {
			File dir = new File(filePath);			
			if(dir.isDirectory()){
				deleteDir(dir);
			}			
		} catch (Exception e) {
			insertAuditTrail("deleteFromDirectory Exception");
			logger.error("[deleteFromDirectory]" +"deleteException e:"+e);
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
					logger.info("[deleteDir]" + "Delete start"+f.toString());

					file.delete();
					deleteDir(f);
					logger.info("[deleteDir]" + "Delete done"+f.toString());
				}
			}
			file.delete();
		} catch (Exception e) {
			insertAuditTrail("deleteFromDirectory Exception");
			logger.error("[deleteDir]" + "deleteDirException e:"+e);
		}
	}

	private boolean zipFiles(String zipFileName, List<File> filesToZip, String zipPass) {
		ZipFile zipFile=null;
		ZipParameters zipParameters=null;
		try {
			zipFile = new ZipFile(zipFileName);
			zipParameters = new ZipParameters();
			zipParameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			zipParameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_ULTRA);
			zipParameters.setEncryptFiles(true);
			zipParameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
			zipParameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
			zipParameters.setPassword(zipPass);
			zipFile.addFiles(new ArrayList<File>(filesToZip), zipParameters);
		} catch (Throwable exp) {
			insertAuditTrail("zipFile Exception");
			logger.error("[zipFiles]" + "zipFilesException exp :"+exp);
			logger.error("[zipFiles]" + "CPSDOC100");
			return false;
		}finally{
			if(zipFile!=null)
				zipFile=null;
			filesToZip.clear();
			if(zipParameters!=null)
				zipParameters=null;
		}
		return true;
	}

	public String createInputXML() throws Exception {
		StringBuilder inputXML = new StringBuilder();
		//		refNo = CBGUtils.getInstance().generateSysRefNumber();
		try {
			inputXML.append("<?xml version=\"1.0\"?>").append("\n")
			.append("<NGOAddDocument_Input>").append("\n")
			.append("<Option>NGOAddDocument</Option>").append("\n")
			.append("<CabinetName>WMSUPGUAT</CabinetName>").append("\n")
			.append("<UserDBId>" + sessionId + "</UserDBId>").append("\n")
			.append("<GroupIndex>0</GroupIndex>").append("\n")
			.append("<Document>").append("\n")		
			.append("<ParentFolderIndex>" + folderIndex + "</ParentFolderIndex>").append("\n")
			.append("<DocumentName>"+ zipName +"</DocumentName>").append("\n")
			.append("<CreatedByAppName>" + appName + "</CreatedByAppName>").append("\n")
			.append("<Comment>" + zipName + "</Comment>").append("\n")
			.append("<VolumeIndex>" + volumeID + "</VolumeIndex>").append("\n")		
			//.append("<ProcessDefId>" + LapsConfigurations.getInstance().ProcessDefId + "</ProcessDefId>").append("\n")		
			.append("<ProcessDefId>" + CPSProcessDefId + "</ProcessDefId>").append("\n")		
			.append("<DataDefinition>" + "" + "</DataDefinition>").append("\n")		
			.append("<AccessType>S</AccessType>").append("\n")		
			.append("<ISIndex>" + ISIndex + "</ISIndex>").append("\n")		
			.append("<NoOfPages>1</NoOfPages>").append("\n")		
			.append("<DocumentType>N</DocumentType>").append("\n")		
			.append("<DocumentSize>" + docSize + "</DocumentSize>").append("\n")		
			.append("<FTSFlag>PP</FTSFlag>").append("\n")		
			.append("</Document>").append("\n")		
			.append("</NGOAddDocument_Input>");
			logger.info("[createInputXML]" + "CPSAddDocument inputXML ===>> "+inputXML.toString());
		} catch (Exception e) {
			insertAuditTrail("zipInputXML Exception");
			//deleteFromEventgenTable();
			logger.error("[createInputXML]" + "InputXMLException"+e);
			logger.error("[createInputXML]" + "CPSDOC1001");
		}
		return inputXML.toString();   
	}
	
	public void responseHandler(String outputXML, String inputXML) throws Exception {
		try {
			XMLParser xp = new XMLParser(outputXML);
			returnCode = Integer.parseInt(xp.getValueOf("Status", "1", true));
			if(returnCode == 0) {
				callStatus = "S";
				errorDescription = "Successfully Executed";
				logger.info("[responseHandler]" + "CPSDOC090");
			} else {
				callStatus = "F";
				errorDescription = "Failed";
				logger.info("[responseHandler]" + "CPSDOC101");
			}
			updateCallOutput(inputXML);
		} catch (Exception e) {
			logger.error("[responseHandler]" + "ResponseHandlerException"+e);
			logger.error("[responseHandler]" + "CPSDOC100");
		}			
	}

	public void updateCallOutput(String inputXml) throws Exception {
		try {
			String valList = "'"+ WI_NAME +"',"+ stageId +", '"+CALL_NAME+"', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"'";
			APCallCreateXML.APInsert("USR_0_CBG_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON", valList, sessionId);
		} catch (Exception e) {
			
			logger.error("[updateCallOutput]" + "CallOutputException"+e);
			logger.error("[updateCallOutput]" + WI_NAME);
		}
	}

	public String executeOnWI(String winame)
	{
		String sQuery = "";
		String sourcingChannel = fetchFromCPSMapping(reqProcessName,"SOURCING_CHANNEL");
		logger.info("Inside executeOnWI" + sourcingChannel);
		try
		{
			if(sourcingChannel.equalsIgnoreCase("ITQAN")){
				sQuery = "select a.DOCUMENTINDEX ,a.IMAGEINDEX, a.VOLUMEID,a.NOOFPAGES,a.DOCUMENTSIZE, a.APPNAME,"
					+ " a.COMMNT,a.NAME,b.orderid ,b.DOC_DESCRIPTION from pdbdocument a inner join usr_0_cps_doc_mast b on b.doctype=a.name where a.documentindex in "
					+ "(select DOCUMENTINDEX from pdbdocumentcontent where parentfolderindex in"
					+ "(select folderindex  from pdbfolder where folderindex ="
					+ "(select folderindex from pdbfolder where name = '"+winame+"'))) "
					+ "and upper(b.doctype) = upper(a.name) and b.processname = '"+reqProcessName+"'"
					+ " and b.doc_flag = 'Y'  and orderid in (1,3,4) order by b.orderid";
			}else {
			sQuery = "select a.DOCUMENTINDEX ,a.IMAGEINDEX, a.VOLUMEID,a.NOOFPAGES,a.DOCUMENTSIZE, a.APPNAME,"
					+ " a.COMMNT,a.NAME,b.orderid ,b.DOC_DESCRIPTION from pdbdocument a inner join usr_0_cps_doc_mast b on b.doctype=a.name where a.documentindex in "
					+ "(select DOCUMENTINDEX from pdbdocumentcontent where parentfolderindex in"
					+ "(select folderindex  from pdbfolder where folderindex ="
					+ "(select folderindex from pdbfolder where name = '"+winame+"'))) "
					+ "and upper(b.doctype) = upper(a.name) and b.processname = '"+reqProcessName+"'"
					+ " and b.doc_flag = 'Y' order by b.orderid";
			}
			logger.info("[executeOnWI]" + "executeOnWI cabinetName : " + cabinetName);

			String outputXML = ExecuteXML.executeXML(GenerateXml.APSelectWithColumnNames(cabinetName,sQuery,sessionId));
			XMLParser xp = new XMLParser(outputXML);
			outputString = xp.getValueOf("APSelectWithColumnNames_Output");	
			logger.info("[executeOnWI]" + "executeOnWI sQuery : " + sQuery);
			logger.info("[executeOnWI]" + "executeOnWI sQuery : " + outputXML);
			logger.info("[executeOnWI]" + "executeOnWI outputString : " + outputString);
			logger.info("[executeOnWI]" + "executeOnWI sQuery : " + xp);
		
		}catch (Exception localException5){
			//deleteFromEventgenTable();
			insertAuditTrail("executeOnWI Exception");
			logger.error("[executeOnWI]" + "executeOnWI Exception : " +localException5);
		} 

		return outputString; 
	}

	private void createWI(String communicationType) throws NGException{
		String accClass = fetchFromCPSMapping(reqProcessName,"ACC_CLASS");
		if(accClass.equals("I")){
			accClass = "Islamic";
		}
		else if(accClass.equals("C")){
			accClass = "Conventional";
		}
	
		HashMap<String, String> attribMap = new HashMap<String, String>();
		attribMap.put("REQUESTOR_PROCESS", reqProcessName);
		attribMap.put("REQUESTOR_WI_NAME", WI_NAME);
		String currentDate = getCurrentDate();
		attribMap.put("REQ_DATE_TIME", currentDate);
		attribMap.put("COMMUNICATION_TYPE",communicationType);
		attribMap.put("ACC_CLASS", accClass);
		logger.info("[CPSPrepareDocument}" + "attriMap :"+attribMap );
		String outputXml = "";
		try {
			outputXml = GenerateXml.WFUploadWorkItem(sessionId, "Y", attribMap,"",CPSProcessDefId,initActivityName,cabinetName);
			logger.info("[CPSPrepareDocument]" +"outputXml 123:"+outputXml);
			XMLParser xp  = new XMLParser(outputXml);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			logger.info("[CPSPrepareDocument]" +"mainCode 123" + mainCode);
			if (mainCode == 0) {
				CPSWorkItemNumber = xp.getValueOf("ProcessInstanceId");
				folderIndex = xp.getValueOf("FolderIndex");	
			}
		} catch (Exception e1) {
			insertAuditTrail("Create CPS WI Exception");
			deleteFromEventgenTable();
			logger.info("[CPSPrepareDocument]" +"CPSAddDocument Exception");
		}
	}

	private boolean isAccMinorORJoint(){
		if(reqProcessName.equalsIgnoreCase("AO")){
			String accType = fetchFromCPSMapping(reqProcessName,"ACC_TYPE");
			if(accType.equalsIgnoreCase("Joint") || accType.equalsIgnoreCase("Minor") || getNoOfAccount()){
				return true;
			}
		} else if(reqProcessName.equalsIgnoreCase("WBG_AO")){
			String sourcingChannel = fetchFromCPSMapping(reqProcessName,"SOURCING_CHANNEL");
			logger.info("Inside WBG_AO" + sourcingChannel);
			if(sourcingChannel.equalsIgnoreCase("ITQAN")){
				return true;
			}
		} 
		return false;
	}
	
//	private String CreateJointMinorPDF() throws MalformedURLException, IOException, DocumentException{
//		String outputXML = getJointMinorDetails();
//		XMLParser xp = new XMLParser(outputXML);			
//		int start = xp.getStartIndex("Records", 0, 0);
//		int deadEnd = xp.getEndIndex("Records", start, 0);
//		int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
//		int end = 0;
//		int indentation = 0;
//		for (int i = 0; i < noOfFields; ++i) {
//			start = xp.getStartIndex("Record", end, 0);
//			end = xp.getEndIndex("Record", start, 0);
//			String custID = xp.getValueOf("CUST_ID" , start, end);
//			String finalFullName = xp.getValueOf("FINAL_FULL_NAME" , start, end);
//			logger.info("[createStagecallMap]" + " custID is " + custID);
//			logger.info("[createStagecallMap]" + " finalFullName is " + finalFullName);			
//			
//				Document document = new Document();
//				String DEST = folderLocation+"/"+WI_NAME+"StichedPdf"+finalFullName+".pdf";
//		        PdfWriter writer;
//    			writer = PdfWriter.getInstance(document, new FileOutputStream(DEST));
//				document.open();
//				String imageDir = folderLocation+ "/Documents";
//		         logger.info( "imageDir :"+imageDir);
//		         File file = new File(imageDir);
//		         String[] fileList = file.list();
//		        for (String fileName : fileList) {
//		        		if(fileName.contains(".pdf")){
//			        		 listTemp.add(folderLocation+"/Documents/"+fileName);
//			        	 } else {
//			        		 logger.info( "inside for fileName :"+fileName);
//			 	            Image image2 = Image.getInstance(folderLocation+"/Documents/"+fileName);
//			 		         logger.info( "inside for image2 :"+image2);
//			 	            float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
//			 	                  - document.rightMargin() - indentation) / image2.getWidth()) * 100;
//			 	            image2.scalePercent(scaler);
//			 	            document.newPage();
//			 	            document.add(image2);
//			        	 }
//		        	
//		        	 
//		         }
//		         document.close();
//		         logger.info( "stichMultipleDocument File docSize :"+docSize);
//		         writer.close();
//		         if(listTemp.size()>0){
//		        	 mergePDF();
//			         try {
//			        	 mergeStichedPdfs();
//			         } catch (NGException e) {
//			        	 
//			        }
//		         }
//			
//					
//		}
//		return outputXML;
//	
//}

	private void getJointMinorDetails(){
		String outputXML = "";
		String sQuery1 = "";
		try{
			int start;
			int deadEnd;
			if (reqProcessName.equalsIgnoreCase("AO")){
				 sQuery1 = "SELECT CUST_ID, FINAL_FULL_NAME ,FINAL_EMAIL, FINAL_MOBILE_NO , TO_CHAR(FINAL_DOB,'DDMMYYYY') as FINAL_DOB FROM USR_0_CUST_TXN WHERE WI_NAME = N'"+ WI_NAME + "'";
			}else if (reqProcessName.equalsIgnoreCase("WBG_AO")){
				sQuery1 = "SELECT custid CUST_ID, custfullname FINAL_FULL_NAME, email FINAL_EMAIL, mobile FINAL_MOBILE_NO, to_char(to_date(incdt,'YYYY-MM-DD HH24:MI:SS'),'DDMMYYYY') AS FINAL_DOB from ext_wbg_ao where wi_name= N'"+ WI_NAME + "'"
						+ " Union SELECT cust_id CUST_ID,custfullname FINAL_FULL_NAME, emailid FINAL_EMAIL, telmob FINAL_MOBILE_NO,  to_char(to_date(DOB,'DD/MM/YYYY'),'DDMMYYYY') as FINAL_DOB from usr_0_wbg_ao_aus  "
						+ " where wi_name=N'"+ WI_NAME + "' ";
			}
			logger.info("[getJointMinorDetails]"+"sQuery1 "+sQuery1 );
			String jointMinorOutputXML = ExecuteXML.executeXML(GenerateXml.APSelectWithColumnNames(cabinetName,sQuery1,sessionId));
			XMLParser xp = new XMLParser(jointMinorOutputXML);			
			start = xp.getStartIndex("Records", 0, 0);
			deadEnd = xp.getEndIndex("Records", start, 0);
			noOfFields = xp.getNoOfFields("Record", start, deadEnd);
			int end = 0;
			for (int i = 0; i < noOfFields; ++i) {
				start = xp.getStartIndex("Record", end, 0);
				end = xp.getEndIndex("Record", start, 0);
				jointMinorDetails.add(xp.getValueOf("CUST_ID" , start, end));
				jointMinorDetails.add(xp.getValueOf("FINAL_FULL_NAME" , start, end));
				jointMinorDetails.add(xp.getValueOf("FINAL_EMAIL" , start, end));
				jointMinorDetails.add(xp.getValueOf("FINAL_MOBILE_NO" , start, end));
				jointMinorDetails.add(xp.getValueOf("FINAL_DOB" , start, end));
			}
		} catch (Exception e) {
			insertAuditTrail("getJointMinorDetails Function Exception");
			logger.info("[getJointMinorDetails]"+"getJointMinorDetails "+e );
		}
	}

	private String stichMultipleDocument(String WINAME,int counter) throws MalformedURLException, IOException{
		//10-Jan-2022
		Document document = new Document();
		String DEST = "";
		int filecount =0;
		String countDest = "";
		try{

			DEST = folderLocation+"/"+WINAME+"StichedPdf"+counter+".pdf";
			File length = new File(DEST);
			FileOutputStream fos = new FileOutputStream(DEST);
			PdfWriter writer = PdfWriter.getInstance(document, fos);
//			PdfCopy copy = new PdfSmartCopy(document, fos);

			document.open();
			String imageDir = folderLocation+ documentPath;
			logger.info("[stichMultipleDocument]" + "imageDir :"+imageDir);
			File file = new File(imageDir);
			String[] fileList = file.list();
			Arrays.sort(fileList);

			for (String fileName : fileList) {	
				if(fileName.toUpperCase().contains(".PDF")){
					logger.info("[stichMultipleDocument]" + "Inside PDF ");

					PdfContentByte cb = writer.getDirectContent();
					String in = folderLocation+documentPath+"/"+fileName; 
					logger.info("[stichMultipleDocument]" + "Inside folder document " + in);

					PdfReader reader = new PdfReader(in);
					int pageNumber = reader.getNumberOfPages();

					for (int i = 1; i <= reader.getNumberOfPages(); i++) 
					{
						int rot = reader.getPageRotation(pageNumber);
						document.newPage();
						PdfImportedPage page = writer.getImportedPage(reader, i);
						 Image image = Image.getInstance(page); 
		                 image.setRotationDegrees(-rot);
		                 image.setAbsolutePosition(0, 0); 
		                 document.add(image); 
//						cb.addTemplate(page, 0, 0);
					}
//		            copy.addDocument(reader);

				} else {
					try {
						logger.info("[stichMultipleDocument]" + "Inside Image");
						logger.info("[stichMultipleDocument]" + "inside for fileName :"+fileName);
						logger.info("[stichMultipleDocument]" + "File List :"+fileList);
						RandomAccessFileOrArray tiffFile = new RandomAccessFileOrArray(folderLocation+documentPath+"/"+fileName);
						int numberOfPages = TiffImage.getNumberOfPages(tiffFile);
						logger.info("[stichMultipleDocument]" + "No of Pages Image :"+numberOfPages);
					//	Image image2 = Image.getInstance(folderLocation+documentPath+"/"+fileName);
						Image image2 ;
						//logger.info("[stichMultipleDocument]" + "inside for image2 :"+image2);
						for (int i = 1; i <= numberOfPages; i++) 
						{
							image2 = TiffImage.getTiffImage(tiffFile, i);
							logger.info("[stichMultipleDocument]" + "inside for image2 :"+image2);
							float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
								- document.rightMargin() - indentation) / image2.getWidth()) * 100;
							image2.scalePercent(scaler);
							document.newPage();
							document.add(image2);
						}
					} catch (DocumentException e) {
						insertAuditTrail("stitch PDF Exception");
						deleteFromEventgenTable();
						logger.info("[stichMultipleDocument]" + "DocumentException :: " +e);
					}
				}
			}
			document.close();
			fos.flush();
			fos.close();
			writer.close();
			logger.info("[stichMultipleDocument]" + "stichMultipleDocument File docSize :");
			countDest = Integer.toString(filecount) +"#"+ DEST;
			waterMarkPdf(counter,DEST);
			
		}

		catch (DocumentException e) {
			insertAuditTrail("Stitch PDF Exception");
			deleteFromEventgenTable();
			logger.info("[stichMultipleDocument]" + "DocumentException :: " +e);
		}
		

		return countDest;
	}

//	public void mergePDF(int counter) throws DocumentException, IOException{
//		String path = folderLocation+"/"+WI_NAME+"StichedPdf1"+counter+".pdf";
//		OutputStream out1 = new FileOutputStream(new File(path));	
//		doMerge(listTemp, out1);
//	}
//
//	public void mergeStichedPdfs(int counter) throws DocumentException, IOException, NGException{
//		String path = folderLocation+"/"+WI_NAME+"BusinessDocuments"+counter+".pdf";
//		listTemp1.add(folderLocation+"/"+WI_NAME+"StichedPdf"+counter+".pdf");
//		listTemp1.add(folderLocation+"/"+WI_NAME+"StichedPdf1"+counter+".pdf");
//		//filesToZip.add(new File(path));
//		OutputStream out2 = new FileOutputStream(new File(path));	
//		doMerge(listTemp1, out2);
//		waterMarkPdf(counter,path);
//	}
//
//	public static void doMerge(List<String> list12, OutputStream outputStream) throws DocumentException, IOException
//	{
//		Document document = new Document();
//		PdfWriter writer = PdfWriter.getInstance(document, outputStream);
//		document.open();
//		PdfContentByte cb = writer.getDirectContent();
//		for (String in : list12) 
//		{
//			PdfReader reader = new PdfReader(in);
//			for (int i = 1; i <= reader.getNumberOfPages(); i++) 
//			{
//				document.newPage();
//				PdfImportedPage page = writer.getImportedPage(reader, i);
//				cb.addTemplate(page, 0, 0);
//			}
//		}
//		outputStream.flush();
//		document.close();
//		outputStream.close();		
//	}
	//Added by Ayush
	

	public String createSMSAndEmailInputXML(String sendChannel, String languagePref, String email,String mobileNo, String custName) throws Exception {
		StringBuilder inputXML = new StringBuilder();
		String fetchedRecord2 = fetchFromRejectionMaster();
		XMLParser xp1 = new XMLParser(fetchedRecord2);
		String prodDesc = fetchFromCPSMapping(reqProcessName,"PRODDESC");
		String reason = fetchFromCPSMapping(reqProcessName,"REASON");
//		String newRequestorWIName = renameAOWorkItem();
		String today = getTodayDate();
		String email_fromId = xp1.getValueOf("MAIL_ID");	
		String email_msg = xp1.getValueOf("MAIL_MSG");	
		String sms_templateId = xp1.getValueOf("SMS_ID");	
		String sms_msg = xp1.getValueOf("SMS_MSG");
		String accType = xp1.getValueOf("ACC_CLASS");
		String refNo = getRefNo();
		email_msg = email_msg.replaceAll("PRODUCT", prodDesc).replaceAll("REFERENCE", WI_NAME).replaceAll("PRODUCT", prodDesc).replaceAll("DATE", today).replaceAll("CUSTOMER", custName);
		sms_msg = sms_msg.replaceAll("PRODUCT", prodDesc).replaceAll("REFERENCE", WI_NAME).replaceAll("DATE", today).replaceAll("CUSTOMER", custName);
		String transactionType = "AC";
		String senderID = "WMSBPMENG";
		int flexiFiller1 = 0 ;
		String flexiFiller3 = "Unable to process your request";
		if(accType.equals("Conventional")){
			flexiFiller1 = 2627;
		}
		else if(accType.equals("Islamic")){
			flexiFiller1 = 2628;
		}
       
		logger.info("[fetchFromRejectionMaster]" + " xp1 : " + xp1);
		logger.info("[fetchFromRejectionMaster]" + " mail_Id : " + email_fromId );
		logger.info("[fetchFromRejectionMaster]" + " mail_msg : " + email_msg);
		logger.info("[fetchFromRejectionMaster]" + " sms_id: " + sms_templateId);
		logger.info("[fetchFromRejectionMaster]" + " sms_msg : " + sms_msg );
		logger.info("[PropertyFile]" + " cps_fromID: " + rejectionFromID );
		logger.info("[argumentPassing]" + "	mobile : " + mobileNo);
		logger.info("[argumentPassing]" + " email: " + email);
		logger.info("[fetchFromCPSMapping]" + " prodDesc: " + prodDesc);
		logger.info("[fetchFromCPSMapping]" + " reason: " + reason);
		logger.info("[getRefNo]" + " Reference No : " + refNo);
		logger.info("[fetchFromRejectionMaster]" + " Acc Class : " + accType);
		logger.info("[argumentPassing]" + " Customer Name: " + custName);
//		logger.info("[renameAOWorkItem]" + " Workitem No : " + newRequestorWIName );


		try {
			logger.info("[createSMSAndEmailInputXML]" + "SendSMSEmail refNo ===> "+refNo);
			
		if(sendChannel == "B"){	
			inputXML.append("<?xml version=\"1.0\"?>").append("\n")
			.append("<APWebService_Input>").append("\n")
			.append("<Option>WebService</Option>").append("\n")
			.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
			.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
			.append("<WINAME>" + WI_NAME + "</WINAME>").append("\n")
			.append("<Calltype>CBG</Calltype>").append("\n")
			.append("<CBGCallType>SEND_SMS_EMAIL</CBGCallType>").append("\n")
			.append("<REF_NO>"+refNo+"</REF_NO>")
			.append("<OLDREF_NO>"+refNo+"</OLDREF_NO>")
			.append("<senderID>"+senderID+"</senderID>").append("\n")
			.append("<username></username>").append("\n")
			.append("<CUST_ID></CUST_ID>").append("\n")
			.append("<Account_Number></Account_Number>").append("\n")
			.append("<EMAIL_TEMP_ID>"+email_fromId+"</EMAIL_TEMP_ID>").append("\n")
			.append("<EMAIL_TEXT>"+email_msg+"</EMAIL_TEXT>").append("\n")
			.append("<EMAIL_ADDRESS>"+email+"</EMAIL_ADDRESS>").append("\n")
			.append("<FlexiFiller1>"+flexiFiller1+"</FlexiFiller1>").append("\n")
//			.append("<FlexiFiller2>"+rejectionFromID+"</FlexiFiller2>").append("\n")
			.append("<FlexiFiller3>"+flexiFiller3+"</FlexiFiller3>").append("\n")
			.append("<FlexiFiller4></FlexiFiller4>").append("\n")
			.append("<FlexiFiller5></FlexiFiller5>").append("\n")
			.append("<LanguagePref>"+languagePref+"</LanguagePref>").append("\n")
			.append("<transactionType>"+transactionType+"</transactionType>").append("\n")
			.append("<sendAsChannel>"+sendChannel+"</sendAsChannel>").append("\n")
			.append("<TEMPLATE_ID>"+sms_templateId+"</TEMPLATE_ID>").append("\n")
			.append("<MSG>"+sms_msg+"</MSG>").append("\n")
			.append("<Mobile>"+mobileNo+"</Mobile>").append("\n")
			.append("</APWebService_Input>");
		}else{	
			inputXML.append("<?xml version=\"1.0\"?>").append("\n")
			.append("<APWebService_Input>").append("\n")
			.append("<Option>WebService</Option>").append("\n")
			.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
			.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
			.append("<WINAME>" + WI_NAME + "</WINAME>").append("\n")
			.append("<Calltype>CBG</Calltype>").append("\n")
			.append("<CBGCallType>SEND_SMS_EMAIL</CBGCallType>").append("\n")
			.append("<REF_NO>"+refNo+"</REF_NO>")
			.append("<OLDREF_NO>"+refNo+"</OLDREF_NO>")
			.append("<senderID>"+senderID+"</senderID>").append("\n")
			.append("<username></username>").append("\n")
			.append("<CUST_ID></CUST_ID>").append("\n")
			.append("<Account_Number></Account_Number>").append("\n")
//			.append("<EMAIL_TEMP_ID>"+email_fromId+"</EMAIL_TEMP_ID>").append("\n")
//			.append("<EMAIL_TEXT>"+email_msg+"</EMAIL_TEXT>").append("\n")
//			.append("<EMAIL_ADDRESS>"+email+"</EMAIL_ADDRESS>").append("\n")
			.append("<FlexiFiller1>"+flexiFiller1+"</FlexiFiller1>").append("\n")
//			.append("<FlexiFiller2>"+rejectionFromID+"</FlexiFiller2>").append("\n")
			.append("<FlexiFiller3>"+flexiFiller3+"</FlexiFiller3>").append("\n")
			.append("<FlexiFiller4></FlexiFiller4>").append("\n")
			.append("<FlexiFiller5></FlexiFiller5>").append("\n")
			.append("<LanguagePref>"+languagePref+"</LanguagePref>").append("\n")
			.append("<transactionType>"+transactionType+"</transactionType>").append("\n")
			.append("<sendAsChannel>"+sendChannel+"</sendAsChannel>").append("\n")
			.append("<TEMPLATE_ID>"+sms_templateId+"</TEMPLATE_ID>").append("\n")
			.append("<MSG>"+sms_msg+"</MSG>").append("\n")
			.append("<Mobile>"+mobileNo+"</Mobile>").append("\n")
			.append("</APWebService_Input>");
			}
			logger.info("[createSMSAndEmailInputXML]" + "SendSMSEmail inputXML ===> "+inputXML.toString());
		} catch (Exception e) {
			insertAuditTrail("Reject INPUT XML Exception");
			//deleteFromEventgenTable();
			logger.info("[createSMSAndEmailInputXML]" + "Exception ::" + e);

		}
		return inputXML.toString();   
	}

	private void initializeSocket() {
		try{
			//socketIP ="127.0.0.1";
			logger.info("[initializeSocket]" + "outputXml="+cabinetName);
			logger.info("[initializeSocket]" + "outputXml="+sessionId);
			String sQuery1 = "SELECT (SELECT VALUE FROM BPM_SERVICE_CONFIG WHERE KEY = 'SOCKETIP') IP,"
					+ "(SELECT VALUE FROM BPM_SERVICE_CONFIG WHERE KEY = 'SOCKETPORT') PORT FROM DUAL";
			String outputXml = ExecuteXML.executeXML(
					GenerateXml.APSelectWithColumnNames(cabinetName,sQuery1,sessionId));
			logger.info("[initializeSocket]" + "outputXml="+outputXml);
			XMLParser xmlDataParser1 = new XMLParser(outputXml);
			int mainCode = Integer.parseInt(xmlDataParser1.getValueOf("MainCode"));
			if (mainCode == 0) {
				String ip = xmlDataParser1.getValueOf("IP");
				String port = xmlDataParser1.getValueOf("PORT");
				socket = ConnectSocket.getReference(ip, Integer.parseInt(port));
			}
		}catch(Exception e){
			insertAuditTrail("initializeSocket Exception");
			logger.error("[initializeSocket]" + "Exception in initializeSocket",e);
		}
	}
	
	private String uploadDocument(String ImagePath){
			String outputXML ="";
			File imageFile = new File(ImagePath);
			long lFileLength = imageFile.length();
			try {        	
				
				JPISIsIndex strISINDEX = new JPISIsIndex();
				JPDBRecoverDocData JPISDEC = new JPDBRecoverDocData();
				CPISDocumentTxn.AddDocument_MT(null,JTSIP, Short.parseShort(JTSPort),EDBMSCabinet,
						Short.parseShort(volId_DD), imageFile.getPath(), JPISDEC, "", strISINDEX);
				String docIsIndx = strISINDEX.m_nDocIndex + "#" + strISINDEX.m_sVolumeId + "#";
				outputXML = addDocumentCall(ImagePath, lFileLength, docIsIndx);
				logger.info("Document uploaded successfully--");
			}catch (JPISException ex) {
				insertAuditTrail("Document uploaded  Exception");
				deleteFromEventgenTable();
				logger.error("[uploadDocument]" + "Exception in uploadDocument",ex);
			} 
			catch (Throwable exp) {
				insertAuditTrail("Document uploaded  Exception");
				logger.error("[uploadDocument]" + "Exception in uploadDocument",exp);

			}finally{
				if(imageFile!=null){
					imageFile=null;
				}			
			}
			return outputXML;
		}

	private String addDocumentCall(String strImagePath, Long docLength, String docIsIndx) {
			logger.info("strImagePath--" + strImagePath);
			logger.info("docLength--" + docLength);
			String addDocOutputXml ="";
			try {

				String addDocInputXml = "<?xml version=\"1.0\"?><NGOAddDocument_Input>"
						+ "<BypassHookTag></BypassHookTag>"
						+ "<Option>NGOAddDocument</Option>"
						+ "<CabinetName>" + cabinetName + "</CabinetName>"
						+ "<UserDBId>" + sessionId + "</UserDBId>"
						+ "<GroupIndex>0</GroupIndex>"
						+ "<Document>"
						+ "<ParentFolderIndex>" + folderIndex + "</ParentFolderIndex>"
						+ "<NoOfPages>1</NoOfPages>"
						+ "<AccessType>I</AccessType>"
						+ "<DocumentName>"+ zipName +"</DocumentName>"
						+ "<CreationDateTime></CreationDateTime>"
						+ "<DocumentType>N</DocumentType>"
						+ "<DocumentSize>" + docLength + "</DocumentSize>"
						+ "<CreatedByAppName>zip</CreatedByAppName>"
						+ "<ISIndex>" + docIsIndx + "</ISIndex>"
						+ "<ODMADocumentIndex></ODMADocumentIndex>"
						+ "<Comment>"+WI_NAME+"</Comment>"
						+ "<EnableLog>Y</EnableLog>"
						+ "<FTSFlag>PP</FTSFlag>"
						+ "<DataDefinition></DataDefinition>"
						+ "<Keywords></Keywords></Document>"
						+ "</NGOAddDocument_Input>";

				 addDocOutputXml =  ExecuteXML.executeXML(addDocInputXml);
				logger.info("Document added successfully--"+addDocOutputXml);

			} catch (Exception exp) {
				insertAuditTrail("addDocumentCall  Exception");
				logger.error("[addDocumentCall]" + "Exception in addDocumentCall",exp);
			}
			return addDocOutputXml;
		}
	
	public String fetchFromCPSMaster(String reqProcessName)
	{
		String sQuery= "";
		String outputXML="";
		
		try
		{
			 String custSegment = getSegment();
			 String reqSourcingChannel = fetchFromCPSMapping(reqProcessName,"SOURCING_CHANNEL");
			 String accType = fetchFromCPSMapping(reqProcessName,"ACC_CLASS");
			 if(accType.equals("I")){
				 accType = "Islamic";
			}else if(accType.equals("C")){
					accType = "Conventional";
			}
			 sQuery = "select CUST_CATEGORY ,CUST_SEGMENT, CUST_CLASS, SMS_TEMPLATE_ID, SMS_TEMPLATE,SUBJECT,"
					+ "EMAIL_TEMPLATE_ID, EMAIL_TEMPLATE, FROM_ID, CHANNEL, COMMUNICATION_TYPE, COMMUNICATION_DESC, MAIL_REQUIRED "
					+ " from BPM_CPS_COMMUNICATION_MASTER  where PROCESS = '"+reqProcessName+"' and CHANNEL = '"+reqSourcingChannel+"'"
					+ " and ACC_CLASS = '"+accType+"' and CUST_SEGMENT = '"+custSegment+"'";

			logger.info("[fetchFromCPSMaster]" + " sQuery : " + sQuery);
			outputXML = ExecuteXML.executeXML(GenerateXml.APSelectWithColumnNames(cabinetName,sQuery,sessionId));
			
		}catch (Exception localException5){
			insertAuditTrail("fetch from master table Exception");
			//deleteFromEventgenTable();
			logger.error("[fetchFromCPSMaster]" + " Exception : " +localException5);
		} 
		return outputXML;

	}

	private String getCurrentDate()	
	{
		String today = null;
		try {
			Date date = Calendar.getInstance().getTime();
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			today = formatter.format(date);
			logger.info("[getCurrentDate]"  + today);
		} catch (Throwable exp) {
			logger.info("[getCurrentDate]" + " Exeception : " + exp);
		}

		return today;
	} 
	
	private void readCPSConfig() throws IOException {
		
		try {
			
			Properties p = null;
			p = new Properties();
			p.load(new FileInputStream("cpsconfig.properties"));
			volumeID = p.getProperty("volumeID");
			String config_processdefid = p.getProperty("processdefid");
			initActivityName = p.getProperty("initActivityName");
			JTSIP = p.getProperty("JTSIP");
			JTSPort = p.getProperty("JTSPort");
			appName = p.getProperty("appName");
			folderLocation = p.getProperty("folderPath");
			folderLocation = folderLocation.replaceAll("WINAME",WI_NAME);
			documentPath = p.getProperty("documentPath");
			zipName = p.getProperty("zipName");
			pdfName = p.getProperty("pdfName");
			EDBMSCabinet = p.getProperty("EDBMSCabinet");
			CPSProcessDefId = Integer.parseInt(p.getProperty("CPSProcessDefId"));
			CPSProcessName = p.getProperty("CPSProcessName");	
			reInsertInterval = p.getProperty("reInsertInterval");
			waterMarkText = p.getProperty("waterMarkText");
			waterMarkCount = p.getProperty("waterMarkCount");
			rejectionFromID = p.getProperty("CPSRejectionMailId");
			indentation = Integer.parseInt(p.getProperty("indentationPdf"));
			
			logger.info("[readCPSConfig]" +" config_volumeID : "+ volumeID);
			logger.info("[readCPSConfig]" +" config_processdefid : "+config_processdefid);
			logger.info("[readCPSConfig]" +" config_initActivityName : "+initActivityName);
			logger.info("[readCPSConfig]" +" config_JTSIP : "+JTSIP);
			logger.info("[readCPSConfig]" +" config_JTSPort : "+JTSPort);
			logger.info("[readCPSConfig]" +" config_appName : "+appName);
			logger.info("[readCPSConfig]" +" config_folderLocation : "+folderLocation);
			logger.info("[readCPSConfig]" +" config_zipName : "+zipName);
			logger.info("[readCPSConfig]" +" config_pdfName : "+pdfName);
			logger.info("[readCPSConfig]" +" config_documentPath : "+ documentPath);
			logger.info("[readCPSConfig]" +" config_EDBMSCabinet : "+ EDBMSCabinet);
			logger.info("[readCPSConfig]" +" CPSProcessDefId : "+CPSProcessDefId);
			logger.info("[readCPSConfig]" +" CPSProcessName : "+ CPSProcessName);
			logger.info("[readCPSConfig]" +" reInsertInterval : "+ reInsertInterval);
			logger.info("[readCPSConfig]" +" waterMarkText : "+ waterMarkText);
			logger.info("[readCPSConfig]" +" waterMarkCount : "+ waterMarkCount);
			logger.info("[readCPSConfig]" +" CPSRejectionMailId : "+ rejectionFromID);
			logger.info("[readCPSConfig]" +" Indentation : "+ indentation);
		} catch (IOException exp) {
			insertAuditTrail("readCPSConfig Exception");
			logger.info("[readCPSConfig]" +" IOException : "+ exp);
			
			
		}
	}
		
	public String fetchFromCPSMapping(String reqProcessName, String colDesc) {
		String sQuery= "";
		String outputXML="";
		String data  = "";
		try
		{
			ArrayList<String> values = new ArrayList<>();
			values = CPSDefaultMap.get(colDesc+"#"+reqProcessName);
			logger.info("[fetchFromCPSMapping]" + " Given reqProcessName : " + reqProcessName + " & ColDescvalues : " + colDesc);
			logger.info("[fetchFromCPSMapping]" + " value : " + values);
			//String[] values = value.split("#");  //tablename#column_name#wi_name_column
			String orderByColumn = "";
			if (reqProcessName.equalsIgnoreCase("AO")) {
				orderByColumn = "CREATE_DAT";
			} else if(reqProcessName.equalsIgnoreCase("WBG_AO")){
				orderByColumn = "dec_date_time";
			} else if(reqProcessName.equalsIgnoreCase("CBG") || reqProcessName.equalsIgnoreCase("SME")){
				orderByColumn = "date_time";
			}
			if(colDesc.contains("REASON") || colDesc.contains("WI_DECISION")) {
				sQuery = "select "+values.get(2)+" from (select "+values.get(2)+" from "+values.get(0)+" where "+values.get(1)+" = '"+WI_NAME+"' order by "+orderByColumn+" desc ) where rownum =1";	
			} else {
				sQuery = "select "+values.get(2)+" from "+values.get(0)+" where "+values.get(1)+" = '"+WI_NAME+"'";
			}
			if(colDesc.contains("PASSWORD") && values.get(2).contains("DOB")) {
				sQuery = "select TO_CHAR("+values.get(2)+",'DDMMYYYY') as "+values.get(2)+" from "+values.get(0)+" where "+values.get(1)+" = '"+WI_NAME+"' ";	
			}
			logger.info("[fetchFromCPSMapping]" + " sQuery : " + sQuery);
			outputXML = ExecuteXML.executeXML(GenerateXml.APSelectWithColumnNames(cabinetName,sQuery,sessionId));
			logger.info("[fetchFromCPSMapping]" + " outputXML : " + outputXML);
			XMLParser sxp = new XMLParser(outputXML);
			data = sxp.getValueOf(values.get(2));
			logger.info("[fetchFromCPSMapping]" + " outputXML : " + outputXML);

		}catch (Exception localException5){
			insertAuditTrail("CPS MAPPING Exception");
			deleteFromEventgenTable();
			logger.error("[fetchFromCPSMapping]" + " Exception : " + localException5);
		} 
		return data;
	}
	
	public String fetchFromRejectionMaster()
	{
		String sQuery= "";
		String outputXML="";
		String accType = fetchFromCPSMapping(reqProcessName,"ACC_CLASS");
		if(accType.equals("I")){
			accType = "Islamic";
		}
		else if(accType.equals("C")){
			accType = "Conventional";
		}
		String Reason = fetchFromCPSMapping(reqProcessName,"REASON");
		logger.info("[fetchFromRejectionMaster]" + " REJECTION REASON : " + Reason);
		try
		{
			sQuery = "select sms_msg,sms_id,mail_msg,mail_id,acc_class "
					+ " from CPS_Rejection_Reason_Master  where PROCESS_NAME = '"+reqProcessName+"' and ACC_CLASS = '"+accType+"' and REJECTION_REASON = '"+Reason+"'";

			logger.info("[fetchFromRejectionMaster]" + " cabinetName : " + cabinetName);

			outputXML = ExecuteXML.executeXML(GenerateXml.APSelectWithColumnNames(cabinetName,sQuery,sessionId));
			
			logger.info("[fetchFromRejectionMaster]" + " sQuery : " + sQuery);
			logger.info("[fetchFromRejectionMaster]" + " outputXML : " + outputXML);
		
	}catch (Exception localException5){
		    insertAuditTrail("Rejection Master Exception");
			//deleteFromEventgenTable();
			logger.error("[fetchFromRejectionMaster]" + " Exception : " +localException5);
		} 
		return outputXML;

	}
	
	public void waterMarkPdf(int counter,String path) throws IOException, DocumentException {
		//String path = "/appl/WMS/CPS/"+WI_NAME+"/"+WI_NAME+"StichedPdf"+counter+".pdf";
        PdfReader reader = new PdfReader(path);
		pdfName = pdfName.replaceAll("CID",cid);
		String destPath = folderLocation+"/"+pdfName+".pdf";
		filesToZip.add(new File(destPath));

        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(destPath));
        Font FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, new GrayColor(0.1f));
        Phrase p = new Phrase(waterMarkText, FONT);
        int count = Integer.parseInt(waterMarkCount); 
        PdfContentByte over;
        Rectangle pagesize;
        float x, y,horizontalgap,verticalGap;
        noOfPages = reader.getNumberOfPages();
        for (int i = 1; i <= noOfPages; i++) {
        	pagesize = reader.getPageSizeWithRotation(i);
        	verticalGap = (pagesize.getTop() + pagesize.getBottom())/count;
        	horizontalgap = (pagesize.getLeft() + pagesize.getRight())/count;
        	over = stamper.getOverContent(i);
            over.saveState();
            PdfGState state = new PdfGState();
            state.setFillOpacity(0.2f);
            over.setGState(state);
        	for(int j = 1;j<count;j++) {
        		for(int k = 1;k<count;k++) {
        			x = pagesize.getLeft() + horizontalgap*k;
                    y = pagesize.getBottom() + verticalGap*j;
                    ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, x, y, 45);
        		}
            
        	}
        	   over.restoreState();
        }
        stamper.close();
        reader.close();
        try {
        	String outputXml = uploadDocumentToParentWI(destPath);
        	XMLParser xp  = new XMLParser(outputXml);
        	int mainCode = Integer.parseInt(xp.getValueOf("Status"));
        	logger.info("uploadToParentWIDocument]" + " outputXml : " + outputXml);
        	logger.info("uploadToParentWIDocument]" + " mainCode : " + mainCode);
        }catch (Throwable exp) {
			insertAuditTrail("WaterMark PDF Exception");
			//deleteFromEventgenTable();
        	logger.info("uploadToParentWIDocument]" + " Exeception : " + exp);
        }
    }
	
	private String getTodayDate()	
	{
		String today = null;
		try {
			Date date = Calendar.getInstance().getTime();
			DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			today = formatter.format(date);
			logger.info("[getCurrentDate]"  + today);
		} catch (Throwable exp) {
			logger.info("[getCurrentDate]" + " Exeception : " + exp);
		}
		return today;
	}
	
	public void updateWorkstep(){
		String updateQuery = "";
		int mainCode = -1;
		String currWS = "Exit";
		try{
		updateQuery = GenerateXml.APUpdate("EXT_CPS", "CURR_WS", 
				"'"+currWS+"'", "WI_NAME = N'"+CPSWorkItemNumber+"'", 
				sessionId , cabinetName) ;
		logger.info("[updateWorkstep]"  + " Update Query " +updateQuery);
		XMLParser xmlDataParser1 = new XMLParser(updateQuery);
		mainCode = Integer.parseInt(xmlDataParser1.getValueOf("MainCode"));
		logger.info("[updateWorkstep]"  + " Main Code " +mainCode);
		}catch(Exception e) {
			insertAuditTrail("updateWorkstep Exception");
			logger.info("[updateWorkstep]"  + " Exception in Updation " +e);
		}
	}
	
	public void insertInAudit(String progressDetail){
		String insertQuery = "";
		String outputXML ="";
		try {
			if(progressDetail.contains("Success")){
				finalOuptputXML  = "<returnCode>0</returnCode><errorDescription>Success</errorDescription>";
			} else {
				finalOuptputXML  = "<returnCode>-1</returnCode><errorDescription>"+progressDetail+"</errorDescription>";
			}
			insertQuery = GenerateXml.apInsertInputXml(cabinetName, sessionId, "BPM_CPS_DECISION_HISTORY", 
					"WI_NAME,PROGRESS_DETAILS,DATETIME", "'"+CPSWorkItemNumber+"','"+progressDetail+"',SYSDATE");
			logger.info("[insertInAudit]"  + " Insert Query " +insertQuery);
			outputXML = ExecuteXML.executeXML(insertQuery.toString());
			logger.info("[insertInAudit]"  + " Output XML " +outputXML);
		} catch (Exception e) {
			insertAuditTrail("insertInAudit Exception");
			logger.info("[insertInAudit]"  + " Exception in Insertion " +e);
		}
		
	}
	
	public void insertAuditTrail(String progressDetail){
		String insertQuery = "";
		String outputXML ="";
		try {
			insertQuery = GenerateXml.apInsertInputXml(cabinetName, sessionId, "BPM_CPS_AUDIT_TRAIL", 
					"EVENT_TYPE,WI_NAME,COUNT_OF_PROCESSED_WI,INSERTED_DATETIME", "'"+progressDetail+"','"+WI_NAME+"','1',SYSDATE");
			logger.info("[insertInAudit]"  + " Insert Query " +insertQuery);
			outputXML = ExecuteXML.executeXML(insertQuery.toString());
			logger.info("[insertInAudit]"  + " Output XML " +outputXML);
		} catch (Exception e) {
			logger.info("[insertInAudit]"  + " Exception in Insertion " +e);
		}
		
	}
	
	public void downloadDocuments(String fetchedDocuments) throws Exception{
	logger.info("[CPSPrepareDocument]" +" CPSAddDocument fetchedDocuments "+fetchedDocuments);
	XMLParser parser = new XMLParser(fetchedDocuments);
	int fisrtmainCode = Integer.parseInt(parser.getValueOf("MainCode"));
	if (fisrtmainCode == 0) {

		int start = parser.getStartIndex("Records", 0, 0);
		int deadEnd = parser.getEndIndex("Records", start, 0);
		int noOfFields = parser.getNoOfFields("Record", start, deadEnd);

		String folderPath =  folderLocation + "/" +"Documents";
		logger.info("[CPSPrepareDocument]" +"folderPath : "+ folderPath);
		sortedDocs = new HashMap<String,ArrayList<String>>();
		File file = new File(folderPath);
		if (!file.exists()) {
			Boolean createFile = file.mkdirs();
			logger.info( "[CPSPrepareDocument]" +" Folder created : "+ createFile);
			for (int i = 0; i < noOfFields; ++i) { 
				String Record = parser.getNextValueOf("Record");
				XMLParser xp2 = new XMLParser(Record);
				String IMAGEINDEX = xp2.getValueOf("IMAGEINDEX");
				String appname = xp2.getValueOf("APPNAME");
				String volumeID = xp2.getValueOf("VOLUMEID");
				String name = xp2.getValueOf("NAME");
				String commnt = xp2.getValueOf("COMMNT");
				String doc_desc = xp2.getValueOf("DOC_DESCRIPTION");
				volId_DD = volumeID;
				String docProgress = " "+doc_desc+" Document Downloaded Successfully";

				String docName  = name.trim() +"#"+ commnt.trim() ; // Adding key as name # commnt and values will be imageindex # volumid # appname
				JPDBString oSiteName = new JPDBString("adcbedmsuatsite");

				try {
					CPISDocumentTxn.GetDocInFile_MT(null,JTSIP,Short.parseShort(JTSPort),
							EDBMSCabinet,Short.parseShort("1"),Short.parseShort(volumeID),
							Integer.parseInt(IMAGEINDEX),
							null,folderLocation+"/Documents/"+(i)+docName
							+"."+appname,oSiteName);
				}catch (JPISException e) {
					insertAuditTrail("Document Download Exception");
					docProgress =  " "+doc_desc+" Document Download Exception Occured.";
					logger.info("[CPSPrepareDocument]" +" Exception "+e);
				}
				insertInAudit(docProgress);							
			}																
			
		}else{
			logger.info("[CPSPrepareDocument]" +"New Workitem Directory Already Exist : ");
		}
	} else{
		logger.info("[CPSPrepareDocument]" +"firstmainCode is not Zero(0)");
	}
  }
	
	

	public boolean isReqWiForRetry(){
		logger.info("[isReqWiForRetry]" +"isReqWiForRetry Inside");
		String query = "select COUNT(requestor_wi_name) as COUNT from ext_cps where requestor_wi_name = '"+WI_NAME+"'"
				+"and curr_ws = 'Prepared Document'";
		try{
			String outputXML = ExecuteXML.executeXML(GenerateXml.APSelectWithColumnNames(cabinetName,query,sessionId));
			XMLParser xp = new XMLParser(outputXML);
			int reqs_count = Integer.parseInt(xp.getValueOf("COUNT"));
			if(reqs_count ==  0){
				return false;
			}
		}catch(Exception e){
			insertAuditTrail("isReqWiForRetry Function Exception");
			logger.error("[isReqWiForRetry]" + e);	
		}
		return true;
	}
	
	public String getCPSWiDetails(){
		String query = "select WI_NAME,ITEMINDEX FROM (select WI_NAME,ITEMINDEX from ext_cps where "
				+ "requestor_wi_name = '"+WI_NAME+"' and curr_ws = 'Prepared Document' order by req_date_time "
						+ "desc ) WHERE ROWNUM = 1";
		String outputXML = "";
		try {
			 outputXML = ExecuteXML.executeXML(GenerateXml.APSelectWithColumnNames(cabinetName,query,sessionId));
			 XMLParser xp = new XMLParser(outputXML);
			 CPSWorkItemNumber = xp.getValueOf("WI_NAME");
			 folderIndex = xp.getValueOf("ITEMINDEX");	
		} catch (Exception e) {
			insertAuditTrail("getCPSWiDetails Function Exception");
			logger.error("[getCPSWiDetails]" + e);	

		}
		return outputXML;
	}
	
	public void deleteFromAuditTable(){
	String tableName = "BPM_CPS_DECISION_HISTORY";
	String whereClause =  "WI_NAME='" + CPSWorkItemNumber + "'";
	try {
		String soutputXML = GenerateXml.APDelete(cabinetName,tableName,whereClause,sessionId);
		logger.info("[deleteFromAuditTable]" + "deleteFromAuditTable"+soutputXML);

	} catch (Exception e) {
		insertAuditTrail("deleteFromAuditTable Exception");
		logger.info("[deleteFromAuditTable]" +"FinalDecisonException e:"+e);
	  }
	}
	
	
	private String uploadDocumentToParentWI(String destinationPath){
		String outputXML ="";
		File imageFile = new File(destinationPath);
		long docLength = imageFile.length();
		try {        	
			JPISIsIndex strISINDEX = new JPISIsIndex();
			JPDBRecoverDocData JPISDEC = new JPDBRecoverDocData();
			CPISDocumentTxn.AddDocument_MT(null,JTSIP, Short.parseShort(JTSPort),EDBMSCabinet,
					Short.parseShort(volId_DD), imageFile.getPath(), JPISDEC, "", strISINDEX);
			String docIndx = strISINDEX.m_nDocIndex + "#" + strISINDEX.m_sVolumeId + "#";
			outputXML = addDocumentToParentWI(destinationPath, docLength, docIndx);
			logger.info("Document uploaded to Parent WI successfully--");
		}catch (JPISException ex) {
			insertAuditTrail("Document Upload Parent WI Exception");
			logger.error("[uploadToParentWIDocument]" + "Exception in uploadParentDocument",ex);
		} 
		catch (Throwable exp) {
			logger.error("[uploadToParentWIDocument]" + "Exception in uploadParentDocument",exp);

		}finally{
			if(imageFile!=null){
				imageFile=null;
			}			
		}
		return outputXML;
	}
	
	private String addDocumentToParentWI(String documentPath, Long documentLength, String documentIndex) {
		logger.info("strImagePath--" + documentPath);
		logger.info("docLength--" + documentLength);
		String addDocOutputXml ="";
		String folderIndx = fetchFromCPSMapping(reqProcessName,"ITEMINDEX");
		try {

			String addDocInputXml = "<?xml version=\"1.0\"?><NGOAddDocument_Input>"
					+ "<BypassHookTag></BypassHookTag>"
					+ "<Option>NGOAddDocument</Option>"
					+ "<CabinetName>" + cabinetName + "</CabinetName>"
					+ "<UserDBId>" + sessionId + "</UserDBId>"
					+ "<GroupIndex>0</GroupIndex>"
					+ "<Document>"
					+ "<ParentFolderIndex>" + folderIndx + "</ParentFolderIndex>"
					+ "<NoOfPages>"+ noOfPages +"</NoOfPages>"
					+ "<AccessType>I</AccessType>"
					+ "<DocumentName>"+pdfName+"</DocumentName>"
					+ "<CreationDateTime></CreationDateTime>"
					+ "<DocumentType>N</DocumentType>"
					+ "<DocumentSize>" + documentLength + "</DocumentSize>"
					+ "<CreatedByAppName>pdf</CreatedByAppName>"
					+ "<ISIndex>" + documentIndex + "</ISIndex>"
					+ "<ODMADocumentIndex></ODMADocumentIndex>"
					+ "<Comment>"+WI_NAME+"</Comment>"
					+ "<EnableLog>Y</EnableLog>"
					+ "<FTSFlag>PP</FTSFlag>"
					+ "<DataDefinition></DataDefinition>"
					+ "<Keywords></Keywords></Document>"
					+ "</NGOAddDocument_Input>";

			 addDocOutputXml =  ExecuteXML.executeXML(addDocInputXml);
			logger.info("Document added to Parent WI successfully--"+addDocOutputXml);
			logger.info("Document Length "+documentLength);
			logger.info("Document Index "+documentIndex);
			logger.info("Document Pages "+noOfPages);
			logger.info("Document Folder Index "+ folderIndx);
			logger.info("Document Name "+ pdfName);

		} catch (Exception exp) {
			insertAuditTrail("addToParentDocumentCall Exception");
			logger.error("[addToParentDocumentCall]" + "Exception in addToParentDocumentCall",exp);
		}
		return addDocOutputXml;
	}
	
	public void updateRetryCalls() throws NGException{
		try{
		String updateQuery = "";
		int mainCode = -1;
		String status = "DONE";
		updateQuery = GenerateXml.APUpdate("CPS_RETRY_CALLS", "STATUS", 
				"'"+status+"'", "CPS_WI_NAME = N'"+CPSWorkItemNumber+"'", 
				sessionId,cabinetName);
		logger.info("[updateRetryCalls]" + " Update Query " +updateQuery);
		XMLParser xmlDataParser1 = new XMLParser(updateQuery);
		mainCode = Integer.parseInt(xmlDataParser1.getValueOf("MainCode"));
		logger.info("[updateRetryCalls]" + " Main Code " +mainCode);
		}catch (Exception e) {
			insertAuditTrail("updateRetryCalls Exception");
			logger.info("Exception Found"+e);
		}
	}
	public Boolean identifyprivateclients(){
		try {
			String custSegement = "";
			String reqCategory = fetchFromCPSMapping(reqProcessName,"CUSTCAT");
			if(reqProcessName.equalsIgnoreCase("AO")){
				logger.info("[CPSPrepareDocument]" + " identifyprivateclients "+reqProcessName);
				if (reqCategory.equalsIgnoreCase("New Account With Category Change")){
					custSegement = fetchFromCPSMapping(reqProcessName,"CUST_SEG_NEW");
				}else {
					custSegement = fetchFromCPSMapping(reqProcessName,"CUST_SEGMENT");
				}	
				logger.info("[CPSPrepareDocument]" + " Customer Segment "+custSegement);
				if(custSegement.equalsIgnoreCase("Private Clients")){
					logger.info("[CPSPrepareDocument]" + "Return False");
					return false;                                     //Email,SMS Function would not run
				  }
			  }
		   }catch (Exception e) {
			insertAuditTrail("identifyprivateclients Exception");
			logger.info("Exception Found"+e);				
		}
		return true;
	}
	
	public Boolean checkReqCategory(){	
		try {
			String reqCategory = fetchFromCPSMapping(reqProcessName,"CUSTCAT");
			logger.info("[CPSPrepareDocument]" + " checkReqCategory "+reqProcessName);
			logger.info("[CPSPrepareDocument]" + " Request Type "+reqCategory);
			if(reqProcessName.equalsIgnoreCase("AO")){
				if(reqCategory.equalsIgnoreCase("Family Banking") || reqCategory.equalsIgnoreCase("Category Change Only")){
					return false;                                     						//CPS WI will not create
				  }
			} else if(reqProcessName.equalsIgnoreCase("WBG_AO")){
				if(!(reqCategory.equalsIgnoreCase("Commercial Premium") || reqCategory.equalsIgnoreCase("Commercial Ultra") 
						|| reqCategory.equalsIgnoreCase("Commercial") || reqCategory.equalsIgnoreCase("Self Employed"))){
					return false;                                     
					}
		    } else if(reqProcessName.equalsIgnoreCase("SME")){       //Change FOR SME
				if(!(reqCategory.equalsIgnoreCase("Commercial Premium") || reqCategory.equalsIgnoreCase("Commercial Ultra") 
						|| reqCategory.equalsIgnoreCase("Commercial"))){
					return false;                                     
					}
				 }
		   }catch (Exception e) {
			insertAuditTrail("checkReqCategory Exception");
			logger.info("Exception Found"+e);				
		}
		return true;
	}
	
	public void deleteFromEventgenTable(){
		String tableName = "bpm_eventgen_txn_data";
		String whereClause =  "WI_NAME='" + WI_NAME + "'";
		try {
			String sQuery1 = "SELECT COUNT(WI_NAME) as COUNT FROM BPM_EVENTGEN_TXN_DATA WHERE WI_NAME = N'"+ WI_NAME + "' AND SOURCING_CHANNEL = 'CPS'";
			String selectOutputXML = ExecuteXML.executeXML(GenerateXml.APSelectWithColumnNames(cabinetName,sQuery1,sessionId));
			XMLParser xp = new XMLParser(selectOutputXML);
			int wiCount = Integer.parseInt(xp.getValueOf("COUNT"));
			logger.info("[deleteFromEventgenTable]" +"CPS WI Count in BPM_EVENTGEN_TXN_DATA : "+ wiCount);
			if(wiCount == 1){
				String soutputXML = GenerateXml.APDelete(cabinetName,tableName,whereClause,sessionId);
				logger.info("[deleteFromEventgenTable]" + "deleteFromEventgenTable output "+soutputXML);
			}
		} catch (Exception e) {
			insertAuditTrail("deleteFromEventgenTable Exception");
			logger.info("[deleteFromEventgenTable]" +"Delete Exception e:"+e);
		}	
	}
	
	public String getSegment() throws Exception{
		String custSegment ="";
		try {
			if(reqProcessName.equalsIgnoreCase("AO") || reqProcessName.equalsIgnoreCase("CBG")){
			custSegment = fetchFromCPSMapping(reqProcessName,"CUST_SEGMENT");
			logger.info("[getSegment]" +"Cust Segment from Master: "+custSegment);
			}
			if(custSegment.equalsIgnoreCase("Emirati Excellency")){
				custSegment = "T";
			}else if(custSegment.equals("Aspire")){
				custSegment = "A";
			}else if(custSegment.equals("Privilege")){
				custSegment = "E";
			}else if(custSegment.equals("Private Clients")){
				custSegment = "U";
			}else if(custSegment.equals("Simplylife")){
				custSegment = "W";
			}else if(custSegment.equals("Excellency")){
				custSegment = "H";	
			}else if(custSegment.equals("Emirati")){
				custSegment = "O";
			}else if(custSegment.equals("Signatory")){
				custSegment = "Z";
			}else 
				custSegment = "A";
			logger.info("[CPSPrepareDocument]" +"Process name: "+reqProcessName +" Check Segment: "+custSegment);
		} catch (Exception e) {
		    insertAuditTrail("Check Segment Exception");
			logger.error("[checkSegment]" +" Exception: "+e);
		}
		return custSegment;	
	}
	
	public boolean mailRequired() throws Exception{
	try {
		String outputXML = fetchFromCPSMaster(reqProcessName);
		XMLParser xp1 = new XMLParser(outputXML);
		String mailRequired = xp1.getValueOf("MAIL_REQUIRED");
		String emailTemplate = xp1.getValueOf("EMAIL_TEMPLATE");
		String subject = xp1.getValueOf("SUBJECT");
		logger.info("[CPSPrepareDocument]" +"Mail Subject : "+subject +", Mail Required :"+mailRequired);
		logger.info("[CPSPrepareDocument]" +"Mail Template : "+emailTemplate);
		if(mailRequired.equalsIgnoreCase("Y")){
			 return true;
		   }
	    }catch (Exception e) {
			insertAuditTrail("Mail Required Function Exception");
			logger.info("[CPSPrepareDocument]" +"checkSegment: "+e);
	  }return false;	
  }
	
	public Boolean checkCPSWI(){
		String query = "select COUNT(WI_NAME) as COUNT from ext_cps where requestor_wi_name = '"+WI_NAME+"'";
		String outputXML = "";
		try {
			 outputXML = ExecuteXML.executeXML(GenerateXml.APSelectWithColumnNames(cabinetName,query,sessionId));
			 XMLParser xp = new XMLParser(outputXML);
			 int req_count = Integer.parseInt(xp.getValueOf("COUNT"));
				logger.info("[checkCPSWI]" +"CPS WI Count: "+ req_count);
				if(req_count ==  0){
					return true;
				}
		} catch (Exception e) {
			insertAuditTrail("Check CPS WI Function Exception");
			logger.error("[checkCPSWI]" + e);	
		}
		return false;
	}
	
	public Boolean checkRejectionReason(){
		String sQuery= "";
		String outputXML="";
		String accType = fetchFromCPSMapping(reqProcessName,"ACC_CLASS");
		if(accType.equals("I")){
			accType = "Islamic";
		}
		else if(accType.equals("C")){
			accType = "Conventional";
		}
		String Reason = fetchFromCPSMapping(reqProcessName,"REASON");
		logger.info("[checkRejectionReason]" + " REJECTION REASON : " + Reason);
		try {
			sQuery = "SELECT COUNT(REJECTION_REASON) as COUNT "
					+ " FROM CPS_Rejection_Reason_Master  WHERE PROCESS_NAME = '"+reqProcessName+"' AND ACC_CLASS = '"+accType+"' AND REJECTION_REASON = '"+Reason+"'";

			logger.info("[checkRejectionReason]" + " cabinetName : " + cabinetName);
			outputXML = ExecuteXML.executeXML(GenerateXml.APSelectWithColumnNames(cabinetName,sQuery,sessionId));
			
			logger.info("[checkRejectionReason]" + " sQuery : " + sQuery);
			XMLParser xp = new XMLParser(outputXML);
			int req_count = Integer.parseInt(xp.getValueOf("COUNT"));
				logger.info("[checkRejectionReason]" +"Reason Count: "+ req_count);
				if(req_count ==  0){
					return false;
				}
		} catch (Exception e) {
			insertAuditTrail("Check Rejection Reason Exception");
			logger.error("[checkRejectionReason]" + e);	
		}
		return true;
	}
	
	
	
	private Boolean getNoOfAccount(){
	int noOfCount = 0;
	try{
			String sQuery1 = "SELECT COUNT(CUST_ID) as COUNT FROM USR_0_CUST_TXN WHERE WI_NAME = N'"+ WI_NAME + "'";
			String countOutputXML = ExecuteXML.executeXML(GenerateXml.APSelectWithColumnNames(cabinetName,sQuery1,sessionId));
			XMLParser xp = new XMLParser(countOutputXML);			
			noOfCount = Integer.parseInt(xp.getValueOf("COUNT"));
			logger.error("[getNoOfAccount] Count :"+noOfCount );
				if(noOfCount > 1){
					return true;
				}
		} catch (Exception e) {
			logger.error("[getNoOfAccount]"+e );
		}
	return false;
	}
	
}
