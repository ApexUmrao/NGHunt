<%@page import="java.util.*,java.text.*"%>
<%@page import="com.newgen.wfdesktop.xmlapi.*,java.io.*,java.util.*"%>
<%@ page import ="org.apache.log4j.Logger"%>
<%@ page import ="org.apache.log4j.PropertyConfigurator"%>
<%@ page import ="javax.xml.parsers.DocumentBuilder" %>
<%@ page import  ="javax.xml.parsers.DocumentBuilderFactory" %>
<%@ page import  ="org.w3c.dom.Document" %>
<%@ page import  ="org.w3c.dom.Element" %>
<%@ page import  ="org.w3c.dom.Node" %>
<%@ page import  ="org.w3c.dom.NodeList" %>
<%@ page import  ="org.xml.sax.InputSource" %>
<%@ page import="org.xml.sax.SAXException"%>
<%@ page import="javax.xml.parsers.ParserConfigurationException"%>
<%@ page import="java.io.IOException" %>
<%@ page import ="java.io.File"%>
<%@ page import ="java.io.FileOutputStream" %>
<%@ page import ="java.io.OutputStreamWriter" %>
<%@ page import ="java.io.Writer" %>
<%@ page import ="java.text.SimpleDateFormat" %>
<%@ page import ="java.util.Calendar" %>
<%@ page import ="java.util.Date" %>
<%@page import="com.newgen.omni.wf.util.app.*"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.Properties"%>
<%@page import="org.apache.log4j.PropertyConfigurator"%>
<%@page import ="org.apache.log4j.Logger"%>
<jsp:useBean id="customSession" class="com.newgen.custom.wfdesktop.session.WFCustomSession" scope="session"/>
<html>
<head>
<title>Discreprancy Details</title>
</head>
<body>
<FORM name=mainFrm>

<table cellspacing="0" cellpadding="2" width="100%" border="0" align ="center">
	  <tr>
		<td valign="top" align="right">
		 <img id="main_image_init" src="/webdesktop/webtop/images/logo.jpg" border="0">
		</td>		
	  </tr>
</table>



<div style="overflow:auto; align:center" >	
<table id="CallTypeTable" align="center" border="1" width="100%" cellspacing="1"  bgColor="#ebf3ff">
	<tr style="align:center;background-color:#aaaaaa">
		<th class="body2" width="4%" valign="top" background="/webdesktop/webtop/images/middle.gif" >
			<font color="#ffffff" face="Microsoft Sans Serif" size="2"><b>S.No.</b></font>
		</th>
		<th class="body2" width="9%" valign="top" background="/webdesktop/webtop/images/middle.gif"  >
			<font color="#ffffff" face="Microsoft Sans Serif" size="2"><b>Code</b></font>
		</th>
		<th class="body2" width="50%" valign="top" background="/webdesktop/webtop/images/middle.gif">
			<font color="#ffffff" face="Microsoft Sans Serif" size="2"><b>Description</b></font>
		</th>
		<th class="body2" width="9%" valign="top"  background="/webdesktop/webtop/images/middle.gif">
			<font color="#ffffff" face="Microsoft Sans Serif" size="2"><b>Resolved ?</b></font>
		</th>
		<th class="body2" width="9%" valign="top" background="/webdesktop/webtop/images/middle.gif">
			<font color="#ffffff" face="Microsoft Sans Serif" size="2"><b>Resolved Date</b></font>
		</th>
		<th class="body2" width="9%" valign="top"  background="/webdesktop/webtop/images/middle.gif">
			<font color="#ffffff" face="Microsoft Sans Serif" size="2"><b>Received Date</b></font>
		</th>

	</tr>

