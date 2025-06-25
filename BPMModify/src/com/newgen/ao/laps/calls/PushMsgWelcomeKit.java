
package com.newgen.ao.laps.calls;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.core.ICallExecutor;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.ExecuteXML;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.omni.jts.cmgr.XMLParser;

public class PushMsgWelcomeKit implements ICallExecutor, Callable<String> {
	private CallEntity callEntity;
	private String sessionId;
	private String winame;
	private String sUserName;
	private String engineName;
	private String stageId;
	Map<String, String> attributeMap = new HashMap<String, String>();
	private String callStatus;
	private String correlationNo = "";
	private int returnCode;
	private String errorDescription;	
	private String status;
	private String responseXML;
	
	
	public PushMsgWelcomeKit(Map<String, String> attributeMap, String sessionId, String stageId, String wiNumber, 
			Boolean prevStageNoGo, CallEntity callEntity) {
		this.callEntity = callEntity;
		this.attributeMap = attributeMap;
		this.sessionId = sessionId;
		this.engineName=LapsConfigurations.getInstance().CabinetName;
		this.stageId=stageId;
		this.winame=wiNumber;
		this.correlationNo=attributeMap.get("correlationNo");
		sUserName = LapsConfigurations.getInstance().UserName;
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "PushMsgWelcomeKit cunstructor called map===> " +callEntity.toString()+" Session id ===>"+sessionId);
	}

	@Override
	public String call() throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"PushMsgWelcomeKit call: inside");
		String inputXml = "";
		callStatus="Y";
		try {			
			inputXml = createInputXML();
			responseXML = ExecuteXML.executeXML(inputXml);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "PushMsgWelcomeKit outputXml ===> " + responseXML);
			if(responseXML==null || responseXML.equalsIgnoreCase("")){				
				callStatus = "Y";
				status="Success";
				returnCode=1;
				errorDescription = "Web Service Error";
			}
			//responseHandler(responseXML, inputXml);
			responseXML=responseXML+"<CallStatus>"+callStatus+"</CallStatus><CallResponse>"+errorDescription+""
					+ "</CallResponse><returnCode>"+returnCode+"</returnCode><errorDescription>"+errorDescription+""
					+ "</errorDescription><errorDetail></errorDetail><Reason></Reason><Status>"+status+"</Status>";
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "PushMsgWelcomeKit outputXml ===> " + responseXML);
			
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			throw new Exception(e.getMessage());
		}
	   
		return responseXML;
	}

	@Override
	public String createInputXML() throws Exception {
		StringBuffer input = new StringBuffer();
		try{
			
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside create xml");
			String outputXml="";
//			String query = APCallCreateXML.APSelect("select processdefid from wfinstrumenttable where processinstanceid='"+this.winame+"'");
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Query :"+query);
//			XMLParser xp1 = new XMLParser(query);
//			int processdefID = Integer.parseInt(xp1.getValueOf("processdefid"));
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "processdefID :"+processdefID);
			
			if(this.winame.startsWith("DJ-")){
			 outputXml = APCallCreateXML.APSelect("SELECT EXT.WI_NAME AS WI_NAME,EXT.CUSTOMER_FULL_NAME AS CUST_FULL_NAME,EXT.CUSTOMER_ID AS CUSTOMER_ID, EXT.ACCOUNT_NUMBER AS ACCOUNT_NUMBER,PROD.PRODUCT_CODE AS PRODUCT_CODE,eli.description AS PRODUCT_DESCRIPTION,"
					+ " DECODE(EXT.DEBIT_CARD_NUMBER,NULL,'N','Y') AS DEBIT_CARD,EXT.CUSTOMER_SEGMENT AS CUSTOMER_TYPE ,PROD.CHEQUEBOOK AS CHEQUE_BOOK FROM EXT_CBG_CUST_ONBOARDING EXT"
					+ " INNER JOIN BPM_COP_PRODUCT_DETAILS PROD ON PROD.WI_NAME=EXT.WI_NAME AND PROD.PRODUCT_TYPE='CASA' AND PROD.OPER_CODE IN ('A','U') "
					+ " INNER JOIN bpm_cop_product_eligibility ELI ON ELI.WI_NAME=EXT.WI_NAME AND PROD.PRODUCT_CODE=ELI.PRODUCT_CODE"
					+ " WHERE  EXT.WI_NAME='"+this.winame+"' and rownum=1");
			 LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "outputXml :"+outputXml);
			
			}else if(this.winame.startsWith("AO-")){
			  outputXml = APCallCreateXML.APSelect("SELECT AO.WI_NAME AS WI_NAME, PRD.ACC_NO AS ACCOUNT_NUMBER,AO.CUST_ID AS CUSTOMER_ID,acc.name AS CUST_FULL_NAME, "
					+ " PRD.PROD_CODE AS PRODUCT_CODE,PRD.PROD_DESC AS PRODUCT_DESCRIPTION,TXN.CUST_SEG AS CUSTOMER_TYPE, "
					+ " DECODE(PRD.CHEQUE_BOOK,'Yes','Y','No','N') AS CHEQUE_BOOK,(select decode (count(1),0,'N','Y') as Count  from debit_card_rep where"
					+ " wi_name ='"+this.winame+"' and REP_NEW_LINK='New') AS DEBIT_CARD "
					+ " FROM EXT_AO AO "
					+ " INNER JOIN ACC_RELATION_REPEATER ACC ON AO.WI_NAME=ACC.WI_NAME and ACC_RELATION <> 'Minor' "
					+ " INNER JOIN USR_0_PRODUCT_SELECTED PRD ON AO.WI_NAME=PRD.WI_NAME AND PRD.CID=1"
					+ " INNER JOIN USR_0_CUST_TXN TXN ON AO.WI_NAME = TXN.WI_NAME "
					+ " WHERE AO.WI_NAME='"+this.winame+"' AND ROWNUM =1");
			  LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "outputXml :"+outputXml);
			  			}
			
			XMLParser xp = new XMLParser(outputXml);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if (mainCode == 0) {
				String accNo = xp.getValueOf("ACCOUNT_NUMBER");
				String custID = xp.getValueOf("CUSTOMER_ID");
				String accHolderName = xp.getValueOf("CUST_FULL_NAME");
				String chequeBook = xp.getValueOf("CHEQUE_BOOK");
				String debitCard = xp.getValueOf("DEBIT_CARD");
				String prodCode = xp.getValueOf("PRODUCT_CODE");
				String prodDesc = xp.getValueOf("PRODUCT_DESCRIPTION"); 
				String custType = xp.getValueOf("CUSTOMER_TYPE");
				String wiName = xp.getValueOf("WI_NAME");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "accNo--> " +accNo);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "custID--> " +custID);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "accHolderName--> " +accHolderName);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "chequeBook--> " +chequeBook);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "debitCard--> " +debitCard);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "prodCode--> " +prodCode);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "prodDesc--> " +prodDesc);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "custType--> " +custType);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "wiName--> " +wiName);
				
				
				String Query = APCallCreateXML.APSelect("select unique_id from usr_0_cust_segment where cust_segment='"+custType+"'");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Query--> " +Query);
				XMLParser xp1 = new XMLParser(Query);
				custType = xp1.getValueOf("unique_id");
				
				input.append("<?xml version=\"1.0\"?>")
				.append("<APWebService_Input>")
				.append("<Option>WebService</Option>")
				.append("<Calltype>WS-2.0</Calltype>")
				.append("<InnerCallType>LapsResponse</InnerCallType>")
				.append("<SessionId>" + sessionId + "</SessionId>")
				.append("<EngineName>"+engineName+"</EngineName>")
