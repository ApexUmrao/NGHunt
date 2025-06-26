<%@page import="java.util.*,java.text.*"%>
<%@page import="com.newgen.wfdesktop.xmlapi.*,java.io.*,java.util.*"%>
<%@ page import="com.newgen.omni.wf.util.app.*"%>
<jsp:useBean id="customSession" class="com.newgen.custom.wfdesktop.session.WFCustomSession" scope="session"/>


<%
String sWIName = request.getParameter("WI_NAME");
String doccount = request.getParameter("DOC_COUNT");
int previnswlcount;
if(doccount.equalsIgnoreCase(""))
{
previnswlcount=0;
}
else
{
previnswlcount=Integer.parseInt(doccount);
}
previnswlcount=previnswlcount+1;
	// Updated by Abhay after upgrade
	String sCabname= customSession.getEngineName();
	String sSessionId =  customSession.getDMSSessionId();
	String sUserName = customSession.getUserName();
	String sJtsIp =customSession.getJtsIp();
	//String wd_uid=request.getParameter("session");
	String iJtsPort = String.valueOf(customSession.getJtsPort());
String callXML="";
String sQuery="";
String sInput="";
sInput="<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>select count(1) INSTANT from pdbdocument a,pdbdocumentcontent b,pdbfolder c where a.name='Instant_Welcome_Letter' and b.parentfolderindex=c.folderindex and c.name='"+sWIName+"' and a.documentindex=b.documentindex</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
	String sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
	String sCount=sOutput.substring(sOutput.indexOf("<INSTANT>")+9,sOutput.indexOf("</INSTANT>"));
	//out.println("Not done"+sCount);
	//if ( sCount.equalsIgnoreCase("0"))
		//out.println("Not done");
	//else
		
		int currinswlcount;
if(sCount.equalsIgnoreCase(""))
{
currinswlcount=0;
}
else
{
currinswlcount=Integer.parseInt(sCount);
}
		
		if ( currinswlcount==previnswlcount)
		{
		
		sQuery= "select * from (select b.parentfolderindex,documentorderno,b.documentindex,a.versionnumber,a.owner,a.imageindex||'#'||a.volumeid as imageindex,a.documentsize,a.commnt from pdbdocument a,pdbdocumentcontent b,pdbfolder c where a.name='Instant_Welcome_Letter' and b.parentfolderindex=c.folderindex and c.name='"+sWIName+"' and a.documentindex=b.documentindex  order by documentorderno desc) where rownum=1";
			
		
		
		sInput="<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+sQuery+"</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
		
		if(!sInput.equals(""))
		{
			sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
		}
		if(!sOutput.equals(""))
		{
			String sMainCode = sOutput.substring(sOutput.indexOf("<MainCode>")+10,sOutput.indexOf("</MainCode>"));
			
			if(sMainCode.equalsIgnoreCase("0"))
			{	
			out.println("Success"+sOutput);
			}
			}
		
		
		
		}
	else
		out.println("Not done");
%>
