package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqFinanceOperationDtlStub;
import com.newgen.dscop.stub.InqFinanceOperationDtlStub.GetLoanIslamicOperativeInfoReq;
import com.newgen.dscop.stub.InqFinanceOperationDtlStub.GetLoanIslamicOperativeInfoReq_type0;
import com.newgen.dscop.stub.InqFinanceOperationDtlStub.GetLoanIslamicOperativeInfoReqMsg;
import com.newgen.dscop.stub.InqFinanceOperationDtlStub.GetLoanIslamicOperativeInfoRes;
import com.newgen.dscop.stub.InqFinanceOperationDtlStub.GetLoanIslamicOperativeInfoRes_type0;
import com.newgen.dscop.stub.InqFinanceOperationDtlStub.GetLoanIslamicOperativeInfoResMsg;
import com.newgen.dscop.stub.InqFinanceOperationDtlStub.Header;
import com.newgen.dscop.stub.InqFinanceOperationDtlStub.HeaderType;
import com.newgen.dscop.stub.InqFinanceOperationDtlStub.BalScheduleDetails;
import com.newgen.dscop.stub.InqFinanceOperationDtlStub.BalScheduleDetails_type0;
import com.newgen.dscop.stub.InqFinanceOperationDtlStub.LoanAcctNo;
import com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchEMIDetailsResMsg;

public class InqFinanceOperationDtl extends DSCOPServiceHandler
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

