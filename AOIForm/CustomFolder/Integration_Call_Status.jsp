<%@page import="java.util.*,java.text.*"%>
<%@page import="com.newgen.wfdesktop.xmlapi.*,java.io.*,java.util.*"%>
<%@ page import="com.newgen.omni.wf.util.app.*"%> 
<jsp:useBean id="customSession" class="com.newgen.custom.wfdesktop.session.WFCustomSession" scope="session"/>

<html>
<head>
<title>Integration Call Status</title>
<script>
	
	
	var counter=1;
	var working=0;
	var lastcounter=0;
	var startcounter=0;
	var optionallastfail=0;
	counter=0;
//var tbl=document.getElementById('CallTypeTable');

//callWeb();
window.onbeforeunload=closeandReturn;
function closeandReturn()
{
	//if (document.getElementById('close').disabled==true)
		//return false;
	var mandatoryfail=false;
	var int_running=false;
	var error=false;
	var val="";
	var tbl=document.getElementById('CallTypeTable');
	var i=0;
	for(i=1;i<tbl.rows.length;i++)
	{
		if(document.getElementById("img_"+(i)).style.display=="block")
		{
			//alert("Integration in progress...please close after all integration calls are completed");
			int_running=true;
			//return false;
		}
		//alert('value :'+tbl.rows.item(i).cells.item(2).innerText+"--");
		if((tbl.rows.item(i).cells.item(2).innerText=='Error ' || tbl.rows.item(i).cells.item(2).innerText=='Error' || tbl.rows.item(i).cells.item(2).innerText=='Pending ' || tbl.rows.item(i).cells.item(2).innerText=='Pending') && tbl.rows.item(i).cells.item(5).innerText=='Mandatory')
		{
		
			mandatoryfail=true;
			break;
		}
		else if(tbl.rows.item(i).cells.item(2).innerText=='Error ' || tbl.rows.item(i).cells.item(2).innerText=='Error' || tbl.rows.item(i).cells.item(2).innerText=='Pending ' || tbl.rows.item(i).cells.item(2).innerText=='Pending')
		{
			error=true;
		}
		
	}
	//alert(mandatoryfail);
	//alert(error);
	//alert(val);
	if(mandatoryfail==true)
		val='Mandatory';
	else if(error==true)
		val='Error';
	else
		val='Success';
	if(int_running)
	{	
		val='running';
	}
		//window.returnValue=val;
		
		//window.close();
		window.parent.handleJSPResponse('integrationCallStatus',val);
	
}
function callWeb()
{
		//alert(counter);
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
					//alert(xmlobj.responseText.indexOf("Session Timeout"));
					
					if(xmlobj.responseText.indexOf("Session Timeout") > -1)
					{
						alert("Your Session is time out");	
						window.close();						
					}
					else
					{
						if(xmlobj.responseText.replace(/^\s+|\s+$/g,'').indexOf("0#")>=0 )
						{  
							var tbl=document.getElementById('CallTypeTable');	
							var resval=	xmlobj.responseText.split('#');
							tbl.rows.item(counter).cells.item(3).innerHTML=resval[1];
							tbl.rows.item(counter).style.backgroundColor='limegreen';
							tbl.rows.item(counter).cells.item(2).innerHTML='Success <img id="img_'+(counter)+ '" src="loading.gif" style="display:none" />';
							if(xmlobj.responseText.replace(/^\s+|\s+$/g,'').indexOf("100901")>=0)
								tbl.rows.item(counter).cells.item(6).innerHTML=resval[2];
							else if (xmlobj.responseText.indexOf("Image Already Exists") || xmlobj.responseText.indexOf("Record already exists") >=0)
								tbl.rows.item(counter).cells.item(6).innerHTML=resval[2];
							else
							tbl.rows.item(counter).cells.item(6).innerHTML='';
							document.getElementById("btn_"+(counter)).disabled=true;
							document.getElementById("img_"+(counter)).style.display="none";
							counter++;
							if(tbl.rows.length>counter)
							{
							//alert('ggg'+tbl.rows.item(counter).cells.item(2).innerText+'ggg');
								if(tbl.rows.item(counter).cells.item(2).innerText=='Pending' || tbl.rows.item(counter).cells.item(2).innerText=='Pending ')
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
								
						}
						else
						{
							
							var tbl=document.getElementById('CallTypeTable');
							var resval=	xmlobj.responseText.split('#');						
							tbl.rows.item(counter).style.backgroundColor='red';						
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
								callWeb();
							}
							else
							{
								document.getElementById('retryAll').disabled=false;
								document.getElementById('close').disabled=false;
							}
							
							
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
				if((tbl.rows.item(j).cells.item(2).innerText =='Pending ')||(tbl.rows.item(j).cells.item(2).innerText =='Pending')||(((tbl.rows.item(j).cells.item(2).innerText=='Error') || (tbl.rows.item(j).cells.item(2).innerText =='Error ')) ) )				
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
					xmlobj.open("POST","webserviceCall.jsp?rnd="+rnd+"&WI_NAME="+ workitem +"&seq="+(counter),true);
					xmlobj.send("sd");
			}
			else
			{
				document.getElementById('retryAll').disabled=false;
				document.getElementById('close').disabled=false;
			}
			
			
		}
		document.getElementById('close').disabled=false;
	}
