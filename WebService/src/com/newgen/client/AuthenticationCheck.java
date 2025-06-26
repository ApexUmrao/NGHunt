package com.newgen.client;

import com.newgen.stub.Blacklist_Check_V1_0WebServiceStub;
import com.newgen.stub.Blacklist_Check_V1_0WebServiceStub.Blacklist_Check_V1_0_Req;
import com.newgen.stub.Blacklist_Check_V1_0WebServiceStub.Blacklist_Check_V1_0_Resp;
import com.newgen.stub.Blacklist_Check_V1_0WebServiceStub.ExecuteRuleset;
import com.newgen.stub.Blacklist_Check_V1_0WebServiceStub.ExecuteRulesetResponse;
import com.newgen.stub.Blacklist_Check_V1_0WebServiceStub.Input;
import com.newgen.stub.Blacklist_Check_V1_0WebServiceStub.Output;
import com.newgen.client.WebServiceHandler;

public class AuthenticationCheck extends WebServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;

	
	/**
	 * Function written to calculate final eligibility
	 * @author gupta.ashish
	 * @param sInputXML
	 * @return
	 */

		public String EligibilityCalculate(String sInputXML) 
		{
			LogGEN.writeTrace("Log", "Fuction called EligibilityCalculate");
			LogGEN.writeTrace("Log", "sInputXML---"+sInputXML);
			XMLParser xmlDataParser = new XMLParser();
			xmlDataParser.setInputXML(sInputXML);
			
			WebServiceHandler sHandler= new WebServiceHandler();
			try
			{
				sHandler.readCabProperty("Authentication");
				
				sWSDLPath=(String)currentCabPropertyMap.get("WSDL_PATH");
				sCabinet=(String)currentCabPropertyMap.get("CABINET");
				sUser=(String)currentCabPropertyMap.get("USER");
				sLoginReq=Boolean.valueOf((String)currentCabPropertyMap.get("LOGIN_REQ"));
				sPassword=(String)currentCabPropertyMap.get("PASSWORD");
				
				LogGEN.writeTrace("Log", "read Property successfully");
				LogGEN.writeTrace("Log", "WSDL PATH---- "+(String)currentCabPropertyMap.get("WSDL_PATH"));
				LogGEN.writeTrace("Log", "CABINET---- "+(String)currentCabPropertyMap.get("CABINET"));
				LogGEN.writeTrace("Log", "USER---- "+(String)currentCabPropertyMap.get("USER"));
				LogGEN.writeTrace("Log", "PASSWORD---- "+(String)currentCabPropertyMap.get("PASSWORD"));
				LogGEN.writeTrace("Log", "LOGIN_REQ---- "+(String)currentCabPropertyMap.get("LOGIN_REQ"));
				
				String sBlackListRisk= xmlDataParser.getValueOf("BLACKLIST_RISK");
				String sCentralBankRisk= xmlDataParser.getValueOf("CENTRALBANK_RISK");
				String sWorldCheckRisk= xmlDataParser.getValueOf("WORLDCHECK_RISK");
				LogGEN.writeTrace("Log", "sBlackListRisk---"+sBlackListRisk);
				LogGEN.writeTrace("Log", "sCentralBankRisk---"+sCentralBankRisk);
				LogGEN.writeTrace("Log", "sWorldCheckRisk---"+sWorldCheckRisk);
				
				Blacklist_Check_V1_0WebServiceStub Auth_Risk=new Blacklist_Check_V1_0WebServiceStub(sWSDLPath);
				ExecuteRuleset Exe_RuleSet= new ExecuteRuleset();
				ExecuteRulesetResponse Exe_RuleSet_Res;
				Blacklist_Check_V1_0_Resp Auth_Risk_res;
				Blacklist_Check_V1_0_Req Auth_Risk_Req=new Blacklist_Check_V1_0_Req();
				Input Auth_input= new Input();
				Output Auth_Output = new Output();
				LogGEN.writeTrace("Log", "All Objects created");
				
				Auth_input.setInternal(sBlackListRisk);
				Auth_input.setCentral(sCentralBankRisk);
				Auth_input.setWorld(sWorldCheckRisk);
				Auth_Output.setEligiblity("Eligible");
				
				Auth_Risk_Req.setCabinetName(sCabinet);
				Auth_Risk_Req.setUserName(sUser);
				Auth_Risk_Req.setPassword(sPassword);
				Auth_Risk_Req.setInputobj(Auth_input);
				Auth_Risk_Req.setOutputobj(Auth_Output);
				Auth_Risk_Req.setLoginReqd(sLoginReq);
				
				LogGEN.writeTrace("Log", "All values set");
				
				Exe_RuleSet.setParam0(Auth_Risk_Req);
				Exe_RuleSet_Res=Auth_Risk.executeRuleset(Exe_RuleSet);
				Auth_Risk_res = Exe_RuleSet_Res.get_return();
				Auth_Output = Auth_Risk_res.getOutputobj();
				LogGEN.writeTrace("Log", "Output received");
				
				String sEligibility = Auth_Output.getEligiblity();
				LogGEN.writeTrace("Log", "Eligibility..... "+sEligibility);			
				return sEligibility;
			}
			catch(Exception e)
			{
				LogGEN.writeTrace("Log", "EligibilityCalculate Exception--- "+e);
			}
			return "";
		}
}

