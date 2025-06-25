package com.newgen.cbg.calls;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import com.newgen.cbg.callhandler.CallEntity;
import com.newgen.cbg.callhandler.CallHandler;
import com.newgen.cbg.core.ICallExecutor;
import com.newgen.cbg.logger.DSCOPDBLogMe;
import com.newgen.cbg.logger.DSCOPLogMe;
import com.newgen.cbg.utils.APCallCreateXML;
import com.newgen.cbg.utils.DSCOPConfigurations;
import com.newgen.cbg.utils.DSCOPUtils;
import com.newgen.cbg.utils.ExecuteXML;
import com.newgen.cbg.utils.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;

public class SuppSanctionScreeningDigital implements ICallExecutor, Callable<String>{
	private String wiName;
	private int stageId;
	private String sessionId;
	private String callStatus;
	private int returnCode;
	private String errorDescription;
	private String errorDetail;
	private String status;
	private String finalOutputXML = "";
	private String auditCallName = "SANCTIONSCREENING";
	private boolean prevStageNoGo;
	private HashMap<String, String> defaultMap;
	private CallEntity callEntity;
	private String reason;
	private String trsdStatus;
	private String nameMissmatch;
	boolean skip = false;

	public SuppSanctionScreeningDigital(Map<String, String> defaultMap, String sessionId, String stageId, String wiName, Boolean prevStageNoGo, CallEntity callEntity)
	{
		this.sessionId=sessionId;
		this.wiName = wiName;
		this.stageId = Integer.parseInt(stageId);
		this.prevStageNoGo = prevStageNoGo;
		this.defaultMap = (HashMap<String, String>) defaultMap;
		this.callEntity = callEntity;
		try {
			handleScreeningData(fetchSanctionScreening());
		} catch (Exception e) {
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SANCTIONSCREENING100", e, sessionId);
		}

	}

	@Override
	public String call() throws Exception {
		String inputXml = createInputXML();
		String outputXml = "";
		try{
			if(!skip){
				outputXml = ExecuteXML.executeXML(inputXml);
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Sanction Screening outputXml ===> "+ outputXml.toString());
				errorDescription = "Successfully Executed";
				responseHandler(outputXml, inputXml);
			} else {
				callStatus = "N";
				APCallCreateXML.APUpdate("EXT_DSCOP", "ROUTE_TO_REPAIR","'Y'", " WI_NAME = N'"+ wiName +"'", sessionId);
				updateCallOutput(inputXml);
			}

		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Sanction Screening outputXml ===> "+ outputXml.toString());
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SANCTIONSCREENING100", e, sessionId);
		}
		return finalOutputXML;
	}

	@Override
	public String createInputXML() throws Exception {
		String refNo;
		StringBuilder sInputXML = new StringBuilder();
		try {
			refNo = DSCOPUtils.getInstance().generateSysRefNumber();
			sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
			.append("<APWebService_Input>").append("\n")
			.append("<Option>WebService</Option>").append("\n")
			.append("<EngineName>" + DSCOPConfigurations.getInstance().CabinetName + "</EngineName>").append("\n")
			.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
			.append("<Calltype>WS-2.0</Calltype>").append("\n")
			.append("<InnerCallType>BPMModify</InnerCallType>").append("\n")              
			.append("<wiNumber>" +wiName + "</wiNumber>").append("\n")
			.append("<REF_NO>" + refNo + "</REF_NO>").append("\n")
			.append("<SENDERID>" + "WMS" + "</SENDERID>").append("\n")
			.append("<mode>C</mode>").append("\n")
			.append("<channelName>SANCTION_SCREENING</channelName>").append("\n")        
			.append("<correlationId>"+refNo+"</correlationId>").append("\n")
			.append("<channelRefNumber>"+refNo+"</channelRefNumber>").append("\n")  
			.append("<sysrefno>"+refNo+"</sysrefno>").append("\n")
			.append("<processName>DSCOP</processName>").append("\n")
			.append("</APWebService_Input>");         
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Sanction Screening inputXML ===> "+sInputXML.toString());
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SANCTIONSCREENING100", e, sessionId);
		}
		return sInputXML.toString();
	}

	@Override
	public String executeCall(HashMap<String, String> input) throws Exception {
		return call();
	}

