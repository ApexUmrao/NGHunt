package com.newgen.ao.laps.sourcingchannel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.cache.ITQAN_M_StagingCache;
import com.newgen.ao.laps.cache.ProcessEventCache;
import com.newgen.ao.laps.cache.CallValidation;
import com.newgen.ao.laps.callhandler.CallHandler;
import com.newgen.ao.laps.calls.PushMessageItqanM;
import com.newgen.ao.laps.calls.PushMessageItqanM_Temp;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.response.LapsModifyResponse;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.wf.util.excp.NGException;

public class C_ITQAN_M implements SourcingChannelHandler{
	public LapsModifyResponse resp = new LapsModifyResponse();
	private int processDefId;
	int stageId = 1;
	String status = "0";
	String workItemNumber ="";
	String statusDesc = "Calls Executed Successfully";
	HashMap<String, String> attribMap = new HashMap<String, String>();
	private HashMap<String, LinkedHashMap<Integer, HashMap<String, String>>> complexMap;
	HashMap<String, String> attributeCLOBMap = new HashMap<String, String>();
	private String documentName;
	private String documentIndex;
	private String txnDataoutputXml ="";
	private String queryOutputXml ="";
	private String errorDescMandatoryCheck;
	private String errorCodeMandatoryCheck;
	private String statusCodeMandatoryCheck;
	private String leadRefNo;





	private static Map<String, ArrayList<String>> wbgStagingMasterMap;
	HashMap<String,CallValidation> wbgValidationMasterMap;

	private HashMap<String, String> attributeMap = new HashMap<String, String>();
	@Override
	public LapsModifyResponse dispatchEvent(String sessionId, String uniqueRefNo, String correlationId,
			String sourcingChannel, String mode, String wiNumber, String processName) {
		resp.setChannelRefNumber(uniqueRefNo);
		resp.setCorrelationId(correlationId);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside C_ITQAN_M");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside C_ITQAN_M  ProcessName" +processName);

