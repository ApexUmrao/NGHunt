package com.newgen.iforms.user.ao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import org.json.simple.JSONArray;

import com.newgen.iforms.EControl;
import com.newgen.iforms.user.ao.mandatorycheck.*;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.iforms.user.config.Constants;
import com.newgen.mvcbeans.model.WorkdeskModel;

public class Introduction extends Common implements Constants, IFormServerEventHandler{

	

	public Introduction(IFormReference formObject) {
		super(formObject);
	}

	@Override
	public void beforeFormLoad(FormDef arg0, IFormReference arg1) {
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
		List<List<String>> list;
		String sQuery = "";
		String sWMSID ="";
		String sID = "";
		String sServiceShortName = "";
		String sHomeBrShortName = "";
		sendMessageList.clear();
		logInfo("executeServerEvent","sendmessagelist49: "+sendMessageList);
		String retMsg = getReturnMessage(true);
		try {
			if(eventType.equalsIgnoreCase(EVENT_TYPE_LOAD)) {
				setIntroData();
				if(formObject.getValue("IS_INITIATED_UAE_PASS").toString().equalsIgnoreCase("Y")){
					setBankRelnData();
				}
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)) {
				if(controlName.equalsIgnoreCase(BTN_SAVE_NEXT)) {	
					formObject.setStyle(BTN_SAVE_NEXT,DISABLE,TRUE);
					logInfo("executeServerEvent","sendmessagelist57: "+sendMessageList);
					if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Family Banking")) {
						createHistory();
						createAllHistory();
						return getReturnMessage(true,"btnSubmit");
					}
					List result = IntroductionMandatoryCheck.mandatoryAtIntroduction(formObject);
					logInfo("executeServerEvent","Try catch Start");
					//logInfo("executeServerEvent",e.toString());
					try {
						sendMessageList = result;
						logInfo("executeServerEvent","sendmessagelist: "+sendMessageList);
						logInfo("executeServerEvent","Inside try");
						logInfo("executeServerEvent","BTN_SAVE_NEXT result now---"+result);
						logInfo("executeServerEvent","After result");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						logInfo("executeServerEvent",e.toString());
					}
					logInfo("executeServerEvent","Try catch After");
					if(!result.isEmpty()){
						formObject.setStyle(BTN_SAVE_NEXT,DISABLE,FALSE);
						logInfo("executeServerEvent","sendmessagelist2: "+sendMessageList);
					} else {
						if(formObject.getValue(WMS_ID).toString().equalsIgnoreCase("")) {
							sQuery = "SELECT SEQ_WMSID.nextval as ID from DUAL ";
							log.info("query : "+sQuery);
							list = formObject.getDataFromDB(sQuery);
							if (list != null && !list.isEmpty()) {
								log.info("ID : "+list.get(0).get(0));
								sID = list.get(0).get(0);
							}
							sQuery = "SELECT SHORT_NAME FROM USR_0_SERVICE_CHANNEL WHERE SERVICE_CHANNEL = '"+formObject.getValue(SOURCING_CHANNEL).toString()+"'";
							log.info("sQuery : "+ sQuery);
							list = formObject.getDataFromDB(sQuery);
							if (list != null && !list.isEmpty()){
								log.info("sServiceShortName : "+list.get(0).get(0));
								sServiceShortName =  list.get(0).get(0); 
							}
							sQuery = "SELECT SHORT_NAME FROM USR_0_HOME_BRANCH WHERE HOME_BRANCH = '"+formObject.getValue(ACC_HOME_BRANCH).toString()+"'";
							log.info("sQuery : "+ sQuery);
							list = formObject.getDataFromDB(sQuery);
							if (list != null && !list.isEmpty()){
								log.info("sHomeBrShortName : "+list.get(0).get(0));
								sHomeBrShortName =  list.get(0).get(0); 
							}	
							 
							if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only"))
							{
								sWMSID = sWMSID+"CC-";
							}
							else
							{
								sWMSID = sWMSID+"AO-";
							}
							
							sWMSID = (sWMSID+sServiceShortName+"-"+sHomeBrShortName+"-"+sID).toUpperCase();
							
							log.info("WMS ID---"+sWMSID);
							if(sWMSID.length()<8)
							{
								sendMessageValuesList("","Error In Calculating WMS ID");
								formObject.setStyle(BTN_SAVE_NEXT,DISABLE,FALSE);
								if(!sendMessageList.isEmpty()){
									return  getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1).toString());
								}
							} else {
							String sUpdateCreateDate="update "+sExtTable+" set CREATEDATE=sysdate Where WI_NAME='"+ sWorkitemId +"'";
							formObject.saveDataInDB(sUpdateCreateDate);
							createHistory();
							createAllHistory();
							formObject.setValue(WMS_ID,sWMSID);
							}
						}
						log.info("return alert message");
						return getReturnMessage(true,"btnSubmit","Your WMS ID Is "+formObject.getValue(WMS_ID).toString()).toString();
					}
				}
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_CHANGE)) {
				if(controlName.equalsIgnoreCase(DATA_ENTRY_MODE)){
					if(formObject.getValue(DATA_ENTRY_MODE).toString().equalsIgnoreCase("Detail Data Entry"))
					{
						formObject.setValue(FORM_AUTO_GENERATE, "Yes");
						formObject.setStyle(FORM_AUTO_GENERATE,DISABLE,FALSE);
					} else if(formObject.getValue(DATA_ENTRY_MODE).toString().equalsIgnoreCase("Quick Data Entry"))
					{
						formObject.setValue(FORM_AUTO_GENERATE, "No");
						formObject.setStyle(FORM_AUTO_GENERATE,DISABLE,TRUE);
					} else
					{
						formObject.setStyle(FORM_AUTO_GENERATE,DISABLE,FALSE);
					}
				} else if(controlName.equalsIgnoreCase(REQUEST_TYPE)) {
					if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only"))
					{
						formObject.setValue(ACC_OWN_TYPE, "Single");
						formObject.setStyle(ACC_OWN_TYPE,DISABLE,TRUE);
						formObject.setValue(ACC_CLASS, "");
						formObject.setStyle(ACC_CLASS,DISABLE,TRUE);
					} else {
						formObject.setStyle(ACC_OWN_TYPE,DISABLE,FALSE);
						formObject.setStyle(ACC_CLASS,DISABLE,FALSE);
						formObject.setValue(DATA_ENTRY_MODE, "");
						formObject.setStyle(DATA_ENTRY_MODE,DISABLE,FALSE);
						  if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Downgrade")){//Downgrade changes
							  formObject.setValue(DATA_ENTRY_MODE, "Quick Data Entry");
							  formObject.setStyle(DATA_ENTRY_MODE,DISABLE,TRUE);
							  formObject.setValue(FORM_AUTO_GENERATE, "No");
							  formObject.setStyle(FORM_AUTO_GENERATE,DISABLE,TRUE);
							  formObject.setStyle(ACC_OWN_TYPE,DISABLE,TRUE);
							  formObject.setStyle(ACC_CLASS,DISABLE,TRUE);
						  }else if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade")){ //Added by shivanshu for upgrade new
							  formObject.setValue(DATA_ENTRY_MODE, "Detail Data Entry");
							  formObject.setStyle(DATA_ENTRY_MODE,DISABLE,TRUE);
							  formObject.setValue(FORM_AUTO_GENERATE, "Yes");
							  formObject.setValue(ACC_OWN_TYPE, "Single");
							  formObject.setStyle(ACC_OWN_TYPE,DISABLE,TRUE);
							  formObject.setValue(ACC_CLASS, "");
							  formObject.setStyle(ACC_CLASS,DISABLE,TRUE);
						  }
					}
					if(formObject.getValue(SOURCING_CHANNEL).toString().equalsIgnoreCase("DFC")) {
						formObject.setValue(DATA_ENTRY_MODE, "Detail Data Entry");
						formObject.setStyle(DATA_ENTRY_MODE,DISABLE,TRUE);
					}
				} else if(controlName.equalsIgnoreCase(SOURCING_CHANNEL)) {
					String pControlValue = formObject.getValue(SOURCING_CHANNEL).toString();
					if(pControlValue.equalsIgnoreCase("Branch-Excellency")) {
						formObject.clearCombo("SOURCING_CENTER");
						addDataInComboFromQuery("SELECT SOURCING_CENTER FROM USR_0_SOURCING_CENTER WHERE SOURCING_CHANNEL ='Excellency' ORDER BY TO_NUMBER(UNIQUE_ID)","SOURCING_CENTER");
						formObject.setStyle(ACC_HOME_BRANCH,DISABLE,FALSE);
					} else if(pControlValue.equalsIgnoreCase("sales")) {
						addDataInComboFromQuery("SELECT SOURCING_CENTER FROM USR_0_SOURCING_CENTER WHERE SOURCING_CHANNEL ='Sales' ORDER BY TO_NUMBER(UNIQUE_ID)","SOURCING_CENTER");
						formObject.setStyle(ACC_HOME_BRANCH,DISABLE,FALSE);
					} else if(pControlValue.equalsIgnoreCase("Private Center")) {
						formObject.clearCombo("SOURCING_CENTER");
						addDataInComboFromQuery("SELECT SOURCING_CENTER FROM USR_0_SOURCING_CENTER WHERE SOURCING_CHANNEL ='Private Center' ORDER BY TO_NUMBER(UNIQUE_ID)","SOURCING_CENTER");
						formObject.setStyle(ACC_HOME_BRANCH,DISABLE,FALSE);
					}  else if(pControlValue.equalsIgnoreCase("Emirati")){
						formObject.clearCombo("SOURCING_CENTER");
						addDataInComboFromQuery("SELECT SOURCING_CENTER FROM USR_0_SOURCING_CENTER WHERE SOURCING_CHANNEL ='Emirati' ORDER BY TO_NUMBER(UNIQUE_ID)","SOURCING_CENTER");
						formObject.setStyle(ACC_HOME_BRANCH,DISABLE,FALSE);
					} else if(pControlValue.equalsIgnoreCase("DFC")) {
						formObject.clearCombo("SOURCING_CENTER");
						addDataInComboFromQuery("SELECT SOURCING_CENTER FROM USR_0_SOURCING_CENTER WHERE SOURCING_CHANNEL ='DFC' ORDER BY TO_NUMBER(UNIQUE_ID)","SOURCING_CENTER");
						formObject.setStyle(ACC_HOME_BRANCH,DISABLE,FALSE);
						formObject.setValue(DATA_ENTRY_MODE, "Detail Data Entry");
						formObject.setStyle(DATA_ENTRY_MODE,DISABLE,TRUE);
						formObject.setValue(FORM_AUTO_GENERATE, "Yes");
						formObject.setStyle(FORM_AUTO_GENERATE,DISABLE,TRUE);
					} else {
						formObject.clearCombo("SOURCING_CENTER");
						formObject.setStyle("SOURCING_CENTER",DISABLE,TRUE);
						formObject.setStyle(ACC_HOME_BRANCH,DISABLE,TRUE);
					}
				}
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_LOSTFOCUS)) {  

			}
		} catch (Exception e) {
			log.error("Exception in Event- " + eventType + "control name- " +controlName+ ": ", e);
		} finally{
			logInfo("executeserverevent","Finaaly = "+sendMessageList);
			if(!sendMessageList.isEmpty()){
				return  getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1).toString());
			}
		}
		return retMsg;
	}


	
	public void setAccountTypeData(){
		String sQuery ="SELECT IS_INITIATED_UAE_PASS FROM EXT_AO where WI_NAME = '"+sWorkitemId+"'";
		List<List<String>> recordList = formObject.getDataFromDB(sQuery);
		logInfo("setBankRelnData","setBankRelnData: "+recordList);
		if(recordList.size() != 0 && recordList!=null){
			logInfo("setBankRelnData","setBankRelnData: inside ize ");
				logInfo("setBankRelnData","setBankRelnData: inside for "+recordList.get(0).get(0).toString());
				String isInitiate = recordList.get(0).get(0).toString();
				if(isInitiate.equalsIgnoreCase("Y")){
					formObject.setValue("ACC_OWN_TYPE",isInitiate);
				}
				
			}
	}
	
	private void setIntroData() {
		String sQuery = "";
		List<List<String>> list;
		try {
			sQuery ="SELECT USERNAME,BRANCH,REGION,AREA,DEPARTMENT,SALES,CPD FROM WFFILTERTABLE WHERE UPPER(USERNAME)=UPPER('"+sUserName+"') AND PROCESSDEFID='"+sProcessDefId+"'";
			log.info("query="+sQuery);
			list= formObject.getDataFromDB(sQuery);
			log.info("list="+list);
			if (list != null && !list.isEmpty()) {
				for(int i=0;i<list.size();i++){
					/*String sChannel= list.get(i).get(4).toString();
					if(sChannel.equals("BRANCH")) {
						sChannel = "Branch";
					}*/
					log.info("data : "+list.get(i));
					formObject.addItemInCombo(ACC_HOME_BRANCH,list.get(i).get(1).toString());
					formObject.setValue(ACC_HOME_BRANCH,list.get(i).get(1).toString());
					formObject.addItemInCombo(SOURCING_CHANNEL,list.get(i).get(4).toString());
					formObject.setValue(SOURCING_CHANNEL,list.get(i).get(4).toString());
			//    	formObject.addItemInCombo(SOURCING_CHANNEL,list.get(i).get(4).toString());
			//		formObject.setValue(SOURCING_CHANNEL,list.get(i).get(4).toString());
					String pControlValue = formObject.getValue(SOURCING_CHANNEL).toString();
					if(pControlValue.equalsIgnoreCase("Branch-Excellency")) {
						formObject.clearCombo("SOURCING_CENTER");
						addDataInComboFromQuery("SELECT SOURCING_CENTER FROM USR_0_SOURCING_CENTER WHERE "
								+ "SOURCING_CHANNEL ='Excellency' ORDER BY TO_NUMBER(UNIQUE_ID)","SOURCING_CENTER");
						formObject.setStyle(ACC_HOME_BRANCH,DISABLE,FALSE);
					} else if(pControlValue.equalsIgnoreCase("sales")) {
						addDataInComboFromQuery("SELECT SOURCING_CENTER FROM USR_0_SOURCING_CENTER WHERE "
								+ "SOURCING_CHANNEL ='Sales' ORDER BY TO_NUMBER(UNIQUE_ID)","SOURCING_CENTER");
						formObject.setStyle(ACC_HOME_BRANCH,DISABLE,FALSE);
					} else if(pControlValue.equalsIgnoreCase("Private Center")) {
						formObject.clearCombo("SOURCING_CENTER");
						addDataInComboFromQuery("SELECT SOURCING_CENTER FROM USR_0_SOURCING_CENTER WHERE "
								+ "SOURCING_CHANNEL ='Private Center' ORDER BY TO_NUMBER(UNIQUE_ID)","SOURCING_CENTER");
						formObject.setStyle(ACC_HOME_BRANCH,DISABLE,FALSE);
					}  else if(pControlValue.equalsIgnoreCase("Emirati")){
						formObject.clearCombo("SOURCING_CENTER");
						addDataInComboFromQuery("SELECT SOURCING_CENTER FROM USR_0_SOURCING_CENTER WHERE "
								+ "SOURCING_CHANNEL ='Emirati' ORDER BY TO_NUMBER(UNIQUE_ID)","SOURCING_CENTER");
						formObject.setStyle(ACC_HOME_BRANCH,DISABLE,FALSE);
					} else if(pControlValue.equalsIgnoreCase("DFC")) {
						formObject.clearCombo("SOURCING_CENTER");
						addDataInComboFromQuery("SELECT SOURCING_CENTER FROM USR_0_SOURCING_CENTER WHERE "
								+ "SOURCING_CHANNEL ='DFC' ORDER BY TO_NUMBER(UNIQUE_ID)","SOURCING_CENTER");
						formObject.setStyle(ACC_HOME_BRANCH,DISABLE,FALSE);
						formObject.setValue(DATA_ENTRY_MODE, "Detail Data Entry");
						formObject.setStyle(DATA_ENTRY_MODE,DISABLE,TRUE);
						formObject.setValue(FORM_AUTO_GENERATE, "Yes");
						formObject.setStyle(FORM_AUTO_GENERATE,DISABLE,TRUE);
					} else {
						formObject.clearCombo("SOURCING_CENTER");
						formObject.setStyle("SOURCING_CENTER",DISABLE,TRUE);
						formObject.setStyle(ACC_HOME_BRANCH,DISABLE,TRUE);
					}
					formObject.setValue(REASON,list.get(i).get(2).toString());
					formObject.setValue("AREA",list.get(i).get(3).toString());
					formObject.setValue("DEPARTMENT",list.get(i).get(4).toString());
					formObject.setValue("CPD",list.get(i).get(6).toString());
					if(!list.get(i).get(5).equalsIgnoreCase("")) {
						formObject.addItemInCombo("SOURCING_CENTER", list.get(i).get(5));
						formObject.setValue("SOURCING_CENTER", list.get(i).get(5));
					}
				}
			}else {
				sendMessageValuesList("","Your role is not defined in user access module. Please contact to administrator");
			}
			if(!formObject.getValue(SOURCING_CHANNEL).toString().equalsIgnoreCase(""))
			{
				sQuery ="SELECT CHANNEL_TYPE FROM USR_0_SERVICE_CHANNEL WHERE SERVICE_CHANNEL ='"+formObject.getValue(SOURCING_CHANNEL).toString()+"'";
				log.info("query="+sQuery);
				list = formObject.getDataFromDB(sQuery);
				if (list != null && !list.isEmpty()) {
					for(int i=0;i<list.size();i++){
						log.info("data : "+list.get(i));
						formObject.setValue("CHANNEL_TYPE",list.get(i).get(0).toString());
					}
				}
			}

			/*String pControlValue = formObject.getValue(SOURCING_CHANNEL).toString();
			if(pControlValue.equalsIgnoreCase("Branch-Excellency")) {
				formObject.clearCombo("SOURCING_CENTER");
				addDataInComboFromQuery("SELECT SOURCING_CENTER FROM USR_0_SOURCING_CENTER WHERE SOURCING_CHANNEL ='Excellency' ORDER BY TO_NUMBER(UNIQUE_ID)","SOURCING_CENTER");
				formObject.setStyle(ACC_HOME_BRANCH,DISABLE,FALSE);
			} else if(pControlValue.equalsIgnoreCase("sales")) {
				addDataInComboFromQuery("SELECT SOURCING_CENTER FROM USR_0_SOURCING_CENTER WHERE SOURCING_CHANNEL ='Sales' ORDER BY TO_NUMBER(UNIQUE_ID)","SOURCING_CENTER");
				formObject.setStyle(ACC_HOME_BRANCH,DISABLE,FALSE);
			} else if(pControlValue.equalsIgnoreCase("Private Center")) {
				formObject.clearCombo("SOURCING_CENTER");
				addDataInComboFromQuery("SELECT SOURCING_CENTER FROM USR_0_SOURCING_CENTER WHERE SOURCING_CHANNEL ='Private Center' ORDER BY TO_NUMBER(UNIQUE_ID)","SOURCING_CENTER");
				formObject.setStyle(ACC_HOME_BRANCH,DISABLE,FALSE);
			}  else if(pControlValue.equalsIgnoreCase("Emirati")){
				formObject.clearCombo("SOURCING_CENTER");
				addDataInComboFromQuery("SELECT SOURCING_CENTER FROM USR_0_SOURCING_CENTER WHERE SOURCING_CHANNEL ='Emirati' ORDER BY TO_NUMBER(UNIQUE_ID)","SOURCING_CENTER");
				formObject.setStyle(ACC_HOME_BRANCH,DISABLE,FALSE);
			} else if(pControlValue.equalsIgnoreCase("DFC")) {
				formObject.clearCombo("SOURCING_CENTER");
				addDataInComboFromQuery("SELECT SOURCING_CENTER FROM USR_0_SOURCING_CENTER WHERE SOURCING_CHANNEL ='DFC' ORDER BY TO_NUMBER(UNIQUE_ID)","SOURCING_CENTER");
				formObject.setStyle(ACC_HOME_BRANCH,DISABLE,FALSE);
				formObject.setValue(DATA_ENTRY_MODE, "Detail Data Entry");
				formObject.setStyle(DATA_ENTRY_MODE,DISABLE,TRUE);
				formObject.setValue(FORM_AUTO_GENERATE, "Yes");
				formObject.setStyle(FORM_AUTO_GENERATE,DISABLE,TRUE);
			} else {
				formObject.clearCombo("SOURCING_CENTER");
				formObject.setStyle("SOURCING_CENTER",DISABLE,TRUE);
				formObject.setStyle(ACC_HOME_BRANCH,DISABLE,TRUE);
			}*/
		} catch (Exception e) {
			logError("setIntroData",e);
		}			
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
	public JSONArray validateSubmittedForm(FormDef arg0, IFormReference arg1, String arg2) {
		return null;
	}

	@Override
	public void updateDataInWidget(IFormReference arg0, String arg1) {
		//Unused
	}

	@Override
	public String validateDocumentConfiguration(String arg0, String arg1, File arg2, Locale arg3) {
		return null;
	}

}
