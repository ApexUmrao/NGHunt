/*

/webdesktop/CustomFolder/TFO/ADDLLIDocument.jsp?&PROCESS_NAME=TFO&WI_NAME=TF-00000001983-REQUEST&TEMPLATE_CODE=1&DOCUMENT_PATH=/appl/IBM/WebSphere/AppServer/profiles/WMSProf01/TFO_FILES/LLI.txt&DOC_EXT=txt&DOC_NAME=LLI&session="+wd_uid+"&WD_UID=TF-00000001983-REQUEST&ITEM_INDEX=90825","","dialogWidth=1000px

/appl/IBM/WebSphere/AppServer/profiles/WMSProf01/TFO_FILES/LLI.txt
 **********************************************************************************************
Group							: 	Application Project 2
Product/Project 				: 	Trade Finance Operations
Module							: 	Web
File Name						: 	AddLLIDocument.jsp
Author                          : 	Amrendra Pratap Singh
Date written (DD/MM/YYYY)       :	09/10/2019
Description						: 	Adding Document to Workitem from DMS
 **********************************************************************************************
CHANGE HISTORY
 **********************************************************************************************
Date		Change By               Change Description (Bug No. (If Any))
 **********************************************************************************************

 **********************************************************************************************
 */
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.newgen.wfdesktop.xmlapi.*" %>
<%@ page import="com.newgen.wfdesktop.util.*" %>
<%@ page import ="com.newgen.omni.wf.util.excp.*"%>
<%@ page import ="com.newgen.omni.jts.cmgr.*"%>
<%@ page import ="java.util.*"%>
<%@ page import ="java.io.*"%>
<%@ page import ="ISPack.CPISDocumentTxn"%>
<%@ page import ="ISPack.ISUtil.*"%>
<%@ page import ="java.text.DateFormat,java.util.GregorianCalendar,java.util.Calendar"%>

<%@ page import ="java.util.regex.Matcher"%>
<%@page import="com.newgen.omni.wf.util.excp.NGException"%>
<%@page import="com.newgen.omni.wf.util.app.NGEjbClient"%>
<%@ page import ="java.util.*"%>
<%@ page import ="java.io.*"%>
<%@page import="com.newgen.omni.wf.util.app.*"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.Properties"%>
<%@page import="org.apache.log4j.PropertyConfigurator"%>
<%@page import ="org.apache.log4j.Logger"%>
<jsp:useBean id="customSession" class="com.newgen.custom.wfdesktop.session.WFCustomSession" scope="session"/>

<%
initializeLogger();
String appServerType = "WEBSPHERE";
String jtsIP = customSession.getJtsIp();
String jtsPort = String.valueOf(customSession.getJtsPort());
String sessId = customSession.getDMSSessionId();
String strEngineName = customSession.getEngineName();
String strItemIndex;

String sResponse="";
String processName = null;
String workitemName = null;
String templateCode = null;
String docPath = null;
String outputXml = null;
int folderIndex = -1;
long lFileLength;
int iNoOfPages = 1;
int volumeId = 8;

String sIpAddress = null;
String cabinetName = null;
String docIndex = "";
int sessionId = -1;
String groupIndex = "0";
String processDefId = "0";
File fileObj = null;
XMLParser parserObj = null;
XMLParser parserObj1 = null;
XMLParser xmlParser = null;
String templatePath = null;
String destinationPath = null;
String viewer = null;
String viewerTemp = null;
String DMSActualDocName = null;
String documentName = null;
String LLIDocName = null;
ArrayList arrlist = null;
ArrayList arrGridlist = null;
ArrayList arrExtlist = null;
String extTblName = null;
boolean adddocument = false;
boolean readstatus = false;
boolean bReturn = false;
Properties propObj = null;
Long startTime;
Long timeDiff;
String workStepName = null;
String xmltopass=null;
String kyc=null;

String sCurrDirectory="";
String sFoldername="ApplicationLogs/ProcessLogs/TFO/TFO_PDF_Files";
String sFolderPathN="";

