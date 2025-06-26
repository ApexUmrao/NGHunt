<%@page import="javax.print.DocFlavor.STRING"%>
<%@page import="com.newgen.omni.jts.cmgr.XMLParser"%>
<%@page import="java.util.*,java.text.*"%>
<%@page import="com.newgen.wfdesktop.xmlapi.*,java.io.*,java.util.*"%>
<%@page import="com.newgen.omni.wf.util.app.*"%>
<jsp:useBean id="customSession" class="com.newgen.custom.wfdesktop.session.WFCustomSession" scope="session"/>
<html>
<head>
<title>Movement Details</title>
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
	FONT-FAMILY:Calibri;

	
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
				<Center style="color : #ba1b18;FONT-FAMILY: Calibri;font-size: 16pt;padding-left: 20px"><b>Vessel Movement Details</b></Center>
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
		
		public ArrayList<ArrayList<String>> getTableData(String sOutputXML, String ...sParameters){
			ArrayList<ArrayList<String>> arrL = new ArrayList<ArrayList<String>>();
			ArrayList<String> ArrIntenal = null;
			System.out.println("OutputXML  "+sOutputXML);
			String sRecord="";
			String sMainCode;
			if(!sOutputXML.equals(""))
			{
				sMainCode = sOutputXML.substring(sOutputXML.indexOf("<MainCode>")+10,sOutputXML.indexOf("</MainCode>"));
				System.out.println("sMainCode  "+sMainCode);
				if(sMainCode.equalsIgnoreCase("0"))
				{		
						while((sOutputXML.indexOf("<Record>")>-1))
						{	
							ArrIntenal = new ArrayList<String>();
							sRecord = sOutputXML.substring(sOutputXML.indexOf("<Record>")+8,sOutputXML.indexOf("</Record>"));
							System.out.println("sRecord  "+sRecord);
							for(String sParameter:sParameters){
								String sTempString = sRecord.substring(sRecord.indexOf("<"+sParameter+">")+sParameter.length()+2,sRecord.indexOf("</"+sParameter+">"));
								System.out.println("sTempString  "+sTempString);
								sTempString=sTempString.replaceAll("\n","<br>");
								System.out.println("sTempString  "+sTempString);
								ArrIntenal.add(sTempString);
							}
							System.out.println("internalList  "+ArrIntenal);
							arrL.add(ArrIntenal);
							System.out.println("MasterList  "+arrL);
							sOutputXML=sOutputXML.substring(sOutputXML.indexOf("</Record>")+"</Record>".length());
						}
					}
				
				}
			return arrL;
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
		
		
		public String formatDuration(String duration){
			
			String sRes ="";
			Double d = 0.0;
			try{
				d = Double.parseDouble(duration);
			}catch(Exception e){
				d =(double) -1;
			}
			
			if(d>=0){
				if(d >=  1)
					sRes = (int)Math.floor(d) +" Days";
				else if((d*24) >= 1 )
					sRes = (int)Math.floor(d*24)+" Hours";
				else if((d*24*60) >=1)
					sRes = (int)Math.floor(d*24*60) + " Minutes";
				else 
					sRes = "a few seconds";
			}
			return sRes;
		}
		
		
		%>
		<%
		String sWiName=request.getParameter("WI_NAME");
		String sVesselName=request.getParameter("VesselName").toUpperCase();
		String sInputXML ="";
		String sOutputXMLSighting="";
		String sOutputXMLCalling="";
		String sOutputXMLETA="";
		String sOutputReltnTypeXML="";
		String sFinalDestination="",sFinalETA="";
		HashMap<String, String> hmpRelationType = null;
		ArrayList<ArrayList<String>> ETAList = null;
		ArrayList<ArrayList<String>> CallingList = null;
		
		String sCabname=customSession.getEngineName();
		String sSessionId = customSession.getDMSSessionId();
		String sUserName = customSession.getUserName();
		String sJtsIp = customSession.getJtsIp();
		String wd_uid=customSession.getDMSSessionId();
		String iJtsPort =String.valueOf(customSession.getJtsPort());
		String sQuery = "SELECT VESSEL_TYPE, DISTANCE, NEAREST_PLACE, NEAREST_COUNTRY, DECODE(FROM_GMT,'null','',FROM_GMT) FROM_GMT, DECODE(TO_GMT,'null','',TO_GMT) TO_GMT,ROUND((TO_DATE(DECODE(TO_GMT,'null','',TO_GMT),'dd/MM/yyyy HH24:MI:SS') - TO_DATE(DECODE(FROM_GMT,'null','',FROM_GMT),'dd/MM/yyyy HH24:MI:SS')),3) DURATION, DESTINATION, ETA FROM  TFO_DJ_LLI_MVMNT_DTLS_SIGHTING WHERE WI_NAME ='"+sWiName+"' AND UPPER(VESSEL_NAME)='"+sVesselName+"' order by TO_DATE(DECODE(FROM_GMT,'null','',FROM_GMT),'dd/MM/yyyy HH24:MI:SS') DESC";
		System.out.println("Processing maker Query"+sQuery);
		sInputXML=prepareAPSelectInputXml(sQuery,sCabname, sSessionId);
		System.out.println("Processing maker XML "+sInputXML);
		if(!sInputXML.isEmpty())
			sOutputXMLSighting = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXML);
		XMLParser parser = new XMLParser(sOutputXMLSighting);
		
		sQuery="SELECT PLACE, COUNTRY, DECODE(ARRIVAL_DATE,'null','',ARRIVAL_DATE) ARRIVAL_DATE, DECODE(SAILING_DATE,'null','',SAILING_DATE) SAILING_DATE, ROUND((TO_DATE(DECODE(SAILING_DATE,'null','',SAILING_DATE),'dd/MM/yyyy HH24:MI:SS') - TO_DATE(DECODE(ARRIVAL_DATE,'null','',ARRIVAL_DATE),'dd/MM/yyyy HH24:MI:SS')),3) DURATION, MOVEMENT_TTYPE  FROM  TFO_DJ_LLI_MVMNT_DTLS_CALLING WHERE WI_NAME ='"+sWiName+"' AND UPPER(VESSEL_NAME)='"+sVesselName+"' ORDER BY TO_DATE(DECODE(ARRIVAL_DATE,'null','',ARRIVAL_DATE),'dd/MM/yyyy HH24:MI:SS') DESC";
		System.out.println("Processing maker Query"+sQuery);
		sInputXML=prepareAPSelectInputXml(sQuery,sCabname, sSessionId);
		if(!sInputXML.isEmpty())
			sOutputXMLCalling = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXML);
		XMLParser parser1 = new XMLParser(sOutputXMLCalling);
		
		CallingList = getTableData(sOutputXMLCalling,"PLACE","COUNTRY","ARRIVAL_DATE","SAILING_DATE","DURATION","MOVEMENT_TTYPE");
		
		
		
		sQuery = "SELECT PLACE,COUNTRY, ETA FROM TFO_DJ_LLI_MVMNT_ETA_DTLS WHERE WI_NAME = '"+sWiName+"' AND UPPER(VESSEL_NAME)='"+sVesselName+"' and  TRUNC(TO_DATE(ETA,'dd/MM/yyyy hh24:mi:ss')) > sysdate-1		ORDER BY TO_DATE(ETA,'dd/MM/yyyy hh24:mi:ss') DESC";
		sInputXML=prepareAPSelectInputXml(sQuery,sCabname, sSessionId);
		if(!sInputXML.isEmpty())
			sOutputXMLETA = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXML);
		ETAList = getTableData(sOutputXMLETA,"PLACE","COUNTRY","ETA");
		System.out.println("ETAList "+ETAList);
		%>
		<BR>
