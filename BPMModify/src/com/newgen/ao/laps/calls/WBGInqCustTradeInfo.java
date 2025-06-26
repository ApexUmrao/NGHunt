package com.newgen.ao.laps.calls;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.callhandler.CallHandler;
import com.newgen.ao.laps.core.ICallExecutor;
import com.newgen.ao.laps.logger.LapsModifyDBLogger;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;

public class WBGInqCustTradeInfo implements ICallExecutor, Callable<String>{
	private String WI_NAME;
	private int StageID;
	private String sessionId;
	private String callStatus;
	private int returnCode;
	private String errorDescription;
	private String errorDetail;
	private String status;
	private String reason;
	private String refNo;
	private String leadRefNumber ="";
	private boolean updateCheckFlag=true;
	private boolean prevStageNoGo;
	private Map<String, String> attributeMap;
	private CallEntity callEntity;
	private String auditCallName = "WBGInqCustTradeInfo";
	private String outputXML;
	private String custID;
	private String tradeLicenseNumber ="";
	private String customerEmail ="";
	private String customerType ="";
	private String businessType ="";
	private String businessLicenseExpiryDate ="";
	private String businessStartDate ="";
	private String customerNationality ="";
	String senderId = "WMSBPMENG";
	boolean isCallExecuted;
	
	 HashMap<String, String> attribMap = new HashMap<String, String>();