try
{     
	WriteToLog("LLIDocumentstart>>>>>>>>>");
	//System.out.println("LLIDocumentstart.............................................."+appServerType+"  "+jtsIP);

	WriteToLog("APS Note Start"+appServerType+" \t\t  "+jtsPort);
	WriteToLog("APS Note Start"+appServerType+" \t\t "+jtsIP);
	WriteToLog("APS Note Start"+strEngineName+"  \t\t"+sessId);
	
	processName = "TFO";
	workitemName = request.getParameter("WI_NAME");
	templateCode = "1";
	docPath = "";
	viewerTemp = "pdf";
	DMSActualDocName = "LLI";
	
	sCurrDirectory = System.getProperty("user.dir");
	sFolderPathN =sCurrDirectory+"/"+sFoldername+"/"+workitemName+"/"+DMSActualDocName+".pdf";
	docPath = sFolderPathN;
	WriteToLog("DMSActualDocName ==> "+DMSActualDocName);
	//System.out.println("DMSActualDocName ==> "+DMSActualDocName);
	int pos = viewerTemp.indexOf("-");
    if (pos > -1) {
		documentName = viewerTemp.substring(0, pos);
		viewer=viewerTemp.substring(pos +1);
		
    //    System.out.println(">>" + detail.substring(0, pos) + "<<") ;
    //    System.out.println(">>" + detail.substring(pos +1) + "<<") ;
    }else{
		viewer=viewerTemp;
		documentName = "LLI";
	}
	WriteToLog("processName: "+processName+" : "+workitemName+" templateCode: "+templateCode);
	parserObj = new XMLParser();
	parserObj1 = new XMLParser();
	try
	{

		//documentName = "LLI";

		WriteToLog("documentName::::::"+documentName);

		if(processName.equalsIgnoreCase("TFO"))
		{
			if(templateCode.equalsIgnoreCase("1"))
			{
				WriteToLog("Inside ==> ");
				Calendar cal = new GregorianCalendar();
				cal.setTime(new Date());
				int month = cal.get(cal.MONTH) +1;
				String DtString =String.valueOf("_"+cal.get(cal.DAY_OF_MONTH))+"_" + String.valueOf(month) +"_" + String.valueOf(cal.get(cal.YEAR))+"_"  + String.valueOf(cal.get(cal.HOUR_OF_DAY))+"_"  + String.valueOf(cal.get(cal.MINUTE))+"_"  +String.valueOf(cal.get(cal.SECOND)); 

				LLIDocName = DMSActualDocName + DtString; //f.getName();
				WriteToLog("LLIDocName ==> "+LLIDocName);
				//System.out.println("LLIDocName ==> "+LLIDocName);
				readstatus = true;
			}
		}
		WriteToLog("viewer: "+viewer+"readstatus : "+readstatus);
		if(processName.equalsIgnoreCase("TFO"))
		{
			destinationPath = docPath;
		}
		else
		{
			destinationPath = docPath+"."+viewer;
		}		
		
		WriteToLog("destinationPath :"+destinationPath);
	}
	catch(Exception exp)
	{
		readstatus = false;
		WriteToLog("Error in reading the Property File :="+exp);
		WriteToLog("Error in reading the Property File overloaded:=",exp);
	}
	if(readstatus)
	{
		String query = "select processdefid, var_rec_1 from QUEUEVIEW where processinstanceid = '" + workitemName + "'";
		WriteToLog("processdefId fetching InputXml " + query);
		WriteToLog("Session::::::: " + jtsIP+"engine::::::::"+jtsPort);
		String inputXmlSelect = "<?xml version='1.0'?><APSelect_Input><Option>APSelect</Option><QueryString>" + query + "</QueryString><SessionId>" + sessId + "</SessionId><EngineName>" + strEngineName + "</EngineName></APSelect_Input>";
		WriteToLog("processdefId fetching inputXmlSelect " + inputXmlSelect);
		WriteToLog("Session::::::: " + jtsIP+"engine::::::::"+jtsPort); 
		String outputXmlSelect = "";
		try 
		{
			outputXmlSelect = NGEjbClient.getSharedInstance().makeCall(jtsIP, jtsPort, "WebSphere", inputXmlSelect);
		}
		catch (NGException e)
		{
			e.printStackTrace();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		//System.out.println("outputXmlSelect  -->" + outputXmlSelect);
		WriteToLog("processdefId fetching OutputXml " + outputXmlSelect);
		xmlParser = new XMLParser();
		xmlParser.setInputXML((outputXmlSelect));

		String mainCode = xmlParser.getValueOf("MainCode");

		if(mainCode.equals("0"))
		{
			processDefId = xmlParser.getNextValueOf("td");
			folderIndex = Integer.parseInt(xmlParser.getNextValueOf("td"));
			bReturn = true;
		}


		if(bReturn)
		{
			int adddocjtsPort =Integer.parseInt(jtsPort);
			WriteToLog("jtsPort : " + jtsPort);
			sIpAddress = jtsIP;
			WriteToLog("sIpAddress : " + sIpAddress);
			cabinetName = strEngineName;
			WriteToLog("cabinetName : " + cabinetName);
			sessionId = Integer.parseInt(sessId);
			WriteToLog("sessionId : " + sessId);
			groupIndex = "0";
			fileObj = new File(destinationPath);
			WriteToLog("fileObj : " + fileObj + " \n destinationPath : " + destinationPath);
			lFileLength = fileObj.length();
			WriteToLog("lFileLength : " + lFileLength);
			iNoOfPages = 1;
			JPISIsIndex ISINDEX = new JPISIsIndex();
			WriteToLog("ISINDEX : " + ISINDEX);
			JPDBRecoverDocData JPISDEC = new JPDBRecoverDocData();
			WriteToLog("JPISDEC : " + JPISDEC);
			JPISDEC.m_cDocumentType = 'N';
			WriteToLog("JPISDEC.m_cDocumentType : " + JPISDEC.m_cDocumentType);
			JPISDEC.m_nDocumentSize = (int) lFileLength;
			WriteToLog("lFileLength : " + lFileLength);
			WriteToLog("JPISDEC.m_nDocumentSize : " + JPISDEC.m_nDocumentSize);
			JPISDEC.m_sVolumeId = (short)volumeId;
			WriteToLog("volumeId : " + volumeId);
			WriteToLog("JPISDEC.m_sVolumeId : " + JPISDEC.m_sVolumeId);		
			
			volumeId =8;
			
			WriteToLog(" jtsPort : " + jtsPort + " sIpAddress : " + sIpAddress+ " cabinetName : " + cabinetName+ " sessionId : " + sessId+ " lFileLength : " + lFileLength+ " fileObj : " + fileObj+ " ISINDEX : " + ISINDEX+ " JPISDEC : " + JPISDEC+ " JPISDEC.m_nDocumentSize : " + JPISDEC.m_nDocumentSize+ " JPISDEC.m_sVolumeId : " + volumeId+ " JPISDEC.m_sVolumeId : " + volumeId);
			
			try{
				if(JPISDEC.m_nDocumentSize!=0)
				{ 
					WriteToLog("Inside ."+sIpAddress+"  adddocjtsPort"+adddocjtsPort+"  cabinetName "+cabinetName+" volumeId "+volumeId+" destinationPath "+destinationPath+" JPISDEC "+JPISDEC+"  ISINDEX "+ISINDEX);
					CPISDocumentTxn.AddDocument_MT(null, sIpAddress, (short)adddocjtsPort, cabinetName, (short)volumeId, destinationPath, JPISDEC, "", ISINDEX);
					WriteToLog("Document added "+JPISDEC.m_nDocIndex);

				}   
			}catch(Exception e){
				WriteToLog("Exception."+e);
			}
			WriteToLog("Document added to IS successfully."+JPISDEC.m_nDocIndex);
			//System.out.println("Document added to IS successfully."+JPISDEC.m_nDocIndex);
			volumeId =(short)getVolumeID(String.valueOf(folderIndex),strEngineName,sessId,jtsIP ,jtsPort);
			docIndex = JPISDEC.m_nDocIndex + "#" + volumeId;
			WriteToLog("docIndex : " + docIndex);
			String inputXml = "<?xml version='1.0'?><NGOAddDocument_Input><Option>NGOAddDocument</Option><CabinetName>" + cabinetName + "</CabinetName><UserDBId>" + sessId + "</UserDBId><GroupIndex>" + groupIndex + "</GroupIndex><ParentFolderIndex>" + folderIndex + "</ParentFolderIndex><DocumentName>" + documentName + "</DocumentName><CreatedByAppName>"+viewer+"</CreatedByAppName><Comment>" + LLIDocName + "</Comment><VolumeIndex>" + volumeId + "</VolumeIndex><FilePath>" + destinationPath + "</FilePath><ProcessDefId>" + processDefId + "</ProcessDefId><DataDefinition></DataDefinition><ISIndex>" + docIndex + "</ISIndex><NoOfPages>" + iNoOfPages + "</NoOfPages><AccessType>I</AccessType><VersionFlag>Y</VersionFlag><DocumentType>N</DocumentType><DocumentSize>" + lFileLength + "</DocumentSize></NGOAddDocument_Input>";
			WriteToLog("inputXml add document   "+inputXml);   

			String outputXmlAddDocument = "";
			try 
			{
				outputXmlAddDocument = NGEjbClient.getSharedInstance().makeCall(jtsIP, jtsPort, "WebSphere", inputXml);
				
			} 
			catch (NGException e)
			{
				e.printStackTrace();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			try{
				deleteDirectory(new File(sFolderPathN.substring(0, sFolderPathN.lastIndexOf("/"))));
			}catch(Exception e){
				WriteToLog("Exception while removing Folder   "+xmltopass);
			}
			//System.out.println("outputXmlSelect  -->" + outputXmlAddDocument);
			sResponse = outputXmlAddDocument;
			xmltopass=outputXmlAddDocument;
			WriteToLog("outputXmlAddDocument add document123123   "+xmltopass);
			xmltopass=outputXmlAddDocument.substring(outputXmlAddDocument.indexOf("<NGOAddDocument_Output>"));
			WriteToLog("outputXmlAddDocument add document123123231231   "+xmltopass);
			WriteToLog("outputXmlAddDocument add document   "+inputXml);

			WriteToLog("outputXmlAddDocument add document   "+jtsIP);
			WriteToLog("outputXmlAddDocument add document   "+jtsPort);
			WriteToLog("outputXmlAddDocument add document   "+outputXmlAddDocument);
			WriteToLog("outputXmlAddDocument add document  XML TOPASSS   "+xmltopass);

			parserObj.setInputXML((outputXmlAddDocument));
			if(parserObj.getValueOf("Status").equals("0"))
			{
				//start code delete
				//added for count
				WriteToLog("inside if for check status::::::  "+parserObj.getValueOf("Status"));
				query ="select  count(*) from PDBDocument doc, PDBDocumentContent docCont where doc.DocumentIndex = docCont.DocumentIndex and docCont.ParentFolderIndex = '"+folderIndex+"' and doc.name = '"+documentName+"'";
				inputXmlSelect = "<?xml version='1.0'?><APSelect_Input><Option>APSelect</Option><Query>" + query + "</Query><SessionId>" + sessId + "</SessionId><EngineName>" + strEngineName + "</EngineName></APSelect_Input>";
				WriteToLog("count inputXmlSelect " + inputXmlSelect);
				WriteToLog("count query " + query);

				//outputXmlSelect = WFCallBroker.execute(inputXmlSelect, jtsIP, jtsPort, 1);

				try
				{
					outputXmlSelect = NGEjbClient.getSharedInstance().makeCall(jtsIP, jtsPort, "WebSphere", inputXmlSelect);
				} 
				catch (NGException e)
				{
					e.printStackTrace();
				}
				catch (Exception ex) 
				{
					ex.printStackTrace();
				}
				//System.out.println("outputXmlSelect  -->" + outputXmlSelect);
				WriteToLog(" in count outputXmlSelect "+outputXmlSelect);
				xmlParser = new XMLParser();
				xmlParser.setInputXML((outputXmlSelect));

				mainCode = xmlParser.getValueOf("MainCode");

				if(mainCode.equals("0"))
				{
					if(xmlParser.getNextValueOf("td").equals("1") || xmlParser.getNextValueOf("td").equals("0"))
					{
						adddocument = false;
						WriteToLog("doc added in count if condition");
					}
					else
					{
					adddocument = true;
					WriteToLog("doc added in count else condition");
					}
				}
				//added for count
				//added for delete start
				if(adddocument)
				{
					query ="select  docCont.DocumentIndex from PDBDocument doc, PDBDocumentContent docCont where doc.DocumentIndex = docCont.DocumentIndex and docCont.ParentFolderIndex = '"+folderIndex+"' and doc.name = '"+documentName+"' order by docCont.DocumentOrderNo asc";

					inputXmlSelect = "<?xml version='1.0'?><APSelect_Input><Option>APSelect</Option><Query>" + query + "</Query><SessionId>" + sessId + "</SessionId><EngineName>" + strEngineName + "</EngineName></APSelect_Input>";
					WriteToLog("count deletion inputXmlSelect " + inputXmlSelect);
					WriteToLog("count deletion query " + query);  
					//outputXmlSelect = WFCallBroker.execute(inputXmlSelect, jtsIP, jtsPort, 1);
					outputXmlSelect = "";
					try
					{
						outputXmlSelect = NGEjbClient.getSharedInstance().makeCall(jtsIP, jtsPort, "WebSphere", inputXmlSelect);
					} catch (NGException e)
					{
						e.printStackTrace();
					} catch (Exception ex)
					{
						ex.printStackTrace();
					}
					//System.out.println("outputXmlSelect  -->" + outputXmlSelect);
					WriteToLog("count deletion outputXmlSelect " + outputXmlSelect);
					xmlParser.setInputXML((outputXmlSelect));

					mainCode = xmlParser.getValueOf("MainCode");

					if(mainCode.equals("0"))
					{
						docIndex = xmlParser.getNextValueOf("td");
					}
					inputXml = "<?xml version='1.0'?><NGODeleteDocumentExt_Input><Option>NGODeleteDocumentExt</Option><CabinetName>" + cabinetName + "</CabinetName><UserDBId>" + sessId + "</UserDBId><Documents><Document><DocumentIndex>"+docIndex+"</DocumentIndex><ParentFolderIndex>"+folderIndex+"</ParentFolderIndex><ReferenceFlag>N</ReferenceFlag></Document></Documents></NGODeleteDocumentExt_Input>";

					WriteToLog("inputXml delete document   "+inputXml);
					//outputXmlAddDocument = WFCallBroker.execute(inputXml, jtsIP, jtsPort, 1);

					try 
					{
						//outputXmlAddDocument = NGEjbClient.getSharedInstance().makeCall(jtsIP, String.valueOf(jtsPort), appServerType, inputXml);
					} catch (Exception ex)
					{
						ex.printStackTrace();
					}
				}
				else
				{
					WriteToLog("No Document Found");
				}
				//end code delete
				docIndex = parserObj.getValueOf("DocumentIndex");
				outputXml = xmltopass;
			}
			else
			{
				outputXml = "<Record><ErrorCode>1</ErrorCode><ErrorDesc>Error in generating the template111111111. Contact to  Bank BPM Support Team.</ErrorDesc></Record>";
			}
		}
		else
		{
			outputXml = "<Record><ErrorCode>1</ErrorCode><ErrorDesc>Error in generating the template222222222. Contact to  Bank BPM Support Team.</ErrorDesc></Record>";
		}
		//System.out.println("Template Generated Successfully = " + bReturn);
		WriteToLog("Generated Successfully");
		out.println(sResponse);
		
	}
}
catch(Exception exp)
{
	outputXml = "<Record><ErrorCode>1</ErrorCode><ErrorDesc>Error in generating the template333333333. Contact to  Bank BPM Support Team</ErrorDesc></Record>";
	WriteToLog("Error - " + exp);
	WriteToLog("Error overloaded- ",exp);
}
finally
{
	processName = null;
	workitemName = null;
	fileObj = null;
	parserObj = null;
	//ht = null;
}

response.setContentType("text/xml");
response.setHeader("Cache-Control","no-cache");
response.getWriter().write(outputXml); 

%>

<%!


private String APSelectWithcColumnName(String queryString,String sessionid,String cabinatename)
{
	String inXML = "<?xml version='1.0'?><APSelectWithcColumnNames_Input><Option>APSelect</Option><Query>" + queryString + "</Query><EngineName>" + cabinatename + "</EngineName><SessionId>" + sessionid + "</SessionId></APSelectWithcColumnNames_Input>";
	return inXML;
} 

private int getVolumeID(String strItemindex, String strEnginename, String sessionId, String jtsIP, String jtsPort){
		WriteToLog("strItemindex - " + strItemindex);
		WriteToLog("strEnginename - " + strEnginename);
		WriteToLog("sessionId - " + sessionId);
		WriteToLog("jtsIP - " + jtsIP);
		WriteToLog("jtsPort - " + jtsPort);
		int volId=0;
		String docResponse = "";
		XMLParser parserDocProperty=new XMLParser();
		
		try {
			
			String sInputXML = "<NGOGetFolderPropertyInput><Option>NGOGetFolderProperty</Option><CabinetName>"+strEnginename+"</CabinetName><UserDBId>"+sessionId+"</UserDBId><FolderIndex>"+strItemindex+"</FolderIndex><DataAlsoFlag>N</DataAlsoFlag></NGOGetFolderPropertyInput>";
			docResponse = NGEjbClient.getSharedInstance().makeCall(jtsIP, jtsPort, "WebSphere",sInputXML);
			WriteToLog("docResponse - " + docResponse);
			parserDocProperty.setInputXML(docResponse);
			volId = Integer.parseInt(parserDocProperty.getValueOf("ImageVolumeIndex"));
			WriteToLog("volId - " + volId);
		}
		catch (Exception exp) {
			WriteToLog("Exception "+exp);
		}
		
	return volId;
	
}

public void initializeLogger(){
			try{
				Properties properties = new Properties();
				String log4JPropertyFile =  System.getProperty("user.dir") +  System.getProperty("file.separator") + "NGConfig" + System.getProperty("file.separator") + "TFO" +  System.getProperty("file.separator") +  "log4jJSP.properties";
				properties.load(new FileInputStream(log4JPropertyFile));
				PropertyConfigurator.configure(properties);
			}catch(Exception e){
				e.printStackTrace();
			}
		} 
		
	Logger log = Logger.getLogger(_ADDLLIDocument.class);
		
	private void WriteToLog(String strOutput){
		log.info(strOutput);
	}
/*	
private void WriteToLog(String strOutput)
{
	StringBuffer str = new StringBuffer();
	str.append(DateFormat.getDateTimeInstance(0,2).format(new java.util.Date()));
	str.append(" | ");
	str.append(strOutput);
	str.append("\n");
	StringBuffer stringBuffer = null;
	String tmpFilePath="";
	Calendar calendar=new GregorianCalendar();
	String DtString=String.valueOf(""+calendar.get(Calendar.DAY_OF_MONTH) +(calendar.get(Calendar.MONTH) + 1) + calendar.get(Calendar.YEAR));
	try
	{
	stringBuffer = new StringBuffer(50);
	stringBuffer.append(System.getProperty("user.dir"));
							stringBuffer.append(File.separatorChar);
							stringBuffer.append("ApplicationLogs");
							stringBuffer.append(File.separatorChar);
							stringBuffer.append("ProcessLogs");
							stringBuffer.append(File.separatorChar);
							stringBuffer.append("TFO");
							stringBuffer.append(File.separatorChar);
							stringBuffer.append("JSPLogs");				
	File fBackup=new File(stringBuffer.toString());
	if(fBackup == null || !fBackup.isDirectory())
	{
		fBackup.mkdirs();
	}
	stringBuffer.append(File.separatorChar);
	stringBuffer.append("LLI_EDMS_Upload_"+DtString+".xml");
	tmpFilePath = stringBuffer.toString();
	BufferedWriter out = new BufferedWriter(new FileWriter(tmpFilePath, true));
	out.write(str.toString());
	out.close();
	}
	catch (Exception exception)
	{

	}
	finally
	{
		stringBuffer = null;
	}
}
*/
private void WriteToLog(String strOutput, Exception ex)
{
	
	StringBuffer str = new StringBuffer();
	str.append(DateFormat.getDateTimeInstance(0,2).format(new java.util.Date()));
	str.append(" | ");
	str.append(strOutput);
	str.append("\n");
	StringBuffer stringBuffer = null;
	String tmpFilePath="";
	Calendar calendar=new GregorianCalendar();
	String DtString=String.valueOf(""+calendar.get(Calendar.DAY_OF_MONTH) +(calendar.get(Calendar.MONTH) + 1) + calendar.get(Calendar.YEAR));
	try
	{
	stringBuffer.append(System.getProperty("user.dir"));
							stringBuffer.append(File.separatorChar);
							stringBuffer.append("ApplicationLogs");
							stringBuffer.append(File.separatorChar);
							stringBuffer.append("ProcessLogs");
							stringBuffer.append(File.separatorChar);
							stringBuffer.append("TFO");
							stringBuffer.append(File.separatorChar);
							stringBuffer.append("JSPLogs");
	File fBackup=new File(stringBuffer.toString());
	if(fBackup == null || !fBackup.isDirectory())
	{
		fBackup.mkdirs();
	}
	stringBuffer.append(File.separatorChar);
	stringBuffer.append("LLI_Doc_Generation_Log_"+DtString+".xml");
	tmpFilePath = stringBuffer.toString();
	BufferedWriter out = new BufferedWriter(new FileWriter(tmpFilePath, true));
	PrintStream ps = new PrintStream(new File(tmpFilePath));
	ex.printStackTrace(ps);
	out.write(str.toString());
	out.close();
	}
	catch (Exception exception)
	{

	}
	finally
	{
	stringBuffer = null;
	}
}

	public boolean deleteDirectory(File dir){
		if(dir.isDirectory()){
			File[] children = dir.listFiles();
			for(int i=0;i<children.length;i++){
				boolean success = deleteDirectory(children[i]);
				if(!success){
					return false;
				}
			}
		}
		return dir.delete();
	}



%>