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

public class Delivery extends Common implements Constants, IFormServerEventHandler{
	boolean bSubmit = true;
	
	public Delivery(IFormReference formObject) {
		super(formObject);
	}

	@Override
	public void beforeFormLoad(FormDef arg0, IFormReference arg1) {
		log.info("Inside Delivery beforeFormLoad >>>");
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
				if (CONTROL_NAME_FORM.equalsIgnoreCase(controlName)) {
					log.info("inside on form load Delivery >>>>");
					String wiName = formObject.getValue("WI_NAME").toString();
					log.info("inside form load event -- WorkItem Name: " + wiName);
					Boolean view = setUserDetail();
					intializeStaticValue();
					deliveryLoadCombo();		
					formObject.applyGroup(CONTROL_SET_DELIVERY_CUSTOMER_DETAILS);
					if(Boolean.FALSE.equals(view)) {
						formObject.applyGroup(CONTROL_SET_READONLY_DELIVERY);
					}
					formObject.addItemInCombo(DEC_CODE, "Document Couriered to Bank");
					formObject.setValue(DEC_CODE, "Document Couriered to Bank");
					retMsg = getReturnMessage(view, controlName);
				} else if (FRAME_DECISION_HIST.equalsIgnoreCase(controlName)) {
					log.info("loading decision history ...");
					loadDecisionHistoryListView();
				}
			} else if (controlName.equalsIgnoreCase(BUTTON_SUBMIT) && eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)) {
//				if(bSubmit){
//				  bSubmit=false;
				String sWIName = formObject.getObjGeneralData().getM_strProcessInstanceId();

				//added by shivanshu
					int returnCode = getDocumentDHL();
					String sQuery1="SELECT TRADE_CUST_EMAIL_ID FROM EXT_TFO WHERE WI_NAME = '"+sWIName+"'";  //ATP-255
					List<List<String>> lresultSet1=null;
					String customerEmailID ="";
					lresultSet1 =  formObject.getDataFromDB(sQuery1);
					log.info("lresultSet1: "+lresultSet1);
					 if(lresultSet1!=null || !lresultSet1.isEmpty()){
						customerEmailID=lresultSet1.get(0).get(0);
					 }else {
						 customerEmailID="TFOManagers@adcb.com";
					 }
					log.info("customerEmailID: "+customerEmailID);
					String requestType = formObject.getValue("REQUEST_TYPE").toString();
					log.info("ALERT requestType ..." + requestType + " and returnCode " + returnCode);
					if (returnCode == -1 &&(requestType.equals("EC_AM") || requestType.equals("ELCB_AM")) ) {
						retMsg = getReturnMessage(false, controlName,"Attach AWB Courier Document");
					} else if (returnCode == -2 && (requestType.equals("EC_AM") || requestType.equals("ELCB_AM") 
							|| requestType.equals("EC_BL") || requestType.equals("ELCB_BL"))) {
						retMsg = getReturnMessage(false, controlName,"Attach Remittance Letter Document");
					} else if (returnCode == -3 && (requestType.equals("EC_AM") || requestType.equals("ELCB_AM") 
							|| requestType.equals("EC_BL") || requestType.equals("ELCB_BL"))) {
						retMsg = getReturnMessage(false, controlName,"Attach AWB Courier or Remittance Letter Document");
					}else {
				  saveDecHistory();
						//Added by Shivanshu
						if (requestType.equals("EC_AM") || requestType.equals("ELCB_AM") 
								|| requestType.equals("EC_BL") || requestType.equals("ELCB_BL")){
						String insertionID =  getInsertIonIdForTable("TFO_DJ_FINAL_DEC_SUMMARY");
						String query = "Insert into TFO_DJ_FINAL_DEC_SUMMARY (INSERTIONORDERID,REFFERD_TO,DECISION,EXISTING_MAIL,WI_NAME)"
								+ "VALUES ('"+insertionID+"','Customer','Y','"+customerEmailID+"','"+sWIName+"')";
						log.info("insert query: " + query);
						int b = formObject.saveDataInDB(query);
						log.info("insert status: "+b);
				}
			}
	
//				}
			}
		} catch (Exception e) {
			log.error("Exception in Event- " + eventType + "control name- " +controlName+ ": ", e);
		}
		return retMsg;
	}
	
	private void deliveryLoadCombo(){
		try {
			log.info("***********deliveryLoadCombo*************");
			decisionValidation(DEC_CODE);
			loadSwiftDecisionCombo(requestCategory,requestType);
			log.info("***********deliveryLoadCombo END*************");
		} catch (Exception e) {
			log.error("Exception: ",e);
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
		//unused
	}

	@Override
	public String validateDocumentConfiguration(String arg0, String arg1, File arg2, Locale arg3) {
		return null;
	}

}
