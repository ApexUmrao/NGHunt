package com.newgen.raroc.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.wf.util.app.NGEjbClient;
import com.newgen.omni.wf.util.excp.NGException;
import com.newgen.raroc.entities.RarocAttrDetails;
import com.newgen.raroc.entities.RarocRequestAttrDetails;
import com.newgen.raroc.entities.RarocTableDetails;
@Configuration
public class APCallCreateXML {


	private static final Logger logger = LogManager.getLogger(APCallCreateXML.class);

    private String sourcingChannel = "RAROC";
    static NGEjbClient objNGEjbClient = null;

    private HashMap<String, HashMap<String, RequestAttributeDetails>> sourcingChannelAttribMap = new HashMap<>();


    public static void init(String IP, String port, String appName) {
        if (objNGEjbClient == null) {
            try {
                objNGEjbClient = NGEjbClient.getSharedInstance();
            } catch (NGException e) {
                throw new RuntimeException(e);
            }
            objNGEjbClient.initialize(IP, port, appName);
        }
    }

    public static String APDelete(String tableName, String sWhere, String sessionId, String cabinetName) throws NGException {
        StringBuilder sInputXML = new StringBuilder();
        sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
                .append("<APDelete_Input>").append("\n")
                .append("<Option>APDelete</Option>").append("\n")
                .append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
                .append("<SessionId>" + sessionId + "</SessionId>").append("\n")
                .append("<TableName>" + tableName + "</TableName>").append("\n")
                .append("<WhereClause>" + sWhere + "</WhereClause>").append("\n")
                .append("</APDelete_Input>");
        logger.info("sInputXML "+sInputXML);
        logger.info("objNGEjbClient : "+objNGEjbClient);
        String outputXML = objNGEjbClient.makeCall(String.valueOf(sInputXML));

        return outputXML;
    }

    public static String APInsert(String tableName, String columnName, String strValues, String sessionId, String cabinetName) throws NGException {
        StringBuilder sInputXML = new StringBuilder();
        sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
                .append("<APInsert_Input>").append("\n")
                .append("<Option>APInsert</Option>").append("\n")
                .append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
                .append("<SessionId>" + sessionId + "</SessionId>").append("\n")
                .append("<TableName>" + tableName + "</TableName>").append("\n")
                .append("<ColName>" + columnName + "</ColName>").append("\n")
                .append("<Values>" + strValues + "</Values>").append("\n")
                .append("</APInsert_Input>");
        logger.info("sInputXML "+sInputXML);
        String outputXML = objNGEjbClient.makeCall(String.valueOf(sInputXML));

        return outputXML;
    }


    public static String APSelect(String Query, String cabinetName) throws NGException {
        StringBuilder sInputXML = new StringBuilder();
        sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
                .append("<APSelectWithColumnNames_Input>").append("\n")
                .append("<Option>APSelectWithColumnNames</Option>").append("\n")
                .append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
                .append("<Query>" + Query + "</Query>").append("\n")
                .append("</APSelectWithColumnNames_Input>");
        logger.info("sInputXML "+sInputXML);
        String outputXML = objNGEjbClient.makeCall(String.valueOf(sInputXML));
        logger.info("outputXML "+outputXML);
        return outputXML;
    }

    public static String APUpdate(String tableName, String columnName, String strValues, String whereClause, String sessionId, String cabinetName) throws NGException {
        StringBuilder sInputXML = new StringBuilder();
        sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
                .append("<APUpdate_Input>").append("\n")
                .append("<Option>APUpdate</Option>").append("\n")
                .append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
                .append("<SessionId>" + sessionId + "</SessionId>").append("\n")
                .append("<TableName>" + tableName + "</TableName>").append("\n")
                .append("<ColName>" + columnName + "</ColName>").append("\n")
                .append("<Values>" + strValues + "</Values>").append("\n")
                .append("<WhereClause>" + whereClause + "</WhereClause>").append("\n")
                .append("</APUpdate_Input>");
        logger.info("sInputXML "+sInputXML);
        String outputXML = objNGEjbClient.makeCall(String.valueOf(sInputXML));
        logger.info("outputXML "+outputXML);
        return outputXML;
    }

