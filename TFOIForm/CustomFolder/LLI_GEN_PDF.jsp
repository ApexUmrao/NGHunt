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
.button-viewer {
    min-height: 26px;
    min-width: 128px;
    /* max-width: 256px; */
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
<!--<Script src="../../webtop/scripts/client.js">
</Script>-->
<Script>
function generatePDF(){
	document.getElementById("SavePDF").disabled = true;
	var content = document.documentElement.innerHTML.toString();
	//alert(content);
	var urlDoc = document.URL;
	//alert('urlDoc'+urlDoc);
	var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('TFO')-1);
	//alert('sLocationOrigin' + sLocationOrigin);
	var folderName = document.URL;
	//alert(folderName);
	folderName  = folderName.substr(folderName.lastIndexOf('=')).substr(1);
	var param = document.URL;
	//alert(param);
	param = param.substr(param.indexOf("?")).substr(1);
	//alert("After Change "+param);
	/*content = content.replace(/\r\n/g,"");
	content = content.replace("\"","'");
	content = content.substr(0,content.indexOf('<script'))+content.substr(content.lastIndexOf('</head>'));*/
	content = '<html><HEAD></HEAD><BODY><center>MY Name is : \" final \"  ; d</center></BODY></html>';
	
	//alert("Folder name "+folderName);
	//alert("content "+content);
	//alert(window.location.pathname);
	var url1='';
	var url = sLocationOrigin +window.location.pathname;
	//alert('usrl before change ' +url);
	url = url.substr(0,url.lastIndexOf('/'))+'/pdfConversion.jsp?';
	//alert('usrl mid change ' +url);
	url1 = url.substr(0,url.lastIndexOf('/'))+"/";
	//alert('usrl after change ' +url);
	var req = initRequest();
	//alert('final url'+url);
	req.open("POST",url,true);
	//alert("request open");
	req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	//alert("request set Header");
	//alert("Encoded "+encodeURIComponent(content));
	//alert("Param "+param);
	req.send(param);	

	 req.onreadystatechange = function() {
   			 if (this.readyState == 4 && this.status == 200) {
				// alert("response First "+req.responseText);
				 if(req.responseText.indexOf('Success')>-1)
      				addToOD(param,url1); 
				else{
					alert("Fail To Generate Document");
				}
			}
 	};

       
return false;		
	
}

function addToOD(folderName,urlCall){
		
		var params2 = "WI_NAME="+folderName.substr(folderName.indexOf("WI_NAME")+8,22);
		//alert(params2);
		var sResponse = "";
		var sStatusCode="";
		var req2 = initRequest();
		var urlAddLlli = urlCall+"ADDLLIDocument.jsp?" + params2;
		//alert("url  "+urlAddLlli);
		req2.open("GET",urlAddLlli,true);
		req2.send();	
		req2.onreadystatechange = function() {
   			 if (this.readyState == 4 && this.status == 200) {
				// alert("Response "+req2.responseText);
				 sResponse = req2.responseText;
				 sStatusCode = sResponse.substr(sResponse.indexOf('<Status>')+8,1);
				// alert("Ststus "+sStatusCode);
				 sResponse = sResponse.substr(0,sResponse.indexOf('</NGOAddDocument_Output>')+24);
				 sResponse = sResponse.replace(/[\r\n]/g,'');
				 //alert("Responsefrom EDMS 6 "+sResponse);
				 if(sStatusCode == '0'){
					 //callClient(sResponse);
					 addToWI(sResponse);
				 }
				else
					alert("Workitem Failed to upload to EDMS");
			}
 		};
		
}

function addToWI(response){
	alert('PDF has been generated');
	//alert(response);
	//alert('to check');
	window.opener.handleJSPResponse('LLI_PDF',response);
	window.close();	
	//alert('to check again ');
	//window.close();
}

function initRequest() {
	//alert('isnide ajax');
	var req;
	if (window.XMLHttpRequest) {
		//alert('isnide request');
		req = new XMLHttpRequest();
	} else if (window.ActiveXObject) {
		//alert('isnide request true');
		isIE = true;
		req = new ActiveXObject("Microsoft.XMLHTTP");
	}
	return req;
}



