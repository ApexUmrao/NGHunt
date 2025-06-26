package com.newgen.iforms.user.ao;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringJoiner;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.user.ao.model.casafulfillment.AccountCBRCodes;
import com.newgen.iforms.user.ao.model.casafulfillment.CASAAccountDetails;
import com.newgen.iforms.user.ao.model.casafulfillment.CBRCode;
import com.newgen.iforms.user.ao.model.casafulfillment.CBRequest;
import com.newgen.iforms.user.ao.model.casafulfillment.CasaAccount;
import com.newgen.iforms.user.ao.model.casafulfillment.ChequeDetails;
import com.newgen.iforms.user.ao.model.casafulfillment.CustomerRelation;
import com.newgen.iforms.user.ao.model.casafulfillment.DebitCardDetails;
import com.newgen.iforms.user.ao.model.casafulfillment.PrimaryRel;
import com.newgen.iforms.user.ao.model.casafulfillment.PrimaryRelation;
import com.newgen.iforms.user.ao.model.casafulfillment.SRChequeBook;
import com.newgen.iforms.user.ao.model.customerfulfillment.BankingDetail;
import com.newgen.iforms.user.ao.model.customerfulfillment.CompanyAddress;
import com.newgen.iforms.user.ao.model.customerfulfillment.ContactDetail;
import com.newgen.iforms.user.ao.model.customerfulfillment.CorrespondenceAddress;
import com.newgen.iforms.user.ao.model.customerfulfillment.CustomerDocument;
import com.newgen.iforms.user.ao.model.customerfulfillment.EmploymentDetail;
import com.newgen.iforms.user.ao.model.customerfulfillment.LabourCard;
import com.newgen.iforms.user.ao.model.customerfulfillment.OfficeAddress;
import com.newgen.iforms.user.ao.model.customerfulfillment.Passport;
import com.newgen.iforms.user.ao.model.customerfulfillment.PermanentAddress;
import com.newgen.iforms.user.ao.model.customerfulfillment.PersonalDetails;
import com.newgen.iforms.user.ao.model.customerfulfillment.ResidentialAddress;
import com.newgen.iforms.user.ao.model.customerfulfillment.Signature;
import com.newgen.iforms.user.ao.model.customerfulfillment.UaeNational;
import com.newgen.iforms.user.ao.model.customerfulfillment.Visa;
import com.newgen.iforms.user.ao.util.ConnectSocket;
import com.newgen.iforms.user.ao.util.LoadConfiguration;
import com.newgen.iforms.user.config.AOLogger;
import com.newgen.iforms.user.config.Constants;

public class Fulfillment implements Constants {
	
	public static final Logger log = AOLogger.getInstance().getLogger();
	protected IFormReference formObject;
	String sActivityName = "";
	String sWorkitemName  = "";
	String sSessionId="";
	String sEngineName="";
	String sUserName ="";
	String sJTSIP = "";
	String sJTSPORT = "";
//	private String refNo = "";
//	private String topicName = "";
	ConnectSocket socket;
	private static String hexServerIP = null;
	private static final java.security.SecureRandom seeder = new java.security.SecureRandom();
	HashMap<String, String> fulfillmentDefaultMap = new HashMap<>();
//	private HashMap<String, String> fcrDefault = new HashMap<>();
	
	
	public Fulfillment(IFormReference formObject) {
		this.formObject = formObject;
		sActivityName = formObject.getActivityName();
		sWorkitemName = formObject.getObjGeneralData().getM_strProcessInstanceId();
		sSessionId = formObject.getObjGeneralData().getM_strDMSSessionId();
		fulfillmentDefaultMap= LoadConfiguration.getInstance(formObject).getFulfillmentValue();
//		fcrDefault= LoadConfiguration.getInstance(formObject).getFcrValue();
		sEngineName = formObject.getObjGeneralData().getM_strEngineName();
		socket = ConnectSocket.getReference("",0);
		sUserName = formObject.getUserName();
		sJTSIP = formObject.getObjGeneralData().getM_strJTSIP();
		sJTSPORT = formObject.getObjGeneralData().getM_strJTSPORT();
	}
	
	public void pushMessageFulfillment() throws Exception {
		String sQuery= "SELECT INPUT_XML, SLNO FROM USR_0_INTEGRATION_CALLS WHERE WI_NAME = '"+sWorkitemName+"' AND ORCHESTRATION_TYPE = '"+FULFILLMENT+"' AND STATUS != 'Success' ORDER BY TO_NUMBER(CALL_ORDER) FETCH FIRST ROW ONLY";
		logInfo("pushMessageCIDFulfillment:  ", sQuery);
		List<List<String>> sOutput = formObject.getDataFromDB(sQuery);
    	if (sOutput.size() == 1) {
//    		for(int i = 0; i < sOutput.size(); i++) {
    			String inputXml = sOutput.get(0).get(0);
    			String slNo =  sOutput.get(0).get(1);
    					
    			inputXml = replaceDynamicParams(inputXml, slNo);
    			
    			String finalOutputXML = socket.connectToSocket(inputXml);
//    			String finalOutputXML = IFormCallBroker.execute(inputXml, sJTSIP, Integer.parseInt(sJTSPORT));
    			logInfo("pushMessageFulfillment: finalOutputXML:  ", finalOutputXML);
//    		}
    	}
	}
	
	public String replaceDynamicParams(String inputXml, String slNo) {
		logInfo("replaceDynamicParams:  ", inputXml);
		
		String customerIdDynamicParam = "#CUST_"+ slNo +"#";
		
		if (inputXml.indexOf(customerIdDynamicParam) > 0) {
			String query = "SELECT CID_ACCNO FROM USR_0_INTEGRATION_CALL_VALUES WHERE WI_NAME='"+ sWorkitemName +"' AND CALLTYPE LIKE '%CID_FULFILLMENT_"+slNo+"%' AND SNO='"+slNo+"'";
			List<List<String>> sOutput = formObject.getDataFromDB(query);
			if (sOutput.size() > 0) {
    			String cid = sOutput.get(0).get(0);
    			inputXml = inputXml.replaceAll(customerIdDynamicParam, cid);
	    	}
		}
		
		return inputXml;
	}
	
	public void refreshStatus() throws Exception {
		JSONArray jsonArray = formObject.getDataFromGrid(GRID_FULFILLMENT);
		logInfo("refreshStatus:  ", jsonArray.toJSONString());
		for (int row=0; row<jsonArray.size(); row++) {
			JSONObject rowJSON = (JSONObject) jsonArray.get(row);
			logInfo("refreshStatus:  ", rowJSON.toJSONString());
			String status = rowJSON.get("Status").toString();
			if (!"Success".equals(status)) {
				String callName = rowJSON.get("Call Name").toString();
				String sQuery= "SELECT STATUS, RESPONSE, ERRORDESC FROM USR_0_INTEGRATION_CALLS WHERE WI_NAME = '"+sWorkitemName+"' AND ORCHESTRATION_TYPE = '"+FULFILLMENT+"' AND CALL_NAME='"+ callName +"'";
				logInfo("refreshStatus:  ", sQuery);
				List<List<String>> sOutput = formObject.getDataFromDB(sQuery);
				logInfo("refreshStatus:  ", sOutput.toString());
		    	if (sOutput.size() > 0) {
	    			String integrationStatus = sOutput.get(0).get(0);
	    			String response = sOutput.get(0).get(1);
	    			String errorDesc = sOutput.get(0).get(2);
	    			formObject.setTableCellValue(GRID_FULFILLMENT, row, 1, integrationStatus);
	    			formObject.setTableCellValue(GRID_FULFILLMENT, row, 2, response);
	    			formObject.setTableCellValue(GRID_FULFILLMENT, row, 4, errorDesc);
		    	}
			}
		}
	}
	
