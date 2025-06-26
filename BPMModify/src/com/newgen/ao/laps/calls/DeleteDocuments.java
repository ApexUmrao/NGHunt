package com.newgen.ao.laps.calls;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.core.ICallExecutor;
import com.newgen.ao.laps.logger.LapsModifyDBLogger;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.omni.jts.cmgr.XMLParser;

public class DeleteDocuments implements ICallExecutor, Callable<String>{
	private String sessionId;
	private Map<String, String> defaultMap;
	private String channelRefNo;
	private String correlationId;
	private String wiName;
	private int stageId;
	private String mode = "";
	private String auditCallName = "DD";
	private String callStatus = "N";
	private int returnCode = 0;
	private String errorDetail = "Success";
	private String errorDescription = "Documents deleted successfully";	
	private String status;
	private String reason;

	public DeleteDocuments(Map<String, String> defaultMap, String sessionId, String stageId, String wiName, Boolean prevStageNoGo, CallEntity callEntity){
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside DeleteDocuments >>");
		this.sessionId = sessionId;
		this.defaultMap = defaultMap;
		this.mode = defaultMap.get("mode");
		this.wiName = wiName;
		this.stageId = Integer.parseInt(stageId);
		try {
			String outputXml = APCallCreateXML.APSelect("select PRO_TRADE_REF_NO, CORRELATIONID from ext_tfo "
					+ "where WI_NAME = '"+wiName+"'");
			XMLParser xp = new XMLParser(outputXml);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if (mainCode == 0) {
				this.channelRefNo = xp.getValueOf("PRO_TRADE_REF_NO");
				this.correlationId = xp.getValueOf("CORRELATIONID");
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in DeleteDocuments: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
	}
	
	@Override
	public String call() throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside DeleteDocuments call>>");
		String inputXml = "" ;
//		String fromDocumentPath = System.getProperty("user.dir")+File.separatorChar
		String fromDocumentPath = File.separatorChar+LapsConfigurations.getInstance().docFolderName+File.separatorChar
				+channelRefNo;
//				+File.separatorChar+LapsConfigurations.getInstance().ProTradeReceiptDoc;
//		String documentPathCustDocs = System.getProperty("user.dir")+File.separatorChar
//				+LapsConfigurations.getInstance().docFolderName+File.separatorChar+channelRefNo
//				+File.separatorChar+LapsConfigurations.getInstance().ProTradeCustomerDoc;
//		String toChannelRefPath = System.getProperty("user.dir")+File.separatorChar
		String toChannelRefPath = File.separatorChar+LapsConfigurations.getInstance().moveDocFolderName+File.separatorChar+channelRefNo
				+"_"+correlationId;
		File file = new File(toChannelRefPath);
		if(!file.exists()){
			file.mkdir();
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "new directory created");
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "fromChannelRefPath: "+fromDocumentPath);
//		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "fromChannelRefPath: "+documentPathCustDocs);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "toChannelRefPath: "+toChannelRefPath);
//		File fromEReceipt = new File(documentPathEReceipt);
		File from = new File(fromDocumentPath);
		File to = new File(toChannelRefPath);
		try {
			Files.move(from.toPath(), to.toPath(), StandardCopyOption.REPLACE_EXISTING);
//			Files.move(fromCustDocs.toPath(), to.toPath(), StandardCopyOption.REPLACE_EXISTING);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "directory moved ");
			callStatus = "Y";
			Boolean deleteDirctry = LapsUtils.deleteDirectory(new File(fromDocumentPath));
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "original directory deleted ");
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in delete document: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		updateCallOutput(inputXml);
		String outputXml = "<returnCode>"+returnCode+"</returnCode><errorDescription>"+errorDescription+"</errorDescription>";
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXml: " +outputXml);
		return outputXml;
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
	public void responseHandler(String outputXML, String inputXml) throws Exception {
		
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		try {
			if(callStatus == "N"){
				returnCode = -1 ;
				errorDetail = "Failed";
				errorDescription = "Document Deletion failed";
			}
			if("R".equalsIgnoreCase(mode) || "DCR".equalsIgnoreCase(mode) || "DMR".equalsIgnoreCase(mode)){
				stageId = -1 * stageId;
			}
			String valList = "'"+ wiName +"',"+ stageId +", 'DeleteDocuments', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"'";
			APCallCreateXML.APInsert("BPM_ORCHESTRATION_STATUS", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
					+ "ERROR_DETAIL, STATUS, REASON", valList, sessionId);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, wiName, auditCallName, "LD100", e.getMessage(), sessionId);
		}	
	}

}
