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
import com.newgen.iforms.user.ao.util.ExecuteXML;
import com.newgen.iforms.user.config.Constants;
import com.newgen.mvcbeans.model.WorkdeskModel;
import com.newgen.omni.jts.cmgr.XMLParser;

public class CPDBulkEODCheckerHistory extends Common implements Constants, IFormServerEventHandler{
	boolean bFormLoaded = false; //sOnLoad new variable name
	
	public CPDBulkEODCheckerHistory(IFormReference formObject) {
		super(formObject);
	}

	@Override
	public void beforeFormLoad(FormDef arg0, IFormReference arg1) {
		log.info("Inside beforeFormLoad >>>");
		log.info("WorkItem Name: " + sWorkitemId+", Activity Name: "+sActivityName);		
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
		log.info("Inside executeServerEvent CPDBulkEODCheckerHistory >>>");
		log.info("Event: " + eventType + ", Control Name: " + controlName + ", Data: " + data);
		sendMessageList.clear();
		String retMsg = getReturnMessage(true,controlName);
		try {
			if(eventType.equalsIgnoreCase(EVENT_TYPE_LOAD)) {
				onCheckerHistoryFormLoad();
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)) {
				if(TABCLICK.equalsIgnoreCase(controlName)) {
					onClickTabCheckerHistory(data);
				} else if(controlName.equalsIgnoreCase(VIEW)) {
					logInfo("VIEW","inside view 5");
					formObject.clearTable(LVW_DEDUPE_RESULT);
					int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
					String sQuery = "SELECT CUST_ID,CUST_NAME,CUST_IC,CUST_PASSPORT,CUST_EMAIL,CUST_MOBILE,'','',to_date(CUST_DOB,'"
							+ getDateValue("DATEFORMAT")
							+ "'),CUST_EIDA,CUST_NATIONALITY,system_type FROM USR_0_DUPLICATE_SEARCH_DATA WHERE WI_NAME='"
							+ sWorkitemId
							+ "' AND CUST_SNO ='"
							+ iSelectedRow
							+ "'";
					List<List<String>> list = formObject.getDataFromDB(sQuery);
					loadListView(list,"CID,Name,CustomerIC,PassportNo,Email,Mobile,DebitCardNo,CreditCardNo,"
							+ "DOB,EIDANO,Nationality,System",LVW_DEDUPE_RESULT);
					String sQuery1 = "select dedupe_selected_index from USR_0_CUST_TXN Where WI_NAME='"
							+ sWorkitemId+ "' and cust_sno='"+ iSelectedRow+ "'";
					logInfo("","sQuery ==== " + sQuery1);
					List<List<String>> list1 = formObject.getDataFromDB(sQuery1);
					logInfo("",list1.toString());
					int index1 = Integer.parseInt(list1.get(0).get(0));
					logInfo("VIEW","index1" + index1);
					int[] intA = new int[1];
					intA[0] = index1;
					return getReturnMessage(true,controlName,""+intA[0]);
					// formObject.setNGLVWSelectedRows(LVW_DEDUPE_RESULT, intA
					// );
				} else if(BTN_SUBMIT.equalsIgnoreCase(controlName)) {
					if(submitWorkitem()) {
						getReturnMessage(true, controlName);
					}
				}
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_CHANGE)) {
				if(controlName.equalsIgnoreCase(CP_CITY) || controlName.equalsIgnoreCase(PA_CITY) 
						||controlName.equalsIgnoreCase(RA_CITY)) {
					manageCity(controlName);
				} else if(controlName.equalsIgnoreCase("table94_mode_of_funding")) {
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
			logInfo("executeServerEvent", "Exception in Event- " + eventType + "control name- " +controlName+ ": ");
			logError("executeServerEvent", e);
		} finally {
			logInfo("executeServerEvent", sendMessageList.toString());
			if(!sendMessageList.isEmpty()) {
				return getReturnMessage(false,controlName,sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
			}
		}
		return retMsg;
	}
	
	private void onCheckerHistoryFormLoad() {
		String decision = "";
		String sCompDec = "";
		List<List<String>> result;
		try {			
			loadSICombos();
			prefLang();
			Frame_delivery();
			String query = "select cro_dec,comp_dec from "+sExtTable+" where wi_name ='"+sWorkitemId+"'";
			result = formObject.getDataFromDB(query);
			decision = result.get(0).get(0);
			sCompDec = result.get(0).get(1);
//			Enable_Disable_Load(sCurrTabIndex, sAcitivityName);
	//		formObject.setStyle(BTN_SUBMIT, DISABLE, TRUE);
//			formObject.setNGEnable("Command15", false);
//			formObject.setNGEnable("Command48", false);
//			formObject.setNGEnable("Command58", false);
//			formObject.setNGEnable("Command27", false);
//			formObject.setNGEnable("save_4", false);
//			formObject.setNGEnable("Command69", false);
//			formObject.setNGEnable("Command55", false);
//			formObject.setNGEnable("Command52", false);
//			formObject.setNGEnable("Command26", false);			
			formObject.setValue(SELECTED_ROW_INDEX, "0");
			sendMessageValuesList(ACC_RELATION+"_0", ""); //setfocus in js
			frame81_CPD_Disable();
//			formObject.setNGEnable("Frame35",true);
//			formObject.setNGEnable("Frame57",true);
//			formObject.setNGEnable("MASK9",true);
//			formObject.setNGEnable("MASK10",true);
			setCPDCheckerCombos();
			clearUdfGrid();
			int custNo = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
			System.out.println("field valuebagset custNO: "+custNo);
			String sQuery2 = "SELECT GRADUATION_DATE, SPECIAL_CUSTOMER_IDENTIFIER FROM USR_0_UDF_DETAILS WHERE "
					+ "ACC_RELATION_SERIALNO='"+custNo+"' AND WI_NAME='"+sWorkitemId+"'";
			populateUDFGrid(sQuery2);
			populateTRSD();
			populateTRSDCPD();
//			formObject.setNGVisible("Pic1", false);
			String req_type = formObject.getValue(REQUEST_TYPE).toString();
			if(req_type.equalsIgnoreCase("New Account with Category Change") 
					|| req_type.equalsIgnoreCase("Category Change Only")) {
				formObject.setValue(CHECKER_CODE_CAT_CHANGE,formObject.getValue(CHECKER_CODE).toString());
				formObject.setValue(CHECKER_NAME_CAT_CHANGE,formObject.getValue(CHECKER_NAME).toString());
			}			
			String sQuery = "SELECT CALL_NAME FROM USR_0_INTEGRATION_CALLS_Hist WHERE WI_NAME='"+ sWorkitemId +"' AND "
					+ "STATUS='Success'";
			result = formObject.getDataFromDB(query);
			for(int i=0; i<result.size(); i++) {
				String sCallName  = result.get(i).get(0);
				if(sCallName.contains("CUSTOMER_CREATION") || sCallName.contains("ACCOUNT_CREATION")) {
					loadIntegrationDataOnFormHistory();
				}
			}			
			if(decision.equalsIgnoreCase("Permanent Reject - Discard") 
					|| sCompDec.equalsIgnoreCase("Negative Advisory")) {
				formObject.applyGroup("CONTROL_SET_CPD_FORM");//formObject.NGMakeFormReadOnly();
				frame81_CPD_Disable();
//				manual_frame_CPD_disable();
//				Frame18_QDE_Disable();
				disableControls(new String[] {NEG_INFO, FATF, KYC});
				Frame_delivery();
				Frame48_CPD_Disable();
				if(decision.equalsIgnoreCase("Permanent Reject - Discard") 
						|| sCompDec.equalsIgnoreCase("Negative Advisory")) {
					enableControls(new String[] {CRO_DEC, CRO_REMARKS, CRO_REJ_REASON});
			//		formObject.setStyle(BTN_SUBMIT, DISABLE, TRUE);
					formObject.clearCombo(CRO_DEC);
					formObject.addItemInCombo(CRO_DEC,"Permanent Reject - Discard");
					formObject.setValue(CRO_DEC, "");
				}
				setTemp_usr_0_product_selected();
			}
			fieldValueUsr_0_Risk_Data();
			loadECBChqBookValidation();
			populateStndInstr();
		} catch (Exception e) {	
			logError("onCheckerHistoryFormLoad", e);
		}			
		formObject.setStyle(VIEW, DISABLE, FALSE);
	}
	
	private void onClickTabCheckerHistory(String data) {
		try {
			logInfo("onClickTabCheckerHistory", "INSIDE");
			String[] selectedSheetInfo = data.split(",");
			String tabCaption = selectedSheetInfo[0];
			int selectedSheetIndex = Integer.parseInt(selectedSheetInfo[1]);
			if(selectedSheetIndex == 1 || selectedSheetIndex == 2 || selectedSheetIndex == 3 || 
					selectedSheetIndex == 4 || selectedSheetIndex == 5) {
				setManualFieldsBlank();
//				setManualChecksBlank();
//				clearComparisonFields(); //in js
//				clearPersonalData();
//				clearKYCData();
//				clearRiskData();
				setCPDCheckerCombos();
				populatePersonalDataCPD();
				populateRiskData();
				populateKYCData();
	//			populatePreAssesmentDetails();  //shahbaz
				populateComparisonFields();
				PopulatePrivateClientQuestions();
				loadDedupeSearchDataHistory();
				visibleonSegmentBasisCPDCHECKER(formObject.getValue(PD_CUSTSEGMENT).toString());
			} else if(selectedSheetIndex == 13) {
				if(formObject.getValue(SCAN_MODE).toString().equalsIgnoreCase("New WMS ID") 
						&& sActivityName.equalsIgnoreCase("CPD Checker")) {						
					String sQuery = "SELECT USERNAME,PERSONALNAME||' '||FAMILYNAME AS NAME FROM PDBUSER WHERE "
							+ "UPPER(USERNAME) =UPPER('"+sUserName+"')";
					List<List<String>> output = formObject.getDataFromDB(sQuery);
					formObject.setValue(CHECKER_NAME_CAT_CHANGE, output.get(0).get(1));
					formObject.setValue(CHECKER_CODE_CAT_CHANGE, output.get(0).get(0));
					sQuery ="SELECT DEPARTMENT FROM WFFILTERTABLE WHERE UPPER(USERNAME) =UPPER('"+sUserName+"') AND "
							+ "PROCESSDEFID='"+sProcessDefId+"'";
					output = formObject.getDataFromDB(sQuery);
					formObject.setValue(CHECKER_DEPARMENT_CAT_CHANGE, output.get(0).get(0));
				}
			} else if(selectedSheetIndex == 6 || selectedSheetIndex == 7) {
				if(!formObject.getValue(SCAN_MODE).toString().equalsIgnoreCase("New WMS ID")) {
					PopulateScreeningDataCRO_History();
				}
				populateScreeningDataCPD_History();
				populateTRSD();
				populateTRSDCPD();
				populateTRSDRemarks();
//				formObject.setNGSelectedTab("Tab2",1);//check in old
			} else if(selectedSheetIndex == 8 || selectedSheetIndex == 9) {	
				loadApplicationAssessmentData_History();
				loadApplicationAssessmentDataCPD_History();						
				String sQuery= "";
				String sQueryTnx="";
				String sQueryUnionHist="";
				if(!formObject.getValue(SCAN_MODE).toString().equalsIgnoreCase("New WMS ID")) {
					if(getGridCount(FAC_OFRD_LVW_CRO) == 0) {
						sQueryTnx= "SELECT CUST_ID,CUST_NAME,CARD_TYPE FROM USR_0_DEBITCARD_OFFERED WHERE WI_NAME = '"
								+sWorkitemId+"'";
						sQueryUnionHist= " union all SELECT CUST_ID,CUST_NAME,CARD_TYPE FROM "
								+ "USR_0_DEBITCARD_OFFERED_HIST WHERE WI_NAME = '"+sWorkitemId+"'";
						sQuery=sQueryTnx+sQueryUnionHist;
						loadListView(formObject.getDataFromDB(sQuery), "CUST_ID,CUST_NAME,CARD_TYPE", FAC_OFRD_LVW_CRO);
					}
					if(getGridCount(PROD_OFRD_CRO_LVW) == 0) {
						sQueryTnx= "SELECT PRODUCT_CODE,PRODUCT_DESC,CURRENCY_DESC,'' FROM USR_0_PRODUCT_OFFERED WHERE "
								+ "WI_NAME ='"+sWorkitemId+"' ";
						sQueryUnionHist= " union all SELECT PRODUCT_CODE,PRODUCT_DESC,CURRENCY_DESC,'' FROM "
								+ "USR_0_PRODUCT_OFFERED_HIST WHERE WI_NAME ='"+sWorkitemId+"' ORDER BY PRODUCT_CODE";				
						sQuery=sQueryTnx+sQueryUnionHist;
						loadListView(formObject.getDataFromDB(sQuery), "PRODUCT_CODE,PRODUCT_DESC,CURRENCY_DESC", PROD_OFRD_CRO_LVW);
					}
					if(getGridCount(FAC_LVW_CRO) == 0) {
						sQueryTnx ="SELECT CID,FACILITY,DESCRIPTION FROM USR_0_FACILITY_SELECTED WHERE WI_NAME ='"
								+sWorkitemId+"' ";
						sQueryUnionHist =" union all SELECT CID,FACILITY,DESCRIPTION FROM USR_0_FACILITY_SELECTED_HIST "
								+ "WHERE WI_NAME ='"+sWorkitemId+"' ORDER BY FACILITY";
						sQuery = sQueryTnx+sQueryUnionHist;
						loadListView(formObject.getDataFromDB(sQuery), "CID,FACILITY,DESCRIPTION", FAC_LVW_CRO);
					}
				}
				if(getGridCount(PROD_SEC_OFRD_CPD_LVW) == 0) {
					sQueryTnx= "SELECT PRODUCT_CODE,PRODUCT_DESC,CURRENCY_DESC,'' FROM USR_0_PRODUCT_OFFERED_CPD WHERE WI_NAME ='"+sWorkitemId+"' ";
					sQueryUnionHist= " union all SELECT PRODUCT_CODE,PRODUCT_DESC,CURRENCY_DESC,'' FROM USR_0_PRODUCT_OFFERED_CPD_HIST WHERE WI_NAME ='"+sWorkitemId+"' ORDER BY PRODUCT_CODE";				
					sQuery=sQueryTnx+sQueryUnionHist;
					loadListView(formObject.getDataFromDB(sQuery),"PRODUCT_CODE,PRODUCT_DESC,CURRENCY_DESC,PROD_ACC_OPENING",PROD_SEC_OFRD_CPD_LVW);	
				}
				if(getGridCount(FAC_LVW_CPD)==0) {
					sQueryTnx ="SELECT CID,FACILITY,DESCRIPTION FROM USR_0_FACILITY_SELECTED_CPD WHERE WI_NAME ='"
							+sWorkitemId+"' ";
					sQueryUnionHist =" union all SELECT CID,FACILITY,DESCRIPTION FROM USR_0_FACILITY_SELECTED_CPD_H "
							+ "WHERE WI_NAME ='"+sWorkitemId+"' ORDER BY FACILITY";
					sQuery=sQueryTnx+sQueryUnionHist;
					loadListView(formObject.getDataFromDB(sQuery),"CID,FACILITY,DESCRIPTION",FAC_LVW_CPD);
				}
				if(getGridCount(FAC_OFRD_LVW_CPD) == 0) {
					sQueryTnx= "SELECT CUST_ID,CUST_NAME,CARD_TYPE FROM USR_0_DEBITCARD_OFFERED_CPD WHERE WI_NAME = '"
							+sWorkitemId+"'";
					sQueryUnionHist= " union all SELECT CUST_ID,CUST_NAME,CARD_TYPE FROM USR_0_DEBITCARD_OFFERED_CPD_H "
							+ "WHERE WI_NAME = '"+sWorkitemId+"'";		
					sQuery=sQueryTnx+sQueryUnionHist;
					loadListView(formObject.getDataFromDB(sQuery),"CUST_ID,CUST_NAME,CARD_TYPE",FAC_OFRD_LVW_CPD);
				}
			} else if(selectedSheetIndex == 10) {
				if(formObject.getValue(SCAN_MODE).toString().equalsIgnoreCase("New WMS ID") 
						&& sActivityName.equalsIgnoreCase("CPD Checker")) {						
					String sQuery ="SELECT USERNAME,PERSONALNAME||' '||FAMILYNAME AS NAME FROM PDBUSER WHERE "
							+ "UPPER(USERNAME) =UPPER('"+sUserName+"')";
					List<List<String>> output = formObject.getDataFromDB(sQuery);
					formObject.setValue(CHECKER_NAME, output.get(0).get(1));
					formObject.setValue(CHECKER_CODE, output.get(0).get(0));
					sQuery ="SELECT DEPARTMENT FROM WFFILTERTABLE WHERE UPPER(USERNAME) =UPPER('"+sUserName+"') AND "
							+ "PROCESSDEFID='"+sProcessDefId+"'";
					output = formObject.getDataFromDB(sQuery);
					formObject.setValue("CHECKER_DEPT", output.get(0).get(0));
				}
				String sQuery= "SELECT CUST_ID,EMBOSS_NAME,CARD_TYPE_DESC,STATUS_DESC,ISSUANCE_DATE,EXPIRY_DATE FROM "
						+ "USR_0_DEBITCARD_EXISTING_CPD_H WHERE WI_NAME = '"+sWorkitemId+"'";	
				loadListView(formObject.getDataFromDB(sQuery), "CUST_ID,EMBOSS_NAME,CARD_TYPE_DESC,STATUS_DESC,"
						+ "ISSUANCE_DATE,EXPIRY_DATE", ACC_INFO_EDC_LVW);
//				LoadListView(sQuery,6,"ListView20","0,1,2,3,4,5");	
			}
			if(tabCaption.equalsIgnoreCase("Standing Instruction")) {
				loadSICombos_History();
				populateStndInstr();
			} else if(tabCaption.equalsIgnoreCase("Delivery Preferences")) {
				Frame_delivery();
			} else if(tabCaption.equalsIgnoreCase("Decision")) {
				disableControls(new String[] {IS_COMPLIANCE_NAME_SCR, IS_COMPLIANCE_RISK_ASSESS, IS_PROD_APP_REQ,
						IS_CALL_BACK_REQ});
				hideControls(new String[] {L1_APP_REQ, L2_APP_REQ, L3_APP_REQ, L4_APP_REQ});
				int iCount = getListCount(CRO_DEC);
				if(iCount==0 || iCount==1) {
					loadCombo("Select to_char(WS_DECISION) from USR_0_DECISION_MASTER where ws_name='"+sActivityName+"' "
							+ "and route_type='" +formObject.getValue("AO_SCAN_MODE")+"'", CRO_DEC);
				}
				iCount = getListCount(CRO_REJ_REASON);
				if(iCount==0 || iCount==1) {
					loadCombo("Select to_char(WS_DECISION) from USR_0_DECISION_MASTER where ws_name='"+sActivityName+"' "
							+ "and route_type='" +formObject.getValue("AO_SCAN_MODE")+"'", CRO_DEC);
				}
//				if(formObject.getNGListCount("LISTVIEW_DECISION") == 0) {
//					LoadListView("SELECT CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,CHANNEL,WS_NAME,"
//							+ "WS_COMMENTS FROM (SELECT TO_CHAR(CREATE_DAT,'DD/MM/YYYY HH:MI:SS AM')CREATE_DAT, USERID, "
//							+ "USERNAME, GROUP_NAME, WS_DECISION,REJ_REASON, CHANNEL, WS_NAME, WS_COMMENTS,"
//							+ "TO_CHAR(CREATE_DAT,'YYYYMMDDHH24:MI:SS') A FROM USR_0_AO_DEC_HIST WHERE WI_NAME = '"
//							+sWorkitemId+"') ORDER BY A",9,"LISTVIEW_DECISION","0,1,2,3,4,5,6,7,8");							
//				}
			} else if(tabCaption.equalsIgnoreCase("Category Change")) {
				manageCategoryChangeSectionCPDChecker();
			}
			formObject.setStyle(VIEW, DISABLE, FALSE);
		} catch(Exception e) {
			logError("onClickTabCheckerHistory", e);
		}
	}
	
	private boolean submitWorkitem() {
		createHistory();
		createAllHistory();			
		if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Return to Originator")) {
			formObject.setValue(NO_OF_CUST_PROCESSED, "0");
			if(sActivityName.equalsIgnoreCase("Bulk EOD Checker")) {
				Date d= new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				String sDate = dateFormat.format(d);
				formObject.setValue(BULK_EOD_DEC,formObject.getValue(CRO_DEC).toString());
				formObject.setValue(BULK_EOD_USER,sUserName);
				formObject.setValue(BULK_EOD_DATE,sDate);
				/*String sGeneralData[]=sDocTypes.split("</DocumentType>");
				String sTemp="";
				for(int i=0;i<sGeneralData.length-1;i++) {
					String docindex=sGeneralData[i].substring(sGeneralData[i].indexOf("<DocId>")+"<DocId>".length(),sGeneralData[i].indexOf("</DocId>"));
					String docname=sGeneralData[i].substring(sGeneralData[i].indexOf("<DocName>")+"<DocName>".length(),sGeneralData[i].indexOf("</DocName>"));
					String doctype=sGeneralData[i].substring(sGeneralData[i].indexOf("<DocType>")+"<DocType>".length(),sGeneralData[i].indexOf("</DocType>"));
					String pages=sGeneralData[i].substring(sGeneralData[i].indexOf("<NoOfPages>")+"<NoOfPages>".length(),sGeneralData[i].indexOf("</NoOfPages>"));
					List<List<String>> sOutputXML=formObject.getDataFromDB("select (imageindex || '#' || volumeid) as imageindex,documentsize from pdbdocument where documentindex="+docindex);
					if(sOutputXML.size()>0 && sOutputXML!=null){
					String imageindex=sOutputXML.get(0).get(0);
					String docsize=sOutputXML.get(0).get(1);					
					sTemp=sTemp+docname+(char)21+imageindex+(char)21+pages+(char)21+docsize+(char)21+doctype+(char)25;
					}
				}				
				String sCard= "";
				String sOD= "";
				String sFD= "";
				String sQuery= "";
				String sXMLIN = "";
				List<List<String>> sOutput= null;
				String sTempDataMSRCard = "";
				String sTempDataMSROD = "";
				String sTempDataMSRFD = "";
				boolean bOutput;				
				if(formObject.getValue(PRE_APP_CREDIT_CARD).toString().equalsIgnoreCase("True") 
						|| formObject.getValue("PRE_APP_CREDIT_CARD_CPD").toString().equalsIgnoreCase("True")) {
					sCard ="True";
				}
				if(formObject.getValue(OVERDRAFT).toString().equalsIgnoreCase("True") 
						|| formObject.getValue("OVERDRAFT_CPD").toString().equalsIgnoreCase("True")) {
					sOD ="True";
				}
				if(formObject.getValue("FD").toString().equalsIgnoreCase("True") 
						|| formObject.getValue("FD_CPD").toString().equalsIgnoreCase("True")) {
					sFD ="True";
				}
				if(sCard.equalsIgnoreCase("True") || sOD.equalsIgnoreCase("True") || sFD.equalsIgnoreCase("True")) {
					sQuery = "SELECT CUST_SEG,CUST_ID,CUST_IC, NATIONALITY, FINAL_FULL_NAME, FINAL_EMAIL, "
							+ "TO_CHAR(FINAL_DOB,'YYYY-MM-DD') FINAL_DOB, FINAL_PASS_NO, FINAL_MOBILE_NO FROM "
							+ "USR_0_CUST_TXN WHERE WI_NAME='"+sWorkitemId+"' AND CUST_ID=(SELECT CID FROM "
							+ "ACC_RELATION_REPEATER WHERE WI_NAME='"+sWorkitemId+"' AND ACC_RELATION "
							+ "IN ('JAF','JOF','SOW','Guardian'))";
					sOutput = formObject.getDataFromDB(sQuery);
					sTempDataMSRCard = sTempDataMSROD= sTempDataMSRFD= "WMS_ID"+(char)21+formObject.getValue(WMS_ID)
							+(char)25+"MSRCATEGORY"+(char)21+sOutput.get(0).get(0)+(char)25+"INITIATIONMODE"
							+(char)21+"From AO"+(char)25+"BANKRELATIONSHIP"+(char)21+"Existing"+(char)25+"CID"+(char)21
							+sOutput.get(0).get(1)+(char)25+"IC"+(char)21+sOutput.get(0).get(2)
							+(char)25+"NATIONALITY"+(char)21+sOutput.get(0).get(3)+(char)25+"FULLNAME"
							+(char)21+sOutput.get(0).get(4)+(char)25+"EMAIL"+(char)21
							+sOutput.get(0).get(5)+(char)25+"CUST_DOB"+(char)21
							+sOutput.get(0).get(6)+(char)25+"PASSPORTNUMBER"+(char)21
							+sOutput.get(0).get(7)+(char)25+"MOBILENUMBER"+(char)21
							+sOutput.get(0).get(8)+(char)25;					
					sOutput = formObject.getDataFromDB("SELECT PROCESSDEFID FROM PROCESSDEFTABLE WHERE PROCESSNAME "
							+ "='MSR'");					
					if(sCard.equalsIgnoreCase("True")) {
						sTempDataMSRCard+="MSRTYPE"+(char)21+"Credit Cards"+(char)25;
						sTempDataMSRCard+="MSRSUBTYPE"+(char)21+"Pre-approved credit card"+(char)25;						
						sXMLIN = "<?xml version=\"1.0\"?>"+
								"<WFUploadWorkItem_Input>"+
								"<Option>WFUploadWorkItem</Option>"+
								"<EngineName>"+sEngineName+"</EngineName>"+
								"<SessionId>"+sSessionId+"</SessionId>"+
								"<ProcessDefId>"+sProcessDefId+"</ProcessDefId>"+
								"<InitiateAlso>Y</InitiateAlso>"+
								"<Documents>"+sTemp+"</Documents>"+
								"<Attributes>"+sTempDataMSRCard+"</Attributes>"+
								"</WFUploadWorkItem_Input>";
						bOutput = InitiateWorkitem(sXMLIN);						
						if(!bOutput) {
							return false;
						}
					}					
					if(sOD.equalsIgnoreCase("True")) {
						sTempDataMSROD+="MSRTYPE"+(char)21+"OD"+(char)25;
						sTempDataMSROD+="MSRSUBTYPE"+(char)21+"Pre-approved OD"+(char)25;						
						sXMLIN = "<?xml version=\"1.0\"?>"+
								"<WFUploadWorkItem_Input>"+
								"<Option>WFUploadWorkItem</Option>"+
								"<EngineName>"+sEngineName+"</EngineName>"+
								"<SessionId>"+sSessionId+"</SessionId>"+
								"<ProcessDefId>"+sProcessDefId+"</ProcessDefId>"+
								"<InitiateAlso>Y</InitiateAlso>"+
								"<Documents>"+sTemp+"</Documents>"+
								"<Attributes>"+sTempDataMSROD+"</Attributes>"+
								"</WFUploadWorkItem_Input>";						
						bOutput = InitiateWorkitem(sXMLIN);						
						if(!bOutput) {
							return false;
						}
					}					
					if(sFD.equalsIgnoreCase("True")) {
						sTempDataMSRFD+="MSRTYPE"+(char)21+"Fixed Deposit"+(char)25;						
						sXMLIN = "<?xml version=\"1.0\"?>"+
								"<WFUploadWorkItem_Input>"+
								"<Option>WFUploadWorkItem</Option>"+
								"<EngineName>"+sEngineName+"</EngineName>"+
								"<SessionId>"+sSessionId+"</SessionId>"+
								"<ProcessDefId>"+sProcessDefId+"</ProcessDefId>"+
								"<InitiateAlso>Y</InitiateAlso>"+
								"<Documents>"+sTemp+"</Documents>"+
								"<Attributes>"+sTempDataMSRFD+"</Attributes>"+
								"</WFUploadWorkItem_Input>";						
						bOutput = InitiateWorkitem(sXMLIN);						
						if(!bOutput) {
							return false;
						}
					}
				}*/
			} else if(sActivityName.equalsIgnoreCase("MailRoom HandOff")) {
				List<String> paramlist =new ArrayList<>();
				paramlist.add (PARAM_TEXT+sWorkitemId);
				formObject.getDataFromStoredProcedure("CDTS_ProcessedWI", paramlist);
           }
		}
		return true;
	}
	
