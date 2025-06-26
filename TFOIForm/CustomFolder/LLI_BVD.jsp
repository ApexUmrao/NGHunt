<%@page import="javax.print.DocFlavor.STRING"%>
<%@page import="com.newgen.omni.jts.cmgr.XMLParser"%>
<%@page import="java.util.*,java.text.*"%>
<%@page import="com.newgen.wfdesktop.xmlapi.*,java.io.*,java.util.*"%>
<%@page import="com.newgen.omni.wf.util.app.*"%>
<jsp:useBean id="customSession" class="com.newgen.custom.wfdesktop.session.WFCustomSession" scope="session"/>
<html>
<head>
<title>Vessel Details</title>
<style>
th {
	border: 1px solid Gainsboro;
}
.FormLabel {
	background-color: #D3D3D3;
	word-wrap:break-word;
	width:10%;
	FONT-FAMILY:Calibri;
}
.FormValue {
	background-color: #F0EFEF;
	word-wrap:break-word;
	width:25%;
	FONT-FAMILY:Calibri;
}
br{
	height:5px;
}
.divHead{
	background-color:#DBEEDE;font:bold 8pt DejaVu Sans;color:blue;vertical-align:middle;text-align:left;
}
.divColl{
	background-color:#E1E1E1;font:8pt DejaVu Sans;color:black;vertical-align:middle;text-align:left;
}
.InternalTableHeader{
	color:White;
	font-size: 11pt;
	FONT-FAMILY: Calibri;
	
}
.APMainFrameTable
{
	margin:0px;	
	border-color:blue;
	border-width: 1px;
	border-style:solid;
	padding:10px;
	background-color:#F7F7F7;
	
}
.collapsible{
	background-color:#777;
	color: white;
	cursor: pointer;
	padding: 0px;
	width: 100px;
	border: none;
	text-align: left;
	outline:none;
}
.active, .collapsible:hover{
	background-color:#555;
}
.APCellwhiteBoldHead
{
	background-color:#BA1B18; COLOR:#ffffff ;
	FONT-FAMILY: Calibri;FONT-WEIGHT: normal;font-size: 11pt;
	padding-left: 2px;
	vertical-align:middle;
	text-align:left;	
	font-weight:bold;
	width:100%;
}
.content{
	padding: 0 18px;
	display: none;
	overflow: hidden;
	background-color:#f1f1f1;
}
.APCellwhiteBold
{
	BACKGROUND-COLOR:#FFFFFF; COLOR:#000000 ;
	FONT-FAMILY: Calibri;FONT-WEIGHT: normal;font-size: 9pt;
	padding-left: 2px;
	vertical-align:middle;
	text-align:left;
	display:inline;
}
table,td{
			  border-collapse: collapse;
			  font-size:15px;
			  width="50px";
			}
