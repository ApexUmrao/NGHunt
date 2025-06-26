<%@page import="java.util.*,java.text.*"%>
<%@page import="com.newgen.wfdesktop.xmlapi.*,java.io.*,java.util.*"%>
<%@ page import="com.newgen.omni.wf.util.app.*"%>
<jsp:useBean id="customSession" class="com.newgen.custom.wfdesktop.session.WFCustomSession" scope="session"/>

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
		//alert("INSIDE CALL web"+counter);
		try
		{
			document.getElementById('retryAll').disabled=true;
			//document.getElementById('close').disabled=true;
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
					//alert('Hello tfo 1 ' + xmlobj.responseText + "     ==== " + xmlobj.status);
					if(xmlobj.readyState==4 && xmlobj.status==200)
					{
						//alert(xmlobj.responseText.indexOf("Session Timeout"));
						//alert('Hello tfo' + xmlobj.responseText);
						if(xmlobj.responseText.indexOf("Session Timeout") > -1)
						{
							alert("Your Session is time out");	
							window.close();						
						}
						else
						{
							var resval=xmlobj.responseText.split('#');
							//alert(" returned resval[0] " + resval[0] + ":" );
						//	alert(" returned resval[1] " + resval[1] + ":" );
						//	alert(" returned resval[1] " + resval[2] + ":" );
							//alert('resval[0] '+resval[0] + );
							if(xmlobj.responseText.replace(/^\s+|\s+$/g,'').indexOf("0#")>=0)
							{  
								
								var tbl=document.getElementById('CallTypeTable');	
								var resval=	xmlobj.responseText.split('#');
							//	tbl.rows.item(counter).cells.item(3).innerHTML=resval[1];
								tbl.rows.item(counter).cells.item(3).innerHTML=resval[1];
								tbl.rows.item(counter).cells.item(4).innerHTML=resval[2];
								tbl.rows.item(counter).style.backgroundColor='#2dd280';
								tbl.rows.item(counter).cells.item(2).innerHTML='Y <img id="img_'+(counter)+ '" src="loading.gif" style="display:none" />';
								//alert('green');
								if(xmlobj.responseText.replace(/^\s+|\s+$/g,'').indexOf("100901")>=0)
								{
									tbl.rows.item(counter).cells.item(3).innerHTML=resval[1];
									tbl.rows.item(counter).cells.item(4).innerHTML=resval[2];
								}
								else if (xmlobj.responseText.indexOf("Image Already Exists")>=0)
								{
									tbl.rows.item(counter).cells.item(3).innerHTML=resval[1];
									tbl.rows.item(counter).cells.item(4).innerHTML=resval[2];
								}
								else
								{
								tbl.rows.item(counter).cells.item(3).innerHTML=resval[1];
								tbl.rows.item(counter).cells.item(4).innerHTML=resval[2];
								}
							//alert('after green');
								//document.getElementById("btn_"+(counter)).disabled=true;
								document.getElementById("img_"+(counter)).style.display="none";
								counter++;
								if(tbl.rows.length>counter)
								{
								//alert('ggg'+tbl.rows.item(counter).cells.item(2).innerText+'ggg');
									if(tbl.rows.item(counter).cells.item(2).innerText=='N' || tbl.rows.item(counter).cells.item(2).innerText=='N ')
									{
										//alert('1');
										document.getElementById("img_"+(counter)).style.display="block";
										callWeb();					
									}
									else
									{
									//alert('2');
										counter++;							
										callWeb();							
									}
								}
							//	if(callRequestType=='OpenLinkWIJSP'){
			                  //window.parent.completeWorkItem();	
		                     //} 
								 	
							}
							else
							{
								//alert('inside else');
								var tbl=document.getElementById('CallTypeTable');
								var resval=	xmlobj.responseText.split('#');						
								tbl.rows.item(counter).style.backgroundColor='#ba1b18';						
								document.getElementById("img_"+(counter)).style.display="none";
								tbl.rows.item(counter).cells.item(2).innerHTML='N <img id="img_'+(counter)+ '" src="loading.gif" style="display:none" />';
								tbl.rows.item(counter).cells.item(3).innerHTML=resval[1];
								tbl.rows.item(counter).cells.item(4).innerHTML=resval[2];
								//document.getElementById("btn_"+(counter)).disabled=false;
								//alert("MANDATORY: CHEC" + (tbl.rows.item(counter).cells.item(4).innerText=="Mandatory"));
								
								if(!(tbl.rows.item(counter).cells.item(3).innerText=="Mandatory"))
								{	
							//alert('idhr nahi ana');
									//if(optionallastfail==0)
										//optionallastfail=counter;
									
									counter++;
									document.getElementById('retryAll').disabled=false;
									//alert('counter mandatory: '+ counter);									
									//callWeb();
								}
								else
								{
									document.getElementById('retryAll').disabled=false;
									//document.getElementById('close').disabled=false;
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
				//alert("INSIDE CALL web"+(tbl.rows.length));
				for(var j=startcounter;j<tbl.rows.length ; j++)			
				{
					//alert(tbl.rows.item(j).cells.item(4).innerText);
					/*if(((tbl.rows.item(j).cells.item(2).innerText =='Pending ')||(tbl.rows.item(j).cells.item(2).innerText =='Pending')||(tbl.rows.item(j).cells.item(2).innerText=='Error') || (tbl.rows.item(j).cells.item(2).innerText =='Error ')) && j!=lastcounter)
					*/
					//&&  (tbl.rows.item(j).cells.item(4).innerText!='Mandatory')
					if((tbl.rows.item(j).cells.item(2).innerText =='N ')||(tbl.rows.item(j).cells.item(2).innerText =='N')||(((tbl.rows.item(j).cells.item(3).innerText=='Error') || (tbl.rows.item(j).cells.item(4).innerText =='Error')) ) )				
					{
						//alert(lastcounter);
						//tbl.rows.item(j).cells.item(7).innerText='1';
						//tbl.rows.item(j-1).cells.item(7).innerText='0';
						counter=j;
						lastcounter=j;	
						//startcounter=j+1;	
						//alert('break');					
						break;	
					}
				}
				//alert('COUNTER AF: '+counter);
				//alert((counter!=0 && counter<tbl.rows.length && tbl.rows.item(counter).cells.item(2).innerText!='Y '));
				if(counter!=0 && counter<tbl.rows.length && tbl.rows.item(counter).cells.item(2).innerText!='Y ')
				{
					//alert('WORKING');
					document.getElementById("img_"+(counter)).style.display="block";
					//document.getElementById("btn_"+(counter)).disabled=true;
					var rnd=Math.floor(Math.random()*1000000+1);
					var tbl=document.getElementById('CallTypeTable');	
					//alert("webserviceCallRetry.jsp?WD_UID="+wd_uid+"&rnd="+rnd+"&WI_NAME="+ workitem +"&seq="+(counter)+": "+(tbl.rows.item(j).cells.item(0).innerText)+":");
					//alert('just before execution'+callOperation+' --');
					//alert('callRequestType : '+callRequestType);
					if(callRequestType=="C")
					{	//	alert("FCUBS_Integration_Calls_Executor.jsp?WD_UID="+wd_uid+"&rnd="+rnd+"&WI_NAME="+ workitem +"&seq="+(counter)+				  "&CALL_OPERATION="+callOperation);
						xmlobj.open("POST","FCUBS_Integration_Calls_Executor.jsp?WD_UID="+wd_uid+"&rnd="+rnd+"&WI_NAME="+ workitem +"&seq="+(counter)+"&CALL_OPERATION="+callOperation,true);
					}else if (callRequestType=="A")
					{
						//alert("FCUBS_Integration_Calls_Executor_Amend.jsp?WD_UID="+wd_uid+"&rnd="+rnd+"&WI_NAME="+ workitem +"&seq="+(counter)+"&CALL_OPERATION="+callOperation);
						xmlobj.open("POST","FCUBS_Integration_Calls_Executor_Amend.jsp?WD_UID="+wd_uid+"&rnd="+rnd+"&WI_NAME="+ workitem +"&seq="+(counter)+"&CALL_OPERATION="+callOperation,true);
					}else if(callRequestType=="OpenLinkWIJSP"){
			              
						xmlobj.open("POST","Create_Link_Workitem.jsp?WD_UID="+wd_uid+"&rnd="+rnd+"&WI_NAME="+ workitem +"&seq="+(counter)+"&CALL_OPERATION="+callOperation,true);
						
		           }  
					//alert('WORKING');
					xmlobj.send("sd");
					out.println("success");
				}
				else
				{
					document.getElementById('retryAll').disabled=false;
					//document.getElementById('close').disabled=false;
				}
				//alert('resval[2] '+resval[2]);
				
			}
			//document.getElementById('close').disabled=false;
		}
		catch(err)
		{
			//alert("Error " + err.getMessage());
			//document.getElementById('close').disabled=false;
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
function callsingleWeb()
{
	//alert("ROWNUN: "+row_num);
//alert("CALLNAME: "+sCallName);
	try
	{
	document.getElementById("btn_"+(row_num)).disabled=true;
	document.getElementById('retryAll').disabled=true;
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
			if(xmlobj.responseText.indexOf("Session Timeout") > -1)
			{
				//alert("Your Session is time out");	
				window.close();						
			}
			else
			{
				if(xmlobj.responseText.replace(/^\s+|\s+$/g,'').indexOf("0#")>=0 )
				{  
					var tbl=document.getElementById('CallTypeTable');
					var resval =	xmlobj.responseText.split('#');
					tbl.rows.item(row_num).cells.item(3).innerHTML=resval[1];
					tbl.rows.item(counter).cells.item(4).innerHTML=resval[2];
					//tbl.rows.item(counter).cells.item(6).innerHTML='';
					if(xmlobj.responseText.replace(/^\s+|\s+$/g,'').indexOf("100901")>=0)
						tbl.rows.item(row_num).cells.item(4).innerHTML=resval[2];
					else
					tbl.rows.item(row_num).cells.item(4).innerHTML=resval[2];				
					tbl.rows.item(row_num).style.backgroundColor='#2dd280';				
					tbl.rows.item(row_num).cells.item(3).innerHTML='Success <img id="img_'+(row_num)+ '" src="loading.gif" style="display:none" />';//document.getElementById("btn_"+(row_num)).disabled=true;	
					document.getElementById('retryAll').disabled=false;				
				  document.getElementById("img_"+(row_num)).style.display="none";							
				}
				else
				{
					var resval=	xmlobj.responseText.split('#');					
					var tbl=document.getElementById('CallTypeTable');
					tbl.rows.item(row_num).cells.item(3).innerHTML=resval[1];
					tbl.rows.item(counter).cells.item(4).innerHTML=resval[2];
					tbl.rows.item(row_num).style.backgroundColor='#ba1b18';		
					tbl.rows.item(row_num).cells.item(2).innerHTML='Error <img id="img_'+(row_num)+ '" src="loading.gif" style="display:none" />';document.getElementById('retryAll').disabled=false;							
					//document.getElementById("btn_"+(row_num)).disabled=false;
				}
                               
			}				
		}
	}
	
	document.getElementById("img_"+(row_num)).style.display="block";
	
	var rnd=Math.floor(Math.random()*1000000+1);
	var tbl=document.getElementById('CallTypeTable');	
		if(callRequestType=="C")
		{
		//alert('inside C');
		xmlobj.open("POST","FCUBS_Integration_Calls_Executor.jsp?WD_UID="+wd_uid+"&rnd="+rnd+"&WI_NAME="+ workitem +"&seq="+(row_num)+"&CALL_OPERATION="+callOperation,true);
		}else if(callRequestType=="A"){
		xmlobj.open("POST","FCUBS_Integration_Calls_Executor_Amend.jsp?WD_UID="+wd_uid+"&rnd="+rnd+"&WI_NAME="+ workitem +"&seq="+(row_num)+"&CALL_OPERATION="+callOperation,true);	
		}
		else if(callRequestType=="OpenLinkWIJSP"){
			
			xmlobj.open("POST","Create_Link_Workitem.jsp?WD_UID="+wd_uid+"&rnd="+rnd+"&WI_NAME="+ workitem +"&seq="+(row_num)+"&CALL_OPERATION="+callOperation,true);	
			
		}
		xmlobj.send("sd");	
	  
	  out.println("success");
	}
	catch(err)
	{
	}
}	
	

</script>
</head>
<body>
<FORM name=mainFrm>

<table cellspacing="0" cellpadding="2" width="100%" border="0" align ="center">
	  <tr>
		<td valign="top" align="right">
		<!-- <img id="main_image_init" src="/webdesktop/webtop/images/logo.jpg" border="0"> -->
		</td>		
	  </tr>
</table>

<table cellspacing="1" cellpadding="3" width="100%" >
			<tr>
				<td class="body2" valign="top">
					<table  cellpadding="3" cellspacing="0" width="100%">
						<tr>
							<td  align="left" width="30%" valign="bottom">
								 <!--<font color="#330099" face="Microsoft Sans Serif" size="2"><b>User&nbsp;Name:&nbsp;&nbsp;<b><%=customSession.getUserName()%></b></b> -->
								</font>
							</td>
							<td  align="right" width="70%" valign="bottom">								
								<input Type="button"  class="buttonStyle button-viewer" value="Start/Retry All" id="retryAll" onclick="resetAll();callWeb();"/>
							    <input Type="button" class="buttonStyle button-viewer" id="close" value="Close" onclick="closeandReturn();"/> 
							</td>							
						</tr>
					</table>
				</td>
			</tr>
</table>

<div style="overflow:auto; align:center" >	
<table id="CallTypeTable" align="center"  width="100%" cellspacing="1"  >
	<tr>
		<th style="background-color: #ba1b18" width="20%"   height="20%" valign="top"  >
			<font color="#ffffff" face="Calibri" font-weight: "bold" size="3"><b>Call Name</b></font>
		</th>
		<th style="background-color: #ba1b18" width="15%" valign="top" >
			<font color="#ffffff" face="Calibri" size="3"><b>Request Datetime</b></font>
		</th>
		<th style="background-color: #ba1b18"  width="15%" valign="top" >
			<font color="#ffffff" face="Calibri" size="3"><b>Call Status</b></font>
		</th>
		<th style="background-color: #ba1b18" width="20%" valign="top" >
			<font color="#ffffff" face="Calibri" size="3"><b>Response</b></font>
		</th>
		<!--<th class="body2" width="5%" valign="top"  background="/webdesktop/webtop/images/middle.gif"><font color="#ffffff" face="Microsoft Sans Serif" size="2"><b>REFNO</b></font>
		
		</th>-->
		<th style="background-color: #ba1b18" width="20%" valign="top" colspan=2 >
			<font color="#ffffff" face="Calibri" size="3"><b>Error Detail</b></font>
		</th>		
		
	</tr>
	
<%
	try
	{
		String sWIName = request.getParameter("WI_NAME");
		String sCallRequestType = request.getParameter("callRequestType");
		String sQuery="";
		String sInput="";
		String sOutput="";
		String sRecord = "";
		String sCall ="";
		String sStatus ="";
		String mandatory="";
		String sResponse="";
		String RequestDt="";
		String ErrorDesc="";
		String sCallOperation="";
		
		String sCabname=customSession.getEngineName();
		String sSessionId = customSession.getDMSSessionId();
		String sUserName = customSession.getUserName();
		String sJtsIp = customSession.getJtsIp();
		String wd_uid=sSessionId;
		String iJtsPort = String.valueOf(customSession.getJtsPort());
		//sWIName= sWIName.substring(0,sWIName.length()-1);
		
		if(sSessionId=="")
		{
			out.print("<script>alert('Session has expired!!');</script>");
			out.print("<script>window.close();</script>");
			
		}
		if(!sWIName.equals(""))
		{
	           sQuery= "select CALL_NAME,CALL_OPERATION, CALL_STATUS, STATUS, to_char(CALL_DT,'dd/mm/yyyy hh:mi:ss') CALL_DATE, ERROR_DESCRIPTION from TFO_DJ_INTEGRATION_CALLS WHERE WI_NAME =N'"+sWIName+"' AND CALL_STATUS = N'N'";
			  		
		}
				
		sInput="<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+sQuery+"</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
		
		if(!sInput.equals(""))
		{
			sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);	
		}
		
		if(!sOutput.equals(""))
		{
			String sMainCode = sOutput.substring(sOutput.indexOf("<MainCode>")+10,sOutput.indexOf("</MainCode>"));
			
			if(sMainCode.equalsIgnoreCase("0"))
			{	int i=1;				
				while((sOutput.indexOf("<Record>")>-1))
				{
					sRecord = sOutput.substring(sOutput.indexOf("<Record>")+8,sOutput.indexOf("</Record>"));
					sCall =sRecord.substring(sRecord.indexOf("<CALL_NAME>")+11,sRecord.indexOf("</CALL_NAME>"));
					sStatus =sRecord.substring(sRecord.indexOf("<CALL_STATUS>")+13,sRecord.indexOf("</CALL_STATUS>"));
					mandatory =sRecord.substring(sRecord.indexOf("<STATUS>")+8,sRecord.indexOf("</STATUS>"));
					RequestDt=sRecord.substring(sRecord.indexOf("<CALL_DATE>")+11,sRecord.indexOf("</CALL_DATE>"));
					ErrorDesc=sRecord.substring(sRecord.indexOf("<ERROR_DESCRIPTION>")+19,sRecord.indexOf("</ERROR_DESCRIPTION>"));
					sCallOperation =sRecord.substring(sRecord.indexOf("<CALL_OPERATION>")+16,sRecord.indexOf("</CALL_OPERATION>"));
					if(sResponse.equalsIgnoreCase(""))
						sResponse="&nbsp;";
					if(ErrorDesc.equalsIgnoreCase(""))
						ErrorDesc="&nbsp;";
					
					
					%>
						<TR  <% if(sStatus.equalsIgnoreCase("Success")) 
							out.println("style=\"background-color:#2dd280\""); 
							else  
							out.println("style=\"background-color:#ebf3ff\"");%> >
						<TD width="30%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt"><%= sCall%></TD>
						<TD width="15%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt"><%= RequestDt%></TD>
						<TD width="5%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt"><%= sStatus%> <img id="img_<%=i%>" src="/webdesktop/webtop/images/loading.gif" style="display:none" /></TD>
						<!--<TD width="20%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt"></TD>-->
						<!--<TD width="5%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt">12345</TD> -->
						
						<TD width="20%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt"><%= ErrorDesc%></TD>	
						<!--<TD width="0%" style="display:none;FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt"></TD>-->	
						<TD width="20%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt"><%= ErrorDesc%></TD>									
						</TR>
					<%
					i++;
					sOutput=sOutput.substring(sOutput.indexOf("</Record>")+"</Record>".length());
					
				}
			}			
		}
		%>
		<script>
		var workitem='<%= sWIName%>';
		var session='<%= sSessionId%>';
		var wd_uid='<%= wd_uid%>';
		var callRequestType='<%= sCallRequestType%>';
		var callOperation='<%= sCallOperation%>';
		</script>
		<%
		
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

//callWeb();
//window.onbeforeunload=closeandReturn;
function closeandReturn()
{
	//if (document.getElementById('close').disabled==true)
		//return false;
	var mandatoryfail=false;
	var error=false;
	var val="";
	var tbl=document.getElementById('CallTypeTable');
	var i=0;
	for(i=1;i<tbl.rows.length;i++)
	{
		if((tbl.rows.item(i).cells.item(2).innerText=='Error ' || tbl.rows.item(i).cells.item(2).innerText=='Pending ') && tbl.rows.item(i).cells.item(4).innerText=='Mandatory')
		{
		
			mandatoryfail=true;
			break;
		}
		else if(tbl.rows.item(i).cells.item(2).innerText=='Error ' || tbl.rows.item(i).cells.item(2).innerText=='Pending '||tbl.rows.item(i).cells.item(3).innerText=='Failure')
		{
			error=true;

		}
		
	}

	if(mandatoryfail==true)
		val='Failed';
	else if(error==true)
		val='Failed';
	else
		val='Success';
		if(callRequestType=='OpenLinkWIJSP'){
			window.parent.handleJSPResponsePC('OpenLinkWIJSP',val);	
		}else{
				window.parent.handleJSPResponse('FCUBS_INTEGRATION_CALLS',val);	
			}
	
	//setPMSFData('btnSubmit',val);
	
	//window.close();
}
</script>
