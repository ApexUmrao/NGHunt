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
import com.newgen.dscop.stub.ModLoanDisbursementStub.AddIslamicLoanDisbReqMsg;
import com.newgen.dscop.stub.ModLoanDisbursementStub.AddIslamicLoanDisbReq_type0;
import com.newgen.dscop.stub.ModLoanDisbursementStub.AddIslamicLoanDisbResMsg;
import com.newgen.dscop.stub.ModLoanDisbursementStub.AddIslamicLoanDisbRes_type0;
import com.newgen.dscop.stub.ModLoanDisbursementStub.EffDts_type0;
import com.newgen.dscop.stub.ModLoanDisbursementStub.HeaderType;
import com.newgen.dscop.stub.ModLoanDisbursementStub.OtherApp_type0;
import com.newgen.dscop.stub.ModLoanDisbursementStub.UaeDds_type0;
import com.newgen.dscop.stub.ModLoanDisbursementStub.UdeVals_type0;
import com.newgen.dscop.stub.ModLoanDisbursementStub.UdfCharf_type0;
import com.newgen.dscop.stub.ModLoanDisbursementStub.UdfDatef_type0;
import com.newgen.dscop.stub.ModLoanDisbursementStub.UdfNumf_type0;

public class AddIslamicLoanDisb extends DSCOPServiceHandler {

		protected String sWSDLPath = "";
		protected static String sCabinet = "";
		protected String sUser = "";
		protected String sPassword = "";
		protected boolean sLoginReq = false;
		protected static long lTimeOut = 30000;
		String xmlInput = "";
		String sOrg_put = "";

