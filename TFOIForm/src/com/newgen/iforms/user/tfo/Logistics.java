package com.newgen.iforms.user.tfo;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;

import com.newgen.iforms.EControl;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.iforms.user.config.Constants;
import com.newgen.mvcbeans.model.WorkdeskModel;

import ISPack.CPISDocumentTxn;
import ISPack.ISUtil.JPISException;
import Jdts.DataObject.JPDBString;

public class Logistics extends Common implements Constants, IFormServerEventHandler{
	boolean bSubmit = true;
	String callRequestType = ""; 
	private String tsTxnId ="";
	private boolean bCallTraydstreamRestService = false; //Traydstream |reyaz|atp-519|17-09-2024| start
	private boolean bcallStatus = false; //Traydstream |reyaz|atp-519|17-09-2024| start
	public Logistics(IFormReference formObject) {
		super(formObject);
	}
	
	@Override
	public void beforeFormLoad(FormDef arg0, IFormReference formObject) {
		log.info("Inside beforeFormLoad >>>");
		String workitemName = formObject.getObjGeneralData().getM_strProcessInstanceId();
		log.info("WorkItem Name: " + workitemName);		
	}

	@Override
	public String executeCustomService(FormDef arg0, IFormReference arg1, String arg2, String arg3, String arg4) {
		return null;
	}

	@Override
	public JSONArray executeEvent(FormDef arg0, IFormReference arg1, String arg2, String arg3) {
		return null;
	}

