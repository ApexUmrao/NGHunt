package com.newgen.ao.laps.cache;

import java.util.ArrayList;
import java.util.HashMap;

import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.wf.util.excp.NGException;

public class EACache {
	private HashMap<String, Integer> processdefMap = new HashMap<String, Integer>();
	private HashMap<Integer, HashMap<String,ArrayList<EAFlow>>> eaFlowMap = new HashMap<>();
	private HashMap<Integer, EADecisionMaster> flowDecMast = new HashMap<>();
	
	private static EACache instance = new EACache();

	public static EACache getInstance() {
		return instance;
	}
	
	private EACache(){
		try {
			createProcessdefMap();
			//createEAFlowMap();
			createFlowDecMap();
		} catch (NGException e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"errror" + e);
		}
		
	}

	public HashMap<String, Integer> getProcessdefMap() {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"call Name is ");
		if (processdefMap.isEmpty()) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"call Name is empty ");
			try {
				processdefMap = createProcessdefMap();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Value of call name is " + processdefMap.toString());
			} catch (NGException e) {
				// TODO Auto-generated catch block
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"errror" + e);
			}
		}
		return processdefMap;
	}

	private HashMap<String, Integer> createProcessdefMap() throws NGException {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"inside createProcessdefMap once");
		processdefMap = new HashMap<String, Integer>();
		String outputXML = APCallCreateXML
				.APSelect("SELECT PROCESSDEFID, REGPREFIX FROM PROCESSDEFTABLE");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputxml issss " + outputXML);
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
		int end = 0;
		for (int i = 0; i < noOfFields; ++i) {
			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			int processdefid = Integer.parseInt(xp.getValueOf("PROCESSDEFID", start, end));
			String regPrefix = xp.getValueOf("REGPREFIX", start, end);
			processdefMap.put(regPrefix, processdefid);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
					"Value of callName are " + processdefMap.toString());

		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
				"inside callMap " + processdefMap.toString());
		return processdefMap;
	}
		
	public ArrayList<EAFlow> getEAFlowMap(int processDefID,String code) {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside EAFlowMap ");	
		ArrayList<EAFlow> flowList = new ArrayList<>();
		if (eaFlowMap.isEmpty()) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"EAFlowMap is empty ");
			try {
				eaFlowMap = createEAFlowMap();				
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Value of EAFlowMap is " + eaFlowMap.toString());
				
			} catch (Exception e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"errror" + e);
			}
		}
		flowList = eaFlowMap.get(processDefID).get(code);
		return flowList;
	}
	
	public EADecisionMaster getflowDecMast(int processdefId){
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside getflowDecMast is ");
		EADecisionMaster eda  = null;
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"EAFlowMap is empty ");
		try {
			flowDecMast = createFlowDecMap();				
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Value of EAFlowMap is " + eaFlowMap.toString());
			eda = flowDecMast.get(processdefId);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"errror" + e);
		}
		return eda;		
	}
	
	private HashMap<Integer, HashMap<String,ArrayList<EAFlow>>> createEAFlowMap() throws NGException {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"inside createeaflowMap once");
		String outputXML = APCallCreateXML
				.APSelect("SELECT BPM_EA_TEMPLATES.PROCESSDEFID, BPM_EA_TEMPLATES.EA_CODE, BPM_EA_TEMPLATES.EA_LEVEL_SEQ, "
				+"BPM_EA_TEMPLATE_IDS.DECISION_MAKER_USERID, BPM_EA_TEMPLATE_IDS.DECISION_MAKER_USERNAME, BPM_EA_TEMPLATE_IDS.DECISION_MAKER_EMAIL "
				+"FROM BPM_EA_TEMPLATES JOIN BPM_EA_TEMPLATE_IDS ON BPM_EA_TEMPLATES.TEMPLATE_ID = BPM_EA_TEMPLATE_IDS.TEMPLATE_ID");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputxml issss " + outputXML);
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
		int end = 0;
		for (int i = 0; i < noOfFields; ++i) {
			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			int processdefid = Integer.parseInt(xp.getValueOf("PROCESSDEFID", start, end));
			String eacode = xp.getValueOf("EA_CODE", start, end);
			int level = Integer.parseInt(xp.getValueOf("EA_LEVEL_SEQ", start, end));
			String userid = xp.getValueOf("DECISION_MAKER_USERID", start, end);
			String username = xp.getValueOf("DECISION_MAKER_USERNAME", start, end);
			String email = xp.getValueOf("DECISION_MAKER_EMAIL", start, end);
			if(eaFlowMap.containsKey(processdefid)){
				HashMap<String,ArrayList<EAFlow>> codeMap = eaFlowMap.get(processdefid);
				if(codeMap.containsKey(eacode)){
					ArrayList<EAFlow> list = codeMap.get(eacode);
					EAFlow obj = new EAFlow();
					obj.setEmail(email);
					obj.setLevel(level);
					obj.setUserID(userid);
					obj.setUserName(username);
					list.add(obj);
					codeMap.put(eacode, list);
					eaFlowMap.put(processdefid, codeMap);
				} else{
					ArrayList<EAFlow> list = new ArrayList<>();
					EAFlow obj = new EAFlow();
					obj.setEmail(email);
					obj.setLevel(level);
					obj.setUserID(userid);
					obj.setUserName(username);
					list.add(obj);
					codeMap.put(eacode, list);
					eaFlowMap.put(processdefid, codeMap);
				}
			} else {
				HashMap<String,ArrayList<EAFlow>> codeMap = new HashMap<>();
//				if(codeMap.containsKey(eacode)){
//					ArrayList<EAFlow> list = codeMap.get(eacode);
//					EAFlow obj = new EAFlow();
//					obj.setEmail(email);
//					obj.setLevel(level);
//					obj.setUserID(userid);
//					obj.setUserName(username);
//					list.add(obj);
//					codeMap.put(eacode, list);
//					eaFlowMap.put(processdefid, codeMap);
//				} else{
					ArrayList<EAFlow> list = new ArrayList<>();
					EAFlow obj = new EAFlow();
					obj.setEmail(email);
					obj.setLevel(level);
					obj.setUserID(userid);
					obj.setUserName(username);
					list.add(obj);
					codeMap.put(eacode, list);
					eaFlowMap.put(processdefid, codeMap);
				//}
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
					"Value of EAFlow are " + eaFlowMap.toString());
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
				"inside callMap " + eaFlowMap.toString());
		return eaFlowMap;
	}
	
	private HashMap<Integer,EADecisionMaster> createFlowDecMap() throws NGException {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"inside createProcessdefMap once");
		String outputXML = APCallCreateXML
				.APSelect("SELECT PROCESSDEFID, DECISION_TABLE_NAME, DECISION_COLUMN_NAME, RESPONSE_TABLE_NAME, "
						+ "RESPONSE_COLUMN_NAME  FROM BPM_EA_PROCESS_DECISION_MASTER");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputxml issss " + outputXML);
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
		int end = 0;
		for (int i = 0; i < noOfFields; ++i) {
			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			int processdefId = Integer.parseInt(xp.getValueOf("PROCESSDEFID", start, end));
			String decTableName = xp.getValueOf("DECISION_TABLE_NAME", start, end);
			String decColumnName = xp.getValueOf("DECISION_COLUMN_NAME", start, end);
			String responseTableName = xp.getValueOf("RESPONSE_TABLE_NAME", start, end);
			String responseColumnName = xp.getValueOf("RESPONSE_COLUMN_NAME", start, end);
			EADecisionMaster edm = new EADecisionMaster();
			edm.setDecisionColumnName(decColumnName);
			edm.setDecisionTableName(decTableName);
			edm.setResponseColumnName(responseTableName);
			edm.setResponseTableName(responseColumnName);			
			flowDecMast.put(processdefId, edm);
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
				"inside callMap " + processdefMap.toString());
		return flowDecMast;
	}
}
