/*
 * By Mohit
 * 04-11-2024	prod_04112024  all API calls to be inserted into raroc_audit table
 * 11-12-2024	prod_11122024 Facility_Amount_proposed=0 should not be sent to the projectedRAROC api		
 * 11-02-2025	prod_11022025 only Main Limits and those sub limits which will be modified will be used for RAROC calculation
 */
package com.newgen.iforms.user.raroc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.user.config.Constants;
import com.newgen.iforms.user.config.RarocLogger;
import com.newgen.iforms.user.raroc.bean.AaPaymentScheduleResponse;
import com.newgen.iforms.user.raroc.bean.FacilitiesByLimitIdResponse;
import com.newgen.iforms.user.raroc.bean.LoanAccountDetailsResponse;
import Jdts.Parser.XMLParser;

public class Common implements Constants {

	public static final Logger log = RarocLogger.getInstance().getLogger();
	protected IFormReference formObject;
	boolean callsStatusFlag = true;
	int iProcessedCustomer = 0;
	String sProcessName = "";
	String sFieldName = "";
	String sUserName = "";
	String sGeneralData = "";
	String sActivityName = "";
	String sWorkitemID = "";
	String sUserIndex = "";
	String sMode = "";
	String sActivityID = "";
	String sProcessDefId = "";
	String engineName;
	String sessionId;
	String senderId;
	String sEngineName = "";
	String sSessionId = "";
	String serverUrl = "";
	String sJTSIP = "";
	String sJTSPORT = "";
	String sJTSApp = "";
	String strItemIndex = "";
	String personName = "";

	// Addded by Shivanshu
	public Common(IFormReference formObject) {
		this.formObject = formObject;
		sActivityName = formObject.getActivityName();
		engineName = formObject.getObjGeneralData().getM_strEngineName();
		sProcessName = formObject.getProcessName();
		sessionId = formObject.getObjGeneralData().getM_strDMSSessionId();
		sUserName = formObject.getUserName();
		sFieldName = sProcessName + "_";
		serverUrl = formObject.getObjGeneralData().getM_strServletPath();
		sJTSIP = formObject.getObjGeneralData().getM_strJTSIP();
		sJTSPORT = formObject.getObjGeneralData().getM_strJTSPORT();
		sJTSApp = formObject.getObjGeneralData().getM_strAppServerType();
		strItemIndex = formObject.getObjGeneralData().getM_strFolderId();
		sWorkitemID = formObject.getObjGeneralData().getM_strProcessInstanceId();
		sUserIndex = formObject.getObjGeneralData().getM_strUserIndex();
		log.info("sUserIndex: " + sUserIndex);
	}

	public Common() {

	}

