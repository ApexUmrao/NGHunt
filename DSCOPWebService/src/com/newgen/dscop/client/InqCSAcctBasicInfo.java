package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqCSAcctBasicInfoStub;
import com.newgen.dscop.stub.InqCSAcctBasicInfoStub.Accounts_type0;
import com.newgen.dscop.stub.InqCSAcctBasicInfoStub.Acct_Basic_Details_type0;
import com.newgen.dscop.stub.InqCSAcctBasicInfoStub.GetAcctBasicDetailsReq;
import com.newgen.dscop.stub.InqCSAcctBasicInfoStub.GetAcctBasicDetailsReqMsg;
import com.newgen.dscop.stub.InqCSAcctBasicInfoStub.GetAcctBasicDetailsReq_type0;
import com.newgen.dscop.stub.InqCSAcctBasicInfoStub.GetAcctBasicDetailsResMsg;
import com.newgen.dscop.stub.InqCSAcctBasicInfoStub.GetAcctBasicDetailsRes_type0;
import com.newgen.dscop.stub.InqCSAcctBasicInfoStub.HeaderType;

public class InqCSAcctBasicInfo extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected long lTimeOut = 30000;
	String sOrg_Output=null;

	String xmlInput="";
	@SuppressWarnings("finally")
	public String acctBasicInfo(String sInputXML) 
	{

		LogGEN.writeTrace("CBG_Log", "Fuction called InqCustomerLendingDtls");
		LogGEN.writeTrace("CBG_Log", "InqCustomerLendingDtls sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";
		String sOutput= "";

		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		DSCOPServiceHandler sHandler= new DSCOPServiceHandler();
		try
		{

			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("InqCSAcctBasicInfo");
			LogGEN.writeTrace("CBG_Log", "InqCSAcctBasicInfo WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "InqCSAcctBasicInfo WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "InqCSAcctBasicInfo CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "InqCSAcctBasicInfo USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "InqCSAcctBasicInfo PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "InqCSAcctBasicInfo LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "InqCSAcctBasicInfo TIME_OUT: "+lTimeOut);

			String ref_no=xmlDataParser.getValueOf("REF_NO");
			String customerId=xmlDataParser.getValueOf("cust_id");
			String accountNumber=xmlDataParser.getValueOf("accountNumber");
			String accountIBAN=xmlDataParser.getValueOf("accountIBAN");


			LogGEN.writeTrace("CBG_Log", "customerId :"+customerId);
			LogGEN.writeTrace("CBG_Log", "ref_no :"+ref_no);
			LogGEN.writeTrace("CBG_Log", "accountNumber :"+accountNumber);

			InqCSAcctBasicInfoStub acctBasic =new InqCSAcctBasicInfoStub(sWSDLPath);
			GetAcctBasicDetailsReq_type0 getAcctBasicDetailsReq_type0 = new GetAcctBasicDetailsReq_type0();
			GetAcctBasicDetailsReq getAcctBasicDetailsReq  = new GetAcctBasicDetailsReq();
			GetAcctBasicDetailsReqMsg getAcctBasicDetailsReqMsg = new GetAcctBasicDetailsReqMsg();
			HeaderType Header_Input = new HeaderType();

			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("InqCSAcctBasicInfo");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setConsumer(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setCredentials(loggedinuser);
			Header_Input.setUsername(loggedinuser);


			if(customerId != null || customerId != "" || !customerId.isEmpty())
			{
				getAcctBasicDetailsReq_type0.setCID(customerId);
			}
			if(accountNumber != null || accountNumber != "" || !accountNumber.isEmpty())
			{
				getAcctBasicDetailsReq_type0.setAccountNumber(accountNumber);
			}
			if(accountIBAN != null || accountIBAN != "" || !accountIBAN.isEmpty())
			{
				getAcctBasicDetailsReq_type0.setAccountNumberIBAN(accountIBAN);
			}

			LogGEN.writeTrace("CBG_Log", "All values set11");

			getAcctBasicDetailsReq.setGetAcctBasicDetailsReq(getAcctBasicDetailsReq_type0);
			getAcctBasicDetailsReqMsg.setGetAcctBasicDetailsReq(getAcctBasicDetailsReq_type0);
			getAcctBasicDetailsReqMsg.setHeader(Header_Input);

			acctBasic._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			xmlInput=acctBasic.getinputXML(getAcctBasicDetailsReqMsg);
			LogGEN.writeTrace("CBG_Log", "inupt xml : "+xmlInput);
			GetAcctBasicDetailsResMsg res_msg= acctBasic.inqCSAcctBasicInfo_Oper(getAcctBasicDetailsReqMsg);
			sOrg_Output = acctBasic.responseAcctBaisc;
			LogGEN.writeTrace("CBG_Log", "output xml : "+sOrg_Output);
			HeaderType Header_output = new HeaderType();
			Header_output = res_msg.getHeader();

			sReturnCode=  Header_output.getReturnCode();
			sErrorDetail=Header_output.getErrorDetail();
			sErrorDesc = Header_output.getErrorDescription();
			LogGEN.writeTrace("CBG_Log", "sReturnCode :"+sReturnCode);
			LogGEN.writeTrace("CBG_Log", "sErrorDetail :"+sErrorDetail);
			LogGEN.writeTrace("CBG_Log", "sErrorDesc :"+sErrorDesc);

			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				GetAcctBasicDetailsRes_type0 fetchAccRes = new GetAcctBasicDetailsRes_type0();
				fetchAccRes=res_msg.getGetAcctBasicDetailsRes();

				sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>InqCSAcctBasicInfo</Option>" +
						"<header>"+
						"<usecaseID>"+Header_output.getUsecaseID()+"</usecaseID>"+
						"<serviceName>"+Header_output.getServiceName()+"</serviceName>"+
						"<versionNo>"+Header_output.getVersionNo()+"</versionNo>"+
						"<serviceAction>"+Header_output.getServiceAction()+"</serviceAction>"+
						"<correlationID>"+Header_output.getCorrelationID()+"</correlationID>"+
						"<sysRefNumber>"+Header_output.getSysRefNumber()+"</sysRefNumber>"+
						"<consumer>"+Header_output.getConsumer()+"</consumer>"+
						"<reqTimeStamp>"+Header_output.getRepTimeStamp()+"</reqTimeStamp>"+
						"<username>"+Header_output.getUsername()+"</username>"+
						"<credentials>"+Header_output.getCredentials()+"</credentials>"+
						"<returnCode>"+Header_output.getReturnCode()+"</returnCode>"+
						"<errorDescription>"+Header_output.getErrorDescription()+"</errorDescription>"+
						"<errorDetail>"+Header_output.getErrorDetail()+"</errorDetail>"+
						"</header>";

				if (fetchAccRes != null) 
				{	
					sOutput +=  "<GetAcctBasicDetailsRes>"+
							"<acct_Basic_Details>";

					Acct_Basic_Details_type0 acct_Basic_Details_type0 = fetchAccRes.getAcct_Basic_Details();
					if(acct_Basic_Details_type0 != null){
						Accounts_type0[]accounts_type = acct_Basic_Details_type0.getAccounts();
						if(accounts_type != null){
							for (int i=0;i<accounts_type.length ; i++)
							{
								sOutput += "<accounts>"+
										"<accountNo>"+accounts_type[i].getAccountNo()+"</accountNo>"+
										"<accountNumberIBAN>"+accounts_type[i].getAccountNumberIBAN()+"</accountNumberIBAN>"+
										"<acctTitle>"+accounts_type[i].getAcctTitle()+"</acctTitle>"+
										"<productCode>"+accounts_type[i].getProductCode()+"</productCode>"+
										"<productName>"+accounts_type[i].getProductName()+"</productName>"+
										"<productType>"+accounts_type[i].getProductType()+"</productType>"+
										"<modeofOperations>"+accounts_type[i].getModeofOperations()+"</modeofOperations>"+
										"<acctType>"+accounts_type[i].getAcctType()+"</acctType>"+
										"<mortgageFlag>"+accounts_type[i].getMortgageFlag()+"</mortgageFlag>"+
										"<acctBranchName>"+accounts_type[i].getAcctBranchName()+"</acctBranchName>"+
										"<acctBranchShortName>"+accounts_type[i].getAcctBranchShortName()+"</acctBranchShortName>"+
										"<acctBranchCode>"+accounts_type[i].getAcctBranchCode()+"</acctBranchCode>"+
										"<acctStatusCode>"+accounts_type[i].getAcctStatusCode()+"</acctStatusCode>"+
										"<acctStatusDescription>"+accounts_type[i].getAcctStatusDescription()+"</acctStatusDescription>"+
										"<acctCurrency>"+accounts_type[i].getAcctCurrency()+"</acctCurrency>"+
										"<acctCurrencyCode>"+accounts_type[i].getAcctCurrencyCode()+"</acctCurrencyCode>"+
										"<acctCurrencyLiteral>"+accounts_type[i].getAcctCurrencyLiteral()+"</acctCurrencyLiteral>"+
										"<acctOpenDate>"+accounts_type[i].getAcctOpenDate()+"</acctOpenDate>"+
										"<acctMemo>"+accounts_type[i].getAcctMemo()+"</acctMemo>"+
										"<memoDesc>"+accounts_type[i].getMemoDesc()+"</memoDesc>"+
										"<memoFlagSev>"+accounts_type[i].getMemoFlagSev()+"</memoFlagSev>"+
										"<prvEODBal>"+accounts_type[i].getPrvEODBal()+"</prvEODBal>"+
										"<netAvalWithdrawl>"+accounts_type[i].getNetAvalWithdrawl()+"</netAvalWithdrawl>"+
										"<unClearedBal>"+accounts_type[i].getUnClearedBal()+"</unClearedBal>"+
										"<amountOnHold>"+accounts_type[i].getAmountOnHold()+"</amountOnHold>"+
										"<totalODLimitAmt>"+accounts_type[i].getTotalODLimitAmt()+"</totalODLimitAmt>"+
										"<statementFlag>"+accounts_type[i].getStatementFlag()+"</statementFlag>"+
										"<statementFreq>"+accounts_type[i].getStatementFlag()+"</statementFreq>"+
										"<eStatementFlag>"+accounts_type[i].getEStatementFlag()+"</eStatementFlag>"+
										"<lastStatementDate>"+accounts_type[i].getLastStatementDate()+"</lastStatementDate>"+
										"<accountSourcedBy>"+accounts_type[i].getAccountSourcedBy()+"</accountSourcedBy>"+
										"<SIFlag>"+accounts_type[i].getSIFlag()+"</SIFlag>"+
										"<sweepInFlag>"+accounts_type[i].getSweepInFlag()+"</sweepInFlag>"+
										"<sweepOutFlag>"+accounts_type[i].getSweepOutFlag()+"</sweepOutFlag>"+
										"<holdFlag>"+accounts_type[i].getHoldFlag()+"</holdFlag>"+
										"<staffAcctFlag>"+accounts_type[i].getStaffAcctFlag()+"</staffAcctFlag>"+
										"<acctAddressFlag>"+accounts_type[i].getAcctAddressFlag()+"</acctAddressFlag>"+
										"<ATM>"+accounts_type[i].getATM()+"</ATM>"+
										"<Telebanking>"+accounts_type[i].getTelebanking()+"</Telebanking>"+
										"<pointOfSale>"+accounts_type[i].getPointOfSale()+"</pointOfSale>"+
										"<Internet>"+accounts_type[i].getInternet()+"</Internet>"+
										"<serviceChargePkg>"+accounts_type[i].getServiceChargePkg()+"</serviceChargePkg>"+
										"<SCPkgDesc>"+accounts_type[i].getSCPkgDesc()+"</SCPkgDesc>"+
										"<balAvailable>"+accounts_type[i].getBalAvailable()+"</balAvailable>"+
										"<jointAcctflag>"+accounts_type[i].getJointAcctflag()+"</jointAcctflag>"+
										"<overDrawnAmount>"+accounts_type[i].getOverDrawnAmount()+"</overDrawnAmount>"+
										"<prvEODBalLcy>"+accounts_type[i].getPrvEODBalLcy()+"</prvEODBalLcy>"+
										"<netAvalWithdrawlLcy>"+accounts_type[i].getNetAvalWithdrawlLcy()+"</netAvalWithdrawlLcy>"+
										"<totalODLimitAmtLcy>"+accounts_type[i].getTotalODLimitAmtLcy()+"</totalODLimitAmtLcy>"+
										"<amountOnHoldLcy>"+accounts_type[i].getAmountOnHoldLcy()+"</amountOnHoldLcy>"+
										"<balAvailableLcy>"+accounts_type[i].getBalAvailableLcy()+"</balAvailableLcy>"+
										"<unClearedBalLcy>"+accounts_type[i].getUnClearedBalLcy()+"</unClearedBalLcy>"+
										"<overDrawnAmountLcy>"+accounts_type[i].getOverDrawnAmountLcy()+"</overDrawnAmountLcy>"+
										"<totCrBalAvgYy>"+accounts_type[i].getTotCrBalAvgYy()+"</totCrBalAvgYy>"+
										"<totCrBalAvgYyLcy>"+accounts_type[i].getTotCrBalAvgYyLcy()+"</totCrBalAvgYyLcy>"+
										"</accounts>";
							}
						}
					}
					sOutput +="</acct_Basic_Details>"+
							"</GetAcctBasicDetailsRes></Output>";
				}
				else
				{
					sOutput +=  "<accounts>No Data Found</accounts>"+
							"</acct_Basic_Details>"+
							"</GetAcctBasicDetailsRes></Output>";
				}
			}
			else
			{
				try
				{

					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqCSAcctBasicInfo</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Reason></Reason><Status>Unable to perform InqCSAcctBasicInfo service</Status></Output>";
				}
				catch(Exception e)
				{
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqCSAcctBasicInfo</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Reason></Reason><Status>Unable to perform InqCSAcctBasicInfo service</Status></Output>";

				}
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqCSAcctBasicInfo</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>Unable to perform InqCSAcctBasicInfo service</Status></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());	

			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqCSAcctBasicInfo</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>Unable to perform InqCSAcctBasicInfo service</Status></Output>";
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
			LogGEN.writeTrace("CBG_Log", inputXml);
			String winame=xmlDataParser.getValueOf("WiName");
			String sessionID= xmlDataParser.getValueOf("SessionId");
			String call_type=xmlDataParser.getValueOf("DSCOPCallType");
			sCabinet=xmlDataParser.getValueOf("EngineName");
			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

			try
			{
				dbpass=AESEncryption.decrypt(dbpass);
			}
			catch(Exception e)
			{
				LogGEN.writeTrace("CBG_Log", "Error Trace in AESEncryption :"+e.getStackTrace());
			}
			DBConnection con=new DBConnection();

			String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
			LogGEN.writeTrace("CBG_Log"," perform Delinquent service finally :"+Query);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_Output.replaceAll("'", "''"));
			} catch (Exception e2) {
				// TODO: handle exception
			}
			return sOutput;			
		}
	}
}