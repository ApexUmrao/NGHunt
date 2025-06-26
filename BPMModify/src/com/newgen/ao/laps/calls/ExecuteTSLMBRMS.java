package com.newgen.ao.laps.calls;

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

public class ExecuteTSLMBRMS implements ICallExecutor, Callable<String>{
	
	 private String WI_NAME;
	  private int StageID;
	  private String sessionId;
	  private String profitCentre = "";
	  private String transactionAmount = "";
	  private String reqCategory = "";
	  private String callStatus;
	  private int returnCode;
	  private String errorDescription="";
	  private String errorDetail;
	  private String status;
	  private String reason;
	  private String refNo;
	  private boolean updateCheckFlag = true;
	  private String auditCallName = "ExecuteTSLMBRMS";
	  private String sourcingChannel;
	  private String CID;
	  private String utcEligible;
	  private String sUserName;
	  private String engineName;

	  public ExecuteTSLMBRMS( String sessionId, String WI_NAME, String profitCentre, String transactionAmount, String reqCategory)
	  {
		  this.WI_NAME = WI_NAME;
		  this.sessionId = sessionId;
		  this.engineName=LapsConfigurations.getInstance().CabinetName;
		  this.sUserName = LapsConfigurations.getInstance().UserName;
		  this.profitCentre=profitCentre;
		  this.transactionAmount=transactionAmount;
		  this.reqCategory=reqCategory;
		 
		  LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"INSIDE ExecuteTSLMBRMS");
			  if(profitCentre == null||transactionAmount==null||reqCategory==null){
					  this.updateCheckFlag = false;
				        callStatus = "N";
			  }
			  LapsModifyLogger.logMe(1, "profitCentre" + profitCentre);
			  LapsModifyLogger.logMe(1, "transactionAmount" + transactionAmount);
			  LapsModifyLogger.logMe(1, "reqCategory" + reqCategory);
	  }

	@Override
	public String call() throws Exception {
	    LapsModifyLogger.logMe(1, "ExecuteTSLMBRMS call: inside");
	    
	    LapsModifyLogger.logMe(1, "ExecuteTSLMBRMS call: this.updateCheckFlag"+this.updateCheckFlag);
	    String inputXml = "";
	    String outputXml = "";
	    try
	    {
	      if (this.updateCheckFlag) {
	        inputXml = createInputXML();
	        outputXml =  LapsConfigurations.getInstance().socket.connectToSocket(inputXml);
	        LapsModifyLogger.logMe(1, "ExecuteTSLMBRMS outputXml ===> " + outputXml);
	        if ((outputXml == null) || (outputXml.equalsIgnoreCase(""))) {
	          outputXml = "<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>";
	        }
	        responseHandler(outputXml, inputXml);
	        outputXml = outputXml + "<CallStatus>" + this.callStatus + "</CallStatus><CallResponse>" + this.errorDescription + 
	          "</CallResponse><returnCode>" + this.returnCode + "</returnCode><errorDescription>" + this.errorDescription + 
	          "</errorDescription><errorDetail></errorDetail><Reason></Reason><Status>" + this.status + "</Status>";
	        LapsModifyLogger.logMe(1, "ExecuteTSLMBRMS outputXml ===> " + outputXml);
	      }else {
				callStatus = "F";
				errorDescription = "BRMS Execution failed";
				returnCode = 1;
				updateCallOutput(inputXml);
			}
	    } catch (Exception e) {
	      LapsModifyLogger.logMe(2, e);
	      throw new Exception(e.getMessage());
	    }

	    return outputXml;
	  }

	@Override
	public String createInputXML() throws Exception {
		this.refNo = LapsUtils.getInstance().generateSysRefNumber();
		StringBuilder inputXML = new StringBuilder();
		try{
			inputXML.append("<?xml version=\"1.0\"?>").append("\n")
					.append("<APWebService_Input>").append("\n")
					.append("<Option>WebService</Option>").append("\n")
					.append("<Calltype>WS-2.0</Calltype>").append("\n")
					.append("<InnerCallType>WBG_BRMS_RULES_RESPONSE</InnerCallType>").append("\n")
					.append("<serviceName>InqBusinessRules</serviceName>").append("\n")
					.append("<serviceAction>Inquiry</serviceAction>").append("\n")
					.append("<REF_NO>" + refNo + "</REF_NO>").append("\n")
					.append("<WIName>" + WI_NAME + "</WIName>").append("\n")
					.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
					.append("<EngineName>" + LapsConfigurations.getInstance().CabinetName + "</EngineName>").append("\n")
					.append("<SENDERID>UTC</SENDERID>").append("\n").append(" <username>appuser</username>").append("\n")
					.append("<credentials>appuser</credentials>").append("\n")
					.append("<InqBusinessRulesReq>")
					.append("<ruleFlowGroup>TFO-UTC</ruleFlowGroup>").append("\n")
					.append("<requestChannelName>WMS</requestChannelName>").append("\n")
					.append("<Eligibility>")
					.append("<PAYLOAD type=\"UTC_ELIGIBILITY\"><UTC_ELIGIBILITY>"
					+ "<REQUEST_CATEGORY>"+ reqCategory +"</REQUEST_CATEGORY>"
					+ "<PROFIT_CENTER_CODE>"+profitCentre+"</PROFIT_CENTER_CODE>"
					+ "<TRANSACTION_AMOUNT>"+ transactionAmount +"</TRANSACTION_AMOUNT></UTC_ELIGIBILITY></PAYLOAD>"
					+ "</Eligibility>").append("\n")
					.append("</InqBusinessRulesReq>")
					.append("</APWebService_Input>");
			LapsModifyLogger.logMe(1, "BRMS inputXML ===> " + inputXML.toString());
		}catch (Exception e) {
			LapsModifyLogger.logMe(2, e);
			LapsModifyDBLogger.logMe(2, this.WI_NAME, this.auditCallName, "BRMS", e, this.sessionId);
		}
		return inputXML.toString();
	}

	@Override
	public String executeCall(HashMap<String, String> input) throws Exception {
		// TODO Auto-generated method stub
		return call();
	}

	@Override
	public void executeDependencyCall() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void responseHandler(String outputXML, String inputXml)
			throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "ExecuteTSLMBRMS responseHandler inside ===> ");
		XMLParser xp = new XMLParser(outputXML);
		returnCode = Integer.parseInt(xp.getValueOf("returnCode", "1", true));
		errorDescription = xp.getValueOf("errorDescription", "", true);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "ExecuteTSLMBRMS inputXML ===> "+returnCode);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "ExecuteTSLMBRMS inputXML ===> "+errorDescription);

		try{
			if(returnCode == 0){
				callStatus = "Y";
				if(xp.getValueOf("STATUS_CODE").equalsIgnoreCase("0") ){
					if((xp.getValueOf("UTC_ELIGIBILITY_OUTPUT").toUpperCase()).equalsIgnoreCase("TRUE")){
						utcEligible = "Yes";
					} else if((xp.getValueOf("UTC_ELIGIBILITY_OUTPUT").toUpperCase()).equalsIgnoreCase("FALSE")){
						utcEligible = "No";
					}
				}
			}else{
				callStatus = "N";
				LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "BRMS", "ExecuteTSLMBRMS Failed", sessionId);
			}
			updateCallOutput(outputXML);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "BRMS", e, sessionId);
		}	
}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		 APCallCreateXML.APUpdate("EXT_TFO", "utc_required_brms", " " + utcEligible + " ", " WI_NAME = N'" + this.WI_NAME + "'", this.sessionId);
	     
		String valList = "'"+ WI_NAME +"','"+ sUserName +"','','PP_MAKER',sysdate,'','','BRMS Call Executed: " + errorDescription+ "',"
				+ "'',sysdate,'UTC Required BRMS " + utcEligible + "','',' ',' ',''";
		APCallCreateXML.APInsert("TFO_DJ_DEC_HIST", "WI_NAME,USER_ID,PRV_WS_NAME,CURR_WS_NAME,CREATE_DATE,USERNAME,USER_GROUPNAME,ACTION,REASON_FOR_ACTION,TRANSACTION_DATE_TIME,REMARKS,BRANCH,DUP_CHECK,ROUTE_TO,SEND_EMAIL_FLAG", valList, sessionId);
		
	}

}
