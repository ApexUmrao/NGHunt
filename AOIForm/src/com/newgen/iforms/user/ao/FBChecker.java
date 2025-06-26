package com.newgen.iforms.user.ao;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

import com.newgen.iforms.EControl;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.iforms.user.config.Constants;
import com.newgen.mvcbeans.model.WorkdeskModel;

public class FBChecker extends Common implements Constants, IFormServerEventHandler{

	public FBChecker(IFormReference formObject) {
		super(formObject);
	}
	
	@Override
	public void beforeFormLoad(FormDef arg0, IFormReference arg1) {		
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
	public String executeServerEvent(IFormReference formObject, String controlName, String eventType, String data){
		logInfo("executeServerEvent", "Inside FBChecker >>>");
		logInfo("executeServerEvent", "Event: " + eventType + ", Control Name: " + controlName + ", Data: " + data);
		sendMessageList.clear();
		String retMsg = getReturnMessage(true,controlName);
		try{
			if(eventType.equalsIgnoreCase(EVENT_TYPE_LOAD)) {
				
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)) {
				if(BTN_SUBMIT.equalsIgnoreCase(controlName)) {
					if(data.equalsIgnoreCase("afterJSP")) {
						String query = "select count(1) as COUNT_SUCCESS from USR_0_INTEGRATION_CALLS where WI_NAME='"
								+sWorkitemId+"'  and (STATUS is null or STATUS<>'Success')";
						logInfo("insertIntoETL", "query: "+query);
						List<List<String>> result = formObject.getDataFromDB(query);
						logInfo("insertIntoETL", "result: "+result);
						if(result.get(0).get(0).equalsIgnoreCase("0")) {
							insertIntoETL();
							createHistory();
						//	callforSMS();//Integration call added by Ayush to hit SMS service.
							createAllHistory();
							String sProcName="SP_TemplateGeneration_SMS";
							List<String> paramlist =new ArrayList<>();
							paramlist.add (PARAM_TEXT+sWorkitemId);
							paramlist.add (PARAM_TEXT+"FBChecker");
							logInfo("executeServerEvent postSubmit",sProcName+":"+paramlist);
							formObject.getDataFromStoredProcedure(sProcName, paramlist);
						} else {
							sendMessageValuesList("", "Integration call(s) not successful. "
									+ "Please close the worktiem and try again.");
						}
					} else if(submitFBValidation()) {
						if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")) {
							insertDataInIntegrationTable();
							return getReturnMessage(true, controlName, "openJSP");
						}						
					}
				}
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_CHANGE)) {
				
			}
		} catch (Exception e) {
			logInfo("executeServerEvent", "Exception in Event- " + eventType + "control name- " +controlName+ ": ");
			logError("executeServerEvent", e);
		} finally {
			logInfo("executeServerEvent", sendMessageList.toString());
			if(!sendMessageList.isEmpty()) {
				if(sendMessageList.get(1).contains(" does not start with 971") ||
						sendMessageList.get(1).equalsIgnoreCase("Mobile number is not of 12 digits") || 
						sendMessageList.get(1).equalsIgnoreCase("Residence Telephone Number is not of 11 digits")) {
					return getReturnMessage(true,controlName,sendMessageList.get(0)+"###"
							+ sendMessageList.get(1));
				}
				return getReturnMessage(false,controlName,sendMessageList.get(0).toString()+"###"
						+ sendMessageList.get(1));
			}
		}
		return retMsg;
	}
	
	private void insertIntoETL() {
		try {
			logInfo("insertIntoETL", "INSIDE");
			int count = getGridCount(LVW_FAMILY_MEMBERS);
			String columns = "WI_NAME,REQUEST_TYPE,HOF_CUST_ID,HOF_CUSTOMER_NAME,HOF_CUSTOMER_SEGMENT,HOF_RM_CODE,"
					+ "HOF_RM_NAME,FM_CUST_ID,FB_GROUP_ID,FM_CUSTOMER_NAME,FM_CUSTOMER_SEGMENT,RELATION_WITH_HOF,"
					+ "MAKER_DATE,APPROVED_DATE,MAKER_ID,CHECKER_ID,OPERATION_TYPE,PROCESS_FLAG";
			String query = "select CREATE_DAT, USERNAME from USR_0_AO_DEC_HIST where WI_NAME='"+sWorkitemId
					+"' and WS_NAME='FB_Maker' order by CREATE_DAT desc";
			logInfo("insertIntoETL", "query: "+query);
			String hofName = formObject.getValue("HOF_NAME").toString();
			String hofSegment = formObject.getValue("HOF_CUSTOMER_SEGMENT").toString();
			List<List<String>> result = formObject.getDataFromDB(query);
			logInfo("insertIntoETL", "result: "+result);
			String makerDate = "";
			String makerId = "";
			if(result.size()>0) {
				makerDate = result.get(0).get(0);
				makerId = result.get(0).get(1);
			}	
			String checkerId = sUserName;
			for(int i=0; i<count; i++) {
				String custIdFM = formObject.getTableCellValue(LVW_FAMILY_MEMBERS, i, 2);
				String custNameFM = formObject.getTableCellValue(LVW_FAMILY_MEMBERS, i, 3);
				String custSegFM = formObject.getTableCellValue(LVW_FAMILY_MEMBERS, i, 4);
				String custRltnFM = formObject.getTableCellValue(LVW_FAMILY_MEMBERS, i, 6);
				String custStatusFM = formObject.getTableCellValue(LVW_FAMILY_MEMBERS, i, 8);
				if(!custStatusFM.equalsIgnoreCase("E")) {
					String values = "'"+sWorkitemId+"','Family Banking','"+formObject.getValue(CID_HOF)+"','"
							+hofName+"','"+hofSegment+"','"+formObject.getValue("HOF_RM_CODE")+"','"
							+formObject.getValue(RM_HOF)+"','"+custIdFM+"','"+
							formObject.getValue(CID_FAMILY_GROUP)+"','"+custNameFM+"','"+custSegFM+"','"+custRltnFM+"'"
							+ ",to_date('"+makerDate+"','DD/MM/YYYY hh24:MI:SS'),SYSDATE,'"+makerId+"','"+checkerId
							+"','"+custStatusFM+"','U'";
					insertDataIntoDB("WMS_TO_ODS_FAMILY_BANKING_HANDOFF", columns, values);
				}				
			}			
		} catch (Exception e) {
			logError("insertIntoETL", e);
		}
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
	}

	@Override
	public String validateDocumentConfiguration(String arg0, String arg1,
			File arg2, Locale arg3) {
		return null;
	}

	@Override
	public JSONArray validateSubmittedForm(FormDef arg0, IFormReference arg1,
			String arg2) {
		return null;
	}

}
