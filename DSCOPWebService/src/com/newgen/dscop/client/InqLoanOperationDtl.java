package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqLoanOperationDtlStub;
import com.newgen.dscop.stub.InqLoanOperationDtlStub.BalScheduleDetails_type1;
import com.newgen.dscop.stub.InqLoanOperationDtlStub.Delinquency_type1;
import com.newgen.dscop.stub.InqLoanOperationDtlStub.GetLoanOperativeInfoReqMsg;
import com.newgen.dscop.stub.InqLoanOperationDtlStub.GetLoanOperativeInfoReq_type0;
import com.newgen.dscop.stub.InqLoanOperationDtlStub.GetLoanOperativeInfoResMsg;
import com.newgen.dscop.stub.InqLoanOperationDtlStub.GetLoanOperativeInfoRes_type0;
import com.newgen.dscop.stub.InqLoanOperationDtlStub.HeaderType;
import com.newgen.dscop.stub.InqLoanOperationDtlStub.Limits_type1;
import com.newgen.dscop.stub.InqLoanOperationDtlStub.LinkedCustomer_type1;
import com.newgen.dscop.stub.InqLoanOperationDtlStub.Loan_Operative_Info_type0;
import com.newgen.dscop.stub.InqLoanOperationDtlStub.PDC_type1;
import com.newgen.dscop.stub.InqLoanOperationDtlStub.StandingInst_type1;

