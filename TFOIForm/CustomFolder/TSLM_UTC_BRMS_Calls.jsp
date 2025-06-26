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


<html>
<head>

<style>

.buttonStyle {
    font-weight: normal;
    font-style: normal;
    padding: 0px 8px;
    border-radius: 3px;
    outline: none;
    font-size: 14px !important;
    color: #ffffff;
    background-color: #ba1b18;
    font-family: Calibri !important;
    border: 1px solid !important;
    border-color: #ccc !important;
    -webkit-transition-duration: 0.4s;
    transition-duration: 0.4s;
}

element.style {
    word-break: break-word;
    margin-top: 0px;
    margin-bottom: 0px;
}
.buttonStyle {
    font-weight: normal;
    font-style: normal;
    padding: 0px 8px;
    border-radius: 3px;
    outline: none;
    font-size: 14px !important;
    color: #ffffff;
    background-color: #ba1b18;
    font-family: Calibri !important;
    border: 1px solid !important;
    border-color: #ccc !important;
    -webkit-transition-duration: 0.4s;
    transition-duration: 0.4s;
}
.thStyle{
	 min-height: 26px;
     min-width: 128px;
	 background-color: #ba1b18;
}
.buttonStyle {
    color: #ffffff;
}
.button-viewer {
	
    min-height: 26px;
    min-width: 128px;
	margin-right :43px;
	font-weight: bold;
    /* max-width: 256px; */
}
</style>
<title>Integration Call Status</title>
<script>
	
	
	var counter=1;
	var working=0;
	var lastcounter=0;
	var startcounter=0;
	var optionallastfail=0;
	function callWeb()
	{
		alert('Inside CallWeb()');
		try
		{
			document.getElementById('retryAll').disabled=true;
			document.getElementById('close').disabled=true;
			if(counter<=document.getElementById('CallTypeTable').rows.length)
			{
				var xmlobj;
				if(window.XMLHttpRequest)
				xmlobj=new XMLHttpRequest();
				else
				{
					xmlobj=new ActiveXObject("Microsoft.XMLHTTP");
				}
				//xx.onreadystatechange=handler;
				xmlobj.onreadystatechange=function()			
				{
					if(xmlobj.readyState==4 && xmlobj.status==200)
					{
						//alert('response'+xmlobj.responseText);
						//alert('Hello' + xmlobj.status + " " + xmlobj.readyState + " "+xmlobj.responseText);
						if(xmlobj.responseText.indexOf("Session Timeout") > -1)
						{
							alert("Your Session is time out");	
							window.close();						
						}
						else
						{
							alert('Hello');
                            var resval=xmlobj.responseText.split('#');
							alert('Hello');
							//alert('resval[2] '+resval[2]);
							//alert('Hello' + xmlobj.status + " " + xmlobj.readyState + " "+xmlobj.responseText +  " in else");
							//if(xmlobj.responseText.replace(/^\s+|\s+$/g,'').indexOf("0#")>=0 )
							if(xmlobj.responseText.replace(/^\s+|\s+$/g,'').indexOf("0#")>=0 || resval[2].indexOf("Record already exists")>=0)
							{   
								var tbl=document.getElementById('CallTypeTable');	
								var resval=	xmlobj.responseText.split('#');
								tbl.rows.item(counter).cells.item(3).innerHTML=resval[1];
								tbl.rows.item(counter).style.backgroundColor='#2dd280';
								tbl.rows.item(counter).cells.item(2).innerHTML='Success <img id="img_'+(counter)+ '" src="loading.gif" style="display:none" />';
								if(xmlobj.responseText.replace(/^\s+|\s+$/g,'').indexOf("100901")>=0)
									tbl.rows.item(counter).cells.item(6).innerHTML=resval[2];
								else if (xmlobj.responseText.indexOf("Image Already Exists")>=0)
									tbl.rows.item(counter).cells.item(6).innerHTML=resval[2];
								else
								tbl.rows.item(counter).cells.item(6).innerHTML=resval[2];
								document.getElementById("btn_"+(counter)).disabled=true;
								document.getElementById("img_"+(counter)).style.display="none";
								counter++;
								if(tbl.rows.length>counter)
								{
								//alert('ggg'+tbl.rows.item(counter).cells.item(2).innerText+'ggg');
									if(tbl.rows.item(counter).cells.item(2).innerText=='Pending' || tbl.rows.item(counter).cells.item(2).innerText=='Failure')
									{
										alert('1');
										document.getElementById("img_"+(counter)).style.display="block";
										callWeb();					
									}
									else
									{
										alert('2');
										counter++;							
										callWeb();							
									}
								}
									
							}
							else
							{
								//alert('yolo' +resval[2]);
								var tbl=document.getElementById('CallTypeTable');
								var resval=	xmlobj.responseText.split('#');						
								tbl.rows.item(counter).style.backgroundColor='#ba1b18';						
								document.getElementById("img_"+(counter)).style.display="none";
								tbl.rows.item(counter).cells.item(2).innerHTML='Error <img id="img_'+(counter)+ '" src="loading.gif" style="display:none" />';
								tbl.rows.item(counter).cells.item(6).innerHTML=resval[2];
								document.getElementById("btn_"+(counter)).disabled=false;
								//alert(tbl.rows.item(counter).cells.item(5).innerText);
								
								if(!(tbl.rows.item(counter).cells.item(5).innerText=="Mandatory"))
								{	
									//if(optionallastfail==0)
										//optionallastfail=counter;
									counter++;								
//callWeb();
								}
								else
								{
									document.getElementById('retryAll').disabled=false;
									document.getElementById('close').disabled=false;
								}
								//alert('resval[2] '+resval[2]);
								
							}
						}
					}
				}
				var tbl=document.getElementById('CallTypeTable');
				//if(optionallastfail==0 || optionallastfail==-1)
					startcounter=counter;
				//else
				//{
					//startcounter=optionallastfail;
					//optionallastfail=-1;
				//}
				for(var j=startcounter;j<tbl.rows.length ; j++)			
				{
					//alert(tbl.rows.item(j).cells.item(5).innerText);
					/*if(((tbl.rows.item(j).cells.item(2).innerText =='Pending ')||(tbl.rows.item(j).cells.item(2).innerText =='Pending')||(tbl.rows.item(j).cells.item(2).innerText=='Error') || (tbl.rows.item(j).cells.item(2).innerText =='Error ')) && j!=lastcounter)
					*/
					//&&  (tbl.rows.item(j).cells.item(5).innerText!='Mandatory')
					if((tbl.rows.item(j).cells.item(2).innerText =='Pending ')||(tbl.rows.item(j).cells.item(2).innerText =='Failure')||(((tbl.rows.item(j).cells.item(2).innerText=='Error') || (tbl.rows.item(j).cells.item(2).innerText =='Error ')) ) )				
					{
						//alert(lastcounter);
						//tbl.rows.item(j).cells.item(7).innerText='1';
						//tbl.rows.item(j-1).cells.item(7).innerText='0';
						counter=j;
						lastcounter=j;	
						//startcounter=j+1;	
						//alert(counter);					
						break;	
					}
				}	
				
				if(counter!=0 && counter<tbl.rows.length && tbl.rows.item(counter).cells.item(2).innerText!='Success ')
				{
					document.getElementById("img_"+(counter)).style.display="block";
					document.getElementById("btn_"+(counter)).disabled=true;
					var rnd=Math.floor(Math.random()*1000000+1);
					var tbl=document.getElementById('CallTypeTable');	
						//alert("webserviceCall.jsp?WD_UID="+wd_uid+"&rnd="+rnd+"&WI_NAME="+ workitem +"&seq="+(counter));
					//xmlobj.open("POST","webserviceCall.jsp?WD_UID="+wd_uid+"&rnd="+rnd+"&WI_NAME="+ workitem +"&seq="+(counter),true);
						//xmlobj.send("sd");
						out.println("success");
				}
				else
				{
					document.getElementById('retryAll').disabled=false;
					document.getElementById('close').disabled=false;
				}
				//alert('resval[2] '+resval[2]);
				
			}
			document.getElementById('close').disabled=false;
		}
		catch(err)
		{
			//alert("Error " + err);
			document.getElementById('close').disabled=false;
		}
	}
	
	