		@SuppressWarnings("finally")
		public String AddIslamicLoan(String sInputXML){
			LogGEN.writeTrace("CBG_Log", "Fuction called DebitEnquiry");
			LogGEN.writeTrace("CBG_Log", "AddDebitCards sInputXML: ");
			XMLParser xmlParser = new XMLParser();
			xmlParser.setInputXML(sInputXML);
			String sOutput="";
			String sReturnCode= "";
			String sErrorDetail="";
			String sErrorDesc = "";
			Date d= new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String sDate = dateFormat.format(d);
			try{
				LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
				ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("ModLoanDisbursement");
				LogGEN.writeTrace("CBG_Log", "ModLoanDisbursement WebServiceConfig Map : "  +wsConfig.toString());
				sWSDLPath=(String)wsConfig.get(0);
				sCabinet=(String)wsConfig.get(1);
				sUser=(String)wsConfig.get(2);
				sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
				sPassword=(String)wsConfig.get(4);
				lTimeOut=Long.valueOf((String)wsConfig.get(5));
				LogGEN.writeTrace("CBG_Log", "Add_Debit_Card_Issuence WSDL PATH: "+sWSDLPath);
				LogGEN.writeTrace("CBG_Log", "Add_Debit_Card_Issuence CABINET: "+sCabinet);
				LogGEN.writeTrace("CBG_Log", "Add_Debit_Card_Issuence USER: "+sUser);
				LogGEN.writeTrace("CBG_Log", "Add_Debit_Card_Issuence PASSWORD: "+sPassword);
				LogGEN.writeTrace("CBG_Log", "Add_Debit_Card_Issuence LOGIN_REQ: "+sLoginReq);
				LogGEN.writeTrace("CBG_Log", "Add_Debit_Card_Issuence TIME_OUT: "+lTimeOut);
				//Instantiating the Stub object: 
				ModLoanDisbursementStub stub = new ModLoanDisbursementStub(sWSDLPath);
				AddIslamicLoanDisbReqMsg reqMsg = new AddIslamicLoanDisbReqMsg();
				AddIslamicLoanDisbReq_type0 reqMsg_type0 = new AddIslamicLoanDisbReq_type0();
				AddIslamicLoanDisbResMsg response_msg = new AddIslamicLoanDisbResMsg();
				AddIslamicLoanDisbRes_type0 response_type0  = new AddIslamicLoanDisbRes_type0();
				
				String sRef_no = xmlParser.getValueOf("REF_NO");
				String senderId = xmlParser.getValueOf("SenderId");
				
				HeaderType headerType_req = new HeaderType();
				headerType_req.setServiceName("ModLoanDisbursement");
				headerType_req.setVersionNo("1.0");
				headerType_req.setServiceAction("Modify");
				headerType_req.setSysRefNumber(sRef_no);
				headerType_req.setSenderID(senderId);
				headerType_req.setReqTimeStamp(sDate);
				
				String branchCode = xmlParser.getValueOf("branchCode");
				String customerId = xmlParser.getValueOf("customerId");
				String productCode = xmlParser.getValueOf("productCode");
				String valueDate = xmlParser.getValueOf("valueDate");
				String maturityDate = xmlParser.getValueOf("maturityDate");
				String currency = xmlParser.getValueOf("currency");
				String amountFinanced = xmlParser.getValueOf("amountFinanced");
				String noOfInstallments = xmlParser.getValueOf("noOfInstallments");
				String units = xmlParser.getValueOf("units");
				String frequency = xmlParser.getValueOf("frequency");
				String installmentStartDate = xmlParser.getValueOf("installmentStartDate");
				String dueDateOn = xmlParser.getValueOf("dueDateOn");
				String crAcc = xmlParser.getValueOf("crAcc");
				String crdBrn = xmlParser.getValueOf("crdBrn");
				String dbtAcc = xmlParser.getValueOf("dbtAcc");
				String drBrn = xmlParser.getValueOf("drBrn");
				String reqRefNo = xmlParser.getValueOf("reqRefNo");
				String makerId = xmlParser.getValueOf("makerId");
				String makerDtStamp = xmlParser.getValueOf("makerDtStamp");
				String authStat = xmlParser.getValueOf("authStat");
				String checkerId = xmlParser.getValueOf("checkerId");
				String checkerDtStamp = xmlParser.getValueOf("checkerDtStamp");
				String applicationNum = xmlParser.getValueOf("applicationNum");
				String altAccNo = xmlParser.getValueOf("altAccNo");
				String userRefNo = xmlParser.getValueOf("userRefNo");
				String narrCustom = xmlParser.getValueOf("narrCustom");
				String internalremarks = xmlParser.getValueOf("internalremarks");
				String murabahaLoanType = xmlParser.getValueOf("murabahaLoanType");
				String oldMurabahaAcc = xmlParser.getValueOf("oldMurabahaAcc");
				String loanAgainstSal = xmlParser.getValueOf("loanAgainstSal");
				
				reqMsg_type0.setBranchCode(branchCode);
				reqMsg_type0.setCustomerId(customerId);
				reqMsg_type0.setProductCode(productCode);
				reqMsg_type0.setValueDate(valueDate);
				reqMsg_type0.setMaturityDate(maturityDate);
				reqMsg_type0.setCurrency(currency);
				reqMsg_type0.setAmountFinanced(amountFinanced);
				reqMsg_type0.setNoOfInstallments(noOfInstallments);
				reqMsg_type0.setUnits(units);
				reqMsg_type0.setFrequency(frequency);
				reqMsg_type0.setInstallmentStartDate(installmentStartDate);
				reqMsg_type0.setDueDateOn(dueDateOn);
				reqMsg_type0.setCrAcc(crAcc);
				reqMsg_type0.setCrdBrn(crdBrn);
				reqMsg_type0.setDbtAcc(dbtAcc);
				reqMsg_type0.setDrBrn(drBrn);
				reqMsg_type0.setReqRefNo(reqRefNo);
				reqMsg_type0.setMakerId(makerId);
				reqMsg_type0.setMakerDtStamp(makerDtStamp);
				reqMsg_type0.setAuthStat(authStat);
				reqMsg_type0.setCheckerId(checkerId);
				reqMsg_type0.setCheckerDtStamp(checkerDtStamp);
				reqMsg_type0.setApplicationNum(applicationNum);
				reqMsg_type0.setAltAccNo(altAccNo);
				reqMsg_type0.setUserRefNo(userRefNo);
				reqMsg_type0.setNarrCustom(narrCustom);
				reqMsg_type0.setInternalremarks(internalremarks);
				reqMsg_type0.setMurabahaLoanType(murabahaLoanType);
				reqMsg_type0.setOldMurabahaAcc(oldMurabahaAcc);
				reqMsg_type0.setLoanAgainstSal(loanAgainstSal);
				
				UdfCharf_type0[] udfCharf_type0 = new UdfCharf_type0[]{};
				List<UdfCharf_type0> list =	ConverterUtil.toList(udfCharf_type0);
				
				int start = xmlParser.getStartIndex("udfCharf_type", 0, 0);
				int deadEnd = xmlParser.getEndIndex("udfCharf_type", start, 0);
				int noOfFieldss = xmlParser.getNoOfFields("udfCharf", start,deadEnd);
				int end = 0;
				for(int i = 0; i < noOfFieldss; ++i){
					start = xmlParser.getStartIndex("udfCharf", end, 0);
					end = xmlParser.getEndIndex("udfCharf", start, 0);
					String fldNam = xmlParser.getValueOf("fldNam", start, end);
					String fieldValue = xmlParser.getValueOf("fieldValue", start, end);
					String trnDesc = xmlParser.getValueOf("trnDesc", start, end);

					UdfCharf_type0 udfCharf_type = new UdfCharf_type0();
					udfCharf_type.setFldNam(fldNam);
					udfCharf_type.setFieldValue(fieldValue);
					udfCharf_type.setTrnDesc(trnDesc);
					list.add(udfCharf_type);
				}
				udfCharf_type0 =(UdfCharf_type0[])list.toArray(new UdfCharf_type0[list.size()]);
				reqMsg_type0.setUdfCharf(udfCharf_type0);
				
				EffDts_type0[] effDts_type0 = new EffDts_type0[]{};
				List<EffDts_type0> list_EffDts_type0 =	ConverterUtil.toList(effDts_type0);
				
				start = xmlParser.getStartIndex("effDts_type", 0, 0);
				deadEnd = xmlParser.getEndIndex("effDts_type", start, 0);
				noOfFieldss = xmlParser.getNoOfFields("effDts", start,deadEnd);
				end = 0;
				for(int i = 0; i < noOfFieldss; ++i){
					start = xmlParser.getStartIndex("effDts", end, 0);
					end = xmlParser.getEndIndex("effDts", start, 0);
					EffDts_type0 effDts_type = new EffDts_type0();
					String effectiveDate = xmlParser.getValueOf("effectiveDate");
					UdeVals_type0[] udeVals_type0 = new UdeVals_type0[]{};
					List<UdeVals_type0> listUdeVals_type0 =	ConverterUtil.toList(udeVals_type0);
					int start_Inside = xmlParser.getStartIndex("udeVals_type", 0, 0);
					int deadEnd_Inside = xmlParser.getEndIndex("udeVals_type", start_Inside, 0);
					int noOfFieldss_Inside = xmlParser.getNoOfFields("udeVals", start_Inside,deadEnd_Inside);
					int end_Inside = 0;
					for(int j = 0; j < noOfFieldss_Inside; ++j){
						start_Inside = xmlParser.getStartIndex("udeVals", end_Inside, 0);
						end_Inside = xmlParser.getEndIndex("udeVals", start_Inside, 0);
						String udeId = xmlParser.getValueOf("udeId", start_Inside, end_Inside);
						String udeVal = xmlParser.getValueOf("udeVal", start_Inside, end_Inside);
						String rateCode = xmlParser.getValueOf("rateCode", start_Inside, end_Inside);
						String codeUsage = xmlParser.getValueOf("codeUsage", start_Inside, end_Inside);

						UdeVals_type0 udeVals_type = new UdeVals_type0();
						udeVals_type.setUdeId(udeId);
						udeVals_type.setUdeVal(udeVal);
						udeVals_type.setRateCode(rateCode);
						udeVals_type.setCodeUsage(codeUsage);
						listUdeVals_type0.add(udeVals_type);
					}
					udeVals_type0 =(UdeVals_type0[])listUdeVals_type0.toArray(new UdeVals_type0[listUdeVals_type0.size()]);
					effDts_type.setEffectiveDate(effectiveDate);
					effDts_type.setUdeVals(udeVals_type0);
					list_EffDts_type0.add(effDts_type);
				}
				effDts_type0 =(EffDts_type0[])list_EffDts_type0.toArray(new EffDts_type0[list_EffDts_type0.size()]);
				reqMsg_type0.setEffDts(effDts_type0);
				
				UdfNumf_type0[] udfNumf_type0 = new UdfNumf_type0[]{};
				List<UdfNumf_type0> listUdfNumf_type0 =	ConverterUtil.toList(udfNumf_type0);
				start = xmlParser.getStartIndex("udfNumf_type", 0, 0);
				deadEnd = xmlParser.getEndIndex("udfNumf_type", start, 0);
				noOfFieldss = xmlParser.getNoOfFields("udfNumf", start,deadEnd);
				end = 0;
				for(int i = 0; i < noOfFieldss; ++i){
					start = xmlParser.getStartIndex("udfNumf", end, 0);
					end = xmlParser.getEndIndex("udfNumf", start, 0);
					String fldNam = xmlParser.getValueOf("fldNam", start, end);
					String fieldValue = xmlParser.getValueOf("fieldValue", start, end);
					String trnDesc = xmlParser.getValueOf("trnDesc", start, end);
					
					UdfNumf_type0 udfNumfStr = new UdfNumf_type0();
					udfNumfStr.setFldNam(fldNam);
					udfNumfStr.setFieldValue(fieldValue);
					udfNumfStr.setTrnDesc(trnDesc);
					listUdfNumf_type0.add(udfNumfStr);
				}
				udfNumf_type0 =(UdfNumf_type0[])listUdfNumf_type0.toArray(new UdfNumf_type0[listUdfNumf_type0.size()]);
				reqMsg_type0.setUdfNumf(udfNumf_type0);

				UdfDatef_type0[] udfDatef_type0 = new UdfDatef_type0[]{};
				List<UdfDatef_type0> listUdfDatef_type0 =	ConverterUtil.toList(udfDatef_type0);

				start = xmlParser.getStartIndex("udfDatef_type", 0, 0);
				deadEnd = xmlParser.getEndIndex("udfDatef_type", start, 0);
				noOfFieldss = xmlParser.getNoOfFields("udfDatef", start,deadEnd);
				end = 0;
				for(int i = 0; i < noOfFieldss; ++i){
					start = xmlParser.getStartIndex("udfDatef", end, 0);
					end = xmlParser.getEndIndex("udfDatef", start, 0);
					String fldName_udfDatef = xmlParser.getValueOf("fldNam");
					String fieldValue_udfDatef = xmlParser.getValueOf("fieldValue");
					
					UdfDatef_type0 udfDatef_type0S = new UdfDatef_type0();
					udfDatef_type0S.setFldName(fldName_udfDatef);
					udfDatef_type0S.setFieldValue(fieldValue_udfDatef);
					
					listUdfDatef_type0.add(udfDatef_type0S);
				}
				udfDatef_type0 =(UdfDatef_type0[])listUdfDatef_type0.toArray(new UdfDatef_type0[listUdfDatef_type0.size()]);
				reqMsg_type0.setUdfDatef(udfDatef_type0);
				
				OtherApp_type0[] otherApp_type0 = new OtherApp_type0[]{};
				List<OtherApp_type0> listOtherApp_type0 =	ConverterUtil.toList(otherApp_type0);
				
				start = xmlParser.getStartIndex("otherApp_type", 0, 0);
				deadEnd = xmlParser.getEndIndex("otherApp_type", start, 0);
				noOfFieldss = xmlParser.getNoOfFields("otherApp", start,deadEnd);
				end = 0;
				for(int i = 0; i < noOfFieldss; ++i){
					start = xmlParser.getStartIndex("otherApp", end, 0);
					end = xmlParser.getEndIndex("otherApp", start, 0);
					String applNo = xmlParser.getValueOf("applNo");
					String customerName = xmlParser.getValueOf("customerName");
					String responsibility = xmlParser.getValueOf("responsibility");
					String liability = xmlParser.getValueOf("liability");
					String liabilityAmt = xmlParser.getValueOf("liabilityAmt");
					String effectiveDate_otherApp = xmlParser.getValueOf("effectiveDate");
					
					OtherApp_type0 otherApp_t = new OtherApp_type0();
					otherApp_t.setApplNo(applNo);
					otherApp_t.setCustomerName(customerName);
					otherApp_t.setLiability(liability);
					otherApp_t.setLiabilityAmt(liabilityAmt);
					otherApp_t.setResponsibility(responsibility);
					otherApp_t.setEffectiveDate(effectiveDate_otherApp);
					
					listOtherApp_type0.add(otherApp_t);
				}
				otherApp_type0 =(OtherApp_type0[])listOtherApp_type0.toArray(new OtherApp_type0[listOtherApp_type0.size()]);
				reqMsg_type0.setOtherApp(otherApp_type0);
				
				
				UaeDds_type0[] UaeDds_type0 = new UaeDds_type0[]{};
				List<UaeDds_type0> listUaeDds_type0 =	ConverterUtil.toList(UaeDds_type0);
				start = xmlParser.getStartIndex("uaeDds_type", 0, 0);
				deadEnd = xmlParser.getEndIndex("uaeDds_type", start, 0);
				noOfFieldss = xmlParser.getNoOfFields("uaeDds", start,deadEnd);
				end = 0;
				for(int i = 0; i < noOfFieldss; ++i){
					start = xmlParser.getStartIndex("uaeDds", end, 0);
					end = xmlParser.getEndIndex("uaeDds", start, 0);
					String ddsFlag = xmlParser.getValueOf("ddsFlag");
					String ibanAc = xmlParser.getValueOf("ibanAc");
					String payerBank = xmlParser.getValueOf("payerBank");
					String paymentType = xmlParser.getValueOf("paymentType");
					String remarks = xmlParser.getValueOf("remarks");
					String payerBankName = xmlParser.getValueOf("payerBankName");
					String errorCode = xmlParser.getValueOf("errorCode");
					String errorMessage = xmlParser.getValueOf("errorMessage");
					
					UaeDds_type0 uaeDds_T = new UaeDds_type0();
					uaeDds_T.setDdsFlag(ddsFlag);
					uaeDds_T.setIbanAc(ibanAc);
					uaeDds_T.setPayerBank(payerBank);
					uaeDds_T.setPaymentType(paymentType);
					uaeDds_T.setRemarks(remarks);
					uaeDds_T.setPayerBankName(payerBankName);
					uaeDds_T.setErrorCode(errorCode);
					uaeDds_T.setErrorMessage(errorMessage);
					listUaeDds_type0.add(uaeDds_T);
				}
				UaeDds_type0 =(UaeDds_type0[])listUaeDds_type0.toArray(new UaeDds_type0[listUaeDds_type0.size()]);
				reqMsg_type0.setUaeDds(UaeDds_type0);
				
				reqMsg.setAddIslamicLoanDisbReq(reqMsg_type0);
				reqMsg.setHeader(headerType_req);
				xmlInput= stub.getinputXML(reqMsg);
				LogGEN.writeTrace("CBG_Log", "AddIslamicLoanDisb xmlInput xml : "+xmlInput);
				
				response_msg = stub.addIslamicLoanDisb_Oper(reqMsg);
				headerType_req=response_msg.getHeader();
				response_type0 = response_msg.getAddIslamicLoanDisbRes();
				stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
				sOrg_put=stub.outputXML;
				LogGEN.writeTrace("CBG_Log", "AddIslamicLoanDisbResMsg sOrg_put: "+sOrg_put);

				sReturnCode =  headerType_req.getReturnCode();
				sErrorDetail = headerType_req.getErrorDetail();
				sErrorDesc = headerType_req.getErrorDescription();
				if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
				{
					StringBuilder details = new StringBuilder(); 
					if(response_type0 != null){
						
					}
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
							"<Output>"+
							"<Option>AddIslamicLoanDisb</Option>" +
							"<returnCode>"+sReturnCode+"</returnCode>" +
							"<errorDescription>"+sErrorDetail+"</errorDescription>"+
							"<AddIslamicLoanDisbRes>"+
							details+
							"</AddIslamicLoanDisbRes>"+	
							"</Output>";
				}
				else
				{
					LogGEN.writeTrace("CBG_Log", "Failed");
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>AddIslamicLoanDisb</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><Status>ERROR</Status><td>Unable to Add loan Record</td></Output>";
				}
				
				
			}catch(Exception e){
				LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
				LogGEN.writeTrace("CBG_Log", "Error Trace in Web Service :"+e.getStackTrace());
				sReturnCode="-1";
				sErrorDetail=e.getMessage();
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>AddIslamicLoanDisb</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to add islamic loan.</td></Output>";
				e.printStackTrace();			
			}finally{
				LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
				if(sOutput.trim().length()<1){
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>AddIslamicLoanDisb</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to add islamic loan.</td></Output>";
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

				try
				{
					dbpass=AESEncryption.decrypt(dbpass);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				DBConnection con=new DBConnection();

				String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
						"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
						"?,sysdate,'"+  Status + "')";
				LogGEN.writeTrace("CBG_Log", " Add  Query : finally :" + Query);
				//			LogGEN.writeTrace("CBG_Log", "sOrg_Output : finally :" + sOrgnlOutput);
				try {
					LogGEN.writeTrace("CBG_Log", "*****Executing Query : AddIslamicLoanDisb*****");
					con.executeClob("jdbc:oracle:thin:@" + dburl, dbuser, dbpass, Query, inputXml.replaceAll("'", "''"), sOrg_put.replaceAll("'", "''"));
					LogGEN.writeTrace("CBG_Log", "*****Query Executed : AddIslamicLoanDisb*****");
				} catch (Exception e2) {
					e2.getMessage();
				}
				return sOutput;	
			}
			
		}

}
