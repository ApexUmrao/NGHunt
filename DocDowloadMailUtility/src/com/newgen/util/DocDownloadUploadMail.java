package com.newgen.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import ISPack.CPISDocumentTxn;
import ISPack.ISUtil.JPDBRecoverDocData;
import ISPack.ISUtil.JPISException;
import ISPack.ISUtil.JPISIsIndex;
import Jdts.DataObject.JPDBString;

import com.newgen.AESEncryption;

import com.newgen.dmsapi.DMSXmlList;
import com.newgen.dmsapi.DMSXmlResponse;
import com.newgen.omni.jts.cmgr.XMLParser;
//import com.newgen.tfo.jms.ExecuteXML;
import com.newgen.wfdesktop.xmlapi.WFXmlList;
import com.newgen.wfdesktop.xmlapi.WFXmlResponse;
import org.apache.commons.codec.binary.Base64;
import org.ini4j.Ini;


class ThreadClient extends Log implements Runnable {

	//Config variables start here 
	private static String cabinetName;
	private static String username;
	private static String password;
	private static String serverIp;
	private static String jndiPort;
	private static String EDMScabinetName;
	private static String EDMSjtsIP;
	private static String EDMSjtsPort;
	private static String FolderPath_DD;
	private static String SwiftFolderPath_DD;
	//private static String configReqType="";
	private static String zipDocName="";
	private static String swiftZipDocName="";
	private static String queueId;
	private static String sJtsIp;
	private static String iJtsPort;
	private static String siteId_DD;
	private static String exceptionFrom;
	private static String exceptionTo;
	private static String exceptionCC;
	private static String exceptionSubject;
	private static String exceptionMail;
	private static String sessionId = "";
	private static String sCustomerEmailId;
	private String documentName;
	private static int sleepTime = 0;
	//End here
	boolean bWorkItemCompleteStatus=false;

	//Document Variables Details	
	private String documentIndex;	
	private String swiftDocumentIndex;
	private String volId_DD;
	private String ISIndex;
	private static long sessionInterval;
	private long prevUpdateTime = System.currentTimeMillis();

	//Fetch Workitem Data variables
	private String workItemId;
	private String WINo;
	//External Table Variable name end here
	private String strItemIndex;
	private String strrequest_type;
	private String strrequest_cat;
	private String strtransaction_reference;
	private String strcustomer_id;
	private String strPassword;
	private String sPcAdditionalEmailId="";
	private String sCurrWsName = "";	
	private String processType = "";
	private String swiftDecision = "";
	// end here
	// Master load Variable
	private static HashMap<String ,String> refToActivityRelation=null;
	private static HashMap<String ,String> refToReqTypeCat=null;
	private static boolean bMasterFlg=true;
	private String sInputXML_APSelect;
	private String sOutputXML_apselect;
	private String swiftDocumentName;
	String pid;
	DMSXmlResponse DMSGetWorkItemXmlResponse = null;
	DMSXmlList DMSGetWorkItemXmlList = null;
	private static int wiCount = 0;
	private Boolean errorFlag = false;
	private int errorCount = 0; // ATP-452 REYAZ 24-04-2024

	public ThreadClient() {
		try {
			readConfig(); // To read the configuration file. 
			setCustomServiceStatus("14011","Started","0");
			ExecuteXML.init("WebSphere", serverIp, String.valueOf(jndiPort));
		} catch (Exception ex) {
			customExceptionWithFlag("Exception...", ex);
			System.out.println("Error during reading the configuration file.");
		}
		System.out.println("Inside ThreadClient() constructor.");
	}
	public void run() {
		while (true) {
			if (sessionId.equalsIgnoreCase("") || sessionId.equalsIgnoreCase("null") || !isSessionIdValid(sessionId)) {
				connectToWorkflow();
			}
			try {
				Thread.sleep(sleepTime);
				loadAndProcessWorkItems();
			} catch (Exception exp) {
				customExceptionWithFlag("Exception...", exp);
			} finally
			{
				if(errorFlag)
				{
					setCustomServiceStatus("-25033","Stopped",0+"");
					System.exit(0);
				} 
			}
		}
	}
	private boolean isSessionIdValid(String sessionId) {
		String sOutputXML = "";
		try {
			logger.info("[isSessionIdValid]searchTimestamp : " + prevUpdateTime);
			long prevTime = System.currentTimeMillis() - sessionInterval;
			logger.info("[isSessionIdValid]prevTime : " + prevTime);
			if (prevUpdateTime < prevTime) {
				prevUpdateTime = System.currentTimeMillis();
				sOutputXML = GenerateXml.APProcedure_new(sessionId,cabinetName,"BPM_SESSION_UPDATE","'"+sessionId+"'",1);
				logger.info("WFSESSIONVIEW update : "+sOutputXML);
			}
			logger.info("session id check starts for : "+sessionId);
			sOutputXML = GenerateXml.IsSessionValid(sessionId, cabinetName);
			logger.info("session id check sOutputXML : "+sOutputXML);
			XMLParser xp=new XMLParser(sOutputXML);   
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if (mainCode != 0){
				return false;
			}
		} catch (Exception e) {
			logger.error("Error  while reading config file...", e);
		}
		return true;
	}
	/**
	 * To Connect workflow server
	 */
	/**
	 * Read config file
	 * @throws IOException
	 */
	private void readConfig() throws IOException {
		try {
			Properties p = null;
			p = new Properties();
			p.load(new FileInputStream("config.properties"));
			username = p.getProperty("username");
			password = p.getProperty("password");
			cabinetName = p.getProperty("CabinetName");
			serverIp = p.getProperty("JTSIP");
			jndiPort = p.getProperty("JTSPort");
			EDMScabinetName = p.getProperty("EDMSCabinetName");
			EDMSjtsIP = p.getProperty("EDMSJTSIP");
			EDMSjtsPort = p.getProperty("EDMSJTSPort");			
			exceptionFrom = p.getProperty("exceptionFrom");
			exceptionTo = p.getProperty("exceptionTo");
			exceptionCC = p.getProperty("exceptionCC");
			exceptionSubject = p.getProperty("exceptionSubject");
			exceptionMail = p.getProperty("exceptionMail");
			//volId_DD = p.getProperty("VolumeId");
			siteId_DD = p.getProperty("SiteId");
			FolderPath_DD = System.getProperty("user.dir") + "/" + "DocumentProcessing/DownloadDocument";
			SwiftFolderPath_DD = System.getProperty("user.dir") + "/" + "SwiftDocumentProcessing/DownloadDocument";
			queueId = p.getProperty("QueueId");
			documentName = p.getProperty("DocumentName");
			swiftDocumentName = p.getProperty("SwiftDocumentName");
			sJtsIp = p.getProperty("IP");
			iJtsPort = p.getProperty("PORT"); 
			//configReqType = p.getProperty("RequestType");
			zipDocName = p.getProperty("ZipDocName");
			swiftZipDocName = p.getProperty("SwiftZipDocName");
			sleepTime = Integer.parseInt(p.getProperty("sleepTime"));
			sessionInterval = Long.parseLong(p.getProperty("sessionInterval")) * 60 * 1000;
			deleteFromDirectory(FolderPath_DD);
			deleteFromDirectory(SwiftFolderPath_DD);


			try {
				File myObj = new File("WFServiceConfig.ini");
				Scanner myReader = new Scanner(myObj);
				while (myReader.hasNextLine()) {
					String data = myReader.nextLine();
					if(data != null && !"".equalsIgnoreCase(data))
					{
						pid=data.split("=")[1];
					}
				}
				myReader.close();
				logger.info("pid : "+pid);
			} catch (FileNotFoundException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}

			try {
				password = AESEncryption.decrypt(password);
			} catch (Exception ex) {
				customExceptionWithFlag("Error while decypting the password... ", ex);
			}

			File directory = new File(String.valueOf(FolderPath_DD));

			if(!directory.exists())
			{
				directory.mkdir();
			}
		} catch (IOException exp) {
			customExceptionWithFlag("Error  while reading config file...", exp);
		}
	}