//				.append("<ChannelName>ITQAN_M</ChannelName>")
				.append("<ChannelName>WELCOME_KIT</ChannelName>")
				.append("<WI_NAME>" + wiName + "</WI_NAME>")
				.append("<PushMessage>")
				.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
				.append("<WELCOMEKITDELIVERYDETAIL>")
				.append("<CustomerID>" + custID + "</CustomerID>");
				
				if(!accNo.isEmpty()){
				input.append("<AccountNo>"+ accNo +"</AccountNo>");
				}
				
				input.append("<AccountHolderName>"+ accHolderName +"</AccountHolderName>")
				.append("<ProductCode>"+ prodCode +"</ProductCode>")
				.append("<ProdDescription>"+ prodDesc +"</ProdDescription>")
				.append("<IssuedBy>WMS</IssuedBy>")
				.append("<DebitCardInclusion>"+  debitCard +"</DebitCardInclusion>")
				.append("<ChequebookInclusion>"+ chequeBook +"</ChequebookInclusion>")
				.append("<Status>0</Status>")
				.append("<CustType>"+ custType +"</CustType>")
				.append("</WELCOMEKITDELIVERYDETAIL>")
				.append("</PushMessage>")
				.append("</APWebService_Input>");
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "input--> " +input);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		return input.toString();
	}
	

	@Override
	public String executeCall(HashMap<String, String> input) throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Execute call called in PushMsgWelcomeKit");
		return call();
	}

	@Override
	public void executeDependencyCall() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void responseHandler(String outputXML, String inputXml)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		// TODO Auto-generated method stub
		
	}

	

}

