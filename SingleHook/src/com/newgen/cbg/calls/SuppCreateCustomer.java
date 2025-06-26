package com.newgen.cbg.calls;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

public class SuppCreateCustomer implements ICallExecutor, Callable<String> {

	private String wiName;
	private int stageId;
	private String sessionId;
	private String callStatus = "";
	private int returnCode;
	private String errorDescription;
	private String errorDetail;
	private String status;
	private String reason;
	private String custFullName;
	private String custShortName;
	private String custPrefix;
	private String custGender;
	private String custEmirate;
	private String custState;
	private String custNationality;
	private String custResidence;
	private String custNationalId;
	private String txtCustAddr1;
	private String txtCustAddr2;
	private String txtCustAddr3;
	private String txtCustDOB;
	private String custEmail;
	private String txtCustPhone;
	private String custMobile;
	private String icType;
	private String homeBranch;
	private String citizenshipId;
	private String permAddrState;
	private String custCountry;
	private String trsdFlag;
	private String isNTBCust;
	private String finalOutputXml;
	private String senderID;
	private boolean prevStageNoGo;
	private Map<String, String> defaultMap;
	private String eidaDedupe;
	private String oldRefNo;
	private String ppLocalDedupe;
	private String eidaLocalDedupe;
	private CallEntity callEntity;
	private String auditCallName = "CRCUST";
	private String eidaNationality;
	private String isEidaExpired;
	private String isPassportExpired;
	private String customerId;
	private String makerId;
	private String checkerId;
	private String refNo;
	private String customerIC;
	private String deviceID;
	private String flgCustType;
	private String dualNationality;
	private String associateCard;
	private String sMinorFlag;


