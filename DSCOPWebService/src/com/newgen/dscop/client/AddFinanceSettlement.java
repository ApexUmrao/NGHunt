package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.AddFinanceSettlementStub;
import com.newgen.dscop.stub.AddFinanceSettlementStub.AddFinanceSettlementReqMsg;
import com.newgen.dscop.stub.AddFinanceSettlementStub.AddFinanceSettlementReq_type0;
import com.newgen.dscop.stub.AddFinanceSettlementStub.AddFinanceSettlementResMsg;
import com.newgen.dscop.stub.AddFinanceSettlementStub.AddFinanceSettlementRes_type0;
import com.newgen.dscop.stub.AddFinanceSettlementStub.HeaderType;

public class AddFinanceSettlement extends DSCOPServiceHandler{
	protected String sWSDLPath = "";
	protected static String sCabinet = "";
	protected String sUser = "";
	protected String sPassword = "";
	protected boolean sLoginReq = false;
	protected long lTimeOut = 30000L;
	String xmlInput = "";
	String sMIB_Regisration = null;
	String Status = "";
	String sOrg_put=null;
	
	@SuppressWarnings("finally")
	public String addFinanceSettlementCall(String sInputXML){
		LogGEN.writeTrace("CBG_Log", "Function called Add_Finance_Settlement");
		LogGEN.writeTrace("CBG_Log", "AddFinanceSettlement sInputXML---" + sInputXML);
		XMLParser xmlParser = new XMLParser();
		xmlParser.setInputXML(sInputXML);
		String sReturnCode = "";
		String sErrorDetail = "";
		String sErrorDesc = "";
		String sOutput = "";
		
		DSCOPServiceHandler sHandler= new DSCOPServiceHandler();
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		try
		{
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("AddFinanceSettlement");
			LogGEN.writeTrace("CBG_Log", "AddFinanceSettlement WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "AddFinancialSettlement WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "AddFinancialSettlement CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "AddFinancialSettlement USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "AddFinancialSettlement PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "AddFinancialSettlement LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "AddFinancialSettlement TIME_OUT: "+lTimeOut);

			
			String ref_no=xmlParser.getValueOf("REF_NO");
			String loanAccountNumber=xmlParser.getValueOf("loanAccNumber");
			String productCategory=xmlParser.getValueOf("productCategory");
			String debitAccountNumber=xmlParser.getValueOf("debitAccountNumber");
			String fullSettlementFlag=xmlParser.getValueOf("fullSettlementFlag");
			String esfFeeAmount=xmlParser.getValueOf("esfFeeAmount");
			String esfFeePercentage=xmlParser.getValueOf("esfFeePercentage");
			String valueDate=xmlParser.getValueOf("valueDate");
			String remarks=xmlParser.getValueOf("remarks");
			
			AddFinanceSettlementStub add_finance_settlement_stub=new AddFinanceSettlementStub(sWSDLPath);
			AddFinanceSettlementReq_type0 add_finance_req=new AddFinanceSettlementReq_type0();
			AddFinanceSettlementReqMsg add_finance_req_msg=new AddFinanceSettlementReqMsg();
            
			HeaderType Header_Input = new HeaderType();
			//Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("AddFinanceSettlement");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Addition");
			//Header_Input.setCorrelationID("");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlParser.getValueOf("SENDERID")));
			//Header_Input.setConsumer(sHandler.setSenderId(xmlParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			//Header_Input.setRepTimeStamp("");
			//Header_Input.setUsername(loggedinuser);
			//Header_Input.setCredentials(loggedinuser);
			//Header_Input.setReturnCode("");
			//Header_Input.setErrorDescription("");
			//Header_Input.setErrorDetail("");
			
			add_finance_req_msg.setHeader(Header_Input);
			
			add_finance_req.setLoanAccNumber(loanAccountNumber);
			add_finance_req.setProductCategory(productCategory);
			add_finance_req.setDebitAccountNumber(debitAccountNumber);
			add_finance_req.setFullSettlementFlag(fullSettlementFlag);
			add_finance_req.setEsfFeeAmount(esfFeeAmount);
			add_finance_req.setEsfFeePercentage(esfFeePercentage);
			add_finance_req.setValueDate(valueDate);
			add_finance_req.setRemarks(remarks);
			
			add_finance_req_msg.setAddFinanceSettlementReq(add_finance_req);
			xmlInput= add_finance_settlement_stub.getAddFinanceSettlementXML(add_finance_req_msg);
			
			LogGEN.writeTrace("CBG_Log", "AddFinanceSettlement xmlInput xml : "+xmlInput);
			add_finance_settlement_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			AddFinanceSettlementResMsg add_finance_settlement_res_msg = add_finance_settlement_stub.addFinanceSettlement_Oper(add_finance_req_msg);
			sOrg_put=add_finance_settlement_stub.outputXML;
			LogGEN.writeTrace("CBG_Log", "AddFinanceSettlementResMsg sOrg_put: "+sOrg_put);

			Header_Input=add_finance_settlement_res_msg.getHeader();
			sReturnCode=  Header_Input.getReturnCode();
			sErrorDetail=Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();
			AddFinanceSettlementRes_type0 add_finance_settlement_type0 = add_finance_settlement_res_msg.getAddFinanceSettlementRes();
			
			LogGEN.writeTrace("CBG_Log", "CBG AddFinanceSettlement Return Code---"+sReturnCode);
			LogGEN.writeTrace("CBG_Log", "CBG AddFinanceSettlement Error Detail---"+sErrorDetail);
			LogGEN.writeTrace("CBG_Log", "CBG AddFinanceSettlement Error Description---"+sErrorDesc);
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>AddFinanceSettlement</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<AddFinanceSettlementRes>"+
						"<loanAccountNumber>"+add_finance_settlement_type0.getLoanAccNumber()+"</loanAccountNumber>"+
						//"<loanAccountNumber>"+add_finance_settlement_type0.getEsfFeeAmount()+"<loanAccountNumber>"+
						"</AddFinanceSettlementRes>"+	
						"</Output>";
				LogGEN.writeTrace("CBG_Log", "Output XML--- ");	
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>AddFinanceSettlement</Option><Output><returnCode>"
				+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><Status>ERROR</Status>"
						+ "<td>Unable to retrieve FinanceSettlement info.</td></Output>";
			}	
		}
		catch(Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Service :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>AddFinanceSettlement</Option><returnCode>"
			+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to retrieve FinanceSettlement info.</td></Output>";
			e.printStackTrace();	
		
	    }
		finally{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1){
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>AddFinanceSettlement</Option><returnCode>"
					+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to retrieve FinanceSettlement info.</td></Output>";
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
			LogGEN.writeTrace("CBG_Log", inputXml);
			String winame=xmlParser.getValueOf("WiName");
			LogGEN.writeTrace("CBG_Log", "WINAME:"+winame);
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

			String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"
					+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
			LogGEN.writeTrace("CBG_Log", " Add  Query : finally :" + Query);
						LogGEN.writeTrace("CBG_Log", "sOrg_Output : finally :" + sOrg_put);
			try {
				//LogGEN.writeTrace("CBG_Log", "*****Executing Query : AddDebitCardNew*****");
				con.executeClob("jdbc:oracle:thin:@" + dburl, dbuser, dbpass, Query, inputXml.replaceAll("'", "''"), sOrg_put.replaceAll("'", "''"));
				//LogGEN.writeTrace("CBG_Log", "*****Query Executed : AddDebitCardNew*****");
			} catch (Exception e2) {
				e2.getMessage();
			}
			return sOutput;			
		}		

       
		
    }
}