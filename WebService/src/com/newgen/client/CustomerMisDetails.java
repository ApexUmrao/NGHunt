package com.newgen.client;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.activation.DataHandler;

import com.newgen.AESEncryption;
import com.newgen.stub.InqCustAcctMiscDtlsStub.CBRDetails_type0;
import com.newgen.stub.InqCustAcctMiscDtlsStub.CustomerCBRDetails_type0;
import com.newgen.stub.InqCustAcctMiscDtlsStub.CustomerRelationDetails_type0;
import com.newgen.stub.InqCustAcctMiscDtlsStub.CustomerRelations_type0;
import com.newgen.stub.InqCustAcctMiscDtlsStub.HeaderType;
import com.newgen.stub.InqCustAcctMiscDtlsStub.InqCustAcctMiscDtlsReqMsg;
import com.newgen.stub.InqCustAcctMiscDtlsStub.InqCustAcctMiscDtlsReq_type0;
import com.newgen.stub.InqCustAcctMiscDtlsStub.InqCustAcctMiscDtlsResMsg;
import com.newgen.stub.InqCustAcctMiscDtlsStub.InqCustAcctMiscDtlsRes_type0;
import com.newgen.stub.*;
import com.newgen.stub.InqCustAcctMiscDtlsStub.MisCodeDetails_type0;
import com.newgen.stub.InqCustAcctMiscDtlsStub.MisCodes_type0;
public class CustomerMisDetails extends WebServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_CustMis_ot=null;//Added By Harish For inserting original mssg
	
	/**
	 * Function written to fetch customer mis information
	 * @author gupta.ashish
	 * @param sInputXML
	 * @return
	 */

		@SuppressWarnings("finally")
		public String FetchCustomerMISInformation(String sInputXML) 
		{
			LogGEN.writeTrace("Log", "Fuction called FetchCustomerMISInformation");
			LogGEN.writeTrace("Log", "sInputXML---"+sInputXML);
			
		
			String sReturnCode= "";
			String sErrorDetail="";
			String sErrorDesc = "";
			String sOutput="";
			
			LogGEN.writeTrace("Log", "Fuction called FetchCustomerMISInformation");
			LogGEN.writeTrace("Log", "sInputXML---"+sInputXML);
			XMLParser xmlDataParser = new XMLParser();
			xmlDataParser.setInputXML(sInputXML);
			
			WebServiceHandler sHandler= new WebServiceHandler();
			Date d= new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String sDate = dateFormat.format(d);
			try
			{
				sHandler.readCabProperty("Customer_MIS_Information");
				
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
				
				InqCustAcctMiscDtlsStub Cust_MIS_Info = new InqCustAcctMiscDtlsStub(sWSDLPath);
				InqCustAcctMiscDtlsReqMsg Cust_MIS_Info_Req = new InqCustAcctMiscDtlsReqMsg();
				InqCustAcctMiscDtlsResMsg Cust_MIS_Info_Rep = new InqCustAcctMiscDtlsResMsg();
				InqCustAcctMiscDtlsReq_type0 Cust_MIS_Info_0 = new InqCustAcctMiscDtlsReq_type0();
				HeaderType Header_Input = new HeaderType();
				
				String ref_no=xmlDataParser.getValueOf("REF_NO");
				LogGEN.writeTrace("Log", "All Objects created");
				
				//sDate="06/08/2013 18:33:10";
				LogGEN.writeTrace("Log", "sDate---"+sDate);
				
				Header_Input.setUsecaseID("1234");
				Header_Input.setServiceName("InqCustAcctMiscDtls");
				Header_Input.setVersionNo("1.0");
				Header_Input.setServiceAction("Inquiry");
				Header_Input.setSysRefNumber(ref_no);
				Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
				Header_Input.setReqTimeStamp(sDate);
				Header_Input.setUsername(xmlDataParser.getValueOf("CUST_ID"));
				Header_Input.setCredentials(loggedinuser);
				
				Cust_MIS_Info_0.setCustomerId(xmlDataParser.getValueOf("CUST_ID"));
				Cust_MIS_Info_0.setCustomerSignatureFlag(xmlDataParser.getValueOf("CustomerSignatureFlag"));
				Cust_MIS_Info_0.setCustomerSignatureType(xmlDataParser.getValueOf("CustomerSignatureType"));				
				Cust_MIS_Info_Req.setHeader(Header_Input);
				
				System.out.println(xmlDataParser.getValueOf("CUST_ID"));
				System.out.println(xmlDataParser.getValueOf("CustomerSignatureFlag"));
				System.out.println(xmlDataParser.getValueOf("CustomerSignatureType"));
				System.out.println("sdfd333333333333333333333333333");
				Cust_MIS_Info_Req.setInqCustAcctMiscDtlsReq(Cust_MIS_Info_0);
				
				LogGEN.writeTrace("Log", "All values set");
				xmlInput=Cust_MIS_Info.getInputXML(Cust_MIS_Info_Req);
				
				LogGEN.writeTrace("Log", "MIS INPUT:"+xmlInput);
				
				Cust_MIS_Info._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
				Cust_MIS_Info_Rep= Cust_MIS_Info.inqCustAcctMiscDtls_Oper(Cust_MIS_Info_Req);
				sOrg_CustMis_ot=Cust_MIS_Info.resCustMis;//Added By Harish For inserting original mssg
				
				Header_Input= Cust_MIS_Info_Rep.getHeader();	
				
				sReturnCode=  Header_Input.getReturnCode();
				sErrorDetail=Header_Input.getErrorDetail();
				sErrorDesc = Header_Input.getErrorDescription();
				
				LogGEN.writeTrace("Log", "Return Code---"+sReturnCode);
				LogGEN.writeTrace("Log", "Error Detail---"+sErrorDetail);
				System.out.println( "Error Detail---"+sErrorDetail);
				LogGEN.writeTrace("Log", "Error Description---"+sErrorDesc);
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<Output>"+
					"<Option>Customer_MIS_Information</Option>" +
					"<returnCode>"+sReturnCode+"</returnCode>" +
					"<errorDescription>"+sErrorDetail+"</errorDescription>"+
					"<CustMISDetRes>";
			    if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
				{
			    	
			    	InqCustAcctMiscDtlsRes_type0 res=new InqCustAcctMiscDtlsRes_type0();
			    	res=Cust_MIS_Info_Rep.getInqCustAcctMiscDtlsRes();
			    	sOutput+="<CustomerId>"+res.getCustomerId()+"</CustomerId>"+
			    	"<CustomerPhotoPresent>"+res.getCustomerPhotoPresent()+"</CustomerPhotoPresent>"+
			    	"<CustomerSignatureFlag>"+res.getCustomerSignatureFlag()+"</CustomerSignatureFlag>"+
			    	"<CustomerSignaturePresent>"+res.getCustomerSignaturePresent()+"</CustomerSignaturePresent>"+
			    	"<CustomerSignatureType>"+res.getCustomerSignatureType()+"</CustomerSignatureType>";
			    	DataHandler data;
			    	try
			    	{
			    		data=res.getCustomerSignature();			    	
			    		sOutput+="<CustomerSignature>"+ data.getContent().toString()+"</CustomerSignature>";
			    	}
			    	catch(Exception x)
			    	{
			    		System.out.println("errrrrrrrrrrrrrrrrrrrrr");
			    	}
			    	MisCodeDetails_type0 mis=res.getMisCodeDetails();
			    	try
			    	{
				    	sOutput+="<MISCodes>";
				    	MisCodes_type0[] misc=mis.getMisCodes();
				    	for(int i=0;i<misc.length;i++)
				    	{
				    		sOutput+="<MISCode>"+
				    		"<MisCodeNumber>"+misc[i].getMisCodeNumber()+"</MisCodeNumber>"+
				    		"<MisCodeText>"+misc[i].getMisCodeText()+"</MisCodeText>"+
				    		"<MisCodeType>"+misc[i].getMisCodeType()+"</MisCodeType>"+
				    		"</MISCode>";
				    	}
				    	sOutput+="</MISCodes>";
			    	}
			    	catch(Exception ex1)
			    	{
			    		sOutput+="<MISCodes></MISCodes>";
			    	}
			    	CustomerCBRDetails_type0 cbrd=res.getCustomerCBRDetails();
			    	
			    	try
			    	{
			    		CBRDetails_type0[] cbr=cbrd.getCBRDetails();
				    	sOutput+="<CBRDetails>";			    	
				    	for(int i=0;i<cbr.length;i++)
				    	{
				    		sOutput+="<CBRDetail>"+
				    		"<CBRField>"+cbr[i].getCBRField()+"</CBRField>"+
				    		"<CBRLable>"+cbr[i].getCBRLable()+"</CBRLable>"+
				    		"<CBRValue>"+cbr[i].getCBRValue()+"</CBRValue>"+
				    		"<CBRValueDescription>"+cbr[i].getCBRValueDescription()+"</CBRValueDescription>"+
				    		"<CustAccountNumber>"+cbr[i].getCustAccountNumber()+"</CustAccountNumber>"+
				    		"</CBRDetail>";
				    	}
				    	sOutput+="</CBRDetails>";
			    	}
			    	catch(Exception ex2)
			    	{
			    		sOutput+="<CBRDetails></CBRDetails>";
			    	}
			    	CustomerRelationDetails_type0 reldet=res.getCustomerRelationDetails();
			    	try
			    	{
				    	CustomerRelations_type0[] rel=reldet.getCustomerRelations();
				    	sOutput+="<RelationDetails>";			    	
				    	for(int i=0;i<rel.length;i++)
				    	{
				    		sOutput+="<RelDetail>"+
				    		"<CustFullName>"+rel[i].getCustFullName()+"</CustFullName>"+
				    		"<CustInvRelationCode>"+rel[i].getCustInvRelationCode()+"</CustInvRelationCode>"+
				    		"<CustInvRelationText>"+rel[i].getCustInvRelationText()+"</CustInvRelationText>"+
				    		"<CustomerId>"+rel[i].getCustomerId()+"</CustomerId>"+
				    		"<CustRelationCode>"+rel[i].getCustRelationCode()+"</CustRelationCode>"+
				    		"<CustRelationText>"+rel[i].getCustRelationText()+"</CustRelationText>"+
				    		"<FlagPrimarySecondary>"+rel[i].getFlagPrimarySecondary()+"</FlagPrimarySecondary>"+
				    		"</RelDetail>";
				    	}
				    	sOutput+="</RelationDetails>";
			    	}
			    	catch(Exception EE)
			    	{
			    		sOutput+="<RelationDetails>";			    	
				    	
				    	sOutput+="</RelationDetails>";
			    	}
			    	
			    	
				}
			    sOutput+="</CustMISDetRes></Output>";
			    System.out.println(sOutput);			    
			    
			    
			  // String sCustomer_ID= Cust_MIS_Info_Res_0.getCustomerId();		   
			   
			   
			   
			  // LogGEN.writeTrace("Log", "Customer ID---"+sCustomer_ID);
			  
			}
			catch(Exception e)
			{
				LogGEN.writeTrace("Log", "Error in Web Serviice :"+e.toString());
				LogGEN.writeTrace("Log", "Error Trace in Web Serviice :"+e.getStackTrace());
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>Customer_MIS_Information</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to fetch customer detail.</td></Output>";
				e.printStackTrace();
				
			}
			finally
			{
				LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput.trim().length());				
				if(sOutput.trim().length()<1)
				{
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>Customer_MIS_Information</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to fetch customer detail.</td></Output>";
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
						 LogGEN.writeTrace("Log","sOrg_CustMis_ot : finally :"+sOrg_CustMis_ot);
						 try {
							 con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_CustMis_ot.replaceAll("'", "''"));
						} catch (Exception e2) {
							// TODO: handle exception
							e2.getMessage();
						}
				LogGEN.writeTrace("Log","outputXML : finally :"+sOutput);
				return sOutput;			
			}
		}
}