	private String createRequestCIDFulfillmentJson(int sNo) {
		String message = "";
		try {
//			String outputXML;
			com.newgen.iforms.user.ao.model.customerfulfillment.Root customerFulfillmentInitiated = new com.newgen.iforms.user.ao.model.customerfulfillment.Root();
			com.newgen.iforms.user.ao.model.customerfulfillment.Payload payload = new com.newgen.iforms.user.ao.model.customerfulfillment.Payload();
			com.newgen.iforms.user.ao.model.customerfulfillment.Header header = new com.newgen.iforms.user.ao.model.customerfulfillment.Header();

			EmploymentDetail employmentDetail  = new EmploymentDetail();
			ContactDetail contactDetail = new ContactDetail();
			UaeNational uaeNational = new UaeNational();
			BankingDetail bankingDetail = new BankingDetail();
			Signature signature = new Signature();
			CustomerDocument customerDocument = new CustomerDocument();
			PersonalDetails personalDetails = new PersonalDetails();
			CompanyAddress companyAddress = new CompanyAddress();
			ResidentialAddress residentialAddress = new ResidentialAddress();
			PermanentAddress permanentAddress = new PermanentAddress();
			OfficeAddress officeAddress = new OfficeAddress();
			CorrespondenceAddress correspondenceAddress = new CorrespondenceAddress();
			long start_Time1 = System.currentTimeMillis();	
			//String sNo ="0";
			String sQuery = "SELECT FINAL_FULL_NAME,FINAL_PREFIX,FINAL_GENDER,CUST_MARITAL_STATUS,FINAL_CITY,FINAL_STATE,FINAL_NATIONALITY,"
					+ "FINAL_EIDA_NO,FINAL_EMP_NAME, EMP_POSITION, EMP_STATE, EMP_PHONE, EMP_SAL, FINAL_ADDRESS,CORR_PO_BOX, CORR_FLOOR,"
					+ "CORR_STREET, FINAL_DOB,FINAL_EMAIL,CORR_PHONE,FINAL_MOBILE_NO,CUST_SEG,STAFF_FLAG,SIGN_STYLE, FINAL_PROFESSION,"
					+ "COUNTRY_RESIDENCE,RM_CODE,FINAL_PASS_NO,IS_UAE_RESIDENT,RES_BUILDING,DECODE(RES_CITY,'OTHERS',"
					+ "OTHER_RESI_CITY,RES_CITY) AS RES_CITY,(select country_code from usr_0_country_mast where country=RES_CNTRY)RES_CNTRY,"
					+ "DECODE(RES_STATE,'OTHERS',RES_OTHER,RES_STATE) AS RES_STATE, RES_VILLA, RES_LANDMARK,(select country_code from usr_0_country_mast"
					+ " where country=FINAL_COUNTRY)FINAL_COUNTRY, FINAL_PHONE_NO, CORR_MOB,CORR_EMAIL,"
					+ "DECODE(CORR_STATE,'OTHERS',CORR_OTHER,CORR_STATE) AS CORR_STATE,DECODE(CORR_CITY,'OTHERS',"
					+ "OTHER_CORR_CITY,CORR_CITY) AS CORR_CITY,RELIGION,(select country_code from usr_0_country_mast "
					+ "where country=FINAL_RESIDENCE_COUNTRY)FINAL_RESIDENCE_COUNTRY,CUST_SHORT_NAME,EMP_ID,"
					+ "PRI_SRC_OF_INCOME,DUAL_NATIONALITY,EMP_ANNUAL_INCOME,OTHER_PERK_ALLOWANCES_AED,COMP_WEBSITE,EMP_NAME,PRIMARYSRCCTRY,EMP_STATUS,EIDA_EXPIRY " //Added by Shivanshu ATP-377
					+ "FROM USR_0_CUST_TXN WHERE"
					+ " WI_NAME ='" + sWorkitemName + "' AND CUST_SNO='" + sNo + "'";
			logInfo("createRequestCIDFulfillmentJson sQuery ", sQuery);
			List<List<String>> sOutput = formObject.getDataFromDB(sQuery);
			logInfo("createRequestCIDFulfillmentJson sOutput ", sOutput.toString());
			long end_Time = System.currentTimeMillis();
			long diff = start_Time1 - end_Time;
			logInfo("createRequestCIDFulfillmentJson", "Time Difference: " + diff+ " for Query: " + sQuery);
			List<List<String>> sTableOutput = null;
			String sReligion = "";
			String sMaritalStatus = "";
			String sNationality = "";
			String sSignCode = "";
			String sCountry = "";
			String sProfession = "";
			String sAccBranch = "";
			String sAccClass = "";
			String sMinorFlag = "";
			String sAddress1 = "";
			String sCity = "";
			String sState = "";
			String sEmail = "";
			String sPhone = "";
			String sMobile = "";
			String code = "";
			String sCustPosition = sOutput.get(0).get(9);
			String sCustEmpPhoneNo = sOutput.get(0).get(11);
			String country = (sOutput != null && sOutput.size() > 0) ? sOutput.get(0).get(42).toString() : "";
			String sDOB = (sOutput != null && sOutput.size() > 0) ? sOutput.get(0).get(17).toString() : "";
			sDOB =setDateValue1(sDOB);
			String sSalary = (sOutput != null && sOutput.size() > 0) ? sOutput.get(0).get(12).toString() : "";
			String sCenterCode = "";
			String sResident = (sOutput != null && sOutput.size() > 0) ? sOutput.get(0).get(28).toString() : "";
			String sGender = (sOutput != null && sOutput.size() > 0) ? sOutput.get(0).get(2).toString() : "";
			if (!sGender.isEmpty()) {
				sGender = sGender.substring(0, 1);
			}
			String sCustSeg = (sOutput != null && sOutput.size() > 0) ? sOutput.get(0).get(21).toString() : "";
			String sStaffFlag = (sOutput != null && sOutput.size() > 0) ? sOutput.get(0).get(22).toString() : "";
			String sEmpName = (sOutput != null && sOutput.size() > 0) ? sOutput.get(0).get(8).toString() : "";
			logInfo("createRequestCIDFulfillmentJson", "sOutput---" + sOutput);
			logInfo("createRequestCIDFulfillmentJson", "sDOB---" + sDOB);
			if (sResident.equalsIgnoreCase("Yes")) {
				sResident = "Resident";
				sCountry = "AE";
			} else {
				start_Time1 = System.currentTimeMillis();
				sResident = "Non-Resident";
				sQuery = "SELECT COUNTRY_CODE FROM USR_0_COUNTRY_MAST WHERE COUNTRY='"+ country + "'";
				sTableOutput = formObject.getDataFromDB(sQuery);
				sCountry = (sTableOutput != null && sTableOutput.size() > 0) ? sTableOutput.get(0).get(0).toString(): "";
				end_Time = System.currentTimeMillis();
				diff = start_Time1 - end_Time;
				logInfo("createRequestCIDFulfillmentJson", "Time Difference: " + diff+ " for Query: " + sQuery);
			}
			if (!sStaffFlag.equalsIgnoreCase("")) {
				sStaffFlag = sStaffFlag.substring(0, 1);
			}
			// sCustSeg = "Aspire";
			start_Time1 = System.currentTimeMillis();
			sQuery = "SELECT UNIQUE_ID,PROFIT_CENTER_CODE FROM USR_0_CUST_SEGMENT WHERE CUST_SEGMENT ='"
					+ sCustSeg + "'";
			sTableOutput = formObject.getDataFromDB(sQuery);
			sCenterCode = (sTableOutput != null && sTableOutput.size() > 0) ? sTableOutput.get(0).get(1).toString(): "";
			sCustSeg = (sTableOutput != null && sTableOutput.size() > 0) ? sTableOutput.get(0).get(0).toString(): "";
			end_Time = System.currentTimeMillis();
			diff = start_Time1 - end_Time;
			logInfo("createRequestCIDFulfillmentJson", "Time Difference: " + diff+ " for Query: " + sQuery);
			start_Time1 = System.currentTimeMillis();
			sQuery = "SELECT VALUE,(SELECT VALUE FROM USR_0_DEFAULTVALUE_FCR WHERE NAME ='"+ sResident
					+ "')RESIDENT FROM USR_0_DEFAULTVALUE_FCR WHERE NAME ='MISCODE4'";
			sTableOutput = formObject.getDataFromDB(sQuery);
			end_Time = System.currentTimeMillis();
			diff = start_Time1 - end_Time;
			logInfo("createRequestCIDFulfillmentJson", "Time Difference: " + diff+ " for Query: " + sQuery);
			sResident = (sTableOutput != null && sTableOutput.size() > 0) ? sTableOutput.get(0).get(1).toString(): "";
			String sMis4 = (sTableOutput != null && sTableOutput.size() > 0) ? sTableOutput.get(0).get(0).toString(): "";
			if (sSalary.equalsIgnoreCase("")) {
				start_Time1 = System.currentTimeMillis();
				sQuery = "SELECT VALUE FROM USR_0_DEFAULTVALUE_FCR WHERE NAME ='Salary_QDE'";
				sTableOutput = formObject.getDataFromDB(sQuery);
				sSalary = (sTableOutput != null && sTableOutput.size() > 0) ? sTableOutput.get(0).get(0).toString(): "";
				end_Time = System.currentTimeMillis();
				diff = start_Time1 - end_Time;
				log.info("Time Difference: " + diff + " for Query: " + sQuery);
			}
			if (formObject.getValue(DATA_ENTRY_MODE).toString().equalsIgnoreCase("Quick Data Entry")) {
				start_Time1 = System.currentTimeMillis();
				sQuery = "SELECT VALUE FROM USR_0_DEFAULTVALUE_FCR WHERE NAME ='Religion_QDE'";
				sTableOutput = formObject.getDataFromDB(sQuery);
				sReligion = (sTableOutput != null && sTableOutput.size() > 0) ? sTableOutput.get(0).get(0).toString(): "";
				end_Time = System.currentTimeMillis();
				diff = start_Time1 - end_Time;
				start_Time1 = System.currentTimeMillis();
				logInfo("createRequestCIDFulfillmentJson", "Time Difference: " + diff+ " for Query: " + sQuery);
				sQuery = "SELECT VALUE FROM USR_0_DEFAULTVALUE_FCR WHERE NAME ='Marital_QDE'";
				sTableOutput = formObject.getDataFromDB(sQuery);
				sMaritalStatus = (sTableOutput != null && sTableOutput.size() > 0) ? sTableOutput.get(0).get(0).toString(): "";
				end_Time = System.currentTimeMillis();
				diff = start_Time1 - end_Time;
				logInfo("createRequestCIDFulfillmentJson", "Time Difference: " + diff+ " for Query: " + sQuery);
				start_Time1 = System.currentTimeMillis();
				sQuery = "SELECT VALUE FROM USR_0_DEFAULTVALUE_FCR WHERE NAME ='Staff_Flag_QDE'";
				sTableOutput = formObject.getDataFromDB(sQuery);
				sStaffFlag = (sTableOutput != null && sTableOutput.size() > 0) ? sTableOutput.get(0).get(0).toString(): "";
				end_Time = System.currentTimeMillis();
				diff = start_Time1 - end_Time;
				logInfo("createRequestCIDFulfillmentJson", "Time Difference: " + diff+ " for Query: " + sQuery);
				if (sOutput != null && sOutput.size() > 0) {
					sAddress1 = sOutput.get(0).get(13);
					sCity = sOutput.get(0).get(4);
					sState = sOutput.get(0).get(5);
					sEmail = sOutput.get(0).get(18);
					sPhone = sOutput.get(0).get(36);
					sMobile = sOutput.get(0).get(20);
				}
			} else {
				start_Time1 = System.currentTimeMillis();
				sQuery = "SELECT UNIQUE_CODE FROM USR_0_RELIGION WHERE RELIGION='"+ sOutput.get(0).get(41) + "'";
				sTableOutput = formObject.getDataFromDB(sQuery);
				sReligion = (sTableOutput != null && sTableOutput.size() > 0) ? sTableOutput.get(0).get(0).toString(): "";
				end_Time = System.currentTimeMillis();
				diff = start_Time1 - end_Time;
				logInfo("createRequestCIDFulfillmentJson", "Time Difference: " + diff+ " for Query: " + sQuery);
				start_Time1 = System.currentTimeMillis();
				sQuery = "SELECT UNIQUE_CODE FROM USR_0_MARITAL_STATUS WHERE MARITAL_STATUS='"+ sOutput.get(0).get(3) + "'";
				sTableOutput = formObject.getDataFromDB(sQuery);
				sMaritalStatus = (sTableOutput != null && sTableOutput.size() > 0) ? sTableOutput.get(0).get(0).toString(): "";
				end_Time = System.currentTimeMillis();
				diff = start_Time1 - end_Time;
				logInfo("createRequestCIDFulfillmentJson", "Time Difference: " + diff
						+ " for Query: " + sQuery);
				if (sOutput != null && sOutput.size() > 0) {
					sAddress1 = sOutput.get(0).get(14);
					sCity = sOutput.get(0).get(40);
					sState = sOutput.get(0).get(39);
					sEmail = sOutput.get(0).get(38);
					sPhone = sOutput.get(0).get(19);
					sMobile = sOutput.get(0).get(19);
				}
			}
			start_Time1 = System.currentTimeMillis();
			sQuery = "SELECT ACC_HOME_BRANCH,ACC_CLASS FROM " + sExtTable+ " WHERE WI_NAME ='" + sWorkitemName + "'";
			sTableOutput = formObject.getDataFromDB(sQuery);
			end_Time = System.currentTimeMillis();
			diff = start_Time1 - end_Time;
			logInfo("createRequestCIDFulfillmentJson", "Time Difference: " + diff+ " for Query: " + sQuery);
			String accClass = (sTableOutput != null && sTableOutput.size() > 0) ? sTableOutput.get(0).get(1).toString(): "";
			if (accClass.equalsIgnoreCase("Both")) {
				sAccClass = "C";
			} else {
				sAccClass = sTableOutput.get(0).get(1).substring(0, 1);
			}
			start_Time1 = System.currentTimeMillis();
			code = (sTableOutput != null && sTableOutput.size() > 0) ? sTableOutput.get(0).get(0).toString() : "";
			sQuery = "SELECT CODE FROM USR_0_HOME_BRANCH WHERE HOME_BRANCH='"+ code + "'";
			sTableOutput = formObject.getDataFromDB(sQuery);
			sAccBranch = (sTableOutput != null && sTableOutput.size() > 0) ? sTableOutput.get(0).get(0).toString(): "";
			end_Time = System.currentTimeMillis();
			diff = start_Time1 - end_Time;
			logInfo("createRequestCIDFulfillmentJson", "Time Difference: " + diff+ " for Query: " + sQuery);
			start_Time1 = System.currentTimeMillis();
			sQuery = "SELECT COUNTRY_CODE FROM USR_0_COUNTRY_MAST WHERE COUNTRY='"+ sOutput.get(0).get(6) + "'";
			sTableOutput = formObject.getDataFromDB(sQuery);
			sNationality = (sTableOutput != null && sTableOutput.size() > 0) ? sTableOutput.get(0).get(0).toString(): "";
			logInfo("createRequestCIDFulfillmentJson", "bikash: " + sQuery);
			logInfo("createRequestCIDFulfillmentJson", "bikash: " + sNationality);
			end_Time = System.currentTimeMillis();
			diff = start_Time1 - end_Time;
			logInfo("createRequestCIDFulfillmentJson", "Time Difference: " + diff+ " for Query: " + sQuery);
			start_Time1 = System.currentTimeMillis();
			sQuery = "SELECT SIGN_CODE FROM USR_0_SIGN_STYLE WHERE SIGN_DESC='"+ sOutput.get(0).get(23) + "'";
			sTableOutput = formObject.getDataFromDB(sQuery);
			sSignCode = (sTableOutput != null && sTableOutput.size() > 0) ? sTableOutput.get(0).get(0).toString(): "";
			end_Time = System.currentTimeMillis();
			diff = start_Time1 - end_Time;
			logInfo("createRequestCIDFulfillmentJson", "Time Difference: " + diff+ " for Query: " + sQuery);
			start_Time1 = System.currentTimeMillis();
			code = (sTableOutput != null && sTableOutput.size() > 0) ? sTableOutput.get(0).get(0).toString() : "";
			sQuery = "SELECT PROFESSION_CODE FROM USR_0_PROFESSION WHERE PROFESSION_DESC='"+ code + "'";
			sTableOutput = formObject.getDataFromDB(sQuery);
			sProfession = (sTableOutput != null && sTableOutput.size() > 0) ? sTableOutput.get(0).get(0).toString(): "";
			sQuery = "SELECT CUST_CATEGORY_TYPE FROM usr_0_cust_cat WHERE CUST_CATEGORY_CODE='"+ sCustSeg + "'";
			sTableOutput = formObject.getDataFromDB(sQuery);
			String sCustClass = (sTableOutput != null && sTableOutput.size() > 0) ? sTableOutput.get(0).get(0).toString(): "";
			end_Time = System.currentTimeMillis();
			diff = start_Time1 - end_Time;
			logInfo("createRequestCIDFulfillmentJson", "Time Difference: " + diff+ " for Query: " + sQuery);
			if (sProfession.equalsIgnoreCase("")) {
				sProfession = "99";
			}
			int iAge = CalculateAge1(sDOB);
			String sQueryy = "select VALUE from usr_0_defaultvalue_fcr where name='Minor_Age'";
			List<List<String>> sOutputt = formObject.getDataFromDB(sQueryy);
			logInfo("createRequestCIDFulfillmentJson", "sOutputt------" + sOutputt);
			int sMinorAge = (sOutputt != null && sOutputt.size() > 0) ? Integer.parseInt(sOutputt.get(0).get(0).toString()) : 0;
			logInfo("createRequestCIDFulfillmentJson", "sMinorAge....." + sMinorAge);
			String natureOfBussiness= sOutput.get(0).get(45);
			String secondNationality = sOutput.get(0).get(46);
			String annualIncome = sOutput.get(0).get(47);
			String sourceOfIncome = sOutput.get(0).get(48);
			String website = sOutput.get(0).get(49);
			String companyName = sOutput.get(0).get(50);
			String countryOfIncome= sOutput.get(0).get(51);
			String employmentType = sOutput.get(0).get(52);
			String eidaExpiryDate = sOutput.get(0).get(53);
			String dualNationalityFlag = "";
			String selfCompanyName = "";
			String secondNationalityCode = "";
			String natureOfBussinessCode = getIndustryRisk(natureOfBussiness);
			String bussinessQuery = "SELECT INDUSTRY_TYPE FROM USR_0_AO_INDUSTRY_RISK WHERE INDUSTRY_CD = '"+natureOfBussinessCode+"'";
			List<List<String>> output = formObject.getDataFromDB(bussinessQuery);								
			if (output != null && output.size() > 0) {
				natureOfBussiness=output.get(0).get(0);
			}

			String queryCountry = "SELECT COUNTRY_CODE FROM USR_0_COUNTRY_MAST WHERE COUNTRY='"+ secondNationality + "'";
			List<List<String>> outputCountry = formObject.getDataFromDB(queryCountry);								
			if (outputCountry != null && outputCountry.size() > 0) {
				secondNationalityCode=outputCountry.get(0).get(0);
			}
			if (secondNationality != null && !secondNationality.isEmpty()) {
				dualNationalityFlag = "Y";
			}else {
				dualNationalityFlag = "N";
			}

			String employmentTypeCode = "SELECT EMP_CODE FROM USR_0_EMPLOYMENT_STATUS WHERE EMP_STATUS ='"+employmentType+"'";
			List<List<String>> outputCode = formObject.getDataFromDB(employmentTypeCode);								
			if (outputCode != null && outputCode.size() > 0) {
				employmentType=outputCode.get(0).get(0);
			}

			if ("S".equalsIgnoreCase(employmentType)) {
				selfCompanyName = companyName;
			}

			logInfo("Customer_add", " annualIncome : " + annualIncome + " , sourceOfIncome: " + sourceOfIncome + " , website: "
					+ website + " ,companyName : "+ companyName + " ,natureOfBussiness : "+ natureOfBussiness
					+ " ,employmentType : "+ employmentType + " ,eidaExpiryDate " +eidaExpiryDate + " ,countryOfIncome :" 
					+ countryOfIncome + " ,secondNationality : "+ secondNationality + " ,secondNationalityCode : "+ secondNationalityCode);

			if (iAge < sMinorAge) {
				sMinorFlag = "Y";
			} else {
				sMinorFlag = "N";
			}
			if (sEmpName.length() > 40) {
				sEmpName = sEmpName.substring(0, 40);
			}
			if (sMinorFlag.equalsIgnoreCase("Y")) {
				logInfo("createRequestCIDFulfillmentJson","createRequestCIDFulfillmentJson ---if minor Input XML.....27122016 111");
				String sCustGuardian = "";
				int sSNOGuardian;
				try {
					String sQueryGuardianCidSNO = "Select CID,SNO from acc_relation_repeater where wi_name='"+ sWorkitemName + "' and acc_relation='Guardian'";
					List<List<String>> sOutputGuardianCidSNO = formObject.getDataFromDB(sQueryGuardianCidSNO);
					logInfo("createRequestCIDFulfillmentJson", "sQueryGuardianCid :"+ sQueryGuardianCidSNO);
					int sCustGuardianCIdTag = sOutputGuardianCidSNO.size();
					logInfo("createRequestCIDFulfillmentJson", "sCustGuardianCIdTag..."+ sCustGuardianCIdTag);
					sCustGuardian = (sOutputGuardianCidSNO != null && sOutputGuardianCidSNO.size() > 0) ? sOutputGuardianCidSNO.get(0).get(0).toString() : "";
					sSNOGuardian = (sOutputGuardianCidSNO != null && sOutputGuardianCidSNO.size() > 0) ? Integer.parseInt(sOutputGuardianCidSNO.get(0).get(1).toString()) : 0;
					logInfo("createRequestCIDFulfillmentJson", "sCustGuardian...."+ sCustGuardian);
					logInfo("createRequestCIDFulfillmentJson", "sSNOGuardian ....."+ sSNOGuardian);
					if (sCustGuardianCIdTag != 0) {
						if (!sCustGuardian.equalsIgnoreCase("")) {
							logInfo("createRequestCIDFulfillmentJson","inside 19042017#### sCustGuardian is not null...."+ sCustGuardian);
						} else {
							logInfo("createRequestCIDFulfillmentJson","before sSNOGuardian.equalsIgnoreCase(sNo)");
							int SnoCompare = sNo + 1;
							logInfo("createRequestCIDFulfillmentJson", "SnoCompare..."+ SnoCompare);
							sCustGuardian = "#CUST_" + (sSNOGuardian) + "#";
							logInfo("createRequestCIDFulfillmentJson","else sCustGuardian is null 19042017....."+ sCustGuardian);
						}
					}
					logInfo("createRequestCIDFulfillmentJson", "if sCustGuardian:"+ sCustGuardian);
				} catch (Exception e) {
					logError("Exception in createRequestCIDFulfillmentJson  ", e);
				}
			}
			if(sOutput!= null && sOutput.size() > 0 ){
				employmentDetail.setProfession(sProfession);
				employmentDetail.setSalaryDate("");
				employmentDetail.setDateOfJoining("");
				employmentDetail.setStaffFlag(sStaffFlag);
				employmentDetail.setSalaryAmount(sSalary);

				companyAddress.setZip("");
				companyAddress.setCountry(country);
				companyAddress.setCity("");
				companyAddress.setState("");
				companyAddress.setLine3("");
				companyAddress.setLine2("");
				companyAddress.setLine1("");
				employmentDetail.setCompanyAddress(companyAddress);
				employmentDetail.setEmploymentId("");
				employmentDetail.setEmployerName(sEmpName);
				employmentDetail.setDesignation("");
				employmentDetail.setPosition(sCustPosition);
				employmentDetail.setEmployerPhone(sCustEmpPhoneNo);
				contactDetail.setPhone(sPhone);
				residentialAddress.setArea("");
				residentialAddress.setBuildingName("");
				residentialAddress.setCountry(sCountry);
				residentialAddress.setPoBox("");
				residentialAddress.setStreetName("");
				residentialAddress.setFlatNo("");
				residentialAddress.setCity("");
				residentialAddress.setPhone(sPhone);


				residentialAddress.setState("");
				contactDetail.setResidentialAddress(residentialAddress);
				contactDetail.setMobile(sMobile);
				contactDetail.setEmailId(sEmail);

				permanentAddress.setZip("");
				permanentAddress.setCountry(sOutput.get(0).get(31));
				permanentAddress.setCity(sOutput.get(0).get(30));
				permanentAddress.setState(sOutput.get(0).get(32));
				permanentAddress.setLine3(sOutput.get(0).get(34));
				permanentAddress.setLine2(sOutput.get(0).get(33));
				permanentAddress.setLine1(sOutput.get(0).get(29));
				contactDetail.setPermanentAddress(permanentAddress);

				officeAddress.setZip(""); 
				officeAddress.setCountry("");
				officeAddress.setCity(sOutput.get(0).get(10));
				officeAddress.setState("");
				officeAddress.setLine3("");
				officeAddress.setLine2("");
				officeAddress.setLine1("");
				contactDetail.setOfficeAddress(officeAddress);

				correspondenceAddress.setZip("");
				correspondenceAddress.setCountry(sOutput.get(0).get(35));
				correspondenceAddress.setCity(sCity);
				correspondenceAddress.setState(sState);
				correspondenceAddress.setLine3(sOutput.get(0).get(16));
				correspondenceAddress.setLine2(sOutput.get(0).get(15));
				correspondenceAddress.setLine1(sAddress1);
				contactDetail.setCorrespondenceAddress(correspondenceAddress);

				uaeNational.setExpiryDate(eidaExpiryDate);
				uaeNational.setNatlId("");
				uaeNational.setNationalId(sOutput.get(0).get(27));
				//AMIT Adhoc Changes Rishabh 100724
				//uaeNational.setIssueDate("");
				uaeNational.setIssueDate("");
				uaeNational.setCitizenshipId(sOutput.get(0).get(7));

				bankingDetail.setCustomerTypeCOD(sAccClass);
				bankingDetail.setCustomerClass("I");//hardcoded as per discussion
				bankingDetail.setMisCod1(sCenterCode);					
				bankingDetail.setMisCod2(sOutput.get(0).get(26));

				//bankingDetail.setCustomerTypeFlag("H");//Hard code as suggested by Amit 12/06/2024
				bankingDetail.setCustomerTypeFlag(sCustSeg);//AMIT Adhoc Changes Rishabh 100724

				bankingDetail.setHomeBranch(sAccBranch);
				bankingDetail.setMisCod3(sResident);
				bankingDetail.setMisCod4(sMis4);
				// COLMP-10761 | 07072024 | KRISHAN
				bankingDetail.setDsaCode("");
				bankingDetail.setRiskClassification("");
				bankingDetail.setPromoCode("");

				signature.setSignature(getSignature(sNo));
				signature.setSignatureType("3");

				LabourCard labourCard = new LabourCard();
				labourCard.setExpiryDate("");
				labourCard.setIssueDate("");
				labourCard.setLabourCardNumber("");
				customerDocument.setLabourCard(labourCard);

				Passport passport = new Passport();
				passport.setPassportNumber(sOutput.get(0).get(29));
				passport.setExpiryDate("");
				passport.setIssueDate("");
				customerDocument.setPassport(passport);

				Visa visa = new Visa();
				visa.setExpiryDate(eidaExpiryDate);
				visa.setVisaNumber(sOutput.get(0).get(7));
				visa.setIssueDate("");
				customerDocument.setVisa(visa);

				// Setting personal details
				personalDetails.setGender(sGender);
				personalDetails.setNationality(sNationality);
				personalDetails.setPrefix(sOutput.get(0).get(1));
				personalDetails.setFullName(sOutput.get(0).get(0));//Sanal Grover 12072024 Amit asked to change from customer full name
				personalDetails.setDateOfBirth(sDOB);
				personalDetails.setShortName(sOutput.get(0).get(43));
				personalDetails.setNoOfDependants("");
				personalDetails.setMinorFlag(sMinorFlag);
				personalDetails.setMaritalStatus(sMaritalStatus);
				personalDetails.setReligion(sReligion);
				payload.setCustCountry(sOutput.get(0).get(42));
			}


//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			SimpleDateFormat sdfTo = new SimpleDateFormat("dd/MM/yyyy");
//			String topicName = fulfillmentDefaultMap.get("KAFKA_TOPIC_CID_FULFILLMENT");
//			refNo = LoadConfiguration.getInstance(formObject).generateSysRefNumber(formObject);
			Date d = new Date();
			SimpleDateFormat inputFormat = new SimpleDateFormat(PM_FF_DATE_FORMAT);
			String sentAt = inputFormat.format(d);
			
			String guid = generateGUID(sWorkitemName);
			
			header.setWorkItemNo(extractStringBeforeLastHypen(sWorkitemName));
			header.setSenderId(fulfillmentDefaultMap.get("CID_FULFILL_SENDER_ID"));
			header.setSentOn(sentAt);
			header.setMessageId(guid);
			header.setEventName(fulfillmentDefaultMap.get("PM_CID_FF_EVENT_NAME"));
			header.setExternalReferenceNo(extractStringBeforeLastHypen(sWorkitemName));
			header.setCorrelationId(guid);
			header.setSentBy(fulfillmentDefaultMap.get("PM_CID_FF_SENT_BY"));
			header.setStatusCode(fulfillmentDefaultMap.get("PM_CID_FF_STATUS_CODE"));

			payload.setEmploymentDetail(employmentDetail);
			payload.setContactDetail(contactDetail);
			payload.setUaeNational(uaeNational);

			payload.setBankingDetail(bankingDetail);
			payload.setSignature(signature);
			payload.setCustomerDocument(customerDocument);
			payload.setPersonalDetails(personalDetails);

			customerFulfillmentInitiated.setHeader(header);
			customerFulfillmentInitiated.setPayload(payload);

			message = new Gson().toJson(customerFulfillmentInitiated);
			log.info("Message|CustomerFulfillmentInitiated: " + message);

		} catch (Exception e) {
			logError("createRequestCIDFulfillmentJson", e);

		}

		return message;
	}
	
