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

public class CustomerScreening extends Common implements Constants, IFormServerEventHandler{

	public CustomerScreening(IFormReference formObject) {
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
		logInfo("executeServerEvent", "Inside Customer Screening");
		List<List<String>> list;
		String sQuery = "";
		sendMessageList.clear();
		logInfo("executeServerEvent", "sendmessagelist49");
		String retMsg = getReturnMessage(true);
		try {
			if(eventType.equalsIgnoreCase(EVENT_TYPE_LOAD)) {
				onLoadCustScreening();
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)){
				logInfo("executeServerEvent", "INSIDE CLICK EVENT CUST SCREENING");
				if(TABCLICK.equalsIgnoreCase(controlName)) {
					onTabCustScreening(data);
				} else if(controlName.equalsIgnoreCase(EDIT)) {
					logInfo("executeServerEvent","EDIT BUTTON");
					//formObject.setStyle(EDIT1, DISABLE, TRUE);
					String sUpdateDecision="update "+sExtTable+" set back_route_flag='true' Where WI_NAME='"+ sWorkitemId +"'";
					int sout = formObject.saveDataInDB(sUpdateDecision);
					logInfo("executeServerEvent","sUpdateDecision: "+sUpdateDecision+" sout: "+sout);
					formObject.setValue(CUST_PROCESSED, Integer.parseInt(formObject.getValue(CUST_PROCESSED).toString())-1+"");
					sBackRoute = TRUE;
				} else if(BTN_SUBMIT.equalsIgnoreCase(controlName)){
					if(submitWorkitem(controlName)){
						return getReturnMessage(true,controlName);
					}
				} else if("saveNextTabClick".equalsIgnoreCase(controlName)) {
					if(saveAndNextValidations((String)data.split(",")[1])) {
						logInfo("saveNextTabClick","saveAndNextValidations successful");
						return getReturnMessage(true, controlName);
					}
				} else if(controlName.equalsIgnoreCase(BTN_RECHECK_SANC)) {
					loadBlackListData();
					//formObject.setValue(SANC_FINAL_ELIGIBILITY, "--Select--");
					//formObject.setValue("COMBO7", "--Select--");
				}
				else if(controlName.equalsIgnoreCase(BTN_ADD_SANC_SCRN)) {
					boolean result = validateCentralBankData(SANC_CNTRL_BNK_BAD_LVW);
					logInfo("ADD_CRO result -",""+result);
					if(!result) {
						return getReturnMessage(true, controlName);
					}
				}
				else if(controlName.equalsIgnoreCase(BTN_SANC_CALCULATE)) {
					logInfo("execute server event ","inside  click CALCULATE");
					//formObject.setValue(SANC_FINAL_ELIGIBILITY, "VALUE");
					logInfo("execute server event ","inside  click CALCULATE 8");
				}
				else if( controlName.equalsIgnoreCase(BTN_TRSD_CHECK))  {
					logInfo("execute server event ","Click event called without click");
					/*callTRSD("CRO");
					if( formObject.getValue(TRSD_FINAL_DECISION).toString().equalsIgnoreCase("Approved")) {
						formObject.setValue(SANC_FINAL_ELIGIBILITY, "Eligible");
					} else {
						formObject.setValue(SANC_FINAL_ELIGIBILITY, "Not Eligible");
					}
					insertBankDecisionFromTRSD(formObject.getValue(TRSD_FINAL_DECISION).toString());	//added by harinder
					if(formObject.getValue(TRSD_FINAL_DECISION).toString().equalsIgnoreCase("Approved") 
							|| formObject.getValue(TRSD_FINAL_DECISION).toString().equalsIgnoreCase("Pending")) {							
						formObject.setStyle(BTN_TRSD_CHECK,DISABLE,TRUE);
					}	*/
					// Added by reyaz 02-05-2023 
					logInfo("CLICK","INSIDE FSK_CHECK data"+data);
					callFSKService("CRO");
					logInfo("execute server event ","Trsd final decision"+formObject.getValue(TRSD_FINAL_DECISION).toString());
					if( formObject.getValue("TRSD_FLAG").toString().equalsIgnoreCase("N")) {
						formObject.setValue(SANC_FINAL_ELIGIBILITY, "Eligible");
					} else {
						formObject.setValue(SANC_FINAL_ELIGIBILITY, "Not Eligible");
					}
					insertBankDecisionFromTRSD(formObject.getValue(TRSD_FINAL_DECISION).toString());	//added by harinder
					if(formObject.getValue("TRSD_FLAG").toString().equalsIgnoreCase("N")
							|| formObject.getValue("TRSD_FLAG").toString().equalsIgnoreCase("Y")) {							
						formObject.setStyle(BTN_TRSD_CHECK,DISABLE,TRUE);
					}
					logInfo("CLICK"," SANCT_RISK_NAME Value:::"+formObject.getValue("SANCT_RISK_NAME").toString());
					logInfo("CLICK"," SANC_FINAL_ELIGIBILITY Value:::"+formObject.getValue("SANC_FINAL_ELIGIBILITY").toString());
				}
				else if(controlName.equalsIgnoreCase(VIEW)) {
					logInfo("ExecuteServerEvent","INSIDE view button");
					formObject.clearTable(LVW_DEDUPE_RESULT);
					int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
					sQuery = "SELECT CUST_ID,CUST_NAME,CUST_IC,CUST_PASSPORT,CUST_EMAIL,CUST_MOBILE,'','',to_date(CUST_DOB,'"+DATEFORMAT+"'),CUST_EIDA,CUST_NATIONALITY,system_type"
							+ " FROM USR_0_DUPLICATE_SEARCH_DATA "
							+ "WHERE WI_NAME='"+sWorkitemId+"' AND CUST_SNO ='"+iSelectedRow+"'";
					logInfo("VIEW BUTTON","button query"+sQuery);
					List<List<String>> recordList = formObject.getDataFromDB(sQuery);
					loadListView(recordList,"CID,Name,CustomerIC,PassportNo,Email,Mobile,DebitCardNo,CreditCardNo,DOB,EIDANo,Nationality,System",LVW_DEDUPE_RESULT);
					sQuery = "select dedupe_selected_index from USR_0_CUST_TXN Where WI_NAME='"+ sWorkitemId +"' and cust_sno='"+iSelectedRow+"'";
					logInfo("executeServerEvent",sQuery);
					recordList = formObject.getDataFromDB(sQuery);
					logInfo("ExecuteServerEvent",""+recordList);//need to remove 
					int  index1 = Integer.parseInt(recordList.get(0).get(0));
					logInfo("inside executeServerEvent",index1+"");
					int[] intA = new int[1];
					intA[0] = index1;
					return getReturnMessage(true,recordList.get(0).get(0),"s");
					//formObject.setNGLVWSelectedRows(LVW_DEDUPE_RESULT,  intA );
				} else if (controlName.equalsIgnoreCase(BTN_APP_LEVEL_RISK)){
					appLevelMandatoryCheck = false;
					executeApplicationAssessmentRisk();
				}
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_CHANGE)) {
				logInfo("executeServerEvent","INSIDE change event: ");
				/* NEED TO BE CHECKED FARDEEN 
				 * AND KRITIKA
				 * */
				if(controlName.equalsIgnoreCase(SANCT_RISK_CURRENT_RSK_BANK)) {
					logInfo("executeServerEvent ","INSIDE SANCT_RISK_CURRENT_RSK_BANK"+data);
					if(!data.equalsIgnoreCase("")) {
						if(formObject.getValue(SANCT_RISK_CURRENT_RSK_SYSTEM).toString().equalsIgnoreCase("Neutral Risk")) {
							if(formObject.getValue(SANCT_RISK_CURRENT_RSK_BANK).toString().equalsIgnoreCase("")) {
								logInfo(SANCT_RISK_CURRENT_RSK_BANK,"ON CHANGE");
								sendMessageValuesList("",CA0144);
								formObject.setValue(SANCT_RISK_CURRENT_RSK_BANK,"");
								return getReturnMessage(true, controlName);
							}
						}else if(formObject.getValue(SANCT_RISK_CURRENT_RSK_SYSTEM).toString().equalsIgnoreCase("Medium Risk")) {		//changed 27022023
							if(formObject.getValue(SANCT_RISK_CURRENT_RSK_BANK).toString().equalsIgnoreCase("")) {
								logInfo(SANCT_RISK_CURRENT_RSK_BANK,"ON CHANGE");
								sendMessageValuesList("",CA0144);
								formObject.setValue(SANCT_RISK_CURRENT_RSK_BANK,"");
								return getReturnMessage(true, controlName);
							}
						}
						else if(( formObject.getValue(SANCT_RISK_CURRENT_RSK_SYSTEM).toString()).equalsIgnoreCase("Increased Risk")) {
							if((( formObject.getValue(SANCT_RISK_CURRENT_RSK_BANK).toString()).equalsIgnoreCase("Neutral")) ||
							(( formObject.getValue(SANCT_RISK_CURRENT_RSK_BANK).toString()).equalsIgnoreCase("Medium Risk"))){			//changed 27022023
								sendMessageValuesList("",CA0144);
								formObject.setValue(SANCT_RISK_CURRENT_RSK_BANK,"");
								return getReturnMessage(true, controlName);
							}
						}
						else if((formObject.getValue(SANCT_RISK_CURRENT_RSK_SYSTEM).toString()).equalsIgnoreCase("UAE-PEP")) {
							if(((formObject.getValue(SANCT_RISK_CURRENT_RSK_BANK).toString()).equalsIgnoreCase("Neutral"))
									||(formObject.getValue(SANCT_RISK_CURRENT_RSK_BANK).toString()).equalsIgnoreCase("Medium Risk")			//changed 27022023
									||(formObject.getValue(SANCT_RISK_CURRENT_RSK_BANK).toString()).equalsIgnoreCase("Increased Risk")) {
								sendMessageValuesList("",CA0144);
								formObject.setValue(SANCT_RISK_CURRENT_RSK_BANK,"");
								return getReturnMessage(true, controlName);
							}
						}
						else if((formObject.getValue(SANCT_RISK_CURRENT_RSK_SYSTEM).toString()).equalsIgnoreCase("Unacceptable Risk")) {
							if(((formObject.getValue(SANCT_RISK_CURRENT_RSK_BANK).toString()).equalsIgnoreCase("Neutral"))
									||(formObject.getValue(SANCT_RISK_CURRENT_RSK_BANK).toString()).equalsIgnoreCase("Medium Risk")			//changed 27022023
									||(formObject.getValue(SANCT_RISK_CURRENT_RSK_BANK).toString()).equalsIgnoreCase("Increased Risk")
									||(formObject.getValue(SANCT_RISK_CURRENT_RSK_BANK).toString()).equalsIgnoreCase("PEP"))  {
								sendMessageValuesList("",CA0144);
								formObject.setValue(SANCT_RISK_CURRENT_RSK_BANK,"");
								return getReturnMessage(true, controlName);
							}
						}
						String sPrevRisk  ="";
						String sPrevRiskDate = "";
						String sComplainceApproval = "";
						String sCustID = formObject.getValue(SANCT_RISK_CID).toString();
						if(!sCustID.equalsIgnoreCase("")) {
							sQuery = "SELECT CURRENT_RISK_SYSTEM,TO_CHAR(PREV_RISK_DATE,'dd/mm/yyyy') "
									+ "PREV_RISK_DATE FROM USR_0_RISK_ASSESSMENT_DATA WHERE CUST_ID='"+sCustID
									+"' AND PREV_RISK_DATE =(SELECT MAX(PREV_RISK_DATE) FROM "
									+ "USR_0_RISK_ASSESSMENT_DATA_CPD WHERE CUST_ID='"+sCustID+"') AND ROWNUM=1";
							list = formObject.getDataFromDB(sQuery);
							if(list.size()>0) {
								sPrevRisk = list.get(0).get(0);
								sPrevRiskDate = list.get(0).get(1);
							}
						}
						if(data.equalsIgnoreCase("Neutral") || data.equalsIgnoreCase("Medium Risk") ) {
							sComplainceApproval = "No";
						}
						else if(data.equalsIgnoreCase(sPrevRisk) && !sPrevRiskDate.equalsIgnoreCase("")) {
							sComplainceApproval = "No";
						} 
						else {
							sComplainceApproval = "Yes";
						}
						//						formObject.setValue(CUST_COM_APP, sComplainceApproval);
						formObject.setValue(SANCT_RISK_COMPL_APP_REQ, sComplainceApproval);
					}
					else {
						logInfo("before calling set_Values_From_Usr_0_Risk_Data in custScreening cust_curr_risk_bank is null","");
						set_Values_From_Usr_0_Risk_Data();
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

	public boolean generateTemplate() {
		String sReqType = formObject.getValue(REQUEST_TYPE).toString();
		String sProdCode ="";
		int iRows = getGridCount(PRODUCT_QUEUE);
		if(!sReqType.equalsIgnoreCase("Category Change Only")) {
			if(iRows <= 1) {
				sendMessageValuesList("", "Please add atleast one product.");
			}
			for(int i=1;i<iRows;i++) {
				sProdCode = formObject.getTableCellValue(PRODUCT_QUEUE,i,1);
				if(sProdCode.equalsIgnoreCase("")) {
					sendMessageValuesList(PRODUCT_QUEUE, "Blank Row is not allowed.");
				}
			}
		}
		//String response= wfsave_status();//jsp
		if(sReqType.equalsIgnoreCase("Category Change Only") || iRows>1) {  
			String param = sWorkitemId+"','"+sProcessName;
			List<String> paramlist =new ArrayList<>();
			paramlist.add (PARAM_TEXT+sWorkitemId);
			paramlist.add (PARAM_TEXT+sProcessName);
			formObject.getDataFromStoredProcedure("SP_TemplateGeneration_Forms", paramlist);
			try {
				String qryRepeater = "SELECT CUSTOMER_NAME AS CUST_NAME, TEMPLATE_NAME AS DOC_NAME, "
						+ "DECODE(GENERATION_STATUS, 'Y', 'Generated', 'F', 'Failed', 'WIP') AS STATUS, '' "
						+ "AS REMARKS, WORKITEM_NAME AS WI_NAME, DECODE(GENERATION_STATUS, 'Y', 'Y', 'N') "
						+ "AS IS_GENERATED, TMP_CATEGORY AS RETRY, '' AS custsno, '' AS ftp_status, "
						+ "ENTRY_DATE_TIME AS entrydatetime FROM NG_TEMPLATE_TRANSACTION "
						+ "WHERE WORKITEM_NAME = '"+ sWorkitemId +"' AND TMP_CATEGORY = 'Other' "
						+ "ORDER BY CUSTOMER_NAME";
				System.out.println("qryRepeater-----"+qryRepeater);
				List<List<String>> sOutRepeater = formObject.getDataFromDB(qryRepeater);
				logInfo(""," sOutRepeater     ===== "+sOutRepeater);
				//String sRecordStr= sOutRepeater.getTagValue(sOutRepeater,"Record");
				//NGRepeater objChkRepeaterDoc = formObject.getNGRepeater("Frame58");
				String CUST_NAME = "";
				String DOC_NAME = "";
				String STATUS = "";
				String REMARKS = "";
				String WI_NAME = "";
				String IS_GENERATED = "";
				String RETRY = "";
				String CUSTSNO = "";
				String FTP_STATUS = "";
				String ENTRYDATETIME = "";
				int iRowsDoc = getGridCount("temp_gen_queue");
				formObject.clearTable("temp_gen_queue");
				int sCount = sOutRepeater.size();
				if(sOutRepeater.size() > 0 && null != sOutRepeater.get(0)) {
					for(int i = iRowsDoc; i <= sCount ;i++) {
						CUST_NAME = sOutRepeater.get(i).get(0);
						DOC_NAME = sOutRepeater.get(i).get(1);
						STATUS = sOutRepeater.get(i).get(2);
						REMARKS = sOutRepeater.get(i).get(3);
						WI_NAME = sOutRepeater.get(i).get(4);
						IS_GENERATED = sOutRepeater.get(i).get(5);
						RETRY = sOutRepeater.get(i).get(6);
						CUSTSNO = sOutRepeater.get(i).get(7);
						FTP_STATUS = sOutRepeater.get(i).get(8);
						ENTRYDATETIME = sOutRepeater.get(i).get(9);
						String coloumnName = "Customer_Name,Document_name,Status,Remarks,wi_name,Is_generated,retry,custsno,"
								+ "ftp_status,entrydatetime";
						String tableName = "temp_gen_queue";
						String values = CUST_NAME+"##"+DOC_NAME+"##"+STATUS+"##"+REMARKS+"##"+WI_NAME+"##"+IS_GENERATED+
								"##"+RETRY+"##"+CUSTSNO+"##"+FTP_STATUS+"##"+ENTRYDATETIME;
						LoadListViewWithHardCodeValues(tableName,coloumnName,values);
					}
				} else {
					sendMessageValuesList("", "No Doc Types For This Application");
					return false;
				}


				if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("New Account")) {
					//								formObject.setNGSelectedTab("Tab5",8);
					//								formObject.NGFocus("button_refresh");
				} else {
					//								formObject.setNGSelectedTab("Tab5",9);
					//								formObject.NGFocus("button_refresh");
				}
			} catch (Exception e) {
				logError("onLoadCustScreening", e);
				if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("New Account")) {
					//					formObject.setNGSelectedTab("Tab5",8);
					//					formObject.NGFocus("button_refresh");
				} else {
					//					formObject.setNGSelectedTab("Tab5",9);
					//					formObject.NGFocus("button_refresh");
				}
				return false;
			}
		}
		else {
			//formObject.RaiseEvent("WFSave");
			return false;
		}
		return true;
	}	

	public void refreshDocRepeater() {
		logInfo("refreshDocRepeater","into refreshdocrepeater ");
		try {
			String qryRepeater = "SELECT CUSTOMER_NAME AS CUST_NAME, TEMPLATE_NAME AS DOC_NAME, DECODE(GENERATION_STATUS, 'Y', 'Generated', 'F', 'Failed', 'WIP') AS STATUS, '' AS REMARKS, WORKITEM_NAME AS WI_NAME, DECODE(GENERATION_STATUS, 'Y', 'Y', 'N') AS IS_GENERATED, TMP_CATEGORY AS RETRY, '' AS custsno, '' AS ftp_status, ENTRY_DATE_TIME AS entrydatetime FROM NG_TEMPLATE_TRANSACTION WHERE WORKITEM_NAME = '"+ sWorkitemId +"' AND TMP_CATEGORY = 'Other' ORDER BY CUSTOMER_NAME";
			logInfo("refreshDocRepeater",qryRepeater);
			List<List<String>> sOutRepeater = formObject.getDataFromDB(qryRepeater);
			logInfo("refreshDocRepeater","" +sOutRepeater);
			String CUST_NAME = "";
			String DOC_NAME = "";
			String STATUS = "";
			String REMARKS = "";
			String WI_NAME = "";
			String IS_GENERATED = "";
			String RETRY = "";
			String CUSTSNO = "";
			String FTP_STATUS = "";
			String ENTRYDATETIME = "";
			int iRowsDoc = getGridCount("temp_gen_queue");
			formObject.clearTable("temp_gen_queue");
			int sCount = sOutRepeater.size();
			if(sOutRepeater.size() > 0 && null != sOutRepeater.get(0)) {
				for(int i = iRowsDoc; i <= sCount ;i++) {
					CUST_NAME = sOutRepeater.get(i).get(0);
					DOC_NAME = sOutRepeater.get(i).get(1);
					STATUS = sOutRepeater.get(i).get(2);
					REMARKS = sOutRepeater.get(i).get(3);
					WI_NAME = sOutRepeater.get(i).get(4);
					IS_GENERATED = sOutRepeater.get(i).get(5);
					RETRY = sOutRepeater.get(i).get(6);
					CUSTSNO = sOutRepeater.get(i).get(7);
					FTP_STATUS = sOutRepeater.get(i).get(8);
					ENTRYDATETIME = sOutRepeater.get(i).get(9);
				}
			}
		} catch (Exception e) {
			logError("refreshDocRepeater", e);
		}
	}


	private void onLoadCustScreening() {
		logInfo("onLoadCustScreenig","Inside onLoadCustScreenig Customer Screen");
		try {
			String query = "";
			List<List<String>> sOutput ;
			disableControls(new String[]{CRO_SYS_DEC,SANC_FINAL_ELIGIBILITY});
			formObject.setValue(WI_NAME,sWorkitemId);
			formObject.setValue(CRO_BANK_DECISION,"");
			formObject.setValue(CURR_WS_DETAIL,sActivityName);
			formObject.setStyle(CURR_WS_DETAIL,VISIBLE, FALSE);
			formObject.setValue(TXT_CURRWS, ACTIVITY_CUST_SCREEN);//Label_WS_Name
			//Enable_Disable_Load(sCurrTabIndex, sActivityName); to be checked
			try {
				int iProcessedCustomer=0;			
				iProcessedCustomer=Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()); 				
				formObject.setValue(TXT_CUSTOMERNAME, formObject.getTableCellValue(ACC_RELATION,iProcessedCustomer,1));
				formObject.setValue(TXT_CUSTOMERID, formObject.getTableCellValue(ACC_RELATION,iProcessedCustomer, 2));
				formObject.setValue(TXT_DOB, formObject.getTableCellValue(ACC_RELATION,iProcessedCustomer,5));
			} catch (Exception e) {
				logError("onLoadCustScreening", e);
			}
			query = "Select VISA_STATUS from USR_0_VISA_STATUS order by 1";
			sOutput = formObject.getDataFromDB(query);
			if(sOutput!=null && sOutput.size()>0){
				for(List<String> valueOfColumns: sOutput){
					for(String value : valueOfColumns){
						formObject.addItemInCombo(MANUAL_VISASTATUS,value);
					}
				}
			}
			query = "Select PASSPORT_TYPE from USR_0_PASSPORT_TYPE order by 1";
			sOutput = formObject.getDataFromDB(query);
			if(sOutput.size() > 0){
				for(List<String> valueOfColumns: sOutput){
					for(String value : valueOfColumns){
						formObject.addItemInCombo(MANUAL_PASSTYPE,value);//visaStatus_manual
					}
				}
			}
			populatePassAndVisaFields();
			disableControls(new String []{CRO_SYS_DEC,SANC_FINAL_ELIGIBILITY,HIDDEN_SEC_ACC_REL,SEC_CRS_DETAILS,
					FRAME_CLIENTQUESTIONS,SEC_CONTACT_DET_RA,SEC_CONTACT_DET_PA,SEC_CONTACT_DET_CP,SEC_INT_DETAIL});// added 6 sections in frame4 as frame 4 is entire custinfotab
			disableControls(new String []{CK_PER_DET,CHK_CONTACT_DET,CHK_INTERNAL_SEC,CHECKBOX_FATCA,CRS_CB});
			int custProcessed= Integer.parseInt(formObject.getValue(CUST_PROCESSED).toString());
			int searched=Integer.parseInt(formObject.getValue(NO_OF_CUST_SEARCHED).toString());
			logInfo("onLoadCustScreening:","Searched : "+searched);
			if(custProcessed == searched+1){
				formObject.setStyle(BTN_NEXT_CUST_SANCT,DISABLE,TRUE);
				formObject.setStyle(BTN_SUBMIT,DISABLE,FALSE);
			}
			else {
				formObject.setStyle(BTN_NEXT_CUST_SANCT,DISABLE,FALSE);
				formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
			}
			if(((String) formObject.getValue(SCAN_MODE)).equalsIgnoreCase("New WMS ID")){
				formObject.setStyle(BTN_NEXT_CUST_SANCT,DISABLE,FALSE);
				formObject.setStyle(BTN_SUBMIT,DISABLE,FALSE);
			}
			frame81_CPD_Disable();
			populateScreeningDataCRO();
			loadBlackListData();
			loadCustData();
			formObject.setStyle(BTN_SUBMIT,DISABLE,FALSE);
			if(sActivityName.equalsIgnoreCase(ACTIVITY_CUST_SCREEN_QDE)){
				frame18_QDE_Disable();
			}
			int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
			query="SELECT count(1) CNT from usr_0_trsd_details where wi_name='"+sWorkitemId
					+"' and (sysdate-trsd_screeningdate)>3 and acc_relation_sno='"+iSelectedRow+"'";
			sOutput = formObject.getDataFromDB(query);
			if(!sOutput.get(0).get(0).equalsIgnoreCase("0") || !validateLastTRSD(iSelectedRow)) {
				query= "DELETE FROM usr_0_trsd_details WHERE WI_NAME ='"+sWorkitemId+"' and acc_relation_sno='"+iSelectedRow+"'";
				formObject.saveDataInDB(query);
			}
			clearUdfGrid();
			int custNo = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
			query="SELECT GRADUATION_DATE, SPECIAL_CUSTOMER_IDENTIFIER FROM USR_0_UDF_DETAILS WHERE ACC_RELATION_SERIALNO='"+custNo+"' AND WI_NAME='"+sWorkitemId+"'";
			sOutput = formObject.getDataFromDB(query);
			if(sOutput!=null && sOutput.size()>0) { 
				populateUDFGrid(sOutput.toString());
			} 
			logInfo("onLoadCustScreening","one step before going into populateTRSD");
			populateTRSD();
			logInfo("onLoadCustScreening","log after  populateTRSD");
			populateTRSDRemarks();
			populatePreAssesmentDetails();  //shahbaz
			logInfo("onLoadCustScreening","before  populatePepAssesmentDetails");
			populatePepAssesmentDetails(); //reyaz
			logInfo("onLoadCustScreening","after  populatePepAssesmentDetails");
			if( ((String) formObject.getValue("TRSD_FLAG")).equalsIgnoreCase("N"))
				formObject.setValue(SANC_FINAL_ELIGIBILITY, "Eligible");
			else
				formObject.setValue(SANC_FINAL_ELIGIBILITY, "Not Eligible");
			fieldValueUsr_0_Risk_Data();
			query="select PROD_CODE,CURRENCY from usr_0_product_selected where wi_name='"+sWorkitemId+"'";
			sOutput = formObject.getDataFromDB(query);

			/*String sQuery = "select to_number(CUST_PROCESSED) -  to_number(no_of_cust_searched) as diff"
					+ " from ext_ao where wi_name = '"+sWorkitemId+"'";
			String accOwnType = formObject.getValue(ACC_OWN_TYPE).toString();
   		    logInfo("onLoadCustScreening","sQuery : "+sQuery);
			sOutput = formObject.getDataFromDB(sQuery);
			logInfo("onLoadCustScreening","sOutput : "+sOutput);
			if(sOutput!=null && sOutput.size()>0) { 
				if("1".equalsIgnoreCase(sOutput.get(0).get(0)) && !accOwnType.equalsIgnoreCase("Single")){
					logInfo("onLoadCustScreening","ENABLING CUST RISK BUTTON");
					formObject.setStyle(BTN_APP_LEVEL_RISK, DISABLE, FALSE);
				}
			}*/  // code shifted to common.java
			setDisableCalculateAppRiskButton(sWorkitemId);
			setEnableAndDisableFskButton();
			populateKYCData(); //Added by Shivanshu
			populateKycMultiDrop();
			accountPurpose(); //AO DCRA
			additionalSource();
			populatePOANationality(); //Jamshed
		}
		catch (Exception e) {
			logError("onLoadCustScreening", e);
		}
	}

