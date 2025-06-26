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

public class Repair extends Common implements Constants, IFormServerEventHandler{
	String remarks="";

	public Repair(IFormReference formObject) {
		super(formObject);
	}
	@Override
	public void beforeFormLoad(FormDef arg0, IFormReference arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String executeCustomService(FormDef arg0, IFormReference arg1,
			String arg2, String arg3, String arg4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray executeEvent(FormDef arg0, IFormReference arg1,
			String arg2, String arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String executeServerEvent(IFormReference formObject,String controlName, String eventType, String data) {
		log.info("Inside executeServerEvent PM");
		log.info("Event: " + eventType + ", Control Name: " + controlName
				+ ", Data: " + data);
		sendMessageList.clear();
		String retMsg = getReturnMessage(true);
		Boolean success = true;
		try {
			if (eventType.equalsIgnoreCase(EVENT_TYPE_LOAD)) {
				if (CONTROL_NAME_FORM.equalsIgnoreCase(controlName)) {
					boolean openJSPOnFormLoad = checkCallStatusRepair();
					if(openJSPOnFormLoad){
						return getReturnMessage(true, controlName, "openRepairJSP");
					}
				}
				  if (FRAME_DECISION_HIST.equalsIgnoreCase(controlName)) {
					log.info("loading decision history ...");
					loadDecisionHistoryListView();
				}
			}
			else  if (eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)) {
				  if (controlName.equalsIgnoreCase(BUTTON_SUBMIT) && eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)) {
					  
					  boolean openJSPOnFormLoad = checkCallStatusRepair();
						if(openJSPOnFormLoad){
							return getReturnMessage(true, controlName, "openRepairJSP");
						}else{
					       saveDecHistory( getDescriptionFromCode(DEC_CODE,formObject.getValue(DEC_CODE).toString()),remarks);
					  }
				 }
				/*  else if (controlName.equalsIgnoreCase(BUTTON_SF_CLOSE) && eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)) {
						if(!".".equalsIgnoreCase(data)){remarks=data+" created succesfully";
						log.info("remarks="+remarks);}
				  }*/
			}
		}catch (Exception e) {
			log.error("executeServerEvent Exception: ",e);
		}
			boolean sOnClick = false;
		// TODO Auto-generated method stub
		return retMsg;
	}

	@Override
	public String generateHTML(EControl arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCustomFilterXML(FormDef arg0, IFormReference arg1,
			String arg2) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String introduceWorkItemInWorkFlow(IFormReference arg0,
			HttpServletRequest arg1, HttpServletResponse arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String introduceWorkItemInWorkFlow(IFormReference arg0,
			HttpServletRequest arg1, HttpServletResponse arg2,
			WorkdeskModel arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setMaskedValue(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return arg1;
	}

	@Override
	public void updateDataInWidget(IFormReference arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String validateDocumentConfiguration(String arg0, String arg1,
			File arg2, Locale arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray validateSubmittedForm(FormDef arg0, IFormReference arg1,
			String arg2) {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean checkCallStatusRepair(){
		try {
			log.info("inside checkCallStatus >>");
			String query = "select max(count_1) from ( select count(1) as count_1 "
					+ "from bpm_orchestration_status where wi_name='"+this.sWorkitemID+"' "
							+ "and  call_status='N'  union select count(1) "
							+ "as count_1 from TFO_DJ_INTEGRATION_CALLS where "
							+ "call_operation='Linked_Workitem_Creation' and "
							+ " wi_name='"+this.sWorkitemID+"'and call_status='N') a";
			List<List<String>> list = formObject.getDataFromDB(query);
			int count = Integer.parseInt(list.get(0).get(0));
			log.info("call failed count: "+count);
			if(count > 0){
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			log.error("exception in checkCallStatus: ",e);
		}
		return false;
	}
}
