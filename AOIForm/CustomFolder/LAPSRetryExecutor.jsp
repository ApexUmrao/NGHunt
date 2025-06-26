<!--%@page import="com.newgen.wfdesktop.xmlapi.*,com.newgen.wfdesktop.session.*"%-->
<%@page import="com.newgen.wfdesktop.xmlapi.*,java.io.*,java.util.*"%>
<jsp:useBean id="customSession" class="com.newgen.custom.wfdesktop.session.WFCustomSession" scope="session"/>
<!--jsp:useBean id="customSession" class="com.newgen.custom.wfdesktop.session.WFCustomSession" scope="session"/-->
<%@ page import="com.newgen.wfdesktop.xmlapi.*" %>
<%@ page import="com.newgen.wfdesktop.util.*" %>
<%@page  import="java.util.*"%>
<%@page  import="java.text.SimpleDateFormat"%>
<%@page  import="java.util.List"%>
<%@page  import="java.util.ArrayList"%>
<%@ page import="java.io.IOException" %>
<%@ page import ="java.io.StringReader" %>
<%@page import="java.util.Date"%>
<%@page import="java.text.*"%>
<%@page import="com.newgen.niplj.io.RandomInputStreamSource"%>
<%@page import="com.newgen.ao.laps.calls.CustomerInformation"%>
<%@page import="com.newgen.omni.wf.util.excp.NGException"%>
<%@page import="java.lang.reflect.Constructor"%>
<%@page import="java.lang.reflect.InvocationTargetException"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="com.newgen.ao.laps.core.ICallExecutor"%>
<%@page import="com.newgen.ao.laps.util.*"%>
<%@page import="org.apache.log4j.PropertyConfigurator"%>
<%@page import ="org.apache.log4j.Logger"%>
<%@page import="java.util.*,java.text.*"%>
<%@ page import="com.newgen.omni.wf.util.app.*"%>
<%@page import="com.newgen.ao.laps.calls.CustomerInformation"%>
<%@page import="com.newgen.ao.laps.core.ICallExecutor"%>
<%@page import="com.newgen.ao.laps.util.*"%>
<%@page import="org.apache.log4j.PropertyConfigurator"%>
<%@page import ="org.apache.log4j.Logger"%>
<%@page import="org.apache.log4j.PropertyConfigurator"%>
<%@page import ="org.apache.log4j.Logger"%>



<%
initializeLogger();
String sWIName = request.getParameter("WI_NAME");
String sCallName = request.getParameter("CALL_NAME");
log.info(" ***************ap ProcParams input "+sWIName+sCallName);
String sCabname = customSession.getEngineName();
String sSessionId = customSession.getDMSSessionId();
String sUserName = customSession.getUserName();
String sJtsIp =  customSession.getJtsIp();
String iJtsPort = String.valueOf(customSession.getJtsPort()); 
/*String sCabname = "wmsupguat";
String sSessionId = "-1361447635";
String sJtsIp = "127.0.0.1";
String iJtsPort =  "9810";
String sUserName = "sahil";*/

log.info("sCabname : "+sCabname);
log.info("sSessionId : "+sSessionId);
log.info("sUserName : "+sUserName);
log.info("sJtsIp : "+sJtsIp);
log.info("iJtsPort : "+iJtsPort);

String sQuery="";
String sInput="";
String sOutput="";
String Server="";
String ServerIP="";
String ServerPort="";
String sRes;
final LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
Server = "WebSphere";
ServerIP = "127.0.0.1";
ServerPort = "9810"; 

try
{
	
	LapsConfigurations.getInstance().loadConfiguration();
	
	sQuery="SELECT COUNT(1) AS COUNT_WI FROM PDBCONNECTION WHERE RANDOMNUMBER = '"+sSessionId+"'";
	log.info("sQuery : "+sQuery);
	sInput=prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
	log.info("sInput : "+sInput);
	sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
	log.info("sOutput : "+sOutput);
	String sCount=sOutput.substring(sOutput.indexOf("<COUNT_WI>")+10,sOutput.indexOf("</COUNT_WI>"));
	log.info("sCount : "+sCount);
	if(sCount.equalsIgnoreCase("0"))
	{
		out.println("Session Timeout");
	}else
	{	

		if(!sWIName.equals("")){
			if(!sCallName.equals("")){
					try {
						log.info("INSIDE TRY");
						sRes = executeLapsCalls(sCallName,sWIName,map, sSessionId, sCabname, Server, ServerIP, ServerPort,sJtsIp,iJtsPort);
						log.info("sRes : "+sRes);
						out.println(sRes);
						
						//out.println("0#SUCCESS");
					} catch (Exception e) {
						//out.println("1#EXCEPTION RAISED");
						log.info("Exception : "+e.toString());
						out.println("#FAILURE");
					}				
			}
		}else{
			out.println("Session Timeout");
		}
	}
}catch(Exception e){	
}
%>

