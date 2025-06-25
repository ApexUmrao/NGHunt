/**
 * BUG-ID		Changed DT		Changed By		Description
 */
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
import com.newgen.omni.wf.util.excp.NGException;

public class SuppCustomerDormantAcc implements ICallExecutor, Callable<String> {
	private String sessionId;
	private String wiName;
	private String primaryCid ;
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
	private String auditCallName = "CPD";
	private boolean skipCall = false;
	String outputXml;
	

	public SuppCustomerDormantAcc(Map<String, String> defaultMap, String sessionId, String stageId, String wiName, Boolean prevStageNoGo, CallEntity callEntity){
		this.wiName = wiName;
		stageID = Integer.parseInt(stageId);
		this.sessionId = sessionId;
		this.prevStageNoGo = prevStageNoGo;
		this.defaultMap = defaultMap;
		this.callEntity = callEntity;
		primaryCid=defaultMap.get("primaryCid");
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"SuppCardInformation call: primaryCid"+primaryCid);	
		try {
			fetchDormant();
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "CPD100", e, sessionId);
		}
	}

	@Override
	public String call() throws Exception {
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"SuppCustomerDormantAcc call: inside");
		String inputXml = "" ;
	    outputXml = "<returnCode>0</returnCode>";
		try {
			if (!skipCall) {
				inputXml = createInputXML();
				if (!prevStageNoGo) {
					outputXml = ExecuteXML.executeWebServiceSocket(inputXml);
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SuppCustomerDormantAcc outputXml ===> " + outputXml);
					if (outputXml == null || outputXml.equalsIgnoreCase("")) {
						outputXml = "<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>";
					}
					responseHandler(outputXml, inputXml);
				} else {
					callStatus = "N";
					updateCallOutput(inputXml);
				}
			} else {
				callStatus = "X";
				updateCallOutput("");
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "CPD100", e, sessionId);
		}
		return outputXml;
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
			.append("<Calltype>DSCOP</Calltype>").append("\n")
			.append("<DSCOPCallType>CustomerPersonalDetailsDA</DSCOPCallType>").append("\n")			
			.append("<WiName>" + wiName + "</WiName>").append("\n")
			.append("<REF_NO>" + refNo + "</REF_NO>").append("\n")
			.append("<OLDREF_NO>" + refNo + "</OLDREF_NO>").append("\n")
			.append("<senderId>" + senderID + "</senderId>").append("\n")
			.append("<CUST_ID>" + primaryCid + "</CUST_ID>").append("\n")	
			.append("</APWebService_Input>");
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, inputXml.toString());
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "CPD100", e, sessionId);
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
			String outputXMLTemp = "";
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "CPD004", "AS DependencyCall:"+ callEntity.getDependencyCallID(), sessionId);
			outputXMLTemp = CallHandler.getInstance().executeDependencyCall(callEntity, defaultMap, sessionId, wiName, prevStageNoGo);
			if(!outputXMLTemp.isEmpty()) {
				outputXml=outputXMLTemp;
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "CPD100", e, sessionId);
		}
	}

	@Override
	public void responseHandler(String outputXML, String inputXml) throws Exception {
		String dormantCustomer;
		try {
			XMLParser xp = new XMLParser(outputXML);
			returnCode = Integer.parseInt(xp.getValueOf("returnCode", "1", true));
			errorDescription = xp.getValueOf("errorDescription", "", true);
			errorDetail = xp.getValueOf("errorDetail", "", true);
			status = xp.getValueOf("Status", "", true);
			reason = xp.getValueOf("Reason", "", true);
			if (returnCode == 0) {
				callStatus = "Y";
				String custStatus = xp.getValueOf("custStatus");
				if("D".equalsIgnoreCase(custStatus)  || "I".equalsIgnoreCase(custStatus)) {
					dormantCustomer="Y";
				}
				else {
					dormantCustomer="N";
				}
				APCallCreateXML.APUpdate("EXT_DSCOP_EXTENDED", "DORMANT_CUSTOMER","'"+dormantCustomer+"'", " WI_NAME = '"+wiName+"'", sessionId);

				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "CPD090", "SuppCustomerDormantAcc Successfully Executed : CustomerDormant status : "+dormantCustomer, sessionId);

			} else {
				callStatus = "F";
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "CPD101", "SuppCustomerDormantAcc Failed", sessionId);
			}		 
			updateCallOutput(inputXml);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "CPD100", e, sessionId);
		}		
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		try {
			String valList = "'"+ wiName +"',"+ stageID +", 'SuppCustomerDormantAcc', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"'";
			APCallCreateXML.APInsert("USR_0_DSCOP_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
					+ "ERROR_DETAIL, STATUS, REASON", valList, sessionId);

			if(callStatus.equals("Y")){
				String valList2 = "'"+ wiName +"',"+ stageID +", 'SuppCustomerDormantAcc', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml +"', 0";
				APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "PRIMARY_CID, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON,"
						+ " RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);
				executeDependencyCall();
			}
			else {
				String valList2 = "'"+ wiName +"',"+ stageID +", 'SuppCustomerDormantAcc', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml +"', 1";
				APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON, "
						+" RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "CPD100", e, sessionId);
		}
	}
	public void fetchDormant() {
		int count = 0;
		String outputXMLNew = null;
		try {
			String query ="SELECT COUNT(1) AS COUNT FROM USR_0_DSCOP_CALL_OUT where WI_NAME = '"+wiName+"' AND CALL_NAME = 'SuppCustomerDormantAcc' "
					+ "AND CALL_STATUS = 'Y'";
			outputXMLNew = APCallCreateXML.APSelect(query);
		} catch (NGException e) {
			e.printStackTrace();
		}
		XMLParser xpNew = new XMLParser(outputXMLNew);
		int mainCodeNew = Integer.parseInt(xpNew.getValueOf("MainCode"));
		if (mainCodeNew == 0 && Integer.parseInt(xpNew.getValueOf("TotalRetrieved")) > 0) {
			count = Integer.parseInt(xpNew.getValueOf("COUNT"));
		}
		senderID = defaultMap.get("SENDER_ID");		
		if ("".equals(primaryCid) || count > 0) {
			skipCall = true;
		}

	}
}

