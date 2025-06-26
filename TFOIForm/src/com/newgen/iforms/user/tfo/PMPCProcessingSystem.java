package com.newgen.iforms.user.tfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;





import com.newgen.iforms.EControlOption;
import com.newgen.iforms.controls.EComboControl;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.user.config.Constants;
import com.newgen.iforms.user.config.TFOLogger;
import com.newgen.iforms.user.tfo.util.ConnectSocket;
import com.newgen.iforms.user.tfo.util.ConnectTemplateSocket;
import com.newgen.iforms.user.tfo.util.ExecuteXML;
import com.newgen.iforms.user.tfo.util.LoadConfiguration;
import com.newgen.iforms.user.tfo.util.ProtradeComplexMapping;
import com.newgen.iforms.user.tfo.util.XMLParser;


import ISPack.CPISDocumentTxn;
import ISPack.ISUtil.JPDBRecoverDocData;
import ISPack.ISUtil.JPISException;
import ISPack.ISUtil.JPISIsIndex;





//import com.newgen.laps.util.APCallCreateXML;
import com.newgen.iforms.EControl;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.iforms.user.config.Constants;
import com.newgen.iforms.user.tfo.util.XMLParser;
import com.newgen.mvcbeans.model.WorkdeskModel;
import com.newgen.omni.wf.util.excp.NGException;

public class PMPCProcessingSystem extends Common implements Constants, IFormServerEventHandler{
	boolean bSubmit = true;

	public PMPCProcessingSystem(IFormReference formObject) {
		super(formObject);
	}