<%
	initializeLogger();
	String callingFile="Discreprancy Details";
	String LOG_FILE_NAME="Logs";
	writeLog(callingFile,LOG_FILE_NAME,"********************************* Inside Discreprancy Details jsp *********************************");
	
	try
	{
		
		String sWIName = normalizeString(request.getParameter("WI_NAME"));		
		//String sSessionId = normalizeString(request.getParameter("SessionId"));	
		String sSessionId  = customSession.getDMSSessionId();
		String sRecord ;
		String sSno ;
		String sCode ;
		String sDesc ;
		String sResolve ;
		String sResolveDt ;
		String sReceiveDt ;
		String sOutput ="";
		String sQuery ="";
		String sInput ="";
		//String sCabname="adwmswbg";
		//String sSessionId = normalizeString(wfsession.getSessionId());
		String sCabname=customSession.getEngineName();
		//String sSessionId = wfsession.getSessionId();
		String sJtsIp = customSession.getJtsIp();
		String iJtsPort = String.valueOf(customSession.getJtsPort());
		
		writeLog(callingFile,LOG_FILE_NAME,"sCabname : "+sCabname);
		writeLog(callingFile,LOG_FILE_NAME,"sSessionId : "+sSessionId);
		writeLog(callingFile,LOG_FILE_NAME,"sWIName : "+sWIName);
		
	%>
		<script>
			var workitem='<%= sWIName%>';
			var session='<%= sSessionId%>';
		
		</script>
	<%
		if(isEmpty(sSessionId))
		{
			out.print("<script>alert('Session has expired!!');</script>");
			out.print("<script>window.close();</script>");
			
		}
		if(!isEmpty(sWIName))
		{
			sQuery= "SELECT SNO,DISCREPRANCY_CODE,DISCREPRANCY_DESCRIPTION,DISCREPRANCY_RESOLVED,to_char(DISCREPRANCY_RESOLVED_DATE,'dd/MM/yyyy') AS  DISCREPRANCY_RESOLVED_DATE,to_char(DISCREPRANCY_RECEIVED_DATE,'dd/mm/yyyy') AS DISCREPRANCY_RECEIVED_DATE  FROM TFO_DJ_DISCREPRANCY_DETAILS WHERE UPPER(WI_NAME) = UPPER('"+sWIName+"') ";
			
			writeLog(callingFile,LOG_FILE_NAME,"sQuery : "+sQuery);
		}
				
		sInput="<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+sQuery+"</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
		
		writeLog(callingFile,LOG_FILE_NAME,"sInput : "+sInput);
		
		if(!isEmpty(sInput))
		{
			sOutput= NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
		}
		
		writeLog(callingFile,LOG_FILE_NAME,"sOutput : "+sOutput);
		
		if(!isEmpty(sOutput))
		{
			String sMainCode = normalizeString(sOutput.substring(sOutput.indexOf("<MainCode>")+"<MainCode>".length(),sOutput.indexOf("</MainCode>")));
			
			writeLog(callingFile,LOG_FILE_NAME,"sMainCode : "+sMainCode);
			
			if(sMainCode.equalsIgnoreCase("0"))
			{	
				int i=1;				
				
			
				while((sOutput.indexOf("<Record>")>-1))
				{
					sRecord = normalizeString(sOutput.substring(sOutput.indexOf("<Record>")+8,sOutput.indexOf("</Record>")));
					sSno =normalizeString(sRecord.substring(sRecord.indexOf("<SNO>")+5,sRecord.indexOf("</SNO>")));
					sCode =normalizeString(sRecord.substring(sRecord.indexOf("<DISCREPRANCY_CODE>")+19,sRecord.indexOf("</DISCREPRANCY_CODE>")));
					sDesc =normalizeString(sRecord.substring(sRecord.indexOf("<DISCREPRANCY_DESCRIPTION>")+26,sRecord.indexOf("</DISCREPRANCY_DESCRIPTION>")));
					sResolve =normalizeString(sRecord.substring(sRecord.indexOf("<DISCREPRANCY_RESOLVED>")+23,sRecord.indexOf("</DISCREPRANCY_RESOLVED>")));
					sResolveDt =normalizeString(sRecord.substring(sRecord.indexOf("<DISCREPRANCY_RESOLVED_DATE>")+28,sRecord.indexOf("</DISCREPRANCY_RESOLVED_DATE>")));
					sReceiveDt =normalizeString(sRecord.substring(sRecord.indexOf("<DISCREPRANCY_RECEIVED_DATE>")+28,sRecord.indexOf("</DISCREPRANCY_RECEIVED_DATE>")));
					
					
					
						
					
					%>
						<TR>
							<TD width="4%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt"><%= sSno%></TD>
							<TD width="9%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt"><%= sCode%></TD>
							<TD width="50%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt"><%= sDesc%></TD>
							<TD width="9%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt"><%= sResolve%></TD>
							<TD width="9%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt"><%= sResolveDt%></TD>
							<TD width="9%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt"><%= sReceiveDt%></TD>
						</TR>
						
					<%
					i++;
					sOutput=normalizeString(sOutput.substring(sOutput.indexOf("</Record>")+"</Record>".length()));
					
				}
				
				
			}			
		}
		
		
	}	
	catch(Exception e)
	{
		out.print("<script>alert('"+e.getMessage()+"');</script>");
		writeLog(callingFile,LOG_FILE_NAME,"exception+");
	}
