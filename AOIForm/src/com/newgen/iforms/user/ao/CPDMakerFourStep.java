package com.newgen.iforms.user.ao;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
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
import com.newgen.iforms.user.ao.util.XMLParser;
import com.newgen.iforms.user.config.Constants;
import com.newgen.json.JSONObject;
import com.newgen.mvcbeans.model.WorkdeskModel;

public class CPDMakerFourStep extends Common implements Constants, IFormServerEventHandler {
	boolean custInfoTabLoad = false;
	String custSno = "";
	boolean sancScreeningTabClicked = false;
	boolean appAssessTabClicked = false;
	boolean validate = false;

	public CPDMakerFourStep(IFormReference formObject) {
		super(formObject);
	}

	@Override
	public void beforeFormLoad(FormDef arg0, IFormReference arg1) {
		String workitemName = formObject.getObjGeneralData().getM_strProcessInstanceId();
		log.info("WorkItem Name: " + workitemName);		
	}

	@Override
	public String executeCustomService(FormDef arg0, IFormReference arg1,
			String arg2, String arg3, String arg4) {
		return null;
	}

	@Override
	public JSONArray executeEvent(FormDef arg0, IFormReference arg1,
			String arg2, String arg3) {
		return null;
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
	public void updateDataInWidget(IFormReference arg0, String arg1) {
		// unsued
	}

	@Override
	public String validateDocumentConfiguration(String arg0, String arg1,
			File arg2, Locale arg3) {
		return null;
	}

	@Override
	public JSONArray validateSubmittedForm(FormDef arg0, IFormReference arg1,
			String arg2) {
		return null;
	}

	@Override
	public String executeServerEvent(IFormReference formObject, String controlName, String eventType, String data) {
		logInfo("Inside executeServerEvent >","");
		logInfo("executeServerEvent parameters ","Event: " + eventType + ", ControlName: " 
		+ controlName + ", Data: " + data);
		sendMessageList.clear();
		List<List<String>> list;
		String sQuery = "";
		String sWMSID ="";
		String sID = "";
		String sServiceShortName = "";
		String sHomeBrShortName = "";
		sendMessageList.clear();
		logInfo("sendmessagelist : ",String.valueOf(sendMessageList));
		String retMsg = getReturnMessage(true ,controlName);
		try {
			logInfo("isRestrictDisplay : ",isRestrictDisplay()+"");
			if(isRestrictDisplay()){
				return getReturnMessage(false,controlName,"The user is not authorized to access Staff Data.");
			}
			
			try {
				fillManualDataInBelowFields(controlName,formObject.getValue(controlName).toString());
				fillFCRDataInBelowFields(controlName,formObject.getValue(controlName).toString());
				fillEIDADataInBelowFields(controlName,formObject.getValue(controlName).toString());
			} catch (Exception e) {
				logInfo("executeServerEvent", "Exception in Event- " + eventType + "control name- " +controlName+ ": ");
				logError("executeServerEvent", e);
			}
			if(eventType.equalsIgnoreCase(EVENT_TYPE_LOAD)) {
				eventOnLoadCPDMakerFourStep(controlName, eventType, data);
				custSno = formObject.getValue(SELECTED_ROW_INDEX).toString();
				fbValidation();	//family banking
				populatePreAssesmentDetails(); //added by krishna
				populateUAEPassInfoStatus(sWorkitemId);//UAEPASS
				accountPurpose(); //AO DCRA
				additionalSource();
				logInfo("populatePepAssesmentDetails"," after populatePepAssesmentDetails");
				populatePepAssesmentDetails();
				logInfo("populatePepAssesmentDetails"," before populatePepAssesmentDetails");
				formObject.setStyle("SKIPUAEPASS_REASON",DISABLE,TRUE);//Gokul change 28042023
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)) {
				if("VALIDATE_PRODUCT_MODIFY".equalsIgnoreCase(controlName)) {
					if(checkFundTransferFields()) {
						return retMsg;
					}
				} else if("PRODUCT_ROW_CLICK".equalsIgnoreCase(controlName)) {
					enableDisableChequebookField();
					enableDisableProductFields();
				} else if (RD_INST_DEL.equalsIgnoreCase(controlName)){
					logInfo("RD_INST_DEL","inside if  no radio button is clicked");
					if(formObject.getValue(RD_INST_DEL).toString().equalsIgnoreCase("No"))
					{
						FETCH_INFO_flag_NO=false;
						FETCH_INFO_flag=false;
						formObject.setStyle(NOM_REQ,DISABLE,TRUE);
						formObject.setStyle(EXISTING_NOM_PRSN,DISABLE,TRUE);
						formObject.clearTable(DELIVERY_PREFERENCE);
						logInfo("","KioskId is null.");
						int sOut1=updateDataInDB(sExtTable,INSTANT_DEL_NO,"'true'","WI_NAME ='"+sWorkitemId+"'");
						logInfo("","Update query output sout-----"+sOut1);
						int sOutt1=updateDataInDB(sExtTable,INSTANT_DEL_YES,"'false'","WI_NAME ='"+sWorkitemId+"'");
					} else if (formObject.getValue(RD_INST_DEL).toString().equalsIgnoreCase("Yes")) {
						logInfo("","KioskId is null.");
						int sOut1=updateDataInDB(sExtTable,INSTANT_DEL_NO,"'false'","WI_NAME ='"+sWorkitemId+"'");
						logInfo("","Update query output sout-----"+sOut1);
						int sOutt1=updateDataInDB(sExtTable,INSTANT_DEL_YES,"'true'","WI_NAME ='"+sWorkitemId+"'");
				}
				}else if(controlName.equalsIgnoreCase(FETCH_INFO)) { //DP
					executeFetch(data);
					if(controlName.equalsIgnoreCase(FETCH_INFO)  && formObject.getValue(RD_INST_DEL).toString().equalsIgnoreCase("Yes")) {
						logInfo("CHANGE EVENT: FETCH_INFO","Fetch info button clicked after no radio button is clicked........111111");
						formObject.setStyle(NOM_REQ, DISABLE, TRUE);
						formObject.setStyle(EXISTING_NOM_PRSN, DISABLE, TRUE);
						formObject.clearTable(DELIVERY_PREFERENCE);
					}
				} else if(PRODUCT_QUEUE.equalsIgnoreCase(controlName) || "PROD_CODE".equalsIgnoreCase(controlName)) {
					if(addProductInGrid()) {
						return getReturnMessage(true, controlName);
					}
				} else if("PRODUCT_QUEUE_POST".equalsIgnoreCase(controlName)) {
					EnableEtihadFrame();
					LoadDebitCardCombo();
					EnableFamilyReffered();
				} else if(TABCLICK.equalsIgnoreCase(controlName)) {
					logInfo("Inside onClickEventCPDMakerFourStep EVENT_TYPE_CLICK  controlName1: "
							,controlName+" eventType: "+eventType+" data: "+data);
					onTabCPDMakerFourStep(data);
				} else if(SAVE_TAB_CLICK.equalsIgnoreCase(controlName)) {
//					if(Integer.parseInt(data.split(",")[1]) != 0) {
					int sheet = Integer.parseInt(data.split(",")[1]);
					if(sheet == 1 || sheet == 2 || sheet == 3 || sheet == 4 || sheet == 5) {
						onSaveClick();
						populatePepAssesmentDetails();
					} else if(sheet == 10) {
						insertUdfDetails();
					}
					//onTabCPDMakerFourStep(data);
				} else if("saveNextTabClick".equalsIgnoreCase(controlName)) {
					int sheet = Integer.parseInt(data.split(",")[1]);
					if(sheet == 1 || sheet == 2 || sheet == 3 || sheet == 4 || sheet == 5) {
						onSaveClick();
						populatePepAssesmentDetails();
					} else if(sheet == 10) {
						insertUdfDetails();
					}
					//saveAndNextCPDFourStep(data);
					if(saveAndNextCPDFourStep((String)data.split(",")[1])){
//						return getReturnMessage(true, controlName);
					}
				} else if (controlName.equalsIgnoreCase(BTN_APP_LEVEL_RISK)){
					appLevelMandatoryCheck =false;
					executeApplicationAssessmentRisk();
				} else if(controlName.equalsIgnoreCase("DELETEGRID")){
					checkCRS();
				} else if(controlName.equalsIgnoreCase("DELETEPRODUCTROW")){
					updateCustSnoInProductGrid(Integer.parseInt(data));
				} else if(controlName.equalsIgnoreCase(BTN_FCR_SRCH)) {
					String sCustomerID =formObject.getValue(IDS_REF_BY_CUST).toString();
					if(sCustomerID.equalsIgnoreCase("")) {
						sendMessageValuesList(IDS_REF_BY_CUST,CA093);
						return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
					}
					sQuery ="SELECT CUST_FULL_NAME, CUST_DOB FROM USR_0_CUST_MASTER WHERE CUST_ID='"+sCustomerID+"'";
					List<List<String>> sOutput1= formObject.getDataFromDB(sQuery);
					logInfo("submitbutton","sOutput1-----"+sOutput1);	
					if(!Integer.toString(sOutput1.size()).equalsIgnoreCase("0")) {	
						if(data.equalsIgnoreCase("")){
							//sendMessageValuesList("","This customer id has following details :"+"\n"+"\n"+"Name : "+sOutput1.get(0).get(0)+"\n"+"DOB : "+sOutput1.get(0).get(1)+"\n"+"\n"+"Is it correct?");//, null, JOptionPane.YES_NO_OPTION);	
							return getReturnMessage(true,controlName,"This customer id has following details :"+"\n"+"\n"+"Name : "+sOutput1.get(0).get(0)+"\n"+"DOB : "+sOutput1.get(0).get(1)+"\n"+"\n"+"Is it correct?");
						}
					} else {
						sendMessageValuesList("","No Customer present with this ID");
						return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
					}
				} else if(controlName.equalsIgnoreCase("postSubmit_BTN_FCR_SRCH")) {
					if(data.equalsIgnoreCase("YES_OPTION")) {
						validate = true;
						return getReturnMessage(false,"",BTN_FCR_SRCH+"###"+"Customer validated successfully");
					} else if(data.equalsIgnoreCase("NO_OPTION")){
						formObject.setValue(IDS_REF_BY_CUST,"");
						return getReturnMessage(false,"",BTN_FCR_SRCH+"###"+ "Please enter a new customer id");
					}	
				} else if(BTN_FG_VALIDATE.equalsIgnoreCase(controlName)) {	// Changes for Family Banking
					if(validateFBFetch()){
						familyBankingCalls();
					}
				} else {
					logInfo("Inside onClickEventCPDMakerFourStep controlName1: "
							,controlName+" eventType: "+eventType+" data: "+data);
					String msg = onClickEventCPDMakerFourStep(controlName, eventType, data);
					if(!msg.isEmpty()) {
						if(msg.equalsIgnoreCase(TRUE)) {
							return retMsg;
						} else return msg;
					}
				}
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_CHANGE)) {
				logInfo("Inside onChangeEventCPDMakerFourStep controlName1: "
						,controlName+" eventType: "+eventType+" data: "+data);
				onChangeEventCPDMakerFourStep(controlName, eventType, data);
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_LOSTFOCUS)) {
				onLostFocusEventCPDMakerFourStep(controlName, eventType, data);
			} else if("handlingJSPData".equalsIgnoreCase(eventType)) {
				  logInfo("handlingJSPData","controlName: "+controlName);
				  if(controlName.equalsIgnoreCase(BTN_PRD_SEARCH)) {
						searchProductList("USR_0_PRODUCT_OFFERED_CPD", data);
				  } 
			}
			/*try {
				fillManualDataInBelowFields(controlName,formObject.getValue(controlName).toString());
				fillFCRDataInBelowFields(controlName,formObject.getValue(controlName).toString());
				fillEIDADataInBelowFields(controlName,formObject.getValue(controlName).toString());
			} catch (Exception e) {
				logInfo("executeServerEvent", "Exception in Event- " + eventType + "control name- " +controlName+ ": ");
				logError("executeServerEvent", e);
			}*/
		} catch (Exception e) {
			logError("Exception in Event- " + eventType + "control name- " +controlName+ ": ", e);
		} finally{
			logInfo("sendMessageList = "," "+sendMessageList);			
			if(!sendMessageList.isEmpty()){
				if(sendMessageList.get(1).contains(" does not start with 971") ||
						sendMessageList.get(1).equalsIgnoreCase("Mobile number is not of 12 digits") || 
						sendMessageList.get(1).equalsIgnoreCase("Residence Telephone Number is not of 11 digits")) {
					return getReturnMessage(true,controlName,sendMessageList.get(0)+"###"
							+ sendMessageList.get(1));
				}
				return  getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ 
						sendMessageList.get(1).toString());
			}
		}
		return retMsg;
	}

	private void eventOnLoadCPDMakerFourStep(String controlName,String  eventType,String data) {
		logInfo("Inside eventOnLoadCPDMakerFourStep  eventType: " , eventType + ", Control Name: " + controlName + ", Data: " + data);
		try {
			
			String sQuery = "update ext_ao set new_maker ='"+sUserName+"'where wi_name ='"+sWorkitemId+"'";
			logInfo(" sQuery ",sQuery);
			List<List<String>> sOutput = formObject.getDataFromDB(sQuery);
			formObject.setValue(CRS_CERTIFICATION_OBTAINED,"Yes");
			loadSICombos();
			disableControls(new String[]{SEC_SRCH_EXIST_CUST,SEC_ADD_NEW_CUSTOMER,SEC_ACC_REL,SEC_SS_TRSD,
					SEC_SS_RISK_ASSESS,SEC_RISK_ASSESS_CRO,FINAL_ELIGIBILITY,PD_MARITALSTATUSOTHER,PD_OTHERS,
					PD_FULLNAME,PD_MOTHERMAIDENNAME,PD_DOB,PD_EIDANO,FAT_CUST_CLASSIFICATION,INDICIACOMBO});
			disableControls(new String[]{"NEG_INFO","NEG_AXPLAIN","FATF","FATF_EXPLAIN","KYC","KYC_AXPLAIN"});
			prefLang();
			populateCRSData();
			populateAuditSearch(SEARCH_DETAILS_LVW);
			if("".equalsIgnoreCase(formObject.getValue(RA_SIGN_TYPE).toString())) {
				formObject.setValue(RA_SIGN_TYPE, "Signature in English");
			}
			populateMakaniData();
			populatePassAndVisaFields();
			fieldValueBagSetCPDMaker(); 
			if("".equalsIgnoreCase(formObject.getValue(PD_CUSTSEGMENT).toString())){				
				formObject.clearCombo(PRO_CODE);
				formObject.addItemInCombo(PRO_CODE,"");
				//formObject.setNGListIndex("PRO_CODE", 0);
				formObject.clearCombo(EXCELLENCY_CNTR);
				formObject.addItemInCombo(EXCELLENCY_CNTR,"");
				//formObject.setNGListIndex("EXCELLENCY_CNTR", 0);					
			}
			populateScreeningDataCRO();
			frame23_CPD_Disable();
			String sDOB = getFinalDataComparison(CHECKBOX_DOB_FCR,CHECKBOX_DOB_EIDA,CHECKBOX_DOB_MANUAL,FCR_DOB,EIDA_DOB,MANUAL_DOB).trim().toUpperCase();
			formObject.setValue(PD_DOB,sDOB);
			//setTabVisible(); do later
			int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
//			int iRows = getGridCount(PRODUCT_QUEUE);
//			for(int i = 0; i < iRows; i++) { commented by ameena 18082023 for multiple account creation
//				logInfo(" sQuery1 ","Checking product selected CID"+formObject.getTableCellValue(PRODUCT_QUEUE, i, 14));
//				formObject.setTableCellValue(PRODUCT_QUEUE, i, 14 ,i+1+""); 
//				logInfo(" sQuery1 ","Checking product selected CID"+formObject.getTableCellValue(PRODUCT_QUEUE, i, 14));
//			}
			//changes for multiple account bug 7-11-2023
	        String sQuery5 = "SELECT CID FROM USR_0_PRODUCT_SELECTED WHERE WI_NAME='"+ sWorkitemId+ "' ORDER BY TO_NUMBER(INSERTIONORDERID)";
			logInfo(" sQuery5 ",sQuery5);
			List<List<String>> sOutput5 = formObject.getDataFromDB(sQuery5);
			for(int i = 0; i < sOutput5.size(); i++) {
				 logInfo(" CPDMakerFourStep sQuery5 ","Checking product selected CID"+formObject.getTableCellValue(PRODUCT_QUEUE, i, 14));
				 formObject.setTableCellValue(PRODUCT_QUEUE, i, 14 ,i+1+"");
		         logInfo(" CPDMakerFourStep sQuery5 ","Checking product selected CID"+formObject.getTableCellValue(PRODUCT_QUEUE, i, 14)); 
			}
			
			String sQuery1 = "select count(*) countwi from usr_0_ao_dec_hist  where ws_name='CPD Checker' and "
					+ "upper(userid)=upper('"+sUserName+"') and wi_name='"+sWorkitemId+"'";
			List<List<String>> sOutput1 = formObject.getDataFromDB(sQuery1);
			logInfo(" sQuery1 ",sQuery1);
			logInfo(" sOutput1 ",sOutput1.toString());
			logInfo(" sOutput1 ",String.valueOf(sOutput1.size())); 
			int sCount1 = (sOutput1 != null && sOutput1.size() > 0)  ? Integer.parseInt(sOutput1.get(0).get(0).toString()) : 0;
			logInfo(" sCount1 ",sCount1+""); 
			if(sCount1>0){
				formObject.applyGroup("CONTROL_SET_CPD_FORM"); //formObject.NGMakeFormReadOnly();
				sendMessageValuesList("","Maker & Checker cannot be same!");
			}
			String decision = formObject.getValue(CRO_DEC).toString();
		    if(decision.equalsIgnoreCase("Permanent Reject - Discard")) {
		    	formObject.applyGroup("CONTROL_SET_CPD_FORM");//formObject.NGMakeFormReadOnly();
		    	Frame_delivery();
		    }
			if((formObject.getValue(CHECKBOX_VISA_NO_MANUAL).toString().equalsIgnoreCase("true") || 
			 		 formObject.getValue(CHECKBOX_SELECTALL_MANUAL).toString().equalsIgnoreCase("true")) &&  
			 			returnVisaStatus().equalsIgnoreCase("Under Processing")) {
					formObject.setValue(CHECKBOX_VISA_NO_MANUAL,"true");
					formObject.setValue(MANUAL_VISANO,"MDSA");
			}
			if(formObject.getValue(CK_PER_DET).toString().equalsIgnoreCase("False")) {
				formObject.setStyle(DRP_RESEIDA, DISABLE,TRUE);
			} else if(returnVisaStatus().equalsIgnoreCase("Residency Visa")) {
				formObject.setStyle(DRP_RESEIDA, DISABLE,FALSE);
			} else {
				formObject.setStyle(DRP_RESEIDA, DISABLE,TRUE);
			}
			sQuery1 = "SELECT COUNT(1) AS COUNT FROM ACC_RELATION_REPEATER WHERE WI_NAME='"+sWorkitemId+"'";
			sOutput1 = formObject.getDataFromDB(sQuery1);
			logInfo(" sQuery1 ",sQuery1);
			logInfo(" sOutput1 ",sOutput1.toString());
			logInfo(" sOutput1 ",String.valueOf(sOutput1.size())); 
			int sCount2 = (sOutput1 != null && sOutput1.size() > 0)  ? Integer.parseInt(sOutput1.get(0).get(0).toString()) : 0;
			logInfo(" sCount2 ",sCount2+""); 
			if(sCount2 == 0) {
				String sQuery2 = "SELECT CHANNEL_TYPE FROM USR_0_SERVICE_CHANNEL WHERE "
						+ "SERVICE_CHANNEL='"+formObject.getValue("SOURCING_CHANNEL")+"'";
                List<List<String>> sOutput2 = formObject.getDataFromDB(sQuery2);
				if(sOutput1 != null && sOutput1.size() > 0) {
					  formObject.setValue("CHANNEL_TYPE",sOutput1.get(0).get(0));
				}
				if(formObject.getValue("CHANNEL_TYPE").toString().equalsIgnoreCase("Alternate") 
						&& formObject.getValue("DATA_ENTRY_MODE").toString().equalsIgnoreCase("Detail Data Entry")) {
					String sQuery3 = "SELECT LODGEMENT_REF_NO FROM USR_0_AOWEBSERVICEDATA WHERE WORKITEMNO='"+sWorkitemId +"'";
					List<List<String>> sOutput3 = formObject.getDataFromDB(sQuery3);
					logInfo(" sQuery3 ",sQuery3);
					logInfo(" sOutput3 ",sOutput3.toString());
					logInfo(" sOutput3 ",String.valueOf(sOutput3.size())); 
					String slodge = (sOutput3 != null && sOutput3.size() > 0)  ? sOutput3.get(0).get(0).toString() : "";
					logInfo(" slodge ",slodge); 
					if(!slodge.equalsIgnoreCase("")) {
						logInfo(" slodge ",slodge); 
						String sNO = "";
						int iRows1 = getGridCount(ACC_RELATION);
						if(iRows1 != 1) {
							sNO = formObject.getTableCellValue(ACC_RELATION,iRows1-1 , 0);
							sNO =(Integer.parseInt(sNO)+1)+"";
						} else {
							sNO = "1";
						}
						String sQuery4 = "SELECT FULLNAME,CID,MOBILE,EIDANUMBER,DOB,NATIONALITY FROM USR_0_WEBCUST_PERSONAL_INFO "
								+ "WHERE LODGEMENT_REF_NO='"+formObject.getValue("LODGEMENT_NO").toString()+"'";
						List<List<String>> sOutput4 = formObject.getDataFromDB(sQuery4);
						int sCount = sOutput4.size();
						logInfo(" sQuery4 ",sQuery4);
						logInfo(" sOutput4 ",sOutput4.toString());
						logInfo(" sOutput4 ",String.valueOf(sOutput4.size())); 
						logInfo(" sCount ",sCount+"");
						String sfullname = "";
						String sCid = "";
						String sMobile = "";
						String sEidaNo = "";
						String sDob = "";
						String sNationality = "";
						//iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
						for(int i = iRows1; i <= sCount ;i++) {
							//objChkRepeater111.addRow(); 	// using LoadListViewWithHardCodeValues
							sfullname = sOutput4.get(i).get(0);
							sCid = sOutput4.get(i).get(1);
							sMobile = sOutput4.get(i).get(2);
							sEidaNo = sOutput4.get(i).get(3);
							sDob = sOutput4.get(i).get(4);
							sNationality = sOutput4.get(0).get(5);
							/*formObject.setTableCellValue(ACC_RELATION, iSelectedRow, 0, sNO);
							formObject.setTableCellValue(ACC_RELATION, iSelectedRow, 1, sfullname);
							formObject.setTableCellValue(ACC_RELATION, iSelectedRow, 2, sCid);
							formObject.setTableCellValue(ACC_RELATION, iSelectedRow, 3, sMobile);
							formObject.setTableCellValue(ACC_RELATION, iSelectedRow, 4, sEidaNo);*/
							String sDOBi = sDob;
							logInfo(" sDOBi ",sDOBi); 
							if(sDOBi.indexOf(" ") > 0) {
								logInfo("sDOBi ",sDOBi); 
								String [] temp =sDOBi.split(" ");
								temp = temp[0].split("-");
								sDOBi = temp[2]+"/"+temp[1]+"/"+temp[0];
							}
							logInfo(" sDOBi ",sDOBi); 
							/*formObject.setTableCellValue(ACC_RELATION, iSelectedRow, 5, sDOBi);
							formObject.setTableCellValue(ACC_RELATION, iSelectedRow, 6, sNationality);
							formObject.setTableCellValue(ACC_RELATION, iSelectedRow, 7, "Existing");
							formObject.setTableCellValue(ACC_RELATION, iSelectedRow, 11, sWorkitemId);*/
							//objChkRepeater111.setEditable(i, "AO_ACC_RELATION.BANK_RELATION",false);
							//objChkRepeater111.setEnabled(i, "AO_ACC_RELATION.BANK_RELATION",false);
							//objChkRepeater111.setValue(i, "AO_ACC_RELATION.WI_NAME",sWorkitemID); 
							String tablename = "USR_0_WEBCUST_PERSONAL_INFO";
							String columnname = "";
							String accOwnTypeValue = "";
							String accRelationValue= "";
							String values = "";
							if(i >= 2 && formObject.getValue(ACC_OWN_TYPE).toString().equalsIgnoreCase("Joint")) {
								accRelationValue = "JAO";
								//formObject.setTableCellValue(ACC_RELATION, iSelectedRow, 9, "JAO");
							} else if(formObject.getValue(ACC_OWN_TYPE).toString().equalsIgnoreCase("Single")) {
								accOwnTypeValue = "Single";
								//formObject.setTableCellValue(ACC_RELATION, iSelectedRow, 8 , "Single");
								if(i == 1) {
									accRelationValue = "SOW";
									//formObject.setTableCellValue(ACC_RELATION, iSelectedRow, 9, "SOW");
									//objChkRepeater111.setEnabled(i, "AO_ACC_RELATION.ACC_RELATION",false);
									//objChkRepeater111.setEditable(i, "AO_ACC_RELATION.ACC_RELATION",false);
								} else {
									accRelationValue = "AUS";
									//formObject.setTableCellValue(ACC_RELATION, iSelectedRow, 9, "AUS");
									//objChkRepeater111.setEnabled(i, "AO_ACC_RELATION.ACC_RELATION",true);
									//objChkRepeater111.setEditable(i, "AO_ACC_RELATION.ACC_RELATION",true);
								}
							} else if(formObject.getValue(ACC_OWN_TYPE).toString().equalsIgnoreCase("Minor")) {
								if(i == 1) {
									accRelationValue = "Minor";
									//formObject.setTableCellValue(ACC_RELATION, iSelectedRow, 9, "Minor");
									//objChkRepeater111.setEnabled(i, "AO_ACC_RELATION.ACC_RELATION",false);
									//objChkRepeater111.setEditable(i, "AO_ACC_RELATION.ACC_RELATION",false);
								} else {
									accRelationValue = "Guardian";
									//formObject.setTableCellValue(ACC_RELATION, iSelectedRow, 9, "Guardian");
									//objChkRepeater111.setEnabled(i, "AO_ACC_RELATION.ACC_RELATION",true);
									//objChkRepeater111.setEditable(i, "AO_ACC_RELATION.ACC_RELATION",true);
								}
							}
							
							if(!accOwnTypeValue.equalsIgnoreCase("") && !accRelationValue.equalsIgnoreCase("")){
								values = sfullname+"##"+sCid+"##"+sMobile+"##"+sEidaNo+"##"+sDOBi+"##"+sNationality+
										"##Existing##"+sWorkitemId+"##"+accOwnTypeValue+"##"+accRelationValue;
								columnname = "FULLNAME,CID,MOBILE,EIDANUMBER,DOB,NATIONALITY,BANK_RELATION,WI_NAME,ACC_OWN_TYPE,"
										+ "ACC_RELATION";
							}else if(accOwnTypeValue.equalsIgnoreCase("") && accRelationValue.equalsIgnoreCase("")){
								values = sfullname+"##"+sCid+"##"+sMobile+"##"+sEidaNo+"##"+sDOBi+"##"+sNationality+
										"##Existing##"+sWorkitemId;
								columnname = "FULLNAME,CID,MOBILE,EIDANUMBER,DOB,NATIONALITY,BANK_RELATION,WI_NAME";
							} else {
								if(!accOwnTypeValue.equalsIgnoreCase("")){
									values = sfullname+"##"+sCid+"##"+sMobile+"##"+sEidaNo+"##"+sDOBi+"##"+sNationality+
											"##Existing##"+sWorkitemId+"##"+accOwnTypeValue;
									columnname = "FULLNAME,CID,MOBILE,EIDANUMBER,DOB,NATIONALITY,BANK_RELATION,WI_NAME,ACC_OWN_TYPE";
								}
								if(!accRelationValue.equalsIgnoreCase("")) {
									values = sfullname+"##"+sCid+"##"+sMobile+"##"+sEidaNo+"##"+sDOBi+"##"+sNationality+
											"##Existing##"+sWorkitemId+"##"+accRelationValue;
									columnname = "FULLNAME,CID,MOBILE,EIDANUMBER,DOB,NATIONALITY,BANK_RELATION,WI_NAME,ACC_RELATION";
								}
							}
							LoadListViewWithHardCodeValues(tablename,columnname,values);
							sNO = sNO+1;
						}
						String sTable = "ACC_RELATION_REPEATER";
						String sManual = "MANUAL";
						String updatequery = "update '"+sTable+"' set SEARCH_TYPE='"+sManual+"' where WI_NAME='"+sWorkitemId+"'";
						int sOut = formObject.saveDataInDB(updatequery);
						logInfo("sout-------",sOut+"");
					}
				}
			}
			String sQuery2 = "SELECT CALL_NAME FROM USR_0_INTEGRATION_CALLS WHERE WI_NAME='"+ sWorkitemId +"' AND STATUS='Success'";
		    List<List<String>> sOutput2 = formObject.getDataFromDB(sQuery2);
			String sCallName  =  (sOutput2 != null && sOutput2.size() > 0)  ? sOutput2.get(0).get(0).toString() : "";
			if(sCallName.contains("CUSTOMER_CREATION") || sCallName.contains("ACCOUNT_CREATION")) {
				loadIntegrationDataOnForm();
			}			
			setTemp_usr_0_product_selected();
			fieldValueUsr_0_Risk_Data();
			populateCRSData();
			if(formObject.getValue(PD_ANY_CHNG_CUST_INFO).toString().equalsIgnoreCase("Yes")){
				String segment = formObject.getValue(PD_CUSTSEGMENT).toString();
				getPromoCode(segment);
			}
			deleteECBCallsDetails();
			if(formObject.getValue(SALARY_TRANSFER).toString().equals("")){
				formObject.setValue(SALARY_TRANSFER,"No");
			}
			checkCRS();
			//formObject.setStyle(BTN_APP_LEVEL_RISK, DISABLE, FALSE);
			LoadInstantDelivery();
			populateStndInstr();
			//disable tab fb
			fbValidation();
			populatePreAssesmentDetails(); //added by krishna
			accountPurpose(); //AO DCRA
			additionalSource();
		} catch (Exception e)  {
			logError("Exception in eventOnLoadCPDMakerFourStep ",e);
		} finally {
			formObject.setStyle(DRP_RESEIDA, DISABLE, FALSE);
			formObject.setStyle(RA_CARRYNG_EID_CARD, DISABLE, FALSE);
			logInfo("Outside eventOnLoadCPDMakerFourStep ","  ");
		}
	}
	
	public void onTabCPDMakerFourStep(String data) {
		logInfo("onTabCPDMakerFourStep", "INSIDE");
		List<List<String>> recordList;
		String[] selectedSheetInfo = data.split(",");
		String tabCaption = selectedSheetInfo[0];
		int selectedSheetIndex = Integer.parseInt(selectedSheetInfo[1]);
		int sCurrTabIndex = selectedSheetIndex;
		boolean isCustInfoVisit=false; 
		logInfo("onTabCPDMakerFourStep", "selectedSheetIndex: "+selectedSheetIndex);
		try{
			if(selectedSheetIndex == 1 || selectedSheetIndex == 2 || selectedSheetIndex == 3 
					|| selectedSheetIndex == 4 || selectedSheetIndex == 5) {
				// sudhanhu start
				logInfo("onTabCPDMakerFourStep", "INSIDE qwerty");
				setPermCntry();
				setResCntry();
				setCorrCntry();
				//formObject.applyGroup(CONTROL_SET_MANUAL);
				//formObject.applyGroup(CONTROL_SET_EIDA);
				formObject.applyGroup(CONTROL_SET_FCR);
				if(!custInfoTabLoad || !custSno.equalsIgnoreCase(formObject.getValue(SELECTED_ROW_INDEX).toString())) {
					custSno = formObject.getValue(SELECTED_ROW_INDEX).toString();
					custInfoTabLoad = true;
					resetRekey();
					populateCRSData();
					populateMakaniData();
				}
				setRMCode();
				isCustInfoVisit=true;
				lockOthersFields();
				manageInternalSection();
				int fieldToFocus=Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
				String sBankRelation = formObject.getTableCellValue(ACC_RELATION, fieldToFocus, 7);
				String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus,9);
				manageCustomerChangeCheckboxes(sBankRelation,sAccRelation);
				if(formObject.getValue(CHK_EMP_DETAIL).toString().equalsIgnoreCase("true")) {
					if(formObject.getValue(PROFESION).toString().equalsIgnoreCase("Others") || formObject.getValue(PROFESION).toString().equalsIgnoreCase("")) {
						formObject.setStyle(ED_OTHER, DISABLE, FALSE);
					} else {
						formObject.setStyle(ED_OTHER, DISABLE, TRUE);
					}
					if(formObject.getValue(EMPNAME).toString().equalsIgnoreCase("Others") || formObject.getValue(EMPNAME).toString().equalsIgnoreCase("")) {
						formObject.setStyle(ED_EMPNAME, DISABLE, FALSE);//02052023 by Ameena //CompanyName issue
					} else {
						formObject.setStyle(ED_EMPNAME, DISABLE, TRUE);
					}
				}

				formObject.setStyle(RELIGION,DISABLE,TRUE);
				formObject.setStyle(MARITAL_STATUS,DISABLE,TRUE);
				formObject.setStyle(ED_CB_TML,DISABLE,TRUE);
				formObject.setStyle(ED_CB_NON_TML,DISABLE,TRUE);
				if(formObject.getValue(CHECKBOX_STATE_MANUAL).toString().equalsIgnoreCase("false")) {
					formObject.setStyle(MANUAL_STATE,DISABLE,FALSE);
				}
				if(formObject.getValue(GI_IS_CUST_VIP).toString().equalsIgnoreCase("")) {
					formObject.setValue(GI_IS_CUST_VIP, "No");
				}
				if(formObject.getValue(RA_IS_CUST_DEALNG_ARMAMNT).toString().equalsIgnoreCase("")) {
					formObject.setValue(RA_IS_CUST_DEALNG_ARMAMNT, "No");//RA_IS_CUST_DEALNG_ARMAMNT
				}
				if(formObject.getValue(RA_IS_CUST_DEALNG_ARMAMNT).toString().equalsIgnoreCase("")) {
					formObject.setValue(RA_IS_CUST_DEALNG_ARMAMNT, "No");//RA_IS_CUST_DEALNG_ARMAMNT
				}
				if(formObject.getValue(RA_IS_CUST_DEALNG_HAWALA).toString().equalsIgnoreCase("")) {
					formObject.setValue(RA_IS_CUST_DEALNG_HAWALA, "No");//Combo39
				}
				if(formObject.getValue(RA_PRPSE_TAX_EVSN).toString().equalsIgnoreCase("")) {
					formObject.setValue(RA_PRPSE_TAX_EVSN, "No");//Combo38
				}
				if(formObject.getValue(RA_IS_CUST_PEP).toString().equalsIgnoreCase("")) {
					formObject.setValue(RA_IS_CUST_PEP, "No");//Combo36
				}
				if(!formObject.getValue(FCR_NATIONALITY).toString().equalsIgnoreCase("") && 
						formObject.getValue(FCR_NATIONALITY).toString().equalsIgnoreCase("USA")) {
					if(formObject.getValue(FAT_US_PERSON).toString().equalsIgnoreCase("")) {
						formObject.setValue(FAT_US_PERSON, "YES");
						formObject.setStyle(FAT_US_PERSON,DISABLE,TRUE);
					}
				}
				if(formObject.getValue(ED_MONTHLY_INCM).toString().equalsIgnoreCase("")) {
					formObject.setValue(ED_MONTHLY_INCM,"0");
				}
				if(formObject.getValue(CHECKBOX_SELECTALL_MANUAL).toString().equalsIgnoreCase("False")) {
					setManualFieldsLock();
				}
				try {
					Calendar calendar = Calendar.getInstance();
					SimpleDateFormat simpledateformat = new SimpleDateFormat(DATEFORMAT);
					String scurrentDate = simpledateformat.format(calendar.getTime());
					//shahbaz
					String bank_Relationship = formObject.getTableCellValue(ACC_RELATION, 0, 7); 
					logInfo("populateLastKycDate ", "bank_Relationship: " + bank_Relationship);
					
					if(!bank_Relationship.equalsIgnoreCase("Existing")){
						if(formObject.getValue(GI_DATE_KYC_PREP).toString().equalsIgnoreCase("")) {
							formObject.setValue(GI_DATE_KYC_PREP, scurrentDate);
						}
					}
				}
				catch(Exception ex)
				{
					ex.getStackTrace();
				}
				formObject.setStyle(MANUAL_EIDANO,DISABLE,FALSE);

			
				
				//sudhanshu end
				boolean result=false;
				if(formObject.getValue(RA_IS_UAE_RESIDENT).toString().equalsIgnoreCase("")) {
					calculateResidencyStatus(RA_IS_UAE_RESIDENT);	
				}
				/*result=checkNatSegment();
				logInfo("onTabCPDMakerFourStep","NEW VALIDATION"+result);
				if(!result) {
					formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
					return ;
				}
				result = mandatoryComparisonData();//BUG ID: 1062275
				logInfo("onTabCPDMakerFourStep","COMMAND50 KYC result"+result);
				if(!result) {
					return ;
				}
				result = mandatoryIndividualInfo();
				logInfo("onTabCPDMakerFourStep","Individual Info result---"+result);
				if(!result) {
					return ;
				}
				result = mandatoryContactInfo();
				logInfo("onTabCPDMakerFourStep","COMMAND50 Contact Info result"+result);
				if(!result) {
					return ;
				}				
				String sFinalData = getFinalDataComparison(CHECKBOX_COUNTRY_PER_RES_FCR,CHECKBOX_COUNTRY_PER_RES_EIDA
						,CHECKBOX_COUNTRY_PER_RES_MANUAL,
						FCR_PER_CNTRY,EIDA_PER_CNTRY,MANUAL_PER_CNTRY);
				if(!sFinalData.equalsIgnoreCase(formObject.getValue(PERM_CNTRY).toString())) {
					sendMessageValuesList(PERM_CNTRY,"Permanent Country data is not same. Please change it.");
					return;
				}
				if(!formObject.getValue(CUST_NATIONALITY).toString().equalsIgnoreCase("United Arab Emirates")) {
					result = mandatoryEmploymentInfo();
					logInfo("onTabCPDMakerFourStep","COMMAND50 Employment result---"+result);
				} else  {
					if(!formObject.getValue(ED_DATE_OF_JOING).toString().equalsIgnoreCase("")) {
						if(!validateFutureDate(ED_DATE_OF_JOING,"Date of Joining")) {
							sCurrTabIndex=1;
							return ;
						}
					}
				}
				result = MandatoryiKYC_CPD();
				logInfo("onTabCPDMakerFourStep","COMMAND50 KYC result---"+result);
				if(!result) {
					sCurrTabIndex=1;
					return ;
				}
				String custSegment= formObject.getValue(PD_CUSTSEGMENT).toString(); 
				List<List<String>> List = formObject.getDataFromDB("select iscrsmandatory from usr_0_cust_segment "
						+ "where cust_segment='"+custSegment+"'");
				String ismandatory = null;
				try {
					ismandatory = List.get(0).get(0);
				} catch (Exception e) {
				logInfo("onTabCPDMakerFourStep","CUST Segment result: "+ismandatory);
				}
				boolean custSegmentCheck;
				if(ismandatory.equalsIgnoreCase("Yes"))
					custSegmentCheck=true;
				else
					custSegmentCheck=false;
				result = mandatoryCRSCheck(custSegmentCheck);
				logInfo("onTabCPDMakerFourStep","Submit CRS Check result--"+result);
				if(!result) {
					formObject.setStyle(BTN_SUBMIT,DISABLE,FALSE);
					return ;
				}
				result= mandatoryAtFatca();
				logInfo("onTabCPDMakerFourStep","Submit validation fatca result now---"+result);
				if(!result) {
					formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
					return ;
				}
				if(!validatePassportType(CHECKBOX_PASSPORT_TYPE_FCR,CHECKBOX_PASSPORT_TYPE_EIDA,
						CHECKBOX_PASSPORT_TYPE_MANUAL,FCR_PASSTYPE,EIDA_PSSTYPE,MANUAL_PASSTYPE,CA018,HD_PASS_TYPE)) {
					logInfo("onTabCPDMakerFourStep","INSIDE DDE_CUSTACC ValidatePassportType");
					formObject.setStyle(BTN_SUBMIT,DISABLE,FALSE);
					return;
				}
				if(!ValidateComparisonDataCombo(CHECKBOX_VISA_STATUS_FCR,CHECKBOX_VISA_STATUS_EIDA,CHECKBOX_VISA_STATUS_MANUAL,FCR_VISASTATUS,EIDA_VISASTATUS,
						MANUAL_VISASTATUS,CA019,"Mandatory","Visa Status")) {
					return;
				}
				if(!validateVisaStatus()) {
					formObject.setStyle(BTN_SUBMIT,DISABLE,FALSE);
					return;
				}
				boolean validate=false;
				if(validate==false && !formObject.getValue(IDS_REF_BY_CUST).toString().equalsIgnoreCase("")) {
					sendMessageValuesList("Command45", "Please Validate Reffered by customer ID.");
					return;	
				}
				try  {
					String sFinalDOB = getFinalDataComparison(CHECKBOX_DOB_FCR,CHECKBOX_DOB_EIDA,CHECKBOX_DOB_MANUAL,FCR_DOB,EIDA_DOB,MANUAL_DOB);
					int age = calculateAge(sFinalDOB);
					int age1 = CalculateAge1(sFinalDOB);
					int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
					sAccRelation = formObject.getTableCellValue(ACC_RELATION, iSelectedRow,9);
					String sQueryy="select VALUE from usr_0_defaultvalue_fcr where name='Minor_Age'";
					List = formObject.getDataFromDB(sQueryy);
					logInfo("onTabCPDMakerFourStep","sQueryy"+sQueryy);
					String a =  List.get(0).get(0);
					int sMinorAge= Integer.parseInt(a);
					logInfo("onTabCPDMakerFourStep",""+sMinorAge);
					if(sAccRelation.equalsIgnoreCase("Minor")) {
						if(age1 >=sMinorAge) {
							if(formObject.getValue(CHECKBOX_DOB_FCR).toString().equalsIgnoreCase(TRUE)) {
								sendMessageValuesList(FCR_DOB,"Date Of Birth Should Be Less Than "+sMinorAge+" Years For Minor Customer.");
							}
							else if(formObject.getValue(CHECKBOX_DOB_EIDA).toString().equalsIgnoreCase(TRUE)) {
								sendMessageValuesList(EIDA_DOB,"Date Of Birth Should Be Less Than "+sMinorAge+" Years For Minor Customer.");
							}
							else if(formObject.getValue(CHECKBOX_DOB_MANUAL).toString().equalsIgnoreCase(TRUE)) {
								sendMessageValuesList(MANUAL_DOB,"Date Of Birth Should Be Less Than "+sMinorAge+" Years For Minor Customer.");
							}
							return;	
						}
					} else {
						if(!formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only")) {
							if(age<18) {
								if(formObject.getValue(CHECKBOX_DOB_FCR).toString().equalsIgnoreCase(TRUE)) {
									sendMessageValuesList(FCR_DOB,"Date Of Birth Should Be Greater Than or "
											+ "equal to 18 Years.");
								}
								else if(formObject.getValue(CHECKBOX_DOB_EIDA).toString().equalsIgnoreCase(TRUE)) {
									sendMessageValuesList(EIDA_DOB,"Date Of Birth Should Be Greater Than or equal to 18 Years.");
								}
								else if(formObject.getValue(CHECKBOX_DOB_MANUAL).toString().equalsIgnoreCase(TRUE)) {
									sendMessageValuesList(MANUAL_DOB,"Date Of Birth Should Be Greater Than or "
											+ "equal to 18 Years.");
								}
								return;	
							}
						}
					}
				}
				catch (Exception e) {
					logError("executeServerEvent", e);
				}				try {
					int sSelectedRow=Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());				
					String sQuery1 ="SELECT IS_DEDUPE_CLICK_CPD FROM USR_0_CUST_TXN WHERE WI_NAME ='"+sWorkitemId+"' "
							+ "and cust_sno='"+sSelectedRow+"'";
					List=formObject.getDataFromDB(sQuery1);
					String dedupeDone = "";
					if(List != null && List.size() > 0) {
						dedupeDone = List.get(0).get(0);
					}
					if(!dedupeDone.equalsIgnoreCase(TRUE)) {
						sendMessageValuesList("", "Please Do Dedupe Search For This Customer.");
						return ;
					}
				}
				catch (Exception e) {
					logError("executeServerEvent", e);
				}
				if(!formObject.getValue(PD_DATEOFATTAININGMAT).toString().equalsIgnoreCase("")) {
					SimpleDateFormat f = new SimpleDateFormat(formObject.getValue(DATEFORMAT).toString());
					Date d=new Date();
					logInfo("onTabCPDMakerFourStep","Sysdate = "+f.format(d).toString());
					boolean rtn = compareDateMMM(formObject.getValue(PD_DATEOFATTAININGMAT).toString(),f.format(d));
					logInfo("onTabCPDMakerFourStep","rtn == "+rtn);
					if(!rtn) {
						sendMessageValuesList(PD_DATEOFATTAININGMAT, 
								"Date Of Attaining Majority Should Be Future Date.");
						sCurrTabIndex=1;
						return;	
					}
				}*///commented for tabclick on 11july2021
				logInfo("onTabCPDMakerFourStep","Inside sMode=R----");
				saveKYCInfo();		
				saveKycMultiDropDownData();
//				savePreAssessmentDetails();    //shahbaz
				saveIndividualInfo();
				saveComparisonData();
				saveIndividualContactInfo();
				saveClientQuestionsData();  
				saveCRSDetails(); 
				try {
					String sQuery1="SELECT COUNT(A.WI_NAME) IS_MOB_CHANGE "
							+ "FROM USR_0_CUST_TXN A, ACC_RELATION_REPEATER B WHERE A.WI_NAME='"+ sWorkitemId +"' "
							+ "AND A.WI_NAME=B.WI_NAME  AND A.CUST_SNO =B.SNO AND B.BANK_RELATION='Existing'"
							+ " AND A.final_mobile_no <> nvl(A.fcr_mobile_no,0) "
							+ "and a.final_mobile_no <> nvl(a.AFTER_CONT_CNTR_MOB_NO,0)";
					List<List<String>> out = formObject.getDataFromDB(sQuery1);
					String isMobChange = out.get(0).get(0);
					if(Integer.parseInt(isMobChange)>0) {
						sQuery1="SELECT COUNT(WI_NAME) IS_MOB_CHANGE FROM USR_0_CHANGE_TRACKER "
								+ "WHERE WI_NAME='"+ sWorkitemId +"' AND CUST_SNO ='"+Integer.toString(fieldToFocus+1)
								+"' AND FIELD_NAME ='MOBILE' "
								+ "AND WORK_STEP in ('CPD Maker','QDE_Cust_Info','DDE_Cust_Info') "
								+ "AND STATUS='Pending'";
						logInfo("onTabCPDMakerFourStep","sQuery1----"+sQuery1);
						out = formObject.getDataFromDB(sQuery1);
						String output = out.get(0).get(0);
						if(output.equalsIgnoreCase("0")) {
							formObject.setValue(MOBILE_CHANGE_FLAG,"False");
						}
						else {
							formObject.setValue(MOBILE_CHANGE_FLAG,"True");
						}
					} else {
						formObject.setValue(MOBILE_CHANGE_FLAG,"False");
					}
				} catch(Exception e) {
					logError("executeServerEvent",e);
				}
				try {
					int iProcessedCustomer=Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
					String sCID = formObject.getTableCellValue(ACC_RELATION, iProcessedCustomer,3);//CID
					String sCASA= "";
					sBankRelation = formObject.getTableCellValue(ACC_RELATION, iProcessedCustomer,7);//bnkrel = 7
					String sCustNo="";				
					sCustNo = formObject.getTableCellValue(ACC_RELATION, iProcessedCustomer,0);
					if(sBankRelation.equalsIgnoreCase("Existing")) {
						String sOutput="SELECT COUNT(1) AS COUNT_WI "
								+ "FROM USR_0_PRODUCT_EXISTING WHERE WI_NAME ='"+sWorkitemId+"' "
								+ "AND CUSTOMER_ID ='"+sCID+"' AND ACCOUNT_TYPE ='CASA'";
						List<List<String>> out = formObject.getDataFromDB(sOutput);
						logInfo("onTabCPDMakerFourStep","X"+sOutput);
						sCASA = out.get(0).get(0);						
					}
					if(selectedSheetIndex == 1 && (sBankRelation.equalsIgnoreCase("New") 
							|| sCASA.equalsIgnoreCase("0"))) {
						String output=getApplicationRiskInputXML(iProcessedCustomer+1);
						logInfo("onTabCPDMakerFourStep","XML:"+output);
						String outputresult = socket.connectToSocket(output);
						logInfo("onTabCPDMakerFourStep","outputresult--in webservice--"+outputresult);
						if(outputresult.equalsIgnoreCase("NO")) {
							sendMessageValuesList("", "Selected passport holder and Non "
									+ "UAE Residents, not allowed to open Account");
							return;
						}		
						//to be checked by YAMINI
						else if(outputresult.equalsIgnoreCase("Partial")) {
							/*getReturnMessage(true, "SHOWALERT");
							int respose=JOptionPane.showConfirmDialog(null,"Selected passport holder Residents does "
									+ "not meet conditions,\nHence not allowed to open Account. Do you still want"
									+ " to proceed with account opening?",null,JOptionPane.YES_NO_OPTION);
							if(respose==JOptionPane.YES_OPTION) {
								formObject.setValue("NIG_CPDMAKER","YES");
								String updatequery="update USR_0_CUST_TXN set NIGEXCEPTIONCPDMAKER='YES' "
										+ "Where WI_NAME='"+formObject.getValue("AO_WI_Name")+"' AND CUST_SNO ='"+sCustNo+"'";
								formObject.saveDataInDB(updatequery);
							} else {
								return;
							}*/
							sendMessageValuesList("", "Selected passport holder Residents does not meet conditions,"
									+ "\nHence not allowed to open Account. Do you still want to proceed with account"
									+ " opening?");
						}
					}
				} catch (Exception e) {
					logError("executeServerEvent", e);
				}
				boolean isNextClick = true;
				if(selectedSheetIndex == 4) {
					if(formObject.getValue(CRS_CERTIFICATION_OBTAINED).toString().equalsIgnoreCase("Yes")) {
						formObject.setValue(CRS_CLASSIFICATION,"UPDATED-DOCUMENTED");
					} 
				} else if(selectedSheetIndex == 2) {
					setEIDAInPersonalAndKYCTab();
					verifyChequeBook();
					String proCode = formObject.getValue(PRO_CODE).toString();
					getPromoCode(formObject.getValue(PD_CUSTSEGMENT).toString());
					if(!proCode.isEmpty()) {
						formObject.setValue(PRO_CODE, proCode);
					}
				}
			} else if(selectedSheetIndex == 6 || selectedSheetIndex == 7) {
				sancScreeningTabClicked = true;
				setDisableCalculateAppRiskButton(sWorkitemId);
				logInfo("onTabCPDMakerFourStep","INSIDE selectedSheetIndex "+selectedSheetIndex);
				reKeyInsert();
				updateReKeyTemp("CPD");
				if(!formObject.getValue(SCAN_MODE).toString().equalsIgnoreCase("New WMS ID")) {
					populateScreeningDataCRO();
				}
				int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
				logInfo("onTabCPDMakerFourStep","INSIDE iSelectedRow "+iSelectedRow+"");
			//	String sInputXML = getIndRiskInputXML(iSelectedRow);
			// AO DCRA COMMENTED //15082023
				String sInputXML = executeApplicationAssessmentRiskRetail(iSelectedRow);
				logInfo("onTabCPDMakerFourStep","sInputXML in CPD: "+sInputXML);
				String sOutputxml = "";
				if(sInputXML.contains("<APWebService_Input>")){
					sOutputxml = socket.connectToSocket(sInputXML);
//                  Added by Jamshed
//					AO DCRA COMMENTED 16082023
					XMLParser xp = new XMLParser(sOutputxml);
					String finalRisk_cd = xp.getValueOf("finalRisk");
					logInfo("executeApplicationAssessmentRiskRetail", "finalRisk_cd :  "+ finalRisk_cd);
					
					String finalRisk_cd_query ="select risk_value from usr_0_risk_values where risk_code='"+finalRisk_cd+"'";
					logInfo("executeApplicationAssessmentRiskRetail","finalRisk_cd_query= "+ finalRisk_cd_query);
					List<List<String>> output_list_db =formObject.getDataFromDB(finalRisk_cd_query); 
					String finalRisk_value=output_list_db.get(0).get(0); 
					logInfo("executeApplicationAssessmentRiskRetail","finalRisk_value= "+ finalRisk_value);
					sOutputxml=finalRisk_value;
					//end
				}
				else{
					sOutputxml = sInputXML;
				}
				logInfo("onTabCPDMakerFourStep","sOutput:-------------"+sOutputxml);
				if(!sOutputxml.equalsIgnoreCase("")) {
					logInfo("onTabCPDMakerFourStep","Inside selectedSheetIndex: "+selectedSheetIndex);
					String sSegment =formObject.getValue(PD_CUSTSEGMENT).toString();
					
					if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only") 
							|| (formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("New Account with Category Change") 
									&& iSelectedRow == 1)) {
						sSegment= formObject.getValue(NEW_CUST_SEGMENT).toString();
					}
					
					if((!sOutputxml.equalsIgnoreCase("Unacceptable Risk") && !sOutputxml.equalsIgnoreCase("PEP") 
							&& !sOutputxml.equalsIgnoreCase("UAE-PEP") && !sOutputxml.equalsIgnoreCase("Non UAE-PEP")
							&& !sOutputxml.equalsIgnoreCase("Increased Risk") && !sOutputxml.equalsIgnoreCase("HIO PEP")) // Added by Ameena 
							&& sSegment.equalsIgnoreCase("Private Clients"))
//							&& !formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only")) //commented by  Ameena for private client change
						// added by krishna
					{
//						sOutputxml="Increased Risk";
						sOutputxml="Medium Risk";
					}
//					 if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only") && 
//							formObject.getValue(NEW_CUST_SEGMENT).toString().equalsIgnoreCase("Private Clients")){
//						sOutputxml="Neutral Risk";
//						logInfo("onTabClickCPDFourStep ","Cust Segment" + formObject.getValue(NEW_CUST_SEGMENT).toString());
//						logInfo("onTabClickCPDFourStep","Request Type"+formObject.getValue(REQUEST_TYPE).toString());
//							
//						}	
					logInfo("onTabClickCPDFourStep>>> ","Cust Segment" + formObject.getValue(NEW_CUST_SEGMENT).toString());
			     	logInfo("onTabClickCPDFourStep>>>","Request Type"+formObject.getValue(REQUEST_TYPE).toString());
					String sWsName = formObject.getValue(CURR_WS_NAME).toString();
					String sriskColumn = "SNO,WI_NAME,WS_NAME,CUST_CUR_RISK_BANK";
					String sriskValue= "'"+iSelectedRow+"','"+sWorkitemId+"','"+sWsName+"','"+sOutputxml+"'";	
					logInfo("sriskColumn",sriskColumn);
					logInfo("sriskValue",sriskValue);
					insert_Into_Usr_0_Risk_Data(sriskColumn,sriskValue);
					String sUpdateDecision="update USR_0_CUST_TXN set CPD_CUST_INDI_RISK='"+ sOutputxml+"'"
							+ " Where WI_NAME='"+ sWorkitemId +"' and cust_sno='"+iSelectedRow+"'";
					logInfo("onTabCPDMakerFourStep","sUpdateDecision: "+sUpdateDecision);
					formObject.saveDataInDB(sUpdateDecision);
				}
				loadCPDcustdata();
				populateScreeningDataCPD();
				populateTRSD();
				populateTRSDCPD();
				if( formObject.getValue(CPD_TRSD_FINAL_DECISION).toString().equalsIgnoreCase("Approved"))
					formObject.setValue(CPD_FINAL_ELIGIBILITY, "Eligible");
				else
					formObject.setValue(CPD_FINAL_ELIGIBILITY, "Not Eligible");
				if( formObject.getValue(TRSD_APPROVEDRESULT).toString().equalsIgnoreCase("Approved"))
					formObject.setValue(FINAL_ELIGIBILITY, "Eligible");
				else
					formObject.setValue(FINAL_ELIGIBILITY, "Not Eligible");

				formObject.setStyle(TRSD_CHECK,DISABLE,TRUE);
				int iRows = getGridCount(ACC_RELATION);
				if(iRows-1 == iSelectedRow) {
					formObject.setStyle(BTN_NEXT_CUST,DISABLE,TRUE);
				} else {
					formObject.setStyle(BTN_NEXT_CUST,DISABLE,FALSE);
				}
				boolean result=false;
				iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
				String sCustNo = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 0);
				logInfo("onTabCPDMakerFourStep","iRows: "+iRows+"iSelectedRow: "+iSelectedRow);
				int sOutput= updateDataInDB("USR_0_BRMS_TRACKER","SCREENING_STATUS","'Success'",
						"WI_NAME ='"+sWorkitemId+"' AND CUST_SNO='"+sCustNo+"' AND SCREENING_STATUS ='Pending'");
				logInfo("onTabCPDMakerFourStep","sOutput----"+sOutput);
				saveScreeningDataCPD();//made 
			} else if (selectedSheetIndex == 8 || selectedSheetIndex == 9) {
				if(sancScreeningTabClicked) {
					appAssessTabClicked = true;
				}
				logInfo("onTabCPDMakerFourStep","INSIDE selectedSheetIndex "+selectedSheetIndex);
				String sQuery1 = "SELECT case when (select count(*) from acc_relation_repeater where wi_name='"+sWorkitemId+"'"
						+ " and sno is not null)=(select count(*) from usr_0_cust_txn where wi_name='"+sWorkitemId+"'"
						+ " and cust_sno is not null) then 'Done' else 'Not Done' end cust_info from dual";
				List<List<String>> output1=formObject.getDataFromDB(sQuery1);
				String custInfo = output1.get(0).get(0);
				String sQueryFinalElig = "SELECT case when (select count(*) from acc_relation_repeater where "
						+ "wi_name='"+sWorkitemId+"' and sno is not null)=(select count(*) from usr_0_cust_txn "
						+ "where wi_name='"+sWorkitemId+"' and cust_sno is not null and final_eligibility_cpd is not null)"
						+ " then 'Done' else 'Not Done' end FINAL_ELIGIBILITY from dual";
				List<List<String>> outputFinalElig = formObject.getDataFromDB(sQueryFinalElig);
				String finalElig=outputFinalElig.get(0).get(0);
				/*String sQuerySystemDec="SELECT case when (select count(*) from acc_relation_repeater where"
						+ " wi_name='"+sWorkitemId+"' and sno is not null)=(select count(*) from usr_0_cust_txn"
						+ " where wi_name='"+sWorkitemId+"' and cust_sno is not null and system_dec_cpd is not null)"
						+ " then 'Done' else 'Not Done' end SYSTEM_DEC_CPD from dual";
				List<List<String>> outputSystemDec=formObject.getDataFromDB(sQuerySystemDec);
				String systemDec=outputSystemDec.get(0).get(0);*/
                /*logInfo("onTabCPDMakerFourStep","sQuerySystemDec: "+sQuerySystemDec);
				logInfo("onTabCPDMakerFourStep","outputSystemDec: "+outputSystemDec);
			    */				
				logInfo("onTabCPDMakerFourStep","sQuery1: "+sQuery1+",output1: "+output1+",sQueryFinalElig: "
                 +sQueryFinalElig+",outputFinalElig: "+outputFinalElig);
				if(custInfo.equalsIgnoreCase("Not Done")) {
					formObject.setStyle(LABEL_APPASSESS,VISIBLE, TRUE);
				} else if (finalElig.equalsIgnoreCase("Not Done")) {
					formObject.setStyle(LABEL_APPASSESS,VISIBLE, TRUE);
				} else {
					formObject.setStyle(LABEL_APPASSESS,VISIBLE, FALSE);
					//kp
					loadApplicationAssessmentDataCPD(); 
					calculateCustomerRiskCPD();
					calculateAppRiskCPD();
					LoadFacilitiesData(FAC_LVW_CPD);
					if(!formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only")) {
						LoadOfferedProduct(PROD_SEC_OFRD_CPD_LVW);
						loadOfferedDebitCard(PROD_SEC_OFRD_CPD_LVW,FAC_OFRD_LVW_CPD); 
					}
					loadRequiredDocument(DOC_REQ_QUEUE_CPD);
				}
				saveOfferedProduct("USR_0_PRODUCT_OFFERED_CPD",PROD_SEC_OFRD_CPD_LVW);// newly Added
				saveFacilitiesData("USR_0_FACILITY_SELECTED_CPD",FAC_LVW_CPD);// newly Added
				saveOfferedDebitCard("USR_0_DEBITCARD_OFFERED_CPD",FAC_OFRD_LVW_CPD);// newly Added
				updateDataInDB("USR_0_BRMS_TRACKER","APP_ASSESSMENT_STATUS","'Success'","WI_NAME ='"+sWorkitemId+"' "
						+ "AND APP_ASSESSMENT_STATUS ='Pending'");
			} else if (selectedSheetIndex == 10) {
				try {
					clearControls(new String[] {GROUP_TYPE, CARD_TYPE});
					PopulatePrivateClientQuestions(); 
					LockChequebookField();	
					LockCreatedAccountRows();
					String sEtihadValue = formObject.getValue(EXISTING_ETIHAD_CUST).toString();
					String sEtihadNo = formObject.getValue(ETIHAD_NO).toString();
					if(getListCount(GROUP_TYPE)==0 || getListCount(GROUP_TYPE)==1) {   // to be added on form check
						/*formObject.getNGDataFromDataSource("Select GROUP_TYPE from USR_0_GROUP_TYPE order by 1", 1, GROUP_TYPE);
						addItemsDropDown(GROUP_TYPE,GROUP_TYPE);
						formObject.getNGDataFromDataSource("Select CARD_TYPE from USR_0_CARD_TYPE order by 1", 1, CARD_TYPE);
						addItemsDropDown(CARD_TYPE,CARD_TYPE);
						formObject.getNGDataFromDataSource("Select YES_NO from USR_0_YES_NO order by 1",1,"AO_EXISTING_ETIHAD_CUST");
						addItemsDropDown(EXISTING_ETIHAD_CUST,EXISTING_ETIHAD_CUST);*/
						formObject.setValue(EXISTING_ETIHAD_CUST,sEtihadValue);
						formObject.setValue(ETIHAD_NO,sEtihadNo);
					}
					int iRows = getGridCount(ACC_RELATION);
					formObject.setStyle(BUTTON_GENERATE_TEMPLATE,DISABLE, TRUE);
					formObject.setStyle(LABEL_APPASSESS,VISIBLE, TRUE);
					EnableEtihadFrame();
					String sLockDebitCard= "False";
					String sQuery= "SELECT CUST_ID,EMBOSS_NAME,CARD_TYPE_DESC,STATUS_DESC,ISSUANCE_DATE,EXPIRY_DATE FROM USR_0_DEBITCARD_EXISTING_CPD WHERE WI_NAME = '"+sWorkitemId+"'";		
					List recordList1 = formObject.getDataFromDB(sQuery);
					loadListView(recordList1,"CUST_ID,CUST_NAME,CARD_TYPE",FAC_OFRD_LVW_CRO);
					loadListView(recordList1,"CUST_ID,CUST_NAME,CARD_TYPE",ACC_INFO_EDC_LVW);	
					for(int iLoop=1;iLoop<iRows;iLoop++) {
						if(formObject.getTableCellValue(ACC_RELATION,iLoop,9).toString().equalsIgnoreCase("JAF")) {
							sLockDebitCard ="True";
							break;
						}					
						if(formObject.getTableCellValue(ACC_RELATION,iLoop,9).toString().equalsIgnoreCase("JAO")) {
							sLockDebitCard ="True";
							break;
						}
					}
					String sPrimaryCust = getPrimaryCustomerSNO();
					sQuery = "SELECT SIGN_STYLE FROM USR_0_CUST_TXN WHERE WI_NAME ='"+sWorkitemId+"' AND CUST_SNO ='"+sPrimaryCust+"'";
					List<List<String>> sOutput = formObject.getDataFromDB(sQuery);
					if((sOutput.get(0).get(0)).indexOf("Sign") == -1) {
						sLockDebitCard ="True";
					}
					if(sLockDebitCard.equalsIgnoreCase("True")) {
						formObject.setStyle(SEC_ACC_INFO_DCL,DISABLE,TRUE);
					} else {
						formObject.setStyle(SEC_ACC_INFO_DCL,DISABLE,FALSE);
						LoadDebitCardCombo();
					}
				} catch(Exception e) {
					logError("excetion", e);
				}

			} else if (selectedSheetIndex == 11) { //standing instrction
				formObject.setStyle("Frame41", DISABLE, TRUE);   //full optional product tab
			} else if (selectedSheetIndex == 14) { //standing instrction
				loadSICombos();
				//populateStndInstr();
				fbValidation();
			} else if (selectedSheetIndex == 13) {//Category Change
				//sudhanshu start
				List<List<String>> output;
				String query = "SELECT CUST_SEG,RM_NAME,RM_CODE FROM USR_0_CUST_TXN WHERE WI_NAME ='"+sWorkitemId+"' AND CUST_SNO='1'";
				logInfo("onTabClickCPDFourStep","query"+query);
				output = formObject.getDataFromDB(query);
				logInfo("onTabClickCPDFourStep","output"+output);
				formObject.addItemInCombo(OLD_RM_NAME_CAT_CHANGE, output.get(0).get(1));
				formObject.addItemInCombo(OLD_RM_CODE_CAT_CHANGE, output.get(0).get(2));
				setValuesFromDB (output,new String []{OLD_CUST_SEGMENT,OLD_RM_NAME_CAT_CHANGE,OLD_RM_CODE_CAT_CHANGE});
				String sReqType=formObject.getValue(REQUEST_TYPE).toString();
				if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only")) {	
					formObject.setStyle(SEC_SI, DISABLE, TRUE);
				}
				if(sReqType.equalsIgnoreCase("New Account with Category Change") || sReqType.equalsIgnoreCase("Category Change Only")) {
					String sCID = formObject.getTableCellValue(ACC_RELATION, 1, 1);
					List<List<String>> categoryChangeDate = formObject.getDataFromDB("SELECT MAX(CATEGORY_CHANGE_DATE) AS "
							+ "CATEGORY_CHANGE_DATE FROM USR_0_CUST_TXN WHERE CUST_ID ='"+sCID+"'");
					formObject.setValue(CHANNEL_TYPE,categoryChangeDate.get(0).get(0));
				}
				if(sReqType.equalsIgnoreCase("New Account with Category Change")) {
					formObject.setValue(SOURCE_NAME_CAT_CHANGE,formObject.getValue(SOURCE_NAME).toString());
					formObject.setValue(SOURCE_CODE_CAT_CHANGE,formObject.getValue(SOURCE_CODE).toString());
					formObject.setStyle(SOURCE_NAME_CAT_CHANGE, DISABLE, TRUE);
					formObject.setStyle(SOURCE_CODE_CAT_CHANGE, DISABLE, TRUE);
					formObject.setStyle(SRC_NAME_BTN, DISABLE, TRUE);
				}
				if(formObject.getValue(IS_CAT_BENEFIT_OTHER).toString().equalsIgnoreCase("False")) {
					formObject.setStyle(CAT_BENEFIT_OTHER, DISABLE, TRUE);
					formObject.setStyle(IS_CAT_BENEFIT_OTHER, DISABLE, TRUE);
					if(!formObject.getValue(NEW_CUST_SEGMENT).toString().equalsIgnoreCase("")) {
						formObject.setStyle(IS_CAT_BENEFIT_OTHER, DISABLE, TRUE);
						manageCategoryChangeSection();

					} else {
						formObject.setStyle(IS_SALARY_TRANSFER_CAT_CHANGE, DISABLE, TRUE);
						formObject.setStyle(IS_MORTAGAGE_CAT_CHANGE, DISABLE, TRUE);
						formObject.setStyle(IS_INSURANCE_CAT_CHANGE, DISABLE, TRUE);
						formObject.setStyle(IS_TRB_CAT_CHANGE, DISABLE, TRUE);
						formObject.setStyle(IS_OTHERS_CAT_CHANGE, DISABLE, TRUE);
						formObject.setStyle(IS_VVIP, DISABLE, TRUE);
						formObject.setStyle(IS_PREVILEGE_TP_CAT_CHANGE, DISABLE, TRUE);
						formObject.setStyle(IS_ENTERTAINMENT_CAT_CHANGE, DISABLE, TRUE);
						formObject.setStyle(IS_SHOPPING_CAT_CHANGE, DISABLE, TRUE);
						formObject.setStyle(IS_SPORT_CAT_CHANGE, DISABLE, TRUE);
						formObject.setStyle(IS_TRAVEL_CAT_CHANGE, DISABLE, TRUE);
						formObject.setStyle(IS_EXCELLENCY_TP_CAT_CHANGE, DISABLE, TRUE);
					}
				}
				else {
					formObject.setStyle(CAT_BENEFIT_OTHER, DISABLE, TRUE);
					formObject.setStyle(IS_CAT_BENEFIT_OTHER, DISABLE, TRUE);
					manageCategoryChangeSection();
				}
				if(formObject.getValue(NEW_CUST_SEGMENT).toString().equalsIgnoreCase("")) {
					formObject.clearCombo(PROMO_CODE_CAT_CHANGE);
					formObject.setValue(PROMO_CODE_CAT_CHANGE, "");
					//formObject.NGAddItem("AO_PROMO_CODE_CAT_CHANGE","--Select--");
					//formObject.setNGListIndex("AO_PROMO_CODE_CAT_CHANGE", 0);				
					formObject.clearCombo(EXCELLENCY_CENTER_CC);
					formObject.setValue(EXCELLENCY_CENTER_CC, "");
				}
				//sudhanshu end
				if(formObject.getValue(REQUEST_TYPE).toString().
						equalsIgnoreCase("New Account with Category Change") 
						|| formObject.getValue(REQUEST_TYPE).toString().
						equalsIgnoreCase("Category Change Only")) {
					boolean result=false;
					result = mandatoryCategoryChangeData();
					logInfo("onTabCPDMakerFourStep","NEXT_7 result---"+result);
					if(!result) {
						return ;
					}
					result=checkNatCatSegment();
					logInfo("onTabCPDMakerFourStep","NEW VALIDATION---"+result);
					if(!result) {
						formObject.setStyle(BTN_SUBMIT,DISABLE,FALSE);
						return ;
					}
				}
			} else if(selectedSheetIndex == 12){//(tabCaption.equalsIgnoreCase("Delivery Preferences")) {
				if(getListCount(DEL_DELIVERY_MODE)==0 || getListCount(DEL_DELIVERY_MODE)==1) {
					// to be added on form check
					/*formObject.getNGDataFromDataSource("Select delivery_mode from usr_0_delivery_mode order by 1",1,"AO_DEL_DELIVERY_MODE");
					addItemsDropDown("AO_DEL_DELIVERY_MODE","AO_DEL_DELIVERY_MODE");
					formObject.getNGDataFromDataSource("Select Branch from usr_0_brnch_of_instant_issue order by 1",1,"AO_BRNCH_OF_INSTANT_ISSUE");
					addItemsDropDown("AO_BRNCH_OF_INSTANT_ISSUE","AO_BRNCH_OF_INSTANT_ISSUE");
					formObject.getNGDataFromDataSource("Select YES_NO from USR_0_YES_NO order by 1",1,"AO_NOM_REQ");
					addItemsDropDown("AO_NOM_REQ","AO_NOM_REQ");
					formObject.getNGDataFromDataSource("Select YES_NO from USR_0_YES_NO order by 1",1,"AO_EXISTING_NOM_PRSN");
					addItemsDropDown("AO_NOM_REQ","AO_EXISTING_NOM_PRSN");*/
				}
				setMailingAddInToDel();
				Frame_delivery();
			} else if(selectedSheetIndex == 19){//} else if(selectedSheetIndex == 18){//(tabCaption.equalsIgnoreCase("Decision")) {
				formObject.setStyle(IS_COMPLIANCE_NAME_SCR, DISABLE, TRUE);
				formObject.setStyle(IS_COMPLIANCE_RISK_ASSESS, DISABLE, TRUE);
				formObject.setStyle(IS_PROD_APP_REQ, DISABLE, TRUE);
				formObject.setStyle(IS_CALL_BACK_REQ, DISABLE, TRUE);
				formObject.setStyle(L1_APP_REQ, VISIBLE, FALSE);
				formObject.setStyle(L2_APP_REQ, VISIBLE, FALSE);
				formObject.setStyle(L3_APP_REQ, VISIBLE, FALSE);
				formObject.setStyle(L4_APP_REQ, VISIBLE, FALSE);
				String sQuery = "Select to_char(WS_DECISION) from USR_0_DECISION_MASTER where ws_name='"+formObject.getValue(CURR_WS_NAME)+"' and route_type='"+formObject.getValue(SCAN_MODE)+"' order by to_char(WS_DECISION)";
				formObject.clearCombo(CRO_DEC);
				addDataInComboFromQuery(sQuery, CRO_DEC);
				/*int iCount = getListCount(CRO_DEC);
				if(iCount==0 || iCount==1)
				{
					formObject.getNGDataFromDataSource("Select '--Select--' from dual Union Select to_char(WS_DECISION) from USR_0_DECISION_MASTER where ws_name='"+formObject.getValue("AO_CURR_WS_NAME")+"'",1,"AO_CRO_DEC");
				}

				iCount = getListCount(CRO_REJ_REASON);
				if(iCount==0 || iCount==1)
				{
					formObject.getNGDataFromDataSource("Select '--Select--' from dual Union Select to_char(ws_rej_reason) from usr_0_rej_reason_mast",1,"AO_CRO_REJ_REASON");
				}

				if(getListCount(LISTVIEW_DECISION) == 0)
				{
					LoadListView("SELECT CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,CHANNEL,WS_NAME,WS_COMMENTS FROM (SELECT TO_CHAR(CREATE_DAT,'DD/MM/YYYY HH:MI:SS AM')CREATE_DAT, USERID, USERNAME, GROUP_NAME, WS_DECISION,REJ_REASON, CHANNEL, WS_NAME, WS_COMMENTS,TO_CHAR(CREATE_DAT,'YYYYMMDDHH24:MI:SS') A FROM USR_0_AO_DEC_HIST WHERE WI_NAME = '"+sWorkitemID+"') ORDER BY A",9,"LISTVIEW_DECISION","0,1,2,3,4,5,6,7,8");							
				}*/
			} else if(selectedSheetIndex == 17) {
				fbValidation();	//family  banking tab change check
				clearFBData(); //Clearing FB tab on category change
			}
			try {
				formObject.setStyle(DOC_APPROVAL_OBTAINED, DISABLE, TRUE);
				formObject.setStyle(COURT_ORD_TRADE_LIC, DISABLE, TRUE);
				String sQuery1 = "SELECT DOB FROM ACC_RELATION_REPEATER WHERE WI_NAME ='"+sWorkitemId+"'";	
				List<List<String>> sOutput1 = formObject.getDataFromDB(sQuery1);
				String minorDOB = sOutput1.get(0).get(0);
				int minorAge = CalculateAge(minorDOB);
				if((minorAge >= 18) && (minorAge <= 21)){
					sQuery1 = "SELECT COUNT(1) AS COUNT FROM ACC_RELATION_REPEATER WHERE acc_relation = 'Guardian' AND "
							+ "WI_NAME ='"+sWorkitemId+"'";
					sOutput1 = formObject.getDataFromDB(sQuery1);
					String guardianCount = sOutput1.get(0).get(0);
					if("0".equalsIgnoreCase(guardianCount)){
						formObject.setStyle(DOC_APPROVAL_OBTAINED, VISIBLE, TRUE);
						formObject.setStyle(COURT_ORD_TRADE_LIC, VISIBLE, TRUE);
						formObject.setStyle(DOC_APPROVAL_OBTAINED, DISABLE, FALSE);
						formObject.setStyle(COURT_ORD_TRADE_LIC, DISABLE, FALSE);
					}
				}
				sQuery1 = "SELECT PRODUCT_CODE FROM USR_0_PRODUCT_OFFERED WHERE WI_NAME ='"+sWorkitemId+"'";	
				sOutput1 = formObject.getDataFromDB(sQuery1);
				/*if(sOutput1 != null && sOutput1.size() > 0){
					String sProduct = sOutput1.get(0).get(0).trim();
					if("871".equalsIgnoreCase(sProduct)){
						formObject.setStyle(DOC_APPROVAL_OBTAINED, VISIBLE, FALSE);
						formObject.setStyle(COURT_ORD_TRADE_LIC, VISIBLE, FALSE);
					}	
				}*/
				for(int i=0; i<sOutput1.size(); i++) {
					String sProduct = sOutput1.get(i).get(0);
					logInfo("onTabClickCPDThreeStep","sProduct"+sProduct);
					if("871".equalsIgnoreCase(sProduct)){
						hideControls(new String[]{DOC_APPROVAL_OBTAINED,COURT_ORD_TRADE_LIC});
						break;
					}	
				}
			} catch (Exception e) {
				logError("Exception in onTabClick ",e);
			} 
		}catch (Exception e) {
			logError("onFourStep", e);
		}
	}

		private void onChangeEventCPDMakerFourStep(String controlName,String eventType, String data) {
			logInfo("onChangeEventCPDMakerFourStep1","INSIDE");
			String controlValue = "";
			try{
				controlValue = formObject.getValue(controlName).toString();
				logInfo("check control","controlValue : "+controlValue);
				if(formObject.getValue(CUST_PREFIX).toString().equalsIgnoreCase("Others")) {
					formObject.setStyle(PD_OTHERS,DISABLE,FALSE);
				} else {
					formObject.setStyle(PD_OTHERS,DISABLE,TRUE);
				}
			} catch(Exception e) {
				logError("onChangeEventCPDMakerFourStep",e);
			}
			logInfo("check control","set eida before : "+controlName);
			setEIDA();
			logInfo("check control","set eida after : "+controlName);
			if(controlName.equalsIgnoreCase("table103_trnsfr_from_acc_no")) {
//				int iRows = getGridCount(PRODUCT_QUEUE);				
//				int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()); 
				String sMode = formObject.getValue("table103_mode_of_funding").toString();
				if(!sMode.equalsIgnoreCase("")) {
					String sAccNo = formObject.getValue("table103_trnsfr_from_acc_no").toString();
					String sQuery= "SELECT DISTINCT ACC_BALANCE,CURRENCY FROM USR_0_PRODUCT_EXISTING WHERE "
							+ "WI_NAME ='"+sWorkitemId+"' AND ACC_NO = '"+sAccNo+"' ";
					List<List<String>> sOutput=formObject.getDataFromDB(sQuery);
					if(sOutput.size()>0) {
						String sAccBalance = sOutput.get(0).get(0);
						String sCurrency = sOutput.get(0).get(1);
						logInfo("PRODUCT_QUEUE.TRNSFR_FROM_ACC_NO","sOutput: "+sOutput);
						logInfo("PRODUCT_QUEUE.TRNSFR_FROM_ACC_NO","sAccBalance: "+sAccBalance+", sCurrency: "
								+sCurrency);
						formObject.setValue("table103_from_acc_bal",sAccBalance);
						formObject.setValue("table103_trnsfr_from_currency",sCurrency);
					}
				} else {
					sendMessageValuesList(PRODUCT_QUEUE,"Please Select Mode of Transfer first");
					return;
				}
//				if(iRows>0) {}
			} else if("table103_mode_of_funding".equalsIgnoreCase(controlName)) {
				manageFundTransfer();
			}else if(controlName.equalsIgnoreCase(ADDITIONAL_SOURCES_INCOME_AED)){
				logInfo("ADDITIONAL_SOURCES_INCOME_AED", "inside ADDITIONAL_SOURCES_INCOME_AED: ");
				additionalSource();
	    	}else if(controlName.equalsIgnoreCase("LISTVIEW_PUR_ACC_RELATION")){
				logInfo("LISTVIEW_PUR_ACC_RELATION", "inside QVAR_ACC_POURPOSE: ");
				accountPurpose();
			} else if(controlName.equalsIgnoreCase(PERM_CNTRY)) {
				String sState = formObject.getValue(PERM_STATE).toString();
				String sCity = formObject.getValue(PA_CITY).toString();
				logInfo("PERM_CNTRY","sCitykdd Perm----"+sCity);
				if(formObject.getValue(controlName).toString().equalsIgnoreCase("UNITED ARAB EMIRATES")) {
					formObject.clearCombo(PERM_STATE);	
					String[] states = UAESTATES.split(",");
					for(int i=0; i < states.length; i++) {
						formObject.addItemInCombo(PERM_STATE, states[i], states[i]);							
					}
					formObject.setValue(PERM_STATE,sState);
					formObject.clearCombo(PA_CITY);
					for(int i=0; i < states.length; i++) {
						formObject.addItemInCombo(PA_CITY, states[i], states[i]);							
					}
					formObject.setValue(PA_CITY,sCity);
					enableControls(new String[]{PERM_MAKANI});
				} else {
					logInfo("CORR_CNTRY","skddd----"+sCity);
					formObject.clearCombo(PERM_STATE);
					formObject.addItemInCombo(PERM_STATE, OTHERTHENUAESTATES, OTHERTHENUAESTATES);
					formObject.setValue(PERM_STATE,sState);
					formObject.clearCombo(PA_CITY);
					formObject.addItemInCombo(PA_CITY, "OTHERS");
					formObject.setValue(PA_CITY,sCity);
					disableControls(new String[]{PERM_MAKANI});
				}
			} else if(controlName.equalsIgnoreCase(DFC_STATIONERY_AVAIL)) {
				try {
					if(formObject.getValue(DFC_STATIONERY_AVAIL).toString().isEmpty() && fetchInfoFlag) {
						updateDataInDB(sExtTable,INSTANT_DEL_NO,"'true'","WI_NAME ='"
								+sWorkitemId+"'");
						updateDataInDB(sExtTable,INSTANT_DEL_YES,"'false'","WI_NAME ='"
								+sWorkitemId+"'");
						 formObject.setValue(RD_INST_DEL, "No");
						/*formObject.setValue(INSTANT_DEL_YES,FALSE);
						formObject.setValue(INSTANT_DEL_NO,TRUE); */
						disableControls(new String[]{INSTANT_DEL_YES, INSTANT_DEL_NO});
					} else if(!formObject.getValue(DFC_STATIONERY_AVAIL).toString().isEmpty()) {
						enableControls(new String[]{INSTANT_DEL_YES, INSTANT_DEL_NO});
						updateDataInDB(sExtTable,INSTANT_DEL_YES,"'true'","WI_NAME ='"
								+sWorkitemId+"'");
						updateDataInDB(sExtTable,INSTANT_DEL_NO,"'false'","WI_NAME ='"
								+sWorkitemId+"'");
						 formObject.setValue(RD_INST_DEL, "Yes");
					/*	formObject.setValue(INSTANT_DEL_NO,FALSE);
						formObject.setValue(INSTANT_DEL_YES,TRUE); */
					}
				} catch(Exception e) {
					logError("CHANGE EVENT: DFC_STATIONERY_AVAIL", e);;
				}
			} else if(controlName.equalsIgnoreCase(CPD_RISK_CURRENT_RSK_BANK)){
				/*boolean currentRiskChange = false; 
				if(controlName.equalsIgnoreCase(CPD_RISK_CURRENT_RSK_BANK)){
					currentRiskChange = true;
					logInfo("$$   currentRiskChange $$$$ ",formObject.getValue(controlName).toString());
				}*/
				logInfo("$$   currentRiskChange $$$$ ",formObject.getValue(controlName).toString());
				if("Neutral".equalsIgnoreCase(formObject.getValue(CPD_RISK_CURRENT_RSK_BANK).toString())){
					formObject.setValue(CPD_CHKBOX_COMPL_AP, FALSE);
					formObject.setStyle(CPD_CHKBOX_COMPL_AP, DISABLE,TRUE);
				} else if("false".equalsIgnoreCase(formObject.getValue(CPD_CHKBOX_COMPL_AP).toString()) && 
						!("Neutral".equalsIgnoreCase(formObject.getValue(CPD_RISK_CURRENT_RSK_BANK).toString()) 
								|| formObject.getValue(SANCT_RISK_FCR_RSK).toString().equalsIgnoreCase
								(formObject.getValue(CPD_RISK_CURRENT_RSK_BANK).toString()))){
					//JOptionPane.showMessageDialog(_objApplet,"Please check below check box to change Current Risk");
					sendMessageValuesList("","Please check below check box to change Current Risk");//Rakshita
					formObject.setValue(CPD_CHKBOX_COMPL_AP, TRUE);
					formObject.setStyle(CPD_CHKBOX_COMPL_AP, DISABLE,TRUE);
				}
				if(!formObject.getValue(controlName).toString().equalsIgnoreCase("")){
					if(formObject.getValue(CPD__RISK_CURRENT_RSK_SYSTEM).toString().equalsIgnoreCase("Neutral Risk")){
						if(formObject.getValue(CPD_RISK_CURRENT_RSK_BANK).toString().equalsIgnoreCase("")){
							sendMessageValuesList(CPD_RISK_CURRENT_RSK_BANK,CA0144);
							formObject.setValue(CPD_RISK_CURRENT_RSK_BANK,"");
							//return ;
						}
					}else if(formObject.getValue(CPD__RISK_CURRENT_RSK_SYSTEM).toString().equalsIgnoreCase("Medium Risk")){  //Changed 27022023
						if(formObject.getValue(CPD_RISK_CURRENT_RSK_BANK).toString().equalsIgnoreCase("")){
							sendMessageValuesList(CPD_RISK_CURRENT_RSK_BANK,CA0144);
							formObject.setValue(CPD_RISK_CURRENT_RSK_BANK,"");
							//return ;
						}
					}else if(formObject.getValue(CPD__RISK_CURRENT_RSK_SYSTEM).toString().equalsIgnoreCase("Increased Risk")){
						if((formObject.getValue(CPD_RISK_CURRENT_RSK_BANK).toString().equalsIgnoreCase("Neutral"))
								|| (formObject.getValue(CPD_RISK_CURRENT_RSK_BANK).toString().equalsIgnoreCase("Medium Risk")))		//Changed 27022023
						{
							sendMessageValuesList(CPD_RISK_CURRENT_RSK_BANK,CA0144);
							formObject.setValue(CPD_RISK_CURRENT_RSK_BANK,"");
							//return ;
						}
					}else if(formObject.getValue(CPD__RISK_CURRENT_RSK_SYSTEM).toString().equalsIgnoreCase("UAE-PEP")){
						if((formObject.getValue(CPD_RISK_CURRENT_RSK_BANK).toString().equalsIgnoreCase("Neutral"))||
								(formObject.getValue(CPD_RISK_CURRENT_RSK_BANK).toString().equalsIgnoreCase("Medium Risk"))	||		//Changed 27022023
								(formObject.getValue(CPD_RISK_CURRENT_RSK_BANK).toString().equalsIgnoreCase("Increased Risk"))){
							sendMessageValuesList(CPD_RISK_CURRENT_RSK_BANK,CA0144);
							formObject.setValue(CPD_RISK_CURRENT_RSK_BANK,"");
							//return ;
						}
					}else if(formObject.getValue(CPD__RISK_CURRENT_RSK_SYSTEM).toString().equalsIgnoreCase("Unacceptable Risk")){
						if((formObject.getValue(CPD_RISK_CURRENT_RSK_BANK).toString().equalsIgnoreCase("Neutral"))||
								(formObject.getValue(CPD_RISK_CURRENT_RSK_BANK).toString().equalsIgnoreCase("Medium Risk")) ||			//Changed 27022023
								(formObject.getValue(CPD_RISK_CURRENT_RSK_BANK).toString().equalsIgnoreCase("Increased Risk"))||
								(formObject.getValue(CPD_RISK_CURRENT_RSK_BANK).toString().equalsIgnoreCase("UAE-PEP")))
						{
							sendMessageValuesList(CPD_RISK_CURRENT_RSK_BANK,CA0144);
							formObject.setValue(CPD_RISK_CURRENT_RSK_BANK,"");					
							//return ;
						}
					}
					int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1; 
					//String sAccRelation  = objChkRepeater.getValue(iSelectedRow,"AO_ACC_RELATION.BANK_RELATION");
					//String sAccRelation  =formObject.getTableCellValue(ACC_RELATION, fieldToFocus,);//Rakshita doubt
					String sPrevRisk ="";
					String sPrevRiskDate ="";
					String sComplainceApproval ="";
					String sCustID = formObject.getValue(CPD_RISK_CID).toString();

					//						if(!sCustID.equalsIgnoreCase("") && sAccRelation.equalsIgnoreCase("Existing")){
					//							String sOutput=formObject.getDataFromDB("SELECT CURRENT_RISK_SYSTEM,TO_CHAR(PREV_RISK_DATE,'"+NGFUserResourceMgr.getResourceString_val("DATEFORMAT")+"') PREV_RISK_DATE FROM USR_0_RISK_ASSESSMENT_DATA_CPD WHERE CUST_ID='"+sCustID+"' AND PREV_RISK_DATE =(SELECT MAX(PREV_RISK_DATE) FROM USR_0_RISK_ASSESSMENT_DATA_CPD WHERE CUST_ID='"+sCustID+"') AND ROWNUM=1");
					//							//sPrevRisk = getTagValues(sOutput,"CURRENT_RISK_SYSTEM");
					//							//sPrevRiskDate = getTagValues(sOutput,"PREV_RISK_DATE");
					//							sPrevRisk = sOutput.get(0).get(0);
					//							sPrevRiskDate=sOutput.get(0).get(1);
					//						} //rakshita doubt
					if(!sCustID.equalsIgnoreCase("")) {
						String sQuery = "SELECT CURRENT_RISK_SYSTEM,TO_CHAR(PREV_RISK_DATE,'dd/mm/yyyy') "
								+ "PREV_RISK_DATE FROM USR_0_RISK_ASSESSMENT_DATA_CPD WHERE CUST_ID='"
								+sCustID+"' AND PREV_RISK_DATE =(SELECT MAX(PREV_RISK_DATE) FROM "
								+ "USR_0_RISK_ASSESSMENT_DATA_CPD WHERE CUST_ID='"+sCustID+"') AND ROWNUM=1";
						List<List<String>> list = formObject.getDataFromDB(sQuery);
						if(list.size() > 0) {
							sPrevRisk = list.get(0).get(0);
							sPrevRiskDate = list.get(0).get(1);
						}
					}
					if(formObject.getValue(controlName).toString().equalsIgnoreCase("Neutral")){
						sComplainceApproval="No";
					}else if(formObject.getValue(controlName).toString().equalsIgnoreCase("Medium Risk")){   //changed 27022023
						sComplainceApproval="No";
					}else if(formObject.getValue(controlName).toString().equalsIgnoreCase(sPrevRisk) && 
							!sPrevRiskDate.equalsIgnoreCase("")){
						sComplainceApproval="No";
					}else{
						sComplainceApproval="Yes";
					}
					formObject.setValue(CPD_RISK_COMPL_APP_REQ, sComplainceApproval);
				}else{
					logInfo(" ","before calling set_Values_From_Usr_0_Risk_Data in cpd maker 4 step cust_curr_risk_bank is null");
					set_Values_From_Usr_0_Risk_Data();
				}
			} else if (NOM_REQ.equalsIgnoreCase(controlName)){
				String val = formObject.getValue(NOM_REQ).toString();
					if("Yes".equalsIgnoreCase(val))	
					{			
						formObject.setStyle(EXISTING_NOM_PRSN,DISABLE,FALSE);
						formObject.setValue(EXISTING_NOM_PRSN,"");				
						Frame48_CPD_ENable();
						manageNomineeDetails(val);
					}
					else
					{
						formObject.setStyle(EXISTING_NOM_PRSN,DISABLE,TRUE);
						formObject.setValue(EXISTING_NOM_PRSN,"");				
						Frame48_CPD_Disable();
						manageNomineeDetails(val);
					}
				
			} else if(EXISTING_NOM_PRSN.equalsIgnoreCase(controlName))
			{
				String val = formObject.getValue(EXISTING_NOM_PRSN).toString();
				if("Yes".equalsIgnoreCase(val))	
				{
					String cust_id = formObject.getTableCellValue(ACC_RELATION, 0, 2).toString();
					String sql="select nom_name,nom_po_box,nom_add1||nom_add2,nom_land,nom_city,nom_state,nom_others"
							+ ",nom_cntry,nom_fax,nom_zip,nom_email,nom_pref_lang,nom_phone,cust_id,"
							+ "nom_id,nom_mob from usr_0_nom_mast where cust_id ='"+cust_id+"'";
					logInfo("","ok"+cust_id+"---------"+sql);
					List<List<String>> sOutput = formObject.getDataFromDB(sql);
					logInfo("","sOutput "+sOutput);
					loadListView(sOutput, "NAME,POBOX,ADDRESS,LANDMARK,CITY,STATE,OTHERS,COUNTRY,FAX,"
							+ "ZIPCODE,EMAIL,PREFLANG,PHONE,CID,NOMID,MOBNO", DELIVERY_PREFERENCE);
					logInfo("","ok"+cust_id+"---------"+sql);
					logInfo("","************ Count ************** "+getGridCount(DELIVERY_PREFERENCE));
					if(getGridCount(DELIVERY_PREFERENCE)==0)
					{
						sendMessageValuesList(EXISTING_NOM_PRSN, "There Is No Existing Nominee For This Customer");
						formObject.setValue(EXISTING_NOM_PRSN, "");
						getReturnMessage(false);
					}
					//formObject.setNGEnable("Frame48",true);
					Frame48_CPD_ENable();
				}else
				{

					formObject.clearTable(DELIVERY_PREFERENCE);
				}
			} else if("confirmAppRisk".equalsIgnoreCase(controlName)) {
				int sCustNo = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 1;
				formObject.setValue(NIG_CPDMAKER,"YES");
				String updatequery = "update USR_0_CUST_TXN set NIGEXCEPTIONCPDMAKER='YES' "
						+ "Where WI_NAME='"+formObject.getValue("AO_WI_Name")+"' AND CUST_SNO ='"+sCustNo+"'";
				formObject.saveDataInDB(updatequery);
			} else if(controlName.equalsIgnoreCase(PD_ANY_CHNG_CUST_INFO)) {
				logInfo("check control","PD_ANY_CHNG_CUST_INFO : start "+controlName);
				String sAccRelation= "";
				if(!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
					int fieldToFocus=Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
					sAccRelation= formObject.getTableCellValue(ACC_RELATION,fieldToFocus,9);//Acc relation.accrelatn
				}
				manageCustomerDetailChange(formObject.getValue(controlName).toString());	
				manageFATCAFieldsEnable(formObject.getValue(PD_ANY_CHNG_CUST_INFO).toString(),sAccRelation);
				String segment = formObject.getValue(PD_CUSTSEGMENT).toString();
				getPromoCode(segment);
				logInfo("check control","PD_ANY_CHNG_CUST_INFO : enda "+controlName);
			} else if(controlName.equalsIgnoreCase("rowSelect")) {
				//			formObject.setValue(SELECTED_ROW_INDEX, (Integer.parseInt(formObject.getValue(controlName).toString())-1)+"");
				String sBankRelation = formObject.getTableCellValue(ACC_RELATION,0,7);
				gotFocusCustInfoDataCPD();
				populateMakaniData();
				logInfo("onChangeEventCPDMakerFourStep","ON FOCUS 4 STEP RMCODE");
				setRMCode();
				try {
					populatePassAndVisaFields();
				}  catch (Exception e) {
					logError("",e);
				}
				if(TRUE.equalsIgnoreCase(formObject.getValue(CHECKBOX_VISA_STATUS_MANUAL).toString())) {
					logInfo("onChangeEventCPDMakerFourStep","INSIDE VISA MANUAL CPD MAKER");
					formObject.setStyle(MANUAL_VISASTATUS,DISABLE,FALSE);
				}
				if(formObject.getValue(DATA_ENTRY_MODE).toString().equalsIgnoreCase("Quick Data Entry") 
						&& formObject.getValue(RA_BUILDINGNAME).toString().equalsIgnoreCase("")
						&& formObject.getValue(RA_BUILDINGNAME).toString().equalsIgnoreCase("") 
						&& sBankRelation.equalsIgnoreCase("Existing")) {
					populateQDEModeOtherData(formObject.getTableCellValue(ACC_RELATION,0,0));//cid
				}
				if(formObject.getValue(RELIGION).toString().equalsIgnoreCase("")) {
					formObject.setValue(RELIGION,"Others");
				}
				if(formObject.getValue(MARITAL_STATUS).toString().equalsIgnoreCase("")) {
					formObject.setValue(MARITAL_STATUS,"Single");
				}
				if(formObject.getValue(GI_IS_CUST_VIP).toString().equalsIgnoreCase("")) {
					formObject.setValue(GI_IS_CUST_VIP, "No");
				}
				if(formObject.getValue(RA_IS_CUST_DEALNG_ARMAMNT).toString().equalsIgnoreCase("")) {
					formObject.setValue(RA_IS_CUST_DEALNG_ARMAMNT, "No");//Combo37
				}
				if(formObject.getValue(RA_IS_CUST_DEALNG_HAWALA).toString().equalsIgnoreCase("")) {
					formObject.setValue(RA_IS_CUST_DEALNG_HAWALA, "No");//Combo39
				}
				if(formObject.getValue(RA_PRPSE_TAX_EVSN).toString().equalsIgnoreCase("")) {
					formObject.setValue(RA_PRPSE_TAX_EVSN, "No");//Combo38
				}
				if(formObject.getValue(RA_IS_CUST_PEP).toString().equalsIgnoreCase("")) {
					formObject.setValue(RA_IS_CUST_PEP, "No");//Combo36
				}
			} else if(controlName.equalsIgnoreCase(COMBODOC) ) {
				logInfo("COMBODOC QW", "INSIDE, sControlName: "+controlName+", "+ "sControlValue: "+controlValue);
				if(controlValue.equalsIgnoreCase("W9")) {
					formObject.setStyle(FAT_SSN,DISABLE,FALSE);
					formObject.setStyle(DATEPICKERW8,DISABLE,FALSE);
					formObject.setValue(DATEPICKERW8,"");
				} else if(controlValue.equalsIgnoreCase("W8BEN")) {
					Calendar calendar = Calendar.getInstance();
					SimpleDateFormat simpledateformat = new SimpleDateFormat(DATEFORMAT);
					String scurrentDate = simpledateformat.format(calendar.getTime());
					formObject.setValue(FAT_SSN,"");
					formObject.setStyle(FAT_SSN,DISABLE,TRUE);
					formObject.setStyle(DATEPICKERW8,DISABLE,FALSE);
					formObject.setValue(DATEPICKERW8, scurrentDate);
				} else {
					formObject.setValue(FAT_SSN,"");
					formObject.setValue(DATEPICKERW8,"");
					formObject.setStyle(FAT_SSN,DISABLE,TRUE);
					formObject.setStyle(DATEPICKERW8,DISABLE,TRUE);
				}
				String sFinalCountryOfBirth =getFinalDataComparison(CHECKBOX_COB_FCR,CHECKBOX_COB_EIDA,CHECKBOX_COB_MANUAL
						,FCR_COUNTRYBIRTH,EIDA_COUNTRYBIRTH,MANUAL_COUNTRYBIRTH);
				logInfo("",sFinalCountryOfBirth+"combodoc sFinalCountryOfBirth");
				if(sFinalCountryOfBirth.equalsIgnoreCase("UNITED STATES")) {
					List<List<String>> sOutput=formObject.getDataFromDB("SELECT FATCA_CLASSIFICATION FROM USR_0_DOC_MASTER "
							+ "WHERE DOC_NAME ='"+controlValue+"'");
					logInfo("COMBODOC CHANGE","sOuput----"+sOutput);
					formObject.setValue(FAT_CUST_CLASSIFICATION,sOutput.get(0).get(0));
				} else {
					if(formObject.getValue(INDICIACOMBO).toString().equalsIgnoreCase("No")) {
						formObject.setValue(FAT_CUST_CLASSIFICATION,"NON-US PERSON");
					} else if (formObject.getValue(INDICIACOMBO).toString().equalsIgnoreCase("Yes")) {
						formObject.setValue(FAT_CUST_CLASSIFICATION,"US PERSON");
					}
				}
			} else if(controlName.equalsIgnoreCase(FAT_US_PERSON) ) {	
				if(controlValue.equalsIgnoreCase("Yes")) {
					formObject.setValue(FAT_LIABLE_TO_PAY_TAX,"YES");
					formObject.setStyle(FAT_LIABLE_TO_PAY_TAX,DISABLE,TRUE);
				} else {
					formObject.setValue(FAT_LIABLE_TO_PAY_TAX,"");
					if(formObject.getValue("CHECK95").toString().equalsIgnoreCase("true")) {
						formObject.setStyle(FAT_LIABLE_TO_PAY_TAX,DISABLE,FALSE);
					}
				}
			} else if((controlName.equalsIgnoreCase(FCR_COUNTRYBIRTH)) || (controlName.equalsIgnoreCase(EIDA_COUNTRYBIRTH))||(controlName.equalsIgnoreCase(MANUAL_COUNTRYBIRTH))) {
				logInfo("","inside change event of COMBO50 ");
				String sFinalCountryOfBirth =getFinalDataComparison("CHECK117","CHECK119","CHECK122",FCR_COUNTRYBIRTH,EIDA_COUNTRYBIRTH,MANUAL_COUNTRYBIRTH); // updated by Shivam Arora 01/10/2018, Incorrect Control name was passed  earlier
				logInfo("",sFinalCountryOfBirth+"sFinalCountryOfBirth inside country click");
				if(sFinalCountryOfBirth.equalsIgnoreCase("UNITED STATES")) {
					logInfo("",sFinalCountryOfBirth+"united insoide inside country click firse");
					formObject.setStyle(COMBODOC,DISABLE,FALSE);
					formObject.clearCombo(COMBODOC);
					formObject.addItemInCombo(COMBODOC,"");
					formObject.addItemInCombo(COMBODOC,"W8BEN");
					formObject.addItemInCombo(COMBODOC,"W9");
	
				} else {
					logInfo("",sFinalCountryOfBirth+"else inside country click firse");
					formObject.setStyle(COMBODOC,DISABLE,FALSE);
					formObject.clearCombo(COMBODOC);
					formObject.setValue(FAT_CUST_CLASSIFICATION, "");
					formObject.addItemInCombo(COMBODOC,"");
					formObject.addItemInCombo(COMBODOC,"W8BEN");
					formObject.addItemInCombo(COMBODOC,"W9");
					formObject.addItemInCombo(COMBODOC,"NA");
				}
			} else if(controlName.equalsIgnoreCase(NEW_CUST_SEGMENT)) {
				if(formObject.getValue(NEW_CUST_SEGMENT).toString().equalsIgnoreCase("")) {				
					formObject.setValue(IS_SALARY_TRANSFER_CAT_CHANGE,"False");
					formObject.setValue(IS_MORTAGAGE_CAT_CHANGE,"False");
					formObject.setValue(IS_INSURANCE_CAT_CHANGE,"False");
					formObject.setValue(IS_TRB_CAT_CHANGE, "False");
					formObject.setValue(IS_OTHERS_CAT_CHANGE, "False");
					formObject.setValue(IS_VVIP, "False");
					formObject.setValue(IS_PREVILEGE_TP_CAT_CHANGE, "False");
					formObject.setValue(IS_ENTERTAINMENT_CAT_CHANGE, "False");
					formObject.setValue(IS_SHOPPING_CAT_CHANGE, "False");
					formObject.setValue(IS_SPORT_CAT_CHANGE, "False");
					formObject.setValue(IS_TRAVEL_CAT_CHANGE, "False");
					formObject.setValue(IS_EXCELLENCY_TP_CAT_CHANGE, "False");
					formObject.setStyle(IS_SALARY_TRANSFER_CAT_CHANGE,DISABLE,TRUE);
					formObject.setStyle(IS_MORTAGAGE_CAT_CHANGE,DISABLE, TRUE);
					formObject.setStyle(IS_INSURANCE_CAT_CHANGE,DISABLE, TRUE);
					formObject.setStyle(IS_TRB_CAT_CHANGE,DISABLE, TRUE);
					formObject.setStyle(IS_OTHERS_CAT_CHANGE,DISABLE, TRUE);
					formObject.setStyle(IS_VVIP,DISABLE, TRUE);
					formObject.setStyle(IS_PREVILEGE_TP_CAT_CHANGE,DISABLE, TRUE);
					formObject.setStyle(IS_ENTERTAINMENT_CAT_CHANGE,DISABLE, TRUE);
					formObject.setStyle(IS_SHOPPING_CAT_CHANGE,DISABLE, TRUE);
					formObject.setStyle(IS_SPORT_CAT_CHANGE,DISABLE, TRUE);
					formObject.setStyle(IS_TRAVEL_CAT_CHANGE,DISABLE,TRUE);
					formObject.setStyle(IS_EXCELLENCY_TP_CAT_CHANGE,DISABLE, TRUE);
					formObject.setValue(IS_CAT_BENEFIT_OTHER, "False");
					formObject.setValue(CAT_BENEFIT_OTHER, "");
					formObject.setStyle(IS_CAT_BENEFIT_OTHER,DISABLE,TRUE);
					formObject.setStyle(CAT_BENEFIT_OTHER,DISABLE, TRUE);
					formObject.setStyle(IS_SALARY_TRANSFER_CAT_CHANGE,VISIBLE,TRUE);
					formObject.setStyle(IS_MORTAGAGE_CAT_CHANGE,VISIBLE, TRUE);
					formObject.setStyle(IS_INSURANCE_CAT_CHANGE,VISIBLE, TRUE);
					formObject.setStyle(IS_TRB_CAT_CHANGE,VISIBLE, TRUE);
					formObject.setStyle(IS_OTHERS_CAT_CHANGE,VISIBLE, TRUE);
					formObject.setStyle(IS_VVIP,VISIBLE, TRUE);
					formObject.setStyle(IS_PREVILEGE_TP_CAT_CHANGE,VISIBLE, TRUE);
					formObject.setStyle(IS_ENTERTAINMENT_CAT_CHANGE,VISIBLE, TRUE);
					formObject.setStyle(IS_SHOPPING_CAT_CHANGE,VISIBLE, TRUE);
					formObject.setStyle(IS_SPORT_CAT_CHANGE,VISIBLE, TRUE);
					formObject.setStyle(IS_TRAVEL_CAT_CHANGE,VISIBLE,TRUE);
					formObject.setStyle(IS_EXCELLENCY_TP_CAT_CHANGE,VISIBLE, TRUE);
					formObject.setStyle(IS_CAT_BENEFIT_OTHER,VISIBLE,TRUE);
					formObject.setStyle(CAT_BENEFIT_OTHER,VISIBLE, TRUE);
				} else {
					formObject.setValue(IS_SALARY_TRANSFER_CAT_CHANGE,"False");
					formObject.setValue(IS_MORTAGAGE_CAT_CHANGE, "False");
					formObject.setValue(IS_INSURANCE_CAT_CHANGE, "False");
					formObject.setValue(IS_TRB_CAT_CHANGE, "False");
					formObject.setValue(IS_OTHERS_CAT_CHANGE, "False");
					formObject.setValue(IS_VVIP, "False");
					formObject.setValue(IS_PREVILEGE_TP_CAT_CHANGE, "False");
					formObject.setValue(IS_ENTERTAINMENT_CAT_CHANGE, "False");
					formObject.setValue(IS_SHOPPING_CAT_CHANGE, "False");
					formObject.setValue(IS_SPORT_CAT_CHANGE, "False");
					formObject.setValue(IS_TRAVEL_CAT_CHANGE, "False");
					formObject.setValue(IS_EXCELLENCY_TP_CAT_CHANGE, "False");
					formObject.setValue(IS_CAT_BENEFIT_OTHER, "False");
					formObject.setValue(CAT_BENEFIT_OTHER, "");
					formObject.setStyle(IS_SALARY_TRANSFER_CAT_CHANGE,DISABLE,FALSE);
					formObject.setStyle(IS_MORTAGAGE_CAT_CHANGE,DISABLE, FALSE);
					formObject.setStyle(IS_INSURANCE_CAT_CHANGE,DISABLE, FALSE);
					formObject.setStyle(IS_TRB_CAT_CHANGE,DISABLE, FALSE);
					formObject.setStyle(IS_OTHERS_CAT_CHANGE,DISABLE, FALSE);
					formObject.setStyle(IS_VVIP,DISABLE, FALSE);
					formObject.setStyle(IS_PREVILEGE_TP_CAT_CHANGE,DISABLE, FALSE);
					formObject.setStyle(IS_ENTERTAINMENT_CAT_CHANGE,DISABLE, FALSE);
					formObject.setStyle(IS_SHOPPING_CAT_CHANGE,DISABLE, FALSE);
					formObject.setStyle(IS_SPORT_CAT_CHANGE,DISABLE, FALSE);
					formObject.setStyle(IS_TRAVEL_CAT_CHANGE,DISABLE,FALSE);
					formObject.setStyle(IS_EXCELLENCY_TP_CAT_CHANGE,DISABLE, FALSE);
					formObject.setStyle(IS_CAT_BENEFIT_OTHER,DISABLE,FALSE);
					formObject.setStyle(CAT_BENEFIT_OTHER,DISABLE, FALSE);
				}
				manageCategoryChangeSection();
				if(!formObject.getValue(NEW_CUST_SEGMENT).toString().equalsIgnoreCase("")) {
					formObject.setStyle(IS_CAT_BENEFIT_OTHER,DISABLE,FALSE);		
					formObject.setValue(IS_CAT_BENEFIT_OTHER,"False");
					formObject.setStyle(CAT_BENEFIT_OTHER,DISABLE,TRUE);
					formObject.setValue(CAT_BENEFIT_OTHER,"");
				} else {
					formObject.clearCombo(PROMO_CODE_CAT_CHANGE);
					formObject.addItemInCombo(PROMO_CODE_CAT_CHANGE,"");
					//formObject.setNGListIndex(PROMO_CODE_CAT_CHANGE, 0);		
					formObject.clearCombo(EXCELLENCY_CENTER_CC);
					formObject.addItemInCombo(EXCELLENCY_CENTER_CC,"");
					//formObject.setNGListIndex("EXCELLENCY_CENTER_CC", 0);
				}
				clearFBData();	//Family Banking
			} else if(controlName.equalsIgnoreCase(ED_EMP_TYPE) ) {	
				if(controlValue.equalsIgnoreCase("ADCB")) {
					formObject.setStyle(ED_SET_FLG,DISABLE,FALSE);
					formObject.setValue(ED_SET_FLG, "Yes");
				} else {
					formObject.setStyle(ED_SET_FLG,DISABLE,TRUE);
					formObject.setValue(ED_SET_FLG, "No");
				}
			}  else if(controlName.equalsIgnoreCase(CK_PER_DET) 
					&& formObject.getValue(CK_PER_DET).toString().equalsIgnoreCase("true")) {
				Frame18_CPD_ENable();
				formObject.setStyle(CK_PER_DET,DISABLE,FALSE);
				formObject.setStyle(PD_SHORTNAME,DISABLE,FALSE);
				if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only")) {
					formObject.setStyle(PD_CUSTSEGMENT,DISABLE,TRUE);
				}					
				int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
				String sBankRelation = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 7);
				String sAccRelation = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 9);
				if(sBankRelation.equalsIgnoreCase("Existing")) {
					formObject.setStyle(PD_CUSTSEGMENT,DISABLE,TRUE);
				}
				if(sAccRelation.equalsIgnoreCase("Minor")) {
					formObject.setStyle("MASK3",DISABLE,FALSE);
				} else{
					formObject.setStyle("MASK3",DISABLE,TRUE);
				}
				if(! returnVisaStatus().equalsIgnoreCase("Residency Visa")) {
					formObject.setStyle(DRP_RESEIDA,DISABLE,TRUE);
				} else {
					formObject.setStyle(DRP_RESEIDA,DISABLE,FALSE);
				}			
			} else if(controlName.equalsIgnoreCase(CK_PER_DET) 
					&& formObject.getValue(CK_PER_DET).toString().equalsIgnoreCase("false")) {
				Frame18_CPD_Disable();
				formObject.setStyle(CK_PER_DET,DISABLE,FALSE);
				formObject.setStyle(DRP_RESEIDA,DISABLE,TRUE);
			} else if(controlName.equalsIgnoreCase(CHK_CONTACT_DET) && formObject.getValue(CHK_CONTACT_DET).toString().equalsIgnoreCase("true")) {
				Frame20_CPD_ENable();
				formObject.setStyle(CHK_CONTACT_DET,DISABLE,FALSE);
			} else if(controlName.equalsIgnoreCase(CHK_CONTACT_DET) && formObject.getValue(CHK_CONTACT_DET).toString().equalsIgnoreCase("false")) {
				Frame20_CPD_Disable();
				formObject.setStyle(CHK_CONTACT_DET,DISABLE,FALSE);
			} else if(controlName.equalsIgnoreCase(CHK_PASSPORT_DET) && formObject.getValue(CHK_PASSPORT_DET).toString().equalsIgnoreCase("true")) {
				Frame21_CPD_ENable();
				formObject.setStyle(CHK_PASSPORT_DET,DISABLE,FALSE);
			} else if(controlName.equalsIgnoreCase(CHK_PASSPORT_DET) && formObject.getValue(CHK_PASSPORT_DET).toString().equalsIgnoreCase("false")) {
				Frame21_CPD_Disable();
				formObject.setStyle(CHK_PASSPORT_DET,DISABLE,FALSE);
			} else if(controlName.equalsIgnoreCase(CHK_INTERNAL_SEC)) {
				if(formObject.getValue(CHK_INTERNAL_SEC).toString().equalsIgnoreCase("true")) {
					Frame22_CPD_ENable();
					formObject.setStyle(CHK_INTERNAL_SEC,DISABLE,FALSE);
					formObject.setStyle("TEXT55",DISABLE,TRUE);
				} else {
					Frame22_CPD_Disable();
					formObject.setStyle(CHK_INTERNAL_SEC,DISABLE,FALSE);
				}
			} else if(controlName.equalsIgnoreCase(CHK_GEN_INFO) && formObject.getValue(CHK_GEN_INFO).toString().equalsIgnoreCase("true")) {
				Frame28_CPD_ENable();
				formObject.setStyle(CHK_GEN_INFO,DISABLE,FALSE);
			} else if(controlName.equalsIgnoreCase(CHK_GEN_INFO) && formObject.getValue(CHK_GEN_INFO).toString().equalsIgnoreCase("false")) {
				Frame28_CPD_Disable();
				formObject.setStyle(CHK_GEN_INFO,DISABLE,FALSE);
			} else if(controlName.equalsIgnoreCase(CHK_EMP_DETAIL) && formObject.getValue(CHK_EMP_DETAIL).toString().equalsIgnoreCase("true")){
				Frame27_CPD_ENable();
				formObject.setStyle(CHK_EMP_DETAIL,DISABLE,FALSE);
					if(formObject.getValue(PROFESION).toString().equalsIgnoreCase("Others") || formObject.getValue(PROFESION).toString().equalsIgnoreCase("")) {
						formObject.setStyle(ED_OTHER, DISABLE, FALSE);
					} else {
						formObject.setStyle(ED_OTHER, DISABLE, TRUE);
					}
					if(formObject.getValue(EMPNAME).toString().equalsIgnoreCase("Others") || formObject.getValue(EMPNAME).toString().equalsIgnoreCase("")) {
						formObject.setStyle(ED_EMPNAME, DISABLE, FALSE);//02052023 by Ameena //CompanyName issue
					} else {
						formObject.setStyle(ED_EMPNAME, DISABLE, TRUE);
					}
			} else if(controlName.equalsIgnoreCase(CHK_EMP_DETAIL) && formObject.getValue(CHK_EMP_DETAIL).toString().equalsIgnoreCase("false")) {
				Frame27_CPD_Disable();
				formObject.setStyle(CHK_EMP_DETAIL,DISABLE,FALSE);
			} else if(controlName.equalsIgnoreCase(CHK_FUNDING_INFO) && formObject.getValue(CHK_FUNDING_INFO).toString().equalsIgnoreCase("true")) {
				Frame30_CPD_ENable();
				formObject.setStyle(CHK_FUNDING_INFO,DISABLE,FALSE);
			} else if(controlName.equalsIgnoreCase(CHK_FUNDING_INFO) && formObject.getValue(CHK_FUNDING_INFO).toString().equalsIgnoreCase("false")){
				Frame30_CPD_Disable();
				formObject.setStyle(CHK_FUNDING_INFO,DISABLE,FALSE);
			} else if(controlName.equalsIgnoreCase(CHK_RISK_ASS) 
					&& formObject.getValue(CHK_RISK_ASS).toString().equalsIgnoreCase("true")){
				riskAssessmentSectionEnable();    //Frame25_CPD_ENable();
				formObject.setStyle(CHK_RISK_ASS,DISABLE,FALSE);
				//disableEidaField();
			} else if(controlName.equalsIgnoreCase(CHK_RISK_ASS) 
					&& formObject.getValue(CHK_RISK_ASS).toString().equalsIgnoreCase("false")) {
				Frame25_CPD_Disable();
				formObject.setStyle(CHK_RISK_ASS,DISABLE,FALSE);
				//disableEidaField();
			} else if(controlName.equalsIgnoreCase("CHECK95") && controlValue.equalsIgnoreCase("true")) {
				FrameFATCA_CPD_Enable();
				formObject.setStyle("CHECK95",DISABLE,FALSE);
				formObject.setStyle(COMBODOC,DISABLE,FALSE);
			} else if(controlName.equalsIgnoreCase("CHECK95") && controlValue.equalsIgnoreCase("false")) {
				frameFatcaCpdDisable();
				formObject.setStyle("CHECK95",DISABLE,FALSE);
				formObject.setStyle(COMBODOC,DISABLE,FALSE);
			} else if(controlName.equalsIgnoreCase(CHK_EDD) && formObject.getValue(CHK_EDD).toString().equalsIgnoreCase("true")) {
				Frame31_CPD_ENable();
				formObject.setStyle(CHK_EDD,DISABLE,FALSE);
			} else if(controlName.equalsIgnoreCase(CHK_EDD) && formObject.getValue(CHK_EDD).toString().equalsIgnoreCase("false")) {
				Frame31_CPD_Disable();
				formObject.setStyle(CHK_EDD,DISABLE,FALSE);
			} else if(controlName.equalsIgnoreCase(CHK_BANKING_RELATION) && formObject.getValue(CHK_BANKING_RELATION).toString().equalsIgnoreCase("true")) {
				Frame32_CPD_ENable();
				formObject.setStyle(CHK_BANKING_RELATION,DISABLE,FALSE);
			} else if(controlName.equalsIgnoreCase(CHK_BANKING_RELATION) && formObject.getValue(CHK_BANKING_RELATION).toString().equalsIgnoreCase("false")) {
				Frame32_CPD_Disable();
				formObject.setStyle(CHK_BANKING_RELATION,DISABLE,FALSE);
			} else if(controlName.equalsIgnoreCase(ACC_INFO_PG)){
				if(formObject.getValue(ACC_INFO_PG).toString().equalsIgnoreCase("")){
					formObject.setValue(GROUP_TYPE,"");
					formObject.setValue(CARD_TYPE,"");
					formObject.setValue(EMBOSS_NAME,"");
					formObject.setValue(NEW_LINK,"");
					formObject.setValue(EXISTING_CARD_NO,"");
				} else if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("New Account With Category Change") 
						&& formObject.getValue(NEW_CUST_SEGMENT).toString().equalsIgnoreCase("")) {
					sendMessageValuesList(NEW_CUST_SEGMENT,"Please select New Category.");
				} else{
					String[] sSelectedProduct = formObject.getValue(ACC_INFO_PG).toString().split("_");
					String sQuery  = "SELECT COD_PROD_TYPE FROM USR_0_PRODUCT_MASTER WHERE PRODUCT_CODE ='"+sSelectedProduct[1]+"'";
					logInfo("ACC_INFO_PG","sQuery: "+sQuery);
					List<List<String>> sOutput = formObject.getDataFromDB(sQuery);
					logInfo("ACC_INFO_PG","sOutput: "+sOutput.toString());
					String sCategory = "";
					if(sOutput.size()>0) {
						sCategory = sOutput.get(0).get(0);
					}
					if(!sCategory.equalsIgnoreCase("")){
						if(sCategory.equalsIgnoreCase("I")){
							formObject.setValue(GROUP_TYPE,"Islamic");
							formObject.setStyle(GROUP_TYPE, DISABLE, TRUE);
						}else{
							sQuery  = "SELECT SUB_PRODUCT_TYPE FROM USR_0_PRODUCT_TYPE_MASTER"
									+ " WHERE PRODUCT_CODE ='"+sSelectedProduct[1]+"'";
							sOutput=formObject.getDataFromDB(sQuery);
							logInfo("",sOutput.toString());
							sCategory = sOutput.get(0).get(0);
							if(!sCategory.equalsIgnoreCase("")){
								if(sCategory.equalsIgnoreCase("Etihad")){
									formObject.setValue(GROUP_TYPE,"Etihad");
									formObject.setStyle(GROUP_TYPE, DISABLE, TRUE);
								}
								else{
									formObject.setValue(GROUP_TYPE,"");
									formObject.setStyle(GROUP_TYPE, DISABLE, FALSE);
								}
							}
						}
					}
				}
				if(!formObject.getValue(GROUP_TYPE).toString().isEmpty()) {
					String sCustID = getPrimaryCustomerID();
					String[] sSelectedProduct = formObject.getValue(ACC_INFO_PG).toString().split("_");
					String sGroup = formObject.getValue(GROUP_TYPE).toString();
					int iRows = getGridCount(QUEUE_DC);
					int iCount = 0;
					List<List<String>> sOutput;
					String sQuery = "";
					String sGroupType = "";
					sQuery = "SELECT COD_PROD_TYPE FROM USR_0_PRODUCT_MASTER WHERE PRODUCT_CODE ='"
							+sSelectedProduct[1]+"'";
					sOutput = formObject.getDataFromDB(sQuery);
					formObject.setValue(NEW_LINK, "");
					String sCategory = "";
					try {
						sCategory = sOutput.get(0).get(0);
					} catch(Exception ex) {
						logError("CHANGE EVENT: GROUP_TYPE", ex);
					}
					if(!sCategory.isEmpty()) {
						if(!sCategory.equalsIgnoreCase("I") 
								&& formObject.getValue(GROUP_TYPE).toString().equalsIgnoreCase("Islamic")) {
							sendMessageValuesList("","Islamic group type is not allowed for conventional product");
							formObject.setValue(GROUP_TYPE, "");
							//return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));							}
						}
						if(sGroup.isEmpty()) {
							formObject.setValue(CARD_TYPE,"");
							formObject.setValue(NEW_LINK,"");
							enableControls(new String[]{CARD_TYPE, NEW_LINK});
							//return getReturnMessage(false);
						}
						for(int i=0; i<iRows; i++) {
							sGroupType = formObject.getTableCellValue(QUEUE_DC, i, 1).toString();
							logInfo("GROUP_TYPE", "sGroupType: "+sGroupType+", sGroup: "+sGroup);
							if(sGroupType.equalsIgnoreCase(sGroup)) {
								iCount = iCount+1;
								logInfo("GROUP_TYPE", "inside loop iCount: "+iCount);
								break;
							}
						}
						logInfo("GROUP_TYPE", "iCount: "+iCount+", sCustID: "+sCustID);
						if(sCustID.isEmpty() && iCount == 0) {
							formObject.setValue(CARD_TYPE,"Primary");
							formObject.setValue(NEW_LINK,"New");
							disableControls(new String[]{CARD_TYPE, NEW_LINK});
						} else if(iCount != 0) {
							formObject.setValue(CARD_TYPE,"Supplementary");
							formObject.setStyle(CARD_TYPE, DISABLE, TRUE);
							formObject.setStyle(NEW_LINK, DISABLE, FALSE);
						} else {
							sQuery = "SELECT COUNT(WI_NAME) as COUNT_WI FROM USR_0_DEBITCARD_EXISTING "
									+ "WHERE WI_NAME ='"+sWorkitemId+"' AND CUST_ID='"+sCustID+"' AND "
									+ "STATUS NOT IN ('2','3','4') AND PRODUCT_GROUP IN (SELECT DISTINCT GROUPID "
									+ "FROM USR_0_CARD_PRODUCT_GROUP  WHERE PRODTYPE = '"+sGroup+"')";
							logInfo("GROUP_TYPE", "sQuery: "+sQuery);
							sOutput = formObject.getDataFromDB(sQuery);
							logInfo("GROUP_TYPE", "sOutput: "+sOutput);
							iCount = Integer.parseInt(sOutput.get(0).get(0));
							if(iCount == 0) {
								formObject.setValue(CARD_TYPE,"Primary");
								formObject.setValue(NEW_LINK,"New");
								disableControls(new String[]{CARD_TYPE, NEW_LINK});
							} else {
								formObject.setValue(CARD_TYPE,"Supplementary");
								formObject.setStyle(CARD_TYPE, DISABLE, TRUE);
								formObject.setStyle(NEW_LINK, DISABLE, FALSE);
							}
						}
					}
				}
			} else if(controlName.equalsIgnoreCase(GROUP_TYPE) && !controlValue.equalsIgnoreCase("")) {
				String sCustID = getPrimaryCustomerID();
				String[] sSelectedProduct = formObject.getValue(ACC_INFO_PG).toString().split("_"); //combo3
				String sGroup = formObject.getValue(GROUP_TYPE).toString(); //frame71
				int iRows = getGridCount(QUEUE_DC);
				int iCount=0;
				List<List<String>> sOutput;
				String sQuery="";
				String sGroupType="";
				sQuery  = "SELECT COD_PROD_TYPE FROM USR_0_PRODUCT_MASTER WHERE PRODUCT_CODE ='"+sSelectedProduct[1]+"'";
				sOutput=formObject.getDataFromDB(sQuery);
				formObject.setValue(NEW_LINK,"");
				String sCategory="";
				try {
					sCategory = sOutput.get(0).get(0);
					logInfo("","sCategory---"+sCategory);
				} catch(Exception ex) {
					logInfo("","sCategory---"+ex.getStackTrace());
				}
				if(!sCategory.equalsIgnoreCase("")) {
					if(!sCategory.equalsIgnoreCase("I") && formObject.getValue(GROUP_TYPE).toString().equalsIgnoreCase("Islamic")) {
						sendMessageValuesList("","Islamic group type is not allowed for conventional product");
						formObject.setValue(GROUP_TYPE,"");
					}
				}
				if(sGroup.equalsIgnoreCase("")) {
					formObject.setValue(CARD_TYPE,"");
					formObject.setValue(NEW_LINK,"");
					formObject.setStyle(CARD_TYPE,DISABLE,FALSE);
					formObject.setStyle(NEW_LINK,DISABLE,FALSE);
				}
				for(int i=0; i<iRows; i++) {
					sGroupType = formObject.getTableCellValue(QUEUE_DC, i, 1);
					if(sGroupType.equalsIgnoreCase(sGroup)){
						iCount=iCount+1;
						break;
					}
				}
				logInfo("GROUP_TYPE", "iCount: "+iCount+", sCustID: "+sCustID);
				if(sCustID.equalsIgnoreCase("") && iCount == 0) {
					formObject.setValue(CARD_TYPE,"Primary");
					formObject.setValue(NEW_LINK,"New");
					formObject.setStyle(CARD_TYPE,DISABLE,TRUE);
					formObject.setStyle(NEW_LINK,DISABLE,FALSE);
				} else if(iCount != 0) {
					formObject.setValue(CARD_TYPE,"Supplementary");
					formObject.setStyle(CARD_TYPE,DISABLE,TRUE);
					formObject.setStyle(NEW_LINK,DISABLE,FALSE);
				} else {
					sQuery = "SELECT COUNT(WI_NAME) as COUNT_WI FROM USR_0_DEBITCARD_EXISTING_CPD WHERE WI_NAME ='"+sWorkitemId+"' AND CUST_ID='"+sCustID+"' AND STATUS NOT IN ('2','3','4') AND PRODUCT_GROUP IN (SELECT DISTINCT GROUPID FROM USR_0_CARD_PRODUCT_GROUP  WHERE PRODTYPE = '"+sGroup+"')";
					logInfo("GROUP_TYPE", "sQuery: "+sQuery);
					sOutput=formObject.getDataFromDB(sQuery);
					logInfo("GROUP_TYPE", "sOutput: "+sOutput);
					iCount = Integer.parseInt(sOutput.get(0).get(0));
					if(iCount==0) {
						formObject.setValue(CARD_TYPE,"Primary");
						formObject.setValue(NEW_LINK,"New");
						formObject.setStyle(CARD_TYPE,DISABLE,TRUE);
						formObject.setStyle(NEW_LINK,DISABLE,TRUE);		
					} else {
						formObject.setValue(CARD_TYPE,"Supplementary");
						formObject.setStyle(CARD_TYPE,DISABLE,TRUE);
						formObject.setStyle(NEW_LINK,DISABLE,FALSE);		
					}
				}
			} else if(controlName.equalsIgnoreCase(GROUP_TYPE)){
				formObject.setValue(CARD_TYPE,"");
				formObject.setValue(NEW_LINK,"");
				formObject.setStyle(CARD_TYPE,DISABLE,FALSE);
				formObject.setStyle(NEW_LINK,DISABLE,FALSE);
			} else if(controlName.equalsIgnoreCase(NEW_LINK)) {
	
				List<List<String>>sOutput;			
				String sQuery="";
				String sGroup="";
				String sGroupType = "";
				if(formObject.getValue(NEW_LINK).toString().equalsIgnoreCase("Link")){
					String sProdCode = "";
					String sNewLink = "";
					String sProd = formObject.getValue(ACC_INFO_PG).toString();
					sGroup = formObject.getValue(GROUP_TYPE).toString();
					//FCR Changes
					//NGRepeater objProdRepeater = formObject.getNGRepeater("ACC_REPEATER");//Rakshita
					//FCR Changes
					int iCount1=Integer.parseInt(sProd.split("_")[2]);
					logInfo("NEW_LINK","sLinkCard-***555*=====--"+iCount1);
					String productCurr ="";
					try{
						productCurr=formObject.getTableCellValue(PRODUCT_QUEUE, iCount1-1,3);//Rakshita
						logInfo("NEW_LINK","sLinkCard12345=--"+productCurr);
					}
					catch(Exception e){
	
					}
	
					sQuery ="SELECT distinct(CARD_NO) FROM USR_0_DEBITCARD_EXISTING_CPD a,"
							+ "USR_0_DC_ACC_MAPPING_EXISTING b,usr_0_product_existing c "+
							"where a.WI_NAME ='"+sWorkitemId+"' and a.wi_name= b.wi_name and card_no=DEBIT_CARD_NO "
							+ "AND STATUS IN ('0','1') and b.linked_acc_no= c.acc_no and c.product_code in "
							+ "(SELECT PRODUCT_CODE FROM USR_0_PRODUCT_TYPE_MASTER WHERE PROCESS_TYPE ='Onshore')"
							+"and a.wi_name=c.wi_name and a.currency='"+productCurr+"'";
	
					formObject.setValue(CARD_TYPE,"");
					sOutput=formObject.getDataFromDB(sQuery);
					logInfo("sLinkCard---","sLinkCard");
					formObject.clearCombo(EXISTING_CARD_NO);
					if(sOutput.size()>0){
						for(int i=0;i<sOutput.size();i++){
							formObject.addItemInCombo(EXISTING_CARD_NO, sOutput.get(i).get(0));
						}
					}
					int iRows = getGridCount(QUEUE_DC);
					for(int i=0;i<iRows;i++){
						sProdCode = formObject.getTableCellValue(QUEUE_DC,i,0);	//rakshita						
						sNewLink = formObject.getTableCellValue(QUEUE_DC,i,4);		//rakshita					
						sGroupType = formObject.getTableCellValue(QUEUE_DC,i,1);//rakshita
						if(!sProdCode.equalsIgnoreCase(sProd) && !sNewLink.equalsIgnoreCase("Link") 
								&& sGroupType.equalsIgnoreCase(sGroup)){
							formObject.addItemInCombo(EXISTING_CARD_NO,"CARD_"+(i+1));
						}
					}
					formObject.setStyle(EXISTING_CARD_NO, DISABLE, FALSE);
					formObject.setStyle(EMBOSS_NAME, DISABLE, TRUE);
				}
				else{
					String sCustID = getPrimaryCustomerID();
					formObject.clearCombo(EXISTING_CARD_NO);//Rakshita
					formObject.setValue(EXISTING_CARD_NO,"");
					formObject.setStyle(EXISTING_CARD_NO, DISABLE, TRUE);
					formObject.setStyle(EMBOSS_NAME, DISABLE, FALSE);
					int iCount=0;
					sGroup = formObject.getValue(GROUP_TYPE).toString();
					int iRows = getGridCount(QUEUE_DC);
					for(int i=0;i<iRows;i++) {
						sGroupType = formObject.getTableCellValue(QUEUE_DC,i,1);//rakshita
						if(sGroupType.equalsIgnoreCase(sGroup)){
							iCount=iCount+1;
							break;
						}
					}
					if(sCustID.equalsIgnoreCase("") && iCount == 0){
						formObject.setValue(CARD_TYPE,"Primary");
						formObject.setStyle(CARD_TYPE, DISABLE, TRUE);
					}
					else if(iCount != 0){
						formObject.setValue(CARD_TYPE,"Supplementary");
						formObject.setStyle(CARD_TYPE, DISABLE, TRUE);
					}
					else{
						sQuery = "SELECT COUNT(WI_NAME) as COUNT_WI FROM USR_0_DEBITCARD_EXISTING_CPD WHERE WI_NAME ='"+sWorkitemId+"' AND CUST_ID='"+sCustID+"' AND "+
								"STATUS NOT IN ('2','3','4') AND PRODUCT_GROUP IN (SELECT DISTINCT GROUPID FROM USR_0_CARD_PRODUCT_GROUP"+
								"WHERE PRODTYPE = '"+sGroup+"')";
						sOutput=formObject.getDataFromDB(sQuery);
						iCount = Integer.parseInt( sOutput.get(0).get(0));
						if(iCount==0){
							formObject.setValue(CARD_TYPE,"Primary");
							formObject.setStyle(CARD_TYPE, DISABLE, TRUE);
						}else{
							formObject.setValue(CARD_TYPE,"Supplementary");
							formObject.setStyle(CARD_TYPE, DISABLE, TRUE);
						}
					}
				}
				formObject.setValue(EXISTING_CARD_NO,"");
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_FATCA)) { 
				if(formObject.getValue(CHECKBOX_FATCA).toString().equalsIgnoreCase(TRUE)) {
					enableFATCACPD();
				} else {
					frameFatcaCpdDisable();
				}
				formObject.setStyle(CHECKBOX_FATCA, DISABLE, FALSE);
			} else if(controlName.equalsIgnoreCase(PD_CUSTSEGMENT)) {
				if(!formObject.getValue(controlName).toString().equalsIgnoreCase("")) {/*
					int fieldToFocus=Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
					String bankRelation = formObject.getTableCellValue(ACC_RELATION, fieldToFocus,7);//bank relation = 7
					String reqType= formObject.getValue(REQUEST_TYPE).toString();
					if(bankRelation.equalsIgnoreCase("New") && !(reqType.equalsIgnoreCase("New Account with Category Change"))) {
						String segment = formObject.getValue(PD_CUSTSEGMENT).toString();
						String rmCode = formObject.getValue(RM_CODE).toString();
						if(segment.equalsIgnoreCase("Aspire") && rmCode.equalsIgnoreCase("")) {
						} else if(segment.equalsIgnoreCase("Privilege") || segment.equalsIgnoreCase("Emirati")) {
							logInfo("Inside Click Event","INSIDE Combo4");
						//commneted by sahil for the issue	formObject.setValue(RM_CODE,"");
						} else if((segment.equalsIgnoreCase("Excellency") || segment.equalsIgnoreCase("Emirati Excellency"))
								&& rmCode.equalsIgnoreCase("")) {
							formObject.setValue(RM_CODE,"");
						} else if(controlName.equalsIgnoreCase(RM_CODE)) {
							logInfo("Inside Click Event","INSIDE Profit Center");
							//setProfitCode();//as mentioned its empty
						} else if(controlName.equalsIgnoreCase(CP_CITY)||controlName.equalsIgnoreCase(PA_CITY)||controlName.equalsIgnoreCase(RA_CITY)){
							manageCity(controlName);
						}
					}
				 */
					manageInternalSection();
					int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());  //need to checked again
					String sAccRelation = formObject.getTableCellValue(ACC_RELATION, 0, 9);//objChkRepeater.getValue(fieldToFocus,"AO_ACC_RELATION.ACC_RELATION");
					String bnk_relation = formObject.getTableCellValue(ACC_RELATION, 0, 7);//objChkRepeater.getValue(fieldToFocus,"AO_ACC_RELATION.BANK_RELATION");
					if(sAccRelation.equalsIgnoreCase("Existing")){
						Frame22_CPD_Disable();
					}
					if(bnk_relation.equalsIgnoreCase("Existing")||bnk_relation.equalsIgnoreCase("New")){
						String segment = formObject.getValue(PD_CUSTSEGMENT).toString();
						if(bnk_relation.equalsIgnoreCase("Existing")) {
							formObject.setStyle(IDS_OTH_CB_OTHERS,DISABLE,TRUE);
							if(segment.equalsIgnoreCase("Aspire")||segment.equalsIgnoreCase("Privilege")||
									segment.equalsIgnoreCase("Excellency")||segment.equalsIgnoreCase("Private Clients")||
									segment.equalsIgnoreCase("Simplylife")||segment.equalsIgnoreCase("Emirati")||
									segment.equalsIgnoreCase("Emirati Excellency")) {
								formObject.setStyle(RM_CODE,DISABLE,TRUE);	
							}					
						} else if(bnk_relation.equalsIgnoreCase("New")){	
							formObject.setStyle(PRO_CODE, DISABLE,FALSE);
							formObject.setStyle(EXCELLENCY_CNTR, DISABLE,FALSE);
							if(segment.equalsIgnoreCase("Aspire") || segment.equalsIgnoreCase("Simplylife")){
								getPromoCode(segment);						
								visibleOnSegmentBasis(segment);			
							} else if(segment.equalsIgnoreCase("Privilege") || segment.equalsIgnoreCase("Emirati")){
								formObject.setValue(RM_CODE,"");
								formObject.setValue(RM_NAME,"");
								getPromoCode("Privilege");	
								getPromoCode(segment);	
								visibleOnSegmentBasis("Privilege");			
							} else if(segment.equalsIgnoreCase("Excellency") || segment.equalsIgnoreCase("Private Clients") ||
									segment.equalsIgnoreCase("Emirati Excellency")) {
								formObject.setValue(RM_CODE,"");
								formObject.setValue(RM_NAME,"");
								getPromoCode(segment);				
								visibleOnSegmentBasis(segment);	
							} else if(segment.equalsIgnoreCase("Signatory")) {
								if(!(formObject.getTableCellValue(ACC_RELATION, fieldToFocus, 9).equalsIgnoreCase("AUS") 
										|| formObject.getTableCellValue(ACC_RELATION, fieldToFocus, 9).equalsIgnoreCase("POA"))) {
									sendMessageValuesList("","Signatory not allowed. Please select another type.");
									formObject.setValue(PD_CUSTSEGMENT, ""); 
									formObject.setValue(RM_CODE, "");
									formObject.setValue(RM_NAME, "");
								} else {
									getPromoCode("Signatory");
									visibleOnSegmentBasis("Signatory");	
								}
							}
							if(!segment.equalsIgnoreCase("")){
								formObject.setStyle(IDS_OTH_CB_OTHERS,DISABLE,FALSE);		
								formObject.setValue(IDS_OTH_CB_OTHERS,FALSE);
								formObject.setStyle(IDS_BNFT_CB_OTHERS,DISABLE,TRUE);
								formObject.setValue(IDS_BNFT_CB_OTHERS,"");
							} else {
								formObject.setStyle(IDS_OTH_CB_OTHERS,DISABLE,TRUE);
								formObject.setStyle(IDS_BNFT_CB_OTHERS,DISABLE,TRUE);
								formObject.setValue(IDS_OTH_CB_OTHERS,FALSE);
								formObject.setValue(IDS_BNFT_CB_OTHERS,"");
								formObject.setStyle(IDS_PC_CB_TP,VISIBLE,TRUE);
								formObject.setStyle(IDS_PC_CB_TRAVEL,VISIBLE,TRUE);
								formObject.setStyle(IDS_PC_CB_SPORT,VISIBLE,TRUE);
								formObject.setStyle(IDS_PC_CB_SHOPPING,VISIBLE,TRUE);
								formObject.setStyle(IDS_PC_CB_ENTERTAINMENT,VISIBLE,TRUE);
								formObject.setStyle(IDS_CB_SAL_TRANSFER,VISIBLE,TRUE);
								formObject.setStyle(IDS_CB_MORTGAGES,VISIBLE,TRUE);
								formObject.setStyle(IDS_CB_INSURANCE,VISIBLE,TRUE);
								formObject.setStyle(IDS_CB_TRB,VISIBLE,TRUE);
								formObject.setStyle(IDS_CB_OTHERS,VISIBLE,TRUE);
								formObject.setStyle(IDS_CB_VVIP,VISIBLE,TRUE);
								formObject.setStyle(IDS_BNFT_CB_TP,VISIBLE,TRUE);
								//							formObject.setStyle("Label128",VISIBLE,TRUE);	//check lables moksh
								//							formObject.setStyle("Label134",VISIBLE,TRUE);
								//							formObject.setStyle("Label133",VISIBLE,TRUE);
								formObject.setStyle(PRO_CODE, DISABLE,TRUE);
								formObject.setStyle(EXCELLENCY_CNTR, DISABLE,TRUE);
							}
						}
						if(bnk_relation.equalsIgnoreCase("Existing")){
							String seg=formObject.getValue(PD_CUSTSEGMENT).toString();
							segmentSelectionForExistingcustomer(seg);	//to be made MOKSH
						} else if(bnk_relation.equalsIgnoreCase("New")){
							if(formObject.getValue(IDS_OTH_CB_OTHERS).toString().equalsIgnoreCase(FALSE)){
								formObject.setStyle(IDS_BNFT_CB_OTHERS,DISABLE,TRUE);
								formObject.setStyle(IDS_OTH_CB_OTHERS,DISABLE,TRUE);
								if(!formObject.getValue(PD_CUSTSEGMENT).toString().equalsIgnoreCase("Select")){
									formObject.setStyle(IDS_OTH_CB_OTHERS,DISABLE,FALSE);
								}
							} else {
								formObject.setStyle(IDS_BNFT_CB_OTHERS,DISABLE,FALSE);
								formObject.setStyle(IDS_OTH_CB_OTHERS,DISABLE,FALSE);
							}
						}
					}				
				}
				logInfo("CPDMakerThreeStep","bfr calling setRMCode");
				setRMCode();
			} else if(controlName.equalsIgnoreCase(DRP_RESEIDA) || (controlName.equalsIgnoreCase(PD_EIDANO))) {
				logInfo("onChange drp_reseida","INSIDE EIDA");
				setEIDA();
			} else if(controlName.equalsIgnoreCase(MANUAL_PER_CNTRY)){
				formObject.setValue(PA_OTHERS,"");
				formObject.setValue(OTHER_PERM_CITY,"");
			} /*else if(controlName.equalsIgnoreCase(MANUAL_FIRSTNAME)
					&&formObject.getValue(CHECKBOX_SHORTNAME_MANUAL).toString().equalsIgnoreCase(TRUE)){
				shortnamefunctionality();
			}
			else if(controlName.equalsIgnoreCase(MANUAL_LASTNAME)
					&&formObject.getValue(CHECKBOX_SHORTNAME_MANUAL).toString().equalsIgnoreCase(TRUE)){
				shortnamefunctionality();
			}*/ else if(controlName.equalsIgnoreCase(MANUAL_NAME)
					&&formObject.getValue(CHECKBOX_SHORTNAME_MANUAL).toString().toString().equalsIgnoreCase(TRUE)){
				shortnamefunctionality();
			} else if(controlName.equalsIgnoreCase("UDF_Value")) {
				formObject.setValue("UDF_Value","");
			} else if(controlName.equalsIgnoreCase(MANUAL_FIRSTNAME) || controlName.equalsIgnoreCase(MANUAL_LASTNAME)) { 
				formObject.setValue(MANUAL_NAME,formObject.getValue(MANUAL_FIRSTNAME).toString()+" "
						+formObject.getValue(MANUAL_LASTNAME).toString());
				formObject.setValue(CRS_FIRSTNAME,formObject.getValue(MANUAL_FIRSTNAME).toString());
				formObject.setValue(CRS_LASTNAME,formObject.getValue(MANUAL_LASTNAME).toString());
				formObject.setValue(PD_FULLNAME,formObject.getValue(MANUAL_NAME).toString());
				if(TRUE.equalsIgnoreCase(formObject.getValue(CHECKBOX_SHORTNAME_MANUAL ).toString())){
					shortnamefunctionality();
				}
			} else if(controlName.equalsIgnoreCase(MANUAL_RESIDENT)){
				flag_phone_start=TRUE;
				flag_phone=true;
				flag_mobile=true;
				logInfo("onChange RESIDENT_MANUAL",flag_phone_start+flag_mobile+flag_phone);
				formObject.setValue(RA_OTHERS,"");
				formObject.setValue(OTHER_RES_CITY,"");
			} else if(controlName.equalsIgnoreCase(MANUAL_STATE)){
				formObject.setValue(MANUAL_CITY,formObject.getValue(MANUAL_STATE).toString());
				formObject.setValue(CP_CITY, formObject.getValue(MANUAL_STATE).toString());
				formObject.setValue(RA_CITY, formObject.getValue(MANUAL_STATE).toString());
				formObject.setValue(PERM_STATE, formObject.getValue(MANUAL_STATE).toString());
			} else if(controlName.equalsIgnoreCase(MANUAL_VISASTATUS)) {
				logInfo("onchange visaStatus_manual ","INSIDE visaStatus_manual CHANGE");
				if(returnVisaStatus().equalsIgnoreCase("Residency Visa")) {
					formObject.setStyle(DRP_RESEIDA,DISABLE,FALSE);
				} else {
					formObject.setStyle(DRP_RESEIDA,DISABLE,TRUE);	
				}
				if(returnVisaStatus().equalsIgnoreCase("Under Processing")){
					logInfo("CPDMakerThreeStep","Click: INSIDE MANUAL_VISASTATUS");
					formObject.setValue(CHECKBOX_VISA_NO_MANUAL,"true");
					formObject.setValue(CHECKBOX_VISA_NO_FCR,"false");
					formObject.setValue(CHECKBOX_VISA_NO_EIDA,"false");
					if(formObject.getValue(CHECKBOX_SELECTALL_MANUAL).toString().equalsIgnoreCase("true")||
							formObject.getValue(CHECKBOX_VISA_NO_MANUAL).toString().equalsIgnoreCase("true")){
						formObject.setValue(MANUAL_VISANO,"MDSA");
						formObject.setValue(HD_VISA_NO,formObject.getValue(MANUAL_VISANO).toString());
					}
					sendMessageValuesList("",CA0172);
					String updatequery="UPDATE USR_0_CUST_TXN set VISA_NO='MDSA' WHERE cust_sno='"
							+getPrimaryCustomerSNO()+"' AND WI_NAME = '"+sWorkitemId+"'";
					formObject.saveDataInDB(updatequery);
					logInfo("CPDMakerThreeStep","Click: INSIDE MANUAL_VISASTATUS "+CA0172);
				}
			} else if(controlName.equalsIgnoreCase("COMBO34")) {
				if(! returnVisaStatus().equalsIgnoreCase("Residency Visa")) {
					formObject.setStyle(DRP_RESEIDA,DISABLE,TRUE);
					formObject.setValue(DRP_RESEIDA,"");
				}
				else  {
					formObject.setStyle(DRP_RESEIDA,DISABLE,FALSE);
				}
			} else if(controlName.equalsIgnoreCase(EMP_STATUS)  
					&& !formObject.getValue(controlName).toString().equalsIgnoreCase("")) {
				logInfo("onChange EMP_STATUS","IN EMP_STATUS");
				if(controlValue.equalsIgnoreCase("Employed")) {
					String [] fieldName = {RA_CB_GEN_TRDNG_CMPNY,RA_CB_PRECIOUS_STONE_DEALER,RA_CB_BULLN_COMMDTY_BROKR,
							RA_CB_REAL_STATE_BROKR,RA_CB_USD_AUTO_DEALER,ED_NO_UAE_OVRS_BRNCH};
					String [] fieldValue = {FALSE,FALSE,FALSE,FALSE,FALSE,""};
					setMultipleFieldValues(fieldName, fieldValue);
					String [] disableField = {ED_NATURE_OF_BUSNS,ED_PERC_OF_OWNRSHP,ED_NO_UAE_OVRS_BRNCH,
							ED_COMP_WEBSITE};
					disableControls(BUSINESS_NATURE_SECTION);
					disableControls(disableField);
					if(formObject.getValue(CHK_EMP_DETAIL).toString().equalsIgnoreCase(TRUE)) {
						formObject.setStyle(ED_EMP_TYPE,DISABLE,FALSE);
					}
					if(formObject.getValue("CHK_RISK_ASS").toString().equalsIgnoreCase(TRUE)) {
						formObject.setStyle(RA_IS_CUST_WRKNG_NON_UAE,DISABLE,FALSE);
						formObject.setStyle(RA_IS_CUST_WRKNG_UAE,DISABLE,FALSE);
					}
				} else if(controlValue.equalsIgnoreCase("Self Employed")) {
					formObject.setValue(RA_IS_CUST_WRKNG_UAE,"");//, true);
					formObject.setValue(RA_IS_CUST_WRKNG_NON_UAE,"");// true);
					enableControls(new String [] {ED_NATURE_OF_BUSNS,ED_PERC_OF_OWNRSHP,
							ED_NO_UAE_OVRS_BRNCH,ED_COMP_WEBSITE});
					enableControls(BUSINESS_NATURE_SECTION);
					disableControls(new String [] {ED_EMP_TYPE,RA_IS_CUST_WRKNG_UAE,RA_IS_CUST_WRKNG_NON_UAE});
					formObject.setValue(ED_EMP_TYPE, "");
				} else {
					disableControls(new String [] {ED_NATURE_OF_BUSNS,ED_PERC_OF_OWNRSHP,ED_NO_UAE_OVRS_BRNCH,
							ED_COMP_WEBSITE,ED_EMP_TYPE,RA_IS_CUST_WRKNG_UAE,
							RA_IS_CUST_WRKNG_NON_UAE,DEALS_IN_WMD});
					disableControls(BUSINESS_NATURE_SECTION);
					formObject.setValue(ED_EMP_TYPE, "");
					if(controlValue.equalsIgnoreCase("Salaried")) {
						formObject.setValue(ED_NO_UAE_OVRS_BRNCH, "");
						disableControls(new String [] {ED_NO_UAE_OVRS_BRNCH});
						disableControls(BUSINESS_NATURE_SECTION);
						enableControls(new String [] {RA_IS_CUST_WRKNG_UAE,RA_IS_CUST_WRKNG_NON_UAE});
						String [] fieldName = {ED_NO_UAE_OVRS_BRNCH,RA_CB_GEN_TRDNG_CMPNY,RA_CB_PRECIOUS_STONE_DEALER,
								RA_CB_BULLN_COMMDTY_BROKR,RA_CB_REAL_STATE_BROKR,RA_CB_USD_AUTO_DEALER};
						String [] fieldValue = {"",FALSE,FALSE,FALSE,FALSE,FALSE};
						setMultipleFieldValues(fieldName, fieldValue);
					}
				}
				/*if(!controlValue.equalsIgnoreCase("Self Employed")) {
					disableControls(new String [] {RA_CB_OTHERS,"Text17"});
					formObject.setValue(RA_CB_OTHERS, FALSE);
					formObject.setValue(RA_OTHERS, "");
				} else {
					formObject.setStyle(RA_CB_OTHERS,DISABLE,FALSE);
					formObject.setValue(RA_CB_OTHERS, FALSE);
					formObject.setValue(RA_OTHERS, "");
					formObject.setStyle(RA_CB_OTHERS,DISABLE,TRUE);
				}*/
				formObject.setValue(BN_OTHERS,"");
				formObject.setValue(RA_CB_OTHERS, FALSE);
				formObject.setStyle(BN_OTHERS, DISABLE, TRUE);
				if(!controlValue.equalsIgnoreCase("Self Employed")) {
					formObject.setStyle(RA_CB_OTHERS, DISABLE, TRUE);
				} else {
					formObject.setStyle(RA_CB_OTHERS, DISABLE, FALSE);
				}
			} else if(controlName.equalsIgnoreCase(ED_CUST_CRS_BRDR_PAYMENT)) {
				if(formObject.getValue(controlName).toString().equalsIgnoreCase("Yes")) {
					enableControls(new String [] {ED_PURPSE_CRS_BRDR_PAYMENT,ED_ANTCPATD_CRS_BRDER_PYMT,
							ED_ANTCPATD_MNTHVAL_BRDER_PYMT,ED_CNTRY_PYMT_RECV});
				} else {
					String [] fieldName = {ED_PURPSE_CRS_BRDR_PAYMENT,ED_ANTCPATD_CRS_BRDER_PYMT,
							ED_ANTCPATD_MNTHVAL_BRDER_PYMT,ED_CNTRY_PYMT_RECV};
					String [] fieldValue = {"","","",""};
					setMultipleFieldValues(fieldName, fieldValue);
					disableControls(new String [] {ED_PURPSE_CRS_BRDR_PAYMENT,ED_ANTCPATD_CRS_BRDER_PYMT,
							ED_ANTCPATD_MNTHVAL_BRDER_PYMT,ED_CNTRY_PYMT_RECV});
				}
			} else if(controlName.equalsIgnoreCase(RA_IS_UAE_RESIDENT) && !controlName.equalsIgnoreCase("")) {
				if(formObject.getValue(controlName).toString().equalsIgnoreCase("No")) {
					formObject.setStyle(RA_RSN_BNKNG_UAE,DISABLE,FALSE);
				} else {
					formObject.setValue(RA_RSN_BNKNG_UAE, "");
					formObject.setStyle(RA_RSN_BNKNG_UAE,DISABLE,TRUE);
				}
			} else if(controlName.equalsIgnoreCase(RA_IS_CUST_PEP)) {
				if(formObject.getValue(controlName).toString().equalsIgnoreCase("Yes")) {
					formObject.setStyle(RA_LIST_OF_CUST_PEP,DISABLE,FALSE);
				} else {
					formObject.setValue(RA_LIST_OF_CUST_PEP,"");
					formObject.setStyle(RA_LIST_OF_CUST_PEP,DISABLE,TRUE);
				}
			} else if(controlName.equalsIgnoreCase(MANUAL_VISASTATUS)) {	
				logInfo("onChangeEventCPDMakerFourStep","4 step");
				mohit_flag=false;
				if(returnVisaStatus().equalsIgnoreCase("Under Processing")&& (!mohit_flag)) {
					logInfo("onChangeEventCPDMakerFourStep","before MDSA pop up");
					String updatequery="UPDATE USR_0_CUST_TXN set VISA_NO='MDSA' "
							+ "WHERE cust_sno='"+getPrimaryCustomerSNO()+"' AND WI_NAME = '"+sWorkitemId+"'";
					formObject.saveDataInDB(updatequery);
					logInfo("onChangeEventCPDMakerFourStep","Udate successful"+updatequery);
					formObject.setValue(CHECKBOX_VISA_NO_MANUAL,TRUE);
					formObject.setValue(CHECKBOX_VISA_NO_FCR,FALSE);
					formObject.setValue(CHECKBOX_VISA_NO_EIDA,FALSE);
					if(formObject.getValue(CHECKBOX_SELECTALL_MANUAL).toString().equalsIgnoreCase(TRUE)
							|| formObject.getValue(CHECKBOX_VISA_NO_MANUAL).toString().equalsIgnoreCase(TRUE)) {	
						logInfo("onChangeEventCPDMakerFourStep","before VISANO_MANUAL is set"+
								formObject.getValue(MANUAL_VISANO)+formObject.getValue(CHECKBOX_VISA_NO_MANUAL)+
								formObject.getValue(CHECKBOX_SELECTALL_MANUAL));
						formObject.setValue(MANUAL_VISANO,"MDSA");
						formObject.setStyle(MANUAL_VISANO,DISABLE,TRUE);
						String x = formObject.getValue(MANUAL_VISANO).toString();
						formObject.setValue(HD_VISA_NO,x);
					}
					logInfo("onChangeEventCPDMakerFourStep","after VISANO_MANUAL is set and out if"+formObject.getValue(MANUAL_VISANO).toString());
					sendMessageValuesList("", CA0172);
				}
			} else if((formObject.getValue(MANUAL_VISANO).toString().equalsIgnoreCase("MDSA")) 
					&&(!( returnVisaStatus().equalsIgnoreCase("Under Processing"))) && (!mohit_flag)) {
				logInfo("onChangeEventCPDMakerFourStep","before MDSA is null pop up");
				String updateQuery="UPDATE USR_0_CUST_TXN set VISA_NO='' WHERE cust_sno='"+getPrimaryCustomerSNO()+"' AND WI_NAME = '"+sWorkitemId+"'";
				formObject.saveDataInDB(updateQuery);
				logInfo("onChangeEventCPDMakerFourStep","Udate successful"+updateQuery);
				formObject.setValue(CHECKBOX_VISA_NO_MANUAL,TRUE);
				formObject.setValue(CHECKBOX_VISA_NO_FCR,FALSE);
				formObject.setValue(CHECKBOX_VISA_NO_EIDA,FALSE);
				formObject.setStyle(MANUAL_VISANO,DISABLE,FALSE);
				if(formObject.getValue(CHECKBOX_SELECTALL_MANUAL).toString().equalsIgnoreCase(TRUE)
						||formObject.getValue(CHECKBOX_VISA_NO_MANUAL).toString().equalsIgnoreCase(TRUE))
				{
					formObject.setValue(MANUAL_VISANO,"");
					formObject.setValue(HD_VISA_NO,formObject.getValue(MANUAL_VISANO).toString());
				}
				sendMessageValuesList("", CA0173);
			} else if(controlName.equalsIgnoreCase("PRODUCTQUEUE_MODEOFFUNDING")) {
				//manageFundTransfer();//to be chedkedyamini
			} else if(controlName.equalsIgnoreCase(INDICIACOMBO)||controlName.equalsIgnoreCase(FAT_SSN)
					||controlName.equalsIgnoreCase(FAT_CUST_CLASSIFICATION)||controlName.equalsIgnoreCase(DATEPICKERCUST)
					||controlName.equalsIgnoreCase(DATEPICKERW8)||controlName.equalsIgnoreCase(COMBODOC)) {
				logInfo("onChange COMBODOC1:","CHECK95");
				formObject.setValue("Change_In_FATCA_3way_Inputs","Yes");
				formObject.setValue("fatcamain","Yes");   
				if(formObject.getValue("CHECK95").toString().equalsIgnoreCase(TRUE)) {
					formObject.setStyle("validateFATCA",DISABLE,FALSE);
					logInfo("onChange:","CHECK95");
					formObject.setStyle(COMBODOC,DISABLE,FALSE);
				}
				if(controlName.equalsIgnoreCase(COMBODOC)
						&& formObject.getValue(COMBODOC).toString().equalsIgnoreCase("W8BEN")) {
					logInfo("onChange COMBODOC2:","CHECK95");
					formObject.setValue(FAT_CUST_CLASSIFICATION,"NON-US PERSON");
				}
			} else if(controlName.equalsIgnoreCase(FAT_CUST_CLASSIFICATION)) {
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat simpledateformat = new SimpleDateFormat(getDateValue("DATEFORMAT"));
				String scurrentDate = simpledateformat.format(calendar.getTime());
				formObject.setValue(DATEPICKERCUST, scurrentDate);
			} else if(controlName.equalsIgnoreCase(FCR_EMPLYR_NAME) 
					|| controlName.equalsIgnoreCase(EIDA_EMPLYR_NAME) 
					|| controlName.equalsIgnoreCase(MANUAL_EMPLYR_NAME)) {
				String sIsFCREmpName = formObject.getValue(CHECKBOX_EMP_NAME_FCR).toString();
				String sIsEIDAEmpName = formObject.getValue(CHECKBOX_EMP_NAME_EIDA).toString();
				String sIsManualEmpName = formObject.getValue(CHECKBOX_EMP_NAME_MANUAL).toString();
				String sFCREmpName = formObject.getValue(FCR_EMPLYR_NAME).toString();
				String sEIDAEmpName = formObject.getValue(EIDA_EMPLYR_NAME).toString();
				String sManualEmpName = formObject.getValue(MANUAL_EMPLYR_NAME).toString();
				String sFinalEmpName = getFinalData(sIsFCREmpName,sIsEIDAEmpName,sIsManualEmpName,
						sFCREmpName,sEIDAEmpName,sManualEmpName);
				logInfo("onChangeEventCPDMakerFourStep","sFinalEmpName ==== "+sFinalEmpName);
				if(formObject.getValue(EMP_STATUS).toString().equalsIgnoreCase("")) {
					if(!sFinalEmpName.equalsIgnoreCase("")) {
						formObject.setValue(EMP_STATUS, "Employed");
					}
				}
				List<List<String>> sOutput = formObject.getDataFromDB("SELECT CD_STATUS FROM USR_0_EMPLOYER_MASTER"
						+ " WHERE EMP_NAME ='"+formObject.getValue(controlName)+"'");
				logInfo("","Emp Mast Output---"+sOutput);
				formObject.setValue(ED_CB_TML,"False");
				formObject.setValue(ED_CB_NON_TML,"False");
				if(sOutput!=null && sOutput.size() > 0){
					if(sOutput.get(0).get(0).equalsIgnoreCase("1") || sOutput.get(0).get(0).equalsIgnoreCase("2")) {
						formObject.setValue(ED_CB_TML,"True");
					}
					else {
						formObject.setValue(ED_CB_NON_TML,"True");
					}
				}
				logInfo("onChangeEventCPDMakerFourStep","Inside EMPNAME");
				String EmpName=formObject.getValue(EMPNAME).toString();
				if(!(formObject.getValue(EMPNAME).toString().equalsIgnoreCase("")) 
						&& !(formObject.getValue(EMPNAME).toString().equalsIgnoreCase("OTHERS"))){
					logInfo("","When EmpName is not empty");
					String sqString = "Select CD_ADDRESS1,CD_PO_BOX_NO,CD_CITY,CD_STATE from company_details"
							+ " where CD_NAME like '%"+sFinalEmpName+"%'";
					logInfo("onChangeEventCPDMakerFourStep","sqString : "+sqString);
					List<List<String>> sOutput1=formObject.getDataFromDB(sqString);
					if(sOutput1!= null && sOutput1.size()>0){
						logInfo("onChangeEventCPDMakerFourStep","company_details1 Output---"+sOutput);
						formObject.setValue(EMP_STREET,sOutput1.get(0).get(0));
						formObject.setValue(EMP_PO_BOX,sOutput1.get(0).get(1));
						formObject.setValue(EMP_CITY,sOutput1.get(0).get(2));
						formObject.setValue(EMP_STATE,sOutput1.get(0).get(3));
					}
				}
				else{
					formObject.setValue(EMP_STREET,"");
					formObject.setValue(EMP_PO_BOX,"");
					formObject.setValue(EMP_CITY,"");
					formObject.setValue(EMP_STATE,"");
					formObject.setValue(EMP_COUNTRY,"");
				}
	
			} else if(controlName.equalsIgnoreCase(FCR_RESIDENT) || controlName.equalsIgnoreCase(EIDA_RESIDENT) 
					|| controlName.equalsIgnoreCase(MANUAL_RESIDENT) 
					||controlName.equalsIgnoreCase(FCR_NATIONALITY) 
					|| controlName.equalsIgnoreCase(EIDA_NATIONALITY) 
					|| controlName.equalsIgnoreCase(MANUAL_NATIONALITY)) {					
				logInfo("onChangeEventCPDMakerFourStep","Setting Visa Status");
				manageResidencyAndVisaStatus(RA_IS_UAE_RESIDENT,"COMBO34");
			} else if(controlName.equalsIgnoreCase(ED_MONTHLY_INCM)) {
				String mnthsalary = formObject.getValue(ED_MONTHLY_INCM).toString();					
				try {
					if(mnthsalary.equalsIgnoreCase("")) {
						formObject.setValue(ED_ANNUAL_INC,"");
						formObject.setValue(ED_SAL_AED,"");
					}
					long mnthslry = Long.parseLong(mnthsalary);
					long finalsalary=mnthslry*12;
					formObject.setValue(ED_ANNUAL_INC,finalsalary+"");
					formObject.setValue(ED_CB_SAL_AED, TRUE);
					if(formObject.getValue(ED_CB_SAL_AED).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(ED_SAL_AED,finalsalary+"");
					}
				} catch (Exception e) {
					logInfo("executeServerEvent", "Exception in Event- " + eventType + "control name- " +controlName+ ": ");
					logError("executeServerEvent", e);
				}
			} else if(controlName.equalsIgnoreCase(ED_ANNUAL_INC)) {
				String sAnnualSalary = formObject.getValue(ED_ANNUAL_INC).toString();					
				try {
					int iAnnualSal = Integer.parseInt(sAnnualSalary);
					int finalsalary=iAnnualSal/12;
					formObject.setValue(ED_MONTHLY_INCM,finalsalary+"");
					formObject.setValue(ED_SAL_AED,formObject.getValue(ED_ANNUAL_INC).toString());
				} catch (Exception e) {
					logInfo("executeServerEvent", "Exception in Event- " + eventType + "control name- " +controlName+ ": ");
					logError("executeServerEvent", e);
				}
			} else if(controlName.equalsIgnoreCase(SI_DEB_ACC_NO)) {
				fetchCurrency(formObject.getValue(controlName).toString());	
			} else if(controlName.equalsIgnoreCase(ED_CB_SAL_AED)) {
				formObject.setValue(ED_SAL_AED,formObject.getValue(ED_ANNUAL_INC).toString());
			} else if(controlName.equalsIgnoreCase("CRS_CERTIFICATION_OBTAINED")) {
				if(formObject.getValue(controlName).toString().equalsIgnoreCase("Yes")) {
					formObject.setValue("CRS_Classification","UPDATED-DOCUMENTED");
				} else if(formObject.getValue(controlName).toString().equalsIgnoreCase("No")) {
					formObject.setValue("CRS_Classification","UPDATED-UNDOCUMENTED");
				}
			} else if(controlName.equalsIgnoreCase("CRS_CheckBox")) {
				if(formObject.getValue(CRS_CB).toString().equalsIgnoreCase(TRUE))
					formObject.setStyle("Frame_CRS_Details",DISABLE,FALSE);
				else
					formObject.setStyle("Frame_CRS_Details",DISABLE,TRUE);	
			} else if(controlName.equalsIgnoreCase(DEL_PREF_RADIO)){
				if(formObject.getValue(controlName).toString().equalsIgnoreCase("Yes")) {
					logInfo("onChangeEventCPDMakerFourStep","In side Yes");
					formObject.setStyle(NEW_DEL_MODE,DISABLE,FALSE);
					formObject.setStyle(CHANNEL_NAME,DISABLE,FALSE);
				}else if(formObject.getValue(controlName).toString().equalsIgnoreCase("No")){
					logInfo("onChangeEventCPDMakerFourStep","In side DEL_PREF_RADIO IF ");
					formObject.setStyle(NEW_DEL_MODE,DISABLE,TRUE);
					formObject.setStyle(CHANNEL_NAME,DISABLE,TRUE);
				}
			} else if(controlName.equalsIgnoreCase(RD_INST_DEL)){
				if(formObject.getValue(controlName).toString().equalsIgnoreCase("Yes")) {
					if(sActivityName.equalsIgnoreCase("QDE_ACCOUNT_INFO")){	
						sendMessageValuesList("", "Instant delievery not allowed at this Workstep.");
						formObject.setValue(RD_INST_DEL,"No");
					} else {
						disableControls(new String [] {"COMBO65","TEXT131",RD_INST_DEL,BRNCH_OF_INSTANT_ISSUE,
								DEL_EXCELLENCY_CNTR, DEL_BRNCH_COURIER,DEL_BRNCH_NAME});
					}
				}else if(formObject.getValue(controlName).toString().equalsIgnoreCase("No")){
					disableControls(new String [] {BRNCH_OF_INSTANT_ISSUE,EXISTING_NOM_PRSN});
					enableControls(new String [] {NOM_REQ,"COMBO65","TEXT131",RD_INST_DEL,DEL_EXCELLENCY_CNTR,
							DEL_BRNCH_COURIER,DEL_BRNCH_NAME});
					formObject.setValue(NOM_REQ,"");
					Frame48_CPD_ENable();
					formObject.setStyle(NEW_DEL_MODE,DISABLE,TRUE);
					formObject.setStyle(CHANNEL_NAME,DISABLE,TRUE);
				}
			} else if(controlName.equalsIgnoreCase(ACC_INFO_PG)) {
				if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("New Account With Category Change") 
						&& formObject.getValue(NEW_CUST_SEGMENT).toString().equalsIgnoreCase("")) {
					sendMessageValuesList(NEW_CUST_SEGMENT, "Please select New Category.");
					return;
				}
			} else if(controlName.equalsIgnoreCase(DOC_APPROVAL_OBTAINED)) {
				logInfo("Inside Click Event","INSIDE AO_DOC_APPROVAL_OBTAINED Click");
				if(TRUE.equalsIgnoreCase(formObject.getValue(DOC_APPROVAL_OBTAINED).toString())) {
					logInfo("Inside Click Event","INSIDE AO_DOC_APPROVAL_OBTAINED true");
					formObject.setValue(COURT_ORD_TRADE_LIC,FALSE);
				} else {
					logInfo("Inside Click Event","INSIDE AO_DOC_APPROVAL_OBTAINED false");
					formObject.setValue(COURT_ORD_TRADE_LIC,TRUE);
				}
			}
			else if(controlName.equalsIgnoreCase(COURT_ORD_TRADE_LIC)) {
				logInfo("Inside Click Event","INSIDE AO_COURT_ORD_TRADE_LIC Click");
				if(TRUE.equalsIgnoreCase(formObject.getValue(COURT_ORD_TRADE_LIC).toString())) {
					logInfo("Inside Click Event","INSIDE AO_COURT_ORD_TRADE_LIC true");
					formObject.setValue(DOC_APPROVAL_OBTAINED,FALSE);
				}
				else{
					logInfo("Inside Click Event","INSIDE AO_COURT_ORD_TRADE_LIC false");
					formObject.setValue(DOC_APPROVAL_OBTAINED,TRUE);
				}
			}
			else if(controlName.equalsIgnoreCase(MANUAL_PREFIX)) {
				logInfo("Inside Click Event","INSIDE PREFIX_MANUAL");
				setGender();
				formObject.setValue(CUST_GENDER, formObject.getValue(MANUAL_GENDER).toString());
			} else if(controlName.equalsIgnoreCase(CHECKBOX_SHORTNAME_MANUAL)) {
				shortnamefunctionality();
			}
			else if(controlName.equalsIgnoreCase(CHECKBOX_FULLNAME_MANUAL)) {
				if(formObject.getValue(CHECKBOX_FULLNAME_MANUAL).toString().equalsIgnoreCase(TRUE)
						&&formObject.getValue(CHECKBOX_SHORTNAME_MANUAL).toString().equalsIgnoreCase(TRUE)) {
					shortnamefunctionality();
				} else {
					formObject.setValue(MANUAL_SHORTNAME,"");
				}
			}
			else if(controlName.equalsIgnoreCase(CHECKBOX_FULLNAME_FCR)) {
				if(formObject.getValue(CHECKBOX_FULLNAME_FCR).toString().equalsIgnoreCase(TRUE)
						&&formObject.getValue(CHECKBOX_SHORTNAME_MANUAL).toString().equalsIgnoreCase(TRUE)) {
					shortnamefunctionality();
				} else{
					formObject.setValue(MANUAL_SHORTNAME,"");
				}
			}
			else if(controlName.equalsIgnoreCase(CHECKBOX_FULLNAME_EIDA)) {
				if(formObject.getValue(CHECKBOX_FULLNAME_EIDA).toString().equalsIgnoreCase(TRUE)
						&&formObject.getValue(CHECKBOX_SHORTNAME_MANUAL).toString().equalsIgnoreCase(TRUE)){
					shortnamefunctionality();
				} else {
					formObject.setValue(MANUAL_SHORTNAME,"");
				}
			}
			else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_STATUS_FCR) 
					|| controlName.equalsIgnoreCase(CHECKBOX_VISA_STATUS_EIDA) || 
					controlName.equalsIgnoreCase(CHECKBOX_VISA_STATUS_MANUAL)) {
				logInfo("onChangeEventCPDMakerFourStep","INSIDE NEW TOGGLE VISA");
				if(controlName.equalsIgnoreCase(CHECKBOX_VISA_STATUS_FCR))
					toggleCheckbox(controlName,CHECKBOX_VISA_STATUS_EIDA,CHECKBOX_VISA_STATUS_MANUAL);
				else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_STATUS_MANUAL))
					toggleCheckbox(controlName,CHECKBOX_VISA_STATUS_EIDA,CHECKBOX_VISA_STATUS_FCR);
				else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_STATUS_EIDA))
					toggleCheckbox(controlName,CHECKBOX_VISA_STATUS_FCR,CHECKBOX_VISA_STATUS_MANUAL);
				manageManualFields(CHECKBOX_VISA_STATUS_MANUAL,MANUAL_VISASTATUS);
				if( returnVisaStatus().equalsIgnoreCase("Residency Visa") ) {
					formObject.setStyle(DRP_RESEIDA,DISABLE,FALSE);
				} else {
					formObject.setStyle(DRP_RESEIDA,DISABLE,TRUE);
				}
				return;
			} else if(controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_TYPE_MANUAL) 
					|| controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_TYPE_FCR) || 
					controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_TYPE_EIDA)) {
				logInfo("onChangeEventCPDMakerFourStep","INSIDE NEW TOGGLE PASSPORT");
				if(controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_TYPE_MANUAL))
					toggleCheckbox(controlName,CHECKBOX_PASSPORT_TYPE_FCR,CHECKBOX_PASSPORT_TYPE_EIDA);
				else if(controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_TYPE_EIDA))
					toggleCheckbox(controlName,CHECKBOX_PASSPORT_TYPE_FCR,CHECKBOX_PASSPORT_TYPE_MANUAL);
				else if(controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_TYPE_FCR))
					toggleCheckbox(controlName,CHECKBOX_PASSPORT_TYPE_MANUAL,CHECKBOX_PASSPORT_TYPE_EIDA);
				manageManualFields(CHECKBOX_PASSPORT_TYPE_MANUAL,MANUAL_PASSTYPE);
				return;
			} else if(controlName.equalsIgnoreCase(CHECKBOX_COB_FCR) ||
					controlName.equalsIgnoreCase(CHECKBOX_COB_EIDA) || 
					controlName.equalsIgnoreCase(CHECKBOX_COB_MANUAL))  {
				logInfo("onChangeEventCPDMakerFourStep","INSIDE NEW TOGGLE");
				if(controlName.equalsIgnoreCase(CHECKBOX_COB_FCR))
					toggleCheckbox(controlName,CHECKBOX_COB_EIDA,CHECKBOX_COB_MANUAL);
				else if(controlName.equalsIgnoreCase(CHECKBOX_COB_EIDA))
					toggleCheckbox(controlName,CHECKBOX_COB_FCR,CHECKBOX_COB_MANUAL);
				else if(controlName.equalsIgnoreCase(CHECKBOX_COB_MANUAL))
					toggleCheckbox(controlName,CHECKBOX_COB_FCR,CHECKBOX_COB_EIDA);
				manageManualFields(CHECKBOX_COB_MANUAL,MANUAL_COUNTRYBIRTH);
				return;
			} else if(controlName.equalsIgnoreCase(CHECKBOX_FIRSTNAME_FCR)) {
				toggleCheckbox(controlName,CHECKBOX_FIRSTNAME_EIDA,CHECKBOX_FIRSTNAME_MANUAL);
				manageManualFields(CHECKBOX_FIRSTNAME_MANUAL,MANUAL_FIRSTNAME);
				if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
					formObject.setValue("CRS_FirstName",formObject.getValue("firstname_fcr").toString());
				}
				return;
			} else if(controlName.equalsIgnoreCase(CHECKBOX_FIRSTNAME_EIDA)) {
				toggleCheckbox(controlName,CHECKBOX_FIRSTNAME_FCR,CHECKBOX_FIRSTNAME_MANUAL);
				manageManualFields(CHECKBOX_FIRSTNAME_MANUAL,MANUAL_FIRSTNAME);
				if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
					formObject.setValue("CRS_FirstName",formObject.getValue(EIDA_FIRSTNAME).toString());
				}
				return;
			} else if(controlName.equalsIgnoreCase(CHECKBOX_FIRSTNAME_MANUAL)) {
				toggleCheckbox(controlName,CHECKBOX_FIRSTNAME_FCR,CHECKBOX_FIRSTNAME_EIDA);
				manageManualFields(CHECKBOX_FIRSTNAME_MANUAL,MANUAL_FIRSTNAME);
				if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
					formObject.setValue("CRS_FirstName",formObject.getValue(MANUAL_FIRSTNAME).toString());
				}
				return;
			} else if(controlName.equalsIgnoreCase(CHECKBOX_LASTNAME_FCR)) {
				toggleCheckbox(controlName,CHECKBOX_LASTNAME_EIDA,CHECKBOX_LASTNAME_MANUAL);
				manageManualFields(CHECKBOX_LASTNAME_MANUAL,MANUAL_LASTNAME);
				if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
					formObject.setValue("CRS_LastName",formObject.getValue(FCR_LASTNAME).toString());
				}
				return;
			} else if(controlName.equalsIgnoreCase(CHECKBOX_LASTNAME_EIDA)) {
				toggleCheckbox(controlName,CHECKBOX_LASTNAME_FCR,CHECKBOX_LASTNAME_MANUAL);
				manageManualFields(CHECKBOX_LASTNAME_MANUAL,MANUAL_LASTNAME);
				if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
					formObject.setValue("CRS_LastName",formObject.getValue(EIDA_LASTNAME).toString());
				}
				return;
			} else if(controlName.equalsIgnoreCase(CHECKBOX_LASTNAME_MANUAL)) {
				toggleCheckbox(controlName,CHECKBOX_LASTNAME_FCR,CHECKBOX_LASTNAME_EIDA);
				manageManualFields(CHECKBOX_LASTNAME_MANUAL,MANUAL_LASTNAME);
				if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
					formObject.setValue("CRS_LastName",formObject.getValue(MANUAL_LASTNAME).toString());
				}
				return;
			} else if(controlName.equalsIgnoreCase(RA_CB_OTHERS)) {          
				/*logInfo(RA_CB_OTHERS,"1235 Block");
					if(formObject.getValue(RA_CB_OTHERS).toString().equalsIgnoreCase(TRUE)) {
						logInfo(RA_CB_OTHERS,"IF Block");
						formObject.setStyle("Text17",DISABLE,FALSE);
						formObject.setValue(RA_CB_GEN_TRDNG_CMPNY,FALSE);
						formObject.setValue(RA_CB_PRECIOUS_STONE_DEALER,FALSE);
						formObject.setValue(RA_CB_BULLN_COMMDTY_BROKR,FALSE);
						formObject.setValue(RA_CB_REAL_STATE_BROKR,FALSE);
						formObject.setValue(RA_CB_USD_AUTO_DEALER,FALSE);
					} else {
						logInfo(RA_CB_OTHERS,"ELSE Block");
						formObject.setValue("Text17","");
						formObject.setStyle("Text17",DISABLE,TRUE);
					}*/
				if(formObject.getValue(RA_CB_OTHERS).toString().equalsIgnoreCase("True")) {
					formObject.setValue(RA_CB_GEN_TRDNG_CMPNY,FALSE);
					formObject.setValue(RA_CB_PRECIOUS_STONE_DEALER,FALSE);
					formObject.setValue(RA_CB_BULLN_COMMDTY_BROKR,FALSE);
					formObject.setValue(RA_CB_REAL_STATE_BROKR,FALSE);
					formObject.setValue(RA_CB_USD_AUTO_DEALER,FALSE);
					formObject.setValue(FINANCIAL_BROKERS,FALSE);
					formObject.setValue(NOTARY_PUBLIC,FALSE);
					formObject.setValue(SOCIAL_MEDIA_INFLUNCER,FALSE);
					formObject.setStyle(BN_OTHERS, DISABLE, FALSE);
				} else {
					formObject.setValue(BN_OTHERS,"");
					formObject.setStyle(BN_OTHERS, DISABLE, TRUE);
				}
			} else if(controlName.equalsIgnoreCase(RA_CB_GEN_TRDNG_CMPNY)
					||controlName.equalsIgnoreCase(RA_CB_PRECIOUS_STONE_DEALER)
					||controlName.equalsIgnoreCase(RA_CB_BULLN_COMMDTY_BROKR)
					||controlName.equalsIgnoreCase(RA_CB_REAL_STATE_BROKR)
					||controlName.equalsIgnoreCase(RA_CB_USD_AUTO_DEALER)
					||controlName.equalsIgnoreCase(FINANCIAL_BROKERS)
					||controlName.equalsIgnoreCase(NOTARY_PUBLIC)
					||controlName.equalsIgnoreCase(SOCIAL_MEDIA_INFLUNCER)){
				if(formObject.getValue(RA_CB_OTHERS).toString().equalsIgnoreCase("True")){
					logInfo("CPDMakerThreeStep","Click: INSIDE RA_CB_OTHERS");
					/*formObject.setValue(RA_CB_OTHERS,"False");
						formObject.setValue(RA_OTHERS,"");
						formObject.setStyle(RA_OTHERS,DISABLE,TRUE);*/
					formObject.setValue(RA_CB_OTHERS,FALSE);
					formObject.setValue(BN_OTHERS,"");
					formObject.setStyle(BN_OTHERS, DISABLE, TRUE);
				}
			} else if(controlName.equalsIgnoreCase(IDS_OTH_CB_OTHERS)) {          
				logInfo(IDS_OTH_CB_OTHERS,"1235 Block");
				if(formObject.getValue(IDS_OTH_CB_OTHERS).toString().equalsIgnoreCase(TRUE)) {
					logInfo(IDS_OTH_CB_OTHERS,"Block");
					formObject.setStyle(IDS_BNFT_CB_OTHERS,DISABLE,FALSE);
					formObject.setValue(IDS_CB_SAL_TRANSFER,FALSE);
					formObject.setValue(IDS_CB_MORTGAGES,FALSE);
					formObject.setValue(IDS_CB_INSURANCE,FALSE);
					formObject.setValue(IDS_CB_TRB,FALSE);
					formObject.setValue(IDS_CB_OTHERS,FALSE);
					formObject.setValue(IDS_CB_VVIP,FALSE);
					if(formObject.getValue(PD_CUSTSEGMENT).toString().equalsIgnoreCase("Privilege") || 
							formObject.getValue(PD_CUSTSEGMENT).toString().equalsIgnoreCase("Emirati")) {
						logInfo(IDS_OTH_CB_OTHERS,"Block2");
						formObject.setValue(IDS_PC_CB_TP,FALSE);
						formObject.setValue(IDS_PC_CB_TRAVEL,FALSE);
						formObject.setValue(IDS_PC_CB_SPORT,FALSE);
						formObject.setValue(IDS_PC_CB_SHOPPING,FALSE);
						formObject.setValue(IDS_PC_CB_ENTERTAINMENT,FALSE);					
					} else if(formObject.getValue(PD_CUSTSEGMENT).toString().equalsIgnoreCase("Emirati Excellency")||
							formObject.getValue(PD_CUSTSEGMENT).toString().equalsIgnoreCase("Excellency")||
							formObject.getValue(PD_CUSTSEGMENT).toString().equalsIgnoreCase("Private Clients")) {
						formObject.setValue(IDS_BNFT_CB_TP,FALSE);
					}
				} else {
					logInfo(IDS_OTH_CB_OTHERS,"ELSE Block");
					formObject.setValue(IDS_BNFT_CB_OTHERS,"");
					formObject.setStyle(IDS_BNFT_CB_OTHERS,DISABLE,TRUE);
				}
			} else if((controlName.equalsIgnoreCase(IDS_CB_SAL_TRANSFER)||
					controlName.equalsIgnoreCase(IDS_CB_MORTGAGES)||controlName.equalsIgnoreCase(IDS_CB_INSURANCE)||
					controlName.equalsIgnoreCase(IDS_CB_TRB)||controlName.equalsIgnoreCase(IDS_CB_OTHERS)||
					controlName.equalsIgnoreCase(IDS_CB_VVIP)||controlName.equalsIgnoreCase(IDS_PC_CB_TP)||
					controlName.equalsIgnoreCase(IDS_PC_CB_ENTERTAINMENT)||controlName.equalsIgnoreCase(IDS_PC_CB_SHOPPING)||
					controlName.equalsIgnoreCase(IDS_PC_CB_SPORT)||controlName.equalsIgnoreCase(IDS_PC_CB_TRAVEL)||
					controlName.equalsIgnoreCase(IDS_BNFT_CB_TP))) {
				if(formObject.getValue(IDS_OTH_CB_OTHERS).toString().equalsIgnoreCase(TRUE)) {
					formObject.setValue(IDS_OTH_CB_OTHERS,FALSE);
					formObject.setValue(IDS_BNFT_CB_OTHERS,"");
					formObject.setStyle(IDS_BNFT_CB_OTHERS,DISABLE,TRUE);
				}
			}
			else if(controlName.equalsIgnoreCase(IS_CAT_BENEFIT_OTHER)) {   
				logInfo("onChangeEventCPDMakerFourStep","1235 Block");
				if(formObject.getValue(IS_CAT_BENEFIT_OTHER).toString().equalsIgnoreCase(TRUE)) {
					logInfo("onChangeEventCPDMakerFourStep","IF Block");
					formObject.setStyle(CAT_BENEFIT_OTHER,DISABLE,FALSE);
					String[]fieldName = {IS_SALARY_TRANSFER_CAT_CHANGE,
							IS_MORTAGAGE_CAT_CHANGE,IS_INSURANCE_CAT_CHANGE,IS_TRB_CAT_CHANGE,
							IS_OTHERS_CAT_CHANGE,IS_VVIP};
					String[] fieldValue = {FALSE,FALSE,FALSE,FALSE,FALSE,FALSE};
					setMultipleFieldValues(fieldName,fieldValue);
					if(formObject.getValue(NEW_CUST_SEGMENT).toString().equalsIgnoreCase("Privilege") || 
							formObject.getValue(NEW_CUST_SEGMENT).toString().equalsIgnoreCase("Emirati")) {
						formObject.setValue(IS_PREVILEGE_TP_CAT_CHANGE,FALSE);
						formObject.setValue(IS_ENTERTAINMENT_CAT_CHANGE,FALSE);
						formObject.setValue(IS_SHOPPING_CAT_CHANGE,FALSE);
						formObject.setValue(IS_SPORT_CAT_CHANGE,FALSE);
						formObject.setValue(IS_TRAVEL_CAT_CHANGE,FALSE);					
					} else if(formObject.getValue(NEW_CUST_SEGMENT).toString().equalsIgnoreCase("Emirati Excellency")||
							formObject.getValue(NEW_CUST_SEGMENT).toString().equalsIgnoreCase("Excellency")||
							formObject.getValue(NEW_CUST_SEGMENT).toString().equalsIgnoreCase("Private Clients")) {
						formObject.setValue(IS_EXCELLENCY_TP_CAT_CHANGE,FALSE);
					}
				} else {
					logInfo("onChangeEventCPDMakerFourStep","Else Block");
					formObject.setValue(CAT_BENEFIT_OTHER,"");
					formObject.setStyle(CAT_BENEFIT_OTHER,DISABLE,TRUE);
				}
			} else if((controlName.equalsIgnoreCase(IS_SALARY_TRANSFER_CAT_CHANGE)||
					controlName.equalsIgnoreCase(IS_MORTAGAGE_CAT_CHANGE)||
					controlName.equalsIgnoreCase(IS_INSURANCE_CAT_CHANGE)||
					controlName.equalsIgnoreCase(IS_TRB_CAT_CHANGE)||
					controlName.equalsIgnoreCase(IS_OTHERS_CAT_CHANGE)||
					controlName.equalsIgnoreCase(IS_VVIP)||
					controlName.equalsIgnoreCase(IS_PREVILEGE_TP_CAT_CHANGE)||
					controlName.equalsIgnoreCase(IS_ENTERTAINMENT_CAT_CHANGE)||
					controlName.equalsIgnoreCase(IS_SHOPPING_CAT_CHANGE)||
					controlName.equalsIgnoreCase(IS_SPORT_CAT_CHANGE)||
					controlName.equalsIgnoreCase(IS_TRAVEL_CAT_CHANGE)||
					controlName.equalsIgnoreCase(IS_EXCELLENCY_TP_CAT_CHANGE)||
					controlName.equalsIgnoreCase(IS_SALARY_TRANSFER_CAT_CHANGE))) {
				if(formObject.getValue(IS_CAT_BENEFIT_OTHER).toString().equalsIgnoreCase(TRUE)) {
					String[] fieldValue = {IS_CAT_BENEFIT_OTHER,CAT_BENEFIT_OTHER,CAT_BENEFIT_OTHER};
					String[] fieldName = {FALSE,"",TRUE}; ;
					setMultipleFieldValues(fieldName,fieldValue);
				}
			} else if(controlName.equalsIgnoreCase(NEW_DEL_MODE)) {
				if(formObject.getValue(NEW_DEL_MODE).toString().equalsIgnoreCase("Internal Courier")){
					formObject.clearCombo(CHANNEL_NAME);
					formObject.addItemInCombo(CHANNEL_NAME, "Aramex", "Aramex");
				} else if(formObject.getValue(NEW_DEL_MODE).toString().equalsIgnoreCase("Branch")){
					String branch = "Select HOME_BRANCH from usr_0_home_branch order by 1";
					addDataInComboFromQuery(branch,CHANNEL_NAME);
				} else if(formObject.getValue(NEW_DEL_MODE).toString().equalsIgnoreCase("Excellency")){
					String excellency = "select sourcing_center from usr_0_sourcing_center "
							+ "where sourcing_channel ='Excellency' order by 1";
					addDataInComboFromQuery(excellency,CHANNEL_NAME);
				} else {
					formObject.clearCombo(CHANNEL_NAME);
				}
			} else if(controlName.equalsIgnoreCase(DEL_STATE)) {
				if(formObject.getValue(DEL_STATE).toString().equalsIgnoreCase("Others")) {
					formObject.setValue(DEL_STATE_OTHER, "");
					formObject.setStyle(MANUAL_VISANO,DISABLE,FALSE);
				} else {
					formObject.setValue(DEL_STATE_OTHER, "");
					formObject.setStyle(DEL_STATE_OTHER,DISABLE,TRUE);
				}
			} else if(controlName.equalsIgnoreCase(SOURCE_NAME)) {
				String sReqType=formObject.getValue(REQUEST_TYPE).toString();
				if(sReqType.equalsIgnoreCase("New Account with Category Change") 
						|| sReqType.equalsIgnoreCase("Category Change Only")) {
					formObject.setValue(SOURCE_NAME_CAT_CHANGE,formObject.getValue(SOURCE_NAME).toString());
				}
			} else if(controlName.equalsIgnoreCase(SOURCE_CODE)) {
				String sReqType=formObject.getValue(REQUEST_TYPE).toString();
				if(sReqType.equalsIgnoreCase("New Account with Category Change") 
						|| sReqType.equalsIgnoreCase("Category Change Only")) {
					formObject.setValue(SOURCE_CODE_CAT_CHANGE,formObject.getValue(SOURCE_CODE).toString());
				}
			} else if(controlName.equalsIgnoreCase(MAKER_NAME)) {
				String sReqType=formObject.getValue(REQUEST_TYPE).toString();
				if(sReqType.equalsIgnoreCase("New Account with Category Change") 
						|| sReqType.equalsIgnoreCase("Category Change Only")) {
					formObject.setValue(MAKER_NAME_CAT_CHANGE,formObject.getValue(MAKER_NAME).toString());
				}
			} else if(controlName.equalsIgnoreCase(MAKER_CODE)) {
				String sReqType=formObject.getValue(REQUEST_TYPE).toString();
				if(sReqType.equalsIgnoreCase("New Account with Category Change") 
						|| sReqType.equalsIgnoreCase("Category Change Only")) {
					formObject.setValue(MAKER_CODE_CAT_CHANGE,formObject.getValue(MAKER_CODE).toString());
				}
			}  else if(controlName.equalsIgnoreCase(MANUAL_CNTRY)) {
				manageCityStateManual(formObject.getValue(controlName).toString());
				formObject.setValue(CP_OTHERS,"");
				formObject.setValue(OTHER_CORR_CITY,"");
			} else if(controlName.equalsIgnoreCase(DEL_CNTRY)) {
				String sState = formObject.getValue(DEL_STATE).toString();
				logInfo("onChangeEventCPDMakerFourStep","sState----"+sState);
				if(formObject.getValue(controlName).toString().equalsIgnoreCase("UNITED ARAB EMIRATES")) {
					formObject.clearCombo(DEL_STATE);
					String[] states = UAESTATES.split(",");
					for(int i=0; i < states.length; i++) {
						formObject.addItemInCombo(DEL_STATE,states[i]);
					}					
					formObject.setValue(DEL_STATE,sState);
				} else if(!formObject.getValue(controlName).toString().equalsIgnoreCase("")) {
					formObject.clearCombo(DEL_STATE);
					formObject.addItemInCombo(DEL_STATE,OTHERTHENUAESTATES);
					formObject.setValue(DEL_STATE,sState);
				}
				if(formObject.getValue(DEL_STATE)==null) {
					formObject.setValue(DEL_STATE,"");
				}
			}  else if(controlName.equalsIgnoreCase(CORR_CNTRY)) {
				String sState = formObject.getValue(CORR_STATE).toString();
				String sCity = formObject.getValue(CP_CITY).toString();;
				logInfo("CORR_CNTRY","sCity----"+sCity);
				if(formObject.getValue(controlName).toString().equalsIgnoreCase("UNITED ARAB EMIRATES")) {
					formObject.clearCombo(CORR_STATE);
					String[] states = UAESTATES.split(",");
					for(int i=0; i < states.length; i++) {
						formObject.addItemInCombo(CORR_STATE, states[i], states[i]);							
					}
					formObject.setValue(CORR_STATE,sState);
					formObject.clearCombo(CP_CITY);
					for(int i=0; i < states.length; i++) {
						formObject.addItemInCombo(CP_CITY, states[i], states[i]);							
					}
					formObject.setValue(CP_CITY,sState);
				} else {
					logInfo("CORR_CNTRY","skddd----"+sCity);
					formObject.clearCombo(CORR_STATE);
					formObject.addItemInCombo(CORR_STATE, OTHERTHENUAESTATES, OTHERTHENUAESTATES);
					formObject.setValue(CORR_STATE,sState);
					formObject.clearCombo(CP_CITY);
					formObject.addItemInCombo(CP_CITY, "OTHERS");
					formObject.setValue(CP_CITY,sCity);
				}
			}
			else if(controlName.equalsIgnoreCase(RES_CNTRY)) {
				String sState = formObject.getValue(RES_STATE).toString();
				//			String sCity = formObject.getValue(PA_CITY).toString();
				String sCity = formObject.getValue(RA_CITY).toString();
				logInfo("RES_CNTRY","sCity RES1----"+sCity);
				if(formObject.getValue(controlName).toString().equalsIgnoreCase("UNITED ARAB EMIRATES")) {
					formObject.clearCombo(RES_STATE);
					String[] states = UAESTATES.split(",");
					for(int i=0; i < states.length; i++) {
						formObject.addItemInCombo(RES_STATE, states[i], states[i]);							
					}
					formObject.setValue(RES_STATE,sState);
					//				formObject.clearCombo(PA_CITY);
					formObject.clearCombo(RA_CITY);
					for(int i=0; i < states.length; i++) {
						formObject.addItemInCombo(RA_CITY, states[i], states[i]);							
					}
					//				formObject.setValue(PA_CITY,sCity);
					formObject.setValue(RA_CITY,sCity);
					enableControls(new String[]{RES_MAKANI});
				} else  {
					formObject.clearCombo(RES_STATE);
					formObject.addItemInCombo(RES_STATE, OTHERTHENUAESTATES,OTHERTHENUAESTATES);
					formObject.setValue(RES_STATE,sState);
					formObject.clearCombo(RA_CITY);
					logInfo("RES_CNTRY","Lict COunt---"+getListCount(RA_CITY));
					formObject.addItemInCombo(RA_CITY,"OTHERS");
					formObject.setValue(RA_CITY,sCity);
					disableControls(new String[]{RES_MAKANI});
				}
			} else if(controlName.equalsIgnoreCase(ED_MONTHLY_INCM)) {
				String mnthsalary = formObject.getValue(ED_MONTHLY_INCM).toString();
				logInfo("onChangeEventCPDMakerFourStep","In side salary "+mnthsalary);
				try {
					long mnthslry = Long.parseLong(mnthsalary);
					logInfo("onChangeEventCPDMakerFourStep","In side salary "+mnthslry);
					long finalsalary=mnthslry*12;	
					formObject.setValue(ED_ANNUAL_INC,finalsalary+"");
					logInfo("onChangeEventCPDMakerFourStep","In side salary "+finalsalary);
				} catch(Exception ex) {
					logInfo("onChangeEventCPDMakerFourStep","In side salary code"+ex.getStackTrace());
				}
			} else if(controlName.equalsIgnoreCase(PA_SAMEAS)) {
				if(controlValue.equalsIgnoreCase("Permanent Address")) {
					formObject.setValue(RA_VILLAFLATNO, formObject.getValue(PA_VILLAFLATNO).toString());
					formObject.setValue(RA_STREET, formObject.getValue(PA_STREET).toString());
					formObject.setValue(RES_CNTRY, formObject.getValue(PERM_CNTRY).toString());
					formObject.setValue(RES_STATE, formObject.getValue(PERM_STATE).toString());
					formObject.setValue(RA_BUILDINGNAME, formObject.getValue(PA_BUILDINGNAME).toString());
					formObject.setValue(RA_CITY, formObject.getValue(PA_CITY).toString());
					formObject.setValue(OTHER_RES_CITY, formObject.getValue(OTHER_PERM_CITY).toString());
					formObject.setValue(RA_OTHERS, formObject.getValue(PA_OTHERS).toString());
					formObject.setValue(CONTACT_DETAILS_CITY_OTHERS,formObject.getValue(OTHER_RESIDENTIAL_CITY).toString());
					formObject.setValue(RES_MAKANI,formObject.getValue(PERM_MAKANI).toString());  
				} else if(controlValue.equalsIgnoreCase("Mailing Address")) {
					if(formObject.getValue(CORR_CNTRY).toString().equalsIgnoreCase("UNITED ARAB EMIRATES")) {
						formObject.clearCombo(PERM_STATE);
						formObject.clearCombo(PA_CITY);
						String[] states = UAESTATES.split(",");
						for(int i=0; i < states.length; i++) {
							formObject.addItemInCombo(PERM_STATE, states[i], states[i]);	
							formObject.addItemInCombo(PA_CITY, states[i], states[i]);							
						}
					} else if(!formObject.getValue(CORR_CNTRY).toString().equalsIgnoreCase("")) {
						formObject.clearCombo(PERM_STATE);
						formObject.clearCombo(PA_CITY);
						formObject.addItemInCombo(PERM_STATE, OTHERTHENUAESTATES,OTHERTHENUAESTATES);
						formObject.addItemInCombo(PA_CITY, OTHERTHENUAESTATES,OTHERTHENUAESTATES);
					}
					formObject.setValue(PA_CITY, formObject.getValue(CP_CITY).toString());
					formObject.setValue(OTHER_PERM_CITY, formObject.getValue(OTHER_CORR_CITY).toString());
					formObject.setValue(PA_OTHERS, formObject.getValue(CP_OTHERS).toString());
					formObject.setValue(PERM_CNTRY, formObject.getValue(CORR_CNTRY).toString());
					formObject.setValue(PERM_STATE, formObject.getValue(CORR_STATE).toString());
					formObject.setValue(RA_STREET, formObject.getValue(CP_STREET).toString());
					formObject.setValue(PA_VILLAFLATNO, formObject.getValue(CP_FLOOR).toString());
					formObject.setValue(PA_BUILDINGNAME, formObject.getValue(CP_POBOXNO).toString());
					formObject.setValue(PA_STREET,formObject.getValue(CP_STREET).toString()); 
					formObject.setValue(PERM_MAKANI,formObject.getValue(COR_MAKANI).toString());
				} else {
					String[] clearFields = {PERM_COUNTRY, PERM_STATE, PA_STREET, RA_VILLAFLATNO, PA_CITY, PA_OTHERS,
							PA_BUILDINGNAME, CONTACT_DETAILS_CITY_OTHERS, PERM_MAKANI};
					clearControls(clearFields);
				}
			} else if(controlName.equalsIgnoreCase(RA_SAMEAS)) {			        	 
				if(formObject.getValue(RA_SAMEAS).toString().equalsIgnoreCase("Mailing Address")) {
					logInfo("RA_SAMEAS","inside mailing");
					if(formObject.getValue(CORR_CNTRY).toString().equalsIgnoreCase("UNITED ARAB EMIRATES")) {
						formObject.clearCombo(RES_STATE);
						formObject.clearCombo(RA_CITY);
						String[] states = UAESTATES.split(",");
						for(int i=0; i < states.length; i++) {
							formObject.addItemInCombo(RES_STATE, states[i], states[i]);	
							formObject.addItemInCombo(RA_CITY, states[i], states[i]);							
						}
					} else if(!formObject.getValue(CORR_CNTRY).toString().equalsIgnoreCase("")) {
						formObject.clearCombo(RES_STATE);
						formObject.clearCombo(RA_CITY);
						formObject.addItemInCombo(RES_STATE, OTHERTHENUAESTATES,OTHERTHENUAESTATES);
						formObject.addItemInCombo(RA_CITY, OTHERTHENUAESTATES,OTHERTHENUAESTATES);
					}
					formObject.setValue(RA_CITY, formObject.getValue(CP_CITY).toString());
					formObject.setValue(OTHER_RES_CITY, formObject.getValue(OTHER_CORR_CITY).toString());
					//formObject.setValue(PA_OTHERS, formObject.getValue(CP_OTHERS));
					formObject.setValue(RA_OTHERS, formObject.getValue(CP_OTHERS).toString());
					formObject.setValue(RES_CNTRY, formObject.getValue(CORR_CNTRY).toString());
					formObject.setValue(RES_STATE, formObject.getValue(CORR_STATE).toString());
					formObject.setValue(RA_STREET, formObject.getValue(CP_STREET).toString());
					formObject.setValue(RA_VILLAFLATNO, formObject.getValue(CP_FLOOR).toString());
					formObject.setValue(RA_BUILDINGNAME, formObject.getValue(CP_POBOXNO).toString());
					//formObject.setValue(OTHER_PERM_CITY, formObject.getValue(OTHER_CORR_CITY).toString());
					formObject.setValue(RES_MAKANI, formObject.getValue(COR_MAKANI).toString());	
				} else if(formObject.getValue(RA_SAMEAS).toString().equalsIgnoreCase("Permanent Address")) {
					if(formObject.getValue(PERM_CNTRY).toString().equalsIgnoreCase("UNITED ARAB EMIRATES")) {
						formObject.clearCombo(RES_STATE);
						formObject.clearCombo(RA_CITY);
						String[] states = UAESTATES.split(",");
						for(int i=0; i < states.length; i++) {
							formObject.addItemInCombo(RES_STATE, states[i], states[i]);	
							formObject.addItemInCombo(RA_CITY, states[i], states[i]);							
						}
					} else if(!formObject.getValue(PERM_CNTRY).toString().equalsIgnoreCase("")) {
						formObject.clearCombo(RES_STATE);
						formObject.clearCombo(RA_CITY);
						formObject.addItemInCombo(RES_STATE, OTHERTHENUAESTATES,OTHERTHENUAESTATES);
						formObject.addItemInCombo(RA_CITY, OTHERTHENUAESTATES,OTHERTHENUAESTATES);
					}
					formObject.setValue(RA_VILLAFLATNO, formObject.getValue(PA_VILLAFLATNO).toString());
					formObject.setValue(RA_STREET, formObject.getValue(PA_STREET).toString());
					formObject.setValue(RES_CNTRY, formObject.getValue(PERM_CNTRY).toString());
					formObject.setValue(RES_STATE, formObject.getValue(PERM_STATE).toString());
					formObject.setValue(RA_BUILDINGNAME, formObject.getValue(PA_BUILDINGNAME).toString());
					formObject.setValue(RA_CITY, formObject.getValue(PA_CITY).toString());
					formObject.setValue(RA_OTHERS, formObject.getValue(PA_OTHERS).toString());
					//formObject.setValue(OTHER_RES_CITY, formObject.getValue(OTHER_PERM_CITY));
					//formObject.setValue(OTHER_PERM_CITY, formObject.getValue(OTHER_RESIDENTIAL_CITY).toString());
					//formObject.setValue("Text95", formObject.getValue(PA_OTHERS).toString());
					formObject.setValue(OTHER_RES_CITY,formObject.getValue(OTHER_PERM_CITY).toString());
					formObject.setValue(RES_MAKANI,formObject.getValue(PERM_MAKANI).toString()); 
				}  else {
					formObject.setValue(RES_CNTRY,"");
					formObject.setValue(RES_STATE,"");
					formObject.setValue(RA_BUILDINGNAME, "");
					formObject.setValue(RA_VILLAFLATNO,"");
					formObject.setValue(RA_CITY, "");
					formObject.setValue(RA_OTHERS,"");//
					formObject.setValue(RA_STREET,"");//
					formObject.setValue(OTHER_RES_CITY,""); 
					formObject.setValue(RES_MAKANI,"");
				} 
			} else if(controlName.equalsIgnoreCase(NOM_REQ)) {
				if(formObject.getValue(controlName).toString().equalsIgnoreCase("Yes"))	 {
					formObject.setStyle(EXISTING_NOM_PRSN,DISABLE,FALSE);
					formObject.setValue(EXISTING_NOM_PRSN,"");				
					Frame48_CPD_ENable();
					manageNomineeDetails(formObject.getValue(controlName).toString());
				} else {
					formObject.setStyle(EXISTING_NOM_PRSN,DISABLE,TRUE);
					formObject.setValue(EXISTING_NOM_PRSN,"");	
					Frame48_CPD_Disable();
					manageNomineeDetails(formObject.getValue(controlName).toString());
				}
			} else if(controlName.equalsIgnoreCase(EXISTING_NOM_PRSN)) {
				if(formObject.getValue(controlName).toString().equalsIgnoreCase("Yes")) {
					int iSelectedRow = Integer.parseInt(data);
					String sCustNo = formObject.getTableCellValue(ACC_RELATION,iSelectedRow,0);
					String sql="select nom_name,nom_po_box,nom_add1||nom_add2,nom_land,nom_city,nom_state,nom_others,"
							+ "nom_cntry,nom_fax,nom_zip,nom_email,nom_pref_lang,nom_phone,'',cust_id,nom_id,nom_mob "
							+ "from usr_0_nom_mast where cust_id ='"+sCustNo+"'";
					logInfo("onChangeEventCPDMakerFourStep","ok"+sCustNo+"---------"+sql);
					List<List<String>> recordList = formObject.getDataFromDB(sql);
					loadListView(recordList,COLUMNS_LVW_DEL_PREF,DELIVERY_PREFERENCE);
					logInfo("gotFocusCustInfoDataCPD","Count"+getGridCount(DELIVERY_PREFERENCE));
					if(getGridCount(DELIVERY_PREFERENCE)== 0) {
						sendMessageValuesList(EXISTING_NOM_PRSN, "There Is No Existing Nominee For This Customer.");
						formObject.setValue(EXISTING_NOM_PRSN, "");
						return ;
					}
					Frame48_CPD_ENable();
				} else {
					formObject.clearTable(DELIVERY_PREFERENCE);
				}
			} else if(controlName.equalsIgnoreCase(CORR_STATE)) {
				if(formObject.getValue(CORR_STATE).toString().equalsIgnoreCase("Others") 
						&& formObject.getValue(CHK_CONTACT_DET).toString().equalsIgnoreCase(TRUE)) {
					formObject.setStyle(CP_OTHERS,DISABLE,FALSE);
				} else {
					formObject.setStyle(CP_OTHERS,DISABLE,TRUE);
				}
			} else if(controlName.equalsIgnoreCase(RES_STATE)) {
				if(formObject.getValue(RES_STATE).toString().equalsIgnoreCase("Others") 
						&& formObject.getValue(CHK_CONTACT_DET).toString().equalsIgnoreCase(TRUE)) {
					formObject.setStyle(PA_OTHERS,DISABLE,FALSE);
				} else {
					formObject.setStyle(PA_OTHERS,DISABLE,FALSE);
				}
			} else if(controlName.equalsIgnoreCase(PERM_STATE)) {
				if(formObject.getValue(PERM_STATE).toString().equalsIgnoreCase("Others")
						&& formObject.getValue(CHK_CONTACT_DET).toString().equalsIgnoreCase(TRUE)) {
					formObject.setStyle(RA_OTHERS,DISABLE,FALSE);
				} else {
					formObject.setStyle(RA_OTHERS,DISABLE,FALSE);
				}
			} else if(controlName.equalsIgnoreCase(CRO_DEC)) {
				if(formObject.getValue(controlName).toString().equalsIgnoreCase("Approve")) {
					formObject.setStyle(CRO_REJ_REASON,DISABLE,TRUE);
					formObject.setValue(CRO_REJ_REASON, "");
				} else {
					formObject.setStyle(CRO_REJ_REASON,DISABLE,FALSE);
				}
			} else if(controlName.equalsIgnoreCase(CRO_REJ_REASON)) {
				if(!formObject.getValue(controlName).toString().equalsIgnoreCase("")) {
					if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(CRO_DEC, "Please select user decision first");
						formObject.setValue(CRO_REJ_REASON, "");
					}
				}
			}  else if(controlName.equalsIgnoreCase(PD_CUSTSEGMENT)) {
				manageInternalSection();
				int fieldToFocus=Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
				String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus,9);
				String bnk_relation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus,7);
				if(sAccRelation.equalsIgnoreCase("Existing")) {
					Frame22_CPD_Disable();
				}
				if(bnk_relation.equalsIgnoreCase("Existing")||bnk_relation.equalsIgnoreCase("New")){
					String segment = formObject.getValue(PD_CUSTSEGMENT).toString();
					if(bnk_relation.equalsIgnoreCase("Existing")){
						formObject.setStyle(IDS_OTH_CB_OTHERS,DISABLE,TRUE);
						if(segment.equalsIgnoreCase("Aspire")||segment.equalsIgnoreCase("Privilege")
								||segment.equalsIgnoreCase("Excellency")||segment.equalsIgnoreCase("Private Clients")
								||segment.equalsIgnoreCase("Simplylife") ||segment.equalsIgnoreCase("Emirati")
								||segment.equalsIgnoreCase("Emirati Excellency")){
							formObject.setStyle(RM_CODE,DISABLE,TRUE);
						}					
					}
					if(bnk_relation.equalsIgnoreCase("New"))  {	
						formObject.setStyle(PRO_CODE,DISABLE,FALSE);
						formObject.setStyle(EXCELLENCY_CNTR,DISABLE,FALSE);
						if(segment.equalsIgnoreCase("Aspire")) {
							getPromoCode("Aspire");						
							visibleOnSegmentBasis("Aspire");			
						}
						else if(segment.equalsIgnoreCase("Privilege") || segment.equalsIgnoreCase("Emirati")) {
							getPromoCode(segment);	
							visibleOnSegmentBasis("Privilege");		
						}
						else if(segment.equalsIgnoreCase("Excellency") || segment.equalsIgnoreCase("Emirati Excellency")) {
							getPromoCode(segment);	
							visibleOnSegmentBasis("Excellency");
						}
						else if(segment.equalsIgnoreCase("Signatory")) {
							if(!(formObject.getValue("AO_ACC_RELATION.ACC_RELATION").toString().equalsIgnoreCase("AUS")
									||formObject.getValue("AO_ACC_RELATION.ACC_RELATION").toString()
									.equalsIgnoreCase("POA"))) {
								sendMessageValuesList("", "Signatory not allowed. Please select another type.");
								formObject.setValue(PD_CUSTSEGMENT,"");
								formObject.setValue(RM_CODE,"");
								formObject.setValue(RM_NAME,"");
							} else {
								getPromoCode("Signatory");				
								visibleOnSegmentBasis("Signatory");		
							}
						} else if(segment.equalsIgnoreCase("Private Clients")) 	{
							getPromoCode("Private Clients");				
							visibleOnSegmentBasis("Private Clients");	
						}
						else if(segment.equalsIgnoreCase("Simplylife")) {
							getPromoCode("Simplylife");	 
							visibleOnSegmentBasis("Simplylife"); 
						}
						if(!segment.equalsIgnoreCase("")) {
							formObject.setStyle(IDS_OTH_CB_OTHERS,DISABLE,FALSE);
							formObject.setStyle(IDS_BNFT_CB_OTHERS,DISABLE,TRUE);
							formObject.setValue(IDS_OTH_CB_OTHERS,FALSE);
							formObject.setValue(IDS_BNFT_CB_OTHERS,"");
						} else {
							formObject.setStyle(IDS_OTH_CB_OTHERS,DISABLE,TRUE);
							formObject.setStyle(IDS_BNFT_CB_OTHERS,DISABLE,TRUE);
							formObject.setValue(IDS_OTH_CB_OTHERS,FALSE);
							formObject.setValue(IDS_BNFT_CB_OTHERS,"");
							showControls(new String[]{IDS_PC_CB_TP,IDS_PC_CB_TRAVEL,IDS_PC_CB_SPORT,IDS_PC_CB_SHOPPING,
									IDS_PC_CB_ENTERTAINMENT,
									IDS_CB_SAL_TRANSFER,IDS_CB_MORTGAGES,IDS_CB_INSURANCE,IDS_CB_TRB,IDS_CB_OTHERS,
									IDS_CB_VVIP,IDS_BNFT_CB_TP});
							formObject.setStyle(PRO_CODE,DISABLE,TRUE);
							formObject.setStyle(EXCELLENCY_CNTR,DISABLE,TRUE);
						}
					}
					if(bnk_relation.equalsIgnoreCase("Existing")) {
						String seg=formObject.getValue(PD_CUSTSEGMENT).toString();
						segmentSelectionForExistingcustomer(seg);
					}
				}
			} else if(controlName.equalsIgnoreCase(CHECKBOX_SELECTALL_FCR)) {
				toggleCheckbox(controlName,CHECKBOX_SELECTALL_EIDA ,CHECKBOX_SELECTALL_MANUAL);
				manageFCRCheckBoxes();
				manageEIDACheckBoxes();
				manageManualCheckBoxes();
				setManualFieldsEnable();
				setManualFieldsLock();
				if(formObject.getValue(CHECKBOX_SELECTALL_FCR).toString().equalsIgnoreCase("true")) {
					setFCRDataInBelowFields();
				}
			} else if(controlName.equalsIgnoreCase(CHECKBOX_PREFIX_FCR)) {
				toggleCheckbox(controlName,CHECKBOX_PREFIX_EIDA,CHECKBOX_PREFIX_MANUAL);
				manageManualFields(CHECKBOX_PREFIX_MANUAL,MANUAL_PREFIX);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(CUST_PREFIX,formObject.getValue(FCR_PREFIX).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_FULLNAME_FCR)) {
				toggleCheckbox(controlName,CHECKBOX_FULLNAME_EIDA,CHECKBOX_FULLNAME_MANUAL);
				manageManualFields(CHECKBOX_FULLNAME_MANUAL,MANUAL_NAME);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(PD_FULLNAME,formObject.getValue(FCR_NAME).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_SHORTNAME_FCR)) {
				toggleCheckbox(controlName,CHECKBOX_SHORTNAME_EIDA,CHECKBOX_SHORTNAME_MANUAL);
				manageManualFields(CHECKBOX_SHORTNAME_MANUAL,MANUAL_SHORTNAME);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(PD_SHORTNAME,formObject.getValue(FCR_SHORTNAME).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_DOB_FCR)) {
				toggleCheckbox(controlName,CHECKBOX_DOB_EIDA,CHECKBOX_DOB_MANUAL);
				manageManualFields(CHECKBOX_DOB_MANUAL,MANUAL_DOB);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(PD_DOB,formObject.getValue(FCR_DOB).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_NO_FCR)) {
				toggleCheckbox(controlName,CHECKBOX_PASSPORT_NO_EIDA,CHECKBOX_PASSPORT_NO_MANUAL);
				manageManualFields(CHECKBOX_PASSPORT_NO_MANUAL,MANUAL_PASSPORTNO);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(HD_PASSPORT_NO,formObject.getValue(FCR_PASSPORTNO).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_PASS_ISS_DT_FCR)) {
				toggleCheckbox(controlName,CHECKBOX_PASS_ISS_DT_EIDA,CHECKBOX_PASS_ISS_DT_MANUAL);
				manageManualFields(CHECKBOX_PASS_ISS_DT_MANUAL,MANUAL_PASSPORTISSDATE);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(HD_PASS_ISS_DATE,formObject.getValue(FCR_PASSPORTISSDATE).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_PASS_EXP_DT_FCR)) {
				toggleCheckbox(controlName,CHECKBOX_PASS_EXP_DT_EIDA,CHECKBOX_PASS_EXP_DT_MANUAL);
				manageManualFields(CHECKBOX_PASS_EXP_DT_MANUAL,MANUAL_PASSPORTEXPDATE);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(HD_PASS_EXP_DATE,formObject.getValue(FCR_PASSPORTEXPDATE).toString());
				}
	
			} else if(controlName.equalsIgnoreCase("CHECK17")) {
				toggleCheckbox(controlName,"CHECK62","CHECK39");
				manageManualFields("CHECK39",PASSPORTEXPPLACE_MANUAL);
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_NO_FCR)) {
				toggleCheckbox(controlName,CHECKBOX_VISA_NO_EIDA,CHECKBOX_VISA_NO_MANUAL);
				manageManualFields(CHECKBOX_VISA_NO_MANUAL,MANUAL_VISANO);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(HD_VISA_NO,formObject.getValue(FCR_VISANO).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_ISSUE_DATE_FCR)) {
				toggleCheckbox(controlName,CHECKBOX_VISA_ISSUE_DATE_EIDA,CHECKBOX_VISA_ISSUE_DATE_MANUAL);
				manageManualFields(CHECKBOX_VISA_ISSUE_DATE_MANUAL,MANUAL_VISAISSDATE);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(HD_VISA_ISSUE_DATE,formObject.getValue(FCR_VISAISSDATE).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_EXPIRY_DATE_FCR)) {
				toggleCheckbox(controlName,CHECKBOX_VISA_EXPIRY_DATE_EIDA,CHECKBOX_VISA_EXPIRY_DATE_MANUAL);
				manageManualFields(CHECKBOX_VISA_EXPIRY_DATE_MANUAL,MANUAL_VISAEXPDATE);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(HD_EXP_DATE,formObject.getValue(FCR_VISAEXPDATE).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_NATIONALITY_FCR)) {
				toggleCheckbox(controlName,CHECKBOX_NATIONALITY_EIDA,CHECKBOX_NATIONALITY_MANUAL);
				manageManualFields(CHECKBOX_NATIONALITY_MANUAL,MANUAL_NATIONALITY);
				autoSetFatca(controlName);
				manageResidencyAndVisaStatus(RA_IS_UAE_RESIDENT,"COMBO34");
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(CUST_NATIONALITY,formObject.getValue(FCR_NATIONALITY).toString());
				}
				if("UNITED ARAB EMIRATES".equalsIgnoreCase(formObject.getValue(CUST_NATIONALITY).toString())) {
					formObject.setStyle(COR_MAKANI,DISABLE,FALSE);
					formObject.setStyle(COR_MAKANI,DISABLE,FALSE);
					formObject.setStyle(COR_MAKANI,DISABLE,FALSE);
				} else {
					formObject.setStyle(COR_MAKANI,DISABLE,TRUE);
					formObject.setStyle(COR_MAKANI,DISABLE,TRUE);
					formObject.setStyle(COR_MAKANI,DISABLE,TRUE);
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_MOTHERSNAME_FCR)) {
				toggleCheckbox(controlName,CHECKBOX_MOTHERSNAME_EIDA,CHECKBOX_MOTHERSNAME_MANUAL);
				manageManualFields(CHECKBOX_MOTHERSNAME_MANUAL,MANUAL_MOTHERNAME);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(PD_MOTHERMAIDENNAME,formObject.getValue(FCR_MOTHERSNAME).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_EIDANO_FCR)) {
				toggleCheckbox(controlName,CHECKBOX_EIDANO_EIDA,CHECKBOX_EIDANO_MANUAL);
				manageManualFields(CHECKBOX_EIDANO_MANUAL,MANUAL_EIDANO);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(PD_EIDANO,formObject.getValue(FCR_EIDANO).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_CORR_POB_FCR)) {
				toggleCheckbox(controlName,CHECKBOX_CORR_POB_EIDA,CHECKBOX_CORR_POB_MANUAL);
				manageManualFields(CHECKBOX_CORR_POB_MANUAL,MANUAL_ADDRESS);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(CP_POBOXNO,formObject.getValue(FCR_ADDRESS).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_CITY_FCR)) {
				toggleCheckbox(controlName,CHECKBOX_CITY_EIDA,CHECKBOX_CITY_MANUAL);
				manageManualFields(CHECKBOX_CITY_MANUAL,MANUAL_CITY);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(CP_CITY,formObject.getValue(FCR_CITY).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_STATE_FCR)) {
				toggleCheckbox(controlName,CHECKBOX_STATE_EIDA,CHECKBOX_STATE_MANUAL);
				manageManualFields(CHECKBOX_STATE_MANUAL,MANUAL_STATE);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(CORR_STATE,formObject.getValue(FCR_STATE).toString());
					if(formObject.getValue(MANUAL_STATE).toString().equalsIgnoreCase("OTHERS")) {
						formObject.setStyle(CP_OTHERS,DISABLE,FALSE);
					}
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_CNTRY_OF_CORR_FCR)) {
				toggleCheckbox(controlName,CHECKBOX_CNTRY_OF_CORR_EIDA,CHECKBOX_CNTRY_OF_CORR_MANUAL);
				manageManualFields(CHECKBOX_CNTRY_OF_CORR_MANUAL,MANUAL_CNTRY);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(CORR_CNTRY,formObject.getValue(FCR_CNTRY).toString());
	
					if(formObject.getValue("CNTRY_FCR").toString().equalsIgnoreCase("UNITED ARAB EMIRATES"))
						formObject.setStyle(COR_MAKANI,DISABLE,FALSE);
					else
						formObject.setStyle(COR_MAKANI,DISABLE,TRUE);
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_COUNTRY_PER_RES_FCR)) {
				toggleCheckbox(controlName,CHECKBOX_COUNTRY_PER_RES_EIDA,CHECKBOX_COUNTRY_PER_RES_MANUAL);
				manageManualFields(CHECKBOX_COUNTRY_PER_RES_MANUAL,MANUAL_PER_CNTRY);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(RES_CNTRY,formObject.getValue(FCR_PER_CNTRY).toString());
	
					if(formObject.getValue(FCR_PER_CNTRY).toString().equalsIgnoreCase("UNITED ARAB EMIRATES"))
						formObject.setStyle(CONTACT_DETAILS_MAKANI_NO,DISABLE,FALSE);
					else
						formObject.setStyle(CONTACT_DETAILS_MAKANI_NO,DISABLE,TRUE);
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_TELE_RES_FCR)) {
				toggleCheckbox(controlName,CHECKBOX_TELE_RES_EIDA,CHECKBOX_TELE_RES_MANUAL);
				manageManualFields(CHECKBOX_TELE_RES_MANUAL,MANUAL_PH);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(CP_PHONENO,formObject.getValue(FCR_PH).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_TELE_MOB_FCR)) {
				toggleCheckbox(controlName,CHECKBOX_TELE_MOB_EIDA,CHECKBOX_TELE_MOB_MANUAL);
				manageManualFields(CHECKBOX_TELE_MOB_MANUAL,MANUAL_MOBILE);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(CP_MOBILE,formObject.getValue(FCR_MOBILE).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_EMAIL_FCR)) {
				toggleCheckbox(controlName,CHECKBOX_EMAIL_EIDA,CHECKBOX_EMAIL_MANUAL);
				manageManualFields(CHECKBOX_EMAIL_MANUAL,MANUAL_EMAIL);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(CP_EMAIL,formObject.getValue(FCR_EMAIL).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_PROFESSION_FCR)) {
				toggleCheckbox(controlName,CHECKBOX_PROFESSION_EIDA,CHECKBOX_PROFESSION_MANUAL);
				manageManualFields(CHECKBOX_PROFESSION_MANUAL,MANUAL_PROFESSION,BTNPROFESSION);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(PROF_CODE,formObject.getValue(FCR_PROFESSION).toString());
					formObject.setValue(PROFESION,formObject.getValue(FCR_PROFESSION).toString());
				}
	
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_GENDER_FCR)) {
				toggleCheckbox(controlName,CHECKBOX_GENDER_EIDA,CHECKBOX_GENDER_MANUAL);
				manageManualFields(CHECKBOX_GENDER_MANUAL,MANUAL_GENDER);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(CUST_GENDER,formObject.getValue(FCR_GENDER).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_EMP_NAME_FCR)) {
				toggleCheckbox(controlName,CHECKBOX_EMP_NAME_EIDA,CHECKBOX_EMP_NAME_MANUAL);
				manageManualFields(CHECKBOX_EMP_NAME_MANUAL,MANUAL_EMPLYR_NAME,BTNEMLOYERNAME);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(EMPNAME,formObject.getValue(FCR_EMPLYR_NAME).toString());//Change For Bug Id :13281
					List<List<String>> sOutput = formObject.getDataFromDB("SELECT CD_STATUS FROM USR_0_EMPLOYER_MASTER WHERE EMP_NAME ='"+formObject.getValue("EMPLYR_NAME_FCR")+"'");
					formObject.setValue(ED_CB_TML,"False");
					formObject.setValue(ED_CB_NON_TML,"False");
					if(sOutput.get(0).get(0).equalsIgnoreCase("1") ||sOutput.get(0).get(0).equalsIgnoreCase("2")) {
						formObject.setValue(ED_CB_TML,"True");
					} else {
						formObject.setValue(ED_CB_NON_TML,"True");
					}
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKFCR)) {
				toggleCheckbox(controlName,CHECKEIDA,CHECKMANUAL);
				manageManualFields(CHECKMANUAL,MANUAL_RESIDENT);
				manageResidencyAndVisaStatus(RA_IS_UAE_RESIDENT,"COMBO34");
				if(controlValue.equalsIgnoreCase("True")) {
					//				formObject.setValue(PERM_CNTRY, formObject.getValue(FCR_RESIDENT).toString());
					formObject.setValue(RES_CNTRY, formObject.getValue(FCR_RESIDENT).toString());
					if(formObject.getValue(FCR_RESIDENT).toString().equalsIgnoreCase("UNITED ARAB EMIRATES"))
						formObject.setStyle(RES_MAKANI,DISABLE,FALSE);
					else
						formObject.setStyle(RES_MAKANI,DISABLE,TRUE);
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_SELECTALL_EIDA )) {
				toggleCheckbox(controlName,CHECKBOX_SELECTALL_FCR,CHECKBOX_SELECTALL_MANUAL);
				manageFCRCheckBoxes();
				manageEIDACheckBoxes();
				manageManualCheckBoxes();
				setManualFieldsEnable();
				setManualFieldsLock();
				if(formObject.getValue(CHECKBOX_SELECTALL_EIDA ).toString().equalsIgnoreCase("true")) {
					setEIDADataInBelowFields();
				}
			} else if(controlName.equalsIgnoreCase(CHECKBOX_PREFIX_EIDA   )) {
				toggleCheckbox(controlName,CHECKBOX_PREFIX_FCR,CHECKBOX_PREFIX_MANUAL);
				manageManualFields(CHECKBOX_PREFIX_MANUAL,MANUAL_PREFIX);
	
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(CUST_PREFIX,formObject.getValue(EIDA_PREFIX).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_FULLNAME_EIDA)) {
				toggleCheckbox(controlName,CHECKBOX_FULLNAME_FCR,CHECKBOX_FULLNAME_MANUAL);
				manageManualFields(CHECKBOX_FULLNAME_MANUAL,MANUAL_NAME);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(PD_FULLNAME,formObject.getValue(EIDA_NAME).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_SHORTNAME_EIDA)) {
				toggleCheckbox(controlName,CHECKBOX_SHORTNAME_FCR,CHECKBOX_SHORTNAME_MANUAL);
				manageManualFields(CHECKBOX_SHORTNAME_MANUAL,MANUAL_SHORTNAME);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(PD_SHORTNAME,formObject.getValue(EIDA_SHORTNAME).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_DOB_EIDA)) {
				toggleCheckbox(controlName,CHECKBOX_DOB_FCR,CHECKBOX_DOB_MANUAL);
				manageManualFields(CHECKBOX_DOB_MANUAL,MANUAL_DOB);
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_NO_EIDA)) {
				toggleCheckbox(controlName,CHECKBOX_PASSPORT_NO_FCR,CHECKBOX_PASSPORT_NO_MANUAL);
				manageManualFields(CHECKBOX_PASSPORT_NO_MANUAL,MANUAL_PASSPORTNO);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(HD_PASSPORT_NO,formObject.getValue(EIDA_PASSPORTNO).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_PASS_ISS_DT_EIDA)) {
				toggleCheckbox(controlName,CHECKBOX_PASS_ISS_DT_FCR,CHECKBOX_PASS_ISS_DT_MANUAL);	
				manageManualFields(CHECKBOX_PASS_ISS_DT_MANUAL,MANUAL_PASSPORTISSDATE);	
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(HD_PASS_ISS_DATE,formObject.getValue(EIDA_PASSPORTISSDATE).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_PASS_EXP_DT_EIDA)) {
				toggleCheckbox(controlName,CHECKBOX_PASS_EXP_DT_FCR,CHECKBOX_PASS_EXP_DT_MANUAL);
				manageManualFields(CHECKBOX_PASS_EXP_DT_MANUAL,MANUAL_PASSPORTEXPDATE);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(HD_PASS_EXP_DATE,formObject.getValue(EIDA_PASSPORTEXPDATE).toString());
				}
	
			} else if(controlName.equalsIgnoreCase("CHECK62")) {
				toggleCheckbox(controlName,"CHECK17","CHECK39");
				manageManualFields("CHECK39",PASSPORTEXPPLACE_MANUAL);
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_NO_EIDA)) {
				toggleCheckbox(controlName,CHECKBOX_VISA_NO_FCR,CHECKBOX_VISA_NO_MANUAL);
				manageManualFields(CHECKBOX_VISA_NO_MANUAL,MANUAL_VISANO);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(HD_VISA_NO,formObject.getValue(EIDA_VISANO).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_ISSUE_DATE_EIDA)) {
				toggleCheckbox(controlName,CHECKBOX_VISA_ISSUE_DATE_FCR,CHECKBOX_VISA_ISSUE_DATE_MANUAL);
				manageManualFields(CHECKBOX_VISA_ISSUE_DATE_MANUAL,MANUAL_VISAISSDATE);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(HD_VISA_ISSUE_DATE,formObject.getValue(EIDA_VISAISSDATE).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_EXPIRY_DATE_EIDA)) {
				toggleCheckbox(controlName,CHECKBOX_VISA_EXPIRY_DATE_FCR,CHECKBOX_VISA_EXPIRY_DATE_MANUAL);
				manageManualFields(CHECKBOX_VISA_EXPIRY_DATE_MANUAL,MANUAL_VISAEXPDATE);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(HD_EXP_DATE,formObject.getValue(EIDA_VISAEXPDATE).toString());
				}
	
			}
			if(controlName.equalsIgnoreCase(CHECKBOX_NATIONALITY_EIDA)) {
				toggleCheckbox(controlName,CHECKBOX_NATIONALITY_FCR,CHECKBOX_NATIONALITY_MANUAL);
				manageManualFields(CHECKBOX_NATIONALITY_MANUAL,MANUAL_NATIONALITY);
				autoSetFatca(controlName);
				manageResidencyAndVisaStatus(RA_IS_UAE_RESIDENT,"COMBO34");
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(CUST_NATIONALITY,formObject.getValue(EIDA_NATIONALITY).toString());
				}
				if("UNITED ARAB EMIRATES".equalsIgnoreCase(formObject.getValue(CUST_NATIONALITY).toString())) {
					formObject.setStyle(COR_MAKANI,DISABLE,FALSE);
					formObject.setStyle(CONTACT_DETAILS_MAKANI_NO,DISABLE,FALSE);
					formObject.setStyle(RES_MAKANI,DISABLE,FALSE);
				} else {
					formObject.setStyle(COR_MAKANI,DISABLE,TRUE);
					formObject.setStyle(CONTACT_DETAILS_MAKANI_NO,DISABLE,TRUE);
					formObject.setStyle(RES_MAKANI,DISABLE,TRUE);
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_MOTHERSNAME_EIDA)) {
				toggleCheckbox(controlName,CHECKBOX_MOTHERSNAME_FCR,CHECKBOX_MOTHERSNAME_MANUAL);
				manageManualFields(CHECKBOX_MOTHERSNAME_MANUAL,MANUAL_MOTHERNAME);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(PD_MOTHERMAIDENNAME,formObject.getValue(EIDA_MOTHERNAME).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_EIDANO_EIDA)) {
				toggleCheckbox(controlName,CHECKBOX_EIDANO_FCR,CHECKBOX_EIDANO_MANUAL);
				manageManualFields(CHECKBOX_EIDANO_MANUAL,MANUAL_EIDANO);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(PD_EIDANO,formObject.getValue(EIDA_EIDANO).toString());
				}
	
			}
			if(controlName.equalsIgnoreCase(CHECKBOX_CORR_POB_EIDA))
			{
				toggleCheckbox(controlName,CHECKBOX_CORR_POB_FCR,CHECKBOX_CORR_POB_MANUAL);
				manageManualFields(CHECKBOX_CORR_POB_MANUAL,MANUAL_ADDRESS);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(CP_POBOXNO,formObject.getValue(EIDA_ADDRESS).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_CITY_EIDA)) {
				toggleCheckbox(controlName,CHECKBOX_CITY_FCR,CHECKBOX_CITY_MANUAL);
				manageManualFields(CHECKBOX_CITY_MANUAL,MANUAL_CITY);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(CP_CITY,formObject.getValue(EIDA_CITY).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_STATE_EIDA)) {
				toggleCheckbox(controlName,CHECKBOX_STATE_FCR,CHECKBOX_STATE_MANUAL);
				manageManualFields(CHECKBOX_STATE_MANUAL,MANUAL_STATE);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(CORR_STATE,formObject.getValue(EIDA_STATE).toString());
					if(formObject.getValue("STATE_EIDA").toString().equalsIgnoreCase("OTHERS")) {
						formObject.setStyle(CP_OTHERS,DISABLE,FALSE);
					}
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_CNTRY_OF_CORR_EIDA)) {
				toggleCheckbox(controlName,CHECKBOX_CNTRY_OF_CORR_FCR,CHECKBOX_CNTRY_OF_CORR_MANUAL);
				manageManualFields(CHECKBOX_CNTRY_OF_CORR_MANUAL,MANUAL_CNTRY);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(CORR_CNTRY,formObject.getValue(EIDA_CNTRY).toString());
					if(formObject.getValue("CNTRY_EIDA").toString().equalsIgnoreCase("UNITED ARAB EMIRATES"))
						formObject.setStyle(COR_MAKANI,DISABLE,FALSE);
					else
						formObject.setStyle(COR_MAKANI,DISABLE,TRUE);
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_COUNTRY_PER_RES_EIDA)) {
				toggleCheckbox(controlName,CHECKBOX_COUNTRY_PER_RES_FCR,CHECKBOX_COUNTRY_PER_RES_MANUAL);
				manageManualFields(CHECKBOX_COUNTRY_PER_RES_MANUAL,MANUAL_PER_CNTRY);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(RES_CNTRY,formObject.getValue(EIDA_PER_CNTRY).toString());
					if(formObject.getValue(EIDA_PER_CNTRY).toString().equalsIgnoreCase("UNITED ARAB EMIRATES"))
						formObject.setStyle(CONTACT_DETAILS_MAKANI_NO,DISABLE,FALSE);
					else
						formObject.setStyle(CONTACT_DETAILS_MAKANI_NO,DISABLE,TRUE);
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_TELE_RES_EIDA)) {
				toggleCheckbox(controlName,CHECKBOX_TELE_RES_FCR,CHECKBOX_TELE_RES_MANUAL);
				manageManualFields(CHECKBOX_TELE_RES_MANUAL,MANUAL_PH);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(CP_PHONENO,formObject.getValue(EIDA_PH).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_TELE_MOB_EIDA)) {
				toggleCheckbox(controlName,CHECKBOX_TELE_MOB_FCR,CHECKBOX_TELE_MOB_MANUAL);
				manageManualFields(CHECKBOX_TELE_MOB_MANUAL,MANUAL_MOBILE);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(CP_MOBILE,formObject.getValue(EIDA_MOBILE).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_EMAIL_EIDA)) {
				toggleCheckbox(controlName,CHECKBOX_EMAIL_FCR,CHECKBOX_EMAIL_MANUAL);
				manageManualFields(CHECKBOX_EMAIL_MANUAL,MANUAL_EMAIL);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(CP_EMAIL,formObject.getValue(EIDA_EMAIL).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_PROFESSION_EIDA)) {
				toggleCheckbox(controlName,CHECKBOX_PROFESSION_FCR,CHECKBOX_PROFESSION_MANUAL);
				manageManualFields(CHECKBOX_PROFESSION_MANUAL,MANUAL_PROFESSION,BTNPROFESSION);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(PROF_CODE,formObject.getValue(EIDA_PROFESSION).toString());
					formObject.setValue(PROFESION,formObject.getValue(EIDA_PROFESSION).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_GENDER_EIDA)) {
				toggleCheckbox(controlName,CHECKBOX_GENDER_FCR,CHECKBOX_GENDER_MANUAL);
				manageManualFields(CHECKBOX_GENDER_MANUAL,MANUAL_GENDER);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(CUST_GENDER,formObject.getValue(EIDA_GENDER).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_EMP_NAME_EIDA)) {
				toggleCheckbox(controlName,CHECKBOX_EMP_NAME_FCR,CHECKBOX_EMP_NAME_MANUAL);
				manageManualFields(CHECKBOX_EMP_NAME_MANUAL,MANUAL_EMPLYR_NAME,BTNEMLOYERNAME);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(EMPNAME,formObject.getValue(EIDA_EMPLYR_NAME).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKEIDA)) {
				toggleCheckbox(controlName,CHECKFCR,CHECKMANUAL);
				manageManualFields(CHECKMANUAL,MANUAL_RESIDENT);
				manageResidencyAndVisaStatus(RA_IS_UAE_RESIDENT,"COMBO34");
				if(controlValue.equalsIgnoreCase("True")) {
					//				formObject.setValue(PERM_CNTRY, formObject.getValue(EIDA_RESIDENT).toString());
					formObject.setValue(RES_CNTRY, formObject.getValue(EIDA_RESIDENT).toString());
					if(formObject.getValue(EIDA_RESIDENT).toString().equalsIgnoreCase("UNITED ARAB EMIRATES"))
						formObject.setStyle(RES_MAKANI,DISABLE,FALSE);
					else
						formObject.setStyle(RES_MAKANI,DISABLE,TRUE);
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_SELECTALL_MANUAL)) {  //eida ended  // manual started
				toggleCheckbox(controlName,CHECKBOX_SELECTALL_FCR,CHECKBOX_SELECTALL_EIDA );
				manageFCRCheckBoxes();
				manageEIDACheckBoxes();
				setManualFieldsEnable();
				manageManualCheckBoxes();
				if(formObject.getValue(CHECKBOX_SELECTALL_MANUAL).toString().equalsIgnoreCase("true")) {
					setManualDataInBelowFields();
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_PREFIX_MANUAL)) {
				toggleCheckbox(controlName,CHECKBOX_PREFIX_FCR,CHECKBOX_PREFIX_EIDA   );
				manageManualFields(CHECKBOX_PREFIX_MANUAL,MANUAL_PREFIX);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(CUST_PREFIX,formObject.getValue(MANUAL_PREFIX).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_FULLNAME_MANUAL)) {
				toggleCheckbox(controlName,CHECKBOX_FULLNAME_FCR,CHECKBOX_FULLNAME_EIDA);
				manageManualFields(CHECKBOX_FULLNAME_MANUAL,MANUAL_NAME);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(PD_FULLNAME,formObject.getValue(MANUAL_NAME).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_SHORTNAME_MANUAL)) {
				toggleCheckbox(controlName,CHECKBOX_SHORTNAME_FCR,CHECKBOX_SHORTNAME_EIDA);
				manageManualFields(CHECKBOX_SHORTNAME_MANUAL,MANUAL_SHORTNAME);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(PD_SHORTNAME,formObject.getValue(MANUAL_SHORTNAME).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_DOB_MANUAL)) {
				toggleCheckbox(controlName,CHECKBOX_DOB_FCR,CHECKBOX_DOB_EIDA);
				manageManualFields(CHECKBOX_DOB_MANUAL,MANUAL_DOB);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(PD_DOB,formObject.getValue(MANUAL_DOB).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_NO_MANUAL)) {
				toggleCheckbox(controlName,CHECKBOX_PASSPORT_NO_FCR,CHECKBOX_PASSPORT_NO_EIDA);
				manageManualFields(CHECKBOX_PASSPORT_NO_MANUAL,MANUAL_PASSPORTNO);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(HD_PASSPORT_NO,formObject.getValue(MANUAL_PASSPORTNO).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_PASS_ISS_DT_MANUAL)) {
				toggleCheckbox(controlName,CHECKBOX_PASS_ISS_DT_FCR,CHECKBOX_PASS_ISS_DT_EIDA);	
				manageManualFields(CHECKBOX_PASS_ISS_DT_MANUAL,MANUAL_PASSPORTISSDATE);
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_PASS_EXP_DT_MANUAL)) {
				toggleCheckbox(controlName,CHECKBOX_PASS_EXP_DT_FCR,CHECKBOX_PASS_EXP_DT_EIDA);
				manageManualFields(CHECKBOX_PASS_EXP_DT_MANUAL,MANUAL_PASSPORTEXPDATE);
	
			} else if(controlName.equalsIgnoreCase("CHECK39")) {
				toggleCheckbox(controlName,"CHECK17","CHECK62");
				manageManualFields("CHECK39",PASSPORTEXPPLACE_MANUAL);
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_NO_MANUAL)) {
				toggleCheckbox(controlName,CHECKBOX_VISA_NO_FCR,CHECKBOX_VISA_NO_EIDA);
				manageManualFields(CHECKBOX_VISA_NO_MANUAL,MANUAL_VISANO);
				logInfo("","after opening form again ");
				if(formObject.getValue(CHECKBOX_VISA_NO_MANUAL).toString().equalsIgnoreCase("True") && formObject.getValue(MANUAL_VISANO).toString().equalsIgnoreCase("MDSA")) {
					formObject.setStyle(MANUAL_VISANO,DISABLE,FALSE);
				}
				if(formObject.getValue(CHECKBOX_VISA_NO_MANUAL).toString().equalsIgnoreCase("True") && formObject.getValue(MANUAL_VISANO).toString().equalsIgnoreCase("")) {
					formObject.setStyle(MANUAL_VISANO,DISABLE,FALSE);
				}
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(HD_VISA_NO,formObject.getValue(MANUAL_VISANO).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_ISSUE_DATE_MANUAL)) {
				toggleCheckbox(controlName,CHECKBOX_VISA_ISSUE_DATE_FCR,CHECKBOX_VISA_ISSUE_DATE_EIDA);
				manageManualFields(CHECKBOX_VISA_ISSUE_DATE_MANUAL,MANUAL_VISAISSDATE);
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_EXPIRY_DATE_MANUAL)) {
				toggleCheckbox(controlName,CHECKBOX_VISA_EXPIRY_DATE_FCR,CHECKBOX_VISA_EXPIRY_DATE_EIDA);
				manageManualFields(CHECKBOX_VISA_EXPIRY_DATE_MANUAL,MANUAL_VISAEXPDATE);
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_NATIONALITY_MANUAL)) {
				toggleCheckbox(controlName,CHECKBOX_NATIONALITY_FCR,CHECKBOX_NATIONALITY_EIDA);
				manageManualFields(CHECKBOX_NATIONALITY_MANUAL,MANUAL_NATIONALITY);
				autoSetFatca(controlName);
				manageResidencyAndVisaStatus(RA_IS_UAE_RESIDENT,"COMBO34");
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(CUST_NATIONALITY,formObject.getValue(MANUAL_NATIONALITY).toString());
				}
				if("UNITED ARAB EMIRATES".equalsIgnoreCase(formObject.getValue(CUST_NATIONALITY).toString())) {
					formObject.setStyle(COR_MAKANI,DISABLE,FALSE);
					formObject.setStyle(CONTACT_DETAILS_MAKANI_NO,DISABLE,FALSE);
					formObject.setStyle(RES_MAKANI,DISABLE,FALSE);
				} else {
					formObject.setStyle(COR_MAKANI,DISABLE,TRUE);
					formObject.setStyle(CONTACT_DETAILS_MAKANI_NO,DISABLE,TRUE);
					formObject.setStyle(RES_MAKANI,DISABLE,TRUE);
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_MOTHERSNAME_MANUAL)) {
				toggleCheckbox(controlName,CHECKBOX_MOTHERSNAME_EIDA,CHECKBOX_MOTHERSNAME_FCR);
				manageManualFields(CHECKBOX_MOTHERSNAME_MANUAL,MANUAL_MOTHERNAME);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(PD_MOTHERMAIDENNAME,formObject.getValue(MANUAL_MOTHERNAME).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_EIDANO_MANUAL)) {
				toggleCheckbox(controlName,CHECKBOX_EIDANO_FCR,CHECKBOX_EIDANO_EIDA);
				manageManualFields(CHECKBOX_EIDANO_MANUAL,MANUAL_EIDANO);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(PD_EIDANO,formObject.getValue(MANUAL_EIDANO).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_CORR_POB_MANUAL)) {
				toggleCheckbox(controlName,CHECKBOX_CORR_POB_FCR,CHECKBOX_CORR_POB_EIDA);
				manageManualFields(CHECKBOX_CORR_POB_MANUAL,MANUAL_ADDRESS);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(CP_POBOXNO,formObject.getValue(MANUAL_ADDRESS).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_CITY_MANUAL)) {
				toggleCheckbox(controlName,CHECKBOX_CITY_FCR,CHECKBOX_CITY_EIDA);
				manageManualFields(CHECKBOX_CITY_MANUAL,MANUAL_CITY);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(CP_CITY,formObject.getValue(MANUAL_CITY).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_STATE_MANUAL)) {
				toggleCheckbox(controlName,CHECKBOX_STATE_FCR,CHECKBOX_STATE_EIDA);
				manageManualFields(CHECKBOX_STATE_MANUAL,MANUAL_STATE);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(CORR_STATE,formObject.getValue(MANUAL_STATE).toString());
					if(formObject.getValue(MANUAL_STATE).toString().equalsIgnoreCase("OTHERS")) {
						if(formObject.getValue(MANUAL_STATE).toString().equalsIgnoreCase("OTHERS")) {
							formObject.setStyle(CP_OTHERS,DISABLE,FALSE);
						}
					}
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_CNTRY_OF_CORR_MANUAL)) {
				toggleCheckbox(controlName,CHECKBOX_CNTRY_OF_CORR_FCR,CHECKBOX_CNTRY_OF_CORR_EIDA);
				manageManualFields(CHECKBOX_CNTRY_OF_CORR_MANUAL,MANUAL_CNTRY);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(CORR_CNTRY,formObject.getValue(MANUAL_CNTRY).toString());
					if(formObject.getValue(MANUAL_CNTRY).toString().equalsIgnoreCase("UNITED ARAB EMIRATES"))
						formObject.setStyle(COR_MAKANI,DISABLE,FALSE);
					else
						formObject.setStyle(COR_MAKANI,DISABLE,TRUE);
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_COUNTRY_PER_RES_MANUAL)) {
				toggleCheckbox(controlName,CHECKBOX_COUNTRY_PER_RES_FCR,CHECKBOX_COUNTRY_PER_RES_EIDA);
				manageManualFields(CHECKBOX_COUNTRY_PER_RES_MANUAL,MANUAL_PER_CNTRY);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(RES_CNTRY,formObject.getValue(MANUAL_PER_CNTRY).toString());
	
					if(formObject.getValue(MANUAL_PER_CNTRY).toString().equalsIgnoreCase("UNITED ARAB EMIRATES"))
						formObject.setStyle(CONTACT_DETAILS_MAKANI_NO,DISABLE,FALSE);
					else
						formObject.setStyle(CONTACT_DETAILS_MAKANI_NO,DISABLE,TRUE);
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_TELE_RES_MANUAL)) {
				toggleCheckbox(controlName,CHECKBOX_TELE_RES_FCR,CHECKBOX_TELE_RES_EIDA);
				manageManualFields(CHECKBOX_TELE_RES_MANUAL,MANUAL_PH);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(CP_PHONENO,formObject.getValue(MANUAL_PH).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_TELE_MOB_MANUAL)) {
				toggleCheckbox(controlName,CHECKBOX_TELE_MOB_FCR,CHECKBOX_TELE_MOB_EIDA);
				manageManualFields(CHECKBOX_TELE_MOB_MANUAL,MANUAL_MOBILE);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(CP_MOBILE,formObject.getValue(MANUAL_MOBILE).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_EMAIL_MANUAL)) {
				toggleCheckbox(controlName,CHECKBOX_EMAIL_FCR,CHECKBOX_EMAIL_EIDA);
				manageManualFields(CHECKBOX_EMAIL_MANUAL,MANUAL_EMAIL);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(CP_EMAIL,formObject.getValue(MANUAL_EMAIL).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_PROFESSION_MANUAL)) {
				toggleCheckbox(controlName,CHECKBOX_PROFESSION_FCR,CHECKBOX_PROFESSION_EIDA);
				manageManualFields(CHECKBOX_PROFESSION_MANUAL,MANUAL_PROFESSION,BTNPROFESSION);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(PROF_CODE,formObject.getValue(MANUAL_PROFESSION).toString());
					formObject.setValue(PROFESION,formObject.getValue(MANUAL_PROFESSION).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(CHECKBOX_GENDER_MANUAL)) {
				toggleCheckbox(controlName,CHECKBOX_GENDER_FCR,CHECKBOX_GENDER_EIDA);
				manageManualFields(CHECKBOX_GENDER_MANUAL,MANUAL_GENDER);
				if(controlValue.equalsIgnoreCase("True")) {
					formObject.setValue(CUST_GENDER,formObject.getValue(MANUAL_GENDER).toString());
				}
	
			} else if(controlName.equalsIgnoreCase(MANUAL_GENDER)) {
				formObject.setValue(CUST_GENDER,formObject.getValue(MANUAL_GENDER).toString());
			}//MANUAL_PROFESSION
			else if(controlName.equalsIgnoreCase(MANUAL_PROFESSION)) {
				formObject.setValue(PROF_CODE,formObject.getValue(MANUAL_PROFESSION).toString());
				formObject.setValue(PROFESION,formObject.getValue(MANUAL_PROFESSION).toString());
			}
			else if(controlName.equalsIgnoreCase(CHECKBOX_EMP_NAME_MANUAL)) {
				toggleCheckbox(controlName,CHECKBOX_EMP_NAME_FCR,CHECKBOX_EMP_NAME_EIDA);
				manageManualFields(CHECKBOX_EMP_NAME_MANUAL,MANUAL_EMPLYR_NAME,BTNEMLOYERNAME);
	
			} else if(controlName.equalsIgnoreCase(CHECKMANUAL)) {
				toggleCheckbox(controlName,CHECKFCR,CHECKEIDA);
				manageManualFields(CHECKMANUAL,MANUAL_RESIDENT);
				manageResidencyAndVisaStatus(RA_IS_UAE_RESIDENT,"COMBO34");
				if(controlValue.equalsIgnoreCase("True")) {
					//				formObject.setValue(PERM_CNTRY, formObject.getValue(MANUAL_RESIDENT).toString());
					formObject.setValue(RES_CNTRY, formObject.getValue(MANUAL_RESIDENT).toString());
					if(formObject.getValue(EIDA_RESIDENT).toString().equalsIgnoreCase("UNITED ARAB EMIRATES"))
						formObject.setStyle(RES_MAKANI,DISABLE,FALSE);
					else
						formObject.setStyle(RES_MAKANI,DISABLE,TRUE);
				}
	
			} else 	if(controlName.equalsIgnoreCase(IS_SALARY_TRANSFER_CAT_CHANGE)) {  //Manual Ended  //			//MemberShip With Checkboxes in category change
				toggleCheckboxTP(controlName,IS_MORTAGAGE_CAT_CHANGE,IS_INSURANCE_CAT_CHANGE,IS_TRB_CAT_CHANGE,IS_OTHERS_CAT_CHANGE,IS_VVIP);
				managePromoCodeCatChange();
	
			} else if(controlName.equalsIgnoreCase(IS_MORTAGAGE_CAT_CHANGE)){
				toggleCheckboxTP(controlName,IS_VVIP,IS_SALARY_TRANSFER_CAT_CHANGE,IS_INSURANCE_CAT_CHANGE,IS_TRB_CAT_CHANGE,IS_OTHERS_CAT_CHANGE);
				managePromoCodeCatChange();
	
			} else if(controlName.equalsIgnoreCase(IS_INSURANCE_CAT_CHANGE)) {
				toggleCheckboxTP(controlName,IS_VVIP,IS_SALARY_TRANSFER_CAT_CHANGE,IS_MORTAGAGE_CAT_CHANGE,IS_TRB_CAT_CHANGE,IS_OTHERS_CAT_CHANGE);
				managePromoCodeCatChange();
	
			} else if(controlName.equalsIgnoreCase(IS_TRB_CAT_CHANGE)) {
				toggleCheckboxTP(controlName,IS_VVIP,IS_SALARY_TRANSFER_CAT_CHANGE,IS_MORTAGAGE_CAT_CHANGE,IS_INSURANCE_CAT_CHANGE,IS_OTHERS_CAT_CHANGE);
				managePromoCodeCatChange();
	
			} else if(controlName.equalsIgnoreCase(IS_OTHERS_CAT_CHANGE)) {
				toggleCheckboxTP(controlName,IS_VVIP,IS_SALARY_TRANSFER_CAT_CHANGE,IS_MORTAGAGE_CAT_CHANGE,IS_INSURANCE_CAT_CHANGE,IS_TRB_CAT_CHANGE);
				managePromoCodeCatChange();
	
			} else if(controlName.equalsIgnoreCase(IS_VVIP)) {
				toggleCheckboxTP(controlName,IS_SALARY_TRANSFER_CAT_CHANGE,IS_MORTAGAGE_CAT_CHANGE,IS_INSURANCE_CAT_CHANGE,IS_TRB_CAT_CHANGE,IS_OTHERS_CAT_CHANGE);
				managePromoCodeCatChange();
	
			} else if(controlName.equalsIgnoreCase(IS_PREVILEGE_TP_CAT_CHANGE)) {   //		//Priviledge Club Checkboxes start
				toggleCheckboxTP(controlName,IS_TRAVEL_CAT_CHANGE,IS_SPORT_CAT_CHANGE,IS_SHOPPING_CAT_CHANGE,IS_ENTERTAINMENT_CAT_CHANGE);
	
			} else if(controlName.equalsIgnoreCase(IS_TRAVEL_CAT_CHANGE)) {
				toggleCheckboxTP(controlName,IS_PREVILEGE_TP_CAT_CHANGE,IS_SPORT_CAT_CHANGE,IS_SHOPPING_CAT_CHANGE,IS_ENTERTAINMENT_CAT_CHANGE);
	
			} else if(controlName.equalsIgnoreCase(IS_SPORT_CAT_CHANGE)) {
				toggleCheckboxTP(controlName,IS_PREVILEGE_TP_CAT_CHANGE,IS_TRAVEL_CAT_CHANGE,IS_SHOPPING_CAT_CHANGE,IS_ENTERTAINMENT_CAT_CHANGE);
	
			} else if(controlName.equalsIgnoreCase(IS_SHOPPING_CAT_CHANGE)) {
				toggleCheckboxTP(controlName,IS_PREVILEGE_TP_CAT_CHANGE,IS_TRAVEL_CAT_CHANGE,IS_SPORT_CAT_CHANGE,IS_ENTERTAINMENT_CAT_CHANGE);
	
			} else if(controlName.equalsIgnoreCase(IS_ENTERTAINMENT_CAT_CHANGE))
			{
				toggleCheckboxTP(controlName,IS_PREVILEGE_TP_CAT_CHANGE,IS_TRAVEL_CAT_CHANGE,IS_SPORT_CAT_CHANGE,IS_SHOPPING_CAT_CHANGE);
	
			} else if(controlName.equalsIgnoreCase(IDS_CB_SAL_TRANSFER)) {
				toggleCheckboxTP(controlName,IDS_CB_MORTGAGES,IDS_CB_INSURANCE,IDS_CB_TRB,IDS_CB_OTHERS,IDS_CB_VVIP); //		//MemberShip With Checkboxes Cust Info
				managePromoCode();
	
			} else if(controlName.equalsIgnoreCase(IDS_CB_MORTGAGES)) {
				toggleCheckboxTP(controlName,IDS_CB_SAL_TRANSFER,IDS_CB_INSURANCE,IDS_CB_TRB,IDS_CB_OTHERS,IDS_CB_VVIP);
				managePromoCode();
	
			} else if(controlName.equalsIgnoreCase(IDS_CB_INSURANCE)) {
				toggleCheckboxTP(controlName,IDS_CB_SAL_TRANSFER,IDS_CB_MORTGAGES,IDS_CB_TRB,IDS_CB_OTHERS,IDS_CB_VVIP);
				managePromoCode();
	
			} else if(controlName.equalsIgnoreCase(IDS_CB_TRB)) {
				toggleCheckboxTP(controlName,IDS_CB_SAL_TRANSFER,IDS_CB_MORTGAGES,IDS_CB_INSURANCE,IDS_CB_OTHERS,IDS_CB_VVIP);
				managePromoCode();
	
			} else if(controlName.equalsIgnoreCase(IDS_CB_OTHERS)) {
				toggleCheckboxTP(controlName,IDS_CB_SAL_TRANSFER,IDS_CB_MORTGAGES,IDS_CB_INSURANCE,IDS_CB_TRB,IDS_CB_VVIP);
				managePromoCode();
			} else if(controlName.equalsIgnoreCase("table91_UDF Field")){
				logInfo("CPDMakerThreeStep","Click: INSIDE table91_UDF Field");
				if (formObject.getValue("table91_UDF Field").toString().equalsIgnoreCase("Graduation Date")){
					formObject.clearCombo("table91_UDF Value");
				}
				else if (formObject.getValue("table91_UDF Field").toString().equalsIgnoreCase("Special Customer Identifier")){
					formObject.clearCombo("table91_UDF Value");
					loadCombo("SELECT LOV FROM USR_0_UDF_VALUES_LOV WHERE IS_VALID = 'YES'",
							"table91_UDF Value");					
					}

			} else if(controlName.equalsIgnoreCase(IDS_CB_VVIP)) {
				toggleCheckboxTP(controlName,IDS_CB_SAL_TRANSFER,IDS_CB_MORTGAGES,IDS_CB_INSURANCE,IDS_CB_TRB,IDS_CB_OTHERS);
				managePromoCode();
	
			} else if(controlName.equalsIgnoreCase(IDS_PC_CB_TP)) {    		//Priviledge Club Checkboxes Cust Info
				toggleCheckboxTP(controlName,IDS_PC_CB_ENTERTAINMENT,IDS_PC_CB_SHOPPING,IDS_PC_CB_SPORT,IDS_PC_CB_TRAVEL);
	
			} else if(controlName.equalsIgnoreCase(IDS_PC_CB_ENTERTAINMENT)) {
				toggleCheckboxTP(controlName,IDS_PC_CB_TP,IDS_PC_CB_SHOPPING,IDS_PC_CB_SPORT,IDS_PC_CB_TRAVEL);
	
			} else if(controlName.equalsIgnoreCase(IDS_PC_CB_SHOPPING)) {
				toggleCheckboxTP(controlName,IDS_PC_CB_TP,IDS_PC_CB_ENTERTAINMENT,IDS_PC_CB_SPORT,IDS_PC_CB_TRAVEL);
	
			} else if(controlName.equalsIgnoreCase(IS_SHOPPING_CAT_CHANGE)) {
				toggleCheckboxTP(controlName,IDS_PC_CB_TP,IDS_PC_CB_ENTERTAINMENT,IDS_PC_CB_SHOPPING,IDS_PC_CB_SPORT);
	
			} else if(controlName.equalsIgnoreCase(IDS_PC_CB_SPORT)) {
				toggleCheckboxTP(controlName,IDS_PC_CB_TP,IDS_PC_CB_ENTERTAINMENT,IDS_PC_CB_SHOPPING,IDS_PC_CB_TRAVEL);
	
			} else if(controlName.equalsIgnoreCase(IDS_PC_CB_TRAVEL)) {
				toggleCheckboxTP(controlName,IDS_PC_CB_TP,IDS_PC_CB_ENTERTAINMENT,IDS_PC_CB_SHOPPING,IDS_PC_CB_SPORT);
	
			} else if(controlName.equalsIgnoreCase(ED_CB_TML)) {
				if(formObject.getValue(ED_CB_TML).toString().equalsIgnoreCase("true")) {
					formObject.setValue(ED_CB_NON_TML,"false");
				} else {
					formObject.setValue(ED_CB_TML,"false");
				}
			} else if(controlName.equalsIgnoreCase(ED_CB_NON_TML)) {
				if(formObject.getValue(ED_CB_NON_TML).toString().equalsIgnoreCase("true")){
					formObject.setValue(ED_CB_TML,"false");
				}
				else {
					formObject.setValue(ED_CB_NON_TML,"false");
				}
			} else if(controlName.equalsIgnoreCase(DEL_MODE_YES)) {
				toggleCheckbox_2(controlName,DEL_MODE_NO);
			} else if(controlName.equalsIgnoreCase(DEL_BRNCH_COURIER)) {
				toggleCheckbox_2(controlName,DEL_EXCELLENCY_CNTR);
			} else if(controlName.equalsIgnoreCase(DEL_MODE_NO)) {
				toggleCheckbox_2(controlName,DEL_MODE_YES);
			} else if(controlName.equalsIgnoreCase(DEL_EXCELLENCY_CNTR)) {
				toggleCheckbox_2(controlName,DEL_BRNCH_COURIER);
			} else if(controlName.equalsIgnoreCase(DEL_BRNCH_COURIER)){
				logInfo("","-------------AO_DEL_BRNCH_COURIER--------111-----");
				if(controlName.equalsIgnoreCase(DEL_BRNCH_COURIER) && controlValue.equalsIgnoreCase("True")) {
					logInfo("","controlValue"+controlValue);
					formObject.setStyle(DEL_BRNCH_NAME,DISABLE,FALSE);
					formObject.setStyle(EXCELLENCY_CENTER,DISABLE,TRUE);
				} else if(controlName.equalsIgnoreCase(DEL_BRNCH_COURIER) && controlValue.equalsIgnoreCase("False")){
					logInfo("","controlValue"+controlValue);
					formObject.setStyle(DEL_BRNCH_NAME,DISABLE,TRUE);
					formObject.setStyle(EXCELLENCY_CENTER,DISABLE,FALSE);
				}
			} else if(controlName.equalsIgnoreCase(DEL_EXCELLENCY_CNTR)) {
				if(controlName.equalsIgnoreCase(DEL_EXCELLENCY_CNTR) && controlValue.equalsIgnoreCase("True")) {
					logInfo("","controlValue"+controlValue);
					formObject.setStyle(DEL_BRNCH_NAME,DISABLE,TRUE);
					formObject.setStyle(EXCELLENCY_CENTER,DISABLE,FALSE);
				}
				else if(controlName.equalsIgnoreCase(DEL_EXCELLENCY_CNTR) && controlValue.equalsIgnoreCase("False")){
					logInfo("","controlValue"+controlValue);
					formObject.setStyle(DEL_BRNCH_NAME,DISABLE,FALSE);
					formObject.setStyle(EXCELLENCY_CENTER,DISABLE,TRUE);
				}
			}else if(controlName.equalsIgnoreCase(PER_INC_TAX_CON_1) ){
				System.out.println("Country 1 Selected........Sourav");
				checkforDuplicateCountries(PER_INC_TAX_CON_1);
			}else if (controlName.equalsIgnoreCase(PER_INC_TAX_CON_2) ){
				System.out.println("Country 2 Selected........Sourav");
				checkforDuplicateCountries(PER_INC_TAX_CON_2);
			}else if (controlName.equalsIgnoreCase(PER_INC_TAX_CON_3)){
				System.out.println("Country 3 Selected........Sourav");
				checkforDuplicateCountries(PER_INC_TAX_CON_3);	   
			}else if (controlName.equalsIgnoreCase(SUB_PERSONAL_TAX)) {
				logInfo("change event","SUB_PERSONAL_TAX sarted");
				if (formObject.getValue(SUB_PERSONAL_TAX).toString().equalsIgnoreCase("Yes")) {
					formObject.setStyle(PER_INC_TAX_CON_1, DISABLE, FALSE);
				}else if( formObject.getValue(SUB_PERSONAL_TAX).toString().equalsIgnoreCase("No")) {
					formObject.setStyle(PER_INC_TAX_CON_1, DISABLE, FALSE);
					formObject.setStyle(PER_INC_TAX_CON_2, DISABLE, FALSE);
					formObject.setStyle(PER_INC_TAX_CON_3, DISABLE, FALSE);
					formObject.setValue(PER_INC_TAX_CON_1,"");
					formObject.setValue(PER_INC_TAX_CON_1,"");
					formObject.setValue(PER_INC_TAX_CON_1,"");
				}
			//Added by Shivanshu CRS TIN COUNTRY
			}else if(controlName.equalsIgnoreCase(CRS_TAX_COUNTRY)){
				logInfo("CRS_TAX_COUNTRY", "inside CRS_TAX_COUNTRY : ");
				disableFieldCRSCountry();
			}
			if(controlName.equalsIgnoreCase(CHECKBOX_NATIONALITY_FCR)
					||controlName.equalsIgnoreCase(FCR_NATIONALITY)
					||controlName.equalsIgnoreCase(CHECKBOX_NATIONALITY_EIDA)
					||controlName.equalsIgnoreCase(CHECKBOX_NATIONALITY_MANUAL)
					||controlName.equalsIgnoreCase(EIDA_NATIONALITY)
					||controlName.equalsIgnoreCase(MANUAL_NATIONALITY)
					||controlName.equalsIgnoreCase(CHECKBOX_CNTRY_OF_CORR_FCR)
					||controlName.equalsIgnoreCase(FCR_CNTRY)
					||controlName.equalsIgnoreCase(CHECKBOX_CNTRY_OF_CORR_EIDA)
					||controlName.equalsIgnoreCase(CHECKBOX_CNTRY_OF_CORR_MANUAL)
					||controlName.equalsIgnoreCase(EIDA_CNTRY)||controlName.equalsIgnoreCase(MANUAL_CNTRY)
					||controlName.equalsIgnoreCase(CHECKBOX_TELE_RES_FCR)||controlName.equalsIgnoreCase(FCR_PH)
					||controlName.equalsIgnoreCase(CHECKBOX_TELE_RES_EIDA)
					||controlName.equalsIgnoreCase(CHECKBOX_TELE_RES_MANUAL)
					||controlName.equalsIgnoreCase(EIDA_PH)||controlName.equalsIgnoreCase(MANUAL_PH)
					||controlName.equalsIgnoreCase(CHECKBOX_TELE_MOB_FCR)||controlName.equalsIgnoreCase(FCR_MOBILE)
					||controlName.equalsIgnoreCase(CHECKBOX_TELE_MOB_EIDA)
					||controlName.equalsIgnoreCase(CHECKBOX_TELE_MOB_MANUAL)
					||controlName.equalsIgnoreCase(EIDA_MOBILE)||controlName.equalsIgnoreCase(MANUAL_MOBILE)
					||controlName.equalsIgnoreCase(CHECKBOX_COUNTRY_RES_FCR)
					||controlName.equalsIgnoreCase(FCR_RESIDENT)
					||controlName.equalsIgnoreCase(CHECKBOX_COUNTRY_RES_EIDA)
					||controlName.equalsIgnoreCase(CHECKBOX_COUNTRY_RES_MANUAL)
					||controlName.equalsIgnoreCase(EIDA_RESIDENT)
					||controlName.equalsIgnoreCase(MANUAL_RESIDENT)
					||controlName.equalsIgnoreCase(FAT_US_PERSON)||controlName.equalsIgnoreCase(FAT_LIABLE_TO_PAY_TAX)
					||controlName.equalsIgnoreCase(CNTRY_OF_BIRTH)||controlName.equalsIgnoreCase(POACOMBO)
					||controlName.equalsIgnoreCase(INDICIACOMBO)||controlName.equalsIgnoreCase(FAT_SSN)
					||controlName.equalsIgnoreCase(FAT_CUST_CLASSIFICATION)
					||controlName.equalsIgnoreCase(DATEPICKERCUST)
					||controlName.equalsIgnoreCase(DATEPICKERW8)||controlName.equalsIgnoreCase(COMBODOC)
					||controlName.equalsIgnoreCase(CHECKBOX_COB_FCR)||controlName.equalsIgnoreCase(CHECKBOX_COB_EIDA)
					||controlName.equalsIgnoreCase(CHECKBOX_COB_MANUAL)
					||controlName.equalsIgnoreCase(FCR_COUNTRYBIRTH)
					||controlName.equalsIgnoreCase(EIDA_COUNTRYBIRTH)
					||controlName.equalsIgnoreCase(MANUAL_COUNTRYBIRTH)) {
				if(!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
					int fieldToFocus=Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
					String sAccRelation = formObject.getTableCellValue(ACC_RELATION, fieldToFocus, 7);
					logInfo("onchange","sAccRelation------"+sAccRelation);
					manageChangeinFATCAFields(controlName,sAccRelation);
					autoSetFatca(controlName);
				}
				if(controlName.equalsIgnoreCase(MANUAL_MOBILE) &&
						"United Arab Emirates".equalsIgnoreCase(checkCountry())) {
					flag_mobile=true;
					mobileChangeFlag = true;
					String sNumber=formObject.getValue(MANUAL_MOBILE).toString();
					//Added by SHivanshu ATP-472
					if(!(sNumber.startsWith("971") || sNumber.startsWith("+971") || sNumber.startsWith("00971"))) {
						sendMessageValuesList("","Mobile Number does not start with 971");
					}
				} else if(controlName.equalsIgnoreCase(MANUAL_PH)){
					flag_phone=true;
				}
			}
		}

	private String onClickEventCPDMakerFourStep(String controlName, String eventType, String data) {
		String controlValue = formObject.getValue(controlName).toString();
		ArrayList<Boolean> TRSDclicked=new ArrayList<Boolean>();   
		boolean validate=false;        
		int iEtihadValidate =3;
		List<String> processedCustomerList= new ArrayList<String>();  
		String retMsg = getReturnMessage(true ,controlName);
		if(controlName.equalsIgnoreCase(DC_BTN_ADD)){
			if(addDebitCard()){
				return  getReturnMessage(true, DC_BTN_ADD);
			}
		} else if(controlName.equalsIgnoreCase("fetch_balance")) {
			logInfo("onClickEventFourStep","INSIDE fetch_balance button");
			//getRealTimeDetails(); //made in CPDBulkEODChecker
		} else if(controlName.equalsIgnoreCase(BTN_VIEW)) {
			logInfo("Inside VIEW Click Event","inside view 5");
			formObject.clearTable(LVW_DEDUPE_RESULT);
			int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
			String sQuery= "SELECT CUST_ID,CUST_NAME,CUST_IC,CUST_PASSPORT,CUST_EMAIL,"
					+ "CUST_MOBILE,'','',to_date(CUST_DOB,'"+getDateValue("DATEFORMAT")+"'),CUST_EIDA,"
					+ "CUST_NATIONALITY,system_type FROM USR_0_DUPLICATE_SEARCH_DATA "
					+ "WHERE WI_NAME='"+sWorkitemId+"' AND CUST_SNO ='"+iSelectedRow+"'";
			List<List<String>> recordList = formObject.getDataFromDB(sQuery);
			loadListView(recordList,COLUMNS_LVW_DEDUPE_RESULT,LVW_DEDUPE_RESULT);
			String sQuery1 = "select dedupe_selected_index from USR_0_CUST_TXN Where WI_NAME='"+ sWorkitemId +"' "
					+ "and cust_sno='"+iSelectedRow+"'";
			logInfo("Inside VIEW Click Event","sQuery" +sQuery1);
			recordList = formObject.getDataFromDB(sQuery1);
			logInfo("Inside VIEW Click Event",""+recordList);
			int index1=Integer.parseInt(data);
			logInfo("Inside VIEW Click Event","index1"+index1);
			int[] intA = new int[1];
			intA[0] =index1;
		} else if (controlName.equalsIgnoreCase("AccInfo_UdfList")) {  //ACCOUNT INFO SAVE AND NECT BUTTON
			logInfo("AccInfo_UdfList","Inside udf_addRow");
			logInfo("AccInfo_UdfList","ACC_INFO_UDF_FIELD: " 
					+ formObject.getValue("table91_UDF Field").toString());
			if (formObject.getValue("table91_UDF Field").toString().equalsIgnoreCase("")) {
				sendMessageValuesList("table91_UDF Field","Select a UDF field first.");
				//						return false;
			} else {
				if (UdfUniquenessCheck(formObject.getValue("table91_UDF Field").toString())) {
					logInfo("AccInfo_UdfList","Inside udf_addRow 1");
					if (formObject.getValue("table91_UDF Field").toString().equalsIgnoreCase("Graduation Date")) {
						logInfo("AccInfo_UdfList","Inside udf_addRow 2");
						if (validateJavaDate(formObject.getValue("table91_UDF Value").toString())) {
							logInfo("AccInfo_UdfList","Inside udf_addRo 3");
							/*String colnames = "UDF_FIELD,UDF_VALUE";
							String values = formObject.getValue(ACC_INFO_UDF_FIELD)+ "##"
									+ formObject.getValue(ACC_INFO_UDF_VALUE);
							//LoadListViewWithHardCodeValues("ACCINFO_UDF_LIST", colnames, values);
							formObject.setValue(ACC_INFO_UDF_VALUE, "");*/
						} else {
							sendMessageValuesList("table91_UDF Value","Date entered by you is not valid");
						}
					} else {
						logInfo("UDF_ADDROW","Inside UDF_ADDROW 4");
						/*String colnames = "UDF_FIELD,UDF_VALUE";
						String values = formObject.getValue(ACC_INFO_UDF_FIELD)+ "##" 
								+ formObject.getValue(ACC_INFO_UDF_VALUE);*/
						//LoadListViewWithHardCodeValues("ACCINFO_UDF_LIST", colnames, values);
//						formObject.setValue(ACC_INFO_UDF_VALUE, "");
					}
				} else {
					sendMessageValuesList("table91_UDF Field",formObject.getValue("table91_UDF Field").toString()
							+ " field already exist");
				}
			}
		}  else if (controlName.equalsIgnoreCase(CRS_ADD)) {  //CRS TAB ADD BUTTON
			logInfo("CRS_ADD","CRS add clicked");
			int gridCount = getGridCount(CRS_TAXCOUNTRYDETAILS);
			//Added by Shivanshu For CRS_TIN 22-11-2024
			boolean TINFlag = validateCRSTINField();
			if (!(null == formObject.getValue(CRS_TAX_COUNTRY)
					|| formObject.getValue(CRS_TAX_COUNTRY).toString().equalsIgnoreCase("") 
					|| formObject.getValue(CRS_TAX_COUNTRY).toString().equalsIgnoreCase("."))) {
				if (!(null == formObject.getValue(CRS_TAXPAYERIDENTIFICATIONNUMBER) 
						|| formObject.getValue(CRS_TAXPAYERIDENTIFICATIONNUMBER).toString().equalsIgnoreCase(""))) {
					if (crsGridUniquenessCheck(formObject.getValue(CRS_TAX_COUNTRY).toString())) {
						if ((!(null == formObject.getValue(CRS_TAXPAYERIDENTIFICATIONNUMBER) 
								|| "".equalsIgnoreCase(formObject.getValue(CRS_TAXPAYERIDENTIFICATIONNUMBER).toString())))
								&& ((!(null == formObject.getValue(CRS_REASONNOTPROVIDINGTIN) 
								|| "".equalsIgnoreCase(formObject.getValue(CRS_REASONNOTPROVIDINGTIN).toString()))) 
								|| (!(null == formObject.getValue(REASON_DESC)
								|| "".equalsIgnoreCase(formObject.getValue(REASON_DESC).toString()))))) {
							sendMessageValuesList("","Please select either of TIN or Reason for not providing TIN");
						} else {
							 //Added by Shivanshu CRS_TIN 22-11-2024
							if(TINFlag) {
								String colnames = "Sno,Country_Of_Tax_Residency,TaxPayer_Identification_Number"
										+ ",Reason_For_Not_Providing_TIN,Reason_Description";
								String values = Integer.toString(gridCount + 1) + "##"
										+ formObject.getValue(CRS_TAX_COUNTRY)
										+ "##"
										+ formObject.getValue(CRS_TAXPAYERIDENTIFICATIONNUMBER)
										+ "##"
										+ formObject.getValue(CRS_REASONNOTPROVIDINGTIN)
										+ "##"
										+ formObject.getValue(REASON_DESC);
								LoadListViewWithHardCodeValues(CRS_TAXCOUNTRYDETAILS, colnames,values);
								/*formObject.setValue(CRS_TAXPAYERIDENTIFICATIONNUMBER,"");
							formObject.setValue(REASON_DESC, "");
							formObject.setValue(CRS_REASONNOTPROVIDINGTIN, "");
							formObject.setValue(CRS_TAX_COUNTRY, "");*/
								clearControls(new String[]{CRS_TAXPAYERIDENTIFICATIONNUMBER,REASON_DESC,CRS_TAX_COUNTRY,CRS_REASONNOTPROVIDINGTIN});
							}
						}
					} else {
						sendMessageValuesList("","TIN information already exist for this country.");
					}
				} else if (!(null == formObject.getValue(CRS_REASONNOTPROVIDINGTIN) 
						|| formObject.getValue(CRS_REASONNOTPROVIDINGTIN).toString().equalsIgnoreCase(""))) {
					if (crsGridUniquenessCheck(formObject.getValue(CRS_TAX_COUNTRY).toString())) {
						if ((!(null == formObject.getValue(CRS_TAXPAYERIDENTIFICATIONNUMBER)
								|| "".equalsIgnoreCase(formObject.getValue(CRS_TAXPAYERIDENTIFICATIONNUMBER).toString())))
								&& ((!(null == formObject.getValue(CRS_REASONNOTPROVIDINGTIN) 
								|| "".equalsIgnoreCase(formObject.getValue(CRS_REASONNOTPROVIDINGTIN).toString()))) 
								|| (!(null == formObject.getValue(REASON_DESC)
								|| "".equalsIgnoreCase(formObject.getValue(REASON_DESC).toString())))))
						{
							sendMessageValuesList("","Please select either of TIN or Reason for not providing TIN");
						} else { 
							 //Added by Shivanshu CRS_TIN 22-11-2024
							if(TINFlag) {
								String colnames = "Sno,Country_Of_Tax_Residency,TaxPayer_Identification_Number,"
										+ "Reason_For_Not_Providing_TIN,Reason_Description";
								String values = Integer.toString(gridCount + 1)
										+ "##"
										+ formObject.getValue(CRS_TAX_COUNTRY)
										+ "##"
										+ formObject.getValue(CRS_TAXPAYERIDENTIFICATIONNUMBER)
										+ "##"
										+ formObject.getValue(CRS_REASONNOTPROVIDINGTIN)
										+ "##"
										+ formObject.getValue(REASON_DESC);
								LoadListViewWithHardCodeValues(CRS_TAXCOUNTRYDETAILS, colnames,values);
								logInfo("executeserverevent","crs_add button blank ");
								/*formObject.setValue(CRS_TAXPAYERIDENTIFICATIONNUMBER,"");
							formObject.setValue(REASON_DESC, "");
							formObject.setValue(CRS_REASONNOTPROVIDINGTIN, "");
							formObject.setValue(CRS_TAX_COUNTRY, "");*/
								clearControls(new String[]{CRS_TAXPAYERIDENTIFICATIONNUMBER,REASON_DESC,CRS_TAX_COUNTRY,CRS_REASONNOTPROVIDINGTIN});
							}
						}
					} else {
						sendMessageValuesList("","TIN information already exist for this country.");
					}
				} else {
					//Need to add validation for TIN is null then throw error message shivanshu 13-03-2025
					sendMessageValuesList(CRS_REASONNOTPROVIDINGTIN,"Please enter valid TIN.");
				}
			} else {
				sendMessageValuesList(CRS_TAX_COUNTRY,"Enter Country of Tax Residency");
			}
			checkCRS();
		}  else if(controlName.equalsIgnoreCase("DOC_VIEW_CPD")) {

		} else if(controlName.equalsIgnoreCase(BTN_SELECT_CUST)) {
			fillDuplicateData(data);
		}  else if(controlName.equalsIgnoreCase("CNTRL_BANK_URL_CPD")) {//logon1.jsp
			String sQuery= "SELECT VALUE FROM USR_0_DEFAULTVALUE_FCR WHERE NAME ='CentralBankURL'";
			List<List<String>> sOutput= formObject.getDataFromDB(sQuery);
			String ain = sOutput.get(0).get(0);
			getReturnMessage(true, ain);
			//			JSObject objJStemp = formObject.getJSObject();
			//			objJStemp.call("openURL",ain);
		} else if(controlName.equalsIgnoreCase(BTN_CHK_VIEW_DETAILS)) {
			//			int iProcessedCustomer = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
			//			int iRowCount = getGridCount("ListView17");
			//			String[] iListViewSelectedRow = data;
			//
			//			if(iRowCount==0) {
			//				JOptionPane.showMessageDialog(null,"NO row in the grid.");
			//				return;
			//			}
			//			else if(iRowCount>0 && iListViewSelectedRow.length==0) {
			//				JOptionPane.showMessageDialog(null, "Please select a row first.");
			//				return;
			//			} else {
			//				int iSelectedRow=iListViewSelectedRow[0];//viewBlackListData.jsp
			//				logInfo("onClickEventCPDMakerFourStep","iSelectedRow---"+iSelectedRow);
			//				JSObject objJStemp = formObject.getJSObject();
			//				Object sOutput;
			//	 Object [] ain = {sWorkitemId+"#"+iProcessedCustomer+"#"+iSelectedRow+"#"+"USR_0_BLACKLIST_DATA_CPD"};
			//				sOutput = objJStemp.call("viewBlacklist",ain); 
			//			}
		} else if(controlName.equalsIgnoreCase(BTN_CPD_VIEWDETAILS)) {
			//			int iProcessedCustomer = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX)
			//.toString())+1;
			//			int iRowCount = getGridCount("ListView19");
			//			int[] iListViewSelectedRow = formObject.getNGLVWSelectedRows("ListView19");
			//
			//			if(iRowCount==0) {
			//				JOptionPane.showMessageDialog(null,"No row in the grid.");
			//				return;
			//			} else if(iRowCount>0 && iListViewSelectedRow.length==0) {
			//				JOptionPane.showMessageDialog(null, "Please select a row first.");
			//				return;
			//			} else {
			//				int iSelectedRow=iListViewSelectedRow[0];//viewBlackListData.jsp
			//				logInfo("onClickEventCPDMakerFourStep","iSelectedRow---"+iSelectedRow);
			//				JSObject objJStemp = formObject.getJSObject();
			//				Object sOutput;
			//				Object [] ain = {sWorkitemId+"#"+iProcessedCustomer+"#"+iSelectedRow+"#"+
			//"USR_0_BLACKLIST_DATA_CPD"};
			//				sOutput = objJStemp.call("viewWorldCheck",ain); 
			//			}
		} else if(controlName.equalsIgnoreCase(BTN_VIEWDETAILS_SANCT)) {
			logInfo("onClickEventCPDMakerFourStep","saving data");
			saveScreeningDataCPD();
			int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
			String sCustNo = formObject.getTableCellValue(ACC_RELATION,iSelectedRow,0);
			int iOutput= updateDataInDB("USR_0_BRMS_TRACKER","SCREENING_STATUS","'Success'",
					"WI_NAME ='"+sWorkitemId+"' AND CUST_SNO='"+sCustNo+"' AND SCREENING_STATUS ='Pending'");
			logInfo("onClickEventCPDMakerFourStep","sOutput----"+iOutput);
		} else if(controlName.equalsIgnoreCase("Command60")) {
			logInfo("onClickEventCPDMakerFourStep","data saved into CPD table...");
			saveScreeningDataCPD();
		} else if(controlName.equalsIgnoreCase(BTN_SI_ADD)) {
			logInfo("onClickEventCPDMakerFourStep","Inside");
			if(formObject.getValue(SI_DEB_ACC_NO).toString().equalsIgnoreCase("")) {
				sendMessageValuesList(SI_DEB_ACC_NO, "Please select Debit to Product");
				return "";
			}
			if(formObject.getValue(SI_CURRENCY).toString().equalsIgnoreCase("")) {
				sendMessageValuesList(SI_CURRENCY, "Please select Currency");
				return "";
			}
			if(formObject.getValue(SI_CRED_PROD).toString().equalsIgnoreCase("")) {
				sendMessageValuesList(SI_CRED_PROD, "Please select Credit to Product");
				return "";
			}
			if(formObject.getValue(SI_FRST_PAYMNT).toString().trim().equalsIgnoreCase("")) {
				sendMessageValuesList(SI_FRST_PAYMNT, "Please select First Payment Date");
				return "";
			} else {
				boolean bReturn = validateSIDate(SI_FRST_PAYMNT,"First Payment ");
				if(bReturn == false) {
					return "";
				}
			}
			if(formObject.getValue(SI_LST_PAYMNT).toString().trim().equalsIgnoreCase("")) {
				sendMessageValuesList(SI_LST_PAYMNT, "Please select Last Payment Date");
				return "";
			} else {
				boolean bReturn =  validateSIDate(SI_LST_PAYMNT,"Last Payment ");
				if(bReturn == false) {
					return "";
				}
				if(!formObject.getValue(SI_LST_PAYMNT).toString().equalsIgnoreCase("")
						&& !formObject.getValue(SI_FRST_PAYMNT).toString().equalsIgnoreCase("")) {
					boolean isFuture = compareDateMMM(formObject.getValue(SI_LST_PAYMNT).toString(),formObject.getValue(SI_FRST_PAYMNT).toString());
					if(!isFuture) {
						sendMessageValuesList("", "Last Payment Date Should Be Greater Then First Payment Date.");
						return "";
					}
				}
				if(formObject.getValue(SI_PERIOD).toString().equalsIgnoreCase("")) {
					sendMessageValuesList(SI_PERIOD, "Please select Period");
					return "";
				}
				if(formObject.getValue(SI_TRNS_AMT).toString().equalsIgnoreCase("")) {
					sendMessageValuesList(SI_TRNS_AMT, "Please fill Transfer Amount");
					return "";
				}
				String values = formObject.getValue(SI_DEB_ACC_NO).toString()+"##"+
				formObject.getValue(SI_CURRENCY).toString()+"##"+formObject.getValue(SI_CRED_PROD).toString()
				+"##"+formObject.getValue(SI_FRST_PAYMNT).toString()+"##"+formObject.getValue(SI_LST_PAYMNT).toString()
				+"##"+formObject.getValue(SI_PERIOD).toString()+"##"+formObject.getValue(SI_TRNS_AMT).toString();
				logInfo("standing instr","values " + values);
				LoadListViewWithHardCodeValues(STND_INST_LVW, "DEBIT_ACC_NO,DEBIT_CURRENCY,CREDIT_PRODUCT,"
						+ "FIRST_PAY_DATE,LAST_PAY_DATE,PERIOD,AMOUNT", values);
				formObject.setValue(SI_DEB_ACC_NO, "");
				formObject.setValue(SI_CURRENCY, "");
				formObject.setValue(SI_CRED_PROD, "");
				formObject.setValue(SI_FRST_PAYMNT, "");
				formObject.setValue(SI_LST_PAYMNT, "");
				formObject.setValue(SI_PERIOD, "");
				formObject.setValue(SI_TRNS_AMT, ""); 
				return "true"; 
			}
		} else if(controlName.equalsIgnoreCase(BTN_SI_MOD)) {
			logInfo("onClickEventCPDMakerFourStep","In Command MODIFY");
			if(formObject.getValue(SI_DEB_ACC_NO).toString().equalsIgnoreCase("")) {
				sendMessageValuesList(SI_DEB_ACC_NO, "Please select Debit to Productt");
				return "";
			}
			if(formObject.getValue(SI_CURRENCY).toString().equalsIgnoreCase("")) {
				sendMessageValuesList(SI_CURRENCY, "Please select Currency");
				return "";
			}
			if(formObject.getValue(SI_CRED_PROD).toString().equalsIgnoreCase("")) {
				sendMessageValuesList(SI_CRED_PROD, "Please select Product Description");
				return "";
			}
			if(formObject.getValue(SI_FRST_PAYMNT).toString().equalsIgnoreCase("")) {
				sendMessageValuesList(SI_FRST_PAYMNT, "Please select First Payment");
				return "";
			} else {
				boolean bReturn = validateSIDate(SI_FRST_PAYMNT,"First Payment Date");
				if(bReturn == false) {
					return "";
				}
			}
			if(formObject.getValue(SI_LST_PAYMNT).toString().equalsIgnoreCase("")) {
				sendMessageValuesList(SI_LST_PAYMNT, "Please select Last Payment Date");
				return "";
			}
			if(!formObject.getValue(SI_LST_PAYMNT).toString().equalsIgnoreCase("") 
					&& !formObject.getValue(SI_FRST_PAYMNT).toString().equalsIgnoreCase("")) {
				boolean isFuture= compareDateMMM(formObject.getValue(SI_LST_PAYMNT).toString(),
						formObject.getValue(SI_FRST_PAYMNT).toString());
				if(!isFuture) {
					sendMessageValuesList("", "Last Payment Date Should Be Greater Then First Payment Date.");
					return "";
				}
			}
			if(formObject.getValue(SI_PERIOD).toString().equalsIgnoreCase("")) {
				sendMessageValuesList(SI_PERIOD, "Please select Period");
				return "";
			}
			if(formObject.getValue(SI_TRNS_AMT).toString().equalsIgnoreCase("")) {
				sendMessageValuesList(SI_TRNS_AMT, "Please fill Transfer Amount");
				return "";
			}
			return "true"; 
			
		} else if(BTN_SUBMIT.equalsIgnoreCase(controlName) && data.equalsIgnoreCase("Finally_Decision_Clear")) {// Product_Clear
			if(submitWorkItem(eventType,data)) {
				return "true";
			}
		} else if(BTN_SUBMIT.equalsIgnoreCase(controlName) && data.equalsIgnoreCase("CA008_Clear")){// CA008_Clear
			createHistory();
			createAllHistory();
			updateProfitCentre(); 
			return getReturnMessage(true, controlName);
		} else if(BTN_SUBMIT.equalsIgnoreCase(controlName) && data.contains("@@@")) {
			String[] dataArr = data.split("@@@");
			if("FOR_YES".equalsIgnoreCase(dataArr[0])) {
				if(submitWorkItem(eventType,data)) {
					return "true";
				}
			} else {
				sendMessageValuesList(PRODUCT_QUEUE, "");
			}
		} else if(BTN_SUBMIT.equalsIgnoreCase(controlName) && (data.equalsIgnoreCase("confirmSubmit") ||
				data.equalsIgnoreCase("confirmSubmitOnReturnReject"))) {
			if("confirmSubmitOnReturnReject".equalsIgnoreCase(data)) {
				createHistory();
				createAllHistory();
				updateProfitCentre();
				return getReturnMessage(true, controlName);
			} else if("confirmSubmit".equalsIgnoreCase(data)) {
				if(submitWorkItem(eventType,data)) {
					return "true";
				}
			}
			/*if(submitCPDMakerValidations(eventType,data)) {
				if(!(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Return to Originator") || 
						formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Return") || 
						formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Reject"))) {
					if(submitValidationForWorkItem(eventType,data)) {
						String msg2 = confirmOnSubmitInForLoop();
						if(msg2.equalsIgnoreCase("true")) {
							if(submitWorkItem(eventType,data)) {
								return "true";
							}
						} else return msg2;
					}
				} else {
					return getReturnMessage(true, controlName, "###"+CA008);
				}
			}*/
		} else if(controlName.equalsIgnoreCase(CT_BTN_REFRESH)) {
			int iProcessedCustomer=Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
			String sCustID  = formObject.getTableCellValue(ACC_RELATION, iProcessedCustomer , 2);
			logInfo("CT_BTN_REFRESH","iProcessedCustomer----"+iProcessedCustomer);
			logInfo("CT_BTN_REFRESH","sCustID----"+sCustID);
			if(sCustID.equalsIgnoreCase("")) {
				sendMessageValuesList("", "Customer ID not present for this customer");
				return "";
			}
			setFCRValueonLoad(sCustID);
		} else if(controlName.equalsIgnoreCase(BTN_CPD_RE_CHK)) {
			loadBlackListDataCPD();
		} else if(controlName.equalsIgnoreCase(BTN_SUBMIT) && data.equalsIgnoreCase("confirmAppRisk")) {
			return afterAppRiskCheck(data);
		} else if(controlName.equalsIgnoreCase(BTN_SUBMIT)) {//((controlName.equalsIgnoreCase("COMMAND24")||controlName.equalsIgnoreCase("static_submit"))) {
			// newly commented
			onSaveClick();
			String query = "select TRSD_2_STATUS from USR_0_TRSD_DETAILS where WI_NAME='"+sWorkitemId+"'";
			logInfo("BTN_SUBMIT","query: "+query);
			List<List<String>> list = formObject.getDataFromDB(query);
			logInfo("BTN_SUBMIT","list: "+list);
			String trsdDec = "";
			if(list.size() > 0) {
				trsdDec = list.get(0).get(0);
			}
			calculateAppRiskCPD();  //added for setting risk
			validateCrsClassification(); //added by reyaz 16092022
			if(!sancScreeningTabClicked && !trsdDec.equalsIgnoreCase("Returned")) {
				sendMessageValuesList("", "Please click on Sanction Screening tab before proceeding");
				return "";
			} else if(!appAssessTabClicked && !trsdDec.equalsIgnoreCase("Returned")) {
				sendMessageValuesList("", "Please click on Application Assessment tab before proceeding");
				return "";
			}
			logInfo("currentRiskChange",controlValue);
			if(isControlVisible(DOC_APPROVAL_OBTAINED) && isControlVisible(COURT_ORD_TRADE_LIC)) {
				if (formObject.getValue(DOC_APPROVAL_OBTAINED).toString().equalsIgnoreCase("false")
						&& formObject.getValue(COURT_ORD_TRADE_LIC).toString().equalsIgnoreCase("false")) {
					sendMessageValuesList("", "Please select the appropriate checkbox to complete the validation");
					return "";
				}				
			}
			boolean prodChangeFlag = checkProdChngForNoEligibility();
			if(!prodChangeFlag) {
				sendMessageValuesList("","Customer is not eligible for cheque book. Please change the product.");
				return "";
			}
			insertUdfDetails();
			// CHEQBOOK - RESIDANCE WITHOUT EIDA CHANGE
			if(!noChequeBookForResidenceWithoutEida()) {
				return ""; 
			}
			if(!MandatoryEmploymentAddress()){
				return "";
			}
			String accOwnType = formObject.getValue(ACC_OWN_TYPE).toString();
			logInfo("appLevelMandatoryCheck","appLevelMandatoryCheck : "+appLevelMandatoryCheck);
			if(appLevelMandatoryCheck && !accOwnType.equalsIgnoreCase("Single")){
				logInfo(BTN_SUBMIT,"INSIDE IFBLOCK");
				sendMessageValuesList(BTN_APP_LEVEL_RISK,"Please perform Application Level Risk Check");
				//return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
				return "";
			}
			if(getGridCount(CRS_TAXCOUNTRYDETAILS) > 0 )
			{
				for(int i=0 ; i<getGridCount(CRS_TAXCOUNTRYDETAILS);i++)
				{
					String countryOfTaxResidency=formObject.getTableCellValue(CRS_TAXCOUNTRYDETAILS,i,1);
					logInfo("submit","Value of selected Tax Residency country"+ countryOfTaxResidency);
					if (countryOfTaxResidency.equalsIgnoreCase("UNITED ARAB EMIRATES")) {
						boolean result = MandatoryCRSDueDiligence();
						System.out.println("MandatoryCRSDueDiligence result---"+result);
						if(!result)
						{
							return "";
						}
						break;
					}
				}
			}
			if(!checkSalaryTransfer()){
				return "";
			}
			//formObject.setStyle("STATIC_SUBMIT",DISABLE,TRUE);// newly commented
			formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
			//formObject.setStyle("Command24",DISABLE,TRUE);// newly commented
			try {
				//updateTRSDDecision();
				updateFSKDecision(); // Changes date  29-05-2023
			} catch (Exception e) {
				logError("BTN_SUBMIT",e);
			}
			String resultTRSD =	checkTRSDResult("CPD");
			String resultTRSD1 = checkTRSDResult1("CPD");
			logInfo("","resultTRSD CPD:"+resultTRSD);
			if(resultTRSD.equalsIgnoreCase("1")  && !formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Return to Originator")
					&& "Returned".equalsIgnoreCase(resultTRSD1)) {
				sendMessageValuesList("","This application can only be Returned since TRSD result is Returned.");
				return "";
			}				
			if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Reject") || 
					formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Return to Originator")) {
				formObject.setStyle(BTN_SUBMIT,DISABLE,FALSE); // newly added
				return afterAppRiskCheck(data);
			}
			boolean result=false;
			result=checkNatSegment();
			logInfo("","NEW VALIDATION---"+result);
			if(!result) {
				formObject.setStyle(BTN_SUBMIT,DISABLE,FALSE);
				return "";
			}
			result = mandatoryComparisonData();
			logInfo("BTN_SUBMIT","COMMAND24 KYC result---"+result);
			if(!result) {
				//sCurrTabIndex=1;
				//formObject.setStyle("static_next",DISABLE,FALSE);
				return "";
			}

			result = mandatoryIndividualInfo();
			logInfo("BTN_SUBMIT","COMMAND24 Individual Info result---"+result);
			if(!result) {
				//formObject.setStyle("static_next",DISABLE,FALSE);
				return "";
			}

			result = mandatoryContactInfo();
			logInfo("BTN_SUBMIT","COMMAND24 Contact Info result---"+result);
			if(!result) {
				//formObject.setStyle("static_next",DISABLE,FALSE);
				return "";
			}
			if(!formObject.getValue(CUST_NATIONALITY).toString().equalsIgnoreCase("United Arab Emirates")) {
				result = mandatoryEmploymentInfo();
				logInfo("BTN_SUBMIT","COMMAND24 Employment result---"+result);
				if(!result) {
					//formObject.setStyle("static_next",DISABLE,FALSE);
					return "";
				}
			} else {
				if(!formObject.getValue(ED_DATE_OF_JOING).toString().equalsIgnoreCase("")) {
					if(!validateFutureDate(ED_DATE_OF_JOING,"Date of Joining")) {
						//formObject.setStyle("static_next",DISABLE,FALSE);
						return "";
					}
				}
			}
			result = MandatoryiKYC_CPD();
			logInfo("BTN_SUBMIT","COMMAND24 KYC result---"+result);
			if(!result) {
				return "";
			}
			result= mandatoryAtFatca();
			logInfo("BTN_SUBMIT","Submit validation fatca result now---"+result);
			if(!result) {
				formObject.setStyle("SUBMIT_1",DISABLE,FALSE);
				return "";
			}
			if(!validatePassportType(CHECKBOX_PASSPORT_TYPE_FCR,CHECKBOX_PASSPORT_TYPE_EIDA,
					CHECKBOX_PASSPORT_TYPE_MANUAL,FCR_PASSTYPE,EIDA_PASSPORT_TYPE,MANUAL_PASSTYPE,CA018,HD_PASS_TYPE)) {
				return "";
			}
			boolean result1=false;
			logInfo("","TRSDclicked  ----");
			boolean check =  isControlEnabled(BTN_CPD_TRSD_CHK);
			if(check) {
				logInfo("BTN_SUBMIT","inside iffffffffffffffff");
				sendMessageValuesList(TRSD_CHECK,"Please perform TRSD Check again as some of the customer data was changed.");
				return "";
			}			
			boolean crscategory=mandatoryCRSCheckcategorychange();
			if(!crscategory && formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")) {
				return "";
			}
			/*result1 = mandatoryCustScreen();     //MandatoryCustScreenCPD();
			if(!result1) {
				return "";
			}*/
			if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("New Account with Category Change") 
					|| formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only")) {
				result = mandatoryCategoryChangeData();
				if(!result) {
					return "";
				}
				result = checkNatCatSegment();
				if(!result) {
					formObject.setStyle(BTN_SUBMIT,DISABLE,FALSE);
					return "";
				}
				/*if(validateFBFetch()) { // family Banking submit validation
					   isValidateFBDone();
				}*///FB SUPPRESSED
				if(validateFBFetch()) { // family Banking submit validation
				   isValidateFBDone();
				}//FB ADDED
			}
			try {
				if(formObject.getValue(NOM_REQ).toString().equalsIgnoreCase("Yes")) {
					if(formObject.getValue(EXISTING_NOM_PRSN).toString().equalsIgnoreCase("Yes"))	{
						if(!data.isEmpty() && data.split("%%%").length == 3) {
							String[] dataArr = data.split("%%%");
							logInfo("BTN_SUBMIT","dataArr[2]: "+dataArr[2]);
							if(!nomDetailsUpdate(Integer.parseInt(dataArr[2]))) {
								return "";
							}
						} else {
							if(!nomDetailsUpdate(-1)) {
								return "";
							}
						}
					}
				} else {
					nomDetailsInsert();
				}
			} catch (Exception e1) {
				logError("BTN_SUBMIT", e1);
			}		
			try {
				String sFinalDOB = getFinalDataComparison(CHECKBOX_DOB_FCR,CHECKBOX_DOB_EIDA,CHECKBOX_DOB_MANUAL,
						FCR_DOB,EIDA_DOB,MANUAL_DOB);
				int age = calculateAge(sFinalDOB);
				int age1 = CalculateAge1(sFinalDOB);
				int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
				String sAccRelation = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 9);
				String sQueryy="select VALUE from usr_0_defaultvalue_fcr where name='Minor_Age'";
				List<List<String>> sOutputt=formObject.getDataFromDB(sQueryy);
				logInfo("BTN_SUBMIT","sOutputt------"+sOutputt);
				int sMinorAge= Integer.parseInt(sOutputt.get(0).get(0));
				logInfo("BTN_SUBMIT","sMinorAge....."+sMinorAge);
				if(sAccRelation.equalsIgnoreCase("Minor")) {
					if(age1 >= sMinorAge) {//21
						String Focus = "";
						if(formObject.getValue(CHECKBOX_DOB_FCR).toString().equalsIgnoreCase("true")){
							Focus = FCR_DOB;
						} else if(formObject.getValue(CHECKBOX_DOB_EIDA).toString().equalsIgnoreCase("true")) {
							Focus = "DOB_EIDA";
						} else if(formObject.getValue(CHECKBOX_DOB_MANUAL).toString().equalsIgnoreCase("true")) {
							Focus = MANUAL_DOB;
						}
						sendMessageValuesList(Focus,"Date Of Birth Should Be Less Than "+sMinorAge
								+" Years For Minor Customer.");
						return "";	
					}
				} else {
					if(!formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only")) {
						if(age<18) {
							String Focus = "";
							if(formObject.getValue(CHECKBOX_DOB_FCR).toString().equalsIgnoreCase("true")){
								Focus = FCR_DOB;
							} else if(formObject.getValue(CHECKBOX_DOB_EIDA).toString().equalsIgnoreCase("true")) {
								Focus = EIDA_DOB;
							} else if(formObject.getValue(CHECKBOX_DOB_MANUAL).toString().equalsIgnoreCase("true")) {
								Focus = MANUAL_DOB;
							}		
							sendMessageValuesList(Focus,"Date Of Birth Should Be Greater Than or equal to 18 Years.");
							return "";	
						}
					}
				}
			} catch(Exception e) {
				logError("BTN_SUBMIT", e);
			}
			if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")) {
				if(!checkMandatoryDoc(data)) {			
					formObject.setStyle(BTN_SUBMIT, DISABLE, FALSE);
					return "";
				}
			}
			int iProcessedCustomer=Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()); 
			String sCustNo = "";
			int iSelectedRow = 0;
			iSelectedRow = Integer.parseInt(formObject.getValue("SELECTED_ROW_INDEX").toString());
			sCustNo = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 0);
			String sBankRelation = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 7);
			String sCID = formObject.getTableCellValue(ACC_RELATION, iSelectedRow,2 );
			String sCASA= "";
			if(sBankRelation.equalsIgnoreCase("Existing")) {	
				List<List<String>> sOutput=formObject.getDataFromDB("SELECT COUNT(1) AS COUNT_WI FROM "
						+ "USR_0_PRODUCT_EXISTING WHERE WI_NAME ='"+sWorkitemId+"' AND CUSTOMER_ID ='"+sCID
						+"' AND ACCOUNT_TYPE ='CASA'");
				sCASA = sOutput.get(0).get(0);						
			}
			if(sBankRelation.equalsIgnoreCase("New") || sCASA.equalsIgnoreCase("0")) {
				String output = getApplicationRiskInputXML(iProcessedCustomer+1);
				logInfo("BTN_SUBMIT","XML:"+output);
				String outputresult = socket.connectToSocket(output); 
				logInfo("BTN_SUBMIT","ApplicationRisk output: "+outputresult);
				if(outputresult != null && outputresult.equalsIgnoreCase("NO")) {
					sendMessageValuesList("", "Selected passport holder and Non UAE Residents, not allowed to open Account");
					formObject.setStyle(BTN_SUBMIT,DISABLE,FALSE);
					return "";
				} else if(outputresult != null && outputresult.equalsIgnoreCase("Partial")) {
					/*int respose=JOptionPane.showConfirmDialog(null,"Selected passport holder Residents does not meet conditions,\nHence not allowed to open Account. Do you still want to proceed with account opening?",null,JOptionPane.YES_NO_OPTION);
					if(respose==JOptionPane.YES_OPTION) {
						formObject.setValue(NIG_CPDMAKER,"YES");
						String updatequery="update USR_0_CUST_TXN set NIGEXCEPTIONCPDMAKER='YES' Where WI_NAME='"+formObject.getValue("WI_Name")+"' AND CUST_SNO ='"+sCustNo+"'";
						formObject.saveDataInDB(updatequery);
						logInfo("BTN_SUBMIT","Updated Successfully");
					} else {
						formObject.setStyle(BTN_SUBMIT,DISABLE,FALSE);
						return "";
					}*/
					sendMessageList.clear();
					return getReturnMessage(false, controlName, "Selected passport holder Residents does not meet "
							+ "conditions,\nHence not allowed to open Account. Do you still want to proceed with "
							+ "account opening?");
				}
			}
			return afterAppRiskCheck(data);
			/*saveScreeningDataCPD();
			String param = sWorkitemId+"','"+sProcessName;
			List<String> paramlist =new ArrayList<>();
			paramlist.add (PARAM_TEXT+sWorkitemId);
			paramlist.add (PARAM_TEXT+sProcessName);			
			formObject.getDataFromStoredProcedure("SP_TRSD_EMAIL_NGF", paramlist);
			formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
			if(submitCPDMakerValidations(eventType,data)) {
				if(!(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Return to Originator") || 
						formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Return") || 
						formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Reject"))) {
					if(submitValidationForWorkItem(eventType,data)) {
						String msg2 = confirmOnSubmitInForLoop();
						if(msg2.equalsIgnoreCase("true")) {
							return getReturnMessage(true, controlName, CA008);
						} else return msg2;
					}
				} else {
					return getReturnMessage(true, controlName, CA008);
				}			
			}*/
//		} else if(controlName.equalsIgnoreCase("PRODUCT_QUEUE.TRNSFR_FROM_ACC_NO")) {
		} else if(controlName.equalsIgnoreCase("table103_trnsfr_from_acc_no")) {
//			int iRows = getGridCount(PRODUCT_QUEUE);				
//			int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()); 
			String sMode = formObject.getValue("table103_mode_of_funding").toString();
			if(!sMode.equalsIgnoreCase("")) {
				String sAccNo = formObject.getValue("table103_trnsfr_from_acc_no").toString();
				String sQuery= "SELECT DISTINCT ACC_BALANCE,CURRENCY FROM USR_0_PRODUCT_EXISTING WHERE "
						+ "WI_NAME ='"+sWorkitemId+"' AND ACC_NO = '"+sAccNo+"' ";
				List<List<String>> sOutput=formObject.getDataFromDB(sQuery);
				if(sOutput.size()>0) {
					String sAccBalance = sOutput.get(0).get(0);
					String sCurrency = sOutput.get(0).get(1);
					logInfo("PRODUCT_QUEUE.TRNSFR_FROM_ACC_NO","sOutput: "+sOutput);
					logInfo("PRODUCT_QUEUE.TRNSFR_FROM_ACC_NO","sAccBalance: "+sAccBalance+", sCurrency: "
							+sCurrency);
					formObject.setValue("table103_from_acc_bal",sAccBalance);
					formObject.setValue("table103_trnsfr_from_currency",sCurrency);
				}
			} else {
				sendMessageValuesList(PRODUCT_QUEUE,"Please Select Mode of Transfer first");
				return "";
			}
//			if(iRows>0) {}
		} else if(controlName.equalsIgnoreCase(CT_BTN_RESETALL)) {
			if(!sActivityName.equalsIgnoreCase("Account_Relation")) {
				setManualFieldsBlank();
			}
		} else if(controlName.equalsIgnoreCase(BTN_DEDUPE_SEARCH)) {
			/*try {
				int sSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;				
				String sQuery1 = "SELECT IS_DEDUPE_CLICK_CPD FROM USR_0_CUST_TXN WHERE WI_NAME ='"+sWorkitemId+"'"
						       + " and cust_sno='"+sSelectedRow+"'";
				List<List<String>> output1 = formObject.getDataFromDB(sQuery1);
				String dedupeDone = "";
				if(output1 != null && output1.size() > 0) {
					dedupeDone = output1.get(0).get(0);
				}
				if(!dedupeDone.equalsIgnoreCase("true")) {
					sendMessageValuesList(BTN_DEDUPE_SEARCH,"Please Do Dedupe Search For This Customer.");
					return "";
				}
			} catch(Exception e) {
				logError("Exception in onClickEventCPDMakerFourStep",e);
			}*/
			if(formObject.getValue(MANUAL_NAME).toString().equalsIgnoreCase("") 
					&& formObject.getValue(EIDA_NAME).toString().equalsIgnoreCase("")
					&& formObject.getValue(FCR_NAME).toString().equalsIgnoreCase("")) {
				sendMessageValuesList(MANUAL_NAME,CA011);
				return "";
			}
			saveComparisonData();
			int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
			formObject.clearCombo(LVW_DEDUPE_RESULT);
			checkDuplicate(iSelectedRow);
			String sUpdateDedupe="update USR_0_CUST_TXN set IS_DEDUPE_CLICK_CPD='true' where WI_NAME='"
					+ sWorkitemId +"' and cust_sno='"+(iSelectedRow+1)+"'";
			logInfo("onClickEventCPDMakerFourStep","sUpdateDedupe: "+sUpdateDedupe);
			formObject.saveDataInDB(sUpdateDedupe);
		}/* else if (controlName.equalsIgnoreCase(BTNPROFESSION)) {  //not to be used now
			String sQuery = "Select to_char(PROFESSION_DESC) from USR_0_PROFESSION ORDER BY UPPER(PROFESSION_DESC)";
			getPopUpWindowDataSearchEnable(sQuery,controlName,"PROFESSION_DESC",1);
		} else if (controlName.equalsIgnoreCase(BTNEMLOYERNAME)) {
			String sQuery = "SELECT TO_CHAR(EMP_NAME) FROM USR_0_EMPLOYER_MASTER ORDER BY UPPER(EMP_NAME)";
			getPopUpWindowDataSearchEnable(sQuery,controlName,"EMP_NAME",1);
		}*/ else if((controlName.equalsIgnoreCase("COMMAND48") || controlName.equalsIgnoreCase("STATIC_SAVE"))) {
			try {	
				boolean prodChangeFlag = checkProdChngForNoEligibility();
				if(!prodChangeFlag) {
					logInfo("","inside if--------");
					sendMessageValuesList("","Customer is not eligible for cheque book. Please change the product.");
					return "";
				}
				int fieldToFocus=Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
				//objChkRepeater.setFocus(fieldToFocus, "AO_ACC_RELATION.SNO");
				checkView();
				saveKYCInfo();	
				saveKycMultiDropDownData();
//				savePreAssessmentDetails();    //shahbaz
				saveComparisonData();
				saveIndividualInfo();
				saveIndividualContactInfo();
				//saveDuplicateData();   
				saveClientQuestionsData();  
				saveCRSDetails(); 
				try {
					insertUdfDetails();	
				} catch (Exception e)  {
					logError("exception ", e);
				}
			}
			catch (Exception e) 
			{
				logError("excetion", e);
			}
		} else if(controlName.equalsIgnoreCase(BTN_CPD_RE_CHK)) {
			formObject.setValue(CPD_FINAL_ELIGIBILITY, "");
			formObject.setValue(SANC_SYS_DEC, "");
		} else if(controlName.equalsIgnoreCase(BTN_CPD_TRSD_CHK)) {    //TRSD_CHECK
			/*callTRSD("CPD");
			if( formObject.getValue(CPD_TRSD_FINAL_DECISION).toString().equalsIgnoreCase("Approved"))
				formObject.setValue(CPD_FINAL_ELIGIBILITY, "Eligible");
			else
				formObject.setValue(CPD_FINAL_ELIGIBILITY, "Not Eligible");
			int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
			String sCustNo = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 0);
			logInfo("CPD_TRSD_FINAL_DECISION: ",sCustNo);				

			try {
				logInfo("clicked set true arraylist",CPD_FINAL_ELIGIBILITY);
				//TRSDclicked.set(Integer.parseInt(sCustNo)-1, true);
				logInfo("TRSD clicked set true arraylist completed","");
			} catch(Exception e) {
				logError("exception trsd", e);
			}
			insertBankDecisionFromTRSD(formObject.getValue(CPD_TRSD_FINAL_DECISION).toString());
			logInfo("CPD_TRSD_FINAL_DECISION: "+formObject.getValue(CPD_TRSD_FINAL_DECISION).toString(),"");				
			if(formObject.getValue(CPD_TRSD_FINAL_DECISION).toString().equalsIgnoreCase("Approved") 
					|| formObject.getValue(CPD_TRSD_FINAL_DECISION).toString().equalsIgnoreCase("Pending")) {							
				formObject.setStyle(BTN_CPD_TRSD_CHK,DISABLE,TRUE);
			}*/
			// Added by reyaz 02-05-2023
			logInfo("CLICK","INSIDE FSK_CHECK data"+data);
			callFSKService("CPD"); 
			if(formObject.getValue("TRSD_FLAG").toString().equalsIgnoreCase("N")){
				formObject.setValue(CPD_FINAL_ELIGIBILITY, "Eligible");
			} 
			else {
				formObject.setValue(CPD_FINAL_ELIGIBILITY, "Not Eligible");
			}
			int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
			String sCustNo = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 0);
			logInfo("CPD_TRSD_FINAL_DECISION: ",sCustNo);				

			try {
				logInfo("clicked set true arraylist",CPD_FINAL_ELIGIBILITY);
				//TRSDclicked.set(Integer.parseInt(sCustNo)-1, true);
				logInfo("TRSD clicked set true arraylist completed","");
			} catch(Exception e) {
				logError("exception trsd", e);
			}
			insertBankDecisionFromTRSD(formObject.getValue(CPD_TRSD_FINAL_DECISION).toString());
			logInfo("CPD_TRSD_FINAL_DECISION: "+formObject.getValue(CPD_TRSD_FINAL_DECISION).toString(),"");				
			if(formObject.getValue("TRSD_FLAG").toString().equalsIgnoreCase("N")
					|| formObject.getValue("TRSD_FLAG").toString().equalsIgnoreCase("Y")) {							
				formObject.setStyle(BTN_CPD_TRSD_CHK,DISABLE,TRUE);
			}
		} else if(controlName.equalsIgnoreCase(CALCULATE)) {
			// Calculate Button functionality is not required as per AO Lifecycle CRs 2018
		} else if(controlName.equalsIgnoreCase(CPD_CUR_RISK_BANK) ) {
			logInfo("","currentRiskChange"+controlValue);
			if(!controlValue.equalsIgnoreCase("")) {
				if(formObject.getValue(CPD_CUR_RISK).toString().equalsIgnoreCase("Neutral Risk")) {
					if(formObject.getValue(CPD_CUR_RISK_BANK).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(CPD_CUR_RISK_BANK,CA0144);	
						formObject.setValue(CPD_CUR_RISK_BANK,"");
						return "";
					}
				}else if(formObject.getValue(CPD_CUR_RISK).toString().equalsIgnoreCase("Medium Risk")) {     //Changed 27022023
					if(formObject.getValue(CPD_CUR_RISK_BANK).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(CPD_CUR_RISK_BANK,CA0144);	
						formObject.setValue(CPD_CUR_RISK_BANK,"");
						return "";
					}
				} else if(formObject.getValue(CPD_CUR_RISK).toString().equalsIgnoreCase("Increased Risk")) {
					if((formObject.getValue(CPD_CUR_RISK_BANK).toString().equalsIgnoreCase("Neutral")) 
					||(formObject.getValue(CPD_CUR_RISK_BANK).toString().equalsIgnoreCase("Medium Risk"))){    //Changed 27022023
						sendMessageValuesList(CPD_CUR_RISK_BANK,CA0144);	
						formObject.setValue(CPD_CUR_RISK_BANK,"");
						return "";
					}
				} else if(formObject.getValue(CPD_CUR_RISK).toString().equalsIgnoreCase("UAE-PEP")) { // WMS WORKFLOW SAHIL_CR
					if((formObject.getValue(CPD_CUR_RISK_BANK).toString().equalsIgnoreCase("Neutral"))
							||(formObject.getValue(CPD_CUR_RISK_BANK).toString().equalsIgnoreCase("Medium Risk"))		//Changed 27022023
						||(formObject.getValue(CPD_CUR_RISK_BANK).toString().equalsIgnoreCase("Increased Risk"))) {
						sendMessageValuesList(CPD_CUR_RISK_BANK,CA0144);	
						formObject.setValue(CPD_CUR_RISK_BANK,"");
						return "";
					}
				} else if(formObject.getValue(CPD_CUR_RISK).toString().equalsIgnoreCase("Unacceptable Risk")) {
					if((formObject.getValue(CPD_CUR_RISK_BANK).toString().equalsIgnoreCase("Neutral"))
							||(formObject.getValue(CPD_CUR_RISK_BANK).toString().equalsIgnoreCase("Medium Risk"))		//Changed 27022023
							||(formObject.getValue(CPD_CUR_RISK_BANK).toString().equalsIgnoreCase("Increased Risk"))
							||(formObject.getValue(CPD_CUR_RISK_BANK).toString().equalsIgnoreCase("UAE-PEP"))) {  //SAHIL_CR WMSWORKFLOW 
						sendMessageValuesList(CPD_CUR_RISK_BANK,CA0144);	
						formObject.setValue(CPD_CUR_RISK_BANK,"");
						sendMessageValuesList(CPD_CUR_RISK_BANK,CA0144);	
						return "";
					}
				}
				int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()); 
				String sAccRelation = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 7);
				String sPrevRisk ="";
				String sPrevRiskDate ="";
				String sComplainceApproval ="";
				String sCustID = formObject.getValue("CPD_ID").toString();
				if(!sCustID.equalsIgnoreCase("") && sAccRelation.equalsIgnoreCase("Existing")) {
					List<List<String>> sOutput=formObject.getDataFromDB("SELECT CURRENT_RISK_SYSTEM,TO_CHAR(PREV_RISK_DATE,'"+("DATEFORMAT")+"') PREV_RISK_DATE FROM USR_0_RISK_ASSESSMENT_DATA_CPD WHERE CUST_ID='"+sCustID+"' AND PREV_RISK_DATE =(SELECT MAX(PREV_RISK_DATE) FROM USR_0_RISK_ASSESSMENT_DATA_CPD WHERE CUST_ID='"+sCustID+"') AND ROWNUM=1");
					sPrevRisk = sOutput.get(0).get(0);
					sPrevRiskDate = sOutput.get(0).get(1);
				}
				if(controlValue.equalsIgnoreCase("Neutral")) {
					sComplainceApproval="No";
				}else if(formObject.getValue(controlName).toString().equalsIgnoreCase("Medium Risk")){    //changed 27022023
					sComplainceApproval="No";
				} else if(controlValue.equalsIgnoreCase(sPrevRisk) && !sPrevRiskDate.equalsIgnoreCase("")) {
					sComplainceApproval="No";
				} else {
					sComplainceApproval="Yes";
				}
				formObject.setValue(CPD_RISK_COMPL_APP_REQ, sComplainceApproval);
			} else {
				logInfo("","before calling set_Values_From_Usr_0_Risk_Data in cpd maker 4 step cust_curr_risk_bank is null");
				set_Values_From_Usr_0_Risk_Data();
			}
		} else if(controlName.equalsIgnoreCase(BTN_DOCUMENT_LINK)){
			saveScreeningDataCPD();
		} else if(controlName.equalsIgnoreCase("DELETEROW")) {
			int iRows = getGridCount("queue_dc");
			int iSelectedRow = Integer.parseInt(data);
			if(iRows==0){
				sendMessageValuesList("queue_dc", "Please add a row first in the grid.");
				return "";
			}
			if(iRows != iSelectedRow+1) {
				sendMessageValuesList("queue_dc", "Only last row can be deleted.");
				return "";
			} 
		} else if(controlName.equalsIgnoreCase(BTN_FCR_SRCH)) {
			String sCustomerID =formObject.getValue(IDS_REF_BY_CUST).toString();
			if(sCustomerID.equalsIgnoreCase("")) {
				sendMessageValuesList(IDS_REF_BY_CUST,CA093);
				return "";
			}
			String sQuery ="SELECT CUST_FULL_NAME, CUST_DOB FROM USR_0_CUST_MASTER WHERE CUST_ID='"+sCustomerID+"'";
			List<List<String>> sOutput= formObject.getDataFromDB(sQuery);
			logInfo("","sOutput-----"+sOutput);	
			if(!(sOutput.size() == 0)) {	
				int reply = JOptionPane.showConfirmDialog(null,"This customer id has following details :"+"\n"+"\n"+"Name : "+sOutput.get(0).get(0)+"\n"+"DOB : "+sOutput.get(0).get(1)+"\n"+"\n"+"Is it correct?", null, JOptionPane.YES_NO_OPTION);	
				if (reply == JOptionPane.YES_OPTION) {
					sendMessageValuesList("","Customer validated successfully");
					validate=true;
					return "";
				} else {
					sendMessageValuesList("","Please enter a new customer id");
					formObject.setValue(IDS_REF_BY_CUST,"");
					return "";
				}	
			} else {
				sendMessageValuesList("","No Customer present with this ID");
				return "";
			}
		} else if(controlName.equalsIgnoreCase(SEARCH)) {
			searchProductList("USR_0_PRODUCT_OFFERED_CPD",data);
		} else if(controlName.equalsIgnoreCase(BTN_ECD_VALIDATE)) {
			String sEtihadNo  = formObject.getValue(ETIHAD_NO).toString();
			String sEtihadExist  = formObject.getValue(EXISTING_ETIHAD_CUST).toString();
			if(sEtihadExist.equalsIgnoreCase("")) {
				sendMessageValuesList(EXISTING_ETIHAD_CUST,"Please select Existing Etihad Customer");
				return "" ;
			}
			if(sEtihadNo.equalsIgnoreCase("")) {
				sendMessageValuesList(ETIHAD_NO,"Please fill Etihad Guest Number");
				return "";
			}				
			String sCustomerID = getPrimaryCustomerID();
			String sOutput  = fetchEtihadDetails(sCustomerID,sEtihadNo);
			String sReturnCode = getTagValues(sOutput,"ResponseCode");
			String sReturnValue = getTagValues(sOutput,"ResponseValue");

			if(sReturnCode.equalsIgnoreCase("00")) {
				sendMessageValuesList("","Etihad Number Validated Successfully.");
				iEtihadValidate=1;
				formObject.setValue(IS_ETIHAD,"1");
				return "";
			} else if(sReturnCode.equalsIgnoreCase("01")) {
				if(!sCustomerID.equalsIgnoreCase("") && sReturnValue.equalsIgnoreCase(sCustomerID)) {
					sendMessageValuesList("","Etihad Number Validated Successfully.");
					iEtihadValidate=2;
					formObject.setValue(IS_ETIHAD,"2");
					return "";
				} else {
					sendMessageValuesList(ETIHAD_NO,"Etihad Number is already associated with customer ID "+sReturnValue+"\n"+"Please enter a new Etihad Number");
					formObject.setValue(ETIHAD_NO,"");
					return "";
				}
			} else {
				sendMessageValuesList(ETIHAD_NO,"Etihad Number is not valid. Please enter a new Etihad Number");
				formObject.setValue(ETIHAD_NO,"");
				return "";
			}
		} else if(controlName.equalsIgnoreCase(BTN_AI_CLOSE)) {					
			deleteSelectedProduct();
			EnableFamilyReffered();
		} else if(controlName.equalsIgnoreCase(ADD_PRODUCT)) {   // no need diabled on form
			//			logInfo("","In Add Product");
			//			NGRepeater objChkRepeater = formObject.getNGRepeater("ACC_REPEATER");
			//			logInfo("","In Add Product after repeater");
			//			objChkRepeater.addRow();
		} else if(controlName.equalsIgnoreCase(REMOVE_PRODUCT)) {
			if(removeProducts(Integer.parseInt(data))){
				return getReturnMessage(true, controlName);
			}
		} else if(controlName.equalsIgnoreCase(EMPNAME) && !(controlValue.equalsIgnoreCase("") && controlValue.equalsIgnoreCase(""))) {
			List<List<String>> sOutput=formObject.getDataFromDB("SELECT CD_STATUS FROM USR_0_EMPLOYER_MASTER WHERE EMP_NAME ='"+controlValue+"'");
			logInfo("","Emp Mast Output---"+sOutput);
			formObject.setValue(ED_CB_TML,"False");
			formObject.setValue(ED_CB_NON_TML,"False");
			if(sOutput.get(0).get(0).equalsIgnoreCase("1") || sOutput.get(0).get(1).equalsIgnoreCase("2")) {
				formObject.setValue(ED_CB_TML,"True");
			} else {
				formObject.setValue(ED_CB_NON_TML,"True");
			}
			
			logInfo("","Inside EMPNAME");
			String EmpName=formObject.getValue(EMPNAME).toString();
			if(!(formObject.getValue(EMPNAME).toString().equalsIgnoreCase("")) 
					&& !(formObject.getValue(EMPNAME).toString().equalsIgnoreCase("OTHERS"))){
				logInfo("","When EmpName is not empty");
				String sqString = "Select CD_ADDRESS1,CD_PO_BOX_NO,CD_CITY,CD_STATE from company_details where CD_NAME like '%"+EmpName+"%'";
				List<List<String>> sOutput1=formObject.getDataFromDB("");
				if(sOutput1!= null && sOutput1.size()>0){
				logInfo("","company_details1 Output---"+sOutput);
				formObject.setValue(EMP_STREET,sOutput1.get(0).get(0));

				formObject.setValue(EMP_PO_BOX,sOutput1.get(0).get(1));
				
				formObject.setValue(EMP_CITY,sOutput1.get(0).get(2));

				formObject.setValue(EMP_STATE,sOutput1.get(0).get(3));
				}
			}
			else{
				formObject.setValue(EMP_STREET,"");
				formObject.setValue(EMP_PO_BOX,"");
				formObject.setValue(EMP_CITY,"");
				formObject.setValue(EMP_STATE,"");
				formObject.setValue(EMP_COUNTRY,"");
			}
		} /*else if (controlName.equalsIgnoreCase("SRC_MAKER_BTN")) {  
			String sQuery = "SELECT DSA_NAME, DSA_CODE FROM USR_0_DSA_DETAILS UNION SELECT PERSONALNAME||' '||FAMILYNAME,USERNAME FROM PDBUSER where deletedflag <>'Y' AND USERALIVE ='Y'";
			NGPickList objPickList = formObject.getNGPickList("AO_SOURCE_CODE","SOURCE NAME,SOURCE CODE",true);
			objPickList.setCacheEnable(true);
			objPickList.setSearchEnable(true);
			popupWindow thisPopup = new popupWindow(formObject,objPickList,sQuery,100,2,"AO_SOURCE_CODE");
			objPickList.addPickListListener(thisPopup);
			objPickList.setVisible(true);
		} else if (controlName.equalsIgnoreCase("SRC_CODE_BTN")) {          
			NGPickList objPickList = formObject.getNGPickList("AO_SOURCE_NAME","SOURCE CODE,SOURCE NAME",true);
			String sQuery = "SELECT DSA_CODE,DSA_NAME FROM USR_0_DSA_DETAILS UNION SELECT USERNAME,PERSONALNAME||' '||FAMILYNAME FROM PDBUSER where deletedflag <>'Y' AND USERALIVE ='Y'";
			objPickList.setCacheEnable(true);
			objPickList.setSearchEnable(true);
			popupWindow thisPopup = new popupWindow(formObject,objPickList,sQuery,100,2,"AO_SOURCE_NAME");
			objPickList.addPickListListener(thisPopup);
			objPickList.setVisible(true);
		} else if (controlName.equalsIgnoreCase("SRC_CODECC_BTN")) {          
			NGPickList objPickList = formObject.getNGPickList("AO_SOURCE_CODE_CAT_CHANGE","SOURCE CODE,SOURCE NAME",true);
			String sQuery = "SELECT DSA_CODE,DSA_NAME FROM USR_0_DSA_DETAILS UNION SELECT USERNAME,PERSONALNAME||' '||FAMILYNAME FROM PDBUSER where deletedflag <>'Y' AND USERALIVE ='Y'";
			objPickList.setCacheEnable(true);
			objPickList.setSearchEnable(true);
			popupWindow thisPopup = new popupWindow(formObject,objPickList,sQuery,100,2,"AO_SOURCE_CODE_CAT_CHANGE");
			objPickList.addPickListListener(thisPopup);
			objPickList.setVisible(true);
		} else if (controlName.equalsIgnoreCase("SRC_NAME_BTN")) {          
			NGPickList objPickList = formObject.getNGPickList("AO_SOURCE_NAME_CAT_CHANGE","SOURCE NAME,SOURCE CODE",true);
			String sQuery = "SELECT DSA_NAME, DSA_CODE FROM USR_0_DSA_DETAILS UNION SELECT PERSONALNAME||' '||FAMILYNAME,USERNAME FROM PDBUSER where deletedflag <>'Y' AND USERALIVE ='Y'";
			objPickList.setCacheEnable(true);
			objPickList.setSearchEnable(true);
			popupWindow thisPopup = new popupWindow(formObject,objPickList,sQuery,100,2,"AO_SOURCE_NAME_CAT_CHANGE");
			objPickList.addPickListListener(thisPopup);
			objPickList.setVisible(true);
		} else if (controlName.equalsIgnoreCase("STAFF_REFER_BTN")) {          
			NGPickList objPickList = formObject.getNGPickList("REF_BY_STAFF","SOURCE NAME,SOURCE CODE",true);
			String sQuery = "SELECT DSA_NAME,DSA_CODE FROM USR_0_DSA_DETAILS UNION SELECT PERSONALNAME||' '||FAMILYNAME,USERNAME FROM PDBUSER where deletedflag <>'Y'";
			objPickList.setCacheEnable(true);
			objPickList.setSearchEnable(true);
			popupWindow thisPopup = new popupWindow(formObject,objPickList,sQuery,100,2,"REF_BY_STAFF");
			objPickList.addPickListListener(thisPopup);
			objPickList.setVisible(true);
		} else if ((controlName.equalsIgnoreCase("BTN_RM_CODE") || controlName.equalsIgnoreCase("BTN_RM_NAME"))) {
			NGPickList objPickList = formObject.getNGPickList("RM_CODE","RM_CODE,RM_NAME",true);
			objPickList.setSearchEnable(true);
			String sQuery = "Select RM_CODE,RM_NAME from USR_0_RM ORDER BY UPPER(RM_CODE)";
			popupWindow thisPopup = new popupWindow(formObject,objPickList,sQuery,300,2,pControlName);
			objPickList.addPickListListener(thisPopup);
			objPickList.setVisible(true);
		} else if ((controlName.equalsIgnoreCase("BTN_RM_CODE_CAT_CHANGE") || controlName.equalsIgnoreCase("BTN_RM_NAME_CAT_CHANGE"))) {
			NGPickList objPickList = formObject.getNGPickList("AO_NEW_RM_CODE_CAT_CHANGE","RM_CODE,RM_NAME",true);
			objPickList.setSearchEnable(true);
			String sQuery = "Select RM_CODE,RM_NAME from USR_0_RM ORDER BY UPPER(RM_CODE)";
			popupWindow thisPopup = new popupWindow(formObject,objPickList,sQuery,300,2,pControlName);
			objPickList.addPickListListener(thisPopup);
			objPickList.setVisible(true);
		}*/ else if (controlName.equalsIgnoreCase(CRO_DEC)){
			if(controlValue.equalsIgnoreCase("Approve with Additional Approval") || controlValue.equalsIgnoreCase("Send To Compliance")){
				formObject.setStyle(L1_APP_REQ,DISABLE,FALSE);
				formObject.setStyle(L2_APP_REQ,DISABLE,FALSE);
				formObject.setStyle(L3_APP_REQ,DISABLE,FALSE);
				formObject.setStyle(L4_APP_REQ,DISABLE,FALSE);
				if(formObject.getValue(FINAL_RISK_VAL).toString().equalsIgnoreCase("Increased Risk")){
					formObject.setValue(L1_APP_REQ,"true");
					formObject.setValue(L2_APP_REQ,"true");
					formObject.setValue(L3_APP_REQ,"true");
					formObject.setValue(L4_APP_REQ,"false");
				} else if(formObject.getValue(FINAL_RISK_VAL).toString().equalsIgnoreCase("UAE-PEP")){
					formObject.setValue(L1_APP_REQ,"true");
					formObject.setValue(L2_APP_REQ,"true");
					formObject.setValue(L3_APP_REQ,"true");
					formObject.setValue(L4_APP_REQ,"true");
				} else {
					formObject.setValue(L1_APP_REQ,"false");
					formObject.setValue(L2_APP_REQ,"false");
					formObject.setValue(L3_APP_REQ,"false");
					formObject.setValue(L4_APP_REQ,"false");
				}
				if(controlValue.equalsIgnoreCase("Approve with Additional Approval")){
					formObject.setStyle(CRO_REJ_REASON,DISABLE,TRUE);
				} else{
					formObject.setStyle(CRO_REJ_REASON,DISABLE,FALSE);
				}
			} else {
				formObject.setValue(L1_APP_REQ,"false");
				formObject.setValue(L2_APP_REQ,"false");
				formObject.setValue(L3_APP_REQ,"false");
				formObject.setValue(L4_APP_REQ,"false");
				formObject.setStyle(L1_APP_REQ,DISABLE,TRUE);
				formObject.setStyle(L2_APP_REQ,DISABLE,TRUE);
				formObject.setStyle(L3_APP_REQ,DISABLE,TRUE);
				formObject.setStyle(L4_APP_REQ,DISABLE,TRUE);
			}
		} else if(controlName.equalsIgnoreCase(BTN_NEXT_CUST_SANCT)) {
			logInfo("","saving data");
			int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
			String sCustNo = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 0);
			int sOutput= updateDataInDB("USR_0_BRMS_TRACKER","SCREENING_STATUS","'Success'",
					"WI_NAME ='"+sWorkitemId+"' AND CUST_SNO='"+sCustNo+"' AND SCREENING_STATUS ='Pending'");
			logInfo("","sOutput----"+sOutput);
			saveScreeningDataCPD();					
			if(!processedCustomerList.contains(iSelectedRow)) {
				processedCustomerList.add(iSelectedRow+"");
			}

			Collections.sort(processedCustomerList);
			boolean bSnoSet = false;

			for(int i=0;i<processedCustomerList.size();i++) {
				if(!processedCustomerList.get(i).equalsIgnoreCase(i+1+"")) {
					bSnoSet = true;
					loadCustDataOnRepeaterSelect((data));    // need to pass data from js
					break;
				}
			}
			if(bSnoSet == false) {
				loadCustDataOnRepeaterSelect(data);
			}
		} else if(controlName.equalsIgnoreCase(BTN_VALIDATE)) {
			ValidateFATCADetails("Mini");
			formObject.setStyle(COMBODOC,DISABLE,FALSE);
		} else if(controlName.equalsIgnoreCase(BTN_VALIDATEFATCA) ) {
			ValidateFATCADetails("Main");
		}  
		return "";
	}

	private void onLostFocusEventCPDMakerFourStep(String controlName, String eventType, String data) {
		if(controlName.equalsIgnoreCase(MANUAL_DOB)) {
			logInfo("onLostFocusEventCPDMakerFourStep","In dob Manual----"
		+formObject.getValue(CHECKBOX_DOB_MANUAL).toString());
			if(formObject.getValue(CHECKBOX_DOB_MANUAL).toString().equalsIgnoreCase(TRUE)) {
				logInfo("onLostFocusEventCPDMakerFourStep","In dob Manual");
				formObject.setValue("MASK2",formObject.getValue(controlName).toString());
				logInfo("onLostFocusEventCPDMakerFourStep","In dob Manual Changed");
			}
		} else if(controlName.equalsIgnoreCase(MANUAL_PASSPORTISSDATE)) {
			formObject.setValue(HD_PASS_ISS_DATE,formObject.getValue(controlName).toString());
		} else if(controlName.equalsIgnoreCase(MANUAL_PASSPORTEXPDATE)) {
			formObject.setValue(HD_PASS_EXP_DATE,formObject.getValue(controlName).toString());
		} else if(controlName.equalsIgnoreCase(MANUAL_VISAISSDATE)) {
			formObject.setValue(HD_VISA_ISSUE_DATE,formObject.getValue(controlName).toString());
		} else if(controlName.equalsIgnoreCase(MANUAL_VISAEXPDATE)) {
			formObject.setValue(HD_EXP_DATE,formObject.getValue(controlName).toString());
		} else if(controlName.equalsIgnoreCase("AO_PRODUCT_QUEUE.PROD_CODE")
				||controlName.equalsIgnoreCase("AO_PRODUCT_QUEUE.CURRENCY")) {
			int iRows = getGridCount(PRODUCT_QUEUE);					
			int iSelectedRow = Integer.parseInt(data);
			String prod = formObject.getTableCellValue(PRODUCT_QUEUE,iSelectedRow,1);
			if(iRows>1 && !prod.equalsIgnoreCase("")) {
				String sAccClass = formObject.getValue(ACC_HOME_BRANCH).toString();
				if(controlName.equalsIgnoreCase("AO_PRODUCT_QUEUE.PROD_CODE")) {
					logInfo("onLostFocusEventCPDMakerFourStep",
							"inside if condition LOSTFOCUS....AO_PRODUCT_QUEUE.PROD_CODE...");
					setProductCurrencyCombo1(prod,sAccClass);
					//objChkRepeater.setEnabled(iSelectedRow,"AO_PRODUCT_QUEUE.CURRENCY",true);
					//objChkRepeater.setEditable(iSelectedRow, "AO_PRODUCT_QUEUE.CURRENCY", true);
				}
				if(controlName.equalsIgnoreCase("AO_PRODUCT_QUEUE.CURRENCY")) {
					logInfo("onLostFocusEventCPDMakerFourStep",
							"inside if condition LOSTFOCUS....AO_PRODUCT_QUEUE.CURRENCY...");
				}
				String sCurrCode = formObject.getTableCellValue(PRODUCT_QUEUE,iSelectedRow,3);//currency
				logInfo("onLostFocusEventCPDMakerFourStep","sCurrCode......"+sCurrCode);
				String sQuery="select b.CURRENCY_SHORT_NAME as CURRENCY_SHORT_NAME,b.product_desc as "
						+ "PRODUCT_DESC, DECODE(a.CHEQUE_BOOK_FAC,'Y','Yes','No') CHEQUE_BOOK "
						+ "from USR_0_PRODUCT_OFFERED_CPD B ,USR_0_PRODUCT_MASTER a "
						+ "where b.product_code='"+prod+"' AND b.WI_NAME ='"+sWorkitemId+"'"
						+ " and a.product_code=b.product_code";	
				List<List<String>> recordList = formObject.getDataFromDB(sQuery);
				logInfo("onLostFocusEventCPDMakerFourStep",""+sQuery);
				logInfo("onLostFocusEventCPDMakerFourStep",""+recordList);
				String sEmail  = "SELECT final_email FROM USR_0_CUST_TXN WHERE cust_sno='"+getPrimaryCustomerSNO()+"'"
						+ " AND WI_NAME = '"+sWorkitemId+"'";
				logInfo("onLostFocusEventCPDMakerFourStep","sQuery---"+sEmail);
				List<List<String>> sEmailOutput = formObject.getDataFromDB(sEmail);
				String email = sEmailOutput.get(0).get(0);
				if(email.equalsIgnoreCase("")) {
					String sQuery1  = "SELECT COUNT(SUB_PRODUCT_TYPE) as SUB_PRODUCT_TYPE "
							+ "FROM USR_0_PRODUCT_TYPE_MASTER WHERE PRODUCT_CODE = '"+prod+"'"
							+ " AND UPPER(SUB_PRODUCT_TYPE) ='ETIHAD'";
					logInfo("onLostFocusEventCPDMakerFourStep","sQuery---"+sQuery1);
					List<List<String>> sOutput1 = formObject.getDataFromDB(sQuery1);
					if(!sOutput1.get(0).get(0).equalsIgnoreCase("0")) {
						sendMessageValuesList(PRODUCT_QUEUE, "Primary Customer's Email Id Is "
								+ "Mendatory For Etihad Product.");
						formObject.setTableCellValue(PRODUCT_QUEUE,iSelectedRow,1,"");
					}
				}
				int iNoOfProduct = recordList.size();
				if(iNoOfProduct==0) {
					sendMessageValuesList(PRODUCT_QUEUE, "This product is not allowed for this customer");
					formObject.setTableCellValue(PRODUCT_QUEUE,iSelectedRow,1,"");
				} else {
					String sChequebook = recordList.get(0).get(1);
					if(sChequebook.equalsIgnoreCase("Yes")|| sChequebook.equalsIgnoreCase("Y")) {
						logInfo("onLostFocusEventCPDMakerFourStep","inside sChequebook.. is Yes");
						sChequebook="Yes";
						formObject.setTableCellValue(PRODUCT_QUEUE,iSelectedRow,6,sChequebook);
						// objChkRepeater.setEnabled(iSelectedRow, "AO_PRODUCT_QUEUE.CHEQUE_BOOK", true);
						// objChkRepeater.setEditable(iSelectedRow, "AO_PRODUCT_QUEUE.CHEQUE_BOOK", true);
					} else {
						logInfo("onLostFocusEventCPDMakerFourStep","inside sChequebook.. is No");
						sChequebook="No";
						formObject.setTableCellValue(PRODUCT_QUEUE,iSelectedRow,6,sChequebook);
						// objChkRepeater.setEnabled(iSelectedRow, "AO_PRODUCT_QUEUE.CHEQUE_BOOK", false);
						// objChkRepeater.setEditable(iSelectedRow, "AO_PRODUCT_QUEUE.CHEQUE_BOOK", false);
					}
					String getquery="SELECT visa_status FROM USR_0_CUST_TXN WHERE cust_sno='"
					+getPrimaryCustomerSNO()+"' AND WI_NAME = '"+sWorkitemId+"'";
					logInfo("onLostFocusEventCPDMakerFourStep","getquery---"+getquery);
					List<List<String>> getqueryOutput = formObject.getDataFromDB(getquery);
					String Visa =  getqueryOutput.get(0).get(0);
					logInfo("onLostFocusEventCPDMakerFourStep","getquery---"+Visa);
					if(Visa.equalsIgnoreCase("Under Processing")) {
						formObject.setTableCellValue(PRODUCT_QUEUE,iSelectedRow,6,"No");
						// objChkRepeater.setEnabled(iSelectedRow, "AO_PRODUCT_QUEUE.CHEQUE_BOOK", false);
						// 	objChkRepeater.setEditable(iSelectedRow, "AO_PRODUCT_QUEUE.CHEQUE_BOOK", false);
					}
					String sProductDesc = recordList.get(0).get(1);
					logInfo("onLostFocusEventCPDMakerFourStep","sProductDesc"+sProductDesc);
					formObject.setTableCellValue(PRODUCT_QUEUE,iSelectedRow,2,sProductDesc);
					formObject.setTableCellValue(PRODUCT_QUEUE,iSelectedRow,16,sWorkitemId);
					formObject.setTableCellValue(PRODUCT_QUEUE,iSelectedRow,14,(iRows-1)+"");
					sQuery= "select (case when a.COD_PROD_TYPE='C' then TO_CHAR(c.CODE) else "
							+ "DECODE(TO_CHAR(c.EQV_ISLAMIC_BR_CODE),NULL,TO_CHAR(c.CODE),"
							+ "TO_CHAR(c.EQV_ISLAMIC_BR_CODE)) end) CODE "
							+ "from usr_0_product_offered_cpd B,USR_0_PRODUCT_MASTER A,USR_0_HOME_BRANCH c "
							+ "where a.product_code= b.product_code and UPPER(b.WI_NAME) = UPPER('"+sWorkitemId+"') "
							+ "and  c.HOME_BRANCH='"+sAccClass+"' and b.product_code='"+prod+"' and rownum=1";
					logInfo("onLostFocusEventCPDMakerFourStep","sQuery"+sQuery);
					List<List<String>> sOutput = formObject.getDataFromDB(sQuery);
					formObject.setTableCellValue(PRODUCT_QUEUE,iSelectedRow,5,sOutput.get(0).get(0));
					sQuery  = "SELECT SUB_PRODUCT_TYPE FROM USR_0_PRODUCT_TYPE_MASTER WHERE PRODUCT_CODE ='"+prod+"'";
					List<List<String>> sOutput1 = formObject.getDataFromDB(sQuery);
					if(sOutput1.get(0).get(0).equalsIgnoreCase("Etihad")) {
						Frame37_CPD_ENable();
					}
				}
			}
			LoadDebitCardCombo();
			EnableFamilyReffered();
		}
	}

	private void gotFocusCustInfoDataCPD() {
		logInfo("gotFocusCustInfoDataCPD", "INSIDE");
		int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
		logInfo("gotFocusCustInfoDataCPD", "fieldToFocus: "+fieldToFocus);
		String sBankRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);  
		String sAccRelation =  formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 9);
		String sName = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 1); 
		String sDOB = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 5); 
		String cust_id =  formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 2); 
		long start_Time1=System.currentTimeMillis();
		setManualFieldsBlank();
		setManualChecksBlank();
		clearComparisonFields();
		clearPersonalData();
		clearKYCData();
		clearRiskData();
		populatePersonalDataCPD();
		populateRiskData();
		populateKYCData();
		populateKycMultiDrop();
		populatePreAssesmentDetails();  //shahbaz
		//PopulateCRSData();  
		PopulatePrivateClientQuestions(); 
		populateComparisonFields();
		setManualFieldsEnable();
		setDetailsinBelowfields();
		setCustomerRelation();
		loadDedupeSearchData(sWorkitemId);
		manageCustomerChangeCheckboxes(sBankRelation,sAccRelation);
		if(formObject.getValue(CHK_EMP_DETAIL).toString().equalsIgnoreCase("true")) {
			if(formObject.getValue(PROFESION).toString().equalsIgnoreCase("Others") || formObject.getValue(PROFESION).toString().equalsIgnoreCase("")) {
				formObject.setStyle(ED_OTHER, DISABLE, FALSE);
			} else {
				formObject.setStyle(ED_OTHER, DISABLE, TRUE);
			}
			if(formObject.getValue(EMPNAME).toString().equalsIgnoreCase("Others") || formObject.getValue(EMPNAME).toString().equalsIgnoreCase("")) {
				formObject.setStyle(ED_EMPNAME, DISABLE, FALSE);//02052023 by Ameena //CompanyName issue
			} else {
				formObject.setStyle(ED_EMPNAME, DISABLE, TRUE);
			}
		}
		if(formObject.getValue(CHECKBOX_SELECTALL_MANUAL).toString().equalsIgnoreCase(TRUE)) {
			manageManualCheckBoxes();
		} else {
			setManualFieldsLock();
		}
		try {
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat simpledateformat = new SimpleDateFormat(getDateValue("DATEFORMAT"));
			String scurrentDate = simpledateformat.format(calendar.getTime());
			//shahbaz
			String bank_Relationship = formObject.getTableCellValue(ACC_RELATION, 0, 7); 
			logInfo("populateLastKycDate ", "bank_Relationship: " + bank_Relationship);
			
			if(!bank_Relationship.equalsIgnoreCase("Existing")){
				if(formObject.getValue(GI_DATE_KYC_PREP).toString().equalsIgnoreCase("")) {
					formObject.setValue(GI_DATE_KYC_PREP, scurrentDate);
				}
			}
		} catch (Exception e) {
			logError("executeServerEvent", e);
		}
		if(!formObject.getValue(PD_CUSTSEGMENT).toString().equalsIgnoreCase("")) {
			if(formObject.getValue(PD_CUSTSEGMENT).toString().equalsIgnoreCase("Aspire")) {
				formObject.setValue(IDS_PROF_CENTER_CODE,"104");
			} else if(formObject.getValue(PD_CUSTSEGMENT).toString().equalsIgnoreCase("Privilege")) {
				formObject.setValue(IDS_PROF_CENTER_CODE,"171");
			} else if(formObject.getValue(PD_CUSTSEGMENT).toString().equalsIgnoreCase("Excellency")) {
				formObject.setValue(IDS_PROF_CENTER_CODE,"239");
			}
		}
		if(!formObject.getValue(PD_CUSTSEGMENT).toString().equalsIgnoreCase("")) {
			manageInternalSection();
		}	
		try {
			loadCPDcustdata();
			populateScreeningDataCPD();
		}
		catch (Exception e) {
			logError("executeServerEvent", e);
		}
		try {
			formObject.setValue(TXT_CUSTOMERNAME, sName);
			formObject.setValue(TXT_DOB, sDOB);
			formObject.setValue(TXT_CUSTOMERID, cust_id);
		} catch (Exception e) {
			logError("executeServerEvent", e);
		}
		logInfo("gotFocusCustInfoDataCPD","--disable makani 4 step--:");
		String sFinalCountry = getFinalData(formObject.getValue(CHECKBOX_CNTRY_OF_CORR_FCR).toString(),
				formObject.getValue(CHECKBOX_CNTRY_OF_CORR_EIDA).toString(),
				formObject.getValue(CHECKBOX_CNTRY_OF_CORR_MANUAL).toString(),
				formObject.getValue(FCR_CNTRY).toString(),
				formObject.getValue(EIDA_CNTRY).toString(),
				formObject.getValue(MANUAL_CNTRY).toString());
		String sFinalResidentNew = getFinalData(formObject.getValue(CHECKBOX_COUNTRY_RES_FCR).toString(),
				formObject.getValue(CHECKBOX_COUNTRY_RES_EIDA).toString(),
				formObject.getValue(CHECKBOX_COUNTRY_RES_MANUAL).toString(),
				formObject.getValue(FCR_RESIDENT).toString(),
				formObject.getValue(EIDA_RESIDENT).toString(),formObject.getValue(MANUAL_RESIDENT).toString());
		String sFinalPermanentCountry = getFinalData(formObject.getValue(CHECKBOX_COUNTRY_PER_RES_FCR).toString(),
				formObject.getValue(CHECKBOX_COUNTRY_PER_RES_EIDA).toString(),
				formObject.getValue(CHECKBOX_COUNTRY_PER_RES_MANUAL).toString(),
				formObject.getValue(FCR_PER_CNTRY).toString(),
				formObject.getValue(EIDA_PER_CNTRY).toString(),formObject.getValue(MANUAL_PER_CNTRY).toString());
		if(!"UNITED ARAB EMIRATES".equalsIgnoreCase(sFinalCountry)) {
			formObject.setStyle(COR_MAKANI,DISABLE,TRUE);
		}
		if(!"UNITED ARAB EMIRATES".equalsIgnoreCase(sFinalResidentNew)) {
			formObject.setStyle(RES_MAKANI,DISABLE,TRUE);
		}
		if(!"UNITED ARAB EMIRATES".equalsIgnoreCase(sFinalPermanentCountry)) {
			formObject.setStyle(PERM_MAKANI,DISABLE,TRUE);
		}
	}
	
	
	/*private boolean submitWorkItemForProductQueueRecursively(String eventType,String data) {
		logInfo("Inside submitWorkItemForProductQueueRecursively "," eventType "+eventType+ " data "+data);
				try {
				int iRows = getGridCount(PRODUCT_QUEUE);
				String sQuery = "SELECT DISTINCT PRODUCT_CODE,PRODUCT_NAME,CURRENCY FROM USR_0_PRODUCT_EXISTING WHERE WI_NAME ='"+sWorkitemId+"'";
				List<List<String>> sOutput = formObject.getDataFromDB(sQuery);
				int iTotalRetrived = 0;
				if(sOutput != null && sOutput.size() > 0){
					iTotalRetrived = sOutput.size();
					if(iTotalRetrived != 0) {
						try {
							for(int i = 0;i  < iTotalRetrived;i++) {
								formObject.getTableCellValue(PRODUCT_QUEUE, i , 1);
								formObject.getTableCellValue(PRODUCT_QUEUE, i , 2);
								formObject.getTableCellValue(PRODUCT_QUEUE, i , 3);
								if(formObject.getTableCellValue(PRODUCT_QUEUE, i , 1).toString().equalsIgnoreCase(sOutput.get(i).get(0))
								   && formObject.getTableCellValue(PRODUCT_QUEUE, i , 2).toString().equalsIgnoreCase(sOutput.get(i).get(1))
								   && formObject.getTableCellValue(PRODUCT_QUEUE, i , 3).toString().equalsIgnoreCase(sOutput.get(i).get(2))) {
									
									if(!data.equalsIgnoreCase("")){
										sendMessageValuesList("","Product with following details already added, Do you still want to add "
												+ "\n Code:"+sOutput.get(i).get(0)+" \n Description:"+sOutput.get(i).get(1)+" "
												+ "\n Currency:"+sOutput.get(i).get(2)+"");
										break;  // In this case return true at last 
									} else {
										return false;
									}			 	
								}
							}
						} catch (Exception ex) {
							logError("Exception in submitWorkItemForProductQueueRecursively ",ex);
						}
					}
				}
			//submitWorkItem(eventType,data);
		} catch (Exception ex) {
			logError("Exception in submitWorkItemForProductQueueRecursively ",ex);
		}
	    return true;
	}*/
	
  
	public void lockOthersFields() {
		if(formObject.getValue(CUST_PREFIX).toString().equalsIgnoreCase("Others")) {
			formObject.setStyle(PD_OTHERS,DISABLE,TRUE);
		} else {
			formObject.setStyle(PD_OTHERS,DISABLE,FALSE);
		}
		if(formObject.getValue(MARITAL_STATUS).toString().equalsIgnoreCase("Others")) {
			formObject.setStyle(PD_MARITALSTATUSOTHER,DISABLE,TRUE);
		} else {
			formObject.setStyle(PD_MARITALSTATUSOTHER,DISABLE,FALSE);
		}
		if(formObject.getValue(CORR_STATE).toString().equalsIgnoreCase("Others")) {
			formObject.setStyle(CP_OTHERS,DISABLE,TRUE);
		} else {
			formObject.setStyle(CP_OTHERS,DISABLE,FALSE);
		}
		if(formObject.getValue(RES_STATE).toString().equalsIgnoreCase("Others")) {
			formObject.setStyle(PA_OTHERS,DISABLE,TRUE);
		} else {
			formObject.setStyle(PA_OTHERS,DISABLE,FALSE);
		}
		if(formObject.getValue(PERM_STATE).toString().equalsIgnoreCase("Others")) {
			formObject.setStyle(RA_OTHERS,DISABLE,TRUE);
		} else {
			formObject.setStyle(RA_OTHERS,DISABLE,FALSE);
		}
		if(formObject.getValue(PROFESION).toString().equalsIgnoreCase("Others")) {
			formObject.setStyle(ED_OTHER,DISABLE,TRUE);
		} else {
			formObject.setStyle(ED_OTHER,DISABLE,FALSE);
		}
		if(!formObject.getValue(MANUAL_DOB).toString().equalsIgnoreCase("")) {
			if(formObject.getValue(CHECKBOX_DOB_MANUAL).toString().equalsIgnoreCase("True")) {
				formObject.setValue(PD_DOB,formObject.getValue(MANUAL_DOB).toString());
			}
		}						
		if(!formObject.getValue(MANUAL_PASSPORTISSDATE).toString().equalsIgnoreCase("")) {
			if(formObject.getValue(CHECKBOX_PASS_ISS_DT_MANUAL).toString().equalsIgnoreCase("True")) {
				formObject.setValue(HD_PASS_ISS_DATE,formObject.getValue(MANUAL_PASSPORTISSDATE).toString());
			}
		}
		if(!formObject.getValue(MANUAL_PASSPORTEXPDATE).toString().equalsIgnoreCase("")) {
			if(formObject.getValue(CHECKBOX_PASS_EXP_DT_MANUAL).toString().equalsIgnoreCase("True")) {
				formObject.setValue(HD_PASS_EXP_DATE,formObject.getValue(MANUAL_PASSPORTEXPDATE).toString());
			}
		}
		if(!formObject.getValue(MANUAL_VISAISSDATE).toString().equalsIgnoreCase("")) {
			if(formObject.getValue(CHECKBOX_VISA_ISSUE_DATE_MANUAL).toString().equalsIgnoreCase("True")) {
				formObject.setValue(HD_VISA_ISSUE_DATE,formObject.getValue(MANUAL_VISAISSDATE).toString());
			}
		}
		if(!formObject.getValue(MANUAL_VISAEXPDATE).toString().equalsIgnoreCase("")) {
			if(formObject.getValue(CHECKBOX_VISA_EXPIRY_DATE_MANUAL).toString().equalsIgnoreCase("True")) {
				formObject.setValue(HD_EXP_DATE,formObject.getValue(MANUAL_VISAEXPDATE).toString());
			}
		}
		if(!formObject.getValue(MANUAL_NATIONALITY).toString().equalsIgnoreCase("")) {
			if(formObject.getValue(CHECKBOX_NATIONALITY_MANUAL).toString().equalsIgnoreCase("True")) {
				formObject.setValue(CUST_NATIONALITY,formObject.getValue(MANUAL_NATIONALITY).toString());
			}
		}
		if(!formObject.getValue(PD_CUSTSEGMENT).toString().equalsIgnoreCase("Excellency") 
				|| !formObject.getValue(PD_CUSTSEGMENT).toString().equalsIgnoreCase("Emirati Excellency")) {
			formObject.setStyle(EXCELLENCY_CNTR,DISABLE,TRUE);
		}
	}
	
	public void calculateCustomerRiskCPD() {
		logInfo("calculateCustomerRiskCPD","Inside");
		String sQuery = "SELECT BANK_DEC_CPD FROM USR_0_CUST_TXN WHERE WI_NAME='"+sWorkitemId+"' AND BANK_DEC_CPD IS NOT NULL";
		List<List<String>> sOutput = formObject.getDataFromDB(sQuery);
		logInfo("calculateCustomerRiskCPD","sOutput: "+sOutput);
		int iTotalRetrived = sOutput.size();
		String sRiskArray = (sOutput != null && sOutput.size() > 0)  ? sOutput.get(0).get(0).toString() : "";
		String sFinalRisk ="";
		logInfo("calculateCustomerRiskCPD","sQuery: "+sQuery+" sOutput: "+sOutput);
		if(sOutput !=null && sOutput.size() > 0) {
			if(!sRiskArray.equalsIgnoreCase("")) {
				for(int i = 0;i < iTotalRetrived;i++) {
					sRiskArray = sOutput.get(i).get(0);
					if(sRiskArray.equalsIgnoreCase("Not Approved")) {
						sFinalRisk = "Not Eligible";
						break;
					} else if(sRiskArray.equalsIgnoreCase("Further Approval Required")) {
						sFinalRisk = "Approval Required";
					} else if(sRiskArray.equalsIgnoreCase("Pending")) {
						System.out.println("inside apprved CPD---998");
						sFinalRisk = "Pending";
					} else if(sRiskArray.equalsIgnoreCase("Approved")) {
						if(!sFinalRisk.equalsIgnoreCase("Approval Required")) 	{
							sFinalRisk = "Eligible";
						}
					}
				
				}
			}
		}
		formObject.setValue("CUSTOMER_RISK_CPD",sFinalRisk); // doubt
		logInfo("calculateCustomerRiskCPD","Outside sFinalRisk: "+sFinalRisk);
	}

//	public void calculateAppRiskCPD() {	
//		logInfo("calculateAppRiskCPD","Inside");
//		String sQuery = "SELECT CURRENT_RISK_BUSSINESS FROM USR_0_RISK_ASSESSMENT_DATA_CPD WHERE WI_NAME='"+sWorkitemId+"'";
//		List<List<String>> sOutput = formObject.getDataFromDB(sQuery);
//		logInfo("calculateAppRiskCPD","sQuery: "+sQuery+",sOutput: "+sOutput);
//		int iTotalRetrived = sOutput.size();
//		logInfo("calculateAppRiskCPD","iTotalRetrived: "+iTotalRetrived);
//		String sRiskArray = (sOutput != null && sOutput.size() > 0)  ? sOutput.get(0).get(0).toString() : "";
//		String sFinalRisk = "";
//		if(sOutput != null && sOutput.size() > 0){
//			if(!sRiskArray.equalsIgnoreCase("")) {	
//				for(int i = 0;i < iTotalRetrived;i++) {
//					sRiskArray = sOutput.get(i).get(0);
//					if(sRiskArray.equalsIgnoreCase("Unacceptable Risk")) {
//						sFinalRisk = "Unacceptable Risk";
//						break;
//					} else if(sRiskArray.equalsIgnoreCase("PEP")) {
//						sFinalRisk = "PEP";
//					} else if(sRiskArray.equalsIgnoreCase("Increased Risk")) {
//						if(!sFinalRisk.equalsIgnoreCase("UAE-PEP")) {
//							sFinalRisk = "Increased Risk";
//						}
//					} else if(sRiskArray.equalsIgnoreCase("Neutral")) {
//						if(!sFinalRisk.equalsIgnoreCase("UAE-PEP") && !sFinalRisk.equalsIgnoreCase("Increased Risk")) {
//							sFinalRisk = "Neutral Risk";
//						}
//					} else if(sRiskArray.equalsIgnoreCase("UAE-PEP")) {
//						sFinalRisk = "UAE-PEP";
//					} else if(sRiskArray.equalsIgnoreCase("Non UAE-PEP")) {
//						sFinalRisk = "Non UAE-PEP";
//					}	
//					else if(sRiskArray.equalsIgnoreCase("Medium Risk")) {
//						sFinalRisk = "Medium Risk";
//					}
//
//				}
//			}
//		}
//		logInfo("calculateAppRiskCPD","sFinalRisk: "+sFinalRisk);
//		if(!sFinalRisk.equalsIgnoreCase("")) {
//			logInfo("calculateAppRiskCPD","sFinalRisk: "+sFinalRisk);
//			formObject.setValue(FINAL_RISK_VAL_CPD,sFinalRisk);
//		} else {	
//			logInfo("calculateAppRiskCPD","sFinalRisk: "+sFinalRisk);
//			String sQueryy = "select max(CUST_CUR_RISK_BANK) as CUST_CUR_RISK_BANK from usr_0_risk_data where "
//					+ "(entrydatetime) in (select max(entrydatetime) from usr_0_risk_data  where wi_name='"+sWorkitemId+"' "
//					+ "and CUST_CUR_RISK_BANK is not null) and wi_name='"+sWorkitemId+"'";
//			List<List<String>> sOutputt = formObject.getDataFromDB(sQueryy);
//			logInfo("calculateAppRiskCPD","sQueryy: "+sQueryy+",sOutputt: "+sOutputt);
//			String sCustCurRiskBank = (sOutput != null && sOutput.size() > 0)  ? sOutput.get(0).get(0).toString() : "";
//			logInfo("calculateAppRiskCPD","sCustCurRiskBank: "+sCustCurRiskBank);
//			if(sOutput != null && sOutput.size() > 0){
//				if(!sCustCurRiskBank.equalsIgnoreCase("")) {
//					if(sCustCurRiskBank.equalsIgnoreCase("Unacceptable Risk")) {
//						sFinalRisk = "Unacceptable Risk";
//					} else if(sCustCurRiskBank.equalsIgnoreCase("PEP")) {
//						sFinalRisk = "PEP";
//					} else if(sCustCurRiskBank.equalsIgnoreCase("Increased Risk")) {
//						if(!sFinalRisk.equalsIgnoreCase("UAE-PEP")) {
//							sFinalRisk = "Increased Risk";
//						}
//					} else if(sCustCurRiskBank.equalsIgnoreCase("Neutral")) {
//						if(!sFinalRisk.equalsIgnoreCase("UAE-PEP") && !sFinalRisk.equalsIgnoreCase("Increased Risk")) {
//							sFinalRisk = "Neutral Risk";
//						}
//					} else if(sCustCurRiskBank.equalsIgnoreCase("UAE-PEP")) {
//						sFinalRisk = "UAE-PEP";
//					} else if(sCustCurRiskBank.equalsIgnoreCase("Non UAE-PEP")) {
//						sFinalRisk = "Non UAE-PEP";
//					}	else if(sCustCurRiskBank.equalsIgnoreCase("Medium Risk")) {
//						sFinalRisk = "Medium Risk";
//					}
//					formObject.setValue(FINAL_RISK_VAL_CPD,sFinalRisk);
//				}
//			}
//		}
//		logInfo("calculateAppRiskCPD","Outside sFinalRisk: "+sFinalRisk);
//	}

	public void loadFacilitiesData(String sFacilityGrid) {
		logInfo("loadFacilitiesData","Inside sFacilityGrid: "+sFacilityGrid);
		try {
			StringBuilder showList = new StringBuilder();
			String sPrimaryCust = getPrimaryCustomerSNO();
			String sCID = "";
			String sAccRelation = formObject.getTableCellValue(ACC_RELATION, Integer.parseInt(sPrimaryCust), 7);
			formObject.clearCombo(sFacilityGrid);
			String sQuery = "SELECT CUST_ID,SMS_FLAG,IB_FLAG,IVR_FLAG,STAFF_FLAG_EXISTING,ESTATEMENT_FLAG, "
					+ "CHEQUEBOOK_BLOCK,DECODE(SIGNATUREPRESENTFLAG,'Y','Yes','No') AS SIGNATUREPRESENTFLAG,"
					+ "DECODE(PHOTOPRESENTFLAG,'Y','Yes','No') AS PHOTOPRESENTFLAG,CUSTOMER_OPEN_DATE,TOUCHPOINTS,"
					+ "MIB_FLAG FROM USR_0_CUST_TXN WHERE CUST_SNO ='"+sPrimaryCust+"' AND WI_NAME = '"+sWorkitemId+"'";
			List<List<String>> sOutput=formObject.getDataFromDB(sQuery);
			logInfo("loadFacilitiesData","sQuery: "+sQuery+"sOutput: "+sOutput);
			if(sOutput != null && sOutput.size() > 0){
				sCID = sOutput.get(0).get(0);
				if(sAccRelation.equalsIgnoreCase("New")) {
					showList.append("<ListItems><ListItem><SubItem>"+sCID+"</SubItem><SubItem>"
							+ "Customer Open Date</SubItem><SubItem>Not Defined-NTB</SubItem></ListItem>"
							+ "<ListItem><SubItem>"+sCID+"</SubItem><SubItem>Estatement Registered</SubItem>"
						    + "<SubItem>Not Defined-NTB</SubItem></ListItem><ListItem><SubItem>"+sCID+"</SubItem>"
							+ "<SubItem>IB Flag</SubItem><SubItem>Not Defined-NTB</SubItem></ListItem>"
							+ "<ListItem><SubItem>"+sCID+"</SubItem><SubItem>IVR Flag</SubItem><SubItem>Not Defined-NTB</SubItem>"
						    + "</ListItem><ListItem><SubItem>"+sCID+"</SubItem><SubItem>MIB Flag</SubItem><SubItem>Not Defined-NTB"
						    + "</SubItem></ListItem><ListItem><SubItem>"+sCID+"</SubItem><SubItem>PhotoPresentFlag</SubItem><SubItem>"
						    + "Not Defined-NTB</SubItem></ListItem><ListItem><SubItem>"+sCID+"</SubItem><SubItem>SMS Flag</SubItem>"
						    + "<SubItem>Not Defined-NTB</SubItem></ListItem><ListItem><SubItem>"+sCID+"</SubItem>"
						    + "<SubItem>SignaturePresentFlag</SubItem><SubItem>Not Defined-NTB</SubItem></ListItem>"
						    + "<ListItem><SubItem>"+sCID+"</SubItem><SubItem>Staff Flag</SubItem><SubItem>Not Defined-NTB</SubItem>"
						    + "</ListItem><ListItem><SubItem>"+sCID+"</SubItem><SubItem>TouchPoints</SubItem><SubItem>Not Defined-NTB"
						   	+ "</SubItem></ListItem></ListItems>");
				} else {
					showList.append("<ListItems><ListItem><SubItem>"+sCID+"</SubItem><SubItem>"
							+ "Customer Open Date</SubItem><SubItem>"+sOutput.get(0).get(9)+"</SubItem>"
							+ "</ListItem><ListItem><SubItem>"+sCID+"</SubItem><SubItem>Estatement Registered</SubItem>"
							+ "<SubItem>"+sOutput.get(0).get(5)+"</SubItem></ListItem><ListItem><SubItem>"+sCID+"</SubItem>"
							+ "<SubItem>IB Flag</SubItem><SubItem>"+sOutput.get(0).get(2)+"</SubItem></ListItem><ListItem><SubItem>"
							+ ""+sCID+"</SubItem><SubItem>IVR Flag</SubItem><SubItem>"+sOutput.get(0).get(3)+"</SubItem>"
							+ "</ListItem><ListItem><SubItem>"+sCID+"</SubItem><SubItem>MIB Flag</SubItem><SubItem>"
							+ ""+sOutput.get(0).get(11)+"</SubItem></ListItem><ListItem><SubItem>"+sCID+""
							+ "</SubItem><SubItem>PhotoPresentFlag</SubItem><SubItem>"+sOutput.get(0).get(8)
							+"</SubItem></ListItem><ListItem><SubItem>"+sCID+"</SubItem><SubItem>SMS Flag</SubItem><SubItem>"
							+ ""+sOutput.get(0).get(1)+"</SubItem></ListItem><ListItem><SubItem>"+sCID+"</SubItem><SubItem>"
							+ "SignaturePresentFlag</SubItem><SubItem>"+sOutput.get(0).get(7)+"</SubItem></ListItem>"
							+ "<ListItem><SubItem>"+sCID+"</SubItem><SubItem>Staff Flag</SubItem><SubItem>"+sOutput.get(0).get(4)+
							"</SubItem></ListItem><ListItem><SubItem>"+sCID+"</SubItem><SubItem>TouchPoints</SubItem><SubItem>"
							+ ""+sOutput.get(0).get(10)+"</SubItem></ListItem></ListItems>");
					
				}
			}
			logInfo("loadFacilitiesData","sOutput: "+showList.toString());
			formObject.addItemInCombo(sFacilityGrid,showList.toString());
		} catch(Exception e) {
			logError("Exception inloadFacilitiesData ", e);
		}
		logInfo("loadFacilitiesData","Outside");
	}

	public void loadOfferedProduct(String sGridName) {
		logInfo("loadOfferedProduct","Inside sGridName: "+sGridName);
		String sInputXML = getProdEligibilityInputXML();
		String sClassification= "";
		String sProductCategory= "";
		String sProductType= "";
		String sCurrency= "";
		String sSaving= "";
		String sCall= "";
		String sTerm= "";
		String sCurrent= "";
		String sCurrentCurrency= "";
		String sProdTypeQuery1= "";
		String sProdCatQuery1= "";
		String sQuery1= "";
		List<List<String>> sOutput = null;
		String sBranch= "";
		sQuery1 = "SELECT CODE,EQV_ISLAMIC_BR_CODE FROM USR_0_HOME_BRANCH WHERE"
				+ " HOME_BRANCH = ('"+formObject.getValue(ACC_HOME_BRANCH).toString()+"')";
		sOutput = formObject.getDataFromDB(sQuery1);
		logInfo("loadOfferedProduct","sOutput: "+sOutput);
		if(!sOutput.get(0).get(0).equalsIgnoreCase("")) {
			sBranch  = sBranch+"'"+sOutput.get(0).get(0)+"',";
		}
		if(!sOutput.get(0).get(1).equalsIgnoreCase("")) {
			sBranch  = sBranch+"'"+sOutput.get(0).get(1)+"',";
		}
		formObject.clearCombo(sGridName);
		sBranch = sBranch.substring(1,sBranch.length()-2);
		logInfo("loadOfferedProduct","sBranch-----"+sBranch);
		sQuery1 = "";
		String sOutputxml = socket.connectToSocket(sInputXML); 
		logInfo("loadOfferedProduct","sInputXML: "+sInputXML+",sOutputxml: "+sOutputxml);		
		sProductCategory = getTagValues(sOutputxml,"subcategory");
		if(sProductCategory.equalsIgnoreCase("")) {
			sendMessageValuesList("", "Error in calculating offered products");
			return;
		} else if(sProductCategory.equalsIgnoreCase("Select")) {
			sendMessageValuesList("","No product found as per rule engine."
					+ "Generic product list is getting displayed"+"\n"+"Or "
					+ "Customer information validations are not run.");
		} else {
			sProductType ="";
			sClassification = getTagValues(sOutputxml,"classification_output");
			sCurrent = getTagValues(sOutputxml,"current");
			sSaving = getTagValues(sOutputxml,"savings");
			sCall = getTagValues(sOutputxml,"call");
			sTerm = getTagValues(sOutputxml,"term");
			sCurrentCurrency = getTagValues(sOutputxml,"current_currency");
			if(sClassification.equalsIgnoreCase("Conventional")) {
				sClassification="C";
			} else if(sClassification.equalsIgnoreCase("Islamic")) {
				sClassification="I";
			}
			if(sCurrent.equalsIgnoreCase("Yes")) {
				sProductType=sProductType+"'Current',";
			}
			if(sSaving.equalsIgnoreCase("Yes")) {
				sProductType=sProductType+"'Saving',";
			}
			if(sCall.equalsIgnoreCase("Yes")) {
				sProductType=sProductType+"'Call',";
			}
			if(sTerm.equalsIgnoreCase("Yes")) {
				sProductType=sProductType+"'Term',";
			}
			if(sCurrentCurrency.equalsIgnoreCase("NON AED")) {
				sCurrency="Arab Emirates Dirham";
			}
			String[] sTempCategory =sProductCategory.split(",");
			sProductCategory="";
			for(int i=0;i<sTempCategory.length;i++) {
				sProductCategory = sProductCategory+"'"+sTempCategory[i]+"',";
			}
			sProductCategory  = sProductCategory+"'Others','Investment',";
			if(!sProductType.equalsIgnoreCase("")) {
				sProductType = sProductType.substring(1,sProductType.length()-2);
			}
			if(!sProductCategory.equalsIgnoreCase("")) {
				sProductCategory = sProductCategory.substring(1,sProductCategory.length()-2);
			}
			logInfo("","23012017---");
			sQuery1 =sQuery1+"SELECT DISTINCT A.PRODUCT_CODE, A.PRODUCT_DESC,CURRENCY"
					+ " CURRENCY_CODE,'' FROM USR_0_PRODUCT_MASTER A,USR_0_PRODUCT_BRANCH_MAPPING B,"
					+ "USR_0_PRODUCT_TYPE_MASTER C,USR_0_CURRENCY D WHERE A.PRODUCT_CODE = B.PRODUCT_CODE"
					+ " AND A.PRODUCT_CODE = C.PRODUCT_CODE AND C.COD_PROD_TYPE ='"+sClassification+"' AND"
					+ " C.PROCESS_TYPE ='Onshore' AND CURR_CODE = CURRENCY_CODE AND ";
			if(!sProductType.equalsIgnoreCase("")) {
				sProdTypeQuery1 = "C.PRODUCT_TYPE IN ('"+sProductType+"')";
			} else {
				sProdTypeQuery1 = "C.PRODUCT_TYPE NOT IN ('Internal','NonRetail')";
			}
			if(!sProductCategory.equalsIgnoreCase("")) {
				sProdCatQuery1 = "C.SUB_PRODUCT_TYPE IN('"+sProductCategory+"')";
			}
			sQuery1 = sQuery1 + sProdTypeQuery1 + (sProdTypeQuery1.length() > 0?" AND ": "") + sProdCatQuery1+
					" AND BRANCH_CODE IN ('"+sBranch+"')";
			if(!sCurrency.equalsIgnoreCase("")) {
				sQuery1 = sQuery1+" AND CURRENCY_CODE != (CASE WHEN PRODUCT_TYPE='Current' THEN (SELECT CURR_CODE FROM USR_0_CURRENCY WHERE UPPER(CURRENCY) = UPPER('Arab Emirates Dirham')) ELSE '1090' END )";
			}
			sQuery1 = sQuery1+" ORDER BY TO_NUMBER(PRODUCT_CODE)";
		}
		if(!sQuery1.equalsIgnoreCase("")) {
			List<List<String>> sFinalOutput = formObject.getDataFromDB(sQuery1);
			logInfo("","sFinalOutput------"+sFinalOutput);
			loadListView(sFinalOutput,"CODE,EQV_ISLAMIC_BR_CODE,CURRENCY_CODE,PRODUCT_TYPE","sGridName");
			//LoadListView(sQuery1,4,sGridName,"0,1,2,3");
		}
		int iListViewRows = getListCount(sGridName);
		if(iListViewRows==0) {
			if(formObject.getValue(ACC_CLASS).toString().equalsIgnoreCase("Conventional")) {
				sClassification="'C'";
			} else if(formObject.getValue(ACC_CLASS).toString().equalsIgnoreCase("Islamic")) {
				sClassification="'I'";
			} else {
				sClassification="'I','C'";
			}
			if(formObject.getValue(ACC_OWN_TYPE).toString().equalsIgnoreCase("Minor")) {
				sProdCatQuery1="SUB_PRODUCT_TYPE IN ('Child Saver";
			} else {
				sProdCatQuery1="SUB_PRODUCT_TYPE NOT IN ('Child Saver";
			}
			sQuery1= "SELECT PRODUCT_CODE, PRODUCT_DESC,(SELECT CURRENCY FROM USR_0_CURRENCY WHERE CURR_CODE = CURRENCY_CODE) CURRENCY_CODE,'' FROM USR_0_PRODUCT_MASTER WHERE PRODUCT_CODE IN (SELECT DISTINCT PRODUCT_CODE FROM USR_0_PRODUCT_BRANCH_MAPPING WHERE PRODUCT_CODE IN (SELECT PRODUCT_CODE FROM USR_0_PRODUCT_TYPE_MASTER WHERE COD_PROD_TYPE IN ("+sClassification+") AND PROCESS_TYPE ='Onshore' AND PRODUCT_TYPE NOT IN ('Internal','NonRetail') AND "+sProdCatQuery1+"')) AND BRANCH_CODE IN ('"+sBranch+"')) ORDER BY TO_NUMBER(PRODUCT_CODE)";
			List<List<String>> recordList = formObject.getDataFromDB(sQuery1);
			logInfo("","sQuery1------"+sQuery1);
			loadListView(recordList,"PRODUCT_CODE,PRODUCT_DESC,CURRENCY_CODE","sGridName");
			//loadListView(sQuery1,sGridName,"sGridName");
		}
	}
	
	public String getProdEligibilityInputXML() {
		String sXML ="";
		try {
			String sPrimaryCust = getPrimaryCustomerSNO();
			List<List<String>> sOutput = formObject.getDataFromDB("SELECT SEQ_WEBSERVICE.nextval as ID from DUAL ");
			String sSeqNo=sOutput.get(0).get(0);
			String sQuery = "SELECT B.SIGN_STYLE AS SIGN_STYLE,B.EMPLOYEE_TYPE as EMPLOYEE_TYPE,(SELECT DISTINCT A.TYPE FROM USR_0_EMPLOYMENT_STATUS A WHERE A.EMP_STATUS =B.EMP_STATUS) EMP_STATUS,B.FINAL_RESIDENCE_COUNTRY AS FINAL_COUNTRY, to_char(B.FINAL_DOB,'yyyy/MM/dd') FINAL_DOB,B.CUST_ID AS CUST_ID,B.FINAL_NATIONALITY AS FINAL_NATIONALITY FROM USR_0_CUST_TXN B WHERE WI_NAME ='"+sWorkitemId+"' AND CUST_SNO ='"+sPrimaryCust+"'";
			sOutput = formObject.getDataFromDB(sQuery);
			logInfo("","sQuery----"+sQuery);
			logInfo("","sOutput----"+sOutput);
			String sSignType="";
			String sEmpType=sOutput.get(0).get(1);
			int iAge=0;
			String sOwnership=formObject.getValue(ACC_OWN_TYPE).toString();
			if(sOwnership.equalsIgnoreCase("Joint")) {
				sOwnership = "Single";
			}
			if(sOutput.get(0).get(0).indexOf("Signature")!= -1) {
				sSignType="Sign";
			} else if(sOutput.get(0).get(0).indexOf("Thumb")!= -1) {
				sSignType="Thumb Impression";
			}
			if(!sOutput.get(0).get(4).equalsIgnoreCase("")) {
				iAge = CalculateAge(sOutput.get(0).get(4));
			}
			if(sEmpType.equalsIgnoreCase("")) {
				sEmpType ="*";
			}
			String sQueryy="select VALUE from usr_0_defaultvalue_fcr where name='Minor_Age'";
			List<List<String>> sOutputt=formObject.getDataFromDB(sQueryy);
			logInfo("","sOutputt------"+sOutputt);
			int sMinorAge= Integer.parseInt(sOutputt.get(0).get(0));
			logInfo("","sMinorAge....."+sMinorAge);
			sXML = "<?xml version=\"1.0\"?>" +
					"<APWebService_Input>" +
					"<Option>WebService</Option>" +
					"<Calltype>Product_Eligibility</Calltype>" +
					"<Customer>" +
					"<acc_classification>"+formObject.getValue(ACC_CLASS)+"</acc_classification>"+
					"<acc_ownership>"+sOwnership+"</acc_ownership>"+
					"<country_of_res>"+sOutput.get(0).get(3)+"</country_of_res>"+
					"<emp_type>"+sEmpType+"</emp_type>"+
					"<sign_type>"+sSignType+"</sign_type>"+
					"<emp_status>"+sOutput.get(0).get(2)+"</emp_status>"+
					"<casa_availed>No</casa_availed>"+
					"<cust_age>"+iAge+"</cust_age>"+
					"<nationality>"+sOutput.get(0).get(6)+"</nationality>"+
					"<call>Select</call>"+
					"<call_currency>Select</call_currency>"+
					"<classification_output>Select</classification_output>"+
					"<cntry_specific>Select</cntry_specific>"+
					"<current>Select</current>"+
					"<current_currency>Select</current_currency>"+
					"<cust_literacy>Yes</cust_literacy>"+
					"<max_age>*</max_age>"+
					"<min_age>"+sMinorAge+"</min_age>"+//21
					"<res_status>Yes</res_status>"+
					"<savings>Select</savings>"+
					"<savings_currency>Select</savings_currency>"+
					"<subcategory>Select</subcategory>"+
					"<term>Select</term>"+
					"<term_currency>Select</term_currency>"+
					"<trade_license>No</trade_license>"+
					"<REF_NO>"+sSeqNo+"</REF_NO>"+
					"<userName>"+sUserName+"</userName>"+
					"<SessionId>" + sSessionId + "</SessionId>" +
					"<WiName>" + sWorkitemId + "</WiName>" +
					"</Customer>";

			logInfo("","sXML-----"+sXML);
		}
		catch(Exception e)
		{
			logError("excetion", e);
		}
		return sXML;
	}

	public void loadOfferedDebitCard(String sProdGrid,String sDebitCardGrid) {
		logInfo("loadOfferedDebitCard","Inside sProdGrid: "+sProdGrid+",sDebitCardGrid: "+sDebitCardGrid);
		try {
			String sPrimaryCust = getPrimaryCustomerSNO();
			formObject.clearCombo(sDebitCardGrid);
			String sProduct = "";
			int sCount = getGridCount(sProdGrid);
			String sCustID = formObject.getTableCellValue(ACC_RELATION, Integer.parseInt(sPrimaryCust),2);
			for(int row = 0;row < sCount;row++) {
				sProduct = sProduct+"'"+formObject.getTableCellValue(sProdGrid,row,0).toString()+"'"+",";
			}
			if(!sProduct.equalsIgnoreCase("")) {
				sProduct = sProduct.substring(1,sProduct.length()-2);
			}
			String sQuery = "SELECT FINAL_FULL_NAME,CUST_SEG,DECODE(STAFF_FLAG,'Yes','Y','N') "
					+ "AS STAFF_FLAG FROM USR_0_CUST_TXN WHERE CUST_SNO = '"+sPrimaryCust+"'"
				    + " AND WI_NAME ='"+sWorkitemId+"'";
			List<List<String>> sOutput = formObject.getDataFromDB(sQuery);
			logInfo("loadOfferedDebitCard","sQuery: "+sQuery+", sOutput: "+sOutput);
			String sSegment = "";
			String sStaff = "";
			String sName = "";
			if(sOutput != null && sOutput.size() > 0) {
				 sSegment = sOutput.get(0).get(1);
				 sStaff = sOutput.get(0).get(2);
				 sName= sOutput.get(0).get(0);
			}
			sQuery = "SELECT DISTINCT TO_CHAR(CASE WHEN COD_PROD_TYPE='C' THEN 'CVNONS' ELSE 'ISM' END)"
					+ " AS COD_PROD_TYPE FROM USR_0_PRODUCT_TYPE_MASTER WHERE PRODUCT_CODE IN (SELECT PRODUCT_CODE"
					+ " FROM USR_0_PRODUCT_MASTER WHERE bitand(ATM_FLAG,1) = '1' and PRODUCT_CODE IN ('"+sProduct+"'))"
					+ " INTERSECT SELECT DISTINCT TO_CHAR(CARDTYPE) AS COD_PROD_TYPE FROM USR_0_DEBITCARD_MASTER"
					+ " WHERE PROCESS_TYPE ='Onshore' AND CUST_CATEGORY ='"+sSegment+"' AND STAFF_FLAG = '"+sStaff+"'";
			sOutput = formObject.getDataFromDB(sQuery);
			logInfo("loadOfferedDebitCard","sQuery: "+sQuery+", sOutput: "+sOutput);
			String sCodType = (sOutput != null && sOutput.size() > 0)  ? sOutput.get(0).get(0).toString() : "";
			if(!sCodType.equalsIgnoreCase("")) {
				StringBuilder showList = new StringBuilder();
				showList.append("<ListItems>");
				if(formObject.getValue(ACC_CLASS).toString().equalsIgnoreCase("Islamic")) {
					showList.append("<ListItem><SubItem>"+sCustID+"</SubItem><SubItem>"+sName+""
							+ "</SubItem><SubItem>ISM</SubItem></ListItem>");
				} else {
					showList.append("<ListItem><SubItem>"+sCustID+"</SubItem><SubItem>"+sName+""
							+ "</SubItem><SubItem>CVNONS</SubItem></ListItem><ListItem><SubItem>"+sCustID+"</SubItem>"
							+ "<SubItem>"+sName+"</SubItem><SubItem>ETD</SubItem></ListItem>");
				}
				formObject.addItemInCombo(sDebitCardGrid,showList.toString());
			}
		} catch(Exception e) {
			logError("Exception in loadOfferedDebitCard", e);
		}
		logInfo("loadOfferedDebitCard","Outside");
	}

	/*public void loadRequiredDocument(String sGridName) {
		logInfo("loadRequiredDocument","Inside sGridName: "+sGridName);
		int SNO = 1;
		String data= "";
		String sQuery1= "";
		List<List<String>> outQuery1;
		formObject.clearCombo(sGridName);
		HashMap<String,String> sDocGroup = getDocumentGroup();	
		try {			
			sQuery1 ="SELECT CUST.CUST_ID,EXT.REQUEST_TYPE,CUST.FINAL_COUNTRY,CUST.SPECIAL_CATAGORY,CUST.FINAL_FULL_NAME ,CUST.FINAL_NATIONALITY, CUST.VISA_STATUS,CUST.IS_UAE_RESIDENT,(SELECT TYPE FROM USR_0_EMPLOYMENT_STATUS WHERE EMP_STATUS = CUST.EMP_STATUS) EMP_STATUS,CUST.EMPLOYEE_TYPE FROM USR_0_CUST_TXN  CUST,"+sExtTable+" EXT WHERE CUST.WI_NAME='"+sWorkitemId+"' and cust.wi_name=ext.wi_name ORDER BY TO_NUMBER(CUST_SNO)";
			outQuery1= formObject.getDataFromDB(sQuery1);
			logInfo("","outQuery1----"+outQuery1);
			logInfo("","Record------"+data);
			StringBuilder showList = new StringBuilder();
			int iNo=1;
			String sCountryResidence="";
			String EMP_STATUS="";
			String acc_details="";
			String sBankRelation="";
			String GCC_COUNTRY= null;
			String SPECIAL_CATAGORY= null;
			String RES_STATUS= null;
			String NATIONALITY_TYPE= null;
			String STAFF_FLAG= null;
			String VISA_STATUS= null;
			String CUST_ID= null;
			String FULL_NAME= null;			
			String sReqType= null;	
			for(int cust_count=0;cust_count<outQuery1.size();cust_count++) {
				String sQuery2 ="SELECT ACC_RELATION,BANK_RELATION FROM ACC_RELATION_REPEATER WHERE WI_NAME='"+sWorkitemId+"' and SNO='"+iNo+"'";
				List<List<String>> outQuery2= formObject.getDataFromDB(sQuery2);
				acc_details=outQuery2.get(0).get(0);	
				sBankRelation=outQuery2.get(0).get(1);	
				logInfo("","acc_details---"+acc_details);
				sCountryResidence = outQuery1.get(0).get(2);	
				EMP_STATUS = 	outQuery1.get(0).get(8);	
				VISA_STATUS=	outQuery1.get(0).get(6);	
				CUST_ID = 		outQuery1.get(0).get(0);	
				FULL_NAME =		outQuery1.get(0).get(4);	
				sReqType =		outQuery1.get(0).get(1);	
				RES_STATUS =	outQuery1.get(0).get(7);	
				if(sReqType.equalsIgnoreCase("Category Change Only")) {
					sReqType ="New Account with Category Change";
				}
				if((sCountryResidence!=null) && (sCountryResidence.equalsIgnoreCase("SAUDI ARABIA") ||sCountryResidence.equalsIgnoreCase("Bahrain")
					||sCountryResidence.equalsIgnoreCase("Oman")||sCountryResidence.equalsIgnoreCase("Kuwait")||sCountryResidence.equalsIgnoreCase("Qatar"))) {
					GCC_COUNTRY="Yes";
				} else {
					GCC_COUNTRY="No";
				} if((outQuery1.get(0).get(3)!=null) && outQuery1.get(0).get(3).equalsIgnoreCase("Irena")) {
					SPECIAL_CATAGORY="Yes";
				} else {
					SPECIAL_CATAGORY="No";
				} if((outQuery1.get(0).get(7)!=null) && outQuery1.get(0).get(7).equalsIgnoreCase("UNITED ARAB EMIRATES")) {
					NATIONALITY_TYPE="UAE";
				} else {
					NATIONALITY_TYPE="Non UAE";
				}
				if(outQuery1.get(0).get(9)!=null && outQuery1.get(0).get(9).equalsIgnoreCase("ADCB")) {
					STAFF_FLAG="Yes";
				} else {
					STAFF_FLAG="No";
				}
				if(RES_STATUS.equalsIgnoreCase("Yes")) {
					sQuery1="select distinct VALID_ENTRY_PERMIT,BANK_STATEMENT_NONUAE,ADCB_LETTER,BANK_CERTIFICATE,ESTATEMENT,SAL_CERTIFICATE,ATT_SAL_CERTIFICATE,PAYSLIP,OFFER_LETTER,MINI_STMT,BANK_STATEMENT_UAE,EMP_CONTRACT,UPGRADE_PROOF,SAL_TRNSFR_LTR,UTILITY_BILL,TENANCY_CONTRACT,TRADE_LICENSE,MARR_CERTI,EMP_CERTI,FAMILY_BOOK,LABOUR_CARD,PASSPORT,VISA,SPL_LABR_CARD,EIDA_CARD,STATELESS_CARD,GOVT_ID,EMP_LTR_GOVT from usr_0_doc_req_rule where REQUEST_TYPE='"+sReqType+"' AND BANKING_RELATIONSHIP = '"+sBankRelation+"' AND GCC_COUNTRY='"+GCC_COUNTRY+"' AND RESIDENCY_STATUS='"+RES_STATUS+"' AND NATIONALITY_TYPE='"+NATIONALITY_TYPE+"' AND (VISA_STATUS='"+VISA_STATUS+"' or VISA_STATUS =' ') AND EMPLOYMENT_TYPE='"+EMP_STATUS+"' AND STAFF_FLAG='"+STAFF_FLAG+"' AND MDSA_ALREADY_STATUS='No' AND EMSA_ALREADY_STATUS='No' AND MONTHLY_SALARY_LESS_THAN=' '";
				} else {
					sQuery1="select distinct VALID_ENTRY_PERMIT,BANK_STATEMENT_NONUAE,ADCB_LETTER,BANK_CERTIFICATE,ESTATEMENT,SAL_CERTIFICATE,ATT_SAL_CERTIFICATE,PAYSLIP,OFFER_LETTER,MINI_STMT,BANK_STATEMENT_UAE,EMP_CONTRACT,UPGRADE_PROOF,SAL_TRNSFR_LTR,UTILITY_BILL,TENANCY_CONTRACT,TRADE_LICENSE,MARR_CERTI,EMP_CERTI,FAMILY_BOOK,LABOUR_CARD,PASSPORT,VISA,SPL_LABR_CARD,EIDA_CARD,STATELESS_CARD,GOVT_ID,EMP_LTR_GOVT from usr_0_doc_req_rule where REQUEST_TYPE='"+sReqType+"' AND BANKING_RELATIONSHIP = '"+sBankRelation+"' AND GCC_COUNTRY='"+GCC_COUNTRY+"' AND RESIDENCY_STATUS='"+RES_STATUS+"' AND NATIONALITY_TYPE='"+NATIONALITY_TYPE+"' AND (VISA_STATUS='"+VISA_STATUS+"' or VISA_STATUS =' ') AND SPECIAL_CATEGORY_TYPE='"+SPECIAL_CATAGORY+"' AND EMPLOYMENT_TYPE='"+EMP_STATUS+"' AND STAFF_FLAG='"+STAFF_FLAG+"' AND MDSA_ALREADY_STATUS='No' AND EMSA_ALREADY_STATUS='No' AND MONTHLY_SALARY_LESS_THAN=' '";
				}                                                               
				logInfo("","sQuery1----"+sQuery1);
				outQuery2= formObject.getDataFromDB(sQuery1);
				logInfo("","outQuery1----"+outQuery1);
				if(outQuery2.size() == 0) {
					sQuery1="select distinct VALID_ENTRY_PERMIT,BANK_STATEMENT_NONUAE,ADCB_LETTER,BANK_CERTIFICATE,ESTATEMENT,SAL_CERTIFICATE,ATT_SAL_CERTIFICATE,PAYSLIP,OFFER_LETTER,MINI_STMT,BANK_STATEMENT_UAE,EMP_CONTRACT,UPGRADE_PROOF,SAL_TRNSFR_LTR,UTILITY_BILL,TENANCY_CONTRACT,TRADE_LICENSE,MARR_CERTI,EMP_CERTI,FAMILY_BOOK,LABOUR_CARD,PASSPORT,VISA,SPL_LABR_CARD,EIDA_CARD,STATELESS_CARD,GOVT_ID,EMP_LTR_GOVT from usr_0_doc_req_rule where REQUEST_TYPE='"+sReqType+"' AND BANKING_RELATIONSHIP = '"+sBankRelation+"' AND GCC_COUNTRY='"+GCC_COUNTRY+"' AND RESIDENCY_STATUS='"+RES_STATUS+"' AND NATIONALITY_TYPE='"+NATIONALITY_TYPE+"' AND EMPLOYMENT_TYPE='"+EMP_STATUS+"' AND ROWNUM=1";
					outQuery1= formObject.getDataFromDB(sQuery1);
					logInfo("","Input :"+sQuery1);					
				}
				List<List<String>> sOutput = formObject.getDataFromDB("SELECT VALUE FROM USR_0_DEFAULTVALUE_FCR WHERE NAME ='EDMSSYSTEMAVAIL'");	
				String sEDMSAvail = sOutput.get(0).get(0);
				showList.append("<ListItems>");
				if(!acc_details.equalsIgnoreCase("Minor")) {
					if(!outQuery1.get(0).get(0).equalsIgnoreCase("No") && !outQuery1.get(0).get(0).equalsIgnoreCase("")) {
						showList.append(getDocumentList(FULL_NAME,"VALID_ENTRY_PERMIT","PERMIT",CUST_ID,sDocGroup.get("VALID ENTRY PERMIT"),outQuery1.get(0).get(0),acc_details,sEDMSAvail,sBankRelation));
					}
					if(!outQuery1.get(0).get(1).equalsIgnoreCase("No") && !outQuery1.get(0).get(1).equalsIgnoreCase("")) {
						showList.append(getDocumentList(FULL_NAME,"BANK_STATEMENT_NONUAE","NONUAE",CUST_ID,sDocGroup.get("BANK_STATEMENT_NONUAE"),outQuery1.get(0).get(1),acc_details,sEDMSAvail,sBankRelation));
					}
					if(!outQuery1.get(0).get(2).equalsIgnoreCase("No") && !outQuery1.get(0).get(2).equalsIgnoreCase("")) {
						showList.append(getDocumentList(FULL_NAME,"ADCB_LETTER","ADCB%LETTER",CUST_ID,sDocGroup.get("ADCB_LETTER"),outQuery1.get(0).get(2),acc_details,sEDMSAvail,sBankRelation));	
					}
					if(!outQuery1.get(0).get(3).equalsIgnoreCase("No") && !outQuery1.get(0).get(3).equalsIgnoreCase("")) {
						showList.append(getDocumentList(FULL_NAME,"BANK_CERTIFICATE","BANK%CERTIFICATE",CUST_ID,sDocGroup.get("BANK _CERTIFICATE"),outQuery1.get(0).get(3),acc_details,sEDMSAvail,sBankRelation));								
					}
					if(!outQuery1.get(0).get(4).equalsIgnoreCase("No") && !outQuery1.get(0).get(4).equalsIgnoreCase("")) {
						showList.append(getDocumentList(FULL_NAME,"ESTATEMENT","ESTATEMENT",CUST_ID,sDocGroup.get("ESTATEMENT"),outQuery1.get(0).get(4),acc_details,sEDMSAvail,sBankRelation));
					}
					if(!outQuery1.get(0).get(5).equalsIgnoreCase("No") && !outQuery1.get(0).get(5).equalsIgnoreCase("")) {
						showList.append(getDocumentList(FULL_NAME,"SAL_CERTIFICATE","SAL%CERTIFICATE",CUST_ID,sDocGroup.get("SAL_CERTIFICATE"),outQuery1.get(0).get(5),acc_details,sEDMSAvail,sBankRelation));
					}
					if(!outQuery1.get(0).get(6).equalsIgnoreCase("No") && !outQuery1.get(0).get(6).equalsIgnoreCase("")) {
						showList.append(getDocumentList(FULL_NAME,"ATT_SAL_CERTIFICATE","ATT%SAL%CERTIFICATE",CUST_ID,sDocGroup.get("ATT_SAL_CERTIFICATE"),outQuery1.get(0).get(6),acc_details,sEDMSAvail,sBankRelation));							
					}
					if(!outQuery1.get(0).get(7).equalsIgnoreCase("No") && !outQuery1.get(0).get(7).equalsIgnoreCase("")) {
						showList.append(getDocumentList(FULL_NAME,"PAYSLIP","PAYSLIP",CUST_ID,sDocGroup.get("PAYSLIP"),outQuery1.get(0).get(7),acc_details,sEDMSAvail,sBankRelation));
					}
					if(!outQuery1.get(0).get(8).equalsIgnoreCase("No") && !outQuery1.get(0).get(8).equalsIgnoreCase("")) {
						showList.append(getDocumentList(FULL_NAME,"OFFER_LETTER","OFFER%LETTER",CUST_ID,sDocGroup.get("OFFER_LETTER"),outQuery1.get(0).get(8),acc_details,sEDMSAvail,sBankRelation));							
					}
					if(!outQuery1.get(0).get(9).equalsIgnoreCase("No") && !outQuery1.get(0).get(9).equalsIgnoreCase("")) {
						showList.append(getDocumentList(FULL_NAME,"MINI_STMT","MINI%STMT",CUST_ID,sDocGroup.get("MINI_STMT"),outQuery1.get(0).get(9),acc_details,sEDMSAvail,sBankRelation));
					}
					if(!outQuery1.get(0).get(10).equalsIgnoreCase("No") && !outQuery1.get(0).get(10).equalsIgnoreCase("")) {
						showList.append(getDocumentList(FULL_NAME,"BANK_STATEMENT_UAE","BANK%STATEMENT%UAE",CUST_ID,sDocGroup.get("BANK_STATEMENT_UAE"),outQuery1.get(0).get(10),acc_details,sEDMSAvail,sBankRelation));
					}
					if(!outQuery1.get(0).get(11).equalsIgnoreCase("No") && !outQuery1.get(0).get(11).equalsIgnoreCase("")) {
						showList.append(getDocumentList(FULL_NAME,"EMP_CONTRACT","EMP%CONTRACT",CUST_ID,sDocGroup.get("EMP_CONTRACT"),outQuery1.get(0).get(11),acc_details,sEDMSAvail,sBankRelation));
					}
					if(!outQuery1.get(0).get(12).equalsIgnoreCase("No")&& !outQuery1.get(0).get(12).equalsIgnoreCase("")) {
						showList.append(getDocumentList(FULL_NAME,"UPGRADE_PROOF","UPGRADE%PROOF",CUST_ID,sDocGroup.get("UPGRADE_PROOF"),outQuery1.get(0).get(12),acc_details,sEDMSAvail,sBankRelation));
					}
					if(!outQuery1.get(0).get(13).equalsIgnoreCase("No") && !outQuery1.get(0).get(13).equalsIgnoreCase("")) {
						showList.append(getDocumentList(FULL_NAME,"SAL_TRNSFR_LTR","SAL%TRNSFR%LTR",CUST_ID,sDocGroup.get("SAL_TRNSFR_LTR"),outQuery1.get(0).get(13),acc_details,sEDMSAvail,sBankRelation));					
					}
					if(!outQuery1.get(0).get(14).equalsIgnoreCase("No") && !outQuery1.get(0).get(14).equalsIgnoreCase("")) {
						showList.append(getDocumentList(FULL_NAME,"UTILITY_BILL","UTILITY%BILL",CUST_ID,sDocGroup.get("UTILITY_BILL"),outQuery1.get(0).get(14),acc_details,sEDMSAvail,sBankRelation));
					}
					if(!outQuery1.get(0).get(15).equalsIgnoreCase("No") && !outQuery1.get(0).get(15).equalsIgnoreCase("")) {
						showList.append(getDocumentList(FULL_NAME,"TENANCY_CONTRACT","TENANCY%CONTRACT",CUST_ID,sDocGroup.get("TENANCY_CONTRACT"),outQuery1.get(0).get(15),acc_details,sEDMSAvail,sBankRelation));
					}
					if(!outQuery1.get(0).get(16).equalsIgnoreCase("No") && !outQuery1.get(0).get(16).equalsIgnoreCase("")) {
						showList.append(getDocumentList(FULL_NAME,"TRADE_LICENSE","TRADE%LICENSE",CUST_ID,sDocGroup.get("TRADE_LICENSE"),outQuery1.get(0).get(16),acc_details,sEDMSAvail,sBankRelation));
					}
					if(!outQuery1.get(0).get(17).equalsIgnoreCase("No") && !outQuery1.get(0).get(17).equalsIgnoreCase("")) {
						showList.append(getDocumentList(FULL_NAME,"MARR_CERTI","MARR%CERTI",CUST_ID,sDocGroup.get("MARR_CERTI"),outQuery1.get(0).get(17),acc_details,sEDMSAvail,sBankRelation));
					}
					if(!outQuery1.get(0).get(18).equalsIgnoreCase("No") && !outQuery1.get(0).get(18).equalsIgnoreCase("")) {
						showList.append(getDocumentList(FULL_NAME,"EMP_CERTI","EMP%CERTI",CUST_ID,sDocGroup.get("EMP_CERTI"),outQuery1.get(0).get(18),acc_details,sEDMSAvail,sBankRelation));							
					}
					if(!outQuery1.get(0).get(19).equalsIgnoreCase("No") && !outQuery1.get(0).get(19).equalsIgnoreCase("")) {
						showList.append(getDocumentList(FULL_NAME,"FAMILY_BOOK","FAMILY%BOOK",CUST_ID,sDocGroup.get("FAMILY_BOOK"),outQuery1.get(0).get(19),acc_details,sEDMSAvail,sBankRelation));	
					}
					if(!outQuery1.get(0).get(20).equalsIgnoreCase("No") && !outQuery1.get(0).get(20).equalsIgnoreCase("")) {
						showList.append(getDocumentList(FULL_NAME,"LABOUR_CARD","LABOUR%CARD",CUST_ID,sDocGroup.get("LABOUR_CARD"),outQuery1.get(0).get(20),acc_details,sEDMSAvail,sBankRelation));
					}
					if(!outQuery1.get(0).get(23).equalsIgnoreCase("No") && !outQuery1.get(0).get(23).equalsIgnoreCase("")) {
						showList.append(getDocumentList(FULL_NAME,"SPL_LABR_CARD","SPL%LABR%CARD",CUST_ID,sDocGroup.get("SPL_LABR_CARD"),outQuery1.get(0).get(23),acc_details,sEDMSAvail,sBankRelation));
					}
					if(!outQuery1.get(0).get(24).equalsIgnoreCase("No") && !outQuery1.get(0).get(24).equalsIgnoreCase("")) {
						showList.append(getDocumentList(FULL_NAME,"EIDA_CARD","EIDA%CARD",CUST_ID,sDocGroup.get("EIDA_CARD"),outQuery1.get(0).get(24),acc_details,sEDMSAvail,sBankRelation));
					}
					if(!outQuery1.get(0).get(25).equalsIgnoreCase("No") && !outQuery1.get(0).get(25).equalsIgnoreCase("")) {
						showList.append(getDocumentList(FULL_NAME,"STATELESS_CARD","STATELESS%CARD",CUST_ID,sDocGroup.get("STATELESS_CARD"),outQuery1.get(0).get(25),acc_details,sEDMSAvail,sBankRelation));
					}
					if(!outQuery1.get(0).get(26).equalsIgnoreCase("No") && !outQuery1.get(0).get(26).equalsIgnoreCase("")) {
						showList.append(getDocumentList(FULL_NAME,"GOVT_ID","GOVT%ID",CUST_ID,sDocGroup.get("GOVT_ID"),outQuery1.get(0).get(26),acc_details,sEDMSAvail,sBankRelation));
					}
					if(!outQuery1.get(0).get(27).equalsIgnoreCase("No") && !outQuery1.get(0).get(27).equalsIgnoreCase("")) {
						showList.append(getDocumentList(FULL_NAME,"EMP_LTR_GOVT","EMP%LTR%GOVT",CUST_ID,sDocGroup.get("EMP_LTR_GOVT"),outQuery1.get(0).get(27),acc_details,sEDMSAvail,sBankRelation));
					}
				}
				if(!outQuery1.get(0).get(21).equalsIgnoreCase("No") && !outQuery1.get(0).get(21).equalsIgnoreCase("")) {
					showList.append(getDocumentList(FULL_NAME,"PASSPORT","PASSPORT",CUST_ID,sDocGroup.get("PASSPORT"),outQuery1.get(0).get(21),acc_details,sEDMSAvail,sBankRelation));
				}
				if(!outQuery1.get(0).get(22).equalsIgnoreCase("No") && !outQuery1.get(0).get(27).equalsIgnoreCase("")) {
					showList.append(getDocumentList(FULL_NAME,"VISA","VISA",CUST_ID,sDocGroup.get("VISA"),outQuery1.get(0).get(22),acc_details,sEDMSAvail,sBankRelation));
				}
				showList.append("</ListItems>");
				iNo=iNo+1;
			}
			logInfo("",showList.toString()+"  showList");
			formObject.addItemInCombo(sGridName,showList.toString());
			SNO=1;
		} catch(Exception e){
			logError("excetion", e);
		} finally {
			sDocGroup.clear();
		}
		logInfo("loadRequiredDocument","Outside");
	}*/

	public HashMap<String, String> getDocumentGroup() {
		HashMap<String , String> hmap = new HashMap<String, String>();
		try {
			List<List<String>> sOutput = formObject.getDataFromDB("SELECT DOC_NAME,DOC_GROUP FROM USR_0_DOC_GROUP");
			for(int i=0 ; i <sOutput.size(); i++) {
				hmap.put(sOutput.get(i).get(0),sOutput.get(i).get(1));				
			}
		} catch (Exception e) {
			logInfo("","Exception while getting values in Hashmap "+e.getMessage());
		}
		return hmap;		
	}
	
	
//	public String getDocumentList(String sCustName,String sDocName,String sEDMSDocType,String CUST_ID,String sDocGroupType,String sDocDesc,String sCustType, String sEDMSAvail,String sBankRelation) {
//		int SNO=1;
//		String dataNt= "";
//		if(sBankRelation.equalsIgnoreCase("New")) {
//
//		} else if(sEDMSAvail.equalsIgnoreCase("Yes")) {
//			dataNt=getEDMSDocs(sEDMSDocType,CUST_ID);
//		}
//		if(sDocGroupType == null) {
//			sDocGroupType ="";
//		}
//		if(sDocDesc.equalsIgnoreCase("Yes")) {
//			sDocDesc ="";
//		}
//		if(dataNt.equalsIgnoreCase("")) {
//			return "<ListItem><SubItem>"+SNO+++"</SubItem><SubItem>"+sCustName+"</SubItem><SubItem>"+CUST_ID+"</SubItem><SubItem>"+sDocName+"</SubItem><SubItem>"+sDocGroupType+"</SubItem><SubItem>"+sDocDesc+"</SubItem><SubItem>NO</SubItem><SubItem></SubItem><SubItem>"+sCustType+"</SubItem><SubItem></SubItem><SubItem></SubItem></ListItem>";
//		} else {
//			String Rdate=getTagValues(dataNt,"Rdate");
//			String foldInd=getTagValues(dataNt,"FI3");
//			String docInd=getTagValues(dataNt,"Docindex");
//			return "<ListItem><SubItem>"+SNO+++"</SubItem><SubItem>"+sCustName+"</SubItem><SubItem>"+CUST_ID+"</SubItem><SubItem>"+sDocName+"</SubItem><SubItem>"+sDocGroupType+"</SubItem><SubItem>"+sDocDesc+"</SubItem><SubItem>YES</SubItem><SubItem>"+Rdate+"</SubItem><SubItem>"+sCustType+"</SubItem><SubItem>"+foldInd+"</SubItem><SubItem>"+docInd+"</SubItem></ListItem>";
//		}
//	}

	public String getEDMSDocs(String doctype,String cust_id) {	
		logInfo("Inside getEDMSDocs ","  ");
		String dataNt = "";
		try{
		    dataNt = fetchEDMSDocs(cust_id,doctype);
		} catch(Exception e) {
			logError("Exception in getEDMSDocs ",e);
		} finally {
			logInfo("Outside getEDMSDocs ","  ");
		}
		return dataNt;
	}
	
	public String fetchEDMSDocs(String sCustID,String docname) {
		logInfo("Inside fetchEDMSDocs ","  ");
		String sOutput = "";
		try{
			String sInputXML= "<?xml version=\"1.0\"?>" +
					"<APWebService_Input>" +
					"<Option>WebService</Option>" +
					"<Calltype>EMDS_DOCS</Calltype>" +
					"<CUST_ID>"+sCustID+"</CUST_ID>"+
					"<Doc_Name>"+docname+"</Doc_Name>";
			socket.connectToSocket(sInputXML);
			sOutput = socket.connectToSocket(sInputXML);
			logInfo("fetchEDMSDocs ExecuteWebservice_FCRValues-----",sOutput);	
		} catch(Exception e) {
			logError("Exception in fetchEDMSDocs ",e);
		} finally {
			logInfo("Outside fetchEDMSDocs ","  ");
		}
		logInfo("fetchEDMSDocs  sOutput  ",sOutput);
		return sOutput;
	}
	
	public void lockChequebookField() { // LockChequebookField
		logInfo("Inside lockChequebookField ","  ");
		try{
			int iRows = getGridCount(PRODUCT_QUEUE);
			String sProdCode = "";
			String sQuery = "";
			List<List<String>> sOutput = null;
			String getQuery = "SELECT visa_status FROM USR_0_CUST_TXN WHERE cust_sno='"+getPrimaryCustomerSNO()+"' "
					+ "AND WI_NAME = '"+sWorkitemId+"'";
			List<List<String>> getqQueryOutput = formObject.getDataFromDB(getQuery);
			logInfo(" getquery ",getQuery);
			logInfo(" getqueryOutput ",getqQueryOutput.toString());
			logInfo(" getqueryOutput ",getqQueryOutput.size()+""); 	
			if(getqQueryOutput != null && getqQueryOutput.size() > 0){
				String Visa = getqQueryOutput.get(0).get(0);
				for(int i = 1;i < iRows;i++) {
					sProdCode = formObject.getTableCellValue(PRODUCT_QUEUE,i,0);
					sQuery  = "SELECT CHEQUE_BOOK_FAC FROM USR_0_PRODUCT_MASTER WHERE PRODUCT_CODE = '"+sProdCode+"'";
					sOutput = formObject.getDataFromDB(sQuery);
					logInfo(" sQuery ",sQuery);
					logInfo(" sOutput ",sOutput.toString());
					logInfo(" sOutput ",String.valueOf(sOutput.size())); 
					if(sOutput.get(0).get(0).equalsIgnoreCase("N")) {
						//objChkRepeater.setEnabled(i, "AO_PRODUCT_QUEUE.CHEQUE_BOOK", false);
						//objChkRepeater.setEditable(i, "AO_PRODUCT_QUEUE.CHEQUE_BOOK", false);
					} else {
						//objChkRepeater.setEnabled(i, "AO_PRODUCT_QUEUE.CHEQUE_BOOK", true);
						//objChkRepeater.setEditable(i, "AO_PRODUCT_QUEUE.CHEQUE_BOOK", true);
					}
					if(Visa.equalsIgnoreCase("Under Processing")) {
						//objChkRepeater.setValue(i,"AO_PRODUCT_QUEUE.CHEQUE_BOOK","No");
						//objChkRepeater.setEnabled(i, "AO_PRODUCT_QUEUE.CHEQUE_BOOK", false);
						//objChkRepeater.setEditable(i, "AO_PRODUCT_QUEUE.CHEQUE_BOOK", false);
					}
				}
			}
		} catch(Exception e) {
			logError("Exception in lockChequebookField ",e);
		} finally {
			logInfo("Outside lockChequebookField ","  ");
		}
	}	
	
	public void loadBlackListDataCPD() { // LoadBlackListData_CPD
		logInfo("loadBlackListDataCPD","Inside");
		try {
			String sValues  = "";
			String resAPInsert = "";
			String sQuery = "";
			int iListViewRows = 0;
			List<List<String>> sOutput = null ;
			int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
			formObject.clearCombo(CPD_CHK_INT_BLK_LVW);
			formObject.clearCombo(CPD_HD2_LVW);
			String sName = getFinalDataComparison(CHECKBOX_FULLNAME_FCR ,CHECKBOX_FULLNAME_EIDA ,
					CHECKBOX_FULLNAME_MANUAL,FCR_NAME,EIDA_NAME
					       ,MANUAL_NAME).trim().toUpperCase();
			String sDOB = getFinalDataComparison(CHECKBOX_DOB_FCR,CHECKBOX_DOB_EIDA,
					CHECKBOX_DOB_MANUAL,FCR_DOB,EIDA_DOB
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
					String sColumn  = "BLACKLIST_TYPE,CUST_ID,CUST_NAME,CUST_TYPE,DOB,"
							+ "EMP_NAME,GENDER,MOBILE_NO,MOTHER_NAME,NATIONALITY,NOTES"
							+ ",PASSPORT_NO,POBOX_NO,REG_DATE,VISA_NO,PHONE_NO,CUST_SNO,WI_NAME,"
							+ "SEARCHED_CUST_NAME,SEARCHED_CUST_DOB,"
							+ "SEARCHED_CUST_NATIONALITY";
					if(!sMatchRecvd.equalsIgnoreCase("") && !sMatchRecvd.equalsIgnoreCase("0")) {
						int iTotalMatch = Integer.parseInt(sMatchRecvd);
						String[] sAllRecord = getTagValue(outputXml,"Records").split(";");
						String[] sEachReacord;
						String sRecord ="";
						String sNationality="";
						sQuery = "Delete from USR_0_BLACKLIST_DATA_CPD where  WI_NAME='"+sWorkitemId+"'"
								+ " and CUST_SNO='"+(iSelectedRow+1)+"'";
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
								sValues ="'"+sEachReacord[0].replaceAll("'","''")+"','"+sEachReacord[1]+"',"
										+ "'"+sEachReacord[2].replaceAll("'","''")+"','"+sEachReacord[3]+"',"
										+ "'"+sEachReacord[4]+"','"+sEachReacord[5].replaceAll("'","''")+"',"
									    + "'"+sEachReacord[6]+"','"+sEachReacord[7]+"','"+sEachReacord[8].replaceAll("'","''")+"',"
									    + "'"+sNationality+"','"+sEachReacord[10].replaceAll("'","''")+"','"+sEachReacord[11]+"',"
									    + "'"+sEachReacord[12]+"','"+sEachReacord[14]+"','"+sEachReacord[15]+"',"
									    + "'"+sEachReacord[16]+"','"+(iSelectedRow+1)+"','"+sWorkitemId+"',"
									    + "'"+sName+"','"+sDOB+"','"+sCountry+"'";
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
			sQuery ="SELECT CUST_NAME,NATIONALITY,DOB ,PASSPORT_NO,'',BLACKLIST_TYPE FROM "
					+ "USR_0_BLACKLIST_DATA_CPD WHERE WI_NAME='"+sWorkitemId+"' "
					+ "and CUST_SNO='"+(iSelectedRow+1)+"' AND BLACKLIST_TYPE IN (SELECT CHECKTYPE FROM"
							+ " USR_0_CHECK_TYPE WHERE TYPE='INT') "
							+ "ORDER BY CUST_NAME";					
			List<List<String>> recordList = formObject.getDataFromDB(sQuery);
			logInfo("loadBlackListDataCPD","sQuery: "+sQuery+",recordList: "+recordList);
			loadListView(recordList,"CUST_NAME,NATIONALITY,DOB ,PASSPORT_NO,'',"
					+ "BLACKLIST_TYPE",CPD_CHK_INT_BLK_LVW);
			sQuery ="SELECT CUST_NAME,NATIONALITY,DOB,PASSPORT_NO,'',BLACKLIST_TYPE FROM "
					+ "USR_0_BLACKLIST_DATA_CPD WHERE WI_NAME='"+sWorkitemId+"' "
					+ "and CUST_SNO='"+(iSelectedRow+1)+"' AND BLACKLIST_TYPE IN (SELECT "
					+ "CHECKTYPE FROM USR_0_CHECK_TYPE WHERE TYPE='EXT')"
					+ " ORDER BY CUST_NAME";			
			logInfo("loadBlackListDataCPD","sQuery: "+sQuery+",recordList: "+recordList);
			List<List<String>> recordList1 = formObject.getDataFromDB(sQuery);
			loadListView(recordList,"CUST_NAME,NATIONALITY,DOB ,PASSPORT_NO,'',BLACKLIST_TYPE",
					CPD_CHK_INT_BLK_LVW);
			logInfo("loadBlackListDataCPD","sQuery: "+sQuery+",recordList: "+recordList);
			iListViewRows = getGridCount(CPD_CHK_INT_BLK_LVW);
			if(iListViewRows == 0) {
				formObject.setValue(CPD_CHK_MATCH_FOUND,"Verified False Positive");
			} else {
				formObject.setValue(CPD_CHK_MATCH_FOUND,"");
			}
			iListViewRows = getGridCount(CPD_HD2_LVW);
			if(iListViewRows == 0) {
				formObject.setValue(CPD_MTCH_FOUND,"Verified False Positive");
			} else {
				formObject.setValue(CPD_MTCH_FOUND,"");
			}
			iListViewRows = getGridCount(CPD_CNTRL_BNK_BAD_LVW);
			if(iListViewRows == 0) {
				formObject.setValue(CPD_MATCH_FOUND,"Verified False Positive");
			} else {
				formObject.setValue(CPD_MATCH_FOUND,"");
			}
		} catch(Exception e) {
			logError("Exception in loadBlackListDataCPD ",e);
		} 
		logInfo("loadBlackListDataCPD ","Outside");
	}
	
	public String callBlacklist() { // CallBlacklist
		logInfo("callBlacklist","Inside");
		String outputXml = "";
		try{
			List<List<String>> sOutput = formObject.getDataFromDB("SELECT SEQ_WEBSERVICE.nextval as ID from DUAL ");
			String sOutput1 = getBlacklistInputXML(sOutput.get(0).get(0),"TP906079");
			outputXml = socket.connectToSocket(sOutput1); 
			logInfo("callBlacklist outputXml -- ",outputXml);
		} catch(Exception e) {
			logError("Exception in callBlacklist ",e);
		} finally {
			logInfo("callBlacklist ","Outside");
		}
		return outputXml;
	}

	public String fetchEtihadDetails(String sCustID,String sEtihadNo) {
		logInfo("Inside fetchEtihadDetails ","");
		String outputXml = "";
		try{
			List<List<String>> sOutput = formObject.getDataFromDB("SELECT SEQ_WEBSERVICE.nextval as ID from DUAL ");
			String sOutput1 = getEtihadInputXML(sCustID,sEtihadNo,sOutput.get(0).get(0),"TP906079");
			outputXml = socket.connectToSocket(sOutput1); 
			logInfo("fetchEtihadDetails sOutput ",sOutput.toString());	
		} catch(Exception e) {
			logError("Exception in fetchEtihadDetails ",e);
		} finally {
			logInfo("Outside fetchEtihadDetails ","  ");
		}
		return outputXml;
	}
	
	public String getEtihadInputXML(String sCustID,String sEtihad,String sSeqNo,String sUserName) {
		logInfo("getEtihadInputXML","Inside");
		String sInputXML= "<?xml version=\"1.0\"?>" +
				"<APWebService_Input>" +
				"<Option>WebService</Option>" +
				"<Calltype>Etihad_Guest_Information</Calltype>" +
				"<Customer>" +	
				"<CUST_ID></CUST_ID>"+	
				"<etihadGuestNo>"+sEtihad+"</etihadGuestNo>"+	
				"<REF_NO>"+sSeqNo+"</REF_NO>"+	
				"<USER>"+sUserName+"</USER>"+	
				"<WiName>" + sWorkitemId + "</WiName>"+
				"</Customer>"; 
		logInfo("sInputXML ",sInputXML);
		logInfo("getEtihadInputXML","Outside");
		return sInputXML;
	}
	
	public void loadCustDataOnRepeaterSelect(String sValue) { //LoadCustDataOnRepeaterSelect
		logInfo("Inside loadCustDataOnRepeaterSelect ","  ");
		try{
			formObject.setValue(SELECTED_ROW_INDEX, (Integer.parseInt(sValue))+"");
			//objChkRepeater.setEditable(Integer.parseInt(sValue), "AO_ACC_RELATION.SNO",false);
			gotFocusCustInfoDataCPDOnload();		//gotFocusCustInfoDataCPD		
			gotFocusAccountInfoCPD();
			if(formObject.getValue(RELIGION).toString().equalsIgnoreCase("")) {
				formObject.setValue(RELIGION,"Others");
			}
			if(formObject.getValue(MARITAL_STATUS).toString().equalsIgnoreCase("")) {
				formObject.setValue(MARITAL_STATUS,"Single");
			}
			if(!formObject.getValue(FCR_CNTRY).toString().trim().equalsIgnoreCase("") && 
					formObject.getValue(FCR_CNTRY).toString().trim().equalsIgnoreCase("UAE")) {
				if(formObject.getValue(RESIDENCY_STATUS).toString().equalsIgnoreCase("")) {
					formObject.setStyle(RESIDENCY_STATUS,DISABLE,TRUE);
				}
				if(returnVisaStatus().equalsIgnoreCase("")) {
					setFinalDataComparison(formObject.getValue(CHECKBOX_VISA_STATUS_FCR).toString(),
							  formObject.getValue(CHECKBOX_VISA_STATUS_EIDA).toString(),
							  formObject.getValue(CHECKBOX_VISA_STATUS_MANUAL).toString(),
							  getDateValue(FCR_VISASTATUS),getDateValue(EIDA_VISASTATUS),
							  getDateValue(MANUAL_VISASTATUS),"Not Required");
				}
				if(formObject.getValue(RA_IS_UAE_RESIDENT).toString().equalsIgnoreCase("")) {
					formObject.setValue(RA_IS_UAE_RESIDENT, "Yes");
					formObject.setStyle(RA_IS_UAE_RESIDENT,DISABLE,TRUE);
				}
			}
			if(formObject.getValue(GI_IS_CUST_VIP).toString().equalsIgnoreCase("")) {
				formObject.setValue(GI_IS_CUST_VIP , "No");
			}
			if(formObject.getValue(RA_IS_CUST_DEALNG_ARMAMNT).toString().equalsIgnoreCase("")) {
				formObject.setValue(RA_IS_CUST_DEALNG_ARMAMNT, "No");//Combo37
			}
			if(formObject.getValue(RA_IS_CUST_DEALNG_HAWALA).toString().equalsIgnoreCase("")) {
				formObject.setValue(RA_IS_CUST_DEALNG_HAWALA, "No");//Combo39
			}
			if(formObject.getValue(RA_PRPSE_TAX_EVSN).toString().equalsIgnoreCase("")) {
				formObject.setValue(RA_PRPSE_TAX_EVSN, "No");//Combo38
			}
			if(formObject.getValue(RA_IS_CUST_PEP).toString().equalsIgnoreCase("")) {
				formObject.setValue(RA_IS_CUST_PEP, "No");//Combo36
			}
			if(!formObject.getValue(FCR_NATIONALITY).toString().equalsIgnoreCase("") 
					&& formObject.getValue(FCR_NATIONALITY).toString().equalsIgnoreCase("USA")) {
				if(formObject.getValue(FAT_US_PERSON).toString().equalsIgnoreCase("")) {
					formObject.setValue(FAT_US_PERSON, "YES");
					formObject.setStyle(FAT_US_PERSON,DISABLE,TRUE);
				}
			}
			if(formObject.getValue(FAT_US_PERSON).toString().equalsIgnoreCase("Yes") || 
					formObject.getValue(FAT_LIABLE_TO_PAY_TAX).toString().equalsIgnoreCase("Yes")) {
				if(formObject.getValue(FAT_CUST_CLASSIFICATION).toString().equalsIgnoreCase("")) {
					formObject.setValue(FAT_CUST_CLASSIFICATION, "Yes");
					formObject.setStyle(FAT_CUST_CLASSIFICATION,DISABLE,TRUE);
				}
			}
			if(formObject.getValue(SCAN_MODE).toString().equalsIgnoreCase("New WMS ID")) {
				saveKYCInfo();
				saveKycMultiDropDownData();
//				savePreAssessmentDetails();    //shahbaz
				saveComparisonData();
				saveIndividualInfo();
				saveIndividualContactInfo();
			}
		} catch(Exception e) {
			logError("Exception in loadCustDataOnRepeaterSelect ",e);
		} finally {
			logInfo("Outside loadCustDataOnRepeaterSelect ","  ");
		}
	}
	
	public void gotFocusAccountInfoCPD() {
		logInfo("Inside gotFocusAccountInfoCPD ","  ");
		try{
			int iRows = getGridCount(ACC_RELATION);
			String sLockDebitCard= "False";
			for(int iLoop=1;iLoop<iRows;iLoop++) {
				if(formObject.getTableCellValue(ACC_RELATION,iLoop,9).toString().equalsIgnoreCase("JAF")) {
					sLockDebitCard = "True";
					break;
				}					
				if(formObject.getTableCellValue(ACC_RELATION,iLoop,9).toString().equalsIgnoreCase("JAO")) {
					sLockDebitCard = "True";
					break;
				}
			}
			if(sLockDebitCard.equalsIgnoreCase("True")) {
				formObject.setStyle("frame54",DISABLE,TRUE);
			} else {
				formObject.setStyle("frame54",DISABLE,FALSE);
				LoadDebitCardCombo();
			}
		} catch(Exception e) {
			logError("Exception in gotFocusAccountInfoCPD ",e);
		} finally {
			logInfo("Outside gotFocusAccountInfoCPD ","  ");
		}
	}
     
	/*public void addDebitCard() {
		logInfo("Inside addDebitCard ","  ");
		try{
			String sProductType = formObject.getValue(ACC_INFO_PG).toString(); 
			String Grptype = formObject.getValue(GROUP_TYPE).toString();
			String CardType = formObject.getValue(CARD_TYPE).toString();
			String EmbossName = formObject.getValue(EMBOSS_NAME).toString();
			String NewLink = formObject.getValue(NEW_LINK).toString();
			String existingCardNo = formObject.getValue(EXISTING_CARD_NO).toString();
			if(sProductType.equalsIgnoreCase("")) {
				sendMessageValuesList(ACC_INFO_PG,"Please Select Product Type");
				return;
			}
			if(Grptype.equalsIgnoreCase("")) {
				sendMessageValuesList(GROUP_TYPE,"Please Select Group Type");
				return;
			}
			if(NewLink.equalsIgnoreCase(NEW_LINK)) {
				sendMessageValuesList(null,"Please Select New/Link");
				return;
			}
			if(NewLink.equalsIgnoreCase("New")) {
				if(CardType.equalsIgnoreCase("")) {
					sendMessageValuesList(CARD_TYPE,"Please Select Card Type");
					return;
				}
				if(EmbossName.equalsIgnoreCase("")) {
					sendMessageValuesList(EMBOSS_NAME,"Please fill Emboss Name");
					return;
				}
			}
			else {
				if(existingCardNo.equalsIgnoreCase("")) {
					sendMessageValuesList(EXISTING_CARD_NO,"Please Select Exisiting Card No");
					return;
				}
			}
			int iRows = getGridCount(QUEUE_DC);
			if(NewLink.equalsIgnoreCase("Link")) {
				for(int i=1;i<iRows;i++) {
					if(sProductType.equalsIgnoreCase(formObject.getTableCellValue(QUEUE_DC, i, 0))
							&& NewLink.equalsIgnoreCase(formObject.getTableCellValue(QUEUE_DC, i, 4))
							&& existingCardNo.equalsIgnoreCase(formObject.getTableCellValue(QUEUE_DC, i, 5))) {
						sendMessageValuesList("","This mapping is already available");
						return;
					}
				}
			}
			int sno=1;
			if(NewLink.equalsIgnoreCase("New")) {
				for(int i=1;i< iRows;i++) {
					if("New".equalsIgnoreCase(formObject.getTableCellValue(QUEUE_DC, i, 4))) {
						sno=sno+1;
					}
				}
			}
			//objChkRepeater.addRow();
			JSONArray jsonArray = new JSONArray();
			JSONObject obj = new JSONObject();
			obj.put("acc_status", "");
			obj.put("prod_code", "");
			obj.put("prod_desc", "");
			obj.put(CURRENCY, "");
			obj.put("acc_no", "");
			obj.put("acc_brnch", "");
			obj.put("cheque_book", "");
			obj.put(CHEQUE_BOOK_NO, "");
			obj.put("mode_of_funding", "");
			obj.put(TRNSFR_FROM_ACC_NO, "");
			obj.put("from_acc_bal", "MANUAL");
			obj.put("chk_box", "");
			obj.put(AMT_TRNSFERED, "");
			obj.put("cid", "");
			obj.put("iban_no", "");
			obj.put(WI_NAME, "");
			obj.put(ACC_TITLE, "");
			obj.put("date_acc_opening", "");
			jsonArray.add(obj);
			logInfo("onClickAcctRel", "jsonArray2:: "+ jsonArray.toString());
			formObject.addDataToGrid(QUEUE_DC,jsonArray);
			formObject.setTableCellValue(QUEUE_DC,iRows, 0, Grptype);
			formObject.setTableCellValue(QUEUE_DC,iRows, 1, CardType);
			formObject.setTableCellValue(QUEUE_DC,iRows, 2, sProductType);
			formObject.setTableCellValue(QUEUE_DC,iRows, 3, EmbossName);
			formObject.setTableCellValue(QUEUE_DC,iRows, 4, NewLink);
			formObject.setTableCellValue(QUEUE_DC,iRows, 5, existingCardNo);
			formObject.setTableCellValue(QUEUE_DC,iRows, 6, sWorkitemId);
			/*objChkRepeater.setEditable(iRows,"QUEUE_DC.GROUP_TYPE",false);     
			objChkRepeater.setEditable(iRows,"QUEUE_DC.CARD_TYPE",false);
			objChkRepeater.setEditable(iRows,"QUEUE_DC.PROD_GRP",false);
			objChkRepeater.setEditable(iRows,"QUEUE_DC.EMBOSS_NAME",false);
			objChkRepeater.setEditable(iRows,"QUEUE_DC.NEW_LINK",false);
			objChkRepeater.setEditable(iRows,"QUEUE_DC.EXISTING_CARD_NO",false);
			objChkRepeater.setEditable(iRows,"QUEUE_DC.WI_NAME",false);
			objChkRepeater.setEditable(iRows,"QUEUE_DC.CARD_NO",false);
			if(NewLink.equalsIgnoreCase("New")) {
				formObject.setTableCellValue(QUEUE_DC,iRows, 6, "CARD_"+sno);
			}
			formObject.setValue(ACC_INFO_PG,"");
			formObject.setValue(GROUP_TYPE,"");
			formObject.setValue(CARD_TYPE,"");
			formObject.setValue(EMBOSS_NAME,"");
			formObject.setValue(NEW_LINK,"");
			formObject.setValue(EXISTING_CARD_NO,"");
		} catch(Exception e) {
			logError("Exception in addDebitCard ",e);
		} finally {
			logInfo("Outside addDebitCard ","  ");
		}
	} */
	
	public void segmentSelectionForExistingCustomer(String seg) { // segmentselectionforexistingcustomer
		logInfo("Inside segmentSelectionForExistingCustomer ","  ");
		try{
			if(seg.equalsIgnoreCase("Aspire")||seg.equalsIgnoreCase("Simplylife")) {
				formObject.setStyle(IDS_CB_SAL_TRANSFER,VISIBLE,TRUE);
				formObject.setStyle(IDS_CB_MORTGAGES,VISIBLE,TRUE);
				formObject.setStyle(IDS_CB_INSURANCE,VISIBLE,TRUE);
				formObject.setStyle(IDS_CB_TRB,VISIBLE,TRUE);
				formObject.setStyle(IDS_CB_OTHERS,VISIBLE,TRUE);
				formObject.setStyle(IDS_CB_VVIP,VISIBLE,TRUE);					
				formObject.setStyle(IDS_PC_CB_TP,VISIBLE,FALSE);
				formObject.setStyle(IDS_PC_CB_TRAVEL,VISIBLE,FALSE);
				formObject.setStyle(IDS_PC_CB_SPORT,VISIBLE,FALSE);
				formObject.setStyle(IDS_PC_CB_SHOPPING,VISIBLE,FALSE);
				formObject.setStyle(IDS_PC_CB_SHOPPING,VISIBLE,FALSE);
				formObject.setStyle(IDS_BNFT_CB_TP,VISIBLE,FALSE);
				formObject.setStyle("Label134",VISIBLE,FALSE);
				formObject.setStyle("Label128",VISIBLE,FALSE);
				formObject.setStyle("Label133",VISIBLE,TRUE);
			} else if(seg.equalsIgnoreCase("Private Clients") || 
					seg.equalsIgnoreCase("Excellency") ||
					seg.equalsIgnoreCase("Emirati Excellency")) {
				formObject.setStyle(IDS_CB_SAL_TRANSFER,VISIBLE,TRUE);
				formObject.setStyle(IDS_CB_MORTGAGES,VISIBLE,TRUE);
				formObject.setStyle(IDS_CB_INSURANCE,VISIBLE,TRUE);
				formObject.setStyle(IDS_CB_TRB,VISIBLE,TRUE);
				formObject.setStyle(IDS_CB_OTHERS,VISIBLE,TRUE);
				formObject.setStyle(IDS_CB_VVIP,VISIBLE,TRUE);
				formObject.setStyle(IDS_PC_CB_TP,VISIBLE,FALSE);
				formObject.setStyle(IDS_PC_CB_TRAVEL,VISIBLE,FALSE);
				formObject.setStyle(IDS_PC_CB_SPORT,VISIBLE,FALSE);
				formObject.setStyle(IDS_PC_CB_SHOPPING,VISIBLE,FALSE);
				formObject.setStyle(IDS_PC_CB_SHOPPING,VISIBLE,FALSE);
				formObject.setStyle(IDS_BNFT_CB_TP,VISIBLE,TRUE);
				formObject.setStyle("Label134",VISIBLE,FALSE);
				formObject.setStyle("Label133",VISIBLE,TRUE);
				formObject.setStyle("Label128",VISIBLE,TRUE);
			} else if(seg.equalsIgnoreCase("Privilege") || seg.equalsIgnoreCase("Emirati")) {
				formObject.setStyle(IDS_CB_SAL_TRANSFER,VISIBLE,TRUE);
				formObject.setStyle(IDS_CB_MORTGAGES,VISIBLE,TRUE);
				formObject.setStyle(IDS_CB_INSURANCE,VISIBLE,TRUE);
				formObject.setStyle(IDS_CB_TRB,VISIBLE,TRUE);
				formObject.setStyle(IDS_CB_OTHERS,VISIBLE,TRUE);
				formObject.setStyle(IDS_CB_VVIP,VISIBLE,TRUE);
				formObject.setStyle(IDS_PC_CB_TP,VISIBLE,TRUE);
				formObject.setStyle(IDS_PC_CB_TRAVEL,VISIBLE,TRUE);
				formObject.setStyle(IDS_PC_CB_SPORT,VISIBLE,TRUE);
				formObject.setStyle(IDS_PC_CB_SHOPPING,VISIBLE,TRUE);
				formObject.setStyle(IDS_PC_CB_SHOPPING,VISIBLE,TRUE);
				formObject.setStyle(IDS_BNFT_CB_TP,VISIBLE,FALSE);
				formObject.setStyle("Label128",VISIBLE,FALSE);
				formObject.setStyle("Label134",VISIBLE,TRUE);
				formObject.setStyle("Label133",VISIBLE,TRUE);
			}
		} catch(Exception e) {
			logError("Exception in segmentSelectionForExistingCustomer ",e);
		} finally {
			logInfo("Outside segmentSelectionForExistingCustomer ","  ");
		}
	}
	
	public void deleteSelectedProduct() {   // DeleteSelectedProduct
		String isClkBx="";
		String sDeleteIndexes="";
		logInfo("","In Delete Product");
		int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
		String sAccNo = formObject.getTableCellValue(PRODUCT_QUEUE, iSelectedRow, "ACC_NO").toString();
		if(!sAccNo.equalsIgnoreCase("")) {
			sendMessageValuesList(PRODUCT_QUEUE,"You can not delete this product as account has been created for this.");
			return;
		}
		//NGRepeater repDebitcard = formObject.getNGRepeater("FRAME71");
		int iRows = getGridCount(ACC_INFO_DC_LVW);
		//int iRows = repDebitcard.getRowCount();
		if(iRows>1) {
			sendMessageValuesList(ACC_INFO_DC_LVW,"Please remove the rows from Debit Card frame as you are going to change product selection");
			return;
		}
		for(int i=1; i<iRows;i++)
		{
			isClkBx = formObject.getTableCellValue(PRODUCT_QUEUE, i, "CHK_BOX").toString();
			sAccNo = formObject.getTableCellValue(PRODUCT_QUEUE, i, "ACC_NO").toString();
			if(sAccNo.equalsIgnoreCase("")) {
				logInfo("removeProducts","isClkBx---"+isClkBx);	
				if(isClkBx.equalsIgnoreCase("true")) {
					sDeleteIndexes = sDeleteIndexes+i+",";
				}
			}
		}
		sDeleteIndexes = sDeleteIndexes.substring(0,sDeleteIndexes.length()-1);
		String[] sTempDelete = sDeleteIndexes.split(",");
		for(int iLoop=0; iLoop<sTempDelete.length;iLoop++)
		{
			logInfo("","In loop");
			int[] arr = null;
			arr[0] = Integer.parseInt(sTempDelete[iLoop]);
			formObject.deleteRowsFromGrid(PRODUCT_QUEUE, arr);
			for(int innerLoop =iLoop+1;innerLoop<sTempDelete.length;innerLoop++)
			{
				logInfo("",sTempDelete[innerLoop]);
				sTempDelete[innerLoop] = (Integer.parseInt(sTempDelete[innerLoop])-1)+"";
			}
		}	
		EnableEtihadFrame();
		LoadDebitCardCombo();
	}
	
	public Boolean noChequeBookForResidenceWithoutEida() {
		logInfo("","noChequeBookForResidenceWithoutEida starts");
		String eidaCheck="";
		String chqBookVal="";
		try {
			eidaCheck=formObject.getValue(DRP_RESEIDA).toString();
			logInfo("","eidaCheck :"+eidaCheck);
			if((!"".equalsIgnoreCase(eidaCheck) || null != eidaCheck) && "Yes".equalsIgnoreCase(eidaCheck) ) {
				int iRows = getGridCount(PRODUCT_QUEUE);
				logInfo("","rows :"+iRows);
				for(int i=1;i<iRows;i++) {
					chqBookVal = formObject.getTableCellValue(PRODUCT_QUEUE, i, 8);
					//chqBookVal = objChkRepeaterforprods.getValue(i,"AO_PRODUCT_QUEUE.CHEQUE_BOOK");
					logInfo("","chqBookVal :"+chqBookVal);
					if(chqBookVal.equalsIgnoreCase("Yes")) {
						logInfo("","inside return block");
						sendMessageValuesList(PRODUCT_QUEUE,"Please select no for cheque book as residence without eida flag value is yes");
						return false;
					}
				}
			}
		} catch(Exception e) {
			logError("excetion", e);
		}
		logInfo("","noChequeBookForResidenceWithoutEida ends");
		return true;
	}
	
	/*private String afterAppRiskCheck(String data) {
		logInfo("afterAppRiskCheck", "INSIDE");
		saveScreeningDataCPD();
		String param = sWorkitemId+"','"+sProcessName;
		List<String> paramlist =new ArrayList<>();
		paramlist.add (PARAM_TEXT+sWorkitemId);
		paramlist.add (PARAM_TEXT+sProcessName);			
		formObject.getDataFromStoredProcedure("SP_TRSD_EMAIL_NGF", paramlist);
		formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
		if(submitCPDMakerValidations(data)) {
			if(!(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Return to Originator") || 
					formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Return") || 
					formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Reject"))) {
				if(submitValidationForWorkItem(data)) {
					String msg2 = confirmOnSubmitInForLoop();
					if(msg2.equalsIgnoreCase("true")) {
						return getReturnMessage(true, BTN_SUBMIT, CA008);
					} else return msg2;
				}
			} else {
				return getReturnMessage(true, BTN_SUBMIT, CA008);
			}			
		} else {
			if(!sendMessageList.isEmpty()){
				return  getReturnMessage(false,BTN_SUBMIT,sendMessageList.get(0).toString()+"###"+ 
						sendMessageList.get(1).toString());
			}
		}
		logInfo("afterAppRiskCheck", "returning false");
		return getReturnMessage(false, BTN_SUBMIT);
	}*/
	
	public boolean saveAndNextCPDFourStep(String data){
		logInfo("saveAndNextCPDFourStep","INSIDE saveAndNextCPDFourStep");
		try {
//			boolean validate = false;
			ArrayList<Boolean> TRSDclicked=new ArrayList<Boolean>();
			int selectedSheetIndex = Integer.parseInt(data);
			String ismandatory = "";
			logInfo("saveAndNextCPDFourStep"," selectedSheetIndex: "+selectedSheetIndex);
			boolean result = false;
			if(formObject.getValue(RA_IS_UAE_RESIDENT).toString().equalsIgnoreCase("")){
				calculateResidencyStatus(RA_IS_UAE_RESIDENT);	
			}
			long start_Time1=System.currentTimeMillis();
			if(selectedSheetIndex == 0) {
				logInfo("saveAndNextCPDFourStep","INSIDE Account Relationship");
				if(formObject.getValue(SOURCING_CHANNEL).toString().equalsIgnoreCase("LAPS")) {
					int iProcessedCustomer = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
					String sCustID = formObject.getTableCellValue(ACC_RELATION,iProcessedCustomer,2);
					logInfo("saveAndNextCPDFourStep","iProcessedCustomer"+iProcessedCustomer);
					logInfo("saveAndNextCPDFourStep","sCustID"+sCustID);
					setFCRValueonLoad(sCustID);
					ValidateFATCADetails("Mini");
					ValidateFATCADetails("Main");
				}
			} else if(selectedSheetIndex == 1 || selectedSheetIndex == 2 || selectedSheetIndex == 3 ||
					selectedSheetIndex == 4 || selectedSheetIndex == 5) {
				logInfo("saveAndNextCPDFourStep","INSIDE Customer Info");
				if(selectedSheetIndex == 1) {
					if(!noChequeBookForResidenceWithoutEida()) {
						return false; 
					}
					if(!validatePassportType(CHECKBOX_PASSPORT_TYPE_FCR,CHECKBOX_PASSPORT_TYPE_EIDA,CHECKBOX_PASSPORT_TYPE_MANUAL,
							FCR_PASSTYPE,EIDA_PSSTYPE,MANUAL_PASSTYPE,CA018,HD_PASS_TYPE)){
						logInfo("saveAndNextCPDFourStep","INSIDE DDE_CUSTACC ValidatePassportType");
						formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
						return false;
					}
					if(!ValidateComparisonDataCombo(CHECKBOX_VISA_STATUS_FCR,CHECKBOX_VISA_STATUS_EIDA,
							CHECKBOX_VISA_STATUS_MANUAL,FCR_VISASTATUS,EIDA_VISASTATUS,MANUAL_VISASTATUS,
							CA019,"Mandatory","Visa Status")){
						return false;
					}
					if(!validateVisaStatus()) {
						formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
						return false;
					}
					logInfo("saveAndNextCPDFourStep", "aftr validate visa status");
					/*if(validate == false && !formObject.getValue(IDS_REF_BY_CUST).toString().equalsIgnoreCase("")){
						sendMessageValuesList(BTN_FCR_SRCH,"Please Validate Reffered by customer ID.");
						formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
						return;	
					}*/
					String sShortName =getFinalDataComparison(CHECKBOX_SHORTNAME_FCR,CHECKBOX_SHORTNAME_EIDA,CHECKBOX_SHORTNAME_MANUAL,FCR_SHORTNAME,EIDA_SHORTNAME,MANUAL_SHORTNAME).trim();
					if(sShortName !=null  && sShortName.length() > 20){
						sendMessageValuesList(MANUAL_SHORTNAME, "Short name length must be less than or equal to 20");
						return false;
					}
					logInfo("saveAndNextCPDFourStep", "aftr Validate Reffered by customer ID");
					try  {
						String sFinalDOB = getFinalDataComparison(CHECKBOX_DOB_FCR,CHECKBOX_DOB_EIDA,CHECKBOX_DOB_MANUAL,FCR_DOB,EIDA_DOB,MANUAL_DOB);
						int age = calculateAge(sFinalDOB);
						int age1 = CalculateAge1(sFinalDOB);
						int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION, iSelectedRow,9);
						String sQueryy="select VALUE from usr_0_defaultvalue_fcr where name='Minor_Age'";
						List<List<String>> list = formObject.getDataFromDB(sQueryy);
						logInfo("onTabCPDMakerFourStep","sQueryy"+sQueryy);
						String a =  list.get(0).get(0);
						int sMinorAge= Integer.parseInt(a);
						logInfo("onTabCPDMakerFourStep",""+sMinorAge);
						if(sAccRelation.equalsIgnoreCase("Minor")) {
							if(age1 >=sMinorAge) {
								if(formObject.getValue(CHECKBOX_DOB_FCR).toString().equalsIgnoreCase(TRUE)) {
									sendMessageValuesList(FCR_DOB,"Date Of Birth Should Be Less Than "+sMinorAge+" Years For Minor Customer.");
								}
								else if(formObject.getValue(CHECKBOX_DOB_EIDA).toString().equalsIgnoreCase(TRUE)) {
									sendMessageValuesList(EIDA_DOB,"Date Of Birth Should Be Less Than "+sMinorAge+" Years For Minor Customer.");
								}
								else if(formObject.getValue(CHECKBOX_DOB_MANUAL).toString().equalsIgnoreCase(TRUE)) {
									sendMessageValuesList(MANUAL_DOB,"Date Of Birth Should Be Less Than "+sMinorAge+" Years For Minor Customer.");
								}
								return false;	
							}
						} else {
							if(!formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only")) {
								if(age<18) {
									if(formObject.getValue(CHECKBOX_DOB_FCR).toString().equalsIgnoreCase(TRUE)) {
										sendMessageValuesList(FCR_DOB,"Date Of Birth Should Be Greater Than or "
												+ "equal to 18 Years.");
									}
									else if(formObject.getValue(CHECKBOX_DOB_EIDA).toString().equalsIgnoreCase(TRUE)) {
										sendMessageValuesList(EIDA_DOB,"Date Of Birth Should Be Greater Than or equal to 18 Years.");
									}
									else if(formObject.getValue(CHECKBOX_DOB_MANUAL).toString().equalsIgnoreCase(TRUE)) {
										sendMessageValuesList(MANUAL_DOB,"Date Of Birth Should Be Greater Than or "
												+ "equal to 18 Years.");
									}
									return false;	
								}
							}
						}
					}
					catch (Exception e) {
						logError("executeServerEvent", e);
					}					
					boolean result1=false;
					result1=checkNatSegment();
					logInfo("saveAndNextCPDFourStep","NEW VALIDATION---"+result1);
					if(!result1) {
						formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
						return false;
					}
					logInfo("saveAndNextCPDFourStep", "bfr calling mandatoryCustInfoFields");
					//result1 = mandatoryComparisonData(); 
					result1 = mandatoryCustInfoFields();
					logInfo("mandatoryCustInfoFields","mandatoryCustInfoFields---"+result1);
					if(!result1) {
						return false;
					}
					result1 = mandatoryIndividualInfo(); 
					logInfo("mandatoryComparisonData","COMMAND24 Individual Info result---"+result);
					if(!result1) {
						//formObject.setStyle("static_next",DISABLE,FALSE);
						return false;
					}
					try {
						int sSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;				
						String sQuery1 = "SELECT IS_DEDUPE_CLICK_CPD FROM USR_0_CUST_TXN WHERE WI_NAME ='"+sWorkitemId+"'"
								+ " and cust_sno='"+sSelectedRow+"'";
						List<List<String>> output1 = formObject.getDataFromDB(sQuery1);
						String dedupeDone = output1.get(0).get(0);
						if(!dedupeDone.equalsIgnoreCase("true")){
							sendMessageValuesList(BTN_DEDUPE_SEARCH,"Please Do Dedupe Search For This Customer.");
							formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);	
							return false;
						}
					}
					catch(Exception e){
						logError("saveAndNextCPDFourStep",e);
					}
					/*if(!validateMobileNo(CHECKBOX_TELE_MOB_FCR,CHECKBOX_TELE_MOB_EIDA,CHECKBOX_TELE_MOB_MANUAL,
							FCR_MOBILE,EIDA_MOBILE,MANUAL_MOBILE,CA0126)) {
						if(!mobileConfirmFlag || mobileChangeFlag) {
							return false;
						}
					}*/
					
				} else if(selectedSheetIndex == 2) {//CA094
					if(formObject.getValue(PD_CUSTSEGMENT).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(PD_CUSTSEGMENT,CA042);
						return false;
					}
					logInfo("mandatoryPersonalDetailsFields","Bfr Calling mandatoryPersonalDetailsFields---");
					result = mandatoryPersonalDetailsFields();
					logInfo("mandatoryPersonalDetailsFields","mandatoryPersonalDetailsFields---"+result);
					if(!result){
						return false;
					}
					result = mandatoryContactInfo(); //2
					logInfo("BTN_SUBMIT","COMMAND24 Contact Info result---"+result);
					if(!result) {
						//formObject.setStyle("static_next",DISABLE,FALSE);
						return false;
					}
					String sFinalData = getFinalDataComparison(CHECKBOX_COUNTRY_PER_RES_FCR,CHECKBOX_COUNTRY_PER_RES_EIDA,
							CHECKBOX_COUNTRY_PER_RES_MANUAL,FCR_PER_CNTRY,EIDA_PER_CNTRY,MANUAL_PER_CNTRY);
					if(!sFinalData.equalsIgnoreCase(formObject.getValue(PERM_CNTRY).toString())){
						sendMessageValuesList(PERM_CNTRY,"Permanent Country data is not same. Please change it.");
						return false;
					} else  {
						if(!formObject.getValue(ED_DATE_OF_JOING).toString().equalsIgnoreCase("")) {
							if(!validateFutureDate(ED_DATE_OF_JOING,"Date of Joining")) {
								return false;
							}
						}
					} 
					if(!validate && !formObject.getValue(IDS_REF_BY_CUST).toString().equalsIgnoreCase("")){
						sendMessageValuesList(BTN_FCR_SRCH,"Please Validate Referred by Customer ID.");
						formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
						return false;	
					}
					if(!formObject.getValue(PD_DATEOFATTAININGMAT).toString().equalsIgnoreCase("")){
						SimpleDateFormat simpledateformat = new SimpleDateFormat(DATEFORMAT);
						Date d = new Date();
						boolean rtn = compareDateMMM(formObject.getValue(PD_DATEOFATTAININGMAT).toString(),simpledateformat.format(d).toString());
						logInfo("saveAndNextCPDFourStep","rtn == "+rtn);
						if(!rtn) {
							sendMessageValuesList(PD_DATEOFATTAININGMAT,"Date Of Attaining Majority Should Be Future Date.");
							formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);						
							return false;	
						}
					}
				} else if(selectedSheetIndex == 3) {
					result = MandatoryiKYC_CPD(); // 3
					logInfo("BTN_SUBMIT","KYC result---"+result);
					if(!result) {
						formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
						return false;
					}
					if(!formObject.getValue(CUST_NATIONALITY).toString().equalsIgnoreCase("United Arab Emirates")){
						result = mandatoryEmploymentInfo();
						if(!result){
							formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
							return false;
						}
					}
					else {
						if(!formObject.getValue(ED_DATE_OF_JOING).toString().equalsIgnoreCase("")){
							if(!validateFutureDate(ED_DATE_OF_JOING,"Date of Joining")){
								formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
								return false;
							}
						}
					}
					if(!MandatoryEmploymentAddress()){
						formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
						return false;
					}
				} else if(selectedSheetIndex == 4 || selectedSheetIndex == 5) {//if(selectedSheetIndex == 4) {
					String custSegment = formObject.getValue(PD_CUSTSEGMENT).toString();
					String query;
					List<List<String>> output = null ;
					query = "select iscrsmandatory from usr_0_cust_segment where cust_segment='"+custSegment+"'";
					output = formObject.getDataFromDB(query);
					try {
						if(output!=null && output.size()>0)
							ismandatory = output.get(0).get(0);
					} catch (Exception e) {
						logError("saveAndNextCPDFourStep",e);
					} 
					//}
					logInfo("saveAndNextCPDFourStep","ismandatory"+ismandatory);
					boolean custSegmentCheck;
					if(ismandatory.equalsIgnoreCase("Yes"))
						custSegmentCheck=true;
					else
						custSegmentCheck=false;
					if(selectedSheetIndex == 4) {
						if (formObject.getValue(CRS_CITYOFBIRTH).toString().isEmpty()) {
							sendMessageValuesList(CRS_CITYOFBIRTH,"Please enter city of birth");
							return false;
						}
						result = mandatoryCRSCheck(custSegmentCheck);
						logInfo("saveAndNextCPDFourStep","Submit CRS check result"+result);
						if(!result){
							formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
							return false;
						}  
						boolean crscategory=mandatoryCRSCheckcategorychange(); //5
						if(!crscategory && formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")) {
							return false;
						}
					} else if(selectedSheetIndex == 5) {
						result = mandatoryAtFatca();
						logInfo("","Submit validation fatca result now---"+result);
						long end_Time5=System.currentTimeMillis();
						if(!result) {
							formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
							return false;
						}
					}
				}
				/*if(!formObject.getValue(PD_DATEOFATTAININGMAT).toString().equalsIgnoreCase("")){
					SimpleDateFormat simpledateformat = new SimpleDateFormat(DATEFORMAT);
					Date d = new Date();
					boolean rtn = compareDateMMM(formObject.getValue(PD_DATEOFATTAININGMAT).toString(),simpledateformat.format(d).toString());
					logInfo("saveAndNextCPDFourStep","rtn == "+rtn);
					if(!rtn) {
						sendMessageValuesList(PD_DATEOFATTAININGMAT,"Date Of Attaining Majority Should Be Future Date.");
						formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);						
						return;	
					}
				}*/
				long end_Time6 = System.currentTimeMillis();
				//logInfo("saveAndNextCPDFourStep","Diff Time for Other Calls---"+Long.toString(end_Time6-end_Time5));
				saveKYCInfo();		
			    saveKycMultiDropDownData();
//				savePreAssessmentDetails();    //shahbaz
				long end_Time7 = System.currentTimeMillis();
				logInfo("saveAndNextCPDFourStep","Diff Time for SaveKYCInfo---"+Long.toString(end_Time7-end_Time6));
				saveIndividualInfo();
				long end_Time8 = System.currentTimeMillis();
				logInfo("saveAndNextCPDFourStep","Diff Time for SaveIndividualInfo---"+Long.toString(end_Time8-end_Time7));
				saveComparisonData();
				long end_Time9 = System.currentTimeMillis();
				logInfo("saveAndNextCPDFourStep","Diff Time for saveComparisonData---"+Long.toString(end_Time9-end_Time8));
				saveIndividualContactInfo();
				long end_Time10 = System.currentTimeMillis();
				logInfo("","Diff Time for SaveIndividualContactInfo---"+Long.toString(end_Time10-end_Time9));
				saveClientQuestionsData();  
				saveCRSDetails(); 
				//saveDuplicateData();everything commented in old code
				List<List<String>> output = null;
				if(selectedSheetIndex == 1) {
					try {
						String sQuery1 = "SELECT COUNT(A.WI_NAME) IS_MOB_CHANGE FROM USR_0_CUST_TXN A, ACC_RELATION_REPEATER"
								+ " B WHERE A.WI_NAME='"+ sWorkitemId +"' AND A.WI_NAME=B.WI_NAME  AND A.CUST_SNO =B.SNO AND"
								+ " B.BANK_RELATION='Existing' AND A.final_mobile_no <> nvl(A.fcr_mobile_no,0)"
								+ " and a.final_mobile_no <> nvl(a.AFTER_CONT_CNTR_MOB_NO,0)";
						output = formObject.getDataFromDB(sQuery1);
						String isMobChange =  (output != null && output.size() > 0)  ? output.get(0).get(0).toString() : "";
						if(Integer.parseInt(isMobChange)>0) {
							formObject.setValue(MOBILE_CHANGE_FLAG,"True");
						}
						else {
							formObject.setValue(MOBILE_CHANGE_FLAG,"False");
						}
					}
					catch(Exception e) {
						logError("saveAndNextCPDFourStep",e);
					}
				}
				try {
					int iProcessedCustomer = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
					String sCID = formObject.getTableCellValue(ACC_RELATION,iProcessedCustomer,2);
					String sCASA= "";
					String sBankRelation = formObject.getTableCellValue(ACC_RELATION, iProcessedCustomer, 7);
					String sCustNo="";				
					sCustNo = formObject.getTableCellValue(ACC_RELATION, iProcessedCustomer, 0);
					if(sBankRelation.equalsIgnoreCase("Existing")){
						String squery = "SELECT COUNT(1) AS COUNT_WI FROM USR_0_PRODUCT_EXISTING WHERE WI_NAME ="
								+ "'"+sWorkitemId+"' AND CUSTOMER_ID ='"+sCID+"' AND ACCOUNT_TYPE ='CASA'";
						List<List<String>>sOutput = formObject.getDataFromDB(squery);
						sCASA = sOutput.get(0).get(0);						
					}
					if(sBankRelation.equalsIgnoreCase("New") || sCASA.equalsIgnoreCase("0")){
						String soutput = getApplicationRiskInputXML(iProcessedCustomer);
						String outputresult = socket.connectToSocket(soutput);
						if(outputresult.equalsIgnoreCase("NO")) {
							sendMessageValuesList("", "Selected passport holder and Non UAE Residents,"
									+ " not allowed to open Account");//CH_14032017
							return false;
						}		
						else if(outputresult.equalsIgnoreCase("Partial")){
							logInfo("saveAndNextCPDFourStep","outputresult: "+outputresult);
						}
					}
				}
				catch(Exception e) {
					logError("saveAndNextCPDFourStep",e);
				}
				/*if(formObject.getValue(SOURCING_CHANNEL).toString().equalsIgnoreCase("LAPS")) {
					callTRSD("CPD");
					if( formObject.getValue(CPD_TRSD_FINAL_DECISION).toString().equalsIgnoreCase("Approved"))
						formObject.setValue(CPD_FINAL_ELIGIBILITY, "Eligible");
					else
						formObject.setValue(CPD_FINAL_ELIGIBILITY, "Not Eligible");
					int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
					String sCustNo = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 0);
					try {
						logInfo("saveAndNextCPDFourStep","TRSD clicked set true arraylist");
						TRSDclicked.set(Integer.parseInt(sCustNo)-1, true);
						logInfo("saveAndNextCPDFourStep","TRSD clicked set true arraylist completed");
					}
					catch(Exception e){
						logError("saveAndNextCPDFourStep",e);
					}
					insertBankDecisionFromTRSD(formObject.getValue(CPD_TRSD_FINAL_DECISION).toString());	//added by harinder
					if(formObject.getValue(CPD_TRSD_FINAL_DECISION).toString().equalsIgnoreCase("Approved")
							|| formObject.getValue(CPD_TRSD_FINAL_DECISION).toString().equalsIgnoreCase("Pending")){
						formObject.setStyle((BTN_CPD_TRSD_CHK),DISABLE,TRUE);
					}							
				}*/	
			}
			else if(selectedSheetIndex == 7) {
				logInfo("saveAndNextCPDFourStep","INSIDE Sanction Screening");//2	
				int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
				String sCustNo = formObject.getTableCellValue(ACC_RELATION,iSelectedRow,0);
				String sQuery21 = "update USR_0_BRMS_TRACKER set SCREENING_STATUS = Success where WI_NAME ="
						+ "'"+sWorkitemId+"'AND CUST_SNO='"+sCustNo+"' AND SCREENING_STATUS ='Pending'";
				formObject.saveDataInDB(sQuery21);
				saveScreeningDataCPD();
				long end_time1=System.currentTimeMillis();
			} else if(selectedSheetIndex == 12) {
				logInfo("saveAndNextCPDFourStep","INSIDE Delivery Preference");
				if(formObject.getValue(CHANNEL_TYPE).toString().equalsIgnoreCase("")){
					String query = "SELECT CHANNEL_TYPE FROM USR_0_SERVICE_CHANNEL WHERE SERVICE_CHANNEL"
							+ " ='"+formObject.getValue(SOURCING_CHANNEL).toString()+"'";
					logInfo("saveAndNextCPDFourStep","query"+query);
					addDataInComboFromQuery(query,CHANNEL_TYPE);
				}
				if(formObject.getValue(CHANNEL_TYPE).toString().equalsIgnoreCase("Alternate")){
					boolean result3 = mandatoryDeliveryMode_InstantIssue_OnNext();
					if(!result3){
						return false;
					}
				}
			} else if(selectedSheetIndex == 13){
				logInfo("saveAndNextCPDFourStep","INSIDE Category change");
				//formObject.RaiseEvent("WFSave");
				if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("New Account with Category Change")
						|| formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only"))
				{
					boolean result1 = false;
					result1 = mandatoryCategoryChangeData();
					if(!result1) {
						return false;
					}
					result=checkNatCatSegment();
					if(!result){
						formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
						return false;
					}
				}
			} else if(selectedSheetIndex == 14){
				logInfo("saveAndNextCPDFourStep","INSIDE SI");
				logInfo("saveAndNextCPDFourStep","INSIDE Standing Instruction");
				saveStandingInstrInfo();
				saveStandInstrInfo();
			} else if(selectedSheetIndex == 17) { // Family Banking 
				clearFBData(); //Clearing FB tab on category change
				if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase
						("New Account with Category Change") || 
						formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase
						("Category Change Only")) {
					/*if(validateFBFetch()) {
						isValidateFBDone();
					}*///FB SUPPRESSED
					if(validateFBFetch()) {
						isValidateFBDone();
					}//FB ADDED
				}
			}
		} catch(Exception e) {
			logError("Exception in saveAndNextCPDFourStep ",e);
		}
		return true;
	}
}
