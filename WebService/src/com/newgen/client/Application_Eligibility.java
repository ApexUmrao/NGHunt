/**------------------------------------------------------------------------------------------------------

+"                   NEWGEN SOFTWARE TECHNOLOGIES LIMITED

Group                          : Application -Projects

Project/Product                : ADCB

Application                    : WMS_CR_MAY_2015

Module                         : Webservice

File Name                      : Application_Eligibility.java

Author                         : Harish Yadav

Date (DD/MM/YYYY)              : 01/07/2015

Description                    : 1.31. Nigerian National to be validated

---------------------------------------------------------------------------------------------------------------------------------------

							CHANGE HISTORY

---------------------------------------------------------------------------------------------------------------------------------------

Problem No/CR No   	Change Date   		Changed By    					Change Description

*/
package com.newgen.client;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.newgen.AESEncryption;
import com.newgen.stub.App_Eligibilty_V1_0WebServiceStub;
import com.newgen.stub.App_Eligibilty_V1_0WebServiceStub.App_Eligibilty_V1_0_Req;
import com.newgen.stub.App_Eligibilty_V1_0WebServiceStub.App_Eligibilty_V1_0_Resp;
import com.newgen.stub.App_Eligibilty_V1_0WebServiceStub.App_eligibility;
import com.newgen.stub.App_Eligibilty_V1_0WebServiceStub.ExecuteRuleset;
import com.newgen.stub.App_Eligibilty_V1_0WebServiceStub.ExecuteRulesetResponse;

public class Application_Eligibility extends WebServiceHandler{

