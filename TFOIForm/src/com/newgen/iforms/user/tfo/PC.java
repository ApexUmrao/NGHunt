package com.newgen.iforms.user.tfo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.newgen.iforms.user.tfo.util.XMLParser;
import com.newgen.mvcbeans.model.WorkdeskModel;
//DART 1129676
public class PC extends Common implements Constants, IFormServerEventHandler {
	public PC(IFormReference formObject) {
		super(formObject);
	}

	@Override
	public void beforeFormLoad(FormDef arg0, IFormReference arg1) {
		//unused
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
		log.info("Inside executeServerEvent PC");
		log.info("Event: " + eventType + ", Control Name: " + controlName + ", Data: " + data);
		sendMessageList.clear();
		String retMsg = getReturnMessage(true); 
		try {
			if(eventType.equalsIgnoreCase(EVENT_TYPE_LOAD)) {			
				if(CONTROL_NAME_FORM.equalsIgnoreCase(controlName)) {
					String winame = formObject.getValue("WI_NAME").toString();
					log.info("Inside form load event -> WorkItem Name: " + winame);
					Boolean view = setUserDetail(); // create control set 
					setProperties();
					intializeStaticValue();
					loadIntCodeDeconLoad();
					loadProdLov(srcChnl, relType, listPrdCode1, PRODUCT_TYPE);	
					pmPcFieldFrmOnLoad(requestType);
					decisionValidation(DEC_CODE);
					tabHandlingMast();
					finalDecisionHandling(DEC_CODE,REJ_RESN_PPM); 
					populateComplianceTab();			
					loadComplianceTabData();
					inputTabFrmHideShow();
					setLoadRefTRVal(EVENT_TYPE_LOAD,"");
					duplicateCheckConfirmation(DUP_CHK_CONFIRMATION, false);
					setSelectToEmptyLov();
					setLovSelect();
					showUTCDetails();  //added by reyaz 5082022
					disableInitCustFrm();
					loadDataLOVFormLoad(ROUTE_TO_PM);
					if(!formObject.getValue(PROCESS_TYPE).toString().equalsIgnoreCase("PT")){
						removeItemFromCombo(SOURCE_CHANNEL, "IPT");
					}
					if(!formObject.getValue(PROCESS_TYPE).toString().equalsIgnoreCase("ET")){ //ATP-469 Shahbaz 04-06-2024
						removeItemFromCombo(SOURCE_CHANNEL, "IET");
					}
					showAmendFrameFieldsPC(); //sheenu
					retMsg = getReturnMessage(view, controlName);
					SetDefaultValAnyDoc_SBLC_AM();//santhosh
					setDataInTRSDTab();
				} else if(PM_DECISION_HISTORY.equalsIgnoreCase(controlName)) {
					log.info("loading decision history ...");
					loadDecisionHistoryListView();
				} //DART 1129676
			} else if(PC_FCUBS_REF.equalsIgnoreCase(controlName)){
				formObject.setValue(TRANSACTION_REFERENCE, formObject.getValue(PC_FCUBS_REF).toString());
			} else if(MANUAL_TAB_CLICK_PC.equalsIgnoreCase(controlName)) {
				log.info("Inside Manual_TAB_CLICK PC");
				enableFieldOnDemand(BUTTON_SUBMIT);
				duplicateCheckPPMPC(DUP_CHK_CONFIRMATION,false);
			} else if(BUTTON_SUBMIT.equalsIgnoreCase(controlName)
					&& validateMandatoryFields(TFO_BRANCH_CITY,"Please select Issuing Center")
					&& validateMandatoryFields(TFO_ASSIGNED_CENTER,"Please select Processing Center")){
				formObject.setStyle(BUTTON_SUBMIT,"disable","true");
				if(submitPCValidations()){
					List<String> paramlist =new ArrayList<>( );
					paramlist.add (PARAM_TEXT+sWorkitemID);
					paramlist.add (PARAM_TEXT+"N");
					formObject.getDataFromStoredProcedure("TFO_DJ_EMAIL_CONFIG", paramlist);
					saveDecHistory();
					saveContractLimitData();  
					//ATP-508 REYAZ 22-08-2024 START 
					String processType=formObject.getValue(PROCESS_TYPE).toString();
					if(processType.equalsIgnoreCase("TSLM Front End")){
						createTSLMPushMsg("R");
					}
					//ATP-508 REYAZ 22-08-2024 END
					if(!"RPM".equalsIgnoreCase(formObject.getValue(DEC_CODE).toString()) 
							&& !"RPPM".equalsIgnoreCase(formObject.getValue(DEC_CODE).toString())) {
						insertIntoNotificationTxn();
						
						 String output= CreateNewWorkitem();  //US_SPRINT4_PT_148
						 
						 if("OpenLinkWIJSP".equalsIgnoreCase(output)){
							 return   getReturnMessage(true, "",output);
						 }
					}
				} else {
					enableControls(BUTTON_SUBMIT);
					log.info("returning false from PC..");
					log.info("sendMessageList="+sendMessageList);
					if(!sendMessageList.isEmpty()){
						return  getReturnMessage(false,"",sendMessageList.get(0)+"###"+ sendMessageList.get(1));
					} else {
						return getReturnMessage(false);
					}
				}
				if("IFP".equalsIgnoreCase(requestCategory)|| "IFA".equalsIgnoreCase(requestCategory) || "IFS".equalsIgnoreCase(requestCategory) || "SCF".equalsIgnoreCase(requestCategory) ){ //ADDED FOR SCF ATP -207
					String utcRequire = formObject.getValue("UTC_REQUIRED").toString();
					String utcBrms = formObject.getValue("UTC_REQUIRED_BRMS").toString();
					String decision = formObject.getValue("DEC_CODE").toString();
					String isGetDocVal = formObject.getValue("IS_GETDOCUMENT_UTC_DONE").toString();
					if("Y".equalsIgnoreCase(isGetDocVal) && "TXNAC".equalsIgnoreCase(decision) && "Yes".equalsIgnoreCase(utcRequire) && ("Yes".equalsIgnoreCase(utcBrms) || "No".equalsIgnoreCase(utcBrms))){
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
					log.info("Data Inserted"+i);
					}
					//insertFCUBSIntegrationCalls("updateDocumentStatusUTC","updateDocumentStatusUTC");
					//updateDocumentStatus();
					log.info("UTC_START_CHECK ends");
				}
			} else if(DUP_CHK_CONFIRMATION.equalsIgnoreCase(controlName) && eventType.equalsIgnoreCase(EVENT_TYPE_CHANGE)){    
				duplicateCheckPPMPC(controlName,true);
			} else if(DEC_CODE.equalsIgnoreCase(controlName)){
				finalDecisionHandling(controlName,"REJ_RESN_PPM");	
			} else if(eventType.equalsIgnoreCase(EVENT_TYPE_LOSTFOCUS)) {//mansi
				if(controlName.equalsIgnoreCase(Q_AMENDMENT_DATA_USER_CNTR_GTEE_EXP_DATE)){
					 if(checkExpDateValidationPC()){
						 formObject.setValue(Q_AMENDMENT_DATA_USER_CNTR_GTEE_EXP_DATE, "");
						 formObject.setValue(Q_AMENDMENT_DATA_FIN_CNTR_GTEE_EXP_DATE, "");	
					 }
				}
			} else if ("UTC_START_CHECK".equalsIgnoreCase(controlName)){
				String columnNames = "SNO,WI_NAME,CALL_NAME,CALL_DT,CALL_STATUS,RETRY_COUNT,VESSEL_NAME";
				log.info("UTC_START_CHECK starts");
				int i=0;
				if(!getUpdateDocDetailsIntegration()){
					String sInsertScript = "INSERT INTO TFO_DJ_INTEGRATION_CALLS ("+columnNames+") VALUES "
							+ "('"+(i+1)+"','" + sWorkitemID + "','updateDocumentStatusUTC',sysdate,'N','0','updateDocumentStatusUTC')";
					log.info("[sInsertScript for Integration Calls]"+sInsertScript);
					formObject.saveDataInDB(sInsertScript);
				}
				log.info("Data Inserted"+i);
				//insertFCUBSIntegrationCalls("updateDocumentStatusUTC","updateDocumentStatusUTC");
				//updateDocumentStatus();
				log.info("UTC_START_CHECK ends");
			}
		} catch (Exception e){
			log.error("Exception in Event- " + eventType + "control name- " +controlName+ ": " + e, e);
		} finally{
			log.info("sendMessageList="+sendMessageList);
			if(!sendMessageList.isEmpty()){
				return  getReturnMessage(false,"",sendMessageList.get(0)+"###"+ sendMessageList.get(1));
			}
		}
		return retMsg;
	}