    public static String WFUploadWorkItem(String sessionId, String initiateAlso, HashMap<String, String> attribMap, String processDefId, HashMap<String, String> complexMap, String cabinetName) throws NGException {

        StringBuilder attribXML = new StringBuilder("");
        StringBuilder key = new StringBuilder("");
        StringBuilder val = new StringBuilder("");
        attribMap.entrySet().removeAll(complexMap.entrySet());
        Set<Map.Entry<String, String>> entrySet = attribMap.entrySet();
        for (Entry<String, String> entry : entrySet) {
            key = key.append(entry.getKey());
            if (key.toString().contains("_DATETIME") || key.toString().contains("_DateTime") || key.toString().equalsIgnoreCase("EXPIRY_DATE")) {
                val = val.append(formatToBPMDateTime(entry.getValue().toString()));

            } else if (key.toString().contains("_DATE")) {
                val = val.append(formatToBPMDate(entry.getValue().toString()));
            } else {
                val = val.append(entry.getValue());
            }
            attribXML.append(key.toString() + (char) 21 + val.toString() + (char) 25);
            key.setLength(0);
            val.setLength(0);
        }

        StringBuilder sInputXML = new StringBuilder();
        sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
                .append("<WFUploadWorkItem_Input>").append("\n")
                .append("<Option>WFUploadWorkItem</Option>").append("\n")
                .append("<SessionId>" + sessionId + "</SessionId>").append("\n")
                .append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
                .append("<ProcessDefId>" + processDefId + "</ProcessDefId>").append("\n")
                .append("<InitiateAlso>" + initiateAlso + "</InitiateAlso>").append("\n")
                .append("<ValidationRequired></ValidationRequired>").append("\n")
                .append("<DataDefName></DataDefName>").append("\n")
                .append("<InitiateFromActivityId>1</InitiateFromActivityId>").append("\n")
                .append("<InitiateFromActivityName>Initiator</InitiateFromActivityName>").append("\n")
                .append("<Attributes>" + attribXML + "</Attributes>").append("\n")
                .append("</WFUploadWorkItem_Input>");
        logger.info("sInputXML "+sInputXML);
        logger.info("before "+objNGEjbClient);
        logger.info("after "+objNGEjbClient);
        String outputXML = objNGEjbClient.makeCall(String.valueOf(sInputXML));
        logger.info("sInputXML "+outputXML);
        return outputXML;
    }

