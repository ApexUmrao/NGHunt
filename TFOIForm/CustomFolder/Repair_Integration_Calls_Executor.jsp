<jsp:useBean id="customSession" class="com.newgen.custom.wfdesktop.session.WFCustomSession" scope="session"/>

<%@ page import="ISPack.ISUtil.*,ISPack.*,java.io.*,Jdts.DataObject.JPDBInteger, Jdts.DataObject.JPDBISAddress,Jdts.DataObject.*,Jdts.*,java.net.*,java.awt.*"%>
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
<%@page import="com.newgen.omni.wf.util.excp.NGException"%>
<%@page import="org.apache.commons.codec.binary.Base64"%>
<%@page import="java.awt.Toolkit"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.newgen.omni.jts.cmgr.XMLParser"%>
<%@ page import="com.newgen.wfmonitor.xmlapi.*"%>
<%@ page import="com.newgen.wfdesktop.xmlapi.*" %>
<%@ page import="ISPack.ISUtil.*,ISPack.*,java.io.*,Jdts.DataObject.JPDBInteger, Jdts.DataObject.JPDBISAddress,Jdts.DataObject.*,Jdts.*,java.net.*,java.awt.*"%>
<%@ page import="com.newgen.wfdesktop.util.*" %>
<%@ page import=="com.newgen.iforms.user.tfo.util.ConnectSocket"%>
<%@page import="com.newgen.ao.laps.cache.CallEntity,com.newgen.ao.laps.callhandler.CallHandler,com.newgen.ao.laps.cache.ProcessEventCache,com.newgen.ao.laps.util.ProdCreateXML,com.newgen.ao.laps.util.APCallCreateXML,com.newgen.ao.laps.util.ExecuteXML"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.Properties"%>
<%@page import="org.apache.log4j.PropertyConfigurator"%>
<%@page import ="org.apache.log4j.Logger"%>

<%
initializeLogger();
sQuery="";
sInput="";
sOutput="";
String sRecord = "";
String sCall ="";
String sStatus ="";
String returnXML="";
String sReason ="";
String sErrorDescription="";
String sResponse="";
String sContractRefNumber="";
String sBillStage = "";
sCabname=customSession.getEngineName();
sSessionId = customSession.getDMSSessionId();
sUserName = customSession.getUserName();
sJtsIp = customSession.getJtsIp();
iJtsPort = String.valueOf(customSession.getJtsPort());
String sWIName = request.getParameter("WI_NAME");
String sCallName = request.getParameter("CALL_NAME");
String sStageId = request.getParameter("STAGE_ID");
String currWS = "";
String targetWS = "";
String callXML="";
String sResVal="";
String ErrorDesc="";
String SLNO="";
String errCase;
String oldREFNO="";
String REFNO = "";

XMLParser xp = null;
String returnCode = "";
String sExternalRefNumber = "";
String sOperationCode = "";
String sAmendType = "";
String sNewTransactionAmount = "";
String sProductCode = "";
String sNewExpiryDate = "";
String sRequestCategoryCode = "";
String sRequestTypeCode = "";
String sRelationshipType = "";
String limitSerialNumber = "";
String limitPartyType = "";
String limitLinkageType = "";
String limitPercentContribution = "";
String limitAmountTag = "";
String limitLineRefNumber = "";
String customerType = "";
limitLineCustomerId = "";
String CurrDate = "";
String sBranchCreateCode = "";
String sCurrentDateValue= "";
String sMaturityDateValue="";
String sContractLimitTag = "";

String sStage="";
String sLcReferenceNo="";
String sLiquidationAmount="";
String sMaturityDate="";
String sTenorDays="";
String stransitDays="";
String sBaseDate="";
String sDaysAfterBaseDate="";
String sPartyType="";
String sLimitTrackFlag="";
String sAcceptComFromDate="";
String sAcceptComToDate="";
String sCode="";
String sDescription="";
String sResolved="";
String sResolvedDate="";
String sReceivedDate="";
String sLimitOperation="";
String sLiquidationDate="";
String sFCUBSCurrentDate="";
String ngAddDocOutput = "";

String sSwiftProcStatus = "";
String processType = ""; //ATP-265 DATE 13-12-2023 BY REYAZ