		try {
			if(uniqueRefNo.equalsIgnoreCase("")){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"uniqueRefNo is null");
				PushMessageItqanM_Temp pushMsg_temp = new PushMessageItqanM_Temp(sessionId);
				pushMsg_temp.call();
			} else {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"uniqueRefNo is not null");
				String output = APCallCreateXML.APSelect("SELECT DISTINCT(PROCESSDEFID) from wfinstrumenttable where PROCESSNAME = '"+processName+"'");
				XMLParser xmlparser = new XMLParser(output);
				int mainCode1 = Integer.parseInt(xmlparser.getValueOf("MainCode"));
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode1: " + mainCode1);			
				if(mainCode1 == 0){
					if(Integer.parseInt(xmlparser.getValueOf("TotalRetrieved")) > 0){
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode1: " + mainCode1);			
						processDefId = Integer.parseInt(xmlparser.getValueOf("PROCESSDEFID"));
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "processDefId: " + processDefId);			

					}
				}
				//if(Integer.parseInt(xp.getValueOf("MainCode")) == 0 && Integer.parseInt(xp.getValueOf("WI_COUNT")) == 0){
				StringBuffer ausColumns = new StringBuffer();
				StringBuffer ausValues = new StringBuffer();
				StringBuffer accColumns = new StringBuffer();
				StringBuffer accValues = new StringBuffer();

				wbgStagingMasterMap = ITQAN_M_StagingCache.getInstance().getWbgStagingMasterMap();
				txnDataoutputXml = APCallCreateXML.APSelect("select * from BPM_WBG_MSG_TXN_CUST_DATA where UNIQUE_REF_NO = '" +uniqueRefNo +"'");
				XMLParser txnDataxp = new XMLParser(txnDataoutputXml);

				for(String attributeKey : wbgStagingMasterMap.keySet()){
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside C_ITQAN_M 1"+attributeKey);
					ArrayList<String> values = new ArrayList<String>();
					values = wbgStagingMasterMap.get(attributeKey);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside C_ITQAN_M values "+values);
					if(values.get(5).equalsIgnoreCase("EXTERNAL")){
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside EXT_WBG_AO values "+values.get(0));
						if(values.get(2) == "Y"){  
							attributeMap.put(values.get(1), txnDataxp.getValueOf("TO_DATE('"+txnDataxp.getValueOf(values.get(3))+"','DD/MM/YYYY hh24:mi:ss'),"));
						} else {
							attributeMap.put(values.get(1), txnDataxp.getValueOf(values.get(3)));
						}
					}  else if(values.get(5).equalsIgnoreCase("AUS")) {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside aus values "+values.get(1));
						ausColumns.append(values.get(1)+",");
						if(values.get(2) == "Y"){   //date
							ausValues.append("TO_DATE('"+txnDataxp.getValueOf(values.get(3))+"','DD/MM/YYYY hh24:mi:ss'),");
						}  else {
							if("custCountry".equalsIgnoreCase(attributeKey) || "custNationality".equalsIgnoreCase(attributeKey)
									 || "custResidence".equalsIgnoreCase(attributeKey)){
								queryOutputXml = APCallCreateXML.APSelect("SELECT COUNTRY  FROM USR_0_COUNTRY_MAST WHERE CNTRY_CODE ='" + txnDataxp.getValueOf(values.get(3)) +"'");
								XMLParser Outputxp = new XMLParser(queryOutputXml);
							//	LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside USR_0_WBG_AO_AUS values "+Outputxp.toString());
								ausValues.append("'" + Outputxp.getValueOf("COUNTRY") + "',");
							} else if("flgCustType".equalsIgnoreCase(attributeKey)){
								queryOutputXml = APCallCreateXML.APSelect("SELECT cust_category_desc FROM usr_0_cob_cust_cat WHERE cust_category_code='" + txnDataxp.getValueOf(values.get(3)) +"'");
								XMLParser Outputxp = new XMLParser(queryOutputXml);
							//	LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside USR_0_WBG_AO_AUS values "+Outputxp.toString());
								ausValues.append("'" + Outputxp.getValueOf("cust_category_desc") + "',");
							}  else if("signType".equalsIgnoreCase(attributeKey)){
								queryOutputXml = APCallCreateXML.APSelect("select sign_desc FROM USR_0_sign_style WHERE SIGN_CODE ='" + txnDataxp.getValueOf(values.get(3)) +"'");
								XMLParser Outputxp = new XMLParser(queryOutputXml);
							//	LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside USR_0_WBG_AO_AUS values "+Outputxp.toString());
								ausValues.append("'" + Outputxp.getValueOf("sign_desc") + "',");
							} else if("systemRisk".equalsIgnoreCase(attributeKey)){
								queryOutputXml = APCallCreateXML.APSelect("select risk_value from  USR_0_RISK_VALUES WHERE risk_code ='" + txnDataxp.getValueOf(values.get(3)) +"'");
								XMLParser Outputxp = new XMLParser(queryOutputXml);
							//	LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside USR_0_WBG_AO_AUS values "+Outputxp.toString());
								ausValues.append("'" + Outputxp.getValueOf("risk_value") + "',");
							} else {
								ausValues.append("'" + txnDataxp.getValueOf((String)values.get(3)) + "',");
							}
						}
					} else if(values.get(5).equalsIgnoreCase("ACC_INFO")) {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside USR_0_WBG_AO_ACC_INFO values "+values.get(1));
						accColumns.append(values.get(1)+",");
						if(values.get(2) == "Y"){   //date
							accValues.append("TO_DATE('"+txnDataxp.getValueOf(values.get(3))+"','DD/MM/YYYY hh24:mi:ss'),");
						}  else {
							accValues.append("'"+ txnDataxp.getValueOf(values.get(3))+"',");
						}
					}
				}

				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside EXT_WBG_AO attributeMap "+attributeMap);
			//	LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside EXT_WBG_AO ausValues "+ausValues);
			//	LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside EXT_WBG_AO ausColumns "+ausColumns);
			//	LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside USR_0_WBG_AO_ACC_INFO "+accValues);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside USR_0_WBG_AO_ACC_INFO "+accColumns);

				String outputXml = ProdCreateXML.WFUploadWorkItem(sessionId, "N", attributeMap,"",processDefId,"AutoIntro");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXml :"+outputXml);
				XMLParser xp = new XMLParser(outputXml);
				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode " + mainCode);

				if (mainCode == 0) {
					resp.setStatusCode("0");
					resp.setStatusDescription("Workitem Created Successfully");
					workItemNumber = xp.getValueOf("ProcessInstanceId");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "workItemNumber is " +workItemNumber);
					HashMap<String, String> hm = new HashMap<>();
					hm.put("WI_NAME", workItemNumber);
					outputXml = ProdCreateXML.WFSetAttributes(sessionId, workItemNumber, 1,
							hm, complexMap, processDefId);
					String outputXml1 = APCallCreateXML.APSelect("SELECT LEAD_REF_NO FROM  EXT_WBG_AO  WHERE WI_NAME = '"+workItemNumber+"'");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "outputXml1 is " +outputXml1);
					XMLParser xp1 = new XMLParser(outputXml1);
					mainCode = Integer.parseInt(xp1.getValueOf("MainCode"));
					if (mainCode == 0) {
						leadRefNo = xp1.getValueOf("LEAD_REF_NO");
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "leadRefNo is " +leadRefNo);
					}	
					//folderIndex = xp.getValueOf("FolderIndex");	
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Wi no is " +workItemNumber);
					resp.setWiNumber(workItemNumber);
					ausColumns.append("WI_NAME"+",").append("LEAD_REFNO");
					ausValues.append("'"+workItemNumber+"'"+",").append("'"+txnDataxp.getValueOf("LEAD_REF_NO")+"'");
					accColumns.append("WI_NAME"+",").append("LEAD_REFNO");
					accValues.append("'"+workItemNumber+"'"+",").append("'"+txnDataxp.getValueOf("LEAD_REF_NO")+"'");

					APCallCreateXML.APInsert("USR_0_WBG_AO_AUS",ausColumns.toString(), ausValues.toString(), sessionId);
					APCallCreateXML.APInsert("USR_0_WBG_AO_ACC_INFO",accColumns.toString(), accValues.toString(), sessionId);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "errorDescMandatoryCheck is " +errorDescMandatoryCheck);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "errorCodeMandatoryCheck is " +errorCodeMandatoryCheck);
					
					if(!mandatoryDataCheck() || !mandatoryDocCheck(uniqueRefNo)){
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside IF ");
						String pushMessageXML = LapsUtils.createPushMesgXML(leadRefNo,workItemNumber,
								statusCodeMandatoryCheck,errorCodeMandatoryCheck,errorDescMandatoryCheck);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "pushMessageXML if "+pushMessageXML);
						LapsUtils.updatePushMeassgeXML(sessionId,workItemNumber,pushMessageXML);
						PushMessageItqanM pushMsg = new PushMessageItqanM(sessionId, workItemNumber);
						pushMsg.call();
					} else {
						HashMap<Integer, Integer> eventMap = LapsUtils.getInstance().getEventDetector
								(processDefId, sourcingChannel, "1", sourcingChannel,stageId);
						int eventID = (Integer) eventMap.keySet().toArray()[0];
						List<CallEntity> callArray = ProcessEventCache.getReference().getEventCallList(eventID);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "eventID: " + eventID);

						if(callArray != null) {
							attributeMap.put("UNIQUE_REF_NO",uniqueRefNo);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Call_Array: " + workItemNumber + ":" + callArray.toString());
							for (CallEntity callEntity : callArray) {
								if(callEntity.isMandatory()){
									String outputXML = CallHandler.getInstance().executeCall(callEntity, attributeMap, sessionId,
											String.valueOf(eventID),workItemNumber, false);
									xp = new XMLParser(outputXML);
									if("0".equalsIgnoreCase(status)){
										status = xp.getValueOf("returnCode");
									}
									if(!"0".equals(status)){
										statusDesc = xp.getValueOf("errorDescription");
									}
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Execute Call: outputxml: "
											+ outputXML);
								}
							}
						}
						//added by reyaz 21092022
						statusCodeMandatoryCheck ="0";
					    errorCodeMandatoryCheck ="200";
					    errorDescMandatoryCheck ="Work Item created successfully";
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "leadRefNo is " +leadRefNo);
						String pushMessageXML = LapsUtils.createPushMesgXML(leadRefNo,workItemNumber,
								statusCodeMandatoryCheck,errorCodeMandatoryCheck,errorDescMandatoryCheck);
						LapsUtils.updatePushMeassgeXML(sessionId,workItemNumber,pushMessageXML);
						PushMessageItqanM pushMsg = new PushMessageItqanM(sessionId, workItemNumber);
						pushMsg.call();
					}
					
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"C_ITQAN_M: workItemNumber= "+workItemNumber+"   errorCodeMandatoryCheck= "+errorCodeMandatoryCheck);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"C_ITQAN_M: errorDescMandatoryCheck = "+errorDescMandatoryCheck+" statusDesc= "+statusDesc);
					
					///}
				}
				resp.setChannelRefNumber(uniqueRefNo);
				resp.setCorrelationId(correlationId);
				//			} else {
				//				resp.setStatusCode("-1");
				//				resp.setStatusDescription("Duplicate Request (Workitem with same  Ref Number already exists)");
				//	
				//			}
			}
	
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in C_ITQAN_M dispatchEvent: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Workitem Number : " +workItemNumber);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Workitem Number : " +errorCodeMandatoryCheck);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Workitem Number : " +errorDescMandatoryCheck);
		resp.setWiNumber(workItemNumber);
		resp.setStatusCode(errorCodeMandatoryCheck); // status
		resp.setStatusDescription(errorDescMandatoryCheck); //statusDesc
		resp.setChannelRefNumber(uniqueRefNo);
		resp.setCorrelationId(correlationId);
		return resp;
	}
	
	private boolean mandatoryDocCheck(String uniqueRefNo){
		String outputXML = "";
		boolean passCheck = false;
		boolean eidaCheck = false;
		try {
			outputXML = APCallCreateXML.APSelect("SELECT DOCNAME, DOCINDEX FROM BPM_WBG_MSG_TXN_DOC_DATA "
					+ "WHERE UNIQUE_REF_NO = N'" + uniqueRefNo + "' and UPPER(DOCNAME) IN  ('EIDA','PASSPORT')");
		} catch (NGException e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		XMLParser xp = new XMLParser(outputXML);
		int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		if(mainCode == 0){
			if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) >0){
				int start = xp.getStartIndex("Records", 0, 0);
				int deadEnd = xp.getEndIndex("Records", start, 0);
				int noOfFields = xp.getNoOfFields("Record", start,deadEnd);
				int end = 0;
				for(int i = 0; i < noOfFields; ++i){
					if(!eidaCheck || !passCheck){
						start = xp.getStartIndex("Record", end, 0);
						end = xp.getEndIndex("Record", start, 0);
						documentName = xp.getValueOf("DOCNAME" ,start, end);
						documentIndex = xp.getValueOf("DOCINDEX" ,start, end);
						if(documentName.toUpperCase().toString().equalsIgnoreCase("EIDA")){
							if(!documentIndex.equalsIgnoreCase("")) {
								eidaCheck = true;
							}
						} else if(documentName.toUpperCase().toString().equalsIgnoreCase("PASSPORT")){
							if(!documentIndex.equalsIgnoreCase("")) {
								passCheck = true;
							}				
						}
					}
				}
				if(!eidaCheck){
					errorDescMandatoryCheck = "Invalid request. Eida Document is mandatory for customer";
					errorCodeMandatoryCheck = "450";
					statusCodeMandatoryCheck = "1";
					return false;
				} else if(!passCheck){
					errorDescMandatoryCheck = "Invalid request. Passport Document is mandatory for customer";
					errorCodeMandatoryCheck = "451";
					statusCodeMandatoryCheck = "1";
					return false;
				}
			} else {
				errorDescMandatoryCheck = "Invalid request. Passport And Eida Document is mandatory for customer";
				errorCodeMandatoryCheck = "452";
				return false;
			}
		} else {
			errorDescMandatoryCheck = "Invalid request. Passport And Eida Document is mandatory for customer";
			errorCodeMandatoryCheck = "452";
			return false;
		}
		return true;
	}
	
	public static boolean validateDateFormat(String strDate,String strDateFormat){
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside validateDateFormat ");
		if(strDate.trim().equals("")){
			return true;
		} else {
			SimpleDateFormat sdfrmt = new SimpleDateFormat(strDateFormat);
			sdfrmt.setLenient(false);
			try{
				Date javaDate  = sdfrmt.parse(strDate);
				
			} catch(ParseException e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Invalid date format");
				return false;
			}
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"valid date format");
		return true;
		
	}
	
	private boolean mandatoryDataCheck(){
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside mandatoryDataCheck "+txnDataoutputXml);
		wbgValidationMasterMap = ITQAN_M_StagingCache.getInstance().getValidationMasterMap();
		XMLParser xp = new XMLParser(txnDataoutputXml);
		int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		String tagValue = "";
		if(mainCode == 0){
			for(String attributeKey : wbgValidationMasterMap.keySet()){
				CallValidation values = wbgValidationMasterMap.get(attributeKey);
				String errorCode = values.getErrorCode();
				String errorDesc = values.getErrorDesc();
				String isMand = values.getIsMandateValue();
				String maxLen = values.getMaxLength();
				String ColName = values.getStagingColName();
				String minLen = values.isMinLength();
				String lenCheck = values.isLengthCheck();
				String isLOV = values.getIsLOV();
				String lovQuery = values.getLOVQuery();
				String isSpecValue = values.getIsSpecValue();
				String specValue = values.getSpecValue();
				String dateFormat = values.getDateFormat();
				tagValue = xp.getValueOf(ColName);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside ColName "+ColName);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside ColName Value "+xp.getValueOf(ColName));
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside tagValue "+tagValue);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside errorCode "+errorCode);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside errorDesc "+errorDesc);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside isMand "+isMand);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside maxLen "+maxLen);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside ColName "+ColName);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside minLen "+minLen);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside lenCheck "+lenCheck);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside lenCheck "+dateFormat);

				if(isMand.equalsIgnoreCase("Y")){   //6 is_mandatory
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside MandatoryCheck "+isMand);
					if(tagValue.equalsIgnoreCase("")){
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Values is null For "+attributeKey);
						errorDescMandatoryCheck = errorDesc;  //7 is errorDesc
						errorDescMandatoryCheck = errorDescMandatoryCheck.replace("<FieldName>", attributeKey);
						errorCodeMandatoryCheck = errorCode;  //8 is errorCode
						statusCodeMandatoryCheck = "1";
						return false;
					}
				}
				if(lenCheck.equalsIgnoreCase("Y")){          //9 is length check
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside MandatoryCheck "+lenCheck);
					int tagValuelength = tagValue.length();		 // 10 is min length   and //11 is max length
					if(tagValuelength < Integer.parseInt(minLen) || tagValuelength > Integer.parseInt(maxLen)) {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"len is < "+minLen+" or len is > "+maxLen);
						errorDescMandatoryCheck = errorDesc;
						errorDescMandatoryCheck = errorDescMandatoryCheck.replace("<FieldName>", attributeKey);
						errorCodeMandatoryCheck = errorCode;
						statusCodeMandatoryCheck = "1";
						return false;
					}
				}
				if(isSpecValue.equalsIgnoreCase("Y")){
					boolean flag = false;
					String[] specificValuesArray = specValue.split(",");
					for(String specificValues : specificValuesArray){
						if(tagValue.equalsIgnoreCase(specificValues)){
							flag = true;
							break;
						}
					}
					if(flag == false){
						errorDescMandatoryCheck = errorDesc;
						errorDescMandatoryCheck = errorDescMandatoryCheck.replace("<FieldName>", attributeKey);
						errorCodeMandatoryCheck = errorCode;
						statusCodeMandatoryCheck = "1";
						return false;
					}
				}
				if(isLOV.equalsIgnoreCase("Y")){
					String outputXml;
					try {
						outputXml = APCallCreateXML.APSelect(lovQuery);
						XMLParser xpLov = new XMLParser(outputXml);						
						if(Integer.parseInt(xpLov.getValueOf("MainCode")) == 0){
							boolean flag = false;
							int start = xpLov.getStartIndex("Records", 0, 0);
							int deadEnd = xpLov.getEndIndex("Records", start, 0);
							int noOfFields = xpLov.getNoOfFields("Record", start, deadEnd);
							int end = 0;
							for (int i = 0; i < noOfFields; ++i) {
								start = xpLov.getStartIndex("Record", end, 0);
								end = xpLov.getEndIndex("Record", start, 0);
								String lovValue = xpLov.getValueOf("LOV_VALUE", start, end);
								if(lovValue.equalsIgnoreCase(tagValue)){
									flag = true;
									break;
								}
							}
							if(flag == false) {
								errorDescMandatoryCheck = errorDesc;  //7 is errorDesc
								errorDescMandatoryCheck = errorDescMandatoryCheck.replace("<FieldName>", attributeKey);
								errorCodeMandatoryCheck = errorCode;  //8 is errorCode
								statusCodeMandatoryCheck = "1";
								return false;
							}
						}
					} catch (NGException e) {
						e.printStackTrace();
					}
				} if(attributeKey.equalsIgnoreCase("custEmail")){
					if(!(tagValue.contains(".") && tagValue.contains("@"))){
						errorDescMandatoryCheck = errorDesc;  //7 is errorDesc
						errorDescMandatoryCheck = errorDescMandatoryCheck.replace("<FieldName>", attributeKey);
						errorCodeMandatoryCheck = errorCode;  //8 is errorCode
						statusCodeMandatoryCheck = "1";
						return false;
					}
				}
				if(attributeKey.contains("Date") || attributeKey.contains("DOB")){
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside date ");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Date is -> "+tagValue+" Expected date format is -> "+dateFormat);

					if(!validateDateFormat(tagValue,dateFormat)){
						errorDescMandatoryCheck = errorDesc;  //7 is errorDesc
						errorDescMandatoryCheck = errorDescMandatoryCheck.replace("<FieldName>", attributeKey);
						errorCodeMandatoryCheck = errorCode;  //8 is errorCode
						statusCodeMandatoryCheck = "1";
						return false;
					}
				}
			}
		}
		return true;
	}
	
	
	
	}

