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

public class SuppPersonalInformation implements ICallExecutor, Callable<String> {
	private String sessionId;
	private String wiName;
	private String primaryCid;
	private String callStatus;
	private int stageID;
	private int returnCode;
	private String errorDetail;
	private String errorDescription;	
	private String status;
	private String reason;
	private String refNo;
	private String senderID;
	private boolean prevStageNoGo;
	private Map<String, String> defaultMap;
	private CallEntity callEntity;
	private String auditCallName = "SPI";
	private String customerType = "";
	String outputXml="";

	public SuppPersonalInformation(Map<String, String> defaultMap, String sessionId, String stageId, String wiName , Boolean prevStageNoGo, CallEntity callEntity){
		this.wiName = wiName;
		stageID = Integer.parseInt(stageId);
		this.sessionId = sessionId;
		this.prevStageNoGo = prevStageNoGo;
		this.defaultMap = defaultMap;
		this.callEntity = callEntity;
		primaryCid=defaultMap.get("primaryCid");
		try {
			customerType = "ETB";
			senderID = defaultMap.get("SENDER_ID");
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"SuppPersonalInformation customerId: "+wiName+" isNTBCust: "+customerType +" senderID: "+senderID +" primaryCid: "+primaryCid);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SPI100", e, sessionId);
		}
	}

	@Override
	public String call() throws Exception {
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"SuppPersonalInformation call: inside");
		String inputXml = "" ;
	    outputXml = "<returnCode>0</returnCode>";
		try {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"SuppPersonalInformation call: insideTry");
			if(!primaryCid.isEmpty() && customerType.equalsIgnoreCase("ETB")){
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"SuppPersonalInformation call:" +customerType);
				inputXml = createInputXML();
				if(!prevStageNoGo){
					outputXml = ExecuteXML.executeWebServiceSocket(inputXml);
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SuppPersonalInformation outputXml ===> " + outputXml);
					if(outputXml==null || outputXml.equalsIgnoreCase("")){
						outputXml= "<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>";
					}
					responseHandler(outputXml, inputXml);
				} else {
					callStatus = "N";
					updateCallOutput(inputXml);
				}
			} else {
				callStatus = "X";
				errorDescription = "CustomerID not Generated/NTB Customer - CALL SKIPPED";
				updateCallOutput(inputXml);
			}

		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SPI100", e, sessionId);
		}
		return outputXml;
	}

	@Override
	public String createInputXML() throws Exception {
		StringBuilder inputXml=new StringBuilder();
		try {
			refNo = DSCOPUtils.getInstance().generateSysRefNumber();
			inputXml.append("<?xml version=\"1.0\"?>").append("\n")
			.append("<APWebService_Input>").append("\n")
			.append("<Option>WebService</Option>").append("\n")
			.append("<EngineName>" + DSCOPConfigurations.getInstance().CabinetName + "</EngineName>").append("\n")
			.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
			.append("<Calltype>DSCOP</Calltype>").append("\n")
			.append("<DSCOPCallType>InqCustPersonalDtl2</DSCOPCallType>").append("\n")			
			.append("<WiName>" + wiName + "</WiName>").append("\n")
			.append("<REF_NO>" + refNo + "</REF_NO>").append("\n")
			.append("<OLDREF_NO>" + refNo + "</OLDREF_NO>").append("\n")
			.append("<senderId>WMSBPMENG</senderId>").append("\n")//NEED TO CHANGE RCRM TO SENDER ID
			.append("<customerID>" + primaryCid + "</customerID>").append("\n")
			.append("</APWebService_Input>");
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, inputXml.toString());
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SPI100", e, sessionId);
		}
		return inputXml.toString();
	}

	@Override
	public String executeCall(HashMap<String, String> input) throws Exception {
		return call();
	}

	@Override
	public void executeDependencyCall() throws Exception {
		try {
			String outputXMLTemp = "";
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "SPI004", "AS DependencyCall:"+ callEntity.getDependencyCallID(), sessionId);
			outputXMLTemp = CallHandler.getInstance().executeDependencyCall(callEntity, defaultMap, sessionId, wiName, prevStageNoGo);
			if(!outputXMLTemp.isEmpty()) {
				outputXml=outputXMLTemp;
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SPI100", e, sessionId);
		}

	}

	@Override
	public void responseHandler(String outputXML, String inputXml) throws Exception {
  String mobileNo;
  String email;
  String eidaExpDate;
  String passportDate;
		try {
			XMLParser xp = new XMLParser(outputXML);
			returnCode = Integer.parseInt(xp.getValueOf("returnCode", "1", true));
			errorDescription = xp.getValueOf("errorDescription", "", true);
			errorDetail = xp.getValueOf("errorDetail", "", true);
			status = xp.getValueOf("Status", "", true);
			reason = xp.getValueOf("Reason", "", true);
			if(returnCode == 0 || returnCode == 2){
				callStatus = "Y";
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"SuppPersonalInformation call: inside");
				XMLParser parser = new XMLParser(outputXML);
				passportDate=parser.getValueOf("expiryDate");
				eidaExpDate=parser.getValueOf("NationalIDExpiryDate");
				email=parser.getValueOf("eMail");
				mobileNo=parser.getValueOf("mobileNo");
				String gender=parser.getValueOf("gender");
				String nationCode=parser.getValueOf("nationalityDesc");
				String prefix=parser.getValueOf("prefix");
				String firstName=parser.getValueOf("firstName");
				String lastName=parser.getValueOf("lastName");
				String fullName=parser.getValueOf("fullName");
				String eidaNO=parser.getValueOf("UAENationalID");
				String dateOfBirth=parser.getValueOf("dateofBirth");
				String passportNo=parser.getValueOf("passportNo");
				String countryOfResidence=parser.getValueOf("countryOfResidence");

				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"SuppPersonalInformation call:"+passportDate+""+eidaExpDate+""+email+""+mobileNo);
				APCallCreateXML.APUpdate("EXT_DSCOP_EXTENDED", "PASSPORT_EXP_DATE,EIDA_EXP_DATE,primary_gender,primary_prefix,primary_firstname,primary_lastname,"
				+ "primary_fullname,primary_eidano,primary_dob,primary_passportno,primary_residence,primary_nationality","'"+passportDate+"','"+eidaExpDate+"',"
				+ "'"+gender+"','"+prefix+"','"+firstName+"','"+lastName+"','"+fullName+"','"+eidaNO+"','"+dateOfBirth+"','"+passportNo+"','"+countryOfResidence+"','"+nationCode+"'", " WI_NAME = '"+wiName+"'", sessionId);
				APCallCreateXML.APUpdate("EXT_DSCOP", "CUSTOMER_EMAIL,CUSTOMER_MOBILE_NO","'"+email+"','"+mobileNo+"'", " WI_NAME = '"+wiName+"'", sessionId);
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "SPI090", "SCABI Successfully Executed", sessionId);

			}else {
				callStatus = "N";
			}		 
			updateCallOutput(inputXml);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, primaryCid, auditCallName, "SPI100", e, sessionId);
		}		
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		try {
			String valList = "'"+ wiName +"',"+ stageID +", 'SuppPersonalInformation', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"'";
			APCallCreateXML.APInsert("USR_0_DSCOP_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
					+ "ERROR_DETAIL, STATUS, REASON", valList, sessionId);

			if(callStatus.equals("Y")){

				String valList2 = "'"+ wiName +"',"+ stageID +", 'SuppPersonalInformation', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml +"', 0";
				APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON,"
						+ " RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);
				executeDependencyCall();
			}
			else {
				String valList2 = "'"+ wiName +"',"+ stageID +", 'SuppPersonalInformation', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml +"', 1";
				APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON, "
						+" RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SPI100", e, sessionId);
		}

	}

}
