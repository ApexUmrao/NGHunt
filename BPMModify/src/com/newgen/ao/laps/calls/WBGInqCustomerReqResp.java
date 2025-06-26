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
public class WBGInqCustomerReqResp implements ICallExecutor, Callable<String>{
	private String WI_NAME;
	private int StageID;
	private String sessionId;
	private String EIDANum;
	private String COMPLETE_FLAG;
	private String callStatus;
	private int returnCode;
	private String errorDescription;
	private String errorDetail;
	private String status;
	private String reason;
	private String refNo;
	private String leadRefNumber ="";
	private String senderID;
	private boolean updateCheckFlag=true;
	private boolean prevStageNoGo;
	private Map<String, String> attributeMap;
	 HashMap<String, String> attribMap = new HashMap<String, String>();
	private CallEntity callEntity;
	private String auditCallName = "WBGInqCustomerReqResp";
	private String outputXML;
	private String sTxn = "GetCustomerSummary";
	private String custID;
	String senderId = "WMS";
	private String custCategory;
	private String customerID;
	private String prefix;
	private String domicileBranchName;
	private String custIslamicFlag;
	private String accountClassification;
	private String homeLandlineNo;
	private String officeLandlineNo;
	private String mobileNo;
	private String fax;
	private String eMail;
	private String corPOBox;
	private String coraddressLine_1;
	private String coraddressLine_2;
	private String corcity;
	private String corstate;
	private String corcountry;
	private String phyaddressLine_1;
	private String phyaddressLine_2;
	private String phyaddressLine_3;
	private String  phycity;
	private String  phystate;
	private String phycountry;
	private String cusIslamicFlag;
	private String RMCode;
	private String RMName;
	private String profitCenterCode;
	private String sType;
	private String sFax;
	private String fullName;
	private String branchName;
	private String isCustVip;
	private String rmMail;
	private String rmPhone;
	private String tradeCustEmail;
	private String acctNo;
	boolean isCallExecuted;
	
