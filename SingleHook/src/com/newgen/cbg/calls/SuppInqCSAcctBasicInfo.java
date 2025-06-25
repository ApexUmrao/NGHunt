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

public class SuppInqCSAcctBasicInfo implements ICallExecutor, Callable<String>{

	private String sessionId;
	private String wiName;
	private String callStatus;
	private int stageID;
	private int returnCode;
	private String errorDetail;
	private String errorDescription;	
	private String status;
	private String reason;
	private String primaryCid ;
	private String refNo;
	private String senderID;
	private boolean prevStageNoGo;
	private Map<String, String> defaultMap;
	private CallEntity callEntity;
	private String auditCallName = "SCABI";
	private boolean isExecuteCall=true;
	String outputXml;
	public SuppInqCSAcctBasicInfo(Map<String, String> defaultMap, String sessionId, String stageId, String wiName, Boolean prevStageNoGo, CallEntity callEntity){
		this.wiName = wiName;
		stageID = Integer.parseInt(stageId);
		this.sessionId = sessionId;
		this.prevStageNoGo = prevStageNoGo;
		this.defaultMap = defaultMap;
		this.callEntity = callEntity;
		primaryCid=defaultMap.get("primaryCid");
		try {
			
			handleInqAcc();
			
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SCABI100", e, sessionId);
		}
	}