	public void Frame52_Disable(){
		disableControls(new String []{SOURCING_CHANNEL,"",REQUEST_TYPE,DATA_ENTRY_MODE,FORM_AUTO_GENERATE,ACC_OWN_TYPE,ACC_CLASS,ACC_HOME_BRANCH,WMS_ID});
		if(!sActivityName.equalsIgnoreCase(ACTIVITY_ACCOUNT_RELATION)){
			disableControls(new String []{TXT_CUSTOMERNAME,TXT_DOB,TXT_CUSTOMERID});
		}
	}




	public void onTabCustScreening(String data){
		logInfo("onTabCustScreening", "INSIDE");
		try{
			String[] selectedSheetInfo = data.split(",");
			String tabCaption = selectedSheetInfo[0];
			int selectedSheetIndex = Integer.parseInt(selectedSheetInfo[1]);
			logInfo("onTabCustScreening", "tabCaption: "+tabCaption+", selectedSheetIndex: "+selectedSheetIndex);
			if(selectedSheetIndex == 1 || selectedSheetIndex == 2 || selectedSheetIndex == 3 || selectedSheetIndex == 4 
					|| selectedSheetIndex == 5) {
				if(((String) formObject.getValue(DATA_ENTRY_MODE)).equalsIgnoreCase("Quick Data Entry")){
					logInfo("onTabClick","disabling kyc: ");
					formObject.setStyle(QDE_KYC,DISABLE,TRUE);
					formObject.setStyle(QDE_KYC_CLIENT,DISABLE,TRUE);
					formObject.setStyle(SEC_CI,DISABLE,TRUE);
					formObject.setStyle(SEC_PERSONAL_DET,DISABLE,TRUE);
					formObject.setStyle(VIEW,DISABLE,FALSE);
					//setCustScreeningCombos();
					populateComparisonFields();
					populateRiskDataQDE();
					frameFatcaCpdDisable();
					populatePepAssesmentDetails(); //AO DCRA
					populatePreAssesmentDetails();  //shahbaz
					populateKycMultiDrop();
				}
				else {
					logInfo("onTabCustScreening","inside else: ");
					formObject.setStyle(SEC_CI,DISABLE,TRUE);
					//ClearComparisonFields();
					//clearPersonalData();
					//clearRiskData();
					//clearKYCData();
					setDDEModeCombos();
					populatePersonalData();
					populateRiskData();
					populateKYCData();    
					populateKycMultiDrop();
					populatePepAssesmentDetails(); //AO DCRA
					populatePreAssesmentDetails();  //shahbaz
					populateComparisonFields();
					PopulatePrivateClientQuestions(); 
					Frame25_CPD_Disable();
					//	setAutoFilledFieldsLocked();
					Frame27_CPD_Disable();
					Frame28_CPD_Disable();
					Frame22_CPD_Disable();
					Frame21_CPD_Disable();
					Frame20_CPD_Disable();
					Frame18_CPD_Disable();
					formObject.setStyle(PD_DATEOFATTAININGMAT, DISABLE, TRUE);//falls under frame 4 , hence written separate
					formObject.setStyle(PD_ANY_CHNG_CUST_INFO,DISABLE,TRUE);
					frameFatcaCpdDisable();

					//				NGRepeater objChkRepeater1 = formObject.getNGRepeater("REPEAT_FRAME");
					//				int fieldToFocus=Integer.parseInt(formObject.getValue("AO_SELECTED_ROW_INDEX"))+1;
					//				String bnk_relation= objChkRepeater1.getValue(fieldToFocus,"AO_ACC_RELATION.BANK_RELATION");
					//				if(bnk_relation.equalsIgnoreCase("New")||bnk_relation.equalsIgnoreCase("Existing"))
					//				{
					//					VisibleonSegmentBasisCPDCHECKER(formObject.getValue("COMBO4"));
					//				}
				}
				loadDedupeSearchData(sWorkitemId);
				formObject.setStyle(DRP_RESEIDA,DISABLE,TRUE);
				formObject.setStyle(VIEW,DISABLE,FALSE);
				//				setDisableCalculateAppRiskButton(sWorkitemId);
				//Jamshed POA nationality
				populatePOANationality();
			} if(selectedSheetIndex == 0||selectedSheetIndex == 6 ) {	
				populatePepAssesmentDetails(); //AO DCRA
			} else if(tabCaption == "Decision"){
				formObject.setStyle(BTN_SUBMIT,DISABLE,FALSE);
				if (getGridCount("DECISION_HISTORY") == 0) {/*
					String sQuery1 = "SELECT CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,"
							+ "CHANNEL,WS_NAME,WS_COMMENTS FROM (SELECT TO_CHAR(CREATE_DAT,'DD/MM/YYYY HH:MI:SS AM'"
							+ ")CREATE_DAT, USERID, USERNAME, GROUP_NAME, WS_DECISION,REJ_REASON, CHANNEL, WS_NAME,"
							+ " WS_COMMENTS,TO_CHAR(CREATE_DAT,'YYYYMMDDHH24:MI:SS') A FROM USR_0_AO_DEC_HIST "
							+ "WHERE WI_NAME = '"
							+ sWorkitemId + "') ORDER BY A";
					List recordList = formObject.getDataFromDB(sQuery1);
					loadListView(recordList,
							"CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,CHANNEL,WS_NAME,WS_COMMENTS","DECISION_HISTORY");
				 */}
			}
		}
		catch (Exception e) {
			logError("onTabCustScreening", e);
		}
	}
	
