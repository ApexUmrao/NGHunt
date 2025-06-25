package com.newgen.ao.laps.calls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.callhandler.CallHandler;
import com.newgen.ao.laps.core.ICallExecutor;
import com.newgen.ao.laps.logger.LapsModifyDBLogger;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;

public class DeLinkDocuments implements ICallExecutor, Callable<String> {
	private String sessionId;
	private String WI_NAME;
	private String callStatus = "Y";
	private int stageID;
	private int returnCode = 0;
	private String errorDetail = "Success";
	private String errorDescription = "Documents Delinked successfully";	
	private String customerId;
	private String status;
	private String reason;
	private String channleRefNo;
	private boolean prevStageNoGo;
	private Map<String, String> defaultMap;
	private CallEntity callEntity;
	private String auditCallName = "DLD";
	private String mode = "";
	private String currWS = "Initiator";
	private String finaloutputXml =""; //ATP-496 29-07-2024 REYAZ

	public DeLinkDocuments(Map<String, String> defaultMap, String sessionId, String stageId, String WI_NAME, Boolean prevStageNoGo, CallEntity callEntity){
		this.WI_NAME = WI_NAME;
		stageID = Integer.parseInt(stageId);
		this.sessionId = sessionId;
		this.prevStageNoGo = prevStageNoGo;
		this.defaultMap = defaultMap;
		this.callEntity = callEntity;
		if(defaultMap.containsKey("mode")){
			this.mode = defaultMap.get("mode");
		}
		String outputXML;		
		try {
			outputXML = APCallCreateXML.APSelect("SELECT PRO_TRADE_REF_NO, CURR_WS FROM EXT_TFO WHERE WI_NAME = N'" + WI_NAME + "'");
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "DLD001", "Started", sessionId);
			if(mainCode == 0){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"FetchDetails TotalRetrieved: "+Integer.parseInt(xp.getValueOf("TotalRetrieved")));
				if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
					channleRefNo = xp.getValueOf("PRO_TRADE_REF_NO");
					if("R".equalsIgnoreCase(mode) || "DCR".equalsIgnoreCase(mode) || "DMR".equalsIgnoreCase(mode)){
						currWS = xp.getValueOf("CURR_WS");
					} else if("DM".equalsIgnoreCase(mode)){
						currWS = "CUSTOMER_REVIEW";
					}
					LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "DLD002", "Customer Id: "+customerId, sessionId);
				}
			}		
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "DLD100", e.getMessage(), sessionId);
		}
	}
	
	@Override
	public String call() throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"FetchDetails call: inside" +prevStageNoGo);
		String inputXml = "" , finaloutputXml = "<returnCode>0</returnCode>";
			if(!prevStageNoGo){
				modifyDocDetails(sessionId);
				updateCallOutput(inputXml);
			} else {
				callStatus="N";
				updateCallOutput(inputXml);
			}
		return finaloutputXml;
	}

	@Override
	public void executeDependencyCall() throws Exception {
		try {
			String outputXMLTemp = "";
			outputXMLTemp =CallHandler.getInstance().executeDependencyCall(callEntity, defaultMap, sessionId, WI_NAME, prevStageNoGo);
			if(!outputXMLTemp.isEmpty()){
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "DLD004", "DependencyCall"+ callEntity.getDependencyCallID(), sessionId);
				finaloutputXml = outputXMLTemp;
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "DLD100", e.getMessage(), sessionId);
		}
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		try {
			if("N".equals(callStatus)){
				returnCode = -1 ;
				errorDetail = "Failed";
				errorDescription = "Document Delinking failed";
			}
			if("R".equalsIgnoreCase(mode) || "DCR".equalsIgnoreCase(mode) || "DMR".equalsIgnoreCase(mode)){
				stageID = -1 * stageID;
			}
			String valList = "'"+ WI_NAME +"',"+ stageID +", 'DeLinkDocuments', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"'";
			APCallCreateXML.APInsert("BPM_ORCHESTRATION_STATUS", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
					+ "ERROR_DETAIL, STATUS, REASON", valList, sessionId);
			if("Y".equals(callStatus)){
				executeDependencyCall();
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "DLD100", e.getMessage(), sessionId);
		}	
	}

	@Override
	public String executeCall(HashMap<String, String> map) throws Exception {
		return call();
	} 
	
	private void modifyDocDetails(String sessionId){
		String sQuery;
		String outputXml;
		List<String> documentIndex; 
		String itemIndex = "";
		try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "modify Doc Details starts");
			sQuery = "SELECT DOC.DOCUMENTINDEX DOCUMENTINDEX, FOLDER.FOLDERINDEX ITEMINDEX FROM PDBDOCUMENT DOC, "
					+ "PDBDOCUMENTCONTENT DOCCONTENT , PDBFOLDER FOLDER "
					+ "WHERE DOC.DOCUMENTINDEX = DOCCONTENT.DOCUMENTINDEX AND "
					+ "DOCCONTENT.PARENTFOLDERINDEX = FOLDER.FOLDERINDEX  "
					+ "AND FOLDER.NAME  = '"+this.WI_NAME+"' AND DOC.NAME  = '"
					+LapsConfigurations.getInstance().ProTradeCustomerDoc+"'";
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "sQuery to get docindex is :"+sQuery);
			outputXml = APCallCreateXML.APSelect(sQuery);		
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"docindex outputxml " + outputXml);
			XMLParser xp = new XMLParser(outputXml);
			int start = xp.getStartIndex("Records", 0, 0);
			int deadEnd = xp.getEndIndex("Records", start, 0);
			int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
			int end = 0;
			documentIndex = new ArrayList<String>();
			for (int i = 0; i < noOfFields; ++i) {
				start = xp.getStartIndex("Record", end, 0);
				end = xp.getEndIndex("Record", start, 0);
				String docIndex = xp.getValueOf("DOCUMENTINDEX", start, end);
				documentIndex.add(docIndex);
				itemIndex = xp.getValueOf("ITEMINDEX", start, end);
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "documentIndex.size(): "+documentIndex.size());
			if(documentIndex.size()>0){
				String outputXmlDeLink = ProdCreateXML.DeleteDocument(sessionId, itemIndex, documentIndex);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "modify Doc Details ends");
				xp = new XMLParser(outputXmlDeLink);
				if ("0".equalsIgnoreCase(xp.getValueOf("Status")) || "0".equals(noOfFields)){
					insertDocDecisionHistory("Documents Delinked Successfully");
				} else{
					insertDocDecisionHistory("Documents Delinked Failed");
					callStatus = "N";
				}
			} else {
				insertDocDecisionHistory("No Document Found");
				callStatus = "Y";
			}
		}
		catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in M_PROTRADE dispatchEvent: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			callStatus = "N";
		}

	}
	public void insertDocDecisionHistory(String status) {
		try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside insertDocDecisionHistory");
			String valList = "'"+ WI_NAME +"','BPM-USER','"+currWS+"',SYSTIMESTAMP,'Document Delinking','"+status+"'";
			APCallCreateXML.APInsert("TFO_DJ_DEC_HIST", "WI_NAME, USERNAME, CURR_WS_NAME, CREATE_DATE, ACTION, REMARKS", valList, sessionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	@Override
	public String createInputXML() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void responseHandler(String outputXML, String inputXml)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
}