<%!
public void initializeLogger(){
			try{
				Properties properties = new Properties();
				String log4JPropertyFile =  System.getProperty("user.dir") +  System.getProperty("file.separator") + "NGConfig" + System.getProperty("file.separator") + "AO" +  System.getProperty("file.separator") +  "log4jJSP.properties";
				properties.load(new FileInputStream(log4JPropertyFile));
				PropertyConfigurator.configure(properties);
			}catch(Exception e){
				e.printStackTrace();
			}
	} 
		
	Logger log = Logger.getLogger(_LAPSRetryExecutor.class);
private String prepareAPSelectInputXml(String sQuery, String sCabname, String sSessionId){	
	return "<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+sQuery+"</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
}

private String prepareAPUpdateInputXml(String tableName,String colname,String sValues,String whrcls, String sCabname, String sSessionId){	
	return "<?xml version=\"1.0\"?><APUpdate_Input><Option>APUpdate</Option><TableName>"+tableName+"</TableName><ColName>"+colname+"</ColName><Values>"+sValues+"</Values><WhereClause>"+whrcls+"</WhereClause><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APUpdate_Input>";
}


private String executeLapsCalls(String sCallName, String sWIName, LinkedHashMap<Integer, String> defaultAttributeMap,
								String sessionId, String engineName, String Server, String ServerIP, String ServerPort, String jtsIP, String jtsPort){
	String sResponse="aa";
	String sInput="";
	String responseXML="";
	Constructor c;
	ICallExecutor ob;
	try{
		log.info("inside executeLapsCalls");
		c = Class.forName("com.newgen.ao.laps.calls."+sCallName).getConstructor(LinkedHashMap.class, String.class, String.class,String.class,String.class);
		log.info("c :"+c);
		ob= (ICallExecutor) c.newInstance(defaultAttributeMap,sessionId,sWIName,engineName,"-1");
		log.info("ob :"+ob);
		responseXML = ob.executeCall(defaultAttributeMap);
		log.info("responseXML :"+responseXML);
		String callStatus=responseXML.substring(responseXML.indexOf("<CallStatus>")+12,responseXML.indexOf("</CallStatus>"));
		log.info("callStatus :"+callStatus);
		String response=responseXML.substring(responseXML.indexOf("<CallResponse>")+14,responseXML.indexOf("</CallResponse>"));
		log.info("response :"+response);

		if(callStatus.equalsIgnoreCase("Y"))
		{			
			String returnCode = responseXML.substring(responseXML.indexOf("<returnCode>")+12,responseXML.indexOf("</returnCode>"));
			log.info("returnCode :"+returnCode);
			String errorDescription =responseXML.substring(responseXML.indexOf("<errorDescription>")+18,responseXML.indexOf("</errorDescription>"));		
			log.info("errorDescription :"+errorDescription);
			String status = responseXML.substring(responseXML.indexOf("<Status>")+8,responseXML.indexOf("</Status>"));
			log.info("status :"+status);
			sResponse="0#"+status;
			log.info("sResponse :"+sResponse);
			sInput=prepareAPUpdateInputXml("USR_0_LAPS_CALL_OUT","CALL_STATUS,RETURN_CODE,ERROR_DESCRIPTION,STATUS", "'"+ callStatus +"','"+ returnCode +"','"+ errorDescription +"','"+status+"'","WI_NAME='"+sWIName+"' and CALL_NAME='"+sCallName+"' and CALL_STATUS='N'",engineName,sessionId);
			log.info("sInput :"+sInput);
			String sOutput=NGEjbClient.getSharedInstance().makeCall(jtsIP,jtsPort,"WebSphere",sInput);
			log.info("sOutput :"+sOutput);			
		}
		else
		{
			sResponse="-1#FAILURE || "+response;
			log.info("sResponse :"+sResponse);
		} 
		
	}catch(Exception e){
		sResponse = "#FAILURE"+e.toString();
		log.info("sResponse :"+sResponse);
	}
	return sResponse;
}

%>