package com.newgen.ao.laps.sourcingchannel;

import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.response.LapsModifyResponse;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;

public class D_PROTRADE implements SourcingChannelHandler{
	public LapsModifyResponse resp = new LapsModifyResponse();
	private String decHist = "TFO_DJ_DEC_HIST";
	@Override
	public LapsModifyResponse dispatchEvent(String sessionId, String channelRefNumber,
			String correlationNo, String sourcingChannel, String mode, String wiNumber, String processName) {
		try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside D_PROTRADE");
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
			//			String outputXML = APCallCreateXML.APSelect("select WI_NAME FROM TFO_DJ_PROTRADE_TXN_DATA "
			//					+ "WHERE channel_ref_number='"+channelRefNumber+"' AND CORRELATIONID = '"+correlationNo
			//					+"' AND REQUESTMODE='"+mode+"'");
			//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXML is: " + outputXML);
			//			String workItemNumber = "";
			//			String exitWS = "Work Exit";
			//			XMLParser xp = new XMLParser(outputXML);
			//			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"mainCode is: " + mainCode);
			//			if (mainCode == 0) {
			//				workItemNumber = xp.getValueOf("WI_NAME");
			//			}
			String processType = "";
			String outputXML = "";
			try {
				outputXML = APCallCreateXML.APSelect("select PROCESS_TYPE FROM EXT_TFO "
						+ "WHERE WI_NAME='"+wiNumber+"'");
			} catch (Exception e1) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in fetch process type: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e1);
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXML is: " + outputXML);
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"mainCode is: " + mainCode);
			if (mainCode == 0) {
				processType = xp.getValueOf("PROCESS_TYPE");
			}
			if(processType.equalsIgnoreCase("PT")){
				String discardWS = "Discard_WI";
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"wiNameInput is: " + wiNumber);
				String output = "";
				int mainCode1 = -1;
				try {
					if(!wiNumber.isEmpty()){
						output = ProdCreateXML.WFAdhocWorkItem(sessionId, wiNumber, 1, discardWS, "3");
					}
				} catch (Exception e) {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in D_PROTRADE WFAdhocWorkItem:");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
				}
				XMLParser xp1 = new XMLParser(output);
				mainCode1 = Integer.parseInt(xp1.getValueOf("MainCode"));
				if (mainCode1 == 0) {
					//insert into dec hist
					StringBuilder strDecHistColumns = new StringBuilder();
					StringBuilder strDecHistValues = new StringBuilder();
					strDecHistColumns.append("WI_NAME").append(",").append("CREATE_DATE").append(",")
					.append("USER_ID").append(",").append("ACTION").append(",")
					.append("REMARKS").append(",").append("REASON_FOR_ACTION").append(",").append("BRANCH");
					strDecHistValues.append("'").append(xp.getValueOf(wiNumber)).append("',")
					.append("SYSTIMESTAMP,'PROTRADE SYSTEM USER','Discard','Workitem Discarded',"
							+ "'Discard Workitem',''");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inert into strDecHistColumns: "
							+strDecHistColumns.toString());
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inert into strDecHistValues: "
							+strDecHistValues.toString());
					try {
						APCallCreateXML.APInsert(decHist, strDecHistColumns.toString(), strDecHistValues.toString()
								,sessionId);
					} catch (Exception e) {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
					}
					resp.setStatusCode("0");
					resp.setStatusDescription("Workitem discarded successfully");
				} else {
					resp.setStatusCode("-1");
					resp.setStatusDescription("Workitem Not Discarded");
				}
			} else {
				resp.setStatusCode("-1");
				resp.setStatusDescription("Discard Failed [Not a Protrade workitem]");
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"set final response: ");
			resp.setChannelRefNumber(channelRefNumber);
			resp.setCorrelationId(correlationNo);
			resp.setChannelName(sourcingChannel);
			resp.setWiNumber(wiNumber);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in D_PROTRADE dispatchEvent: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		return resp;
	}
}
