<%@page import="javax.print.DocFlavor.STRING"%>
<%@page import="com.newgen.omni.jts.cmgr.XMLParser"%>
<%@page import="java.util.*,java.text.*"%>
<%@page import="com.newgen.wfdesktop.xmlapi.*,java.io.*,java.util.*"%>
<%@page import="com.newgen.omni.wf.util.app.*"%>
<jsp:useBean id="customSession" class="com.newgen.custom.wfdesktop.session.WFCustomSession" scope="session"/>
<html>
<head>
<title>Ownership Details</title>
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
				<Center style="color : #ba1b18;FONT-FAMILY: Calibri;font-size: 16pt;padding-left: 20px"><b>Vessel Ownership Details</b></Center>
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
		
		
		public HashMap<String,String> getRelationTypeDesc(String sOutput){
			HashMap<String, String>hmpRelationType = new HashMap<String,String>();
			String sMainCode,sRecord;
			String sRelationKey, sRelationDesc;
			if(!sOutput.equals(""))
			{
				sMainCode = sOutput.substring(sOutput.indexOf("<MainCode>")+10,sOutput.indexOf("</MainCode>"));
			if(sMainCode.equalsIgnoreCase("0"))
			{	
					while((sOutput.indexOf("<Record>")>-1))
					{
						
						sRecord = sOutput.substring(sOutput.indexOf("<Record>")+8,sOutput.indexOf("</Record>"));
						sRelationKey =sRecord.substring(sRecord.indexOf("<RELATIONCODE>")+14,sRecord.indexOf("</RELATIONCODE>"));
						sRelationDesc =sRecord.substring(sRecord.indexOf("<RELATIONTYPE>")+14,sRecord.indexOf("</RELATIONTYPE>"));
						hmpRelationType.put(sRelationKey,sRelationDesc);
						sOutput=sOutput.substring(sOutput.indexOf("</Record>")+"</Record>".length());
					}
				}
			
			}
			return hmpRelationType;
		}
		%>
		<%
		String sWiName=request.getParameter("WI_NAME");
		String sVesselName=request.getParameter("VesselName").toUpperCase();
		String sInputXML ="";
		String sOutputXML="";
		String sOutputReltnTypeXML="";
		HashMap<String, String> hmpRelationType = null;
		
		String sCabname=customSession.getEngineName();
		String sSessionId =  customSession.getDMSSessionId();
		String sUserName = customSession.getUserName();
		String sJtsIp = customSession.getJtsIp();
		String wd_uid=customSession.getDMSSessionId();
		String iJtsPort = String.valueOf(customSession.getJtsPort());
		String sQuery = "SELECT RELATIONSHIP_TYPE, COMPANY_ID, COMPANY_NAME, COUNTRY_NAME FROM  TFO_DJ_LLI_OWNERSHIP_DTLS WHERE WI_NAME ='"+sWiName+"' AND UPPER(VESSEL_NAME)='"+sVesselName+"'";
		System.out.println("Processing maker Query"+sQuery);
		sInputXML=prepareAPSelectInputXml(sQuery,sCabname, sSessionId);
		System.out.println("Processing maker XML"+sInputXML);
		if(!sInputXML.isEmpty())
			sOutputXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXML);
		System.out.println("Processing maker sOutputXML"+sOutputXML);
		
		XMLParser parser = new XMLParser(sOutputXML);
		
		
		sQuery="SELECT RELATIONTYPE, RELATIONCODE FROM TFO_DJ_LLI_RELATIONSHIP_MAST";
		sInputXML=prepareAPSelectInputXml(sQuery,sCabname, sSessionId);
		
		if(!sInputXML.isEmpty())
			sOutputReltnTypeXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXML);
		hmpRelationType = getRelationTypeDesc(sOutputReltnTypeXML);
		%>
		<BR>
<Center>			
			
				<table width = 770px border=0 align="Center">
					<tr bgColor=#ffffff>
						<th class="InternalTableHeader"  bgColor="#ba1b18" width=150px><b>Owner Type</b></th>
						<th class="InternalTableHeader"  bgColor="#ba1b18" width=150px><b>Company ID</b></th>
						<th class="InternalTableHeader"  bgColor="#ba1b18" width=150px><b>Company Name</b></th>
						<th class="InternalTableHeader"  bgColor="#ba1b18" width=150px><b>Country Name</b></th>
					</tr>
					<%int start, end, deadend;
						start = parser.getStartIndex("RECORDS", 0, 0);
						end = 0;
						deadend = parser.getEndIndex("RECORDS", start, 0); 
						int count = parser.getNoOfFields("RECORD", start, deadend);
					if (count > 0){
						for (int counter = 0; counter < count; ++counter) {
									start = parser.getStartIndex("RECORD", end, 0);
									end = parser.getEndIndex("RECORD", start, 0);%>
						<TR>	
							
									<TD class="FormValue"><%=hmpRelationType.get(parser.getValueOf("RELATIONSHIP_TYPE", start, end))%></TD>
									<TD class="FormValue"><%=parser.getValueOf("COMPANY_ID", start, end)%></TD>
									<TD class="FormValue"><%=parser.getValueOf("COMPANY_NAME", start, end)%></TD>
									<TD class="FormValue"><%=parser.getValueOf("COUNTRY_NAME", start, end)%></TD>
								
						</TR>	
						<%}
					}	%>
					</td></tr>
				</table>
			


			<br>
		
			</div>
		</div>
	</FORM>
	
	
	








</body>
</html>