	@Override
	public String generateHTML(EControl arg0) {
		return null;
	}

	@Override
	public String getCustomFilterXML(FormDef arg0, IFormReference arg1,	String arg2) {
		return "";
	}

	@Override
	public String introduceWorkItemInWorkFlow(IFormReference arg0,	HttpServletRequest arg1, HttpServletResponse arg2) {
		return null;
	}

	@Override
	public String introduceWorkItemInWorkFlow(IFormReference arg0,HttpServletRequest arg1, HttpServletResponse arg2, WorkdeskModel arg3) {
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

	public void pmPcFieldFrmOnLoad(String requestType) {
		log.info("*****************Inside pmFieldFrmOnLoad***********************");		
		if("GRNT".equalsIgnoreCase(requestCategory)){
			if("GA".equalsIgnoreCase(requestType)) {
				log.info("## GA ##");
				formObject.setValue(PRODUCT_TYPE, "T414");
				String isAccValid = formObject.getValue(ACCOUNT_VALID).toString();
				if(null == isAccValid || "0".equalsIgnoreCase(isAccValid)){
					formObject.setValue(ACCOUNT_VALID, "2");
				}
				disableControls(AMEND_TYPE); 
			} else if("GAA".equalsIgnoreCase(requestType)) {
				log.info("## GAA ##");
				formObject.setValue(PRODUCT_TYPE, "T414");
				disableControls("EXP_DATE,TRN_TENOR");	
			}
		}
		log.info("*****************END pmFieldFrmOnLoad***********************");
	}

	private void loadComplianceTabData() {
		log.info("loadComplianceTabData for PC started");
		try {
			formObject.applyGroup(CONTROL_SET_COMPLIANCE_CHECK_DISABLE_FIELDS);
		}catch (Exception e) {
			log.info("exception in loadComplianceTabData: "+e,e);
		}
		log.info("loadComplianceTabData for PM ends");
	}

	public void duplicateCheckConfirmation(String controlName, boolean bChange){
		try {
			String sDuplicateCheckConf = "";
			sDuplicateCheckConf = formObject.getValue(controlName).toString();
			if(bChange){
				formObject.setValue(DEC_CODE, "");
			}
			if("N".equalsIgnoreCase(sDuplicateCheckConf)){
				if("GRNT".equalsIgnoreCase(requestCategory)){
					if("GA".equalsIgnoreCase(requestType) || "GAA".equalsIgnoreCase(requestType)){
						loadDecision("TXNC,REF,RET", DEC_CODE);
						formObject.setValue(DEC_CODE, "REJ");
					}else{
						loadDecision("TXNC,REJ,REF", DEC_CODE);
						formObject.setValue(DEC_CODE, "RET");
					}
				}
				else {
					loadDecision("TXNC,REJ,REF", DEC_CODE);
					formObject.setValue(DEC_CODE, "RET");
				}
				disableControls(DEC_CODE);
			}else{
				enableControls(DEC_CODE);
				decisionValidation(DEC_CODE);
				String sDecision="";
				sDecision  =formObject.getValue(DEC_CODE).toString();
				if(sDecision.isEmpty() || sDecision.trim().equalsIgnoreCase(""))
					formObject.setValue(DEC_CODE, "");
			}
		}catch (Exception e) {
			log.error("exception in duplicateCheckConfirmation: "+e,e);
		}
	}

	public void disableInitCustFrm(){
		try {
			if(("GA".equalsIgnoreCase(requestType) || "GAA".equalsIgnoreCase(requestType))){
				disableControls(GRNT_ACC_CONTROLS);
			}else{
				disableControls(DISABLE_VD_FRAME);
				disableControls(DISABLE_ID_LOV_FRAME);
				disableControls(DISABLE_ID_TXT_FRAME);
			}			
		}catch (Exception e) {
			log.error("exception in disableInitCustFrm: "+e,e);
		}
	}

	protected void loadDefaultMap(){
		try {
			List<List<String>> tmpList=null;
			tmpList = formObject.getDataFromDB("SELECT DEF_NAME,DEF_VALUE FROM TFO_DJ_DEFAULT_MAST");
			if(tmpList != null){
				for(int counter = 0 ; counter< tmpList.size() ; counter++){
					defaultMap.put(tmpList.get(counter).get(0),tmpList.get(counter).get(1));
				}
				log.info("Default map "+ defaultMap.toString());
			}  			
		} catch (Exception e) {
			log.error("exception in loadDefaultMap: "+e,e);
		}	
	}

	@Override
	public void updateDataInWidget(IFormReference arg0, String arg1) {
		//unused
	}

	@Override
	public String validateDocumentConfiguration(String arg0, String arg1, File arg2, Locale arg3) {
		return null;
	}	

	public void changeFCUBSContractNumber(String controlName){
		String createAmendValue=formObject.getValue(CREATE_AMEND_CNTRCT_FCUBS).toString();
		if("Y".equalsIgnoreCase(createAmendValue)){
			formObject.setValue(controlName, "");
			disableControls(controlName);
		}else{
			enableControls(controlName);
		}
	}

	public void setLimitPartyType(String controlName){
		String operationCode = formObject.getValue(OPERATION_CODE).toString();
		String limitPartyType = formObject.getValue(controlName).toString();
		if("ELC_LCA".equalsIgnoreCase(requestType))
		{
			if("ANC".equalsIgnoreCase(operationCode)) {
				enableControls(controlName);
				if("0".equalsIgnoreCase(limitPartyType) || "".equalsIgnoreCase(limitPartyType)){
					formObject.setValue(controlName,"ISB");
				} 
			}else{
				formObject.setValue(controlName,"");
				disableControls(controlName);
			}
		}else if("ELCB_BL".equalsIgnoreCase(requestType))
		{
			String query="Select LC_LIMIT_LINE from ext_tfo where wi_name='"+this.sWorkitemID+"'";
			String lcLimitLine=formObject.getDataFromDB(query).toString();
			log.info("lcLimitType="+lcLimitLine);
			if(!("".equalsIgnoreCase(lcLimitLine)||"null".equalsIgnoreCase(lcLimitLine))){
				enableControls(controlName);
			}else{
				formObject.setValue(controlName, "");
				disableControls(controlName);
			}
		}else{
			formObject.setValue(controlName, "");
			disableControls(controlName);
		}
	}

	protected boolean contractReferenceValidationPC(){
		enableControls(BUTTON_SUBMIT);
		enableControls(BUTTON_PREVIOUS);
		enableControls(BUTTON_SAVE);
		disableControls(BUTTON_NEXT);
		if(PROCESSING_CHECKER.equalsIgnoreCase(sActivityName)){
			if("IFS".equalsIgnoreCase(requestCategory) || "IFP".equalsIgnoreCase(requestCategory) ||
					"TL".equalsIgnoreCase(requestCategory) || "IFA".equalsIgnoreCase(requestCategory)
					|| "SCF".equalsIgnoreCase(requestCategory) ){ ///ADDED BY SHIVANSHU FOR SCF ATP - 207
				String sFCUBCOntractReference = "";
				String sTransactionReference = "";
				sFCUBCOntractReference = formObject.getValue(PC_FCUBS_REF).toString();
				sTransactionReference = formObject.getValue(TRANSACTION_REFERENCE).toString();
				/*if(!validateMandatoryFields("BILL_LN_REFRNCE", "Please enter Bill/Loan Reference number")){
					return false;
				}*/
				if("TL".equalsIgnoreCase(requestCategory) 
						&& !("BC".equalsIgnoreCase(formObject.getValue(IFS_LOAN_GRP).toString()) || 
								"MRPA".equalsIgnoreCase(formObject.getValue(IFS_LOAN_GRP).toString()))) {
					if(!validateMandatoryFields("BILL_LN_REFRNCE", "Please enter Bill/Loan Reference number")){
						return false;
					}
				}
				if(!validateMandatoryFields("PMNT_AUTH_OTH_SYS", "Please select if Payment Authorized in other system")){
					return false;
				}
				if(sTransactionReference.isEmpty() || sTransactionReference.equalsIgnoreCase("")){
					if(sFCUBCOntractReference.isEmpty()||sFCUBCOntractReference.equalsIgnoreCase("")){
						sendMessageHashMap("", "Please Enter FCUBS Contract reference number");
						return false;
					}
					else{
						return true;
					}
				}else{
					return true;
				}
			}else{
				return true;
			}
		}else{
			return true;
		}
	}

	protected boolean submitPCValidations(){
		try {
			String strDecision="";
			String strRemarks="";
			strDecision= formObject.getValue(DEC_CODE).toString();
			strRemarks= formObject.getValue(REMARKS).toString();
			log.info("submitPCValidations  strDecision " + strDecision);	
			if(duplicateCheckValidation()){
				if(!(strDecision.isEmpty() || strDecision.equalsIgnoreCase("0") || strDecision.equalsIgnoreCase(""))){
					if("TXNAC".equalsIgnoreCase(strDecision) && contractReferenceValidationPC() 
							&& contrRefLengthValidation(PC_FCUBS_REF) 
							&& txnRefNoSubmitCheck() && mandatoryFieldsILCPC()
							&& mandatoryFieldsECPC()
							&& mandatoryFieldsILCBPC()
							&& mandatoryFieldsELCBPC()
							&& mandatoryFieldsELPC() 
							&& checkCACheck()
							&& validateLoanReferencePC()
							//&& validateMandatoryFields(FCUBS_PUR_OF_MSG,"Please select FCUBS Purpose of Message.")
							&&PCvalidateMandatoryPurposeMessage() //BY santhosh
							&& PCDocumentValidation()){	  //ATP-375 
						log.info("decsion TXNAC");
						List<String> paramlist =new ArrayList<>( );
						paramlist.add (PARAM_TEXT+sWorkitemID);
						paramlist.add (PARAM_TEXT+formObject.getValue(TRANSACTION_REFERENCE).toString());
						paramlist.add (PARAM_TEXT+formObject.getValue(CUSTOMER_ID).toString());
						formObject.getDataFromStoredProcedure("TFO_DJ_PC_WMS_MASTER", paramlist);
						return true; 	   
					}
					else if("RPM".equalsIgnoreCase(strDecision) || "RPPM".equalsIgnoreCase(strDecision)){
						if(strRemarks.isEmpty() || strRemarks.equalsIgnoreCase("")){
							log.info("remarks: "+ strRemarks);
							sendMessageHashMap(REMARKS, "Please enter return remarks.");
							return false;
						}
						else {
							return true;
						}
					}else if("DOCSSCAN".equalsIgnoreCase(strDecision)){ 
						return true;
					} 
					else if("TXNAU".equalsIgnoreCase(strDecision)){ 
						return true;
					}  
					else 
						return false;
				} else{
					sendMessageHashMap(DEC_CODE, "Please select Decision");
					return false;
				}
			} else{
				return false;
			}
		}catch (Exception e) {
			log.error("exception in submitPCValidations: "+e,e);
			return false;
		}
	}

	protected boolean mandatoryFieldsELPC(){
		try {
			if("ELC".equalsIgnoreCase(requestCategory)){
				if(PROCESSING_CHECKER.equalsIgnoreCase(sActivityName)){
					if("ELC_AOP".equalsIgnoreCase(requestType) && FALSE.equalsIgnoreCase(formObject.getValue("ELC_TXN_FILE_MRKD_AOP").toString())){
						sendMessageHashMap("ELC_TXN_FILE_MRKD_AOP", "Please check Transaction file marked status.");
						return false;
					} else if("ELC_LCT".equalsIgnoreCase(requestType) && FALSE.equalsIgnoreCase(formObject.getValue("ELC_ATC_SWIFT_ACK_CPY_ADV_CUST").toString())){
						sendMessageHashMap("ELC_ATC_SWIFT_ACK_CPY_ADV_CUST", "Please check Attach SWIFT Ack copy (MT720)and email advices to customer status.");
						return false;
					} else if("ELC_LCT".equalsIgnoreCase(requestType) && FALSE.equalsIgnoreCase(formObject.getValue("ELC_ORIG_LC_ENDO_TRANS_AMT").toString())){
						sendMessageHashMap("ELC_ORIG_LC_ENDO_TRANS_AMT", "Please check Original LC endorsed with transfer amount status.");
						return false;
					} else{
						return true;
					}
				} else{
					return true;
				}
			} return true ;
		} catch(Exception e){
			log.error("exception in mandatoryFieldsELC_PC: "+e,e);
			return false;
		}
	}

	private boolean checkCACheck(){
		log.info("reqCat="+requestCategory);
		log.info("reqType="+requestType);
		if("GRNT".equalsIgnoreCase(requestCategory) && ("NI".equalsIgnoreCase(requestType) || "AM".equalsIgnoreCase(requestType ))){			
			log.info("reqType in alert section");
			return validateMandatoryFields("CA_CHCKED","Please select  CA double checked over 500K USD Eqv");
		}else if("SBLC".equalsIgnoreCase(requestCategory) && ("SBLC_NI".equalsIgnoreCase(requestType) || "SBLC_AM".equalsIgnoreCase(requestType ))){  //RR			
			log.info("reqType in alert section");
			return validateMandatoryFields("CA_CHCKED","Please select  CA double checked over 500K USD Eqv");
		}else if("ELC".equalsIgnoreCase(requestCategory) && ("ELC_SLCA".equalsIgnoreCase(requestType) || "ELC_SLCAA".equalsIgnoreCase(requestType ))){  //RR			
			log.info("reqType in alert section");
			return validateMandatoryFields("CA_CHCKED","Please select  CA double checked over 500K USD Eqv");
		}
		return true;
	}

	protected boolean mandatoryFieldsELCBPC(){
		try {
			if("ELCB".equalsIgnoreCase(requestCategory)){
				if(PROCESSING_CHECKER.equalsIgnoreCase(sActivityName)){
					if("ELCB_DISC".equalsIgnoreCase(requestType)){
						if (validateMandatoryFields("ELCB_CRDT_ADV_EMAIL_CUST", "Please select credit advice emailed to customer status.")) {
							return true;
						} else {
							return false;
						}
					} else{
						return true;
					}
				} return true;
			} else{
				return true;
			}
		} catch (Exception e) {
			log.error("exception in mandatoryFieldsELCB_PC: "+e,e);
			return false;
		}
	}
	protected boolean mandatoryFieldsILCBPC(){
		try {
			if("ILCB".equalsIgnoreCase(requestCategory)){
				if(PROCESSING_CHECKER.equalsIgnoreCase(sActivityName)){
					if("ILCB_BS".equalsIgnoreCase(requestType) && FALSE.equalsIgnoreCase(formObject.getValue("ILCB_AUTH_FCUBS_TEL_SWFT_MSG").toString())){
						sendMessageHashMap("ILCB_AUTH_FCUBS_TEL_SWFT_MSG", "Please check transaction authorisation status.");
						return false;
					}else{
						return true;
					}
				} return true;
			} else{
				return true;
			}
		} catch (Exception e) {
			log.error("exception in mandatoryFieldsILCB_PC: "+e,e);
			return false;
		}
	}

	protected boolean mandatoryFieldsILCPC(){
		try {
			enableControls(BUTTON_SUBMIT);
			enableControls(BUTTON_PREVIOUS);
			enableControls(BUTTON_SAVE);
			disableControls(BUTTON_NEXT);
			if("ILC".equalsIgnoreCase(requestCategory)){
				if(PROCESSING_CHECKER.equalsIgnoreCase(sActivityName)){
					if(("ILC_NI".equalsIgnoreCase(requestType) || "ILC_AM".equalsIgnoreCase(requestType) 
							|| "ILC_UM".equalsIgnoreCase(requestType)) && FALSE.equalsIgnoreCase(formObject.getValue("SWIFT_LMT_VERIFY").toString())){
						sendMessageHashMap("SWIFT_LMT_VERIFY", "Please verify SWIFT and Limit");
						return false;
					} else if(("ILC_NI".equalsIgnoreCase(requestType) || "ILC_AM".equalsIgnoreCase(requestType) 
							|| "ILC_UM".equalsIgnoreCase(requestType)) 
							&& !validateMandatoryFields("CA_CHCKED", "Please select CA Double checked condition")){
						return false;
					}
					else 
						return true;
				}else{
					return true;
				} 
			} else 
				return true;
		} catch (Exception e) {
			log.error("exception in mandatoryFieldsILC_PC: "+e,e);
			return false;
		}
	}
	protected boolean mandatoryFieldsECPC(){
		try {
			enableControls(BUTTON_SUBMIT);
			enableControls(BUTTON_PREVIOUS);
			enableControls(BUTTON_SAVE);
			disableControls(BUTTON_NEXT);
			if("EC".equalsIgnoreCase(requestCategory)){
				if(PROCESSING_CHECKER.equalsIgnoreCase(sActivityName)){
					if((!("EC_BL".equalsIgnoreCase(requestType))) 
							&& FALSE.equalsIgnoreCase(formObject.getValue("EC_VER_TXN_DTL_AUTH").toString())){
						sendMessageHashMap("EC_VER_TXN_DTL_AUTH", "Please check transaction details verified status.");
						return false;
					}else if(("EC_LDDI".equalsIgnoreCase(requestType)) && FALSE.equalsIgnoreCase(formObject.getValue("EC_CHK_CORR_BNK_ADD_AWB").toString())){ 
						sendMessageHashMap("EC_CHK_CORR_BNK_ADD_AWB", "Please check correct bank address in courier awb status.");
						return false;
					}else
						return true;
				} else {
					return false;
				}
			} else { 
				return true;
			}		
		} catch (Exception e) {
			log.error("exception in mandatoryFieldsEC_PC: "+e,e);
			return false;
		}
	}
	
	public boolean PCvalidateMandatoryPurposeMessage()  
	{
		if(("SBLC".equalsIgnoreCase(requestCategory) && "SBLC_NI".equalsIgnoreCase(requestType))
					|| ("ELC".equalsIgnoreCase(requestCategory) && "ELC_SLCA".equalsIgnoreCase(requestType))
					|| ("ILC".equalsIgnoreCase(requestCategory) && "ILC_UM".equalsIgnoreCase(requestType)))
	     {
			return validateMandatoryFields(FCUBS_PUR_OF_MSG,"Please select FCUBS Purpose of Message.");
		}
		else if(("GRNT".equalsIgnoreCase(requestCategory) && "NI".equalsIgnoreCase(requestType))){//santhosh
			return validateMandatoryFields(PURPOSE_OF_MESSAGE,"Please select Purpose of Message.")
					&& validateMandatoryFields(FCUBS_PUR_OF_MSG,"Please select FCUBS Purpose of Message.");
		}
		return true;
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
	
	private boolean validateLoanReferencePC(){  //BY KISHAN TSLM
		log.info("INSIDE validateLoanReferencePC");
		String Req_CAT = formObject.getValue(REQUEST_CATEGORY).toString();
		String Req_TYPE = formObject.getValue(REQUEST_TYPE).toString();
		String Processing_System = formObject.getValue(PROCESSING_SYSTEM).toString();
		String createAmendValue = formObject.getValue(CREATE_AMEND_CNTRCT_FCUBS).toString();
		int loanCount = getGridCount("TAB_TSLM_LOAN_REF");
		if (Req_CAT.equalsIgnoreCase("IFA")|| Req_CAT.equalsIgnoreCase("IFS")|| Req_CAT.equalsIgnoreCase("IFP") || Req_CAT.equalsIgnoreCase("SCF")) { //ATP -207
			log.info("Check 1");
			if (Processing_System.equalsIgnoreCase("T")) {
				log.info("Check 2");
				if (Req_TYPE.equalsIgnoreCase("LD") && createAmendValue.equalsIgnoreCase("N")) {
					log.info("Check 3");
					if(loanCount == 0){
						log.info("Check 6");
						sendMessageHashMap("TAB_TSLM_LOAN_REF", "Please Add data in TSLM Loan Reference Grid");
						return false;
					}
				}/*else if(Req_CAT.equalsIgnoreCase("IFA") && Req_TYPE.equalsIgnoreCase("IFA_CTP")
						&& createAmendValue.equalsIgnoreCase("N") ){
					log.info("Check 5");
					if(loanCount == 0){
						log.info("Check 6");
						sendMessageHashMap("TAB_TSLM_LOAN_REF", "Please Add data in TSLM Loan Reference Grid");
						return false;
					}
				}*/
				else if( Req_TYPE.equalsIgnoreCase("AM") && (createAmendValue.equalsIgnoreCase("N"))){
					log.info("Check 7");
					if(loanCount == 0){
						log.info("Check 8");
						sendMessageHashMap("TAB_TSLM_LOAN_REF", "Please Add data in TSLM Loan Reference Grid");
						return false;
					}
				}else if( Req_TYPE.equalsIgnoreCase("STL") && createAmendValue.equalsIgnoreCase("N") ){
					log.info("Check 9");
					if(loanCount == 0){
						log.info("Check 10");
						sendMessageHashMap("TAB_TSLM_LOAN_REF", "Please Add data in TSLM Loan Reference Grid");
						return false;
					}
				}else if((Req_TYPE.equalsIgnoreCase("PD") || Req_TYPE.equalsIgnoreCase("PDD"))&& createAmendValue.equalsIgnoreCase("N") ){ //ADDED BY SHIVANSHU FOR SCF ATP -207
					log.info("Check 11");
					if(loanCount == 0){
						log.info("Check 12");
						sendMessageHashMap("TAB_TSLM_LOAN_REF", "Please Add data in TSLM Loan Reference Grid");
						return false;
					}
				}
			}
		}
		log.info("END validateLoanReferencePC");
		return true;
	}
	private void showAmendFrameFieldsPC(){
		String control[] = null;
		try{
			if("ELC_LCAA".equalsIgnoreCase(requestType) ||"ELC_LCC".equalsIgnoreCase(requestType) ||"ILC_AM".equalsIgnoreCase(requestType)){
				control = AMEND_FRAME_FIELDS.split("#");
				hideshowFrmInputTab(AMEND_FRAME_FIELDS,true);
			}else if("GRNT".equalsIgnoreCase(requestCategory) && "AM".equalsIgnoreCase(requestType)){
				control = GRNT_AM_AMEND_FRAME_FIELDS.split("#");
				hideshowFrmInputTab(GRNT_AM_AMEND_FRAME_FIELDS,true);
            }else if(("ELC".equalsIgnoreCase(requestCategory) && "ELC_SLCAA".equalsIgnoreCase(requestType))){
				control = SBLC_AM_AMEND_FRAME_FIELDS.split("#");
				hideshowFrmInputTab(SBLC_AM_AMEND_FRAME_FIELDS,true);
            }
            else if(("SBLC".equalsIgnoreCase(requestCategory) && "SBLC_AM".equalsIgnoreCase(requestType))){
				control = SBLC_AM_ONLY_AMEND_FRAME_FIELDS.split("#");
				hideshowFrmInputTab(SBLC_AM_AMEND_FRAME_FIELDS,true);
			}
            else if(("GRNT".equalsIgnoreCase(requestCategory) && "GAA".equalsIgnoreCase(requestType))){
				control = GRNT_GAA_AMEND_FRAME_FIELDS.split("#");
				hideshowFrmInputTab(GRNT_GAA_AMEND_FRAME_FIELDS,true);
            }
			for (int counter = 0; counter < control.length; counter++ ) {
				  String showValues="LABEL_"+control[counter]+"#"+"Q_Amendment_Data_USER_"+control[counter]+"#"+"Q_Amendment_Data_PT_"+control[counter]
						  +"#"+"Q_Amendment_Data_FC_"+control[counter]+"#"+"Q_Amendment_Data_FIN_"+control[counter];
				hideshowFrmInputTab(showValues,true);
			}
		}catch(Exception e){
			log.error("Exception in showAmendFrameFieldsPM",e);
		}
	}
	
	//santhosh
		public void SetDefaultValAnyDoc_SBLC_AM()
		{
		if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("SBLC_AM"))
		{
			hideshowFrmInputTab(SBLC_AM_LC_FRAME_FIELDS,true);
			hideshowFrmInputTab(SBLC_AM_LC_FRAME_HIDE_FIELDS,false);
			disableControls(LC_DOC_COURIER);
		}
		}
		//Mansi 
		protected boolean checkDateValidationPC(String sUDate, String sUCntrDate){
		try {
			log.info("UDate  "+sUDate);
			Date dUDate=null;
			Date dUCntrDate = null;
			//dUDate=formObject.getValue(sUDate).toString();
			//dUCntrDate=formObject.getValue(sUCntrDate).toString();
			try{
				dUDate = sdf.parse(sUDate);
				dUCntrDate = sdf.parse(sUCntrDate);
				log.info("getting converted date"+dUDate);

			}catch(Exception e){
				log.error("Exception: ",e);
			}
			log.info("counterDate "+dUCntrDate);
			log.info("userDate "+dUDate);
			if(dUCntrDate.equals(dUDate)){
				return false;
			}
			else if(dUCntrDate.before(dUDate)){
				return true;	
			}else{
				return false;	
			}
		} catch (Exception e) {		
			log.error("Exception: ",e);
			return false;
		}
	}

	protected Boolean checkExpDateValidationPC(){
		log.info("Inside checkExpDateValidation");

		String msg="";
		String gteecntrexpdate=formObject.getValue(Q_AMENDMENT_DATA_USER_CNTR_GTEE_EXP_DATE).toString();
		String usercntrexpdate=formObject.getValue(Q_AMENDMENT_DATA_USER_EXPIRY_DATE).toString();

		if ("AM".equalsIgnoreCase(requestType)) {
			
			if(checkDateValidationPC(usercntrexpdate,gteecntrexpdate)) {
				log.info("Inside checkDateValidation for showing alert");
				msg=ALERT_CNTRGTEE_DATE;
				sendMessageHashMap(gteecntrexpdate,msg);
				log.info("getting message"+msg);
				return true;
			}
		}
		return false;
	}
	
	//ATP-375 24-01-2024 JAMSHED 
	//START
	private boolean PCDocumentValidation() { 
		if("ELCB_BL".equalsIgnoreCase(requestType) || "EC_BL".equalsIgnoreCase(requestType) ||
				"ELCB_AM".equalsIgnoreCase(requestType) || "EC_AM".equalsIgnoreCase(requestType)){
			log.info("INSIDE PCDocumentValidation Remmitance Letter document check");
			getDocumentDHL();
		}
		return true;
	}
	//END ATP-375 24-01-2024 JAMSHED
	
}