	public String extractStringBeforeLastHypen(String input) {
        int lastHypenIndex = input.lastIndexOf('-');
        if (lastHypenIndex != -1) {
            return input.substring(0, lastHypenIndex);
        } else {
            return "";
        }
    }
	
	/*public String extractStringBetweenHyphens(String input) {
        // Find the positions of the first and second hyphen
        int firstHyphenIndex = input.indexOf('-');
        int secondHyphenIndex = input.indexOf('-', firstHyphenIndex + 1);

        // Extract and return the substring
        if (firstHyphenIndex != -1 && secondHyphenIndex != -1) {
            return input.substring(firstHyphenIndex + 1, secondHyphenIndex);
        } else {
            // Return an empty string if hyphens are not found
            return "";
        }
    }*/

	private String createCASAFulfillmentJSON(String sProdCode, String sAccBranch, String sCurrency) {
		logInfo("createCASAFulfillmentJSON", "ACCOUNT_FULFILLMENT");
		String message = "";
		try {
			com.newgen.iforms.user.ao.model.casafulfillment.Root casaFulfilment = new com.newgen.iforms.user.ao.model.casafulfillment.Root();
			com.newgen.iforms.user.ao.model.casafulfillment.Payload payload = new com.newgen.iforms.user.ao.model.casafulfillment.Payload();

			ArrayList<CasaAccount> casaAccounts = new ArrayList<>();
			
			String sInputXML = "";
			long end_Time = System.currentTimeMillis();
			long start_Time1 = System.currentTimeMillis();
			long diff = System.currentTimeMillis();
			try {
				int iSearchedCustomer = Integer.parseInt(formObject.getValue("NO_OF_CUST_SEARCHED").toString());
				String sOprInstruction = formObject.getValue("OPERATING_INST").toString();
				String sAccTitle = formObject.getValue("ACC_TITLE").toString();
				String sSourceCode = formObject.getValue("SOURCE_CODE").toString();
				String sAccRelation = "";
				String sNo = "";
				String sCustomerID = "";
				String sNoOfLeaves = "0";
				String sStatusCode = "";
				if (formObject.getValue("SCAN_MODE").toString().equalsIgnoreCase("New WMS ID")) {
					log.info("before writing channel_type again------" + formObject.getValue("CHANNEL_TYPE").toString());
					if (formObject.getValue("CHANNEL_TYPE").toString().equalsIgnoreCase("")) {
						log.info("writing channel_type again------");
						formObject.getDataFromDB("SELECT CHANNEL_TYPE FROM USR_0_SERVICE_CHANNEL WHERE SERVICE_CHANNEL ='"+ formObject.getValue("SOURCING_CHANNEL") + "'");
					}
					end_Time = System.currentTimeMillis();
					diff = start_Time1 - end_Time;
					log.info("Time Difference: " + diff + " for Query: " + "SELECT CHANNEL_TYPE FROM USR_0_SERVICE_CHANNEL WHERE SERVICE_CHANNEL ='"+ formObject.getValue("SOURCING_CHANNEL") + "'");
					if (formObject.getValue("CHANNEL_TYPE").toString().equalsIgnoreCase("Direct")
							|| formObject.getValue("BRNCH_OF_INSTANT_ISSUE").toString().equalsIgnoreCase("Kiosk"))
						sStatusCode = "8";
					else {
						sStatusCode = "3";
					}

				} else {
					sStatusCode = "3";
				}
				logInfo("createCASAFulfillmentJSON", "iSearchedCustomer " + iSearchedCustomer);
				if (iSearchedCustomer == 1) {
					sNo = "1";
				} else {
					for (int i = 0; i < iSearchedCustomer; i++) {
						sAccRelation = formObject.getTableCellValue(ACC_RELATION, i + 1, 7);

						if (sAccRelation.equalsIgnoreCase("JAF")
								|| sAccRelation.equalsIgnoreCase("JOF")) {
							sNo = formObject.getTableCellValue(ACC_RELATION, i + 1, 0);
							break;
						}
					}
				}
				logInfo("createCASAFulfillmentJSON", "sNo " + sNo);
				if (sNo.equalsIgnoreCase("")) {
					sNo = "1";
				}
				start_Time1 = System.currentTimeMillis();
				List<List<String>> sOutput = formObject.getDataFromDB("SELECT CUST_ID from USR_0_CUST_TXN WHERE WI_NAME ='"+ sWorkitemName + "' AND CUST_SNO='" + sNo + "'");
				sCustomerID = (sOutput != null && sOutput.size() > 0) ? sOutput.get(0).get(0).toString() : "";
				logInfo("createCASAFulfillmentJSON", "sCustomerID " + sCustomerID);
				end_Time = System.currentTimeMillis();
				diff = start_Time1 - end_Time;
				log.info("Time Difference: " + diff + " for Query: "
						+ "SELECT CUST_ID from USR_0_CUST_TXN WHERE WI_NAME ='"+ sWorkitemName + "' AND CUST_SNO='" + sNo + "'");
				if (sCustomerID.equalsIgnoreCase("")) {
					sCustomerID = "#" + "CUST_" + sNo + "#";
				}
				start_Time1 = System.currentTimeMillis();
				String sQuery = "SELECT STAFF_FLAG FROM USR_0_CUST_TXN WHERE WI_NAME = '"+ sWorkitemName + "' and CUST_SNO = '"
						+ sNo+ "'"+ " and STAFF_FLAG !='null'";
				sOutput = formObject.getDataFromDB(sQuery);
				String sStaffFlag = (sOutput != null && sOutput.size() > 0) ? sOutput
						.get(0).get(0).toString()
						: "";
				end_Time = System.currentTimeMillis();
				diff = start_Time1 - end_Time;
				log.info("Time Difference: " + diff + " for Query: " + sQuery);
				if (sStaffFlag.equalsIgnoreCase("")) {
					sStaffFlag = "N";
				} else {
					sStaffFlag = sStaffFlag.substring(0, 1);
				}
				start_Time1 = System.currentTimeMillis();
				sOutput = formObject.getDataFromDB("SELECT distinct SUB_PRODUCT_TYPE FROM USR_0_PRODUCT_TYPE_MASTER WHERE PRODUCT_CODE ='"+ sProdCode + "'");
				log.info("sOutput EMSA----" + sOutput);
				String sSubProduct = (sOutput != null && sOutput.size() > 0) ? sOutput
						.get(0).get(0).toString()
						: "";
				logInfo("createCASAFulfillmentJSON", "sSubProduct " + sSubProduct);
				String sCode18 = "";
				end_Time = System.currentTimeMillis();
				diff = start_Time1 - end_Time;
				log.info("Time Difference: "+ diff
						+ " for Query: "+ "SELECT distinct SUB_PRODUCT_TYPE FROM USR_0_PRODUCT_TYPE_MASTER WHERE PRODUCT_CODE ='"
						+ sProdCode + "'");
				if (sSubProduct.equalsIgnoreCase("EMSA")) {
					if (formObject.getValue("FAMILY_REFFERED").toString().equalsIgnoreCase("")) {
						sCode18 = getPrimaryCustomerID();
					} else {
						sCode18 = formObject.getValue("FAMILY_REFFERED").toString();
					}
				}
				start_Time1 = System.currentTimeMillis();
				log.info("COD18---" + sCode18);
				logInfo("createCASAFulfillmentJSON", "before sPrimaryCust ");
				String sPrimaryCust = getPrimaryCustomerSNO();
				logInfo("createCASAFulfillmentJSON", "after sPrimaryCust "
						+ sPrimaryCust);
				end_Time = System.currentTimeMillis();
				diff = start_Time1 - end_Time;
				log.info("Time Difference: " + diff + " for Query: " + sPrimaryCust);
				start_Time1 = System.currentTimeMillis();
				sPrimaryCust = "1";
				String sQuery1 = "SELECT SIGN_STYLE FROM USR_0_CUST_TXN B WHERE WI_NAME ='"
						+ sWorkitemName + "' AND CUST_SNO ='" + sPrimaryCust + "'";
				String sQuery3 = "select b.IVR_FACILITY,b.IB_FACILITY,b.POS_FACILITY,b.FLG_ATM_FAC from usr_0_product_master a , usr_0_atm_master b where product_code='"
						+ sProdCode
						+ "' and currency_code='"
						+ sCurrency
						+ "' and b.IVR_FACILITY=a.ivr_facility and b.IB_FACILITY=a.ib_facility and b.POS_FACILITY=a.pos_facility and b.FLG_ATM_FAC=a.atm_flag";
				String sQuery2 = "select IVR_FACILITY,IB_FACILITY,POS_FACILITY,FLG_ATM_FAC from usr_0_atm_master b where b.atm_flag='0'";

				String flagAtm = "";
				String flagIvr = "";
				String flagIB = "";
				String flagPOS = "";
				String sign = "";
				sOutput = formObject.getDataFromDB(sQuery1);
				sign = (sOutput != null && sOutput.size() > 0) ? sOutput.get(0)
						.get(0).toString() : "";
				end_Time = System.currentTimeMillis();
				diff = start_Time1 - end_Time;
				log.info("Time Difference: " + diff + " for Query: " + sQuery1);
				start_Time1 = System.currentTimeMillis();
				if (sign.indexOf("Signature") != -1) {
					logInfo("createCASAFulfillmentJSON", "inside sign if ");
					sOutput = formObject.getDataFromDB(sQuery3);
					if (sOutput != null && sOutput.size() > 0) {
						flagAtm = sOutput.get(0).get(3);
						flagIvr = sOutput.get(0).get(0);
						flagIB = sOutput.get(0).get(1);
						flagPOS = sOutput.get(0).get(2);
					}
					end_Time = System.currentTimeMillis();
					diff = start_Time1 - end_Time;
					log.info("Time Difference.....: " + diff + " for Query: "
							+ sQuery3);
				} else if (sign.indexOf("Thumb") != -1) {
					logInfo("createCASAFulfillmentJSON", "inside else if 1 ");
					sOutput = formObject.getDataFromDB(sQuery2);
					flagAtm = sOutput.get(0).get(3);
					flagIvr = sOutput.get(0).get(0);
					flagIB = sOutput.get(0).get(1);
					flagPOS = sOutput.get(0).get(2);
					end_Time = System.currentTimeMillis();
					diff = start_Time1 - end_Time;
					log.info("Time Difference: " + diff + " for Query: " + sQuery2);

				}
				
				String sCode48 = "";
				List<List<String>> misDetails = formObject.getDataFromDB("SELECT TO_CHAR(TO_DATE(GRADUATION_DATE,'dd/MM/yyyy'),'yyyy-mm-dd') GRADUATION_DATE FROM USR_0_UDF_DETAILS WHERE WI_NAME='"+ sWorkitemName + "'");
				if (misDetails != null && misDetails.size() > 0) {
					if (!(misDetails.get(0).get(0).equalsIgnoreCase("--Select--") || misDetails
							.get(0).get(0).equalsIgnoreCase(""))) {
//						sInputXML = sInputXML + "<CBRCode>" + "<Tag>48</Tag>"
//								+ "<Value>" + misDetails.get(0).get(0) + "</Value>"
//								+ "</CBRCode>";
						sCode48 = misDetails.get(0).get(0);
					}
				}
				
				List<List<String>> sOutput1 = formObject.getDataFromDB("SELECT RECORD_STATUS,NO_DEBIT,NO_CREDIT,DORMANT,FROZEN FROM usr_0_account_status_code WHERE code='"+ sStatusCode + "'");
				logInfo("createCASAFulfillmentJSON", "sOutput1 before xml "+ sOutput1);
				if (sOutput1 != null && sOutput1.size() > 0) {
					try {
						sCustomerID = String.valueOf(Integer.parseInt(sCustomerID));
					} catch (Exception e) {
						logError("createCASAFulfillmentJSON ", e);
					}
					
					String recordStatus = sOutput1.get(0).get(0);
					String flagNoDebit = sOutput1.get(0).get(1);
					String flagNoCredit = sOutput1.get(0).get(2);
					String accountDormant = sOutput1.get(0).get(3);
					String flagFrozen = sOutput1.get(0).get(4);
					
					casaAccounts.add(createCASAAccount(sAccTitle, sCustomerID, sStaffFlag, sProdCode, sAccBranch, sCurrency, "", "", flagNoDebit, flagAtm, flagPOS, sCode18, sSourceCode, flagIvr, sNoOfLeaves, accountDormant, flagIB, recordStatus, flagFrozen, sOprInstruction, flagNoCredit));
					payload.setCasaAccounts(casaAccounts);
					payload.setCustomerId(sCustomerID);
				}
				
//				logInfo("createCASAFulfillmentJSON", "sOutput1 after xml ");
//				logInfo("createCASAFulfillmentJSON", "sCode18 after xml " + sCode18);
				
//				try {
//					sCustomerID = String.valueOf(Integer.parseInt(sCustomerID));
//				} catch (Exception e) {
//					logError("createCASAFulfillmentJSON ", e);
//				}

				sInputXML = sInputXML + "</CBRCodes>"
						+ "<misCode><misCodeType>IBD_UDF1</misCodeType>"
						+ "<misCodeNumber>1</misCodeNumber><misCodeText>EMSA-"
						+ sCustomerID + "</misCodeText></misCode>"
						+ "<CustomerRelation>";
//				start_Time1 = System.currentTimeMillis();
//				sOutput = formObject.getDataFromDB("SELECT CID,ACC_RELATION from ACC_RELATION_REPEATER WHERE WI_NAME ='"+ sWorkitemName + "' ORDER BY TO_NUMBER(SNO)");
//				end_Time = System.currentTimeMillis();
//				diff = start_Time1 - end_Time;
//				log.info("Time Difference: "
//						+ diff+ " for Query: "
//						+ "SELECT CID,ACC_RELATION from ACC_RELATION_REPEATER WHERE WI_NAME ='"+ sWorkitemName + "' ORDER BY TO_NUMBER(SNO)");
//				log.info("sOutput---" + sOutput);
//				int sTemp = sOutput.size();
//				for (int i = 0; i < sTemp; i++) {
//					if (sOutput != null && sOutput.size() > 0) {
//						sCustomerID = sOutput.get(i).get(0).replaceAll("\r", "");
//						sCustomerID = sCustomerID.replaceAll("\n", "");
//						sCustomerID = sCustomerID.replaceAll("\t", "");
//						sAccRelation = sOutput.get(i).get(1).replaceAll("\r", "");
//						sAccRelation = sAccRelation.replaceAll("\n", "");
//						sAccRelation = sAccRelation.replaceAll("\t", "");
//					}
//					log.info("sCustomerID---" + sTemp);
//					if (sAccRelation.equalsIgnoreCase("Guardian")) {
//						sAccRelation = "GUR";
//					} else if (sAccRelation.equalsIgnoreCase("Minor")) {
//						sAccRelation = "SOW";
//					}
//
//					if (sCustomerID.equalsIgnoreCase("")) {
//						sCustomerID = "#" + "CUST_" + (i + 1) + "#";
//					}
//					log.info("sCustomerID---" + sCustomerID);
//					log.info("sAccRelation---" + sAccRelation);
//					try {
//						sCustomerID = String.valueOf(Integer.parseInt(sCustomerID));
//					} catch (Exception e) {
//						logError("createCASAFulfillmentJSON ", e);
//					}
//					sInputXML = sInputXML + "<CustRel>" + "<CustomerId>"
//							+ sCustomerID + "</CustomerId>" + "<Relation>"
//							+ sAccRelation + "</Relation>" + "</CustRel>";
//				}

				sInputXML = sInputXML + "</CustomerRelation>" + "<EngineName>"
						+ sEngineName + "</EngineName>" + "<SessionId>"
						+ sSessionId + "</SessionId>" + "<WiName>" + sWorkitemName
						+ "</WiName>" + "<REF_NO>#REF_NO#</REF_NO>"
						+ "</APWebService_Input>";

				log.info("sInputXML---" + sInputXML);
			} catch (Exception e) {
				logError("Exception in  createCASAFulfillmentJSON ", e);
			}
			
//			String topicName = fulfillmentDefaultMap.get("KAFKA_TOPIC_CASA_FULFILLMENT");
//			refNo = CBGUtils.getInstance().generateSysRefNumber();

			casaFulfilment.setHeader(createHeader());
			casaFulfilment.setPayload(payload);

			message = new Gson().toJson(casaFulfilment);
			log.info("Message|CASAFulfilment: " + message);
		} catch (Exception e) {
			logError("createCASAAccount", e);
		}
		
		return message;
	}
	
