package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.Ikyc_Individual_V1_0WebServiceStub;
import com.newgen.dscop.stub.Ikyc_Individual_V1_0WebServiceStub.Entities;
import com.newgen.dscop.stub.Ikyc_Individual_V1_0WebServiceStub.ExecuteRuleset;
import com.newgen.dscop.stub.Ikyc_Individual_V1_0WebServiceStub.ExecuteRulesetResponse;
import com.newgen.dscop.stub.Ikyc_Individual_V1_0WebServiceStub.Ikyc_Individual_V1_0_Req;
import com.newgen.dscop.stub.Ikyc_Individual_V1_0WebServiceStub.Ikyc_Individual_V1_0_Resp;

public class IndividualRisk extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String sOrg_Output=null;

	public String IndividualRiskCalculate(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Fuction called IndividualRiskCalculate");
		LogGEN.writeTrace("CBG_Log", "sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);

		try
		{
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("IndividualRisk");
			LogGEN.writeTrace("CBG_Log", "IndividualRisk WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "IndividualRisk WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "IndividualRisk CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "IndividualRisk USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "IndividualRisk PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "IndividualRisk LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "IndividualRisk TIME_OUT: "+lTimeOut);

			Date d= new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String sDate = dateFormat.format(d);

			String sCountryResidence= xmlDataParser.getValueOf("country_of_residence");
			String sArmsDealer= xmlDataParser.getValueOf("arms_dealer_question");
			String sDealsInWMD= xmlDataParser.getValueOf("deals_in_wmd_question");
			String sSalaryTransfer= xmlDataParser.getValueOf("Salary_transfer");
			String sHawala= xmlDataParser.getValueOf("hawala_question");
			String sNationality= xmlDataParser.getValueOf("nationality");
			String sPep= xmlDataParser.getValueOf("pep_question");
			String sTaxEvasion= xmlDataParser.getValueOf("tax_evasion_question");
			String sEmpStatus= xmlDataParser.getValueOf("Employment_Status");
			String sBusinessNature= xmlDataParser.getValueOf("Business_nature");
			String sWorkingForUAEEntity= xmlDataParser.getValueOf("IS_UAE_ENTITY");
			String sWorkingForNonUAEEntity= xmlDataParser.getValueOf("IS_NON_UAE_ENTITY");

			LogGEN.writeTrace("CBG_Log", "All values Received");

			Ikyc_Individual_V1_0WebServiceStub iRiskStub = new Ikyc_Individual_V1_0WebServiceStub(sWSDLPath);
			ExecuteRuleset Exe_RuleSet= new ExecuteRuleset();
			Ikyc_Individual_V1_0_Req Ikyc_Individual_Req = new Ikyc_Individual_V1_0_Req();
			Entities Ikyc_Individual_Entity = new Entities();

			LogGEN.writeTrace("CBG_Log", "All Objects created");

			Ikyc_Individual_Entity.setArms_dealer_question(sArmsDealer);
			Ikyc_Individual_Entity.setDeals_in_wmd_question(sDealsInWMD);
			Ikyc_Individual_Entity.setSalary_transfer(sSalaryTransfer);
			Ikyc_Individual_Entity.setCountry_of_residence(sCountryResidence);
			Ikyc_Individual_Entity.setHawala_question(sHawala);
			Ikyc_Individual_Entity.setNationality(sNationality);
			Ikyc_Individual_Entity.setPep_question(sPep);
			Ikyc_Individual_Entity.setTax_evasion_question(sTaxEvasion);
			Ikyc_Individual_Entity.setEmployment_status(sEmpStatus);
			Ikyc_Individual_Entity.setNonuaegovtentitystatus(sWorkingForNonUAEEntity);
			Ikyc_Individual_Entity.setUae_govt_entity_status(sWorkingForUAEEntity);
			Ikyc_Individual_Entity.setSpecial_business_nature(sBusinessNature);				

			Ikyc_Individual_Req.setCabinetName(sCabinet);
			Ikyc_Individual_Req.setUserName(sUser);
			Ikyc_Individual_Req.setPassword(sPassword);
			Ikyc_Individual_Req.setLoginReqd(sLoginReq);
			Ikyc_Individual_Req.setEntitiesobj(Ikyc_Individual_Entity);
			LogGEN.writeTrace("CBG_Log", "All values set");


			Exe_RuleSet.setArgs0(Ikyc_Individual_Req);
			iRiskStub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			LogGEN.writeTrace("CBG_Log", "Exe_RuleSet : "+Exe_RuleSet);
			LogGEN.writeTrace("CBG_Log", "iRiskStub : "+iRiskStub);
			ExecuteRulesetResponse Exe_RuleSet_Res=iRiskStub.executeRuleset(Exe_RuleSet);
			LogGEN.writeTrace("CBG_Log", "Exe_RuleSet_Res : "+Exe_RuleSet_Res);
			Ikyc_Individual_V1_0_Resp Ikyc_Individual_Res = Exe_RuleSet_Res.get_return();
			Ikyc_Individual_Entity = Ikyc_Individual_Res.getEntitiesobj();
			LogGEN.writeTrace("CBG_Log", "Output received");
			sOrg_Output=iRiskStub.resIkycMsg; 
			LogGEN.writeTrace("CBG_Log", "Output : "+sOrg_Output);
			String sFinalEligibility = Ikyc_Individual_Entity.getRisk_category_final();
			LogGEN.writeTrace("CBG_Log", "sFinalEligibility..... "+sFinalEligibility);

			String sAllValues = sFinalEligibility;

			String Status="Success";
			String dburl=(String)currentCabPropertyMap.get("DBURL");
			String dbuser=(String)currentCabPropertyMap.get("USER");
			String dbpass=(String)currentCabPropertyMap.get("PASS");


			String inputXml=sInputXML;
			LogGEN.writeTrace("CBG_Log", inputXml);
			String winame=xmlDataParser.getValueOf("WiName");
			String sessionID= xmlDataParser.getValueOf("SessionId");
			String call_type=xmlDataParser.getValueOf("DSCOPCallType");
			sCabinet=xmlDataParser.getValueOf("EngineName");

			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

			String outputxml=sFinalEligibility;
			try
			{
				dbpass=AESEncryption.decrypt(dbpass);
			}
			catch(Exception e)
			{

			}
			DBConnection con=new DBConnection();
			String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
			LogGEN.writeTrace("CBG_Log"," Add Cheque Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","sOrg_Output : finally :"+sOrg_Output);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_Output.replaceAll("'", "''"));
			} catch (Exception e2) {
				// TODO: handle exception
				e2.getMessage();
			}
			LogGEN.writeTrace("CBG_Log","Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","outputXML : finally :"+outputxml);

			System.out.println(sAllValues+" final value");
			return sAllValues;
		}
		catch(Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "IndividualRisk Exception--- "+e);
			e.printStackTrace();
		}
		return "";
	}
}
