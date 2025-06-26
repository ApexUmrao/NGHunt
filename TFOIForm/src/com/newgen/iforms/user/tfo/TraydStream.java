package com.newgen.iforms.user.tfo;

import java.io.File;

import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;


import com.newgen.iforms.EControl;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.iforms.user.config.Constants;
import com.newgen.mvcbeans.model.WorkdeskModel;


public class TraydStream extends Common implements Constants, IFormServerEventHandler{
	boolean bSubmit = true;
	String callRequestType = ""; 
	public TraydStream(IFormReference formObject) {
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
		try {
			if (eventType.equalsIgnoreCase(EVENT_TYPE_LOAD)) {
				log.info("Inside formload event :::: >");
				formLoadData();
			} else if (controlName.equalsIgnoreCase(BUTTON_TSINQUIRY) && eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)) {
				 log.info("TS_START_CHECK starts");
					List<List<String>> sOutputlist =null;
					sOutputlist = formObject.getDataFromDB("SELECT SEQ_WEBSERVICE.nextval as IDValue from DUAL");
					String txnNumber= sOutputlist.get(0).get(0);
					String output=callTS(txnNumber);
					log.info("callTS  output= " + output);
			}else if(eventType.equalsIgnoreCase(EVENT_TYPE_CHANGE)) {
				if(DEC_CODE.equalsIgnoreCase(controlName)){
					loadReasonCombo(formObject.getValue(DEC_CODE).toString(),REJ_RESN_PPM);
				}
			}else if (controlName.equalsIgnoreCase(BUTTON_SUBMIT) && eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)){
				String processType = formObject.getValue(PROCESS_TYPE).toString();
				String requestType = formObject.getValue(REQUEST_TYPE).toString();
				String requestCategory = formObject.getValue(REQUEST_CATEGORY).toString();
				String ts_req = formObject.getValue(TS_REQUIRED).toString();
				
				log.info(" Traydstream ws"+"ProcessType " +processType +"requestType " +requestType +"requestCategory " +requestCategory +"ts_req "+ ts_req);
				TraydStreamValues = TraydStreamValueMap.get(processType + "#" + requestCategory + "#" + requestType + "#"+ts_req);
				log.info("skip traydstream flag: " + formObject.getValue(SKIP_TRAYDSTREAM).toString());
				log.info("Decision Code: " + formObject.getValue(DEC_CODE).toString());
				log.info("TraydStreamValues  :::: " + TraydStreamValues.toString());
				if (TraydStreamValues != null && !TraydStreamValues.isEmpty() && "MAPP".equalsIgnoreCase(formObject.getValue(DEC_CODE).toString())
						&& !TRUE.equalsIgnoreCase(formObject.getValue(SKIP_TRAYDSTREAM).toString())) {
					log.info("Worskstep Name ::: "+TraydStreamValues.get(6));
					if("Distribute3".equalsIgnoreCase(TraydStreamValues.get(6))) {
						String sQuery="update ext_tfo set target_workstep='Collect3' where wi_name='"+this.sWorkitemID+"'";
						log.info("Updating target Workstep  in ext table records query : "+sQuery);
						log.info("response : "+formObject.saveDataInDB(sQuery)+"");
					}
				}
				saveDecHistory();
			}
		} catch (Exception e) {
			log.error("Exception in Event- " + eventType + "control name- " +controlName+ ": ", e);
		}
		return retMsg;
	}

	public void formLoadData(){
		String winame = formObject.getValue("WI_NAME").toString();
		log.info("inside Traydstream formload event -- WorkItem Name: " + winame);
		intializeStaticValue();
		setUserDetail();
		setProperties();
		tabHandlingMast();
		loadDecisionHistoryListView();
		loadDecision("",DEC_CODE);
		loadReasonCombo(formObject.getValue(DEC_CODE).toString(),REJ_RESN_PPM);
		log.info("End of PPM Load");

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
	

	
}

