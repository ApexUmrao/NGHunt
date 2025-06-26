package com.newgen.client;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.stub.ProductEligibility_V1_0WebServiceStub;
import com.newgen.stub.ProductEligibility_V1_0WebServiceStub.ExecuteRuleset;
import com.newgen.stub.ProductEligibility_V1_0WebServiceStub.ExecuteRulesetResponse;
import com.newgen.stub.ProductEligibility_V1_0WebServiceStub.Prod_entities;
import com.newgen.stub.ProductEligibility_V1_0WebServiceStub.ProductEligibility_V1_0_Req;
import com.newgen.stub.ProductEligibility_V1_0WebServiceStub.ProductEligibility_V1_0_Resp;


public class ProductEligibility extends WebServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String sOrg_output_Prd_eligi=null;//Added By Harish For inserting original mssg
	/**
	 * Function written to calculate Individual customer risk
	 * @author gupta.ashish
	 * @param sInputXML
	 * @return
	 */

		public String ProductEligibilityCalculate(String sInputXML) 
		{
			LogGEN.writeTrace("Log", "Fuction called ProductEligibilityCalculate");
			LogGEN.writeTrace("Log", "sInputXML---"+sInputXML);
			XMLParser xmlDataParser = new XMLParser();
			xmlDataParser.setInputXML(sInputXML);
			
			Date d= new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String sDate = dateFormat.format(d);
			String Status="";
			String retVal="";
			WebServiceHandler sHandler= new WebServiceHandler();
			try
			{
				sHandler.readCabProperty("Product_Eligibility");
				
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
				
				// start editing from here 
				
				String sAccClass= xmlDataParser.getValueOf("acc_classification");
				String sAccOwner= xmlDataParser.getValueOf("acc_ownership");
				String sCall= xmlDataParser.getValueOf("call");
				String sCallCurrency= xmlDataParser.getValueOf("call_currency");
				String sCasaAvailed= xmlDataParser.getValueOf("casa_availed");
				String sClassOutput= xmlDataParser.getValueOf("classification_output");
				String sCntrySpec= xmlDataParser.getValueOf("cntry_specific");
				String sCountryOfRes= xmlDataParser.getValueOf("country_of_res");
				String sNationality= xmlDataParser.getValueOf("nationality");
				String sCurrent= xmlDataParser.getValueOf("current");
				String sCurrentCurrency= xmlDataParser.getValueOf("current_currency");
				String sCustLiteracy= xmlDataParser.getValueOf("cust_literacy");
				String sEmpStatus= xmlDataParser.getValueOf("emp_status");
				String sEmpType= xmlDataParser.getValueOf("emp_type");
				String sMaxAge= xmlDataParser.getValueOf("max_age");
				String sMinAge= xmlDataParser.getValueOf("min_age");
				String sResStatus= xmlDataParser.getValueOf("res_status");
				String sSaving= xmlDataParser.getValueOf("savings");
				String sSavCurrency= xmlDataParser.getValueOf("savings_currency");
				String sSignType= xmlDataParser.getValueOf("sign_type");
				String sSubCategory= xmlDataParser.getValueOf("subcategory");
				String sTerm= xmlDataParser.getValueOf("term");
				String sTermCurrency= xmlDataParser.getValueOf("term_currency");
				String sTradeLicense= xmlDataParser.getValueOf("trade_license");
				int sCustAge= Integer.parseInt(xmlDataParser.getValueOf("cust_age"));
				
				LogGEN.writeTrace("Log", "All values Received");
				
				ProductEligibility_V1_0WebServiceStub iRiskStub = new ProductEligibility_V1_0WebServiceStub(sWSDLPath);
				ExecuteRuleset Exe_RuleSet= new ExecuteRuleset();
				ExecuteRulesetResponse  Exe_ruleSet_Rep = new ExecuteRulesetResponse();
				ProductEligibility_V1_0_Req PELIG_Req = new ProductEligibility_V1_0_Req();
				ProductEligibility_V1_0_Resp prod_Rep ;
				Prod_entities Prod_Entity = new Prod_entities();
				iRiskStub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
				LogGEN.writeTrace("Log", "All Objects created");
				
				Prod_Entity.setAcc_classification(sAccClass);
				Prod_Entity.setAcc_ownership(sAccOwner);
				Prod_Entity.setCall(sCall);
				Prod_Entity.setCall_currency(sCallCurrency);
				Prod_Entity.setCasa_availed(sCasaAvailed);
				Prod_Entity.setClassification_output(sClassOutput);
				Prod_Entity.setCntry_specific(sCntrySpec);
				Prod_Entity.setCountry_of_res(sCountryOfRes);
				Prod_Entity.setCurrent(sCurrent);
				Prod_Entity.setCurrent_currency(sCurrentCurrency);
				Prod_Entity.setCust_literacy(sCustLiteracy);
				Prod_Entity.setEmp_status(sEmpStatus);
				Prod_Entity.setEmp_type(sEmpType);
//				Prod_Entity.setMax_age(sMaxAge);
//				Prod_Entity.setMin_age(sMinAge);
				Prod_Entity.setRes_status(sResStatus);
				Prod_Entity.setSavings(sSaving);
				Prod_Entity.setSavings_currency(sSavCurrency);
				Prod_Entity.setSign_type(sSignType);
				Prod_Entity.setPe_nationality(sNationality);
				Prod_Entity.setSubcategory(sSubCategory);
				Prod_Entity.setTerm(sTerm);
				Prod_Entity.setTerm_currency(sTermCurrency);
				Prod_Entity.setTrade_license(sTradeLicense);
				Prod_Entity.setCust_age(sCustAge);
				
				PELIG_Req.setCabinetName(sCabinet);
				PELIG_Req.setUserName(sUser);
				PELIG_Req.setPassword(sPassword);
				PELIG_Req.setLoginReqd(sLoginReq);
				PELIG_Req.setProd_entitiesobj(Prod_Entity);
				LogGEN.writeTrace("Log", "All values set");
				
				
				Exe_RuleSet.setArgs0(PELIG_Req);
				LogGEN.writeTrace("Log", "PELIG_Req : "+PELIG_Req);
				Exe_ruleSet_Rep=iRiskStub.executeRuleset(Exe_RuleSet);
				LogGEN.writeTrace("Log", "Exe_ruleSet_Rep : " + Exe_ruleSet_Rep);
				sOrg_output_Prd_eligi=iRiskStub.resPrd_Eilbly;//Added By Harish For inserting original mssg
				LogGEN.writeTrace("Log", "sOrg_output_Prd_eligi "+sOrg_output_Prd_eligi);
				prod_Rep = Exe_ruleSet_Rep.get_return();
				LogGEN.writeTrace("Log", "prod_Rep :"+prod_Rep);
				Prod_Entity =prod_Rep.getProd_entitiesobj();
				LogGEN.writeTrace("Log", "Prod_Entity :"+Prod_Entity);
				
				retVal="<?xml version=\"1.0\" encoding=\"utf-8\"?>";
				retVal+="<Output>";
				retVal+="<classification_output>"+Prod_Entity.getClassification_output()+"</classification_output>";
				retVal+="<current>"+Prod_Entity.getCurrent()+"</current>";
				retVal+="<savings>"+Prod_Entity.getSavings()+"</savings>";
				retVal+="<call>"+Prod_Entity.getCall()+"</call>";
				retVal+="<term>"+Prod_Entity.getTerm()+"</term>";
				retVal+="<current_currency>"+Prod_Entity.getCurrent_currency()+"</current_currency>";
				retVal+="<subcategory>"+Prod_Entity.getSubcategory()+"</subcategory>";
				retVal+="</Output>";
				
				Status="Success";
				return retVal;
			}
			catch(Exception e)
			{
				LogGEN.writeTrace("Log", "Eligibility Analysis Exception--- "+e);
				Status="Error";
				e.printStackTrace();
			}
			finally
			{
				 try {
					sHandler.readCabProperty("JTS");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
				 
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
					 String outputxml=retVal;
					// String Query="insert into usr_0_other_calls_web_log(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					//	"and randomnumber='"+ sessionID +"'),'"+call_type+"','"+ inputXml.replaceAll("'", "''") +"',to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					//	"'"+ outputxml.replaceAll("'", "''") +"',sysdate,'"+  Status + "')";
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
					 LogGEN.writeTrace("Log","ADD CASA Account Query : finally :"+Query);
					 LogGEN.writeTrace("Log","sOrg_Output : finally :"+sOrg_output_Prd_eligi);

					 //DBConnection con=new DBConnection();
					 try
					 {
						 con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_output_Prd_eligi.replaceAll("'", "''"));
					 }
					 catch(Exception ee)
					 {
						 ee.getMessage();
					 }
					 LogGEN.writeTrace("Log","Query : finally :"+Query);
					 LogGEN.writeTrace("Log","outputXML : finally :"+outputxml);
			}
			return "";
		}
}