	private CasaAccount createCASAAccount(String accountTitle, String customerID, String flgEmpAccount, String sProdCode, String sAccBranch, String sCurrency, String isDcReq, String linkedWith,
			String flagNoDebit, String flagAtm, String flagPOS, String sCode18, String sSourceCode, String flagIvr, String sNoOfLeaves, String accountDormant, String flagIB, String recordStatus, 
			String flagFrozen, String sOprInstruction, String flagNoCredit) {
		CasaAccount casaAccount = new CasaAccount();
		try {
			casaAccount.setCasaAccountDetails(createCasaAccDetails(accountTitle, customerID, flgEmpAccount, sProdCode, sAccBranch, sCurrency, isDcReq, linkedWith,
					flagNoDebit, flagAtm, flagPOS, sCode18, sSourceCode, flagIvr, sNoOfLeaves, accountDormant, flagIB, recordStatus, flagFrozen, sOprInstruction,
					flagNoCredit));
			casaAccount.setDebitCardDetails(createDebitCardDetails());
			casaAccount.setChequeDetails(createChequeDetails());
			casaAccount.setCustomerAccountDetails(null);
		} catch (Exception e) {
			logError("createCASAAccount", e);
		}
		return casaAccount;
	}
	
	private DebitCardDetails createDebitCardDetails() {
		DebitCardDetails debitCardDetails = new DebitCardDetails();
		try {
			String sQuery = "SELECT REP_EMBOSS_NAME,REP_GROUP_TYPE,PROD_GROUP FROM DEBIT_CARD_REP WHERE "
					+ "WI_NAME ='"+ sWorkitemName +"' AND REP_NEW_LINK='New' and instr(card_number,'_')=0 "
					+ "ORDER BY TO_NUMBER(CID)";
			logInfo("insertDataInIntegrationTable",
					"DEBITCARD_NEW sQuery: " + sQuery);
			List<List<String>> output = formObject.getDataFromDB(sQuery);
			
			String cardEmbossName = output.get(0).get(0).trim();
			
			String sInstantYes = "";
			String sInstantNo = "";
			String sInstantFlag = "";
			
			String val = formObject.getValue(RD_INST_DEL).toString();
			if ("Yes".equalsIgnoreCase(val)) {
				sInstantYes = "true";
				sInstantNo = "false";
			} else if ("No".equalsIgnoreCase(val)) {
				sInstantYes = "false";
				sInstantNo = "true";
			}

			logInfo("INSTANT_DEL_YES====", sInstantYes + " and INSTANT_DEL_NO" + sInstantNo);
			if (sInstantYes.equalsIgnoreCase("true") && sInstantNo.equalsIgnoreCase("false")) {
				sInstantFlag = "Y";
				log.info(" inside 1st if --------sInstantFlag =====" + sInstantFlag);
			} else if (sInstantYes.equalsIgnoreCase("false") && sInstantNo.equalsIgnoreCase("true")) {
				sInstantFlag = "N";
				log.info(" inside 2nd if --------sInstantFlag =====" + sInstantFlag);
			}
			
			String cardProductGroup = output.get(0).get(1);
			if (cardProductGroup.equalsIgnoreCase("Islamic")) {
				cardProductGroup = "ISM";
			} else if (cardProductGroup.equalsIgnoreCase("Etihad")) {
				cardProductGroup = "ETD";
			} else {
				cardProductGroup = "CVNONS";
			}
			
			debitCardDetails.setCardEmbossName(cardEmbossName);
			debitCardDetails.setCardProductGroup(cardProductGroup);
			debitCardDetails.setInstantFlag(sInstantFlag);
		} catch (Exception e) {
			logError("createDebitCardDetails", e);
		}
		return debitCardDetails;
	}
	
