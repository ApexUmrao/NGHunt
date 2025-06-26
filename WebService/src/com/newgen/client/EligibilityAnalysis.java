package com.newgen.client;


import java.text.SimpleDateFormat;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.client.WebServiceHandler;
import com.newgen.stub.Eligibility_Analysis_V1_0WebServiceStub;
import com.newgen.stub.Eligibility_Analysis_V1_0WebServiceStub.Entities;
import com.newgen.stub.Eligibility_Analysis_V1_0WebServiceStub.ExecuteRuleset;
import com.newgen.stub.Eligibility_Analysis_V1_0WebServiceStub.ExecuteRulesetResponse;
import com.newgen.stub.Eligibility_Analysis_V1_0WebServiceStub.Eligibility_Analysis_V1_0_Req;
import com.newgen.stub.Eligibility_Analysis_V1_0WebServiceStub.Eligibility_Analysis_V1_0_Resp;


public class EligibilityAnalysis extends WebServiceHandler
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

		public String EligibilityAnalysisCalculate(String sInputXML) 
		{
			LogGEN.writeTrace("Log", "Fuction called EligibilityAnalysis");
			LogGEN.writeTrace("Log", "sInputXML---"+sInputXML);
			XMLParser xmlDataParser = new XMLParser();
			xmlDataParser.setInputXML(sInputXML);
						
			WebServiceHandler sHandler= new WebServiceHandler();
			try
			{
				sHandler.readCabProperty("Eligibility_Analysis");			
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
				
				LogGEN.writeTrace("Log", "All values Received");
				try
				{
				Eligibility_Analysis_V1_0WebServiceStub iRiskStub = new Eligibility_Analysis_V1_0WebServiceStub(sWSDLPath);
				ExecuteRuleset Exe_RuleSet= new ExecuteRuleset();
				ExecuteRulesetResponse Exe_RuleSet_Res;
				Eligibility_Analysis_V1_0_Resp IKYC_Res;
				Eligibility_Analysis_V1_0_Req IKYC_Req = new Eligibility_Analysis_V1_0_Req();
				Entities IKYC_Entity = new Entities();
								
				LogGEN.writeTrace("Log", "All Objects created");
				
				IKYC_Entity.setArms_dealer_question(sArmsDealer);
				IKYC_Entity.setCentral_bank_black_list(sCentralBankBlacklist);
				IKYC_Entity.setCountry_blacklist_status(sCountryBlacklist);
				IKYC_Entity.setCountry_of_residence(sCountryResidence);
				IKYC_Entity.setFinal_eligibility_analysi(sFinalEligibilityAnalysis);
				IKYC_Entity.setHawala_question(sHawala);
				// Removed on 21_01_2021 as not used in rules
				//IKYC_Entity.setIkYC_Risk_Assessment(sRiskAssessment);
				IKYC_Entity.setInternal_black_list(sInternalBlackList);
				IKYC_Entity.setName_screening_eligibilit(sScreenEligibility);
				IKYC_Entity.setNationality(sNationality);
				IKYC_Entity.setNationality_blacklist_sta(sNationalityBlacklist);
				IKYC_Entity.setPep_question(sPep);
				IKYC_Entity.setResidency_status(sResidency);
				IKYC_Entity.setRisk_category_final(sRiskFinal);
				IKYC_Entity.setRisk_categoy_int1(sRiskInt1);
				IKYC_Entity.setRisk_categoy_int2(sRiskInt2);
				IKYC_Entity.setTax_evasion_question(sTaxEvasion);
				IKYC_Entity.setWorld_check(sWorldCheck);
				IKYC_Entity.setNonuaegovtentitystatus("");
				IKYC_Entity.setSalaried_status("");
				IKYC_Entity.setSelf_employed_status("");
				IKYC_Entity.setSpecial_business_nature("");
				IKYC_Entity.setUae_govt_entity_status("");
				IKYC_Entity.setEmployment_status("fese");
				
				
				IKYC_Req.setCabinetName(sCabinet);
				IKYC_Req.setUserName(sUser);
				IKYC_Req.setPassword(sPassword);
				IKYC_Req.setLoginReqd(sLoginReq);
				IKYC_Req.setEntitiesobj(IKYC_Entity);
				LogGEN.writeTrace("Log", "All values set");
				
				Exe_RuleSet.setArgs0(IKYC_Req);
				//Exe_RuleSet.setParam0(IKYC_Req);
				LogGEN.writeTrace("Log", "Before Writing XML");
				LogGEN.writeTrace("Log", "input"+ iRiskStub.getinputXml(Exe_RuleSet));
				iRiskStub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
				
					Exe_RuleSet_Res=iRiskStub.executeRuleset(Exe_RuleSet);
				
					IKYC_Res = Exe_RuleSet_Res.get_return();
					IKYC_Entity = IKYC_Res.getEntitiesobj();
					LogGEN.writeTrace("Log", "Output received");
					
					String sFinalEligibility = IKYC_Entity.getFinal_eligibility_analysi();
					String sFinalNameScrenning = IKYC_Entity.getName_screening_eligibilit();
					
					LogGEN.writeTrace("Log", "sFinalEligibility..... "+sFinalEligibility);
					String sAllValues = sFinalEligibility+","+sFinalNameScrenning;
					String Status="";
					Status="Success";
				
				
				sHandler.readCabProperty("JTS");	
				
				
				String dburl=(String)currentCabPropertyMap.get("DBURL");
				String dbuser=(String)currentCabPropertyMap.get("USER");
				String dbpass=(String)currentCabPropertyMap.get("PASS");
				
				
				String inputXml=sInputXML;
				sOrg_Output=iRiskStub.resEligibilityMsg;
				LogGEN.writeTrace("Log", inputXml);
				String winame=xmlDataParser.getValueOf("WiName");
				String sessionID= xmlDataParser.getValueOf("SessionId");
				String call_type=xmlDataParser.getValueOf("Calltype");
				sCabinet=xmlDataParser.getValueOf("EngineName");
				
				dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				//String sDate1 = dateFormat.format(d1);
				// String outputxml=sAllValues;
				/* String Query="insert into usr_0_other_calls_web_log(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type+"','"+ inputXml.replaceAll("'", "''") +"',to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"'"+ outputxml.replaceAll("'", "''") +"',sysdate,'"+  Status + "')";*/
				 
				
				 try
				 {
					 dbpass=AESEncryption.decrypt(dbpass);
				 }
				 catch(Exception e)
				 {
					 LogGEN.writeTrace("Log", "PASSWORD Exception:"+e.getMessage());
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
				 LogGEN.writeTrace("Log","outputXML : finally :"+sOrg_Output);
				
				System.out.println(sAllValues+"sAllValues");
				return sAllValues;
				}
				catch(Exception eee)
				{
					LogGEN.writeTrace("Log", "exception"+ eee.getMessage());
				}
			}
			catch(Exception e)
			{
				LogGEN.writeTrace("Log", "Eligibility Analysis Exception--- "+e);
				e.printStackTrace();
			}
			return "";
		}
}
