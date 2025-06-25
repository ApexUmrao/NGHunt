package com.newgen.cbg.core;

import java.util.HashMap;

public interface ICallExecutor {

	public String createInputXML() throws Exception;
	
	public String executeCall(HashMap<String, String> input) throws Exception;
	
	public void executeDependencyCall() throws Exception;
	
	public void responseHandler(String outputXML, String inputXml) throws Exception;
	
	public void updateCallOutput(String inputXml) throws Exception;
	
}

