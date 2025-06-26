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
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.AddDeliveryDetailsReqMsg;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.AddDeliveryDetailsReq_type0;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.AddDeliveryDetailsResMsg;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.AddDeliveryDetailsRes_type0;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.HeaderType;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.UpdateDeliveryStatusReqMsg;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.UpdateDeliveryStatusReq_type0;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.UpdateDeliveryStatusResMsg;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.UpdateDeliveryStatusRes_type0;

public class AddUpdateCDTSDetails extends DSCOPServiceHandler{

	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected long lTimeOut = 30000;
	String xmlInput="";
	String sOrgRes="";
	String dburl="";
	String dbuser="";
	String dbpass="";
	String sOutput= "";
	String callType="";
	UpdateDeliveryStatusResMsg statusResMsg;
	AddDeliveryDetailsResMsg detailsResMsg;

	public String addUpdateCDTS(String sInputXML){
		String Status="";                        
		LogGEN.writeTrace("CBG_Log", "Fuction called addUpdateCDTS");
		LogGEN.writeTrace("CBG_Log", "AddUpdateCDTSDetails sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";

		DSCOPServiceHandler sHandler= new DSCOPServiceHandler();
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);

		try {
			LogGEN.writeTrace("CBG_Log", "inside try block");
			String wsdlpath=loadWSDLDtls(sHandler);             
			String ref_no=xmlDataParser.getValueOf("REF_NO");
			String senderId = xmlDataParser.getValueOf("SenderId");
			callType = xmlDataParser.getValueOf("ServiceName");
			LogGEN.writeTrace("CBG_Log", "read Property successfully "+ref_no+"||"+senderId+"||"+callType);

			ModCBGCustomerOnboardingStub customerOnboardingStub =new ModCBGCustomerOnboardingStub(wsdlpath);
			HeaderType headerType= new HeaderType();
			// for addDeliveryDetails_Oper method
			AddDeliveryDetailsReqMsg deliveryDetailsReqMsg=new AddDeliveryDetailsReqMsg();
			AddDeliveryDetailsReq_type0 deliveryDetailsReq_type0=new AddDeliveryDetailsReq_type0();

			// for updateDeliveryStatus_Oper method
			UpdateDeliveryStatusReqMsg statusReqMsg =new  UpdateDeliveryStatusReqMsg();
			UpdateDeliveryStatusReq_type0 statusReq_type0 = new UpdateDeliveryStatusReq_type0();

			if(callType.equalsIgnoreCase("AddDeliveryDetails")){
				deliveryDetailsReqMsg.setHeader(setHeaderDtls(sDate,ref_no,senderId,callType,headerType));
			}
			if(callType.equalsIgnoreCase("UpdateDeliveryStatus")){
				statusReqMsg.setHeader(setHeaderDtls(sDate, ref_no, senderId, callType,headerType));
			}


			if(callType.equalsIgnoreCase("AddDeliveryDetails")){
				LogGEN.writeTrace("CBG_Log", "inside AddDeliveryDetails");
				deliveryDetailsReq_type0.setCustomerId(xmlDataParser.getValueOf("customerId"));
				deliveryDetailsReq_type0.setReferenceNo(xmlDataParser.getValueOf("referenceNo"));
				deliveryDetailsReq_type0.setBranchCode(xmlDataParser.getValueOf("branchCode"));
				deliveryDetailsReq_type0.setBranchName(xmlDataParser.getValueOf("branchName"));
				deliveryDetailsReq_type0.setCustomerName(xmlDataParser.getValueOf("customerName"));
				deliveryDetailsReq_type0.setCustomerType(xmlDataParser.getValueOf("customerType"));
				deliveryDetailsReq_type0.setPoBox(xmlDataParser.getValueOf("poBox"));
				deliveryDetailsReq_type0.setAddress1(xmlDataParser.getValueOf("address1"));
				deliveryDetailsReq_type0.setAddress2(xmlDataParser.getValueOf("address2"));
				deliveryDetailsReq_type0.setCity(xmlDataParser.getValueOf("city"));
				deliveryDetailsReq_type0.setCountry(xmlDataParser.getValueOf("country"));
				deliveryDetailsReq_type0.setEmirates(xmlDataParser.getValueOf("emirates"));
				deliveryDetailsReq_type0.setPhoneNumber(xmlDataParser.getValueOf("phoneNumber"));
				deliveryDetailsReq_type0.setMobileNumber(xmlDataParser.getValueOf("mobileNumber"));
				deliveryDetailsReq_type0.setAirwayBillNo(xmlDataParser.getValueOf("airwayBillNo"));
				deliveryDetailsReq_type0.setAirwayBillBy(xmlDataParser.getValueOf("airwayBillBy"));
				deliveryDetailsReq_type0.setAirwayBillTime(xmlDataParser.getValueOf("airwayBillTime"));
				deliveryDetailsReq_type0.setCustomerNationality(xmlDataParser.getValueOf("customerNationality"));
				deliveryDetailsReq_type0.setEmployerName(xmlDataParser.getValueOf("employerName"));
				deliveryDetailsReq_type0.setAccountNumberWelcomeKit(xmlDataParser.getValueOf("accountNumberWelcomeKit"));
				deliveryDetailsReq_type0.setAccountTitle(xmlDataParser.getValueOf("accountTitle"));
				deliveryDetailsReq_type0.setAccountOpendate(xmlDataParser.getValueOf("accountOpendate"));
				deliveryDetailsReq_type0.setProductCode(xmlDataParser.getValueOf("productCode"));
				deliveryDetailsReq_type0.setProductDesc(xmlDataParser.getValueOf("productDesc"));
				deliveryDetailsReq_type0.setSdcPrimary(xmlDataParser.getValueOf("sdcPrimary"));
				deliveryDetailsReq_type0.setSdcSupp1(xmlDataParser.getValueOf("sdcSupp1"));
				deliveryDetailsReq_type0.setSdcSupp2(xmlDataParser.getValueOf("sdcSupp2"));
				deliveryDetailsReq_type0.setSdcSupp3(xmlDataParser.getValueOf("sdcSupp3"));
				deliveryDetailsReq_type0.setSdcSupp4(xmlDataParser.getValueOf("sdcSupp4"));
				deliveryDetailsReq_type0.setDebitCardNumber(xmlDataParser.getValueOf("debitCardNumber"));
				deliveryDetailsReq_type0.setChequeBookStatus(xmlDataParser.getValueOf("chequeBookStatus"));
				deliveryDetailsReq_type0.setPrivilegeVoucherStatus(xmlDataParser.getValueOf("privilegeVoucherStatus"));
				deliveryDetailsReq_type0.setPodStatus(xmlDataParser.getValueOf("podStatus"));
				deliveryDetailsReq_type0.setAssignedBranch(xmlDataParser.getValueOf("assignedBranch"));
				deliveryDetailsReq_type0.setAccountHomeBranch(xmlDataParser.getValueOf("accountHomeBranch"));
				deliveryDetailsReq_type0.setCreditcardnumber(xmlDataParser.getValueOf("creditcardnumber"));
				deliveryDetailsReq_type0.setStatus(xmlDataParser.getValueOf("status"));
				deliveryDetailsReq_type0.setStage(xmlDataParser.getValueOf("stage"));
				deliveryDetailsReq_type0.setCardNumberDC(xmlDataParser.getValueOf("cardNumberDC"));
				deliveryDetailsReq_type0.setIssuanceDate(xmlDataParser.getValueOf("issuanceDate"));
				deliveryDetailsReq_type0.setCardholderNameDC(xmlDataParser.getValueOf("cardholderNameDC"));
				deliveryDetailsReq_type0.setCardType(xmlDataParser.getValueOf("cardType"));
				deliveryDetailsReq_type0.setDcmsStatus(xmlDataParser.getValueOf("dcmsStatus"));
				deliveryDetailsReq_type0.setPrimSupply(xmlDataParser.getValueOf("primSupply"));
				deliveryDetailsReq_type0.setStartChequeNo(xmlDataParser.getValueOf("startChequeNo"));
				deliveryDetailsReq_type0.setNoOfLeaves(xmlDataParser.getValueOf("noOfLeaves"));
				deliveryDetailsReq_type0.setAccountNoCB(xmlDataParser.getValueOf("accountNoCB"));
				deliveryDetailsReq_type0.setDateIssue(xmlDataParser.getValueOf("dateIssue"));
				deliveryDetailsReq_type0.setSourceLocation(xmlDataParser.getValueOf("sourceLocation"));
				deliveryDetailsReq_type0.setNameThirdParty(xmlDataParser.getValueOf("nameThirdParty"));
				deliveryDetailsReq_type0.setThirdPartyMobileNo(xmlDataParser.getValueOf("thirdPartyMobileNo"));
				deliveryDetailsReq_type0.setPhotoIdType(xmlDataParser.getValueOf("photoIdType"));
				deliveryDetailsReq_type0.setPhotoIdNo(xmlDataParser.getValueOf("photoIdNo"));
				deliveryDetailsReq_type0.setEmailId(xmlDataParser.getValueOf("emailId"));
				deliveryDetailsReq_type0.setCardNumberCC(xmlDataParser.getValueOf("cardNumberCC"));
				deliveryDetailsReq_type0.setPrimaryCardLogo(xmlDataParser.getValueOf("primaryCardLogo"));
				deliveryDetailsReq_type0.setBatchNumber(xmlDataParser.getValueOf("batchNumber"));
				deliveryDetailsReq_type0.setCardHolderNameCC(xmlDataParser.getValueOf("cardHolderNameCC"));
				deliveryDetailsReq_type0.setWelcomePack(xmlDataParser.getValueOf("welcomePack"));
				deliveryDetailsReq_type0.setReplacementIndicator(xmlDataParser.getValueOf("replacementIndicator"));
				deliveryDetailsReq_type0.setBranchId(xmlDataParser.getValueOf("branchId"));
				deliveryDetailsReq_type0.setPrimarySupplementary(xmlDataParser.getValueOf("primarySupplementary"));
				deliveryDetailsReq_type0.setPromoCode1(xmlDataParser.getValueOf("promoCode1"));
				deliveryDetailsReq_type0.setPromoCode2(xmlDataParser.getValueOf("promoCode2"));

				deliveryDetailsReqMsg.setAddDeliveryDetailsReq(deliveryDetailsReq_type0);
				xmlInput=customerOnboardingStub.getInputXml(deliveryDetailsReqMsg);
				LogGEN.writeTrace("CBG_Log", "AddCDTSDetails InputXML before hitting url:  " + xmlInput);
				detailsResMsg= customerOnboardingStub.addDeliveryDetails_Oper(deliveryDetailsReqMsg);
				headerType=detailsResMsg.getHeader();
			}
			if(callType.equalsIgnoreCase("UpdateDeliveryStatus")){
				LogGEN.writeTrace("CBG_Log", "inside UpdateDeliveryStatus");
				statusReq_type0.setReferenceNo(xmlDataParser.getValueOf("referenceNo"));
				statusReq_type0.setAirwayBillNo(xmlDataParser.getValueOf("airwayBillNo"));
				statusReq_type0.setStatus(xmlDataParser.getValueOf("status"));
				statusReq_type0.setStage(xmlDataParser.getValueOf("stage"));
				statusReq_type0.setBranchID(xmlDataParser.getValueOf("branchID"));
				statusReq_type0.setUserId(xmlDataParser.getValueOf("userId"));
				statusReq_type0.setHandoverDate(xmlDataParser.getValueOf("handoverDate"));
				statusReq_type0.setDeliveryReturnDate(xmlDataParser.getValueOf("deliveryReturnDate"));
				statusReq_type0.setDestruction(xmlDataParser.getValueOf("destruction"));
				statusReq_type0.setDestructionRemarks(xmlDataParser.getValueOf("destructionRemarks"));
				statusReq_type0.setReturnedReason(xmlDataParser.getValueOf("returnedReason"));
				statusReq_type0.setDeliveryReturnRemarks(xmlDataParser.getValueOf("deliveryReturnRemarks"));

				statusReqMsg.setUpdateDeliveryStatusReq(statusReq_type0);
				xmlInput=customerOnboardingStub.getInputXml(statusReqMsg);
				LogGEN.writeTrace("CBG_Log", "AddUpdateCDTSDetails InputXML: " + xmlInput);
				statusResMsg = customerOnboardingStub.updateDeliveryStatus_Oper(statusReqMsg);
				headerType=statusResMsg.getHeader();

			}

			customerOnboardingStub._getServiceClient().getOptions().setTimeOutInMilliSeconds(this.lTimeOut);
			if(callType.equalsIgnoreCase("AddDeliveryDetails")){
				xmlInput=customerOnboardingStub.getInputXml(deliveryDetailsReqMsg);
				sOrgRes = customerOnboardingStub.outputXML;
			}
			if(callType.equalsIgnoreCase("UpdateDeliveryStatus")){
				xmlInput=customerOnboardingStub.getInputXml(statusReqMsg);
				sOrgRes = customerOnboardingStub.outputXML;
			}
			//			LogGEN.writeTrace("CBG_Log", "AddUpdateCDTSDetails OutputXML: " + sOrgRes);

			sReturnCode=headerType.getReturnCode();
			sErrorDetail=headerType.getErrorDetail();
			sErrorDesc=headerType.getErrorDescription();
			LogGEN.writeTrace("CBG_Log", "Error Description--- " + sErrorDesc+"|| sReturnCode-- "+sReturnCode+"|| sErrorDetail-- "+sErrorDetail);

			if ((!(sErrorDesc.equalsIgnoreCase("Failure"))) || (!(sReturnCode.equalsIgnoreCase("1")))){
				if(callType.equalsIgnoreCase("AddDeliveryDetails")){
					AddDeliveryDetailsRes_type0 detailsRes_type0=new AddDeliveryDetailsRes_type0();
					detailsRes_type0=detailsResMsg.getAddDeliveryDetailsRes();
					sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>CBGCustomerOnboarding</Option><returnCode>" + 
							sReturnCode + "</returnCode>" + 
							"<errorDescription>" + sErrorDetail + "</errorDescription>" + 
							"<addDeliveryDetailsRes>"+
							"<customerId>"+detailsRes_type0.getCustomerId()+"</customerId>"+
							"<referenceNo>"+detailsRes_type0.getReferenceNo()+"</referenceNo>"+
							"<reason>"+detailsRes_type0.getReason()+"</reason>"+
							"<status>"+detailsRes_type0.getStatus()+"</status>"+
							"</addDeliveryDetailsRes>"+
							"</Output>";
				}
				if(callType.equalsIgnoreCase("UpdateDeliveryStatus")){
					UpdateDeliveryStatusRes_type0 statusRes_type0 =new UpdateDeliveryStatusRes_type0();
					statusRes_type0=statusResMsg.getUpdateDeliveryStatusRes();
					sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>CBGCustomerOnboarding</Option><returnCode>" + 
							sReturnCode + "</returnCode>" + 
							"<errorDescription>" + sErrorDetail + "</errorDescription>" + 
							"<updateDeliveryStatusRes>"+
							"<airwayBillNo>"+statusRes_type0.getAirwayBillNo()+"</airwayBillNo>"+
							"<referenceNo>"+statusRes_type0.getReferenceNo()+"</referenceNo>"+
							"<reason>"+statusRes_type0.getReason()+"</reason>"+
							"<status>"+statusRes_type0.getStatus()+"</status>"+
							"</updateDeliveryStatusRes>"+
							"</Output>";
				}
			}
			else{
				LogGEN.writeTrace("CBG_Log", "Failed");
				System.out.println("Failed");
				sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>CBGCustomerOnboarding</Option><Output><returnCode>" + sReturnCode + "</returnCode><errorDescription>" + sErrorDetail + "</errorDescription><td>Unable to AddUpdateCDTSDetails.</td></Output>";
			}

		} catch (Exception e) {
			LogGEN.writeTrace("CBG_Log", "Error in Web Serviice :" + e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :" + e.getStackTrace());
			System.out.println("Error Trace in Web Serviice :" + e.getStackTrace());
			Status = "Failure";
			sErrorDetail = e.getMessage();
			sReturnCode = "-1";
			sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>CBGCustomerOnboarding</Option><Output><returnCode>" + sReturnCode + "</returnCode><errorDescription>" + sErrorDetail + "</errorDescription><td>Unable to AddUpdateCDTSDetails.</td></Output>";
			e.printStackTrace();
		}
		finally{
			LogGEN.writeTrace("CBG_Log", "outputXML.trim().length() :" + sOutput.trim().length()); 
			System.out.println("outputXML.trim().length() :" + sOutput.trim().length());
		}
		if (sOutput.trim().length() < 1)
		{
			sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>CBGCustomerOnboarding</Option><Output><returnCode>" + sReturnCode + "</returnCode><errorDescription>" + sErrorDetail + "</errorDescription><td>Unable to AddUpdateCDTSDetails.</td></Output>";
		}

		if ((sReturnCode.equalsIgnoreCase("0")) || (sReturnCode.equalsIgnoreCase("2")))
		{
			Status = "Success";
		}
		else
			Status = "Failure";
		//		String inputXml=xmlInput;
		String dburl = (String)currentCabPropertyMap.get("DBURL");
		String dbuser = (String)currentCabPropertyMap.get("USER");
		String dbpass = (String)currentCabPropertyMap.get("PASS");
		String winame = xmlDataParser.getValueOf("WiName");
		String sessionID = xmlDataParser.getValueOf("SessionId");
		String call_type = xmlDataParser.getValueOf("DSCOPCallType");
		sCabinet = xmlDataParser.getValueOf("EngineName");
		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		LogGEN.writeTrace("CBG_Log", "11111111111111111111%%%%%%%%%%%%");
		try
		{
			dbpass = AESEncryption.decrypt(dbpass);
		}
		catch (Exception localException1)
		{
		}
		DBConnection con=new DBConnection();
		String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
				"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
				"?,sysdate,'"+  Status + "')";
		LogGEN.writeTrace("CBG_Log"," Add  Query : finally :"+Query);
		con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,xmlInput.replaceAll("'", "''"),sOrgRes.replaceAll("'", "''"));
		return sOutput;
	}

