<jsp:useBean id="customSession" class="com.newgen.custom.wfdesktop.session.WFCustomSession" scope="session"/>

<%@ page import="ISPack.ISUtil.*,ISPack.*,java.io.*,Jdts.DataObject.JPDBInteger, Jdts.DataObject.JPDBISAddress,Jdts.DataObject.*,Jdts.*, java.net.*,com.newgen.wfmonitor.util.*, com.newgen.wfmonitor.session.*,com.newgen.wfmonitor.exception.*,com.newgen.wfmonitor.xmlapi.*, com.newgen.wfmonitor.dmsinterface.*,java.awt.*"%>
<%@ page import="com.newgen.wfdesktop.xmlapi.*" %>
<%@ page import="com.newgen.wfdesktop.util.*" %>
<%@ page import="com.newgen.wfmonitor.xmlapi.*"%>
<%@ page import="com.newgen.omni.wf.util.app.*"%>
<%@ page import =" javax.xml.parsers.DocumentBuilder " %>
<%@ page import  =" javax.xml.parsers.DocumentBuilderFactory" %>
<%@ page import  =" org.w3c.dom.Document" %>
<%@ page import  =" org.w3c.dom.Element" %>
<%@ page import  =" org.w3c.dom.Node" %>
<%@ page import  =" org.w3c.dom.NodeList" %>
<%@ page import  =" org.xml.sax.InputSource" %>
<%@page  import="java.util.*"%>
<%@page  import="java.text.SimpleDateFormat"%>
<%@page  import="java.util.List"%>
<%@page  import="java.util.ArrayList"%>
<%@ page import="org.xml.sax.SAXException"%>
<%@ page import="javax.xml.parsers.ParserConfigurationException"%>
<%@ page import="java.io.IOException" %>
<%@ page import ="java.io.StringReader" %>
<%@ page import ="Jdts.DataObject.JPDBString"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.*"%>
<%@ page import ="org.apache.log4j.Logger"%>
<%@ page import ="org.apache.log4j.PropertyConfigurator"%>
<%@page import="java.awt.Image"%>
<%@page import="com.newgen.niplj.NIPLJ"%>
<%@page import="com.newgen.niplj.codec.DecodeParam"%>
<%@page import="com.newgen.niplj.codec.EncodeParam"%>
<%@page import="com.newgen.niplj.codec.jpeg.JpegEncodeParam"%>
<%@page import="com.newgen.niplj.fileformat.Tif6"%>
<%@page import="com.newgen.niplj.generic.NG_BufferedImageOperations"%>
<%@page import="com.newgen.niplj.io.RandomInputStreamSource"%>
<%@page import="com.newgen.omni.wf.util.excp.NGException"%>
<%@page import="com.newgen.niplj.io.RandomInputStream"%>


<%@page import="com.newgen.niplj.io.RandomInputStreamSource"%>
<%@page import="com.newgen.niplj.operations.NG_SimpleImageProducer"%>
<%@page import="com.newgen.niplj.codec.gif.GifEncodeParam"%>
<%@page import="org.apache.commons.codec.binary.Base64"%>
<%@page import="java.awt.Toolkit"%>
<%@page import="ISPack.CPISDocumentTxn"%>
<%@page import="ISPack.ISUtil.JPISException"%>
<%@page import="com.newgen.omni.jts.cmgr.XMLParser"%>
<%@ page import="com.newgen.wfdesktop.util.*" %>
<%@ page import=="com.newgen.iforms.user.tfo.util.ConnectSocket"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.Properties"%>
<%@page import="org.apache.log4j.PropertyConfigurator"%>
<%@page import ="org.apache.log4j.Logger"%>
<%
initializeLogger();
//socket = ConnectSocket.getReference("10.101.109.182", 4444);

String sQuery="";
String sInput="";
String sOutput="";
String sRecord = "";
String sCall ="";
String sStatus ="";
String returnXML="";
String sReason ="";
String sErrorDescription="";

String sWIName = request.getParameter("WI_NAME");
String sCallName = request.getParameter("CALL_NAME");
String sVesselName = request.getParameter("VesselName");
String sVesselID = request.getParameter("vesselID");
String sCabname=customSession.getEngineName();
String sSessionId = customSession.getDMSSessionId();
String sUserName = customSession.getUserName();
String sJtsIp = customSession.getJtsIp();
String callXML="";
String sResVal="";
String ErrorDesc="";
String SLNO="";
String errCase;
String oldREFNO="";
String REFNO = "";
String iJtsPort = String.valueOf(customSession.getJtsPort());

