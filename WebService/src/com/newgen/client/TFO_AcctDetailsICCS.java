package com.newgen.client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.stub.InqAccountDetailsICCSStub;
import com.newgen.stub.InqAccountDetailsICCSStub.AcctDetails_type0;
import com.newgen.stub.InqAccountDetailsICCSStub.CreditAccountInput_type0;
import com.newgen.stub.InqAccountDetailsICCSStub.CustContactDetails_type0;
import com.newgen.stub.InqAccountDetailsICCSStub.CustDetails_type0;
import com.newgen.stub.InqAccountDetailsICCSStub.HeaderType;
import com.newgen.stub.InqAccountDetailsICCSStub.InqAccountDetailsICCSReqMsg;
import com.newgen.stub.InqAccountDetailsICCSStub.InqAccountDetailsICCSReq_type0;
import com.newgen.stub.InqAccountDetailsICCSStub.InqAccountDetailsICCSResMsg;
import com.newgen.stub.InqAccountDetailsICCSStub.InqAccountDetailsICCSRes_type0;

public class TFO_AcctDetailsICCS extends WebServiceHandler
{

	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected long lTimeOut = 30000;
	String sOrg_Output=null;
	
	String xmlInput="";
	@SuppressWarnings({ "finally", "unused" })
	public String FetchAcctDetailsICCS(String sInputXML) 
	{
	
		
		LogGEN.writeTrace("Log", "TFO_AcctDetailsICCS sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";
		String sOutput= "";				
		
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		WebServiceHandler sHandler= new WebServiceHandler();
		try
		{
			sHandler.readCabProperty("TFO_AcctDetailsICCS");
			
			sWSDLPath=(String)currentCabPropertyMap.get("WSDL_PATH");
			sCabinet=(String)currentCabPropertyMap.get("CABINET");
			sUser=(String)currentCabPropertyMap.get("USER");
			sLoginReq=Boolean.valueOf((String)currentCabPropertyMap.get("LOGIN_REQ"));
			sPassword=(String)currentCabPropertyMap.get("PASSWORD");
			lTimeOut=Long.valueOf((String)currentCabPropertyMap.get("TIMEOUT_INTERVAL"));
			
			LogGEN.writeTrace("Log", "read Property successfully");
			LogGEN.writeTrace("Log", "WSDL PATH TFO_AcctDetailsICCS---- "+(String)currentCabPropertyMap.get("WSDL_PATH"));
			LogGEN.writeTrace("Log", "CABINET TFO_AcctDetailsICCS---- "+(String)currentCabPropertyMap.get("CABINET"));
			LogGEN.writeTrace("Log", "USER TFO_AcctDetailsICCS---- "+(String)currentCabPropertyMap.get("USER"));
			LogGEN.writeTrace("Log", "PASSWORD TFO_AcctDetailsICCS---- "+(String)currentCabPropertyMap.get("PASSWORD"));
			LogGEN.writeTrace("Log", "LOGIN_REQ TFO_AcctDetailsICCS---- "+(String)currentCabPropertyMap.get("LOGIN_REQ"));
			LogGEN.writeTrace("Log", "TIME_OUT TFO_AcctDetailsICCS---- "+(String)currentCabPropertyMap.get("TIMEOUT_INTERVAL"));
			
					
			String ref_no=xmlDataParser.getValueOf("sysRefNumber");			
			String Accno=xmlDataParser.getValueOf("acctNumber");
			String senderId=xmlDataParser.getValueOf("senderId");
			
			
			InqAccountDetailsICCSStub inqAccountDetailsICCSStub=new InqAccountDetailsICCSStub(sWSDLPath);
			InqAccountDetailsICCSReqMsg inqAccountDetailsICCSReqMsg = new InqAccountDetailsICCSReqMsg();
			
			InqAccountDetailsICCSReq_type0 inqAccountDetailsICCSReq_type0  = new InqAccountDetailsICCSReq_type0();
			
			CreditAccountInput_type0 creditAccountInput  = new CreditAccountInput_type0();			
			creditAccountInput.setAcctNumber(Accno);
			inqAccountDetailsICCSReq_type0.setCreditAccountInput(creditAccountInput);
			
			inqAccountDetailsICCSReqMsg.setInqAccountDetailsICCSReq(inqAccountDetailsICCSReq_type0);			
			
			HeaderType Header_Input = new HeaderType();
			
			Header_Input.setUsecaseID("1.0");
			Header_Input.setServiceName("InqAccountDetailsICCS");
			Header_Input.setVersionNo("2.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(senderId);
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setUsername("");
			Header_Input.setCredentials("");
			inqAccountDetailsICCSReqMsg.setHeader(Header_Input);
			LogGEN.writeTrace("Log", "All values set11");			
			
			xmlInput=inqAccountDetailsICCSStub.getinputXML(inqAccountDetailsICCSReqMsg);
			LogGEN.writeTrace("Log", "input xml for inqAccountDetailsICCSReqMsg  " + xmlInput);
			HeaderType header_output = new HeaderType();
			InqAccountDetailsICCSResMsg inqAccountDetailsICCSResMsg= inqAccountDetailsICCSStub.inqAccountDetailsICCS_Oper(inqAccountDetailsICCSReqMsg);
			LogGEN.writeTrace("Log", "gettig output xml for inqAccountDetailsICCSReqMsg  " );
			sOrg_Output=inqAccountDetailsICCSStub.response;
			LogGEN.writeTrace("Log", "output xml for inqAccountDetailsICCSReqMsg  " + sOrg_Output);
			header_output=inqAccountDetailsICCSResMsg.getHeader();
			sReturnCode=  header_output.getReturnCode();
			sErrorDetail=header_output.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();
			System.out.println(sErrorDetail);
			
			if((sErrorDesc != null && !sErrorDesc.equalsIgnoreCase("Failure") ) || (sReturnCode != null && !sReturnCode.equalsIgnoreCase("1")))
			{
				InqAccountDetailsICCSRes_type0 inqAccountDetailsICCSRes_type0 = new InqAccountDetailsICCSRes_type0();
				inqAccountDetailsICCSRes_type0  = inqAccountDetailsICCSResMsg.getInqAccountDetailsICCSRes();
				AcctDetails_type0  acctDetails_type0 = inqAccountDetailsICCSRes_type0.getAcctDetails();
				CustDetails_type0 custDetails_type0 = inqAccountDetailsICCSRes_type0.getCustDetails();
				CustContactDetails_type0 custContactDetails_type0 = inqAccountDetailsICCSRes_type0.getCustContactDetails();
				int noOfLinkedCustomer  = inqAccountDetailsICCSRes_type0.getLinkedCustomer().length;
				StringBuffer tempOutput = new StringBuffer();
				tempOutput.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
						.append("<Output>")
						.append("<option>AcctDetailsICCS</option>")
						.append("<returnCode>"+sReturnCode+"</returnCode>")
						.append("<errorDescription>"+sErrorDesc+"</errorDescription>")
						.append("<errorDetail>"+sErrorDetail+"</errorDetail>")
						.append("<InqAccountDetailsICCSResMsg>")						
						.append("<InqAccountDetailsICCSRes>")
						.append("<acctNumber>"+inqAccountDetailsICCSRes_type0.getAcctNumber()+"</acctNumber>")
						
						//Account Details
						.append("<acctDetails>")
						//.append("<acctNumberIBAN>"+acctDetails_type0.getAcctNumberIBAN()+"</acctNumberIBAN>")
						//.append("<acctTitle>"+acctDetails_type0.getAcctTitle()+"</acctTitle>")
						.append("<acctBranchCode>"+acctDetails_type0.getAcctBranchCode()+"</acctBranchCode>")
						.append("<acctBranchType>"+acctDetails_type0.getAcctBranchType()+"</acctBranchType>")
						.append("<acctProductCode>"+acctDetails_type0.getAcctProductCode()+"</acctProductCode>")
						.append("<acctCurrency>"+acctDetails_type0.getAcctCurrency()+"</acctCurrency>")
						.append("<acctStatusCode>"+acctDetails_type0.getAcctStatusCode()+"</acctStatusCode>")
						.append("<acctStatusDescription>"+acctDetails_type0.getAcctStatusDescription()+"</acctStatusDescription>")
						.append("<acctMemo>"+acctDetails_type0.getAcctMemo()+"</acctMemo>")
						.append("<acctMemoDesc>"+acctDetails_type0.getAcctMemoDesc()+"</acctMemoDesc>")
						.append("<acctMemoSeverity>"+acctDetails_type0.getAcctMemoSeverity()+"</acctMemoSeverity>")
						.append("<acctModeofOps1>"+acctDetails_type0.getAcctModeofOps1()+"</acctModeofOps1>")
						.append("<acctModeofOps2>"+acctDetails_type0.getAcctModeofOps2()+"</acctModeofOps2>")
						.append("<acctModeofOps3>"+acctDetails_type0.getAcctModeofOps3()+"</acctModeofOps3>")
						.append("<acctModeofOps4>"+acctDetails_type0.getAcctModeofOps4()+"</acctModeofOps4>")
						.append("<acctBalAvailable>"+acctDetails_type0.getAcctBalAvailable()+"</acctBalAvailable>")
						.append("<acctBalBook>"+acctDetails_type0.getAcctBalBook()+"</acctBalBook>")
						.append("<amountOnHold>"+acctDetails_type0.getAmountOnHold()+"</amountOnHold>")
						.append("<amountODLimit>"+acctDetails_type0.getAmountODLimit()+"</amountODLimit>")
						.append("<amountSweepinLien>"+acctDetails_type0.getAmountSweepinLien()+"</amountSweepinLien>")
						.append("<netBalAvailable>"+acctDetails_type0.getNetBalAvailable()+"</netBalAvailable>")
						.append("<excessOD>"+acctDetails_type0.getExcessOD()+"</excessOD>")
						.append("<acctNumberIBAN>"+acctDetails_type0.getAcctNumberIBAN()+"</acctNumberIBAN>")
						.append("</acctDetails>")
						
						
						//Customer Details
						.append("<custDetails>")
						.append("<custId>"+custDetails_type0.getCustId()+"</custId>")
						/*.append("<custFullName>"+custDetails_type0.getCustFullName()+"</custFullName>")
						.append("<custNationality>"+custDetails_type0.getCustNationality()+"</custNationality>")
						.append("<custCategoryCode>"+custDetails_type0.getCustCategoryCode()+"</custCategoryCode>")
						.append("<custCategoryDesc>"+custDetails_type0.getCustCategoryDesc()+"</custCategoryDesc>")
						.append("<custCategoryClass>"+custDetails_type0.getCustCategoryClass()+"</custCategoryClass>")
						.append("<custIdType>"+custDetails_type0.getCustIdType()+"</custIdType>")
						.append("<custIdTypeDesc>"+custDetails_type0.getCustIdTypeDesc()+"</custIdTypeDesc>")
						.append("<custIdNumber>"+custDetails_type0.getCustIdNumber()+"</custIdNumber>")
						.append("<custCitizenshipId>"+custDetails_type0.getCustCitizenshipId()+"</custCitizenshipId>")
						.append("<custPrimarySector>"+custDetails_type0.getCustPrimarySector()+"</custPrimarySector>")
						.append("<custSecondarySector>"+custDetails_type0.getCustSecondarySector()+"</custSecondarySector>")
						.append("<custProfitCenter>"+custDetails_type0.getCustProfitCenter()+"</custProfitCenter>")
						.append("<custRMCode>"+custDetails_type0.getCustRMCode()+"</custRMCode>")
						.append("<custRMFullName>"+custDetails_type0.getCustRMFullName()+"</custRMFullName>")
						.append("<custRMMobileNumber>"+custDetails_type0.getCustRMMobileNumber()+"</custRMMobileNumber>")*/
						.append("<custMemo>"+custDetails_type0.getCustMemo()+"</custMemo>")
						.append("<custMemoSeverity>"+custDetails_type0.getCustMemoSeverity()+"</custMemoSeverity>")
						.append("</custDetails>");
						
						//Customer Contact Details 
						/*.append("<custContactDetails>")
						.append("<addressLine1>"+custContactDetails_type0.getAddressLine1()+"</addressLine1>")
						.append("<addressLine2>"+custContactDetails_type0.getAddressLine2()+"</addressLine2>")
						.append("<addressLine3>"+custContactDetails_type0.getAddressLine3()+"</addressLine3>")
						.append("<city>"+custContactDetails_type0.getCity()+"</city>")
						.append("<state>"+custContactDetails_type0.getState()+"</state>")
						.append("<country>"+custContactDetails_type0.getCountry()+"</country>")
						.append("<custPhoneNo>"+custContactDetails_type0.getCustPhoneNo()+"</custPhoneNo>")
						.append("<custPhoneOffice>"+custContactDetails_type0.getCustPhoneOffice()+"</custPhoneOffice>")
						.append("<custMobileNo>"+custContactDetails_type0.getCustMobileNo()+"</custMobileNo>")
						.append("<custFaxNo>"+custContactDetails_type0.getCustFaxNo()+"</custFaxNo>")
						.append("<custEmail>"+custContactDetails_type0.getCustEmail()+"</custEmail>")
						.append("</custContactDetails>");										
*/				
					StringBuffer linkedCustomerList = new StringBuffer();
					for(int i=0;i<noOfLinkedCustomer;i++)
					{
						linkedCustomerList.append("<linkedCustomer>")
						.append("<custID>"+inqAccountDetailsICCSRes_type0.getLinkedCustomer()[i].getCustID()+"</custID>")
						.append("<custRelation>"+inqAccountDetailsICCSRes_type0.getLinkedCustomer()[i].getCustRelation()+"</custRelation>")
						.append("<custName>"+inqAccountDetailsICCSRes_type0.getLinkedCustomer()[i].getCustName()+"</custName>")
						.append("</linkedCustomer>");						
					}
					tempOutput.append(linkedCustomerList)
					.append("</InqAccountDetailsICCSRes>")
					.append("</InqAccountDetailsICCSResMsg>")
					.append("</Output>");
					sOutput = tempOutput.toString();		
			
			}
			else
			{
				try
				{
					LogGEN.writeTrace("Log", "Failed");
			    	sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>AcctDetailsICCS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to fetch Data.</td></Output>";
				}
				catch(Exception e)
				{
					LogGEN.writeTrace("Log", "Failed");
					e.printStackTrace();
				}
			}
			
				 
				 
						
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>AcctDetailsICCS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to fetch Data.</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput.trim().length());	
			LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput);	

			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>AcctDetailsICCS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to fetch Data.</td></Output>";
			}
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
				String call_type=xmlDataParser.getValueOf("Calltype");
				sCabinet=xmlDataParser.getValueOf("EngineName");
				dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				
				 try
				 {
					 dbpass=AESEncryption.decrypt(dbpass);
				 }
				 catch(Exception e)
				 {
					 
				 }
				 DBConnection con=new DBConnection();
				  /**
				  * //Added By Ashwani For inserting original mssg
				  * Date			:	17 Mar 2015
				  * Description 	:	Replace execute with executeClob
				  */
				 String Query="insert into usr_0_other_calls_web_log(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
				 LogGEN.writeTrace("Log","AcctDetailsICCS Query : finally :"+Query);
				 LogGEN.writeTrace("Log","sOrg_Output : finally :"+sOrg_Output);
				 try {
					 con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_Output.replaceAll("'", "''"));
				} catch (Exception e2) {
					// TODO: handle exception
				}
				
			 //End Here
			return sOutput;			
		}
	}

	

}