	protected static String sWSDLPath="";
	protected static String sCabinet="";
	protected static String sUser="";
	protected static String sPassword="";
	protected static boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String sOrg_Output=null;
	/**
	 * Function written to calculate application eligibility For Nigerian Customer
	 * @author harish-yadav
	 * @param sInputXML
	 * @return
	 */
	public  String ApplicationEligibilityCalculate(String sInputXML) 
	{
		LogGEN.writeTrace("Log", "Fuction called ApplicationEligibilityCalculate");
		LogGEN.writeTrace("Log", "sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		WebServiceHandler sHandler= new WebServiceHandler();
		try {
			sHandler.readCabProperty("Application_Eligibility");
			Date d= new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String sDate = dateFormat.format(d);
			sWSDLPath=(String)currentCabPropertyMap.get("WSDL_PATH");
			sCabinet=(String)currentCabPropertyMap.get("CABINET");
			sUser=(String)currentCabPropertyMap.get("USER");
			sLoginReq=Boolean.valueOf((String)currentCabPropertyMap.get("LOGIN_REQ"));
			sPassword=(String)currentCabPropertyMap.get("PASSWORD");
			lTimeOut=Long.valueOf((String)currentCabPropertyMap.get("TIMEOUT_INTERVAL"));			
			LogGEN.writeTrace("Log", "read Property successfully");
			LogGEN.writeTrace("Log", "WSDL PATH---- "+(String)currentCabPropertyMap.get("WSDL_PATH"));
			LogGEN.writeTrace("Log", "CABINET---- "+(String)currentCabPropertyMap.get("CABINET"));
			LogGEN.writeTrace("Log", "USER---- "+(String)currentCabPropertyMap.get("USER"));
			LogGEN.writeTrace("Log", "PASSWORD---- "+(String)currentCabPropertyMap.get("PASSWORD"));
			LogGEN.writeTrace("Log", "LOGIN_REQ---- "+(String)currentCabPropertyMap.get("LOGIN_REQ"));
			LogGEN.writeTrace("Log", "TIME_OUT---- "+(String)currentCabPropertyMap.get("TIMEOUT_INTERVAL"));
						
			String sResidentCountry=xmlDataParser.getValueOf("resident_Country");
			String sEmpStatus=xmlDataParser.getValueOf("empStatus");
			String sNationality=xmlDataParser.getValueOf("nationality");
			String sSalary=xmlDataParser.getValueOf("salary");
			String sTmlValue=xmlDataParser.getValueOf("tml_Nontml");
			LogGEN.writeTrace("Log", "All values Received");
			
			App_Eligibilty_V1_0WebServiceStub App_Stub= new App_Eligibilty_V1_0WebServiceStub(sWSDLPath);			
			ExecuteRuleset Exe_RuleSet= new ExecuteRuleset();
			ExecuteRulesetResponse Exe_RuleSet_Res;		
			App_Eligibilty_V1_0_Req aEligibilty_Req = new App_Eligibilty_V1_0_Req();
			App_Eligibilty_V1_0_Resp aEligibilty_Res;
			App_eligibility app_Eligibility_Entity = new App_eligibility();
			LogGEN.writeTrace("Log", "All Objects created");
			
			app_Eligibility_Entity.setAe_nationality(sNationality);
			app_Eligibility_Entity.setEmpstatus(sEmpStatus);
			app_Eligibility_Entity.setResident_country(sResidentCountry);
			app_Eligibility_Entity.setSalary(Long.parseLong(sSalary));
			app_Eligibility_Entity.setTml_nontml(sTmlValue);
			app_Eligibility_Entity.setAe_country_of_residence("");
			app_Eligibility_Entity.setEmpstatus("");
			app_Eligibility_Entity.setResult("");
			app_Eligibility_Entity.setSalariedstatus("");
	
			aEligibilty_Req.setCabinetName(sCabinet);
			aEligibilty_Req.setLoginReqd(sLoginReq);
			aEligibilty_Req.setUserName(sUser);
			aEligibilty_Req.setPassword(sPassword);
			aEligibilty_Req.setApp_eligibilityobj(app_Eligibility_Entity);
			LogGEN.writeTrace("Log", "All Values set");
			
			Exe_RuleSet.setArgs0(aEligibilty_Req);
			App_Stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			LogGEN.writeTrace("Log", "bwefore Executed");
			
			LogGEN.writeTrace("Log", "Before Writing XML");
			LogGEN.writeTrace("Log", "input"+ App_Stub.getinputXml(Exe_RuleSet));
			
			Exe_RuleSet_Res=App_Stub.executeRuleset(Exe_RuleSet);
			LogGEN.writeTrace("Log", "Executed");
			aEligibilty_Res = Exe_RuleSet_Res.get_return();
			app_Eligibility_Entity=aEligibilty_Res.getApp_eligibilityobj();
			LogGEN.writeTrace("Log", "Output received");
			
			String inputXml=App_Stub.getinputXml(Exe_RuleSet);
			sOrg_Output = App_Stub.resDCDtlMsg;
						
			String sFinalAppEligibility=app_Eligibility_Entity.getResult();
			LogGEN.writeTrace("Log", "sFinalAppEligibility..... "+sFinalAppEligibility);
			
			String Status="Success";
			sHandler.readCabProperty("JTS");
			String dburl=(String)currentCabPropertyMap.get("DBURL");
			String dbuser=(String)currentCabPropertyMap.get("USER");
			String dbpass=(String)currentCabPropertyMap.get("PASS");			
			
			String winame=xmlDataParser.getValueOf("WiName");
			String sessionID= xmlDataParser.getValueOf("SessionId");
			String call_type=xmlDataParser.getValueOf("Calltype");
			sCabinet=xmlDataParser.getValueOf("EngineName");			
			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			 try
			 {
				 dbpass=AESEncryption.decrypt(dbpass);
			 }
			 catch(Exception e)
			 {
				 e.getMessage();
			 }
			 DBConnection con=new DBConnection();
			 String Query="insert into usr_0_other_calls_web_log(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
				"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
				"?,sysdate,'"+  Status + "')";
			 
			 try 
			 {
				 con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_Output.replaceAll("'", "''"));
			 }
			 catch (Exception e2) 
			 {
				e2.getMessage();
			 }
			return sFinalAppEligibility;
		} 
		catch (Exception e) 
		{
			e.getMessage();
		}
		return "";
	}
}