</Script>
<body onbeforeunload="ConfirmClose()" onunload="HandleOnClose()" >
	<FORM name="mainFrm" action="javascript:selectandreturn()">
		<div style="overflow: visible; align: center">
			<br/>
			<div style="height: 100%; width: 100%; overflow: visible; position: absolute;top=30 px; FONT-FAMILY:Calibri">
				
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
					sTagValues = replaceEscapeCharacters(sTagValues);
						return sTagValues;
				}
				
		protected String formatDuration(String duration){
			
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
		
		Logger log = Logger.getLogger(_LLI_5F_GEN_5F_PDF.class);
	
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
							File fBackup=new File(strFilePath.toString());
							if(fBackup == null || !fBackup.isDirectory())
							{
								fBackup.mkdirs();
							}
							strFilePath.append(File.separatorChar);
							strFilePath.append("LLI_GEN_PDF"+DtString+".xml");
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
				
				private HashMap<String, ArrayList<String>> loadMap(String[] sColumnsName,String sOutputMasterXML){
					HashMap<String,ArrayList<String>> map = new HashMap<String,ArrayList<String>>();

					XMLParser xp = new XMLParser(sOutputMasterXML);
					int noOfRecords = xp.getNoOfFields("Record");
					
					for(int j = 0; j < noOfRecords; j++){
						String nextValue = xp.getNextValueOf("Record");
						
						XMLParser xp1 = new XMLParser(nextValue);
						ArrayList<String> list = new ArrayList<String>();
						for(int k = 0; k < sColumnsName.length; k++){
							list.add(xp1.getValueOf(sColumnsName[k]));
						}
						
						map.put(xp1.getValueOf("VESSEL_NAME").toUpperCase(),list);
						//WriteToLog_showpage("Y","["+xp1.getValueOf("VESSEL_NAME")+j+"]=>"+map.get(xp1.getValueOf("VESSEL_NAME")+j));
					}
					return map;
				}
				
				private HashMap<String, ArrayList<ArrayList<String>>> loadMap1(String[] vesselName,String[] sColumnsName,String sOutputMasterXML){
					HashMap<String, ArrayList<ArrayList<String>>> map = new HashMap<String, ArrayList<ArrayList<String>>>();
					String sTempValue = "";
					
					for(int j = 0; j < vesselName.length; j++){
						XMLParser xp = new XMLParser(sOutputMasterXML);
						int noOfRecords = xp.getNoOfFields("RECORD");
						ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
						for(int k = 0; k < noOfRecords; k++){
							String nextValue = xp.getNextValueOf("RECORD");
							XMLParser xp1 = new XMLParser(nextValue);
							ArrayList<String> list1 = new ArrayList<String>();
							if(xp1.getValueOf("VESSEL_NAME").equalsIgnoreCase(vesselName[j])){
								
								for(int x = 0; x < sColumnsName.length; x++){
									list1.add(replaceEscapeCharacters(xp1.getValueOf(sColumnsName[x])));
								}
								list.add(list1);								
							}
							
						}
						
						map.put(vesselName[j].toUpperCase(),list);
					}
					WriteToLog_showpage("Y","xxxxxxxxxxxxxxx"+map);
					return map;
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
				
				
				
			public String replaceEscapeCharacters(String sTagValues){
			
			String sRes=sTagValues;
			try{
				if(sTagValues != null && sTagValues.length()>0){
					sRes=sRes.replaceAll("<","&lt");
					sRes=sRes.replaceAll(">","&gt");
					sRes=sRes.replaceAll("'","&#039");
					sRes=sRes.replaceAll("'","&#034");
					}
				
				}catch(Exception e){
					
				}
				return sRes;
			}
				
				
				String sWiName = "";
				String wd_uid = "";
				String vesselSize = "";
				String[] vesselNames;
				HashMap<String,ArrayList<String>> bvdDetailsMap = new HashMap<String,ArrayList<String>>();
				HashMap<String, ArrayList<ArrayList<String>>> ownerShipDetailsMap = new HashMap<String, ArrayList<ArrayList<String>>>();
				HashMap<String, ArrayList<ArrayList<String>>> movmentDetailsSightingMap = new HashMap<String, ArrayList<ArrayList<String>>>();
				HashMap<String, ArrayList<ArrayList<String>>> movmentDetailsCallingMap = new HashMap<String, ArrayList<ArrayList<String>>>();
				HashMap<String, ArrayList<ArrayList<String>>> movmentDetailsETAMap = new HashMap<String, ArrayList<ArrayList<String>>>();
				int sMainCode;
				int sTotalRetrieved;
				String sFinalDestination="",sFinalETA="";
				HashMap<String, String> hmpRelationType = null;
				
				
			%>
		
			<%
				initializeLogger();
				WriteToLog_showpage("Y"," ***************LLI_GEN_PDF********************");
				
				String sCabname=customSession.getEngineName();
				String sSessionId = customSession.getDMSSessionId();
				String sUserName = customSession.getUserName();
				String sJtsIp =customSession.getJtsIp();	
				String iJtsPort =String.valueOf(customSession.getJtsPort());
				sWiName=request.getParameter("WI_NAME");
				wd_uid = request.getParameter("session");
				WriteToLog_showpage("Y","[sCabname]=>"+sCabname+"[sSessionId]=>"+sSessionId+"[sUserName]=>"+sUserName+"[sJtsIp]=>"+sJtsIp+"[iJtsPort]=>"+iJtsPort+"[sWiName]=>"+sWiName+"[wd_uid]=>"+wd_uid);
				
				String sInputXML ="";
				String sOutputXML="";
				String sOutputMasterXML="";
				
				XMLParser xml;
				
				vesselNames = (request.getParameter("VesselDetails").toUpperCase()).split(",");
				String queryIN = "";
				int sizeOfGrid = vesselNames.length;
				
				// This will make the IN part of the below query.
				for(int i = 0; i < sizeOfGrid; i++){
				
					queryIN = queryIN + "'" +vesselNames[i].toUpperCase()+ "'";
					if(i != sizeOfGrid-1)
						queryIN += ",";
				}
				
				try{
					String sbvdColumns = "VESSEL_ID,VESSEL_IMO,VESSEL_NAME,VESSEL_BUILT,VESSEL_FLAG,VESSEL_TYPE,VESSEL_STATUS,GROSS_TONNAGE,DWT_TONNAGE,REGISTERED_OWNER";
					String bvdCols = sbvdColumns.replace("VESSEL_FLAG","(select Country_full_name from TFO_DJ_LLI_COUNTRY_MAST where country_code = a.VESSEL_FLAG) VESSEL_FLAG");
					String bvdDetailsQuery="Select "+bvdCols+" from TFO_DJ_LLI_BASIC_VSSL_DTLS a where WI_NAME = '"+sWiName+"' AND UPPER(VESSEL_NAME) IN("+queryIN+")";
					WriteToLog_showpage("Y","[Query]=>"+bvdDetailsQuery);
					
					sInputXML = prepareAPSelectInputXml(bvdDetailsQuery,sCabname, sSessionId);
					WriteToLog_showpage("Y","--------------APSelect_Input----------------\n"+sInputXML+"\n");
					
					if(sInputXML != null || !"".equalsIgnoreCase(sInputXML))
						sOutputMasterXML =NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXML);
					WriteToLog_showpage("Y","--------------APSelect_Output----------------\n"+sOutputMasterXML+"\n");
					if(sOutputMasterXML != null || !"".equalsIgnoreCase(sOutputMasterXML)){
						xml = new XMLParser(sOutputMasterXML);
						sMainCode = Integer.parseInt(xml.getValueOf("MainCode"));
						sTotalRetrieved = Integer.parseInt(xml.getValueOf("TotalRetrieved"));
						if(sMainCode == 0 && sTotalRetrieved > 0){
						WriteToLog_showpage("Y","xxxxxxxxxxxxxxxx");
							String[] bvdColumn = sbvdColumns.split(",");
							bvdDetailsMap=loadMap(bvdColumn,sOutputMasterXML);
							
						}else{
							WriteToLog_showpage("Y","xxxxxxx <ERROR> bvdDetailsMap xxxxxxxxx");
						}
					}else{
						WriteToLog_showpage("Y","xxxxxxx Wrong InputXML xxxxxxxxx");
					}
					
				}catch(Exception e){
					e.printStackTrace();
				}
				try{
					String sOwnerShipColumns = "RELATIONSHIP_TYPE,COMPANY_ID,COMPANY_NAME,COUNTRY_NAME,VESSEL_NAME";
					String ownerShipQuery = "SELECT "+sOwnerShipColumns+" FROM  TFO_DJ_LLI_OWNERSHIP_DTLS WHERE WI_NAME ='"+sWiName+"' AND UPPER(VESSEL_NAME) IN("+queryIN+")";
					
					WriteToLog_showpage("Y","[Query]=>"+ownerShipQuery);
					
					sInputXML = prepareAPSelectInputXml(ownerShipQuery,sCabname, sSessionId);
					WriteToLog_showpage("Y","--------------APSelect_Input----------------\n"+sInputXML+"\n");
					
					if(sInputXML != null || !"".equalsIgnoreCase(sInputXML))
						sOutputMasterXML =NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXML);
					WriteToLog_showpage("Y","--------------APSelect_Output----------------\n"+sOutputMasterXML+"\n");
					if(sOutputMasterXML != null || !"".equalsIgnoreCase(sOutputMasterXML)){
						xml = new XMLParser(sOutputMasterXML);
						sMainCode = Integer.parseInt(xml.getValueOf("MainCode"));
						sTotalRetrieved = Integer.parseInt(xml.getValueOf("TotalRetrieved"));
						if(sMainCode == 0 && sTotalRetrieved > 0){
							String[] OwnerShipColumns = sOwnerShipColumns.split(",");
							ownerShipDetailsMap=loadMap1(vesselNames,OwnerShipColumns,sOutputMasterXML);
							
							 String sRelTypeQuery="SELECT RELATIONTYPE, RELATIONCODE FROM TFO_DJ_LLI_RELATIONSHIP_MAST";
							 
							sInputXML=prepareAPSelectInputXml(sRelTypeQuery,sCabname, sSessionId);
							WriteToLog_showpage("Y","--------------APSelect_Input----------------\n"+sInputXML+"\n");
							if(!sInputXML.isEmpty())
								sOutputMasterXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXML);
							WriteToLog_showpage("Y","--------------APSelect_Output----------------\n"+sOutputMasterXML+"\n");
							hmpRelationType = getRelationTypeDesc(sOutputMasterXML);
							
						}else{
							WriteToLog_showpage("Y","xxxxxxx <ERROR> ownerShipDetailsMap hmpRelationType xxxxxxxxx");
						}
					}else{
						WriteToLog_showpage("Y","xxxxxxx Wrong InputXML xxxxxxxxx");
					}
					
				}catch(Exception e){
					e.printStackTrace();
				}
				
				try{
					String sMovmentDetailsSightingColumns = "VESSEL_TYPE,DISTANCE,NEAREST_PLACE,NEAREST_COUNTRY,FROM_GMT,TO_GMT,DURATION,DESTINATION,ETA,VESSEL_NAME";
					String sMovmentDetailsSightingQuery = "SELECT VESSEL_TYPE, DISTANCE, NEAREST_PLACE, NEAREST_COUNTRY,DECODE(FROM_GMT,'null','',FROM_GMT) FROM_GMT, DECODE(TO_GMT,'null','',TO_GMT) TO_GMT,ROUND((TO_DATE(DECODE(TO_GMT,'null','',TO_GMT),'dd/MM/yyyy HH24:MI:SS') - TO_DATE(DECODE(FROM_GMT,'null','',FROM_GMT),'dd/MM/yyyy HH24:MI:SS')),3) DURATION,DESTINATION,ETA,VESSEL_NAME FROM  TFO_DJ_LLI_MVMNT_DTLS_SIGHTING WHERE WI_NAME ='"+sWiName+"' AND UPPER(VESSEL_NAME) IN("+queryIN+") order by TO_DATE(DECODE(FROM_GMT,'null','',FROM_GMT),'dd/MM/yyyy HH24:MI:SS') DESC";
					WriteToLog_showpage("Y","[Query]=>"+sMovmentDetailsSightingQuery);
					sInputXML=prepareAPSelectInputXml(sMovmentDetailsSightingQuery,sCabname, sSessionId);
					WriteToLog_showpage("Y","--------------APSelect_Input----------------\n"+sInputXML+"\n");
					if(!sInputXML.isEmpty())
						sOutputMasterXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXML);
					WriteToLog_showpage("Y","--------------APSelect_Output----------------\n"+sOutputMasterXML+"\n");
					if(sOutputMasterXML != null || !"".equalsIgnoreCase(sOutputMasterXML)){
						xml = new XMLParser(sOutputMasterXML);
						sMainCode = Integer.parseInt(xml.getValueOf("MainCode"));
						sTotalRetrieved = Integer.parseInt(xml.getValueOf("TotalRetrieved"));
						if(sMainCode == 0 && sTotalRetrieved > 0){
							String[] movmentDetailsSightingColumns = sMovmentDetailsSightingColumns.split(",");
							movmentDetailsSightingMap=loadMap1(vesselNames,movmentDetailsSightingColumns,sOutputMasterXML);
						}else{
						movmentDetailsSightingMap.clear();
						
							WriteToLog_showpage("Y","xxxxxxx <ERROR> movmentDetailsSightingMap xxxxxxxxx");
						}
					}else{
						WriteToLog_showpage("Y","xxxxxxx Wrong InputXML xxxxxxxxx");
					}
					
					
					String sMovmentDetailsCallingColumns = "PLACE,COUNTRY,ARRIVAL_DATE,SAILING_DATE,DURATION,MOVEMENT_TTYPE,VESSEL_NAME";
					String sMovmentDetailsCallingQuery = "SELECT PLACE, COUNTRY,DECODE(ARRIVAL_DATE,'null','',ARRIVAL_DATE) ARRIVAL_DATE,DECODE(SAILING_DATE,'null','',SAILING_DATE) SAILING_DATE, ROUND((TO_DATE(DECODE(SAILING_DATE,'null','',SAILING_DATE),'dd/MM/yyyy HH24:MI:SS') - TO_DATE(DECODE(ARRIVAL_DATE,'null','',ARRIVAL_DATE),'dd/MM/yyyy HH24:MI:SS')),3) DURATION, MOVEMENT_TTYPE, VESSEL_NAME  FROM  TFO_DJ_LLI_MVMNT_DTLS_CALLING WHERE WI_NAME ='"+sWiName+"' AND UPPER(VESSEL_NAME) IN("+queryIN+")  ORDER BY TO_DATE(DECODE(ARRIVAL_DATE,'null','',ARRIVAL_DATE),'dd/MM/yyyy HH24:MI:SS') DESC";
					WriteToLog_showpage("Y","[Query]=>"+sMovmentDetailsCallingQuery);
					sInputXML=prepareAPSelectInputXml(sMovmentDetailsCallingQuery,sCabname, sSessionId);
					WriteToLog_showpage("Y","--------------APSelect_Input----------------\n"+sInputXML+"\n");
					if(!sInputXML.isEmpty())
						sOutputMasterXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXML);
					WriteToLog_showpage("Y","--------------APSelect_Output----------------\n"+sOutputMasterXML+"\n");
					if(sOutputMasterXML != null || !"".equalsIgnoreCase(sOutputMasterXML)){
						xml = new XMLParser(sOutputMasterXML);
						sMainCode = Integer.parseInt(xml.getValueOf("MainCode"));
						sTotalRetrieved = Integer.parseInt(xml.getValueOf("TotalRetrieved"));
						if(sMainCode == 0 && sTotalRetrieved > 0){
							String[] movmentDetailsCallingColumns = sMovmentDetailsCallingColumns.split(",");
							movmentDetailsCallingMap=loadMap1(vesselNames,movmentDetailsCallingColumns,sOutputMasterXML);
						}else{
						movmentDetailsCallingMap.clear();
						
							WriteToLog_showpage("Y","xxxxxxx <ERROR> movmentDetailsCallingMap xxxxxxxxx");
						}
					}else{
						WriteToLog_showpage("Y","xxxxxxx Wrong InputXML xxxxxxxxx");
					}
					
					
					
					String sMovmentDetailsETAColumns = "PLACE,COUNTRY,ETA,VESSEL_NAME";
					String sMovmentDetailsETAQuery = "SELECT PLACE,COUNTRY,ETA,VESSEL_NAME FROM TFO_DJ_LLI_MVMNT_ETA_DTLS WHERE WI_NAME ='"+sWiName+"' AND UPPER(VESSEL_NAME) IN("+queryIN+") and  TRUNC(TO_DATE(ETA,'dd/MM/yyyy hh24:mi:ss')) > sysdate-1 ORDER By TO_DATE(ETA,'dd/MM/yyyy hh24:mi:ss') DESC";
					WriteToLog_showpage("Y","[Query]=>"+sMovmentDetailsETAQuery);
					sInputXML=prepareAPSelectInputXml(sMovmentDetailsETAQuery,sCabname, sSessionId);
					WriteToLog_showpage("Y","--------------APSelect_Input----------------\n"+sInputXML+"\n");
					if(!sInputXML.isEmpty())
						sOutputMasterXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXML);
					WriteToLog_showpage("Y","--------------APSelect_Output----------------\n"+sOutputMasterXML+"\n");
					if(sOutputMasterXML != null || !"".equalsIgnoreCase(sOutputMasterXML)){
						xml = new XMLParser(sOutputMasterXML);
						sMainCode = Integer.parseInt(xml.getValueOf("MainCode"));
						sTotalRetrieved = Integer.parseInt(xml.getValueOf("TotalRetrieved"));
						if(sMainCode == 0 && sTotalRetrieved > 0){
							String[] movmentDetailsETAColumns = sMovmentDetailsETAColumns.split(",");
							movmentDetailsETAMap=loadMap1(vesselNames,movmentDetailsETAColumns,sOutputMasterXML);
						}else{
						movmentDetailsETAMap.clear();
						
							WriteToLog_showpage("Y","xxxxxxx <ERROR> movmentDetailsCallingMap xxxxxxxxx");
						}
					}else{
						WriteToLog_showpage("Y","xxxxxxx Wrong InputXML xxxxxxxxx");
					}
					
					
					
					
				}catch(Exception e){
					e.printStackTrace();
				}
				
				WriteToLog_showpage("Y","####################################"+bvdDetailsMap);
				WriteToLog_showpage("Y","####################################"+ownerShipDetailsMap);
				WriteToLog_showpage("Y","####################################"+movmentDetailsSightingMap);
				WriteToLog_showpage("Y","####################################"+movmentDetailsCallingMap);
				WriteToLog_showpage("Y","########movmentDetailsETAMap############################"+movmentDetailsETAMap);
					
				
				
			%>
		
			<%
				for(int i = 0; i < sizeOfGrid; i++){
					sFinalDestination = "";
					sFinalETA= "";
					WriteToLog_showpage("Y","########################"+vesselNames[i]+"######################");
					WriteToLog_showpage("Y","########################"+bvdDetailsMap.containsKey(vesselNames[i])+"######################");
			%>
					<% if(!bvdDetailsMap.isEmpty() && bvdDetailsMap.containsKey(vesselNames[i])) {
							WriteToLog_showpage("Y","########################"+vesselNames[i]+" Not Empty ######################");
							WriteToLog_showpage("Y","########################"+vesselNames[i]+" has bvd details ######################");
					%>
					
					
					<Center style="color : black;FONT-FAMILY: Calibri;font-size: 18pt;padding-left: 20px"><b><u>Vessel Name : <%=vesselNames[i]%></u></b></Center>
					<br/>
					
					<Center style="color : black;FONT-FAMILY: Calibri;font-size: 16pt;padding-left: 20px"><b>Vessel Details - Basic Characteristics</b></Center>
					<br/>
		
		
					<div name = "Vessel Details">
						<center>
					
							<table width=770px border=2 id="AccountSearch_ICBS_Table">
								<tr >
									<td class="FormLabel" width=125px>Vessel Name</td>
									<td class="FormValue" width=175px><%=bvdDetailsMap.get(vesselNames[i]).get(2)%></td>
									<td class="FormLabel" width=125px>FLAG</td>
									<td class="FormValue" width=175px><%=bvdDetailsMap.get(vesselNames[i]).get(4)%></td>
								</tr>
								
								<tr >
									<td class="FormLabel" width=125px>IMO</td>
									<td class="FormValue" width=175px><%=bvdDetailsMap.get(vesselNames[i]).get(1)%></td>
									<td class="FormLabel" width=125px>Status</td>
									<td class="FormValue" width=175px><%=bvdDetailsMap.get(vesselNames[i]).get(6)%></td>
								</tr>
								
								<tr >
									<td class="FormLabel" width=125px>Registered Owner</td>
									<td class="FormValue" width=175px><%=bvdDetailsMap.get(vesselNames[i]).get(9)%></td>
									<td class="FormLabel" width=125px>Built</td>
									<td class="FormValue" width=175px><%=bvdDetailsMap.get(vesselNames[i]).get(3)%></td>
								</tr>
								
								<tr >
									<td class="FormLabel" width=125px>DWT</td>
									<td class="FormValue" width=175px><%=bvdDetailsMap.get(vesselNames[i]).get(8)%></td>
									<td class="FormLabel" width=125px>GT</td>
									<td class="FormValue" width=175px><%=bvdDetailsMap.get(vesselNames[i]).get(7)%></td>
								</tr>
								
								<tr >
									<td class="FormLabel" width=125px>Vessel Type</td>
									<td class="FormValue" width=175px><%=bvdDetailsMap.get(vesselNames[i]).get(5)%></td>
									<td class="FormLabel" width=125px>Vessel ID</td>
									<td class="FormValue" width=175px><%=bvdDetailsMap.get(vesselNames[i]).get(0)%></td>
								</tr>
							
							</table>
						</center>
					</div>
					<br/>
					<br/>
					<% if(!ownerShipDetailsMap.isEmpty() && ownerShipDetailsMap.containsKey(vesselNames[i]) && !ownerShipDetailsMap.get(vesselNames[i]).get(0).isEmpty()){
							WriteToLog_showpage("Y","########################"+vesselNames[i]+" has ownership details ######################");
					%>
					<Center style="color : black;FONT-FAMILY: Calibri;font-size: 16pt;padding-left: 20px"><b>Vessel Ownership Detail</b></Center>
					<br/>
					<div>
						<Center>			
				
							<table width = 770px border=0 align="Center">
								<tr bgColor="#808080">
									<th class="InternalTableHeader"  bgColor="#808080" width=150px><b>Owner Type</b></th>
									<th class="InternalTableHeader"  bgColor="#808080" width=150px><b>Company ID</b></th>
									<th class="InternalTableHeader"  bgColor="#808080" width=150px><b>Company Name</b></th>
									<th class="InternalTableHeader"  bgColor="#808080" width=150px><b>Country Name</b></th>
								</tr>
								<%	
									for (int counter = 0; counter < ownerShipDetailsMap.get(vesselNames[i]).size(); ++counter) {
										if(ownerShipDetailsMap.containsKey(vesselNames[i])){
								%>
										<tr >	
											
											<TD class="FormValue"><%=hmpRelationType.get(ownerShipDetailsMap.get(vesselNames[i]).get(counter).get(0))%></TD>
											<TD class="FormValue"><%=ownerShipDetailsMap.get(vesselNames[i]).get(counter).get(1)%></TD>
											<TD class="FormValue"><%=ownerShipDetailsMap.get(vesselNames[i]).get(counter).get(2)%></TD>
											<TD class="FormValue"><%=ownerShipDetailsMap.get(vesselNames[i]).get(counter).get(3)%></TD>
												
										</tr>	
								<% 		}
									}
								%>
							</table>
						</center>
					</div>
					<br/>
					<br/>
					<% 
					}
					%>
					
					<% if((!movmentDetailsSightingMap.isEmpty() && movmentDetailsSightingMap.containsKey(vesselNames[i])) || (!movmentDetailsCallingMap.isEmpty() && movmentDetailsCallingMap.containsKey(vesselNames[i]))
						||(!movmentDetailsETAMap.isEmpty() && movmentDetailsETAMap.containsKey(vesselNames[i])))
					{	
						WriteToLog_showpage("Y","########################"+vesselNames[i]+" has movement details ######################");
					%>
					<Center style="color : black;FONT-FAMILY: Calibri;font-size: 16pt;padding-left: 20px"><b>Vessel Movement Details</b></Center>
					<br/>
					<div>
						

						<% if((!movmentDetailsCallingMap.isEmpty() && movmentDetailsCallingMap.containsKey(vesselNames[i]))
							||(!movmentDetailsETAMap.isEmpty() && movmentDetailsETAMap.containsKey(vesselNames[i])))
						{
							WriteToLog_showpage("Y","############inside movementDetails ######################");

							int serialNumber = 0;
						%>
						<Center>
						<DIV border=1 style="color : black; font-size: 12pt; FONT-FAMILY:Calibri"><B>Calling Details</B></DIV>
						<table width = 900px border=0 align="Center">
								<tr bgColor=#808080>
									<th class="InternalTableHeader"  bgColor="#808080" width=30px><b>Sr#</b></th>
									<th class="InternalTableHeader"  bgColor="#808080" width=90px><b>Status</b></th>
									<th class="InternalTableHeader"  bgColor="#808080" width=200px><b>Port</b></th>
									<th class="InternalTableHeader"  bgColor="#808080" width=200px><b>Country</b></th>
									<th class="InternalTableHeader"  bgColor="#808080" width=75px><b>Type</b></th>
									<th class="InternalTableHeader"  bgColor="#808080" width=100px><b>From</b></th>
									<th class="InternalTableHeader"  bgColor="#808080" width=100px><b>To</b></th>
									<th class="InternalTableHeader"  bgColor="#808080" width=80px><b>Duration</b></th>
									<th class="InternalTableHeader"  bgColor="#808080" width=100px><b>Destination</b></th>
									<th class="InternalTableHeader"  bgColor="#808080" width=80px><b>ETA</b></th>
									<th class="InternalTableHeader"  bgColor="#808080" width=100px><b>Details</b></th>

								</tr>
								<%
								try{	
								int count1 = movmentDetailsETAMap.get(vesselNames[i]).size();
								if (count1 > 0){
									for (int counter = 0; counter < count1; ++counter) {
										serialNumber +=1;
								%>
									<TR >	
										
										<TD class="FormValue"><%=serialNumber%></TD>
										<TD class="FormValue"><%="ETA"%></TD>
										<TD class="FormValue"><%=movmentDetailsETAMap.get(vesselNames[i]).get(counter).get(0).equalsIgnoreCase("null")?"":movmentDetailsETAMap.get(vesselNames[i]).get(counter).get(0)%></TD>
										<TD class="FormValue"><%=movmentDetailsETAMap.get(vesselNames[i]).get(counter).get(1).equalsIgnoreCase("null")?"":movmentDetailsETAMap.get(vesselNames[i]).get(counter).get(1)%></TD>
										<TD class="FormValue"><%=""%></TD>
										<TD class="FormValue"><%=movmentDetailsETAMap.get(vesselNames[i]).get(counter).get(2)%></TD>
										<TD class="FormValue"><%=""%></TD>
										<TD class="FormValue"><%=""%></TD>
										<TD class="FormValue"><%=sFinalDestination%></TD>
										<TD class="FormValue"><%=sFinalETA%></TD>
										<TD class="FormValue"><%=""%></TD>
											
									</TR>	
									<%																		
									sFinalDestination = movmentDetailsETAMap.get(vesselNames[i]).get(counter).get(0);
									sFinalETA = movmentDetailsETAMap.get(vesselNames[i]).get(counter).get(2);	
									}
								}
								}catch(Exception e){
									
								}
								try{
								int count2 = movmentDetailsCallingMap.get(vesselNames[i]).size();
								if (count2 > 0){
									for (int counter = 0; counter < count2; ++counter) {
										serialNumber +=1;
								%>
									<TR >	
										
										<TD class="FormValue"><%=serialNumber%></TD>
										<TD class="FormValue"><%=movmentDetailsCallingMap.get(vesselNames[i]).get(counter).get(5).toUpperCase().contains("PASSED")?"Passing":"Called at"%></TD>
										<TD class="FormValue"><%=movmentDetailsCallingMap.get(vesselNames[i]).get(counter).get(0).equalsIgnoreCase("null")?"":movmentDetailsCallingMap.get(vesselNames[i]).get(counter).get(0)%></TD>
										<TD class="FormValue"><%=movmentDetailsCallingMap.get(vesselNames[i]).get(counter).get(1).equalsIgnoreCase("null")?"":movmentDetailsCallingMap.get(vesselNames[i]).get(counter).get(1)%></TD>
										<TD class="FormValue"><%=""%></TD>
										<TD class="FormValue"><%=movmentDetailsCallingMap.get(vesselNames[i]).get(counter).get(2)%></TD>
										<TD class="FormValue"><%=movmentDetailsCallingMap.get(vesselNames[i]).get(counter).get(3)%></TD>
										<TD class="FormValue"><%=formatDuration(movmentDetailsCallingMap.get(vesselNames[i]).get(counter).get(4))%></TD>
										<TD class="FormValue"><%=sFinalDestination%></TD>
										<TD class="FormValue"><%=sFinalETA%></TD>
										<TD class="FormValue"><%=movmentDetailsCallingMap.get(vesselNames[i]).get(counter).get(5).equalsIgnoreCase("null")?"":movmentDetailsCallingMap.get(vesselNames[i]).get(counter).get(5)%></TD>
											
									</TR>	
									<%
									sFinalDestination = movmentDetailsCallingMap.get(vesselNames[i]).get(counter).get(0);
									sFinalETA = movmentDetailsCallingMap.get(vesselNames[i]).get(counter).get(2);									
									}
								}
								}catch(Exception e){
									
								}								
								%>
								
						</table>
						
						<%
						}//end of movmentDetailsCalling
						%>
						<br/>
						<% if(!movmentDetailsSightingMap.isEmpty() && movmentDetailsSightingMap.containsKey(vesselNames[i]))
						{
						%>
							
						<DIV style="color : black;font-size: 12pt; FONT-FAMILY:Calibri"><B>Sighting Details</B></DIV>
						<table width = 900px border=0 align="Center">
							<tr bgColor=#808080>
								<th class="InternalTableHeader"  bgColor="#808080" width=30px><b>Sr#</b></th>
								<th class="InternalTableHeader"  bgColor="#808080" width=90px><b>Status</b></th>
								<th class="InternalTableHeader"  bgColor="#808080" width=70px><b>Distance(nm)</b></th>
								<th class="InternalTableHeader"  bgColor="#808080" width=163px><b>Port</b></th>
								<th class="InternalTableHeader"  bgColor="#808080" width=162px><b>Country</b></th>
								<th class="InternalTableHeader"  bgColor="#808080" width=75px><b>Type</b></th>
								<th class="InternalTableHeader"  bgColor="#808080" width=100px><b>From</b></th>
								<th class="InternalTableHeader"  bgColor="#808080" width=100px><b>To</b></th>
								<th class="InternalTableHeader"  bgColor="#808080" width=80px><b>Duration</b></th>
								<th class="InternalTableHeader"  bgColor="#808080" width=100px><b>Destination</b></th>
								<th class="InternalTableHeader"  bgColor="#808080" width=100px><b>ETA</b></th>
							</tr>
							<%
								int count = movmentDetailsSightingMap.get(vesselNames[i]).size();
								if (count > 0){
									for (int counter = 0; counter < count; ++counter) {
							%>
										<TR >	
									
											<TD class="FormValue"><%=(counter+1)%></TD>
											<TD class="FormValue"><%=movmentDetailsSightingMap.get(vesselNames[i]).get(counter).get(0)%></TD>
											<TD class="FormValue"><%=movmentDetailsSightingMap.get(vesselNames[i]).get(counter).get(1)%></TD>
											<TD class="FormValue"><%=movmentDetailsSightingMap.get(vesselNames[i]).get(counter).get(2).equalsIgnoreCase("null")?"":movmentDetailsSightingMap.get(vesselNames[i]).get(counter).get(2)%></TD>
											<TD class="FormValue"><%=movmentDetailsSightingMap.get(vesselNames[i]).get(counter).get(3).equalsIgnoreCase("null")?"":movmentDetailsSightingMap.get(vesselNames[i]).get(counter).get(3)%></TD>
											<TD class="FormValue"><%=""%></TD>
											<TD class="FormValue"><%=movmentDetailsSightingMap.get(vesselNames[i]).get(counter).get(4)%></TD>
											<TD class="FormValue"><%=movmentDetailsSightingMap.get(vesselNames[i]).get(counter).get(5)%></TD>
											<TD class="FormValue"><%=formatDuration(movmentDetailsSightingMap.get(vesselNames[i]).get(counter).get(6))%></TD>
											<TD class="FormValue"><%=movmentDetailsSightingMap.get(vesselNames[i]).get(counter).get(7)%></TD>
											<TD class="FormValue"><%=movmentDetailsSightingMap.get(vesselNames[i]).get(counter).get(8)%></TD>
										
										</TR>	
							<%	
									}
								}
							%>
							
						</table>
						<%
						}//end of movmentDetailsSighting
						%>
						</center>
					</div>
					<%
					}
					%>
					
					<%}//End of outer if
					else{
						WriteToLog_showpage("Y","########################"+vesselNames[i]+" Is Empty ######################");
					%>
						<Center style="color : black;FONT-FAMILY: Calibri;font-size: 18pt;padding-left: 20px"><b><u>Vessel Name : <%=vesselNames[i]%> - No data found.<u></b></Center>
					<br/>
					<%
					}// end of else if
					%>
			
					<br/>	
				
				<%	
				}//end of for
				
				%>
				<Center><button type="button" class="buttonStyle button-viewer"   id="SavePDF" onclick='generatePDF();'><b>Save PDF</b></button></Center>
								<br/><br/>
				
			</div>
		</div>
	</FORM>
</body>
</html>
