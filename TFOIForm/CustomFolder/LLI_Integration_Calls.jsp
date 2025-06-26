<%@page import="java.util.*,java.text.*"%>
<%@page import="com.newgen.wfdesktop.xmlapi.*,java.io.*,java.util.*"%>
<%@page import="com.newgen.omni.wf.util.app.*"%>
<%@page import="com.newgen.omni.jts.cmgr.XMLParser"%>

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
	var row='';
	var cell='';
	var startcounter=0;
	var optionallastfail=0;
	var pageURL = document.URL;
	var URLWithParam = pageURL.substr(0,pageURL.indexOf('session')-1).replace("LLI_Integration_Calls.jsp",'LLI_Basic.jsp');
	var vesselID="";
	var vesselIDSelected = "";

	function validation(){
	//alert('inside done');
	var sSelectedValue="";
	var sColString="";
	var ID="";
	var count=0;
	//var cols=document.getElementById('BasicVesselDetails').getElementsByTagName('td'),colsLen=cols.length,i=-1;
	var colsLen = document.getElementById('BasicVesselDetails').rows.length;
	var i =0;
	var isVesselselected =0;
	while(i<colsLen-1){
		//alert(document);
		if(document.getElementById('RADIO_ID_'+i).checked == true)
		{
			//alert('VESSEL_ID_'+i);
			//alert(document.getElementById('VESSEL_ID_'+i).innerHTML);
			vesselIDSelected = document.getElementById('VESSEL_ID_'+i).innerHTML;
			isVesselselected =1;
			break;
		} 
		i++;
		}
	if(isVesselselected==0){
		//alert("Please select atleast one row.");
		return false;
	}	
	else{
		//alert(vesselIDSelected);
		document.getElementById('CallTypeTable').deleteRow(2);
		callWeb();	
	}
}
	function callWeb()
	{
	//alert('inside callweb'+counter);
		//alert(URLWithParam);
		//alert("INSIDE CALL web"+counter);
		try
		{
			document.getElementById('retryAll').disabled=true;
			//document.getElementById('close').disabled=true;
			if(counter<=document.getElementById('CallTypeTable').rows.length)
			{
				//alert('1');
				var xmlobj;
				if(window.XMLHttpRequest)
				xmlobj=new XMLHttpRequest();
				else
				{
					//alert('2');
					xmlobj=new ActiveXObject("Microsoft.XMLHTTP");
				}
				
				xmlobj.onreadystatechange=function()			
				{
					//alert('3');
					if(xmlobj.readyState==4 && xmlobj.status==200)
					{
						//alert('4');
						if(xmlobj.responseText.indexOf("Session Timeout") > -1)
						{
							alert("Your Session is time out");	
							window.close();						
						}
						else
						{
							//alert(xmlobj.responseText);
							//alert('5');
							var resval=xmlobj.responseText.split('#');
							//alert('response : '+resval[3]);
							if(xmlobj.responseText.replace(/^\s+|\s+$/g,'').indexOf("0")>=0)
							{  
								//alert('6');
								//alert(xmlobj.responseText);
								var tbl=document.getElementById('CallTypeTable');	
								var resval=	xmlobj.responseText.split('#');
								
								//if(counter ==1){
									returnVal='';
								//}
								//alert(returnVal);
								//alert('7');
								vesselID = returnVal;							
								if(returnVal == null){
											//alert('8');			
									tbl.rows.item(counter).style.backgroundColor='red';						
									document.getElementById("img_"+(counter)).style.display="none";
									tbl.rows.item(counter).cells.item(2).innerHTML='N <img id="img_'+(counter)+ '" src="loading.gif" style="display:none" />';
									tbl.rows.item(counter).cells.item(4).innerHTML="NO DATA SELECTED";
								}else{
									//alert('9');
									tbl.rows.item(counter).cells.item(3).innerHTML=resval[1];
									tbl.rows.item(counter).style.backgroundColor='#2dd280';
									tbl.rows.item(counter).cells.item(2).innerHTML='Y <img id="img_'+(counter)+ '" src="loading.gif" style="display:none" />';
									//alert('green');
									if(xmlobj.responseText.replace(/^\s+|\s+$/g,'').indexOf("100901")>=0)
										tbl.rows.item(counter).cells.item(4).innerHTML=resval[1];
									else if (xmlobj.responseText.indexOf("Image Already Exists")>=0)
										tbl.rows.item(counter).cells.item(4).innerHTML=resval[1];
									else if(xmlobj.responseText.indexOf("PARTIAL SUCCESS")>=0)
										tbl.rows.item(counter).cells.item(4).innerHTML=resval[2];
									else
									tbl.rows.item(counter).cells.item(4).innerHTML='';
									document.getElementById("img_"+(counter)).style.display="none";
									
									if(counter ==1){
									//returnVal=window.open(URLWithParam,"","dialogWidth=1000px, scroll: off");
									//alert('counter 1');
									row = tbl.insertRow(2);
									cell = row.insertCell(0);
									var innerHtml='';
									var htmlContent='';
									cell.colSpan="5";
									
									
									innerHtml = '<BR><Center><table width = 1200px border=0 align="Center" id ="BasicVesselDetails"><tr><th bgColor="#ba1b18" width=40px><font color="#ffffff" face="Calibri" font-weight: "bold" size="3"><b>Select</font></th><th bgColor="#ba1b18" width=40px><font color="#ffffff" face="Calibri" font-weight: "bold" size="3"><b>S.no.</font></th><th bgColor="#ba1b18" width=180px><font color="#ffffff" face="Calibri" font-weight: "bold" size="3"><b>Vessel Name</font></th><th bgColor="#ba1b18" width=100px><font color="#ffffff" face="Calibri" font-weight: "bold" size="3"><b>Vessel IMO</font></th><th bgColor="#ba1b18" width=100px><font color="#ffffff" face="Calibri" font-weight: "bold" size="3"><b>Vessel ID</font></th><th bgColor="#ba1b18" width=100px><font color="#ffffff" face="Calibri" font-weight: "bold" size="3"><b>Vessel Type</font></th><th bgColor="#ba1b18" width=100px><font color="#ffffff" face="Calibri" font-weight: "bold" size="3"><b>Vessel Flag</font></th><th bgColor="#ba1b18" width=100px><font color="#ffffff" face="Calibri" font-weight: "bold" size="3"><b>Vessel Status</font></th></tr></Center>';
									
									if(resval[3] != '' && resval[3] != null)
									{
										var vessellength = resval[3].split("$$").length;
										var vesselDetails = resval[3].split("$$");
										for(var  i =0;i< (vessellength-1) ;i++){
											var vesselValues = vesselDetails[i].split("@@");
											var sVesselID = 'VESSEL_ID_'+i;
											var sRadioId = 'RADIO_ID_'+i;
											innerHtml = innerHtml +'<TR bgColor="#ebf3ff"><TD><input type = "radio" name="RADIO_1" id ="'+sRadioId+'" value=" '+vesselValues[3]+' "></TD><TD>'+(i+1)+'</TD><TD>'+vesselValues[0]+'</TD><TD>'+vesselValues[1]+'</TD><TD id ="'+sVesselID+'">'+vesselValues[2]+'</TD><TD>'+vesselValues[3]+'</TD><TD>'+vesselValues[4]+'</TD><TD>'+vesselValues[5]+'</TD></TR>';
										}
									}
									innerHtml = innerHtml + '</table><br/><Center><button type=\"button\" class="buttonStyle button-viewer" onclick=\"validation()\"><b>Done</b></button></Center><br>';
				
									htmlContent = htmlContent + innerHtml;
									//alert(htmlContent);
									cell.innerHTML = '<div>'+htmlContent+'</div>';
									document.getElementById("RADIO_ID_0").checked = true;
									//alert('addition done');
									counter=counter+1;
									}else{
										//alert('10');
										counter++;
										if(tbl.rows.length>counter)
										{
											if(tbl.rows.item(counter).cells.item(2).innerText=='N' || tbl.rows.item(counter).cells.item(2).innerText=='N ')
											{
												document.getElementById("img_"+(counter)).style.display="block";
												//alert('11');
												callWeb();					
											}
											else
											{
												//alert('12');
												counter++;							
												callWeb();							
											}
										}
									} 
									
								}		
							}
							else
							{
								//alert('13'+resval[1]);
								//alert('red');
								var tbl=document.getElementById('CallTypeTable');
								var resval=	xmlobj.responseText.split('#');						
								tbl.rows.item(counter).style.backgroundColor='red';						
								document.getElementById("img_"+(counter)).style.display="none";
								tbl.rows.item(counter).cells.item(2).innerHTML='N <img id="img_'+(counter)+ '" src="loading.gif" style="display:none" />';
								tbl.rows.item(counter).cells.item(4).innerHTML=resval[1];
								//counter++;
								/*								
								if(!(tbl.rows.item(counter).cells.item(4).innerText=="Mandatory"))
								{	
									counter++;
									callWeb();
								}
								else
								{
									document.getElementById('retryAll').disabled=true;
									document.getElementById('close').disabled=false;
								}
								*/
							}
						}
					}
				}
				var tbl=document.getElementById('CallTypeTable');
				
					startcounter=counter;
				//alert('14');
				for(var j=startcounter;j<tbl.rows.length ; j++)			
				{
					//alert('15');
					if((tbl.rows.item(j).cells.item(2).innerText =='N ')||(tbl.rows.item(j).cells.item(2).innerText =='N')||(((tbl.rows.item(j).cells.item(2).innerText=='Error') || (tbl.rows.item(j).cells.item(2).innerText =='Error ')) ) )				
					{
						//alert('16');
						counter=j;
						lastcounter=j;											
						break;	
					}
				}
				
				if(counter!=0 && counter<tbl.rows.length && tbl.rows.item(counter).cells.item(2).innerText!='Y ')
				{
					var tbl=document.getElementById('CallTypeTable');	
					//alert('WORKING');
					//alert('vessel name  : '+vesselIDSelected);
					//alert('counter  : '+counter);
					//alert('j  : '+j);
					//alert(tbl.rows.item(j).cells.item(0).innerText);
					document.getElementById("img_"+(counter)).style.display="block";
					//document.getElementById("btn_"+(counter)).disabled=true;
					var rnd=Math.floor(Math.random()*1000000+1);
					//alert('after');
					//alert("&VesselName="+vesselName);
					//alert("LLI_Integration_Calls_Executor.jsp?WD_UID="+wd_uid+"&rnd="+rnd+"&WI_NAME="+workitem+"&VesselName="+vesselName+"&CALL_NAME="+(tbl.rows.item(j).cells.item(0).innerText)+"&vesselID="+vesselIDSelected);
					xmlobj.open("POST","LLI_Integration_Calls_Executor.jsp?WD_UID="+wd_uid+"&rnd="+rnd+"&WI_NAME="+workitem+"&VesselName="+vesselName+"&CALL_NAME="+(tbl.rows.item(j).cells.item(0).innerText)+"&vesselID="+vesselIDSelected,true);
					xmlobj.send("sd");
					//out.println("failed");
				}
				else
				{
					document.getElementById('retryAll').disabled=true;
					//document.getElementById('close').disabled=false;
				}
				//alert('resval[2] '+resval[2]);
				
			}
			document.getElementById('close').disabled=false;
		}
		catch(err)
		{
			//alert("Error " + err.getMessage());
			//document.getElementById('close').disabled=false;
		}
	}


