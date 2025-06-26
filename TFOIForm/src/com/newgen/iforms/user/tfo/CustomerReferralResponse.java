package com.newgen.iforms.user.tfo;

import java.io.File;
import java.util.ArrayList;
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

public class CustomerReferralResponse extends Common implements Constants, IFormServerEventHandler{
	boolean bSubmit = true;

	public CustomerReferralResponse(IFormReference formObject) {
		super(formObject);
	}

	@Override
	public void beforeFormLoad(FormDef arg0, IFormReference arg1) {
		log.info("Inside CustomerReferralResponse beforeFormLoad >>>");
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
		log.info("Inside CustomerReferralResponse executeServerEvent>");
		log.info("Event: " + eventType + ", Control Name: " + controlName + ", Data: " + data);
		String retMsg = getReturnMessage(true);
		try {
			if (eventType.equalsIgnoreCase(EVENT_TYPE_LOAD)) {
				if (CONTROL_NAME_FORM.equalsIgnoreCase(controlName)) {
					//formloadData();					
					log.info("inside on form load CustomerReferralResponse >>>>");
					String wiName = formObject.getValue("WI_NAME").toString();
					log.info("inside form load event -- WorkItem Name: " + wiName);
					Boolean view = setUserDetail();					
					intializeStaticValue();
					referralLoadCombo();
					formObject.applyGroup(CONTROL_SET_CUSTOMER_DETAILS);
					loadDecReferalGrid();
					setDecision(); //PT_US50
					log.info("Creating object of PPM -- WorkItem Name: " + wiName);
					PPM ppm_obj = new PPM(formObject);
					ppm_obj.formLoadData();
					//disableFieldOnDemand("PRODUCT_TYPE####BRN_CODE");
					if(Boolean.FALSE.equals(view)) {
						formObject.applyGroup(CONTROL_SET_READONLY_RM);
					}
					retMsg = getReturnMessage(view, controlName);
				} else if (FRAME_DECISION_HIST.equalsIgnoreCase(controlName)) {
					log.info("loading decision history ...");
					loadDecisionHistoryListView();
				} 
			} else if (controlName.equalsIgnoreCase(BUTTON_SUBMIT) && eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)) {
				String wiName = formObject.getValue("WI_NAME").toString();
				String emailFrom ="";
                if(bSubmit)
				 {
					bSubmit=false;
					if(formObject.getValue(DEC_CODE).toString().equalsIgnoreCase("CRC")){
						formObject.setValue(IS_CR_PPM, "N");
						String query = "delete from tfo_dj_ref_doc_rvw where wi_name = '"+wiName+"' and refer_to='Customer'";
						log.info("delete query: "+query);
                        int response=formObject.saveDataInDB(query);
						log.info("delete query response: "+response);
					} else if(formObject.getValue(DEC_CODE).toString().equalsIgnoreCase("RCA")){
						formObject.setValue(IS_CR_PPM, "Y");
					}
					saveDecHistory();
					insertIntoNotificationTxn();
					String ptemail="";
					String sQuery="select def_value from tfo_dj_default_mast where def_name = 'PT_FROM_MAIL'";
					List<List<String>> lresultSet=null;
					lresultSet =  formObject.getDataFromDB(sQuery);
					if(lresultSet!=null){
						if(!lresultSet.isEmpty()){
							ptemail=lresultSet.get(0).get(0);
							}}
					
					if("COTE".equalsIgnoreCase(formObject.getValue(DEC_CODE).toString())){
						emailFrom ="Customer Only Through Email";
					} else if("RCA".equalsIgnoreCase(formObject.getValue(DEC_CODE).toString())){
						emailFrom ="Customer";
					}
					List<String> paramlist = new ArrayList<String>();
					String workitemName = formObject.getObjGeneralData().getM_strProcessInstanceId();
					paramlist.add("Text :"+workitemName);
					paramlist.add("Text :"+emailFrom);
					paramlist.add("Text :"+ptemail);
					paramlist.add("Text :");
					paramlist.add("Text :PP_MAKER");
					paramlist.add("Text :");
					paramlist.add("Text :");
					paramlist.add("Text :");
					log.info("paramlist : "+paramlist);
					List statusProc = formObject.getDataFromStoredProcedure("TFO_DJ_REFF_EMAIL_NOTIFY", paramlist);
					log.info(statusProc);
					/*paramlist = new ArrayList();
					paramlist.add("Text :"+workitemName);
					paramlist.add("Text :Customer");
					paramlist.add("Text :"+ptemail);
					paramlist.add("Text :ab");
					paramlist.add("Text :PP_MAKER");
					paramlist.add("Text :ab");
					paramlist.add("Text :ab");
					paramlist.add("Text :ab");
					log.info("paramlist : "+paramlist);
					 statusProc = formObject.getDataFromStoredProcedure("TFO_DJ_REFF_EMAIL_NOTIFY", paramlist);
					log.info(statusProc);*/
					if(formObject.getValue(DEC_CODE).toString().equalsIgnoreCase("COTE")){//COTE
						submitReferalHistory();
					}
				}
			}

		} catch (Exception e) {
			log.error("Exception in Event- " + eventType + "control name- " +controlName+ ": ", e);
		}
		return retMsg;
	}
	//PT_US50
	private void setDecision(){

		try{
			String sQuery="select is_rm_ppm , IS_CR_PPM, is_legal_ppm,IS_REF_PPM,IS_CB_PPM,IS_TRADE_PPM from  ext_tfo "
					+ " WHERE WI_NAME='"+this.sWorkitemID+"'";

			List<List<String>> lresultSet=null;
			log.info("setFlag sQuery="+sQuery);
			lresultSet =  formObject.getDataFromDB(sQuery);
			log.info("setFlag output="+lresultSet);
			if(lresultSet!=null){
				log.info("in setFlag lresultSet=");
				if(!lresultSet.isEmpty()){
					log.info("in setFlag lresultSet.size");
					String isRM=lresultSet.get(0).get(0);
					String isCR=lresultSet.get(0).get(1);
					String isLegal=lresultSet.get(0).get(2);
					String isREF=lresultSet.get(0).get(3);
					String isCB=lresultSet.get(0).get(4);
					String isTrade=lresultSet.get(0).get(5);
					if("Y".equalsIgnoreCase(isRM)
							||"Y".equalsIgnoreCase(isLegal)||"Y".equalsIgnoreCase(isREF)
							||"Y".equalsIgnoreCase(isCB)||"Y".equalsIgnoreCase(isTrade))
					{
						formObject.setValue(DEC_CODE, "RCA");
					}else{
						formObject.setValue(DEC_CODE, "CRC");
					}
				}
			}
		}catch(Exception e){
			log.error("Exception in",e);
		}
	}
	
	private void insertIntoNotificationTxn() {
		try{
			log.info("inside insertIntoNotificationTxn");
			String proTradeRef = "";
			String prodCode = "";
			String status = "";
			String remarks = "";
			String sQuery="select is_rm_ppm , IS_CR_PPM, is_legal_ppm,IS_REF_PPM,IS_CB_PPM,IS_TRADE_PPM, "
							+ "PRO_TRADE_REF_NO, PRODUCT_TYPE "
							+ "from ext_tfo "
							+ " WHERE WI_NAME='"+this.sWorkitemID+"'";
			String decision = formObject.getValue(DEC_CODE).toString();
			List<List<String>> lresultSet=null;
			log.info("setFlag sQuery="+sQuery);
			lresultSet =  formObject.getDataFromDB(sQuery);
			log.info("setFlag output="+lresultSet);
			if(lresultSet!=null){
				log.info("in setFlag lresultSet=");
				if(!lresultSet.isEmpty()){
					log.info("in setFlag lresultSet.size");
					String isRM=lresultSet.get(0).get(0);
					String isCR=lresultSet.get(0).get(1);
					String isLegal=lresultSet.get(0).get(2);
					String isREF=lresultSet.get(0).get(3);
					String isCB=lresultSet.get(0).get(4);
					String isTrade=lresultSet.get(0).get(5);
					proTradeRef = lresultSet.get(0).get(6);
					prodCode = lresultSet.get(0).get(7);
					if("N".equalsIgnoreCase(isTrade) && "N".equalsIgnoreCase(isCB)
						&& "N".equalsIgnoreCase(isLegal) && "N".equalsIgnoreCase(isREF)
						&& "N".equalsIgnoreCase(isRM)){
						if("RCA".equalsIgnoreCase(decision)){
							status = "Send for modification";
							remarks = formObject.getValue(REMARKS).toString();
						}else if("CRC".equalsIgnoreCase(decision)){
							status = "Transaction under review";
						}
					}else if(("Y".equalsIgnoreCase(isTrade) || "Y".equalsIgnoreCase(isCB)
						|| "Y".equalsIgnoreCase(isLegal) || "Y".equalsIgnoreCase(isREF)
						|| "Y".equalsIgnoreCase(isRM))){
						if("RCA".equalsIgnoreCase(decision)){
							status = "Send for modification";
							remarks = formObject.getValue(REMARKS).toString();
						}else if("CRC".equalsIgnoreCase(decision)){
							status = "Pending Approval";
						}
					}
					/*
					if("RCA".equalsIgnoreCase(formObject.getValue(DEC_CODE).toString()) 
							&& "N".equalsIgnoreCase(isTrade) && "N".equalsIgnoreCase(isCB)
							&& "N".equalsIgnoreCase(isLegal) && "N".equalsIgnoreCase(isREF)
							&& "N".equalsIgnoreCase(isRM)) {
						status = "Refer to customer";
						remarks = formObject.getValue(REMARKS).toString();
						if("Y".equalsIgnoreCase(PT_UTILITY_FLAG)){
							status = "Sent for modification";
						}
					} else if("RCA".equalsIgnoreCase(formObject.getValue(DEC_CODE).toString()) 
							&& ("Y".equalsIgnoreCase(isTrade) || "Y".equalsIgnoreCase(isCB)
							|| "Y".equalsIgnoreCase(isLegal) || "Y".equalsIgnoreCase(isREF)
							|| "Y".equalsIgnoreCase(isRM))) {
						status = "Refer to customer and internal approvals";
						if("Y".equalsIgnoreCase(PT_UTILITY_FLAG)){
							status = "Sent for modification";
						}
					} else if("CRC".equalsIgnoreCase(formObject.getValue(DEC_CODE).toString()) 
							&& ("Y".equalsIgnoreCase(isTrade) || "Y".equalsIgnoreCase(isCB)
							|| "Y".equalsIgnoreCase(isLegal) || "Y".equalsIgnoreCase(isREF)
							|| "Y".equalsIgnoreCase(isRM))) {
						status = "Internal approvals";
						if("Y".equalsIgnoreCase(PT_UTILITY_FLAG)){
							status = "Pending Approval";
						}
					} else if("CRC".equalsIgnoreCase(formObject.getValue(DEC_CODE).toString()) //PT
							&& ("N".equalsIgnoreCase(isTrade) || "N".equalsIgnoreCase(isCB)
							|| "N".equalsIgnoreCase(isLegal) || "N".equalsIgnoreCase(isREF)
							|| "N".equalsIgnoreCase(isRM))) {
						if("Y".equalsIgnoreCase(PT_UTILITY_FLAG)){
							status = "Transaction under review";
						}
					}
					*/
					if(!status.isEmpty()){
						String query = "INSERT INTO TFO_DJ_PROTRADE_FCDB_UPDATE (INSERTIONDATETIME,"
								+ "WI_NAME, PRO_TRADE_REF_NO, PRODUCT_CODE, STATUS, REMARKS, WS_NAME, EXECUTION_STATUS) "
								+ "VALUES (SYSDATE,'"+this.sWorkitemID+"','"+proTradeRef+"','"+prodCode+"','"+status+"'"
										+ ",'"+remarks.replaceAll("'", "''")+"','"+this.sActivityName+"','N')";
						log.info("insertIntoNotificationTxn query: " + query);
						int b = formObject.saveDataInDB(query);
						log.info("insert status: "+b);
					}
				}
			}
		}catch(Exception e){
			log.error("Exception in insertIntoNotificationTxn: ",e);
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
	public void updateDataInWidget(IFormReference arg0, String arg1) {

	}

	@Override
	public String validateDocumentConfiguration(String arg0, String arg1, File arg2, Locale arg3) {
		return null;
	}

	@Override
	public JSONArray validateSubmittedForm(FormDef arg0, IFormReference arg1, String arg2) {
		return null;
	}

}