String vesselId;
String vesselimo;
String vesselName;
String vesselBuilt;
String vesselflag;
String vesselcallsign;
String maritimeMobileID;
String portOfRegistry;
String grossTonnage;
String netTonnage;
String dwtTonnage;
String vesselGenericType;
String subtype;
String vesselType;
String vesselStatus;
String lastUpdated;
String relationshipType;
String companyId;
String companyName;
String startDate;
String startQualifier;
String endDate;
String endQualifier;
String ownershipLastUpdated;
XMLParser xp = null;
String fromMD;
String toMD;
String mmsi;
String sighttype;
String fromGmt;
String toGmt;
String latitude;
String longitude;
String SpeedoverGround;
String CourseoverGround;
String nearestPlace;
String nearestCountry;
String distance;
String destination;
String eta;
String Draft;
String numberOfUpdates;
String moveId;
String moveSequence;
String place;
String country;
String arrivalDate;
String arrivalEstimated;
String arrivalQualifier;
String sailingDate;
String sailingEstimated;
String sailingQualifier;
String movementType;
String callingLastUpdated;
String callingId;  //ATP-250 DATE 09-01-2024 REYAZ
int returnCode = 1;
int totalRecords = 0;
String etaPlaceName;
String etaPlaceCountry;
String etaEstimatedTimeArrival;
String etaTerminal;
String etaVoyage;
String etaEstimatedTimeDeparture;
String etaNextPort;
String etaSource;
String officeId;
String headOffice;
String countryName;
String veselDetailsFromIntegration="";
try
{
	String sQueryBPM = "SELECT (SELECT VALUE FROM BPM_SERVICE_CONFIG WHERE KEY = 'SOCKETIP') IP,(SELECT VALUE FROM BPM_SERVICE_CONFIG WHERE KEY = 'SOCKETPORT') PORT FROM DUAL";
	String sInputBPM = prepareAPSelectInputXml(sQueryBPM,sCabname,sSessionId);
	WriteToLog_showpage("Y"," ***************Ref number Input "+sInputBPM+" *****************************");
	String sOutputBPM = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputBPM);
	WriteToLog_showpage("Y"," ***************Ref number output = "+sOutputBPM+"  *****************************");
	try
	{
			WriteToLog_showpage("Y","started");
			String ipBPM = sOutputBPM.substring(sOutputBPM.indexOf("<IP>")+4,sOutputBPM.indexOf("</IP>"));
			WriteToLog_showpage("Y","started  1");
			int portBPM = Integer.parseInt(sOutputBPM.substring(sOutputBPM.indexOf("<PORT>")+6,sOutputBPM.indexOf("</PORT>")));
			WriteToLog_showpage("Y","ipBPM : " +ipBPM);
			WriteToLog_showpage("Y"," portBPM : " +portBPM);
			socket = ConnectSocket.getReference(ipBPM, portBPM);
	}
	catch(Exception e)
	{
	}

	WriteToLog_showpage("Y"," ***************start *****************************");
	sQuery="SELECT COUNT(1) AS COUNT_WI FROM PDBCONNECTION WHERE RANDOMNUMBER = '"+sSessionId+"'";
	sInput=prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
	WriteToLog_showpage("Y"," ***************Checking connection "+sInput+" *****************************");
	sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);	
	String sCount=sOutput.substring(sOutput.indexOf("<COUNT_WI>")+10,sOutput.indexOf("</COUNT_WI>"));
	if(sCount.equalsIgnoreCase("0"))
	{
		out.println("Session Timeout");
	}
	else
	{	
		WriteToLog_showpage("Y"," ***************start *****************************sCount else"+sCount);
		WriteToLog_showpage("Y"," sQuery inside sCallName"+sCallName);
		WriteToLog_showpage("Y"," sQuery inside sCallName"+sVesselName);
		if(sCallName.equalsIgnoreCase("updateDocumentStatusUTC")){
			WriteToLog_showpage("Y"," sQuery inside");
			sQuery = "SELECT REF_NUM, CALL_XML FROM TFO_DJ_INTEGRATION_CALLS_DTLS WHERE WI_NAME = '"+ sWIName +"'  and call_name = '"+ sCallName +"'";
			WriteToLog_showpage("Y"," sQuery"+sQuery);
		} else {
			sQuery = "SELECT REF_NUM, CALL_XML FROM TFO_DJ_INTEGRATION_CALLS_DTLS WHERE WI_NAME = '"+ sWIName +"' AND VESSEL_NAME = '"+ sVesselName +"' and call_name = '"+ sCallName +"'";
		}
		WriteToLog_showpage("Y"," sQuery"+sQuery);
		sInput = prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
		WriteToLog_showpage("Y"," ***************Ref number Input "+sInput+" *****************************");
		sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
		WriteToLog_showpage("Y"," ***************Ref number output = "+sOutput+"  *****************************");
		try
		{
			oldREFNO = sOutput.substring(sOutput.indexOf("<REF_NUM>")+9,sOutput.indexOf("</REF_NUM>"));
		}
		catch(Exception e)
		{
			oldREFNO = "";
		}
		
		if(!sWIName.equals(""))
		{
			if(oldREFNO.equals("")){
				sInput = prepareAPSelectInputXml("select seq_webservice.nextval REFNO from dual",sCabname,sSessionId);
				sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
				REFNO = sOutput.substring(sOutput.indexOf("<REFNO>")+7,sOutput.indexOf("</REFNO>"));
				
				if(sCallName.equalsIgnoreCase("BasicVesselDetails")){
					callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>LLIVesselOps</InnerCallType><refNo>"+ REFNO +"</refNo><senderID>WMSDGTRD</senderID><winame>"+ sWIName +"</winame><serviceName>InqCustomerTradeJourney</serviceName><vesselName>"+ sVesselName +"</vesselName><operationType>fetchVesselDetails</operationType><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";
					
					sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, VESSEL_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sVesselName +"','"+ sCallName +"','"+ callXML +"','"+ REFNO +"'", sCabname, sSessionId);
					WriteToLog_showpage("Y","BasicVesselDetails callXML TFO_DJ_INTEGRATION_CALLS_DTLS: "+ sQuery + " *****************************");	
					sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);						
					WriteToLog_showpage("Y","BasicVesselDetails sOutput: "+ sOutput);	
						
					
					int liveCounter = 0;
					sOutput = socket.connectToSocket(callXML);
					WriteToLog_showpage("Y","BasicVesselDetails callXML sOutput"+ sOutput);
					xp = new XMLParser(sOutput);
					returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
					totalRecords = Integer.parseInt(xp.getValueOf("totalRecords"));
					String nextValue ="";
					if(returnCode == 0 && totalRecords > 0){
						
						//int noOfRecords = xp.getNoOfFields("vesselBasicDetails");
						for(int j = 0; j < totalRecords; j++){
							
							nextValue = xp.getNextValueOf("vesselInfo");
							XMLParser xp1 = new XMLParser(nextValue);
							if(xp1.getValueOf("vesselStatus").equalsIgnoreCase("Live")){
								liveCounter +=1;
								vesselId = xp1.getValueOf("vesselId");
								vesselimo = xp1.getValueOf("vesselimo");
								vesselName = characterHandler(xp1.getValueOf("vesselName"));
								vesselBuilt = xp1.getValueOf("vesselBuilt");
								vesselflag = xp1.getValueOf("vesselflag");
								vesselcallsign = xp1.getValueOf("vesselcallsign");
								maritimeMobileID = xp1.getValueOf("maritimeMobileID");
								portOfRegistry = characterHandler(xp1.getValueOf("portOfRegistry"));
								grossTonnage = xp1.getValueOf("grossTonnage");
								netTonnage = xp1.getValueOf("netTonnage");
								dwtTonnage = xp1.getValueOf("dwtTonnage");
								vesselGenericType = xp1.getValueOf("vesselGenericType");
								subtype = characterHandler(xp1.getValueOf("subtype"));
								vesselType = xp1.getValueOf("vesselType");
								vesselStatus = xp1.getValueOf("vesselStatus");
								lastUpdated = xp1.getValueOf("lastUpdated");
							
								sQuery = prepareAPInsertInputXml("TFO_DJ_LLI_BASIC_VSSL_TEMP","SNO,WI_NAME, VESSEL_ID, VESSEL_IMO, VESSEL_NAME, VESSEL_BUILT, VESSEL_FLAG, VESSEL_CALL_SIGN, MARITIME_MOBILE_ID, PORT_OF_REGISTRY, GROSS_TONNAGE, NET_TONNAGE, DWT_TONNAGE, VESSEL_GENERIC_TYPE, VESSEL_SUBTYPE, VESSEL_TYPE, VESSEL_STATUS, LAST_UPDATED","'" + liveCounter + "','" + sWIName + "','" + vesselId + "','" + vesselimo + "','" + vesselName + "','" + vesselBuilt + "','" + vesselflag + "','" + vesselcallsign + "','" + maritimeMobileID + "','" + portOfRegistry + "','" + grossTonnage + "','" + netTonnage + "','" + dwtTonnage + "','" + vesselGenericType + "','" + subtype + "','" + vesselType + "','" + vesselStatus + "','" + lastUpdated + "'",sCabname,sSessionId); 
								WriteToLog_showpage("Y","BasicVesselDetails insert query TFO_DJ_LLI_BASIC_VSSL_TEMP: "+ sQuery);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
								WriteToLog_showpage("Y","BasicVesselDetails output insert query TFO_DJ_LLI_BASIC_VSSL_TEMP: " + sOutput);
								
								XMLParser xpInner=null;
								String sQueryVF = "select Country_full_name as VESSEL_FLAG from TFO_DJ_LLI_COUNTRY_MAST where country_code = '"+vesselflag+"'";
								String sInputVF = prepareAPSelectInputXml(sQueryVF,sCabname,sSessionId);
								WriteToLog_showpage("Y"," ***************select flag "+sInputVF+" *****************************");
								String sOutputVF = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputVF);
								xpInner = new XMLParser(sOutputVF); 		
								veselDetailsFromIntegration = veselDetailsFromIntegration + vesselName+"@@"+vesselimo+"@@"+vesselId+"@@"+vesselType+"@@"+xpInner.getValueOf("VESSEL_FLAG")+"@@"+vesselStatus+"$$";
								//veselDetailsFromIntegration = veselDetailsFromIntegration + vesselName+"@@"+vesselimo+"@@"+vesselId+"@@"+vesselType+"@@"+xpInner.getValueOf("VESSEL_FLAG")+"@@"+vesselStatus+"$$";
								
								
							}
						}
						
								
						WriteToLog_showpage("Y","live counter " + liveCounter);
						if(liveCounter == 0){
							WriteToLog_showpage("Y","live counter if " + liveCounter);
							out.println("1#NO LIVE VESSEL FOUND");
						}else{
							WriteToLog_showpage("Y","live counter else  " + liveCounter);
							out.println("0#SUCCESS#SUCCESS#"+veselDetailsFromIntegration);
						}	
					} else if(totalRecords == 0){
						WriteToLog_showpage("Y","total record empty  " + totalRecords); 
						out.println("1#VESSEL_DETAILS_NOT_FOUND");
					} else {
						WriteToLog_showpage("Y","failure  ");
						out.println("1#CALL FAILURE");
					}
					
					
				} else if(sCallName.equalsIgnoreCase("OwnershipDetails")){
					insertBasicDetails(sVesselID,sCabname,sSessionId,sWIName,sJtsIp,iJtsPort);
					sQuery = prepareAPSelectInputXml("SELECT HISTORYTYPE FROM TFO_DJ_LLI_VSL_OWNRSHIP_MAST WHERE DEFAULTVALUE = 'Yes'", sCabname, sSessionId);
					WriteToLog_showpage("Y","HISTORYTYPE: "+ sQuery);	
					sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
					WriteToLog_showpage("Y","HISTORYTYPE sOutput: "+ sOutput);	
					String historyType = "";
					if(!sOutput.equals(""))
					{
						String sMainCode = sOutput.substring(sOutput.indexOf("<MainCode>")+10,sOutput.indexOf("</MainCode>"));						
						if(sMainCode.equalsIgnoreCase("0"))
						{
							historyType = sOutput.substring(sOutput.indexOf("<HISTORYTYPE>")+13,sOutput.indexOf("</HISTORYTYPE>"));
						}
					}					
					
					callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>LLIVesselOps</InnerCallType><refNo>"+ REFNO +"</refNo><senderID>WMSDGTRD</senderID><winame>"+ sWIName +"</winame><serviceName>InqCustomerTradeJourney</serviceName><vesselName>"+ sVesselName + (char)(172) +"</vesselName><historyType>"+ historyType +"</historyType><relationshipType>all</relationshipType><operationType>fetchVesselOwnershipDtls</operationType><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";
					
					sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, VESSEL_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sVesselName +"','"+ sCallName +"','"+ callXML +"','"+ REFNO +"'", sCabname, sSessionId);
					WriteToLog_showpage("Y","OwnershipDetails callXML TFO_DJ_INTEGRATION_CALLS_DTLS "+ sQuery);	
					sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);						
					WriteToLog_showpage("Y","OwnershipDetails sOutput"+ sOutput);	
					sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sOutput);
					
					sOutput = socket.connectToSocket(callXML);
					WriteToLog_showpage("Y","OwnershipDetails callXML sOutput"+ sOutput);
					xp = new XMLParser(sOutput);
					returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
					int liveCounterOwner = 0;
					if(returnCode == 0){
						totalRecords = Integer.parseInt(xp.getValueOf("totalRecords"));
						if(totalRecords > 0){
						int noOfRecords = xp.getNoOfFields("vesselOwnership");                                                                                
							for(int j = 0; j < noOfRecords; j++){
								String nextValue = xp.getNextValueOf("vesselOwnership");
								XMLParser xp1 = new XMLParser(nextValue);
								if(xp1.getValueOf("vesselID").equalsIgnoreCase(sVesselID)){
									liveCounterOwner +=1;
									vesselId = xp1.getValueOf("vesselID");
									vesselimo = xp1.getValueOf("vesselimo");
									vesselName = characterHandler(xp1.getValueOf("vesselName"));
									relationshipType = xp1.getValueOf("relationshipType");						
									companyId = xp1.getValueOf("companyId");
									companyName = characterHandler(xp1.getValueOf("companyName"));
									startDate = xp1.getValueOf("startDate");
									startQualifier = xp1.getValueOf("startQualifier");
									endDate = xp1.getValueOf("endDate");
									endQualifier = xp1.getValueOf("endQualifier");						
									ownershipLastUpdated = xp1.getValueOf("lastUpdated");
									
									sQuery = prepareAPInsertInputXml("TFO_DJ_LLI_OWNERSHIP_DTLS","SNO, WI_NAME, VESSEL_ID, VESSEL_NAME,VESSEL_IMO, RELATIONSHIP_TYPE, COMPANY_ID,COMPANY_NAME, START_DATE, START_QUALIFIER, END_DATE, END_QUALIFIER, LAST_UPDATED","'" + liveCounterOwner + "','" + sWIName + "','" + vesselId + "','" + vesselName + "','" + vesselimo + "','" + relationshipType + "','" + companyId + "','" + companyName + "','" + startDate + "','" + startQualifier + "','" + endDate + "','" + endQualifier + "','" + ownershipLastUpdated + "'", sCabname, sSessionId); 
									
									WriteToLog_showpage("Y","OwnershipDetails insert query TFO_DJ_LLI_OWNERSHIP_DTLS: "+ sQuery);
									sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
									WriteToLog_showpage("Y","OwnershipDetails output insert query TFO_DJ_LLI_OWNERSHIP_DTLS: " + sOutput);
									
								}
							}
							
							if(liveCounterOwner > 0){
								sQuery ="Select company_name from tfo_dj_lli_ownership_dtls where WI_NAME = '"+ sWIName +"' AND UPPER(VESSEL_NAME)= '"+ sVesselName.toUpperCase() +"' AND relationship_type = 'RO' AND rownum <= 1";
								WriteToLog_showpage("Y","Registered Owner "+ sQuery);
								String sInputXML = prepareAPSelectInputXml(sQuery,sCabname, sSessionId);					
								if(!sInputXML.isEmpty()){
									sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXML);
									WriteToLog_showpage("Y","Registered Owner "+ sOutput);
									xp = new XMLParser(sOutput);
									
									sQuery = prepareAPUpdateInputXml("TFO_DJ_LLI_BASIC_VSSL_DTLS","REGISTERED_OWNER","'" + xp.getValueOf("company_name") + "'","WI_NAME = '"+ sWIName +"' AND UPPER(VESSEL_NAME)= '"+ sVesselName.toUpperCase() +"'", sCabname, sSessionId);
									WriteToLog_showpage("Y","Registered Owner insert query TFO_DJ_LLI_BASIC_VSSL_DTLS: "+ sQuery);
									sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
									WriteToLog_showpage("Y","Registered Owner insert query TFO_DJ_LLI_BASIC_VSSL_DTLS: " + sOutput);
								}
							}
						}
						
						if(liveCounterOwner == 0)
							out.println("1#NO DETAILS FOR SELECTED VESSEL");
						else
							out.println("0#SUCCESS");			
					} else {
						out.println("1#CALL FAILURE");
					}
					
				}
				//ATP-250 22-01-2024 REYAZ
				//START CODE
				/* else if(sCallName.indexOf("MovementDetails") != -1){
					int callIndexLast = sCallName.indexOf("_Last");
					int callIndex = -1;
					if(callIndexLast != -1){
						callIndex = Integer.parseInt(sCallName.substring(sCallName.indexOf("_") + 1, callIndexLast));
					} else {
						callIndex = Integer.parseInt(sCallName.substring(sCallName.indexOf("_") + 1, sCallName.length()));
					}
					
					WriteToLog_showpage("Y","callIndex: "+ sCallName + ": " + callIndex);
					sQuery = prepareAPSelectInputXml("SELECT FROM_MVMNT_DATE, TO_MVMNT_DATE FROM TFO_DJ_LLI_MVMNT_DTLS WHERE WI_NAME = N'"+ sWIName + "' AND VESSEL_NAME = N'"+ sVesselName +"' AND SNO = "+ callIndex, sCabname, sSessionId);
					WriteToLog_showpage("Y","Movement Date Range: "+ sQuery);	
					sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
					WriteToLog_showpage("Y","Movement Date Range sOutput: "+ sOutput);	
					String historyType = "";
					if(!sOutput.equals(""))
					{
						String sMainCode = sOutput.substring(sOutput.indexOf("<MainCode>")+10,sOutput.indexOf("</MainCode>"));						
						if(sMainCode.equalsIgnoreCase("0"))
						{
							fromMD = sOutput.substring(sOutput.indexOf("<FROM_MVMNT_DATE>")+17,sOutput.indexOf("</FROM_MVMNT_DATE>"));
							toMD = sOutput.substring(sOutput.indexOf("<TO_MVMNT_DATE>")+15,sOutput.indexOf("</TO_MVMNT_DATE>"));
							sQuery = prepareAPSelectInputXml("SELECT RECORDTYPE FROM TFO_DJ_LLI_RECORD_TYPE_MAST WHERE UPPER(DEFAULTVALUE) = 'YES'", sCabname, sSessionId);
							WriteToLog_showpage("Y","RECORDTYPE: "+ sQuery);	
							sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
							WriteToLog_showpage("Y","RECORDTYPE sOutput: "+ sOutput);
							xp = new XMLParser(sOutput);
							callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>LLIVesselOps</InnerCallType><refNo>"+ REFNO +"</refNo><senderID>WMSDGTRD</senderID><winame>"+ sWIName +"</winame><serviceName>InqCustomerTradeJourney</serviceName><vesselName>"+ sVesselName + (char)(172)+"</vesselName><reportOnVesselPort>"+ xp.getValueOf("RECORDTYPE") +"</reportOnVesselPort><fromDate>"+ fromMD +"</fromDate><toDate>"+ toMD +"</toDate><operationType>fetchVesselMovementDtls</operationType><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";
							WriteToLog_showpage("Y","MovementDetails callXML Input:"+ callXML);
							sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, VESSEL_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sVesselName +"','"+ sCallName +"','"+ callXML +"','"+ REFNO +"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","MovementDetails callXML TFO_DJ_INTEGRATION_CALLS_DTLS "+ sQuery);	
							sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);						
							WriteToLog_showpage("Y","MovementDetails sOutput"+ sOutput);	
							//sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sOutput);
							
							sOutput = socket.connectToSocket(callXML);
							WriteToLog_showpage("Y","MovementDetails callXML sOutput:"+ sOutput);
							xp = new XMLParser(sOutput);
							returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
							
							
							if(returnCode == 0){								
								totalRecords = Integer.parseInt(xp.getValueOf("totalRecords"));
								if(totalRecords > 0){
									
									for(int vslCounter=0; vslCounter < totalRecords; vslCounter++){
										String VesselInfoMvmnt = xp.getNextValueOf("vesselInfo");
										XMLParser xpMvmnt = new XMLParser(VesselInfoMvmnt);
										
										if(xpMvmnt.getValueOf("vesselId").equalsIgnoreCase(sVesselID)){
											int noOfRecords = xpMvmnt.getNoOfFields("sightingInfo");
											for(int j = 0; j < noOfRecords; j++){
												liveCounterSightMvmnt +=1;
												String nextValue = xpMvmnt.getNextValueOf("sightingInfo");
												XMLParser xp1 = new XMLParser(nextValue);
												mmsi = xp1.getValueOf("mmsi");
												sighttype = xp1.getValueOf("type");
												fromGmt = xp1.getValueOf("fromGmt");                                             
												toGmt = xp1.getValueOf("toGmt");
												latitude = characterHandler(xp1.getValueOf("latitude"));
												longitude = characterHandler(xp1.getValueOf("longitude"));
												SpeedoverGround = xp1.getValueOf("SpeedoverGround");
												CourseoverGround = xp1.getValueOf("CourseoverGround");
												nearestPlace = characterHandler(xp1.getValueOf("nearestPlace"));                                              
												nearestCountry = characterHandler(xp1.getValueOf("nearestCountry"));
												distance = xp1.getValueOf("distance");
												destination = characterHandler(xp1.getValueOf("destination"));
												eta = xp1.getValueOf("eta");
												Draft = xp1.getValueOf("Draft");              
												numberOfUpdates = xp1.getValueOf("numberOfUpdates");
												
												sQuery = prepareAPSelectInputXml("SELECT COUNTRY_SHORT_NAME FROM TFO_DJ_LLI_COUNTRY_MAST WHERE UPPER(COUNTRY_CODE) = UPPER('"+ nearestCountry +"')", sCabname, sSessionId);
												WriteToLog_showpage("Y","MovementDetails COUNTRY_SHORT_NAME: "+ sQuery);
												sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
												WriteToLog_showpage("Y","MovementDetails COUNTRY_SHORT_NAME: " + sOutput);
												XMLParser xp2 = new XMLParser(sOutput);
												nearestCountry = xp2.getValueOf("COUNTRY_SHORT_NAME");
												
												sQuery = prepareAPInsertInputXml("TFO_DJ_LLI_MVMNT_DTLS_SIGHTING","SNO, WI_NAME, VESSEL_NAME, MMSI, VESSEL_TYPE, FROM_GMT, TO_GMT, LATITUDE, LONGITUDE, SPEED_OVER_GROUND, COURSE_OVER_GROUND, NEAREST_PLACE, NEAREST_COUNTRY, DISTANCE, DESTINATION, ETA,DRAFT, NUMBER_OF_UPDATES, VESSEL_ID","'" + liveCounterSightMvmnt + "','" + sWIName + "','" + sVesselName + "','" + mmsi + "','" + sighttype + "','" + fromGmt + "','" + toGmt + "','" + latitude + "','" + longitude + "','" + SpeedoverGround + "','" + CourseoverGround + "','" + nearestPlace + "','" + nearestCountry +"','"+ distance + "','" + destination + "','" + eta +"','" + Draft + "','" + numberOfUpdates + "','" + xpMvmnt.getValueOf("vesselId") +  "'", sCabname,sSessionId); 									
		
												WriteToLog_showpage("Y","MovementDetails insert query TFO_DJ_LLI_MVMNT_DTLS_SIGHTING: "+ sQuery);
												sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
												WriteToLog_showpage("Y","MovementDetails output insert query TFO_DJ_LLI_MVMNT_DTLS_SIGHTING: " + sOutput);							
											}
										
										
											noOfRecords = xpMvmnt.getNoOfFields("callingInfo");
											for(int j = 0; j < noOfRecords; j++){
												liveCounterCallMvmnt +=1;
												String nextValue = xpMvmnt.getNextValueOf("callingInfo");
												XMLParser xp1 = new XMLParser(nextValue);
												moveId = xp1.getValueOf("moveId");
												moveSequence = xp1.getValueOf("moveSequence");
												place = characterHandler(xp1.getValueOf("place"));                                                
												country = characterHandler(xp1.getValueOf("country"));
												arrivalDate = xp1.getValueOf("arrivalDate");
												arrivalEstimated = xp1.getValueOf("arrivalEstimated");
												arrivalQualifier = xp1.getValueOf("arrivalQualifier");
												sailingDate = xp1.getValueOf("sailingDate");
												sailingEstimated = xp1.getValueOf("sailingEstimated");                                    
												sailingQualifier = xp1.getValueOf("sailingQualifier");
												movementType = characterHandler(xp1.getValueOf("movementType"));
												callingLastUpdated = xp1.getValueOf("lastUpdated");
										
												sQuery = prepareAPInsertInputXml("TFO_DJ_LLI_MVMNT_DTLS_CALLING","SNO, WI_NAME, VESSEL_NAME, MOVE_ID, MOVE_SEQUENCE, PLACE, COUNTRY, ARRIVAL_DATE, ARRIVAL_ESTIMATED, ARRIVAL_QUALIFIER, SAILING_DATE, SAILING_ESTIMATED, SAILING_QUALIFIER, MOVEMENT_TTYPE, LAST_UPDATED, VESSEL_ID","'" + liveCounterCallMvmnt + "','" + sWIName + "','" + sVesselName + "','" + moveId + "','" + moveSequence + "','" + place + "','" + country + "','" + arrivalDate + "','" + arrivalEstimated + "','" + arrivalQualifier + "','" + sailingDate + "','" + sailingEstimated + "','" + sailingQualifier + "','" + movementType + "','" + callingLastUpdated +  "','" + xpMvmnt.getValueOf("vesselId") +  "'",sCabname,sSessionId); 
		
												WriteToLog_showpage("Y","MovementDetails insert query TFO_DJ_LLI_MVMNT_DTLS_CALLING: "+ sQuery);
												sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
												WriteToLog_showpage("Y","MovementDetails output insert query TFO_DJ_LLI_MVMNT_DTLS_CALLING: " + sOutput);
												
											}
										}										
									}
								}
								//if(liveCounterCallMvmnt ==0 && liveCounterSightMvmnt == 0)
									//out.println("1#NO DETAILS FOR SELECTED VESSEL");
								//else{
									out.println("0#SUCCESS");
								//}
								
							} else {
								out.println("1#CALL FAILURE");
							}
						}					
					
					}	
				
				} */
				else if(sCallName.indexOf("MovementDetails") != -1){
					int callIndexLast = sCallName.indexOf("_Last");
					int callIndex = -1;
					if(callIndexLast != -1){
						callIndex = Integer.parseInt(sCallName.substring(sCallName.indexOf("_") + 1, callIndexLast));
					} else {
						callIndex = Integer.parseInt(sCallName.substring(sCallName.indexOf("_") + 1, sCallName.length()));
					}
					
					WriteToLog_showpage("Y","callIndex: "+ sCallName + ": " + callIndex);
					sQuery = prepareAPSelectInputXml("SELECT FROM_MVMNT_DATE, TO_MVMNT_DATE FROM TFO_DJ_LLI_MVMNT_DTLS WHERE WI_NAME = N'"+ sWIName + "' AND VESSEL_NAME = N'"+ sVesselName +"' AND SNO = "+ callIndex, sCabname, sSessionId);
					WriteToLog_showpage("Y","Movement Date Range: "+ sQuery);	
					sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
					WriteToLog_showpage("Y","Movement Date Range sOutput: "+ sOutput);	
					String historyType = "";
					if(!sOutput.equals(""))
					{
						String sMainCode = sOutput.substring(sOutput.indexOf("<MainCode>")+10,sOutput.indexOf("</MainCode>"));						
						if(sMainCode.equalsIgnoreCase("0"))
						{
							fromMD = sOutput.substring(sOutput.indexOf("<FROM_MVMNT_DATE>")+17,sOutput.indexOf("</FROM_MVMNT_DATE>"));
							toMD = sOutput.substring(sOutput.indexOf("<TO_MVMNT_DATE>")+15,sOutput.indexOf("</TO_MVMNT_DATE>"));
							sQuery = prepareAPSelectInputXml("SELECT RECORDTYPE FROM TFO_DJ_LLI_RECORD_TYPE_MAST WHERE UPPER(DEFAULTVALUE) = 'YES'", sCabname, sSessionId);
							WriteToLog_showpage("Y","RECORDTYPE: "+ sQuery);	
							sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
							WriteToLog_showpage("Y","RECORDTYPE sOutput: "+ sOutput);
							xp = new XMLParser(sOutput);
							callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>LLIVesselOps</InnerCallType><refNo>"+ REFNO +"</refNo><senderID>WMSDGTRD</senderID><winame>"+ sWIName +"</winame><serviceName>InqCustomerTradeJourney</serviceName><vesselName>"+ sVesselName + (char)(172)+"</vesselName><dateRange>"+ toMD +"</dateRange><fromDate>"+ fromMD +"</fromDate><toDate>"+ toMD +"</toDate><operationType>fetchVesselCalls</operationType><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";
							WriteToLog_showpage("Y","fetchVesselCalls callXML Input:"+ callXML);
							sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, VESSEL_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sVesselName +"','"+ sCallName +"','"+ callXML +"','"+ REFNO +"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","fetchVesselCalls callXML TFO_DJ_INTEGRATION_CALLS_DTLS "+ sQuery);	
							sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);						
							WriteToLog_showpage("Y","fetchVesselCalls sOutput"+ sOutput);	
							//sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sOutput);
							
							sOutput = socket.connectToSocket(callXML);
							WriteToLog_showpage("Y","fetchVesselCalls callXML sOutput:"+ sOutput);
							xp = new XMLParser(sOutput);
							returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
							WriteToLog_showpage("Y","fetchVesselCalls returnCode:  "+ returnCode);
							
							if(returnCode == 0){
								totalRecords = Integer.parseInt(xp.getValueOf("totalRecords"));
								WriteToLog_showpage("Y","fetchVesselCalls totalRecords:  "+ totalRecords);
								if(totalRecords > 0){
									
									for(int vslCounter=0; vslCounter < totalRecords; vslCounter++){
										String VesselInfoMvmnt = xp.getNextValueOf("vesselDtls");
										WriteToLog_showpage("Y","fetchVesselCalls VesselInfoMvmnt:  "+ VesselInfoMvmnt);
										XMLParser xpMvmnt = new XMLParser(VesselInfoMvmnt);
										
										if(xpMvmnt.getValueOf("vesselId").equalsIgnoreCase(sVesselID)){
											int noOfRecords = xpMvmnt.getNoOfFields("callsType");
											for(int j = 0; j < noOfRecords; j++){
												liveCounterCallMvmnt +=1;
												String nextValue = xpMvmnt.getNextValueOf("callsType");
												XMLParser xp1 = new XMLParser(nextValue);
												callingId = xp1.getValueOf("callingId");
												arrivalDate = xp1.getValueOf("arrivalDate");
												arrivalQualifier = xp1.getValueOf("arrivalQualifier");
												sailingDate = xp1.getValueOf("sailingDate");
												sailingQualifier = xp1.getValueOf("sailingQualifier"); 
                                                callingLastUpdated = xp1.getValueOf("lastUpdated");
												nextValue = xp1.getNextValueOf("place");
												XMLParser xp2 = new XMLParser(nextValue);
												place = characterHandler(xp2.getValueOf("placeName"));  // ATP-412 REYAZ 20-02-2024                                              
												country = characterHandler(xp2.getValueOf("countryName")); // ATP-412 REYAZ 20-02-2024 
										
												sQuery = prepareAPInsertInputXml("TFO_DJ_LLI_MVMNT_DTLS_CALLING","SNO, WI_NAME, VESSEL_NAME, MOVE_ID, MOVE_SEQUENCE, PLACE, COUNTRY, ARRIVAL_DATE, ARRIVAL_ESTIMATED, ARRIVAL_QUALIFIER, SAILING_DATE, SAILING_ESTIMATED, SAILING_QUALIFIER, MOVEMENT_TTYPE, LAST_UPDATED, VESSEL_ID","'" + liveCounterCallMvmnt + "','" + sWIName + "','" + sVesselName + "','','','" + place + "','" + country + "','" + arrivalDate + "','','" + arrivalQualifier + "','" + sailingDate + "','','" + sailingQualifier + "','','" + callingLastUpdated +  "','" + xpMvmnt.getValueOf("vesselId") +  "'",sCabname,sSessionId); 
		
												WriteToLog_showpage("Y","fetchVesselCalls insert query TFO_DJ_LLI_MVMNT_DTLS_CALLING: "+ sQuery);
												sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
												WriteToLog_showpage("Y","fetchVesselCalls output insert query TFO_DJ_LLI_MVMNT_DTLS_CALLING: " + sOutput); 
												
											} 
										} 
									} 
								}
								
							} else {
								out.println("1#CALL FAILURE");
							} 
							
						   sInput = prepareAPSelectInputXml("select seq_webservice.nextval REFNO from dual",sCabname,sSessionId);
						   sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
						   REFNO = sOutput.substring(sOutput.indexOf("<REFNO>")+7,sOutput.indexOf("</REFNO>"));
						   
                           callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>LLIVesselOps</InnerCallType><refNo>"+ REFNO +"</refNo><senderID>WMSDGTRD</senderID><winame>"+ sWIName +"</winame><serviceName>InqCustomerTradeJourney</serviceName><vesselName>"+ sVesselName + (char)(172)+"</vesselName><dateRange>"+ toMD +"</dateRange><fromDate>"+ fromMD +"</fromDate><toDate>"+ toMD +"</toDate><operationType>fetchVesselSightings</operationType><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";
							WriteToLog_showpage("Y","fetchVesselSightings callXML Input:"+ callXML);
							sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, VESSEL_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sVesselName +"','"+ sCallName +"','"+ callXML +"','"+ REFNO +"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","Fetch Vessel Sightings callXML TFO_DJ_INTEGRATION_CALLS_DTLS "+ sQuery);	
							sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);						
							WriteToLog_showpage("Y","fetchVesselSightings sOutput"+ sOutput);	
							//sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sOutput);
							
							sOutput = socket.connectToSocket(callXML);
							WriteToLog_showpage("Y","fetchVesselSightings callXML sOutput:"+ sOutput);
							xp = new XMLParser(sOutput);
							returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
							
							
							if(returnCode == 0){								
								totalRecords = Integer.parseInt(xp.getValueOf("totalRecords"));
								if(totalRecords > 0){
									
									for(int vslCounter=0; vslCounter < totalRecords; vslCounter++){
										String VesselInfoMvmnt = xp.getNextValueOf("vesselDtls");
										XMLParser xpMvmnt = new XMLParser(VesselInfoMvmnt);
										
										if(xpMvmnt.getValueOf("vesselId").equalsIgnoreCase(sVesselID)){
											int noOfRecords = xpMvmnt.getNoOfFields("sightingsType");
											for(int j = 0; j < noOfRecords; j++){
												liveCounterSightMvmnt +=1;
												String nextValue = xpMvmnt.getNextValueOf("sightingsType");
												XMLParser xp1 = new XMLParser(nextValue);
												mmsi = xp1.getValueOf("mmsi");
												sighttype = xp1.getValueOf("type");
												fromGmt = xp1.getValueOf("fromGmt");                                             
												toGmt = xp1.getValueOf("toGmt");
												latitude = characterHandler(xp1.getValueOf("latitude"));
												longitude = characterHandler(xp1.getValueOf("longitude"));
												SpeedoverGround = xp1.getValueOf("sog");
												CourseoverGround = xp1.getValueOf("cog");
												nearestPlace = characterHandler(xp1.getValueOf("nearestPlace"));                                              
												nearestCountry = characterHandler(xp1.getValueOf("nearestCountry"));
												distance = xp1.getValueOf("distance");
												destination = characterHandler(xp1.getValueOf("destination"));
												eta = xp1.getValueOf("eta");
												Draft = xp1.getValueOf("draft");              
												numberOfUpdates = xp1.getValueOf("numberUpdates");
												
												sQuery = prepareAPSelectInputXml("SELECT COUNTRY_SHORT_NAME FROM TFO_DJ_LLI_COUNTRY_MAST WHERE UPPER(COUNTRY_CODE) = UPPER('"+ nearestCountry +"')", sCabname, sSessionId);
												WriteToLog_showpage("Y","Fetch Vessel Sightings COUNTRY_SHORT_NAME: "+ sQuery);
												sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
												WriteToLog_showpage("Y","Fetch Vessel Sightings COUNTRY_SHORT_NAME: " + sOutput);
												XMLParser xp2 = new XMLParser(sOutput);
												nearestCountry = xp2.getValueOf("COUNTRY_SHORT_NAME");
												
												sQuery = prepareAPInsertInputXml("TFO_DJ_LLI_MVMNT_DTLS_SIGHTING","SNO, WI_NAME, VESSEL_NAME, MMSI, VESSEL_TYPE, FROM_GMT, TO_GMT, LATITUDE, LONGITUDE, SPEED_OVER_GROUND, COURSE_OVER_GROUND, NEAREST_PLACE, NEAREST_COUNTRY, DISTANCE, DESTINATION, ETA,DRAFT, NUMBER_OF_UPDATES, VESSEL_ID","'" + liveCounterSightMvmnt + "','" + sWIName + "','" + sVesselName + "','" + mmsi + "','" + sighttype + "','" + fromGmt + "','" + toGmt + "','" + latitude + "','" + longitude + "','" + SpeedoverGround + "','" + CourseoverGround + "','" + nearestPlace + "','" + nearestCountry +"','"+ distance + "','" + destination + "','" + eta +"','" + Draft + "','" + numberOfUpdates + "','" + xpMvmnt.getValueOf("vesselId") +  "'", sCabname,sSessionId); 									
		
												WriteToLog_showpage("Y","fetchVesselSightings insert query TFO_DJ_LLI_MVMNT_DTLS_SIGHTING: "+ sQuery);
												sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
												WriteToLog_showpage("Y","fetchVesselSightings output insert query TFO_DJ_LLI_MVMNT_DTLS_SIGHTING: " + sOutput);							
											}
										
								}										
									}
								}
							} else {
								out.println("1#CALL FAILURE");
							} 
							out.println("0#SUCCESS");
						}					
					
					}	
				
				} 
				//ATP-250 22-01-2024 REYAZ
				//END CODE
				else if(sCallName.equalsIgnoreCase("ETADetails")){
					String mainCode = "";
					callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>LLIVesselOps</InnerCallType><refNo>"+ REFNO +"</refNo><senderID>WMSDGTRD</senderID><winame>"+ sWIName +"</winame><serviceName>InqCustomerTradeJourney</serviceName><vesselId>"+ sVesselID +"</vesselId><etaType>all</etaType><operationType>fetchVesselETADtls</operationType><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";
					WriteToLog_showpage("Y"," ETADetails callXML: "+ callXML);
					sOutput = socket.connectToSocket(callXML);
					WriteToLog_showpage("Y"," ETADetails sOutput: "+ sOutput);
					xp = new XMLParser(sOutput);
					returnCode = Integer.parseInt(xp.getValueOf("returnCode"));						
					WriteToLog_showpage("Y"," ETADetails returnCode: "+ returnCode);
					if(returnCode == 0){								
							totalRecords = Integer.parseInt(xp.getValueOf("totalRecords"));
							if(totalRecords>0){
								int noOfRecords = xp.getNoOfFields("vesselDetails");
								WriteToLog_showpage("Y"," ETADetails noOfRecords: "+ noOfRecords);
								for(int j = 0; j < noOfRecords; j++){
									String nextValue = xp.getNextValueOf("vesselDetails");
									XMLParser xp1 = new XMLParser(nextValue);
									noOfRecords = xp1.getNoOfFields("vesselEtaDetails");
									WriteToLog_showpage("Y"," ETADetails noOfRecords1: "+ noOfRecords);
									for(int k = 0; k < noOfRecords; k++){
										String nextValue1 = "";
										if(k == 0){
											nextValue1 = xp1.getValueOf("vesselEtaDetails");
										} else {
											nextValue1 = xp1.getNextValueOf("vesselEtaDetails");
										}
										
										
										WriteToLog_showpage("Y"," ETADetails vesselEtaDetails: "+ nextValue1);
										XMLParser xp2 = new XMLParser(nextValue1);
										etaPlaceName = characterHandler(xp2.getValueOf("placeName"));											
										etaPlaceCountry = characterHandler(xp2.getValueOf("placeCountry"));
										etaEstimatedTimeArrival = xp2.getValueOf("estimatedTimeArrival");
										etaTerminal = characterHandler(xp2.getValueOf("terminal"));
										etaVoyage = characterHandler(xp2.getValueOf("voyage"));
										etaEstimatedTimeDeparture = xp2.getValueOf("estimatedTimeDeparture");
										etaNextPort = characterHandler(xp2.getValueOf("nextPort"));
										etaSource = characterHandler(xp2.getValueOf("source"));
										
										sQuery = prepareAPInsertInputXml("TFO_DJ_LLI_MVMNT_ETA_DTLS","SNO, WI_NAME, VESSEL_ID, VESSEL_NAME, PLACE, COUNTRY, ETA, TERMINAL, VOYAGE, ETD, NEXT_PORT, SOURCE","'" + (k+1) + "','" + sWIName + "','" + sVesselID + "','" + sVesselName + "','" + etaPlaceName + "','" + etaPlaceCountry + "','" + etaEstimatedTimeArrival + "','" + etaTerminal + "','" + etaVoyage + "','" + etaEstimatedTimeDeparture + "','" + etaNextPort + "','" + etaSource + "'",sCabname,sSessionId); 

										WriteToLog_showpage("Y","ETADetails insert query TFO_DJ_LLI_MVMNT_ETA_DTLS: "+ sQuery);
										sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
										WriteToLog_showpage("Y","ETADetails output insert query TFO_DJ_LLI_MVMNT_ETA_DTLS: " + sOutput);
										
									}
								}
							}
						out.println("0#SUCCESS");
					} else {
						out.println("1#CALL FAILURE");
					}		
						
					
					
				} else if(sCallName.equalsIgnoreCase("CompanyDetails")){
					String tempCompanyId = "";
					int count = 0;
					String mainCode = "";
					sQuery = prepareAPSelectInputXml("SELECT DISTINCT COMPANY_ID FROM TFO_DJ_LLI_OWNERSHIP_DTLS WHERE WI_NAME = '" + sWIName + "' AND VESSEL_ID = "+ sVesselID, sCabname, sSessionId);
					WriteToLog_showpage("Y"," CompanyDetails COMPANY_ID: "+ sQuery);	
					sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
					WriteToLog_showpage("Y"," CompanyDetails COMPANY_ID sOutput: "+ sOutput);
					xp = new XMLParser(sOutput);
					mainCode = xp.getValueOf("MainCode");						
					if(mainCode.equals("0")){
						totalRecords = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
						if(totalRecords > 0){								
							WriteToLog_showpage("Y"," CompanyDetails noOfRecords: "+ totalRecords);
							for(int i = 0; i < totalRecords; i++){
								sInput = prepareAPSelectInputXml("select seq_webservice.nextval REFNO from dual",sCabname,sSessionId);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
								REFNO = sOutput.substring(sOutput.indexOf("<REFNO>")+7,sOutput.indexOf("</REFNO>"));
								WriteToLog_showpage("Y"," CompanyDetails REFNO: "+ REFNO);
								tempCompanyId = xp.getNextValueOf("COMPANY_ID");
								callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>LLIVesselOps</InnerCallType><refNo>"+ REFNO +"</refNo><senderID>WMSDGTRD</senderID><winame>"+ sWIName +"</winame><serviceName>InqCustomerTradeJourney</serviceName><CompanyId>"+ tempCompanyId +"</CompanyId><HeadOffice>true</HeadOffice><operationType>fetchVesselCompanyDtls</operationType><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";
								WriteToLog_showpage("Y"," CompanyDetails callXML: "+ callXML);
								sOutput = socket.connectToSocket(callXML);
								WriteToLog_showpage("Y"," CompanyDetails sOutput: "+ sOutput);
								XMLParser xp1 = new XMLParser(sOutput);
								returnCode = Integer.parseInt(xp1.getValueOf("returnCode"));						
								WriteToLog_showpage("Y"," CompanyDetails returnCode: "+ returnCode);
								if(returnCode == 0){								
									int totalRecords1 = Integer.parseInt(xp1.getValueOf("totalRecords"));
									if(totalRecords1 > 0){
										int noOfRecords1 = xp1.getNoOfFields("officeDtls");
										WriteToLog_showpage("Y"," CompanyDetails noOfRecords1: "+ noOfRecords1);
										for(int j = 0; j < noOfRecords1; j++){
											String nextValue = xp1.getNextValueOf("officeDtls");
											XMLParser xp2 = new XMLParser(nextValue);
											headOffice = xp2.getNextValueOf("headOffice");												
											countryName = xp2.getNextValueOf("countryName");
											if(headOffice.equalsIgnoreCase("true")){
												sQuery = prepareAPUpdateInputXml("TFO_DJ_LLI_OWNERSHIP_DTLS","COUNTRY_NAME","'" + countryName + "'","WI_NAME = '"+ sWIName +"' AND VESSEL_ID = '"+ sVesselID +"' AND COMPANY_ID = '"+tempCompanyId+"'", sCabname, sSessionId);
												WriteToLog_showpage("Y","CountryName update query CompanyDetails: "+ sQuery);
												sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
												WriteToLog_showpage("Y","CountryName Owner update query CompanyDetails: " + sOutput);
												break;
											}
										}
									}
								} else {
									count +=1;
									//out.println("1#Failure");
									//break;
									
								}
								Thread.sleep(1000);
								
							}	
						}
						if(count >0){
							out.println("0#PARTIAL SUCCESS#"+count+" CALLS FAILED");
						}else{
							out.println("0#SUCCESS");
						}
						
					}
				} else if(sCallName.equalsIgnoreCase("updateDocumentStatusUTC")){
					int count = 0;
					String mainCode = "";
					String currWS = "";
					String status = "";
					sQuery = prepareAPSelectInputXml("select invoice_number,utc_ref_no from tfo_dj_utc_invoice_detail WHERE WI_NAME = '" + sWIName + "'", sCabname, sSessionId);
					WriteToLog_showpage("Y"," UpdateDocumentDetailsUTC : "+ sQuery);	
					sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
					WriteToLog_showpage("Y"," UpdateDocumentDetailsUTC  sOutput: "+ sOutput);
					xp = new XMLParser(sOutput);
					mainCode = xp.getValueOf("MainCode");	
					 sQuery = prepareAPSelectInputXml("select curr_ws from ext_tfo WHERE WI_NAME = '" + sWIName + "'", sCabname, sSessionId);
					WriteToLog_showpage("Y"," UpdateDocumentDetailsUTC : "+ sQuery);	
				 sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
					WriteToLog_showpage("Y"," UpdateDocumentDetailsUTC  sOutput1: "+ sOutput);
					xp = new XMLParser(sOutput);
					currWS = xp.getValueOf("curr_ws");	
					if("PP_MAKER".equalsIgnoreCase(currWS)){
						status = "REJECTED";
					}
					else {
						status = "FINANCED";
					}
					if(mainCode.equals("0")){
						totalRecords = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
						if(totalRecords > 0){								
							WriteToLog_showpage("Y"," UpdateDocumentDetailsUTC noOfRecords: "+ totalRecords);
							int end = 0;
							int start = xp.getStartIndex("Records", 0, 0);
							int deadEnd = xp.getEndIndex("Records", start, 0);
							StringBuilder inputReqXml=new StringBuilder();
							for(int i = 0; i < totalRecords; i++){
								start = xp.getStartIndex("Record", end, 0);
								end = xp.getEndIndex("Record", start, 0);
								inputReqXml.append("<DocumentData>").append("\n")
								.append("<documentNo>"+xp.getValueOf("invoice_number", start, end)+"</documentNo>").append("\n")
								.append("<UTCRefNo>"+xp.getValueOf("utc_ref_no", start, end)+"</UTCRefNo>").append("\n")
								.append("<status>"+status+"</status>").append("\n")
								.append("<reason></reason>").append("\n")
								.append("</DocumentData>").append("\n");
							}
								sInput = prepareAPSelectInputXml("select seq_webservice.nextval REFNO from dual",sCabname,sSessionId);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
								REFNO = sOutput.substring(sOutput.indexOf("<REFNO>")+7,sOutput.indexOf("</REFNO>"));
								WriteToLog_showpage("Y"," CompanyDetails REFNO: "+ REFNO);
								callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>updateDocumentStatus</InnerCallType><REF_NO>"+ REFNO +"</REF_NO><senderID>WMS</senderID><WiName>"+ sWIName +"</WiName><serviceName>ModInvoiceValidation</serviceName><updateDocumentStatusReqMsg><Documents>"+inputReqXml.toString()+"</Documents></updateDocumentStatusReqMsg><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId></APWebService_Input>";
								
								WriteToLog_showpage("Y"," UpdateDocumentDetailsUTC callXML: "+ callXML);
								sOutput = socket.connectToSocket(callXML);
								WriteToLog_showpage("Y"," UpdateDocumentDetailsUTC sOutput: "+ sOutput);
								XMLParser xp1 = new XMLParser(sOutput);
								returnCode = Integer.parseInt(xp1.getValueOf("returnCode"));						
								WriteToLog_showpage("Y"," UpdateDocumentDetailsUTC returnCode: "+ returnCode);
								if(returnCode == 0){
									String updateQuery = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS","CALL_STATUS,STATUS","'Y','Success'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
									NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",updateQuery);
									
									String query = prepareAPInsertInputXml("TFO_DJ_DEC_HIST","WI_NAME,CURR_WS_NAME,CREATE_DATE,USERNAME,ACTION,REASON_FOR_ACTION,TRANSACTION_DATE_TIME,REMARKS","'" + sWIName + "','PROCESSING CHECKER',sysdate,'TFO User','Update Document UTC Call executed','',sysdate,'Success'",sCabname,sSessionId); 
								WriteToLog_showpage("Y","Decision insert query TFO_DJ_DEC_HIST: "+ query);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",query);
								WriteToLog_showpage("Y","Decision output insert query TFO_DJ_DEC_HIST: " + sOutput);
								
							


									out.println("0#SUCCESS");
								} else {
									count +=1;
									out.println("1#CALL FAILURE");	
								}
								Thread.sleep(1000);
						}
					}
				} 
			} else {
				WriteToLog_showpage("Y","RETRY:"+sOutput);	
				callXML = sOutput.substring(sOutput.indexOf("<CALL_XML>")+10,sOutput.indexOf("</CALL_XML>"));
				WriteToLog_showpage("Y","RETRY1");	
				sInput = prepareAPSelectInputXml("SELECT SEQ_WEBSERVICE.NEXTVAL REFNO FROM DUAL", sCabname, sSessionId);
				sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
				REFNO = sOutput.substring(sOutput.indexOf("<REFNO>")+7,sOutput.indexOf("</REFNO>"));
				callXML = callXML.replaceAll(oldREFNO,REFNO);
				WriteToLog_showpage("Y",callXML);
				sOutput = socket.connectToSocket(callXML);
			}
			
		}
		
		
	}
}
catch(Exception e)
{
	sInput=prepareAPInsertInputXml("USR_0_INTEGRATION_VALUES","wi_name,exception_error","'" + sWIName + "','"+e.getMessage()+"'",sCabname,sSessionId);
	WriteToLog_showpage("Y"," ***************Exception "+sInput+" *****************************");
	sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
	out.println("Error#FAILED#"+e.getMessage());
	e.printStackTrace();
}

