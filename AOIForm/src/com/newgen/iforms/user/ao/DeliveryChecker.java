package com.newgen.iforms.user.ao;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import org.json.simple.JSONArray;

import com.newgen.iforms.EControl;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.iforms.user.config.ConstantAlerts;
import com.newgen.iforms.user.config.Constants;
import com.newgen.mvcbeans.model.WorkdeskModel;

public class DeliveryChecker extends Common implements Constants,ConstantAlerts,
IFormServerEventHandler {

	public DeliveryChecker(IFormReference iFormReference) {
		// TODO Auto-generated constructor stub
		super(iFormReference); 
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
	public String executeServerEvent(IFormReference formObject, String controlName, String eventType, String data) {
		logInfo("executeServerEvent", "eventType "+eventType+" Inside Delivery Checker data "+data);
		List<List<String>> list;
		sendMessageList.clear();
		logInfo("executeServerEvent", "sendmessagelist49");
		String retMsg = getReturnMessage(true);
		List<List<String>> recordList;
		String controlValue = formObject.getValue(controlName).toString();
		try {
			if(eventType.equalsIgnoreCase(EVENT_TYPE_LOAD)) {
				logInfo("EVENT_TYPE_LOAD","Inside EVENT_TYPE_LOAD");
				onLoadDeliveryChecker();
				int iRows = getGridCount(ACC_RELATION);
				if(iRows>0){
					String sFinalName = formObject.getTableCellValue(ACC_RELATION, 0, 1);
					String sFinalDOB = formObject.getTableCellValue(ACC_RELATION, 0, 5);
					String cust_id = formObject.getTableCellValue(ACC_RELATION, 0, 2);
					formObject.setValue(TXT_CUSTOMERNAME, sFinalName);
					formObject.setValue(TXT_CUSTOMERID, cust_id);
					formObject.setValue(TXT_DOB, sFinalDOB);
				}
				LoadInstantDelivery();
				populateStndInstr();
			} else if(eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)){
				logInfo("EVENT_TYPE_CLICK","Inside EVENT_TYPE_CLICK k");
				if(TABCLICK.equalsIgnoreCase(controlName)) {
					onTabClickDeliveryChecker(data);
				}  
				else if(BTN_SUBMIT.equalsIgnoreCase(controlName)){
					logInfo("BTN_SUBMIT","Inside BTN_SUBMIT bool");
					if(submitWorkitem()) {
						logInfo("BTN_SUBMIT","Inside BTN_SUBMIT data"+data);
						if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approved") && !data.equalsIgnoreCase("AfterJSP")) {
							return getReturnMessage(true, controlName, "openJSP");
						} if (formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approved") && data.equalsIgnoreCase("AfterJSP")) {
							return getReturnMessage(true, controlName, CA008);
						}
					}
				} else if("handlingJSPData".equalsIgnoreCase(eventType)) {
					if(BTN_SUBMIT.equalsIgnoreCase(controlName)) {
						if(showOpenCallJSP(data)) {
							return getReturnMessage(true, controlName, CA008);
						}
					}
				} else if(controlName.equalsIgnoreCase("postSubmit")){
					if(postSubmit()) {
						return getReturnMessage(true, controlName);
					}
				}
			} else if(eventType.equalsIgnoreCase(EVENT_TYPE_CHANGE)){
				if(controlName.equalsIgnoreCase("table94_mode_of_funding")) {
				logInfo("table94_mode_of_funding","In Transfer Mode");
				int iPrimaryCust = Integer.parseInt(getPrimaryCustomerSNO());
				String sCustID  = formObject.getTableCellValue(ACC_RELATION,iPrimaryCust,2);
				int iRows = getGridCount(PRODUCT_QUEUE);				
				int iSelectedRow=Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
				logInfo("table94_mode_of_funding","iRows---"+iRows);
				if(iRows>0) {
					String sMode=formObject.getTableCellValue(PRODUCT_QUEUE,iSelectedRow,8);
					if(sMode.equalsIgnoreCase("Transfer - Internal")) {
						String sQuery= "SELECT ACC_NO FROM USR_0_PRODUCT_EXISTING WHERE WI_NAME ='"+sWorkitemId+"' AND ACC_STATUS IN (SELECT DESCRIPTION FROM USR_0_ACCOUNT_STATUS_CODE WHERE CODE IN ('6','8')) AND CUSTOMER_ID='"+sCustID+"'";
						List<List<String>> out = formObject.getDataFromDB(sQuery);
						logInfo("table94_mode_of_funding","out---"+out);
						if(out!=null && out.size()>0){
							logInfo("table94_mode_of_funding","out---"+out);
							for(int i=0;i<out.size();i++) {
								formObject.addItemInTableCellCombo(PRODUCT_QUEUE, i, 4, out.get(i).get(0),out.get(i).get(0));//("AO_PRODUCT_QUEUE.TRNSFR_FROM_ACC_NO", sTemp[i]);
							}
							formObject.setTableCellValue(PRODUCT_QUEUE, 0, 4,"");
						}
					} 
				}
			} 
			}
		} catch (Exception e) {
			logError("Exception in Event- " + eventType + "control name- " +controlName+ ": ", e);
		} finally{
			logInfo("executeServerEvent", "sendMessageList");
			if(!sendMessageList.isEmpty()){
				return  getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1).toString());
			}
		}
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

	public void onTabClickDeliveryChecker(String data){
		logInfo("onTabClickDeliveryChecker", "INSIDE");
		try{
			logInfo("onTabClick", "INSIDE");
			String[] selectedSheetInfo = data.split(",");
			String tabCaption = selectedSheetInfo[0];
			int selectedSheetIndex = Integer.parseInt(selectedSheetInfo[1]);
			if(selectedSheetIndex == 1 || selectedSheetIndex == 2 || selectedSheetIndex == 3 
					|| selectedSheetIndex == 4 || selectedSheetIndex == 5) {	
				disableControls(new String[]{BTN_SUBMIT});	
				setManualFieldsBlank();
				setCPDCheckerCombos();
				populatePersonalDataCPD();
				populateRiskData();
				populateKYCData();
	//			populateKycMultiDrop();
	//			populatePreAssesmentDetails();  //shahbaz
				populateComparisonFields();
				PopulatePrivateClientQuestions(); //added by harinder
				loadDedupeSearchData(sWorkitemId);
				int fieldToFocus=Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
				String bnk_relation= formObject.getTableCellValue(ACC_RELATION,fieldToFocus,7);
				if(bnk_relation.equalsIgnoreCase("New")||bnk_relation.equalsIgnoreCase("Existing")) {
					String segment=formObject.getValue(PD_CUSTSEGMENT).toString();
					visibleonSegmentBasisCPDCHECKER(segment);
					String req_type=formObject.getValue(REQUEST_TYPE).toString();
					if(req_type.equalsIgnoreCase("New Account with Category Change") 
							|| req_type.equalsIgnoreCase("Category Change Only")) {
						manageCategoryChangeSectionCPDChecker();
					}
				}
			} else if(selectedSheetIndex == 6 || selectedSheetIndex == 7) {
				populateScreeningDataCPD();
				populateScreeningDataCRO();
			} else if(selectedSheetIndex == 8 || selectedSheetIndex == 9) {	
				loadApplicationAssessmentDataCPD();						
				String sQuery= "";
				List<List<String>> recordList = null;
				if(getGridCount(PROD_SEC_OFRD_CPD_LVW) == 0) {
					sQuery = "SELECT PRODUCT_CODE,PRODUCT_DESC,CURRENCY_DESC,'' FROM USR_0_PRODUCT_OFFERED_CPD WHERE WI_NAME = '"+sWorkitemId+"' ORDER BY TO_NUMBER(PRODUCT_CODE)";				
					recordList = formObject.getDataFromDB(sQuery);
					logInfo("",sQuery);
					loadListView(recordList,"PRODUCT_CODE,PRODUCT_DESC,CURRENCY_DESC,PROD_ACCT_OPNG",PROD_SEC_OFRD_CPD_LVW);
				} 
				if(getGridCount(FAC_LVW_CPD) == 0) {
					String sQuery1 = "SELECT CID,FACILITY,DESCRIPTION FROM USR_0_FACILITY_SELECTED_CPD WHERE WI_NAME = '"+sWorkitemId+"' ORDER BY FACILITY";
					recordList = formObject.getDataFromDB(sQuery1);
					logInfo("",sQuery1);
					loadListView(recordList,"CID,FACILITY,DESCRIPTION",FAC_LVW_CPD);
				}
				if(getGridCount(FAC_OFRD_LVW_CPD) == 0) {
					String sQuery2  =  "SELECT CUST_ID,CUST_NAME,CARD_TYPE FROM USR_0_DEBITCARD_OFFERED_CPD WHERE WI_NAME = '"+sWorkitemId+"'";		
					recordList = formObject.getDataFromDB(sQuery2);
					logInfo("",sQuery2);
					loadListView(recordList,"CUST_ID,CUST_NAME,CARD_TYPE",FAC_OFRD_LVW_CPD);
				}
			} else if(selectedSheetIndex==10) {
				disableControls(new String[]{BTN_SUBMIT});
				logInfo("onTabClick", "INSIDE 8");
				disableControls(new String[]{BTN_SUBMIT});
				logInfo("","inside on click tab 9 Account Info ");
				int gridCount = getGridCount(ACC_INFO_EDC_LVW);
				logInfo("","COUNT OF GRID = "+gridCount);
				if(gridCount ==  0) {
					String sQuery6  =  "SELECT CUST_ID,EMBOSS_NAME,CARD_TYPE_DESC,STATUS_DESC,ISSUANCE_DATE,EXPIRY_DATE FROM USR_0_DEBITCARD_EXISTING WHERE WI_NAME = '"+sWorkitemId+"'";		
					List<List<String>> recordList = formObject.getDataFromDB(sQuery6);
					logInfo("","query for account info " +sQuery6);
					loadListView(recordList,"CUST_ID,EMBOSS_NAME,CARD_TYPE_DESC,STATUS_DESC,ISSUANCE_DATE,EXPIRY_DATE",ACC_INFO_EDC_LVW);	
				}
			} else if(tabCaption.equalsIgnoreCase("Standing Instruction")) {
				loadSICombos();
				populateStndInstr();
			} else if(tabCaption.equalsIgnoreCase("Delivery Preferences")) {
				Frame_delivery();
			} else if(tabCaption.equalsIgnoreCase("Decision")) {						
				enableControls(new String[]{BTN_SUBMIT});
				logInfo("selectedSheetIndex==19","Decision Tab");
				String[] cName = {IS_COMPLIANCE_NAME_SCR,IS_COMPLIANCE_RISK_ASSESS,IS_PROD_APP_REQ,IS_CALL_BACK_REQ};
				disableControls(cName);
				hideControls(new String[]{L1_APP_REQ,L2_APP_REQ,L3_APP_REQ,L4_APP_REQ});
				formObject.clearCombo(CRO_DEC);
				int iCount =getListCount(CRO_DEC);
				if(iCount==0) {
					formObject.getDataFromDB("Select to_char(WS_DECISION) from USR_0_DECISION_MASTER where ws_name='"+formObject.getValue(CURR_WS_NAME)+"' and route_type='"+formObject.getValue(SCAN_MODE)+"'");
					String sQuery = "Select to_char(WS_DECISION) from USR_0_DECISION_MASTER where ws_name='"+formObject.getValue(CURR_WS_NAME)+"' and route_type='"+formObject.getValue(SCAN_MODE)+"' order by to_char(WS_DECISION)";
					addDataInComboFromQuery(sQuery, CRO_DEC);
				}
				if(getGridCount(DECISION_LVW) == 0) {
					String sQuery1 = "SELECT CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,CHANNEL,WS_NAME,WS_COMMENTS FROM (SELECT TO_CHAR(CREATE_DAT,'DD/MM/YYYY HH:MI:SS AM')CREATE_DAT, USERID, USERNAME, GROUP_NAME, WS_DECISION,REJ_REASON, CHANNEL, WS_NAME, WS_COMMENTS,TO_CHAR(CREATE_DAT,'YYYYMMDDHH24:MI:SS') A FROM USR_0_AO_DEC_HIST WHERE WI_NAME = '"+sWorkitemId+"') ORDER BY A";
					List<List<String>> recordList = formObject.getDataFromDB(sQuery1);
					logInfo("","decision history query "+sQuery1);
					loadListView(recordList,"CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,CHANNEL,WS_NAME,WS_COMMENTS",DECISION_LVW);							
				}	
			} else if(tabCaption.equalsIgnoreCase("Category Change")) {	
				manageCategoryChangeSectionCPDChecker();
			}
		} catch (Exception e) {
			logError("Exception in onTabClick",e);
		}
	}
	
	private void onLoadDeliveryChecker(){
		logInfo("onLoadDeliveryChecker","Inside onLoadDeliveryChecker");
		prefLang();
		Frame_delivery();
		//refreshdocrepeater();	
		hideControls(new String[]{NIG_MAKER,NIG_CHECKER,PIC1});
		formObject.setStyle(STAFF_ID,VISIBLE,TRUE);
		formObject.setStyle(DELIVERY_DATE,VISIBLE,TRUE);
		formObject.setStyle("btn_Staff_Id",VISIBLE,TRUE);
		disableControls(new String[]{SEC_CI,SEC_SRCH_EXIST_CUST,SEC_ADD_NEW_CUSTOMER,SEC_ACC_REL,SEC_SI,
				SEC_DEL_INST,SEC_INTERNAL_DETL,SEC_PERSONAL_DET,Frame_ClientQuestions,SEC_CONTACT_DET_CP,SEC_CONTACT_DET_PA,
				SEC_CONTACT_DET_RA,SEC_INT_DETAIL,SEC_GEN_INFO,SEC_EMPLYMNT_DETAILS,SEC_FUND_EXP_RELTNSHP,SEC_ASSESS_OTH_INFO,
				SEC_BNK_REL_UAE_OVRS,SEC_SIGN_OFF,FrameFATCA,SEC_CRS_DETAILS,SEC_SS_CPD_TRSD,SEC_SS_CPD_RISK_ASSESS,
				SEC_SS_TRSD,SEC_SS_RISK_ASSESS,SEC_RISK_ASSESS_CRO,SEC_ELIG_ANALYSIS,SEC_PRODUCTION_CRO,SEC_OPT_PROD_CRO,
				SEC_FACILITY_CRO,SEC_DOC_REQ_CRO,SEC_RISK_ASSESS_CPD,SEC_ELIG_ANALYSIS_CPD,SEC_PRODUCTION_CPD,
				SEC_OPT_PROD_CPD,SEC_FACILITY_CPD,SEC_DOC_REQ_CPD,SEC_ACC_INFO_PD,SEC_ACC_INFO_ECD,SEC_ACC_INFO_DCL,
				SEC_SI_SIRFT,SEC_SI_SWP,CK_PER_DET,PD_ANY_CHNG_CUST_INFO,CHK_CONTACT_DET,CHK_INTERNAL_SEC,
				CHK_GEN_INFO,CHK_EMP_DETAIL,CHK_FUNDING_INFO,CHK_RISK_ASS,CHK_BANKING_RELATION,CHECKBOX_FATCA,CRS_CB,BTN_TRSD_CHECK,
				FINAL_ELIGIBILITY,SANCT_RISK_ASSESS_MARKS,BTN_CPD_TRSD_CHK,CPD_FINAL_ELIGIBILITY,BTN_CALCULATE,CPD_RISK_ASSESS_MARKS,BTN_NEXT_CUST,BTN_NEXT_CUST_SANCT});
		
		frame81_CPD_Disable();
		manualFrameCPDDisable();
		setCPDCheckerCombos();
		clearUdfGrid();
		int custNo = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
		logInfo("","field valuebagset custNO: "+custNo);
		String sQuery2 = "SELECT GRADUATION_DATE, SPECIAL_CUSTOMER_IDENTIFIER FROM USR_0_UDF_DETAILS "
				+ "WHERE ACC_RELATION_SERIALNO = "+custNo+" AND WI_NAME = '"+sWorkitemId+"'";
		populateUDFGrid(sQuery2);
		populateTRSD();
		populateTRSDCPD();
		disableControls(new String[]{"btn_Staff_Id"});
		setTemp_usr_0_product_selected();
		fieldValueUsr_0_Risk_Data();
	}
	
	private boolean submitWorkitem(){
		logInfo("submitWorkitem","Inside submitWorkitem");
		if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approved")) {
			String sQuery = "";
			List<List<String>>	sOutput = null;
			sQuery ="SELECT delivery_maker_dec AS DECISION FROM "+sExtTable+" WHERE WI_NAME ='"+sWorkitemId+"'";
			sOutput=formObject.getDataFromDB(sQuery);
			logInfo("submitWorkitem","Inside BTN_SUBMIT"+sOutput.get(0).get(0));
			if(sOutput!=null && sOutput.size()>0){
				logInfo("submitWorkitem","Inside BTN_SUBMIT1");
				if(sOutput.get(0).get(0).equalsIgnoreCase("Delivery is Successful")) {
					logInfo("submitWorkitem","Delivery is Successful");
					String sProcName="SP_TemplateGeneration_SMS";
//					String param = sWorkitemId+"','"+sProcessName;
					List<String> paramlist =new ArrayList<>();
					paramlist.add (PARAM_TEXT+sWorkitemId);
					paramlist.add (PARAM_TEXT+"DELIVERY_CHECKER");
					formObject.getDataFromStoredProcedure(sProcName, paramlist);
					String sFinalStatus = insertDataInIntegrationTable();
					logInfo("submitWorkitem","Returned after insertion---"+sFinalStatus);
					if(sFinalStatus.equalsIgnoreCase("Success")) {
						sQuery ="SELECT COUNT(WI_NAME) AS COUNT FROM USR_0_INTEGRATION_CALLS WHERE WI_NAME ='"+sWorkitemId+"' AND SOURCE ='"+sActivityName+"'";
						sOutput=formObject.getDataFromDB(sQuery);
						if(sOutput!=null && sOutput.size()>0){
							if(sOutput.get(0).get(0).equalsIgnoreCase("0")) {
								sendMessageValuesList("","Some error occured in starting webservice");
								return false;
							} else {
								logInfo("submitWorkitem","return true"+sOutput.get(0).get(0));
								return true;
							}
						} 
					}
				}
			}
		return true ;
		}
		return true;
	}
	
	private boolean showOpenCallJSP(String sResult) {
		loadIntegrationDataOnForm();
		if(sResult.equalsIgnoreCase("Mandatory")) {
			sendMessageValuesList("", "Mandatory calls are not successful.Please close the workitem and try again later.");
			return false;
		} else if(!sResult.equalsIgnoreCase("Success")){
			String decision=formObject.getValue(CRO_DEC).toString();
			if(sActivityName.equalsIgnoreCase(ACTIVITY_DELIVERY_CHECKER)){
				String sUpdateDecision="update "+sExtTable+" set cpd_chkr_dec='"+ decision +"' Where WI_NAME='"+ sWorkitemId +"'";
				formObject.saveDataInDB(sUpdateDecision);
			} else {
				sendMessageValuesList("","Please close and the workitem and try again later");
				return false;
			}
		}
		String sQuery ="SELECT COUNT(WI_NAME) AS COUNT FROM USR_0_INTEGRATION_CALLS WHERE WI_NAME ='"+sWorkitemId+"' AND STATUS='Success'";
		List<List<String>> output = formObject.getDataFromDB(sQuery);
		if(output!=null && output.size()>0){
			if(output.get(0).get(0).equalsIgnoreCase("0")) {
				sendMessageValuesList("","No call is successful yet. Please close the WI and open again.");
				return false;
			}
		}
		int iResult = validateIntegrationCalls();
		if(iResult == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean postSubmit(){
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		String sDate = dateFormat.format(d);
		int sOutput=updateDataInDB(sExtTable,"delivery_checker_name ,DELIVERY_CHECKER_DEC,WI_COMPLETED_FROM,"
				+ "DELIVERY_CHECKER_SUBMIT_DATE","'"+sUserName+"'"+(char)25+"'"+formObject.getValue(CRO_DEC)+"'"+(char)25+"'"
				+ sActivityName+"'"+(char)25+"'"+sDate+"'","WI_NAME ='"+sWorkitemId+"'");
		logInfo("postSubmit","sOutput-----"+sOutput);
		createHistory();				
		createAllHistory();
		return true;
	}
}
