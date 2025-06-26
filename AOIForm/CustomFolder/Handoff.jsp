<%@page import="java.util.*,java.text.*"%>
<%@page import="com.newgen.wfdesktop.xmlapi.*,java.io.*,java.util.*,com.newgen.wfdesktop.session.*"%>
<%@ page import="com.newgen.omni.wf.util.app.*"%>
<jsp:useBean id="customSession" class="com.newgen.custom.wfdesktop.session.WFCustomSession" scope="session"/>

<html>
<head>
<link type="text/css" rel="stylesheet" href="/omniapp/javax.faces.resource/themes.css.app?ln=en_us//css">
<title>Mailroom Handoff</title>
<script>
var elements = window.parent.document.querySelectorAll('[id^="blankp1_bp_Ext"]');
for (var i=0; i<elements.length; i++) {
	elements[i].rows ? elements[i].rows[0].style.display='none' : '';
}
</script>
</head>
<%!
		String sCabname= "";
		String sSessionId = "";
		String sUserName = "";
		String sJtsIp = "";
		String iJtsPort = "";
%>
<%
	
		sCabname=customSession.getEngineName();
		sSessionId =customSession.getDMSSessionId();
		sUserName =customSession.getUserName();
		sJtsIp = customSession.getJtsIp();
		iJtsPort = String.valueOf(customSession.getJtsPort());
	
		String pageAction=request.getParameter("action");
		String sameJSP=request.getParameter("sameJSP");
		
		if (sameJSP == null) {
			sameJSP = "";
		}
		
		String count="";
		String sQuery="select count(1) CNT from pdbgroup a,pdbgroupmember b where a.groupindex=b.groupindex and upper(groupname) like '%MAILROOM%' and b.userindex="+customSession.getUserIndex();
		
		String sInput="<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+sQuery+"</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
		
		String sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
		String sMainCode = sOutput.substring(sOutput.indexOf("<MainCode>")+10,sOutput.indexOf("</MainCode>"));
		String grpCount="";	
		if(sMainCode.equalsIgnoreCase("0"))
		{
				grpCount=sOutput.substring(sOutput.indexOf("<CNT>")+5,sOutput.indexOf("</CNT>"));
		}
		
		if("submit".equalsIgnoreCase(pageAction))
		{
			boolean result = false;

			String query = "SELECT COUNT(1) AS COUNT FROM VW_CDTS WHERE TRUNC(TO_DATE(INSERTIONDATE, 'DD-Mon-YYYY hh24:mi:ss')) = TRUNC(sysdate)";
		
			String sInput1="<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+query+"</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
			
			String sOutput1= NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput1);
			
			String sMainCode1 = sOutput1.substring(sOutput1.indexOf("<MainCode>")+10,sOutput1.indexOf("</MainCode>"));
			int count1=0;	
			if(sMainCode1.equalsIgnoreCase("0"))
			{
					count1= Integer.parseInt(sOutput1.substring(sOutput1.indexOf("<COUNT>")+7,sOutput1.indexOf("</COUNT>")));
			}
			if(count1>0){
				result = true;
			}

			if (result && !sameJSP.equals("submit")) {
				%>
					<script>
							var r = window.confirm("Today's BDone has already been completed. Do you want to do it again ?");
							if (r == true) {
								window.location.href = "Handoff.jsp?sameJSP=submit&action=submit&RN="+(Math.random()*10000);
							}
					</script>
				<%
			} 

			if (!result || sameJSP.equals("submit")) {
				 sInput = "<?xml version=\"1.0\"?>"+
						"<APProcedure_Input><Option>APProcedure</Option>"+
						"<ProcName>CDTS_Handoff_New</ProcName>"+
						"<Params>'Param'</Params>"+
						"<NoOfCols>1</NoOfCols>"+                                                       
						"<EngineName>"+sCabname+"</EngineName>"+
						"<SessionId>"+sSessionId+"</SessionId>"+
						"</APProcedure_Input>";  
				 sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
				 sMainCode = sOutput.substring(sOutput.indexOf("<MainCode>")+10,sOutput.indexOf("</MainCode>"));
				 if(sMainCode.equalsIgnoreCase("0"))
				 {
					 sQuery="select PROCESSED_COUNT from CDTS_PROCESSED_COUNT";
					 sInput="<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+sQuery+"</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
			
					sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
					sMainCode = sOutput.substring(sOutput.indexOf("<MainCode>")+10,sOutput.indexOf("</MainCode>"));				
					if(sMainCode.equalsIgnoreCase("0"))
					{
							count=sOutput.substring(sOutput.indexOf("<PROCESSED_COUNT>")+17,sOutput.indexOf("</PROCESSED_COUNT>"));
					}
					%>
					<script>alert('Handoff has been completed. Total application processed: <%= count%>');
					window.location.href = "Handoff.jsp?RN="+(Math.random()*10000);</script>
					<%
				 }			
			}		
		}
%>
<form name="handoff" id="handoff" method="post">
<%
if(!"0".equalsIgnoreCase(grpCount))
{
	%>
	<input type="button" id="btnhandoff" value="Mailroom Handoff" style="margin-left:5px;margin-top: 7px;height:22px !important;" class="buttonstyle advs" onclick="submitapp();"/>
	<%
}
%>
<input type="hidden" id="action" name="action" />
<input type="hidden" id="sameJSP" name="sameJSP" value="" />
</form>
</html>	
<script>
function submitapp()
{
	window.parent.initPopUp();
	window.parent.setPopupMask();
	window.parent.CreateIndicator('mailroomHandoffIndicator');
	//console.log(window.parent.CreateIndicator);
	document.getElementById('action').value="submit";
	document.getElementById('sameJSP').value="";
	document.getElementById("handoff").submit();
	window.parent.RemoveIndicator('mailroomHandoffIndicator');
	window.parent.hidePopupMask();
}
</script>