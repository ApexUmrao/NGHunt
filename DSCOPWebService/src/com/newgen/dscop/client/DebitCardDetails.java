package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqDebitCardDtlStub;
import com.newgen.dscop.stub.InqDebitCardDtlStub.DebitCard_Details2_type0;
import com.newgen.dscop.stub.InqDebitCardDtlStub.DebitCards_type1;
import com.newgen.dscop.stub.InqDebitCardDtlStub.GetDebitCardDetailsReqMsg;
import com.newgen.dscop.stub.InqDebitCardDtlStub.GetDebitCardDetailsReq_type0;
import com.newgen.dscop.stub.InqDebitCardDtlStub.GetDebitCardDetailsResMsg;
import com.newgen.dscop.stub.InqDebitCardDtlStub.GetDebitCardDetailsRes_type0;
import com.newgen.dscop.stub.InqDebitCardDtlStub.HeaderType;
import com.newgen.dscop.stub.InqDebitCardDtlStub.LinkedAccounts_type1;

public class DebitCardDetails extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String sOrg_DB_Details_Ot=null;

	/**
	 * Function written to fetch Debit Card Details
	 * @author Gaurav.berry
	 * @param sInputXML
	 * @return
	 */

	@SuppressWarnings("finally")
	public String FetchDebitCardDetails(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Fuction called FetchDebitCardDetails");
		LogGEN.writeTrace("CBG_Log", "DebitCardDetails sInputXML---");
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc = "";
		String sOutput="";
		String xmlInput="";
		DSCOPServiceHandler sHandler= new DSCOPServiceHandler();
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		try
		{
			//sHandler.readCabProperty("Debit_Card_Information");

			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("Debit_Card_Information");
			LogGEN.writeTrace("CBG_Log", "Debit_Card_Information WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "Debit_Card_Information WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "Debit_Card_Information CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "Debit_Card_Information USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "Debit_Card_Information PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "Debit_Card_Information LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "Debit_Card_Information TIME_OUT: "+lTimeOut);


			String sCustomerID= xmlDataParser.getValueOf("CUST_ID");
			String sAccountNum= xmlDataParser.getValueOf("ACCOUNT_NUM");
			String sdebitCardNum= xmlDataParser.getValueOf("DEBIT_CARD_NUMBER");
			String ref_no=xmlDataParser.getValueOf("REF_NO");
			LogGEN.writeTrace("CBG_Log", "sCustomerID---"+sCustomerID);
			LogGEN.writeTrace("CBG_Log", "sDate---"+sDate);

			InqDebitCardDtlStub Debit_Card = new InqDebitCardDtlStub(sWSDLPath);
			GetDebitCardDetailsReqMsg Debit_Card_Req_Msg = new GetDebitCardDetailsReqMsg();
			GetDebitCardDetailsResMsg Debit_Card_Rep_Msg = new GetDebitCardDetailsResMsg();
			GetDebitCardDetailsReq_type0 Debit_Card_Req_0 = new GetDebitCardDetailsReq_type0();
			GetDebitCardDetailsRes_type0 Debit_Card_Rep_0 = new GetDebitCardDetailsRes_type0();
			DebitCard_Details2_type0 Debit_Card_Details2_0 = new DebitCard_Details2_type0();
			DebitCards_type1 [] Debit_Card_Type1; 
			HeaderType Header_Input = new HeaderType();
			LogGEN.writeTrace("CBG_Log", "All Objects created");

			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("InqDebitCardDtl");
			Header_Input.setVersionNo("2.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setUsername(sCustomerID);
			Header_Input.setCredentials(sCustomerID);
			LogGEN.writeTrace("CBG_Log", "All values set11");
			Debit_Card_Req_0.setCID(sCustomerID);
			Debit_Card_Req_0.setCustAcctNo(sAccountNum);
			Debit_Card_Req_0.setDebitCardNumber(sdebitCardNum);				
			Debit_Card_Req_Msg.setGetDebitCardDetailsReq(Debit_Card_Req_0);
			Debit_Card_Req_Msg.setHeader(Header_Input);
			LogGEN.writeTrace("CBG_Log", "All values set");

			xmlInput=Debit_Card.getinputXml(Debit_Card_Req_Msg);

			Debit_Card._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			Debit_Card_Rep_Msg =Debit_Card.inqDebitCardDtl_Oper(Debit_Card_Req_Msg);
			sOrg_DB_Details_Ot=Debit_Card.resDCDtlMsg;			
			Header_Input = Debit_Card_Rep_Msg.getHeader();
			sReturnCode=  Header_Input.getReturnCode();
			sErrorDetail=Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();

			LogGEN.writeTrace("CBG_Log", "Return Code---"+sReturnCode);
			LogGEN.writeTrace("CBG_Log", "Error Detail---"+sErrorDetail);
			LogGEN.writeTrace("CBG_Log", "Error Description---"+sErrorDesc);
			System.out.println("Details:"+sErrorDetail);
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				//				String Output1="";
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>Debit_Card_Information</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>";

				try
				{
					if(Debit_Card_Rep_Msg.isGetDebitCardDetailsResSpecified())
					{
						Debit_Card_Rep_0= Debit_Card_Rep_Msg.getGetDebitCardDetailsRes();				   
						Debit_Card_Details2_0=Debit_Card_Rep_0.getDebitCard_Details2();
						Debit_Card_Type1 = Debit_Card_Details2_0.getDebitCards();
						LinkedAccounts_type1[] Link_Acc_type_1;
						Link_Acc_type_1=Debit_Card_Type1[0].getLinkedAccounts();

						sOutput+="<listOfDebitCards>" ;
						//						Output1=sOutput;

						for(int i=0;i<Debit_Card_Type1.length;i++)
						{
							sOutput+="<debitCards>" +
									"<cardNumber>"+Debit_Card_Type1[i].getCardNumber()+"</cardNumber>"+
									"<customerId>"+Debit_Card_Type1[i].getCustomerId()+"</customerId>"+
									"<cardType>"+Debit_Card_Type1[i].getCardType()+"</cardType>"+
									"<cardCurrency>"+Debit_Card_Type1[i].getCardCurrency()+"</cardCurrency>"+
									"<cardTypeDescription>"+Debit_Card_Type1[i].getCardTypeDescription()+"</cardTypeDescription>"+
									"<debitProductGroup>"+Debit_Card_Type1[i].getDebitProductGroup()+"</debitProductGroup>"+					    	
									"<embossName>"+Debit_Card_Type1[i].getEmbossName()+"</embossName>"+
									"<status>"+Debit_Card_Type1[i].getStatus()+"</status>"+
									"<statusDescription>"+Debit_Card_Type1[i].getStatusDescription()+"</statusDescription>";



							Link_Acc_type_1=Debit_Card_Type1[i].getLinkedAccounts();

							for(int j=0;j<Link_Acc_type_1.length;j++)
							{
								sOutput+="<linkedAccounts>" +
										"<linkedAccount>"+Link_Acc_type_1[j].getLinkedAccount().trim()+"</linkedAccount>"+
										"<linkedAccountType>"+Link_Acc_type_1[j].getLinkedAccountType()+"</linkedAccountType>"+
										"</linkedAccounts>";

								//								Output1+="<LinkedAccounts>" +
								//										"<LinkAcc>"+Link_Acc_type_1[j].getLinkedAccount().trim()+"</LinkAcc>"+
								//										"<LinkAccType>"+Link_Acc_type_1[j].getLinkedAccountType()+"</LinkAccType>"+
								//										"</LinkedAccounts>";

							}
							sOutput+="</debitCards>" ;
							//							Output1+="</DebitCard>" ;
						}

						sOutput+="</listOfDebitCards></Output>";
						//						Output1+="</listOfDebitCards></Output>";
					}
					else
					{
						sOutput+="</Output>";
						//						Output1+="</Output>";
					}
					//						LogGEN.writeTrace("CBG_Log", "Debit_Card_Information Output XML--- "+Output1);
				}
				catch(Exception ee)
				{
					ee.printStackTrace();
					sOutput+="</Output>";
				}
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Debit_Card_Information Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>Debit_Card_Information</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to search debit card.</td></Output>";
			}
		}
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Debit_Card_Information Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Debit_Card_Information Error Trace in Web Serviice :"+e.getStackTrace());
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Debit_Card_Information</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to search debit card.</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			//LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+Output1.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Debit_Card_Information</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to search debit card.</td></Output>";
			}

			String Status="";
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
			{
				Status="Success";
			}
			else
				Status="Failure";

			String dburl=(String)currentCabPropertyMap.get("DBURL");
			String dbuser=(String)currentCabPropertyMap.get("USER");
			String dbpass=(String)currentCabPropertyMap.get("PASS");


			String inputXml=xmlInput;
			//					LogGEN.writeTrace("CBG_Log", inputXml);
			String winame=xmlDataParser.getValueOf("WiName");
			String sessionID= xmlDataParser.getValueOf("SessionId");
			String call_type=xmlDataParser.getValueOf("DSCOPCallType");
			sCabinet=xmlDataParser.getValueOf("EngineName");

			try
			{
				dbpass=AESEncryption.decrypt(dbpass);
			}
			catch(Exception e)
			{
				e.getMessage();
			}
			DBConnection con=new DBConnection();

			String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
			LogGEN.writeTrace("CBG_Log"," Add Cheque Query : finally :"+Query);
			//						 LogGEN.writeTrace("CBG_Log","sOrg_DB_Details_Ot : finally :"+sOrg_DB_Details_Ot);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_DB_Details_Ot.replaceAll("'", "''"));
			} catch (Exception e2) {

				e2.getMessage();
			}
			//				LogGEN.writeTrace("CBG_Log","Debit_Card_Information outputXML : finally :"+sOutput);
			return sOutput;			
		}
	}
}
