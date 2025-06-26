<jsp:useBean id="customSession" class="com.newgen.custom.wfdesktop.session.WFCustomSession" scope="session"/>
<%@page import="java.util.*,java.text.*"%>
<%@page import="com.newgen.wfdesktop.xmlapi.*,java.io.*,java.util.*"%>
<%@ page import="com.newgen.omni.wf.util.app.*"%>

<html>
<head>
<title>Offered Product List</title>
<style>
table,th,td
{
border:1px solid Gainsboro;
}
</style>
<script>   
 function handleKeyPress(evt) {   
  var nbr, chr;   
  if (window.Event) nbr = evt.which;   
    else nbr = event.keyCode;   
  if(nbr==13){   
    selectandreturn();   
  }   
  return true;   
 }   
 document.onkeydown= handleKeyPress   
</script>  
</head>
<body>
<FORM name="mainFrm" action="javascript:selectandreturn()">
<div style="overflow:auto; align:center" >
<%
		String sQuery="";
		String sInput="";
		String sOutput="";
		String sRecord = "";
		String prod_code ="";
		String prod_desc ="";
		String cur="";
		String sChequebook="";
		String sCabname= customSession.getEngineName();
		String sSessionId = customSession.getDMSSessionId();
		String sUserName =customSession.getUserName();
		String sJtsIp = customSession.getJtsIp();
		String iJtsPort =  String.valueOf(customSession.getJtsPort());
		String crncy="";
		String returnStr="";
		String aryCurr[]=null;
		sQuery= "SELECT CURRENCY FROM USR_0_CURRENCY ORDER BY CURRENCY";
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
					crncy =sRecord.substring(sRecord.indexOf("<CURRENCY>")+10,sRecord.indexOf("</CURRENCY>"));
					
					if(i==1)
					{
						returnStr=crncy;
					}
					else
					{
						returnStr=returnStr+"#"+crncy;
					}
					
					i++;
					sOutput=sOutput.substring(sOutput.indexOf("</Record>")+"</Record>".length());
					
				}
				
			}	
			aryCurr=returnStr.split("#");			
		}

%>
<table id="CallTypeTable" border=0 width="100%" cellspacing="1"  bgColor=#ffffff>
	<tr >		
		<td background="/webdesktop/webtop/images/middle.gif" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt;color:white"><b>Product Code</b></td>
		<td background="/webdesktop/webtop/images/middle.gif"><input type="text" size="20" id="prodCode" value=""/></td>
		<td background="/webdesktop/webtop/images/middle.gif" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt;color:white"><b>Product Description</b></td>
		<td background="/webdesktop/webtop/images/middle.gif"><input type="text" size="20" id="prodDesc" value=""/></td>
		<td background="/webdesktop/webtop/images/middle.gif" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt;color:white"><b>Currency</b></td>
		<td background="/webdesktop/webtop/images/middle.gif">			
			<select id="currency"><option value="">Select</option>
			<%
				for(int arryVlaue=0;arryVlaue<aryCurr.length;arryVlaue++)
				{
			%>
				<option value="<%=aryCurr[arryVlaue]%>"><%=aryCurr[arryVlaue]%></option>
			<%
				}
			%>									
			</select>
		</td>
		<td></td>
		<td><input type='button' class='EWButton' name='Search' value='Search' onclick='ajexSearch();'/></td>
	</tr>
</table>
</br>&nbsp;&nbsp;<input type="button" value="Done" onclick="selectandreturn();"/>
</br>
<div style="height:440 px;width:100%;overflow:auto;position:absolute;top=80 px">
<table id="ProductTable" align="center" >
	<tr>
		<th background="/webdesktop/webtop/images/middle.gif" width="5%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt;color:white"><b>Select</b></th>
		<th background="/webdesktop/webtop/images/middle.gif" width="15%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt;color:white"><b>Product Code</b></th>
		<th background="/webdesktop/webtop/images/middle.gif" width="40%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt;color:white"><b>Product Description</b></th>
		<th background="/webdesktop/webtop/images/middle.gif" width="10%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt;color:white"><b>Currency</b></th>
		<th background="/webdesktop/webtop/images/middle.gif" width="10%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt;color:white"><b>Cheque Book</b></th>
		<th background="/webdesktop/webtop/images/middle.gif" width="20%" style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt;color:white"><b>Account Branch</b></th>
	</tr>