public class InqLoanOperationDtl extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected long lTimeOut = 30000;
	String sOrg_Output="";
	String xmlInput = "";

	@SuppressWarnings("finally")
	public String InqLoanOperationDtlCall(String sInputXML) 
	{

		LogGEN.writeTrace("CBG_Log", "Fuction called InqLoanOperationDtl");
		LogGEN.writeTrace("CBG_Log", "InqLoanOperationDtl sInputXML---");
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";
		String sOutput= "";

		DSCOPServiceHandler sHandler= new DSCOPServiceHandler();
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		try
		{
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("InqLoanOperationDtl");
			LogGEN.writeTrace("CBG_Log", "InqLoanOperationDtl WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "InqLoanOperationDtl WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "InqLoanOperationDtl CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "InqLoanOperationDtl USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "InqLoanOperationDtl PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "InqLoanOperationDtl LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "InqLoanOperationDtl TIME_OUT: "+lTimeOut);

			String ref_no = xmlDataParser.getValueOf("REF_NO");
			String loanAccountNo =xmlDataParser.getValueOf("LoanAcctNo");
			sCabinet = xmlDataParser.getValueOf("EngineName");
			String senderId = xmlDataParser.getValueOf("SenderId");
			String customerId = xmlDataParser.getValueOf("customerId");
			String settlementValDate = xmlDataParser.getValueOf("settlementValDate");
			String loanForClosureFlag = xmlDataParser.getValueOf("loanForeClosureFlag");

			//Parsing inputXML to retrieve tag values: 
			String sCustomerID = xmlDataParser.getValueOf("CUST_ID");
			String sRef_no = xmlDataParser.getValueOf("REF_NO");

			InqLoanOperationDtlStub Inq_Loan_Operation_Dtl_stub=new InqLoanOperationDtlStub(sWSDLPath);
			GetLoanOperativeInfoReq_type0 loan_operation_req=new GetLoanOperativeInfoReq_type0();
			GetLoanOperativeInfoReqMsg loan_operation_msg=new GetLoanOperativeInfoReqMsg();


			HeaderType Header_Input = new HeaderType();

			//Setting Request Header params:
			Header_Input.setUsecaseID("121");
			Header_Input.setServiceName("InqLoanOperationDtl"); 
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setSysRefNumber(sRef_no);
			Header_Input.setSenderID(senderId);
			Header_Input.setReqTimeStamp(sDate);


			loan_operation_msg.setHeader(Header_Input);

			loan_operation_req.setCustomerId(customerId);
			loan_operation_req.setSettlementValDate(settlementValDate );
			loan_operation_req.setLoanAcctNo(loanAccountNo);
			loan_operation_req.setLoanForeClosureFlag(loanForClosureFlag);



			loan_operation_msg.setGetLoanOperativeInfoReq(loan_operation_req);;

			xmlInput=Inq_Loan_Operation_Dtl_stub.inqLoanOperationDtlXML(loan_operation_msg);
			Inq_Loan_Operation_Dtl_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			GetLoanOperativeInfoResMsg res_msg =Inq_Loan_Operation_Dtl_stub.inqLoanOperationDtl_Oper(loan_operation_msg);
			sOrg_Output=Inq_Loan_Operation_Dtl_stub.outputXML;
			Header_Input=res_msg.getHeader();
			sReturnCode=  Header_Input.getReturnCode();
			sErrorDetail=Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();

			LogGEN.writeTrace("CBG_Log", "CBG InqLoanOperationDtl Return Code---"+sReturnCode);
			LogGEN.writeTrace("CBG_Log", "CBG InqLoanOperationDtl Error Detail---"+sErrorDetail);
			LogGEN.writeTrace("CBG_Log", "CBG InqLoanOperationDtl Error Description---"+sErrorDesc);

			GetLoanOperativeInfoRes_type0 res_type0=res_msg.getGetLoanOperativeInfoRes();
			Loan_Operative_Info_type0 loan_Operative_Info = res_type0.getLoan_Operative_Info();
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{

				//				BalScheduleDetails_type1 balScheduleDetails=loan_Operative_Info.getBalScheduleDetails();
				//				PDC_type1 pdc = loan_Operative_Info.getPDC();
				//				Delinquency_type1 delinquency = loan_Operative_Info.getDelinquency();
				//				StandingInst_type1[] standingInst = loan_Operative_Info.getStandingInst();
				//				Limits_type1[] limits = loan_Operative_Info.getLimits();
				//				LinkedCustomer_type1[] linkedCustomer = loan_Operative_Info.getLinkedCustomer();

				BalScheduleDetails_type1 balScheduleDetails=loan_Operative_Info.getBalScheduleDetails();
				LogGEN.writeTrace("CBG_Log", "CBG InqLoanOperationDtl balScheduleDetails---"+balScheduleDetails);
				PDC_type1 pdc = loan_Operative_Info.getPDC();
				LogGEN.writeTrace("CBG_Log", "CBG InqLoanOperationDtl pdc---"+pdc);
				Delinquency_type1 delinquency = loan_Operative_Info.getDelinquency();
				LogGEN.writeTrace("CBG_Log", "CBG InqLoanOperationDtl delinquency---"+delinquency);
				StandingInst_type1[] standingInst = loan_Operative_Info.getStandingInst();
				LogGEN.writeTrace("CBG_Log", "CBG InqLoanOperationDtl standingInst---"+standingInst);
				Limits_type1[] limits = loan_Operative_Info.getLimits();
				LogGEN.writeTrace("CBG_Log", "CBG InqLoanOperationDtl limits---"+limits);
				LinkedCustomer_type1[] linkedCustomer = loan_Operative_Info.getLinkedCustomer();
				LogGEN.writeTrace("CBG_Log", "CBG InqLoanOperationDtl linkedCustomer---"+linkedCustomer);

				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>InqLoanOperationDtl</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<GetLoanOperativeInfoRes>"+
						"<loan_Operative_Info>"+"<balScheduleDetails>";

				LogGEN.writeTrace("CBG_Log", "CBG InqLoanOperationDtl sOutput 1---"+sOutput);

				if(balScheduleDetails!=null){
					sOutput+="<outstandingBal>"+balScheduleDetails.getOutstandingBal()+"</outstandingBal>"
							+"<interestCharged>"+balScheduleDetails.getInterestCharged()+"</interestCharged>"
							+"<advancePayment>"+balScheduleDetails.getAdvancePayment()+"</advancePayment>"
							+"<netPayableAmt>"+balScheduleDetails.getNetPayableAmt()+"</netPayableAmt>"
							+"<principalBal>"+balScheduleDetails.getPrincipalBal()+"</principalBal>"
							+"<installmentArrears>"+balScheduleDetails.getInstallmentArrears()+"</installmentArrears>"
							+"<penalInterestDue>"+balScheduleDetails.getPenalInterestDue()+"</penalInterestDue>"
							+"<interestDue>"+balScheduleDetails.getInterestDue()+"</interestDue>"
							+"<noOfDefsTotal>"+balScheduleDetails.getNoOfDefsTotal()+"</noOfDefsTotal>"
							+"<noOfDefsCurrentYear>"+balScheduleDetails.getNoOfDefsCurrentYear()+"</noOfDefsCurrentYear>"
							+"<lastDefDate>"+balScheduleDetails.getLastDefDate()+"</lastDefDate>"
							+"<interestPaidLastEmi>"+balScheduleDetails.getInterestPaidLastEmi()+"</interestPaidLastEmi>"
							+"<interestToBePaid>"+balScheduleDetails.getInterestToBePaid()+"</interestToBePaid>"
							+"<penalltyAmount>"+balScheduleDetails.getPenalltyAmount()+"</penalltyAmount>"
							+"<rateFlag>"+balScheduleDetails.getRateFlag()+"</rateFlag>"
							+"<custShortName>"+balScheduleDetails.getCustShortName()+"</custShortName>"
							+"<unclearAmount>"+balScheduleDetails.getUnclearAmount()+"</unclearAmount>"
							+"<arrearsChargesAmount>"+balScheduleDetails.getArrearsChargesAmount()+"</arrearsChargesAmount>";

				}
				sOutput+="</balScheduleDetails>"+	
						"<PDC>";

				LogGEN.writeTrace("CBG_Log", "CBG InqLoanOperationDtl sOutput 2---"+sOutput);

				if(pdc!=null){
					sOutput+="<PDCAmount>"+pdc.getPDCAmount()+"</PDCAmount>"
							+"<PDCDraweeAccNO>"+pdc.getPDCDraweeAccNO()+"</PDCDraweeAccNO>"
							+"<PDCDate>"+pdc.getPDCDate()+"</PDCDate>"
							+"<PDCBankName>"+pdc.getPDCBankName()+"</PDCBankName>";
				}
				sOutput+="</PDC>";	

				LogGEN.writeTrace("CBG_Log", "CBG InqLoanOperationDtl sOutput 3---"+sOutput);

				if(standingInst!=null){
					if(standingInst.length!=0){
						for(int i=0;i<standingInst.length;i++){
							sOutput+="<standingInst>"+"<SINO>"+standingInst[i].getSINo()+"</SINO>"
									+"<SIStartDate>"+standingInst[i].getSIStartDate()+"</SIStartDate>"
									+"<SIEndDate>"+standingInst[i].getSIEndDate()+"</SIEndDate>"
									+"<Amount>"+standingInst[i].getAmount()+"</Amount>"
									+"<Benificiery>"+standingInst[i].getBeneficiery()+"</Benificiery>"
									+"<LastSIExecution>"+standingInst[i].getLastSIExecution()+"</LastSIExecution>"
									+"<ReasonForFailure>"+standingInst[i].getReasonForFailure()+"</ReasonForFailure>"
									+"<Narration>"+standingInst[i].getNarration()+"</Narration>"
									+"<SIFrequency>"+standingInst[i].getSIFrequency()+"</SIFrequency>"+"</standingInst>";	   	
						}
					}
				}

				LogGEN.writeTrace("CBG_Log", "CBG InqLoanOperationDtl sOutput 4---"+sOutput);

				if(limits!=null){
					if(limits.length!=0){
						for(int i=0;i<limits.length;i++){
							sOutput+="<limits>"+"<limitNo>"+limits[i].getLimitNo()+"</limitNo>"
									+"<startDate>"+limits[i].getStartDate()+"</startDate>"
									+"<expiryDate>"+limits[i].getExpiryDate()+"</expiryDate>"
									+"<amount>"+limits[i].getAmount()+"</amount>"
									+"<rate>"+limits[i].getRate()+"</rate>"
									+"<reasonCode>"+limits[i].getReasonCode()+"</reasonCode>"+"</limits>";
						}
					}
				}

				LogGEN.writeTrace("CBG_Log", "CBG InqLoanOperationDtl sOutput 5---"+sOutput);

				if(linkedCustomer!=null){
					if(linkedCustomer.length!=0){
						for(int i=0;i<linkedCustomer.length;i++){
							sOutput+="<linkedCustomer>"+"<customerID>"+linkedCustomer[i].getCustomerID()+"</customerID>"
									+"<relation>"+linkedCustomer[i].getRelation()+"</relation>"
									+"<CustFullName>"+linkedCustomer[i].getCustFullName()+"</CustFullName>"+"</linkedCustomer>";
						}
					}
				}
				sOutput+="<delinquency>";

				LogGEN.writeTrace("CBG_Log", "CBG InqLoanOperationDtl sOutput 6---"+sOutput);

				if(delinquency!=null){
					sOutput+="<delinquencyAmount>"+delinquency.getDelinquencyAmount()+"</delinquencyAmount>"
							+"<NPLStatus>"+delinquency.getNPLStatus()+"</NPLStatus>"
							+"<lastDeliquencyDate>"+delinquency.getLastDeliquencyDate()+"</lastDeliquencyDate>"
							+"<noOfDaysPastDue>"+delinquency.getNoOfDaysPastDue()+"</noOfDaysPastDue>";
				}
				sOutput+="</delinquency>"	
						+"<dealerCode>"+loan_Operative_Info.getDealerCode()+"</dealerCode>"
						+"<sourceBy>"+loan_Operative_Info.getSourceBy()+"</sourceBy>"
						+"<sourceCode>"+loan_Operative_Info.getSourceCode()+"</sourceCode>"
						+"<principalOverDue>"+loan_Operative_Info.getPrincipalOverDue()+"</principalOverDue>"
						+"<customerId>"+loan_Operative_Info.getCustomerId()+"</customerId>"
						+"<loanAccountNumber>"+loan_Operative_Info.getLoanAccountNumber()+"</loanAccountNumber>"
						+"<valueDate>"+loan_Operative_Info.getValueDate()+"</valueDate>"
						+"<penalIntOverDue>"+loan_Operative_Info.getPenalIntOverDue()+"</penalIntOverDue>"
						+"<futureIntAccural>"+loan_Operative_Info.getFutureIntAccural()+"</futureIntAccural>"
						+"<futurePenalInt>"+loan_Operative_Info.getFuturePenalInt()+"</futurePenalInt>"
						+"<esfPercentage>"+loan_Operative_Info.getEsfPercentage()+"</esfPercentage>"
						+"<esfAmount>"+loan_Operative_Info.getEsfAmount()+"</esfAmount>"
						+"<rpaBalance>"+loan_Operative_Info.getRpaBalance()+"</rpaBalance>"+"</Loan_Operative_Info>";

				LogGEN.writeTrace("CBG_Log", "CBG InqLoanOperationDtl sOutput 7---"+sOutput);

			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqLoanOperationDtl</Option>"
						+ "<Output><returnCode>"
						+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+
						"</errorDescription><Status>ERROR</Status><td>Unable to process loan operation.</td></Output>";

				LogGEN.writeTrace("CBG_Log", "CBG InqLoanOperationDtl sOutput---"+sOutput);
			}


		}catch(Exception e){
			LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDesc=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqLoanOperationDtl</Option>"
					+ "<Output>"
					+ "<returnCode>"+
					sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+
					"</errorDescription>Unable to process loan operation.</Output>";

			LogGEN.writeTrace("CBG_Log", "CBG InqLoanOperationDtl catch sOutput---"+sOutput);
			e.printStackTrace();

		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqLoanOperationDtl</Option>"
						+ "<Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"
						+sErrorDesc+"</errorDescription>Unable to process loan operation.</Output>";
			}

			LogGEN.writeTrace("CBG_Log","outputXML : finally :"+sOutput);

			String Status="";
			if(sReturnCode.equalsIgnoreCase("0"))
			{
				Status="Success";
			}
			else if(sReturnCode.equalsIgnoreCase("2"))
				Status="Pratial Success";
			else
				Status="Failure";


			String dburl=(String)currentCabPropertyMap.get("DBURL");
			String dbuser=(String)currentCabPropertyMap.get("USER");
			String dbpass=(String)currentCabPropertyMap.get("PASS");

			String inputXml=xmlInput;
			LogGEN.writeTrace("CBG_Log", inputXml);
			String winame=xmlDataParser.getValueOf("WiName");
			LogGEN.writeTrace("CBG_Log", "WINAME:"+winame);
			String sessionID= xmlDataParser.getValueOf("SessionId");
			String call_type1=xmlDataParser.getValueOf("DSCOPCallType");
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
			String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+
					winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type1+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
			LogGEN.writeTrace("CBG_Log","Add  Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","sOrg_Output : finally :"+sOrg_Output);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_Output.replaceAll("'", "''"));
			} catch (Exception e2) {
				//LogGEN.writeTrace("CBG_Log","ModifyCudtomer  Exception: finally :"+e2.getStackTrace());
			}
			return sOutput;			
		}



	}
}
