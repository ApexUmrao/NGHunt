package com.newgen.ao.laps.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.newgen.ao.laps.cache.ChannelCallCache;
import com.newgen.ao.laps.cache.ProtradeComplexMapping;
import com.newgen.ao.laps.cache.SwiftComplexMapping;
import com.newgen.ao.laps.cache.TSLMComplexMapping;
import com.newgen.ao.laps.cache.TSLMStagingCache;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.omni.wf.util.excp.NGException;
import com.newgen.omni.wf.util.xml.XMLParser;

public class ProdCreateXML {
	static private String cabinetName;
	static private String EdmsCabName;
	static private HashMap<String, ProtradeComplexMapping> protradeComplexMap;
	static private HashMap<String, TSLMComplexMapping> TSLMComplexMap;
	static private HashMap<String, SwiftComplexMapping> swiftComplexMap;
	//	static private int processDefId;

	public static void init(String cabName)
	{
		cabinetName = cabName;
		//		processDefId = processdefId;
	}
	public static void initEdms(String EdmsName)
	{
		EdmsCabName = EdmsName;
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
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		//LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "WMConnect OutputXML ===>" + outputXML);
		return outputXML;    
	}

	public static String WFUploadWorkItem(String sessionId, String initiateAlso, HashMap<String, String> attribMap,
			String documents, int processDefId, String initiatorActivityName) throws NGException
	{
		StringBuilder attribXML = new StringBuilder("");
		StringBuilder key = new StringBuilder("");
		StringBuilder val = new StringBuilder("");
		HashMap<String, String> MasterMap=LapsUtils.getMasterData("EXT_AO");
		//LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "MasterMap is" + MasterMap.toString());
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "documents: " + documents);
		Set<Map.Entry<String, String>> entrySet = attribMap.entrySet();
		Iterator<Entry<String,String>> entrySetIterator = entrySet.iterator();
		while(entrySetIterator.hasNext()){
			Entry<String, String> entry = entrySetIterator.next();
			//if(MasterMap.containsKey(entry.getKey())){
			//key = key.append(MasterMap.get(entry.getKey()));	
			key = key.append(entry.getKey());	
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "entry.getKey().toString(): " + entry.getKey().toString());
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "entry.getValue().toString(): " 
					+ entry.getValue().toString());
			if(key.toString().contains("_DATETIME") || key.toString().contains("_DateTime") || key.toString().equalsIgnoreCase("EXPIRY_DATE"))
			{				
				val = val.append(LapsUtils.getInstance().formatToBPMDateTime(entry.getValue().toString()));
			} 
			else if(!key.toString().contains("_DATE_TIME") && key.toString().contains("_DATE"))
				//					&& !key.toString().equalsIgnoreCase("INF_BASE_DOC_DATE")
				//					&& !key.toString().equalsIgnoreCase("INF_MATURITY_DATE")
				//					&& !key.toString().equalsIgnoreCase("LC_LTST_SHIPMNT_DATE"))
			{
				val = val.append(LapsUtils.getInstance().formatToBPMDate(entry.getValue().toString()));
			} 
			else 
			{
				val = val.append(entry.getValue());
			}			
			attribXML.append(entry.getKey() + (char)21 + val.toString() + (char)25);//key changed by sanal
			key.setLength(0);
			val.setLength(0);
			//}
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "attribXML attribXML ===>" + attribXML);


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
		.append("<Documents>"+documents+"</Documents>").append("\n")
		.append("<InitiateFromActivityId>1</InitiateFromActivityId>").append("\n")
		//		.append("<InitiateFromActivityId>233</InitiateFromActivityId>").append("\n")
		//.append("<InitiateFromActivityName>Work Introduction1</InitiateFromActivityName>").append("\n")
		//	.append("<InitiateFromActivityName>Initiator</InitiateFromActivityName>").append("\n")
		.append("<InitiateFromActivityName>"+initiatorActivityName+"</InitiateFromActivityName>").append("\n")
		.append("<Attributes>"+ attribXML + "</Attributes>").append("\n")
		.append("</WFUploadWorkItem_Input>");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "WFUploadWorkItem sInputXML ===>" + sInputXML);

		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "WFUploadWorkItem OutputXML ===>" + outputXML);
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
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "WMGetWorkItem OutputXML ===>" + outputXML);
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
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "WMCompleteWorkItem OutputXML ===>" + outputXML);
		return outputXML;   
	}

	public static String WFSetAttributes(String sessionId,String processInstanceid, int WorkitemId, 
			HashMap<String, String> attribMap, HashMap<String, LinkedHashMap<Integer, 
			HashMap<String, String>>> complexMap, int processDefId) throws NGException
	{	
		protradeComplexMap = ChannelCallCache.getInstance().getProtradeComplexMap();
		//		ProtradeComplexMapping cmplxMaster;
		StringBuilder attribXML = new StringBuilder("");
		StringBuilder key = new StringBuilder("");
		StringBuilder val = new StringBuilder("");
		String cmplxVarName = "";
		Set<Map.Entry<String, String>> entrySet ;
		Set<Map.Entry<Integer, HashMap<String, String>>> entrySetComplex ;
		Set<Map.Entry<String, LinkedHashMap<Integer, HashMap<String, String>>>> entrySetComplexOuter;
		Iterator<Entry<String,String>> entrySetIterator ;
		Iterator<Entry<Integer,HashMap<String, String>>> entrySetIteratorComplex ;
		if(attribMap!=null && !attribMap.isEmpty()){
			entrySet = attribMap.entrySet();
			entrySetIterator = entrySet.iterator();
			while(entrySetIterator.hasNext()){
				Entry<String, String> entry = entrySetIterator.next();
				key = key.append(entry.getKey());
				if(key.toString().contains("_DATETIME") || key.toString().contains("_DateTime") || key.equals("EXPIRY_DATE"))
				{
					val = val.append(LapsUtils.getInstance().formatToBPMDateTime(entry.getValue().toString()));

				} 
				else if(!key.toString().contains("_DATE_TIME") && key.toString().contains("_DATE"))
				{
					val = val.append(LapsUtils.getInstance().formatToBPMDate(entry.getValue().toString()));

				} 
				else if(key.toString().contains("DATE_OF"))
				{
					val = val.append(LapsUtils.getInstance().formatToBPMDate(entry.getValue().toString()));
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
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "in assign 3: " +attribXML);
				attribXML.append("<").append(key.toString()).append(">").append(val.toString()).append("</").append(key.toString()).append(">");
				key.setLength(0);
				val.setLength(0);
			}
		}

		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "in assign 2: " +attribXML);
		if(complexMap != null && !complexMap.isEmpty()){
			entrySetComplexOuter = complexMap.entrySet();
			for(Map.Entry<String, LinkedHashMap<Integer, HashMap<String, String>>> entryComplexOuter: 
				entrySetComplexOuter){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "entryComplexOuter.getKey() cmplx: " 
						+ entryComplexOuter.getKey());
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "entryComplexOuter.getValue(): " 
//						+ entryComplexOuter.getValue().toString());
				ProtradeComplexMapping protradeComplexMapping = protradeComplexMap.get(entryComplexOuter.getKey());
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "protradeComplexMapping: " 
						+ protradeComplexMapping.toString());
				String mappingAttr = "";
				String mappingAttrValue = "";
				//ProtradeComplexMapping protradeComplexMapping = cmplxMaster.get(0);
				cmplxVarName = protradeComplexMapping.getComplexVarName();
				if(protradeComplexMapping.getDeletePreviousEntry().equals("Y")){
					try {
						mappingAttr = protradeComplexMapping.getMappingAttribute();
						String outputXmlcount = APCallCreateXML.APSelect("SELECT count(1) as ROWCOUNT"
								+ " FROM " + protradeComplexMapping.getComplexTableName() + " WHERE "+ mappingAttr
								+" = '"
								+ processInstanceid + "'");
						XMLParser xpCount = new XMLParser(outputXmlcount);
						int mainCodeCount = Integer.parseInt(xpCount.getValueOf("MainCode"));
						if(mainCodeCount == 0 && Integer.parseInt(xpCount.getValueOf("ROWCOUNT"))>0){
							APCallCreateXML.APDelete(protradeComplexMapping.getComplexTableName(), 
									mappingAttr+"='"+processInstanceid+"'", sessionId);
						}
					} catch (Exception e) {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in delete previous: ");
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
					}
				}
				//				String outputXml = APCallCreateXML.APSelect("SELECT COMPLEX_VAR_NAME, DELETE_PREV_ENTRY, COMPLEX_TABLE_NAME"
				//						+ " FROM TFO_DJ_PROTRADE_COMPLEX_MASTER WHERE STRUCTURE_TYPE = '"
				//						+ entryComplexOuter.getKey() + "'");
				//				XMLParser xp = new XMLParser(outputXml);
				//				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				//				if(mainCode == 0){
				//					cmplxVarName = xp.getValueOf("COMPLEX_VAR_NAME");
				//					if(xp.getValueOf("DELETE_PREV_ENTRY").equals("Y")){
				//						try {
				//							String outputXmlcount = APCallCreateXML.APSelect("SELECT count(1) as ROWCOUNT"
				//									+ " FROM " + xp.getValueOf("COMPLEX_TABLE_NAME") + " WHERE WI_NAME = '"
				//									+ processInstanceid + "'");
				//							XMLParser xpCount = new XMLParser(outputXmlcount);
				//							int mainCodeCount = Integer.parseInt(xpCount.getValueOf("MainCode"));
				//							if(mainCodeCount == 0 && Integer.parseInt(xpCount.getValueOf("ROWCOUNT"))>0){
				//								APCallCreateXML.APDelete(xp.getValueOf("COMPLEX_TABLE_NAME"), 
				//										"WI_NAME='"+processInstanceid+"'", sessionId);
				//							}
				//						} catch (Exception e) {
				//							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in delete previous: ");
				//							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
				//						}
				//					}
				//				}
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "cmplx variable name: " + cmplxVarName);
				entrySetComplex = complexMap.get(entryComplexOuter.getKey()).entrySet();
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "complexMap in assign: " 
//						+ complexMap.toString());
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "entrySetComplex size: " 
						+ entrySetComplex.size());
				for(Map.Entry<Integer, HashMap<String, String>> entry: entrySetComplex){
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "entry.getKey() cmplx: " 
							+ entry.getKey());