try
{       
	    WriteToLog_showpage("Y","repair jsp starts");
		ExecuteXML.init("WebSphere", sJtsIp, iJtsPort);
		ProdCreateXML.init(sCabname);
		APCallCreateXML.init(sCabname);
		CallEntity callEntity = new CallEntity();
		callEntity.setCallName(sCallName);
		callEntity.setExecutionType("S"); 
		callEntity.setStage(sStageId);
		HashMap<String, String> attributeMap = new HashMap<String, String>();
		WriteToLog_showpage("Y","sStageId: "+sStageId);	
		sQuery = "select CURR_WS, TARGET_WORKSTEP,PROCESS_TYPE from EXT_TFO where WI_NAME='"+sWIName+"'";
		sInput = "<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+sQuery+"</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
		if(!sInput.equals(""))
		{
			sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);	
		}
		if(!sOutput.equals(""))
		{
			String sMainCode = sOutput.substring(sOutput.indexOf("<MainCode>")+10,sOutput.indexOf("</MainCode>"));
			if(sMainCode.equalsIgnoreCase("0"))
			{
				int i=1;				
				while((sOutput.indexOf("<Record>")>-1))
				{
					sRecord = sOutput.substring(sOutput.indexOf("<Record>")+8,sOutput.indexOf("</Record>"));
					currWS = sRecord.substring(sRecord.indexOf("<CURR_WS>")+9,sRecord.indexOf("</CURR_WS>"));
					targetWS = sRecord.substring(sRecord.indexOf("<TARGET_WORKSTEP>")+17,sRecord.indexOf("</TARGET_WORKSTEP>"));
					processType = sRecord.substring(sRecord.indexOf("<PROCESS_TYPE>")+14,sRecord.indexOf("</PROCESS_TYPE>"));  //ATP-265 DATE 13-12-2023 BY REYAZ
					WriteToLog_showpage("Y","currWS: "+currWS);
					i++;
					sOutput=sOutput.substring(sOutput.indexOf("</Record>")+"</Record>".length());
				}
			}
		}				
	    WriteToLog_showpage("Y","map initializing");
		if(!"Repair".equalsIgnoreCase(currWS)){
			attributeMap.put("ruleFlag","N");
		} else {
			attributeMap.put("ruleFlag","Y");
		}
		if("DC_ToDoList".equalsIgnoreCase(targetWS)){
			attributeMap.put("mode","DCR");
		} else if("DM_ToDoList".equalsIgnoreCase(targetWS) || "Customer Referral Response".equalsIgnoreCase(targetWS)){
			attributeMap.put("mode","DMR");
		} else {
			attributeMap.put("mode","R");
		}		
		//attributeMap.put("mode","R");
		//start
		WriteToLog_showpage("Y","EVENT_ID ::" +sStageId);
		WriteToLog_showpage("Y","CALL_NAME ::" +sCallName);
		ArrayList<Integer> dependencyCallID = new ArrayList<Integer>();
		sQuery = "select DEPENDENT_CALL_ID from BPM_EVENT_DEPENDENCY_MATRIX where EVENT_ID="+sStageId+" and CALL_ID in (select CALL_ID from USR_0_CBG_CALL_MASTER where CALL_NAME='"+sCallName+"')";
		sInput = "<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+sQuery+"</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
		if(!sInput.equals(""))
		{
			sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);	
		}
		if(!sOutput.equals(""))
		{
			String sMainCode = sOutput.substring(sOutput.indexOf("<MainCode>")+10,sOutput.indexOf("</MainCode>"));
			if(sMainCode.equalsIgnoreCase("0"))
			{
				int i=1;				
				while((sOutput.indexOf("<Record>")>-1))
				{
					sRecord = sOutput.substring(sOutput.indexOf("<Record>")+8,sOutput.indexOf("</Record>"));
					int depId = Integer.parseInt(sRecord.substring(sRecord.indexOf("<DEPENDENT_CALL_ID>")+19,sRecord.indexOf("</DEPENDENT_CALL_ID>")));
					WriteToLog_showpage("Y","depId: "+depId);
					dependencyCallID.add(depId);
					i++;
					sOutput=sOutput.substring(sOutput.indexOf("</Record>")+"</Record>".length());
				}
			}
		}
		callEntity.setDependencyCallID(dependencyCallID);
		//end
		WriteToLog_showpage("Y","map initialized");
		WriteToLog_showpage("Y","attributeMap  ::" +attributeMap);
	    WriteToLog_showpage("Y","sSessionId  ::" +sSessionId);
		WriteToLog_showpage("Y","sStageId  ::" +sStageId);
		WriteToLog_showpage("Y","sWIName  ::" +sWIName);
        sOutput=CallHandler.getInstance().executeCall(callEntity, attributeMap, sSessionId, sStageId, sWIName, false);
        WriteToLog_showpage("Y","output="+sOutput);
	    WriteToLog_showpage("Y","returnCode="+sOutput.substring(sOutput.indexOf("<returnCode>")+"<returnCode>".length(),sOutput.indexOf("</returnCode>")));
        returnCode=sOutput.substring(sOutput.indexOf("<returnCode>")+"<returnCode>".length(),sOutput.indexOf("</returnCode>"));
		if(sOutput.contains("<errorDescription>")){
		sErrorDescription=sOutput.substring(sOutput.indexOf("<errorDescription>")+"<errorDescription>".length(),sOutput.indexOf("</errorDescription>"));
		}else{
			if("0".equalsIgnoreCase(returnCode)){
				sErrorDescription="Success";
			}
		}
		
		if("0".equalsIgnoreCase(returnCode)){
			
			sQuery = prepareAPUpdateInputXml("bpm_orchestration_status","call_status","'Y'","WI_NAME = '"+ sWIName +"' and call_name='"+sCallName+"'", sCabname, sSessionId);
			WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
			sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
			WriteToLog_showpage("Y","sOutput: "+ sOutput);
			if("LinkDocuments".equalsIgnoreCase(sCallName)){
				sQuery = "select NG_ADD_DOC_OUTPUT from TFO_DJ_REFRESH_DOC where WI_NAME='"+sWIName+"'";
				sInput = "<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+sQuery+"</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
				if(!sInput.equals(""))
				{
					sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);	
				}
				if(!sOutput.equals(""))
				{
					String sMainCode = sOutput.substring(sOutput.indexOf("<MainCode>")+10,sOutput.indexOf("</MainCode>"));
					if(sMainCode.equalsIgnoreCase("0"))
					{	
						ngAddDocOutput = "";
						int i=1;				
						while((sOutput.indexOf("<Record>")>-1))
						{
							sRecord = sOutput.substring(sOutput.indexOf("<Record>")+8,sOutput.indexOf("</Record>"));
							ngAddDocOutput = ngAddDocOutput + sRecord.substring(sRecord.indexOf("<NG_ADD_DOC_OUTPUT>")+19,sRecord.indexOf("</NG_ADD_DOC_OUTPUT>")) + "$$$";
							WriteToLog_showpage("Y","ngAddDocOutput: "+ngAddDocOutput);
							//window.parent.refreshDocumentFrame(ngAddDocOutput);
							i++;
							sOutput=sOutput.substring(sOutput.indexOf("</Record>")+"</Record>".length());
						}
					}
				}
			}
			
			//ATP-265 DATE 13-12-2023 BY REYAZ
			//START
			if("TSLM Front End".equalsIgnoreCase(processType)){
				sQuery = "select count(1) as COUNT  from bpm_orchestration_status where wi_name ='"+sWIName+"' and call_status in ('N','F')";
				sInput = "<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+sQuery+"</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
				if(!sInput.equals(""))
				{
					WriteToLog_showpage("Y"," *************** Before output ");
					sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
					WriteToLog_showpage("Y"," *************** After Output " +sOutput);			
				}
				
				 if(!sOutput.equals(""))
				 {
						int count =0;
						String sMainCode = sOutput.substring(sOutput.indexOf("<MainCode>")+10,sOutput.indexOf("</MainCode>"));
						if(sMainCode.equalsIgnoreCase("0"))
						{
								WriteToLog_showpage("Y"," *************** sRecord ");
								WriteToLog_showpage("Y"," *************** count " +count);
								int i=1;				
								while((sOutput.indexOf("<Record>")>-1))
								{
									sRecord = sOutput.substring(sOutput.indexOf("<Record>")+8,sOutput.indexOf("</Record>"));
									count = Integer.parseInt(sRecord.substring(sRecord.indexOf("<COUNT>")+7,sRecord.indexOf("</COUNT>")));
									i++;
									sOutput=sOutput.substring(sOutput.indexOf("</Record>")+"</Record>".length());
								}						
							  
						}
						if(count == 0){
							sQuery = prepareAPUpdateInputXml("EXT_TFO","TARGET_WORKSTEP","'PP_MAKER'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","update Target workstep for Fintrade case "+ sQuery);
							sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
							WriteToLog_showpage("Y","Target workstep sOutput: "+ sOutput);
						} 
				 }
			} 
			//END 
			WriteToLog_showpage("Y",returnCode+"#"+"Success"+"#"+sErrorDescription+"#"+ngAddDocOutput);
			out.println(returnCode+"#"+"Success"+"#"+sErrorDescription+"#"+ngAddDocOutput);
         }else{
            out.println(returnCode+"#"+"Failure"+"#"+sErrorDescription);

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
ConnectSocket socket;
String sQuery="";
String sInput="";
String sOutput="";
String sUserName="";
String sCabname="";
String sSessionId ="";
String sJtsIp ="";
String iJtsPort = "";
String limitLineCustomerId = "";
String sContractAmount = "";
String sNegativeTolerance = "";
String sPositiveTolerance = "";
String sExpiryDate = "";
String sFromPlace = "";
String sToPlace = "";
String sLastShipmentDate = "";
String sPortOfDischarge = "";
String sPortOfLoading = "";
String sGoodsDescription = "";	
private String prepareAPSelectInputXml(String sQuery, String sCabname, String sSessionId){	
	return "<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId><QueryString>"+sQuery+"</QueryString></APSelectWithColumnNames_Input>";
}

private String prepareAPInsertInputXml(String tableName,String colname,String sValues, String sCabname, String sSessionId){	
	return "<?xml version=\"1.0\"?><APInsert_Input><Option>APInsert</Option><EngineName>" + sCabname + "</EngineName>" +    "<SessionId>" + sSessionId + "</SessionId><TableName>"+tableName+"</TableName><ColName>"+colname+"</ColName><Values>"+sValues+"</Values></APInsert_Input>";
}

private String prepareAPUpdateInputXml(String tableName,String colname,String sValues,String whrcls, String sCabname, String sSessionId){	
	return "<?xml version=\"1.0\"?><APUpdate_Input><Option>APUpdate</Option><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId><TableName>"+tableName+"</TableName><ColName>"+colname+"</ColName><Values>"+sValues+"</Values><WhereClause>"+whrcls+"</WhereClause></APUpdate_Input>";
}

private String prepareAPProcInputXml(String ProcName,String ProcParams, String sCabname, String sSessionId){	
	return "<?xml version=\"1.0\"?><APProcedure_Input><Option>APProcedure</Option><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId><ProcName>"+ProcName+"</ProcName><Params>"+ProcParams+"</Params></APUpdate_Input>";
}

private String prepareAPDeleteXml(String tableName,String whereClause, String sCabname, String sSessionId){
	return "<?xml version=1.0?><APDelete_Input><Option>APDelete</Option><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId><TableName>"+tableName+"</TableName><WhereClause>"+whereClause+"</WhereClause></APDelete_Input>";

}


private String convertToPlainString(String sInputXML){
	StringBuffer outputxml = new StringBuffer();
	WriteToLog_showpage("Y"," sInputXML.length() " + sInputXML.length());
    if (sInputXML.length() > 4000) {
      int itr = sInputXML.length() / 4000;
      int mod = sInputXML.length() % 4000;
      if (mod > 0) {
        ++itr;
      }
      WriteToLog_showpage("Y"," itr " + itr);
      for (int i = 0; i < itr; ++i) {
        WriteToLog_showpage("Y"," output part " + i + 1);
        if (i == 0) {
          outputxml.append("TO_NCLOB('" + sInputXML.substring(0, 4000) + "')");
        }
        else if (i < itr - 1) {
          outputxml.append(" || TO_NCLOB('" + sInputXML.substring(4000 * i, 4000 * (i + 1)) + "')");
        }
        else
        {
          outputxml.append(" || TO_NCLOB('" + sInputXML.substring(4000 * i, sInputXML.length()) + "') ");
        }
      }

    }
    else
    {
      //outputxml.append(sInputXML); 
 outputxml.append("'"+sInputXML+"'"); 
    }
	return outputxml.toString();
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
Logger log = Logger.getLogger(_Repair_5F_Integration_5F_Calls_5F_Executor.class);
	
private void WriteToLog_showpage(String flag,String strOutput){
	log.info(strOutput);
}
	
/*private void WriteToLog_showpage(String flag,String strOutput)
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
			strFilePath.append("Repair_Integration_Calls"+DtString+".xml");
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
