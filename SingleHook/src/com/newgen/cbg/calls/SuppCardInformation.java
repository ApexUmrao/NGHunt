
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

	public class SuppCardInformation implements ICallExecutor, Callable<String> {
		private String sessionId;
		private String wiName;
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
		private String auditCallName = "SCPD";
		private boolean skipCall = false;  
		private String associatedCard ="";
		private String primaryCid = "";
		String outputXml;
		public SuppCardInformation(Map<String, String> defaultMap, String sessionId, String stageId, String wiName, Boolean prevStageNoGo, CallEntity callEntity){
			this.wiName = wiName;
			stageID = Integer.parseInt(stageId);
			this.sessionId = sessionId;
			this.prevStageNoGo = prevStageNoGo;
			this.defaultMap = defaultMap;
			this.callEntity = callEntity;		
			try {
				senderID = defaultMap.get("SENDER_ID");		
				primaryCid=defaultMap.get("primaryCid");
				associatedCard=defaultMap.get("associatedCard");
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"SuppCardInformation call: primaryCid "+primaryCid+" " + "associatedCard:" +associatedCard);
				if ("".equals(primaryCid)) {
					skipCall = true;
				}
			} catch (Exception e) {
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SCPD100", e, sessionId);
			}
		}

		@Override
		public String call() throws Exception {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"SuppCardInformation call: inside");
			String inputXml = "" ;
			String outputXml = "<returnCode>0</returnCode>";
			try {
				if (!skipCall) {
					inputXml = createInputXML();
					if (!prevStageNoGo) {
						outputXml = ExecuteXML.executeWebServiceSocket(inputXml);
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SuppCardInformation outputXml ===> " + outputXml);
						if (outputXml == null || outputXml.equalsIgnoreCase("")) {
							outputXml = "<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>";
						}
						responseHandler(outputXml, inputXml);
					} else {
						callStatus = "F";
						updateCallOutput(inputXml);
					}
				} else {
					callStatus = "X";
					updateCallOutput("");
				}
			} catch (Exception e) {
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SCPD100", e, sessionId);
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
				.append("<DSCOPCallType>InqSuppcardDetails</DSCOPCallType>").append("\n")			
				.append("<WiName>" + wiName + "</WiName>").append("\n")
				.append("<REF_NO>" + refNo + "</REF_NO>").append("\n")
				.append("<OLDREF_NO>" + refNo + "</OLDREF_NO>").append("\n")
				.append("<senderId>" + senderID + "</senderId>").append("\n")
				.append("<CUST_ID>" + primaryCid + "</CUST_ID>").append("\n")
				.append("<cardType>" + associatedCard + "</cardType>").append("\n")
				.append("</APWebService_Input>");
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, inputXml.toString());
			} catch (Exception e) {
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SCPD100", e, sessionId);
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
				String outputXMLTemp ="";
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "SCPD004", "AS DependencyCall:"+ callEntity.getDependencyCallID(), sessionId);
				outputXMLTemp = CallHandler.getInstance().executeDependencyCall(callEntity, defaultMap, sessionId, wiName, prevStageNoGo);
				if(!outputXMLTemp.isEmpty()) {
					outputXml=outputXMLTemp;
				}
			} catch (Exception e) {
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SCPD100", e, sessionId);
			}
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
					if(returnCode == 0) {
						callStatus = "Y";
						int start = xp.getStartIndex("suppCardDetails", 0, 0);
						int deadEnd = xp.getEndIndex("suppCardDetails", start, 0);
						int noOfFieldS = xp.getNoOfFields("suppCards", start,deadEnd);
						String cardType = xp.getValueOf("CardType");
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "suppCards noOfFieldS : "+noOfFieldS+ "cardType"+cardType);
						int end = 0;
						for(int i = 0; i < noOfFieldS; ++i){
							start = xp.getStartIndex("suppCards", end, 0);
							end = xp.getEndIndex("suppCards", start, 0);
							String primaryCardNumber = xp.getValueOf("primaryCardNumber", start, end);
							String suppCardNumber = xp.getValueOf("suppCardNumber", start, end);
							String suppCardEmbossName = xp.getValueOf("suppCardEmbossName", start, end);
							String suppCardProductDesc = xp.getValueOf("suppCardProductDesc", start, end);
							String suppCardStatus = xp.getValueOf("suppCardStatus", start, end);
							String suppCardMobileNumber = xp.getValueOf("suppCardMobileNumber", start, end);
							String suppCardEmailAddress = xp.getValueOf("suppCardEmailAddress", start, end);
							String suppCardDOB = xp.getValueOf("suppCardDOB", start, end);
							String suppCardGender = xp.getValueOf("suppCardGender", start, end);
							String suppCardRelationWithPrimary = xp.getValueOf("suppCardRelationWithPrimary", start, end);
							if("Credit".equalsIgnoreCase(cardType)){
								String colNames="DATETIME,WI_NAME,PRIMARY_CARD_NUMBER,SUPP_CARD_NUMBER,EMBOSS_NAME,CARDTYPE_DESC,CARD_STATUS,MOBILE_NO,EMAIL,DOB,GENDER,RELATION";
								String valNames="SYSDATE,'"+wiName+"','"+primaryCardNumber+"','"+suppCardNumber+"','"+suppCardEmbossName+"','"+suppCardProductDesc+"','"+suppCardStatus+"','"+suppCardMobileNumber+"','"+suppCardEmailAddress+"','"+suppCardDOB+"','"+suppCardGender+"','"+suppCardRelationWithPrimary+"'";
								APCallCreateXML.APInsert("BPM_DSCOP_CREDITCARD_DETAILS", colNames, valNames, sessionId);		
							}	
						} 
						DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "SGCC090", "InqSuppcardDetails Success", sessionId);
					} else {
						callStatus = "N";
						DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "SGCC101", "InqSuppcardDetails Failed", sessionId);
					}
				} else {
					callStatus = "N";
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "SGCC102", "InqSuppcardDetails Failed", sessionId);
				}
				updateCallOutput(inputXml);
			}catch (Exception e) {
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SCPD100", e, sessionId);
			}		
		}

		@Override
		public void updateCallOutput(String inputXml) throws Exception {
			try {
				String valList = "'"+ wiName +"',"+ stageID +", 'SuppCardInformation', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"'";
				APCallCreateXML.APInsert("USR_0_DSCOP_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
						+ "ERROR_DETAIL, STATUS, REASON", valList, sessionId);

				if(callStatus.equals("Y")){
					String valList2 = "'"+ wiName +"',"+ stageID +", 'SuppCardInformation', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
							errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml +"', 0";
					APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON,"
							+ " RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);
					executeDependencyCall();
				}
				else {
					String valList2 = "'"+ wiName +"',"+ stageID +", 'SuppCardInformation', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
							errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml +"', 1";
					APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON, "
							+" RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);
				}
			} catch (Exception e) {
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SCPD100", e, sessionId);
			}
		}
	}



