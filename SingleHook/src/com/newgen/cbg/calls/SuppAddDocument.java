/**
 * BUG-ID		Changed DT		Changed By		Description
 * COLMP-5643   03/10/2023		AJAY			Change query under 12127 stage id
 */
package com.newgen.cbg.calls;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
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
import com.newgen.cbg.utils.ExecuteXML;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.jts.srvr.NGDBConnection;

public class SuppAddDocument implements ICallExecutor, Callable<String> {

	private String wiName;
	private int stageId;
	private String sessionId;
	private String callStatus;
	private int returnCode;
	private String errorDescription;
	private String errorDetail;
	private String status;
	private String reason;
	private String documentName;
	private String folderIndex;
	private String ISIndex;
	private String volumeID;
	private String docSize;	
	private String appName;
	private String NOOFPages;
	private String nationality;
	private String CALL_NAME;
	private boolean prevStageNoGo;
	private Map<String, String> defaultMap;
	private CallEntity callEntity;
	private String auditCallName = "DOC";
	private String passportdocumentName;
	private String passportDocumentIndex;
	private String eidadocumentName;
	private String eidaDocumentIndex;
	private String countryOfResidence;
	public SuppAddDocument(Map<String, String> defaultMap, String sessionId, String stageId, String wiName, Boolean prevStageNoGo, CallEntity callEntity) {
		this.sessionId=sessionId;
		this.wiName = wiName;
		this.stageId = Integer.parseInt(stageId);
		this.prevStageNoGo = prevStageNoGo;
		this.defaultMap = defaultMap;
		this.callEntity = callEntity;
		try {
		    DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"SuppAddDocument Start - Stageid:"+stageId +" WI_NAME : "+wiName);
			handleAddDocData(fetchAddDocData());
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "DOC100", e, sessionId);
		}
	}

	@Override
	public String call() throws Exception {
		String outputXml = "";
		try {
				String inputXml = createInputXML();
				outputXml = ExecuteXML.executeXML(inputXml);	
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "DOC002", "DOC output: "+outputXml, sessionId);
				responseHandler(outputXml, inputXml);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "DOC100", e, sessionId);
		}
		return outputXml;
	}


	@Override
	public void executeDependencyCall() throws Exception{
		try{
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "DOC003", "DOC DependencyCall:"+callEntity.getDependencyCallID(), sessionId);
			CallHandler.getInstance().executeDependencyCall(callEntity, defaultMap,sessionId, wiName, prevStageNoGo);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "DOC100", e, sessionId);
		}
	}

	@Override
	public String createInputXML() throws Exception {
		StringBuilder inputXML = new StringBuilder();
		try {
			inputXML.append("<?xml version=\"1.0\"?>").append("\n")
			.append("<NGOAddDocument_Input>").append("\n")
			.append("<Option>NGOAddDocument</Option>").append("\n")
			.append("<CabinetName>" + DSCOPConfigurations.getInstance().CabinetName + "</CabinetName>").append("\n")
			.append("<UserDBId>" + sessionId + "</UserDBId>").append("\n")
			.append("<GroupIndex>0</GroupIndex>").append("\n")
			.append("<Document>").append("\n")		
			.append("<ParentFolderIndex>" + folderIndex + "</ParentFolderIndex>").append("\n")
			.append("<DocumentName>"+ documentName +"</DocumentName>").append("\n")
			.append("<CreatedByAppName>" + appName + "</CreatedByAppName>").append("\n")
			.append("<Comment>" + documentName + "</Comment>").append("\n")
			.append("<VolumeIndex>" + volumeID + "</VolumeIndex>").append("\n")		
			.append("<ProcessDefId>" + DSCOPConfigurations.getInstance().ProcessDefId + "</ProcessDefId>").append("\n")		
			.append("<DataDefinition>" + "" + "</DataDefinition>").append("\n")		
			.append("<AccessType>S</AccessType>").append("\n")		
			.append("<ISIndex>" + ISIndex + "</ISIndex>").append("\n")		
			.append("<NoOfPages>" + NOOFPages + "</NoOfPages>").append("\n")		
			.append("<DocumentType>I</DocumentType>").append("\n")		
			.append("<DocumentSize>" + docSize + "</DocumentSize>").append("\n")		
			.append("<FTSFlag>PP</FTSFlag>").append("\n")		
			.append("</Document>").append("\n")		
			.append("</NGOAddDocument_Input>");
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SuppAddDocument inputXML ===> "+inputXML.toString());
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "DOC100", e, sessionId);
		}
		return inputXML.toString();   
	}

	@Override
	public String executeCall(HashMap<String, String> input) throws Exception {
		return call();
	}

	@Override
	public void responseHandler(String outputXML, String inputXML) throws Exception {
		try {
			XMLParser xp = new XMLParser(outputXML);
			returnCode = Integer.parseInt(xp.getValueOf("Status", "1", true));
			if(returnCode == 0) {
				callStatus = "S";
				errorDescription = "Successfully Executed";
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "DOC090", documentName + " Attached", sessionId);
			} else {
				callStatus = "F";
				errorDescription = "Failed";
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "DOC101", documentName, sessionId);
			}
			updateCallOutput(inputXML);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "DOC100", e, sessionId);
		}			
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		try {
			String valList = "'"+ wiName +"',"+ stageId +", '"+CALL_NAME+"', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"'";
			APCallCreateXML.APInsert("USR_0_DSCOP_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON", valList, sessionId);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "DOC100", e, sessionId);
		}
	}

	private String getFolderIndex(){
		 folderIndex=null;
		try {
			String outputXML = APCallCreateXML.APSelect("SELECT FOLDERINDEX FROM PDBFOLDER WHERE NAME = N'" + wiName + "'");
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if(mainCode == 0){
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"getFolderIndex TotalRetrieved: "+Integer.parseInt(xp.getValueOf("TotalRetrieved")));
				if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
					folderIndex = xp.getValueOf("FOLDERINDEX");
				}
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,e.getMessage());
			e.printStackTrace();
		}
		return folderIndex;
	}

	private void getISIndex(String docIndex){
		try {

			String edmscabinet = DSCOPConfigurations.getInstance().EDMS;
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"SuppAddDocument edmscabinet: "+ edmscabinet);		
			String outputXML = executeOnEDMS(edmscabinet, docIndex); 
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"SuppAddDocument: "+ outputXML);		

			XMLParser xp = new XMLParser(outputXML);
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"getISIndex TotalRetrieved: "+Integer.parseInt(xp.getValueOf("TotalRetrieved")));
			if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
				String imageIndex = xp.getValueOf("IMAGEINDEX");
				volumeID = xp.getValueOf("VOLUMEID");
				ISIndex = imageIndex+"#"+ volumeID +"#";
				docSize = xp.getValueOf("DOCUMENTSIZE");
				NOOFPages = xp.getValueOf("NOOFPAGES");
				appName = xp.getValueOf("APPNAME");			
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,e);
		}
	}

	public String executeOnEDMS(String edmsDB, String docIndex)
	{
		ResultSet localResultSet = null;
		Statement localStatement = null;
		ResultSetMetaData localResultSetMetaData = null;
		StringBuilder localStringBuffer2 = new StringBuilder();//String Buffer used before 
		StringBuilder localStringBuffer1 = new StringBuilder();
		Connection conn = null;
		String sQuery = "";
		try
		{
			sQuery = "SELECT DOCUMENTINDEX ,IMAGEINDEX, VOLUMEID,NOOFPAGES, DOCUMENTSIZE,APPNAME FROM PDBDOCUMENT WHERE DOCUMENTINDEX IN (" + docIndex + ")";
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"executeOnEDMS sQuery : " + sQuery);
			conn = NGDBConnection.getDBConnection(edmsDB, "Oracle");
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"executeOnEDMS conn : " + conn);
			localStatement = conn.createStatement();
			localResultSet = localStatement.executeQuery(sQuery);
			localResultSetMetaData = localResultSet.getMetaData();
			int i = localResultSetMetaData.getColumnCount();
			int j = 0;
			while (localResultSet.next()) {
				localStringBuffer2.append("\n\t\t<Record>");

				for (int k = 1; k <= i; ++k) {
					localStringBuffer2.append("\n\t\t\t<" + localResultSetMetaData.getColumnName(k) + ">");
					localStringBuffer2.append(removeNULL(localResultSet.getString(k)));
					localStringBuffer2.append("</" + localResultSetMetaData.getColumnName(k) + ">");
				}

				localStringBuffer2.append("\n\t\t</Record>");
				++j;
			}
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"executeOnEDMS localStringBuffer2 : " + localStringBuffer2);
			String str1 = localStringBuffer2.toString();
			str1 = localStringBuffer2.toString();
			str1 = "\n\t<Records>" + str1 + "\n\t</Records>";
			str1 = str1 + "\n\t<TotalRetrieved>" + j + "</TotalRetrieved>";
			localStringBuffer1.append("<Exception>\n<MainCode>0</MainCode>\n</Exception>\n");
			localStringBuffer1.append("<Output>" + str1 + "\n</Output>");

			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"executeOnEDMS str1 : " + str1);
		}
		catch (SQLException localException4)
		{
			localStringBuffer1.append("<Exception>\n<MainCode>" + localException4.getErrorCode() + "</MainCode>\n</Exception>\n");
			localStringBuffer1.append("<Output>" + localException4.getMessage() + "\n</Output>\n");
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,"localException4 : " +localException4.getStackTrace());
		}
		catch (Exception localException5)
		{
			localStringBuffer1.append("<Exception>\n<MainCode>9</MainCode>\n</Exception>\n");
			localStringBuffer1.append("<Output>" + localException5.toString() + "\n</Output>\n");
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,"localException5 : " +localException5.getStackTrace());
		} finally {
			try {
				if (localResultSet != null)
					localResultSet.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (localStatement != null)
					localStatement.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (conn != null)
					NGDBConnection.closeDBConnection(conn, "Oracle");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		return localStringBuffer1.toString(); 
	}

	private static String removeNULL(String paramString) {
		if (paramString == null) {
			return "";
		}
		return paramString.trim();
	}
	
	public String fetchAddDocData()throws Exception {
		return APCallCreateXML.APSelect("SELECT A.NATIONALITY,A.PASSPORT_DOC_NAME,A.PASSPORT_DOC_INDEX,A.EIDA_DOC_NAME,A.EIDA_DOC_INDEX,B.COUNTRY_OF_RESIDENCE FROM EXT_DSCOP A,EXT_DSCOP_EXTENDED B WHERE A.WI_NAME = B.WI_NAME AND A.WI_NAME = N'" + wiName + "'");
	}
	
	private void handleAddDocData(String outputXML) throws Exception {
		XMLParser xp = new XMLParser(outputXML);
		int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		if (mainCode == 0 && Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0) {
			nationality = xp.getValueOf("NATIONALITY");
			countryOfResidence = xp.getValueOf("COUNTRY_OF_RESIDENCE");
			passportdocumentName = xp.getValueOf("PASSPORT_DOC_NAME");
			passportDocumentIndex = xp.getValueOf("PASSPORT_DOC_INDEX");
			eidadocumentName = xp.getValueOf("EIDA_DOC_NAME");
			eidaDocumentIndex = xp.getValueOf("EIDA_DOC_INDEX");
		}
		if(!countryOfResidence.equalsIgnoreCase("AE")){
			CALL_NAME = "SuppAddDocument";
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "DOC001", "Passport DOC Started", sessionId);
			documentName = passportdocumentName;
			folderIndex = getFolderIndex();
			if(folderIndex!=null){
				getISIndex(passportDocumentIndex);
			}
		}
		else if(countryOfResidence.equalsIgnoreCase("AE")) {
			CALL_NAME = "SuppAddDocument";
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "DOC001", "EIDA DOC Started", sessionId);
			documentName = eidadocumentName;
			folderIndex = getFolderIndex();
			if(folderIndex!=null){
				getISIndex(eidaDocumentIndex);
			}
		}
	}
}