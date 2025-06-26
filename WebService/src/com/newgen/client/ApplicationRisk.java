package com.newgen.client;

import com.newgen.stub.ADCBAppRule_V1_0WebServiceStub;


public class ApplicationRisk extends WebServiceHandler
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

		public static String ApplicationRiskCalculate(String sInputXML) 
		{
			LogGEN.writeTrace("Log", "Fuction called ApplicationRiskCalculate");
			LogGEN.writeTrace("Log", "sInputXML---"+sInputXML);
			XMLParser xmlDataParser = new XMLParser();
			xmlDataParser.setInputXML(sInputXML);
			
			WebServiceHandler sHandler= new WebServiceHandler();
			try
			{
				sHandler.readCabProperty("Application_Risk");
				
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
				
				ADCBAppRule_V1_0WebServiceStub App_Risk=new ADCBAppRule_V1_0WebServiceStub(sWSDLPath);
				ADCBAppRule_V1_0WebServiceStub.ExecuteRuleset Exe_RuleSet= new ADCBAppRule_V1_0WebServiceStub.ExecuteRuleset();
				ADCBAppRule_V1_0WebServiceStub.ExecuteRulesetResponse Exe_RuleSet_Res;
				ADCBAppRule_V1_0WebServiceStub.ADCBAppRule_V1_0_Resp App_Risk_res;
				ADCBAppRule_V1_0WebServiceStub.ADCBAppRule_V1_0_Req App_Risk_Req=new ADCBAppRule_V1_0WebServiceStub.ADCBAppRule_V1_0_Req();
				ADCBAppRule_V1_0WebServiceStub.Input Risk_input= new ADCBAppRule_V1_0WebServiceStub.Input();
				ADCBAppRule_V1_0WebServiceStub.Output Risk_Output = new ADCBAppRule_V1_0WebServiceStub.Output();
				LogGEN.writeTrace("Log", "All Objects created");
				
				Risk_input.setWi_Name(sWiName);
				Risk_Output.setRisk("Neutral");
				
				App_Risk_Req.setCabinetName(sCabinet);
				App_Risk_Req.setUserName(sUser);
				App_Risk_Req.setPassword(sPassword);
				App_Risk_Req.setInputobj(Risk_input);
				App_Risk_Req.setOutputobj(Risk_Output);
				App_Risk_Req.setLoginReqd(sLoginReq);
				
				LogGEN.writeTrace("Log", "All values set");
				
				Exe_RuleSet.setParam0(App_Risk_Req);
				Exe_RuleSet_Res=App_Risk.executeRuleset(Exe_RuleSet);
				App_Risk_res = Exe_RuleSet_Res.get_return();
				Risk_Output = App_Risk_res.getOutputobj();
				LogGEN.writeTrace("Log", "Output received");
				
				String Risk = Risk_Output.getRisk();
				LogGEN.writeTrace("Log", "Risk..... "+Risk);			
				return Risk;
			}
			catch(Exception e)
			{
				LogGEN.writeTrace("Log", "IndividualRisk Exception--- "+e);
			}
			return "";
		}
}