	private CustomerRelation createCustRelation() {
		CustomerRelation customerRelation = new CustomerRelation();
		try {
			PrimaryRel primaryRel = new PrimaryRel();
			primaryRel.setAccountNumber(BLANK);
			primaryRel.setRelationship(fulfillmentDefaultMap.get("PM_CASA_FF_PRI_RELATIONSHIP"));
			
			List<PrimaryRel> primaryRels = new ArrayList<>();
			primaryRels.add(primaryRel);

			PrimaryRelation primaryRelation = new PrimaryRelation();
			primaryRelation.setPrimaryRel(primaryRels);
			
			customerRelation.setPrimaryRelation(primaryRelation);
		} catch (Exception e) {
			logError("createCustRelation", e);
		}
		return customerRelation;
	}
	
	private CASAAccountDetails createCasaAccDetails(String accountTitle, String customerID, String flgEmpAccount, String sProdCode, String sAccBranch, String sCurrency, String isDcReq, String linkedWith,
			String flagNoDebit, String flagAtm, String flagPOS, String sCode18, String sSourceCode, String flagIvr, String sNoOfLeaves, String accountDormant, String flagIB, String recordStatus, 
			String flagFrozen, String sOprInstruction, String flagNoCredit) {
		CASAAccountDetails casaAccountDetails = new CASAAccountDetails();
		try {
			casaAccountDetails.setFlagNoDebit(flagNoDebit);
			casaAccountDetails.setFlagATM(flagAtm);
			casaAccountDetails.setFlagPOS(flagPOS);
			casaAccountDetails.setAccountTitle(accountTitle);
			casaAccountDetails.setStatementCopies(fulfillmentDefaultMap.get("STATEMENT_COPIES"));
			casaAccountDetails.setFlagIVR(flagIvr);
			casaAccountDetails.setCustomerId(customerID);
			casaAccountDetails.setMaintenanceOption(fulfillmentDefaultMap.get("PM_CASA_FF_MAINTENANCE_OPTION"));
			casaAccountDetails.setCtrlLeaves(sNoOfLeaves);
			casaAccountDetails.setFlagHoldMail(fulfillmentDefaultMap.get("FLAG_HOLD_MAIL"));
			casaAccountDetails.setFlagAcctDormant(accountDormant);
			casaAccountDetails.setMakerId(fulfillmentDefaultMap.get("PM_CASA_FF_SENDER_ID"));
			casaAccountDetails.setFlagEmpAccount(flgEmpAccount);
			casaAccountDetails.setFlagIB(flagIB);
			casaAccountDetails.setAccountNumber(BLANK);
			casaAccountDetails.setFlagMailAddCtrl(fulfillmentDefaultMap.get("FLAG_MAIL_ADDCTRL"));
			casaAccountDetails.setProductCode(sProdCode);
			casaAccountDetails.setRecordStatus(recordStatus);
			casaAccountDetails.setAccountCcyCode(sCurrency);
			casaAccountDetails.setFlagAcctFrozen(flagFrozen);
			casaAccountDetails.setOperatingInstruction(sOprInstruction);
			casaAccountDetails.setAccountStatusCode(BLANK);
			casaAccountDetails.setCheckerId(fulfillmentDefaultMap.get("PM_CASA_FF_SENDER_ID"));
			casaAccountDetails.setOrigRefNumber(BLANK);
			casaAccountDetails.setCustomerBranchCode(sAccBranch);
			casaAccountDetails.setFlagNoCredit(flagNoCredit);
			
			casaAccountDetails.setAccountCBRCodes(accountCBRCodes(sCode18, sSourceCode));
			
			casaAccountDetails.setCustomerRelation(createCustRelation());

			casaAccountDetails.setIsDCRequired(isDcReq);
			casaAccountDetails.setLinkedWith(linkedWith);
		} catch (Exception e) {
			logError("createCasaAccDetails", e);
		}
		return casaAccountDetails;
	}
	
	private AccountCBRCodes accountCBRCodes(String sCode18, String sSourceCode) {
		AccountCBRCodes accountCBRCodes = new AccountCBRCodes();
		try {
			accountCBRCodes.setCBRCodes(createCBRCodeList(sCode18, sSourceCode));
		} catch (Exception e) {
			logError("accountCBRCodes", e);
		} 
		return accountCBRCodes;
	}
	
	private List<CBRCode> createCBRCodeList(String sCode18, String sSourceCode) {
		List<CBRCode> cbrCodes = new ArrayList<>();
		try {
			if (!sCode18.isEmpty()) {
				CBRCode cbrCode18 = new CBRCode();
				cbrCode18.setCBRCode("18");
				cbrCode18.setCBRValue(sCode18);
				cbrCodes.add(cbrCode18);
			}
			if (!sSourceCode.equalsIgnoreCase("--Select--") && !sSourceCode.isEmpty()) {
				CBRCode cbrCode2 = new CBRCode();
				cbrCode2.setCBRCode("2");
				cbrCode2.setCBRValue(sSourceCode);
				cbrCodes.add(cbrCode2);
			}
		} catch (Exception e) {
			logError("createCBRCodeList", e);
		}
		return cbrCodes;
	}
	
	private ChequeDetails createChequeDetails() {
		ChequeDetails chequeDetails = new ChequeDetails();
		try {
			chequeDetails.setSrChequeBook(createSRChequeBook());
			chequeDetails.setCbRequest(createCBRequest());
		} catch (Exception e) {
			logError("createChequeDetails", e);
		}
		return chequeDetails;
	}
	