	/*public boolean InitiateWorkitem(String sInputXML) {	
		XMLParser parser = new XMLParser();	
		String sXMLOUT = ExecuteXML.executeXml(sInputXML);					   
		parser.setInputXML(sXMLOUT);
		
		if(!parser.getValueOf("Maincode").equalsIgnoreCase("0"))
		{
			JOptionPane.showMessageDialog(_objApplet,"Error in initiating workitem to MSR process");
			formObject.setNGEnable("Command24", true);
			formObject.setNGEnable("static_submit", true);
			return false;
		}
		
		if(sInputXML.length()>2000)
		{
			sInputXML=sInputXML.substring(0,1999);
		}
			
		 if(sXMLOUT.length()>2000)
		 {
			 sXMLOUT=sXMLOUT.substring(0,1999);
		 }					 
		 
		String sColumn="WI_NAME,DT,INPUTXML,OUTPUTXML,STATUS";
		String sValues = "'"+sWorkitemID+"',sysdate,'"+sInputXML+"','"+sXMLOUT+"','"+parser.getValueOf("Maincode")+"'";
		String sOutput=ExecuteQuery_APInsert("USR_0_AO_INIT_LOG",sColumn,sValues);	
		System.out.println("sOutput----"+sOutput);
		return true;		
	}*/

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
	