%>




<%!
int liveCounterCallMvmnt = 0;
int liveCounterSightMvmnt = 0;
ConnectSocket socket;

private String prepareAPSelectInputXml(String sQuery, String sCabname, String sSessionId){	
	return "<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+sQuery+"</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
}

private String prepareAPInsertInputXml(String tableName,String colname,String sValues, String sCabname, String sSessionId){	
	return "<?xml version=\"1.0\"?><APInsert_Input><Option>APInsert</Option><TableName>"+tableName+"</TableName><ColName>"+colname+"</ColName><Values>"+sValues+"</Values><EngineName>" + sCabname + "</EngineName>" +    "<SessionId>" + sSessionId + "</SessionId></APInsert_Input>";
}

private String prepareAPUpdateInputXml(String tableName,String colname,String sValues,String whrcls, String sCabname, String sSessionId){	
	return "<?xml version=\"1.0\"?><APUpdate_Input><Option>APUpdate</Option><TableName>"+tableName+"</TableName><ColName>"+colname+"</ColName><Values>"+sValues+"</Values><WhereClause>"+whrcls+"</WhereClause><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APUpdate_Input>";
}

private String prepareAPProcInputXml(String ProcName,String ProcParams, String sCabname, String sSessionId){	
	return "<?xml version=\"1.0\"?><APProcedure_Input><Option>APProcedure</Option><ProcName>"+ProcName+"</ProcName><Params>"+ProcParams+"</Params><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APUpdate_Input>";
}