	private void connectToWorkflow(){

		try {			
			if (username == null || password == null) {
				throw new DocDownloadUploadMailException("Username or Password not specified.");
			}
			logger.info("cabinetName= " + cabinetName + " username-" + username);
			String connectInputXML = GenerateXml.getConnectInputXML(cabinetName, username, password);
			logger.info("Connect Input XML:\n" + modifyInputXML(connectInputXML));

			String connectOutputXML = ExecuteXML.executeXML(connectInputXML);

			logger.info(connectOutputXML);

			String mainCode = getTagValue(connectOutputXML, "MainCode");
			mainCode = mainCode.trim();
			logger.info("main code: " + mainCode);

			if (!mainCode.equalsIgnoreCase("0")) {
				logger.info("Error during connection with workflow.");	
			}

			sessionId = getTagValue(connectOutputXML, "SessionId");
			sessionId = sessionId.trim();
			logger.info("session id: " + sessionId);

			if (sessionId.equalsIgnoreCase("") || sessionId.equalsIgnoreCase("null")) {
				logger.info("Unable to login, some problem occurred.");
			}
			logger.info("Successfully logged in into workflow");
		} catch (Throwable e) {
			logger.info("Exception:" + e.getMessage());
			logger.info("Exception:" + e.getStackTrace());
			errorFlag=true;
			e.printStackTrace();
		}
	}
	/**
	 * Fetching workitem and processing.
	 * @throws NumberFormatException
	 * @throws IOException
	 * @throws Exception
	 */
	private void loadAndProcessWorkItems() throws NumberFormatException, IOException, Exception {

		loadMaster();
		int mainCode = 0;
		try {
			String workItemListInputXML = GenerateXml.getFetchWorkItemsInputXML(sessionId, cabinetName, queueId);
			logger.info("The InputXml for Fetch Workitem Call = " + workItemListInputXML);

			String workItemListOutputXML =  ExecuteXML.executeXML(workItemListInputXML);

			logger.info("The OutputXml for Fetch Workitem Call = " + workItemListOutputXML);

			mainCode = getMainCode(workItemListOutputXML);
			if (mainCode == 0) {
				Document doc = getDocument(workItemListOutputXML);

				NodeList instruments = doc.getElementsByTagName("Instrument");
				int length = instruments.getLength();

				for (int NOOfFileProcessed = 0; NOOfFileProcessed < length; NOOfFileProcessed++) {
					wiCount++;
					setCustomServiceStatus("14017","Processing Workitem",1+"");
					Node inst = instruments.item(NOOfFileProcessed);
					WINo = getTagValue(inst, "ProcessInstanceId");
					workItemId = getTagValue(inst, "WorkItemId");

					logger.info("WorkItemId" + NOOfFileProcessed + " = " + WINo);

					String getItemIndex = gettingItemIndex(WINo);
					if("PP_MAKER".equalsIgnoreCase(sCurrWsName) 
							&& checkReqTypeAndCat(strrequest_type,strrequest_cat)){
						downloadDocument(getItemIndex);
					}
					if("Initiator".equalsIgnoreCase(sCurrWsName) && "SWIFT".equalsIgnoreCase(processType) && "TBATC".equalsIgnoreCase(swiftDecision)){
						downloadDocumentSwift(getItemIndex);
					}
					sendReferalEmails(getItemIndex);
					if(bWorkItemCompleteStatus){                    
						String getWorkItemInputXML = GenerateXml.getGetWorkItemXML(WINo, workItemId, sessionId, cabinetName);
						logger.info("The InputXml for WMGetWorkItem = " + getWorkItemInputXML);

						String getWorkItemOutputXML =  ExecuteXML.executeXML(getWorkItemInputXML);

						logger.info("The OutputXml for WMGetWorkItem = " + getWorkItemOutputXML);

						if (!isSuccess(getWorkItemOutputXML)) {
							throw new DocDownloadUploadMailException("Failed to lock work item: ProcessInstanceId=" + WINo + ", WorkItemId=" + workItemId);
						} 
						else {
							logger.info("Locked successfully, work item: " + WINo);
							String wiCompleteInputXML = GenerateXml.getWICompleteXML(WINo, workItemId, sessionId, cabinetName);
							logger.info("The InputXml for WMCompleteWI = " + wiCompleteInputXML);

							String wiCompleteOutputXML =  ExecuteXML.executeXML(wiCompleteInputXML);
							logger.info("The OutputXml  for WMCompleteWI = " + wiCompleteOutputXML);

							if (!isSuccess(wiCompleteOutputXML)) {
								throw new DocDownloadUploadMailException("Failed to complete work item: ProcessInstanceId=" + WINo + ", WorkItemId=" + workItemId);
							} else {
								logger.info("Completed Successfully, work item: " + WINo);
							}
							bWorkItemCompleteStatus = false;
						}                   
					}
				}
			} 
			if(!(mainCode == 18 || mainCode ==0))
				connectToWorkflow();


		} catch (Throwable exp) {
			customException("loadAndProcessWorkItems...", exp);
			logger.info("Exception:" + exp.getMessage());
			logger.info("Exception:" + exp.getStackTrace());
			errorFlag=true;
			exp.printStackTrace();
		} finally {
			
			if (errorFlag) {
				// ATP-452 REYAZ 24-04-2024
				// START CODE
				logger.info("Before  errorCount  :: " +errorCount);
				errorCount++;
				if (errorCount <= 10) {
					logger.info("Inside   (errorCount <= 10 ) :: " +errorCount);
					errorFlag = false;
					Thread.sleep(120000);
				}
				logger.info("After errorCount  :: " +errorCount);
				// END CODE
				if (errorFlag) {
					setCustomServiceStatus("-25033", "Stopped", 0 + "");
					logger.info("Inside loadAndProcessWorkItems  System.exit(0)");
					System.exit(0);
				}
			} else{
				setCustomServiceStatus("-14007","No more Workitems available",0+"");
			}
		}
	}
	private void downloadDocumentSwift(String getItemIndex) {
		logger.info("swift execution starts  for item index"+getItemIndex);
		String folder="";
		String docResponse = "";
		List<File> filesToZip=null;
		try {
			String sInputXML = "<?xml version=\"1.0\"?>"
					+ "<NGOGetDocumentListExt_Input><Option>NGOGetDocumentListExt</Option>"
					+ "<CabinetName>" + cabinetName + "</CabinetName><UserDBId>" + sessionId + "</UserDBId><ZipBuffer>N</ZipBuffer>"
					+ "<FolderIndex>" + strItemIndex + "</FolderIndex><StartPos>0</StartPos><NoOfRecordsToFetch>500</NoOfRecordsToFetch>"
					+ "<OrderBy>5</OrderBy><SortOrder>A</SortOrder><DataAlsoFlag>N</DataAlsoFlag><PreviousRefIndex>0</PreviousRefIndex>"
					+ "<LastRefField></LastRefField><RefOrderBy>2</RefOrderBy><RefSortOrder>A</RefSortOrder><RecursiveFlag>N</RecursiveFlag>"
					+ "<ThumbnailAlsoFlag>N</ThumbnailAlsoFlag></NGOGetDocumentListExt_Input>";

			logger.info("xml NGOGetDocumentListExt_Input  "+sInputXML);
			docResponse =  ExecuteXML.executeXML(sInputXML);
			int numOfDocs = Integer.parseInt(docResponse.substring(docResponse.indexOf("<NoOfRecordsFetched>") + 20, docResponse.indexOf("</NoOfRecordsFetched>")));
			logger.info("sOutputXML found in the current sOutputXML---" + docResponse);
			logger.info("numOfDocs found in the current appref/LAN---" + numOfDocs);
			if (numOfDocs == 0) {
				logger.info("No document found for " + WINo + "--");
			}else{
				logger.info("document found first time");
				folder = SwiftFolderPath_DD+File.separator+WINo;
				File f = new File(folder);
				if (!f.exists()) {
					f.mkdirs();
				}
				logger.info("siteId_DD---" + siteId_DD);
				boolean docFoundSwift=false;
				filesToZip= new ArrayList<File>();
				int docNo=0;
				getMaxDocOrderSwift(docResponse);			
				if(docResponse.indexOf("Documents")>0){
					WFXmlResponse xmlResponse = new WFXmlResponse(docResponse);			
					WFXmlList lWfXml = xmlResponse.createList("Documents","Document");				
					for (int k = 0; lWfXml.hasMoreElements(true); lWfXml.skip(true), k++){					

						ISIndex=lWfXml.getVal("ISIndex");
						String ISIndex1 = ISIndex.substring(0, ISIndex.indexOf("#"));
						volId_DD = ISIndex.substring(ISIndex.indexOf("#") + 1, ISIndex.lastIndexOf("#"));
						String doc_name=lWfXml.getVal("DocumentName");
						String file_type=lWfXml.getVal("CreatedByAppName");

						logger.info("Customer swift document found and its details "	+"\n1. doc index " + getSwiftDocumentIndex()	+"\n2.doc name " +doc_name+"\n3.ISIndex " +lWfXml.getVal("ISIndex")	+"\n4.file type or ext" +file_type+"\n5.DocOrderNo " +lWfXml.getVal("DocOrderNo")+"\n6.ISIndex1t " +ISIndex1+"\n7.volId_DD " +volId_DD);
						if(getSwiftDocumentIndex().equalsIgnoreCase(lWfXml.getVal("DocumentIndex")) 
								&& swiftDocumentName.equalsIgnoreCase(doc_name)
								&& !"zip".equalsIgnoreCase(file_type)){
							logger.info("Customer swift found for " + WINo +" and document index is : "+ getSwiftDocumentIndex());	                	 

							docFoundSwift=true;

							try {
								String sDumpPathFromImageServer_DD = folder + File.separator + doc_name +"_"+docNo+ "." + file_type;
								logger.info("swift Document will be downloaded to---" + sDumpPathFromImageServer_DD);
								//String success = fDownloadDocumentFromImageServer(serverIp,jndiPort, cabinetName, Integer.parseInt(ISIndex1), Short.parseShort(siteId_DD), Short.parseShort(volId_DD),  sDumpPathFromImageServer_DD);
								String success =fDownloadDocumentFromImageServerWithoutAppServer(EDMSjtsIP,EDMSjtsPort, EDMScabinetName, Integer.parseInt(ISIndex1), Short.parseShort(siteId_DD), Short.parseShort(volId_DD),  sDumpPathFromImageServer_DD);
								logger.info("swift success for document download---" + success);
								if("0".equalsIgnoreCase(success)){
									filesToZip.add(new File(sDumpPathFromImageServer_DD));
								}
							} catch (Exception e) {				
								customException("swift Exception while calling fDownloadDocumentFromImageServer()--", e);							
							}				
						}
					}
				}

				try {
					if(docFoundSwift){
						if(filesToZip !=null && filesToZip.size()>0){
							String zipFileName = folder + File.separator + swiftZipDocName + ".zip";						
							boolean isFilesZipped = zipFiles(zipFileName, filesToZip, strPassword);
							if (isFilesZipped) {
								uploadDocumentSwift(zipFileName,docResponse);
								logger.info("swift Upload document successfully");							
							} else {
								logger.info("swift Files zipping failed");
							}							
						}						
					}
				} catch (Exception e) {
					customException("swift downloadDocument...while zipping", e);
				}finally{
					docFoundSwift=false;
					if(folder!=null){
						deleteFromDirectory(folder);
					}					
				}

			}

		} catch (Exception exp) {
			customException("downloadDocument...", exp);
		}finally{
			if(folder!=null){
				deleteFromDirectory(folder); 
			}
			if(filesToZip!=null){
				filesToZip=null;
			}
		}

	}
	private String getTagValue(Node node, String tag) {
		String value = "";
		NodeList nodeList = node.getChildNodes();
		int length = nodeList.getLength();

		for (int i = 0; i < length; ++i) {
			Node child = nodeList.item(i);
			if (child.getNodeType() == Node.ELEMENT_NODE && child.getNodeName().equalsIgnoreCase(tag)) {
				return child.getTextContent();
			}

		}
		return value;
	}