	public WBGInqCustomerReqResp(Map<String, String> attributeMap, String sessionId, String stageId, String WI_NAME, Boolean prevStageNoGo, CallEntity callEntity) {
		this.WI_NAME = WI_NAME;
		this.StageID = Integer.parseInt(stageId);
		this.sessionId = sessionId;
		this.prevStageNoGo = prevStageNoGo;
		this.attributeMap = attributeMap;
		this.callEntity = callEntity;
		String sTxn = "GetCustomerSummary";
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"defaultMap "+attributeMap);

//		String outputXML;
		try {outputXML = APCallCreateXML.APSelect("SELECT CUSTID FROM EXT_WBG_AO"
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
		String soutputXML = APCallCreateXML.APSelect("SELECT LEAD_REF_NO FROM EXT_WBG_AO WHERE WI_NAME = N'" + WI_NAME + "'");
		XMLParser xp1 = new XMLParser(soutputXML);
		int mainCode1 = Integer.parseInt(xp1.getValueOf("MainCode"));
		LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, WI_NAME, auditCallName, "EIDA001", "EIDADedupe Started", sessionId);
		if(mainCode1 == 0){
			if(Integer.parseInt(xp1.getValueOf("TotalRetrieved")) > 0){

				leadRefNumber = xp1.getValueOf("LEAD_REF_NO");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"leadRefNumber"+leadRefNumber);
				LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, WI_NAME, auditCallName, "EIDA002", leadRefNumber, sessionId);
			}
		}	
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "CUSTINFO", e, sessionId);
		}
	}

	@Override
	public String call() throws Exception {

		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"FetchDetails call: inside" +prevStageNoGo);
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
						outputXml= "<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>";
					}
					responseHandler(outputXml, inputXml);
				} else {
					callStatus = "F";
					returnCode = 1;
					errorDescription = "Customer Id cannot be blank";
					updateCallOutput(inputXml);
				}
			}if(!callStatus.equalsIgnoreCase("F")){
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
		inputXml.append("<?xml version=\"1.0\"?><APWebService_Input><Option>WebService</Option><Calltype>Customer_Information</Calltype>").append("\n")
		.append( "<Customer>").append("\n")
		.append( "<CUST_ID>"+ custID + "</CUST_ID>").append("\n")
		.append( "<REF_NO>"+ refNo + "</REF_NO>").append("\n")
		.append( "<senderID>"+ senderId + "</senderID>").append("\n")
		.append( "<txnType>"+ "GetCustomerSummary"	+ "</txnType>").append("\n")
		.append( "<EngineName>"+LapsConfigurations.getInstance().CabinetName+"</EngineName>").append("\n")
		.append( "<USER>"+ "TP906079" + "</USER>").append("\n")
		.append( "<WiName>"+ this.WI_NAME + "</WiName>").append("\n")
		.append( "</Customer></APWebService_Input>");
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
			LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, WI_NAME, auditCallName, "cust_info", " DependencyCall:"+callEntity.getDependencyCallID(), sessionId);
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
				LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "TGCS090", "TGCS Successfully Executed", sessionId);
				
				//new
				fullName = xp.getValueOf("fullName");
				custCategory = xp.getValueOf("custCategory");
				customerID = xp.getValueOf("customerID");
				prefix = xp.getValueOf("prefix");
				domicileBranchName = xp.getValueOf("domicileBranchName"); 
				custIslamicFlag = xp.getValueOf("custIslamicFlag");
			    RMCode = xp.getValueOf("RMCode");
			    RMName = xp.getValueOf("RMName");
			    profitCenterCode = xp.getValueOf("profitCenterCode");
			    accountClassification = xp.getValueOf("accountIslamicFlag");
			    if(outputXML.indexOf("Addresses") > -1){
			    	int start = xp.getStartIndex("Addresses", 0, 0);
			    	int deadEnd =xp.getEndIndex("Addresses", start, 0);
			    	int noOfFields = xp.getNoOfFields("Address", start, deadEnd);
			    	LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "no. of Address "+noOfFields);
			    	int end = 0;
			    	for (int i = 0; i < noOfFields; i++) {
			    		   start = xp.getStartIndex("Address", end, 0);
			    		   end = xp.getEndIndex("Address", start, 0);
			    		   LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, " Address: "+xp.getValueOf("Address", start, end));
			    		   sType = xp.getValueOf("Type", start, end);
			    		   sFax  = xp.getValueOf("Fax", start, end);
			    		if(sType.equalsIgnoreCase("Correspondence Address")){
			    			corPOBox = xp.getValueOf("POBox", start, end);
			    			coraddressLine_1 = xp.getValueOf("AddressLine_1", start, end);
			    			coraddressLine_2 = xp.getValueOf("AddressLine_2", start, end);
			    			corcity = xp.getValueOf("City", start, end);
			    			corstate = xp.getValueOf("State", start, end);
			    			corcountry = xp.getValueOf("Country", start, end);
			    		} 
			    		else if(sType.equalsIgnoreCase("Office Address")){
			    			homeLandlineNo = xp.getValueOf("AddressLine_1", start, end);
			    			officeLandlineNo = xp.getValueOf("AddressLine_2", start, end);
			    			mobileNo = xp.getValueOf("Phone", start, end);
			    			eMail = xp.getValueOf("Email", start, end);
			    		}
			    		else if(sType.equalsIgnoreCase("Permanent Address")){
			    			phyaddressLine_1 = xp.getValueOf("POBox", start, end);
			    			phyaddressLine_2 = xp.getValueOf("AddressLine_1", start, end);
			    			phyaddressLine_3 = xp.getValueOf("AddressLine_2", start, end);
			    			phycity = xp.getValueOf("City", start, end);
			    			phystate = xp.getValueOf("State", start, end);
			    			phycountry = xp.getValueOf("Country", start, end);										
			    		}
			    	}
			    }		
				if(outputXML.indexOf("Accounts") > -1){
					int start = xp.getStartIndex("Accounts", 0, 0);
					int deadEnd = xp.getEndIndex("Accounts", start, 0);
					int noOfFields = xp.getNoOfFields("Account", start, deadEnd);

					int end = 0;
					for (int i = 0; i < noOfFields; i++) {
							start = xp.getStartIndex("Account", end, 0);
							end = xp.getEndIndex("Account", start, 0);
							branchName = xp.getValueOf("BranchName", start, end);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "branchName :" + branchName);
						}
					}
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "custFullName :" + fullName+
						"strdomicileBranchName:"+domicileBranchName);
			} else {
				callStatus = "F";
				returnCode = 1;
				errorDescription = "Fetch Customer details failed";
			}		 
			updateCallOutput(inputXml);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "TGCS100", e.getMessage(), sessionId);
		}		
	}

	@Override
	public void updateCallOutput(String EidaNumber) throws Exception {
		if(LapsUtils.isCallNameInBpmCallOut(auditCallName,WI_NAME)){
			String valList = "'"+ callStatus +"','"+ returnCode +"','" + errorDescription +"'";
//			APCallCreateXML.APUpdate("BPM_CALL_OUT", "CALL_STATUS,RETURN_CODE,ERROR_DESCRIPTION",valList, " WI_NAME = N'"+ winame +"'  AND CALL_STATUS = '"+auditCallName+"'", sessionId);
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
				reasonForAction = " Fetch Customer Details for " + custID;
				action = "Fetch Customer Details Success";

				errorDescription = "Fetch Customer Details Successful";
			} else {
				noResultsFoundMsg = "FAILURE";
				sActivityName = "Introduction";
				reasonForAction = "Fetch Customer Details Failed for  " + custID;
				action = "Fetch Customer Details Failed";

				errorDescription = "Fetch Customer Details Failed";
				APCallCreateXML.APUpdate("EXT_WBG_AO", "REPAIR_FLAG", "'Y'", "WI_NAME = '"+WI_NAME+"'", 
						sessionId);
				APCallCreateXML.APUpdate("EXT_WBG_AO", "RETRY_FLAG", "'Y'", "WI_NAME = '"+WI_NAME+"'", 
						sessionId);
				ProdCreateXML.WMCompleteWorkItem(sessionId, WI_NAME, 1);
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"leadRefNumber "+leadRefNumber);

			String valList = "'"+ WI_NAME +"','"+ leadRefNumber +"','"+ sActivityName +"', '"+ action +"', '"+ reasonForAction +"', '"+ noResultsFoundMsg +"'";
			APCallCreateXML.APInsert("USR_0_WBG_AO_DEC_HIST", "WI_NAME,LEAD_REFNO, WS_NAME, WS_DECISION, REJ_REASON, WS_COMMENTS", valList, sessionId);
			

			if(callStatus.equals("Y")){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO," INSIDE call status  Y ");
				String columnList = "CUSTFULLNAME,CUSTCAT,PREFIX,HOME_BRANCH,OFFNUMBER,OFFNUMBER2,MOBILE,"
						+ "CORRADDRSLINE1,CORRADDRSLINE2,CORRADDRSLINE3,CORRCITY,CORRSTATE,CORRCOUNTRY,PHYADDRSLINE1,"
						+ "PHYCORRADDRSLINE2,PHYCORRADDRSLINE3,PHYCITY,PHYSTATE,PHYCOUNTRY,RM_CODE,RM_NAME,"
						+ "PROFITCENTER,BRNAME,FAX";
				String valList3 = "'"+fullName+"','"+custCategory+"','"+prefix+"','"+domicileBranchName+"',"
						+ "'"+homeLandlineNo+"','"+officeLandlineNo+"','"+mobileNo+"',"
						+ "'"+corPOBox+"','"+coraddressLine_1+"','"+coraddressLine_2+"','"+corcity+"','"+corstate+"',"
						+ "'"+corcountry+"','"+phyaddressLine_1+"','"+phyaddressLine_2+"','"+phyaddressLine_3+"','"+phycity+"','"+phystate+"','"+phycountry+"',"
						+ "'"+RMCode+"','"+RMName+"','"+profitCenterCode+"','"+branchName+"','"+sFax+"'";
				APCallCreateXML.APUpdate("EXT_WBG_AO", columnList, valList3, " WI_NAME = '"+WI_NAME+"'", sessionId);
			} else {
				returnCode = -1;
			}

		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
	}
	
		
	}

}
