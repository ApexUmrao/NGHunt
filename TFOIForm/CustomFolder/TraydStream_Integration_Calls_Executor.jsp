<jsp:useBean id="customSession" class="com.newgen.custom.wfdesktop.session.WFCustomSession" scope="session"/>

<%@ page import="com.newgen.omni.wf.util.app.*"%>
<%@ page import =" javax.xml.parsers.DocumentBuilder " %>
<%@ page import  =" javax.xml.parsers.DocumentBuilderFactory" %>
<%@ page import  =" org.w3c.dom.Document" %>
<%@ page import  =" org.w3c.dom.Element" %>
<%@ page import  =" org.w3c.dom.Node" %>
<%@ page import  =" org.w3c.dom.NodeList" %>
<%@ page import  =" org.xml.sax.InputSource" %>
<%@page  import="java.util.*"%>
<%@page  import="java.text.SimpleDateFormat"%>
<%@page  import="java.util.List"%>
<%@page  import="java.util.ArrayList"%>
<%@ page import="org.xml.sax.SAXException"%>
<%@ page import="javax.xml.parsers.ParserConfigurationException"%>
<%@ page import="java.io.IOException" %>
<%@ page import ="java.io.StringReader" %>
<%@ page import ="Jdts.DataObject.JPDBString"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.*"%>
<%@ page import ="org.apache.log4j.Logger"%>
<%@ page import ="org.apache.log4j.PropertyConfigurator"%>
<%@page import="java.awt.Image"%>
<%@page import="com.newgen.omni.wf.util.excp.NGException"%>
<%@page import="java.util.Base64"%>
<%@page import="java.awt.Toolkit"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.newgen.omni.jts.cmgr.XMLParser"%>
<%@ page import="com.newgen.wfmonitor.xmlapi.*"%>
<%@ page import="com.newgen.wfdesktop.xmlapi.*" %>
<%@ page import = "ISPack.ISUtil.JPISException" %>
<%@ page import = "ISPack.ISUtil.JPISIsIndex" %>
<%@ page import="java.io.*,Jdts.DataObject.JPDBInteger, Jdts.DataObject.JPDBISAddress,Jdts.DataObject.*,Jdts.*,java.net.*,java.awt.*"%>
<%@ page import="com.newgen.wfdesktop.util.*" %>
<%@ page import=="com.newgen.iforms.user.tfo.util.ConnectSocket"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.Properties"%>
<%@page import="org.apache.log4j.PropertyConfigurator"%>
<%@page import ="org.apache.log4j.Logger"%>
<%@page import="java.io.File"%>
<%@page import="ISPack.CPISDocumentTxn"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.parser.JSONParser"%>
<%@page import="java.nio.file.Path"%>
<%@page import="java.nio.file.Files"%>
<%@page import="java.nio.file.Paths"%>









<%
initializeLogger();
sQuery="";
sInput="";
sOutput="";
String sErrorDescription="";
String sResponse="";

sCabname=customSession.getEngineName();
sSessionId = customSession.getDMSSessionId();
sUserName = customSession.getUserName();
sJtsIp = customSession.getJtsIp();
iJtsPort = String.valueOf(customSession.getJtsPort());

String sWIName = request.getParameter("WI_NAME");
String sCallName = request.getParameter("CALL_OPERATION");
String callXML="";

XMLParser xp = null;
int returnCode = 1;
String sRequestCategoryCode = "";
String sRequestTypeCode = "";
String sProcessType = "";
String sSourceChannel = "";
String refNo="";
String jtsIp = "";	
String jtsPort = "";	
String cabinetName = "";	
String siteId = "";	
String docDownLoadPath = "";
String restResponse = "";
String caseUrl ="";


