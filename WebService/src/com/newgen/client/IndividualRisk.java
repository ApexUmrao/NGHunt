package com.newgen.client;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.stub.Ikyc_package_V3_0WebServiceStub;
import com.newgen.stub.Ikyc_package_V3_0WebServiceStub.Entities;
import com.newgen.stub.Ikyc_package_V3_0WebServiceStub.ExecuteRuleset;
import com.newgen.stub.Ikyc_package_V3_0WebServiceStub.ExecuteRulesetResponse;
import com.newgen.stub.Ikyc_package_V3_0WebServiceStub.Ikyc_package_V3_0_Req;
import com.newgen.stub.Ikyc_package_V3_0WebServiceStub.Ikyc_package_V3_0_Resp;

public class IndividualRisk extends WebServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String sOrg_Output=null;//Added By Harish For inserting original mssg
	/**
	 * Function written to calculate Individual customer risk
	 * @author gupta.ashish
	 * @param sInputXML
	 * @return
	 */

		public String IndividualRiskCalculate(String sInputXML) 
		{
			LogGEN.writeTrace("Log", "Fuction called IndividualRiskCalculate");
			LogGEN.writeTrace("Log", "sInputXML---"+sInputXML);
			XMLParser xmlDataParser = new XMLParser();
			xmlDataParser.setInputXML(sInputXML);
			
			
			
			WebServiceHandler sHandler= new WebServiceHandler();
			try
			{
				/*VersionStub ver=new VersionStub();
				GetVersionResponse r =ver.getVersion();
				System.out.println(r.get_return());*/
				
				sHandler.readCabProperty("Individual_Risk");
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
				
				String sArmsDealer= xmlDataParser.getValueOf("arms_dealer_question");
				String sDealsInWMD= xmlDataParser.getValueOf("deals_in_wmd_question");
				String sSalaryTransfer= xmlDataParser.getValueOf("Salary_transfer");
				String sCentralBankBlacklist= xmlDataParser.getValueOf("central_Bank_Black_List");
				String sCountryBlacklist= xmlDataParser.getValueOf("country_Blacklist_Status");
				String sCountryResidence= xmlDataParser.getValueOf("country_of_residence");
				String sFinalEligibilityAnalysis= xmlDataParser.getValueOf("final_Eligibility_Analysi");
				String sHawala= xmlDataParser.getValueOf("hawala_question");
				String sRiskAssessment= xmlDataParser.getValueOf("ikYC_Risk_Assessment");
				String sInternalBlackList= xmlDataParser.getValueOf("internal_Black_List");
				String sScreenEligibility= xmlDataParser.getValueOf("name_Screening_Eligibilit");
				String sNationality= xmlDataParser.getValueOf("nationality");
				String sNationalityBlacklist= xmlDataParser.getValueOf("nationality_Blacklist_Sta");
				String sPep= xmlDataParser.getValueOf("pep_question");
				String sResidency= xmlDataParser.getValueOf("residency_Status");
				String sRiskFinal= xmlDataParser.getValueOf("risk_Category_Final");
				String sRiskInt1= xmlDataParser.getValueOf("risk_Categoy_Int1");
				String sRiskInt2= xmlDataParser.getValueOf("risk_Categoy_Int2");
				String sTaxEvasion= xmlDataParser.getValueOf("tax_evasion_question");
				String sWorldCheck= xmlDataParser.getValueOf("world_Check");
				String sEmpStatus= xmlDataParser.getValueOf("Employment_Status");
				String sBusinessNature= xmlDataParser.getValueOf("Business_nature");
				String sWorkingForUAEEntity= xmlDataParser.getValueOf("IS_UAE_ENTITY");
				String sWorkingForNonUAEEntity= xmlDataParser.getValueOf("IS_NON_UAE_ENTITY");
				
				LogGEN.writeTrace("Log", "All values Received");
				
				Ikyc_package_V3_0WebServiceStub iRiskStub = new Ikyc_package_V3_0WebServiceStub(sWSDLPath);
				ExecuteRuleset Exe_RuleSet= new ExecuteRuleset();
				ExecuteRulesetResponse Exe_RuleSet_Res;
				Ikyc_package_V3_0_Resp Ikyc_Individual_Res;
				Ikyc_package_V3_0_Req Ikyc_Individual_Req = new Ikyc_package_V3_0_Req();
				Entities Ikyc_Individual_Entity = new Entities();
								
				LogGEN.writeTrace("Log", "All Objects created");
				
				Ikyc_Individual_Entity.setArms_dealer_question(sArmsDealer);
				Ikyc_Individual_Entity.setDeals_in_wmd_question(sDealsInWMD);
				Ikyc_Individual_Entity.setSalary_transfer(sSalaryTransfer);
				Ikyc_Individual_Entity.setCentral_bank_black_list(sCentralBankBlacklist);
				Ikyc_Individual_Entity.setCountry_blacklist_status(sCountryBlacklist);
				Ikyc_Individual_Entity.setCountry_of_residence(sCountryResidence);
				Ikyc_Individual_Entity.setFinal_eligibility_analysi(sFinalEligibilityAnalysis);
				Ikyc_Individual_Entity.setHawala_question(sHawala);
				// Removed on 21_01_2021 as not used in rules
//				Ikyc_Individual_Entity.setIkYC_Risk_Assessment(sRiskAssessment);
				Ikyc_Individual_Entity.setInternal_black_list(sInternalBlackList);
				Ikyc_Individual_Entity.setName_screening_eligibilit(sScreenEligibility);
				Ikyc_Individual_Entity.setNationality(sNationality);
				Ikyc_Individual_Entity.setNationality_blacklist_sta(sNationalityBlacklist);
				Ikyc_Individual_Entity.setPep_question(sPep);
				Ikyc_Individual_Entity.setResidency_status(sResidency);
				Ikyc_Individual_Entity.setRisk_category_final(sRiskFinal);
				Ikyc_Individual_Entity.setRisk_categoy_int1(sRiskInt1);
				Ikyc_Individual_Entity.setRisk_categoy_int2(sRiskInt2);
				Ikyc_Individual_Entity.setTax_evasion_question(sTaxEvasion);
				Ikyc_Individual_Entity.setWorld_check(sWorldCheck);
				Ikyc_Individual_Entity.setEmployment_status(sEmpStatus);
				Ikyc_Individual_Entity.setNonuaegovtentitystatus(sWorkingForNonUAEEntity);
				Ikyc_Individual_Entity.setUae_govt_entity_status(sWorkingForUAEEntity);
				Ikyc_Individual_Entity.setSpecial_business_nature(sBusinessNature);				
				
				Ikyc_Individual_Req.setCabinetName(sCabinet);
				Ikyc_Individual_Req.setUserName(sUser);
				Ikyc_Individual_Req.setPassword(sPassword);
				Ikyc_Individual_Req.setLoginReqd(sLoginReq);
				Ikyc_Individual_Req.setEntitiesobj(Ikyc_Individual_Entity);
				LogGEN.writeTrace("Log", "All values set");
				
				
				Exe_RuleSet.setArgs0(Ikyc_Individual_Req);
				iRiskStub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
				Exe_RuleSet_Res=iRiskStub.executeRuleset(Exe_RuleSet);
				Ikyc_Individual_Res = Exe_RuleSet_Res.get_return();
				Ikyc_Individual_Entity = Ikyc_Individual_Res.getEntitiesobj();
				LogGEN.writeTrace("Log", "Output received");
				sOrg_Output=iRiskStub.resIkycMsg;
				String sFinalEligibility = Ikyc_Individual_Entity.getRisk_category_final();
				LogGEN.writeTrace("Log", "sFinalEligibility..... "+sFinalEligibility);
				
				String sAllValues = sFinalEligibility;
				
				// Commented by Abhay on 22_01_2021
				// To be handled in BRMS
				/*
				if("PEP".equalsIgnoreCase(sFinalEligibility) && "UNITED ARAB EMIRATES".equalsIgnoreCase(sNationality)){
					sAllValues="UAE-PEP";
				}else if("PEP".equalsIgnoreCase(sFinalEligibility) && !"UNITED ARAB EMIRATES".equalsIgnoreCase(sNationality)){
					sAllValues="Non UAE-PEP";
				}
				*/
				
				String Status="";
				
					Status="Success";
				
				
				 sHandler.readCabProperty("JTS");	
				
					String dburl=(String)currentCabPropertyMap.get("DBURL");
					String dbuser=(String)currentCabPropertyMap.get("USER");
					String dbpass=(String)currentCabPropertyMap.get("PASS");
					
					
					String inputXml=sInputXML;
					LogGEN.writeTrace("Log", inputXml);
					String winame=xmlDataParser.getValueOf("WiName");
					String sessionID= xmlDataParser.getValueOf("SessionId");
					String call_type=xmlDataParser.getValueOf("Calltype");
					sCabinet=xmlDataParser.getValueOf("EngineName");
					
					dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					
					 String outputxml=sFinalEligibility;
					/* String Query="insert into usr_0_other_calls_web_log(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
						"and randomnumber='"+ sessionID +"'),'"+call_type+"','"+ inputXml.replaceAll("'", "''") +"',to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
						"'"+ outputxml.replaceAll("'", "''") +"',sysdate,'"+  Status + "')";*/
					 try
					 {
						 dbpass=AESEncryption.decrypt(dbpass);
					 }
					 catch(Exception e)
					 {
						 
					 }
					 DBConnection con=new DBConnection();
					 /**
					  * //Added By Harish For inserting original mssg
					  * Date			:	17 Mar 2015
					  * Description 	:	Replace execute with executeClob
					  */
						 String Query="insert into usr_0_other_calls_web_log(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
							"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
							"?,sysdate,'"+  Status + "')";
						 LogGEN.writeTrace("Log"," Add Cheque Query : finally :"+Query);
						 LogGEN.writeTrace("Log","sOrg_Output : finally :"+sOrg_Output);
						 try {
							 con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_Output.replaceAll("'", "''"));
						} catch (Exception e2) {
							// TODO: handle exception
							e2.getMessage();
						}
					 LogGEN.writeTrace("Log","Query : finally :"+Query);
					 LogGEN.writeTrace("Log","outputXML : finally :"+outputxml);
				
				System.out.println(sAllValues+" final value");
				return sAllValues;
			}
			catch(Exception e)
			{
				LogGEN.writeTrace("Log", "IndividualRisk Exception--- "+e);
				e.printStackTrace();
			}
			return "";
		}
}