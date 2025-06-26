<%@page import="javax.print.DocFlavor.STRING"%>
<%@page import="com.newgen.omni.jts.cmgr.XMLParser"%>
<%@page import="java.util.*,java.text.*"%>
<%@page import="com.newgen.wfdesktop.xmlapi.*,java.io.*,java.util.*"%>
<%@page import="com.newgen.omni.wf.util.app.*"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.Properties"%>
<%@page import="org.apache.log4j.PropertyConfigurator"%>
<%@page import ="org.apache.log4j.Logger"%>
<jsp:useBean id="customSession" class="com.newgen.custom.wfdesktop.session.WFCustomSession" scope="session"/>

<html>
<head>
<title>Basic Vessel Details</title>
<style>
th {
	border: 1px solid Gainsboro;
}
.FormLabel {
	background-color: #D3D3D3;
}
.FormValue {
	font-size: 8pt;
	background-color: #F0EFEF;
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
	font-size: 9pt;
	FONT-FAMILY: Arial;
	
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
	BACKGROUND-COLOR:#FFFFFF; COLOR:#000000 ;
	FONT-FAMILY: Arial;FONT-WEIGHT: normal;font-size: 11pt;
	padding-left: 2px;
	vertical-align:middle;
	text-align:left;
	display:inline;
	font-weight:bold;
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
	FONT-FAMILY: Arial;FONT-WEIGHT: normal;font-size: 9pt;
	padding-left: 2px;
	vertical-align:middle;
	text-align:left;
	display:inline;
}
</style>
<Script>





function validation(){
	var sSelectedValue="";
	var sColString="";
	var ID="";
	var count=0;
	
	var cols=document.getElementById('BasicVesselDetails').getElementsByTagName('td'),colsLen=cols.length,i=-1;
	while(++i<colsLen){
		if(cols[i].innerHTML.indexOf("CHECKED")>-1){
			count+=1;
			sColString +=cols[i+4].innerHTML;
			}
		}
	if(count==0){
		alert("Please select atleast one row.");
		return false;
	}	
	else{
		window.returnValue=sColString;
		window.close();	
	}
}	
</Script>
</head>
<body onbeforeunload="ConfirmClose()" onunload="HandleOnClose()" >
	<FORM name="mainFrm" action="javascript:selectandreturn()">
		<div style="overflow: visible; align: center">
			</br>
			<div
				style="height: 100%; width: 100%; overflow: visible; position: absolute;top=30 px">
				<Center style="color : crimson;FONT-FAMILY: Arial;font-size: 16pt;padding-left: 20px"><b>Basic Vessel Details</b></Center>
				<%!
		private String prepareAPSelectInputXml(String sQuery, String sCabname, String sSessionId){	
				return "<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+sQuery+"</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
			}
			
		public void initializeLogger(){
			try{
				Properties properties = new Properties();
				String log4JPropertyFile =  System.getProperty("user.dir") +  System.getProperty("file.separator") + "NGConfig" + System.getProperty("file.separator") + "TFO" +  System.getProperty("file.separator") +  "log4jJSP.properties";
				properties.load(new FileInputStream(log4JPropertyFile));
				PropertyConfigurator.configure(properties);
			}catch(Exception e){
				e.printStackTrace();
			}
		} 
		
		Logger log = Logger.getLogger(_LLI_5F_Basic.class);
	
		private void WriteToLog_showpage(String flag,String strOutput){
			log.info(strOutput);
		}		
			
