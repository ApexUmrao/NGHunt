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

public class MailRoomOperation extends Common implements Constants, IFormServerEventHandler{
	public String sWorkitemID =formObject.getObjGeneralData().getM_strProcessInstanceId();
	public MailRoomOperation(IFormReference formObject) {
		super(formObject);
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
		logInfo("Inside executeServerEvent MAILROOMOPERATION eventType: " , eventType + ", "
				+ "Control Name: " + controlName + ", Data: " + data);
		sendMessageList.clear();
		List<List<String>> list;
		String sQuery = "";
		String sWMSID ="";
		String sID = "";
		String sServiceShortName = "";
		String sHomeBrShortName = "";
		sendMessageList.clear();
		logInfo("sendMessageList=",sendMessageList.toString());
		String retMsg = getReturnMessage(true ,controlName);
		try{
			if(eventType.equalsIgnoreCase(EVENT_TYPE_LOAD)) {
				prefLang();
				Frame_delivery();
				populateComparisonFields();
				loadApplicationAssessmentData();
				loadApplicationAssessmentDataCPD();
				frame81_CPD_Disable();
				manualFrameCPDDisable();
				//Frame52_Disable();
				formObject.setValue(CRO_REMARKS,"");
				disableControls(new String[] {IS_COMPLIANCE_NAME_SCR,IS_COMPLIANCE_RISK_ASSESS,IS_PROD_APP_REQ
												,IS_CALL_BACK_REQ,L4_APP_REQ,L3_APP_REQ,L2_APP_REQ
												,L1_APP_REQ,SEC_ACC_REL,SEC_SI,SEC_DEL_INST,SEC_INTERNAL_DETL,
												SEC_OPT_PROD_CRO,SEC_DOC_REQ_CRO,HD_BTN,SEC_SS_CPD_TRSD,
												SEC_CC_AORM,SEC_CC_AORC,SEC_CI});
				enableControls(new String[] {SEC_DEC,SI_FRST_PAYMNT,SI_LST_PAYMNT});
				//setAutoFilledFieldsLocked();
				setCPDCheckerCombos();
				int custNo = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
				clearUdfGrid();
				String sQuery2="SELECT GRADUATION_DATE, SPECIAL_CUSTOMER_IDENTIFIER FROM USR_0_UDF_DETAILS"
						+ " WHERE ACC_RELATION_SERIALNO='"+custNo+"' AND WI_NAME='"+sWorkitemID+"'";
				list = formObject.getDataFromDB(sQuery2);
				if(list != null && list.size() >0) {
				   populateUDFGrid(sQuery2);
				}
				populateTRSD();
				populateTRSDCPD();
				setTemp_usr_0_product_selected();
				fieldValueUsr_0_Risk_Data();
			}
			else if(eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)) {
				int index1 = 0;
				if(TABCLICK.equalsIgnoreCase(controlName)) {
					onTabMailRoomOperation(data); 
				} else if(controlName.equalsIgnoreCase(BTN_VIEW)) {
					logInfo("BTN_VIEW","INSIDE");
					int iSelectedRow = Integer.parseInt((String) formObject.getValue(SELECTED_ROW_INDEX))+1; 
					formObject.clearTable(LVW_DEDUPE_RESULT);
					loadDedupeSearchData(sWorkitemId);
					String sQuery1 = "select dedupe_selected_index from USR_0_CUST_TXN Where WI_NAME='"+ sWorkitemId+"' and cust_sno='"+iSelectedRow+"'";
					logInfo("onClickEventQuery","sQuery1@@@@"+sQuery1);
					List<List<String>> sOutput=formObject.getDataFromDB(sQuery1);
					if(sOutput!=null && sOutput.size()>0){
						int  index2=Integer.parseInt(sOutput.get(0).get(0));
						int[] intA = new int[1];
						intA[0] =index2;
						return getReturnMessage(true, controlName, Integer.toString(index2));
					}
				}  else if(controlName.equalsIgnoreCase(CRO_DEC)) {
					if(formObject.getValue(controlName).toString().equalsIgnoreCase("Approve")) {
						formObject.setValue(CRO_REJ_REASON,"");
						formObject.setStyle(CRO_REJ_REASON, "disable", "true");
					} else {
						formObject.setStyle(CRO_REJ_REASON, "disable", "false");
					}
				} else if(controlName.equalsIgnoreCase(BTN_SUBMIT)) {
					saveMailRoom(eventType);
				}
			} else if((eventType.equalsIgnoreCase(EVENT_TYPE_GOTFOCUS) || eventType.equalsIgnoreCase(DATA))
					&& controlName.equalsIgnoreCase("ACC_RELATION.SNO")) {
				try {
					int iRows = getGridCount(ACC_RELATION);
					formObject.setValue(SELECTED_ROW_INDEX, (Integer.parseInt(formObject.getValue(controlName).toString())-1)+"");
					String sName=formObject.getTableCellValue(ACC_RELATION,iRows,1);
					String sDOB=formObject.getTableCellValue(ACC_RELATION,iRows,5);
					String cust_id=formObject.getTableCellValue(ACC_RELATION,iRows,2);
					formObject.setValue(TXT_CUSTOMERNAME, sName);
					formObject.setValue(TXT_DOB, sDOB);
					formObject.setValue(CUST_ID, cust_id);
				} catch(Exception e) {
					logError("Exception in executeServerEvent EVENT_TYPE_GOTFOCUS",e);
				}
			}
			
		} catch (Exception e) {
			logError("Exception in executeServerEvent Event- " + eventType + "control name- " +controlName+ ": ", e);
		} finally{
			logInfo("sendMessageList=",sendMessageList.toString());
			if(!sendMessageList.isEmpty()){
				return  getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1).toString());
			}
		}
		logInfo("","retMsg 2 : "+retMsg); 
		return retMsg;
	}
	
	public boolean saveMailRoom(String eventType) {
		logInfo("saveMailRoom","Inside");
		try{
			if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("")) {
				sendMessageValuesList(CRO_DEC, "Please select user decision.");
				formObject.setStyle(BUTTON_SUBMIT,ENABLE,TRUE);
				return false;
			}
			if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")) {
				String query = "select (to_date(sysdate,'dd/MON/YYYY HH24:MI:SS') - "
						+ "to_date(MAILROOM_ENTRY_DATE,'dd/MON/YYYY HH24:MI:SS'))"
						+ " as TimeDiff from "+sExtTable+" where WI_NAME='"+sWorkitemID+"'";
				formObject.saveDataInDB(query);
				int iMailRoomTAT =0;
				String query1 = "SELECT MAILROOM_SUBMIT_DATE,MAIL_ROOM_TAT_DAYS,SYSDATE,'"+iMailRoomTAT+"'"
						+ " from "+sExtTable+" WHERE WI_NAME ='"+sWorkitemID+"'";
				formObject.saveDataInDB(query1);
			}
			createHistory();				
			createAllHistory();
		} catch(Exception e) {
			logError("Exception in saveMailRoom ",e);
		}
		return true;
		
	}
	
	public void onTabMailRoomOperation(String data) {
		List<List<String>> recordList;
		try{
			String[] selectedSheetInfo = null ;
			String tabCaption = "";
			int selectedSheetIndex = 0;
			if(data.indexOf(",") != -1){
				selectedSheetInfo = data.split(",");
				selectedSheetIndex = Integer.parseInt(selectedSheetInfo[1]);
			}else{
				selectedSheetInfo =  new String[1];
				selectedSheetInfo[0] = data;
				selectedSheetIndex = Integer.parseInt(selectedSheetInfo[0]);
			}
		    tabCaption = selectedSheetInfo[0];
		    if(selectedSheetIndex == 0 || selectedSheetIndex == 1 || selectedSheetIndex== 2
		    		|| selectedSheetIndex == 3 || selectedSheetIndex == 4 || selectedSheetIndex ==5
		    		|| selectedSheetIndex == 6 || selectedSheetIndex == 7 || selectedSheetIndex == 8
		    		|| selectedSheetIndex == 9 || selectedSheetIndex == 10 || selectedSheetIndex == 12
		    		|| selectedSheetIndex == 13 || selectedSheetIndex == 14 || selectedSheetIndex == 15
		    		|| selectedSheetIndex == 16) {
		    	formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
//			} else if(selectedSheetIndex == 18) {
		    } else if(selectedSheetIndex == 19) {	// Changes for Family Banking
			    	formObject.setStyle(BTN_SUBMIT,DISABLE,FALSE);
		    }
			if(selectedSheetIndex == 1) {	
				//ClearComparisonFields();
				//ClearPersonalData();
				//ClearKYCData();
				//ClearRiskData();
				setCPDCheckerCombos();
				populatePersonalDataCPD();
				//populatePersonalData();
				populateRiskData();
				populateKYCData();
				populateKycMultiDrop();
				populatePreAssesmentDetails();  //shahbaz
				populateComparisonFields();
				PopulatePrivateClientQuestions();  
				loadDedupeSearchData(sWorkitemId);
				visibleOnSegmentBasis(formObject.getValue(PD_CUSTSEGMENT).toString());
			} else if(selectedSheetIndex == 2) {
				loadBlackListDataHistory();
				//loadBlackListData();
				if(!formObject.getValue(SCAN_MODE).toString().equalsIgnoreCase("New WMS ID")) {
					PopulateScreeningDataCRO_History();
				}
				PopulateScreeningDataCRO_History();
				//formObject.setNGSelectedTab("Tab2",1);
			} else if(selectedSheetIndex == 8 || selectedSheetIndex==  9) {
		        logInfo("ontabCLickMailRoom","Inside");
				String sQuery ="";
				if(!formObject.getValue(SCAN_MODE).toString().equalsIgnoreCase("New WMS ID")) {
			        logInfo("ontabCLickMailRoom","New WMS ID");
					loadApplicationAssessmentData();
					if(getGridCount(FAC_OFRD_LVW_CRO)==0) {
				        logInfo("ontabCLickMailRoom","FAC_OFRD_LVW_CRO");
						sQuery= "SELECT CUST_ID,CUST_NAME,CARD_TYPE FROM USR_0_DEBITCARD_OFFERED"
								+ " WHERE WI_NAME = '"+sWorkitemID+"'";
						List<List<String>> out = formObject.getDataFromDB(sQuery);
						//LoadListView(sQuery,3,"OFFERED_DEBIT_CARD_GRID","0,1,2");
						loadListView(out, "CUST_ID,CUST_NAME,CARD_TYPE", FAC_OFRD_LVW_CRO);
					}
					if(getGridCount(PROD_OFRD_CRO_LVW)==0)  { //prod 2
				        logInfo("ontabCLickMailRoom","PROD_OFRD_LVW_CRO");
						sQuery= "SELECT PRODUCT_CODE,PRODUCT_DESC,CURRENCY_DESC FROM USR_0_PRODUCT_OFFERED"
								+ " WHERE WI_NAME ='"+sWorkitemID+"' ORDER BY TO_NUMBER(PRODUCT_CODE)";
						List<List<String>> out = formObject.getDataFromDB(sQuery);
						loadListView(out,"PRODUCT_CODE,PRODUCT_DESC,CURRENCY_DESC",PROD_OFRD_CRO_LVW);
						
					}
					if(getGridCount(FAC_LVW_CRO) == 0)  { //fac
				        logInfo("ontabCLickMailRoom","FAC_LVW_CRO");
						sQuery ="SELECT CID,FACILITY,DESCRIPTION FROM USR_0_FACILITY_SELECTED"
								+ " WHERE WI_NAME ='"+sWorkitemID+"' ORDER BY FACILITY";
						List<List<String>> out = formObject.getDataFromDB(sQuery);
						loadListView(out, "CID,FACILITY,DESCRIPTION", FAC_LVW_CRO);
					}
				}
				if(getGridCount(PROD_SEC_OFRD_CPD_LVW) == 0) {
			        logInfo("ontabCLickMailRoom","PROD_SEC_OFRD_CPD_LVW");
					sQuery= "SELECT PRODUCT_CODE,PRODUCT_DESC,CURRENCY_DESC FROM USR_0_PRODUCT_OFFERED_CPD"
							+ " WHERE WI_NAME ='"+sWorkitemID+"' ORDER BY TO_NUMBER(PRODUCT_CODE)";
					List<List<String>> out = formObject.getDataFromDB(sQuery);
					loadListView(out,"PRODUCT_CODE,PRODUCT_DESCRIPTION,CURRENCY",PROD_SEC_OFRD_CPD_LVW);	
				}
				if(getGridCount(FAC_LVW_CPD)==0) {
			        logInfo("ontabCLickMailRoom","FAC_LVW_CPD");
					sQuery ="SELECT CID,FACILITY,DESCRIPTION FROM USR_0_FACILITY_SELECTED_CPD"
							+ " WHERE WI_NAME ='"+sWorkitemID+"' ORDER BY FACILITY";
					List<List<String>> out = formObject.getDataFromDB(sQuery);
					loadListView(out,"CID,FACILITY,DESCRIPTION",FAC_LVW_CPD);
				}
				if(getGridCount(FAC_OFRD_LVW_CPD)==0) {
			        logInfo("ontabCLickMailRoom","FAC_OFRD_LVW_CPD");
					sQuery= "SELECT CUST_ID,CUST_NAME,CARD_TYPE FROM USR_0_DEBITCARD_OFFERED_CPD"
							+ " WHERE WI_NAME = '"+sWorkitemID+"'";
					List<List<String>> out = formObject.getDataFromDB(sQuery);
					loadListView(out,"CUST_ID,CUST_NAME,CARD_TYPE",FAC_OFRD_LVW_CPD);
				}
				
			} else if(selectedSheetIndex == 4) {	
				formObject.setStyle(SEC_ACC_INFO_PD, "disable", "true");
				LoadDebitCardCombo();
			} else if(selectedSheetIndex == 7) {
				formObject.setStyle(BTN_CPD_TRSD_CHK,DISABLE,TRUE);
			//} else if(selectedSheetIndex == 12) {// "Standing Instruction"
			} else if(selectedSheetIndex == 8) {
				loadApplicationAssessmentData_History();
			} else if(selectedSheetIndex == 9) {
				loadApplicationAssessmentDataCPD_History();
			} else if(selectedSheetIndex == 6) {
				PopulateScreeningDataCRO_History();
			} else if(selectedSheetIndex == 7) {
				populateScreeningDataCPD_History();
			} 
			else if(selectedSheetIndex == 14) {// "Standing Instruction"
				loadSICombos_History();
				populateStndInstr();
			} else if(selectedSheetIndex == 12) {//"Delivery Preferences"
				Frame_delivery();
//				} else if(selectedSheetIndex == 18) {//"Decision	
			} else if(selectedSheetIndex == 19) {//"Decision	// Changes for Family Banking
				disableControls(new String[] {IS_COMPLIANCE_NAME_SCR,IS_COMPLIANCE_RISK_ASSESS
											,IS_PROD_APP_REQ,IS_CALL_BACK_REQ});
				formObject.clearCombo(CRO_DEC);
				int iCount = getListCount(CRO_DEC);
				if(iCount==0 || iCount==1) {
					String query = "Select '' from dual Union Select to_char(WS_DECISION)"
							+ " from USR_0_DECISION_MASTER where ws_name='"+formObject.getValue(CURR_WS_NAME)+"'"
							+ " and route_type='"+formObject.getValue(SCAN_MODE)+"'";
					List<List<String>> listt = formObject.getDataFromDB(query);
					formObject.addItemInCombo(CRO_DEC, listt.get(0).get(0));
				}
				/*iCount = getListCount(CRO_REJ_REASON);
				if(iCount==0 || iCount == 1) {
					String query = "Select '' from dual Union Select to_char(ws_rej_reason) from usr_0_rej_reason_mast";
					List<List<String>> listt = formObject.getDataFromDB(query);
					formObject.addItemInCombo(CRO_REJ_REASON, listt.get(0).get(0));
				}*/
				if(getGridCount("DECISION_HISTORY") == 0) {
					logInfo("DECISION_HISTORY","DECISION_HISTORY: "+getGridCount("DECISION_HISTORY"));
					String sQuery1="SELECT CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,CHANNEL,WS_NAME,WS_COMMENTS FROM (SELECT TO_CHAR(CREATE_DAT,'DD/MM/YYYY HH:MI:SS AM') CREATE_DAT, USERID, USERNAME, GROUP_NAME, WS_DECISION,REJ_REASON, CHANNEL,WS_NAME,WS_COMMENTS,TO_CHAR(CREATE_DAT,'YYYYMMDDHH24:MI:SS') A FROM USR_0_AO_DEC_HIST WHERE WI_NAME = '"+sWorkitemId+"') ORDER BY A";
					logInfo("DECISIONTAB",sQuery1);
					List<List<String>> recordList1 = formObject.getDataFromDB(sQuery1); 
					loadListView(recordList1,"CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,CHANNEL,WS_NAME,WS_COMMENTS","DECISION_HISTORY");							
					formObject.setStyle(DEC_STORAGE_DETAILS,VISIBLE,TRUE);
				}
			} else if(selectedSheetIndex == 13) {
				manageCategoryChangeSectionCPDChecker();
			}
			
		} catch(Exception e) {
			
		}
	}
	
	public void loadBlackListDataHistory() { // LoadBlackListData_History
        logInfo("loadBlackListDataHistory","Inside");
		try {
			String sQuery = "";
			int iListViewRows=0;
			int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
			formObject.clearTable(CHECK_TYPE_LVW);
			formObject.clearTable(SANC_SCRN_HD2_LVW);
			formObject.clearTable(SANC_CNTRL_BNK_BAD_LVW);
			long start_Time3 =0;
			String sQueryTnx = "";
			String sQueryUnionHist = "";
			sQueryTnx ="SELECT CUST_NAME,NATIONALITY,DOB ,PASSPORT_NO,'',BLACKLIST_TYPE FROM USR_0_BLACKLIST_DATA"
					+ " WHERE WI_NAME='"+sWorkitemID+"' and CUST_SNO='"+iSelectedRow+"' AND BLACKLIST_TYPE IN"
					+ " (SELECT CHECKTYPE FROM USR_0_CHECK_TYPE WHERE TYPE='INT') ORDER BY CUST_NAME";
			sQueryUnionHist =" union all SELECT CUST_NAME,NATIONALITY,DOB ,PASSPORT_NO,'',"
					+ "BLACKLIST_TYPE FROM USR_0_BLACKLIST_DATA_HIST WHERE WI_NAME='"+sWorkitemID+"'"
					+ " and CUST_SNO='"+iSelectedRow+"' AND BLACKLIST_TYPE IN"
					+ " (SELECT CHECKTYPE FROM USR_0_CHECK_TYPE WHERE TYPE='INT') ORDER BY CUST_NAME";
			sQuery=sQueryTnx+sQueryUnionHist;
			List<List<String>> recordList1 = formObject.getDataFromDB(sQuery);
			logInfo("loadBlackListDataHistory","sQuery: "+sQuery+",recordList1: "+recordList1);
			loadListView(recordList1,"CUST_NAME,NATIONALITY,DOB ,PASSPORT_NO,''BLACKLIST_TYPE",CHECK_TYPE_LVW);

			sQueryTnx ="SELECT CUST_NAME,NATIONALITY,DOB,PASSPORT_NO,'',BLACKLIST_TYPE FROM USR_0_BLACKLIST_DATA"
					+ " WHERE WI_NAME='"+sWorkitemID+"' and CUST_SNO='"+iSelectedRow+"' AND BLACKLIST_TYPE IN"
					+ " (SELECT CHECKTYPE FROM USR_0_CHECK_TYPE WHERE TYPE='EXT') ORDER BY CUST_NAME";
			sQueryUnionHist =" union all SELECT CU ST_NAME,NATIONALITY,DOB,PASSPORT_NO,'',BLACKLIST_TYPE FROM"
					+ " USR_0_BLACKLIST_DATA_HIST WHERE WI_NAME='"+sWorkitemID+"' and CUST_SNO='"+iSelectedRow+"'"
					+ " AND BLACKLIST_TYPE IN (SELECT CHECKTYPE FROM USR_0_CHECK_TYPE WHERE TYPE='EXT') ORDER BY CUST_NAME";			
			sQuery = sQueryTnx+sQueryUnionHist;
			recordList1 = formObject.getDataFromDB(sQuery);	
			logInfo("loadBlackListDataHistory","sQuery: "+sQuery+",recordList1: "+recordList1);
			loadListView(recordList1,"CUST_NAME,NATIONALITY,DOB,PASSPORT_NO,'',BLACKLIST_TYPE",SANC_SCRN_HD2_LVW);
			
			long start_Time4=System.currentTimeMillis();
			iListViewRows = getGridCount(CHECK_TYPE_LVW);
			if(iListViewRows==0) {
				formObject.setValue(CHK_MATCH_FOUND,"Verified False Positive");
			}
			iListViewRows = getGridCount(SANC_SCRN_HD2_LVW);
			if(iListViewRows == 0) {
				formObject.setValue(SANC_SCRN_MATCH,"Verified False Positive");
			}
			iListViewRows = getGridCount(SANC_CNTRL_BNK_BAD_LVW);
			if(iListViewRows == 0) {
				formObject.setValue(SANC_SCRN_MATCH_FOUND,"Verified False Positive");
			}

		} catch(Exception e) {
			logError("Exception in loadBlackListDataHistory ",e);
		}
	} 
	
	public void loadApplicationAssessmentData_History() { // loadApplicationAssessmentData_History
		logInfo("loadApplicationAssessmentData_History","Inside");
		try {
			int iPrimaryCust = Integer.parseInt(getPrimaryCustomerSNO());
			String sCustID  = formObject.getTableCellValue(ACC_RELATION,iPrimaryCust , 2);
			String sAccRelation  = formObject.getTableCellValue(ACC_RELATION,iPrimaryCust , 7);
			String sQueryTnx="";
			String sQueryUnionHist="";
			String sQuery= "SELECT A.CID,B.CUST_NAME,A.ACC_RELATION,B.CURRENT_RISK_SYSTEM,"
					+ "B.CURRENT_RISK_BUSSINESS FROM ACC_RELATION_REPEATER A,USR_0_RISK_ASSESSMENT_DATA"
					+ " B WHERE A.WI_NAME=B.WI_NAME AND A.SNO=B.SNO AND A.WI_NAME = '"+sWorkitemID+"'"
					+ " ORDER BY TO_NUMBER(A.SNO)";
			List<List<String>> sOutput = formObject.getDataFromDB(sQuery);
			logInfo("LoadApplicationAssessmentData_History","sQuery: "+sQuery+",sOutput: "+sOutput);
			loadListView(sOutput,"CID,CUST_NAME,ACC_RELATION,CURRENT_RISK_SYSTEM,CURRENT_RISK_BUSSINESS",RISK_ASSESS_LVW);
			
			sQuery= "SELECT B.CUST_ID,B.CUST_FULL_NAME,B.final_eligibility,A.ACC_RELATION, "
					+ "B.final_eligibility FROM ACC_RELATION_REPEATER A,USR_0_CUST_TXN B WHERE A.WI_NAME "
					+ "='"+sWorkitemID+"' and A.WI_NAME=B.WI_NAME AND A.SNO=B.CUST_SNO ORDER BY TO_NUMBER(A.SNO)";
			sOutput = formObject.getDataFromDB(sQuery);
			logInfo("loadApplicationAssessmentData_History","sQuery: "+sQuery+",sOutput: "+sOutput);
			loadListView(sOutput,"CUST_ID,CUST_FULL_NAME,final_eligibility,ACC_RELATION,final_eligibility",ELIG_LVW_CRO); 

			if(sActivityName.equalsIgnoreCase(ACTIVITY_APP_ASSESSMENT) && sAccRelation.equalsIgnoreCase("Existing")) {
				if(getGridCount(FAC_EXST_LVW_CRO)==0) {
					loadExistingDebitCard("USR_0_DEBITCARD_EXISTING",FAC_EXST_LVW_CRO,sCustID);
				}
			}
			if(sAccRelation.equalsIgnoreCase("Existing")) {
				sQueryTnx= "SELECT PRODUCT_CODE,PRODUCT_NAME,CURRENCY FROM USR_0_PRODUCT_EXISTING"
						+ " WHERE WI_NAME ='"+sWorkitemID+"' AND CUSTOMER_ID='"+sCustID+"'";
				sQueryUnionHist= " union all SELECT PRODUCT_CODE,PRODUCT_NAME,CURRENCY FROM"
						+ " USR_0_PRODUCT_EXISTING_HIST WHERE WI_NAME ='"+sWorkitemID+"' AND CUSTOMER_ID='"+sCustID+"'";
				sQuery = sQueryTnx+sQueryUnionHist;
				sOutput = formObject.getDataFromDB(sQuery);
				loadListView(sOutput,PROD_CRO_LVW,"PRODUCT_CODE,PRODUCT_NAME,CURRENCY");

				sQueryTnx= "SELECT CUST_ID,EMBOSS_NAME,CARD_TYPE_DESC,STATUS_DESC,ISSUANCE_DATE,EXPIRY_DATE"
						+ " FROM USR_0_DEBITCARD_EXISTING WHERE WI_NAME = '"+sWorkitemID+"' AND CUST_ID='"+sCustID+"'";
				sQueryUnionHist= " union all SELECT CUST_ID,EMBOSS_NAME,CARD_TYPE_DESC,STATUS_DESC"
						+ ",ISSUANCE_DATE,EXPIRY_DATE FROM USR_0_DEBITCARD_EXISTING_HIST WHERE WI_NAME = '"+sWorkitemID+"'"
								+ " AND CUST_ID='"+sCustID+"'";
				sQuery=sQueryTnx+sQueryUnionHist;
				sOutput = formObject.getDataFromDB(sQuery);
				logInfo("loadApplicationAssessmentData_History","sQuery: "+sQuery+",sOutput: "+sOutput);
				loadListView(sOutput,"CUST_ID,EMBOSS_NAME,CARD_TYPE_DESC,STATUS_DESC,"
						+ "ISSUANCE_DATE,EXPIRY_DATE",FAC_EXST_LVW_CRO);
				sQuery = "SELECT PRODUCT_NAME,CURRENCY FROM USR_0_BANQUADETAILS"
						+ " WHERE WI_NAME ='"+sWorkitemID+"' AND CUSTOMER_ID='"+sCustID+"'";
				sOutput = formObject.getDataFromDB(sQuery);
				logInfo("loadApplicationAssessmentData_History","sQuery: "+sQuery+",sOutput: "+sOutput);
				String sAllProducts = "";
				String sEachProducts = "";
				int iTotalRetrived = sOutput.size();
				try {
					if(iTotalRetrived != 0) {
						for(int i=0;i<iTotalRetrived;i++) {
							formObject.addItemInTableCellCombo(PROD_CRO_LVW, i, 1,sOutput.get(i).get(0));
							formObject.addItemInTableCellCombo(PROD_CRO_LVW, i, 3,sOutput.get(i).get(1));
						}
					}
				} catch(Exception e) {
					logError("Exception in loadApplicationAssessmentData_History ",e);
				}
			}
		} catch(Exception e) {
			logError("Exception in loadApplicationAssessmentData_History ",e);
		}
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

}