	public String getReturnMessage(Boolean success, String data, String message) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", success);
		jsonObject.put("data", data);
		jsonObject.put("message", message);
		return jsonObject.toString();
	}

	public String getReturnMessage(Boolean success, String message) throws JSONException {
		return getReturnMessage(success, "", message);
	}

	public String getReturnMessage(Boolean success) throws JSONException {
		return getReturnMessage(success, "", "");
	}

	// TANU
	public FacilitiesByLimitIdResponse[] executeIntFacility(IFormReference iFormReference, String data, String limitid)
			throws Exception {
		log.info("executeIntFacility  : INSIDE");
		String inputXML = "";
		String retMsg = null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		String date = "";
		try {
			date = df.format(d);
		} catch (Exception e) {
			log.info("Exception in date " + e);
		}
		String processName = iFormReference.getProcessName();
		log.info("executeIntFacility : processName " + processName);
		String workitemName = iFormReference.getObjGeneralData().getM_strProcessInstanceId();
		log.info("executeIntFacility : workitemName " + workitemName);

		// inputXML = loanDetailsEnquiry(workitemName);
		inputXML = facilitiesLimitID(iFormReference, workitemName, limitid);
		log.info("executeIntFacility : inputXML : " + inputXML);

		T24_Integration t24integration = new T24_Integration(processName, workitemName);
		String responseXML = t24integration.executeIntegrationCall(inputXML);

		log.info("executeIntFacility : outputXML===>> " + responseXML);
		String status = (responseXML.contains("<STATUS_CODE>100</STATUS_CODE>")
				|| responseXML.contains("\"successIndicator\": \"Success\"")) ? "SUCCESS" : "ERROR";
		Common Common = new Common(iFormReference);
		Common.insertIntoRarocIntegrationLogs(iFormReference, "facilitiesParticularLimitDetails", inputXML, responseXML,
				date, status);// prod_04112024
		// to_add_insert
		// TANU
		FacilitiesByLimitIdResponse[] facilitiesByLimitIdResponse = populatelFacilityDetails(iFormReference,
				responseXML, limitid);
		return facilitiesByLimitIdResponse;
	}

	public String executeRepaymentSchedule(IFormReference iFormReference, String data, String arrangementid, int g,
			String LimitId) throws Exception {
		log.info("executeRepaymentSchedule  : INSIDE");
		String inputXML = "";
		String retMsg = null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		String date = "";
		try {
			date = df.format(d);
		} catch (Exception e) {
			log.info("Exception in dateeeeee " + e);
		}
		String processName = iFormReference.getProcessName();
		log.info("executeRepaymentSchedule : processName " + processName);
		String workitemName = iFormReference.getObjGeneralData().getM_strProcessInstanceId();
		log.info("executeRepaymentSchedule : workitemName " + workitemName);

		inputXML = RepaymentSchedule(iFormReference, workitemName, arrangementid);
		// inputXML = facilitiesLimitID(workitemName);
		log.info("executeRepaymentSchedule : inputXML : " + inputXML);

		T24_Integration t24integration = new T24_Integration(processName, workitemName);
		String responseXML = t24integration.executeIntegrationCall(inputXML);

		log.info("executeRepaymentSchedule : outputXML===>> " + responseXML);
		String status = (responseXML.contains("<STATUS_CODE>100</STATUS_CODE>")
				|| responseXML.contains("\"successIndicator\": \"Success\"")) ? "SUCCESS" : "ERROR";
		Common Common = new Common(iFormReference);
		Common.insertIntoRarocIntegrationLogs(iFormReference, "LoanpayoffdetailsEnquiry", inputXML, responseXML, date,
				status);// prod_04112024
		// to_add_insert

		populatelRepaymentDetails(iFormReference, responseXML, arrangementid, LimitId);
		return responseXML;
	}

	public String executeIntCallLoan(IFormReference iFormReference, String data, String loanaccountnumber, int f)
			throws Exception {
		log.info("executeIntCallLoan   : INSIDE");
		String inputXML = "";
		String retMsg = null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		String date = "";
		try {
			date = df.format(d);
		} catch (Exception e) {
			log.info("Exception in dateeeeee!!!!!! " + e);
		}
		String processName = iFormReference.getProcessName();
		log.info("executeIntCallLoan : processName " + processName);
		String workitemName = iFormReference.getObjGeneralData().getM_strProcessInstanceId();
		log.info("executeIntCallLoan : workitemName " + workitemName);

		inputXML = LoanDetails(iFormReference, workitemName, loanaccountnumber);
		// inputXML = facilitiesLimitID(workitemName);
		log.info("executeIntCallLoan : inputXML : " + inputXML);

		T24_Integration t24integration = new T24_Integration(processName, workitemName);
		String responseXML = t24integration.executeIntegrationCall(inputXML);

		log.info("executeIntCallLoan : outputXML===>> " + responseXML);
		String status = (responseXML.contains("<STATUS_CODE>100</STATUS_CODE>")
				|| responseXML.contains("\"successIndicator\": \"Success\"")) ? "SUCCESS" : "ERROR";
		Common Common = new Common(iFormReference);
		Common.insertIntoRarocIntegrationLogs(iFormReference, "LoandetailsEnquiry", inputXML, responseXML, date,
				status);// prod_04112024
		// to_add_insert
		populatelLoanDetails(iFormReference, responseXML);
		return responseXML;
	}

	public String facilitiesLimitID(IFormReference iformrefrence, String workitemName, String limitid) {
		log.info("facilitiesLimitID : INSIDE ");
		String inputXML = "";
		// String userID= iformrefrence.getUserName();
		String workstep = iformrefrence.getActivityName();
		String processName = iformrefrence.getProcessName();
		String CallName = "facilitiesParticularLimitDetails";
		String username = iformrefrence.getUserName();

		try {
			inputXML = "<EthixInputRequest>" + "<CallName>" + CallName + "</CallName>" + "<IsREST>Y</IsREST>"
					+ "<Format>JSON</Format>" + "<DirectDataBaseFlag>N</DirectDataBaseFlag>"
					+ "<ResponseRequiredInXML>Y</ResponseRequiredInXML>" + "<Params>" + "<Param>" + "<Key>limitid</Key>"
					+ "<Value>" + limitid + "</Value>" // here we have to take commitment no from facility tab
					+ "</Param>" + "<Param>" + "<Key>productline</Key>" + "<Value>LENDING</Value>" + "</Param>"
					+ "</Params>" + "<ProcessName>" + processName + "</ProcessName>" + "<WIName>" + workitemName
					+ "</WIName>" + "<MsgId>" + getNextT24Sequence() + "</MsgId>" + "<UserId>" + getUserIndex(username)
					+ "</UserId>" + "<UserName>" + username + "</UserName>" + "<WorkStep>" + workstep + "</WorkStep>"
					+ "</EthixInputRequest>";

			log.info("facilitiesLimitID  : inputXML" + inputXML);

		} catch (Exception e) {
			log.error("Exception : ", e);
		}
		return inputXML;
	}

	public String RepaymentSchedule(IFormReference iFormReference, String workitemName, String arrangementid) {
		log.info("loanDetailsEnquiry : INSIDE ");
		String inputXML = "";
		String username = iFormReference.getUserName();
		String workstep = iFormReference.getActivityName();
		String processName = iFormReference.getProcessName();
		String CallName = "LoanpayoffdetailsEnquiry";
//		String arrangementId = arrangementid; // response arrangement id coming from facility API
//
//		String fileVersion = "SIM";
//		String simulationRef = "AASIMR22267KSPF3ZP"; // need to check if this will be hardcode

		try {
			JSONObject requestObject = new JSONObject();
			// requestObject.put("arrangementId", arrangementId);
//			requestObject.put("fileVersion", fileVersion);
//			requestObject.put("simulationRef", simulationRef);

			log.info("loanDetailsEnquiry : RequestObject JSON :" + requestObject);

			inputXML = "<EthixInputRequest>" + "<CallName>" + CallName + "</CallName>" + "<IsREST>Y</IsREST>"
					+ "<Format>JSON</Format>" + "<DirectDataBaseFlag>N</DirectDataBaseFlag>"
					+ "<ResponseRequiredInXML>Y</ResponseRequiredInXML>" + "<Params>" + "<Param>"
					+ "<Key>arrangementId</Key>" + "<Value>" + arrangementid + "</Value>" + "</Param>" + "</Params>"
					+ "<ProcessName>" + processName + "</ProcessName>" + "<WIName>" + workitemName + "</WIName>"
					+ "<MsgId>" + getNextT24Sequence() + "</MsgId>" + "<UserId>" + getUserIndex(username) + "</UserId>"
					+ "<UserName>" + username + "</UserName>" + "<WorkStep>" + workstep + "</WorkStep>"
					+ "</EthixInputRequest>";

			log.info("RepaymentDeatils  : inputXML" + inputXML);

		} catch (Exception e) {
			log.error("Exception : ", e);
		}
		return inputXML;

	}

	// TANU
	public String LoanDetails(IFormReference iFormReference, String workitemName, String loanaccountnumber) {
		log.info("loanDetailsEnquiry : INSIDE ");
		String inputXML = "";
		String username = iFormReference.getUserName();

		String workstep = iFormReference.getActivityName();
		String processName = iFormReference.getProcessName();
		String CallName = "LoandetailsEnquiry";
		String loanAccountNo = loanaccountnumber; // response account no coming from facility API

		try {
			JSONObject requestObject = new JSONObject();
			requestObject.put("loanAccountNo", loanAccountNo);

			log.info("loanDetailsEnquiry : RequestObject JSON :" + requestObject);

			inputXML = "<EthixInputRequest>" + "<CallName>" + CallName + "</CallName>" + "<IsREST>Y</IsREST>"
					+ "<Format>JSON</Format>" + "<DirectDataBaseFlag>N</DirectDataBaseFlag>"
					+ "<ResponseRequiredInXML>Y</ResponseRequiredInXML>" + "<RequestObject>" + requestObject.toString()
					+ "</RequestObject>" + "<ProcessName>" + processName + "</ProcessName>" + "<WIName>" + workitemName
					+ "</WIName>" + "<MsgId>" + getNextT24Sequence() + "</MsgId>" + "<UserId>" + getUserIndex(username)
					+ "</UserId>" + "<UserName>" + username + "</UserName>" + "<WorkStep>" + workstep + "</WorkStep>"
					+ "</EthixInputRequest>";

			log.info("loanDetailsEnquiry  : inputXML" + inputXML);

		} catch (Exception e) {
			log.error("Exception : ", e);
		}
		return inputXML;

	}

	// TANU
	public void populatelLoanDetails(IFormReference iFormReference, String inputXML) throws Exception {

		com.newgen.iforms.user.raroc.XMLParser XMLParser0 = new com.newgen.iforms.user.raroc.XMLParser(inputXML);
		XMLParser0.setInputXML(inputXML);

		String res = XMLParser0.getValueOf("STATUS_CODE");
//			res = "100";//HARDCODED
		if (res.equalsIgnoreCase("100")) {
			String response = XMLParser0.getValueOf("ResponseObject");
//				//HARDCODED
//				response = "[{\r\n"
//						+ "                    \"LoanCategoryName\": \"COMM.TERM.LOANS\",                  \r\n"
//						+ "                    \"LoanCurrency\": \"AED\",                                  \r\n"
//						+ "                    \"CurrentOutstandingAmt\": \"64487457.86\",                 \r\n"
//						+ "                    \"LoanPrincipalAmount\": \"61901467.94\",                   \r\n"
//						+ "                    \"LoanPaidAmount\": \"7605828.48\",                         \r\n"
//						+ "                    \"Openingdate\": \"20190803\",                              \r\n"
//						+ "                    \"EndDate\": \"20270630\",                                  \r\n"
//						+ "                    \"NextAmountDetails\": \"1500000\",                        \r\n"
//						+ "                    \"TotalEMIsPaid\": \"5\",                                   \r\n"
//						+ "                    \"PendingEMIs\": \"6\",                                     \r\n"
//						+ "                    \"LoanOverdueAmount\": \"9023747.43\",                      \r\n"
//						+ "                    \"NextPaymentDate\": \"\",                                      \r\n"
//						+ "                    \"InterestRate\": \"\"                                        \r\n"
//						+ "        }]";
//
//				//END
			log.info("Response Loan" + response);
			ObjectMapper mapper = new ObjectMapper();
			LoanAccountDetailsResponse[] loanAccountDetailsResponse = mapper.readValue(response,
					LoanAccountDetailsResponse[].class);
			int index = 0;
			for (int i = 0; i < iFormReference.getDataFromGrid("LV_LoanDetailsAPI").size(); i++) {
				String cat = iFormReference.getTableCellValue("LV_LoanDetailsAPI", i, 5);
				log.info("@@@@@ CAT VALUE" + cat);
				if (cat.equalsIgnoreCase("")) {
					index = i;
					log.info("@@304 CAT Index" + index);
					break;
				}
			}

			for (int i = 0; i < loanAccountDetailsResponse.length; i++) {

				log.info("308>>>>>>" + index);

				String categoryName = loanAccountDetailsResponse[i].getLoanCategoryName();
				String currency = loanAccountDetailsResponse[i].getLoanCurrency();
				String currentOutsatAmnt = loanAccountDetailsResponse[i].getCurrentOutstandingAmt();
				String principalAmount = loanAccountDetailsResponse[i].getLoanPrincipalAmount();
				String paidAmount = loanAccountDetailsResponse[i].getLoanPaidAmount();
				String openingDate = loanAccountDetailsResponse[i].getOpeningdate();
				String endDate = loanAccountDetailsResponse[i].getEndDate();
				String nextAmountDetails = loanAccountDetailsResponse[i].getNextAmountDetails();
				// String totalNoEMIs = loanAccountDetailsResponse[i].getTotalEMIsPaid();
				String totalEMIsPaid = loanAccountDetailsResponse[i].getTotalEMIsPaid();
				String PendingEMIs = loanAccountDetailsResponse[i].getPendingEMIs();
				String overdueAmount = loanAccountDetailsResponse[i].getLoanOverdueAmount();
				String nextDueDate = loanAccountDetailsResponse[i].getNextPaymentDate();
				String interestRate = loanAccountDetailsResponse[i].getInterestRate();

				log.info("categoryname>>" + categoryName);

				iFormReference.setTableCellValue("LV_LoanDetailsAPI", index, 5, categoryName);
				iFormReference.setTableCellValue("LV_LoanDetailsAPI", index, 6, currency);
				log.info("currency>>" + currency);
				iFormReference.setTableCellValue("LV_LoanDetailsAPI", index, 7, currentOutsatAmnt);
				log.info("currentOutsatAmnt>>" + currentOutsatAmnt);
				iFormReference.setTableCellValue("LV_LoanDetailsAPI", index, 8, principalAmount);
				iFormReference.setTableCellValue("LV_LoanDetailsAPI", index, 9, paidAmount);
				iFormReference.setTableCellValue("LV_LoanDetailsAPI", index, 10, openingDate);
				iFormReference.setTableCellValue("LV_LoanDetailsAPI", index, 11, endDate);
				iFormReference.setTableCellValue("LV_LoanDetailsAPI", index, 12, nextAmountDetails);
				// iFormReference.setTableCellValue("LV_LoanDetailsAPI", index, 13,
				// totalNoEMIs);
				iFormReference.setTableCellValue("LV_LoanDetailsAPI", index, 14, totalEMIsPaid);
				iFormReference.setTableCellValue("LV_LoanDetailsAPI", index, 15, PendingEMIs);
				iFormReference.setTableCellValue("LV_LoanDetailsAPI", index, 16, overdueAmount);
				iFormReference.setTableCellValue("LV_LoanDetailsAPI", index, 17, nextDueDate);
				iFormReference.setTableCellValue("LV_LoanDetailsAPI", index, 18, interestRate);

				index++;

				log.info("index2 >>>" + index);
			}
		} else {
			throw new Exception("Loan Account Number Not Present");
		}

	}

	// TANU
	public FacilitiesByLimitIdResponse[] populatelFacilityDetails(IFormReference formObject, String inputXML,
			String limitid) throws Exception {

		FacilitiesByLimitIdResponse[] facilitiesByLimitIdResponse = null;

		com.newgen.iforms.user.raroc.XMLParser XMLParser0 = new com.newgen.iforms.user.raroc.XMLParser(inputXML);
		XMLParser0.setInputXML(inputXML);
		String res = XMLParser0.getValueOf("STATUS_CODE");
//		res = "100"; //HARDCODED
		if (res.equalsIgnoreCase("100")) {

			String response = XMLParser0.getValueOf("ResponseObject");
//			///HARDCODED
//			response ="[\r\n"
//					+ "            {\r\n"
//					+ "                \"acctNo\": \"991000000040\",\r\n"
//					+ "                \"arrangement\": \"AA192154ZCQT\",\r\n"
//					+ "                \"prodLine\": \"ACCOUNTS\",\r\n"
//					+ "                \"productGroup\": \"CURRENT.ACCOUNTS\"\r\n"
//					+ "            },\r\n"
//					+ "            {\r\n"
//					+ "                \"acctNo\": \"998000086611\",\r\n"
//					+ "                \"arrangement\": \"AA19215PKTPK\",\r\n"
//					+ "                \"prodLine\": \"LENDING\",\r\n"
//					+ "                \"productGroup\": \"COMM.TERM.LOANS\"\r\n"
//					+ "            }\r\n"
//					+ "        ]"
//				   ;
//			//END
			ObjectMapper mapper = new ObjectMapper();
			facilitiesByLimitIdResponse = mapper.readValue(response, FacilitiesByLimitIdResponse[].class);
			JSONArray Array = new JSONArray();

			for (int i = 0; i < facilitiesByLimitIdResponse.length; i++) {
				org.json.simple.JSONObject JsonObj = new org.json.simple.JSONObject();

				String LoanAccountNumber = facilitiesByLimitIdResponse[i].getAcctNo();
				log.info("Acct no>>" + LoanAccountNumber);
				String arrangement = facilitiesByLimitIdResponse[i].getArrangement();
				String productGroup = facilitiesByLimitIdResponse[i].getProductGroup();
				String productLine = facilitiesByLimitIdResponse[i].getProdLine();

				JsonObj.put("Limit ID", limitid);
				JsonObj.put("Loan Account Number	", LoanAccountNumber);
				JsonObj.put("Arrangement", arrangement);
				JsonObj.put("Product Group", productGroup);
				JsonObj.put("Product Line", productLine);

				Array.add(JsonObj);
			}
			log.info("FACILITY ID RESPONSE >>" + Array);
			formObject.addDataToGrid("LV_LoanDetailsAPI", Array);
		} else {
			throw new Exception("Status not 100");
		}

		return facilitiesByLimitIdResponse;

	}

	// TANU

	public void populatelRepaymentDetails(IFormReference iFormReference, String inputXML, String arrangementid,
			String LimitId) throws Exception {

		com.newgen.iforms.user.raroc.XMLParser XMLParser0 = new com.newgen.iforms.user.raroc.XMLParser(inputXML);
		XMLParser0.setInputXML(inputXML);
		String res = XMLParser0.getValueOf("STATUS_CODE");
//			res = "100";//HARDCODED
		if (res.equalsIgnoreCase("100")) {
			String response = XMLParser0.getValueOf("ResponseObject");
//				///HARDCODED
//				response = "[\r\n"
//						+ "            {\r\n"
//						+ "              \"PaymentDate\": \"2020-03-10\",\r\n"
//						+ "              \"TotalAmount\": \"\",\r\n"
//						+ "              \"Charge\": \"\",\r\n"
//						+ "              \"Interest\": \"\",\r\n"
//						+ "              \"Principal\": \"329,000.00\",\r\n"
//						+ "              \"Outstanding\": \"329,000.00\",\r\n"
//						+ "              \"ScheduleType\": \"PAID\"\r\n"
//						+ "            },\r\n"
//						+ "            {\r\n"
//						+ "              \"PaymentDate\": \"2020-10-25\",\r\n"
//						+ "              \"TotalAmount\": \"7,802.22\",\r\n"
//						+ "              \"Charge\": \"\",\r\n"
//						+ "              \"Interest\": \"7,802.22\",\r\n"
//						+ "              \"Principal\": \"0\",\r\n"
//						+ "              \"Outstanding\": \"329,000.00\",\r\n"
//						+ "              \"ScheduleType\": \"DUE\"\r\n"
//						+ "            },\r\n"
//						+ "            {\r\n"
//						+ "              \"PaymentDate\": \"2020-11-02\",\r\n"
//						+ "              \"TotalAmount\": \"128.76\",\r\n"
//						+ "              \"Charge\": \"122.63\",\r\n"
//						+ "              \"Interest\": \"\",\r\n"
//						+ "              \"Principal\": \"\",\r\n"
//						+ "              \"Outstanding\": \"329,000.00\",\r\n"
//						+ "              \"ScheduleType\": \"DUE\"\r\n"
//						+ "            }\r\n"
//						+ "          ]";
//				//END
			log.info("ResponseObject." + response);

			ObjectMapper mapper = new ObjectMapper();
			AaPaymentScheduleResponse[] AaPaymentScheduleResponse = mapper.readValue(response,
					AaPaymentScheduleResponse[].class);
			JSONArray Array = new JSONArray();
			log.info("ReponseData " + AaPaymentScheduleResponse.toString());

			int arrangeindex = 0;
			for (int i = 0; i < iFormReference.getDataFromGrid("LV_LoanDetailsAPI").size(); i++) {
				String date = iFormReference.getTableCellValue("LV_LoanDetailsAPI", i, 21);
				if (date.equalsIgnoreCase("")) {
					arrangeindex = i;
					break;
				}
			}

			for (int i = 0; i < AaPaymentScheduleResponse.length; i++) {

				org.json.simple.JSONObject JsonObj1 = new org.json.simple.JSONObject();

				// String LoanAccountNumber = AcbPayOffDetailresponse[i].getApplicationId() !=
				// null ? AcbPayOffDetailresponse[i].getApplicationId() : "";
				String charge = AaPaymentScheduleResponse[i].getCharge() != null
						? AaPaymentScheduleResponse[i].getCharge()
						: "";
				String interest = AaPaymentScheduleResponse[i].getInterest() != null
						? AaPaymentScheduleResponse[i].getInterest()
						: "";
				// String categoryName = AcbPayOffDetailresponse[i].getId() != null ?
				// AcbPayOffDetailresponse[i].getId() : "";
				String paymentDate = AaPaymentScheduleResponse[i].getPaymentDate() != null
						? AaPaymentScheduleResponse[i].getPaymentDate()
						: "";
				String outstanding = AaPaymentScheduleResponse[i].getOutstanding() != null
						? AaPaymentScheduleResponse[i].getOutstanding()
						: "";
				String principal = AaPaymentScheduleResponse[i].getPrincipal() != null
						? AaPaymentScheduleResponse[i].getPrincipal()
						: "";
				String amount = AaPaymentScheduleResponse[i].getTotalAmount() != null
						? AaPaymentScheduleResponse[i].getTotalAmount()
						: "";

				String scheduleType = AaPaymentScheduleResponse[i].getScheduleType() != null
						? AaPaymentScheduleResponse[i].getScheduleType()
						: "";

				JsonObj1.put("Limit Id", LimitId);
				JsonObj1.put("Arrangement ID", arrangementid);
				JsonObj1.put("Payment Date", paymentDate);
				JsonObj1.put("Total Amount", amount);
				JsonObj1.put("Charge", charge);
				JsonObj1.put("Interest", interest);
				JsonObj1.put("Principal Amount", principal);
				JsonObj1.put("Outstanding Amount", outstanding);
				JsonObj1.put("Schedule Type", scheduleType);

//					iFormReference.setTableCellValue("LV_REPAYMENT_DETAILS", arrangeindex, 0, LimitId);
//					iFormReference.setTableCellValue("LV_REPAYMENT_DETAILS", arrangeindex, 1, arrangementid);
//					iFormReference.setTableCellValue("LV_REPAYMENT_DETAILS", arrangeindex, 5, interest);
//					iFormReference.setTableCellValue("LV_REPAYMENT_DETAILS", arrangeindex, 7, outstanding);
//					iFormReference.setTableCellValue("LV_REPAYMENT_DETAILS", arrangeindex, 2, paymentDate);
//					iFormReference.setTableCellValue("LV_REPAYMENT_DETAILS", arrangeindex, 4, charge);
//					iFormReference.setTableCellValue("LV_REPAYMENT_DETAILS", arrangeindex, 6, principal);
//					iFormReference.setTableCellValue("LV_REPAYMENT_DETAILS", arrangeindex, 8, scheduleType);
//					iFormReference.setTableCellValue("LV_REPAYMENT_DETAILS", arrangeindex, 3, amount);
//					iFormReference.setTableCellValue("LV_REPAYMENT_DETAILS", arrangeindex, 9, "E");

				// arrangeindex++;

				Array.add(JsonObj1);

			}
			log.info("Repayment Schedule RESPONSE >>" + Array);
			iFormReference.addDataToGrid("LV_REPAYMENT_DETAILS", Array);
			iFormReference.setTableCellValue("LV_REPAYMENT_DETAILS", arrangeindex, 9, "E");
		} else {
			throw new Exception("Arrangement ID not Present");
		}

	}

	public void getTotalFD(IFormReference iFormReference) {

		JSONArray jsonArray = formObject.getDataFromGrid("table27");
		log.info("inside getTotalFD jsonArray");
		int gridCount = jsonArray.size();
		log.info("inside copyTopUpAECB gridCount" + gridCount);
		if (gridCount != 0) {
			float totalFDIncome = 0;
			for (int i = 0; i < gridCount; i++) {
				String fdIncome = iFormReference.getTableCellValue("table27", i, 4);
				totalFDIncome = totalFDIncome + Float.parseFloat(fdIncome);
			}
			log.info("inside totalFDIncome>>>>> " + totalFDIncome);
			// iFormReference.setValue("", String.valueOf(totalFDIncome));
		}
	}

	// added by suraj
	public String customerRealisedDetails(IFormReference iformrefrence) {

		log.info("customerRealisedDetails : INSIDE ");
		String realizedType = "customer";
		String contentType = "group";
		String realisedID = "";
		String inputXML = "";
		String msg = "";

		try {
			String processName = iformrefrence.getProcessName();
			String wiName = iformrefrence.getObjGeneralData().getM_strProcessInstanceId();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_SQL);
			Date currentDate = new Date();
			String requestDate = simpleDateFormat.format(currentDate);
			// requestDate = "2023-12-31";
			SimpleDateFormat DateTimeFormat = new SimpleDateFormat(Constants.REQ_DATE_TIME_FORMAT);
			String requestDateTime = DateTimeFormat.format(currentDate);

			String customerID = iformrefrence.getValue("LEAD_REF_NO").toString();
			String groupID = iformrefrence.getValue("Q_NG_RAROC_CUSTOMER_DETAILS_Group_ID").toString();

			log.info("groupID : " + groupID + " and customerID : " + customerID);
			if (groupID.equalsIgnoreCase("") || groupID.equalsIgnoreCase(null)) {
				realisedID = customerID;
				contentType = "customer";
			} else {
				realisedID = groupID;
			}

			String requestParameter = "<Param>" + "<Key>realizeType</Key><Value>" + realizedType + "</Value>"
					+ "</Param>" + "<Param>" + "<Key>date</Key><Value>" + requestDate + "</Value>" + "</Param>"
					+ "<Param>" + "<Key>content</Key><Value>" + realisedID + "</Value>" + "</Param>" + "<Param>"
					+ "<Key>contentType</Key><Value>" + contentType + "</Value>" + "</Param>";

			inputXML = inputXML(iformrefrence, requestParameter);
			log.info("inputXML....++++" + inputXML);
			T24_Integration t24integration = new T24_Integration(processName, wiName);
			String responseXML = t24integration.executeIntegrationCall(inputXML);
			log.info("responseXML : outputXML===>> " + responseXML);
//			String outputXml = "<CBS_RESPONSE><STATUS_CODE>100</STATUS_CODE><STATUS_DESC>Success</STATUS_DESC><STATUS_REMARKS></STATUS_REMARKS><RESPONSE><rarocDetailsResponseDetails>{\r\n"
//					+ "  \"exceptionDetails\": {\r\n"
//					+ "    \"successIndicator\": \"Success\",\r\n"
//					+ "    \"messages\": []\r\n"
//					+ "  },\r\n"
//					+ "  \"body\": {\r\n"
//					+ "    \"rarocDetailsResponse\": [\r\n"
//					+ "      {\r\n"
//					+ "        \"lienBenefitAed\": \"0.0\",\r\n"
//					+ "        \"assetClass\": \"Corporate/GRE\",\r\n"
//					+ "        \"totalCapitalAed\": \"10981.074\",\r\n"
//					+ "        \"crossSellIncome\": \"0.0\",\r\n"
//					+ "        \"feeIncomeAed\": \"128541.0\",\r\n"
//					+ "        \"eclAed\": \"44071.200000000004\",\r\n"
//					+ "        \"customerSegment\": \"Corporate Large\",\r\n"
//					+ "        \"version\": \"1709638204755\",\r\n"
//					+ "        \"interestIncomeAed\": \"110178.0\",\r\n"
//					+ "        \"totalReturn\": \"105708.45458491253\",\r\n"
//					+ "        \"operatingExpenseAed\": \"44174.47937749547\",\r\n"
//					+ "        \"groupId\": \"5827\",\r\n"
//					+ "        \"realizedRaroc\": \"9.62642220468713\",\r\n"
//					+ "        \"customerIndustry\": \"MFG Beverages & tobacco\",\r\n"
//					+ "        \"fundingCostAed\": \"122775.48457350272\",\r\n"
//					+ "        \"riskAdjustedReturn\": \"105708.45458491253\",\r\n"
//					+ "        \"legalEntity\": \"Almasraf\",\r\n"
//					+ "        \"reportingDate\": \"2023-12-31\",\r\n"
//					+ "        \"customerId\": \"206\",\r\n"
//					+ "        \"grossRevenueAed\": \"115943.51542649728\",\r\n"
//					+ "        \"customerName\": \"Customer 01\",\r\n"
//					+ "        \"customerInternalRating \": \"2\",\r\n"
//					+ "        \"totalFundedAed\": \"71000\",\r\n"
//					+ "        \"totalNonFundedAed\": \"20000\",\r\n"
//					+ "        \"customerCountry\": \"United Arab Emirates\"\r\n"
//					+ "      }\r\n"
//					+ "    ]\r\n"
//					+ "  }\r\n"
//					+ "}</rarocDetailsResponseDetails></RESPONSE></CBS_RESPONSE>";
//			// System.out.println(res);
//			log.info("customerRealisedDetails : outputXml===>> " + outputXml);

			msg = setT24CallData.setRarocdetails(iformrefrence,
					formObject.getObjGeneralData().getM_strProcessInstanceId(), responseXML, customerID, inputXML,
					requestDateTime);
			log.info("customerRealisedDetails msg ...." + msg);

		} catch (Exception e) {
			log.error("Exception : ", e);
		}
		// }
		return msg;
	}

	// added suraj
	public String groupRealisedDetails(IFormReference iformRefrence) {
		log.info("groupRealisedDetails INSIDE>>> ");
		String inputXML = "";
		String msg = "";
		String realizedType = "group";

		try {
			String processName = iformRefrence.getProcessName();
			String wiName = iformRefrence.getObjGeneralData().getM_strProcessInstanceId();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_SQL);
			Date currentDate = new Date();
			String requestDate = simpleDateFormat.format(currentDate);
			// requestDate = "2023-12-31";
			SimpleDateFormat DateTimeFormat = new SimpleDateFormat(Constants.REQ_DATE_TIME_FORMAT);
			String requestDateTime = DateTimeFormat.format(currentDate);
			String groupId = iformRefrence.getValue("Q_NG_RAROC_CUSTOMER_DETAILS_Group_ID").toString();
			log.info("groupId : " + groupId);

			String requestParameter = "<Param>" + "<Key>realizeType</Key><Value>" + realizedType + "</Value>"
					+ "</Param>" + "<Param>" + "<Key>date</Key><Value>" + requestDate + "</Value>" + "</Param>"
					+ "<Param>" + "<Key>content</Key><Value>" + groupId + "</Value>" + "</Param>";

			inputXML = inputXML(iformRefrence, requestParameter);
			T24_Integration t24integration = new T24_Integration(processName, wiName);
			String responseXML = t24integration.executeIntegrationCall(inputXML);
			log.info("responseXML : outputXML===>> " + responseXML);
			/*
			 * String outputXml =
			 * "<CBS_RESPONSE><STATUS_CODE>100</STATUS_CODE><STATUS_DESC>Success</STATUS_DESC><STATUS_REMARKS></STATUS_REMARKS><RESPONSE><rarocDetailsResponseDetails>{\r\n"
			 * + "  \"exceptionDetails\": {\r\n" +
			 * "    \"successIndicator\": \"Success\",\r\n" + "    \"messages\": []\r\n" +
			 * "  },\r\n" + "  \"body\": {\r\n" + "    \"rarocDetailsResponse\": [\r\n" +
			 * "      {\r\n" + "        \"lienBenefitAed\": \"0.0\",\r\n" +
			 * "        \"totalCapitalAed\": \"11371.074\",\r\n" +
			 * "        \"crossSellIncome\": \"0.0\",\r\n" +
			 * "        \"feeIncomeAed\": \"148541.0\",\r\n" +
			 * "        \"eclAed\": \"85071.20000000001\",\r\n" +
			 * "        \"version\": \"1709638204806\",\r\n" +
			 * "        \"interestIncomeAed\": \"130178.0\",\r\n" +
			 * "        \"totalReturn\": \"132829.83829919825\",\r\n" +
			 * "        \"operatingExpenseAed\": \"55526.724275454646\",\r\n" +
			 * "        \"groupId\": \"5827\",\r\n" +
			 * "        \"realizedRaroc\": \"11.68138016683369\",\r\n" +
			 * "        \"fundingCostAed\": \"132979.56620615578\",\r\n" +
			 * "        \"riskAdjustedReturn\": \"132829.83829919825\",\r\n" +
			 * "        \"legalEntity\": \"Almasraf\",\r\n" +
			 * "        \"reportingDate\": \"2023-12-31\",\r\n" +
			 * "        \"grossRevenueAed\": \"145739.43379384422\"\r\n" + "      }\r\n" +
			 * "    ]\r\n" + "  }\r\n" +
			 * "}</rarocDetailsResponseDetails></RESPONSE></CBS_RESPONSE>"; //
			 * System.out.println(res); log.info("groupRealisedDetails : outputXml===>> " +
			 * outputXml);
			 */
			msg = setT24CallData.setRarocGroupdetails(iformRefrence,
					formObject.getObjGeneralData().getM_strProcessInstanceId(), responseXML, inputXML, requestDateTime);
			log.info("groupRealisedDetails msg ...." + msg);
		} catch (Exception e) {
			log.error("Exception : ", e);
		}
		// }
		return msg;
	}

	// added suraj
	public String facilityRealisedDetails(IFormReference iformrefrence) {
		String inputXML = "";
		String msg = "";
		String realizedType = "facility";

		log.info("facilityRealisedDetails : INSIDE ");

		try {
			String processName = iformrefrence.getProcessName();
			String wiName = iformrefrence.getObjGeneralData().getM_strProcessInstanceId();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_SQL);
			Date currentDate = new Date();
			String requestDate = simpleDateFormat.format(currentDate);
			// requestDate = "2023-12-31";
			SimpleDateFormat DateTimeFormat = new SimpleDateFormat(Constants.REQ_DATE_TIME_FORMAT);
			String requestDateTime = DateTimeFormat.format(currentDate);
			String customerID = iformrefrence.getValue("LEAD_REF_NO").toString();

			log.info("customerID : " + customerID);

			String requestParameter = "<Param>" + "<Key>realizeType</Key><Value>" + realizedType + "</Value>"
					+ "</Param>" + "<Param>" + "<Key>date</Key><Value>" + requestDate + "</Value>" + "</Param>"
					+ "<Param>" + "<Key>content</Key><Value>" + customerID + "</Value>" + "</Param>";

			inputXML = inputXML(iformrefrence, requestParameter);
			T24_Integration t24integration = new T24_Integration(processName, wiName);
			String responseXML = t24integration.executeIntegrationCall(inputXML);
			log.info("responseXML : outputXML===>> " + responseXML);
//			String outputXml = "<CBS_RESPONSE><STATUS_CODE>100</STATUS_CODE><STATUS_DESC>Success</STATUS_DESC><STATUS_REMARKS></STATUS_REMARKS><RESPONSE><rarocDetailsResponseDetails>{\r\n"
//					+ "  \"exceptionDetails\": {\r\n" + "    \"successIndicator\": \"Success\",\r\n"
//					+ "    \"messages\": []\r\n" + "  },\r\n" + "  \"body\": {\r\n"
//					+ "    \"rarocDetailsResponse\": [\r\n" + "      {\r\n"
//					+ "        \"operatingExpense\": \"1714.5\",\r\n" + "        \"product\": \"FX\",\r\n"
//					+ "        \"fundingCost\": \"0.0\",\r\n" + "        \"assetClass\": \"Corporate/GRE\",\r\n"
//					+ "        \"industry\": \"MFG Beverages & tobacco\",\r\n"
//					+ "        \"customerSegment\": \"Corporate Small\",\r\n"
//					+ "        \"version\": \"1709638204691\",\r\n" + "        \"interestIncome\": \"0.0\",\r\n"
//					+ "        \"ecl\": \"0.0\",\r\n" + "        \"feeIncome\": \"0.0\",\r\n"
//					+ "        \"realizedRaroc\": \"1.1249567567567569\",\r\n" + "        \"lienBenefit\": \"0.0\",\r\n"
//					+ "        \"grossRevenue\": \"4500.0\",\r\n" + "        \"facilityId\": \"785\",\r\n"
//					+ "        \"riskAdjustedReturn\": \"4162.34\",\r\n" + "        \"legalEntity\": \"Almasraf\",\r\n"
//					+ "        \"facilityType\": \"OTC Products\",\r\n" + "        \"rmName\": \"Ajay Moolay\",\r\n"
//					+ "        \"customerId\": \"202\",\r\n" + "        \"facilityCurrency\": \"\",\r\n"
//					+ "        \"reportingDate\": \"2023-12-31\",\r\n" + "        \"totalCapital\": \"3700.0\",\r\n"
//					+ "        \"disbursedAmount\": \"70000.0\"\r\n" + "      }\r\n" + "    ]\r\n" + "  }\r\n"
//					+ "}</rarocDetailsResponseDetails></RESPONSE></CBS_RESPONSE>";
//			// System.out.println(res);
//			log.info("facilityRealisedDetails : outputXml===>> " + outputXml);
//
			msg = setT24CallData.setRarocfacilitydetails(iformrefrence,
					formObject.getObjGeneralData().getM_strProcessInstanceId(), responseXML, inputXML, requestDateTime);
			log.info("facilityRealisedDetails msg ...." + msg);
		} catch (Exception e) {
			log.error("Exception : ", e);
		}
		// }
		return msg;
	}

	// added suraj
	public String execCustomerProjected(IFormReference iformrefrence) {
		String inputXML = "";
		String msg = "";
		log.info("execCustomerProjected : INSIDE ");

		String processName = iformrefrence.getProcessName();
		log.info("processName : INSIDE " + processName);
		String CallName = "execCustomerProjected";
		String username = iformrefrence.getUserName();
		String wiName = iformrefrence.getObjGeneralData().getM_strProcessInstanceId();
		String workstepName = iformrefrence.getActivityName();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_SQL);
		Date currentDate = new Date();
		String requestDate = simpleDateFormat.format(currentDate);

		SimpleDateFormat DateTimeFormat = new SimpleDateFormat(Constants.REQ_DATE_TIME_FORMAT);
		String requestDateTime = DateTimeFormat.format(currentDate);
		String code = iformrefrence.getValue("LEAD_REF_NO").toString();
		String name = iformrefrence.getValue("CUSTOMER_NAME").toString();
		String country = iformrefrence.getValue("Q_NG_RAROC_CUSTOMER_customer_country_Proposed").toString();
		// String country = workstepName.equalsIgnoreCase("BUSINESS") ?
		// iformrefrence.getValue("Q_NG_RAROC_CUSTOMER_customer_country_Proposed").toString():
		// iformrefrence.getValue("Q_NG_RAROC_CUSTOMER_customer_country_RECOMMENDED").toString();
		String internalRatingCust = iformrefrence.getValue("Q_NG_RAROC_CUSTOMER_internal_rating_Proposed").toString();
		String extRating = iformrefrence.getValue("Q_NG_RAROC_CUSTOMER_external_rating_Proposed").toString();
		String industrySector = iformrefrence.getValue("Q_NG_RAROC_CUSTOMER_Industry_Segement_Proposed").toString();
		String salesTurnover = iformrefrence.getValue("Q_NG_RAROC_CUSTOMER_sales_turnover_Proposed").toString();
		String segment = iformrefrence.getValue("Q_NG_RAROC_CUSTOMER_Business_segment_Proposed").toString();
		String groupId = iformrefrence.getValue("Q_NG_RAROC_CUSTOMER_DETAILS_Group_ID").toString();
		String groupName = iformrefrence.getValue("Q_NG_RAROC_CUSTOMER_DETAILS_Group_Name").toString();
		String assessmentDate = requestDate;
		// start changes by mohit 03-07-2024 for CUSTOMER: Missing Field region
		String sQueryRegion = "SELECT EST_PLACE FROM " + CA_CUSTOMER_DETAILS
				+ " WHERE  wi_name =(select ca_wi_name from ALM_RAROC_EXT_TABLE WHERE wi_name = '" + wiName + "')";
		/*
		 * List<List<String>> sOutputRegion = formObject.getDataFromDB(sQueryRegion);
		 * log.info("sOutputRegion...:" + sOutputRegion); String region =
		 * sOutputRegion.get(0).get(0); log.info("region...:" + region);
		 */
		String region = "";
		String outputXMLregion = getDataFromDBWithColumns(sQueryRegion, "EST_PLACE");
		XMLParser xmlParserregion = new XMLParser(outputXMLregion);
		log.info("xmlParser...." + xmlParserregion);
		int totalRetrievedregion = Integer.parseInt(xmlParserregion.getValueOf(Constants.TOTAL_RETRIEVED));
		if (totalRetrievedregion == 1) {
			region = xmlParserregion.getValueOf("EST_PLACE");
		}
		// end changes by mohit 03-07-2024 for CUSTOMER: Missing Field region

		// log.info("facilityId : INSIDE "+facilityId);
		log.info("requestDate : " + requestDate);
		String column = "COMMITMENT_NO,TREASURY_PRODUCT,FACILITY_TYPE,FACILITY_NAME,Repayment_Type_Proposed,Repayment_Frequency_Proposed,"
				+ "Tenor_Proposed,Moratorium_Period_Proposed,Currency_Proposed,Index_Proposed,Index_Rate_Proposed,Tenor_unit_Proposed,"
				+ "Margin_Commision_Proposed,Cash_Margin_Proposed,Credit_Limit_Proposed,EXPECTED_UTILISATION,SENIOR_SUBORDINATE,"
				+ "COMMITTED_UNCOMMITTED,Facility_Amount_Proposed,Expected_year_Return_Proposed,Marketing_Risk_Capital_Proposed,"
				+ "REPRICING_FERQUENCY_PROPOSED,FTP_RATE_PROPOSED,COLLATERAL_NAME_PROPOSED,COLLATERAL_TYPE_PROPOSED,COLLATERAL_AMOUNT_PROPOSED,"
				+ "COLLATERAL_CURRENCY_PROPOSED,COLLATERAL_LEAN_AMOUNT_PROPOSED,COLLATERAL_LEAN_INTEREST_PROPOSED,COLLATERAL_LEAN_TENURE_PROPOSED,"
				+ "INCOME_NAME,INCOME_TYPE,INCOME_PERCENTAGE,INCOME_ABSOLUTE,"
				+ "INDEX_KEY_PROPOSED,INDEX_TENOR_PROPOSED,INDEX_TENOR_UNIT_PROPOSED,"
				+ "Minimum_Proposed,Allocation_Percentage_Proposed,Utilization_Proposed,facility_id,VALUE_PROPOSED,Ftp_Override_Proposed";// bp05
																																			// //nc
		String query = "SELECT " + column + " FROM NG_RAROC_FACILITY_DETAILS A WHERE A.WI_NAME='" + wiName
				+ "' and Facility_Amount_proposed<>'0' AND Facility_Amount_proposed <>'' AND Facility_Amount_proposed IS NOT NULL"
				+ " and MAIN_SUB_LIMIT='Main Limit'";// prod_11122024 //prod_11022025
		log.info("query>>>>>> : " + query);
		String outputXML = getDataFromDBWithColumns(query, column);
		XMLParser xmlParser = new XMLParser(outputXML);
		log.info("xmlParser" + xmlParser);
		int totalRetrieved = Integer.parseInt(xmlParser.getValueOf(Constants.TOTAL_RETRIEVED));

		List<String> name_value1 = new ArrayList<>();
		List<String> otc_flag_value1 = new ArrayList<>();
		List<String> type_value1 = new ArrayList<>();
		List<String> product_type_value1 = new ArrayList<>();
		List<String> repayment_mode_value1 = new ArrayList<>();
		List<String> repayment_frequency_value1 = new ArrayList<>();
		List<String> tenor_value1 = new ArrayList<>();
		List<String> moratorium_period_value1 = new ArrayList<>();
		List<String> currency_value1 = new ArrayList<>();
		List<String> index_value1 = new ArrayList<>();
		List<String> index_tenor_value1 = new ArrayList<>();
		List<String> tenor_unit_value1 = new ArrayList<>();
		List<String> margin_value1 = new ArrayList<>();
		List<String> valueProp_value1 = new ArrayList<>();
		List<String> commission_rate_value1 = new ArrayList<>();
		List<String> limit_value1 = new ArrayList<>();
		List<String> utilisation_value1 = new ArrayList<>();
		List<String> facility_position_value1 = new ArrayList<>();
		List<String> commitment_type_value1 = new ArrayList<>();
		List<String> csv_path_value1 = new ArrayList<>();
		List<String> notional_amount_value1 = new ArrayList<>();
		List<String> expected_1_year_return_type_value1 = new ArrayList<>();
		List<String> market_risk_capital_value1 = new ArrayList<>();
		List<String> ftp_override_value1 = new ArrayList<>();
		List<String> facilityId_value1 = new ArrayList<>();
		List<String> repricing_frequency_value1 = new ArrayList<>();
		List<String> ftp_rate_value1 = new ArrayList<>();
		List<String> interest_floor_value1 = new ArrayList<>();
		List<String> allocation_Perc_Prop_value1 = new ArrayList<>();

		List<String> collateral_Name1 = new ArrayList<>();
		List<String> collateral_No1 = new ArrayList<>();
		List<String> collateral_Type1 = new ArrayList<>();
		List<String> collateral_Amount1 = new ArrayList<>();
		List<String> collateral_Currency1 = new ArrayList<>();
		List<String> newCollateralType1 = new ArrayList<>();
		List<String> collateral_Lean1 = new ArrayList<>();
		List<String> collateral_LeanInterest1 = new ArrayList<>();
		List<String> collateral_LeanTenor1 = new ArrayList<>();
		List<String> crmCollateral_cash_margin1 = new ArrayList<>();
		List<String> crmCollateral_haircut1 = new ArrayList<>();
		List<String> income_Name1 = new ArrayList<>();
		List<String> income_Type1 = new ArrayList<>();
		List<String> income_Percentage1 = new ArrayList<>();
		List<String> income_Absolute1 = new ArrayList<>();

		/*
		 * //start changes by mohit 11-06-2024 for other section List<String>
		 * f_average_balance = new ArrayList<>(); List<String> f_forex_income = new
		 * ArrayList<>(); List<String> f_commission_export = new ArrayList<>();
		 * List<String> f_other_income = new ArrayList<>(); List<String> f_fixed_deposit
		 * = new ArrayList<>(); //end changes by mohit 11-06-2024 for other section
		 */
		String commitmentNo = ""; // also used for COLLATERAL_FACILITY_MAP
		String facilityId = "";
		String TreasuryProposed = "";
		String UtilizationPerc = "";
		String facilitytype = "";
		String facilityName = ""; // also used for COLLATERAL_FACILITY_MAP
		String repaymentTyprProp = "";
		String repFreqProp = "";
		String tenureProposed = "";
		String moratoriumProp = "";
		String currencyProp = "";
		String indexProp = "";
		String indexRateProp = "";
		String tenorUnitProp = "";
		String margin = "";
		String valueProp = "";
		String marginCommisionProp = "";
		String creditLimitProp = "";
		String expUtilisationProp = "";
		String seniorSubProp = "";
		String committedUncommited = "";
		String facilityAmntProp = "";
		String expReturnProp = "";
		String marketingRiskCapProp = "";
		String repricingFreqProp = "";
		String ftpOverride = "";
		String ftpRate = "";
		String interestFloor = "";

		String collateralName = "";
		String collateralNo = "";
		String commitment = "";
		String collateralType = "";
		String collateralAmount = "";
		String collateralCurrency = "";
		String newCollateralType = "";
		String collateralLean = "";
		String collateralLeanInterest = "";
		String collateralLeanTenor = "";
		String crmCollateral_cash_margin = "";
		String crmCollateral_haircut = "";
		String incomeName = "";
		String incomeType = "";
		String incomePercentage = "";
		String incomeAbsolute = "";

		// start changes by mohit 11-06-2024 for other section
		String fAverageBalance = "";
		String fForexIncome = "";
		String fCommissionExport = "";
		String fOtherIncome = "";
		String fFixedDeposit = "";
		// Boolean bTreasuryProposed= null; //value should be boolean true/ false
		// List<Boolean> bOtc_flag_value1 = new ArrayList<>();
		String sHardCodeValue = "";// mohitsHardCodeValue
		String allocationPercProp = ""; // 03-07-2024 by mohit for COLLATERAL_FACILITY_MAP
		String fCollateralNumberProposed = "";
		// end changes by mohit 11-06-2024 for other section
		fAverageBalance = iformrefrence.getValue("F_AVERAGE_BALANCE").toString();
		fForexIncome = iformrefrence.getValue("F_FOREX_INCOME").toString();
		fCommissionExport = iformrefrence.getValue("F_COMMISSION_EXPORT").toString();
		fOtherIncome = iformrefrence.getValue("F_OTHER_INCOME").toString();
		fFixedDeposit = iformrefrence.getValue("F_FIXED_DEPOSIT").toString();
		if (totalRetrieved != 0) {
			for (int i = 0; i < totalRetrieved; i++) {
				String row = xmlParser.getNextValueOf(Constants.ROW);
				XMLParser rowParser = new XMLParser(row);
				log.info("inside totalRetrieve>>>>>>");
				commitmentNo = rowParser.getValueOf("COMMITMENT_NO");
				facilityId = rowParser.getValueOf("facility_id");
				TreasuryProposed = rowParser.getValueOf("TREASURY_PRODUCT");// Y,N
				UtilizationPerc = rowParser.getValueOf("Utilization_Proposed");
				log.info("UtilizationPerc...." + UtilizationPerc + "+++");
				facilitytype = rowParser.getValueOf("FACILITY_TYPE");
				facilityName = rowParser.getValueOf("FACILITY_NAME");
				repaymentTyprProp = rowParser.getValueOf("REPAYMENT_TYPE_PROPOSED");// mapping is interchanged on
																					// //bp05//d
																					// form hence using this
				repFreqProp = rowParser.getValueOf("REPAYMENT_FREQUENCY_PROPOSED");// bp05 //d
				tenureProposed = rowParser.getValueOf("Tenor_Proposed");
				log.info("tenureProposed..." + tenureProposed + "+++");
				moratoriumProp = rowParser.getValueOf("Moratorium_Period_Proposed");
				currencyProp = rowParser.getValueOf("Currency_Proposed");
				// indexProp=rowParser.getValueOf("Index_Proposed");
				// indexRateProp=rowParser.getValueOf("Index_Rate_Proposed");
				// tenorUnitProp=rowParser.getValueOf("Tenor_unit_Proposed");
				indexProp = rowParser.getValueOf("INDEX_KEY_PROPOSED");
				indexRateProp = rowParser.getValueOf("INDEX_TENOR_PROPOSED");
				tenorUnitProp = rowParser.getValueOf("INDEX_TENOR_UNIT_PROPOSED");

				margin = rowParser.getValueOf("Cash_Margin_Proposed");
				valueProp = rowParser.getValueOf("VALUE_PROPOSED");
				marginCommisionProp = rowParser.getValueOf("Margin_Commision_Proposed");
				// creditLimitProp=rowParser.getValueOf("Credit_Limit_Proposed");by mohit
				// 01-07-2024 Limit is FAcility Amt ;Limit is not credit Limit
				creditLimitProp = rowParser.getValueOf("Facility_Amount_Proposed");
				expUtilisationProp = rowParser.getValueOf("EXPECTED_UTILISATION");
				seniorSubProp = rowParser.getValueOf("SENIOR_SUBORDINATE");
				committedUncommited = rowParser.getValueOf("COMMITTED_UNCOMMITTED");
				facilityAmntProp = rowParser.getValueOf("Facility_Amount_Proposed");
				expReturnProp = rowParser.getValueOf("Expected_year_Return_Proposed");
				marketingRiskCapProp = rowParser.getValueOf("Marketing_Risk_Capital_Proposed");
				repricingFreqProp = rowParser.getValueOf("REPRICING_FERQUENCY_PROPOSED");
				ftpOverride = rowParser.getValueOf("Ftp_Override_Proposed");// to add field on form
				ftpRate = rowParser.getValueOf("FTP_RATE_PROPOSED");
				interestFloor = rowParser.getValueOf("Minimum_Proposed");// 03-07-2024 by mohit for FACILITY: Missing
																			// Field interest_floor

				// allocationPercProp =rowParser.getValueOf("Allocation_Percentage_Proposed");//
				// 03-07-2024 by mohit for COLLATERAL_FACILITY_MAP

				// collateralName =rowParser.getValueOf("COLLATERAL_NAME_PROPOSED");
				collateralNo = rowParser.getValueOf("COLLATERAL_NUMBER_PROPOSED");
				// collateralType =rowParser.getValueOf("COLLATERAL_TYPE_PROPOSED");
				// collateralAmount =rowParser.getValueOf("COLLATERAL_AMOUNT_PROPOSED");
				// collateralCurrency =rowParser.getValueOf("COLLATERAL_CURRENCY_PROPOSED");
				collateralLean = rowParser.getValueOf("COLLATERAL_LEAN_AMOUNT_PROPOSED");
				collateralLeanInterest = rowParser.getValueOf("COLLATERAL_LEAN_INTEREST_PROPOSED");
				collateralLeanTenor = rowParser.getValueOf("COLLATERAL_LEAN_TENURE_PROPOSED");
				crmCollateral_cash_margin = rowParser.getValueOf(""); // mvalue
				crmCollateral_haircut = rowParser.getValueOf("");
				incomeName = rowParser.getValueOf("INCOME_NAME");
				incomeType = rowParser.getValueOf("INCOME_TYPE");
				incomePercentage = rowParser.getValueOf("INCOME_PERCENTAGE");
				incomeAbsolute = rowParser.getValueOf("INCOME_ABSOLUTE");

				// start changes by mohit 11-06-2024 for other section
				/*
				 * fAverageBalance =rowParser.getValueOf("AVERAGE_BALANCE"); fForexIncome =
				 * rowParser.getValueOf("FOREX_INCOME"); fCommissionExport
				 * =rowParser.getValueOf("COMMISSION_EXPORT"); fOtherIncome
				 * =rowParser.getValueOf("OTHER_INCOME"); fFixedDeposit
				 * =rowParser.getValueOf("FIXED_DEPOSIT");
				 */

				if (TreasuryProposed.equalsIgnoreCase("YES") || TreasuryProposed.equalsIgnoreCase("Y")) {
					// bTreasuryProposed = true;
					TreasuryProposed = "True";
				} else {
					// bTreasuryProposed =false;
					TreasuryProposed = "False";
				}
				// end changes by mohit 11-06-2024 for other section

				// start edit by mohit 16-08-2024
				log.info("before yes and non-funded..type_value1.." + type_value1);
				if ((TreasuryProposed.equalsIgnoreCase("YES") || TreasuryProposed.equalsIgnoreCase("Y")
						|| TreasuryProposed.equalsIgnoreCase("True")) && facilitytype.equalsIgnoreCase("Non-Funded")) {
					log.info("Inside yes and non-funded prop..");
					facilitytype = "OTC-Products";
					// start edit by mohit 21-08-2024
					repaymentTyprProp = null;
					repFreqProp = null;
					moratoriumProp = null;
					indexProp = null;
					indexRateProp = null;
					tenorUnitProp = null;
					marginCommisionProp = null;
					valueProp = null;
					creditLimitProp = null;
					UtilizationPerc = null;
					seniorSubProp = null;
					committedUncommited = null;
					marketingRiskCapProp = null;
					repricingFreqProp = null;
					interestFloor = null;
					// end edit by mohit 21-08-2024
				}
				// end edit by mohit 16-08-2024

				name_value1.add(commitmentNo);
				facilityId_value1.add(facilityId);
				otc_flag_value1.add(TreasuryProposed);
				// bOtc_flag_value1.add(bTreasuryProposed);// value should be String "true"/
				// "false"
				type_value1.add(facilitytype);
				product_type_value1.add(facilityName);
				repayment_mode_value1.add(repaymentTyprProp);
				repayment_frequency_value1.add(repFreqProp);
				tenor_value1.add(tenureProposed);
				moratorium_period_value1.add(moratoriumProp);
				currency_value1.add(currencyProp);
				index_value1.add(indexProp);
				index_tenor_value1.add(indexRateProp);
				tenor_unit_value1.add(tenorUnitProp);
				margin_value1.add(margin);
				valueProp_value1.add(valueProp);
				commission_rate_value1.add(marginCommisionProp);
				limit_value1.add(creditLimitProp);
				// utilisation_value1.add(expUtilisationProp);
				utilisation_value1.add(UtilizationPerc);
				facility_position_value1.add(seniorSubProp);
				commitment_type_value1.add(committedUncommited);
				csv_path_value1.add(""); // value not provided
				notional_amount_value1.add(facilityAmntProp);
				expected_1_year_return_type_value1.add(expReturnProp);
				market_risk_capital_value1.add(marketingRiskCapProp);
				ftp_override_value1.add(ftpOverride);
				repricing_frequency_value1.add(repricingFreqProp);
				ftp_rate_value1.add(ftpRate);
				log.info("inside ftp_rate_value1....");
				interest_floor_value1.add(interestFloor);// 03-07-2024 by mohit for FACILITY: Missing Field
															// interest_floor
				// allocation_Perc_Prop_value1.add(allocationPercProp);

				// collateral_Name1.add(collateralName);
				// collateral_No1.add(collateralNo);
				// collateral_Type1.add(collateralType);
				// collateral_Amount1.add(collateralAmount);
				// collateral_Currency1.add(collateralCurrency);
				collateral_Lean1.add(collateralLean);
				collateral_LeanInterest1.add(collateralLeanInterest);
				collateral_LeanTenor1.add(collateralLeanTenor);
				crmCollateral_haircut1.add(crmCollateral_haircut);
				crmCollateral_cash_margin1.add(crmCollateral_cash_margin);
				income_Name1.add(incomeName);
				income_Type1.add(incomeType);
				income_Percentage1.add(incomePercentage);
				income_Absolute1.add(incomeAbsolute);

				/*
				 * //start changes by mohit 11-06-2024 for other section
				 * f_average_balance.add(fAverageBalance); f_forex_income.add(fForexIncome);
				 * f_commission_export.add(fCommissionExport); f_other_income.add(fOtherIncome);
				 * f_fixed_deposit.add(fFixedDeposit); //end changes by mohit 11-06-2024 for
				 * other section
				 */

			}
		}
		// start edit by mohit 11-02-2025 //prod_11022025 only Main Limits and those sub limits which will be modified will be used for RAROC calculation
		log.info("13022025 #######");
		String columnsb = "COMMITMENT_NO,TREASURY_PRODUCT,FACILITY_TYPE,FACILITY_NAME,Repayment_Type_Proposed,Repayment_Frequency_Proposed,"
				+ "Tenor_Proposed,Moratorium_Period_Proposed,Currency_Proposed,Index_Proposed,Index_Rate_Proposed,Tenor_unit_Proposed,"
				+ "Margin_Commision_Proposed,Cash_Margin_Proposed,Credit_Limit_Proposed,EXPECTED_UTILISATION,SENIOR_SUBORDINATE,"
				+ "COMMITTED_UNCOMMITTED,Facility_Amount_Proposed,Expected_year_Return_Proposed,Marketing_Risk_Capital_Proposed,"
				+ "REPRICING_FERQUENCY_PROPOSED,FTP_RATE_PROPOSED,COLLATERAL_NAME_PROPOSED,COLLATERAL_TYPE_PROPOSED,COLLATERAL_AMOUNT_PROPOSED,"
				+ "COLLATERAL_CURRENCY_PROPOSED,COLLATERAL_LEAN_AMOUNT_PROPOSED,COLLATERAL_LEAN_INTEREST_PROPOSED,COLLATERAL_LEAN_TENURE_PROPOSED,"
				+ "INCOME_NAME,INCOME_TYPE,INCOME_PERCENTAGE,INCOME_ABSOLUTE,"
				+ "INDEX_KEY_PROPOSED,INDEX_TENOR_PROPOSED,INDEX_TENOR_UNIT_PROPOSED,"
				+ "Minimum_Proposed,Allocation_Percentage_Proposed,Utilization_Proposed,facility_id,VALUE_PROPOSED,Ftp_Override_Proposed";// bp05
																																			// //nc
		String querysb = "SELECT " + columnsb + " FROM NG_RAROC_FACILITY_DETAILS A WHERE A.WI_NAME='" + wiName
				+ "' and Facility_Amount_proposed<>'0' "
				+ "AND Facility_Amount_proposed <>'' AND Facility_Amount_proposed IS NOT NULL"
				+ " and MAIN_SUB_LIMIT <> 'Main Limit' and (TREASURY_PRODUCT <>'' and TREASURY_PRODUCT IS NOT NULL)  and ( FACILITY_TYPE <>'' and FACILITY_TYPE IS NOT NULL )"
				+ "and ( FACILITY_NAME <>'' and  FACILITY_NAME IS NOT NULL ) and (Repayment_Type_Proposed <>'' and Repayment_Type_Proposed IS NOT NULL) and (Repayment_Frequency_Proposed <>'' and Repayment_Frequency_Proposed IS NOT NULL)"
				+ " and (Tenor_Proposed <>'' and Tenor_Proposed IS NOT NULL) and (Currency_Proposed <>'' and Currency_Proposed IS NOT NULL) "
				+ "and ((FACILITY_TYPE = 'Non-Funded') or (FACILITY_TYPE = 'Funded' AND Index_Proposed <>'' and Index_Proposed IS NOT NULL and Index_Rate_Proposed <>'' and Index_Rate_Proposed IS NOT NULL)) "//FOr non-funded Index_Proposed and Index_Rate_Proposed is ''
				+ " and (Tenor_unit_Proposed <>'' and Tenor_unit_Proposed IS NOT NULL) "
				+ "and (SENIOR_SUBORDINATE <>'' and SENIOR_SUBORDINATE IS NOT NULL) "
				+ "and (COMMITTED_UNCOMMITTED <>'' and COMMITTED_UNCOMMITTED IS NOT NULL) "
				+ " and ((TREASURY_PRODUCT = 'No') or (TREASURY_PRODUCT = 'Yes' AND Expected_year_Return_Proposed <>'' and Expected_year_Return_Proposed IS NOT NULL and Marketing_Risk_Capital_Proposed <>'' and Marketing_Risk_Capital_Proposed IS NOT NULL)) "
				+ " and (Utilization_Proposed <>'' and Utilization_Proposed IS NOT NULL)  and (FACILITY_NAME <>'' and FACILITY_NAME IS NOT NULL) "
				+ "and (VALUE_PROPOSED <>'' and VALUE_PROPOSED IS NOT NULL) ";// prod_11122024 //prod_11022025
		log.info("querysb>>>>>> : " + querysb);
		String outputXMLsb = getDataFromDBWithColumns(querysb, columnsb);
		XMLParser xmlParsersb = new XMLParser(outputXMLsb);
		log.info("xmlParsersb" + xmlParsersb);
		int totalRetrievedsb = Integer.parseInt(xmlParsersb.getValueOf(Constants.TOTAL_RETRIEVED));
		if (totalRetrievedsb != 0) {
			for (int i = 0; i < totalRetrievedsb; i++) {
				String row = xmlParsersb.getNextValueOf(Constants.ROW);
				XMLParser rowParser = new XMLParser(row);
				log.info("inside totalRetrieve sb>>>>>>");
				commitmentNo = rowParser.getValueOf("COMMITMENT_NO");
				facilityId = rowParser.getValueOf("facility_id");
				TreasuryProposed = rowParser.getValueOf("TREASURY_PRODUCT");// Y,N
				UtilizationPerc = rowParser.getValueOf("Utilization_Proposed");
				log.info("UtilizationPerc sb...." + UtilizationPerc + "+++");
				facilitytype = rowParser.getValueOf("FACILITY_TYPE");
				facilityName = rowParser.getValueOf("FACILITY_NAME");
				repaymentTyprProp = rowParser.getValueOf("REPAYMENT_TYPE_PROPOSED");// mapping is interchanged on
																					// //bp05//d
																					// form hence using this
				repFreqProp = rowParser.getValueOf("REPAYMENT_FREQUENCY_PROPOSED");// bp05 //d
				tenureProposed = rowParser.getValueOf("Tenor_Proposed");
				log.info("tenureProposed sb..." + tenureProposed + "+++");
				moratoriumProp = rowParser.getValueOf("Moratorium_Period_Proposed");
				currencyProp = rowParser.getValueOf("Currency_Proposed");
				indexProp = rowParser.getValueOf("INDEX_KEY_PROPOSED");
				indexRateProp = rowParser.getValueOf("INDEX_TENOR_PROPOSED");
				tenorUnitProp = rowParser.getValueOf("INDEX_TENOR_UNIT_PROPOSED");

				margin = rowParser.getValueOf("Cash_Margin_Proposed");
				valueProp = rowParser.getValueOf("VALUE_PROPOSED");
				marginCommisionProp = rowParser.getValueOf("Margin_Commision_Proposed");
				// creditLimitProp=rowParser.getValueOf("Credit_Limit_Proposed");by mohit
				// 01-07-2024 Limit is FAcility Amt ;Limit is not credit Limit
				creditLimitProp = rowParser.getValueOf("Facility_Amount_Proposed");
				expUtilisationProp = rowParser.getValueOf("EXPECTED_UTILISATION");
				seniorSubProp = rowParser.getValueOf("SENIOR_SUBORDINATE");
				committedUncommited = rowParser.getValueOf("COMMITTED_UNCOMMITTED");
				facilityAmntProp = rowParser.getValueOf("Facility_Amount_Proposed");
				expReturnProp = rowParser.getValueOf("Expected_year_Return_Proposed");
				marketingRiskCapProp = rowParser.getValueOf("Marketing_Risk_Capital_Proposed");
				repricingFreqProp = rowParser.getValueOf("REPRICING_FERQUENCY_PROPOSED");
				ftpOverride = rowParser.getValueOf("Ftp_Override_Proposed");// to add field on form
				ftpRate = rowParser.getValueOf("FTP_RATE_PROPOSED");
				interestFloor = rowParser.getValueOf("Minimum_Proposed");// 03-07-2024 by mohit for FACILITY: Missing
																			// Field interest_floor
				collateralNo = rowParser.getValueOf("COLLATERAL_NUMBER_PROPOSED");
				collateralLean = rowParser.getValueOf("COLLATERAL_LEAN_AMOUNT_PROPOSED");
				collateralLeanInterest = rowParser.getValueOf("COLLATERAL_LEAN_INTEREST_PROPOSED");
				collateralLeanTenor = rowParser.getValueOf("COLLATERAL_LEAN_TENURE_PROPOSED");
				crmCollateral_cash_margin = rowParser.getValueOf(""); // mvalue
				crmCollateral_haircut = rowParser.getValueOf("");
				incomeName = rowParser.getValueOf("INCOME_NAME");
				incomeType = rowParser.getValueOf("INCOME_TYPE");
				incomePercentage = rowParser.getValueOf("INCOME_PERCENTAGE");
				incomeAbsolute = rowParser.getValueOf("INCOME_ABSOLUTE");

				// start changes by mohit 11-06-2024 for other section

				if (TreasuryProposed.equalsIgnoreCase("YES") || TreasuryProposed.equalsIgnoreCase("Y")) {
					// bTreasuryProposed = true;
					TreasuryProposed = "True";
				} else {
					// bTreasuryProposed =false;
					TreasuryProposed = "False";
				}
				// end changes by mohit 11-06-2024 for other section

				// start edit by mohit 16-08-2024
				log.info("before yes and non-funded..type_value1.." + type_value1);
				if ((TreasuryProposed.equalsIgnoreCase("YES") || TreasuryProposed.equalsIgnoreCase("Y")
						|| TreasuryProposed.equalsIgnoreCase("True")) && facilitytype.equalsIgnoreCase("Non-Funded")) {
					log.info("Inside yes and non-funded prop..");
					facilitytype = "OTC-Products";
					// start edit by mohit 21-08-2024
					repaymentTyprProp = null;
					repFreqProp = null;
					moratoriumProp = null;
					indexProp = null;
					indexRateProp = null;
					tenorUnitProp = null;
					marginCommisionProp = null;
					valueProp = null;
					creditLimitProp = null;
					UtilizationPerc = null;
					seniorSubProp = null;
					committedUncommited = null;
					marketingRiskCapProp = null;
					repricingFreqProp = null;
					interestFloor = null;
					// end edit by mohit 21-08-2024
				}
				// end edit by mohit 16-08-2024

				name_value1.add(commitmentNo);
				facilityId_value1.add(facilityId);
				otc_flag_value1.add(TreasuryProposed);
				type_value1.add(facilitytype);
				product_type_value1.add(facilityName);
				repayment_mode_value1.add(repaymentTyprProp);
				repayment_frequency_value1.add(repFreqProp);
				tenor_value1.add(tenureProposed);
				moratorium_period_value1.add(moratoriumProp);
				currency_value1.add(currencyProp);
				index_value1.add(indexProp);
				index_tenor_value1.add(indexRateProp);
				tenor_unit_value1.add(tenorUnitProp);
				margin_value1.add(margin);
				valueProp_value1.add(valueProp);
				commission_rate_value1.add(marginCommisionProp);
				limit_value1.add(creditLimitProp);
				// utilisation_value1.add(expUtilisationProp);
				utilisation_value1.add(UtilizationPerc);
				facility_position_value1.add(seniorSubProp);
				commitment_type_value1.add(committedUncommited);
				csv_path_value1.add(""); // value not provided
				notional_amount_value1.add(facilityAmntProp);
				expected_1_year_return_type_value1.add(expReturnProp);
				market_risk_capital_value1.add(marketingRiskCapProp);
				ftp_override_value1.add(ftpOverride);
				repricing_frequency_value1.add(repricingFreqProp);
				ftp_rate_value1.add(ftpRate);
				log.info("inside ftp_rate_value1 sb....");
				interest_floor_value1.add(interestFloor);// 03-07-2024 by mohit for FACILITY: Missing Field
															// interest_floor
				collateral_Lean1.add(collateralLean);
				collateral_LeanInterest1.add(collateralLeanInterest);
				collateral_LeanTenor1.add(collateralLeanTenor);
				crmCollateral_haircut1.add(crmCollateral_haircut);
				crmCollateral_cash_margin1.add(crmCollateral_cash_margin);
				income_Name1.add(incomeName);
				income_Type1.add(incomeType);
				income_Percentage1.add(incomePercentage);
				income_Absolute1.add(incomeAbsolute);

			}
		}

		// end edit by mohit 11-02-2025 //prod_11022025 only Main Limits and those sublimits which will be modified will be used for RAROC calculation

		// start edit by mohit 16-07-2024 collateral_facility_map integration from table
		List<String> commitment_value1 = new ArrayList<>();
		String columnCFM = "COLLATERAL_NUMBER_PROP,COMMITMENT_NO,COLLATERAL_ALLOCATION_PERCENTAGE_PROP";
		String queryCFM = "SELECT " + columnCFM + " FROM NG_RAROC_COLLATERAL_FACILITY_MAP WHERE WI_NAME='" + wiName
				+ "'";
		log.info("queryCFM>>>>>> : " + queryCFM);
		String outputXMLCFM = getDataFromDBWithColumns(queryCFM, columnCFM);
		XMLParser xmlParserCFM = new XMLParser(outputXMLCFM);
		log.info("xmlParserCFM" + xmlParserCFM);
		int totalRetrievedCFM = Integer.parseInt(xmlParserCFM.getValueOf(Constants.TOTAL_RETRIEVED));
		log.info("totalRetrievedCFM..." + totalRetrievedCFM);
		if (totalRetrievedCFM != 0) {
			for (int i = 0; i < totalRetrievedCFM; i++) {
				log.info("i====" + i);
				String rowCFM = xmlParserCFM.getNextValueOf(Constants.ROW);
				log.info("rowCFM####" + rowCFM);
				XMLParser rowParserCFM = new XMLParser(rowCFM);

				log.info("before assign..." + rowParserCFM.getValueOf("COLLATERAL_NUMBER_PROP"));
				collateralNo = rowParserCFM.getValueOf("COLLATERAL_NUMBER_PROP");
				log.info("collateralNo..." + collateralNo);
				collateral_No1.add(collateralNo);

				commitment = rowParserCFM.getValueOf("COMMITMENT_NO");
				log.info("commitment..." + commitment);
				commitment_value1.add(commitment);

				allocationPercProp = rowParserCFM.getValueOf("COLLATERAL_ALLOCATION_PERCENTAGE_PROP");
				log.info("allocationPercProp..." + allocationPercProp);
				allocation_Perc_Prop_value1.add(allocationPercProp);

			}
			log.info("collateral_No1..." + collateral_No1);
			log.info("commitment_value1..." + commitment_value1);
			log.info("allocation_Perc_Prop_value1..." + allocation_Perc_Prop_value1);
		}
		// end edit by mohit 16-07-2024 collateral_facility_map integration from table

		// start edit by mohit 18-07-2024 for crm collateral array mohitcrm
		// String columnCRM
		// ="COLLATERAL_NAME_PROP,COLLATERAL_TYPE_PROP,COLLATERAL_AMOUNT_PROP,COLLATERAL_CURRENCY_PROP,New_Collateral_Type";//new
		// col type 22-07-2024
		// String queryCRM = "SELECT "+columnCRM+" FROM NG_RAROC_COLLATERAL_FACILITY_MAP
		// WHERE WI_NAME='"+ wiName+ "'";
		// String columnCustCRM = " a.collateral_number collateral_number,a.SEC_NAME
		// COLLATERAL_TYPE ,a.collateral_amount AS collateral_amount
		// ,a.collateral_currency AS collateral_currency ";
		// String columnFacColCRM = " b.COLLATERAL_NUMBER_PROP AS
		// collateral_number,b.New_Collateral_Type AS
		// COLLATERAL_TYPE,b.COLLATERAL_AMOUNT_PROP AS
		// collateral_amount,b.COLLATERAL_CURRENCY_PROP AS collateral_currency ";
		String columnCustCRM = " a.collateral_number ,a.SEC_NAME  ,a.collateral_amount ,a.collateral_currency ";
		String columnFacColCRM = " b.COLLATERAL_NUMBER_PROP,b.New_Collateral_Type ,b.COLLATERAL_AMOUNT_PROP ,b.COLLATERAL_CURRENCY_PROP";
		String newColNameCRM = "COLLATERAL_NUMBER,COLLATERAL_TYPE,COLLATERAL_AMOUNT,COLLATERAL_CURRENCY";
		String columnCRM = columnCustCRM + columnFacColCRM;
		String queryCRM = " SELECT " + columnCustCRM + " FROM NG_RAROC_SECURITY_CUSTOMER a WHERE a.WI_NAME='" + wiName
				+ "'" + " UNION " + " SELECT " + columnFacColCRM
				+ " FROM NG_RAROC_COLLATERAL_FACILITY_MAP b WHERE b.WI_NAME='" + wiName + "'";
		log.info("queryCRM>>>>>> : " + queryCRM);

		String outputXMLCRM = getDataFromDBWithColumnsNew(queryCRM, columnCRM, newColNameCRM);
		XMLParser xmlParserCRM = new XMLParser(outputXMLCRM);
		log.info("xmlParserCRM...test ###mohit@@@@" + xmlParserCRM);
		int totalRetrievedCRM = Integer.parseInt(xmlParserCRM.getValueOf(Constants.TOTAL_RETRIEVED));
		log.info("totalRetrievedCRM..." + totalRetrievedCRM);
		if (totalRetrievedCRM != 0) {
			for (int i = 0; i < totalRetrievedCRM; i++) {
				log.info("i>>>>>" + i);
				String rowCRM = xmlParserCRM.getNextValueOf(Constants.ROW);
				log.info("rowCRM####" + rowCRM);
				XMLParser rowParserCRM = new XMLParser(rowCRM);

				log.info("before assign..." + rowParserCRM.getValueOf("COLLATERAL_NAME_PROP"));
				// collateralName=rowParserCRM.getValueOf("COLLATERAL_NAME_PROP");
				collateralName = rowParserCRM.getValueOf("COLLATERAL_NUMBER");
				log.info("COLLATERAL_NUMBER(collateral_number)..." + collateralName);
				collateral_Name1.add(collateralName);

				collateralType = rowParserCRM.getValueOf("COLLATERAL_TYPE");
				log.info("COLLATERAL_TYPE..." + collateralType);
				collateral_Type1.add(collateralType);

				collateralAmount = rowParserCRM.getValueOf("COLLATERAL_AMOUNT");
				log.info("COLLATERAL_AMOUNT..." + collateralAmount);
				collateral_Amount1.add(collateralAmount);

				collateralCurrency = rowParserCRM.getValueOf("COLLATERAL_CURRENCY");
				log.info("COLLATERAL_CURRENCY..." + collateralCurrency);
				collateral_Currency1.add(collateralCurrency);

				/*
				 * newCollateralType=rowParserCRM.getValueOf("New_Collateral_Type");
				 * log.info("New_Collateral_Type..."+newCollateralType);
				 * newCollateralType1.add(newCollateralType);
				 */
			}
			log.info("collateral_Name1..." + collateral_Name1);
			log.info("collateral_Type1..." + collateral_Type1);
			log.info("collateral_Amount1..." + collateral_Amount1);
			log.info("collateral_Currency1..." + collateral_Currency1);
			// log.info("newCollateralType1..."+newCollateralType);
		}
		// end edit by mohit 18-07-2024for crm collateral array mohitcrm

		JSONObject customerRequestObject = new JSONObject();
		JSONArray filesInfoArr = new JSONArray();

		// start edit by mohit on 21-08-2024
		String sFacilityId = "";
		Float sumPrincipalAmt = 0.0f;
		String sFinalSumPrincipalAmt = "";
		String sPaymentDate = "";
		String sTotalAmt = "";
		String principalAmtPattern = "@*";
		String sPaymentDatePattern = "@*";
		String sQueryReSchFacilityIds = "select distinct facility_id from NG_RAROC_REPAYMANT_DETAILS_API where wi_name='"
				+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "'";
		log.info("sQueryReSchFacilityIds..." + sQueryReSchFacilityIds);
		List<List<String>> sQueryReSchFacilityIdsOut = formObject.getDataFromDB(sQueryReSchFacilityIds);
		log.info("sQueryReSchFacilityIdsOut..." + sQueryReSchFacilityIdsOut);
		if (sQueryReSchFacilityIdsOut != null || !sQueryReSchFacilityIdsOut.isEmpty()) {
			for (List<String> faciltyIdrow : sQueryReSchFacilityIdsOut) {
				sumPrincipalAmt = 0.0f;
				principalAmtPattern = "@*";
				sPaymentDatePattern = "@*";
				sFacilityId = faciltyIdrow.get(0);
				String sQueryReSch = "select principal_amount from NG_RAROC_REPAYMANT_DETAILS_API where wi_name='"
						+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "' and facility_id='"
						+ sFacilityId + "'";
				log.info("sQueryReSch..." + sQueryReSch);
				List<List<String>> sQueryReSchOut = formObject.getDataFromDB(sQueryReSch);
				log.info("sQueryReSchOut..." + sQueryReSchOut);
				if (sQueryReSchOut != null || !sQueryReSchOut.isEmpty()) {
					for (List<String> principalAmtrow : sQueryReSchOut) {
						sumPrincipalAmt += Float.parseFloat(principalAmtrow.get(0));
						principalAmtPattern = principalAmtPattern + principalAmtrow + "*";
						log.info("sumPrincipalAmt.." + sumPrincipalAmt);
					}
					/*
					 * String sPaymentDate=principalAmtrow.get(1);
					 * sPaymentDatePattern=sPaymentDatePattern+sPaymentDate.substring(0, 10)+"*" ;
					 */

					principalAmtPattern = principalAmtPattern + "@";
					// sPaymentDatePattern=sPaymentDatePattern+"@";
				}

				String sQueryReSchPaymentDate = "select payment_date from NG_RAROC_REPAYMANT_DETAILS_API where wi_name='"
						+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "' and facility_id='"
						+ sFacilityId + "'";
				log.info("sQueryReSchPaymentDate..." + sQueryReSchPaymentDate);
				List<List<String>> sQueryReSchPaymentDateOut = formObject.getDataFromDB(sQueryReSchPaymentDate);
				log.info("sQueryReSchPaymentDateOut..." + sQueryReSchPaymentDateOut);
				if (sQueryReSchPaymentDateOut != null || !sQueryReSchPaymentDateOut.isEmpty()) {
					for (List<String> paymentDateRow : sQueryReSchPaymentDateOut) {
						sPaymentDate = paymentDateRow.get(0);
						sPaymentDatePattern = sPaymentDatePattern + sPaymentDate.substring(0, 10) + "*";
					}
					sPaymentDatePattern = sPaymentDatePattern + "@";
					log.info("sPaymentDatePattern..+++" + sPaymentDatePattern);
				}

				String sQueryTotalAmt = "select distinct total_amount  from NG_RAROC_REPAYMANT_DETAILS_API where wi_name='"
						+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "' and facility_id='"
						+ sFacilityId + "'";
				log.info("sQueryTotalAmt.." + sQueryTotalAmt);
				List<List<String>> sQueryTotalAmtOut = formObject.getDataFromDB(sQueryTotalAmt);
				log.info("sQueryTotalAmtOut..." + sQueryTotalAmtOut);
				if (sQueryTotalAmtOut != null || !sQueryTotalAmtOut.isEmpty()) {
					sTotalAmt = sQueryTotalAmtOut.get(0).get(0);
					log.info("sTotalAmt..." + sTotalAmt);

					String sQueryRepaymentType = "SELECT repayment_frequency_proposed FROM NG_RAROC_FACILITY_DETAILS where wi_name='"
							+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "' and facility_id='"
							+ sFacilityId + "'";// bp05
					List<List<String>> sQueryRepaymentTypeOut = formObject.getDataFromDB(sQueryRepaymentType);
					String sRepaymentType = sQueryRepaymentTypeOut.get(0).get(0);
					log.info("sRepaymentTypeshould be manual..." + sRepaymentType);
					if (Float.parseFloat(sTotalAmt) == sumPrincipalAmt && sRepaymentType.equalsIgnoreCase("Manual")) {
						principalAmtPattern = principalAmtPattern.replace("@*", "").replace("*@", "").replace("[", "")
								.replace("]", "");
						sPaymentDatePattern = sPaymentDatePattern.replace("@*", "").replace("*@", "");
						JSONObject filedataObject = new JSONObject();
						filedataObject.put("arrangement", sFacilityId);
						filedataObject.put("repayment_date", sPaymentDatePattern);
						filedataObject.put("principal_payment", principalAmtPattern);
						filesInfoArr.add(filedataObject);
						log.info("filesInfoArr...+++" + filesInfoArr);
					}
				}
			}
		}

		// end edit by mohit on 21-08-2024
		// commented on 21-08-2024
		/*
		 * String queryarrangement =
		 * "SELECT Arrangement_ FROM NG_RAROC_LOAN_DETAILS_API WHERE WI_NAME='" +
		 * formObject.getObjGeneralData().getM_strProcessInstanceId() + "'";
		 * log.info("queryarrangement>>>" + queryarrangement); String outputXMLArr =
		 * getDataFromDBWithColumns(queryarrangement, "Arrangement_"); XMLParser
		 * xmlParserArr = new XMLParser(outputXMLArr); log.info("xmlParserArr" +
		 * xmlParserArr); int totalRetrievedArr =
		 * Integer.parseInt(xmlParserArr.getValueOf(Constants.TOTAL_RETRIEVED));
		 * 
		 * if (totalRetrievedArr != 0) {
		 * log.info("Inside if of totalRetrievedArr != 0"); for (int j = 0; j <
		 * totalRetrievedArr; j++) { String paymentDate =""; String principalAmount ="";
		 * String row1 = xmlParserArr.getNextValueOf(Constants.ROW); XMLParser
		 * rowParser1 = new XMLParser(row1); String arrangementID =
		 * rowParser1.getValueOf("Arrangement_");
		 * 
		 * String columnLoan ="Payment_Date,Total_Amount"; String queryLoan = "SELECT "
		 * +columnLoan+" FROM NG_RAROC_REPAYMANT_DETAILS_API WHERE Arrangement='" +
		 * arrangementID + "'"; log.info("queryLoan>>>" + queryLoan); String
		 * outputXMLLoan = getDataFromDBWithColumns(queryLoan, columnLoan); XMLParser
		 * xmlParserLoan = new XMLParser(outputXMLLoan); log.info("xmlParserLoan" +
		 * xmlParserLoan); int totalRetrievedLoan =
		 * Integer.parseInt(xmlParserLoan.getValueOf(Constants.TOTAL_RETRIEVED));
		 * 
		 * if (totalRetrievedLoan != 0) {
		 * log.info("Inside if of totalRetrievedLoan != 0"); for (int i = 0; i <
		 * totalRetrievedLoan; i++) { String row =
		 * xmlParserLoan.getNextValueOf(Constants.ROW); XMLParser rowParser = new
		 * XMLParser(row); if(i ==0) { paymentDate
		 * =rowParser.getValueOf("Payment_Date"); principalAmount=
		 * rowParser.getValueOf("Total_Amount"); }else { paymentDate =
		 * paymentDate+"*"+rowParser.getValueOf("Payment_Date");
		 * principalAmount=principalAmount+"*"+ rowParser.getValueOf("Total_Amount"); }
		 * 
		 * } log.info("before paymentDate..."+paymentDate);//02-07-2024 mohit String
		 * updatepaymentDate = getDateFormat(paymentDate); //paymentDate =
		 * paymentDate.replaceAll("-", "");
		 * log.info("after paymentDate"+updatepaymentDate); principalAmount
		 * =principalAmount.replaceAll(",", ""); JSONObject jsonObject = new
		 * JSONObject(); jsonObject.put("arrangement", arrangementID);
		 * //jsonObject.put("repayment_date", paymentDate);
		 * jsonObject.put("repayment_date", updatepaymentDate);
		 * jsonObject.put("principal_payment", principalAmount);
		 * log.info("Inside if jsonObject"+jsonObject); filesInfoArr.add(jsonObject); }
		 * 
		 * } }
		 */// commented on 21-08-2024
			// log.info("Inside if filesInfoArr"+filesInfoArr);

		/*
		 * JSONObject jsonObject = new JSONObject(); JSONObject jsonObject2 = new
		 * JSONObject(); jsonObject.put("arrangement", "AA192154ZCQT");
		 * jsonObject.put("repayment_date", "20221012*20221013*20221014*20221015");
		 * jsonObject.put("principal_payment", "6070.5*6070.92*6071.33*6071.75");
		 * jsonObject2.put("arrangement", "AA19215PKTPK");
		 * jsonObject2.put("repayment_date", "20221012*20221013*20221014*20221015");
		 * jsonObject2.put("principal_payment", "6070.5*6070.92*6071.33*6071.75");
		 * log.info("Inside if jsonObject"+jsonObject);
		 * 
		 * filesInfoArr.add(jsonObject); filesInfoArr.add(jsonObject2);
		 */

		// JSONArray formDataArr = new JSONArray(); //06-07-2024 by mohit as no array of
		// formData needed ;
		JSONObject formDataObj = new JSONObject();
		JSONArray customerArr = new JSONArray();
		JSONObject customerObj = new JSONObject();
		JSONArray facilitiesArr = new JSONArray();
		JSONObject facilitiesObj = new JSONObject();
		log.info("inside facilitiesObj>>>>>>");
		JSONArray crmDataArr = new JSONArray();
		JSONObject crmDataObj = new JSONObject();
		JSONArray incomeTypeArr = new JSONArray();
		JSONObject incomeTypeObj = new JSONObject();

		// start changes by mohit 11-06-2024 for other section
		JSONArray otherTypeArr = new JSONArray();
		JSONObject otherTypeObj = new JSONObject();
		// end changes by mohit 11-06-2024 for other section
		// start edit by mohit 03-07-2024 for COLLATERAL_FACILITY_MAP
		JSONArray colfacilitiesArr = new JSONArray();
		JSONObject colfacilitiesObj = new JSONObject();
		// end edit by mohit 03-07-2024 for COLLATERAL_FACILITY_MAP

		JSONObject customerObj1 = new JSONObject();
		customerObj1.put("name", "new_customer");
		customerObj1.put("value", "False");
		JSONObject customerObj2 = new JSONObject();
		customerObj2.put("name", "code");// Mandatory
		customerObj2.put("value", code);// T24 Customer ID

		JSONObject customerObj3 = new JSONObject();
		customerObj3.put("name", "name");// Mandatory
		customerObj3.put("value", name);// Customer Name
		JSONObject customerObj4 = new JSONObject();
		customerObj4.put("name", "country");// Mandatory
		customerObj4.put("value", country);// Risk Country Name

		JSONObject customerObj5 = new JSONObject();
		customerObj5.put("name", "region");
		// customerObj5.put("value", "Dubai");//karan to confirm
		customerObj5.put("value", region);// EST_PLACE DB
		JSONObject customerObj6 = new JSONObject();
		customerObj6.put("name", "internal_rating");// Mandatory
		customerObj6.put("value", internalRatingCust);// Internal MRA Rating

		JSONObject customerObj7 = new JSONObject();
		customerObj7.put("name", "external_rating_agency_code");
		customerObj7.put("value", "SNP");// Unrated
		JSONObject customerObj8 = new JSONObject();
		customerObj8.put("name", "external_rating");// Mandatory
		customerObj8.put("value", "Unrated");// External Rating; extRating

		JSONObject customerObj9 = new JSONObject();
		customerObj9.put("name", "industry_sector");// Mandatory
		customerObj9.put("value", industrySector);// Industry Segement
		JSONObject customerObj10 = new JSONObject();
		customerObj10.put("name", "sales_turnover");// Mandatory
		customerObj10.put("value", salesTurnover);// Sales Turnover

		JSONObject customerObj11 = new JSONObject();
		customerObj11.put("name", "segment");// Mandatory
		customerObj11.put("value", segment);// Business segment
		JSONObject customerObj12 = new JSONObject();
		customerObj12.put("name", "group_id");// Mandatory
		customerObj12.put("value", groupId);

		JSONObject customerObj13 = new JSONObject();
		customerObj13.put("name", "group_name");// Mandatory
		customerObj13.put("value", groupName);
		// JSONObject customerObj14 = new JSONObject();
		/*
		 * customerObj14.put("name", "assessment_date"); customerObj14.put("value",
		 * assessmentDate);
		 */// 06-07-2024

		customerArr.add(customerObj1);
		customerArr.add(customerObj2);
		customerArr.add(customerObj3);
		customerArr.add(customerObj4);
		customerArr.add(customerObj5);
		customerArr.add(customerObj6);
		customerArr.add(customerObj7);
		customerArr.add(customerObj8);
		customerArr.add(customerObj9);
		customerArr.add(customerObj10);
		customerArr.add(customerObj11);
		customerArr.add(customerObj12);
		customerArr.add(customerObj13);
		// customerArr.add(customerObj14);
		formDataObj.put("customer", customerArr);
		/*
		 * customerObj.put("customer", customerArr); formDataArr.add(customerObj);
		 */ // 06-07-2024
		// formDataObj.put("customer", customerArr);
		// formDataArr.add(formDataObj);

		// start edit by mohit 03-07-2024 for COLLATERAL_FACILITY_MAP:
		// commitmentNo(name_value1),
		// facilityName(product_type_value1),allocationPercProp
		JSONObject colfacilitiesObj1 = new JSONObject();
		colfacilitiesObj1.put("name", "facility");
		JSONArray colfacilitiesValArr1 = new JSONArray();
		/*
		 * for (String product_type_value : product_type_value1) {
		 * colfacilitiesValArr1.add(product_type_value); }
		 */
		for (String commitment_value : commitment_value1) {
			log.info("####commitment_value####" + commitment_value);
			colfacilitiesValArr1.add(commitment_value);
		}
		colfacilitiesObj1.put("value", colfacilitiesValArr1);

		JSONObject colfacilitiesObj2 = new JSONObject();
		colfacilitiesObj2.put("name", "collateral");
		JSONArray colfacilitiesValArr2 = new JSONArray();// F_Collateral_Number_Proposed
		/*
		 * fCollateralNumberProposed=
		 * iformrefrence.getValue("F_Collateral_Number_Proposed").toString();
		 * colfacilitiesObj2.put("value", fCollateralNumberProposed);
		 */
		/*
		 * for (String name_value : name_value1) { colfacilitiesValArr2.add(name_value);
		 * }
		 */
		for (String collateral_No : collateral_No1) {
			log.info("####collateral_No####" + collateral_No);
			colfacilitiesValArr2.add(collateral_No);
			// colfacilitiesValArr2.add(iformrefrence.getValue("F_Collateral_Number_Proposed").toString());
		}
		colfacilitiesObj2.put("value", colfacilitiesValArr2);

		JSONObject colfacilitiesObj3 = new JSONObject();
		colfacilitiesObj3.put("name", "weight");
		JSONArray colfacilitiesValArr3 = new JSONArray();
		for (String allocation_Perc_Prop_value : allocation_Perc_Prop_value1) {
			/*
			 * if (allocation_Perc_Prop_value != null || allocation_Perc_Prop_value != "") {
			 * Float fAallocation_Perc_Prop_value =
			 * Float.parseFloat(allocation_Perc_Prop_value); allocation_Perc_Prop_value =
			 * String.format("%.3f",fAallocation_Perc_Prop_value / 100);
			 * //allocation_Perc_Prop_value = String.valueOf(fAallocation_Perc_Prop_value /
			 * 100); }
			 */
			log.info("####allocation_Perc_Prop_value####" + allocation_Perc_Prop_value);
			allocation_Perc_Prop_value = decimalFloatValue(allocation_Perc_Prop_value);
			colfacilitiesValArr3.add(allocation_Perc_Prop_value);
		}
		colfacilitiesObj3.put("value", colfacilitiesValArr3);
		// end edit by mohit 03-07-2024 for COLLATERAL_FACILITY_MAP:
		// commitmentNo(name_value1),
		// facilityName(product_type_value1),allocationPercProp

		JSONObject facilitiesObj1 = new JSONObject();
		facilitiesObj1.put("name", "name");
		JSONArray facilitiesValArr1 = new JSONArray();
		int counter1 = 0;
		int counter2 = 0;
		for (String name_value : name_value1) {// commitment a,b
			counter1 = counter1 + 1;
			log.info("name_value.." + name_value + "..counter1..." + counter1);
			for (String facilityId_value : facilityId_value1) {// facilty aa,bb
				counter2 = counter2 + 1;
				if (counter2 == counter1) {
					log.info("facilityId_value.." + facilityId_value + "..counter2..." + counter2);
					if (name_value.isEmpty() || name_value == null || name_value == "") {
						log.info("inside if name_value is empty...");
						facilitiesValArr1.add(facilityId_value);

					} else {
						log.info("inside if name_value is not empty...");
						facilitiesValArr1.add(name_value);
					}
				}
			}
			counter2 = 0;

		}
		facilitiesObj1.put("value", facilitiesValArr1);

		JSONObject facilitiesObj2 = new JSONObject();
		facilitiesObj2.put("name", "otc_flag");// Mandatory
		JSONArray facilitiesValArr2 = new JSONArray();

		/*
		 * for (Boolean bOtc_flag_value : bOtc_flag_value1) {
		 * facilitiesValArr2.add(bOtc_flag_value); }
		 */
		for (String otc_flag_value : otc_flag_value1) {
			facilitiesValArr2.add(otc_flag_value);// TreasuryProposed
		}
		facilitiesObj2.put("value", facilitiesValArr2);

		JSONObject facilitiesObj3 = new JSONObject();
		facilitiesObj3.put("name", "type_");// Mandatory
		JSONArray facilitiesValArr3 = new JSONArray();
		for (String type_value : type_value1) {// FACILITY_TYPE
			facilitiesValArr3.add(type_value);
		}
		facilitiesObj3.put("value", facilitiesValArr3);

		JSONObject facilitiesObj4 = new JSONObject();
		facilitiesObj4.put("name", "product_type");// Mandatory
		JSONArray facilitiesValArr4 = new JSONArray();
		for (String product_type_value : product_type_value1) { // Facility Name
			facilitiesValArr4.add(product_type_value);
		}
		facilitiesObj4.put("value", facilitiesValArr4);

		JSONObject facilitiesObj5 = new JSONObject();
		facilitiesObj5.put("name", "repayment_mode");// Mandatory
		JSONArray facilitiesValArr5 = new JSONArray();
		for (String repayment_mode_value : repayment_mode_value1) {// Repayment Type
			log.info("repayment_mode_value..." + repayment_mode_value);
			facilitiesValArr5.add(repayment_mode_value);
		}
		facilitiesObj5.put("value", facilitiesValArr5);
		JSONObject facilitiesObj6 = new JSONObject();
		facilitiesObj6.put("name", "repayment_frequency");// Mandatory FOR Constant/Linear
		JSONArray facilitiesValArr6 = new JSONArray();
		for (String repayment_frequency_value : repayment_frequency_value1) {
			log.info("repayment_frequency_value..." + repayment_frequency_value);
			facilitiesValArr6.add(repayment_frequency_value);
		}
		facilitiesObj6.put("value", facilitiesValArr6);

		JSONObject facilitiesObj7 = new JSONObject();
		facilitiesObj7.put("name", "tenor");// Mandatory
		JSONArray facilitiesValArr7 = new JSONArray();
		for (String tenor_value : tenor_value1) {
			facilitiesValArr7.add(tenor_value);
		}
		facilitiesObj7.put("value", facilitiesValArr7);

		JSONObject facilitiesObj8 = new JSONObject();
		facilitiesObj8.put("name", "moratorium_period");
		JSONArray facilitiesValArr8 = new JSONArray();
		for (String moratorium_period_value : moratorium_period_value1) {
			facilitiesValArr8.add(moratorium_period_value);
		}
		facilitiesObj8.put("value", facilitiesValArr8);

		JSONObject facilitiesObj9 = new JSONObject();
		facilitiesObj9.put("name", "currency");// Mandatory
		JSONArray facilitiesValArr9 = new JSONArray();
		for (String currency_value : currency_value1) {// Currency
			facilitiesValArr9.add(currency_value);
		}
		facilitiesObj9.put("value", facilitiesValArr9);

		JSONObject facilitiesObj10 = new JSONObject();
		facilitiesObj10.put("name", "index");// Mandatory FOR FUNDED FACILITIES
		JSONArray facilitiesValArr10 = new JSONArray();
		for (String index_value : index_value1) {
			// if(!index_value.isEmpty()) {
			facilitiesValArr10.add(index_value);
			/*
			 * }else { facilitiesValArr10.add("None"); }
			 */ // 21-08-2024 due to null pointer exception
		}
		facilitiesObj10.put("value", facilitiesValArr10);

		JSONObject facilitiesObj11 = new JSONObject();
		facilitiesObj11.put("name", "index_tenor");// Mandatory IF index IS NOT NONE
		JSONArray facilitiesValArr11 = new JSONArray();
		for (String index_tenor_value : index_tenor_value1) {
			facilitiesValArr11.add(index_tenor_value);
		}
		facilitiesObj11.put("value", facilitiesValArr11);

		JSONObject facilitiesObj12 = new JSONObject();
		facilitiesObj12.put("name", "tenor_unit");// Mandatory
		JSONArray facilitiesValArr12 = new JSONArray();
		for (String tenor_unit_value : tenor_unit_value1) {
			facilitiesValArr12.add(tenor_unit_value);
		}
		facilitiesObj12.put("value", facilitiesValArr12);

		JSONObject facilitiesObj13 = new JSONObject();
		facilitiesObj13.put("name", "margin");
		JSONArray facilitiesValArr13 = new JSONArray();
		// for (String margin_value : margin_value1) {//Cash Margin
		/*
		 * if (margin_value != null || margin_value != "") { Float fmargin_value =
		 * Float.parseFloat(margin_value); margin_value =
		 * String.format("%.3f",fmargin_value / 100); }
		 */
		for (String margin_value : commission_rate_value1) {// Margin/Commision
			if (margin_value != null) // 21-08-2024 for otc_product null values
				margin_value = decimalFloatValue(margin_value);
			facilitiesValArr13.add(margin_value);
		}
		facilitiesObj13.put("value", facilitiesValArr13);

		JSONObject facilitiesObj14 = new JSONObject();
		facilitiesObj14.put("name", "commission_rate");// Mandatory FOR NONFUNDED FACILITIES
		JSONArray facilitiesValArr14 = new JSONArray();
		// for (String commission_rate_value : commission_rate_value1)
		// {//Margin/Commision
		/*
		 * if (commission_rate_value != null || commission_rate_value != "") { Float
		 * fCommission_rate_value = Float.parseFloat(commission_rate_value);
		 * commission_rate_value = String.format("%.3f",fCommission_rate_value / 100);
		 * //commission_rate_value = String.valueOf(fCommission_rate_value / 100); }
		 */
		for (String commission_rate_value : valueProp_value1) {
			if (commission_rate_value != null) // 21-08-2024 for otc_product null values
				commission_rate_value = decimalFloatValue(commission_rate_value);
			facilitiesValArr14.add(commission_rate_value);
		}
		facilitiesObj14.put("value", facilitiesValArr14);

		JSONObject facilitiesObj15 = new JSONObject();
		facilitiesObj15.put("name", "limit");// Mandatory
		JSONArray facilitiesValArr15 = new JSONArray();
		for (String limit_value : limit_value1) {// Facility Amount
			try {
				if (limit_value == null)
					facilitiesValArr15.add(limit_value);
				else if (!limit_value.isEmpty() || limit_value != "") {
					int val = new BigDecimal(limit_value).intValue();
					facilitiesValArr15.add(String.valueOf(val));
				}
			} catch (Exception e) {
				log.error("limit_value... : ", e);
			}
		}
		facilitiesObj15.put("value", facilitiesValArr15);

		JSONObject facilitiesObj16 = new JSONObject();
		facilitiesObj16.put("name", "utilisation");// Mandatory
		JSONArray facilitiesValArr16 = new JSONArray();
		for (String utilisation_value : utilisation_value1) {// Expected Utilisation
			/*
			 * if (utilisation_value != null || utilisation_value != "") { Float
			 * futilisation_value = Float.parseFloat(utilisation_value); utilisation_value =
			 * String.format("%.3f", futilisation_value / 100); // interest_floor_value =
			 * String.valueOf(fInterest_floor_value / 100); }
			 */
			// utilisation_value
			// utilisation_value =
			// decimalFloatValue(iformrefrence.getValue("F_Utilization_Proposed").toString());
			if (utilisation_value != null) // 21-08-2024 for otc_product null values
				utilisation_value = decimalFloatValue(utilisation_value);
			facilitiesValArr16.add(utilisation_value);
		}

		facilitiesObj16.put("value", facilitiesValArr16);

		JSONObject facilitiesObj17 = new JSONObject();
		facilitiesObj17.put("name", "facility_position");// Mandatory
		JSONArray facilitiesValArr17 = new JSONArray();
		for (String facility_position_value : facility_position_value1) {// Senior/Subordinate
			facilitiesValArr17.add(facility_position_value);
		}
		facilitiesObj17.put("value", facilitiesValArr17);

		JSONObject facilitiesObj18 = new JSONObject();
		facilitiesObj18.put("name", "commitment_type");// Mandatory
		JSONArray facilitiesValArr18 = new JSONArray();
		for (String commitment_type_value : commitment_type_value1) {// Committed/Uncommited
			facilitiesValArr18.add(commitment_type_value);
		}
		facilitiesObj18.put("value", facilitiesValArr18);

		JSONObject facilitiesObj19 = new JSONObject();
		facilitiesObj19.put("name", "csv_path");// Mandatory IF repayment_mode is Manual
		JSONArray facilitiesValArr19 = new JSONArray();
		for (String csv_path_value : csv_path_value1) {// value not provided
			facilitiesValArr19.add(csv_path_value);
		}
		facilitiesObj19.put("value", facilitiesValArr19);

		JSONObject facilitiesObj20 = new JSONObject();
		facilitiesObj20.put("name", "notional_amount");// Mandatory FOR OTC PROD (Treasury Products IS YES)
		JSONArray facilitiesValArr20 = new JSONArray();
		int counterNA = 0;
		int counterOtc = 0;
		for (String notional_amount_value : notional_amount_value1) {// Facility Amount
			counterNA = counterNA + 1;
			for (String otc_flag_value : otc_flag_value1) {
				counterOtc = counterOtc + 1;
				if (counterNA == counterOtc) {
					if (otc_flag_value.equalsIgnoreCase("True")) {
						facilitiesValArr20.add(notional_amount_value);
					} else if (otc_flag_value.equalsIgnoreCase("False")) {
						facilitiesValArr20.add("");
					}
				}
			}
			counterOtc = 0;
		}
		facilitiesObj20.put("value", facilitiesValArr20);

		JSONObject facilitiesObj21 = new JSONObject();
		facilitiesObj21.put("name", "expected_1_year_return");// Mandatory FOR OTC PROD (Treasury Products IS YES)
		JSONArray facilitiesValArr21 = new JSONArray();
		for (String expected_1_year_return_type_value : expected_1_year_return_type_value1) {
			facilitiesValArr21.add(expected_1_year_return_type_value);
		}
		facilitiesObj21.put("value", facilitiesValArr21);

		JSONObject facilitiesObj22 = new JSONObject();
		facilitiesObj22.put("name", "market_risk_capital");// Mandatory FOR OTC PROD (Treasury Products IS YES)
		JSONArray facilitiesValArr22 = new JSONArray();
		for (String market_risk_capital_value : market_risk_capital_value1) {
			facilitiesValArr22.add(market_risk_capital_value);
		}
		facilitiesObj22.put("value", facilitiesValArr22);

		JSONObject facilitiesObj23 = new JSONObject();
		facilitiesObj23.put("name", "ftp_override");
		JSONArray facilitiesValArr23 = new JSONArray();
		for (String ftp_override_value : ftp_override_value1) {
			facilitiesValArr23.add(ftp_override_value);
		}
		facilitiesObj23.put("value", facilitiesValArr23);

		JSONObject facilitiesObj24 = new JSONObject();
		facilitiesObj24.put("name", "repricing_frequency");// Mandatory if interest rate is floating
		JSONArray facilitiesValArr24 = new JSONArray();
		for (String repricing_frequency_value : repricing_frequency_value1) {
			facilitiesValArr24.add(repricing_frequency_value);
			sHardCodeValue = repricing_frequency_value; // sHardCodeValue
		}
		facilitiesObj24.put("value", facilitiesValArr24);

		JSONObject facilitiesObj25 = new JSONObject();
		facilitiesObj25.put("name", "ftp_rate");
		JSONArray facilitiesValArr25 = new JSONArray();
		for (String ftp_rate_value : ftp_rate_value1) {
			if (ftp_rate_value.equalsIgnoreCase("-")) {
				ftp_rate_value = "";
			}
			facilitiesValArr25.add(ftp_rate_value);
		}
		facilitiesObj25.put("value", facilitiesValArr25);

		// start 03-07-2024 by mohit for FACILITY: Missing Field interest_floor
		JSONObject facilitiesObj26 = new JSONObject();
		facilitiesObj26.put("name", "interest_floor");
		JSONArray facilitiesValArr26 = new JSONArray();
		for (String interest_floor_value : interest_floor_value1) { // Minimum
			/*
			 * if (interest_floor_value != null || interest_floor_value != "") { Float
			 * fInterest_floor_value = Float.parseFloat(interest_floor_value);
			 * interest_floor_value = String.format("%.3f",fInterest_floor_value / 100);
			 * //interest_floor_value = String.valueOf(fInterest_floor_value / 100); }
			 */
			if (interest_floor_value != null) // 21-08-2024 for otc_product null values
				interest_floor_value = decimalFloatValue(interest_floor_value);
			facilitiesValArr26.add(interest_floor_value);
		}
		facilitiesObj26.put("value", facilitiesValArr26);

		// end 03-07-2024 by mohit for FACILITY: Missing Field interest_floor

		facilitiesArr.add(facilitiesObj1);
		facilitiesArr.add(facilitiesObj2);
		facilitiesArr.add(facilitiesObj3);
		facilitiesArr.add(facilitiesObj4);
		facilitiesArr.add(facilitiesObj5);
		facilitiesArr.add(facilitiesObj6);
		facilitiesArr.add(facilitiesObj7);
		facilitiesArr.add(facilitiesObj8);
		facilitiesArr.add(facilitiesObj9);
		facilitiesArr.add(facilitiesObj10);
		facilitiesArr.add(facilitiesObj11);
		facilitiesArr.add(facilitiesObj12);
		facilitiesArr.add(facilitiesObj13);
		facilitiesArr.add(facilitiesObj14);
		facilitiesArr.add(facilitiesObj15);
		facilitiesArr.add(facilitiesObj16);
		facilitiesArr.add(facilitiesObj17);
		facilitiesArr.add(facilitiesObj18);
		facilitiesArr.add(facilitiesObj19);
		facilitiesArr.add(facilitiesObj20);
		facilitiesArr.add(facilitiesObj21);
		facilitiesArr.add(facilitiesObj22);
		facilitiesArr.add(facilitiesObj23);
		facilitiesArr.add(facilitiesObj24);
		facilitiesArr.add(facilitiesObj25);
		facilitiesArr.add(facilitiesObj26);

		formDataObj.put("facilities", facilitiesArr);
		// facilitiesObj.put("facilities", facilitiesArr);
		// formDataArr.add(facilitiesObj); //06-07-2024

		// start edit by mohit 03-07-2024 for COLLATERAL_FACILITY_MAP
		colfacilitiesArr.add(colfacilitiesObj1);
		colfacilitiesArr.add(colfacilitiesObj2);
		colfacilitiesArr.add(colfacilitiesObj3);
		formDataObj.put("collateral_facility_map", colfacilitiesArr);
		/*
		 * colfacilitiesObj.put("collateral_facility_map", colfacilitiesArr);
		 * formDataArr.add(colfacilitiesObj);
		 */ // 06-07-2024
		// end edit by mohit 03-07-2024 for COLLATERAL_FACILITY_MAP

		// CRMDetails Obj
		JSONArray gridCount = iformrefrence.getDataFromGrid("table30");
		String bankGuarantor = "";
		// start edit 04-07-2024 mohit
		/*
		 * if(gridCount .size() != 0) { for (int j = 0; j < gridCount .size(); j++) {
		 * log.info("gridCount size " +gridCount.size()); String guaranteeguarantorTag =
		 * iformrefrence.getTableCellValue("table30", j, 3).toString();
		 * log.info("mandatoryType --->>>>  " +guaranteeguarantorTag);
		 * if(guaranteeguarantorTag.equalsIgnoreCase("Bank Gurantee")) { bankGuarantor
		 * ="Y"; String guaranteeType = iformrefrence.getTableCellValue("table30", j,
		 * 0).toString(); String guaranteeName =
		 * iformrefrence.getTableCellValue("table30", j, 1).toString(); String
		 * guaranteeAmount = iformrefrence.getTableCellValue("table30", j,
		 * 2).toString(); String guaranteeguarantor =
		 * iformrefrence.getTableCellValue("table30", j, 3).toString();//4
		 * COLLATERAL_NUMBER AND 5 COLLATERAL_CURRENCY String externalRating =
		 * iformrefrence.getTableCellValue("table30", j, 6).toString(); String
		 * internalRating = iformrefrence.getTableCellValue("table30", j, 7).toString();
		 * String externalRatingCode = iformrefrence.getTableCellValue("table30", j,
		 * 8).toString();
		 * 
		 * JSONObject crmDataObj1 = new JSONObject(); crmDataObj1.put("name",
		 * "guarantee_type_"); crmDataObj1.put("value", guaranteeType); JSONObject
		 * crmDataObj2 = new JSONObject(); crmDataObj2.put("name", "guarantee_name");
		 * crmDataObj2.put("value", guaranteeName); JSONObject crmDataObj3 = new
		 * JSONObject(); crmDataObj3.put("name", "guarantee_amount");
		 * crmDataObj3.put("value", guaranteeAmount); JSONObject crmDataObj4 = new
		 * JSONObject(); crmDataObj4.put("name", "guarantee_external_rating");
		 * crmDataObj4.put("value", externalRating); JSONObject crmDataObj5 = new
		 * JSONObject(); crmDataObj5.put("name", "guarantee_guarantor_type");
		 * crmDataObj5.put("value", guaranteeguarantor); JSONObject crmDataObj6 = new
		 * JSONObject(); crmDataObj6.put("name", "guarantee_internal_rating");
		 * crmDataObj6.put("value", internalRating); JSONObject crmDataObj7 = new
		 * JSONObject(); crmDataObj7.put("name",
		 * "guarantee_external_rating_agency_code"); crmDataObj7.put("value",
		 * externalRatingCode); crmDataArr.add(crmDataObj1);
		 * crmDataArr.add(crmDataObj2); crmDataArr.add(crmDataObj3);
		 * crmDataArr.add(crmDataObj4); crmDataArr.add(crmDataObj5);
		 * crmDataArr.add(crmDataObj6); crmDataArr.add(crmDataObj7); } } }
		 * if(!bankGuarantor.equalsIgnoreCase("Y")) { JSONObject crmNonBankDataObj = new
		 * JSONObject(); crmNonBankDataObj.put("name", "guarantee_external_rating");
		 * crmNonBankDataObj.put("value", "Unrated"); crmDataArr.add(crmNonBankDataObj);
		 * }
		 */
		String guaranteeType = "";
		String guaranteeName = "";
		String guaranteeAmount = "";
		String guaranteeguarantor = "";
		String externalRating = "";
		String internalRating = "";
		String externalRatingCode = "";
		String sCollateralCurrencyHaircut = "";

		JSONObject crmDataObj1 = new JSONObject();
		crmDataObj1.put("name", "guarantee_type_");
		crmDataObj1.put("value", guaranteeType);
		JSONObject crmDataObj2 = new JSONObject();
		crmDataObj2.put("name", "guarantee_name");
		crmDataObj2.put("value", guaranteeName);
		JSONObject crmDataObj3 = new JSONObject();
		crmDataObj3.put("name", "guarantee_amount");// Mandatory in case of guarantee
		crmDataObj3.put("value", guaranteeAmount);
		JSONObject crmDataObj4 = new JSONObject();
		crmDataObj4.put("name", "guarantee_external_rating");// Mandatory in case of guarantee default Unrated
		if (!guaranteeType.isEmpty()) {
			crmDataObj4.put("value", "Unrated");
		} else {
			crmDataObj4.put("value", externalRating);
		}

		JSONObject crmDataObj5 = new JSONObject();
		crmDataObj5.put("name", "guarantee_guarantor_type");// Mandatory in case of guarantee default BANK
		if (!guaranteeType.isEmpty()) {
			crmDataObj4.put("value", "Bank");
		} else {
			crmDataObj4.put("value", guaranteeguarantor);
		}

		crmDataObj5.put("value", guaranteeguarantor);
		JSONObject crmDataObj6 = new JSONObject();
		crmDataObj6.put("name", "guarantee_internal_rating");// Mandatory in case of guarantee
		crmDataObj6.put("value", internalRating);
		JSONObject crmDataObj7 = new JSONObject();
		crmDataObj7.put("name", "guarantee_external_rating_agency_code");
		crmDataObj7.put("value", externalRatingCode);
		JSONObject crmDataObj8 = new JSONObject();
		crmDataObj8.put("name", "collateral_currency_haircut");
		crmDataObj8.put("value", externalRatingCode);
		crmDataArr.add(crmDataObj1);
		crmDataArr.add(crmDataObj2);
		crmDataArr.add(crmDataObj3);
		crmDataArr.add(crmDataObj4);
		crmDataArr.add(crmDataObj5);
		crmDataArr.add(crmDataObj6);
		crmDataArr.add(crmDataObj7);
		crmDataArr.add(crmDataObj8);

		// end edit 04-07-2024 mohit gm to check

		JSONObject crmComplexDataObj1 = new JSONObject();
		crmComplexDataObj1.put("name", "collateral_name");// MANDATORY
		JSONArray crmComplexDataArr1 = new JSONArray();
		for (String collateral_Name : collateral_Name1) {
			// for (String collateral_Name : collateral_No1) {//from CFM grid 22-07-2024
			crmComplexDataArr1.add(collateral_Name);// R_09-07-2024
			// crmComplexDataArr1.add(collateral_Name);
		}
		crmComplexDataObj1.put("value", crmComplexDataArr1);

		JSONObject crmComplexDataObj2 = new JSONObject();
		crmComplexDataObj2.put("name", "collateral_type_");// MANDATORY
		JSONArray crmComplexDataArr2 = new JSONArray();
		for (String collateral_Type : collateral_Type1) {
			log.info("newCollateralType1....." + newCollateralType1);
			crmComplexDataArr2.add(collateral_Type);
		}
		crmComplexDataObj2.put("value", crmComplexDataArr2);

		JSONObject crmComplexDataObj3 = new JSONObject();
		crmComplexDataObj3.put("name", "collateral_amount");// MANDATORY
		JSONArray crmComplexDataArr3 = new JSONArray();
		for (String collateral_Amount : collateral_Amount1) {
			// crmComplexDataArr3.add(collateral_Amount);
			try {
				int val = 0;
				if (!collateral_Amount.isEmpty() || collateral_Amount != null || collateral_Amount != "") {
					val = new BigDecimal(collateral_Amount).intValue();

					crmComplexDataArr3.add(String.valueOf(val));
				}
			} catch (Exception e) {
				log.error("collateral_Amount... : ", e);
			}
		}
		crmComplexDataObj3.put("value", crmComplexDataArr3);

		JSONObject crmComplexDataObj4 = new JSONObject();
		crmComplexDataObj4.put("name", "collateral_currency");// MANDATORY
		JSONArray crmComplexDataArr4 = new JSONArray();
		for (String collateral_Currency : collateral_Currency1) {
			crmComplexDataArr4.add(collateral_Currency);
		}
		crmComplexDataObj4.put("value", crmComplexDataArr4);

		JSONObject crmComplexDataObj5 = new JSONObject();
		crmComplexDataObj5.put("name", "collateral_lien_amount");// Mandatory for Lien CollateraL
		JSONArray crmComplexDataArr5 = new JSONArray();
		for (String collateral_Lean : collateral_Lean1) {
			for (String collateral_Name : collateral_Name1) {
				if (collateral_Name.equalsIgnoreCase("Lien")) {
					crmComplexDataArr5.add(collateral_Lean);
				} else {
					crmComplexDataArr5.add("");
				}
			}
		}
		crmComplexDataObj5.put("value", crmComplexDataArr5);

		JSONObject crmComplexDataObj6 = new JSONObject();
		crmComplexDataObj6.put("name", "collateral_lien_interest");// Mandatory for Lien Collateral
		JSONArray crmComplexDataArr6 = new JSONArray();
		// int counterLI=0;
		// int counterN1=0;
		for (String collateral_LeanInterest : collateral_LeanInterest1) {
			// counterLI=counterLI+1;
			for (String collateral_Name : collateral_Name1) {
				// counterN1=counterN1+1;
				// if (counterN1 == counterLI) {
				if (collateral_Name.equalsIgnoreCase("Lien")) {
					crmComplexDataArr6.add(collateral_LeanInterest);
				} else {
					crmComplexDataArr6.add("");
				}
				// }
			} // counterN1=0;
		}
		crmComplexDataObj6.put("value", crmComplexDataArr6);

		JSONObject crmComplexDataObj7 = new JSONObject();
		crmComplexDataObj7.put("name", "collateral_lien_tenor");// Mandatory for Lien Collateral
		JSONArray crmComplexDataArr7 = new JSONArray();
		// int counterLT=0;
		// int counterN2=0;
		for (String collateral_LeanTenor : collateral_LeanTenor1) {
			// counterLT=counterLT+1;
			for (String collateral_Name : collateral_Name1) {
				// counterN2=counterN2+1;
				// if(counterN2==counterLT) {
				if (collateral_Name.equalsIgnoreCase("Lien")) {
					crmComplexDataArr7.add(collateral_LeanTenor);
				} else {
					crmComplexDataArr7.add("");
				}
				// }
			} // counterN2=0;

		}
		crmComplexDataObj7.put("value", crmComplexDataArr7);

		// start edit by mohit 04-07-2024 for cash margin and collateral haircut for
		// crmdetails
		JSONObject crmComplexDataObj8 = new JSONObject();
		crmComplexDataObj8.put("name", "collateral_cash_margin");// Mandatory for cash margin,???
		JSONArray crmComplexDataArr8 = new JSONArray();
		// int counterCM=0;
		// int counterN3=0;
		for (String crmCollateralCashMargin : crmCollateral_cash_margin1) {
			// counterCM=counterCM+1;
			for (String collateral_Name : collateral_Name1) {
				// counterN3=counterN3+1;
				// if(counterN3==counterCM) {
				if (collateral_Name.equalsIgnoreCase("Lien")) {
					crmComplexDataArr8.add(sHardCodeValue);
				} else {
					crmComplexDataArr8.add("");
				}
				// }
			} // counterN3=0;
		}
		crmComplexDataObj8.put("value", crmComplexDataArr8);

		JSONObject crmComplexDataObj9 = new JSONObject();
		crmComplexDataObj9.put("name", "collateral_haircut");
		JSONArray crmComplexDataArr9 = new JSONArray();
		for (String crmCollateralHaircut : crmCollateral_haircut1) {
			for (String collateral_Name : collateral_Name1) {
				if (collateral_Name.equalsIgnoreCase("Lien")) {
					crmComplexDataArr9.add(crmCollateralHaircut);
				} else {
					crmComplexDataArr9.add("");
				}
			}

		}
		crmComplexDataObj9.put("value", crmComplexDataArr9);

		// end edit by mohit 04-07-2024 for cash margin and collateral haircut for
		// crmdetails

		crmDataArr.add(crmComplexDataObj1);
		crmDataArr.add(crmComplexDataObj2);
		crmDataArr.add(crmComplexDataObj3);
		crmDataArr.add(crmComplexDataObj4);
		crmDataArr.add(crmComplexDataObj5);
		crmDataArr.add(crmComplexDataObj6);
		crmDataArr.add(crmComplexDataObj7);
		crmDataArr.add(crmComplexDataObj8);
		crmDataArr.add(crmComplexDataObj9);
		formDataObj.put("crm_details", crmDataArr);
		/*
		 * crmDataObj.put("crmDetails", crmDataArr); formDataArr.add(crmDataObj);
		 */ // 06-07-2024

		// IncomeType Obj
		JSONObject incomeDataObj1 = new JSONObject();
		incomeDataObj1.put("name", "name");// Mandatory
		JSONArray incomeDataArr1 = new JSONArray();
		for (String income_Name : income_Name1) {
			incomeDataArr1.add(income_Name);
		}
		incomeDataObj1.put("value", incomeDataArr1);

		JSONObject incomeDataObj2 = new JSONObject();
		incomeDataObj2.put("name", "type_");// Mandatory
		JSONArray incomeDataArr2 = new JSONArray();
		for (String income_Type : income_Type1) {
			incomeDataArr2.add(income_Type);
		}
		incomeDataObj2.put("value", incomeDataArr2);

		JSONObject incomeDataObj3 = new JSONObject();
		incomeDataObj3.put("name", "percentage");// Mandatory if type is percentage
		JSONArray incomeDataArr3 = new JSONArray();
		for (String income_Percentage : income_Percentage1) {
			/*
			 * if (income_Percentage!= null || income_Percentage!="") { Float
			 * dIncome_Percentage = Float.parseFloat(income_Percentage); income_Percentage =
			 * String.format("%.3f",dIncome_Percentage / 100); //income_Percentage =
			 * String.valueOf(dIncome_Percentage / 100); }
			 */
			income_Percentage = decimalFloatValue(income_Percentage);
			incomeDataArr3.add(income_Percentage);
			// incomeDataArr3.add(income_Percentage);
		}
		incomeDataObj3.put("value", incomeDataArr3);

		JSONObject incomeDataObj4 = new JSONObject();
		incomeDataObj4.put("name", "absolute_exposure");// Mandatory if type is Absolute Income
		JSONArray incomeDataArr4 = new JSONArray();
		for (String income_Absolute : income_Absolute1) {
			incomeDataArr4.add(income_Absolute);
		}
		incomeDataObj4.put("value", incomeDataArr4);

		// start changes by mohit 13-06-2024 for facilities --> Compulsory field
		// facilities is missing
		JSONObject incomeDataObj5 = new JSONObject();
		incomeDataObj5.put("name", "facilities");// Mandatory
		incomeDataObj5.put("value", facilitiesValArr1);
		// end changes by mohit 13-06-2024 for facilities --> Compulsory field
		// facilities is missing

		incomeTypeArr.add(incomeDataObj1);
		incomeTypeArr.add(incomeDataObj2);
		incomeTypeArr.add(incomeDataObj3);
		incomeTypeArr.add(incomeDataObj4);
		incomeTypeArr.add(incomeDataObj5);
		formDataObj.put("incomes", incomeTypeArr);
		/*
		 * incomeTypeObj.put("incomes", incomeTypeArr); formDataArr.add(incomeTypeObj);
		 */// 06-07-2024

		/*
		 * //start changes by mohit 11-06-2024 for other section as value should not be
		 * array JSONObject otherDataObj1 = new JSONObject(); otherDataObj1.put("name",
		 * "average_balance"); JSONArray otherDataArr1 = new JSONArray(); for (String
		 * average_balance : f_average_balance) { otherDataArr1.add(average_balance); }
		 * otherDataObj1.put("value", otherDataArr1);
		 * 
		 * JSONObject otherDataObj2 = new JSONObject(); otherDataObj2.put("name",
		 * "forex_income"); JSONArray otherDataArr2 = new JSONArray(); for (String
		 * forex_income : f_forex_income) { otherDataArr2.add(forex_income); }
		 * otherDataObj2.put("value", otherDataArr2);
		 * 
		 * JSONObject otherDataObj3 = new JSONObject(); otherDataObj3.put("name",
		 * "commission_export_letter_of_credit"); JSONArray otherDataArr3 = new
		 * JSONArray(); for (String commission_export : f_commission_export) {
		 * otherDataArr3.add(commission_export); } otherDataObj3.put("value",
		 * otherDataArr3);
		 * 
		 * JSONObject otherDataObj4 = new JSONObject(); otherDataObj4.put("name",
		 * "other_income"); JSONArray otherDataArr4 = new JSONArray(); for (String
		 * other_income : f_other_income) { otherDataArr4.add(other_income); }
		 * otherDataObj4.put("value", otherDataArr4);
		 * 
		 * JSONObject otherDataObj5 = new JSONObject(); otherDataObj5.put("name",
		 * "fixed_deposit_income"); JSONArray otherDataArr5 = new JSONArray(); for
		 * (String fixed_deposit : f_fixed_deposit) { otherDataArr5.add(fixed_deposit);
		 * } otherDataObj5.put("value", otherDataArr5);
		 * 
		 * otherTypeArr.add(otherDataObj1); otherTypeArr.add(otherDataObj2);
		 * otherTypeArr.add(otherDataObj3); otherTypeArr.add(otherDataObj4);
		 * otherTypeArr.add(otherDataObj5); otherTypeObj.put("others", otherTypeArr);
		 */

		JSONObject otherTypeObj1 = new JSONObject();
		otherTypeObj1.put("name", "average_balance");
		otherTypeObj1.put("value", fAverageBalance);

		JSONObject otherTypeObj2 = new JSONObject();
		otherTypeObj2.put("name", "forex_income");
		otherTypeObj2.put("value", fForexIncome);

		JSONObject otherTypeObj3 = new JSONObject();
		otherTypeObj3.put("name", "commission_export_letter_of_credit");
		otherTypeObj3.put("value", fCommissionExport);

		JSONObject otherTypeObj4 = new JSONObject();
		otherTypeObj4.put("name", "other_income");
		otherTypeObj4.put("value", fOtherIncome);

		JSONObject otherTypeObj5 = new JSONObject();
		otherTypeObj5.put("name", "fixed_deposit_income");
		otherTypeObj5.put("value", fFixedDeposit);

		JSONObject otherTypeObj6 = new JSONObject();
		otherTypeObj6.put("name", "assessment_date");// Mandatory
		otherTypeObj6.put("value", assessmentDate);

		otherTypeArr.add(otherTypeObj1);
		otherTypeArr.add(otherTypeObj2);
		otherTypeArr.add(otherTypeObj3);
		otherTypeArr.add(otherTypeObj4);
		otherTypeArr.add(otherTypeObj5);
		otherTypeArr.add(otherTypeObj6);
		formDataObj.put("others", otherTypeArr);
		/*
		 * otherTypeObj.put("others",otherTypeArr); formDataArr.add(otherTypeObj);
		 */ // 06-07-2024
		// end changes by mohit 11-06-2024 for other section

		customerRequestObject.put("fileData", filesInfoArr);
		customerRequestObject.put("formData", formDataObj);// 06-07-2024
		// customerRequestObject.put("formData", formDataArr);
		log.info("inside customerRequestObject>>>>>>" + customerRequestObject);

		try {
			inputXML = "<EthixInputRequest>" + "<CallName>" + CallName + "</CallName>" + "<IsREST>Y</IsREST>"
					+ "<Format>JSON</Format>" + "<DirectDataBaseFlag>N</DirectDataBaseFlag>"
					+ "<ResponseRequiredInXML>Y</ResponseRequiredInXML>" + "<RequestObject>"
					+ customerRequestObject.toJSONString() + "</RequestObject>" + "<ProcessName>" + processName
					+ "</ProcessName>" + "<WIName>" + wiName + "</WIName>" + "<MsgId>" + getNextT24Sequence()
					+ "</MsgId>" + "<UserId>" + getUserIndex(username) + "</UserId>" + "<UserName>" + username
					+ "</UserName>" + "<WorkStep>" + workstepName + "</WorkStep>" + "</EthixInputRequest>";

			if (inputXML.contains("&")) {
				inputXML = inputXML.replace("&", "&amp;");
			}
			log.info("execCustomerProjected...inputXML==>" + inputXML);
			T24_Integration t24integration = new T24_Integration(processName, wiName);
			log.info("t24integration..." + t24integration);
			String responseXML = t24integration.executeIntegrationCallProj(inputXML);

			log.info("execCustomerProjected...outputXML===>> " + responseXML);
			/*
			 * String outputXml =
			 * "<CBS_RESPONSE><STATUS_CODE>100</STATUS_CODE><STATUS_DESC>Success</STATUS_DESC><STATUS_REMARKS></STATUS_REMARKS><RESPONSE><projectedRarocResponse>{\r\n"
			 * + "  \"exceptionDetails\": {\r\n" +
			 * "    \"successIndicator\": \"Success\",\r\n" + "    \"messages\": []\r\n" +
			 * "  },\r\n" + "  \"body\": {\r\n" +
			 * "    \"projectedRarocDetailsResponse\": {\r\n" +
			 * "      \"facilities\": [\r\n" + "        {\r\n" +
			 * "          \"country\": \"UNITED ARAB EMIRATES\",\r\n" +
			 * "          \"orr\": \"3\",\r\n" + "          \"facility\": \"Fac4\",\r\n" +
			 * "          \"tenure\": \"24\",\r\n" + "          \"product\": \"CDS\",\r\n" +
			 * "          \"limit\": \"500000\",\r\n" +
			 * "          \"utilisedAmount\": \"0\",\r\n" +
			 * "          \"interestCharge\": \"0\",\r\n" +
			 * "          \"ftpRate\": \"0\",\r\n" +
			 * "          \"commitmentFeeAnnual\": \"0\",\r\n" +
			 * "          \"upfrontFeeAnnual\": \"0\",\r\n" +
			 * "          \"riskAdjustedCapitalAnnual\": \"10676.10402190678\",\r\n" +
			 * "          \"expectedCreditLossAnnual\": \"0\",\r\n" +
			 * "          \"riskAdjustedReturnAnnual\": \"41000\",\r\n" +
			 * "          \"rarocAnnual\": \"3.8403522404680817\",\r\n" +
			 * "          \"cashOrEquivalentValue\": \"\",\r\n" +
			 * "          \"receivablesName\": \"\"\r\n" + "        },\r\n" +
			 * "        {\r\n" + "          \"country\": \"UNITED ARAB EMIRATES\",\r\n" +
			 * "          \"orr\": \"3\",\r\n" + "          \"facility\": \"fac01\",\r\n" +
			 * "          \"tenure\": \"24\",\r\n" +
			 * "          \"product\": \"Overdraft Corp Rev\",\r\n" +
			 * "          \"limit\": \"200000\",\r\n" +
			 * "          \"utilisedAmount\": \"140000\",\r\n" +
			 * "          \"interestCharge\": \"0.101356875\",\r\n" +
			 * "          \"ftpRate\": \"0.058616876939146245\",\r\n" +
			 * "          \"commitmentFeeAnnual\": \"0\",\r\n" +
			 * "          \"upfrontFeeAnnual\": \"5006.839945280438\",\r\n" +
			 * "          \"riskAdjustedCapitalAnnual\": \"10364.782935099847\",\r\n" +
			 * "          \"expectedCreditLossAnnual\": \"61.843885577743144\",\r\n" +
			 * "          \"riskAdjustedReturnAnnual\": \"20660.525730881574\",\r\n" +
			 * "          \"rarocAnnual\": \"1.9933389691081402\",\r\n" +
			 * "          \"cashOrEquivalentValue\": \"50000\",\r\n" +
			 * "          \"receivablesName\": \"\"\r\n" + "        },\r\n" +
			 * "        {\r\n" + "          \"country\": \"UNITED ARAB EMIRATES\",\r\n" +
			 * "          \"orr\": \"3\",\r\n" + "          \"facility\": \"Fac3\",\r\n" +
			 * "          \"tenure\": \"36\",\r\n" +
			 * "          \"product\": \"LC Imp Sight Rev\",\r\n" +
			 * "          \"limit\": \"200000\",\r\n" +
			 * "          \"utilisedAmount\": \"140000\",\r\n" +
			 * "          \"interestCharge\": \"0.050729166666666665\",\r\n" +
			 * "          \"ftpRate\": \"0.050588\",\r\n" +
			 * "          \"commitmentFeeAnnual\": \"1402.5547445255474\",\r\n" +
			 * "          \"upfrontFeeAnnual\": \"0\",\r\n" +
			 * "          \"riskAdjustedCapitalAnnual\": \"8003.284654\",\r\n" +
			 * "          \"expectedCreditLossAnnual\": \"7.401411737\",\r\n" +
			 * "          \"riskAdjustedReturnAnnual\": \"10392.521127309454\",\r\n" +
			 * "          \"rarocAnnual\": \"1.2985319873899186\",\r\n" +
			 * "          \"cashOrEquivalentValue\": \"21000\",\r\n" +
			 * "          \"receivablesName\": \"\"\r\n" + "        },\r\n" +
			 * "        {\r\n" + "          \"country\": \"UNITED ARAB EMIRATES\",\r\n" +
			 * "          \"orr\": \"3\",\r\n" + "          \"facility\": \"FAC2\",\r\n" +
			 * "          \"tenure\": \"48\",\r\n" +
			 * "          \"product\": \"Overdraft Corp Rev\",\r\n" +
			 * "          \"limit\": \"500000\",\r\n" +
			 * "          \"utilisedAmount\": \"500000\",\r\n" +
			 * "          \"interestCharge\": \"0.074774792\",\r\n" +
			 * "          \"ftpRate\": \"0.064953799\",\r\n" +
			 * "          \"commitmentFeeAnnual\": \"0\",\r\n" +
			 * "          \"upfrontFeeAnnual\": \"0\",\r\n" +
			 * "          \"riskAdjustedCapitalAnnual\": \"19265.15847468267\",\r\n" +
			 * "          \"expectedCreditLossAnnual\": \"256.24463591999194\",\r\n" +
			 * "          \"riskAdjustedReturnAnnual\": \"5361.897988859675\",\r\n" +
			 * "          \"rarocAnnual\": \"0.278320991\",\r\n" +
			 * "          \"cashOrEquivalentValue\": \"\",\r\n" +
			 * "          \"receivablesName\": \"\"\r\n" + "        }\r\n" + "      ],\r\n"
			 * + "      \"customer\": {\r\n" + "        \"customerName\": \"cust1\",\r\n" +
			 * "        \"customerCode\": \"122456\",\r\n" +
			 * "        \"crossSellIncome\": \"26012.237\",\r\n" +
			 * "        \"rarocWithCrossSellIncome\": \"1.1799645994209735\",\r\n" +
			 * "        \"totalLimitFunded\": \"\",\r\n" +
			 * "        \"totalLimitNonFunded\": \"\"\r\n" + "      },\r\n" +
			 * "      \"groupProjectedRaroc\": \"1.1799645994209735\"\r\n" + "    }\r\n" +
			 * "  }\r\n" + "}</projectedRarocResponse></RESPONSE></CBS_RESPONSE>";
			 * //System.out.println(res); log.info("execCustomerProjected : outputXml===>> "
			 * + outputXml);
			 */
			msg = setT24CallData.setCustomerProjectedData(iformrefrence, wiName, responseXML, inputXML,
					requestDateTime);
			log.info("msg....." + msg);
		} catch (Exception e) {
			log.error("Exception : ", e);
		}
		// }
		return msg;
	}

	// START EDIT BY MOHIT 25-07-2024
	public String execCustomerProjectedSens(IFormReference iformrefrence) {
		String inputXML = "";
		String msg = "";
		log.info("execCustomerProjected : INSIDE ");

		String processName = iformrefrence.getProcessName();
		log.info("processName : INSIDE " + processName);
		String CallName = "execCustomerProjected";
		String username = iformrefrence.getUserName();
		String wiName = iformrefrence.getObjGeneralData().getM_strProcessInstanceId();
		String workstepName = iformrefrence.getActivityName();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_SQL);
		Date currentDate = new Date();
		String requestDate = simpleDateFormat.format(currentDate);

		SimpleDateFormat DateTimeFormat = new SimpleDateFormat(Constants.REQ_DATE_TIME_FORMAT);
		String requestDateTime = DateTimeFormat.format(currentDate);
		String code = iformrefrence.getValue("LEAD_REF_NO").toString();
		String name = iformrefrence.getValue("CUSTOMER_NAME").toString();
		String country = iformrefrence.getValue("Q_NG_RAROC_CUSTOMER_customer_country_Sensitized").toString();
		// String country = workstepName.equalsIgnoreCase("BUSINESS") ?
		// iformrefrence.getValue("Q_NG_RAROC_CUSTOMER_customer_country_Proposed").toString():
		// iformrefrence.getValue("Q_NG_RAROC_CUSTOMER_customer_country_RECOMMENDED").toString();
		String internalRatingCust = iformrefrence.getValue("Q_NG_RAROC_CUSTOMER_internal_rating_Sensitized").toString();
		String extRating = iformrefrence.getValue("Q_NG_RAROC_CUSTOMER_external_rating_Sensitized").toString();
		String industrySector = iformrefrence.getValue("Q_NG_RAROC_CUSTOMER_Industry_Segement_sensitized").toString();
		String salesTurnover = iformrefrence.getValue("Q_NG_RAROC_CUSTOMER_External_Code_Sensitized").toString();
		String segment = iformrefrence.getValue("Q_NG_RAROC_CUSTOMER_Business_segment_sensitized").toString();
		String groupId = iformrefrence.getValue("Q_NG_RAROC_CUSTOMER_DETAILS_Group_ID").toString();
		String groupName = iformrefrence.getValue("Q_NG_RAROC_CUSTOMER_DETAILS_Group_Name").toString();
		String assessmentDate = requestDate;
		// start changes by mohit 03-07-2024 for CUSTOMER: Missing Field region
		String sQueryRegion = "SELECT EST_PLACE FROM " + CA_CUSTOMER_DETAILS
				+ " WHERE  wi_name =(select ca_wi_name from ALM_RAROC_EXT_TABLE WHERE wi_name = '" + wiName + "')";
		/*
		 * List<List<String>> sOutputRegion = formObject.getDataFromDB(sQueryRegion);
		 * log.info("sOutputRegion...:" + sOutputRegion); String region =
		 * sOutputRegion.get(0).get(0); log.info("region...:" + region);
		 */
		String region = "";
		String outputXMLregion = getDataFromDBWithColumns(sQueryRegion, "EST_PLACE");
		XMLParser xmlParserregion = new XMLParser(outputXMLregion);
		log.info("xmlParser...." + xmlParserregion);
		int totalRetrievedregion = Integer.parseInt(xmlParserregion.getValueOf(Constants.TOTAL_RETRIEVED));
		if (totalRetrievedregion == 1) {
			region = xmlParserregion.getValueOf("EST_PLACE");
		}
		// end changes by mohit 03-07-2024 for CUSTOMER: Missing Field region

		// log.info("facilityId : INSIDE "+facilityId);
		log.info("requestDate : " + requestDate);
		String column = "COMMITMENT_NO,TREASURY_PRODUCT,FACILITY_TYPE,FACILITY_NAME,Repayment_Type_Sensitized,Repayment_Frequency_Sensitized,"
				+ "Tenor_Sensitized,Moratorium_Period_Sensitized,Currency_Sensitized,Index_Sensitized,Index_Rate_Sensitized,Tenor_unit_Sensitized,"
				+ "Margin_Commision_Sensitized,Cash_Margin_Sensitized,Credit_Limit_Sensitized,EXPECTED_UTILISATION,SENIOR_SUBORDINATE,"
				+ "COMMITTED_UNCOMMITTED,Facility_Amount_Sensitized,Expected_year_Return_Sensitized,Marketing_Risk_Capital_Sensitized,"
				+ "REPRICING_FERQUENCY_SENITISED,FTP_RATE_SENITISED,COLLATERAL_NAME_SENITISED,COLLATERAL_TYPE_SENITISED,COLLATERAL_AMOUNT_SENITISED,"
				+ "COLLATERAL_CURRENCY_SENITISED,COLLATERAL_LEAN_AMOUNT_SENITISED,COLLATERAL_LEAN_INTEREST_SENITISED,COLLATERAL_LEAN_TENURE_SENITISED,"
				+ "INCOME_NAME,INCOME_TYPE,INCOME_PERCENTAGE,INCOME_ABSOLUTE,"
				+ "INDEX_KEY_SENSITIZED,INDEX_TENOR_SENSITIZED,INDEX_TENOR_UNIT_SENSITIZED,"
				+ "Minimum_Sensitized,Allocation_Percentage_Sensitized,Utilization_Sensitized,facility_id,Value_Sensitized,Ftp_Override_Sensitized";
		String query = "SELECT " + column + " FROM NG_RAROC_FACILITY_DETAILS A WHERE A.WI_NAME='" + wiName
				+ "' and Facility_Amount_sensitized<>'0'  AND Facility_Amount_sensitized <>'' AND Facility_Amount_sensitized IS NOT NULL "
				+ " and MAIN_SUB_LIMIT='Main Limit'";// prod_11122024";//prod_11022025
		log.info("query>>>>>> : " + query);
		String outputXML = getDataFromDBWithColumns(query, column);
		XMLParser xmlParser = new XMLParser(outputXML);
		log.info("xmlParser" + xmlParser);
		int totalRetrieved = Integer.parseInt(xmlParser.getValueOf(Constants.TOTAL_RETRIEVED));

		List<String> name_value1 = new ArrayList<>();
		List<String> otc_flag_value1 = new ArrayList<>();
		List<String> type_value1 = new ArrayList<>();
		List<String> product_type_value1 = new ArrayList<>();
		List<String> repayment_mode_value1 = new ArrayList<>();
		List<String> repayment_frequency_value1 = new ArrayList<>();
		List<String> tenor_value1 = new ArrayList<>();
		List<String> moratorium_period_value1 = new ArrayList<>();
		List<String> currency_value1 = new ArrayList<>();
		List<String> index_value1 = new ArrayList<>();
		List<String> index_tenor_value1 = new ArrayList<>();
		List<String> tenor_unit_value1 = new ArrayList<>();
		List<String> margin_value1 = new ArrayList<>();
		List<String> valueProp_value1 = new ArrayList<>();
		List<String> commission_rate_value1 = new ArrayList<>();
		List<String> limit_value1 = new ArrayList<>();
		List<String> utilisation_value1 = new ArrayList<>();
		List<String> facility_position_value1 = new ArrayList<>();
		List<String> commitment_type_value1 = new ArrayList<>();
		List<String> csv_path_value1 = new ArrayList<>();
		List<String> notional_amount_value1 = new ArrayList<>();
		List<String> expected_1_year_return_type_value1 = new ArrayList<>();
		List<String> market_risk_capital_value1 = new ArrayList<>();
		List<String> ftp_override_value1 = new ArrayList<>();
		List<String> facilityId_value1 = new ArrayList<>();
		List<String> repricing_frequency_value1 = new ArrayList<>();
		List<String> ftp_rate_value1 = new ArrayList<>();
		List<String> interest_floor_value1 = new ArrayList<>();
		List<String> allocation_Perc_Prop_value1 = new ArrayList<>();

		List<String> collateral_Name1 = new ArrayList<>();
		List<String> collateral_No1 = new ArrayList<>();
		List<String> collateral_Type1 = new ArrayList<>();
		List<String> collateral_Amount1 = new ArrayList<>();
		List<String> collateral_Currency1 = new ArrayList<>();
		List<String> newCollateralType1 = new ArrayList<>();
		List<String> collateral_Lean1 = new ArrayList<>();
		List<String> collateral_LeanInterest1 = new ArrayList<>();
		List<String> collateral_LeanTenor1 = new ArrayList<>();
		List<String> crmCollateral_cash_margin1 = new ArrayList<>();
		List<String> crmCollateral_haircut1 = new ArrayList<>();
		List<String> income_Name1 = new ArrayList<>();
		List<String> income_Type1 = new ArrayList<>();
		List<String> income_Percentage1 = new ArrayList<>();
		List<String> income_Absolute1 = new ArrayList<>();

		/*
		 * //start changes by mohit 11-06-2024 for other section List<String>
		 * f_average_balance = new ArrayList<>(); List<String> f_forex_income = new
		 * ArrayList<>(); List<String> f_commission_export = new ArrayList<>();
		 * List<String> f_other_income = new ArrayList<>(); List<String> f_fixed_deposit
		 * = new ArrayList<>(); //end changes by mohit 11-06-2024 for other section
		 */
		String commitmentNo = ""; // also used for COLLATERAL_FACILITY_MAP
		String facilityId = "";
		String TreasuryProposed = "";
		String UtilizationPerc = "";
		String facilitytype = "";
		String facilityName = ""; // also used for COLLATERAL_FACILITY_MAP
		String repaymentTyprProp = "";
		String repFreqProp = "";
		String tenureProposed = "";
		String moratoriumProp = "";
		String currencyProp = "";
		String indexProp = "";
		String indexRateProp = "";
		String tenorUnitProp = "";
		String margin = "";
		String valueProp = "";
		String marginCommisionProp = "";
		String creditLimitProp = "";
		String expUtilisationProp = "";
		String seniorSubProp = "";
		String committedUncommited = "";
		String facilityAmntProp = "";
		String expReturnProp = "";
		String marketingRiskCapProp = "";
		String repricingFreqProp = "";
		String ftpOverride = "";
		String ftpRate = "";
		String interestFloor = "";

		String collateralName = "";
		String collateralNo = "";
		String commitment = "";
		String collateralType = "";
		String collateralAmount = "";
		String collateralCurrency = "";
		String newCollateralType = "";
		String collateralLean = "";
		String collateralLeanInterest = "";
		String collateralLeanTenor = "";
		String crmCollateral_cash_margin = "";
		String crmCollateral_haircut = "";
		String incomeName = "";
		String incomeType = "";
		String incomePercentage = "";
		String incomeAbsolute = "";

		// start changes by mohit 11-06-2024 for other section
		String fAverageBalance = "";
		String fForexIncome = "";
		String fCommissionExport = "";
		String fOtherIncome = "";
		String fFixedDeposit = "";
		// Boolean bTreasuryProposed= null; //value should be boolean true/ false
		// List<Boolean> bOtc_flag_value1 = new ArrayList<>();
		String sHardCodeValue = "";// mohitsHardCodeValue
		String allocationPercProp = ""; // 03-07-2024 by mohit for COLLATERAL_FACILITY_MAP
		String fCollateralNumberProposed = "";
		// end changes by mohit 11-06-2024 for other section
		fAverageBalance = iformrefrence.getValue("F_AVERAGE_BALANCE").toString();
		fForexIncome = iformrefrence.getValue("F_FOREX_INCOME").toString();
		fCommissionExport = iformrefrence.getValue("F_COMMISSION_EXPORT").toString();
		fOtherIncome = iformrefrence.getValue("F_OTHER_INCOME").toString();
		fFixedDeposit = iformrefrence.getValue("F_FIXED_DEPOSIT").toString();
		if (totalRetrieved != 0) {
			for (int i = 0; i < totalRetrieved; i++) {
				String row = xmlParser.getNextValueOf(Constants.ROW);
				XMLParser rowParser = new XMLParser(row);
				log.info("inside totalRetrieve>>>>>>");
				commitmentNo = rowParser.getValueOf("COMMITMENT_NO");
				facilityId = rowParser.getValueOf("facility_id");
				TreasuryProposed = rowParser.getValueOf("TREASURY_PRODUCT");// Y,N
				UtilizationPerc = rowParser.getValueOf("Utilization_Sensitized");
				log.info("UtilizationPerc...." + UtilizationPerc + "+++");
				facilitytype = rowParser.getValueOf("FACILITY_TYPE");
				facilityName = rowParser.getValueOf("FACILITY_NAME");
				repaymentTyprProp = rowParser.getValueOf("Repayment_Type_Sensitized");// mapping is interchanged on
																						// form hence using this
				repFreqProp = rowParser.getValueOf("Repayment_Frequency_Sensitized");
				tenureProposed = rowParser.getValueOf("Tenor_Sensitized");
				log.info("tenureProposed..." + tenureProposed + "+++");
				moratoriumProp = rowParser.getValueOf("Moratorium_Period_Sensitized");
				currencyProp = rowParser.getValueOf("Currency_Sensitized");
				// indexProp=rowParser.getValueOf("Index_Proposed");
				// indexRateProp=rowParser.getValueOf("Index_Rate_Proposed");
				// tenorUnitProp=rowParser.getValueOf("Tenor_unit_Proposed");
				indexProp = rowParser.getValueOf("INDEX_KEY_SENSITIZED");
				indexRateProp = rowParser.getValueOf("INDEX_TENOR_SENSITIZED");
				tenorUnitProp = rowParser.getValueOf("INDEX_TENOR_UNIT_SENSITIZED");

				margin = rowParser.getValueOf("Cash_Margin_Sensitized");
				valueProp = rowParser.getValueOf("Value_Sensitized");
				marginCommisionProp = rowParser.getValueOf("Margin_Commision_Sensitized");
				// creditLimitProp=rowParser.getValueOf("Credit_Limit_Proposed");by mohit
				// 01-07-2024 Limit is FAcility Amt ;Limit is not credit Limit
				creditLimitProp = rowParser.getValueOf("Facility_Amount_Sensitized");
				expUtilisationProp = rowParser.getValueOf("EXPECTED_UTILISATION");
				seniorSubProp = rowParser.getValueOf("SENIOR_SUBORDINATE");
				committedUncommited = rowParser.getValueOf("COMMITTED_UNCOMMITTED");
				facilityAmntProp = rowParser.getValueOf("Facility_Amount_Sensitized");
				expReturnProp = rowParser.getValueOf("Expected_year_Return_Sensitized");
				marketingRiskCapProp = rowParser.getValueOf("Marketing_Risk_Capital_Sensitized");
				repricingFreqProp = rowParser.getValueOf("REPRICING_FERQUENCY_SENITISED");
				ftpOverride = rowParser.getValueOf("Ftp_Override_Sensitized");
				ftpRate = rowParser.getValueOf("FTP_RATE_SENITISED");
				interestFloor = rowParser.getValueOf("Minimum_Sensitized");// 03-07-2024 by mohit for FACILITY: Missing
																			// Field interest_floor

				// allocationPercProp =rowParser.getValueOf("Allocation_Percentage_Proposed");//
				// 03-07-2024 by mohit for COLLATERAL_FACILITY_MAP

				// collateralName =rowParser.getValueOf("COLLATERAL_NAME_PROPOSED");
				// collateralNo =rowParser.getValueOf("COLLATERAL_NUMBER_PROPOSED");
				// collateralType =rowParser.getValueOf("COLLATERAL_TYPE_PROPOSED");
				// collateralAmount =rowParser.getValueOf("COLLATERAL_AMOUNT_PROPOSED");
				// collateralCurrency =rowParser.getValueOf("COLLATERAL_CURRENCY_PROPOSED");
				collateralLean = rowParser.getValueOf("COLLATERAL_LEAN_AMOUNT_SENITISED");
				collateralLeanInterest = rowParser.getValueOf("COLLATERAL_LEAN_INTEREST_SENITISED");
				collateralLeanTenor = rowParser.getValueOf("COLLATERAL_LEAN_TENURE_SENITISED");
				crmCollateral_cash_margin = rowParser.getValueOf(""); // mvalue
				crmCollateral_haircut = rowParser.getValueOf("");
				incomeName = rowParser.getValueOf("INCOME_NAME");
				incomeType = rowParser.getValueOf("INCOME_TYPE");
				incomePercentage = rowParser.getValueOf("INCOME_PERCENTAGE");
				incomeAbsolute = rowParser.getValueOf("INCOME_ABSOLUTE");

				// start changes by mohit 11-06-2024 for other section
				/*
				 * fAverageBalance =rowParser.getValueOf("AVERAGE_BALANCE"); fForexIncome =
				 * rowParser.getValueOf("FOREX_INCOME"); fCommissionExport
				 * =rowParser.getValueOf("COMMISSION_EXPORT"); fOtherIncome
				 * =rowParser.getValueOf("OTHER_INCOME"); fFixedDeposit
				 * =rowParser.getValueOf("FIXED_DEPOSIT");
				 */

				if (TreasuryProposed.equalsIgnoreCase("YES") || TreasuryProposed.equalsIgnoreCase("Y")) {
					// bTreasuryProposed = true;
					TreasuryProposed = "True";
				} else {
					// bTreasuryProposed =false;
					TreasuryProposed = "False";
				}
				// end changes by mohit 11-06-2024 for other section
				// start edit by mohit 16-08-2024
				log.info("SENS before yes and non-funded..type_value1.." + type_value1);
				if ((TreasuryProposed.equalsIgnoreCase("YES") || TreasuryProposed.equalsIgnoreCase("Y")
						|| TreasuryProposed.equalsIgnoreCase("True")) && facilitytype.equalsIgnoreCase("Non-Funded")) {
					log.info("Inside yes and non-funded SENS... ");
					facilitytype = "OTC-Products";
					// start edit by mohit 21-08-2024
					repaymentTyprProp = null;
					repFreqProp = null;
					moratoriumProp = null;
					indexProp = null;
					indexRateProp = null;
					tenorUnitProp = null;
					marginCommisionProp = null;
					valueProp = null;
					creditLimitProp = null;
					UtilizationPerc = null;
					seniorSubProp = null;
					committedUncommited = null;
					marketingRiskCapProp = null;
					repricingFreqProp = null;
					interestFloor = null;
					// end edit by mohit 21-08-2024
				}
				// end edit by mohiy 16-08-2024
				name_value1.add(commitmentNo);
				facilityId_value1.add(facilityId);
				otc_flag_value1.add(TreasuryProposed);
				// bOtc_flag_value1.add(bTreasuryProposed);// value should be String "true"/
				// "false"
				type_value1.add(facilitytype);
				product_type_value1.add(facilityName);
				repayment_mode_value1.add(repaymentTyprProp);
				repayment_frequency_value1.add(repFreqProp);
				tenor_value1.add(tenureProposed);
				moratorium_period_value1.add(moratoriumProp);
				currency_value1.add(currencyProp);
				index_value1.add(indexProp);
				index_tenor_value1.add(indexRateProp);
				tenor_unit_value1.add(tenorUnitProp);
				margin_value1.add(margin);
				valueProp_value1.add(valueProp);
				commission_rate_value1.add(marginCommisionProp);
				limit_value1.add(creditLimitProp);
				// utilisation_value1.add(expUtilisationProp);
				utilisation_value1.add(UtilizationPerc);
				facility_position_value1.add(seniorSubProp);
				commitment_type_value1.add(committedUncommited);
				csv_path_value1.add(""); // value not provided
				notional_amount_value1.add(facilityAmntProp);
				expected_1_year_return_type_value1.add(expReturnProp);
				market_risk_capital_value1.add(marketingRiskCapProp);
				ftp_override_value1.add(ftpOverride);
				repricing_frequency_value1.add(repricingFreqProp);
				ftp_rate_value1.add(ftpRate);
				log.info("inside ftp_rate_value1....");
				interest_floor_value1.add(interestFloor);// 03-07-2024 by mohit for FACILITY: Missing Field
															// interest_floor
				// allocation_Perc_Prop_value1.add(allocationPercProp);

				// collateral_Name1.add(collateralName);
				// collateral_No1.add(collateralNo);
				// collateral_Type1.add(collateralType);
				// collateral_Amount1.add(collateralAmount);
				// collateral_Currency1.add(collateralCurrency);
				collateral_Lean1.add(collateralLean);
				collateral_LeanInterest1.add(collateralLeanInterest);
				collateral_LeanTenor1.add(collateralLeanTenor);
				crmCollateral_haircut1.add(crmCollateral_haircut);
				crmCollateral_cash_margin1.add(crmCollateral_cash_margin);
				income_Name1.add(incomeName);
				income_Type1.add(incomeType);
				income_Percentage1.add(incomePercentage);
				income_Absolute1.add(incomeAbsolute);

				/*
				 * //start changes by mohit 11-06-2024 for other section
				 * f_average_balance.add(fAverageBalance); f_forex_income.add(fForexIncome);
				 * f_commission_export.add(fCommissionExport); f_other_income.add(fOtherIncome);
				 * f_fixed_deposit.add(fFixedDeposit); //end changes by mohit 11-06-2024 for
				 * other section
				 */

			}
		}

		// start edit by mohit 11-02-2025 //prod_11022025 only Main Limits and those sub limits which will be modified will be used for RAROC calculation
		log.info("13-02-2025 @@@@@@");
		String columnssb = "COMMITMENT_NO,TREASURY_PRODUCT,FACILITY_TYPE,FACILITY_NAME,Repayment_Type_Sensitized,Repayment_Frequency_Sensitized,"
				+ "Tenor_Sensitized,Moratorium_Period_Sensitized,Currency_Sensitized,Index_Sensitized,Index_Rate_Sensitized,Tenor_unit_Sensitized,"
				+ "Margin_Commision_Sensitized,Cash_Margin_Sensitized,Credit_Limit_Sensitized,EXPECTED_UTILISATION,SENIOR_SUBORDINATE,"
				+ "COMMITTED_UNCOMMITTED,Facility_Amount_Sensitized,Expected_year_Return_Sensitized,Marketing_Risk_Capital_Sensitized,"
				+ "REPRICING_FERQUENCY_SENITISED,FTP_RATE_SENITISED,COLLATERAL_NAME_SENITISED,COLLATERAL_TYPE_SENITISED,COLLATERAL_AMOUNT_SENITISED,"
				+ "COLLATERAL_CURRENCY_SENITISED,COLLATERAL_LEAN_AMOUNT_SENITISED,COLLATERAL_LEAN_INTEREST_SENITISED,COLLATERAL_LEAN_TENURE_SENITISED,"
				+ "INCOME_NAME,INCOME_TYPE,INCOME_PERCENTAGE,INCOME_ABSOLUTE,"
				+ "INDEX_KEY_SENSITIZED,INDEX_TENOR_SENSITIZED,INDEX_TENOR_UNIT_SENSITIZED,"
				+ "Minimum_Sensitized,Allocation_Percentage_Sensitized,Utilization_Sensitized,facility_id,Value_Sensitized,Ftp_Override_Sensitized";
		String queryssb = "SELECT " + columnssb + " FROM NG_RAROC_FACILITY_DETAILS A WHERE A.WI_NAME='" + wiName
				+ "' and Facility_Amount_sensitized<>'0'  AND Facility_Amount_sensitized <>'' AND Facility_Amount_sensitized IS NOT NULL "
				+ " and MAIN_SUB_LIMIT <> 'Main Limit' and (TREASURY_PRODUCT <>'' and TREASURY_PRODUCT IS NOT NULL)  and ( FACILITY_TYPE <>'' and FACILITY_TYPE IS NOT NULL ) "
				+" and ( FACILITY_NAME <>'' and  FACILITY_NAME IS NOT NULL ) and "
				+ " (Repayment_Type_Sensitized <>'' and Repayment_Type_Sensitized IS NOT NULL) and (Repayment_Frequency_Sensitized <>'' and Repayment_Frequency_Sensitized IS NOT NULL)"
				+ "  and (Tenor_Sensitized <>'' and Tenor_Sensitized IS NOT NULL) and (Currency_Sensitized <>'' and Currency_Sensitized IS NOT NULL) "
				+ "  and ((FACILITY_TYPE = 'Non-Funded') or (FACILITY_TYPE = 'Funded' AND Index_Sensitized <>'' and Index_Sensitized IS NOT NULL and Index_Rate_Sensitized <>'' and Index_Rate_Sensitized IS NOT NULL)) "//FOr non-funded Index_Proposed and Index_Rate_Proposed is '';// prod_11122024";
				+ "  and (Tenor_unit_Sensitized <>'' and Tenor_unit_Sensitized IS NOT NULL) and (SENIOR_SUBORDINATE <>'' and SENIOR_SUBORDINATE IS NOT NULL) and (COMMITTED_UNCOMMITTED <>'' and COMMITTED_UNCOMMITTED IS NOT NULL) "
				+ " and ((TREASURY_PRODUCT = 'No') or (TREASURY_PRODUCT = 'Yes' AND Expected_year_Return_Sensitized <>'' and Expected_year_Return_Sensitized IS NOT NULL and Marketing_Risk_Capital_Sensitized <>'' and Marketing_Risk_Capital_Sensitized IS NOT NULL)) "
				+ " and (Utilization_Sensitized <>'' and Utilization_Sensitized IS NOT NULL)  and (FACILITY_NAME <>'' and FACILITY_NAME IS NOT NULL) and (Value_Sensitized <>'' and Value_Sensitized IS NOT NULL) "; 
		log.info("queryssb>>>>>> : " + queryssb);
		String outputXMLssb = getDataFromDBWithColumns(queryssb, columnssb);
		XMLParser xmlParserssb = new XMLParser(outputXMLssb);
		log.info("xmlParserssb" + xmlParserssb);
		int totalRetrievedssb = Integer.parseInt(xmlParserssb.getValueOf(Constants.TOTAL_RETRIEVED));
		if (totalRetrievedssb != 0) {
		for (int i = 0; i < totalRetrievedssb; i++) {
			String row = xmlParserssb.getNextValueOf(Constants.ROW);
			XMLParser rowParser = new XMLParser(row);
			log.info("inside totalRetrievessb>>>>>>");
			commitmentNo = rowParser.getValueOf("COMMITMENT_NO");
			facilityId = rowParser.getValueOf("facility_id");
			TreasuryProposed = rowParser.getValueOf("TREASURY_PRODUCT");// Y,N
			UtilizationPerc = rowParser.getValueOf("Utilization_Sensitized");
			log.info("UtilizationPercssb...." + UtilizationPerc + "+++");
			facilitytype = rowParser.getValueOf("FACILITY_TYPE");
			facilityName = rowParser.getValueOf("FACILITY_NAME");
			repaymentTyprProp = rowParser.getValueOf("Repayment_Type_Sensitized");// mapping is interchanged on
																					// form hence using this
			repFreqProp = rowParser.getValueOf("Repayment_Frequency_Sensitized");
			tenureProposed = rowParser.getValueOf("Tenor_Sensitized");
			log.info("tenureProposedssb..." + tenureProposed + "+++");
			moratoriumProp = rowParser.getValueOf("Moratorium_Period_Sensitized");
			currencyProp = rowParser.getValueOf("Currency_Sensitized");
			// indexProp=rowParser.getValueOf("Index_Proposed");
			// indexRateProp=rowParser.getValueOf("Index_Rate_Proposed");
			// tenorUnitProp=rowParser.getValueOf("Tenor_unit_Proposed");
			indexProp = rowParser.getValueOf("INDEX_KEY_SENSITIZED");
			indexRateProp = rowParser.getValueOf("INDEX_TENOR_SENSITIZED");
			tenorUnitProp = rowParser.getValueOf("INDEX_TENOR_UNIT_SENSITIZED");

			margin = rowParser.getValueOf("Cash_Margin_Sensitized");
			valueProp = rowParser.getValueOf("Value_Sensitized");
			marginCommisionProp = rowParser.getValueOf("Margin_Commision_Sensitized");
			// creditLimitProp=rowParser.getValueOf("Credit_Limit_Proposed");by mohit
			// 01-07-2024 Limit is FAcility Amt ;Limit is not credit Limit
			creditLimitProp = rowParser.getValueOf("Facility_Amount_Sensitized");
			expUtilisationProp = rowParser.getValueOf("EXPECTED_UTILISATION");
			seniorSubProp = rowParser.getValueOf("SENIOR_SUBORDINATE");
			committedUncommited = rowParser.getValueOf("COMMITTED_UNCOMMITTED");
			facilityAmntProp = rowParser.getValueOf("Facility_Amount_Sensitized");
			expReturnProp = rowParser.getValueOf("Expected_year_Return_Sensitized");
			marketingRiskCapProp = rowParser.getValueOf("Marketing_Risk_Capital_Sensitized");
			repricingFreqProp = rowParser.getValueOf("REPRICING_FERQUENCY_SENITISED");
			ftpOverride = rowParser.getValueOf("Ftp_Override_Sensitized");
			ftpRate = rowParser.getValueOf("FTP_RATE_SENITISED");
			interestFloor = rowParser.getValueOf("Minimum_Sensitized");// 03-07-2024 by mohit for FACILITY: Missing
																		// Field interest_floor

			// allocationPercProp =rowParser.getValueOf("Allocation_Percentage_Proposed");//
			// 03-07-2024 by mohit for COLLATERAL_FACILITY_MAP

			// collateralName =rowParser.getValueOf("COLLATERAL_NAME_PROPOSED");
			// collateralNo =rowParser.getValueOf("COLLATERAL_NUMBER_PROPOSED");
			// collateralType =rowParser.getValueOf("COLLATERAL_TYPE_PROPOSED");
			// collateralAmount =rowParser.getValueOf("COLLATERAL_AMOUNT_PROPOSED");
			// collateralCurrency =rowParser.getValueOf("COLLATERAL_CURRENCY_PROPOSED");
			collateralLean = rowParser.getValueOf("COLLATERAL_LEAN_AMOUNT_SENITISED");
			collateralLeanInterest = rowParser.getValueOf("COLLATERAL_LEAN_INTEREST_SENITISED");
			collateralLeanTenor = rowParser.getValueOf("COLLATERAL_LEAN_TENURE_SENITISED");
			crmCollateral_cash_margin = rowParser.getValueOf(""); // mvalue
			crmCollateral_haircut = rowParser.getValueOf("");
			incomeName = rowParser.getValueOf("INCOME_NAME");
			incomeType = rowParser.getValueOf("INCOME_TYPE");
			incomePercentage = rowParser.getValueOf("INCOME_PERCENTAGE");
			incomeAbsolute = rowParser.getValueOf("INCOME_ABSOLUTE");

			// start changes by mohit 11-06-2024 for other section
			/*
			 * fAverageBalance =rowParser.getValueOf("AVERAGE_BALANCE"); fForexIncome =
			 * rowParser.getValueOf("FOREX_INCOME"); fCommissionExport
			 * =rowParser.getValueOf("COMMISSION_EXPORT"); fOtherIncome
			 * =rowParser.getValueOf("OTHER_INCOME"); fFixedDeposit
			 * =rowParser.getValueOf("FIXED_DEPOSIT");
			 */

			if (TreasuryProposed.equalsIgnoreCase("YES") || TreasuryProposed.equalsIgnoreCase("Y")) {
				// bTreasuryProposed = true;
				TreasuryProposed = "True";
			} else {
				// bTreasuryProposed =false;
				TreasuryProposed = "False";
			}
			// end changes by mohit 11-06-2024 for other section
			// start edit by mohit 16-08-2024
			log.info("SENS before yes and non-funded..type_value1..ssb" + type_value1);
			if ((TreasuryProposed.equalsIgnoreCase("YES") || TreasuryProposed.equalsIgnoreCase("Y")
					|| TreasuryProposed.equalsIgnoreCase("True")) && facilitytype.equalsIgnoreCase("Non-Funded")) {
				log.info("Inside yes and non-funded SENS...ssb ");
				facilitytype = "OTC-Products";
				// start edit by mohit 21-08-2024
				repaymentTyprProp = null;
				repFreqProp = null;
				moratoriumProp = null;
				indexProp = null;
				indexRateProp = null;
				tenorUnitProp = null;
				marginCommisionProp = null;
				valueProp = null;
				creditLimitProp = null;
				UtilizationPerc = null;
				seniorSubProp = null;
				committedUncommited = null;
				marketingRiskCapProp = null;
				repricingFreqProp = null;
				interestFloor = null;
				// end edit by mohit 21-08-2024
			}
			// end edit by mohiy 16-08-2024
			name_value1.add(commitmentNo);
			facilityId_value1.add(facilityId);
			otc_flag_value1.add(TreasuryProposed);
			// bOtc_flag_value1.add(bTreasuryProposed);// value should be String "true"/
			// "false"
			type_value1.add(facilitytype);
			product_type_value1.add(facilityName);
			repayment_mode_value1.add(repaymentTyprProp);
			repayment_frequency_value1.add(repFreqProp);
			tenor_value1.add(tenureProposed);
			moratorium_period_value1.add(moratoriumProp);
			currency_value1.add(currencyProp);
			index_value1.add(indexProp);
			index_tenor_value1.add(indexRateProp);
			tenor_unit_value1.add(tenorUnitProp);
			margin_value1.add(margin);
			valueProp_value1.add(valueProp);
			commission_rate_value1.add(marginCommisionProp);
			limit_value1.add(creditLimitProp);
			// utilisation_value1.add(expUtilisationProp);
			utilisation_value1.add(UtilizationPerc);
			facility_position_value1.add(seniorSubProp);
			commitment_type_value1.add(committedUncommited);
			csv_path_value1.add(""); // value not provided
			notional_amount_value1.add(facilityAmntProp);
			expected_1_year_return_type_value1.add(expReturnProp);
			market_risk_capital_value1.add(marketingRiskCapProp);
			ftp_override_value1.add(ftpOverride);
			repricing_frequency_value1.add(repricingFreqProp);
			ftp_rate_value1.add(ftpRate);
			log.info("inside ftp_rate_value1ssb....");
			interest_floor_value1.add(interestFloor);// 03-07-2024 by mohit for FACILITY: Missing Field
														// interest_floor
			// allocation_Perc_Prop_value1.add(allocationPercProp);

			// collateral_Name1.add(collateralName);
			// collateral_No1.add(collateralNo);
			// collateral_Type1.add(collateralType);
			// collateral_Amount1.add(collateralAmount);
			// collateral_Currency1.add(collateralCurrency);
			collateral_Lean1.add(collateralLean);
			collateral_LeanInterest1.add(collateralLeanInterest);
			collateral_LeanTenor1.add(collateralLeanTenor);
			crmCollateral_haircut1.add(crmCollateral_haircut);
			crmCollateral_cash_margin1.add(crmCollateral_cash_margin);
			income_Name1.add(incomeName);
			income_Type1.add(incomeType);
			income_Percentage1.add(incomePercentage);
			income_Absolute1.add(incomeAbsolute);

		}
	
	}
		// end edit by mohit 11-02-2025 //prod_11022025 only Main Limits and those sub limits which will be modified will be used for RAROC calculation

		// start edit by mohit 16-07-2024 collateral_facility_map integration from table
		List<String> commitment_value1 = new ArrayList<>();
		String columnCFM = "COLLATERAL_NUMBER_SENS,COMMITMENT_NO,COLLATERAL_ALLOCATION_PERCENTAGE_SENS";
		String queryCFM = "SELECT " + columnCFM + " FROM NG_RAROC_COLLATERAL_FACILITY_MAP WHERE WI_NAME='" + wiName
				+ "'";
		log.info("queryCFM>>>>>> : " + queryCFM);
		String outputXMLCFM = getDataFromDBWithColumns(queryCFM, columnCFM);
		XMLParser xmlParserCFM = new XMLParser(outputXMLCFM);
		log.info("xmlParserCFM" + xmlParserCFM);
		int totalRetrievedCFM = Integer.parseInt(xmlParserCFM.getValueOf(Constants.TOTAL_RETRIEVED));
		log.info("totalRetrievedCFM..." + totalRetrievedCFM);
		if (totalRetrievedCFM != 0) {
			for (int i = 0; i < totalRetrievedCFM; i++) {
				log.info("i====" + i);
				String rowCFM = xmlParserCFM.getNextValueOf(Constants.ROW);
				log.info("rowCFM####" + rowCFM);
				XMLParser rowParserCFM = new XMLParser(rowCFM);

				log.info("before assign..." + rowParserCFM.getValueOf("COLLATERAL_NUMBER_SENS"));
				collateralNo = rowParserCFM.getValueOf("COLLATERAL_NUMBER_SENS");
				log.info("collateralNo..." + collateralNo);
				collateral_No1.add(collateralNo);

				commitment = rowParserCFM.getValueOf("COMMITMENT_NO");
				log.info("commitment..." + commitment);
				commitment_value1.add(commitment);

				allocationPercProp = rowParserCFM.getValueOf("COLLATERAL_ALLOCATION_PERCENTAGE_SENS");
				log.info("allocationPercProp..." + allocationPercProp);
				allocation_Perc_Prop_value1.add(allocationPercProp);

			}
			log.info("collateral_No1..." + collateral_No1);
			log.info("commitment_value1..." + commitment_value1);
			log.info("allocation_Perc_Prop_value1..." + allocation_Perc_Prop_value1);
		}
		// end edit by mohit 16-07-2024 collateral_facility_map integration from table

		// start edit by mohit 18-07-2024 for crm collateral array mohitcrm
		// String columnCRM
		// ="COLLATERAL_NAME_PROP,COLLATERAL_TYPE_PROP,COLLATERAL_AMOUNT_PROP,COLLATERAL_CURRENCY_PROP,New_Collateral_Type";//new
		// col type 22-07-2024
		// String queryCRM = "SELECT "+columnCRM+" FROM NG_RAROC_COLLATERAL_FACILITY_MAP
		// WHERE WI_NAME='"+ wiName+ "'";
		// String columnCustCRM = " a.collateral_number collateral_number,a.SEC_NAME
		// COLLATERAL_TYPE ,a.collateral_amount AS collateral_amount
		// ,a.collateral_currency AS collateral_currency ";
		// String columnFacColCRM = " b.COLLATERAL_NUMBER_PROP AS
		// collateral_number,b.New_Collateral_Type AS
		// COLLATERAL_TYPE,b.COLLATERAL_AMOUNT_PROP AS
		// collateral_amount,b.COLLATERAL_CURRENCY_PROP AS collateral_currency ";
		String columnCustCRM = " a.collateral_number ,a.SEC_NAME  ,a.collateral_amount ,a.collateral_currency ";
		String columnFacColCRM = " b.COLLATERAL_NUMBER_SENS ,b.New_Collateral_Type ,b.COLLATERAL_AMOUNT_SENS ,b.COLLATERAL_CURRENCY_SENS";
		String newColNameCRM = "COLLATERAL_NUMBER,COLLATERAL_TYPE,COLLATERAL_AMOUNT,COLLATERAL_CURRENCY";
		String columnCRM = columnCustCRM + columnFacColCRM;
		String queryCRM = " SELECT " + columnCustCRM + " FROM NG_RAROC_SECURITY_CUSTOMER a WHERE a.WI_NAME='" + wiName
				+ "'" + " UNION  " + " SELECT " + columnFacColCRM
				+ " FROM NG_RAROC_COLLATERAL_FACILITY_MAP b WHERE b.WI_NAME='" + wiName + "'";
		log.info("queryCRM>>>>>> : " + queryCRM);

		String outputXMLCRM = getDataFromDBWithColumnsNew(queryCRM, columnCRM, newColNameCRM);
		XMLParser xmlParserCRM = new XMLParser(outputXMLCRM);
		log.info("xmlParserCRM...test ###mohit@@@@" + xmlParserCRM);
		int totalRetrievedCRM = Integer.parseInt(xmlParserCRM.getValueOf(Constants.TOTAL_RETRIEVED));
		log.info("totalRetrievedCRM..." + totalRetrievedCRM);
		if (totalRetrievedCRM != 0) {
			for (int i = 0; i < totalRetrievedCRM; i++) {
				log.info("i>>>>>" + i);
				String rowCRM = xmlParserCRM.getNextValueOf(Constants.ROW);
				log.info("rowCRM####" + rowCRM);
				XMLParser rowParserCRM = new XMLParser(rowCRM);

				log.info("before assign..." + rowParserCRM.getValueOf("COLLATERAL_NAME_PROP"));
				// collateralName=rowParserCRM.getValueOf("COLLATERAL_NAME_PROP");
				collateralName = rowParserCRM.getValueOf("COLLATERAL_NUMBER");
				log.info("COLLATERAL_NUMBER(collateral_number)..." + collateralName);
				collateral_Name1.add(collateralName);

				collateralType = rowParserCRM.getValueOf("COLLATERAL_TYPE");
				log.info("COLLATERAL_TYPE..." + collateralType);
				collateral_Type1.add(collateralType);

				collateralAmount = rowParserCRM.getValueOf("COLLATERAL_AMOUNT");
				log.info("COLLATERAL_AMOUNT..." + collateralAmount);
				collateral_Amount1.add(collateralAmount);

				collateralCurrency = rowParserCRM.getValueOf("COLLATERAL_CURRENCY");
				log.info("COLLATERAL_CURRENCY..." + collateralCurrency);
				collateral_Currency1.add(collateralCurrency);

				/*
				 * newCollateralType=rowParserCRM.getValueOf("New_Collateral_Type");
				 * log.info("New_Collateral_Type..."+newCollateralType);
				 * newCollateralType1.add(newCollateralType);
				 */
			}
			log.info("collateral_Name1..." + collateral_Name1);
			log.info("collateral_Type1..." + collateral_Type1);
			log.info("collateral_Amount1..." + collateral_Amount1);
			log.info("collateral_Currency1..." + collateral_Currency1);
			// log.info("newCollateralType1..."+newCollateralType);
		}
		// end edit by mohit 18-07-2024for crm collateral array mohitcrm

		JSONObject customerRequestObject = new JSONObject();
		JSONArray filesInfoArr = new JSONArray();

		// start edit by mohit on 21-08-2024
		String sFacilityId = "";
		Float sumPrincipalAmt = 0.0f;
		String sFinalSumPrincipalAmt = "";
		String sPaymentDate = "";
		String sTotalAmt = "";
		String principalAmtPattern = "@*";
		String sPaymentDatePattern = "@*";
		String sQueryReSchFacilityIds = "select distinct facility_id from NG_RAROC_REPAYMANT_DETAILS_API where wi_name='"
				+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "'";
		log.info("sQueryReSchFacilityIds..." + sQueryReSchFacilityIds);
		List<List<String>> sQueryReSchFacilityIdsOut = formObject.getDataFromDB(sQueryReSchFacilityIds);
		log.info("sQueryReSchFacilityIdsOut..." + sQueryReSchFacilityIdsOut);
		if (sQueryReSchFacilityIdsOut != null || !sQueryReSchFacilityIdsOut.isEmpty()) {
			for (List<String> faciltyIdrow : sQueryReSchFacilityIdsOut) {
				sumPrincipalAmt = 0.0f;
				principalAmtPattern = "@*";
				sPaymentDatePattern = "@*";
				sFacilityId = faciltyIdrow.get(0);
				String sQueryReSch = "select principal_amount from NG_RAROC_REPAYMANT_DETAILS_API where wi_name='"
						+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "' and facility_id='"
						+ sFacilityId + "'";
				log.info("sQueryReSch..." + sQueryReSch);
				List<List<String>> sQueryReSchOut = formObject.getDataFromDB(sQueryReSch);
				log.info("sQueryReSchOut..." + sQueryReSchOut);
				if (sQueryReSchOut != null || !sQueryReSchOut.isEmpty()) {
					for (List<String> principalAmtrow : sQueryReSchOut) {
						sumPrincipalAmt += Float.parseFloat(principalAmtrow.get(0));
						principalAmtPattern = principalAmtPattern + principalAmtrow + "*";
						log.info("sumPrincipalAmt.." + sumPrincipalAmt);
					}
					/*
					 * String sPaymentDate=principalAmtrow.get(1);
					 * sPaymentDatePattern=sPaymentDatePattern+sPaymentDate.substring(0, 10)+"*" ;
					 */

					principalAmtPattern = principalAmtPattern + "@";
					// sPaymentDatePattern=sPaymentDatePattern+"@";
				}

				String sQueryReSchPaymentDate = "select payment_date from NG_RAROC_REPAYMANT_DETAILS_API where wi_name='"
						+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "' and facility_id='"
						+ sFacilityId + "'";
				log.info("sQueryReSchPaymentDate..." + sQueryReSchPaymentDate);
				List<List<String>> sQueryReSchPaymentDateOut = formObject.getDataFromDB(sQueryReSchPaymentDate);
				log.info("sQueryReSchPaymentDateOut..." + sQueryReSchPaymentDateOut);
				if (sQueryReSchPaymentDateOut != null || !sQueryReSchPaymentDateOut.isEmpty()) {
					for (List<String> paymentDateRow : sQueryReSchPaymentDateOut) {
						sPaymentDate = paymentDateRow.get(0);
						sPaymentDatePattern = sPaymentDatePattern + sPaymentDate.substring(0, 10) + "*";
					}
					sPaymentDatePattern = sPaymentDatePattern + "@";
					log.info("sPaymentDatePattern..+++" + sPaymentDatePattern);
				}

				String sQueryTotalAmt = "select distinct total_amount  from NG_RAROC_REPAYMANT_DETAILS_API where wi_name='"
						+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "' and facility_id='"
						+ sFacilityId + "'";
				log.info("sQueryTotalAmt.." + sQueryTotalAmt);
				List<List<String>> sQueryTotalAmtOut = formObject.getDataFromDB(sQueryTotalAmt);
				log.info("sQueryTotalAmtOut..." + sQueryTotalAmtOut);
				if (sQueryTotalAmtOut != null || !sQueryTotalAmtOut.isEmpty()) {
					sTotalAmt = sQueryTotalAmtOut.get(0).get(0);
					log.info("sTotalAmt..." + sTotalAmt);

					String sQueryRepaymentType = "SELECT repayment_frequency_sensitized FROM NG_RAROC_FACILITY_DETAILS where wi_name='"
							+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "' and facility_id='"
							+ sFacilityId + "'";
					List<List<String>> sQueryRepaymentTypeOut = formObject.getDataFromDB(sQueryRepaymentType);
					String sRepaymentType = sQueryRepaymentTypeOut.get(0).get(0);
					log.info("sRepaymentTypeshould be manual sens..." + sRepaymentType);
					if (Float.parseFloat(sTotalAmt) == sumPrincipalAmt && sRepaymentType.equalsIgnoreCase("Manual")) {
						principalAmtPattern = principalAmtPattern.replace("@*", "").replace("*@", "").replace("[", "")
								.replace("]", "");
						sPaymentDatePattern = sPaymentDatePattern.replace("@*", "").replace("*@", "");
						JSONObject filedataObject = new JSONObject();
						filedataObject.put("arrangement", sFacilityId);
						filedataObject.put("repayment_date", sPaymentDatePattern);
						filedataObject.put("principal_payment", principalAmtPattern);
						filesInfoArr.add(filedataObject);
						log.info("filesInfoArr...+++" + filesInfoArr);
					}
				}
			}
		}

		// end edit by mohit on 21-08-2024
		// commented on 21-08-2024
		/*
		 * String queryarrangement =
		 * "SELECT Arrangement_ FROM NG_RAROC_LOAN_DETAILS_API WHERE WI_NAME='" +
		 * formObject.getObjGeneralData().getM_strProcessInstanceId() + "'";
		 * log.info("queryarrangement>>>" + queryarrangement); String outputXMLArr =
		 * getDataFromDBWithColumns(queryarrangement, "Arrangement_"); XMLParser
		 * xmlParserArr = new XMLParser(outputXMLArr); log.info("xmlParserArr" +
		 * xmlParserArr); int totalRetrievedArr =
		 * Integer.parseInt(xmlParserArr.getValueOf(Constants.TOTAL_RETRIEVED));
		 * 
		 * if (totalRetrievedArr != 0) {
		 * log.info("Inside if of totalRetrievedArr != 0"); for (int j = 0; j <
		 * totalRetrievedArr; j++) { String paymentDate =""; String principalAmount ="";
		 * String row1 = xmlParserArr.getNextValueOf(Constants.ROW); XMLParser
		 * rowParser1 = new XMLParser(row1); String arrangementID =
		 * rowParser1.getValueOf("Arrangement_");
		 * 
		 * String columnLoan ="Payment_Date,Total_Amount"; String queryLoan = "SELECT "
		 * +columnLoan+" FROM NG_RAROC_REPAYMANT_DETAILS_API WHERE Arrangement='" +
		 * arrangementID + "'"; log.info("queryLoan>>>" + queryLoan); String
		 * outputXMLLoan = getDataFromDBWithColumns(queryLoan, columnLoan); XMLParser
		 * xmlParserLoan = new XMLParser(outputXMLLoan); log.info("xmlParserLoan" +
		 * xmlParserLoan); int totalRetrievedLoan =
		 * Integer.parseInt(xmlParserLoan.getValueOf(Constants.TOTAL_RETRIEVED));
		 * 
		 * if (totalRetrievedLoan != 0) {
		 * log.info("Inside if of totalRetrievedLoan != 0"); for (int i = 0; i <
		 * totalRetrievedLoan; i++) { String row =
		 * xmlParserLoan.getNextValueOf(Constants.ROW); XMLParser rowParser = new
		 * XMLParser(row); if(i ==0) { paymentDate
		 * =rowParser.getValueOf("Payment_Date"); principalAmount=
		 * rowParser.getValueOf("Total_Amount"); }else { paymentDate =
		 * paymentDate+"*"+rowParser.getValueOf("Payment_Date");
		 * principalAmount=principalAmount+"*"+ rowParser.getValueOf("Total_Amount"); }
		 * 
		 * } log.info("before paymentDate..."+paymentDate);//02-07-2024 mohit String
		 * updatepaymentDate = getDateFormat(paymentDate); //paymentDate =
		 * paymentDate.replaceAll("-", "");
		 * log.info("after paymentDate"+updatepaymentDate); principalAmount
		 * =principalAmount.replaceAll(",", ""); JSONObject jsonObject = new
		 * JSONObject(); jsonObject.put("arrangement", arrangementID);
		 * //jsonObject.put("repayment_date", paymentDate);
		 * jsonObject.put("repayment_date", updatepaymentDate);
		 * jsonObject.put("principal_payment", principalAmount);
		 * log.info("Inside if jsonObject"+jsonObject); filesInfoArr.add(jsonObject); }
		 * 
		 * } }
		 */// commented on 21-08-2024
		/*
		 * JSONObject jsonObject = new JSONObject(); JSONObject jsonObject2 = new
		 * JSONObject(); jsonObject.put("arrangement", "AA192154ZCQT");
		 * jsonObject.put("repayment_date", "20221012*20221013*20221014*20221015");
		 * jsonObject.put("principal_payment", "6070.5*6070.92*6071.33*6071.75");
		 * jsonObject2.put("arrangement", "AA19215PKTPK");
		 * jsonObject2.put("repayment_date", "20221012*20221013*20221014*20221015");
		 * jsonObject2.put("principal_payment", "6070.5*6070.92*6071.33*6071.75");
		 * log.info("Inside if jsonObject"+jsonObject);
		 * 
		 * filesInfoArr.add(jsonObject); filesInfoArr.add(jsonObject2);
		 */

		// JSONArray formDataArr = new JSONArray(); //06-07-2024 by mohit as no array of
		// formData needed ;
		JSONObject formDataObj = new JSONObject();
		JSONArray customerArr = new JSONArray();
		JSONObject customerObj = new JSONObject();
		JSONArray facilitiesArr = new JSONArray();
		JSONObject facilitiesObj = new JSONObject();
		log.info("inside facilitiesObj>>>>>>");
		JSONArray crmDataArr = new JSONArray();
		JSONObject crmDataObj = new JSONObject();
		JSONArray incomeTypeArr = new JSONArray();
		JSONObject incomeTypeObj = new JSONObject();

		// start changes by mohit 11-06-2024 for other section
		JSONArray otherTypeArr = new JSONArray();
		JSONObject otherTypeObj = new JSONObject();
		// end changes by mohit 11-06-2024 for other section
		// start edit by mohit 03-07-2024 for COLLATERAL_FACILITY_MAP
		JSONArray colfacilitiesArr = new JSONArray();
		JSONObject colfacilitiesObj = new JSONObject();
		// end edit by mohit 03-07-2024 for COLLATERAL_FACILITY_MAP

		JSONObject customerObj1 = new JSONObject();
		customerObj1.put("name", "new_customer");
		customerObj1.put("value", "False");
		JSONObject customerObj2 = new JSONObject();
		customerObj2.put("name", "code");// Mandatory
		customerObj2.put("value", code);// T24 Customer ID

		JSONObject customerObj3 = new JSONObject();
		customerObj3.put("name", "name");// Mandatory
		customerObj3.put("value", name);// Customer Name
		JSONObject customerObj4 = new JSONObject();
		customerObj4.put("name", "country");// Mandatory
		customerObj4.put("value", country);// Risk Country Name

		JSONObject customerObj5 = new JSONObject();
		customerObj5.put("name", "region");
		// customerObj5.put("value", "Dubai");//karan to confirm
		customerObj5.put("value", region);// EST_PLACE DB
		JSONObject customerObj6 = new JSONObject();
		customerObj6.put("name", "internal_rating");// Mandatory
		customerObj6.put("value", internalRatingCust);// Internal MRA Rating

		JSONObject customerObj7 = new JSONObject();
		customerObj7.put("name", "external_rating_agency_code");
		customerObj7.put("value", "SNP");// Unrated
		JSONObject customerObj8 = new JSONObject();
		customerObj8.put("name", "external_rating");// Mandatory
		customerObj8.put("value", "Unrated");// External Rating; extRating

		JSONObject customerObj9 = new JSONObject();
		customerObj9.put("name", "industry_sector");// Mandatory
		customerObj9.put("value", industrySector);// Industry Segement
		JSONObject customerObj10 = new JSONObject();
		customerObj10.put("name", "sales_turnover");// Mandatory
		customerObj10.put("value", salesTurnover);// Sales Turnover

		JSONObject customerObj11 = new JSONObject();
		customerObj11.put("name", "segment");// Mandatory
		customerObj11.put("value", segment);// Business segment
		JSONObject customerObj12 = new JSONObject();
		customerObj12.put("name", "group_id");// Mandatory
		customerObj12.put("value", groupId);

		JSONObject customerObj13 = new JSONObject();
		customerObj13.put("name", "group_name");// Mandatory
		customerObj13.put("value", groupName);
		// JSONObject customerObj14 = new JSONObject();
		/*
		 * customerObj14.put("name", "assessment_date"); customerObj14.put("value",
		 * assessmentDate);
		 */// 06-07-2024

		customerArr.add(customerObj1);
		customerArr.add(customerObj2);
		customerArr.add(customerObj3);
		customerArr.add(customerObj4);
		customerArr.add(customerObj5);
		customerArr.add(customerObj6);
		customerArr.add(customerObj7);
		customerArr.add(customerObj8);
		customerArr.add(customerObj9);
		customerArr.add(customerObj10);
		customerArr.add(customerObj11);
		customerArr.add(customerObj12);
		customerArr.add(customerObj13);
		// customerArr.add(customerObj14);
		formDataObj.put("customer", customerArr);
		/*
		 * customerObj.put("customer", customerArr); formDataArr.add(customerObj);
		 */ // 06-07-2024
		// formDataObj.put("customer", customerArr);
		// formDataArr.add(formDataObj);

		// start edit by mohit 03-07-2024 for COLLATERAL_FACILITY_MAP:
		// commitmentNo(name_value1),
		// facilityName(product_type_value1),allocationPercProp
		JSONObject colfacilitiesObj1 = new JSONObject();
		colfacilitiesObj1.put("name", "facility");
		JSONArray colfacilitiesValArr1 = new JSONArray();
		/*
		 * for (String product_type_value : product_type_value1) {
		 * colfacilitiesValArr1.add(product_type_value); }
		 */
		for (String commitment_value : commitment_value1) {
			log.info("####commitment_value####" + commitment_value);
			colfacilitiesValArr1.add(commitment_value);
		}
		colfacilitiesObj1.put("value", colfacilitiesValArr1);

		JSONObject colfacilitiesObj2 = new JSONObject();
		colfacilitiesObj2.put("name", "collateral");
		JSONArray colfacilitiesValArr2 = new JSONArray();// F_Collateral_Number_Proposed
		/*
		 * fCollateralNumberProposed=
		 * iformrefrence.getValue("F_Collateral_Number_Proposed").toString();
		 * colfacilitiesObj2.put("value", fCollateralNumberProposed);
		 */
		/*
		 * for (String name_value : name_value1) { colfacilitiesValArr2.add(name_value);
		 * }
		 */
		for (String collateral_No : collateral_No1) {
			log.info("####collateral_No####" + collateral_No);
			colfacilitiesValArr2.add(collateral_No);
			// colfacilitiesValArr2.add(iformrefrence.getValue("F_Collateral_Number_Proposed").toString());
		}
		colfacilitiesObj2.put("value", colfacilitiesValArr2);

		JSONObject colfacilitiesObj3 = new JSONObject();
		colfacilitiesObj3.put("name", "weight");
		JSONArray colfacilitiesValArr3 = new JSONArray();
		for (String allocation_Perc_Prop_value : allocation_Perc_Prop_value1) {
			/*
			 * if (allocation_Perc_Prop_value != null || allocation_Perc_Prop_value != "") {
			 * Float fAallocation_Perc_Prop_value =
			 * Float.parseFloat(allocation_Perc_Prop_value); allocation_Perc_Prop_value =
			 * String.format("%.3f",fAallocation_Perc_Prop_value / 100);
			 * //allocation_Perc_Prop_value = String.valueOf(fAallocation_Perc_Prop_value /
			 * 100); }
			 */
			log.info("####allocation_Perc_Prop_value####" + allocation_Perc_Prop_value);
			allocation_Perc_Prop_value = decimalFloatValue(allocation_Perc_Prop_value);
			colfacilitiesValArr3.add(allocation_Perc_Prop_value);
		}
		colfacilitiesObj3.put("value", colfacilitiesValArr3);
		// end edit by mohit 03-07-2024 for COLLATERAL_FACILITY_MAP:
		// commitmentNo(name_value1),
		// facilityName(product_type_value1),allocationPercProp

		JSONObject facilitiesObj1 = new JSONObject();
		facilitiesObj1.put("name", "name");
		JSONArray facilitiesValArr1 = new JSONArray();
		int counter1 = 0;
		int counter2 = 0;
		for (String name_value : name_value1) {// commitment a,b
			counter1 = counter1 + 1;
			log.info("name_value.." + name_value + "..counter1..." + counter1);
			for (String facilityId_value : facilityId_value1) {// facilty aa,bb
				counter2 = counter2 + 1;
				if (counter2 == counter1) {
					log.info("facilityId_value.." + facilityId_value + "..counter2..." + counter2);
					if (name_value.isEmpty() || name_value == null || name_value == "") {
						log.info("inside if name_value is empty...");
						facilitiesValArr1.add(facilityId_value);

					} else {
						log.info("inside if name_value is not empty...");
						facilitiesValArr1.add(name_value);
					}
				}
			}
			counter2 = 0;

		}
		facilitiesObj1.put("value", facilitiesValArr1);

		JSONObject facilitiesObj2 = new JSONObject();
		facilitiesObj2.put("name", "otc_flag");// Mandatory
		JSONArray facilitiesValArr2 = new JSONArray();

		/*
		 * for (Boolean bOtc_flag_value : bOtc_flag_value1) {
		 * facilitiesValArr2.add(bOtc_flag_value); }
		 */
		for (String otc_flag_value : otc_flag_value1) {
			facilitiesValArr2.add(otc_flag_value);// TreasuryProposed
		}
		facilitiesObj2.put("value", facilitiesValArr2);

		JSONObject facilitiesObj3 = new JSONObject();
		facilitiesObj3.put("name", "type_");// Mandatory
		JSONArray facilitiesValArr3 = new JSONArray();
		for (String type_value : type_value1) {// FACILITY_TYPE
			facilitiesValArr3.add(type_value);
		}
		facilitiesObj3.put("value", facilitiesValArr3);

		JSONObject facilitiesObj4 = new JSONObject();
		facilitiesObj4.put("name", "product_type");// Mandatory
		JSONArray facilitiesValArr4 = new JSONArray();
		for (String product_type_value : product_type_value1) { // Facility Name
			facilitiesValArr4.add(product_type_value);
		}
		facilitiesObj4.put("value", facilitiesValArr4);

		JSONObject facilitiesObj5 = new JSONObject();
		facilitiesObj5.put("name", "repayment_mode");// Mandatory
		JSONArray facilitiesValArr5 = new JSONArray();
		for (String repayment_mode_value : repayment_mode_value1) {// Repayment Type
			log.info("repayment_mode_value..." + repayment_mode_value);
			facilitiesValArr5.add(repayment_mode_value);
		}
		facilitiesObj5.put("value", facilitiesValArr5);
		JSONObject facilitiesObj6 = new JSONObject();
		facilitiesObj6.put("name", "repayment_frequency");// Mandatory FOR Constant/Linear
		JSONArray facilitiesValArr6 = new JSONArray();
		for (String repayment_frequency_value : repayment_frequency_value1) {
			log.info("repayment_frequency_value..." + repayment_frequency_value);
			facilitiesValArr6.add(repayment_frequency_value);
		}
		facilitiesObj6.put("value", facilitiesValArr6);

		JSONObject facilitiesObj7 = new JSONObject();
		facilitiesObj7.put("name", "tenor");// Mandatory
		JSONArray facilitiesValArr7 = new JSONArray();
		for (String tenor_value : tenor_value1) {
			facilitiesValArr7.add(tenor_value);
		}
		facilitiesObj7.put("value", facilitiesValArr7);

		JSONObject facilitiesObj8 = new JSONObject();
		facilitiesObj8.put("name", "moratorium_period");
		JSONArray facilitiesValArr8 = new JSONArray();
		for (String moratorium_period_value : moratorium_period_value1) {
			facilitiesValArr8.add(moratorium_period_value);
		}
		facilitiesObj8.put("value", facilitiesValArr8);

		JSONObject facilitiesObj9 = new JSONObject();
		facilitiesObj9.put("name", "currency");// Mandatory
		JSONArray facilitiesValArr9 = new JSONArray();
		for (String currency_value : currency_value1) {// Currency
			facilitiesValArr9.add(currency_value);
		}
		facilitiesObj9.put("value", facilitiesValArr9);

		JSONObject facilitiesObj10 = new JSONObject();
		facilitiesObj10.put("name", "index");// Mandatory FOR FUNDED FACILITIES
		JSONArray facilitiesValArr10 = new JSONArray();
		for (String index_value : index_value1) {
			// if(!index_value.isEmpty()) {
			facilitiesValArr10.add(index_value);
			/*
			 * }else { facilitiesValArr10.add("None"); }
			 */ // 21-08-2024 due to null pointer exception
		}
		facilitiesObj10.put("value", facilitiesValArr10);

		JSONObject facilitiesObj11 = new JSONObject();
		facilitiesObj11.put("name", "index_tenor");// Mandatory IF index IS NOT NONE
		JSONArray facilitiesValArr11 = new JSONArray();
		for (String index_tenor_value : index_tenor_value1) {
			facilitiesValArr11.add(index_tenor_value);
		}
		facilitiesObj11.put("value", facilitiesValArr11);

		JSONObject facilitiesObj12 = new JSONObject();
		facilitiesObj12.put("name", "tenor_unit");// Mandatory
		JSONArray facilitiesValArr12 = new JSONArray();
		for (String tenor_unit_value : tenor_unit_value1) {
			facilitiesValArr12.add(tenor_unit_value);
		}
		facilitiesObj12.put("value", facilitiesValArr12);

		JSONObject facilitiesObj13 = new JSONObject();
		facilitiesObj13.put("name", "margin");
		JSONArray facilitiesValArr13 = new JSONArray();
		// for (String margin_value : margin_value1) {//Cash Margin
		/*
		 * if (margin_value != null || margin_value != "") { Float fmargin_value =
		 * Float.parseFloat(margin_value); margin_value =
		 * String.format("%.3f",fmargin_value / 100); }
		 */
		for (String margin_value : commission_rate_value1) {// Margin/Commision
			if (margin_value != null) // 21-08-2024 for otc_product null values
				margin_value = decimalFloatValue(margin_value);
			facilitiesValArr13.add(margin_value);
		}
		facilitiesObj13.put("value", facilitiesValArr13);

		JSONObject facilitiesObj14 = new JSONObject();
		facilitiesObj14.put("name", "commission_rate");// Mandatory FOR NONFUNDED FACILITIES
		JSONArray facilitiesValArr14 = new JSONArray();
		// for (String commission_rate_value : commission_rate_value1)
		// {//Margin/Commision
		/*
		 * if (commission_rate_value != null || commission_rate_value != "") { Float
		 * fCommission_rate_value = Float.parseFloat(commission_rate_value);
		 * commission_rate_value = String.format("%.3f",fCommission_rate_value / 100);
		 * //commission_rate_value = String.valueOf(fCommission_rate_value / 100); }
		 */
		for (String commission_rate_value : valueProp_value1) {
			if (commission_rate_value != null) // 21-08-2024 for otc_product null values
				commission_rate_value = decimalFloatValue(commission_rate_value);
			facilitiesValArr14.add(commission_rate_value);
		}
		facilitiesObj14.put("value", facilitiesValArr14);

		JSONObject facilitiesObj15 = new JSONObject();
		facilitiesObj15.put("name", "limit");// Mandatory
		JSONArray facilitiesValArr15 = new JSONArray();
		for (String limit_value : limit_value1) {// Facility Amount
			try {
				if (limit_value == null)
					facilitiesValArr15.add(limit_value);
				else if (!limit_value.isEmpty() || limit_value != "") {
					int val = new BigDecimal(limit_value).intValue();
					facilitiesValArr15.add(String.valueOf(val));
				}
			} catch (Exception e) {
				log.error("limit_value... : ", e);
			}
		}
		facilitiesObj15.put("value", facilitiesValArr15);

		JSONObject facilitiesObj16 = new JSONObject();
		facilitiesObj16.put("name", "utilisation");// Mandatory
		JSONArray facilitiesValArr16 = new JSONArray();
		for (String utilisation_value : utilisation_value1) {// Expected Utilisation
			/*
			 * if (utilisation_value != null || utilisation_value != "") { Float
			 * futilisation_value = Float.parseFloat(utilisation_value); utilisation_value =
			 * String.format("%.3f", futilisation_value / 100); // interest_floor_value =
			 * String.valueOf(fInterest_floor_value / 100); }
			 */
			// utilisation_value
			// utilisation_value =
			// decimalFloatValue(iformrefrence.getValue("F_Utilization_Proposed").toString());
			if (utilisation_value != null) // 21-08-2024 for otc_product null values
				utilisation_value = decimalFloatValue(utilisation_value);
			facilitiesValArr16.add(utilisation_value);
		}

		facilitiesObj16.put("value", facilitiesValArr16);

		JSONObject facilitiesObj17 = new JSONObject();
		facilitiesObj17.put("name", "facility_position");// Mandatory
		JSONArray facilitiesValArr17 = new JSONArray();
		for (String facility_position_value : facility_position_value1) {// Senior/Subordinate
			facilitiesValArr17.add(facility_position_value);
		}
		facilitiesObj17.put("value", facilitiesValArr17);

		JSONObject facilitiesObj18 = new JSONObject();
		facilitiesObj18.put("name", "commitment_type");// Mandatory
		JSONArray facilitiesValArr18 = new JSONArray();
		for (String commitment_type_value : commitment_type_value1) {// Committed/Uncommited
			facilitiesValArr18.add(commitment_type_value);
		}
		facilitiesObj18.put("value", facilitiesValArr18);

		JSONObject facilitiesObj19 = new JSONObject();
		facilitiesObj19.put("name", "csv_path");// Mandatory IF repayment_mode is Manual
		JSONArray facilitiesValArr19 = new JSONArray();
		for (String csv_path_value : csv_path_value1) {// value not provided
			facilitiesValArr19.add(csv_path_value);
		}
		facilitiesObj19.put("value", facilitiesValArr19);

		JSONObject facilitiesObj20 = new JSONObject();
		facilitiesObj20.put("name", "notional_amount");// Mandatory FOR OTC PROD (Treasury Products IS YES)
		JSONArray facilitiesValArr20 = new JSONArray();
		int counterNA = 0;
		int counterOtc = 0;
		for (String notional_amount_value : notional_amount_value1) {// Facility Amount
			counterNA = counterNA + 1;
			for (String otc_flag_value : otc_flag_value1) {
				counterOtc = counterOtc + 1;
				if (counterNA == counterOtc) {
					if (otc_flag_value.equalsIgnoreCase("True")) {
						facilitiesValArr20.add(notional_amount_value);
					} else if (otc_flag_value.equalsIgnoreCase("False")) {
						facilitiesValArr20.add("");
					}
				}
			}
			counterOtc = 0;
		}
		facilitiesObj20.put("value", facilitiesValArr20);

		JSONObject facilitiesObj21 = new JSONObject();
		facilitiesObj21.put("name", "expected_1_year_return");// Mandatory FOR OTC PROD (Treasury Products IS YES)
		JSONArray facilitiesValArr21 = new JSONArray();
		for (String expected_1_year_return_type_value : expected_1_year_return_type_value1) {
			facilitiesValArr21.add(expected_1_year_return_type_value);
		}
		facilitiesObj21.put("value", facilitiesValArr21);

		JSONObject facilitiesObj22 = new JSONObject();
		facilitiesObj22.put("name", "market_risk_capital");// Mandatory FOR OTC PROD (Treasury Products IS YES)
		JSONArray facilitiesValArr22 = new JSONArray();
		for (String market_risk_capital_value : market_risk_capital_value1) {
			facilitiesValArr22.add(market_risk_capital_value);
		}
		facilitiesObj22.put("value", facilitiesValArr22);

		JSONObject facilitiesObj23 = new JSONObject();
		facilitiesObj23.put("name", "ftp_override");
		JSONArray facilitiesValArr23 = new JSONArray();
		for (String ftp_override_value : ftp_override_value1) {
			facilitiesValArr23.add(ftp_override_value);
		}
		facilitiesObj23.put("value", facilitiesValArr23);

		JSONObject facilitiesObj24 = new JSONObject();
		facilitiesObj24.put("name", "repricing_frequency");// Mandatory if interest rate is floating
		JSONArray facilitiesValArr24 = new JSONArray();
		for (String repricing_frequency_value : repricing_frequency_value1) {
			facilitiesValArr24.add(repricing_frequency_value);
			sHardCodeValue = repricing_frequency_value; // sHardCodeValue
		}
		facilitiesObj24.put("value", facilitiesValArr24);

		JSONObject facilitiesObj25 = new JSONObject();
		facilitiesObj25.put("name", "ftp_rate");
		JSONArray facilitiesValArr25 = new JSONArray();
		for (String ftp_rate_value : ftp_rate_value1) {
			if (ftp_rate_value.equalsIgnoreCase("-")) {
				ftp_rate_value = "";
			}
			facilitiesValArr25.add(ftp_rate_value);
		}
		facilitiesObj25.put("value", facilitiesValArr25);

		// start 03-07-2024 by mohit for FACILITY: Missing Field interest_floor
		JSONObject facilitiesObj26 = new JSONObject();
		facilitiesObj26.put("name", "interest_floor");
		JSONArray facilitiesValArr26 = new JSONArray();
		for (String interest_floor_value : interest_floor_value1) { // Minimum
			/*
			 * if (interest_floor_value != null || interest_floor_value != "") { Float
			 * fInterest_floor_value = Float.parseFloat(interest_floor_value);
			 * interest_floor_value = String.format("%.3f",fInterest_floor_value / 100);
			 * //interest_floor_value = String.valueOf(fInterest_floor_value / 100); }
			 */
			if (interest_floor_value != null) // 21-08-2024 for otc_product null values
				interest_floor_value = decimalFloatValue(interest_floor_value);
			facilitiesValArr26.add(interest_floor_value);
		}
		facilitiesObj26.put("value", facilitiesValArr26);

		// end 03-07-2024 by mohit for FACILITY: Missing Field interest_floor

		facilitiesArr.add(facilitiesObj1);
		facilitiesArr.add(facilitiesObj2);
		facilitiesArr.add(facilitiesObj3);
		facilitiesArr.add(facilitiesObj4);
		facilitiesArr.add(facilitiesObj5);
		facilitiesArr.add(facilitiesObj6);
		facilitiesArr.add(facilitiesObj7);
		facilitiesArr.add(facilitiesObj8);
		facilitiesArr.add(facilitiesObj9);
		facilitiesArr.add(facilitiesObj10);
		facilitiesArr.add(facilitiesObj11);
		facilitiesArr.add(facilitiesObj12);
		facilitiesArr.add(facilitiesObj13);
		facilitiesArr.add(facilitiesObj14);
		facilitiesArr.add(facilitiesObj15);
		facilitiesArr.add(facilitiesObj16);
		facilitiesArr.add(facilitiesObj17);
		facilitiesArr.add(facilitiesObj18);
		facilitiesArr.add(facilitiesObj19);
		facilitiesArr.add(facilitiesObj20);
		facilitiesArr.add(facilitiesObj21);
		facilitiesArr.add(facilitiesObj22);
		facilitiesArr.add(facilitiesObj23);
		facilitiesArr.add(facilitiesObj24);
		facilitiesArr.add(facilitiesObj25);
		facilitiesArr.add(facilitiesObj26);

		formDataObj.put("facilities", facilitiesArr);
		// facilitiesObj.put("facilities", facilitiesArr);
		// formDataArr.add(facilitiesObj); //06-07-2024

		// start edit by mohit 03-07-2024 for COLLATERAL_FACILITY_MAP
		colfacilitiesArr.add(colfacilitiesObj1);
		colfacilitiesArr.add(colfacilitiesObj2);
		colfacilitiesArr.add(colfacilitiesObj3);
		formDataObj.put("collateral_facility_map", colfacilitiesArr);
		/*
		 * colfacilitiesObj.put("collateral_facility_map", colfacilitiesArr);
		 * formDataArr.add(colfacilitiesObj);
		 */ // 06-07-2024
		// end edit by mohit 03-07-2024 for COLLATERAL_FACILITY_MAP

		// CRMDetails Obj
		JSONArray gridCount = iformrefrence.getDataFromGrid("table30");
		String bankGuarantor = "";
		// start edit 04-07-2024 mohit
		/*
		 * if(gridCount .size() != 0) { for (int j = 0; j < gridCount .size(); j++) {
		 * log.info("gridCount size " +gridCount.size()); String guaranteeguarantorTag =
		 * iformrefrence.getTableCellValue("table30", j, 3).toString();
		 * log.info("mandatoryType --->>>>  " +guaranteeguarantorTag);
		 * if(guaranteeguarantorTag.equalsIgnoreCase("Bank Gurantee")) { bankGuarantor
		 * ="Y"; String guaranteeType = iformrefrence.getTableCellValue("table30", j,
		 * 0).toString(); String guaranteeName =
		 * iformrefrence.getTableCellValue("table30", j, 1).toString(); String
		 * guaranteeAmount = iformrefrence.getTableCellValue("table30", j,
		 * 2).toString(); String guaranteeguarantor =
		 * iformrefrence.getTableCellValue("table30", j, 3).toString();//4
		 * COLLATERAL_NUMBER AND 5 COLLATERAL_CURRENCY String externalRating =
		 * iformrefrence.getTableCellValue("table30", j, 6).toString(); String
		 * internalRating = iformrefrence.getTableCellValue("table30", j, 7).toString();
		 * String externalRatingCode = iformrefrence.getTableCellValue("table30", j,
		 * 8).toString();
		 * 
		 * JSONObject crmDataObj1 = new JSONObject(); crmDataObj1.put("name",
		 * "guarantee_type_"); crmDataObj1.put("value", guaranteeType); JSONObject
		 * crmDataObj2 = new JSONObject(); crmDataObj2.put("name", "guarantee_name");
		 * crmDataObj2.put("value", guaranteeName); JSONObject crmDataObj3 = new
		 * JSONObject(); crmDataObj3.put("name", "guarantee_amount");
		 * crmDataObj3.put("value", guaranteeAmount); JSONObject crmDataObj4 = new
		 * JSONObject(); crmDataObj4.put("name", "guarantee_external_rating");
		 * crmDataObj4.put("value", externalRating); JSONObject crmDataObj5 = new
		 * JSONObject(); crmDataObj5.put("name", "guarantee_guarantor_type");
		 * crmDataObj5.put("value", guaranteeguarantor); JSONObject crmDataObj6 = new
		 * JSONObject(); crmDataObj6.put("name", "guarantee_internal_rating");
		 * crmDataObj6.put("value", internalRating); JSONObject crmDataObj7 = new
		 * JSONObject(); crmDataObj7.put("name",
		 * "guarantee_external_rating_agency_code"); crmDataObj7.put("value",
		 * externalRatingCode); crmDataArr.add(crmDataObj1);
		 * crmDataArr.add(crmDataObj2); crmDataArr.add(crmDataObj3);
		 * crmDataArr.add(crmDataObj4); crmDataArr.add(crmDataObj5);
		 * crmDataArr.add(crmDataObj6); crmDataArr.add(crmDataObj7); } } }
		 * if(!bankGuarantor.equalsIgnoreCase("Y")) { JSONObject crmNonBankDataObj = new
		 * JSONObject(); crmNonBankDataObj.put("name", "guarantee_external_rating");
		 * crmNonBankDataObj.put("value", "Unrated"); crmDataArr.add(crmNonBankDataObj);
		 * }
		 */
		String guaranteeType = "";
		String guaranteeName = "";
		String guaranteeAmount = "";
		String guaranteeguarantor = "";
		String externalRating = "";
		String internalRating = "";
		String externalRatingCode = "";
		String sCollateralCurrencyHaircut = "";

		JSONObject crmDataObj1 = new JSONObject();
		crmDataObj1.put("name", "guarantee_type_");
		crmDataObj1.put("value", guaranteeType);
		JSONObject crmDataObj2 = new JSONObject();
		crmDataObj2.put("name", "guarantee_name");
		crmDataObj2.put("value", guaranteeName);
		JSONObject crmDataObj3 = new JSONObject();
		crmDataObj3.put("name", "guarantee_amount");// Mandatory in case of guarantee
		crmDataObj3.put("value", guaranteeAmount);
		JSONObject crmDataObj4 = new JSONObject();
		crmDataObj4.put("name", "guarantee_external_rating");// Mandatory in case of guarantee default Unrated
		if (!guaranteeType.isEmpty()) {
			crmDataObj4.put("value", "Unrated");
		} else {
			crmDataObj4.put("value", externalRating);
		}

		JSONObject crmDataObj5 = new JSONObject();
		crmDataObj5.put("name", "guarantee_guarantor_type");// Mandatory in case of guarantee default BANK
		if (!guaranteeType.isEmpty()) {
			crmDataObj4.put("value", "Bank");
		} else {
			crmDataObj4.put("value", guaranteeguarantor);
		}

		crmDataObj5.put("value", guaranteeguarantor);
		JSONObject crmDataObj6 = new JSONObject();
		crmDataObj6.put("name", "guarantee_internal_rating");// Mandatory in case of guarantee
		crmDataObj6.put("value", internalRating);
		JSONObject crmDataObj7 = new JSONObject();
		crmDataObj7.put("name", "guarantee_external_rating_agency_code");
		crmDataObj7.put("value", externalRatingCode);
		JSONObject crmDataObj8 = new JSONObject();
		crmDataObj8.put("name", "collateral_currency_haircut");
		crmDataObj8.put("value", externalRatingCode);
		crmDataArr.add(crmDataObj1);
		crmDataArr.add(crmDataObj2);
		crmDataArr.add(crmDataObj3);
		crmDataArr.add(crmDataObj4);
		crmDataArr.add(crmDataObj5);
		crmDataArr.add(crmDataObj6);
		crmDataArr.add(crmDataObj7);
		crmDataArr.add(crmDataObj8);

		// end edit 04-07-2024 mohit gm to check

		JSONObject crmComplexDataObj1 = new JSONObject();
		crmComplexDataObj1.put("name", "collateral_name");// MANDATORY
		JSONArray crmComplexDataArr1 = new JSONArray();
		for (String collateral_Name : collateral_Name1) {
			// for (String collateral_Name : collateral_No1) {//from CFM grid 22-07-2024
			crmComplexDataArr1.add(collateral_Name);// R_09-07-2024
			// crmComplexDataArr1.add(collateral_Name);
		}
		crmComplexDataObj1.put("value", crmComplexDataArr1);

		JSONObject crmComplexDataObj2 = new JSONObject();
		crmComplexDataObj2.put("name", "collateral_type_");// MANDATORY
		JSONArray crmComplexDataArr2 = new JSONArray();
		for (String collateral_Type : collateral_Type1) {
			log.info("newCollateralType1....." + newCollateralType1);
			crmComplexDataArr2.add(collateral_Type);
		}
		crmComplexDataObj2.put("value", crmComplexDataArr2);

		JSONObject crmComplexDataObj3 = new JSONObject();
		crmComplexDataObj3.put("name", "collateral_amount");// MANDATORY
		JSONArray crmComplexDataArr3 = new JSONArray();
		for (String collateral_Amount : collateral_Amount1) {
			// crmComplexDataArr3.add(collateral_Amount);
			try {
				int val = 0;
				if (!collateral_Amount.isEmpty() || collateral_Amount != null || collateral_Amount != "") {
					val = new BigDecimal(collateral_Amount).intValue();

					crmComplexDataArr3.add(String.valueOf(val));
				}
			} catch (Exception e) {
				log.error("collateral_Amount... : ", e);
			}
		}
		crmComplexDataObj3.put("value", crmComplexDataArr3);

		JSONObject crmComplexDataObj4 = new JSONObject();
		crmComplexDataObj4.put("name", "collateral_currency");// MANDATORY
		JSONArray crmComplexDataArr4 = new JSONArray();
		for (String collateral_Currency : collateral_Currency1) {
			crmComplexDataArr4.add(collateral_Currency);
		}
		crmComplexDataObj4.put("value", crmComplexDataArr4);

		JSONObject crmComplexDataObj5 = new JSONObject();
		crmComplexDataObj5.put("name", "collateral_lien_amount");// Mandatory for Lien CollateraL
		JSONArray crmComplexDataArr5 = new JSONArray();
		for (String collateral_Lean : collateral_Lean1) {
			for (String collateral_Name : collateral_Name1) {
				if (collateral_Name.equalsIgnoreCase("Lien")) {
					crmComplexDataArr5.add(collateral_Lean);
				} else {
					crmComplexDataArr5.add("");
				}
			}
		}
		crmComplexDataObj5.put("value", crmComplexDataArr5);

		JSONObject crmComplexDataObj6 = new JSONObject();
		crmComplexDataObj6.put("name", "collateral_lien_interest");// Mandatory for Lien Collateral
		JSONArray crmComplexDataArr6 = new JSONArray();
		// int counterLI=0;
		// int counterN1=0;
		for (String collateral_LeanInterest : collateral_LeanInterest1) {
			// counterLI=counterLI+1;
			for (String collateral_Name : collateral_Name1) {
				// counterN1=counterN1+1;
				// if (counterN1 == counterLI) {
				if (collateral_Name.equalsIgnoreCase("Lien")) {
					crmComplexDataArr6.add(collateral_LeanInterest);
				} else {
					crmComplexDataArr6.add("");
				}
				// }
			} // counterN1=0;
		}
		crmComplexDataObj6.put("value", crmComplexDataArr6);

		JSONObject crmComplexDataObj7 = new JSONObject();
		crmComplexDataObj7.put("name", "collateral_lien_tenor");// Mandatory for Lien Collateral
		JSONArray crmComplexDataArr7 = new JSONArray();
		// int counterLT=0;
		// int counterN2=0;
		for (String collateral_LeanTenor : collateral_LeanTenor1) {
			// counterLT=counterLT+1;
			for (String collateral_Name : collateral_Name1) {
				// counterN2=counterN2+1;
				// if(counterN2==counterLT) {
				if (collateral_Name.equalsIgnoreCase("Lien")) {
					crmComplexDataArr7.add(collateral_LeanTenor);
				} else {
					crmComplexDataArr7.add("");
				}
				// }
			} // counterN2=0;

		}
		crmComplexDataObj7.put("value", crmComplexDataArr7);

		// start edit by mohit 04-07-2024 for cash margin and collateral haircut for
		// crmdetails
		JSONObject crmComplexDataObj8 = new JSONObject();
		crmComplexDataObj8.put("name", "collateral_cash_margin");// Mandatory for cash margin,???
		JSONArray crmComplexDataArr8 = new JSONArray();
		// int counterCM=0;
		// int counterN3=0;
		for (String crmCollateralCashMargin : crmCollateral_cash_margin1) {
			// counterCM=counterCM+1;
			for (String collateral_Name : collateral_Name1) {
				// counterN3=counterN3+1;
				// if(counterN3==counterCM) {
				if (collateral_Name.equalsIgnoreCase("Lien")) {
					crmComplexDataArr8.add(sHardCodeValue);
				} else {
					crmComplexDataArr8.add("");
				}
				// }
			} // counterN3=0;
		}
		crmComplexDataObj8.put("value", crmComplexDataArr8);

		JSONObject crmComplexDataObj9 = new JSONObject();
		crmComplexDataObj9.put("name", "collateral_haircut");
		JSONArray crmComplexDataArr9 = new JSONArray();
		for (String crmCollateralHaircut : crmCollateral_haircut1) {
			for (String collateral_Name : collateral_Name1) {
				if (collateral_Name.equalsIgnoreCase("Lien")) {
					crmComplexDataArr9.add(crmCollateralHaircut);
				} else {
					crmComplexDataArr9.add("");
				}
			}

		}
		crmComplexDataObj9.put("value", crmComplexDataArr9);

		// end edit by mohit 04-07-2024 for cash margin and collateral haircut for
		// crmdetails

		crmDataArr.add(crmComplexDataObj1);
		crmDataArr.add(crmComplexDataObj2);
		crmDataArr.add(crmComplexDataObj3);
		crmDataArr.add(crmComplexDataObj4);
		crmDataArr.add(crmComplexDataObj5);
		crmDataArr.add(crmComplexDataObj6);
		crmDataArr.add(crmComplexDataObj7);
		crmDataArr.add(crmComplexDataObj8);
		crmDataArr.add(crmComplexDataObj9);
		formDataObj.put("crm_details", crmDataArr);
		/*
		 * crmDataObj.put("crmDetails", crmDataArr); formDataArr.add(crmDataObj);
		 */ // 06-07-2024

		// IncomeType Obj
		JSONObject incomeDataObj1 = new JSONObject();
		incomeDataObj1.put("name", "name");// Mandatory
		JSONArray incomeDataArr1 = new JSONArray();
		for (String income_Name : income_Name1) {
			incomeDataArr1.add(income_Name);
		}
		incomeDataObj1.put("value", incomeDataArr1);

		JSONObject incomeDataObj2 = new JSONObject();
		incomeDataObj2.put("name", "type_");// Mandatory
		JSONArray incomeDataArr2 = new JSONArray();
		for (String income_Type : income_Type1) {
			incomeDataArr2.add(income_Type);
		}
		incomeDataObj2.put("value", incomeDataArr2);

		JSONObject incomeDataObj3 = new JSONObject();
		incomeDataObj3.put("name", "percentage");// Mandatory if type is percentage
		JSONArray incomeDataArr3 = new JSONArray();
		for (String income_Percentage : income_Percentage1) {
			/*
			 * if (income_Percentage!= null || income_Percentage!="") { Float
			 * dIncome_Percentage = Float.parseFloat(income_Percentage); income_Percentage =
			 * String.format("%.3f",dIncome_Percentage / 100); //income_Percentage =
			 * String.valueOf(dIncome_Percentage / 100); }
			 */
			income_Percentage = decimalFloatValue(income_Percentage);
			incomeDataArr3.add(income_Percentage);
			// incomeDataArr3.add(income_Percentage);
		}
		incomeDataObj3.put("value", incomeDataArr3);

		JSONObject incomeDataObj4 = new JSONObject();
		incomeDataObj4.put("name", "absolute_exposure");// Mandatory if type is Absolute Income
		JSONArray incomeDataArr4 = new JSONArray();
		for (String income_Absolute : income_Absolute1) {
			incomeDataArr4.add(income_Absolute);
		}
		incomeDataObj4.put("value", incomeDataArr4);

		// start changes by mohit 13-06-2024 for facilities --> Compulsory field
		// facilities is missing
		JSONObject incomeDataObj5 = new JSONObject();
		incomeDataObj5.put("name", "facilities");// Mandatory
		incomeDataObj5.put("value", facilitiesValArr1);
		// end changes by mohit 13-06-2024 for facilities --> Compulsory field
		// facilities is missing

		incomeTypeArr.add(incomeDataObj1);
		incomeTypeArr.add(incomeDataObj2);
		incomeTypeArr.add(incomeDataObj3);
		incomeTypeArr.add(incomeDataObj4);
		incomeTypeArr.add(incomeDataObj5);
		formDataObj.put("incomes", incomeTypeArr);
		/*
		 * incomeTypeObj.put("incomes", incomeTypeArr); formDataArr.add(incomeTypeObj);
		 */// 06-07-2024

		/*
		 * //start changes by mohit 11-06-2024 for other section as value should not be
		 * array JSONObject otherDataObj1 = new JSONObject(); otherDataObj1.put("name",
		 * "average_balance"); JSONArray otherDataArr1 = new JSONArray(); for (String
		 * average_balance : f_average_balance) { otherDataArr1.add(average_balance); }
		 * otherDataObj1.put("value", otherDataArr1);
		 * 
		 * JSONObject otherDataObj2 = new JSONObject(); otherDataObj2.put("name",
		 * "forex_income"); JSONArray otherDataArr2 = new JSONArray(); for (String
		 * forex_income : f_forex_income) { otherDataArr2.add(forex_income); }
		 * otherDataObj2.put("value", otherDataArr2);
		 * 
		 * JSONObject otherDataObj3 = new JSONObject(); otherDataObj3.put("name",
		 * "commission_export_letter_of_credit"); JSONArray otherDataArr3 = new
		 * JSONArray(); for (String commission_export : f_commission_export) {
		 * otherDataArr3.add(commission_export); } otherDataObj3.put("value",
		 * otherDataArr3);
		 * 
		 * JSONObject otherDataObj4 = new JSONObject(); otherDataObj4.put("name",
		 * "other_income"); JSONArray otherDataArr4 = new JSONArray(); for (String
		 * other_income : f_other_income) { otherDataArr4.add(other_income); }
		 * otherDataObj4.put("value", otherDataArr4);
		 * 
		 * JSONObject otherDataObj5 = new JSONObject(); otherDataObj5.put("name",
		 * "fixed_deposit_income"); JSONArray otherDataArr5 = new JSONArray(); for
		 * (String fixed_deposit : f_fixed_deposit) { otherDataArr5.add(fixed_deposit);
		 * } otherDataObj5.put("value", otherDataArr5);
		 * 
		 * otherTypeArr.add(otherDataObj1); otherTypeArr.add(otherDataObj2);
		 * otherTypeArr.add(otherDataObj3); otherTypeArr.add(otherDataObj4);
		 * otherTypeArr.add(otherDataObj5); otherTypeObj.put("others", otherTypeArr);
		 */

		JSONObject otherTypeObj1 = new JSONObject();
		otherTypeObj1.put("name", "average_balance");
		otherTypeObj1.put("value", fAverageBalance);

		JSONObject otherTypeObj2 = new JSONObject();
		otherTypeObj2.put("name", "forex_income");
		otherTypeObj2.put("value", fForexIncome);

		JSONObject otherTypeObj3 = new JSONObject();
		otherTypeObj3.put("name", "commission_export_letter_of_credit");
		otherTypeObj3.put("value", fCommissionExport);

		JSONObject otherTypeObj4 = new JSONObject();
		otherTypeObj4.put("name", "other_income");
		otherTypeObj4.put("value", fOtherIncome);

		JSONObject otherTypeObj5 = new JSONObject();
		otherTypeObj5.put("name", "fixed_deposit_income");
		otherTypeObj5.put("value", fFixedDeposit);

		JSONObject otherTypeObj6 = new JSONObject();
		otherTypeObj6.put("name", "assessment_date");// Mandatory
		otherTypeObj6.put("value", assessmentDate);

		otherTypeArr.add(otherTypeObj1);
		otherTypeArr.add(otherTypeObj2);
		otherTypeArr.add(otherTypeObj3);
		otherTypeArr.add(otherTypeObj4);
		otherTypeArr.add(otherTypeObj5);
		otherTypeArr.add(otherTypeObj6);
		formDataObj.put("others", otherTypeArr);
		/*
		 * otherTypeObj.put("others",otherTypeArr); formDataArr.add(otherTypeObj);
		 */ // 06-07-2024
		// end changes by mohit 11-06-2024 for other section

		customerRequestObject.put("fileData", filesInfoArr);
		customerRequestObject.put("formData", formDataObj);// 06-07-2024
		// customerRequestObject.put("formData", formDataArr);
		log.info("inside customerRequestObject>>>>>>" + customerRequestObject);

		try {
			inputXML = "<EthixInputRequest>" + "<CallName>" + CallName + "</CallName>" + "<IsREST>Y</IsREST>"
					+ "<Format>JSON</Format>" + "<DirectDataBaseFlag>N</DirectDataBaseFlag>"
					+ "<ResponseRequiredInXML>Y</ResponseRequiredInXML>" + "<RequestObject>"
					+ customerRequestObject.toJSONString() + "</RequestObject>" + "<ProcessName>" + processName
					+ "</ProcessName>" + "<WIName>" + wiName + "</WIName>" + "<MsgId>" + getNextT24Sequence()
					+ "</MsgId>" + "<UserId>" + getUserIndex(username) + "</UserId>" + "<UserName>" + username
					+ "</UserName>" + "<WorkStep>" + workstepName + "</WorkStep>" + "</EthixInputRequest>";

			if (inputXML.contains("&")) {
				inputXML = inputXML.replace("&", "&amp;");
			}
			log.info("execCustomerProjected...inputXML==>" + inputXML);
			T24_Integration t24integration = new T24_Integration(processName, wiName);
			log.info("t24integration..." + t24integration);
			String responseXML = t24integration.executeIntegrationCallProj(inputXML);

			log.info("execCustomerProjected...outputXML===>> " + responseXML);

			msg = setT24CallData.setCustomerProjectedDataSen(iformrefrence, wiName, responseXML, inputXML,
					requestDateTime);
			log.info("msg....." + msg);
		} catch (Exception e) {
			log.error("Exception : ", e);
		}
		// }
		return msg;
	}

	// end

	public String getUserIndex(String username) {
		log.info("Inside getUserIndex >");

		String userIndex = "";

		String query = "SELECT USERINDEX FROM PDBUSER WHERE USERNAME='" + username + "'";
		String outputXML = getDataFromDBWithColumns(query, "USERINDEX");
		XMLParser xmlParser = new XMLParser(outputXML);
		log.info("xmlParser" + xmlParser + "outputXML" + outputXML);
		int totalRetrieved = Integer.parseInt(xmlParser.getValueOf(Constants.TOTAL_RETRIEVED));
		log.info("value of total retrieved" + totalRetrieved);
		if (totalRetrieved == 1) {
			userIndex = xmlParser.getValueOf("USERINDEX");
		}

		return userIndex;
	}

	public String getDataFromDBWithColumns(String query, String columns) {
		log.info("Inside getDatafromDBWithColumns >");
		log.info("Query: " + query);
		String data = "";

		List<List<String>> dbData = null;
		try {
			dbData = formObject.getDataFromDB(query);
		} catch (Exception exception) {
			log.error("Error while fetching data from database: " + exception.getMessage());
		} finally {
			if (dbData == null || dbData.isEmpty()) {
				data = "<" + Constants.TOTAL_RETRIEVED + ">0</" + Constants.TOTAL_RETRIEVED + ">";
			} else {
				log.info("DB Data: " + dbData.toString());
				String[] dbColumns = columns.split(",");
				log.info("Columns : " + dbColumns);
				data = "<" + Constants.DATA + "><" + Constants.TOTAL_RETRIEVED + ">" + dbData.size() + "</"
						+ Constants.TOTAL_RETRIEVED + ">";
				for (int r = 0; r < dbData.size(); ++r) {
					log.info("dbdata.size : " + dbData.size());
					data = data + "<" + Constants.ROW + ">";
					for (int c = 0; c < dbData.get(r).size(); ++c) {
						log.info("((List<String>) dbData.get(r)).size() : " + dbData.get(r).size());
						data = data + "<" + dbColumns[c] + ">" + (dbData.get(r).get(c)) + "</" + dbColumns[c] + ">";
						log.info("c " + c);
					}
					data = data + "</" + Constants.ROW + ">";
				}
				data = data + "</" + Constants.DATA + ">";
			}
			log.info("Output XML: " + data);
			dbData = null;
		}

		return data;
	}

	// start edit by mohit 23-07-2024
	public String getDataFromDBWithColumnsNew(String query, String columns, String newcolumn) {
		log.info("Inside getDatafromDBWithColumns >");
		log.info("Query: " + query);
		String data = "";

		List<List<String>> dbData = null;
		try {
			dbData = formObject.getDataFromDB(query);
		} catch (Exception exception) {
			log.error("Error while fetching data from database: " + exception.getMessage());
		} finally {
			if (dbData == null || dbData.isEmpty()) {
				data = "<" + Constants.TOTAL_RETRIEVED + ">0</" + Constants.TOTAL_RETRIEVED + ">";
			} else {
				log.info("DB Data: " + dbData.toString());
				String[] dbColumns = newcolumn.split(",");
				log.info("Columns : " + dbColumns);
				data = "<" + Constants.DATA + "><" + Constants.TOTAL_RETRIEVED + ">" + dbData.size() + "</"
						+ Constants.TOTAL_RETRIEVED + ">";
				for (int r = 0; r < dbData.size(); ++r) {
					log.info("dbdata.size : " + dbData.size());
					data = data + "<" + Constants.ROW + ">";
					for (int c = 0; c < dbData.get(r).size(); ++c) {
						log.info("((List<String>) dbData.get(r)).size() : " + dbData.get(r).size());
						data = data + "<" + dbColumns[c] + ">" + (dbData.get(r).get(c)) + "</" + dbColumns[c] + ">";
						log.info("c " + c);
					}
					data = data + "</" + Constants.ROW + ">";
				}
				data = data + "</" + Constants.DATA + ">";
			}
			log.info("Output XML: " + data);
			dbData = null;
		}

		return data;
	}
	// end edit by mohit 23-07-2024

	public String getNextT24Sequence() {
		log.info("Inside getNextT24Sequence >");

		String value = "";

		String query = "SELECT NEXT VALUE FOR T24_REST_REQUEST_SEQ";
		String outputXML = getDataFromDBWithColumns(query, "VALUE");
		XMLParser xmlParser = new XMLParser(outputXML);
		log.info("xmlParser" + xmlParser + "outputXML" + outputXML);
		int totalRetrieved = Integer.parseInt(xmlParser.getValueOf(Constants.TOTAL_RETRIEVED));
		log.info("value of total retrieved" + totalRetrieved);
		if (totalRetrieved == 1) {
			value = xmlParser.getValueOf("VALUE");
		}

		return value;
	}

	public void setMultipleFieldValues(String[] controlNames, List<List<String>> resultSet) {
		log.info("Reshank Check");
		for (int i = 0; i < controlNames.length; i++) {
			formObject.setValue(controlNames[i], resultSet.get(0).get(i));
			log.info(controlNames[i] + " = " + resultSet.get(0).get(i));
		}
	}

	public void setMultipleNullValues(String[] controlNames, List<List<String>> resultSet) {
		for (int i = 0; i < controlNames.length; i++) {
			log.info(">>>>setMultipleNullValues : " + formObject.getValue(controlNames[i]) + " "
					+ resultSet.get(0).get(i));
			if (formObject.getValue(controlNames[i]) == null || formObject.getValue(controlNames[i]).equals("")) {
				log.info("setMultipleNullValues :>>>>>>> ");
				formObject.setValue(controlNames[i], resultSet.get(0).get(i));
			}
		}
	}

	public void setFacilityData(String DBColumn, String TableName, String FacilityID, String[] Field) {
		try {
			String query = "SELECT " + DBColumn + " FROM " + TableName + "" + " WHERE WI_NAME='"
					+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "'and FACILITY_ID ='" + FacilityID
					+ "'";
			log.info("setFacilityData query : " + query);
			List<List<String>> sOutput = formObject.getDataFromDB(query);
			log.info("setFacilityData sOutput :" + sOutput);
			if (sOutput != null && !sOutput.isEmpty()) {
				log.info("setFacilityData field :" + Arrays.toString(Field));
				setMultipleFieldValues(Field, sOutput);
			}
		} catch (Exception e) {
			log.error("Exception : ", e);
		}
	}

	// start edit by mohit 29-07-2024
	public void setFacilityDataCFM(String DBColumn, String TableName, String[] Field) {
		try {
			String query = "SELECT " + DBColumn + " FROM " + TableName + "" + " WHERE WI_NAME='"
					+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "'";
			log.info("setFacilityDataCFM query : " + query);
			List<List<String>> sOutput = formObject.getDataFromDB(query);
			log.info("setFacilityDataCFM sOutput :" + sOutput);
			if (sOutput != null && !sOutput.isEmpty()) {
				log.info("setFacilityDataCFM field :" + Arrays.toString(Field));
				setMultipleFieldValues(Field, sOutput);
			}
		} catch (Exception e) {
			log.error("Exception : ", e);
		}
	}
	// end edit by mohit 29-07-2024

