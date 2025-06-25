package com.newgen.ao.laps.calls;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.cache.ChannelCallCache;
import com.newgen.ao.laps.core.ICallExecutor;
import com.newgen.ao.laps.logger.LapsModifyDBLogger;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.sourcingchannel.DC_PROTRADE;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;

public class RouteBRMSWorkitem implements ICallExecutor, Callable<String> {

	private String sessionId;
	private String callStatus = "N";
	private int returnCode;
	private String errorDetail;
	private String errorDescription;
	private int stageId;
	private Map<String, String> attributeMap;
	private Map<String, String> fetchMap;
	private String wiName;
	private String mode;
	private String ruleFlag = "";
	private String auditCallName = "RBM";
	private Map<String, ArrayList<String>> tfoBRMSMap;
	private Map<String,String> protradeTargetMap;
//	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
	private String finaloutputXml =""; //ATP-496 29-07-2024 REYAZ

	public RouteBRMSWorkitem(Map<String, String> attributeMap, String sessionId, String stageId, String wiName, 
			Boolean prevStageNoGo, CallEntity callEntity){
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside RouteBRMSWorkitem >> ");
		try {
			this.sessionId =sessionId;
			this.wiName = wiName;
			this.attributeMap = attributeMap;
			this.mode = attributeMap.get("mode");
			this.stageId = Integer.parseInt(stageId);
			if(attributeMap.containsKey("ruleFlag")){
				this.ruleFlag = attributeMap.get("ruleFlag");
			}
			tfoBRMSMap = ChannelCallCache.getInstance().getBRMSMap();
			fetchMap = ChannelCallCache.getInstance().getFetchMap();
			protradeTargetMap = ChannelCallCache.getInstance().getProtradeTargetMap();
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"RouteBRMSWorkitem attributeMap: "+attributeMap.toString());
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "RouteBRMSWorkitem exception: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
	}