//					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "innerComplex.get(entry.getKey()): " 
//							+ entry.getValue().toString());
					attribXML.append("<"+cmplxVarName+">");
					for(Map.Entry<String, String> entryInner: entry.getValue().entrySet()){
//						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "entry.getValue().entrySet(): " 
//								+ entry.getValue().entrySet());
						key = key.append(entryInner.getKey());
						if(key.toString().contains("_DATETIME") || key.toString().contains("_DateTime") 
								|| key.equals("EXPIRY_DATE"))
						{
							val = val.append(LapsUtils.getInstance().formatToBPMDateTime(entryInner.getValue()));

						} 
						else if(key.toString().contains("_DATE"))
						{
							val = val.append(LapsUtils.getInstance().formatToBPMDate(entryInner.getValue()));

						} 
						else if(key.toString().contains("DATE_OF"))
						{
							val = val.append(LapsUtils.getInstance().formatToBPMDate(entryInner.getValue()));

						} 
						else 
						{
							if(entryInner.getValue().toString().contains("&")){
								val = val.append(entryInner.getValue().toString().replace("&", "&amp;"));
							}
							else {
								val = val.append(entryInner.getValue());
							}
						}
						attribXML.append("<").append(key.toString()).append(">").append(val.toString()).append("</").append(key.toString()).append(">");
						key.setLength(0);
						val.setLength(0);
					}
					attribXML.append("</"+cmplxVarName+">");