	//Risk Calculation based on self employed
	
//	public void calculateRisk(){
//		try{
//			logInfo("calculateRisk","Inside calculateRisk: ");
//			String sRisk = formObject.getValue(SANCT_RISK_CURRENT_RSK_BANK).toString();
//			logInfo("calculateRisk","sRisk value"+sRisk);
//			if(!sRisk.equalsIgnoreCase("")){
//				if(formObject.getValue(EMP_STATUS).toString().equalsIgnoreCase("Self Employed")){
//					if(formObject.getValue(RA_CB_GEN_TRDNG_CMPNY).toString().equalsIgnoreCase("true") 
//							|| formObject.getValue(RA_CB_PRECIOUS_STONE_DEALER).toString().equalsIgnoreCase("true")
//							|| formObject.getValue(RA_CB_BULLN_COMMDTY_BROKR).toString().equalsIgnoreCase("true")
//							|| formObject.getValue(RA_CB_REAL_STATE_BROKR).toString().equalsIgnoreCase("true") 
//							|| formObject.getValue(RA_CB_USD_AUTO_DEALER).toString().equalsIgnoreCase("true")
//							|| formObject.getValue(FINANCIAL_BROKERS).toString().equalsIgnoreCase("true")
//							|| formObject.getValue(NOTARY_PUBLIC).toString().equalsIgnoreCase("true")
//							|| formObject.getValue(SOCIAL_MEDIA_INFLUNCER).toString().equalsIgnoreCase("true")
//							|| formObject.getValue(RA_CB_OTHERS).toString().equalsIgnoreCase("true")){
//						if(!(sRisk.equalsIgnoreCase("UAE-PEP")	  ||
//								sRisk.equalsIgnoreCase("Non UAE-PEP") || 
//								sRisk.equalsIgnoreCase("Unacceptable Risk"))){
//							logInfo("calculateRisk","Inside Increased Risk Change: ");
//							formObject.setValue(SANCT_RISK_CURRENT_RSK_BANK,"Increased Risk");
//
//						}
//
//					}
//				}
//
//			}
//		}catch (Exception e) {
//			logError("calculateRisk", e);
//		}
//	}
	
	
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