	@Override
	public String call() throws Exception {
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"SUPPInqCSAcctBasicInfo call: inside");
		String inputXml = "";
		outputXml = "<returnCode>0</returnCode>";
		if(!"".equals(primaryCid)){
			try {			
				inputXml = createInputXML();
				if(!prevStageNoGo){
					if(isExecuteCall){
						outputXml = ExecuteXML.executeWebServiceSocket(inputXml);
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SUPPInqCSAcctBasicInfo outputXml ===> " + outputXml);
						if(outputXml==null || outputXml.equalsIgnoreCase("")){
							outputXml= "<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>";
						}
						responseHandler(outputXml, inputXml);
					}
				} else {
					callStatus = "N";
					updateCallOutput(inputXml);
				}
			} catch (Exception e) {
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SCABI100", e, sessionId);
			}
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
			.append("<DSCOPCallType>InqCSAcctBasicInfo</DSCOPCallType>").append("\n")			
			.append("<WiName>" + wiName + "</WiName>").append("\n")
			.append("<REF_NO>" + refNo + "</REF_NO>").append("\n")
			.append("<OLDREF_NO>" + refNo + "</OLDREF_NO>").append("\n")
			.append("<senderId>" + senderID + "</senderId>").append("\n")
			.append("<InqCustomerDetailsReq><cust_id>"+ primaryCid +"</cust_id></InqCustomerDetailsReq>").append("\n")
			.append("</APWebService_Input>");
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, inputXml.toString());
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SCABI100", e, sessionId);
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
			String outputXMLTemp="";
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "SCABI004", "AS DependencyCall:"+ callEntity.getDependencyCallID(), sessionId);
			outputXMLTemp = CallHandler.getInstance().executeDependencyCall(callEntity, defaultMap, sessionId, wiName, prevStageNoGo);
			if(!outputXMLTemp.isEmpty()) {
				outputXml=outputXMLTemp;
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SCABI100", e, sessionId);
		}
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
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "SCABI090", "SUPPInqCSAcctBasicInfo Successfully Executed", sessionId);
				int start = xp.getStartIndex("acct_Basic_Details", 0, 0);
				int deadEnd = xp.getEndIndex("acct_Basic_Details", start, 0);
				int noOfFields = xp.getNoOfFields("accounts", start,deadEnd);
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"SUPPInqCSAcctBasicInfo accounts noOfFields: "+noOfFields);
				int end = 0;
				if(noOfFields>0){

					APCallCreateXML.APDelete("BPM_COP_PRODUCT_ELIGIBILITY", " WI_NAME = N'"+ wiName +"' AND PRODUCT_TYPE = 'CASA' AND ISEXISTING ='Y'", sessionId);

					for(int i = 0; i < noOfFields; ++i){
						start = xp.getStartIndex("accounts", end, 0);
						end = xp.getEndIndex("accounts", start, 0);
						String accountStatus = xp.getValueOf("acctStatusDescription", start, end);
						String acctStatusCode = xp.getValueOf("acctStatusCode", start, end);
						String productCode = xp.getValueOf("productCode", start, end);
						String productName = xp.getValueOf("productName", start, end);
						String productType = xp.getValueOf("productType", start, end).equalsIgnoreCase("Conventional")?"C":"I";
						String accountNo = xp.getValueOf("accountNo", start, end);
						String acctCurrency = xp.getValueOf("acctCurrencyCode", start, end);
						String acctBalance = xp.getValueOf("netAvalWithdrawl", start, end);
						String acctMemo = xp.getValueOf("acctMemo", start, end);
						String memoDesc = xp.getValueOf("memoDesc", start, end);
						String memoFlagSev = xp.getValueOf("memoFlagSev", start, end);
						String branchCode = xp.getValueOf("acctBranchCode", start, end);

						String chequeBookEligble = "N";
						if(productCode.equals("132") || productCode.equals("217") || productCode.equals("148")){// Remove once come from BRMS
							chequeBookEligble="Y";
						}

						if(!"1".equals(acctStatusCode)){ //1=ACCOUNT CLOSED
							String isExisting="N";
							int productCodeCount = Integer.parseInt(new XMLParser(APCallCreateXML.APSelect("SELECT COUNT(1) "
									+ "AS COUNT FROM BPM_COP_PRODUCT_MASTER WHERE PROD_CODE='"+productCode+"'")).getValueOf("COUNT"));
							if(productCodeCount>0){
								isExisting="Y";
							}
							DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"SUPPInqCSAcctBasicInfo isExisting: "+isExisting);
							String debitCardEligiblity = new XMLParser(APCallCreateXML.APSelect("SELECT ATM_FLAG FROM USR_0_PRODUCT_MASTER WHERE PRODUCT_CODE='"+productCode+"'")).getValueOf("ATM_FLAG");					
							String valList = "'"+ wiName +"','CASA', '"+productCode+"', 'N', '"+productName+"', '', '"+isExisting+"', '"+productType+"', 'Y', 'N', 'N', '', '', '',"
									+ "'', '', '', '', '','"+chequeBookEligble+"','"+accountNo+"','"+acctCurrency+"','"+acctBalance+"','"+acctMemo+"','"+memoDesc+"',"
									+ "'"+memoFlagSev+"','"+branchCode+"',SYSDATE,'"+debitCardEligiblity+"'";
							String columnList = "WI_NAME,PRODUCT_TYPE,PRODUCT_CODE,CHEQUEBOOK,DESCRIPTION,TYPE,ISEXISTING,BANKING_TYPE,ELIGIBILITY,ADDEDTOCART,UPGRADE,AMOUNT,TENOR,ROI,BIN,"
									+ "OUTSTANDINGBALANCE,REMAININGCCLIMIT,MINAMOUNT,MAXAMOUNT,CHEQUEBOOKELIGIBILITY,ACCOUNT_NUMBER,ACCOUNT_CURRENCY,ACCOUNT_BALANCE,ACCOUNT_MEMO,MEMO_DESC,MEMO_SEVERITY,BRANCH_CODE,INSERTIONDATETIME,DEBITCARDELIGIBILITY";
							APCallCreateXML.APInsert("BPM_DSCOP_PRODUCT_ELIGIBILITY", columnList, valList, sessionId);

							//COLMP 8801 Subtask 8982
							String columnList1 = "WI_NAME,ACCOUNT_NUMBER,ACCOUNT_MEMO,MEMO_DESC,MEMO_SEVERITY,ACCOUNT_STATUS,ACCOUNT_STATUS_CODE,"
									+"PRODUCT_TYPE,PRODUCT_CODE,DESCRIPTION,INSERTIONDATETIME,DEBITCARDELIGIBILITY";
							String valList1 = "'"+ wiName +"','"+accountNo+"','"+acctMemo+"','"+memoDesc+"','"+memoFlagSev+"','"+accountStatus+"','"+acctStatusCode+"',"
									+ "'"+productType+"','"+productCode+"','"+productName+"',SYSDATE,'"+debitCardEligiblity+"'";

							APCallCreateXML.APInsert("BPM_DSCOP_ACCOUNT_DETAILS", columnList1, valList1, sessionId);

						}	
					}	
				}
			} else {
				callStatus = "F";
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "SCABI090", "SUPPInqCSAcctBasicInfo Fail", sessionId);
			}		 
			updateCallOutput(inputXml);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SCABI100", e, sessionId);
		}		
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		try {
			String valList = "'"+ wiName +"',"+ stageID +", 'SUPPInqCSAcctBasicInfo', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"'";
			APCallCreateXML.APInsert("USR_0_DSCOP_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
					+ "ERROR_DETAIL, STATUS, REASON", valList, sessionId);

			if(callStatus.equals("Y")){
				String valList2 = "'"+ wiName +"',"+ stageID +", 'SUPPInqCSAcctBasicInfo', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml +"', 0";
				APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON,"
						+ " RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);
				executeDependencyCall();
			}
			else {
				String valList2 = "'"+ wiName +"',"+ stageID +", 'SUPPInqCSAcctBasicInfo', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml +"', 1";
				APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON, "
						+" RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SCABI100", e, sessionId);
		}
	}
	
	public void handleInqAcc()  {
		if(primaryCid==null || primaryCid.equals("")){
			isExecuteCall = false;
		}
		senderID = defaultMap.get("SENDER_ID");
		}
}