	@Override
	public String executeServerEvent(IFormReference formObject, String controlName, String eventType, String data) {
		log.info("Inside executeServerEvent >");
		log.info("Event: " + eventType + ", Control Name: " + controlName + ", Data: " + data);
		String retMsg = getReturnMessage(true);
		String msg = "";
		Boolean success = true;
		
		try {
			if (eventType.equalsIgnoreCase(EVENT_TYPE_LOAD)) {
				if (CONTROL_NAME_FORM.equalsIgnoreCase(controlName)) {
					log.info("inside on form load Logistics >>>>");
					String wiName = formObject.getValue("WI_NAME").toString();
					log.info("inside form load event -- WorkItem Name: " + wiName);
					Boolean view = true;
					view = setUserDetail();
					formObject.applyGroup(CONTROL_SET_DISABLE_TEMP_TXT);
					formObject.applyGroup(CONTROL_SET_DISABLE_STATIC_INTRO);
					formObject.applyGroup(CONTROL_SET_DISABLE_STATIC_LOGIS_LOV);
					formObject.setStyle(TRANSACTION_AMOUNT, "mandatory", "false");
					log.info("view: "+view);
					if ("IFS".equalsIgnoreCase(requestCategory)|| "IFP".equalsIgnoreCase(requestCategory)|| "IFA".equalsIgnoreCase(requestCategory) )  {
						log.info("inside reqCat");
						if("LD".equalsIgnoreCase(requestType) || "IFA_CTP".equalsIgnoreCase(requestType)) {
							showUTCDetails();  //added by reyaz 5082022
						}}
					loadRequestCategory();
					if(Boolean.FALSE.equals(view)) {
						formObject.applyGroup(CONTROL_SET_READONLY);
					}
					retMsg = getReturnMessage(view, controlName);
				} else if (FRAME_DECISION_HIST.equalsIgnoreCase(controlName)) {
					log.info("loading decision history ...");
					loadDecisionHistoryListView();
				}
			} else if (controlName.equalsIgnoreCase(BUTTON_SUBMIT) && eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)
					&& bSubmit) {
				Boolean callExecution = true; // Traydstream |reyaz|atp-519|17-09-2024|
				callRequestType = "";
				if (bSubmit) {
					bSubmit = false;
					formObject.setStyle(BUTTON_SUBMIT, "disable", "true");
					// Traydstream |reyaz|atp-519|17-09-2024| start
					callExecution = callTraydstreamRestService();
					if (callExecution) {
						log.info("WF Done Called stopped for a while");
						log.info("callRequestType: " + callRequestType);
						return getReturnMessage(true, controlName, callRequestType);
					}
					// Traydstream |reyaz|atp-519|17-09-2024| end

					String[] retVal = docCheck(formObject.getValue(REQUEST_TYPE).toString()).split("#");
					if (retVal[0].equalsIgnoreCase("true")) {
						formObject.setValue(DEC_CODE, "App");
						formObject.setValue(DECISION, "Accept");
						log.info("wi name: " + sWorkitemID);
						List<String> paramlist = new ArrayList<>();
						paramlist.add("Text :" + sWorkitemID);
						paramlist.add("Text :N");
						log.info("calling procedure TFO_DJ_EMAIL_CONFIG from logistics submit button..");
						List<List<String>> statusProc = formObject.getDataFromStoredProcedure("TFO_DJ_EMAIL_CONFIG",
								paramlist);
						log.info("getDataFromStoredProcedure TFO_DJ_EMAIL_CONFIG list returned: " + statusProc);
						saveDecHistory();
						success = true;
						retMsg = getReturnMessage(success);
						return retMsg;
					} else {
						bSubmit = true;
						formObject.setStyle(BUTTON_SUBMIT, "disable", "false");
						success = false;
						msg = retVal[1];
						retMsg = getReturnMessage(success, controlName, msg);
						return retMsg;
					}
				}
			}
		} catch (Exception e) {
			log.error("Exception in Event- " + eventType + "control name- " +controlName+ ": ", e);
		}
		return retMsg;
	}

	@Override
	public String generateHTML(EControl arg0) {
		return null;
	}

	@Override
	public String getCustomFilterXML(FormDef arg0, IFormReference arg1, String arg2) {
		return "";
	}

	@Override
	public String introduceWorkItemInWorkFlow(IFormReference arg0, HttpServletRequest arg1, HttpServletResponse arg2) {
		return null;
	}

	@Override
	public String introduceWorkItemInWorkFlow(IFormReference arg0, HttpServletRequest arg1, HttpServletResponse arg2,
			WorkdeskModel arg3) {
		return null;
	}

	@Override
	public String setMaskedValue(String arg0, String arg1) {
		return arg1;
	}

	@Override
	public JSONArray validateSubmittedForm(FormDef arg0, IFormReference arg1,
			String arg2) {
		return null;
	}

	@Override
	public void updateDataInWidget(IFormReference arg0, String arg1) {
		//unused
	}

	@Override
	public String validateDocumentConfiguration(String arg0, String arg1, File arg2, Locale arg3) {
		return null;
	}
	
	// Traydstream |reyaz|atp-519|17-09-2024| start
	public Boolean callTraydstreamRestService() {
		log.info("callTraydstreamRestService");
		bCallTraydstreamRestService = false;
		List<List<String>> sOutput;
		String processType = formObject.getValue(PROCESS_TYPE).toString();
		String requestType = formObject.getValue(REQUEST_TYPE).toString();
		String requestCategory = formObject.getValue(REQUEST_CATEGORY).toString();
		String ts_req = formObject.getValue(TS_REQUIRED).toString();
		log.info("callTraydstreamRestService"+"ProcessType " +processType +"requestType " +requestType +"requestCategory " +requestCategory +"ts_req "+ ts_req);
		try {
			TraydStreamValues = TraydStreamValueMap.get(processType + "#" + requestCategory + "#" + requestType + "#"+ts_req);
			if (TraydStreamValues != null && !TraydStreamValues.isEmpty()) {
				log.info("skip traydstream flag: " + formObject.getValue(SKIP_TRAYDSTREAM).toString());
				if (FALSE.equalsIgnoreCase(formObject.getValue(SKIP_TRAYDSTREAM).toString())) {
					String sQuery = "select versioncomment,appname from PDBDOCUMENT  where   documentindex in ("
							+ "select documentindex from PDBDOCUMENTCONTENT where parentfolderindex="
							+ "(select itemindex from ext_tfo where wi_name='" + sWorkitemID + "')" + ") and  NAME in ("
							+ TraydStreamValues.get(5)
							+ ") and versioncomment not in (select vessel_name from TFO_DJ_INTEGRATION_CALLS where "
							+ "wi_name ='" + sWorkitemID + "' and call_status ='Y' and CALL_OPERATION ='Traydstream_Oper')";
					log.info("sQuery is :******" + sQuery);
					sOutput = formObject.getDataFromDB(sQuery);
					log.info("sOutput is :******" + sOutput);
					if (sOutput != null && sOutput.size() > 0) {
						String sInsertDeleteScript = "DELETE FROM TFO_DJ_INTEGRATION_CALLS WHERE WI_NAME = '" + sWorkitemID
								+ "' and call_status in ('N','F') and CALL_OPERATION ='Traydstream_Oper' ";
						formObject.saveDataInDB(sInsertDeleteScript);
						log.info("Data deleted");
						for (int i = 0; i < sOutput.size(); i++) {
							log.info("***************Inside Traydstream toInsertIntegrationCalls******************");
							String columnNames = "SNO,WI_NAME,VESSEL_NAME,CALL_OPERATION,CALL_DT,CALL_STATUS,RETRY_COUNT,CALL_NAME";
							sInsertDeleteScript = "INSERT INTO TFO_DJ_INTEGRATION_CALLS (" + columnNames + ") VALUES (1,'"
									+ sWorkitemID + "','" + sOutput.get(i).get(0) + "',"
									+ "'Traydstream_Oper',SYSTIMESTAMP,'N','0','" + sOutput.get(i).get(0)
									+ "~" + sOutput.get(i).get(1) + "~TSSubmitDocument')";
							log.info("[sInsertScript for callTraydstreamRestService ]" + sInsertDeleteScript);
							formObject.saveDataInDB(sInsertDeleteScript);
							log.info("Data Inserted");
						}
						executeTSSubmitCall();
						log.info("bcallStatus :::::  "+bcallStatus);
						if(bcallStatus) {
							bCallTraydstreamRestService = false;
						}else {
							bCallTraydstreamRestService = true;
							callRequestType = "A";
						}
						sQuery="update ext_tfo set target_workstep='"+TraydStreamValues.get(6)+"' where wi_name='"+this.sWorkitemID+"'";
						log.info("Updating target Workstep  in ext table records query : "+sQuery);
						log.info("response : "+formObject.saveDataInDB(sQuery)+"");
					}
				}
				else {
					insertInTFODecistory("TSSubmitDocument","skip traydstream flag :" + formObject.getValue(SKIP_TRAYDSTREAM) + "","Reason for Skip : " + formObject.getValue(TS_JUSTIFICATION) + "");
				}
			}
		} catch (Exception e) {
			log.error("exception in callTraydstreamRestService: ", e);
		}
		return bCallTraydstreamRestService;
	}
	
	public void executeTSSubmitCall() {
	    log.info("inside executeTSSubmitCall >>");
	    List<List<String>> sOutput;
	    String sQuery = "SELECT CALL_NAME FROM TFO_DJ_INTEGRATION_CALLS WHERE WI_NAME = '"+sWorkitemID+"' AND CALL_STATUS = 'N' AND CALL_OPERATION = 'Traydstream_Oper'";
	    log.info("sQuery is :******" + sQuery);
	    sOutput = formObject.getDataFromDB(sQuery);
	    log.info("sOutput is :******" + sOutput);
	    if (sOutput != null && !sOutput.isEmpty()) {
	        log.info("Inside ");
	    	for (int i = 0; i < sOutput.size(); i++) {
	            String[] data = sOutput.get(i).get(0).split("~");
	            String docFileName = data[0];
	            String docExtensionType = data[1];
	    	    log.info("docFileName :******" + docFileName);
	    	    log.info("docExtensionType :******" + docExtensionType);
	            try {
	                downloadDocuments(docFileName, docExtensionType, sOutput.get(i).get(0),i);
	            } catch (Exception e) {
	                log.error("Exception in Download Document: ", e);
	            }
	        }
	    }
	}

	public void downloadDocuments(String docFileName, String docExtensionType, String sCallName,int i) throws Exception {
	    List<List<String>> sOutput;
	    log.info("Inside downloadDocuments");
	    String sQuery = "SELECT a.DOCUMENTINDEX, a.IMAGEINDEX, a.VOLUMEID, a.NOOFPAGES, a.DOCUMENTSIZE, a.APPNAME, a.VERSIONCOMMENT, a.NAME " +
	                    "FROM PDBDOCUMENT a WHERE documentindex IN (SELECT documentindex FROM PDBDOCUMENTCONTENT WHERE parentfolderindex = "
	                    + "(SELECT itemindex FROM ext_tfo WHERE wi_name = '"+sWorkitemID+"') AND a.NAME IN ("+TraydStreamValues.get(5)+")) " +
	                    "AND a.versioncomment = '"+docFileName+"' AND a.appname = '"+docExtensionType+"' AND rownum = 1";
	    log.info("Inside downloadDocuments sQuery "+sQuery);
	    sOutput = formObject.getDataFromDB(sQuery);
	    log.info("Inside downloadDocuments sOutput "+sOutput);
	    if (sOutput != null && !sOutput.isEmpty()) {
	        String imageIndex = sOutput.get(0).get(1);
	        String volumeID = sOutput.get(0).get(2);
	        String appname = sOutput.get(0).get(5);
	        String docName = sOutput.get(0).get(6);
	        String folderPath = bpmConfigValueMap.get("DOCDOWNLOADPATH") + sWorkitemID;
	        String downLoadLocation = folderPath + File.separator + docName.trim() + "." + appname;
	        // Ensure directory exists
	        File file = new File(folderPath);
	        if (!file.exists() && !file.mkdirs()) {
	            log.error("Failed to create directory: " + folderPath);
	            return;
	        }

	        try {
	            log.info("Document download started:");
	            CPISDocumentTxn.GetDocInFile_MT(null, bpmConfigValueMap.get("JTSIP"), Short.parseShort(bpmConfigValueMap.get("JTSPORT")),
	                    bpmConfigValueMap.get("CABINETNAME"), Short.parseShort(bpmConfigValueMap.get("SITEID")), Short.parseShort(volumeID),
	                    Integer.parseInt(imageIndex), "", downLoadLocation, new JPDBString());
	            log.info("Document downloaded successfully:");
	            processDownloadedDocument(downLoadLocation, sCallName,docName,appname,i);
	        } catch (JPISException e) {
	            log.error("Exception in Download Document: ", e);
	        }
	    }
	}

	private void processDownloadedDocument(String downLoadLocation, String sCallName, String docName,String appname,int i) throws Exception {
	    byte[] inFileBytes = Files.readAllBytes(Paths.get(downLoadLocation));
	    String base64 = Base64.getEncoder().encodeToString(inFileBytes);
	    String jsonInput = createJsonInput(base64,docName,appname);
	    String uUid=UUID.randomUUID().toString();
	    insertInIntegrationDtlsTable(jsonInput,uUid);
	    String restResponse = executeRestAPIJSON(bpmConfigValueMap.get("CASE_CREATION_URL"), jsonInput,uUid);
	    handleJasonResponse(restResponse, sCallName, downLoadLocation,uUid);
	}

	private String createJsonInput( String base64, String docName,String appname) {
		 log.info("lc refID ::"+formObject.getValue("TRANSACTION_ADCB_LC_REFERENCE").toString());
		 String lcrefId =formObject.getValue("TRANSACTION_ADCB_LC_REFERENCE").toString();
		 lcrefId="";
	    return "{\n" +
	           "    \"txnId\": \"" + tsTxnId + "\",\n" +
	           "    \"lcRefId\": \"" + lcrefId + "\",\n" +
	           "    \"uploadsCount\": \"" + TraydStreamValues.get(3) + "\",\n" +
	           "    \"tenantId\": \"" + TraydStreamValues.get(2) + "\",\n" +
	           "    \"clientData\": \"{\\\"clientRefId\\\": \\\"" + sWorkitemID + "\\\","
	           		+ "\\\"secondClientRefId\\\": \\\""+formObject.getValue("PT_ASSIGNED_MAKER").toString()+"\\\","
	           		+ "\\\"branchId\\\": \\\"" + TraydStreamValues.get(1) + "\\\","
	           		+ "\\\"source\\\": \\\"" + TraydStreamValues.get(0) + "\\\"}\",\n" +
	           "    \"txnType\": \"" + TraydStreamValues.get(4) + "\",\n" +
	           "    \"userProfile\": \"{\\\"email\\\" : \\\"" + sUserName + "\\\"}\",\n" +
	           "    \"data\": \"" + base64 + "\",\n" +
	           "    \"fileName\": \"" + docName.trim() + "." + appname + "\"\n" +
	           "}";
	}

	public void handleJasonResponse(String outputJson, String sCallName, String downLoadLocation,String uUid) {
	    String  sErrorDescription = "";
	    try {
	        if (!outputJson.contains("Error") && !outputJson.isEmpty() && outputJson!=null) {
	            tsTxnId = getTagValueJSON(outputJson, "txnId");
	            sErrorDescription = getTagValueJSON(outputJson, "desc");
	            updateIntegrationRecords("Y", sErrorDescription, sCallName, outputJson,uUid);
	            deleteDirectory(downLoadLocation);
	            bcallStatus= true;
	        } else {
	        	if(outputJson.contains("ErrorCode")) {
	            sErrorDescription = getTagValueJSON(outputJson, "ErrorCode");
	        	}else {
	        		sErrorDescription=outputJson;
	        	}
	            updateIntegrationRecords("F",  sErrorDescription, sCallName, outputJson,uUid);
	            deleteDirectory(downLoadLocation);
	        }
	    } catch (Exception e) {
	        log.error("Error in handling JSON response: ", e);
	    }
	}

	private void updateIntegrationRecords(String status,  String sErrorDescription, String sCallName, String outputJson,String uUid) {
		
		String query = "UPDATE EXT_TFO SET TSTXNID = '"+tsTxnId+"' WHERE WI_NAME = '"+sWorkitemID+"'";
		formObject.saveDataInDB(query);
	    query = "UPDATE TFO_DJ_INTEGRATION_CALLS SET CALL_STATUS = '"+status+"', ERROR_DESCRIPTION = '"+sErrorDescription+"' "
	    		       + "WHERE WI_NAME = '"+sWorkitemID+"' AND CALL_NAME = '"+sCallName+"'";
	    formObject.saveDataInDB(query);
	    query = "UPDATE TFO_DJ_INTEGRATION_CALLS_DTLS SET OUTPUT_XML = "+convertToPlainString(outputJson)+", REF_NUM =sysdate"
	    		+ "  WHERE WI_NAME = '"+sWorkitemID+"' AND VESSEL_NAME = "+uUid+"";
	    formObject.saveDataInDB(query);
	    insertInTFODecistory("TSSubmitDocument",sErrorDescription,"TsRefNo : " + formObject.getValue("TSTXNID") + "");
	}

	public void insertInIntegrationDtlsTable(String outputJson,String uUid) {
	    try {
	        log.info("TSSubmitDocument: insertInIntegrationDtlsTable");
	        String valList = "'" + sWorkitemID + "','" + uUid + "', 'TSSubmitDocument', sysdate, " + convertToPlainString(outputJson) + "";
	    	String query = "INSERT INTO TFO_DJ_INTEGRATION_CALLS_DTLS (WI_NAME,VESSEL_NAME, CALL_NAME, REF_NUM, CALL_XML) "
	    			+ "VALUES ("+valList+")";
	        log.info("TSSubmitDocument: insertInIntegrationDtlsTable query  "+query);
	    	formObject.saveDataInDB(query);
	    } catch (Exception e) {
	        log.error("Error inserting in integration details table: ", e);
	    }
	}

	public void insertInTFODecistory(String action,String resonForAction,String remarks) {
	    try {
	        log.info("TSSubmitDocument: insertInTFODecistory");
	        String sValues = "'" + sWorkitemID + "', '" + sUserName + "', '" + formObject.getValue("PREV_WS") + "',"
	        		+ "'" + formObject.getValue("CURR_WS") + "', sysdate, '" + sUserName + "', '"+action+"', "
	        		+ "'" + resonForAction + "', sysdate, '" + remarks+ "'";
	        String Column ="WI_NAME, USER_ID, PRV_WS_NAME, CURR_WS_NAME, CREATE_DATE, USERNAME, ACTION, REASON_FOR_ACTION,TRANSACTION_DATE_TIME, REMARKS";
	        String query = "INSERT INTO TFO_DJ_DEC_HIST ("+Column+") VALUES ("+sValues+")";
	        log.info("TSSubmitDocument: insertInTFODecistory query  "+query);
	    	formObject.saveDataInDB(query);
	    } catch (Exception e) {
	        log.error("Error inserting in TFO decistory: ", e);
	    }
	}
	//Traydstream |reyaz|atp-519|17-09-2024| End
	
}
