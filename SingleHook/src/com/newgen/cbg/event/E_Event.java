package com.newgen.cbg.event;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.newgen.cbg.core.CoreEvent;
import com.newgen.cbg.core.IEventHandler;
import com.newgen.cbg.logger.DSCOPLogMe;
import com.newgen.cbg.request.ApplicationAttributes;
import com.newgen.cbg.request.AttributeDetails;
import com.newgen.cbg.request.Attributes;
import com.newgen.cbg.response.CBGSingleHookResponse;
import com.newgen.cbg.utils.APCallCreateXML;
import com.newgen.cbg.utils.DSCOPConfigurations;
import com.newgen.cbg.utils.DSCOPUtils;
import com.newgen.cbg.utils.EnquiryParser;
import com.newgen.cbg.utils.GenerateSecurityToken;
import com.newgen.cbg.utils.XMLParser;
import com.newgen.omni.jts.srvr.NGDBConnection;

public class E_Event implements IEventHandler {

	@Override
	public CBGSingleHookResponse dispatchEvent(CoreEvent paramCoreEvent) throws Exception {
		CBGSingleHookResponse responseObj = new CBGSingleHookResponse();
		String WI_NAME = paramCoreEvent.getWI_NAME();
		String stageId = paramCoreEvent.getStageId();
		String sysRefNo = paramCoreEvent.getSysRefNo();
		String sourcingCenter = paramCoreEvent.getSourcingCenter();
		String sourcingChannel = paramCoreEvent.getSourcingChannel();
		String applicationName = paramCoreEvent.getApplicationName();
		String applicationVersion=paramCoreEvent.getApplicationVersion();
		String language = paramCoreEvent.getLanguage();
		String applicationJourney=paramCoreEvent.getApplicationJourney();
		HashMap<String, String> defaultAttributeMap = DSCOPUtils.getInstance().requestToDefaultValueMap();
		int FT_MIN_AMOUNT = Integer.parseInt(defaultAttributeMap.get("FT_MIN_AMOUNT"));
		int FT_MAX_AMOUNT = Integer.parseInt(defaultAttributeMap.get("FT_MAX_AMOUNT"));
		String MIN_LOAN_AMT = defaultAttributeMap.get("MIN_LOAN_AMT");
		String MAX_LOAN_AMT = defaultAttributeMap.get("MAX_LOAN_AMT");
		ApplicationAttributes[] aaa = new ApplicationAttributes[1];
		AttributeDetails[] ada = new AttributeDetails[1];
		String outputXML = "";
		String mobileNumber = "";
		String barcodeNum = "";
		boolean isDeviceDup = false;
		String deviceID = paramCoreEvent.getDeviceID();

		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SourcingChannel :"+sourcingChannel+", StaeID :"+stageId+", WI_NAME :"+WI_NAME);

		if("ADCBCOP".equals(sourcingChannel)) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Going in COP class");
//			return new COP_EEvent().dispatchEvent(paramCoreEvent);
		}
		else if("ITQAN".equalsIgnoreCase(sourcingChannel)){
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Going in ITQAN_OPTY EEvent class");
//			return new OPTY_EEvent().dispatchEvent(paramCoreEvent);
		}
		else if("ADCBCCSSO".equalsIgnoreCase(sourcingChannel) && "MIB".equalsIgnoreCase(applicationJourney)){
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Going in ADCB CCSSO EEvent MIB class");
	//		return new CCMIB_EEvent().dispatchEvent(paramCoreEvent);
		}
		else if("ADCBCCSSO".equalsIgnoreCase(sourcingChannel)){
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Going in ADCB CCSSO EEvent TALABAT class");
//			return new CCSSO_EEvent().dispatchEvent(paramCoreEvent);
		}
		else if("CBGAPP".equalsIgnoreCase(sourcingChannel) && "RWA".equals(applicationJourney)){ 
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Going in ADCB CBGAPP HYK EEvent class");
//			return new HYKUpgrade_EEvent().dispatchEvent(paramCoreEvent);
		}
		else if("CBGAPP".equalsIgnoreCase(sourcingChannel) && "PRODUCTSEGMENT".equals(applicationJourney)){
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Going in Hayyak_EEvent class");
//			return new Hayyak_EEvent().dispatchEvent(paramCoreEvent);
		}
		else if("AGWWHAPP".equalsIgnoreCase(sourcingChannel)){
			HashMap<String, String> attributeMap = DSCOPUtils.getInstance().requestToAttributeMap(paramCoreEvent.getRequest().getApplicationAttributes());
			try{
				Connection con = NGDBConnection.getDBConnection(DSCOPConfigurations.getInstance().CabinetName, "APProcedure");
				CallableStatement cstmt = null;
				cstmt = con.prepareCall("{call adcbbpmdedupe.InqDedupeCheck(?,?,?,?,?,?,?,?,?,?,?,?,?)}");

				cstmt.setString(1, sourcingChannel);
				cstmt.setString(2, sysRefNo);
				if(!"".equals(WI_NAME)){
					cstmt.setString(3, "W");
					cstmt.setString(4, WI_NAME);
				}
				else{
					cstmt.setString(3, "M");
					cstmt.setString(4, attributeMap.get("M"));
				}
				cstmt.registerOutParameter(5, Types.CHAR);
				cstmt.registerOutParameter(6, Types.CHAR);
				cstmt.registerOutParameter(7, Types.CHAR);
				cstmt.registerOutParameter(8, Types.CHAR);
				cstmt.registerOutParameter(9, Types.CHAR);
				cstmt.registerOutParameter(10, Types.CHAR);
				cstmt.registerOutParameter(11, Types.CHAR);
				cstmt.registerOutParameter(12, Types.CHAR);
				cstmt.registerOutParameter(13, Types.CHAR);
				cstmt.execute();

				WI_NAME = cstmt.getString(12);
				String result = cstmt.getString(11);
				NGDBConnection.closeDBConnection(con, "APProcedure");
				con = null;

				outputXML = APCallCreateXML.APSelect("SELECT '"+result+"' AS ACCOUNT_STATUS, TO_CHAR(SYSDATE ,'dd/mm/yyyy hh24:mi:ss') AS INITTIONDATETIME FROM DUAL");
				XMLParser xp = new XMLParser(outputXML);
				String record = xp.getValueOf("Records");
				record = record.replace("&", "&amp;");
				HashMap<String,String> fieldVal = EnquiryParser.parseXML("<?xml version=\"1.0\"?>"+record);
				Attributes[] ata = new Attributes[fieldVal.size()]; 
				Set<Map.Entry<String,String>> fieldValSet = fieldVal.entrySet();
				int i = 0;
				//Attributes array 
				for(Entry<String,String> entry:fieldValSet){
					Attributes a = new Attributes();
					a.setAttributeKey((String) entry.getKey());
					String temp = (((String) entry.getValue()));
					if(temp != null){
						temp = temp.replace("&amp;", "&");
					}
					a.setAttributeValue(temp);
					ata[i] = a;
					i++;
				}

				//Attribute Details array
				AttributeDetails ad = new AttributeDetails();
				ad.setAttributes(ata);
				ada[0] = ad;

				//Application Attributes
				ApplicationAttributes aa = new ApplicationAttributes();
				aa.setAttributeDetails(ada);
				aaa[0] = aa;
				responseObj.setApplicationAttributes(aaa);
				responseObj.setStage(fieldVal.get("NEXT_STAGE_ID"));
			} catch (Exception e) {
				responseObj.setStatusCode("1");
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, "E_Event :"+e);
			}
			responseObj.setWI_NAME(WI_NAME);
			responseObj.setLanguage("EN");
			responseObj.setStatusCode("0");
			responseObj.setApplicationName(applicationName);
			responseObj.setSYSREFNO(sysRefNo);
			return responseObj;	
		}
		else if("IHUB".equals(sourcingChannel)) {
			HashMap<String, String> attributeMap = DSCOPUtils.getInstance().requestToAttributeMap(paramCoreEvent.getRequest().getApplicationAttributes());
			String eidaNumber = attributeMap.get("EIDA_NUMBER");
			String eidaNationality = attributeMap.get("EIDA_NATIONALITY");
			String eidaExpiry = attributeMap.get("EIDA_EXPIRY_DATE");
			String eidaDOB = attributeMap.get("EIDA_DOB_DATE");
			String cardProductType = attributeMap.get("CARD_PRODUCT_TYPE");
			String whereClause;
			if(eidaNumber != null && eidaNationality  != null && eidaExpiry != null && eidaDOB != null && cardProductType != null){
				whereClause = "WHERE EIDA_NUMBER='"+eidaNumber+"' AND EIDA_NATIONALITY='"+eidaNationality+"' AND EIDA_EXPIRY_DATE=to_date('"+eidaExpiry+"','dd/MM/yyyy') AND EIDA_DOB_DATE=to_date('"+eidaDOB+"','dd/MM/yyyy') AND CARD_PRODUCT_TYPE='"+cardProductType+"'";
			}
			else if(eidaNumber != null && eidaExpiry != null && eidaDOB != null && cardProductType != null){
				whereClause = "WHERE EIDA_NUMBER='"+eidaNumber+"' AND EIDA_EXPIRY_DATE=to_date('"+eidaExpiry+"','dd/MM/yyyy') AND EIDA_DOB_DATE=to_date('"+eidaDOB+"','dd/MM/yyyy') AND CARD_PRODUCT_TYPE='"+cardProductType+"'";
			}
			else if(eidaNumber != null && eidaNationality != null && eidaExpiry != null && cardProductType != null){
				whereClause = "WHERE EIDA_NUMBER='"+eidaNumber+"' AND EIDA_NATIONALITY='"+eidaNationality+"' AND EIDA_EXPIRY_DATE=to_date('"+eidaExpiry+"','dd/MM/yyyy') AND CARD_PRODUCT_TYPE='"+cardProductType+"'";
			}
			else if(eidaNumber != null && eidaNationality != null && eidaDOB != null && cardProductType != null){
				whereClause = "WHERE EIDA_NUMBER='"+eidaNumber+"' AND EIDA_NATIONALITY='"+eidaNationality+"' AND EIDA_DOB_DATE=to_date('"+eidaDOB+"','dd/MM/yyyy') AND CARD_PRODUCT_TYPE='"+cardProductType+"'";
			}
			else if(eidaNumber != null && eidaNationality != null && eidaExpiry != null){
				whereClause = "WHERE EIDA_NUMBER='"+eidaNumber+"' AND EIDA_NATIONALITY='"+eidaNationality+"' AND EIDA_EXPIRY_DATE=to_date('"+eidaExpiry+"','dd/MM/yyyy')";
			}
			else if(eidaNumber != null && eidaExpiry != null && eidaDOB != null){
				whereClause = "WHERE EIDA_NUMBER='"+eidaNumber+"' AND EIDA_EXPIRY_DATE=to_date('"+eidaExpiry+"','dd/MM/yyyy') AND EIDA_DOB_DATE=to_date('"+eidaDOB+"','dd/MM/yyyy')";
			}
			else if(eidaNumber != null && eidaNationality != null && eidaDOB != null){
				whereClause = "WHERE EIDA_NUMBER='"+eidaNumber+"' AND EIDA_NATIONALITY='"+eidaNationality+"' AND EIDA_DOB_DATE=to_date('"+eidaDOB+"','dd/MM/yyyy')";
			}
			else if(eidaNumber != null && eidaNationality != null){
				whereClause = "WHERE EIDA_NUMBER='"+eidaNumber+"' AND EIDA_NATIONALITY='"+eidaNationality+"'";
			}
			else if(eidaNumber != null && eidaExpiry != null){
				whereClause = "WHERE EIDA_NUMBER='"+eidaNumber+"' AND EIDA_EXPIRY_DATE=to_date('"+eidaExpiry+"','dd/MM/yyyy')";
			}
			else if(eidaNumber != null && eidaDOB != null){
				whereClause = "WHERE EIDA_NUMBER='"+eidaNumber+"' AND EIDA_DOB_DATE=to_date('"+eidaDOB+"','dd/MM/yyyy')";
			}
			else{
				whereClause = "WHERE EIDA_NUMBER='"+eidaNumber+"'";
			}
			outputXML  = APCallCreateXML.APSelect("SELECT * FROM (SELECT STAGE_ID,NEXT_STAGE_ID, DELINQUENT_STATUS, VISA_REQUIRED, IBAN_NUMBER_SALARY_ACCOUNT, "
					+ " UAE_NATIONAL, APPLICATION_STATUS, CUSTOMER_TYPE, PASSPORT_EXPIRY_FLAG, REFRENCE_NUMBER, CUSTOMER_MOBILE_NO, WI_NAME, EIDA_DOC_INDEX "
					+ " FROM EXT_IHUB "+whereClause+"  AND STAGE_ID NOT IN (-3,-4) ORDER BY INITIATION_DATETIME DESC) WHERE ROWNUM=1");
			try {

				XMLParser xp = new XMLParser(outputXML);
				if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) == 0){
					outputXML  = APCallCreateXML.APSelect("SELECT '0' AS STAGE_ID,'1' AS NEXT_STAGE_ID, 'N' AS DELINQUENT_STATUS, 'NEW' AS APPLICATION_STATUS, '' AS CUSTOMER_TYPE FROM DUAL");
				}
				xp = new XMLParser(outputXML);
				String record = xp.getValueOf("Records");
				record = record.replace("&", "&amp;");
				HashMap<String,String> fieldVal = EnquiryParser.parseXML("<?xml version=\"1.0\"?>"+record);
				Attributes[] ata = new Attributes[fieldVal.size()]; 
				Set<Map.Entry<String,String>> fieldValSet = fieldVal.entrySet();
				int i = 0;
				//Attributes array 
				for(Entry<String,String> entry:fieldValSet){
					Attributes a = new Attributes();
					a.setAttributeKey((String) entry.getKey());
					String temp = (((String) entry.getValue()));
					if(temp != null){
						temp = temp.replace("&amp;", "&");
					}
					a.setAttributeValue(temp);
					ata[i] = a;
					i++;
				}
				if(WI_NAME == null || ("".equals(WI_NAME))){
					WI_NAME = fieldVal.get("WI_NAME");
				}

				//Attribute Details array
				AttributeDetails ad = new AttributeDetails();
				ad.setAttributes(ata);
				ada[0] = ad;

				//Application Attributes
				ApplicationAttributes aa = new ApplicationAttributes();
				aa.setAttributeDetails(ada);
				aaa[0] = aa;
				responseObj.setApplicationAttributes(aaa);
				responseObj.setStage(fieldVal.get("NEXT_STAGE_ID"));
			} catch (Exception e) {
				responseObj.setStatusCode("1");
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, "E_Event CCDJ :"+e);
			}
			responseObj.setWI_NAME(WI_NAME);
			responseObj.setLanguage("EN");
			responseObj.setStatusCode("0");
			responseObj.setApplicationName(applicationName);
			responseObj.setSYSREFNO(sysRefNo);
			return responseObj;	
		}
		else if("HAPI".equals(sourcingChannel) && "0".equals(stageId)){
			HashMap<String, String> attributeMap = DSCOPUtils.getInstance().requestToAttributeMap(paramCoreEvent.getRequest().getApplicationAttributes());
			if(!attributeMap.containsKey("CUSTOMER_MOBILE_NO")){
				if(!GenerateSecurityToken.validatekey(WI_NAME, attributeMap.get("SECURITY_TOKEN"))){
					outputXML  = APCallCreateXML.APSelect("SELECT '"+sourcingChannel+"' AS SOURCING_CHANNEL, '"+sourcingCenter+"' AS SOURCING_CENTER FROM DUAL");
					XMLParser xp = new XMLParser(outputXML);
					String record = xp.getValueOf("Records");
					record = record.replace("&", "&amp;");
					HashMap<String,String> fieldVal = EnquiryParser.parseXML("<?xml version=\"1.0\"?>"+record);
					Attributes[] ata = new Attributes[fieldVal.size()]; 
					Set<Map.Entry<String,String>> fieldValSet = fieldVal.entrySet();
					int i = 0;
					//Attributes array 
					for(Entry<String,String> entry:fieldValSet){
						Attributes a = new Attributes();
						a.setAttributeKey((String) entry.getKey());
						String temp = (((String) entry.getValue()));
						if(temp != null){
							temp = temp.replace("&amp;", "&");
						}
						a.setAttributeValue(temp);
						ata[i] = a;
						i++;
					}
					if(WI_NAME == null || ("".equals(WI_NAME))){
						WI_NAME = fieldVal.get("WI_NAME");
					}

					//Attribute Details array
					AttributeDetails ad = new AttributeDetails();
					ad.setAttributes(ata);
					ada[0] = ad;

					//Application Attributes
					ApplicationAttributes aa = new ApplicationAttributes();
					aa.setAttributeDetails(ada);
					aaa[0] = aa;
					responseObj.setApplicationAttributes(aaa);
					
					responseObj.setWI_NAME(WI_NAME);
					responseObj.setLanguage(language);
					responseObj.setStatusCode("1");
					responseObj.setStatusMessage("SECURITY TOKEN NOT VALID");
					responseObj.setStage(stageId);
					responseObj.setApplicationName(applicationName);
					responseObj.setSYSREFNO(sysRefNo);
					return responseObj;	
				}
			}
		}

		if(WI_NAME != null && !(WI_NAME.equals(""))){
			isDeviceDup = DSCOPUtils.getInstance().isSameDeviceVaild30Days(deviceID,WI_NAME);
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Same Device exists within 30 days : " + isDeviceDup);
			if(isDeviceDup){
				responseObj.setWI_NAME(WI_NAME);
				responseObj.setLanguage(language);
				responseObj.setStatusCode("24");
				responseObj.setStatusMessage("DEVICE ALREADY REGISTERED");
				responseObj.setStage(stageId);
				responseObj.setApplicationName(applicationName);
				responseObj.setSYSREFNO(sysRefNo);
				return responseObj;	
			}
			
			String FT_IS_ELIGIBLE =defaultAttributeMap.get("FT_IS_ELIGIBLE");//"Y";
			int FT_AMOUNT = 0;
			outputXML = APCallCreateXML.APSelect("SELECT FT_AMOUNT FROM USR_0_CBG_FT WHERE WI_NAME = N'" + WI_NAME + "' ORDER BY  ft_requesteddatetime DESC");
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if(mainCode == 0){
				if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
					FT_AMOUNT = Integer.parseInt(xp.getValueOf("FT_AMOUNT"));
				}
			}
			outputXML = APCallCreateXML.APSelect("SELECT DECODE(PG_STATUS_CODE,'200','Y','N') AS FT_STATUS_1, DECODE(FT_STATUS_CODE,null,'N',FT_STATUS_CODE) AS FT_STATUS_2 FROM USR_0_CBG_FT WHERE WI_NAME = N'" + WI_NAME + "' ORDER BY  ft_requesteddatetime DESC");
			xp = new XMLParser(outputXML);
			mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if(mainCode == 0){
				if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
					String FT_STATUS_1 = xp.getValueOf("FT_STATUS_1");
					String FT_STATUS_2 = xp.getValueOf("FT_STATUS_2");
					if(!FT_STATUS_1.equalsIgnoreCase("N")){
						FT_IS_ELIGIBLE = "N";
					}
					if(FT_STATUS_1.equalsIgnoreCase("N") || FT_STATUS_2.equalsIgnoreCase("N")){
						FT_AMOUNT = 0;
					}
				}
			}

			String PIL_TENOR_12 = "";
			String PIL_TENOR_24 = "";
			String PIL_TENOR_36 = "";
			String PIL_TENOR_48 = "";
			outputXML = APCallCreateXML.APSelect("SELECT "
					+ "(SELECT  (TENOR || '|' || REQUESTED_AMOUNT || '|' || ELIGIBLE_AMOUNT || '|' ||ROI) FROM USR_0_CBG_PILCREDIT_MATRIX  WHERE TENOR = '12' AND WI_NAME= N'" + WI_NAME + "' AND ROWNUM=1)  AS PIL_TENOR_12, "
					+ "(SELECT  (TENOR || '|' || REQUESTED_AMOUNT || '|' || ELIGIBLE_AMOUNT || '|' ||ROI) FROM USR_0_CBG_PILCREDIT_MATRIX  WHERE TENOR = '24' AND WI_NAME= N'" + WI_NAME + "' AND ROWNUM=1) AS PIL_TENOR_24, "
					+ "(SELECT  (TENOR || '|' || REQUESTED_AMOUNT || '|' || ELIGIBLE_AMOUNT || '|' ||ROI) FROM USR_0_CBG_PILCREDIT_MATRIX  WHERE TENOR = '36' AND WI_NAME= N'" + WI_NAME + "' AND ROWNUM=1) AS PIL_TENOR_36, "
					+ "(SELECT  (TENOR || '|' || REQUESTED_AMOUNT || '|' || ELIGIBLE_AMOUNT || '|' ||ROI) FROM USR_0_CBG_PILCREDIT_MATRIX  WHERE TENOR = '48' AND WI_NAME= N'" + WI_NAME + "' AND ROWNUM=1) AS PIL_TENOR_48 "
					+ "FROM DUAL");
			xp = new XMLParser(outputXML);
			mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if(mainCode == 0){
				PIL_TENOR_12 = xp.getValueOf("PIL_TENOR_12");										
				PIL_TENOR_24 = xp.getValueOf("PIL_TENOR_24");
				PIL_TENOR_36 = xp.getValueOf("PIL_TENOR_36");
				PIL_TENOR_48 = xp.getValueOf("PIL_TENOR_48");
			}
			outputXML = APCallCreateXML.APSelect("SELECT WI_NAME, CASE WHEN LENGTH(SUBSTR(CUSTOMER_NICK_NAME,3,  INSTR(CUSTOMER_NICK_NAME,' ',-1)-1))<14 THEN SUBSTR(CUSTOMER_NICK_NAME,0, "
					+ " INSTR(CUSTOMER_NICK_NAME,' ',-1)-1) ELSE SUBSTR(CUSTOMER_NICK_NAME,0,14) END AS CUSTOMER_NICK_NAME, CUSTOMER_EMAIL, CUSTOMER_MOBILE_NO, CUSTOMER_PREFIX, EXPIRY_DATE, "
					+ " PASSPORT_FIRST_NAME, PASSPORT_LAST_NAME, NVL(PASSPORT_FULL_NAME,PASSPORT_FIRST_NAME || ' ' || PASSPORT_LAST_NAME) AS PASSPORT_FULL_NAME, CUSTOMER_SHORT_NAME, PASSPORT_GENDER, "
					+ "PASSPORT_NUMBER, PASSPORT_NATIONALITY, DECODE(PASSPORT_NATIONALITY,'AE','Y','N') AS UAE_NATIONAL, PASSPORT_DOB_DATE, PASSPORT_EXPIRY_DATE, EIDA_VALIDATION, EIDA_NUMBER, EIDA_DOB_DATE, EIDA_FULL_NAME,"
					+ " EIDA_EXPIRY_DATE, EIDA_NATIONALITY, EIDA_GENDER, EIDA_CARD_NUMBER, NVL(DEF_CRDT_LMT,'0')||'.00' AS DEF_CRDT_LMT,  CBG_CC_CALL_STATUS(WI_NAME, CC_ELIGIBILITY_FLAG) AS CC_ELIGIBILITY_FLAG , EMPLOYMENT_TYPE, CUSTOMER_EMPLOYER_NAME, "
					+ "CUSTOMER_MONTHLY_INCOME, NAME_OF_BUSINESS, NATURE_OF_BUSINESS_CODE, CUSTOMER_ANNUAL_INCOME, BUSI_RISK_FLG, CARD_EMBOSS_NAME, DELIVERY_OPTION, "
					+ "DELIVERY_PREF, DELI_MAP_USED_FLG, DELI_ADD_POBOX_VILA_FLATNO, DELI_ADD_FLOOR_BUILDINGNAME, DELI_ADD_STREET_NAME, DELI_ADD_CITY, "
					+ "DELI_ADD_STATE_EMIRATE, DELI_ADD_COUNTRY, DELI_BRN_NAME, DELI_BRN_WORKING_DAYS, DELI_BRN_TIMING_END, DELI_BRN_TIMING_ST, CUSTOMER_ID,"
					+ "ACCOUNT_NUMBER, PRODUCT_CODE, PRODUCT_DESC, SWIFT_CODE, IBAN, CARD_EMBOSS_NAME, CC_APPLIED_FLG, CC_REQ_LIMIT, CC_UPG_FLG, CC_PROD_CODE, "
					+ "CC_PROD_DESC, CARD_BIN, LMT_UPG_IBAN_ACC, CC_CARD_TYPE, NPS_FLG,NPS_TEXT, '' AS CREDIT_CARD_NUMBER, CC_STATUS, CREDIT_CARD_EXPIRY_DATE, DECODE(DELIVERY_STATUS,'DELIVERED','Y','Success','Y',DELIVERY_STATUS) AS DELIVERY_STATUS,"
					+ "CC_LIMT_UPG_APPR_FLG, CC_APPROVED_LIMIT, BANKING_TYPE, MURABAHA_ACCEPTANCE,  NVL(LIMIT_AMOUNT,0)||'.00' AS LIMIT_AMOUNT, CC_INVESTMENT_FLG, CC_MURHABA_FLG, CC_ON_SALE_AGT_FLG,"
					+ " DECODE(STAGE_ID,10, 9,-1,-1, -2,-2,-5,-5,-6,-1,-9,-1,-8,-1, NEXT_STAGE_ID) AS STAGE_ID, LEAD_REF_NO, PREFERRED_LANGUAGE, HOME_BRANCH, IS_APP_TO_DISPLAY, CUSTOMER_FULL_NAME, PASSPORT_TYPE, "
					+ "RESI_ADD_POBOX_VILA_FLATNO, RESI_ADD_FLOOR_BUILDINGNAME, RESI_ADD_STREET_NAME, RESI_ADD_CITY, RESI_ADD_STATE_EMIRATE,"
					+ "RESI_ADD_COUNTRY, RESI_ADD_CONFIRMATION_STATUS, ACCOUNT_TYPE, EIDA_INFO, ACCOUNT_TITLE, DIGI_CODE, RESI_MAP_USED_FLG, OFF_MAP_USED_FLG,"
					+ "EMPLYR_ADD_POBOX_VILA_FLATNO, EMPLYR_ADD_FLOOR_BUILDINGNAME, EMPLYR_ADD_STREET_NAME, EMPLYR_ADD_CITY, EMPLYR_ADD_STATE_EMIRATE, EMPLYR_ADD_COUNTRY, NI_ONBOARD_FLAG, "
					+ FT_MIN_AMOUNT + " AS FT_MIN_AMOUNT," + FT_MAX_AMOUNT + " AS FT_MAX_AMOUNT,'" + FT_IS_ELIGIBLE  + "' AS FT_IS_ELIGIBLE," + FT_AMOUNT + " AS FT_AMOUNT, PIL_REQUESTED_AMOUNT, "
					+ "CUSTOMER_PROFESSION_NAME, DATE_OF_JOINING, SALARY_DATE, PIL_ELIGIBILITY_FLAG, CBG_CC_CALL_STATUS(WI_NAME, CC_ELIGIBILITY_FLAG) AS CC_ELIGIBILITY_FLAG, '"
					+ PIL_TENOR_12 + "' AS PIL_TENOR_12,'" + PIL_TENOR_24 + "' AS PIL_TENOR_24,'" + PIL_TENOR_36  + "' AS PIL_TENOR_36,'" + PIL_TENOR_48 + "' AS PIL_TENOR_48, "
					+ "PIL_SELECTED_AMOUNT, PIL_SELECTED_ROI,PIL_NO_OF_TENORS, PIL_SELECTED_TENOR, PIL_INSTALLMENT_START_DATE, PIL_PROCESSING_FEE, PIL_INSURANCE_FEE, PIL_INSTALLMENT_AMOUNT,"
					+ " PIL_DISBURSEMENT_DATE, PIL_MATURITY_DATE, PIL_DISBURSED_AMOUNT, JOURNEY_TYPE, "+ MAX_LOAN_AMT + " AS MAX_LOAN_AMT,"+ MIN_LOAN_AMT + " AS MIN_LOAN_AMT, MURABAHA_COMMODITY,"
					+ " MURABAHA_COMMODITYUNIT, MURABAHA_COMMODITYVALUE, MURABAHA_PROFITRATE, CURRENT_ACCOUNT_ELIGIBILITY, ADDRESS_TYPE, UBANK_DELI_ELIGIBILITY, UBANK_BRANCH_DETAILS, STL_STATUS,"
					+ " PENDING_TASK_COUNT, CUSTOMER_SEGMENT, PRODUCT_CODE, PRODUCT_DESC, '"+sourcingChannel+"' AS SOURCING_CHANNEL, '"+sourcingCenter+"' AS SOURCING_CENTER, NPS_CAPTURED, "
					+ DSCOPUtils.getInstance().segmentJSON(WI_NAME) 
					//					+ ", " +DSCOPUtils.getInstance().pilProductsJSON(WI_NAME)
					//					+ ", " +DSCOPUtils.getInstance().bundleDetailsJSON(WI_NAME)
					+ " FROM EXT_CBG_CUST_ONBOARDING  WHERE WI_NAME = N'" + WI_NAME + "' AND (STAGE_ID!='-3' AND STAGE_ID!='-4')");
		} else {
			HashMap<String, String> attributeMap = DSCOPUtils.getInstance().requestToAttributeMap(paramCoreEvent.getRequest().getApplicationAttributes());
			mobileNumber = attributeMap.get("CUSTOMER_MOBILE_NO");
			if(mobileNumber != null && !(mobileNumber.equals(""))){
				outputXML = APCallCreateXML.APSelect("SELECT WI_NAME, CASE WHEN LENGTH(SUBSTR(CUSTOMER_NICK_NAME,3,  INSTR(CUSTOMER_NICK_NAME,' ',-1)-1))<14 THEN "
						+ " SUBSTR(CUSTOMER_NICK_NAME,0,  INSTR(CUSTOMER_NICK_NAME,' ',-1)-1) ELSE SUBSTR(CUSTOMER_NICK_NAME,0,14) END AS CUSTOMER_NICK_NAME, "
						+ " CUSTOMER_EMAIL, CUSTOMER_PREFIX, EXPIRY_DATE, PASSPORT_FIRST_NAME, PASSPORT_LAST_NAME, NVL(PASSPORT_FULL_NAME,PASSPORT_FIRST_NAME || ' ' || PASSPORT_LAST_NAME) AS PASSPORT_FULL_NAME, CUSTOMER_SHORT_NAME, PASSPORT_GENDER, "
						+ "PASSPORT_NUMBER, PASSPORT_NATIONALITY, DECODE(PASSPORT_NATIONALITY,'AE','Y','N') AS UAE_NATIONAL, PASSPORT_DOB_DATE, PASSPORT_EXPIRY_DATE, EIDA_VALIDATION, EIDA_NUMBER, EIDA_DOB_DATE, EIDA_FULL_NAME,"
						+ " EIDA_EXPIRY_DATE, EIDA_NATIONALITY, EIDA_GENDER, EIDA_CARD_NUMBER, NVL(DEF_CRDT_LMT,'0')||'.00' AS DEF_CRDT_LMT,  CBG_CC_CALL_STATUS(WI_NAME, CC_ELIGIBILITY_FLAG) AS CC_ELIGIBILITY_FLAG , EMPLOYMENT_TYPE, CUSTOMER_EMPLOYER_NAME, "
						+ "CUSTOMER_MONTHLY_INCOME, NAME_OF_BUSINESS, NATURE_OF_BUSINESS_CODE, CUSTOMER_ANNUAL_INCOME, BUSI_RISK_FLG, CARD_EMBOSS_NAME, DELIVERY_OPTION, "
						+ "DELIVERY_PREF, DELI_MAP_USED_FLG, DELI_ADD_POBOX_VILA_FLATNO, DELI_ADD_FLOOR_BUILDINGNAME, DELI_ADD_STREET_NAME, DELI_ADD_CITY, "
						+ "DELI_ADD_STATE_EMIRATE, DELI_ADD_COUNTRY, DELI_BRN_NAME, DELI_BRN_WORKING_DAYS, DELI_BRN_TIMING_END, DELI_BRN_TIMING_ST, CUSTOMER_ID,"
						+ "ACCOUNT_NUMBER, PRODUCT_CODE, PRODUCT_DESC, SWIFT_CODE, IBAN, CARD_EMBOSS_NAME, CC_APPLIED_FLG, CC_REQ_LIMIT, CC_UPG_FLG, CC_PROD_CODE, "
						+ "CC_PROD_DESC, CARD_BIN, LMT_UPG_IBAN_ACC, CC_CARD_TYPE, NPS_FLG,NPS_TEXT, '' AS CREDIT_CARD_NUMBER, CC_STATUS, CREDIT_CARD_EXPIRY_DATE, DECODE(DELIVERY_STATUS,'DELIVERED','Y','Success','Y',DELIVERY_STATUS) AS DELIVERY_STATUS,"
						+ "CC_LIMT_UPG_APPR_FLG, CC_APPROVED_LIMIT, BANKING_TYPE, MURABAHA_ACCEPTANCE,  NVL(LIMIT_AMOUNT,0)||'.00' AS LIMIT_AMOUNT, CC_INVESTMENT_FLG, CC_MURHABA_FLG, CC_ON_SALE_AGT_FLG,"
						+ "DECODE(STAGE_ID,10, 9,-1,-1, -2,-2,-5,-5,-6,-1,-9,-1,-7,-1,-8,-1, NEXT_STAGE_ID) AS STAGE_ID, LEAD_REF_NO, PREFERRED_LANGUAGE, HOME_BRANCH, IS_APP_TO_DISPLAY, CUSTOMER_FULL_NAME, PASSPORT_TYPE, "
						+ "RESI_ADD_POBOX_VILA_FLATNO, RESI_ADD_FLOOR_BUILDINGNAME, RESI_ADD_STREET_NAME, RESI_ADD_CITY, RESI_ADD_STATE_EMIRATE,"
						+ "RESI_ADD_COUNTRY, RESI_ADD_CONFIRMATION_STATUS, ACCOUNT_TYPE, EIDA_INFO, ACCOUNT_TITLE, DIGI_CODE, RESI_MAP_USED_FLG, OFF_MAP_USED_FLG,"
						+ "EMPLYR_ADD_POBOX_VILA_FLATNO, EMPLYR_ADD_POBOX_VILA_FLATNO, EMPLYR_ADD_FLOOR_BUILDINGNAME, EMPLYR_ADD_STREET_NAME, EMPLYR_ADD_CITY, "
						+ "EMPLYR_ADD_STATE_EMIRATE, EMPLYR_ADD_COUNTRY, NI_ONBOARD_FLAG, MURABAHA_COMMODITY, MURABAHA_COMMODITYUNIT, MURABAHA_COMMODITYVALUE,"
						+ " MURABAHA_PROFITRATE, ADDRESS_TYPE, UBANK_DELI_ELIGIBILITY, UBANK_BRANCH_DETAILS, STL_STATUS, PENDING_TASK_COUNT, CUSTOMER_SEGMENT, PRODUCT_CODE, PRODUCT_DESC, NPS_CAPTURED, "
						+ DSCOPUtils.getInstance().segmentJSON(WI_NAME) 
						//						+ ", " +DSCOPUtils.getInstance().pilProductsJSON(WI_NAME)
						//						+ ", " +DSCOPUtils.getInstance().bundleDetailsJSON(WI_NAME)
						+ " FROM EXT_CBG_CUST_ONBOARDING WHERE CUSTOMER_MOBILE_NO = N'"+ mobileNumber +"' AND (STAGE_ID!='-3' AND STAGE_ID!='-4')");
			} else {
				barcodeNum = attributeMap.get("BARCODE_NUM");
				outputXML = APCallCreateXML.APSelect("SELECT WI_NAME, CUSTOMER_MOBILE_NO, CUSTOMER_PREFIX, EXPIRY_DATE, PASSPORT_FIRST_NAME, PASSPORT_LAST_NAME, NVL(PASSPORT_FULL_NAME,PASSPORT_FIRST_NAME || ' ' || PASSPORT_LAST_NAME) AS PASSPORT_FULL_NAME, CUSTOMER_SHORT_NAME, PASSPORT_GENDER, "
						+ "PASSPORT_NUMBER, PASSPORT_NATIONALITY, DECODE(PASSPORT_NATIONALITY,'AE','Y','N') AS UAE_NATIONAL, PASSPORT_DOB_DATE, PASSPORT_EXPIRY_DATE, EIDA_VALIDATION, EIDA_NUMBER, EIDA_DOB_DATE, EIDA_FULL_NAME,"
						+ "EIDA_EXPIRY_DATE, EIDA_NATIONALITY, EIDA_GENDER, EIDA_CARD_NUMBER, NVL(DEF_CRDT_LMT,'0')||'.00' AS DEF_CRDT_LMT,  CBG_CC_CALL_STATUS(WI_NAME, CC_ELIGIBILITY_FLAG) AS CC_ELIGIBILITY_FLAG , EMPLOYMENT_TYPE, CUSTOMER_EMPLOYER_NAME, "
						+ "CUSTOMER_MONTHLY_INCOME, NAME_OF_BUSINESS, NATURE_OF_BUSINESS_CODE, CUSTOMER_ANNUAL_INCOME, BUSI_RISK_FLG, CARD_EMBOSS_NAME, DELIVERY_OPTION, "
						+ "DELIVERY_PREF, DELI_MAP_USED_FLG, DELI_ADD_POBOX_VILA_FLATNO, DELI_ADD_FLOOR_BUILDINGNAME, DELI_ADD_STREET_NAME, DELI_ADD_CITY, "
						+ "DELI_ADD_STATE_EMIRATE, DELI_ADD_COUNTRY, DELI_BRN_NAME, DELI_BRN_WORKING_DAYS, DELI_BRN_TIMING_END, DELI_BRN_TIMING_ST, CUSTOMER_ID,"
						+ "ACCOUNT_NUMBER, PRODUCT_CODE, PRODUCT_DESC, SWIFT_CODE, IBAN, CARD_EMBOSS_NAME, CC_APPLIED_FLG, CC_REQ_LIMIT, CC_UPG_FLG, CC_PROD_CODE, "
						+ "CC_PROD_DESC, CARD_BIN, LMT_UPG_IBAN_ACC, CC_CARD_TYPE, NPS_FLG,NPS_TEXT, '' AS CREDIT_CARD_NUMBER, CC_STATUS, CREDIT_CARD_EXPIRY_DATE, DECODE(DELIVERY_STATUS,'DELIVERED','Y','Success','Y',DELIVERY_STATUS) AS DELIVERY_STATUS,"
						+ "CC_LIMT_UPG_APPR_FLG, CC_APPROVED_LIMIT, BANKING_TYPE, MURABAHA_ACCEPTANCE,  NVL(LIMIT_AMOUNT,0)||'.00' AS LIMIT_AMOUNT, CC_INVESTMENT_FLG, CC_MURHABA_FLG, "
						+ "CC_ON_SALE_AGT_FLG, DECODE(STAGE_ID,10, 9,-1,-1, -2,-2,-5,-5,-6,-1,-9,-1,-7,-1,-8,-1, NEXT_STAGE_ID) AS STAGE_ID, LEAD_REF_NO, PREFERRED_LANGUAGE, HOME_BRANCH, IS_APP_TO_DISPLAY, CUSTOMER_FULL_NAME, PASSPORT_TYPE, "
						+ "CUSTOMER_NICK_NAME, CURRENT_ACCOUNT_ELIGIBILITY, NPS_CAPTURED "
						+ "FROM EXT_CBG_CUST_ONBOARDING WHERE BARCODE_NUM = N'"+ barcodeNum +"'");
			}
		}

		XMLParser xp = new XMLParser(outputXML);
		int totalRetrieved = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
		if(totalRetrieved==0){
			responseObj.setWI_NAME(WI_NAME);
			responseObj.setLanguage(language);
			responseObj.setStatusCode("1");
			responseObj.setStatusMessage("NO DATA FOUND");
			responseObj.setStage(stageId);
			responseObj.setApplicationName(applicationName);
			responseObj.setSYSREFNO(sysRefNo);
			return responseObj;	
		}

		String record = xp.getValueOf("Records");
		record = record.replace("&", "&amp;");
		HashMap<String,String> fieldVal = EnquiryParser.parseXML("<?xml version=\"1.0\"?>"+record);
		Attributes[] ata = new Attributes[fieldVal.size()]; 
		Set<Map.Entry<String,String>> fieldValSet = fieldVal.entrySet();
		int i = 0;
		//Attributes array 
		for(Entry<String,String> entry:fieldValSet){
			Attributes a = new Attributes();
			a.setAttributeKey((String) entry.getKey());
			String temp = (((String) entry.getValue()));
			if(temp != null){
				temp = temp.replace("&amp;", "&");
			}
			a.setAttributeValue(temp);
			ata[i] = a;
			i++;
		}
		if(WI_NAME == null || (WI_NAME.equals(""))){
			WI_NAME = fieldVal.get("WI_NAME");
		}

		//Attribute Details array
		AttributeDetails ad = new AttributeDetails();
		ad.setAttributes(ata);
		ada[0] = ad;

		//Application Attributes
		ApplicationAttributes aa = new ApplicationAttributes();
		aa.setAttributeDetails(ada);
		aaa[0] = aa;

		responseObj.setApplicationAttributes(aaa);
		responseObj.setWI_NAME(WI_NAME);
		responseObj.setLanguage(fieldVal.get("PREFERRED_LANGUAGE"));
		responseObj.setLeadNumber(fieldVal.get("LEAD_REF_NO"));
		responseObj.setStatusCode("0");
		responseObj.setStage(fieldVal.get("STAGE_ID"));
		responseObj.setApplicationName(applicationName);
		responseObj.setSYSREFNO(sysRefNo);
		return responseObj;	
	}
}
