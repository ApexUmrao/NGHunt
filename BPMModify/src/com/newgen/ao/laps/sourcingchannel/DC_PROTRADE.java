package com.newgen.ao.laps.sourcingchannel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.cache.ChannelCallCache;
import com.newgen.ao.laps.cache.ProcessEventCache;
import com.newgen.ao.laps.callhandler.CallHandler;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.request.LapsModifyRequest;
import com.newgen.ao.laps.response.LapsModifyResponse;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;

public class DC_PROTRADE implements SourcingChannelHandler{
	public LapsModifyResponse resp = new LapsModifyResponse();
	private HashMap<String, String> attributeMap = new HashMap<String, String>();
	public LapsModifyRequest request;
	private int processDefId;
	XMLParser xp=new XMLParser();
	int stageId = 1;
	int mainCode = -1;
	String status = "0";
	String statusDesc = "Calls Executed Successfully";
	String requestCategory ="";
	String requestType="";
	String wiNumber;

	@Override
	public LapsModifyResponse dispatchEvent(String sessionId, String channelRefNumber, String correlationNo, 
			String sourcingChannel, String mode, String wiNumber, String processName) {
		try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside DC_PROTRADE >>");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"DC_PROTRADE mode: "+mode
					+", sourcingChannel: "+sourcingChannel);
			processDefId = LapsConfigurations.getInstance().processDefIdTFO;
//			SingleUserConnection instance = SingleUserConnection.getInstance(1000);
//			try {
//				sessionId = instance.getSession(
//						LapsConfigurations.getInstance().CabinetName,
//						LapsConfigurations.getInstance().UserName,
//						LapsConfigurations.getInstance().Password);
//			} catch (Exception e) {
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in fetching session ID: ");
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
//			}
			String journeyType = sourcingChannel;
			this.wiNumber=wiNumber;
			String processType;
			String customerId;  // ATP-379 DATE 08-02-2024 REYAZ
			Map<String, String> fetchMap = ChannelCallCache.getInstance().getFetchMap();
			String outputXml = APCallCreateXML.APSelect("select PROCESS_TYPE, REQUEST_CATEGORY, REQUEST_TYPE,CUSTOMER_ID from EXT_TFO where "
					+ "WI_NAME='"+wiNumber+"'");
			XMLParser xp = new XMLParser(outputXml);
			mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if(mainCode == 0 && Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0) {
				requestCategory = xp.getValueOf("REQUEST_CATEGORY");
				requestType = xp.getValueOf("REQUEST_TYPE");
				processType = xp.getValueOf("PROCESS_TYPE");
				customerId  = xp.getValueOf("CUSTOMER_ID");  // ATP-379 DATE 08-02-2024 REYAZ
				mainCode = -1;
				String key = processType + "#" + requestCategory + "#" + requestType;
				if(fetchMap.get(key) != null && "CUSTOMER_ID".equalsIgnoreCase(fetchMap.get(key))) {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"journey type is issuance.");
					journeyType = "ISSUANCE";
				}
				/*outputXml = APCallCreateXML.APSelect("select FIELD_NAME from TFO_DJ_RECEPTION_FETCH_MAST where "
						+ "REQ_CAT_CODE='"+requestCategory+"' and REQ_TYPE_CODE='"+requestType+"' and PROCESS_TYPE='BAU'");
				xp = new XMLParser(outputXml);
				mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				if(mainCode == 0 && Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0) {
					int start = xp.getStartIndex("Records", 0, 0);
					int deadEnd = xp.getEndIndex("Records", start, 0);
					int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
					int end = 0;
					for (int i = 0; i < noOfFields; ++i) {
						start = xp.getStartIndex("Record", end, 0);
						end = xp.getEndIndex("Record", start, 0);
						String fetchFieldName = xp.getValueOf("FIELD_NAME", start, end);
						if(fetchFieldName != null && "CID_Txt".equalsIgnoreCase(fetchFieldName)) {
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"journey type is issuance >>");
							journeyType = "ISSUANCE";
							break;
			}
					}
				}*/
			} else {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Invalid Workitem Number");
				resp.setStatusCode("-1");
				resp.setStatusDescription("Invalid Workitem Number");
				return resp;
			}
			attributeMap.put("mode", mode);
			attributeMap.put("ruleFlag", "Y");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"DC_PROTRADE attributeMap: "+attributeMap.toString());
			HashMap<Integer, Integer> eventMap = LapsUtils.getInstance().getEventDetector
					(processDefId, sourcingChannel, "1.0", journeyType,stageId);
			int eventID = (Integer) eventMap.keySet().toArray()[0];
			List<CallEntity> callArray = ProcessEventCache.getReference().getEventCallList(eventID);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "eventID: " + eventID);
			if(callArray != null) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Call_Array: " + wiNumber + ":" + callArray.toString());
				boolean nogoCall = false;
				for (CallEntity callEntity : callArray) {
					if(callEntity.isMandatory()){
						String outputXML = CallHandler.getInstance().executeCall
								(callEntity, attributeMap, sessionId, String.valueOf(eventID), 
										wiNumber, false);
						xp = new XMLParser(outputXML);
						if("0".equalsIgnoreCase(status)){
							status = xp.getValueOf("returnCode");
						}
						if(!"0".equals(status)){
							statusDesc =  "Calls Not Executed Successfully";
						}
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Execute Call: outputxml: "
								+ outputXML);
//						if(!"".equals(outputXML)){
//							xp = new XMLParser(outputXML);
//							mainCode = Integer.parseInt(xp.getValueOf("returnCode"));
//							if(mainCode != 0){
//								nogoCall = true;
//							}
//						}
					}
				}
			}
			//ATP-383 20-01-2024 Jamshed start
			updateLimitLineDetails(sessionId);
			//ATP-383 20-01-2024 Jamshed Ends
			
			//ATP-496 REYAZ 29-07-2024 START
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"DC_PROTRADE: status= "+status+"statusDesc= "
					+statusDesc);
			resp.setWiNumber(wiNumber);
			resp.setStatusCode(status);
			resp.setStatusDescription(statusDesc);
			if("0".equals(status)) {
			if(journeyType.equalsIgnoreCase("ISSUANCE")){
				APCallCreateXML.APUpdate("EXT_TFO", "TARGET_WORKSTEP, CURR_WS", "'PP_MAKER','Initiator'", 
						"WI_NAME = '"+wiNumber+"'", sessionId);
				ProdCreateXML.WMCompleteWorkItem(sessionId, wiNumber, 1);
				autoSubmitPPM(customerId,wiNumber,requestCategory,sessionId); // ATP-379 DATE 08-02-2024 REYAZ
				}
						}
			//ATP-496 REYAZ 29-07-2024 END
		} catch(Exception e){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in DC_PROTRADE dispatchEvent: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"returning response from DC_PROTRADE");
		return resp;
	}
	
	// ATP-379 DATE 08-02-2024 REYAZ
	// START CODE
	public void autoSubmitPPM(String custID,String wiNumber,String requestCategory,String sessionId) {		
		try{
				String autoTrigger =getFlagFromMaster("AutoEmail");
				String groupMailId =getFlagFromMaster("EmailID");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"autoTrigger :"+ autoTrigger);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"groupMailId :"+ groupMailId);
					
				String TRADE_CUST_EMAIL_ID =fetchTradeEmailDetailfrmETL(custID,requestCategory);
				String autoReferralFlag=getFlagFromMaster("AutoReferal");
				String opXml =APCallCreateXML.APSelect("select 'Limit/Credit Review' AS MODULE,'RM' AS REFFERED_TO,refdesc from TFO_DJ_TSLM_LIMIT_CREDIT_REVIEW where wi_name ='"+wiNumber+"' and REFCODE='RM' AND STATUS='Active'"
							+" union "
							+"select 'Signature and Acc Bal Check' AS MODULE,'RM' AS REFFERED_TO,refdesc from TFO_DJ_TSLM_REFERRAL_DETAIL where wi_name ='"+wiNumber+"' and REFCODE='RM' AND STATUS='Active'"
							+" union "
							+"select 'Document Review' AS MODULE,'RM' AS REFFERED_TO,refdesc from TFO_DJ_TSLM_DOCUMENT_REVIEW where wi_name ='"+wiNumber+"' and REFCODE='RM' AND STATUS='Active'");
				XMLParser parser=new XMLParser(opXml);
				int mainCode = Integer.parseInt(parser.getValueOf("MainCode"));
			if ((autoReferralFlag.trim().equalsIgnoreCase("Y")||autoReferralFlag.trim().equalsIgnoreCase("Yes"))&&mainCode == 0 
					&& Integer.parseInt(parser.getValueOf("TotalRetrieved"))!= 0) {
					int count=parser.getNoOfFields("Record");
				
				for(int i=0;i<count;i++){
					String Record = parser.getNextValueOf("Record");
					XMLParser xp2 = new XMLParser(Record);
					
					 //CODE TO INSERT DATA IN FINAL DEC SUMMARY TABLE
	 				String insertionorderID="";
	 				String tablName="tfo_dj_final_dec_summary";						   
	 	            String outputXml1 = APCallCreateXML.APSelect("select S_"+tablName+".nextval from dual");
	 					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"data output XML:"+ outputXml1);
	 					XMLParser xp1 = new XMLParser(outputXml1);
	 					int mainCode2 = Integer.parseInt(xp1.getValueOf("MainCode").trim().isEmpty() ? "1" : xp1.getValueOf("MainCode").trim());
	 					if (mainCode2 == 0) {
	 						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"insertionOrderID TotalRetrieved: "+ Integer.parseInt(xp1.getValueOf("TotalRetrieved")));
	 						if (Integer.parseInt(xp1.getValueOf("TotalRetrieved")) > 0) {
	 							insertionorderID = xp1.getValueOf("NEXTVAL");
	 						}
	 					}
	 					StringBuffer strColumns2= new StringBuffer();
	 					StringBuffer strValues2 = new StringBuffer();	
	 				   String	MODULE    =xp2.getValueOf("MODULE");          
	 				   String	REFFERDTO   ="RM";           
	 				   String	DECISION     ="Yes";           
	 				   String	EXCPREMARKS  =xp2.getValueOf("refdesc");         
	 				   String	EXISTINGMAIL  =TRADE_CUST_EMAIL_ID; 
	 				   String  NEW_MAIL="";
	 				   
	 				   if(autoTrigger.trim().equalsIgnoreCase("Y")||autoTrigger.trim().equalsIgnoreCase("Yes"))
	 					  NEW_MAIL=groupMailId;
	 				   
	 				   strColumns2.append("TAB_MODULE,REFFERD_TO,DECISION,EXCP_REMARKS,EXISTING_MAIL,NEW_MAIL,WI_NAME,INSERTIONORDERID");
	 				   strValues2.append("'"+MODULE+"','"+REFFERDTO+"','"+DECISION+"','"+EXCPREMARKS+"','"+EXISTINGMAIL+"','"+NEW_MAIL+"','"+wiNumber+"',"+insertionorderID);
	 				   String output = APCallCreateXML.APInsert("tfo_dj_final_dec_summary",strColumns2.toString(), strValues2.toString(), sessionId);
	 				   LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Insert output: " + output);	
	 					 
					
				}
				
				//CODE TO MOVE WORKITREM TO RM
					APCallCreateXML.APUpdate("EXT_TFO", "DEC_CODE", "'REF'", "WI_NAME = '" + wiNumber + "'", sessionId);
					
					APCallCreateXML.APUpdate("EXT_TFO", "IS_RM_PPM", "'Y'", "WI_NAME = '" + wiNumber + "'",sessionId);
					
					APCallCreateXML.APUpdate("EXT_TFO", "IS_CR_PPM", "'N'", "WI_NAME = '" + wiNumber + "'", sessionId);
					
					APCallCreateXML.APUpdate("EXT_TFO", "IS_LEGAL_PPM", "'N'", "WI_NAME = '" + wiNumber + "'", sessionId);
					
					APCallCreateXML.APUpdate("EXT_TFO", "IS_REF_PPM", "'N'", "WI_NAME = '" + wiNumber + "'", sessionId);
					
					APCallCreateXML.APUpdate("EXT_TFO", "IS_CB_PPM", "'N'", "WI_NAME = '" + wiNumber + "'", sessionId);
					
					APCallCreateXML.APUpdate("EXT_TFO", "IS_TRADE_PPM", "'N'", "WI_NAME = '" + wiNumber + "'", sessionId);
					
					ProdCreateXML.WMCompleteWorkItem(sessionId, wiNumber, 1);
			}
		}catch(Exception e){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
	 }
	
	private String getFlagFromMaster(String MDMFlag){
		String utcFlag = "";
		String trsdFlag = "";
		String referralFlag = "";	
		String emailFlag ="";
		String emailID ="";

		try{
			String outputXml = APCallCreateXML.APSelect("SELECT AUTO_UTC_TRIGGER,AUTO_TRSD_TRIGGER,AUTO_REFERRAL_FLAG,AUTO_TRIGGER_EMAIL,TFO_GROUP_MAILID FROM TFO_DJ_AUTO_TRIGGER_MASTER WHERE SOURCING_CHANNEL='PT'"); //ATP-379 15-FEB-24 --JAMSHED
			XMLParser parser = new XMLParser(outputXml);
			utcFlag = parser.getValueOf("AUTO_UTC_TRIGGER");
			trsdFlag = parser.getValueOf("AUTO_TRSD_TRIGGER");
			referralFlag = parser.getValueOf("AUTO_REFERRAL_FLAG");
			emailFlag = parser.getValueOf("AUTO_TRIGGER_EMAIL");
			emailID = parser.getValueOf("TFO_GROUP_MAILID");

			if("AutoUTC".equalsIgnoreCase(MDMFlag)){
				MDMFlag = utcFlag;
			}else if("AutoScreening".equalsIgnoreCase(MDMFlag)){
				MDMFlag = trsdFlag;
			}else if("AutoReferal".equalsIgnoreCase(MDMFlag)){
				MDMFlag = referralFlag;
			}else if("AutoEmail".equalsIgnoreCase(MDMFlag)){
				MDMFlag = emailFlag;
			}else if("EmailID".equalsIgnoreCase(MDMFlag)){
				MDMFlag = emailID;
			}
		}catch(Exception e){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, " Flag value --> " +MDMFlag);
		return MDMFlag;

	}
	
	private String fetchTradeEmailDetailfrmETL(String cid, String reqCat) {

		String tradeCustEmailID="";
		try {
			String tradeFinanceService = "";

			if("GRNT".equalsIgnoreCase(reqCat)){
				tradeFinanceService="Guarantee";
			}else if("ELC".equalsIgnoreCase(reqCat)){
				tradeFinanceService="Export LC";
			}
			else if("ILCB".equalsIgnoreCase(reqCat) ||  "IC".equalsIgnoreCase(reqCat) || "DBA".equalsIgnoreCase(reqCat)){
				tradeFinanceService="Incoming Bill";
			}
			else if("ELCB".equalsIgnoreCase(reqCat) ||  "EC".equalsIgnoreCase(reqCat)){
				tradeFinanceService="Outgoing Bill";
			}
			else if("SG".equalsIgnoreCase(reqCat)){
				tradeFinanceService="Shipping Guarantee";
			}
			else if("ILC".equalsIgnoreCase(reqCat)){
				tradeFinanceService="Import LC";
			}
			else if("IFA".equalsIgnoreCase(reqCat)|| "IFS".equalsIgnoreCase(reqCat) || "IFP".equalsIgnoreCase(reqCat)){
				tradeFinanceService="Loans";
			}
			String str = "SELECT email "
					+ "FROM TFO_DJ_TRADE_EMAIL_MAST "
					+ "WHERE CUST_ID ='"+ cid +"' and trade_finance_service='"+tradeFinanceService+"'  AND ROWNUM=1 union  all"+
					" select email  from tfo_dj_trade_email_mast where  cust_id='"+cid+"' "
					+ "and UPPER(trade_finance_service)=UPPER('ALL') AND ROWNUM=1";
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"fetchTradeEmailDeatilfrmETL   " + str);

			String sOutputXML = APCallCreateXML.APSelect(str);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "sOutputXML:" +sOutputXML);
			XMLParser Xp3 = new XMLParser(sOutputXML);
			tradeCustEmailID=Xp3.getValueOf("email");
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}

		return tradeCustEmailID;
	}
	// ATP-379 DATE 08-02-2024 REYAZ
	// END CODE

	//ATP-383 20-01-2024 Jamshed start
	public void updateLimitLineDetails(String sessionId) {		
		try{
			StringBuffer strColumns2= new StringBuffer();
			StringBuffer strValues2 = new StringBuffer();
			String output="";
			strColumns2.append("WI_NAME,LT_DOC_RVW_GDLINES,LT_RESPONSE,INSERTIONORDERID");
				
			//GENERATE INSERTION ORDER ID
			String insertionorderID="";
			String tablName="TFO_DJ_LMT_CRDT_RECORDS";
			
			String sQuery = APCallCreateXML.APSelect("SELECT IS_100PCT_CASHBACK,LL_CODE FROM EXT_TFO WHERE WI_NAME = '"+wiNumber+"'");

			XMLParser xpParser = new XMLParser(sQuery);
			String is_100pct_cshbk=xpParser.getValueOf("IS_100PCT_CASHBACK");
			String ll_code=xpParser.getValueOf("LL_CODE");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside RequestCategory:"+requestCategory );
			
			if(is_100pct_cshbk !=null && !is_100pct_cshbk.equalsIgnoreCase("") && is_100pct_cshbk.equalsIgnoreCase("True")) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "is_100pct_cshbk: TRUE CASE");
				if(requestCategory.equalsIgnoreCase("GRNT") && requestType.equalsIgnoreCase("NI")) {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "is_100pct_cshbk: GUARANTEE ISSUANCE CASE");
					insertionorderID=LapsUtils.returnInsertionOrderID(tablName);
					strValues2.append("'"+wiNumber+"','"+"Please select Collateral details."+"','CASH MARGIN',"+insertionorderID);
					output = APCallCreateXML.APInsert("TFO_DJ_LMT_CRDT_RECORDS",strColumns2.toString(), strValues2.toString(), sessionId);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Insert output: " + output);
					
					strValues2 = new StringBuffer();
					insertionorderID=LapsUtils.returnInsertionOrderID(tablName);
					strValues2.append("'"+wiNumber+"','"+"Please input Cash Margin percentage."+"','100',"+insertionorderID);
					output = APCallCreateXML.APInsert("TFO_DJ_LMT_CRDT_RECORDS",strColumns2.toString(), strValues2.toString(), sessionId);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Insert output: " + output);	
					
					strValues2 = new StringBuffer();
					insertionorderID=LapsUtils.returnInsertionOrderID(tablName);
					strValues2.append("'"+wiNumber+"','"+"Please specify Limit line."+"','"+ll_code+"',"+insertionorderID);
					output = APCallCreateXML.APInsert("TFO_DJ_LMT_CRDT_RECORDS",strColumns2.toString(), strValues2.toString(), sessionId);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Insert output: " + output);
				}
				else if((requestCategory.equalsIgnoreCase("SBLC") && requestType.equalsIgnoreCase("SBLC_NI"))
						|| (requestCategory.equalsIgnoreCase("ILC") && requestType.equalsIgnoreCase("ILC_NI"))) {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "is_100pct_cshbk: SBLC & ILC ISSUANCE CASE");
					insertionorderID=LapsUtils.returnInsertionOrderID(tablName);
					strValues2.append("'"+wiNumber+"','"+"Specify Collateral Details"+"','CASH MARGIN',"+insertionorderID);
					output = APCallCreateXML.APInsert("TFO_DJ_LMT_CRDT_RECORDS",strColumns2.toString(), strValues2.toString(), sessionId);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Insert output: " + output);
					
					strValues2 = new StringBuffer();
					insertionorderID=LapsUtils.returnInsertionOrderID(tablName);
					strValues2.append("'"+wiNumber+"','"+"Specify Cash Margin Percentage"+"','100',"+insertionorderID);
					output = APCallCreateXML.APInsert("TFO_DJ_LMT_CRDT_RECORDS",strColumns2.toString(), strValues2.toString(), sessionId);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Insert output: " + output);	
					
					strValues2 = new StringBuffer();
					insertionorderID=LapsUtils.returnInsertionOrderID(tablName);
					strValues2.append("'"+wiNumber+"','"+"Specify Limit line"+"','"+ll_code+"',"+insertionorderID);
					output = APCallCreateXML.APInsert("TFO_DJ_LMT_CRDT_RECORDS",strColumns2.toString(), strValues2.toString(), sessionId);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Insert output: " + output);
				}
				
			} else if(is_100pct_cshbk !=null && !is_100pct_cshbk.equalsIgnoreCase("") && is_100pct_cshbk.equalsIgnoreCase("False")) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "is_100pct_cshbk: FALSE CASE");
				if(requestCategory.equalsIgnoreCase("GRNT") && requestType.equalsIgnoreCase("NI")) {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "is_100pct_cshbk: GUARANTEE ISSUANCE CASE");						
					strValues2 = new StringBuffer();
					if(ll_code !=null && !ll_code.equalsIgnoreCase("")){
						insertionorderID=LapsUtils.returnInsertionOrderID(tablName);
						strValues2.append("'"+wiNumber+"','"+"Please specify Limit line."+"','"+ll_code+"',"+insertionorderID);
						output = APCallCreateXML.APInsert("TFO_DJ_LMT_CRDT_RECORDS",strColumns2.toString(), strValues2.toString(), sessionId);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Insert output: " + output);
					}
					
				}
				else if((requestCategory.equalsIgnoreCase("SBLC") && requestType.equalsIgnoreCase("SBLC_NI"))
						|| (requestCategory.equalsIgnoreCase("ILC") && requestType.equalsIgnoreCase("ILC_NI"))) {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "is_100pct_cshbk: SBLC & ILC ISSUANCE CASE");						
					strValues2 = new StringBuffer();
					if(ll_code !=null && !ll_code.equalsIgnoreCase("")){
						insertionorderID=LapsUtils.returnInsertionOrderID(tablName);
						strValues2.append("'"+wiNumber+"','"+"Specify Limit line"+"','"+ll_code+"',"+insertionorderID);
						output = APCallCreateXML.APInsert("TFO_DJ_LMT_CRDT_RECORDS",strColumns2.toString(), strValues2.toString(), sessionId);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Insert output: " + output);
					}
				}
				
			}
			//ATP-383 20-01-2024 Jamshed Ends
		
		}catch(Exception e){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
	 }
	


}
