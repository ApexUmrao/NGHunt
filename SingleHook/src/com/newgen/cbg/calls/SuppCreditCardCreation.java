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

public class SuppCreditCardCreation implements ICallExecutor, Callable<String>{
	private String wiName;
	private int stageId;
	private String sessionId;
	private String callStatus="";
	private int returnCode;
	private String errorDescription;
	private String errorDetail;
	private String status;
	private String reason;
	private String leadReferenceNumber;
	private String primaryCid;
	private String refNo;
	private boolean prevStageNoGo;
	private boolean isSkipCall=false;
	private Map<String, String> defaultMap;
	private CallEntity callEntity;
	private String finalOutputXml;
	private String auditCallName = "ADCC";
	private String cardNumber;
	private String associateCard;
	private String creditCardNo = "";
	private String mobileNumber = "";
	private String accountNumber = "";
	private String emailId = "";

	String outputXML;
	int sMainCode;
	private StringBuilder inputReqXml=new StringBuilder();

	public SuppCreditCardCreation(Map<String, String> defaultMap, String sessionId, String stageId, String wiName, Boolean prevStageNoGo, CallEntity callEntity)
	{
		this.sessionId=sessionId;
		this.wiName = wiName;
		this.stageId = Integer.parseInt(stageId);
		this.prevStageNoGo = prevStageNoGo;
		this.defaultMap = defaultMap;
		this.callEntity = callEntity;

		try {
			outputXML = fetchCreditCardCreateData();
			handleCreditCardCreateData(outputXML);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "ADCC100", e, sessionId);
		}
	}

	@Override
	public String call() throws Exception {
		String inputXml = createInputXML();
		finalOutputXml = "<returnCode>0</returnCode>";
		try {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SuppCreditCardCreation prevStageNoGo ===> " + prevStageNoGo);
			if(!isSkipCall && associateCard.equalsIgnoreCase("Credit")){
				inputXml = finalOutputXml = ExecuteXML.executeWebServiceSocket(inputXml);
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SuppCreditCardCreation outputXml ===> " + finalOutputXml);
				if(finalOutputXml==null ||finalOutputXml.equalsIgnoreCase("")){
					finalOutputXml= "<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>";
				}
				responseHandler(finalOutputXml, inputXml);
			}else {
				callStatus = "X";
				errorDescription="Supplementary Skipped";
				finalOutputXml = "<returnCode>0</returnCode><errorDescription>"+errorDescription+"</errorDescription>";
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "ADCC100", "SuppCreditCardCreation Skipped", sessionId);
				updateCallOutput(inputXml);

			}

			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SuppCreditCardCreation outputXml ===> "+ finalOutputXml);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "ADCC002", "ADCC output: "+finalOutputXml, sessionId);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "ADCC100", e, sessionId);
		}
		return finalOutputXml;
	}


	@Override
	public String createInputXML() throws Exception {
		StringBuilder inputXML = new StringBuilder(); 
		try {
			refNo = DSCOPUtils.getInstance().generateSysRefNumber();
			inputXML.append("<?xml version=\"1.0\"?>").append("\n")
			.append("<APWebService_Input>").append("\n")
			.append("<Option>WebService</Option>").append("\n")
			.append("<EngineName>" + DSCOPConfigurations.getInstance().CabinetName + "</EngineName>").append("\n")
			.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
			.append("<WINAME>" + wiName + "</WINAME>").append("\n")
			.append("<Calltype>DSCOP</Calltype>").append("\n")
			.append("<DSCOPCallType>AddDigitalSupplCardReq</DSCOPCallType>").append("\n")
			.append("<REF_NO>" + refNo + "</REF_NO>").append("\n")
			.append("<OLDREF_NO>"+ refNo +"</OLDREF_NO>").append("\n")
			.append("<senderID>WMSBPMENG</senderID>").append("\n")
			.append("<leadRefNo>" + leadReferenceNumber + "</leadRefNo>").append("\n")
			.append("<customerId>" + primaryCid + "</customerId>").append("\n")
			.append("<primaryRefNo>" + cardNumber+ "</primaryRefNo>").append("\n")
			.append("<dsaCode>DSCOP</dsaCode>").append("\n")
			.append("<SupplementryCardDetails>").append("\n")
			.append(finalXml()).append("\n")
			.append("</SupplementryCardDetails>").append("\n")
			.append("</APWebService_Input>");
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "ADCC100", e, sessionId);
		}
		return inputXML.toString();  
	}

	@Override
	public String executeCall(HashMap<String, String> input) throws Exception {
		return call();
	}

	@Override
	public void responseHandler(String outputXML, String inputXML) throws Exception {
		try {
			XMLParser xp = new XMLParser(outputXML);
			returnCode = Integer.parseInt(xp.getValueOf("returnCode", "1", true));
			errorDescription = xp.getValueOf("errorDescription", "", true);
			errorDetail = xp.getValueOf("errorDetail", "", true);
			status = xp.getValueOf("Status", "", true);
			reason = xp.getValueOf("Reason", "", true);

			if(returnCode == 0) {
				callStatus = "Y";
				String lapsAppNo = xp.getValueOf("lendperfectReferenceNumber");
				String cardNo = xp.getValueOf("cardNumber");
				String cardExpiryDate = xp.getValueOf("expiryDate");
				String cardTypeDesc = xp.getValueOf("cardType");
				String cardType = "Credit Card";
				APCallCreateXML.APUpdate("EXT_DSCOP", "LEND_PERFECT_NO","'"+ lapsAppNo +"'", " WI_NAME = N'"+ wiName +"'", sessionId);
				String valList = "'"+ wiName +"','"+ cardNo +"','"+ cardExpiryDate +"','"+ cardTypeDesc +"','"+ cardType +"'";
				APCallCreateXML.APInsert("BPM_DSCOP_SUPPLEMENTARY_CARD_DETAILS", "WI_NAME,CARD_NUMBER,EXPIRY_DATE,CARD_TYPE_DESC,CARD_TYPE", valList, sessionId);
				creditCardNo = DSCOPUtils.getInstance().maskCreditCardNo(cardNo);
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "ADCC101", "SuppCreditCardCreation SUCCESS", sessionId);
			} else {
				callStatus = "N";
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "ADCC102", "SuppCreditCardCreation Failed", sessionId);
			} 
			updateCallOutput(inputXML);

		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "ADCC100", e, sessionId);
		}	
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		try {

			APCallCreateXML.APUpdate("USR_0_DSCOP_CALL_OUT", "CALL_STATUS,ERROR_DESCRIPTION", "'Y','Processed By Expiry Utility'", " WI_NAME = N'"+ wiName +"' AND CALL_NAME = 'SuppCreditCardCreation' AND CALL_STATUS ='N' and STAGE_ID= '"+stageId+"'", sessionId);

			String valList = "'"+ wiName +"',"+ stageId +", 'SuppCreditCardCreation', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"'";
			APCallCreateXML.APInsert("USR_0_DSCOP_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON", valList, sessionId);

			if(callStatus.equals("Y")){
				String valList2 = "'"+ wiName +"',"+ stageId +", 'SuppCreditCardCreation', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +","+DSCOPUtils.convertToPlainString(inputXml)+", 0";
				APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON,"
						+ " RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);

				//Added by Shivanshu
			/*	String msgValues = leadReferenceNumber;
				String prefLanguage = "EN";
				DSCOPUtils.getInstance().sendEmailSMS(sessionId, "E", primaryCid, cardNumber, "C", 
						defaultMap.get("ISSUANCE_NOTIFY_ID"), msgValues, mobileNumber,	
						defaultMap.get("ISSUANCE_NOTIFY_ID"), emailId, 
						defaultMap.get("FROM_EMAIL_ID"),"", "P", wiName, prefLanguage);*/

				executeDependencyCall();

			}else if(callStatus.equals("X")){
				executeDependencyCall();
			}else {
				String valList2 = "'"+ wiName +"',"+ stageId +", 'SuppCreditCardCreation', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml+"', 1";
				APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON, "
						+" RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "ADCC100", e, sessionId);
		}
	}



	@Override
	public void executeDependencyCall() throws Exception{
		try{
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "CCPS004", "CCPS DependencyCall:"+callEntity.getDependencyCallID(), sessionId);
			finalOutputXml = CallHandler.getInstance().executeDependencyCall(callEntity, defaultMap, sessionId, wiName, prevStageNoGo);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "CCPS100", e, sessionId);
		}
	}

	public StringBuilder finalXml(){
		String customerId;
		String suppTitle;
		try {
			/*outputXML = APCallCreateXML.APSelect("SELECT A.SUPP_CARDHOLDER_FULL_NAME,A.CUSTOMER_ID,B.EMBOSSED_NAME,B.RELATIONSHIP,"
					+ " A.DOB, B.PERC_CREDIT_LIMIT,A.NATIONALITY ,A.PASSPORT_NO , A.EIDA_NUMBER, B.COUNTRY_OF_RESIDENCE ,A.GENDER,"
					+ " TO_CHAR(TO_DATE(B.EIDA_EXPIRY_DATE,'YYYY-MM-DD'),'DD/MM/YYYY') as EIDAEXP, "
					+ " TO_CHAR(TO_DATE(B.PASSPORT_EXPIRY_DATE,'YYYY-MM-DD'),'DD/MM/YYYY') as PASSPORTEXP "
					+ " FROM EXT_DSCOP A, EXT_DSCOP_EXTENDED B "
					+ " WHERE A.WI_NAME = B.WI_NAME AND A.WI_NAME =  N'" + wiName + "' ");*/
			outputXML = APCallCreateXML.APSelect("SELECT A.SUPP_CARDHOLDER_FULL_NAME,A.CUSTOMER_ID,B.EMBOSSED_NAME,B.RELATIONSHIP,"
					+ " A.DOB, B.PERC_CREDIT_LIMIT,A.NATIONALITY ,A.PASSPORT_NO , A.EIDA_NUMBER, B.COUNTRY_OF_RESIDENCE ,A.GENDER,"
					+ " B.EIDA_EXPIRY_DATE as EIDAEXP, "
					+ " B.PASSPORT_EXPIRY_DATE as PASSPORTEXP "
					+ " FROM EXT_DSCOP A, EXT_DSCOP_EXTENDED B "
					+ " WHERE A.WI_NAME = B.WI_NAME AND A.WI_NAME =  N'" + wiName + "' ");

			XMLParser xp1 = new XMLParser(outputXML);
			sMainCode = Integer.parseInt(xp1.getValueOf("MainCode"));
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "PMCC001", "SuppCreditCardCreation Started2", sessionId);
			if(sMainCode == 0){

				if(Integer.parseInt(xp1.getValueOf("TotalRetrieved")) > 0){
					int start = xp1.getStartIndex("Records", 0, 0);
					int deadEnd = xp1.getEndIndex("Records", start, 0);									
					int end = 0;
					int noOfFields = xp1.getNoOfFields("Record", start,deadEnd);
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"SuppCreditCardCreation Supplementry noOfFields: "+noOfFields);
					for(int i = 0; i < noOfFields; ++i){
						start = xp1.getStartIndex("Record", end, 0);
						end = xp1.getEndIndex("Record", start, 0);
						String cardHolderName = xp1.getValueOf("SUPP_CARDHOLDER_FULL_NAME", start, end);
						customerId = xp1.getValueOf("CUSTOMER_ID", start, end);
						String embossedName = xp1.getValueOf("EMBOSSED_NAME", start, end);
						String relationship = xp1.getValueOf("RELATIONSHIP", start, end);
						String dob = xp1.getValueOf("DOB", start, end);
						String cardLimitPerc = xp1.getValueOf("PERC_CREDIT_LIMIT", start, end);
						String nationality = xp1.getValueOf("NATIONALITY", start, end);
						String passportNo = xp1.getValueOf("PASSPORT_NO", start, end);
						String eidaNo = xp1.getValueOf("EIDA_NUMBER", start, end);
						String countryResidence = xp1.getValueOf("COUNTRY_OF_RESIDENCE", start, end);
						String gender = xp1.getValueOf("GENDER", start, end);
						String eidaExp = xp1.getValueOf("EIDAEXP", start, end);
						String passportExp = xp1.getValueOf("PASSPORTEXP", start, end);

						if(gender.equalsIgnoreCase("F")) {
							suppTitle="2";
						}else {
							suppTitle="1";
						}
						inputReqXml.append("<supplementaryDetails>").append("\n")
						.append("<supplementaryTitle>"+suppTitle+"</supplementaryTitle>").append("\n")
						.append("<supplementaryName>"+cardHolderName+"</supplementaryName>").append("\n")
						.append("<supplementaryEmbossName>"+embossedName+"</supplementaryEmbossName>").append("\n")
						.append("<supplementaryLimitPercentage>"+cardLimitPerc+"</supplementaryLimitPercentage>").append("\n")
						.append("<supplementaryRelationship>"+relationship+"</supplementaryRelationship>").append("\n")
						.append("<supplementaryCID>"+customerId+"</supplementaryCID>").append("\n")
						.append("<supplementaryNationality>"+nationality+"</supplementaryNationality>").append("\n")
						.append("<supplementaryPassport>"+passportNo+"</supplementaryPassport>").append("\n")
						.append("<supplementaryMaritalStatus></supplementaryMaritalStatus>").append("\n")
						.append("<supplementaryMotherMaidenName></supplementaryMotherMaidenName>").append("\n")
						.append("<supplementaryEIDA>"+eidaNo+"</supplementaryEIDA>").append("\n")
						.append("<supplementaryCustomizationID></supplementaryCustomizationID>").append("\n")
						.append("<countryOfResidence>"+countryResidence+"</countryOfResidence>").append("\n")
						.append("<supplementaryDOB>"+dob+"</supplementaryDOB>").append("\n")
						.append("<passportExpiryDate>"+passportExp+"</passportExpiryDate>").append("\n")
						.append("<eidaExpiryDate>"+eidaExp+"</eidaExpiryDate>").append("\n")
						.append("<mobileNo></mobileNo>").append("\n")
						.append("<email></email>").append("\n")
						.append("<gender>"+gender+"</gender>").append("\n")
						.append("<transactionTypeFlag></transactionTypeFlag>").append("\n")
						.append("</supplementaryDetails>");
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "AddDigitalSupplementryCard inputReqXml ===> " + inputReqXml);
					}
				}else {
					isSkipCall = true;
				}

			}
		} catch (NGException e) {
			e.printStackTrace();
		}
		return inputReqXml;

	}

	public String fetchCreditCardCreateData() throws Exception{
		return APCallCreateXML.APSelect("SELECT A.PRIMARY_CID,A.ASSOCIATE_CARD,A.LEAD_REF_NO,B.PRIMARY_CARD, A.CUSTOMER_MOBILE_NO, "
				+ "  A.CUSTOMER_EMAIL " 
				+ " FROM EXT_DSCOP A, EXT_DSCOP_EXTENDED B "
				+ " WHERE A.WI_NAME = B.WI_NAME AND A.WI_NAME =  N'" + wiName + "' ");
	}

	public void handleCreditCardCreateData(String outputXML) throws Exception{

		XMLParser xp1 = new XMLParser(outputXML);
		sMainCode = Integer.parseInt(xp1.getValueOf("MainCode"));
		DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "PMCC001", "CCSSOAddCardCCPS Started2", sessionId);
		if(sMainCode == 0 && Integer.parseInt(xp1.getValueOf("TotalRetrieved")) > 0){
			primaryCid = xp1.getValueOf("PRIMARY_CID");
			leadReferenceNumber = xp1.getValueOf("LEAD_REF_NO");
			cardNumber = xp1.getValueOf("PRIMARY_CARD");
			associateCard = xp1.getValueOf("ASSOCIATE_CARD");
			mobileNumber = xp1.getValueOf("CUSTOMER_MOBILE_NO");
			emailId = xp1.getValueOf("CUSTOMER_EMAIL");

		}else {
			isSkipCall = true;
		}

	} 

}

