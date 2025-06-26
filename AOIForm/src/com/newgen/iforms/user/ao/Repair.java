package com.newgen.iforms.user.ao;

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

	public Repair(IFormReference formObject) {
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
	public String executeServerEvent(IFormReference formObject,String controlName,String eventType,String data) {
		logInfo("executeServerEvent", "Inside Repair");
		sendMessageList.clear();
		logInfo("executeServerEvent", "sendmessagelist49");
		String controlValue = formObject.getValue(controlName).toString();
		try {
			if(eventType.equalsIgnoreCase(EVENT_TYPE_LOAD)) {
				onLoadRepair(controlName,data);
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)) {
				onClickRepair(controlName,data);
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_CHANGE)) { 
				onChangeRepair(controlName,data);
			}
		}catch (Exception e) {
			logError("Exception in Event- " + eventType + "control name- " +controlName+ ": ", e);
		} finally {
			logInfo("executeServerEvent", "sendMessageList");
			if(!sendMessageList.isEmpty()) {
				return  getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ 
						sendMessageList.get(1).toString());
			}
		}
		return getReturnMessage(true, controlName);
	}

	private void onChangeRepair(String controlName, String data) {
		String controlValue = formObject.getValue(controlName).toString();
		if(controlName.equalsIgnoreCase("REJECT_DEC")) {
			if(controlValue.equalsIgnoreCase("Approve")) {
				formObject.setStyle(CPD_CHK_MATCH_FOUND,ENABLE,FALSE);
				formObject.setStyle("Text3",ENABLE,FALSE);
				List<List<String>> sOutput=formObject.getDataFromDB("SELECT COUNT(*) AS CALLCOUNT FROM "
						+ "USR_0_LAPS_CALL_OUT where wi_name='"+ sWorkitemId +"' AND CALL_STATUS='Y'");
				int a=0;
				logInfo("","Repair output is  :: "+sOutput);
				String callCounts;
				try {
					callCounts = sOutput.get(0).get(0);
					logInfo("onChangeRepair","callcountsss is"+callCounts);
					a= Integer.parseInt(callCounts);
					logInfo("onChangeRepair","a is"+a);
					if(a<11) {
						sendMessageValuesList("Command1", "All calls are not successfull."
								+ " Please try again.");
						formObject.setValue("REJECT_DEC", "");
					}} catch (Exception e) {
						logError("",e);
					}
			} else if(controlValue.equalsIgnoreCase("Reject")) {
				formObject.setStyle(CPD_CHK_MATCH_FOUND,ENABLE,TRUE);
			}
		}
	}

	private void onLoadRepair(String controlName, String data) {
		//		loadingImage();
		logInfo("onLoadRepair","Inside fieldValueBagSet--- Repair");
		formObject.setValue("REP_DEC", "");
		formObject.setValue("REP_REMARKS", "");
		String sQuery1="SELECT FINAL_DOB,FINAL_FULL_NAME from USR_0_CUST_TXN where wi_name='"+sWorkitemId+"'";
		List<List<String>> output1=formObject.getDataFromDB(sQuery1);
		formObject.setValue(RA_LIST_OF_CUST_PEP,output1.get(0).get(0));
		formObject.setValue(SANCT_RISK_ASSESS_MARKS,output1.get(0).get(1));
		formObject.setValue(PD_OTHERS,"LAPS");
	}

	private void onClickRepair(String controlName,String data) {
		String controlValue = formObject.getValue(controlName).toString();
		if(CT_BTN_RESETALL.equalsIgnoreCase(controlName) || BTN_SUBMIT.equalsIgnoreCase(controlName)
				||"BTN_REPAIR_RETRY".equalsIgnoreCase(controlName)) {
			if(!(formObject.getValue("REJECT_DEC").toString().equalsIgnoreCase("Approve") 
					|| formObject.getValue("REJECT_DEC").toString().equalsIgnoreCase("Reject"))) {
				sendMessageValuesList("REJECT_DEC", "Please Select Decision");
			} else if(formObject.getValue("REJECT_DEC").toString().equalsIgnoreCase("Reject") 
					&& !formObject.getValue(CRO_REJ_REASON).toString().equalsIgnoreCase("Others")) {
				sendMessageValuesList(CRO_REJ_REASON, "Please Select Rejection Reason");
			} else if(formObject.getValue("REJECT_DEC").toString().equalsIgnoreCase("Reject") 
					&& formObject.getValue(CRO_REJ_REASON).toString().equalsIgnoreCase("Others") 
					&& (formObject.getValue(CRO_REMARKS).toString().trim().equalsIgnoreCase("") 
							|| formObject.getValue(CRO_REMARKS) == null)) {
				
				sendMessageValuesList(CRO_REMARKS, "Please fill Remarks");
			} 
		}else if(controlName.equalsIgnoreCase(CRO_REJ_REASON)) {
			if(formObject.getValue("REJECT_DEC").toString().equalsIgnoreCase("Reject") 
					&& controlValue.equalsIgnoreCase("Others")) {
				formObject.setStyle(CRO_REJ_REASON,ENABLE,TRUE);
			}
		}else if(controlName.equalsIgnoreCase(BTN_SUBMIT)) {
			createHistory();
			createAllHistory();
			if(formObject.getValue("REJECT_DEC").toString().equalsIgnoreCase("Reject")) {
				logInfo("","Inside reject vlue dec");
				try {
					String inputXml=createInputXML();
					logInfo("onClickRepair","Input XML is"+inputXml);
					String outputXml = ExecuteWebserviceAll1(inputXml);
					logInfo("onClickRepair","output XML is"+inputXml);
				} catch (Exception e) {
					logError("",e);
				}
			}
		}
	}

	public String createInputXML() throws Exception {
		StringBuilder inputXml=new StringBuilder();
		try {
			String ACC_NO="";
			String IBAN_NO="";
			String CUST_ID="";
			String ACC_OPEN_DT="";
			String CPD_CUST_INDI_RISK="";
			String CHANNEL_REFNUMBER="";
			sSessionId = formObject.getObjGeneralData().getM_strDMSSessionId();
			List<List<String>> listOutput = formObject.getDataFromDB("SELECT USR_0_CUST_TXN.cust_id,"
					+ "usr_0_product_selected.ACC_NO,usr_0_product_selected.iban_no,"
					+ "usr_0_product_selected.acc_open_dt,USR_0_CUST_TXN.CPD_CUST_INDI_RISK,"
					+ "EXT_AO.CHANNEL_REF_NUMBER FROM USR_0_CUST_TXN  JOIN usr_0_product_selected ON "
					+ "USR_0_CUST_TXN.WI_NAME = usr_0_product_selected.WI_NAME JOIN EXT_AO ON "
					+ "EXT_AO.WI_NAME = usr_0_product_selected.WI_NAME "
					+ "where usr_0_product_selected.wi_name='"+sWorkitemId+"'");
			if(listOutput!=null && listOutput.size()>0) {
				ACC_NO = listOutput.get(0).get(0);
				IBAN_NO = listOutput.get(0).get(1);
				CUST_ID = listOutput.get(0).get(2);
				ACC_OPEN_DT = listOutput.get(0).get(3);
				CPD_CUST_INDI_RISK = listOutput.get(0).get(4);
				CHANNEL_REFNUMBER = listOutput.get(0).get(5);
			}
			inputXml.append("<?xml version=\"1.0\"?>")
			.append("<APWebService_Input>")
			.append("<Option>WebService</Option>")
			.append("<Calltype>WS-2.0</Calltype>")
			.append("<InnerCallType>LapsResponse</InnerCallType>")
			.append("<SessionId>" + sSessionId + "</SessionId>")
			.append("<EngineName>wmscbuuat</EngineName>")
			.append("<PushMessage>")
			.append("<ChannelResponse>")
			.append("<AccountNumber>"+ACC_NO+"</AccountNumber>")
			.append("<WINumber>"+sWorkitemId+"</WINumber>")
			.append("<WIQueue></WIQueue>")
			.append("<WILockedByUser></WILockedByUser>")
			.append("<IBAN>" + IBAN_NO + "</IBAN>")
			.append("<CID>"+CUST_ID+"</CID>")
			.append("<AccountCreatedDate>" + ACC_OPEN_DT + "</AccountCreatedDate>")
			.append("<RiskClassification>"+CPD_CUST_INDI_RISK+"</RiskClassification>")
			.append("<correlationId>11</correlationId>")
			.append("<ChannelName>LAPS</ChannelName>")
			.append("<ChannelRefNumber>"+CHANNEL_REFNUMBER+"</ChannelRefNumber>")
			.append("<StatusCode>111</StatusCode><StatusDescription>Rejected from repair</StatusDescription>")
			.append("</ChannelResponse>")
			.append("</PushMessage>")
			.append("</APWebService_Input>");
		} catch (Exception e) {
			logError("Exception",e);
		}
		return inputXml.toString();
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
	public String introduceWorkItemInWorkFlow(IFormReference arg0,HttpServletRequest arg1,HttpServletResponse arg2) {
		return null;
	}

	@Override
	public String introduceWorkItemInWorkFlow(IFormReference arg0,HttpServletRequest arg1,HttpServletResponse arg2,
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