	@Override
	public void beforeFormLoad(FormDef arg0, IFormReference arg1) {
		log.info("Inside PMPCProcessingSystem beforeFormLoad >>>");
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
		sendMessageList.clear();
		String retMsg = getReturnMessage(true);
		try {
			if (eventType.equalsIgnoreCase(EVENT_TYPE_LOAD)) {
				if (CONTROL_NAME_FORM.equalsIgnoreCase(controlName)) {
					log.info("inside on form load PMPCProcessingSystem >>>>");
					String wiName = formObject.getValue("WI_NAME").toString();
					log.info("inside form load event -- WorkItem Name: " + wiName);
					Boolean view = setUserDetail();
					intializeStaticValue();
					referralLoadCombo();
					formObject.applyGroup(CONTROL_SET_PMPS_CUSTOMER_DETAILS);
					loadDecReferalGrid();
					showUTCDetails();  //added by reyaz 5082022
					loadReasonCombo(formObject.getValue(DEC_CODE).toString(), "REASON_FOR_ACTION");
					if(Boolean.FALSE.equals(view)) {
						formObject.applyGroup(CONTROL_SET_READONLY_PMPS);
					}
					retMsg = getReturnMessage(view, controlName);
				} else if (FRAME_DECISION_HIST.equalsIgnoreCase(controlName)) {
					log.info("loading decision history ...");
					loadDecisionHistoryListView();
				}
				if("SCC".equalsIgnoreCase(sActivityName)){
					formObject.removeItemFromCombo(DEC_CODE,3); //ATP-402 Changes by Shivanshu
					formObject.clearCombo("REASON_FOR_ACTION");
					formObject.addItemInCombo("REASON_FOR_ACTION","Internal Policy Issues");
					formObject.setValue("REASON_FOR_ACTION", "Internal Policy Issues");       //ADDED BY SHIVANSHU
				}
			} else if (controlName.equalsIgnoreCase(BUTTON_SUBMIT) && validateLoanReferencePCPM() //BY KISHAN TSLM
					&& eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)) {
				setReasonForAction();
				saveDecHistory();
				String	activityName= formObject.getValue("SYSTEM_ACTIVITY_NAME").toString();
				if("PC PROCESSING SYSTEM".equalsIgnoreCase(sActivityName) && "ARDD".equalsIgnoreCase(activityName)){
					log.info("if PC PROCESSING SYSTEM and ARDD System Activity Name ..");
					saveContractLimitData();
					log.info("sWorkitemID="+sWorkitemID);
					List<String> paramlist =new ArrayList<>( );
					paramlist.add (PARAM_TEXT+sWorkitemID);
					paramlist.add ("Text :N");
					formObject.getDataFromStoredProcedure("TFO_DJ_EMAIL_CONFIG", paramlist);
					List<String> paramlist2 =new ArrayList<>( );
					paramlist2.add (PARAM_TEXT+sWorkitemID);
					paramlist2.add (PARAM_TEXT+formObject.getValue(TRANSACTION_REFERENCE).toString());
					paramlist2.add (PARAM_TEXT+formObject.getValue(CUSTOMER_ID).toString());
					formObject.getDataFromStoredProcedure("TFO_DJ_PC_WMS_MASTER", paramlist2);
				}
				if("PC PROCESSING SYSTEM".equalsIgnoreCase(sActivityName) 
						&& "SBMT".equalsIgnoreCase(formObject.getValue(DEC_CODE).toString())){
					if("IFP".equalsIgnoreCase(requestCategory)|| "IFA".equalsIgnoreCase(requestCategory) || "IFS".equalsIgnoreCase(requestCategory) || "SCF".equalsIgnoreCase(requestCategory) ){	 //ADDED BY SHIVANSHU SCF ATP - 207
						if("LD".equalsIgnoreCase(requestType) || "IFA_CTP".equalsIgnoreCase(requestType) || "PD".equalsIgnoreCase(requestType) || "PDD".equalsIgnoreCase(requestType)) {
							String utcRequire = formObject.getValue("UTC_REQUIRED").toString();
							String utcBrms = formObject.getValue("UTC_REQUIRED_BRMS").toString();
							String sActivity = formObject.getValue("SYSTEM_ACTIVITY_NAME").toString();
							String isGetDocVal = formObject.getValue("IS_GETDOCUMENT_UTC_DONE").toString();
							if("Y".equalsIgnoreCase(isGetDocVal) && "ARDD".equalsIgnoreCase(sActivity) && "Yes".equalsIgnoreCase(utcRequire) && ("Yes".equalsIgnoreCase(utcBrms) || "No".equalsIgnoreCase(utcBrms))){

								log.info("inside requestType");
								String columnNames = "SNO,WI_NAME,CALL_NAME,CALL_DT,CALL_STATUS,RETRY_COUNT,VESSEL_NAME";
								log.info("UTC_START_CHECK starts");
								int i=0;
								String query = "delete from TFO_DJ_INTEGRATION_CALLS WHERE CALL_NAME = 'updateDocumentStatusUTC' AND WI_NAME = '"+sWorkitemID+"'";
								formObject.saveDataInDB(query);
								if(!getUpdateDocDetailsIntegration()){
									String sInsertScript = "INSERT INTO TFO_DJ_INTEGRATION_CALLS ("+columnNames+") VALUES "
											+ "('"+(i+1)+"','" + sWorkitemID + "','updateDocumentStatusUTC',sysdate,'N','0','updateDocumentStatusUTC')";
									log.info("[sInsertScript for Integration Calls]"+sInsertScript);
									formObject.saveDataInDB(sInsertScript);
									return   getReturnMessage(true, "","updateDocStatus");

								}
							}
							setLegalisationFlag();	
						}
					}
					if ((requestType.equals("EC_AM") || requestType.equals("ELCB_AM") 
							|| requestType.equals("EC_BL") || requestType.equals("ELCB_BL")) && "ARDD".equalsIgnoreCase(activityName)){
						//fetchAdviceDetails();
						List<List<String>> sOutputlist = formObject.getDataFromDB("SELECT SEQ_WEBSERVICE.nextval as IDValue from DUAL");
						String txnNumber= sOutputlist.get(0).get(0);
						String referenceNo = "1";
						callDocumentDHL(txnNumber,referenceNo);	
						//insertIntoNotificationTxnRemittanceLetter();
					}
					insertIntoNotificationTxn();
					//ATP-508 REYAZ 22-08-2024 START 
					String processType=formObject.getValue(PROCESS_TYPE).toString();
					if(processType.equalsIgnoreCase("TSLM Front End")){
						createTSLMPushMsg("R");
					}
					//ATP-508 REYAZ 22-08-2024 END
					return CreateNewWorkitem(); //US_SPRINT4_PT_148
				}
				if("Legalization Queue".equalsIgnoreCase(sActivityName) 
						&& "SBMT".equalsIgnoreCase(formObject.getValue(DEC_CODE).toString())){
					if("IFP".equalsIgnoreCase(requestCategory)|| "IFA".equalsIgnoreCase(requestCategory) || "IFS".equalsIgnoreCase(requestCategory) || "SCF".equalsIgnoreCase(requestCategory)){	//ADDED BY SHIVANSHU SCF ATP - 207
						if("LD".equalsIgnoreCase(requestType) || "IFA_CTP".equalsIgnoreCase(requestType) || "PD".equalsIgnoreCase(requestType) || "PDD".equalsIgnoreCase(requestType)) {
							log.info("Inside Legalisation Queue Configuration");
							setLegalisationFlag();
							createTSLMPushMsg("R");
							return "";
							}		
						}
					}
			}else if (eventType.equalsIgnoreCase(EVENT_TYPE_CHANGE)){
				if("SCC".equalsIgnoreCase(sActivityName) && controlName.equalsIgnoreCase(DEC_CODE)){
					log.info("if inside SCC Activity Name ..");
					if("INTREJ".equalsIgnoreCase(formObject.getValue(DEC_CODE).toString())){  //ATP-402 Change by Shivanshu
						log.info("else if inside Internal Rejection..");
						formObject.clearCombo("REASON_FOR_ACTION");
						formObject.addItemInCombo("REASON_FOR_ACTION","Internal Policy Issues");
						formObject.setValue("REASON_FOR_ACTION", "Internal Policy Issues");
						formObject.setStyle("REASON_FOR_ACTION",DISABLE, "true");
					}else {
						formObject.setStyle("REASON_FOR_ACTION",DISABLE, "false");
					}
			}
			}
					
		} catch (Exception e) {
			log.error("Exception in Event- " + eventType + "control name- " +controlName+ ": ", e);
		} finally{
			log.info("sendMessageList="+sendMessageList);
			log.info("bSubmit="+bSubmit);
			if(!sendMessageList.isEmpty()){
				if(!bSubmit){
					bSubmit=true;
				}
				return  getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1).toString());
			}

		}
		return retMsg;
	}
	
	private void insertIntoNotificationTxn() {
		try{
			log.info("inside insertIntoNotificationTxn");
			String proTradeRef = "";
			String prodCode = "";
			String status = "Transaction Completed";
			String remarks = "";
			String sQuery="select PRO_TRADE_REF_NO, PRODUCT_TYPE "
					+ "from ext_tfo "
					+ " WHERE WI_NAME='"+this.sWorkitemID+"'";
			List<List<String>> lresultSet=null;
			log.info("setFlag sQuery="+sQuery);
			lresultSet =  formObject.getDataFromDB(sQuery);
			log.info("setFlag output="+lresultSet);
			if(lresultSet!=null){
				log.info("in setFlag lresultSet=");
				if(!lresultSet.isEmpty()){
					log.info("in setFlag lresultSet.size");
					proTradeRef = lresultSet.get(0).get(0);
					prodCode = lresultSet.get(0).get(1);
					String query = "INSERT INTO TFO_DJ_PROTRADE_FCDB_UPDATE (INSERTIONDATETIME,"
							+ "WI_NAME, PRO_TRADE_REF_NO, PRODUCT_CODE, STATUS, REMARKS, WS_NAME, EXECUTION_STATUS) "
							+ "VALUES (SYSDATE,'"+this.sWorkitemID+"','"+proTradeRef+"','"+prodCode+"','"+status+"'"
									+ ",'"+remarks.replaceAll("'", "''")+"','"+this.sActivityName+"','N')";
					log.info("insertIntoNotificationTxn query: " + query);
					int b = formObject.saveDataInDB(query);
					log.info("insert status: "+b);
				}
			}
		}catch(Exception e){
			log.error("Exception in insertIntoNotificationTxn: ",e);
		}
	}	
	
	private void insertIntoBpmTxnForUTC() {
		try{
			log.info("inside insertIntoBpmTxnForUTC");
			String query = "INSERT INTO BPM_EVENTGEN_TXN_DATA (insertiondatetime, wi_name, expiry_date, "
							+ "status_flag,PROCESS_NAME,SOURCING_CHANNEL, REQUESTMODE) "
							+ "VALUES (SYSDATE,'"+this.sWorkitemID+"',(SYSDATE+(5/(24*60))),'N','TFO'"
									+ ",'UTC','C','N')";
			log.info("insertIntoBpmTxnForUTC query: " + query);
			int b = formObject.saveDataInDB(query);
			log.info("insert status: "+b);
				
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

	private boolean validateLoanReferencePCPM(){  //BY KISHAN TSLM
		log.info("INSIDE validateLoanReferencePCPM");
		String Req_CAT = formObject.getValue(REQUEST_CATEGORY).toString();
		String Req_TYPE = formObject.getValue(REQUEST_TYPE).toString();
		String Processing_System = formObject.getValue(PROCESSING_SYSTEM).toString();
		String createAmendValue = formObject.getValue(CREATE_AMEND_CNTRCT_FCUBS).toString();
		String target_activity_name = formObject.getValue("SYSTEM_ACTIVITY_NAME").toString();
		int loanCount = getGridCount("TAB_TSLM_LOAN_REF");
		if (Req_CAT.equalsIgnoreCase("IFA")|| Req_CAT.equalsIgnoreCase("IFS")|| Req_CAT.equalsIgnoreCase("IFP") || Req_CAT.equalsIgnoreCase("SCF")) {//ADDED by Shivanshu FOR SCF ATP -207
			log.info("Check 1");
			log.info("target_activity_name==="+target_activity_name);
			if (Processing_System.equalsIgnoreCase("T")){
				log.info("Check 2");
				if (Req_TYPE.equalsIgnoreCase("LD") && createAmendValue.equalsIgnoreCase("Y")) {
					log.info("Check 3");
					if(loanCount == 0){
						if((!target_activity_name.equalsIgnoreCase("PM"))){
							log.info("Check 6");
							sendMessageHashMap("TAB_TSLM_LOAN_REF", "Please Add data in TSLM Loan Reference Grid");
							return false;
						}
					}
				}
				/*else if(Req_CAT.equalsIgnoreCase("IFA") && Req_TYPE.equalsIgnoreCase("IFA_CTP")
						&& createAmendValue.equalsIgnoreCase("Y") ){
					log.info("Check 5");
					if(loanCount == 0){
						if((!target_activity_name.equalsIgnoreCase("PM"))){
							log.info("Check 6");
							sendMessageHashMap("TAB_TSLM_LOAN_REF", "Please Add data in TSLM Loan Reference Grid");
							return false;
						}
					}
				}*/
				else if( Req_TYPE.equalsIgnoreCase("AM") && (createAmendValue.equalsIgnoreCase("Y"))){
					log.info("Check 7");
					if(loanCount == 0){
						if((!target_activity_name.equalsIgnoreCase("PM"))){
							log.info("Check 8");
							sendMessageHashMap("TAB_TSLM_LOAN_REF", "Please Add data in TSLM Loan Reference Grid");
							return false;
						}
					}
				}else if( Req_TYPE.equalsIgnoreCase("STL") && createAmendValue.equalsIgnoreCase("Y")){
					log.info("Check 9");
					if(loanCount == 0){
						if((!target_activity_name.equalsIgnoreCase("PM"))){
							log.info("Check 10");
							sendMessageHashMap("TAB_TSLM_LOAN_REF", "Please Add data in TSLM Loan Reference Grid");
							return false;
						}
					}
				}
			}
		}
		log.info("END validateLoanReferencePCPM");
		return true;
	}
	//Ajeet 1129714
	public void setLegalisationFlag(){
		try{	
			String decisionEDAS = formObject.getValue("EDAS_APPROVAL").toString();
			String EDASFlag = "";
			log.info("setLegalisationFlag decisionEDAS: "+decisionEDAS);
			if(decisionEDAS.equalsIgnoreCase("Approved") || decisionEDAS.equalsIgnoreCase("Rejected")){
				EDASFlag = "N";
			}else if (decisionEDAS.equalsIgnoreCase("Pending")){
				EDASFlag = "Y";
			}
			String tableName = "EXT_TFO";
			String sQuery = "UPDATE "+tableName+" SET Legalization_Status_Flag = '"+EDASFlag+"'"+
							" WHERE WI_NAME = '"+sWorkitemID+"'";
			log.info("setLegalisationFlag query :: "+sQuery);
			int sUpdateOutput = formObject.saveDataInDB(sQuery);
			log.info("setLegalisationFlag Output: "+sUpdateOutput);
		} catch (Exception e) {		
			log.error("Inside catch: ",e);
	}
  }

  //Shivanshu 25/5/2023
  public void setReasonForAction(){
	try{
		if("SCC".equalsIgnoreCase(sActivityName)){
			String decCode = formObject.getValue(DEC_CODE).toString();
			String remarks = formObject.getValue("REMARKS").toString();

			log.info("setReasonForAction decCode: "+decCode);
			String decReasonForAction = "";
			if (decCode.equalsIgnoreCase("INTREJ")){            //ATP-402 Change by Shivanshu
				 decReasonForAction = "Internal Policy Issues";
			}else {
				decReasonForAction = formObject.getValue("REASON_FOR_ACTION").toString();
			}
				log.info("setReasonForAction decReasonForAction: "+decReasonForAction);
				String tableName = "EXT_TFO";
				String sQuery = "UPDATE "+tableName+" SET REASON_FOR_ACTION = '"+decReasonForAction+"'"+
								" WHERE WI_NAME = '"+sWorkitemID+"'";
				log.info("setReasonForAction query :: "+sQuery);
				int sUpdateOutput = formObject.saveDataInDB(sQuery);
				log.info("setReasonForAction Output: "+sUpdateOutput);

				log.info("Inside Remarks setting "+decReasonForAction);
				String tableNameSummary = "TFO_DJ_FINAL_DEC_SUMMARY";
				String sQuerySummary = "UPDATE "+tableNameSummary+" SET EXCP_REMARKS = '"+decReasonForAction+" - "+remarks+"' "+
								" WHERE WI_NAME = '"+sWorkitemID+"'";
				log.info("setReasonForAction query :: "+sQuerySummary);
				int sUpdateOutputSummary = formObject.saveDataInDB(sQuerySummary);
				log.info("setReasonForAction Output: "+sUpdateOutputSummary);
			}
		} catch (Exception e) {		
			log.error("Inside catch: ",e);
		}
  	}
	
}