try
{	
	String sQueryBPM = "SELECT (SELECT VALUE FROM BPM_SERVICE_CONFIG WHERE KEY = 'SOCKETIP') IP,(SELECT VALUE FROM BPM_SERVICE_CONFIG WHERE KEY = 'SOCKETPORT') PORT FROM DUAL";
	String sInputBPM = prepareAPSelectInputXml(sQueryBPM,sCabname,sSessionId);
	WriteToLog_showpage("Y"," ***************Ref number Input "+sInputBPM+" *****************************");
	String sOutputBPM = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputBPM);
	WriteToLog_showpage("Y"," ***************Ref number output =  *****************************");
	WriteToLog_showpage("Y"," ***************Ref number output = "+sOutputBPM+"  *****************************");
	try
	{
		WriteToLog_showpage("Y","started");
		String ipBPM = sOutputBPM.substring(sOutputBPM.indexOf("<IP>")+4,sOutputBPM.indexOf("</IP>"));
		WriteToLog_showpage("Y","started  1");
		int portBPM = Integer.parseInt(sOutputBPM.substring(sOutputBPM.indexOf("<PORT>")+6,sOutputBPM.indexOf("</PORT>")));
		WriteToLog_showpage("Y","ipBPM : " +ipBPM);
		WriteToLog_showpage("Y"," portBPM : " +portBPM);
		socket = ConnectSocket.getReference(ipBPM, portBPM);
	}
	catch(Exception e)
	{
	}
	
	sQuery="SELECT SEQ_WEBSERVICE.NEXTVAL  AS REFNO FROM DUAL" ;
	sInput=prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
	WriteToLog_showpage("Y"," *************** Ref Input"+sInput+" *****************************");
	sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);	
	refNo=sOutput.substring(sOutput.indexOf("<REFNO>")+7,sOutput.indexOf("</REFNO>"));
	
	sQuery="SELECT COUNT(1) AS COUNT_WI FROM PDBCONNECTION WHERE RANDOMNUMBER = '"+sSessionId+"'";
	sInput=prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
	WriteToLog_showpage("Y"," ***************Checking connection "+sInput+" *****************************");
	sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);	
	String sCount=sOutput.substring(sOutput.indexOf("<COUNT_WI>")+10,sOutput.indexOf("</COUNT_WI>"));
	WriteToLog_showpage("Y"," ***************Connection sCount "+sCount+" *****************************");
	if(sCount.equalsIgnoreCase("0"))
	{
		out.println("Session Timeout");
	}
	else
	{	

	  WriteToLog_showpage("Y"," ***************Connection sCallName "+sCallName+" *****************************");
	   sQuery="SELECT PROCESS_TYPE,REQUEST_TYPE,REQUEST_CATEGORY,TSTXNID,IS_TS_REQUIRED,PT_ASSIGNED_MAKER FROM EXT_TFO WHERE WI_NAME ='"+sWIName+"'";
	   sInput=prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
	   WriteToLog_showpage("Y"," ***************Checking connection "+sInput+" *****************************");
	   sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
	   String processType = sOutput.substring(sOutput.indexOf("<PROCESS_TYPE>")+14,sOutput.indexOf("</PROCESS_TYPE>"));
       String reqCat = sOutput.substring(sOutput.indexOf("<REQUEST_TYPE>")+14,sOutput.indexOf("</REQUEST_TYPE>"));
       String reqType = sOutput.substring(sOutput.indexOf("<REQUEST_CATEGORY>")+18,sOutput.indexOf("</REQUEST_CATEGORY>"));
       String tsTxnId = sOutput.substring(sOutput.indexOf("<TSTXNID>")+9,sOutput.indexOf("</TSTXNID>"));
	   String tsReq = sOutput.substring(sOutput.indexOf("<IS_TS_REQUIRED>")+16,sOutput.indexOf("</IS_TS_REQUIRED>"));
	   String assignMaker = sOutput.substring(sOutput.indexOf("<PT_ASSIGNED_MAKER>")+19,sOutput.indexOf("</PT_ASSIGNED_MAKER>"));
	   
		try {
			sQuery = "SELECT KEY, VALUE FROM BPM_CONFIG WHERE PROCESS_NAME ='TFO'";
			sInput=prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
	        WriteToLog_showpage("Y"," ***************Checking connection "+sInput+" *****************************");
	        sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);	
			String mainCode = sOutput.substring(sOutput.indexOf("<MainCode>")+10,sOutput.indexOf("</MainCode>"));	
			
			if(mainCode.equalsIgnoreCase("0")) {					
				WriteToLog_showpage("Y","inside  maincode 0");
				int totalRetrieved = Integer.parseInt(sOutput.substring(sOutput.indexOf("<TotalRetrieved>")+16,sOutput.indexOf("</TotalRetrieved>")));
				WriteToLog_showpage("Y"," ***************totalRetrieved   ------------:"+totalRetrieved);
				if(totalRetrieved>0) {
					XMLParser xp1 = new XMLParser(sOutput);
					int start = xp1.getStartIndex("Records", 0, 0);
					int deadEnd = xp1.getEndIndex("Records", start, 0);
					int noOfFields = xp1.getNoOfFields("Record", start, deadEnd);
					int end = 0;
						if(noOfFields > 0){
							for(int i = 1; i <= noOfFields; ++i) {
								start = xp1.getStartIndex("Record", end, 0);
								end = xp1.getEndIndex("Record", start, 0);
															
								String key = xp1.getValueOf("KEY", start, end);
								String value = xp1.getValueOf("VALUE", start, end);
										
								if ("JTSIP".equals(key)) {
									jtsIp = value;
								} else if ("JTSPORT".equals(key)) {
									jtsPort = value;
								} else if ("CABINETNAME".equals(key)) {
									cabinetName = value;
								} else if ("SITEID".equals(key)) {
									siteId = value;
								} else if ("DOCDOWNLOADPATH".equals(key)) {
									docDownLoadPath = value;
								} else if ("CASE_CREATION_URL".equals(key)) {
									caseUrl = value;
								}
							}
						}
				}
			}
		} catch (Exception e) {
		  WriteToLog_showpage("Y","Exception in getting key value data: "+ e.getMessage());
		  e.printStackTrace();
		}
			   
	   sQuery ="SELECT SOURCE,BRANCHID,TENANTID,UPLOADSCOUNT,TXNTYPE,DOCTYPE FROM USR_0_TRAYDSTREAM_DEFAULT_MASTER WHERE PROCESS_TYPE ='"+processType+"'   AND REQUEST_TYPE ='"+reqCat+"' AND REQUEST_CATEGORY ='"+reqType+"'";
	   sInput=prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
       WriteToLog_showpage("Y"," ***************Checking connection "+sInput+" *****************************");
	   sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
	   String source = sOutput.substring(sOutput.indexOf("<SOURCE>")+8,sOutput.indexOf("</SOURCE>"));
       String branchId = sOutput.substring(sOutput.indexOf("<BRANCHID>")+10,sOutput.indexOf("</BRANCHID>"));
       String tenantId = sOutput.substring(sOutput.indexOf("<TENANTID>")+10,sOutput.indexOf("</TENANTID>"));
       String uploadsCount = sOutput.substring(sOutput.indexOf("<UPLOADSCOUNT>")+14,sOutput.indexOf("</UPLOADSCOUNT>"));
	   String txnType = sOutput.substring(sOutput.indexOf("<TXNTYPE>")+9,sOutput.indexOf("</TXNTYPE>"));
	   String docType = sOutput.substring(sOutput.indexOf("<DOCTYPE>")+9,sOutput.indexOf("</DOCTYPE>"));
	
	   String[] data =sCallName.split("~");
	   String docFileName =data[0];
	   String docExtensionType =data[1];
	   
	   sQuery="select a.DOCUMENTINDEX ,a.IMAGEINDEX, a.VOLUMEID,a.NOOFPAGES,a.DOCUMENTSIZE, a.APPNAME,a.VERSIONCOMMENT from PDBDOCUMENT a where   documentindex in (select documentindex from PDBDOCUMENTCONTENT where parentfolderindex=(select itemindex from ext_tfo where wi_name='" + sWIName + "') and  a.NAME in ("+docType+")) and a.versioncomment ='" + docFileName + "'  and a.appname ='" + docExtensionType + "'  and rownum=1";
	   
       sInput=prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
	   WriteToLog_showpage("Y"," ***************Checking connection "+sInput+" *****************************");
	   sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);	
	   
	   String imageIndex = sOutput.substring(sOutput.indexOf("<IMAGEINDEX>")+12,sOutput.indexOf("</IMAGEINDEX>"));
       String appname = sOutput.substring(sOutput.indexOf("<APPNAME>")+9,sOutput.indexOf("</APPNAME>"));
       String volumeID = sOutput.substring(sOutput.indexOf("<VOLUMEID>")+10,sOutput.indexOf("</VOLUMEID>"));
       String name = sOutput.substring(sOutput.indexOf("<VERSIONCOMMENT>")+16,sOutput.indexOf("</VERSIONCOMMENT>"));
	   String folderPath =docDownLoadPath+sWIName;
	   String downLoadLocation = folderPath + File.separatorChar + name.trim() + "." + appname;
       JPDBString oSiteName = new JPDBString();
	   File file = new File(folderPath);
	    if (!file.exists()) {
          Boolean createFile = Boolean.valueOf(file.mkdirs());
		  WriteToLog_showpage("Y"," createFile    "+createFile);
	    }

	   try {
			WriteToLog_showpage("Y"," Document downloaded successfully :");
			CPISDocumentTxn.GetDocInFile_MT(null, jtsIp, Short.parseShort(jtsPort), 
                cabinetName, Short.parseShort(siteId), Short.parseShort(volumeID), 
                Integer.parseInt(imageIndex), "", downLoadLocation, oSiteName);
			WriteToLog_showpage("Y"," Document downloaded successfully :");
        } catch (JPISException e) {
            WriteToLog_showpage("Y"," Exception in Download Document " + e);
        } 
		WriteToLog_showpage("Y"," After Downloaded Document Start  Converting Into Base 64 data:");
        byte[] inFileBytes = Files.readAllBytes(Paths.get(downLoadLocation, new String[0]));
        String base64 = Base64.getEncoder().encodeToString(inFileBytes);
		
	     String jsonInput = "{\n" +
                "    \"txnId\": \""+ tsTxnId +"\",\n" +
                "    \"lcRefId\": \"\",\n" +
                "    \"uploadsCount\": \""+uploadsCount+"\",\n" +
                "    \"tenantId\": \""+tenantId+"\",\n" +
                "    \"clientData\": \"{\\\"clientRefId\\\": \\\""+sWIName+"\\\",\\\"secondClientRefId\\\": \\\""+assignMaker+"\\\",\\\"branchId\\\": \\\""+branchId+"\\\",\\\"source\\\": \\\""+source+"\\\"}\",\n" +
                "    \"txnType\": \""+txnType+"\",\n" +
                "    \"userProfile\": \"{\\\"email\\\" : \\\""+sUserName+"\\\"}\",\n" +
                "    \"data\": \""+base64+"\",\n" +
                "    \"fileName\": \""+ name.trim() + "." + appname+"\"\n" +
                "}";	
				
	
	//  WriteToLog_showpage("Y"," executeRestAPIJSON  jsonInput :" +jsonInput);
	    String uUid =UUID.randomUUID().toString();
		sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME,VESSEL_NAME,CALL_NAME,CALL_XML,REF_NUM","'" + sWIName + "','"+ uUid +"','"+ sCallName +"',"+ convertToPlainString(jsonInput) +",sysdate", sCabname, sSessionId);
	//	WriteToLog_showpage("Y","jsonInput TFO_DJ_INTEGRATION_CALLS_DTLS "+ sQuery);	
		sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);						
		WriteToLog_showpage("Y","jsonInput TFO_DJ_INTEGRATION_CALLS_DTLS "+ sOutput);	
		
		restResponse = executeRestAPIJSON(caseUrl,jsonInput,uUid);
		
		WriteToLog_showpage("Y"," executeRestAPIJSON  sOutput :" +sOutput);
		
		 if (!restResponse.contains("Error") && !(restResponse== null || restResponse.isEmpty())) {
            tsTxnId= getTagValueJSON(restResponse, "txnId");
		   	sResponse = "Success";
		    sErrorDescription = getTagValueJSON(restResponse, "desc");
			
			sQuery = prepareAPUpdateInputXml("EXT_TFO","TSTXNID","'"+tsTxnId+"'","WI_NAME = '"+ sWIName +"'",sCabname,sSessionId);
			WriteToLog_showpage("Y","update txnId and lcrefId in external table ext_tfo "+ sQuery);
			sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
			WriteToLog_showpage("Y","update txnId and lcrefId in external table ext_tfo " + sOutput);
			sQuery = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS","CALL_STATUS,ERROR_DESCRIPTION","'Y','"+ sErrorDescription+ "'","WI_NAME = '"+ sWIName +"'  AND CALL_NAME ='"+sCallName+"'",sCabname,sSessionId);
			WriteToLog_showpage("Y","update TFO_DJ_INTEGRATION_CALLS "+ sQuery);
			sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
			WriteToLog_showpage("Y","update TFO_DJ_INTEGRATION_CALLS " + sOutput);
			sQuery = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'"+restResponse+"'","WI_NAME = '"+ sWIName +"'  AND CALL_NAME ='"+sCallName+"' AND VESSEL_NAME ='"+uUid+"'",sCabname,sSessionId);
			WriteToLog_showpage("Y","update TFO_DJ_INTEGRATION_CALLS_DTLS "+ sQuery);
			sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
			WriteToLog_showpage("Y","update TFO_DJ_INTEGRATION_CALLS_DTLS " + sOutput);
			out.println("0#"+sResponse+"#"+sErrorDescription);
         }else {
			sResponse = "Failure";
			if(restResponse.contains("ErrorCode")){
				sErrorDescription =getTagValueJSON(restResponse, "ErrorCode");
			}else{
				sErrorDescription =restResponse;
			}
			sQuery = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS","CALL_STATUS,ERROR_DESCRIPTION","'F','"+ sErrorDescription+ "'","WI_NAME = '"+ sWIName +"'  AND CALL_NAME ='"+sCallName+"'",sCabname,sSessionId);
			WriteToLog_showpage("Y","update TFO_DJ_INTEGRATION_CALLS "+ sQuery);
			sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
			sQuery = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'"+restResponse+"'","WI_NAME = '"+ sWIName +"'  AND CALL_NAME ='"+sCallName+"' AND VESSEL_NAME ='"+uUid+"'",sCabname,sSessionId);
			WriteToLog_showpage("Y","update TFO_DJ_INTEGRATION_CALLS "+ sQuery);
			sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
			WriteToLog_showpage("Y","update TFO_DJ_INTEGRATION_CALLS_DTLS " + sOutput);
			out.println("1#"+sResponse+"#"+sErrorDescription);
		}
        
    }
	
}	
catch(Exception e)
{
	sInput=prepareAPInsertInputXml("USR_0_INTEGRATION_VALUES","wi_name,exception_error","'" + sWIName + "','"+e.getMessage()+"'",sCabname,sSessionId);
	WriteToLog_showpage("Y"," ***************Exception "+sInput+" *****************************");
	sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
	out.println("Error#FAILED#"+e.getMessage());
	e.printStackTrace();
}



