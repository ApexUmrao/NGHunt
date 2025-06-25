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

public class SuppModifyCustomer implements ICallExecutor, Callable<String> {
	private String wiName;
	private int stageId;
	private String sessionId;
	private String callStatus;
	private int returnCode;
	private String errorDescription;
	private String errorDetail;
	private String status;
	private String reason;
	private String customerId;
	private String customerName;
	private String passportNo;
	private String passportExpiryDate;
	private String corresFlatNo;
	private String corresbuilding;
	private String corresStreet;
	private String corresCity;
	private String corresEmirate;
	private String corresCountry;
	private String perCity;
	private String perEmirate;
	private String perCountry;
	private String perZip;
	private String refNo;
	private String cbrValue23;
	private String senderID;
	private String custMotherMaidenName;
	private String isNTBCust;
	private boolean prevStageNoGo;
	private Map<String, String> defaultMap;
	private CallEntity callEntity;
	private String auditCallName = "MODCUST";
	private String sEidaNumber;
	private String sMobileno;
	private String sPassportGender;
	private String finaloutputXml;
	private String customerNationality;
	private String eidaExpiryDate; 
	private String permAddLine2= "";
	private String permAddLine3= "";
	private String skipModify;
	public SuppModifyCustomer(Map<String, String> defaultMap, String sessionId, String stageId, String wiName, Boolean prevStageNoGo, CallEntity callEntity){
		this.wiName = wiName;
		this.stageId = Integer.parseInt(stageId);
		this.sessionId = sessionId;
		this.prevStageNoGo = prevStageNoGo;
		this.defaultMap = defaultMap;
		this.callEntity = callEntity;
		String outputXML;
		try {
			/*outputXML = APCallCreateXML.APSelect("SELECT A.CUSTOMER_ID, A.SUPP_CARDHOLDER_FULL_NAME, A.PASSPORT_NO,A.GENDER,"
					+ " TO_CHAR(TO_DATE(B.PASSPORT_EXPIRY_DATE,'YYYY-MM-DD'),'DD/MM/YYYY') as PASSPORT_EXPIRY_DATE, A.EMPLYR_ADD_CITY, "
					+ " NVL((SELECT STATE FROM USR_0_STATE_EMIRATE_MASTER WHERE EMIRATE_CODE=A.EMPLYR_ADD_STATE_EMIRATE),EMPLYR_ADD_STATE_EMIRATE)"
					+ " AS EMPLYR_ADD_STATE_EMIRATE, A.EMPLYR_ADD_COUNTRY, A.EMPLYR_ADD_ZIP, A.EMPLYR_ADD_ADDRESSLINE2, A.EMPLYR_ADD_ADDRESSLINE3,"
					+ " A.CORRES_ADD_POBOX_VILA_FLATNO, A.CORRES_ADD_FLOOR_BUILDINGNAME,A.CORRES_ADD_CITY, "
					+ " NVL((SELECT STATE FROM USR_0_STATE_EMIRATE_MASTER WHERE EMIRATE_CODE=A.CORRES_ADD_STATE_EMIRATE),CORRES_ADD_STATE_EMIRATE) AS "
					+ " CORRES_ADD_STATE_EMIRATE, A.CUSTOMER_SEGMENT,  A.CORRES_ADD_COUNTRY,  A.RESI_ADD_POBOX_VILA_FLATNO, A.RESI_ADD_FLOOR_BUILDINGNAME,"
					+ " A.RESI_ADD_STREET_NAME, A.RESI_ADD_CITY, NVL((SELECT STATE FROM USR_0_STATE_EMIRATE_MASTER WHERE EMIRATE_CODE=RESI_ADD_STATE_EMIRATE),"
					+ " RESI_ADD_STATE_EMIRATE) AS RESI_ADD_STATE_EMIRATE, A.RESI_ADD_COUNTRY, A.RESI_ADD_ZIP,A.CUSTOMER_MOTHER_MAIDEN_NAME,A.IS_NTB_CUST ,"
					+ " A.PERM_ADD_CITY, NVL((SELECT STATE FROM USR_0_STATE_EMIRATE_MASTER WHERE EMIRATE_CODE=A.PERM_ADD_STATE_EMIRATE),PERM_ADD_STATE_EMIRATE) "
					+ " AS PERM_ADD_STATE_EMIRATE,A.SKIP_MODIFY,"
					+ " A.PERM_ADD_COUNTRY, A.PERM_ADD_ZIP, A.RESI_ADD_LANDMARK, A.EIDA_NUMBER , A.CUSTOMER_EMAIL ,"
					+ " A.CUSTOMER_MOBILE_NO, A.CUSTOMER_TYPE, A.NATIONALITY,TO_CHAR(TO_DATE(B.EIDA_EXPIRY_DATE,'YYYY-MM-DD'),'DD/MM/YYYY') as EIDA_EXPIRY_DATE, "
					+ "  A.PERM_ADD_ADDRESSLINE2, A.PERM_ADD_ADDRESSLINE3,A.SOURCING_CHANNEL FROM EXT_DSCOP A,"
					+ " EXT_DSCOP_EXTENDED B WHERE A.WI_NAME = B.WI_NAME AND A.WI_NAME = N'" + wiName + "'");*/
			
			outputXML = APCallCreateXML.APSelect("SELECT A.CUSTOMER_ID, A.SUPP_CARDHOLDER_FULL_NAME, A.PASSPORT_NO,A.GENDER,"
					+ " B.PASSPORT_EXPIRY_DATE , A.EMPLYR_ADD_CITY, "
					+ " NVL((SELECT STATE FROM USR_0_STATE_EMIRATE_MASTER WHERE EMIRATE_CODE=A.EMPLYR_ADD_STATE_EMIRATE),EMPLYR_ADD_STATE_EMIRATE)"
					+ " AS EMPLYR_ADD_STATE_EMIRATE, A.EMPLYR_ADD_COUNTRY, A.EMPLYR_ADD_ZIP, A.EMPLYR_ADD_ADDRESSLINE2, A.EMPLYR_ADD_ADDRESSLINE3,"
					+ " A.CORRES_ADD_POBOX_VILA_FLATNO, A.CORRES_ADD_FLOOR_BUILDINGNAME,A.CORRES_ADD_CITY, "
					+ " NVL((SELECT STATE FROM USR_0_STATE_EMIRATE_MASTER WHERE EMIRATE_CODE=A.CORRES_ADD_STATE_EMIRATE),CORRES_ADD_STATE_EMIRATE) AS "
					+ " CORRES_ADD_STATE_EMIRATE, A.CUSTOMER_SEGMENT,  A.CORRES_ADD_COUNTRY,  A.RESI_ADD_POBOX_VILA_FLATNO, A.RESI_ADD_FLOOR_BUILDINGNAME,"
					+ " A.RESI_ADD_STREET_NAME, A.RESI_ADD_CITY, NVL((SELECT STATE FROM USR_0_STATE_EMIRATE_MASTER WHERE EMIRATE_CODE=RESI_ADD_STATE_EMIRATE),"
					+ " RESI_ADD_STATE_EMIRATE) AS RESI_ADD_STATE_EMIRATE, A.RESI_ADD_COUNTRY, A.RESI_ADD_ZIP,A.CUSTOMER_MOTHER_MAIDEN_NAME,A.IS_NTB_CUST ,"
					+ " A.PERM_ADD_CITY, NVL((SELECT STATE FROM USR_0_STATE_EMIRATE_MASTER WHERE EMIRATE_CODE=A.PERM_ADD_STATE_EMIRATE),PERM_ADD_STATE_EMIRATE) "
					+ " AS PERM_ADD_STATE_EMIRATE,A.SKIP_MODIFY,"
					+ " A.PERM_ADD_COUNTRY, A.PERM_ADD_ZIP, A.RESI_ADD_LANDMARK, A.EIDA_NUMBER , A.CUSTOMER_EMAIL ,"
					+ " A.CUSTOMER_MOBILE_NO, A.CUSTOMER_TYPE, A.NATIONALITY,B.EIDA_EXPIRY_DATE, "
					+ "  A.PERM_ADD_ADDRESSLINE2, A.PERM_ADD_ADDRESSLINE3,A.SOURCING_CHANNEL FROM EXT_DSCOP A,"
					+ " EXT_DSCOP_EXTENDED B WHERE A.WI_NAME = B.WI_NAME AND A.WI_NAME = N'" + wiName + "'");

			
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "MODCUST001", "COPModifyCustomer Started", sessionId);
			if(mainCode == 0){
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"DSCOPModifyCustomer TotalRetrieved: "+Integer.parseInt(xp.getValueOf("TotalRetrieved")));
				if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){

					customerId = xp.getValueOf("CUSTOMER_ID");
					customerName = xp.getValueOf("SUPP_CARDHOLDER_FULL_NAME");
					passportNo = xp.getValueOf("PASSPORT_NO");
					sPassportGender=xp.getValueOf("GENDER");
					passportExpiryDate = xp.getValueOf("PASSPORT_EXPIRY_DATE");
					corresFlatNo = xp.getValueOf("CORRES_ADD_POBOX_VILA_FLATNO");
					corresbuilding = xp.getValueOf("CORRES_ADD_FLOOR_BUILDINGNAME");
					corresStreet = xp.getValueOf("CORRES_ADD_STREET_NAME");
					corresCity = xp.getValueOf("CORRES_ADD_CITY");
					corresEmirate = xp.getValueOf("CORRES_ADD_STATE_EMIRATE");
					corresCountry = xp.getValueOf("CORRES_ADD_COUNTRY");
					custMotherMaidenName = xp.getValueOf("CUSTOMER_MOTHER_MAIDEN_NAME");
					isNTBCust = xp.getValueOf("IS_NTB_CUST");
					perCity = xp.getValueOf("PERM_ADD_CITY");
					perEmirate = xp.getValueOf("PERM_ADD_STATE_EMIRATE");
					skipModify  = xp.getValueOf("SKIP_MODIFY");
					perCountry = xp.getValueOf("PERM_ADD_COUNTRY");
					perZip = xp.getValueOf("PERM_ADD_ZIP");
					sEidaNumber=xp.getValueOf("EIDA_NUMBER");
					sMobileno=xp.getValueOf("CUSTOMER_MOBILE_NO");
					customerNationality = xp.getValueOf("NATIONALITY");
					eidaExpiryDate = xp.getValueOf("EIDA_EXPIRY_DATE");
					permAddLine2 = xp.getValueOf("PERM_ADD_ADDRESSLINE2");
					permAddLine3 = xp.getValueOf("PERM_ADD_ADDRESSLINE3");
					cbrValue23 = "1";		//neutral case				
				}

			}
			senderID = defaultMap.get("SENDER_ID");

		}catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "MODCUST100", e, sessionId);
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
			.append("<EngineName>"+ DSCOPConfigurations.getInstance().CabinetName+ "</EngineName>").append("\n")
			.append("<winame>" + wiName + "</winame>").append("\n")
			.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
			.append("<Calltype>DSCOP</Calltype>").append("\n")
			.append("<DSCOPCallType>Modify_Customer</DSCOPCallType>").append("\n")
			.append("<REF_NO>" + refNo + "</REF_NO>")
			.append("<OLDREF_NO>" + refNo + "</OLDREF_NO>")
			.append("<senderID>" + senderID + "</senderID>").append("\n")
			.append("<customerId>"+ customerId +"</customerId>").append("\n")
			.append("<customerName>"+ customerName +"</customerName>").append("\n")
			.append("<custPassportNumber>"+ passportNo +"</custPassportNumber>").append("\n")
			.append("<custSex>"+ sPassportGender +"</custSex>").append("\n")
			.append("<custPassportExpiryDate>"+ passportExpiryDate +"</custPassportExpiryDate>").append("\n")
			.append("<custMobile>"+ sMobileno +"</custMobile>").append("\n")
			.append("<custEIDA>"+ sEidaNumber +"</custEIDA>").append("\n")
			.append("<eidaExpiryDate>"+ eidaExpiryDate +"</eidaExpiryDate>").append("\n");
			//newely addded for RM code
			inputXML.append("<misCodes>")
			.append("<misCode>")
			.append("<misCodeType>Comp</misCodeType>")
			.append("<misCodeNumber>2</misCodeNumber>")
			.append("<misCodeText>RET110063</misCodeText>")
			.append("</misCode>")
			.append("</misCodes>");

			inputXML.append("<Res_Addresses>")
			.append("<addressType>C</addressType>").append("\n")
			.append("<addressLine1>"+ corresFlatNo +"</addressLine1>").append("\n")
			.append("<addressLine2>"+ corresbuilding +"</addressLine2>").append("\n")
			.append("<addressLine3>"+ corresStreet +"</addressLine3>").append("\n")
			.append("<addressCity>"+ corresCity +"</addressCity>").append("\n")
			.append("<addressState>"+ corresEmirate +"</addressState>").append("\n")
			.append("<addressCountry>"+getCountryCode(corresCountry)+"</addressCountry>").append("\n")
			.append("</Res_Addresses>");

			boolean perAddressFlag = true;
			if("AE".equalsIgnoreCase(customerNationality)){
				perAddressFlag = false;
			}

			if(perAddressFlag) {
				inputXML.append("<Per_Addresses>")
				.append("<addressType>P</addressType>").append("\n")
				.append("<addressLine1>"+ perZip +"</addressLine1>").append("\n")
				.append("<addressLine2>"+ permAddLine2 +"</addressLine2>").append("\n")
				.append("<addressLine3>"+ permAddLine3 +"</addressLine3>").append("\n")
				.append("<addressCity>"+ perCity +"</addressCity>").append("\n")
				.append("<addressState>"+ perEmirate +"</addressState>").append("\n")
				.append("<addressCountry>"+ perCountry +"</addressCountry>").append("\n")
				.append("</Per_Addresses>");
			}
			if(!"N".equalsIgnoreCase(isNTBCust)){
				inputXML.append("<custMotherMaidenName>"+ custMotherMaidenName +"</custMotherMaidenName>").append("\n");
			}
			inputXML.append("<codMntOption>M</codMntOption>").append("\n")
			.append("<CBRCodes>")
			.append("<CBRCode>")
			.append("<Tag>23</Tag>")
			.append("<CBRValue>"+cbrValue23+"</CBRValue>")
			.append("</CBRCode>");
			inputXML.append("</CBRCodes>")
			.append("</APWebService_Input>");
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SuppModifyCustomer inputXML ===> " + inputXML.toString());
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "MODCUST100", e, sessionId);
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
			if(!prevStageNoGo){
				XMLParser xp = new XMLParser(outputXML);
				returnCode = Integer.parseInt(xp.getValueOf("returnCode", "1", true));
				errorDescription = xp.getValueOf("errorDescription", "", true);
				errorDetail = xp.getValueOf("errorDetail", "", true);
				status = xp.getValueOf("Status", "", true);
				reason = xp.getValueOf("Reason", "", true);
				if(returnCode == 0){
					callStatus = "Y";
					finaloutputXml = "<returnCode>0</returnCode>";
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "MODCUST090", "SuppModifyCustomer Successfully Executed", sessionId);
				} else {
					callStatus = "N";
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "MODCUST101", "SuppModifyCustomer Failed", sessionId);
				}
			} else {
				callStatus = "N";
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "MODCUST102", "SuppModifyCustomer Failed", sessionId);
			}
			updateCallOutput(inputXml);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "MODCUST100", e, sessionId);
		}
	}

	@Override
	public String call() throws Exception {
		String inputXml = createInputXML();
		finaloutputXml = "<returnCode>0</returnCode>";
		try {
			if("N".equalsIgnoreCase(isNTBCust) || "Y".equalsIgnoreCase(skipModify)){
				callStatus = "X";
				errorDescription="Modification Customer Call Skipped -- ETB Case";
				finaloutputXml = "<returnCode>0</returnCode><errorDescription>"+errorDescription+"</errorDescription>";
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "MODCUST090", "Modification Customer Call Skipped", sessionId);
				updateCallOutput(inputXml);
			}else if(!prevStageNoGo ){
				finaloutputXml = ExecuteXML.executeWebServiceSocket(inputXml);
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SuppModifyCustomer outputXml ===> " + finaloutputXml);
				if(finaloutputXml==null ||finaloutputXml.equalsIgnoreCase("")){
					finaloutputXml= "<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>";
				}
				responseHandler(finaloutputXml, inputXml);
			} else {
				callStatus = "N";
				updateCallOutput(inputXml);
			}
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "MODCUST002", "SuppModifyCustomer outputXml: "+finaloutputXml, sessionId);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "MODCUST100", e, sessionId);
		}
		return finaloutputXml;
	}

	@Override
	public void executeDependencyCall() throws Exception{
		try{
			String outputXMLTemp = "";
			outputXMLTemp = CallHandler.getInstance().executeDependencyCall(callEntity, defaultMap, sessionId, wiName, prevStageNoGo);
			if(!outputXMLTemp.isEmpty()){
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "MODCUST003", "ECB DependencyCall:"+callEntity.getDependencyCallID(), sessionId);
				finaloutputXml = outputXMLTemp;
			}else {
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "MODCUST003", "ECB DependencyCall Not Found", sessionId);
			}
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "MODCUST003", "SuppModifyCustomer DependencyCall: "+callEntity.getDependencyCallID(), sessionId);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "MODCUST100", e, sessionId);
		}
	}

	public void updateCallOutput(String inputXml) throws Exception {
		try {

			APCallCreateXML.APUpdate("USR_0_DSCOP_CALL_OUT", "CALL_STATUS,ERROR_DESCRIPTION", "'Y','Processed By Expiry Utility'", " WI_NAME = N'"+ wiName +"' AND CALL_NAME = 'SuppModifyCustomer' AND CALL_STATUS ='N' and STAGE_ID= '"+stageId+"'", sessionId);

			String valList = "'"+ wiName +"',"+ stageId +", 'SuppModifyCustomer', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"'";
			APCallCreateXML.APInsert("USR_0_DSCOP_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
					+ "ERROR_DETAIL, STATUS, REASON", valList, sessionId);

			if(callStatus.equals("Y")){
				String valList2 = "'"+ wiName +"',"+ stageId +", 'SuppModifyCustomer', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml+"', 0";
				APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON,"
						+ " RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);			
				executeDependencyCall();
			}else if(callStatus.equals("X")){
				executeDependencyCall();
			}else {
				String valList2 = "'"+ wiName +"',"+ stageId +", 'SuppModifyCustomer', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml+"', 1";
				APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON, "
						+" RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "MODCUST100", e, sessionId);
		}
	}
	
	public String getCountryCode(String country) throws Exception {
		String outputXML2 = APCallCreateXML.APSelect("SELECT COUNTRY_CODE FROM USR_0_COUNTRY_MAST WHERE COUNTRY = UPPER('"+country+"')");
		String countryCode = "";
		XMLParser xp2 = new XMLParser(outputXML2);
		int mainCode2 = Integer.parseInt(xp2.getValueOf("MainCode"));
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"COUNTRY_CODE --> COUNTRY_DESC mainCode2: "+mainCode2);
		if(mainCode2 == 0){
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"COUNTRY_CODE --> COUNTRY_DESC TotalRetrieved: "+Integer.parseInt(xp2.getValueOf("TotalRetrieved")));
			if(Integer.parseInt(xp2.getValueOf("TotalRetrieved")) > 0){
				countryCode = xp2.getValueOf("COUNTRY_CODE");
				}
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"countryCode: "+countryCode);
		}
		return countryCode;
	}
}
