package com.newgen.ao.laps.calls;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.core.ICallExecutor;
import com.newgen.ao.laps.logger.LapsModifyDBLogger;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.ExecuteXML;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class WBGEIDAChecksum
  implements ICallExecutor, Callable<String>
{
  private String WI_NAME;
  private int StageID;
  private String sessionId;
  private String EIDANum;
  private String authSign;
  private String callStatus;
  private int returnCode;
  private String errorDescription;
  private String errorDetail;
  private String status;
  private String reason;
  private String refNo;
  private String senderID ="WMS";
  private String leadRefNumber = "";
  private boolean updateCheckFlag = true;
  private boolean prevStageNoGo;
  private Map<String, String> attributeMap;
  private CallEntity callEntity;
  private String auditCallName = "EIDA_VALIDATION";
  private String sourcingChannel;
  private String CID;
  private String COMPLETE_FLAG;

  public WBGEIDAChecksum(Map<String, String> attributeMap, String sessionId, String stageId, String WI_NAME, Boolean prevStageNoGo, CallEntity callEntity)
  {
    this.WI_NAME = WI_NAME;
    this.StageID = Integer.parseInt(stageId);
    this.sessionId = sessionId;
    this.prevStageNoGo = prevStageNoGo.booleanValue();
    this.attributeMap = attributeMap;
    this.callEntity = callEntity;
    LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"INSIDE WBGEidachecksum");
    try
    {
      this.EIDANum = ((String)attributeMap.get("EIDA_NO"));
      this.COMPLETE_FLAG = ((String)attributeMap.get("COMPLETE_FLAG"));
      LapsModifyLogger.logMe(1, "EIDANum in EIDA Checksum===>" + this.EIDANum);
      LapsModifyLogger.logMe(1, "COMPLETE_FLAG in EIDA Checksum===>" + this.COMPLETE_FLAG);

      if ((this.EIDANum == null) || (this.EIDANum.equals(""))) {
        this.updateCheckFlag = false;
        callStatus = "N";
      }

      String soutputXML = APCallCreateXML.APSelect("SELECT LEAD_REF_NO FROM EXT_WBG_AO WHERE WI_NAME = N'" + WI_NAME + "'");
      XMLParser xp1 = new XMLParser(soutputXML);
      int mainCode1 = Integer.parseInt(xp1.getValueOf("MainCode"));
      LapsModifyDBLogger.logMe(1, WI_NAME, this.auditCallName, "EIDA001", "EIDADedupe Started", sessionId);
      if ((mainCode1 != 0) || 
        (Integer.parseInt(xp1.getValueOf("TotalRetrieved")) <= 0))
        return;
      this.leadRefNumber = xp1.getValueOf("LEAD_REF_NO");
      LapsModifyLogger.logMe(1, "leadRefNumber" + this.leadRefNumber);
      LapsModifyDBLogger.logMe(1, WI_NAME, this.auditCallName, "EIDA002", this.EIDANum, sessionId);
    }
    catch (Exception e)
    {
      LapsModifyLogger.logMe(2, e);
      LapsModifyDBLogger.logMe(2, WI_NAME, this.auditCallName, "EIDA100", e.getMessage(), sessionId);
    }
  }

  public String call()
    throws Exception
  {
    LapsModifyLogger.logMe(1, "WBGEIDAChecksum call: inside");
    String inputXml = "";
    String outputXml = "";
    try
    {
      if (this.updateCheckFlag) {
        inputXml = createInputXML();
        outputXml =  LapsConfigurations.getInstance().socket.connectToSocket(inputXml);
        LapsModifyLogger.logMe(1, "WBGEIDAChecksum outputXml ===> " + outputXml);
        if ((outputXml == null) || (outputXml.equalsIgnoreCase(""))) {
          outputXml = "<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>";
        }
        responseHandler(outputXml, inputXml);
        outputXml = outputXml + "<CallStatus>" + this.callStatus + "</CallStatus><CallResponse>" + this.errorDescription + 
          "</CallResponse><returnCode>" + this.returnCode + "</returnCode><errorDescription>" + this.errorDescription + 
          "</errorDescription><errorDetail></errorDetail><Reason></Reason><Status>" + this.status + "</Status>";
        LapsModifyLogger.logMe(1, "WBGEIDAChecksum outputXml ===> " + outputXml);
      }else {
			callStatus = "F";
			errorDescription = "EIDA No cannot be blank";
			returnCode = 1;
			updateCallOutput(inputXml);
		}
    } catch (Exception e) {
      LapsModifyLogger.logMe(2, e);
      throw new Exception(e.getMessage());
    }

    return outputXml;
  }

  public String createInputXML()
    throws Exception
  {
    StringBuilder inputXML = new StringBuilder();
    try {
      this.refNo = LapsUtils.getInstance().generateSysRefNumber();
      inputXML.append("<?xml version=\"1.0\"?>").append("\n")
        .append("<APWebService_Input>").append("\n")
        .append("<Option>WebService</Option>").append("\n")
        .append("<Calltype>WS-2.0</Calltype>").append("\n")
        .append("<InnerCallType>ValidateAUSEIDA</InnerCallType>").append("\n")
        .append("<useCase>Inquiry</useCase>").append("\n")
        .append("<Customer>").append("\n")
        .append("<CustID>" + CID + "</CustID>").append("\n")
        .append("<SENDERID>" + senderID + "</SENDERID>").append("\n")
        .append("<EIDA_No>" + EIDANum + "</EIDA_No>").append("\n")
        .append("<REF_NO>" + refNo + "</REF_NO>").append("\n")
        .append("<USER>System</USER>").append("\n")
        .append("<CREDENTIALS>****</CREDENTIALS>").append("\n")
        .append("<WiName>" + WI_NAME + "</WiName>").append("\n")
        .append("</Customer>").append("\n")
        .append("<EngineName>" + LapsConfigurations.getInstance().CabinetName + "</EngineName>").append("\n")
        .append("<SessionId>" + this.sessionId + "</SessionId>").append("\n")
        .append("</APWebService_Input>");
      LapsModifyLogger.logMe(1, "EIDA Checksum inputXML ===> " + inputXML.toString());
    } catch (Exception e) {
      LapsModifyLogger.logMe(2, e);
      LapsModifyDBLogger.logMe(2, this.WI_NAME, this.auditCallName, "EIDA100", e, this.sessionId);
    }
    return inputXML.toString();
  }

  public String executeCall(HashMap<String, String> input)
    throws Exception
  {
    return call();
  }

  public void executeDependencyCall()
    throws Exception
  {
  }

  public void responseHandler(String outputXML, String inputXml)
    throws Exception
  {
    try
    {
      XMLParser xp = new XMLParser(outputXML);
      this.returnCode = Integer.parseInt(xp.getValueOf("returnCode", "1", true));
      this.errorDescription = xp.getValueOf("errorDescription", "", true);
      this.errorDetail = xp.getValueOf("errorDetail", "", true);
      this.status = xp.getValueOf("Status", "", true);
      this.reason = xp.getValueOf("Reason", "", true);
      if (this.returnCode == 0) {
        this.callStatus = "Y";
        LapsModifyDBLogger.logMe(1, this.WI_NAME, this.auditCallName, "EIDA090", "EIDA Validation Successfully Executed - " + this.callStatus, this.sessionId);
      } else if (this.returnCode == 1) {
        this.callStatus = "X";
        LapsModifyDBLogger.logMe(1, this.WI_NAME, this.auditCallName, "EIDA090", "EIDA Validation Executed - " + this.callStatus, this.sessionId);
      } else {
        this.callStatus = "N";
        LapsModifyDBLogger.logMe(1, this.WI_NAME, this.auditCallName, "EIDA090", "EIDA Validation Executed - " + this.callStatus, this.sessionId);
      }
      updateCallOutput(this.EIDANum);
      LapsModifyLogger.logMe(1, "callStatus " + this.callStatus);
      LapsModifyLogger.logMe(1, "errorDescription " + this.errorDescription);
      LapsModifyLogger.logMe(1, "returnCode " + this.returnCode);
    }
    catch (Exception e) {
      LapsModifyLogger.logMe(2, e);
      LapsModifyDBLogger.logMe(2, this.WI_NAME, this.auditCallName, "EIDA100", e, this.sessionId);
    }
  }

  public void updateCallOutput(String EidaNumber)
    throws Exception
  {
    try
    {
      APCallCreateXML.APUpdate("EXT_WBG_AO", "EIDA_CHKSUM_FLAG", " " + this.callStatus + " ", " WI_NAME = N'" + this.WI_NAME + "'", this.sessionId);
      String noResultsFoundMsg = "";
      String sActivityName = "";
      String reasonForAction = "";
      String action = "";

 
      if (this.callStatus == "Y") {
        noResultsFoundMsg = "EIDA Validation Successful";
        sActivityName = "Introduction";
        reasonForAction = "Eida Validation for EIDA Number: " + EidaNumber;
        action = "EIDA Validation Successful";

      } else {
        noResultsFoundMsg = "EIDA Validation Failed";
        sActivityName = "Introduction";
        reasonForAction = "Eida Validation for EIDA Number: " + EidaNumber;
        action = "EIDA Validation Failed";
      }

      LapsModifyLogger.logMe(1, "leadRefNumber " + this.leadRefNumber);

      String valList = "'" + this.WI_NAME + "','" + this.leadRefNumber + "','" + sActivityName + "', '" + action + "', '" + reasonForAction + "', '" + noResultsFoundMsg + "'";
      APCallCreateXML.APInsert("USR_0_WBG_AO_DEC_HIST", "WI_NAME,LEAD_REFNO, WS_NAME, WS_DECISION, REJ_REASON, WS_COMMENTS", valList, this.sessionId);
    
       APCallCreateXML.APSelect("select prev_ws,curr_ws from "
		 		+ "ext_wbg_ao where WI_NAME =  N'" + WI_NAME + "'");
	
      ProdCreateXML.WMCompleteWorkItem(this.sessionId, this.WI_NAME, 1);
      
       APCallCreateXML.APSelect("select prev_ws,curr_ws from "
		 		+ "ext_wbg_ao where WI_NAME =  N'" + WI_NAME + "'");
	

    }
    catch (Exception e)
    {
      LapsModifyLogger.logMe(2, e);
    }
  }
}