	private CBRequest createCBRequest() {
		CBRequest cbRequest = new CBRequest();
		try {
			cbRequest.setPhotoIdType(BLANK);
			cbRequest.setCustomerAddressCity(BLANK);
			cbRequest.setPhotoIdNo(BLANK);
			cbRequest.setCustomerAddressCountry(BLANK);
			cbRequest.setThirdPartyMobileNo(BLANK);
			cbRequest.setCustomerAddresszip(BLANK);
			cbRequest.setCustomerNumber(BLANK);
			cbRequest.setAccountNumber(BLANK);
			cbRequest.setNoofchequeBooks(BLANK);
			cbRequest.setCustomerMobileNo(BLANK);
			cbRequest.setBookSize(BLANK);
			cbRequest.setThirdPartyName(BLANK);
			cbRequest.setCustomerAddressStateEmirate(BLANK);
			cbRequest.setCustomerAddress1(BLANK);
			cbRequest.setCustomerAddress3(BLANK);
			cbRequest.setFlagDeliveryMode(BLANK);
			cbRequest.setCustomerAddress2(BLANK);
		} catch (Exception e) {
			logError("createCBRequest", e);
		}
		return cbRequest;
	}
	
	private SRChequeBook createSRChequeBook() {
		logInfo("createSRChequeBook", "CHEQUEBOOK");
		
		SRChequeBook srChequeBook = new SRChequeBook();
		
		try {
			Date d = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String sDate = dateFormat.format(d);
			
			String sCustomerID = getPrimaryCustomerID();
			
			String sQuery = "";
			List<List<String>> sOutput = null;
			if (sCustomerID.indexOf("#") != -1) {
				sQuery = "SELECT COUNT(CB_CODES) AS CB_CODE FROM USR_0_CB_RESTRICT_CODE WHERE CB_CODES = (SELECT VALUE FROM USR_0_DEFAULTVALUE_FCR WHERE NAME = (SELECT DECODE(IS_UAE_RESIDENT,'Yes','Resident','Non-Resident') FROM USR_0_CUST_TXN WHERE WI_NAME ='"
						+ sWorkitemName
						+ "' AND CUST_SNO = '"
						+ sCustomerID.substring(6, sCustomerID.length() - 1)
						+ "'))";
			} else {
				sQuery = "SELECT COUNT(CB_CODES) AS CB_CODE FROM USR_0_CB_RESTRICT_CODE WHERE CB_CODES = (SELECT VALUE FROM USR_0_DEFAULTVALUE_FCR WHERE NAME = (SELECT DECODE(IS_UAE_RESIDENT,'Yes','Resident','Non-Resident') FROM USR_0_CUST_TXN WHERE WI_NAME ='"
						+ sWorkitemName
						+ "' AND CUST_ID = '"
						+ sCustomerID
						+ "'))";
			}
			logInfo(" sQuery ", sQuery);
			sOutput = formObject.getDataFromDB(sQuery);

			if (sOutput != null && sOutput.size() > 0) {
				if (sOutput.get(0).get(0).equals("0")) {
					sQuery = "SELECT (SELECT VALUE FROM USR_0_DEFAULTVALUE_FCR WHERE NAME='ChequeBookStatus') "
							+ "ChequeBookStatus ,(SELECT VALUE FROM USR_0_DEFAULTVALUE_FCR WHERE "
							+ "NAME ='FlagChequeBookType')FlagChequeBookType,(SELECT VALUE FROM "
							+ "USR_0_DEFAULTVALUE_FCR WHERE NAME ='FlagChequeType')FlagChequeType,"
							+ "(SELECT VALUE FROM USR_0_DEFAULTVALUE_FCR WHERE NAME ='FlagPRN')FlagPRN,"
							+ "(SELECT VALUE FROM USR_0_DEFAULTVALUE_FCR WHERE NAME ='FlagServiceChargesWaiver')"
							+ "FlagServiceChargesWaiver,(SELECT VALUE FROM USR_0_DEFAULTVALUE_FCR WHERE "
							+ "NAME ='NoOfLeavesRequested')NoOfLeavesRequested FROM DUAL";
					sOutput = formObject.getDataFromDB(sQuery);
					
					srChequeBook.setChequeBookSerialNo(BLANK);
					srChequeBook.setChequeBookStatus(sOutput.get(0).get(0));
					srChequeBook.setUpdateSerialNo(BLANK);
					srChequeBook.setCustAccountNumber(BLANK);
					srChequeBook.setNoOfLeavesRequested(sOutput.get(0).get(5));
					srChequeBook.setChequeEndNumber(BLANK);
					srChequeBook.setFlagPRN(sOutput.get(0).get(3));
					srChequeBook.setChequeBookIssueDate(sDate);
					srChequeBook.setChequePaidStatus(BLANK);
					srChequeBook.setFlagChequeType(sOutput.get(0).get(2));
					srChequeBook.setCheckerId(fulfillmentDefaultMap.get("PM_CASA_FF_SENDER_ID"));
					srChequeBook.setMaintenanceOption(BLANK);
					srChequeBook.setFlagServiceChargesWaiver(sOutput.get(0).get(4));
					srChequeBook.setOrigRefNumber(BLANK);
					srChequeBook.setFlagChequeBookType(sOutput.get(0).get(1));
					srChequeBook.setChequeStartNumber(BLANK);
					srChequeBook.setMakerId(fulfillmentDefaultMap.get("PM_CASA_FF_SENDER_ID"));
				}
			}
		} catch (Exception e) {
			logError("createSRChequeBook", e);
		}
		return srChequeBook;
	}
	
	private com.newgen.iforms.user.ao.model.casafulfillment.Header createHeader() {
		com.newgen.iforms.user.ao.model.casafulfillment.Header header = new com.newgen.iforms.user.ao.model.casafulfillment.Header();
		try {
			SimpleDateFormat inputFormat = new SimpleDateFormat(PM_FF_DATE_FORMAT);
			String sentAt = "";
			Date d = new Date();
			sentAt = inputFormat.format(d);
			String guid = generateGUID(sWorkitemName);
			header.setWorkItemNo(sWorkitemName);
			header.setSenderId(fulfillmentDefaultMap.get("CASA_FULFILL_SENDER_ID"));
			header.setSentOn(sentAt);
			header.setMessageId(guid);
			header.setEventName(fulfillmentDefaultMap.get("PM_CASA_FF_EVENT_NAME"));
			header.setExternalReferenceNo(extractStringBeforeLastHypen(sWorkitemName));
			header.setCorrelationId(guid);
			header.setSentBy(fulfillmentDefaultMap.get("PM_CASA_FF_SENT_BY"));
			header.setStatusCode(fulfillmentDefaultMap.get("PM_CASA_FF_STATUS_CODE"));
		} catch (Exception e) {
			logError("createHeader", e);
		}
		return header;
	}
	
	public String getIndustryRisk(String priSrcOfIncome) {
		String [] priSrcOfInc =priSrcOfIncome.replaceAll("\\[","").replaceAll("\\]","").replaceAll("\"","").split(",");
		StringJoiner str =new StringJoiner("|");
		for(int k=0;k<priSrcOfInc.length;k++) {
			str.add(priSrcOfInc[k]);
		}
		return str.toString();
	}
		
	public int CalculateAge1(String dob) {
		Calendar dobDate = Calendar.getInstance();
		Calendar today = Calendar.getInstance();
		int curYear = today.get(Calendar.YEAR);
		int curMonth = today.get(Calendar.MONTH);
		int curDay = today.get(Calendar.DAY_OF_MONTH);

		SimpleDateFormat f = new SimpleDateFormat(DATEFORMAT);
		String d1 = dob;
		try {
			Date formatted1 = f.parse(d1);
			dobDate.setTime(formatted1);
		} catch (ParseException e) {
			logError("CalculateAge1", e);
		}
		int year = dobDate.get(Calendar.YEAR);
		int month = dobDate.get(Calendar.MONTH);
		int day = dobDate.get(Calendar.DAY_OF_MONTH);

		int age = curYear - year;
		if (curMonth < month || (month == curMonth && curDay < day)) {
			age--;
		}

		log.info("age==" + age);
		return age;
	}
		
		public String createCidInputXML(int sNo, String sCallName) throws Exception {
			StringBuilder inputXML = new StringBuilder();
			try {
				String topicName = fulfillmentDefaultMap.get("KAFKA_TOPIC_CID_FULFILLMENT");
				String message = createRequestCIDFulfillmentJson(sNo);
//				String refNo = LoadConfiguration.getInstance(formObject).generateSysRefNumber(formObject);
				inputXML.append("<?xml version=\"1.0\"?>").append("\n")
				.append("<APWebService_Input>").append("\n")
				.append("<Option>WebService</Option>").append("\n")
				.append("<EngineName>" +sEngineName + "</EngineName>").append("\n")
				.append("<SessionId>" + sSessionId + "</SessionId>").append("\n")
				.append("<WINAME>" + sWorkitemName + "</WINAME>").append("\n")
				.append("<ProcessName>"+ formObject.getProcessName() +"</ProcessName>").append("\n")
				.append("<CallType>WS-2.0</CallType>").append("\n")
				.append("<InnerCallType>PushMessageTopic</InnerCallType>").append("\n")
				.append("<CallName>"+ sCallName +"</CallName>").append("\n")
//				.append("<OLDREF_NO>"+ refNo +"</OLDREF_NO>").append("\n")
//				.append("<senderID>WMS</senderID>").append("\n")
				.append("<PushMessageCIDFulfillment>").append("\n")
				.append("<TopicName>" + topicName + "</TopicName>" ).append("\n")
				.append("<Message>" + message + "</Message>" ).append("\n")
				.append("</PushMessageCIDFulfillment>").append("\n")
				.append("</APWebService_Input>");
			}catch (Exception e) {
				log.error("createInputXML", e );
			}
			return inputXML.toString();	
		}
		
		public String createCASAInputXML(String sProdCode, String sAccBranch, String sCurrency, String sCallName) throws Exception {
			StringBuilder inputXML = new StringBuilder();
			try {
				String topicName = fulfillmentDefaultMap.get("KAFKA_TOPIC_CASA_FULFILLMENT");
				String message = createCASAFulfillmentJSON(sProdCode, sAccBranch, sCurrency);
//				String refNo = LoadConfiguration.getInstance(formObject).generateSysRefNumber(formObject);
				inputXML.append("<?xml version=\"1.0\"?>").append("\n")
				.append("<APWebService_Input>").append("\n")
				.append("<Option>WebService</Option>").append("\n")
				.append("<EngineName>" +sEngineName + "</EngineName>").append("\n")
				.append("<SessionId>" + sSessionId + "</SessionId>").append("\n")
				.append("<WINAME>" + sWorkitemName + "</WINAME>").append("\n")
				.append("<ProcessName>"+ formObject.getProcessName() +"</ProcessName>").append("\n")
				.append("<CallType>WS-2.0</CallType>").append("\n")
				.append("<InnerCallType>PushMessageTopic</InnerCallType>").append("\n")
				.append("<CallName>"+ sCallName +"</CallName>").append("\n")
//				.append("<OLDREF_NO>"+ refNo +"</OLDREF_NO>").append("\n")
//				.append("<senderID>WMS</senderID>").append("\n")
				.append("<PushMessageCIDFulfillment>").append("\n")
				.append("<TopicName>" + topicName + "</TopicName>" ).append("\n")
				.append("<Message>" + message + "</Message>" ).append("\n")
				.append("</PushMessageCIDFulfillment>").append("\n")
				.append("</APWebService_Input>");
			}catch (Exception e) {
				log.error("createInputXML", e );
			}
			return inputXML.toString();	
		}
		
