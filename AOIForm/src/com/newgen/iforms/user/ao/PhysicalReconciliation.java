package com.newgen.iforms.user.ao;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONArray;
import org.xml.sax.SAXException;

import com.newgen.iforms.EControl;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.iforms.user.config.Constants;
import com.newgen.mvcbeans.model.WorkdeskModel;

public class PhysicalReconciliation extends Common implements Constants, IFormServerEventHandler{

	public PhysicalReconciliation(IFormReference formObject) {
		super(formObject);
	}

	@Override
	public void beforeFormLoad(FormDef arg0, IFormReference arg1) {		
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
		logInfo("executeServerEvent", "Inside Physical Reconciliation");
		List<List<String>> list;
		sendMessageList.clear();
		logInfo("executeServerEvent", "sendmessagelist49");
		String retMsg = getReturnMessage(true);
		List<List<String>> recordList;
		String controlValue = formObject.getValue(controlName).toString();
		try {
			if(eventType.equalsIgnoreCase(EVENT_TYPE_LOAD)) {
				logInfo("executeServerEvent", "INSIDE CLICK EVENT CUST SCREENING");
				disableControls(new String[]{BTN_SUBMIT});
				onLoadPhysicalReconciliation();
				int iRows = getGridCount(ACC_RELATION);
				if(iRows>0){
					String sFinalName = formObject.getTableCellValue(ACC_RELATION, 0, 7);
					String sFinalDOB = formObject.getTableCellValue(ACC_RELATION, 0, 1);
					String cust_id = formObject.getTableCellValue(ACC_RELATION, 0, 0);
					formObject.setValue(TXT_CUSTOMERNAME, cust_id);
					formObject.setValue(TXT_CUSTOMERID, sFinalName);
					formObject.setValue(TXT_DOB, sFinalDOB);
				}
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)){
				if(TABCLICK.equalsIgnoreCase(controlName)) {
					onTabPhysicalReconciliation(data);
				} else if(BTN_SUBMIT.equalsIgnoreCase(controlName)){
					logInfo("submitWorkitem","Inside submitWorkitem");
					if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")) {
						logInfo(CRO_DEC,"INSIDE Approve");
						if(formObject.getValue(CHUBB).toString().equalsIgnoreCase("")) {
							logInfo(CHUBB,"INSIDE:");
							sendMessageValuesList(CHUBB,"Please Fill Barcode details.");
						}
					}
				} else if("postSubmit".equalsIgnoreCase(controlName)) {
					createHistory();
					createAllHistory();
					Date d = new Date();
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					String sDate = dateFormat.format(d); 
					System.out.println("Value of decision: "+formObject.getValue(CRO_DEC));
					formObject.setValue(PHYSICAL_RECON_DECISION,formObject.getValue(CRO_DEC).toString());
					formObject.setValue(PHYSICAL_RECON_DATE,sDate);
					formObject.setValue(PHYSICAL_RECON_USER,sUserName);
				}
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_CHANGE)) {
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
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_LOSTFOCUS)) {  
			}
		} catch (Exception e) {
			logError("Exception in Event- " + eventType + "control name- " +controlName+ ": ", e);
		} finally{
			logInfo("executeServerEvent", "sendMessageList");
			if(!sendMessageList.isEmpty()){
				return  getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1).toString());
			}
		}
		return getReturnMessage(true, controlName);
	}

	private void onLoadPhysicalReconciliation() {
		logInfo("onLoadPhysicalReconciliation","Inside onLoadPhysicalReconciliation: ");
		try {
			prefLang();
			Frame_delivery();
			Frame18_CPD_Disable();
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
			setCPDCheckerCombos(); 
			clearUdfGrid();
			int custNo = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
			log.info("field valuebagset custNO: "+custNo);
			String sQuery2 = "SELECT GRADUATION_DATE, SPECIAL_CUSTOMER_IDENTIFIER FROM USR_0_UDF_DETAILS WHERE ACC_RELATION_SERIALNO = "+custNo+" AND WI_NAME = '"+sWorkitemId+"'";
			populateUDFGrid(sQuery2);
			populateTRSD();
			populateTRSDCPD();
			setTemp_usr_0_product_selected();
			fieldValueUsr_0_Risk_Data();
			manualFrameCPDDisable();
		} catch (Exception e) {
			logError("Exception onPhysicalReconciliation", e);
		}
	}

	public void onTabPhysicalReconciliation(String data){
		logInfo("onTabPhysicalReconciliation", "INSIDE");
		try{
			logInfo("onTabClick", "INSIDE");
			String[] selectedSheetInfo = data.split(",");
			String tabCaption = selectedSheetInfo[0];
			int selectedSheetIndex = Integer.parseInt(selectedSheetInfo[1]);
			if(selectedSheetIndex == 1 || selectedSheetIndex == 2 || selectedSheetIndex == 3 || selectedSheetIndex == 4 || selectedSheetIndex == 5) {	
				disableControls(new String[]{BTN_SUBMIT});
				setManualFieldsBlank();
				setCPDCheckerCombos();
				populatePersonalDataCPD();
				populateRiskData();
				populateKYCData();
				populateKycMultiDrop();
				populatePreAssesmentDetails();  //shahbaz
				populateComparisonFields();
				populatePrivateClientQuestions(); 
				loadDedupeSearchData(sWorkitemId);
				visibleonSegmentBasisCPDCHECKER(formObject.getValue(PD_CUSTSEGMENT).toString());
			} else if(selectedSheetIndex == 6 || selectedSheetIndex == 7) {
				disableControls(new String[]{BTN_SUBMIT});
				if(!formObject.getValue(SCAN_MODE).toString().equalsIgnoreCase("New WMS ID")) {
					PopulateScreeningDataCRO_History();
				}
				populateScreeningDataCPD_History();
			} else if(selectedSheetIndex == 8 || selectedSheetIndex == 9) {	
				disableControls(new String[]{BTN_SUBMIT});
				if(!formObject.getValue(SCAN_MODE).toString().equalsIgnoreCase("New WMS ID")) {
					loadApplicationAssessmentData_History();
				}
				loadApplicationAssessmentDataCPD_History();
				String sQuery= "";
				List<List<String>> recordList = null;
				if(formObject.getValue(SCAN_MODE).toString().equalsIgnoreCase("New WMS ID")) {
					loadApplicationAssessmentDataCPD();		
					if(getGridCount(PROD_SEC_OFRD_CPD_LVW) == 0) {
						sQuery = "SELECT PRODUCT_CODE,PRODUCT_DESC,CURRENCY_DESC,'' FROM USR_0_PRODUCT_OFFERED_CPD WHERE WI_NAME = '"+sWorkitemId+"' ORDER BY TO_NUMBER(PRODUCT_CODE)";				
						recordList = formObject.getDataFromDB(sQuery);
						log.info(sQuery);
						loadListView(recordList,"PRODUCT_CODE,PRODUCT_DESC,CURRENCY_DESC,PROD_ACCT_OPNG",PROD_SEC_OFRD_CPD_LVW);
					} 
					if(getGridCount(FAC_LVW_CPD) == 0) {
						String sQuery1 = "SELECT CID,FACILITY,DESCRIPTION FROM USR_0_FACILITY_SELECTED_CPD WHERE WI_NAME = '"+sWorkitemId+"' ORDER BY FACILITY";
						recordList = formObject.getDataFromDB(sQuery1);
						log.info(sQuery1);
						loadListView(recordList,"CID,FACILITY,DESCRIPTION",FAC_LVW_CPD);
					}
					if(getGridCount(FAC_OFRD_LVW_CPD) == 0) {
						String sQuery2  =  "SELECT CUST_ID,CUST_NAME,CARD_TYPE FROM USR_0_DEBITCARD_OFFERED_CPD WHERE WI_NAME = '"+sWorkitemId+"'";		
						recordList = formObject.getDataFromDB(sQuery2);
						log.info(sQuery2);
						loadListView(recordList,"CUST_ID,CUST_NAME,CARD_TYPE",FAC_OFRD_LVW_CPD);
					}
				} else {
					log.info("inside else block of loadApplicationAssessmentData ");
					if(getGridCount(FAC_OFRD_LVW_CRO) == 0) {
						String sQuery3  =  "SELECT CUST_ID,CUST_NAME,CARD_TYPE FROM USR_0_DEBITCARD_OFFERED WHERE WI_NAME = '"+sWorkitemId+"'";		
						recordList = formObject.getDataFromDB(sQuery3);
						log.info(sQuery3);
						loadListView(recordList,"CUST_ID,CUST_NAME,CARD_TYPE",FAC_OFRD_LVW_CRO);
					}
					if(getGridCount (PROD_OFRD_CRO_LVW)  == 0) {
						String sQuery4  =  "SELECT PRODUCT_CODE,PRODUCT_DESC,CURRENCY_DESC,'' FROM USR_0_PRODUCT_OFFERED WHERE WI_NAME = '"+sWorkitemId+"' ORDER BY TO_NUMBER(PRODUCT_CODE)";				
						recordList = formObject.getDataFromDB(sQuery4);
						log.info(sQuery4);
						loadListView(recordList,"PRODUCT_CODE,PRODUCT_DESCRIPTION,CURRENCY,PROD_ACC_OPENING",PROD_OFRD_CRO_LVW);
					}
					if(getGridCount(FAC_LVW_CRO) == 0) {
						String sQuery5 = "SELECT CID,FACILITY,DESCRIPTION FROM USR_0_FACILITY_SELECTED WHERE WI_NAME = '"+sWorkitemId+"' ORDER BY FACILITY";
						recordList = formObject.getDataFromDB(sQuery5);
						log.info(sQuery5);
						loadListView(recordList,"CID,FACILITY,DESCRIPTION",FAC_LVW_CRO);
					}
					loadApplicationAssessmentData();
				} 
			} else if(selectedSheetIndex==10) {
				disableControls(new String[]{BTN_SUBMIT});
				logInfo("onTabClick", "INSIDE 8");
				disableControls(new String[]{BTN_SUBMIT});
				log.info("inside on click tab 9 Account Info ");
				int gridCount = getGridCount(ACC_INFO_EDC_LVW);
				log.info("COUNT OF GRID = "+gridCount);
				if(gridCount ==  0) {
					String sQuery6  =  "SELECT CUST_ID,EMBOSS_NAME,CARD_TYPE_DESC,STATUS_DESC,ISSUANCE_DATE,EXPIRY_DATE FROM USR_0_DEBITCARD_EXISTING WHERE WI_NAME = '"+sWorkitemId+"'";		
					List<List<String>> recordList = formObject.getDataFromDB(sQuery6);
					log.info("query for account info " +sQuery6);
					loadListView(recordList,"CUST_ID,EMBOSS_NAME,CARD_TYPE_DESC,STATUS_DESC,ISSUANCE_DATE,EXPIRY_DATE",ACC_INFO_EDC_LVW);	
				}
			} else if(tabCaption.equalsIgnoreCase("Standing Instruction")) {
				disableControls(new String[]{BTN_SUBMIT});
				loadSICombos_History();
				populateStndInstr();
			} else if(tabCaption.equalsIgnoreCase("Delivery Preferences")) {
				disableControls(new String[]{BTN_SUBMIT});
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
					log.info("decision history query "+sQuery1);
					loadListView(recordList,"CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,CHANNEL,WS_NAME,WS_COMMENTS",DECISION_LVW);							
				}				
			} else if(tabCaption.equalsIgnoreCase("Category Change")) {
				manageCategoryChangeSectionCPDChecker();
			}
		} catch (Exception e) {
			logError("Exception in onPhysicalReconciliation", e);
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