function resetAll()
{
	 counter=1;
	 working=0;
	 lastcounter=0;
	 startcounter=0;
	 optionallastfail=0;

}	

</script>
</head>
<body>
<FORM name=mainFrm>
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
String utcEligible="";

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
      outputxml.append(sInputXML);
    }
	return outputxml.toString();
}
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
			strFilePath.append("UTC_BRMS_Calls"+DtString+".xml");
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



<table cellspacing="0" cellpadding="2" width="100%" border="0" align ="center">
	  <tr>
		<td valign="top" align="right">
		<!-- <img id="main_image_init" src="/webdesktop/webtop/images/logo.jpg" border="0">-->
		</td>		
	  </tr>
</table>

<table cellspacing="1" cellpadding="3" width="100%" >
			<tr>
				<td class="body2" valign="top">
					<table  cellpadding="3" cellspacing="0" width="100%">
						<tr>
							<td  align="left" width="30%" valign="bottom">
								<!--<font color="#330099" face="Microsoft Sans Serif" size="2"><b>User&nbsp;Name:&nbsp;&nbsp;<b></b></b>-->
								</font>
							</td>
							<td  align="right" width="80%" valign="bottom">								
							<input Type="button" class="buttonStyle button-viewer" value="Start/Retry All" id="retryAll" onclick="callWeb();resetAll()"/> 
								<input Type="button" class="buttonStyle button-viewer" id="close" value="Close" onclick="closeandReturn();"/>
							</td>							
						</tr>
					</table>
				</td>
			</tr>