function resetAll()
{
	document.getElementById("retryAll").disabled = true;
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

<table cellspacing="0" cellpadding="2" width="100%" border="0" align ="center">
	  <tr>
		<td valign="top" align="right">
			<!-- <img id="main_image_init" src="/webdesktop/webtop/images/logo.jpg" border="0"> -->		</td>		
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
								<input Type="button"  class="buttonStyle button-viewer"  id="close" value="Close" onclick="closeandReturn();"/>
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
			<font color="#ffffff" face="Calibri" font-weight: "bold" size="3"><b>Call Type</b></font>
		</th>
		<th style="background-color: #ba1b18" width="15%" valign="top" >
			<font color="#ffffff" face="Calibri" size="3"><b>Request Datetime</b></font>
		</th>
		<th style="background-color: #ba1b18" width="15%" valign="top" >
			<font color="#ffffff" face="Calibri" size="3"><b>Call Status</b></font>
		</th>
		<th style="background-color: #ba1b18" width="15%" valign="top" >
			<font color="#ffffff" face="Calibri" size="3"><b>Response</b></font>
		</th>
		<th style="background-color: #ba1b18" width="15%" valign="top" >
			<font color="#ffffff" face="Calibri" size="3"><b>Error Detail</b></font>
		</th>		
		
	</tr>
<%
	try
	{
		String sWIName = request.getParameter("WI_NAME");
		String sVesselName = request.getParameter("VesselName");
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
		//String wd_uid=request.getParameter("session");
		String wd_uid=customSession.getDMSSessionId();
		String iJtsPort = String.valueOf(customSession.getJtsPort());
		sWIName= sWIName.substring(0,sWIName.length());
		%>
		<script>
		var workitem='<%= sWIName%>';
		var session='<%= sSessionId%>';
		var wd_uid='<%= wd_uid%>';
		var vesselName='<%= sVesselName%>';
		
		</script>
		<%
		if(sSessionId=="")
		{
			out.print("<script>alert('Session has expired!!');</script>");
			out.print("<script>window.close();</script>");
			
		}
		if(!sWIName.equals(""))
		{
				WriteToLog_showpage("Y","sWIName :"+sWIName);
				WriteToLog_showpage("Y","wd_uid :"+wd_uid);
				WriteToLog_showpage("Y","sVesselName :"+sVesselName);					
			   if(sVesselName.equalsIgnoreCase("updateDocumentStatusUTC")){
				   WriteToLog_showpage("Y","sWIName :"+sWIName);
				   sQuery= "select CALL_NAME, CALL_STATUS, to_char(CALL_DT,'dd/mm/yyyy hh:mi:ss') CALL_DATE, STATUS, ERROR_DESCRIPTION from TFO_DJ_INTEGRATION_CALLS WHERE WI_NAME =N'"+sWIName+"' AND CALL_NAME = 'updateDocumentStatusUTC' AND CALL_STATUS != N'F' ORDER BY SNO";
 WriteToLog_showpage("Y","sQuery :"+sQuery);				   
			   } else  {
					sQuery= "select CALL_NAME, CALL_STATUS, to_char(CALL_DT,'dd/mm/yyyy hh:mi:ss') CALL_DATE, STATUS, ERROR_DESCRIPTION from TFO_DJ_INTEGRATION_CALLS WHERE WI_NAME =N'"+sWIName+"' AND VESSEL_NAME = N'"+ sVesselName +"' AND CALL_STATUS != N'F' ORDER BY SNO";			
			   }			   
		}
				 WriteToLog_showpage("Y","outside :"+sQuery);	
		sInput="<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+sQuery+"</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
		 WriteToLog_showpage("Y","sInput :"+sInput);	
		
		if(!sInput.equals(""))
		{
			 WriteToLog_showpage("Y","inside input :");
			sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
				 WriteToLog_showpage("Y","sOutput :"+sOutput);
		}
		
		if(!sOutput.equals(""))
		{
			String sMainCode = sOutput.substring(sOutput.indexOf("<MainCode>")+10,sOutput.indexOf("</MainCode>"));
			 WriteToLog_showpage("Y","inside sMainCode :"+sMainCode);
			if(sMainCode.equalsIgnoreCase("0"))
			{	int i=1;				
				while((sOutput.indexOf("<Record>")>-1))
				{
					 WriteToLog_showpage("Y","inside while :");
					sRecord = sOutput.substring(sOutput.indexOf("<Record>")+8,sOutput.indexOf("</Record>"));
					sCall =sRecord.substring(sRecord.indexOf("<CALL_NAME>")+11,sRecord.indexOf("</CALL_NAME>"));
					sStatus =sRecord.substring(sRecord.indexOf("<CALL_STATUS>")+13,sRecord.indexOf("</CALL_STATUS>"));					
					sResponse =sRecord.substring(sRecord.indexOf("<STATUS>")+8,sRecord.indexOf("</STATUS>"));
					RequestDt=sRecord.substring(sRecord.indexOf("<CALL_DATE>")+11,sRecord.indexOf("</CALL_DATE>"));
					ErrorDesc=sRecord.substring(sRecord.indexOf("<ERROR_DESCRIPTION>")+19,sRecord.indexOf("</ERROR_DESCRIPTION>"));
					 WriteToLog_showpage("Y","inside sResponse :"+sResponse);
					  WriteToLog_showpage("Y","inside ErrorDesc :"+ErrorDesc);
					if(sResponse.equalsIgnoreCase(""))
						sResponse="&nbsp;";
					if(ErrorDesc.equalsIgnoreCase(""))
						ErrorDesc="&nbsp;";
					
					
					%>
						<TR  <% if(sResponse.equalsIgnoreCase("Success")) 
							out.println("style=\"background-color:#2dd280\""); 
							else  
							out.println("style=\"background-color:#ebf3ff\"");%> >
						<TD width="30%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt"><%= sCall%></TD>
						<TD width="15%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt"><%= RequestDt%></TD>
						<TD width="5%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt"><%= sStatus%> <img id="img_<%=i%>" src="/webdesktop/webtop/images/loading.gif" style="display:none" /></TD>
						<TD width="20%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt"><%= sResponse%></TD>
						<!--<TD width="5%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt">12345</TD> -->
						<!--<TD width="0%" style="display:none;FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt"><%= mandatory%></TD> -->
						<TD width="20%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt"><%= ErrorDesc%></TD>										
						</TR>
					<%
					//i++;
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
<%!
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
			strFilePath.append("LLI_front"+DtString+".xml");
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
	//alert('inside close');
	//if (document.getElementById('close').disabled==true)
		//return false;
	var mandatoryfail=false;
	var error=false;
	var callType = "";
	var val="";
	var tbl=document.getElementById('CallTypeTable');
	var i=0;
	for(i=1;i<tbl.rows.length;i++)
	{
		//alert(tbl.rows.item(i).cells.item(2).innerText);
		//alert(tbl.rows.item(i).cells.item(3).innerText);
					//alert(tbl.rows.item(i).cells.item(0).innerText);

		if(tbl.rows.item(i).cells.item(0).innerText == 'updateDocumentStatusUTC'){
				callType = "UTC";
			}
		if(tbl.rows.item(i).cells.item(2).innerText=='N ' || tbl.rows.item(i).cells.item(2).innerText=='N')
		{			
			
			error=true;
			//alert('sanal:'+error);
			break;
		}
		
	}

	if(error==true)
		val='Failed';
	else
		val='Success';
	
		//alert(val);
		//alert(callType);
		if(callType == "UTC"){
			//alert('UTC');
			window.parent.handleJSPResponse('UTC',val);
		} else {
			window.parent.handleJSPResponse('LLI',val);
		}
	//window.returnValue=val;
	
	//window.close();
}
</script>