//	public void setApprovedData(String DBColumn, String TableName, String[] Field) {
//		try {
//			String query = "SELECT " + DBColumn + " FROM " + TableName + "" + " WHERE WI_NAME='" + formObject.getObjGeneralData().getM_strProcessInstanceId()
//					+ "'and FACILITY_ID ='" + FacilityID + "'";
//			log.info("setFacilityData query : " + query);
//			List<List<String>> sOutput = formObject.getDataFromDB(query);
//			log.info("setFacilityData sOutput :" + sOutput);
//			if (sOutput != null && !sOutput.isEmpty()) {
//				log.info("setFacilityData field :" + Arrays.toString(Field));
//				setMultipleFieldValues(Field, sOutput);
//			}
//		} catch (Exception e) {
//			log.error("Exception : ", e);
//		}
//	}
	// Added by Shivanshu
	public int updateDataInDB(String tableName, String sColumn, String sValue, String sWhere) {
		int output = -1;
		StringBuilder query = new StringBuilder();
		try {
			query.append("UPDATE " + tableName + " SET ");
			String[] columnArray = sColumn.split(",");
			String[] valueArray = sValue.split(Character.toString((char) 25));
			log.info("updateDataInDB valueArray : " + Arrays.toString(valueArray));
			log.info("updateDataInDB columnArray : " + Arrays.toString(columnArray));
			if (columnArray != null) {

				for (int i = 0; i < columnArray.length; i++) {
					query.append(columnArray[i] + " = ");

					if (i == columnArray.length - 1) {

						if ("'".equalsIgnoreCase(valueArray[i].substring(0, 1)))
							query.append(
									"'" + valueArray[i].substring(1, valueArray[i].length() - 1).replaceAll("'", "''")
											+ "' ");
						else
							query.append(valueArray[i].replaceAll("'", "''"));
					} else if ("'".equalsIgnoreCase(valueArray[i].substring(0, 1)))
						query.append("'" + valueArray[i].substring(1, valueArray[i].length() - 1).replaceAll("'", "''")
								+ "' , ");
					else
						query.append(valueArray[i].replaceAll("'", "''") + ",");
				}
			}
			if (sWhere != null && !"".equals(sWhere)) {
				query.append(" WHERE " + sWhere);
			}
			log.info("updateDataInDB New Final Update Query : " + query.toString());
			output = formObject.saveDataInDB(query.toString());
			return output;
		} catch (Exception e) {
			log.error("updateDataInDB", e);
		}
		return output;
	}

	// Added by Shivanshu
	public void getUpdateFacilityData(String facilityID, IFormReference formObject) {
		log.info("Inside getUpdateFacilityData!!!!!");
		int sOutputUpdate = 0;
		int sOutputUpdateSens = 0;
		String modifyFlag = "Y";
		String sWhere = " FACILITY_ID = '" + facilityID + "' AND WI_NAME='"
				+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "'";
		try {
			log.info("Inside getUpdateFacilityData : Inside Proposed and Sens Data Set ");
			// Proposed Data Update
			String curr_proposed = formObject.getValue(CURRENCY_PROPOSED).toString();
			String facility_amt_proposed = formObject.getValue(FACILITY_AMOUNT_PROPOSED).toString();
			String tenor_proposed = formObject.getValue(TENOR_PROPOSED).toString();
			String tenorUnit_proposed = formObject.getValue(TENOR_UNIT_PROPOSED).toString();
			String expDate_proposed = formObject.getValue(EXPIRY_DATE_PROPOSED).toString();
			String cashMargin_proposed = formObject.getValue(CASH_MARGIN_PROPOSED).toString();
			String outAmt_proposed = formObject.getValue(OUTSTANDING_AMOUNT_PROPOSED).toString();
			String pricing_proposed = formObject.getValue(PRICING_PROPOSED).toString();
			String index_proposed = formObject.getValue(INDEX_PROPOSED).toString();
			log.info("index_proposed...." + index_proposed);
			String indexRate_proposed = formObject.getValue(INDEX_RATE_PROPOSED).toString();
			String marginComm_proposed = formObject.getValue(MARGIN_COMMISION_PROPOSED).toString();
			String value_proposed = formObject.getValue(VALUE_PROPOSED).toString();
			log.info("value_proposed proposed" + value_proposed);
			String min_proposed = formObject.getValue(MINIMUM_PROPOSED).toString();
			String max_proposed = formObject.getValue(MAXIMUM_PROPOSED).toString();
			String expectedRet_Proposed = formObject.getValue(EXPECTED_YEAR_RETURN_PROPOSED).toString();
			String marketingRisk_Proposed = formObject.getValue(MARKETING_RISK_CAPITAL_PROPOSED).toString();
			String counterPartyRate = formObject.getValue(COUNTERPARTY_RATE_PROPOSED).toString();
			String counterPartyType = formObject.getValue(COUNTERPARTY_TYP_PROPOSED).toString();
			String repricingFreq_proposed = formObject.getValue(REPRICING_FREQUENCY_PROPOSED).toString();
			String specialCost_proposed = formObject.getValue(SPL_COST_PROPOSED).toString();

			String repayFreq_proposed = formObject.getValue(REPAYMENT_FREQUENCY_PROPOSED).toString();// bp05 //nc
			String moramPeriod_proposed = formObject.getValue(MORATORIUM_PERIOD_PROPOSED).toString();
			String repayType_proposed = formObject.getValue(REPAYMENT_TYPE_PROPOSED).toString();
			String creditLimit_Proposed = formObject.getValue(CREDIT_LIMIT_PROPOSED).toString();
			String eqvAEDAmt_proposed = formObject.getValue(EQV_AED_AMOUNT_PROPOSED).toString();
			String ecl_proposed = formObject.getValue(ECL_PROPOSED).toString();
			String riskCap_proposed = formObject.getValue(RISK_CAPITAL_PROPOSED).toString();
			String riskReturn_proposed = formObject.getValue(RISK_ADJUSTED_RETURN_PROPOSED).toString();
			String facilityRAROC_proposed = formObject.getValue(FACILITY_RAROC_PROPOSED).toString();
			String avgUtil_proposed = formObject.getValue(AVERAGE_UTILISED_PROPOSED).toString();
			String remTerm_proposed = formObject.getValue(REMAINING_TERMS_IN_MONTHS_PROPOSED).toString();
			String FTPRate_proposed = formObject.getValue(FTP_RATE_PROPOSED).toString();
			log.info("FTPRate_proposed " + FTPRate_proposed);
			String interestRate_proposed = formObject.getValue(INTEREST_RATE_APPLIED_PROPOSED).toString();

			String collateralName_proposed = formObject.getValue(COLLATERAL_NAME_PROPOSED).toString();
			String collateralType_proposed = formObject.getValue(COLLATRAL_TYPE_PROPOSED).toString();
			String collateralAmt_proposed = formObject.getValue(COLLATERAL_AMOUNT_PROPOSED).toString();
			String collateralCurr_proposed = formObject.getValue(COLLATRAL_CURRENCY_PROPOSED).toString();
			String collateralLienAmt_proposed = formObject.getValue(COLLATRAL_LIEN_AMOUNT_PROPOSED).toString();
			String collateralLienInt_proposed = formObject.getValue(COLLATRAL_LIEN_INTEREST_PROPOSED).toString();
			String collateralTenure_proposed = formObject.getValue(COLLATERAL_LIAN_TENURE_PROPOSED).toString();
			String borrowerRate_proposed = formObject.getValue(BORROWER_RATING_PROPOSED).toString();
			String comittmentFee_proposed = formObject.getValue(COMMITMENT_FEE_PROPOSED).toString();
			String upfrontFee_proposed = formObject.getValue(UPFRONT_FEE_PROPOSED).toString();
			String incomeName = formObject.getValue(INCOME_NAME).toString();
			String incomeType = formObject.getValue(INCOME_TYPE).toString();
			String incomePercent = formObject.getValue(INCOME_PERCENTAGE).toString();
			String incomeAbs = formObject.getValue(INCOME_ABSOLUTE).toString();

			String facility_average_balance = formObject.getValue(FACILITY_AVERAGE_BALANCE).toString();
			String facility_forex_income = formObject.getValue(FACILITY_FOREX_INCOME).toString();
			String facility_commission_export = formObject.getValue(FACILITY_COMMISSION_EXPORT).toString();
			String facility_other_income = formObject.getValue(FACILITY_OTHER_INCOME).toString();
			String facility_fixed_deposit = formObject.getValue(FACILITY_FIXED_DEPOSIT).toString();

			String Collateral_Number_Proposed = formObject.getValue(COLLATERAL_NUMBER_PROPOSED).toString();
			String Allocation_Percentage_Proposed = formObject.getValue(ALLOCATION_PERCENTAGE_PROPOSED).toString();
			String index_key_proposed = formObject.getValue(INDEX_KEY_PROPOSED).toString();
			log.info("index_key_proposed...." + index_key_proposed);
			if (index_key_proposed.equalsIgnoreCase("")) {
				// start edit by mohit 04-09-2024 for bug : index key not getting saved in db
				// for first facility.
				if (index_proposed.equalsIgnoreCase("REF - CB EIBOR"))
					index_key_proposed = "EIBOR";
				else if (index_proposed.equalsIgnoreCase("REF-SOFR"))
					index_key_proposed = "SOFR";

				log.info("Checking index key value..." + index_key_proposed);
				/*
				 * List<List<String>> sOutputlist = formObject
				 * .getDataFromDB("select index_key_proposed from NG_RAROC_FACILITY_DETAILS where wi_name='"
				 * + formObject.getObjGeneralData().getM_strProcessInstanceId() + "'");
				 * 
				 * log.info("setParentData sOutputlist : " + sOutputlist); if (sOutputlist !=
				 * null && !sOutputlist.isEmpty()) { index_key_proposed =
				 * sOutputlist.get(0).get(0); }
				 */

				// end edit by mohit 04-09-2024 for bug : index key not getting saved in db for
				// first facility.
			}
			String index_tenor_proposed = formObject.getValue(INDEX_TENOR_PROPOSED).toString();
			log.info("index_tenor_proposed...." + index_tenor_proposed);
			String index_tenor_unit_proposed = formObject.getValue(INDEX_TENOR_UNIT_PROPOSED).toString();
			log.info("index_tenor_unit_proposed...." + index_tenor_unit_proposed);

			// shikha
			String utilization_proposed = formObject.getValue(UTILIZATION_PROPOSED).toString();
			log.info("utilization_proposed...." + utilization_proposed);
			String ftp_over_proposed = formObject.getValue(FTP_OVERRIDE_PROPOSED).toString();
			// shikha

			if (repayType_proposed.equalsIgnoreCase("Bullet")) {// b05
				repayFreq_proposed = "Bullet";
			}

			log.info("Repayment Freq===" + repayFreq_proposed);
			log.info("Repayment Type===" + repayType_proposed);
			log.info("getUpdateFacilityData sColumn" + RAROC_FACILITY_PROPOSED_COLUMN);

			String sValues = "'" + curr_proposed + "'" + (char) 25 + "'" + facility_amt_proposed + "'" + (char) 25 + "'"
					+ tenor_proposed + "'" + (char) 25 + "'" + tenorUnit_proposed + "'" + (char) 25 + "'"
					+ expDate_proposed + "'" + (char) 25 + "'" + cashMargin_proposed + "'" + (char) 25 + "'"
					+ outAmt_proposed + "'" + (char) 25 + "'" + pricing_proposed + "'" + (char) 25 + "'"
					+ index_proposed + "'" + (char) 25 + "'" + indexRate_proposed + "'" + (char) 25 + "'"
					+ marginComm_proposed + "'" + (char) 25 + "'" + value_proposed + "'" + (char) 25 + "'"
					+ min_proposed + "'" + (char) 25 + "'" + max_proposed + "'" + (char) 25 + "'" + expectedRet_Proposed
					+ "'" + (char) 25 + "'" + marketingRisk_Proposed + "'" + (char) 25 + "'" + counterPartyRate + "'"
					+ (char) 25 + "'" + counterPartyType + "'" + (char) 25 + "'" + repricingFreq_proposed + "'"
					+ (char) 25 + "'" + specialCost_proposed + "'" + (char) 25 + "'"

					+ repayFreq_proposed + "'" + (char) 25 + "'" + moramPeriod_proposed + "'" + (char) 25 + "'"
					+ repayType_proposed + "'" + (char) 25 + "'" + creditLimit_Proposed + "'" + (char) 25 + "'"
					+ eqvAEDAmt_proposed + "'" + (char) 25 + "'" + ecl_proposed + "'" + (char) 25 + "'"
					+ riskCap_proposed + "'" + (char) 25 + "'" + riskReturn_proposed + "'" + (char) 25 + "'"
					+ facilityRAROC_proposed + "'" + (char) 25 + "'" + avgUtil_proposed + "'" + (char) 25 + "'"
					+ remTerm_proposed + "'" + (char) 25 + "'" + FTPRate_proposed + "'" + (char) 25 + "'"
					+ interestRate_proposed + "'" + (char) 25 + "'"

					+ collateralName_proposed + "'" + (char) 25 + "'" + collateralType_proposed + "'" + (char) 25 + "'"
					+ collateralAmt_proposed + "'" + (char) 25 + "'" + collateralCurr_proposed + "'" + (char) 25 + "'"
					+ collateralLienAmt_proposed + "'" + (char) 25 + "'" + collateralLienInt_proposed + "'" + (char) 25
					+ "'" + collateralTenure_proposed + "'" + (char) 25 + "'" + borrowerRate_proposed + "'" + (char) 25
					+ "'" + comittmentFee_proposed + "'" + (char) 25 + "'" + upfrontFee_proposed + "'" + (char) 25 + "'"
					+ incomeName + "'" + (char) 25 + "'" + incomeType + "'" + (char) 25 + "'" + incomePercent + "'"
					+ (char) 25 + "'" + incomeAbs + "'" + (char) 25 + "'" + Collateral_Number_Proposed + "'" + (char) 25
					+ "'" + Allocation_Percentage_Proposed + "'" + (char) 25 + "'" + index_key_proposed + "'"
					+ (char) 25 + "'" + index_tenor_proposed + "'" + (char) 25 + "'" + index_tenor_unit_proposed + "'"
					+ (char) 25 + "'" + utilization_proposed + "'" + (char) 25 + "'" + ftp_over_proposed + "'";

			sOutputUpdate = updateDataInDB(RAROC_FACILITY_DETAILS_TABLE, RAROC_FACILITY_PROPOSED_COLUMN, sValues,
					sWhere);
			log.info("getUpdateFacilityData : " + sOutputUpdate);
			// Added by Shivanshu Umrao
			if (sOutputUpdate == 0) {
				modifyFlag = "N";
			}
			String modifyQuery = "UPDATE " + RAROC_FACILITY_DETAILS_TABLE + " SET MODIFY_FLAG = '" + modifyFlag
					+ "' WHERE " + sWhere;

			log.info("getUpdateFacilityData : " + modifyQuery);
			int output = formObject.saveDataInDB(modifyQuery.toString());
			log.info("getUpdateFacilityData : " + output);
			// END
		} catch (Exception e) {
			log.error("Exception in checkpreviousStage: ", e);
		}

	}

	// Added by shivanshu
	public String inputXML(IFormReference iformRefrence, String requestParameter) {
		log.info("inputXML INSIDE>>> ");
		String username = iformRefrence.getUserName();
		String processName = iformRefrence.getProcessName();
		log.info("processName groupRealisedDetails " + processName);
		String CallName = "customerRealisedDetails";
		String wiName = iformRefrence.getObjGeneralData().getM_strProcessInstanceId();
		String workstepName = iformRefrence.getActivityName();

		String inputXML = "<EthixInputRequest>" + "<CallName>" + CallName + "</CallName>" + "<IsREST>Y</IsREST>"
				+ "<Format>JSON</Format>" + "<DirectDataBaseFlag>Y</DirectDataBaseFlag>"
				+ "<ResponseRequiredInXML>N</ResponseRequiredInXML>" + "<Params>" + requestParameter + "</Params>"
				+ "<ProcessName>" + processName + "</ProcessName>" + "<WIName>" + wiName + "</WIName>" + "<MsgId>"
				+ getNextT24Sequence() + "</MsgId>" + "<UserId>" + getUserIndex(username) + "</UserId>" + "<UserName>"
				+ username + "</UserName>" + "<WorkStep>" + workstepName + "</WorkStep>" + "</EthixInputRequest>";

		log.info("inputXML : outputXML===>> " + inputXML);

		return inputXML;
	}

	public void insertIntoRarocIntegrationLogs(IFormReference iFormReference, String callType, String inputXML,
			String responseXml, String requestDateTime, String Status) { // throws Exception {// prod_04112024
		try {
			log.info("Inside insertIntoPLIntegrationLogs #######");
			String outputXML = responseXml;
			String query = "INSERT INTO " + Constants.NG_RAROC_API_LOGS
					+ "(WI_NAME, CALL_TYPE, INPUT_XML, OUTPUT_XML, AUDIT_DATETIME,STATUS) " + "VALUES " + "('"
					+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "', '" + callType + "', '"
					+ inputXML.replaceAll("'", "''") + "', '" + outputXML.replaceAll("'", "''")
					+ "', CONVERT(DATETIME, '" + requestDateTime + "'),'" + Status + "')";
			log.info("Query: " + query);
			int queryStatus = iFormReference.saveDataInDB(query);
			log.info("Query Execution Status: " + queryStatus);
		} catch (Exception e) {
			log.info("Exception inside insertIntoRarocIntegrationLogs: " + e);
		}
		/*
		 * if (queryStatus == -1) { throw new Exception(
		 * "Error occurred in insertion of integration logs data. Please contact technical support team."
		 * ); }
		 */

	}

	public int getGridCount(String controlName) {
		int count = 0;
		try {
			JSONArray jsonArray = formObject.getDataFromGrid(controlName);
			count = jsonArray.size();
		} catch (Exception e) {
			log.error("Exception in getListCount: " + e, e);
		}
		return count;
	}