function resetAll()
{
	 counter=1;
	 working=0;
	 lastcounter=0;
	 startcounter=0;
	 optionallastfail=0;
}	
function callsingleWeb(row_num)
{
	document.getElementById("btn_"+(row_num)).disabled=true;
	document.getElementById('retryAll').disabled=true;
	var xmlobj;
	if(window.XMLHttpRequest)
	{	
		xmlobj=new XMLHttpRequest();}
	else
	{
		alert("else");
		xmlobj=new ActiveXObject("Microsoft.XMLHTTP");
	}
	//xx.onreadystatechange=handler;
	xmlobj.onreadystatechange=function()			
	{
		if(xmlobj.readyState==4 && xmlobj.status==200)
		{
			if(xmlobj.responseText.indexOf("Session Timeout") > -1)
			{
				alert("Your Session is time out");	
				window.close();						
			}
			else
			{
				if(xmlobj.responseText.replace(/^\s+|\s+$/g,'').indexOf("0#")>=0 )
				{  
					var tbl=document.getElementById('CallTypeTable');
					var resval=	xmlobj.responseText.split('#');
					tbl.rows.item(row_num).cells.item(3).innerHTML=resval[1];
					//tbl.rows.item(counter).cells.item(6).innerHTML='';
					if(xmlobj.responseText.replace(/^\s+|\s+$/g,'').indexOf("100901")>=0)
						tbl.rows.item(row_num).cells.item(6).innerHTML=resval[2];
					else
					tbl.rows.item(row_num).cells.item(6).innerHTML='';				
					tbl.rows.item(row_num).style.backgroundColor='limegreen';				
					tbl.rows.item(row_num).cells.item(2).innerHTML='Success <img id="img_'+(row_num)+ '" src="loading.gif" style="display:none" />';document.getElementById("btn_"+(row_num)).disabled=true;	
					document.getElementById('retryAll').disabled=false;				
				  document.getElementById("img_"+(row_num)).style.display="none";							
				}
				else
				{
					var resval=	xmlobj.responseText.split('#');					
					var tbl=document.getElementById('CallTypeTable');
					tbl.rows.item(row_num).cells.item(6).innerHTML=resval[2];
					tbl.rows.item(row_num).style.backgroundColor='red';		
					tbl.rows.item(row_num).cells.item(2).innerHTML='Error <img id="img_'+(row_num)+ '" src="loading.gif" style="display:none" />';document.getElementById('retryAll').disabled=false;							
					document.getElementById("btn_"+(row_num)).disabled=false;
				}
			}				
		}
	}
	
	document.getElementById("img_"+(row_num)).style.display="block";
	
	var rnd=Math.floor(Math.random()*1000000+1);
	var tbl=document.getElementById('CallTypeTable');
		xmlobj.open("POST","webserviceCall.jsp?rnd="+rnd+"&WI_NAME="+ workitem +"&seq="+(row_num),true);
		xmlobj.send("sd");	
}	
	
</script>
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

<table cellspacing="1" cellpadding="3" width="100%" >
			<tr>
				<td class="body2" valign="top">
					<table  cellpadding="3" cellspacing="0" width="100%">
						<tr>
							<td  align="left" width="30%" valign="bottom">
								<font color="#330099" face="Microsoft Sans Serif" size="2"><b>User&nbsp;Name:&nbsp;&nbsp;<b><%=customSession.getUserName()%></b></b>
								</font>
							</td>
							<td  align="right" width="70%" valign="bottom">								
								<input Type="button" value="Start/Retry All" id="retryAll" onclick="resetAll();callWeb();"/>
								<input Type="button" id="close" value="Done" onclick="closeandReturn();"/>
							</td>							
						</tr>
					</table>
				</td>
			</tr>
</table>