	private boolean submitWorkitem(String controlName) {
		try {
			logInfo("inside submit click ",controlName);
			try {
			//	updateTRSDDecision();
				updateFSKDecision(); // Changes date  29-05-2023
			} catch(Exception e){
				logError("BTN_SUBMIT",e);
			} 
			logInfo("appLevelMandatoryCheck","appLevelMandatoryCheck : "+appLevelMandatoryCheck);
			if(!checkFSKDecisionforNull() || checkFSKDecisionforReturn()) {
				logInfo(BTN_SUBMIT,"INSIDE IFBLOCK");
				sendMessageValuesList(BTN_TRSD_CHECK,"Please perform TRSD Check");
				//return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
				return false;
			}else if(appLevelMandatoryCheck){
				logInfo(BTN_SUBMIT,"INSIDE IFBLOCK");
				sendMessageValuesList(BTN_APP_LEVEL_RISK,"Please perform Application Level Risk Check");
				//return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
				return false;
			} else {
				logInfo(BTN_SUBMIT,"INSIDE ELSE block");
				formObject.setStyle(controlName,"disable","true");
				boolean result = mandatoryCustScreen();
				logInfo("inside submit click"+controlName,""+result);
				if(!result) {
					formObject.setStyle(controlName,DISABLE,FALSE);
				}
				result = saveScreeningData();
				if(result) {
					int searched=Integer.parseInt(formObject.getValue(NO_OF_CUST_SEARCHED).toString());
					int custProcessed = Integer.parseInt(formObject.getValue(CUST_PROCESSED).toString());
					if(custProcessed == searched+1) {
						formObject.setValue(SELECTED_ROW_INDEX,"0");
						String param = sWorkitemId+"','"+sProcessName;
						List<String> paramlist =new ArrayList<>( );
						paramlist.add (PARAM_TEXT+sWorkitemId);
						paramlist.add (PARAM_TEXT+sProcessName);
						formObject.getDataFromStoredProcedure("TFO_DJ_EMAIL_CONFIG", paramlist);
						logInfo("PROC PARAM",param);
						List<String> procoutput = formObject.getDataFromStoredProcedure("SP_TRSD_EMAIL_NGF",paramlist);
						logInfo("","procoutput"+procoutput);
					}
					logInfo("Inside SUBMIT BUTTON","just before updateTRSDDecision ---");
					//updateTRSDDecision();
					updateFSKDecision(); // Changes date  29-05-2023
				}
			}
			logInfo("submit button NGF save","Entered Done Event"+sActivityName);
			createHistory();
			createAllHistory();
			if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("") ||
					formObject.getValue(CRO_DEC).toString().equalsIgnoreCase(null)) {
				formObject.setValue(CRO_DEC, "Approve");
			}
			String sUpdateDecision = "update "+sExtTable+" set wi_completed_from='"+ sActivityName +"' Where WI_NAME='"+ sWorkitemId +"'";
			formObject.saveDataInDB(sUpdateDecision);
			String sQuery1 = "select PROD_CODE,CURRENCY from usr_0_product_selected where wi_name='"+sWorkitemId+"'";
			logInfo("","usr_0_product_selectedmohit  sQuery cust screening..."+sQuery1);
			List<String> sOutput = formObject.getDataFromDB(sQuery1);
			logInfo("","sOutput..."+sOutput);
		} catch(Exception e) {
			logInfo("ERROR","ERROR INSubmit clik ");
		}
		logInfo("execute server event","INSIDE SUBMIT BUTTON :");
		//return getReturnMessage(true, controlName);
		return true;
	}

	private boolean saveAndNextValidations(String data){
		logInfo("saveAndNextValidations","Inside saveAndNextValidations data: "+data);
		int sheetIndex = Integer.parseInt(data);
		try {
			if(ACTIVITY_CUST_SCREEN.equalsIgnoreCase(sActivityName)){
				logInfo("saveAndNextValidations","Inside ACTIVITY_CUST_SCREEN sheetIndex: "+sheetIndex);
				if(sheetIndex == 6){
					logInfo("saveAndNextValidations","Inside sheetIndex: "+sheetIndex);
					if(!checkFSKDecisionforNull() || checkFSKDecisionforReturn()) {
						logInfo(BTN_SUBMIT,"INSIDE IFBLOCK");
						sendMessageValuesList(BTN_TRSD_CHECK,"Please perform TRSD Check");
						//return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
						return false;
					} else {
						logInfo(BTN_SUBMIT,"INSIDE ELSE block");
						formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
						boolean result = mandatoryCustScreen();
						logInfo("inside submit click"+BTN_SUBMIT,""+result);
						if(result) {
							formObject.setStyle(BTN_SUBMIT,DISABLE,FALSE);
						}
					}
				}
			} else if (ACTIVITY_CUST_SCREEN_QDE.equalsIgnoreCase(sActivityName)) {
				logInfo("saveAndNextValidations","Inside ACTIVITY_CUST_SCREEN_QDE sheetIndex: "+sheetIndex);
				if(sheetIndex == 5){
					logInfo("saveAndNextValidations","Inside sheetIndex: "+sheetIndex);
					if(!checkFSKDecisionforNull() || checkFSKDecisionforReturn()) {
						logInfo(BTN_SUBMIT,"INSIDE IFBLOCK");
						sendMessageValuesList(BTN_TRSD_CHECK,"Please perform TRSD Check");
						//return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
						return false;
					} else {
						logInfo(BTN_SUBMIT,"INSIDE ELSE block");
						formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
						boolean result = mandatoryCustScreen();
						logInfo("inside submit click"+BTN_SUBMIT,""+result);
						if(result) {
							formObject.setStyle(BTN_SUBMIT,DISABLE,FALSE);
						}
					}
				}
			}
		} catch (Exception e) {
			logError("Exception in saveAndNextValidations: ",e);
		} return true;
	}
	
	private boolean validateLastTRSD(int iSelectedRow) {
		try {
			logInfo("validateLastTRSD", "INSIDE");
			String query = "select count(1) as COUNT_CHANGE from USR_0_CHANGE_TRACKER where WI_NAME='"+sWorkitemId
					+"' and STATUS='Pending' and FIELD_NAME in ('NAME','GENDER','NATIONALITY','DOB') and "
					+ "DT > (select max(TRSD_SCREENINGDATE) from USR_0_TRSD_DETAILS where WI_NAME='"+sWorkitemId
					+"' and ACC_RELATION_SNO='"+iSelectedRow+"')";
			logInfo("validateLastTRSD", "query: "+query);
			List<List<String>> result = formObject.getDataFromDB(query);
			logInfo("validateLastTRSD", "result: "+result);
			if(result.get(0).get(0).equalsIgnoreCase("0")) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logError("validateLastTRSD", e);
		}	
		return true;
	}

}