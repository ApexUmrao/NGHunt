package com.newgen.cbg.calls;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;

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

public class SuppNameSplit implements ICallExecutor, Callable<String>  {
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
	private String ruleFlowGroup;
	private String requestChannelName;
	private String passportMRZ;
	private String passportFullNameMRZ;
	private String passportFirstName;
	private String passportMiddleNameMRZ;
	private String passportLastName;
	private String refNo;
	private boolean prevStageNoGo;
	private String nameSplitErrorFlag="";
	private String accountTitle = "";
	private String truncatedName;
	private boolean isSkipCall=false;
	private Map<String, String> defaultMap;
	private CallEntity callEntity;
	private String auditCallName = "SUPPSUPPNMSPLT";

	public SuppNameSplit(Map<String, String> defaultMap, String sessionId, String stageId, String wiName, Boolean prevStageNoGo, CallEntity callEntity)
	{
		this.sessionId=sessionId;
		this.wiName = wiName;
		this.stageId = Integer.parseInt(stageId);
		this.prevStageNoGo = prevStageNoGo;
		this.defaultMap = defaultMap;
		this.callEntity = callEntity;
		ruleFlowGroup = "MRZ-Code";
		requestChannelName = "WMS-BPM";
		String outputXML;
		try {

			outputXML = fetchNameSplitData();
			handleNameSplitData(outputXML);

		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SUPPNMSPLT100", e, sessionId);
		}
	}

