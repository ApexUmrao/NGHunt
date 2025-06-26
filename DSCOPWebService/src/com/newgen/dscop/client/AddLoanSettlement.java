package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.stub.AddLoanSettlementStub.HeaderType;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.AddLoanSettlementStub;
import com.newgen.dscop.stub.AddLoanSettlementStub.AddLoanSettlementReqMsg;
import com.newgen.dscop.stub.AddLoanSettlementStub.AddLoanSettlementReq_type0;
import com.newgen.dscop.stub.AddLoanSettlementStub.AddLoanSettlementRes_type0;
import com.newgen.dscop.stub.AddLoanSettlementStub.AddLoanSettlementResMsg;

public class AddLoanSettlement  extends DSCOPServiceHandler{
	protected String sWSDLPath = "";
	protected static String sCabinet = "";
	protected String sUser = "";
	protected String sPassword = "";
	protected boolean sLoginReq = false;
	protected long lTimeOut = 30000L;
	String xmlInput = "";
	String sMIB_Regisration = null;
	String Status = "";
	String sOrgnlOutput = "";
	
	@SuppressWarnings("finally")
	public String addLoanSettlementCall(String sInputXML)
	{
		LogGEN.writeTrace("CBG_Log", "Fuction called AddLoanSettlement");
		LogGEN.writeTrace("CBG_Log", "AddLoanSettlement sInputXML: ");
		XMLParser xmlParser = new XMLParser();
		xmlParser.setInputXML(sInputXML);
		String sOutput="";
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc = "";
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		
		String customerId=xmlParser.getValueOf("customerId");
		String loanAccNumber=xmlParser.getValueOf("loanAccNumber");
		String settlementValDate=xmlParser.getValueOf("settlementValDate");
		String esfPercentage=xmlParser.getValueOf("esfPercentage");
		String esfValue=xmlParser.getValueOf("esfValue");
		String settlementAmount=xmlParser.getValueOf("settlementAmount");
		String settlementCASA=xmlParser.getValueOf("settlementCASA");
		String settlementCASABranch=xmlParser.getValueOf("settlementCASABranch");

		try
		{
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("AddLoanSettlement");
			LogGEN.writeTrace("CBG_Log", "AddLoanSettlement WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "AddLoanSettlement WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "AddLoanSettlement CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "AddLoanSettlement USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "AddLoanSettlement PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "AddLoanSettlement LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "AddLoanSettlement TIME_OUT: "+lTimeOut);

			//Parsing inputXML to retrieve tag values: 
			//String sCustomerID = xmlParser.getValueOf("CUST_ID");
			String sRef_no = xmlParser.getValueOf("REF_NO");
			String senderId = xmlParser.getValueOf("SenderId");

			//LogGEN.writeTrace("CBG_Log", "sCustomerID---"+sCustomerID);

			//Setting Request Header params:
			HeaderType header_req = new HeaderType();
			//header_req.setUsecaseID("1234");
			header_req.setServiceName("AddLoanSettlement"); 
			header_req.setVersionNo("1.0");
			header_req.setServiceAction("Addition");
			header_req.setSysRefNumber(sRef_no);
			header_req.setSenderID(senderId);
			header_req.setReqTimeStamp(sDate);
			//header_req.setUsername(sCustomerID);
			//header_req.setCredentials(loggedinuser);	
			

			//Instantiating the Stub object: 
			//--AddDebitCardIssueStub stub = new AddDebitCardIssueStub(sWSDLPath);
			AddLoanSettlementStub stub=new AddLoanSettlementStub(sWSDLPath);

			//Based on the type of call:
			//Instantiating the Response objects to get the required params:
			//			if(sCallType.equalsIgnoreCase("DebitCard_New")){


			//--AddDebitCardIssueReqMsg add_request = new AddDebitCardIssueReqMsg();			
			//--AddDebitCardIssueReq_type0 add_req = new AddDebitCardIssueReq_type0();
			
			AddLoanSettlementReqMsg add_request=new AddLoanSettlementReqMsg();
			AddLoanSettlementReq_type0 add_req=new AddLoanSettlementReq_type0();
			
			add_req.setCustomerId(customerId);
			add_req.setLoanAccNumber(loanAccNumber);
			add_req.setSettlementValDate(settlementValDate);
			add_req.setEsfPercentage(esfPercentage);
			add_req.setEsfValue(esfValue);
			add_req.setSettlementAmount(settlementAmount);
			add_req.setSettlementCASA(settlementCASA);
			add_req.setSettlementCASABranch(settlementCASABranch);
			
			add_request.setHeader(header_req);
			add_request.setAddLoanSettlementReq(add_req);
			//--add_request.setHeader(header_req);
			//--add_request.setAddDebitCardIssueReq(add_req);
		    
		    
			LogGEN.writeTrace("CBG_Log", "CBG AddLoanSettlement add_request---");
			xmlInput = stub.getAddLoanSettlementXML(add_request);
			LogGEN.writeTrace("CBG_Log", "CBG AddLoanSettlement xmlInput---");
			stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);

			
			//--AddDebitCardIssueResMsg response = stub.addDebitCardIssue_Oper(add_request);
			//--AddDebitCardIssueRes_type0 res = response.getAddDebitCardIssueRes();
			
			AddLoanSettlementResMsg response = stub.addLoanSettlement_Oper(add_request);
			AddLoanSettlementRes_type0 res=response.getAddLoanSettlementRes();

			sOrgnlOutput = stub.outputXML;
			HeaderType header_resp = response.getHeader();
			sReturnCode = header_resp.getReturnCode();
			sErrorDetail = header_resp.getErrorDetail();
			sErrorDesc = header_resp.getErrorDescription();

			LogGEN.writeTrace("CBG_Log", "CBG AddLoanSettlement Return Code---"+sReturnCode);
			LogGEN.writeTrace("CBG_Log", "CBG AddLoanSettlement Error Detail---"+sErrorDetail);
			LogGEN.writeTrace("CBG_Log", "CBG AddLoanSettlement Error Description---"+sErrorDesc);
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1")){
				LogGEN.writeTrace("CBG_Log", "Successful Result");

				sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>LoanSettlement</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<AddLoanSettlementRes>"+					
						"<customerId>"+res.getCustomerId()+"</customerId>"+
						"<loanAccountNumber>"+res.getLoanAccountNumber()+"</loanAccountNumber>"+
						"<loanStatus>"+res.getLoanStatus()+"</loanStatus>"+
						"</AddLoanSettlementRes>"+					
						"</Output>";
				LogGEN.writeTrace("CBG_Log", "Output XML--- ");		    
			}
			else{			
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>LoanSettlement</Option>"
						+ "<returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+
						"</errorDescription><td>Unable to retrieve LoanSettlement info.</td></Output>";
			}
		}
		catch(Exception e){
			LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Service :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>LoanSettlement"
					+ "</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+
					"</errorDescription><td>Unable to retrieve LoanSettlement info.</td></Output>";
			e.printStackTrace();			
		}
		finally{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1){
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>LoanSettlement"
						+ "</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+
						"</errorDescription><td>Unable to retrieve LoanSettlement info.</td></Output>";
			}
			String Status="";
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2")){
				Status="Success";
			}
			else
				Status="Failure";
			
			String dburl=(String)currentCabPropertyMap.get("DBURL");
			String dbuser=(String)currentCabPropertyMap.get("USER");
			String dbpass=(String)currentCabPropertyMap.get("PASS");


			String inputXml=xmlInput;
//			LogGEN.writeTrace("CBG_Log", inputXml);
			String winame=xmlParser.getValueOf("WiName");
			String sessionID= xmlParser.getValueOf("SessionId");
			String call_type=xmlParser.getValueOf("DSCOPCallType");
			sCabinet=xmlParser.getValueOf("EngineName");
			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			try {
				dbpass=AESEncryption.decrypt(dbpass);
			}
			catch(Exception e){

			}
			DBConnection con=new DBConnection();

			String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
			LogGEN.writeTrace("CBG_Log", " Add  Query : finally :" + Query);
			//			LogGEN.writeTrace("CBG_Log", "sOrg_Output : finally :" + sOrgnlOutput);
			try {
				LogGEN.writeTrace("CBG_Log", "*****Executing Query : AddLoanSettlement*****");
				con.executeClob("jdbc:oracle:thin:@" + dburl, dbuser, dbpass, Query, inputXml.replaceAll("'", "''"), sOrgnlOutput.replaceAll("'", "''"));
				LogGEN.writeTrace("CBG_Log", "*****Query Executed : AddLoanSettlement*****");
			} catch (Exception e2) {
				e2.getMessage();
			}
			return sOutput;			
		}		
	}
}