		public void insertFulfillmentDataInIntegrationTable() {
			String	sQuery = "SELECT CALLTYPE,MANDATORY_STATUS FROM USR_0_AO_FULFILLMENT_CONFIG WHERE CALL_ORDER IS NOT NULL "
					+ "ORDER BY TO_NUMBER(CALL_ORDER)";
			logInfo("insertFulfillmentDataInIntegrationTable","sQuery: "+sQuery);
			List<List<String>> fulfillment = formObject.getDataFromDB(sQuery);
			logInfo("insertFulfillmentDataInIntegrationTable","fulfillment: "+fulfillment);
//			int i =0;
			String sCallType;
			String sStatus;
			int iNoOfCustomer = 0;
			long start_Time1 = System.currentTimeMillis();
			long end_Time = System.currentTimeMillis();
			long diff = System.currentTimeMillis();
			String sCallName = "";
			int iCount = 0;
			String sCustID = "";
			String sInputXML = "";
			String sRequestDate = "";
			List<List<String>> output2;
			int sout = 0;
			int iCallOrder = 1;
			String sValues = "";
			String updatequery = "";
			String sTable = "USR_0_INTEGRATION_CALLS";
			String sColumn = "WI_NAME,CUST_NO,USER_NAME,CALL_NAME,CALL_ORDER,RETRY_COUNT,INPUT_XML,STATUS,"
					+ "MANDATE_STATUS,REQUEST_DATETIME,SLNO,SOURCE,ORCHESTRATION_TYPE";
			if (!formObject.getValue(NO_OF_CUST_SEARCHED).toString().isEmpty()) {
				iNoOfCustomer = Integer.parseInt(formObject.getValue(NO_OF_CUST_SEARCHED).toString());
			}
			start_Time1 = System.currentTimeMillis();
			if (fulfillment != null && fulfillment.size() > 0) {
				for (int i = 0; i < fulfillment.size(); i++) {
					sCallType = fulfillment.get(i).get(0);
					sStatus = fulfillment.get(i).get(1);

					if (sCallType.equalsIgnoreCase("CID_FULFILLMENT")) {
						logInfo("insertFulfillmentDataInIntegrationTable","CID_FULFILLMENT");
						logInfo("insertFulfillmentDataInIntegrationTable"," iNoOfCustomer " + iNoOfCustomer);
						if (formObject.getValue("ACC_OWN_TYPE").toString().equalsIgnoreCase("Minor")) {
							for (int sNo = iNoOfCustomer - 1; sNo >= 0; sNo--) {
								start_Time1 = System.currentTimeMillis();
								String sName = formObject.getTableCellValue(
										ACC_RELATION, sNo, 1);
								sCallName = "CID_FULFILLMENT_" + (sNo + 1)+ "_" + sName;
								logInfo("insertFulfillmentDataInIntegrationTable ","CID_FULFILLMENT sName :" + sName+ " sNo :" + sNo
										+ " sCallName :" + sCallName+ " iNoOfCustomer :"+ iNoOfCustomer);
								int cust_no = sNo + 1;
								sQuery = "SELECT COUNT(WI_NAME) AS COUNT_WI FROM USR_0_INTEGRATION_CALLS WHERE WI_NAME='"
										+ sWorkitemName
										+ "' and STATUS = 'Success' and call_name like 'CID_FULFILLMENT_"
										+ cust_no + "%'";
								output2 = formObject.getDataFromDB(sQuery);
								iCount = (output2 != null && output2.size() > 0) ? Integer.parseInt(output2.get(0).get(0).toString()) : 0;
								end_Time = System.currentTimeMillis();
								diff = start_Time1 - end_Time;
								if (iCount == 0) {
									sCustID = formObject.getTableCellValue(ACC_RELATION, sNo, 2);
									if (sCustID.equalsIgnoreCase("")) {
										try {
											sInputXML = createCidInputXML(sNo + 1, sCallName);
										} catch (Exception e) {
											logError("insertFulfillmentDataInIntegrationTable", e);
										}
										sRequestDate = getReqDate();
										if (!sInputXML.equalsIgnoreCase("")) {
											sValues = "'"+ sWorkitemName+ "','"+ (sNo + 1)+ "','"+ sUserName
													+ "','"+ sCallName+ "','"+ iCallOrder+ "','0',"+ (convertToNClob(sInputXML))
													+ ",'Pending','"+ sStatus+ "',to_date('"
													+ sRequestDate+ "','dd/MM/yyyy HH24:mi:ss'),'"
													+ (cust_no) + "','"+ sActivityName + "'"
													+ ",'"+FULFILLMENT+"'";// NEW VALUE ADDED FOR FULFILLMENT
											sout = insertDataIntoDB("USR_0_INTEGRATION_CALLS",sColumn, sValues);
											if (String.valueOf(sout).equalsIgnoreCase("1")) {
												iCallOrder = iCallOrder + 1;
											}
										}
									}
								} else {
									updatequery = "update " + sTable+ " set CALL_ORDER='" + iCallOrder+ "' where WI_NAME='" + sWorkitemName
											+ "'" + "  and CALL_NAME='"
											+ sCallName + "'";
									sout = formObject.saveDataInDB(updatequery);
									iCallOrder = iCallOrder + 1;
								}
							}
							logInfo("insertFulfillmentDataInIntegrationTable","outside if CID_FULFILLMENT");
						} else {
							logInfo("insertFulfillmentDataInIntegrationTable",
									"CID_FULFILLMENT   else condition");
							logInfo("insertFulfillmentDataInIntegrationTable",
									" iNoOfCustomer " + iNoOfCustomer);
							for (int sNo = 0; sNo < iNoOfCustomer; sNo++) {
								start_Time1 = System.currentTimeMillis();
								String sName = formObject.getTableCellValue(ACC_RELATION, sNo, 1);
								sCallName = "CID_FULFILLMENT_" + (sNo + 1)+ "_" + sName;
								logInfo("insertFulfillmentDataInIntegrationTable ","CID_FULFILLMENT sName :" + sName+ " sNo :" + sNo
										+ " sCallName :" + sCallName+ " iNoOfCustomer :"+ iNoOfCustomer);
								int cust_no = sNo + 1;
								sQuery = "SELECT COUNT(WI_NAME) AS COUNT_WI FROM USR_0_INTEGRATION_CALLS WHERE WI_NAME='"
										+ sWorkitemName
										+ "' and STATUS = 'Success' and call_name like 'CID_FULFILLMENT_"
										+ cust_no + "%'";
								output2 = formObject.getDataFromDB(sQuery);
								iCount = (output2 != null && output2.size() > 0) ? Integer
										.parseInt(output2.get(0).get(0)
												.toString()) : 0;
								end_Time = System.currentTimeMillis();
								diff = start_Time1 - end_Time;
								logInfo("insertFulfillmentDataInIntegrationTable",
										"CID_FULFILLMENT  iCount " + iCount);
								if (iCount == 0) {
									sCustID = formObject.getTableCellValue(
											ACC_RELATION, sNo, 2);
									if (sCustID.equalsIgnoreCase("")) {
										try {
											sInputXML = createCidInputXML(sNo + 1,sCallName);
										} catch (Exception e) {
											logError("insertFulfillmentDataInIntegrationTable", e);
										}
										sRequestDate = getReqDate();
										if (!sInputXML.equalsIgnoreCase("")) {
											sValues = "'"+ sWorkitemName+ "','"+ (sNo + 1)+ "','"+ sUserName+ "','"+ sCallName
													+ "','"+ iCallOrder+ "','0',"+ (convertToNClob(sInputXML))+ ",'Pending','"+ sStatus
													+ "',to_date('"+ sRequestDate+ "','dd/MM/yyyy HH24:mi:ss'),'"+ (sNo + 1) + "','"
													+ sActivityName + "'"+ ",'"+FULFILLMENT+"'";// NEW VALUE ADDED FOR FULFILLMENT
											sout = insertDataIntoDB("USR_0_INTEGRATION_CALLS",sColumn, sValues);
											if (String.valueOf(sout).equalsIgnoreCase("1")) {
												iCallOrder = iCallOrder + 1;
											}
										}
									}
								} else {
									updatequery = "update " + sTable+ " set CALL_ORDER='" + iCallOrder+ "' where WI_NAME='" + sWorkitemName
											+ "'" + "  and CALL_NAME='"+ sCallName + "'";
									sout = formObject.saveDataInDB(updatequery);
									iCallOrder = iCallOrder + 1;
								}
							}

							logInfo("insertFulfillmentDataInIntegrationTable","outside else CID_FULFILLMENT");
						}
					}
					else if (sCallType.equalsIgnoreCase("CASA_FULFILLMENT")) {
						logInfo("insertDataInIntegrationTable", "CASA_FULFILLMENT");
						start_Time1 = System.currentTimeMillis();
						
						sQuery = "SELECT PROD_CODE,ACC_BRANCH,CURRENCY,ACC_NO FROM USR_0_PRODUCT_SELECTED WHERE "
								+ "WI_NAME='"+ sWorkitemName + "' ORDER BY TO_NUMBER(INSERTIONORDERID)";

						logInfo("insertDataInIntegrationTable", "sQuery ### " + sQuery);
						end_Time = System.currentTimeMillis();
						diff = start_Time1 - end_Time;
						output2 = formObject.getDataFromDB(sQuery);
						logInfo("insertDataInIntegrationTable", "CASA_FULFILLMENT output2 " + output2);
						String sAccBranch = "", sCurrency = "";
						
						int iNoOfProduct = output2.size();
						logInfo("insertDataInIntegrationTable", "CASA_FULFILLMENT iNoOfProduct " + iNoOfProduct);
						for (int np = 0; np < iNoOfProduct; np++) {
							String sProdCode = output2.get(np).get(0);
							String sAccNo = output2.get(np).get(3);
							sAccBranch = output2.get(np).get(1);
							sCurrency = output2.get(np).get(2);
							logInfo("insertDataInIntegrationTable",
									"CASA_FULFILLMENT sProdCode " + sProdCode);
							logInfo("insertDataInIntegrationTable",
									"CASA_FULFILLMENT sAccNo " + sAccNo);
							start_Time1 = System.currentTimeMillis();
							sCallName = "CASA_FULFILLMENT_" + sProdCode + "_" + (np + 1);
							sQuery = "SELECT COUNT(WI_NAME) AS COUNT_WI FROM USR_0_INTEGRATION_CALLS WHERE WI_NAME='"
									+ sWorkitemName + "' and CALL_NAME='" + sCallName + "' and STATUS = 'Success'";
							List<List<String>> countOutput = formObject.getDataFromDB(sQuery);
							iCount = Integer.parseInt(countOutput.get(0).get(0));
							logInfo("insertDataInIntegrationTable CASA_FULFILLMENT", "iCount " + iCount);
							
							end_Time = System.currentTimeMillis();
							diff = start_Time1 - end_Time;
							if (iCount == 0) {
								try {
									sInputXML = createCASAInputXML(sProdCode, sAccBranch, sCurrency, sCallName);
								} catch (Exception e) {
									logError("insertFulfillmentDataInIntegrationTable", e);
								}
								sRequestDate = getReqDate();
								if (!sInputXML.equalsIgnoreCase("")) {
									sValues = "'" + sWorkitemName + "','"
											+ (np + 1) + "','" + sUserName
											+ "','" + sCallName + "','"
											+ iCallOrder + "','0','"
											+ sInputXML + "','Pending','"
											+ sStatus + "',to_date('"
											+ sRequestDate
											+ "','dd/MM/yyyy HH24:mi:ss'),'"
											+ (np + 1) + "','" + sActivityName
											+ "'"
											+ ",'"+FULFILLMENT+"'";// NEW VALUE ADDED FOR FULFILLMENT
									sout = insertDataIntoDB(
											"USR_0_INTEGRATION_CALLS", sColumn,
											sValues);
									if (String.valueOf(sout).equalsIgnoreCase("1")) {
										iCallOrder = iCallOrder + 1;
									}
								}
							} else {
								updatequery = "update " + sTable
										+ " set CALL_ORDER='" + iCallOrder
										+ "' where WI_NAME='" + sWorkitemName
										+ "'" + "  and CALL_NAME='" + sCallName
										+ "'";
								sout = formObject.saveDataInDB(updatequery);
								iCallOrder = iCallOrder + 1;
							}
						}
						logInfo("insertDataInIntegrationTable",
								"outside CASA_FULFILLMENT");
					}
				}
			}
		}	
		
		public void updateCallOrder() {
			String sQuery = "SELECT CALL_NAME FROM USR_0_INTEGRATION_CALLS  WHERE WI_NAME = '"+ sWorkitemName +"' AND ORCHESTRATION_TYPE != 'NA'"
					+ " ORDER BY (CASE WHEN ORCHESTRATION_TYPE = 'PRE' THEN 1 END), (CASE WHEN ORCHESTRATION_TYPE = 'FULFILLMENT' THEN 2 END),"
					+ "(CASE WHEN ORCHESTRATION_TYPE = 'POST' THEN 3 END) ASC, TO_NUMBER(CALL_ORDER) ASC";
			logInfo("updateCallOrder","sQuery: "+sQuery);
			List<List<String>> fulfillment = formObject.getDataFromDB(sQuery);
			logInfo("updateCallOrder","fulfillment: "+fulfillment);
			
			if (fulfillment != null && fulfillment.size() > 0) {
				for (int callOrder = 0; callOrder < fulfillment.size(); callOrder++) {
					String sCallName = fulfillment.get(callOrder).get(0);
					
					String updateQuery = "UPDATE USR_0_INTEGRATION_CALLS SET CALL_ORDER = '"+ (callOrder+1) +"' WHERE WI_NAME = '"+ sWorkitemName +"' AND CALL_NAME = '"+ sCallName +"'";
					logInfo("updateCallOrder","updateQuery: "+updateQuery);
					int updateStatus = formObject.saveDataInDB(updateQuery);
					logInfo("updateCallOrder","updateStatus: "+updateStatus);
				}
				
				String updateQueryNA = "UPDATE USR_0_INTEGRATION_CALLS SET CALL_ORDER = '"+ 0 +"' WHERE WI_NAME = '"+ sWorkitemName +"' AND ORCHESTRATION_TYPE = '"+ NOT_APPLICABLE_FULFILLMENT +"'";
				logInfo("updateCallOrder","updateQueryNA: "+updateQueryNA);
				int updateStatusNA = formObject.saveDataInDB(updateQueryNA);
				logInfo("updateCallOrder","updateStatusNA: "+updateStatusNA);
			}
		}
		