.innerTable
{
	width:100%;border: 0.1px solid white;
}
table{width:95%}
</style>
</head>
<body onbeforeunload="ConfirmClose()" onunload="HandleOnClose()" >
	<FORM name="mainFrm" action="javascript:selectandreturn()">
		<div style="overflow: visible; align: center">
			</br>
			<div
				style="height: 100%; width: 100%; overflow: visible; position: absolute;top=30 px">
				<Center style="color : #ba1b18;FONT-FAMILY: Calibri;font-size: 16pt;padding-left: 20px"><b>Vessel Details - Basic Characteristics</b></Center>
				<%!
		private String prepareAPSelectInputXml(String sQuery, String sCabname, String sSessionId){	
				return "<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+sQuery+"</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
			}
		public String getTagValues(String sXML, String sTagName) 
		{  
			String sTagValues = "";
			String sStartTag = "<" + sTagName + ">";
			String sEndTag = "</" + sTagName + ">";
			String tempXML = sXML;
			tempXML=tempXML.replaceAll("&","#amp#");
			try
			{
			
				for(int i=0;i<sXML.split(sEndTag).length-1;i++) 
				{
					if(tempXML.indexOf(sStartTag) != -1) 
					{
						sTagValues += tempXML.substring(tempXML.indexOf(sStartTag) + sStartTag.length(), tempXML.indexOf(sEndTag));
						//System.out.println("sTagValues"+sTagValues);
						tempXML=tempXML.substring(tempXML.indexOf(sEndTag) + sEndTag.length(), tempXML.length());
					}
					if(tempXML.indexOf(sStartTag) != -1) 
					{    
						sTagValues +=",";
						//System.out.println("sTagValues"+sTagValues);
					}
				}
				if(sTagValues.indexOf("#amp#")!= -1)
				{
					System.out.println("Index found");
					sTagValues =sTagValues.replaceAll("#amp#", "&");
				}
				//System.out.println(" Final sTagValues"+sTagValues);
			}
			catch(Exception e) 
			{   
			}
				return sTagValues;
		}
		%>
		<%
		String sWiName=request.getParameter("WI_NAME");
		String sVesselName=request.getParameter("VesselName").toUpperCase();
		String sInputXML ="";
		String sOutputXML="";
		String sOutputMasterXML="";
		String sCabname=customSession.getEngineName();
		String sSessionId = customSession.getDMSSessionId();
		String sUserName = customSession.getUserName();
		String sJtsIp = customSession.getJtsIp();
		String wd_uid=customSession.getDMSSessionId();
		String iJtsPort = String.valueOf(customSession.getJtsPort());
		System.out.println("sCabname "+sCabname+" sUserName "+sUserName+" sJtsIp "+sJtsIp+" wd_uid "+wd_uid);
		String sQuery="Select VESSEL_ID,VESSEL_IMO, VESSEL_NAME,VESSEL_BUILT,(select Country_full_name from TFO_DJ_LLI_COUNTRY_MAST where country_code = a.VESSEL_FLAG) VESSEL_FLAG,VESSEL_TYPE,VESSEL_STATUS,GROSS_TONNAGE,DWT_TONNAGE,PORT_OF_REGISTRY ,REGISTERED_OWNER from TFO_DJ_LLI_BASIC_VSSL_DTLS a where WI_NAME = '"+sWiName+"' AND UPPER(VESSEL_NAME)='"+sVesselName+"'";;
		
		sInputXML = prepareAPSelectInputXml(sQuery,sCabname, sSessionId);
		System.out.println("App Summar Ip XML "+sInputXML);
		
		if(!sInputXML.isEmpty())
			sOutputMasterXML =NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXML);
		
				
		%>
		<BR>
<Center>			
			
				<Table width=770px border=2 id="AccountSearch_ICBS_Table" class="innerTable">
				<tr>
				<td class="FormLabel" width=125px>Vessel Name</td>
				<td class="FormValue" width=175px><%=getTagValues(sOutputMasterXML, "VESSEL_NAME")%></td>
				<td class="FormLabel" width=125px>FLAG</td>
				<td class="FormValue" width=175px><%=getTagValues(sOutputMasterXML, "VESSEL_FLAG")%></td>
				</tr>
				
				<tr>
				<td class="FormLabel" width=125px>IMO</td>
				<td class="FormValue" width=175px><%=getTagValues(sOutputMasterXML, "VESSEL_IMO")%></td>
				<td class="FormLabel" width=125px>Status</td>
				<td class="FormValue" width=175px><%=getTagValues(sOutputMasterXML, "VESSEL_STATUS")%></td>
				</tr>
				
				<tr>
				<td class="FormLabel" width=125px>Registered Owner</td>
				<td class="FormValue" width=175px><%=getTagValues(sOutputMasterXML, "REGISTERED_OWNER")%></td>
				<td class="FormLabel" width=125px>Built</td>
				<td class="FormValue" width=175px><%=getTagValues(sOutputMasterXML, "VESSEL_BUILT")%></td>
				</tr>
				
				<tr>
				<td class="FormLabel" width=125px>DWT</td>
				<td class="FormValue" width=350px><%=getTagValues(sOutputMasterXML, "DWT_TONNAGE")%></td>
				<td class="FormLabel" width=125px>GT</td>
				<td class="FormValue" width=350px><%=getTagValues(sOutputMasterXML, "GROSS_TONNAGE")%></td>
				</tr>
				
				<tr>
				<td class="FormLabel" width=125px>Vessel Type</td>
				<td class="FormValue" width=175px><%=getTagValues(sOutputMasterXML, "VESSEL_TYPE")%></td>
				<td class="FormLabel" width=125px>Vessel ID</td>
				<td class="FormValue" width=175px><%=getTagValues(sOutputMasterXML, "VESSEL_ID")%></td>
				</tr>
				
				</table>
		
			</div>
		</div>
	</FORM>
	
	
	








</body>
</html>
