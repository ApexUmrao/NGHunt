package com.newgen.cbg.calls;

import java.text.SimpleDateFormat;
import java.util.Date;
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

public class SuppAddSRServiceRequest implements ICallExecutor, Callable<String>{
	
	private String wiName="";
	private int stageId;
	private String sessionId="";
	private String callStatus="";
	private int returnCode;
	private String errorDescription="";
	private String errorDetail="";
	private String status="";
	private String reason="";
	private String senderID="CMS";
	private String refNo="";
	private boolean prevStageNoGo;
	private Map<String, String> defaultMap;
	private CallEntity callEntity;
	private String auditCallName = "SR DSCOP";
	StringBuilder sb = new StringBuilder();
	private String module = "SR";
	private String unit = "CBG";
	private String sourceChannel = "CBG";
	private String typeIdentifierNo = "776";
	private String serialNo = "";
	private String moduleEvent = "";
	private String modeOfOperation = "Create";
	private String notification = "Y";
	private String assignmentType = "N";
	private String submissionReq = "Y";
	private String primaryCid="";
	private String custFullName="";
	private String primaryCardNo="";
	private String embossName="";
	private String relationship="";
	private String dob="";
	private String creditLimit="";
	private String srStatus="";
	private String passportNo="";
	private String eidaNumber="";
	private String eidaExpiryDate="";
	private String gender="";
	private String nameMissmatch="";
	private String card="";
	private String passportExpDate="";
	
	String outputXML ="";
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY HH:mm:ss");
	String currentDate = sdf.format(new Date());
	
	public SuppAddSRServiceRequest(Map<String, String> defaultMap, String sessionId, String stageId, String wiName,
			Boolean prevStageNoGo, CallEntity callEntity) {

		this.sessionId = sessionId;
		this.wiName = wiName;
		this.stageId = Integer.parseInt(stageId);
		this.prevStageNoGo = prevStageNoGo;
		this.defaultMap = defaultMap;
		this.callEntity = callEntity;
		

		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SuppAddSRServiceRequest StageID ===> " + stageId);

		try {
			outputXML = fetchLeadCreationData();
			handleLeadCreationData(outputXML);
			//createSRMap();
			
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "ADDSR100", e, sessionId);
		}
	}
	


	@Override
	public void responseHandler(String outputXML, String inputXml) {
		try {
			XMLParser xp = new XMLParser(outputXML);
			returnCode = Integer.parseInt(xp.getValueOf("returnCode", "1", true));
			
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"returnCode : "+returnCode);
			
			errorDescription = xp.getValueOf("errorDescription", "", true);
			errorDetail = xp.getValueOf("errorDetail", "", true);
			status = xp.getValueOf("responseStatus", "", true);
			reason = xp.getValueOf("responseDescription", "", true);
			
			if(returnCode == 0) {
				DSCOPDBLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, wiName, auditCallName,"ADDSR090", "Add SR DSCOP Successfully Completed", sessionId);
				callStatus = "Y";
				status = "Success";

				String srNumber = xp.getValueOf("SRNo", "", true);
				String srStatus = xp.getValueOf("SRStatus", "", true);
				
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"SRNumber  : "+srNumber + " || SRStatus : " + srStatus);
				
	
				String output = APCallCreateXML.APUpdate("EXT_DSCOP", "LEAD_REF_NO,SRSTATUS",
						"'"+srNumber+"','"+srStatus+"'", " WI_NAME = '"+ wiName +"'", sessionId);
				XMLParser xp1 = new XMLParser(output);
				int mainCode = Integer.parseInt(xp1.getValueOf("MainCode"));
				if(mainCode == 0){
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Lead Creation : "+ mainCode);
				}
			} else {
				
				callStatus = "N";
			}
			
			updateCallOutput(inputXml);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "exception in SuppAddSRServiceRequest response handler : "+e);
			DSCOPDBLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,  wiName, auditCallName, "ADDSR100", e, sessionId);
		}
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		try {
			
			String valList = "'"+ wiName +"',"+ stageId +", 'SuppAddSRServiceRequest', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"'";
			APCallCreateXML.APInsert("USR_0_DSCOP_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
					+ "ERROR_DETAIL, STATUS, REASON", valList, sessionId);

			if(callStatus.equals("Y")){
				String valList2 = "'"+ wiName +"',"+ stageId +", 'SuppAddSRServiceRequest', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml+"', 0";
				APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON,"
						+ " RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);
				executeDependencyCall();
			}
			else {
				String valList2 = "'"+ wiName +"',"+ stageId +", 'SuppAddSRServiceRequest', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml+"', 1";
				APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON, "
						+" RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SLDCRTN100", e, sessionId);
		}
	}

	@Override
	public String call() throws Exception {
		String inputXml = createInputXML();
		String outputXml = "";
		try {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "InputXML ===>" + inputXml);
			if(!prevStageNoGo){
				outputXml = ExecuteXML.executeWebServiceSocket(inputXml);
			}
			//outputXml = ExecuteXML.executeXML(inputXml); // commented due to WI Stuck at Initiation/Data Handoff issue raised on production
			XMLParser xp = new XMLParser(outputXml);
			returnCode = Integer.parseInt(xp.getValueOf("returnCode", "1", true));
			
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "returnCode : call SuppAddSRServiceRequest "+returnCode);
			
			responseHandler(outputXml, inputXml);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "exception while calling SuppAddSRServiceRequest : "+e);
			DSCOPDBLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "ADDSR100", e, sessionId);
		}
		return outputXml;
	}
	