	    public void loadFulfillmentdata(){
	    	JSONArray jsonArray = new JSONArray();
			
	    	String sQuery= "SELECT CALL_NAME,STATUS,MANDATE_STATUS,RESPONSE,to_char(REQUEST_DATETIME,'dd/mm/yyyy hh:mi:ss')REQUEST_DATETIME,"
	    			+ "ERRORDESC FROM USR_0_INTEGRATION_CALLS WHERE WI_NAME = '"+sWorkitemName+"' and ORCHESTRATION_TYPE = '"+FULFILLMENT+"'"
	    			+ " ORDER BY to_number(CALL_ORDER)";
	    	logInfo("loadFulfillmentdata.... ", "sQuery" + sQuery);
	    	List<List<String>> sOutput = formObject.getDataFromDB(sQuery);
	    	if (sOutput.size() > 0) {
	    		for (int i = 0; i < sOutput.size(); i++) {
	    			JSONObject obj = new JSONObject();
	    			String sCallName = sOutput.get(i).get(0);
	    			String sStatus = sOutput.get(i).get(1);
	    			String sMandate = sOutput.get(i).get(2);
	    			String sResponse = sOutput.get(i).get(3);
	    			String SdateTime = sOutput.get(i).get(4);
	    			String sErrorDes = sOutput.get(i).get(5);
	    			obj.put("Call Name", sCallName);
	    			obj.put("Status", sStatus);
	    			obj.put("Response", sResponse);
	    			obj.put("Request Date", SdateTime);
	    			obj.put("Error Desc", sErrorDes);
	    			jsonArray.add(obj);
	    			logInfo("loadFulfillmentdata", jsonArray + "");
	    		}	
	    		formObject.addDataToGrid(GRID_FULFILLMENT, jsonArray);
	    	}
	    }
		
	    public String getReqDate() {
			Date d = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"dd/MM/yyyy HH:mm:ss");
			String sDate = dateFormat.format(d);
			log.info("sDate---" + sDate);
			return sDate;
		}
	    	
	    	
	    public int insertDataIntoDB(String tablename, String columnname, String values) {
	    	logInfo("insertDataIntoDB", "insertQuery NEW 18/01/2022 : values "
	    			+ values);
	    	if (values.contains("" + (char) 25)) {
	    		values = values.replaceAll("'" + (char) 25 + "'", "!~!");
	    		values = values.replaceAll("'" + (char) 25, "!#!");
	    		values = values.replaceAll((char) 25 + "'", "!@!");
	    		values = values.replaceAll("','", "!~!");
	    		values = values.replaceAll("\\('", "!b!");
	    		values = values.replaceAll("'\\)", "!c!");
	    		// System.out.println(values);
	    		values = values.replaceAll("'", "''");
	    		// values = values.replaceAll(""+(char)25 , "'"+(char)25+"'");
	    		values = values.replaceAll("!~!", "','");
	    		values = values.replaceAll("!#!", "',");
	    		values = values.replaceAll("!@!", ",'");
	    		values = values.replaceAll("!b!", "\\('");
	    		values = values.replaceAll("!c!", "'\\)");
	    		values = values.replaceAll(Character.toString((char) 25), ",");
	    		if ("'".equalsIgnoreCase(values.substring(1, 2)))
	    			values = values.substring(1, values.length());
	    		if ("'".equalsIgnoreCase(values.substring(values.length() - 1,
	    				values.length())))
	    			values = values.substring(0, values.length() - 1);
	    	} else {
	    		values = values.replaceAll("','", "!~!");
	    		values = values.replaceAll("',", "!#!");
	    		values = values.replaceAll(",'", "!@!");
	    		values = values.replaceAll("','", "!~!");
	    		values = values.replaceAll("\\('", "!b!");
	    		values = values.replaceAll("'\\)", "!c!");
	    		// System.out.println(values);
	    		values = values.replaceAll("'", "''");
	    		// values = values.replaceAll(""+(char)25 , "'"+(char)25+"'");
	    		values = values.replaceAll("!~!", "','");
	    		values = values.replaceAll("!#!", "',");
	    		values = values.replaceAll("!@!", ",'");
	    		values = values.replaceAll("!b!", "\\('");
	    		values = values.replaceAll("!c!", "'\\)");
	    		values = values.replaceAll(Character.toString((char) 25), ",");
	    		if ("'".equalsIgnoreCase(values.substring(1, 2)))
	    			values = values.substring(1, values.length());
	    		if ("'".equalsIgnoreCase(values.substring(values.length() - 1,
	    				values.length())))
	    			values = values.substring(0, values.length() - 1);
	    	}
	    	String insertQuery = "INSERT INTO " + tablename + " (" + columnname
	    			+ ") VALUES (" + values + ")";

	    	logInfo("insertDataIntoDB", "insertQuery : " + insertQuery);
	    	int result = formObject.saveDataInDB(insertQuery);
	    	return result;
	    }
	    
	    public String convertToNClob(String data) {
	    	if(data.length() > 4000) {
				int itr = data.length()/4000;
				int mod = data.length()%4000;
				if(mod > 0){
					++itr;
				}
				StringBuilder request = new StringBuilder();
				try {
					for (int i = 0; i < itr; i++) {
						if(i == 0){
							request.append("TO_NCLOB('"+ data.substring(0,4000) + "')");
						}
						else if(i < itr - 1) {
							request.append(" || TO_NCLOB('"+data.substring((4000*i),4000*(i+1))+ "')");
						}
						else {
							request.append(" || TO_NCLOB('"+data.substring((4000*i),data.length())+ "')");
						}
					}
				} catch(Exception e){
					logError("convertToNClob", e);
				}
				data = request.toString();
			} else {
				data = "TO_NCLOB('"+ data +"')"; 
			}
	    	
	    	return data;
	    }

		public static final String generateGUID(Object o) {
			StringBuffer tmpBuffer = new StringBuffer(16);
			if (hexServerIP == null) {
				java.net.InetAddress localInetAddress = null;
				try {
					// get the inet address
					localInetAddress = java.net.InetAddress.getLocalHost();

				} catch (java.net.UnknownHostException uhe) {

					log.info("AccountUtil: Could not get the local IP address using InetAddress.getLocalHost()!");
					uhe.printStackTrace();
					return null;
				}
				byte serverIP[] = localInetAddress.getAddress();
				hexServerIP = hexFormat(getInt(serverIP), 8);
			}

			String hashcode = hexFormat(System.identityHashCode(o), 8);
			tmpBuffer.append(hexServerIP);
			tmpBuffer.append(hashcode);
			long timeNow = System.currentTimeMillis();
			int timeLow = (int) timeNow & 0xFFFFFFFF;
			int node = seeder.nextInt();
			StringBuffer guid = new StringBuffer(32);
			guid.append(hexFormat(timeLow, 8));
			guid.append(tmpBuffer.toString());
			guid.append(hexFormat(node, 8));
			return guid.toString();

		}

		private static int getInt(byte bytes[]) {
			int i = 0;
			int j = 24;
			for (int k = 0; j >= 0; k++) {
				int l = bytes[k] & 0xff;
				i += l << j;
				j -= 8;
			}
			return i;
		}

		private static String hexFormat(int i, int j) {
			String s = Integer.toHexString(i);
			return padHex(s, j) + s;
		}

		private static String padHex(String s, int i) {
			StringBuffer tmpBuffer = new StringBuffer();
			if (s.length() < i) {
				for (int j = 0; j < i - s.length(); j++) {
					tmpBuffer.append('0');
				}
			}

			return tmpBuffer.toString();

		}

		public String setDateValue1(String sValue) {
			log.info("in setDateValue1----" + sValue);
			try {
				if (!sValue.equalsIgnoreCase("")) {
					String[] temp = sValue.split(" ");
					temp = temp[0].split("/");
					sValue = temp[0] + "/" + temp[1] + "/" + temp[2];
					return sValue;
				}
				return "";
			} catch (Exception e) {
				logError("", e);
			}
			return "";
		}
		
		public String getPrimaryCustomerID() {
			logInfo("getPrimaryCustomerID", "INSIDE");
			String sCustomerID = "";
			if (!formObject.getValue(NO_OF_CUST_SEARCHED).toString().isEmpty()) {
				int iSearchedCustomer = Integer.parseInt(formObject.getValue(
						NO_OF_CUST_SEARCHED).toString());
				String sAccRelation = "";
				String sNo = "";
				logInfo("getPrimaryCustomerID", "iSearchedCustomer: "
						+ iSearchedCustomer);
				if (iSearchedCustomer == 1) {
					sNo = "1";
				} else {
					for (int i = 0; i < iSearchedCustomer; i++) {
						sAccRelation = formObject.getTableCellValue(ACC_RELATION,
								i, 9);
						if (sAccRelation.equalsIgnoreCase("SOW")) {
							sNo = "1";
							break;
						} else if (sAccRelation.equalsIgnoreCase("JAF")
								|| sAccRelation.equalsIgnoreCase("JOF")
								|| sAccRelation.equalsIgnoreCase("Minor")) {
							sNo = formObject.getTableCellValue(ACC_RELATION, i, 0);
							break;
						}
					}
				}
				List<List<String>> sOutput = formObject
						.getDataFromDB("SELECT CUST_ID from USR_0_CUST_TXN WHERE "+ "WI_NAME ='" + sWorkitemName + "' AND CUST_SNO='"+ sNo + "'");
				logInfo("getPrimaryCustomerID", "sOutput: " + sOutput);

				if (sOutput != null && sOutput.size() > 0) {
					sCustomerID = sOutput.get(0).get(0);
				}
				if (sCustomerID.isEmpty()) {
					sCustomerID = "#" + "CUST_" + sNo + "#";
				}
			}
			logInfo("getPrimaryCustomerID", "sCustomerID: " + sCustomerID);
			return sCustomerID;
		}
		
		public String getPrimaryCustomerSNO() {
			int iRows = getGridCount(ACC_RELATION);
			String sAccRelation = "";
			String sNo = "";
			if (iRows == 0) {
				sNo = "0";
			} else {
				for (int i = 0; i < iRows; i++) {
					sAccRelation = formObject.getTableCellValue(ACC_RELATION, i, 9);
					if ("".equalsIgnoreCase(sAccRelation)) {
						sAccRelation = formObject.getTableCellValue(ACC_RELATION,
								i, 8);
					}
					if (sAccRelation.equalsIgnoreCase("SOW")) {
						sNo = "1";
						break;
					} else if (sAccRelation.equalsIgnoreCase("JAF")
							|| sAccRelation.equalsIgnoreCase("JOF")
							|| sAccRelation.equalsIgnoreCase("Minor")) {
						sNo = formObject.getTableCellValue(ACC_RELATION, i, 0);
						break;
					}
				}
			}
			return sNo;
		}
		
		public int getGridCount(String controlName) {
			int count = 0;
			try {
				JSONArray jsonArray = formObject.getDataFromGrid(controlName);
				count = jsonArray.size();
			} catch (Exception e) {
				logError("Exception in getListCount: " + e, e);
			}
			return count;
		}
		
	    public void logInfo(String functionName, String message) {
			log.info("[" + sActivityName + "][" + sWorkitemName + "][" + functionName
					+ "] : " + message);
		}

		public void logError(String functionName, Exception e) {
			log.error("[" + sActivityName + "][" + sWorkitemName + "] Exception in ["
					+ functionName + "] : ", e);
		}
		
		public String getSignature(int sNo) {
	    	String sQuery = "";
	    	String signature ="";
	    	try {
	    		if (!formObject.getTableCellValue(ACC_RELATION, sNo - 1, 9)
	    				.toString().equalsIgnoreCase("Minor")) {
	    			sQuery = "select SIGN from USR_0_DOC_DETAILS B WHERE WI_NAME='" + sWorkitemName + "' AND UPPER(DOC_NAME)='SIGNATURE' AND COMMENTS='"+ sNo+ "' "
	    					+ " AND ROWID=(SELECT MAX(ROWID) FROM USR_0_DOC_DETAILS WHERE COMMENTS=B.COMMENTS AND WI_NAME=B.WI_NAME  AND UPPER(DOC_NAME)='SIGNATURE')";
	    			logInfo("getSignature", " sInputXML: " + sQuery);
	    		} else {
	    			sQuery = "select VALUE from usr_0_defaultvalue_fcr B WHERE UPPER(NAME)='MINOR_SIGN'";
	    			logInfo("getSignature", " sInputXML: " + sQuery);
	    		}
	    		List<List<String>> sOutput = formObject.getDataFromDB(sQuery);
	    		if(sOutput != null && sOutput.size() > 0){
	    			signature=sOutput.get(0).get(0);
	    		}
	    	} catch (Exception e) {
	    		logError("getSignature", e);
	    	}
	    	return signature;
	    }
	    	
}