	private String loadWSDLDtls(DSCOPServiceHandler sHandler) {
		try {
			//sHandler.readCabProperty("CBGCustomerOnboarding");
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("CBGCustomerOnboarding");
			LogGEN.writeTrace("CBG_Log", "CBGCustomerOnboarding WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "CBGCustomerOnboarding WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "CBGCustomerOnboarding CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "CBGCustomerOnboarding USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "CBGCustomerOnboarding PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "CBGCustomerOnboarding LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "CBGCustomerOnboarding TIME_OUT: "+lTimeOut);
			return sWSDLPath;
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return "";
	}


	private HeaderType setHeaderDtls(String sDate,String ref_no,String senderId,String callType,HeaderType headerType){
		LogGEN.writeTrace("CBG_Log", "inside setHeaderDtls function "); 	
		headerType.setUsecaseID("1234");
		headerType.setServiceName("ModCBGCustomerOnboarding");
		headerType.setVersionNo("1.0");
		headerType.setServiceAction("Modify");
		headerType.setSysRefNumber(ref_no);		
		headerType.setSenderID(senderId); 
		headerType.setConsumer("");
		headerType.setReqTimeStamp(sDate);
		headerType.setUsername("");
		headerType.setCredentials("");
		headerType.setCorrelationID("1212");

		LogGEN.writeTrace("CBG_Log", "setHeaderDtls complete ");

		return headerType;
	}
}