/*			
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
			//strFilePath.append(System.getProperty("user.dir"));
			strFilePath.append(File.separatorChar);
			strFilePath.append("BPMSHARE");
			strFilePath.append(File.separatorChar);
			strFilePath.append("ProductionLogs");
			strFilePath.append(File.separatorChar);
			strFilePath.append("ProcessSpecificLogs");
			strFilePath.append(File.separatorChar);
			strFilePath.append("Node1");
			strFilePath.append(File.separatorChar);
			strFilePath.append("TFO");
			strFilePath.append(File.separatorChar);
			strFilePath.append("JSPLogs");
			strFilePath.append(File.separatorChar);
			strFilePath.append("LLI_Basic"+DtString+".xml");
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
*/
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
						//WriteToLog_showpage("Y","sTagValues"+sTagValues);
						tempXML=tempXML.substring(tempXML.indexOf(sEndTag) + sEndTag.length(), tempXML.length());
					}
					if(tempXML.indexOf(sStartTag) != -1) 
					{    
						sTagValues +=",";
						//WriteToLog_showpage("Y","sTagValues"+sTagValues);
					}
				}
				if(sTagValues.indexOf("#amp#")!= -1)
				{
					WriteToLog_showpage("Y","Index found");
					sTagValues =sTagValues.replaceAll("#amp#", "&");
				}
				//WriteToLog_showpage("Y"," Final sTagValues"+sTagValues);
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
		String Const_radio = "RADIO_ID_";
		String sRadioId = "";
		%>
		<%
		initializeLogger();
		WriteToLog_showpage("Y","LLI basic");
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
		String wd_uid=request.getParameter("session");
		String iJtsPort = String.valueOf(customSession.getJtsPort());
		String sQuery = "SELECT SNO, VESSEL_NAME, VESSEL_IMO, VESSEL_ID, VESSEL_TYPE,(select Country_full_name from TFO_DJ_LLI_COUNTRY_MAST where country_code = T.VESSEL_FLAG) VESSEL_FLAG, VESSEL_STATUS FROM TFO_DJ_LLI_BASIC_VSSL_TEMP T WHERE WI_NAME ='"+sWiName+"' AND UPPER(VESSEL_NAME)='"+sVesselName+"'";
		WriteToLog_showpage("Y","Processing maker Query1"+sQuery);
		sInputXML=prepareAPSelectInputXml(sQuery,sCabname, sSessionId);
		WriteToLog_showpage("Y","Processing maker XML"+sInputXML);
		if(!sInputXML.isEmpty())
			sOutputXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXML);
		WriteToLog_showpage("Y","Processing maker sOutputXML"+sOutputXML);
		
		XMLParser parser = new XMLParser(sOutputXML);
		
		
		%>
		<BR>
<Center>			
			
				<table width = 800px border=0 align="Center" id ="BasicVesselDetails">
					<tr bgColor=#ffffff>
						<th class="InternalTableHeader"  bgColor="crimson" width=80px><b>Select</b></th>
						<th class="InternalTableHeader"  bgColor="crimson" width=40px><b>SNO</b></th>
						<th class="InternalTableHeader"  bgColor="crimson" width=180px><b>VESSEL NAME</b></th>
						<th class="InternalTableHeader"  bgColor="crimson" width=100px><b>VESSEL IMO</b></th>
						<th class="InternalTableHeader"  bgColor="crimson" width=100px><b>VESSEL ID</b></th>
						<th class="InternalTableHeader"  bgColor="crimson" width=100px><b>VESSEL TYPE</b></th>
						<th class="InternalTableHeader"  bgColor="crimson" width=100px><b>VESSEL FLAG</b></th>
						<th class="InternalTableHeader"  bgColor="crimson" width=100px><b>VESSEL STATUS</b></th>
						
					</tr>
					<%int start, end, deadend;
						start = parser.getStartIndex("RECORDS", 0, 0);
						end = 0;
						
						deadend = parser.getEndIndex("RECORDS", start, 0); 
						int count = parser.getNoOfFields("RECORD", start, deadend);
					if (count > 0){
						for (int counter = 0; counter < count; ++counter) {
									sRadioId = Const_radio + String.valueOf(counter);
									start = parser.getStartIndex("RECORD", end, 0);
									end = parser.getEndIndex("RECORD", start, 0);%>
						<TR class="APCellwhiteBold">	
									<TD class="FormValue"><input type = "radio" name="RADIO_1" id ="<%=sRadioId%>" value="<%parser.getValueOf("VESSEL_ID", start, end);%>"></TD>
									<TD class="FormValue"><%=counter+1%></TD>
									<TD class="FormValue"><%=parser.getValueOf("VESSEL_NAME", start, end)%></TD>
									<TD class="FormValue"><%=parser.getValueOf("VESSEL_IMO", start, end)%></TD>
									<TD class="FormValue"><%=parser.getValueOf("VESSEL_ID", start, end)%></TD>
									<TD class="FormValue"><%=parser.getValueOf("VESSEL_TYPE", start, end)%></TD>
									<TD class="FormValue"><%=parser.getValueOf("VESSEL_FLAG", start, end)%></TD>
									<TD class="FormValue"><%=parser.getValueOf("VESSEL_STATUS", start, end)%></TD>
										
						</TR>	
						<%
							
						}
					}	%>
					
				</table>
				<br/>
				<br/>
							<Center>
								<button type="button" style="width: 110px; border-radius:20px; color : white; background-color:Crimson;" color="Crimson" onclick="validation()"><b>Done</b></button>
								</Center>


			<br>
		
			</div>
		</div>
	</FORM>
<Script>

window.onload = onLoadHandler("Demo");
function onLoadHandler(value){
							var cols1 = document.getElementById('BasicVesselDetails').getElementsByTagName('td'),colsLen1 = cols1.length;
							if(colsLen1 <= 8){
								document.getElementById("RADIO_ID_0").checked = true;
							}
						}

</Script>	
	
	








</body>
</html>