	@Override
	public String call() throws Exception {
		String inputXml = createInputXML();
		String outputXml = "<returnCode>0</returnCode>";
		try {
			if(!prevStageNoGo){
				if(isSkipCall) {
					outputXml = ExecuteXML.executeWebServiceSocketWithTimeout(inputXml);
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "NameSplit outputXml ===>>"+ outputXml);
					if(outputXml==null ||outputXml.equalsIgnoreCase("")){
						outputXml= "<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>";
					}
					responseHandler(outputXml, inputXml);
				} else {
					callStatus = "X";
					errorDescription="Name Split Call Skipped";
					outputXml = "<returnCode>0</returnCode><errorDescription>"+errorDescription+"</errorDescription>";
					executeDependencyCall();
					updateCallOutput(inputXml);
				}
			} else {
					callStatus = "F";
					updateCallOutput(inputXml);
				}
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "NameSplit outputXml ===> "+ outputXml);
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "SUPPNMSPLT002", "NameSplit outputXml: "+outputXml, sessionId);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SUPPNMSPLT100", e, sessionId);
		}
		return outputXml;
	}

	@Override
	public void executeDependencyCall() throws Exception{
		try{
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "SUPPNMSPLT003", "NameSplit DependencyCall: "+callEntity.getDependencyCallID(), sessionId);
			CallHandler.getInstance().executeDependencyCall(callEntity, defaultMap, sessionId, wiName, prevStageNoGo);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SUPPNMSPLT100", e, sessionId);
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
			.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
			.append("<WINAME>" + wiName + "</WINAME>").append("\n")
			.append("<Calltype>DSCOP</Calltype>").append("\n")
			.append("<DSCOPCallType>InqBusinessRules</DSCOPCallType>").append("\n")
			.append("<REF_NO>" + refNo + "</REF_NO>").append("\n")
			.append("<OLDREF_NO>"+ refNo +"</OLDREF_NO>").append("\n")
			.append("<senderID>" + senderID + "</senderID>").append("\n")
			.append("<ruleFlowGroup>" + ruleFlowGroup + "</ruleFlowGroup>").append("\n")
			.append("<requestChannelName>" + requestChannelName + "</requestChannelName>").append("\n")
			.append("<Eligibility><PAYLOAD type=\"MRZ_SPLITTER\"><MRZCODE><CODE><![CDATA["+ passportMRZ +"]]></CODE></MRZCODE></PAYLOAD></Eligibility>").append("\n")
			.append("</APWebService_Input>");
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SUPPNMSPLT100", e, sessionId);
		}
		return inputXML.toString();  
	}

	@Override
	public String executeCall(HashMap<String, String> input) throws Exception {
		return call();
	}

	@Override
	public void responseHandler(String outputXML, String inputXML) throws Exception {
		String mrzErrorFlag = "Y";
		try {
			XMLParser xp = new XMLParser(outputXML);
			returnCode = Integer.parseInt(xp.getValueOf("returnCode", "1", true));
			errorDescription = xp.getValueOf("errorDescription", "", true);
			errorDetail = xp.getValueOf("errorDetail", "", true);
			status = xp.getValueOf("Status", "", true);
			reason = "";
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "NameSplit MRZDECODED ===> "+ reason);
			if(returnCode == 0) {
				callStatus = "Y";
				passportFullNameMRZ = xp.getValueOf("FULLNAME", "", true);
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "NameSplit passportFullNameMRZ ===> "+ passportFullNameMRZ);
				passportFirstName = xp.getValueOf("FIRSTNAME", "", true);
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "NameSplit passportFirstName ===> "+ passportFirstName);
				passportMiddleNameMRZ = xp.getValueOf("SECONDNAME", "", true);
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "NameSplit passportMiddleNameMRZ ===> "+ passportMiddleNameMRZ);
				passportLastName = xp.getValueOf("LASTNAME", "", true);
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "NameSplit passportLastName ===> "+ passportLastName);
				mrzErrorFlag = xp.getValueOf("ERRORFLAG", "", true);
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "NameSplit mrzErrorFlag ===> "+ mrzErrorFlag);
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "SUPPNMSPLT090", " Name Extracted Error - "+mrzErrorFlag, sessionId);

				if(!validateRegex(passportFullNameMRZ) || !validateRegex(passportFirstName) || !validateRegex(passportMiddleNameMRZ) 
						|| !validateRegex(passportLastName) || passportFirstName.equals("") || passportLastName.equals("")){
					mrzErrorFlag = "Yes";
				}
				if(mrzErrorFlag.equalsIgnoreCase("No")){
					reason = "ERROR FLAG: No";
					nameSplitErrorFlag = "N";
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "SUPPNMSPLT091", " Name Extraction Not Found", sessionId);
				} else if(mrzErrorFlag.equalsIgnoreCase("Yes")){
					nameSplitErrorFlag = "Y";
					reason = "ERROR FLAG: Yes";
					callStatus = "T";
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "SUPPNMSPLT092", " Name Extraction Found", sessionId);
					DSCOPUtils.getInstance().addAudit(sessionId, wiName, "REPAIR", "NAME EXTRACTION SENT TO REPAIR", "");				
				} else if(mrzErrorFlag.equalsIgnoreCase("AUTO_CORRECT")){
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "NameSplit GOING for ABBREVIATION ===> ");
					accountTitle = abbreviateName(passportFirstName, passportMiddleNameMRZ, passportLastName);
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Name Split accountTitle===> "+accountTitle);
					if(accountTitle.length() > 35){
						nameSplitErrorFlag = "Y";
						reason = "ERROR FLAG: Yes";
						callStatus = "T";
						DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "SUPPNMSPLT093", " Account Title > 35 characters after Auto Correct", sessionId);
						DSCOPUtils.getInstance().addAudit(sessionId, wiName, "REPAIR", "ACCOUNT TITLE > 35 CHARACTERS AFTER AUTO REPAIR", "");
					} else {
						nameSplitErrorFlag = "N";
						callStatus = "Y";
						DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "SUPPNMSPLT094", " Account Title Auto Derived to make it <=35 characters", sessionId);
						DSCOPUtils.getInstance().addAudit(sessionId, wiName, "AUTO REPAIR", "ACCOUNT TITLE AUTO DERIVED TO MAKE IT <=35 CHARACTERS", "");
					}

				} else {
					nameSplitErrorFlag = "R";
				}			
				String restrictedPassport  = xp.getValueOf("RESTRICTED_PASSPORT", "", true);
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "NameSplit restrictedPassport ===> "+ restrictedPassport);
				if(!"".equals(restrictedPassport)){
					APCallCreateXML.APUpdate("EXT_DSCOP", "RESTRICTED_PASSPORT", "'"+restrictedPassport+"'", " WI_NAME = N'"+ wiName +"'", sessionId);
				}
			} else {
				callStatus = "F";
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "SUPPNMSPLT101", "NameSplit Failed", sessionId);
			} 		
			updateCallOutput(inputXML);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SUPPNMSPLT100", e, sessionId);
		}	
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		String valList = "'"+ wiName +"',"+ stageId +", 'SuppNameSplit', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
				errorDetail +"', '"+ status +"', '"+ reason +"'";
		APCallCreateXML.APInsert("USR_0_DSCOP_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL,"
				+ " STATUS, REASON", valList, sessionId);

		if(callStatus.equals("Y")){
			String valList2 = "'"+ wiName +"',"+ stageId +", 'SuppNameSplit', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml+"', 0";
			APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON,"
					+ " RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);			
		  executeDependencyCall();
			
		}
		else {
			String valList2 = "'"+ wiName +"',"+ stageId +", 'SuppNameSplit', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml+"', 1";
			APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON, "
					+" RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);
		}
		
		try {
			if("N".equalsIgnoreCase(nameSplitErrorFlag)){
				valList = "'"+ nameSplitErrorFlag +"','"+ passportFullNameMRZ +"','"+ passportFirstName +"','"+ passportMiddleNameMRZ +"','"+ passportLastName +"','"+ accountTitle +"','','"+ passportFirstName +"','"+ passportLastName + "'";
				String output = APCallCreateXML.APUpdate("EXT_DSCOP", "NAMESPLIT_FLG, SUPP_CARDHOLDER_FULL_NAME, PASSPORT_FIRST_NAME, PASSPORT_MIDDLE_NAME, PASSPORT_LAST_NAME, ACCOUNT_TITLE, ADD_COMMTS, CRS_FIRST_NAME, CRS_LAST_NAME", 
						valList, " WI_NAME = N'"+ wiName +"'", sessionId);
				XMLParser xp = new XMLParser(output);
				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"NameSplit : "+ mainCode);
				if(mainCode == 0){
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"NameSplit : "+ mainCode);
				}
			}
			if("T".equalsIgnoreCase(callStatus) || "F".equalsIgnoreCase(callStatus)){

				String output = APCallCreateXML.APUpdate("EXT_DSCOP", "NAMESPLIT_DECISION", "'Y'", " WI_NAME = N'"+ wiName +"'", sessionId);
				XMLParser xp = new XMLParser(output);
				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"NameSplit : "+ mainCode);
				if(mainCode == 0){
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"NameSplit : "+ mainCode);
				}
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,e);
		}
	}

	private String convertMRZString(String passportMRZ){
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "passport MRZ before ==> "+ passportMRZ);
		String decodedValue = "";
		try {
			if(passportMRZ != null){
				decodedValue = new String(Base64.decodeBase64(passportMRZ.getBytes("UTF-8")));
				decodedValue = decodedValue.replaceAll((char)0+"", "");
				decodedValue = decodedValue.replaceAll((char)10+"","");
				decodedValue = decodedValue.replaceAll((char)13+"", "");
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "passport MRZ after decode ==> "+ decodedValue);
			}            
		} catch(Exception e){
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}
		return decodedValue;
	}

	private boolean checkTruncatedName(String passportMRZ){
		String decodedValue = "";
		try {
			if(passportMRZ != null){
				decodedValue = new String(Base64.decodeBase64(passportMRZ.getBytes("UTF-8")));
				decodedValue = decodedValue.replaceAll((char)0+"", "");
				decodedValue = decodedValue.substring(0,decodedValue.indexOf((char)10+""));
				char ch = decodedValue.charAt(decodedValue.length()-1);
				if((ch+"").equals("<")){
					return false;
				}
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "passport MRZ last character ==> "+ ch);
			}            

		} catch(Exception e){
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}
		return true;
	}

	private String abbreviateName(String firstName, String middleName, String lastName){
		try {
			int fnLen = firstName.length();
			int mnLen = middleName.length();
			int lnLen = lastName.length();		
			int totalLen = fnLen + mnLen + lnLen + 2;//2 for 2 spaces
			StringBuilder accountTitle = new StringBuilder(); // Before String Buffer Is used
			StringBuilder abbrMiddleName = new StringBuilder("");
			if(totalLen > 35 && mnLen > 1){
				int reductionLen = totalLen - 35;
				String[] middleNameSplit = middleName.split(" ");
				int noOfWords = middleNameSplit.length;
				for(int i = noOfWords - 1 ; i >= 0 ; --i){
					String namePart = middleNameSplit[i];
					if(reductionLen > 0){
						middleNameSplit[i] = namePart.charAt(0) + "";
						reductionLen  = reductionLen - namePart.length() + 1;
					} else {
						middleNameSplit[i] = namePart + "";
					}
				}

				for(String word: middleNameSplit){
					abbrMiddleName.append(word);
					abbrMiddleName.append(" ");
				}
			} else {
				abbrMiddleName.append(middleName);
			}
			accountTitle.append(firstName);
			accountTitle.append(" ");
			accountTitle.append(abbrMiddleName);
			accountTitle.append(lastName);
			return accountTitle+"";
		} catch(Exception e){
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}
		return "";
	}

	private boolean validateRegex(String value){
		boolean result = false;
		if(value != null){
			String regex = "^[a-zA-Z\\s]*$";
			Pattern pattern = Pattern.compile(regex);
			Matcher match = pattern.matcher(value);
			if(match.matches()){
				result = true;
			}
		}
		return result;
	}
	
	public String fetchNameSplitData() throws Exception {
		return APCallCreateXML.APSelect("SELECT DOB,PASSPORT_MRZ_STRING FROM EXT_DSCOP WHERE WI_NAME = N'" + wiName + "'");
	}

	private void handleNameSplitData(String outputXML) throws Exception {
		XMLParser xp = new XMLParser(outputXML);
		int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		if(mainCode == 0 && Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "SUPPNMSPLT001", "NameSplit Started", sessionId);
			passportMRZ = xp.getValueOf("PASSPORT_MRZ_STRING");	
			truncatedName = checkTruncatedName(passportMRZ)?"Y":"N";
			passportMRZ = convertMRZString(passportMRZ);	
		}
		senderID = defaultMap.get("SENDER_ID");
	}

}
