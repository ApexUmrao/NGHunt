package com.newgen.dscop.client;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.ModCustMDMPubInfoStub;
import com.newgen.dscop.stub.ModCustMDMPubInfoStub.HeaderType;
import com.newgen.dscop.stub.ModCustMDMPubInfoStub.ModCustMDMPubInfoReqMsg;
import com.newgen.dscop.stub.ModCustMDMPubInfoStub.ModCustMDMPubInfoReq_type0;
import com.newgen.dscop.stub.ModCustMDMPubInfoStub.ModCustMDMPubInfoResMsg;
import com.newgen.dscop.stub.ModCustMDMPubInfoStub.ModCustMDMPubInfoRes_type0;

public class SaveKYCDetails extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String reason ="";
	String status ="";
	String sOrg_Output=null;
	
	@SuppressWarnings("finally")
	public String saveKYCDetailsStatus(String sInputXML) throws RemoteException
	{
	
		LogGEN.writeTrace("CBG_Log", "Fuction called saveKYCDetailsStatus");
		XMLParser xmlDataParser = new XMLParser();
		LogGEN.writeTrace("CBG_Log", "SaveKYCDetails input xml "+sInputXML.replaceAll("&", "and"));
		xmlDataParser.setInputXML(sInputXML.replaceAll("&", "and"));
		String sOutput="";
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc = "";
		DSCOPServiceHandler sHandler= new DSCOPServiceHandler();
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		
		try
		{
			//sHandler.readCabProperty("SaveFATCADetails");				
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("SaveFATCADetails");
			LogGEN.writeTrace("CBG_Log", "SaveFATCADetails WebServiceConfig Map : " +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "SaveFATCADetails WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "SaveFATCADetails CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "SaveFATCADetails USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "SaveFATCADetails PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "SaveFATCADetails LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "SaveFATCADetails TIME_OUT: "+lTimeOut);
			
			String ref_no=xmlDataParser.getValueOf("REF_NO");
			String customerId = xmlDataParser.getValueOf("customerId");
			String sMaintenanceOption=xmlDataParser.getValueOf("maintenanceOption");
			String dateOfMaturity=xmlDataParser.getValueOf("dateOfAttainingMaturity");
			String addBuildName=xmlDataParser.getValueOf("permAddBuildingName");
			String permAddVillaFlatNo=xmlDataParser.getValueOf("permAddVillaFlatNo");
			String permAddStreetNoNameLandmark=xmlDataParser.getValueOf("permAddStreetNoNameLandmark");
			String permAddState=xmlDataParser.getValueOf("permAddState");
			String permAddCountry=xmlDataParser.getValueOf("permAddCountry");
			String permAddCity=xmlDataParser.getValueOf("permAddCity");
			String visaStatus=xmlDataParser.getValueOf("visaStatus");
			String passportType=xmlDataParser.getValueOf("passportType");
			String dateOfKYCPreparation=xmlDataParser.getValueOf("dateOfKYCPreparation");
			String existingRelationshipSince=xmlDataParser.getValueOf("existingRelationshipSince");
			String accountNoCovered=xmlDataParser.getValueOf("accountNoCovered");
			String purposeForAccountReltnship=xmlDataParser.getValueOf("purposeForAccountReltnship");
			String companyCode=xmlDataParser.getValueOf("companyCode");
			String finActivitiesCountries=xmlDataParser.getValueOf("finActivitiesCountries");
			String natureOfActivityOfIntlFTs=xmlDataParser.getValueOf("natureOfActivityOfIntlFTs");
		
			String expctdMonDepCash=xmlDataParser.getValueOf("expctdMonDepCash").replaceAll("<", "Is less than ").replaceAll(">", "Is more than ");
			String expctdMonDepCheque=xmlDataParser.getValueOf("expctdMonDepCheque").replaceAll("<", "Is less than ").replaceAll(">", "Is more than ");
			String expctdMonDepTransfer=xmlDataParser.getValueOf("expctdMonDepTransfer").replaceAll("<", "Is less than ").replaceAll(">", "Is more than ");
			String expctdMonDepInvest=xmlDataParser.getValueOf("expctdMonDepInvest").replaceAll("<", "Is less than ").replaceAll(">", "Is more than ");
			String expctdMonWthdrlCash=xmlDataParser.getValueOf("expctdMonWthdrlCash").replaceAll("<", "Is less than ").replaceAll(">", "Is more than ");
			String expctdMonWthdrlCheque=xmlDataParser.getValueOf("expctdMonWthdrlCheque").replaceAll("<", "Is less than ").replaceAll(">", "Is more than ");
			String expctdMonWthdrlTransfer = xmlDataParser.getValueOf("expctdMonWthdrlTransfer").replaceAll("<", "Is less than ").replaceAll(">", "Is more than ");
			String expctdMonWthdrlInvest=xmlDataParser.getValueOf("expctdMonWthdrlInvest").replaceAll("<", "Is less than ").replaceAll(">", "Is more than ");
			
			String dealsInArmaments=xmlDataParser.getValueOf("dealsInArmaments");
			String sHawala=xmlDataParser.getValueOf("customerHawalaFlag");//xmlDataParser.getValueOf("hawala");	//01/02/2023
			String customerPEPFlag=xmlDataParser.getValueOf("customerPEPFlag");
			String customerUAEResidentFlag=xmlDataParser.getValueOf("customerUAEResidentFlag");
			String detailRealEstate=xmlDataParser.getValueOf("detailRealEstate");
			String relationship1BankName=xmlDataParser.getValueOf("relationship1BankName");
			String relationship2BankName=xmlDataParser.getValueOf("relationship2BankName");
			String relationship3BankName=xmlDataParser.getValueOf("relationship3BankName");
			String relationship1Address=xmlDataParser.getValueOf("relationship1Address");
			String relationship2Address=xmlDataParser.getValueOf("relationship2Address");
			String relationship3Address=xmlDataParser.getValueOf("relationship3Address");
			String customerTypeOldFlag=xmlDataParser.getValueOf("customerTypeOldFlag");
			String customerTypeNewFlag=xmlDataParser.getValueOf("customerTypeNewFlag");
			String sProcessType  = xmlDataParser.getValueOf("onshoreOffshoreFlag");
			
			String lastCategoryChangeDate=xmlDataParser.getValueOf("lastCategoryChangeDate");
			String membershipWith=xmlDataParser.getValueOf("membershipWith");
			String privilegeBenefits=xmlDataParser.getValueOf("privilegeBenefits");
			String excellencyBenefits=xmlDataParser.getValueOf("excellencyBenefits");
			String sMakerID=xmlDataParser.getValueOf("makerid");
			
//			String dealsInWMD = xmlDataParser.getValueOf("dealsInWMD");
//			String salaryTransfer = xmlDataParser.getValueOf("salaryTransfer");
				 
			ModCustMDMPubInfoStub stub = new ModCustMDMPubInfoStub(sWSDLPath);
			HeaderType Header_Input = new HeaderType();
			ModCustMDMPubInfoReq_type0 reqType = new ModCustMDMPubInfoReq_type0();
			ModCustMDMPubInfoReqMsg reqMessage = new ModCustMDMPubInfoReqMsg();
			ModCustMDMPubInfoRes_type0 resType = new ModCustMDMPubInfoRes_type0();
			ModCustMDMPubInfoResMsg resMessage = new ModCustMDMPubInfoResMsg();
			
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("ModCustMDMPubInfo");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Modify");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setUsername(sMakerID);
			Header_Input.setCredentials(loggedinuser);
			
			reqMessage.setHeader(Header_Input);
			
			String customerInformation = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
			"<KYCDetails>"+
				"<dateOfAttainingMaturity>"+dateOfMaturity+"</dateOfAttainingMaturity>"+
				"<permAddBuildingName>"+addBuildName+"</permAddBuildingName>"+
				"<permAddVillaFlatNo>"+permAddVillaFlatNo+"</permAddVillaFlatNo>"+
				"<permAddStreetNoNameLandmark>"+permAddStreetNoNameLandmark+"</permAddStreetNoNameLandmark>"+
				"<permAddState>"+permAddState+"</permAddState>"+
				"<permAddCountry>"+permAddCountry+"</permAddCountry>"+
				"<permAddCity>"+permAddCity+"</permAddCity>"+
				"<visaStatus>"+visaStatus+"</visaStatus>"+
				"<passportType>"+passportType+"</passportType>"+
				"<dateOfKYCPreparation>"+dateOfKYCPreparation+"</dateOfKYCPreparation>"+
				"<existingRelationshipSince>"+existingRelationshipSince+"</existingRelationshipSince>"+
				"<accountNoCovered>"+accountNoCovered+"</accountNoCovered>"+
				"<purposeForAccountReltnship>"+purposeForAccountReltnship+"</purposeForAccountReltnship>"+
				"<companyCode>"+companyCode+"</companyCode>"+
				"<finActivitiesCountries>"+finActivitiesCountries+"</finActivitiesCountries>"+
				"<natureOfActivityOfIntlFTs>"+natureOfActivityOfIntlFTs+"</natureOfActivityOfIntlFTs>"+
				"<expctdMonDepCash>"+expctdMonDepCash+"</expctdMonDepCash>"+
				"<expctdMonDepCheque>"+expctdMonDepCheque+"</expctdMonDepCheque>"+
				"<expctdMonDepTransfer>"+expctdMonDepTransfer+"</expctdMonDepTransfer>"+
				"<expctdMonDepInvest>"+expctdMonDepInvest+"</expctdMonDepInvest>"+
				"<expctdMonDepCashSOF></expctdMonDepCashSOF>"+
				"<expctdMonDepChequeSOF></expctdMonDepChequeSOF>"+
				"<expctdMonDepTransferSOF></expctdMonDepTransferSOF>"+
			    "<expctdMonDepInvestSOF></expctdMonDepInvestSOF>"+
			    "<expctdMonDepCashNOT></expctdMonDepCashNOT>"+
			    "<expctdMonDepChequeNOT></expctdMonDepChequeNOT>"+
			    "<expctdMonDepTransferNOT></expctdMonDepTransferNOT>"+
			    "<expctdMonDepInvestNOT></expctdMonDepInvestNOT>"+
				"<expctdMonWthdrlCash>"+expctdMonWthdrlCash+"</expctdMonWthdrlCash>"+
				"<expctdMonWthdrlCheque>"+expctdMonWthdrlCheque+"</expctdMonWthdrlCheque>"+
				"<expctdMonWthdrlTransfer>"+expctdMonWthdrlTransfer+"</expctdMonWthdrlTransfer>"+
				"<expctdMonWthdrlInvest>"+expctdMonWthdrlInvest+"</expctdMonWthdrlInvest>"+
				"<expctdMonWthdrlCashDOF></expctdMonWthdrlCashDOF>"+
				"<expctdMonWthdrlChequeDOF></expctdMonWthdrlChequeDOF>"+
				"<expctdMonWthdrlTransferDOF></expctdMonWthdrlTransferDOF>"+
				"<expctdMonWthdrlInvestDOF></expctdMonWthdrlInvestDOF>"+
				"<dealsInArmaments>"+dealsInArmaments+"</dealsInArmaments>"+
				"<customerHawalaFlag>"+sHawala+"</customerHawalaFlag>"+
				"<customerPEPFlag>"+customerPEPFlag+"</customerPEPFlag>"+
				"<customerUAEResidentFlag>"+customerUAEResidentFlag+"</customerUAEResidentFlag>"+
				"<detailRealEstate>"+detailRealEstate+"</detailRealEstate>"+
				"<estimatedCMVRealEstate></estimatedCMVRealEstate>"+
				"<estimatedMIRealEstate></estimatedMIRealEstate>"+
				"<detailInvestment></detailInvestment>"+
				"<estimatedCMVInvestment></estimatedCMVInvestment>"+
				"<estimatedMIInvestment>12312</estimatedMIInvestment>"+
				"<detailCASH></detailCASH>"+
				"<estimatedCMVCASH></estimatedCMVCASH>"+
				"<estimatedMICASH></estimatedMICASH>"+
				"<detailNetWorth></detailNetWorth>"+
				"<estimatedCMVNetWorth></estimatedCMVNetWorth>"+
				"<estimatedMINetWorth></estimatedMINetWorth>"+
				"<relationship1BankName>"+relationship1BankName+"</relationship1BankName>"+
				"<relationship2BankName>"+relationship2BankName+"</relationship2BankName>"+
				"<relationship3BankName>"+relationship3BankName+"</relationship3BankName>"+
				"<relationship1Address>"+relationship1Address+"</relationship1Address>"+
				"<relationship2Address>"+relationship2Address+"</relationship2Address>"+
				"<relationship3Address>"+relationship3Address+"</relationship3Address>"+
				"<relationship1AccountNumber></relationship1AccountNumber>"+
				"<relationship2AccountNumber></relationship2AccountNumber>"+
				"<relationship3AccountNumber></relationship3AccountNumber>"+
				"<customerTypeOldFlag>"+customerTypeOldFlag+"</customerTypeOldFlag>"+
				"<customerTypeNewFlag>"+customerTypeNewFlag+"</customerTypeNewFlag>"+				
				"<lastCategoryChangeDate>"+lastCategoryChangeDate+"</lastCategoryChangeDate>"+
				"<membershipWith>"+membershipWith+"</membershipWith>"+
				"<privilegeBenefits>"+privilegeBenefits+"</privilegeBenefits>"+
				"<excellencyBenefits>"+excellencyBenefits+"</excellencyBenefits>"+
//				"<dealsInWMD>"+dealsInWMD+"</dealsInWMD>"+
//				"<salaryTransfer>"+salaryTransfer+"</salaryTransfer>"+
				"<addVerNameOfTheClient></addVerNameOfTheClient>"+
				"<addVerConfirmationOf></addVerConfirmationOf>"+
				"<addVerAddressVerified></addVerAddressVerified>"+
				"<addVerDateOfVisit></addVerDateOfVisit>"+
				"<addVerReasonForNonVer></addVerReasonForNonVer>"+
				"<salaryAccountMaintainedWith>"+customerId+"</salaryAccountMaintainedWith>"+
				"<onshoreOffshoreFlag>"+sProcessType+"</onshoreOffshoreFlag>"+
			"</KYCDetails>";

			reqType.setCustomerId(customerId);
			reqType.setCustomerInfoType("KYCDetails");
			reqType.setMaintenanceOption(sMaintenanceOption);
			reqType.setCustomerInformation(customerInformation);
			reqMessage.setModCustMDMPubInfoReq(reqType);
			
			
			xmlInput=stub.getinputXML(reqMessage);
//			LogGEN.writeTrace("CBG_Log", "SaveKYCDetails xmlInput: "+xmlInput);
			resMessage = stub.modCustMDMPubInfo_Oper(reqMessage);	
			sOrg_Output=stub.resEidaMsg;//Added By Harish For inserting original mssg
//			LogGEN.writeTrace("CBG_Log", "SaveKYCDetails sOrg_Output: "+sOrg_Output);
			Header_Input = resMessage.getHeader();
			sReturnCode= Header_Input.getReturnCode();
			sErrorDetail=Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();

			LogGEN.writeTrace("CBG_Log", "Return Code---"+sReturnCode);
			LogGEN.writeTrace("CBG_Log", "Error Detail---"+sErrorDetail);
			LogGEN.writeTrace("CBG_Log", "Error Description---"+sErrorDesc);
			
			
			if (sReturnCode.equalsIgnoreCase("0") && sErrorDesc.equalsIgnoreCase("Success"))
			{
				resType = resMessage.getModCustMDMPubInfoRes();
				reason = resType.getReason();
				status = resType.getStatus();			
				
				sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
				"<Output>" +
				"<Option>ModCustMDMPubInfo</Option>" +
				"<returnCode>"+sReturnCode+"</returnCode>" +
				"<errorDesc>"+sErrorDesc+"</errorDesc>" +
				"<errorDescription>"+sErrorDetail+"</errorDescription>"+
				"<Reason>"+reason+"</Reason>" +
				"<customerId>"+resType.getCustomerId()+"</customerId>"+
				"<customerInfoType>"+resType.getCustomerInfoType()+"</customerInfoType>"+
				"<customerInformation>"+resType.getCustomerInformation()+"</customerInformation>"+
				"<Status>"+status+"</Status>" +											
				"</Output>";
			}
			else
			{
				sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
				"<Output>" +
				"<Option>ModCustMDMPubInfo</Option>" +
				"<returnCode>"+sReturnCode+"</returnCode>" +
				"<errorDesc>"+sErrorDesc+"</errorDesc>" +
				"<errorDescription>"+sErrorDetail+"</errorDescription>"+
				"</Output>";
			}	
		}
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>ModCustMDMPubInfo</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to Save KYC Details.</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>ModCustMDMPubInfo</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to Save KYC Details.</td></Output>";
			}

			String Status="";
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
			{
			Status="Success";
			}
			else
			{
			Status="Failure";
			}

				try 
				{
					//sHandler.readCabProperty("JTS");
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
				
				String dburl=(String)currentCabPropertyMap.get("DBURL");
				String dbuser=(String)currentCabPropertyMap.get("USER");
				String dbpass=(String)currentCabPropertyMap.get("PASS");
				
				
				String inputXml=(xmlInput.replaceAll("&lt;", "<")).replaceAll("&gt;", ">");
				String winame=xmlDataParser.getValueOf("WiName");
				String sessionID= xmlDataParser.getValueOf("SessionId");
				String call_type=xmlDataParser.getValueOf("DSCOPCallType");
				sCabinet=xmlDataParser.getValueOf("EngineName");
				dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				
				/*String outputxml=sOutput;
				String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
				"and randomnumber='"+ sessionID +"'),'"+call_type+"','"+ inputXml.replaceAll("'", "''") +"',to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
				"'"+ outputxml.replaceAll("'", "''") +"',sysdate,'"+  Status + "')";
				
				LogGEN.writeTrace("CBG_Log",Query);*/
				
				try
				{
					dbpass=AESEncryption.decrypt(dbpass);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
				DBConnection con=new DBConnection();
				 /**
				  * //Added By Harish For inserting original mssg
				  * Date			:	17 Mar 2015
				  * Description 	:	Replace execute with executeClob
				  */
					 String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
						"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
						"?,sysdate,'"+  Status + "')";
					 LogGEN.writeTrace("CBG_Log"," Add  Query : finally :"+Query);
					 LogGEN.writeTrace("CBG_Log","sOrg_Output : finally :"+sOrg_Output);
					 try {
						 con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_Output.replaceAll("'", "''"));
					} catch (Exception e2) {
						// TODO: handle exception
						e2.getMessage();
					}
				return sOutput;
		}
	}
}