	 public WBGInqCustTradeInfo(Map<String, String> attributeMap, String sessionId, String stageId, String WI_NAME, Boolean prevStageNoGo, CallEntity callEntity) {
			this.WI_NAME = WI_NAME;
			this.StageID = Integer.parseInt(stageId);
			this.sessionId = sessionId;
			this.prevStageNoGo = prevStageNoGo;
			this.attributeMap = attributeMap;
			this.callEntity = callEntity;
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"defaultMap "+attributeMap);

//			String outputXML;
			try {
					outputXML = APCallCreateXML.APSelect("SELECT CUSTID FROM EXT_WBG_AO"
					+ " WHERE WI_NAME = N'" + WI_NAME + "'");
					XMLParser xp = new XMLParser(outputXML);
					int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
					LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "FTD001", "Started", sessionId);
					if(mainCode == 0){
						if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
							custID =  xp.getValueOf("CUSTID");
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"custID "+ custID);
						}
					}
				} catch (Exception e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
				LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "CUSTINFO", e, sessionId);
			}
		}
	 @Override
	 public String call() throws Exception {

		 LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Fetch TradeDetails call: inside" +prevStageNoGo);
		 String inputXml = "" ;
		 String outputXml = "<returnCode>0</returnCode>";		
		 inputXml = createInputXML();
		 callStatus = "Y";
		 isCallExecuted = LapsUtils.isItqanMCallExecuted(auditCallName,WI_NAME);
		 LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "isCallExecuted " + isCallExecuted);

		 try {			
			 if(!isCallExecuted){	
				 if(!prevStageNoGo && (!"".equalsIgnoreCase(custID) && null != custID)){
					 outputXml = LapsConfigurations.getInstance().socket.connectToSocket(inputXml);
					 LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "FetchDetails outputXml ===> " + outputXml);
					 if(outputXml==null || outputXml.equalsIgnoreCase("")){
						 callStatus = "F";
						 outputXml= "<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>";
					 }
					 responseHandler(outputXml, inputXml);
				 } else {
					 callStatus = "F";
					 returnCode = 1;
					 errorDescription = "Customer Id cannot be blank";
					 updateCallOutput(inputXml);
				 }
			 }
			 if(!callStatus.equalsIgnoreCase("F")){
					executeDependencyCall();
				}
			

		 } catch (Exception e) {
			 LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			 LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "TGCS100", e.getMessage(), sessionId);
		 }
		 if(returnCode == 2){
			 returnCode = 0;
		 }
		 outputXml = "<returnCode>"+returnCode+"</returnCode><errorDescription>"+errorDescription+"</errorDescription>";
		 LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXml: " +outputXml);
		 return outputXml;

	 }
		@Override
		public String createInputXML() throws Exception {StringBuilder inputXml=new StringBuilder();
		try {
			refNo = LapsUtils.getInstance().generateSysRefNumber();
			inputXml.append("<APWebService_Input>").append("\n")
			.append("<Option>WebService</Option>").append("\n")
			.append("<Calltype>WS-2.0</Calltype>").append("\n")
			.append("<InnerCallType>CustTradeInfo</InnerCallType>").append("\n")
			.append("<WIName>" + WI_NAME+ "</WIName>").append("\n")
			.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
			.append( "<EngineName>"+LapsConfigurations.getInstance().CabinetName+"</EngineName>").append("\n")
			.append( "<REF_NO>"+ refNo + "</REF_NO>").append("\n").append("\n")
			.append("<senderID>" + senderId + "</senderID>").append("\n")
			.append("<custId>" + custID + "</custId>").append("\n")
			.append("</APWebService_Input>");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, inputXml.toString());
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "TGCS100", e.getMessage(), sessionId);
		}
		return inputXml.toString();}

		@Override
		public String executeCall(HashMap<String, String> input) throws Exception {
			// TODO Auto-generated method stub
			return call();
		}

		@Override
		public void executeDependencyCall() throws Exception {
			try{
				LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, WI_NAME, auditCallName, "EIDA003", "EIDA DependencyCall:"+callEntity.getDependencyCallID(), sessionId);
				CallHandler.getInstance().executeDependencyCall(callEntity, attributeMap, sessionId, WI_NAME, prevStageNoGo);
			} catch (Exception e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
				LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "EIDA100", e, sessionId);
			}
		}
		
		@Override
		public void responseHandler(String outputXML, String inputXml)
				throws Exception {
			try {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "output xml inside responseHandler :" + outputXML);
				XMLParser xp = new XMLParser(outputXML);
				returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
				errorDescription = xp.getValueOf("errorDescription");
				errorDetail = xp.getValueOf("errorDetail");
				status = xp.getValueOf("Status");
				reason = xp.getValueOf("Reason");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "returnCode :" + returnCode);
				if(returnCode == 0 || returnCode == 2){
					callStatus = "Y";
					LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "FTD001", "Started", sessionId);
					        tradeLicenseNumber = xp.getValueOf("tradeLicenseNumber");
					        businessStartDate = xp.getValueOf("businessStartDate");
					        businessLicenseExpiryDate = xp.getValueOf("businessLicenseExpiryDate");
					        customerEmail = xp.getValueOf("customerEmail");
					        customerType = xp.getValueOf("customerType");
					        businessType = xp.getValueOf("businessType");
					        customerNationality = xp.getValueOf("customerNationality");
					        String  businessTypeXML = APCallCreateXML.APSelect("SELECT BIZ_DESC FROM USR_0_COB_BUSINESS"
									+ " WHERE BIZ_CODE = '" + businessType + "'");
							XMLParser xp1 = new XMLParser(businessTypeXML);
							int mainCode = Integer.parseInt(xp1.getValueOf("MainCode"));
							if(mainCode == 0){
								if(Integer.parseInt(xp1.getValueOf("TotalRetrieved")) > 0){
									businessType =  xp1.getValueOf("BIZ_DESC");
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"TYPE OF BUSINESS "+ businessType);
								}
							}
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "custFullName :" );
				} else {
					callStatus = "F";
					returnCode = 1;
					 errorDescription = "Fetch Customer Trade details failed";
				}		 
				updateCallOutput(inputXml);
			} catch (Exception e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			//	LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, auditCallName, "TGCS100", e.getMessage(), sessionId);
			}
			
		}

		@Override
		public void updateCallOutput(String custID) throws Exception {
			if(LapsUtils.isCallNameInBpmCallOut(auditCallName,WI_NAME)){
				String valList = "'"+ callStatus +"','"+ returnCode +"','" + errorDescription +"'";
//				APCallCreateXML.APUpdate("BPM_CALL_OUT", "CALL_STATUS,RETURN_CODE,ERROR_DESCRIPTION",valList, " WI_NAME = N'"+ winame +"'  AND CALL_STATUS = '"+auditCallName+"'", sessionId);
				String sTable_info = "BPM_CALL_OUT";
				String sColumn = "CALL_STATUS,RETURN_CODE,ERROR_DESCRIPTION";
				String sWhere = "wi_name = N'" + WI_NAME + "' AND CALL_NAME = '"+auditCallName+"'";

				APCallCreateXML.APUpdate(sTable_info, sColumn, valList, sWhere, sessionId);

			} else {
			try {
				String valueList = "'"+ WI_NAME +"',"+ StageID +",'" + auditCallName +"', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"'";
				APCallCreateXML.APInsert("BPM_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
						+ "ERROR_DETAIL, STATUS, REASON", valueList, sessionId);
				//APCallCreateXML.APUpdate("EXT_WBG_AO", "EIDA_CHKSUM_FLAG", " "+ callStatus +" ", " WI_NAME = N'"+ wiName +"'", sessionId);
				String noResultsFoundMsg = "";
				String sActivityName = "";
				String reasonForAction = "" ;
				String action = "";

				if(callStatus == "Y"){
					noResultsFoundMsg = "SUCCESS";
					sActivityName = "Introduction";
					reasonForAction = "Trade Details";
					action = "Fetch Trade Details Success";
					errorDescription = "Fetch Trade Details Successful";

				} else {
					noResultsFoundMsg = "FAILURE";
					sActivityName = "Introduction";
					reasonForAction = "Trade Details";
					action = "Fetch Trade Details Failed";

					errorDescription = "Fetch Trade Details Failed";
					APCallCreateXML.APUpdate("EXT_WBG_AO", "REPAIR_FLAG", "'Y'", "WI_NAME = '"+WI_NAME+"'", 
							sessionId);
					APCallCreateXML.APUpdate("EXT_WBG_AO", "RETRY_FLAG", "'Y'", "WI_NAME = '"+WI_NAME+"'", 
							sessionId);
					ProdCreateXML.WMCompleteWorkItem(sessionId, WI_NAME, 1);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"leadRefNumber "+leadRefNumber);
				}
				String valList = "'"+ WI_NAME +"','"+ leadRefNumber +"','"+ sActivityName +"', '"+ action +"', '"+ noResultsFoundMsg +"'";
				APCallCreateXML.APInsert("USR_0_WBG_AO_DEC_HIST", "WI_NAME,LEAD_REFNO, WS_NAME, WS_DECISION,WS_COMMENTS", valList, sessionId);

				if(callStatus.equals("Y")){
					
						String columnList = "TLNO,TLISSDT,TLEXPDT,EMAIL,INCDT,RELTYPE,TYPEOFBUSS,CNTRYINCP";
						String valList3 = "'"+tradeLicenseNumber+"','"+businessStartDate+"','"+businessLicenseExpiryDate+"','"+customerEmail+"',"
								+ "'"+businessStartDate+"','"+customerType+"','"+businessType+"','"+customerNationality+"'";
						APCallCreateXML.APUpdate("EXT_WBG_AO", columnList, valList3, " WI_NAME = '"+WI_NAME+"'", sessionId);
				} 
//				else {
//					returnCode = -1;
//				}

			} catch (Exception e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			}
		}
		
			
		}
}