//					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"assign attribXML at "+entry.getKey()+": "
//							+attribXML.toString());
				}
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"assign attribXML complx final: "
//						+attribXML.toString());
			}
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"assign attribXML complx final: "
				+attribXML.toString());
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
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "WFSetAttributes OutputXML ===>" + outputXML);
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
		//LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "IsSessionValid OutputXML ===>" + outputXML);
		return outputXML;   
	}
	//Added by reyaz 20-06-2023
	public static String IsEdmsSessionValid(String sessionId,String jtsIP,String jtsPort) throws NumberFormatException, IOException, Exception
	{
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<WFIsSessionValid_Input>").append("\n")
		.append("<Option>WFIsSessionValid</Option>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("<EngineName>" + EdmsCabName + "</EngineName>").append("\n")
		.append("</WFIsSessionValid_Input>");
		String outputXML =  ExecuteXML.executeXMLEdms(sInputXML.toString(),jtsIP,jtsPort);
		//LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "IsSessionValid OutputXML ===>" + outputXML);
		return outputXML;   
	}

	/*public static String AddDocument(String sessionId, String parentFolderIndex, String documentName, String createdByAppName, String comment, 
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
		//LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "IsSessionValid OutputXML ===>" + outputXML);
		return outputXML;   
	}*/
	public static String DeleteDocument(String sessionId, String parentFolderIndex, List<String> documentIndex) throws NGException{
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<NGODeleteDocumentExt_Input>").append("\n")
		.append("<Option>NGODeleteDocumentExt</Option>").append("\n")
		.append("<CabinetName>"+cabinetName+"</CabinetName>").append("\n")
		.append("<UserDBId>"+sessionId+"</UserDBId>").append("\n")
		.append("<Documents>").append("\n");
		for(String docIndex : documentIndex) 
		{
			sInputXML.append("<Document>").append("\n")
			.append("<DocumentIndex>"+docIndex+"</DocumentIndex>").append("\n")
			.append("<ParentFolderIndex>"+parentFolderIndex+"</ParentFolderIndex>").append("\n")
			.append("<ReferenceFlag>N</ReferenceFlag>").append("\n")
			.append("</Document>").append("\n");
		}
		sInputXML.append("</Documents>").append("\n")
		.append("</NGODeleteDocumentExt_Input>");
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "DeleteDocument OutputXML ===>" + outputXML);
		return outputXML;	
	}
	
	public static String DeleteDocumentNew(String sessionId, String parentFolderIndex, String documentIndex) throws NGException{
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<NGODeleteDocumentExt_Input>").append("\n")
		.append("<Option>NGODeleteDocumentExt</Option>").append("\n")
		.append("<CabinetName>"+cabinetName+"</CabinetName>").append("\n")
		.append("<UserDBId>"+sessionId+"</UserDBId>").append("\n")
		.append("<Document>").append("\n")
		.append("<DocumentIndex>"+documentIndex+"</DocumentIndex>").append("\n")
		.append("<ParentFolderIndex>"+parentFolderIndex+"</ParentFolderIndex>").append("\n")
		.append("<ReferenceFlag>N</ReferenceFlag>").append("\n")
		.append("</Document>").append("\n")
		.append("</NGODeleteDocumentExt_Input>");
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "DeleteDocument OutputXML ===>" + outputXML);
		return outputXML;	
	}
	
	private static StringBuilder append(String string) {
		// TODO Auto-generated method stub
		return null;
	}
	public static String AddDocument(String sessionId, String parentFolderIndex, String documentName, 
			String createdByAppName, String comment,  int volumeIndex,  String ISIndex, int noOfPages, 
			String docType, String docSize) throws NGException
	{
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<NGOAddDocument_Input>").append("\n")
		.append("<Option>NGOAddDocument</Option>").append("\n")
		.append("<CabinetName>"+cabinetName+"</CabinetName>").append("\n")
		.append("<UserDBId>" + sessionId + "</UserDBId>").append("\n")
		.append("<GroupIndex>0</GroupIndex>").append("\n")
		.append("<Document>").append("\n")
		.append("<VersionFlag>Y</VersionFlag>").append("\n")
		.append("<ParentFolderIndex>" + parentFolderIndex + "</ParentFolderIndex>").append("\n")
		.append("<DocumentName>" + documentName + "</DocumentName>").append("\n")
		.append("<CreatedByAppName>" + createdByAppName + "</CreatedByAppName>").append("\n")
		.append("<Comment>" + comment + "</Comment>").append("\n")
		.append("<VolumeIndex>" + volumeIndex + "</VolumeIndex>").append("\n")
		.append("<NoOfPages>" + noOfPages + "</NoOfPages>").append("\n")
		.append("<ISIndex>" + ISIndex + "</ISIndex>").append("\n")
		.append("<DocumentType>" + docType + "</DocumentType>").append("\n")
		.append("<DocumentSize>" + docSize + "</DocumentSize>").append("\n")
		.append("</Document>").append("\n")
		.append("</NGOAddDocument_Input>");
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		//LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "IsSessionValid OutputXML ===>" + outputXML);
		return outputXML;   
	}

	public static String AddDocumentNew(String sessionId, String parentFolderIndex, String documentName, 
			String createdByAppName, String comment,  int volumeIndex,  String ISIndex, int noOfPages, 
			String docType, String docSize) throws NGException
	{
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<NGOAddDocument_Input>").append("\n")
		.append("<Option>NGOAddDocument</Option>").append("\n")
		.append("<CabinetName>"+cabinetName+"</CabinetName>").append("\n")
		.append("<UserDBId>" + sessionId + "</UserDBId>").append("\n")
		.append("<GroupIndex>0</GroupIndex>").append("\n")
		.append("<Document>").append("\n")
		.append("<VersionFlag>N</VersionFlag>").append("\n")
		.append("<ParentFolderIndex>" + parentFolderIndex + "</ParentFolderIndex>").append("\n")
		.append("<DocumentName>" + documentName + "</DocumentName>").append("\n")
		.append("<CreatedByAppName>" + createdByAppName + "</CreatedByAppName>").append("\n")
		.append("<Comment>" + comment + "</Comment>").append("\n")
		.append("<VolumeIndex>" + volumeIndex + "</VolumeIndex>").append("\n")
		.append("<DataDefinition>" + "" + "</DataDefinition>").append("\n")		
		.append("<AccessType>S</AccessType>").append("\n")	
		.append("<NoOfPages>" + noOfPages + "</NoOfPages>").append("\n")
		.append("<ISIndex>" + ISIndex + "</ISIndex>").append("\n")
		.append("<DocumentType>" + docType + "</DocumentType>").append("\n")
		.append("<DocumentSize>" + docSize + "</DocumentSize>").append("\n")
		.append("<FTSFlag>PP</FTSFlag>").append("\n")
		.append("</Document>").append("\n")
		.append("</NGOAddDocument_Input>");
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		//LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "IsSessionValid OutputXML ===>" + outputXML);
		return outputXML;   
	}
	
	public static String WFAdhocWorkItem(String sessionId, String processInstanceid, int WorkitemId,
			String activityName, String activityId) throws NGException
	{
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<WFAdhocWorkItem_Input>").append("\n")
		.append("<Option>WFAdhocWorkItem</Option>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
		.append("<RightFlag>010100</RightFlag>").append("\n")
		.append("<ProcessInstanceId>"+ processInstanceid +"</ProcessInstanceId>").append("\n")
		.append("<WorkitemId>"+ WorkitemId +"</WorkitemId>").append("\n")
		.append("<ActivityID>"+ activityId +"</ActivityID>").append("\n")
		.append("<ActivityName>"+ activityName +"</ActivityName>").append("\n")
		.append("<ActivityType>3</ActivityType>").append("\n")
		.append("<PrevActivityType>10</PrevActivityType>").append("\n")
		.append("<Comments>ok</Comments>").append("\n")
		.append("<OpenMode>PM</OpenMode>").append("\n")
		.append("</WFAdhocWorkItem_Input>");
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		return outputXML;   
	}

	public static String APUpdateFromMap(String sessionId,String  tableName,HashMap<String, String> attribMap,String sWhere) throws NGException
	{
		String outputXML = "";
		try {
			if(attribMap != null) {
				StringBuilder columnList = new StringBuilder(); 
				StringBuilder valList = new StringBuilder(); 
				for(Map.Entry<String, String> entryInner: attribMap.entrySet()){
					columnList.append(entryInner.getKey());
					columnList.append(",");
					valList.append("'");
					valList.append(entryInner.getValue().replaceAll("'", "''"));
					valList.append("'");
					valList.append(",");
				}
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"columnList : "+columnList.toString());
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"value list : "+valList.toString());
				if(columnList != null && valList != null) {
					outputXML = APCallCreateXML.APUpdate(tableName, columnList.toString().substring(0, columnList.toString().length() -1), valList.toString().substring(0, valList.toString().length()-1), sWhere, sessionId);
				}
			}
			return outputXML;
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"Exception in APUpdateFromMap "+ e);
		}
		return outputXML;   
	}
	
	public static String WFSwiftSetAttributes(String sessionId,String processInstanceid, int WorkitemId, 
			HashMap<String, String> attribMap, HashMap<String, LinkedHashMap<Integer, 
			HashMap<String, String>>> complexMap, int processDefId) throws NGException
	{	
		swiftComplexMap = ChannelCallCache.getInstance().getSwiftComplexHashMap();
		StringBuilder attribXML = new StringBuilder("");
		StringBuilder key = new StringBuilder("");
		StringBuilder val = new StringBuilder("");
		String cmplxVarName = "";
		Set<Map.Entry<String, String>> entrySet ;
		Set<Map.Entry<Integer, HashMap<String, String>>> entrySetComplex ;
		Set<Map.Entry<String, LinkedHashMap<Integer, HashMap<String, String>>>> entrySetComplexOuter;
		Iterator<Entry<String,String>> entrySetIterator ;
		Iterator<Entry<Integer,HashMap<String, String>>> entrySetIteratorComplex ;
		if(attribMap!=null && !attribMap.isEmpty()){
			entrySet = attribMap.entrySet();
			entrySetIterator = entrySet.iterator();
			while(entrySetIterator.hasNext()){
				Entry<String, String> entry = entrySetIterator.next();
				key = key.append(entry.getKey());
				if(key.toString().contains("_DATETIME") || key.toString().contains("_DateTime") || key.equals("EXPIRY_DATE"))
				{
					val = val.append(LapsUtils.getInstance().formatToBPMDateTime(entry.getValue().toString()));

				} 
				else if(!key.toString().contains("_DATE_TIME") && key.toString().contains("_DATE"))
				{
					val = val.append(LapsUtils.getInstance().formatToBPMDate(entry.getValue().toString()));

				} 
				else if(key.toString().contains("DATE_OF"))
				{
					val = val.append(LapsUtils.getInstance().formatToBPMDate(entry.getValue().toString()));
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
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "in assign 3: " +attribXML);
				attribXML.append("<").append(key.toString()).append(">").append(val.toString()).append("</").append(key.toString()).append(">");
				key.setLength(0);
				val.setLength(0);
			}
		}

		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "in assign 2: " +attribXML);
		if(complexMap != null && !complexMap.isEmpty()){
			entrySetComplexOuter = complexMap.entrySet();
			for(Map.Entry<String, LinkedHashMap<Integer, HashMap<String, String>>> entryComplexOuter: 
				entrySetComplexOuter){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "entryComplexOuter.getKey() cmplx: " 
						+ entryComplexOuter.getKey());
				SwiftComplexMapping swiftComplexMapping = swiftComplexMap.get(entryComplexOuter.getKey());
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "protradeComplexMapping: " 
						+ swiftComplexMapping.toString());
				String mappingAttr = "";
				String mappingAttrValue = "";
				//ProtradeComplexMapping protradeComplexMapping = cmplxMaster.get(0);
				cmplxVarName = swiftComplexMapping.getComplexVarName();
				if(swiftComplexMapping.getDeletePreviousEntry().equals("Y")){
					try {
						mappingAttr = swiftComplexMapping.getMappingAttribute();
						String outputXmlcount = APCallCreateXML.APSelect("SELECT count(1) as ROWCOUNT"
								+ " FROM " + swiftComplexMapping.getComplexTableName() + " WHERE "+ mappingAttr
								+" = '"
								+ processInstanceid + "'");
						XMLParser xpCount = new XMLParser(outputXmlcount);
						int mainCodeCount = Integer.parseInt(xpCount.getValueOf("MainCode"));
						if(mainCodeCount == 0 && Integer.parseInt(xpCount.getValueOf("ROWCOUNT"))>0){
							APCallCreateXML.APDelete(swiftComplexMapping.getComplexTableName(), 
									mappingAttr+"='"+processInstanceid+"'", sessionId);
						}
					} catch (Exception e) {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in delete previous: ");
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
					}
				}
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "cmplx variable name: " + cmplxVarName);
				entrySetComplex = complexMap.get(entryComplexOuter.getKey()).entrySet();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "entrySetComplex size: " 
						+ entrySetComplex.size());
				for(Map.Entry<Integer, HashMap<String, String>> entry: entrySetComplex){
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "entry.getKey() cmplx: " 
							+ entry.getKey());
					attribXML.append("<"+cmplxVarName+">");
					for(Map.Entry<String, String> entryInner: entry.getValue().entrySet()){
						key = key.append(entryInner.getKey());
						if(key.toString().contains("_DATETIME") || key.toString().contains("_DateTime") 
								|| key.equals("EXPIRY_DATE"))
						{
							val = val.append(LapsUtils.getInstance().formatToBPMDateTime(entryInner.getValue()));

						} 
						else if(key.toString().contains("_DATE"))
						{
							val = val.append(LapsUtils.getInstance().formatToBPMDate(entryInner.getValue()));

						} 
						else if(key.toString().contains("DATE_OF"))
						{
							val = val.append(LapsUtils.getInstance().formatToBPMDate(entryInner.getValue()));

						} 
						else 
						{
							if(entryInner.getValue().toString().contains("&")){
								val = val.append(entryInner.getValue().toString().replace("&", "&amp;"));
							}
							else {
								val = val.append(entryInner.getValue());
							}
						}
						attribXML.append("<").append(key.toString()).append(">").append(val.toString()).append("</").append(key.toString()).append(">");
						key.setLength(0);
						val.setLength(0);
					}
					attribXML.append("</"+cmplxVarName+">");
