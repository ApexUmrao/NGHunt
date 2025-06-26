package com.newgen.client;

import com.newgen.stub.Test1_V1_0WebServiceStub;
import com.newgen.stub.Test1_V1_0WebServiceStub.Entities;
import com.newgen.stub.Test1_V1_0WebServiceStub.ExecuteRuleset;
import com.newgen.stub.Test1_V1_0WebServiceStub.ExecuteRulesetResponse;
import com.newgen.stub.Test1_V1_0WebServiceStub.Test1_V1_0_Req;
import com.newgen.stub.Test1_V1_0WebServiceStub.Test1_V1_0_Resp;




public class TestWebService extends WebServiceHandler
{
	protected static String sWSDLPath="";
	protected static String sCabinet="";
	protected static String sUser="";
	protected static String sPassword="";
	protected static boolean sLoginReq=false;

	
	/**
	 * Function written to calculate application risk
	 * @author gupta.ashish
	 * @param sInputXML
	 * @return
	 */

		public static String Testing(String sInputXML) 
		{
			LogGEN.writeTrace("Log", "Fuction called ApplicationRiskCalculate");
			LogGEN.writeTrace("Log", "sInputXML---"+sInputXML);
			XMLParser xmlDataParser = new XMLParser();
			xmlDataParser.setInputXML(sInputXML);
			
			WebServiceHandler sHandler= new WebServiceHandler();
			try
			{
				sHandler.readCabProperty("Testing");
				
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
				
				String sWiName= xmlDataParser.getValueOf("WI_NAME");
				LogGEN.writeTrace("Log", "sWiName---"+sWiName);
				
				Test1_V1_0WebServiceStub Test = new Test1_V1_0WebServiceStub();
				ExecuteRuleset Exe_RuleSet = new ExecuteRuleset();
				ExecuteRulesetResponse Exe_RuleSet_Res;
				Test1_V1_0_Resp Test_res;
				Test1_V1_0_Req Test_Req=new Test1_V1_0_Req();
				Entities enti = new Entities();
				
				LogGEN.writeTrace("Log", "All Objects created");
				
				
				enti.setSno(10);
				enti.setValue("Raj");
				Test_Req.setCabinetName(sCabinet);
				Test_Req.setLoginReqd(sLoginReq);
				Test_Req.setPassword(sPassword);
				Test_Req.setUserName(sUser);
				Test_Req.setEntitiesobj(enti);
				LogGEN.writeTrace("Log", "All values set");
				
				Exe_RuleSet.setParam0(Test_Req);
				Exe_RuleSet_Res=Test.executeRuleset(Exe_RuleSet);
				Test_res = Exe_RuleSet_Res.get_return();
				enti = Test_res.getEntitiesobj();
				LogGEN.writeTrace("Log", "Output received");
				
				String Risk = enti.getValue();
				LogGEN.writeTrace("Log", "Risk..... "+Risk);			
				return Risk;
			}
			catch(Exception e)
			{
				LogGEN.writeTrace("Log", "TestWebServiec Exception--- "+e);
			}
			return "";
		}
}
