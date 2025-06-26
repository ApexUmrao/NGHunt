package com.newgen.client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.stub.InqDebitCardDtlStub.DebitCard_Details2_type0;
import com.newgen.stub.InqDebitCardDtlStub.DebitCards_type1;
import com.newgen.stub.InqDebitCardDtlStub.GetDebitCardDetailsReqMsg;
import com.newgen.stub.InqDebitCardDtlStub.GetDebitCardDetailsReq_type0;
import com.newgen.stub.InqDebitCardDtlStub.GetDebitCardDetailsResMsg;
import com.newgen.stub.InqDebitCardDtlStub.GetDebitCardDetailsRes_type0;
import com.newgen.stub.InqDebitCardDtlStub.HeaderType;
import com.newgen.stub.InqDebitCardDtlStub.LinkedAccounts_type1;
import com.newgen.stub.*;

public class DebitCardDetails extends WebServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String sOrg_DB_Details_Ot=null;//Added By Harish For inserting original mssg
	/**
	 * Function written to fetch Debit Card Details
	 * @author gupta.ashish
	 * @param sInputXML
	 * @return
	 */

		@SuppressWarnings("finally")
		public String FetchDebitCardDetails(String sInputXML) 
		{
			LogGEN.writeTrace("Log", "Fuction called FetchDebitCardDetails");
			LogGEN.writeTrace("Log", "sInputXML---"+sInputXML);
			XMLParser xmlDataParser = new XMLParser();
			xmlDataParser.setInputXML(sInputXML);
			String sReturnCode= "";
			String sErrorDetail="";
			String sErrorDesc = "";
			String sOutput="";
			String xmlInput="";
			WebServiceHandler sHandler= new WebServiceHandler();
			Date d= new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String sDate = dateFormat.format(d);
			try
			{
				sHandler.readCabProperty("Debit_Card_Information");
				
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
				LogGEN.writeTrace("Log", "sDate---"+sDate);
				
				InqDebitCardDtlStub Debit_Card = new InqDebitCardDtlStub(sWSDLPath);
				GetDebitCardDetailsReqMsg Debit_Card_Req_Msg = new GetDebitCardDetailsReqMsg();
				GetDebitCardDetailsResMsg Debit_Card_Rep_Msg = new GetDebitCardDetailsResMsg();
				GetDebitCardDetailsReq_type0 Debit_Card_Req_0 = new GetDebitCardDetailsReq_type0();
				GetDebitCardDetailsRes_type0 Debit_Card_Rep_0 = new GetDebitCardDetailsRes_type0();
				DebitCard_Details2_type0 Debit_Card_Details2_0 = new DebitCard_Details2_type0();
				DebitCards_type1 [] Debit_Card_Type_1; 
				HeaderType Header_Input = new HeaderType();
				LogGEN.writeTrace("Log", "All Objects created");
				
				Header_Input.setUsecaseID("1234");
				Header_Input.setServiceName("InqDebitCardDtl");
				Header_Input.setVersionNo("2.0");
				Header_Input.setServiceAction("Inquiry");
				Header_Input.setSysRefNumber(ref_no);
				Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
				Header_Input.setReqTimeStamp(sDate);
				Header_Input.setUsername(sCustomerID);
				Header_Input.setCredentials(xmlDataParser.getValueOf("CUST_ID"));
				LogGEN.writeTrace("Log", "All values set11");
				Debit_Card_Req_0.setCID(sCustomerID);
				Debit_Card_Req_0.setCustAcctNo("");
				Debit_Card_Req_0.setDebitCardNumber("");				
				Debit_Card_Req_Msg.setGetDebitCardDetailsReq(Debit_Card_Req_0);
				Debit_Card_Req_Msg.setHeader(Header_Input);
				LogGEN.writeTrace("Log", "All values set");
			
				xmlInput=Debit_Card.getinputXml(Debit_Card_Req_Msg);
				
				Debit_Card._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
				Debit_Card_Rep_Msg =Debit_Card.inqDebitCardDtl_Oper(Debit_Card_Req_Msg);
				sOrg_DB_Details_Ot=Debit_Card.resDCDtlMsg;			
				Header_Input = Debit_Card_Rep_Msg.getHeader();
				sReturnCode=  Header_Input.getReturnCode();
				sErrorDetail=Header_Input.getErrorDetail();
				sErrorDesc = Header_Input.getErrorDescription();
				
				LogGEN.writeTrace("Log", "Return Code---"+sReturnCode);
				LogGEN.writeTrace("Log", "Error Detail---"+sErrorDetail);
				LogGEN.writeTrace("Log", "Error Description---"+sErrorDesc);
				System.out.println("Details:"+sErrorDetail);
				if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
				{
					String Output1="";
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
					    Debit_Card_Type_1 = Debit_Card_Details2_0.getDebitCards();
					    LinkedAccounts_type1 [] Link_Acc_type_1;
					Link_Acc_type_1=Debit_Card_Type_1[0].getLinkedAccounts();
					
					sOutput+="<listOfDebitCards>" ;
					Output1=sOutput;
							
					    for(int i=0;i<Debit_Card_Type_1.length;i++)
						{
					    	sOutput+="<DebitCard>" +
					    	"<CardNo>"+Debit_Card_Type_1[i].getCardNumber().trim()+"</CardNo>"+
					    	"<CustID>"+Debit_Card_Type_1[i].getCustomerId()+"</CustID>"+
					    	"<CardType>"+Debit_Card_Type_1[i].getCardType()+"</CardType>"+
					    	"<CardTypeDesc>"+Debit_Card_Type_1[i].getCardTypeDescription()+"</CardTypeDesc>"+
					    	"<ProductGrp>"+Debit_Card_Type_1[i].getDebitProductGroup()+"</ProductGrp>"+
					    	"<CardName>"+Debit_Card_Type_1[i].getEmbossName()+"</CardName>"+
					    	"<Status>"+Debit_Card_Type_1[i].getStatus()+"</Status>"+					    	
					    	"<StatusDesc>"+Debit_Card_Type_1[i].getStatusDescription()+"</StatusDesc>"+
					    	"<StatusChangeDate>"+Debit_Card_Type_1[i].getStatusChangeDate()+"</StatusChangeDate>"+
					    	"<StatusChangeUser>"+Debit_Card_Type_1[i].getStatusChangeUser()+"</StatusChangeUser>"+
					    	"<ExpDate>"+Debit_Card_Type_1[i].getExpiryDate()+"</ExpDate>"+	
					    	//fcr changes
					    	
					    	"<Currency>"+Debit_Card_Type_1[i].getCardCurrency()+"</Currency>"+
					    	"<PrevExpDate>"+Debit_Card_Type_1[i].getPrevExpiryDate()+"</PrevExpDate>"+
					    	"<PrevStatus>"+Debit_Card_Type_1[i].getPrevStatus()+"</PrevStatus>"+
					    	"<PrevStatusDesc>"+Debit_Card_Type_1[i].getPrevStatusDescription()+"</PrevStatusDesc>"+
					    	"<PrevStatusChangeDate>"+Debit_Card_Type_1[i].getPrevStatusChangeDate()+"</PrevStatusChangeDate>"+
					    	"<PrevStatusChangeUser>"+Debit_Card_Type_1[i].getPrevStatusChangeUser()+"</PrevStatusChangeUser>"+
					    	"<SetupDate>"+Debit_Card_Type_1[i].getSetupDate()+"</SetupDate>"+
					    	"<LastReplaceDate>"+Debit_Card_Type_1[i].getLastReplacedDate()+"</LastReplaceDate>"+
					    	"<OldCardNo>"+Debit_Card_Type_1[i].getOldCardNumber()+"</OldCardNo>"+
					    	"<LinkedAccountCount>"+Debit_Card_Type_1[i].getLinkedAccountsCount()+"</LinkedAccountCount>";
					    	
					    	Output1+="<DebitCard>" +
					    	"<CardNo>XXXXXXXXXXXXXXXX</CardNo>"+
					    	"<CustID>"+Debit_Card_Type_1[i].getCustomerId()+"</CustID>"+
					    	"<CardType>"+Debit_Card_Type_1[i].getCardType()+"</CardType>"+
					    	"<CardTypeDesc>"+Debit_Card_Type_1[i].getCardTypeDescription()+"</CardTypeDesc>"+
					    	"<ProductGrp>"+Debit_Card_Type_1[i].getDebitProductGroup()+"</ProductGrp>"+
					    	"<CardName>"+Debit_Card_Type_1[i].getEmbossName()+"</CardName>"+
					    	"<Status>"+Debit_Card_Type_1[i].getStatus()+"</Status>"+
					    	"<StatusDesc>"+Debit_Card_Type_1[i].getStatusDescription()+"</StatusDesc>"+
					    	"<StatusChangeDate>"+Debit_Card_Type_1[i].getStatusChangeDate()+"</StatusChangeDate>"+
					    	"<StatusChangeUser>"+Debit_Card_Type_1[i].getStatusChangeUser()+"</StatusChangeUser>"+
					    	"<ExpDate>"+Debit_Card_Type_1[i].getExpiryDate()+"</ExpDate>"+					    	
					    	"<PrevExpDate>"+Debit_Card_Type_1[i].getPrevExpiryDate()+"</PrevExpDate>"+
					    	"<PrevStatus>"+Debit_Card_Type_1[i].getPrevStatus()+"</PrevStatus>"+
					    	"<PrevStatusDesc>"+Debit_Card_Type_1[i].getPrevStatusDescription()+"</PrevStatusDesc>"+
					    	"<PrevStatusChangeDate>"+Debit_Card_Type_1[i].getPrevStatusChangeDate()+"</PrevStatusChangeDate>"+
					    	"<PrevStatusChangeUser>"+Debit_Card_Type_1[i].getPrevStatusChangeUser()+"</PrevStatusChangeUser>"+
					    	"<SetupDate>"+Debit_Card_Type_1[i].getSetupDate()+"</SetupDate>"+
					    	"<LastReplaceDate>"+Debit_Card_Type_1[i].getLastReplacedDate()+"</LastReplaceDate>"+
					    	"<OldCardNo>XXXXXXXXXXXXXXXX</OldCardNo>"+
					    	"<LinkedAccountCount>"+Debit_Card_Type_1[i].getLinkedAccountsCount()+"</LinkedAccountCount>";
					    	
					    	Link_Acc_type_1=Debit_Card_Type_1[i].getLinkedAccounts();
					    	
					    	for(int j=0;j<Link_Acc_type_1.length;j++)
							{
					    		sOutput+="<LinkedAccounts>" +
					    				
					    		"<LinkAcc>"+Link_Acc_type_1[j].getLinkedAccount().trim()+"</LinkAcc>"+
						    	"<LinkAccType>"+Link_Acc_type_1[j].getLinkedAccountType()+"</LinkAccType>"+
						    	"</LinkedAccounts>";
					    		
					    		Output1+="<LinkedAccounts>" +
					    		"<LinkAcc>"+Link_Acc_type_1[j].getLinkedAccount().trim()+"</LinkAcc>"+
						    	"<LinkAccType>"+Link_Acc_type_1[j].getLinkedAccountType()+"</LinkAccType>"+
						    	"</LinkedAccounts>";
					    		
							}
					    	sOutput+="</DebitCard>" ;
					    	Output1+="</DebitCard>" ;
						}
						
					    sOutput+="</listOfDebitCards></Output>";
					    Output1+="</listOfDebitCards></Output>";
						}
						else
						{
							sOutput+="</Output>";
							Output1+="</Output>";
						}
						LogGEN.writeTrace("Log", "Output XML--- "+Output1);
					}
					catch(Exception ee)
					{
						ee.printStackTrace();
						sOutput+="</Output>";
					}
				}
				else
				{
			    	LogGEN.writeTrace("Log", "Failed");
			    	sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>Debit_Card_Information</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to search debit card.</td></Output>";
				}
			}
			catch (Exception e)
			{
				LogGEN.writeTrace("Log", "Error in Web Serviice :"+e.toString());
				LogGEN.writeTrace("Log", "Error Trace in Web Serviice :"+e.getStackTrace());
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Debit_Card_Information</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to search debit card.</td></Output>";
				e.printStackTrace();
			}
			finally
			{
				//LogGEN.writeTrace("Log","outputXML.trim().length() :"+Output1.trim().length());				
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
				
				 try {
					sHandler.readCabProperty("JTS");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
						 LogGEN.writeTrace("Log","sOrg_DB_Details_Ot : finally :"+sOrg_DB_Details_Ot);
						 try {
							 con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_DB_Details_Ot.replaceAll("'", "''"));
						} catch (Exception e2) {
							// TODO: handle exception
							e2.getMessage();
						}
				
				
				
				LogGEN.writeTrace("Log","outputXML : finally :"+sOutput);
				return sOutput;			
			}
		}
}