private String prepareAPDeleteXml(String tableName,String whereClause, String sCabname, String sSessionId){
	return "<?xml version=1.0?><APDelete_Input><Option>APDelete</Option><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId><TableName>"+tableName+"</TableName><WhereClause>"+whereClause+"</WhereClause></APDelete_Input>";

}

public void insertBasicDetails(String sVesselID, String sCabname, String sSessionId, String sWIName, String sJtsIp, String iJtsPort){
	try{
		String sInput="",sOutput="";
		XMLParser xpInner=null;
		String sQuery = "SELECT SNO, WI_NAME,VESSEL_ID, VESSEL_IMO,VESSEL_NAME, VESSEL_BUILT,VESSEL_FLAG, VESSEL_CALL_SIGN,MARITIME_MOBILE_ID, PORT_OF_REGISTRY,GROSS_TONNAGE, NET_TONNAGE,DWT_TONNAGE, VESSEL_GENERIC_TYPE,VESSEL_SUBTYPE, VESSEL_TYPE,VESSEL_STATUS, LAST_UPDATED,INSERTED_DATE_TIME, REGISTERED_OWNER FROM TFO_DJ_LLI_BASIC_VSSL_TEMP WHERE WI_NAME = '"+ sWIName +"' AND VESSEL_ID = '"+ sVesselID+"'";
		sInput = prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
		WriteToLog_showpage("Y"," ***************insertBasicDetails "+sInput+" *****************************");
		sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
		xpInner = new XMLParser(sOutput);
		
		sInput = prepareAPInsertInputXml("TFO_DJ_LLI_BASIC_VSSL_DTLS","SNO, WI_NAME, VESSEL_ID, VESSEL_IMO, VESSEL_NAME, VESSEL_BUILT, VESSEL_FLAG, VESSEL_CALL_SIGN, MARITIME_MOBILE_ID, PORT_OF_REGISTRY, GROSS_TONNAGE, NET_TONNAGE, DWT_TONNAGE, VESSEL_GENERIC_TYPE, VESSEL_SUBTYPE, VESSEL_TYPE, VESSEL_STATUS, LAST_UPDATED, INSERTED_DATE_TIME, REGISTERED_OWNER","'" + xpInner.getValueOf("SNO") + "','"+ xpInner.getValueOf("WI_NAME") +"','"+ xpInner.getValueOf("VESSEL_ID") +"','"+ xpInner.getValueOf("VESSEL_IMO") +"','"+ characterHandler(xpInner.getValueOf("VESSEL_NAME")) +"','"+ xpInner.getValueOf("VESSEL_BUILT") +"','"+ xpInner.getValueOf("VESSEL_FLAG") +"','"+ xpInner.getValueOf("VESSEL_CALL_SIGN") +"','"+ xpInner.getValueOf("MARITIME_MOBILE_ID") +"','"+ characterHandler(xpInner.getValueOf("PORT_OF_REGISTRY")) +"','"+ xpInner.getValueOf("GROSS_TONNAGE") +"','"+ xpInner.getValueOf("NET_TONNAGE") +"','"+ xpInner.getValueOf("DWT_TONNAGE") +"','"+ xpInner.getValueOf("VESSEL_GENERIC_TYPE") +"','"+ characterHandler(xpInner.getValueOf("VESSEL_SUBTYPE")) +"','"+ xpInner.getValueOf("VESSEL_TYPE") +"','"+ xpInner.getValueOf("VESSEL_STATUS") +"','"+ xpInner.getValueOf("LAST_UPDATED") +"','"+ xpInner.getValueOf("INSERTED_DATE_TIME") +"','"+ xpInner.getValueOf("REGISTERED_OWNER") +"'", sCabname, sSessionId);
		
		WriteToLog_showpage("Y","Insertion into basic vessel details"+sInput);	
		sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);						
		WriteToLog_showpage("Y","output of Insertion "+sOutput);
		
		sInput = prepareAPDeleteXml("TFO_DJ_LLI_BASIC_VSSL_TEMP"," WI_NAME ='"+sWIName+"' ",sCabname,sSessionId);
		sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);	
		WriteToLog_showpage("Y","Deletion record "+sOutput);
	}catch(Exception e){
		
	}
	
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
		
	Logger log = Logger.getLogger(_LLI_5F_Integration_5F_Calls_5F_Executor.class);
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
			strFilePath.append("LLI_Integration_Calls_Executor"+DtString+".xml");
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