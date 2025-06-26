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

public class SuppDebitCardCreation implements ICallExecutor, Callable<String>{

	private String cardEmbossName;
	private String sessionId;
	private String wiName;
	private int stageId;
	private String callStatus;
	private int returnCode;
	private String errorDescription;
	private String errorDetail;
	private String status;
	private String reason;
	private String debitCardNo;
	private String refNo;
	private boolean prevStageNoGo;
	private Map<String, String> defaultMap;
	private CallEntity callEntity;
	private String auditCallName = "DCC";
	private String finalOutputXml;
	private String suppCardNo = "";
	private String suppPrimaryCardNo;
	private String suppCardCID;
	private String suppRelation;
	private String instantFlag="N";
	private String workIdRefNbr="";
	private String senderID ="DCMS";
	private String insertUpdateFlag="i";
	private String associateCard;	
	private String emailKeyContactPerson;
	private String mobKeyContactPerson;
	private String cardNumber;
	private String leadRefNumber;
	private String primaryCid;
	private String accountNumber;



	public SuppDebitCardCreation(Map<String, String> defaultMap, String sessionId, String stageId, String wiName, Boolean prevStageNoGo, CallEntity callEntity) {
		this.sessionId=sessionId;
		this.wiName = wiName;
		this.stageId = Integer.parseInt(stageId);
		this.prevStageNoGo = prevStageNoGo;
		this.defaultMap = defaultMap;
		this.callEntity = callEntity;
		try {
			String outputXML = fetchDebitCardCreateData();
			handleDebitCardCreateData(outputXML);

		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "DCC100", e, sessionId);
		}
	}

	@Override
	public String call() throws Exception {
		String inputXml = createInputXML();
		finalOutputXml = "<returnCode>0</returnCode>";
		try {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SuppDebitCardCreation prevStageNoGo ===> " + prevStageNoGo);
					if (associateCard.equalsIgnoreCase("Debit")) {
						finalOutputXml = ExecuteXML.executeWebServiceSocket(inputXml);
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,	"SuppDebitCardCreation outputXml ===> " + finalOutputXml);
						if (finalOutputXml == null || finalOutputXml.equalsIgnoreCase("")) {
							finalOutputXml = "<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>";
						}
						responseHandler(finalOutputXml, inputXml);
					}else {
						callStatus = "X";
						errorDescription="Supplementary Skipped";
						updateCallOutput(inputXml);
					}
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "DCC003", "DCC output: "+finalOutputXml, sessionId);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "DCC100", e, sessionId);
		}
		return finalOutputXml;
	}

	@Override
	public String executeCall(HashMap<String, String> input) throws Exception {
		return call();
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
				if(returnCode == 0) {
					callStatus = "Y";
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "DCC090", "Debit Card Creation Successfully Executed", sessionId);
					String cardType = "Debit Card";
					String cardNo = xp.getValueOf("suppCardNumber");
					String cardExpiryDate = xp.getValueOf("suppCardExpiryDate");
					String cardTypeDesc = xp.getValueOf("suppCardTypeDesc");

					String valList = "'"+ wiName +"','"+ cardNo +"','"+ cardExpiryDate +"','"+ cardTypeDesc +"','"+ cardType +"'";
					APCallCreateXML.APInsert("BPM_DSCOP_SUPPLEMENTARY_CARD_DETAILS", "WI_NAME,CARD_NUMBER,EXPIRY_DATE,CARD_TYPE_DESC,CARD_TYPE", valList, sessionId);

					if(!cardNo.equalsIgnoreCase("")){
						APCallCreateXML.APUpdate("EXT_DSCOP_EXTENDED", "SECURITY_ITEM", "'Yes'", " WI_NAME = N'"+ wiName +"'", sessionId);

					}
				} else {
					callStatus = "N";
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "DCC101", "Debit Card Creation Failed", sessionId);
				}
			updateCallOutput(inputXml);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "DCC100", e, sessionId);
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
			.append("<DSCOPCallType>AddDigitalDebitCardRequest</DSCOPCallType>").append("\n")
			.append("<REF_NO>" + refNo + "</REF_NO>").append("\n")
			.append("<OLDREF_NO>"+ fetchOldRefNumber() +"</OLDREF_NO>").append("\n")
			.append("<senderID>" + senderID + "</senderID>").append("\n")
			.append("<winame>" + wiName + "</winame>").append("\n")
			.append("<CardEmbossName>" + cardEmbossName + "</CardEmbossName>").append("\n")
			.append("<primaryCardNumber>" + suppPrimaryCardNo + "</primaryCardNumber>").append("\n")
			.append("<SuppCardNo>" + suppCardNo + "</SuppCardNo>").append("\n")
			.append("<SuppCardCid>" + suppCardCID + "</SuppCardCid>").append("\n")
			.append("<ChannelID>MIB</ChannelID>").append("\n")
			.append("<InstantFlag>"+instantFlag+"</InstantFlag>").append("\n")
			.append("<WorkIdRefNbr>"+workIdRefNbr+"</WorkIdRefNbr>").append("\n")
			.append("<insertUpdateFlag>"+insertUpdateFlag+"</insertUpdateFlag>").append("\n")
			.append("<relationship>"+suppRelation+"</relationship>").append("\n")
			.append("<transactionRefNumber>"+refNo+"</transactionRefNumber>").append("\n")
			.append("<EngineName>" + DSCOPConfigurations.getInstance().CabinetName + "</EngineName>").append("\n")
			.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
			.append("</APWebService_Input>");
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SuppDebitCardCreation inputXML ===> "+inputXML.toString());
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "DCC100", e, sessionId);
		}
		return inputXML.toString();   
	}

	@Override
	public void executeDependencyCall() throws Exception{
		try{
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "DCC004", "DCC DependencyCall:"+callEntity.getDependencyCallID(), sessionId);
			finalOutputXml = CallHandler.getInstance().executeDependencyCall(callEntity, defaultMap,sessionId, wiName, prevStageNoGo);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "DCC100", e, sessionId);
		}
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		try {
			
			APCallCreateXML.APUpdate("USR_0_DSCOP_CALL_OUT", "CALL_STATUS,ERROR_DESCRIPTION", "'Y','Processed By Expiry Utility'", " WI_NAME = N'"+ wiName +"' AND CALL_NAME = 'SuppDebitCardCreation' AND CALL_STATUS ='N' and STAGE_ID= '"+stageId+"'", sessionId);
			
			String valList = "'"+ wiName +"',"+ stageId +", 'SuppDebitCardCreation', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ debitCardNo +"'";
			APCallCreateXML.APInsert("USR_0_DSCOP_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
					+ "ERROR_DETAIL, STATUS, REASON", valList, sessionId);

			if(callStatus.equals("Y")){
				String valList2 = "'"+ wiName +"',"+ stageId +", 'SuppDebitCardCreation', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml+"', 0";
				APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON,"
						+ " RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);
				
				//Added by Shivanshu
			/*	String msgValues = leadRefNumber;
				String prefLanguage = "EN";
				DSCOPUtils.getInstance().sendEmailSMS(sessionId, "E", primaryCid, accountNumber, "AC", 
				defaultMap.get("ISSUANCE_NOTIFY_ID"), msgValues, mobKeyContactPerson,	
				defaultMap.get("ISSUANCE_NOTIFY_ID"), emailKeyContactPerson, 
				defaultMap.get("FROM_EMAIL_ID"),"", "P", wiName, prefLanguage);*/
				executeDependencyCall();
				
			}else if (callStatus.equals("X")){
			     	executeDependencyCall();
			}else {
				String valList2 = "'"+ wiName +"',"+ stageId +", 'SuppDebitCardCreation', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml+"', 1";
				APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON, "
						+" RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "DCC100", e, sessionId);
		}
	}
	
	public String fetchDebitCardCreateData() throws Exception {
		return APCallCreateXML.APSelect("SELECT A.CUSTOMER_ID,A.ASSOCIATE_CARD,B.EMBOSSED_NAME,B.PRIMARY_CARD,B.RELATIONSHIP,"
				+ " A.CUSTOMER_EMAIL, A.CUSTOMER_MOBILE_NO, A.PRIMARY_CID, A.PRIMARY_CARD_NO, A.LEAD_REF_NO "
				+ " FROM EXT_DSCOP A,EXT_DSCOP_EXTENDED B WHERE A.WI_NAME=B.WI_NAME AND A.WI_NAME = N'" + wiName + "'");
	}
	
	private void handleDebitCardCreateData(String outputXML) throws Exception {
		XMLParser xp = new XMLParser(outputXML);
		int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "DCC100", "DCC Started", sessionId);
		if (mainCode == 0 && Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0) {
			cardEmbossName = xp.getValueOf("EMBOSSED_NAME");
			suppPrimaryCardNo = xp.getValueOf("PRIMARY_CARD");
			suppCardCID = xp.getValueOf("CUSTOMER_ID");
			suppRelation = xp.getValueOf("RELATIONSHIP");
			associateCard = xp.getValueOf("ASSOCIATE_CARD");
			emailKeyContactPerson = xp.getValueOf("CUSTOMER_EMAIL");
			mobKeyContactPerson = xp.getValueOf("CUSTOMER_MOBILE_NO");
			primaryCid = xp.getValueOf("PRIMARY_CID");
			cardNumber = xp.getValueOf("PRIMARY_CARD_NO");
			leadRefNumber = xp.getValueOf("LEAD_REF_NO");
		}
		

		String outputACC = APCallCreateXML.APSelect("SELECT LINKED_ACCOUNT FROM BPM_DSCOP_DEBITCARD_DETAILS WHERE CARD_NUMBER = '"+cardNumber+"' and WI_NAME = N'" + wiName + "'");
		XMLParser xpAcc = new XMLParser(outputACC);
		 mainCode = Integer.parseInt(xpAcc.getValueOf("MainCode"));
		if(mainCode == 0){
			if(Integer.parseInt(xpAcc.getValueOf("TotalRetrieved")) > 0){
				accountNumber = xpAcc.getValueOf("LINKED_ACCOUNT");
			}
		}
	}
	

	public String fetchOldRefNumber() throws Exception {
		String outputXML = "";
		String oldRefNumber = "";
		outputXML=APCallCreateXML.APSelect("SELECT OLDREFNO FROM USR_0_DSCOP_REQUEST  WHERE WI_NAME = N'" + wiName + "'"
				+ " AND CALL_NAME = 'SuppDebitCardCreation' AND CALL_STATUS = 'N' "
				+ " ORDER BY 1 DESC FETCH FIRST ROW ONLY ");
		XMLParser xp = new XMLParser(outputXML);
		int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "DCC100", "DCC Started", sessionId);
		if (mainCode == 0 && Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0) {
			oldRefNumber = xp.getValueOf("OLDREFNO");
		}
		return oldRefNumber;
	}
}
