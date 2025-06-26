package com.newgen.client;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.stub.InqFxRateStub;
import com.newgen.stub.InqFxRateStub.GetFxRatesReq2_type0;
import com.newgen.stub.InqFxRateStub.GetFxRatesReqMsgChoice_type0;
import com.newgen.stub.InqFxRateStub.GetFxRatesRes2_type0;
import com.newgen.stub.InqFxRateStub.GetFxRatesResMsg;
import com.newgen.stub.InqFxRateStub.GetFxRatesResMsgChoice_type0;
import com.newgen.stub.InqFxRateStub.HeaderType;
import com.newgen.stub.InqFxRateStub.GetFxRatesReqMsg;



public class ConversionRate extends WebServiceHandler{


	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	String sOrg_FXRT_Ot=null;//Added By Harish For inserting original mssg
	protected static long lTimeOut = 30000;
	@SuppressWarnings("finally")
	public String rate_convert(String sInputXML) 
	{
	
		LogGEN.writeTrace("Log", "Fuction called Rate_Convert");
		LogGEN.writeTrace("Log", "sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sOutput= "";
		
				
		WebServiceHandler sHandler= new WebServiceHandler();
		try
		{
			sHandler.readCabProperty("Rate_Convert");
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
			
			String sCustomerID= xmlDataParser.getValueOf("CUST_ID");	
			String ref_no=xmlDataParser.getValueOf("REF_NO");
			LogGEN.writeTrace("Log", "sCustomerID---"+sCustomerID);
			
			InqFxRateStub rate_stub=new InqFxRateStub(sWSDLPath);
			GetFxRatesReq2_type0 rate_req_type=new GetFxRatesReq2_type0();
			GetFxRatesReqMsg rate_req_msg=new GetFxRatesReqMsg();
			GetFxRatesReqMsgChoice_type0 rate_req=new GetFxRatesReqMsgChoice_type0();
			HeaderType Header_Input = new HeaderType();
			
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("InqFxRate");
			Header_Input.setVersionNo("2.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setUsername("SS");
			Header_Input.setCredentials(loggedinuser);
			
			rate_req_type.setCustomerId(xmlDataParser.getValueOf("customerId"));
			rate_req_type.setFromAmount(xmlDataParser.getValueOf("fromAmount"));
			rate_req_type.setFromCurrency(xmlDataParser.getValueOf("fromCurrency"));
			rate_req_type.setToAmount(xmlDataParser.getValueOf("toAmount"));
			rate_req_type.setToCurrency(xmlDataParser.getValueOf("toCurrency"));
			rate_req_type.setChannelName(xmlDataParser.getValueOf("channelName"));
			rate_req_type.setTxnType(xmlDataParser.getValueOf("txnType"));
		
			rate_req.setGetFxRatesReq2(rate_req_type);
			rate_req_msg.setHeader(Header_Input);
			rate_req_msg.setGetFxRatesReqMsgChoice_type0(rate_req);
			rate_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);	
			GetFxRatesResMsg rate_res_msg= rate_stub.inqFxRate_Oper(rate_req_msg);
			sOrg_FXRT_Ot=rate_stub.resFXrate;
			Header_Input=rate_res_msg.getHeader();
			sReturnCode=  Header_Input.getReturnCode();
			sErrorDetail=Header_Input.getErrorDetail();
			System.out.println(sReturnCode+":"+sErrorDetail);
		
			try
			{
				GetFxRatesResMsgChoice_type0 res= new GetFxRatesResMsgChoice_type0();
				res= rate_res_msg.getGetFxRatesResMsgChoice_type0();
				GetFxRatesRes2_type0 r= res.getGetFxRatesRes2();
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<Output>"+
					"<Option>Rate_Inq</Option>" +
					"<returnCode>"+sReturnCode+"</returnCode>" +
					"<errorDescription>"+sErrorDetail+"</errorDescription>"+
					"<RateRes>"+
						"<AmountAED>"+ r.getAmountAED() +"</AmountAED>"+
						"<ChannelName>"+ r.getChannelName() +"</ChannelName>"+
						"<CustomerId>"+ r.getCustomerId() +"</CustomerId>"+
						"<FromAmount>"+ r.getFromAmount() +"</FromAmount>"+
						"<FromAmountToAEDRate>"+ r.getFromAmountToAEDRate() +"</FromAmountToAEDRate>"+
						"<FromCurrency>"+ r.getFromCurrency() +"</FromCurrency>"+
						"<ToAmountToAEDRate>"+ r.getToAmountToAEDRate() +"</ToAmountToAEDRate>"+	
						"<ToAmount>"+ r.getToAmount() +"</ToAmount>"+
						"<ToCurrency>"+ r.getToCurrency() +"</ToCurrency>"+	
						"<TxnType>"+ r.getTxnType() +"</TxnType>"+	
						
						
					"</RateRes>"+	
					"</Output>";
			}
			catch(Exception ex)
			{
				LogGEN.writeTrace("Log", "Error in Web Serviice :"+ex.toString());
				LogGEN.writeTrace("Log", "Error Trace in Web Serviice :"+ex.getStackTrace());
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Rate_Convert</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to transfer funds.</td></Output>";

			}
			//}
			//else
			//{
		    	//LogGEN.writeTrace("Log", "Failed");
		    	//sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_GETCUSTINFO</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to fetch etihad guest.</td></Output>";
			//}
			String Status="";
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
			{
				Status="Success";
			}
			else
				Status="Failure";
			
			
			 sHandler.readCabProperty("JTS");	
			 
				
				String dburl=(String)currentCabPropertyMap.get("DBURL");
				String dbuser=(String)currentCabPropertyMap.get("USER");
				String dbpass=(String)currentCabPropertyMap.get("PASS");
				
				
				String inputXml=rate_stub.getinputXML(rate_req_msg);
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
				  * //Added By Harish For inserting original mssg
				  * Date			:	17 Mar 2015
				  * Description 	:	Replace execute with executeClob
				  */
					 String Query="insert into usr_0_other_calls_web_log(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
						"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
						"?,sysdate,'"+  Status + "')";
					 LogGEN.writeTrace("Log"," Add Cheque Query : finally :"+Query);
					 LogGEN.writeTrace("Log","sOrg_FXRT_Ot : finally :"+sOrg_FXRT_Ot);
					 try {
						 con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_FXRT_Ot.replaceAll("'", "''"));
					} catch (Exception e2) {
						// TODO: handle exception
						e2.getMessage();
					}
			
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Rate_Convert</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to get rate.</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput.trim().length());	
			LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput);	

			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Rate_Convert</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to get rate.</td></Output>";
			}
			
			LogGEN.writeTrace("Log","outputXML : finally :"+sOutput);
			return sOutput;			
		}
	}
}
