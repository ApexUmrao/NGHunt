package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.AddOnlineLeadReqMsg;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.AddOnlineLeadReq_type0;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.AddOnlineLeadResMsg;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.AddOnlineLeadRes_type0;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.HeaderType;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.InqLeadAccountCurrency_type0;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.InqLeadAccountDetails_type0;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.InqLeadAuthSignatory_type0;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.InqLeadCRSCP_type0;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.InqLeadCRS_type0;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.InqLeadFATCA_type0;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.InqLeadOwnershipStructure_type0;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.InqLeadSMEFin_type0;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.InqPrimaryContactDetails_type0;


public class LeadCreation extends DSCOPServiceHandler  {
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String Lead_Creation_ouput=null;

	@SuppressWarnings("finally")
	public String leadCreation(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Fuction called leadCreation");
		LogGEN.writeTrace("CBG_Log", "LeadCreation sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sOutput="";
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc = "";
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		try
		{

			//sHandler.readCabProperty("mod_cbg_customer_onboarding");				

			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("mod_cbg_customer_onboarding");
			LogGEN.writeTrace("CBG_Log", "mod_cbg_customer_onboarding WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "mod_cbg_customer_onboarding WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "mod_cbg_customer_onboarding CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "mod_cbg_customer_onboarding USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "mod_cbg_customer_onboarding PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "mod_cbg_customer_onboarding LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "mod_cbg_customer_onboarding TIME_OUT: "+lTimeOut);


			LogGEN.writeTrace("CBG_Log", "sDate---"+sDate);					
			String ref_no=xmlDataParser.getValueOf("REF_NO");
			sCabinet=xmlDataParser.getValueOf("EngineName");
			String senderId = xmlDataParser.getValueOf("SenderId");

			ModCBGCustomerOnboardingStub lead_creation_stub=new ModCBGCustomerOnboardingStub(sWSDLPath);
			AddOnlineLeadReqMsg lead_creation_req = new AddOnlineLeadReqMsg();
			AddOnlineLeadResMsg res_msg = new AddOnlineLeadResMsg();
			AddOnlineLeadReq_type0 req= new AddOnlineLeadReq_type0();

			HeaderType Header_Input = new HeaderType();
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("ModCBGCustomerOnboarding");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(senderId);
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setCredentials(loggedinuser);			  

			req.setRMName(xmlDataParser.getValueOf("RMName"));	
			req.setEmailKeyContactPerson(xmlDataParser.getValueOf("EmailKeyContactPerson"));
			req.setEmirateLoc(xmlDataParser.getValueOf("EmirateLoc"));
			req.setFirstNKeyContactPerson(xmlDataParser.getValueOf("FirstNKeyContactPerson"));
			req.setFreeZoneLocation(xmlDataParser.getValueOf("FreeZoneLocation"));
			req.setIntegrationId(xmlDataParser.getValueOf("IntegrationId"));
			req.setTypeofRelationship(xmlDataParser.getValueOf("TypeofRelationship"));
			req.setTradeLicenseNumber(xmlDataParser.getValueOf("TradeLicenseNumber"));
			req.setTradeLicenseIssueDate(xmlDataParser.getValueOf("TradeLicenseIssueDate"));
			req.setProductGroup(xmlDataParser.getValueOf("ProductGroup"));
			req.setProductCode(xmlDataParser.getValueOf("ProductCode"));
			req.setPortalstatus(xmlDataParser.getValueOf("Portalstatus"));
			req.setPortalchannel(xmlDataParser.getValueOf("Portalchannel"));
			req.setSourceChannel(xmlDataParser.getValueOf("SourceChannel"));
			req.setPlaceOfIncorporation(xmlDataParser.getValueOf("PlaceOfIncorporation"));
			req.setMobKeyContactPerson(xmlDataParser.getValueOf("MobKeyContactPerson"));
			req.setLegalConstitution(xmlDataParser.getValueOf("LegalConstitution"));
			req.setLeadStatus(xmlDataParser.getValueOf("LeadStatus"));
			req.setLeadRefNumber(xmlDataParser.getValueOf("LeadRefNumber"));
			req.setLastNKeyContactPerson(xmlDataParser.getValueOf("LastNKeyContactPerson"));
			req.setADCBNoYearsBusiness(xmlDataParser.getValueOf("ADCBNoYearsBusiness"));
			req.setADCBWBGR2ExpiryDate(xmlDataParser.getValueOf("ADCBWBGR2ExpiryDate"));
			req.setBusinessSegment(xmlDataParser.getValueOf("BusinessSegment"));
			req.setBusinessTurnover(xmlDataParser.getValueOf("BusinessTurnover"));
			req.setCompanyName(xmlDataParser.getValueOf("CompanyName"));
			req.setCustomerId(xmlDataParser.getValueOf("CustomerId"));
			req.setFlexiField1(xmlDataParser.getValueOf("FlexiField1"));
			req.setAccessPoint(xmlDataParser.getValueOf("AccessPoint"));
		    req.setADCBAssignFlag(xmlDataParser.getValueOf("ADCBAssignFlag"));

			InqLeadAuthSignatory_type0[] param=new InqLeadAuthSignatory_type0[1];
			param[0]=new InqLeadAuthSignatory_type0();
			param[0].setCountryOfResidence("");
			param[0].setCustomerName("");
			param[0].setISSignatoryResidentofUAE("");
			param[0].setMobile("");
			param[0].setPrefix("");
			req.setInqLeadAuthSignatory(param);

			InqLeadOwnershipStructure_type0[] param1=new InqLeadOwnershipStructure_type0[1];
			param1[0]=new InqLeadOwnershipStructure_type0();
			param1[0].setCountryofLegalResidence("");
			param1[0].setNameofOwner("");
			param1[0].setNationality("");
			param1[0].setOwnerType("");
			param1[0].setPhysicalAddress("");
			param1[0].setPlaceofIncorporation("");
			param1[0].setPhysicalAddress("");
			param1[0].setPrefix("");
			param1[0].setSharesPercentage("");
			param1[0].setStatus("");
			param1[0].setType("");			
			req.setInqLeadOwnershipStructure(param1);

			InqPrimaryContactDetails_type0[] param2=new InqPrimaryContactDetails_type0[1];
			param2[0] = new InqPrimaryContactDetails_type0();
			param2[0].setContactPerson("");
			param2[0].setCorrCity("");
			param2[0].setCorrCountry("");
			param2[0].setCorrEmirate("");
			param2[0].setCorrPOBox("");
			param2[0].setDepartment("");
			param2[0].setDisignation("");
			param2[0].setMobile("");
			param2[0].setOfficialEmail("");
			param2[0].setPhBuildingName("");
			param2[0].setPhCity("");
			param2[0].setPhCountry("");
			param2[0].setPhEmirate("");
			param2[0].setPhOfficeNumber("");
			param2[0].setPhOfficeUnitNo("");
			param2[0].setPhPOBox("");
			param2[0].setPhStreetName("");
			param2[0].setTelephone("");
			req.setInqPrimaryContactDetails(param2);

			InqLeadFATCA_type0[] param3=new InqLeadFATCA_type0[1];
			param3[0] = new InqLeadFATCA_type0();
			param3[0].setEIN("");
			param3[0].setEntityStatus("");
			param3[0].setFATCAStatus("");
			param3[0].setGIIN("");
			param3[0].setNatureofEntity("");
			req.setInqLeadFATCA(param3);

			InqLeadSMEFin_type0[] param4=new InqLeadSMEFin_type0[1];
			param4[0] = new InqLeadSMEFin_type0();
			param4[0].setFacilitiesSelected("");
			param4[0].setProductInterested("");
			req.setInqLeadSMEFin(param4);

			InqLeadCRS_type0[] param5=new InqLeadCRS_type0[1];
			param5[0] = new InqLeadCRS_type0();
			InqLeadCRSCP_type0[] param6=new InqLeadCRSCP_type0[1];
			param6[0] = new InqLeadCRSCP_type0();
			param6[0].setBuildingName("");
			param6[0].setCity("");
			param6[0].setCityOfBirth("");
			param6[0].setCountry("");
			param6[0].setCountryOfBirth("");
			param6[0].setCountryOfTaxResidency("");
			param6[0].setDateOfBirth("");
			param6[0].setEmirate("");
			param6[0].setFirstName("");
			param6[0].setLastName("");
			param6[0].setReasonsForNotProvidingTIN("");
			param6[0].setStreet("");
			param6[0].setTaxpayerIdentificationNumber("");
			param6[0].setTypeOfControllingPerson("");
			param6[0].setVillaFlateNo("");
			param5[0].setInqLeadCRSCP(param6);

			param5[0].setCountryOfTaxResidency("");
			param5[0].setEntityStatus("");
			param5[0].setReasonsForNotProvidingTIN("");
			param5[0].setTaxpayerIdentificationNumber("");
			param5[0].setEntityType("");
			req.setInqLeadCRS(param5);

			InqLeadAccountDetails_type0[] param7=new InqLeadAccountDetails_type0[1];
			param7[0] = new InqLeadAccountDetails_type0();
			param7[0].setAccountType("");
			param7[0].setBranchName("");
			param7[0].setChequeBookRequired("");
			param7[0].setDeliveryContactMobileNumber("");
			param7[0].setDeliveryContactName("");
			param7[0].setDeliveryMethod("");
			param7[0].setEmiratesIDNo("");
			param7[0].setModeOfOperation("");
			param7[0].setOperatingInstruction("");

			InqLeadAccountCurrency_type0[] param8=new InqLeadAccountCurrency_type0[1];
			param8[0] = new InqLeadAccountCurrency_type0();
			param8[0].setCurrency("");
			param7[0].setInqLeadAccountCurrency(param8);
			req.setInqLeadAccountDetails(param7);

			lead_creation_req.setHeader(Header_Input);
			lead_creation_req.setAddOnlineLeadReq(req);


			LogGEN.writeTrace("CBG_Log", "All values set");

			xmlInput=lead_creation_stub.getInputXml(lead_creation_req);
			LogGEN.writeTrace("CBG_Log", "All values set xml " + xmlInput);
			lead_creation_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			res_msg=lead_creation_stub.addOnlineLead_Oper(lead_creation_req);
			Lead_Creation_ouput = lead_creation_stub.outputXML;
			//		    LogGEN.writeTrace("CBG_Log", "Lead_Creation_ouput : "+Lead_Creation_ouput);
			Header_Input = res_msg.getHeader();
			sReturnCode = Header_Input.getReturnCode();
			sErrorDetail = Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();

			LogGEN.writeTrace("CBG_Log", "Return Code---"+sReturnCode);
			LogGEN.writeTrace("CBG_Log", "Error Detail---"+sErrorDetail);
			LogGEN.writeTrace("CBG_Log", "Error Description---"+sErrorDesc);



			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{			    
				LogGEN.writeTrace("CBG_Log", "Successful Result");
				AddOnlineLeadRes_type0 res=new AddOnlineLeadRes_type0();
				res=res_msg.getAddOnlineLeadRes();

				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>LEAD_CREATION</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<returnCode>"+sReturnCode+"</returnCode><errorDetail>"+sErrorDetail+"</errorDetail>";						
				sOutput+=
						"<Reason>"+res.getReason()+"</Reason>"+
								"<LeadRefNo>"+res.getLeadRefNumber()+"</LeadRefNo>";
				sOutput+="</Output>";
				System.out.println(sOutput);
				LogGEN.writeTrace("CBG_Log", "Lead_Creation Output XML--- "+sOutput);

			}
			else
			{
				//AddOnlineLeadRes_type0 res=new AddOnlineLeadRes_type0();
				//res=res_msg.getAddOnlineLeadRes();
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>LEAD_CREATION</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>-1</Status><Reason>Failure</Reason><td>LEAD not created.</td></Output>";
			}

		}
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="1";
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>LEAD_CREATION</Option><returnCode>-1</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>LEAD not created.</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>LEAD_CREATION</Option><returnCode>-1</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>LEAD not created.</td></Output>";
			}
			String Status="";
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
			{
				Status="Success";
			}
			else
				Status="Failure";

			try {
				//sHandler.readCabProperty("JTS");
			} catch (Exception e) {
				e.printStackTrace();
			}	

			String dburl=(String)currentCabPropertyMap.get("DBURL");
			String dbuser=(String)currentCabPropertyMap.get("USER");
			String dbpass=(String)currentCabPropertyMap.get("PASS");

			String inputXml = xmlInput;
			//				LogGEN.writeTrace("CBG_Log", inputXml);
			String winame = xmlDataParser.getValueOf("WiName");
			String sessionID = xmlDataParser.getValueOf("SessionId");
			String call_type = xmlDataParser.getValueOf("DSCOPCallType");
			sCabinet = xmlDataParser.getValueOf("EngineName");

			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
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
			LogGEN.writeTrace("CBG_Log"," Inquiry business rules Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","Lead_Creation_ouput : finally :"+Lead_Creation_ouput);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),Lead_Creation_ouput.replaceAll("'", "''"));
			} catch (Exception e2) {
				e2.getMessage();
			}
			//			LogGEN.writeTrace("CBG_Log","outputXML : finally :"+sOutput);
			return sOutput;			
		}


	}


}
