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

public class SuppGetDebitCardDetails implements ICallExecutor, Callable<String> {

	private String sessionId;
	private String wiName;
	private int stageId;
	private String callStatus;
	private int returnCode;
	private String errorDescription;
	private String errorDetail;
	private String status;
	private String reason;
	private String refNo;
	private String primaryCid;
	private String associatedCard; //Added by Shivanshu For Skipping Debit Info Call
	private String senderID;
	private boolean prevStageNoGo;
	private Map<String, String> defaultMap;
	private CallEntity callEntity;
	private String auditCallName = "SGDC";
	private String customerType = "ETB";
	String outputXML ;
	public SuppGetDebitCardDetails(Map<String, String> defaultMap, String sessionId, String stageId, String wiName, Boolean prevStageNoGo, CallEntity callEntity) {
		this.sessionId=sessionId;
		this.wiName = wiName;
		this.stageId = Integer.parseInt(stageId);
		this.prevStageNoGo = prevStageNoGo;
		this.defaultMap = defaultMap;
		this.callEntity = callEntity;
		primaryCid=defaultMap.get("primaryCid");
		associatedCard = defaultMap.get("associatedCard"); //Added by Shivanshu For Skipping Debit Info Call
		try {
			senderID = defaultMap.get("SENDER_ID");
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SGDC100", e, sessionId);
		}
	}

	@Override
	public String call() throws Exception {
		String inputXml = createInputXML();
		String outputXML = "<returnCode>0</returnCode>";
		try {
			if("ETB".equalsIgnoreCase(customerType) && "Debit".equalsIgnoreCase(associatedCard)){ //Added by Shivanshu For Skipping Debit Info Call
				if(!prevStageNoGo){
					outputXML = ExecuteXML.executeWebServiceSocket(inputXml);
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SuppGetDebitCardDetails outputXml ===> "+outputXML);
					if(outputXML==null ||outputXML.equalsIgnoreCase("")){
						outputXML= "<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>";
					}
					responseHandler(outputXML,inputXml);
				} else {
					callStatus = "N";
					updateCallOutput(inputXml);
				}
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "SGDC003", "SGDC output: "+outputXML, sessionId);

			}else {
				callStatus = "X";
				errorDescription="Debit Card Information Skipped";
				updateCallOutput(inputXml);
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SGDC100", e, sessionId);
		}
		return outputXML;
	}

	@Override
	public String executeCall(HashMap<String, String> input) throws Exception {
		return call();
	}

