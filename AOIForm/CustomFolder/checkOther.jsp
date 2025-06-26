<%@page import="java.util.*,java.text.*"%>
<%@page import="com.newgen.wfdesktop.xmlapi.*,java.io.*,java.util.*"%>
<%@ page import="com.newgen.omni.wf.util.app.*"%>
<jsp:useBean id="customSession" class="com.newgen.custom.wfdesktop.session.WFCustomSession" scope="session"/>
<%
	String sWIName = request.getParameter("WI_NAME");

	String sCabname= customSession.getEngineName();
	String sSessionId = customSession.getDMSSessionId();
	String sUserName = customSession.getUserName();
	String sJtsIp = customSession.getJtsIp();
	String iJtsPort = String.valueOf(customSession.getJtsPort());

	String sQuery="";
	String sInput="";
	String sOutput="";
	
	sQuery= "select * from (select a.name,b.parentfolderindex,documentorderno,b.documentindex,a.versionnumber,a.owner,a.imageindex||'#'||a.volumeid as imageindex,a.documentsize,a.commnt from pdbdocument a,pdbdocumentcontent b,pdbfolder c where a.name in ('illetrate_add', 'CRS_Form', 'cim', 'cust_undertaking', 'delegation', 'emsa_mdsa', 'aof_existing', 'iKYC', 'guardian_und', 'jom', 'jam_investment', 'aof_ntb', 'upgrade_form', 'fund_transfer', 'si_sweep_form') and b.parentfolderindex=c.folderindex and c.name='"+sWIName+"' and a.documentindex=b.documentindex  order by documentorderno desc)";
		
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
		}  else {
			out.println("Error");
		}
	} else {
		out.println("Document not found");
	}
		
%>