package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.axis2.databinding.utils.ConverterUtil;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.ModLoanDisbursementStub;
import com.newgen.dscop.stub.ModLoanDisbursementStub.AddLoanRecordReqMsg;
import com.newgen.dscop.stub.ModLoanDisbursementStub.AddLoanRecordReq_type0;
import com.newgen.dscop.stub.ModLoanDisbursementStub.AddLoanRecordResMsg;
import com.newgen.dscop.stub.ModLoanDisbursementStub.AddLoanRecordRes_type0;
import com.newgen.dscop.stub.ModLoanDisbursementStub.AssociateDtls_type0;
import com.newgen.dscop.stub.ModLoanDisbursementStub.BeneficiaryDtls_type0;
import com.newgen.dscop.stub.ModLoanDisbursementStub.BookFeeDtls_type0;
import com.newgen.dscop.stub.ModLoanDisbursementStub.FreqFeeDtls_type0;
import com.newgen.dscop.stub.ModLoanDisbursementStub.HeaderType;
import com.newgen.dscop.stub.ModLoanDisbursementStub.HybSchDtls_type0;
import com.newgen.dscop.stub.ModLoanDisbursementStub.UdfDtls_type0;
	
public class AddLoanRecord extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_put="";

	public String addLoanRecordCall(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Function called AddLoanRecord");
		LogGEN.writeTrace("CBG_Log", "AddLoanRecord sInputXML---"+sInputXML);
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
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("ModLoanDisbursement");
			LogGEN.writeTrace("CBG_Log", "AddLoanRecord WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "ModLoanDisbursement WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "ModLoanDisbursement CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "ModLoanDisbursement USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "ModLoanDisbursement PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "ModLoanDisbursement LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "ModLoanDisbursement TIME_OUT: "+lTimeOut);

			String ref_no=xmlDataParser.getValueOf("REF_NO");
			String senderId = xmlDataParser.getValueOf("SenderId");
			ModLoanDisbursementStub request_stub=new ModLoanDisbursementStub(sWSDLPath);
			AddLoanRecordReqMsg fetch_camp_dtls = new AddLoanRecordReqMsg();			
			AddLoanRecordReq_type0 fetch_camp_type0 = new AddLoanRecordReq_type0();
			AddLoanRecordResMsg fetch_camp_response = new AddLoanRecordResMsg();
			AddLoanRecordRes_type0 fetch_camp_res_type0 = new AddLoanRecordRes_type0();
			
			HeaderType Header_Input = new HeaderType();
			Header_Input.setServiceName("ModLoanDisbursement");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Modify");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(senderId);
			Header_Input.setReqTimeStamp(sDate);
			fetch_camp_type0.setAppRefNo(xmlDataParser.getValueOf("appRefNo"));
			fetch_camp_type0.setAppCBSCustId(xmlDataParser.getValueOf("appCBSCustId"));
			fetch_camp_type0.setSpecificProduct(xmlDataParser.getValueOf("specificProduct"));
			fetch_camp_type0.setPerAppBranchCode(xmlDataParser.getValueOf("perAppBranchCode"));
			fetch_camp_type0.setLoanBookingDate(xmlDataParser.getValueOf("loanBookingDate"));
			fetch_camp_type0.setLoanTenor(xmlDataParser.getValueOf("loanTenor"));
			fetch_camp_type0.setLoanAmount(xmlDataParser.getValueOf("loanAmount"));
			fetch_camp_type0.setFirstDueDt(xmlDataParser.getValueOf("firstDueDt"));		
			fetch_camp_type0.setDisbursalDate(xmlDataParser.getValueOf("disbursalDate"));
			fetch_camp_type0.setModeOfPay(xmlDataParser.getValueOf("modeOfPay"));
			fetch_camp_type0.setRepayAcc(xmlDataParser.getValueOf("repayAcc"));
			fetch_camp_type0.setRepayAccBr(xmlDataParser.getValueOf("repayAccBr"));
			fetch_camp_type0.setIbanAcc(xmlDataParser.getValueOf("ibanAcc"));
			fetch_camp_type0.setSanctionAuthority(xmlDataParser.getValueOf("sanctionAuthority"));
			fetch_camp_type0.setApprovalDate(xmlDataParser.getValueOf("approvalDate"));
			fetch_camp_type0.setExpiryDate(xmlDataParser.getValueOf("expiryDate"));
			fetch_camp_type0.setLpolocNumber(xmlDataParser.getValueOf("lpolocNumber"));
			fetch_camp_type0.setLoanIntrType(xmlDataParser.getValueOf("loanIntrType"));
			fetch_camp_type0.setIntrScheme(xmlDataParser.getValueOf("intrScheme"));
			fetch_camp_type0.setBaseRate(xmlDataParser.getValueOf("baseRate"));
			fetch_camp_type0.setLoanOffSet(xmlDataParser.getValueOf("loanOffSet"));
			fetch_camp_type0.setFloorRate(xmlDataParser.getValueOf("floorRate"));
			fetch_camp_type0.setCeilRate(xmlDataParser.getValueOf("ceilRate"));
			BeneficiaryDtls_type0[] beneficiaryDtls_type0s = new  BeneficiaryDtls_type0[]{};
			List<BeneficiaryDtls_type0> list_BeneficiaryDtls_type0 = ConverterUtil.toList(beneficiaryDtls_type0s);
			int start = xmlDataParser.getStartIndex("beneficiaryDtls_type", 0, 0);
			int deadEnd = xmlDataParser.getEndIndex("beneficiaryDtls_type", start, 0);
			int noOfFieldss = xmlDataParser.getNoOfFields("beneficiaryDtls", start,deadEnd);
			int end = 0;
			for(int i = 0; i < noOfFieldss; ++i){
				start = xmlDataParser.getStartIndex("beneficiaryDtls", end, 0);
				end = xmlDataParser.getEndIndex("beneficiaryDtls", start, 0);
				String beneficiaryType = xmlDataParser.getValueOf("beneficiaryType", start, end);
				String beneficiaryCode = xmlDataParser.getValueOf("beneficiaryCode", start, end);
				String disbMod = xmlDataParser.getValueOf("disbMod", start, end);
				String beneficiaryAcc = xmlDataParser.getValueOf("beneficiaryAcc", start, end);
				String beneficiaryAccBr = xmlDataParser.getValueOf("beneficiaryAccBr", start, end);
				String benIBANAcc = xmlDataParser.getValueOf("benIBANAcc", start, end);
				String disbRemarks = xmlDataParser.getValueOf("disbRemarks", start, end);
				String disbRemarks1 = xmlDataParser.getValueOf("disbRemarks1", start, end);
				String currency = xmlDataParser.getValueOf("currency", start, end);
				String conversionRate = xmlDataParser.getValueOf("conversionRate", start, end);
				String beneficiaryDistAmount = xmlDataParser.getValueOf("beneficiaryDistAmount", start, end);

				BeneficiaryDtls_type0 beneficiaryDtls_type0 = new BeneficiaryDtls_type0();
				beneficiaryDtls_type0.setBeneficiaryType(beneficiaryType);
				beneficiaryDtls_type0.setBeneficiaryCode(beneficiaryCode);
				beneficiaryDtls_type0.setDisbMod(disbMod);
				beneficiaryDtls_type0.setBeneficiaryAcc(beneficiaryAcc);
				beneficiaryDtls_type0.setBeneficiaryAccBr(beneficiaryAccBr);
				beneficiaryDtls_type0.setBenIBANAcc(benIBANAcc);
				beneficiaryDtls_type0.setDisbRemarks(disbRemarks);
				beneficiaryDtls_type0.setDisbRemarks1(disbRemarks1);
				beneficiaryDtls_type0.setCurrency(currency);
				beneficiaryDtls_type0.setConversionRate(conversionRate);
				beneficiaryDtls_type0.setBeneficiaryDistAmount(beneficiaryDistAmount);
				list_BeneficiaryDtls_type0.add(beneficiaryDtls_type0);
			}
			beneficiaryDtls_type0s = (BeneficiaryDtls_type0[])list_BeneficiaryDtls_type0.toArray(new BeneficiaryDtls_type0[list_BeneficiaryDtls_type0.size()]);
			
			fetch_camp_type0.setBeneficiaryDtls(beneficiaryDtls_type0s);
			
			BookFeeDtls_type0[] bookFeeDtls_type0 = new BookFeeDtls_type0[]{};
			List<BookFeeDtls_type0> list_BookFeeDtls_type0 = ConverterUtil.toList(bookFeeDtls_type0);
			start = xmlDataParser.getStartIndex("bookFeeDtls_type", 0, 0);
			deadEnd = xmlDataParser.getEndIndex("bookFeeDtls_type", start, 0);
			noOfFieldss = xmlDataParser.getNoOfFields("bookFeeDtls", start,deadEnd);
			end = 0;
			for(int i = 0; i < noOfFieldss; ++i){
				start = xmlDataParser.getStartIndex("bookFeeDtls", end, 0);
				end = xmlDataParser.getEndIndex("bookFeeDtls", start, 0);
				String feeCode = xmlDataParser.getValueOf("feeCode", start, end);
				String orgFeeAmount = xmlDataParser.getValueOf("orgFeeAmount", start, end);
				String feeAmount = xmlDataParser.getValueOf("feeAmount", start, end);
				String feePaidIndicator = xmlDataParser.getValueOf("feePaidIndicator", start, end);
				
				BookFeeDtls_type0 bookFeeDtls_type = new BookFeeDtls_type0();
				bookFeeDtls_type.setFeeCode(feeCode);
				bookFeeDtls_type.setOrgFeeAmount(orgFeeAmount);
				bookFeeDtls_type.setFeeAmount(feeAmount);
				bookFeeDtls_type.setFeePaidIndicator(feePaidIndicator);
				list_BookFeeDtls_type0.add(bookFeeDtls_type);
			}
			bookFeeDtls_type0 = (BookFeeDtls_type0[])list_BookFeeDtls_type0.toArray(new BookFeeDtls_type0[list_BookFeeDtls_type0.size()]);
			fetch_camp_type0.setBookFeeDtls(bookFeeDtls_type0);
			
			FreqFeeDtls_type0[] FreqFeeDtls_type0 = new FreqFeeDtls_type0[]{};
			List<FreqFeeDtls_type0> list_FreqFeeDtls_type0 = ConverterUtil.toList(FreqFeeDtls_type0);
			start = xmlDataParser.getStartIndex("freqFeeDtls_type", 0, 0);
			deadEnd = xmlDataParser.getEndIndex("freqFeeDtls_type", start, 0);
			noOfFieldss = xmlDataParser.getNoOfFields("freqFeeDtls", start,deadEnd);
			end = 0;
			
			for(int i = 0; i < noOfFieldss; ++i){
				start = xmlDataParser.getStartIndex("freqFeeDtls", end, 0);
				end = xmlDataParser.getEndIndex("freqFeeDtls", start, 0);
				String freqFeeCode = xmlDataParser.getValueOf("freqFeeCode", start, end);
				String margin = xmlDataParser.getValueOf("margin", start, end);
				
				FreqFeeDtls_type0 freqFeeDtls_type = new FreqFeeDtls_type0();
				freqFeeDtls_type.setFreqFeeCode(freqFeeCode);
				freqFeeDtls_type.setMargin(margin);
				list_FreqFeeDtls_type0.add(freqFeeDtls_type);
			}
			
			FreqFeeDtls_type0 = (FreqFeeDtls_type0[])list_FreqFeeDtls_type0.toArray(new FreqFeeDtls_type0[list_FreqFeeDtls_type0.size()]);
			fetch_camp_type0.setFreqFeeDtls(FreqFeeDtls_type0);
			
			HybSchDtls_type0[]  hybSchDtls_type0 = new HybSchDtls_type0[]{};
			List<HybSchDtls_type0> list_HybSchDtls_type0 = ConverterUtil.toList(hybSchDtls_type0);
			start = xmlDataParser.getStartIndex("hybSchDtls_type", 0, 0);
			deadEnd = xmlDataParser.getEndIndex("hybSchDtls_type", start, 0);
			noOfFieldss = xmlDataParser.getNoOfFields("hybSchDtls", start,deadEnd);
			end = 0;
			for(int i = 0; i < noOfFieldss; ++i){
				start = xmlDataParser.getStartIndex("hybSchDtls", end, 0);
				end = xmlDataParser.getEndIndex("hybSchDtls", start, 0);
				String phaseNum = xmlDataParser.getValueOf("phaseNum", start, end);
				String fromInstallment = xmlDataParser.getValueOf("fromInstallment", start, end);
				String toInstallment = xmlDataParser.getValueOf("toInstallment", start, end);
				String hybIntrType = xmlDataParser.getValueOf("hybIntrType", start, end);
				String phaseIntrScheme = xmlDataParser.getValueOf("phaseIntrScheme", start, end);
				String baseRate = xmlDataParser.getValueOf("baseRate", start, end);
				String loanOffset = xmlDataParser.getValueOf("loanOffset", start, end);
				String floorRate = xmlDataParser.getValueOf("floorRate", start, end);
				String ceilRate = xmlDataParser.getValueOf("ceilRate", start, end);
				
				HybSchDtls_type0 hybSchDtls_type = new HybSchDtls_type0();
				hybSchDtls_type.setPhaseNum(phaseNum);
				hybSchDtls_type.setFromInstallment(fromInstallment);
				hybSchDtls_type.setToInstallment(toInstallment);
				hybSchDtls_type.setHybIntrType(hybIntrType);
				hybSchDtls_type.setPhaseIntrScheme(phaseIntrScheme);
				hybSchDtls_type.setBaseRate(baseRate);
				hybSchDtls_type.setLoanOffset(loanOffset);
				hybSchDtls_type.setFloorRate(floorRate);
				hybSchDtls_type.setCeilRate(ceilRate);
				
				list_HybSchDtls_type0.add(hybSchDtls_type);
			}
			
			hybSchDtls_type0 = (HybSchDtls_type0[])list_HybSchDtls_type0.toArray(new HybSchDtls_type0[list_HybSchDtls_type0.size()]);
			
			fetch_camp_type0.setHybSchDtls(hybSchDtls_type0);
			
			
			AssociateDtls_type0[] associateDtls_type0 = new AssociateDtls_type0[]{};
			List<AssociateDtls_type0> list_AssociateDtls_type0 = ConverterUtil.toList(associateDtls_type0);
			start = xmlDataParser.getStartIndex("associateDtls_type", 0, 0);
			deadEnd = xmlDataParser.getEndIndex("associateDtls_type", start, 0);
			noOfFieldss = xmlDataParser.getNoOfFields("associateDtls", start,deadEnd);
			end = 0;
			for(int i = 0; i < noOfFieldss; ++i){
				start = xmlDataParser.getStartIndex("associateDtls", end, 0);
				end = xmlDataParser.getEndIndex("associateDtls", start, 0);
				String associateType = xmlDataParser.getValueOf("associateType", start, end);
				String associateId = xmlDataParser.getValueOf("associateId", start, end);
				String percOfGuarantee = xmlDataParser.getValueOf("percOfGuarantee", start, end);
				String assoRefNum = xmlDataParser.getValueOf("assoRefNum", start, end);
				String assoRemarks = xmlDataParser.getValueOf("assoRemarks", start, end);
				
				AssociateDtls_type0 associateDtls_type = new AssociateDtls_type0();
				associateDtls_type.setAssociateType(associateType);
				associateDtls_type.setAssociateId(associateId);
				associateDtls_type.setPercOfGuarantee(percOfGuarantee);
				associateDtls_type.setAssoRefNum(assoRefNum);
				associateDtls_type.setAssoRemarks(assoRemarks);
			
				list_AssociateDtls_type0.add(associateDtls_type);
			}
			associateDtls_type0 = (AssociateDtls_type0[])list_AssociateDtls_type0.toArray(new AssociateDtls_type0[list_AssociateDtls_type0.size()]);
			fetch_camp_type0.setAssociateDtls(associateDtls_type0);
					
			UdfDtls_type0[] udfDtls_type0 = new UdfDtls_type0[]{};
			List<UdfDtls_type0> list_UdfDtls_type0 = ConverterUtil.toList(udfDtls_type0);
			start = xmlDataParser.getStartIndex("udfDtls_type", 0, 0);
			deadEnd = xmlDataParser.getEndIndex("udfDtls_type", start, 0);
			noOfFieldss = xmlDataParser.getNoOfFields("udfDtls", start,deadEnd);
			end = 0;
			for(int i = 0; i < noOfFieldss; ++i){
				start = xmlDataParser.getStartIndex("udfDtls", end, 0);
				end = xmlDataParser.getEndIndex("udfDtls", start, 0);
				String udfString = xmlDataParser.getValueOf("udfString", start, end);
				String udfValue = xmlDataParser.getValueOf("udfValue", start, end);
				
				UdfDtls_type0 udfDtls_type = new UdfDtls_type0();
				udfDtls_type.setUdfString(udfString);
				udfDtls_type.setUdfValue(udfValue);
				list_UdfDtls_type0.add(udfDtls_type);
			}
			
			udfDtls_type0 = (UdfDtls_type0[])list_UdfDtls_type0.toArray(new UdfDtls_type0[list_UdfDtls_type0.size()]);
			fetch_camp_type0.setUdfDtls(udfDtls_type0);
			
			fetch_camp_dtls.setAddLoanRecordReq(fetch_camp_type0);
			fetch_camp_dtls.setHeader(Header_Input);
			

			xmlInput= request_stub.getAddLoanRecordXML(fetch_camp_dtls);
			LogGEN.writeTrace("CBG_Log", "AddLoanRecord xmlInput xml : "+xmlInput);
			fetch_camp_response = request_stub.addLoanRecord_Oper(fetch_camp_dtls);
			Header_Input = fetch_camp_response.getHeader();
			fetch_camp_res_type0 = fetch_camp_response.getAddLoanRecordRes();
			request_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			sOrg_put=request_stub.outputXML;
			LogGEN.writeTrace("CBG_Log", "AddLoanRecordResMsg sOrg_put: "+sOrg_put);

			sReturnCode =  Header_Input.getReturnCode();
			sErrorDetail = Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();

			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				StringBuilder details = new StringBuilder(); 
				if(fetch_camp_res_type0 != null){
					
				}
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>AddLoanRecord</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<AddLoanRecordRes>"+
						details+
						"</AddLoanRecordRes>"+	
						"</Output>";
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>AddLoanRecord</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><Status>ERROR</Status><td>Unable to Add loan Record</td></Output>";
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Service :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDesc=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>AddLoanRecord</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to fetch customer Campaign Details</Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","AddLoanRecord outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>AddLoanRecord</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to fetch customer Campaign Details</Output>";
			}

			LogGEN.writeTrace("CBG_Log","AddLoanRecord outputXML : finally :"+sOutput);

			String Status="";
			if(sReturnCode.equalsIgnoreCase("0"))
			{
				Status="Success";
			}
			else if(sReturnCode.equalsIgnoreCase("2"))
				Status="Partial Success";
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
			String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type1+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
			LogGEN.writeTrace("CBG_Log","AddLoanRecord  Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","sOrg_put : finally :"+sOrg_put);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_put.replaceAll("'", "''"));
			} catch (Exception e2) {
				LogGEN.writeTrace("CBG_Log","AddLoanRecord  Exception: finally :"+e2.getStackTrace());
			}
		}
		return sOutput;			
	}
}

