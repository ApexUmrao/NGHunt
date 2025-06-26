<jsp:useBean id="customSession" class="com.newgen.custom.wfdesktop.session.WFCustomSession" scope="session"/>

<%@ page import="ISPack.ISUtil.*,ISPack.*,java.io.*,Jdts.DataObject.JPDBInteger, Jdts.DataObject.JPDBISAddress,Jdts.DataObject.*,Jdts.*,java.net.*,java.awt.*"%>
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
<%@page import="org.apache.commons.codec.binary.Base64"%>
<%@page import="java.awt.Toolkit"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.newgen.omni.jts.cmgr.XMLParser"%>
<%@ page import="com.newgen.wfmonitor.xmlapi.*"%>
<%@ page import="com.newgen.wfdesktop.xmlapi.*" %>
<%@ page import="ISPack.ISUtil.*,ISPack.*,java.io.*,Jdts.DataObject.JPDBInteger, Jdts.DataObject.JPDBISAddress,Jdts.DataObject.*,Jdts.*,java.net.*,java.awt.*"%>
<%@ page import="com.newgen.wfdesktop.util.*" %>
<%@ page import=="com.newgen.iforms.user.tfo.util.ConnectSocket"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.Properties"%>
<%@page import="org.apache.log4j.PropertyConfigurator"%>
<%@page import ="org.apache.log4j.Logger"%>
<script>
alert('jj');
</script>
<%
initializeLogger();
sQuery="";
sInput="";
sOutput="";
String sRecord = "";
String sCall ="";
String sStatus ="";
String returnXML="";
String sReason ="";
String sErrorDescription="";
String sResponse="";
String sContractRefNumber="";

sCabname=customSession.getEngineName();
sSessionId = customSession.getDMSSessionId();
sUserName = customSession.getUserName();
sJtsIp = customSession.getJtsIp();
iJtsPort = String.valueOf(customSession.getJtsPort());

String sWIName = request.getParameter("WI_NAME");
String sCallName = request.getParameter("CALL_OPERATION");
String callXML="";
String sResVal="";
String ErrorDesc="";
String SLNO="";
String errCase;
String oldREFNO="";
String REFNO = "";

XMLParser xp = null;
int returnCode = 1;
String sExternalRefNumber = "";
String sAdvisingRefNumber = "";
String sIssuingLCRefNumber = "";
String sOperationCode = "";
String sContractCurrency = "";
String sContractAmount = "";
String sNegativeTolerance = "";
String sPositiveTolerance = "";
String sExpiryDate = "";
String sCustomerId = "";
String sProductCode = "";
String sFromPlace = "";
String sToPlace = "";
String sLastShipmentDate = "";
String sPortOfDischarge = "";
String sPortOfLoading = "";
String sGoodsDescription = "";
String sUdfFieldValue = "";
String sRequestCategoryCode = "";
String sRequestTypeCode = "";
String sRelationshipType = "";
String sProcessType = "";
String sMessageType = "";
String sLimitType = "";
String limitSerialNumber = "";
String limitPartyType = "";
String limitLinkageType = "";
String limitPercentContribution = "";
String limitAmountTag = "";
String limitLineRefNumber = "";
String customerType = "";
limitLineCustomerId = "";
sRiskParticipation="N";
String CurrDate = "";
String sBranchCreateCode = "";
String sLimitTracking = "";
String sContractLimitTag = "";