	public SuppCreateCustomer(Map<String, String> defaultMap, String sessionId, String stageId, String wiName, Boolean prevStageNoGo, CallEntity callEntity){
		this.sessionId = sessionId;
		this.wiName = wiName;
		this.stageId = Integer.parseInt(stageId);
		this.prevStageNoGo = prevStageNoGo;
		this.defaultMap = defaultMap;
		this.callEntity = callEntity;

		try {
			String outputXML1 = fetchCreateCustomerData();
			handleCreateCustomerData(outputXML1);
		} catch (Exception e) {
			handleException(e);
		}
	}
	@Override
	public String call() {
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SuppCreateCustomer==> callinside1" );
		finalOutputXml = "<returnCode>0</returnCode>";
		try{
			String inputXml = createInputXML();
			if(!prevStageNoGo && (trsdFlag.equals("N")||trsdFlag.equals("A"))){
				
				if("N".equalsIgnoreCase(isNTBCust) && !"".equals(customerId)){
					APCallCreateXML.APUpdate("EXT_DSCOP", "SKIP_MODIFY","'Y'", " WI_NAME = N'"+ wiName +"'", sessionId);
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "CRCUST090", "ETB Customer ID :"+customerId, sessionId);
					executeDependencyCall();
					errorDescription="Create Customer Skipped -- ETB Case";
					finalOutputXml = "<returnCode>0</returnCode><errorDescription>"+errorDescription+"</errorDescription>";
				}
				else {
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SuppCreateCustomer==> callinside3" );
					finalOutputXml = ExecuteXML.executeWebServiceSocket(inputXml);
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SuppCreateCustomer outputXml ===> " + finalOutputXml);
					responseHandler(finalOutputXml, inputXml);
				}
			} else{
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SuppCreateCustomer==> callinside7" );
				callStatus = "N";
				finalOutputXml = "<returnCode>1</returnCode>";
				if(trsdFlag.equals("Yes"))
					errorDescription = "TRSD Flag : YES";
				updateCallOutput(inputXml);
			}

			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "CRCUST003", "CRCUST output: "+finalOutputXml, sessionId);
		} catch(Exception e){
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, e );
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "CRCUST100", e, sessionId);
		}
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SuppCreateCustomer==>" + finalOutputXml);
		return finalOutputXml;
	}

	@Override
	public void executeDependencyCall() throws Exception{
		try{
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "CRCUST004", "CRCUST DependencyCall:"+callEntity.getDependencyCallID(), sessionId);
			finalOutputXml = CallHandler.getInstance().executeDependencyCall(callEntity, defaultMap,sessionId, wiName, prevStageNoGo);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "CRCUST100", e, sessionId);
		}
	}

	@Override
	public String createInputXML() throws Exception {
		StringBuilder inputXML = new StringBuilder();
		int age = calculateAge(txtCustDOB);
		int minorAge = Integer.parseInt(defaultMap.get("MINOR_AGE"));
		if (age < minorAge) {
			sMinorFlag = "Y";
		} else {
			sMinorFlag = "N";
		}

		try {
			refNo = DSCOPUtils.getInstance().generateSysRefNumber();
			inputXML.append("<?xml version=\"1.0\"?>").append("\n")
			.append("<APWebService_Input>").append("\n")
			.append("<Option>WebService</Option>").append("\n")
			.append("<EngineName>" + DSCOPConfigurations.getInstance().CabinetName + "</EngineName>").append("\n")
			.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
			.append("<WINAME>" + wiName + "</WINAME>").append("\n")
			.append("<senderID>"+senderID+"</senderID>").append("\n")
			.append("<Calltype>DSCOP</Calltype>").append("\n")
			.append("<DSCOPCallType>" + "ADD_CUSTOMER_AUS_WBG" + "</DSCOPCallType>").append("\n")
			.append("<REF_NO>" + refNo + "</REF_NO>").append("\n")
			.append("<OLDREF_NO>"+ oldRefNo +"</OLDREF_NO>").append("\n")
			.append("<custFullName>" + custFullName + "</custFullName>").append("\n")
			.append("<custShortName>" + custShortName + "</custShortName>").append("\n")
			.append("<custPrefix>" + custPrefix + "</custPrefix>").append("\n")
			.append("<custGender>" + custGender + "</custGender>").append("\n")
			.append("<custReligion></custReligion>").append("\n")
			.append("<custMaritalStatus></custMaritalStatus>").append("\n")
			.append("<custEmirate>" + custEmirate + "</custEmirate>").append("\n")
			.append("<custState>" + custState + "</custState>").append("\n")
			.append("<custNationality>" + custNationality + "</custNationality>").append("\n")
			.append("<custResidence>" + custResidence + "</custResidence>").append("\n")
			.append("<custNationalId>" + custNationalId + "</custNationalId>").append("\n")
			.append("<custEmployerName></custEmployerName>").append("\n")
			.append("<custPosition></custPosition>").append("\n")
			.append("<custEmployerEmirate></custEmployerEmirate>").append("\n")
			.append("<custEmployerPhone></custEmployerPhone>").append("\n")
			.append("<custMonthlySalary></custMonthlySalary>").append("\n")
			.append("<txtCustAddr1>" + txtCustAddr1 + "</txtCustAddr1>").append("\n")
			.append("<txtCustAddr2>" + txtCustAddr2 + "</txtCustAddr2>").append("\n")
			.append("<txtCustAddr3>" + txtCustAddr3 + "</txtCustAddr3>").append("\n")
			.append("<txtCustDOB>"+txtCustDOB+"</txtCustDOB>").append("\n")
			.append("<custEmail>" + custEmail + "</custEmail>").append("\n")
			.append("<txtCustPhone>" + txtCustPhone + "</txtCustPhone>").append("\n")
			.append("<custMobile>" + custMobile + "</custMobile>").append("\n")
			.append("<ICType>" + icType + "</ICType>").append("\n")
			.append("<flgCustType>" + flgCustType + "</flgCustType>").append("\n")
			.append("<flgCustClass>I</flgCustClass>").append("\n")
			.append("<flgStaff></flgStaff>").append("\n")
			.append("<homeBranch>"+homeBranch+"</homeBranch>").append("\n")
			.append("<signType></signType>").append("\n")
			.append("<professCategory></professCategory>").append("\n")
			.append("<custCountry>" + custCountry + "</custCountry>").append("\n")
			.append("<zip></zip>").append("\n")
			.append("<flgMinor>" + sMinorFlag + "</flgMinor>").append("\n")
			.append("<codCustType>C</codCustType>").append("\n")
			.append("<misCod1></misCod1>").append("\n")
			.append("<misCod2></misCod2>").append("\n")
			.append("<misCod3></misCod3>").append("\n")
			.append("<misCod4></misCod4>").append("\n")
			.append("<citizenshipId>" + citizenshipId + "</citizenshipId>").append("\n")
			.append("<codEmpId></codEmpId>").append("\n")
			.append("<noOfDependants></noOfDependants>").append("\n")
			.append("<permAddr1></permAddr1>").append("\n")
			.append("<permAddr2></permAddr2>").append("\n")
			.append("<permAddr3></permAddr3>").append("\n")
			.append("<permAddrCity></permAddrCity>").append("\n")
			.append("<permAddrCountry></permAddrCountry>").append("\n")
			.append("<permAddrState></permAddrState>").append("\n")
			.append("<permAddrZip></permAddrZip>").append("\n")
			.append("<makerId></makerId>").append("\n")
			.append("<checkerId></checkerId>").append("\n")
			//			.append("<dualNationalityFlag>"+dualNationality+"</dualNationalityFlag>").append("\n")	
			.append("<secondNationality></secondNationality>").append("\n")
			.append("<countryOfIncome></countryOfIncome>").append("\n")
			.append("<annualIncome></annualIncome>").append("\n")
			.append("<eidaExpiryDate></eidaExpiryDate>").append("\n")						
			.append("</APWebService_Input>");
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SuppCreateCustomer inputXML ===> "+inputXML.toString());
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, e );
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "CRCUST100", e, sessionId);
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
			customerId = xp.getValueOf("CustomerID", "", true);
			customerIC = xp.getValueOf("CustomerIC", "", true);
			if(returnCode == 0){
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "CRCUST090",  customerId, sessionId);
				callStatus = "Y";
				APCallCreateXML.APUpdate("EXT_DSCOP", "SKIP_MODIFY,IS_NTB_CUST","'N','Y'", " WI_NAME = N'"+ wiName +"'", sessionId);
			} else {
				callStatus = "N";
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "CRCUST101",  "SuppCreateCustomer Failed", sessionId);
			}
			updateCallOutput(inputXML);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, e );
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "CRCUST100", e, sessionId);
		}		
	}

	@Override
	public void updateCallOutput(String inputXML) throws Exception {
		try {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Inside updateCallOutput callStatus ===> "+callStatus);
			
			 APCallCreateXML.APUpdate("USR_0_DSCOP_CALL_OUT", "CALL_STATUS,ERROR_DESCRIPTION", "'Y','Processed By Expiry Utility'", " WI_NAME = N'"+ wiName +"' AND CALL_NAME = N'SuppCreateCustomer' AND CALL_STATUS ='N' and STAGE_ID= '"+stageId+"'", sessionId);
			
			 String valList = "'"+ wiName +"',"+ stageId +", 'SuppCreateCustomer', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ customerId +"', '"+ reason +"'";
			 APCallCreateXML.APInsert("USR_0_DSCOP_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
					+ "ERROR_DETAIL, STATUS, REASON", valList, sessionId);

			if(oldRefNo.equals("")){
				 APCallCreateXML.APUpdate("EXT_DSCOP", "CUSTOMER_REFNO", " "+ refNo + " ", " WI_NAME = N'" + wiName + "'", sessionId);
			}

			if(callStatus.equals("Y")||callStatus.equals("X")){
				String outputXML;
				outputXML = APCallCreateXML.APUpdate("EXT_DSCOP", "CUSTOMER_ID,CUSTOMER_IC,CID_CREATION_DATE", "'"+ customerId + "','"+customerIC+"','"+new SimpleDateFormat("dd/MMM/yyyy").format(new Date())+"'", " WI_NAME = N'" + wiName + "'", sessionId);
				XMLParser xp = new XMLParser(outputXML);
				int	mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				if(mainCode == 0){
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"SuppCreateCustomer RETRY: "+ mainCode);
				}

				valList = "'"+ wiName +"','"+ deviceID +"','"+ customerId +"',SYSDATE";
				 APCallCreateXML.APInsert("BPM_DEVIE_CID_MAPPING", "WI_NAME, DEVICE_ID, CUSTOMER_ID, CID_CREATION_DATE", valList, sessionId);

				String valList2 = "'"+ wiName +"',"+ stageId +", 'SuppCreateCustomer', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXML+"', 0";
			  APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON,"
						+ " RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);
				executeDependencyCall();
			}
			else if(!callStatus.equals("R")){
				String valList2 = "'"+ wiName +"',"+ stageId +", 'SuppCreateCustomer', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXML+"', 1";
				 APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON, "
						+" RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "CRCUST100", e, sessionId);
		}
	}

	public String fetchCreateCustomerData() throws Exception {
		return APCallCreateXML.APSelect("select  CUSTOMER_ID, NAMESPLIT_FLG, EIDA_DEDUPE, PASSPORT_LOCAL_DEDUPE, EIDA_LOCAL_DEDUPE, TRSD_FLAG, "
				+ "SUPP_CARDHOLDER_FULL_NAME, ACCOUNT_TITLE, PREFIX, GENDER, CORRES_ADD_STATE_EMIRATE, "
				+ "CORRES_ADD_CITY, NATIONALITY, PASSPORT_NO, CORRES_ADD_POBOX_VILA_FLATNO, CORRES_ADD_FLOOR_BUILDINGNAME, CORRES_ADD_STREET_NAME, "
				+ "RESI_ADD_COUNTRY, DOB, CUSTOMER_EMAIL, CUSTOMER_MOBILE_NO, CORRES_ADD_COUNTRY, "
				+ "EIDA_NUMBER, PERM_ADD_CITY, PERM_ADD_COUNTRY,ASSOCIATE_CARD,"
				+ "PERM_ADD_STATE_EMIRATE, EIDA_NATIONALITY,"
				+ "EMPLYR_ADD_COUNTRY,IS_NTB_CUST,HOME_BRANCH from ext_dscop WHERE WI_NAME = N'" + wiName + "'");
	}

	private void handleCreateCustomerData(String outputXML1) throws Exception {
		String nameSplit;
		String eidaDedupe;
		XMLParser xp = new XMLParser(outputXML1);
		int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "CRCUST001", "CRCUST Started", sessionId);
		if (mainCode == 0 && Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0) {
			customerId = xp.getValueOf("CUSTOMER_ID");
			nameSplit = xp.getValueOf("NAMESPLIT_FLG");       
			eidaDedupe = xp.getValueOf("EIDA_DEDUPE");
			ppLocalDedupe = xp.getValueOf("PASSPORT_LOCAL_DEDUPE");
			eidaLocalDedupe = xp.getValueOf("EIDA_LOCAL_DEDUPE");
			trsdFlag = xp.getValueOf("TRSD_FLAG");
			custFullName = xp.getValueOf("SUPP_CARDHOLDER_FULL_NAME").toUpperCase();
			custShortName = xp.getValueOf("ACCOUNT_TITLE").equals("")? custFullName: xp.getValueOf("ACCOUNT_TITLE");//ACCOUNT_TITLE REMOVED
			custPrefix = xp.getValueOf("PREFIX");
			custGender = xp.getValueOf("GENDER");
			custEmirate = xp.getValueOf("CORRES_ADD_STATE_EMIRATE");
			custState = xp.getValueOf("CORRES_ADD_CITY");
			custNationality = xp.getValueOf("NATIONALITY");
			custNationalId = xp.getValueOf("PASSPORT_NO");
			txtCustAddr1 = xp.getValueOf("CORRES_ADD_POBOX_VILA_FLATNO");
			txtCustAddr2 = xp.getValueOf("CORRES_ADD_FLOOR_BUILDINGNAME");
			txtCustAddr3 = xp.getValueOf("CORRES_ADD_STREET_NAME");
			custResidence = xp.getValueOf("RESI_ADD_COUNTRY");
			txtCustDOB = xp.getValueOf("DOB");
			custEmail = xp.getValueOf("CUSTOMER_EMAIL");
			txtCustPhone = xp.getValueOf("CORRES_ADD_CONTACT_NUMBER");
			custMobile = xp.getValueOf("CUSTOMER_MOBILE_NO");
			custCountry = xp.getValueOf("CORRES_ADD_COUNTRY");
			icType = "P";
			homeBranch=xp.getValueOf("HOME_BRANCH");
			flgCustType = "Q"; // HARDCODED VALUE AS PER THILIBAN
			citizenshipId = xp.getValueOf("EIDA_NUMBER");
			permAddrState = xp.getValueOf("PERM_ADD_COUNTRY");
			eidaNationality = xp.getValueOf("EIDA_NATIONALITY");
			isNTBCust = xp.getValueOf("IS_NTB_CUST");
			associateCard = xp.getValueOf("ASSOCIATE_CARD");

			oldRefNo="";
			senderID = defaultMap.get("SENDER_ID");
			makerId = defaultMap.get("MAKER_ID");
			checkerId = defaultMap.get("CHECKER_ID");
			dualNationality = "N";
			
			String outputXML2 = APCallCreateXML.APSelect("SELECT COUNTRY_CODE FROM USR_0_COUNTRY_MAST WHERE COUNTRY = UPPER('"+custCountry+"')");
			XMLParser xp2 = new XMLParser(outputXML2);
			int mainCode2 = Integer.parseInt(xp2.getValueOf("MainCode"));
			if(mainCode2 == 0){
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"SuppCreateCustomer COUNTRY_DESC --> COUNTRY_CODE TotalRetrieved: "+Integer.parseInt(xp2.getValueOf("TotalRetrieved")));
				if(Integer.parseInt(xp2.getValueOf("TotalRetrieved")) > 0){
					custCountry = xp2.getValueOf("COUNTRY_CODE");
				}
			}

			if(nameSplit.equalsIgnoreCase("R")||"Y".equalsIgnoreCase(isPassportExpired)||eidaDedupe.equalsIgnoreCase("D")){
				callStatus="X";
		
			}
		}
	}   public int calculateAge(String dob) {
		Calendar dobDate = Calendar.getInstance();
		Calendar today = Calendar.getInstance();
		int curYear = today.get(Calendar.YEAR);
		int curMonth = today.get(Calendar.MONTH);
		int curDay = today.get(Calendar.DAY_OF_MONTH);

		SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
		String d1 = dob;
		try {
			Date formatted1 = f.parse(d1);
			dobDate.setTime(formatted1);
		} catch (ParseException e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, e);
		}
		int year = dobDate.get(Calendar.YEAR);
		int month = dobDate.get(Calendar.MONTH);
		int day = dobDate.get(Calendar.DAY_OF_MONTH);

		int age = curYear - year;
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"SuppCreateCustomer age "+age);
		if (curMonth < month || (month == curMonth && curDay < day)) {
			age--;
		}
		return age;
	}
	
	private void handleException(Exception e) {
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName,auditCallName , "CRCUST100", e, sessionId);
	}
}