	public void loadIntegrationDataOnFormHistory() {//LoadIntegrationDataOnForm_History
		try {
			String sTableCID="";
			String sCID="";
			int iRows = getGridCount(ACC_RELATION);
			String sQuery = "SELECT SNO,CID_ACCNO FROM USR_0_INTEGRATION_CALLVALUES_h WHERE WI_NAME = '"+sWorkitemId
					+"' AND CALLTYPE LIKE 'CUSTOMER_CREATION%' ORDER BY TO_NUMBER(SNO)";
			List<List<String>> result = formObject.getDataFromDB(sQuery);
			for(int i=0; i<iRows; i++) {
				sCID = formObject.getTableCellValue(ACC_RELATION, i, 2);
				if(sCID.equalsIgnoreCase("")) {
					formObject.setTableCellValue(ACC_RELATION, i, 2, result.get(i).get(1));
				}
			}
			sQuery = "SELECT SNO,CID_ACCNO,ACC_STATUS,IBAN_NO,ACC_OPEN_DATE FROM USR_0_INTEGRATION_CALLVALUES_h WHERE "
					+ "WI_NAME = '"+sWorkitemId+"' AND CALLTYPE LIKE 'ACCOUNT_CREATION%' ORDER BY TO_NUMBER(SNO)";
			List<List<String>> result2 = formObject.getDataFromDB(sQuery);
			for(int i=0; i<result2.size(); i++) {
				sTableCID = result2.get(i).get(0);
				for(int j=0;j<result2.size();j++) {
					sCID = formObject.getTableCellValue(PRODUCT_QUEUE, j, 14);				
					if(sTableCID.equalsIgnoreCase(sCID)) {
						formObject.setTableCellValue(PRODUCT_QUEUE, j, 4, result2.get(i).get(1));
						formObject.setTableCellValue(PRODUCT_QUEUE, j, 0, result2.get(i).get(2));
						formObject.setTableCellValue(PRODUCT_QUEUE, j, 15, result2.get(i).get(3));
						formObject.setTableCellValue(PRODUCT_QUEUE, j, 18, result2.get(i).get(4));
						break;
					}				
				}
			}

			sQuery = "SELECT SNO,ACC_STATUS FROM USR_0_INTEGRATION_CALLVALUES_h WHERE WI_NAME = '"+sWorkitemId+"' AND "
					+ "CALLTYPE LIKE 'ACCOUNT_UPDATE%' ORDER BY TO_NUMBER(SNO)";
			List<List<String>> result3 = formObject.getDataFromDB(sQuery);
			for(int i=0;i<result3.size();i++) {
				sTableCID = result3.get(i).get(0);
				for(int j=0;j<result3.size();j++) {
					sCID = formObject.getTableCellValue(PRODUCT_QUEUE, j, 14);				
					if(sTableCID.equalsIgnoreCase(sCID)) {
						formObject.setTableCellValue(PRODUCT_QUEUE, j, 15, result3.get(i).get(1));
						break;
					}				
				}
			}
			sQuery = "SELECT SNO,CID_ACCNO FROM USR_0_INTEGRATION_CALLVALUES_h WHERE WI_NAME = '"+sWorkitemId+"' AND "
					+ "CALLTYPE LIKE 'DEBITCARD%' AND PROD_CODE='New' ORDER BY TO_NUMBER(SNO)";
			List<List<String>> result4 = formObject.getDataFromDB(sQuery);
			int iCount = 0;
			for(int i=0; i<getGridCount(QUEUE_DC); i++) {
				if(formObject.getTableCellValue(QUEUE_DC, i, 4).equalsIgnoreCase("New")) {
					formObject.setTableCellValue(QUEUE_DC, i, 6, result3.get(i).get(1));
					iCount++;
				}
			}	

			sQuery = "SELECT SNO,CID_ACCNO FROM USR_0_INTEGRATION_CALLVALUES_h WHERE WI_NAME = '"+sWorkitemId+"' AND "
					+ "CALLTYPE LIKE 'DEBITCARD%' AND PROD_CODE='Link' ORDER BY TO_NUMBER(SNO)";
			List<List<String>> result5 = formObject.getDataFromDB(sQuery);
			iCount=0;
			for(int i=0; i<getGridCount(QUEUE_DC); i++) {
				if(formObject.getTableCellValue(QUEUE_DC, i, 4).equalsIgnoreCase("New")) { //NEW_LINK
					formObject.setTableCellValue(QUEUE_DC, i, 6, result3.get(i).get(1)); //CARD_NO
					iCount++;
				}
			}
		} catch(Exception e) {
			logError("loadIntegrationDataOnFormHistory", e);
		}
	}
	
	public void loadDedupeSearchDataHistory() { //loadDedupeSearchData_History
		int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
		String sQueryTnx = "";
		String sQueryUnionHist = "";
		sQueryTnx="select cust_id,cust_name,cust_ic,cust_passport,cust_email,cust_mobile,'','',"
				+ "to_char(cust_dob,'dd/MM/yyyy'),cust_eida,cust_nationality from usr_0_duplicate_search_data where "
				+ "wi_name='"+sWorkitemId+"' AND CUST_SNO ='"+iSelectedRow+"'";
		sQueryUnionHist = " union all select cust_id,cust_name,cust_ic,cust_passport,cust_email,cust_mobile,'','',"
				+ "to_char(cust_dob,'dd/MM/yyyy'),cust_eida,cust_nationality from usr_0_duplicate_search_data_H where "
				+ "wi_name='"+sWorkitemId+"' AND CUST_SNO ='"+iSelectedRow+"'";
		String query = sQueryTnx+sQueryUnionHist;
		loadListView(formObject.getDataFromDB(query), COLUMNS_LVW_DEDUPE_RESULT, LVW_DEDUPE_RESULT);
	}

}
