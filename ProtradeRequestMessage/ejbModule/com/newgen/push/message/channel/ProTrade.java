package com.newgen.push.message.channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.wf.util.excp.NGException;
import com.newgen.push.message.APCallCreateXML;
import com.newgen.push.message.BPMReqLogMe;
import com.newgen.push.message.RequestMessage;
import com.newgen.push.message.SingleUserConnection;
import com.newgen.push.message.Utils.ProTradeUtils;

public class ProTrade extends RequestMessage{
	
	static HashMap<String, String> attributeMap = new HashMap<String, String>();
	private static Map<String, String> protradeStagingMap;
	private static Map<String, String> protradeDateMap;
	static String channelRefNumber;
	static String correlationId;
	private static Map<String, String> protradeCLOBMasterMap;

	public static void startProcessing(String message, String columns, String values, String sessionId, String cabinetName, String username,
			String password, String sessionInterval){
		int start;
		int deadEnd;
		int noOfFields;
		int end;
		int deadEndInner;
		int noOfFieldsInner;
		int startInner;
		int endInner=0;
		StringBuffer strColumns = new StringBuffer();
		StringBuffer strValues = new StringBuffer();
		StringBuffer strRepeaterColumns;
		StringBuffer strRepeaterValues;

		try {
		    BPMReqLogMe.logMe(1, "inside ProTrade startProcessing");
		    protradeStagingMap = ProTradeUtils.getInstance().getProtradeStagingMap();
		    protradeDateMap = ProTradeUtils.getInstance().getProtradeDateMap();
		    protradeCLOBMasterMap = ProTradeUtils.getInstance().getprotradeCLOBMasterMap();
		    if(message.contains("<attributeValue/>")){
		    	message = message.replaceAll("<attributeValue/>", "<attributeValue></attributeValue>");
			    BPMReqLogMe.logMe(1, "message after replacing blank tags: " + message);
		    }
			XMLParser xp = new XMLParser(message);
			channelRefNumber = xp.getValueOf("channelRefNumber");
			correlationId = xp.getValueOf("correlationId");
			strColumns.append(columns);
			strValues.append(values);
			start = xp.getStartIndex("attributeDetails", 0, 0);
			deadEnd = xp.getEndIndex("attributeDetails", start, 0);
			noOfFields = xp.getNoOfFields("attributes", start, deadEnd);
			end = 0;
			for (int i = 0; i < noOfFields; ++i) {
				start = xp.getStartIndex("attributes", end, 0);
				end = xp.getEndIndex("attributes", start, 0);
				String attributeKey = xp.getValueOf("attributeKey", start, end);
				String attributeValue = xp.getValueOf("attributeValue", start, end);
				if(i != (noOfFields-1)) {
					strColumns.append(attributeKey+",");
					if(protradeCLOBMasterMap.containsKey(attributeKey)
							&& protradeCLOBMasterMap.get(attributeKey).equals("Y")){
						strValues.append(createNormalizedXML(attributeValue)+",");
					} else if(attributeKey.contains("_DATE") && protradeDateMap.containsKey(attributeKey)
							&& protradeDateMap.get(attributeKey).equals("Y")){
						strValues.append("TO_DATE('"+attributeValue+"','DD/MM/YYYY hh24:mi:ss'),");
					} else {
						strValues.append("'"+attributeValue+"',");
					} 
				}
				else {
					strColumns.append(attributeKey);
					if(protradeCLOBMasterMap.containsKey(attributeKey)
							&& protradeCLOBMasterMap.get(attributeKey).equals("Y")){
						strValues.append(createNormalizedXML(attributeValue));
					}else if(attributeKey.contains("_DATE") && protradeDateMap.containsKey(attributeKey)
							&& protradeDateMap.get(attributeKey).equals("Y")){
						strValues.append("TO_DATE('"+attributeValue+"','DD/MM/YYYY hh24:mi:ss')");
					} else {
						strValues.append("'"+attributeValue+"'");
					}
				}
			}
			String opXml = APCallCreateXML.APInsert("TFO_DJ_PROTRADE_TXN_DATA", strColumns.toString(), 
					strValues.toString(), sessionId);
			XMLParser xmlParser = new XMLParser(opXml);
			if(Integer.parseInt(xmlParser.getValueOf("MainCode")) == 11) {
				sessionId = SingleUserConnection.getInstance(100).getSession(cabinetName, username, 
						password, sessionInterval);
				opXml = APCallCreateXML.APInsert("TFO_DJ_PROTRADE_TXN_DATA", strColumns.toString(), 
						strValues.toString(), sessionId);
				xmlParser = new XMLParser(opXml);
			} 
			
			if(Integer.parseInt(xmlParser.getValueOf("MainCode")) != 0) {
				BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO,"calling triggerExceptionMail");
				APCallCreateXML.triggerExceptionMail("TFO_DJ_PROTRADE_TXN_DATA", xmlParser.getValueOf("Output"), 
						channelRefNumber, correlationId, sessionId);
			} else {
			    start = xp.getStartIndex("Repeaters", 0, 0);
				deadEnd = xp.getEndIndex("Repeaters", start, 0);
				noOfFields = xp.getNoOfFields("Repeater", start, deadEnd);
				end = 0;
				for (int i = 0; i < noOfFields; ++i) {
					strRepeaterColumns = new StringBuffer();
					strRepeaterValues = new StringBuffer();
					String Repeater = xp.getNextValueOf("Repeater");
					BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO,"Repeater :"+Repeater);
					String type = xp.getNextValueOf("type");
					String rownum = xp.getNextValueOf("rowno");
					XMLParser xp2 = new XMLParser(Repeater); 
					noOfFieldsInner = xp2.getNoOfFields("attributes");
					strRepeaterColumns.append("CHANNELREFNUMBER,CORRELATIONID,INSERTIONDATETIME,ROWNO,");
					strRepeaterValues.append("'"+channelRefNumber+"','"+correlationId+"',CURRENT_TIMESTAMP,'"+rownum+"',");
					for (int  j = 0 ;j<noOfFieldsInner ;j++) {
						String attributeKey = xp2.getNextValueOf("attributeKey");
						String attributeValue = xp2.getNextValueOf("attributeValue");
						BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO,"attributeKey :"+ attributeKey);
						BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO,"attributeValue :"+ attributeValue);
						if(j != (noOfFieldsInner-1)) {
							strRepeaterColumns.append(attributeKey+",");
							if(attributeKey.contains("_DATE") && protradeDateMap.containsKey(attributeKey)
									&& protradeDateMap.get(attributeKey).equals("Y")){
								strRepeaterValues.append("TO_DATE('"+attributeValue+"','DD/MM/YYYY hh24:mi:ss'),");
							} else {
								strRepeaterValues.append("'"+attributeValue+"',");
							}					
						} else {
							strRepeaterColumns.append(attributeKey);
							if(attributeKey.contains("_DATE") && protradeDateMap.containsKey(attributeKey)
									&& protradeDateMap.get(attributeKey).equals("Y")){
								strRepeaterValues.append("TO_DATE('"+attributeValue+"','DD/MM/YYYY hh24:mi:ss')");
							} else {
								strRepeaterValues.append("'"+attributeValue+"'");
							}
						}
					}
					String tableName = protradeStagingMap.get(type);
					BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "repeater tableName: " + tableName);	 				
					if(!tableName.isEmpty()){
						String opXmlRepeater = APCallCreateXML.APInsert(tableName, 
								strRepeaterColumns.toString(), strRepeaterValues.toString(), sessionId);
						BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "opXmlRepeater: " + opXmlRepeater);
						xmlParser = new XMLParser(opXmlRepeater);
						if(Integer.parseInt(xmlParser.getValueOf("MainCode")) != 0) {
							BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO,"calling triggerExceptionMail");
							APCallCreateXML.triggerExceptionMail(tableName, xmlParser.getValueOf("Output"), 
									channelRefNumber, correlationId, sessionId);
						}
					}
				}
			}
		} catch (Exception e) {
			APCallCreateXML.triggerExceptionMail("", e.toString(), channelRefNumber, correlationId, sessionId);
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_ERROR, "startProcessing: ");
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_ERROR, e);	 				
		}
	}
	
	public static  String createNormalizedXML(String outputXml) {
		try {
			if ((outputXml != null) && (!("".equalsIgnoreCase(outputXml)))) {
				outputXml = outputXml.replace("'", "''");
				outputXml = outputXml.replace("&", "'||chr(38)||'");
				if (outputXml.length() > 3200) {				
					outputXml = breakCLOBString(outputXml);
					return outputXml;
				}
				outputXml = "TO_NCLOB('" + outputXml + "')";
				return outputXml;
			}			
		} catch (Exception ex) {
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_ERROR, ex);	 				
		}
		return "''";
	}
	
	private static  String breakCLOBString(String xml) {
		int itr = xml.length() / 3200;
		int mod = xml.length() % 3200;
		if (mod > 0) {
			++itr;
		}
		StringBuilder response = new StringBuilder();
		try {
			for (int i = 0; i < itr; ++i) {
				if (i == 0) {
					response.append("TO_NCLOB('" + xml.substring(0, 3200) + "')");
				} else if (i < itr - 1) {
					response.append(" || TO_NCLOB('" + xml.substring(3200 * i,3200 * (i + 1)) + "')");
				} else {
					response.append(" || TO_NCLOB('" + xml.substring(3200 * i,xml.length()) + "') ");
				}
			}
		} catch (Exception e) {
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_ERROR, e);	 				
		}
		return response.toString();
	}

}
