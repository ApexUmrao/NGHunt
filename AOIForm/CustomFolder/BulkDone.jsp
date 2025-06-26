<jsp:useBean id="customSession" class="com.newgen.custom.wfdesktop.session.WFCustomSession" scope="session"/>
<%@page import="java.util.*,java.text.*"%>
<%@page import="com.newgen.wfdesktop.xmlapi.*,java.io.*,java.util.*"%>
<html>
<head>
<link type="text/css" rel="stylesheet" href="/omniapp/javax.faces.resource/themes.css.app?ln=en_us//css">
<!--<link type="text/css" rel="stylesheet" href="/webdesktop/javax.faces.resource/stylesheet.css.app?ln=en_us//css">
<style>
iframe#iframe_Ext2 {
	height: 90% !important;
}
</style>-->
<title>Bulk Done</title>
<script>
/*var row=window.parent.document.getElementById('blankp1_bp_Ext3_bluepanel').rows;
row[0].style.display='none';*/
var elements = window.parent.document.querySelectorAll('[id^="blankp1_bp_Ext"]');
for (var i=0; i<elements.length; i++) {
	elements[i].rows ? elements[i].rows[0].style.display='none' : '';
}
</script>
</head>

<%
		String sCabname=customSession.getEngineName();
		String sSessionId =customSession.getDMSSessionId();
		String sUserName = customSession.getUserName();
		String sJtsIp = customSession.getJtsIp();
		String iJtsPort = String.valueOf(customSession.getJtsPort());
		String pageAction=request.getParameter("action");
		
%>

<form name="handoff" id="handoff" method="post">

	<input type="button" id="btnBdone" value="BDone" style="margin-left:5px;margin-top: 7px;height:22px !important;" class="buttonstyle advs" onclick="submitapp();"/>

<input type="hidden" id="action" name="action" />
</form>
</html>
<script>
function submitapp()
{
	
	var selectedWIInfo='<Workitems>';
	
	var ctrlTableId="wlf:pnlResult";
    var checkboxId="wlf:cb_";
	if (window.parent.document.querySelectorAll('[id^="iframe_Int"]').length == 3) {
		//var ctrlTable=window.parent.document.getElementById('iframe_Int40').contentWindow.document.getElementById(ctrlTableId);
		var tableIframe = window.parent.document.querySelectorAll('[id^="iframe_Int"]')[2];
		var ctrlTable=tableIframe.contentWindow.document.getElementById(ctrlTableId);
	
		var pid="";
		var strSelectedIndex="";
		if(ctrlTable!=null)
		{
		
			var rowCount = ctrlTable.tBodies[0].rows.length;
			if(rowCount>0) {
				for(var iCount = 0; iCount < rowCount-1;iCount++)
				{
					
					//var wiClicked=window.parent.document.getElementById('iframe_Int40').contentWindow.document.getElementById(checkboxId+iCount);
					var wiClicked=tableIframe.contentWindow.document.getElementById(checkboxId+iCount);
					if(wiClicked.checked){
						selectedWIInfo=selectedWIInfo+'<Workitem>';
						if(strSelectedIndex.length==0){
							strSelectedIndex=strSelectedIndex+iCount;
						}else{
							strSelectedIndex=strSelectedIndex+","+iCount;
						}
						//var jsonOutput=window.parent.document.getElementById('iframe_Int40').contentWindow.document.getElementById("wlf:hjn"+(iCount+1)).innerHTML;
						var jsonOutput=tableIframe.contentWindow.document.getElementById("wlf:hjn"+(iCount+1)).innerHTML;
						//jsonOutput= eval("("+jsonOutput+")");
						jsonOutput = jsonOutput.split("\u00B6");
						var arrobjJsonOutput= jsonOutput;
						//for(var i=0;i<arrobjJsonOutput.length;i++){
						  //  var outputJson=arrobjJsonOutput[i];
						  //  var objJson=outputJson.Output;
							//if(objJson.Name=='ProcessInstanceID'){
								pid =arrobjJsonOutput[3];
							//}
						//}
						selectedWIInfo=selectedWIInfo+'<ProcessInstanceId>'+pid.substring(1)+'</ProcessInstanceId>';
						selectedWIInfo=selectedWIInfo+'<WorkitemId>'+arrobjJsonOutput[0]+'</WorkitemId>';
						selectedWIInfo=selectedWIInfo+'<QueueName>'+arrobjJsonOutput[7].substring(1)+'</QueueName>';
						selectedWIInfo=selectedWIInfo+'</Workitem>';
					}
				}
			}
		}
	}
	
	selectedWIInfo=selectedWIInfo+'</Workitems>';
	
	tableIframe.contentWindow.CustomButtonClick(selectedWIInfo);
	//document.getElementById('action').value="submit";

	//document.getElementById("handoff").submit();

}
</script>