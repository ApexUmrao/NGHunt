
package com.newgen.ao.laps.calls;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.xml.parsers.ParserConfigurationException;

import jdk.internal.org.xml.sax.SAXException;

import com.newgen.ao.laps.cache.ArchivalCache;
import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.core.ICallExecutor;
import com.newgen.ao.laps.logger.ArchivalLogger;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.ExecuteXML;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.wf.util.excp.NGException;


public class ArchivalService  implements ICallExecutor, Callable<String> {
	private CallEntity callEntity;
	private String wmsSession;
	private String winame;
	private String archival_winame;
	private String sUserName;
	private String engineName;
	private String processdefid;
	private String callStatus = "Y";
	Map<String, String> attributeMap = new HashMap<String, String>();
	//private static Map<String, String> archivalProcessType;
	private  Map<String, String> archivalProcessType;
	private HashMap<String,ArrayList<String>> archivalProcessConfigMap = new HashMap<String, ArrayList<String>>();
	private String refNo;
	private String processName;
	private String responseXML;
	private static String edmsSession;
	private String processType;
	private ArrayList processConfigMapList;
	//ATP-374 19-01-2024 REYAZ
    //START CODE
	/*public static String activityname="";
	public static String extTableName="";
	public static String txnTable="";
	public static String productTable="";
	public static String sComplete="";	
	public static String batchSize="";
	public static String wiColumn="";
	public static String prevfolderindex="";
	*/
	private String activityname="";
	private String extTableName="";
	private String txnTable="";
	private String productTable="";
	private String sComplete="";	
	private String batchSize="";
	private String wiColumn="";
	private String prevfolderindex="";
	//END CODE
	
	private String batchId;
	public static String edmsCabinet=LapsConfigurations.getInstance().EDMS;
	public static String edmsUser=LapsConfigurations.getInstance().EdmsUserName;
	public static String edmsServerIp=LapsConfigurations.getInstance().EdmsServerIP;
	public static String edmsPort=LapsConfigurations.getInstance().EdmsServerPort;
	private HashMap<String,ArrayList<String>> archivalConfigMap = new HashMap<String, ArrayList<String>>();
	private ArrayList configMapList;
	