public String InqFinanceOperationDtl(String sInputXML) 
	{

		LogGEN.writeTrace("CBG_Log", "Fuction called InqFinanceOperationDtl");
		LogGEN.writeTrace("CBG_Log", "InqFinanceOperationDtl sInputXML---"+sInputXML);
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
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("InqFinanceOperationDtl");
			LogGEN.writeTrace("CBG_Log", "InqFinanceOperationDtl WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "InqFinanceOperationDtl WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "InqFinanceOperationDtl CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "InqFinanceOperationDtl USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "InqFinanceOperationDtl PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "InqFinanceOperationDtl LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "InqFinanceOperationDtl TIME_OUT: "+lTimeOut);

			String ref_no=xmlDataParser.getValueOf("REF_NO");
			String customerId=xmlDataParser.getValueOf("customerId");
			String LoanAcctNo=xmlDataParser.getValueOf("LoanAcctNo");
			String settlementValDate=xmlDataParser.getValueOf("settlementValDate");
			String esfPercentage=xmlDataParser.getValueOf("esfPercentage");
			String esfAmount=xmlDataParser.getValueOf("esfAmount");
			String loanForeClosureFlag=xmlDataParser.getValueOf("loanForeClosureFlag");
			String productCategory=xmlDataParser.getValueOf("productCategory");

			LogGEN.writeTrace("CBG_Log", "customerId :"+customerId);
			LogGEN.writeTrace("CBG_Log", "ref_no :"+ref_no);
			LogGEN.writeTrace("CBG_Log", "LoanAcctNo :"+LoanAcctNo);
			
			

			
			HeaderType Header_Input = new HeaderType();
		
			Header_Input.setUsecaseID("121");
			Header_Input.setServiceName("InqFinanceOperationDtl");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			
			InqFinanceOperationDtlStub FinanceOper =new InqFinanceOperationDtlStub(sWSDLPath);
			GetLoanIslamicOperativeInfoReq getLoanIsmReq=new GetLoanIslamicOperativeInfoReq();
			GetLoanIslamicOperativeInfoReq_type0 getLoanIsmReq_type0 = new GetLoanIslamicOperativeInfoReq_type0();
			GetLoanIslamicOperativeInfoReqMsg getLoanIsmReqMsg = new GetLoanIslamicOperativeInfoReqMsg();
			
			if(customerId != null || customerId != "" || !customerId.isEmpty())
			{
				//getLoanIsmReq_type0.setCID(customerId);
				getLoanIsmReq_type0.setCustomerId(customerId);
			}
			if(LoanAcctNo != null || LoanAcctNo != "" || !LoanAcctNo.isEmpty())
			{
				getLoanIsmReq_type0.setLoanAcctNo(LoanAcctNo);
			}
			getLoanIsmReq_type0.setEsfAmount(esfAmount);
			getLoanIsmReq_type0.setEsfPercentage(esfPercentage);
			getLoanIsmReq_type0.setLoanForeClosureFlag(loanForeClosureFlag);
			getLoanIsmReq_type0.setProductCategory(productCategory);
			getLoanIsmReq_type0.setSettlementValDate(settlementValDate);
			
			LogGEN.writeTrace("CBG_Log", "All values set11");
			getLoanIsmReq.setGetLoanIslamicOperativeInfoReq(getLoanIsmReq_type0);
			getLoanIsmReqMsg.setGetLoanIslamicOperativeInfoReq(getLoanIsmReq_type0);
			getLoanIsmReqMsg.setHeader(Header_Input);	
			FinanceOper._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			xmlInput=FinanceOper.getLoanIslamicOperativeInfoXML(getLoanIsmReqMsg);
			LogGEN.writeTrace("CBG_Log", "inupt xml : "+xmlInput);

			GetLoanIslamicOperativeInfoResMsg getLoanIsmResMsg = FinanceOper.getLoanIslamicOperativeInfo_Oper(getLoanIsmReqMsg);
			sOrg_Output =  FinanceOper.outputXML;
			LogGEN.writeTrace("CBG_Log", "output xml : "+sOrg_Output);
		    sOrg_Output = sOrg_Output.replaceAll("ns0:", "").replaceAll("ns1:", "").replaceAll("ns2:", "");
			com.newgen.omni.jts.cmgr.XMLParser parser = new com.newgen.omni.jts.cmgr.XMLParser(sOrg_Output);
			LogGEN.writeTrace("CBG_Log", "output xml : "+sOrg_Output);

			LogGEN.writeTrace("CBG_Log", "sErrorDesc :"+sErrorDesc);
			sReturnCode = parser.getValueOf("returnCode");
			LogGEN.writeTrace("CBG_Log", "sErrorDesc :"+sReturnCode);

			sOutput = sOrg_Output;
			/*
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
				GetLoanCommonDetailsRes_type0 loanDetailRes = new GetLoanCommonDetailsRes_type0();
				loanDetailRes=res_msg.getGetLoanCommonDetailsRes();
			
				sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>InqLoanBasicInfo</Option>" +
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
				
				if (loanDetailRes != null) 
				{	
			
					Loan_Common_Details_type0 loanDetailsType0 =  new Loan_Common_Details_type0();
					loanDetailsType0 = loanDetailRes.getLoan_Common_Details();
					Loan_Details_type0[]  loan_Details_type0;
					loan_Details_type0 = loanDetailsType0.getLoan_Details();

					
							sOutput +=  "<GetLoanCommonDetailsRes>"+
							"<loan_Common_Details>";
							
							for (int i=0;i<loan_Details_type0.length ; i++)
							{
							sOutput +="<loan_Details>"
					                  +"<loanAcct>"+loan_Details_type0[i].getLoanAcct()+"</loanAcct>"
					                  +"<LAPSApplNo>"+loan_Details_type0[i].getLAPSApplNo()+"</LAPSApplNo>"
					                  +"<behaviouralScore>"+loan_Details_type0[i].getBehaviouralScore()+"</behaviouralScore>"
					                  +"<attritionScore>"+loan_Details_type0[i].getAttritionScore()+"</attritionScore>"
					                  +"<domicileBranchName>"+loan_Details_type0[i].getDomicileBranchName()+"</domicileBranchName>"
					                  +"<domicileBranchLiteral>"+loan_Details_type0[i].getDomicileBranchLiteral()+"</domicileBranchLiteral>"
					                  +"<domicileBranchCode>"+loan_Details_type0[i].getDomicileBranchCode()+"</domicileBranchCode>"
					                  +"<acctCurrencyCode>"+loan_Details_type0[i].getAcctCurrencyCode()+"</acctCurrencyCode>"
					                  +"<acctCurrencyLiteral>"+loan_Details_type0[i].getAcctCurrencyLiteral()+"</acctCurrencyLiteral>"
					                  +"<acctCurrencyName>"+loan_Details_type0[i].getAcctCurrencyName()+"</acctCurrencyName>"
					                  +"<loanProductType>"+loan_Details_type0[i].getLoanProductType()+"</loanProductType>"
					                  +"<loanProductCode>"+loan_Details_type0[i].getLoanProductCode()+"</loanProductCode>"
					                  +"<loanProductName>"+loan_Details_type0[i].getLoanProductName()+"</loanProductName>"
					                  +"<loanAmt>"+loan_Details_type0[i].getLoanAmt()+"</loanAmt>"
					                  +"<loanTenor>"+loan_Details_type0[i].getLoanTenor()+"</loanTenor>"
					                  +"<loanInterestRate>"+loan_Details_type0[i].getLoanInterestRate()+"</loanInterestRate>"
					                  +"<disbursalDate>"+loan_Details_type0[i].getDisbursalDate()+"</disbursalDate>"
					                  +"<maturityDate>"+loan_Details_type0[i].getMaturityDate()+"</maturityDate>"
					                  +"<acctType>"+loan_Details_type0[i].getAcctType()+"</acctType>"
					                  +"<acctStatus>"+loan_Details_type0[i].getAcctStatus()+"</acctStatus>"
					                  +"<acctStatusCode>"+loan_Details_type0[i].getAcctStatusCode()+"</acctStatusCode>"
					                  +"<loanAccountOpeningDate>"+loan_Details_type0[i].getLoanAccountOpeningDate()+"</loanAccountOpeningDate>"
					                  +"<EMIAmt>"+loan_Details_type0[i].getEMIAmt()+"</EMIAmt>"
					                  +"<EMIMode>"+loan_Details_type0[i].getEMIMode()+"</EMIMode>"
					                  +"<linkedCASAAccount>"+loan_Details_type0[i].getLinkedCASAAccount()+"</linkedCASAAccount>"
					                  +"<nextEMIDate>"+loan_Details_type0[i].getNextEMIDate()+"</nextEMIDate>"
					                  +"<NoofEMIPaid>"+loan_Details_type0[i].getNoofEMIPaid()+"</NoofEMIPaid>"
					                  +"<outstandingBalance>"+loan_Details_type0[i].getOutstandingBalance()+"</outstandingBalance>"
					                  +"<loanFaceValue>"+loan_Details_type0[i].getLoanFaceValue()+"</loanFaceValue>"
					                  +"<netPayableAmount>"+loan_Details_type0[i].getNetPayableAmount()+"</netPayableAmount>"
					                  +"<homeBranchName>"+loan_Details_type0[i].getHomeBranchName()+"</homeBranchName>"
					                  +"<homeBranchLiteral>"+loan_Details_type0[i].getHomeBranchLiteral()+"</homeBranchLiteral>"
					                  +"<homeBranchCode>"+loan_Details_type0[i].getHomeBranchCode()+"</homeBranchCode>"
					                  +"<custShortName>"+loan_Details_type0[i].getCustShortName()+"</custShortName>"
					                  +"<repaymentModeFlag>"+loan_Details_type0[i].getRepaymentModeFlag()+"</repaymentModeFlag>"
					                  +"<repaymentModeDesc>"+loan_Details_type0[i].getRepaymentModeDesc()+"</repaymentModeDesc>"
					                  +"<totalOutstandingBalanceTCY>"+loan_Details_type0[i].getTotalOutstandingBalanceTCY()+"</totalOutstandingBalanceTCY>"
					                  +"<totalOutstandingBalanceLCY>"+loan_Details_type0[i].getTotalOutstandingBalanceLCY()+"</totalOutstandingBalanceLCY>"
					                  +"<moduleCode>"+loan_Details_type0[i].getModuleCode()+"</moduleCode>"
					                  +"<firstEMIDate>"+loan_Details_type0[i].getFirstEMIDate()+"</firstEMIDate>"
					                  +"<noOfArrears>"+loan_Details_type0[i].getNoOfArrears()+"</noOfArrears>"
					                  +"</loan_Details>";
							}
							sOutput +="</loan_Common_Details>"+
							"</GetLoanCommonDetailsRes></Output>";
				}
				else
				{
					sOutput +=  "<loan_Details>No Data Found</loan_Details>"+
									"</loan_Common_Details></GetLoanCommonDetailsRes></Output>";
				}
				
			}
			else
			{
				try
				{

					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqLoanBasicInfo</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Reason></Reason><Status>Unable to perform InqLoanBasicInfo service</Status></Output>";
				}
				catch(Exception e)
				{
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqLoanBasicInfo</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Reason></Reason><Status>Unable to perform InqLoanBasicInfo service</Status></Output>";

				}
			} */
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqFinanceOperationDtl</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>Unable to perform InqFinanceOperationDtl service</Status></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());	

			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqFinanceOperationDtl</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>Unable to perform InqFinanceOperationDtl service</Status></Output>";
			}
			String Status="";
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
			{
				Status="Success";
			}
			else
				Status="Failure";
			try {
				//sHandler.readCabProperty("JTS");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	

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
