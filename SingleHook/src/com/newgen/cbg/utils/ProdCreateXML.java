package com.newgen.cbg.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.newgen.cbg.cache.ProcessEventCache;
import com.newgen.cbg.cache.RequestAttributeDetails;
import com.newgen.cbg.implementation.BpmColumnMappingDetails;
import com.newgen.cbg.logger.DSCOPLogMe;
import com.newgen.omni.wf.util.excp.NGException;
import com.newgen.omni.wf.util.xml.XMLParser;

public class ProdCreateXML {
	static private String cabinetName;
	static private int processDefId;

	public static void init(String cabName, int processdefId)
	{
		cabinetName = cabName;
		processDefId = processdefId; 
	}

	public static String WMConnect(String sUsername, String sPassword) throws NGException
	{
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<WMConnect_Input>").append("\n")
		.append("<Option>WMConnect</Option>").append("\n")
		.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
		.append("<Name>" + sUsername + "</Name>").append("\n")
		.append("<Password>" + sPassword + "</Password>").append("\n")
		.append("<UserExist>Y</UserExist>").append("\n")
		.append("</WMConnect_Input>");			
		String outputXML =  ExecuteXML.executeXMLNoLogging(sInputXML.toString());
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WMConnect OutputXML ===>" + outputXML);
		return outputXML;    
	}

	public static String WFUploadWorkItem(String sessionId, String initiateAlso, HashMap<String, String> attribMap) throws NGException
	{
		StringBuilder attribXML = new StringBuilder("");
		StringBuilder key = new StringBuilder("");
		StringBuilder val = new StringBuilder("");
		Set<Map.Entry<String, String>> entrySet = attribMap.entrySet();
		Iterator<Entry<String,String>> entrySetIterator = entrySet.iterator();
		while(entrySetIterator.hasNext()){
			Entry<String, String> entry = entrySetIterator.next();
			key = key.append(entry.getKey());			
			if(key.toString().contains("_DATETIME") || key.toString().contains("_DateTime") || key.toString().equalsIgnoreCase("EXPIRY_DATE"))	
			{				
				val = val.append(DSCOPUtils.getInstance().formatToBPMDateTime(entry.getValue().toString()));

			} 
			else if(key.toString().contains("_DATE"))
			{
				val = val.append(DSCOPUtils.getInstance().formatToBPMDate(entry.getValue().toString()));
			} 
			else 
			{
				val = val.append(entry.getValue());
			}			
			attribXML.append(key.toString() + (char)21 + val.toString() + (char)25);
			key.setLength(0);
			val.setLength(0);
		}

		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<WFUploadWorkItem_Input>").append("\n")
		.append("<Option>WFUploadWorkItem</Option>").append("\n")
		.append("<SessionId>" +sessionId+ "</SessionId>").append("\n")
		.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
		.append("<ProcessDefId>" + processDefId + "</ProcessDefId>").append("\n")
		.append("<InitiateAlso>" + initiateAlso + "</InitiateAlso>").append("\n")
		.append("<ValidationRequired></ValidationRequired>").append("\n")
		.append("<DataDefName></DataDefName>").append("\n")
		.append("<InitiateFromActivityId>1</InitiateFromActivityId>").append("\n")
		.append("<InitiateFromActivityName>INITIATE_APPLICATION</InitiateFromActivityName>").append("\n")
		.append("<Attributes>" + attribXML + "</Attributes>").append("\n")
		.append("</WFUploadWorkItem_Input>");
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WFUploadWorkItem OutputXML ===>" + outputXML);
		return outputXML;  
	}