	public ArchivalService(Map<String, String> attributeMap, String sessionId, String stageId, String WI_NAME, Boolean prevStageNoGo, CallEntity callEntity){
		ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Inside ArchivalService");
		this.callEntity = callEntity;
		this.attributeMap = attributeMap;
		this.wmsSession = sessionId;
		this.engineName=LapsConfigurations.getInstance().CabinetName;
		this.winame=attributeMap.get("REQUESTOR_WI_NAME");
		this.refNo = attributeMap.get("channelRefNumber");
		this.processName = attributeMap.get("REQUESTOR_PROCESS_NAME");
		this.archival_winame =WI_NAME;
		this.edmsSession = attributeMap.get("edmsSession");
		this.processdefid= attributeMap.get("processdefid");
		sUserName = LapsConfigurations.getInstance().UserName;
		ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, "ArchivalService cunstructor called map===> " +callEntity.toString()+" Session id ===>"+sessionId);
	}

 @Override
 public String call() throws Exception {
	 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"ArchivalService call: inside");
	 try
	 {       
		
		 archivalProcessConfigMap= ArchivalCache.getInstance().getProcessConfigMap();
	     archivalProcessType = ArchivalCache.getInstance().getProcessType(processName);
	     processType=archivalProcessType.get(processName);
		 processConfigMapList = new ArrayList<String>();
		 processConfigMapList = archivalProcessConfigMap.get(processName);
		 extTableName =(String) processConfigMapList.get(0);
		 activityname =(String) processConfigMapList.get(1);
		 txnTable =(String) processConfigMapList.get(2);
		 productTable =(String) processConfigMapList.get(3);
		 sComplete =(String) processConfigMapList.get(4);
		 batchSize =(String) processConfigMapList.get(5);
		 wiColumn =(String) processConfigMapList.get(6);
		 
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Archival started for Process: "+processName);
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"processType : "+processType);
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"extTableName "+extTableName);
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"activityname "+activityname);
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"txnTable "+txnTable);
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"productTable "+productTable);
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"sComplete "+sComplete);
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"batchSize "+batchSize);

		 String outputXml ="";
			if ("".equalsIgnoreCase(archival_winame) || null == archival_winame){
				batchId = "1";
			} else {
				outputXml = APCallCreateXML.APSelect("SELECT NVL(MAX(BATCH_ID)+1,0) as BATCH_ID FROM BPM_ARCHIVAL_DETAILS WHERE WI_NAME  = '"+this.archival_winame+"'");
				XMLParser xp = new XMLParser(outputXml);
				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				if(mainCode == 0){
					batchId = xp.getValueOf("BATCH_ID");
				}
			}
		 outputXml = APCallCreateXML.APSelect("select archiveflag from " + extTableName + " where "+wiColumn+" ='" + winame + "'");
		 XMLParser xp = new XMLParser(outputXml);
		 String archiveflag ="";
		 int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		 if (mainCode == 0) {
			 archiveflag =xp.getValueOf("archiveflag");
		 }
		 if("Y".equalsIgnoreCase(archiveflag) || "Archived".equalsIgnoreCase(archiveflag)){
			 callStatus ="Z";
			 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Document already archived for this wi : "+winame);
		 } else {	 
			 if(processType.equalsIgnoreCase("Simple")){
				 archiveSimpleDocs(winame,processName);
			 } else if(processType.equalsIgnoreCase("Complex")) {
				 archiveComplexDocs(winame,processName);
			 }
		 }
		  ArchivalLogger.logMe(1, "callStatus : " + this.callStatus);
	      if ("S".equalsIgnoreCase(this.callStatus)) {
	        this.responseXML = "<returnCode>0</returnCode><errorDescription>No documents found for this workitem</errorDescription>";
	      } else if ("Z".equalsIgnoreCase(this.callStatus)) {
	        this.responseXML = "<returnCode>0</returnCode><errorDescription>Document already archived</errorDescription>";
	      } else if ("Y".equalsIgnoreCase(this.callStatus)) {
	        this.responseXML = "<returnCode>0</returnCode><errorDescription>Document Archived Successfully</errorDescription>";
	      } else if ("N".equalsIgnoreCase(this.callStatus)) {
	        this.responseXML = "<returnCode>1</returnCode><errorDescription>Document Archived failed</errorDescription>";
	      } 
	 }
	 catch(Exception e)
	 {
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"callStatus : "+callStatus);
		 responseXML= "<returnCode>1</returnCode><errorDescription>Document Archived failed</errorDescription>";
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Exception occured in the archival of Process: "+processName);
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_ERROR,e);
	 }

	 return responseXML;
 }
 
 
 public void archiveSimpleDocs(String wi_name,String processName) throws ParserConfigurationException, SAXException, IOException, Exception
 {
	 try {
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Processing started for " + wi_name);
		 String outputXml = APCallCreateXML.APSelect("select a.documentindex,a.name,a.imageindex,a.volumeid,a.noofpages,a.documenttype,"
				 + "a.documentsize,a.appname from pdbdocument a,pdbdocumentcontent b  where a.documentindex=b.documentindex and"
				 + " parentfolderindex=(select folderindex from pdbfolder where name ='" +wi_name + "') union select a.documentindex,"
				 + "a.name,a.imageindex,d.volumeid,a.noofpages,a.documenttype,a.documentsize,a.appname from pdbdocumentversion a,"
				 + "pdbdocumentcontent b,pdbdocument d  where a.documentindex=b.documentindex and b.parentfolderindex=(select "
				 + "folderindex from pdbfolder c where c.name ='" + wi_name + "') and a.documentindex=d.documentindex order by imageindex");

		 XMLParser xp = new XMLParser();
		 xp.setInputXML(outputXml);
		 int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		 int totalRetrievedCount = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
		 if (mainCode == 0) {
			 if(totalRetrievedCount>0){
				 int start = xp.getStartIndex("Records", 0, 0);
				 int deadEnd = xp.getEndIndex("Records", start, 0);
				 int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
				 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"No of Records  "+noOfFields);
				 for (int i = 0; i < noOfFields; ++i) {
					 String Record = xp.getNextValueOf("Record");
					 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Value of Record :"+Record);
					 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"No of Record :"+Record);
					 XMLParser xp1 = new XMLParser(Record); 
					 prevfolderindex = "";
					 String docName =xp1.getValueOf("name");
					 String imageindex =xp1.getValueOf("imageindex");
					 String volumeid =xp1.getValueOf("volumeid");
					 String noofpages =xp1.getValueOf("noofpages");
					 String documenttype =xp1.getValueOf("documenttype");
					 String documentsize =xp1.getValueOf("documentsize");
					 String appname =xp1.getValueOf("appname");
					 String clientId =LapsUtils.getInstance().getArchivalRefNum();
					 saveArchivalDetails(archival_winame,docName,processName,clientId,"P",  batchId,"");
					 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Archiving document " + docName);
					 outputXml = APCallCreateXML.APSelect("SELECT DOC,FOLDERPATH,DATACLASS,DATACLASSVALUE,DOCDC,PROCESSNAME from usr_0_folder_structure where doc='" + docName + "'and processname='" + processName + "'");
					 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"outputXml: " + outputXml);
					 xp1.setInputXML(outputXml);
					 mainCode = Integer.parseInt(xp1.getValueOf("MainCode"));
					 totalRetrievedCount = Integer.parseInt(xp1.getValueOf("TotalRetrieved"));
					 if (mainCode == 0) {
						 if (totalRetrievedCount==0)  //need to check
						 {
							 callStatus ="N";
							 updateArchivalDetails("F","","",clientId);
							 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Folder strcuture not found for " + docName);
						 } 
						 else 
						 {
							 String folderpath = xp1.getValueOf("FOLDERPATH");
							 String dataclassHierarchy = xp1.getValueOf("DATACLASS");
							 String DCValues = xp1.getValueOf("DATACLASSVALUE");
							 String docdc = xp1.getValueOf("DOCDC");
							 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"folderpath: " + folderpath + ", dataclassHierarchy: " + dataclassHierarchy + ", DCValues: " + DCValues + ", docdc: " + docdc);
							 String[] folder = folderpath.split("/");
							 String[] dataclass = dataclassHierarchy.split("/");
							 String folderindex = "";
							 String folder_path = "";
							 String[] DCval = DCValues.split("#");
							 String DCQUERY = "";
							 for (int j = 0; j < DCval.length; ++j)
							 {
								 String tmp;
								 if (DCval[j].contains("$"))
								 {
									 tmp = DCval[j].replace("=$", "='||");
									 tmp = tmp.replace("$", "||'#'||");
									 DCQUERY = DCQUERY + "'" + tmp + "'";
								 }
								 else
								 {
									 tmp = DCval[j] + "#'||";
									 DCQUERY = DCQUERY + "'" + tmp + "'";
								 }

							 }

							 DCQUERY = DCQUERY.replace("''", "'");
							 String query = "select " + DCQUERY.substring(0, DCQUERY.length() - 3) + " as DCVALUE from " + extTableName + " where "+wiColumn+" ='" + wi_name + "'";
							 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"query:: " + query);
							 outputXml = APCallCreateXML.APSelect("select " + DCQUERY.substring(0, DCQUERY.length() - 3) + " as DCVALUE from " + extTableName + " where "+wiColumn+" ='" + wi_name + "'");
							 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"outputXml:: " + outputXml);
							 xp1.setInputXML(outputXml);
							 mainCode = Integer.parseInt(xp1.getValueOf("MainCode"));
							 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"MainCode ## " + mainCode);
							 if (mainCode == 0) {
								 DCValues =xp1.getValueOf("DCVALUE");
							 }
							 for (int j = 0; j < folder.length; ++j)
							 {
								 String val;
								 if (folder[j].contains("$"))
								 {
									 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Inside folder[j].contains('$')");
									 val = folder[j].replace("$", " ");
									 outputXml = APCallCreateXML.APSelect("select " + val + " as VAL from " + extTableName + " where "+wiColumn+" ='" + wi_name + "'");
									 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"outputXml::: " + outputXml);
									 xp1.setInputXML(outputXml);
									 mainCode = Integer.parseInt(xp1.getValueOf("MainCode"));
									 if (mainCode == 0) {
										 folder[j] =xp1.getValueOf("VAL");
									 } 
								 }
								 folder[j] = folder[j].replaceAll("\n", "").trim();
								 if (folder[j].equalsIgnoreCase(""))
								 {
									 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Skipping for null folder");
									 System.out.println("Skipping for null folder");
								 }
								 else
								 {
									 folder_path = folder_path + folder[j] + "/";

									 if (dataclass[j].contains("$"))
									 {
										 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"dataclass[j].contains('$')");
										 val = dataclass[j].replace("$", " ");
										 outputXml = APCallCreateXML.APSelect("select " + val + " as VAL from " + extTableName + " where "+wiColumn+" ='" + wi_name + "'");
										 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"outputXml:::: " + outputXml);
										 XMLParser xp2 =new XMLParser(outputXml);
										 dataclass[j] = xp2.getValueOf("VAL");
									 }
									 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Folder creation starts for " + folder_path);
									 folderindex = createFolder(folder_path.substring(0, folder_path.length() - 1),dataclass[j], DCValues); }
							 }
							 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Created Folder Path: " + folder_path);

							 if (docdc.contains("$"))
							 {
								 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"docdc.contains('$')");
								 String val = docdc.replace("$", " ");
								 outputXml = APCallCreateXML.APSelect("select " + val + " as VAL from " + extTableName + " where "+wiColumn+" ='" + wi_name + "'");
								 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"outputXml::::: " + outputXml);
								 xp1.setInputXML(outputXml);
								 docdc = xp1.getValueOf("VAL");
							 }

							 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"LG DATACLASS DATACLASS folder" + docdc);
							 String DocumentIndex = uploadDocument(docName, imageindex + "#" + volumeid + "#", folderindex, noofpages, documenttype, documentsize, appname, docdc, DCValues);
							 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Doument Uploaded: " + DocumentIndex);
							 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Uploaded Document Name " + docName);
							 if(!"".equalsIgnoreCase(DocumentIndex)){
								 updateArchivalDetails("S",folder_path,DocumentIndex,clientId);
							 } else{
								 updateArchivalDetails("F",folder_path,DocumentIndex,clientId);
							 }

						 }
					 }
				 }
			 } else {
				 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"No documents found for this workitem");
			 }
		 }
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Complete flag: " + sComplete);
		 if (sComplete.equalsIgnoreCase("Y"))
		 {
			 int workitemid =1;
			 String sQuery=APCallCreateXML.APSelect("select workitemid from wfinstrumenttable where processinstanceid ='"+wi_name+"'");
			 xp.setInputXML(sQuery);
			 totalRetrievedCount = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
			 if (mainCode == 0) {
				 if(totalRetrievedCount>0){
					 workitemid =Integer.parseInt(xp.getValueOf("workitemid"));
				 }
			 }
			 ProdCreateXML.WMCompleteWorkItem(wmsSession, wi_name, workitemid);
			 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Workitem " + wi_name + " has been moved to next workstep");
		 }
		 if (!"N".equalsIgnoreCase(this.callStatus)) {
			 APCallCreateXML.APUpdate(extTableName,"archiveflag","'Y'",""+wiColumn+" ='" + wi_name + "'",wmsSession);
			 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Archival flag updated");
			 ProdCreateXML.WMCompleteWorkItem(wmsSession, archival_winame, 1);
		 }
	 }
	 catch (Exception e)
	 {
		 callStatus="N";
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_ERROR,"[archiveDocs]" +"Exception in archiveDocs:"+e);	
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Archival failed for workitem: " + wi_name);
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Updating archival flag for error case");
		 try
		 {
			 String archiveflag = "";
			 String outputXml = APCallCreateXML.APSelect("select archiveflag from " + extTableName + " where "+wiColumn+" ='" + wi_name + "'");
			 XMLParser xp1 = new XMLParser();
			 xp1.setInputXML(outputXml);
			 String flag ="";
			 int mainCode = Integer.parseInt(xp1.getValueOf("MainCode"));
			 int totalRetrievedCount = Integer.parseInt(xp1.getValueOf("TotalRetrieved"));
			 if (mainCode == 0) {
				 if(totalRetrievedCount>0){
					 flag =xp1.getValueOf("archiveflag"); 
				 } else {
					 flag ="0";
				 }
			 }
			 switch (Integer.parseInt(flag))
			 {
			 case 0:
				 archiveflag = "1";
				 break;
			 case 1:
				 archiveflag = "2";
				 break;
			 case 2:
				 archiveflag = "3";
				 break;
			 case 3:
				 archiveflag = "E";
			 }
			 APCallCreateXML.APUpdate(extTableName,"archiveflag",archiveflag,""+wiColumn+"='" + wi_name + "'",wmsSession);
		 }
		 catch (Exception ex)
		 {
			 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_ERROR,"[archiveDocs]" +"Exception Exception in updating error status: ex:"+ex);	
		 }
		 throw e;
	 }
 }
 
 public void archiveComplexDocs(String wi_name,String processName) throws ParserConfigurationException, SAXException, IOException, Exception
 {
	 try {
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Inside archiveComplexDocs");
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Processing started for " + wi_name);
		 String count="";
		 String outputXml =  APCallCreateXML.APSelect("select count(1) as count from " + txnTable + " where wi_name='" + wi_name + "'");
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"archiveDocs  " + outputXml);
		 XMLParser xp = new XMLParser();
		 xp.setInputXML(outputXml);
		 int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		 int totalRetrievedCount = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
		 if (mainCode == 0) {
			 if(totalRetrievedCount>0){ 
				 count = xp.getValueOf("count");
				 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"archiveDocs count " + count);
			 }
		 }
		 if (count.equalsIgnoreCase("1"))
		 {
			 archieveDocumentsSingle(wi_name, edmsSession, wmsSession, processName);
			 return;
		 }
		 archieveDocumentsJoint(wi_name, edmsSession, wmsSession, processName);
	 }
	 catch(Exception e)
	 {
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_ERROR,"Excepiton in archiveDocs for workitem: "+e);
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Archival failed for workitem: "+wi_name);
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Updating archival flag for error case");
		 try
		 {
			 String archiveflag="";
			 String flag="";
			 String outputXml =  APCallCreateXML.APSelect("select archiveflag from "+extTableName+" where wi_name='"+wi_name+"'");
			 XMLParser xp = new XMLParser();
			 xp.setInputXML(outputXml);
			 int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			 int totalRetrievedCount = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
			 if (mainCode == 0) {
				 if(totalRetrievedCount>0){ 
					 flag = xp.getValueOf("archiveflag");
				 } 
				 else {
					 flag ="0"; 
				 }
			 }
			 switch(Integer.parseInt(flag))
			 {
			 case 0:		archiveflag="1";	
			 break;
			 case 1:		archiveflag="2";
			 break;
			 case 2:		archiveflag="3";
			 break;
			 case 3:		archiveflag="E";
			 break;
			 }
			 APCallCreateXML.APUpdate(extTableName,"archiveflag",archiveflag,"wi_name='" + wi_name + "'",edmsSession);
		 }
		 catch(Exception ex)
		 {
			 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_ERROR,"Exception in updating error status: "+ex);
		 }
		 throw e;
	 }

 }
 
 
 public String createFolder(String folderpath,String DataClass,String DCVal) throws Exception
	{
	    ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, "Inside createFolder");
		String[] Folder=folderpath.split("/");
		String Query="select folderindex from pdbfolder where upper(name)=upper('"+ Folder[Folder.length-1].replaceAll("'", "''") +"') and location<>'T' ";
		if (prevfolderindex.equalsIgnoreCase("")){
			Query=Query+" and parentfolderindex='0'";
		}
		else {
			Query=Query+" and parentfolderindex='"+prevfolderindex+"'";
		}
		ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Query  : "+Query);
		String folderindex="";
		try 
		{ 
			String output =APCallCreateXML.APSelectEdms(Query);
			ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"output  : "+output);
			XMLParser xp1 = new XMLParser();
			xp1.setInputXML(output);
		    int mainCode = Integer.parseInt(xp1.getValueOf("MainCode"));
	        int totalRetrievedCount = Integer.parseInt(xp1.getValueOf("TotalRetrieved"));
		    if (mainCode == 0) {
			  if(totalRetrievedCount>0){
				  folderindex =xp1.getValueOf("folderindex"); 
			    }
		    }
			ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO," before replace folderindex  : "+folderindex);
			folderindex=folderindex.replaceAll("\r","");
			folderindex=folderindex.replaceAll("\n","");
			folderindex=folderindex.replaceAll("\t","");
			ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO," After replace folderindex  : "+folderindex);
			if(folderindex.equalsIgnoreCase(""))
			{
				Query="<?xml version=\"1.0\"?><NGOAddFolder_Input><Comment>ByP@$$M@kerChecker</Comment>" +
						"<Option>NGOAddFolder</Option><CabinetName>"+edmsCabinet+"</CabinetName>" +
						"<UserDBId>"+ edmsSession +"</UserDBId><Folder>" +
						"<ParentFolderIndex>"+ prevfolderindex +"</ParentFolderIndex>" +
						"<FolderName>"+Folder[Folder.length-1]+"</FolderName>" +
						"<CreationDateTime></CreationDateTime>" +
						"<AccessType>I</AccessType><ImageVolumeIndex></ImageVolumeIndex>" +
						"<FolderType>G</FolderType><Location>G</Location>" +
						"<Comment>User Folder</Comment><EnableFTSFlag>Y</EnableFTSFlag>" +
						"<NoOfDocuments>0</NoOfDocuments>" +
						"<NoOfSubFolders>0</NoOfSubFolders>" +
						"<DataDefinition></DataDefinition></Folder>" +
						"</NGOAddFolder_Input>";
				String outputXML = ExecuteXML.executeXMLEdms(Query,edmsServerIp,edmsPort);
				ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"outputXML  : "+outputXML);
				xp1.setInputXML(outputXML);			
				folderindex=xp1.getValueOf("FolderIndex");
			}
				ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Folder index: "+folderindex);
			if(folderindex.equalsIgnoreCase(""))
				throw new Exception("Folder Creation failed");
				ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Folder "+Folder[Folder.length-1]+" created with folderindex: "+folderindex);
			if(!DataClass.equalsIgnoreCase("null"))
			{
				boolean t=associateDataclass(folderindex,DataClass,DCVal);
				if(t==true)
						ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Dataclass "+DataClass+" Associated to folderindex: "+folderindex);
				else
					throw new Exception("Data class association failed for folderindex: "+folderindex);
			}

			prevfolderindex=folderindex;
		} 
		catch (Exception e) 
		{
			ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_ERROR,"[createFolder]" +"Exception in createFolder e:"+e);		
			prevfolderindex=folderindex;
			throw e;
		}

		return folderindex;
	}
 
	public  String uploadDocument(String docname,String docindex,String folderind,String nopages,String doctype,String docsize,String appname,String DCname,String DCValues) throws Exception
	{
		ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Inside uploadDocument");
		String Query="";
		String documentindex="";
		Query="select a.documentindex from pdbdocument a,pdbdocumentcontent b where a.documentindex=b.documentindex and parentfolderindex="+folderind+" and a.name='"+docname+"'";
		ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Query  : "+Query);
		try
		{
			String out = APCallCreateXML.APSelectEdms(Query);
			XMLParser xp= new XMLParser();
			xp.setInputXML(out);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			int totalRetrievedCount = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
			if (mainCode == 0) {
			    if(totalRetrievedCount==0){
			    	Query="<?xml version=\"1.0\"?><NGOAddDocument_Input><BypassHookTag></BypassHookTag>" +
						"<Option>NGOAddDocument</Option><CabinetName>"+edmsCabinet+"</CabinetName>" +
						"<UserDBId>"+edmsSession+"</UserDBId><GroupIndex>0</GroupIndex>" +
						"<Document><ParentFolderIndex>"+folderind+"</ParentFolderIndex>" +
						"<NoOfPages>"+nopages+"</NoOfPages><AccessType>I</AccessType>" +
						"<DocumentName>"+docname+"</DocumentName>" +
						"<CreationDateTime></CreationDateTime>" +
						"<DocumentType>"+doctype+"</DocumentType>" +
						"<DocumentSize>"+docsize+"</DocumentSize>" +
						"<CreatedByAppName>"+appname+"</CreatedByAppName>" +
						"<ISIndex>"+docindex+"</ISIndex>" +
						"<ODMADocumentIndex></ODMADocumentIndex>" +
						"<Comment></Comment>" +
						"<EnableLog>Y</EnableLog>" +
						"<FTSFlag>PP</FTSFlag>" +
						"<DataDefinition></DataDefinition>" +
						"<Keywords></Keywords></Document>" +
						"</NGOAddDocument_Input>";
			  
				    	String outputXML = ExecuteXML.executeXMLEdms(Query,edmsServerIp,edmsPort);
						xp.setInputXML(outputXML);			
						documentindex=xp.getValueOf("DocumentIndex");
			       }
					else
					{
						documentindex=chekincheckout(docindex, docsize, doctype, nopages, appname, folderind,docname);
		
					}
			}
			if(!documentindex.equalsIgnoreCase(""))
			{
				associateDataClassDoc(documentindex,DCname,DCValues);
				ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"associated data class");
			}
			else
			{
				callStatus="N";
				ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Problem in Document Addition");
				throw new Exception("Document addition failed");
			}
		}
		catch(Exception e)
		{
			ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_ERROR,"Exception in uploadDocument: "+e);
			throw e;
		}
	  return documentindex;
  }
	
 public static boolean associateDataclass(String folderindex,String DCname,String DCVal) throws NumberFormatException, IOException, Exception
 {
	 String dataclassindex ="";
	 String output =APCallCreateXML.APSelectEdms("select datadefindex from pdbdatadefinition where upper(datadefname)=upper('"+ DCname +"')");
	 XMLParser xp1 = new XMLParser();
	 xp1.setInputXML(output);
	 int mainCode = Integer.parseInt(xp1.getValueOf("MainCode"));
	 int totalRetrievedCount = Integer.parseInt(xp1.getValueOf("TotalRetrieved"));
	 if (mainCode == 0) {
		 if(totalRetrievedCount>0){
			 dataclassindex =xp1.getValueOf("datadefindex"); 
		 }
	 }
	 String dataclassxml=APCallCreateXML.APSelectEdms("select b.datafieldindex as IndexId,datafieldtype as IndexType,"
			 + "upper(datafieldname) as IndexValue from pdbdatafieldstable a,pdbglobalindex b where a.datafieldindex = "
			 + "b.datafieldindex and datadefindex="+dataclassindex+"");
	 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"associateDataclass Query Output: "+dataclassxml);
	 String datafieldname ="";
	 String temp="";
	 xp1.setInputXML(dataclassxml);
	 mainCode = Integer.parseInt(xp1.getValueOf("MainCode"));
	 totalRetrievedCount = Integer.parseInt(xp1.getValueOf("TotalRetrieved"));
	 if (mainCode == 0) {
		 if (totalRetrievedCount > 0) {
			 int start = xp1.getStartIndex("Records", 0, 0);
			 int deadEnd = xp1.getEndIndex("Records", start, 0);
			 int noOfFields = xp1.getNoOfFields("Record", start, deadEnd);
			 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,
					 "No of Records  " + noOfFields);
			 for (int i = 0; i < noOfFields; ++i) {
				 String Record = xp1.getNextValueOf("Record");
				 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,
						 "No of Record :" + Record);
				 XMLParser xp2 = new XMLParser(Record);
				 datafieldname = xp2.getValueOf("IndexValue");
				 temp = temp + datafieldname + ",";
			 }
		 }
	 }

	 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"associateDataclass dataclassxml1: "+dataclassxml);
	 dataclassxml=dataclassxml.replace("<Record>", "<Field>");
	 dataclassxml=dataclassxml.replace("</Record>", "</Field>");
	 dataclassxml=dataclassxml.replace("<tr>", "");
	 dataclassxml=dataclassxml.replace("</tr>", "");
	 dataclassxml=dataclassxml.replace("\n", "");
	 dataclassxml=dataclassxml.replace("\r", "");
	 dataclassxml=dataclassxml.replace("\t", "");    	 
	 dataclassxml=dataclassxml.substring(dataclassxml.indexOf("<Records>")+9,dataclassxml.indexOf("</Records>"));

	 String temp_array[]=temp.split(",");
	 String[] dcvalues=DCVal.split("#");   
	 for (int i = 0; i < dcvalues.length; ++i) {
		 if (dcvalues[i].equalsIgnoreCase("")) {
			 continue;
		 }
		 String[] val = dcvalues[i].split("=");
		 if (val.length == 1) {
			 dataclassxml = dataclassxml.replace(val[0].toUpperCase(), "");
		 } else {
			 dataclassxml = dataclassxml.replace(val[0].toUpperCase(),val[1]);
		 }
	 }

	 for(int i=0;i<temp_array.length;i++)
	 {
		 dataclassxml=dataclassxml.replaceAll(temp_array[i], "");
	 }

	 dataclassxml="<?xml version=\"1.0\"?><NGOChangeFolderProperty_Input><Comment>ByP@$$M@kerChecker</Comment>" +
			 "<Option>NGOChangeFolderProperty</Option><CabinetName>"+edmsCabinet+"</CabinetName>" +
			 "<UserDBId>"+ edmsSession +"</UserDBId><Folder><FolderIndex>"+folderindex+"</FolderIndex>" +
			 "<NameLength>255</NameLength><VersionFlag></VersionFlag>" +
			 "<DataDefinition><DataDefIndex>"+dataclassindex+"</DataDefIndex>" +
			 "<Fields>"+dataclassxml+"</Fields></DataDefinition></Folder></NGOChangeFolderProperty_Input>";
	 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"associateDataclass dataclassxml2: "+dataclassxml);
	 String retval=ExecuteXML.executeXMLEdms(dataclassxml,edmsServerIp,edmsPort);

	 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"associateDataclass retval: "+retval);
	 if(retval.contains("<Status>0</Status>")){
		 return true;
	 }
	 else{
		 return false;
	 }
 }
	
	
 public static String chekincheckout(String docindex,String docsize,String doctype,String nopages,String appname,String folderindex,String docname) throws Exception
 {
	 String output="";
	 String old_documentindex = "";
	 String old_volumeid = "";
	 String old_imageindex ="";
	 String old_versionnumber = "";
	 String old_ParentFolderIndex = "";
	 String old_owner = "";
	 String old_createdbyuser = "";
	 String old_noofpages = "";
	 String old_appname = "";
	 String old_documentsize = "";
	 String old_ftsflag = "";
	 String old_printflag = "";
	 String old_doctype = "";
	 try
	 {
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"CheckOutCheckIn call started");
		 output =APCallCreateXML.APSelectEdms("select a.documentindex,a.volumeid,a.imageindex,a.versionnumber,b.ParentFolderIndex,a.owner,a.createdbyuser,"
				 + " a.NOOFPAGES,a.appname,a.DocumentSize, a.FTSFlag, a.PullPrintFlag, a.DocumentType from pdbdocument a,pdbdocumentcontent b where "
				 + "a.documentindex=b.documentindex and parentfolderindex=" + folderindex + " and name='" + docname + "' and rownum=1");
		 XMLParser xp =new XMLParser();
		 xp.setInputXML(output);
		 int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		 int totalRetrievedCount = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
		 if (mainCode == 0) {
			 if(totalRetrievedCount>0){
				 old_documentindex =xp.getValueOf("documentindex"); 
				 old_volumeid =xp.getValueOf("volumeid"); 
				 old_imageindex =xp.getValueOf("imageindex"); 
				 old_versionnumber =xp.getValueOf("versionnumber"); 
				 old_ParentFolderIndex =xp.getValueOf("ParentFolderIndex"); 
				 old_owner =xp.getValueOf("owner"); 
				 old_createdbyuser =xp.getValueOf("createdbyuser"); 
				 old_noofpages =xp.getValueOf("NOOFPAGES"); 
				 old_appname =xp.getValueOf("appname"); 
				 old_documentsize =xp.getValueOf("DocumentSize"); 
				 old_ftsflag =xp.getValueOf("FTSFlag"); 
				 old_printflag =xp.getValueOf("PullPrintFlag"); 
				 old_doctype =xp.getValueOf("DocumentType"); 
			 }
		 }
		 String new_imageindex = docindex.split("#")[0];
		 String new_volid = docindex.split("#")[1];

		 if (old_imageindex.equalsIgnoreCase(new_imageindex))
		 {
			 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Document with same imageindex already exits. So bypassing this document");
			 return old_documentindex;
		 }

		 Date today_date = new Date();
		 SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		 String today = sdf.format(today_date);
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Todays date: " + today);
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"old_versionnumber: " + old_versionnumber);

		 String ColName = "DocumentIndex ,ParentFolderIndex, VersionNumber,VersionComment, CreatedDateTime, Name, Owner, CreatedByUserIndex,ImageIndex, VolumeIndex, NoOfPages, LockFlag, LockByUser, AppName, DocumentSize, FTSFlag, PullPrintFlag, DocumentType, lockmessage";

		 String Values = "'" + old_documentindex + "','" + old_ParentFolderIndex + "','" + old_versionnumber + "','','" + today + "','" + docname + "','" + old_owner + "','" + old_createdbyuser + "','" + old_imageindex + "','" + old_volumeid + "','" + old_noofpages + "','','','" + old_appname + "','" + old_documentsize + "','" + old_ftsflag + "','" + old_printflag + "','" + old_doctype + "',''";
		 APCallCreateXML.APInsertEdms("pdbdocumentversion", ColName, Values, edmsSession,edmsServerIp,edmsPort);
		 if (old_versionnumber.contains(".")) {
			 old_versionnumber = old_versionnumber.substring(0, old_versionnumber.indexOf("."));
		 }
		 int new_versionnumber = Integer.parseInt(old_versionnumber) + 1;
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"New versionnumber: " + new_versionnumber);
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"UserName : " + edmsUser.toUpperCase());
		 output = APCallCreateXML.APSelectEdms("select userindex from pdbuser where upper(username)='" + edmsUser.toUpperCase() + "'");
		 xp.setInputXML(output);
		 mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		 totalRetrievedCount = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
		 String new_owner ="";
		 if (mainCode == 0) {
			 if(totalRetrievedCount>0){
				 new_owner =xp.getValueOf("userindex"); 
			 }
		 }
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"new_owner: " + new_owner + ", new_imageindex: " + new_imageindex + ",new_volid: " + new_volid);
		 String columnName = "RevisedDateTime,AccessedDateTime,DocumentType,ImageIndex,VolumeId,NoOfPages,DocumentSize,CheckOutStatus,CheckOutByUser,versionnumber,AppName,DocumentLock,LockMessage";
		 String strValues = "'" + today + "','" + today + "','" + doctype + "','" + new_imageindex + "','" + new_volid + "','" + nopages + "','" + docsize + "','N','0','" + new_versionnumber + "','" + appname + "','N',''";
		 String sWhere = "documentindex='" + old_documentindex + "'";

		 APCallCreateXML.APUpdateEdms("pdbdocument",columnName,strValues,sWhere,edmsSession,edmsServerIp,edmsPort);
		 output = old_documentindex;
	 }
	 catch(Exception e)
	 {
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_ERROR,"Exception in check in check out: "+e);
		 throw e;
	 }
	 return output;

 }
	
 public  void associateDataClassDoc(String docindex,String DCname,String DCVal) throws Exception
 {
	 String dataclassindex ="";
	 String Query=APCallCreateXML.APSelectEdms("select datadefindex from pdbdatadefinition where upper(datadefname)=upper('"+ DCname +"')");
	 XMLParser xp1 = new XMLParser();
	 xp1.setInputXML(Query);
	 int mainCode = Integer.parseInt(xp1.getValueOf("MainCode"));
	 int totalRetrievedCount = Integer.parseInt(xp1.getValueOf("TotalRetrieved"));
	 if (mainCode == 0) {
		 if(totalRetrievedCount>0){
			 dataclassindex =xp1.getValueOf("datadefindex"); 
		 }
	 }
	 try
	 {
		 String dataclassxml=APCallCreateXML.APSelectEdms("select b.datafieldindex as IndexId,datafieldtype as IndexType,upper(datafieldname) as IndexValue " +
				 " from pdbdatafieldstable a,pdbglobalindex b where a.datafieldindex=b.datafieldindex"+
				 " and datadefindex='"+dataclassindex+"'");
		 String datafieldname ="";
		 String temp="";
		 xp1.setInputXML(dataclassxml);
		 mainCode = Integer.parseInt(xp1.getValueOf("MainCode"));
		 totalRetrievedCount = Integer.parseInt(xp1.getValueOf("TotalRetrieved"));
		 if (mainCode == 0) {
			 if(totalRetrievedCount>0){
				 int start = xp1.getStartIndex("Records", 0, 0);
				 int deadEnd = xp1.getEndIndex("Records", start, 0);
				 int noOfFields = xp1.getNoOfFields("Record", start, deadEnd);
				 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"No of Records  "+noOfFields);
				 for (int i = 0; i < noOfFields; ++i) {
					 String Record = xp1.getNextValueOf("Record");
					 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"No of Record :"+Record);
					 XMLParser xp2 = new XMLParser(Record); 
					 datafieldname =xp2.getValueOf("IndexValue");
					 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"datafieldname :"+datafieldname);
					 temp = temp+datafieldname+",";
				 }
			 }
		 }
		 dataclassxml=dataclassxml.replace("<Record>", "<Field>");
		 dataclassxml=dataclassxml.replace("</Record>", "</Field>");
		 dataclassxml=dataclassxml.replace("<tr>", "");
		 dataclassxml=dataclassxml.replace("</tr>", "");
		 dataclassxml=dataclassxml.replace("\n", "");
		 dataclassxml=dataclassxml.replace("\r", "");
		 dataclassxml=dataclassxml.replace("\t", "");    	 
		 dataclassxml=dataclassxml.substring(dataclassxml.indexOf("<Records>")+9,dataclassxml.indexOf("</Records>"));
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"temp :"+temp);
		 String temp_array[]=temp.split(",");

		 String[] dcvalues=DCVal.split("#");
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"DCVal dataclassxml :"+DCVal);
		 for(int i=0;i<dcvalues.length;i++)
		 {
			 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"DCVal dataclassxml :"+dcvalues[i]);
			 String[] val=dcvalues[i].split("=");
			 if(val.length==1)
			 {
				 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"If After dataclassxml :"+dataclassxml);
				 dataclassxml=dataclassxml.replace(val[0].toUpperCase(), "");
				 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"If Before dataclassxml :"+dataclassxml);
			 }
			 else	
			 {
				 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Else after dataclassxml :"+dataclassxml);
				 dataclassxml=dataclassxml.replace(val[0].toUpperCase(), val[1]);
				 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Else before dataclassxml :"+dataclassxml);
			 }

		 }

		 for(int i=0;i<temp_array.length;i++)
		 {
			 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"temp_array[i] :"+temp_array[i]);
			 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"dataclassxml :"+dataclassxml);
			 dataclassxml=dataclassxml.replaceAll(temp_array[i], "");
		 }

		 dataclassxml="<?xml version=\"1.0\"?><NGOChangeDocumentProperty_Input><Comment>ByP@$$M@kerChecker</Comment>" +
				 "<Option>NGOChangeDocumentProperty</Option><CabinetName>"+edmsCabinet+"</CabinetName>" +
				 "<UserDBId>"+edmsSession+"</UserDBId><GroupIndex>0</GroupIndex><Document>" +
				 "<DocumentIndex>"+docindex.split("#")[0]+"</DocumentIndex>" +
				 "<DataDefinition><DataDefName>"+DCname+"</DataDefName>" +
				 "<Fields>"+dataclassxml+"</Fields></DataDefinition>" +
				 "</Document></NGOChangeDocumentProperty_Input>";
		 ExecuteXML.executeXMLEdms(dataclassxml,edmsServerIp,edmsPort);

	 }
	 catch(Exception e)
	 {
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_ERROR,"Exception in associateDataClassDoc: "+e);
		 throw e;
	 }

 }
	
	
 public void archieveDocumentsSingle(String wi_name, String edmsSession, String wmsSession,String processName) throws Exception
 {
	 try
	 {
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Inside archieveDocumentsSingle");
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Processing started for " + wi_name);
		 String outputXml = APCallCreateXML.APSelect("select a.documentindex,name,imageindex,volumeid,noofpages,documenttype,documentsize,appname  from pdbdocument a,pdbdocumentcontent b where a.documentindex=b.documentindex and b.parentfolderindex=(select folderindex from pdbfolder where name='" + 
				 wi_name + "')");
		 XMLParser xp = new XMLParser();
		 xp.setInputXML(outputXml);
		 int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		 int totalRetrievedCount = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
		 if (mainCode == 0) {
			 if(totalRetrievedCount>0){
				 int start = xp.getStartIndex("Records", 0, 0);
				 int deadEnd = xp.getEndIndex("Records", start, 0);
				 int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
				 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"No of Records  "+noOfFields);
				 for (int i = 0; i < noOfFields; ++i) {
					 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"i :"+i);
					 String Record = xp.getNextValueOf("Record");
					 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Data :"+Record);
					 XMLParser xp1 = new XMLParser(Record); 
					 prevfolderindex = "";
					 String docName =xp1.getValueOf("name");
					 String imageindex =xp1.getValueOf("imageindex");
					 String volumeid =xp1.getValueOf("volumeid");
					 String noofpages =xp1.getValueOf("noofpages");
					 String documenttype =xp1.getValueOf("documenttype");
					 String documentsize =xp1.getValueOf("documentsize");
					 String appname =xp1.getValueOf("appname");
					 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Archiving document " + docName);
					 String clientId =LapsUtils.getInstance().getArchivalRefNum();
					 saveArchivalDetails(archival_winame,docName,processName,clientId,"P",  batchId,"");
					 outputXml = APCallCreateXML.APSelect("SELECT DOC,FOLDERPATH,DATACLASS,DATACLASSVALUE,DOCDC,PROCESSNAME from usr_0_folder_structure where doc='" + docName + "'and processname='" + processName + "'");
					 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO," single outputXml for " + outputXml);
					 xp1.setInputXML(outputXml);
					 mainCode = Integer.parseInt(xp1.getValueOf("MainCode"));
					 totalRetrievedCount = Integer.parseInt(xp1.getValueOf("TotalRetrieved"));
					 if (mainCode == 0) {
						 if (totalRetrievedCount==0)  //need to check
						 {
							 callStatus ="N";
							 updateArchivalDetails("F","","",clientId);
							 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Folder strcuture not found for " + docName);
						 } else {
							 String Query;
							 String folderpath =xp1.getValueOf("FOLDERPATH");
							 String dataclassHierarchy = xp1.getValueOf("DATACLASS");
							 String DCValues = xp1.getValueOf("DATACLASSVALUE");
							 String docdc = xp1.getValueOf("DOCDC");
							 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"folderpath: " + folderpath + ", dataclassHierarchy: " + dataclassHierarchy + ", DCValues: " + DCValues + ", docdc: " + docdc);
							 String[] folder = folderpath.split("/");
							 String[] dataclass = dataclassHierarchy.split("/");
							 String folderindex = "";
							 String folder_path = "";
							 String[] DCval = DCValues.split("#");
							 String DCQUERY = "";
							 for (int j = 0; j < DCval.length; ++j)
							 {
								 String tmp;
								 if (DCval[j].contains("$"))
								 {
									 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"DCval[j].contains('$')  "+j);
									 tmp = DCval[j].replace("=$", "='||");
									 tmp = tmp.replace("$", "||'#'||");
									 DCQUERY = DCQUERY + "'" + tmp + "'";
								 }
								 else
								 {
									 tmp = DCval[j] + "#'||";
									 DCQUERY = DCQUERY + "'" + tmp + "'";
								 }
							 }
							 DCQUERY = DCQUERY.replace("''", "'");
							 boolean acc_case = false;
							 for (int j = 0; j < folder.length; ++j)
							 {
								 if (folder[j].contains("acc_no"))
									 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"folder[j].contains('acc_no') "+j);
									 acc_case = true;
							 }
							 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"acc_case "+acc_case);
							 if (acc_case)
							 {
								 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Inside If case of archieveDocumentsSingle acc_case");
								 Query = getConfigMapQuery("CustInfo", processName);
								 String[] Query1 = Query.split("$$$");
								 Query =Query1[0];
								 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Query returned from readComplexXml: " + Query);
								 Query = Query.replaceAll("##", "'" + wi_name + "'");
								 String cust_acc = APCallCreateXML.APSelect(Query);
								 xp1.setInputXML(cust_acc);
								 mainCode = Integer.parseInt(xp1.getValueOf("MainCode"));
								 totalRetrievedCount = Integer.parseInt(xp1.getValueOf("TotalRetrieved"));
								 if (mainCode == 0) {
									 if(totalRetrievedCount>0){
										int start1 = xp1.getStartIndex("Records", 0, 0);
										int deadEnd1 = xp1.getEndIndex("Records", start1, 0);
										int noOfFields1 = xp1.getNoOfFields("Record", start1, deadEnd1);
										 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"No of noOfFields1 Records  "+noOfFields1);
										 for (int k = 0; k < noOfFields1; ++k) { 
											 Record = xp1.getNextValueOf("Record");
											 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"No of Record :"+Record);
											 XMLParser xp2 = new XMLParser(Record);
											 String tmpAcc =xp2.getValueOf("cust_id");
											 String tmpAcc1 =xp2.getValueOf("acc_no");
											 prevfolderindex = "";

											 Query = APCallCreateXML.APSelect("select " + DCQUERY.substring(0, DCQUERY.length() - 3) + " as DCVAL from " + txnTable + "," + extTableName + "," + productTable + " where " + txnTable + ".wi_name=" + extTableName + ".wi_name " + 
													 " and " + extTableName + ".wi_name='" + wi_name + "' and " + productTable + ".wi_name(+)=" + extTableName + ".wi_name and " + productTable + ".acc_no(+)='" + tmpAcc1 + "'");
											 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Inside Query: "+Query);
											 xp2.setInputXML(Query);
											 mainCode = Integer.parseInt(xp2.getValueOf("MainCode"));
											 totalRetrievedCount = Integer.parseInt(xp2.getValueOf("TotalRetrieved"));
											 if (mainCode == 0) {
												 if(totalRetrievedCount>0){
													 DCValues =xp2.getValueOf("DCVAL");
												 }
											 }
											 folder_path = "";
											 prevfolderindex = "";

											 String tmpReplcepath = "";
											 for (int j = 0; j < folder.length; ++j)
											 {
												 if (folder[j].contains("$"))
												 {
													 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"folder[j].contains('$') "+j);
													 String val = folder[j].replace("$", " ");
													 if (val.contains("cust_id"))
													 {
														 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"val @@ "+val);
														 tmpReplcepath = tmpAcc;
													 } else {
														 if ((val.contains("acc_no")) && (((tmpAcc1.equalsIgnoreCase("null")) || (tmpAcc1.trim().equalsIgnoreCase("")))))
														 {
															 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Skipping for null account no. folder");
															 System.out.println("Skipping for null account no. folder");
															 continue;
														 }

														 if (val.trim().equalsIgnoreCase(""))
														 {
															 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Skipping for null folder");
															 System.out.println("Skipping for null folder");
															 continue;
														 }

														 Query = APCallCreateXML.APSelect("select " + val + " as VAL from " + txnTable + "," + extTableName + "," + productTable + " where " + txnTable + ".wi_name=" + extTableName + ".wi_name " + 
																 " and " + extTableName + ".wi_name='" + wi_name + "' and " + productTable + ".wi_name(+)=" + extTableName + ".wi_name and " + txnTable + ".cust_id='" + tmpAcc + "' and " + productTable + ".acc_no(+)='" + tmpAcc1 + "'");

														 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Queuries ## "+Query);
														 xp2.setInputXML(Query);
														 mainCode = Integer.parseInt(xp2.getValueOf("MainCode"));
														 totalRetrievedCount = Integer.parseInt(xp2.getValueOf("TotalRetrieved"));
														 if (mainCode == 0) {
															 if(totalRetrievedCount>0){
																 tmpReplcepath=xp2.getValueOf("VAL");
															 }
														 }
													 }

												 }
												 else
												 {
													 tmpReplcepath = folder[j];
												 }
												 tmpReplcepath = tmpReplcepath.replaceAll("\n", "").trim();

												 if (tmpReplcepath.equalsIgnoreCase(""))
												 {
													 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Skipping for null folder");
													 System.out.println("Skipping for null folder");
												 }
												 else
												 {
													 folder_path = folder_path + tmpReplcepath + "/";
													 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Folder creation starts for " + folder_path);
													 folderindex = createFolder(folder_path.substring(0, folder_path.length() - 1), dataclass[j], DCValues); }
											 }
											 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Created Folder Path: " + folder_path);
											 String DocumentIndex = uploadDocument(docName, imageindex + "#" + volumeid + "#", folderindex, noofpages, documenttype, documentsize, appname, docdc, DCValues);
											 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Doument Uploaded: " + DocumentIndex);
											 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Uploaded Document Name " + docName);
											 if(!"".equalsIgnoreCase(DocumentIndex)){
												 updateArchivalDetails("S",folder_path,DocumentIndex,clientId);	 
											 } else{
												 updateArchivalDetails("F",folder_path,DocumentIndex,clientId);
											 }
										 }
									 }	
								 }
							 } else {
								 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Inside else case of archieveDocumentsSingle acc_case");

								 Query = APCallCreateXML.APSelect("select " + DCQUERY.substring(0, DCQUERY.length() - 3) + " as VAL from " + txnTable + "," + extTableName + "," + productTable + " where " + txnTable + ".wi_name=" + extTableName + ".wi_name " + 
										 " and " + extTableName + ".wi_name='" + wi_name + "' and " + productTable + ".wi_name(+)=" + extTableName + ".wi_name and rownum=1");
								 xp1.setInputXML(Query);
								 mainCode = Integer.parseInt(xp1.getValueOf("MainCode"));
								 totalRetrievedCount = Integer.parseInt(xp1.getValueOf("TotalRetrieved"));
								 if (mainCode == 0) {
									 if(totalRetrievedCount>0){
										 DCValues=xp1.getValueOf("VAL");
									 }
								 }
								 for (int j = 0; j < folder.length; ++j)
								 {
									 if (folder[j].contains("$"))
									 {
										 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"folder[j].contains('$') "+j);
										 String val = folder[j].replace("$", " ");

										 Query = APCallCreateXML.APSelect("select " + val + " as VAL from " + txnTable + "," + extTableName + "," + productTable + " where " + txnTable + ".wi_name=" + extTableName + ".wi_name " + 
												 " and " + extTableName + ".wi_name='" + wi_name + "' and " + productTable + ".wi_name(+)=" + extTableName + ".wi_name");
										 xp1.setInputXML(Query);
										 mainCode = Integer.parseInt(xp1.getValueOf("MainCode"));
										 totalRetrievedCount = Integer.parseInt(xp1.getValueOf("TotalRetrieved"));
										 if (mainCode == 0) {
											 if(totalRetrievedCount>0){
												 folder[j]=xp1.getValueOf("VAL");
											 }
										 }
									 }

									 folder[j] = folder[j].replaceAll("\n", "").trim();

									 if (folder[j].equalsIgnoreCase(""))
									 {
										 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Skipping for null folder");
									 }
									 else
									 {
										 folder_path = folder_path + folder[j] + "/";
										 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Folder creation starts for " + folder_path);
										 folderindex = createFolder(folder_path.substring(0, folder_path.length() - 1), dataclass[j], DCValues); }
								 }
								 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Created Folder Path: " + folder_path);
								 String DocumentIndex = uploadDocument(docName, imageindex + "#" + volumeid + "#", folderindex, noofpages, documenttype, documentsize, appname, docdc, DCValues);
								 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Doument Uploaded: " + DocumentIndex);
								 if(!"".equalsIgnoreCase(DocumentIndex)){
									 updateArchivalDetails("S",folder_path,DocumentIndex,clientId);		 
								 } else{
									 updateArchivalDetails("F",folder_path,DocumentIndex,clientId);
								 }
								 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Uploaded Document Name " + docName);
							 }
							 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"No. of documents uploaded for workitem " + wi_name + ": ");
						 }
					 }
					 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"No. of i " +i);
				 }
			 }
			 else
			 {
				 callStatus="S";
				 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"No documents found for this workitem");
			 }
			 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Complete flag: " + sComplete);
			 if (sComplete.equalsIgnoreCase("Y"))
			 {
				 APCallCreateXML.APUpdate(extTableName,"cro_dec","Approve","wi_name='" + wi_name + "'",wmsSession);
				 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"CRO Decision updated");
				 int workitemid =1;
				 String sQuery=APCallCreateXML.APSelect("select workitemid from wfinstrumenttable where processinstanceid ='"+wi_name+"'");
				 xp.setInputXML(sQuery);
				 totalRetrievedCount = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
				 if (mainCode == 0) {
					 if(totalRetrievedCount>0){
						 workitemid =Integer.parseInt(xp.getValueOf("workitemid"));
					 }
				 }
				 ProdCreateXML.WMCompleteWorkItem(wmsSession, wi_name, workitemid);
				 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Workitem " + wi_name + " has been moved to next workstep");
			 }
			 if (!"N".equalsIgnoreCase(this.callStatus)) {
				 APCallCreateXML.APUpdate(extTableName,"archiveflag","'Archived'","wi_name='" + wi_name + "'",wmsSession);
				 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Archival flag updated");
				 ProdCreateXML.WMCompleteWorkItem(wmsSession, archival_winame, 1);
			 }
		 }
	 }
	 catch (Exception e)
	 {
		 callStatus="N";
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_ERROR,"Exception in archieveDocumentsSingle: "+e);
		 throw e;
	 }
 }
	
 public void archieveDocumentsJoint(String wi_name, String edmsSession, String wmsSession, String processName) throws Exception
 {
	 try
	 {
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Inside archieveDocumentsJoint");
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Processing started for " + wi_name);
		 String Query = getConfigMapQuery("Custom", processName);
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Query started for " + Query);
		 Query = Query.replaceAll("##", "'" + wi_name + "'");
		 String outputXml =APCallCreateXML.APSelect(Query);
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"outputXml started for " + outputXml);
		 XMLParser xp = new XMLParser();
		 xp.setInputXML(outputXml);
		 int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		 int totalRetrievedCount = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
		 if (mainCode == 0) {
			 if(totalRetrievedCount==0){
				 callStatus="S";
				 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"No documents found for " + wi_name);
			 } else{
				 int start = xp.getStartIndex("Records", 0, 0);
				 int deadEnd = xp.getEndIndex("Records", start, 0);
				 int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
				 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"No of Records  "+noOfFields);
				 for (int i = 0; i < noOfFields; ++i) {
					 String Record = xp.getNextValueOf("Record");
					 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"No of Record :"+Record);
					 XMLParser xp1 = new XMLParser(Record); 
					 prevfolderindex = "";
					 String custid =xp1.getValueOf("cust_id");
					 String docName =xp1.getValueOf("name");
					 String imageindex =xp1.getValueOf("imageindex");
					 String volumeid =xp1.getValueOf("volumeid");
					 String noofpages =xp1.getValueOf("noofpages");
					 String documenttype =xp1.getValueOf("documenttype");
					 String documentsize =xp1.getValueOf("docsize");
					 String appname =xp1.getValueOf("appname");
					 String clientId =LapsUtils.getInstance().getArchivalRefNum();
					 saveArchivalDetails(archival_winame,docName,processName,clientId,"P",  batchId,"");
					 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Archiving document " + docName);
					 if (docName.equalsIgnoreCase(""))
					 {
						 callStatus="N";
						 throw new Exception("Empty document name for " + wi_name);
					 }
					 outputXml = APCallCreateXML.APSelect("SELECT DOC,FOLDERPATH,DATACLASS,DATACLASSVALUE,DOCDC,PROCESSNAME from usr_0_folder_structure where doc='" + docName + "'and processname='" + processName + "'");
					 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO," outputXml for " + outputXml);
					 xp1.setInputXML(outputXml);
					 mainCode = Integer.parseInt(xp1.getValueOf("MainCode"));
					 totalRetrievedCount = Integer.parseInt(xp1.getValueOf("TotalRetrieved"));
					 if (mainCode == 0) {
						 if (totalRetrievedCount==0)  //need to check
						 {
							 callStatus ="N";
							 updateArchivalDetails("F","","",clientId);
							 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Folder strcuture not found for " + docName);
						 } 
						 else {
							 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO," else  ");
							 String folderpath =xp1.getValueOf("FOLDERPATH");
							 String dataclassHierarchy = xp1.getValueOf("DATACLASS");
							 String DCValues = xp1.getValueOf("DATACLASSVALUE");
							 String docdc = xp1.getValueOf("DOCDC");
							 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Folder structure:" + folderpath);
							 String[] folder = folderpath.split("/");
							 String[] dataclass = dataclassHierarchy.split("/");
							 String folderindex = "";
							 String folder_path = "";
							 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"DCValues::::" + DCValues);
							 String[] DCval = DCValues.split("#");
							 Query = "";
							 String DCQUERY = "";
							 for (int j = 0; j < DCval.length; ++j)
							 {
								 String tmp;
								 if (DCval[j].contains("$"))
								 {
									 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"DCValues:   DCval[j].contains('$')");
									 tmp = DCval[j].replace("=$", "='||");
									 tmp = tmp.replace("$", "||'#'||");
									 DCQUERY = DCQUERY + "'" + tmp + "'";
								 }
								 else
								 {
									 tmp = DCval[j] + "#'||";
									 DCQUERY = DCQUERY + "'" + tmp + "'";
								 }
							 }
							 DCQUERY = DCQUERY.replace("''", "'");

							 String[] values = DCValues.split("#");
							 String cid = values[0].substring(values[0].indexOf("=") + 1);
							 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"cid  "+cid);
							 boolean acc_case = false;
							 for (int j = 0; j < folder.length; ++j)
							 {
								 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"folder[j]  "+folder[j]);
								 if (folder[j].contains("acc_no"))
									 acc_case = true;
							 }
							 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"acc_case  "+acc_case);
							 if (acc_case)
							 {
								 Query = getConfigMapQuery("CustInfo", processName);
								 Query = Query.replaceAll("##", "'" + wi_name + "'");
								 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Query@  "+Query);
								 String cust_acc = APCallCreateXML.APSelect(Query);
								 String flag = "0";
								 xp1.setInputXML(cust_acc);
								 mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
								 totalRetrievedCount = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
								 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"totalRetrievedCount  "+totalRetrievedCount);
								 if (mainCode == 0) {
									 if(totalRetrievedCount==0){
										 flag = "1";
									 } 
									 int start1 = xp1.getStartIndex("Records", 0, 0);
									 int deadEnd1 = xp1.getEndIndex("Records", start1, 0);
									 int noOfFields1 = xp1.getNoOfFields("Record", start1, deadEnd1);
									 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"No of Records noOfFields1 "+noOfFields1);
									 for (int k = 0; k < noOfFields1; ++k) {
										 Record = xp1.getNextValueOf("Record");
										 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"No of Record :"+Record);
										 XMLParser xp2 = new XMLParser(Record);
										 String tmpAcc =xp2.getValueOf("");
										 String tmpAcc1 =xp2.getValueOf("");
										 folder_path = "";
										 prevfolderindex = "";
										 Query = APCallCreateXML.APSelect("select " + DCQUERY.substring(0, DCQUERY.length() - 3) + " as DCVAL from " + txnTable + "," + extTableName + "," + productTable + " where " + txnTable + ".wi_name=" + extTableName + ".wi_name " + 
												 " and " + extTableName + ".wi_name='" + wi_name + "' and " + productTable + ".wi_name(+)=" + extTableName + ".wi_name and " + productTable + ".acc_no(+)='" + tmpAcc1 + "'");
										 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Query##  "+Query);
										 xp2.setInputXML(Query);
										 mainCode = Integer.parseInt(xp2.getValueOf("MainCode"));
										 totalRetrievedCount = Integer.parseInt(xp2.getValueOf("TotalRetrieved"));
										 if (mainCode == 0) {
											 if(totalRetrievedCount>0){
												 DCValues =xp2.getValueOf("DCVAL");
											 }
										 }
										 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"DCValues: " + DCValues);
										 String tmpReplcepath = "";
										 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"folder length: " + folder.length);
										 for (int j = 0; j < folder.length; ++j)
										 {
											 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Inside loop ");
											 if (folder[j].contains("$"))
											 {
												 String val = folder[j].replace("$", " ");
												 //need to check and verify
												 Query = APCallCreateXML.APSelect("select " + val + " as VAL from " + txnTable + "," + extTableName + ", " + productTable + " where " + txnTable + ".wi_name=" + extTableName + ".wi_name " + 
														 " and " + extTableName + ".wi_name='" + wi_name + "' and " + productTable + ".wi_name(+)=" + extTableName + ".wi_name and " + txnTable + ".cust_id='" + tmpAcc + "' and " + productTable + ".acc_no(+)='" + tmpAcc1 + "' and " + txnTable + ".cust_id='" + custid + "'");
												 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Query::"+Query);
												 xp2.setInputXML(Query);
												 mainCode = Integer.parseInt(xp2.getValueOf("MainCode"));
												 totalRetrievedCount = Integer.parseInt(xp2.getValueOf("TotalRetrieved"));
												 if (mainCode == 0) {
													 if(totalRetrievedCount>0){
														 tmpReplcepath=xp2.getValueOf("VAL");
													 }
												 }
											 }
											 else
											 {
												 tmpReplcepath = folder[j];
											 }
											 tmpReplcepath = tmpReplcepath.replaceAll("\n", "").trim();
											 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Folder folder_path ::" + folder_path);
											 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Folder tmpReplcepath :" + tmpReplcepath);


											 if (tmpReplcepath.equalsIgnoreCase(""))
											 {
												 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Skipping for null folder");
											 }
											 else
											 {
												 folder_path = folder_path + tmpReplcepath + "/";
												 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Folder creation starts for " + folder_path);
												 folderindex = createFolder(folder_path.substring(0, folder_path.length() - 1), dataclass[j], DCValues);
											 }
										 }
										 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Created Folder Path: " + folder_path);
										 String DocumentIndex = uploadDocument(docName, imageindex + "#" + volumeid + "#", folderindex, noofpages, documenttype, documentsize, appname, docdc, DCValues);
										 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Doument Uploaded: " + DocumentIndex);
										 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Uploaded Document Name " + docName);
										 if(!"".equalsIgnoreCase(DocumentIndex)){
											 updateArchivalDetails("S",folder_path,DocumentIndex,clientId);		 
										 } else{
											 updateArchivalDetails("F",folder_path,DocumentIndex,clientId);
										 }
									 }

								 }
							 }
							 else
							 {
								 Query = APCallCreateXML.APSelect("select " + DCQUERY.substring(0, DCQUERY.length() - 3) + " as VAL from " + txnTable + "," + extTableName + "," + productTable + " where " + txnTable + ".wi_name=" + extTableName + ".wi_name " + 
										 " and " + extTableName + ".wi_name='" + wi_name + "' and " + productTable + ".wi_name(+)=" + extTableName + ".wi_name and rownum=1");
								 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,Query);
								 xp1.setInputXML(Query);
								 mainCode = Integer.parseInt(xp1.getValueOf("MainCode"));
								 totalRetrievedCount = Integer.parseInt(xp1.getValueOf("TotalRetrieved"));
								 if (mainCode == 0) {
									 if(totalRetrievedCount>0){
										 DCValues=xp1.getValueOf("VAL");
									 }
								 }
								 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"DCValues:" + DCValues);
								 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Folder length:" + folder.length);
								 for (int j = 0; j < folder.length; ++j)
								 {
									 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Loop folder[j]:" + folder[j]);
									 if (folder[j].contains("$"))
									 {
										 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"folder[j]:" + folder[j]);
										 String val = folder[j].replace("$", " ");
										 //need to check and verify
										 Query = APCallCreateXML.APSelect("select " + val + " as VAL from " + txnTable + "," + extTableName + "," + productTable + " where " + txnTable + ".wi_name=" + extTableName + ".wi_name " + 
												 " and " + extTableName + ".wi_name='" + wi_name + "' and " + productTable + ".wi_name(+)=" + extTableName + ".wi_name and " + txnTable + ".cust_id='" + custid+ "'"); //docparam[0]
										 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Inside $ Query:"+Query);
										 xp1.setInputXML(Query);
										 mainCode = Integer.parseInt(xp1.getValueOf("MainCode"));
										 totalRetrievedCount = Integer.parseInt(xp1.getValueOf("TotalRetrieved"));
										 if (mainCode == 0) {
											 if(totalRetrievedCount>0){
												 folder[j]=xp1.getValueOf("VAL");
											 }
										 }
									 }
									 folder[j] = folder[j].replaceAll("\n", "").trim();
									 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Folder folder[j] ::" + folder[j]);
									 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Folder folder_path ::" + folder_path);


									 if (folder[j].equalsIgnoreCase(""))
									 {
										 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Skipping for null folder");
									 }
									 else
									 {
										 folder_path = folder_path + folder[j] + "/";
										 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Folder creation starts for " + folder_path);
										 folderindex = createFolder(folder_path.substring(0, folder_path.length() - 1), dataclass[j], DCValues);
									 }
								 }
								 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Created Folder Path: " + folder_path);
								 String DocumentIndex = uploadDocument(docName, imageindex + "#" + volumeid + "#", folderindex, noofpages, documenttype, documentsize, appname, docdc, DCValues);
								 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Doument Uploaded: " + DocumentIndex);
								 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Uploaded Document Name " + docName);
								 if(!"".equalsIgnoreCase(DocumentIndex)){
									 updateArchivalDetails("S",folder_path,DocumentIndex,clientId);
								 } else{
									 updateArchivalDetails("F",folder_path,DocumentIndex,clientId);
								 }
							
							 }
						 }
					 }
					 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"No. of documents uploaded for workitem " + wi_name);
				 }
			 }
		 }
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Complete flag: " + sComplete);
		 if (sComplete.equalsIgnoreCase("Y"))
		 {
			 APCallCreateXML.APUpdate(extTableName,"cro_dec","Approve","wi_name='" + wi_name + "'",wmsSession);
			 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"CRO Decision updated");
			 int workitemid =1;
			 String sQuery=APCallCreateXML.APSelect("select workitemid from wfinstrumenttable where processinstanceid ='"+wi_name+"'");
			 xp.setInputXML(sQuery);
			 totalRetrievedCount = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
			 if (mainCode == 0) {
				 if(totalRetrievedCount>0){
					 workitemid =Integer.parseInt(xp.getValueOf("workitemid"));
				 }
			 }
			 ProdCreateXML.WMCompleteWorkItem(wmsSession, wi_name, workitemid);
			 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Workitem " + wi_name + " has been moved to next workstep");
		 }
		 if (!"N".equalsIgnoreCase(this.callStatus)) {
			 APCallCreateXML.APUpdate(extTableName,"archiveflag","Archived","wi_name='" + wi_name + "'",wmsSession);
			 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Archival flag updated");
			 ProdCreateXML.WMCompleteWorkItem(wmsSession, archival_winame, 1);
		 }
	 }
	 catch (Exception e)
	 {
		 callStatus="N";
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_ERROR,"Exception in archieveDocumentsJoint: "+e);
		 throw e;
	 }
 }

 public void saveArchivalDetails(String winame,String docname, String channelName, String ClientID, String callStatus, String batchId, String targetLoc) {
	 try {
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, "Inside saveArchivalDetails : ");
		 String tableName = "BPM_ARCHIVAL_DETAILS";
		 StringBuilder columnName = new StringBuilder();
		 StringBuilder sValues = new StringBuilder();
		 columnName.append("BATCH_ID, WI_NAME,DOCUMENT_NAME,ARCHIVAL_CHANNEL_NAME,ARCHIVAL_STATUS,ARCHIVAL_CLIENT_ID, ARCHIVAL_DATE,TARGET_LOCATION");
		 sValues.append("'" + batchId + "','" + winame + "','" + docname + "','" + channelName + "','"+callStatus+"','" + ClientID + "',SYSDATE,'" +targetLoc + "'");
		 String outputXml = APCallCreateXML.APInsert(tableName, columnName.toString(), sValues.toString(), wmsSession);
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, "outputXml :saveArchivalDetails " + outputXml);
	 } catch (Exception e) {
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_ERROR,"Exception in saveArchivalDetails e:"+e);		

	 } 
 } 
 
 public void updateArchivalDetails(String status,String loc,String DocIndex,String clientID) {
	 try {

		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, "Inside updateArchivalDetails : ");
		 APCallCreateXML.APUpdate("BPM_ARCHIVAL_DETAILS","ARCHIVAL_STATUS,TARGET_LOCATION,DOCUMENT_INDEX","'"+status+"','"+loc+"','"+DocIndex+"'"," WI_NAME  = '"+archival_winame+"' and ARCHIVAL_CLIENT_ID ='"+clientID+"'", wmsSession);
	 } catch (Exception e) {
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_ERROR,"Exception in updateArchivalDetails e:"+e);		

	 }

 }
 
 public String getConfigMapQuery(String TagName,String processName) throws Exception 
 {
	 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Inside getConfigMapQuery");
	 String sTable="";
	 String sWhere="";
	 String sField="";
	 String out="";
	 archivalConfigMap= ArchivalCache.getInstance().getConfigMap();
	 configMapList =new ArrayList<String>();
	 configMapList = archivalConfigMap.get(processName+"#"+TagName);

	 try {
		 sTable =(String) configMapList.get(0);
		 sField =(String) configMapList.get(1);
		 sWhere =(String) configMapList.get(2);

		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"sTable :" + sTable);
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"sWhere :" + sWhere);
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"sField :" + sField);
	 }
	 catch (Exception e)
	 {
		 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_ERROR,"[getConfigMapQuery]" +"Exception in getConfigMapQuery: "+e);
		 throw e;
	 }

	 out = "select " + sField + " from " + sTable + " where " + sWhere;
	 ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"out###"+out);

	 return out;
 }	

	@Override
	public String createInputXML() throws Exception {
			return "";
	}

	@Override
	public String executeCall(HashMap<String, String> input) throws Exception {
		ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, "Execute call called in ArchivalService");
		return call();
	}

	@Override
	public void executeDependencyCall() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void responseHandler(String outputXML, String inputXml)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		// TODO Auto-generated method stub
		
	}
	

}
		
