package com.newgen.iforms.user.tfo;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

import com.newgen.iforms.EControl;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.iforms.user.config.Constants;
import com.newgen.mvcbeans.model.WorkdeskModel;

public class ReadOnly  extends Common implements Constants, IFormServerEventHandler{
	String retMsg = ""; 
	public ReadOnly(IFormReference formObject) {
		super(formObject);
	}

	@Override
	public void beforeFormLoad(FormDef arg0, IFormReference arg1) {
		//unused
	}

	@Override
	public String executeCustomService(FormDef arg0, IFormReference arg1,
			String arg2, String arg3, String arg4) {

		return null;
	}

	@Override
	public JSONArray executeEvent(FormDef arg0, IFormReference arg1,
			String arg2, String arg3) {

		return null;
	}

	@Override
	public String executeServerEvent(IFormReference formObject, String controlName, String eventType, String data) {
		log.info("Inside executeServerEvent Read Only > :"+sWorkitemID);
		sendMessageList.clear();
		log.info("Event: " + eventType + ", Control Name: " + controlName + ", Data: " + data);
		log.info("Event: " + eventType + ", Control Name: " + controlName + ", Data: " + data);

		retMsg = getReturnMessage(true);
		try {
				
			if (eventType.equalsIgnoreCase(EVENT_TYPE_LOAD)) {
				if (FRAME_DECISION_HIST.equalsIgnoreCase(controlName)) {
					log.info("loading decision history ...");
					loadDecisionHistoryListView();
				} //Traystream change |reyaz|atp-518|28-10-2024  start  
				else if(CONTROL_NAME_FORM.equalsIgnoreCase(controlName) && sActivityName.equalsIgnoreCase("Trayd Stream")) {
					String wiName = formObject.getValue(TFO_WI_NAME).toString();
					log.info("Inside form load event -> WorkItem Name: " + wiName);
					TraydStream ts_obj = new TraydStream(formObject); //Read_only_change
					ts_obj.formLoadData();
				}  //Traystream change |reyaz|atp-518|28-10-2024  end  
				else if(CONTROL_NAME_FORM.equalsIgnoreCase(controlName)) {
					String wiName = formObject.getValue(TFO_WI_NAME).toString();
					log.info("Inside form load event -> WorkItem Name: " + wiName);
					intializeStaticValue();
					loadProdLov(srcChnl, relType, listPrdCode1, PRODUCT_TYPE);
					loadDecisionList();
					log.info("Inside form load event -setDataInTRSDTab -> WorkItem Name: " + wiName);
					setDataInTRSDTab(); 
					PPM ppm_obj = new PPM(formObject); //Read_only_change
					ppm_obj.formLoadData();
				} 
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		return retMsg;
	}

	@Override
	public String generateHTML(EControl arg0) {
		return null;
	}

	@Override
	public String getCustomFilterXML(FormDef arg0, IFormReference arg1,
			String arg2) {
		return "";
	}

	@Override
	public String introduceWorkItemInWorkFlow(IFormReference arg0,
			HttpServletRequest arg1, HttpServletResponse arg2) {
		return null;
	}

	@Override
	public String introduceWorkItemInWorkFlow(IFormReference arg0,
			HttpServletRequest arg1, HttpServletResponse arg2,
			WorkdeskModel arg3) {
		return null;
	}

	@Override
	public String setMaskedValue(String arg0, String arg1) {
		return arg1;
	}

	@Override
	public void updateDataInWidget(IFormReference arg0, String arg1) {
		//unused
	}

	@Override
	public String validateDocumentConfiguration(String arg0, String arg1, File arg2, Locale arg3) {
		return null;
	}

	@Override
	public JSONArray validateSubmittedForm(FormDef arg0, IFormReference arg1, String arg2) {
		return null;
	}

	private void loadDecisionList(){
		try{
			String sDec ="";
			sDec = formObject.getValue(DEC_CODE).toString();
			log.info("inside for loop load decision:");
			formObject.clearCombo(DEC_CODE);
			for(Entry<String, LinkedHashMap<String, String>> entryLinked: decisionMap.entrySet()){
				log.info("inside for loop load decision: "+entryLinked);
				for(Map.Entry<String, String> entry: entryLinked.getValue().entrySet()){
					log.info("inside for loop load decision: ");
					log.info("description: "+entry.getValue()+", code:"+entry.getKey());
					formObject.addItemInCombo(DEC_CODE, entry.getValue(),entry.getKey());
					List<String> tempList=new ArrayList<>();
					tempList.add(entry.getKey());
					tempList.add(entry.getValue());
					lstDec.add(tempList);	
				}
			}
			log.info("lstDec list="+lstDec.size());
			formObject.setValue(DEC_CODE, sDec);
		}catch(Exception e){
			log.error("Eception in",e);
		}
	}
}