//	public void createSRMap() throws NGException{
//		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Inside createDefaultValueMap");
//
//		String outputXML = APCallCreateXML.APSelect("SELECT ATTRIBUTE_NAME, EXT_FIELD_MAP "
//				+ " FROM CCL_LEAD_RESPONSE_MAPPING WHERE REQUEST_TYPE = '" + RequestType + "' AND DEFAULT_KEY = 'I'");
//		XMLParser xp = new XMLParser(outputXML);
//		int start = xp.getStartIndex("Records", 0, 0);
//		int deadEnd = xp.getEndIndex("Records", start, 0);
//		int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
//		int end = 0;
//		attributeNames = "";
//		attributeColumns = "";
//		for(int i = 0; i < noOfFields; ++i){
//			start = xp.getStartIndex("Record", end, 0);
//			end = xp.getEndIndex("Record", start, 0);
//			if(i == (noOfFields - 1)){
//				attributeNames = attributeNames + xp.getValueOf("ATTRIBUTE_NAME", start, end) +"";
//				attributeColumns = attributeColumns + xp.getValueOf("EXT_FIELD_MAP", start, end) +"";
//
//			}
//			else{
//				attributeNames = attributeNames + xp.getValueOf("ATTRIBUTE_NAME", start, end) +",";
//				attributeColumns = attributeColumns + xp.getValueOf("EXT_FIELD_MAP", start, end) +",";
//			}
//			//defaultLRMap.put(xp.getValueOf("ATTRIBUTE_NAME", start, end), xp.getValueOf("EXT_FIELD_MAP", start, end));
//		}
//		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,attributeNames.toString()+""+ "attributeColumns "+attributeColumns);
//	}

	@Override
	public String executeCall(HashMap<String, String> input) throws Exception {
		return call();
	}

	@Override
	public void executeDependencyCall() throws Exception {
		try{
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "SLDCRTN003", "SuppAddSRServiceRequest DependencyCall: "+callEntity.getDependencyCallID(), sessionId);
			CallHandler.getInstance().executeDependencyCall(callEntity, defaultMap, sessionId, wiName, prevStageNoGo);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SLDCRTN100", e, sessionId);
		}
		
	}


	@Override
	public String createInputXML() throws Exception {
		StringBuilder inputXml = new StringBuilder();
		try {
			refNo = DSCOPUtils.getInstance().generateSysRefNumber();
			
			inputXml.append("<?xml version=\"1.0\"?>").append("\n")
			.append("<APWebService_Input>").append("\n")
			.append("<Option>WebService</Option>").append("\n")
			.append("<EngineName>" + DSCOPConfigurations.getInstance().CabinetName + "</EngineName>").append("\n")
			.append("<WiName>" + wiName + "</WiName>").append("\n")
			.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
			.append("<Calltype>DSCOP</Calltype>").append("\n")
			.append("<DSCOPCallType>AddSRServiceRequest</DSCOPCallType>").append("\n")
			.append("<REF_NO>" + refNo + "</REF_NO>").append("\n")
			.append("<OLDREF_NO>"+ refNo +"</OLDREF_NO>").append("\n")
			.append("<senderID>" + senderID + "</senderID>").append("\n")
			.append("<module>" + module + "</module>" ).append("\n")  
			.append("<unit>" + unit + "</unit>" ).append("\n")
			.append("<typeIdentifierNo>" + typeIdentifierNo + "</typeIdentifierNo>").append("\n")
			.append("<serialNo>" + serialNo + "</serialNo>" ).append("\n")
			.append("<srStatus>" + srStatus + "</srStatus>" ).append("\n")         
			.append("<moduleEvent>" + moduleEvent + "</moduleEvent>" ).append("\n")
			.append("<sourceChannel>" + sourceChannel + "</sourceChannel>").append("\n")
			.append("<modeOfOperation>" + modeOfOperation + "</modeOfOperation>" ).append("\n")
			.append("<notification>" + notification + "</notification>" ).append("\n")       
			.append("<assignmentType>" + assignmentType + "</assignmentType>" ).append("\n")  
			.append("<submissionReq>" + submissionReq + "</submissionReq>" ).append("\n")
			.append(sb.toString())
			.append("</APWebService_Input>");
		} catch (Exception e) {
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "ADDSR100", e, sessionId);
		}
		
		return inputXml.toString();
	}

	public String fetchLeadCreationData() throws Exception {
		return APCallCreateXML.APSelect(" SELECT  A.PRIMARY_CID AS CUSTOMERID ,A.SUPP_CARDHOLDER_FULL_NAME AS CUSTOMERNAME ,"
				+ " B.PRIMARY_CARD AS PRIMARYCARDNUM ,B.EMBOSSED_NAME AS SUPPEMBOSSNAME ,"
				+ " B.RELATIONSHIP AS SCRELATIONSHIP ,TO_CHAR(TO_DATE(A.DOB,'DD/MM/YYYY'),'MM/DD/YYYY') AS DOB,B.PERC_CREDIT_LIMIT AS SCCREDITLIMIT, "
				+ " A.PASSPORT_NO,A.EIDA_NUMBER,TO_CHAR(TO_DATE(B.EIDA_EXPIRY_DATE,'YYYY-MM-DD'),'MM/DD/YYYY') as EIDAEXP ,TO_CHAR(TO_DATE(B.PASSPORT_EXPIRY_DATE,'YYYY-MM-DD'),'MM/DD/YYYY') AS PASSPORTEXP,A.GENDER,A.NAME_MISSMATCH,A.ASSOCIATE_CARD  FROM EXT_DSCOP A, EXT_DSCOP_EXTENDED B "
				+ " WHERE A.WI_NAME = B.WI_NAME AND A.WI_NAME = N'" + wiName + "'");
	}

	private void handleLeadCreationData(String outputXML) throws Exception {
		XMLParser xp = new XMLParser(outputXML);
		int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		if(mainCode == 0){
			
			if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
				primaryCid = xp.getValueOf("CUSTOMERID");
				custFullName = xp.getValueOf("CUSTOMERNAME");
				primaryCardNo = xp.getValueOf("PRIMARYCARDNUM");
				embossName = xp.getValueOf("SUPPEMBOSSNAME");
				relationship = xp.getValueOf("SCRELATIONSHIP");
				dob = xp.getValueOf("DOB");
				creditLimit =  xp.getValueOf("SCCREDITLIMIT");
				passportNo = xp.getValueOf("PASSPORT_NO");
				eidaNumber = xp.getValueOf("EIDA_NUMBER");
				eidaExpiryDate = xp.getValueOf("EIDAEXP");
				gender = xp.getValueOf("GENDER");
				nameMissmatch = xp.getValueOf("NAME_MISSMATCH");
				card = xp.getValueOf("ASSOCIATE_CARD");
				passportExpDate = xp.getValueOf("PASSPORTEXP");
				
				srStatus = "Pending";
			}

			sb.append("<serviceModuleDtls>");
			sb.append("<entityName>SRDetail</entityName>");
			sb.append("<entityRecord>");
			
			sb.append("<attributeNameValPair>");
			sb.append("<attributeName>CUSTOMERID</attributeName>");
			sb.append("<attributeValue>"+primaryCid+"</attributeValue>");
			sb.append("</attributeNameValPair>");
			
			sb.append("<attributeNameValPair>");
			sb.append("<attributeName>REQUESTTYPE</attributeName>");
			sb.append("<attributeValue>"+card+"</attributeValue>");
			sb.append("</attributeNameValPair>");
			
			
			sb.append("<attributeNameValPair>");
			sb.append("<attributeName>CUSTOMERNAME</attributeName>");
			sb.append("<attributeValue>"+custFullName+"</attributeValue>");
			sb.append("</attributeNameValPair>");
			
			sb.append("<attributeNameValPair>");
			sb.append("<attributeName>SRSTATUS</attributeName>");
			sb.append("<attributeValue></attributeValue>");
			sb.append("</attributeNameValPair>");
			
			sb.append("<attributeNameValPair>");
			sb.append("<attributeName>PRIMARYCARDNUM</attributeName>");
			sb.append("<attributeValue>"+primaryCardNo+"</attributeValue>");
			sb.append("</attributeNameValPair>");
			
			sb.append("<attributeNameValPair>");
			sb.append("<attributeName>CARDDELMOD</attributeName>");
			sb.append("<attributeValue></attributeValue>");
			sb.append("</attributeNameValPair>");
			
			sb.append("<attributeNameValPair>");
			sb.append("<attributeName>SUPPEMBOSSNAME</attributeName>");
			sb.append("<attributeValue>"+embossName+"</attributeValue>");
			sb.append("</attributeNameValPair>");

			sb.append("<attributeNameValPair>");
			sb.append("<attributeName>SCRELATIONSHIP</attributeName>");
			sb.append("<attributeValue>"+relationship+"</attributeValue>");
			sb.append("</attributeNameValPair>");
			
			sb.append("<attributeNameValPair>");
			sb.append("<attributeName>SCMARITALSTATUS</attributeName>");
			sb.append("<attributeValue></attributeValue>");
			sb.append("</attributeNameValPair>");
			  
			sb.append("<attributeNameValPair>");
			sb.append("<attributeName>DOB</attributeName>");
			sb.append("<attributeValue>"+dob+"</attributeValue>");
			sb.append("</attributeNameValPair>");
			
			sb.append("<attributeNameValPair>");
			sb.append("<attributeName>SCCREDITLIMIT</attributeName>");
			sb.append("<attributeValue>"+creditLimit+"</attributeValue>");
			sb.append("</attributeNameValPair>");
			
			sb.append("<attributeNameValPair>");
			sb.append("<attributeName>MU_PASSPORT_EXPIRY_DATE</attributeName>");
			sb.append("<attributeValue>"+passportExpDate+"</attributeValue>");
			sb.append("</attributeNameValPair>");
			
			sb.append("<attributeNameValPair>");
			sb.append("<attributeName>PASSPORT_NO</attributeName>");
			sb.append("<attributeValue>"+passportNo+"</attributeValue>");
			sb.append("</attributeNameValPair>");
			
			
			sb.append("<attributeNameValPair>");
			sb.append("<attributeName>EIDA_NUMBER</attributeName>");
			sb.append("<attributeValue>"+eidaNumber+"</attributeValue>");
			sb.append("</attributeNameValPair>");
			
			sb.append("<attributeNameValPair>");
			sb.append("<attributeName>EIDA_EXPIRY_DATE</attributeName>");
			sb.append("<attributeValue>"+eidaExpiryDate+"</attributeValue>");
			sb.append("</attributeNameValPair>");
			
			sb.append("<attributeNameValPair>");
			sb.append("<attributeName>GENDER</attributeName>");
			sb.append("<attributeValue>"+gender+"</attributeValue>");
			sb.append("</attributeNameValPair>");
			
			sb.append("<attributeNameValPair>");
			sb.append("<attributeName>NAME_MISSMATCH</attributeName>");
			sb.append("<attributeValue>"+nameMissmatch+"</attributeValue>");
			sb.append("</attributeNameValPair>");
			
			sb.append("<attributeNameValPair>");
			sb.append("<attributeName>CALLIGRAPHY_IMAGE_ID</attributeName>");
			sb.append("<attributeValue>AB123</attributeValue>");
			sb.append("</attributeNameValPair>");
			sb.append("</entityRecord>");
			sb.append("</serviceModuleDtls>");
			}
       }
	}
