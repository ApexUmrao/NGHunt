<%@page import="java.util.*,java.text.*"%>
<%@page import="com.newgen.omni.jts.cmgr.XMLParser"%>
<%@page import="com.newgen.wfdesktop.xmlapi.*,java.io.*,java.util.*,com.newgen.wfdesktop.session.*"%>
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
	var pageURL = document.URL;
	var URLWithParam = pageURL.substr(0,pageURL.indexOf('session')-1).replace("LAPSRetry.jsp",'LLI_Basic.jsp');
	var vesselID="";
	
	function callWeb()
	{
		//alert(URLWithParam);
		//alert("INSIDE CALL web"+counter);
		try
		{
			document.getElementById('retryAll').disabled=true;
			document.getElementById('close').disabled=true;
			if(counter<=document.getElementById('CallTypeTable').rows.length)
			{ 
				//alert('rows '+document.getElementById('CallTypeTable').rows.length);
				var xmlobj;
				if(window.XMLHttpRequest)
				xmlobj=new XMLHttpRequest();
				else
				{
					xmlobj=new ActiveXObject("Microsoft.XMLHTTP");
				}
				xmlobj.onreadystatechange=function()			
				{
					
					if(xmlobj.readyState==4 && xmlobj.status==200)
					{
						var currdate=new Date();
						var setDate=currdate.getDate()+"/"+(currdate.getMonth()+1)+"/"+currdate.getFullYear()+" "+currdate.getHours()+":"+currdate.getMinutes()+":"+currdate.getSeconds();
					
						if(xmlobj.responseText.indexOf("Session Timeout") > -1)
						{
							alert("Your Session is time out");	
							window.close();						
						}
						else
						{
							//alert(xmlobj.responseText);
							var resval=xmlobj.responseText.split('#');
							
							if(xmlobj.responseText.replace(/^\s+|\s+$/g,'').indexOf("FAILURE")<0)
							{
								//alert(xmlobj.responseText);
								var tbl=document.getElementById('CallTypeTable');	
								var resval=	xmlobj.responseText.split('#');
									tbl.rows.item(counter).cells.item(3).innerHTML=resval[1];
									tbl.rows.item(counter).style.backgroundColor='limegreen';
									tbl.rows.item(counter).cells.item(2).innerHTML='Y <img id="img_'+(counter)+ '" src="loading.gif" style="display:none" />';
									//alert('green');
									tbl.rows.item(counter).cells.item(4).innerHTML='';
									tbl.rows.item(counter).cells.item(1).innerHTML=setDate;
									document.getElementById("img_"+(counter)).style.display="none";

									counter++;
									if(tbl.rows.length>counter)
									{
										if(tbl.rows.item(counter).cells.item(2).innerText=='N' || tbl.rows.item(counter).cells.item(2).innerText=='N ')
										{
											document.getElementById("img_"+(counter)).style.display="block";
											callWeb();					
										}
										else
										{
											counter++;							
											callWeb();							
										}
									}
							}
							else
							{
								//alert('red');
								var tbl=document.getElementById('CallTypeTable');
								var resval=	xmlobj.responseText.split('#');	
								tbl.rows.item(counter).style.backgroundColor='red';						
								document.getElementById("img_"+(counter)).style.display="none";
								tbl.rows.item(counter).cells.item(2).innerHTML='N <img id="img_'+(counter)+ '" src="loading.gif" style="display:none" />';
								tbl.rows.item(counter).cells.item(4).innerHTML=resval[1];
								tbl.rows.item(counter).cells.item(1).innerHTML=setDate;
							}
						}
					}	
				}
				
				var tbl=document.getElementById('CallTypeTable');
				
				startcounter=counter;
				
				for(var j=startcounter;j<tbl.rows.length ; j++)			
				{
					//alert('vccc' +tbl.rows.item(j).cells.item(2).innerText );
					
					if((tbl.rows.item(j).cells.item(2).innerText =='N ')||(tbl.rows.item(j).cells.item(2).innerText =='N')||(((tbl.rows.item(j).cells.item(2).innerText=='Error') || (tbl.rows.item(j).cells.item(2).innerText =='Error ')) ) )				
					{
						
						counter=j;
						lastcounter=j;											
						break;	
					}
				}
				if(counter!=0 && counter<tbl.rows.length && tbl.rows.item(counter).cells.item(2).innerText!='Y ')
				{
					//alert('WORKING2'+counter);
					document.getElementById("img_"+(counter)).style.display="block";
					//alert('WORKING3');
					
					//document.getElementById("btn_"+(counter)).disabled=true;
					var rnd=Math.floor(Math.random()*1000000+1);
					//alert('WORKING4'+rnd);
					
					var tbl=document.getElementById('CallTypeTable');
					//alert('WORKING5'+tbl);
					
				//alert("LAPSRetryExecutor.jsp?WD_UID="+wd_uid+"&rnd="+rnd+"&WI_NAME="+workitem+"&CALL_NAME="+(tbl.rows.item(j).cells.item(0).innerText));
					xmlobj.open("POST","LAPSRetryExecutor.jsp?rnd="+rnd+"&WI_NAME="+workitem+"&CALL_NAME="+(tbl.rows.item(j).cells.item(0).innerText),true);
					xmlobj.send("sd");
					//out.println("failed");
				}
				else
				{
					document.getElementById('retryAll').disabled=true;
					document.getElementById('close').disabled=false;
				}
				//alert('resval[2] '+resval[2]);
			}
			document.getElementById('close').disabled=false;	
		}
		catch(err)
		{
			//alert("Error " + err.getMessage());
			document.getElementById('close').disabled=false;
		}
	}	

	function resetAll()
	{
		//alert('Reset all');
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
								<input Type="button" value="Start" id="retryAll" onclick="resetAll();callWeb();"/>
								<input Type="button" id="close" value="Close" onclick="closeandReturn();"/>
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
		<!--<th class="body2" width="5%" valign="top"  background="/webdesktop/webtop/images/middle.gif"><font color="#ffffff" face="Microsoft Sans Serif" size="2"><b>REFNO</b></font>
		
		</th>-->
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
		
		sWIName= sWIName.substring(0,sWIName.length());
		List<String> listCallName = new ArrayList<String>();
		List<String> listCalls = new ArrayList<String>();
		List<String> listTriedCalls = new ArrayList<String>();
		%>
		<script>
			var workitem='<%=sWIName%>';
			var session='<%=sSessionId%>';
		</script>
		<%
		if(sSessionId=="")
		{
			out.print("<script>alert('Session has expired!!');</script>");
			out.print("<script>window.close();</script>");
			
		}
		if(!sWIName.equals(""))
		{
			sQuery="Select call_name from USR_0_LAPS_CALL_OUT where WI_NAME='"+sWIName+"'  order by call_dt ASC";
			sInput = prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
			sOutput =NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
			listTriedCalls = getList(sOutput);
			WriteToLog_showpage("Y","Tried calls  "+listTriedCalls.toString());
			
			sQuery="Select call_name,call_id from CHANNEL_CALL_MASTER  where channel_name='LAPS' order by to_number(call_id)";
			sInput = prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
			sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
			//listCalls = getList(sOutput);
			//WriteToLog_showpage("Y","Master calls  "+listCalls.toString());
			
			/*for(String tempCallName : listCalls){
				if(!listTriedCalls.contains(tempCallName)){
				sInput = prepareAPInsertInputXml("USR_0_LAPS_CALL_OUT","WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT","'" + sWIName + "','1','"+ tempCallName +"','N',sysdate", sCabname, sSessionId);
				WriteToLog_showpage("Y","sInput  "+sInput);
				sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);				
				WriteToLog_showpage("Y","sOutput  "+sOutput);
				}
			}*/
			
			int totalRetrieved = Integer.parseInt(sOutput.substring(sOutput.indexOf("<TotalRetrieved>")+16,sOutput.indexOf("</TotalRetrieved>")));
			WriteToLog_showpage("Y"," ***************totalRetrieved   ------------:"+totalRetrieved);
			if(totalRetrieved>0)
			{
				XMLParser xp = new XMLParser(sOutput);
				int start = xp.getStartIndex("Records", 0, 0);
				int deadEnd = xp.getEndIndex("Records", start, 0);
				int noOfFields = xp.getNoOfFields("Record", start,deadEnd);
				int end = 0;
				int i=1;
				if(noOfFields > 0){
					for(int j = 1; j <= noOfFields; ++j){
						start = xp.getStartIndex("Record", end, 0);
						end = xp.getEndIndex("Record", start, 0);
						String callName=xp.getValueOf("call_name", start, end);
						String callId=xp.getValueOf("call_id", start, end);
						
						//if(listTriedCalls.contains(callName)){
							 sQuery= "SELECT CALL_NAME, CALL_STATUS, to_char(CALL_DT,'dd/mm/yyyy hh:mi:ss') CALL_DATE, STATUS,ERROR_DESCRIPTION from usr_0_laps_call_out WHERE WI_NAME =N'"+sWIName+"'";
							// and CALL_NAME='"+callName+"' and rownum=1";
							sInput = prepareAPSelectInputXml(sQuery,sCabname,sSessionId);		
		
		if(!sInput.equals(""))
		{
			sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
			WriteToLog_showpage("Y","###################################################################  ");
			WriteToLog_showpage("Y","Quer  "+sQuery);
			WriteToLog_showpage("Y","sInput  "+sInput);
			WriteToLog_showpage("Y","sOutput  "+sOutput);
				
		}
		
		if(!sOutput.equals(""))
		{
			String sMainCode = sOutput.substring(sOutput.indexOf("<MainCode>")+10,sOutput.indexOf("</MainCode>"));
			
			if(sMainCode.equalsIgnoreCase("0"))
			{					
				while((sOutput.indexOf("<Record>")>-1))
				{
					
					sRecord = sOutput.substring(sOutput.indexOf("<Record>")+8,sOutput.indexOf("</Record>"));
					sCall =sRecord.substring(sRecord.indexOf("<CALL_NAME>")+11,sRecord.indexOf("</CALL_NAME>"));
					sStatus =sRecord.substring(sRecord.indexOf("<CALL_STATUS>")+13,sRecord.indexOf("</CALL_STATUS>"));					
					sResponse =sRecord.substring(sRecord.indexOf("<STATUS>")+8,sRecord.indexOf("</STATUS>"));
					RequestDt=sRecord.substring(sRecord.indexOf("<CALL_DATE>")+11,sRecord.indexOf("</CALL_DATE>"));
					ErrorDesc=sRecord.substring(sRecord.indexOf("<ERROR_DESCRIPTION>")+19,sRecord.indexOf("</ERROR_DESCRIPTION>"));
					
					
					
					if(sResponse.equalsIgnoreCase(""))
						sResponse="&nbsp;";
					if(ErrorDesc.equalsIgnoreCase(""))
						ErrorDesc="&nbsp;";
					
					
					%>
						<TR  <% if(sResponse.equalsIgnoreCase("Success") || sResponse.equalsIgnoreCase("Skipped")) 
							out.println("style=\"background-color:limegreen\""); 
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
					i++;
								WriteToLog_showpage("Y","value of i is  "+i);
				
					sOutput=sOutput.substring(sOutput.indexOf("</Record>")+"</Record>".length());
					
				}
			}			
		} 
		
						//}
						else{
					sCall =callName;
					sStatus ="N";					
					sResponse ="Pending";
					RequestDt="Pending";
					ErrorDesc="Pending";
							
							
							%>
							<TR  <% if(sResponse.equalsIgnoreCase("Success")) 
							out.println("style=\"background-color:limegreen\""); 
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
						i++;	
								WriteToLog_showpage("Y","value of i is  "+i);
						}
					}
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
int liveCounterCallMvmnt = 0;
int liveCounterSightMvmnt = 0;

private String prepareAPSelectInputXml(String sQuery, String sCabname, String sSessionId){	
	return "<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+sQuery+"</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
}

private String prepareAPInsertInputXml(String tableName,String colname,String sValues, String sCabname, String sSessionId){	
	return "<?xml version=\"1.0\"?><APInsert_Input><Option>APInsert</Option><TableName>"+tableName+"</TableName><ColName>"+colname+"</ColName><Values>"+sValues+"</Values><EngineName>" + sCabname + "</EngineName>" +    "<SessionId>" + sSessionId + "</SessionId></APInsert_Input>";
}

private String prepareAPUpdateInputXml(String tableName,String colname,String sValues,String whrcls, String sCabname, String sSessionId){	
	return "<?xml version=\"1.0\"?><APUpdate_Input><Option>APUpdate</Option><TableName>"+tableName+"</TableName><ColName>"+colname+"</ColName><Values>"+sValues+"</Values><WhereClause>"+whrcls+"</WhereClause><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APUpdate_Input>";
}

private List<String> getList(String sOutput){
	List<String> listItem = new ArrayList<String>();
	XMLParser parser = new XMLParser(sOutput);
		int noOfRecords = parser.getNoOfFields("Record");
		for(int rowCounter=0;rowCounter<noOfRecords;rowCounter++){
			String nextValue = parser.getNextValueOf("Record");
			XMLParser xpInner = new XMLParser(nextValue);
			listItem.add(xpInner.getValueOf("CALL_NAME"));
		}
	return listItem;
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
							strFilePath.append("Custom_Log");
							strFilePath.append(File.separatorChar);
							strFilePath.append("AO");
							strFilePath.append(File.separatorChar);
							strFilePath.append("AORetry");
							File fBackup=new File(strFilePath.toString());
							if(fBackup == null || !fBackup.isDirectory())
							{
								fBackup.mkdirs();
							}
							strFilePath.append(File.separatorChar);
							strFilePath.append("AORetry_"+DtString+".xml");
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

</table>
</div>

</FORM>
</body>
</html>
<script>
counter=0;
//var tbl=document.getElementById('CallTypeTable');

//callWeb();
window.onbeforeunload=closeandReturn;
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
		
		if(tbl.rows.item(i).cells.item(2).innerText=='N ')
		{			
			
			error=true;
			//alert('sanal:'+error);

		}
		
	}

	if(error==true)
		val='Failed';
	else
		val='Success';
		
		
	window.returnValue=val;
	
	window.close();
}
</script>