	@Override
	public String call() throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside RouteBRMSWorkitem call() ");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"RouteBRMSWorkitem stageId: "+stageId+", mode: "+mode
				+", ruleFlag: "+ruleFlag);
		String targetWS = "";
		String subRule = "";
		String currWS = "";
		ArrayList<String> list;
		String outputXml = APCallCreateXML.APSelect("SELECT CURR_WS, PROCESS_TYPE, REQUEST_CATEGORY, REQUEST_TYPE, BILL_TYPE, IF_SIGHT_BILL, "
				+ "DISCREPANCY_INSTRUCTION, SETTLEMENT_INSTRUCTION, trunc(SYSDATE) AS NEW_DATE, INF_MATURITY_DATE, "
				+ "case when trunc(to_date(INF_MATURITY_DATE,'yyyy-MM-dd HH24:MI:SS'))>trunc(SYSDATE) then 'Greater' else 'EqualOrLess' end as MATURITY_FLAG, "
				+ "TRN_STATUS, BILL_STAGE, TRANSACTION_REFERENCE, BILL_LN_REFRNCE, TRANSACTION_ADCB_LC_REFERENCE, CUSTOMER_ID"
				+ " from ext_tfo where WI_NAME = '" +wiName+"'");
		XMLParser xp = new XMLParser(outputXml);
		int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		if(mainCode == 0) {
			currWS = xp.getValueOf("CURR_WS");
			String requestCategory = xp.getValueOf("REQUEST_CATEGORY");
			String requestType = xp.getValueOf("REQUEST_TYPE");
			String billType = xp.getValueOf("BILL_TYPE");
			String ifSight = xp.getValueOf("IF_SIGHT_BILL");
			String discInst = xp.getValueOf("DISCREPANCY_INSTRUCTION");
			String settInst = xp.getValueOf("SETTLEMENT_INSTRUCTION");
			String processType = xp.getValueOf("PROCESS_TYPE");
			String trnStatus = xp.getValueOf("TRN_STATUS");
			String billStage = xp.getValueOf("BILL_STAGE");
			String maturityDate = xp.getValueOf("INF_MATURITY_DATE");
			String customerId = xp.getValueOf("CUSTOMER_ID");
			String maturityFlag = "";
			String trnsRefColumn = "";
			String trnsRefVal = "";
			try {
				trnsRefColumn = fetchMap.get(processType+"#"+requestCategory+"#"+requestType);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"UpdateTargetWorkstep trnsRefColumn: "+trnsRefColumn);
				if(trnsRefColumn != null && !trnsRefColumn.isEmpty()){
					trnsRefVal = xp.getValueOf(trnsRefColumn);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"UpdateTargetWorkstep trnsRefVal: "+trnsRefVal);
				}
			} catch (Exception e1) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"Exception: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e1);
			}
			if("X".equalsIgnoreCase(mode) || "R".equalsIgnoreCase(mode)){
				maturityFlag = "Greater";
			} else {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"maturityDate is: "+xp.getValueOf("MATURITY_FLAG"));
				maturityFlag = xp.getValueOf("MATURITY_FLAG");
			}
			mainCode = -1;
			String rule = "";
			String target1 = "";
			String target2 = "";
			String key = processType+"#"+requestCategory+"#"+requestType+"#"+billType+"#"+ifSight+"#"+discInst+"#"
					+settInst+"#"+maturityFlag;
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"get BRMS value from key: "+key);
			if(tfoBRMSMap.containsKey(key)) {
				list = tfoBRMSMap.get(key);
				rule = list.get(0);
				target1 = list.get(1);
				target2 = list.get(2);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"BRMS value from key: "+list.toString());
			}
			//DART 1132889 AYUSH
			if(("DC".equalsIgnoreCase(mode) || "DCR".equalsIgnoreCase(mode)) && "R2".equalsIgnoreCase(rule) 
					&& "Y".equalsIgnoreCase(ruleFlag)){
				targetWS = target1;
				if("ToDoList".equalsIgnoreCase(targetWS)){
					APCallCreateXML.APInsert("BPM_EVENTGEN_TXN_DATA", "INSERTIONDATETIME, WI_NAME, PROCESS_NAME, "
							+ "SOURCING_CHANNEL, REQUESTMODE, EXPIRY_DATE, STATUS_FLAG", 
							"SYSDATE,'"+wiName+"','TFO','TFO_RULES','X',to_date('"+maturityDate+"','yyyy-MM-dd HH24:MI:SS'),'N'", sessionId);
					outputXml = APCallCreateXML.APSelect("SELECT WI_NAME FROM EXT_TFO "
							+ "WHERE (TRANSACTION_REFERENCE = '"+ trnsRefVal +"' OR BILL_LN_REFRNCE = '"+trnsRefVal+"')"
									+ " AND CURR_WS = 'ToDoList' AND WI_NAME <> '"+wiName+"'");
					xp = new XMLParser(outputXml);
					mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
					if(mainCode == 0) {
						int start = xp.getStartIndex("Records", 0, 0);
						int deadEnd = xp.getEndIndex("Records", start, 0);
						int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
						int end = 0;
						for (int i = 0; i < noOfFields; ++i) {
							start = xp.getStartIndex("Record", end, 0);
							end = xp.getEndIndex("Record", start, 0);
							String moveWorkitemNo = xp.getValueOf("WI_NAME", start, end);
							if(!moveWorkitemNo.isEmpty()) {
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "moving to exit: " 
										+ moveWorkitemNo);
								try {
									APCallCreateXML.APUpdate("EXT_TFO", "TARGET_WORKSTEP", 
											"'A'", "WI_NAME = '"+moveWorkitemNo+"'", sessionId);
									ProdCreateXML.WMCompleteWorkItem(sessionId, moveWorkitemNo, 1);
								} catch (Exception e) {
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in "
											+ "RouteBRMSWorkitem moving to exit: ");
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
								}
							}
						}
					}
				}
			} else if("X".equalsIgnoreCase(mode) || "R".equalsIgnoreCase(mode)){
				/*if("ACTIVE".equalsIgnoreCase(trnStatus) && "FIN".equalsIgnoreCase(billStage)){
					targetWS = target2;
				} else {
					targetWS = "A";
				}*/
			//	targetWS = "A"; //sheenu
				targetWS = target2; //sheenu

				subRule = "Sub-Rule (SR1)";
			} 
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"mode: "+mode+", rule: "+rule+", targetWS: "+targetWS);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"target to be set from RouteBRMSWorkitem: "+targetWS);
			if(!targetWS.isEmpty() && "Y".equalsIgnoreCase(ruleFlag)){
				APCallCreateXML.APUpdate("EXT_TFO", "TARGET_WORKSTEP", "'"+targetWS+"'", "WI_NAME = '"+wiName+"'", sessionId);
				try {
					String action = "";
					String targetQueue = targetWS;
					if("A".equalsIgnoreCase(targetWS)){
						targetQueue = "Archival";
					}
					if(!subRule.isEmpty()){
						action = "Rule "+rule+" ("+subRule+") executed";
					} else {
						action = "Rule "+rule+" executed";
					}
					APCallCreateXML.APInsert("TFO_DJ_DEC_HIST", " WI_NAME,CURR_WS_NAME,USERNAME,CREATE_DATE,USER_ID,ACTION,REMARKS", 
							"'"+wiName+"','"+currWS+"','BPM-USER',SYSDATE,'BPM-USER','"+action+"',"
							+ "'Workitem routed to "+targetQueue+"'",sessionId);
				} catch (Exception e) {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in insert into dec hist: ");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
				}
			}
			if(("DC".equalsIgnoreCase(mode) || "DCR".equalsIgnoreCase(mode) || "X".equalsIgnoreCase(mode)) 
					&& "Y".equalsIgnoreCase(ruleFlag) && !targetWS.equalsIgnoreCase(currWS)){
				if("DC".equalsIgnoreCase(mode) || "DCR".equalsIgnoreCase(mode)){
					APCallCreateXML.APUpdate("EXT_TFO", "CURR_WS", "'Initiator'", "WI_NAME = '"+wiName+"'",
							sessionId);
				}
				if(targetWS.isEmpty() && ("DC".equalsIgnoreCase(mode) || "DCR".equalsIgnoreCase(mode))) {
					String trgtKey = processType+"#"+requestCategory+"#"+requestType;
//					if("EC_AM".equalsIgnoreCase(requestType) || "ELCB_AM".equalsIgnoreCase(requestType)){
//						targetWS = "PROCESSING MAKER";
//					} else {
//						targetWS = "PP_MAKER";
//					}
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"trgtKey: "+trgtKey);
					if(protradeTargetMap.get(trgtKey) != null) {
						targetWS = protradeTargetMap.get(trgtKey);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"targetWS: "+targetWS);
					} else {
						targetWS = "PP_MAKER";
					}
					APCallCreateXML.APUpdate("EXT_TFO", "TARGET_WORKSTEP", "'"+targetWS+"'", "WI_NAME = '"+wiName+"'", sessionId);
					// ATP-379 DATE 13-02-2024 REYAZ/Jamshed 
					if("PP_MAKER".equalsIgnoreCase(targetWS)) {
						DC_PROTRADE dc_pt_obj=new DC_PROTRADE();
						dc_pt_obj.autoSubmitPPM(customerId,wiName,requestCategory,sessionId); 
					}
					// ATP-379 DATE 13-02-2024 REYAZ/Jamshed ends
				}
				if(!"DCR".equalsIgnoreCase(mode)){
					String outputXmlComplete = ProdCreateXML.WMCompleteWorkItem(sessionId, wiName, 1);
					xp = new XMLParser(outputXmlComplete);
					mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
					if(mainCode == 0) {
						callStatus = "Y";
						returnCode = 0;
						errorDescription = "Success";
						errorDetail = "Workitem routed successfully";
					} else {
						callStatus = "N";
						returnCode = -1;
					}
				} else if(!targetWS.isEmpty()){
					callStatus = "Y";
					returnCode = 0;
					errorDescription = "Success";
				}
			} else if("N".equalsIgnoreCase(ruleFlag)){
				callStatus = "X";
				returnCode = 0;
			} else if(!targetWS.isEmpty()){
				callStatus = "Y";
				returnCode = 0;
				errorDescription = "Success";
			}
		}
		updateCallOutput("");
		finaloutputXml = "<returnCode>"+returnCode+"</returnCode><errorDescription>"+errorDescription+"</errorDescription>";
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXml: " +finaloutputXml);
		return finaloutputXml;
	}

	@Override
	public String createInputXML() throws Exception {
		return null;
	}

	@Override
	public String executeCall(HashMap<String, String> input) throws Exception {
		return call();
	}

	@Override
	public void executeDependencyCall() throws Exception {

	}

	@Override
	public void responseHandler(String outputXML, String inputXml)
			throws Exception {

	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		try {
			if(callStatus == "N"){
				returnCode = -1;
				errorDescription = "Failure";
				errorDetail = "Failed";
				errorDescription = "Routing Workitem failed";
			}
			if("R".equalsIgnoreCase(mode) || "DCR".equalsIgnoreCase(mode) || "DMR".equalsIgnoreCase(mode)){
				stageId = -1 * stageId;
			}
			String valList = "'"+ wiName +"',"+ stageId +", 'RouteBRMSWorkitem', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '', ''";
			APCallCreateXML.APInsert("BPM_ORCHESTRATION_STATUS", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
					+ "ERROR_DETAIL, STATUS, REASON", valList, sessionId);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, wiName, auditCallName, "LD100", e.getMessage(), sessionId);
		}	
	}

}