//					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"assign attribXML at "+entry.getKey()+": "
//							+attribXML.toString());
				}
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"assign attribXML complx final: "
//						+attribXML.toString());
			}
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"assign attribXML complx final: "
				+attribXML.toString());
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
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "WFSetAttributes OutputXML ===>" + outputXML);
		return outputXML;  
	}
	
	private static String insertComplexData(String json, String attributeName){
		StringBuilder complexXml = new StringBuilder("");
		try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "[BusinessChoice]Product JSON: "+json);
			JsonElement jelement = new JsonParser().parse(json);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "[BusinessChoice]JSON jelement: "+jelement);
			JsonArray jArray = jelement.getAsJsonArray();
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "[BusinessChoice]JSON jArray: "+jArray);
			
			for (JsonElement jsonElement : jArray) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "[BusinessChoice]JSON jsonElement: "+jsonElement);
				JsonObject jObject = jsonElement.getAsJsonObject();
				Gson gson = new Gson();
				String ss = new Gson().toJson(jObject);
				HashMap<String,Object> mm = gson.fromJson(ss, HashMap.class);
				Set<Map.Entry<String, Object>> entrySet = mm.entrySet();
				Iterator<Entry<String,Object>> entrySetIterator = entrySet.iterator();
				complexXml.append("<"+attributeName+">");
				while(entrySetIterator.hasNext()){
					Entry<String,Object> entry = entrySetIterator.next();
					complexXml.append("<"+entry.getKey()+">").append(entry.getValue()).append("</"+entry.getKey()+">");					
				}
				complexXml.append("</"+attributeName+">");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "[BusinessChoice]JSON complexXml: "+complexXml);
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		return complexXml.toString();
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
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "WFSetAttributes While Loop: ");
			Entry<String, String> entry = entrySetIterator.next();
			key = key.append(entry.getKey());
			if(entry.getValue().toString().contains("&")){
				val = val.append(entry.getValue().toString().replace("&", "&amp;"));
			}
			else {
				val = val.append(entry.getValue());
			}
			attribXML.append("<").append(key.toString()).append(">").append(val.toString()).append("</").append(key.toString()).append(">");
			key.setLength(0);
			val.setLength(0);
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "WFSetAttributes While Loop EXIT ");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "WFSetAttributes complexMap: "+complexMap);
		if(complexMap != null){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "WFSetAttributes complexMap Not NULL");
			entrySet = complexMap.entrySet();
			entrySetIterator = entrySet.iterator();
			while(entrySetIterator.hasNext()){
				Entry<String, String> entry = entrySetIterator.next();
				attribXML.append(insertComplexData(entry.getValue(),entry.getKey()));			
			}
		}
		
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "WFSetAttributes attribXML ===>" + attribXML);
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
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "WFSetAttributes OutputXML ===>" + outputXML);
		return outputXML;  
	}
	
	public static String WMReassignWorkItem(String sessionId,
			String processInstanceid, String WorkitemId, String targetUser, String comments) throws NGException
	{
		StringBuilder sInputXML = new StringBuilder();
		sInputXML.append("<?xml version=\"1.0\"?>");
		sInputXML.append("<WMReassignWorkItem_Input>");
		sInputXML.append("<Option>WMReassignWorkItem</Option>");
		sInputXML.append("<EngineName>" + cabinetName + "</EngineName>");
		sInputXML.append("<SessionId>" + sessionId + "</SessionId>");
		sInputXML.append("<ProcessInstanceId>" + processInstanceid+ "</ProcessInstanceId>");
		sInputXML.append("<WorkItemId>" + WorkitemId + "</WorkItemId>");
		sInputXML.append("<SourceUser></SourceUser>");
		sInputXML.append("<TargetUser>" + targetUser + "</TargetUser>");
		sInputXML.append("<Comments>" + comments + "</Comments>");
		sInputXML.append("<OpenMode>PM</OpenMode>");
		sInputXML.append("</WMReassignWorkItem_Input>");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "WFSetAttributes InputXML ===>" + sInputXML);
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "WFSetAttributes OutputXML ===>" + outputXML);
		return outputXML;
	}
	
	public static String WFSetAttributes_TSLM(String sessionId,String processInstanceid, int WorkitemId, 
			HashMap<String, String> attribMap, HashMap<String, LinkedHashMap<Integer, 
			HashMap<String, String>>> complexMap, int processDefId,String sourcingChannel) throws NGException
	{	
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside WFSetAttributes_TSLM ");
		TSLMComplexMap = TSLMStagingCache.getInstance().getTSLMComplexMap(Integer.toString(processDefId),sourcingChannel);
		
		StringBuilder attribXML = new StringBuilder("");
		StringBuilder key = new StringBuilder("");
		StringBuilder val = new StringBuilder("");
		String cmplxVarName = "";
		Set<Map.Entry<String, String>> entrySet ;
		Set<Map.Entry<Integer, HashMap<String, String>>> entrySetComplex ;
		Set<Map.Entry<String, LinkedHashMap<Integer, HashMap<String, String>>>> entrySetComplexOuter;
		Iterator<Entry<String,String>> entrySetIterator ;
		Iterator<Entry<Integer,HashMap<String, String>>> entrySetIteratorComplex ;
		if(attribMap!=null && !attribMap.isEmpty()){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside WFSetAttributes_TSLM attribMap");
			entrySet = attribMap.entrySet();
			entrySetIterator = entrySet.iterator();
			while(entrySetIterator.hasNext()){
				Entry<String, String> entry = entrySetIterator.next();
				key = key.append(entry.getKey());
				if(key.toString().contains("_DATETIME") || key.toString().contains("_DateTime") || key.equals("EXPIRY_DATE")&&!entry.getValue().toString().trim().isEmpty())
				{
					val = val.append(LapsUtils.getInstance().formatToBPMDateTime(entry.getValue().toString()));

				} 
				else if(!key.toString().contains("_DATE_TIME") && key.toString().contains("_DATE")&&!entry.getValue().toString().trim().isEmpty())
				{
					val = val.append(LapsUtils.getInstance().formatToBPMDate(entry.getValue().toString()));

				} 
				else if(key.toString().contains("DATE_OF")&&!entry.getValue().toString().trim().isEmpty())
				{
					val = val.append(LapsUtils.getInstance().formatToBPMDate(entry.getValue().toString()));
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
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "in assign 3: " +attribXML);
				attribXML.append("<").append(key.toString()).append(">").append(val.toString()).append("</").append(key.toString()).append(">");
				key.setLength(0);
				val.setLength(0);
			}
		}

		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "in assign 2: " +attribXML);
		if(complexMap != null && !complexMap.isEmpty()){
			entrySetComplexOuter = complexMap.entrySet();
			for(Map.Entry<String, LinkedHashMap<Integer, HashMap<String, String>>> entryComplexOuter: 
				entrySetComplexOuter){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "entryComplexOuter.getKey() cmplx: " 
						+ entryComplexOuter.getKey());
				TSLMComplexMapping TSLMComplexMapping = TSLMComplexMap.get(entryComplexOuter.getKey());
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "TSLMComplexMapping: " 
						+ TSLMComplexMapping.toString());
				String mappingAttr = "";
				String mappingAttrValue = "";
				//TSLMComplexMapping TSLMComplexMapping = cmplxMaster.get(0);
				cmplxVarName = TSLMComplexMapping.getComplexVarName();
				if(TSLMComplexMapping.getDeletePreviousEntry().equals("Y")){
					try {
						mappingAttr = TSLMComplexMapping.getMappingAttribute();
						String outputXmlcount = APCallCreateXML.APSelect("SELECT count(1) as ROWCOUNT"
								+ " FROM " + TSLMComplexMapping.getComplexTableName() + " WHERE "+ mappingAttr
								+" = '"
								+ processInstanceid + "'");
						XMLParser xpCount = new XMLParser(outputXmlcount);
						int mainCodeCount = Integer.parseInt(xpCount.getValueOf("MainCode"));
						if(mainCodeCount == 0 && Integer.parseInt(xpCount.getValueOf("ROWCOUNT"))>0){
							APCallCreateXML.APDelete(TSLMComplexMapping.getComplexTableName(), 
									mappingAttr+"='"+processInstanceid+"'", sessionId);
						}
					} catch (Exception e) {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in delete previous: ");
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
					}
				}
				//				String outputXml = APCallCreateXML.APSelect("SELECT COMPLEX_VAR_NAME, DELETE_PREV_ENTRY, COMPLEX_TABLE_NAME"
				//						+ " FROM TFO_DJ_PROTRADE_COMPLEX_MASTER WHERE STRUCTURE_TYPE = '"
				//						+ entryComplexOuter.getKey() + "'");
				//				XMLParser xp = new XMLParser(outputXml);
				//				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				//				if(mainCode == 0){
				//					cmplxVarName = xp.getValueOf("COMPLEX_VAR_NAME");
				//					if(xp.getValueOf("DELETE_PREV_ENTRY").equals("Y")){
				//						try {
				//							String outputXmlcount = APCallCreateXML.APSelect("SELECT count(1) as ROWCOUNT"
				//									+ " FROM " + xp.getValueOf("COMPLEX_TABLE_NAME") + " WHERE WI_NAME = '"
				//									+ processInstanceid + "'");
				//							XMLParser xpCount = new XMLParser(outputXmlcount);
				//							int mainCodeCount = Integer.parseInt(xpCount.getValueOf("MainCode"));
				//							if(mainCodeCount == 0 && Integer.parseInt(xpCount.getValueOf("ROWCOUNT"))>0){
				//								APCallCreateXML.APDelete(xp.getValueOf("COMPLEX_TABLE_NAME"), 
				//										"WI_NAME='"+processInstanceid+"'", sessionId);
				//							}
				//						} catch (Exception e) {
				//							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in delete previous: ");
				//							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
				//						}
				//					}
				//				}
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "cmplx variable name: " + cmplxVarName);
				entrySetComplex = complexMap.get(entryComplexOuter.getKey()).entrySet();
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "complexMap in assign: " 
//						+ complexMap.toString());
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "entrySetComplex size: " 
						+ entrySetComplex.size());
				for(Map.Entry<Integer, HashMap<String, String>> entry: entrySetComplex){
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "entry.getKey() cmplx: " 
							+ entry.getKey());