	public static String formatToBPMDate(String dt) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = sdf.parse(dt);
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            dt = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }

    public static String formatToBPMDateTime(String dt) {
        System.out.print("BPMTODATETIME: " + dt);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = sdf.parse(dt);
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dt = sdf.format(date);
        } catch (Exception e) {
            //			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_ERROR,e);
        }
        return dt;
    }

    public String WFSetAttributesNew(String sessionId, String processInstanceid, int WorkitemId,
                                     HashMap<String, String> attribMap, String processDefId, String cabinetName) throws Exception {
        logger.info("WFSetAttributesNew Inside SpringBoot");
        logger.info("attribMap>>>>>>>"+attribMap);
        int noOfCustSearched = 0;
        HashMap<String, RequestAttributeDetails> reqAttrib = createRequestMappingMap(cabinetName);
        logger.info("requestAttributes>>>>"+reqAttrib);
        StringBuilder attribXML = new StringBuilder("");
        StringBuilder key = new StringBuilder("");
        StringBuilder val = new StringBuilder("");
        HashMap<String, JSONArray> complexMap = new HashMap<>();
        HashMap<String, String> jsonMap = new HashMap<>();
        attribMap.remove("");
        Set<Map.Entry<String, JSONArray>> entrySetComplex;
        Iterator<Entry<String, JSONArray>> entrySetIteratorComplex;
        Set<Map.Entry<String, String>> entrySet = attribMap.entrySet();
        Iterator<Entry<String, String>> entrySetIterator = entrySet.iterator();
        logger.info("attribMap indside setatributes>>>>"+attribMap);
        logger.info("entrySet indside setatributes>>>>"+entrySet);
        while (entrySetIterator.hasNext()) {
            Entry<String, String> entry = entrySetIterator.next();
            key = key.append(entry.getKey());
           logger.info("inside entry key"+key);

            if (key.toString().contains("_DATETIME") || key.toString().contains("_DateTime") || key.equals("EXPIRY_DATE")) {
                val = val.append(APCallCreateXML.formatToBPMDateTime(entry.getValue().toString()));

            } else if (key.toString().contains("_DATE")) {
                val = val.append(APCallCreateXML.formatToBPMDate(entry.getValue().toString()));

            } else if (key.toString().contains("DATE_OF")) {
                val = val.append(APCallCreateXML.formatToBPMDate(entry.getValue().toString()));

            } else {
                if (entry.getValue().toString().contains("&")) {
                    val = val.append(entry.getValue().toString().replace("&", "&amp;"));
                }else {
                	val = val.append(entry.getValue());
                }

            }


            if (reqAttrib != null && reqAttrib.containsKey(key.toString())) {
                logger.info("WFSetAttributesNew reqAttrib contains: " + key);
                RequestAttributeDetails rad = reqAttrib.get(key.toString());
                logger.info("RequestAttributeDetails reqAttrib contains: " + rad);
                if (rad.getAttributeType().equalsIgnoreCase("C")) {
                	logger.info("rad  getMappingVarName()>> " + rad.getMappingVarName());
                    if (complexMap.containsKey(rad.getMappingVarName())) {
                        JSONArray ja = complexMap.get(rad.getMappingVarName());
                        logger.info("WFSetAttributesNew reqAttrib ja0: " + ja);
                        JSONObject jo = new JSONObject();
                        jo.put(rad.getMappingColumnName(), val.toString());
                        ja.put(jo);
                        complexMap.put(rad.getMappingVarName(), ja);

                    } else {
                        JSONArray ja = new JSONArray();
                        JSONObject jo = new JSONObject();
                        jo.put(rad.getMappingColumnName(), val.toString());
                        ja.put(jo);
                        complexMap.put(rad.getMappingVarName(), ja);
                        logger.info("complexMap containsKey else ja:>> " + ja);

                    }
                } else if (rad.getAttributeType().equalsIgnoreCase("J")) {

                    if ("Y".equalsIgnoreCase(rad.getDeleteTableEntry())) {
                        APCallCreateXML.APDelete(rad.getComplexTableName(), "WI_NAME = N'" + processInstanceid + "'", sessionId, cabinetName);
                    }
                    jsonMap.put(rad.getMappingVarName(), val.toString());
                }
                //-----------------------------------------------------------------------------------------


                key.setLength(0);
                val.setLength(0);
                continue;
            }

            logger.info( " inside reqAttrib with null key>>>>" );
            logger.info( " inside reqAttrib bedore apend>>>>"+attribXML );
            attribXML.append("<").append(key.toString()).append(">").append(val.toString()).append("</").append(key.toString()).append(">");
            key.setLength(0);
            val.setLength(0);
            //attribXML.append(processInstanceid,"WI_NAME");
            //}

        }

        logger.info("WFSetAttributesNew jsonMap SpringBoot: " + jsonMap);
        logger.info("WFSetAttributesNew attribXML SpringBoot: " + attribXML);
        logger.info("WFSetAttributesNew complexMap SpringBoot: " + complexMap);
        logger.info("WFSetAttributesNew jsonMap reqAttrib:>>>> " + reqAttrib);

        if (complexMap.size() > 0) {
            entrySetComplex = complexMap.entrySet();
            entrySetIteratorComplex = entrySetComplex.iterator();
            while (entrySetIteratorComplex.hasNext()) {
            	logger.info(" INSIDE entrySetIteratorComplex SpringBoot: "+entrySetIteratorComplex);
                Entry<String, JSONArray> entry = entrySetIteratorComplex.next();
                logger.info(" entry entrySetIteratorComplex SpringBoot:>>> "+attribXML);
                attribXML.append(insertComplexData(entry.getValue().toString(), entry.getKey()));
                logger.info(" FINAL insertComplexData  :>> "+attribXML);
            }
        }
        if (jsonMap.size() > 0) {
            entrySet = jsonMap.entrySet();
            entrySetIterator = entrySet.iterator();
            while (entrySetIterator.hasNext()) {
                Entry<String, String> entry = entrySetIterator.next();
                attribXML.append(insertComplexData(entry.getValue(), entry.getKey()));
            }
        }

        logger.info("WFSetAttributesNew attribXML SpringBoot===>" + attribXML);
        StringBuilder sInputXML = new StringBuilder();
        sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
                .append("<WMAssignWorkItemAttributes_Input>").append("\n")
                .append("<Option>WMAssignWorkItemAttributes</Option>").append("\n")
                .append("<SessionId>" + sessionId + "</SessionId>").append("\n")
                .append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
                .append("<ProcessDefId>" + processDefId + "</ProcessDefId>").append("\n")
                .append("<ProcessInstanceId>" + processInstanceid + "</ProcessInstanceId>").append("\n")
                .append("<WorkitemId>" + WorkitemId + "</WorkitemId>").append("\n")
                .append("<UserDefVarFlag>Y</UserDefVarFlag>").append("\n")
                .append("<ReminderFlag>N</ReminderFlag>").append("\n")
                .append("<Attributes>" + attribXML + "</Attributes>").append("\n")
                .append("</WMAssignWorkItemAttributes_Input>");
        //Lock Workitem
        WMGetWorkItem(sessionId, processInstanceid, WorkitemId, cabinetName);
        logger.info("WFSetAttributesNew sInputXML SpringBoot===>" + sInputXML);
        String outputXML = objNGEjbClient.makeCall(String.valueOf(sInputXML));
        logger.info("WFSetAttributesNew OutputXML SpringBoot===>" + outputXML);

        //		if(processInstanceid.substring(0,2).equalsIgnoreCase("AO")){
        //			ProdCreateXML.saveCustTxn(attribMap,processInstanceid,sessionId);
        //		}

        return outputXML;
    }

    public static String WMGetWorkItem(String sessionId, String ProcessInstanceId, int WorkitemId, String cabinetName) throws NGException {
        StringBuilder sInputXML = new StringBuilder();
        sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
                .append("<WMGetWorkItem_Input>").append("\n")
                .append("<Option>WMGetWorkItem</Option>").append("\n")
                .append("<SessionId>" + sessionId + "</SessionId>").append("\n")
                .append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
                .append("<ProcessInstanceId>" + ProcessInstanceId + "</ProcessInstanceId>").append("\n")
                .append("<WorkitemId>" + WorkitemId + "</WorkitemId>").append("\n")
                .append("</WMGetWorkItem_Input>");
        String outputXML = objNGEjbClient.makeCall(String.valueOf(sInputXML));
        logger.info("WMGetWorkItem OutputXML ===>" + outputXML);
        int mainCode = Integer.parseInt(new XMLParser(outputXML).getValueOf("MainCode"));
        if (mainCode == 16) {
            WMUnlockWorkItem(sessionId, ProcessInstanceId, WorkitemId, cabinetName);
        }
        return outputXML;
    }

    public static String WMUnlockWorkItem(String sessionId, String ProcessInstanceId, int WorkitemId, String cabinetName) throws NGException {
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
        String outputXML = objNGEjbClient.makeCall(String.valueOf(sInputXML));
        return outputXML;
    }

    private static String insertComplexData(String json, String attributeName) {
        StringBuilder complexXml = new StringBuilder("");
        try {
            logger.info("[BusinessChoice]Product JSON: " + json);
            logger.info("[BusinessChoice]Product attributeName: " + attributeName);
            JsonElement jelement = new JsonParser().parse(json);
            logger.info("[BusinessChoice]JSON jelement: " + jelement);
            JsonArray jArray = jelement.getAsJsonArray();
            logger.info("[BusinessChoice]JSON jArray: " + jArray);
            String key = "";
            String val = "";

            for (JsonElement jsonElement : jArray) {
                logger.info("[BusinessChoice]JSON jsonElement: " + jsonElement);
                JsonObject jObject = jsonElement.getAsJsonObject();
                Gson gson = new Gson();
                String ss = new Gson().toJson(jObject);
                HashMap<String, Object> mm = gson.fromJson(ss, HashMap.class);
                Set<Map.Entry<String, Object>> entrySet = mm.entrySet();
                complexXml.append("<" + attributeName + ">");
                for (Entry<String, Object> entry : entrySet) {
                    val = entry.getValue().toString();
                    key = entry.getKey();

                    complexXml.append("<" + key + ">").append(val).append("</" + key + ">");
                }
                complexXml.append("</" + attributeName + ">");
                logger.info("[BusinessChoice]JSON complexXml: " + complexXml);
            }
        } catch (Exception e) {
            logger.info("Exception in " + e);
            //e.printStackTrace();
        }
        return complexXml.toString();
    }

    public HashMap<String, RequestAttributeDetails> createRequestMappingMap(String cabinetName) throws NGException {
        logger.info("Inside createRequestMappingMap");
        String outputXML = APCallCreateXML.APSelect("SELECT SOURCING_CHANNEL, ATTRIBUTE_ID, ATTRIBUTE_NAME,"
                + "ATTRIBUTE_TYPE, MAPPING_VAR_NAME, MAPPING_COLUMN_NAME, DELETE_TABLE_ENTRY,"
                + "COMPLEX_TABLE_NAME,FETCH_DATA_COLUMN_NAME,FETCH_DATA_TABLE_NAME FROM RAROC_REQUEST_ATTRIBUTE_DETAILS", cabinetName);//Change By Rishabh
        logger.info("createRequestMappingMap "+outputXML);
        XMLParser xp = new XMLParser(outputXML);
        int start = xp.getStartIndex("Records", 0, 0);
        int deadEnd = xp.getEndIndex("Records", start, 0);
        int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
        int end = 0;
        HashMap<String, RequestAttributeDetails> attribMap;
        attribMap = new HashMap<>();
        for (int i = 0; i < noOfFields; ++i) {
            start = xp.getStartIndex("Record", end, 0);
            end = xp.getEndIndex("Record", start, 0);
            String sourcingChannel = xp.getValueOf("SOURCING_CHANNEL", start, end);
            int attributeId = Integer.parseInt(xp.getValueOf("ATTRIBUTE_ID", start, end));
            String attributeName = xp.getValueOf("ATTRIBUTE_NAME", start, end);
            String attributeType = xp.getValueOf("ATTRIBUTE_TYPE", start, end);
            String mappingVarName = xp.getValueOf("MAPPING_VAR_NAME", start, end);
            String mappingColumnName = xp.getValueOf("MAPPING_COLUMN_NAME", start, end);
            String deleteTableEntry = xp.getValueOf("DELETE_TABLE_ENTRY", start, end);
            String complexTableName = xp.getValueOf("COMPLEX_TABLE_NAME", start, end);
            String fetchColumnName = xp.getValueOf("FETCH_DATA_COLUMN_NAME", start, end);//Added by Rishabh
            String fetchTableName = xp.getValueOf("FETCH_DATA_TABLE_NAME", start, end);//Added by Rishabh
            RequestAttributeDetails rad = new RequestAttributeDetails();
            rad.setAttributeId(attributeId);
            rad.setAttributeName(attributeName);
            rad.setAttributeType(attributeType);
            rad.setMappingColumnName(mappingColumnName);
            rad.setMappingVarName(mappingVarName);
            rad.setDeleteTableEntry(deleteTableEntry);
            rad.setComplexTableName(complexTableName);
            rad.setDeleteTableEntry(deleteTableEntry);
            rad.setComplexTableName(complexTableName);
            rad.setFetchColumnName(fetchColumnName);//Added by Rishabh
            rad.setFetchTableName(fetchTableName);//Added by Rishabh
            logger.info("RequestAttributeDetails rad>>>>>>> "+rad);
            attribMap.put(attributeName, rad);

           // sourcingChannelAttribMap.put(sourcingChannel, attribMap);
          // logger.info("sourcingChannelAttribMap after attributeName  >>>>>>> "+sourcingChannelAttribMap);
        }
        logger.info("sourcingChannelAttribMap return val >>>>>>>> "+sourcingChannelAttribMap.get(sourcingChannel));
       // return sourcingChannelAttribMap.get(sourcingChannel);
        logger.info("attribMap after attributeName  >>>>>>> "+attribMap);
        return attribMap;
    }

    //Created By Rishabh
    /** This Function is used to Create Object Loaded with Master Having Master Table(Raroc Request Attribute Details) Data **/
	public RarocRequestAttrDetails createRarocReqAttrDetails(String cabinetName) throws NGException {
		String outputXML = APCallCreateXML.APSelect(
				"SELECT ATTRIBUTE_NAME,ATTRIBUTE_TYPE,MAPPING_VAR_NAME,"
						+ "FETCH_DATA_COLUMN_NAME,FETCH_DATA_TABLE_NAME,DATA_TYPE FROM RAROC_REQUEST_ATTRIBUTE_DETAILS",
				cabinetName);
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
		int end = 0;
		RarocRequestAttrDetails rarocRequestAttrDetails = new RarocRequestAttrDetails();
		HashMap<String, RarocTableDetails> radMap = new HashMap<>();//MAP Contains Key and RarocTableDetails Object
		for (int i = 0; i < noOfFields; ++i) {
			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			String attributeName = xp.getValueOf("ATTRIBUTE_NAME", start, end);
			String attributeType = xp.getValueOf("ATTRIBUTE_TYPE", start, end);
			String mappingVarName = xp.getValueOf("MAPPING_VAR_NAME", start, end);
			String fetchColumnName = xp.getValueOf("FETCH_DATA_COLUMN_NAME", start, end);
			String fetchTableName = xp.getValueOf("FETCH_DATA_TABLE_NAME", start, end);
			String dataType = xp.getValueOf("DATA_TYPE", start, end);

			HashMap<String, RarocAttrDetails> colAttrMap = new HashMap<>();//COLUMN ATTRIBUTE MAP
			RarocTableDetails rtd = new RarocTableDetails();//This Object Contains Table Name and COLUMN ATTRIBUTE MAP
			RarocAttrDetails rad = new RarocAttrDetails();//This Object Contains Attribute Details

			String keyName = mappingVarName;//Default the Key Name To Mapping Var Name
			if ("E".equals(attributeType)) {
				keyName = fetchTableName;//set the Key Name To Table Name if Attribute Type is E
			}

			if (radMap != null && radMap.get(keyName + "#" + attributeType) != null) {//Check if Same Key is already Present in Object then Extract the Map and Load New Column Details with existing Map Details
				Set<Entry<String, RarocTableDetails>> entrySet = radMap.entrySet();
				for (Entry<String, RarocTableDetails> entry : entrySet) {
					String compositeKey = entry.getKey();
					rtd = entry.getValue();
					if (compositeKey.split("#")[1].equals(attributeType)
							&& compositeKey.split("#")[0].equals(keyName)) {
						colAttrMap = rtd.getColAttrMap();
						rad.setClob(false);
						rad.setDate(false);
						rad.setContainsAmp(false);
						rad.setAttributeName(attributeName);
						rad.setDataTypeName(dataType);

						colAttrMap.put(fetchColumnName, rad);

						rtd.setColAttrMap(colAttrMap);

						break;
					}
				}
				radMap.put(keyName + "#" + attributeType, rtd);
			} else {
				rad.setClob(false);
				rad.setDate(false);
				rad.setContainsAmp(false);
				rad.setAttributeName(attributeName);
				rad.setDataTypeName(dataType);
				colAttrMap.put(fetchColumnName, rad);

				rtd.setColAttrMap(colAttrMap);
				rtd.setTableName(fetchTableName);


				try {
					radMap.put(keyName + "#" + attributeType, rtd);
				} catch (Exception e) {
					System.out.println(" exception in radmap rkd " + e.getMessage());
					e.getMessage();
				}
			}
		}
		rarocRequestAttrDetails.setRadMap(radMap);//Set Final Map with Master Data
		return rarocRequestAttrDetails;
	}
	 /** This Function is used to Create Attribute XML Going to use in WFSetAttribute API To set Data in WI**/
	public StringBuilder createAttributeXML(RarocRequestAttrDetails rarocRequestAttrDetails, String sourceWI,
			String targetWI, String cabinetName) throws Exception {
		StringBuilder attributeXML = new StringBuilder("");
		try {
			HashMap<String, RarocTableDetails> radMap = rarocRequestAttrDetails.getRadMap();
			Set<Entry<String, RarocTableDetails>> entrySet = radMap.entrySet();
			for (Entry<String, RarocTableDetails> entry : entrySet) {
				String compositekey = entry.getKey();
				RarocTableDetails rtd = entry.getValue();
				if ("E".equals(compositekey.split("#")[1])) {
					String tableName = compositekey.split("#")[0];
					HashMap<String, RarocAttrDetails> colAttrMap = rtd.getColAttrMap();
					String createQuery = "SELECT " + String.join(",", colAttrMap.keySet()) + " FROM " + tableName
							+ " WHERE WI_NAME = N'" + sourceWI + "'";
					if(tableName.equalsIgnoreCase("NG_CA_Security_Details"))
					{
						createQuery+=" and COLLATERAL_TYPE<>'Gurantees' ";
						createQuery+= "union all SELECT " + String.join(",", colAttrMap.keySet()) + " FROM " + tableName
								+ " WHERE WI_NAME = N'" + sourceWI + "' and COLLATERAL_TYPE='Gurantees' and COLLATERAL_SUB_TYPE='Bank Gurantee'";
						
					}
					if(tableName.equalsIgnoreCase("NG_CA_FACILITY_DETAILS"))
					{
						createQuery+=" and PROP_FACILITY_AMT<>0";

					}
					String outputXML = APCallCreateXML.APSelect(createQuery, cabinetName);
					XMLParser xp = new XMLParser(outputXML);
					int start = xp.getStartIndex("Records", 0, 0);
					int deadEnd = xp.getEndIndex("Records", start, 0);
					int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
					int end = 0;
					if (noOfFields == 1) {
						start = xp.getStartIndex("Record", end, 0);
						end = xp.getEndIndex("Record", start, 0);
						String colValue = "";
						for (String colName : colAttrMap.keySet()) {
							colValue = xp.getValueOf(colName, start, end);
							colValue = colValue.replace("&", "");
							RarocAttrDetails attrDetails = colAttrMap.get(colName);
							//if (attrDetails.isDate()) {
								//colValue = APCallCreateXML.formatToBPMDate(colValue);
							//}else
								if (attrDetails.isContainsAmp()) {
								colValue = colValue.replace("&", "");
							}
							String atributename =attrDetails.getAttributeName();
							String dataTypeval = attrDetails.getDataType();
							//String dataTypeval =dataTypaVal(cabinetName ,atributename);
							if(dataTypeval.equalsIgnoreCase("Integer")) {
								if(!colValue.equalsIgnoreCase("")) {
								colValue= doubleValue(colValue);
								}
							}
							if(dataTypeval.equalsIgnoreCase("Date")) {
								if(!colValue.equalsIgnoreCase("")) {
								colValue= dateValue( colValue);
								}
						}
							attributeXML.append("<" + attrDetails.getAttributeName() + ">" + colValue + "</"
									+ attrDetails.getAttributeName() + ">");
						}
						attributeXML.append("<WI_NAME>" + targetWI + "</WI_NAME>");
					}
				} else if("Q".equals(compositekey.split("#")[1])){

					String qVarName = compositekey.split("#")[0];

					HashMap<String, RarocAttrDetails> colAttrMap = rtd.getColAttrMap();
					String tableName = rtd.getTableName();
					String createQuery = "SELECT " + String.join(",", colAttrMap.keySet()) + " FROM " + tableName
							+ " WHERE WI_NAME = N'" + sourceWI + "'";
					if(tableName.equalsIgnoreCase("NG_CA_Security_Details"))
					{
						createQuery+=" and COLLATERAL_TYPE<>'Gurantees' ";
						createQuery+= "union all SELECT " + String.join(",", colAttrMap.keySet()) + " FROM " + tableName
								+ " WHERE WI_NAME = N'" + sourceWI + "' and COLLATERAL_TYPE='Gurantees' and COLLATERAL_SUB_TYPE='Bank Gurantee'";
						
					}
					if(tableName.equalsIgnoreCase("NG_CA_FACILITY_DETAILS"))
					{
						createQuery+=" and PROP_FACILITY_AMT<>0";

					}
					String outputXML = APCallCreateXML.APSelect(createQuery, cabinetName);
					XMLParser xp = new XMLParser(outputXML);
					int start = xp.getStartIndex("Records", 0, 0);
					int deadEnd = xp.getEndIndex("Records", start, 0);
					int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
					int end = 0;
					for (int i = 0; i < noOfFields; i++) {
						start = xp.getStartIndex("Record", end, 0);
						end = xp.getEndIndex("Record", start, 0);
						String colValue = "";
						attributeXML.append("<" + qVarName + ">");
						for (String colName : colAttrMap.keySet()) {
							colValue = xp.getValueOf(colName, start, end);
							colValue = colValue.replace("&", "&amp;");
							RarocAttrDetails attrDetails = colAttrMap.get(colName);
							//if (attrDetails.isDate()) {
								//colValue = APCallCreateXML.formatToBPMDate(colValue);
							//} else
								if (attrDetails.isContainsAmp()) {
								colValue = colValue.replace("&", "&amp;");
							}
							String atributename =attrDetails.getAttributeName();
							String dataTypeval = attrDetails.getDataType();

							//String dataTypeval =dataTypaVal(cabinetName ,atributename);
							if(dataTypeval.equalsIgnoreCase("Integer")) {
								if(!colValue.equalsIgnoreCase("")) {
								colValue= doubleValue(colValue);
								}
							}
							if(dataTypeval.equalsIgnoreCase("Date")) {
								if(!colValue.equalsIgnoreCase("")) {
								colValue= dateValue( colValue);
								}
						}
							attributeXML.append("<" + attrDetails.getAttributeName() + ">" + colValue + "</"
									+ attrDetails.getAttributeName() + ">");
						}
						attributeXML.append("</" + qVarName + ">");
					}


				} else {
					String qVarName = compositekey.split("#")[0];
					attributeXML.append("<" + qVarName + ">");
					HashMap<String, RarocAttrDetails> colAttrMap = rtd.getColAttrMap();
					String tableName = rtd.getTableName();
					String createQuery = "SELECT " + String.join(",", colAttrMap.keySet()) + " FROM " + tableName
							+ " WHERE WI_NAME = N'" + sourceWI + "'";
					String outputXML = APCallCreateXML.APSelect(createQuery, cabinetName);
					XMLParser xp = new XMLParser(outputXML);
					int start = xp.getStartIndex("Records", 0, 0);
					int deadEnd = xp.getEndIndex("Records", start, 0);
					int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
					int end = 0;
					for (int i = 0; i < noOfFields; i++) {
						start = xp.getStartIndex("Record", end, 0);
						end = xp.getEndIndex("Record", start, 0);
						String colValue = "";
						for (String colName : colAttrMap.keySet()) {
							colValue = xp.getValueOf(colName, start, end);
							colValue = colValue.replace("&", "&amp;");
							RarocAttrDetails attrDetails = colAttrMap.get(colName);
							//if (attrDetails.isDate()) {
								//colValue = APCallCreateXML.formatToBPMDate(colValue);
							//} else
							if (attrDetails.isContainsAmp()) {
								colValue = colValue.replace("&", "&amp;");
							}
							String atributename =attrDetails.getAttributeName();
							String dataTypeval = attrDetails.getDataType();
							//String dataTypeval =dataTypaVal(cabinetName ,atributename);
							if(dataTypeval.equalsIgnoreCase("Integer")) {
								if(!colValue.equalsIgnoreCase("")) {
								colValue= doubleValue(colValue);
								}
							}
							if(dataTypeval.equalsIgnoreCase("Date")) {
								if(!colValue.equalsIgnoreCase("")) {
								colValue= dateValue( colValue);
								}
						}
							attributeXML.append("<" + attrDetails.getAttributeName() + ">" + colValue + "</"
									+ attrDetails.getAttributeName() + ">");
						}
					}
					attributeXML.append("</" + qVarName + ">");
				}
			}
			logger.info("attributeXML:  " + attributeXML);
		} catch (Exception e) {
			logger.info("Exception in createAttributeXML " + e.getMessage());
			e.printStackTrace();
		}
		return attributeXML;
	}

    public static String WFUploadWorkItemNew(String sessionId, String initiateAlso, String processDefId,
			String cabinetName) throws NGException {

		StringBuilder sInputXML = new StringBuilder();
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n").append("<WFUploadWorkItem_Input>").append("\n")
				.append("<Option>WFUploadWorkItem</Option>").append("\n")
				.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
				.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
				.append("<ProcessDefId>" + processDefId + "</ProcessDefId>").append("\n")
				.append("<InitiateAlso>" + initiateAlso + "</InitiateAlso>").append("\n")
				.append("<ValidationRequired></ValidationRequired>").append("\n").append("<DataDefName></DataDefName>")
				.append("\n").append("<InitiateFromActivityId>1</InitiateFromActivityId>").append("\n")
				.append("<InitiateFromActivityName>Initiator</InitiateFromActivityName>").append("\n")
				.append("<Attributes></Attributes>").append("\n").append("</WFUploadWorkItem_Input>");
		logger.info("sInputXML " + sInputXML);
		logger.info("before " + objNGEjbClient);
		logger.info("after " + objNGEjbClient);
		String outputXML = objNGEjbClient.makeCall(String.valueOf(sInputXML));
		logger.info("sInputXML " + outputXML);
		return outputXML;
	}

    public String WFSetAttributes(String sessionId, String processInstanceid, int WorkitemId,
			String attrXML, String processDefId, String cabinetName) throws Exception {
		logger.info("*************WFSetAttributes************");
		logger.info("WFSetAttributes attribXML SpringBoot===>" + attrXML);
		StringBuilder sInputXML = new StringBuilder();
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n").append("<WMAssignWorkItemAttributes_Input>")
				.append("\n").append("<Option>WMAssignWorkItemAttributes</Option>").append("\n")
				.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
				.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
				.append("<ProcessDefId>" + processDefId + "</ProcessDefId>").append("\n")
				.append("<ProcessInstanceId>" + processInstanceid + "</ProcessInstanceId>").append("\n")
				.append("<WorkitemId>" + WorkitemId + "</WorkitemId>").append("\n")
				.append("<UserDefVarFlag>Y</UserDefVarFlag>").append("\n").append("<ReminderFlag>N</ReminderFlag>")
				.append("\n").append("<Attributes>" + attrXML + "</Attributes>").append("\n")
				.append("</WMAssignWorkItemAttributes_Input>");
		// Lock Workitem
		WMGetWorkItem(sessionId, processInstanceid, WorkitemId, cabinetName);
		logger.info("WFSetAttributes sInputXML SpringBoot===>" + sInputXML);
		String outputXML = objNGEjbClient.makeCall(String.valueOf(sInputXML));
		logger.info("WFSetAttributes OutputXML SpringBoot===>" + outputXML);
		return outputXML;
	}
	public String dataTypaVal(String cabinetName, String attributeName) throws NGException{
		String dataType="";
		String outputXML = APCallCreateXML.APSelect(
				"SELECT DATA_TYPE FROM RAROC_REQUEST_ATTRIBUTE_DETAILS WHERE ATTRIBUTE_NAME ='"+attributeName+"'",
				cabinetName);
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
		int end = 0;
		if ( noOfFields ==1) {
			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			 dataType = xp.getValueOf("DATA_TYPE", start, end);
            }
		return dataType;
		}

	public static String doubleValue(String value1) // Got here 6.743240136E7 or something..
	{
		String returnVal =value1;
		try {
		double value = Double.parseDouble(value1);
	  DecimalFormat formatter;
	  if (value - (int) value == 0.0) {
	    formatter = new DecimalFormat("0"); // Here you can also deal with rounding if you wish..
	  }else {
	    formatter = new DecimalFormat("0.00");
	        }
	  returnVal= formatter.format(value);
		}
	catch (Exception e) {
		logger.info("Exception in doubleValue " + e.getMessage());
		e.printStackTrace();
	 }
		return  returnVal;
    }
	public static String dateValue(String value1) throws ParseException {
		logger.info("inside in dateValue ");
    	String dateval =value1;
    	try {
    	SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    	Date date = inputFormat.parse(dateval);

    	dateval = simpleDateFormat.format(date);
		}
	   catch (Exception e) {
		logger.info("Exception in dateValue " + e.getMessage());
		e.printStackTrace();
	 }
    	return dateval;
	}
	//start changes by mohit 24-06-2024
	public static String createAP_Procedure_XML(String procedureName,String sParams,String sSessionId,String sCabname)throws NGException
	{  
		String inXml = "<?xml version=\"1.0\"?>"
					+ "<APProcedure_WithDBO_Input>" 
					+ "<Option>APProcedure_WithDBO</Option>"
					+ "<ProcName>"+procedureName+"</ProcName>"
					+ "<Params>"+sParams+"</Params>" 
					+ "<EngineName>"+sCabname+"</EngineName>"
					+ "<SessionId>"+sSessionId+"</SessionId>\n"	
					+ "<NoOfCols>"+"3"+"</NoOfCols>\n"	
					+ "</APProcedure_WithDBO_Input>";
		logger.info("sInputXML " + inXml);
		logger.info("before.. " + objNGEjbClient);
		String outputXML = objNGEjbClient.makeCall(String.valueOf(inXml));
		logger.info("after.. " + objNGEjbClient);
	
		logger.info("sInputXML " + outputXML);
					return outputXML;
	}
	
	//end changes by mohit 24-06-2024


}