%>


<%!			
	
    public  String executeRestAPIJSON(String url, String inputXML,String uUid) throws Exception{
		StringBuilder outputXML = new StringBuilder();
		HttpURLConnection conn=null;
		try {
		    WriteToLog_showpage("Y","URL: "+ url);
			URL urlName = new URL(url);
			conn =  (HttpURLConnection) urlName.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("x-fapi-interaction-id", uUid);

			OutputStream os = conn.getOutputStream();
			os.write(inputXML.getBytes());
			os.flush();
		    WriteToLog_showpage("Y","conn.getResponseCode()===> "+conn.getResponseCode());
			if(conn.getResponseCode() != HttpURLConnection.HTTP_OK && conn.getResponseCode() != HttpURLConnection.HTTP_CREATED){
			    WriteToLog_showpage("Y","Failed : HTTP error code :===> "+conn.getResponseCode());
				throw new RuntimeException("Failed : HTTP error code : "+conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		    WriteToLog_showpage("Y","Output from server ...\n");
			String out ;
			while ((out = br.readLine()) != null) {
				outputXML.append(out);
	//		 WriteToLog_showpage("Y","RestAPI output===> "+outputXML);
			}
		} catch (MalformedURLException e) {
		    WriteToLog_showpage("Y","RestAPI exception1===> "+e.getMessage());
			outputXML.append("interaction Id :" + uUid + "  Error : "+e.getMessage());
		}catch(IOException e) {
		    WriteToLog_showpage("Y","RestAPI exception2===> "+e.getMessage());
			outputXML.append("interaction Id :" + uUid + " Error : "+e.getMessage());
		}
		finally{
			if(conn!=null){
				conn.disconnect();
			}
		}
		return outputXML+"";
	}
	
	public  String getTagValueJSON(String jsonData, String tagname) {
	    JSONParser parser = new JSONParser();
		try
		{
		  JSONObject jsonObject = (JSONObject)parser.parse(jsonData);
		  return searchTag(tagname, jsonObject);
				
		}catch(Exception e)
		{
			WriteToLog_showpage("Y","getTagValueJSON===> "+e.getMessage());
		}
      return null;
    } 
	
	public  String searchTag(String tagname, JSONObject jsonObj) {
		if (jsonObj == null || tagname == null || tagname.isEmpty())
		  return null; 
		for (Object key : jsonObj.keySet()) {
		  String keyStr = (String)key;
		  if (keyStr.equals(tagname)) {
			Object object = jsonObj.get(keyStr);
			return (object != null) ? object.toString() : null;
		  } 
		  Object value = jsonObj.get(keyStr);
		  if (value instanceof JSONObject) {
			String result = searchTag(tagname, (JSONObject)value);
			if (result != null)
			  return result; 
		  } 
		  if (value instanceof JSONArray) {
			JSONArray jsonArray = (JSONArray)value;
			for (Object arrayItem : jsonArray) {
			  if (arrayItem instanceof JSONObject) {
				String result = searchTag(tagname, (JSONObject)arrayItem);
				if (result != null)
				  return result; 
			  } 
			} 
		  } 
		} 
		return null;
    }
	
    public  void deleteDirectory(String filelocation) {
      Path filePath = Paths.get(filelocation);
      Path directoryPath = filePath.getParent(); // This gets the parent directory
     try {
            // Delete the file
            Files.deleteIfExists(filePath);
            WriteToLog_showpage("Y","File deleted: " + filePath);
            // Delete the directory if it is empty
            if (Files.isDirectory(directoryPath) && Files.list(directoryPath).count() == 0) {
                Files.deleteIfExists(directoryPath);
               WriteToLog_showpage("Y","Directory deleted: " + directoryPath);
            } else {
                WriteToLog_showpage("Y","Directory not deleted because it is not empty: " + directoryPath);
            }
        } catch(Exception e)
		{
		   WriteToLog_showpage("Y","Exception While deleteDirectory " + e.getMessage());	
		}
   }
   
   private String convertToPlainString(String sInputXML){
	StringBuffer outputxml = new StringBuffer();
	WriteToLog_showpage("Y"," sInputXML.length() " + sInputXML.length());
	sInputXML=sInputXML.replace("SessionId","SessionIdTemp");
    if (sInputXML.length() > 4000) {
      int itr = sInputXML.length() / 4000;
      int mod = sInputXML.length() % 4000;
      if (mod > 0) {
        ++itr;
      }
      WriteToLog_showpage("Y"," itr " + itr);
      for (int i = 0; i < itr; ++i) {
        WriteToLog_showpage("Y"," output part " + i + 1);
        if (i == 0) {
          outputxml.append("TO_NCLOB('" + sInputXML.substring(0, 4000) + "')");
        }
        else if (i < itr - 1) {
          outputxml.append(" || TO_NCLOB('" + sInputXML.substring(4000 * i, 4000 * (i + 1)) + "')");
        }
        else
        {
          outputxml.append(" || TO_NCLOB('" + sInputXML.substring(4000 * i, sInputXML.length()) + "') ");
        }
      }

    }
    else
    {
		//outputxml.append(sInputXML); 
      outputxml.append("'"+sInputXML+"'");
    }
	return outputxml.toString();
    }
	

%>


		
<%!
String sQuery="";
String sInput="";
String sOutput="";
String sUserName="";
String sCabname="";
String sSessionId ="";
String sJtsIp ="";
String iJtsPort = "";
ConnectSocket socket;

private String prepareAPSelectInputXml(String sQuery, String sCabname, String sSessionId){	
	return "<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+sQuery+"</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
}

private String prepareAPInsertInputXml(String tableName,String colname,String sValues, String sCabname, String sSessionId){	
	return "<?xml version=\"1.0\"?><APInsert_Input><Option>APInsert</Option><TableName>"+tableName+"</TableName><ColName>"+colname+"</ColName><Values>"+sValues+"</Values><EngineName>" + sCabname + "</EngineName>" +    "<SessionId>" + sSessionId + "</SessionId></APInsert_Input>";
}

private String prepareAPUpdateInputXml(String tableName,String colname,String sValues,String whrcls, String sCabname, String sSessionId){	
	return "<?xml version=\"1.0\"?><APUpdate_Input><Option>APUpdate</Option><TableName>"+tableName+"</TableName><ColName>"+colname+"</ColName><Values>"+sValues+"</Values><WhereClause>"+whrcls+"</WhereClause><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APUpdate_Input>";
}

private String prepareAPProcInputXml(String ProcName,String ProcParams, String sCabname, String sSessionId){	
	return "<?xml version=\"1.0\"?><APProcedure_Input><Option>APProcedure</Option><ProcName>"+ProcName+"</ProcName><Params>"+ProcParams+"</Params><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APUpdate_Input>";
}

private String prepareAPDeleteXml(String tableName,String whereClause, String sCabname, String sSessionId){
	return "<?xml version=1.0?><APDelete_Input><Option>APDelete</Option><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId><TableName>"+tableName+"</TableName><WhereClause>"+whereClause+"</WhereClause></APDelete_Input>";

}


private String characterHandler(String str) {
        
        return str.replaceAll("'","''");
}
 
 public void initializeLogger(){
			try{
				Properties properties = new Properties();
				String log4JPropertyFile =  System.getProperty("user.dir") +  System.getProperty("file.separator") + "ProcessLoggerConfig" +  System.getProperty("file.separator") +  "log4jJSP.properties";
				properties.load(new FileInputStream(log4JPropertyFile));
				PropertyConfigurator.configure(properties);
			}catch(Exception e){
				e.printStackTrace();
			}
		} 
Logger log = Logger.getLogger(_TraydStream_5F_Integration_5F_Calls_5F_Executor.class);
	
private void WriteToLog_showpage(String flag,String strOutput){
		log.info(strOutput);
}



%>
