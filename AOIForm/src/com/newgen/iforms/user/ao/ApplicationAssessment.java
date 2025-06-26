//----------------------------------------------------------------------------------------------------
//		NEWGEN SOFTWARE TECHNOLOGIES LIMITED
//Group						: AP2
//Product / Project			: iForm Builder
//Module					: iForms
//File Name					: ApplicationAssessment.java
//Author					: 	
//Date written (DD/MM/YYYY)	: 
//Description				: Java File for AO Application Assessment Workstep
//----------------------------------------------------------------------------------------------------
//			CHANGE HISTORY
//----------------------------------------------------------------------------------------------------
// Date	(DD/MM/YYYY)		 Change By	 	Change Description (Bug No. (If Any))
// 08/11/2021			   Gautam Rajbhar	Bug : CQRN-0000179564
//----------------------------------------------------------------------------------------------------
package com.newgen.iforms.user.ao;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.newgen.iforms.EControl;
import com.newgen.iforms.user.ao.mandatorycheck.*;
import com.newgen.iforms.user.ao.util.XMLParser;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.iforms.user.config.Constants;
import com.newgen.mvcbeans.model.WorkdeskModel;

public class ApplicationAssessment extends Common implements Constants, IFormServerEventHandler {
	public String sOnLoad = "";
	static int SNO=1;
	public ApplicationAssessment(IFormReference formObject) {
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
		log.info("Inside executeServerEvent AppAssessment >");
		log.info("Event: " + eventType + ", Control Name: " + controlName + ", Data: " + data);
		sendMessageList.clear(); 
		String retMsg = getReturnMessage(true,controlName);
		List<String> recordList = null;
		try {
			if(eventType.equalsIgnoreCase(EVENT_TYPE_LOAD)) {
				logInfo("EVENT_TYPE_LOAD APP Assessment","Inside Loadd Call IFF." +controlName);
				assesmentOnLoad();
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)) {
				if(TABCLICK.equalsIgnoreCase(controlName)) {
					onTabClick(data);
					/*if(eventType.equalsIgnoreCase("Click") && controlName.equalsIgnoreCase("edit_3")) {
						String sUpdateDecision="update "+sExtTable+" set back_route_flag='true' Where WI_NAME='"+ sWorkitemId +"'";
						formObject.saveDataInDB(sUpdateDecision);
						sBackRoute = "True";
						formObject.setValue(NO_OF_CUST_PROCESSED,"0");
						logInfo("APP Assessment","Inside Edit");
					}*/
				} else if (controlName.equalsIgnoreCase(VIEW)) {
					logInfo("APP Assessment","inside view 5");
					formObject.clearTable(LVW_DEDUPE_RESULT);
					int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
					String sQuery= "SELECT CUST_ID,CUST_NAME,CUST_IC,CUST_PASSPORT,CUST_EMAIL,CUST_MOBILE,'','',to_date(CUST_DOB,'DATEFORMAT'),CUST_EIDA,CUST_NATIONALITY,system_type FROM USR_0_DUPLICATE_SEARCH_DATA WHERE WI_NAME='"+sWorkitemId+"' AND CUST_SNO ='"+iSelectedRow+"'";
					List<List<String>> recordList1 = formObject.getDataFromDB(sQuery);
					log.info(sQuery);
					loadListView(recordList,COLUMNS_LVW_DEDUPE_RESULT,LVW_DEDUPE_RESULT);
					String sQuery1 = "select dedupe_selected_index from USR_0_CUST_TXN Where WI_NAME='"+ sWorkitemId +"' and cust_sno='"+iSelectedRow+"'";
					logInfo("APP Assessment","sQuery1::"+sQuery1);
					List<List<String>> sOutput=formObject.getDataFromDB(sQuery1);
					logInfo("APP Assessment","sOutput:"+sOutput);
					if(sOutput!=null && sOutput.size()>0){
						int index1 = Integer.parseInt(sOutput.get(0).get(0));
						logInfo("APP Assessment","index1"+index1);
						int[] intA = new int[1];
						intA[0] = index1;
						//formObject.setNGLVWSelectedRows(LVW_DEDUPE_RESULT,  intA ); doubt
					}
				} else if (controlName.equalsIgnoreCase(BTN_DOC_CPD)) {
//					String folderIndex= formObject.getTableCellValue(DOC_REQ_QUEUE,0,9);
//					String docIndex= formObject.getTableCellValue(DOC_REQ_QUEUE,0,10);
//					///JSObject objJStemp =formObject.getJSObject();
//					logInfo("Query:fold "+folderIndex+"#"+docIndex);	
//					Object [] ain = {folderIndex+"#"+docIndex};
//					logInfo("ain :"+ ain[0].toString());
//					//objJStemp.call("attributeDetails",ain);  
				} else if("saveNextTabClick".equalsIgnoreCase(controlName)) {
					if(saveAndNextValidations((String)data.split(",")[1])) {
						logInfo("saveNextTabClick","saveAndNextValidations successful");
						return getReturnMessage(true, controlName);
					}
				} else if (controlName.equalsIgnoreCase(BTN_SUBMIT)) { 
					saveOfferedProduct("USR_0_PRODUCT_OFFERED",PROD_OFRD_CRO_LVW);
					saveFacilitiesData("USR_0_FACILITY_SELECTED",FAC_LVW_CRO);
					saveOfferedDebitCard("USR_0_DEBITCARD_OFFERED",FAC_OFRD_LVW_CRO);
					checkView();
					if(!formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Reject") ) { //Jamshed
						if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")){
							logInfo("APP Assessment","Inside medium Approve");
							String sCustNo =getPrimaryCustomerSNO();
							String sQuery1="SELECT CUST_SEG,RM_NAME,RM_CODE FROM USR_0_CUST_TXN WHERE WI_NAME ='"+sWorkitemId+"' AND CUST_SNO='"+sCustNo+"'";
							List<List<String>> sOutput=formObject.getDataFromDB(sQuery1);
							if(sOutput!=null && sOutput.size()>0){
								String sCustSeg = sOutput.get(0).get(0);
								String sRMName = sOutput.get(0).get(1);
								String sRMCode = sOutput.get(0).get(2);
								String decision=formObject.getValue(CRO_DEC).toString();
								int sOutput1=updateDataInDB(sExtTable,"APP_ASSEMENT_MAKER_DEC,PRI_CUST_SEGMENT,PRI_RM_NAME,"
										+ "PRI_RM_CODE","'"+decision+"'"+(char)25+"'"+sCustSeg+"'"+(char)25+"'"+sRMName+"'"
												+(char)25+"'"+sRMCode+"'","WI_NAME ='"+sWorkitemId+"'");
								logInfo("APP Assessment","sOutput-----"+sOutput1);
							}	
						}
						if(formObject.getValue(FINAL_RISK_VAL).toString().equalsIgnoreCase("Unacceptable Risk")) {
							sendMessageValuesList(CRO_DEC,"You cannot Approve as Risk is Unacceptable.");
							formObject.setValue(CRO_DEC,"Reject");
							enableControls(new String[]{CRO_REJ_REASON});
							return getReturnMessage(false,controlName,sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
						} if(formObject.getValue(FINAL_CUSTOMER_RISK).toString().equalsIgnoreCase("Not Eligible")) {
							sendMessageValuesList(CRO_DEC,"You cannot Approve as customers are not eligible.");
							formObject.setValue(CRO_DEC,"Reject");
							enableControls(new String[]{CRO_REJ_REASON});
							return getReturnMessage(false,controlName,sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
						} if(!(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only")||formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade"))) {
							int iListViewRows = getGridCount(PROD_OFRD_CRO_LVW);
							if(iListViewRows ==0) {
								sendMessageValuesList(PROD_OFRD_CRO_LVW,"Some error occured in product offered display.");
								return getReturnMessage(false,controlName,sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
							}
						}
						
					} else if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("") || formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")) {
						logInfo("BTN_SUBMIT","CRO_DEC: "+formObject.getValue(CRO_DEC));
						 if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("")) {
							formObject.setValue(CRO_DEC, "Approve");
						}
						String sCustNo =getPrimaryCustomerSNO();
						String sQuery1="SELECT CUST_SEG,RM_NAME,RM_CODE FROM USR_0_CUST_TXN WHERE WI_NAME ='"+sWorkitemId+"' AND CUST_SNO='"+sCustNo+"'";
						List<List<String>> sOutput=formObject.getDataFromDB(sQuery1);
						if(sOutput!=null && sOutput.size()>0){
							String sCustSeg = sOutput.get(0).get(0);
							String sRMName = sOutput.get(0).get(1);
							String sRMCode = sOutput.get(0).get(2);
							String decision=formObject.getValue(CRO_DEC).toString();
							int sOutput1=updateDataInDB(sExtTable,"APP_ASSEMENT_MAKER_DEC,PRI_CUST_SEGMENT,PRI_RM_NAME,"
									+ "PRI_RM_CODE","'"+decision+"'"+(char)25+"'"+sCustSeg+"'"+(char)25+"'"+sRMName+"'"
											+(char)25+"'"+sRMCode+"'","WI_NAME ='"+sWorkitemId+"'");
							logInfo("APP Assessment","sOutput-----"+sOutput1);
						} 
					} 
				} else if("postSubmit".equalsIgnoreCase(controlName)){
						createHistory();
						createAllHistory();
						String decision=formObject.getValue(CRO_DEC).toString();
						String sUpdateDecision1="update "+sExtTable+" set app_assement_maker_dec='"+ decision +"',wi_completed_from='"+ sActivityName +"' Where WI_NAME='"+ sWorkitemId +"'";
						formObject.saveDataInDB(sUpdateDecision1);
						String sQuery="select PROD_CODE,CURRENCY from usr_0_product_selected where wi_name='"+sWorkitemId+"'";
						logInfo("APP Assessment","usr_0_product_selectedmohit  sQuery appass..."+sQuery);
						List<List<String>> sOutput2=formObject.getDataFromDB(sQuery);
						logInfo("APP Assessment","sQuery..."+sOutput2);
				 } else if(controlName.equalsIgnoreCase(EDIT3)) {
					    logInfo("APP Assessment","Inside Edit");
					    String sUpdateDecision="update "+sExtTable+" set back_route_flag='true' Where WI_NAME='"+ sWorkitemId +"'";
						formObject.saveDataInDB(sUpdateDecision);
						sBackRoute = "True";
						formObject.setValue(NO_OF_CUST_PROCESSED,"0");
						saveOfferedProduct("USR_0_PRODUCT_OFFERED",PROD_OFRD_CRO_LVW);
						saveFacilitiesData("USR_0_FACILITY_SELECTED",FAC_LVW_CRO);
						saveOfferedDebitCard("USR_0_DEBITCARD_OFFERED",FAC_OFRD_LVW_CRO);
				}
			}
		} catch (Exception e) {
			log.info("Exception in Event- " + eventType + "control name- " +controlName+ ": ", e);
		} finally {
			logInfo("AppAssessment","sendMessageList="+sendMessageList);
			if(!sendMessageList.isEmpty()) {
				return getReturnMessage(false,controlName,sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
			}
		}
		return retMsg;
	}

	public String getEDMSDocs(String doctype,String cust_id) {	
		String dataNt=fetchEDMSDocs(cust_id,doctype);
		logInfo("APP Assessment","  EDMS DOCS  -->"+dataNt);
		return dataNt;
	}

	public String fetchEDMSDocs(String sCustID,String docname) {
		String sInputXML= "<?xml version=\"1.0\"?>" +
				"<APWebService_Input>" +
				"<Option>WebService</Option>" +
				"<Calltype>EMDS_DOCS</Calltype>" +
				"<CUST_ID>"+sCustID+"</CUST_ID>"+
				"<Doc_Name>"+docname+"</Doc_Name>";
		//String sOutput=ExecuteWebserviceAll(sInputXML);	
		String sOutput = socket.connectToSocket(sInputXML) ;
		logInfo("APP Assessment","ExecuteWebservice_FCRValues-----"+sOutput);	
		return sOutput;
	}

	public void assesmentOnLoad() {				
		sOnLoad="True";
		logInfo("assesmentOnLoad", "INSIDE");
		List<List<String>> sOutput= null;
		List<List<String>> recordList = null;
		String sCallName  = "";
		try {		
//			Frame52_Disable();
			loadApplicationAssessmentData();
			loadFacilitiesData(FAC_LVW_CRO);
			calculateAppRisk();
			CalculateCustomerRisk();
			String sCustID  = formObject.getTableCellValue(ACC_RELATION,0,2).toString();
			String sAccRelation  =  formObject.getTableCellValue(ACC_RELATION,0,7).toString();
			String sRequestType = formObject.getValue(REQUEST_TYPE).toString();
			if(sRequestType.equalsIgnoreCase("Category Change Only") || sRequestType.equalsIgnoreCase("Upgrade")) {
				disableControls(new String[]{SEC_OPT_PROD_CPD});
			}
			String sQuery = "SELECT CALL_NAME FROM USR_0_INTEGRATION_CALLS WHERE WI_NAME='"+ sWorkitemId 
					+"' AND STATUS='Success'";
			sOutput=formObject.getDataFromDB(sQuery);
			logInfo("onLoadAppAssessment","USR_0_INTEGRATION_CALLS sOutput::"+sOutput);
			if(sOutput != null && sOutput.size()>0) {
//				sCallName  = sOutput.get(0).get(0);
				for(int i=0; i<sOutput.size(); i++) {
					sCallName  = sCallName + sOutput.get(i).get(0) + ",";
				}
				logInfo("onLoadAppAssessment","Inside sCallName::::"+sCallName);
			}
			if(!(sCallName.contains("CUSTOMER_CREATION") || sCallName.contains("ACCOUNT_CREATION"))) {
				if(!(sRequestType.equalsIgnoreCase("Category Change Only") || sRequestType.equalsIgnoreCase("Upgrade"))) {//added by krishna 
					logInfo("onLoadAppAssessment","Inside BFR LoadOfferedProduct::::");
					LoadOfferedProduct(PROD_OFRD_CRO_LVW);
					logInfo("onLoadAppAssessment","Inside After LoadOfferedProduct::::");
					loadOfferedDebitCard(PROD_OFRD_CRO_LVW,FAC_OFRD_LVW_CRO);
				}			
				loadRequiredDocument(DOC_REQ_QUEUE);
			} else {	
				String sQuery4  =  "SELECT PRODUCT_CODE,PRODUCT_DESC,CURRENCY_DESC,'' FROM USR_0_PRODUCT_OFFERED "
						+ "WHERE WI_NAME = '"+sWorkitemId+"' ORDER BY TO_NUMBER(PRODUCT_CODE)";				
				recordList = formObject.getDataFromDB(sQuery4);
				logInfo("onLoadAppAssessment","sQuery4::"+sQuery4);
				loadListView(recordList,"PRODUCT_CODE,PRODUCT_DESCRIPTION,CURRENCY,PROD_ACC_OPENING",PROD_OFRD_CRO_LVW);
				//PRODUCT_CODE,PRODUCT_DESCRIPTION,CURRENCY,PROD_ACC_OPENING
				String sQuery3  =  "SELECT CUST_ID,CUST_NAME,CARD_TYPE FROM USR_0_DEBITCARD_OFFERED WHERE WI_NAME = '"+sWorkitemId+"'";		
				recordList = formObject.getDataFromDB(sQuery3);
				logInfo("onLoadAppAssessment","sQuery3::"+sQuery3);
				loadListView(recordList,"CUST_ID,CUST_NAME,CARD_TYPE",FAC_OFRD_LVW_CRO);
			}
			if(sActivityName.equalsIgnoreCase(ACTIVITY_APP_ASSESSMENT) && sAccRelation.equalsIgnoreCase("Existing")) { 
				if(getGridCount(FAC_EXST_LVW_CRO)==0) {
					loadExistingDebitCard("USR_0_DEBITCARD_EXISTING",FAC_EXST_LVW_CRO,sCustID);
				}
			}
			String sQueryy="select PROD_CODE,CURRENCY from usr_0_product_selected where wi_name='"+sWorkitemId+"'";
			logInfo("APP Assessment","usr_0_product_selectedmohit  sQuery appass fieldvalue..."+sQueryy);		        
			List<List<String>> sOutputt=formObject.getDataFromDB(sQueryy);
			logInfo("APP Assessment","sOutput..."+sOutputt);
		} catch (Exception e)  {
			e.printStackTrace();
		}
		disableControls(new String[]{MANUAL_SHORTNAME});
		hideControls(new String[]{PIC1});
		formObject.setStyle("Tab5",VISIBLE, TRUE);
	}

	public void onTabClick(String data) {
		try {
			logInfo("onTabClick", "INSIDE");
			String[] selectedSheetInfo = data.split(",");
			String tabCaption = selectedSheetInfo[0];
			int selectedSheetIndex = Integer.parseInt(selectedSheetInfo[1]);
			if(selectedSheetIndex == 17) {/*
				if(getGridCount(DECISION_LVW) == 0) {
					String sQuery = "SELECT CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,CHANNEL,WS_NAME,WS_COMMENTS FROM (SELECT TO_CHAR(CREATE_DAT,'DD/MM/YYYY HH:MI:SS AM')CREATE_DAT, USERID, USERNAME, GROUP_NAME, WS_DECISION,REJ_REASON, CHANNEL, WS_NAME, WS_COMMENTS,TO_CHAR(CREATE_DAT,'YYYYMMDDHH24:MI:SS') A FROM USR_0_AO_DEC_HIST WHERE WI_NAME = '"+sWorkitemId+"') ORDER BY A";
					List<List<String>> recordList = formObject.getDataFromDB(sQuery);
					log.info("decision history query "+sQuery);
					loadListView(recordList,"CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,CHANNEL,WS_NAME,WS_COMMENTS",DECISION_LVW);							
				}
			*/}
		} catch (Exception e) {
			logInfo("onTabClick","tab details: "+data);
			logError("onTabClick", e);
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
	
	public void loadBlackListDataCPD() { // LoadBlackListData_CPD
		try {
			String sValues  = "";
			String resAPInsert = "";
			String sQuery = "";
			int iListViewRows = 0;
			List<List<String>> sOutput = null ;
			int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
			formObject.clearCombo(CPD_CHK_INT_BLK_LVW);
			formObject.clearCombo(CPD_HD2_LVW);
			String sName = getFinalDataComparison(CHECKBOX_FULLNAME_FCR ,CHECKBOX_FULLNAME_EIDA ,CHECKBOX_FULLNAME_MANUAL,FCR_NAME,EIDA_NAME
					       ,MANUAL_NAME).trim().toUpperCase();
			String sDOB = getFinalDataComparison(CHECKBOX_DOB_FCR,CHECKBOX_DOB_EIDA,CHECKBOX_DOB_MANUAL,FCR_DOB,EIDA_DOB
					      ,MANUAL_DOB).trim().toUpperCase();
			String sCountry = getFinalDataComparison(CHECKBOX_NATIONALITY_FCR,CHECKBOX_NATIONALITY_EIDA,CHECKBOX_NATIONALITY_MANUAL
					     ,FCR_NATIONALITY ,EIDA_NATIONALITY,MANUAL_NATIONALITY).trim();
			sQuery = "SELECT COUNT(WI_NAME) as COUNT_WI FROM USR_0_BLACKLIST_DATA_CPD WHERE WI_NAME ='"+sWorkitemId+"' "
					+ "AND SEARCHED_CUST_NAME='"+sName+"' AND SEARCHED_CUST_DOB='"+sDOB+"' AND SEARCHED_CUST_NATIONALITY='"+sCountry+"'";
			sOutput=formObject.getDataFromDB(sQuery);
			logInfo(" sQuery ",sQuery);
			logInfo(" sOutput ",sOutput.toString());
			logInfo(" sOutput ",sOutput.size()+""); 
	        if(sOutput != null && sOutput.size() > 0){
				if(sOutput.get(0).get(0).equalsIgnoreCase("0")) {			
					String outputXml = callBlacklist();
					outputXml = outputXml.replaceAll("null","");
					String sMatchRecvd = getTagValues(outputXml,"matchCount");   //discussed on call
					String sColumn  = "BLACKLIST_TYPE,CUST_ID,CUST_NAME,CUST_TYPE,DOB,EMP_NAME,GENDER,MOBILE_NO,MOTHER_NAME,NATIONALITY,NOTES"
							+ ",PASSPORT_NO,POBOX_NO,REG_DATE,VISA_NO,PHONE_NO,CUST_SNO,WI_NAME,SEARCHED_CUST_NAME,SEARCHED_CUST_DOB,"
							+ "SEARCHED_CUST_NATIONALITY";
					if(!sMatchRecvd.equalsIgnoreCase("") && !sMatchRecvd.equalsIgnoreCase("0")) {
						int iTotalMatch = Integer.parseInt(sMatchRecvd);
						String[] sAllRecord = getTagValue(outputXml,"Records").split(";");
						String[] sEachReacord;
						String sRecord ="";
						String sNationality="";
						sQuery = "Delete from USR_0_BLACKLIST_DATA_CPD where  WI_NAME='"+sWorkitemId+"' and CUST_SNO='"+(iSelectedRow+1)+"'";
						formObject.saveDataInDB(sQuery);
						logInfo(" sOutput ",sOutput.toString());
						try {
							for(int i = 0;i < iTotalMatch;i++) {
								logInfo(" i ",i+"");
								sRecord = sAllRecord[i]+",END";
								sRecord = sRecord.replaceAll("#col#",";");
								sEachReacord = sRecord.split(",");
								if(!sEachReacord[9].equalsIgnoreCase("")) {
									sQuery ="SELECT COUNTRY FROM USR_0_COUNTRY_MAST WHERE COUNTRY_CODE='"+sEachReacord[9]+"'";
									sOutput=formObject.getDataFromDB(sQuery);
									sNationality= sOutput.get(0).get(0);
								}
								sValues ="'"+sEachReacord[0].replaceAll("'","''")+"','"+sEachReacord[1]+"','"+sEachReacord[2].replaceAll("'","''")+"','"+sEachReacord[3]+"','"+sEachReacord[4]+"','"+sEachReacord[5].replaceAll("'","''")+"','"+sEachReacord[6]+"','"+sEachReacord[7]+"','"+sEachReacord[8].replaceAll("'","''")+"','"+sNationality+"','"+sEachReacord[10].replaceAll("'","''")+"','"+sEachReacord[11]+"','"+sEachReacord[12]+"','"+sEachReacord[14]+"','"+sEachReacord[15]+"','"+sEachReacord[16]+"','"+(iSelectedRow+1)+"','"+sWorkitemId+"','"+sName+"','"+sDOB+"','"+sCountry+"'";
								logInfo("loadBlackListDataCPD  sValues ",sValues);
								insertDataIntoDB("USR_0_BLACKLIST_DATA_CPD",sColumn,sValues);	
							}
						} catch(Exception e) {
							logError("Exception in loadBlackListDataCPD  ",e);
						}
					} else if(!sMatchRecvd.equalsIgnoreCase("0")) {
						//JOptionPane.showMessageDialog(null,"Unable to fetch blacklist data");
					}
				}
	        }
			sQuery ="SELECT CUST_NAME,NATIONALITY,DOB ,PASSPORT_NO,'',BLACKLIST_TYPE FROM USR_0_BLACKLIST_DATA_CPD WHERE WI_NAME='"+sWorkitemId+"' "
					+ "and CUST_SNO='"+(iSelectedRow+1)+"' AND BLACKLIST_TYPE IN (SELECT CHECKTYPE FROM USR_0_CHECK_TYPE WHERE TYPE='INT') "
							+ "ORDER BY CUST_NAME";					
			List<List<String>> recordList = formObject.getDataFromDB(sQuery);
			logInfo(" sQuery ",sQuery);
			logInfo(" sOutput ",recordList.toString());
			logInfo(" sOutput ",recordList.size()+""); 
			loadListView(recordList,"CUST_NAME,NATIONALITY,DOB ,PASSPORT_NO,'',BLACKLIST_TYPE",CPD_CHK_INT_BLK_LVW);
			sQuery ="SELECT CUST_NAME,NATIONALITY,DOB,PASSPORT_NO,'',BLACKLIST_TYPE FROM USR_0_BLACKLIST_DATA_CPD WHERE WI_NAME='"+sWorkitemId+"' "
					+ "and CUST_SNO='"+(iSelectedRow+1)+"' AND BLACKLIST_TYPE IN (SELECT CHECKTYPE FROM USR_0_CHECK_TYPE WHERE TYPE='EXT')"
							+ " ORDER BY CUST_NAME";			
			logInfo(" sQuery ",sQuery);
			logInfo(" sOutput ",recordList.toString());
			logInfo(" sOutput ",recordList.size()+""); 
			List<List<String>> recordList1 = formObject.getDataFromDB(sQuery);
			loadListView(recordList,"CUST_NAME,NATIONALITY,DOB ,PASSPORT_NO,'',BLACKLIST_TYPE",CPD_CHK_INT_BLK_LVW);
			logInfo(" sQuery ",sQuery);
			logInfo(" sOutput ",recordList.toString());
			logInfo(" sOutput ",recordList.size()+""); 
			iListViewRows = getListCount(CPD_CHK_INT_BLK_LVW);
			if(iListViewRows == 0) {
				formObject.setValue(CPD_CHK_MATCH_FOUND,"Verified False Positive");
			} else {
				formObject.setValue(CPD_CHK_MATCH_FOUND,"");
			}
			iListViewRows = getListCount(CPD_HD2_LVW);
			if(iListViewRows == 0) {
				formObject.setValue(CPD_MTCH_FOUND,"Verified False Positive");
			} else {
				formObject.setValue(CPD_MTCH_FOUND,"");
			}
			iListViewRows = getListCount(CPD_CNTRL_BNK_BAD_LVW);
			if(iListViewRows == 0) {
				formObject.setValue(CPD_MATCH_FOUND,"Verified False Positive");
			} else {
				formObject.setValue(CPD_MATCH_FOUND,"");
			}
		} catch(Exception e) {
			logError("Exception in loadBlackListDataCPD ",e);
		} finally {
			logInfo("Outside loadBlackListDataCPD ","  ");
		}
	}
	
	
	
	public void loadFacilitiesData(String sFacilityGrid) {
		try {
			JSONArray jsonArray=new JSONArray(); 
			JSONObject obj=new JSONObject();
			JSONObject obj1=new JSONObject();
			JSONObject obj2=new JSONObject();
			JSONObject obj3=new JSONObject();
			JSONObject obj4=new JSONObject();
			JSONObject obj5=new JSONObject();
			JSONObject obj6=new JSONObject();
			JSONObject obj7=new JSONObject();
			JSONObject obj8=new JSONObject();
			JSONObject obj9=new JSONObject();
			StringBuilder showList = new StringBuilder();
			String sPrimaryCust = getPrimaryCustomerSNO();
			logInfo("Inside sPrimaryCust: " , sPrimaryCust);
			//NGRepeater objChkRepeater = formObject.getNGRepeater("REPEAT_FRAME");
			String sAccRelation  = formObject.getTableCellValue(ACC_RELATION, Integer.parseInt(sPrimaryCust),7);
			logInfo("Inside sAccRelation: " , sAccRelation);
			formObject.clearTable(sFacilityGrid);
			//String sQuery = "SELECT CUST_ID,SMS_FLAG,IB_FLAG,IVR_FLAG,STAFF_FLAG_EXISTING,ESTATEMENT_FLAG, CHEQUEBOOK_BLOCK,DECODE(SIGNATUREPRESENTFLAG,'Y','Yes','No') AS SIGNATUREPRESENTFLAG,DECODE(PHOTOPRESENTFLAG,'Y','Yes','No') AS PHOTOPRESENTFLAG,CUSTOMER_OPEN_DATE,TOUCHPOINTS,MIB_FLAG FROM USR_0_CUST_TXN WHERE CUST_SNO ='"+sPrimaryCust+"' AND WI_NAME = '"+sWorkitemId+"'";
			String sQuery = "SELECT CUST_ID,CUSTOMER_OPEN_DATE,ESTATEMENT_FLAG, IB_FLAG,IVR_FLAG,MIB_FLAG,DECODE(PHOTOPRESENTFLAG,'Y','Yes','No') AS PHOTOPRESENTFLAG,SMS_FLAG,DECODE(SIGNATUREPRESENTFLAG,'Y','Yes','No') AS SIGNATUREPRESENTFLAG,STAFF_FLAG_EXISTING,TOUCHPOINTS,CHEQUEBOOK_BLOCK FROM USR_0_CUST_TXN WHERE CUST_SNO ='"+sPrimaryCust+"' AND WI_NAME = '"+sWorkitemId+"'";
			List<List<String>> sOutput=formObject.getDataFromDB(sQuery);
			logInfo("APP Assessment","sOutput----"+sOutput);
			logInfo("APP Assessment","sAccRelation----"+sAccRelation);
			if(sOutput!=null && sOutput.size()>0){
				String sCID = sOutput.get(0).get(0);
				if(sAccRelation.equalsIgnoreCase("New")) {
					//showList.append("<ListItems><ListItem><SubItem>"+sCID+"</SubItem><SubItem>Customer Open Date</SubItem><SubItem>Not Defined-NTB</SubItem></ListItem><ListItem><SubItem>"+sCID+"</SubItem><SubItem>Estatement Registered</SubItem><SubItem>Not Defined-NTB</SubItem></ListItem><ListItem><SubItem>"+sCID+"</SubItem><SubItem>IB Flag</SubItem><SubItem>Not Defined-NTB</SubItem></ListItem><ListItem><SubItem>"+sCID+"</SubItem><SubItem>IVR Flag</SubItem><SubItem>Not Defined-NTB</SubItem></ListItem><ListItem><SubItem>"+sCID+"</SubItem><SubItem>MIB Flag</SubItem><SubItem>Not Defined-NTB</SubItem></ListItem><ListItem><SubItem>"+sCID+"</SubItem><SubItem>PhotoPresentFlag</SubItem><SubItem>Not Defined-NTB</SubItem></ListItem><ListItem><SubItem>"+sCID+"</SubItem><SubItem>SMS Flag</SubItem><SubItem>Not Defined-NTB</SubItem></ListItem><ListItem><SubItem>"+sCID+"</SubItem><SubItem>SignaturePresentFlag</SubItem><SubItem>Not Defined-NTB</SubItem></ListItem><ListItem><SubItem>"+sCID+"</SubItem><SubItem>Staff Flag</SubItem><SubItem>Not Defined-NTB</SubItem></ListItem><ListItem><SubItem>"+sCID+"</SubItem><SubItem>TouchPoints</SubItem><SubItem>Not Defined-NTB</SubItem></ListItem></ListItems>");
					obj.put("CID",sCID);
					obj.put("FACILITY","Customer Open Date");
					obj.put("DESCRIPTION","Not Defined-NTB");
					obj1.put("CID",sCID);
					obj1.put("FACILITY","Estatement Registered");
					obj1.put("DESCRIPTION","Not Defined-NTB");
					obj2.put("CID",sCID);
					obj2.put("FACILITY","IB Flag");
					obj2.put("DESCRIPTION",sOutput.get(0).get(3));
					obj3.put("CID",sCID);
					obj3.put("FACILITY","IVR Flag");
					obj3.put("DESCRIPTION",sOutput.get(0).get(4));
					obj4.put("CID",sCID);
					obj4.put("FACILITY","MIB Flag");
					obj4.put("DESCRIPTION",sOutput.get(0).get(5));
					obj5.put("CID",sCID);
					obj5.put("FACILITY","PhotoPresentFlag");
					obj5.put("DESCRIPTION",sOutput.get(0).get(6));
					obj6.put("CID",sCID);
					obj6.put("FACILITY","SMS Flag");
					obj6.put("DESCRIPTION",sOutput.get(0).get(7));
					obj7.put("CID",sCID);
					obj7.put("FACILITY","SignaturePresentFlag");
					obj7.put("DESCRIPTION",sOutput.get(0).get(8));
					obj8.put("CID",sCID);
					obj8.put("FACILITY","Staff Flag");
					obj8.put("DESCRIPTION",sOutput.get(0).get(9));
					obj9.put("CID",sCID);
					obj9.put("FACILITY","TouchPoints");
					obj9.put("DESCRIPTION",sOutput.get(0).get(10));
					jsonArray.add(obj);
					jsonArray.add(obj1); 
					jsonArray.add(obj2);
					jsonArray.add(obj3);jsonArray.add(obj4);jsonArray.add(obj5);jsonArray.add(obj6);jsonArray.add(obj7);jsonArray.add(obj8);jsonArray.add(obj9);
					//logInfo(FAC_LVW_CRO);
					formObject.addDataToGrid(FAC_LVW_CRO,jsonArray);
				} else {
					//showList.append("<ListItems><ListItem><SubItem>"+sCID+"</SubItem><SubItem>Customer Open Date</SubItem><SubItem>"+getTagValue(sOutput,"CUSTOMER_OPEN_DATE")+"</SubItem></ListItem><ListItem><SubItem>"+sCID+"</SubItem><SubItem>Estatement Registered</SubItem><SubItem>"+getTagValue(sOutput,"ESTATEMENT_FLAG")+"</SubItem></ListItem><ListItem><SubItem>"+sCID+"</SubItem><SubItem>IB Flag</SubItem><SubItem>"+getTagValue(sOutput,"IB_FLAG")+"</SubItem></ListItem><ListItem><SubItem>"+sCID+"</SubItem><SubItem>IVR Flag</SubItem><SubItem>"+getTagValue(sOutput,"IVR_FLAG")+"</SubItem></ListItem><ListItem><SubItem>"+sCID+"</SubItem><SubItem>MIB Flag</SubItem><SubItem>"+getTagValue(sOutput,"MIB_FLAG")+"</SubItem></ListItem><ListItem><SubItem>"+sCID+"</SubItem><SubItem>PhotoPresentFlag</SubItem><SubItem>"+getTagValue(sOutput,"PHOTOPRESENTFLAG")+"</SubItem></ListItem><ListItem><SubItem>"+sCID+"</SubItem><SubItem>SMS Flag</SubItem><SubItem>"+getTagValue(sOutput,"SMS_FLAG")+"</SubItem></ListItem><ListItem><SubItem>"+sCID+"</SubItem><SubItem>SignaturePresentFlag</SubItem><SubItem>"+getTagValue(sOutput,"SIGNATUREPRESENTFLAG")+"</SubItem></ListItem><ListItem><SubItem>"+sCID+"</SubItem><SubItem>Staff Flag</SubItem><SubItem>"+getTagValue(sOutput,"STAFF_FLAG_EXISTING")+"</SubItem></ListItem><ListItem><SubItem>"+sCID+"</SubItem><SubItem>TouchPoints</SubItem><SubItem>"+getTagValue(sOutput,"TOUCHPOINTS")+"</SubItem></ListItem></ListItems>");
					obj.put("CID",sCID);
					obj.put("FACILITY","Customer Open Date");
					obj.put("DESCRIPTION",sOutput.get(0).get(1));
					obj1.put("CID",sCID);
					obj1.put("FACILITY","Estatement Registered");
					obj1.put("DESCRIPTION",sOutput.get(0).get(2));
					obj2.put("CID",sCID);
					obj2.put("FACILITY","IB Flag");
					obj2.put("DESCRIPTION",sOutput.get(0).get(3));
					obj3.put("CID",sCID);
					obj3.put("FACILITY","IVR Flag");
					obj3.put("DESCRIPTION",sOutput.get(0).get(4));
					obj4.put("CID",sCID);
					obj4.put("FACILITY","MIB Flag");
					obj4.put("DESCRIPTION",sOutput.get(0).get(5));
					obj5.put("CID",sCID);
					obj5.put("FACILITY","PhotoPresentFlag");
					obj5.put("DESCRIPTION",sOutput.get(0).get(6));
					obj6.put("CID",sCID);
					obj6.put("FACILITY","SMS Flag");
					obj6.put("DESCRIPTION",sOutput.get(0).get(7));
					obj7.put("CID",sCID);
					obj7.put("FACILITY","SignaturePresentFlag");
					obj7.put("DESCRIPTION",sOutput.get(0).get(8));
					obj8.put("CID",sCID);
					obj8.put("FACILITY","Staff Flag");
					obj8.put("DESCRIPTION",sOutput.get(0).get(9));
					obj9.put("CID",sCID);
					obj9.put("FACILITY","TouchPoints");
					obj9.put("DESCRIPTION",sOutput.get(0).get(10));
					jsonArray.add(obj);
					jsonArray.add(obj1); 
					jsonArray.add(obj2);
					jsonArray.add(obj3);jsonArray.add(obj4);jsonArray.add(obj5);jsonArray.add(obj6);jsonArray.add(obj7);jsonArray.add(obj8);jsonArray.add(obj9);
					//logInfo(FAC_LVW_CRO);
					formObject.addDataToGrid(FAC_LVW_CRO,jsonArray);
				}
				logInfo("APP Assessment","sOutput----"+sOutput);
				//formObject.NGAddListItem(sFacilityGrid,showList.toString());
			} 
		} catch(Exception e) {
			logError("Exception in loadFacilitiesData",e);
		}
	}
	
	private boolean saveAndNextValidations(String data) {
		logInfo("saveAndNextValidations","Inside saveAndNextValidations:");
		int sheetIndex = Integer.parseInt(data);
		if(sheetIndex == 7){
			logInfo("saveAndNextValidations","Inside sheetIndex:" +sheetIndex);
			if(!formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Reject")) {
				if(formObject.getValue(FINAL_RISK_VAL).toString().equalsIgnoreCase("Unacceptable Risk")) {
					sendMessageValuesList(CRO_DEC,"You cannot Approve as Risk is Unacceptable.");
					formObject.setValue(CRO_DEC,"Reject");
					enableControls(new String[]{CRO_REJ_REASON});
					return false;
				} if(formObject.getValue(FINAL_CUSTOMER_RISK).toString().equalsIgnoreCase("Not Eligible")) {
					sendMessageValuesList(CRO_DEC,"You cannot Approve as customers are not eligible.");
					formObject.setValue(CRO_DEC,"Reject");
					enableControls(new String[]{CRO_REJ_REASON});
					return false;
				} if(!(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only") || formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade"))) {
					int iListViewRows = getGridCount(PROD_OFRD_CRO_LVW);
					if(iListViewRows ==0) {						
						LoadOfferedProduct(PROD_OFRD_CRO_LVW); // CQRN-0000179564 changes for product offered not loading.-- Gautam/08/11/2021
						iListViewRows = getGridCount(PROD_OFRD_CRO_LVW);
						if(iListViewRows ==0) {	
							sendMessageValuesList(PROD_OFRD_CRO_LVW,"Some error occured in product offered display.");
							return false;
						}						
					}
				}
			}
		}
		return true;
	}
}