<%
	try
	{
		String sWIName = request.getParameter("WI_NAME");		
		String sAccClass = request.getParameter("ACC_CLASS");	
		String sTableName= request.getParameter("TABLE");
				
		%>
		<script>
		var workitem='<%= sWIName%>';
		var session='<%= sSessionId%>';
		
		var accClass='<%= sAccClass%>';
		var tablename ='<%= sTableName%>';
		function ajexSearch()
		{
		
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
				
					var responseProdCode=xmlobj.responseText.split('#');
					if(responseProdCode.length>=3)
					{
						var elmtTable = document.getElementById('ProductTable');
						var tableRows = elmtTable.getElementsByTagName('tr');
						var rowCount = tableRows.length;
						
						for (var x=0; x<rowCount-1; x++) 
						{
						   document.getElementById("ProductTable").deleteRow(1);
						}
					
						var counter=0;
						
						for(var i=0; i<responseProdCode.length/5; i++)
						{
						
							var row=document.getElementById("ProductTable").insertRow(i+1);						
							var firstRow=document.getElementById("ProductTable").rows[i+1];						
							var cell1=firstRow.insertCell(-1);	
							var element1 = document.createElement("input");
							element1.type = "checkbox";
							var count=i+1;
							element1.id =  "chk_"+count ;			
							cell1.appendChild(element1);
						
							var cell2=firstRow.insertCell(1);
							cell2.innerHTML='<font face="Microsoft Sans Serif" size="2pt">'+ responseProdCode[counter]+'</font>';					
							counter++;
							
							var cell3=firstRow.insertCell(2);
							cell3.innerHTML='<font face="Microsoft Sans Serif" size="2pt">'+responseProdCode[counter]+'</font>';
							counter++;
							var cell4=firstRow.insertCell(3);
							cell4.innerHTML='<font face="Microsoft Sans Serif" size="2pt">'+responseProdCode[counter]+'</font>';
							counter++;
							var cell5=firstRow.insertCell(4);
							cell5.innerHTML='<font face="Microsoft Sans Serif" size="2pt">'+responseProdCode[counter]+'</font>';
							counter++;
							var cell6=firstRow.insertCell(5);
							cell6.innerHTML='<font face="Microsoft Sans Serif" size="2pt">'+responseProdCode[counter]+'</font>';
							counter++;
						}
					}
					else
					{
						alert("No Data Found.");
					}
				}				
			}
			var rnd=Math.floor(Math.random()*1000000+1);
			var prodCode=document.getElementById('prodCode').value;
			
			var prodDesc=document.getElementById('prodDesc').value;
			var a = document.getElementById("currency");
			var Currency=a.options[a.selectedIndex].value;			
			var url = window.location.href;
			
			
			var params='&productCode='+prodCode+'&productDesc='+prodDesc+'&currency='+Currency+'&ACC_CLASS='+accClass+'&TABLE='+tablename;
			//alert("productListAjex.jsp?WD_UID="+wd_uid+"&rnd="+rnd+"&WI_NAME="+ workitem +params);
			xmlobj.open("POST","productListAjex.jsp?rnd="+rnd+"&WI_NAME="+ workitem +params,true);
			xmlobj.send("sd");
		}
		</script>
		<%
		if(sSessionId=="")
		{
			out.print("<script>alert('Session has expired!!');</script>");
			out.print("<script>window.close();</script>");
			
		}
		WriteToLog_showpage("Y","sWIName: "+sWIName);
		if(!sWIName.equals(""))
		{
			sQuery= "SELECT PRODUCT_CODE,PRODUCT_DESC,CURRENCY_SHORT_NAME,(SELECT DECODE(CHEQUE_BOOK_FAC,'Y','Yes','No') FROM USR_0_PRODUCT_MASTER A WHERE PRODUCT_CODE = B.PRODUCT_CODE) CHEQUE_BOOK,(SELECT CODE FROM USR_0_HOME_BRANCH WHERE CODE=(CASE WHEN (SELECT COD_PROD_TYPE FROM USR_0_PRODUCT_MASTER A WHERE A. PRODUCT_CODE= B.PRODUCT_CODE )='C' THEN ((SELECT TO_CHAR(CODE) FROM USR_0_HOME_BRANCH WHERE HOME_BRANCH='"+sAccClass+"')) ELSE (SELECT DECODE(TO_CHAR(EQV_ISLAMIC_BR_CODE),NULL,TO_CHAR(CODE),TO_CHAR(EQV_ISLAMIC_BR_CODE)) FROM USR_0_HOME_BRANCH WHERE CODE=(SELECT CODE FROM USR_0_HOME_BRANCH WHERE HOME_BRANCH='"+sAccClass+"')) END )) CODE FROM "+sTableName+" B WHERE UPPER(WI_NAME) = UPPER('"+sWIName+"') ORDER BY TO_NUMBER(PRODUCT_CODE)";
			
			sQuery="SELECT b.PRODUCT_CODE,b.PRODUCT_DESC,b.CURRENCY_SHORT_NAME,DECODE(a.CHEQUE_BOOK_FAC,'Y','Yes','No') CHEQUE_BOOK,(case when a.COD_PROD_TYPE='C' then TO_CHAR(c.CODE) else DECODE(TO_CHAR(c.EQV_ISLAMIC_BR_CODE),NULL,TO_CHAR(c.CODE),TO_CHAR(c.EQV_ISLAMIC_BR_CODE)) end) CODE from "+sTableName+" B,USR_0_PRODUCT_MASTER A,USR_0_HOME_BRANCH c where a.product_code= b.product_code and UPPER(b.WI_NAME) = UPPER('"+sWIName+"') and a.currency_code= b.currency_code and  c.HOME_BRANCH='"+sAccClass+"'";
			
			//out.println("<script>alert(\""+sQuery+"\")</script>");
		}
				
		sInput="<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+sQuery+"</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
		WriteToLog_showpage("Y","sInput: "+sInput);
		if(!sInput.equals(""))
		{
		sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);

		}
		WriteToLog_showpage("Y","sOutput: "+sOutput);
		if(!sOutput.equals(""))
		{
			String sMainCode = sOutput.substring(sOutput.indexOf("<MainCode>")+10,sOutput.indexOf("</MainCode>"));
			WriteToLog_showpage("Y",sMainCode);
			if(sMainCode.equalsIgnoreCase("0"))
			{	int i=1;				
				while((sOutput.indexOf("<Record>")>-1))
				{
					sRecord = sOutput.substring(sOutput.indexOf("<Record>")+8,sOutput.indexOf("</Record>"));
					prod_code =sRecord.substring(sRecord.indexOf("<PRODUCT_CODE>")+14,sRecord.indexOf("</PRODUCT_CODE>"));
					prod_desc =sRecord.substring(sRecord.indexOf("<PRODUCT_DESC>")+14,sRecord.indexOf("</PRODUCT_DESC>"));
					cur =sRecord.substring(sRecord.indexOf("<CURRENCY_SHORT_NAME>")+21,sRecord.indexOf("</CURRENCY_SHORT_NAME>"));
					sChequebook =sRecord.substring(sRecord.indexOf("<CHEQUE_BOOK>")+13,sRecord.indexOf("</CHEQUE_BOOK>"));
					sAccClass=sRecord.substring(sRecord.indexOf("<CODE>")+6,sRecord.indexOf("</CODE>"));
					%>
						<TR>
						<TD style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt"><input type="checkbox" id="chk_<%=i%>" ></TD>
						<TD style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt"><%= prod_code%></TD>
						<TD style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt"><%= prod_desc%></TD>						
						<TD style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt"><%= cur%></TD>
						<TD style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt"><%= sChequebook%></TD>
						<TD style="FONT-FAMILY: Microsoft Sans Serif;FONT-WEIGHT: normal;font-size: 10pt"><%= sAccClass%></TD>
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
		WriteToLog_showpage("Y",e.toString());
		out.print("<script>alert('Exception occ');</script>");
	}
	
	
%>
</table>
</div>
</div>
</FORM>
</body>
</html>
<script>
function selectandreturn()
{
	var tbl=document.getElementById("ProductTable");
	var returnVal="";
	//alert(tbl.rows.length);
	for(var i=1;i<=tbl.rows.length-1;i++)
	{
		if(document.getElementById("chk_"+i).checked==true)
		{
			returnVal=returnVal+tbl.rows.item(i).cells.item(1).innerText+'~'+tbl.rows.item(i).cells.item(2).innerText+'~'+tbl.rows.item(i).cells.item(3).innerText+'~'+tbl.rows.item(i).cells.item(4).innerText+'~'+tbl.rows.item(i).cells.item(5).innerText+'#';
			
		}
	}
	returnVal=returnVal.substring(0,returnVal.length-1);
	//alert(returnVal);
	//window.returnValue=returnVal;
	window.parent.handleJSPResponse('productList',returnVal);	
	//window.close();
}
</script>
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
							strFilePath.append(File.separatorChar);
							strFilePath.append("BPMSHARE");
							strFilePath.append(File.separatorChar);
							strFilePath.append("ProductionLogs");
							strFilePath.append(File.separatorChar);
							strFilePath.append("ProcessLogs");
							strFilePath.append(File.separatorChar);
							strFilePath.append("AO");
							strFilePath.append(File.separatorChar);
							strFilePath.append("JSPLogs");
							strFilePath.append(File.separatorChar);
							strFilePath.append("product_list");
							File fBackup=new File(strFilePath.toString());
							if(fBackup == null || !fBackup.isDirectory())
							{
								fBackup.mkdirs();
							}
							strFilePath.append(File.separatorChar);
							strFilePath.append("AO_"+DtString+".xml");
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