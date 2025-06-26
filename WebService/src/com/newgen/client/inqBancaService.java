package com.newgen.client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.stub.InqBancassuranceDetailsStub;
import com.newgen.stub.InqBancassuranceDetailsStub.BancassuranceDetails_type0;
import com.newgen.stub.InqBancassuranceDetailsStub.HeaderType;
import com.newgen.stub.InqBancassuranceDetailsStub.InqBancassuranceDetailsReqMsg;
import com.newgen.stub.InqBancassuranceDetailsStub.InqBancassuranceDetailsReq_type0;
import com.newgen.stub.InqBancassuranceDetailsStub.InqBancassuranceDetailsResMsg;
import com.newgen.stub.InqBancassuranceDetailsStub.InqBancassuranceDetailsRes_type0;



public class inqBancaService extends WebServiceHandler{

	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_Output=null;//Added By Harish For inserting original mssg
	/**
	 * Function written to fetch Debit Card Details
	 * @author gupta.ashish
	 * @param sInputXML
	 * @return
	 */

		@SuppressWarnings("finally")
		public String banca_service(String sInputXML) 
		{
			LogGEN.writeTrace("Log", "Fuction called cust_banca");
			LogGEN.writeTrace("Log", "sInputXML---"+sInputXML);
			XMLParser xmlDataParser = new XMLParser();
			xmlDataParser.setInputXML(sInputXML);
			String sReturnCode= "";
			String sErrorDetail="";
			String sErrorDesc = "";
			String sOutput="";
			WebServiceHandler sHandler= new WebServiceHandler();
			Date d= new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String sDate = dateFormat.format(d);
			try
			{
				sHandler.readCabProperty("cust_banca");
				
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
				LogGEN.writeTrace("Log", "sCustomerID---"+sCustomerID);
				
				
				//sDate="06/08/2013 18:33:10";
				LogGEN.writeTrace("Log", "sDate---"+sDate);
				
				InqBancassuranceDetailsStub banca = new InqBancassuranceDetailsStub(sWSDLPath);
				InqBancassuranceDetailsReq_type0 banca_req=new InqBancassuranceDetailsReq_type0();
			    InqBancassuranceDetailsRes_type0 res=  new InqBancassuranceDetailsRes_type0();
				InqBancassuranceDetailsReqMsg req_msg=new InqBancassuranceDetailsReqMsg();
				
				HeaderType Header_Input = new HeaderType();
				LogGEN.writeTrace("Log", "All Objects created");
				
				Header_Input.setUsecaseID("1234");
				Header_Input.setServiceName("InqBancassuranceDetails");
				Header_Input.setVersionNo("1.0");
				Header_Input.setServiceAction("Inquiry");
				Header_Input.setSysRefNumber(ref_no);
				Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
				Header_Input.setReqTimeStamp(sDate);
				Header_Input.setUsername(sCustomerID);
				Header_Input.setCredentials(loggedinuser);
				LogGEN.writeTrace("Log", "All values set11");
				
				banca_req.setCustomerId(sCustomerID);
			
				req_msg.setHeader(Header_Input);
				req_msg.setInqBancassuranceDetailsReq(banca_req);
				LogGEN.writeTrace("Log", banca.getinputXML(req_msg));
				
				System.out.println( banca.getinputXML(req_msg));
				
				InqBancassuranceDetailsResMsg res_msg=new InqBancassuranceDetailsResMsg();
				xmlInput=banca.getinputXML(req_msg);
				
				banca._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
				res_msg=banca.inqBancassuranceDetails_Oper(req_msg);
				sOrg_Output=banca.resMsg;//Added By Harish For inserting original mssg
				
				LogGEN.writeTrace("Log", "All values set");
					   
				
				Header_Input = res_msg.getHeader();
				sReturnCode=  Header_Input.getReturnCode();
				sErrorDetail=Header_Input.getErrorDetail();
				sErrorDesc = Header_Input.getErrorDescription();
				
				LogGEN.writeTrace("Log", "Return Code---"+sReturnCode);
				LogGEN.writeTrace("Log", "Error Detail---"+sErrorDetail);
				System.out.println( "Error Detail---"+sErrorDetail);
				LogGEN.writeTrace("Log", "Error Description---"+sErrorDesc);
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<Output>"+
					"<Option>CUST_BANCA</Option>" +
					"<returnCode>"+sReturnCode+"</returnCode>" +
					"<errorDescription>"+sErrorDetail+"</errorDescription>";
				
				if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
				{
					try
					{
					    res=res_msg.getInqBancassuranceDetailsRes();
					    BancassuranceDetails_type0[] r=res.getBancassuranceDetails();
					    
					    sOutput+="<CustomerId>"+res.getCustomerId()+"</CustomerId><Accounts>";				    
					    
					    if(res.isBancassuranceDetailsSpecified())
					    {
						    for(int i =0;i<r.length;i++)
					    {
							sOutput+="<AccountNumber>"+r[i].getAccountNumber()+"</AccountNumber>"+
					    	"<Currency>"+r[i].getCurrency()+"</Currency>"+
					    	"<Frequency>"+r[i].getFrequency()+"</Frequency>"+
					    	"<PaidPremium>"+r[i].getPaidPremium()+"</PaidPremium>"+
					    	"<PolicyAccountValue>"+r[i].getPolicyAccountValue()+"</PolicyAccountValue>"+
					    	"<PolicyNumber>"+r[i].getPolicyNumber()+"</PolicyNumber>"+
					    	"<PolicyStartDate>"+r[i].getPolicyStartDate()+"</PolicyStartDate>"+
					    	"<PolicyStatus>"+r[i].getPolicyStatus()+"</PolicyStatus>"+
					    	"<PolicySurrenderValue>"+r[i].getPolicySurrenderValue()+"</PolicySurrenderValue>"+
					    	"<Premium>"+r[i].getPremium()+"</Premium>"+
					    	"<ProductName>"+r[i].getProductName()+"</ProductName>"+
					    	"<ProviderName>"+r[i].getProviderName()+"</ProviderName>"+
					    	"<PolicyTerm>"+r[i].getPolicyTerm()+"</PolicyTerm>";
						    }
					    }
					    sOutput+="</Accounts>";
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						 res=res_msg.getInqBancassuranceDetailsRes();
						 sOutput+="<CustomerId>"+res.getCustomerId()+"</CustomerId><Accounts>";
						 sOutput+="</Accounts>";
						
					}
				} 
				else
				{
					LogGEN.writeTrace("Log", "Failed");
				}
				sOutput+="</Output>" ;
			}
			catch (Exception e)
			{
				LogGEN.writeTrace("Log", "Error in Web Serviice :"+e.toString());
				LogGEN.writeTrace("Log", "Error Trace in Web Serviice :"+e.getStackTrace());
				sReturnCode="-1";
				sErrorDetail=e.getMessage();
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>CUST_BANCA</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to fetch banca details.</td></Output>";
				e.printStackTrace();
			}
			finally
			{
				LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput.trim().length());				
				if(sOutput.trim().length()<1)
				{
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>CUST_BANCA</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to fetch banca details.</td></Output>";
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
						 LogGEN.writeTrace("Log","outputXML : finally :"+dbpass);
						 dbpass=AESEncryption.decrypt(dbpass);
						 LogGEN.writeTrace("Log","outputXML : finally :"+dbpass);
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
				LogGEN.writeTrace("Log","outputXML : finally :"+sOutput);
				return sOutput;			
			}
		}
}

