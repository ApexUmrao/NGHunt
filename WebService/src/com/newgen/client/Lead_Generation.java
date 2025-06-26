package com.newgen.client;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.newgen.stub.AddLeadResponseStub;
import com.newgen.stub.AddLeadResponseStub.AddLeadResponseReqMsg;
import com.newgen.stub.AddLeadResponseStub.AddLeadResponseReq_type0;
import com.newgen.stub.AddLeadResponseStub.AddLeadResponseResMsg;
import com.newgen.stub.AddLeadResponseStub.AddLeadResponseRes_type0;
import com.newgen.stub.AddLeadResponseStub.HeaderType;


public class Lead_Generation extends WebServiceHandler
{


	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	@SuppressWarnings("finally")
	public String Generate_Lead(String sInputXML) 
	{
	
		LogGEN.writeTrace("Log", "Fuction called Lead_Generation");
		LogGEN.writeTrace("Log", "sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";
		String sOutput= "";
		
		/*String accountTitle=xmlDataParser.getValueOf("accountTitle");
		String customerTypeCode=xmlDataParser.getValueOf("customerTypeCode");
		String productCode=xmlDataParser.getValueOf("productCode");
		String customerBranchCode=xmlDataParser.getValueOf("customerBranchCode");
		String accountStatusCode=xmlDataParser.getValueOf("accountStatusCode");
		String flagEmpAccount=xmlDataParser.getValueOf("flagEmpAccount");
		String flagATM=xmlDataParser.getValueOf("flagATM");
		String flagHoldMail=xmlDataParser.getValueOf("flagHoldMail");
		String flagMailAddCtrl=xmlDataParser.getValueOf("flagMailAddCtrl");
		String ctrlLeaves=xmlDataParser.getValueOf("ctrlLeaves");
		String statementCopies=xmlDataParser.getValueOf("statementCopies");
		String makerId=xmlDataParser.getValueOf("makerId");
		String checkerId=xmlDataParser.getValueOf("checkerId");*/
		
		WebServiceHandler sHandler= new WebServiceHandler();
		try
		{
			sHandler.readCabProperty("Lead_Generation");
			Date d= new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String sDate = dateFormat.format(d);
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
			
			//String sCustomerID= xmlDataParser.getValueOf("CUST_ID");
			String existingCustomer=xmlDataParser.getValueOf("existingCustomer");
			String accountNumber=xmlDataParser.getValueOf("accountNumber");
			String branch=xmlDataParser.getValueOf("branch");
			String customerID=xmlDataParser.getValueOf("customerID");
			String creditCardNumber=xmlDataParser.getValueOf("creditCardNumber");
			String preferredContactTime=xmlDataParser.getValueOf("preferredContactTime");
			String preferredContactMode=xmlDataParser.getValueOf("preferredContactMode");
			String salary=xmlDataParser.getValueOf("salary");
			String customerCategory=xmlDataParser.getValueOf("customerCategory");
			String customerName=xmlDataParser.getValueOf("customerName");
			String contactEmail=xmlDataParser.getValueOf("contactEmail");
			String contactLanguage=xmlDataParser.getValueOf("contactLanguage");
			String contactAddress=xmlDataParser.getValueOf("contactAddress");
			String contactEmirate=xmlDataParser.getValueOf("contactEmirate");
			String contactPOBox=xmlDataParser.getValueOf("contactPOBox");
			String contactResidenceNo=xmlDataParser.getValueOf("contactResidenceNo");
			String contactMobileNo=xmlDataParser.getValueOf("contactMobileNo");
			String employmentName=xmlDataParser.getValueOf("employmentName");
			String employmentOfficeNo=xmlDataParser.getValueOf("employmentOfficeNo");
			String employmentFaxNo=xmlDataParser.getValueOf("employmentFaxNo");
			String businessCategory=xmlDataParser.getValueOf("businessCategory");
			String businessCategoryType=xmlDataParser.getValueOf("businessCategoryType");
			String productService=xmlDataParser.getValueOf("productService");
			String feedBack=xmlDataParser.getValueOf("feedBack");
			String priority=xmlDataParser.getValueOf("priority");
			String contactBefore=xmlDataParser.getValueOf("contactBefore");
			String leadDescription=xmlDataParser.getValueOf("leadDescription");
			String receivedBy=xmlDataParser.getValueOf("receivedBy");
			String receiverPosition=xmlDataParser.getValueOf("receiverPosition");
			String receiverLocation=xmlDataParser.getValueOf("receiverLocation");
			String receiverAccessPoint=xmlDataParser.getValueOf("receiverAccessPoint");
			String receiverBranch=xmlDataParser.getValueOf("receiverBranch");
			String leadInitiation=xmlDataParser.getValueOf("leadInitiation");
			String staffId=xmlDataParser.getValueOf("staffId");
			String publishFlag=xmlDataParser.getValueOf("publishFlag");
			String ref_no=xmlDataParser.getValueOf("REF_NO");
			LogGEN.writeTrace("Log", "sCustomerID---"+customerID);
			
			AddLeadResponseStub lead_stub=new AddLeadResponseStub(sWSDLPath);
			AddLeadResponseReq_type0 lead_cust_req=new AddLeadResponseReq_type0();
			AddLeadResponseReqMsg lead_req_msg=new AddLeadResponseReqMsg();
			
			HeaderType Header_Input = new HeaderType();
			
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("AddLeadResponse");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Addition");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setUsername(customerID);
			Header_Input.setCredentials(loggedinuser);
			lead_cust_req.setCustomerID(customerID);
			lead_cust_req.setAccountNumber(accountNumber);
			lead_cust_req.setBranch(branch);
			lead_cust_req.setBusinessCategory(businessCategory);
			lead_cust_req.setBusinessCategoryType(businessCategoryType);
			lead_cust_req.setContactAddress(contactAddress);
			lead_cust_req.setContactBefore(contactBefore);
			lead_cust_req.setContactEmail(contactEmail);
			lead_cust_req.setContactEmirate(contactEmirate);
			lead_cust_req.setContactLanguage(contactLanguage);
			lead_cust_req.setContactMobileNo(contactMobileNo);
			lead_cust_req.setContactPOBox(contactPOBox);
			lead_cust_req.setContactResidenceNo(contactResidenceNo);
			lead_cust_req.setCreditCardNumber(creditCardNumber);
			lead_cust_req.setCustomerCategory(customerCategory);
			lead_cust_req.setCustomerName(customerName);
			lead_cust_req.setEmploymentFaxNo(employmentFaxNo);
			lead_cust_req.setEmploymentName(employmentName);
			lead_cust_req.setEmploymentOfficeNo(employmentOfficeNo);
			lead_cust_req.setExistingCustomer(existingCustomer);
			lead_cust_req.setFeedBack(feedBack);
			lead_cust_req.setLeadDescription(leadDescription);
			lead_cust_req.setLeadInitiation(leadInitiation);
			lead_cust_req.setPreferredContactMode(preferredContactMode);
			lead_cust_req.setPreferredContactTime(preferredContactTime);
			lead_cust_req.setPriority(priority);
			lead_cust_req.setProductService(productService);
			lead_cust_req.setPublishFlag(publishFlag);
			lead_cust_req.setReceivedBy(receivedBy);
			lead_cust_req.setReceiverAccessPoint(receiverAccessPoint);
			lead_cust_req.setReceiverBranch(receiverBranch);
			lead_cust_req.setReceiverLocation(receiverLocation);
			lead_cust_req.setReceiverPosition(receiverPosition);
			lead_cust_req.setSalary(salary);
			lead_cust_req.setStaffId(staffId);
			
			
			
			LogGEN.writeTrace("Log", "All values set11");
			
			lead_req_msg.setHeader(Header_Input);				
			lead_req_msg.setAddLeadResponseReq(lead_cust_req);
			
			AddLeadResponseResMsg add_acc_res_msg= lead_stub.addLeadResponse_Oper(lead_req_msg);
			
			Header_Input=add_acc_res_msg.getHeader();
			sReturnCode=  Header_Input.getReturnCode();
			sErrorDetail=Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();
			System.out.println(sReturnCode+":"+sErrorDetail);
			//if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			//{
			try
			{
				AddLeadResponseRes_type0 lead_res=new AddLeadResponseRes_type0();
				lead_res=add_acc_res_msg.getAddLeadResponseRes();
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<Output>"+
					"<Option>Lead_Generation</Option>" +
					"<returnCode>"+sReturnCode+"</returnCode>" +
					"<errorDescription>"+sErrorDetail+"</errorDescription>"+
					"<LeadRes>"+
						"<Reason>"+ lead_res.getReason() +"</Reason>"+
						"<Status>"+lead_res.getStatus()+"</Status>"+
								
					"</LeadRes>"+	
					"</Output>";
			}
			catch(Exception ex)
			{
				LogGEN.writeTrace("Log", "Error in Web Serviice :"+ex.toString());
				LogGEN.writeTrace("Log", "Error Trace in Web Serviice :"+ex.getStackTrace());
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_ADDCUST</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to fetch etihad guest.</td></Output>";

			}
			//}
			//else
			//{
		    	//LogGEN.writeTrace("Log", "Failed");
		    	//sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_GETCUSTINFO</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to fetch etihad guest.</td></Output>";
			//}
			
			
			
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_ADDCUST</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to fetch etihad guest.</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput.trim().length());	
			LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput);	

			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_ADDCUST</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to fetch etihad guest.</td></Output>";
			}
			
			LogGEN.writeTrace("Log","outputXML : finally :"+sOutput);
			return sOutput;			
		}
	}
}