<div style="overflow:auto; align:center" >	
<table id="CallTypeTable" align="center" border="1" width="100%" cellspacing="1"  bgColor="#ebf3ff">
	<tr style="align:center;background-color:#aaaaaa">
		<th class="body2" width="30%" valign="top" background="/webdesktop/webtop/images/middle.gif"  >
			<font color="#ffffff" face="Microsoft Sans Serif" size="2"><b>Call Type</b></font>
		</th>
		<th class="body2" width="15%" valign="top" background="/webdesktop/webtop/images/middle.gif">
			<font color="#ffffff" face="Microsoft Sans Serif" size="2"><b>Request Datetime</b></font>
		</th>
		<th class="body2" width="5%" valign="top"  background="/webdesktop/webtop/images/middle.gif">
			<font color="#ffffff" face="Microsoft Sans Serif" size="2"><b>Call Status</b></font>
		</th>
		<th class="body2" width="20%" valign="top" background="/webdesktop/webtop/images/middle.gif">
			<font color="#ffffff" face="Microsoft Sans Serif" size="2"><b>Response</b></font>
		</th>
		<th class="body2" width="5%" valign="top"  background="/webdesktop/webtop/images/middle.gif"><font color="#ffffff" face="Microsoft Sans Serif" size="2"><b>Retry</b></font>
		
		</th>
		<th class="body2" width="20%" valign="top" colspan=2 background="/webdesktop/webtop/images/middle.gif" >
			<font color="#ffffff" face="Microsoft Sans Serif" size="2"><b>Error Detail</b></font>
		</th>
		
	</tr>
<%
	try
	{
		String sWIName = request.getParameter("WI_NAME");
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
		
		String sCabname=customSession.getEngineName();
		String sSessionId = customSession.getDMSSessionId();
		String sUserName = customSession.getUserName();
		String sJtsIp = customSession.getJtsIp();
		String iJtsPort = String.valueOf(customSession.getJtsPort());
		//sWIName= sWIName.substring(0,sWIName.length()-1);
		%>
		<script>
		var workitem='<%= sWIName%>';
		var session='<%= sSessionId%>';
		
		
		</script>
		<%
		if(sSessionId=="")
		{
			out.println("sSessionId");
			out.print("<script>alert('Session has expired!!');</script>");
			out.print("<script>window.close();</script>");
			
		}
		if(!sWIName.equals(""))
		{
			sQuery= "SELECT CALL_NAME,STATUS,MANDATE_STATUS,RESPONSE,to_char(REQUEST_DATETIME,'dd/mm/yyyy hh:mi:ss')REQUEST_DATETIME,ERRORDESC FROM USR_0_INTEGRATION_CALLS WHERE WI_NAME = '"+sWIName+"' ORDER BY to_number(CALL_ORDER)";
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
					sStatus =sRecord.substring(sRecord.indexOf("<STATUS>")+8,sRecord.indexOf("</STATUS>"));
					sResponse=sRecord.substring(sRecord.indexOf("<RESPONSE>")+10,sRecord.indexOf("</RESPONSE>"));
					mandatory =sRecord.substring(sRecord.indexOf("<MANDATE_STATUS>")+16,sRecord.indexOf("</MANDATE_STATUS>"));
					RequestDt=sRecord.substring(sRecord.indexOf("<REQUEST_DATETIME>")+18,sRecord.indexOf("</REQUEST_DATETIME>"));
					ErrorDesc=sRecord.substring(sRecord.indexOf("<ERRORDESC>")+11,sRecord.indexOf("</ERRORDESC>"));
					if(sResponse.equalsIgnoreCase(""))
						sResponse="&nbsp;";
					if(ErrorDesc.equalsIgnoreCase(""))
						ErrorDesc="&nbsp;";
					%>
						<TR  <% if(sStatus.equalsIgnoreCase("Success")) out.println("style=\"background-color:limegreen\"");  else  out.println("style=\"background-color:#ebf3ff\"");%>>
						<TD width="30%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt"><%= sCall%></TD>
						<TD width="15%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt"><%= RequestDt%></TD>
						<TD width="5%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt"><%= sStatus%> <img id="img_<%=i%>" src="/webdesktop/webtop/images/loading.gif" style="display:none" /></TD>
						<TD width="20%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt"><%= sResponse%></TD>
						<TD width="5%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt"><input type="button" id="btn_<%=i%>" value="Retry" disabled ="true" onClick="callsingleWeb(<%=i%>);"></TD>
						<TD width="0%" style="display:none;FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt"><%= mandatory%></TD>
						<TD width="20%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt"><%= ErrorDesc%></TD>										
						</TR>
					<%
					i++;
					sOutput=sOutput.substring(sOutput.indexOf("</Record>")+"</Record>".length());
					
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
