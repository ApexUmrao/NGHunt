package com.newgen.iforms.user.tfo;

import java.awt.HeadlessException;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.newgen.iforms.EControl;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.iforms.user.config.Constants;
import com.newgen.iforms.user.tfo.util.XMLParser;
import com.newgen.mvcbeans.model.WorkdeskModel;
//DART 1132070 
//DART 1130791
public  class PPM extends Common implements Constants, IFormServerEventHandler {
	private static final String sCustHoldingAccWithUs = null;
	Boolean successStatus = true;
	String retMsg =""; 
	String msg="true";
	public String SWIFT_UTILITY_FLAG = "";
	public boolean discrepancyFlag = false;
	boolean bSubmit = true;
	boolean insertFlag;
	//DART 1130791
	public PPM(IFormReference formObject) {
		super(formObject);
	}
//DART 1132890 AYUSH
	@Override
	public void beforeFormLoad(FormDef arg0, IFormReference arg1) {
		//unused
	}
//DART 1132940
	@Override
	public String executeCustomService(FormDef arg0, IFormReference arg1,
			String arg2, String arg3, String arg4) {
		return null;
	}
//DART 1129679 Ayush
	@Override
	public JSONArray executeEvent(FormDef arg0, IFormReference arg1,
			String arg2, String arg3) {
		return null;
	}

	public void loadDecisionSummaryRepeaterPPM()
	{
		JSONArray jsonArray = new JSONArray();
		try {//DART 1132054 
			String qryRepeater = "";
			String processType=formObject.getValue(PROCESS_TYPE).toString();
			log.info("loadDecisionSummaryRepeaterPM: " + processType);
			String requestType = formObject.getValue(REQUEST_TYPE).toString();  // ATP-476 REYAZ 03-05-2024
			if (processType.equalsIgnoreCase("TSLM Front End")) { // ATP-384 REYAZ 09-05-2024
				// ATP-486 REYAZ 03-05-2024 START
					qryRepeater = "SELECT 'Document Review' as TAB_NAME, REFCODE as REFER_TO, DECISION,USERCMNT, EXISTING_EMAIL, '',USERCMNT FROM TFO_DJ_TSLM_DOCUMENT_REVIEW WHERE WI_NAME='"
							+ this.sWorkitemID + "' and FLAG_DEL='N'" + " UNION "
							+ "SELECT 'Signature and Acc Bal Check' as TAB_NAME, REFCODE  as REFER_TO, DECISION,USERCMNT , EXISTING_EMAIL, '',USERCMNT FROM TFO_DJ_TSLM_REFERRAL_DETAIL WHERE WI_NAME='"
							+ this.sWorkitemID + "' and FLAG_DEL='N'" + " UNION "
							+ "SELECT 'Limit/Credit Review' as TAB_NAME, REFCODE  as REFER_TO, DECISION, USERCMNT, EXISTING_EMAIL, '',USERCMNT  FROM TFO_DJ_TSLM_LIMIT_CREDIT_REVIEW WHERE WI_NAME='"
							+ this.sWorkitemID + "' and FLAG_DEL='N'";
			} // ATP-486 REYAZ 09-05-2024 START 
			else  {  
				qryRepeater = "SELECT 'Signature and Acc Bal Check', REFER_TO, DECISION, EXCP_RMRKS, EXISTING_EMAIL, '', EXCP_RMRKS FROM TFO_DJ_REF_SIG_ACC WHERE WI_NAME='"
						+ this.sWorkitemID + "' and FLAG_DEL='N'" + " UNION "
						+ "SELECT 'Document Review', REFER_TO, DECISION, EXCP_RMRKS, EXISTING_EMAIL, '', EXCP_RMRKS FROM TFO_DJ_REF_DOC_RVW WHERE WI_NAME='"
						+ this.sWorkitemID + "' and FLAG_DEL='N'" + " UNION "
						+ "SELECT 'Text Vetting', REFER_TO, DECISION, EXCP_RMRKS, EXISTING_EMAIL, '', EXCP_RMRKS FROM TFO_DJ_REF_TXT_VET WHERE WI_NAME='"
						+ this.sWorkitemID + "' and FLAG_DEL='N'" + " UNION "
						+ "SELECT 'Limit/Credit Review', REFER_TO, DECISION, EXCP_RMRKS, EXISTING_EMAIL, '', EXCP_RMRKS FROM TFO_DJ_REF_LMT_CRDT WHERE WI_NAME='"
						+ this.sWorkitemID + "' and FLAG_DEL='N' UNION SELECT 'Document Review', REFCODE, DECISION, USERCMNT, EXISTING_EMAIL, '',USERCMNT FROM TFO_DJ_TSLM_DOCUMENT_REVIEW WHERE WI_NAME='"
						+ this.sWorkitemID + "' and FLAG_DEL='N'" + " UNION "
						+ "SELECT 'Signature and Acc Bal Check', REFCODE, DECISION, USERCMNT, EXISTING_EMAIL,'',USERCMNT FROM TFO_DJ_TSLM_REFERRAL_DETAIL WHERE WI_NAME='"
						+ this.sWorkitemID + "' and FLAG_DEL='N'" + " UNION "
						+ "SELECT 'Limit/Credit Review', REFCODE, DECISION,USERCMNT, EXISTING_EMAIL, '',USERCMNT FROM TFO_DJ_TSLM_LIMIT_CREDIT_REVIEW WHERE WI_NAME='"
						+ this.sWorkitemID + "' and FLAG_DEL='N'";
			} // ATP-384 REYAZ 09-05-2024 START 

			log.info("checkOnsQuery >>>>>>>>  " + qryRepeater);
			List	listQryResult = formObject.getDataFromDB(qryRepeater);

			log.info(" sOutRepeater  listQryResult   ===== " + listQryResult);
			formObject.clearTable(LISTVIEW_FINAL_DECISION);
			
			// added by reyaz 17-01-2023
			String sQuery1="select AUTO_TRIGGER_EMAIL,TFO_GROUP_MAILID from TFO_DJ_AUTO_TRIGGER_MASTER WHERE SOURCING_CHANNEL='TSLMSY'";  //ATP-379 15-FEB-24 --JAMSHED
			List<List<String>> lresultSet1=null;
			String autoTrigger ="";
			String groupMailId ="";
			lresultSet1 =  formObject.getDataFromDB(sQuery1);
			log.info("lresultSet1: "+lresultSet1);
			if(lresultSet1!=null){
				if(!lresultSet1.isEmpty()){
					autoTrigger=lresultSet1.get(0).get(0);
					groupMailId=lresultSet1.get(0).get(1);
					log.info("autoTrigger: "+autoTrigger);
					log.info("groupMailId: "+groupMailId);
					}
			}
			
			if(listQryResult!=null && !listQryResult.isEmpty()){
				for (int i = 0; i < listQryResult.size(); i++) {
					log.info(" in loadDecisionSummaryRepeaterPPM");
					String repeaterKey = "";
					String additionalMailId = "";
					JSONObject jsonObject = new JSONObject();
					log.info("In loadlist view "+((String) ((List) listQryResult.get(i)).get(0)));
					jsonObject.put("Module",((String) ((List) listQryResult.get(i)).get(0)));
					jsonObject.put("Referred To",((String) ((List) listQryResult.get(i)).get(1)));
					jsonObject.put("Decision",((String) ((List) listQryResult.get(i)).get(2)));
					jsonObject.put("Exception Remarks",((String) ((List) listQryResult.get(i)).get(3)));
					jsonObject.put("Existing Email Id",((String) ((List) listQryResult.get(i)).get(4)));
					jsonObject.put("Email Remarks",((String) ((List) listQryResult.get(i)).get(6)));
					
					
					if(((String) ((List) listQryResult.get(i)).get(1)).equalsIgnoreCase("RM")
							&& (autoTrigger.trim().equalsIgnoreCase("Y")||autoTrigger.trim().equalsIgnoreCase("Yes"))
							&& processType.equalsIgnoreCase("TSLM Front End")){
					jsonObject.put("Additional Email Id",groupMailId);
					}else{
					repeaterKey = ((String) ((List) listQryResult.get(i)).get(0))
							+(String) ((List) listQryResult.get(i)).get(1);
					if(hmapAdditionalMail.containsKey(repeaterKey)){
						additionalMailId = (String) hmapAdditionalMail.get(repeaterKey);
					}
					jsonObject.put("Additional Email Id",additionalMailId);
					}
					jsonArray.add(jsonObject);

					hmapAdditionalMail.put(repeaterKey, additionalMailId);
				}
				formObject.addDataToGrid(LISTVIEW_FINAL_DECISION, jsonArray);
				log.info(" in loadDecisionSummaryRepeaterPPM jsonArray="+jsonArray.size());
				log.info(" in loadDecisionSummaryRepeaterPPM hmapAdditionalMail="+hmapAdditionalMail.size());
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}  
		setFlag();
		log.info("In loadDecisionSummaryRepeaterPPM DEC_CODE: "+DEC_CODE);
		decisionValidationPPM(DEC_CODE);
	}
//DART 1129710 AYUSH
	protected void setFlag(){
		try {
			log.info(" in setFlag ");
			if("PP_MAKER".equalsIgnoreCase(sActivityName)){
				String sQuery = "";
				String processType=formObject.getValue(PROCESS_TYPE).toString();
				log.info("loadDecisionSummaryRepeaterPM: " + processType);
	            if(processType.equalsIgnoreCase("TSLM Front End")){
	             sQuery = "SELECT REFCODE FROM TFO_DJ_TSLM_DOCUMENT_REVIEW WHERE WI_NAME='" + this.sWorkitemID + "' and FLAG_DEL='N' "
	    					+ " UNION SELECT REFCODE FROM TFO_DJ_TSLM_REFERRAL_DETAIL WHERE WI_NAME='" + this.sWorkitemID + "' and FLAG_DEL='N' "
	                        + " UNION SELECT REFCODE FROM TFO_DJ_TSLM_LIMIT_CREDIT_REVIEW WHERE WI_NAME='" + this.sWorkitemID + "'and FLAG_DEL='N' ";
	            } //ATP -384 REYAZ 09-05-2024 START
	            else  {  
		             sQuery = "SELECT REFCODE as REFER_TO  FROM TFO_DJ_TSLM_DOCUMENT_REVIEW WHERE WI_NAME='" + this.sWorkitemID + "' and FLAG_DEL='N' "
		    					+ " UNION SELECT REFCODE as REFER_TO FROM TFO_DJ_TSLM_REFERRAL_DETAIL WHERE WI_NAME='" + this.sWorkitemID + "' and FLAG_DEL='N' "
		                        + " UNION SELECT REFCODE as REFER_TO FROM TFO_DJ_TSLM_LIMIT_CREDIT_REVIEW WHERE WI_NAME='" + this.sWorkitemID + "'and FLAG_DEL='N' "
		                        + " UNION SELECT REFER_TO FROM TFO_DJ_REF_SIG_ACC WHERE WI_NAME='"+this.sWorkitemID+"' and FLAG_DEL='N' "
		                        + " UNION  SELECT REFER_TO FROM TFO_DJ_REF_DOC_RVW WHERE WI_NAME='"+this.sWorkitemID+"' and FLAG_DEL='N' "
		                        + " UNION  SELECT REFER_TO FROM TFO_DJ_REF_LMT_CRDT WHERE WI_NAME='"+this.sWorkitemID+"' and FLAG_DEL='N' "
		                        + " UNION  SELECT REFER_TO FROM TFO_DJ_REF_TXT_VET WHERE WI_NAME='"+this.sWorkitemID+"' and FLAG_DEL='N' ";
		            }
	            //ATP -384 REYAZ 09-05-2024 END
				List<List<String>> lresultSet=null;
				lresultSet =  formObject.getDataFromDB(sQuery);
				log.info("setFlag output="+lresultSet);
				if(lresultSet!=null){
					log.info("in setFlag lresultSet=");
					if(!lresultSet.isEmpty()){
						log.info("in setFlag lresultSet.size");
						for(int i=0;i<lresultSet.size();i++){
							if("RM".equalsIgnoreCase(lresultSet.get(i).get(0)))
								formObject.setValue(IS_RM_PPM, "Y");
							else if("TSD".equalsIgnoreCase(lresultSet.get(i).get(0)))
								formObject.setValue(IS_REF_PPM, "Y");
							else if("Customer".equalsIgnoreCase(lresultSet.get(i).get(0))
									||"Customer Only Through Email".equalsIgnoreCase(lresultSet.get(i).get(0)))
								formObject.setValue(IS_CR_PPM, "Y");
							else if("Legal".equalsIgnoreCase(lresultSet.get(i).get(0)))
								formObject.setValue(IS_LEGAL_PPM, "Y");
							else if("Correspondent Bank".equalsIgnoreCase(lresultSet.get(i).get(0)))
								formObject.setValue(IS_CB_PPM, "Y");
							else if("Trade Sales".equalsIgnoreCase(lresultSet.get(i).get(0)))
								formObject.setValue(IS_TRADE_PPM, "Y");
							else if("TB Sales".equalsIgnoreCase(lresultSet.get(i).get(0)))
								formObject.setValue(IS_TRADE_PPM, "Y");
						}
					}else{
						log.info(" in else1 setting flags N setFlag");
						log.info("in else setFlag lresultSet.size");
						formObject.setValue(IS_RM_PPM, "N");
						formObject.setValue(IS_REF_PPM, "N");
						formObject.setValue(IS_CR_PPM, "N");
						formObject.setValue(IS_LEGAL_PPM, "N");
						formObject.setValue(IS_CB_PPM, "N");
						formObject.setValue(IS_TRADE_PPM, "N");
					}
				} else{
					log.info(" in else2 setting flags N setFlag");
					log.info("in else 2nd setFlag lresultSet.size");
					formObject.setValue(IS_RM_PPM, "N");
					formObject.setValue(IS_REF_PPM, "N");
					formObject.setValue(IS_CR_PPM, "N");
					formObject.setValue(IS_LEGAL_PPM, "N");
					formObject.setValue(IS_CB_PPM, "N");
					formObject.setValue(IS_TRADE_PPM, "N");
				}
			}
		} catch (Exception e) {
			log.error("Exception in setFlag",e);

		}
	}

//DART 1132891
	private boolean validateVerifyDtlsTab() {
		String requestcat =  formObject.getValue(REQUEST_CATEGORY).toString();
		String requestType = formObject.getValue(REQUEST_TYPE).toString();

		if("Y".equalsIgnoreCase(formObject.getValue(PT_UTILITY_FLAG).toString())){  //PT 369
			return (validateMandatoryFieldsPPM(SOURCE_CHANNEL, "Please Select Source Channel")
					&& validateMandatoryFieldsPPM(RELATIONSHIP_TYPE, "Please Select Transcation Branch Code")
					&& validateMandatoryFieldsPPM(TFO_BRANCH_CITY,"Please select Issuing Center"));
		}else 
			return (validateMandatoryFieldsPPM(SOURCE_CHANNEL, "Please Select Source Channel")
					&& validateMandatoryFieldsPPM(RELATIONSHIP_TYPE, "Please Select Transcation Branch Code")
					&& validateMandatoryFieldsPPM(DELIVERY_BRANCH, "Please Select Transcation Delivery Branch")
					&& ((requestcat.equalsIgnoreCase("IFA") || requestcat.equalsIgnoreCase("IFS") ||   //BY KISHAN TSLM
							requestcat.equalsIgnoreCase("IFP") || requestcat.equalsIgnoreCase("SCF")) 
							&& (requestType.equalsIgnoreCase("LD") || requestType.equalsIgnoreCase("PD") || requestType.equalsIgnoreCase("PDD"))  //ATP 199
							&& validateMandatoryFieldsPPM(PROCESSING_SYSTEM, "Please Select Processing System") ));
	}//DART 1132056
	//DART 1132058 AYUSH

	public boolean implementTabNavigation(String controlname,String data) {
		log.info(">>>>>>>>>> Inside navigation method <<<<<<<<<< -- A"+data+"controlname="+controlname);
		try {
			String a[] =data.split(",");
			String button=a[0];
			int sheetID=Integer.parseInt(a[1]);
			int counterListCount=Integer.parseInt(a[2]);
			int signListCount=Integer.parseInt(a[3]);
			int docCheckListCount=Integer.parseInt(a[4]);
			int docReferListCount=Integer.parseInt(a[5]);
			int textListCount=Integer.parseInt(a[6]);;
			int limitChecklistCount=Integer.parseInt(a[7]);
			int limitReferListCount=Integer.parseInt(a[8]);
			int fdRefreealListCount=Integer.parseInt(a[9]);
			int tslmCounterListCount=Integer.parseInt(a[10]);
			int tslmInvoiceListCount=Integer.parseInt(a[11]);

			String request_Cat = formObject.getValue("REQUEST_CATEGORY").toString();

			//DART 1129710 AYUSH
			if("onClickTabPPM".equalsIgnoreCase(controlname)){
				if (sheetID==1) {  //input details
					log.info("Tab1-2");
					checkOnTrnstenor(TRN_TENOR);
					SetGrntFCUBSDefaultValue();//santhosh
					setTBMLExt(); //ATP-487 04-07-2024 --JAMSHED
					SetSBLC_NI_PUROFMSGDefaultValue();//santhosh
					SetUTCValues();
					log.info("PROCESS_TYPE :"+formObject.getValue(PROCESS_TYPE).toString());
					if(formObject.getValue(PROCESS_TYPE).toString().equalsIgnoreCase("TSLM Front End")){
						
						if(formObject.getValue(INF_CHARGE_ACC_TITLE).toString().trim().isEmpty()){
							setAccountDetails(fetchFCRCustomer((String) formObject.getValue(CUSTOMER_ID), "GetCustomerSummary"));
							setTitleCurrency(INF_CHARGE_ACC_NUM,INF_CHARGE_ACC_TITLE,INF_CHARGE_ACC_CURR);
							setSettelmentAccDetails(INF_CHARGE_ACC_NUM);
							fetchAcctDetailsICCS();
							setTitleCurrency(INF_SETTLEMENT_ACC_NUM,INF_SETTLEMENT_ACC_TITLE,INF_SETTLEMENT_ACC_CURR);
							setTitleCurrency(INF_CREDIT_ACC_NUM,INF_CREDIT_ACC_TITLE,INF_CREDIT_ACC_CURR);
						}
					}
					
				}else if (sheetID==9) {  //Final Decision
					saveUTCJustfyDecHistory();
					enableFieldOnDemand(BUTTON_SUBMIT);
					loadDecisionSummaryRepeaterPPM();
					duplicateCheckPPMPC(DUP_CHK_CONFIRMATION,false);
					icgRmCheck("",fdRefreealListCount);
					setEmailFlag(fdRefreealListCount);
					handlingRouteToPPM(DEC_CODE);
					duplicateCheckPPMPC(TSLM_INVOICE_CHK_CONFIRM,false);	//CODE BY MOKSH
					SetGrntElcDecDefaultValue();//Code by mansi
					SetGrntFCUBSDefaultValue();//santhosh
					SetSBLC_NI_PUROFMSGDefaultValue();//santhosh
				}
			}else if("saveAndNextPreHook".equalsIgnoreCase(controlname)){
				if (sheetID==0) {   //verfiy details
					log.info("Tab0-1");
					SetUTCValues();
					if (validateVerifyDtlsTab()) {
						SetGrntFCUBSDefaultValue();
						disableFieldOnDemand(BUTTON_SUBMIT);
					} else {
						disableFieldOnDemand(BUTTON_SUBMIT);
						return false;
					}
				} else if (sheetID==1) {  //input details
					log.info("inside input detail");
					if (validateTSLMFetchButton()){
						disableFieldOnDemand(BUTTON_SUBMIT);
					}
					
					
					
					if(ppmValidateInputFrm(formObject.getValue(REQUEST_TYPE).toString())){
						disableFieldOnDemand(BUTTON_SUBMIT);
					}else{
						disableFieldOnDemand(BUTTON_SUBMIT);
						return false;
					}
				} else if (sheetID==2) {   //Customer Details
					disableFieldOnDemand(BUTTON_SUBMIT);
					// ATP-463 24-06-2024 --JAMSHED START
					validatePastDue();
					// ATP-463 24-06-2024 --JAMSHED END
				}else if (sheetID==3) { //Counter Party Details
					String request_type = formObject.getValue(REQUEST_TYPE).toString();
					log.info("Tab3 -4");
					//CODE BY MOKSH

					log.info("Tab3 -4 COUNTER DET");
					if (request_type.equalsIgnoreCase("LD")   
							&& formObject.getValue(PROCESSING_SYSTEM).toString().equalsIgnoreCase("F")) {
						if( validateCPDTab(counterListCount,"add_Qvar_cpd_details","Qvar_cpd_details")){
							log.info("Tab3 -4 COUNTER DET ENTER 11");
							disableFieldOnDemand(BUTTON_SUBMIT);
						}else {
							log.info("Tab3 -4 COUNTER DET ENTER 12");
							disableFieldOnDemand(BUTTON_SUBMIT);
							return false;
						}	
					}

					if(request_type.equalsIgnoreCase("LD") && tslmCounterListCount == 0
							&& formObject.getValue(PROCESSING_SYSTEM).toString().equalsIgnoreCase("T") ){
						if( validateCPDTab(counterListCount,"add_Qvar_cpd_details","Qvar_cpd_details")){
							log.info("Tab3 -4 COUNTER DET ENTER 11");
							disableFieldOnDemand(BUTTON_SUBMIT);
						}else {
							log.info("Tab3 -4 COUNTER DET ENTER 12");
							disableFieldOnDemand(BUTTON_SUBMIT);
							return false;
						}	
					}
					
					log.info("Tab3 -4 TSLM INVOICE NO LOAN");//DART 1129121
					if( (request_type.equalsIgnoreCase("LD")  || request_type.equalsIgnoreCase("PD") || requestType.equalsIgnoreCase("PDD"))   //ATP - 202
							&& (request_Cat.equalsIgnoreCase("IFA") ||request_Cat.equalsIgnoreCase("IFS")
									|| request_Cat.equalsIgnoreCase("IFP")|| request_Cat.equalsIgnoreCase("SCF"))
									&& formObject.getValue(PROCESSING_SYSTEM).toString().equalsIgnoreCase("T")
									&& formObject.getValue(STANDALONE_LOAN).toString().equalsIgnoreCase("1")
									&& validateCPDTab(tslmInvoiceListCount,"add_Q_TSLM_Invoice_No_SA_Loan","Q_TSLM_Invoice_No_SA_Loan") ){
						log.info("Tab3 -4 TSLM INVOICE NO LOAN ENTER");
						disableFieldOnDemand(BUTTON_SUBMIT);
					}else{
						disableFieldOnDemand(BUTTON_SUBMIT);
						return false;
					}
					//END OF CODE BY MOKSH
					//DART 1129121
				}
				else if (sheetID==4) { //SIgnature and ACC bal check 
					log.info("Tab4-5");
					loadTabListViewTSLM("","SIGN_REFERRAL_ID");
					//setDecisionValidationTSLM("TFO_DJ_TSLM_REFERRAL_DETAIL");
					if (signatureAccBalTabVerification()) {
						disableFieldOnDemand(BUTTON_SUBMIT);
					} else {
						disableFieldOnDemand(BUTTON_SUBMIT);
						return false;
					}
				} else if (sheetID==5) {  //DOcument Review
					log.info("Tab5-6");
					log.info("emptyFlag="+emptyFlag);
					loadTabListViewTSLM("","Doc_Review_RefID");
					//setDecisionValidationTSLM("TFO_DJ_TSLM_DOCUMENT_REVIEW");
					if ((!emptyFlag?checkMandatoryJSPValidation(DOC_RVW_TAB,ADD_LVW_DOC_RVW,docCheckListCount):true)
							&& docRevSuccTabVerification()) {
						disableFieldOnDemand(BUTTON_SUBMIT);
					} else {
						disableFieldOnDemand(BUTTON_SUBMIT);
						return false;
					}
				} else if (sheetID==6) { //Text Vetting
					log.info("Tab6-7");
					if (txtVettTabVerification()) {
						disableFieldOnDemand(BUTTON_SUBMIT);
					} else {
						disableFieldOnDemand(BUTTON_SUBMIT);
						return false;
					}
				} else if (sheetID == 7) {   //Limit and credit Review
					log.info("Tab7-8");
					loadTabListViewTSLM("","LIMIT_REFERRAL_ID");
					setLimitLineTSLM();
					setEDASApproval();
					//setDecisionValidationTSLM("TFO_DJ_TSLM_LIMIT_CREDIT_REVIEW");
					if ((emptyLimitFlag?emptyLimitFlag:checkMandatoryJSPValidation(LMT_CRDT_TAB,ADD_LMY_CRDT_RVW,limitChecklistCount))
							&& lmtCreTabVerification()) {
						SetGrntFCUBSDefaultValue();
						enableFieldOnDemand(BUTTON_SUBMIT);
					} else {
						disableFieldOnDemand(BUTTON_SUBMIT);
						return false;
					}
				} else if(sheetID == 9){//code by mansi
					SetGrntElcDecDefaultValue();
					//setEDASApproval();
				} 
				else {
					return true;
				}
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		return true;
	}

	public void setDecisionFlagPPM(){
		setFlag();
		setDecisionFlagSubmit(IS_RM_PPM,IS_REF_PPM,IS_LEGAL_PPM,IS_CR_PPM,IS_CB_PPM,IS_TRADE_PPM);
	}

	public void loanGroupChange(){
		String sLoanGrp = formObject.getValue(IFS_LOAN_GRP).toString();
		if(sLoanGrp.equalsIgnoreCase("BC") || 
				sLoanGrp.equalsIgnoreCase("MRPA")) {
			formObject.setValue(SUFF_BAL_AVL_PPM, "3");
		}else {
			formObject.setValue(SUFF_BAL_AVL_PPM, "0");
		}
		emptyFlag = false;
		emptyLimitFlag = false;
	}

	public void sendEmailChange(){
		String checkBoxSendEmail=formObject.getValue(CHKBX_SEND_MAIL).toString();
		if("true".equalsIgnoreCase(checkBoxSendEmail)){
			formObject.setValue(MANUALLY_TICKED_MAIL, "Y");
		}else{
			formObject.setValue(MANUALLY_TICKED_MAIL, "N");
		}
	}

	public void formLoadData(){
		String winame = formObject.getValue("WI_NAME").toString();
		log.info("inside form load event -- WorkItem Name: " + winame);
		Boolean view = true;
		view = setUserDetail();
		setProperties();
		intializeStaticValue();
		loadIntCodeDeconLoad();
		decisionValidationPPM(DEC_CODE);
		inputTabFrmHideShowPPM();
		loadProdLov(srcChnl, relType, listPrdCode1, PRODUCT_TYPE);	
		disableAmendType(AMEND_TYPE);
		if(!formObject.getValue(PROCESS_TYPE).toString().equalsIgnoreCase("TSLM Front End")){
		setBranchCodeAtPm();
		}
		
		ppmFieldFrmOnLoad(formObject.getValue(REQUEST_TYPE).toString()) ; 
		finalDecisionHandling(DEC_CODE,REJ_RESN_PPM);
		setAcctDetailsICCS();
		tabHandlingMast();
		handlingCustAccntHolding();
		duplicateCheckPPMPC(DUP_CHK_CONFIRMATION,false);
		checkDiscrepancyDetails();
		setSwiftTxnAmount();
		setDocMatDate();
		tempProtectedBillsPrd();
		getExternalTableData();
		setDefaultPaymentMode();
		tempProtectedBillsPrd();
//		log.info(DISABLE_PPM);
		disableControls(DISABLE_PPM);
		loadDecisionHistoryListView();
		loadDataLOVFormLoad("ACCOUNT_NUMBER,INF_CHARGE_ACC_NUM,INF_SETTLEMENT_ACC_NUM");
		handlingRouteToPPM(DEC_CODE);
		setBranchCode(COMBOX_BRN_CODE,true);
		setDefaultProductCode(); // Shipping GTEE 27
		setAmendFrameData();
		
		if(!formObject.getValue(PROCESS_TYPE).toString().equalsIgnoreCase("PT")){
			removeItemFromCombo(SOURCE_CHANNEL, "IPT");
		}
		if(!formObject.getValue(PROCESS_TYPE).toString().equalsIgnoreCase("ET")){  //ATP-469 Shahbaz 04-06-2024
			removeItemFromCombo(SOURCE_CHANNEL, "IET");
		}
		String billStage = formObject.getValue("BILL_STAGE").toString();
		if (("ILCB_AC".equalsIgnoreCase(requestType) || "IC_AC".equalsIgnoreCase(requestType))&&"FIN".equalsIgnoreCase(billStage)){
			formObject.setValue(DEC_CODE,"ALDAC");
		}
		if(formObject.getValue(PROCESS_TYPE).toString().equalsIgnoreCase("PT")
				|| formObject.getValue(PROCESS_TYPE).toString().equalsIgnoreCase("ET")  //ATP-469 Shahbaz 23-05-2024
				|| formObject.getValue(PT_UTILITY_FLAG).toString().equalsIgnoreCase("Y")){
			disableControls(DELIVERY_BRANCH);
		}

		handlePMFrameReqType();
		duplicateCheckPPMPC(TSLM_INVOICE_CHK_CONFIRM,false);
		setTenorBaseComboTSLM();//CODE BY MOKSH
		SetGrntElcDecDefaultValue();//code by mansi
		SetGrntFCUBSDefaultValue();//santhosh
		setTBMLExt(); //ATP-487 04-07-2024 --JAMSHED
		SetSBLC_NI_PUROFMSGDefaultValue();//santhosh
		setDocRvwDefaultSwift();//29/11/21
		showUTCDetails();  //added by reyaz 5082022
		if(formObject.getValue(PROCESS_TYPE).toString().equalsIgnoreCase("TSLM Front End")){
			
		updateReferralStatus("SIGN_REFERRAL_ID",formObject);
		updateReferralStatus("Doc_Review_RefID",formObject);
		updateReferralStatus("LIMIT_REFERRAL_ID",formObject);
		enableDisbaleRetryButton();
		}else{
			formObject.setStyle(BUTTON_RETRY, VISIBLE, FALSE);
		}
		log.info("End of PPM Load");

	}

	@SuppressWarnings("unchecked")
	@Override
	public String executeServerEvent(IFormReference formObject, String controlName, String eventType, String data) {
		log.info("Inside executeServerEvent PPM>");
		sendMessageList.clear();
		log.info("Event: " + eventType + ", Control Name: " + controlName + ", Data: " + data);
		log.info("Event: " + eventType + ", Control Name: " + controlName + ", Data: " + data);

		retMsg = getReturnMessage(true);
		String reqCat = formObject.getValue(REQUEST_CATEGORY).toString();
		String reqType = formObject.getValue(REQUEST_TYPE).toString();
		Boolean success = true;
		try {
			boolean sOnClick=false;	
			if (eventType.equalsIgnoreCase(EVENT_TYPE_LOAD)){
				if (CONTROL_NAME_FORM.equalsIgnoreCase(controlName)) {
					formLoadData();
					boolean openJSPOnFormLoad = checkCallStatus();
					if(openJSPOnFormLoad){
						return getReturnMessage(true, controlName, "openRepairJSP");
					}
				} else if (FRAME_DECISION_HIST.equalsIgnoreCase(controlName)) {
					log.info("loading decision history ...");
					loadDecisionHistoryListView();
				}
			}else if (eventType.equalsIgnoreCase("handlingJSPData") &&controlName.equalsIgnoreCase(DOC_REV_MODIFY)) {
				addGridData(data,"Qvar_Doc_Rvw",DOC_RVW_TAB);
				discrepancyHandling();
			}else if (eventType.equalsIgnoreCase("handlingJSPData") &&controlName.equalsIgnoreCase(LIMIT_CREDIT_FETCH)) {
				addGridData(data,Qvar_Lmt_Crdt_ID,LMT_CRDT_TAB);
			}else if("saveAndNextPreHook".equalsIgnoreCase(controlName)){ //save and next button click
				implementTabNavigation(controlName,data);							
				log.info("After implementTabNavigation sendMessageList>>>>>>>>>>> "+sendMessageList);
			}else if("onClickTabPPM".equalsIgnoreCase(controlName)){ 
				implementTabNavigation(controlName,data);							
			}else if("PUSHMSGTSLM".equalsIgnoreCase(controlName)){ 
				String processType=formObject.getValue(PROCESS_TYPE).toString();
				String hybridCustomer=formObject.getValue(HYBRID_CUSTOMER).toString();  //ATP-495 29-07-2024 REYAZ
				if(processType.equalsIgnoreCase("TSLM Front End") && !hybridCustomer.equalsIgnoreCase("Yes") ){ //ATP-495 29-07-2024 REYAZ
				createTSLMPushMsg("R");
				}
			}else if (eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)||eventType.equalsIgnoreCase(EVENT_TYPE_CHANGE)){
				if(DOC_REV_MODIFY.equalsIgnoreCase(controlName) ||LIMIT_CREDIT_FETCH.equalsIgnoreCase(controlName)){
					checkGridLoadvalidation(data);
				}else if(BUTTON_RETRY.equalsIgnoreCase(controlName)){
					uploadDocFromPayload();
				}else if(INF_CHARGE_ACC_NUM.equalsIgnoreCase(controlName)){         
					setTitleCurrency(controlName,INF_CHARGE_ACC_TITLE,INF_CHARGE_ACC_CURR);
					setSettelmentAccDetails(INF_CHARGE_ACC_NUM);
					fetchAcctDetailsICCS();
				} else if(INF_CREDIT_ACC_NUM.equalsIgnoreCase(controlName)){                               
					setTitleCurrency(controlName,INF_CREDIT_ACC_TITLE,INF_CREDIT_ACC_CURR);
				} else if("BC_CALL_DONE".equalsIgnoreCase(controlName)){  
					buyerCallingFrameHandling();
				} else if(DEC_CODE.equalsIgnoreCase(controlName)){
					decisionValidationPPM(DEC_CODE);
					finalDecisionHandling(controlName,REJ_RESN_PPM);	
					handlingRouteToPPM(controlName);
				} else if (ACCOUNT_VALID.equalsIgnoreCase(controlName)){   
					btnAccEnableDisable(controlName);				
				} else if (BILL_CUST_HLDING_ACC_US.equalsIgnoreCase(controlName)){		
					btnAccEnableDisable(controlName);
				} else if(DUP_CHK_CONFIRMATION.equalsIgnoreCase(controlName)){    
					duplicateCheckPPMPC(controlName,true);
				} else if(TFO_BRANCH_CITY.equalsIgnoreCase(controlName)){		   		
					formObject.setValue(TFO_ASSIGNED_CENTER, formObject.getValue(TFO_BRANCH_CITY).toString());
				}else if(PRODUCT_TYPE.equalsIgnoreCase(controlName)){ 
					setCounterGTEExpDate(controlName); 
					setDefaultFieldGRNT();
				}else if(controlName.equalsIgnoreCase(SIGN_FETCH)){
					fetchAcctDetailsICCS();
				}else if(controlName.equalsIgnoreCase(ACCOUNT_NUMBER)){
					setTitleCurrency(controlName,"GRNT_CHRG_ACC_TITLE","GRNT_CHRG_ACC_CRNCY");
					clearCustSignAccBal(formObject.getValue(ACCOUNT_NUMBER).toString());
				}else if(controlName.equalsIgnoreCase(INF_SETTLEMENT_ACC_NUM)){
					setTitleCurrency(controlName,INF_SETTLEMENT_ACC_TITLE,INF_SETTLEMENT_ACC_CURR);
				}else if(controlName.equalsIgnoreCase(SOURCE_CHANNEL)){
					loadProdLov(formObject.getValue(SOURCE_CHANNEL).toString(),
							formObject.getValue(RELATIONSHIP_TYPE).toString(),listPrdCode1,PRODUCT_TYPE);
					setDefaultProductCode(); //Shipping GTEE 27
				}else if(controlName.equalsIgnoreCase(RELATIONSHIP_TYPE)){
					loadProdLov(formObject.getValue(SOURCE_CHANNEL).toString(),formObject.getValue(RELATIONSHIP_TYPE).toString(),listPrdCode1,PRODUCT_TYPE);
					setBranchCode(COMBOX_BRN_CODE,false);
					setDefaultProductCode(); //Shipping GTEE 27
					setTenorBaseComboTSLM();	//CODE BY MOKSH
				}else if(IFS_LOAN_GRP.equalsIgnoreCase(controlName) && "TL".equalsIgnoreCase(reqType)){
					loanGroupChange();
				} else if (controlName.equalsIgnoreCase(ACCOUNT_DETAILS)){
					fetchAccountData((String) formObject.getValue(CUSTOMER_ID));
					clearCustSignAccBal(formObject.getValue(ACCOUNT_NUMBER).toString());
				}else if(controlName.equalsIgnoreCase(FETCH_DETAILS)){
					fetchAccountData(formObject.getValue(CUSTOMER_ID).toString());				
				}else if (controlName.equalsIgnoreCase(SIGACC_REF_TO)){
					loadTabListView("","lvwSignAcc");
				}else if (controlName.equalsIgnoreCase(DOCREV_REF_TO)){
					loadTabListView("","lvwDocRvw");
				}else if (controlName.equalsIgnoreCase(TXTVETT_REF_TO)){
					loadTabListView("","lvwTxtVet");
				}else if (controlName.equalsIgnoreCase(LMTCRE_REF_TO)){
					loadTabListView("","lvwLmtCrdt");
				}else if(CHKBX_SEND_MAIL.equalsIgnoreCase(controlName)){		
					sendEmailChange();
				}else if(AMEND_TYPE.equalsIgnoreCase(controlName)){
					ppmNewFieldValidate(formObject.getValue(AMEND_TYPE).toString());
				}else if(TRN_TENOR.equalsIgnoreCase(controlName)){				
					loadLovOnCond(formObject.getValue(TRN_TENOR).toString(),AMEND_TYPE);
					checkOnTrnstenor(controlName);
				}else if(INF_AMEND_TYPE.equalsIgnoreCase(controlName)){  
					newMatDateHandling();
				}else if (BUTTON_SUBMIT.equalsIgnoreCase(controlName)) {
					String result=submitButton(data);
					return result;
				}else if (BTN_FETCH_TSLM_CID_DETAILS.equalsIgnoreCase(controlName)) {//CODE BY MOKSH 
					log.info("INSIDE BTN_FETCH_TSLM_CID_DETAILS CONDITION" );
					return populateTSLMCompanyDetails();
				}else if(TSLM_INVOICE_CHK_CONFIRM.equalsIgnoreCase(controlName)){   
					duplicateCheckPPMPC(controlName,true);
				}else if(PROCESSING_SYSTEM.equalsIgnoreCase(controlName)){//CODE BY MOKSH
					setTenorBaseComboTSLM();
				}else if("UTC_ON_LOAD_INVOICE".equalsIgnoreCase(controlName)){
					log.info("Before validateInvoiceDetailsTab on PPM" );
					validateInvoiceDetailsTab();
					log.info("After validateInvoiceDetailsTab on PPM" );					
				}else if("SIGN_REFERRAL_ID".equalsIgnoreCase(controlName)||"Doc_Review_RefID".equalsIgnoreCase(controlName)||"LIMIT_REFERRAL_ID".equalsIgnoreCase(controlName)){
					log.info("Before updateReferralStatusonChange on PPM" );
					updateReferralStatusonChange(controlName,formObject);
					log.info("After updateReferralStatusonChange on PPM" );	
					
				}else if ("REFF_TO".equalsIgnoreCase(controlName)){
					log.info("inside Reff_to" );	
					loadRefComboTSLM(data); // krishna
				}
			}else if(eventType.equalsIgnoreCase(EVENT_TYPE_GOTFOCUS))
			{
				if(AMEND_TYPE.equalsIgnoreCase(controlName)){
					loadLovOnCond( formObject.getValue(TRN_TENOR).toString(),AMEND_TYPE);
				}else if(INF_MATURITY_DATE.equalsIgnoreCase(controlName)){
					sMatDate = sMatDate1;
					sMatDate1 = formObject.getValue(controlName).toString();
				}else if(INF_BASE_DOC_DATE.equalsIgnoreCase(controlName)){
					sBaseDocDate = sBaseDocDate1;
					sBaseDocDate1 = formObject.getValue(controlName).toString();
				}else if(INF_NEW_MATURITY_DATE.equalsIgnoreCase(controlName)){
					sNewInfDate = sNewInfDate1;
					sNewInfDate1 = formObject.getValue(controlName).toString();
				} 
			}else if(eventType.equalsIgnoreCase(EVENT_TYPE_LOSTFOCUS)){

				if(NEW_TRN_AMT.equalsIgnoreCase(controlName)){							
					String msg= chkAmount(controlName,trnsCurrency);
					if(!msg.equalsIgnoreCase("")){
						msg = controlName + "###" + msg;
						retMsg = getReturnMessage(false, controlName, msg);
						log.info("++++++" +retMsg );
						log.info("+++++---+" +msg );
					}
				}else if(LC_CONF_AMNT.equalsIgnoreCase(controlName)){
					String msg = chkAmount(LC_CONF_AMNT, "");
					if(!msg.equalsIgnoreCase("")){
						sendMessageHashMap(controlName,msg);
					}
				}else if(INF_MATURITY_DATE.equalsIgnoreCase(controlName)){
					String msg=maturityDateValidations(controlName);
					if(!msg.equalsIgnoreCase("")){
						sendMessageHashMap(controlName,msg);
					}
				}else if ("Q_Charges_Frame_legalAmount".equalsIgnoreCase(controlName) ||
						"Q_Charges_Frame_penaltyAmount".equalsIgnoreCase(controlName) ||
						"Q_Charges_Frame_overdrawnAmount".equalsIgnoreCase(controlName) ||
						"Q_Charges_Frame_otherAmount".equalsIgnoreCase(controlName)) {
					try {
						if(formObject.getValue(controlName).toString().endsWith(".")) {
							formObject.setValue(controlName, String.valueOf(Double.parseDouble(formObject.getValue(controlName).toString())));
						}else if(formObject.getValue(controlName).toString().startsWith(".")) {
							log.info("setting double value on lost focus");
							formObject.setValue(controlName, String.valueOf(Double.parseDouble(formObject.getValue(controlName).toString())));
						}else {
							boolean valid = validateAmountFields((String) formObject.getValue(controlName), 17, 3);
							if(!valid){
								sendMessageHashMap(controlName, "Please enter a valid number.");
								formObject.clearCombo(controlName);
							}
						}
					}catch(Exception e) {
						log.error("Exception: ",e);
						sendMessageHashMap(controlName, "Please enter a valid number.");
						formObject.clearCombo(controlName);
					}

				}else if(EXP_DATE.equalsIgnoreCase(controlName) || NEW_EXP_DATE.equalsIgnoreCase(controlName)){
					checkDateFormat(controlName);
					setCounterGTEExpDate(controlName);  
				}else if(INF_NEW_MATURITY_DATE.equalsIgnoreCase(controlName)){
					if (isNewMaturityBaseDocDateAllowed()) {
						if (!checkCurrentDateValidation(formObject.getValue(controlName).toString())) {
							log.info("sNewInfDate " + sNewInfDate);
							log.info("sNewInfDate1 " + sNewInfDate1);
							formObject.setValue(controlName, sNewInfDate);
							sendMessageHashMap(controlName,ALERT_NEW_MATURITY_DATE_GREATER);
						} else {
							formObject.setValue(INF_MATURITY_DATE,(String) formObject.getValue(INF_NEW_MATURITY_DATE));
							maturityDateValidations(controlName);
						}
					}
				}else if(FINAL_DECISION_GRID_ADDITIONAL_MAIL.equalsIgnoreCase(controlName)){
					validateMultipleMailId(controlName);
					int finalDecisionList=Integer.parseInt(data);
					setHashMap(finalDecisionList);
				}else if(INF_BASE_DOC_DATE.equalsIgnoreCase(controlName)){
					if(isMaturityBaseDocDateAllowed()){
						if( !checkBaseMatDatevalidation()){
							log.info("sBaseDocDate "+sBaseDocDate);
							log.info("sBaseDocDate1 "+sBaseDocDate1);
							formObject.setValue(controlName, sBaseDocDate);
							sendMessageHashMap(controlName,ALERT_BASE_DOC_DATE);
						}else{
							if(chkMaturityDtTenorDay()){
								setMaturityDateIFS();	
							}
						}
					}
				}else if(INF_TENOR_DAYS.equalsIgnoreCase(controlName)){
					if(isMaturityBaseDocDateAllowed()){
						int tenorDays = Integer.parseInt(formObject.getValue(controlName).toString().isEmpty()?"0":formObject.getValue(controlName).toString());
						if(tenorDays<0){
							formObject.clearCombo(controlName);
							sendMessageHashMap(controlName,ALERT_TENOR_DAYS);

						}else{
							if(!illState){						
								if(chkMaturityDtTenorDay())	{	
									setMaturityDateIFS(); 
								}
							}
							illState=false;	
						}
					}
				}else if(controlName.equalsIgnoreCase(Q_AMENDMENT_DATA_USER_CNTR_GTEE_EXP_DATE)){//mansi
				 if(checkExpDateValidation()){
					 formObject.setValue(Q_AMENDMENT_DATA_USER_CNTR_GTEE_EXP_DATE, "");
					 formObject.setValue(Q_AMENDMENT_DATA_FIN_CNTR_GTEE_EXP_DATE, "");	
				 }
			}
		}
		}catch (Exception e) {
			log.error("Exception: ",e);

		}finally{
			log.info("sendMessageList="+sendMessageList);
			if(!sendMessageList.isEmpty()){
				if(BUTTON_SUBMIT.equalsIgnoreCase(controlName)){
					bSubmit = true;
					enableDisableMultipleBtnControl(BUTTON_SUBMIT, true);
				}
				return  getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
			}
		}
		return retMsg;
	}
	private String submitButton(String inputData){

		log.info(" input data="+inputData);
		String a[] =inputData.split(",");
		String button=a[0];
		int sheetID=Integer.parseInt(a[1]);
		int counterListCount=Integer.parseInt(a[2]);
		int signListCount=Integer.parseInt(a[3]);
		int docListCount=Integer.parseInt(a[4]);
		int textListCount=Integer.parseInt(a[5]);
		int limitChecklistCount=Integer.parseInt(a[6]);
		int fdRefeeralListCount=Integer.parseInt(a[7]);
		//Added by SHivanshu ATP-481
		int pastDue = Integer.parseInt(a[8]);

		log.info("Implement tab Navigation called");
		sendMessageList.clear();

		if(bSubmit){
			bSubmit = false;
			enableDisableMultipleBtnControl(BUTTON_SUBMIT, true);
			log.info("Implement tab Navigation executed");
			setModeOfGuarantee();
			setTBMLExt(); //ATP-487 04-07-2024 --JAMSHED
			setDecisionFlagPPM();
			
			
		
			log.info(IS_RM_PPM+formObject.getValue(IS_RM_PPM).toString()+IS_REF_PPM
					+formObject.getValue(IS_REF_PPM).toString()+IS_LEGAL_PPM
					+formObject.getValue(IS_LEGAL_PPM).toString()+
					IS_CR_PPM+formObject.getValue(IS_CR_PPM).toString()
					+IS_CB_PPM+formObject.getValue(IS_CB_PPM).toString()
					+IS_TRADE_PPM+formObject.getValue(IS_TRADE_PPM).toString());

			if(submitPPMValidations(docListCount,limitChecklistCount) && 
					icgRmCheck(EVENT_TYPE_CLICK,fdRefeeralListCount)){	
				log.info("Sending referal mails");
				
                log.info(IS_RM_PPM+formObject.getValue(IS_RM_PPM).toString()+IS_REF_PPM
						+formObject.getValue(IS_REF_PPM).toString()+IS_LEGAL_PPM
						+formObject.getValue(IS_LEGAL_PPM).toString()+
						IS_CR_PPM+formObject.getValue(IS_CR_PPM).toString()
						+IS_CB_PPM+formObject.getValue(IS_CB_PPM).toString()
						+IS_TRADE_PPM+formObject.getValue(IS_TRADE_PPM).toString());

				if("REF".equalsIgnoreCase(formObject.getValue(DEC_CODE).toString()) ){
					saveDecHistory();
					submitReferalHistory();
					log.info("Sending referal mails");
				} 
				
				
				 if(formObject.getValue("IS_GETDOCUMENT_UTC_DONE").toString().equalsIgnoreCase("Y") && 
							("INTREJ".equalsIgnoreCase(formObject.getValue(DEC_CODE).toString()) ||
							"REJ".equalsIgnoreCase(formObject.getValue(DEC_CODE).toString()))){
						log.info("Inside Return Workitem");
						if(("IFP".equalsIgnoreCase(requestCategory)|| "IFA".equalsIgnoreCase(requestCategory) || "IFS".equalsIgnoreCase(requestCategory) || "SCF".equalsIgnoreCase(requestCategory)) &&
								("LD".equalsIgnoreCase(requestType) || "IFA_CTP".equalsIgnoreCase(requestType)    //ATP -254
								|| "PD".equalsIgnoreCase(requestType) || "PDD".equalsIgnoreCase(requestType))){
							String columnNames = "SNO,WI_NAME,CALL_NAME,CALL_DT,CALL_STATUS,RETRY_COUNT,VESSEL_NAME";
							log.info("UTC_START_CHECK starts");
							int i=0;
							String query = "DELETE from TFO_DJ_INTEGRATION_CALLS WHERE CALL_NAME = 'updateDocumentStatusUTC' AND WI_NAME = '"+sWorkitemID+"'";
							log.info("[sInsertScript for Integration Calls]"+query);
							formObject.saveDataInDB(query);
							if(!getUpdateDocDetailsIntegration()){
								String sInsertScript = "INSERT INTO TFO_DJ_INTEGRATION_CALLS ("+columnNames+") VALUES "
										+ "('"+(i+1)+"','" + sWorkitemID + "','updateDocumentStatusUTC',sysdate,'N','0','updateDocumentStatusUTC')";
								log.info("[sInsertScript for Integration Calls]"+sInsertScript);
								formObject.saveDataInDB(sInsertScript);
								 return   getReturnMessage(true, "","updateDocStatus");

							}
							log.info("Data Inserted at PPM"+i);
							//insertFCUBSIntegrationCalls("updateDocumentStatusUTC","updateDocumentStatusUTC");
							//updateDocumentStatus();
							log.info("UTC_START_CHECK ends");
						}
					}
				//arpit decision change
				if("REJ".equalsIgnoreCase(formObject.getValue(DEC_CODE).toString())){
					log.info("Reject Reason>>>>>:"+formObject.getValue(REJ_RESN_PPM).toString());
					if(validateRejectCondition()){
						saveRejectReason();
					}
						//saveDecHistory();commented as same was called below at line 916
					List<String> paramlist =new ArrayList<String>();
					log.info("after declare");
					paramlist.add ("Text :"+sWorkitemID);
					log.info("after param add"+paramlist);
					log.info("paramlist::"+paramlist.size());
					String str = formObject.getValue(REJ_RESN_PPM).toString();
					str  = str.substring(1,str.length()-1);
					str = str.replaceAll("\"", "");
					String[] arr = str.split(",");
					if((Arrays.asList(arr).contains("Others") && (!"".equalsIgnoreCase(formObject.getValue("REMARKS").toString()))) || 
							(!Arrays.asList(arr).contains("Others")) ){
						log.info("Before Reject Email"+Arrays.asList(arr).contains("Others"));
						log.info("Before Reject Email New"+((Arrays.asList(arr).contains("Others") && (!"".equalsIgnoreCase(formObject.getValue("REMARKS").toString()))) || 
								(!Arrays.asList(arr).contains("Others")) ));
					List<String> apProcedureInputXml = formObject.getDataFromStoredProcedure("TFO_DJ_REJECT_EMAIL_NOTIFY", paramlist);
					log.info("KHURANA LOGGGGGGGGGGGGGGGGGGGG    " +apProcedureInputXml);
					log.info("first called");
					}
				}
				else if("APP".equalsIgnoreCase(formObject.getValue(DEC_CODE).toString())
						|| "ALDAC".equalsIgnoreCase(formObject.getValue(DEC_CODE).toString())
						|| "STBP".equalsIgnoreCase(formObject.getValue(DEC_CODE).toString())){

					log.info("Calling TFO_DJ_EMAIL_CONFIG");
					List<String> paramlist =new ArrayList<String>( );
					paramlist.add ("Text :"+sWorkitemID);
					paramlist.add ("Text :N");
					List statusProc = formObject.getDataFromStoredProcedure("TFO_DJ_EMAIL_CONFIG", paramlist);
					log.info("getDataFromStoredProcedure TFO_DJ_EMAIL_CONFIG list returned: "+statusProc);
					log.info("Calling End TFO_DJ_EMAIL_CONFIG");
					String processType=formObject.getValue(PROCESS_TYPE).toString();
					String billStage=formObject.getValue("BILL_STAGE").toString();
					if(("BAU".equalsIgnoreCase(processType)||"SWIFT".equalsIgnoreCase(processType))
							&& "FIN".equalsIgnoreCase(billStage)){
						bSubmit=true;
						String output= CreateNewWorkitem();  //US_SPRINT4_PT_148
						if("OpenLinkWIJSP".equalsIgnoreCase(output)){
							return   getReturnMessage(true, "",output);
						}
					}
					log.info("Before Calling createNewWorkitemSBLC Method");
					if("SBLC".equalsIgnoreCase(requestCategory) && "SBLC_ER".equalsIgnoreCase(requestType)
							&& "STBP".equalsIgnoreCase(formObject.getValue(DEC_CODE).toString())){
						log.info("Calling createNewWorkitemSBLC Method");
						createNewWorkitemSBLC();
					}
					log.info("After Calling createNewWorkitemSBLC Method.");
					//traydstream |reyaz|atp-518|27-11-2024 start
					String ts_req = formObject.getValue(TS_REQUIRED).toString();
					log.info("callTraydstreamRestService"+"ProcessType " +processType +"requestType " +requestType +"requestCategory " +requestCategory +"ts_req "+ ts_req);
					TraydStreamValues = TraydStreamValueMap.get(processType + "#" + requestCategory + "#" + requestType + "#"+ts_req);
					log.info("skip traydstream flag: " + formObject.getValue(SKIP_TRAYDSTREAM).toString());
				    String prevWS=formObject.getValue(PREV_WS).toString();
				    String pmEntryDate=formObject.getValue("PM_ENTRY_DATE").toString();
				    String pcEntryDate=formObject.getValue("PC_ENTRY_DATE").toString();
					log.info("prevWS  : " + prevWS  +"pmEntryDate:  " +pmEntryDate +" pcEntryDate: " +pcEntryDate+" Workitem Name :"+this.sWorkitemID);
					if (TraydStreamValues != null  && "APP".equalsIgnoreCase(formObject.getValue(DEC_CODE).toString()) && !TRUE.equalsIgnoreCase(formObject.getValue(SKIP_TRAYDSTREAM).toString()) 
							&& !("PROCESSING MAKER".equalsIgnoreCase(prevWS) || "PROCESSING CHECKER".equalsIgnoreCase(prevWS)||"PM PROCESSING SYSTEM".equalsIgnoreCase(prevWS) 
							||"PC PROCESSING SYSTEM".equalsIgnoreCase(prevWS)|| "Repair".equalsIgnoreCase(prevWS) || "ToDoList".equalsIgnoreCase(prevWS)) 
							&& "".equalsIgnoreCase(pmEntryDate) && "".equalsIgnoreCase(pcEntryDate)) { //REYAZ |ATP-523|11-12-2024
						log.info("Worskstep Name ::: "+TraydStreamValues.get(6));
						if("Distribute3".equalsIgnoreCase(TraydStreamValues.get(6))) {
							String sQuery="update ext_tfo set target_workstep='Collect3' where wi_name='"+this.sWorkitemID+"'";
							log.info("Updating target Workstep  in ext table records query : "+sQuery);
							log.info("response : "+formObject.saveDataInDB(sQuery)+"");
						}
					}
					//traydstream |reyaz|atp-518|27-11-2024 end
				}
				/*else if("ALDAC".equalsIgnoreCase(formObject.getValue(DEC_CODE).toString())){
					String processType=formObject.getValue(PROCESS_TYPE).toString();
					String billStage=formObject.getValue("BILL_STAGE").toString();
					if(("BAU".equalsIgnoreCase(processType)||"SWIFT".equalsIgnoreCase(processType))
						&& "FIN".equalsIgnoreCase(billStage)){
					 bSubmit=true;
					 String output= CreateNewWorkitem();  //US_SPRINT4_PT_148
					 if("OpenLinkWIJSP".equalsIgnoreCase(output)){
						 return   getReturnMessage(true, "",output);
					 }
				}
				}
				 */


				if(!"REF".equalsIgnoreCase(formObject.getValue(DEC_CODE).toString())){
					saveDecHistory();
//					Added by Shivanshu ATP-481	Changes for PAST DUE DEC HISTORY SAVING
					String pastDueLiability = formObject.getValue(PAST_DUE_LIABILITY).toString();
					if ("Yes".equalsIgnoreCase(pastDueLiability)) {
						savePastDueDecHistory(pastDue);
					}
					
				}
				if("Y".equals(formObject.getValue(PT_UTILITY_FLAG).toString())) {
					insertIntoNotificationTxn();
				}
				
				// added by reyaz 17-01-2023
				String sQuery1="select AUTO_UTC_TRIGGER,AUTO_TRSD_TRIGGER from TFO_DJ_AUTO_TRIGGER_MASTER WHERE SOURCING_CHANNEL='TSLMSY'";  //ATP-379 15-FEB-24 --JAMSHED
				List<List<String>> lresultSet1=null;
				String autoTriggerUTC ="";
				String autoTriggerTRSD="";
				lresultSet1 =  formObject.getDataFromDB(sQuery1);
				log.info("lresultSet1: "+lresultSet1);
				if(lresultSet1!=null){
					if(!lresultSet1.isEmpty()){
						autoTriggerUTC=lresultSet1.get(0).get(0);
						autoTriggerTRSD=lresultSet1.get(0).get(1);
						log.info("autoTriggerUTC: "+autoTriggerUTC);
						log.info("autoTriggerTRSD: "+autoTriggerTRSD);
						}
				}
				if("APP".equalsIgnoreCase(formObject.getValue(DEC_CODE).toString()) && 
							formObject.getValue("SOURCE_CHANNEL").toString().equalsIgnoreCase("TSLM Front End") &&
							formObject.getValue("UTC_REQUIRED_BRMS").toString().equalsIgnoreCase("Yes") &&
							"Y".equalsIgnoreCase(autoTriggerUTC)&&
							!(requestCategory.equalsIgnoreCase("IFA")&&requestType.equalsIgnoreCase("LD"))){
					 log.info("UTC_START_CHECK starts");
					 log.info("Auto TRSD starts");
						List<List<String>> sOutputlist =null;
						try {
						sOutputlist = formObject.getDataFromDB("SELECT SEQ_WEBSERVICE.nextval as IDValue from DUAL");
						String txnNumber= sOutputlist.get(0).get(0);
						
							callUTC(txnNumber);
						} catch (Exception e) {
							log.error("Exception: ",e);
						}
						
						log.info("UTC_START_CHECK ends");
				 	}
				if("APP".equalsIgnoreCase(formObject.getValue(DEC_CODE).toString()) && 
						formObject.getValue("SOURCE_CHANNEL").toString().equalsIgnoreCase("TSLM Front End") &&
						"Y".equalsIgnoreCase(autoTriggerTRSD)){
							log.info("Auto TRSD starts");
							List<List<String>> sOutputlist =null;
							try {
							sOutputlist = formObject.getDataFromDB("SELECT SEQ_WEBSERVICE.nextval as IDValue from DUAL");
							String txnNumber= sOutputlist.get(0).get(0);
							
								callTRSD(txnNumber);
							} catch (Exception e) {
								log.error("Exception: ",e);
							}
							log.info("Auto TRSD ends");
					}				
			}else{
				bSubmit = true;
				enableDisableMultipleBtnControl(BUTTON_SUBMIT, true);
				log.info("Inside Submit validation else");
				
			}
			log.info("Set dec flag executed");
		}
		return  getReturnMessage(true, "","");
	}
	protected void setDocMatDate(){
		sBaseDocDate1 = formObject.getValue(INF_BASE_DOC_DATE).toString();
		sMatDate1 =formObject.getValue(INF_MATURITY_DATE).toString();
		sNewInfDate1 = formObject.getValue(INF_NEW_MATURITY_DATE).toString();

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
	public JSONArray validateSubmittedForm(FormDef arg0, IFormReference arg1,
			String arg2) {
		return null;
	}
	private String[] enableFetchFields(IFormReference formObject, boolean returnData){
		try {
			log.info("inside enableFetchFields >>>");
			String enableFieldNames="";
			if("ELC_LCA".equalsIgnoreCase(formObject.getValue(REQUEST_TYPE).toString()) &&
					"SWIFT".equalsIgnoreCase(formObject.getValue(PROCESS_TYPE).toString())&&
					("700".equalsIgnoreCase(formObject.getValue(SWIFT_MESSAGE_TYPE).toString())||
							"710".equalsIgnoreCase(formObject.getValue(SWIFT_MESSAGE_TYPE).toString())) 
					){
			}else{
				log.info("enableFetchFields: "+"SELECT FIELD_NAME FROM DJ_RECEPTION_FETCH_MAST WHERE REQ_CAT_CODE = '"+ formObject.getValue(REQUEST_CATEGORY) +"' AND "
						+ "REQ_TYPE_CODE = '"+ formObject.getValue(REQUEST_TYPE) +"' AND (SWIFT_FETCH_MODULE='NA' OR SWIFT_FETCH_MODULE='"+formObject.getValue(SWIFT_FETCH_MODULE)
						+"') AND PROCESS_TYPE='"+formObject.getValue(PROCESS_TYPE)+"'");
				enableFieldNames = setMasterValues(formObject.getDataFromDB("SELECT FIELD_NAME FROM DJ_RECEPTION_FETCH_MAST WHERE REQ_CAT_CODE = '"+ formObject.getValue(REQUEST_CATEGORY) +"' AND "
						+ "REQ_TYPE_CODE = '"+ formObject.getValue(REQUEST_TYPE) +"' AND (SWIFT_FETCH_MODULE='NA' OR SWIFT_FETCH_MODULE='"+formObject.getValue(SWIFT_FETCH_MODULE)
						+"') AND PROCESS_TYPE='"+formObject.getValue(PROCESS_TYPE)+"'"));
			}
			log.info("enableFetchFields enableFieldNames: "+enableFieldNames);
			String[] fieldList = enableFieldNames.split("#~#"); 
			if(!returnData){
				formObject.setStyle(BUTTON_FETCH, DISABLE, FALSE);
				for(int counter = 0; counter < fieldList.length; counter++){
					log.info("working enableFetchFields fieldName: "+fieldList[counter]);
					formObject.setStyle(fieldList[counter], DISABLE, FALSE);
				}
			}
			return fieldList;
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		return null;

	}
	@SuppressWarnings("unchecked")
	public void lodReferToOnTabClick(String reft) {
		try {
			log.info(" reft : " + reft);
			if (SIGACC_REF_TO.equalsIgnoreCase(reft)) {
				String Q111 = "SELECT TO_CHAR(REFFER_TO) REF FROM TFO_DJ_SIG_REF_MAST ORDER BY 1";
				log.info(" REQ_SIGN_MAN_PPM >>>>>> : " + formObject.getValue(REQ_SIGN_MAN_PPM));
				String sig = formObject.getValue(REQ_SIGN_MAN_PPM).toString();
				String suff = formObject.getValue(SUFF_BAL_AVL_PPM).toString();
				log.info("[REQ_SIGN_MAN_PPM]  => "+sig +"  [SUFF_BAL_AVL_PPM]  => "+suff);
				if ("2".equalsIgnoreCase(sig) || "2".equalsIgnoreCase(suff)) {
					loadCombo( Q111, SIGACC_REF_TO);
				}
			}

			String requestCategory = null;
			if (DOCREV_REF_TO.equalsIgnoreCase(reft)) {
				String Q222 = "SELECT TO_CHAR(REFFER_TO) REF1 FROM TFO_DJ_DOCREV_REF_MAST WHERE REQ_CAT_CODE = '"+requestCategory+"' ORDER BY 1";
				String doc = formObject.getValue(DOC_REV_SUCC_PPM).toString();
				if ("2".equalsIgnoreCase(doc)) {
					loadCombo( Q222, DOCREV_REF_TO);
				}
			}

			if (TXTVETT_REF_TO.equalsIgnoreCase(reft)) {
				String Q333 = "SELECT TO_CHAR(REFFER_TO) REF2 FROM TFO_DJ_TXTVETT_REF_MAST ORDER BY 1";
				String txt = formObject.getValue(TXT_REQ_APP_PPM).toString();
				if ("1".equalsIgnoreCase(txt)) {
					loadCombo( Q333, TXTVETT_REF_TO);
				}
			}

			if (LMTCRE_REF_TO.equalsIgnoreCase(reft)) {
				String Q444 = "SELECT TO_CHAR(REFFER_TO) REF3 FROM TFO_DJ_LMTREV_REF_MAST WHERE REQ_CAT_CODE = '"+requestCategory+"' ORDER BY 1";
				String lmtcre = formObject.getValue(LMTCRE_APP_AVL_PPM).toString().trim();
				if ("2".equalsIgnoreCase(lmtcre)) {
					loadCombo( Q444, LMTCRE_REF_TO);
				}
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	public String loadTabListView(String controlName, String listVwName) {
		String value="";
		try {
			String delFlag = "";
			if ("lvwSignAcc".equalsIgnoreCase(listVwName)) {
				String strTabName =tabDescIdMap.get("SigAcc");
				log.info("SigAcc");
				log.info("in if loop of loadTabListView");
				delFlag = "N";

				setRoutingFlag(SIGACC_REF_TO,"Y");
				updateReferToGrid(formObject.getValue(SIGACC_REF_TO).toString(),strTabName, delFlag,listVwName);

			} else if ("lvwDocRvw".equalsIgnoreCase(listVwName)) {
				String strTabName =tabDescIdMap.get(DOC_RVW_TAB);				
				delFlag = "N";
				setRoutingFlag(DOCREV_REF_TO,"Y");
				updateReferToGrid(formObject.getValue(DOCREV_REF_TO).toString(),strTabName, delFlag,listVwName);

			} else if ("lvwTxtVet".equalsIgnoreCase(listVwName)) {
				log.info("in lvwTxtVet");
				String strTabName =tabDescIdMap.get("TxtVet");


				delFlag = "N";
				setRoutingFlag(TXTVETT_REF_TO,"Y");
				updateReferToGrid(formObject.getValue(TXTVETT_REF_TO).toString(),strTabName, delFlag,listVwName);


			} else if ("lvwLmtCrdt".equalsIgnoreCase(listVwName)) {
				String strTabName =tabDescIdMap.get(LMT_CRDT_TAB);
				log.info("in lvwTxtVet");
				delFlag = "N";
				setRoutingFlag(LMTCRE_REF_TO,"Y");
				updateReferToGrid(formObject.getValue(LMTCRE_REF_TO).toString(),strTabName, delFlag,listVwName);

			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		return value;
	}
	
	
	
	protected void setRoutingFlag(String sControlName, String sStatus){
		String sReferVal="";
		try {
			log.info("Control Name: "+sControlName +" & Status: " + sStatus);
			sReferVal = formObject.getValue(sControlName).toString();
			log.info("Referal Flag: "+sReferVal);
			if("RM".equalsIgnoreCase(sReferVal))
				formObject.setValue(IS_RM_PPM, sStatus);
			else if(sReferVal.equalsIgnoreCase("TSD"))
				formObject.setValue(IS_REF_PPM, sStatus);
			else if(sReferVal.equalsIgnoreCase("Legal"))
				formObject.setValue(IS_LEGAL_PPM,sStatus);
			else if(sReferVal.equalsIgnoreCase("Customer")
					||sReferVal.equalsIgnoreCase("Customer Only Through Email"))
				formObject.setValue(IS_CR_PPM, sStatus);

			else if(sReferVal.equalsIgnoreCase("Correspondent Bank"))
				formObject.setValue(IS_CB_PPM, sStatus);
			else if(sReferVal.equalsIgnoreCase("Trade Sales"))
				formObject.setValue(IS_TRADE_PPM, sStatus);
			else if(sReferVal.equalsIgnoreCase("TB Sales"))
				formObject.setValue(IS_TRADE_PPM, sStatus);
		} catch (Exception e) {			
			log.error("Exception: ",e);
		}
	}
	protected  void updateReferToGrid(String strRefTo,String strTabName,String delFlag,String listVwName){
		String exeEmailID = "";
		try{
			exeEmailID = getReferralmailId(strRefTo);
			exeEmailID = exeEmailID.replace("'", "''");

			if ("lvwSignAcc".equalsIgnoreCase(listVwName)) {
				formObject.setValue("SIGACC_EXISTING_EMAIL_ID",exeEmailID);
				formObject.setValue("SIGACC_DECISION_ID","Yes");
				formObject.setValue("SIGACC_TAB_NAME_ID",strTabName);
				formObject.setValue("SIGACC_DATE_ID",getCurrentDateTime());//2020-10-24 00:00:00
				formObject.setValue("SIGACC_DEL_FLAG_ID",delFlag);
			}else if ("lvwDocRvw".equalsIgnoreCase(listVwName)) {
				log.info("--------------------------inside lvwDocRvw --------------------");
				formObject.setValue("DOCREV_EXISTING_EMAIL_ID",exeEmailID);
				formObject.setValue("DOCREV_DECISION_ID","Yes");
				formObject.setValue("DOCREV_TAB_NAME_ID",strTabName);
				formObject.setValue("DOCREV_DATE_ID",getCurrentDateTime());//2020-10-24 00:00:00
				formObject.setValue("DOCREV_DEL_FLAG_ID",delFlag);
			} else if ("lvwTxtVet".equalsIgnoreCase(listVwName)) {
				log.info("--------------------------inside lvwTxtVet --------------------");
				formObject.setValue("TXTVETT_EXISTING_EMAIL_ID",exeEmailID);
				formObject.setValue("TXTVETT_DECISION_ID","Yes");
				formObject.setValue("TXTVETT_TAB_NAME_ID",strTabName);
				formObject.setValue("TXTVETT_DATE_ID",getCurrentDateTime());//2020-10-24 00:00:00
				formObject.setValue("TXTVETT_DEL_FLAG_ID",delFlag);
			} else if ("lvwLmtCrdt".equalsIgnoreCase(listVwName)) {
				log.info("--------------------------inside lvwLmtCrdt --------------------");
				formObject.setValue("LMTCRE_EXISTING_EMAIL_ID",exeEmailID);
				formObject.setValue("LMTCRE_DECISION_ID","Yes");
				formObject.setValue("LMTCRE_TAB_NAME_ID",strTabName);
				formObject.setValue("LMTCRE_DATE_ID",getCurrentDateTime());//2020-10-24 00:00:00
				formObject.setValue("LMTCRE_DEL_FLAG_ID",delFlag);
			} 
		}
		catch(Exception e){
			log.error("Exception",e);
		}
	}

	public String getCurrentDateTime() {
		String today = null;
		try {
			Date date = Calendar.getInstance().getTime();
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			today = formatter.format(date);
			log.info("today="+today);
		} catch (Exception e) {
			log.error("Exception: ",e);
		}

		return today;
	}
	protected String updateReferToGrid1_old(String sTableName, String sGridName, String strRefTo, String strRemarks,String strTabName, String delFlag) {
		try {
			String sQuery = "";
			log.info("DelFlag " + delFlag + " sGridName " + sGridName);
			int sNo = 0;
			String exeEmailID = "";
			if ("Y".equalsIgnoreCase(delFlag)) {
				sNo = lvwSrNo;
				if (sNo != 0) {
					sQuery = "DELETE FROM " + sTableName + " WHERE WI_NAME ='" + sWorkitemID + "' AND REFER_TO = '" + strRefTo + "'";
					log.info("SNo  " + sNo + " sQuery " + sQuery);
				} else {

					successStatus = false;
					msg = successStatus+","+sGridName+"," + ALERT_NO_ROW;
					return msg ;
				}
			} else {
				int checksNo = getSerialNumber("Select count(1) CNT from " + sTableName + " where WI_NAME ='" + sWorkitemID + "'  AND REFER_TO = '" + strRefTo + "'");
				log.info(" sNo by Test " + sNo);
				if (checksNo == 1) {
					log.info(" Come to add ");

					exeEmailID = getReferralmailId(strRefTo);
					exeEmailID = exeEmailID.replace("'", "''");
					sNo = getSerialNumber("Select count(1) CNT from " + sTableName + " where WI_NAME ='" + sWorkitemID + "'");
					sQuery = "INSERT INTO " + sTableName + " (SNO,WI_NAME,REFER_TO,DECISION,EXCP_RMRKS,TAB_NAME,DATE_TIME,FLAG_DEL,EXISTING_EMAIL) VALUES( " + sNo + ",'" + sWorkitemID + "','" +strRefTo + "','Yes','" + checkSplChar(strRemarks) + "','" + strTabName + "',sysdate,'N','" + exeEmailID + "')";
					log.info("SNo  " + sNo + " sQuery " + sQuery);
				} else {

					successStatus = false;
					msg = successStatus+","+sGridName+"," + ALERT_REFERRAL_EXIST+strRefTo;
					return msg ;
				}

			}
			formObject.saveDataInDB(sQuery);
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		return msg ;
	}
	protected void loadReferalListView(String tableName, String listVwName) {

		log.info("DOC Review listview");
		this.formObject.clearTable(listVwName);
		try {
			String strQ = " SELECT REFER_TO AS \"Refer To\", EXCP_RMRKS as \"Exception Remarks\" ,SNO,WI_NAME,DECISION,TAB_NAME FROM " + tableName + " WHERE WI_NAME ='" + sWorkitemID + "' AND FLAG_DEL='N' ORDER BY SNO";
			log.info("loadref Query  : >>>>>>>" + strQ);
			List recordList = formObject.getDataFromDB(strQ);
			LoadListView(recordList,"Refer To,Exception Remarks", listVwName);
		} catch (Exception e) {
			log.error("Exception: ",e);
		}

	}

	public int getSerialNumber(String sQuery) {
		int sNoCtr = 0;
		try {
			List<List<String>> recordList = formObject.getDataFromDB(sQuery);
			if (!recordList.isEmpty()) {
				if (!recordList.get(0).isEmpty()) {
					sNoCtr = Integer.parseInt(recordList.get(0).get(0).toString()) + 1;
				} else {
					sNoCtr = 1;
				}
			} else {
				sNoCtr = 1;
			}
		} catch (NumberFormatException e) {
			log.error("Exception: ",e);
		}
		log.info("get Serial number : " + sNoCtr);
		return sNoCtr;
	}

	public String getReferralmailId(String strRefTo){
		log.info("**************Inside getReferralmailId******************");
		log.info("strRefTo :"+strRefTo);
		try{
			String existingEmail="";
			if ("RM".equalsIgnoreCase(strRefTo)) {
				existingEmail = formObject.getValue("RM_EMAIL").toString();
			} 
			else if ("Customer".equalsIgnoreCase(strRefTo)
					||"Customer Only Through Email".equalsIgnoreCase(strRefTo)) {
				if("Y".equalsIgnoreCase(formObject.getValue(PT_UTILITY_FLAG).toString())){
					existingEmail = formObject.getValue("PT_CUST_EMAIL_ID").toString();
					if(!existingEmail.equalsIgnoreCase("")){ 
						if(formObject.getValue(TRADE_CUST_EMAIL_ID).toString().isEmpty()){ //BY KISHAN
							existingEmail = existingEmail + "," + formObject.getValue(FCR_CUST_EMAIL_ID).toString();
						}else{
							existingEmail = existingEmail + "," + formObject.getValue(TRADE_CUST_EMAIL_ID).toString();    
						}
						//existingEmail = existingEmail + "," + formObject.getValue(TRADE_CUST_EMAIL_ID).toString(); //by kishan Protrade initiated email change
					}else{
						if(formObject.getValue(TRADE_CUST_EMAIL_ID).toString().isEmpty()){ //BY KISHAN
							existingEmail =  formObject.getValue(FCR_CUST_EMAIL_ID).toString();
						}else{
							existingEmail =  formObject.getValue(TRADE_CUST_EMAIL_ID).toString();    
						}
						//existingEmail = formObject.getValue(TRADE_CUST_EMAIL_ID).toString(); //by kishan Protrade initiated email change
					}
				}else{
					if(formObject.getValue(TRADE_CUST_EMAIL_ID).toString().isEmpty()){
						existingEmail = formObject.getValue(FCR_CUST_EMAIL_ID).toString();
					}else{
						existingEmail = formObject.getValue(TRADE_CUST_EMAIL_ID).toString();   
					}
				}
			}
			else
				existingEmail = referralMailMap.get(strRefTo).toString();
			log.info("existingEmail=>"+existingEmail);
			return existingEmail;
		}catch(Exception e){
			log.error("Exception: ",e);
			return "";

		}
	}

	public void ppmNewFieldValidate(String amndType) {
		log.info("---------------------------insidethe ppmNewFieldValidate-------------------" );
		try {
			Boolean flagSwift=false; 
			if("Y".equalsIgnoreCase(SWIFT_UTILITY_FLAG)  && "ELC_LCAA".equalsIgnoreCase(amndType)){

				flagSwift=true;

			}
			if(!("ILC_AM".equalsIgnoreCase(requestType))&& !"ELC_LCAA".equalsIgnoreCase(requestType)){ //PT 280
				if ("IV".equalsIgnoreCase(amndType) || "DV".equalsIgnoreCase(amndType)
						|| "ILC_IV".equalsIgnoreCase(amndType) || "ILC_DV".equalsIgnoreCase(amndType)
						|| "ELC_IV".equalsIgnoreCase(amndType) || "ELC_DV".equalsIgnoreCase(amndType)
						|| "FOT".equalsIgnoreCase(amndType)) { 

					formObject.setStyle(NEW_EXP_DATE,DISABLE, "true"); 
					log.info("inside the conditions");
					formObject.setStyle(NEW_TRN_AMT,DISABLE, FALSE);
					if(!flagSwift){                                     
						formObject.setValue(NEW_EXP_DATE, "");
					}

				} else if (("EE".equalsIgnoreCase(amndType)
						|| "CE".equalsIgnoreCase(amndType)
						|| "OF".equalsIgnoreCase(amndType)
						|| "ILC_EED".equalsIgnoreCase(amndType)
						|| "ILC_CED".equalsIgnoreCase(amndType)
						|| "ELC_EED".equalsIgnoreCase(amndType)
						|| "ELC_CED".equalsIgnoreCase(amndType)
						)) {

					formObject.setStyle(NEW_TRN_AMT,DISABLE, "true");
					formObject.setStyle(NEW_EXP_DATE,DISABLE, FALSE);
					if(!flagSwift){                               
						formObject.setValue(NEW_TRN_AMT, "");}
				} else if ("CT".equalsIgnoreCase(amndType) || "OFT".equalsIgnoreCase(amndType)  
						|| "ILC_CT".equalsIgnoreCase(amndType) || "ELC_CT".equalsIgnoreCase(amndType)) {  

					formObject.setStyle(NEW_TRN_AMT,DISABLE, FALSE);
					formObject.setStyle(NEW_EXP_DATE,DISABLE, FALSE);
				} else {
					log.info(" else " + amndType);
					log.info("PT_UTILITY_FLAG="+formObject.getValue(PT_UTILITY_FLAG).toString());
					log.info("flagSwift="+flagSwift);
					//formObject.setStyle(NEW_EXP_DATE,DISABLE, "true");
					//formObject.setStyle(NEW_TRN_AMT,DISABLE, "true");

					if(!flagSwift && !"Y".equalsIgnoreCase(formObject.getValue(PT_UTILITY_FLAG).toString())){                                   
						log.info(" NEW_TRN_AMT " + formObject.getValue(NEW_TRN_AMT).toString());
						formObject.setValue(NEW_EXP_DATE, "");
						formObject.setValue(NEW_TRN_AMT, "");
					}
				}

			} else if("ILC_AM".equalsIgnoreCase(requestType)){ //US115
				if ("ILC_IV".equalsIgnoreCase(amndType) || "ILC_DV".equalsIgnoreCase(amndType)
						|| "ILC_CT".equalsIgnoreCase(amndType)) { 
					//formObject.setStyle(Q_AMENDMENT_DATA_USER_TRANSACTION_AMOUNT,DISABLE, "true"); 
					validateMandatoryFieldsPPM(Q_AMENDMENT_DATA_USER_TRANSACTION_AMOUNT, "Please Enter Amendment Transaction Amount"); //US115	
				} 
				if("ILC_IV".equalsIgnoreCase(amndType) || "ILC_EED".equalsIgnoreCase(amndType)
						|| "ILC_CED".equalsIgnoreCase(amndType)){	
					//formObject.setStyle(Q_AMENDMENT_DATA_USER_EXPIRY_DATE,DISABLE, "true");  
					validateMandatoryFieldsPPM(Q_AMENDMENT_DATA_USER_EXPIRY_DATE, "Please Enter Amendment Expiry Date");//US115
				}
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	protected void disableAmendType(String control){
		try {
			String stReqType ="";
			stReqType = formObject.getValue(REQUEST_TYPE).toString();
			if(("GRNT".equalsIgnoreCase(requestCategory) && !"AM".equalsIgnoreCase(stReqType)) 
					|| ("SBLC".equalsIgnoreCase(requestCategory) && !"SBLC_AM".equalsIgnoreCase(stReqType))
					|| ("ILC".equalsIgnoreCase(requestCategory) && !"ILC_AM".equalsIgnoreCase(stReqType))
					|| ("ELC".equalsIgnoreCase(requestCategory) && (!"ELC_LCAA".equalsIgnoreCase(stReqType) 
							|| !"ELC_SLCAA".equalsIgnoreCase(stReqType)))//added by mnasi
							|| ("TL".equalsIgnoreCase(requestCategory) && !"TL_AM".equalsIgnoreCase(stReqType)))
				formObject.setStyle(control,DISABLE,FALSE);
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	public void checkDiscrepancyDetails(){

		List<List<String>> tmpList =null;
		String checklistKey="";
		boolean flag=false;
		try{
			if("ELCB".equalsIgnoreCase(requestCategory)||"ILCB".equalsIgnoreCase(requestCategory))
			{
				if("ELCB".equalsIgnoreCase(requestCategory)){
					tmpList = formObject.getDataFromDB("SELECT doc_rvw FROM tfo_dj_doc_rvw_mast WHERE mapped_controlname='ELCB_Doc_Crdt_Compiled'");
				}else if("ILCB".equalsIgnoreCase(requestCategory)){
					tmpList = formObject.getDataFromDB("SELECT doc_rvw FROM tfo_dj_doc_rvw_mast WHERE mapped_controlname='ILCB_DOC_CRD_COMPLIED'");
				}
				checklistKey=tmpList.get(0).get(0).trim();
				log.info("checklistKey= "+checklistKey);
				String gridListName="Qvar_Doc_Rvw";
				JSONArray output=formObject.getDataFromGrid (gridListName);
				int length = output.size();

				for(int i=0;i<length;i++){
					flag=true;
					log.info("list key Type "+formObject.getTableCellValue(gridListName, i, 0));
					if(checklistKey.equalsIgnoreCase(formObject.getTableCellValue(gridListName, i, 0))){
						dList=formObject.getTableCellValue(gridListName, i, 1);
						log.info("dList= "+dList);
					}
				}
				if(flag){
					discrepancyHandling();
				}
			}}catch(Exception e){
				log.error("Exception in",e);
			}

	}

	public void discrepancyHandling() {
		log.info("DiscrepancyValue==>"+dList);
		try{
			if("ELCB".equalsIgnoreCase(requestCategory)){

				if("No".equalsIgnoreCase(dList.trim())){
					//enableFieldOnDemand(DISCREPANCY_DTLS);
					disableFieldOnDemand(DISCREPANCY_DTLS);
					discrepancyFlag = true;
					formObject.setStyle(IFRAME_DISCREPANCY, VISIBLE, TRUE);
				} else{
					discrepancyFlag = false;
					formObject.setValue(DISCREPANCY_DTLS,"");
					disableFieldOnDemand(DISCREPANCY_DTLS);
					formObject.setStyle(IFRAME_DISCREPANCY, VISIBLE, FALSE);
				}
				log.info("discrepancyFlag=>"+discrepancyFlag);
			} else if("ILCB".equalsIgnoreCase(requestCategory)){
				//|ATP-526|REYAZ|13-02-2024 START
			 	if("No".equalsIgnoreCase(dList.trim())){
			 		disableFieldOnDemand(DISCREPANCY_DTLS);
			 		formObject.setValue(DISCREPANCY_DTLS,"");
					formObject.setStyle(IFRAME_DISCREPANCY, VISIBLE, TRUE);
					discrepancyFlag = true;
				} else{
					discrepancyFlag = false;
					formObject.setValue(DISCREPANCY_DTLS,"");
					disableFieldOnDemand(DISCREPANCY_DTLS);
					formObject.setStyle(IFRAME_DISCREPANCY, VISIBLE, FALSE);
				}
				//|ATP-526|REYAZ|13-02-2024 START END
				log.info("discrepancyFlag=>"+discrepancyFlag);
			}
		}catch(Exception e){
			log.error("Exception in",e);
		}

	}

	@SuppressWarnings("unchecked")
	private void setSwiftTxnAmount() {
		try {
			log.info("inside setSwiftTxnAmount");
			String sQuery = "SELECT SWIFT_MESSAGE_TYPE FROM EXT_TFO WHERE WI_NAME ='"+sWorkitemID+"'";
			List<List<String>> record = formObject.getDataFromDB(sQuery);
			String msgType  = record.get(0).get(0);
			if ("707".equalsIgnoreCase(msgType))
			{
				String sQuery1 = "SELECT INC_DOC_CRD_AMT,DEC_DOC_CRD_AMT FROM DJ_SWIFT_TXN_DETAILS WHERE WI_NAME ='"+sWorkitemID+"'";
				List<List<String>> record1 = formObject.getDataFromDB(sQuery1);
				String incAmount = record1.get(0).get(0);
				String decAmount = record1.get(0).get(1);

				if(!"".equalsIgnoreCase(incAmount) && null != incAmount)
				{
					String trnAmount = formObject.getValue(TRANSACTION_AMOUNT).toString();
					trnAmount = String.valueOf(Integer.parseInt(trnAmount) + Integer.parseInt(incAmount));
					formObject.setValue(NEW_TRN_AMT,trnAmount);


				}else if (!"".equalsIgnoreCase(decAmount) && null != decAmount)
				{
					String trnAmount = formObject.getValue(TRANSACTION_AMOUNT).toString();
					trnAmount = String.valueOf(Integer.parseInt(trnAmount) - Integer.parseInt(decAmount));
					formObject.setValue(NEW_TRN_AMT,trnAmount);

				}

			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}

	}
	protected void clearCustSignAccBal(String accNumber){
		try {

			if(null != accNumber && "".equalsIgnoreCase(accNumber)){
				log.info("in clearCustSignAccBal");
				clearControls("SIGACC_ACC_TOTAL_BAL","SIGACC_ACC_CURR_BAL","SIGACC_DOMCL_BRN_CODE","SIGACC_ACC_CURRENCY",SIGACC_ACC_NO,"SIGACC_BRN_CODE","SIGACC_ACC_CURNY");
				formObject.clearTable(QVAR_LINKED_CUST);
			}			
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}
	protected void fetchAcctDetailsICCS()
	{
		log.info("in fetchAcctDetailsICCS");
		String inputXml = "",inputXml2="";
		String outputXml = "",outputXml2="";
		String custID="";
		try {
			String accNumber = getAccountNumber();
			inputXml = createAcctDetailsInputXML(accNumber);
			if(!"0".equalsIgnoreCase(accNumber) && inputXml.length()>0){
				outputXml = socket.connectToSocket(inputXml);
				log.info("createAcctDetailsInputXML response xml " + outputXml);
				//Added by Ketali
				XMLParser xp = new XMLParser(outputXml);
				custID=xp.getValueOf("custId");
				inputXml2=createInqCSAcctBalanceInputXML(accNumber,custID);
				if(!"0".equalsIgnoreCase(accNumber) && inputXml.length()>0){
					outputXml2 = socket.connectToSocket(inputXml2);
					log.info("InqCSAcctBalance response xml " + outputXml2);
				}
				loadLinkedCustomersDataGrid(outputXml);
				loadLinkedCustomersDataGrid1(outputXml2);
			}else {
				clearCustSignAccBal(accNumber);
			}								
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}
	
	//Added by Ketali 
	protected void loadLinkedCustomersDataGrid1(String datanew){
		try {
			log.info("in loadLinkedCustomersDataGrid1");
			StringBuffer gridData = new StringBuffer();
			String singleCustomerData = "";
			JSONArray jsonArrayCustomer=new JSONArray();
			xmlDataParser = new XMLParser();
			xmlDataParser.setInputXML(datanew);
			XMLParser innerXmlParser = new XMLParser();
			String accBalance = "";
			String liquidityBal="";
			accBalance = xmlDataParser.getValueOf("amountNetAvailable");
			liquidityBal = xmlDataParser.getValueOf("liquidityBalance");
			log.info("in loadLinkedCustomersDataGrid1 accBalance ---- " + accBalance);
			log.info("in loadLinkedCustomersDataGrid1 liquidityBal ---- " + liquidityBal);
			log.info("in loadLinkedCustomersDataGrid1 accBalance ----@@ " + accBalance.length());
			log.info("in loadLinkedCustomersDataGrid1 liquidityBal ----@@ " + liquidityBal.length());
			if(null == liquidityBal || liquidityBal.equalsIgnoreCase("") || liquidityBal.equalsIgnoreCase("null"))
			{
				log.info("in loadLinkedCustomersDataGrid1 111");
				if(null == accBalance || accBalance.equalsIgnoreCase("")|| accBalance.equalsIgnoreCase("null"))
				{
					log.info("in loadLinkedCustomersDataGrid1 222");
					formObject.setValue("SIGACC_ACC_CURR_BAL","0");
				}
				else
				{
					log.info("in loadLinkedCustomersDataGrid1 3333");
					formObject.setValue("SIGACC_ACC_CURR_BAL",accBalance);
				}
			}
			else
			{
				log.info("in loadLinkedCustomersDataGrid1 4444");
				formObject.setValue("SIGACC_ACC_CURR_BAL",liquidityBal);
			}
			log.info("in loadLinkedCustomersDataGrid1 enddddddd");
		}
		catch (Exception e) {
			log.error("Exception: ",e);
		}
	}
//	//End Ketali
	
	protected void loadLinkedCustomersDataGrid(String data){
		try {

			log.info("in loadLinkedCustomersDataGrid ");
			StringBuffer gridData = new StringBuffer();
			String singleCustomerData = "";
			JSONArray jsonArrayCustomer=new JSONArray();
			xmlDataParser = new XMLParser();
			xmlDataParser.setInputXML(data);
			XMLParser innerXmlParser = new XMLParser();	
			
			for(int i=0;i<xmlDataParser.getNoOfFields("linkedCustomer");i++){

				JSONObject jsonObject = new JSONObject();
				singleCustomerData = xmlDataParser.getNextValueOf("linkedCustomer");
				log.info("singleCustomerData "+singleCustomerData);
				innerXmlParser.setInputXML(singleCustomerData);
				//	jsonObject.put("WI_NAME",sWorkitemID);
				jsonObject.put("Signatory ID",innerXmlParser.getValueOf("custID"));
				jsonObject.put("Signatory Name",innerXmlParser.getValueOf("custName"));
				jsonObject.put("Customer Relation",innerXmlParser.getValueOf("custRelation"));
				jsonArrayCustomer.add(jsonObject);
			}
			formObject.clearTable(QVAR_LINKED_CUST);
			log.info("gridData "+jsonArrayCustomer.size());

			formObject.addDataToGrid(QVAR_LINKED_CUST, jsonArrayCustomer);
			formObject.setValue("SIGACC_ACC_TOTAL_BAL", xmlDataParser.getValueOf("netBalAvailable"));
			//commented by Ketali
			formObject.setValue("SIGACC_ACC_CURR_BAL", xmlDataParser.getValueOf("acctBalAvailable"));
			formObject.setValue("SIGACC_DOMCL_BRN_CODE", xmlDataParser.getValueOf("acctBranchCode"));
			formObject.setValue("SIGACC_ACC_CURRENCY", xmlDataParser.getValueOf("acctCurrency"));
			formObject.setValue(SIGACC_ACC_NO, xmlDataParser.getValueOf("acctNumber"));			
			try {

				amountFormat("SIGACC_ACC_TOTAL_BAL", xmlDataParser.getValueOf("acctCurrency"),"TRANSACTION_CURRENCY");
				amountFormat("SIGACC_ACC_CURR_BAL", xmlDataParser.getValueOf("acctCurrency"),"TRANSACTION_CURRENCY");
			} catch (Exception e) {
				log.error("Exception: ",e);
			}			 
			String mandate = xmlDataParser.getValueOf("acctModeofOps1");
			mandate = mandate.replaceAll("&", "~amp~");
			mandate = mandate.replaceAll("%", "~perc~");
			mandate = mandate.replaceAll("\\?", "~ques~");
			mandate = mandate.replaceAll("#", "~hash~");
			formObject.setValue("SIGACC_ACC_MNDT", mandate.replaceAll("(\r\n|\n\r|\n)", "<br />"));
			enableFieldOnDemand(VIEW_ACCOUNT_MANDATE);
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}
	
	protected String createAcctDetailsInputXML(String accNumber){
		log.info("Inside createAcctDetailsInputXML accNumber  "+accNumber);		
		StringBuffer inputXml = new StringBuffer();
		try {
			if (null!=accNumber && !"0".equalsIgnoreCase( accNumber )) {
				inputXml.append("<APWebService_Input>")
				.append("<Option>WebService</Option>")
				.append("<Calltype>TFO_AcctDetailsICCS</Calltype>")
				.append("<sysRefNumber>" + getSysRefNum()+"</sysRefNumber>")
				.append("<senderID>" + senderId + "</senderID>")
				.append("<WiName>" + sWorkitemID + "</WiName>")
				.append("<ServiceName>InqAccountDetailsICCS</ServiceName>")
				.append("<InqAccountDetailsICCSReq>")
				.append("<CreditAccountInput>")
				.append("<acctNumber>" + accNumber + "</acctNumber>")
				.append("</CreditAccountInput>")
				.append("</InqAccountDetailsICCSReq>");
			}
			log.info("createAcctDetailsInputXML Xml ===>  " + inputXml);
		} catch (Exception e) {
			log.error("Exception: ",e);
		}

		return inputXml.toString();
	} 
	
	//Added by Ketali for UTS new Web-service call for liquidity balance start
		protected String createInqCSAcctBalanceInputXML(String accNumber,String custID)
		{
			log.info("Inside createInqCSAcctBalanceInputXML accNumber  "+accNumber);
			log.info("Inside createInqCSAcctBalanceInputXML custID  "+custID);
			
			StringBuffer inputXml = new StringBuffer();
			Date d = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String sDate = dateFormat.format(d);
			try
			{
				if (null!=accNumber && !"0".equalsIgnoreCase(accNumber)) 
				{
					inputXml.append("<APWebService_Input>")
					.append("<Option>WebService</Option>")
					.append ("<Calltype>WS-2.0</Calltype>")
					.append("<InnerCalltype>InqCSAcctBalance</InnerCalltype>")	
					.append("<ServiceName>InqCSAcctBalance</ServiceName>")
					.append("<versionNo>2.0</versionNo>")
					.append("<sysRefNumber>" + getSysRefNum()+"</sysRefNumber>")
					.append("<senderID>" + senderId + "</senderID>")
				    .append("<reqTimeStamp>"+sDate+"</reqTimeStamp>")
					.append("<GetCustBalanceReq>")
					.append("<customerID>" +custID+ "</customerID>")
					.append("<accountNumber>" + accNumber + "</accountNumber>")
					.append("</GetCustBalanceReq>");
				}
				log.info("createInqCSAcctBalanceInputXML Xml ===>  " + inputXml);
			}
			catch (Exception e) {
				log.error("Exception: ",e);
			}
			return inputXml.toString();
		}
		//Added by Ketali for UTS new Web-service call for liquidity balance end
	
	
	protected String getSysRefNum(){
		String result="";
		try {
			List<List<String>> sOutputlist = formObject.getDataFromDB("SELECT SEQ_WEBSERVICE.nextval as IDValue from DUAL ");
			result = sOutputlist.get(0).get(0);	        
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		return result;
	}
	
	protected String getAccountNumber(){
		String accNo="0";
		try {
			if("GRNT".equalsIgnoreCase(requestCategory) 
					|| "SBLC".equalsIgnoreCase(requestCategory) //added by mansi
					|| "ELC".equalsIgnoreCase(requestCategory) && ("ELC_SLCA".equalsIgnoreCase(requestType) 
							|| "ELC_SLCAA".equalsIgnoreCase(requestType)
							|| "ELC_SER".equalsIgnoreCase(requestType)|| "ELC_SCL".equalsIgnoreCase(requestType))//added by mansi
							|| "ELC".equalsIgnoreCase(requestCategory) 
							|| "ILC".equalsIgnoreCase(requestCategory)
							//SHIPPING GTEE 30
							|| "SG".equalsIgnoreCase(requestCategory)){
				accNo= formObject.getValue(ACCOUNT_NUMBER).toString();
			}else if("IFS".equalsIgnoreCase(requestCategory) 
					|| "IFP".equalsIgnoreCase(requestCategory)
					|| "TL".equalsIgnoreCase(requestCategory)
					|| "EC".equalsIgnoreCase(requestCategory)
					|| "IC".equalsIgnoreCase(requestCategory)
					|| "DBA".equalsIgnoreCase(requestCategory)
					|| "ELCB".equalsIgnoreCase(requestCategory)
					|| "ILCB".equalsIgnoreCase(requestCategory)
					||"IFA".equalsIgnoreCase(requestCategory)
					||"SCF".equalsIgnoreCase(requestCategory)){	//CODE BY MOKSH  ATP 257
				accNo= formObject.getValue(INF_CHARGE_ACC_NUM).toString();
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}finally{
			return accNo;
		}

	}
	private void setSettelmentAccDetails(String pControlName){
		try {
			String sChargeAccNo = "";
			sChargeAccNo =formObject.getValue(pControlName).toString();
			String processType=formObject.getValue(PROCESS_TYPE).toString();
			log.info("signatureAccBalTabVerification: " + processType);
			log.info("Inside setSettelmentAccDetails "+pControlName +"   "+sChargeAccNo + " requestCategory "+requestCategory);
			if("EC".equalsIgnoreCase(requestCategory)
					|| "IC".equalsIgnoreCase(requestCategory)
					|| "ELCB".equalsIgnoreCase(requestCategory) 
					|| "ILCB".equalsIgnoreCase(requestCategory)
					|| "TL".equalsIgnoreCase(requestCategory)
					|| "DBA".equalsIgnoreCase(requestCategory)){					
				formObject.setValue(INF_SETTLEMENT_ACC_NUM, sChargeAccNo);
				
				setTitleCurrency(INF_SETTLEMENT_ACC_NUM,INF_SETTLEMENT_ACC_TITLE,INF_SETTLEMENT_ACC_CURR);
			}else if (("IFS".equalsIgnoreCase(requestCategory) || "IFP".equalsIgnoreCase(requestCategory)
					||"IFA".equalsIgnoreCase(requestCategory))	//CODE BY MOKSH
					&& "LD".equalsIgnoreCase(requestType)){
				formObject.setValue(INF_SETTLEMENT_ACC_NUM, sChargeAccNo);
				setTitleCurrency(INF_SETTLEMENT_ACC_NUM,INF_SETTLEMENT_ACC_TITLE,INF_SETTLEMENT_ACC_CURR);
				if("IFS".equalsIgnoreCase(requestCategory)&&!processType.equalsIgnoreCase("TSLM Front End")){
					formObject.setValue(INF_CREDIT_ACC_NUM, sChargeAccNo);
					setTitleCurrency(INF_CREDIT_ACC_NUM,INF_CREDIT_ACC_TITLE,INF_CREDIT_ACC_CURR);
				}
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}
	protected void buyerCallingFrameHandling(){
		try {
			String sBuyerCalling = "";
			sBuyerCalling =formObject.getValue("BC_CALL_DONE").toString();
			if(!"Y".equalsIgnoreCase(sBuyerCalling)){
				clearControls("BC_CALL_INFO","BC_DATE_OF_CALL","BC_RSN_OF_CALL","BC_NAME_OF_CALLER","BC_TIME_OF_CALL","BC_BUYER_NAME","BC_CONTACT_PERSON","BC_BUYER_CONTACT_NUM","BC_INVOICE_CURR" ,"BC_INVOICE_AMT","BC_INVOICE_NUMBER");
				enableFieldOnDemand("BC_REMAKS_NOT_DONE");
				formObject.setValue("BC_INVOICE_CURR", emptyStr);
				disableFieldOnDemand("BC_CALL_INFO####BC_RSN_OF_CALL####BC_INVOICE_CURR####BC_NAME_OF_CALLER####BC_TIME_OF_CALL####BC_BUYER_NAME####BC_CONTACT_PERSON####BC_BUYER_CONTACT_NUM####BC_INVOICE_AMT####BC_INVOICE_NUMBER####BC_DATE_OF_CALL");

			}else{
				clearControls("BC_REMAKS_NOT_DONE");
				disableFieldOnDemand("BC_REMAKS_NOT_DONE");
				enableFieldOnDemand("BC_CALL_INFO####BC_RSN_OF_CALL####BC_INVOICE_CURR####BC_NAME_OF_CALLER####BC_TIME_OF_CALL####BC_BUYER_NAME####BC_CONTACT_PERSON####BC_BUYER_CONTACT_NUM####BC_INVOICE_CURR####BC_INVOICE_AMT####BC_INVOICE_NUMBER####BC_DATE_OF_CALL");
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}
	protected void newMatDateHandling(){
		try {
			String sAmendType="";
			sAmendType = formObject.getValue(INF_AMEND_TYPE).toString();
			log.info("sAmendType: "+ sAmendType);
			if("IFS".equalsIgnoreCase(requestCategory)||"IFP".equalsIgnoreCase(requestCategory)
					||"IFA".equalsIgnoreCase(requestCategory)){	//CODE BY MOKSH
				if("MDC".equalsIgnoreCase(sAmendType) || "APTP".equalsIgnoreCase(sAmendType)){
					enableFieldOnDemand(INF_NEW_MATURITY_DATE);
					if("APTP".equalsIgnoreCase(sAmendType))
						emptyFlag=false; 
				}
				else{
					//formObject.setValue(INF_AMEND_TYPE, "");issue 
					//ATP-454 REYAZ 03-05-2024
					//START CODE
					if(formObject.getValue(PROCESS_TYPE).toString().equalsIgnoreCase("TSLM Front End") && "AM".equalsIgnoreCase(requestType)) {
						enableFieldOnDemand(INF_NEW_MATURITY_DATE);
					} else { 
					//START END ATP-454
					disableFieldOnDemand(INF_NEW_MATURITY_DATE);
				}
				}
			}else{

				if(("TL".equalsIgnoreCase(requestCategory) && ("MDC".equalsIgnoreCase(sAmendType)||"APTP".equalsIgnoreCase(sAmendType)))){
					enableFieldOnDemand(INF_NEW_MATURITY_DATE);
				} else if((sAmendType != null && !sAmendType.equals("") ) && (sAmendType.contains("MDC") || sAmendType.contains("CMD"))) {
					enableFieldOnDemand(INF_NEW_MATURITY_DATE);
				}
				else{
					disableFieldOnDemand(INF_NEW_MATURITY_DATE);

				}
			}

		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}
	public void getExternalTableData(){
		String sQuery = "SELECT SWIFT_UTILITY_FLAG FROM EXT_TFO WHERE " +
				"  WI_NAME = '"+this.sWorkitemID+"' ";
		@SuppressWarnings("unchecked")
		List<List<String>> record = formObject.getDataFromDB(sQuery);
		SWIFT_UTILITY_FLAG=record.get(0).get(0);
	}
	private void handlingCustAccntHolding(){		
		try {
			btnAccEnableDisable(BILL_CUST_HLDING_ACC_US);
			btnAccEnableDisable(ACCOUNT_VALID);			
		} catch (Exception e) {			
			log.error("Exception: ",e);
		}

	}
	public void setAcctDetailsICCS(){
		String accNumber = "";
		try {
			formObject.setValue("SIGACC_BRN_CODE", getDataFromMap(mapBranch,formObject.getValue("SIGACC_ACC_TOTAL_BAL").toString()));
			formObject.setValue("SIGACC_ACC_CURNY", formObject.getControlValue("SIGACC_ACC_CURRENCY").toString());						
			accNumber=getAccountNumber();
			log.info("inside setAccDetailsICCS");
			if(!"0".equalsIgnoreCase(accNumber))
				formObject.setValue(SIGACC_ACC_NO, accNumber);

		} catch (Exception e) {
			log.error("Exception: ",e);
		}				
	}
	private void setCounterGTEExpDate(String controlName){
		log.info("in setCounterGTEExpDate");
		log.info("controlName  "+controlName+" Control Value="+formObject.getValue(controlName).toString());
		String productType=formObject.getValue(PRODUCT_TYPE).toString();
		log.info("productType="+productType);

		String allFrames =COUNTER_GTE_FIELD_NOT_LIST;
		List<String> frames = Arrays.asList(allFrames.split(","));

		try{

			if(("GRNT".equalsIgnoreCase(requestCategory))&&("NI".equalsIgnoreCase(requestType))
					||("SBLC".equalsIgnoreCase(requestCategory))&&("SBLC_NI".equalsIgnoreCase(requestType))//added by mansi
					||("ELC".equalsIgnoreCase(requestCategory))&&("ELC_SLCA".equalsIgnoreCase(requestType))){//added by mansi
				if(((EXP_DATE.equalsIgnoreCase(controlName))||(productType.equalsIgnoreCase(controlName))) 
						&&(!(frames.contains(productType))))
				{
					formObject.setValue(FIELD_GRNTEE_EXP_DATE,formObject.getValue(EXP_DATE).toString());
					log.info("changing exp_date -----");
				}

			}} catch (Exception e) {
				log.error("Exception: ",e);
			}
	}
	public void ppmFieldFrmOnLoad_GTEE(String reqTpe){
		if ("NI".equalsIgnoreCase(reqTpe)) {
			enableFieldOnDemand(PRODUCT_TYPE);
			disableFieldOnDemand("AMEND_TYPE####NEW_TRN_AMT####NEW_EXP_DATE");
		} else if ("AM".equalsIgnoreCase(reqTpe)) {
			enableFieldOnDemand("AMEND_TYPE####NEW_TRN_AMT####NEW_EXP_DATE");
			disableFieldOnDemand("PRODUCT_TYPE####EXP_DATE");
			hideshowFrmInputTab(GRNT_INPUT_HIDE,false);//RR FOR GRNT INPUT FIELDS HIDE

		}else if ("CC".equalsIgnoreCase(reqTpe)|| "CL".equalsIgnoreCase(reqTpe)|| "ER".equalsIgnoreCase(reqTpe)
				||"EPC".equalsIgnoreCase(reqTpe)) {
			disableFieldOnDemand("PRODUCT_TYPE####AMEND_TYPE####EXP_DATE####ACCOUNT_NUMBER");

		} else if (!("AM".equalsIgnoreCase(reqTpe) || "NI".equalsIgnoreCase(reqTpe))) {
			disableFieldOnDemand("PRODUCT_TYPE####AMEND_TYPE####EXP_DATE");
		}
		disableFieldOnDemand("GRNT_CHRG_ACC_TITLE####GRNT_CHRG_ACC_CRNCY");
		ppmNewFieldValidate(formObject.getValue(AMEND_TYPE).toString());

	}
	private void defaultValSignMandate(){
		try {
			if( "GRNT".equalsIgnoreCase(requestCategory) && !"A".equalsIgnoreCase(requestSubmittedBy)){
				log.info("Guarantee case and not applicant : REQ_SIGN_MAN_PPM should be defaulted" +  requestSubmittedBy + "-" + formObject.getValue(REQ_SIGN_MAN_PPM).toString());
				String reqSignPerMandate = formObject.getValue(REQ_SIGN_MAN_PPM).toString();
				if(reqSignPerMandate.isEmpty()||reqSignPerMandate.equalsIgnoreCase("--Select--")||reqSignPerMandate.equalsIgnoreCase(emptyStr)){
					log.info("Setting default for grnt for sing mandate at ppm");
					formObject.setValue(REQ_SIGN_MAN_PPM, "3");
				}
			}
			else if( "SBLC".equalsIgnoreCase(requestCategory) && !"SBLC_A".equalsIgnoreCase(requestSubmittedBy)){
				log.info("SBLC case and not applicant : REQ_SIGN_MAN_PPM should be defaulted" +  requestSubmittedBy + "-" + formObject.getValue(REQ_SIGN_MAN_PPM).toString());
				String reqSignPerMandate = formObject.getValue(REQ_SIGN_MAN_PPM).toString();
				if(reqSignPerMandate.isEmpty()||reqSignPerMandate.equalsIgnoreCase("--Select--")||reqSignPerMandate.equalsIgnoreCase(emptyStr)){
					log.info("Setting default for grnt for sing mandate at ppm");
					formObject.setValue(REQ_SIGN_MAN_PPM, "3");
				}
			}
			else if(("IFS".equalsIgnoreCase(requestCategory) 
					|| "IFP".equalsIgnoreCase(requestCategory)
					|| "IFA".equalsIgnoreCase(requestCategory)) //CODE BY MOKSH
					&& "RM".equalsIgnoreCase(requestSubmittedBy)){
				formObject.setValue(REQ_SIGN_MAN_PPM, "3");
			}
			else if(!(requestSubmittedBy.contains("CT")) && !"GRNT".equalsIgnoreCase(requestCategory) 
					&& !"SBLC".equalsIgnoreCase(requestCategory)){ //changed by Santhosh
				formObject.setValue(REQ_SIGN_MAN_PPM, "3");
			}/*else if(!(requestSubmittedBy.contains("ELC_CT")) && !"ELC".equalsIgnoreCase(requestCategory)){ //changed by mansi
				formObject.setValue(REQ_SIGN_MAN_PPM, "3");
			}*/ 
			if(("IC".equalsIgnoreCase(requestCategory) && "IC_BL".equalsIgnoreCase(requestType))
					||("DBA".equalsIgnoreCase(requestCategory) && "DBA_BL".equalsIgnoreCase(requestType))
					||("EC".equalsIgnoreCase(requestCategory) && ("EC_BL".equalsIgnoreCase(requestType)||"EC_LDDI".equalsIgnoreCase(requestType)))){
				String sCustHoldingAccUs="";
				sCustHoldingAccUs = formObject.getValue(BILL_CUST_HLDING_ACC_US).toString();
				if(sCustHoldingAccUs.isEmpty()||sCustHoldingAccUs.equalsIgnoreCase("--Select--")||sCustHoldingAccUs.equalsIgnoreCase(emptyStr))
					formObject.setValue(BILL_CUST_HLDING_ACC_US, "1");
				if("DBA".equalsIgnoreCase(requestCategory)) {
					formObject.setValue(PRODUCT_TYPE, "T3V1");
				}
			}

			setLmtCrtAvl();
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}
	private void setLmtCrtAvl(){
		try {
			log.info("Inside setLMTCrtAvl  "+formObject.getValue(LMTCRE_APP_AVL_PPM).toString());
			if(!emptyAndAmountCheck(formObject.getValue(LMTCRE_APP_AVL_PPM).toString())){
				log.info("if block setLmtCrtAvl ");
				if((("IC".equalsIgnoreCase(requestCategory)) && ("IC_BL".equalsIgnoreCase(requestType) 
						|| "IC_AM".equalsIgnoreCase(requestType)|| "IC_BS".equalsIgnoreCase(requestType) 
						|| "IC_BCDR".equalsIgnoreCase(requestType) || "IC_PRC".equalsIgnoreCase(requestType)) )
						||(("DBA".equalsIgnoreCase(requestCategory)) && ("DBA_BL".equalsIgnoreCase(requestType) 
								|| "DBA_AM".equalsIgnoreCase(requestType)|| "DBA_STL".equalsIgnoreCase(requestType)) )
								|| (("EC".equalsIgnoreCase(requestCategory)) && ("EC_BL".equalsIgnoreCase(requestType) 
										|| "EC_AM".equalsIgnoreCase(requestType) || "EC_BS".equalsIgnoreCase(requestType) 
										|| "EC_BCDR".equalsIgnoreCase(requestType) || "EC_CAP".equalsIgnoreCase(requestType)))		
										|| ("ELC".equalsIgnoreCase(requestCategory) && "ELC_LCA".equalsIgnoreCase(requestType))
						){
					formObject.setValue(LMTCRE_APP_AVL_PPM, "3");
				}
			}			
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}
	private void setEnableDisabelTxnBrnCode(){
		try {
			if (!(("IC".equalsIgnoreCase(requestCategory) && "IC_BL".equalsIgnoreCase(requestType))
					||("DBA".equalsIgnoreCase(requestCategory) && "DBA_BL".equalsIgnoreCase(requestType))
					|| ("EC".equalsIgnoreCase(requestCategory) && ("EC_BL".equalsIgnoreCase(requestType) || "EC_LDDI".equalsIgnoreCase(requestType) ))
					|| ("ILCB".equalsIgnoreCase(requestCategory) && "ILCB_BL".equalsIgnoreCase(requestType))
					|| ("ELCB".equalsIgnoreCase(requestCategory) && "ELCB_BL".equalsIgnoreCase(requestType))
					|| ("ILC".equalsIgnoreCase(requestCategory) && ("ILC_NI".equalsIgnoreCase(requestType) || "ILC_UM".equalsIgnoreCase(requestType)))
					|| ("ELC".equalsIgnoreCase(requestCategory) && "ELC_LCA".equalsIgnoreCase(requestType))
					|| ("TL".equalsIgnoreCase(requestCategory) && "TL_LD".equalsIgnoreCase(requestType))
					)){
				disableFieldOnDemand(RELATIONSHIP_TYPE);
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}
	private void ilcPpmFieldFrmOnLoad(String reqTpe){
		if ("ILC_NI".equalsIgnoreCase(reqTpe)) {
			enableFieldOnDemand("PRODUCT_TYPE");
			//disableFieldOnDemand("AMEND_TYPE####NEW_TRN_AMT####NEW_EXP_DATE");
			if("Y".equalsIgnoreCase(formObject.getValue(PT_UTILITY_FLAG).toString())){
				disableFieldOnDemand("AMEND_TYPE####NEW_TRN_AMT####NEW_EXP_DATE####EXP_DATE");
			}else
				disableFieldOnDemand("AMEND_TYPE####NEW_TRN_AMT####NEW_EXP_DATE");
		}
		else if ("ILC_UM".equalsIgnoreCase(reqTpe)) {
			enableFieldOnDemand(PRODUCT_TYPE);
			if(!"OE".equalsIgnoreCase(TRN_TENOR))
			{
				enableFieldOnDemand(EXP_DATE);
				log.info("ENABLING EXP_DATE");
			}
			disableFieldOnDemand("AMEND_TYPE####NEW_TRN_AMT####NEW_EXP_DATE####TRN_THIRD_PARTY");
			formObject.setValue(LC_DOC_COURIER, "2");
			formObject.setValue(PRODUCT_TYPE,"ILS6");	//RR
			formObject.setValue(TRN_TENOR,"FD");	//RR
			formObject.setStyle(TRN_TENOR, DISABLE, TRUE); //BY KISHAN

		}else if ("ILC_AM".equalsIgnoreCase(reqTpe)) {
			enableFieldOnDemand(AMEND_TYPE); //####NEW_TRN_AMT####NEW_EXP_DATE"); US117
			disableFieldOnDemand("PRODUCT_TYPE####EXP_DATE####NEW_TRN_AMT####NEW_EXP_DATE");
		}
		else if (!("ILC_AM".equalsIgnoreCase(reqTpe) || "ILC_NI".equalsIgnoreCase(reqTpe) || "ILC_UM".equalsIgnoreCase(reqTpe))) {				
			disableFieldOnDemand("PRODUCT_TYPE####EXP_DATE####AMEND_TYPE####NEW_TRN_AMT####NEW_EXP_DATE####LC_CORRSPNDNT_BNK####LC_CONF_AMNT");
		}

		ppmNewFieldValidate(formObject.getValue(AMEND_TYPE).toString());
		confAmtCheck(requestType, requestCategory);
	}
	private void confAmtCheck(String reqType,String reqCat){
		try{
			if("ILC".equalsIgnoreCase(reqCat)){
				disableFieldOnDemand(LC_CONF_AMNT);
				if("ILC_CL".equalsIgnoreCase(requestType)){
					disableFieldOnDemand("LC_CORRSPNDNT_BNK");
				}
			}			
			if (!("ELC".equalsIgnoreCase(reqCat) 
					&& ("ELC_LCA".equalsIgnoreCase(reqType)
							||"ELC_LCAA".equalsIgnoreCase(reqType)
							|| "ELC_LCCL".equalsIgnoreCase(reqType) 
							|| "ELC_LCC".equalsIgnoreCase(reqType)))){
				disableFieldOnDemand(LC_CONF_AMNT);
				formObject.setValue(LC_CONF_AMNT, "");
			}
		}catch (Exception e) {
			log.error("Exception: ",e);
		}
	}
	private void elcPpmFieldFrmOnLoad(String reqTpe){
		if ("ELC_LCA".equalsIgnoreCase(reqTpe)) {
			enableFieldOnDemand("PRODUCT_TYPE####EXP_DATE");
			disableFieldOnDemand("AMEND_TYPE####NEW_TRN_AMT####NEW_EXP_DATE");

		} else if ("ELC_LCAA".equalsIgnoreCase(reqTpe)) {
			enableFieldOnDemand("AMEND_TYPE");
			disableFieldOnDemand("PRODUCT_TYPE####EXP_DATE####NEW_TRN_AMT####NEW_EXP_DATE"); //PT 280
			hideshowFrmInputTab(GRNT_INPUT_HIDE,false);//RR FOR GRNT INPUT FIELDS HIDE
		}		
		else if (!("ELC_LCA".equalsIgnoreCase(reqTpe) || "ELC_LCAA".equalsIgnoreCase(reqTpe))) {			
			disableFieldOnDemand("PRODUCT_TYPE####EXP_DATE####AMEND_TYPE####NEW_TRN_AMT####NEW_EXP_DATE");
		} 
		ppmNewFieldValidate(formObject.getValue(AMEND_TYPE).toString());
		confAmtCheck(requestType, requestCategory);
	}

	public void ppmFieldFrmOnLoadIFS(){
		log.info("***********ppmFieldFrmOnLoad_IFS***Form load if req_type is IFS/IFP****************");
		try {
			if ("LD".equalsIgnoreCase(requestType)) {
				enableFieldOnDemand(PRODUCT_TYPE);
				disableFieldOnDemand(INF_AMEND_TYPE);
			} else if ("AM".equalsIgnoreCase(requestType)) {
				enableFieldOnDemand(INF_AMEND_TYPE);
			//	disableFieldOnDemand("PRODUCT_TYPE####INF_TENOR_DAYS####INF_LOAN_CURR####INF_MATURITY_DATE");  
				disableFieldOnDemand("PRODUCT_TYPE####INF_LOAN_CURR####INF_MATURITY_DATE");   // md reyazuddin
			}else if ("SBLC_AM".equalsIgnoreCase(requestType)) {//added by mansi
				enableFieldOnDemand(INF_AMEND_TYPE);
				disableFieldOnDemand("PRODUCT_TYPE####INF_TENOR_DAYS####INF_LOAN_CURR####INF_MATURITY_DATE");
			}else if ("ELC_SLCAA".equalsIgnoreCase(requestType)) {//added by mansi
				enableFieldOnDemand(INF_AMEND_TYPE);
				disableFieldOnDemand("PRODUCT_TYPE####INF_TENOR_DAYS####INF_LOAN_CURR####INF_MATURITY_DATE");
			} else if("STL".equalsIgnoreCase(requestType)){
			//  disableFieldOnDemand("PRODUCT_TYPE####INF_LOAN_CURR####INF_TENOR_DAYS####INF_TENOR_BASE####INF_BASE_DOC_DATE####INF_MATURITY_DATE####INF_AMEND_TYPE");
				disableFieldOnDemand("PRODUCT_TYPE####INF_LOAN_CURR####INF_TENOR_BASE####INF_BASE_DOC_DATE####INF_MATURITY_DATE####INF_AMEND_TYPE");   // md reyazuddin
			}			
			String chargeAccNum = "";
			chargeAccNum = formObject.getValue(INF_CHARGE_ACC_NUM).toString();
			if(null != chargeAccNum){
				log.info("chargeAccNum is not null");
				formObject.setValue(SIGACC_ACC_NO,chargeAccNum);
			}
			setRemitCurAmt();
		} catch (Exception e) {
			log.error("Exception: ",e);
		}

	}

	private void setRemitCurAmt(){
		try {

			String transactionCurr = "";String transactionamt = "";
			String remittanceAmt = formObject.getValue("REMITTANCE_AMT").toString();
			String remittanceCurr = formObject.getValue("REMITTANCE_CURR").toString();
			transactionCurr = trnsCurrency;
			transactionamt = formObject.getValue(TRANSACTION_AMOUNT).toString();
			if(null == remittanceCurr || "0".equalsIgnoreCase(remittanceCurr)||"".equalsIgnoreCase(remittanceCurr)){
				if(null != transactionCurr ){
					formObject.setValue("REMITTANCE_CURR",transactionCurr);
					log.info("REMITTANCE_CURR is set");
				}
			}

			if(null != transactionamt && (("".equals(formObject.getValue("REMITTANCE_AMT"))))){
				transactionamt=transactionamt.replaceAll(",", "");
				formObject.setValue("REMITTANCE_AMT",transactionamt);
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}
	private void tlPpmFieldFrmOnLoad(){
		log.info("***********ppmFieldFrmOnLoad***Form load if req_type is Import LC Bills -ILCB****************");
		try {
			if ("TL_LD".equalsIgnoreCase(requestType)) {
				enableFieldOnDemand(PRODUCT_TYPE);
				disableFieldOnDemand(INF_AMEND_TYPE);
			} else if ("TL_AM".equalsIgnoreCase(requestType)) {
				enableFieldOnDemand(INF_AMEND_TYPE);
				disableFieldOnDemand("PRODUCT_TYPE####INF_TENOR_DAYS####INF_LOAN_CURR####INF_MATURITY_DATE");
			} else if("TL_STL".equalsIgnoreCase(requestType)){
				disableFieldOnDemand("PRODUCT_TYPE####INF_LOAN_CURR####INF_TENOR_DAYS####INF_TENOR_BASE####INF_BASE_DOC_DATE####INF_MATURITY_DATE####INF_AMEND_TYPE");
			}
			setRemitCurAmt();
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}
	private void icPpmFieldFrmOnLoad(){
		log.info("***********ppmFieldFrmOnLoad***Form load if req_type is Import Collection -IC****************");
		if ("IC_BL".equalsIgnoreCase(requestType)) {
			enableFieldOnDemand(PRODUCT_TYPE);
			disableFieldOnDemand("INF_AMEND_TYPE####INF_LOAN_CURR####INF_NEW_MATURITY_DATE####BILL_RVSD_DOC_AVL####BILL_OUR_LC_NO");
		}else if ("IC_AM".equalsIgnoreCase(requestType)) {
			enableFieldOnDemand(PRODUCT_TYPE);
			disableFieldOnDemand("INF_LOAN_CURR####INF_TENOR_DAYS####INF_MATURITY_DATE####BILL_OUR_LC_NO");
		}else{
			disableFieldOnDemand("INF_AMEND_TYPE####PRODUCT_TYPE####INF_LOAN_CURR####INF_MATURITY_DATE####INF_TENOR_BASE####INF_ACTUAL_TENOR_BASE####INF_BASE_DOC_DATE####INF_TENOR_DAYS####INF_NEW_MATURITY_DATE####BILL_RVSD_DOC_AVL####BILL_OUR_LC_NO");
		}
		if("IC_AC".equalsIgnoreCase(requestType) || "IC_BS".equalsIgnoreCase(requestType)){
			enableFieldOnDemand(BILL_MODE_OF_PMNT);
		}else{
			disableFieldOnDemand(BILL_MODE_OF_PMNT);
		}
	}
	private void dbaPpmFieldFrmOnLoad(){
		log.info("***********ppmFieldFrmOnLoad***Form load if req_type is DBA****************");
		if ("DBA_BL".equalsIgnoreCase(requestType)) {
			enableFieldOnDemand(PRODUCT_TYPE);
			disableFieldOnDemand("INF_AMEND_TYPE####INF_LOAN_CURR####INF_NEW_MATURITY_DATE####BILL_RVSD_DOC_AVL####BILL_OUR_LC_NO####BILL_MODE_OF_PMNT");
		}else if ("DBA_AM".equalsIgnoreCase(requestType)) {
			enableFieldOnDemand(PRODUCT_TYPE);
			disableFieldOnDemand("INF_LOAN_CURR####INF_TENOR_DAYS####INF_MATURITY_DATE####BILL_OUR_LC_NO####BILL_MODE_OF_PMNT");
		}else if("DBA_STL".equalsIgnoreCase(requestType)){
			enableFieldOnDemand(BILL_MODE_OF_PMNT);
			disableFieldOnDemand("INF_AMEND_TYPE####PRODUCT_TYPE####BILL_RVSD_DOC_AVL####BILL_OUR_LC_NO");
		}
	}private void ecPpmFieldFrmOnLoad(){
		log.info("***********ppmFieldFrmOnLoad***Form load if req_type is Export Collection -EC****************");
		if ("EC_BL".equalsIgnoreCase(requestType) || "EC_LDDI".equalsIgnoreCase(requestType)) {
			enableFieldOnDemand(PRODUCT_TYPE);
			disableFieldOnDemand("INF_AMEND_TYPE####INF_LOAN_CURR####INF_NEW_MATURITY_DATE####BILL_RVSD_DOC_AVL####BILL_OUR_LC_NO");
		}else if("EC_AM".equalsIgnoreCase(requestType)){
			enableFieldOnDemand(INF_AMEND_TYPE);
			disableFieldOnDemand("PRODUCT_TYPE####INF_LOAN_CURR####INF_TENOR_DAYS####INF_MATURITY_DATE####BILL_OUR_LC_NO");
		}else{
			disableFieldOnDemand("INF_AMEND_TYPE####PRODUCT_TYPE####INF_LOAN_CURR####INF_MATURITY_DATE####INF_TENOR_BASE####INF_ACTUAL_TENOR_BASE####INF_BASE_DOC_DATE####INF_TENOR_DAYS####INF_NEW_MATURITY_DATE####BILL_RVSD_DOC_AVL####BILL_OUR_LC_NO");
		}
	}
	private void ilcbPpmFieldFrmOnLoad(){
		log.info("***********ppmFieldFrmOnLoad***Form load if req_type is Import LC Bills -ILCB****************");
		if ("ILCB_BL".equalsIgnoreCase(requestType)) {
			enableFieldOnDemand(PRODUCT_TYPE);
			disableFieldOnDemand("INF_AMEND_TYPE####INF_LOAN_CURR####INF_NEW_MATURITY_DATE####BILL_RVSD_DOC_AVL");
		}else if("ILCB_AM".equalsIgnoreCase(requestType)){
			enableFieldOnDemand(INF_AMEND_TYPE);
			disableFieldOnDemand("PRODUCT_TYPE####INF_TENOR_DAYS####INF_LOAN_CURR####INF_MATURITY_DATE####BILL_OUR_LC_NO");
		} else{
			disableFieldOnDemand("INF_AMEND_TYPE####PRODUCT_TYPE####INF_LOAN_CURR####INF_MATURITY_DATE####INF_TENOR_BASE####INF_ACTUAL_TENOR_BASE####INF_BASE_DOC_DATE####INF_TENOR_DAYS####INF_NEW_MATURITY_DATE####BILL_RVSD_DOC_AVL####BILL_OUR_LC_NO");
		}
		if(!("ILCB_AC".equalsIgnoreCase(requestType) || "ILCB_BS".equalsIgnoreCase(requestType))){
			disableFieldOnDemand(BILL_MODE_OF_PMNT);
		}
	}
	private void elcbPpmFieldFrmOnLoad(){
		log.info("***********ppmFieldFrmOnLoad***Form load if req_type is Export LC Bills -ELCB****************");
		if ("ELCB_BL".equalsIgnoreCase(requestType)) {
			enableFieldOnDemand(PRODUCT_TYPE);
			disableFieldOnDemand("INF_AMEND_TYPE####INF_LOAN_CURR####INF_NEW_MATURITY_DATE####BILL_RVSD_DOC_AVL");
		}else if ("ELCB_AM".equalsIgnoreCase(requestType)) {
			enableFieldOnDemand(PRODUCT_TYPE);
			disableFieldOnDemand("INF_LOAN_CURR####INF_TENOR_DAYS####INF_MATURITY_DATE####BILL_OUR_LC_NO");
		}else{
			disableFieldOnDemand("INF_AMEND_TYPE####PRODUCT_TYPE####INF_LOAN_CURR####INF_MATURITY_DATE####INF_TENOR_BASE####INF_ACTUAL_TENOR_BASE####INF_BASE_DOC_DATE####INF_TENOR_DAYS####INF_NEW_MATURITY_DATE####BILL_RVSD_DOC_AVL####BILL_OUR_LC_NO");
		} 

	}

	public void ppmFieldFrmOnLoad(String reqTpe) {
		try {
			if("GRNT".equalsIgnoreCase(requestCategory)){
				ppmFieldFrmOnLoad_GTEE(reqTpe);
				defaultValSignMandate();
			}
			else if("SBLC".equalsIgnoreCase(requestCategory)){
				ppmFieldFrmOnLoad_SBLC(reqTpe);
				defaultValSignMandate();
			}
			else if("ILC".equalsIgnoreCase(requestCategory)){ 
				setEnableDisabelTxnBrnCode();
				ilcPpmFieldFrmOnLoad(reqTpe);
				defaultValSignMandate();
			}else if("ELC".equalsIgnoreCase(requestCategory) && ("ELC_SLCA".equalsIgnoreCase(requestType)||"ELC_SLCAA".equalsIgnoreCase(requestType)||"ELC_SER".equalsIgnoreCase(requestType)||"ELC_SCL".equalsIgnoreCase(requestType))){
				ppmFieldFrmOnLoad_ELC(reqTpe);
				defaultValSignMandate();
			}else if("ELC".equalsIgnoreCase(requestCategory)){
				setEnableDisabelTxnBrnCode();
				elcPpmFieldFrmOnLoad(reqTpe);
				defaultValSignMandate();
			}
			else if("IFS".equalsIgnoreCase(requestCategory)||"IFP".equalsIgnoreCase(requestCategory)
					||"IFA".equalsIgnoreCase(requestCategory)){		//CODE BY MOKSH		
				disableControls(IFS_LOAN_GRP);				
				ppmFieldFrmOnLoadIFS();
				defaultValSignMandate();
			}
			else if("TL".equalsIgnoreCase(requestCategory)){
				setEnableDisabelTxnBrnCode();
				tlPpmFieldFrmOnLoad();
				defaultValSignMandate();
			}
			else if("IC".equalsIgnoreCase(requestCategory)){
				setEnableDisabelTxnBrnCode();
				icPpmFieldFrmOnLoad();
				defaultValSignMandate();
			}else if("DBA".equalsIgnoreCase(requestCategory)){
				dbaPpmFieldFrmOnLoad();
				defaultValSignMandate();
			}	
			else if("EC".equalsIgnoreCase(requestCategory)){
				setEnableDisabelTxnBrnCode();
				ecPpmFieldFrmOnLoad();
				defaultValSignMandate();
			}			
			else if("ILCB".equalsIgnoreCase(requestCategory)){
				setEnableDisabelTxnBrnCode();
				ilcbPpmFieldFrmOnLoad();
				defaultValSignMandate();
			}
			else if("ELCB".equalsIgnoreCase(requestCategory)){
				setEnableDisabelTxnBrnCode();
				elcbPpmFieldFrmOnLoad();
				defaultValSignMandate();
			}
			else if("SCF".equalsIgnoreCase(requestCategory)){		//Added by Shivanshu FOR SCF ATP - 199		
				ppmFieldFrmOnLoadSCF(reqTpe);
			}

		} catch (Exception e) {
			log.error("Exception: ",e);
		}


	}
	private boolean isMaturityBaseDocDateAllowed(){
		if("IFS".equalsIgnoreCase(requestCategory)
				|| "IFP".equalsIgnoreCase(requestCategory)
				|| "TL".equalsIgnoreCase(requestCategory)
				|| "DBA".equalsIgnoreCase(requestCategory)
				|| "EC".equalsIgnoreCase(requestCategory)
				|| "IC".equalsIgnoreCase(requestCategory)
				|| "ILCB".equalsIgnoreCase(requestCategory)
				|| "ELCB".equalsIgnoreCase(requestCategory)
				||"IFA".equalsIgnoreCase(requestCategory))//CODE BY MOKSH
		{
			return true;
		}else{
			return false;
		}
	}
	private boolean chkMaturityDtTenorDay(){
		if(("IFS".equalsIgnoreCase(requestCategory)
				|| "IFP".equalsIgnoreCase(requestCategory)
				||"IFA".equalsIgnoreCase(requestCategory)) //CODE BY MOKSH
				&&("LD".equalsIgnoreCase(requestType)
						|| "AM".equalsIgnoreCase(requestType) 
						|| "IFA_CTP".equalsIgnoreCase(requestType))) {  //ATP-407 14-FEB-24 --JAMSHED
			return true;
		}else if("TL".equalsIgnoreCase(requestCategory)
				&&("TL_LD".equalsIgnoreCase(requestType)
						||"TL_AM".equalsIgnoreCase(requestType))){
			return true;
		}
		else if("EC".equalsIgnoreCase(requestCategory)
				&&("EC_BL".equalsIgnoreCase(requestType)
						||"EC_LDDI".equalsIgnoreCase(requestType))){
			return true;
		}
		else if("IC".equalsIgnoreCase(requestCategory)
				&&("IC_BL".equalsIgnoreCase(requestType))){
			return true;
		}
		else if("ILCB".equalsIgnoreCase(requestCategory)
				&&("ILCB_BL".equalsIgnoreCase(requestType))){
			return true;
		}
		else if("ELCB".equalsIgnoreCase(requestCategory)
				&&("ELCB_BL".equalsIgnoreCase(requestType))){
			return true;
		}else if("DBA".equalsIgnoreCase(requestCategory)
				&&("DBA_BL".equalsIgnoreCase(requestType))){
			return true;
		}
		else{
			return false;
		}
	}
	protected void setMaturityDateIFS(){
		int loanTenorDays=0;
		String sBaseDocDat="";
		Date dBaseDocDate=null;
		Date dMatDate=null;
		Calendar cal=new GregorianCalendar();
		loanTenorDays = Integer.parseInt(formObject.getValue(INF_TENOR_DAYS).toString().isEmpty()?"0":formObject.getValue(INF_TENOR_DAYS).toString());
		sBaseDocDat = formObject.getValue(INF_BASE_DOC_DATE).toString();
		log.info("Tenor "+loanTenorDays +"  sBaseDocdate "+sBaseDocDat);
		if(!sBaseDocDat.isEmpty())
		{
			try{
				dBaseDocDate = sdf.parse(sBaseDocDat);
				log.info("Base Doc Date "+dBaseDocDate);
				cal.setTime(dBaseDocDate);
				cal.add(Calendar.DATE, loanTenorDays);
				dMatDate=cal.getTime();
				log.info("Maturity Date "+dMatDate);
			}catch(Exception e){
				log.error("Exception: ",e);
			}
		}else{
			formObject.setValue(INF_MATURITY_DATE,formObject.getValue(INF_MATURITY_DATE).toString());
		}
		if(!checkCurrentDateValidation(sdf.format(dMatDate))){
			formObject.setValue(INF_MATURITY_DATE,"");
			sendMessageHashMap("", "Maturity Date Cannot be less than current Date");
		}else{
			formObject.setValue(INF_MATURITY_DATE, sdf.format(dMatDate));
			if(validateNewMaturityDate() && isNewMaturityBaseDocDateAllowed()){
				formObject.setValue(INF_NEW_MATURITY_DATE, sdf.format(dMatDate));
			}
		}
	}
	protected boolean checkCurrentDateValidation(String sMatDate){
		try {
			log.info("MatDate  "+sMatDate);
			Date dMatDate=null;
			Date todayDate = new Date();
			try{
				dMatDate = sdf.parse(sMatDate);
				todayDate = sdf.parse(sdf.format(todayDate));

			}catch(Exception e){
				log.error("Exception: ",e);
			}
			log.info("TodatDate "+todayDate.getTime());
			log.info("MatDate "+dMatDate.getTime());
			if(!(todayDate.before(dMatDate)||todayDate.equals(dMatDate))){
				return false;	
			}else{
				return true;	
			}
		} catch (Exception e) {		
			log.error("Exception: ",e);
			return true;
		}
	}
	protected String maturityDateValidations(String controlName){

		String msg="";
		if (isMaturityBaseDocDateAllowed()) {
			if (!checkCurrentDateValidation(formObject.getValue(controlName).toString())) {
				formObject.setValue(controlName, sMatDate);
				msg=ALERT_MATURITY_DATE_GREATTER;
			} else if (!checkBaseMatDatevalidation()) {
				formObject.setValue(controlName, sMatDate);
				msg=ALERT_MATURITY_DATE_BASE;
			} else {
				if (chkMaturityDtTenorDay()) {
					setTenorDays();
				}
			}
		}	
		return msg;
	}
	private void inputTabFrmHideShowPPM(){
		hideSWIFTFrame();
		disableFieldOnDemand(REF_ICG_RM);
		if("ELC".equalsIgnoreCase(requestCategory) || "ILC".equalsIgnoreCase(requestCategory)||
				"IFS".equalsIgnoreCase(requestCategory) || "IFP".equalsIgnoreCase(requestCategory)
				|| "TL".equalsIgnoreCase(requestCategory) ||"IFA".equalsIgnoreCase(requestCategory)){//CODE BY MOKSH
			setIfTlDataOnload();
		}

		if("IFS".equalsIgnoreCase(requestCategory) || "IFP".equalsIgnoreCase(requestCategory)
				|| "IC".equalsIgnoreCase(requestCategory) || "EC".equalsIgnoreCase(requestCategory)
				|| "ILCB".equalsIgnoreCase(requestCategory) || "ELCB".equalsIgnoreCase(requestCategory)
				|| "TL".equalsIgnoreCase(requestCategory)|| "DBA".equalsIgnoreCase(requestCategory)
				||"IFA".equalsIgnoreCase(requestCategory)){
			if("IFS".equalsIgnoreCase(requestCategory) || "IFP".equalsIgnoreCase(requestCategory)
					||"IFA".equalsIgnoreCase(requestCategory)){//CODE BY MOKSH
				buyerCallingFrameHandling();
			}
			newMatDateHandling();

		}
		if("ELCB".equalsIgnoreCase(requestCategory) 
				|| "ILCB".equalsIgnoreCase(requestCategory)
				|| "IC".equalsIgnoreCase(requestCategory)
				|| "DBA".equalsIgnoreCase(requestCategory)
				|| "EC".equalsIgnoreCase(requestCategory)){
			ieccbInputFrm();
			setIfTlDataOnload();
			makeNonEditableIfsInputDtlsTab();
			setTreasuryTabProctectedFields();
			setVisibleProDealFrame();

			if(!(("ILCB_BL".equalsIgnoreCase(requestType))||("ILCB_AC".equalsIgnoreCase(requestType))||("ELCB_BL".equalsIgnoreCase(requestType))))
			{
				disableFieldOnDemand("LC_LIMIT_LINE"); 
			}

		}
	}
	private void tempProtectedBillsPrd(){
		try {
			if("ILCB".equalsIgnoreCase(requestCategory) || "ELCB".equalsIgnoreCase(requestCategory)
					|| "IC".equalsIgnoreCase(requestCategory) || "EC".equalsIgnoreCase(requestCategory)
					|| "DBA".equalsIgnoreCase(requestCategory)){
				if(!("ILCB_BL".equalsIgnoreCase(requestType) || "ELCB_BL".equalsIgnoreCase(requestType)                    
						|| "IC_BL".equalsIgnoreCase(requestType) || "EC_BL".equalsIgnoreCase(requestType)
						|| "EC_LDDI".equalsIgnoreCase(requestType) || "DBA_BL".equalsIgnoreCase(requestType))){
					String param="INF_TENOR_DAYS####INF_TENOR_BASE####INF_BASE_DOC_DATE####INF_ACTUAL_TENOR_BASE";
					disableFieldOnDemand(param);
				}
				enableFieldOnDemand("BILL_OUR_LC_NO####BILL_CORRSPNDNT_BNK");
			}else if("ELC".equalsIgnoreCase(requestCategory) && ("ELC_SLCA".equalsIgnoreCase(requestType) ||"ELC_SLCAA".equalsIgnoreCase(requestType)//added by mansi
					|| "ELC_SER".equalsIgnoreCase(requestType)|| "ELC_SCL".equalsIgnoreCase(requestType))){
				enableFieldOnDemand(TRN_THIRD_PARTY);
			}else if("ELC".equalsIgnoreCase(requestCategory)){
				disableFieldOnDemand(TRN_THIRD_PARTY);
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}

	}
	protected void setDefaultPaymentMode(){
		try {
			if(("EC".equalsIgnoreCase(requestCategory) 
					&& ("EC_BL".equalsIgnoreCase(requestType) || "EC_LDDI".equalsIgnoreCase(requestType)))){
				setEmptyCombo(BILL_MODE_OF_PMNT, "EC_AC");
			}
			else if(("ELCB".equalsIgnoreCase(requestCategory) && "ELCB_BL".equalsIgnoreCase(requestType))){
				setEmptyCombo(BILL_MODE_OF_PMNT, "ELCB_AC");
			}
			else if(("IC".equalsIgnoreCase(requestCategory) && "IC_AC".equalsIgnoreCase(requestType))){
				setEmptyCombo(BILL_MODE_OF_PMNT, "IC_AD");
			}
			else if("ILCB".equalsIgnoreCase(requestCategory) && "ILCB_AC".equalsIgnoreCase(requestType)){
				setEmptyCombo(BILL_MODE_OF_PMNT, "ILCB_AD");
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}
	private void hideSWIFTFrame(){

		if(("DBA_AM".equalsIgnoreCase(requestType))||("EC_AC".equalsIgnoreCase(requestType))||("EC_BS".equalsIgnoreCase(requestType))||("EC_AM".equalsIgnoreCase(requestType))
				||("ELC_LCA".equalsIgnoreCase(requestType))||("ELC_LCAA".equalsIgnoreCase(requestType))||("ELC_LCC".equalsIgnoreCase(requestType))||("ELCB_AC".equalsIgnoreCase(requestType))
				||("ELCB_AM".equalsIgnoreCase(requestType))||("ELCB_BS".equalsIgnoreCase(requestType))||("IC_AM".equalsIgnoreCase(requestType))||("ILCB_AM".equalsIgnoreCase(requestType))
				||("GRNT".equalsIgnoreCase(requestCategory) &&("NI".equalsIgnoreCase(requestType)||"AM".equalsIgnoreCase(requestType)||"CC".equalsIgnoreCase(requestType)||"CL".equalsIgnoreCase(requestType)||"ER".equalsIgnoreCase(requestType)||"EPC".equalsIgnoreCase(requestType)))
				// Shipping_gtee_28
				||("SG".equalsIgnoreCase(requestCategory)) 
				||("SBLC".equalsIgnoreCase(requestCategory) &&("SBLC_NI".equalsIgnoreCase(requestType)||"SBLC_AM".equalsIgnoreCase(requestType)|| "SBLC_ER".equalsIgnoreCase(requestType) ||"SBLC_CR".equalsIgnoreCase(requestType)|| "SBLC_CS".equalsIgnoreCase(requestType)))//added by mansi
				||("ELC".equalsIgnoreCase(requestCategory) &&("ELC_SLCA".equalsIgnoreCase(requestType)||"ELC_SLCAA".equalsIgnoreCase(requestType)||"ELC_SER".equalsIgnoreCase(requestType)||"ELC_SCL".equalsIgnoreCase(requestType)))){//added by mansi
			hideshowFrmInputTab("FRM_SWIFT",false);                       

		}

	}
	public void setModeOfGuarantee(){
		try{
			log.info("Inside setModeOfGuarantee");
			String sGTMODMast = "";
			List<List<String>> sResultSet = null;
			String sQuery1 =  "SELECT TRNS.RESPONSE FROM TFO_DJ_GT_MOD_MAST GT, TFO_DJ_DOC_RVW_RECORDS TRNS WHERE TRNS.RESPONSE =GT.DESCRIPTION AND TRNS.WI_NAME = '"+this.sWorkitemID+"'";
			log.info("Query "+sQuery1);
			sResultSet = formObject.getDataFromDB(sQuery1);
			log.info("Rseultset "+sResultSet);
			if(sResultSet != null){
				if(!sResultSet.isEmpty() && !sResultSet.get(0).get(0).isEmpty())
					sGTMODMast = sResultSet.get(0).get(0);
			}
			log.info("Mode Of Gtee "+sGTMODMast);
			formObject.setValue("MODE_OF_GTEE", sGTMODMast);
		}catch(Exception e){
			log.error("Exception: ",e);
		}
	}
	protected boolean submitPPMValidations(int docCheckListCount,int limitCheckListCount){
		try {
			String strDecision = "";
			String strDecReason = "";
			String sRouteTo = "";
			strDecision = formObject.getValue(DEC_CODE).toString();
			strDecReason = formObject.getValue(REJ_RESN_PPM).toString();

			log.info("strDecision "+strDecision +"  strDEcReason "+strDecReason+"   sRouteTo "+sRouteTo);
			log.info("emptyFlag "+emptyFlag);
			if(duplicateCheckValidation()){
				if(!(strDecision.isEmpty() || strDecision.equalsIgnoreCase("0") || strDecision.equalsIgnoreCase(""))){
					if((strDecision.equalsIgnoreCase("APP")||strDecision.equalsIgnoreCase("ALDAC"))
							&& ppmValidateInputFrm(formObject.getValue(REQUEST_TYPE).toString())
							&& counterPartyListSubmitValidation()	//by Rakshita
							&& signatureAccBalTabVerification()
							&& (!emptyFlag?checkMandatoryJSPValidation(DOC_RVW_TAB,ADD_LVW_DOC_RVW,docCheckListCount):true)
							&& docRevSuccTabVerification()
							&& txtVettTabVerification()
							&& (!emptyLimitFlag?checkMandatoryJSPValidation(LMT_CRDT_TAB,ADD_LMY_CRDT_RVW,limitCheckListCount):true)
							&& lmtCreTabVerification()
							&& duplicateCheckValidation()
							&& validateTSLMFetchButton()
							&& validateMandatoryFieldsPPM(ROUTE_TO_PPM,"Please select Route To")
							//&& validateMandatoryFieldsPPM(FCUBS_PUR_OF_MSG,"Please select FCUBS Purpose of Message.")
							&&PPMvalidateMandatoryPurposeMessage()
							&& validatePastDue()  //ATP-463 24-06-2024 --JAMSHED
							){

						return true;
					}

					else if(strDecision.equalsIgnoreCase("REF")
							&& ppmValidateInputFrm(formObject.getValue(REQUEST_TYPE).toString())
							&& signatureAccBalTabVerification()
							&& (!emptyFlag?checkMandatoryJSPValidation(DOC_RVW_TAB,ADD_LVW_DOC_RVW,docCheckListCount):true)
							&& docRevSuccTabVerification()
							&& txtVettTabVerification()
							&& (!emptyLimitFlag?checkMandatoryJSPValidation(LMT_CRDT_TAB,ADD_LMY_CRDT_RVW,limitCheckListCount):true)
							&& lmtCreTabVerification()
							&& checkReferralCountPPM()
							&& validateTSLMFetchButton()){
						log.info("Inside REF TRUE: ");
						return true;
					}
					else if(strDecision.equalsIgnoreCase("REJ")){
						 if(strDecReason.isEmpty()|| strDecReason.equalsIgnoreCase("0")|| strDecReason.equalsIgnoreCase("")){
							sendMessageHashMap(REJ_RESN_PPM, "Please select reason for rejection.");
							return false;
						 }
						return true;
						 
					}
					else if(strDecision.equalsIgnoreCase("INTREJ")){
						return true;
					}
					else if(strDecision.equalsIgnoreCase("TAOS")){//added by mansi
						return true;
					}
					else if(strDecision.equalsIgnoreCase("EPOCR")){	//RR
						return true;
					}
					else if(strDecision.equalsIgnoreCase("STBP")){//added by santhosh(test)
						log.info("Inside STBP CONDITION TRUE: "+emptyFlag);
						return true;
					}
					else
						return false;
				}
				else{

					sendMessageHashMap(DEC_CODE, "Please select Decision.");
					return false;
				}
			}else{
				return false;
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
			return false;
		}

	}


	protected void enableDisableMultipleBtnControl(String buttonName,boolean flag) {
		try {
			String [] btnrArry=buttonName.split(",");
			for(String btn: btnrArry){
				this.formObject.setStyle("btn", "flag","N");

				this.formObject.setStyle("btn","backcolor","red");
			}            
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}
	/*
	private boolean validateVerifyDtlsTab() {
		if (validateMandatoryFieldsPPM(SOURCE_CHANNEL, "Please Select Source Channel")
				&& validateMandatoryFieldsPPM(RELATIONSHIP_TYPE, "Please Select Transcation Branch Code")
				&& validateMandatoryFieldsPPM(DELIVERY_BRANCH, "Please Select Transcation Delivery Branch")) {
			return true;
		} else {
			return false;
		}
	}
	 */


	public boolean ppmValidateInputFrm(String reqTpe) {
		log.info("***********ppmValidateInputFrm(String reqTpe)*******************"+reqTpe);
		boolean bRes=false;
		try {
			if("GRNT".equalsIgnoreCase(requestCategory))
				bRes = ppmValidateInputGTEE(reqTpe);
			else if("SBLC".equalsIgnoreCase(requestCategory)){//changes by santhosh(Standby Lc)
				bRes = ppmValidateInputGTEE_SBLC(reqTpe);
			}
			else if("IFS".equalsIgnoreCase(requestCategory) || "IFP".equalsIgnoreCase(requestCategory)
					|| "IFA".equalsIgnoreCase(requestCategory) || "SCF".equalsIgnoreCase(requestCategory)){	//ADDED BY SHIVANSHU FOR SCF REQ
				bRes = infInputFrameValidate() && ("IFS".equalsIgnoreCase(requestCategory)?("LD".equalsIgnoreCase(requestType) || "PD".equalsIgnoreCase(requestType) || "PDD".equalsIgnoreCase(requestType))?callingFrameValidate():true:true); //ADDED BY SHIVANSHU FOR DECISION VALIDATION PPM  ATP - 203
			}
			else if("ELC".equalsIgnoreCase(requestCategory)){ // Export LC
				bRes = elcPpmValidateInput(reqTpe);        
			}
			else if("ILC".equalsIgnoreCase(requestCategory) ){ // Import LC
				bRes = ilcPpmValidateInput(reqTpe);
			}
			else if("TL".equalsIgnoreCase(requestCategory) ){ // Trade Loans
				bRes=tlInputFrameValidate();
			}
			else if("EC".equalsIgnoreCase(requestCategory) ){ //Export Collections
				bRes=ecInputFrameValidate();
			}
			else if("IC".equalsIgnoreCase(requestCategory) ){ //Import Collections
				bRes=icInputFrameValidate();
			}
			else if("DBA".equalsIgnoreCase(requestCategory) ){ //Import Collections
				bRes=dbaInputFrameValidate();
			}
			else if("ELCB".equalsIgnoreCase(requestCategory) ){ //Export LC Bills 
				bRes=elcbInputFrameValidate();
			}
			else if("ILCB".equalsIgnoreCase(requestCategory) ){ //Import LC Bills
				bRes=ilcbInputFrameValidate();
			}
			else if("SG".equalsIgnoreCase(requestCategory)){ //Shipping GTEE 27
				bRes=SGInputFrameValidate(reqTpe);
			}
			log.info("***********ppmValidateInputFrm(String reqTpe) returns=> "+bRes+"*******************");
			return bRes;
		} catch (Exception e) {
			log.error("Exception: ",e);
			return bRes;
		}
	}

	private boolean validateCustomerDtlsTab() {
		return true;
	}

	private boolean validateCPDTab(int cpdListCount,String buttonId,String controlName){
		try { log.info("REQTYPE: "+ requestType +" sActivityName: "+sActivityName+" requestCategory: "+ requestCategory);
		log.info("Inside validateCPDTab");
		if(("PP_MAKER".equalsIgnoreCase(sActivityName)||"PROCESSING MAKER".equalsIgnoreCase(sActivityName))
				&& ("IFS".equalsIgnoreCase(requestCategory) || "IFP".equalsIgnoreCase(requestCategory)
						|| "IFA".equalsIgnoreCase(requestCategory) || "SCF".equalsIgnoreCase(requestCategory))	//CODE BY MOKSH ATP - 200
						&& ("LD".equalsIgnoreCase(requestType) || "AM".equalsIgnoreCase(requestType)
								|| "PD".equalsIgnoreCase(requestType) || "PDD".equalsIgnoreCase(requestType))){
			log.info("List count for couter party detail tab  : "+cpdListCount);

			if(controlName.equalsIgnoreCase("Qvar_cpd_details")){
				if(!("IFS".equalsIgnoreCase(requestCategory) || "IFP".equalsIgnoreCase(requestCategory)
						|| "IFA".equalsIgnoreCase(requestCategory)) && ("LD".equalsIgnoreCase(requestType) ||
								"IFA_CTP".equalsIgnoreCase(requestType)))
				{
					if(!(0< cpdListCount)){ //Qvar_cpd_details
						sendMessageHashMap(buttonId, "Please add Counter party detail grid at Counter party detail tab");
						return false;
					}
				}
			}else if(controlName.equalsIgnoreCase("Q_TSLm_Counter_Dets")){
				if(!(0< cpdListCount)){
					sendMessageHashMap(buttonId, "Please add TSLM Country party details at Counter party detail tab");
					return false;
				}
			}
//				else if(controlName.equalsIgnoreCase("Q_TSLM_Invoice_No_SA_Loan")){
//			
//					if(!(0< cpdListCount)){
//						sendMessageHashMap(buttonId, "Please add Invoice Numbers For Standalone Loan at Counter party detail tab");
//						return false;
//					}
//				
//				
//			}
		}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		return true;
	}

	public boolean signatureAccBalTabVerification() {
		String processType=formObject.getValue(PROCESS_TYPE).toString();
		log.info("signatureAccBalTabVerification: " + processType);
        if(!processType.equalsIgnoreCase("TSLM Front End")){
			if (validateMandatoryFieldsPPM(REQ_SIGN_MAN_PPM, "Please select Request Signed as per Mandate")
					&& validateMandatoryFieldsPPM(SUFF_BAL_AVL_PPM, "Please Select Sufficient Balance Available")
					&& validateReferTo(REQ_SIGN_MAN_PPM, "add_SIGN_REFERRAL_ID", "Please select ReferTo at Signature and Acc check checklist", "TFO_DJ_TSLM_REFERRAL_DETAIL")
					&& validateReferTo(SUFF_BAL_AVL_PPM, "add_SIGN_REFERRAL_ID", "Please select ReferTo at Signature and Acc check checklist", "TFO_DJ_TSLM_REFERRAL_DETAIL")
					//&& checkRemarksManOnGridSig("TFO_DJ_REF_SIG_ACC", REQ_SIGN_MAN_PPM, SUFF_BAL_AVL_PPM,"add_lvwSignAcc")
					//&& sigCheckGridStatus(REQ_SIGN_MAN_PPM, SUFF_BAL_AVL_PPM,"add_lvwSignAcc")
					) {
			 
				return true;
       }else{
				return false;
			}
	     }else{
    	   return true;
       }

	}
	public boolean validateReferTo(String checkControl, String referTo, String alertMessage, String valTableName) {
		log.info("referTo: " + referTo + " " + checkControl + " checkControl: " + formObject.getValue(checkControl).toString());
		if ("2".equalsIgnoreCase(formObject.getValue(checkControl).toString()) || "No".equalsIgnoreCase(formObject.getValue(checkControl).toString())) {
			String strQuerySing = "Select count(1) CNT from " + valTableName + " where WI_NAME ='" + this.sWorkitemID + "'  and status = 'Active' ";
			log.info(" Final strQuerySing : " + strQuerySing);
			int chksNo = getSerialNumber(strQuerySing);
			log.info(" Final chksNo : " + chksNo);
			if (chksNo == 1) {
				return validateMandatoryFieldsPPM(referTo, alertMessage);
			} else {
				return true;
			}

		}
		return true;
	}
	public boolean checkRemarksManOnGrid(String tableName, String lovControl1, String lovControl2,String buttonName) {

		String strQueryOthers = "Select count(1) CNT from " + tableName + " where WI_NAME ='" + this.sWorkitemID + "'";
		log.info(" Final strQueryOthers : " + strQueryOthers);
		log.info(" Final lovControl1 : " + formObject.getValue(lovControl1));
		log.info(" Final lovControl2 : " + formObject.getValue(lovControl2));
		if ("2".equalsIgnoreCase(formObject.getValue(lovControl1).toString())) {
			int chksNo = getSerialNumber(strQueryOthers);
			log.info(" Final chksNo2 on rmk : " + chksNo);
			if (chksNo == 1) {
				sendMessageHashMap(buttonName, "Grid is empty, kindly add some remarks");
				return false;
			} else {
				return true;
			}
		}
		return true;
	}

	public boolean docRevSuccTabVerification() {
		String processType=formObject.getValue(PROCESS_TYPE).toString();
		log.info("signatureAccBalTabVerification: " + processType);
        if(!processType.equalsIgnoreCase("TSLM Front End")){
			if (validateMandatoryFieldsPPM(DOC_REV_SUCC_PPM, "Please Select Doc Review Successful")
					&& validateReferTo(DOC_REV_SUCC_PPM, "add_Doc_Review_RefID", "Please select ReferTo at Doc Review", "TFO_DJ_TSLM_DOCUMENT_REVIEW")
					//&& checkRemarksManOnGrid("TFO_DJ_REF_DOC_RVW", DOC_REV_SUCC_PPM, "DOCREV_EXC_RMKS",ADD_LVW_DOC_RVW)
					//&& checkGridStatus("TFO_DJ_REF_DOC_RVW", DOC_REV_SUCC_PPM,ADD_LVW_DOC_RVW)
					&& mandatoryFieldsELCBPPM()
					) {   

				return true;
			} else {
				return false;
		}}else{
			return true;
		}

	}

	public boolean mandatoryFieldsELCBPPM(){
		log.info(" Final mandatoryFieldsELCBPPM : ");
		/*if("ELCB".equalsIgnoreCase(requestCategory) && discrepancyFlag){
			if("".equalsIgnoreCase(formObject.getValue(DISCREPANCY_DTLS).toString()) || formObject.getValue(DISCREPANCY_DTLS) == null){
				sendMessageHashMap(DISCREPANCY_DTLS, "Please fill the discrepancy details.");
				return false;
			}
		}else*/ if(("ILCB".equalsIgnoreCase(requestCategory) || "ELCB".equalsIgnoreCase(requestCategory)) && discrepancyFlag) {
			int iRowCount = getGridCount(LISTVIEW_DISCREPANCY_DETAILS);
			log.info(" Final iRowCount : "+iRowCount);
			if(iRowCount<1) {
				sendMessageHashMap("", "Please fill the data in  discrepancy details grid");
				return false;
			}
			for (int count = 0; count < iRowCount; count++) {
				String full_text = formObject.getTableCellValue(LISTVIEW_DISCREPANCY_DETAILS, count,2);
				if ("".equals(full_text)){
					sendMessageHashMap("", "Discription Discrepancies field should not be empty in discrepancy details grid");
					return false;
				}
			}
		}
		return true;
	}


	public boolean checkRemarksManOnGridSig(String tableName, String lovControl1, String lovControl2,String buttonName) {

		String strQueryOthers = "Select count(1) CNT from " + tableName + " where WI_NAME ='" + this.sWorkitemID + "'";
		log.info(" Final strQueryOthers : " + strQueryOthers);
		log.info(" Final lovControl1 : " + formObject.getValue(lovControl1));
		log.info(" Final lovControl2 : " + formObject.getValue(lovControl2));
		if ("2".equalsIgnoreCase(formObject.getValue(lovControl1).toString()) || "2".equalsIgnoreCase(formObject.getValue(lovControl2).toString())) {
			int chksNo = getSerialNumber(strQueryOthers);
			log.info(" Final chksNo2 on rmk : " + chksNo);
			if (chksNo == 1) {
				sendMessageHashMap(buttonName, "Grid is empty, kindly add some remarks");
				return false;
			} else {
				return true;
			}
		}
		return true;
	}
	public boolean checkGridStatus(String tableName, String lovControl1,String buttonName) {

		String strQueryOthers = "Select count(1) CNT from " + tableName + " where WI_NAME ='" + this.sWorkitemID + "'";
		log.info(" Final strQueryOthers : " + strQueryOthers);
		if (!"2".equalsIgnoreCase(formObject.getValue(lovControl1).toString())) {
			int chksNo = getSerialNumber(strQueryOthers);
			log.info(" Final chksNo : " + chksNo);
			if (chksNo > 1) {
				sendMessageHashMap(buttonName, "Grid is not empty, kindly check");
				return false;
			} else {
				return true;
			}
		}
		return true;
	}

	protected boolean checkMandatoryJSPValidation(String sTabCode, String sButtonName,int docCheckListCount){
		try {    		
			String sRequestType = "";
			String sRequestCategory = "";
			String sTabName = "";
			sTabName =  tabDescIdMap.get (sTabCode);
			sRequestType =formObject.getValue(REQUEST_TYPE).toString();
			sRequestCategory =  formObject.getValue(REQUEST_CATEGORY).toString();
			String processType=formObject.getValue(PROCESS_TYPE).toString(); 
			log.info("Request Type "+sRequestType +" sRequest Category "+sRequestCategory +" TabName "+sTabName);
			int rowCnt = 0;

			List<List<String>> lstmaster =  null; 
			if(!"IC_AC".equalsIgnoreCase(sRequestType)&&!"FIN".equalsIgnoreCase("BILL_STAGE")){
				String sQuery = "SELECT REQ_CAT, REQ_TYPE, DOC_RVW AS LABEL,CNTRL_TYPE AS LOV_STATUS,DOC_RVW_KEY AS TABLE_REF, TAB_NAME, IS_TP AS IS_THIRD_PARTY"+
						" FROM tfo_dj_doc_rvw_mast where TAB_NAME = '"+sTabCode+"' and REQ_TYPE_CODE = '"+requestType+"' and REQ_CAT_CODE = '"+requestCategory+"'";
				log.info("sQuery "+sQuery);
				lstmaster = formObject.getDataFromDB(sQuery);
				if(lstmaster!=null){
					if(!lstmaster.isEmpty()){
						if(docCheckListCount < 1 && !(sRequestType.equalsIgnoreCase("IFA_CTP")||sRequestType.equalsIgnoreCase("STL"))){//ATP-157 Krishna 11/12/23
							sendMessageHashMap(sButtonName,  "Please click on Add button of "+sTabName);
							return false;
						}
						else
							return true; 			
					}
					else
						return true;
				}
			}

		} catch (HeadlessException e) {
			log.error("Exception: ",e);
		}


		return true;
	}


	public boolean txtVettTabVerification() {
		boolean sres=false;
		if("GRNT".equalsIgnoreCase(requestCategory) || "SBLC".equalsIgnoreCase(requestCategory)){//added by mansi
			sres=txtVettTabVerificationGTEE();
		} else{
			sres = true;
		}
		return sres;

	}

	public boolean txtVettTabVerificationGTEE(){
		if (validateMandatoryFieldsPPM("TXT_FORMAT_PPM", "Please Select Text Format at Text Vetting")
				&& validateMandatoryFieldsPPM(TXT_REQ_APP_PPM, "Please select Text Requires Approval at Text Vetting")
				&& validateReferToTxtVett(TXT_REQ_APP_PPM, "add_lvwTxtVet", "Please select ReferTo at Txt Vetting", "TFO_DJ_REF_TXT_VET")
				&& checkRemarksManOnGridTxtVett("TFO_DJ_REF_TXT_VET","add_lvwTxtVet", TXT_REQ_APP_PPM)
				&& txtVettcheckGridStatus("TFO_DJ_REF_TXT_VET","add_lvwTxtVet", TXT_REQ_APP_PPM)) {

			return true;
		} else {
			return false;
		}

	}
	public boolean txtVettcheckGridStatus(String tableName,String buttonId, String lovControl1) {

		String strQueryOthers = "Select count(1) CNT from " + tableName + " where WI_NAME ='" + this.sWorkitemID + "'";
		log.info(" Final strQueryOthers : " + strQueryOthers);
		if (!"1".equalsIgnoreCase(formObject.getValue(lovControl1).toString())) {
			int chksNo = getSerialNumber(strQueryOthers);
			log.info(" Final chksNo : " + chksNo);
			if (chksNo > 1) {
				sendMessageHashMap(buttonId, "Grid is not empty, kindly check");
				return false;
			} else {
				return true;
			}
		}
		return true;
	}

	public boolean checkRemarksManOnGridTxtVett(String tableName,String buttonId, String lovControl1) {

		String strQueryOthers = "Select count(1) CNT from " + tableName + " where WI_NAME ='" + this.sWorkitemID + "'";
		log.info(" Final strQueryOthers : " + strQueryOthers);
		log.info(" Final lovControl1 : " + formObject.getValue(lovControl1));

		if ("1".equalsIgnoreCase(formObject.getValue(lovControl1).toString())) {
			int chksNo = getSerialNumber(strQueryOthers);
			log.info(" Final chksNo2 on rmk : " + chksNo);
			if (chksNo == 1) {
				sendMessageHashMap("", "Grid is empty, kindly add some remarks");
				return false;
			} else {
				return true;
			}
		}
		return true;
	}
	public boolean validateReferToTxtVett(String checkControl, String referTo, String alertMessage, String valTableName) {
		log.info("referTo: " + referTo + " " + checkControl + " checkControl: " + formObject.getValue(checkControl));
		if ("1".equalsIgnoreCase(formObject.getValue(checkControl).toString()) || "Yes".equalsIgnoreCase(formObject.getValue(checkControl).toString())) {
			String strQuerySing = "Select count(1) CNT from " + valTableName + " where WI_NAME ='" + this.sWorkitemID + "'";
			log.info(" Final strQuerySing : " + strQuerySing);
			int chksNo = getSerialNumber(strQuerySing);
			log.info(" Final chksNo : " + chksNo);
			if (chksNo == 1) {
				return validateMandatoryFieldsPPM(referTo, alertMessage);
			} else {
				return true;
			}

		}
		return true;

	}
	public boolean lmtCreTabVerification() {
		String processType=formObject.getValue(PROCESS_TYPE).toString();
		log.info("signatureAccBalTabVerification: " + processType);
        if(!processType.equalsIgnoreCase("TSLM Front End")){
    		if (validateMandatoryFieldsPPM(LMTCRE_APP_AVL_PPM, "Please Select Limit and Credit Approval Available")
    				&& validateReferTo(LMTCRE_APP_AVL_PPM, "add_LIMIT_REFERRAL_ID", "Please select ReferTo at Limit Credit Review", "TFO_DJ_TSLM_LIMIT_CREDIT_REVIEW")
    				//&& checkRemarksManOnGrid("TFO_DJ_REF_LMT_CRDT", LMTCRE_APP_AVL_PPM, "LMTCRE_EXC_RMKS",ADD_LMY_CRDT_RVW)
    				//&& checkGridStatus("TFO_DJ_REF_LMT_CRDT", LMTCRE_APP_AVL_PPM,ADD_LMY_CRDT_RVW)
    				) {
    			enableDisableMultipleBtnControl(LIST_VIEW_BUTTONS2,true);
    			enableDisableMultipleBtnControl(BUTTON_NEXT,false);
    			return true;
    		} else {
    			return false;
		}}else{
			return true;
		}

	}

	public boolean sigCheckGridStatus(String lovControl1, String lovControl2,String buttonName) {

		String strQuerySing = "Select count(1) CNT from TFO_DJ_REF_SIG_ACC where WI_NAME ='" + this.sWorkitemID + "'";
		log.info(" Final strQuerySing : " + strQuerySing);

		if (!"2".equalsIgnoreCase(formObject.getValue(lovControl1).toString()) && !"2".equalsIgnoreCase(formObject.getValue(lovControl2).toString())) {
			int chksNo = getSerialNumber(strQuerySing);
			log.info(" Final chksNo : " + chksNo);
			if (chksNo > 1) {
				sendMessageHashMap(buttonName, "Grid is not empty, kindly check");
				return false;
			} else {
				return true;
			}
		}
		return true;
	}
	public boolean ppmAssociatedControlCheck(String param1, String param2, String checkControl, String msg) {

		log.info("param1  >> " + param1 + " param2 " + param2 + "  checkControl " + checkControl + "  msg " + checkControl);
		if ("GRNT".equalsIgnoreCase(param1) && "FD".equalsIgnoreCase(formObject.getValue(param2).toString())) {
			if (validateMandatoryFieldsPPM(checkControl, msg)) {
				return true;
			} else {
				return false;
			}
		}
		else if ("NI".equalsIgnoreCase(param1) && "FD".equalsIgnoreCase(formObject.getValue(param2).toString())) {
			if (validateMandatoryFieldsPPM(checkControl, msg)) {
				return true;
			} else {
				return false; 
			}
		} else if ("NI".equalsIgnoreCase(param1) && ("OE".equalsIgnoreCase(formObject.getValue(param2).toString())||
				"COND".equalsIgnoreCase(formObject.getValue(param2).toString()))) {	//RR for conditional change
			return true;
		} else if (!(("NI".equalsIgnoreCase(param1)) || ("AM".equalsIgnoreCase(param1))) && "FD".equalsIgnoreCase(formObject.getValue(param2).toString())) {
			if (validateMandatoryFieldsPPM(checkControl, msg)) {
				return true;
			} else {
				return false;
			}
		} else if (!(("NI".equalsIgnoreCase(param1)) || ("AM".equalsIgnoreCase(param1))) && ("OE".equalsIgnoreCase(formObject.getValue(param2).toString())
				||"COND".equalsIgnoreCase(formObject.getValue(param2).toString()))) { //RR for conditional change
			return true;
		} else if (!"S".equalsIgnoreCase(param1)
				&& !("No".equalsIgnoreCase(param2) || "2".equalsIgnoreCase(param2))) {
			if (validateMandatoryFieldsPPM(checkControl, msg)) {
				return true;
			} else {
				return false;
			}
		} else if (!"S".equalsIgnoreCase(param1) && ("Yes".equalsIgnoreCase(param2) || "1".equalsIgnoreCase(param2))) {
			return validateMandatoryFieldsPPM(checkControl, msg);
		}
		return true;
	}

	public boolean validateMandatoryFieldsPPM(String sFieldName, String alertMsg) {
		try {
			log.info("Validation Conrol Name :"+sFieldName);
			log.info("Validation Conrol Value :"+this.formObject.getValue(sFieldName).toString());
			String fieldValue = normalizeString(this.formObject.getValue(sFieldName).toString());
			log.info("fieldValue --validateMandatoryFieldsPPM  " + fieldValue);
			if (!(isEmpty(fieldValue)) || "".equalsIgnoreCase(fieldValue) || emptyStr.equalsIgnoreCase(fieldValue)) {
				log.info("validateMandatoryFieldsPPM blank");
				sendMessageHashMap(sFieldName, alertMsg);
				return false;
			} else {
				return true;
			}

		} catch (Exception e) {
			log.error("Exception: ",e);
			return false;
		}

	}
	//added by santhosh
	private boolean validateMandatoryExpiryCond()  
	{
		if("BAU".equalsIgnoreCase(normalizeString(formObject.getValue(PROCESS_TYPE).toString())) &&
				"COND".equalsIgnoreCase(normalizeString(formObject.getValue(TRN_TENOR).toString())))
		{
			return validateMandatoryFieldsPPM(EXPIRY_COND, "Please Enter Expiry Conditions.");
		}
		return true;
	}

	protected boolean ppmValidateInputGTEE(String reqTpe){

		enableDisableMultipleBtnControl(LIST_VIEW_BUTTONS3,true);
		enableDisableMultipleBtnControl(BUTTON_SUBMIT,false); 
		if ("NI".equalsIgnoreCase(reqTpe)) {
			return validateMandatoryFieldsPPM(PRODUCT_TYPE, "Please Select Product Code.")
					&& validateMandatoryFieldsPPM(TRN_TENOR, "Please Select Expiry Type.")
					&& ppmAssociatedControlCheck(reqTpe, TRN_TENOR, EXP_DATE, "Please Enter Expiry Date.")
					&& validateMandatoryFieldsPPM(ACCOUNT_VALID, "Please Select Valid Account No.")
					&& validateMandatoryFieldsPPM(TRN_THIRD_PARTY, "Please Select Third Party Transaction.")
					&& ppmAssociatedControlCheck(formObject.getValue(SOURCE_CHANNEL).toString(),formObject.getValue(ACCOUNT_VALID).toString(), ACCOUNT_NUMBER, "Please Fetch Account Number.")
					&& validatAcChrgTitle(formObject.getValue(ACCOUNT_VALID).toString())
					&& ppmAssociatedControlCheck("GRNT", TRN_TENOR, FIELD_GRNTEE_EXP_DATE, "Please Select Counter GTE Expiry Date.")
					&& validateMandatoryNewExpiryTXNAmt()
					&& validateMandatoryExpiryCond()
					&& validateMandatoryFieldsPPM(PURPOSE_OF_MESSAGE, "Please Select Purpose of Message.");

		} 
		else if ("AM".equalsIgnoreCase(reqTpe)) { 
			return validateMandatoryFieldsPPM(PRODUCT_TYPE, "Please Select Product Code.")
					//&& validateMandatoryFieldsPPM(TRN_TENOR, "Please Select Expiry Type.")
					&& validateMandatoryFieldsPPM(AMEND_TYPE, "Please Select Amendment Type.")
					&& validateMandatoryFieldsPPM(ACCOUNT_VALID, "Please Select Valid Account No.")
					&& validateMandatoryFieldsPPM(TRN_THIRD_PARTY, "Please Select Third Party Transaction.")
					&& ppmAssociatedControlCheck(formObject.getValue(SOURCE_CHANNEL).toString(),formObject.getValue(ACCOUNT_VALID).toString(), ACCOUNT_NUMBER, "Please Fetch Account Number.")
					&& validatAcChrgTitle(formObject.getValue(ACCOUNT_VALID).toString())
					&& validateMandatoryExpiryCond_AM();
			//&& ppmNonAssociatedControlCheck(reqTpe, formObject.getValue(AMEND_TYPE).toString(),NEW_TRN_AMT, "Please Enter New Transaction Amount.")
			//&& ppmNonAssociatedControlCheck(reqTpe, formObject.getValue(AMEND_TYPE).toString(),NEW_EXP_DATE, "Please Select New Expiry Date.")
			//&& ppmAssociatedControlCheck("GRNT", TRN_TENOR, FIELD_GRNTEE_EXP_DATE, "Please Select Counter GTE Expiry Date.")
			//&& validateMandatoryNewExpiryTXNAmt();

		}
		else if ("CC".equalsIgnoreCase(reqTpe)) {	//RR
			return validateMandatoryFieldsPPM(PRODUCT_TYPE, "Please Select Product Code.")
					//&& validateMandatoryFieldsPPM(TRN_TENOR, "Please Select Expiry Type.")
					//&& validateMandatoryFieldsPPM(ACCOUNT_VALID, "Please Select Valid Account No.")
					//&& validateMandatoryFieldsPPM(TRN_THIRD_PARTY, "Please Select Third Party Transaction.")
					//&& ppmAssociatedControlCheck(formObject.getValue(SOURCE_CHANNEL).toString(),formObject.getValue(ACCOUNT_VALID).toString(), ACCOUNT_NUMBER, "Please Fetch Account Number.")
					&& validatAcChrgTitle(formObject.getValue(ACCOUNT_VALID).toString())
					//&& ppmAssociatedControlCheck("GRNT", TRN_TENOR, FIELD_GRNTEE_EXP_DATE, "Please Select Counter GTE Expiry Date.")
					&& validateMandatoryNewExpiryTXNAmt();
		}
		else if("GA".equalsIgnoreCase(reqTpe)) {//santhosh
			return validateMandatoryExpiryCond();

		} 
		else if("GAA".equalsIgnoreCase(reqTpe)) {//santhosh
			return validateMandatoryExpiryCond_AM();

		} 
		else if ("CL".equalsIgnoreCase(reqTpe)||"ER".equalsIgnoreCase(reqTpe)
				|| "EPC".equalsIgnoreCase(reqTpe)) {	//RR
			return validateMandatoryFieldsPPM(PRODUCT_TYPE, "Please Select Product Code.")
					//&& validateMandatoryFieldsPPM(TRN_TENOR, "Please Select Expiry Type.")
					//&& validateMandatoryFieldsPPM(ACCOUNT_VALID, "Please Select Valid Account No.")
					//&& validateMandatoryFieldsPPM(TRN_THIRD_PARTY, "Please Select Third Party Transaction.")
					//&& ppmAssociatedControlCheck(formObject.getValue(SOURCE_CHANNEL).toString(),formObject.getValue(ACCOUNT_VALID).toString(), ACCOUNT_NUMBER, "Please Fetch Account Number.")
					&& validatAcChrgTitle(formObject.getValue(ACCOUNT_VALID).toString())
					//&& ppmAssociatedControlCheck("GRNT", TRN_TENOR, FIELD_GRNTEE_EXP_DATE, "Please Select Counter GTE Expiry Date.")
					&& validateMandatoryNewExpiryTXNAmt();
		}
		else if (!(("AM".equalsIgnoreCase(reqTpe)) || ("NI".equalsIgnoreCase(reqTpe)))) {
			return validateMandatoryFieldsPPM(PRODUCT_TYPE, "Please Select Product Code.")
					&& validateMandatoryFieldsPPM(TRN_TENOR, "Please Select Expiry Type.")
					&& validateMandatoryFieldsPPM(ACCOUNT_VALID, "Please Select Valid Account No.")
					&& validateMandatoryFieldsPPM(TRN_THIRD_PARTY, "Please Select Third Party Transaction.")
					&& ppmAssociatedControlCheck(formObject.getValue(SOURCE_CHANNEL).toString(),formObject.getValue(ACCOUNT_VALID).toString(), ACCOUNT_NUMBER, "Please Fetch Account Number.")
					&& validatAcChrgTitle(formObject.getValue(ACCOUNT_VALID).toString())
					&& ppmAssociatedControlCheck("GRNT", TRN_TENOR, FIELD_GRNTEE_EXP_DATE, "Please Select Counter GTE Expiry Date.")
					&& validateMandatoryNewExpiryTXNAmt();
		}
		return true;

	}
	protected boolean infInputFrameValidate(){

		log.info("INSIDE : infInputFrameValidate");
		log.info("INSIDE : STANDALONE_LOAN :: "+ formObject.getValue(STANDALONE_LOAN).toString());
		log.info("INSIDE : PROCESSING_SYSTEM :: "+formObject.getValue(PROCESSING_SYSTEM).toString());


		String requestCat = formObject.getValue(REQUEST_CATEGORY).toString();
		enableDisableMultipleBtnControl(LIST_VIEW_BUTTONS3,true);
		enableDisableMultipleBtnControl(BUTTON_SUBMIT,false); 

		String sCustHoldingAccWithUs="";
		sCustHoldingAccWithUs = formObject.getValue(BILL_CUST_HLDING_ACC_US).toString();
		String reqTypeFlag = ""; //ADDED BY SHIVANHU 25-09-23
				if("IFA_SD".equalsIgnoreCase(requestType) || "IFS_SD".equalsIgnoreCase(requestType) 
						|| "IFP_SD".equalsIgnoreCase(requestType) || "AM".equalsIgnoreCase(requestType)
						|| "STL".equalsIgnoreCase(requestType)
						|| "IFA_CTP".equalsIgnoreCase(requestType)
						|| "PD".equalsIgnoreCase(requestType) || "PDD".equalsIgnoreCase(requestType)){    //ATP - 203
					reqTypeFlag = "NA";
				}
				log.info(" reqTypeFlag " + reqTypeFlag);
		if(!validateMandatoryFieldsPPM(PRODUCT_TYPE, "Please select product type"))
			return false;
		else if(!validateMandatoryFieldsPPM(INF_LOAN_CURR, "Please Select loan currency"))
			return false;
		else if(("AM".equalsIgnoreCase(requestType) || "SBLC_AM".equalsIgnoreCase(requestType) 
				|| "ELC_SLCAA".equalsIgnoreCase(requestType)) && !validateMandatoryFieldsPPM(INF_AMEND_TYPE, "Please select Amendment type")){
			return false;
		}
		else if(("MDC".equalsIgnoreCase(formObject.getValue(INF_AMEND_TYPE).toString())
				||"APTP".equalsIgnoreCase(formObject.getValue(INF_AMEND_TYPE).toString())) 
				&& !validateMandatoryFieldsPPM(INF_NEW_MATURITY_DATE, "Please select new maturity Date")){
			return false;
		}
		else if(!"NA".equalsIgnoreCase(reqTypeFlag) && !validateNumberFieldswithZero(INF_TENOR_DAYS, "Tenor days can't be left blank"))
			return false;
		else if(!"NA".equalsIgnoreCase(reqTypeFlag) && !validateMandatoryFieldsPPM(INF_TENOR_BASE, "Please select tenor base"))
			return false;
		else if(!"NA".equalsIgnoreCase(reqTypeFlag) && !validateMandatoryFieldsPPM(INF_BASE_DOC_DATE, "Please enter base document date"))
			return false;
		else if("OTH".equalsIgnoreCase(formObject.getValue(INF_TENOR_BASE).toString()) 
				&& !validateMandatoryFieldsPPM(INF_ACTUAL_TENOR_BASE, "Please enter actual tenor base details")){
			return false;
		}
		else if(!validateMandatoryFieldsPPM(INF_MATURITY_DATE, "Maturity date can't be left blank"))
			return false;

		else if(!validateMandatoryFieldsPPM(BILL_CUST_HLDING_ACC_US, "Please select customer Account Holding Status"))
			return false;

		//ATP -492 REYAZ 25-07-2024 START
		else if("1".equalsIgnoreCase(sCustHoldingAccWithUs) && validateStlAmTslmFronted()
				&& !validateMandatoryFieldsPPM(INF_CHARGE_ACC_NUM, "Please select  principal account number"))
			return false;
		else if("1".equalsIgnoreCase(sCustHoldingAccWithUs) && validateStlAmTslmFronted()
				&& !validateMandatoryFieldsPPM(INF_CHARGE_ACC_TITLE, "Principal Account Title cannot be left blank"))
			return false;
		else if("1".equalsIgnoreCase(sCustHoldingAccWithUs) && validateStlAmTslmFronted()
				&& !validateMandatoryFieldsPPM(INF_CHARGE_ACC_CURR, "Principal currency cannot be left blank"))
			return false;

		else if("1".equalsIgnoreCase(sCustHoldingAccWithUs) && validateStlAmTslmFronted()
				&& !validateMandatoryFieldsPPM(INF_SETTLEMENT_ACC_NUM, "Please select Interest/Profit account number"))
			return false;
		else if("1".equalsIgnoreCase(sCustHoldingAccWithUs) && validateStlAmTslmFronted()
				&& !validateMandatoryFieldsPPM(INF_SETTLEMENT_ACC_TITLE, "Interest/Profit Account Title cannot be left blank"))
			return false;
		else if("1".equalsIgnoreCase(sCustHoldingAccWithUs) && validateStlAmTslmFronted()
				&& !validateMandatoryFieldsPPM(INF_SETTLEMENT_ACC_CURR, "Interest/Profit currency cannot be left blank"))
			return false;
		//ATP -492 REYAZ 25-07-2024 END 

		else if("1".equalsIgnoreCase(sCustHoldingAccWithUs) 
				&& !validateMandatoryFieldsPPM(INF_CHARGE_ACC_NUM, "Please select charge account number"))
			return false;
		else if("1".equalsIgnoreCase(sCustHoldingAccWithUs) 
				&& !validateMandatoryFieldsPPM(INF_CHARGE_ACC_TITLE, "Charge Account Title cannot be left blank"))
			return false;
		else if("1".equalsIgnoreCase(sCustHoldingAccWithUs) 
				&& !validateMandatoryFieldsPPM(INF_CHARGE_ACC_CURR, "Charge currency cannot be left blank"))
			return false;



		else if("1".equalsIgnoreCase(sCustHoldingAccWithUs) 
				&& !validateMandatoryFieldsPPM(INF_SETTLEMENT_ACC_NUM, "Please select settlement account number"))
			return false;
		else if("1".equalsIgnoreCase(sCustHoldingAccWithUs)
				&& !validateMandatoryFieldsPPM(INF_SETTLEMENT_ACC_TITLE, "Settlement Account Title cannot be left blank"))
			return false;
		else if("1".equalsIgnoreCase(sCustHoldingAccWithUs)
				&& !validateMandatoryFieldsPPM(INF_SETTLEMENT_ACC_CURR, "Settlement currency cannot be left blank"))
			return false;

		else if("1".equalsIgnoreCase(sCustHoldingAccWithUs) && "IFS".equalsIgnoreCase(requestCategory)
				&& "LD".equalsIgnoreCase(requestType) 
				&& !validateMandatoryFieldsPPM(INF_CREDIT_ACC_NUM, "Please select credit account number"))
			return false;
		else if("1".equalsIgnoreCase(sCustHoldingAccWithUs) && "IFS".equalsIgnoreCase(requestCategory)
				&& "LD".equalsIgnoreCase(requestType) 
				&& !validateMandatoryFieldsPPM(INF_CREDIT_ACC_TITLE, "Credit Account Title cannot be left blank"))
			return false;
		else if("1".equalsIgnoreCase(sCustHoldingAccWithUs) && "IFS".equalsIgnoreCase(requestCategory)
				&& "LD".equalsIgnoreCase(requestType) 
				&& !validateMandatoryFieldsPPM(INF_CREDIT_ACC_CURR, "Credit Account currency cannot be left blank"))
			return false;

		else if(!("PD".equalsIgnoreCase(requestType) || "PDD".equalsIgnoreCase(requestType))  //ATP -203
				&& !validateMandatoryFieldsPPM("REMITTANCE_CURR", "Please select Remittance Currency"))
			return false;
		else if(!("PD".equalsIgnoreCase(requestType) || "PDD".equalsIgnoreCase(requestType)) //ATP -203
				&& !validateMandatoryFieldsPPM("REMITTANCE_AMT", "Please enter Remittance Amount"))
			return false;
		else if((requestCat.equalsIgnoreCase("IFA") ||requestCat.equalsIgnoreCase("IFS")||requestCat.equalsIgnoreCase("IFP") ||requestCat.equalsIgnoreCase("SCF"))
				&& !validateMandatoryFieldsPPM(STANDALONE_LOAN, "Please select Standalone Loan")){
			log.info("Please select Standalone Loan :");//BY KISHAN TSLMS
			return false;
		}
		//CODE BY MOKSH
		else if("IFA".equalsIgnoreCase(requestCategory) && "IFA_CTP".equalsIgnoreCase(requestType)
				&& formObject.getValue(PROCESSING_SYSTEM).toString().equalsIgnoreCase("T") 
				&& !validateMandatoryFieldsPPM(TSLM_IF_PURCHASE_PRODUCT_CODE, "Please select IF Purchase Product Code"))
			return false;
		else if("IFA".equalsIgnoreCase(requestCategory) && "IFA_CTP".equalsIgnoreCase(requestType)
				&& formObject.getValue(PROCESSING_SYSTEM).toString().equalsIgnoreCase("T") 
				&& !validateMandatoryFieldsPPM(TSLM_FUNDING_REQUIRED, "Please select Funding Required"))
			return false;
		else if("IFA".equalsIgnoreCase(requestCategory) && "IFA_CTP".equalsIgnoreCase(requestType)&&
				formObject.getValue(TSLM_FUNDING_REQUIRED).toString().equalsIgnoreCase("1")
				&& formObject.getValue(PROCESSING_SYSTEM).toString().equalsIgnoreCase("T") 
				&& !validateMandatoryFieldsPPM(TSLM_ADDITIONAL_LOAN_AMOUNT, "Please enter Additional Loan Amount"))
			return false;
		/*else if((requestCat.equalsIgnoreCase("IFA") ||requestCat.equalsIgnoreCase("IFS")||requestCat.equalsIgnoreCase("IFP"))
				&& !validateMandatoryFieldsPPM(PROCESSING_SYSTEM, "Please select Processing System")){
			return false;
		}*/
		//END OF CODE BY MOKSH
		log.info("END  : infInputFrameValidate");
		return true;
	}
	protected boolean validateNumberFieldswithZero(String sFieldName, String alertMsg){
		try {
			log.info("Validation Conrol for Numbers :"+sFieldName);
			String fieldValue = "";
			fieldValue = this.formObject.getValue(sFieldName).toString();
			log.info("Value  "+fieldValue);
			if(fieldValue.isEmpty()){
				sendMessageHashMap(sFieldName, alertMsg);
				return false;
			}
			else 
				return true;

		} catch (Exception e) {
			log.error("Exception: ",e);
			return false;
		}

	}

	private boolean validatAcChrgTitle(String isAccValid){

		try {
			log.info("**INSIDE validatAcChrgTitle**");
			if("1".equalsIgnoreCase(isAccValid) && (!("CC".equalsIgnoreCase(requestType)||"CL".equalsIgnoreCase(requestType)
					||"ER".equalsIgnoreCase(requestType)||"EPC".equalsIgnoreCase(requestType)||"SBLC_ER".equalsIgnoreCase(requestType)
					||"SBLC_CR".equalsIgnoreCase(requestType)||"SBLC_CS".equalsIgnoreCase(requestType)
					||"ELC_SER".equalsIgnoreCase(requestType)||"ELC_SCL".equalsIgnoreCase(requestType)))){	//RR
				return (validateMandatoryFieldsPPM("GRNT_CHRG_ACC_TITLE", "Charge Account Title cannot be left blank.") 
						||  validateMandatoryFieldsPPM("GRNT_CHRG_ACC_CRNCY", "Charge currency cannot be left blank.")
						);
			}else if("2".equalsIgnoreCase(isAccValid)){
				return true;
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		return true;		
	}

	private boolean validateMandatoryNewExpiryTXNAmt()  
	{
		String amendValue=formObject.getValue(AMEND_TYPE).toString();
		log.info("in validateMandatoryNewExpiryTXNAmt="+amendValue);

		if(("CT".equalsIgnoreCase(amendValue))||("OFT".equalsIgnoreCase(amendValue))   
				||("ILC_CT".equalsIgnoreCase(amendValue)) 
				||("ELC_CT".equalsIgnoreCase(amendValue))){
			return validateMandatoryFieldsPPM(NEW_TRN_AMT, "Please Select New Transaction Amt")
					&& validateMandatoryFieldsPPM(NEW_EXP_DATE, "Please Select New Expiry Date");
		}else if("FOT".equalsIgnoreCase(amendValue)){  
			return validateMandatoryFieldsPPM(NEW_TRN_AMT, "Please Select New Transaction Amt");
		}
		return true;
	}
	private boolean ppmNonAssociatedControlCheck(String param1, String param2, String checkControl, String msg) {
		log.info(" ppmNonAssociatedControlCheck param1  >> " + param1 + " param2 " + param2 + "  checkControl " + checkControl + "  msg " + msg);
		if ("AM".equalsIgnoreCase(param1)|| "SBLC_AM".equalsIgnoreCase(param1)) {//added by mansi
			if ((("EE".equalsIgnoreCase(param2)) || ("CE".equalsIgnoreCase(param2)) || ("OF".equalsIgnoreCase(param2)))
					&& NEW_EXP_DATE.equalsIgnoreCase(checkControl)) {
				formObject.setValue(NEW_TRN_AMT, "");

				if (validateMandatoryFieldsPPM(checkControl, msg)) {
					return true;
				} else {
					return false;
				}
			} else if (NEW_TRN_AMT.equalsIgnoreCase(checkControl)
					&& ("IV".equalsIgnoreCase(param2) || "DV".equalsIgnoreCase(param2))) {
				formObject.setValue(NEW_EXP_DATE, "");
				if (validateMandatoryFieldsPPM(checkControl, msg)) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			formObject.setValue(NEW_TRN_AMT, "");
			formObject.setValue(NEW_EXP_DATE, "");
		}
		return true;
	}
	private boolean elcPpmValidateInput(String reqTpe){

		enableDisableMultipleBtnControl(LIST_VIEW_BUTTONS,true);
		enableDisableMultipleBtnControl(BUTTON_SUBMIT,false); 
		if ("ELC_LCA".equalsIgnoreCase(reqTpe)) {
			if (validateMandatoryFieldsPPM(PRODUCT_TYPE, "Please Select Product Code.")					
					&& validateMandatoryFieldsPPM(EXP_DATE, "Please Enter Expiry Date.")
					&& validateMandatoryFieldsPPM(ACCOUNT_VALID, "Please Select Valid Account No.")
					&& ielcPpmAssociatedControlCheck(ACCOUNT_VALID, ACCOUNT_NUMBER, "Please Fetch Account Number.")
					&& validatAcChrgTitle(formObject.getValue(ACCOUNT_VALID).toString())
					&& validateMandatoryFieldsPPM(LC_DOC_COURIER, "Please Select Documents to be couriered.")					
					&& validateMandatoryNewExpiryTXNAmt()) {
				return true;
			} else {
				return false;
			}
		} else if ("ELC_SLCA".equalsIgnoreCase(reqTpe)) {//added by mansi
			if(validateMandatoryFieldsPPM(PRODUCT_TYPE, "Please Select Product Code.")
					&& validateMandatoryFieldsPPM(TRN_TENOR, "Please Select Expiry Type.")
					&& ppmAssociatedControlCheck_ELC(reqTpe, TRN_TENOR, EXP_DATE, "Please Enter Expiry Date.")
					&& validateMandatoryFieldsPPM(ACCOUNT_VALID, "Please Select Valid Account No.")
					&& validateMandatoryFieldsPPM(TRN_THIRD_PARTY, "Please Select Third Party Transaction.")
					&& ppmAssociatedControlCheck_ELC(formObject.getValue(SOURCE_CHANNEL).toString(),formObject.getValue(ACCOUNT_VALID).toString(), ACCOUNT_NUMBER, "Please Fetch Account Number.")
					&& validatAcChrgTitle(formObject.getValue(ACCOUNT_VALID).toString())
					&& ppmAssociatedControlCheck_ELC("ELC", TRN_TENOR, FIELD_GRNTEE_EXP_DATE, "Please Select Counter GTE Expiry Date.")
					&& validateMandatoryNewExpiryTXNAmt()
					&& validateMandatoryExpiryCond()){
				return true;
			} else {
				return false;
			}

		} else if ("ELC_SLCAA".equalsIgnoreCase(reqTpe)) { //added by mansi
			if( validateMandatoryFieldsPPM(PRODUCT_TYPE, "Please Select Product Code.")
					//&& validateMandatoryFieldsPPM(TRN_TENOR, "Please Select Expiry Type.")
					&& validateMandatoryFieldsPPM(AMEND_TYPE, "Please Select Amendment Type.")
					&& validateMandatoryFieldsPPM(ACCOUNT_VALID, "Please Select Valid Account No.")
					&& validateMandatoryFieldsPPM(TRN_THIRD_PARTY, "Please Select Third Party Transaction.")
					&& ppmAssociatedControlCheck_ELC(formObject.getValue(SOURCE_CHANNEL).toString(),formObject.getValue(ACCOUNT_VALID).toString(), ACCOUNT_NUMBER, "Please Fetch Account Number.")
					&& validatAcChrgTitle(formObject.getValue(ACCOUNT_VALID).toString())
					&& validateMandatoryExpiryCond_AM())
				//&& ppmNonAssociatedControlCheck(reqTpe, formObject.getValue(AMEND_TYPE).toString(),NEW_TRN_AMT, "Please Enter New Transaction Amount.")
				//&& ppmNonAssociatedControlCheck(reqTpe, formObject.getValue(AMEND_TYPE).toString(),NEW_EXP_DATE, "Please Select New Expiry Date.")
				//&& ppmAssociatedControlCheck_ELC("ELC", TRN_TENOR, FIELD_GRNTEE_EXP_DATE, "Please Select Counter GTE Expiry Date.")
				//&& validateMandatoryNewExpiryTXNAmt())
			{
				return true;
			} else {
				return false;
			}

		} 
		/*else if (!(("ELC_SLCAA".equalsIgnoreCase(reqTpe)) || ("ELC_SLCA".equalsIgnoreCase(reqTpe)))) {//added by mnasi
	if( validateMandatoryFieldsPPM(PRODUCT_TYPE, "Please Select Product Code.")
			&& validateMandatoryFieldsPPM(TRN_TENOR, "Please Select Expiry Type.")
			&& validateMandatoryFieldsPPM(ACCOUNT_VALID, "Please Select Valid Account No.")
			&& validateMandatoryFieldsPPM(TRN_THIRD_PARTY, "Please Select Third Party Transaction.")
			&& ppmAssociatedControlCheck_ELC(formObject.getValue(SOURCE_CHANNEL).toString(),formObject.getValue(ACCOUNT_VALID).toString(), ACCOUNT_NUMBER, "Please Fetch Account Number.")
			&& validatAcChrgTitle(formObject.getValue(ACCOUNT_VALID).toString())
			&& ppmAssociatedControlCheck_ELC("ELC", TRN_TENOR, FIELD_GRNTEE_EXP_DATE, "Please Select Counter GTE Expiry Date.")
			&& validateMandatoryNewExpiryTXNAmt()){
				return true;
			} else {
				return false;
			}


}*/else if (("ELC_LCAA".equalsIgnoreCase(reqTpe))) {
	if (validateMandatoryFieldsPPM(PRODUCT_TYPE, "Please Select Product Code.")
			&& validateMandatoryFieldsPPM(AMEND_TYPE, "Please Select Amendment Type.")
			&& validateMandatoryFieldsPPM(ACCOUNT_VALID, "Please Select Valid Account No.")
			&& ielcPpmAssociatedControlCheck(ACCOUNT_VALID, ACCOUNT_NUMBER, "Please Fetch Account Number.")
			&& validatAcChrgTitle(formObject.getValue(ACCOUNT_VALID).toString())
			//&& elcPpmNonAssociatedControlCheck(reqTpe, formObject.getValue(AMEND_TYPE).toString(),NEW_TRN_AMT, "Please Enter New Transaction Amount.") //PT280
			//&& elcPpmNonAssociatedControlCheck(reqTpe, formObject.getValue(AMEND_TYPE).toString(),NEW_EXP_DATE, "Please Select New Expiry Date.")
			&& validateMandatoryFieldsPPM(LC_DOC_COURIER, "Please Select Documents to be couriered."))
		//&& validateMandatoryNewExpiryTXNAmt())      PT 280 
	{
		return true;
	} else {
		return false;
	}
}else if ("ELC_LCC".equalsIgnoreCase(reqTpe)) {
	if (validateMandatoryFieldsPPM(PRODUCT_TYPE, "Please Select Product Code.")
			&& validateMandatoryFieldsPPM(ACCOUNT_VALID, "Please Select Valid Account No.")
			&& ielcPpmAssociatedControlCheck(ACCOUNT_VALID, ACCOUNT_NUMBER, "Please Fetch Account Number.")	
			&& validatAcChrgTitle(formObject.getValue(ACCOUNT_VALID).toString())
			&& validateMandatoryFieldsPPM(LC_DOC_COURIER, "Please Select Documents to be couriered.")
			&& validateMandatoryNewExpiryTXNAmt()) {  
		return true;
	} else {
		return false;
	}
}else if ("ELC_SER".equalsIgnoreCase(reqTpe)||"ELC_SCL".equalsIgnoreCase(reqTpe)) {	//RR
	return validateMandatoryFieldsPPM(PRODUCT_TYPE, "Please Select Product Code.")
			//&& validateMandatoryFieldsPPM(TRN_TENOR, "Please Select Expiry Type.")
			//&& validateMandatoryFieldsPPM(ACCOUNT_VALID, "Please Select Valid Account No.")
			//&& validateMandatoryFieldsPPM(TRN_THIRD_PARTY, "Please Select Third Party Transaction.")
			//&& ppmAssociatedControlCheck(formObject.getValue(SOURCE_CHANNEL).toString(),formObject.getValue(ACCOUNT_VALID).toString(), ACCOUNT_NUMBER, "Please Fetch Account Number.")
			&& validatAcChrgTitle(formObject.getValue(ACCOUNT_VALID).toString())
			//&& ppmAssociatedControlCheck("GRNT", TRN_TENOR, FIELD_GRNTEE_EXP_DATE, "Please Select Counter GTE Expiry Date.")
			&& validateMandatoryNewExpiryTXNAmt();
} 
else if (!(("ELC_LCA".equalsIgnoreCase(reqTpe)) || ("ELC_LCC".equalsIgnoreCase(reqTpe)) || ("ELC_LCAA".equalsIgnoreCase(reqTpe)))) {
	if (validateMandatoryFieldsPPM(PRODUCT_TYPE, "Please Select Product Code.")					
			&& validateMandatoryFieldsPPM(ACCOUNT_VALID, "Please Select Valid Account No.")
			&& validatAcChrgTitle(formObject.getValue(ACCOUNT_VALID).toString())
			&& ielcPpmAssociatedControlCheck(ACCOUNT_VALID, ACCOUNT_NUMBER, "Please Fetch Account Number.")					
			&& validateMandatoryFieldsPPM(LC_DOC_COURIER, "Please Select Documents to be couriered.")
			&& validateMandatoryNewExpiryTXNAmt()) {  
		return true;
	} else {
		return false;
	}
}
		return true;
	}

	private boolean ielcPpmAssociatedControlCheck(String param1, String param2, String msg) {
		log.info(" ilcPpmAssociatedControlCheck  Param1  "+param1 +"  param 2" +param2 + " msg "+msg);
		if(ACCOUNT_NUMBER.equalsIgnoreCase(param2) 
				&& "1".equalsIgnoreCase(formObject.getValue(param1).toString())
				&& validateMandatoryFieldsPPM(param2, msg)){
			return true;
		}
		else if(ACCOUNT_NUMBER.equalsIgnoreCase(param2) 
				&& "2".equalsIgnoreCase(formObject.getValue(param1).toString())){
			return true;
		}		
		return false;
	}

	private boolean elcPpmNonAssociatedControlCheck(String param1, String param2, String checkControl, String msg) {
		log.info(" ppmNonAssociatedControlCheck param1  >> " + param1 + " param2 " + param2 + "  checkControl " + checkControl + "  msg " + msg);
		if ("ELC_LCAA".equalsIgnoreCase(param1)) {
			if (( ("ELC_CED".equalsIgnoreCase(param2)) || ("ELC_EED".equalsIgnoreCase(param2)))
					&& NEW_EXP_DATE.equalsIgnoreCase(checkControl)) {
				formObject.setValue(NEW_TRN_AMT, "");
				if (validateMandatoryFieldsPPM(checkControl, msg)) {
					return true;
				} else {
					return false;
				}
			} else if (NEW_TRN_AMT.equalsIgnoreCase(checkControl)
					&& ( "ELC_IV".equalsIgnoreCase(param2) || "ELC_DV".equalsIgnoreCase(param2))) {
				formObject.setValue(NEW_EXP_DATE, "");
				if (validateMandatoryFieldsPPM(checkControl, msg)) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			formObject.setValue(NEW_TRN_AMT, "");
			formObject.setValue(NEW_EXP_DATE, "");
		}
		return true;
	}
	private boolean ilcPpmValidateInput(String reqTpe){

		enableDisableMultipleBtnControl(LIST_VIEW_BUTTONS,true);
		enableDisableMultipleBtnControl(BUTTON_SUBMIT,false); 
		if ("ILC_NI".equalsIgnoreCase(reqTpe)) {
			if (validateMandatoryFieldsPPM(PRODUCT_TYPE, "Please Select Product Code.")					
					&& validateMandatoryFieldsPPM(EXP_DATE, "Please Enter Expiry Date.")
					&& validateMandatoryFieldsPPM(ACCOUNT_VALID, "Please Select Valid Account No.")
					&& validateMandatoryFieldsPPM(TRN_THIRD_PARTY, "Please Select Third Party Transaction.")
					&& ielcPpmAssociatedControlCheck(ACCOUNT_VALID, ACCOUNT_NUMBER, "Please Fetch Account Number.")
					&& validatAcChrgTitle(formObject.getValue(ACCOUNT_VALID).toString())
					&& validateMandatoryFieldsPPM(LC_DOC_COURIER, "Please Select Documents to be couriered.")					
					&& validateMandatoryNewExpiryTXNAmt()) {  
				return true; 
			} else {
				return false;
			}
		} 
		else if ("ILC_UM".equalsIgnoreCase(reqTpe)) {
			if (validateMandatoryFieldsPPM(PRODUCT_TYPE, "Please Select Product Code.")					
					//&& validateMandatoryFieldsPPM(EXP_DATE, "Please Enter Expiry Date.")
					&& validateMandatoryExpiryDate()	//RR
					&& validateMandatoryFieldsPPM(ACCOUNT_VALID, "Please Select Valid Account No.")
					&& ielcPpmAssociatedControlCheck(ACCOUNT_VALID, ACCOUNT_NUMBER, "Please Fetch Account Number.")
					&& validatAcChrgTitle(formObject.getValue(ACCOUNT_VALID).toString())
					&& validateMandatoryFieldsPPM(LC_DOC_COURIER, "Please Select Documents to be couriered.")					
					&& validateMandatoryNewExpiryTXNAmt()) { 
				return true;
			} else {
				return false;
			}
		}else if (("ILC_AM".equalsIgnoreCase(reqTpe))) {
			if (validateMandatoryFieldsPPM(PRODUCT_TYPE, "Please Select Product Code.")
					&& validateMandatoryFieldsPPM(AMEND_TYPE, "Please Select Amendment Type.")
					&& validateMandatoryFieldsPPM(ACCOUNT_VALID, "Please Select Valid Account No.")
					&& ielcPpmAssociatedControlCheck(ACCOUNT_VALID, ACCOUNT_NUMBER, "Please Fetch Account Number.")
					&& validatAcChrgTitle(formObject.getValue(ACCOUNT_VALID).toString())
					&& validateAmendExpiryTxnAmount(Q_AMENDMENT_DATA_USER_TRANSACTION_AMOUNT,"Please Enter Amendment Transaction Amount")
					&& validateAmendExpiryTxnAmount(Q_AMENDMENT_DATA_USER_EXPIRY_DATE,"Please Enter Amendment Expiry Date")
					//&& ilcPpmNonAssociatedControlCheck(reqTpe, formObject.getValue(AMEND_TYPE).toString(),NEW_TRN_AMT, "Please Enter New Transaction Amount.") US117
					//&& ilcPpmNonAssociatedControlCheck(reqTpe, formObject.getValue(AMEND_TYPE).toString(),NEW_EXP_DATE, "Please Select New Expiry Date.") US117
					&& validateMandatoryFieldsPPM(LC_DOC_COURIER, "Please Select Documents to be couriered."))
				//&& validateMandatoryNewExpiryTXNAmt()) US117
			{
				/*String amndType = formObject.getValue(AMEND_TYPE).toString() ;
				if ("ILC_IV".equalsIgnoreCase(amndType) || "ILC_DV".equalsIgnoreCase(amndType) //US115
						|| "ILC_CT".equalsIgnoreCase(amndType)) { 
					//formObject.setStyle(Q_AMENDMENT_DATA_USER_TRANSACTION_AMOUNT,DISABLE, "true"); 
					if(validateMandatoryFieldsPPM(Q_AMENDMENT_DATA_FIN_TRANSACTION_AMOUNT, "Please Enter Amendment Transaction Amount")){//US115	
					}else{
						return false;
					}
				} 
				if("ILC_IV".equalsIgnoreCase(amndType) || "ILC_EED".equalsIgnoreCase(amndType)//US115
						|| "ILC_CED".equalsIgnoreCase(amndType)){	
					//formObject.setStyle(Q_AMENDMENT_DATA_USER_EXPIRY_DATE,DISABLE, "true");  
					if(validateMandatoryFieldsPPM(Q_AMENDMENT_DATA_FIN_EXPIRY_DATE, "Please Enter Amendment Expiry Date")){//US115	
					}else{
						return false;
					}
				}*/
				return true;
			} else {
				return false;
			}

		}
		else if (!(("ILC_NI".equalsIgnoreCase(reqTpe)) || ("ILC_AM".equalsIgnoreCase(reqTpe)) || "ILC_UM".equalsIgnoreCase(reqTpe))) {
			if (validateMandatoryFieldsPPM(PRODUCT_TYPE, "Please Select Product Code.")					
					&& validateMandatoryFieldsPPM(ACCOUNT_VALID, "Please Select Valid Account No.")
					&& ielcPpmAssociatedControlCheck(ACCOUNT_VALID, ACCOUNT_NUMBER, "Please Fetch Account Number.")	
					&& validatAcChrgTitle(formObject.getValue(ACCOUNT_VALID).toString())
					&& validateMandatoryFieldsPPM(LC_DOC_COURIER, "Please Select Documents to be couriered.")
					&& validateMandatoryNewExpiryTXNAmt()) { 
				return true;
			} else {
				return false;
			}
		}
		return true;
	}
	private boolean ilcPpmNonAssociatedControlCheck(String param1, String param2, String checkControl, String msg) {
		log.info(" ppmNonAssociatedControlCheck param1  >> " + param1 + " param2 " + param2 + "  checkControl " + checkControl + "  msg " + msg);
		if ("ILC_AM".equalsIgnoreCase(param1)) {
			if (( ("ILC_CED".equalsIgnoreCase(param2)) || ("ILC_EED".equalsIgnoreCase(param2)))
					&& NEW_EXP_DATE.equalsIgnoreCase(checkControl)) {
				formObject.setValue(NEW_TRN_AMT, "");
				if (validateMandatoryFieldsPPM(checkControl, msg)) {
					return true;
				} else {
					return false;
				}
			} else if (NEW_TRN_AMT.equalsIgnoreCase(checkControl)
					&& ( "ILC_IV".equalsIgnoreCase(param2) || "ILC_DV".equalsIgnoreCase(param2))) {
				formObject.setValue(NEW_EXP_DATE, "");
				if (validateMandatoryFieldsPPM(checkControl, msg)) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			formObject.setValue(NEW_TRN_AMT, "");
			formObject.setValue(NEW_EXP_DATE, "");
		}
		return true;
	}
	private boolean tlInputFrameValidate(){
		enableDisableMultipleBtnControl(LIST_VIEW_BUTTONS3,true);
		enableDisableMultipleBtnControl(BUTTON_SUBMIT,false); 
		String sCustHoldingAccWithUs="";
		String reqTypeFlag = ""; //ADDED BY SHIVANHU 25-09-23
		if("TL_AM".equalsIgnoreCase(requestType) || "TL_SD".equalsIgnoreCase(requestType) || "TL_STL".equalsIgnoreCase(requestType)){
			reqTypeFlag = "NA";
		}
		log.info(" reqTypeFlag " + reqTypeFlag);
		sCustHoldingAccWithUs = formObject.getValue(BILL_CUST_HLDING_ACC_US).toString();
		String loan_group =  formObject.getValue(IFS_LOAN_GRP).toString();
		if(!validateMandatoryFieldsPPM(PRODUCT_TYPE, "Please select product type"))
			return false;
		else if(!validateMandatoryFieldsPPM(INF_LOAN_CURR, "Please Select loan currency"))
			return false;
		else if("TL_AM".equalsIgnoreCase(requestType) 
				&& !validateMandatoryFieldsPPM(INF_AMEND_TYPE, "Please select Amendment type")){
			return false;
		}
		else if(("MDC".equalsIgnoreCase(formObject.getValue(INF_AMEND_TYPE).toString())
				||"APTP".equalsIgnoreCase(formObject.getValue(INF_AMEND_TYPE).toString())) 
				&& !validateMandatoryFieldsPPM(INF_NEW_MATURITY_DATE, "Please select new maturity Date")){
			return false;
		}
		else if(!"NA".equalsIgnoreCase(reqTypeFlag)&& !validateNumberFieldswithZero(INF_TENOR_DAYS, "Tenor days can't be left blank"))
			return false;
		else if(!"NA".equalsIgnoreCase(reqTypeFlag)&& !validateMandatoryFieldsPPM(INF_TENOR_BASE, "Please select tenor base"))
			return false;
		else if(!"NA".equalsIgnoreCase(reqTypeFlag)&& !validateMandatoryFieldsPPM(INF_BASE_DOC_DATE, "Please enter base document date"))
			return false;
		else if("OTH".equalsIgnoreCase(formObject.getValue(INF_TENOR_BASE).toString()) 
				&& !validateMandatoryFieldsPPM(INF_ACTUAL_TENOR_BASE, "Please enter actual tenor base details")){
			return false;
		}
		else if(!validateMandatoryFieldsPPM(INF_MATURITY_DATE, "Maturity date can't be left blank"))
			return false;
		else if(!validateMandatoryFieldsPPM(BILL_CUST_HLDING_ACC_US, "Please select customer Account Holding Status"))
			return false;


		else if("1".equalsIgnoreCase(sCustHoldingAccWithUs) && !validateMandatoryFieldsPPM(INF_CHARGE_ACC_NUM, "Please select charge account number"))
			return false;
		else if("1".equalsIgnoreCase(sCustHoldingAccWithUs) && !validateMandatoryFieldsPPM(INF_CHARGE_ACC_TITLE, "Charge Account Title cannot be left blank"))
			return false;
		else if("1".equalsIgnoreCase(sCustHoldingAccWithUs) && !validateMandatoryFieldsPPM(INF_CHARGE_ACC_CURR, "Charge currency cannot be left blank"))
			return false;



		else if("1".equalsIgnoreCase(sCustHoldingAccWithUs) && !validateMandatoryFieldsPPM(INF_SETTLEMENT_ACC_NUM, "Please select settlement account number"))
			return false;
		else if("1".equalsIgnoreCase(sCustHoldingAccWithUs) && !validateMandatoryFieldsPPM(INF_SETTLEMENT_ACC_TITLE, "Settlement Account Title cannot be left blank"))
			return false;
		else if("1".equalsIgnoreCase(sCustHoldingAccWithUs) && !validateMandatoryFieldsPPM(INF_SETTLEMENT_ACC_CURR, "Settlement currency cannot be left blank"))
			return false;

		else if(!validateMandatoryFieldsPPM("REMITTANCE_CURR", "Please Select Remittance Currency"))
			return false;
		else if(!validateMandatoryFieldsPPM("REMITTANCE_AMT", "Please enter Remittance Amount"))
			return false;
		else if("TL".equalsIgnoreCase(requestCategory) && "TL_LD".equalsIgnoreCase(requestType) 
				&& !validateMandatoryFieldsPPM(IFS_LOAN_GRP, "Please select Loan Group"))
			return false;
		else if("TL_LD".equalsIgnoreCase(requestType) && ("ILCB".equalsIgnoreCase(loan_group) ||"IC".equalsIgnoreCase(loan_group))
				&& !validateMandatoryFieldsPPM(BILL_TYPE, "Please select Bill Type"))  //Sprint_US_155  //Sprint_US_158
			return false;
		else if("TL_LD".equalsIgnoreCase(requestType) && ("ILCB".equalsIgnoreCase(loan_group) ||"IC".equalsIgnoreCase(loan_group))
				&& !validateMandatoryFieldsPPM(IF_SIGHT_BILL, "Please select If Sight Bill"))  //Sprint_US_155  //Sprint_US_158
			return false;
		else if("TL_LD".equalsIgnoreCase(requestType) && ("ILCB".equalsIgnoreCase(loan_group) ||"IC".equalsIgnoreCase(loan_group))
				&& !validateMandatoryFieldsPPM(DISCREPANCY_INSTRUCTION, "Please select Customer Instruction"))  //Sprint_US_155  //Sprint_US_158
			return false;
		else if("TL_LD".equalsIgnoreCase(requestType) && ("ILCB".equalsIgnoreCase(loan_group) ||"IC".equalsIgnoreCase(loan_group))
				&& !validateMandatoryFieldsPPM(SETTLEMENT_INSTRUCTION, "Please select Settlement Instructions"))  //Sprint_US_155  //Sprint_US_158
			return false;
		return true;
	}
	private boolean ilcbInputFrameValidate(){
		enableDisableMultipleBtnControl(LIST_VIEW_BUTTONS3,true);
		enableDisableMultipleBtnControl(BUTTON_SUBMIT,false); 

		String sCustHoldingAccWithUs="";
		sCustHoldingAccWithUs = formObject.getValue(BILL_CUST_HLDING_ACC_US).toString();

		if(!validateMandatoryFieldsPPM(PRODUCT_TYPE, "Please select product type"))
			return false;
		else if("ILCB_AM".equalsIgnoreCase(requestType) 
				&& !validateMandatoryFieldsPPM(INF_AMEND_TYPE, "Please select Amendment type")){
			return false;
		}

		if("ILCB_BL".equalsIgnoreCase(requestType))
		{	
			if(!validateMandatoryFieldsPPM(INF_TENOR_BASE, "Please select tenor base"))
				return false;
			else if(!validateMandatoryFieldsPPM(INF_BASE_DOC_DATE, "Please enter base document date"))
				return false;
			else if("ILCB_O".equalsIgnoreCase(formObject.getValue(INF_TENOR_BASE).toString()) 
					&& !validateMandatoryFieldsPPM(INF_ACTUAL_TENOR_BASE, "Please enter tenor base details")){
				return false;
			}
			else if(!validateMandatoryFieldsPPM(INF_MATURITY_DATE, "Maturity date can't left blank"))
				return false;
			else if(!validateNumberFieldswithZero(INF_TENOR_DAYS, "Tenor days can't left blank"))
				return false;
		}
		else if(!validateMandatoryFieldsPPM(BILL_CUST_HLDING_ACC_US, "Please select customer Account Holding Status"))
			return false;

		else if("1".equalsIgnoreCase(sCustHoldingAccWithUs) && !validateMandatoryFieldsPPM(INF_CHARGE_ACC_NUM, "Please select charge account number"))
			return false;
		else if("1".equalsIgnoreCase(sCustHoldingAccWithUs) && !validateMandatoryFieldsPPM(INF_CHARGE_ACC_TITLE, "Charge Account Title cannot left blank"))
			return false;
		else if("1".equalsIgnoreCase(sCustHoldingAccWithUs) && !validateMandatoryFieldsPPM(INF_CHARGE_ACC_CURR, "Charge currency cannot left blank"))
			return false;

		else if("ILCB_BS".equalsIgnoreCase(requestType) && "1".equalsIgnoreCase(sCustHoldingAccWithUs) && !validateMandatoryFieldsPPM(INF_SETTLEMENT_ACC_NUM, "Please select settlement account number"))
			return false;
		else if("ILCB_BS".equalsIgnoreCase(requestType) && "1".equalsIgnoreCase(sCustHoldingAccWithUs) && !validateMandatoryFieldsPPM(INF_SETTLEMENT_ACC_TITLE, "Settlement Account Title cannot left blank"))
			return false;
		else if("ILCB_BS".equalsIgnoreCase(requestType) && "1".equalsIgnoreCase(sCustHoldingAccWithUs) && !validateMandatoryFieldsPPM(INF_SETTLEMENT_ACC_CURR, "Settlement currency cannot left blank"))
			return false;		

		else if("ILCB_AM".equalsIgnoreCase(requestType) && !validateMandatoryFieldsPPM(BILL_RVSD_DOC_AVL, "Please select document revised status"))
			return false;
		else if(("ILCB_AC".equalsIgnoreCase(requestType) || "ILCB_BS".equalsIgnoreCase(requestType)) && !validateMandatoryFieldsPPM(BILL_MODE_OF_PMNT, "Please select mode of payment."))
			return false;
		else if(("ILCB_AC".equalsIgnoreCase(requestType) || "ILCB_BS".equalsIgnoreCase(requestType)) && !validateMandatoryFieldsPPM(BILL_TYPE, "Please select Bill Type")) //Sprint_US_149 - 150
			return false;
		else if(("ILCB_AC".equalsIgnoreCase(requestType) || "ILCB_BS".equalsIgnoreCase(requestType)) && !validateMandatoryFieldsPPM(IF_SIGHT_BILL, "Please select If Sight Bill")) //Sprint_US_149 - 150
			return false;
		else if(("ILCB_AC".equalsIgnoreCase(requestType) || "ILCB_BS".equalsIgnoreCase(requestType)) && !validateMandatoryFieldsPPM(DISCREPANCY_INSTRUCTION, "Please select Customer Instruction")) //Sprint_US_149 - 150
			return false;
		else if(("ILCB_AC".equalsIgnoreCase(requestType) || "ILCB_BS".equalsIgnoreCase(requestType)) && !validateMandatoryFieldsPPM(SETTLEMENT_INSTRUCTION, "Please select Settlement Instructions"))  //Sprint_US_149 - 150
			return false;
		return true;
	}
	private boolean ecInputFrameValidate(){
		enableDisableMultipleBtnControl(LIST_VIEW_BUTTONS3,true);
		enableDisableMultipleBtnControl(BUTTON_SUBMIT,false); 

		String sCustHoldingAccWithUs="";
		sCustHoldingAccWithUs = formObject.getValue(BILL_CUST_HLDING_ACC_US).toString();

		if(!validateMandatoryFieldsPPM(PRODUCT_TYPE, "Please select product type"))
			return false;
		else if("EC_AM".equalsIgnoreCase(requestType) 
				&& !validateMandatoryFieldsPPM(INF_AMEND_TYPE, "Please select Amendment type")){
			return false;
		}

		else if ("EC_BL".equalsIgnoreCase(requestType) || "EC_LDDI".equalsIgnoreCase(requestType))
		{	
			if("EC_P".equalsIgnoreCase(formObject.getValue(SOURCE_CHANNEL).toString()) && "EC_BL".equalsIgnoreCase(requestType)) {
				if("EC_O".equalsIgnoreCase(formObject.getValue(INF_TENOR_BASE).toString()) 
						&& !validateMandatoryFieldsPPM(INF_ACTUAL_TENOR_BASE, "Please enter tenor base details")){
					return false;
				}
			}
			else if(!validateMandatoryFieldsPPM(INF_TENOR_BASE, "Please select tenor base"))
				return false;
			else if(!validateMandatoryFieldsPPM(INF_BASE_DOC_DATE, "Please enter base document date"))
				return false;
			else if("EC_O".equalsIgnoreCase(formObject.getValue(INF_TENOR_BASE).toString()) 
					&& !validateMandatoryFieldsPPM(INF_ACTUAL_TENOR_BASE, "Please enter tenor base details")){
				return false;
			}
			else if(!validateMandatoryFieldsPPM(INF_MATURITY_DATE, "Maturity date can't left blank"))
				return false;
			else if(!validateNumberFieldswithZero(INF_TENOR_DAYS, "Tenor days can't left blank"))
				return false;

		}else if(!validateMandatoryFieldsPPM(BILL_CUST_HLDING_ACC_US, "Please select customer Account Holding Status"))
			return false;
		else if("1".equalsIgnoreCase(sCustHoldingAccWithUs) && !validateMandatoryFieldsPPM(INF_CHARGE_ACC_NUM, "Please select charge account number"))
			return false;
		else if("1".equalsIgnoreCase(sCustHoldingAccWithUs) && !validateMandatoryFieldsPPM(INF_CHARGE_ACC_TITLE, "Charge Account Title cannot left blank"))
			return false;
		else if("1".equalsIgnoreCase(sCustHoldingAccWithUs) && !validateMandatoryFieldsPPM(INF_CHARGE_ACC_CURR, "Charge currency cannot left blank"))
			return false;
		else if(("EC_BS".equalsIgnoreCase(requestType)|| "EC_DISC".equalsIgnoreCase(requestType)
				||"EC_CAP".equalsIgnoreCase(requestType)|| "EC_LDDI".equalsIgnoreCase(requestType)) && "1".equalsIgnoreCase(sCustHoldingAccWithUs) && !validateMandatoryFieldsPPM(INF_SETTLEMENT_ACC_NUM, "Please select settlement account number"))
			return false;
		else if(("EC_BS".equalsIgnoreCase(requestType)|| "EC_DISC".equalsIgnoreCase(requestType)
				||"EC_CAP".equalsIgnoreCase(requestType)|| "EC_LDDI".equalsIgnoreCase(requestType)) && "1".equalsIgnoreCase(sCustHoldingAccWithUs) && !validateMandatoryFieldsPPM(INF_SETTLEMENT_ACC_TITLE, "Settlement Account Title cannot left blank"))
			return false;
		else if(("EC_BS".equalsIgnoreCase(requestType)|| "EC_DISC".equalsIgnoreCase(requestType)
				||"EC_CAP".equalsIgnoreCase(requestType)|| "EC_LDDI".equalsIgnoreCase(requestType)) && "1".equalsIgnoreCase(sCustHoldingAccWithUs) && !validateMandatoryFieldsPPM(INF_SETTLEMENT_ACC_CURR, "Settlement currency cannot left blank"))
			return false;	
		else if("EC_AM".equalsIgnoreCase(requestType) && !validateMandatoryFieldsPPM(BILL_RVSD_DOC_AVL, "Please select document revised status"))
			return false;
		else if(!validateMandatoryFieldsPPM(BILL_MODE_OF_PMNT, "Please select mode of payment."))
			return false;

		return true;
	}	
	private boolean icInputFrameValidate(){
		enableDisableMultipleBtnControl(LIST_VIEW_BUTTONS,true);
		enableDisableMultipleBtnControl(BUTTON_SUBMIT,false); 

		String sCustHoldingAccWithUs="";
		sCustHoldingAccWithUs = formObject.getValue(BILL_CUST_HLDING_ACC_US).toString();

		if(!validateMandatoryFieldsPPM(PRODUCT_TYPE, "Please select product type"))
			return false;
		else if("IC_AM".equalsIgnoreCase(requestType) 
				&& !validateMandatoryFieldsPPM(INF_AMEND_TYPE, "Please select Amendment type")){
			return false;
		}

		else if ("IC_BL".equalsIgnoreCase(requestType))
		{
			if(!validateMandatoryFieldsPPM(INF_TENOR_BASE, "Please select tenor base"))
				return false;
			else if(!validateMandatoryFieldsPPM(INF_BASE_DOC_DATE, "Please enter base document date"))
				return false;
			else if("IC_O".equalsIgnoreCase(formObject.getValue(INF_TENOR_BASE).toString()) 
					&& !validateMandatoryFieldsPPM(INF_ACTUAL_TENOR_BASE, "Please enter actual tenor base details")){
				return false;
			}
			else if(!validateMandatoryFieldsPPM(INF_MATURITY_DATE, "Maturity date can't left blank"))
				return false;
			else if(!validateNumberFieldswithZero(INF_TENOR_DAYS, "Tenor days can't left blank"))
				return false;	
		}
		else if(!validateMandatoryFieldsPPM(BILL_CUST_HLDING_ACC_US, "Please select customer Account Holding Status"))
			return false;


		else if("1".equalsIgnoreCase(sCustHoldingAccWithUs) && !validateMandatoryFieldsPPM(INF_CHARGE_ACC_NUM, "Please select charge account number"))
			return false;
		else if("1".equalsIgnoreCase(sCustHoldingAccWithUs) && !validateMandatoryFieldsPPM(INF_CHARGE_ACC_TITLE, "Charge Account Title cannot left blank"))
			return false;
		else if("1".equalsIgnoreCase(sCustHoldingAccWithUs) && !validateMandatoryFieldsPPM(INF_CHARGE_ACC_CURR, "Charge currency cannot left blank"))
			return false;

		else if("IC_BS".equalsIgnoreCase(requestType) && "1".equalsIgnoreCase(sCustHoldingAccWithUs) && !validateMandatoryFieldsPPM(INF_SETTLEMENT_ACC_NUM, "Please select settlement account number"))
			return false;
		else if("IC_BS".equalsIgnoreCase(requestType) && "1".equalsIgnoreCase(sCustHoldingAccWithUs) && !validateMandatoryFieldsPPM(INF_SETTLEMENT_ACC_TITLE, "Settlement Account Title cannot left blank"))
			return false;
		else if("IC_BS".equalsIgnoreCase(requestType) && "1".equalsIgnoreCase(sCustHoldingAccWithUs) && !validateMandatoryFieldsPPM(INF_SETTLEMENT_ACC_CURR, "Settlement currency cannot left blank"))
			return false;	

		else if("IC_AM".equalsIgnoreCase(requestType) && !validateMandatoryFieldsPPM(BILL_RVSD_DOC_AVL, "Please select document revised status"))
			return false;
		else if(("IC_AC".equalsIgnoreCase(requestType)||"IC_BS".equalsIgnoreCase(requestType)) 
				&& !validateMandatoryFieldsPPM(BILL_MODE_OF_PMNT, "Please select mode of payment."))
			return false;
		else if(("IC_AC".equalsIgnoreCase(requestType)||"IC_BS".equalsIgnoreCase(requestType)) //Sprint_US_155 //Sprint_US_157
				&& !validateMandatoryFieldsPPM(BILL_TYPE, "Please select Bill Type"))
			return false;
		else if(("IC_AC".equalsIgnoreCase(requestType)||"IC_BS".equalsIgnoreCase(requestType)) //Sprint_US_155 //Sprint_US_157
				&& !validateMandatoryFieldsPPM(IF_SIGHT_BILL, "Please select If Sight Bill"))
			return false;
		else if(("IC_AC".equalsIgnoreCase(requestType)||"IC_BS".equalsIgnoreCase(requestType)) //Sprint_US_155 //Sprint_US_157
				&& !validateMandatoryFieldsPPM(DISCREPANCY_INSTRUCTION, "Please select Customer Instruction"))
			return false;
		else if(("IC_AC".equalsIgnoreCase(requestType)||"IC_BS".equalsIgnoreCase(requestType)) //Sprint_US_155 //Sprint_US_157
				&& !validateMandatoryFieldsPPM(SETTLEMENT_INSTRUCTION, "Please select Settlement Instruction"))
			return false;
		return true;
	}
	private boolean dbaInputFrameValidate(){
		enableDisableMultipleBtnControl(LIST_VIEW_BUTTONS3,true);
		enableDisableMultipleBtnControl(BUTTON_SUBMIT,false); 

		String sCustHoldingAccWithUs="";
		sCustHoldingAccWithUs = formObject.getValue(BILL_CUST_HLDING_ACC_US).toString();

		if(!validateMandatoryFieldsPPM(PRODUCT_TYPE, "Please select product type"))
			return false;
		else if("DBA_AM".equalsIgnoreCase(requestType) 
				&& !validateMandatoryFieldsPPM(INF_AMEND_TYPE, "Please select Amendment type")){
			return false;
		}

		if ("DBA_BL".equalsIgnoreCase(requestType))
		{ 
			if(!validateMandatoryFieldsPPM(INF_TENOR_BASE, "Please select tenor base"))
				return false;
			else if(!validateMandatoryFieldsPPM(INF_BASE_DOC_DATE, "Please enter base document date"))
				return false;
			else if("DBA_O".equalsIgnoreCase(formObject.getValue(INF_TENOR_BASE).toString()) 
					&& !validateMandatoryFieldsPPM(INF_ACTUAL_TENOR_BASE, "Please enter actual tenor base details")){
				return false;
			}
			else if(!validateMandatoryFieldsPPM(INF_MATURITY_DATE, "Maturity date can't left blank"))
				return false;
			else if(!validateNumberFieldswithZero(INF_TENOR_DAYS, "Tenor days can't left blank"))
				return false;
		}
		else if(!validateMandatoryFieldsPPM(BILL_CUST_HLDING_ACC_US, "Please select customer Account Holding Status"))
			return false;
		else if("1".equalsIgnoreCase(sCustHoldingAccWithUs) && !validateMandatoryFieldsPPM(INF_CHARGE_ACC_NUM, "Please select charge account number"))
			return false;
		else if("1".equalsIgnoreCase(sCustHoldingAccWithUs) && !validateMandatoryFieldsPPM(INF_CHARGE_ACC_TITLE, "Charge Account Title cannot left blank"))
			return false;
		else if("1".equalsIgnoreCase(sCustHoldingAccWithUs) && !validateMandatoryFieldsPPM(INF_CHARGE_ACC_CURR, "Charge currency cannot left blank"))
			return false;

		else if("DBA_STL".equalsIgnoreCase(requestType) && "1".equalsIgnoreCase(sCustHoldingAccWithUs) && !validateMandatoryFieldsPPM(INF_SETTLEMENT_ACC_NUM, "Please select settlement account number"))
			return false;
		else if("DBA_STL".equalsIgnoreCase(requestType) && "1".equalsIgnoreCase(sCustHoldingAccWithUs) && !validateMandatoryFieldsPPM(INF_SETTLEMENT_ACC_TITLE, "Settlement Account Title cannot left blank"))
			return false;
		else if("DBA_STL".equalsIgnoreCase(requestType) && "1".equalsIgnoreCase(sCustHoldingAccWithUs) && !validateMandatoryFieldsPPM(INF_SETTLEMENT_ACC_CURR, "Settlement currency cannot left blank"))
			return false;	

		else if("DBA_AM".equalsIgnoreCase(requestType) && !validateMandatoryFieldsPPM(BILL_RVSD_DOC_AVL, "Please select document revised status"))
			return false;
		else if(("DBA_STL".equalsIgnoreCase(requestType)) 
				&& !validateMandatoryFieldsPPM(BILL_MODE_OF_PMNT, "Please select mode of payment."))
			return false;
		return true;
	}

	private boolean elcbInputFrameValidate(){
		enableDisableMultipleBtnControl(LIST_VIEW_BUTTONS3,true);
		enableDisableMultipleBtnControl(BUTTON_SUBMIT,false); 

		String sCustHoldingAccWithUs="";
		sCustHoldingAccWithUs = formObject.getValue(BILL_CUST_HLDING_ACC_US).toString();

		if(!validateMandatoryFieldsPPM(PRODUCT_TYPE, "Please select product type"))
			return false;
		else if("ELCB_AM".equalsIgnoreCase(requestType) 
				&& !validateMandatoryFieldsPPM(INF_AMEND_TYPE, "Please select Amendment type")){
			return false;
		}
		else if("ELCB_BL".equalsIgnoreCase(requestType))
		{	
			if("ELCB_P".equalsIgnoreCase(formObject.getValue(SOURCE_CHANNEL).toString())) {
				if("ELCB_O".equalsIgnoreCase(formObject.getValue(INF_TENOR_BASE).toString()) 
						&& !validateMandatoryFieldsPPM(INF_ACTUAL_TENOR_BASE, "Please enter tenor base details")){
					return false;
				}
			}else if(!validateMandatoryFieldsPPM(INF_TENOR_BASE, "Please select tenor base"))
				return false;
			else if(!validateMandatoryFieldsPPM(INF_BASE_DOC_DATE, "Please enter base document date"))
				return false;
			else if("ELCB_O".equalsIgnoreCase(formObject.getValue(INF_TENOR_BASE).toString()) 
					&& !validateMandatoryFieldsPPM(INF_ACTUAL_TENOR_BASE, "Please enter tenor base details")){
				return false;
			}
			else if(!validateMandatoryFieldsPPM(INF_MATURITY_DATE, "Maturity date can't left blank"))
				return false;
			else if(!validateNumberFieldswithZero(INF_TENOR_DAYS, "Tenor days can't left blank"))
				return false;

		}
		else if(!validateMandatoryFieldsPPM(BILL_CUST_HLDING_ACC_US, "Please select customer Account Holding Status"))
			return false;


		else if("1".equalsIgnoreCase(sCustHoldingAccWithUs) && !validateMandatoryFieldsPPM(INF_CHARGE_ACC_NUM, "Please select charge account number"))
			return false;
		else if("1".equalsIgnoreCase(sCustHoldingAccWithUs) && !validateMandatoryFieldsPPM(INF_CHARGE_ACC_TITLE, "Charge Account Title cannot left blank"))
			return false;
		else if("1".equalsIgnoreCase(sCustHoldingAccWithUs) && !validateMandatoryFieldsPPM(INF_CHARGE_ACC_CURR, "Charge currency cannot left blank"))
			return false;

		else if(("ELCB_BS".equalsIgnoreCase(requestType)|| "ELCB_DISC".equalsIgnoreCase(requestType)
				|| "ELCB_CLBP".equalsIgnoreCase(requestType)) && "1".equalsIgnoreCase(sCustHoldingAccWithUs) && !validateMandatoryFieldsPPM(INF_SETTLEMENT_ACC_NUM, "Please select settlement account number"))
			return false;
		else if(("ELCB_BS".equalsIgnoreCase(requestType)|| "ELCB_DISC".equalsIgnoreCase(requestType)
				|| "ELCB_CLBP".equalsIgnoreCase(requestType)) && "1".equalsIgnoreCase(sCustHoldingAccWithUs) && !validateMandatoryFieldsPPM(INF_SETTLEMENT_ACC_TITLE, "Settlement Account Title cannot left blank"))
			return false;
		else if(("ELCB_BS".equalsIgnoreCase(requestType)|| "ELCB_DISC".equalsIgnoreCase(requestType)
				|| "ELCB_CLBP".equalsIgnoreCase(requestType)) && "1".equalsIgnoreCase(sCustHoldingAccWithUs) && !validateMandatoryFieldsPPM(INF_SETTLEMENT_ACC_CURR, "Settlement currency cannot left blank"))
			return false;		

		else if("ELCB_AM".equalsIgnoreCase(requestType) && !validateMandatoryFieldsPPM(BILL_RVSD_DOC_AVL, "Please select document revised status"))
			return false;
		else if(!validateMandatoryFieldsPPM(BILL_MODE_OF_PMNT, "Please select mode of payment."))
			return false;

		return true;
	}
	protected boolean callingFrameValidate(){

		String sBuyerCalling="";
		sBuyerCalling= formObject.getValue("BC_CALL_DONE").toString();
		if(!validateMandatoryFieldsPPM("BC_CALL_DONE", "Please select Buyer calling status"))
			return false;
		else if("N".equalsIgnoreCase(sBuyerCalling)){
			if(!validateMandatoryFieldsPPM("BC_REMAKS_NOT_DONE", "Please enter the reason for no call"))
				return false;
		}
		else if("Y".equalsIgnoreCase(sBuyerCalling)){
			if(!validateMandatoryFieldsPPM("BC_CALL_INFO", "Please enter the details about call information"))
				return false;
			if(!validateMandatoryFieldsPPM("BC_DATE_OF_CALL", "Please select the date of call"))
				return false;
			if(!validateMandatoryFieldsPPM("BC_RSN_OF_CALL", "Please enter the reason of call"))
				return false;
			if(!validateMandatoryFieldsPPM("BC_NAME_OF_CALLER", "Please enter the name of caller"))
				return false;
			if(!validateMandatoryFieldsPPM("BC_TIME_OF_CALL", "Please enter time of call"))
				return false;
			if(!validateMandatoryFieldsPPM("BC_BUYER_NAME", "Please enter buyer name"))
				return false;
			if(!validateMandatoryFieldsPPM("BC_CONTACT_PERSON", "Please enter contact person name"))
				return false;
			if(!validateMandatoryFieldsPPM("BC_BUYER_CONTACT_NUM", "Please enter buyer contact number"))
				return false;
			if(!validateMandatoryFieldsPPM("BC_INVOICE_CURR", "Please select invoice currency"))
				return false;
			if(!validateMandatoryFieldsPPM("BC_INVOICE_AMT", "Please enter invoice amount"))
				return false;
			if(!validateMandatoryFieldsPPM("BC_INVOICE_NUMBER", "Please enter invoice number"))
				return false;
		}  		
		return true;
	}

	public void addGridData(String sFinalString, String sQVRName, String tabName) {
		log.info("sFinalString"+sFinalString+"TabName="+tabName+"sQVRName="+sQVRName);
		if("Empty".equalsIgnoreCase(sFinalString)){
			log.info("Flag "+emptyFlag);
			if(tabName.equalsIgnoreCase(DOC_RVW_TAB)) {
				emptyFlag=true;
				log.info("Flag for DocRvw=>"+emptyFlag);
			}
			else if(tabName.equalsIgnoreCase(LMT_CRDT_TAB)) {
				emptyLimitFlag = true;
				log.info("Flag for LmtCrdt=>"+emptyLimitFlag);
			}
			else {
				emptyFlag=true;
				emptyLimitFlag = true;
				log.info("Temprary else=>"+emptyLimitFlag);
			}
		}
		else{	
			List<List<String>> tmpList =null;
			if("ELCB".equalsIgnoreCase(requestCategory)){
				tmpList = formObject.getDataFromDB("SELECT doc_rvw FROM tfo_dj_doc_rvw_mast WHERE mapped_controlname='ELCB_Doc_Crdt_Compiled'");
			}else if("ILCB".equalsIgnoreCase(requestCategory)){
				tmpList = formObject.getDataFromDB("SELECT doc_rvw FROM tfo_dj_doc_rvw_mast WHERE mapped_controlname='ILCB_DOC_CRD_COMPLIED'");
			}

			String[] strArr = sFinalString.split("##");
			StringBuilder sb = new StringBuilder();
			formObject.clearTable(sQVRName);
			JSONArray jsonArray=new JSONArray();
			String strCell = "";
			String[]  columnName = "guideline,response".split(",");
			for (int i = 0; i < strArr.length; i++) {
				String strCellData[] = strArr[i].split("#");
				JSONObject jsonObject=new JSONObject();
				int counter = 0;
				for (int j=0;j< strCellData.length;j++) {
					strCell=strCellData[j];
					strCell=strCell.replace("<BR>", "<>");
					strCell=strCell.replace("<br>", "<>");
					log.info("StrCell="+strCell);
					jsonObject.put(columnName[j], strCell);
					if(counter == 1){
						dList=strCell;
						counter++;
					}
					if(tmpList!=null && strCell.equalsIgnoreCase(tmpList.get(0).get(0).trim())){
						counter++;
					}
					if(columnName[j].trim().equalsIgnoreCase("Legalization Charges Applicable")&&!strCell.trim().equalsIgnoreCase("Yes")){
						formObject.setValue("EDAS_APPROVAL", "Not Applicable");
					}else if(columnName[j].trim().equalsIgnoreCase("Legalization Charges Applicable")&&strCell.trim().equalsIgnoreCase("Yes")){
						formObject.setValue("EDAS_APPROVAL", "");
					}
				}
				jsonArray.add(jsonObject);
			}
			log.info("sQVRName size="+sQVRName);
			log.info("jsonArray size="+jsonArray);
			formObject.addDataToGrid(sQVRName, jsonArray);
			log.info("String After Append " + sb);

		}
	}

	protected boolean checkGridLoadvalidation(String sTabName){
		log.info("Req Type "+requestType+"   Reqcat "+requestCategory+"  sTabname "+sTabName);
		List<List<String>> lstmaster =  null;    	
		String sQuery = "SELECT REQ_CAT, REQ_TYPE, DOC_RVW AS LABEL,CNTRL_TYPE AS LOV_STATUS,DOC_RVW_KEY AS TABLE_REF, TAB_NAME, IS_TP AS IS_THIRD_PARTY"+
				" FROM tfo_dj_doc_rvw_mast where TAB_NAME = '"+sTabName+"' and REQ_TYPE_CODE = '"+requestType+"' and REQ_CAT_CODE = '"+requestCategory+"'";
		log.info("sQuery "+sQuery);
		lstmaster = formObject.getDataFromDB(sQuery);
		log.info("sQuery result  "+sQuery);
		if(lstmaster!=null)
			if(!lstmaster.isEmpty())
				return true;
			else{
				sendMessageHashMap("","No record present for the selected Request Category and Request Type");
				return false;
			}
		else{
			sendMessageHashMap("","No record present for the selected Request Category and Request Type");
			return false;
		}
	}
	protected boolean validateAmountFields(String val, int preDot, int postDot)
	{
		try
		{		if(!val.trim().isEmpty()){
			if((val.indexOf(".") != 0) && (val.length() > 0)){
				Double.parseDouble(val);

			}
			log.info("1");
			if((val.contains(".")) && (val.indexOf(".") != 0) && (val.indexOf(".") != val.length() - 1)){
				if((val.substring(0,val.indexOf("."))).length() > preDot){
					return false;
				}else if((val.substring(val.indexOf(".") + 1,val.length())).length() > postDot){
					return false;
				}
			} 
			else if(val.length() > preDot) {
				return false;
			}
			else if(val.indexOf(".")==0){
				if((val.substring(val.indexOf(".") + 1,val.length())).length() > postDot) {
					return false;
				}else {
					return true;
				}

			}


		}


		}
		catch (Exception e) {
			log.error("Exception: ",e);
			return false;
		}

		return true;
	}
	private void makeNonEditableIfsInputDtlsTab(){
		try {
			if("PROCESSING MAKER".equalsIgnoreCase(sActivityName)){
				makeNonEditableIfInputDtlsTab();
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}
	private void makeNonEditableIfInputDtlsTab(){
		try {
			disableControls(DISABLE_PM_IF_TXT);
			disableControls(DISABLE_PM_ADVISING_TXT);
			disableControls(DISABLE_PM_IF_LOV);
			disableControls(DISABLE_PM_IF_CHECKBOX);  

		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}
	private boolean icgRmCheck(String event,int  fdReferralListCount){
		log.info("event"+event+"fdReferralListCount="+fdReferralListCount);
		boolean bIcgFlg=true;
		boolean bFlgEnable=false;
		try {
			if(("EC".equalsIgnoreCase(requestCategory) && "EC_CA".equalsIgnoreCase(requestType))
					|| ("ELC".equalsIgnoreCase(requestCategory) && "ELC_LCC".equalsIgnoreCase(requestType))
					|| ("ELCB".equalsIgnoreCase(requestCategory) && "ELCB_DISC".equalsIgnoreCase(requestType))){
				log.info(" icgRmCheck RowCount "+fdReferralListCount);
				for(int count=1; count<fdReferralListCount; count++){	
					String rmExistValaue = formObject.getTableCellValue(LISTVIEW_FINAL_DECISION, count, 1);
					if("RM".equalsIgnoreCase(rmExistValaue)){
						log.info("Found RM");
						if(EVENT_TYPE_CLICK.equalsIgnoreCase(event)){
							bIcgFlg=validateMandatoryFieldsPPM(REF_ICG_RM,"Please select Referred To ICG RM");
						}					
						bFlgEnable=true;					
						break;
					}
				}
				if(bFlgEnable) {
					enableFieldOnDemand(REF_ICG_RM);

				} else {
					formObject.setValue(REF_ICG_RM, "");
					disableFieldOnDemand(REF_ICG_RM);
				}				
			} else {
				formObject.setValue(REF_ICG_RM, "");
				disableFieldOnDemand(REF_ICG_RM);				
			}

		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		return bIcgFlg;
	}
	protected void decisionValidationPPM(String sControlName){
		try {
			log.info("************************Inside decisionValidation****************************");
			boolean sReferalFlag = false;
			sReferalFlag = getReferalflagStatusPPM();
			log.info("sReferal Flag returned from getReferalflagStatusPPM()"+sReferalFlag +" Activity Name "+this.sActivityName);

			if(sReferalFlag){
				loadDecision("APP,ALDAC,TAOS,EPOCR,STBP,SAOE",sControlName);//APP -- Approved should not be in case of referal
				disableFieldOnDemand(ROUTE_TO_PPM); 

			}
			else{
				if(!"ILCB_AC".equalsIgnoreCase(requestType)&&!"IC_AC".equalsIgnoreCase(requestType)){
					enableFieldOnDemand(ROUTE_TO_PPM);
					//loadDecision("REF", sControlName);
					if("ELCB_AM".equalsIgnoreCase(requestType)){
						loadDecision("ALDAC,REF,SAOE,STBP,TAOS,EPOCR",sControlName); 
                        formObject.setValue(sControlName, "APP");
					}
					else if("SBLC".equalsIgnoreCase(requestCategory) && "SBLC_ER".equalsIgnoreCase(requestType)){//added by mansi
						loadDecision("ALDAC,REF,TAOS,EPOCR",sControlName);
					}else if("ELC".equalsIgnoreCase(requestCategory) && "ELC_SER".equalsIgnoreCase(requestType)){
						loadDecision("ALDAC,REF,SAOE,STBP,EPOCR",sControlName);
						//formObject.setValue(sControlName, "TAOS");
					}
					else if("GRNT".equalsIgnoreCase(requestCategory) && "EPC".equalsIgnoreCase(requestType)){
						loadDecision("ALDAC,REF,SAOE,STBP,TAOS",sControlName);
						//formObject.setValue(sControlName, "EPOCR");
					}else{
						loadDecision("ALDAC,REF,SAOE,STBP,TAOS,EPOCR",sControlName); 
					}

				} 
				else{
					enableFieldOnDemand(ROUTE_TO_PPM);
					loadDecision("REF", sControlName);
				}
			}

		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}
	@Override
	public void updateDataInWidget(IFormReference arg0, String arg1) {

	}
	@Override
	public String validateDocumentConfiguration(String arg0, String arg1,
			File arg2, Locale arg3) {
		return null;
	}
	protected boolean getReferalflagStatusPPM(){
		try {

			String sRMFlag=formObject.getValue(IS_RM_PPM).toString();
			String sTSDFlag=formObject.getValue(IS_REF_PPM).toString(); 
			String sLEGALFlag=formObject.getValue(IS_LEGAL_PPM).toString();
			String sCRFlag=formObject.getValue(IS_CR_PPM).toString();
			String sCBFlag=formObject.getValue(IS_CB_PPM).toString();
			String sTradeFlag=formObject.getValue(IS_TRADE_PPM).toString();
			log.info("IS_RM_PPM="+sRMFlag+IS_REF_PPM+sTSDFlag+IS_LEGAL_PPM+sLEGALFlag+
					IS_CR_PPM+sCRFlag+IS_CB_PPM+sCBFlag+IS_TRADE_PPM+sTradeFlag);

			if("Y".equalsIgnoreCase(sRMFlag) || "Y".equalsIgnoreCase(sTSDFlag) || "Y".equalsIgnoreCase(sLEGALFlag) || "Y".equalsIgnoreCase(sCRFlag) || "Y".equalsIgnoreCase(sCBFlag)|| "Y".equalsIgnoreCase(sTradeFlag))
				return true;
			else
				return false;
		}
		catch (Exception e) {
			log.error("Exception: ",e);
		}
		return true;

	}

	protected void handlingRouteToPPM(String sControl){
		try {
			String sDecision="";
			sDecision = formObject.getValue(sControl).toString();

			log.info("sDecision="+sDecision);
			if(!sDecision.equalsIgnoreCase("APP")){					
				log.info("in sDecision=");
				disableFieldOnDemand(ROUTE_TO_PPM);
			}
			else{
				log.info("else sDecision=");
				enableFieldOnDemand(ROUTE_TO_PPM);
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	private void setDefaultFieldGRNT(){
		if("GRNT".equalsIgnoreCase(requestCategory) && 
				("NI".equalsIgnoreCase(requestType)||"AM".equalsIgnoreCase(requestType))
				&& formObject.getValue(PRODUCT_TYPE).toString().startsWith("T5") ){
			formObject.setValue(ACCOUNT_VALID, "2"); 
			setEmptyCombo(TRN_THIRD_PARTY,"2");
			setEmptyCombo(SUFF_BAL_AVL_PPM,"3");
		}else if("SBLC".equalsIgnoreCase(requestCategory) && 
				("SBLC_AM".equalsIgnoreCase(requestType))
				&& formObject.getValue(PRODUCT_TYPE).toString().startsWith("T5")){
			formObject.setValue(ACCOUNT_VALID, "2"); 
			setEmptyCombo(TRN_THIRD_PARTY,"2");
			setEmptyCombo(SUFF_BAL_AVL_PPM,"3");
		}else if ("ELC".equalsIgnoreCase(requestCategory)&& "ELC_SLCA".equalsIgnoreCase(requestType))//mansi
		{
			formObject.setValue(ACCOUNT_VALID, "2");
			setEmptyCombo(TRN_THIRD_PARTY,"2");
			//setEmptyCombo(SUFF_BAL_AVL_PPM,"3");
		}else if("ELC".equalsIgnoreCase(requestCategory) && 
				("ELC_SLCAA".equalsIgnoreCase(requestType))
				&& formObject.getValue(PRODUCT_TYPE).toString().startsWith("T5")){
			formObject.setValue(ACCOUNT_VALID, "2"); 
			setEmptyCombo(TRN_THIRD_PARTY,"2");
			setEmptyCombo(SUFF_BAL_AVL_PPM,"3");
		}else if((("GRNT".equalsIgnoreCase(requestCategory) && 
				("NI".equalsIgnoreCase(requestType)||"AM".equalsIgnoreCase(requestType)))
				||("SBLC".equalsIgnoreCase(requestCategory) && "SBLC_AM".equalsIgnoreCase(requestType))
				||("ELC".equalsIgnoreCase(requestCategory) && 
						("ELC_SLCA".equalsIgnoreCase(requestType)||"ELC_SLCAA".equalsIgnoreCase(requestType))))
						&& !(formObject.getValue(PROCESS_TYPE).toString().equalsIgnoreCase("PT") || formObject.getValue(PROCESS_TYPE).toString().equalsIgnoreCase("ET"))){  //ATP-469 Shahbaz 23-05-2024
			formObject.setValue(TRN_THIRD_PARTY,"");
		}
	}

	//Shipping GTEE 27
	private void setDefaultProductCode() {
		try{
			if ("SG".equalsIgnoreCase(requestCategory)) {
				log.info("## SG ##");
				String branchCode=formObject.getValue(RELATIONSHIP_TYPE).toString();
				if ("SG_NIC".equalsIgnoreCase(requestType)){
					log.info("## SG_NIC ##");
					if ("C".equalsIgnoreCase(branchCode)) {
						formObject.setValue(PRODUCT_TYPE, "T410");
					} else if ("I".equalsIgnoreCase(branchCode)) {
						formObject.setValue(PRODUCT_TYPE, "I410");
					}
				} else if ("SG_NILC".equalsIgnoreCase(requestType)){
					log.info("## SG_NILC ##");
					if ("C".equalsIgnoreCase(branchCode)) {
						formObject.setValue(PRODUCT_TYPE, "T409");
					} else if ("I".equalsIgnoreCase(branchCode)) {
						formObject.setValue(PRODUCT_TYPE, "I409");
					}
				}
			}
		} catch (Exception e){
			log.error("Exception: ",e);
		}
	}

	//Shipping GTEE 27
	private boolean SGInputFrameValidate(String requestType) {
		log.info("reqTpe="+requestType);
		if ("SG_NIC".equalsIgnoreCase(requestType) ||"SG_NILC".equalsIgnoreCase(requestType))
		{
			return validateMandatoryFieldsPPM(PRODUCT_TYPE, "Please select product type")
					&& validateMandatoryFieldsPPM(ACCOUNT_VALID, "Please Select Valid Account No.")
					&& SGPpmAssociatedControlCheck(ACCOUNT_VALID, ACCOUNT_NUMBER, "Please Fetch Account Number.")
					&& validatAcChrgTitle(formObject.getValue(ACCOUNT_VALID).toString());
		}
		return true;
	}
	private boolean SGPpmAssociatedControlCheck(String param1, String param2, String msg) {
		log.info(" ilcPpmAssociatedControlCheck  Param1  "+param1 +"  param 2" +param2 + " msg "+msg);
		if(ACCOUNT_NUMBER.equalsIgnoreCase(param2) 
				&& "1".equalsIgnoreCase(formObject.getValue(param1).toString())
				&& validateMandatoryFieldsPPM(param2, msg)){
			return true;
		}
		else if(ACCOUNT_NUMBER.equalsIgnoreCase(param2) 
				&& "2".equalsIgnoreCase(formObject.getValue(param1).toString())){
			return true;
		}		
		return false;
	}
	private void setAmendFrameData() {
		try {
			log.info("setAmendFrameData started");
			String control[] = null;
			//sheenu	
			if("ELC_LCAA".equalsIgnoreCase(requestType) ||"ELC_LCC".equalsIgnoreCase(requestType) ||"ILC_AM".equalsIgnoreCase(requestType)){
				control = AMEND_FRAME_FIELDS.split("#");
				//hideshowFrmInputTab(AMEND_FRAME_FIELDS,true);
			}else if("GRNT".equalsIgnoreCase(requestCategory) && "AM".equalsIgnoreCase(requestType)){
				control = GRNT_AM_AMEND_FRAME_FIELDS.split("#");
				//	hideshowFrmInputTab(GRNT_AM_AMEND_FRAME_FIELDS,true);
			}else if(("ELC".equalsIgnoreCase(requestCategory) && "ELC_SLCAA".equalsIgnoreCase(requestType))){
				control = SBLC_AM_AMEND_FRAME_FIELDS.split("#");
				//hideshowFrmInputTab(SBLC_AM_AMEND_FRAME_FIELDS,true);
			}
			else if(("SBLC".equalsIgnoreCase(requestCategory) && "SBLC_AM".equalsIgnoreCase(requestType))){
				control = SBLC_AM_ONLY_AMEND_FRAME_FIELDS.split("#");
			}
			else if(("GRNT".equalsIgnoreCase(requestCategory) && "GAA".equalsIgnoreCase(requestType))){
				control = GRNT_GAA_AMEND_FRAME_FIELDS.split("#");
				//hideshowFrmInputTab(GRNT_GAA_AMEND_FRAME_FIELDS,true);
			}
			String FIN_Transaction_amount_data = formObject.getValue("Q_Amendment_Data_FIN_TRANSACTION_AMOUNT").toString();
			//sheenu	if("ELC_LCAA".equalsIgnoreCase(requestType) ||"ELC_LCC".equalsIgnoreCase(requestType) ||"ILC_AM".equalsIgnoreCase(requestType)){
			if("".equalsIgnoreCase(FIN_Transaction_amount_data) ||  null==FIN_Transaction_amount_data){
				for (int counter = 0; counter < control.length; counter++ ) {
					String showValues="LABEL_"+control[counter]+"#"+"Q_Amendment_Data_USER_"+control[counter]+"#"+"Q_Amendment_Data_PT_"+control[counter]
							+"#"+"Q_Amendment_Data_FC_"+control[counter]+"#"+"Q_Amendment_Data_FIN_"+control[counter];
					hideshowFrmInputTab(showValues,true);
					if ("".equalsIgnoreCase(formObject.getValue("Q_Amendment_Data_FIN_"+control[counter]).toString()) || null == formObject.getValue("Q_Amendment_Data_FIN_"+control[counter]).toString()){
						log.info("CONTROL : "+control[counter]);
						log.info("Q_Amendment_Data_USER_"+control[counter] +" -- USER : "+formObject.getValue("Q_Amendment_Data_USER_"+control[counter]).toString());
						log.info("Q_Amendment_Data_PT_"+control[counter] +" -- PT : "+formObject.getValue("Q_Amendment_Data_PT_"+control[counter]).toString());
						log.info("Q_Amendment_Data_FC_"+control[counter] +" -- FC : "+formObject.getValue("Q_Amendment_Data_FC_"+control[counter]).toString());
						if (!"".equalsIgnoreCase(formObject.getValue("Q_Amendment_Data_USER_"+control[counter]).toString()) && null != (formObject.getValue("Q_Amendment_Data_USER_"+control[counter]).toString()) ){
							log.info("1"+control[counter]);
							formObject.setValue("Q_Amendment_Data_FIN_"+control[counter],formObject.getValue("Q_Amendment_Data_USER_"+control[counter]).toString());
						} else if (!"".equalsIgnoreCase(formObject.getValue("Q_Amendment_Data_PT_"+control[counter]).toString()) && null != (formObject.getValue("Q_Amendment_Data_PT_"+control[counter]).toString()) ){
							log.info("2"+control[counter]);
							formObject.setValue("Q_Amendment_Data_FIN_"+control[counter],formObject.getValue("Q_Amendment_Data_PT_"+control[counter]).toString());

							if("TRANSACTION_AMOUNT".equalsIgnoreCase(control[counter])||"EXPIRY_DATE".equalsIgnoreCase(control[counter])){
								formObject.setValue("Q_Amendment_Data_USER_"+control[counter],formObject.getValue("Q_Amendment_Data_PT_"+control[counter]).toString());
							}

						} else if (!"".equalsIgnoreCase(formObject.getValue("Q_Amendment_Data_FC_"+control[counter]).toString()) && null != (formObject.getValue("Q_Amendment_Data_FC_"+control[counter]).toString()) ){
							log.info("3"+control[counter]);
							formObject.setValue("Q_Amendment_Data_FIN_"+control[counter],formObject.getValue("Q_Amendment_Data_FC_"+control[counter]).toString());
						}
						//PRODTRADE_3_119  
						if("TRANSACTION_AMOUNT".equalsIgnoreCase(control[counter])){
							formObject.setValue(NEW_TRN_AMT,formObject.getValue("Q_Amendment_Data_FIN_"+control[counter]).toString());
						}else if("EXPIRY_DATE".equalsIgnoreCase(control[counter])){
							formObject.setValue(NEW_EXP_DATE,formObject.getValue("Q_Amendment_Data_FIN_"+control[counter]).toString());
						}
						//PRODTRADE_3_119 
					}
				}
				//Swift condition
			}else{ //sheenu
				for (int counter = 0; counter < control.length; counter++ ) {
					String showValues = "LABEL_"+control[counter]+"#"+"Q_Amendment_Data_USER_"+control[counter]+"#"+"Q_Amendment_Data_PT_"+control[counter]
							+"#"+"Q_Amendment_Data_FC_"+control[counter]+"#"+"Q_Amendment_Data_FIN_"+control[counter];						 
					hideshowFrmInputTab(showValues,true);
				}
			}
			//sheenu}
			log.info("setAmendFrameData ends");
		} catch (Exception e) {
			log.info("exception in saveDecHistory: " + e, e);
		}


	}

	public boolean validateAmendExpiryTxnAmount(String sFieldName, String alertMsg){
		String amndType = formObject.getValue(AMEND_TYPE).toString() ;
		if(sFieldName.equalsIgnoreCase(Q_AMENDMENT_DATA_USER_TRANSACTION_AMOUNT)){
			if ("ILC_IV".equalsIgnoreCase(amndType) || "ILC_DV".equalsIgnoreCase(amndType) //US115
					|| "ILC_CT".equalsIgnoreCase(amndType)) { 
				if(validateMandatoryFieldsPPM(sFieldName, alertMsg)){//US115	
				}else{
					return false;
				}
			} 
		}else if("ILC_CT".equalsIgnoreCase(amndType) || "ILC_EED".equalsIgnoreCase(amndType)//US115
				|| "ILC_CED".equalsIgnoreCase(amndType)){	
			if(validateMandatoryFieldsPPM(sFieldName, alertMsg)){//US115	
			}else{
				return false;
			}
		}
		return true;

	}

	private void insertIntoNotificationTxn() {
		try{
			log.info("inside insertIntoNotificationTxn");
			String proTradeRef = "";
			String prodCode = "";
			String status = "";
			String remarks = "";
			String remarks1 = "";
			String remarks2 = "";
			String remarks3 = "";
			List<List<String>> lresultSet=null;
			List<List<String>> lresultSet1=null;
			String decision = formObject.getValue(DEC_CODE).toString();
			String sQuery="select is_rm_ppm , IS_CR_PPM, is_legal_ppm,IS_REF_PPM,IS_CB_PPM,IS_TRADE_PPM, "
					+ "PRO_TRADE_REF_NO, PRODUCT_TYPE "
					+ "from ext_tfo "
					+ " WHERE WI_NAME='"+this.sWorkitemID+"'";
			String query1="SELECT count(1) FROM TFO_DJ_TSLM_DOCUMENT_REVIEW  WHERE WI_NAME ='"+this.sWorkitemID+"'"
					+" and refcode='Customer Only Through Email' ";

			log.info("setFlag sQuery="+sQuery);
			lresultSet =  formObject.getDataFromDB(sQuery);
			lresultSet1 = formObject.getDataFromDB(query1);
			log.info("setFlag output="+lresultSet);
			log.info("setFlag output="+lresultSet1);
			if(lresultSet!=null){
				log.info("in setFlag lresultSet=");
				if(!lresultSet.isEmpty() ){ 
					log.info("in setFlag lresultSet.size");
					String isRM=lresultSet.get(0).get(0);
					String isCR=lresultSet.get(0).get(1);
					String isLegal=lresultSet.get(0).get(2);
					String isREF=lresultSet.get(0).get(3);
					String isCB=lresultSet.get(0).get(4);
					String isTrade=lresultSet.get(0).get(5);
					proTradeRef = lresultSet.get(0).get(6);
					prodCode = lresultSet.get(0).get(7);
					log.info("isRM="+isRM+"isCR="+isCR+"isLegal="+isLegal+"isREF="+isREF+"isCB="+isCB+"isTrade="+isTrade);
					if("Y".equalsIgnoreCase(isCR)&& "REF".equalsIgnoreCase(decision) 
							&& "0".equalsIgnoreCase(lresultSet1.get(0).get(0))){ // not for customer refer through ){
						status = "Send for modification";
						String sReferTo = "Customer";
						String rmrksQuery = "Select EXCP_RMRKS FROM TFO_DJ_REF_SIG_ACC "
								+ "where wi_name ='"+this.sWorkitemID+"' and FLAG_DEL='N' "
								+ "and refer_to = '"+sReferTo+"' UNION "+
								"Select EXCP_RMRKS from tfo_dj_ref_doc_rvw "
								+ "where wi_name ='"+this.sWorkitemID+"'  and FLAG_DEL='N' "
								+ "and refer_to = '"+sReferTo+"' UNION "+
								"Select EXCP_RMRKS from tfo_dj_ref_txt_vet "
								+ "where wi_name ='"+this.sWorkitemID+"'  and FLAG_DEL='N' "
								+ "and refer_to = '"+sReferTo+"' UNION "+
								"Select EXCP_RMRKS from tfo_dj_ref_lmt_crdt where "
								+ "wi_name ='"+this.sWorkitemID+"'  and FLAG_DEL='N' "
								+ "and refer_to = '"+sReferTo+"'  UNION "+
								" SELECT USERCMNT as EXCP_RMRKS FROM TFO_DJ_TSLM_DOCUMENT_REVIEW WHERE"
								+ " WI_NAME='"+ this.sWorkitemID + "' and FLAG_DEL='N' "
								+ " and refcode = '"+sReferTo+"' UNION "
								+ "SELECT USERCMNT as EXCP_RMRKS FROM TFO_DJ_TSLM_REFERRAL_DETAIL WHERE"
								+ " WI_NAME='"+ this.sWorkitemID + "' and FLAG_DEL='N' "
								+ "and refcode = '"+sReferTo+"'UNION "
								+ "SELECT USERCMNT as EXCP_RMRKS FROM TFO_DJ_TSLM_LIMIT_CREDIT_REVIEW WHERE"
								+ " WI_NAME='"+ this.sWorkitemID + "' and FLAG_DEL='N' and refcode = '"+sReferTo+"'";
						
						log.info("rmrksQuery: "+rmrksQuery);
						List<List<String>> remarksList =  formObject.getDataFromDB(rmrksQuery);
						log.info("remarksList: "+remarksList);
						log.info("remarksList.toString(): "+remarksList.toString());
						if(remarksList != null && !remarksList.isEmpty()) {
//							for(int i=0;i<remarksList.size();i++){
//								if(!"".equalsIgnoreCase(remarksList.get(i).get(0))){
							log.info("remarks: "+remarksList.get(0).get(0));
							 remarks = String.join("-", new CharSequence[] { remarksList.toString() });
								log.info("remarksList remarks: "+remarks);
							
						}
					}else if((("N".equalsIgnoreCase(isCR)||("Y".equalsIgnoreCase(isCR)&& !"0".equalsIgnoreCase(lresultSet1.get(0).get(0)))) //customer only through is present ad any other referrral is also present
							&& ("Y".equalsIgnoreCase(isTrade) || "Y".equalsIgnoreCase(isCB)
									|| "Y".equalsIgnoreCase(isLegal) || "Y".equalsIgnoreCase(isREF)
									|| "Y".equalsIgnoreCase(isRM))) && "REF".equalsIgnoreCase(decision)) {
						status = "Pending Approval";
					}else if("REJ".equalsIgnoreCase(decision)){
						status = "Transaction Declined";
						remarks=formObject.getValue(REJ_RESN_PPM).toString();
					}else if("APP".equalsIgnoreCase(decision)){
						status = "Transaction under review";
					}
					/*if("Y".equalsIgnoreCase(isCR) 
							&& "N".equalsIgnoreCase(isTrade) && "N".equalsIgnoreCase(isCB)
							&& "N".equalsIgnoreCase(isLegal) && "N".equalsIgnoreCase(isREF)
							&& "N".equalsIgnoreCase(isRM)
							&& "REF".equalsIgnoreCase(DEC_CODE)) {
						status = "Send for modification";
						//remarks = formObject.getValue(REMARKS).toString();
						String sReferTo = "Customer";
						String rmrksQuery = "Select EXCP_RMRKS FROM TFO_DJ_REF_SIG_ACC "
								+ "where wi_name ='"+this.sWorkitemID+"' and FLAG_DEL='N' "
										+ "and refer_to = '"+sReferTo+"' UNION "+
								"Select EXCP_RMRKS from tfo_dj_ref_doc_rvw "
								+ "where wi_name ='"+this.sWorkitemID+"'  and FLAG_DEL='N' "
										+ "and refer_to = '"+sReferTo+"' UNION "+
								"Select EXCP_RMRKS from tfo_dj_ref_txt_vet "
								+ "where wi_name ='"+this.sWorkitemID+"'  and FLAG_DEL='N' "
										+ "and refer_to = '"+sReferTo+"' UNION "+
								"Select EXCP_RMRKS from tfo_dj_ref_lmt_crdt where "
								+ "wi_name ='"+this.sWorkitemID+"'  and FLAG_DEL='N' "
										+ "and refer_to = '"+sReferTo+"'"
								+ "UNION SELECT EXCP_REMARKS "
								+ "FROM TFO_DJ_FINAL_DEC_SUMMARY WHERE WI_NAME ='"+this.sWorkitemID+"' "
										+ "AND UPPER(REFFERD_TO) IN ('COMPLIANCE','TREASURY')"; 
						log.info("rmrksQuery: "+rmrksQuery);
						List<List<String>> remarksList =  formObject.getDataFromDB(rmrksQuery);
						log.info("remarksList: "+remarksList);
						if(remarksList != null && !remarksList.isEmpty()) {
							log.info("remarks: "+remarksList.get(0).get(0));
							remarks = remarksList.get(0).get(0);
						}
					} else if("Y".equalsIgnoreCase(isCR) 
							&& ("Y".equalsIgnoreCase(isTrade) || "Y".equalsIgnoreCase(isCB)
							|| "Y".equalsIgnoreCase(isLegal) || "Y".equalsIgnoreCase(isREF)
							|| "Y".equalsIgnoreCase(isRM))
							&& "REF".equalsIgnoreCase(DEC_CODE)) {
						status = "Send for modification";
					} else if("N".equalsIgnoreCase(isCR) 
							&& ("Y".equalsIgnoreCase(isTrade) || "Y".equalsIgnoreCase(isCB)
							|| "Y".equalsIgnoreCase(isLegal) || "Y".equalsIgnoreCase(isREF)
							|| "Y".equalsIgnoreCase(isRM))) {
						//status = "Internal approvals";
						if("REF".equalsIgnoreCase(DEC_CODE)){
							status = "Pending Approval";
						}else if("REJ".equalsIgnoreCase(DEC_CODE)){
							status = "Transaction Declined";
						}else if("APP".equalsIgnoreCase(DEC_CODE)){
							status = "Transaction under review";
						}
					  }
					 */
					log.info("status="+status);
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

	private boolean checkReferralCountPPM() {
		log.info("In checkReferralCountPPM ");
		String sQuery = "";
		List<List<String>> sOutput;
		try {
			String sRMFlag=formObject.getValue(IS_RM_PPM).toString();
			String sTSDFlag=formObject.getValue(IS_REF_PPM).toString(); 
			String sLEGALFlag=formObject.getValue(IS_LEGAL_PPM).toString();
			String sCRFlag=formObject.getValue(IS_CR_PPM).toString();
			String sCBFlag=formObject.getValue(IS_CB_PPM).toString();
			String sTradeFlag=formObject.getValue(IS_TRADE_PPM).toString();
			log.info("IS_RM_PPM="+sRMFlag+IS_REF_PPM+sTSDFlag+IS_LEGAL_PPM+sLEGALFlag+
					IS_CR_PPM+sCRFlag+IS_CB_PPM+sCBFlag+IS_TRADE_PPM+sTradeFlag);

			if("Y".equalsIgnoreCase(sRMFlag) || "Y".equalsIgnoreCase(sTSDFlag) 
					|| "Y".equalsIgnoreCase(sLEGALFlag) || "Y".equalsIgnoreCase(sCRFlag) 
					|| "Y".equalsIgnoreCase(sCBFlag)|| "Y".equalsIgnoreCase(sTradeFlag)){
				sQuery = "SELECT COUNT(1) AS COUNT FROM TFO_DJ_FINAL_DEC_SUMMARY "
						+ "WHERE WI_NAME  = '"+sWorkitemID+"' ";
				log.info("sQuery : "+sQuery);
				sOutput = formObject.getDataFromDB(sQuery);
				log.info("sOutput : "+sOutput);
				if(sOutput != null && sOutput.size()>0){
					int count = Integer.parseInt(sOutput.get(0).get(0));
					if (count > 0){
						return true;
					} else {
						sendMessageHashMap("", "Please add referral again");
						return false;		
					}
				}
			} else {
				sendMessageHashMap("", "Please add referral again");
				return false;
			}
		}
		catch (Exception e) {
			log.error("Exception: ",e);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public String populateTSLMCompanyDetails(){	//BY KISHAN TSLM
		log.info("INSIDE populateTSLMCompanyDetails FUNCTION" );
		if(validateBtnFetchTSLM()){
			String SCUSTID    = formObject.getValue(CUSTOMER_ID).toString();
			String pCode      = formObject.getValue(PRODUCT_TYPE).toString();
			String stdLoanFlg = formObject.getValue(STANDALONE_LOAN).toString();
			String loanCcy    = formObject.getValue(INF_LOAN_CURR).toString();
			String compyType  = formObject.getValue(TSLM_COMPANY_TYPE).toString();

			log.info("Values ::"+ SCUSTID+" :: " +pCode+" :: " +stdLoanFlg+" :: " +loanCcy+" :: " + compyType);
			String sOperationName = "fetchTSLMCompanyDtl_Oper";
			try{
				String sOutputXML = FetchTSLMCompanyDetailsOutputXML(SCUSTID,pCode,stdLoanFlg,loanCcy,
						compyType,sOperationName);
				log.info("sOutputXML ::"+ sOutputXML);
				XMLParser xmlparser = new XMLParser(sOutputXML);
				String returnTypeCheck = xmlparser.getValueOf("returnCode");
				if (returnTypeCheck.equalsIgnoreCase("0")) {
					String oCompType = xmlparser.getValueOf("companyType");
					String oPassDueLoan = xmlparser.getValueOf("pastDueLoan");
					String oSpecialIntrs = xmlparser.getValueOf("specialInstructions").trim()+ "','";
					if (!oCompType.equalsIgnoreCase("")) {
						if (oCompType.equalsIgnoreCase("BUYER")) {
							formObject.setValue(TSLM_COMPANY_TYPE, "B");
						} else if (oCompType.equalsIgnoreCase("SELLER")) {
							formObject.setValue(TSLM_COMPANY_TYPE, "S");
						} else {
							formObject.setValue(TSLM_COMPANY_TYPE, "BT");
						}
					}
					formObject.setValue("TSLM_ANY_PAST_DUE_CID",oPassDueLoan.equalsIgnoreCase("N") ? "2" : "1");
					int specialIntrsCount = xmlparser.getNoOfFields("specialInstructions");
					JSONArray jsonArraySpeclIntrs = new JSONArray();
					String specialIntrsData = "";
					XMLParser innerXmlParser = new XMLParser();
					for (int i = 0; i < specialIntrsCount; i++) {
						JSONObject jsonObject = new JSONObject();
						specialIntrsData = xmlparser.getNextValueOf("specialInstructions");
						log.info("specialInstructionsData " + specialIntrsData);
						innerXmlParser.setInputXML(specialIntrsData);
						log.info("InnerXmlParser " + innerXmlParser.toString());
						log.info("LABEL " + innerXmlParser.getValueOf("label"));
						log.info("MESSAGE " + innerXmlParser.getValueOf("message"));
						jsonObject.put("TSLM Spc Inst Lab",innerXmlParser.getValueOf("label"));
						jsonObject.put("TSLM Spc Inst",innerXmlParser.getValueOf("message"));
						jsonArraySpeclIntrs.add(jsonObject);

					}
					formObject.clearTable("Q_TSLM_Cust_Inst");
					log.info(" 1 gridData " + jsonArraySpeclIntrs.size());
					formObject.addDataToGrid("Q_TSLM_Cust_Inst",jsonArraySpeclIntrs);

					int counterPartyCount = xmlparser.getNoOfFields("counterPartyList");
					JSONArray jsonArrayCounterParty = new JSONArray();
					String counterPartyList = "";
					for (int i = 0; i < counterPartyCount; i++) {
						JSONObject jsonObjectCounter = new JSONObject();
						counterPartyList = xmlparser.getNextValueOf("counterPartyList");
						log.info("counterPartyListData " + counterPartyList);
						innerXmlParser.setInputXML(counterPartyList);
						log.info("innerXmlParser " + innerXmlParser.toString());
						jsonObjectCounter.put("Counter Party CID",innerXmlParser.getValueOf("cid"));
						jsonObjectCounter.put("Counter Party Name",innerXmlParser.getValueOf("countryPartyName"));
						jsonObjectCounter.put("Counter Party Country",innerXmlParser.getValueOf("country"));
						jsonObjectCounter.put("Notice of Acknowledgement",innerXmlParser.getValueOf("noa"));
						jsonArrayCounterParty.add(jsonObjectCounter);
					}
					formObject.clearTable("Q_TSLm_Counter_Dets");
					log.info(" 2 gridData " + jsonArrayCounterParty.size());
					formObject.addDataToGrid("Q_TSLm_Counter_Dets",jsonArrayCounterParty);

					/*String frontInsertQuery = "Insert into TFO_DJ_LMT_CRDT_RECORDS (WI_NAME,TXN_REF_NO,LT_DOC_RVW_GDLINES,"
							+ "LT_RESPONSE,INSERTIONORDERID,ITEMINDEX)";

					int check1 = formObject.saveDataInDB(frontInsertQuery + "values('"+sWorkitemID+"',null,"
							+ "'Please select Collateral details.',"+ "'"+collateral+"',1,null)");

					int check2 = formObject.saveDataInDB(frontInsertQuery + " values ('"+sWorkitemID+"',null,"
							+ "'Please input Cash Margin percentage.','"+cashMarginPercentage+"',2,null)");

					int check3 = formObject.saveDataInDB(frontInsertQuery + " values ('"+sWorkitemID+"',null,"
							+ "'Please specify EBOR/LIBOR rate details','"+rateIndex+"',3,null)");

					int check4 = formObject.saveDataInDB(frontInsertQuery + " values ('"+sWorkitemID+"',null,"
							+ "'Please specify applicable Spread','"+spreadRate+"',4,null)");

					int check5 = formObject.saveDataInDB(frontInsertQuery + " values ('"+sWorkitemID+"',null,"
							+ "'Please specify the Floor Rate','"+floorBaseRate+"',5,null)");

					log.info(check1 + " :: " + check2 + " :: " + check3 + " :: " + check4 + " :: " + check5 + " :: ");*/

					//formObject.setStyle(BTN_FETCH_TSLM_CID_DETAILS,DISABLE,TRUE);
					String sQuery1 = "SELECT TSLM_FETCH_BTN_FLAG FROM EXT_TFO WHERE WI_NAME = '"+sWorkitemID+"'";
					List<List<String>> sOutputlist = formObject.getDataFromDB(sQuery1);
					String flag = sOutputlist.get(0).get(0);
					if(!flag.equalsIgnoreCase("T")){

						//Limit Credit Checklist data
						//						String cashMarginPercentage= xmlparser.getValueOf("cashMarginPercentage");
						//						String collateral= xmlparser.getValueOf("collateral");
						//						String floorBaseRate= xmlparser.getValueOf("floorBaseRate");
						//						String rateIndex= xmlparser.getValueOf("rateIndex");
						//						String spreadRate= xmlparser.getValueOf("spreadRate");

						JSONArray jsonArrayLimitCredit = new JSONArray();

						JSONObject jsonLimitCounter1 = new JSONObject();
						jsonLimitCounter1.put("guideline","Please input Cash Margin percentage.");
						jsonLimitCounter1.put("response",xmlparser.getValueOf("cashMarginPercentage"));
						jsonArrayLimitCredit.add(jsonLimitCounter1);

						JSONObject jsonLimitCounter2 = new JSONObject();
						jsonLimitCounter2.put("guideline","Please select Collateral details.");
						jsonLimitCounter2.put("response",xmlparser.getValueOf("collateral"));
						jsonArrayLimitCredit.add(jsonLimitCounter2);

						JSONObject jsonLimitCounter3 = new JSONObject();
						jsonLimitCounter3.put("guideline","Please specify the Floor Rate");
						jsonLimitCounter3.put("response",xmlparser.getValueOf("floorBaseRate"));
						jsonArrayLimitCredit.add(jsonLimitCounter3);

						JSONObject jsonLimitCounter4 = new JSONObject();
						jsonLimitCounter4.put("guideline","Please specify EBOR/LIBOR rate details");
						jsonLimitCounter4.put("response",xmlparser.getValueOf("rateIndex"));
						jsonArrayLimitCredit.add(jsonLimitCounter4);

						JSONObject jsonLimitCounter5 = new JSONObject();
						jsonLimitCounter5.put("guideline","Please specify applicable Spread");
						jsonLimitCounter5.put("response",xmlparser.getValueOf("spreadRate"));
						jsonArrayLimitCredit.add(jsonLimitCounter5);

						log.info(" 1 jsonLimitCounter" + jsonLimitCounter5.toJSONString());
						log.info(" 2 jsonLimitCounter" + jsonLimitCounter5.toString());

						//formObject.clearTable("Qvar_Lmt_Crdt_ID");
						log.info(" 3 gridData " + jsonArrayLimitCredit.size());
						formObject.addDataToGrid("Qvar_Lmt_Crdt_ID",jsonArrayLimitCredit);


						String sQuery2 = "UPDATE EXT_TFO SET TSLM_FETCH_BTN_FLAG = 'T' WHERE WI_NAME = '"+sWorkitemID+"'";
						int mainCode = formObject.saveDataInDB(sQuery2);
						log.info("Inside populateTSLMCompanyDetails TSLM FETCH BUTTON PRESSED mainCode: "+mainCode);
					}
				}
				else {
					sendMessageHashMap(BTN_FETCH_TSLM_CID_DETAILS, "Unable to fetch CID details from TSLMS: ModTSLMCorpLoanDetails-101807-Failure ");
					log.info("Inside populateTSLMCompanyDetails TSLM FETCH BUTTON PRESSED Data not fetched ");
				}

				//formObject.setValue(arg0, arg1);
				/*int sCount = xmlparser.getNoOfFields("feeList");
				for(int i=0;i<sCount;i++){
					String partyType="'"+xmlparser.getValueOf("calculationBase").trim()+"'";
					String calculationType=",'"+xmlparser.getValueOf("calculationType").trim()+"'";
					String feeCcy=",'"+xmlparser.getValueOf("feeCcy").trim()+"'";
					String feeType=",'"+xmlparser.getValueOf("feeType").trim()+"','";
					String feeCode=",'"+xmlparser.getValueOf("feeCode").trim()+"','";
					String txnCurrency=",'"+xmlparser.getValueOf("txnCurrency").trim()+"','";			
					String insertionId=getInsertIonIdForTable("TFO_DJ_CONTRACT_LIMITS_DETAILS")+"') ";

					finalInsert.append(query1).append(partyType).append(serialNumber).append(operation).append(customerNumber).append(limitType)
					.append(linkageReferenceNumber).append(limitPercentContribution).append(amountTag).append(winame).append(insertionId);
				}*/
				log.info("END OF  populateTSLMCompanyDetails FUNCTION" );
				//Changes for SCF Date 10-11-2023  for Jira Ticket -ATP -199 ,ATP -179 
				//START CODE 
				//ATP-463 16-MAY-2024 --JAMSHED START
				/*
				if("SCF".equalsIgnoreCase(requestCategory) && "PD".equalsIgnoreCase(requestType)) {
					fetchPastDueLiability();
				} else {
				//CODE END
				fetchPastDueDetails();
				}
				*/
				//ATP-463 16-MAY-2024 --JAMSHED END
			}catch(Exception e){
				log.info(e.toString());
			}
			return getReturnMessage(true, "","");
		}else{
			log.info("CONDITION NOT SATISFIED");
			return getReturnMessage(false, "","");
		}
	}

	private void fetchPastDueDetails(){
		//FCUBS Past Due details populate
		try {	//CODE BY MOKSH
			log.info("FCUBS Past Due details populate");
			String customerID = formObject.getValue(CUSTOMER_ID).toString();
			String sOperationName = "fetchTradePastDueDtls_Oper";
			String sOutputXML1 = fetchFCUBSPastDueFlag(customerID,sOperationName);
			log.info("sOutputXML ::"+ sOutputXML1);
			XMLParser xmlparser1 = new XMLParser(sOutputXML1);
			String returnTypeCheck1 = xmlparser1.getValueOf("pastDueFlag");
			String returnCode = xmlparser1.getValueOf("returnCode");
			log.info("FCUBS Past Due details populate returnTypeCheck1: "+ returnTypeCheck1);

			if (returnCode.equalsIgnoreCase("0")) {
				if (returnTypeCheck1.equalsIgnoreCase("Y")) {
					log.info("FCUBS Past Due details populate returnTypeCheck1: Y");
					formObject.setValue(FCUBS_ANY_PAST_DUE_CID, "1");
				} else {
					log.info("FCUBS Past Due details populate returnTypeCheck1: N");
					formObject.setValue(FCUBS_ANY_PAST_DUE_CID, "2");
				}
			}
			else{ 
				log.info("FCUBS Past Due details populate: Unable to fetch FCUBS Past Due Data");
				sendMessageHashMap(FCUBS_ANY_PAST_DUE_CID,"Unable to fetch FCUBS Past Due Data");
				formObject.setValue(FCUBS_ANY_PAST_DUE_CID, "");
			}
		} catch (Exception e) {
			log.info("FCUBS Past Due details populate error : "+ e);
		}
	}
	private boolean validateBtnFetchTSLM(){  //BY KISHAN TSLM
		String loanAlertType = "Loan";
		if ("SCF".equalsIgnoreCase(requestCategory)) {  /// ALERT CONDITION ADDED BY SHIVANSHU FOR SCF ATP - 200
			loanAlertType = "Discount";
		}
		if(formObject.getValue(STANDALONE_LOAN).toString().equalsIgnoreCase("")){
			sendMessageHashMap(STANDALONE_LOAN, "Please Fill Standalone Loan");
			return false;
		}else if(formObject.getValue(CUSTOMER_ID).toString().equalsIgnoreCase("")){
			sendMessageHashMap(CUSTOMER_ID, "Please Fill Customer ID");
			return false;
		}else if(formObject.getValue(PRODUCT_TYPE).toString().equalsIgnoreCase("")){
			sendMessageHashMap(PRODUCT_TYPE, "Please Fill Product Type");
			return false;
		}else if(formObject.getValue(TSLM_COMPANY_TYPE).toString().equalsIgnoreCase("")){
			sendMessageHashMap(TSLM_COMPANY_TYPE, "Please Fill Company Type");
			return false;
		}else if(formObject.getValue(INF_LOAN_CURR).toString().equalsIgnoreCase("")){
			sendMessageHashMap(INF_LOAN_CURR, "Please Fill "+loanAlertType+" Currency");//ATP-200
			return false;
		}
		return true;
	}

	public void setTenorBaseComboTSLM(){	//CODE BY MOKSH
		log.info("Inside setTenorBaseComboTSLM");
		log.info("setTenorBaseComboTSLM req cat: "+requestCategory);
		String tenorValue = formObject.getValue(INF_TENOR_BASE).toString();
		if ("IFS".equalsIgnoreCase(requestCategory) || "IFP".equalsIgnoreCase(requestCategory)
				||"IFA".equalsIgnoreCase(requestCategory)) {
			String flag = formObject.getValue(PROCESSING_SYSTEM).toString();
			String sQuery = "SELECT TENOR_BASE_KEY, TENOR_BASE_DESC FROM TFO_DJ_LN_TNR_MAST "
					+ "WHERE (PROCESSING_SYSTEM = '"
					+ flag+ "' OR PROCESSING_SYSTEM = 'Both') AND"+ "(REQ_CAT_KEY = '"
					+ requestCategory+ "') ORDER BY 1 ";
			List<List<String>> sOutputlist = formObject.getDataFromDB(sQuery);
			log.info("Inside setTenorBaseComboTSLM sQuery: " + sQuery);
			formObject.clearCombo(INF_TENOR_BASE);
			for (int i = 0; i < sOutputlist.size(); i++) {
				formObject.addItemInCombo(INF_TENOR_BASE, sOutputlist
						.get(i).get(1).toString(), sOutputlist.get(i).get(0).toString());
			}
			if(!tenorValue.isEmpty()){
				formObject.setValue(INF_TENOR_BASE,tenorValue);
			}
		}
	}

	public boolean validateTSLMFetchButton(){	//CODE BY MOKSH
		String processType=formObject.getValue(PROCESS_TYPE).toString();
		log.info("signatureAccBalTabVerification: " + processType);
		if (("IFS".equalsIgnoreCase(requestCategory) || "IFP".equalsIgnoreCase(requestCategory)
				||"IFA".equalsIgnoreCase(requestCategory)  || "SCF".equalsIgnoreCase(requestCategory))&&
				("LD".equalsIgnoreCase(requestType) || "IFA_CTP".equalsIgnoreCase(requestType)  //ATP - 199
						|| "PD".equalsIgnoreCase(requestType) || "PDD".equalsIgnoreCase(requestType))
				&& ("T".equalsIgnoreCase(formObject.getValue(PROCESSING_SYSTEM).toString()))
				&& !processType.equalsIgnoreCase("TSLM Front End")) {
			String sQuery = "SELECT TSLM_FETCH_BTN_FLAG FROM EXT_TFO WHERE WI_NAME = '"+ sWorkitemID + "'";
			List<List<String>> sOutputlist = formObject.getDataFromDB(sQuery);
			String flag = sOutputlist.get(0).get(0);  
			if (!flag.equalsIgnoreCase("T")) {
				sendMessageHashMap(BTN_FETCH_TSLM_CID_DETAILS,"Please click TSLM Fetch CID Details Button");
				return false;
			}
		}
		return true;
	}

	private String fetchFCUBSPastDueFlag(String customerID,String sOperationName) {
		log.info("inside fetchFCUBSPastDueFlag..");
		String sInput = "";
		String sOutput="";
		List<List<String>> sOutputlist = formObject.getDataFromDB("SELECT SEQ_WEBSERVICE.nextval as IDValue from DUAL ");
		if (!sOutputlist.isEmpty() && !sOutputlist.get(0).isEmpty()) {
			sInput = getFCUBSPastDueInputXML(sOutputlist.get(0).get(0),customerID, sOperationName);
			sOutput = socket.connectToSocket(sInput);
			log.info("inside fetchFCUBSPastDueFlag.. sOutput: "+sOutput);
		}
		return sOutput;
	}

	private String getFCUBSPastDueInputXML(String sSeqNo,String customerID, String sOperationName) {
		log.info("inside getFCUBSPastDueInputXML.. ");
		String sInputXML = "<?xml version=\"1.0\"?><APWebService_Input><Option>WebService</Option><Calltype>WS-2.0"
				+ "</Calltype><InnerCalltype>FCUBSTradeContractOps</InnerCalltype><Customer>"
				+ "<refNo>"
				+ sSeqNo
				+ "</refNo>"
				+ "<customerID>"
				+ customerID
				+ "</customerID>"
				+ "<serviceName>InqCustomerTradeJourney</serviceName>"
				+ "<senderID>WMS</senderID>"
				+ "<WiName>"
				+ this.sWorkitemID
				+ "</WiName>"
				+ "<operationType>"
				+ sOperationName
				+ "</operationType>" + "</Customer>";
		log.info("input XML: "+sInputXML);
		sInputXML+="<EngineName>" + engineName + "</EngineName>"
				+ "<SessionId>" + sessionId + "</SessionId>"
				+ "</APWebService_Input>";
		log.info("inside getFCUBSPastDueInputXML.. sInputXML:");
		log.info("input XML: "+sInputXML);
		return sInputXML;
	}

	//added by santhosh 
	public void ppmFieldFrmOnLoad_SBLC(String reqTpe){
		if ("SBLC_NI".equalsIgnoreCase(reqTpe)) {
			enableFieldOnDemand(PRODUCT_TYPE);
			disableFieldOnDemand("AMEND_TYPE####NEW_TRN_AMT####NEW_EXP_DATE");
		} else if ("SBLC_AM".equalsIgnoreCase(reqTpe)) {
			enableFieldOnDemand("AMEND_TYPE####NEW_TRN_AMT####NEW_EXP_DATE");
			disableFieldOnDemand("PRODUCT_TYPE####EXP_DATE");
			hideshowFrmInputTab(GRNT_INPUT_HIDE,false);//RR FOR GRNT INPUT FIELDS HIDE
		} else if (!("SBLC_AM".equalsIgnoreCase(reqTpe) || "SBLC_NI".equalsIgnoreCase(reqTpe))) {
			disableFieldOnDemand("PRODUCT_TYPE####AMEND_TYPE####EXP_DATE");
		}
		disableFieldOnDemand("GRNT_CHRG_ACC_TITLE####GRNT_CHRG_ACC_CRNCY");
		ppmNewFieldValidate(formObject.getValue(AMEND_TYPE).toString());

	}
	// ELC added by mansi
	public void ppmFieldFrmOnLoad_ELC(String reqTpe){
		if ("ELC_SLCA".equalsIgnoreCase(reqTpe)) {
			enableFieldOnDemand(PRODUCT_TYPE);
			disableFieldOnDemand("AMEND_TYPE####NEW_TRN_AMT####NEW_EXP_DATE");
		} else if ("ELC_SLCAA".equalsIgnoreCase(reqTpe)) {
			enableFieldOnDemand("AMEND_TYPE####NEW_TRN_AMT####NEW_EXP_DATE");
			disableFieldOnDemand("PRODUCT_TYPE####EXP_DATE");
			hideshowFrmInputTab(GRNT_INPUT_HIDE,false);//RR FOR GRNT INPUT FIELDS HIDE
		} else if (!("ELC_SLCAA".equalsIgnoreCase(reqTpe) || "ELC_SLCA".equalsIgnoreCase(reqTpe))) {
			disableFieldOnDemand("PRODUCT_TYPE####AMEND_TYPE####EXP_DATE");
		}
		disableFieldOnDemand("GRNT_CHRG_ACC_TITLE####GRNT_CHRG_ACC_CRNCY");
		ppmNewFieldValidate(formObject.getValue(AMEND_TYPE).toString());

	}
	//added by santhosh
	protected boolean ppmValidateInputGTEE_SBLC(String reqTpe){

		enableDisableMultipleBtnControl(LIST_VIEW_BUTTONS3,true);
		enableDisableMultipleBtnControl(BUTTON_SUBMIT,false); 
		if ("SBLC_NI".equalsIgnoreCase(reqTpe)) {
			return validateMandatoryFieldsPPM(PRODUCT_TYPE, "Please Select Product Code.")
					&& validateMandatoryFieldsPPM(TRN_TENOR, "Please Select Expiry Type.")
					&& ppmAssociatedControlCheck_SBLC(reqTpe, TRN_TENOR, EXP_DATE, "Please Enter Expiry Date.")
					&& validateMandatoryFieldsPPM(ACCOUNT_VALID, "Please Select Valid Account No.")
					&& validateMandatoryFieldsPPM(TRN_THIRD_PARTY, "Please Select Third Party Transaction.")
					&& ppmAssociatedControlCheck_SBLC(formObject.getValue(SOURCE_CHANNEL).toString(),formObject.getValue(ACCOUNT_VALID).toString(), ACCOUNT_NUMBER, "Please Fetch Account Number.")
					&& validatAcChrgTitle(formObject.getValue(ACCOUNT_VALID).toString())
					//&& ppmAssociatedControlCheck_SBLC("SBLC", TRN_TENOR, FIELD_GRNTEE_EXP_DATE, "Please Select Counter GTE Expiry Date.")
					&& validateMandatoryNewExpiryTXNAmt()
					&& validateMandatoryExpiryCond()
					&& validateMandatoryFieldsPPM(PURPOSE_OF_MESSAGE, "Please Select Purpose of Message.")
					&& validateMandatoryFieldsPPM(LC_DOC_COURIER, "Please Select Any Document to be couriered. ")
					&& sblcNIDocCourierMandatory("REQ_CONF_PARTY", "Please Select Requested Confirmation Party. ");//

		} 
		else if ("SBLC_AM".equalsIgnoreCase(reqTpe)) { 
			return validateMandatoryFieldsPPM(PRODUCT_TYPE, "Please Select Product Code.")
					//&& validateMandatoryFieldsPPM(TRN_TENOR, "Please Select Expiry Type.")
					&& validateMandatoryFieldsPPM(AMEND_TYPE, "Please Select Amendment Type.")
					&& validateMandatoryFieldsPPM(ACCOUNT_VALID, "Please Select Valid Account No.")
					&& validateMandatoryFieldsPPM(TRN_THIRD_PARTY, "Please Select Third Party Transaction.")
					&& ppmAssociatedControlCheck_SBLC(formObject.getValue(SOURCE_CHANNEL).toString(),formObject.getValue(ACCOUNT_VALID).toString(), ACCOUNT_NUMBER, "Please Fetch Account Number.")
					&& validatAcChrgTitle(formObject.getValue(ACCOUNT_VALID).toString())
					&& validateMandatoryAnyDOC_SBLC_AM()
					&& validateMandatoryExpiryCond_AM();
			//&& ppmNonAssociatedControlCheck(reqTpe, formObject.getValue(AMEND_TYPE).toString(),NEW_TRN_AMT, "Please Enter New Transaction Amount.")
			//&& ppmNonAssociatedControlCheck(reqTpe, formObject.getValue(AMEND_TYPE).toString(),NEW_EXP_DATE, "Please Select New Expiry Date.")
			//&& ppmAssociatedControlCheck_SBLC("SBLC", TRN_TENOR, FIELD_GRNTEE_EXP_DATE, "Please Select Counter GTE Expiry Date.")
			//&& validateMandatoryNewExpiryTXNAmt();

		}else if ("SBLC_CR".equalsIgnoreCase(reqTpe)||"SBLC_CS".equalsIgnoreCase(reqTpe)
				|| "SBLC_ER".equalsIgnoreCase(reqTpe)) {	//RR
			return validateMandatoryFieldsPPM(PRODUCT_TYPE, "Please Select Product Code.")
					//&& validateMandatoryFieldsPPM(TRN_TENOR, "Please Select Expiry Type.")
					//&& validateMandatoryFieldsPPM(ACCOUNT_VALID, "Please Select Valid Account No.")
					//&& validateMandatoryFieldsPPM(TRN_THIRD_PARTY, "Please Select Third Party Transaction.")
					//&& ppmAssociatedControlCheck(formObject.getValue(SOURCE_CHANNEL).toString(),formObject.getValue(ACCOUNT_VALID).toString(), ACCOUNT_NUMBER, "Please Fetch Account Number.")
					&& validatAcChrgTitle(formObject.getValue(ACCOUNT_VALID).toString())
					//&& ppmAssociatedControlCheck("GRNT", TRN_TENOR, FIELD_GRNTEE_EXP_DATE, "Please Select Counter GTE Expiry Date.")
					&& validateMandatoryNewExpiryTXNAmt();
		}
		else if (!(("SBLC_AM".equalsIgnoreCase(reqTpe)) || ("SBLC_NI".equalsIgnoreCase(reqTpe)))) {
			return validateMandatoryFieldsPPM(PRODUCT_TYPE, "Please Select Product Code.")
					&& validateMandatoryFieldsPPM(TRN_TENOR, "Please Select Expiry Type.")
					&& validateMandatoryFieldsPPM(ACCOUNT_VALID, "Please Select Valid Account No.")
					&& validateMandatoryFieldsPPM(TRN_THIRD_PARTY, "Please Select Third Party Transaction.")
					&& ppmAssociatedControlCheck_SBLC(formObject.getValue(SOURCE_CHANNEL).toString(),formObject.getValue(ACCOUNT_VALID).toString(), ACCOUNT_NUMBER, "Please Fetch Account Number.")
					&& validatAcChrgTitle(formObject.getValue(ACCOUNT_VALID).toString())
					&& ppmAssociatedControlCheck_SBLC("SBLC", TRN_TENOR, FIELD_GRNTEE_EXP_DATE, "Please Select Counter GTE Expiry Date.")
					&& validateMandatoryNewExpiryTXNAmt();
		}
		return true;

	}
	//added by santhosh 
	public boolean ppmAssociatedControlCheck_SBLC(String param1, String param2, String checkControl, String msg){

		log.info("param1  >> " + param1 + " param2 " + param2 + "  checkControl " + checkControl + "  msg " + checkControl);
		if ("SBLC".equalsIgnoreCase(param1)&& "FD".equalsIgnoreCase(formObject.getValue(param2).toString())) {
			if (validateMandatoryFieldsPPM(checkControl, msg)) {
				return true;
			} else {
				return false;
			}
		}
		else if ("SBLC_NI".equalsIgnoreCase(param1) && "FD".equalsIgnoreCase(formObject.getValue(param2).toString())) {
			if (validateMandatoryFieldsPPM(checkControl, msg)) {
				return true;
			} else {
				return false;
			}
		} else if ("SBLC_NI".equalsIgnoreCase(param1) && ("OE".equalsIgnoreCase(formObject.getValue(param2).toString())||
				"COND".equalsIgnoreCase(formObject.getValue(param2).toString()))) {	//RR for conditional change
			return true;
		} else if (!(("SBLC_NI".equalsIgnoreCase(param1)) || ("SBLC_AM".equalsIgnoreCase(param1))) 
				&& "FD".equalsIgnoreCase(formObject.getValue(param2).toString())) {
			if (validateMandatoryFieldsPPM(checkControl, msg)) {
				return true;
			} else {
				return false;
			}
		} else if (!(("SBLC_NI".equalsIgnoreCase(param1)) || ("SBLC_AM".equalsIgnoreCase(param1))) && ("OE".equalsIgnoreCase(formObject.getValue(param2).toString())
				||"COND".equalsIgnoreCase(formObject.getValue(param2).toString()))) {
			return true;
		} else if (!"S".equalsIgnoreCase(param1)
				&& !("No".equalsIgnoreCase(param2) || "2".equalsIgnoreCase(param2))) {
			if (validateMandatoryFieldsPPM(checkControl, msg)) {
				return true;
			} else {
				return false;
			}
		} else if (!"S".equalsIgnoreCase(param1) && ("Yes".equalsIgnoreCase(param2) || "1".equalsIgnoreCase(param2))) {
			return validateMandatoryFieldsPPM(checkControl, msg);
		}
		return true;
	}

	private boolean PPMvalidateMandatoryPurposeMessage()  
	{
		String processType = formObject.getValue(PROCESS_TYPE).toString();
		if(("GRNT".equalsIgnoreCase(requestCategory) && "NI".equalsIgnoreCase(requestType))
				|| ("SBLC".equalsIgnoreCase(requestCategory) && "SBLC_NI".equalsIgnoreCase(requestType))
				|| ("ELC".equalsIgnoreCase(requestCategory) && "ELC_SLCA".equalsIgnoreCase(requestType))
				|| ("ILC".equalsIgnoreCase(requestCategory) && "ILC_UM".equalsIgnoreCase(requestType))
				||("GRNT".equalsIgnoreCase(requestCategory) && "GA".equalsIgnoreCase(requestType))
						&&("BAU".equalsIgnoreCase(processType)||"SWIFT".equalsIgnoreCase(processType)))
		{
			return validateMandatoryFieldsPPM(FCUBS_PUR_OF_MSG,"Please select FCUBS Purpose of Message.");
		}
		return true;
	}

	//added by Rakshita
	public boolean validateMandatoryExpiryDate()  
	{
		String expiryType = formObject.getValue(TRN_TENOR).toString();
		log.info("in validateMandatoryExpiryDate: "+expiryType);

		if("FD".equalsIgnoreCase(expiryType))
		{
			return validateMandatoryFieldsPPM(EXP_DATE, "Please Enter Expiry Date.");
		}
		return true;
	}


	// ELC added by mansi
	public boolean ppmAssociatedControlCheck_ELC(String param1, String param2, String checkControl, String msg) {

		log.info("param1  >> " + param1 + " param2 " + param2 + "  checkControl " + checkControl + "  msg " + checkControl);
		if ("ELC".equalsIgnoreCase(param1) && "FD".equalsIgnoreCase(formObject.getValue(param2).toString())) {
			if (validateMandatoryFieldsPPM(checkControl, msg)) {
				return true;
			} else {
				return false;
			}
		}
		else if ("ELC_SLCA".equalsIgnoreCase(param1) && "FD".equalsIgnoreCase(formObject.getValue(param2).toString())) {
			if (validateMandatoryFieldsPPM(checkControl, msg)) {
				return true;
			} else {
				return false;
			}
		} else if ("ELC_SLCA".equalsIgnoreCase(param1) && ("OE".equalsIgnoreCase(formObject.getValue(param2).toString())||
				"COND".equalsIgnoreCase(formObject.getValue(param2).toString()))) {
			return true;
		} else if (!(("ELC_SLCA".equalsIgnoreCase(param1)) || ("ELC_SLCAA".equalsIgnoreCase(param1))) && "FD".equalsIgnoreCase(formObject.getValue(param2).toString())) {
			if (validateMandatoryFieldsPPM(checkControl, msg)) {
				return true;
			} else {
				return false;
			}
		} else if (!(("ELC_SLCA".equalsIgnoreCase(param1)) || ("ELC_SLCAA".equalsIgnoreCase(param1))) && ("OE".equalsIgnoreCase(formObject.getValue(param2).toString())
				||"COND".equalsIgnoreCase(formObject.getValue(param2).toString()))) {
			return true;
		} else if (!"S".equalsIgnoreCase(param1)
				&& !("No".equalsIgnoreCase(param2) || "2".equalsIgnoreCase(param2))) {
			if (validateMandatoryFieldsPPM(checkControl, msg)) {
				return true;
			} else {
				return false;
			}
		} else if (!"S".equalsIgnoreCase(param1) && ("Yes".equalsIgnoreCase(param2) || "1".equalsIgnoreCase(param2))) {
			return validateMandatoryFieldsPPM(checkControl, msg);
		}
		return true;
	}

	//added by mansi
	private void createNewWorkitemSBLC(){ //PROTRADE_140
		String query="";
		List<List<String>> sOutputlist =null;
		String output="";
		String reqCat="SBLC";
		String reqType="SBLC_CS";
		//String processType = formObject.getValue(PROCESS_TYPE).toString();
		//ProtradeComplexMapping cmplxMaster;

		try{
			if("SBLC_ER".equalsIgnoreCase(requestType)){
				query="select LINKED_WI_NAME from ext_tfo where wi_name='"+sWorkitemID+"'";
				log.info("createNewWorkitemSBLC=" + query);
				sOutputlist=formObject.getDataFromDB(query);
				log.info("sOutputlist= " + sOutputlist);
				if(!sOutputlist.get(0).get(0).equalsIgnoreCase("")){
					output = sOutputlist.get(0).get(0).toString();
				}
				if("".equalsIgnoreCase(output)||"null".equalsIgnoreCase(output)){
					callChildWICreation(reqCat,reqType);
				}
			}
			/*sOutputlist = formObject.getDataFromDB("SELECT SEQ_WEBSERVICE.nextval as IDValue from DUAL");
				String txnNumber= sOutputlist.get(0).get(0);
				StringBuffer strColumns = new StringBuffer();
				StringBuffer strValues = new StringBuffer();
			    Map<String,HashMap<String, String>> map = protradeMappingMap.get(reqCat+"#"+reqType+"#"+processType);

				for(Map.Entry<String, HashMap<String, String>> mapEntry: map.entrySet()){
					String key = mapEntry.getKey();
					HashMap<String, String> attributeMap = mapEntry.getValue();
  					log.info("attributeMap=" + attributeMap);
  					cmplxMaster=protradeComplexMap.get(key);
  					log.info("cmplxMaster=" + cmplxMaster);

     				StringBuffer strTableColNames= new StringBuffer();

                	 for( Entry<String, String> mapEntry1: attributeMap.entrySet()){
                		 strTableColNames.append(mapEntry1.getValue()+",");
                	 }
                	 strTableColNames.setLength(strTableColNames.length()-1);
                	 query ="SELECT "+strTableColNames+" from "+cmplxMaster.getComplexTableName()+" where "+cmplxMaster.getMappingAttribute()+"='"+sWorkitemID+"'";
                	 log.info("query=" + query);
                	 sOutputlist = formObject.getDataFromDB(query);
                 if("EXTERNAL".equalsIgnoreCase(key)){
                	 query="insert into "+cmplxMaster.getStagingTableName()+"(";
    				 strColumns.append("requestmode, channelname,insertiondatetime,version,"
    						+ "processname,channelrefnumber,sysrefno,correlationid,wi_name,request_type, "
    						+ "request_category,INSERTED_BY");
    				 strValues.append("'C','LINK_WI',current_date,'1','TFO','"+txnNumber+"','"+txnNumber+"','"+txnNumber+"','"
    						+sWorkitemID+"','"+reqType+"','"+reqCat+"','TFO_SYSTEM'");
    				 int counter=0;
     				for( Entry<String, String> mapEntry1: attributeMap.entrySet()){

     					String key1 =mapEntry1.getKey();
     					log.info("key1=" + key1);
     					if(!("REQUEST_CATEGORY".equalsIgnoreCase(key1)||"REQUEST_TYPE".equalsIgnoreCase(key1))){
     						strColumns.append(","+key1);
     						if(protradeDateMap.containsKey(key1) && protradeDateMap.get(key1).equals("Y")){
     							log.info("in protradeDateMap="+sOutputlist.get(0).get(counter));
     							strValues.append(",TO_DATE('"+sOutputlist.get(0).get(counter)+"','YYYY-MM-DD hh24:mi:ss')");
     						}else if(protradeCLOBMasterMap.containsKey(key1)&&protradeCLOBMasterMap.get(key1).equals("Y")){
     							log.info("inc rotradeCLOBMasterMap ="+sOutputlist.get(0).get(counter));
     							strValues.append(","+createNormalizedXML(sOutputlist.get(0).get(counter)));
        					}else{
        						log.info("in else="+sOutputlist.get(0).get(counter));
     						strValues.append(",'"+sOutputlist.get(0).get(counter)+"'");
							}
     						log.info("strValues="+strValues);
     					}
     					counter++;
     				}
    				log.info("strColumns="+strColumns);
    				log.info("strValues="+strValues);


    				query=query+strColumns.toString()+") values("+strValues.toString()+")";
    				log.info("query="+query);
    				int value=formObject.saveDataInDB(query);
    				log.info(" output value="+value);
                 } else{
                	 if (!sOutputlist.isEmpty()) {
                		for (int counter = 0; counter < sOutputlist.size(); counter++) {
                			strColumns=new StringBuffer();
                   		    strValues=new StringBuffer();
                   		    query="insert into "+cmplxMaster.getStagingTableName()+"(";  
                   		    strColumns.append("CHANNELREFNUMBER,CORRELATIONID,INSERTIONDATETIME,ROWNO");
         					strValues.append("'"+txnNumber+"','"+txnNumber+"',CURRENT_TIMESTAMP,'"+(counter+1)+"'");
         					int counter1=0;
             				for( Entry<String, String> mapEntry1: attributeMap.entrySet()){
             					String key1 =mapEntry1.getKey();
             					strColumns.append(","+key1);
             					if(protradeDateMap.containsKey(key1) && protradeDateMap.get(key1).equals("Y")){
         							strValues.append(",TO_DATE('"+sOutputlist.get(counter).get(counter1)+"','YYYY-MM-DD hh24:mi:ss')");
         						}else if(protradeCLOBMasterMap.containsKey(key1) && protradeCLOBMasterMap.get(key1).equals("Y")){
            						strValues.append(","+createNormalizedXML(sOutputlist.get(counter).get(counter1)));
            					}else{
         						strValues.append(",'"+sOutputlist.get(counter).get(counter1)+"'");
    							}
                 				log.info("strValues="+strValues);

             					counter1++;
             				}
             				log.info("strColumns="+strColumns);
            				log.info("strValues="+strValues);

             				query=query+strColumns.toString()+") values("+strValues.toString()+")";
            				log.info("query="+query);
            				int value=formObject.saveDataInDB(query);
            				log.info(" output value="+value);
         				}
         				} 
                 }

				}

				handleCreateWorkitemOutput(createChildWorkitem(txnNumber));
				saveDecHistory("Notification","Child Workitem under claim settlement created successfully!");
			}
		}*/
		}catch(Exception e){
			log.error("Exception in createNewWorkitemSBLC",e);
		}
	}

	private boolean sblcNIDocCourierMandatory(String sFieldName,String alertMsg){
		String confinIntern  = formObject.getValue("CONFN_INTRN").toString();
		if(confinIntern.equalsIgnoreCase("CF") || confinIntern.equalsIgnoreCase("MA")){
			String requestConfValue = formObject.getValue("REQ_CONF_PARTY").toString();//REQ_CONF_PARTY
			if(requestConfValue == null || requestConfValue.equalsIgnoreCase("")||"null".equalsIgnoreCase(requestConfValue)){
				sendMessageHashMap(sFieldName, alertMsg);
				return false;
			}
		}
		return true;
	}


	public boolean counterPartyListSubmitValidation()
	{

		int counterListCount=getGridCount(Qvar_cpd_details);
		int tslmCounterListCount=getGridCount(Q_TSLm_Counter_Dets);
		int tslmInvoiceListCount=getGridCount("Q_TSLM_Invoice_No_SA_Loan");

		String request_Cat = formObject.getValue("REQUEST_CATEGORY").toString();
		String request_type = formObject.getValue("REQUEST_TYPE").toString();


		log.info("counterListCount :"+counterListCount);
		if (request_type.equalsIgnoreCase("LD")   
				&& formObject.getValue(PROCESSING_SYSTEM).toString().equalsIgnoreCase("F")) {
			if( validateCPDTab(counterListCount,"add_Qvar_cpd_details","Qvar_cpd_details")){
				log.info("Tab3 -4 COUNTER DET ENTER 11");
				disableFieldOnDemand(BUTTON_SUBMIT);
			}else {
				log.info("Tab3 -4 COUNTER DET ENTER 12");
				disableFieldOnDemand(BUTTON_SUBMIT);
				return false;
			}	
		}

		log.info("tslmCounterListCount :"+tslmCounterListCount);
		if(request_type.equalsIgnoreCase("LD") && tslmCounterListCount == 0
				&& formObject.getValue(PROCESSING_SYSTEM).toString().equalsIgnoreCase("T") ){
			if( validateCPDTab(counterListCount,"add_Qvar_cpd_details","Qvar_cpd_details")){
				log.info("Tab3 -4 COUNTER DET ENTER 11");
				disableFieldOnDemand(BUTTON_SUBMIT);
			}else {
				log.info("Tab3 -4 COUNTER DET ENTER 12");
				disableFieldOnDemand(BUTTON_SUBMIT);
				return false;
			}	
		}
		log.info("tslmInvoiceListCount :"+tslmInvoiceListCount);
		if( (request_type.equalsIgnoreCase("LD") || request_type.equalsIgnoreCase("PD") || request_type.equalsIgnoreCase("PDD"))  // ATP-200
				&& (request_Cat.equalsIgnoreCase("IFA") ||request_Cat.equalsIgnoreCase("IFS")
						|| request_Cat.equalsIgnoreCase("IFP")
						|| request_Cat.equalsIgnoreCase("SCF"))         //ATP - 200
						&& formObject.getValue(PROCESSING_SYSTEM).toString().equalsIgnoreCase("T")
						&& formObject.getValue(STANDALONE_LOAN).toString().equalsIgnoreCase("1")){
			/*&& validateCPDTab(tslmInvoiceListCount,"add_Q_TSLM_Invoice_No_SA_Loan","Q_TSLM_Invoice_No_SA_Loan") ){
			log.info("Tab3 -4 TSLM INVOICE NO LOAN ENTER");
			disableFieldOnDemand(BUTTON_SUBMIT);*/
			if( validateCPDTab(tslmInvoiceListCount,"add_Q_TSLM_Invoice_No_SA_Loan","Q_TSLM_Invoice_No_SA_Loan")){
				log.info("Tab3 -4 COUNTER DET ENTER 11");
				disableFieldOnDemand(BUTTON_SUBMIT);
			}else{
				disableFieldOnDemand(BUTTON_SUBMIT);
				return false;
			}
		}
		return true;

	}
	public void SetGrntElcDecDefaultValue(){
		boolean sReferalFlag = false;
		sReferalFlag = getReferalflagStatusPPM();
		if(sReferalFlag != true){
			if("ELC".equalsIgnoreCase(requestCategory) && "ELC_SER".equalsIgnoreCase(requestType)){
				formObject.setValue(DEC_CODE, "TAOS");
			}
			else if("GRNT".equalsIgnoreCase(requestCategory) && "EPC".equalsIgnoreCase(requestType)){
				formObject.setValue(DEC_CODE, "EPOCR");
			}
		}
	}

	protected boolean validateMandatoryExpiryCond_AM()  
	{
		String REQUEST_TYPE=formObject.getValue("REQUEST_TYPE").toString();
		
		if(REQUEST_TYPE.equalsIgnoreCase("AM")|| REQUEST_TYPE.equalsIgnoreCase("SBLC_AM")
				|| REQUEST_TYPE.equalsIgnoreCase("GAA") || REQUEST_TYPE.equalsIgnoreCase("ELC_SLCAA"))
		{
		if("COND".equalsIgnoreCase(formObject.getValue(Q_AMENDMENT_DATA_USER_EXPIRY_TYPE).toString()))
		{
			return validateMandatoryFieldsPPM(Q_AMENDMENT_DATA_USER_EXPIRY_COND, "Please Enter Amendment Expiry Conditions.");
		}
		
		else if("FD".equalsIgnoreCase(formObject.getValue(Q_AMENDMENT_DATA_USER_EXPIRY_TYPE).toString()))
		{
			return validateMandatoryFieldsPPM(Q_AMENDMENT_DATA_USER_EXPIRY_DATE, "Please Enter Amendment Expiry Date.");

		}
		}
		else if(REQUEST_TYPE.equalsIgnoreCase("SBLC_AM") || REQUEST_TYPE.equalsIgnoreCase("GAA") || REQUEST_TYPE.equalsIgnoreCase("ELC_SLCAA"))
		{
			if("COND".equalsIgnoreCase(formObject.getValue(Q_AMENDMENT_DATA_USER_EXPIRY_TYPE).toString()))
		{
			return validateMandatoryFieldsPPM(Q_AMENDMENT_DATA_USER_EXPIRY_COND, "Please Enter Amendment Expiry Conditions.");
		}
		}
		return true;
	}

	//santhosh
	public void SetGrntFCUBSDefaultValue()//final decision tab
	{
		log.info(modeOfGurantee+"=======SetGrntFCUBSDefaultValue========="+modeOfGurantee);
		setModeOfGuarantee();
		String modeOfGtee=formObject.getValue("MODE_OF_GTEE").toString();
		String processType = formObject.getValue(PROCESS_TYPE).toString();
		if("GRNT".equalsIgnoreCase(requestCategory) && "NI".equalsIgnoreCase(requestType))//santhosh
		{
			if("Counter Guarantee".equalsIgnoreCase(modeOfGtee)){
				setEmptyCombo(FCUBS_PUR_OF_MSG, "Request");
			}else{
				setEmptyCombo(FCUBS_PUR_OF_MSG, "Issue");
			}
			if("Paper Guarantee".equalsIgnoreCase(modeOfGtee) && (processType.equalsIgnoreCase("PT") || processType.equalsIgnoreCase("ET"))){  //ATP-469 Shahbaz 23-05-2024
				setEmptyCombo(PURPOSE_OF_MESSAGE, "ISSU");
			}
		}
	}
	public void SetUTCValues()
	{
		try{

		log.info("=======SetUTCValues=========");
		String reqType = formObject.getValue(REQUEST_TYPE).toString();
		String	reqCat = formObject.getValue(REQUEST_CATEGORY).toString();
		List<List<String>> resultSet = null;
		if(("IFP".equalsIgnoreCase(reqCat) || "IFS".equalsIgnoreCase(reqCat) || "IFA".equalsIgnoreCase(reqCat) 
				|| "SCF".equalsIgnoreCase(reqCat)) 
			&& ("LD".equalsIgnoreCase(reqType) || "IFA_CTP".equalsIgnoreCase(reqType)
					|| "PD".equalsIgnoreCase(reqType) || "PDD".equalsIgnoreCase(reqType))) {     //ATP - 254
			String sQuery = "SELECT UTC_REQUIRED_BRMS,UTC_CONVERTED_AMOUNT FROM EXT_TFO where wi_name = '"+this.sWorkitemID+"'";
			log.info("Query "+sQuery);
			resultSet = formObject.getDataFromDB(sQuery);
			log.info("SetUTCValues===Rseultset "+resultSet);
			if(resultSet != null){
				String utcReqBRMS = resultSet.get(0).get(0);
			//	Float amount = Float.parseFloat(resultSet.get(0).get(1));  //SCFMVP2.5 REYAZ 25-07-2024
				formObject.addItemInCombo("UTC_REQUIRED_BRMS","Yes");
				formObject.addItemInCombo("UTC_REQUIRED_BRMS","No");
				formObject.setValue("UTC_REQUIRED_BRMS", utcReqBRMS);
			}

		}
		}catch(Exception e){
			log.error("Exception: ",e);
		}
	}
	

	//santhosh
	public void SetSBLC_NI_PUROFMSGDefaultValue()
	{
		log.info("Inside method of SetSBLC_NI_PUROFMSGDefaultValue");
		if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("SBLC_NI"))
		{
			List<List<String>> sResultSet = null;
			String STR_PURPOSE_OF_MESSAGE="";
			String STR_FCUBS_PUR_OF_MSG="";
			String sQuery1 =  "SELECT SWIFT_PUR_OF_MSG,FCUBS_PUR_OF_MSG from EXT_TFO where WI_NAME = '"+this.sWorkitemID+"'";
			log.info("Query "+sQuery1);
			sResultSet = formObject.getDataFromDB(sQuery1);
			log.info("SetSBLC_NI_PUROFMSGDefaultValue===Rseultset "+sResultSet);
			if(sResultSet != null){
				if(!sResultSet.get(0).get(0).isEmpty())
					STR_PURPOSE_OF_MESSAGE = normalizeString(sResultSet.get(0).get(0));
				STR_FCUBS_PUR_OF_MSG = normalizeString(sResultSet.get(0).get(1));
			}
			if(STR_PURPOSE_OF_MESSAGE.equalsIgnoreCase("")){
				formObject.setValue(PURPOSE_OF_MESSAGE, "ISSU");
			}
			if(STR_FCUBS_PUR_OF_MSG.equalsIgnoreCase("")){
				formObject.setValue(FCUBS_PUR_OF_MSG, "Issue");
			}	
		}
	}

	//santhosh
	protected boolean validateMandatoryAnyDOC_SBLC_AM()  
	{
		if("SBLC_AM".equalsIgnoreCase(formObject.getValue(REQUEST_TYPE).toString()))
		{
			return validateMandatoryFieldsPPM(LC_DOC_COURIER, "Please Select Any Document to be couriered.");
		}
		return true;
	}
	//Mansi 
		protected boolean checkDateValidation(String sUDate, String sUCntrDate){
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
				log.info("inside if condition");
				return false;
			}
			else if(dUCntrDate.before(dUDate)){
				log.info("inside elseif condistion");
				return true;	
			}else{
				log.info("inside else");
				return false;
			}
		} catch (Exception e) {		
			log.error("Inside catch: ",e);
			return false;
		}
	}

	protected Boolean checkExpDateValidation(){
		log.info("Inside checkExpDateValidation");

		String msg="";
		String gteecntrexpdate=formObject.getValue(Q_AMENDMENT_DATA_USER_CNTR_GTEE_EXP_DATE).toString();
		String usercntrexpdate=formObject.getValue(Q_AMENDMENT_DATA_USER_EXPIRY_DATE).toString();

		if ("AM".equalsIgnoreCase(requestType)) {
			
			if(checkDateValidation(usercntrexpdate,gteecntrexpdate)) {
				log.info("Inside checkDateValidation for showing alert:");
				msg=ALERT_CNTRGTEE_DATE;
				sendMessageHashMap(gteecntrexpdate,msg);
				log.info("getting message"+msg);
				return true;
			}
		}
		return false;
	}
	public void saveRejectReason(){
		try{
			
			String sQuery = "";
			sQuery = "UPDATE EXT_TFO SET REJ_RESN_PPM = '"+formObject.getValue("REJ_RESN_PPM").toString()+"',REMARKS = '"+formObject.getValue("REMARKS").toString()+"'"+
					"where wi_name = N'"+sWorkitemID+"'";
			log.info("REJ_RESN_PPM UPDATEquery :: "+sQuery);
			int sUpdateOutput = formObject.saveDataInDB(sQuery);
			log.info("REJ_RESN_PPM sUpdateOutput: "+sUpdateOutput);
		} catch (Exception e) {		
			log.error("Inside catch: ",e);
		}
	}
	public boolean validateRejectCondition(){
		
			log.info("Inside Reject Reason Remarks "+formObject.getValue(REJ_RESN_PPM).toString());
			
			String str = formObject.getValue(REJ_RESN_PPM).toString();
			log.info("Inside Reject Reason val "+str);
			str  = str.substring(1,str.length()-1);
			log.info("Inside Substring  val "+str);
			str = str.replaceAll("\"", "");
			log.info("Inside Reject replace val "+str);
			String[] arr = str.split(",");
			log.info("Inside Reject split val "+str);
			log.info("Before Others "+Arrays.asList(arr).contains("Others"));
			if(Arrays.asList(arr).contains("Others")){
				log.info("Inside Others "+Arrays.asList(arr).contains("Others"));
				if("".equalsIgnoreCase(formObject.getValue("REMARKS").toString())){
					log.info("Inside null Remarks "+formObject.getValue(REMARKS).toString());
					sendMessageHashMap("REMARKS", "Please enter Remarks.");
					return false;
				}else 
					return true;
			}else
				return true;
				
			}
		
	
	protected void setDocRvwDefaultSwift(){
		log.info("Inside setDocRvwDefaultSwift");
		String msg="";
		String reqType=formObject.getValue(REQUEST_TYPE).toString();
		String reqCat=formObject.getValue(REQUEST_CATEGORY).toString();
		String processType=formObject.getValue(PROCESS_TYPE).toString();
		String swiftutilityTag=formObject.getValue(SWIFT_UTILITY_FLAG).toString();
		int gridCount = getGridCount("Qvar_Doc_Rvw");//formObject.get("Qvar_Doc_Rvw", jsonArray);
		log.info("reqType : "+reqType);
		log.info("reqCat  : "+reqCat);
		JSONArray jsonArray = new JSONArray();
		log.info("setDocRvwDefaultSwift Check 1");
		if(reqCat.equalsIgnoreCase("GRNT") 
				&& (reqType.equalsIgnoreCase("NI") || reqType.equalsIgnoreCase("GA"))
				&& processType.equalsIgnoreCase("SWIFT") && swiftutilityTag.equalsIgnoreCase("Y") ){
			log.info(" setDocRvwDefaultSwift Check 2");
			List<List<String>> sResultSet = null;
			String purposeOfMessage="";
			String formUndertaking="";
			String DemandType = "";
			String msgType= "";
			String sQuery1 =  "SELECT PURPOSE_MESSAGE_A,FORM_UNDERTAKING_B,DEMAND_TYPE,MSG_TYPE "
					+ "from tfo_dj_swift_txn_details where WI_NAME = '"+this.sWorkitemID+"'";
			log.info("Query "+sQuery1);
			sResultSet = formObject.getDataFromDB(sQuery1);
			log.info("setDocRvwDefaultSwift===Rseultset "+sResultSet);
			if(sResultSet != null){
				if(!(gridCount > 0)){
				if(!sResultSet.get(0).get(0).isEmpty()){
					purposeOfMessage =sResultSet.get(0).get(0);
					formUndertaking = sResultSet.get(0).get(1);
					DemandType = sResultSet.get(0).get(2);
					msgType  = sResultSet.get(0).get(3);
					JSONObject jsonObject = new JSONObject();
					if(msgType.equalsIgnoreCase("760") && formUndertaking.equalsIgnoreCase("DGAR") && DemandType.equalsIgnoreCase("NA")){
						if(purposeOfMessage.equalsIgnoreCase("ISCO")){
							log.info("setDocRvwDefaultSwift ISCO");
							jsonObject.put("guideline","Please select Mode of GTEE.");
							jsonObject.put("response","Paper Guarantee");
							jsonArray.add(jsonObject);
						}else if(purposeOfMessage.equalsIgnoreCase("ICCO")){
							log.info("setDocRvwDefaultSwift ICCO");
							jsonObject.put("guideline","Please select Mode of GTEE.");
							jsonObject.put("response","Counter Guarantee");
							jsonArray.add(jsonObject);
						}
						log.info("setDocRvwDefaultSwift jsonArray : "+jsonArray);
						formObject.addDataToGrid("Qvar_Doc_Rvw", jsonArray);
					}else{
						log.info("setDocRvwDefaultSwift MsgType formUnsertaking DemandType condition matched");
					}
				}
				}else{
					log.info("setDocRvwDefaultSwift Grid Count is not Zero");
				}
			}else{
				log.info("setDocRvwDefaultSwift Empty Query Result");
			}
		log.info("END setDocRvwDefaultSwift");
		}else{
			log.info("setDocRvwDefaultSwift Category Type not matched");
		}
	}
	
	public String loadTabListViewTSLM(String controlName, String listVwName) {
		String value="";
		String referTo="";
		String delFlag = "";
		String status="";
		String seqNo="";
		try {
			JSONArray output=formObject.getDataFromGrid (listVwName);
			log.info("loadTabListView_TSLM output: "+output);
			int length = output.size();
			log.info("loadTabListView_TSLM length: "+length);
			for(int i=0;i<length;i++){
				log.info("list key Type "+formObject.getTableCellValue(listVwName, i, 0));
				referTo=formObject.getTableCellValue(listVwName, i, 1);
				status=formObject.getTableCellValue(listVwName, i, 5);
				seqNo=formObject.getTableCellValue(listVwName, i, 0);
				log.info("Refer To: "+referTo);
				log.info("status: "+status);
				log.info("seqNo: "+seqNo);
				
			if ("SIGN_REFERRAL_ID".equalsIgnoreCase(listVwName)) {
				//String strTabName =tabDescIdMap.get("SIGN");
				log.info("in SIGN_REFERRAL_ID");
				if(status.equalsIgnoreCase("active"))
					delFlag = "N";
				else
					delFlag = "Y";
				
				setRoutingFlag("TSLM_SIGACC_REFER_ID",delFlag);
				log.info("before updateReferToGrid_TSLM: " + formObject.getValue("TSLM_SIGACC_REFER_ID").toString());
				updateReferralDetailsTSLM(referTo,delFlag, listVwName,seqNo);

			} else if ("Doc_Review_RefID".equalsIgnoreCase(listVwName)) {
				//String strTabName =tabDescIdMap.get("DOC");
				log.info("in Doc_Review_RefID");
				if(status.equalsIgnoreCase("active"))
					delFlag = "N";
				else
					delFlag = "Y";
				
				setRoutingFlag("TSLM_DOCREV_REFER_ID",delFlag);
				log.info("before updateReferToGrid_TSLM: " + formObject.getValue("TSLM_DOCREV_REFER_ID").toString());
				updateReferralDetailsTSLM(referTo,delFlag, listVwName,seqNo);

			} else if ("LIMIT_REFERRAL_ID".equalsIgnoreCase(listVwName)) {
				//String strTabName =tabDescIdMap.get("LIMIT1");
				log.info("in LIMIT_REFERRAL_ID");
				if(status.equalsIgnoreCase("active"))
					delFlag = "N";
				else
					delFlag = "Y";
				
				setRoutingFlag("TSLM_LMTCRE_REFER_ID",delFlag);
				log.info("before updateReferToGrid_TSLM: " + formObject.getValue("TSLM_LMTCRE_REFER_ID").toString());
				updateReferralDetailsTSLM(referTo,delFlag,listVwName,seqNo);

			}
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		return value;
	}
	
	protected  void updateReferToGridTSLM(String strRefTo,String delFlag,String listVwName){
		String exeEmailID = "";
		try{
			log.info("UpdateReferToGrid_TSLM Referral To: " + strRefTo);
			exeEmailID = getReferralmailId(strRefTo);
			exeEmailID = exeEmailID.replace("'", "''");

			if ("SIGN_REFERRAL_ID".equalsIgnoreCase(listVwName)) {
				log.info("--------------------------inside SIGN_REFERRAL_ID --------------------");
				formObject.setValue("TSLM_LMTCRE_EXISTING_EMAIL_ID",exeEmailID);
				formObject.setValue("TSLM_LMTCRE_DECISION_ID","Yes");
				formObject.setValue("TSLM_LMTCRE_DEL_FLAG_ID",delFlag);
				log.info("Value for SIGN_REFERRAL_ID set");

			}else if ("Doc_Review_RefID".equalsIgnoreCase(listVwName)) {
				log.info("--------------------------inside Doc_Review_RefID --------------------");
				formObject.setValue("TSLM_DOCREV_EXISTING_EMAIL_ID",exeEmailID);
				formObject.setValue("TSLM_DOCREV_DECISION_ID","Yes");
				formObject.setValue("TSLM_DOCREV_DEL_FLAG_ID",delFlag);
				log.info("Value for Doc_Review_RefID set");
				
			} else if ("LIMIT_REFERRAL_ID".equalsIgnoreCase(listVwName)) {
				log.info("--------------------------inside LIMIT_REFERRAL_ID --------------------");
				formObject.setValue("TSLM_LMTCRE_EXISTING_EMAIL_ID",exeEmailID);
				formObject.setValue("TSLM_LMTCRE_DECISION_ID","Yes");
				formObject.setValue("TSLM_LMTCRE_DEL_FLAG_ID",delFlag);
				log.info("Value for LIMIT_REFERRAL_ID set");
			} 
		}
		catch(Exception e){
			log.error("Exception",e);
		}
	}
	
	
	public void updateReferralDetailsTSLM(String strRefTo,String delFlag,String listVwName, String seqNo){
		try{
			String sQuery = "";
			String exeEmailID = getReferralmailId(strRefTo);
			String tableName = "";
			if ("SIGN_REFERRAL_ID".equalsIgnoreCase(listVwName)) {
				tableName = "TFO_DJ_TSLM_REFERRAL_DETAIL";	
			}else if ("Doc_Review_RefID".equalsIgnoreCase(listVwName)) {
				tableName = "TFO_DJ_TSLM_DOCUMENT_REVIEW";	
			} else if ("LIMIT_REFERRAL_ID".equalsIgnoreCase(listVwName)) {
				tableName = "TFO_DJ_TSLM_LIMIT_CREDIT_REVIEW";	
			}
					
			log.info("updateReferralDetailsTSLM Table Name :: "+tableName);
			log.info("updateReferralDetailsTSLM Email ID :: "+exeEmailID);
			log.info("updateReferralDetailsTSLM Delete Flag :: "+delFlag);
			
			sQuery = "UPDATE "+tableName+" SET DECISION = 'Y', FLAG_DEL = '"+delFlag+"' , EXISTING_EMAIL = '"+exeEmailID+"'"+
					" WHERE WI_NAME = '"+sWorkitemID+"' AND SEQNO='"+seqNo+"'";
			
			log.info("updateReferralDetailsTSLM query :: "+sQuery);
			int sUpdateOutput = formObject.saveDataInDB(sQuery);
			log.info("updateReferralDetailsTSLM Output: "+sUpdateOutput);
		} catch (Exception e) {		
			log.error("Inside catch: ",e);
		}
	}
	public void setDecisionValidationTSLM(String tableName){
		try{
			boolean sReferalFlag = getReferalflagStatusPPM();
			String decision = formObject.getValue(DEC_CODE).toString();
			String delFlag = "N";
			log.info("setDecisionValidationTSLM sReferalFlag: "+sReferalFlag+ " and decision : "+decision);
			if(!sReferalFlag){
				if(decision.equalsIgnoreCase("APP")){
					delFlag ="Y";
				}
			}
			String sQuery = "UPDATE "+tableName+" SET FLAG_DEL = '"+delFlag+"'"+
							" WHERE WI_NAME = '"+sWorkitemID+"'";
			log.info("setDecisionValidationTSLM query :: "+sQuery);
			int sUpdateOutput = formObject.saveDataInDB(sQuery);
			log.info("setDecisionValidationTSLM Output: "+sUpdateOutput);
		} catch (Exception e) {		
			log.error("Inside catch: ",e);
	}
  } 
	public void loadRefComboTSLM(String referTo){ //krishna	
	try{
		    log.info("Inside loadRefComboTSLM"+referTo);
			String sQuery = "SELECT REFERRAL_CODE FROM tfo_dj_auto_ref_mast "
					+ "WHERE REFER_TO = '"
					+ referTo+ "' ORDER BY 1 ";
			List<List<String>> sOutputlist = formObject.getDataFromDB(sQuery);
			log.info("Inside loadRefComboTSLM sQuery: " + sQuery);
			formObject.clearCombo("table66_REFERRALTYPE");
			formObject.clearCombo("REFF_DESC");
			for (int j = 0; j < sOutputlist.size(); j++) {
				formObject.addItemInCombo("table66_REFERRALTYPE", sOutputlist
						.get(j).get(0).toString());
			}
			formObject.clearCombo("table68_Referralcode");
			formObject.clearCombo("REFF_DESC");
			for (int j = 0; j < sOutputlist.size(); j++) {
				formObject.addItemInCombo("table68_Referralcode", sOutputlist
						.get(j).get(0).toString());
			}
			formObject.clearCombo("table69_REFERRALCODE");
			formObject.clearCombo("REFF_DESC");
			for (int j = 0; j < sOutputlist.size(); j++) {
				formObject.addItemInCombo("table69_REFERRALCODE", sOutputlist
						.get(j).get(0).toString());
			}
		
		} catch (Exception e) {		
			log.error("Inside catch: ",e);
	}
 } 
		

	public void setEDASApproval(){
		String sQuery = "";
		String processType=formObject.getValue(PROCESS_TYPE).toString();
		log.info("Process type for EDAS: "+processType);
		try{
		if(processType.equalsIgnoreCase("TSLM Front End")){
            sQuery = "SELECT LT_RESPONSE FROM TFO_DJ_LMT_CRDT_RECORDS WHERE WI_NAME='" + this.sWorkitemID + "' AND LT_DOC_RVW_GDLINES = 'Legalization Charges Applicable' ";		           
			List<List<String>> lresultSet=null;
			lresultSet =  formObject.getDataFromDB(sQuery);
			log.info("Query output="+lresultSet);
			if(lresultSet!=null){
				log.info("in setEDASApproval lresultSet=");
					for(int i=0;i<lresultSet.size();i++){
						if(!"Yes".equalsIgnoreCase(lresultSet.get(i).get(0))){
							log.info("inside setEDASApproval setting");
							formObject.addItemInCombo("EDAS_APPROVAL","Not Applicable");
							formObject.setValue("EDAS_APPROVAL", "Not Applicable");
							}
						else if("Yes".equalsIgnoreCase(lresultSet.get(i).get(0))){
							formObject.setValue("EDAS_APPROVAL", "");
							break;
						}
						}
					}
				}
		} catch (Exception e) {
			log.error("Exception in setFlag",e);

		}
	}

	public void setLimitLineTSLM(){
		String sQuery = "";
		String limitLine = "";
		String processType=formObject.getValue(PROCESS_TYPE).toString();
		log.info("Process type : "+processType);
	try{
		if(processType.equalsIgnoreCase("TSLM Front End")){
			sQuery = "SELECT LC_LIMIT_LINE FROM EXT_TFO WHERE WI_NAME='" + this.sWorkitemID + "' ";		           
			List<List<String>> lresultSet=null;
			lresultSet =  formObject.getDataFromDB(sQuery);
			log.info("Query output="+lresultSet);
			if(lresultSet!=null){
				log.info("in setLimitLineTSLM lresultSet=");
					for(int i=0;i<lresultSet.size();i++){
						 limitLine = lresultSet.get(i).get(0);
						 log.info("Limit Line: " + limitLine);
						}
				String sQuery1 = "UPDATE TFO_DJ_LMT_CRDT_RECORDS SET LT_RESPONSE = '"+limitLine+"'"+
							" WHERE WI_NAME = '"+sWorkitemID+"' AND DOC_RVW = 'Please specify Loan Limit Line to be attached to the contract'";
				log.info("setLimitLineTSLM query :: "+sQuery1);
				int sUpdateOutput = formObject.saveDataInDB(sQuery1);
				log.info("setLimitLineTSLM Output: "+sUpdateOutput);
				}
		}
	} catch (Exception e) {		
		log.error("Inside catch: ",e);
		}
	}
	

	public void ppmFieldFrmOnLoadSCF(String reqTpe) { //ADDED BY Shivanshu FOR SCF ATP - 199
	
		log.info("***********ppmFieldFrmOnLoad_SCF***Form load if req_type is SCF****************");
		try {
			if ("PD".equalsIgnoreCase(reqTpe) || "PDD".equalsIgnoreCase(reqTpe)) {
				formObject.setTabStyle("tab1", "8", "visible", "true");
				if("BAU".equalsIgnoreCase(formObject.getValue(PROCESS_TYPE).toString())) {
					enableFieldOnDemand(BTN_FETCH_TSLM_CID_DETAILS);
//				 	disableFieldOnDemand("INF_AMEND_TYPE####INF_LOAN_CURR####INF_NEW_MATURITY_DATE####BILL_RVSD_DOC_AVL");
				}else if("TSLM Front End".equalsIgnoreCase(formObject.getValue(PROCESS_TYPE).toString())) {
				
				}
			} 	
		} catch (Exception e) {
			log.error("Exception: ",e);
		}

	
	}

	//Changes for SCF Date 10-11-2023  for Jira Ticket -ATP -199 ,ATP -179 
	//START CODE 
	public void fetchPastDueLiability() {
		try {	
			log.info("FCUBS Past Due details populate");
			String customerID = formObject.getValue(CUSTOMER_ID).toString();
			String sOutputXML1 = fetchPastDueLiabilityFlag(customerID);
			log.info("sOutputXML ::"+ sOutputXML1);
			XMLParser xmlparser1 = new XMLParser(sOutputXML1);
			String returnTypeCheck1 = xmlparser1.getValueOf("pastDueFlag");
			String returnCode = xmlparser1.getValueOf("returnCode");
			log.info("FCUBS Past Due Liability details populate returnTypeCheck1: "+ returnTypeCheck1);
			if (returnCode.equalsIgnoreCase("0")) {
				if (returnTypeCheck1.equalsIgnoreCase("Yes")) {
					log.info("Inside FCUBS Past Due Liability details populate returnTypeCheck1: ");
					formObject.setValue(PAST_DUE_LIABILITY, "Yes"); 

				} else {
					formObject.setValue(PAST_DUE_LIABILITY, "No"); 
				}
			}
			else{ 
				log.info("FCUBS Past Due details populate: Unable to fetch FCUBS Past Due Liability Data");
				sendMessageHashMap(FCUBS_ANY_PAST_DUE_CID,"Unable to fetch FCUBS Past Due Liability Data");
				formObject.setValue(FCUBS_ANY_PAST_DUE_CID, "");
			}
		} catch (Exception e) {
			log.info("FCUBS Past Due Liability details populate error : "+ e);
		}
	}
	
	private String fetchPastDueLiabilityFlag(String customerID) {
		log.info("inside fetchPastDueLiabilityFlag..");
		String sInput = "";
		String sOutput="";
		List<List<String>> sOutputlist = formObject.getDataFromDB("SELECT SEQ_WEBSERVICE.nextval as IDValue from DUAL ");
		if (!sOutputlist.isEmpty() && !sOutputlist.get(0).isEmpty()) {
			sInput = getModLendingAutomationInputXML(sOutputlist.get(0).get(0),customerID);
			sOutput = socket.connectToSocket(sInput);
			log.info("inside fetchPastDueLiabilityFlag.. sOutput: "+sOutput);
		}
		return sOutput;
}

	private String getModLendingAutomationInputXML(String sSeqNo,String customerID) {
		log.info("inside getModLendingAutomationInputXML.. ");
		StringBuffer inputXML = new StringBuffer();
		try {
			String requestTimestamp = getCurrentTimeStamp();
			log.info("  requestTimestamp "+ requestTimestamp);
			inputXML.append("<?xml version=\"1.0\"?>")
					.append("\n")
					.append("<APWebService_Input>")
					.append("\n")
					.append("<Option>WebService</Option>")
					.append("\n")
					.append("<EngineName>" + sEngineName + "</EngineName>")
					.append("\n")
					.append("<SessionId>" + sSessionId + "</SessionId>")
					.append("\n")	
					.append("<Calltype>WS-2.0</Calltype>")
					.append("\n")
					.append("<InnerCallType>ModLendingAutomation</InnerCallType>")
					.append("\n")
					.append("<serviceName>ModLendingAutomation</serviceName>")
					.append("\n")
					.append("<versionNo>" + "1.0" + "</versionNo>")
					.append("\n")
					.append("<WIName>" + sWorkitemID + "</WIName>")
					.append("\n")
					.append("<senderID>TSLMSY</senderID>")
					.append("\n")
					.append("<consumer>APIGW</consumer>")
					.append("\n")
					.append("<sysRefNumber>" + sSeqNo + "</sysRefNumber>")
					.append("\n")
					.append("<requestTimestamp>" + requestTimestamp+ "</requestTimestamp>")
					.append("\n")
					.append("<fetchDelinquencyCheckReq>")
					.append("\n")
					.append("<account></account>")
					.append("\n")
					.append("<customerId>" + customerID+ "</customerId>")
					.append("\n")
					.append("<limitLineId></limitLineId>")
					.append("\n")
					.append("</fetchDelinquencyCheckReq>").append("\n")
					.append("</APWebService_Input>");
			
			log.info("input XML: "+inputXML.toString());
		}  catch (Exception e) {
			log.error("Exception:  getModLendingAutomationInputXML ",e);
		}
		return inputXML.toString();
	}
	
	public String getCurrentTimeStamp() {
		String currentTimeStamp = null;
		try {
			Date date = Calendar.getInstance().getTime();
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
			currentTimeStamp = formatter.format(date);
			log.info("getCurrentTimeStamp  currentTimeStamp"+ currentTimeStamp);
		} catch (Exception e) {
		}
		return currentTimeStamp;
	}	
	//END CODE 

	//ATP-463 24-06-2024 --JAMSHED START
	private boolean validatePastDue() {
		boolean res=true;
		MandatoryCheckPastDue mn_chk = new MandatoryCheckPastDue(formObject);
		res=mn_chk.validateMandatoryPastDuePPM();

		log.info("PPM---sendmessagelist: "+sendMessageList);
		log.info("MandatoryCheckPastDue---sendmessagelist: "+mn_chk.sendMessageList);
		sendMessageList =mn_chk.sendMessageList;
		log.info("PPM---sendmessagelist: "+sendMessageList);
		return res;
}
	//ATP-463 24-06-2024 --JAMSHED END

	//ATP-487 04-07-2024 --JAMSHED START
	public void setTBMLExt(){
		try{
			log.info("Inside setTBMLExt");
			List<List<String>> sResultSet = null;
			String sQuery1 =  "SELECT TRNS.RESPONSE, '1' AS QUERY_ORDER FROM TFO_DJ_TBML_MAST TB, TFO_DJ_DOC_RVW_RECORDS TRNS WHERE TRNS.RESPONSE =TB.DESCRIPTION AND TRNS.WI_NAME = '"+this.sWorkitemID+"' AND trns.doc_rvw_gdlines='Line of business check' UNION SELECT TRNS.RESPONSE, '2' AS QUERY_ORDER FROM TFO_DJ_TBML_RED_MAST RED, TFO_DJ_DOC_RVW_RECORDS TRNS WHERE TRNS.RESPONSE =RED.DESCRIPTION AND TRNS.WI_NAME = '"+this.sWorkitemID+"' AND trns.doc_rvw_gdlines IN ('Other TBML/Red flag checks as per ADCB policy/procedures','Other TBML/Red flag checks as per manual') UNION SELECT TRNS.RESPONSE, '3' AS QUERY_ORDER FROM TFO_DJ_TBML_PC_MAST PC, TFO_DJ_DOC_RVW_RECORDS TRNS WHERE TRNS.RESPONSE =PC.DESCRIPTION AND TRNS.WI_NAME = '"+this.sWorkitemID+"' AND trns.doc_rvw_gdlines='Price check' ORDER BY QUERY_ORDER";
			log.info("Query "+sQuery1);
			sResultSet = formObject.getDataFromDB(sQuery1);
			log.info("Rseultset "+sResultSet);
			if(sResultSet != null){
				for(int i=0; i<sResultSet.size(); i++) {
					if(sResultSet.get(i).get(1).equalsIgnoreCase("1")) {
						formObject.setValue("BIZ_CHK", sResultSet.get(i).get(0));
					}else if(sResultSet.get(i).get(1).equalsIgnoreCase("2")) {
						formObject.setValue("TBML_RED_FLG", sResultSet.get(i).get(0));
					}else if(sResultSet.get(i).get(1).equalsIgnoreCase("3")) {
						formObject.setValue("PRICE_CHK", sResultSet.get(i).get(0));
					}
				}
			}
			
			log.info("EXIT ---- setTBMLExt");
		}catch(Exception e){
			log.error("Exception: ",e);
		}
	}
	//ATP-487 04-07-2024 --JAMSHED END

	//ATP-492 25-07-2024 REYAZ START
	private boolean validateStlAmTslmFronted() {
	    String requestCategory = formObject.getValue(REQUEST_CATEGORY).toString().toUpperCase(); 
	    String requestType = formObject.getValue(REQUEST_TYPE).toString().toUpperCase();
	    String processType = formObject.getValue(PROCESS_TYPE).toString().toUpperCase();
	    
	    if (processType.equals("TSLM FRONT END")) {
	        if ((requestCategory.equals("IFA") || requestCategory.equals("IFS") || requestCategory.equals("IFP") || requestCategory.equals("TL"))
	                && (requestType.equals("AM") || requestType.equals("STL") || requestType.equals("TL_AM"))) {
	            return true;
	        }
	    }
	    
	    return false;
	}

	//ATP-492 25-07-2024 REYAZ END

}