//					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "innerComplex.get(entry.getKey()): " 
//							+ entry.getValue().toString());
					attribXML.append("<"+cmplxVarName+">");
					for(Map.Entry<String, String> entryInner: entry.getValue().entrySet()){
//						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "entry.getValue().entrySet(): " 
//								+ entry.getValue().entrySet());
						key = key.append(entryInner.getKey());
						if(key.toString().contains("_DATETIME") || key.toString().contains("_DateTime") 
								|| key.equals("EXPIRY_DATE"))
						{
							val = val.append(LapsUtils.getInstance().formatToBPMDateTime(entryInner.getValue()));

						} 
						else if(key.toString().contains("_DATE"))
						{
							val = val.append(LapsUtils.getInstance().formatToBPMDate(entryInner.getValue()));

						} 
						else if(key.toString().contains("DATE_OF"))
						{
							val = val.append(LapsUtils.getInstance().formatToBPMDate(entryInner.getValue()));

						} 
						else 
						{
							if(entryInner.getValue().toString().contains("&")){
								val = val.append(entryInner.getValue().toString().replace("&", "&amp;"));
							}
							else {
								val = val.append(entryInner.getValue());
							}
						}
						attribXML.append("<").append(key.toString()).append(">").append(val.toString()).append("</").append(key.toString()).append(">");
						key.setLength(0);
						val.setLength(0);
					}
					attribXML.append("</"+cmplxVarName+">");
//					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"assign attribXML at "+entry.getKey()+": "
//							+attribXML.toString());
				}
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"assign attribXML complx final: "
//						+attribXML.toString());
			}
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"assign attribXML complx final: "
				+attribXML.toString());
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
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "WFSetAttributes OutputXML ===>" + outputXML);
		return outputXML;  
	}
}

