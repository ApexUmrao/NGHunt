package com.newgen.cbg.testjourney;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.newgen.cbg.core.CoreEvent;
import com.newgen.cbg.core.IEventHandler;
import com.newgen.cbg.implementation.SingleUserConnection;
import com.newgen.cbg.logger.DSCOPLogMe;
import com.newgen.cbg.request.ApplicationAttributes;
import com.newgen.cbg.request.AttributeDetails;
import com.newgen.cbg.request.Attributes;
import com.newgen.cbg.response.CBGSingleHookResponse;
import com.newgen.cbg.utils.AESEncryption;
import com.newgen.cbg.utils.APCallCreateXML;
import com.newgen.cbg.utils.DSCOPConfigurations;
import com.newgen.cbg.utils.DSCOPUtils;
import com.newgen.cbg.utils.EnquiryParser;
import com.newgen.omni.jts.cmgr.XMLParser;

public class TF_Event implements IEventHandler{

	@Override
	public CBGSingleHookResponse dispatchEvent(CoreEvent paramCoreEvent) throws Exception {
		CBGSingleHookResponse responseObj = new CBGSingleHookResponse();
		String WI_NAME = paramCoreEvent.getWI_NAME();
		String stageId = paramCoreEvent.getStageId();
		String sysRefNo = paramCoreEvent.getSysRefNo();
		String applicationName = paramCoreEvent.getApplicationName();
		String language = paramCoreEvent.getLanguage();
		String requestedDateTime = paramCoreEvent.getRequestedDateTime();
		String valList = "";
		String paymentGatewayIdenitifier = "";
		String encryptedCardDetails = "";
		String ftAmount = "";
		String ftCardNumber = "";
		String ftCardHolderName = "";
		String ftExpDate = "";
		String ftCVV = "";
		String ftCardType = "";
		String ftIsEligible = "";
		String ftTranRefNo = "";
		String ftPGStatusCode = "";
		String ftPGResponseTime = "";
		String ftPGEncryptedResponse = "";
		String custAccountNumber = "";
		
		double ftTotalAmount = 0;
		int mainCode = 0;
		double timeInterval = 0;
		int ftSuccessCount = 0;
		String[][] blocksNFields;
		String wiName = "";

		SingleUserConnection instance = SingleUserConnection.getInstance(DSCOPConfigurations.getInstance().loginCount);
		String sessionId = instance.getSession(DSCOPConfigurations.getInstance().CabinetName,DSCOPConfigurations.getInstance().UserName,DSCOPConfigurations.getInstance().Password);
		HashMap<String, String> defaultAttributeMap = DSCOPUtils.getInstance().requestToDefaultValueMap();
		HashMap<String, String> attributeMap = DSCOPUtils.getInstance().requestToAttributeMap(paramCoreEvent.getRequest().getApplicationAttributes());
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Transaction Event: "+ attributeMap);

		String outputXML = APCallCreateXML.APSelect("SELECT TJ_ACCOUNT_NUM, TJ_AMOUNT, NAME, TJ_DC_DETAILS, TJ_CARD_TYPE   FROM USR_0_CBG_TEST_J_ADMIN_MASTER WHERE TRUNC(SYSDATE) BETWEEN TRUNC(TJ_START_DT) AND TRUNC(TJ_END_DT) ORDER BY ACTION_DATETIME DESC");
		XMLParser xp = new XMLParser(outputXML);
		mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		if(mainCode == 0){
			if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
				custAccountNumber=xp.getValueOf("TJ_ACCOUNT_NUM");
				ftAmount = xp.getValueOf("TJ_AMOUNT");
				ftCardHolderName = xp.getValueOf("NAME");
				String [] dcDetails =AESEncryption.decrypt(xp.getValueOf("TJ_DC_DETAILS")).split("\\|");
				ftCardNumber = dcDetails[0];
				ftExpDate = dcDetails[1];
				ftCVV =dcDetails[2];
				ftCardType = xp.getValueOf("TJ_CARD_TYPE");
			}
		}
		if(custAccountNumber != null && !(custAccountNumber.equals(""))){			
			if(stageId.equals("602")){
				outputXML = APCallCreateXML.APSelect("SELECT SUM(FT_AMOUNT) AS TOTAL_AMOUNT, WI_NAME FROM USR_0_CBG_FT WHERE FT_SUCCESS_COUNT = 1 GROUP BY WI_NAME HAVING WI_NAME = N'" + WI_NAME + "'");
				xp = new XMLParser(outputXML);
				mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				if(mainCode == 0){
					if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
						if(xp.getValueOf("TOTAL_AMOUNT") != null && !(xp.getValueOf("TOTAL_AMOUNT").equals(""))){
							ftTotalAmount = Double.parseDouble(xp.getValueOf("TOTAL_AMOUNT"));
						}						
					}			
				}
				if(Double.parseDouble(ftAmount) >= Double.parseDouble(defaultAttributeMap.get("FT_MIN_AMOUNT")) && (ftTotalAmount + Double.parseDouble(ftAmount)) <= Double.parseDouble(defaultAttributeMap.get("FT_MAX_AMOUNT"))){
					paymentGatewayIdenitifier =  attributeMap.get("FT_PAYMENT_GATEWAY_IDENTIFIER");
					encryptedCardDetails = attributeMap.get("FT_ENCRYPTED_CARD_DETAILS");
//					String decryptedCardDetails = DSCOPUtils.getInstance().decryptPayload602(sessionId, encryptedCardDetails);
					
//					StringTokenizer st = new StringTokenizer(decryptedCardDetails,"|");
//					ftCardNumber = st.nextToken();
//					ftExpDate = st.nextToken();
//					ftCVV = st.nextToken();
					outputXML = APCallCreateXML.APSelect("SELECT TO_CHAR(SYSDATE,'DDMMYYYY')||'602'||LPAD(SEQ_CBG_FT_TRAN_REF_NO.NEXTVAL,5,0) AS FT_TRAN_REF_NUM FROM DUAL");
					xp = new XMLParser(outputXML);
					mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
					if(mainCode == 0){
						ftTranRefNo = xp.getValueOf("FT_TRAN_REF_NUM");
					}
					valList = "'"+ WI_NAME + "', '"+ ftTranRefNo + "','"+ paymentGatewayIdenitifier + "','" + encryptedCardDetails +"',SYSTIMESTAMP,"+ ftAmount +",'"+DSCOPUtils.getInstance().maskCreditCardNo(ftCardNumber)+"','"+ftCardHolderName+"'";
					APCallCreateXML.APInsert("USR_0_CBG_FT", "WI_NAME, FT_TRANSACTION_REF_NUMBER, FT_PAYMENT_GATEWAY_IDENTIFIER, FT_ENCRYPTED_CARD_DETAILS,"
							+ " FT_REQUESTEDDATETIME, FT_AMOUNT, FT_CARD_NUMBER, FT_CARD_HOLDER_NAME"
							+ " ", valList, sessionId);

//					BinValidation call = new  BinValidation(defaultAttributeMap, sessionId, stageId, WI_NAME, ftTranRefNo);
//					outputXML = call.executeCall(defaultAttributeMap);
//					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "BinValidation CALL: output:"+ outputXML);
//					xp = new XMLParser(outputXML);
//					mainCode = Integer.parseInt(xp.getValueOf("returnCode", "1", true));
//					if(mainCode == 0){
						ftIsEligible = "Y";//xp.getValueOf("FTEligibility");
//						ftCardType = xp.getValueOf("CardType");
						if(ftIsEligible.equalsIgnoreCase("Y")){
							String payLoad = "1001000||11111111|"+ftTranRefNo+"|"+ftAmount+"|"+defaultAttributeMap.get("FT_SUCCESS_URL")+"|"+defaultAttributeMap.get("FT_FAILURE_URL")+"|"+defaultAttributeMap.get("FT_MODE")+"|"+
									defaultAttributeMap.get("FT_PAYMENT_MODE")+"|"+defaultAttributeMap.get("FT_TRANSACTION_TYPE")+"|"+defaultAttributeMap.get("FT_CURRENCY")+"||11111100000|"+ftCardNumber+"|"+
									ftExpDate.substring(0, 2)+"|"+ftExpDate.substring(2, 6)+"|"+ftCVV+"|"+ftCardHolderName+"|"+ftCardType;
							DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Transaction Event: payLoad: ");

							String encryptedPayload =  DSCOPUtils.getInstance().encryptPayLoad(sessionId, payLoad);
							DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, " Encrypt Payload Output" + encryptedPayload);
							encryptedPayload = defaultAttributeMap.get("FT_MERCHANT_ID_URL") + defaultAttributeMap.get("FT_MERCHANT_ID") + "||" + defaultAttributeMap.get("FT_COLLABORATOR_ID") + "||" + encryptedPayload;
							APCallCreateXML.APUpdate("USR_0_CBG_FT", "FT_IS_ELIGIBLE, FT_CARD_TYPE, FT_ENCRYPTED_PAYLOAD","'"+ ftIsEligible +"','"+ ftCardType +"','"+ encryptedPayload +"'", " FT_TRANSACTION_REF_NUMBER = N'"+ ftTranRefNo +"'", sessionId);
						} else {
							APCallCreateXML.APUpdate("USR_0_CBG_FT", "FT_IS_ELIGIBLE, FT_CARD_TYPE","'"+ ftIsEligible +"','"+ ftCardType +"'", " FT_TRANSACTION_REF_NUMBER = N'"+ ftTranRefNo +"'", sessionId);
						}
//					} else {
//						ftIsEligible = "N";
//						APCallCreateXML.APUpdate("USR_0_CBG_FT", "FT_IS_ELIGIBLE","'"+ ftIsEligible +"'", " FT_TRANSACTION_REF_NUMBER = N'"+ ftTranRefNo +"'", sessionId);
//					}				
					responseObj.setStatusCode("0");
					responseObj.setStatusMessage("Transaction Successful");	
				} else {
					responseObj.setStatusCode("0");
					responseObj.setStatusMessage("Invalid Transaction Amount");	
				}
				responseObj.setApplicationAttributes(responseAttribute602(defaultAttributeMap, ftTranRefNo, WI_NAME));
			} else if(stageId.equals("603")){
				boolean valid = true;
				ftPGStatusCode = attributeMap.get("PG_STATUS_CODE");
				if(ftPGStatusCode.equals("200")){
					ftPGEncryptedResponse = attributeMap.get("PG_ENCRYPTED_RESPONSE");
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "ftPGEncryptedResponse :"+ ftPGEncryptedResponse);
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "AddFundTransfer CALL: 603 split:"+ (ftPGEncryptedResponse.split("\\|\\|"))[1]);
					blocksNFields = getTransactionRefNo(DSCOPUtils.getInstance().decryptPayload(sessionId,(ftPGEncryptedResponse.split("\\|\\|"))[1]));
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "AddFundTransfer CALL: 603 encryption string array length:"+ blocksNFields.length);
					if(blocksNFields.length >= 4 && blocksNFields[3][1].equalsIgnoreCase("SUCCESS")){
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "AddFundTransfer CALL: 603 responseStatus value:"+ blocksNFields[3][1]);
						ftTranRefNo = blocksNFields[1][1];
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "AddFundTransfer CALL: 603 ftTranRefNo value:"+ ftTranRefNo);
						outputXML = APCallCreateXML.APSelect("SELECT (CAST(CURRENT_TIMESTAMP AS DATE) - (CAST(FT_REQUESTEDDATETIME AS DATE))) * 24 * 60 AS TIMEINTERVAL, DECODE(FT_SUCCESS_COUNT, null, 0, FT_SUCCESS_COUNT) AS FT_SUCCESS_COUNT, FT_AMOUNT, FT_CARD_TYPE, WI_NAME FROM USR_0_CBG_FT WHERE FT_TRANSACTION_REF_NUMBER = N'" + ftTranRefNo + "'");
						xp = new XMLParser(outputXML);
						mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
						if(mainCode == 0){
							if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
								timeInterval = Double.parseDouble(xp.getValueOf("TIMEINTERVAL"));
								ftSuccessCount = Integer.parseInt(xp.getValueOf("FT_SUCCESS_COUNT"));
								ftAmount = xp.getValueOf("FT_AMOUNT");
								ftCardType = xp.getValueOf("FT_CARD_TYPE");
								wiName = xp.getValueOf("WI_NAME");
							} else {
								valid = false;
							}
						}
						if(valid){						
							if(ftSuccessCount == 0){
								if(timeInterval <= Double.parseDouble(defaultAttributeMap.get("FT_TIME_INTERVAL"))){
									if(validate(blocksNFields, ftAmount, ftCardType, wiName, WI_NAME, defaultAttributeMap) == true){
										ftPGResponseTime = attributeMap.get("PG_RESPONSE_TIME_OUT");
										outputXML = APCallCreateXML.APSelect("SELECT TO_CHAR(SYSTIMESTAMP,'DD-MM-YYYY HH24:MI:SS.FF') AS FT_REQUESTEDDATETIME_IFT FROM DUAL");
										xp = new XMLParser(outputXML);
										mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
										String reqDateTimeIFT = "";
										if(mainCode == 0){
											reqDateTimeIFT = xp.getValueOf("FT_REQUESTEDDATETIME_IFT");
										}
										APCallCreateXML.APUpdate("USR_0_CBG_FT", "PG_STATUS_CODE, PG_RESPONSE_TIME_OUT, PG_ENCRYPTED_RESPONSE, FT_REQUESTEDDATETIME_IFT, FT_PG_REFFRENCE_NUMBER, FT_PG_RESPONSE_DATETIME, FT_PG_CARD_ENROLMENT_RESPONSE, FT_PG_ECI_INDICATOR, FT_PG_GATEWAY_TRACE_NUMBER, FT_PG_AUTH_CODE, FT_PG_STATUS_FLAG, FT_PG_ERROR_CODE, FT_PG_ERROR_MESSAGE",
												"'"+ ftPGStatusCode +"','"+ ftPGResponseTime + "','" + ftPGEncryptedResponse +"','"+reqDateTimeIFT+"','"+blocksNFields[2][1]+"','"+blocksNFields[2][2]+"','"+blocksNFields[2][3]+"','"+blocksNFields[2][4]+"','"+blocksNFields[2][5]+"','"+blocksNFields[2][6]+"','"+blocksNFields[3][1]+"','"+blocksNFields[3][2]+"','"+blocksNFields[3][3]+"'", " FT_TRANSACTION_REF_NUMBER = N'"+ ftTranRefNo +"'", sessionId);

										AddFundTransferTest call = new AddFundTransferTest(defaultAttributeMap, sessionId, stageId, WI_NAME, ftTranRefNo);
										outputXML = call.executeCall(defaultAttributeMap);
										DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "AddFundTransfer CALL: output:"+ outputXML);
										xp = new XMLParser(outputXML);
										mainCode = Integer.parseInt(xp.getValueOf("returnCode", "1", true));
										if(mainCode == 0){
											responseObj.setStatusCode("0");
											responseObj.setStatusMessage("Transaction Successful");	
										}
										else {
											responseObj.setStatusCode("0");
											responseObj.setStatusMessage("Internal Fund Transfer Failed");	
										}
									} else {
										responseObj.setStatusCode("0"); //31
										responseObj.setStatusMessage("Invalid Data");
									}
								} else {
									responseObj.setStatusCode("0");//26
									responseObj.setStatusMessage("Transaction Time Interval Exceeded");	
								}
							} else {
								responseObj.setStatusCode("0");//30
								responseObj.setStatusMessage("Internal Fund Transfer Already Done");	
							}
						} else {
							responseObj.setStatusCode("0");
							responseObj.setStatusMessage("Invalid Transaction Number");
						}
					} else {
						outputXML = APCallCreateXML.APSelect("SELECT TO_CHAR(SYSTIMESTAMP,'DD-MM-YYYY HH24:MI:SS.FF') AS FT_REQUESTEDDATETIME_IFT FROM DUAL");
						xp = new XMLParser(outputXML);
						mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
						String reqDateTimeIFT = "";
						if(mainCode == 0){
							reqDateTimeIFT = xp.getValueOf("FT_REQUESTEDDATETIME_IFT");
						}
						ftTranRefNo = blocksNFields[1][1];
						APCallCreateXML.APUpdate("USR_0_CBG_FT", "PG_STATUS_CODE, FT_REQUESTEDDATETIME_IFT, FT_PG_STATUS_FLAG, FT_PG_ERROR_CODE, FT_PG_ERROR_MESSAGE",
								"'"+ ftPGStatusCode +"','"+reqDateTimeIFT+"','"+blocksNFields[2][1]+"','"+blocksNFields[2][2]+"','"+blocksNFields[2][3]+"'", " FT_TRANSACTION_REF_NUMBER = N'"+ ftTranRefNo +"'", sessionId);

						responseObj.setStatusCode("0");
						responseObj.setStatusMessage("External Fund Transfer Failed");	
					}
				} else {
					responseObj.setStatusCode("0");
					responseObj.setStatusMessage("Transaction Successful");	
				}
				responseObj.setApplicationAttributes(responseAttribute603(defaultAttributeMap, ftTranRefNo, WI_NAME));
			}			
		} else {
			responseObj.setStatusCode("0");
			responseObj.setStatusMessage("Account Number Missing");	
		}
		responseObj.setWI_NAME(WI_NAME);
		responseObj.setStage(stageId);
		responseObj.setApplicationName(applicationName);
		responseObj.setLanguage(language);
		responseObj.setSYSREFNO(sysRefNo);
		return responseObj;
	}

	private ApplicationAttributes[] responseAttribute602(HashMap<String, String> defaultAttributeMap, String ftTranRefNo, String WI_NAME){
		ApplicationAttributes[] merged =  new ApplicationAttributes[1];
		try {
			String outputXML = APCallCreateXML.APSelect("SELECT FT_IS_ELIGIBLE, FT_ENCRYPTED_PAYLOAD FROM USR_0_CBG_FT WHERE FT_TRANSACTION_REF_NUMBER = N'"+ ftTranRefNo +"'");
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if(mainCode == 0){
				ApplicationAttributes[] ft = DSCOPUtils.getInstance().getApplicationAttributes(outputXML);										
				String query = "SELECT RESXML FROM USR_0_CBG_TEST_J_STAGE_RES WHERE STAGEID = '602'";
				outputXML = APCallCreateXML.APSelect(query);
				xp = new XMLParser(outputXML);
				mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				if(mainCode == 0){
					String attributeDetails = xp.getValueOf("attributeDetails");
					if(attributeDetails != null && !attributeDetails.equals("")){
						attributeDetails = attributeDetails.replace("&", "&amp;");
						HashMap<String,String> fieldVal = EnquiryParser.parseXML("<?xml version=\"1.0\"?>"+attributeDetails);
						ApplicationAttributes[] aaa = new ApplicationAttributes[1];
						AttributeDetails[] ada = new AttributeDetails[1];
						Attributes[] ata = new Attributes[fieldVal.size()]; 
						Set<Map.Entry<String,String>> fieldValSet = fieldVal.entrySet();
						int i = 0;
						//Attributes array 

						for(Entry entry:fieldValSet){
							Attributes a = new Attributes();
							a.setAttributeKey((String) entry.getKey());
							String temp = (((String) entry.getValue()));
							if(temp != null){
								DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"value with &amp; "+ temp);
								temp = temp.replace("&amp;", "&");
								DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"value with &amp; after "+ temp);
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

						ApplicationAttributes[] ext =aaa;
						int arrayLen = ft[0].getAttributeDetails().length + ext[0].getAttributeDetails().length;
						AttributeDetails[] attributMerged = new AttributeDetails[arrayLen];
						i =0;
						for (AttributeDetails applicationAttributes : ft[0].getAttributeDetails()) {
							attributMerged[i] = applicationAttributes;
							i++;
						}

						for (AttributeDetails applicationAttributes : ext[0].getAttributeDetails()) {
							attributMerged[i] = applicationAttributes;
							i++;
						}
						ApplicationAttributes aa1 = new ApplicationAttributes();
						aa1.setAttributeDetails(attributMerged);
						merged[0]=aa1;
					}
				}
			}
			} catch (Exception e) {
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, "responseAttribute602 error" + e.getMessage());
			}
			return merged;
		}


		private ApplicationAttributes[] responseAttribute603(HashMap<String, String> defaultAttributeMap, String ftTranRefNo, String WI_NAME){
			ApplicationAttributes[] merged = new ApplicationAttributes[1] ;
			try {
				String outputXML = APCallCreateXML.APSelect("SELECT FT_TRANSACTION_REF_NUMBER, FT_AMOUNT, FT_IS_ELIGIBLE, DECODE(FT_PG_ERROR_CODE,'00000','Y','N') AS FT_STATUS_1, DECODE(FT_STATUS_CODE,null,'N',FT_STATUS_CODE) AS FT_STATUS_2,'"+ defaultAttributeMap.get("FT_MIN_AMOUNT") +"' AS FT_MIN_AMOUNT, '"
						+ defaultAttributeMap.get("FT_MAX_AMOUNT") +"' AS FT_MAX_AMOUNT FROM USR_0_CBG_FT WHERE FT_TRANSACTION_REF_NUMBER = N'" + ftTranRefNo + "'");
				XMLParser xp = new XMLParser(outputXML);
				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				if(mainCode == 0){
					ApplicationAttributes[] ft = null;
					if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
						String status1 = xp.getValueOf("FT_STATUS_1");
						String status2 = xp.getValueOf("FT_STATUS_2");

						if(status1.equalsIgnoreCase("N") || status2.equalsIgnoreCase("N")){
							outputXML = APCallCreateXML.APSelect("SELECT FT_TRANSACTION_REF_NUMBER, '0' AS FT_AMOUNT, FT_IS_ELIGIBLE, DECODE(FT_PG_ERROR_CODE,'00000','Y','N') AS FT_STATUS_1, DECODE(FT_STATUS_CODE,null,'N',FT_STATUS_CODE) AS FT_STATUS_2,'"+ defaultAttributeMap.get("FT_MIN_AMOUNT") +"' AS FT_MIN_AMOUNT, '"
									+ defaultAttributeMap.get("FT_MAX_AMOUNT") +"' AS FT_MAX_AMOUNT FROM USR_0_CBG_FT WHERE FT_TRANSACTION_REF_NUMBER = N'" + ftTranRefNo + "'");
						} 

						ft = DSCOPUtils.getInstance().getApplicationAttributes(outputXML);

						//					else {
						//						outputXML = APCallCreateXML.APSelect("SELECT 'N' AS FT_STATUS_1, 'N' AS  FT_STATUS_2, 'Y' AS FT_IS_ELIGIBLE, '0' AS FT_AMOUNT, '"+ defaultAttributeMap.get("FT_MIN_AMOUNT") +"' AS FT_MIN_AMOUNT, '"
						//						+ defaultAttributeMap.get("FT_MAX_AMOUNT") +"' AS FT_MAX_AMOUNT  FROM DUAL");
						//						xp = new XMLParser(outputXML);
						//						ft = DSCOPUtils.getInstance().getApplicationAttributes(outputXML);	
						//					}
					} else {
						outputXML = APCallCreateXML.APSelect("SELECT 'N' AS FT_STATUS_1, 'N' AS  FT_STATUS_2, 'Y' AS FT_IS_ELIGIBLE, '0' AS FT_AMOUNT, '"+ defaultAttributeMap.get("FT_MIN_AMOUNT") +"' AS FT_MIN_AMOUNT, '"
								+ defaultAttributeMap.get("FT_MAX_AMOUNT") +"' AS FT_MAX_AMOUNT  FROM DUAL");
						xp = new XMLParser(outputXML);
						ft = DSCOPUtils.getInstance().getApplicationAttributes(outputXML);	
					}
									
					String query = "SELECT RESXML FROM USR_0_CBG_TEST_J_STAGE_RES WHERE STAGEID = '603'";
					outputXML = APCallCreateXML.APSelect(query);
					xp = new XMLParser(outputXML);
					mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
					if(mainCode == 0){
						String attributeDetails = xp.getValueOf("attributeDetails");
						if(attributeDetails != null && !attributeDetails.equals("")){
							attributeDetails = attributeDetails.replace("&", "&amp;");
							HashMap<String,String> fieldVal = EnquiryParser.parseXML("<?xml version=\"1.0\"?>"+attributeDetails);
							ApplicationAttributes[] aaa = new ApplicationAttributes[1];
							AttributeDetails[] ada = new AttributeDetails[1];
							Attributes[] ata = new Attributes[fieldVal.size()]; 
							Set<Map.Entry<String,String>> fieldValSet = fieldVal.entrySet();
							int i = 0;
							//Attributes array 

							for(Entry entry:fieldValSet){
								Attributes a = new Attributes();
								a.setAttributeKey((String) entry.getKey());
								String temp = (((String) entry.getValue()));
								if(temp != null){
									DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"value with &amp; "+ temp);
									temp = temp.replace("&amp;", "&");
									DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"value with &amp; after "+ temp);
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

							ApplicationAttributes[] ext =aaa;	
						int arrayLen = ft[0].getAttributeDetails().length + ext[0].getAttributeDetails().length;
						AttributeDetails[] attributMerged = new AttributeDetails[arrayLen];
						i =0;
						for (AttributeDetails applicationAttributes : ft[0].getAttributeDetails()) {
							attributMerged[i] = applicationAttributes;
							i++;
						}

						for (AttributeDetails applicationAttributes : ext[0].getAttributeDetails()) {
							attributMerged[i] = applicationAttributes;
							i++;
						}
						ApplicationAttributes aa1 = new ApplicationAttributes();
						aa1.setAttributeDetails(attributMerged);
						merged[0]=aa1;
					}
				}
				}
			} catch (Exception e) {
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);

			}
			return merged;
		}

		private String[][] getTransactionRefNo(String decryptedString){
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "AddFundTransfer CALL: getTransactionRefNo:"+ decryptedString);
			String[][] blocksNFields = DSCOPUtils.getInstance().getResponseList(decryptedString);
			return blocksNFields;
		}

		private boolean validate(String[][] blocksNFields, String amount, String cardType, String wiName603, String wiName602, HashMap<String, String> defaultAttribMap){
			if((Double.parseDouble(blocksNFields[1][3]) == Double.parseDouble(amount)) && blocksNFields[1][5].equals(cardType) 
					&& blocksNFields[1][4].equals(defaultAttribMap.get("FT_PAYMENT_MODE")) 
					&& blocksNFields[1][2].equals(defaultAttribMap.get("FT_CURRENCY"))
					&& wiName602.equals(wiName603)){
				return true;
			} else {
				return false;
			}		
		}
	}