//   	private boolean validateModifyFacilityData(String facilityID){
//		log.info("facilityID :"+facilityID);
//		String gridListName=LISTVIEW_FACILITY;
//		int length = getGridCount(gridListName);
//		log.info("length : " + length);
//		for(int i=0;i<length;i++){
//			log.info("value : "+formObject.getTableCellValue(gridListName, i, 0));
//			if(partyType.equalsIgnoreCase(formObject.getTableCellValue(gridListName, i, 0)))
//			{
//				log.info("inside kundli found");
//				sendMessageHashMap(sFieldName,"Party Type already exist");
//				return false;
//			}
//		}
//		return true;
//	}

	public List<List<String>> getCADataWithColumn(String srcColumnName, String srcTableName) {
		List<List<String>> output = null;
		try {
//   			String wiName = "SELECT CA_WI_NAME  FROM ALM_RAROC_EXT_TABLE WHERE WI_NAME='"+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "'";
//   			List<List<String>>  sOutput = formObject.getDataFromDB(wiName);
//   			wiName =  sOutput.get(0).get(0);
			String query = "SELECT " + srcColumnName + "  FROM " + srcTableName + " WHERE WI_NAME = (SELECT CA_WI_NAME "
					+ " FROM ALM_RAROC_EXT_TABLE WHERE WI_NAME='"
					+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "')";
			log.info("getCADataWithColumn query : " + query);
			output = formObject.getDataFromDB(query);
			log.info("getCADataWithColumn output : " + output);
		} catch (Exception e) {
			log.error("Exception : " + e);
		}
		return output;
	}

	// Added by Shivanshu
	public void getUpdateData(String tableName, String columnName, String facilityID) {
		int output = -1;
		String query = "";
		try {
			query = "UPDATE " + tableName + " SET " + columnName + "  WHERE WI_NAME='"
					+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "' and FACILITY_ID='" + facilityID
					+ "'";

			log.info("Inside getUpdateData Update Query : " + query);
			output = formObject.saveDataInDB(query);
			log.info("Inside getUpdateData Output: " + output);

		} catch (Exception e) {
			log.error("Exception : " + e);
		}

	}

	public void getUpdateSensitizedData(String facilityID, IFormReference formObject) {
		log.info("Inside getUpdateSensitizedData");
		int sOutputUpdateSens = 0;
		String modifyFlag = "Y";
		String sWhere = " FACILITY_ID = '" + facilityID + "' AND WI_NAME='"
				+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "'";
		try {

			// Sensitized Data Update
			String curr_sensitized = formObject.getValue(CURRENCY_SENSITIZED).toString();
			String facility_amt_sensitized = formObject.getValue(FACILITY_AMOUNT_SENSITIZED).toString();
			String tenor_sensitized = formObject.getValue(TENOR_SENSITIZED).toString();
			String tenorUnit_sensitized = formObject.getValue(TENOR_UNIT_SENSITIZED).toString();
			String expDate_sensitized = formObject.getValue(EXPIRY_DATE_SENSITIZED).toString();
			String cashMargin_sensitized = formObject.getValue(CASH_MARGIN_SENSITIZED).toString();
			String outAmt_sensitized = formObject.getValue(OUTS_AMNT_SENSITIZED).toString();
			String pricing_sensitized = formObject.getValue(PRICING_SENSITIZED).toString();
			String index_sensitized = formObject.getValue(INDEX_SENSITIZED).toString();
			String indexRate_sensitized = formObject.getValue(INDEX_RATE_SENSITIZED).toString();
			String marginComm_sensitized = formObject.getValue(MARGIN_COMMISION_SENSITIZED).toString();
			String value_sensitized = formObject.getValue(VALUE_SENSITIZED).toString();
			String min_sensitized = formObject.getValue(MINIMUM_SENSITIZED).toString();
			String max_sensitized = formObject.getValue(MAXIMUM_SENSITIZED).toString();
			String expectedRet_Proposed = formObject.getValue(EXP_RET_SENSITIZED).toString();
			String marketingRisk_Proposed = formObject.getValue(MAR_RIS_SENSITIZED).toString();
			String counterPartyRate = formObject.getValue(COUNT_INT_SENSITIZED).toString();
			String counterPartyType = formObject.getValue(COUNT_TYP_SENSITIZED).toString();
			String repricingFreq_sensitized = formObject.getValue(REP_FREQ_SENSITIZED).toString();
			String specialCost_sensitized = formObject.getValue(SPC_COST_SENSITIZED).toString();

			String repayFreq_sensitized = formObject.getValue(REPAY_FREQ_SENSITIZED).toString();// bp05 //d
			String moramPeriod_sensitized = formObject.getValue(REP_REVOLVING_SENSITIZED).toString();
			String repayType_sensitized = formObject.getValue(REP_TYP_SENSITIZED).toString();
			String creditLimit_Proposed = formObject.getValue(CREDIT_SENSITIZED).toString();
			String eqvAEDAmt_sensitized = formObject.getValue(EQV_AED_AMOUNT_SENSITIZED).toString();
			String ecl_sensitized = formObject.getValue(ECL_SENSITIZED).toString();
			String riskCap_sensitized = formObject.getValue(RISK_CAPITAL_SENSITIZED).toString();
			String riskReturn_sensitized = formObject.getValue(RISK_RETURN_SENSITIZED).toString();
			String facilityRAROC_sensitized = formObject.getValue(FACILITY_RAROC_SENSITIZED).toString();
			String avgUtil_sensitized = formObject.getValue(AVG_SENSITIZED).toString();
			String remTerm_sensitized = formObject.getValue(REMAIN_TRM_SENSITIZED).toString();
			String FTPRate_sensitized = formObject.getValue(FTP_SENSITIZED).toString();
			String interestRate_sensitized = formObject.getValue(INT_RATE_SENSITIZED).toString();

			String collateralName_sensitized = formObject.getValue(COLLATERAL_NAME_SENSITIZED).toString();
			String collateralType_sensitized = formObject.getValue(COLLATERAL_TYPE_SENSITIZED).toString();
			String collateralAmt_sensitized = formObject.getValue(COLLATERAL_AMOUNT_SENSITIZED).toString();
			String collateralCurr_sensitized = formObject.getValue(COLLATRAL_CURRENCY_SENSITIZED).toString();
			String collateralLienAmt_sensitized = formObject.getValue(COLLATRAL_LIEN_AMOUNT_SENSITIZED).toString();
			String collateralLienInt_sensitized = formObject.getValue(COLLATRAL_LIEN_INTEREST_SENSITIZED).toString();
			String collateralTenure_sensitized = formObject.getValue(COLLATERAL_LIAN_TENURE_SENSITIZED).toString();
			String borrowerRate_sensitized = formObject.getValue(BORROWER_RATING_SENSITIZED).toString();
			String comittmentFee_sensitized = formObject.getValue(COMMITMENT_FEE_SENSITIZED).toString();
			String upfrontFee_sensitized = formObject.getValue(UPFRONT_FEE_SENSITIZED).toString();
			String incomeName = formObject.getValue(INCOME_NAME).toString();
			String incomeType = formObject.getValue(INCOME_TYPE).toString();
			String incomePercent = formObject.getValue(INCOME_PERCENTAGE).toString();
			String incomeAbs = formObject.getValue(INCOME_ABSOLUTE).toString();

			String collateral_number_sensitized = formObject.getValue(COLLATERAL_NUMBER_SENSITIZED).toString();
			String allocation_percentage_sensitized = formObject.getValue(ALLOCATION_PERCENTAGE_SENSITIZED).toString();
			String index_key_sensitized = formObject.getValue(INDEX_KEY_SENSITIZED).toString();
			String index_tenor_sensitized = formObject.getValue(INDEX_TENOR_SENSITIZED).toString();
			String index_tenor_unit_sensitized = formObject.getValue(INDEX_TENOR_UNIT_SENSITIZED).toString();

			// changes by shikha 17-07-24
			String utilization_sensitized = formObject.getValue(UTILIZATION_SENSITIZED).toString();
			String ftp_over_sensitized = formObject.getValue(FTP_OVERRIDE_SENSITIZED).toString();
			// changes by reshank on 02-08-24
			if (formObject.getValue(REP_TYP_SENSITIZED).toString().equalsIgnoreCase("Bullet")) {
				repayType_sensitized = "Bullet";
			}

			log.info("getUpdateSensitizedData sColumn" + RAROC_FACILITY_SENSITIZED_COLUMN);

			String sValues = "'" + curr_sensitized + "'" + (char) 25 + "'" + facility_amt_sensitized + "'" + (char) 25
					+ "'" + tenor_sensitized + "'" + (char) 25 + "'" + tenorUnit_sensitized + "'" + (char) 25 + "'"
					+ expDate_sensitized + "'" + (char) 25 + "'" + cashMargin_sensitized + "'" + (char) 25 + "'"
					+ outAmt_sensitized + "'" + (char) 25 + "'" + pricing_sensitized + "'" + (char) 25 + "'"
					+ index_sensitized + "'" + (char) 25 + "'" + indexRate_sensitized + "'" + (char) 25 + "'"
					+ marginComm_sensitized + "'" + (char) 25 + "'" + value_sensitized + "'" + (char) 25 + "'"
					+ min_sensitized + "'" + (char) 25 + "'" + max_sensitized + "'" + (char) 25 + "'"
					+ expectedRet_Proposed + "'" + (char) 25 + "'" + marketingRisk_Proposed + "'" + (char) 25 + "'"
					+ counterPartyRate + "'" + (char) 25 + "'" + counterPartyType + "'" + (char) 25 + "'"
					+ repricingFreq_sensitized + "'" + (char) 25 + "'" + specialCost_sensitized + "'" + (char) 25 + "'"

					+ repayFreq_sensitized + "'" + (char) 25 + "'" + moramPeriod_sensitized + "'" + (char) 25 + "'"
					+ repayType_sensitized + "'" + (char) 25 + "'" + creditLimit_Proposed + "'" + (char) 25 + "'"
					+ eqvAEDAmt_sensitized + "'" + (char) 25 + "'" + ecl_sensitized + "'" + (char) 25 + "'"
					+ riskCap_sensitized + "'" + (char) 25 + "'" + riskReturn_sensitized + "'" + (char) 25 + "'"
					+ facilityRAROC_sensitized + "'" + (char) 25 + "'" + avgUtil_sensitized + "'" + (char) 25 + "'"
					+ remTerm_sensitized + "'" + (char) 25 + "'" + FTPRate_sensitized + "'" + (char) 25 + "'"
					+ interestRate_sensitized + "'" + (char) 25 + "'"

					+ collateralName_sensitized + "'" + (char) 25 + "'" + collateralType_sensitized + "'" + (char) 25
					+ "'" + collateralAmt_sensitized + "'" + (char) 25 + "'" + collateralCurr_sensitized + "'"
					+ (char) 25 + "'" + collateralLienAmt_sensitized + "'" + (char) 25 + "'"
					+ collateralLienInt_sensitized + "'" + (char) 25 + "'" + collateralTenure_sensitized + "'"
					+ (char) 25 + "'" + borrowerRate_sensitized + "'" + (char) 25 + "'" + comittmentFee_sensitized + "'"
					+ (char) 25 + "'" + upfrontFee_sensitized + "'" + (char) 25 + "'" + incomeName + "'" + (char) 25
					+ "'" + incomeType + "'" + (char) 25 + "'" + incomePercent + "'" + (char) 25 + "'" + incomeAbs + "'"
					+ (char) 25 + "'" + collateral_number_sensitized + "'" + (char) 25 + "'"
					+ allocation_percentage_sensitized + "'" + (char) 25 + "'" + index_key_sensitized + "'" + (char) 25
					+ "'" + index_tenor_sensitized + "'" + (char) 25 + "'" + index_tenor_unit_sensitized + "'"
					+ (char) 25 + "'" + utilization_sensitized + "'" + (char) 25 + "'" + ftp_over_sensitized + "'";

			sOutputUpdateSens = updateDataInDB(RAROC_FACILITY_DETAILS_TABLE, RAROC_FACILITY_SENSITIZED_COLUMN, sValues,
					sWhere);
			log.info("getUpdateSensitizedData : " + sOutputUpdateSens);
			// Added by Shivanshu Umrao
			if (sOutputUpdateSens == 0) {
				modifyFlag = "N";
			}
			String modifyQuery = "UPDATE " + RAROC_FACILITY_DETAILS_TABLE + " SET MODIFY_FLAG = '" + modifyFlag
					+ "' WHERE " + sWhere;

			log.info("getUpdateFacilityData : " + modifyQuery);
			int output = formObject.saveDataInDB(modifyQuery.toString());
			log.info("getUpdateFacilityData : " + output);
			// END
		} catch (Exception e) {
			log.error("Exception in checkpreviousStage: ", e);
		}

	}

	// start edit by mohit 02-08-2024 for updating the data from form to db for
	// approved fields
	public void getUpdateApprovedData(String facilityID, IFormReference formObject) {
		log.info("Inside getUpdateSensitizedData");
		int sOutputUpdateApp = 0;
		String modifyFlag = "Y";
		String sWhere = " FACILITY_ID = '" + facilityID + "' AND WI_NAME='"
				+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "'";
		try {

			// Sensitized Data UpdatesWorkitemID
			String curr_approved = formObject.getValue(CURRENCY_APPROVED).toString();
			String facility_amt_approved = formObject.getValue(FACILITY_AMOUNT_APPROVED).toString();
			String tenor_approved = formObject.getValue(TENOR_APPROVED).toString();
			String tenorUnit_approved = formObject.getValue(TENOR_UNIT_APPROVED).toString();
			String expDate_approved = formObject.getValue(EXPIRY_DATE_APPROVED).toString();
			String cashMargin_approved = formObject.getValue(CASH_MARGIN_APPROVED).toString();
			String outAmt_approved = formObject.getValue(OUTSTANDING_AMOUNT_APPROVED).toString();
			String pricing_approved = formObject.getValue(PRICING_APPROVED).toString();
			String index_approved = formObject.getValue(INDEX_APPROVED).toString();
			String indexRate_approved = formObject.getValue(INDEX_RATE_APPROVED).toString();
			String marginComm_approved = formObject.getValue(MARGIN_COMMISION_APPROVED).toString();
			String value_approved = formObject.getValue(VALUE_APPROVED).toString();
			String min_approved = formObject.getValue(MINIMUM_APPROVED).toString();
			String max_approved = formObject.getValue(MAXIMUM_APPROVED).toString();
			String expectedRet_approved = formObject.getValue(EXP_1_YEAR_RATE_APPROVED).toString();
			String marketingRisk_approved = formObject.getValue(MARKETING_RISK_APPROVED).toString();
			String counterPartyRate_approved = formObject.getValue(COUNTERPARTY_INTERNAL_APPROVED).toString();
			String counterPartyType_approved = formObject.getValue(COUNTERPARTY_TYPE_APPROVED).toString();
			String repricingFreq_approved = formObject.getValue(REPRICING_FREQ_APPROVED).toString();
			String specialCost_approved = formObject.getValue(SPECIAL_COST_APPROVED).toString();

			String repayFreq_approved = formObject.getValue(REPAYMENT_TYPE_APPROVED).toString();
			String moramPeriod_approved = formObject.getValue(MORATORIUM_PERIOD_APPROVED).toString();
			String repayType_approved = formObject.getValue(REPAYMENT_FREQUENCY_APPROVED).toString();
			String creditLimit_approved = formObject.getValue(CREDIT_LIMIT_APPROVED).toString();
			String eqvAEDAmt_approved = formObject.getValue(EQV_AED_AMOUNT_APPROVED).toString();
			String ecl_approved = formObject.getValue(ECL_APPROVED).toString();
			String riskCap_approved = formObject.getValue(RISK_CAPITAL_APPROVED).toString();
			String riskReturn_approved = formObject.getValue(RISK_RETURN_APPROVED).toString();
			String facilityRAROC_approved = formObject.getValue(FACILITY_RAROC_APPROVED).toString();
			String avgUtil_approved = formObject.getValue(AVG_UTILISED_APPROVED).toString();
			String remTerm_approved = formObject.getValue(REMAINING_TERM_IN_MONTH_APPROVED).toString();
			String FTPRate_approved = formObject.getValue(FTP_APPROVED).toString();
			String interestRate_approved = formObject.getValue(INTEREST_RATE_APPROVED).toString();

			String collateralName_approved = formObject.getValue(COLLATRAL_NAME_APPROVED).toString();
			String collateralType_approved = formObject.getValue(COLLATRAL_TYPE_APPROVED).toString();
			String collateralAmt_approved = formObject.getValue(COLLATRAL_AMOUNT_APPROVED).toString();
			String collateralCurr_approved = formObject.getValue(COLLATRAL_CURRENCY_APPROVED).toString();
			String collateralLienAmt_approved = formObject.getValue(COLLATRAL_LIEN_AMOUNT_APPROVED).toString();
			String collateralLienInt_approved = formObject.getValue(COLLATRAL_LIEN_INTEREST_APPROVED).toString();
			String collateralTenure_approved = formObject.getValue(COLLATRAL_LIEN_TENURE_APPROVED).toString();
			String borrowerRate_approved = formObject.getValue(BORROWER_RATING_APPROVED).toString();
			String comittmentFee_approved = formObject.getValue(COMMITMENT_FEE_APPROVED).toString();
			String upfrontFee_approved = formObject.getValue(UPFRONT_FEE_APPROVED).toString();

			String collateral_number_approved = formObject.getValue(COLLATERAL_NUMBER_APPROVED).toString();
			String allocation_percentage_approved = formObject.getValue(ALLOCATION_PERCENTAGE_APPROVED).toString();
			String index_key_approved = formObject.getValue(INDEX_KEY_APPROVED).toString();
			String index_tenor_approved = formObject.getValue(INDEX_TENOR_APPROVED).toString();
			String index_tenor_unit_approved = formObject.getValue(INDEX_TENOR_UNIT_APPROVED).toString();

			// changes by shikha 17-07-24
			String utilization_approved = formObject.getValue(UTILIZATION_APPROVED).toString();
			String ftp_over_approved = formObject.getValue(FTP_OVERRIDE_APPROVED).toString();

			log.info("getUpdateApprovedData sColumn" + RAROC_FACILITY_APPROVE_COLUMN);

			String sValues = "'" + curr_approved + "'" + (char) 25 + "'" + facility_amt_approved + "'" + (char) 25 + "'"
					+ tenor_approved + "'" + (char) 25 + "'" + tenorUnit_approved + "'" + (char) 25 + "'"
					+ expDate_approved + "'" + (char) 25 + "'" + cashMargin_approved + "'" + (char) 25 + "'"
					+ outAmt_approved + "'" + (char) 25 + "'" + pricing_approved + "'" + (char) 25 + "'"
					+ index_approved + "'" + (char) 25 + "'" + indexRate_approved + "'" + (char) 25 + "'"
					+ marginComm_approved + "'" + (char) 25 + "'" + value_approved + "'" + (char) 25 + "'"
					+ min_approved + "'" + (char) 25 + "'" + max_approved + "'" + (char) 25 + "'" + expectedRet_approved
					+ "'" + (char) 25 + "'" + marketingRisk_approved + "'" + (char) 25 + "'" + counterPartyRate_approved
					+ "'" + (char) 25 + "'" + counterPartyType_approved + "'" + (char) 25 + "'" + repricingFreq_approved
					+ "'" + (char) 25 + "'" + specialCost_approved + "'" + (char) 25 + "'"

					+ repayFreq_approved + "'" + (char) 25 + "'" + moramPeriod_approved + "'" + (char) 25 + "'"
					+ repayType_approved + "'" + (char) 25 + "'" + creditLimit_approved + "'" + (char) 25 + "'"
					+ eqvAEDAmt_approved + "'" + (char) 25 + "'" + ecl_approved + "'" + (char) 25 + "'"
					+ riskCap_approved + "'" + (char) 25 + "'" + riskReturn_approved + "'" + (char) 25 + "'"
					+ facilityRAROC_approved + "'" + (char) 25 + "'" + avgUtil_approved + "'" + (char) 25 + "'"
					+ remTerm_approved + "'" + (char) 25 + "'" + FTPRate_approved + "'" + (char) 25 + "'"
					+ interestRate_approved + "'" + (char) 25 + "'"

					+ collateralName_approved + "'" + (char) 25 + "'" + collateralType_approved + "'" + (char) 25 + "'"
					+ collateralAmt_approved + "'" + (char) 25 + "'" + collateralCurr_approved + "'" + (char) 25 + "'"
					+ collateralLienAmt_approved + "'" + (char) 25 + "'" + collateralLienInt_approved + "'" + (char) 25
					+ "'" + collateralTenure_approved + "'" + (char) 25 + "'" + borrowerRate_approved + "'" + (char) 25
					+ "'" + comittmentFee_approved + "'" + (char) 25 + "'" + upfrontFee_approved + "'" + (char) 25 + "'"
					+ collateral_number_approved + "'" + (char) 25 + "'" + allocation_percentage_approved + "'"
					+ (char) 25 + "'" + index_key_approved + "'" + (char) 25 + "'" + index_tenor_approved + "'"
					+ (char) 25 + "'" + index_tenor_unit_approved + "'" + (char) 25 + "'" + utilization_approved + "'"
					+ (char) 25 + "'" + ftp_over_approved + "'";

			sOutputUpdateApp = updateDataInDB(RAROC_FACILITY_DETAILS_TABLE, RAROC_FACILITY_APPROVE_COLUMN, sValues,
					sWhere);
			log.info("getUpdateSensitizedData : " + sOutputUpdateApp);
			// Added by Shivanshu Umrao
			if (sOutputUpdateApp == 0) {
				modifyFlag = "N";
			}
			String modifyQuery = "UPDATE " + RAROC_FACILITY_DETAILS_TABLE + " SET MODIFY_FLAG = '" + modifyFlag
					+ "' WHERE " + sWhere;

			log.info("getUpdateFacilityAppData : " + modifyQuery);
			int output = formObject.saveDataInDB(modifyQuery.toString());
			log.info("getUpdateFacilityAppData : " + output);
			// END
		} catch (Exception e) {
			log.error("Exception in checkpreviousStage: ", e);
		}

	}
	// end edit by mohit 02-08-2024 for updating the data from form to db for
	// approved fields

	public void readOnlyApply(IFormReference iFormReference) {
		log.error("Inside readOnlyApply: ");
		iFormReference.setTabStyle("tab1", "0", "disable", "true");
		iFormReference.setTabStyle("tab1", "1", "disable", "true");
		iFormReference.setTabStyle("tab1", "2", "disable", "true");
		iFormReference.setTabStyle("tab1", "3", "disable", "true");
		iFormReference.setTabStyle("tab1", "3", "disable", "true");
		iFormReference.setStyle("LV_FacilityDetails", "disable", "false");
	}

	// added Suraj
	/*
	 * public static void getRimNumber(IFormReference iformrefrence) { String
	 * customerRim = iformrefrence.getValue("CUSTOMER_RIM").toString(); String[]
	 * columnArray = customerRim.split(","); log.info("customerRim length: " +
	 * columnArray.length); if (columnArray.length == 1) {
	 * iformrefrence.setValue("LEAD_REF_NO", customerRim);
	 * iformrefrence.setStyle("Q_NG_RAROC_CUSTOMER_DETAILS_CUSTOMER_T24_ID",
	 * "disable", "true"); } else { for (int i = 0; i < columnArray.length; i++) {
	 * String customerRimVal = columnArray[i]; log.info("customerRimVal : " +
	 * customerRimVal);
	 * iformrefrence.addItemInCombo("Q_NG_RAROC_CUSTOMER_DETAILS_CUSTOMER_T24_ID",
	 * customerRimVal); } } }
	 */// prod_05112024 as this func is not called and is duplicate

	public String indexTenorChange(IFormReference iformrefrence) {
		log.info("inside indexTenorChange@@@@@");
		String rateVal = "";
		String tenorUnit = "";
		String rateCode = iformrefrence.getValue("F_INDEX_KEY_PROPOSED").toString();
		String rateName = iformrefrence.getValue("F_Index_Proposed").toString();
		String tenor = iformrefrence.getValue("F_INDEX_TENOR_PROPOSED").toString();
		String facilityId = iformrefrence.getValue(FACILITY_ID).toString();
		String column = "RATE_VALUE,tenor_unit";
		try {
			if (rateCode.isEmpty() || rateCode == null || rateCode == "") {
				log.info("Inside if block rateCode is empty...");

				String query1 = "SELECT INDEX_KEY_PROPOSED FROM NG_RAROC_FACILITY_DETAILS  where wi_name='"
						+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "' and facility_id='"
						+ facilityId + "'";
				log.info("query1..." + query1);
				List<List<String>> indexKeyProp = formObject.getDataFromDB(query1);
				String setIndexKeyProp = indexKeyProp.get(0).get(0);
				rateCode = setIndexKeyProp;
			}
			if (rateName.isEmpty() || rateName == null || rateName == "") {
				log.info("Inside if block rateName is empty...");
				String query2 = "select INDEX_PROPOSED from NG_RAROC_FACILITY_DETAILS where wi_name='"
						+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "' and facility_id='"
						+ facilityId + "'";
				log.info("query2..." + query2);
				List<List<String>> indexProp = formObject.getDataFromDB(query2);
				String setIndexProp = indexProp.get(0).get(0);
				rateName = setIndexProp;
			}
			if (tenor.isEmpty() || tenor == null || tenor == "") {
				log.info("Inside if block tenor is empty...");
				String query3 = "select INDEX_TENOR_PROPOSED from NG_RAROC_FACILITY_DETAILS where wi_name='"
						+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "' and facility_id='"
						+ facilityId + "'";
				log.info("query3..." + query3);
				List<List<String>> indexTenorProp = formObject.getDataFromDB(query3);
				String setIndexTenorProp = indexTenorProp.get(0).get(0);
				tenor = setIndexTenorProp;
			}
			String query = "SELECT RATE_VALUE, tenor_unit FROM  NG_RAROC_MAST_INDEX_RATE  WHERE RATE_CODE ='" + rateCode
					+ "' AND RATE_NAME='" + rateName + "' AND tenor ='" + tenor + "'";
			String outputXML = getDataFromDBWithColumns(query, column);
			XMLParser xmlParser = new XMLParser(outputXML);
			log.info("xmlParser aaaaaa" + xmlParser);
			int totalRetrieved = Integer.parseInt(xmlParser.getValueOf(Constants.TOTAL_RETRIEVED));
			if (totalRetrieved == 1) {
				rateVal = xmlParser.getValueOf("RATE_VALUE");
				tenorUnit = xmlParser.getValueOf("tenor_unit");
			}
			iformrefrence.setValue("F_Index_Rate_Proposed", rateVal);
			totalValueProp(iformrefrence, rateVal);
			// 22M iformrefrence.setValue("F_INDEX_TENOR_UNIT_PROPOSED", tenorUnit);
		} catch (Exception e) {
			log.error("Unexpected error: ", e);
		}
		return "";
	}

	// Changes by shikha for Sensitized 25-07-24
	public String indexTenorChangeSens(IFormReference iformrefrence) {
		log.info("inside indexTenorChangeSens@@@@");
		String rateValSens = "";
		String tenorUnitSens = "";
		String rateCode = iformrefrence.getValue("F_INDEX_KEY_SENSITIZED").toString();
		String rateName = iformrefrence.getValue("F_Index_Sensitized").toString();
		String tenor = iformrefrence.getValue("F_INDEX_TENOR_SENSITIZED").toString();
		String column = "RATE_VALUE,tenor_unit";
		String facilityId = iformrefrence.getValue(FACILITY_ID).toString();
		try {
			if (rateCode.isEmpty() || rateCode == null || rateCode == "") {

				String query1 = "SELECT INDEX_KEY_SENSITIZED FROM NG_RAROC_FACILITY_DETAILS  where wi_name='"
						+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "' and facility_id='"
						+ facilityId + "'";

				log.info("query1..." + query1);
				List<List<String>> indexKeySens = formObject.getDataFromDB(query1);
				String setIndexKeySens = indexKeySens.get(0).get(0);
				rateCode = setIndexKeySens;
			}
			if (rateName.isEmpty() || rateName == null || rateName == "") {
				String query2 = "SELECT INDEX_SENSITIZED FROM NG_RAROC_FACILITY_DETAILS  where wi_name='"
						+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "' and facility_id='"
						+ facilityId + "'";

				log.info("query2..." + query2);
				List<List<String>> indexSens = formObject.getDataFromDB(query2);
				String setIndexSens = indexSens.get(0).get(0);
				rateName = setIndexSens;
			}
			if (tenor.isEmpty() || tenor == null || tenor == "") {
				String query3 = "SELECT INDEX_TENOR_SENSITIZED FROM NG_RAROC_FACILITY_DETAILS  where wi_name='"
						+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "' and facility_id='"
						+ facilityId + "'";

				log.info("query3..." + query3);
				List<List<String>> indexTenorSens = formObject.getDataFromDB(query3);
				String setIndexTenorSens = indexTenorSens.get(0).get(0);
				tenor = setIndexTenorSens;
			}
			String query = "SELECT RATE_VALUE, tenor_unit FROM  NG_RAROC_MAST_INDEX_RATE  WHERE RATE_CODE ='" + rateCode
					+ "' AND RATE_NAME='" + rateName + "' AND tenor ='" + tenor + "'";
			String outputXML = getDataFromDBWithColumns(query, column);
			XMLParser xmlParser = new XMLParser(outputXML);
			log.info("xmlParser" + xmlParser);
			int totalRetrieved = Integer.parseInt(xmlParser.getValueOf(Constants.TOTAL_RETRIEVED));
			if (totalRetrieved == 1) {
				rateValSens = xmlParser.getValueOf("RATE_VALUE");
				tenorUnitSens = xmlParser.getValueOf("tenor_unit");
			}
			iformrefrence.setValue("F_Index_Rate_Sensitized", rateValSens);
			totalValueSens(iformrefrence, rateValSens);
			// 22M iformrefrence.setValue("F_INDEX_TENOR_UNIT_SENSITIZED", tenorUnitSens);
		} catch (Exception e) {
			log.error("Unexpected error: ", e);
		}
		return "";
	}
	// collateral grid
	/*
	 * //NOT NEEDED TO REMOVE public static void
	 * getCollateralGridData(IFormReference iformrefrence,String CollateralType) {
	 * try { log.info("getCollateralGridData CollateralType...." + CollateralType);
	 * iformrefrence.clearCombo(COLLATRAL_TYPE_PROPOSED);
	 * iformrefrence.clearCombo(COLLATERAL_NAME_PROPOSED);
	 * iformrefrence.clearCombo(COLLATERAL_NUMBER_PROPOSED); String wiName =
	 * iformrefrence.getObjGeneralData().getM_strProcessInstanceId(); String
	 * guarantee_name=""; String collateral_number=""; //String
	 * sflag_guarantee_name=" //"GUARANTEE_TYPE, GUARANTEE_NAME,COLLATERAL_NUMBER";
	 * //String sCollateralCol= "GUARANTEE_NAME,COLLATERAL_NUMBER"; String
	 * sQueryColName
	 * ="SELECT DISTINCT GUARANTEE_NAME FROM  NG_RAROC_GUARANTEE_DETAILS  WHERE GUARANTEE_TYPE ='"
	 * +CollateralType+"' AND WI_NAME='"+wiName+"'"; String outputXMLColName =
	 * getDataFromDBWithColumns(sQueryColName, "GUARANTEE_NAME"); XMLParser
	 * xmlParserColName = new XMLParser(outputXMLColName);
	 * log.info("xmlParserColName" + xmlParserColName); int totalRetrievedColName =
	 * Integer.parseInt(xmlParserColName.getValueOf(Constants.TOTAL_RETRIEVED));
	 * iformrefrence.addItemInCombo(COLLATRAL_TYPE_PROPOSED,CollateralType); if
	 * (totalRetrievedColName != 0) { for (int i = 0; i < totalRetrievedColName;
	 * i++) { String row = xmlParserColName.getNextValueOf(Constants.ROW); XMLParser
	 * rowParser = new XMLParser(row);
	 * log.info("inside totalRetrievedColName>>>>>>"); guarantee_name
	 * =rowParser.getValueOf("GUARANTEE_NAME");
	 * iformrefrence.addItemInCombo(COLLATERAL_NAME_PROPOSED,guarantee_name); String
	 * sQueryColNumber
	 * ="SELECT COLLATERAL_NUMBER FROM  NG_RAROC_GUARANTEE_DETAILS  WHERE GUARANTEE_TYPE ='"
	 * +CollateralType+"' AND WI_NAME='"+wiName+"' AND GUARANTEE_NAME='"
	 * +guarantee_name+"'"; String outputXMLColNumber =
	 * getDataFromDBWithColumns(sQueryColNumber, "COLLATERAL_NUMBER"); XMLParser
	 * xmlParserColNumber = new XMLParser(outputXMLColNumber);
	 * log.info("xmlParserColNumber" + xmlParserColNumber); int
	 * totalRetrievedColNumber =
	 * Integer.parseInt(xmlParserColNumber.getValueOf(Constants.TOTAL_RETRIEVED));
	 * 
	 * if (totalRetrievedColNumber != 0) { for (int j = 0; j <
	 * totalRetrievedColNumber; j++) { String rowColNum =
	 * xmlParserColNumber.getNextValueOf(Constants.ROW); XMLParser rowParsercolNum =
	 * new XMLParser(rowColNum); log.info("inside totalRetrievedColNumber>>>>>>");
	 * collateral_number=rowParsercolNum.getValueOf("COLLATERAL_NUMBER");
	 * iformrefrence.addItemInCombo(COLLATERAL_NUMBER_PROPOSED,collateral_number); }
	 * } } }
	 * 
	 * 
	 * }catch (Exception e) { log.error("Exception in checkpreviousStage: ", e); } }
	 */

	// start edit by mohit 01-07-2024
	public void readOnlyFieldsTreasuryProd(IFormReference iFormReference) {
		log.error("Inside readOnlyFieldsTreasuryProd...: ");
		try {
			String faciltyTreasuryProd = iFormReference.getValue(FACILITY_TREASURY).toString();
			log.info("Inside readOnlyFieldsTreasuryProd faciltyTreasuryProd is...:" + faciltyTreasuryProd);
			if (faciltyTreasuryProd.equalsIgnoreCase("NO") || faciltyTreasuryProd.equalsIgnoreCase("N")) {
				log.info("Inside readOnlyFieldsTreasuryProd...NO:");
				iFormReference.setStyle(EXPECTED_YEAR_RETURN_PROPOSED, "disable", "true");
				iFormReference.setStyle(MARKETING_RISK_CAPITAL_PROPOSED, "disable", "true");
				iFormReference.setStyle(CREDIT_LIMIT_PROPOSED, "disable", "true");
				// start added by mohit 28-07-2024
				iFormReference.setStyle(COUNTERPARTY_RATE_PROPOSED, "disable", "true");
				iFormReference.setStyle(COUNTERPARTY_TYP_PROPOSED, "disable", "true");

				iFormReference.setStyle(EXPECTED_YEAR_RETURN_SENSITIZED, "disable", "true");
				iFormReference.setStyle(MARKETING_RISK_CAPITAL_SENSITIZED, "disable", "true");
				iFormReference.setStyle(CREDIT_LIMIT_SENSITIZED, "disable", "true");
				iFormReference.setStyle(COUNT_INT_SENSITIZED, "disable", "true");
				iFormReference.setStyle(COUNT_TYP_SENSITIZED, "disable", "true");

				// end added by mohit 28-07-2024

			} else if (faciltyTreasuryProd.equalsIgnoreCase("YES") || faciltyTreasuryProd.equalsIgnoreCase("Y")) {
				log.info("Inside readOnlyFieldsTreasuryProd...YES:");
				iFormReference.setStyle(EXPECTED_YEAR_RETURN_PROPOSED, "disable", "false");
				iFormReference.setStyle(MARKETING_RISK_CAPITAL_PROPOSED, "disable", "false");
				iFormReference.setStyle(CREDIT_LIMIT_PROPOSED, "disable", "false");
				// start added by mohit 28-07-2024
				iFormReference.setStyle(COUNTERPARTY_RATE_PROPOSED, "disable", "false");
				iFormReference.setStyle(COUNTERPARTY_TYP_PROPOSED, "disable", "false");

				iFormReference.setStyle(EXPECTED_YEAR_RETURN_SENSITIZED, "disable", "false");
				iFormReference.setStyle(MARKETING_RISK_CAPITAL_SENSITIZED, "disable", "false");
				iFormReference.setStyle(CREDIT_LIMIT_SENSITIZED, "disable", "false");
				iFormReference.setStyle(COUNT_INT_SENSITIZED, "disable", "false");
				iFormReference.setStyle(COUNT_TYP_SENSITIZED, "disable", "false");
				// start added by mohit 28-07-2024
			}
		} catch (Exception e) {
			log.error("Exception in readOnlyFieldsTreasuryProd: ", e);
		}
	}
	// end edit by mohit 01-07-2024

	// start edit by mohit 02-07-2024
	public static String getDateFormat(String sDate) {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
		// Date now = new Date();
		String dateStamp = sdfDate.format(sDate);
		return dateStamp;
	}
	// end edit by mohit 02-07-2024

	public static String decimalFloatValue(String sValue) {
		log.info("Inside decimalFloatValue...");
		String sValueRet = "";
		try {
			if (sValue.isEmpty()) {
				log.info("Inside isempty..sValue" + sValue + "---");
				Float fsValue_value = Float.parseFloat("0.000");
				sValueRet = String.format("%.3f", fsValue_value / 100);
			} else if (sValue != null || sValue != "" || !sValue.isEmpty()) {
				log.info("else if sValue...." + sValue);
				Float fsValue_value = Float.parseFloat(sValue);
				sValueRet = String.format("%.3f", fsValue_value / 100);
			}
			log.info("sValueRet...." + sValueRet);
			return sValueRet;
		} catch (Exception e) {
			log.error("Exception in decimalFloatValue: ", e);
			return "0.000";
		}
	}

	public void totalValueProp(IFormReference ifr, String sRateValue) {
		log.info("Inside totalValueProp...");
		try {
			String facilityId = ifr.getValue(FACILITY_ID).toString();
			String setIndexRateAndCommissionPropQuery = "select MARGIN_COMMISION_PROPOSED from NG_RAROC_FACILITY_DETAILS where wi_name='"
					+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "' and facility_id='" + facilityId
					+ "'";
			log.info("setIndexRateAndCommissionPropQuery..." + setIndexRateAndCommissionPropQuery);

			List<List<String>> setIndexRateAndCommisPropOut = formObject
					.getDataFromDB(setIndexRateAndCommissionPropQuery);
			// String setIndexRate = setIndexRateAndCommisPropOut.get(0).get(0);
			String setIndexRate = sRateValue;
			String setMarginCommission = setIndexRateAndCommisPropOut.get(0).get(0);
			log.info("setIndexRate..." + setIndexRate);
			log.info("setMarginCommission..." + setMarginCommission);

//	   double setIndexRateDecimal = Double.parseDouble(setIndexRate);
//
//       double setMarginCommissionValue = Double.parseDouble(setMarginCommission);
//
//        double valueProp = setIndexRateDecimal + setMarginCommissionValue;	    
//	    DecimalFormat df = new DecimalFormat("0.00");
//	    String totalValueProp = df.format(valueProp);
			if ((sRateValue != null || !sRateValue.isEmpty() || sRateValue != "")
					&& (setMarginCommission != null || !setMarginCommission.isEmpty() || setMarginCommission != "")) {
				BigDecimal setIndexRateDecimal = new BigDecimal(setIndexRate);
				log.info("setIndexRateDecimal::: " + setIndexRateDecimal);
				double setMarginCommissionValue = Double.parseDouble(setMarginCommission);
				log.info("setMarginCommissionValue::: " + setMarginCommissionValue);

				BigDecimal setMarginCommissionDecimal = BigDecimal.valueOf(setMarginCommissionValue);

				BigDecimal valueProp = setIndexRateDecimal.add(setMarginCommissionDecimal);

				BigDecimal roundedValueProp = valueProp.setScale(2, RoundingMode.HALF_UP);

				String totalValueProp = roundedValueProp.toString();
				log.info("valueProp::: " + valueProp);

//    String totalValueProp = Double.toString(valueProp);
				ifr.setValue("F_Value_Proposed", totalValueProp);
			}
		} catch (Exception e) {
			log.error("Exception in totalValueProp: ", e);
		}
	}

	// for Sensitized change start by reshank on 26-08-24
	public void totalValueSens(IFormReference ifr, String rateValSens) {
		log.info("Inside totalValueSens....");
		try {
			String facilityId = ifr.getValue(FACILITY_ID).toString();
			String setIndexRateAndCommissionSensQuery = "select MARGIN_COMMISION_SENSITIZED from NG_RAROC_FACILITY_DETAILS where wi_name='"
					+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "' and facility_id='" + facilityId
					+ "'";
			log.info("setIndexRateAndCommissionSensQuery..." + setIndexRateAndCommissionSensQuery);

			List<List<String>> setIndexRateAndCommisSensOut = formObject
					.getDataFromDB(setIndexRateAndCommissionSensQuery);
			String setIndexRate = rateValSens;
			String setMarginCommission = setIndexRateAndCommisSensOut.get(0).get(0);
			log.info("setIndexRate..." + setIndexRate);
			log.info("setMarginCommission..." + setMarginCommission);
			if ((rateValSens != null || !rateValSens.isEmpty() || rateValSens != "")
					&& (setMarginCommission != null || !setMarginCommission.isEmpty() || setMarginCommission != "")) {
				BigDecimal setIndexRateDecimal = new BigDecimal(setIndexRate);
				log.info("setIndexRateDecimal::: " + setIndexRateDecimal);
				double setMarginCommissionValue = Double.parseDouble(setMarginCommission);
				log.info("setMarginCommissionValue::: " + setMarginCommissionValue);

				BigDecimal setMarginCommissionDecimal = BigDecimal.valueOf(setMarginCommissionValue);

				BigDecimal valueSens = setIndexRateDecimal.add(setMarginCommissionDecimal);

				BigDecimal roundedValueSens = valueSens.setScale(2, RoundingMode.HALF_UP);

				String totalValueSens = roundedValueSens.toString();
				log.info("valueProp::: " + valueSens);

				ifr.setValue("F_Value_Sensitized", totalValueSens);
			}
		} catch (Exception e) {
			log.error("Exception in totalValueSens: " + e.getMessage());
		}
	}

	// added by mohit 11-09-2024
	public void getUpdateData(String tableName, String columnName) {
		int output = -1;
		String query = "";
		try {
			query = "UPDATE " + tableName + " SET " + columnName + "  WHERE WI_NAME='"
					+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "'";

			log.info("Inside getUpdateData with 2 arguments " + query);
			output = formObject.saveDataInDB(query);
			log.info("Inside getUpdateData with 2 arguments Output: " + output);

		} catch (Exception e) {
			log.error("Exception : " + e);
		}

	}

}
