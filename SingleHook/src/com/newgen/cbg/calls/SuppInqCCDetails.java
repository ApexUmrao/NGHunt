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
import com.newgen.omni.jts.cmgr.XMLParser;


public class SuppInqCCDetails implements ICallExecutor, Callable<String>{
	private String sessionId;
	private String wiName;
	private String callStatus;
	private int stageID;
	private int returnCode;
	private String errorDetail;
	private String errorDescription;	
	private String status;
	private String reason;
	private String refNo;
	private String senderID;
	private boolean prevStageNoGo;
	private Map<String, String> defaultMap;
	private CallEntity callEntity;
	private String auditCallName = "SICCD";
	private String finalOutputXml;
	private boolean isCallRequired  = false;
	int sMainCode;
	private String cardNumber;
	private String associateCard;
	public SuppInqCCDetails(Map<String, String> defaultMap, String sessionId, String stageId, String wiName, Boolean prevStageNoGo, CallEntity callEntity){
		this.wiName = wiName;
		stageID = Integer.parseInt(stageId);
		this.sessionId = sessionId;
		this.prevStageNoGo = prevStageNoGo;
		this.defaultMap = defaultMap;
		this.callEntity = callEntity;
		
		String outputXML;
		try {
			outputXML = fetchInqCreditCardData();
			handleInqCreditCardData(outputXML);	
			if("Credit".equalsIgnoreCase(associateCard))
			{
				isCallRequired = true;
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SICCD100", e, sessionId);
		}
	}

	@Override
	public String call() throws Exception {
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"SuppInqCCDetails call: inside");
		String inputXml = "" ;
		finalOutputXml = "<returnCode>0</returnCode>";
		try {			
			if(!prevStageNoGo){
				if(isCallRequired){
					inputXml = createInputXML();
					finalOutputXml = ExecuteXML.executeWebServiceSocket(inputXml);
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SuppInqCCDetails outputXml ===> " + finalOutputXml);
					if(finalOutputXml==null || finalOutputXml.equalsIgnoreCase("")){
						finalOutputXml= "<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>";
					}
					responseHandler(finalOutputXml, inputXml);
				}
				else {
					callStatus = "X";
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "SICCD105", "SuppInqCCDetails Skip", sessionId);
					updateCallOutput(inputXml);
				}
			}	
			else {
				callStatus = "N";
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "SICCD105", "SuppInqCCDetails Failed", sessionId);
				updateCallOutput(inputXml);
				
			}

		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SICCD100", e, sessionId);
		}
		return finalOutputXml;
	}

	@Override
	public String createInputXML() throws Exception {
		StringBuilder inputXml=new StringBuilder();
		try {
			refNo = DSCOPUtils.getInstance().generateSysRefNumber();
			inputXml.append("<?xml version=\"1.0\"?>").append("\n")
			.append("<APWebService_Input>").append("\n")
			.append("<Option>WebService</Option>").append("\n")
			.append("<EngineName>" + DSCOPConfigurations.getInstance().CabinetName + "</EngineName>").append("\n")
			.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
			.append("<WINAME>" + wiName + "</WINAME>").append("\n")
			.append("<Calltype>DSCOP</Calltype>").append("\n")
			.append("<DSCOPCallType>InqCCDetails</DSCOPCallType>").append("\n")			
			.append("<REF_NO>" + refNo + "</REF_NO>").append("\n")
			.append("<OLDREF_NO>"+ refNo +"</OLDREF_NO>").append("\n")
			.append("<senderID>" + senderID + "</senderID>").append("\n")
			.append("<creditCardNumber>"+ cardNumber +"</creditCardNumber>").append("\n")
			.append("</APWebService_Input>");
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, inputXml.toString());
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SICCD100", e, sessionId);
		}
		return inputXml.toString();
	}

	@Override
	public String executeCall(HashMap<String, String> input) throws Exception {
		return call();
	}

	@Override
	public void executeDependencyCall() throws Exception {
		try {
			String outputXMLTemp;
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "SICCD004", "AS DependencyCall:"+ callEntity.getDependencyCallID(), sessionId);
			outputXMLTemp = CallHandler.getInstance().executeDependencyCall(callEntity, defaultMap, sessionId, wiName, prevStageNoGo);
			if(!outputXMLTemp.isEmpty()) {
				finalOutputXml=outputXMLTemp;
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SICCD100", e, sessionId);
		}
	}

	@Override
	public void responseHandler(String outputXML, String inputXml) throws Exception {
		try {
			XMLParser xp = new XMLParser(outputXML);
			returnCode = Integer.parseInt(xp.getValueOf("returnCode", "1", true));
			errorDescription = xp.getValueOf("errorDescription", "", true);
			errorDetail = xp.getValueOf("errorDetail", "", true);
			status = xp.getValueOf("Status", "", true);
			reason = xp.getValueOf("Reason", "", true);
			if(returnCode == 0){
				callStatus = "Y";
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "SICCD090", "SuppInqCCDetails Successfully Executed", sessionId);
				String statusPrimaryCard = xp.getValueOf("cardBlockCode");    
				 APCallCreateXML.APUpdate("EXT_DSCOP_EXTENDED","STATUS_PRIMARY_CARD", "'"+statusPrimaryCard+"'", " WI_NAME = '"+wiName+"'", sessionId);
			} else {
				callStatus = "N";
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "SICCD101", "SuppInqCCDetails Failed", sessionId);
			}		 
			updateCallOutput(inputXml);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SICCD100", e, sessionId);
		}		
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		try {
			String valList = "'"+ wiName +"',"+ stageID +", 'SuppInqCCDetails', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"'";
			APCallCreateXML.APInsert("USR_0_DSCOP_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
					+ "ERROR_DETAIL, STATUS, REASON", valList, sessionId);

			if(callStatus.equals("Y")){

				String valList2 = "'"+ wiName +"',"+ stageID +", 'SuppInqCCDetails', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml +"', 0";
				APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON,"
						+ " RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);
			}
			else {
				String valList2 = "'"+ wiName +"',"+ stageID +", 'SuppInqCCDetails', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml +"', 1";
				APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON, "
						+" RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);
				prevStageNoGo = true;
			}

		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SICCD100", e, sessionId);
		}
	}public String fetchInqCreditCardData() throws Exception{
		return APCallCreateXML.APSelect("SELECT A.ASSOCIATE_CARD,B.PRIMARY_CARD " 
				+ " FROM EXT_DSCOP A, EXT_DSCOP_EXTENDED B "
				+ " WHERE A.WI_NAME = B.WI_NAME AND A.WI_NAME =  N'" + wiName + "' ");
	}

	public void handleInqCreditCardData(String outputXML) throws Exception{

		XMLParser xp1 = new XMLParser(outputXML);
		sMainCode = Integer.parseInt(xp1.getValueOf("MainCode"));
		DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "PMCC001", "CCSSOAddCardCCPS Started2", sessionId);
		if(sMainCode == 0 && Integer.parseInt(xp1.getValueOf("TotalRetrieved")) > 0){
			cardNumber = xp1.getValueOf("PRIMARY_CARD");
			associateCard = xp1.getValueOf("ASSOCIATE_CARD");
			senderID ="MIB";
			}

	} 
}