	private Document getDocument(String xml) throws ParserConfigurationException, SAXException, IOException {

		DocumentBuilderFactory dbf= DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new InputSource(new StringReader(xml)));
		return doc;
	}

	private String getTagValue(String xml, String tag) throws ParserConfigurationException, SAXException, IOException {
		Document doc = getDocument(xml);
		NodeList nodeList = doc.getElementsByTagName(tag);

		int length = nodeList.getLength();

		if (length > 0) {
			Node node = nodeList.item(0);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				NodeList childNodes = node.getChildNodes();
				String value = "";
				int count = childNodes.getLength();
				for (int i = 0; i < count; i++) {
					Node item = childNodes.item(i);
					if (item.getNodeType() == Node.TEXT_NODE) {
						value += item.getNodeValue();
					}
				}
				return value;
			} else if (node.getNodeType() == Node.TEXT_NODE) {
				return node.getNodeValue();
			}

		}
		return "";
	}
	/** 
	 * Checking main code and retuing main code values
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	private int getMainCode(String xml) throws Exception {
		String code = "";
		try {
			code = getTagValue(xml, "MainCode");
		} catch (Exception e) {
			logger.info(""+e);
			throw e;
		}

		int mainCode = -1;
		try {
			mainCode = Integer.parseInt(code);
			logger.info("Main Code: " + mainCode);
		} catch (NumberFormatException e) {
			mainCode = -1;
		}

		return mainCode;

	}
	/**
	 * Checking main code and return boolean value
	 * @param xml
	 * @return
	 */
	private boolean isSuccess(String xml) {
		try {
			if (xml.substring(xml.indexOf("<MainCode>") + 10, xml.indexOf("</MainCode>")).equals("0")) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}
	}
	/**
	 * To Fetch external table data
	 * @param getWiName
	 * @return
	 */
	private String gettingItemIndex(String getWiName) {

		try {
			String strQueryEXT = "SELECT ITEMINDEX,REQUEST_CATEGORY,REQUEST_TYPE,TRANSACTION_REFERENCE,"
					+ "CUSTOMER_ID,CURR_WS,PC_ADDTNL_MAIL,PROCESS_TYPE,SWIFT_DEC_ON_SWIFT_MESSAGE,"
					+ "TRADE_CUST_EMAIL_ID FROM EXT_TFO WHERE WI_NAME='" + getWiName + "'";
			logger.info("strQueryEXT----->" + strQueryEXT);
			sInputXML_APSelect = GenerateXml.APSelectWithColumnNames(cabinetName, strQueryEXT, sessionId);
			sOutputXML_apselect =  ExecuteXML.executeXML(sInputXML_APSelect);

			XMLParser xmlParser= new XMLParser(sOutputXML_apselect);
			if("0".equalsIgnoreCase(xmlParser.getValueOf("MainCode"))){            	

				strItemIndex = xmlParser.getValueOf("ITEMINDEX");
				strrequest_type = xmlParser.getValueOf("REQUEST_TYPE");
				strtransaction_reference = xmlParser.getValueOf("TRANSACTION_REFERENCE");
				strcustomer_id = xmlParser.getValueOf("CUSTOMER_ID");
				sCurrWsName = xmlParser.getValueOf("CURR_WS");
				sPcAdditionalEmailId = xmlParser.getValueOf("PC_ADDTNL_MAIL");
				sCustomerEmailId = xmlParser.getValueOf("TRADE_CUST_EMAIL_ID");
				strrequest_cat= xmlParser.getValueOf("REQUEST_CATEGORY");
				processType=xmlParser.getValueOf("PROCESS_TYPE");
				swiftDecision=xmlParser.getValueOf("SWIFT_DEC_ON_SWIFT_MESSAGE");
				strPassword = strcustomer_id;
				if(!strPassword.isEmpty()){
					strPassword=strPassword.trim();
				}
				sCurrWsName = ("Trayd Stream".equalsIgnoreCase(sCurrWsName)) ? "PP_MAKER" : sCurrWsName; // |ATP-525|REYAZ|16-12-2024
			} else if ("18".equalsIgnoreCase(xmlParser.getValueOf("MainCode"))) {
				logger.info("Record not available....\n");
			} else if ("15".equalsIgnoreCase(xmlParser.getValueOf("MainCode"))) {
				logger.info("Session out / sysnax error : \n" + strQueryEXT + "\n");
			}
			logger.info("strItemIndex...." + strItemIndex);
			logger.info("strrequest_type...." + strrequest_type);
			logger.info("strtransaction_reference...." + strtransaction_reference);
			logger.info("strcustomer_id...." + strcustomer_id);
			logger.info("strrequest_cat" +strrequest_cat);

		} catch (Throwable exp) {
			customException("gettingItemIndex...", exp);

		}
		return strItemIndex;
	}
	/**
	 * Download document from OD and create zip file
	 * @param strItemIndex
	 */
	private void downloadDocument(String strItemIndex) {
		String folder="";
		String docResponse = "";
		List<File> filesToZip=null;
		try {
			String sInputXML = "<?xml version=\"1.0\"?>"
					+ "<NGOGetDocumentListExt_Input><Option>NGOGetDocumentListExt</Option>"
					+ "<CabinetName>" + cabinetName + "</CabinetName><UserDBId>" + sessionId + "</UserDBId><ZipBuffer>N</ZipBuffer>"
					+ "<FolderIndex>" + strItemIndex + "</FolderIndex><StartPos>0</StartPos><NoOfRecordsToFetch>500</NoOfRecordsToFetch>"
					+ "<OrderBy>5</OrderBy><SortOrder>A</SortOrder><DataAlsoFlag>N</DataAlsoFlag><PreviousRefIndex>0</PreviousRefIndex>"
					+ "<LastRefField></LastRefField><RefOrderBy>2</RefOrderBy><RefSortOrder>A</RefSortOrder><RecursiveFlag>N</RecursiveFlag>"
					+ "<ThumbnailAlsoFlag>N</ThumbnailAlsoFlag></NGOGetDocumentListExt_Input>";

			logger.info("xml NGOGetDocumentListExt_Input  "+sInputXML);
			docResponse =  ExecuteXML.executeXML(sInputXML);
			int numOfDocs = Integer.parseInt(docResponse.substring(docResponse.indexOf("<NoOfRecordsFetched>") + 20, docResponse.indexOf("</NoOfRecordsFetched>")));
			logger.info("sOutputXML found in the current sOutputXML---" + docResponse);
			logger.info("numOfDocs found in the current appref/LAN---" + numOfDocs);
			if (numOfDocs == 0) {
				logger.info("No document found for " + WINo + "--");
			}else{
				folder = FolderPath_DD+File.separator+WINo;
				File f = new File(folder);
				if (!f.exists()) {
					f.mkdirs();
				}
				logger.info("siteId_DD---" + siteId_DD);
				boolean docFound=false;
				filesToZip= new ArrayList<File>();
				int docNo=0;
				getMaxDocOrder(docResponse);			
				if(docResponse.indexOf("Documents")>0){
					WFXmlResponse xmlResponse = new WFXmlResponse(docResponse);			
					WFXmlList lWfXml = xmlResponse.createList("Documents","Document");				
					for (int k = 0; lWfXml.hasMoreElements(true); lWfXml.skip(true), k++){					

						ISIndex=lWfXml.getVal("ISIndex");
						String ISIndex1 = ISIndex.substring(0, ISIndex.indexOf("#"));
						volId_DD = ISIndex.substring(ISIndex.indexOf("#") + 1, ISIndex.lastIndexOf("#"));
						String doc_name=lWfXml.getVal("DocumentName");
						String file_type=lWfXml.getVal("CreatedByAppName");

						logger.info("Customer Referral document found and its details "	+"\n1. doc index " + getDocumentIndex()	+"\n2.doc name " +doc_name+"\n3.ISIndex " +lWfXml.getVal("ISIndex")	+"\n4.file type or ext" +file_type+"\n5.DocOrderNo " +lWfXml.getVal("DocOrderNo")+"\n6.ISIndex1t " +ISIndex1+"\n7.volId_DD " +volId_DD);
						if(getDocumentIndex().equalsIgnoreCase(lWfXml.getVal("DocumentIndex")) 
								&& documentName.equalsIgnoreCase(doc_name)
								&& !"zip".equalsIgnoreCase(file_type)){
							logger.info("Customer Request found for " + WINo +" and document index is : "+ getDocumentIndex());	                	 

							docFound=true;
							++docNo;

							try {
								String sDumpPathFromImageServer_DD = folder + File.separator + doc_name +"_"+docNo+ "." + file_type;
								logger.info("Document will be downloaded to---" + sDumpPathFromImageServer_DD);
							//	String success = fDownloadDocumentFromImageServer(serverIp,jndiPort, cabinetName, Integer.parseInt(ISIndex1), Short.parseShort(siteId_DD), Short.parseShort(volId_DD),  sDumpPathFromImageServer_DD);
								String success =fDownloadDocumentFromImageServerWithoutAppServer(EDMSjtsIP,EDMSjtsPort, EDMScabinetName, Integer.parseInt(ISIndex1), Short.parseShort(siteId_DD), Short.parseShort(volId_DD),  sDumpPathFromImageServer_DD);
								logger.info("success for document download---" + success);
								if("0".equalsIgnoreCase(success)){
									filesToZip.add(new File(sDumpPathFromImageServer_DD));
								}
							} catch (Exception e) {				
								customException("Exception while calling fDownloadDocumentFromImageServer()--", e);							
							}				
						}
					}
				}

				try {
					if(docFound){
						if(filesToZip !=null && filesToZip.size()>0){
							String zipFileName = folder + File.separator + zipDocName + ".zip";						
							boolean isFilesZipped = zipFiles(zipFileName, filesToZip, strPassword);
							if (isFilesZipped) {
								uploadDocument(zipFileName,docResponse);
								logger.info("Upload document successfully");							
							} else {
								logger.info("Files zipping failed");
							}							
						}						
					}
				} catch (Exception e) {
					customException("downloadDocument...while zipping", e);
				}finally{
					docFound=false;
					if(folder!=null){
						deleteFromDirectory(folder);
					}					
				}

			}

		} catch (Throwable exp) {
			customException("downloadDocument...", exp);
		}finally{
			if(folder!=null){
				deleteFromDirectory(folder);
			}
			if(filesToZip!=null){
				filesToZip=null;
			}
		}

	}
	/**
	 * Download file from image server
	 * @param ServerIP
	 * @param sPort
	 * @param sCabinetName
	 * @param DocIndex
	 * @param siteId
	 * @param volId
	 * @param sDumpPathFromImageServer
	 * @return
	 */
	private String fDownloadDocumentFromImageServer(String ServerIP, String sPort, String sCabinetName, int DocIndex, short siteId, short volId, String sDumpPathFromImageServer) {
		try {
			JPDBString oSiteName = new JPDBString();
			logger.info("ServerIP " + sJtsIp);
			logger.info("sPort " + iJtsPort);
			logger.info("sCabinetName " + sCabinetName);
			logger.info("siteId " + siteId);
			logger.info("volId " + volId);
			logger.info("DocIndex " + DocIndex);
			logger.info("sDumpPathFromImageServer " + sDumpPathFromImageServer);
			logger.info("oSiteName " + oSiteName);

			CPISDocumentTxn.GetDocInFile_MT(null, sJtsIp, Short.parseShort(iJtsPort), sCabinetName,
					siteId, volId, DocIndex, "", sDumpPathFromImageServer, oSiteName);
			return "0";
		} catch (Throwable ex) {			
			customException("swift Exception while calling fDownloadDocumentFromImageServer()--", ex);
			return "1";
		}
	}
	
	// REYAZ|ATP-525|02-01-2025 for mail attachment issue 
	private String fDownloadDocumentFromImageServerWithoutAppServer(String EdmsServerIP, String EdmssPort, String EdmsCabinetName, int DocIndex, short siteId, short volId, String sDumpPathFromImageServer) {
		try {
			JPDBString oSiteName = new JPDBString();
			logger.info("EdmsServerIP " + EdmsServerIP);
			logger.info("EdmssPort " + EdmssPort);
			logger.info("EdmsCabinetName " + EdmsCabinetName);
			logger.info("siteId " + siteId);
			logger.info("volId " + volId);
			logger.info("DocIndex " + DocIndex);
			logger.info("sDumpPathFromImageServer " + sDumpPathFromImageServer);
			logger.info("oSiteName " + oSiteName);

			CPISDocumentTxn.GetDocInFile_MT(null, EdmsServerIP, Short.parseShort(EdmssPort), EdmsCabinetName,
					siteId, volId, DocIndex, "", sDumpPathFromImageServer, oSiteName);
			return "0";
		} catch (Throwable ex) {			
			customException("swift Exception while calling fDownloadDocumentFromImageServerWithoutAppServer()--", ex);
			return "1";
		}
	}
	

	/**
	 * Preparing zip file with password protected
	 * @param zipFileName
	 * @param filesToZip
	 * @param zipPass
	 * @return
	 */
	private boolean zipFiles(String zipFileName, List<File> filesToZip, String zipPass) {
		ZipFile zipFile=null;
		ZipParameters zipParameters=null;
		try {
			zipFile = new ZipFile(zipFileName);
			zipParameters = new ZipParameters();
			zipParameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			zipParameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			zipParameters.setEncryptFiles(true);
			zipParameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
			zipParameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
			zipParameters.setPassword(zipPass);
			zipFile.addFiles(new ArrayList<File>(filesToZip), zipParameters);
		} catch (Throwable exp) {
			customException("zipFiles...", exp);
			return false;
		}finally{
			if(zipFile!=null)
				zipFile=null;
			if(zipParameters!=null)
				zipParameters=null;
		}
		return true;
	}
	/**
	 * Delete document from OD and add document into image server
	 * @param ImagePath
	 * @param docXml
	 */
	private void uploadDocument(String ImagePath,String docXml){

		File imageFile = new File(ImagePath);
		long lFileLength = imageFile.length();
		try {        	
			JPISIsIndex strISINDEX = new JPISIsIndex();
			JPDBRecoverDocData JPISDEC = new JPDBRecoverDocData();
			CPISDocumentTxn.AddDocument_MT(null, EDMSjtsIP, Short.parseShort(EDMSjtsPort), EDMScabinetName, 
					Short.parseShort(volId_DD), imageFile.getPath(), JPISDEC, "", strISINDEX);
			String docIsIndx = strISINDEX.m_nDocIndex + "#" + strISINDEX.m_sVolumeId + "#";
			addDocumentCall(ImagePath, lFileLength, docIsIndx);
			logger.info("Document uploaded successfully--");
		}catch (JPISException ex) {
			ex.printStackTrace();
			java.util.logging.Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
		} 
		catch (Throwable exp) {
			customException("upload_document...", exp);
		}finally{
			if(imageFile!=null){
				imageFile=null;
			}			
		}
	}
	private void uploadDocumentSwift(String ImagePath,String docXml){

		File imageFile = new File(ImagePath);
		long lFileLength = imageFile.length();
		try {  
			logger.info("ImagePath"+ImagePath);
			logger.info("docXml"+docXml);
			logger.info("upload doc starts");
			JPISIsIndex strISINDEX = new JPISIsIndex();
			logger.info("upload strISINDEX");
			JPDBRecoverDocData JPISDEC = new JPDBRecoverDocData();
			logger.info("upload JPISDEC");
			CPISDocumentTxn.AddDocument_MT(null, EDMSjtsIP, Short.parseShort(EDMSjtsPort), EDMScabinetName, Short.parseShort(volId_DD), imageFile.getPath(), JPISDEC, "", strISINDEX);
			logger.info("upload CPISDocumentTxn");
			String docIsIndx = strISINDEX.m_nDocIndex + "#" + strISINDEX.m_sVolumeId + "#";
			logger.info("upload docIsIndx");
			addSwiftDocumentCall(ImagePath, lFileLength, docIsIndx);
			logger.info("Document uploaded successfully--");
		}catch (JPISException ex) {
			ex.printStackTrace();
			logger.info("Document failed "+ex);
			java.util.logging.Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
		} 
		catch (Throwable exp) {
			customException("upload_document...", exp);
			logger.info("Document failed 2"+exp);
		}finally{
			if(imageFile!=null){
				imageFile=null;
			}			
		}
	}
	/**
	 * Add document API 
	 * @param strImagePath
	 * @param docLength
	 * @param docIsIndx
	 */
	private void addDocumentCall(String strImagePath, Long docLength, String docIsIndx) {
		logger.info("strImagePath--" + strImagePath);
		logger.info("docLength--" + docLength);

		try {

			String addDocInputXml = "<?xml version=\"1.0\"?><NGOAddDocument_Input><BypassHookTag></BypassHookTag><Option>NGOAddDocument</Option>"
					+ "<CabinetName>" + cabinetName + "</CabinetName>"
					+ "<UserDBId>" + sessionId + "</UserDBId><GroupIndex>0</GroupIndex>"
					+ "<Document><ParentFolderIndex>" + strItemIndex + "</ParentFolderIndex>"
					+ "<NoOfPages>1</NoOfPages><AccessType>I</AccessType>"
					+ "<DocumentName>Customer_Referral</DocumentName>"
					+ "<CreationDateTime></CreationDateTime>"
					+ "<DocumentType>N</DocumentType>"
					+ "<DocumentSize>" + docLength + "</DocumentSize>"
					+ "<CreatedByAppName>zip</CreatedByAppName>"
					+ "<ISIndex>" + docIsIndx + "</ISIndex>"
					+ "<ODMADocumentIndex></ODMADocumentIndex>"
					+ "<Comment>"+getCurrentDate1()+"</Comment>"
					+ "<EnableLog>Y</EnableLog>"
					+ "<FTSFlag>PP</FTSFlag>"
					+ "<DataDefinition></DataDefinition>"
					+ "<Keywords></Keywords></Document>"
					+ "</NGOAddDocument_Input>";

			String addDocOutputXml =  ExecuteXML.executeXML(addDocInputXml);
			logger.info("Document added successfully--"+addDocOutputXml);

		} catch (Exception exp) {
			customException("add_Document...", exp);
		}
	}

	private void addSwiftDocumentCall(String strImagePath, Long docLength, String docIsIndx) {
		logger.info("strImagePath--" + strImagePath);
		logger.info("docLength--" + docLength);

		try {
			logger.info("image");
			String addDocInputXml = "<?xml version=\"1.0\"?><NGOAddDocument_Input><BypassHookTag></BypassHookTag><Option>NGOAddDocument</Option>"
					+ "<CabinetName>" + cabinetName + "</CabinetName>"
					+ "<UserDBId>" + sessionId + "</UserDBId><GroupIndex>0</GroupIndex>"
					+ "<Document><ParentFolderIndex>" + strItemIndex + "</ParentFolderIndex>"
					+ "<NoOfPages>1</NoOfPages><AccessType>I</AccessType>"
					+ "<DocumentName>"+swiftDocumentName+"</DocumentName>"
					+ "<CreationDateTime></CreationDateTime>"
					+ "<DocumentType>N</DocumentType>"
					+ "<DocumentSize>" + docLength + "</DocumentSize>"
					+ "<CreatedByAppName>zip</CreatedByAppName>"
					+ "<ISIndex>" + docIsIndx + "</ISIndex>"
					+ "<ODMADocumentIndex></ODMADocumentIndex>"
					+ "<Comment>"+getCurrentDate1()+"</Comment>"
					+ "<EnableLog>Y</EnableLog>"
					+ "<FTSFlag>PP</FTSFlag>"
					+ "<DataDefinition></DataDefinition>"
					+ "<Keywords></Keywords></Document>"
					+ "</NGOAddDocument_Input>";
			logger.info("addDocInputXml"+addDocInputXml);
			String addDocOutputXml =  ExecuteXML.executeXML(addDocInputXml);
			logger.info("Document added successfully--"+addDocOutputXml);

		} catch (Exception exp) {
			customException("add_Document...", exp);
		}
	}

	/**
	 * Send referral mail on thebasis of referral as well as workstep
	 */
	@SuppressWarnings({ "unused", "unused" })
	private void sendReferalEmails(String getItemIndex) {
		ArrayList<String> arrL=null;
		try {

			arrL= new ArrayList<String>();
			String strQueryREF = "SELECT DISTINCT REFFERD_TO FROM TFO_DJ_FINAL_DEC_SUMMARY WHERE WI_NAME ='" + WINo + "'";

			logger.info("strQueryEXT----->" + strQueryREF);

			sInputXML_APSelect = GenerateXml.APSelectWithColumnNames(cabinetName, strQueryREF, sessionId);
			sOutputXML_apselect =  ExecuteXML.executeXML(sInputXML_APSelect);			
			sOutputXML_apselect = sOutputXML_apselect.replaceAll("&", "and");			
			logger.info("Output XML after----->" + sOutputXML_apselect);
			DMSGetWorkItemXmlResponse = new DMSXmlResponse(sOutputXML_apselect);	
			if (DMSGetWorkItemXmlResponse.getVal("MainCode").equals("0")) {
				WFXmlResponse xmlResponse = new WFXmlResponse(sOutputXML_apselect);			
				WFXmlList lWfXml = xmlResponse.createList("Records","Record");				
				for (int k = 0; lWfXml.hasMoreElements(true); lWfXml.skip(true), k++){
					if(lWfXml.getVal("REFFERD_TO")!=null && !lWfXml.getVal("REFFERD_TO").isEmpty()){
						arrL.add(lWfXml.getVal("REFFERD_TO").trim());
					}
				}
			}
			else if (DMSGetWorkItemXmlResponse.getVal("MainCode").equals("18")) {
				logger.info("Record not available....\n");			
			} else if (DMSGetWorkItemXmlResponse.getVal("MainCode").equals("15")) {
				logger.info("Session out / sysnax error : \n" + strQueryREF + "\n");
			}
			String sReferredTo = "";
			String sToMailId = "";
			String sNewParameter = "";

			logger.info("Final ArrLstTableValues after----->" + arrL);
			logger.info("Final ArrLstTableValues Sizeee" + arrL.size());
			if(!sCurrWsName.equalsIgnoreCase("PROCESSING CHECKER") &&
					!sCurrWsName.equalsIgnoreCase("PC PROCESSING SYSTEM") 
					&& !"Initiator".equalsIgnoreCase(sCurrWsName)){
				if (arrL != null) {
					if (arrL.size() > 0) {
						for (int i = 0; i < arrL.size(); i++) {
							logger.info("sValue " + i);
							sReferredTo = arrL.get(i);
							logger.info("Referrred To " + sReferredTo);
							if (!sReferredTo.isEmpty()) {

								sToMailId = getToMailIdFromGrid(sReferredTo);
								String sCurrentDateTime = getCurrentDate();
								String sEncWIName = new String(Base64.encodeBase64(this.WINo.getBytes()));
								String sEncWSName = new String(Base64.encodeBase64(getCurrWSName(sReferredTo).getBytes()));
								String sEncTimeStamp = new String(Base64.encodeBase64(sCurrentDateTime.getBytes()));

								//sNewParameter = sReferredTo + "','" + sToMailId + "','" + AESEncryption.encrypt(this.WINo) + "','" + sCurrWsName + "','" + AESEncryption.encrypt(getCurrWSName(sReferredTo)) + "','" + getGroupMailIDStatus(sToMailId)+ "','" + AESEncryption.encrypt(sCurrentDateTime);

								sNewParameter = sReferredTo + "','" + sToMailId + "','" + sEncWIName + "','" + sCurrWsName + "','" + sEncWSName + "','" + getGroupMailIDStatus(sToMailId)+ "','" + sEncTimeStamp;
								logger.info("sNewParameter" + sNewParameter);
								sendEmailMulExcp("TFO_DJ_REFF_EMAIL_NOTIFY", sNewParameter);
								logger.info(" Inside PPM bWorkItemCompleteStatus " + bWorkItemCompleteStatus);

							}
							sReferredTo = "";
						}
					}else{
						bWorkItemCompleteStatus=true;
					}
				}else{
					bWorkItemCompleteStatus=true;
				}

			}else if("Initiator".equalsIgnoreCase(sCurrWsName) && "SWIFT".equalsIgnoreCase(processType) && "TBATC".equalsIgnoreCase(swiftDecision))
			{
				sNewParameter = "'"+WINo+"','"+sCustomerEmailId+"','"+getItemIndex+"'";
				logger.info("sNewParameter" + sNewParameter);
				sendEmailMulExcpSwift("TFO_DJ_SWIFT_EMAIL_NOTIFY", sNewParameter);
			}
			else{ 
				logger.info(" Inide Processing Checker");
				sNewParameter = "Customer" + "','" + sPcAdditionalEmailId + "','" + "" + "','" + sCurrWsName + "','" + "" + "','" + "0"+"','"+"";
				sendEmailMulExcp("TFO_DJ_REFF_EMAIL_NOTIFY", sNewParameter);
				logger.info(" Inside PC bWorkItemCompleteStatus " + bWorkItemCompleteStatus);
			}
		} catch (Throwable exp) {
			bWorkItemCompleteStatus = false;
			logger.info(" Inside Catch SendREferalmail  "+ bWorkItemCompleteStatus);
			customException("sendReferalEmails...", exp);
		}finally{
			if(arrL!=null){
				arrL=null;
			}
		}
	}
	/**
	 * Get Current date with time stamp
	 * @return
	 */
	private String getCurrentDate() {
		String today = null;
		try {
			Date date = Calendar.getInstance().getTime();
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			today = formatter.format(date);
		} catch (Throwable exp) {
			customException("sendReferalEmails...", exp);
		}

		return today;
	}
	private String getCurrentDate1() {
		String today = "";
		try {
			Date date = Calendar.getInstance().getTime();
			DateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			today = formatter.format(date);		
		} catch (Throwable exp) {
			exp.printStackTrace();
		}
		return today;
	}
	/**
	 * Check the activity name on the basis of referrals
	 * Need fetch from master-In Next sprint 
	 * @param sRefferredTo
	 * @return
	 */
	private String getCurrWSName(String sRefferredTo) {
		String sCurrWsName = "";
		try {
			logger.info("getCurrWSName----->sRefferredTo is >>>" + sRefferredTo);
			sCurrWsName=refToActivityRelation.get(sRefferredTo.toUpperCase());
			logger.info("getCurrWSName----->sCurrWsName is >>>" + sCurrWsName);
		} catch (Throwable e) {
			customException("Exception in getCurrWSName", e);
		}
		return sCurrWsName;
	}
	/**
	 * To find email id for referral case
	 * @param sReferredTo
	 * @return
	 */
	private String getToMailIdFromGrid(String sReferredTo) {
		String sMailTo = "";
		String sQueryToMailId = "SELECT DISTINCT EXISTING_MAIL, NEW_MAIL FROM TFO_DJ_FINAL_DEC_SUMMARY WHERE WI_NAME = '" + WINo + "' AND REFFERD_TO='" + sReferredTo + "'";
		try {

			logger.info("sQueryToMailId----->" + sQueryToMailId);

			sInputXML_APSelect = GenerateXml.APSelectWithColumnNames(cabinetName, sQueryToMailId, sessionId);
			logger.info("getToMailIdFromGrid input XML :\n" + sInputXML_APSelect + "\n");
			sOutputXML_apselect =  ExecuteXML.executeXML(sInputXML_APSelect);			
			DMSGetWorkItemXmlResponse = new DMSXmlResponse(sOutputXML_apselect);			
			logger.info("getToMailIdFromGrid Output XML :\n" + DMSGetWorkItemXmlResponse + "\n");
			if (DMSGetWorkItemXmlResponse.getVal("MainCode").equals("0")) {

				DMSGetWorkItemXmlList = DMSGetWorkItemXmlResponse.createList("Records", "Record");
				for (int DMSGetWorkItemXmlLoopCnt = 0; DMSGetWorkItemXmlList.hasMoreElements(true); DMSGetWorkItemXmlList.skip(true), DMSGetWorkItemXmlLoopCnt++) {
					//logger.info("sReplacedXMLTag before----->" + sReplacedXMLTag);
					if(DMSGetWorkItemXmlList.getVal("EXISTING_MAIL")!=null && (!DMSGetWorkItemXmlList.getVal("EXISTING_MAIL").isEmpty() 
							|| "null".equalsIgnoreCase(DMSGetWorkItemXmlList.getVal("EXISTING_MAIL"))) ){
						sMailTo=sMailTo+DMSGetWorkItemXmlList.getVal("EXISTING_MAIL")+",";
					}
					if(DMSGetWorkItemXmlList.getVal("NEW_MAIL")!=null &&   (!DMSGetWorkItemXmlList.getVal("NEW_MAIL").isEmpty() 
							|| "null".equalsIgnoreCase(DMSGetWorkItemXmlList.getVal("NEW_MAIL"))) ){
						sMailTo=sMailTo+DMSGetWorkItemXmlList.getVal("NEW_MAIL")+",";
					}
					//logger.info("sReplacedXMLTag after-----> Final Demo" + sReplacedXMLTag);
				}
			} else if (DMSGetWorkItemXmlResponse.getVal("MainCode").equals("18")) {
				logger.info("Record not available....\n");
			} else if (DMSGetWorkItemXmlResponse.getVal("MainCode").equals("15")) {
				logger.info("Session out / sysnax error : \n" + sQueryToMailId + "\n");
			}
			logger.info("Final sMailTo is >>>>>>>>>>>>" + sMailTo);
			if (sMailTo.indexOf(",") > -1) {
				sMailTo = sMailTo.substring(0, sMailTo.lastIndexOf(","));
				sMailTo=sMailTo.replaceAll("\'","\'\'");
			}
			logger.info("Final sMailTo after single code replace >>>>>>>>>>>>" + sMailTo);
		} catch (Throwable exp) {
			customException("getToMailIdFromGrid...", exp);  
		}
		return sMailTo;
	}
	/**
	 * Group EMail Id check. We can remove but we have make more effort .Due to this ,its returning default 0
	 * @param sToMailId
	 * @return
	 */
	private String getGroupMailIDStatus(String sToMailId) {
		return "0";
	}
	/**
	 * Send mail if exception raised
	 * @param strProcName
	 * @param strparamName
	 */
	private void sendEmailMulExcp(String strProcName, String strparamName) {

		try {
			strparamName = WINo + "'" + "," + "'" + strparamName;
			sInputXML_APSelect = "<?xml version=\"1.0\"?>"
					+ "<WMTestProcedure_Input>"
					+ "<Option>APProcedure</Option>"
					+ "<SessionId>" + sessionId + "</SessionId>"                   
					+ "<ProcName>" + strProcName + "</ProcName>"
					+ "<Params>'" + strparamName + "'</Params>"                    
					+ "<EngineName>" + cabinetName + "</EngineName>"
					+ "<WMTestProcedure_Input>";
			logger.info("sendEmailMulExcp Mailing-->" + sInputXML_APSelect);

			sOutputXML_apselect =  ExecuteXML.executeXML(sInputXML_APSelect);

			logger.info("Procedure Output "+sOutputXML_apselect);
			bWorkItemCompleteStatus = isSuccess(sOutputXML_apselect);
			if(bWorkItemCompleteStatus){
				logger.info("Procedure Status "+bWorkItemCompleteStatus);
			}else{
				logger.info("Mail Send Failed For "+WINo);
			}
		} catch (Throwable exp) {            
			customException("sendEmailMulExcp...", exp);
			logger.info("Error in getting the item index--" + exp);			
		}

	}
	private void sendEmailMulExcpSwift(String strProcName, String strparamName) {

		try {
			sInputXML_APSelect = "<?xml version=\"1.0\"?>"
					+ "<WMTestProcedure_Input>"
					+ "<Option>APProcedure</Option>"
					+ "<SessionId>" + sessionId + "</SessionId>"                   
					+ "<ProcName>" + strProcName + "</ProcName>"
					+ "<Params>" + strparamName + "</Params>"                    
					+ "<EngineName>" + cabinetName + "</EngineName>"
					+ "<WMTestProcedure_Input>";
			logger.info("sendEmailMulExcp Mailing-->" + sInputXML_APSelect);

			sOutputXML_apselect =  ExecuteXML.executeXML(sInputXML_APSelect);

			logger.info("Procedure Output "+sOutputXML_apselect);
			bWorkItemCompleteStatus = isSuccess(sOutputXML_apselect);
			if(bWorkItemCompleteStatus){
				logger.info("Procedure Status "+bWorkItemCompleteStatus);
			}else{
				logger.info("Mail Send Failed For "+WINo);
			}
		} catch (Throwable exp) {            
			customException("sendEmailMulExcp...", exp);
			logger.info("Error in getting the item index--" + exp);			
		}

	}
	/**
	 * Checking before deleting the directory
	 * @param filePath
	 */
	private void deleteFromDirectory(String filePath){
		try {
			File dir = new File(filePath);			
			if(dir.isDirectory()){
				deleteDir(dir);
			}			
		} catch (Throwable e) {
			customException("exception while deleting file in deleteFRomDirectory",e);
		}

	}
	/**
	 * Delete file and fodler 
	 * @param file
	 */
	private void deleteDir(File file) {
		try {
			File[] contents = file.listFiles();
			if (contents != null) {
				System.out.println("contents : " +contents);
				for (File f : contents) {	
					logger.info("Delete start"+f.toString());

					file.delete();
					deleteDir(f);
					logger.info("Delete done"+f.toString());
				}
			}
			file.delete();
		} catch (Throwable e) {
			customException("exception while deleting file in deleteDir",e);
		}
	}
	/**
	 * Exception print stack trace in log file 
	 * @param called
	 * @param e
	 */
	private void customException(String called, Throwable e){

		try {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.info(called +sw.toString());
		} catch (Throwable e1) {
			e1.printStackTrace();
		}

	}
	private void customExceptionWithFlag(String called, Throwable e){

		try {
			errorFlag = true;
			handleUtilityException(e.toString());
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.info(called +sw.toString());
		} catch (Throwable e1) {
			e1.printStackTrace();
		}

	}
	/**
	 * This method is used for checking whether document will be downloaded or not on the basis of request type and request category
	 * @param reqType
	 * @param reqCat
	 * @return
	 */
	private boolean checkReqTypeAndCat(String reqType, String reqCat) {
		boolean bResult=false;
		try {
			logger.info("inside checkReqTypeandCat method : request cat : "+reqCat +" , request type : "+reqType);
			if (!(reqType.isEmpty() && reqCat.isEmpty())) {	
				logger.info("Checking data from master for request type and request category");
				if(refToReqTypeCat!=null){
					logger.info("Value from map for req type and req cate check"+refToReqTypeCat.get(reqCat+"#"+reqType));
					if(refToReqTypeCat.get(reqCat+"#"+reqType)!=null){
						if("Y".equalsIgnoreCase(refToReqTypeCat.get(reqCat+"#"+reqType))){
							logger.info("Match found for request cat : "+reqCat +" , request type : "+reqType );
							bResult= true;
						}
					}
				}
			}
		} catch (Throwable e) {
			customException("Exception in checkReqTypeandCat",e);
		}
		return bResult;

	}
	/**
	 * To Find latest document index
	 * @param xml
	 */
	private void getMaxDocOrder(String xml){
		HashMap<Integer,String> docDtMap =null;
		List<Integer> docOrder = null;
		try {
			if(xml.indexOf("Documents")>0){
				docDtMap = new HashMap<Integer,String>();
				docOrder = new ArrayList<Integer>();
				WFXmlResponse xmlResponse = new WFXmlResponse(xml);			
				WFXmlList lWfXml = xmlResponse.createList("Documents","Document");				
				for (int k = 0; lWfXml.hasMoreElements(true); lWfXml.skip(true), k++){
					if(documentName.equalsIgnoreCase(lWfXml.getVal("DocumentName"))
							&& !"zip".equalsIgnoreCase(lWfXml.getVal("CreatedByAppName"))
							&& !lWfXml.getVal("DocOrderNo").isEmpty()){
						docOrder.add(Integer.parseInt(lWfXml.getVal("DocOrderNo")));
						docDtMap.put(Integer.parseInt(lWfXml.getVal("DocOrderNo")),lWfXml.getVal("DocumentIndex"));							
					}					
				}
			}
			if(docOrder !=null){
				int maxDocOrder = Collections.max(docOrder);
				logger.info("latest document order "+maxDocOrder + "for document index "+docDtMap.get(maxDocOrder));				
				setDocumentIndex(docDtMap.get(maxDocOrder));
			}
		} catch (Throwable e) {
			customException("exception in checkMaxDate",e);
		}finally{
			if(docOrder!=null)
				docOrder=null;
			if(docDtMap!=null)
				docDtMap=null;
		}
	}

	private void getMaxDocOrderSwift(String xml){
		HashMap<Integer,String> docDtMap =null;
		List<Integer> docOrder = null;
		try {
			if(xml.indexOf("Documents")>0){
				docDtMap = new HashMap<Integer,String>();
				docOrder = new ArrayList<Integer>();
				WFXmlResponse xmlResponse = new WFXmlResponse(xml);			
				WFXmlList lWfXml = xmlResponse.createList("Documents","Document");				
				for (int k = 0; lWfXml.hasMoreElements(true); lWfXml.skip(true), k++){
					if(swiftDocumentName.equalsIgnoreCase(lWfXml.getVal("DocumentName"))
							&& !"zip".equalsIgnoreCase(lWfXml.getVal("CreatedByAppName"))
							&& !lWfXml.getVal("DocOrderNo").isEmpty()){
						logger.info("latest document name for swift is "+lWfXml.getVal("DocumentName"));
						docOrder.add(Integer.parseInt(lWfXml.getVal("DocOrderNo")));
						docDtMap.put(Integer.parseInt(lWfXml.getVal("DocOrderNo")),lWfXml.getVal("DocumentIndex"));							
					}					
				}
			}
			if(docOrder !=null ){
				int maxDocOrder = Collections.max(docOrder);
				logger.info("latest document order "+maxDocOrder + "for document index "+docDtMap.get(maxDocOrder));				
				setSwiftDocumentIndex(docDtMap.get(maxDocOrder));
			}
		} catch (Throwable e) {
			customException("exception in checkMaxDate",e);
		}finally{
			if(docOrder!=null)
				docOrder=null;
			if(docDtMap!=null)
				docDtMap=null;
		}
	}


	private void loadMaster(){
		try {
			logger.info("Calling load master method loadMaster");
			if(bMasterFlg){
				fetchRefToActivityName();
				fetchReqTypeCatCheck();
				bMasterFlg=false;
			}
		} catch (Throwable e) {
			customException("fetchRefToActivityName...", e);
		}
	}

	private  void fetchRefToActivityName(){
		String outputXml="";
		try {
			if(refToActivityRelation==null){
				String query="SELECT UPPER(REF_TO) REF_TO ,ACTIVITYNAME FROM TFO_DJ_REF_ACTIVITY_RELATION ORDER BY 1";
				String inputXml=GenerateXml.APSelectWithColumnNames(cabinetName, query,sessionId);
				logger.info("fetchRefToActivityName inputXml "+inputXml);
				outputXml =  ExecuteXML.executeXML(inputXml);
				logger.info("fetchRefToActivityName outputXml "+outputXml);
				parseRefToActivityName(outputXml);
			}
		} catch (Throwable e) {
			customException("Exception fetchRefToActivityName",e);
		}		
	}

	private void parseRefToActivityName(String out){
		try {
			if(out.indexOf("Record")>0){
				refToActivityRelation = new HashMap<String ,String>();
				WFXmlResponse xmlResponse = new WFXmlResponse(out);	
				WFXmlList lWfXml = xmlResponse.createList("Records","Record");				
				for (int k = 0; lWfXml.hasMoreElements(true); lWfXml.skip(true), k++){	
					if(!lWfXml.getVal("REF_TO").isEmpty() 
							&& !lWfXml.getVal("ACTIVITYNAME").isEmpty() ){						
						refToActivityRelation.put(lWfXml.getVal("REF_TO").trim(), lWfXml.getVal("ACTIVITYNAME").trim());
					}				
				}
			}
		} catch (Throwable e) {
			customException("Exception in parseRefToActivityName",e);
		}
	}	
	private String modifyInputXML(String sInputXML)
	{
		return sInputXML.replace(sInputXML.substring(sInputXML.indexOf("<Password>")+10, sInputXML.indexOf("</Password>")),"*********");		
	}
	private  void fetchReqTypeCatCheck(){
		String outputXml="";
		try {
			if(refToReqTypeCat==null){
				String query="SELECT REQ_CAT,REQ_TYPE,IS_ZIP_REQ FROM TFO_DJ_ZIP_DOC ORDER BY 1";
				String inputXml=GenerateXml.APSelectWithColumnNames(cabinetName,query,sessionId);
				logger.info("fetchReqTypeCatCheck inputXml "+inputXml);
				outputXml =  ExecuteXML.executeXML(inputXml);


				logger.info("fetchReqTypeCatCheck outputXml "+outputXml);
				parseReqTypeCatCheck(outputXml);
			}
		} catch (Throwable e) {
			customException("Exception in fetchReqTypeCatCheck",e);
		}		
	}
	private void parseReqTypeCatCheck(String out) {
		try {
			if(out.indexOf("Record")>0){
				refToReqTypeCat = new HashMap<String ,String>();				
				WFXmlResponse xmlResponse = new WFXmlResponse(out);	
				WFXmlList lWfXml = xmlResponse.createList("Records","Record");				
				for (int k = 0; lWfXml.hasMoreElements(true); lWfXml.skip(true), k++){	
					if(!lWfXml.getVal("REQ_CAT").isEmpty() 
							&& !lWfXml.getVal("REQ_TYPE").isEmpty()
							&& !lWfXml.getVal("IS_ZIP_REQ").isEmpty()){						
						refToReqTypeCat.put(lWfXml.getVal("REQ_CAT").trim()+"#"+lWfXml.getVal("REQ_TYPE").trim(),lWfXml.getVal("IS_ZIP_REQ").trim());
					}
				}
			}

		} catch (Throwable e) {
			customException("Exception in parseReqTypeCatCheck",e);
		}

	}

	private void setDocumentIndex(String documentIndex) {
		this.documentIndex = documentIndex;
	}
	private String getDocumentIndex() {
		return documentIndex;
	}

	private void setSwiftDocumentIndex(String SwiftDocumentIndex) {
		this.swiftDocumentIndex = SwiftDocumentIndex;
	}
	private String getSwiftDocumentIndex() {
		return swiftDocumentIndex;
	}

	private void setCustomServiceStatus(String serviceStatus,String serviceStatusMsg,String workItemCount){
		try {
			logger.info("setCustomServiceStatus started");
			String strparamName ="'"+pid+"','"+serviceStatus+"','"+serviceStatusMsg+"','"+workItemCount+"'";
			logger.info("setCustomServiceStatus params : "+strparamName);
			sInputXML_APSelect = "<?xml version=\"1.0\"?>"
					+ "<WMTestProcedure_Input>"
					+ "<Option>APProcedure</Option>"
					+ "<SessionId>" + sessionId + "</SessionId>"                   
					+ "<ProcName>WFSetCustomServiceStatus</ProcName>"
					+ "<Params>" + strparamName + "</Params>"                    
					+ "<EngineName>" + cabinetName + "</EngineName>"
					+ "<WMTestProcedure_Input>";
			logger.info("sendEmailMulExcp Mailing-->" + sInputXML_APSelect);
			logger.info("setCustomServiceStatus input-->" + sInputXML_APSelect);
			sOutputXML_apselect =  ExecuteXML.executeXML(sInputXML_APSelect);
			logger.info("Procedure setCustomServiceStatus Output "+sOutputXML_apselect);
			logger.info("setCustomServiceStatus ends");
		} catch (Throwable e) {
			e.printStackTrace();
			logger.info("Exception:" + e.getMessage());
			logger.info("Exception:" + e.getStackTrace());
		}
	}


	private void handleUtilityException(String exception) {
		String inputXml = "";
		String outputXML = "";
		String mailBody = "";
		try {
			exception = exception.replaceAll("'", "''");
			logger.info("Inside handleLoginException");
			logger.info( "cab: "+cabinetName);
			logger.info( "session: "+sessionId);
			logger.info( "exception: "+exception);
			inputXml = GenerateXml.apInsertInputXml(cabinetName, sessionId, "BPM_UTILITY_EXCEPTION", "UTILITY_NAME,EXCEPTION_TIME,EXCEPTION_DESC", "'DocDownloadUploadMailUtility',SYSDATE,'"+exception+"'");
			logger.info( "outputXml: "+inputXml);
			outputXML = ExecuteXML.executeXML(inputXml.toString());
			logger.info( "outputXml: "+outputXML);
			mailBody = exceptionMail.replaceAll("&<UTILITYNAME>&", "DocDownloadUploadMail Utility");
			mailBody = mailBody.replaceAll("&<REASON>&", exception);
			inputXml = GenerateXml.apInsertInputXml(cabinetName,sessionId,
					"WFMAILQUEUETABLE", "MAILFROM,MAILTO,MAILCC,MAILSUBJECT,MAILMESSAGE,MAILCONTENTTYPE,ATTACHMENTISINDEX,ATTACHMENTNAMES,ATTACHMENTEXTS,MAILPRIORITY,MAILSTATUS,STATUSCOMMENTS,LOCKEDBY,SUCCESSTIME,LASTLOCKTIME,INSERTEDBY,MAILACTIONTYPE,INSERTEDTIME,PROCESSDEFID,PROCESSINSTANCEID,WORKITEMID,ACTIVITYID,NOOFTRIALS",
					"'"+exceptionFrom+"','"+exceptionTo+"','"+exceptionCC+"'" +
							",'"+exceptionSubject+"','"+mailBody+"'" +
					",'text/html;charset=UTF-8','','','',1,'N','','','','','MailUtilItyUser1','TRIGGER',SYSDATE,'10','EmailUtility','1','10','0'");
			logger.info( "outputXml: "+inputXml);
			outputXML = ExecuteXML.executeXML(inputXml.toString());
			logger.info( "outputXml: "+outputXML);
		} catch (Exception exc) {
			customException("handleLoginException MessagingException ",exc);
		}

	}

}

class SleepThread extends Log implements Runnable {

	public void run() {
		logger.info("Inside SleepThread Class");
	}
}

public class DocDownloadUploadMail extends Log{


	public DocDownloadUploadMail() {

		logger.info("Constructor called");
	}

	public static void main(String[] args) {
		try {
			initializeLogger();
			ThreadClient objTC = new ThreadClient();
			Thread objThread = new Thread(objTC);
			objThread.start();
		} catch (Throwable exception) {
			exception.printStackTrace();
			logger.info("Exception:" + exception.getMessage());
			logger.info("Exception:" + exception.getStackTrace());
		}
	}
}