	public static String WFUploadWorkItem(String sessionId, String initiateAlso, HashMap<String, String> attribMap, String processDefId, HashMap<String, String> complexMap) throws NGException
	{
		StringBuilder attribXML = new StringBuilder("");
		StringBuilder key = new StringBuilder("");
		StringBuilder val = new StringBuilder("");
		attribMap.entrySet().removeAll(complexMap.entrySet());
		Set<Map.Entry<String, String>> entrySet = attribMap.entrySet();
		Iterator<Entry<String,String>> entrySetIterator = entrySet.iterator();
		while(entrySetIterator.hasNext()){
			Entry<String, String> entry = entrySetIterator.next();
			key = key.append(entry.getKey());	
			if(key.toString().contains("_DATETIME") || key.toString().contains("_DateTime") || key.toString().equalsIgnoreCase("EXPIRY_DATE"))
			{				
				val = val.append(DSCOPUtils.getInstance().formatToBPMDateTime(entry.getValue().toString()));

			} 
			else if(key.toString().contains("_DATE"))
			{
				val = val.append(DSCOPUtils.getInstance().formatToBPMDate(entry.getValue().toString()));
			} 
			else 
			{
				val = val.append(entry.getValue());
			}			
			attribXML.append(key.toString() + (char)21 + val.toString() + (char)25);
			key.setLength(0);
			val.setLength(0);
		}

		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<WFUploadWorkItem_Input>").append("\n")
		.append("<Option>WFUploadWorkItem</Option>").append("\n")
		.append("<SessionId>" +sessionId+ "</SessionId>").append("\n")
		.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
		.append("<ProcessDefId>" + processDefId + "</ProcessDefId>").append("\n")
		.append("<InitiateAlso>" + initiateAlso + "</InitiateAlso>").append("\n")
		.append("<ValidationRequired></ValidationRequired>").append("\n")
		.append("<DataDefName></DataDefName>").append("\n")
		.append("<InitiateFromActivityId>1</InitiateFromActivityId>").append("\n")
		.append("<InitiateFromActivityName>AutoIntro</InitiateFromActivityName>").append("\n")
		.append("<Attributes>" + attribXML + "</Attributes>").append("\n")
		.append("</WFUploadWorkItem_Input>");
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WFUploadWorkItem OutputXML ===>" + outputXML);
		return outputXML;  
	}
	public static String WFUploadWorkItemAO(String sessionId, String initiateAlso, HashMap<String, String> attribMap, String processDefId, HashMap<String, String> complexMap, String activityName) throws NGException
	{
		StringBuilder attribXML = new StringBuilder("");
		StringBuilder key = new StringBuilder("");
		StringBuilder val = new StringBuilder("");
		attribMap.entrySet().removeAll(complexMap.entrySet());
		Set<Map.Entry<String, String>> entrySet = attribMap.entrySet();
		Iterator<Entry<String,String>> entrySetIterator = entrySet.iterator();
		while(entrySetIterator.hasNext()){
			Entry<String, String> entry = entrySetIterator.next();
			key = key.append(entry.getKey());			
			if(key.toString().contains("_DATETIME") || key.toString().contains("_DateTime") || key.toString().equalsIgnoreCase("EXPIRY_DATE"))
			{				
				val = val.append(DSCOPUtils.getInstance().formatToBPMDateTime(entry.getValue().toString()));

			} 
			else if(key.toString().contains("_DATE"))
			{
				val = val.append(DSCOPUtils.getInstance().formatToBPMDate(entry.getValue().toString()));
			} 
			else 
			{
				val = val.append(entry.getValue());
			}			
			attribXML.append(key.toString() + (char)21 + val.toString() + (char)25);
			key.setLength(0);
			val.setLength(0);
		}

		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<WFUploadWorkItem_Input>").append("\n")
		.append("<Option>WFUploadWorkItem</Option>").append("\n")
		.append("<SessionId>" +sessionId+ "</SessionId>").append("\n")
		.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
		.append("<ProcessDefId>" + processDefId + "</ProcessDefId>").append("\n")
		.append("<InitiateAlso>" + initiateAlso + "</InitiateAlso>").append("\n")
		.append("<ValidationRequired></ValidationRequired>").append("\n")
		.append("<DataDefName></DataDefName>").append("\n")
		.append("<InitiateFromActivityId>233</InitiateFromActivityId>").append("\n")
		.append("<InitiateFromActivityName>"+activityName+"</InitiateFromActivityName>").append("\n")
		.append("<Attributes>" + attribXML + "</Attributes>").append("\n")
		.append("</WFUploadWorkItem_Input>");
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WFUploadWorkItem OutputXML ===>" + outputXML);
		return outputXML;  
	}
	public static String WMGetWorkItem(String sessionId, String ProcessInstanceId, int WorkitemId) throws NGException
	{
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<WMGetWorkItem_Input>").append("\n")
		.append("<Option>WMGetWorkItem</Option>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
		.append("<ProcessInstanceId>" + ProcessInstanceId + "</ProcessInstanceId>").append("\n")
		.append("<WorkitemId>" + WorkitemId + "</WorkitemId>").append("\n")
		.append("</WMGetWorkItem_Input>");
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WMGetWorkItem OutputXML ===>" + outputXML);
		int mainCode = Integer.parseInt(new XMLParser(outputXML).getValueOf("MainCode"));
		if(mainCode == 16){
			WMUnlockWorkItem(sessionId, ProcessInstanceId, WorkitemId);
		}
		return outputXML;   
	}

	public static String WMUnlockWorkItem(String sessionId, String ProcessInstanceId, int WorkitemId) throws NGException
	{
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<WMUnlockWorkItem_Input>").append("\n")
		.append("<Option>WMUnlockWorkItem</Option>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
		.append("<UnlockOption>W</UnlockOption>").append("\n")
		.append("<ProcessInstanceId>" + ProcessInstanceId + "</ProcessInstanceId>").append("\n")
		.append("<WorkitemId>" + WorkitemId + "</WorkitemId>").append("\n")
		.append("</WMUnlockWorkItem_Input>");
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		return outputXML;
	}

	public static String WMCompleteWorkItem(String sessionId, String ProcessInstanceId, int WorkitemId) throws NGException
	{
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<WMCompleteWorkItem_Input>").append("\n")
		.append("<Option>WMCompleteWorkItem</Option>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
		.append("<ProcessInstanceId>" + ProcessInstanceId + "</ProcessInstanceId>").append("\n")
		.append("<WorkitemId>" + WorkitemId + "</WorkitemId>").append("\n")
		.append("</WMCompleteWorkItem_Input>");
		//Lock Workitem
		WMGetWorkItem(sessionId, ProcessInstanceId, WorkitemId);		
		String outputXML = ExecuteXML.executeXML(sInputXML.toString());
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WMCompleteWorkItem OutputXML ===>" + outputXML);
		return outputXML;   
	}

	public static String WFSetAttributes(String sessionId,String processInstanceid, int WorkitemId, HashMap<String, String> attribMap) throws NGException
	{
		StringBuilder attribXML = new StringBuilder("");
		StringBuilder key = new StringBuilder("");
		StringBuilder val = new StringBuilder("");
		Set<Map.Entry<String, String>> entrySet = attribMap.entrySet();
		Iterator<Entry<String,String>> entrySetIterator = entrySet.iterator();
		while(entrySetIterator.hasNext()){
			Entry<String, String> entry = entrySetIterator.next();
			key = key.append(entry.getKey());
			if(entry.getKey().isEmpty()){
				continue;
			}
			if(key.toString().contains("_DATETIME") || key.toString().contains("_DateTime") || key.equals("EXPIRY_DATE"))
			{
				val = val.append(DSCOPUtils.getInstance().formatToBPMDateTime(entry.getValue().toString()));

			} 
			else if(key.toString().contains("_DATE"))
			{
				val = val.append(DSCOPUtils.getInstance().formatToBPMDate(entry.getValue().toString()));

			} 
			else if(key.toString().contains("DATE_OF"))
			{
				val = val.append(DSCOPUtils.getInstance().formatToBPMDate(entry.getValue().toString()));

			} 
			else 
			{
				if(entry.getValue().toString().contains("&")){
					val = val.append(entry.getValue().toString().replace("&", "&amp;"));
				}
				else{
					val = val.append(entry.getValue());
				}
			}
			attribXML.append("<").append(key.toString()).append(">").append(val.toString()).append("</").append(key.toString()).append(">");
			key.setLength(0);
			val.setLength(0);
		}

		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<WMAssignWorkItemAttributes_Input>").append("\n")
		.append("<Option>WMAssignWorkItemAttributes</Option>").append("\n")
		.append("<SessionId>"+ sessionId +"</SessionId>").append("\n")
		.append("<EngineName>"+ cabinetName + "</EngineName>").append("\n")
		.append("<ProcessDefId>"+ processDefId +"</ProcessDefId>").append("\n")
		.append("<ProcessInstanceId>"+ processInstanceid +"</ProcessInstanceId>").append("\n")
		.append("<WorkitemId>"+ WorkitemId +"</WorkitemId>").append("\n")
		.append("<UserDefVarFlag>Y</UserDefVarFlag>").append("\n")
		.append("<ReminderFlag>N</ReminderFlag>").append("\n")
		.append("<Attributes>"+ attribXML +"</Attributes>").append("\n")
		.append("</WMAssignWorkItemAttributes_Input>");
		//Lock Workitem
		WMGetWorkItem(sessionId, processInstanceid, WorkitemId);
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WFSetAttributes OutputXML ===>" + outputXML);
		return outputXML;  
	}

	public static String WFSetAttributes(String sessionId,String processInstanceid, int WorkitemId, HashMap<String, String> attribMap, String processDefId,
			HashMap<String, String> complexMap) throws NGException
	{
		StringBuilder attribXML = new StringBuilder("");
		StringBuilder key = new StringBuilder("");
		StringBuilder val = new StringBuilder("");
		attribMap.entrySet().removeAll(complexMap.entrySet());
		Set<Map.Entry<String, String>> entrySet = attribMap.entrySet();
		Iterator<Entry<String,String>> entrySetIterator = entrySet.iterator();
		while(entrySetIterator.hasNext()){
			Entry<String, String> entry = entrySetIterator.next();
			key = key.append(entry.getKey());
			if(key.toString().contains("_DATETIME") || key.toString().contains("_DateTime") || key.equals("EXPIRY_DATE"))
			{
				val = val.append(DSCOPUtils.getInstance().formatToBPMDateTime(entry.getValue().toString()));

			} 
			else if(key.toString().contains("_DATE"))
			{
				val = val.append(DSCOPUtils.getInstance().formatToBPMDate(entry.getValue().toString()));

			} 
			else if(key.toString().contains("DATE_OF"))
			{
				val = val.append(DSCOPUtils.getInstance().formatToBPMDate(entry.getValue().toString()));

			} 
			else 
			{
				if(entry.getValue().toString().contains("&")){
					val = val.append(entry.getValue().toString().replace("&", "&amp;"));
				}
				else{
					val = val.append(entry.getValue());
				}
			}
			attribXML.append("<").append(key.toString()).append(">").append(val.toString()).append("</").append(key.toString()).append(">");
			key.setLength(0);
			val.setLength(0);
		}
		if(complexMap != null){
			entrySet = complexMap.entrySet();
			entrySetIterator = entrySet.iterator();
			while(entrySetIterator.hasNext()){
				Entry<String, String> entry = entrySetIterator.next();
				attribXML.append(insertComplexData(entry.getValue(),entry.getKey()));			
			}
		}

		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WFSetAttributes attribXML ===>" + attribXML);
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<WMAssignWorkItemAttributes_Input>").append("\n")
		.append("<Option>WMAssignWorkItemAttributes</Option>").append("\n")
		.append("<SessionId>"+ sessionId +"</SessionId>").append("\n")
		.append("<EngineName>"+ cabinetName + "</EngineName>").append("\n")
		.append("<ProcessDefId>"+ processDefId +"</ProcessDefId>").append("\n")
		.append("<ProcessInstanceId>"+ processInstanceid +"</ProcessInstanceId>").append("\n")
		.append("<WorkitemId>"+ WorkitemId +"</WorkitemId>").append("\n")
		.append("<UserDefVarFlag>Y</UserDefVarFlag>").append("\n")
		.append("<ReminderFlag>N</ReminderFlag>").append("\n")
		.append("<Attributes>"+ attribXML +"</Attributes>").append("\n")
		.append("</WMAssignWorkItemAttributes_Input>");
		//Lock Workitem
		WMGetWorkItem(sessionId, processInstanceid, WorkitemId);
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WFSetAttributes OutputXML ===>" + outputXML);
		return outputXML;  
	}

	public static String WFSetAttributesNew(String sessionId,String processInstanceid, int WorkitemId, 
			HashMap<String, String> attribMap, String processDefId) throws Exception
	{
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WFSetAttributesNew Inside");
		int noOfCustSearched = 0;
		HashMap<String,RequestAttributeDetails> reqAttrib =  ProcessEventCache.getReference().getSourcingChannelAttribMap("MIB");
		StringBuilder attribXML = new StringBuilder("");
		StringBuilder key = new StringBuilder("");
		StringBuilder val = new StringBuilder("");
		HashMap<String, JSONArray> complexMap = new HashMap<String, JSONArray>();
		HashMap<String, String> jsonMap = new HashMap<String, String>();
		attribMap.remove("");
		Set<Map.Entry<String, JSONArray>> entrySetComplex;
		Iterator<Entry<String,JSONArray>> entrySetIteratorComplex;
		Set<Map.Entry<String, String>> entrySet = attribMap.entrySet();
		Iterator<Entry<String,String>> entrySetIterator = entrySet.iterator();
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Key to get map data: "+attribMap.get("SOURCING_CHANNEL")+"#"+attribMap.get("STAGE_ID"));
		while(entrySetIterator.hasNext()){
			Entry<String, String> entry = entrySetIterator.next();
			key = key.append(entry.getKey()); 
			if(key.toString().contains("_DATETIME") || key.toString().contains("_DateTime") || key.toString().contains("EXPIRY_DATE"))
			{
				val = val.append(DSCOPUtils.getInstance().formatToBPMDateTime(entry.getValue().toString()));

			} 
			else if(key.toString().contains("_DATE"))
			{
				val = val.append(DSCOPUtils.getInstance().formatToBPMDate(entry.getValue().toString()));

			} 
			else if(key.toString().contains("DATE_OF"))
			{
				val = val.append(DSCOPUtils.getInstance().formatToBPMDate(entry.getValue().toString()));

			} 
			else 
			{
				if(entry.getValue().toString().contains("&")){
					val = val.append(entry.getValue().toString().replace("&", "&amp;"));
				}
				else{
					val = val.append(entry.getValue());

					if(key.toString().equalsIgnoreCase("CUSTOMER_DETAILS")){
						val = new StringBuilder(createValidJsonForAccRelation(val.toString()));

					} 


				}
			}

			if(reqAttrib != null && reqAttrib.containsKey(key.toString())){
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WFSetAttributesNew reqAttrib contains: "+ key);
				RequestAttributeDetails rad = reqAttrib.get(key.toString());
				if(rad.getAttributeType().equalsIgnoreCase("C")){
					if(complexMap.containsKey(rad.getMappingVarName())){
						JSONArray ja = complexMap.get(rad.getMappingVarName());
						JSONObject jo = new JSONObject();
						jo.put(rad.getMappingColumnName(), val.toString());
						ja.put(jo);
						complexMap.put(rad.getMappingVarName(), ja);
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WFSetAttributesNew reqAttrib complexMap: "+ complexMap);

					} else {
						JSONArray ja = new JSONArray();
						JSONObject jo = new JSONObject();
						jo.put(rad.getMappingColumnName(), val.toString());
						ja.put(jo);
						complexMap.put(rad.getMappingVarName(), ja);
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WFSetAttributesNew reqAttrib ja1: "+ ja);
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WFSetAttributesNew reqAttrib complexMap:2 "+ complexMap);

					}	
				} else if(rad.getAttributeType().equalsIgnoreCase("J")){
					if("Y".equalsIgnoreCase(rad.getDeleteTableEntry())){
						APCallCreateXML.APDelete(rad.getComplexTableName(), "WI_NAME = N'"+processInstanceid+"'", sessionId);
					}
					jsonMap.put(rad.getMappingVarName(), val.toString());
					if(key.toString().equalsIgnoreCase("CUSTOMER_DETAILS")){
						JsonElement jelement = new JsonParser().parse(val.toString());
						noOfCustSearched = jelement.getAsJsonArray().size();
					}
				}else if (rad.getAttributeType().equalsIgnoreCase("NC")) {
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"NC : key: "+ key);
					HashMap<String, BpmColumnMappingDetails> bpmCMDetails = ProcessEventCache.getReference().getColumnMappingDetails();
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"NC : bpmCMDetails: "+ bpmCMDetails.toString()+" rad.getComplexTableName() "+rad.getComplexTableName());
					
					if (bpmCMDetails != null && bpmCMDetails.containsKey(rad.getComplexTableName())) {
						HashMap<String,String> colMap = bpmCMDetails.get(rad.getComplexTableName()).getHashmap();
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"NC : colMap: "+ colMap.toString());
						if (rad.getDeleteTableEntry().equals("U")) {
							
							DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"NC : getDeleteTableEntry: "+ rad.getDeleteTableEntry());
							JsonElement jelement = new JsonParser().parse(val.toString());
							JsonArray jArray = jelement.getAsJsonArray();
							DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"NC : jArray: "+ jArray.toString());
							
							for (JsonElement jsonElement : jArray) {
								JsonObject jObject = jsonElement.getAsJsonObject();
								Gson gson = new Gson();
								String str = new Gson().toJson(jObject);
								DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"NC : jArray str: "+ str);
								
								HashMap<String, Object> mm = gson.fromJson(str, HashMap.class);
								Set<Map.Entry<String, Object>> entrySet1 = mm.entrySet();
								DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"NC : jArray entrySet1: "+ entrySet1.toString());
								
								Iterator<Entry<String, Object>> entrySetIterator1 = entrySet1.iterator();
								HashMap<String,String> colValMap = new HashMap<>(); 
								while (entrySetIterator1.hasNext()) {
									Entry<String, Object> entry1 = entrySetIterator1.next();
									String val1 = entry1.getValue().toString();
									String key1 = entry1.getKey();
									if (colMap.containsKey(key1)) {
										colValMap.put(colMap.get(key1), val1);
									}
								}
								DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"NC : jArray colValMap: "+ colValMap.toString());
								String mappingColumnName = rad.getMappingColumnName();
								String colValue = colValMap.get(mappingColumnName);
						        DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"NC :: mappingColumnName : "+ mappingColumnName +" colValue : "+ colValue);
								if (colValMap != null) {
									colValMap.remove(mappingColumnName);
									String columns = colValMap.keySet().stream().collect(Collectors.joining(","));
									String values = colValMap.values().stream().map(value -> "'" + value + "'").collect(Collectors.joining(","));
							        DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"NC :: columns : "+ columns + " values : " + values);

									if (!colValue.isEmpty()) {
								        APCallCreateXML.APUpdate(rad.getComplexTableName(), columns, values, "WI_NAME = N'" + processInstanceid + "' AND "+ mappingColumnName +" = '"+ colValue +"'", sessionId);
									} else {
										APCallCreateXML.APInsert(rad.getComplexTableName(), columns + ",WI_NAME", values +",'"+ processInstanceid +"'", sessionId);
									}
								}
							}
						}
					}
				}
				if (key.toString().equalsIgnoreCase("CUSTOMER_DETAILS")) {
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "val accRel	");
					attribXML.append("<").append("no_of_cust_searched").append(">").append(noOfCustSearched).append("</").append("no_of_cust_searched").append(">");
				}
				key.setLength(0);
				val.setLength(0);
				continue;
			}
			attribXML.append("<").append(key.toString()).append(">").append(val.toString()).append("</").append(key.toString()).append(">");
			key.setLength(0);
			val.setLength(0); 
		}

		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WFSetAttributesNew jsonMap: "+jsonMap);
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WFSetAttributesNew attribXML: "+attribXML);
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WFSetAttributesNew complexMap: "+complexMap);


		if(complexMap.size() > 0){
			entrySetComplex = complexMap.entrySet();
			entrySetIteratorComplex = entrySetComplex.iterator();
			while(entrySetIteratorComplex.hasNext()){
				Entry<String, JSONArray> entry = entrySetIteratorComplex.next();
				attribXML.append(insertComplexData(entry.getValue().toString(),entry.getKey()));			
			}
		}
		if(jsonMap.size() > 0){
			entrySet = jsonMap.entrySet();
			entrySetIterator = entrySet.iterator();
			while(entrySetIterator.hasNext()){
				Entry<String, String> entry = entrySetIterator.next();
				attribXML.append(insertComplexData(entry.getValue(),entry.getKey()));			
			}
		}

		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WFSetAttributesNew attribXML ===>" + attribXML);
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<WMAssignWorkItemAttributes_Input>").append("\n")
		.append("<Option>WMAssignWorkItemAttributes</Option>").append("\n")
		.append("<SessionId>"+ sessionId +"</SessionId>").append("\n")
		.append("<EngineName>"+ cabinetName + "</EngineName>").append("\n")
		.append("<ProcessDefId>"+ processDefId +"</ProcessDefId>").append("\n")
		.append("<ProcessInstanceId>"+ processInstanceid +"</ProcessInstanceId>").append("\n")
		.append("<WorkitemId>"+ WorkitemId +"</WorkitemId>").append("\n")
		.append("<UserDefVarFlag>Y</UserDefVarFlag>").append("\n")
		.append("<ReminderFlag>N</ReminderFlag>").append("\n")
		.append("<Attributes>"+ attribXML +"</Attributes>").append("\n")
		.append("</WMAssignWorkItemAttributes_Input>");
		//Lock Workitem
		WMGetWorkItem(sessionId, processInstanceid, WorkitemId);
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WFSetAttributesNew OutputXML ===>" + outputXML);

	

		return outputXML;  
	}


	public static String IsSessionValid(String sessionId) throws NGException
	{
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<WFIsSessionValid_Input>").append("\n")
		.append("<Option>WFIsSessionValid</Option>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
		.append("</WFIsSessionValid_Input>");
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "IsSessionValid OutputXML ===>" + outputXML);
		return outputXML;   
	}

	public static String AddDocument(String sessionId, String parentFolderIndex, String documentName, String createdByAppName, String comment, 
			int volumeIndex, int dataDef, String accessType, String ISIndex, int noOfPages, String docType, String docSize, String FTSFlag) throws NGException
	{
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<NGOAddDocument_Input>").append("\n")
		.append("<Option>WFIsSessionValid</Option>").append("\n")
		.append("<UserDBId>" + sessionId + "</UserDBId>").append("\n")
		.append("<Document>").append("\n")
		.append("<ParentFolderIndex>" + parentFolderIndex + "</ParentFolderIndex>").append("\n")
		.append("<DocumentName>" + documentName + "</DocumentName>").append("\n")
		.append("<CreatedByAppName>" + createdByAppName + "</CreatedByAppName>").append("\n")
		.append("<Comment>" + comment + "</Comment>").append("\n")
		.append("<VolumeIndex>" + volumeIndex + "</VolumeIndex>").append("\n")
		.append("<ProcessDefId>" + processDefId + "</ProcessDefId>").append("\n")
		.append("<DataDefinition>"+ dataDef +"</DataDefinition>").append("\n")
		.append("<AccessType>" + accessType + "</AccessType>").append("\n")
		.append("<NoOfPages>" + noOfPages + "</NoOfPages>").append("\n")
		.append("<ISIndex>" + ISIndex + "</ISIndex>").append("\n")
		.append("<DocumentType>" + docType + "</DocumentType>").append("\n")
		.append("<DocumentSize>" + docSize + "</DocumentSize>").append("\n")
		.append("<FTSFlag>" + FTSFlag + "</FTSFlag>").append("\n")
		.append("</Document>").append("\n")
		.append("</NGOAddDocument_Input>");
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		//DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "IsSessionValid OutputXML ===>" + outputXML);
		return outputXML;   
	}

	public static String createValidJsonForAccRelation(String jsonString){
		try{
			jsonString = jsonString.replaceAll("customerFullName","name");
			jsonString = jsonString.replaceAll("eidaNumber","eida_no");
			jsonString = jsonString.replaceAll("customerMobileNo","mobile");
			jsonString = jsonString.replaceAll("customerEmail","EMAIL");
			jsonString = jsonString.replaceAll("accountRelationship","acc_relation");
			jsonString = jsonString.replaceAll("customerId","cid");
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "createValidJsonForAccRelation jsonString "+jsonString);

		} catch(Exception e){
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}
		return jsonString;
	}

	private static String insertComplexData(String json, String attributeName){
		StringBuilder complexXml = new StringBuilder("");
		try {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[BusinessChoice]Product JSON: "+json);
			JsonElement jelement = new JsonParser().parse(json);
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[BusinessChoice]JSON jelement: "+jelement);
			JsonArray jArray = jelement.getAsJsonArray();
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[BusinessChoice]JSON jArray: "+jArray);
			String key = "";
			String val = "";

			for (JsonElement jsonElement : jArray) {
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[BusinessChoice]JSON jsonElement: "+jsonElement);
				JsonObject jObject = jsonElement.getAsJsonObject();
				Gson gson = new Gson();
				String ss = new Gson().toJson(jObject);
				HashMap<String,Object> mm = gson.fromJson(ss, HashMap.class);
				Set<Map.Entry<String, Object>> entrySet = mm.entrySet();
				Iterator<Entry<String,Object>> entrySetIterator = entrySet.iterator();
				complexXml.append("<"+attributeName+">");
				while(entrySetIterator.hasNext()){
					Entry<String,Object> entry = entrySetIterator.next();
					val = entry.getValue().toString();
					key = entry.getKey();
					//MULTI CC By Rishabh on 26012024 
					if((attributeName.equalsIgnoreCase("Q_SUPPLEMENTARY_CARD") || attributeName.equalsIgnoreCase("Q_SUPPLEMENTARY_CARD_MULTI"))
							&& key.equalsIgnoreCase("dob")){
						val = DSCOPUtils.getInstance().formatToBPMDate(val);
					}
					complexXml.append("<"+key+">").append(val).append("</"+key+">");					
				}
				complexXml.append("</"+attributeName+">");
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[BusinessChoice]JSON complexXml: "+complexXml);
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}
		return complexXml.toString();
	}

}