	@Override
	public void executeDependencyCall() throws Exception {
		try{
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "SS003", "SS DependencyCall:"+callEntity.getDependencyCallID(), sessionId);
			finalOutputXML = CallHandler.getInstance().executeDependencyCall(callEntity, defaultMap,sessionId, wiName, prevStageNoGo);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SS100", e, sessionId);
		}
	}

	@Override
	public void responseHandler(String outputXML, String inputXml) throws Exception {
		String trsdScreeningDate = "";
		String trsdCaseId = "";
		String trsdWiName = "";
		try{
			if(outputXML != null && !"".equals(outputXML) ){
				XMLParser xp = new XMLParser(outputXML);
				int returnCode = Integer.parseInt(xp.getValueOf("StatusCode"));
				if (returnCode == 0) {
					callStatus = "Y";
					errorDescription = "SANCTIONSCREENING SUCCESS";
					try {
						trsdWiName = xp.getValueOf("WINumber");
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Inside UPDATE TRSD DATA IN EXT_DSCOP | trsdWIName:"+trsdWiName);
						String outputXml2 = APCallCreateXML.APSelect("SELECT TRSD_CASE_CODE, TRSD_SCREENING_DATE, TRSD_STATUS FROM BPM_TRSD_DETAILS "
								+ "WHERE BATCH_ID =(SELECT MAX(BATCH_ID) FROM BPM_TRSD_DETAILS WHERE WI_NAME = '"+trsdWiName+"') "
								+ "AND WI_NAME = '"+trsdWiName+"'");   
						XMLParser xp2 = new XMLParser(outputXml2);
						int mainCode2 = Integer.parseInt(xp2.getValueOf("MainCode"));
						if(mainCode2 == 0){
							trsdCaseId = xp2.getValueOf("TRSD_CASE_CODE");
							trsdScreeningDate = xp2.getValueOf("TRSD_SCREENING_DATE");
							trsdStatus = xp2.getValueOf("TRSD_STATUS");
							DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Inside UPDATE TRSD DATA IN EXT_DSCOP"
									+ " | trsdCaseID:"+trsdCaseId+" | trsdScreeningDate:"+trsdScreeningDate);
							APCallCreateXML.APUpdate("EXT_DSCOP", "TRSD_CASE_ID, TRSD_ASSESS_DATE", 
									"'"+trsdCaseId+"','"+trsdScreeningDate+"'", " WI_NAME = N'"+ wiName +"'", sessionId);
						}	
					} catch (Exception e) {
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
						DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SANCTIONSCREENING100", e, sessionId);
					}
					//COP-3423 PROD MOKSH 25/05/2023
					finalOutputXML = "<returnCode>0</returnCode><errorDescription>SANCTIONSCREENING SUCCESS</errorDescription>";
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "SANCTIONSCREENING090", "SANCTIONSCREENING Successfully Executed", sessionId);
				} else {
					callStatus = "N";
					finalOutputXML = "<returnCode>1</returnCode><errorDescription>FAIL</errorDescription>";
					errorDescription = "SANCTIONSCREENING call failed";
					APCallCreateXML.APUpdate("EXT_DSCOP", "ROUTE_TO_REPAIR","'Y'", " WI_NAME = N'"+ wiName +"'", sessionId);
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "SANCTIONSCREENING101", "SANCTIONSCREENING Failed", sessionId);
				}
			} else {
				callStatus = "N";
				finalOutputXML = "<returnCode>1</returnCode><errorDescription>FAIL</errorDescription>";
				errorDescription = "Empty response came from SANCTIONSCREENING";
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "SANCTIONSCREENING101", "SANCTIONSCREENING Failed", sessionId);
			}
			updateCallOutput(inputXml);

		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SANCTIONSCREENING100", e, sessionId);
		}
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		try{
		
				 APCallCreateXML.APUpdate("USR_0_DSCOP_CALL_OUT", "CALL_STATUS,ERROR_DESCRIPTION", "'Y','Processed by Expiry Utility'", " WI_NAME = N'"+ wiName +"' AND CALL_NAME = 'SuppSanctionScreeningDigital' AND CALL_STATUS ='N' and STAGE_ID= '"+stageId+"'", sessionId);
				
			String valList = "'"+ wiName +"',"+ stageId +", 'SuppSanctionScreeningDigital', '"+ callStatus +"', SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"'";
			APCallCreateXML.APInsert("USR_0_DSCOP_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
					+ "ERROR_DETAIL, STATUS, REASON", valList, sessionId);
			if("Y".equalsIgnoreCase(callStatus) && !"P".equalsIgnoreCase(trsdStatus)){
				executeDependencyCall();
			//	ProdCreateXML.WMCompleteWorkItem(sessionId, wiName, 1);
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SANCTIONSCREENING100", e, sessionId);
		}
	}	
	
	public String fetchSanctionScreening()throws Exception {
		return APCallCreateXML.APSelect("SELECT NAME_MISSMATCH FROM EXT_DSCOP  WHERE WI_NAME = N'" + wiName + "'");
	}
	
	private void handleScreeningData(String outputXML) throws Exception {
		XMLParser xp = new XMLParser(outputXML);
		int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		if (mainCode == 0 && Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0) {
			nameMissmatch = xp.getValueOf("NAME_MISSMATCH");
			if (nameMissmatch.equalsIgnoreCase("Y")) {
				skip = true;
			}
		}
	}

}
