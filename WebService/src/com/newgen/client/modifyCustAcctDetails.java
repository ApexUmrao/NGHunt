package com.newgen.client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.stub.ModCustAcctDetailsStub;
import com.newgen.stub.ModCustAcctDetailsStub.AccountStatusUpdate_type1;
import com.newgen.stub.ModCustAcctDetailsStub.CustomerRelationUpdate_type0;
import com.newgen.stub.ModCustAcctDetailsStub.CustomerRelationUpdate_type1;
import com.newgen.stub.ModCustAcctDetailsStub.HeaderType;
import com.newgen.stub.ModCustAcctDetailsStub.MemoDetailsUpdate_type0;
import com.newgen.stub.ModCustAcctDetailsStub.MemoDetailsUpdate_type1;
import com.newgen.stub.ModCustAcctDetailsStub.ModCustAcctDetailsReqChoice_type1;
import com.newgen.stub.ModCustAcctDetailsStub.ModCustAcctDetailsReqMsg;
import com.newgen.stub.ModCustAcctDetailsStub.ModCustAcctDetailsReq_type0;
import com.newgen.stub.ModCustAcctDetailsStub.ModCustAcctDetailsResChoice_type0;
import com.newgen.stub.ModCustAcctDetailsStub.ModCustAcctDetailsResMsg;
import com.newgen.stub.ModCustAcctDetailsStub.ModCustAcctDetailsRes_type0;
import com.newgen.stub.ModCustAcctDetailsStub.ServiceChargesUpdate_type0;
import com.newgen.stub.ModCustAcctDetailsStub.ServiceChargesUpdate_type1;
import com.newgen.stub.ModCustAcctDetailsStub.StandingInstructionUpdate_type0;
import com.newgen.stub.ModCustAcctDetailsStub.StandingInstructionUpdate_type1;
import com.newgen.stub.modifyCustSig;
import com.newgen.stub.modifyCustSig.CustomerSignatureUpdate_type1;
public class modifyCustAcctDetails extends WebServiceHandler 
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	 String sOrg_Output=null;//Added By Harish For inserting original mssg
	/**
	 * Function written to fetch customer information
	 * @author gupta.ashish
	 * @param sInputXML
	 * @return
	 */

		@SuppressWarnings("finally")
		public String modify_account(String sInputXML) 
		{
			LogGEN.writeTrace("Log", "Fuction called Modify_Account");
			LogGEN.writeTrace("Log", "sInputXML---"+sInputXML);
			XMLParser xmlDataParser = new XMLParser();
			xmlDataParser.setInputXML(sInputXML);
			String sOutput="";
			String sReturnCode= "";
			String sErrorDetail="";
			String sErrorDesc = "";
			String xmlInput="";
			WebServiceHandler sHandler= new WebServiceHandler();
			Date d= new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String sDate = dateFormat.format(d);
			String call_type="";
			try
			{
				
				sHandler.readCabProperty("Modify_Account");				
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
				
				String sCustomerID= xmlDataParser.getValueOf("CUST_ID");
				String ref_no=xmlDataParser.getValueOf("REF_NO");
				call_type=xmlDataParser.getValueOf("ServiceName");
				LogGEN.writeTrace("Log", "sCustomerID---"+sCustomerID);
				
				
				LogGEN.writeTrace("Log", "sDate---"+sDate);
				
				ModCustAcctDetailsStub acc_stub=new ModCustAcctDetailsStub(sWSDLPath);
				ModCustAcctDetailsReqMsg acc_Req_Msg = new ModCustAcctDetailsReqMsg();
				ModCustAcctDetailsReq_type0 acc_Req_Type=new ModCustAcctDetailsReq_type0();				
				ModCustAcctDetailsResMsg acc_Rep_Msg=new ModCustAcctDetailsResMsg();
				
				modifyCustSig sig_stub=new modifyCustSig(sWSDLPath);
				modifyCustSig.ModCustAcctDetailsReqMsg sig_req_msg=new modifyCustSig.ModCustAcctDetailsReqMsg();
				modifyCustSig.ModCustAcctDetailsReq_type0 acc_Req_Type_sig=new modifyCustSig.ModCustAcctDetailsReq_type0();				
				String acc_Rep_Msg_sig="";
				
			
				HeaderType Header_Input = new HeaderType();
				modifyCustSig.HeaderType Header_Input_sig = new modifyCustSig.HeaderType();
				LogGEN.writeTrace("Log", "All Objects created");
				
				Header_Input.setUsecaseID("1234");
				Header_Input.setServiceName("ModCustAcctDetails");
				Header_Input.setVersionNo("1.0");
				Header_Input.setServiceAction("Modify");
				Header_Input.setSysRefNumber(ref_no);
				Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
				Header_Input.setReqTimeStamp(sDate);
				Header_Input.setUsername(sCustomerID);
				Header_Input.setCredentials(loggedinuser);	
				
				
				Header_Input_sig.setUsecaseID("1234");
				Header_Input_sig.setServiceName("ModCustAcctDetails");
				Header_Input_sig.setVersionNo("1.0");
				
				if(call_type.equalsIgnoreCase("customerSignatureUpdate"))
					if(xmlDataParser.getValueOf("sigInq").equalsIgnoreCase("Y"))
						Header_Input_sig.setServiceAction("Inquiry");
					else
						Header_Input_sig.setServiceAction("Modify");
				else
					Header_Input_sig.setServiceAction("Modify");
					
				Header_Input_sig.setSysRefNumber(ref_no);
				Header_Input_sig.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
				Header_Input_sig.setReqTimeStamp(sDate);
				Header_Input_sig.setUsername(sCustomerID);
				Header_Input_sig.setCredentials(loggedinuser);	
				
				LogGEN.writeTrace("Log", "custAccountNumber---"+xmlDataParser.getValueOf("custAccountNumber"));
				acc_Req_Type.setCustAccountNumber(xmlDataParser.getValueOf("custAccountNumber"));
				acc_Req_Type.setCustomerId(xmlDataParser.getValueOf("customerId"));

				acc_Req_Type.setMaintenanceType(call_type);
				acc_Req_Type.setMaintenanceOption(xmlDataParser.getValueOf("maintenanceOption"));
				acc_Req_Type.setCheckerId("WMSUSER");
				acc_Req_Type.setMakerId("WMSUSER");
				acc_Req_Type.setUpdateSerialNo("");
				
				
				acc_Req_Type_sig.setCustAccountNumber(xmlDataParser.getValueOf("custAccountNumber"));
				acc_Req_Type_sig.setCustomerId(xmlDataParser.getValueOf("customerId"));
				acc_Req_Type_sig.setMaintenanceType(call_type);
				acc_Req_Type_sig.setMaintenanceOption(xmlDataParser.getValueOf("maintenanceOption"));
				acc_Req_Type_sig.setCheckerId("WMSUSER");
				acc_Req_Type_sig.setMakerId("WMSUSER");
				acc_Req_Type_sig.setUpdateSerialNo("");
				
				
				ModCustAcctDetailsReqChoice_type1 choice=new ModCustAcctDetailsReqChoice_type1();
				AccountStatusUpdate_type1 acc_up=new AccountStatusUpdate_type1();
				ServiceChargesUpdate_type1 ser_chr=new ServiceChargesUpdate_type1();
				StandingInstructionUpdate_type1 stand=new StandingInstructionUpdate_type1();
				CustomerRelationUpdate_type1 cust_rel=new CustomerRelationUpdate_type1();
				MemoDetailsUpdate_type1 memo=new MemoDetailsUpdate_type1();
				CustomerSignatureUpdate_type1 sig=new CustomerSignatureUpdate_type1();
				
				
				com.newgen.stub.modifyCustSig.ModCustAcctDetailsReqChoice_type1 sigchoice=new com.newgen.stub.modifyCustSig.ModCustAcctDetailsReqChoice_type1();
				if(call_type.equalsIgnoreCase("accountStatusUpdate"))
				{
					String Record_Status=xmlDataParser.getValueOf("Record_Status");
					String No_Debit=xmlDataParser.getValueOf("No_Debit");
					String No_Credit=xmlDataParser.getValueOf("No_Credit");
					String Dormant=xmlDataParser.getValueOf("Dormant");
					String Frozen=xmlDataParser.getValueOf("Frozen");
					
					acc_up.setRecordStatus(Record_Status);
					acc_up.setFlagNoCredit(No_Credit);
					acc_up.setFlagNoDebit(No_Debit);
					acc_up.setFlagAcctDormant(Dormant);
					acc_up.setFlagAcctFrozen(Frozen);
					LogGEN.writeTrace("Log","No_Credit:"+No_Credit);
					LogGEN.writeTrace("Log","No_Debit:"+No_Debit);
					LogGEN.writeTrace("Log","Dormant:"+Dormant);
					LogGEN.writeTrace("Log","Frozen:"+Frozen);
					
					//acc_up.setAccountNewStatus(xmlDataParser.getValueOf("accountNewStatus"));
					//acc_up.setAccountPrevStatus(xmlDataParser.getValueOf("accountPrevStatus"));
					acc_up.setStatusChangeReason(xmlDataParser.getValueOf("statusChangeReason"));
					//acc_up.setStatusChangeDate(xmlDataParser.getValueOf("statusChangeDate"));
					acc_up.setStatusChangeDate(sDate);
					choice.setAccountStatusUpdate(acc_up);
				}
				if(call_type.equalsIgnoreCase("customerSignatureUpdate"))
				{
					//LogGEN.writeTrace("Log","inside signature upload");
					
					sig.setImageType(xmlDataParser.getValueOf("imageType"));
					sig.setImageAddDate(xmlDataParser.getValueOf("imageAddDate"));
					//javax.activation.DataHandler data=new DataHandler(xmlDataParser.getValueOf("imageBinaryData"), "");
					sig.setImageBinaryData(xmlDataParser.getValueOf("imageBinaryData"));				
					
					sigchoice.setCustomerSignatureUpdate(sig);
				}
				if(call_type.equalsIgnoreCase("memoDetailsUpdate"))
				{
					memo.setMemoText(xmlDataParser.getValueOf("MemoText"));
					memo.setMemoType(xmlDataParser.getValueOf("MemoType"));
					memo.setMemoSeverity(xmlDataParser.getValueOf("MemoSeverity"));
					memo.setMemoDate(xmlDataParser.getValueOf("MemoDate"));
					
					choice.setMemoDetailsUpdate(memo);
				}
				else if(call_type.equalsIgnoreCase("customerRelationUpdate"))
				{
					
					cust_rel.setRelatedCustomerId(xmlDataParser.getValueOf("RelatedCustomerId"));
					cust_rel.setCustomerRelationship(xmlDataParser.getValueOf("CustomerRelationship"));
					cust_rel.setInvRelationship(xmlDataParser.getValueOf("InvRelationship"));
					choice.setCustomerRelationUpdate(cust_rel);
				
				}
				else if(call_type.equalsIgnoreCase("serviceChargesUpdate"))
				{
					ser_chr.setSCCustAccountLevel(xmlDataParser.getValueOf("SCCustAccountLevel"));
					ser_chr.setSCPackageNewValue(xmlDataParser.getValueOf("SCPackageNewValue"));
					ser_chr.setSCPackageOldValue(xmlDataParser.getValueOf("SCPackageOldValue"));
					ser_chr.setSCWaiverFlag(xmlDataParser.getValueOf("SCWaiverFlag"));
					choice.setServiceChargesUpdate(ser_chr);
				}			
				if(call_type.equalsIgnoreCase("standingInstructionUpdate"))
				{
					stand.setSIAmount(xmlDataParser.getValueOf("SIAmount"));
					stand.setSIBenefAccountBrCode(xmlDataParser.getValueOf("SIBenefAccountBrCode"));
					stand.setSIBenefAccountNo(xmlDataParser.getValueOf("SIBenefAccountNo"));
					stand.setSIBenefCurrency(xmlDataParser.getValueOf("SIBenefCurrency"));					
					stand.setSIEndDate(xmlDataParser.getValueOf("SIEndDate"));
					stand.setSIForceDebitFlag(xmlDataParser.getValueOf("SIForceDebitFlag"));
					//stand.setSIFrequency(xmlDataParser.getValueOf("SIFrequency"));
					
					stand.setSIInstrcutionNo(xmlDataParser.getValueOf("SIInstrcutionNo"));
					stand.setSIMaxRetriesCount(xmlDataParser.getValueOf("SIMaxRetriesCount"));
					stand.setSINarration(xmlDataParser.getValueOf("SINarration"));
					stand.setSINextDate(xmlDataParser.getValueOf("SINextDate"));
					stand.setSIPriority(xmlDataParser.getValueOf("SIPriority"));
					stand.setSISCCodeFailure(xmlDataParser.getValueOf("SISCCodeFailure"));
					stand.setSISCCodeSuccess(xmlDataParser.getValueOf("SISCCodeSuccess"));
					stand.setSISCCodeFailure(xmlDataParser.getValueOf("SISCCurrencyFailure"));
					stand.setSISCCodeSuccess(xmlDataParser.getValueOf("SISCCurrencySuccess"));
					stand.setSIStartDate(xmlDataParser.getValueOf("SIStartDate"));
					stand.setSIType(xmlDataParser.getValueOf("SIType"));
					choice.setStandingInstructionUpdate(stand);
				}	
				acc_Req_Type.setModCustAcctDetailsReqChoice_type1(choice);
				
				acc_Req_Type_sig.setModCustAcctDetailsReqChoice_type1(sigchoice);
				
				acc_Req_Msg.setHeader(Header_Input);
				sig_req_msg.setHeader(Header_Input_sig);
				
				acc_Req_Msg.setModCustAcctDetailsReq(acc_Req_Type);
				sig_req_msg.setModCustAcctDetailsReq(acc_Req_Type_sig);
				
				
				
				LogGEN.writeTrace("Log","before calling function");
				if(!call_type.equalsIgnoreCase("customerSignatureUpdate"))
				{
					xmlInput=acc_stub.getinputXML(acc_Req_Msg);
					acc_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
					acc_Rep_Msg=acc_stub.modCustAcctDetails_Oper(acc_Req_Msg);
					sOrg_Output=acc_stub.resModCustAccMsg;//Added By Harish For inserting original mssg
				}	
				else
				{
					xmlInput=sig_stub.getinputXML(sig_req_msg);
					sig_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
					acc_Rep_Msg_sig=sig_stub.modCustAcctDetails_Oper(sig_req_msg);
					sOrg_Output=acc_Rep_Msg_sig;
				}
				
				   LogGEN.writeTrace("Log", "All values set");
			    
				   LogGEN.writeTrace("Log", "Mohit input:"+acc_stub.getinputXML(acc_Req_Msg));
			  // System.out.println(acc_stub.getinputXML(acc_Req_Msg));
			
			   LogGEN.writeTrace("Log","sdafasdfasdfsfsdf");
			    if(call_type.equalsIgnoreCase("customerSignatureUpdate"))
			    {
			    	try
			    	{
				    	LogGEN.writeTrace("Log","21312312313123123123131231233123");
				    	LogGEN.writeTrace("Log",acc_Rep_Msg_sig);
				    	sReturnCode=acc_Rep_Msg_sig.substring(acc_Rep_Msg_sig.indexOf("<ns1:returnCode>")+16,acc_Rep_Msg_sig.indexOf("</ns1:returnCode>"));
				    	sErrorDetail=acc_Rep_Msg_sig.substring(acc_Rep_Msg_sig.indexOf("<ns1:errorDetail>")+17,acc_Rep_Msg_sig.indexOf("</ns1:errorDetail>"));
				    	System.out.println("message:"+acc_Rep_Msg_sig);
				    	sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
							"<Option>ADCB_ADDCUST</Option>" +
							"<returnCode>"+acc_Rep_Msg_sig.substring(acc_Rep_Msg_sig.indexOf("<ns1:returnCode>")+16,acc_Rep_Msg_sig.indexOf("</ns1:returnCode>"))+"</returnCode>" +
							"<errorDescription>"+acc_Rep_Msg_sig.substring(acc_Rep_Msg_sig.indexOf("<ns1:errorDetail>")+17,acc_Rep_Msg_sig.indexOf("</ns1:errorDetail>"))+"</errorDescription>";				
				    	  	sOutput+="<Reason>"+acc_Rep_Msg_sig.substring(acc_Rep_Msg_sig.indexOf("<ns0:reason>")+12,acc_Rep_Msg_sig.indexOf("</ns0:reason>")) +"</Reason>"+
				    	  	"<imageType>"+acc_Rep_Msg_sig.substring(acc_Rep_Msg_sig.indexOf("<ns0:imageType>")+12,acc_Rep_Msg_sig.indexOf("</ns0:imageType>")) +"</imageType>"+
				    	"<Status>"+acc_Rep_Msg_sig.substring(acc_Rep_Msg_sig.indexOf("<ns0:status>")+12,acc_Rep_Msg_sig.indexOf("</ns0:status>")) +"</Status></Output>";
						LogGEN.writeTrace("Log", sOutput);
			    	}
			    	catch(Exception ee)
			    	{
			    		Header_Input= acc_Rep_Msg.getHeader();
					    sReturnCode= Header_Input.getReturnCode();
					    sErrorDetail=Header_Input.getErrorDetail();
					    sErrorDesc = Header_Input.getErrorDescription();
					    sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Modify_Account</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to modify Signature.</td></Output>";

			    	}
			    }
			    else
			    {
			    	LogGEN.writeTrace("Log","3453453453453453453");
			    	Header_Input= acc_Rep_Msg.getHeader();
				    sReturnCode= Header_Input.getReturnCode();
				    sErrorDetail=Header_Input.getErrorDetail();
				    sErrorDesc = Header_Input.getErrorDescription();
				    
				    LogGEN.writeTrace("Log", "Return Code---"+sReturnCode);
				    LogGEN.writeTrace("Log", "Error Detail---"+sErrorDetail);
				    LogGEN.writeTrace("Log", "Error Description---"+sErrorDesc);
				    
				    System.out.println(sErrorDetail);
				    
				    ModCustAcctDetailsRes_type0 res_1= new ModCustAcctDetailsRes_type0();
				    res_1=acc_Rep_Msg.getModCustAcctDetailsRes();
				    
				    if(!sErrorDesc.equalsIgnoreCase("Failure") && (!sReturnCode.equalsIgnoreCase("1") ))
					{		
				    	sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
							"<Option>ADCB_ADDCUST</Option>" +
							"<returnCode>"+sReturnCode+"</returnCode>" +
							"<errorDescription>"+sErrorDetail+"</errorDescription>";
							
							
				    	ModCustAcctDetailsResChoice_type0 res=new ModCustAcctDetailsResChoice_type0();
				    	res=res_1.getModCustAcctDetailsResChoice_type0();
				    	//res=acc_Rep_Msg.getModCustAcctDetailsRes();
				    	sOutput+="<Reason>"+res_1.getReason() +"</Reason>"+
				    	"<Status>"+res_1.getStatus() +"</Status>";
						
				    	System.out.println(sOutput);
				    	LogGEN.writeTrace("Log", "Successful Result");
				    	if(call_type.equalsIgnoreCase("accountStatusUpdate"))
						{
				    		sOutput+="<AccStatusUpdateRes>"+
							"<accountNewStatus>"+ acc_up.getRecordStatus()+acc_up.getFlagNoDebit()+acc_up.getFlagNoCredit()+acc_up.getFlagAcctDormant()+acc_up.getFlagAcctFrozen()+"</accountNewStatus>"+
							//"<accountPrevStatus>"+acc_up.getAccountPrevStatus() +"</accountPrevStatus>"+
							"<accountPrevStatus></accountPrevStatus>"+
							"<statusChangeReason>"+acc_up.getStatusChangeReason() +"</statusChangeReason>"+
							"</AccStatusUpdateRes>";
							
						}	
				    	if(call_type.equalsIgnoreCase("memoDetailsUpdate"))
						{
				    		MemoDetailsUpdate_type0 m=new MemoDetailsUpdate_type0();
				    		m=res.getMemoDetailsUpdate();
				    		if(!(m==null))
				    		{
					    		sOutput+="<MemoRes>"+
								"<MemoType>"+ m.getMemoType() +"</MemoType>"+
								"<MemoText>"+m.getMemoText() +"</MemoText>"+
								"<MemoSeverity>"+m.getMemoSeverity() +"</MemoSeverity>"+
								"<MemoDate>"+m.getMemoDate() +"</MemoDate>"+
								"</MemoRes>";
				    		}
						}
				    	else if(call_type.equalsIgnoreCase("customerRelationUpdate"))
				    	{
				    		CustomerRelationUpdate_type0 cust_r=new CustomerRelationUpdate_type0();
				    		cust_r=res.getCustomerRelationUpdate();
				    		sOutput+="<CustomerRelationRes>"+
							"<RelatedCustomerId>"+ cust_r.getRelatedCustomerId()+"</RelatedCustomerId>"+
							"<CustomerRelationship>"+cust_r.getCustomerRelationship() +"</CustomerRelationship>"+
							"<InvRelationship>"+cust_r.getInvRelationship() +"</InvRelationship>"+
							"</CustomerRelationRes>";
				    	}
						else if(call_type.equalsIgnoreCase("serviceChargesUpdate"))
						{
							ServiceChargesUpdate_type0 ser_chr1=new ServiceChargesUpdate_type0();
							 ser_chr1=res.getServiceChargesUpdate();
							 sOutput+="<serviceChargesUpdateRes>"+ 
							"<SCCustAccountLevel>"+ ser_chr1.getSCCustAccountLevel() +"</SCCustAccountLevel>"+
							"<SCPackageNewValue>"+ser_chr1.getSCPackageNewValue()+"</SCPackageNewValue>"+
							"<SCPackageOldValue>"+ser_chr1.getSCPackageOldValue()+"</SCPackageOldValue>"+
							"<SCWaiverFlag>"+ser_chr1.getSCWaiverFlag()+"</SCWaiverFlag>"+
							"<serviceChargesUpdateRes>"; 
						}			
						if(call_type.equalsIgnoreCase("standingInstructionUpdate"))
						{
							StandingInstructionUpdate_type0 stand1=new StandingInstructionUpdate_type0();
							stand1=res.getStandingInstructionUpdate();
							sOutput+="<standingInstructionUpdateRes>"+ 
							"<SIAmount>"+stand1.getSIAmount() +"</SIAmount>"+
							"<SIBenefAccountBrCode>"+ stand1.getSIBenefAccountBrCode() +"</SIBenefAccountBrCode>"+
							"<SIBenefAccountNo>"+ stand1.getSIBenefAccountNo()+"</SIBenefAccountNo>"+
							"<SIBenefCurrency>"+ stand1.getSIBenefCurrency() +"</SIBenefCurrency>"+					
							"<SIEndDate>"+ stand1.getSIEndDate() +"</SIEndDate>"+
							"<SIForceDebitFlag>"+ stand1.getSIForceDebitFlag() +"</SIForceDebitFlag>"+
						//	"<SIFrequency>"+ stand1.getSIFrequency()+"</SIFrequency>"+
							"<SIFrequency></SIFrequency>"+
							"<SIInstrcutionNo>"+ stand1.getSIInstrcutionNo()+"</SIInstrcutionNo>"+
							"<SIMaxRetriesCount>"+ stand1.getSIMaxRetriesCount()+"</SIMaxRetriesCount>"+
							"<SINarration>"+ stand1.getSINarration()+"</SINarration>"+
							"<SINextDate>"+ stand1.getSINextDate()+"</SINextDate>"+
							"<SIPriority>"+ stand1.getSIPriority()+"</SIPriority>"+
							"<SISCCodeFailure>"+ stand1.getSISCCodeFailure() +"</SISCCodeFailure>"+
							"<SISCCodeSuccess>"+ stand1.getSISCCodeSuccess()+"</SISCCodeSuccess>"+
							//"<SISCCurrencyFailure>"+stand1.getSISCCurrencyFailure()+"</SISCCurrencyFailure>"+
							//"<SISCCurrencySuccess>"+stand1.getSISCCurrencySuccess()+"</SISCCurrencySuccess>"+
							"<SISCCurrencyFailure></SISCCurrencyFailure>"+
							"<SISCCurrencySuccess></SISCCurrencySuccess>"+
							"<SIStartDate>"+ stand1.getSIStartDate() +"</SIStartDate>"+
							"<SIType>"+ stand1.getSIType()+"</SIType></standingInstructionUpdateRes>";
							
						}					
				    	sOutput+="</Output>";
						LogGEN.writeTrace("Log", "Output XML--- "+sOutput);
					}
				    else
					{
				    	LogGEN.writeTrace("Log", "Failed");
				    	try
				    	{
				    		sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Modify_Account</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Reason>"+res_1.getStatus()+"</Reason><Status>"+res_1.getReason()+"</Status><td>Unable to modify account.</td></Output>";
				    	}
				    	catch(Exception e)
				    	{
				    		sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Modify_Account</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to modify account.</td></Output>";
	
				    	}
					}
			    }
			    
			    
			    
			    
			}
			catch (Exception e)
			{
				LogGEN.writeTrace("Log", "Error in Web Serviice :"+e.toString());
				LogGEN.writeTrace("Log", "Error Trace in Web Serviice :"+e.getStackTrace());	
				sReturnCode="-1";
				sErrorDetail=e.getMessage();
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Modify_Account</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to modify account.</td></Output>";
				e.printStackTrace();
			}
			finally
			{
				LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput.trim().length());				
				if(sOutput.trim().length()<1)
				{
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Modify_Account</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to modify account.</td></Output>";
				}
				
				LogGEN.writeTrace("Log","outputXML : finally :"+sOutput);
				
				String Status="";
				if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
				{
					Status="Success";
				}
				else
					Status="Failure";
				
				
				 try {
					sHandler.readCabProperty("JTS");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				 
					String dburl=(String)currentCabPropertyMap.get("DBURL");
					String dbuser=(String)currentCabPropertyMap.get("USER");
					String dbpass=(String)currentCabPropertyMap.get("PASS");
					
					
					String inputXml=xmlInput;
					LogGEN.writeTrace("Log", inputXml);
					String winame=xmlDataParser.getValueOf("WiName");
					String sessionID= xmlDataParser.getValueOf("SessionId");
					String call_type1=xmlDataParser.getValueOf("Calltype");
					sCabinet=xmlDataParser.getValueOf("EngineName");
					dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					 
					 try
					 {
						 dbpass=AESEncryption.decrypt(dbpass);
					 }
					 catch(Exception e)
					 {
						e.getMessage(); 
					 }
					 DBConnection con=new DBConnection();
					/* String Query="insert into usr_0_other_calls_web_log(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
						"and randomnumber='"+ sessionID +"'),'"+call_type1+"','"+ inputXml.replaceAll("'", "''") +"',to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
						"'"+ outputxml.replaceAll("'", "''") +"',sysdate,'"+  Status + "')";*/

						
					 /**
					  * //Added By Harish For inserting original mssg
					  * Date			:	17 Mar 2015
					  * Description 	:	Replace execute with executeClob
					  */
						 String Query="insert into usr_0_other_calls_web_log(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
							"and randomnumber='"+ sessionID +"'),'"+call_type1+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
							"?,sysdate,'"+  Status + "')";
						 LogGEN.writeTrace("Log"," Add  Query : finally :"+Query);
						 LogGEN.writeTrace("Log","sOrg_Output : finally :"+sOrg_Output);
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