</table>

<div style="overflow:auto; align:center" >	
<table id="CallTypeTable" align="center" width="100%" cellspacing="1" > 
	<tr>
	<th style="background-color: #ba1b18" width="10%"   height="20%" valign="top" >
			<font color="#ffffff" face="Calibri" size="2"><b>WI NAME</b></font>
		</th>
	
		<th style="background-color: #ba1b18" width="10%" valign="top" >
			<font color="#ffffff" face="Calibri" size="2"><b>CALL NAME</b></font>
		</th>
		<th style="background-color: #ba1b18" width="10%" valign="top" >
			<font color="#ffffff" face="Calibri" size="2"><b>STATUS</b></font>
		</th>
		<th style="background-color: #ba1b18"  width="20%" valign="top" >
			<font color="#ffffff" face="Calibri" size="2"><b>ERROR DESC</b></font>
		</th>	
		
        		
		
	</tr>
	
	<%
try
{
sQuery="";
sInput="";
sOutput="";
String sRecord = "";
String sCall ="";
String sStatus ="";
String sErrorDescription="";
String sResponse="";
sCabname=customSession.getEngineName();
sSessionId = customSession.getDMSSessionId();
sUserName = customSession.getUserName();
sJtsIp = customSession.getJtsIp();
iJtsPort = String.valueOf(customSession.getJtsPort());
String sWIName =request.getParameter("WI_NAME");
String sCallName = "executeUTCBRMSCalls";
String callXML="";
String oldREFNO="";
String REFNO = "";
String Auto_UTC_Trigger="";
XMLParser xp = null;
int returnCode = 1;
		WriteToLog_showpage("Y"," sSessionId"+sSessionId+" *****************************");

	WriteToLog_showpage("Y"," ***************Exception "+sCallName+" *****************************");
	socket = ConnectSocket.getReference("10.101.109.182", 4444);
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
			
			
			
				sInput = prepareAPSelectInputXml("SELECT SEQ_WEBSERVICE.NEXTVAL REFNO FROM DUAL",sCabname,sSessionId);
				sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
				REFNO = sOutput.substring(sOutput.indexOf("<REFNO>")+"<REFNO>".length(),sOutput.indexOf("</REFNO>"));	
				  sQuery= "select CALL_NAME,CALL_OPERATION, CALL_STATUS, STATUS, to_char(CALL_DT,'dd/mm/yyyy hh:mi:ss') CALL_DATE, ERROR_DESCRIPTION from TFO_DJ_INTEGRATION_CALLS WHERE WI_NAME =N'"+sWIName+"' AND CALL_STATUS = N'N'";
				  WriteToLog_showpage("Y"," ***************sQuery "+sQuery+" *****************************");
			  		sInput="<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+sQuery+"</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
					WriteToLog_showpage("Y"," ***************sInput>>>>>>>>>>>>>>>>> "+sInput+" *****************************");
				// sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);	
				if(!sInput.equals(""))
		{
			WriteToLog_showpage("Y"," ***************sInput "+sInput+" *****************************");
			sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);	
			WriteToLog_showpage("Y"," ***************sOutput "+sOutput+" *****************************");
		}
		
		sQuery= "select AUTO_UTC_TRIGGER from TFO_DJ_AUTO_TRIGGER_MASTER WHERE SOURCING_CHANNEL='TSLMSY'";  //ATP-379 15-FEB-24 --JAMSHED
				  WriteToLog_showpage("Y"," ***************sQuery "+sQuery+" *****************************");
			  		sInput="<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+sQuery+"</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
					WriteToLog_showpage("Y"," ***************sInput>>>>>>>>>>>>>>>>> "+sInput+" *****************************");
				// sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);	
				if(!sInput.equals(""))
		{
			WriteToLog_showpage("Y"," ***************sInput "+sInput+" *****************************");
			sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);	
			WriteToLog_showpage("Y"," ***************sOutput "+sOutput+" *****************************");
		}
		
		Auto_UTC_Trigger=sOutput.substring(sOutput.indexOf("<AUTO_UTC_TRIGGER>")+"<AUTO_UTC_TRIGGER>".length(),sOutput.indexOf("</AUTO_UTC_TRIGGER>"));	
					WriteToLog_showpage("Y"," ***************Auto_UTC_Trigger Flag "+Auto_UTC_Trigger+" *****************************");
				 callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>WBG_BRMS_RULES_RESPONSE</InnerCallType><serviceName>InqBusinessRules</serviceName><wiNumber>"+ sWIName +"</wiNumber><REF_NO>"+ REFNO +"</REF_NO><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><SENDERID>UTC</SENDERID><username>appuser</username><credentials>appuser</credentials><InqBusinessRulesReq><ruleFlowGroup>TFO-UTC</ruleFlowGroup><requestChannelName>WMS</requestChannelName><Eligibility><PAYLOAD type=\"UTC_ELIGIBILITY\"><UTC_ELIGIBILITY>"
						+ "<REQUEST_CATEGORY></REQUEST_CATEGORY>"
						+ "<PROFIT_CENTER_CODE>290</PROFIT_CENTER_CODE>"
						+ "<TRANSACTION_AMOUNT></TRANSACTION_AMOUNT></UTC_ELIGIBILITY></PAYLOAD>"
						+ "</Eligibility></InqBusinessRulesReq></APWebService_Input>";
							
							WriteToLog_showpage("Y","executeUTCBRMSCalls input socket: "+ callXML);
																	
							sOutput = socket.connectToSocket(callXML);
							WriteToLog_showpage("Y","executeUTCBRMSCalls callXML sOutput socket"+ sOutput);
							
							if(Auto_UTC_Trigger.equalsIgnoreCase("N")){
								WriteToLog_showpage("Y"," ***************Inside N Flag  *****************************");
								String aUpdateQuery = prepareAPUpdateInputXml("EXT_TFO","UTC_REQUIRED_BRMS","'No'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update integration external query executeUTCBRMSCalls: "+ aUpdateQuery);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",aUpdateQuery);;
								WriteToLog_showpage("Y","update integration external query executeUTCBRMSCalls: " + sOutput);
								
								WriteToLog_showpage("Y","Success executeUTCBRMSCalls sOutput: " + sOutput);
								
								sResponse = "Not Triggered";
								sErrorDescription = "Auto Trigger UTC Flag is N";
								
								%>
						<TR  <% if(sResponse.equalsIgnoreCase("Not Triggered")) 
							out.println("style=\"background-color:#ebf3ff\"");%> >
						<TD width="10%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt;text-align: center"><%= sWIName%></TD>
						<TD width="10%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt;text-align: center"><%= sCallName%> </TD>	
						<TD width="10%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt;text-align: center"><%= sResponse%> </TD>	
						<TD width="10%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt;text-align: center"><%= sErrorDescription%> </TD>			
									
						
						</TR>
					<%
							}
							else if(sOutput.equals("")){
								sResponse = "Failure";
								sErrorDescription = "Web Service Error";
								%>
						<TR  <% if(sResponse.equalsIgnoreCase("Failure")) 
							out.println("style=\"background-color:#ebf3ff\"");%> >
						<TD width="10%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt;text-align: center"><%= sWIName%></TD>
						<TD width="10%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt;text-align: center"><%= sCallName%> </TD>	
						<TD width="10%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt;text-align: center"><%= sResponse%> </TD>	
						<TD width="10%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt;text-align: center"><%= sErrorDescription%> </TD>			
									
						
						</TR>
					<%
								}
								else {
									
								xp = new XMLParser(sOutput);
								returnCode = Integer.parseInt(xp.getValueOf("STATUS_CODE"));
							WriteToLog_showpage("Y","update integration query executeUTCBRMSCalls returnCode: " + returnCode);
							
							if(returnCode==0){
								String sValue = xp.getValueOf("UTC_ELIGIBILITY_OUTPUT");
								
							if(sValue.equalsIgnoreCase("TRUE")){
								WriteToLog_showpage("Y","update integration query executeUTCBRMSCalls  TRUE value: ");
							utcEligible = "Yes";
						}  if(sValue.equalsIgnoreCase("FALSE")){
							WriteToLog_showpage("Y","update integration query executeUTCBRMSCalls  FALSE value: ");
							utcEligible = "No";
						}
						WriteToLog_showpage("Y","UTC_ELIGIBILITY_OUTPUT: "+ utcEligible);
						String sUpdateQuery = prepareAPUpdateInputXml("EXT_TFO","UTC_REQUIRED_BRMS","'" + utcEligible + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update integration external query executeUTCBRMSCalls: "+ sUpdateQuery);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sUpdateQuery);;
								WriteToLog_showpage("Y","update integration external query executeUTCBRMSCalls: " + sOutput);
								sErrorDescription = xp.getValueOf("STATUS_DESC");
								sResponse = "Success";
								
								WriteToLog_showpage("Y","Success executeUTCBRMSCalls sOutput: " + sOutput);
								
			 					String updateQuery = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS","call_status","'Y'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
			                WriteToLog_showpage("Y","update integration query link workitem: "+ updateQuery);
			               sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",updateQuery);
			                  WriteToLog_showpage("Y","sOutput: "+ sOutput);
								//out.println(returnCode+"#"+sResponse+"#"+sWIName);
							} else {
								WriteToLog_showpage("Y","else  "  );
								sErrorDescription = xp.getValueOf("STATUS_DESC");
								sResponse = "Failure";
							   //out.println("1#"+sResponse+"#"+sErrorDescription);
							}
							WriteToLog_showpage("Y","Success executeUTCBRMSCalls sWIName: " + sWIName);
							WriteToLog_showpage("Y","Success executeUTCBRMSCalls sCallName: " + sCallName);
							WriteToLog_showpage("Y","Success executeUTCBRMSCalls sResponse: " + sResponse);
							WriteToLog_showpage("Y","Success executeUTCBRMSCalls sErrorDescription: " + sErrorDescription);
							%>
						<TR  <% if(sResponse.equalsIgnoreCase("Success")) 
							out.println("style=\"background-color:#2dd280\""); 
							else  
							out.println("style=\"background-color:#ebf3ff\"");%> >
						<TD width="10%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt;text-align: center"><%= sWIName%></TD>
						<TD width="10%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt;text-align: center"><%= sCallName%> </TD>	
						<TD width="10%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt;text-align: center"><%= sResponse%> </TD>	
						<TD width="10%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt;text-align: center"><%= sErrorDescription%> </TD>			
									
						
						</TR>
					<%
								}
							
		}
	}
}	
	catch(Exception e)
	{
		out.print("<script>alert('"+e.getMessage()+"');</script>");
	}
%>
</table>
</div>
</FORM>
</body>
</html>
<script>
counter=0;
//var tbl=document.getElementById('CallTypeTable');

//alert('tbl :'+tbl);

//callWeb();
//window.onbeforeunload=closeandReturn;
closeandReturn();
function closeandReturn()
{
	var tbl=document.getElementById('CallTypeTable').rows.item(0).cells.item(3).innerHTML;
	var response=document.getElementById('CallTypeTable').rows.item(1).cells.item(2).innerHTML.trim();
	window.parent.handleJSPResponsePPM('UTC_BRMS_Calls',response);	
	
}
</script>