String sStage="";
String sLcReferenceNo="";
String sLiquidationAmount="";
String sMaturityDate="";
String sTenorDays="";
String stransitDays="";
String sBaseDate="";
String sDaysAfterBaseDate="";
String sPartyType="";
String sLimitTrackFlag="";
String sAcceptComFromDate="";
String sAcceptComToDate="";
String sCode="";
String sDescription="";
String sResolved="";
String sResolvedDate="";
String sReceivedDate="";
String sLimitOperation="";
String sLiquidationDate="";
String sFCUBSCurrentDate="";
String returnResponse="";
WriteToLog_showpage("Y"," ***************Exception "+sCallName+" *****************************");
try
{	
    //socket = ConnectSocket.getReference("10.101.109.182", 4444);
	String sQueryBPM = "SELECT (SELECT VALUE FROM BPM_SERVICE_CONFIG WHERE KEY = 'SOCKETIP') IP,(SELECT VALUE FROM BPM_SERVICE_CONFIG WHERE KEY = 'SOCKETPORT') PORT FROM DUAL";
	String sInputBPM = prepareAPSelectInputXml(sQueryBPM,sCabname,sSessionId);
	WriteToLog_showpage("Y"," ***************Ref number Input "+sInputBPM+" *****************************");
	String sOutputBPM = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputBPM);
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
	sQuery="SELECT COUNT(1) AS COUNT_WI FROM PDBCONNECTION WHERE RANDOMNUMBER = '"+sSessionId+"'";
	sInput=prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
	WriteToLog_showpage("Y"," ***************Checking connection "+sInput+" *****************************");
	sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);	
	String sCount=sOutput.substring(sOutput.indexOf("<COUNT_WI>")+10,sOutput.indexOf("</COUNT_WI>"));
	if(sCount.equalsIgnoreCase("0"))
	{
		out.println("Session Timeout");
	}
	else
	{
		if(!sWIName.equals(""))
		{
			
			
				if(true){
				sInput = prepareAPSelectInputXml("SELECT SEQ_WEBSERVICE.NEXTVAL REFNO FROM DUAL",sCabname,sSessionId);
				sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
				REFNO = sOutput.substring(sOutput.indexOf("<REFNO>")+"<REFNO>".length(),sOutput.indexOf("</REFNO>"));	
try
				{	        callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>BPMModify</InnerCallType><wiNumber>"+ sWIName +"</wiNumber><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><mode>C</mode><channelName>TFO_RULES</channelName><correlationId>"+REFNO+"</correlationId><channelRefNumber>" + REFNO + "</channelRefNumber><sysrefno>" + sOperationCode + "</sysrefno><processName>TFO</processName><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";
							WriteToLog_showpage("Y","modTradeLCContract_Oper input socket: "+ callXML);
							sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sCallName +"',"+ convertToPlainString(callXML) +",'"+ REFNO +"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","modTradeLCContract_Oper callXML TFO_DJ_INTEGRATION_CALLS_DTLS: "+ sQuery + " *****************************");	
							sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;						
							WriteToLog_showpage("Y"," insert modTradeLCContract_Oper sOutput: "+ sOutput);											
							sOutput = socket.connectToSocket(callXML);
							WriteToLog_showpage("Y","modTradeLCContract_Oper callXML sOutput socket"+ sOutput);
							
							String queryUpdateXML = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'" + sOutput + "'","WI_NAME = '"+ sWIName +"'  and REF_NUM = '"+REFNO+"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","update sOutputUpdateXML input: "+ queryUpdateXML);
							String sOutputUpdateXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",queryUpdateXML);;
							WriteToLog_showpage("Y","update sOutputUpdateXML output: " + sOutputUpdateXML);
								
							
							xp = new XMLParser(sOutput);
							returnCode = Integer.parseInt(xp.getValueOf("StatusCode"));
							String nextValue ="";
							if(0==returnCode){
								sErrorDescription = xp.getValueOf("StatusMessage");
								sResponse = "Success";
								
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
								
			 					sQuery = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS","call_status","'Y'","WI_NAME = '"+ sWIName +"' and  call_operation='Linked_Workitem_Creation'", sCabname, sSessionId);
			                  WriteToLog_showpage("Y","update integration query link workitem: "+ sQuery);
							  sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
			                  WriteToLog_showpage("Y","sOutput: "+ sOutput);
							  returnResponse="0"+"#"+sResponse+"#"+xp.getValueOf("WINumber");
								//out.println(returnCode+"#"+sResponse+"#"+xp.getValueOf("WINumber"));
							} else {
								WriteToLog_showpage("Y","else  "  );
								sErrorDescription = xp.getValueOf("StatusMessage");
								sResponse = "Failure";
								returnResponse="1#"+sResponse+"#"+sErrorDescription;
							    //out.println("1#"+sResponse+"#"+sErrorDescription);
							}
						
					
					
					
				}
				catch(Exception e)
				{
					WriteToLog_showpage("Y","Exception : " +e.getMessage());
					e.printStackTrace();
					returnResponse="Exception is"+e.getMessage();
					//out.println("Exception is"+e.getMessage());
					//WriteToLog_showpage("Y","Exception : " +e.getMessage());
				}
				finally
				{
					WriteToLog_showpage("Y","sResponse="+returnResponse);	
					out.println(returnResponse); 
				}					
			}
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
String sInput1="";
String sQuery="";
String sInput="";
String sOutput="";
String sUserName="";
String sCabname="";
String sSessionId ="";
String sJtsIp ="";
String iJtsPort = "";
String limitLineCustomerId = "";
String sRiskParticipation  ="N";
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

public String normalizeString(String str) {
    try {
      if ((str == null) ||(str.trim().equalsIgnoreCase("null"))) {
        return "";
      }
	}
    catch (Exception e) {
      WriteToLog_showpage("Y"," Excpt normalizeString " + e);
    }
    return str.trim();
}
 
private String convertToPlainString(String sInputXML){
	StringBuffer outputxml = new StringBuffer();
	WriteToLog_showpage("Y"," sInputXML.length() " + sInputXML.length());
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
		
    Logger log = Logger.getLogger(_Create_5F_Link_5F_Workitem.class);
	
private void WriteToLog_showpage(String flag,String strOutput){
	log.info(strOutput);
}
	
	/*
private void WriteToLog_showpage(String flag,String strOutput)
{
    StringBuffer str = new StringBuffer();
    str.append(DateFormat.getDateTimeInstance(0,2).format(new java.util.Date()));
    str.append("\n");
    str.append(strOutput);
    str.append("\n");

    StringBuffer strFilePath = null;
    String tmpFilePath="";
    try 
    {
		
		if("Y".equalsIgnoreCase(flag) || "I".equalsIgnoreCase(flag)){
			Calendar calendar=new GregorianCalendar();
			String DtString=String.valueOf(""+calendar.get(Calendar.DAY_OF_MONTH) +(calendar.get(Calendar.MONTH) + 1) + calendar.get(Calendar.YEAR));
			strFilePath = new StringBuffer(50);
			strFilePath.append(System.getProperty("user.dir"));
			strFilePath.append(File.separatorChar);
			strFilePath.append("ApplicationLogs");
			strFilePath.append(File.separatorChar);
			strFilePath.append("ProcessLogs");
			strFilePath.append(File.separatorChar);
			strFilePath.append("TFO");
			strFilePath.append(File.separatorChar);
			strFilePath.append("JSPLogs");
			strFilePath.append(File.separatorChar);
			strFilePath.append("Create_Link_Workitem"+DtString+".xml");
			tmpFilePath = strFilePath.toString();
			
			//PrintStream out = new PrintStream(new FileOutputStream(tmpFilePath), true);
			BufferedWriter out = new BufferedWriter(new FileWriter(tmpFilePath, true));
			
			out.write(str.toString());
			out.close();
			
		}
    } catch (Exception exception) {
    } finally {
        strFilePath = null;
    }    
}
*/
private static String StackTraceToString_showpage(Exception e) {
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        e.printStackTrace(printWriter);
        return result.toString();
}

private String characterHandler(String str) {
        
        return str.replaceAll("'","''");
}



%>
