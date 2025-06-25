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

public class SuppArchival implements ICallExecutor, Callable<String>{
	private String wiName;
	private int stageId;
	private String sessionId;
	private String callStatus;
	private int returnCode;
	private String errorDescription;
	private String errorDetail;
	private String status;
	private String finalOutputXML = "";
	private String auditCallName = "SuppArchival";
	private boolean prevStageNoGo;
	private HashMap<String, String> defaultMap;
	private CallEntity callEntity;
	private String reason;
	boolean skip = false;

	public SuppArchival(Map<String, String> defaultMap, String sessionId, String stageId, String wiName, Boolean prevStageNoGo, CallEntity callEntity)
	{
		this.sessionId=sessionId;
		this.wiName = wiName;
		this.stageId = Integer.parseInt(stageId);
		this.prevStageNoGo = prevStageNoGo;
		this.defaultMap = (HashMap<String, String>) defaultMap;
		this.callEntity = callEntity;

	}

	@Override
	public String call() throws Exception {
		String inputXml = createInputXML();
		String outputXml = "";
		try{
			if(!skip){
				outputXml = ExecuteXML.executeXML(inputXml);
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SuppArchival outputXml ===> "+ outputXml.toString());
				errorDescription = "Successfully Executed";
				responseHandler(outputXml, inputXml);
			} else {
				callStatus = "X";
				updateCallOutput(inputXml);
			}

		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SuppArchival outputXml ===> "+ outputXml.toString());
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SuppArchival100", e, sessionId);
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
			.append("<channelName>ARCHIVAL</channelName>").append("\n")        
			.append("<correlationId>"+refNo+"</correlationId>").append("\n")
			.append("<channelRefNumber>"+refNo+"</channelRefNumber>").append("\n")  
			.append("<sysrefno>"+refNo+"</sysrefno>").append("\n")
			.append("<processName>DSCOP</processName>").append("\n")
			.append("</APWebService_Input>");         
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SuppArchival inputXML ===> "+sInputXML.toString());
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SuppArchival100", e, sessionId);
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
		String archivalWiName = "";
		try{
			if(outputXML != null && !"".equals(outputXML) ){
				XMLParser xp = new XMLParser(outputXML);
				int returnCode = Integer.parseInt(xp.getValueOf("StatusCode"));
				if (returnCode == 0) {
					callStatus = "Y";
					errorDescription = "SuppArchival Success";
					archivalWiName = xp.getValueOf("WINumber");
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Inside UPDATE e DATA IN EXT_DSCOP | archivalWiName:"+archivalWiName);
					APCallCreateXML.APUpdate("EXT_DSCOP", "ARCHIVAL_WI_NAME", 
							"'"+archivalWiName+"'", " WI_NAME = N'"+ wiName +"'", sessionId);
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "SuppArchival090", "SuppArchival Successfully Executed", sessionId);
				} else {
					callStatus = "N";
					finalOutputXML = "<returnCode>1</returnCode><errorDescription>FAIL</errorDescription>";
					errorDescription = "SuppArchival call failed";
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "SuppArchival101", "SuppArchival Failed", sessionId);
				}
			} else {
				callStatus = "N";
				finalOutputXML = "<returnCode>1</returnCode><errorDescription>FAIL</errorDescription>";
				errorDescription = "Empty response came from SuppArchival";
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "SuppArchival101", "SuppArchival Failed", sessionId);
			}
			updateCallOutput(inputXml);

		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SuppArchival100", e, sessionId);
		}
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		try{
			String valList = "'"+ wiName +"',"+ stageId +", 'SuppArchival', '"+ callStatus +"', SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"'";
			APCallCreateXML.APInsert("USR_0_DSCOP_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
					+ "ERROR_DETAIL, STATUS, REASON", valList, sessionId);
			if("Y".equalsIgnoreCase(callStatus)){
				ProdCreateXML.WMCompleteWorkItem(sessionId, wiName, 1);
				executeDependencyCall();
			}	
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SuppArchival100", e, sessionId);
		}
	}


}
