package com.newgen.cbg.calls;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

public class SuppAddCRSDetails implements ICallExecutor, Callable<String>{
	private String wiName;
	private int stageId;
	private String sessionId;
	private String callStatus;
	private int returnCode;
	private String errorDescription;
	private String errorDetail;
	private String status;
	private String reason;
	private String senderID;
	private String customerId;
	private String custFirstName;
	private String classificationDate;
	private String checkerDate;
	private String makerDate;
	private String refNo;
	private boolean prevStageNoGo;
	private Map<String, String> defaultMap;
	private CallEntity callEntity;
	private String auditCallName = "CRS";
	private boolean isCallSkip=false;
	private String ntbCust;
	private String skipModify;
	private String finalOutputXml;

	public SuppAddCRSDetails(Map<String, String> defaultMap, String sessionId, String stageId, String wiName, Boolean prevStageNoGo, CallEntity callEntity)
	{
		this.wiName = wiName;
		this.stageId = Integer.parseInt(stageId);
		this.sessionId = sessionId;
		this.prevStageNoGo = prevStageNoGo;
		this.defaultMap = defaultMap;
		this.callEntity = callEntity;
		String outputXML;
		try {

			outputXML = APCallCreateXML.APSelect("SELECT CUSTOMER_ID,IS_NTB_CUST,ASSOCIATE_CARD,SUPP_CARDHOLDER_FULL_NAME,SKIP_MODIFY FROM EXT_DSCOP WHERE WI_NAME = N'" + wiName + "'");

			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "CRS001", "CRS Started", sessionId);
			if(mainCode == 0){
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"AddCRS TotalRetrieved: "+Integer.parseInt(xp.getValueOf("TotalRetrieved")));
				customerId = xp.getValueOf("CUSTOMER_ID");
				custFirstName = xp.getValueOf("SUPP_CARDHOLDER_FULL_NAME");
				LocalDateTime now = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				classificationDate = now.format(formatter);
				makerDate = classificationDate;
				checkerDate = classificationDate;
				senderID = defaultMap.get("SENDER_ID");
				ntbCust = defaultMap.get("IS_NTB_CUST");
				skipModify=defaultMap.get("SKIP_MODIFY");
				if (!(ntbCust.equalsIgnoreCase("N") && skipModify.equalsIgnoreCase("Y"))) {
					isCallSkip = true;
				}
			 }	
		  } catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "CRS100", e, sessionId);
		}
	}

	@Override
	public String call() throws Exception {
		String inputXml = createInputXML();
		finalOutputXml = "<returnCode>0</returnCode>";
		try {
			if(!isCallSkip){
				if(!prevStageNoGo){
					finalOutputXml = ExecuteXML.executeWebServiceSocket(inputXml);
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "AddCRSDetails outputXml ===> " + finalOutputXml);
					if(finalOutputXml==null ||finalOutputXml.equalsIgnoreCase("")){
						finalOutputXml= "<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>";
					}
					responseHandler(finalOutputXml, inputXml);
				} else {
					callStatus = "N";
					finalOutputXml= "<returnCode>1</returnCode>";
					updateCallOutput(inputXml);
				}
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"AddCRSDetails outputXml ===> "+ finalOutputXml);
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "CRS002", "CRS output: "+finalOutputXml, sessionId);
			}else {
				callStatus = "X";
				updateCallOutput(inputXml);
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "CRS003", "CRS CALL SKIPPED", sessionId);
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "CRS100", e, sessionId);
		}
		return finalOutputXml;		
	}

	@Override
	public void executeDependencyCall() throws Exception{
		try{
			String outputXMLTemp = "";
			outputXMLTemp = CallHandler.getInstance().executeDependencyCall(callEntity, defaultMap, sessionId, wiName, prevStageNoGo);
			if(!outputXMLTemp.isEmpty()){			
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "CRS003", "CRS DependencyCall:"+callEntity.getDependencyCallID(), sessionId);
				finalOutputXml = outputXMLTemp;
			}else {
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "CRS003", "CRS DependencyCall Not Found.", sessionId);
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "CRS100", e, sessionId);
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
			.append("<EngineName>" + DSCOPConfigurations.getInstance().CabinetName + "</EngineName>").append("\n")
			.append("<Calltype>DSCOP</Calltype>").append("\n")
			.append("<DSCOPCallType>AddCRS</DSCOPCallType>").append("\n")
			.append("<REF_NO>" + refNo  + "</REF_NO>").append("\n")
			.append("<OLDREF_NO></OLDREF_NO>").append("\n")
			.append("<senderID>"+senderID+"</senderID>").append("\n")
			.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
			.append("<WINAME>" + wiName + "</WINAME>")
			.append("<customerDetails>").append("\n")
			.append("<CustID>"+ customerId +"</CustID>").append("\n")
			.append("<CustType>I</CustType>").append("\n")
			.append("<CustFirstName>"+ custFirstName +"</CustFirstName>").append("\n")
			.append("<CustLastName></CustLastName>").append("\n")
			.append("<CustBirthCity></CustBirthCity>").append("\n")
			.append("<ClassificationId>13</ClassificationId>").append("\n")
			.append("<ClassificationDate>"+ classificationDate +"</ClassificationDate>").append("\n")
			.append("<entityTypeId></entityTypeId>").append("\n")
			.append("<Channel>DSCOP</Channel>").append("\n")
			.append("<MakerId>WMSMAKER</MakerId>").append("\n")
			.append("<CheckerId>WMSCHEKER</CheckerId>").append("\n")
			.append("<checkerDate>"+ checkerDate +"</checkerDate>").append("\n")
			.append("<CrsCertFormObtained>N</CrsCertFormObtained>").append("\n")
			.append("<ResidenceAddressConfirmationStatus>Y</ResidenceAddressConfirmationStatus>").append("\n")
			.append("<TermsAndCondAccepted></TermsAndCondAccepted>").append("\n")
			.append("</customerDetails>").append("\n")	
			.append("<documentDetails>").append("\n")
			.append("<documents>").append("\n")
			.append("<CRSDocIndex></CRSDocIndex>").append("\n")
			.append("<CRSDocName></CRSDocName>").append("\n")
			.append("<CRSRefNo></CRSRefNo>").append("\n")
			.append("<CRSDocRefNo></CRSDocRefNo>").append("\n")
			.append("<CRSCertDate></CRSCertDate>").append("\n")
			.append("</documents>").append("\n")
			.append("</documentDetails>").append("\n")
			.append("<taxResidenceCountryDtls>").append("\n")
			.append("<taxResidenceCountries>").append("\n")		
			.append("<TaxResidenceCountry></TaxResidenceCountry>").append("\n")
			.append("<TaxpayerIdNumber></TaxpayerIdNumber>").append("\n")
			.append("<ReasonId></ReasonId>").append("\n")
			.append("<ReasonDesc></ReasonDesc>").append("\n")
			.append("<ReportableFlag></ReportableFlag>").append("\n")
			.append("</taxResidenceCountries>").append("\n")
			.append("<taxResidenceCountries>").append("\n")		
			.append("<TaxResidenceCountry></TaxResidenceCountry>").append("\n")
			.append("<TaxpayerIdNumber></TaxpayerIdNumber>").append("\n")
			.append("<ReasonId></ReasonId>").append("\n")
			.append("<ReasonDesc></ReasonDesc>").append("\n")
			.append("<ReportableFlag></ReportableFlag>").append("\n")
			.append("</taxResidenceCountries>").append("\n")
			.append("<taxResidenceCountries>").append("\n")		
			.append("<TaxResidenceCountry></TaxResidenceCountry>").append("\n")
			.append("<TaxpayerIdNumber></TaxpayerIdNumber>").append("\n")
			.append("<ReasonId></ReasonId>").append("\n")
			.append("<ReasonDesc></ReasonDesc>").append("\n")
			.append("<ReportableFlag></ReportableFlag>").append("\n")
			.append("<makerDate>"+ makerDate +"</makerDate>").append("\n")
			.append("</taxResidenceCountries>").append("\n")
			.append("</taxResidenceCountryDtls>").append("\n")
			.append("<controlPersonTaxResCountryDtls>").append("\n")
			.append("<controlPersonTaxResCountries>").append("\n")
			.append("<ControlPersonId></ControlPersonId>").append("\n")
			.append("<ControlPersonPrimaryKey></ControlPersonPrimaryKey>").append("\n")
			.append("</controlPersonTaxResCountries>").append("\n")
			.append("</controlPersonTaxResCountryDtls>").append("\n")
			.append("<entityControlPersonDtls>").append("\n")
			.append("<controlPersonId></controlPersonId>").append("\n")
			.append("<PersonFirstName></PersonFirstName>").append("\n")
			.append("<PersonLastName></PersonLastName>").append("\n")
			.append("<PersonBuildingName></PersonBuildingName>").append("\n")
			.append("<PersonFlatVillaNo></PersonFlatVillaNo>").append("\n")
			.append("<PersonStreet></PersonStreet>").append("\n")
			.append("<PersonCity></PersonCity>").append("\n")
			.append("<PersonEmirate></PersonEmirate>").append("\n")
			.append("<PersonCountry></PersonCountry>").append("\n")
			.append("<PersonDateOfBirth></PersonDateOfBirth>").append("\n")
			.append("<PersonBirthCity></PersonBirthCity>").append("\n")
			.append("<PersonBirthCountry></PersonBirthCountry>").append("\n")
			.append("<PersonControlTypeId></PersonControlTypeId>").append("\n")
			.append("<ControlPersonPrimaryKey></ControlPersonPrimaryKey>").append("\n")
			.append("</entityControlPersonDtls>").append("\n")
			.append("<UaeResUnderInvestScheme></UaeResUnderInvestScheme>").append("\n")
			.append("<ResOtherThanUAE></ResOtherThanUAE>").append("\n")
			.append("<TaxPayerInOtherCountry></TaxPayerInOtherCountry>").append("\n")
			.append("<TaxCountry1></TaxCountry1>").append("\n")
			.append("<TaxCountry2></TaxCountry2>").append("\n")
			.append("<TaxCountry3></TaxCountry3>").append("\n")
			.append("</APWebService_Input>");
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "AddCRSDetails inputXML ===> "+inputXML.toString());
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "CRS100", e, sessionId);
		}
		return inputXML.toString();  
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
			if(returnCode == 0){
				callStatus = "Y";
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "CRS090", "AddCRS Successfully Executed", sessionId);
			} else {
				callStatus = "N";
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "CRS101", "AddCRS Failed", sessionId);
			}
			updateCallOutput(inputXml);

		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "CRS100", e, sessionId);
		}
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		try {

			APCallCreateXML.APUpdate("USR_0_DSCOP_CALL_OUT", "CALL_STATUS,ERROR_DESCRIPTION", "'Y','Processed By Expiry Utility'", " WI_NAME = N'"+ wiName +"' AND CALL_NAME = 'SuppAddCRSDetails' AND CALL_STATUS ='N' and STAGE_ID= '"+stageId+"'", sessionId);
			
			String valList = "'"+ wiName +"',"+ stageId +", 'SuppAddCRSDetails', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"'";
			APCallCreateXML.APInsert("USR_0_DSCOP_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON", valList, sessionId);

			if(callStatus.equals("Y")){
				APCallCreateXML.APUpdate("EXT_DSCOP", "COMPLETE_FLAG","'Y'", " WI_NAME = N'"+ wiName +"'", sessionId);
				String valList2 = "'"+ wiName +"',"+ stageId +", 'SuppAddCRSDetails', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml+"', 0";
				APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON,"
						+ " RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);
			}else {
				String valList2 = "'"+ wiName +"',"+ stageId +", 'SuppAddCRSDetails', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml+"', 1";
				APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON, "
						+" RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "CRS100", e, sessionId);
		}
	}




}
