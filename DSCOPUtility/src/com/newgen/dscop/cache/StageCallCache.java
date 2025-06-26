package com.newgen.dscop.cache;

import java.util.ArrayList;
import java.util.HashMap;

import com.newgen.dscop.expiry.APCallCreateXML;
import com.newgen.dscop.expiry.CBGLogMe;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.wf.util.excp.NGException;

public class StageCallCache {

	private static StageCallCache cache = new StageCallCache();
	private HashMap<Integer, ArrayList<String>> callMap = new HashMap<Integer, ArrayList<String>>();
    private HashMap<String, ArrayList<String>> callNameMap = new HashMap<String, ArrayList<String>>();
	private HashMap<String,String> defaultMap = new HashMap<String,String>();


	private StageCallCache(){
		try {
			callMap = createCallMap();
			callNameMap = createCallNameMap();
			defaultMap = createDefaultValueMap();
		} catch (NGException e) {
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_ERROR,e);
		}
	}

	public static StageCallCache getReference(){
		return cache;
	}

	public HashMap<Integer, ArrayList<String>> getCachedCallMap() throws NGException{
		CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,"Inside getCachedCallMap:");	
		return callMap;
	}


	public HashMap<String, ArrayList<String>> getCachedCallNameMap() throws NGException{
		CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO," Inside getCachedCallNameMap:");
		return callNameMap;
	}

	public HashMap<String, String> getCachedDefaultMap() throws NGException{
		CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO," Inside getCachedDefaultMap:");
		return defaultMap;
	}
	private HashMap<Integer, ArrayList<String>> createCallMap() throws NGException{
		CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,"Inside1 createCallMap");
		callMap = new HashMap<Integer, ArrayList<String>>();
		String outputXML = APCallCreateXML.APSelect("SELECT CALL_ID, CALL_NAME, IS_MANDATORY, IS_IGNORABLE, PRODUCT_TYPE FROM USR_0_CBG_CALL_MASTER");
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start,deadEnd);
		int end = 0;
		for(int i = 0; i < noOfFields; ++i){
			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			int callId = Integer.parseInt(xp.getValueOf("CALL_ID", start, end));
			ArrayList<String> call = new ArrayList<String>(4);
			call.add(xp.getValueOf("CALL_NAME", start, end));
			call.add(xp.getValueOf("IS_MANDATORY", start, end));
			call.add(xp.getValueOf("IS_IGNORABLE", start, end));
			call.add(xp.getValueOf("PRODUCT_TYPE", start, end));
			callMap.put(callId, call);
		}
		CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,callMap.toString());
		return callMap;		
	}

	private HashMap<String, ArrayList<String>> createCallNameMap() throws NGException{
		CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,"Inside1 createCallMap");
		callNameMap = new HashMap<String, ArrayList<String>>();
		String outputXML = APCallCreateXML.APSelect("SELECT CALL_ID, CALL_NAME, IS_MANDATORY, IS_IGNORABLE, PRODUCT_TYPE FROM USR_0_CBG_CALL_MASTER");
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start,deadEnd);
		int end = 0;
		for(int i = 0; i < noOfFields; ++i){
			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			String callName = xp.getValueOf("CALL_NAME", start, end);
			ArrayList<String> call = new ArrayList<String>(4);
			call.add(xp.getValueOf("CALL_ID", start, end));
			call.add(xp.getValueOf("IS_MANDATORY", start, end));
			call.add(xp.getValueOf("IS_IGNORABLE", start, end));
			call.add(xp.getValueOf("PRODUCT_TYPE", start, end));
			callNameMap.put(callName, call);
		}
		return callNameMap;		
	}
	private HashMap<String, String> createDefaultValueMap() throws NGException{
		CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,"Inside2 createStageMap");
		defaultMap = new HashMap<String, String>();
		String outputXML = APCallCreateXML.APSelect("SELECT DEFAULT_KEY, DEFAULT_VALUE FROM USR_0_CBG_DEF_VAL_MASTER");
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
		int end = 0;
		for(int i = 0; i < noOfFields; ++i){
			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			defaultMap.put(xp.getValueOf("DEFAULT_KEY", start, end), xp.getValueOf("DEFAULT_VALUE", start, end));
		}
		CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,defaultMap.toString());
		return defaultMap;		
	}
}