<Center>	
			    <DIV style="color : #ba1b18; font-size: 12pt; FONT-FAMILY:Calibri"><B>Calling Details</DIV>
			<table width = 900px border=0 align="Center">
					<tr bgColor=#ba1b18>
						<th class="InternalTableHeader"  bgColor="#ba1b18" width=30px><b>Sr#</b></th>
						<th class="InternalTableHeader"  bgColor="#ba1b18" width=70px><b>Status</b></th>
						<th class="InternalTableHeader"  bgColor="#ba1b18" width=162px><b>Port</b></th>
						<th class="InternalTableHeader"  bgColor="#ba1b18" width=163px><b>Country</b></th>
						<th class="InternalTableHeader"  bgColor="#ba1b18" width=60px><b>Type</b></th>
						<th class="InternalTableHeader"  bgColor="#ba1b18" width=50px><b>From</b></th>
						<th class="InternalTableHeader"  bgColor="#ba1b18" width=100px><b>To</b></th>
						<th class="InternalTableHeader"  bgColor="#ba1b18" width=80px><b>Duration</b></th>
						<th class="InternalTableHeader"  bgColor="#ba1b18" width=60px><b>Destination</b></th>
						<th class="InternalTableHeader"  bgColor="#ba1b18" width=60px><b>ETA</b></th>
						<th class="InternalTableHeader"  bgColor="#ba1b18" width=100px><b>Details</b></th>
					</tr>
					<%
						int serialNumber = 0;
						try{
						for (int cntr = 0; cntr < ETAList.size(); cntr++) {
							serialNumber +=1;
							%>
						<TR>	
							
									<TD class="FormValue"><%=serialNumber%></TD>
									<TD class="FormValue"><%="ETA"%></TD>
									<TD class="FormValue"><%=ETAList.get(cntr).get(0).equalsIgnoreCase("null")?"":ETAList.get(cntr).get(0)%></TD>
									<TD class="FormValue"><%=ETAList.get(cntr).get(1).equalsIgnoreCase("null")?"":ETAList.get(cntr).get(1)%></TD>
									<TD class="FormValue"><%=""%></TD>
									<TD class="FormValue"><%=ETAList.get(cntr).get(2)%></TD>
									<TD class="FormValue"><%=""%></TD>
									<TD class="FormValue"><%=""%></TD>
									<TD class="FormValue"><%=sFinalDestination%></TD>
									<TD class="FormValue"><%=sFinalETA%></TD>
									<TD class="FormValue"><%=""%></TD>								
						</TR>	
						<%
							sFinalDestination = ETAList.get(cntr).get(0);
							sFinalETA = ETAList.get(cntr).get(2);
							}
						}catch(Exception e){
							
						}
						%>
					
					<%
						try{
							for (int cntr = 0; cntr < CallingList.size(); cntr++) {	
							serialNumber +=1;
							%>
						<TR>	
							
									<TD class="FormValue"><%=serialNumber%></TD>
									<TD class="FormValue"><%=CallingList.get(cntr).get(5).toUpperCase().contains("PASSED")?"Passing":"Called at"%></TD>
									<TD class="FormValue"><%=CallingList.get(cntr).get(0).equalsIgnoreCase("null")?"":CallingList.get(cntr).get(0)%></TD>
									<TD class="FormValue"><%=CallingList.get(cntr).get(1).equalsIgnoreCase("null")?"":CallingList.get(cntr).get(1)%></TD>
									<TD class="FormValue"><%=""%></TD>
									<TD class="FormValue"><%=CallingList.get(cntr).get(2)%></TD>
									<TD class="FormValue"><%=CallingList.get(cntr).get(3)%></TD>
									<TD class="FormValue"><%=formatDuration(CallingList.get(cntr).get(4))%></TD>
									<TD class="FormValue"><%=sFinalDestination%></TD>
									<TD class="FormValue"><%=sFinalETA%></TD>
									<TD class="FormValue"><%=CallingList.get(cntr).get(5).equalsIgnoreCase("null")?"":CallingList.get(cntr).get(5)%></TD>								
						</TR>	
						<% sFinalDestination = CallingList.get(cntr).get(0);
						   sFinalETA = CallingList.get(cntr).get(2);
							
							}
						}catch(Exception e){
							
						}
						%>
					
					

					</td></tr>
				</table>
				<BR/>
			<DIV style="color : #ba1b18;font-size: 12pt; FONT-FAMILY:Calibri"><B>Sighting Details</DIV>
				<table width = 900px border=0 align="Center">
					<tr bgColor=#ba1b18>
						<th class="InternalTableHeader"  bgColor="#ba1b18" width=30px><b>Sr#</b></th>
						<th class="InternalTableHeader"  bgColor="#ba1b18" width=90px><b>Status</b></th>
						<th class="InternalTableHeader"  bgColor="#ba1b18" width=70px><b>Distance(nm)</b></th>
						<th class="InternalTableHeader"  bgColor="#ba1b18" width=150px><b>Port</b></th>
						<th class="InternalTableHeader"  bgColor="#ba1b18" width=150px><b>Country</b></th>
						<th class="InternalTableHeader"  bgColor="#ba1b18" width=50px><b>Type</b></th>
						<th class="InternalTableHeader"  bgColor="#ba1b18" width=100px><b>From</b></th>
						<th class="InternalTableHeader"  bgColor="#ba1b18" width=100px><b>To</b></th>
						<th class="InternalTableHeader"  bgColor="#ba1b18" width=50px><b>Duration</b></th>
						<th class="InternalTableHeader"  bgColor="#ba1b18" width=70px><b>Destination</b></th>
						<th class="InternalTableHeader"  bgColor="#ba1b18" width=60px><b>ETA</b></th>
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
						<TR >	
							
									<TD class="FormValue"><%=(counter+1)%></TD>
									<TD class="FormValue"><%=parser.getValueOf("VESSEL_TYPE", start, end)%></TD>
									<TD class="FormValue"><%=parser.getValueOf("DISTANCE", start, end)%></TD>
									<TD class="FormValue"><%=parser.getValueOf("NEAREST_PLACE", start, end).equalsIgnoreCase("null")?"":parser.getValueOf("NEAREST_PLACE", start, end)%></TD>
									<TD class="FormValue"><%=parser.getValueOf("NEAREST_COUNTRY", start, end).equalsIgnoreCase("null")?"":parser.getValueOf("NEAREST_COUNTRY", start, end)%></TD>
									<TD class="FormValue"><%=""%></TD>
									<TD class="FormValue"><%=parser.getValueOf("FROM_GMT", start, end)%></TD>
									<TD class="FormValue"><%=parser.getValueOf("TO_GMT", start, end)%></TD>
									<TD class="FormValue"><%=formatDuration(parser.getValueOf("DURATION", start, end))%></TD>
									<TD class="FormValue"><%=parser.getValueOf("DESTINATION", start, end)%></TD>
									<TD class="FormValue"><%=parser.getValueOf("ETA", start, end)%></TD>
								
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