	@Override
	public void responseHandler(String outputXML, String inputXml) throws Exception {
		try {
			if(!prevStageNoGo){
				XMLParser xp = new XMLParser(outputXML);
				returnCode = Integer.parseInt(xp.getValueOf("returnCode", "1", true));
				errorDescription = xp.getValueOf("errorDescription", "", true);
				errorDetail = xp.getValueOf("errorDetail", "", true);
				status = xp.getValueOf("Status", "", true);
				reason = xp.getValueOf("Reason", "", true);
				if(returnCode == 0) {
					callStatus = "Y";
					int start = xp.getStartIndex("listOfDebitCards", 0, 0);
					int deadEnd = xp.getEndIndex("listOfDebitCards", start, 0);
					int noOfFieldS = xp.getNoOfFields("debitCards", start,deadEnd);
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "debitCards noOfFieldS : "+noOfFieldS);
					int end = 0;
					for(int i = 0; i < noOfFieldS; ++i){
						start = xp.getStartIndex("debitCards", end, 0);
						end = xp.getEndIndex("debitCards", start, 0);
						String cardNumber = xp.getValueOf("cardNumber", start, end);
						String cardType = xp.getValueOf("cardType", start, end);
						String cardCurrency = xp.getValueOf("cardCurrency", start, end);
						String cardTypeDescription = xp.getValueOf("cardTypeDescription", start, end);
						String debitProductGroup = xp.getValueOf("debitProductGroup", start, end);
						String embossName = xp.getValueOf("embossName", start, end);
						String linkedAccount = xp.getValueOf("linkedAccount", start, end);
						String linkedAccountType = xp.getValueOf("linkedAccountType", start, end);
						String status = xp.getValueOf("status", start, end);
						String colNames="DATETIME,WI_NAME,CARD_NUMBER,CARD_TYPE,CARD_CURRENCY,CARDTYPE_DESC,DEBIT_PRODUCTGROUP,EMBOSS_NAME,LINKED_ACCOUNT,LINKED_ACCOUNTTYPE,STATUS";
						String valNames="SYSDATE,'"+wiName+"','"+cardNumber+"','"+cardType+"','"+cardCurrency+"','"+cardTypeDescription+"','"+debitProductGroup+"','"+embossName+"','"+linkedAccount+"','"+linkedAccountType+"','"+status+"'";
						APCallCreateXML.APInsert("BPM_DSCOP_DEBITCARD_DETAILS", colNames, valNames, sessionId);

					}
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "SGDC090", "Debit Card Inquiry Success", sessionId);

				} else {
					callStatus = "N";
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "SGDC101", "Debit Card Inquiry Failed", sessionId);
				}
			} else {
				callStatus = "N";
			}
			updateCallOutput(inputXml);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SGDC100", e, sessionId);
		}			
	}

	@Override
	public String createInputXML() throws Exception {
		StringBuilder inputXML = new StringBuilder();
		try {
			refNo = DSCOPUtils.getInstance().generateSysRefNumber();
			inputXML.append("<?xml version=\"1.0\"?>").append("\n")
			.append("<APWebService_Input>").append("\n")
			.append("<Option>WebService</Option>").append("\n")
			.append("<Calltype>DSCOP</Calltype>").append("\n")
			.append("<DSCOPCallType>Debit_Card_Information</DSCOPCallType>").append("\n")
			.append("<REF_NO>" + refNo + "</REF_NO>").append("\n")
			.append("<OLDREF_NO>"+ refNo +"</OLDREF_NO>").append("\n")
			.append("<senderID>" + senderID + "</senderID>").append("\n")
			.append("<winame>" + wiName + "</winame>").append("\n")
			.append("<CUST_ID>" + primaryCid + "</CUST_ID>").append("\n")
			.append("<EngineName>" + DSCOPConfigurations.getInstance().CabinetName + "</EngineName>").append("\n")
			.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
			.append("</APWebService_Input>");
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SuppGetDebitCardDetails inputXML ===> "+inputXML.toString());
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SGDC100", e, sessionId);
		}
		return inputXML.toString();   
	}

	@Override
	public void executeDependencyCall() throws Exception{
		try{
			String outputXMLTemp="";
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "SGDC004", "GDC DependencyCall:"+callEntity.getDependencyCallID(), sessionId);
			outputXMLTemp = CallHandler.getInstance().executeDependencyCall(callEntity, defaultMap,sessionId, wiName, prevStageNoGo);
			if(!outputXMLTemp.isEmpty()) {
				outputXML=outputXMLTemp;
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SGDC100", e, sessionId);
		}
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		try {
			String valList = "'"+ wiName +"',"+ stageId +", 'SuppGetDebitCardDetails', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', ''";
			APCallCreateXML.APInsert("USR_0_DSCOP_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
					+ "ERROR_DETAIL, STATUS, REASON", valList, sessionId);

			if(callStatus.equals("Y")){
				String valList2 = "'"+ wiName +"',"+ stageId +", 'SuppGetDebitCardDetails', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml+"', 0";
				APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON,"
						+ " RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);
				executeDependencyCall();
			}else if (callStatus.equals("X")) {
				executeDependencyCall();
			}
			else {
				String valList2 = "'"+ wiName +"',"+ stageId +", 'SuppGetDebitCardDetails', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml+"', 1";
				APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON, "
						+" RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SGDC100", e, sessionId);
		}
	}
}
