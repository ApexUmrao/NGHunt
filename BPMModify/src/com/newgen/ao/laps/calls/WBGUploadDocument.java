package com.newgen.ao.laps.calls;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.cache.StageCallCache;
import com.newgen.ao.laps.core.ICallExecutor;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.wf.util.excp.NGException;

public class WBGUploadDocument implements ICallExecutor, Callable<String> {
	private CallEntity callEntity;
	private String sessionId;
	private String winame;
	private String sUserName;
	private String engineName;
	private String stageId;
	Map<String, String> attributeMap = new HashMap<String, String>();
	String responseXML = "";
	String callStatus = "";
	String status = "";
	int returnCode ;
	String errorDescription = "";
	private String documentName;
	private String documentIndex;
	private String folderIndex;
	private String ISIndex;
	private String volumeID;
	private String docSize;	
	private String appName;
	private String NOOFPages;
	//	private String docType;
	private String uniqueRefNo="";

	
		
	public WBGUploadDocument(Map<String, String> attributeMap, String sessionId, String stageId, String wiNumber, 
			Boolean prevStageNoGo, CallEntity callEntity) {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside wbgUploadWI");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"attributeMap "+attributeMap.toString());
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Value of UNIQUE_REF_NO =  "+attributeMap.get("UNIQUE_REF_NO"));
		this.callEntity = callEntity;
		this.attributeMap = attributeMap;
		this.sessionId = sessionId;
		this.engineName=LapsConfigurations.getInstance().CabinetName;
		this.stageId=stageId;
		this.winame=wiNumber;
		sUserName = LapsConfigurations.getInstance().UserName;
		String op = "";
		try {
			uniqueRefNo = attributeMap.get("UNIQUE_REF_NO");
			op = APCallCreateXML.APSelect("SELECT ITEMINDEX FROM EXT_WBG_AO WHERE WI_NAME ='"+winame+"'");
		} catch (NGException e) {
			e.printStackTrace();
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "op is"+op);
		XMLParser xp1 = new XMLParser(op);
		int mainCode = Integer.parseInt(xp1.getValueOf("MainCode"));
		if(mainCode == 0){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"TotalRetrieved: "+Integer.parseInt(xp1.getValueOf("TotalRetrieved")));
			if(Integer.parseInt(xp1.getValueOf("TotalRetrieved")) > 0){
				folderIndex= xp1.getValueOf("ITEMINDEX");
			}
		}	
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "WBGUploadDocument cunstructor called map===> " +callEntity.toString()+" Session id ===>"+sessionId);
	}
	
	@Override
	public String call() throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"WBGUploadDocument call: inside");
		String inputXml = "";
		
		try {
			addDoc(uniqueRefNo,sessionId);
			} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			throw new Exception(e.getMessage());
		}
	   
		return responseXML;
	}

	public String executeOnEDMS(String docIndex) {
		String sQuery = "";
		String outputString = "";
		try {
			sQuery = "SELECT DOCUMENTINDEX ,NAME,COMMNT,IMAGEINDEX, VOLUMEID,NOOFPAGES, DOCUMENTSIZE,APPNAME FROM PDBDOCUMENT"
					+ " WHERE DOCUMENTINDEX IN (" + docIndex + ")";
			String outputXML = APCallCreateXML.APSelectEdms(sQuery);
			XMLParser xp = new XMLParser(outputXML);
			outputString = xp.getValueOf("APSelectWithColumnNames_Output");	
		}
		catch (Exception localException5) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"[executeOnWI]" + "executeOnWI localException5 : " +localException5.getStackTrace());
		} 
		return outputString; 
	}
			
	private void addDoc(String uniqueRefNo,String sessionId){
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"uniqueRefNo "+uniqueRefNo);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"sessionId "+sessionId);
		String outputXML;
		String addDocOutputXml;
		try {
			outputXML = APCallCreateXML.APSelect("SELECT DOCNAME, DOCINDEX FROM BPM_WBG_MSG_TXN_DOC_DATA WHERE "
					+ "UNIQUE_REF_NO = N'" + uniqueRefNo + "'");
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if(mainCode == 0){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"AddDocument TotalRetrieved: "+Integer.parseInt(xp.getValueOf("TotalRetrieved")));
				if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
					int start = xp.getStartIndex("Records", 0, 0);
					int deadEnd = xp.getEndIndex("Records", start, 0);
					int noOfFields = xp.getNoOfFields("Record", start,deadEnd);
					int end = 0;
					for(int i = 0; i < noOfFields; ++i){
						start = xp.getStartIndex("Record", end, 0);
						end = xp.getEndIndex("Record", start, 0);
						documentName = xp.getValueOf("DOCNAME" ,start, end);
						documentIndex = xp.getValueOf("DOCINDEX" ,start, end);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"documentIndex: "+documentIndex);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"documentName: "+documentName);
						if(folderIndex!=null && documentIndex!=null && !"".equalsIgnoreCase(documentIndex)){
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside If documentName: ");
							outputXML = executeOnEDMS(documentIndex); 
							XMLParser parser = new XMLParser(outputXML);
							int fisrtmainCode = Integer.parseInt(parser.getValueOf("MainCode"));
							int totalRetrievedCount = Integer.parseInt(parser.getValueOf("TotalRetrieved"));
							 if(totalRetrievedCount==0){
								String errorDescMandatoryCheck = "Invalid request. Eida is mandatory for customer";
								String errorCodeMandatoryCheck = "450";
								String 	statusCodeMandatoryCheck = "1";
								 String pushMessageXML = LapsUtils.createPushMesgXML(uniqueRefNo,winame,
											statusCodeMandatoryCheck,errorCodeMandatoryCheck,errorDescMandatoryCheck);
									LapsUtils.updatePushMeassgeXML(sessionId,winame,pushMessageXML);
									PushMessageItqanM pushMsg = new PushMessageItqanM(sessionId, winame);
									pushMsg.call();
							 } else if (fisrtmainCode == 0) {
								int start1 = parser.getStartIndex("Records", 0, 0);
								int deadEnd1 = parser.getEndIndex("Records", start1, 0);
								int noOfFields1 = parser.getNoOfFields("Record", start1, deadEnd1);
								for (int j = 0; j < noOfFields1; ++j) { 
									String Record = parser.getNextValueOf("Record");
									XMLParser xp2 = new XMLParser(Record);
									String imageIndex = xp2.getValueOf("IMAGEINDEX");
									String appname = xp2.getValueOf("APPNAME");
									String volumeID = xp2.getValueOf("VOLUMEID");
									String name = xp2.getValueOf("NAME");
									String commnt = xp2.getValueOf("COMMNT");
									String documentLength = xp2.getValueOf("DOCUMENTSIZE");
									int noOfPages = Integer.parseInt(xp2.getValueOf("NOOFPAGES"));
									ISIndex = imageIndex+"#"+ volumeID +"#";
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"imageIndex: "+imageIndex);
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"appname: "+appname);
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"volumeID: "+volumeID);
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"name: "+name);
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"commnt: "+commnt);
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"noOfPages: "+noOfPages);
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"documentLength: "+documentLength);
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"ISIndex: "+ISIndex);
									
									addDocOutputXml = ProdCreateXML.AddDocument(sessionId,folderIndex,name,appname,commnt,
											Integer.parseInt(volumeID),ISIndex,noOfPages,"I",documentLength);
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"addDocOutputXml "+addDocOutputXml);

								}
							}
						}
					}
				}
			}			

		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, e);
		}
	}
	
	@Override
	public String executeCall(HashMap<String, String> input) throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Execute call called");
		return call();
	}

	@Override
	public void executeDependencyCall() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void responseHandler(String outputXML, String inputXml)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String createInputXML() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