%>
<%!
	public static String sFileSep = System.getProperty("file.separator");
	public static String sUserDir = System.getProperty("user.dir");
		
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
		
	Logger log = Logger.getLogger(_Discreprancy_5F_Details.class);
	
	public void writeLog(String callingFile,String sFileName,String sData){
		log.info(sData);
	}		
	/*public static void writeLog(String callingFile,String sFileName,String sData) 
	{
		Calendar cal = Calendar.getInstance();
		File logFile;
		File logFolder; 
		String sLogFolderPath = "";
		
		if(sData==null){
			sData="";
		}
		try
		{	
			//sLogFolderPath = sUserDir+sFileSep+"WebService_Logs";
			sLogFolderPath=sFileSep+"BPMSHARE"+sFileSep+"ProductionLogs"+sFileSep+"ProcessSpecificLogs"+
			sFileSep+"Node1"+sFileSep+"TFO"+sFileSep+"JSPLogs"+sFileSep+"DiscreprancyDetails_Log";
			
			logFolder = new File(sLogFolderPath);
			if (!logFolder.exists())
				logFolder.mkdir();

			logFile = getLogFile(sFileName,sLogFolderPath);
			FileOutputStream fos = new FileOutputStream(logFile,true);
			String outData = "["+callingFile+" : "+(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss")).format(cal.getTime())+"] >> "+sData+"\n";
			Writer out = new OutputStreamWriter(fos,"UTF8");
			out.write(outData);
			out.close();
		}
		catch(Exception ex)
		{
			System.out.println("unable to write");
		}
	} */
		
	public String normalizeString(String str) {
        return ((str == null) ? "" : str.trim());
    }
	
	public boolean isEmpty(String str) {
		if (str != null && str.trim().length() > 0
				&& !str.trim().equalsIgnoreCase("null")) {
			return false;
		}
		return true;
	}
	public static File getLogFile(String sFileName,String sFilePath) throws Exception
	{
		File sLogFile ;
		try
		{
			String sNewFileName = ""; 
			for(int iSeqNum = 1;iSeqNum <= 10;iSeqNum++)
			{
				sNewFileName = sFileName + "_" + iSeqNum;
				sLogFile = new File(sFilePath + sFileSep +sNewFileName+".log"); 
				if(sLogFile.exists())
				{
					if( sLogFile.length()/(1024*1024) <= 2)
					{
						//System.out.println("using same file "+sLogFile.getName());
						return sLogFile;
					}
					else
						continue;
				}
				else
				{
					//System.out.println("creating new file"+sLogFile);
					sLogFile.createNewFile();
					return sLogFile;
					
				}
			}
			//System.out.println("All files used deleting files.");
			//Delete All
			for(int i = 1 ;i <= 10 ;i++ )
			{
				sNewFileName = sFileName + "_" + i;
				sLogFile = new File(sFilePath + sFileSep + sNewFileName+".log");
				sLogFile.delete();
				//System.out.println("Deleted"+sLogFile.getName());				
			}
			
			//Creating a new file with the 1st sequence.
			sNewFileName = sFileName + "_1";
			sLogFile = new File(sFilePath + sFileSep +sNewFileName+".log");
			sLogFile.createNewFile();
			return sLogFile;
		}catch(Exception ex)
		{
			throw ex;
		}
	}
%>
</table>
</div>
</FORM>
</body>
</html>
