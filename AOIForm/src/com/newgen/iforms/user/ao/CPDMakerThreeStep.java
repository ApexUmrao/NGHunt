package com.newgen.iforms.user.ao;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.newgen.iforms.EControl;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.iforms.user.ao.util.XMLParser;
import com.newgen.iforms.user.config.Constants;
import com.newgen.mvcbeans.model.WorkdeskModel;

public class CPDMakerThreeStep extends Common implements Constants, IFormServerEventHandler{

	boolean isFactaCompleteValiadate = false;
	boolean custInfoTabLoad = false;
	boolean sancScreeningTabClicked = false;
	boolean appAssessTabClicked = false;
	boolean sendToCompliance = false;
	String custSno = "";
	public boolean validate = false;
	List<String> processedCustomerList= new ArrayList<String>();
	
	public CPDMakerThreeStep(IFormReference formObject) {
		super(formObject);
	}

	@Override
	public void beforeFormLoad(FormDef arg0, IFormReference arg1) {
		String workitemName = formObject.getObjGeneralData().getM_strProcessInstanceId();
		logInfo("WorkItem Name: " , workitemName);				
	}

	@Override
	public String executeCustomService(FormDef arg0, IFormReference arg1, String arg2, String arg3, String arg4) {
		return null;
	}

	@Override
	public JSONArray executeEvent(FormDef arg0, IFormReference arg1, String arg2, String arg3) {
		return null;
	}
	
	public void disableFetchUAEButton(){
		try{
			String isUAEPassAuthFlag = ""+formObject.getValue("UAE_PASS_AUTH_FLAG");
		    String isUAEPassAuthDone = ""+formObject.getValue("IS_UAE_PASS_AUTH_DONE");
			/*List<List<String>> ls = formObject.getDataFromDB("SELECT UAE_PASS_AUTH_FLAG , "
					+ "IS_UAE_PASS_AUTH_DONE FROM EXT_AO WHERE WI_NAME = '"+sWorkitemId+"'");
			 logInfo("disableFetchUAEButton","ls "+ls.toString());
			if(ls!=null && ls.size()>0){
				isUAEPassAuthFlag = ls.get(0).get(0);
				isUAEPassAuthDone = ls.get(0).get(1);
				formObject.setValue("UAE_PASS_AUTH_FLAG", isUAEPassAuthFlag);
				formObject.setValue("IS_UAE_PASS_AUTH_DONE", isUAEPassAuthDone);
			}*/
		    
			logInfo("disableFetchUAEButton","isUAEPassAuthFlag "+isUAEPassAuthFlag+"::isUAEPassAuthDone::"+isUAEPassAuthDone);
		    if("Y".equalsIgnoreCase(isUAEPassAuthFlag)){
			   if("Y".equalsIgnoreCase(isUAEPassAuthDone)){
				   formObject.setStyle("FETCH_UAE_PASS_INFO","disable", "true");
			   }
		    }
		}catch(Exception ex){
			logError("disableFetchUAEButton", ex);
		}
		
	}
	
	@Override
	public String executeServerEvent(IFormReference formObject, String controlName, String eventType, String data) {
		logInfo("CPDMakerThreeStep","Inside executeServerEvent CPDMakerThreeStep Event: " 
	      + eventType + ", Control Name: "+ controlName + ", Data: " + data);
		sendMessageList.clear();
		String retMsg = getReturnMessage(true);
		List<List<String>> recordList = null;
		String controlValue = formObject.getValue(controlName).toString();
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
			logInfo("CPDMakerThreeStep","Inside executeServerEvent try");
			if (eventType.equalsIgnoreCase(EVENT_TYPE_LOAD)) {
				onLoadCPDMakerThreeStep(controlName, eventType, data);
				custSno = formObject.getValue(SELECTED_ROW_INDEX).toString();
				fbValidation();	
				populateUAEPassInfoStatus(sWorkitemId);
				showCustSegmentPC();
				populatePreAssesmentDetails();
				populatePepAssesmentDetails();
			}  else if (eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)){
				setEIDA();
				setAutoFilledFieldsLocked();
				if(controlName.equalsIgnoreCase("FETCH_UAE_PASS_INFO")){
					String wiName = uaePassWILinkForCPD();
					updateAccRelationForLinkWi(wiName);
					
					//String wiName = getUaePassWILinkForCPD();
					if(callEidaValidation()){
						implementUaePassLogic(wiName); 
						if(wiName.equalsIgnoreCase("")){
							wiName = sWorkitemId;
						} 
//						updateAccRelGridFromUaePassInfo(wiName); //Commented19012023
						populateUAEPassInfoStatus(sWorkitemId); // changed to child wi 18012023
						//logInfo("CT_BTN_REFRESH","Before");
						populateCustInfoFromUaePass();
						//logInfo("CT_BTN_REFRESH","After");
						//formObject.setStyle("CRO_DEC","disable", "false");
						formObject.setValue("IS_UAE_PASS_INFO_CLICKED","Y");
						formObject.setStyle("FETCH_UAE_PASS_INFO","disable", "false");
						disableFetchUAEButton();
						addDocToLinkedWI(formObject, wiName);
					} else {
						sendMessageValuesList("","Eida Not Valid");	
						formObject.setValue("CRO_DEC","Reject");
						formObject.setStyle("CRO_DEC","disable", "true");
						return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));

					}
				} else if(controlName.equalsIgnoreCase("ConfirmAuthDone")) {
					confirmUAEPassAuthDone();
				}else if(controlName.equalsIgnoreCase(CT_BTN_REFRESH)) {
					int iProcessedCustomer=Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
					String sCustID  = formObject.getTableCellValue(ACC_RELATION, iProcessedCustomer , 2);
				  //logInfo("CT_BTN_REFRESH","iProcessedCustomer----"+iProcessedCustomer);
					logInfo("CT_BTN_REFRESH","sCustID----"+sCustID);
					if(sCustID.equalsIgnoreCase("")) {
						sendMessageValuesList("", "Customer ID not present for this customer");
						return "";
					}
					setFCRValueonLoad(sCustID);
				} else if("VALIDATE_PRODUCT_MODIFY".equalsIgnoreCase(controlName)) {
					if(checkFundTransferFields()) {
						return retMsg;
					}
				} else if("PRODUCT_ROW_CLICK".equalsIgnoreCase(controlName)) {
					enableDisableChequebookField();
					enableDisableProductFields();
				} else if(controlName.equalsIgnoreCase(FETCH_INFO)) { //DP
					executeFetch(data);
					if(controlName.equalsIgnoreCase(FETCH_INFO)  && formObject.getValue(RD_INST_DEL).toString().equalsIgnoreCase("Yes")) {
						logInfo("CHANGE EVENT: FETCH_INFO","Fetch info button clicked after no radio button is clicked........111111");
						formObject.setStyle(NOM_REQ, DISABLE, TRUE);
						formObject.setStyle(EXISTING_NOM_PRSN, DISABLE, TRUE);
						formObject.clearTable(DELIVERY_PREFERENCE);
					}
				} else if(controlName.equalsIgnoreCase(BTN_SI_ADD)) {
					siAdd();
				} else if(controlName.equalsIgnoreCase(BTN_SI_MOD)) {
					siModd();
				} else if (RD_INST_DEL.equalsIgnoreCase(controlName)){
					rdInstDel();
				} else if(PRODUCT_QUEUE.equalsIgnoreCase(controlName) || "PROD_CODE".equalsIgnoreCase(controlName)) {
					if(addProductInGrid()) {
						return getReturnMessage(true, controlName);
					}
				} else if("PRODUCT_QUEUE_POST".equalsIgnoreCase(controlName)) {
					EnableEtihadFrame();
					LoadDebitCardCombo();
					EnableFamilyReffered();
				} else if(TABCLICK.equalsIgnoreCase(controlName)) {  
					onTabClickCPDThreeStep(data);
				} else if (controlName.equalsIgnoreCase(BTN_APP_LEVEL_RISK)){
					appLevelMandatoryCheck =false;
					//Ao dcra changes for upgrade and cco
					if (!(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade")
					   ||formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only"))) {
					   calculateIndRiskThreeStep();
					}
                     //ATP-310 DATA NOT INSERTD IN REKEY TABLE
					if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only")) {
					//	logInfo("reKeyInsert","Insert into rekey<<<>>>");
						reKeyInsert();
						updateReKeyTemp("CPD");
					}
					updateRiskAssessmentData();
					fieldValueUsr_0_Risk_Data();// to be made
					calculateAppRisk();
					
				} else if(controlName.equalsIgnoreCase("saveTabClick")){
					int sheet = Integer.parseInt(data);
					if(sheet == 1 || sheet == 2 || sheet == 3 || sheet == 4 || sheet == 5) {
						onSaveClick();
						if(sheet == 3) {
							savePepAssessmentDetails();
						}
					} else if(sheet == 10) {
						insertUdfDetails();
					}
					onSaveTabClickCPDMakerThree(Integer.parseInt(data));
				} else if("saveNextTabClick".equalsIgnoreCase(controlName)) {
					int sheet = Integer.parseInt(data.split(",")[1]);
					if(sheet == 1 || sheet == 2 || sheet == 3 || sheet == 4 || sheet == 5) {
						onSaveClick();
						if(sheet == 3) {
							savePepAssessmentDetails();
						}
						confirmUAEPassAuthDone();
						setManualEida(sheet);
						savePepAssessmentDetails();
					} else if(sheet == 10) {
						insertUdfDetails();
					}
					saveAndNextCPDThreeStep(data);
				} else if (controlName.equalsIgnoreCase("AccInfo_UdfList")) {
					accinfoUdflist(controlName);
				} else if(BTN_SUBMIT.equalsIgnoreCase(controlName) && data.equalsIgnoreCase("Finally_Decision_Clear")) {// Product_Clear
					if(submitWorkItem(eventType,data) && checkProductOffered(formObject)) {
						return "true";
					}
				} else if(BTN_SUBMIT.equalsIgnoreCase(controlName) && data.equalsIgnoreCase("ConfirmAuthDone")) {
					confirmUAEPassAuthDone();
				}
				else if(BTN_SUBMIT.equalsIgnoreCase(controlName) && data.equalsIgnoreCase("CA008_Clear")){// CA008_Clear
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
							insertEventgenUAE();
							return getReturnMessage(true, controlName);
						}
					}					
				} else if(controlName.equalsIgnoreCase(BTN_SUBMIT) && data.equalsIgnoreCase("confirmAppRisk")) {
					return afterAppRiskCheck(data);
				} else if(controlName.equalsIgnoreCase(BTN_SUBMIT)) {
					dormantCustAlert();   //Modifed by Shivanshu for Dormant Customer Alert 13-01-2025
					if(!"Y".equalsIgnoreCase(dormantCustomer) 
							&& formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Return to Originator")) {
						//gurwinder for uaepass
						// newly commented
						String query = "select TRSD_2_STATUS from USR_0_TRSD_DETAILS where WI_NAME='"+sWorkitemId+"'";
						logInfo("BTN_SUBMIT","query: "+query);
						List<List<String>> list = formObject.getDataFromDB(query);
						logInfo("BTN_SUBMIT","list: "+list);
						String trsdDec = "";
						if(list.size() > 0) {
							trsdDec = list.get(0).get(0);
						}
						validateCrsClassification(); //added by reyaz 16092022
						//	logInfo("currentRiskChange",controlValue);
						if(!sancScreeningTabClicked && !trsdDec.equalsIgnoreCase("Returned")) {
							sendMessageValuesList("", "Please click on Sanction Screening tab before proceeding");
							return "";
						} else if(!appAssessTabClicked && !trsdDec.equalsIgnoreCase("Returned")) {
							sendMessageValuesList("", "Please click on Application Assessment tab before proceeding");
							return "";
						}
						if(sendToCompliance && formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")) {
							String resultTRSD =	checkTRSDResult("CPD");
							String resultTRSD1 = checkTRSDResult1("CPD");
							//	logInfo("","resultTRSD CPD:"+resultTRSD+"::");
							if(resultTRSD.equalsIgnoreCase("1")  
									&& !formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Return to Originator")
									&& "Returned".equalsIgnoreCase(resultTRSD1)) {
								sendMessageValuesList("","This application can only be Returned since TRSD result is Returned.");
								return "";
							} else {
								sendMessageValuesList(CRO_DEC, "Compliance approval is required in order to proceed.");


								formObject.setValue("CRO_DEC", "");


								return "";
							}
						}
						onSaveClick();
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
						if(!noChequeBookForResidenceWithoutEida()) {
							return ""; 
						}
						if(!MandatoryEmploymentAddress()){
							return "";
						}
						String accOwnType = formObject.getValue(ACC_OWN_TYPE).toString();
						logInfo("appLevelMandatoryCheck","appLevelMandatoryCheck : "+appLevelMandatoryCheck);
						if(appLevelMandatoryCheck && !accOwnType.equalsIgnoreCase("Single")){
							sendMessageValuesList(BTN_APP_LEVEL_RISK,"Please perform Application Level Risk Check");
							return "";
						}
						String ismandatory = "";
						String custSegment = formObject.getValue(PD_CUSTSEGMENT).toString();
						//					String query;
						List<List<String>> outputTemp = null ;
						query = "select iscrsmandatory from usr_0_cust_segment where cust_segment='"+custSegment+"'";
						outputTemp = formObject.getDataFromDB(query);
						try {
							if(outputTemp!=null && outputTemp.size()>0)
								ismandatory = outputTemp.get(0).get(0);
						} catch (Exception e) {
							logError("saveAndNextCPDThreeStep",e);
						} 
						//}
						//	logInfo("saveAndNextCPDThreeStep","ismandatory"+ismandatory);
						boolean custSegmentCheck;
						if(ismandatory.equalsIgnoreCase("Yes"))
							custSegmentCheck=true;
						else
							custSegmentCheck=false;

						boolean	result11 = mandatoryCRSCheck(custSegmentCheck);
						logInfo("saveAndNextCPDThreeStep","Submit CRS check result"+result11);
						if(!result11){
							formObject.setStyle(BTN_SUBMIT,DISABLE,FALSE);
							return "";
						}  
						// newly added ended
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
						formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
						try {
							//	updateTRSDDecision();
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
						//	logInfo("","NEW VALIDATION---"+result);
						if(!result) {
							formObject.setStyle(BTN_SUBMIT,DISABLE,FALSE);
							return "";
						}
						result = mandatoryComparisonData();
						//	logInfo("BTN_SUBMIT","COMMAND24 KYC result---"+result);
						if(!result) {
							return "";
						}

						result = mandatoryIndividualInfo();
						logInfo("BTN_SUBMIT","COMMAND24 Individual Info result---"+result);
						if(!result) {
							return "";
						}

						result = mandatoryContactInfo();
						logInfo("BTN_SUBMIT","COMMAND24 Contact Info result---"+result);
						if(!result) {
							return "";
						}
						if(!formObject.getValue(CUST_NATIONALITY).toString().equalsIgnoreCase("United Arab Emirates")) {
							result = mandatoryEmploymentInfo();
							logInfo("BTN_SUBMIT","COMMAND24 Employment result---"+result);
							if(!result) {
								return "";
							}
						} else {
							if(!formObject.getValue(ED_DATE_OF_JOING).toString().equalsIgnoreCase("")) {
								if(!validateFutureDate(ED_DATE_OF_JOING,"Date of Joining")) {
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
						boolean check =  isControlEnabled(BTN_CPD_TRSD_CHK);
						if(check) {
							sendMessageValuesList(BTN_CPD_TRSD_CHK,"Please perform TRSD Check again as some of the customer data was changed.");
							return "";
						}			
						boolean crscategory=mandatoryCRSCheckcategorychange();
						if(!crscategory && formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")) {
							return "";
						}

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
								sendMessageList.clear();
								return getReturnMessage(false, controlName, "Selected passport holder Residents does not meet "
										+ "conditions,\nHence not allowed to open Account. Do you still want to proceed with "
										+ "account opening?");
							}
						}
					}
					return afterAppRiskCheck(data);
				} else if("BtnCropSignature".equalsIgnoreCase(controlName) 
						&& "LAPS".equalsIgnoreCase(formObject.getValue(SOURCING_CHANNEL).toString())){
					logInfo(""," pControlName CropSignature "  +controlName);
					String sCust_Name, docindex,sCust_Id;
					docindex = getDocumentIndex("AOF_DOCUMENT_INDEX");
					logInfo("loadDocument","docindex : "+docindex);
					sCust_Name = formObject.getValue(TXT_CUSTOMERNAME).toString();
					sCust_Id = formObject.getValue(TXT_CUSTOMERID).toString();
					return getReturnMessage(true, docindex+"$$"+sCust_Name+"$$"+sCust_Id);
				} else if(controlName.equalsIgnoreCase(BTN_CPD_TRSD_CHK)){
					// Added by reyaz 02-05-2023
					logInfo("CLICK","INSIDE FSK_CHECK data"+data);
					callFSKService("CPD"); 
					if(formObject.getValue("TRSD_FLAG").toString().equalsIgnoreCase("N"))
						formObject.setValue(CPD_FINAL_ELIGIBILITY, "Eligible");
					else{
						formObject.setValue(CPD_FINAL_ELIGIBILITY, "Not Eligible");
					}
					
					insertBankDecisionFromTRSD(formObject.getValue(CPD_TRSD_FINAL_DECISION).toString());
					logInfo("CPD_TRSD_FINAL_DECISION: "+formObject.getValue(CPD_TRSD_FINAL_DECISION).toString(),"");				
					if(formObject.getValue("TRSD_FLAG").toString().equalsIgnoreCase("N") 
							||formObject.getValue("TRSD_FLAG").toString().equalsIgnoreCase("Y")) {							
						formObject.setStyle(BTN_CPD_TRSD_CHK,DISABLE,TRUE);
					}
				} else if(controlName.equalsIgnoreCase(VIEW)){
					//logInfo("CPDMakerThreeStep","Click: inside VIEW");
					formObject.clearTable(LVW_DEDUPE_RESULT);
					int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
					recordList = formObject.getDataFromDB("SELECT CUST_ID,CUST_NAME,CUST_IC,CUST_PASSPORT,CUST_EMAIL,"
							+ "CUST_MOBILE,'','',to_date(CUST_DOB,'"+DATEFORMAT+"'),"
							+ "CUST_EIDA,CUST_NATIONALITY,system_type FROM USR_0_DUPLICATE_SEARCH_DATA "
							+ "WHERE WI_NAME='"+sWorkitemId+"' AND CUST_SNO ='"+iSelectedRow+"'");
					loadListView(recordList,COLUMNS_LVW_DEDUPE_RESULT,LVW_DEDUPE_RESULT);
					String sQuery1 = "select dedupe_selected_index from USR_0_CUST_TXN Where WI_NAME='"
							+ sWorkitemId +"' and cust_sno='"+iSelectedRow+"'";
					logInfo("CPDMakerThreeStep","Click: sQuery1"+sQuery1);
					recordList = formObject.getDataFromDB(sQuery1);
					logInfo("CPDMakerThreeStep","Click: DATAFROMDB"+recordList);
					//					int index1 = Integer.parseInt(recordList.get(0).get(0));
					//					formObject.setNGLVWSelectedRows("ListView5",  intA );	//to ask MOKSH
					if(recordList.size() > 0 && null != recordList.get(0)) {
						return getReturnMessage(true, controlName, LVW_DEDUPE_RESULT+"_"+recordList.get(0).get(0));
						//set the value of checkbox in post server event of this control
						//in js: setFieldValue(jsondata.message, TRUE);
					}
				}else if(controlName.equalsIgnoreCase(CRS_ADD)){  //CRS TAB ADD BUTTON
					//Added new Function for mvn conversion
					validateCRS();
					checkCRS();
				}  else if(controlName.equalsIgnoreCase(BTN_CC_GEN_TEMP) || controlName.equalsIgnoreCase(BUTTON_GENERATE_TEMPLATE)){
					if(!isPaperJourney()){
						generateTemplate();
					} 
					formObject.setStyle("static_next",DISABLE,FALSE);
					formObject.setStyle("static_submit",DISABLE,TRUE);
				} else if(controlName.equalsIgnoreCase(DC_BTN_ADD)){
					if(addDebitCard()){
						return  getReturnMessage(true, DC_BTN_ADD);
					}
				} else if(controlName.equalsIgnoreCase(BTN_VIEWDETAILS_SANCT)) {
					logInfo("BTN_VIEWDETAILS_SANCT","saving data");
					saveScreeningDataCPD();
					//NGRepeater objChkRepeater = formObject.getNGRepeater("REPEAT_FRAME");
					int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
					String sCustNo=formObject.getTableCellValue(ACC_RELATION,iSelectedRow,0);
					int sOutput=updateDataInDB("USR_0_BRMS_TRACKER","SCREENING_STATUS","'Success'","WI_NAME ='"+sWorkitemId+"' AND CUST_SNO='"+sCustNo+"' AND SCREENING_STATUS ='Pending'");
					logInfo("BTN_VIEWDETAILS_SANCT","sOutput----"+sOutput);
				}   else if(controlName.equalsIgnoreCase(BTN_SELECT_CUST)) {
					String iListViewSelectedRow = "0";
					fillDuplicateData(data);
				} else if(controlName.equalsIgnoreCase(BTN_CPD_CNTRL_URL)) {
					// done in js
				}
				else if(controlName.equalsIgnoreCase("COMMAND17")) {
					//done in js
				}  else if(controlName.equalsIgnoreCase("Command60")) {    //ID NOT FOUND
					//logInfo("Command60","data saved into CPD table...");
					saveScreeningDataCPD();
				}  else if(controlName.equalsIgnoreCase("ADD")) {
					//logInfo("ADD","In Command Add");
					if(formObject.getValue(SI_DEB_ACC_NO).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(SI_DEB_ACC_NO,"Please select Debit to Product");
					}
					if(formObject.getValue(SI_CURRENCY).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(SI_CURRENCY,"Please select Currency");
					}
					if(formObject.getValue(SI_CRED_PROD).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(SI_CRED_PROD,"Please select Credit to Product");
					}
					if(formObject.getValue(SI_FRST_PAYMNT).toString().trim().equalsIgnoreCase("")) {
						sendMessageValuesList(SI_FRST_PAYMNT,"Please select First Payment Date");
					} else {
						boolean bReturn = validateSIDate(SI_FRST_PAYMNT,"First Payment");
						if(bReturn == false) {
						}
					} if(formObject.getValue(SI_LST_PAYMNT).toString().trim().equalsIgnoreCase("")) {
						sendMessageValuesList(SI_LST_PAYMNT,"Please select Last Payment Date");
					} else {
						boolean bReturn = validateSIDate(SI_LST_PAYMNT,"Last Payment ");
						if(bReturn == false) {
						}
					} if(!formObject.getValue(SI_LST_PAYMNT).toString().equalsIgnoreCase("") && !formObject.getValue(SI_FRST_PAYMNT).toString().equalsIgnoreCase("")) {
						boolean isFuture=compareDateMMM(formObject.getValue(SI_LST_PAYMNT).toString(),formObject.getValue(SI_FRST_PAYMNT).toString());
						if(!isFuture) {
							sendMessageValuesList("","Last Payment Date Should Be Greater Then First Payment Date.");
						}
					} if(formObject.getValue(SI_PERIOD).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(SI_PERIOD,"Please select Period");
					} if(formObject.getValue(SI_TRNS_AMT).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(SI_TRNS_AMT,"Please fill Transfer Amount");
					}
				} else if(controlName.equalsIgnoreCase("MODIFY")) {
					//logInfo("MODIFY","In Command MODIFY");
					if(formObject.getValue(SI_DEB_ACC_NO).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(SI_DEB_ACC_NO,"Please select Debit to Product");
					} 
					if(formObject.getValue(SI_CURRENCY).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(SI_CURRENCY,"Please select Currency");
					}
					if(formObject.getValue(SI_CRED_PROD).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(SI_CRED_PROD,"Please select Credit to Product");
					}
					if(formObject.getValue(SI_FRST_PAYMNT).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(SI_FRST_PAYMNT,"Please select First Payment ");
					} else {
						boolean bReturn = validateSIDate(SI_FRST_PAYMNT,"First Payment Date");
						if(bReturn == false) {
						}
					} if(formObject.getValue(SI_LST_PAYMNT).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(SI_LST_PAYMNT,"Please select Last Payment Date");
					} if(!formObject.getValue(SI_LST_PAYMNT).toString().equalsIgnoreCase("") && !formObject.getValue(SI_FRST_PAYMNT).toString().equalsIgnoreCase("")) {
						boolean isFuture=compareDateMMM(formObject.getValue(SI_LST_PAYMNT).toString(),formObject.getValue(SI_FRST_PAYMNT).toString());
						if(!isFuture) {
							sendMessageValuesList("","Last Payment Date Should Be Greater Then First Payment Date.");
						}
					} if(formObject.getValue(SI_PERIOD).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(SI_PERIOD,"Please select Period");
					} if(formObject.getValue(SI_TRNS_AMT).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(SI_TRNS_AMT,"Please fill Transfer Amount");
					}
				} else if(controlName.equalsIgnoreCase(BTN_CPD_ADD)) {
					boolean result =validateCentralBankDataCPD(CPD_CNTRL_BNK_BAD_LVW);
					logInfo("BTN_CPD_ADD","ADD_CRO result---"+result);
					if(!result) {
					} else {
					}
				} else if(controlName.equalsIgnoreCase(BTN_CPD_MODIFY) ) {
					boolean result =validateCentralBankDataCPD(CPD_CNTRL_BNK_BAD_LVW);
					logInfo("BTN_CPD_MODIFY","MODIFY_CRO result---"+result);
					if(!result) {
					} else {
					}
				}  else if(controlName.equalsIgnoreCase(CT_BTN_EIDA_REFRESH)) {
					String sResult ="";
					xmlDataParser = new XMLParser();
					xmlDataParser.setInputXML(sResult);
					String valEIDA[] = xmlDataParser.getValueOf("Val").split("#");
					HashMap<String,String> sListView = fetchValFromProp();							
					try {
						for(String skey: valEIDA) {
							try {
								String sTemp[] = skey.split("=");
								String sFormField = sListView.get(sTemp[1]);
								if(sFormField.indexOf(EIDA_NATIONALITY)!= -1) {
									List<List<String>> sOutput =formObject.getDataFromDB("SELECT COUNTRY FROM USR_0_COUNTRY_MAST WHERE COUNTRY_CODE = (SELECT COUNTRY_CODE FROM USR_0_EIDA_COUNTRY WHERE EIDA_CODE ='"+sTemp[0]+"')");
									if(sOutput!=null && sOutput.size()>0){
										formObject.setValue(sFormField, sOutput.get(0).get(0));
									}
								} else {
									if(sTemp[0].indexOf("+")!=-1) {
										sTemp[0]=sTemp[0].substring(1,sTemp[0].length());
										formObject.setValue(sFormField, sTemp[0]);
									} else {
										formObject.setValue(sFormField, sTemp[0]);
									}
								}
							} catch (Exception e)  {
								logError("Exception in CT_BTN_EIDA_REFRESH",e);
							}
						}
					} catch(Exception e) {
					//	logError("Exception in CT_BTN_EIDA_REFRESH",e);
					} finally {
						sListView.clear();
					}
				} else if(controlName.equalsIgnoreCase(BTN_EIDA_INFO)) {
					//Added by Shivanshu convert to mvn
					validateEIDAinfo();
				}
				//end of code by NAGASHARAN

				//Start Of Kishan Code 
				else if(controlName.equalsIgnoreCase(BTN_ECD_VALIDATE) ){
					String sEtihadNo  = formObject.getValue(ETIHAD_NO).toString();
					String sEtihadExist  = formObject.getValue(EXISTING_ETIHAD_CUST).toString();
					if(sEtihadExist.equalsIgnoreCase("")){
						sendMessageValuesList(EXISTING_ETIHAD_CUST,"Please select Existing Etihad Customer");			
					}
					if(sEtihadNo.equalsIgnoreCase("")){
						sendMessageValuesList(ETIHAD_NO,"Please fill Etihad Guest Number");
					}				
					String sCustomerID  = getPrimaryCustomerID();
					String sOutput      = fetchEtihadDetails(sCustomerID,sEtihadNo);
					String sReturnCode  = getTagValues(sOutput,"ResponseCode");
					String sReturnValue = getTagValues(sOutput,"ResponseValue");
					if(sReturnCode.equalsIgnoreCase("00")){
						sendMessageValuesList("","Etihad Number Validated Successfully.");
						//iEtihadValidate=1;
						formObject.setValue(IS_ETIHAD,"1");		
					}else if(sReturnCode.equalsIgnoreCase("01")){
						if(!sCustomerID.equalsIgnoreCase("") && sReturnValue.equalsIgnoreCase(sCustomerID)){
							sendMessageValuesList("","Etihad Number Validated Successfully.");
							//iEtihadValidate=2;
							formObject.setValue(IS_ETIHAD,"2");
						}	
						else{
							sendMessageValuesList(ETIHAD_NO,"Etihad Number is already associated with customer ID "+sReturnValue+"\n"+"Please enter a new Etihad Number");
							formObject.setValue(ETIHAD_NO,"");
						}
					}else{
						sendMessageValuesList(ETIHAD_NO,"Etihad Number is not valid. Please enter a new Etihad Number");
						formObject.setValue(ETIHAD_NO,"");
					}
				} else if(controlName.equalsIgnoreCase("COMMAND42") ){					
					deleteSelectedProduct();  //need to be checked
					EnableFamilyReffered();
				} else if(controlName.equalsIgnoreCase("ADD_PRODUCT")){// no need-Fardeen
					/*logInfo("","In Add Product");
						//NGRepeater objChkRepeater = formObject.getNGRepeater("ACC_REPEATER");
						logInfo("","In Add Product after repeater");
						objChkRepeater.addRow();*///  need to be checked
				} else if(controlName.equalsIgnoreCase(REMOVE_PRODUCT)){
					if(removeProducts(Integer.parseInt(data))){
						return getReturnMessage(true, controlName);
					}
				} else if(controlName.equalsIgnoreCase(BTN_NEXT_CUST)) {
					boolean result=false;
					result = mandatoryCustScreenCPD();
					if(!result) {
						return ""; //return false?
					}
					int iSelectedRow = Integer.parseInt((String) formObject.getValue(SELECTED_ROW_INDEX));
					String sCustNo=formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 0);
					String sQuery = "update USR_0_BRMS_TRACKER set SCREENING_STATUS = 'Success' where WI_NAME="
							+ "'"+sWorkitemId+"' AND CUST_SNO='"+sCustNo+"'  AND SCREENING_STATUS ='Pending'";
					int sOut= formObject.saveDataInDB(sQuery);
					logInfo("","sQuery: "+sQuery+", sOut: "+sOut);
					saveScreeningDataCPD();		
					if(!processedCustomerList.contains(iSelectedRow)) {
						processedCustomerList.add(iSelectedRow+"");
					}
					Collections.sort(processedCustomerList);
					boolean bSnoSet = false;
					for(int i = 0;i< processedCustomerList.size();i++)  {
						if(!processedCustomerList.get(i).equalsIgnoreCase(i+1+"")) {
							bSnoSet = true;
							//objChkRepeater.setFocus(i+1, "AO_ACC_RELATION.SNO"); ?
							loadCustDataOnRepeaterSelect(Integer.parseInt((String) formObject.getValue(SELECTED_ROW_INDEX))+"");
							break;
						}
					}
					if(bSnoSet == false) {
						/*objChkRepeater.setFocus(Integer.parseInt(processedCustomerList.get
						 * (processedCustomerList.size()-1))+1, "AO_ACC_RELATION.SNO");*/ // ?
						loadCustDataOnRepeaterSelect(Integer.parseInt((String) formObject.getValue(SELECTED_ROW_INDEX))+"");
					}
					//formObject.setNGSelectedTab("Tab5", 1); ?
				}   else if(controlName.equalsIgnoreCase(BTN_VALIDATE)){
					ValidateFATCADetails("Mini");
					formObject.setStyle(COMBODOC, DISABLE, FALSE);
					isFactaCompleteValiadate = true;
				} else if(controlName.equalsIgnoreCase(BTN_VALIDATEFATCA)){
					ValidateFATCADetails("Main");
					isFactaCompleteValiadate = true;
				}  
				//END OF KISHAN CODE

				//Code by Rakshita
				else if(controlName.equalsIgnoreCase(BTN_CUSTOMER_SEARCH)) 	{
					int iProcessedCustomer=Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
					String sCustID= formObject.getTableCellValue(ACC_RELATION,iProcessedCustomer+1,2);//cid
					logInfo("","sCustID----"+sCustID +"iProcessedCustomer----"+iProcessedCustomer);
					if(sCustID.equalsIgnoreCase("")) {
						sendMessageValuesList("", "Customer ID not present for this customer");
						getReturnMessage(false);
					}
					setFCRValueonLoad(sCustID);
				} else if(controlName.equalsIgnoreCase("NEXT_0")){
					if(formObject.getValue(OPERATING_INST).toString().equalsIgnoreCase("")){
						sendMessageValuesList(OPERATING_INST, "Please fill Operating Instructions.");
						getReturnMessage(false);
					}
				} else if(controlName.equalsIgnoreCase(BTN_CPD_RE_CHK)){
					loadBlackListDataCPD();
				}  else if(controlName.equalsIgnoreCase(DEL_BRNCH_COURIER)){
					toggleCheckbox_2(controlName,DEL_EXCELLENCY_CNTR);
				} else if(controlName.equalsIgnoreCase(CT_BTN_RESETALL)){
					if(!sActivityName.equalsIgnoreCase(ACTIVITY_ACCOUNT_RELATION)) {
						setManualFieldsBlank();
					}
				} else if(controlName.equalsIgnoreCase(BTN_DEDUPE_SEARCH)){
					if(formObject.getValue(MANUAL_NAME).toString().equalsIgnoreCase("") && 
							formObject.getValue(EIDA_NAME).toString().equalsIgnoreCase("") &&
							formObject.getValue(FCR_NAME).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(MANUAL_NAME, CA011);
					}
					saveComparisonData();
					int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
					formObject.clearTable(LVW_DEDUPE_RESULT);
					checkDuplicate(iSelectedRow);
					String sUpdateDedupe="update USR_0_CUST_TXN set IS_DEDUPE_CLICK_CPD='true' Where WI_NAME='"+
							sWorkitemId +"' and cust_sno='"+iSelectedRow+"'";
					logInfo("",sUpdateDedupe+"   sUpdateDecision");
					formObject.saveDataInDB(sUpdateDedupe);
				} else if (controlName.equalsIgnoreCase(BTNPROFESSION)){
					String sQuery = "Select to_char(PROFESSION_DESC) from USR_0_PROFESSION ORDER BY UPPER(PROFESSION_DESC)";
					//getPopUpWindowDataSearchEnable(sQuery,controlName,"PROFESSION_DESC",1);//Rakshita to be made
				} else if (controlName.equalsIgnoreCase(BTNEMLOYERNAME)){
					String sQuery = "SELECT TO_CHAR(EMP_NAME) FROM USR_0_EMPLOYER_MASTER ORDER BY UPPER(EMP_NAME)";
					//getPopUpWindowDataSearchEnable(sQuery,controlName,"EMP_NAME",1);//Rakshita to be made
				} else if(controlName.equalsIgnoreCase(BTN_CPD_RE_CHK)){
					formObject.setValue(CPD_FINAL_ELIGIBILITY, "--Select--");//Rakshita
					formObject.setValue(SANC_SYS_DEC, "--Select--");
				} else if(controlName.equalsIgnoreCase(BTN_DOCUMENT_LINK)){
					saveScreeningDataCPD();
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
					String sQuery ="SELECT CUST_FULL_NAME, CUST_DOB FROM USR_0_CUST_MASTER WHERE CUST_ID='"+sCustomerID+"'";
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
						validate=true;
						return getReturnMessage(false,"",BTN_FCR_SRCH+"###"+"Customer validated successfully");
					} else if(data.equalsIgnoreCase("NO_OPTION")){
						formObject.setValue(IDS_REF_BY_CUST,"");
						return getReturnMessage(false,"",BTN_FCR_SRCH+"###"+ "Please enter a new customer id");
					}	
				} else if(BTN_FG_VALIDATE.equalsIgnoreCase(controlName)) { 	// Changes for Family Banking
					if(validateFBFetch()){
						familyBankingCalls();
					}
				}
				//END of RAKSHITA CODE

			}
			//END OF CLICK EVENT
			//START OF CHANGE EVENT
			//code by MOKSH
			else if (eventType.equalsIgnoreCase(EVENT_TYPE_CHANGE)){
				setEIDA();
				setAutoFilledFieldsLocked();
				logInfo("EVENT_TYPE_CHANGE", "control name: "+controlName);
				if(controlName.equalsIgnoreCase("table103_trnsfr_from_acc_no")) {
//					int iRows = getGridCount(PRODUCT_QUEUE);				
//					int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()); 
					String sMode = formObject.getValue("table103_mode_of_funding").toString();
					if(!sMode.equalsIgnoreCase("")) {
						String sAccNo = formObject.getValue("table103_trnsfr_from_acc_no").toString();
						String sQuery= "SELECT DISTINCT ACC_BALANCE,CURRENCY FROM USR_0_PRODUCT_EXISTING WHERE "
								+ "WI_NAME ='"+sWorkitemId+"' AND ACC_NO = '"+sAccNo+"' ";
						List<List<String>> sOutput=formObject.getDataFromDB(sQuery);
						if(sOutput.size()>0) {
							String sAccBalance = sOutput.get(0).get(0);
							String sCurrency = sOutput.get(0).get(1);
							logInfo("PRODUCT_QUEUE.TRNSFR_FROM_ACC_NO","sAccBalance: "+sAccBalance+", sCurrency: "
									+sCurrency + "sOutput: "+sOutput);
							formObject.setValue("table103_from_acc_bal",sAccBalance);
							formObject.setValue("table103_trnsfr_from_currency",sCurrency);
						}
					} else {
						sendMessageValuesList(PRODUCT_QUEUE,"Please Select Mode of Transfer first");
					}
//					if(iRows>0) {}
				} else if("table103_mode_of_funding".equalsIgnoreCase(controlName)) {
					manageFundTransfer();
				}else if(controlName.equalsIgnoreCase(ADDITIONAL_SOURCES_INCOME_AED)){
					additionalSource();
		    	}else if(controlName.equalsIgnoreCase("LISTVIEW_PUR_ACC_RELATION")){
					accountPurpose();
				} else if("confirmAppRisk".equalsIgnoreCase(controlName)) {
					formObject.setValue(NIG_CPDMAKER,"YES");
					String updatequery = "update USR_0_CUST_TXN set NIGEXCEPTIONCPDMAKER='YES' "
							+ "Where WI_NAME='"+sWorkitemId+"'";
					formObject.saveDataInDB(updatequery);
				} else if(controlName.equalsIgnoreCase(DFC_STATIONERY_AVAIL)) {
					dfAvail();					
				} else if(controlName.equalsIgnoreCase(CPD_RISK_CURRENT_RSK_BANK)){
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
						}else if(formObject.getValue(CPD__RISK_CURRENT_RSK_SYSTEM).toString().equalsIgnoreCase("Medium Risk")){		//changed27022023
							if(formObject.getValue(CPD_RISK_CURRENT_RSK_BANK).toString().equalsIgnoreCase("")){
								sendMessageValuesList(CPD_RISK_CURRENT_RSK_BANK,CA0144);
								formObject.setValue(CPD_RISK_CURRENT_RSK_BANK,"");
								//return ;
							}
						}else if(formObject.getValue(CPD__RISK_CURRENT_RSK_SYSTEM).toString().equalsIgnoreCase("Increased Risk")){
							if((formObject.getValue(CPD_RISK_CURRENT_RSK_BANK).toString().equalsIgnoreCase("Neutral"))||
									(formObject.getValue(CPD_RISK_CURRENT_RSK_BANK).toString().equalsIgnoreCase("Medium Risk")))			//changed27022023
							{
								sendMessageValuesList(CPD_RISK_CURRENT_RSK_BANK,CA0144);
								formObject.setValue(CPD_RISK_CURRENT_RSK_BANK,"");
								//return ;
							}
						}else if(formObject.getValue(CPD__RISK_CURRENT_RSK_SYSTEM).toString().equalsIgnoreCase("UAE-PEP")){
							if((formObject.getValue(CPD_RISK_CURRENT_RSK_BANK).toString().equalsIgnoreCase("Neutral"))||
									(formObject.getValue(CPD_RISK_CURRENT_RSK_BANK).toString().equalsIgnoreCase("Medium Risk"))||			//changed27022023
									(formObject.getValue(CPD_RISK_CURRENT_RSK_BANK).toString().equalsIgnoreCase("Increased Risk"))){
								sendMessageValuesList(CPD_RISK_CURRENT_RSK_BANK,CA0144);
								formObject.setValue(CPD_RISK_CURRENT_RSK_BANK,"");
								//return ;
							}
						}else if(formObject.getValue(CPD__RISK_CURRENT_RSK_SYSTEM).toString().equalsIgnoreCase("Unacceptable Risk")){
							if((formObject.getValue(CPD_RISK_CURRENT_RSK_BANK).toString().equalsIgnoreCase("Neutral"))||
									(formObject.getValue(CPD_RISK_CURRENT_RSK_BANK).toString().equalsIgnoreCase("Medium Risk"))||			//changed27022023
									(formObject.getValue(CPD_RISK_CURRENT_RSK_BANK).toString().equalsIgnoreCase("Increased Risk"))||
									(formObject.getValue(CPD_RISK_CURRENT_RSK_BANK).toString().equalsIgnoreCase("UAE-PEP")))
							{
								sendMessageValuesList(CPD_RISK_CURRENT_RSK_BANK,CA0144);
								formObject.setValue(CPD_RISK_CURRENT_RSK_BANK,"");					
								//return ;
							}
						}
						int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1; 
						String sPrevRisk ="";
						String sPrevRiskDate ="";
						String sComplainceApproval ="";
						String sCustID = formObject.getValue(CPD_RISK_CID).toString();
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
						}else if(formObject.getValue(controlName).toString().equalsIgnoreCase("Medium Risk")){     //changed 27022023
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
				} else if (RD_INST_DEL.equalsIgnoreCase(controlName)){
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
				}else if (NOM_REQ.equalsIgnoreCase(controlName)){
					String val = formObject.getValue(NOM_REQ).toString();
						if("Yes".equalsIgnoreCase(val))	{			
							formObject.setStyle(EXISTING_NOM_PRSN,DISABLE,FALSE);
							formObject.setValue(EXISTING_NOM_PRSN,"");				
							Frame48_CPD_ENable();
							manageNomineeDetails(val);
						} else {
							formObject.setStyle(EXISTING_NOM_PRSN,DISABLE,TRUE);
							formObject.setValue(EXISTING_NOM_PRSN,"");				
							Frame48_CPD_Disable();
							manageNomineeDetails(val);
						}					
				} else if(EXISTING_NOM_PRSN.equalsIgnoreCase(controlName)) {
					String val = formObject.getValue(EXISTING_NOM_PRSN).toString();
					if("Yes".equalsIgnoreCase(val))	{
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
					} else {
						formObject.clearTable(DELIVERY_PREFERENCE);
					}
				} else if("rowSelect".equalsIgnoreCase(controlName)) {
					loadCustDataOnRepeaterSelect(formObject.getValue(SELECTED_ROW_INDEX).toString());
				} else if(controlName.equalsIgnoreCase(CHECKBOX_FATCA)) { 
					if(formObject.getValue(CHECKBOX_FATCA).toString().equalsIgnoreCase(TRUE)) {
						enableFATCACPD();
						enableControls(new String[] {COMBODOC, DATEPICKERCUST});
					} else {
						frameFatcaCpdDisable();
						disableControls(new String[] {COMBODOC, DATEPICKERCUST});
					}
					formObject.setStyle(CHECKBOX_FATCA, DISABLE, FALSE);
				}  else if(controlName.equalsIgnoreCase(EMPNAME) 
						&& !formObject.getValue(controlName).toString().equalsIgnoreCase("")) {
					List<List<String>> sOutput = formObject.getDataFromDB("SELECT CD_STATUS FROM USR_0_EMPLOYER_MASTER WHERE EMP_NAME ='"+formObject.getValue(controlName)+"'");
					logInfo("","Emp Mast Output---"+sOutput);
					formObject.setValue(ED_CB_TML,"False");
					formObject.setValue(ED_CB_NON_TML,"False");
					if(sOutput.get(0).get(0).equalsIgnoreCase("1") ||sOutput.get(0).get(0).equalsIgnoreCase("2")) {
						formObject.setValue(ED_CB_TML,"True");
					} else {
						formObject.setValue(ED_CB_NON_TML,"True");
					}
					///added by Sourav on 6June for EmployerInfo
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
					} else {
						formObject.setValue(EMP_STREET,"");
						formObject.setValue(EMP_PO_BOX,"");
						formObject.setValue(EMP_CITY,"");
						formObject.setValue(EMP_STATE,"");
						formObject.setValue(EMP_COUNTRY,"");
					}
				} else if(controlName.equalsIgnoreCase(COMBODOC)){
					if(controlValue.equalsIgnoreCase("W9")){
						formObject.setStyle(FAT_SSN,DISABLE,FALSE);
						formObject.setValue(DATEPICKERW8,"");
						formObject.setStyle(DATEPICKERW8,DISABLE,TRUE);
					} else if(controlValue.equalsIgnoreCase("W8BEN")){
						Calendar calendar = Calendar.getInstance();
						SimpleDateFormat simpledateformat = new SimpleDateFormat(DATEFORMAT);
						String scurrentDate = simpledateformat.format(calendar.getTime());
						logInfo("COMBODOC","DATEPICKERW8:"+scurrentDate);
						formObject.setValue(FAT_SSN,"");
						formObject.setStyle(FAT_SSN,DISABLE,TRUE);
						formObject.setStyle(DATEPICKERW8,DISABLE,FALSE);
						formObject.setValue(DATEPICKERW8, scurrentDate);
						formObject.setValue(FAT_CUST_CLASSIFICATION,"NON-US PERSON");
					} else{
						formObject.setValue(FAT_SSN,"");
						formObject.setValue(DATEPICKERW8,"");
						formObject.setStyle(FAT_SSN,DISABLE,TRUE);
						formObject.setStyle(DATEPICKERW8,DISABLE,TRUE);
					}
					String sFinalCountryOfBirth =getFinalDataComparison(CHECKBOX_COB_FCR,CHECKBOX_COB_EIDA,CHECKBOX_COB_MANUAL,
							FCR_COUNTRYBIRTH,EIDA_COUNTRYBIRTH,MANUAL_COUNTRYBIRTH); 
					logInfo("",sFinalCountryOfBirth+"combodoc sFinalCountryOfBirth");
					if(sFinalCountryOfBirth.equalsIgnoreCase("UNITED STATES")){
						List<List<String>> sOutput=formObject.getDataFromDB("SELECT FATCA_CLASSIFICATION FROM USR_0_DOC_MASTER"
								+ " WHERE DOC_NAME ='"+controlValue+"'");
						logInfo("","sOuput----"+sOutput);
						formObject.setValue(FAT_CUST_CLASSIFICATION,sOutput.get(0).get(0));
					}else{
						if(formObject.getValue(INDICIACOMBO).toString().equalsIgnoreCase("No")){
							formObject.setValue(FAT_CUST_CLASSIFICATION,"NON-US PERSON");
						}else if (formObject.getValue(INDICIACOMBO).toString().equalsIgnoreCase("Yes")){
							formObject.setValue(FAT_CUST_CLASSIFICATION,"US PERSON");
						}
					}
				} else if(controlName.equalsIgnoreCase(MANUAL_VISASTATUS)){//visa status manual
					logInfo("CPDMakerThreeStep","Click: INSIDE visaStatus_manual CHANGE");
					if( returnVisaStatus().equalsIgnoreCase("Residency Visa") ){
						formObject.setStyle(DRP_RESEIDA,DISABLE,FALSE);
					}else{
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
				} else if(controlName.equalsIgnoreCase(MANUAL_PER_CNTRY)){
					logInfo("CPDMakerThreeStep","Click: INSIDE MANUAL_PER_CNTRY");
					formObject.setValue(PA_OTHERS,"");
					formObject.setValue(OTHER_RESIDENTIAL_CITY,"");
				} else if(controlName.equalsIgnoreCase("fetch_balance")){
					logInfo("CPDMakerThreeStep","Click: INSIDE fetch_balance");
					getRealTimeDetails();
				} else if(controlName.equalsIgnoreCase(DOC_APPROVAL_OBTAINED)){
					logInfo("CPDMakerThreeStep","Click: INSIDE DOC_APPROVAL_OBTAINED");
					if("true".equalsIgnoreCase(formObject.getValue(DOC_APPROVAL_OBTAINED).toString())){
						logInfo("CPDMakerThreeStep","Click: INSIDE DOC_APPROVAL_OBTAINED true");
						formObject.setValue(COURT_ORD_TRADE_LIC,"false");
					}else{
						logInfo("CPDMakerThreeStep","Click: INSIDE DOC_APPROVAL_OBTAINED false");
						formObject.setValue(COURT_ORD_TRADE_LIC,"true");
					}
				} else if(controlName.equalsIgnoreCase(COURT_ORD_TRADE_LIC)){
					logInfo("CPDMakerThreeStep","Click: INSIDE COURT_ORD_TRADE_LIC");
					if("true".equalsIgnoreCase(formObject.getValue(COURT_ORD_TRADE_LIC).toString())){
						logInfo("CPDMakerThreeStep","Click: INSIDE COURT_ORD_TRADE_LIC true");
						formObject.setValue(DOC_APPROVAL_OBTAINED,"false");
					}
					else{
						logInfo("CPDMakerThreeStep","Click: INSIDE COURT_ORD_TRADE_LIC false");
						formObject.setValue(DOC_APPROVAL_OBTAINED,"true");
					}
				} else if(controlName.equalsIgnoreCase(RM_CODE)){
					logInfo("CPDMakerThreeStep","Click: INSIDE Profit Center");
					//setProfitCode();  //blank method in old code
				} else if(controlName.equalsIgnoreCase(MANUAL_PREFIX)){
					logInfo("CPDMakerThreeStep","Click: INSIDE MANUAL_PREFIX");
					setGender();
					formObject.setValue(CUST_GENDER, formObject.getValue(MANUAL_GENDER).toString());
				} else if(controlName.equalsIgnoreCase(PD_CUSTSEGMENT)){
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
//								formObject.setStyle("Label128",VISIBLE,TRUE);	//check lables moksh
//								formObject.setStyle("Label134",VISIBLE,TRUE);
//								formObject.setStyle("Label133",VISIBLE,TRUE);
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
					logInfo("CPDMakerThreeStep","bfr calling setRMCode");
					setRMCode();
				} else if(controlName.equalsIgnoreCase(DRP_RESEIDA)){
					logInfo("CPDMakerThreeStep","Click: INSIDE EIDA");
					setEIDA();
				} else if(controlName.equalsIgnoreCase(CHECKBOX_SHORTNAME_MANUAL)){
					logInfo("CPDMakerThreeStep","Click: INSIDE CHECKBOX_SHORTNAME_MANUAL");
					shortnamefunctionality();
				} else if(controlName.equalsIgnoreCase(CHECKBOX_FULLNAME_FCR)){
					logInfo("CPDMakerThreeStep","Click: INSIDE CHECKBOX_FULLNAME_FCR");
					if(formObject.getValue(CHECKBOX_FULLNAME_FCR).toString().equalsIgnoreCase("True")
							&&formObject.getValue(CHECKBOX_SHORTNAME_MANUAL).toString().equalsIgnoreCase("True")){
						shortnamefunctionality();
					} else{
						formObject.setValue(MANUAL_SHORTNAME,"");
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_FULLNAME_EIDA)){
					logInfo("CPDMakerThreeStep","Click: INSIDE CHECKBOX_FULLNAME_EIDA");
					if(formObject.getValue(CHECKBOX_FULLNAME_EIDA).toString().equalsIgnoreCase("True")
							&&formObject.getValue(CHECKBOX_SHORTNAME_MANUAL).toString().equalsIgnoreCase("True")){
						shortnamefunctionality();
					} else{
						formObject.setValue(MANUAL_SHORTNAME,"");
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_SHORTNAME_MANUAL)){
					logInfo("CPDMakerThreeStep","Click: INSIDE CHECKBOX_SHORTNAME_MANUAL");
					if(formObject.getValue(CHECKBOX_SHORTNAME_MANUAL).toString().equalsIgnoreCase("False")){
						formObject.setValue(MANUAL_SHORTNAME,"");
					}
				} else if(controlName.equalsIgnoreCase(UDF_Field)){
					logInfo("CPDMakerThreeStep","Click: INSIDE UDF_Field");
					if(formObject.getValue(UDF_Field).toString().equalsIgnoreCase("Graduation Date")){
						formObject.setValue(UDF_Value,"DD/MM/YYYY");
					} else{
						formObject.setValue(UDF_Value,"");
					}
				} else if(controlName.equalsIgnoreCase(UDF_Value)){
					logInfo("CPDMakerThreeStep","Click: INSIDE UDF_Value");
					formObject.setValue(UDF_Value,"");
				}  else if(controlName.equalsIgnoreCase("table91_UDF Field")){
					logInfo("CPDMakerThreeStep","Click: INSIDE table91_UDF Field");
					if (formObject.getValue("table91_UDF Field").toString().equalsIgnoreCase("Graduation Date")){
						formObject.clearCombo("table91_UDF Value");
					}
					else if (formObject.getValue("table91_UDF Field").toString().equalsIgnoreCase("Special Customer Identifier")){
						formObject.clearCombo("table91_UDF Value");
						loadCombo("SELECT LOV FROM USR_0_UDF_VALUES_LOV WHERE IS_VALID = 'YES'",
								"table91_UDF Value");					
						}

				} 
				
				/*else if(controlName.equalsIgnoreCase("udf_addRow")){
					logInfo("CPDMakerThreeStep","Click: INSIDE UDF_Value");
					//ACCOUNT INFO SAVE AND NECT BUTTON
					logInfo("AccInfo_UdfList","Inside udf_addRow");
					logInfo("AccInfo_UdfList","ACC_INFO_UDF_FIELD Value ::"
							+ formObject.getValue(ACC_INFO_UDF_FIELD).toString());
					if (formObject.getValue(ACC_INFO_UDF_FIELD).toString().equalsIgnoreCase("")
							|| null == formObject.getValue(ACC_INFO_UDF_FIELD)) {
						sendMessageValuesList(ACC_INFO_UDF_FIELD,"Select a UDF field first.");
					} else {
						if (UdfUniquenessCheck(formObject.getValue(ACC_INFO_UDF_FIELD).toString())) {
							logInfo("AccInfo_UdfList","Inside udf_addRow 1");
							if (formObject.getValue(ACC_INFO_UDF_FIELD).toString().equalsIgnoreCase("Graduation Date")) {
								logInfo("AccInfo_UdfList","Inside udf_addRow 2");
								if (validateJavaDate(formObject.getValue(ACC_INFO_UDF_VALUE).toString())) {
									logInfo("AccInfo_UdfList","Inside udf_addRo 3");
									String colnames = "UDF_FIELD,UDF_VALUE";
									String values = formObject.getValue(ACC_INFO_UDF_FIELD)+ "##"+ 
											formObject.getValue(ACC_INFO_UDF_VALUE);
									//LoadListViewWithHardCodeValues("ACCINFO_UDF_LIST", colnames, values);
									formObject.setValue(ACC_INFO_UDF_VALUE, "");
								} else {
									sendMessageValuesList(ACC_INFO_UDF_VALUE,"Date entered by you is not valid");
								}
							} else {
								logInfo("UDF_ADDROW","Inside UDF_ADDROW 4");
								String colnames = "UDF_FIELD,UDF_VALUE";
								String values = formObject.getValue(ACC_INFO_UDF_FIELD).toString()+ "##" 
										+ formObject.getValue(ACC_INFO_UDF_VALUE).toString();
								//LoadListViewWithHardCodeValues("ACCINFO_UDF_LIST", colnames, values);
								formObject.setValue(ACC_INFO_UDF_VALUE, "");
							}
						} else {
							sendMessageValuesList(ACC_INFO_UDF_FIELD,
									formObject.getValue(ACC_INFO_UDF_FIELD).toString()+ " field already exist");
						}
					}
				}*/ else if(controlName.equalsIgnoreCase("udf_delRow")){
					logInfo("CPDMakerThreeStep","Click: INSIDE udf_delRow");
					int gridIndex = getGridCount("AccInfo_UdfList");
					logInfo("","---------CRS GRID INDEX----------: "+gridIndex);  //kdd
					if(gridIndex>=0) {
						//formObject.NGRemoveItemAt("AccInfo_UdfList",getListIndex("AccInfo_UdfList"));	 //kdd
					} else {
						sendMessageValuesList("AccInfo_UdfList","Please select a row first");
					}
				} else if(controlName.equalsIgnoreCase(MANUAL_RESIDENT)){
					logInfo("CPDMakerThreeStep","Click: INSIDE MANUAL_RESIDENT");
					flag_phone_start="true";
					flag_phone=true;
					flag_mobile=true;
					logInfo("CPDMakerThreeStep","Click: MANUAL_RESIDENT" +flag_phone_start+flag_mobile+flag_phone);
					formObject.setValue(RA_OTHERS,"");
					formObject.setValue(OTHER_PERM_CITY,"");
				} else if (controlName.equalsIgnoreCase(MANUAL_STATE)){
					logInfo("CPDMakerThreeStep","Click: INSIDE MANUAL_STATE");
					formObject.setValue(MANUAL_CITY,formObject.getValue(MANUAL_STATE).toString());
					formObject.setValue(CP_CITY, formObject.getValue(MANUAL_STATE).toString());
					formObject.setValue(RA_CITY, formObject.getValue(MANUAL_STATE).toString());
					formObject.setValue(PERM_STATE, formObject.getValue(MANUAL_STATE).toString());
				} else if(controlName.equalsIgnoreCase(RM_CODE)){
					logInfo("CPDMakerThreeStep","Change: INSIDE Profit Center");
					//setProfitCode();  //blank method in old code MOKSH (check line 7425 in new common)
				} else if(controlName.equalsIgnoreCase(CRS_CERTI_YES)){
					logInfo("CPDMakerThreeStep","Change: INSIDE ");

					if("true".equalsIgnoreCase(formObject.getValue("CRS_Certi_no").toString()))
					{
						formObject.setValue("CRS_Certi_no","false");
					}
					if("true".equalsIgnoreCase(formObject.getValue("CRS_Certi_yes").toString()))
					{
						formObject.setValue("CRS_Classification","UPDATED-DOCUMENTED");
					}
				} else if(controlName.equalsIgnoreCase(CRS_CERTI_NO)){
					logInfo("CPDMakerThreeStep","Change: INSIDE");

					if("true".equalsIgnoreCase(formObject.getValue("CRS_Certi_yes").toString()))
					{
						formObject.setValue("CRS_Certi_yes","false");
					}
					if("true".equalsIgnoreCase(formObject.getValue("CRS_Certi_no").toString()))
					{
						formObject.setValue("CRS_Classification","UPDATED-DOCUMENTED");
					}
				} else if(controlName.equalsIgnoreCase(MANUAL_PREFIX)){
					logInfo("CPDMakerThreeStep","Change: INSIDE MANUAL_PREFIX");
					setGender();
				} else if(controlName.equalsIgnoreCase(PD_EIDANO)){
					logInfo("CPDMakerThreeStep","Change: INSIDE EIDA");
					setEIDA();
				}   else if(controlName.equalsIgnoreCase(CHECKBOX_FULLNAME_MANUAL)){
					logInfo("CPDMakerThreeStep","Change: INSIDE CHECKBOX_FULLNAME_MANUAL kdd");
					toggleCheckbox(controlName,CHECKBOX_FULLNAME_FCR,CHECKBOX_FULLNAME_EIDA);
					manageManualFields(CHECKBOX_FULLNAME_MANUAL,MANUAL_NAME);
					if(formObject.getValue(CHECKBOX_FULLNAME_MANUAL ).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(PD_FULLNAME,formObject.getValue(MANUAL_NAME).toString());
					}
					logInfo("CPDMakerThreeStep","Change: INSIDE CHECKBOX_FULLNAME_MANUAL");
					if(formObject.getValue(CHECKBOX_FULLNAME_MANUAL).toString().equalsIgnoreCase("True")
							&&formObject.getValue(CHECKBOX_SHORTNAME_MANUAL).toString().equalsIgnoreCase("True")){
						shortnamefunctionality();
					} else{
						formObject.setValue(MANUAL_SHORTNAME,"");
					}

				} else if(controlName.equalsIgnoreCase(CHECKBOX_FULLNAME_FCR)){
					logInfo("CPDMakerThreeStep","Change: INSIDE CHECKBOX_FULLNAME_FCR");
					if(formObject.getValue(CHECKBOX_FULLNAME_FCR).toString().equalsIgnoreCase("True")
							&&formObject.getValue(CHECKBOX_SHORTNAME_MANUAL).toString().equalsIgnoreCase("True")){
						shortnamefunctionality();
					} else{
						formObject.setValue(MANUAL_SHORTNAME,"");
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_FULLNAME_EIDA)){
					logInfo("CPDMakerThreeStep","Change: INSIDE CHECKBOX_FULLNAME_EIDA");
					if(formObject.getValue(CHECKBOX_FULLNAME_EIDA).toString().equalsIgnoreCase("True")
							&&formObject.getValue(CHECKBOX_SHORTNAME_MANUAL).toString().equalsIgnoreCase("True")){
						shortnamefunctionality();
					} else{
						formObject.setValue(MANUAL_SHORTNAME,"");
					}
				}else if(MANUAL_FIRSTNAME.equalsIgnoreCase(controlName) || 
						MANUAL_LASTNAME.equalsIgnoreCase(controlName)) { 
					formObject.setValue(MANUAL_NAME,formObject.getValue(MANUAL_FIRSTNAME).toString()+" "
							+formObject.getValue(MANUAL_LASTNAME).toString());
					formObject.setValue(CRS_FIRSTNAME,formObject.getValue(MANUAL_FIRSTNAME).toString());
					formObject.setValue(CRS_LASTNAME,formObject.getValue(MANUAL_LASTNAME).toString());
					formObject.setValue(PD_FULLNAME,formObject.getValue(MANUAL_NAME).toString());
					if(TRUE.equalsIgnoreCase(formObject.getValue(CHECKBOX_SHORTNAME_MANUAL).toString())){
						shortnamefunctionality();
					}
				} /*else if(controlName.equalsIgnoreCase(MANUAL_MOBILE)){
					logInfo("CPDMakerThreeStep","Change: INSIDE MANUAL_MOBILE");
					flag_mobile=true;
					mobileChangeFlag = true;
				}*/ 
				else if(controlName.equalsIgnoreCase(MANUAL_PH)){
					logInfo("CPDMakerThreeStep","Change: INSIDE MANUAL_PH");
					flag_phone=true;
				} else if(controlName.equalsIgnoreCase(INDICIACOMBO)||controlName.equalsIgnoreCase(FAT_SSN)||
						controlName.equalsIgnoreCase(FAT_CUST_CLASSIFICATION)||controlName.equalsIgnoreCase(DATEPICKERCUST)||
						controlName.equalsIgnoreCase(DATEPICKERW8)||controlName.equalsIgnoreCase(COMBODOC)){    	   
					formObject.setValue(CHANGE_IN_FATCA_3WAY_INPUTS,"Yes");
					formObject.setValue(FATCAMAIN,"Yes");   
					if(formObject.getValue("CHECK95").toString().equalsIgnoreCase("true"))
					{
						logInfo("CPDMakerThreeStep","Change: CHECK95 TRUE");
						formObject.setStyle(BTN_VALIDATEFATCA,DISABLE,FALSE);
						formObject.setStyle(COMBODOC,DISABLE,FALSE);
					}
					if(controlName.equalsIgnoreCase(COMBODOC) && 
							formObject.getValue(COMBODOC).toString().equalsIgnoreCase("W8BEN"))
					{
						logInfo("CPDMakerThreeStep","Change: Combodoc is W8BEN");
						formObject.setValue(FAT_CUST_CLASSIFICATION,"NON-US PERSON");
					}
				} else if(controlName.equalsIgnoreCase(EMPLYR_NAME_FCR) || controlName.equalsIgnoreCase(EIDA_EMPLYR_NAME) 
						|| controlName.equalsIgnoreCase(MANUAL_EMPLYR_NAME)){
					String sIsFCREmpName = formObject.getValue(CHECKBOX_EMP_NAME_FCR).toString();
					String sIsEIDAEmpName = formObject.getValue(CHECKBOX_EMP_NAME_EIDA).toString();
					String sIsManualEmpName = formObject.getValue(CHECKBOX_EMP_NAME_MANUAL).toString();
					String sFCREmpName = formObject.getValue(EMPLYR_NAME_FCR).toString();
					String sEIDAEmpName = formObject.getValue(EIDA_EMPLYR_NAME).toString();
					String sManualEmpName = formObject.getValue(MANUAL_EMPLYR_NAME).toString();
					String sFinalEmpName = getFinalData(sIsFCREmpName,sIsEIDAEmpName,sIsManualEmpName,
							sFCREmpName,sEIDAEmpName,sManualEmpName);
					logInfo("CPDMakerThreeStep","sFinalEmpName "+sFinalEmpName);
					if(formObject.getValue(EMP_STATUS).toString().equalsIgnoreCase("")){
						if(!sFinalEmpName.equalsIgnoreCase("")){
							logInfo("CPDMakerThreeStep","Change: EMP_STATUS Select");
							formObject.setValue(EMP_STATUS, "Employed");
						}
					}
					List<List<String>> sOutput = formObject.getDataFromDB("SELECT CD_STATUS FROM USR_0_EMPLOYER_MASTER"
							+ " WHERE EMP_NAME ='"+formObject.getValue(controlName)+"'");
					logInfo("","Emp Mast Output---"+sOutput);
					formObject.setValue(ED_CB_TML,"False");
					formObject.setValue(ED_CB_NON_TML,"False");
					if(sOutput != null && sOutput.size()>0){
						if(sOutput.get(0).get(0).equalsIgnoreCase("1") ||sOutput.get(0).get(0).equalsIgnoreCase("2")) {
							formObject.setValue(ED_CB_TML,"True");
						}
						else {
							formObject.setValue(ED_CB_NON_TML,"True");
						}
					}
					logInfo("CPDMakerThreeStep","Inside EMPNAME");
					String EmpName=formObject.getValue(EMPNAME).toString();
					if(!(formObject.getValue(EMPNAME).toString().equalsIgnoreCase("")) 
							&& !(formObject.getValue(EMPNAME).toString().equalsIgnoreCase("OTHERS"))){
						logInfo("","When EmpName is not empty");
						String sqString = "Select CD_ADDRESS1,CD_PO_BOX_NO,CD_CITY,CD_STATE from company_details where"
								+ " CD_NAME like '%"+sFinalEmpName+"%'";
						logInfo("","sqString : "+sqString);
						List<List<String>> sOutput1=formObject.getDataFromDB(sqString);
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
				
				} else if(controlName.equalsIgnoreCase(FCR_RESIDENT) || controlName.equalsIgnoreCase(EIDA_RESIDENT) || 
						controlName.equalsIgnoreCase(MANUAL_RESIDENT) ||controlName.equalsIgnoreCase(FCR_NATIONALITY) ||
						controlName.equalsIgnoreCase(EIDA_NATIONALITY) || controlName.equalsIgnoreCase(MANUAL_NATIONALITY)){					
					logInfo("CPDMakerThreeStep","Change: Setting Visa Status");
					manageResidencyAndVisaStatus(RA_IS_UAE_RESIDENT,"COMBO34");
				} else if(controlName.equalsIgnoreCase(ED_MONTHLY_INCM)) {
					logInfo("CPDMakerThreeStep","Change: Text79 ");
					/*String mnthsalary = formObject.getValue(ED_MONTHLY_INCM).toString();					
					try{
						logInfo("CPDMakerThreeStep","Change: Text79 try");
						if(mnthsalary.equalsIgnoreCase("")){
							formObject.setValue(ED_ANNUAL_INC,"");
							formObject.setValue(ED_MONTHLY_INCM,"");
						}
						long mnthslry = Long.parseLong(mnthsalary);
						long finalsalary = mnthslry*12;
						formObject.setValue(ED_ANNUAL_INC,finalsalary+"");
						formObject.setValue(ED_CB_SAL_AED, "true");
						if(formObject.getValue(ED_CB_SAL_AED).toString().equalsIgnoreCase("True")){
							formObject.setValue(ED_MONTHLY_INCM,finalsalary+"");

						}
					} catch(Exception ex){
						logInfo("CPDMakerThreeStep","Change: Text79 error: "+ex);
					}*/
					String mnthsalary = formObject.getValue(ED_MONTHLY_INCM).toString();	
					try {
						if(mnthsalary.equalsIgnoreCase("")){
							formObject.setValue(ED_ANNUAL_INC,"");
							formObject.setValue(ED_SAL_AED,"");
						}
						long mnthslry = Long.parseLong(mnthsalary);
						long finalsalary = mnthslry*12;
						formObject.setValue(ED_ANNUAL_INC,finalsalary+"");
						formObject.setValue(ED_CB_SAL_AED, "true");
						if(((String) formObject.getValue(ED_CB_SAL_AED)).equalsIgnoreCase("True")){
							formObject.setValue(ED_SAL_AED,finalsalary+"");
						}
					} catch(Exception ex){
						logInfo("executeServerEvent", "Exception in Event- " + eventType + "control name- " +controlName+ ": ");
						logError("executeServerEvent", ex);
					}
				} else if(controlName.equalsIgnoreCase(ED_ANNUAL_INC)) {
					String sAnnualSalary = formObject.getValue(ED_ANNUAL_INC).toString();					
					try {
						int iAnnualSal = Integer.parseInt(sAnnualSalary);
						int finalsalary = iAnnualSal/12;
						formObject.setValue(ED_MONTHLY_INCM,finalsalary+"");
						//formObject.setValue(ED_MONTHLY_INCM,formObject.getValue(ED_ANNUAL_INC).toString());
						formObject.setValue(ED_SAL_AED,formObject.getValue(ED_ANNUAL_INC).toString());
					} catch(Exception ex){
						logError("executeServerEvent", ex);
					}
				}
				//end of code by MOKSH
				//code by NAGASHARAN
				else if(controlName.equalsIgnoreCase(SOURCE_NAME)) {
					String sReqType=formObject.getValue(REQUEST_TYPE).toString();
					if(sReqType.equalsIgnoreCase("New Account with Category Change")
							|| sReqType.equalsIgnoreCase("Category Change Only")) {
						formObject.setValue(SOURCE_NAME_CAT_CHANGE,formObject.getValue(SOURCE_NAME).toString());
					}
				} else if(controlName.equalsIgnoreCase(SOURCE_CODE)) {
					String sReqType=formObject.getValue(REQUEST_TYPE).toString();;
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
				} else if( controlName.equalsIgnoreCase(MAKER_CODE)) {
					String sReqType=formObject.getValue(REQUEST_TYPE).toString();
					if(sReqType.equalsIgnoreCase("New Account with Category Change") 
							|| sReqType.equalsIgnoreCase("Category Change Only")) {
						formObject.setValue(MAKER_CODE_CAT_CHANGE,formObject.getValue(MAKER_CODE).toString());
					}
				}
				//end of code by NAGASHARAN
				//code by Rakshita
				else if(controlName.equalsIgnoreCase(CK_PER_DET) && 
						formObject.getValue(CK_PER_DET).toString().equalsIgnoreCase(TRUE)){
					Frame18_CPD_ENable();
					showCustSegmentPC(); //Jamshed for DCRA Private Client
					formObject.setStyle(CK_PER_DET, DISABLE, FALSE);
					formObject.setStyle(PD_SHORTNAME, DISABLE, FALSE);
					if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only")){
						formObject.setStyle(PD_CUSTSEGMENT, DISABLE, TRUE);
					}					
					int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
					String sAccRelation = formObject.getTableCellValue(ACC_RELATION,iSelectedRow, 9);
					String sBankRelation = formObject.getTableCellValue(ACC_RELATION,iSelectedRow, 7);
					if(sBankRelation.equalsIgnoreCase("Existing")){
						formObject.setStyle(PD_CUSTSEGMENT, DISABLE, TRUE);
					}if(sAccRelation.equalsIgnoreCase("Minor")){
						formObject.setStyle(PD_DATEOFATTAININGMAT, DISABLE, TRUE);
					}else{
						formObject.setStyle(PD_DATEOFATTAININGMAT, DISABLE, TRUE); 
					}if(! returnVisaStatus().equalsIgnoreCase("Residency Visa")){
						formObject.setStyle(DRP_RESEIDA, DISABLE, TRUE);
					}else{
						formObject.setStyle(DRP_RESEIDA, DISABLE, FALSE);
					}			
				} else if(controlName.equalsIgnoreCase(CK_PER_DET) && 
						formObject.getValue(CK_PER_DET).toString().equalsIgnoreCase(FALSE)){
					Frame18_CPD_Disable();
					formObject.setStyle(CK_PER_DET, DISABLE, FALSE);
					formObject.setStyle(DRP_RESEIDA, DISABLE, TRUE);
				} else if(controlName.equalsIgnoreCase(CHK_CONTACT_DET)) {
					if(formObject.getValue(CHK_CONTACT_DET).toString().equalsIgnoreCase(TRUE)){
						Frame20_CPD_ENable();
						formObject.setStyle(CHK_CONTACT_DET, DISABLE, FALSE);
					}else if(formObject.getValue(CHK_CONTACT_DET).toString().equalsIgnoreCase(FALSE)){
						logInfo("","4444");
						Frame20_CPD_Disable();
						formObject.setStyle(CHK_CONTACT_DET, DISABLE, FALSE);
					}
				} else if(controlName.equalsIgnoreCase(CHK_PASSPORT_DET)){
					if(formObject.getValue(CHK_PASSPORT_DET).toString().equalsIgnoreCase(TRUE)){
						Frame21_CPD_ENable();
						formObject.setStyle(CHK_PASSPORT_DET, DISABLE, FALSE);
					}
					else if(formObject.getValue(CHK_PASSPORT_DET).toString().equalsIgnoreCase(FALSE)){
						logInfo("","5555");
						Frame21_CPD_Disable();
						formObject.setStyle(CHK_PASSPORT_DET, DISABLE, FALSE);
					}		
				} else if(controlName.equalsIgnoreCase(CHK_INTERNAL_SEC)){
					if(formObject.getValue(CHK_INTERNAL_SEC).toString().equalsIgnoreCase(TRUE)){
						Frame22_CPD_ENable();
						formObject.setStyle(CHK_INTERNAL_SEC, DISABLE, FALSE);
						formObject.setStyle(IDS_PROF_CENTER_CODE, DISABLE, TRUE);
					}else{
						Frame22_CPD_Disable();
						formObject.setStyle(CHK_INTERNAL_SEC, DISABLE, FALSE);
					}
				} else if(controlName.equalsIgnoreCase(CHK_GEN_INFO)){
					if(formObject.getValue(CHK_GEN_INFO).toString().equalsIgnoreCase(TRUE)){
						Frame28_CPD_ENable();
						formObject.setStyle(CHK_GEN_INFO, DISABLE, FALSE);
						formObject.setStyle(GI_EXST_SINCE, DISABLE, FALSE);
					}else if(formObject.getValue(CHK_GEN_INFO).toString().equalsIgnoreCase(FALSE)){
						Frame28_CPD_Disable();
						formObject.setStyle(CHK_GEN_INFO, DISABLE, FALSE);
					}
				} else if(controlName.equalsIgnoreCase(CHK_EMP_DETAIL)){
					if(formObject.getValue(CHK_EMP_DETAIL).toString().equalsIgnoreCase(TRUE)){
						Frame27_CPD_ENable();
						formObject.setStyle(CHK_EMP_DETAIL, DISABLE, FALSE);
							if(formObject.getValue(PROFESION).toString().equalsIgnoreCase("Others") || formObject.getValue(PROFESION).toString().equalsIgnoreCase("")) {
								formObject.setStyle(ED_OTHER, DISABLE, FALSE);
							} else {
								formObject.setStyle(ED_OTHER, DISABLE, TRUE);
							}
							if(formObject.getValue(EMPNAME).toString().equalsIgnoreCase("Others") || formObject.getValue(EMPNAME).toString().equalsIgnoreCase("")) {
								formObject.setStyle(ED_EMPNAME, DISABLE, TRUE);
							} else {
								formObject.setStyle(ED_EMPNAME, DISABLE, TRUE);
							}
					}else if(formObject.getValue(CHK_EMP_DETAIL).toString().equalsIgnoreCase(FALSE)){
						Frame27_CPD_Disable();
						formObject.setStyle(CHK_EMP_DETAIL, DISABLE, FALSE);
					}	
					
				} else if(controlName.equalsIgnoreCase(CHK_FUNDING_INFO)){
					if( formObject.getValue(CHK_FUNDING_INFO).toString().equalsIgnoreCase(TRUE)){
						Frame30_CPD_ENable();
						formObject.setStyle(CHK_FUNDING_INFO, DISABLE, FALSE);
					}else if(formObject.getValue(CHK_FUNDING_INFO).toString().equalsIgnoreCase(FALSE)){
						Frame30_CPD_Disable();
						formObject.setStyle(CHK_FUNDING_INFO, DISABLE, FALSE);
					}	
				} else if(controlName.equalsIgnoreCase(CHK_RISK_ASS)){
					if(formObject.getValue(CHK_RISK_ASS).toString().equalsIgnoreCase(TRUE)){
						riskAssessmentSectionEnable();
						formObject.setStyle(CHK_RISK_ASS, DISABLE, FALSE);
					}else if(formObject.getValue(CHK_RISK_ASS).toString().equalsIgnoreCase(FALSE)){
						Frame25_CPD_Disable();
						formObject.setStyle(CHK_RISK_ASS, DISABLE, FALSE);
					}
				} else if(controlName.equalsIgnoreCase("CHECK95")){ //Rakshita{
					if(formObject.getValue("CHECK95").toString().equalsIgnoreCase(TRUE)){
						FrameFATCA_CPD_Enable();
						formObject.setStyle("CHECK95", DISABLE, FALSE);
						formObject.setStyle(COMBODOC, DISABLE, FALSE);
					}else if(formObject.getValue("CHECK95").toString().equalsIgnoreCase(FALSE)){
						frameFatcaCpdDisable();
						formObject.setStyle("CHECK95", DISABLE, FALSE);
						formObject.setStyle(COMBODOC, DISABLE, TRUE);
					}	
				} else if(controlName.equalsIgnoreCase(CHK_EDD)){
					if(formObject.getValue(CHK_EDD).toString().equalsIgnoreCase(TRUE)){
						Frame31_CPD_ENable();
						formObject.setStyle(CHK_EDD, DISABLE, FALSE);
					}else if(formObject.getValue(CHK_EDD).toString().equalsIgnoreCase(FALSE)){
						Frame31_CPD_Disable();
						formObject.setStyle(CHK_EDD, DISABLE, FALSE);
					}
				} else if(controlName.equalsIgnoreCase(CHK_BANKING_RELATION)){
					if(formObject.getValue(CHK_BANKING_RELATION).toString().equalsIgnoreCase(TRUE)){
						Frame32_CPD_ENable();
						formObject.setStyle(CHK_BANKING_RELATION, DISABLE, FALSE);
					}else if(formObject.getValue(CHK_BANKING_RELATION).toString().equalsIgnoreCase(FALSE)){
						Frame32_CPD_Disable();
						formObject.setStyle(CHK_BANKING_RELATION, DISABLE, FALSE);
					}
				} else if(controlName.equalsIgnoreCase(ACC_INFO_PG)){
					prodGroupChange();
				} else if(controlName.equalsIgnoreCase(GROUP_TYPE) && 
						! formObject.getValue(GROUP_TYPE).toString().equalsIgnoreCase("")){
					String sCustID = getPrimaryCustomerID();
					String[] sSelectedProduct = formObject.getValue(ACC_INFO_PG).toString().split("_");
					String sGroup = formObject.getValue(GROUP_TYPE).toString();
					int iRows = getGridCount(QUEUE_DC);
					int iCount=0;
					List<List<String>> sOutput;
					String sQuery="";
					String sGroupType="";
					sQuery  = "SELECT COD_PROD_TYPE FROM USR_0_PRODUCT_MASTER WHERE PRODUCT_CODE "
							+ "='"+sSelectedProduct[1]+"' and rownum=1";
					logInfo("sQuery 30032017......",sQuery);
					sOutput=formObject.getDataFromDB(sQuery);
					logInfo("sOutput---",sOutput.toString());
					formObject.setValue(NEW_LINK,"");
					String sCategory="";
					try{
						sCategory = sOutput.get(0).get(0);
						logInfo("sCategory---",sCategory);
					}catch(Exception ex){
						//logInfo("sCategory---"+ex.getStackTrace());
						logError("auditSearchMobileFCUBS", ex);
					}
					if(!sCategory.equalsIgnoreCase("")){
						if(!sCategory.equalsIgnoreCase("I") && 
								formObject.getValue(GROUP_TYPE).toString().equalsIgnoreCase("Islamic")){
							sendMessageValuesList("","Islamic group type is not allowed for conventional product");
							formObject.setValue(GROUP_TYPE,"");
							//							return false ;
						}
					}
					if(sGroup.equalsIgnoreCase("")){
						formObject.setValue(CARD_TYPE,"");
						formObject.setValue(NEW_LINK,"");
						formObject.setStyle(CARD_TYPE, DISABLE, FALSE);
						formObject.setStyle(NEW_LINK, DISABLE, FALSE);
						//						return;
					}
					for(int i=0; i<iRows; i++) {
						sGroupType = formObject.getTableCellValue(QUEUE_DC, i, 1);
						if(sGroupType.equalsIgnoreCase(sGroup)){
							iCount=iCount+1;
							break;
						}
					}
					logInfo("GROUP_TYPE", "iCount: "+iCount+", sCustID: "+sCustID);
					if(sCustID.equalsIgnoreCase("") && iCount == 0){
						formObject.setValue(CARD_TYPE,"Primary");
						formObject.setValue(NEW_LINK,"New");
						formObject.setStyle(CARD_TYPE, DISABLE, TRUE);
						formObject.setStyle(NEW_LINK, DISABLE, TRUE);
					}
					else if(iCount != 0){
						formObject.setValue(CARD_TYPE,"Supplementary");
						formObject.setStyle(CARD_TYPE, DISABLE, TRUE);
						formObject.setStyle(NEW_LINK, DISABLE, FALSE);
					}
					else{
						sQuery = "SELECT COUNT(WI_NAME) as COUNT_WI FROM USR_0_DEBITCARD_EXISTING_CPD WHERE WI_NAME ='"+sWorkitemId+
								"' AND CUST_ID='"+sCustID+"' AND STATUS NOT IN ('2','3','4') AND PRODUCT_GROUP IN (SELECT DISTINCT "
								+ "GROUPID FROM USR_0_CARD_PRODUCT_GROUP WHERE PRODTYPE = '"+sGroup+"')";
						logInfo("GROUP_TYPE", "sQuery: "+sQuery);
						sOutput=formObject.getDataFromDB(sQuery);
						iCount = Integer.parseInt(sOutput.get(0).get(0));
						logInfo("GROUP_TYPE", "sOutput: "+sOutput);
						if(iCount==0){
							formObject.setValue(CARD_TYPE,"Primary");
							formObject.setValue(NEW_LINK,"New");
							formObject.setStyle(CARD_TYPE, DISABLE, TRUE);
							formObject.setStyle(NEW_LINK, DISABLE, TRUE);
						}else{
							formObject.setValue(CARD_TYPE,"Supplementary");
							formObject.setStyle(CARD_TYPE, DISABLE, TRUE);
							formObject.setStyle(NEW_LINK, DISABLE, FALSE);
						}
					}
				} else if(controlName.equalsIgnoreCase(GROUP_TYPE)){
					formObject.setValue(CARD_TYPE,"");
					formObject.setValue(NEW_LINK,"");
					formObject.setStyle(CARD_TYPE,DISABLE,FALSE);
					formObject.setStyle(NEW_LINK,DISABLE,FALSE);
				} else if(controlName.equalsIgnoreCase(NEW_LINK)){

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

				} else if(controlName.equalsIgnoreCase(CHECKBOX_SELECTALL_FCR)){
					toggleCheckbox(controlName,CHECKBOX_SELECTALL_EIDA,CHECKBOX_SELECTALL_MANUAL);
					manageFCRCheckBoxes();
					manageEIDACheckBoxes();
					manageManualCheckBoxes();
					setManualFieldsEnable();
					setManualFieldsLock();
					if(formObject.getValue(CHECKBOX_SELECTALL_FCR).toString().equalsIgnoreCase(TRUE)){
						setFCRDataInBelowFields();
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_PREFIX_FCR)){
					toggleCheckbox(controlName,CHECKBOX_PREFIX_EIDA ,CHECKBOX_PREFIX_MANUAL);
					manageManualFields(CHECKBOX_PREFIX_MANUAL  ,MANUAL_PREFIX);
					if(formObject.getValue(CHECKBOX_PREFIX_FCR).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(CUST_PREFIX,formObject.getValue(FCR_PREFIX).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_FULLNAME_FCR)){
					toggleCheckbox(controlName,CHECKBOX_FULLNAME_EIDA,CHECKBOX_FULLNAME_MANUAL);
					manageManualFields(CHECKBOX_FULLNAME_MANUAL ,MANUAL_NAME);
					if(formObject.getValue(CHECKBOX_FULLNAME_FCR).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(PD_FULLNAME,formObject.getValue(FCR_NAME).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_SHORTNAME_FCR)){
					toggleCheckbox(controlName,CHECKBOX_SHORTNAME_EIDA,CHECKBOX_SHORTNAME_MANUAL);
					manageManualFields(CHECKBOX_SHORTNAME_MANUAL ,MANUAL_SHORTNAME);
					if(formObject.getValue(CHECKBOX_SHORTNAME_FCR).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(PD_SHORTNAME,formObject.getValue(FCR_SHORTNAME).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_DOB_FCR)){
					toggleCheckbox(controlName,CHECKBOX_DOB_EIDA,CHECKBOX_DOB_MANUAL);
					manageManualFields(CHECKBOX_DOB_MANUAL,MANUAL_DOB);
					if(formObject.getValue(CHECKBOX_DOB_FCR).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(PD_DOB,formObject.getValue(FCR_DOB).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_NO_FCR)){
					toggleCheckbox(controlName,CHECKBOX_PASSPORT_NO_EIDA,CHECKBOX_PASSPORT_NO_MANUAL);
					manageManualFields(CHECKBOX_PASSPORT_NO_MANUAL,MANUAL_PASSPORTNO);

					if(formObject.getValue(CHECKBOX_PASSPORT_NO_FCR).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(HD_PASSPORT_NO,formObject.getValue(FCR_PASSPORTNO).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_PASS_ISS_DT_FCR)){
					toggleCheckbox(controlName,CHECKBOX_PASS_ISS_DT_EIDA,CHECKBOX_PASS_ISS_DT_MANUAL);
					manageManualFields(CHECKBOX_PASS_ISS_DT_MANUAL,MANUAL_PASSPORTISSDATE);

					if(formObject.getValue(CHECKBOX_PASS_ISS_DT_FCR).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(HD_PASS_ISS_DATE,formObject.getValue(FCR_PASSPORTISSDATE).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_PASS_EXP_DT_FCR)){
					toggleCheckbox(controlName,CHECKBOX_PASS_EXP_DT_EIDA,CHECKBOX_PASS_EXP_DT_MANUAL);
					manageManualFields(CHECKBOX_PASS_EXP_DT_MANUAL,MANUAL_PASSPORTEXPDATE);
					if(formObject.getValue(CHECKBOX_PASS_EXP_DT_FCR).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(HD_PASS_EXP_DATE,formObject.getValue(FCR_PASSPORTEXPDATE).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase("CHECK17")){//Rakshita ?
					toggleCheckbox(controlName,"CHECK62","CHECK39");
					manageManualFields("CHECK39",PASSPORTEXPPLACE_MANUAL);
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_NO_FCR)){
					toggleCheckbox(controlName,CHECKBOX_VISA_NO_EIDA,CHECKBOX_VISA_NO_MANUAL);
					manageManualFields(CHECKBOX_VISA_NO_MANUAL,MANUAL_VISANO);
					if(formObject.getValue(CHECKBOX_VISA_NO_FCR).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(HD_VISA_NO,formObject.getValue(FCR_VISANO).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_ISSUE_DATE_FCR)){
					toggleCheckbox(controlName,CHECKBOX_VISA_ISSUE_DATE_EIDA,CHECKBOX_VISA_ISSUE_DATE_MANUAL);
					manageManualFields(CHECKBOX_VISA_ISSUE_DATE_MANUAL,MANUAL_VISAISSDATE);

					if(formObject.getValue(CHECKBOX_VISA_ISSUE_DATE_FCR).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(HD_VISA_ISSUE_DATE,formObject.getValue(FCR_VISAISSDATE).toString());
					}

					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_EXPIRY_DATE_FCR)){
					toggleCheckbox(controlName,CHECKBOX_VISA_EXPIRY_DATE_EIDA,CHECKBOX_VISA_EXPIRY_DATE_MANUAL);
					manageManualFields(CHECKBOX_VISA_EXPIRY_DATE_MANUAL,MANUAL_VISAEXPDATE);

					if(formObject.getValue(CHECKBOX_VISA_EXPIRY_DATE_FCR).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(HD_EXP_DATE,formObject.getValue(FCR_VISAEXPDATE).toString());
					}

					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_NATIONALITY_FCR)){
					toggleCheckbox(controlName,CHECKBOX_NATIONALITY_EIDA,CHECKBOX_NATIONALITY_MANUAL);
					manageManualFields(CHECKBOX_NATIONALITY_MANUAL,MANUAL_NATIONALITY);
					autoSetFatca(controlName);
					manageResidencyAndVisaStatus(RA_IS_UAE_RESIDENT,"COMBO34");//Rakshita ?
					if(formObject.getValue(CHECKBOX_NATIONALITY_FCR).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(CUST_NATIONALITY,formObject.getValue(FCR_NATIONALITY).toString());
					}
					if("UNITED ARAB EMIRATES".equalsIgnoreCase(formObject.getValue(CUST_NATIONALITY).toString())){
						formObject.setStyle(COR_MAKANI, DISABLE, FALSE);
						formObject.setStyle(CONTACT_DETAILS_MAKANI_NO, DISABLE, FALSE);
						formObject.setStyle(RES_MAKANI, DISABLE, FALSE);
					}else{
						formObject.setStyle(COR_MAKANI, DISABLE,TRUE);
						formObject.setStyle(CONTACT_DETAILS_MAKANI_NO, DISABLE,TRUE );
						formObject.setStyle(RES_MAKANI, DISABLE, TRUE);
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_MOTHERSNAME_FCR)){
					toggleCheckbox(controlName,CHECKBOX_MOTHERSNAME_EIDA,CHECKBOX_MOTHERSNAME_MANUAL);
					manageManualFields(CHECKBOX_MOTHERSNAME_MANUAL ,MANUAL_MOTHERNAME);
					if(formObject.getValue(CHECKBOX_MOTHERSNAME_FCR).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(PD_MOTHERMAIDENNAME,formObject.getValue(FCR_MOTHERSNAME).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_EIDANO_FCR)){
					toggleCheckbox(controlName,CHECKBOX_EIDANO_EIDA,CHECKBOX_EIDANO_MANUAL);
					manageManualFields(CHECKBOX_EIDANO_MANUAL ,MANUAL_EIDANO);
					if(formObject.getValue(CHECKBOX_EIDANO_FCR).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(PD_EIDANO,formObject.getValue(FCR_EIDANO).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_CORR_POB_FCR)){
					toggleCheckbox(controlName,CHECKBOX_CORR_POB_EIDA ,CHECKBOX_CORR_POB_MANUAL);
					manageManualFields(CHECKBOX_CORR_POB_MANUAL,MANUAL_ADDRESS);
					if(formObject.getValue(CHECKBOX_CORR_POB_FCR).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(CP_POBOXNO,formObject.getValue(FCR_ADDRESS).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_CITY_FCR)){
					toggleCheckbox(controlName,CHECKBOX_CITY_EIDA,CHECKBOX_CITY_MANUAL);
					manageManualFields(CHECKBOX_CITY_MANUAL,MANUAL_CITY);
					if(formObject.getValue(CHECKBOX_CITY_FCR).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(CP_CITY,formObject.getValue(FCR_CITY).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_STATE_FCR)){
					toggleCheckbox(controlName,CHECKBOX_STATE_EIDA,CHECKBOX_STATE_MANUAL);
					manageManualFields(CHECKBOX_STATE_MANUAL ,MANUAL_STATE);
					if(formObject.getValue(CHECKBOX_STATE_FCR).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(CORR_STATE,formObject.getValue(FCR_STATE).toString());
						if(formObject.getValue(FCR_STATE).toString().equalsIgnoreCase("OTHERS")){
							formObject.setStyle(CP_OTHERS, DISABLE, FALSE);
						}
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_CNTRY_OF_CORR_FCR)){
					toggleCheckbox(controlName,CHECKBOX_CNTRY_OF_CORR_EIDA,CHECKBOX_CNTRY_OF_CORR_MANUAL);
					manageManualFields(CHECKBOX_CNTRY_OF_CORR_MANUAL ,MANUAL_PER_CNTRY);
					if(formObject.getValue(CHECKBOX_CNTRY_OF_CORR_FCR).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(CORR_CNTRY,formObject.getValue(FCR_CNTRY).toString());
						if(formObject.getValue(FCR_CNTRY).toString().equalsIgnoreCase("UNITED ARAB EMIRATES"))
							formObject.setStyle(COR_MAKANI, DISABLE,FALSE);
						else
							formObject.setStyle(COR_MAKANI, DISABLE,TRUE);
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_COUNTRY_PER_RES_FCR)){
					toggleCheckbox(controlName,CHECKBOX_COUNTRY_PER_RES_EIDA,CHECKBOX_COUNTRY_PER_RES_MANUAL);
					manageManualFields(CHECKBOX_COUNTRY_PER_RES_MANUAL,MANUAL_PER_CNTRY);
					if(formObject.getValue(CHECKBOX_COUNTRY_PER_RES_FCR).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(RES_CNTRY,formObject.getValue(FCR_PER_CNTRY).toString());
						if(formObject.getValue(FCR_PER_CNTRY).toString().equalsIgnoreCase("UNITED ARAB EMIRATES"))
							formObject.setStyle(CONTACT_DETAILS_MAKANI_NO, DISABLE, FALSE);
						else
							formObject.setStyle(CONTACT_DETAILS_MAKANI_NO, DISABLE,TRUE);					
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_TELE_RES_FCR)){
					toggleCheckbox(controlName,CHECKBOX_TELE_RES_EIDA,CHECKBOX_TELE_RES_MANUAL);
					manageManualFields(CHECKBOX_TELE_RES_MANUAL,MANUAL_PH);
					if(formObject.getValue(CHECKBOX_TELE_RES_FCR).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(CP_PHONENO,formObject.getValue(FCR_PH).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_TELE_MOB_FCR)){
					toggleCheckbox(controlName,CHECKBOX_TELE_MOB_EIDA,CHECKBOX_TELE_MOB_MANUAL);
					manageManualFields(CHECKBOX_TELE_MOB_MANUAL,MANUAL_MOBILE);
					if(formObject.getValue(CHECKBOX_TELE_MOB_FCR).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(CP_MOBILE,formObject.getValue(FCR_MOBILE).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_EMAIL_FCR)){
					toggleCheckbox(controlName,CHECKBOX_EMAIL_EIDA,CHECKBOX_EMAIL_MANUAL);
					manageManualFields(CHECKBOX_EMAIL_MANUAL,MANUAL_EMAIL);
					if(formObject.getValue(CHECKBOX_EMAIL_FCR).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(CP_EMAIL,formObject.getValue(FCR_EMAIL).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_PROFESSION_FCR)){
					toggleCheckbox(controlName,CHECKBOX_PROFESSION_EIDA,CHECKBOX_PROFESSION_MANUAL);
					manageManualFields(CHECKBOX_PROFESSION_MANUAL,MANUAL_PROFESSION,BTNPROFESSION);
					if(formObject.getValue(CHECKBOX_PROFESSION_FCR).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(PROF_CODE,formObject.getValue(FCR_PROFESSION).toString());
						formObject.setValue(PROFESION,formObject.getValue(FCR_PROFESSION).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_GENDER_FCR)){
					toggleCheckbox(controlName,CHECKBOX_GENDER_EIDA,CHECKBOX_GENDER_MANUAL);
					manageManualFields(CHECKBOX_GENDER_MANUAL,MANUAL_GENDER);
					if(formObject.getValue(CHECKBOX_GENDER_FCR).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(CUST_GENDER,formObject.getValue(FCR_GENDER).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_EMP_NAME_FCR)){
					toggleCheckbox(controlName,CHECKBOX_EMP_NAME_EIDA,CHECKBOX_EMP_NAME_MANUAL);
					manageManualFields(CHECKBOX_EMP_NAME_MANUAL,MANUAL_EMPLYR_NAME,BTNEMLOYERNAME);
					if(formObject.getValue(CHECKBOX_EMP_NAME_FCR).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue("EmpName",formObject.getValue(FCR_EMPLYR_NAME).toString());//Change For Bug Id :13281
						recordList=formObject.getDataFromDB("SELECT CD_STATUS FROM USR_0_EMPLOYER_MASTER WHERE EMP_NAME ='"
								+formObject.getValue(EMPLYR_NAME_FCR).toString()+"'");
						logInfo("Emp Mast Output---",recordList.toString());
						formObject.setValue(ED_CB_TML,FALSE);
						formObject.setValue(ED_CB_NON_TML,FALSE);
						if(recordList.get(0).get(0).equalsIgnoreCase("1") ||recordList.get(0).get(0).equalsIgnoreCase("2")){
							formObject.setValue(ED_CB_TML,TRUE);
						}else{
							formObject.setValue(ED_CB_NON_TML,TRUE);
						}
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKFCR)){
					toggleCheckbox(controlName,CHECKEIDA,CHECKMANUAL);
					manageManualFields(CHECKMANUAL,MANUAL_RESIDENT);
					manageResidencyAndVisaStatus(RA_IS_UAE_RESIDENT,"COMBO34"); // ?
					if(formObject.getValue(CHECKFCR).toString().equalsIgnoreCase(TRUE)){
//						formObject.setValue( PERM_CNTRY, formObject.getValue(FCR_RESIDENT).toString());
						formObject.setValue(RES_CNTRY, formObject.getValue(FCR_RESIDENT).toString());
						if(formObject.getValue(FCR_RESIDENT).toString().equalsIgnoreCase("UNITED ARAB EMIRATES"))
							formObject.setStyle(RES_MAKANI, DISABLE, FALSE);
						else
							formObject.setStyle(RES_MAKANI, DISABLE, TRUE);
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_SELECTALL_EIDA )){
					toggleCheckbox(controlName,CHECKBOX_SELECTALL_FCR ,CHECKBOX_SELECTALL_MANUAL);
					manageFCRCheckBoxes();
					manageEIDACheckBoxes();
					manageManualCheckBoxes();
					setManualFieldsEnable();
					setManualFieldsLock();
					if(formObject.getValue(CHECKBOX_SELECTALL_EIDA ).toString().equalsIgnoreCase(TRUE)){
						setEIDADataInBelowFields();
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_PREFIX_EIDA)){
					toggleCheckbox(controlName,CHECKBOX_PREFIX_FCR ,CHECKBOX_PREFIX_MANUAL);
					manageManualFields(CHECKBOX_PREFIX_MANUAL ,MANUAL_PREFIX);
					if(formObject.getValue(CHECKBOX_PREFIX_EIDA).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue( CUST_PREFIX,formObject.getValue(EIDA_PREFIX).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_FULLNAME_EIDA)){
					toggleCheckbox(controlName,CHECKBOX_FULLNAME_FCR ,CHECKBOX_FULLNAME_MANUAL);
					manageManualFields(CHECKBOX_FULLNAME_MANUAL,MANUAL_NAME);

					if(formObject.getValue(CHECKBOX_FULLNAME_EIDA).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(PD_FULLNAME,formObject.getValue("NAME_EIDA").toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_SHORTNAME_EIDA)){
					toggleCheckbox(controlName,CHECKBOX_SHORTNAME_FCR,CHECKBOX_SHORTNAME_MANUAL);
					manageManualFields(CHECKBOX_SHORTNAME_MANUAL ,MANUAL_SHORTNAME);
					if(formObject.getValue(CHECKBOX_SHORTNAME_EIDA).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(PD_SHORTNAME,formObject.getValue(EIDA_SHORTNAME).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_SHORTNAME_MANUAL  )){
					toggleCheckbox(controlName,CHECKBOX_SHORTNAME_FCR,CHECKBOX_SHORTNAME_EIDA);
					manageManualFields(CHECKBOX_SHORTNAME_MANUAL  ,MANUAL_SHORTNAME);
					if(formObject.getValue(CHECKBOX_SHORTNAME_MANUAL).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(PD_SHORTNAME,formObject.getValue(MANUAL_SHORTNAME).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_DOB_EIDA)){
					toggleCheckbox(controlName,CHECKBOX_DOB_FCR,CHECKBOX_DOB_MANUAL);
					manageManualFields(CHECKBOX_DOB_MANUAL,MANUAL_DOB);
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_NO_EIDA)){
					toggleCheckbox(controlName,CHECKBOX_PASSPORT_NO_FCR,CHECKBOX_PASSPORT_NO_MANUAL);
					manageManualFields(CHECKBOX_PASSPORT_NO_MANUAL,MANUAL_PASSPORTNO);
					if(formObject.getValue(CHECKBOX_PASSPORT_NO_EIDA).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(HD_PASSPORT_NO,formObject.getValue(EIDA_PASSPORTNO).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_PASS_ISS_DT_EIDA)){
					toggleCheckbox(controlName,CHECKBOX_PASS_ISS_DT_FCR,CHECKBOX_PASS_ISS_DT_MANUAL);	
					manageManualFields(CHECKBOX_PASS_ISS_DT_MANUAL,MANUAL_PASSPORTISSDATE);	
					if(formObject.getValue(CHECKBOX_PASS_ISS_DT_EIDA).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(HD_PASS_ISS_DATE,formObject.getValue(EIDA_PASSPORTISSDATE).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_PASS_EXP_DT_EIDA)){
					toggleCheckbox(controlName,CHECKBOX_PASS_EXP_DT_FCR,CHECKBOX_PASS_EXP_DT_MANUAL);
					manageManualFields(CHECKBOX_PASS_EXP_DT_MANUAL,MANUAL_PASSPORTEXPDATE);
					if(formObject.getValue(CHECKBOX_PASS_EXP_DT_EIDA).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(HD_PASS_EXP_DATE,formObject.getValue(EIDA_PASSPORTEXPDATE).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase("CHECK62")){	//Rakshita ?
					toggleCheckbox(controlName,"CHECK17","CHECK39");
					manageManualFields("CHECK39",PASSPORTEXPPLACE_MANUAL);
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_NO_EIDA)){
					toggleCheckbox(controlName,CHECKBOX_VISA_NO_FCR,CHECKBOX_VISA_NO_MANUAL);
					manageManualFields(CHECKBOX_VISA_NO_MANUAL,MANUAL_VISANO);

					if(formObject.getValue(CHECKBOX_VISA_NO_EIDA).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(HD_VISA_NO,formObject.getValue(EIDA_VISANO).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_ISSUE_DATE_EIDA)){
					toggleCheckbox(controlName,CHECKBOX_VISA_ISSUE_DATE_FCR,CHECKBOX_VISA_ISSUE_DATE_MANUAL);
					manageManualFields(CHECKBOX_VISA_ISSUE_DATE_MANUAL,MANUAL_VISAISSDATE);

					if(formObject.getValue(CHECKBOX_VISA_ISSUE_DATE_EIDA).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(HD_VISA_ISSUE_DATE,formObject.getValue(EIDA_VISAISSDATE).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_EXPIRY_DATE_EIDA)){
					toggleCheckbox(controlName,CHECKBOX_VISA_EXPIRY_DATE_FCR,CHECKBOX_VISA_EXPIRY_DATE_MANUAL);
					manageManualFields(CHECKBOX_VISA_EXPIRY_DATE_MANUAL,MANUAL_VISAEXPDATE);

					if(formObject.getValue(CHECKBOX_VISA_EXPIRY_DATE_EIDA).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(HD_EXP_DATE,formObject.getValue(EIDA_VISAEXPDATE).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_NATIONALITY_EIDA)){
					toggleCheckbox(controlName,CHECKBOX_NATIONALITY_FCR,CHECKBOX_NATIONALITY_MANUAL);
					manageManualFields(CHECKBOX_NATIONALITY_MANUAL,MANUAL_NATIONALITY);
					autoSetFatca(controlName);
					manageResidencyAndVisaStatus(RA_IS_UAE_RESIDENT,"COMBO34");//Rakshita ?
					if(formObject.getValue(CHECKBOX_NATIONALITY_EIDA).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(CUST_NATIONALITY,formObject.getValue(EIDA_NATIONALITY).toString());
					}
					if("UNITED ARAB EMIRATES".equalsIgnoreCase(formObject.getValue(CUST_NATIONALITY).toString())){
						formObject.setStyle(COR_MAKANI, DISABLE, FALSE);
						formObject.setStyle(CONTACT_DETAILS_MAKANI_NO, DISABLE, FALSE);
						formObject.setStyle(RES_MAKANI, DISABLE, FALSE);
					}else{
						formObject.setStyle(COR_MAKANI, DISABLE,TRUE);
						formObject.setStyle(CONTACT_DETAILS_MAKANI_NO, DISABLE,TRUE);
						formObject.setStyle(RES_MAKANI, DISABLE,TRUE);
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_MOTHERSNAME_EIDA)){
					toggleCheckbox(controlName,CHECKBOX_MOTHERSNAME_FCR,CHECKBOX_MOTHERSNAME_MANUAL);
					manageManualFields(CHECKBOX_MOTHERSNAME_MANUAL ,MANUAL_MOTHERNAME);

					if(formObject.getValue(CHECKBOX_MOTHERSNAME_EIDA).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(PD_MOTHERMAIDENNAME,formObject.getValue(EIDA_MOTHERNAME).toString());
					}

					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_EIDANO_EIDA)){
					toggleCheckbox(controlName,CHECKBOX_EIDANO_FCR,CHECKBOX_EIDANO_MANUAL);
					manageManualFields(CHECKBOX_EIDANO_MANUAL  ,MANUAL_EIDANO);
					if(formObject.getValue(CHECKBOX_EIDANO_EIDA).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(PD_EIDANO,formObject.getValue(EIDA_EIDANO).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_CORR_POB_EIDA)){
					toggleCheckbox(controlName,CHECKBOX_CORR_POB_FCR,CHECKBOX_CORR_POB_MANUAL);
					manageManualFields(CHECKBOX_CORR_POB_MANUAL ,MANUAL_ADDRESS);
					if(formObject.getValue(CHECKBOX_CORR_POB_EIDA).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(CP_POBOXNO,formObject.getValue(EIDA_ADDRESS).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_CITY_EIDA)){
					toggleCheckbox(controlName,CHECKBOX_CITY_FCR,CHECKBOX_CITY_MANUAL);
					manageManualFields(CHECKBOX_CITY_MANUAL,MANUAL_CITY);

					if(formObject.getValue(CHECKBOX_CITY_EIDA).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(CP_CITY,formObject.getValue(EIDA_CITY).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_STATE_EIDA)){
					toggleCheckbox(controlName,CHECKBOX_STATE_FCR,CHECKBOX_STATE_MANUAL);
					manageManualFields(CHECKBOX_STATE_MANUAL ,MANUAL_STATE);

					if(formObject.getValue(CHECKBOX_STATE_EIDA).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(CORR_STATE,formObject.getValue(EIDA_STATE).toString());

						if(formObject.getValue(EIDA_STATE).toString().equalsIgnoreCase("OTHERS")){//Rakshita
							formObject.setStyle(CP_OTHERS, DISABLE, FALSE);
						}
					}

					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_CNTRY_OF_CORR_EIDA)){
					toggleCheckbox(controlName,CHECKBOX_CNTRY_OF_CORR_FCR,CHECKBOX_CNTRY_OF_CORR_MANUAL );
					manageManualFields(CHECKBOX_CNTRY_OF_CORR_MANUAL ,MANUAL_CNTRY);

					if(formObject.getValue(CHECKBOX_CNTRY_OF_CORR_EIDA).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(CORR_CNTRY,formObject.getValue(EIDA_CNTRY).toString());

						if(formObject.getValue(EIDA_CNTRY).toString().equalsIgnoreCase("UNITED ARAB EMIRATES"))
							formObject.setStyle(COR_MAKANI, DISABLE, FALSE);
						else
							formObject.setStyle(COR_MAKANI, DISABLE,TRUE);
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_COUNTRY_PER_RES_EIDA)){
					toggleCheckbox(controlName,CHECKBOX_COUNTRY_PER_RES_FCR,CHECKBOX_COUNTRY_PER_RES_MANUAL);
					manageManualFields(CHECKBOX_COUNTRY_PER_RES_MANUAL,MANUAL_PER_CNTRY);
					if(formObject.getValue(CHECKBOX_COUNTRY_PER_RES_EIDA).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(RES_CNTRY,formObject.getValue(EIDA_PER_CNTRY).toString());
						if(formObject.getValue(EIDA_PER_CNTRY).toString().equalsIgnoreCase("UNITED ARAB EMIRATES"))
							formObject.setStyle(CONTACT_DETAILS_MAKANI_NO, DISABLE, FALSE);
						else
							formObject.setStyle(CONTACT_DETAILS_MAKANI_NO, DISABLE, TRUE);
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_TELE_RES_EIDA)){
					toggleCheckbox(controlName,CHECKBOX_TELE_RES_FCR,CHECKBOX_TELE_RES_MANUAL);
					manageManualFields(CHECKBOX_TELE_RES_MANUAL,MANUAL_PH);
					if(formObject.getValue(CHECKBOX_TELE_RES_EIDA).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(CP_PHONENO,formObject.getValue(EIDA_PH).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_TELE_MOB_EIDA)){
					toggleCheckbox(controlName,CHECKBOX_TELE_MOB_FCR,CHECKBOX_TELE_MOB_MANUAL);
					manageManualFields(CHECKBOX_TELE_MOB_MANUAL,MANUAL_MOBILE);
					if(formObject.getValue(CHECKBOX_TELE_MOB_EIDA).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(CP_MOBILE,formObject.getValue(EIDA_MOBILE).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_EMAIL_EIDA)){
					toggleCheckbox(controlName,CHECKBOX_EMAIL_FCR,CHECKBOX_EMAIL_MANUAL);
					manageManualFields(CHECKBOX_EMAIL_MANUAL,MANUAL_EMAIL);
					if(formObject.getValue(CHECKBOX_EMAIL_EIDA).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(CP_EMAIL,formObject.getValue(EIDA_EMAIL).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_PROFESSION_EIDA)){
					toggleCheckbox(controlName,CHECKBOX_PROFESSION_FCR,CHECKBOX_PROFESSION_MANUAL);
					manageManualFields(CHECKBOX_PROFESSION_MANUAL,MANUAL_PROFESSION,BTNPROFESSION);
					if(formObject.getValue(CHECKBOX_EMAIL_EIDA).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(PROF_CODE,formObject.getValue(EIDA_PROFESSION).toString());
						formObject.setValue(PROFESION,formObject.getValue(EIDA_PROFESSION).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_GENDER_EIDA)){
					toggleCheckbox(controlName,CHECKBOX_GENDER_FCR,CHECKBOX_GENDER_MANUAL);
					manageManualFields(CHECKBOX_GENDER_MANUAL,MANUAL_GENDER);
					if(formObject.getValue(CHECKBOX_GENDER_EIDA).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(CUST_GENDER,formObject.getValue(EIDA_GENDER).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_EMP_NAME_EIDA)){
					toggleCheckbox(controlName,CHECKBOX_EMP_NAME_FCR,CHECKBOX_EMP_NAME_MANUAL);
					manageManualFields(CHECKBOX_EMP_NAME_MANUAL,MANUAL_EMPLYR_NAME,BTNEMLOYERNAME);
					if(formObject.getValue(CHECKBOX_EMP_NAME_EIDA).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(EMPNAME,formObject.getValue(EIDA_EMPLYR_NAME).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKEIDA)){
					toggleCheckbox(controlName,CHECKFCR ,CHECKMANUAL);
					manageManualFields(CHECKMANUAL, MANUAL_RESIDENT);
					manageResidencyAndVisaStatus(RA_IS_UAE_RESIDENT,"COMBO34");//Rakshit ?
					if(formObject.getValue(CHECKEIDA).toString().equalsIgnoreCase(TRUE)){
//						formObject.setValue(PERM_CNTRY, formObject.getValue(EIDA_RESIDENT).toString());
						formObject.setValue(RES_CNTRY, formObject.getValue(EIDA_RESIDENT).toString());
						if(formObject.getValue(EIDA_RESIDENT).toString().equalsIgnoreCase("UNITED ARAB EMIRATES"))
							formObject.setStyle(RES_MAKANI, DISABLE, FALSE);
						else
							formObject.setStyle(RES_MAKANI, DISABLE,TRUE);
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_SELECTALL_MANUAL)){
					logInfo("","In CHECKBOX_SELECTALL_MANUAL");
					toggleCheckbox(controlName,CHECKBOX_SELECTALL_FCR ,CHECKBOX_SELECTALL_EIDA);
					manageFCRCheckBoxes();
					manageEIDACheckBoxes();
					setManualFieldsEnable();
					manageManualCheckBoxes();
					if(formObject.getValue(CHECKBOX_SELECTALL_MANUAL).toString().equalsIgnoreCase(TRUE)){
						setManualDataInBelowFields();
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_PREFIX_MANUAL)){
					toggleCheckbox(controlName,CHECKBOX_PREFIX_FCR ,CHECKBOX_PREFIX_EIDA);
					manageManualFields(CHECKBOX_PREFIX_MANUAL  ,MANUAL_PREFIX);
					if(formObject.getValue(CHECKBOX_PREFIX_MANUAL).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(CUST_PREFIX,formObject.getValue(MANUAL_PREFIX).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_DOB_MANUAL)){
					toggleCheckbox(controlName,CHECKBOX_DOB_FCR,CHECKBOX_DOB_EIDA);
					manageManualFields(CHECKBOX_DOB_MANUAL,MANUAL_DOB);
					if(formObject.getValue(CHECKBOX_FULLNAME_MANUAL ).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(PD_DOB,formObject.getValue(MANUAL_DOB).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_NO_MANUAL)){
					toggleCheckbox(controlName,CHECKBOX_PASSPORT_NO_FCR,CHECKBOX_PASSPORT_NO_EIDA);
					manageManualFields(CHECKBOX_PASSPORT_NO_MANUAL,MANUAL_PASSPORTNO);
					if(formObject.getValue(CHECKBOX_FULLNAME_MANUAL ).toString().equalsIgnoreCase(TRUE)){
						formObject.setValue(HD_PASSPORT_NO,formObject.getValue(MANUAL_PASSPORTNO).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_PASS_ISS_DT_MANUAL)){
					toggleCheckbox(controlName,CHECKBOX_PASS_ISS_DT_FCR,CHECKBOX_PASS_ISS_DT_EIDA);	
					manageManualFields(CHECKBOX_PASS_ISS_DT_MANUAL,MANUAL_PASSPORTISSDATE);
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_PASS_EXP_DT_MANUAL)){
					toggleCheckbox(controlName,CHECKBOX_PASS_EXP_DT_FCR,CHECKBOX_PASS_EXP_DT_EIDA);
					manageManualFields(CHECKBOX_PASS_EXP_DT_MANUAL,MANUAL_PASSPORTEXPDATE);
					//return;
				} else if(controlName.equalsIgnoreCase("CHECK39")){//Rakshita ?
					toggleCheckbox(controlName,"CHECK17","CHECK62");
					manageManualFields("CHECK39","passportexpplace_manual");
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_NO_MANUAL)){
					toggleCheckbox(controlName,CHECKBOX_VISA_NO_FCR,CHECKBOX_VISA_NO_EIDA);
					manageManualFields(CHECKBOX_VISA_NO_MANUAL,MANUAL_VISANO);
					logInfo("","after opening form again ");
					if(formObject.getValue("CHECK38").toString().equalsIgnoreCase(TRUE) && 
							formObject.getValue(MANUAL_VISANO).toString().equalsIgnoreCase("MDSA")) {
						formObject.setStyle(MANUAL_VISANO, DISABLE,TRUE);
					}
					if(formObject.getValue(CHECKBOX_VISA_NO_MANUAL).toString().equalsIgnoreCase(TRUE) && 
							formObject.getValue(MANUAL_VISANO).toString().equalsIgnoreCase("")) {
						logInfo("","mohit13 in null");//Rakshita
						formObject.setStyle(MANUAL_VISANO, DISABLE,FALSE);
					}
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(HD_VISA_NO,formObject.getValue(MANUAL_VISANO).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_ISSUE_DATE_MANUAL)){
					toggleCheckbox(controlName,CHECKBOX_VISA_ISSUE_DATE_FCR,CHECKBOX_VISA_ISSUE_DATE_EIDA);
					manageManualFields(CHECKBOX_VISA_ISSUE_DATE_MANUAL,MANUAL_VISAISSDATE);
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_EXPIRY_DATE_MANUAL)){
					toggleCheckbox(controlName,CHECKBOX_VISA_EXPIRY_DATE_FCR,CHECKBOX_VISA_EXPIRY_DATE_EIDA);
					manageManualFields(CHECKBOX_VISA_EXPIRY_DATE_MANUAL,MANUAL_VISAEXPDATE);
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_NATIONALITY_MANUAL)){
					toggleCheckbox(controlName,CHECKBOX_NATIONALITY_FCR,CHECKBOX_NATIONALITY_EIDA);
					manageManualFields(CHECKBOX_NATIONALITY_MANUAL,MANUAL_NATIONALITY);
					autoSetFatca(controlName);
					manageResidencyAndVisaStatus(RA_IS_UAE_RESIDENT,"COMBO34");
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE))
					{
						formObject.setValue(CUST_NATIONALITY,formObject.getValue(MANUAL_NATIONALITY).toString());
					}
					if("UNITED ARAB EMIRATES".equalsIgnoreCase(formObject.getValue(CUST_NATIONALITY).toString()))
					{
						formObject.setStyle(COR_MAKANI, DISABLE, FALSE);
						formObject.setStyle(CONTACT_DETAILS_MAKANI_NO, DISABLE, FALSE);
						formObject.setStyle(RES_MAKANI, DISABLE, FALSE);
					}else
					{
						formObject.setStyle(COR_MAKANI, DISABLE,TRUE);
						formObject.setStyle(CONTACT_DETAILS_MAKANI_NO, DISABLE,TRUE);
						formObject.setStyle(RES_MAKANI, DISABLE,TRUE);
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_MOTHERSNAME_MANUAL)){
					toggleCheckbox(controlName,CHECKBOX_MOTHERSNAME_FCR,CHECKBOX_MOTHERSNAME_EIDA);
					manageManualFields(CHECKBOX_MOTHERSNAME_MANUAL ,MANUAL_MOTHERNAME);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE))
					{
						formObject.setValue(PD_MOTHERMAIDENNAME,formObject.getValue(MANUAL_MOTHERNAME).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_EIDANO_MANUAL)){
					toggleCheckbox(controlName,CHECKBOX_EIDANO_FCR,CHECKBOX_EIDANO_EIDA);
					manageManualFields(CHECKBOX_EIDANO_MANUAL,MANUAL_EIDANO);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE))
					{
						formObject.setValue(PD_EIDANO,formObject.getValue(MANUAL_EIDANO).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_CORR_POB_MANUAL)){
					toggleCheckbox(controlName,CHECKBOX_CORR_POB_FCR,CHECKBOX_CORR_POB_EIDA );
					manageManualFields(CHECKBOX_CORR_POB_MANUAL ,MANUAL_ADDRESS);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE))
					{
						formObject.setValue(CP_POBOXNO,formObject.getValue(MANUAL_ADDRESS).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_CITY_MANUAL)){
					toggleCheckbox(controlName,CHECKBOX_CITY_FCR,CHECKBOX_CITY_EIDA);
					manageManualFields(CHECKBOX_CITY_MANUAL,MANUAL_CITY);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE))
					{
						formObject.setValue(CP_CITY,formObject.getValue(MANUAL_CITY).toString());
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_STATE_MANUAL )){
					toggleCheckbox(controlName,CHECKBOX_STATE_FCR,CHECKBOX_STATE_EIDA);
					manageManualFields(CHECKBOX_STATE_MANUAL,MANUAL_STATE);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE))
					{
						formObject.setValue(CORR_STATE,formObject.getValue(MANUAL_STATE).toString());

						if(formObject.getValue(MANUAL_STATE).toString().equalsIgnoreCase("OTHERS"))//Rakshita
						{
							if(formObject.getValue(MANUAL_STATE).toString().equalsIgnoreCase("OTHERS"))
							{
								formObject.setStyle(CP_OTHERS, DISABLE, FALSE);
							}
						}
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_CNTRY_OF_CORR_MANUAL)){
					toggleCheckbox(controlName,CHECKBOX_CNTRY_OF_CORR_FCR,CHECKBOX_CNTRY_OF_CORR_EIDA);
					manageManualFields(CHECKBOX_CNTRY_OF_CORR_MANUAL ,MANUAL_CNTRY);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE))
					{
						formObject.setValue(CORR_CNTRY,formObject.getValue(MANUAL_CNTRY).toString());

						if(formObject.getValue(MANUAL_CNTRY).toString().equalsIgnoreCase("UNITED ARAB EMIRATES"))
							formObject.setStyle(COR_MAKANI, DISABLE,FALSE);
						else
							formObject.setStyle(COR_MAKANI, DISABLE,TRUE);
					}
					//return;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_COUNTRY_PER_RES_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_COUNTRY_PER_RES_FCR,CHECKBOX_COUNTRY_PER_RES_EIDA);
					manageManualFields(CHECKBOX_COUNTRY_PER_RES_MANUAL,MANUAL_PER_CNTRY);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(RES_CNTRY, formObject.getValue(MANUAL_PER_CNTRY).toString());
						if(formObject.getValue(MANUAL_PER_CNTRY).toString().equalsIgnoreCase("UNITED ARAB EMIRATES"))
							formObject.setStyle(CONTACT_DETAILS_MAKANI_NO, DISABLE, FALSE);
						else
							formObject.setStyle(CONTACT_DETAILS_MAKANI_NO, DISABLE, TRUE);
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_TELE_RES_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_TELE_RES_FCR,CHECKBOX_TELE_RES_EIDA);
					manageManualFields(CHECKBOX_TELE_RES_MANUAL,MANUAL_PH);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(CP_PHONENO,formObject.getValue(MANUAL_PH).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_TELE_MOB_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_TELE_MOB_FCR,CHECKBOX_TELE_MOB_EIDA);
					manageManualFields(CHECKBOX_TELE_MOB_MANUAL,MANUAL_MOBILE);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) 
						formObject.setValue(CP_MOBILE,formObject.getValue(MANUAL_MOBILE).toString());

				} else if(controlName.equalsIgnoreCase(CHECKBOX_EMAIL_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_EMAIL_FCR,CHECKBOX_EMAIL_EIDA);
					manageManualFields(CHECKBOX_EMAIL_MANUAL,MANUAL_EMAIL);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(CP_EMAIL,formObject.getValue(MANUAL_EMAIL).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_PROFESSION_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_PROFESSION_FCR,CHECKBOX_PROFESSION_EIDA);
					manageManualFields(CHECKBOX_PROFESSION_MANUAL,MANUAL_PROFESSION,BTNPROFESSION);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(PROF_CODE,formObject.getValue(MANUAL_PROFESSION).toString());
						formObject.setValue(PROFESION,formObject.getValue(MANUAL_PROFESSION).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_GENDER_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_GENDER_FCR,CHECKBOX_GENDER_EIDA);
					manageManualFields(CHECKBOX_GENDER_MANUAL,MANUAL_GENDER);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(CUST_GENDER,formObject.getValue(MANUAL_GENDER).toString());
					}
				} else if(controlName.equalsIgnoreCase(MANUAL_GENDER)) {
					formObject.setValue(CUST_GENDER,formObject.getValue(MANUAL_GENDER).toString());
				}//MANUAL_PROFESSION
				else if(controlName.equalsIgnoreCase(MANUAL_PROFESSION)) {
					formObject.setValue(PROF_CODE,formObject.getValue(MANUAL_PROFESSION).toString());
					formObject.setValue(PROFESION,formObject.getValue(MANUAL_PROFESSION).toString());
				} else if(controlName.equalsIgnoreCase(CHECKBOX_EMP_NAME_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_EMP_NAME_FCR,CHECKBOX_EMP_NAME_EIDA);
					manageManualFields(CHECKBOX_EMP_NAME_MANUAL,MANUAL_EMPLYR_NAME,BTNEMLOYERNAME); 
				} else if(controlName.equalsIgnoreCase(CHECKMANUAL)) {
					toggleCheckbox(controlName,CHECKFCR,CHECKEIDA);
					manageManualFields(CHECKMANUAL,MANUAL_RESIDENT);
					manageResidencyAndVisaStatus(RA_IS_UAE_RESIDENT,"COMBO34");
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
//						formObject.setValue(PERM_CNTRY,formObject.getValue(MANUAL_RESIDENT).toString());
						formObject.setValue(RES_CNTRY,formObject.getValue(MANUAL_RESIDENT).toString());
						if(formObject.getValue(EIDA_RESIDENT).toString().equalsIgnoreCase("UNITED ARAB EMIRATES"))
							formObject.setStyle(RES_MAKANI, DISABLE, FALSE);
						else
							formObject.setStyle(RES_MAKANI, DISABLE, TRUE);
					}
				} else if(controlName.equalsIgnoreCase(IS_SALARY_TRANSFER_CAT_CHANGE)) {
					toggleCheckboxTP(controlName,IS_MORTAGAGE_CAT_CHANGE,IS_INSURANCE_CAT_CHANGE,IS_TRB_CAT_CHANGE,
							IS_OTHERS_CAT_CHANGE,IS_VVIP);
					managePromoCodeCatChange();
				} else if(controlName.equalsIgnoreCase(IS_MORTAGAGE_CAT_CHANGE)) {
					toggleCheckboxTP(controlName,IS_VVIP,IS_SALARY_TRANSFER_CAT_CHANGE,IS_INSURANCE_CAT_CHANGE,
							IS_TRB_CAT_CHANGE,IS_OTHERS_CAT_CHANGE);
					managePromoCodeCatChange(); 
				} else if(controlName.equalsIgnoreCase(IS_INSURANCE_CAT_CHANGE)) {
					toggleCheckboxTP(controlName,IS_VVIP,IS_SALARY_TRANSFER_CAT_CHANGE,IS_MORTAGAGE_CAT_CHANGE,
							IS_TRB_CAT_CHANGE,IS_OTHERS_CAT_CHANGE);
					managePromoCodeCatChange();
				} else if(controlName.equalsIgnoreCase(IS_TRB_CAT_CHANGE)) {
					toggleCheckboxTP(controlName,IS_VVIP,IS_SALARY_TRANSFER_CAT_CHANGE,IS_MORTAGAGE_CAT_CHANGE,
							IS_INSURANCE_CAT_CHANGE,IS_OTHERS_CAT_CHANGE);
					managePromoCodeCatChange();
				} else if(controlName.equalsIgnoreCase(IS_OTHERS_CAT_CHANGE)) {
					toggleCheckboxTP(controlName,IS_VVIP,IS_SALARY_TRANSFER_CAT_CHANGE,IS_MORTAGAGE_CAT_CHANGE,
							IS_INSURANCE_CAT_CHANGE,IS_TRB_CAT_CHANGE);
					managePromoCodeCatChange();
				} else if(controlName.equalsIgnoreCase(IS_VVIP)) {
					toggleCheckboxTP(controlName,IS_SALARY_TRANSFER_CAT_CHANGE,IS_MORTAGAGE_CAT_CHANGE,
							IS_INSURANCE_CAT_CHANGE,IS_TRB_CAT_CHANGE,IS_OTHERS_CAT_CHANGE);
					managePromoCodeCatChange();
				} else if(controlName.equalsIgnoreCase(IS_PREVILEGE_TP_CAT_CHANGE)) {
					toggleCheckboxTP(controlName,IS_TRAVEL_CAT_CHANGE,IS_SPORT_CAT_CHANGE,IS_SHOPPING_CAT_CHANGE,
							IS_ENTERTAINMENT_CAT_CHANGE);
				} else if(controlName.equalsIgnoreCase(IS_TRAVEL_CAT_CHANGE)) {
					toggleCheckboxTP(controlName,IS_PREVILEGE_TP_CAT_CHANGE,IS_SPORT_CAT_CHANGE,IS_SHOPPING_CAT_CHANGE,
							IS_ENTERTAINMENT_CAT_CHANGE);
				} else if(controlName.equalsIgnoreCase(IS_SPORT_CAT_CHANGE)) {
					toggleCheckboxTP(controlName,IS_PREVILEGE_TP_CAT_CHANGE,IS_TRAVEL_CAT_CHANGE,IS_SHOPPING_CAT_CHANGE,
							IS_ENTERTAINMENT_CAT_CHANGE);
				} else if(controlName.equalsIgnoreCase(IS_SHOPPING_CAT_CHANGE)) {
					toggleCheckboxTP(controlName,IS_PREVILEGE_TP_CAT_CHANGE,IS_TRAVEL_CAT_CHANGE,IS_SPORT_CAT_CHANGE,
							IS_ENTERTAINMENT_CAT_CHANGE);
				} else if(controlName.equalsIgnoreCase(IS_ENTERTAINMENT_CAT_CHANGE)) {
					toggleCheckboxTP(controlName,IS_PREVILEGE_TP_CAT_CHANGE,IS_TRAVEL_CAT_CHANGE,IS_SPORT_CAT_CHANGE,
							IS_SHOPPING_CAT_CHANGE);
				} else if(controlName.equalsIgnoreCase(IDS_CB_SAL_TRANSFER)) {
					toggleCheckboxTP(controlName,IDS_CB_MORTGAGES,IDS_CB_INSURANCE,IDS_CB_TRB,IDS_CB_OTHERS,IDS_CB_VVIP);
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
				} else if(controlName.equalsIgnoreCase(IDS_CB_VVIP)) {
					toggleCheckboxTP(controlName,IDS_CB_SAL_TRANSFER,IDS_CB_MORTGAGES,IDS_CB_INSURANCE,IDS_CB_TRB,IDS_CB_OTHERS);
					managePromoCode(); 
				} else if(controlName.equalsIgnoreCase(IDS_PC_CB_TP)) {
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
					if(formObject.getValue(ED_CB_TML).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(ED_CB_NON_TML,FALSE);
					} else {
						formObject.setValue(ED_CB_TML,FALSE);
					}
				} else if(controlName.equalsIgnoreCase(ED_CB_NON_TML)) {
					if(formObject.getValue(ED_CB_NON_TML).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(ED_CB_TML,FALSE);
					} else {
						formObject.setValue(ED_CB_NON_TML,FALSE);
					}
				} else if(controlName.equalsIgnoreCase(PA_SAMEAS)) {
					controlValue = formObject.getValue(controlName).toString();
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
						formObject.setValue(PA_STREET, formObject.getValue(CP_STREET).toString());
						formObject.setValue(PA_VILLAFLATNO, formObject.getValue(CP_FLOOR).toString());
						formObject.setValue(PA_BUILDINGNAME, formObject.getValue(CP_POBOXNO).toString());
						formObject.setValue(PA_STREET,formObject.getValue(CP_STREET).toString()); 
						formObject.setValue(CONTACT_DETAILS_MAKANI_NO,formObject.getValue(COR_MAKANI).toString());
					} else {
						String[] clearFields = {PERM_COUNTRY, PERM_STATE, PA_STREET, PA_VILLAFLATNO, PA_CITY, PA_OTHERS,
								PA_BUILDINGNAME, CITYBIRTH_MANUAL, CONTACT_DETAILS_MAKANI_NO};
						clearControls(clearFields);
					}
				} else if(controlName.equalsIgnoreCase(RA_SAMEAS)) {			        	 
					if(formObject.getValue(RA_SAMEAS).toString().equalsIgnoreCase("Mailing Address")) {
						logInfo("RA_SAMEAS","COMBO 32 CLICK=================inside mailing");
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
						formObject.setValue(OTHER_RES_CITY, formObject.getValue(OTHER_CORR_CITY).toString());
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
						formObject.setValue(OTHER_RES_CITY, formObject.getValue(OTHER_PERM_CITY).toString());
						//formObject.setValue(OTHER_PERM_CITY, formObject.getValue(OTHER_RESIDENTIAL_CITY).toString());
						//formObject.setValue("Text95", formObject.getValue(PA_OTHERS).toString());
						formObject.setValue(OTHER_RES_CITY,formObject.getValue(OTHER_PERM_CITY).toString());
						formObject.setValue(RES_MAKANI,formObject.getValue(CONTACT_DETAILS_MAKANI_NO).toString());  
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
				} else if(controlName.equalsIgnoreCase("CRS_CERTIFICATION_OBTAINED")) {
					if(formObject.getValue(controlName).toString().equalsIgnoreCase("Yes")) {
						formObject.setValue("CRS_Classification","UPDATED-DOCUMENTED");
					} else if(formObject.getValue(controlName).toString().equalsIgnoreCase("No")) {
						formObject.setValue("CRS_Classification","UPDATED-UNDOCUMENTED");
					}
				}  else if(controlName.equalsIgnoreCase(CRS_CHECKBOX)) {	
					logInfo("CPDMakerThreeStep","Click: INSIDE CRS_CHECKBOX");
					if(formObject.getValue(CRS_CHECKBOX).toString().equalsIgnoreCase("true"))
						formObject.setStyle(SEC_CRS_DETAILS,DISABLE,FALSE);
					else
						formObject.setStyle(SEC_CRS_DETAILS,DISABLE,TRUE);
				} else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_STATUS_FCR) || 
						controlName.equalsIgnoreCase(CHECKBOX_VISA_STATUS_EIDA) || 
						controlName.equalsIgnoreCase(CHECKBOX_VISA_STATUS_MANUAL)){
					logInfo("CPDMakerThreeStep","Click: INSIDE toggle Visa Status");
					if(controlName.equalsIgnoreCase(CHECKBOX_VISA_STATUS_FCR))
						toggleCheckbox(controlName,CHECKBOX_VISA_STATUS_EIDA,CHECKBOX_VISA_STATUS_MANUAL);

					else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_STATUS_MANUAL))
						toggleCheckbox(controlName,CHECKBOX_VISA_STATUS_EIDA,CHECKBOX_VISA_STATUS_FCR);

					else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_STATUS_EIDA))
						toggleCheckbox(controlName,CHECKBOX_VISA_STATUS_FCR,CHECKBOX_VISA_STATUS_MANUAL);

					manageManualFields(CHECKBOX_VISA_STATUS_MANUAL,MANUAL_VISASTATUS);
					if( returnVisaStatus().equalsIgnoreCase("Residency Visa") ){
						formObject.setStyle(DRP_RESEIDA,DISABLE,FALSE);
					} else {
						formObject.setStyle(DRP_RESEIDA,DISABLE,TRUE);
					}
					//return false;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_TYPE_MANUAL) ||
						controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_TYPE_FCR) ||
						controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_TYPE_EIDA)){
					logInfo("CPDMakerThreeStep","Click: INSIDE TOGGLE Passport Type");
					if(controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_TYPE_MANUAL))
						toggleCheckbox(controlName,CHECKBOX_PASSPORT_TYPE_FCR,CHECKBOX_PASSPORT_TYPE_EIDA);
					else if(controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_TYPE_EIDA))
						toggleCheckbox(controlName,CHECKBOX_PASSPORT_TYPE_FCR,CHECKBOX_PASSPORT_TYPE_MANUAL);
					else if(controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_TYPE_FCR))
						toggleCheckbox(controlName,CHECKBOX_PASSPORT_TYPE_MANUAL,CHECKBOX_PASSPORT_TYPE_EIDA);

					manageManualFields(CHECKBOX_PASSPORT_TYPE_MANUAL,MANUAL_PASSTYPE);
					if( returnVisaStatus().equalsIgnoreCase("Residency Visa") )
					{
						formObject.setStyle("drp_reseida",DISABLE,FALSE);
					}
					else
					{
						formObject.setStyle("drp_reseida",DISABLE,TRUE);
					}

					//return false;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_COB_FCR) || 
						controlName.equalsIgnoreCase(CHECKBOX_COB_EIDA) || 
						controlName.equalsIgnoreCase(CHECKBOX_COB_MANUAL)) {
					logInfo("CPDMakerThreeStep","Click: INSIDE TOGGLE COB");
					if(controlName.equalsIgnoreCase(CHECKBOX_COB_FCR))
						toggleCheckbox(controlName,CHECKBOX_COB_EIDA,CHECKBOX_COB_MANUAL);
					else if(controlName.equalsIgnoreCase(CHECKBOX_COB_EIDA))
						toggleCheckbox(controlName,CHECKBOX_COB_FCR,CHECKBOX_COB_MANUAL);
					else if(controlName.equalsIgnoreCase(CHECKBOX_COB_MANUAL))
						toggleCheckbox(controlName,CHECKBOX_COB_FCR,CHECKBOX_COB_EIDA);
					manageManualFields(CHECKBOX_COB_MANUAL,MANUAL_COUNTRYBIRTH);
					//return false;
				} else if(controlName.equalsIgnoreCase(CHECKBOX_FIRSTNAME_FCR))	{
					logInfo("CPDMakerThreeStep","Click: INSIDE CHECKBOX_FIRSTNAME_FCR ");
					toggleCheckbox(controlName,CHECKBOX_FIRSTNAME_EIDA,CHECKBOX_FIRSTNAME_MANUAL);
					manageManualFields(CHECKBOX_FIRSTNAME_MANUAL,MANUAL_FIRSTNAME);
					if(controlName.equalsIgnoreCase("True")){
						formObject.setValue(CRS_FIRSTNAME,formObject.getValue(FCR_FIRSTNAME).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_FIRSTNAME_EIDA)){
					logInfo("CPDMakerThreeStep","Click: INSIDE CHECKBOX_FIRSTNAME_EIDA");
					toggleCheckbox(controlName,CHECKBOX_FIRSTNAME_FCR ,CHECKBOX_FIRSTNAME_MANUAL);
					manageManualFields(CHECKBOX_FIRSTNAME_MANUAL,MANUAL_FIRSTNAME);
					if(controlName.equalsIgnoreCase("True")){
						formObject.setValue(CRS_FIRSTNAME,formObject.getValue(EIDA_FIRSTNAME).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_FIRSTNAME_MANUAL))	{
					logInfo("CPDMakerThreeStep","Click: INSIDE CHECKBOX_FIRSTNAME_MANUAL");
					toggleCheckbox(controlName,CHECKBOX_FIRSTNAME_FCR ,CHECKBOX_FIRSTNAME_EIDA);
					manageManualFields(CHECKBOX_FIRSTNAME_MANUAL,MANUAL_FIRSTNAME);
					if(controlName.equalsIgnoreCase("True")){
						formObject.setValue(CRS_FIRSTNAME,formObject.getValue(MANUAL_FIRSTNAME).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_LASTNAME_FCR))			{
					logInfo("CPDMakerThreeStep","Click: INSIDE CHECKBOX_LASTNAME_FCR");
					toggleCheckbox(controlName,CHECKBOX_LASTNAME_EIDA,CHECKBOX_LASTNAME_MANUAL);
					manageManualFields(CHECKBOX_LASTNAME_MANUAL,MANUAL_LASTNAME);
					if(controlName.equalsIgnoreCase("True")){
						formObject.setValue(CRS_LASTNAME,formObject.getValue(FCR_LASTNAME).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_LASTNAME_EIDA)){
					logInfo("CPDMakerThreeStep","Click: INSIDE CHECKBOX_LASTNAME_EIDA");
					toggleCheckbox(controlName,CHECKBOX_LASTNAME_FCR,CHECKBOX_LASTNAME_MANUAL);
					manageManualFields(CHECKBOX_LASTNAME_MANUAL,MANUAL_LASTNAME);
					if(controlName.equalsIgnoreCase("True")) {
						formObject.setValue(CRS_LASTNAME,formObject.getValue(EIDA_LASTNAME).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_LASTNAME_MANUAL)){
					logInfo("CPDMakerThreeStep","Click: INSIDE CHECKBOX_MANUAL_LASTNAME");
					toggleCheckbox(controlName,CHECKBOX_LASTNAME_FCR,CHECKBOX_LASTNAME_EIDA);
					manageManualFields(CHECKBOX_LASTNAME_MANUAL,MANUAL_LASTNAME);
					if(controlName.equalsIgnoreCase("True")){
						formObject.setValue(CRS_LASTNAME,formObject.getValue(MANUAL_LASTNAME).toString());
					}
				} else if(controlName.equalsIgnoreCase(RA_CB_OTHERS)) {     
					/*logInfo("CPDMakerThreeStep","Click: INSIDE RA_CB_OTHERS");
					if(formObject.getValue(RA_CB_OTHERS).toString().equalsIgnoreCase("True")){
						logInfo("CPDMakerThreeStep","Click: INSIDE RA_CB_OTHERS if");
						formObject.setStyle(RA_OTHERS,DISABLE,FALSE);
						formObject.setValue(RA_CB_GEN_TRDNG_CMPNY,FALSE);
						formObject.setValue(RA_CB_PRECIOUS_STONE_DEALER,FALSE);
						formObject.setValue(RA_CB_BULLN_COMMDTY_BROKR,FALSE);
						formObject.setValue(RA_CB_REAL_STATE_BROKR,FALSE);
						formObject.setValue(RA_CB_REAL_STATE_BROKR,FALSE);
					} else {
						logInfo("CPDMakerThreeStep","Click: INSIDE RA_CB_OTHERS else");
						formObject.setValue(RA_OTHERS,"");
						formObject.setStyle(RA_OTHERS,DISABLE,TRUE);				
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
					logInfo("CPDMakerThreeStep","Click: INSIDE IDS_OTH_CB_OTHERS");
					if(formObject.getValue(IDS_OTH_CB_OTHERS).toString().equalsIgnoreCase("True")){
						logInfo("CPDMakerThreeStep","Click: INSIDE IDS_OTH_CB_OTHERS true");
						formObject.setStyle(IDS_BNFT_CB_OTHERS,DISABLE,FALSE);
						formObject.setValue(IDS_CB_SAL_TRANSFER,"False");
						formObject.setValue(IDS_CB_MORTGAGES,"False");
						formObject.setValue(IDS_CB_INSURANCE,"False");
						formObject.setValue(IDS_CB_TRB,"False");
						formObject.setValue(IDS_CB_OTHERS,"False");
						formObject.setValue(IDS_CB_VVIP,"False");
						if(formObject.getValue(PD_CUSTSEGMENT).toString().equalsIgnoreCase("Privilege") || 
								formObject.getValue(PD_CUSTSEGMENT).toString().equalsIgnoreCase("Emirati")){
							formObject.setValue(IDS_PC_CB_TP,"False");
							formObject.setValue(IDS_PC_CB_TRAVEL,"False");
							formObject.setValue(IDS_PC_CB_SPORT,"False");
							formObject.setValue(IDS_PC_CB_SHOPPING,"False");
							formObject.setValue(IDS_PC_CB_ENTERTAINMENT,"False");					
						} else if(formObject.getValue(PD_CUSTSEGMENT).toString().equalsIgnoreCase("Emirati Excellency")||
								formObject.getValue(PD_CUSTSEGMENT).toString().equalsIgnoreCase("Excellency")||
								formObject.getValue(PD_CUSTSEGMENT).toString().equalsIgnoreCase("Private Clients")){
							formObject.setValue(IDS_BNFT_CB_TP,"False");
						}
					} else {
						logInfo("CPDMakerThreeStep","Click: INSIDE IDS_OTH_CB_OTHERS false");
						formObject.setValue(IDS_BNFT_CB_OTHERS,"");
						formObject.setStyle(IDS_BNFT_CB_OTHERS,DISABLE,TRUE);				
					}
				} else if(controlName.equalsIgnoreCase(IDS_CB_SAL_TRANSFER)||controlName.equalsIgnoreCase(IDS_CB_MORTGAGES)
						||controlName.equalsIgnoreCase(IDS_CB_INSURANCE)||controlName.equalsIgnoreCase(IDS_CB_TRB)
						||controlName.equalsIgnoreCase(IDS_CB_OTHERS)||controlName.equalsIgnoreCase(IDS_CB_VVIP)
						||controlName.equalsIgnoreCase(IDS_PC_CB_TP)||controlName.equalsIgnoreCase(IDS_PC_CB_ENTERTAINMENT)
						||controlName.equalsIgnoreCase(IDS_PC_CB_SHOPPING)||controlName.equalsIgnoreCase(IDS_PC_CB_SPORT)
						||controlName.equalsIgnoreCase(IDS_PC_CB_TRAVEL)||controlName.equalsIgnoreCase(IDS_BNFT_CB_TP)) {
					logInfo("CPDMakerThreeStep","Click: INSIDE TOGGLE IDS");
					if(formObject.getValue(IDS_OTH_CB_OTHERS).toString().equalsIgnoreCase("True")){
						formObject.setValue(IDS_OTH_CB_OTHERS,"False");
						formObject.setValue(IDS_BNFT_CB_OTHERS,"");
						formObject.setStyle(IDS_BNFT_CB_OTHERS,DISABLE,TRUE);
					}
				} else if(controlName.equalsIgnoreCase(IS_CAT_BENEFIT_OTHER)){   
					if(formObject.getValue(IS_CAT_BENEFIT_OTHER).toString().equalsIgnoreCase("True")){
						logInfo("CPDMakerThreeStep","Click: INSIDE IS_CAT_BENEFIT_OTHER true");
						formObject.setStyle(CAT_BENEFIT_OTHER,DISABLE,FALSE);
						formObject.setValue(IS_SALARY_TRANSFER_CAT_CHANGE,"False");
						formObject.setValue(IS_MORTAGAGE_CAT_CHANGE,"False");
						formObject.setValue(IS_INSURANCE_CAT_CHANGE,"False");
						formObject.setValue(IS_TRB_CAT_CHANGE,"False");
						formObject.setValue(IS_OTHERS_CAT_CHANGE,"False");
						formObject.setValue(IS_VVIP,"False");
						if(formObject.getValue(NEW_CUST_SEGMENT).toString().equalsIgnoreCase("Privilege") || 
								formObject.getValue(NEW_CUST_SEGMENT).toString().equalsIgnoreCase("Emirati")){
							formObject.setValue(IS_PREVILEGE_TP_CAT_CHANGE,"False");
							formObject.setValue(IS_ENTERTAINMENT_CAT_CHANGE,"False");
							formObject.setValue(IS_SHOPPING_CAT_CHANGE,"False");
							formObject.setValue(IS_SPORT_CAT_CHANGE,"False");
							formObject.setValue(IS_TRAVEL_CAT_CHANGE,"False");					
						} else if(formObject.getValue(NEW_CUST_SEGMENT).toString().equalsIgnoreCase("Emirati Excellency")||
								formObject.getValue(NEW_CUST_SEGMENT).toString().equalsIgnoreCase("Excellency")||
								formObject.getValue(NEW_CUST_SEGMENT).toString().equalsIgnoreCase("Private Clients")) {
							formObject.setValue(IS_EXCELLENCY_TP_CAT_CHANGE,"False");
						}
					} else{
						logInfo("CPDMakerThreeStep","Click: INSIDE IS_CAT_BENEFIT_OTHER else");
						formObject.setValue(CAT_BENEFIT_OTHER,"");
						formObject.setStyle(CAT_BENEFIT_OTHER,DISABLE,TRUE);				
					}
				} else if(controlName.equalsIgnoreCase(BRNCH_OF_INSTANT_ISSUE)){ 
					if(formObject.getValue(BRNCH_OF_INSTANT_ISSUE).toString().toString().equalsIgnoreCase("Kiosk")){
						logInfo("CPDMakerThreeStep","Click: INSIDE BRNCH_OF_INSTANT_ISSUE Kiosk");
						formObject.setStyle(INSTANT_DEL_NO,DISABLE,FALSE);
						formObject.setStyle(INSTANT_DEL_YES,DISABLE,FALSE);
					}else{
						logInfo("CPDMakerThreeStep","Click: INSIDE BRNCH_OF_INSTANT_ISSUE else");
						formObject.setStyle(INSTANT_DEL_NO,DISABLE,TRUE);
						formObject.setStyle(INSTANT_DEL_YES,DISABLE,TRUE);
					}
				} else if(controlName.equalsIgnoreCase(INSTANT_DEL_NO)){
					logInfo("CPDMakerThreeStep","Click: INSIDE INSTANT_DEL_NO");
					formObject.setValue(NOM_REQ,"");	
					Delivery_Preferences_Tab();
				} else if(controlName.equalsIgnoreCase(INSTANT_DEL_YES)) {
					logInfo("CPDMakerThreeStep","Click: INSIDE INSTANT_DEL_YES");
					formObject.setStyle(NOM_REQ,DISABLE,FALSE);
				} else if(controlName.equalsIgnoreCase(NOM_REQ)) {
					if(formObject.getValue(NOM_REQ).toString().equalsIgnoreCase("Yes"))	{	
						logInfo("CPDMakerThreeStep","Click: INSIDE NOM_REQ yes");
						formObject.setStyle(EXISTING_NOM_PRSN,DISABLE,FALSE);	
						formObject.setValue(EXISTING_NOM_PRSN,"Select");
					}else{
						logInfo("CPDMakerThreeStep","Click: INSIDE NOM_REQ no");
						formObject.setValue(EXISTING_NOM_PRSN,"--Select--");	
						formObject.setStyle(EXISTING_NOM_PRSN,DISABLE,TRUE);	
						formObject.clearTable(DELIVERY_PREFERENCE);
						Frame48_CPD_Disable();
						manageNomineeDetails(formObject.getValue(NOM_REQ).toString());
					}
				} else if(controlName.equalsIgnoreCase(EXISTING_NOM_PRSN)) {
					logInfo("CPDMakerThreeStep","Click: INSIDE EXISTING_NOM_PRSN");
					// pass selected rows in data from js , js code written
					// below this code
					if (controlName.equalsIgnoreCase(EXISTING_NOM_PRSN)&& formObject.getValue(EXISTING_NOM_PRSN)
							.toString().equalsIgnoreCase("Yes")) {
						int iSelectedRow = Integer.parseInt(data);
						String cust_Id = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 2);
						String sBankRelation = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 7);
						List<List<String>> result;
						String query = "SELECT NOM_NAME,NOM_PO_BOX,NOM_ADD1||NOM_ADD2,NOM_LAND,NOM_CITY,"
								+ "NOM_STATE,NOM_OTHERS,NOM_CNTRY,NOM_FAX,NOM_ZIP,NOM_EMAIL,NOM_PREF_LANG,NOM_PHONE,'',cust_id,nom_id,NOM_MOB from USR_0_NOM_MAST where cust_id ='"
								+ cust_Id + "'";
						result = formObject.getDataFromDB(query);
						String listviewValues = result.get(0).get(0) + "##"+ result.get(0).get(1) + "##"
								+ result.get(0).get(2) + "##"+ result.get(0).get(3) + "##"+ result.get(0).get(4) + "##"
								+ result.get(0).get(5) + "##"+ result.get(0).get(6) + "##"
								+ result.get(0).get(7) + "##"+ result.get(0).get(8) + "##"+ result.get(0).get(9) + "##"
								+ result.get(0).get(10); 
						LoadListViewWithHardCodeValues(EXISTING_NOM_PRSN, "",listviewValues); // check this ? line 3207
						if (getGridCount(DELIVERY_PREFERENCE) == 0) {
							sendMessageValuesList(EXISTING_NOM_PRSN,"There Is No Existing Nominee For This Customer.");
							formObject.setValue(EXISTING_NOM_PRSN, "");
							return null;
						}
						Frame48_CPD_ENable();
					} else {
						formObject.clearCombo(DELIVERY_PREFERENCE);
					}
					//if(controlValue.equalsIgnoreCase("Yes")){
					/*NGRepeater objChkRepeater = formObject.getNGRepeater("REPEAT_FRAME"); //id issue
						int iSelectedRow = objChkRepeater.getSelectedRowIndex();
						String cust_id=objChkRepeater.getValue(iSelectedRow,"AO_acc_relation.cid");

						String sql="select nom_name,nom_po_box,nom_add1||nom_add2,nom_land,nom_city,nom_state,"+
						"nom_others,nom_cntry,nom_fax,nom_zip,nom_email,nom_pref_lang,nom_phone,'',cust_id,"+
						"nom_id,nom_mob from usr_0_nom_mast where cust_id ='"+cust_id+"'";
						logInfo("","ok"+cust_id+"---------"+sql);
						LoadListView(sql, 17, "AO_delivery_preference", "0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16");	

						logInfo("","************ Count ************** "+formObject.getNGListCount("AO_delivery_preference"));
						if(formObject.getNGListCount("AO_delivery_preference")==0)
						{
							JOptionPane.showMessageDialog(_objApplet,"There Is No Existing Nominee For This Customer.");
							formObject.setValue(EXISTING_NOM_PRSN, "--Select--");
							formObject.NGFocus(EXISTING_NOM_PRSN);
							return false;
						}

						Frame48_CPD_ENable();
					}
					else if(controlValue.equalsIgnoreCase("No"))
					{
						Frame48_CPD_ENable();
						formObject.NGClear("AO_delivery_preference");
					}
					else
					{
						Frame48_CPD_Disable();
					}*/
				} else if(controlName.equalsIgnoreCase(IS_SALARY_TRANSFER_CAT_CHANGE)||
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
						controlName.equalsIgnoreCase(IS_SALARY_TRANSFER_CAT_CHANGE)){
					if(formObject.getValue(IS_CAT_BENEFIT_OTHER).toString().equalsIgnoreCase("True")){
						logInfo("CPDMakerThreeStep","Click: INSIDE IS_CAT_BENEFIT_OTHER True");
						formObject.setValue(IS_CAT_BENEFIT_OTHER,"False");
						formObject.setValue(CAT_BENEFIT_OTHER,"");
						formObject.setStyle(CAT_BENEFIT_OTHER,DISABLE,TRUE);	
					}
				} else if(controlName.equalsIgnoreCase(HD_VISA_STATUS))
				{
					if(! returnVisaStatus().equalsIgnoreCase("Residency Visa"))
					{
						logInfo("CPDMakerThreeStep","Click: INSIDE COMBO34 Residency Visa");
						formObject.setStyle(DRP_RESEIDA,DISABLE,TRUE);
						formObject.setValue(DRP_RESEIDA,"--Select--");
					}
					else
					{
						formObject.setStyle("DRP_RESEIDA",DISABLE,FALSE);
					}
				} //COMBO34 Missing in excel MOKSH //can find in 119
				else if(controlName.equalsIgnoreCase(CP_CITY)||controlName.equalsIgnoreCase(PA_CITY)||
						controlName.equalsIgnoreCase(RA_CITY)){
					logInfo("CPDMakerThreeStep","Click: INSIDE TOGGLE CITY");
					manageCity(controlName);
				} else if(controlName.equalsIgnoreCase(PD_CUSTSEGMENT)) {
					logInfo("CPDMakerThreeStep","Click: INSIDE PD_CUSTSEGMENT");

					if(!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase(""))
					{
						logInfo("CPDMakerThreeStep","Click: INSIDE SELECTED_ROW_INDEX");
						int fieldToFocus=Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
						String bnk_relation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus,7);
						if(bnk_relation.equalsIgnoreCase("New"))
						{	
							String segment = (String) formObject.getValue(PD_CUSTSEGMENT);
							if(segment.equalsIgnoreCase("Signatory"))
							{
								if(!(((String) formObject.getValue("ACC_RELATION.ACC_RELATION")).equalsIgnoreCase("AUS")||formObject.getValue("ACC_RELATION.ACC_RELATION").toString().equalsIgnoreCase("POA")))  
								{
									//kdd
									sendMessageValuesList("","Signatory not allowed. Please select another type.");
									formObject.setValue(PD_CUSTSEGMENT,"");
									formObject.setValue("rm_code","");
									formObject.setValue("rm_name","");
								}
							}
						}
					} //to be changed Moksh
				} else if(controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_TYPE_MANUAL)
						|| controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_TYPE_FCR) ||
						controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_TYPE_EIDA)) {
					logInfo("CPDMakerThreeStep","Click: INSIDE TOGGLE PASSPORT");
					if(controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_TYPE_MANUAL))
						toggleCheckbox(controlName,CHECKBOX_PASSPORT_TYPE_FCR,CHECKBOX_PASSPORT_TYPE_EIDA);
					else if(controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_TYPE_EIDA))
						toggleCheckbox(controlName,CHECKBOX_PASSPORT_TYPE_FCR,CHECKBOX_PASSPORT_TYPE_MANUAL);
					else if(controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_TYPE_FCR))
						toggleCheckbox(controlName,CHECKBOX_PASSPORT_TYPE_MANUAL,CHECKBOX_PASSPORT_TYPE_EIDA);
					manageManualFields(CHECKBOX_PASSPORT_TYPE_MANUAL,MANUAL_PASSTYPE);
				} else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_STATUS_FCR) 
						|| controlName.equalsIgnoreCase(CHECKBOX_VISA_STATUS_EIDA)
						|| controlName.equalsIgnoreCase(CHECKBOX_VISA_STATUS_MANUAL)) {
					logInfo("CPDMakerThreeStep","Click: INSIDE TOGGLE VISA STATUS");
					if(controlName.equalsIgnoreCase(CHECKBOX_VISA_STATUS_FCR))
						toggleCheckbox(controlName,CHECKBOX_VISA_STATUS_EIDA,CHECKBOX_VISA_STATUS_MANUAL);
					else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_STATUS_MANUAL))
						toggleCheckbox(controlName,CHECKBOX_VISA_STATUS_EIDA,CHECKBOX_VISA_STATUS_FCR);
					else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_STATUS_EIDA))
						toggleCheckbox(controlName,CHECKBOX_VISA_STATUS_FCR,CHECKBOX_VISA_STATUS_MANUAL);
					manageManualFields(CHECKBOX_VISA_STATUS_MANUAL,MANUAL_VISASTATUS);
				}  else if(controlName.equalsIgnoreCase(EMP_STATUS) 
						&& !formObject.getValue(controlName).toString().equalsIgnoreCase("")){
					logInfo("CPDMakerThreeStep","Click: INSIDE EMP_STATUS");
					if(formObject.getValue(controlName).toString().equalsIgnoreCase("Employed")){	
						logInfo("CPDMakerThreeStep","Click: INSIDE EMP_STATUS Employed");
						formObject.setValue(RA_CB_GEN_TRDNG_CMPNY, "False");
						formObject.setValue(RA_CB_PRECIOUS_STONE_DEALER, "False");
						formObject.setValue(RA_CB_BULLN_COMMDTY_BROKR, "False");
						formObject.setValue(RA_CB_REAL_STATE_BROKR, "False");
						formObject.setValue(RA_CB_REAL_STATE_BROKR, "False");

						formObject.setStyle(SEC_FUND_EXP_RELTNSHP,DISABLE,TRUE);
						formObject.setStyle(ED_PERC_OF_OWNRSHP,DISABLE,TRUE);
						formObject.setValue(ED_NO_UAE_OVRS_BRNCH, "");
						formObject.setStyle(ED_NO_UAE_OVRS_BRNCH,DISABLE,TRUE);
						formObject.setStyle(ED_COMP_WEBSITE,DISABLE,TRUE);
						disableControls(BUSINESS_NATURE_SECTION);
						if(TRUE.equalsIgnoreCase(formObject.getValue(CHK_EMP_DETAIL).toString())) {
							logInfo("CPDMakerThreeStep","Click: CHK_EMP_DETAIL ED_EMP_TYPE");
							formObject.setStyle(ED_EMP_TYPE,DISABLE,FALSE);
							if(controlName.equalsIgnoreCase(ED_EMP_TYPE)){	
								if(formObject.getValue(ED_EMP_TYPE).toString().equalsIgnoreCase("ADCB")){
									formObject.setStyle(ED_SET_FLG,DISABLE,FALSE);
									formObject.setValue(ED_SET_FLG, "Yes");
								}else{
									formObject.setStyle(ED_SET_FLG,DISABLE,TRUE);
									formObject.setValue(ED_SET_FLG, "No");
								}
							}
						}
						if(TRUE.equalsIgnoreCase(formObject.getValue(CHK_RISK_ASS).toString())) {
							formObject.setStyle(RA_IS_CUST_WRKNG_UAE,DISABLE,FALSE);
							formObject.setStyle(RA_IS_CUST_WRKNG_NON_UAE,DISABLE,FALSE);
						}
					}else if(formObject.getValue(controlName).toString().equalsIgnoreCase("Self Employed")){
						logInfo("CPDMakerThreeStep","Click: INSIDE EMP_STATUS Self Employed");
						formObject.setValue(RA_IS_CUST_WRKNG_UAE,"Select");
						formObject.setValue(RA_IS_CUST_WRKNG_NON_UAE,"Select");
						formObject.setStyle(SEC_FUND_EXP_RELTNSHP,DISABLE,FALSE);
						formObject.setStyle(ED_PERC_OF_OWNRSHP,DISABLE,FALSE);
						formObject.setStyle(ED_NO_UAE_OVRS_BRNCH,DISABLE,FALSE);
						formObject.setStyle(ED_COMP_WEBSITE,DISABLE,FALSE);
						enableControls(BUSINESS_NATURE_SECTION);
						//formObject.setValue(ED_EMP_TYPE, "Select");
						formObject.setValue(ED_EMP_TYPE, "Select");
						formObject.setStyle(ED_EMP_TYPE,DISABLE,TRUE);
						formObject.setStyle(RA_IS_CUST_WRKNG_UAE,DISABLE,TRUE);
						formObject.setStyle(RA_IS_CUST_WRKNG_NON_UAE,DISABLE,TRUE);
					} else {
						formObject.setStyle(SEC_FUND_EXP_RELTNSHP,DISABLE,TRUE);
						formObject.setStyle(ED_PERC_OF_OWNRSHP,DISABLE,TRUE);
						formObject.setStyle(ED_NO_UAE_OVRS_BRNCH,DISABLE,TRUE);
						formObject.setStyle(ED_COMP_WEBSITE,DISABLE,TRUE);
						disableControls(BUSINESS_NATURE_SECTION);
						//formObject.setValue(ED_EMP_TYPE, "Select");
						formObject.setValue(ED_EMP_TYPE, "");
						formObject.setStyle(ED_EMP_TYPE,DISABLE,FALSE);
						formObject.setStyle(RA_IS_CUST_WRKNG_UAE,DISABLE,TRUE);
						formObject.setStyle(RA_IS_CUST_WRKNG_NON_UAE,DISABLE,TRUE);		
						formObject.setStyle(DEALS_IN_WMD,DISABLE,TRUE);		

						if(formObject.getValue(controlName).toString().equalsIgnoreCase("Salaried")) {
							logInfo("CPDMakerThreeStep","Click: INSIDE EMP_STATUS Salaried");
							formObject.setValue(ED_NO_UAE_OVRS_BRNCH, "");
							formObject.setStyle(ED_NO_UAE_OVRS_BRNCH,DISABLE,TRUE);
							formObject.setStyle(RA_IS_CUST_WRKNG_UAE,DISABLE,FALSE);
							formObject.setStyle(DEALS_IN_WMD,DISABLE,FALSE);
							formObject.setStyle(RA_IS_CUST_WRKNG_NON_UAE,DISABLE,FALSE);
							formObject.setValue(RA_CB_GEN_TRDNG_CMPNY, "False");
							formObject.setValue(RA_CB_PRECIOUS_STONE_DEALER, "False");
							formObject.setValue(RA_CB_BULLN_COMMDTY_BROKR, "False");
							formObject.setValue(RA_CB_REAL_STATE_BROKR, "False");
							formObject.setValue(RA_CB_REAL_STATE_BROKR, "False");
							disableControls(BUSINESS_NATURE_SECTION);
						}
					}
					/*if(!formObject.getValue(controlName).toString().equalsIgnoreCase("Self Employed")){
						logInfo("CPDMakerThreeStep","Click: INSIDE EMP_STATUS Self Employed");
						formObject.setStyle(RA_CB_OTHERS,DISABLE,TRUE);
						formObject.setValue(RA_CB_OTHERS, "False");
						formObject.setValue(RA_OTHERS, "");
						formObject.setStyle(RA_OTHERS,DISABLE,TRUE);					
					} else {
						logInfo("CPDMakerThreeStep","Click: INSIDE EMP_STATUS else");
						formObject.setStyle(RA_CB_OTHERS,DISABLE,FALSE);
						formObject.setValue(RA_CB_OTHERS, "False");
						formObject.setValue(RA_OTHERS, "");
						formObject.setStyle(RA_OTHERS,DISABLE,TRUE);
					}*/
					formObject.setValue(BN_OTHERS,"");
					formObject.setValue(RA_CB_OTHERS, FALSE);
					formObject.setStyle(BN_OTHERS, DISABLE, TRUE);
					if(!controlValue.equalsIgnoreCase("Self Employed")) {
						formObject.setStyle(RA_CB_OTHERS, DISABLE, TRUE);
					} else {
						formObject.setStyle(RA_CB_OTHERS, DISABLE, FALSE);
					}
				} 
				else if(controlName.equalsIgnoreCase(ED_CUST_CRS_BRDR_PAYMENT)) {
					if(formObject.getValue(controlName).toString().equalsIgnoreCase("Yes")){
						logInfo("CPDMakerThreeStep","Click: INSIDE ED_CUST_CRS_BRDR_PAYMENT yes");
						formObject.setStyle(ED_PURPSE_CRS_BRDR_PAYMENT,DISABLE,FALSE);
						formObject.setStyle(ED_ANTCPATD_CRS_BRDER_PYMT,DISABLE,FALSE);
						formObject.setStyle(ED_ANTCPATD_MNTHVAL_BRDER_PYMT,DISABLE,FALSE);
						formObject.setStyle(ED_CNTRY_PYMT_RECV,DISABLE,FALSE);
					} else {
						logInfo("CPDMakerThreeStep","Click: INSIDE ED_CUST_CRS_BRDR_PAYMENT else");
						formObject.setValue(ED_PURPSE_CRS_BRDR_PAYMENT, "");
						formObject.setValue(ED_ANTCPATD_CRS_BRDER_PYMT, "");
						formObject.setValue(ED_ANTCPATD_MNTHVAL_BRDER_PYMT, "");
						formObject.setValue(ED_CNTRY_PYMT_RECV, "");
						formObject.setStyle(ED_PURPSE_CRS_BRDR_PAYMENT,DISABLE,TRUE);
						formObject.setStyle(ED_ANTCPATD_CRS_BRDER_PYMT,DISABLE,TRUE);
						formObject.setStyle(ED_ANTCPATD_MNTHVAL_BRDER_PYMT,DISABLE,TRUE);
						formObject.setStyle(ED_CNTRY_PYMT_RECV,DISABLE,TRUE);
					}
				} else if(controlName.equalsIgnoreCase(RA_IS_UAE_RESIDENT)){
					if(formObject.getValue(controlName).toString().equalsIgnoreCase("No")){
						logInfo("CPDMakerThreeStep","Click: INSIDE RA_IS_UAE_RESIDENT No");
						formObject.setStyle(RA_RSN_BNKNG_UAE,DISABLE,FALSE);
					}else{
						logInfo("CPDMakerThreeStep","Click: INSIDE RA_IS_UAE_RESIDENT else");
						formObject.setStyle(RA_RSN_BNKNG_UAE,DISABLE,TRUE);
					}
				} else if(controlName.equalsIgnoreCase(RA_IS_CUST_PEP)){
					if(formObject.getValue(controlName).toString().equalsIgnoreCase("Yes")){
						logInfo("CPDMakerThreeStep","Click: INSIDE RA_IS_CUST_PEP Yes");
						formObject.setStyle(RA_LIST_OF_CUST_PEP,DISABLE,FALSE);
					} else {
						logInfo("CPDMakerThreeStep","Click: INSIDE RA_IS_CUST_PEP Yes");
						formObject.setStyle(RA_LIST_OF_CUST_PEP,DISABLE,TRUE);
					}
				} else if((formObject.getValue(MANUAL_VISANO).toString().equalsIgnoreCase("MDSA"))  
						&&(!( returnVisaStatus().equalsIgnoreCase("Under Processing")))&& (!mohit_flag)){
					logInfo("CPDMakerThreeStep","Click: before MDSA is null pop up");
					String updateQuery="UPDATE USR_0_CUST_TXN set VISA_NO='' WHERE cust_sno='"+getPrimaryCustomerSNO()+
							"' AND WI_NAME = '"+sWorkitemId+"'";
					formObject.saveDataInDB(updateQuery);
					logInfo("CPDMakerThreeStep","Click: INSIDE MANUAL_VISANO Update successful"+updateQuery);
					formObject.setValue(CHECKBOX_VISA_NO_MANUAL,"true");
					formObject.setValue(CHECKBOX_VISA_NO_FCR,"false");
					formObject.setValue(CHECKBOX_VISA_NO_EIDA,"false");
					formObject.setStyle(MANUAL_VISANO,DISABLE,FALSE);
					if(formObject.getValue(CHECKBOX_SELECTALL_MANUAL ).toString().equalsIgnoreCase("True")||
							formObject.getValue(CHECKBOX_VISA_NO_MANUAL).toString().equalsIgnoreCase("true")){
						formObject.setValue(MANUAL_VISANO,"");
						formObject.setValue(HD_VISA_NO,formObject.getValue(MANUAL_VISANO).toString());
					}
					sendMessageValuesList("",CA0172);
				} else if(controlName.equalsIgnoreCase(INDICIACOMBO)||controlName.equalsIgnoreCase(FAT_SSN)||
						controlName.equalsIgnoreCase(FAT_CUST_CLASSIFICATION)||controlName.equalsIgnoreCase(DATEPICKERCUST)||
						controlName.equalsIgnoreCase(DATEPICKERW8)||controlName.equalsIgnoreCase(COMBODOC)){    	   
					formObject.setValue(CHANGE_IN_FATCA_3WAY_INPUTS,"Yes");
					formObject.setValue(FATCAMAIN,"Yes");   
					if(formObject.getValue("CHECK95").toString().equalsIgnoreCase("true")){	//not in eclipse/excel moksh check value in old
						logInfo("CPDMakerThreeStep","Click: CHECK95 TRUE");
						formObject.setStyle(BTN_VALIDATEFATCA,DISABLE,FALSE);
						formObject.setStyle(COMBODOC,DISABLE,FALSE);
					}
					if(controlName.equalsIgnoreCase(COMBODOC) && 
							formObject.getValue(COMBODOC).toString().equalsIgnoreCase("W8BEN")){
						logInfo("CPDMakerThreeStep","Click: Combodoc is W8BEN");
						formObject.setValue(FAT_CUST_CLASSIFICATION,"NON-US PERSON");
					}
				}
				else if(controlName.equalsIgnoreCase(EMPLYR_NAME_FCR) || 
						controlName.equalsIgnoreCase(EIDA_EMPLYR_NAME) 
						|| controlName.equalsIgnoreCase(MANUAL_EMPLYR_NAME)) {
					String sIsFCREmpName = formObject.getValue(CHECKBOX_EMP_NAME_FCR).toString();
					String sIsEIDAEmpName = formObject.getValue(CHECKBOX_EMP_NAME_EIDA).toString();
					String sIsManualEmpName = formObject.getValue(CHECKBOX_EMP_NAME_MANUAL).toString();
					String sFCREmpName = formObject.getValue(EMPLYR_NAME_FCR).toString();
					String sEIDAEmpName = formObject.getValue(EIDA_EMPLYR_NAME).toString();
					String sManualEmpName = formObject.getValue(MANUAL_EMPLYR_NAME).toString();
					String sFinalEmpName = getFinalData(sIsFCREmpName,sIsEIDAEmpName,sIsManualEmpName,
							sFCREmpName,sEIDAEmpName,sManualEmpName);
					logInfo("CPDMakerThreeStep","Click: sFinalEmpName "+sFinalEmpName);

					if(formObject.getValue(EMP_STATUS).toString().equalsIgnoreCase("Select")){
						if(!sFinalEmpName.equalsIgnoreCase("")){
							logInfo("CPDMakerThreeStep","Click: EMP_STATUS Select");
							formObject.setValue(EMP_STATUS, "Employed");
						}
					}
				} else if(controlName.equalsIgnoreCase(FCR_RESIDENT) || controlName.equalsIgnoreCase(EIDA_RESIDENT) || 
						controlName.equalsIgnoreCase(MANUAL_RESIDENT) ||controlName.equalsIgnoreCase(FCR_NATIONALITY) ||
						controlName.equalsIgnoreCase(EIDA_NATIONALITY) || controlName.equalsIgnoreCase(MANUAL_NATIONALITY)) {					
					logInfo("CPDMakerThreeStep","Click: Setting Visa Status");
					manageResidencyAndVisaStatus(RA_IS_UAE_RESIDENT,"COMBO34");	//119
				}
				//				doubt moksh
				//				else if(controlName.equalsIgnoreCase("DOC_VIEW_CPD"))
				//				{
				//					String folderIndex= getDataFromGrid("doc_req_queue_cpd",9);
				//					String docIndex= getDataFromGrid("AO_doc_req_queue_cpd",10);
				//					JSObject objJStemp = formObject.getJSObject();
				//					logInfo("","Query:fold "+folderIndex+"#"+docIndex);	
				//					Object [] ain = {folderIndex+"#"+docIndex};
				//					logInfo("","ain :"+ ain[0].toString());
				//					objJStemp.call("attributeDetails",ain);  
				//				}
				//
				//				else if(controlName.equalsIgnoreCase(BTN_SELECT_CUST)) 
				//				{
				//					fillDuplicateData();
				//					formObject.RaiseEvent("WFSave");
				//				}
				else if(controlName.equalsIgnoreCase(DEL_MODE_YES)){
					logInfo("","In side DEL_MODE_YES");
					if(formObject.getValue(DEL_MODE_YES).toString().equalsIgnoreCase("True")){
						logInfo("","In side DEL_MODE_YES IF ");
						formObject.setStyle(NEW_DEL_MODE,DISABLE,FALSE);
						formObject.setStyle(CHANNEL_NAME,DISABLE,FALSE);
					}else{
						logInfo("","In side DEL_MODE_YES Else");
						formObject.setStyle(NEW_DEL_MODE,DISABLE,TRUE);
						formObject.setStyle(CHANNEL_NAME,DISABLE,TRUE);
					}
				} else if( controlName.equalsIgnoreCase(DEL_MODE_NO)){
					logInfo("","In side DEL_MODE_NO");
					if(formObject.getValue(DEL_MODE_NO).toString().equalsIgnoreCase("True")){
						logInfo("","In side DEL_MODE_NO IF ");
						formObject.setStyle(NEW_DEL_MODE, DISABLE,TRUE);
						formObject.setStyle(CHANNEL_NAME, DISABLE,TRUE);
					}else{
						logInfo("","In side DEL_MODE_NO Else");
						formObject.setStyle(NEW_DEL_MODE, DISABLE,FALSE);
						formObject.setStyle(CHANNEL_NAME, DISABLE,FALSE);
					}
				} else if(controlName.equalsIgnoreCase(NEW_DEL_MODE)) {
					if(formObject.getValue(NEW_DEL_MODE).toString().equalsIgnoreCase("Internal Courier")){
						formObject.clearCombo("channel_name");	//to check moksh
						formObject.addItemInCombo("channel_name", "Aramex");
					} else if(formObject.getValue(NEW_DEL_MODE).toString().equalsIgnoreCase("Branch")){
						String branch = "Select HOME_BRANCH from usr_0_home_branch order by 1";
					} else if(formObject.getValue(NEW_DEL_MODE).toString().equalsIgnoreCase("Excellency")){
						String excellency = "select sourcing_center from usr_0_sourcing_center where sourcing_channel ='Excellency' order by 1";
						//						formObject.getNGDataFromDataSource(excellency,1,"channel_name");
						//						addItemsDropDown("channel_name","channel_name");	tocheck moksh
					}else{
						formObject.clearCombo("channel_name"); 
					}
				} else if(controlName.equalsIgnoreCase(DEL_STATE)){
					if(formObject.getValue(DEL_STATE).toString().equalsIgnoreCase("Others")){
						formObject.setValue(DEL_STATE_OTHER, "");
						formObject.setStyle(DEL_STATE_OTHER,DISABLE,FALSE);
					} else {
						formObject.setValue(DEL_STATE_OTHER, "");
						formObject.setStyle(DEL_STATE_OTHER,DISABLE,TRUE);
					}
				} else if(controlName.equalsIgnoreCase(SI_DEB_ACC_NO)){
					fetchCurrency(formObject.getValue(controlName).toString());	
				}  else if(controlName.equalsIgnoreCase(CORR_STATE)) { 
					if(formObject.getValue(CORR_STATE).toString().equalsIgnoreCase("Others") && 
							formObject.getValue(CHK_CONTACT_DET).toString().equalsIgnoreCase("true")) {
						formObject.setStyle(CP_OTHERS,DISABLE,FALSE);
					} else {
						formObject.setStyle(CP_OTHERS,DISABLE,TRUE);
					}
				} else if(controlName.equalsIgnoreCase(RES_STATE)) {
					if(formObject.getValue(RES_STATE).toString().equalsIgnoreCase("Others")
							&& formObject.getValue(CHK_CONTACT_DET).toString().equalsIgnoreCase("true")){
						formObject.setStyle(RA_OTHERS,DISABLE,FALSE);//RA_OTHERS
					}else {
						formObject.setStyle(RA_OTHERS,DISABLE,TRUE);
					}
				} else if(controlName.equalsIgnoreCase(PERM_STATE)){
					if(formObject.getValue(PERM_STATE).toString().equalsIgnoreCase("Others") && 
							formObject.getValue(CHK_CONTACT_DET).toString().equalsIgnoreCase("true")){
						formObject.setStyle(PA_OTHERS,DISABLE,FALSE);// PA_OTHERS
					} else {
						formObject.setStyle(PA_OTHERS,DISABLE,TRUE);
					}
				} else if(controlName.equalsIgnoreCase(CUST_PREFIX)) {
					if(formObject.getValue(CUST_PREFIX).toString().equalsIgnoreCase("Others")) {
						formObject.setStyle(PD_OTHERS,DISABLE,FALSE);
					} else {
						formObject.setStyle(PD_OTHERS,DISABLE,TRUE);
					}
				}else if(controlName.equalsIgnoreCase(CRO_DEC)){
					if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")){
						formObject.setStyle(CRO_REJ_REASON,DISABLE,TRUE);
						formObject.setValue(CRO_REJ_REASON, "Select");
					} else{
						formObject.setStyle(CRO_REJ_REASON,DISABLE,FALSE);
					}
				} else if(controlName.equalsIgnoreCase(CRO_REJ_REASON)) {
					if(!formObject.getValue(CRO_REJ_REASON).toString().equalsIgnoreCase("Select")) {
						if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Select")){
							sendMessageValuesList(CRO_DEC,"Please select user decision first");
							formObject.setValue(CRO_REJ_REASON,"Select");
							if(!formObject.getValue(SCAN_MODE).toString().equalsIgnoreCase("New WMS ID")){
								formObject.setStyle(EDIT,DISABLE,FALSE);
							}
						}
					}
				} /*else if(controlName.equalsIgnoreCase(PD_CUSTSEGMENT)){
					manageInternalSection();
					//NGRepeater objChkRepeater = formObject.getNGRepeater("REPEAT_FRAME");
					int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());  //need to checked again
					String sAccRelation = formObject.getTableCellValue(ACC_RELATION, 0, 9);//objChkRepeater.getValue(fieldToFocus,"AO_ACC_RELATION.ACC_RELATION");
					String bnk_relation = formObject.getTableCellValue(ACC_RELATION, 0, 7);//objChkRepeater.getValue(fieldToFocus,"AO_ACC_RELATION.BANK_RELATION");
					//						doubt moksh how to handle

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
						}
						if(bnk_relation.equalsIgnoreCase("New")){	
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
								if(!(formObject.getValue("AO_ACC_RELATION.ACC_RELATION").toString().equalsIgnoreCase("AUS")||	//doubt moksh
										formObject.getValue("AO_ACC_RELATION.ACC_RELATION").toString().equalsIgnoreCase("POA"))){
									sendMessageValuesList("","Signatory not allowed. Please select another type.");	//enter data in "" moksh
									formObject.setValue(PD_CUSTSEGMENT,"Select");
									formObject.setValue(RM_CODE,"");
									formObject.setValue(RM_NAME,"");
								} else {
									getPromoCode("Signatory");	
									visibleOnSegmentBasis("Signatory");
								}
							}
							if(!segment.equalsIgnoreCase("")){
								formObject.setStyle(IDS_OTH_CB_OTHERS,DISABLE,FALSE);		
								formObject.setValue(IDS_OTH_CB_OTHERS,"False");
								formObject.setStyle(IDS_BNFT_CB_OTHERS,DISABLE,TRUE);
								formObject.setValue(IDS_BNFT_CB_OTHERS,"");
							} else {
								formObject.setStyle(IDS_OTH_CB_OTHERS,DISABLE,TRUE);
								formObject.setStyle(IDS_BNFT_CB_OTHERS,DISABLE,TRUE);
								formObject.setValue(IDS_OTH_CB_OTHERS,"False");
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

								formObject.setStyle("Label128",VISIBLE,TRUE);	//check lables moksh
								formObject.setStyle("Label134",VISIBLE,TRUE);
								formObject.setStyle("Label133",VISIBLE,TRUE);

								formObject.setStyle(PRO_CODE, DISABLE,TRUE);
								formObject.setStyle(EXCELLENCY_CNTR, DISABLE,TRUE);
							}
						}
						if(bnk_relation.equalsIgnoreCase("Existing")){
							String seg=formObject.getValue(PD_CUSTSEGMENT).toString();
							segmentSelectionForExistingcustomer(seg);	//to be made MOKSH
						} else if(bnk_relation.equalsIgnoreCase("New")){
							if(formObject.getValue(IDS_OTH_CB_OTHERS).toString().equalsIgnoreCase("False")){
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
				}*/
				//end of code created by Moksh
				//Nagasharan code start
				else if(controlName.equalsIgnoreCase(PD_ANY_CHNG_CUST_INFO)) {
					String sAccRelation= "";
					if(!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						int fieldToFocus=Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
						sAccRelation= formObject.getTableCellValue(ACC_RELATION,fieldToFocus,9);
					}
					manageCustomerDetailChange(formObject.getValue(PD_ANY_CHNG_CUST_INFO).toString());	
					manageFATCAFieldsEnable(formObject.getValue(PD_ANY_CHNG_CUST_INFO).toString(),sAccRelation);
					String segment = formObject.getValue(PD_CUSTSEGMENT).toString();
					getPromoCode(segment);
				} else if(controlName.equalsIgnoreCase(ED_CB_SAL_AED)) {
					formObject.setValue(ED_SAL_AED,formObject.getValue(ED_ANNUAL_INC).toString());
					if(formObject.getValue(ED_CB_SAL_AED).toString().equalsIgnoreCase(TRUE)){
						formObject.setStyle(ED_SAL_AED, DISABLE, FALSE);
					} else {
						formObject.setStyle(ED_SAL_AED, DISABLE, TRUE);
					}
				} else if(controlName.equalsIgnoreCase(BTN_VIEWDETAILS_SANCT)) {
					logInfo("BTN_VIEWDETAILS_SANCT","saving data");
					saveScreeningDataCPD();
					//NGRepeater objChkRepeater = formObject.getNGRepeater("REPEAT_FRAME");
					int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
					String sCustNo=formObject.getTableCellValue(ACC_RELATION,iSelectedRow,0);
					int sOutput=updateDataInDB("USR_0_BRMS_TRACKER","SCREENING_STATUS","'Success'","WI_NAME ='"+sWorkitemId+"' AND CUST_SNO='"+sCustNo+"' AND SCREENING_STATUS ='Pending'");
					logInfo("BTN_VIEWDETAILS_SANCT","sOutput----"+sOutput);
				}  else if(controlName.equalsIgnoreCase(MANUAL_CNTRY)) {
					manageCityStateManual(formObject.getValue(controlName).toString());
					formObject.setValue(CP_OTHERS,"");
					formObject.setValue(OTHER_CORR_CITY,"");
				} else if(controlName.equalsIgnoreCase(DEL_CNTRY)) {
					String sState = formObject.getValue(DEL_STATE).toString();
					logInfo("DEL_STATE","sState----"+sState);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase("UNITED ARAB EMIRATES")) {
						formObject.clearCombo(DEL_STATE);
						String[] states = UAESTATES.split(",");
						for(int i=0; i < states.length; i++) {
							formObject.addItemInCombo(DEL_STATE, states[i], states[i]);							
						}
						formObject.setValue(DEL_STATE,sState);
					}
					else if(!formObject.getValue(controlName).toString().equalsIgnoreCase("")) {
						formObject.clearCombo(DEL_STATE);
						formObject.addItemInCombo(DEL_STATE, OTHERTHENUAESTATES, OTHERTHENUAESTATES); //doubt
						formObject.setValue(DEL_STATE,sState);
					}
					if(formObject.getValue(DEL_STATE)==null) {
						formObject.setValue(DEL_STATE,"");
					}
				} else if(controlName.equalsIgnoreCase(RES_CNTRY)) {
					String sState = formObject.getValue(RES_STATE).toString();
//					String sCity = formObject.getValue(PA_CITY).toString();
					String sCity = formObject.getValue(RA_CITY).toString();
					logInfo("RES_CNTRY","sCity RES----"+sCity);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase("UNITED ARAB EMIRATES")) {
						formObject.clearCombo(RES_STATE);
						String[] states = UAESTATES.split(",");
						for(int i=0; i < states.length; i++) {
							formObject.addItemInCombo(RES_STATE, states[i], states[i]);							
						}
						formObject.setValue(RES_STATE,sState);
//						formObject.clearCombo(PA_CITY);
						formObject.clearCombo(RA_CITY);
						for(int i=0; i < states.length; i++) {
							formObject.addItemInCombo(RA_CITY, states[i], states[i]);							
						}
//						formObject.setValue(PA_CITY,sCity);
						formObject.setValue(RA_CITY,sCity);
						enableControls(new String[]{RES_MAKANI});
					} else {
						formObject.clearCombo(RES_STATE);
						formObject.addItemInCombo(RES_STATE, OTHERTHENUAESTATES,OTHERTHENUAESTATES);
						formObject.setValue(RES_STATE,sState);
						formObject.clearCombo(RA_CITY);
						logInfo("RES_CNTRY","Lict COunt---"+getListCount(RA_CITY));
						formObject.addItemInCombo(RA_CITY,"OTHERS");
						formObject.setValue(RA_CITY,sCity);
						disableControls(new String[]{RES_MAKANI});
					}
				} else if(controlName.equalsIgnoreCase(CORR_CNTRY)) {
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
				} else if(controlName.equalsIgnoreCase(PERM_CNTRY)) {
					String sState = formObject.getValue(PERM_STATE).toString();
					String sCity = formObject.getValue(PA_CITY).toString();
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
				} /* else if(controlName.equalsIgnoreCase("EmpName") && !controlValue.equalsIgnoreCase("")){
					List<List<String>> sOutput = formObject.getDataFromDB("SELECT CD_STATUS FROM "
							+ "USR_0_EMPLOYER_MASTER WHERE EMP_NAME ='"+controlValue+"'");
					logInfo("","Emp Mast Output---"+sOutput.get(0).get(0));
					formObject.setValue(ED_CB_TML,"False");
					formObject.setValue(ED_CB_NON_TML,"False");
					if(sOutput.get(0).get(0).equalsIgnoreCase("1")
							||sOutput.get(0).get(0).equalsIgnoreCase("2")){
						formObject.setValue(ED_CB_TML,"True");
					}else{
						formObject.setValue(ED_CB_NON_TML,"True");
					}
				}/*else if(controlName.equalsIgnoreCase("SRC_MAKER_BTN")) {    //from frontend
						String sQuery = "SELECT DSA_NAME, DSA_CODE FROM USR_0_DSA_DETAILS "
						+ "UNION SELECT PERSONALNAME||' '||FAMILYNAME,USERNAME FROM PDBUSER"
						+ " where deletedflag <>'Y' AND USERALIVE ='Y'";

						NGPickList objPickList = formObject.getNGPickList("AO_SOURCE_CODE","SOURCE NAME,SOURCE CODE",true);
						objPickList.setCacheEnable(true);
						objPickList.setSearchEnable(true);
						popupWindow thisPopup = new popupWindow(formObject,objPickList,sQuery,100,2,"AO_SOURCE_CODE");
						objPickList.addPickListListener(thisPopup);
						objPickList.setVisible(true);
				}else if(controlName.equalsIgnoreCase("SRC_CODE_BTN")){          
						NGPickList objPickList = formObject.getNGPickList("AO_SOURCE_NAME","SOURCE CODE,SOURCE NAME",true);
						String sQuery = "SELECT DSA_CODE,DSA_NAME FROM USR_0_DSA_DETAILS "
						+ "UNION SELECT USERNAME,PERSONALNAME||' '||FAMILYNAME FROM PDBUSER"
						+ " where deletedflag <>'Y' AND USERALIVE ='Y'";
						objPickList.setCacheEnable(true);
						objPickList.setSearchEnable(true);
						popupWindow thisPopup = new popupWindow(formObject,objPickList,sQuery,100,2,"AO_SOURCE_NAME");
						objPickList.addPickListListener(thisPopup);
						objPickList.setVisible(true);
				}*//*else if(controlName.equalsIgnoreCase("SRC_CODECC_BTN")) {          
						NGPickList objPickList = formObject.getNGPickList("AO_SOURCE_CODE_CAT_CHANGE","SOURCE CODE,SOURCE NAME",true);
						String sQuery = "SELECT DSA_CODE,DSA_NAME FROM USR_0_DSA_DETAILS UNION SELECT USERNAME,PERSONALNAME||' '||FAMILYNAME FROM PDBUSER where deletedflag <>'Y' AND USERALIVE ='Y'";

						objPickList.setCacheEnable(true);
						objPickList.setSearchEnable(true);
						popupWindow thisPopup = new popupWindow(formObject,objPickList,sQuery,100,2,"AO_SOURCE_CODE_CAT_CHANGE");
						objPickList.addPickListListener(thisPopup);
						objPickList.setVisible(true);
				}else if(controlName.equalsIgnoreCase("SRC_NAME_BTN")) {          
						NGPickList objPickList = formObject.getNGPickList("AO_SOURCE_NAME_CAT_CHANGE","SOURCE NAME,SOURCE CODE",true);
						String sQuery = "SELECT DSA_NAME, DSA_CODE FROM USR_0_DSA_DETAILS UNION SELECT PERSONALNAME||' '||FAMILYNAME,USERNAME FROM PDBUSER where deletedflag <>'Y' AND USERALIVE ='Y'";

						objPickList.setCacheEnable(true);
						objPickList.setSearchEnable(true);
						popupWindow thisPopup = new popupWindow(formObject,objPickList,sQuery,100,2,"AO_SOURCE_NAME_CAT_CHANGE");
						objPickList.addPickListListener(thisPopup);
						objPickList.setVisible(true);
				}*/else if( controlName.equalsIgnoreCase("STAFF_REFER_BTN")) {           //doubt
					/*NGPickList objPickList = formObject.getNGPickList("REF_BY_STAFF","SOURCE NAME,SOURCE CODE",true);
						String sQuery = "SELECT DSA_NAME,DSA_CODE FROM USR_0_DSA_DETAILS UNION SELECT PERSONALNAME||' '||FAMILYNAME,USERNAME FROM PDBUSER where deletedflag <>'Y'";

						objPickList.setCacheEnable(true);
						objPickList.setSearchEnable(true);
						popupWindow thisPopup = new popupWindow(formObject,objPickList,sQuery,100,2,"REF_BY_STAFF");
						objPickList.addPickListListener(thisPopup);
						objPickList.setVisible(true);*/
				}else if(controlName.equalsIgnoreCase(CRO_DEC)){
					String constrolValue =  formObject.getValue(CRO_DEC).toString();
					if(constrolValue.equalsIgnoreCase("Approve with Additional Approval") 
							|| constrolValue.equalsIgnoreCase("Send To Compliance")){
						formObject.setStyle(L1_APP_REQ,DISABLE,"false");
						formObject.setStyle(L2_APP_REQ,DISABLE,"false");
						formObject.setStyle(L3_APP_REQ,DISABLE,"false");
						formObject.setStyle(L3_APP_REQ,DISABLE,"false");
						if(formObject.getValue(FINAL_RISK_VAL).toString().equalsIgnoreCase("Increased Risk")){
							formObject.setValue(L1_APP_REQ,"true");
							formObject.setValue(L2_APP_REQ,"true");
							formObject.setValue(L3_APP_REQ,"true");
							formObject.setValue(L3_APP_REQ,"false");
						}else if(formObject.getValue(FINAL_RISK_VAL).toString().equalsIgnoreCase("UAE-PEP")){
							formObject.setValue(L1_APP_REQ,"true");
							formObject.setValue(L2_APP_REQ,"true");
							formObject.setValue(L3_APP_REQ,"true");
							formObject.setValue(L3_APP_REQ,"true");
						}else{
							formObject.setValue(L1_APP_REQ,"false");
							formObject.setValue(L2_APP_REQ,"false");
							formObject.setValue(L3_APP_REQ,"false");
							formObject.setValue(L3_APP_REQ,"false");
						}
						if(constrolValue.equalsIgnoreCase("Approve with Additional Approval")){
							formObject.setStyle(CRO_REJ_REASON,DISABLE,TRUE);
						} else{
							formObject.setStyle(CRO_REJ_REASON,DISABLE,FALSE);
						}
					}else{
						formObject.setValue(L1_APP_REQ,"false");
						formObject.setValue(L2_APP_REQ,"false");
						formObject.setValue(L3_APP_REQ,"false");
						formObject.setValue(L3_APP_REQ,"false");
						formObject.setStyle(L1_APP_REQ,DISABLE ,"true");
						formObject.setStyle(L2_APP_REQ,DISABLE ,"true");
						formObject.setStyle(L3_APP_REQ,DISABLE ,"true");
						formObject.setStyle(L3_APP_REQ,DISABLE ,"true");
					}
				} else if(controlName.equalsIgnoreCase(FAT_US_PERSON)){	
					if(controlValue.equalsIgnoreCase("Yes")){
						formObject.setValue(FAT_LIABLE_TO_PAY_TAX,"YES");
						formObject.setStyle(FAT_LIABLE_TO_PAY_TAX,DISABLE,"true");
					}else{
						formObject.setValue(FAT_LIABLE_TO_PAY_TAX,"");
						if(formObject.getValue("CHECK95").toString().equalsIgnoreCase("true")){
							formObject.setStyle(FAT_LIABLE_TO_PAY_TAX,DISABLE,"false");
						}
					}
				} else if((controlName.equalsIgnoreCase(FCR_COUNTRYBIRTH)) 
						|| (controlName.equalsIgnoreCase(EIDA_COUNTRYBIRTH))
						|| (controlName.equalsIgnoreCase(MANUAL_COUNTRYBIRTH))){
					String sFinalCountryOfBirth =getFinalDataComparison(CHECKBOX_COB_FCR,CHECKBOX_COB_EIDA,
							CHECKBOX_COB_MANUAL,FCR_COUNTRYBIRTH,EIDA_COUNTRYBIRTH,MANUAL_COUNTRYBIRTH); 
					logInfo("",sFinalCountryOfBirth+"sFinalCountryOfBirth inside country click");
					if(sFinalCountryOfBirth.equalsIgnoreCase("UNITED STATES")){
						logInfo("",sFinalCountryOfBirth+"united insoide inside country click firse");
						formObject.setStyle(COMBODOC,DISABLE,"false");
						formObject.clearCombo(COMBODOC);
						formObject.addItemInCombo(COMBODOC, "","");
						formObject.addItemInCombo(COMBODOC, "W8BEN","W8BEN");
						formObject.addItemInCombo(COMBODOC, "W9","W9");
					}else{
						logInfo("",sFinalCountryOfBirth+"else inside country click firse");
						formObject.setStyle(COMBODOC,DISABLE,"false");
						formObject.clearCombo(COMBODOC);
						formObject.setValue(FAT_CUST_CLASSIFICATION, "");
						formObject.addItemInCombo(COMBODOC, "");
						formObject.addItemInCombo(COMBODOC, "NA");
						formObject.addItemInCombo(COMBODOC, "W8BEN");
						formObject.addItemInCombo(COMBODOC, "W9");
					}
				}else if(controlName.equalsIgnoreCase(FAT_CUST_CLASSIFICATION) ){
					Calendar calendar = Calendar.getInstance();
					SimpleDateFormat simpledateformat = new SimpleDateFormat(DATEFORMAT);
					String scurrentDate = simpledateformat.format(calendar.getTime());
					formObject.setValue(DATEPICKERCUST, scurrentDate);
				}else if(controlName.equalsIgnoreCase("ETIHAD_NO") ){  //KEYPRESS  && event.equalsIgnoreCase("KeyPress") doubt
					//iEtihadValidate=0;  need  to be checked
					formObject.setValue(IS_ETIHAD,"0");
				}else if(controlName.equalsIgnoreCase(ED_EMP_TYPE)){	
					if(controlValue.equalsIgnoreCase("ADCB")){
						formObject.setStyle(ED_SET_FLG,DISABLE,"false");
						formObject.setValue(ED_SET_FLG, "Yes");
					}else{
						formObject.setStyle(ED_SET_FLG,DISABLE, "true");
						formObject.setValue(ED_SET_FLG, "No");
					}
				}else if(controlName.equalsIgnoreCase(NEW_CUST_SEGMENT) ){	
					if(formObject.getValue(NEW_CUST_SEGMENT).toString().equalsIgnoreCase("")){
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

						formObject.setStyle(IS_SALARY_TRANSFER_CAT_CHANGE,DISABLE,TRUE);
						formObject.setStyle(IS_MORTAGAGE_CAT_CHANGE, DISABLE,TRUE);
						formObject.setStyle(IS_INSURANCE_CAT_CHANGE, DISABLE,TRUE);
						formObject.setStyle(IS_TRB_CAT_CHANGE, DISABLE,TRUE);
						formObject.setStyle(IS_OTHERS_CAT_CHANGE, DISABLE,TRUE);
						formObject.setStyle(IS_VVIP, DISABLE,TRUE);

						formObject.setStyle(IS_PREVILEGE_TP_CAT_CHANGE, DISABLE,TRUE);
						formObject.setStyle(IS_ENTERTAINMENT_CAT_CHANGE, DISABLE,TRUE);
						formObject.setStyle(IS_SHOPPING_CAT_CHANGE, DISABLE,TRUE);
						formObject.setStyle(IS_SPORT_CAT_CHANGE, DISABLE,TRUE);
						formObject.setStyle(IS_TRAVEL_CAT_CHANGE,DISABLE,TRUE);
						formObject.setStyle(IS_EXCELLENCY_TP_CAT_CHANGE, DISABLE,TRUE);

						formObject.setValue(IS_CAT_BENEFIT_OTHER, "False");
						formObject.setValue(CAT_BENEFIT_OTHER, "");
						formObject.setStyle(IS_CAT_BENEFIT_OTHER,DISABLE,TRUE);
						formObject.setStyle(CAT_BENEFIT_OTHER, DISABLE,TRUE);

						formObject.setStyle(IS_SALARY_TRANSFER_CAT_CHANGE,VISIBLE,TRUE);
						formObject.setStyle(IS_MORTAGAGE_CAT_CHANGE, VISIBLE,TRUE);
						formObject.setStyle(IS_INSURANCE_CAT_CHANGE, VISIBLE,TRUE);
						formObject.setStyle(IS_TRB_CAT_CHANGE, VISIBLE,TRUE);
						formObject.setStyle(IS_OTHERS_CAT_CHANGE, VISIBLE,TRUE);
						formObject.setStyle(IS_VVIP, VISIBLE,TRUE);

						formObject.setStyle(IS_PREVILEGE_TP_CAT_CHANGE, VISIBLE,TRUE);
						formObject.setStyle(IS_ENTERTAINMENT_CAT_CHANGE, VISIBLE,TRUE);
						formObject.setStyle(IS_SHOPPING_CAT_CHANGE, VISIBLE,TRUE);
						formObject.setStyle(IS_SPORT_CAT_CHANGE, VISIBLE,TRUE);
						formObject.setStyle(IS_TRAVEL_CAT_CHANGE,VISIBLE,TRUE);
						formObject.setStyle(IS_EXCELLENCY_TP_CAT_CHANGE, VISIBLE,TRUE);

						formObject.setStyle(IS_CAT_BENEFIT_OTHER,VISIBLE,TRUE);
						formObject.setStyle(CAT_BENEFIT_OTHER, VISIBLE,TRUE);

												
					}
					else{
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
						formObject.setStyle(IS_MORTAGAGE_CAT_CHANGE, DISABLE,FALSE);
						formObject.setStyle(IS_INSURANCE_CAT_CHANGE, DISABLE,FALSE);
						formObject.setStyle(IS_TRB_CAT_CHANGE, DISABLE,FALSE);
						formObject.setStyle(IS_OTHERS_CAT_CHANGE, DISABLE,FALSE);
						formObject.setStyle(IS_VVIP, DISABLE,FALSE);

						formObject.setStyle(IS_PREVILEGE_TP_CAT_CHANGE, DISABLE,FALSE);
						formObject.setStyle(IS_ENTERTAINMENT_CAT_CHANGE, DISABLE,FALSE);
						formObject.setStyle(IS_SHOPPING_CAT_CHANGE, DISABLE,FALSE);
						formObject.setStyle(IS_SPORT_CAT_CHANGE, DISABLE,FALSE);
						formObject.setStyle(IS_TRAVEL_CAT_CHANGE,DISABLE,FALSE);
						formObject.setStyle(IS_EXCELLENCY_TP_CAT_CHANGE, DISABLE,FALSE);

						formObject.setStyle(IS_CAT_BENEFIT_OTHER,DISABLE,FALSE);
						formObject.setStyle(CAT_BENEFIT_OTHER, DISABLE,FALSE);
					}
					manageCategoryChangeSection();
					if(!formObject.getValue(NEW_CUST_SEGMENT).toString().equalsIgnoreCase("")){
						formObject.setStyle(IS_CAT_BENEFIT_OTHER,DISABLE,FALSE);		
						formObject.setValue(IS_CAT_BENEFIT_OTHER,"False");
						formObject.setStyle(CAT_BENEFIT_OTHER,DISABLE,FALSE);
						formObject.setValue(CAT_BENEFIT_OTHER,"");
					}
					else{
						formObject.clearCombo(PROMO_CODE_CAT_CHANGE);
						formObject.addItemInCombo(PROMO_CODE_CAT_CHANGE,"","");
						//formObject.setNGListIndex(PROMO_CODE_CAT_CHANGE, 0);	doubt
						formObject.clearCombo(EXCELLENCY_CENTER_CC);
						formObject.addItemInCombo(EXCELLENCY_CENTER_CC,"","");
						//formObject.setNGListIndex(EXCELLENCY_CENTER_CC, 0);	

					}
					clearFBData();	//Family Banking
				}else if(controlName.equalsIgnoreCase(DEL_DELIVERY_MODE)){ 
					if(!controlValue.equalsIgnoreCase("Collect")){
						formObject.setValue(INSTANT_DEL_YES, "False");
						formObject.setValue(INSTANT_DEL_NO, "False");
						formObject.setValue(NOM_REQ, "");
						formObject.setValue(EXISTING_NOM_PRSN, "");
						formObject.setStyle(NOM_REQ,DISABLE,TRUE);
						setMailingAddInToDel();
					}
					LoadInstantBranchValue();
				} else if(controlName.equalsIgnoreCase(DEL_EXCELLENCY_CNTR)){
					if(formObject.getValue(DEL_EXCELLENCY_CNTR).toString().equalsIgnoreCase(TRUE)){
						logInfo("executeServerEvent:","DEL_EXCELLENCY_CNTR:TRUE");
						formObject.setStyle(DEL_BRNCH_NAME, DISABLE, TRUE);
						formObject.setStyle(EXCELLENCY_CENTER, DISABLE, FALSE);
					}
					else if(formObject.getValue(DEL_EXCELLENCY_CNTR).toString().equalsIgnoreCase(FALSE)){
						logInfo("executeServerEvent:","DEL_EXCELLENCY_CNTR:FALSE");
						formObject.setStyle(DEL_BRNCH_NAME, DISABLE, FALSE);
						formObject.setStyle(EXCELLENCY_CENTER, DISABLE, TRUE);
					}
				} else if(controlName.equalsIgnoreCase(DEL_EXCELLENCY_CNTR)){
					toggleCheckbox_2(controlName,DEL_BRNCH_COURIER);
				} else if(controlName.equalsIgnoreCase(DEL_BRNCH_COURIER)){
					logInfo("-------------DEL_BRNCH_COURIER--------111-----","");
					if(formObject.getValue(DEL_BRNCH_COURIER).toString().equalsIgnoreCase(TRUE)){
						logInfo("executeServerEvent:","DEL_BRNCH_COURIER:TRUE");
						formObject.setStyle(DEL_BRNCH_NAME, DISABLE, FALSE);
						formObject.setStyle(EXCELLENCY_CENTER, DISABLE, TRUE);
					}
					else if( formObject.getValue(DEL_BRNCH_COURIER).toString().equalsIgnoreCase(FALSE)){
						logInfo("executeServerEvent:","DEL_BRNCH_COURIER:FALSE");
						formObject.setStyle(DEL_BRNCH_NAME, DISABLE, TRUE);
						formObject.setStyle(EXCELLENCY_CENTER, DISABLE, FALSE);
					}
				} else if(controlName.equalsIgnoreCase(PERM_STATE)){
					if(formObject.getValue(PERM_STATE).toString().equalsIgnoreCase("Others")){
						formObject.setStyle(PA_OTHERS, DISABLE, FALSE);
					}else{
						formObject.setStyle(PA_OTHERS, DISABLE, TRUE);
					}
				} else if(controlName.equalsIgnoreCase(RES_STATE)){
					if(formObject.getValue(RES_STATE).toString().equalsIgnoreCase("Others")){
						formObject.setStyle(RA_OTHERS, DISABLE, FALSE);
					}else{
						formObject.setStyle(RA_OTHERS, DISABLE, TRUE);
					}
				} else if(controlName.equalsIgnoreCase(EMPNAME)){
					if(formObject.getValue(EMPNAME).toString().equalsIgnoreCase("Others")){
						formObject.setStyle(ED_EMPNAME,DISABLE, FALSE);//Rakshita text83=ED_EMPNAME
					}else{
						formObject.setStyle(ED_EMPNAME,DISABLE, TRUE);
					}
				} else if(controlName.equalsIgnoreCase(ED_CB_INVSTMNT_RETN_AED)){
					if(formObject.getValue(ED_CB_INVSTMNT_RETN_AED).toString().equalsIgnoreCase(TRUE)){
						formObject.setStyle(ED_INVSTMNT_RETN_AED, DISABLE, FALSE);
					}else{
						formObject.setStyle(ED_INVSTMNT_RETN_AED, DISABLE, TRUE);
					}
				} else if(controlName.equalsIgnoreCase(ED_CB_INHT_AED)){
					if(formObject.getValue(ED_CB_INHT_AED).toString().equalsIgnoreCase(TRUE))
					{
						formObject.setStyle(ED_INHT_AED, DISABLE, FALSE);
					}else{
						formObject.setStyle(ED_INHT_AED, DISABLE, TRUE);
					}
				} else if(controlName.equalsIgnoreCase(ED_CB_REAL_INC_AED)){
					if(formObject.getValue(ED_CB_REAL_INC_AED).toString().equalsIgnoreCase(TRUE)){
						formObject.setStyle(ED_REAL_INC_AED, DISABLE, FALSE);
					}else{
						formObject.setStyle(ED_REAL_INC_AED, DISABLE,TRUE);
					}
				} else if(controlName.equalsIgnoreCase(ED_CB_SALE_OF_ASST)){
					if(formObject.getValue(ED_CB_SALE_OF_ASST).toString().equalsIgnoreCase(TRUE)){
						formObject.setStyle(ED_SALE_OF_ASST, DISABLE, FALSE);
					}else{
						formObject.setStyle(ED_SALE_OF_ASST, DISABLE, TRUE);
					}
				} else if(controlName.equalsIgnoreCase(ED_CB_OTHERS)){
					if(formObject.getValue(ED_CB_OTHERS).toString().equalsIgnoreCase(TRUE)){
						formObject.setStyle(ED_OTHERS, DISABLE, FALSE);
					}else{
						formObject.setStyle(ED_OTHERS, DISABLE, TRUE);
					}
				} else if(controlName.equalsIgnoreCase(EXISTING_ETIHAD_CUST)){
					if(formObject.getValue(controlName).toString().toString().equalsIgnoreCase("Yes"))
					{
						formObject.setStyle(ETIHAD_NO, DISABLE, FALSE);
						formObject.setStyle(BTN_ECD_VALIDATE, DISABLE, FALSE);
					}else{
						formObject.setStyle(ETIHAD_NO, DISABLE, TRUE);
						formObject.setStyle(BTN_ECD_VALIDATE, DISABLE, TRUE);
					}
				}  else if(controlName.equalsIgnoreCase(DEL_PREF_RADIO)){
					if(formObject.getValue(controlName).toString().equalsIgnoreCase("Yes")) {
						logInfo("onChangeEventCPDMakerThreeStep","In side Yes");
						formObject.setStyle(NEW_DEL_MODE,DISABLE,FALSE);
						formObject.setStyle(CHANNEL_NAME,DISABLE,FALSE);
					}else if(formObject.getValue(controlName).toString().equalsIgnoreCase("No")){
						logInfo("onChangeEventCPDMakerthreeStep","In side DEL_PREF_RADIO IF ");
						formObject.setStyle(NEW_DEL_MODE,DISABLE,TRUE);
						formObject.setStyle(CHANNEL_NAME,DISABLE,TRUE);
					}
				}  else if(controlName.equalsIgnoreCase(PERM_STATE)){
					if(formObject.getValue(PERM_STATE).toString().equalsIgnoreCase("Others")){
						formObject.setStyle(PA_OTHERS, DISABLE, FALSE);
						//formObject.setNGLocked("TEXT54",true);
					}else{
						formObject.setStyle(PA_OTHERS, DISABLE, FALSE);
						//formObject.setNGLocked("TEXT54",false);
					}
				} else if(controlName.equalsIgnoreCase(RES_STATE)){
					if(formObject.getValue(RES_STATE).toString().equalsIgnoreCase("Others")){
						formObject.setStyle("TEXT95", DISABLE, FALSE);
					}else{
						formObject.setStyle("TEXT95", DISABLE, TRUE);
					}
				} else if(controlName.equalsIgnoreCase(PROFESION)){
					if(formObject.getValue(PROFESION).toString().equalsIgnoreCase("Others")){
						formObject.setStyle(ED_OTHER,DISABLE,FALSE);//Rakshita text82=ED_OTHER
					}else{
						formObject.setStyle(ED_OTHER,DISABLE, TRUE);
					}
				} else if(controlName.equalsIgnoreCase(EMPNAME)){
					if(formObject.getValue(EMPNAME).toString().equalsIgnoreCase("Others")){
						formObject.setStyle(ED_EMPNAME,DISABLE, FALSE);//Rakshita text83=ED_EMPNAME
					}else{
						formObject.setStyle(ED_EMPNAME,DISABLE, TRUE);
					}
				} else if(controlName.equalsIgnoreCase(ED_CB_INVSTMNT_RETN_AED)){
					if(formObject.getValue(ED_CB_INVSTMNT_RETN_AED).toString().equalsIgnoreCase(TRUE)){
						formObject.setStyle(ED_INVSTMNT_RETN_AED, DISABLE, FALSE);
					}else{
						formObject.setStyle(ED_INVSTMNT_RETN_AED, DISABLE, TRUE);
					}
				} else if(controlName.equalsIgnoreCase(ED_CB_INHT_AED)){
					if(formObject.getValue(ED_CB_INHT_AED).toString().equalsIgnoreCase(TRUE))
					{
						formObject.setStyle(ED_INHT_AED, DISABLE, FALSE);
					}else{
						formObject.setStyle(ED_INHT_AED, DISABLE, TRUE);
					}
				} else if(controlName.equalsIgnoreCase(ED_CB_REAL_INC_AED)){
					if(formObject.getValue(ED_CB_REAL_INC_AED).toString().equalsIgnoreCase(TRUE)){
						formObject.setStyle(ED_REAL_INC_AED, DISABLE, FALSE);
					}else{
						formObject.setStyle(ED_REAL_INC_AED, DISABLE,TRUE);
					}
				} else if(controlName.equalsIgnoreCase(ED_CB_SALE_OF_ASST)){
					if(formObject.getValue(ED_CB_SALE_OF_ASST).toString().equalsIgnoreCase(TRUE)){
						formObject.setStyle(ED_SALE_OF_ASST, DISABLE, FALSE);
					}else{
						formObject.setStyle(ED_SALE_OF_ASST, DISABLE, TRUE);
					}
				} else if(controlName.equalsIgnoreCase(ED_CB_OTHERS)){
					if(formObject.getValue(ED_CB_OTHERS).toString().equalsIgnoreCase(TRUE)){
						formObject.setStyle(ED_OTHERS, DISABLE, FALSE);
					}else{
						formObject.setStyle(ED_OTHERS, DISABLE, TRUE);
					}
				} else if(controlName.equalsIgnoreCase(EXISTING_ETIHAD_CUST)){
					if(formObject.getValue(controlName).toString().toString().equalsIgnoreCase("Yes"))
					{
						formObject.setStyle(ETIHAD_NO, DISABLE, FALSE);
						formObject.setStyle(BTN_ECD_VALIDATE, DISABLE, FALSE);
					}else{
						formObject.setStyle(ETIHAD_NO, DISABLE, TRUE);
						formObject.setStyle(BTN_ECD_VALIDATE, DISABLE, TRUE);
					}
				}else if(controlName.equalsIgnoreCase(PD_CUSTSEGMENT) 
						&& !formObject.getValue(controlName).toString().equalsIgnoreCase("")) {
					int fieldToFocus=Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
					String bankRelation = formObject.getTableCellValue(ACC_RELATION, fieldToFocus,7);//bank relation = 7
					String reqType= formObject.getValue(REQUEST_TYPE).toString();
					if(bankRelation.equalsIgnoreCase("New") && !(reqType.equalsIgnoreCase("New Account with Category Change"))) {
						String segment = formObject.getValue(PD_CUSTSEGMENT).toString();
						String rmCode = formObject.getValue(RM_CODE).toString();
						if(segment.equalsIgnoreCase("Aspire") && rmCode.equalsIgnoreCase("")) {
						} else if(segment.equalsIgnoreCase("Privilege") || segment.equalsIgnoreCase("Emirati")) {
							//commneted by sahil for the issue	logInfo("Inside Click Event","INSIDE Combo4");
							//commneted by sahil formObject.setValue(RM_CODE,"");
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
				} 
				if(controlName.equalsIgnoreCase(EMP_STATUS) && 
						!(formObject.getValue(EMP_STATUS).toString().equalsIgnoreCase("Employed"))){
					formObject.setValue("SALARY_TRANSFER","No");
				} else if(controlName.equalsIgnoreCase("EMP_ADD_CITY")) {
					if(formObject.getValue("EMP_ADD_CITY").toString().equalsIgnoreCase("Others")) {
						formObject.setStyle("EMP_ADD_CITY",DISABLE,FALSE);
					} else {
						formObject.setStyle("EMP_ADD_CITY",DISABLE,TRUE);
					}
				} else if(controlName.equalsIgnoreCase("EMP_ADD_EMIRATES")) {
					if(formObject.getValue("EMP_ADD_EMIRATES").toString().equalsIgnoreCase("Others")){
						formObject.setStyle("EMP_ADD_EMIRATES_OTHERS",DISABLE,FALSE);
					} else {
						formObject.setStyle("EMP_ADD_EMIRATES_OTHERS",DISABLE,TRUE);
					}
				} else if(controlName.equalsIgnoreCase("EMP_ADD_STATE")) {
					if(formObject.getValue("EMP_ADD_STATE").toString().equalsIgnoreCase("Others")){
						formObject.setStyle("STATE_OTHERS",DISABLE,FALSE);
					}
				} else if(controlName.equalsIgnoreCase(CRS_TAX_COUNTRY)) {
					if(formObject.getValue(CRS_TAX_COUNTRY).toString().equalsIgnoreCase("Yes")) {
						formObject.setStyle(PER_INC_TAX_CON_1,DISABLE,FALSE); 
					} else if(formObject.getValue(CRS_TAX_COUNTRY).toString().equalsIgnoreCase("No")) {
						formObject.setStyle(PER_INC_TAX_CON_1,DISABLE,TRUE);
						formObject.setStyle(PER_INC_TAX_CON_2,DISABLE,TRUE);
						formObject.setStyle(PER_INC_TAX_CON_3,DISABLE,TRUE);
						formObject.setValue(PER_INC_TAX_CON_1,"");
						formObject.setValue(PER_INC_TAX_CON_2,"");
						formObject.setValue(PER_INC_TAX_CON_3,"");
					}
				} else if(controlName.equalsIgnoreCase(PER_INC_TAX_CON_1)) {
					checkforDuplicateCountries(PER_INC_TAX_CON_1);
				} else if(controlName.equalsIgnoreCase(PER_INC_TAX_CON_2)) {
					checkforDuplicateCountries(PER_INC_TAX_CON_2);
				} else if(controlName.equalsIgnoreCase(PER_INC_TAX_CON_3)) {
					checkforDuplicateCountries(PER_INC_TAX_CON_3);
				}else if(controlName.equalsIgnoreCase(PER_INC_TAX_CON_1) ){
					System.out.println("Country 1 Selected........Sourav");
					return getReturnMessage(checkforDuplicateCountries(PER_INC_TAX_CON_1));
				}else if (controlName.equalsIgnoreCase(PER_INC_TAX_CON_2) ){
					System.out.println("Country 2 Selected........Sourav");
					return getReturnMessage(checkforDuplicateCountries(PER_INC_TAX_CON_2));
				}else if (controlName.equalsIgnoreCase(PER_INC_TAX_CON_3)){
					System.out.println("Country 3 Selected........Sourav");
					return getReturnMessage(checkforDuplicateCountries(PER_INC_TAX_CON_3));	   
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
				if(controlName.equalsIgnoreCase(CHECKBOX_NATIONALITY_FCR)||controlName.equalsIgnoreCase(FCR_NATIONALITY)||
						controlName.equalsIgnoreCase(CHECKBOX_NATIONALITY_EIDA)||controlName.equalsIgnoreCase(CHECKBOX_NATIONALITY_MANUAL)||
						controlName.equalsIgnoreCase(EIDA_NATIONALITY)||controlName.equalsIgnoreCase(MANUAL_NATIONALITY)||
						controlName.equalsIgnoreCase(CHECKBOX_CNTRY_OF_CORR_FCR)||controlName.equalsIgnoreCase(FCR_CNTRY)||
						controlName.equalsIgnoreCase(CHECKBOX_CNTRY_OF_CORR_EIDA)||controlName.equalsIgnoreCase(CHECKBOX_CNTRY_OF_CORR_MANUAL )||
						controlName.equalsIgnoreCase(EIDA_PER_CNTRY)||controlName.equalsIgnoreCase(MANUAL_CNTRY)||
						controlName.equalsIgnoreCase(CHECKBOX_TELE_RES_FCR)||controlName.equalsIgnoreCase(FCR_PH)||
						controlName.equalsIgnoreCase(CHECKBOX_TELE_RES_EIDA)||controlName.equalsIgnoreCase(CHECKBOX_TELE_RES_MANUAL)||
						controlName.equalsIgnoreCase(EIDA_PH)||controlName.equalsIgnoreCase(MANUAL_PH)||
						controlName.equalsIgnoreCase(CHECKBOX_TELE_MOB_FCR)||controlName.equalsIgnoreCase(FCR_MOBILE)||
						controlName.equalsIgnoreCase(CHECKBOX_TELE_MOB_EIDA)||controlName.equalsIgnoreCase( CHECKBOX_TELE_MOB_MANUAL )||
						controlName.equalsIgnoreCase(EIDA_MOBILE)||controlName.equalsIgnoreCase(MANUAL_MOBILE)||
						controlName.equalsIgnoreCase(CHECKFCR)||controlName.equalsIgnoreCase(FCR_RESIDENT)||
						controlName.equalsIgnoreCase(CHECKEIDA)||controlName.equalsIgnoreCase( CHECKMANUAL )||
						controlName.equalsIgnoreCase(EIDA_RESIDENT)||controlName.equalsIgnoreCase( MANUAL_RESIDENT )||
						controlName.equalsIgnoreCase(FAT_US_PERSON )||controlName.equalsIgnoreCase( FAT_LIABLE_TO_PAY_TAX )||
						controlName.equalsIgnoreCase(CNTRY_OF_BIRTH )||controlName.equalsIgnoreCase( POACOMBO )||
						controlName.equalsIgnoreCase(INDICIACOMBO )||controlName.equalsIgnoreCase( FAT_SSN )||
						controlName.equalsIgnoreCase(FAT_CUST_CLASSIFICATION )||controlName.equalsIgnoreCase( DATEPICKERCUST )||
						controlName.equalsIgnoreCase(DATEPICKERW8 )||controlName.equalsIgnoreCase( COMBODOC )||
						controlName.equalsIgnoreCase(CHECKBOX_COB_FCR )||controlName.equalsIgnoreCase( CHECKBOX_COB_EIDA )||
						controlName.equalsIgnoreCase(CHECKBOX_COB_MANUAL )||controlName.equalsIgnoreCase( FCR_COUNTRYBIRTH )||
						controlName.equalsIgnoreCase(EIDA_COUNTRYBIRTH )
						||controlName.equalsIgnoreCase( MANUAL_COUNTRYBIRTH )){
					if(!formObject.getValue(SELECTED_ROW_INDEX).toString().isEmpty()) {
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION, fieldToFocus, 7);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
					if(controlName.equalsIgnoreCase(MANUAL_MOBILE) &&
							"United Arab Emirates".equalsIgnoreCase(checkCountry())) {
						logInfo("MANUAL_MOBILE_CHANGE", "UAE resident");
						flag_mobile=true;
						mobileChangeFlag = true;
						String sNumber=formObject.getValue(MANUAL_MOBILE).toString();
						if(!(sNumber.startsWith("971") || sNumber.startsWith("+971") || sNumber.startsWith("00971"))) {
							sendMessageValuesList("","Mobile Number does not start with 971");
						}
					}
				}
			}
			else if (eventType.equalsIgnoreCase(EVENT_TYPE_LOSTFOCUS)){
				setAutoFilledFieldsLocked();
				if(controlName.equalsIgnoreCase(MANUAL_DOB)){
					logInfo("","In dob Manual----"+formObject.getValue(CHECKBOX_DOB_MANUAL));
					if(formObject.getValue(CHECKBOX_DOB_MANUAL).toString().equalsIgnoreCase("True")){
						logInfo("","In dob Manual");
						formObject.setValue("MASK2",formObject.getValue(controlName).toString());
						logInfo("","In dob Manual Changed");
					}
				} else if(controlName.equalsIgnoreCase("PASSPORTISSDATE_MANUAL")){
					formObject.setValue(HD_PASS_ISS_DATE,formObject.getValue(controlName).toString());
				} else if(controlName.equalsIgnoreCase("PASSPORTEXPDATE_MANUAL")){
					formObject.setValue(HD_PASS_EXP_DATE,formObject.getValue(controlName).toString());
				} else if(controlName.equalsIgnoreCase("VISAISSDATE_MANUAL")){
					formObject.setValue(HD_VISA_ISSUE_DATE,formObject.getValue(controlName).toString());
				} else if(controlName.equalsIgnoreCase("VISAEXPDATE_MANUAL")) {
					formObject.setValue(HD_EXP_DATE,formObject.getValue(controlName).toString());
				} else if(controlName.equalsIgnoreCase("AO_PRODUCT_QUEUE.PROD_CODE")
				 ||controlName.equalsIgnoreCase("AO_PRODUCT_QUEUE.CURRENCY")){
					
				}
				else if(controlName.equalsIgnoreCase(ED_MONTHLY_INCM)) {
					String mnthsalary = formObject.getValue(ED_MONTHLY_INCM).toString();
					logInfo("","In side salary "+mnthsalary);
					try {
						long mnthslry = Long.parseLong(mnthsalary);
						logInfo("","In side salary "+mnthslry);
						long finalsalary=mnthslry*12;	
						formObject.setValue(ED_ANNUAL_INC,finalsalary+"");
						logInfo("","In side salary "+finalsalary);
					} catch(Exception e) {
						logError("Exception",e);
					}
				}
				//end of code by NAGASHARAN
			} else if("handlingJSPData".equalsIgnoreCase(eventType)) {
				  logInfo("handlingJSPData","controlName: "+controlName);
				  if(controlName.equalsIgnoreCase(BTN_PRD_SEARCH)) {
						searchProductList("USR_0_PRODUCT_OFFERED_CPD", data);
				  } 
			}
			logInfo("","eventType: "+eventType+",controlName: "+controlName);
		} catch (Exception e) {
			logError("Exception in Event- " + eventType + "control name- " +controlName+ ": ", e);
		} finally{
			logInfo("executeServerEvent", "sendMessageList");
			if(!sendMessageList.isEmpty()){
				if(sendMessageList.get(1).contains(" does not start with 971") ||
						sendMessageList.get(1).equalsIgnoreCase("Mobile number is not of 12 digits") || 
						sendMessageList.get(1).equalsIgnoreCase("Residence Telephone Number is not of 11 digits")) {
					return getReturnMessage(true,controlName,sendMessageList.get(0)+"###"
							+ sendMessageList.get(1));
				}
				return  getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1).toString());
			}
		}
		return getReturnMessage(true, controlName);
    }
	
	private void accinfoUdflist(String controlName) {  //ACCOUNT INFO SAVE AND NECT BUTTON
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
//					formObject.setValue(ACC_INFO_UDF_VALUE, "");
					
				}
			} else {
				sendMessageValuesList("table91_UDF Field",formObject.getValue("table91_UDF Field").toString()
						+ " field already exist");
			}
		}
	}
	
	private String siAdd () {
		// TODO Auto-generated method stub

		logInfo("siAdd","Inside");
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
	
	}

	private String siModd() {
		logInfo("siModd","In Command MODIFY");
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
		
	
		// TODO Auto-generated method stub
		
	}

	private void dfAvail() {
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
		
	}

	private void onClickEventCPDMakerFourStep(String controlName,
			String eventType, String data) {
		// TODO Auto-generated method stub
		
	}

	private void onLoadCPDMakerThreeStep(String controlName,String  eventType,String data) {
		String decision = "";
		String sCompDec = "";
		Map mp = new HashMap();
		populateAuditSearch(SEARCH_DETAILS_LVW);
		try{  
			logInfo("CPDMakerThreeStep","Inside executeServerEvent QDECustAccountInfo >");
			//formObject.setStyle(BTN_APP_LEVEL_RISK, DISABLE, FALSE);
			//populateCPDCombos();	//chech queris if used in form or not moksh
//			loadDecisionHistory();
			loadSICombos();
			//uaePassWILinkForCPD();
			linkedUAEPassWI = getUaePassWILinkForCPD();
			compareAccRelationData(linkedUAEPassWI);
//			populateUAEPassInfoStatus(sWorkitemId);
			updateAccRelGridFromUaePassInfo(sWorkitemId);
			
			logInfo("CPDMakerThreeStep", "linkedUAEPassWI" + linkedUAEPassWI);
//			if(linkedUAEPassWI.equalsIgnoreCase("")){ //Commented by Ameena 18012023 for UAEPASS Linked Wi
//				populateUAEPassInfoStatus(sWorkitemId);
//				updateAccRelGridFromUaePassInfo(sWorkitemId);
//
//			} else {
//				populateUAEPassInfoStatus(linkedUAEPassWI);
//				updateAccRelGridFromUaePassInfo(linkedUAEPassWI);
//
//			}
			
			String sQuery1="SELECT count(1)CNT from acc_relation_repeater where wi_name='"+sWorkitemId+"'";
			logInfo("CPDMakerThreeStep", "sQuery1" + sQuery1);
			List<List<String>> list = formObject.getDataFromDB(sQuery1);
			//int CNT = list.get(0).get(0); //integer.parse.int
			if (list != null && !list.isEmpty()) {
				for (int i = 0; i < list.size(); i++) {
					logInfo("CPDMakerThreeStep","on Form Load: Initializing TRSD customer"+ i);
					//TRSDclicked.add(false);  need to be checked
				}
			}
			LoadInstantDelivery(); 
			populateStndInstr();
			
		} catch(Exception e){
			logInfo("","Error"+e.toString());
		}
		
		frame81_CPD_Disable();
		fieldValueBagSet(); //to be checked
		//sOnLoad="True";	//to be removed moksh  need to be checked
		fillEIDADataInBelowFields(controlName,formObject.getValue(controlName).toString());		//were in click&change event called on load
		fillFCRDataInBelowFields(controlName,formObject.getValue(controlName).toString());		//were in click&change event called on load
		fillManualDataInBelowFields(controlName,formObject.getValue(controlName).toString());	//were in click&change event called on load
		if(formObject.getValue(SALARY_TRANSFER).toString().equals("")){
			formObject.setValue(SALARY_TRANSFER,"No");
		}
		checkCRS();
		//disable tab fb
		fbValidation();
	}

	public boolean fieldValueBagSet() {  //BY KISHAN
		String sLoadingDone="False";
		String sOnLoad="False";
		logInfo("fieldValueBagSet","Inside fieldValueBagSet");
		long start_time=System.currentTimeMillis();
		sOnLoad="False";
		try {		
			logInfo("fieldValueBagSet","sUserNamesUserNamesUserName"+sUserName);
			String sQuery = "update ext_ao set new_maker ='"+sUserName+"' where wi_name ='"+sWorkitemId+"'";
			logInfo("fieldValueBagSet","sQuerysQuerysQuery"+sQuery);
			formObject.saveDataInDB(sQuery);
			prefLang();
			formObject.setValue(SELECTED_ROW_INDEX, "0");
			formObject.setStyle(DRP_RESEIDA,DISABLE,TRUE);
			formObject.setStyle(RA_CARRYNG_EID_CARD,DISABLE,TRUE);
			populateAuditSearch(SEARCH_DETAILS_LVW);
			formObject.setValue(CRS_CERTI_YES,"true");
			logInfo("fieldValueBagSet","outside SIGN"+formObject.getValue(RA_SIGN_TYPE).toString());
			if("".equalsIgnoreCase(formObject.getValue(RA_SIGN_TYPE).toString()) 
			   || "".equalsIgnoreCase(formObject.getValue(RA_SIGN_TYPE).toString())){
					formObject.setValue(RA_SIGN_TYPE, "Signature in English");
					logInfo("fieldValueBagSet","INSIDE SIGN"+formObject.getValue(RA_SIGN_TYPE).toString());
			}
			logInfo("fieldValueBagSet","outside STAFF"+formObject.getValue(ED_SET_FLG).toString());
			if(formObject.getValue(ED_SET_FLG).toString().equalsIgnoreCase("")){
				formObject.setValue(ED_SET_FLG,"No");
			}
			populateMakaniData();
			logInfo("fieldValueBagSet",formObject.getValue(SELECTED_ROW_INDEX).toString());
			setRMCode();
			String decision="";
			try{
				String qry = "select cro_dec from "+sExtTable+" where wi_name ='"+sWorkitemId+"'";  //EXT_AO
				logInfo("fieldValueBagSet","sQuery1"+qry);
				List<List<String>> out = formObject.getDataFromDB(qry);
				decision = out.get(0).get(0).toString();//getTagValues(out,"CRO_DEC");
			}
			catch(Exception e){
				logError("fieldValueBagSet",e);
			}		
			logInfo("fieldValueBagSet","before setProd_group and  setRepeaterRowInOrder condition ");
			setProd_Group();
			setRepeaterRowInOrder();
			int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
			try{
				String sQuery_System  = "select SYSTEM_DEC_CPD from usr_0_cust_txn where wi_name = '"+sWorkitemId+"' "
				+ "and cust_sno = '"+iSelectedRow+"'";
				List<List<String>> soutput=formObject.getDataFromDB(sQuery_System);
				if(soutput.get(0).size() != 0){
					String sys_dec = soutput.get(0).get(0);
					if (null == sys_dec ||sys_dec.isEmpty()){
						formObject.setValue("SYSTEM_DEC_CPD","");
					}
				}
			} catch(Exception e){
				logError("fieldValueBagSet",e);
			}		
			fieldValueBagSetCPDMaker();
			logInfo("fieldValueBagSet","SELECTED_ROW_INDEX: "+formObject.getValue(SELECTED_ROW_INDEX).toString());
			setRMCode();
			PopulatePrivateClientQuestions();
			try{
				clearUdfGrid();
				int custNo = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
				String sQuery2="SELECT GRADUATION_DATE, SPECIAL_CUSTOMER_IDENTIFIER FROM USR_0_UDF_DETAILS "
				+ "WHERE ACC_RELATION_SERIALNO='"+custNo+"' AND WI_NAME='"+sWorkitemId+"'";
				
				List<List<String>> listContent= formObject.getDataFromDB(sQuery2);  // Doubt
				if(listContent != null && listContent.size() > 0)
					populateUDFGrid(sQuery2);
				/*String listContent=formObject.getDataFromDB(sQuery2);
				if(Integer.parseInt(getTagValues(listContent,"TotalRetrieved"))>0)
					populateUDFGrid(listContent);*/
			}catch (Exception e){
				logError("",e);
			}
			populatePassAndVisaFields();
			if("true".equalsIgnoreCase(formObject.getValue(CHECKBOX_VISA_STATUS_MANUAL).toString())){
				formObject.setStyle(MANUAL_VISASTATUS,DISABLE, "true");
				formObject.setStyle(MANUAL_VISASTATUS,DISABLE, "true");
			}
			setAutoFilledFieldsLocked();
			formObject.setStyle("COMMAND59",DISABLE, TRUE);
			formObject.setValue("", sActivityName);
			formObject.setValue(WI_NAME,sWorkitemId);
			formObject.setValue(CURR_WS_DETAIL,sActivityName);
			formObject.setStyle(CURR_WS_DETAIL,VISIBLE,"false");
			formObject.setValue(CRO_DEC, "");
			formObject.setValue(CRO_REMARKS, "");
			disableControls(new String[]{"NEG_INFO","NEG_AXPLAIN","FATF","FATF_EXPLAIN","KYC","KYC_AXPLAIN"});
			//Frame52_Disable();
			/*doReadOnlyStatic("AO_SOURCING_CHANNEL,Text163,Mask8,cust_id,AO_ACC_HOME_BRANCH,AO_SOURCING_CENTER,AO_REQUEST_TYPE," //doubt
			+ "AO_DATA_ENTRY_MODE,AO_FORM_AUTO_GENERATE,AO_ACC_OWN_TYPE,AO_ACC_CLASS,AO_WMS_ID,AO_LODGEMENT_NO,AO_CURR_WS_DETAIL,");*/
			String[] disableControls_1 = {SOURCING_CHANNEL,TXT_CUSTOMERNAME,TXT_DOB,TXT_CUSTOMERID,ACC_HOME_BRANCH,
					"SOURCING_CENTER",REQUEST_TYPE,DATA_ENTRY_MODE,FORM_AUTO_GENERATE,ACC_OWN_TYPE,
					 ACC_CLASS,WMS_ID,LODGEMENT_NO,CURR_WS_DETAIL};  
			//disableControls(disableControls_1);
			/*formObject.setNGLocked("Frame35",true);
			formObject.setNGLocked("Frame57",true);
			formObject.setNGLocked("Frame17", false);
			formObject.setNGLocked("Frame10", false);*/
			formObject.setStyle(SEC_OPT_PROD_CRO,DISABLE, TRUE);
			formObject.setStyle(SEC_DOC_REQ_CRO,DISABLE, TRUE);
			formObject.setStyle(BTN_EIDA_INFO,DISABLE, TRUE);
			formObject.setStyle(BTN_SEARCH_CUSTOMER,DISABLE, TRUE);
			formObject.setStyle(BTN_SEARCH_CLEAR,DISABLE, TRUE);
			formObject.setStyle(BTN_ADD_CUST_INFO,DISABLE, TRUE);
			formObject.setStyle(SEARCH_ADD_EIDA_INFO,DISABLE, TRUE);
			formObject.setStyle(BTN_ADD_MANUALLY, DISABLE, TRUE);
			formObject.setStyle("Command14",DISABLE, TRUE); //No ID Found ? BUTTON_REFRESH

			if(formObject.getValue(ACC_OWN_TYPE).toString().equalsIgnoreCase("Single")){
				if(formObject.getValue(OPERATING_INST).toString().equalsIgnoreCase("")){
					formObject.setValue(OPERATING_INST, "Single");
				}
			} else if(formObject.getValue(ACC_OWN_TYPE).toString().equalsIgnoreCase("Minor")){
				if(formObject.getValue(OPERATING_INST).toString().equalsIgnoreCase("")){
					formObject.setValue(OPERATING_INST, "TO BE OPERATED BY GUARDIAN");
				}
			} else if(formObject.getValue(ACC_OWN_TYPE).toString().equalsIgnoreCase("Joint")){
				if(formObject.getValue(OPERATING_INST).toString().equalsIgnoreCase("")){
					formObject.setValue(OPERATING_INST, "TO BE OPERATED BY ANY ONE");
				}
			}
			int count = getGridCount(ACC_RELATION);
			formObject.setValue(NO_OF_CUST_SEARCHED, Integer.toString(count));
			formObject.setStyle(BUTTON_GENERATE_TEMPLATE,DISABLE, TRUE);			
			formObject.setStyle(TXT_DOB,VISIBLE,"true");
			formObject.setStyle(TXT_CUSTOMERNAME,VISIBLE,"true");
			frame23_CPD_Disable();
			formObject.setStyle("Frame4",DISABLE, FALSE);
			formObject.setStyle("Tab1", DISABLE, FALSE);
			formObject.setStyle("Tab2", DISABLE, FALSE);
			formObject.setStyle("Frame34",DISABLE, FALSE);
			formObject.setStyle("Frame39",DISABLE, FALSE);
			formObject.setStyle(SEC_INTERNAL_DETL,DISABLE, FALSE);		
			formObject.setStyle("NIG_MAKER",VISIBLE,"false");
			formObject.setStyle("NIG_CHECKER",VISIBLE,"false");
			formObject.setStyle("Label364",VISIBLE,"false");
			formObject.setStyle("Label365",VISIBLE,"false");
			int iSearchedCustomer=0;
			int iProcessedCustomer=0;
			try{
				iSearchedCustomer = Integer.parseInt(formObject.getValue(NO_OF_CUST_SEARCHED).toString());
				iProcessedCustomer = Integer.parseInt(formObject.getValue(NO_OF_CUST_PROCESSED).toString())+1;
			} catch(Exception e){
				logError("",e);
			}
			if(iSearchedCustomer==iProcessedCustomer){
				formObject.setStyle("COMMAND20",DISABLE, FALSE);  //ID not found
			} else{
				formObject.setStyle("COMMAND20",DISABLE, TRUE);
			}
			String req_type=formObject.getValue(REQUEST_TYPE).toString();
			if(req_type.equalsIgnoreCase("New Account with Category Change") 
			|| req_type.equalsIgnoreCase("Category Change Only")){
				formObject.setStyle(SEC_CAT_CHNG,DISABLE, FALSE);
				formObject.setStyle(SEC_INTERNAL_DETL,DISABLE, FALSE);
				if(req_type.equalsIgnoreCase("Category Change Only")){
					logInfo("fieldValueBagSet","showing cat chng tab");
					formObject.setTabStyle("tab3", "13", VISIBLE, TRUE);
					formObject.setTabStyle("tab3", "10", DISABLE, TRUE);
//					formObject.setStyle("Frame34",DISABLE, TRUE);
//					formObject.setStyle(SEC_ACC_INFO_PD,DISABLE, TRUE);					
				}
//				formObject.setStyle(NIG_MAKER,DISABLE, TRUE);	  //ID not found		
//				formObject.setStyle(NIG_CHECKER,DISABLE, TRUE);
				formObject.setStyle(NIG_MAKER,DISABLE,TRUE);	  //ID not found		
				formObject.setStyle(NIG_CHECKER,DISABLE,TRUE);
			} else {
				formObject.setStyle(SEC_CAT_CHNG, DISABLE, TRUE);
				formObject.setTabStyle("tab3", "10", DISABLE, FALSE);
				formObject.setStyle(SEC_ACC_INFO_PD, DISABLE, FALSE);				
			}
			formObject.setStyle(ADD_PRODUCT,DISABLE, FALSE);
			formObject.setStyle("remove",DISABLE, FALSE);		
			logInfo("fieldValueBagSet","before writing channel_type again------"+formObject.getValue(CHANNEL_TYPE).toString());
			if(formObject.getValue(CHANNEL_TYPE).toString().equalsIgnoreCase("")) {
				/*formObject.getNGDataFromDataSource("SELECT CHANNEL_TYPE FROM USR_0_SERVICE_CHANNEL "   //DOUBT
				+ "WHERE SERVICE_CHANNEL ='"+formObject.getValue(SOURCING_CHANNEL)+"'", 1, CHANNEL_TYPE);*/
				
				List<List<String>> channel_data = formObject.getDataFromDB("SELECT CHANNEL_TYPE FROM USR_0_SERVICE_CHANNEL " 
						+ "WHERE SERVICE_CHANNEL ='"+formObject.getValue(SOURCING_CHANNEL).toString()+"'");
				formObject.setValue(CHANNEL_TYPE,channel_data.get(0).get(0));
			}

			if(formObject.getValue(CHANNEL_TYPE).toString().equalsIgnoreCase("Direct")){
				logInfo("fieldValueBagSet","channel type is direct ");
				formObject.setStyle(SEC_DEL_INST,DISABLE, TRUE);
				logInfo("fieldValueBagSet","channel type is direct frame not enabled ");
			} else {
				formObject.setStyle(SEC_DEL_INST,DISABLE, FALSE);
				LoadInstantBranchValue();
				Delivery_Preferences_Tab();
			}
			if(formObject.getValue(CHECKBOX_SELECTALL_MANUAL ).toString().equalsIgnoreCase("True")){
				manageManualCheckBoxes();
			} else {
				setManualFieldsLock();
			}
			if(formObject.getValue(RA_CB_OTHERS).toString().equalsIgnoreCase("False")){
				formObject.setStyle(RA_OTHERS,DISABLE, TRUE);
			} else{
				formObject.setStyle(RA_OTHERS,DISABLE, FALSE);
			}
			if(formObject.getValue(IDS_OTH_CB_OTHERS).toString().equalsIgnoreCase("False")){
				formObject.setStyle(IDS_BNFT_CB_OTHERS,DISABLE, TRUE);
				formObject.setStyle(IDS_OTH_CB_OTHERS,DISABLE, TRUE);
				if(!formObject.getValue(PD_CUSTSEGMENT).toString().equalsIgnoreCase("")){
					formObject.setStyle(IDS_OTH_CB_OTHERS,DISABLE, FALSE);
				}
			} else{
				formObject.setStyle(IDS_BNFT_CB_OTHERS,DISABLE, FALSE);
				formObject.setStyle(IDS_OTH_CB_OTHERS,DISABLE, FALSE);
			}
			if(formObject.getValue(PD_CUSTSEGMENT).toString().equalsIgnoreCase("")){				
				formObject.clearCombo(PRO_CODE);
//				formObject.addItemInCombo(PRO_CODE,"","");
				//formObject.setNGListIndex("PRO_CODE", 0);  //doubt
				formObject.clearCombo(EXCELLENCY_CNTR);
//				formObject.addItemInCombo(EXCELLENCY_CNTR,"");
				//formObject.setNGListIndex(EXCELLENCY_CNTR, 0);  //doubt					
			}
			iSelectedRow  = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
			String bnk_relation= formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 7);
			if(bnk_relation.equalsIgnoreCase("Existing")){
				formObject.setStyle(IDS_OTH_CB_OTHERS,DISABLE, TRUE);
			}
			if(formObject.getValue(CK_PER_DET).toString().equalsIgnoreCase("False")){
				formObject.setStyle(DRP_RESEIDA,DISABLE, TRUE);					
			} else if( returnVisaStatus().equalsIgnoreCase("Residency Visa")){
				formObject.setStyle(DRP_RESEIDA,DISABLE, FALSE);
			} else {
				formObject.setStyle(DRP_RESEIDA,DISABLE, TRUE);	
			}
			String sDOB =getFinalDataComparison(CHECKBOX_DOB_FCR,CHECKBOX_DOB_EIDA,CHECKBOX_DOB_MANUAL,FCR_DOB,
					EIDA_DOB,MANUAL_DOB).trim().toUpperCase();
			formObject.setValue(PD_DOB,sDOB);

			if(!formObject.getValue(FCR_NAME).toString().equalsIgnoreCase("")){
				formObject.setStyle(PD_CUSTSEGMENT,DISABLE, TRUE);
			} else {
				formObject.setStyle(PD_CUSTSEGMENT,DISABLE, FALSE);
			}
			formObject.setStyle("Tab5",VISIBLE,"true");		 // ?	
			formObject.setStyle(GI_EXST_SINCE,DISABLE, TRUE);
			int iRows = getGridCount(PRODUCT_QUEUE);//objChkRepeater.getRowCount();
			for(int i=0;i<iRows;i++){                                                  //doubt
				formObject.setTableCellValue(PRODUCT_QUEUE, i, 14, i+1+"");
				//formObject.setTableCellValue(PRODUCT_QUEUE, arg1, arg2, arg3);//(i,PRODUCT_QUEUE,i+"");
				//objChkRepeater.setValue(i,"AO_PRODUCT_QUEUE.CID",i+"");
			}

			if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only")){
//				formObject.setStyle(BTN_ACC_INFO_GEN_TEMP,DISABLE, TRUE);
//				formObject.setStyle(SEC_ACC_INFO_PD,DISABLE, TRUE);
//				formObject.setStyle(SEC_ACC_INFO_ECD, DISABLE, TRUE);
//				formObject.setStyle("Frame54", DISABLE, TRUE);
//				formObject.setStyle("Frame41", DISABLE, TRUE);				
//				formObject.setStyle("Frame39", DISABLE, TRUE);//Modification Ends
				formObject.setTabStyle("tab3", "11", DISABLE, TRUE);
				disableControls(new String[]{BTN_ACC_INFO_GEN_TEMP, SEC_ACC_INFO_PD, SEC_ACC_INFO_ECD,
						SEC_ACC_INFO_DCL,SEC_SI});
				/*if(formObject.getValue(NEW_RM_CODE_CAT_CHANGE).toString().equalsIgnoreCase("")){
					formObject.setValue(NEW_RM_CODE_CAT_CHANGE,"");
				}
				if(formObject.getValue(NEW_RM_NAME_CAT_CHANGE).toString().equalsIgnoreCase("")){
					formObject.setValue(NEW_RM_NAME_CAT_CHANGE,"");
				}*///what's the need
			}
			try	{
				if(formObject.getValue(SCAN_MODE).toString().equalsIgnoreCase("New WMS ID")){
					if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("New Account")){
						String acc_rel= formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 9);
						logInfo("fieldValueBagSet","acc_rel value  = "+acc_rel);
						String pri_cust_cat=formObject.getValue(PD_CUSTSEGMENT).toString();
						logInfo("fieldValueBagSet","Customer segment::"+pri_cust_cat);
						if(!(acc_rel.equalsIgnoreCase("AUS") && acc_rel.equalsIgnoreCase("POA"))
						&& pri_cust_cat.equalsIgnoreCase("Signatory")){
							logInfo("","INSIDE IF");
							sendMessageValuesList("","Please select another request type for Signatory Customer");
							//formObject.NGMakeFormReadOnly();
						}
					}
					saveKYCInfo();
					saveKycMultiDropDownData();
					savePreAssessmentDetails();    //shahbaz
					saveComparisonData();
					saveClientQuestionsData(); 
					saveCRSDetails();
					saveIndividualInfo();
					saveIndividualContactInfo();
				}
			} catch(Exception e){
				logError("fieldValueBagSet",e);
			}
//			String sQuery1="select count(*) countwi from usr_0_ao_dec_hist  where ws_name='CPD Checker' and"
//					+ "upper(userid)=upper('"+sUserName+"') and wi_name='"+sWorkitemId+"'";//Commented for invalid column on 27042023(Performanceissue) by Ameena
			String sQuery1="select count(*) countwi from usr_0_ao_dec_hist  where ws_name='CPD Checker' and "
					+ " upper(userid)=upper('"+sUserName+"') and wi_name='"+sWorkitemId+"'";
			List<List<String>> sOutput1=formObject.getDataFromDB(sQuery1);
			logInfo("fieldValueBagSet","query --"+sQuery1);
			logInfo("fieldValueBagSet","sOutput1---"+sOutput1);		
			int sCount = (sOutput1 != null && sOutput1.size() > 0)  ? Integer.parseInt(sOutput1.get(0).get(0).toString()) : 0;
			if(sCount>0){
				formObject.applyGroup("CONTROL_SET_CPD_FORM"); //formObject.NGMakeFormReadOnly();
				sendMessageValuesList("","Maker & Checker cannot be same!");
			}
			 decision = formObject.getValue(CRO_DEC).toString();
		    if(decision.equalsIgnoreCase("Permanent Reject - Discard")) {
		    	formObject.applyGroup("CONTROL_SET_CPD_FORM");//formObject.NGMakeFormReadOnly();
		    	Frame_delivery();
		    }		
			
			sLoadingDone ="True";
			if(formObject.getValue("CHECK95").toString().equalsIgnoreCase("true")){ // ?
				FrameFATCA_CPD_Enable();
				logInfo("fieldValueBagSet","CHECK95 IS TRUE");
				formObject.setStyle(COMBODOC,DISABLE, FALSE);
			}
			if(formObject.getValue(ED_SET_FLG).toString().equalsIgnoreCase("")){
				formObject.setValue(ED_SET_FLG,"No");
			}
			if(formObject.getValue(CHECKBOX_VISA_NO_MANUAL).toString().equalsIgnoreCase("true")
			&& (!formObject.getValue(MANUAL_VISANO).toString().equalsIgnoreCase("MDSA"))){
				logInfo("","fieldval in if "+formObject.getValue(CHECKBOX_VISA_NO_MANUAL));
				formObject.setStyle(MANUAL_VISANO,DISABLE, FALSE);
				formObject.setStyle(MANUAL_VISANO, DISABLE,"true");
			}
			setTemp_usr_0_product_selected();
			deleteECBCallsDetails();
			return true;
		} catch (Exception e)  {
			logError("fieldValueBagSet",e);
			formObject.setStyle("Tab5",VISIBLE , "true");
			return false;
		} finally {
			long end_Time = System.currentTimeMillis();
			logInfo("fieldValueBagSet","Diff time in FieldValueBagSet ::"+Long.toString(end_Time-start_time));
			formObject.setStyle(DRP_RESEIDA,DISABLE, FALSE);
			formObject.setStyle(RA_CARRYNG_EID_CARD,DISABLE, FALSE);
		}
	}
	
	public void onTabClickCPDThreeStep(String data) {
		try {
			logInfo("onTabClickCPDThreeStep","INSIDE onTabClickCPDThreeStep kdd == "+data);
		//	String[] selectedSheetInfo = data.split(",");
//			String tabCaption = selectedSheetInfo[0];
//			int selectedSheetIndex = Integer.parseInt(selectedSheetInfo[1]);
//			int sCurrTabIndex = selectedSheetIndex;
			
			String[] selectedSheetInfo = null ;
			String tabCaption = "";
			int selectedSheetIndex = 0;
			if(data.indexOf(",") != -1){
				selectedSheetInfo = data.split(",");
				selectedSheetIndex = Integer.parseInt(selectedSheetInfo[1]);
			}else{
				selectedSheetInfo = new String[1];
				selectedSheetInfo[0] = data;
				selectedSheetIndex = Integer.parseInt(selectedSheetInfo[0]);
			}
		    tabCaption = selectedSheetInfo[0];
		    int sCurrTabIndex = selectedSheetIndex;
			/*public boolean isNextClick = false;
			public boolean isCustInfoVisit = false; */
			formObject.setStyle(BTN_CC_GEN_TEMP,DISABLE,TRUE);
			//formObject.setStyle(ACC_RELATION,DISABLE,TRUE);
			boolean isCustInfoVisit=false; 
			if(selectedSheetIndex == 0){
				if(formObject.getValue(OPERATING_INST).toString().equalsIgnoreCase("")){
					sendMessageValuesList(OPERATING_INST, "Please fill Operating Instructions.");
					getReturnMessage(false);
				}
			}

			if(selectedSheetIndex == 1 || selectedSheetIndex == 2 || selectedSheetIndex == 3 
					|| selectedSheetIndex == 4 ||  selectedSheetIndex == 5){
				//Added by Shivanshu for Dormant Account Alert on 13-01-2025
				dormantCustAlert();
				if(!custInfoTabLoad || 
						!custSno.equalsIgnoreCase(formObject.getValue(SELECTED_ROW_INDEX).toString())) {
					custSno = formObject.getValue(SELECTED_ROW_INDEX).toString();
					custInfoTabLoad = true;
					populateCRSData();
					populateMakaniData();
					resetRekey();

				}
				setPermCntry();
				setResCntry();
				setCorrCntry();
				formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
				formObject.applyGroup(CONTROL_SET_FCR);
				setRMCode();
				isCustInfoVisit=true; 
				logInfo("before lockOthersFields inside tab click","");
				lockOthersFields();
				logInfo("after lockOthersFields inside tab click","");
				manageInternalSection();
				int fieldToFocus=Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
				logInfo("onTabClickCPDThreeStep","fieldToFocus"+fieldToFocus);
				String sBankRelation= formObject.getTableCellValue(ACC_RELATION, fieldToFocus, 7);
				String sAccRelation= formObject.getTableCellValue(ACC_RELATION, fieldToFocus, 9);
				manageCustomerChangeCheckboxes(sBankRelation,sAccRelation);
				disableControls(new String []{RELIGION,MARITAL_STATUS,ED_CB_TML,ED_CB_NON_TML});
				enableControls(new String[]{CK_PER_DET,CHK_CONTACT_DET,
						CHK_PASSPORT_DET,CHK_INTERNAL_SEC,CHK_GEN_INFO,
						CHK_EMP_DETAIL,CHK_FUNDING_INFO,CHK_RISK_ASS,CHK_ADD,
						CHK_BANKING_RELATION,CHECKBOX_FATCA,DRP_RESEIDA});
				//DRP_RESEIDA
				if(formObject.getValue(CHK_EMP_DETAIL).toString().equalsIgnoreCase("true")) {
					if(formObject.getValue(PROFESION).toString().equalsIgnoreCase("Others") || formObject.getValue(PROFESION).toString().equalsIgnoreCase("")) {
						formObject.setStyle(ED_OTHER, DISABLE, FALSE);
					} else {
						formObject.setStyle(ED_OTHER, DISABLE, TRUE);
					}
					if(formObject.getValue(EMPNAME).toString().equalsIgnoreCase("Others") || formObject.getValue(EMPNAME).toString().equalsIgnoreCase("")) {
						formObject.setStyle(ED_EMPNAME, DISABLE, TRUE);
					} else {
						formObject.setStyle(ED_EMPNAME, DISABLE, TRUE);
					}
				}
				if(formObject.getValue(CHECKBOX_STATE_MANUAL).toString().equalsIgnoreCase("false")) {
					formObject.setStyle(MANUAL_STATE, DISABLE, TRUE);
				}
				if(formObject.getValue(GI_IS_CUST_VIP).toString().equalsIgnoreCase("")) {
					formObject.setValue(GI_IS_CUST_VIP, "No");
				}
				if(formObject.getValue(RA_IS_CUST_DEALNG_ARMAMNT).toString().equalsIgnoreCase("")) {
					formObject.setValue(RA_IS_CUST_DEALNG_ARMAMNT, "No");
				}
				if(formObject.getValue(RA_IS_CUST_DEALNG_HAWALA).toString().equalsIgnoreCase("")) {
					formObject.setValue(RA_IS_CUST_DEALNG_HAWALA, "No");
				}
				if(formObject.getValue(RA_PRPSE_TAX_EVSN).toString().equalsIgnoreCase("")) {
					formObject.setValue(RA_PRPSE_TAX_EVSN, "No");
				}
				if(formObject.getValue(RA_IS_CUST_PEP).toString().equalsIgnoreCase("")) {
					formObject.setValue(RA_IS_CUST_PEP, "No");
				}
				if(!formObject.getValue(FCR_NATIONALITY).toString().equalsIgnoreCase("")
						&& formObject.getValue(FCR_NATIONALITY).toString().equalsIgnoreCase("USA")) {
					if(formObject.getValue(FAT_US_PERSON).toString().equalsIgnoreCase("")){
						formObject.setValue(FAT_US_PERSON, "YES");
						formObject.setStyle(FAT_US_PERSON, DISABLE, TRUE);
					}
				}
				if(formObject.getValue(ED_MONTHLY_INCM).toString().equalsIgnoreCase("")) {
					formObject.setValue(ED_MONTHLY_INCM,"0");
				}
				if(formObject.getValue(CHECKBOX_SELECTALL_MANUAL).toString().equalsIgnoreCase("False")){
					setManualFieldsLock();
				}
				if(formObject.getValue(FATCAMINI).toString().equalsIgnoreCase("") && sBankRelation.equalsIgnoreCase("New")) {  
					formObject.setStyle(BTN_VALIDATE, DISABLE, FALSE);
				}
				try	{
					Calendar calendar = Calendar.getInstance();
					SimpleDateFormat simpledateformat = new SimpleDateFormat(DATEFORMAT);
					String scurrentDate = simpledateformat.format(calendar.getTime());
					//shahbaz
					String bank_Relationship = formObject.getTableCellValue(ACC_RELATION, 0, 7); 
					logInfo("populateLastKycDate ", "bank_Relationship: " + bank_Relationship);
					
					if(!bank_Relationship.equalsIgnoreCase("Existing")){
						if(formObject.getValue(GI_DATE_KYC_PREP).toString().equalsIgnoreCase("")){
							formObject.setValue(GI_DATE_KYC_PREP, scurrentDate);
						}
					}
				} catch(Exception ex) {
					logError("onTabClickCPDThreeStep",ex);
				}
				//formObject.setStyle(MANUAL_EIDANO,DISABLE ,FALSE); 
				boolean result=false;
				if(formObject.getValue(RA_IS_UAE_RESIDENT).toString().equalsIgnoreCase("")) {
					calculateResidencyStatus(RA_IS_UAE_RESIDENT);	
				}
				String bankRelation = formObject.getTableCellValue(ACC_RELATION, fieldToFocus,7);//bank relation = 7
				String reqType= formObject.getValue(REQUEST_TYPE).toString();
				if(bankRelation.equalsIgnoreCase("New") && !(reqType.equalsIgnoreCase("New Account with Category Change"))) {
					String segment = formObject.getValue(PD_CUSTSEGMENT).toString();
					String rmCode = formObject.getValue(RM_CODE).toString();
					if(segment.equalsIgnoreCase("Aspire") && rmCode.equalsIgnoreCase("")) {
					} else if(segment.equalsIgnoreCase("Privilege") || segment.equalsIgnoreCase("Emirati")) {
						//commneted by sahil for the issue	logInfo("Inside Click Event","INSIDE Combo4");
						//formObject.setValue(RM_CODE,"");
					} else if((segment.equalsIgnoreCase("Excellency") || segment.equalsIgnoreCase("Emirati Excellency"))
							&& rmCode.equalsIgnoreCase("")) {
						formObject.setValue(RM_CODE,"");
					} 
				}
				List<List<String>> List = null;
				if(selectedSheetIndex > 3) {
					String custSegment= formObject.getValue(PD_CUSTSEGMENT).toString(); 
					 List = formObject.getDataFromDB("select iscrsmandatory from usr_0_cust_segment "
							+ "where cust_segment='"+custSegment+"'");
					String ismandatory = null;
					try {
						if(List!=null && List.size()>0){
							ismandatory = List.get(0).get(0);
						}
					} catch (Exception e) {
					    logInfo("onTabCPDMakerFourStep","CUST Segment result: "+ismandatory);
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
				}
				/*if(formObject.getValue(EMP_STATUS).toString().equalsIgnoreCase("Employed")) {
					logInfo("","Inside EMP_STATUS....with employed status as Employed");
					if((formObject.getValue(SALARY_TRANSFER).toString().equalsIgnoreCase(""))){
						sendMessageValuesList(SALARY_TRANSFER, "Please select the value of salary transfer");
						return;
					}
				}	*/		// SALARY_TRANSFER not is not present in iforms to be checked by Sahil
				if(selectedSheetIndex > 1) {
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
				
					try  {
						String sFinalDOB = getFinalDataComparison(CHECKBOX_DOB_FCR,CHECKBOX_DOB_EIDA,
								CHECKBOX_DOB_MANUAL,FCR_DOB,EIDA_DOB,MANUAL_DOB);
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
									sendMessageValuesList(FCR_DOB,"Date Of Birth Should Be Less Than "+sMinorAge+""
											+ " Years For Minor Customer.");
								}
								else if(formObject.getValue(CHECKBOX_DOB_EIDA).toString().equalsIgnoreCase(TRUE)) {
									sendMessageValuesList(EIDA_DOB,"Date Of Birth Should Be Less Than "+sMinorAge+""
											+ " Years For Minor Customer.");
								}
								else if(formObject.getValue(CHECKBOX_DOB_MANUAL).toString().equalsIgnoreCase(TRUE)) {
									sendMessageValuesList(MANUAL_DOB,"Date Of Birth Should Be Less Than "+sMinorAge+" "
											+ "Years For Minor Customer.");
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
					} catch (Exception e) {
						logError("executeServerEvent", e);
					}
				}
				/*try {
					int sSelectedRow=Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());				
					String sQuery1 ="SELECT IS_DEDUPE_CLICK_CPD FROM USR_0_CUST_TXN WHERE WI_NAME ='"+sWorkitemId+"' "
							+ "and cust_sno='"+sSelectedRow+"'";
					List = formObject.getDataFromDB(sQuery1);
					String dedupeDone = (List != null && List.size() > 0)  ? List.get(0).get(0).toString() : "";
					if(!dedupeDone.equalsIgnoreCase(TRUE)) {
						sendMessageValuesList("", "Please Do Dedupe Search For This Customer.");
						return ;
					}
				}
				catch (Exception e) {
					logError("executeServerEvent", e);
				}*/ // newly Commented
				/*if(!formObject.getValue(PD_DATEOFATTAININGMAT).toString().equalsIgnoreCase("")) {
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
				}*/// newly Commented
				logInfo("onTabCPDMakerFourStep","Inside sMode=R----");
				saveKYCInfo();		
				saveKycMultiDropDownData();
				savePreAssessmentDetails();     //krishna
				
				String private_Client=formObject.getValue("PRIVATE_CLIENT").toString();	
			 if(private_Client.equalsIgnoreCase("Y")) {
				    getPreAssessmentFlag();
		     int returnCode = mandatoryDocAlert();
			 if (returnCode < 0){
				  sendMessageValuesList("BTN_SUBMIT", "Please Upload/Attach Exception Approval Document");
					//	return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
				   return;
					}else {
						String action = "Approved";
						String reasonForAction = "PreAssesment Detail Approved";
						String preKYCflag = formObject.getValue("PRE_ASSESMENT_FLAG").toString();
						if (preKYCflag.equalsIgnoreCase("No")){
							action = "Reject";
							reasonForAction = "Pre-Assessment Detail Unacceptable hence Rejected";
						}
						String insertDec = "INSERT INTO USR_0_AO_DEC_HIST (WI_NAME, CREATE_DAT, USERNAME, WS_DECISION, WS_COMMENTS) "
								+ " VALUES('"
								+ sWorkitemId
								+ "',SYSDATE,'"+sUserName+"','"+action+"','"+reasonForAction+"')";
						logInfo("Query for insert in hist : ", "" + insertDec);
						formObject.saveDataInDB(insertDec);
				    }
				}
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
					List = formObject.getDataFromDB(sQuery1);
					String isMobChange = List.get(0).get(0);
					if(Integer.parseInt(isMobChange)>0) {
						sQuery1="SELECT COUNT(WI_NAME) IS_MOB_CHANGE FROM USR_0_CHANGE_TRACKER "
								+ "WHERE WI_NAME='"+ sWorkitemId +"' AND CUST_SNO ='"+Integer.toString(fieldToFocus+1)
								+"' AND FIELD_NAME ='MOBILE' "
								+ "AND WORK_STEP in ('CPD Maker','QDE_Cust_Info','DDE_Cust_Info') "
								+ "AND STATUS='Pending'";
						logInfo("onTabCPDMakerFourStep","sQuery1----"+sQuery1);
						List = formObject.getDataFromDB(sQuery1);
						logInfo("onTabCPDMakerFourStep","List----"+List);
						String output = List.get(0).get(0);
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
					int iProcessedCustomer=Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 1;
					String sCID = formObject.getTableCellValue(ACC_RELATION, iProcessedCustomer,3);//CID
					String sCASA= "";
					sBankRelation = formObject.getTableCellValue(ACC_RELATION, iProcessedCustomer,7);//bnkrel = 7
					String sCustNo="";				
					sCustNo = formObject.getTableCellValue(ACC_RELATION, iProcessedCustomer,0);
					if(sBankRelation.equalsIgnoreCase("Existing")) {
						String sOutput="SELECT COUNT(1) AS COUNT_WI "
								+ "FROM USR_0_PRODUCT_EXISTING WHERE WI_NAME ='"+sWorkitemId+"' "
								+ "AND CUSTOMER_ID ='"+sCID+"' AND ACCOUNT_TYPE ='CASA'";
						List = formObject.getDataFromDB(sOutput);
						logInfo("onTabCPDMakerFourStep","X"+sOutput);
						sCASA = List.get(0).get(0);						
					}
					if(selectedSheetIndex == 1 && (sBankRelation.equalsIgnoreCase("New") 
							|| sCASA.equalsIgnoreCase("0"))) {
						String output=getApplicationRiskInputXML(iProcessedCustomer);
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
							logInfo("onTabClickCPDThreeStep","outputresult: "+outputresult);
							/*
							getReturnMessage(true, "SHOWALERT");
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
					logError("onTabClickCPDThreeStep", e);
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
			} else if(selectedSheetIndex == 7) {
				sancScreeningTabClicked = true;
				setDisableCalculateAppRiskButton(sWorkitemId);
				int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
				//Ao dcra changes for upgrade and cco
				if (!(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade")
						||formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only"))) {
					calculateIndRiskThreeStep();
				}
                //ATP-310 DATA NOT INSERTD IN REKEY TABLE
				if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only")) {
				//	logInfo("reKeyInsert","Insert into rekey<<<>>>");
					reKeyInsert();
					updateReKeyTemp("CPD");
				}
				loadCPDcustdata(); 
				long end_Time1=System.currentTimeMillis();
				populateScreeningDataCPD(); 
				long end_Time2=System.currentTimeMillis();
				populateScreeningDataCPD();
				populateTRSD();
				populateTRSDCPD();
				if(!"Returned".equalsIgnoreCase(formObject.getValue(CPD_TRSD_FINAL_DECISION).toString())){
					   setEnableAndDisableFskButton();
					}
				if( formObject.getValue(CPD_TRSD_FINAL_DECISION).toString().equalsIgnoreCase("Approved"))
					formObject.setValue(CPD_FINAL_ELIGIBILITY, "Eligible");
				else
					formObject.setValue(CPD_FINAL_ELIGIBILITY, "Not Eligible");

				if( formObject.getValue(TRSD_FINAL_DECISION).toString().equalsIgnoreCase("Approved"))
					formObject.setValue(FINAL_ELIGIBILITY, "Eligible");
				else
					formObject.setValue(FINAL_ELIGIBILITY, "Not Eligible");
				formObject.setStyle(BTN_TRSD_CHECK, DISABLE, TRUE);  //?  chcek out of condition 
				long end_Time3 = System.currentTimeMillis();
				int iRows = getGridCount(ACC_RELATION);
				logInfo("onTabClickCPDThreeStep","iRows"+iRows);
				if(iRows-1 == iSelectedRow){
					formObject.setStyle(BTN_NEXT_CUST_SANCT,DISABLE, TRUE);
				}
				else {
					formObject.setStyle(BTN_NEXT_CUST_SANCT,DISABLE, FALSE);
				}
				boolean result=false;
				iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
				String sCustNo = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 0);
				logInfo("onTabCPDMakerFourStep","iRows: "+iRows+"iSelectedRow: "+iSelectedRow);
				int sOutput1= updateDataInDB("USR_0_BRMS_TRACKER","SCREENING_STATUS","'Success'",
						"WI_NAME ='"+sWorkitemId+"' AND CUST_SNO='"+sCustNo+"' AND SCREENING_STATUS ='Pending'");
				logInfo("onTabCPDMakerFourStep","sOutput----"+sOutput1);
				saveScreeningDataCPD();
				if(!formObject.getValue(SCAN_MODE).toString().equalsIgnoreCase("New WMS ID")){
					populateScreeningDataCRO();
				}
				frame23_CPD_Disable();
				disableControls(new String[]{FINAL_ELIGIBILITY,CRO_SYS_DEC});

				disableControls(new String[]{CPD_FINAL_ELIGIBILITY,SANC_SYS_DEC});
				int iRows1 = getGridCount(ACC_RELATION);
				int iSelectedRow1 = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
				if(iRows-1 == iRows1) {
					formObject.setStyle(BTN_NEXT_CUST_SANCT,DISABLE,TRUE);
				}
				else {
					formObject.setStyle(BTN_NEXT_CUST_SANCT,DISABLE,FALSE);
				}

			} else if(selectedSheetIndex == 9) {
				if(sancScreeningTabClicked) {
					appAssessTabClicked = true;
				}
				formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
				logInfo("onTabClickCPDThreeStep","INSIDE Application Assessment");//3
				List<List<String>> output;
				long start_Time=System.currentTimeMillis();
				String sQuery1="SELECT case when (select count(*) from acc_relation_repeater where wi_name='"+sWorkitemId+"' "
						+ "and sno is not null)=(select count(*) from usr_0_cust_txn where wi_name='"+sWorkitemId+"' and"
						+ " cust_sno is not null) then 'Done' else 'Not Done' end cust_info from dual";
				logInfo("onTabClickCPDThreeStep","sQuery1"+sQuery1);
				output = formObject.getDataFromDB(sQuery1);
				logInfo("onTabClickCPDThreeStep","output"+output);
				String custInfo = "";
				if(output!=null && output.size()>0){
					custInfo = output.get(0).get(0);
				}
				String sQueryFinalElig = "SELECT case when (select count(*) from acc_relation_repeater"
						+ " where wi_name='"+sWorkitemId+"' and sno is not null)=(select count(*) from usr_0_cust_txn "
						+ "where wi_name='"+sWorkitemId+"'"
						+ " and cust_sno is not null and final_eligibility_cpd is not null) then 'Done' "
						+ "else 'Not Done' end FINAL_ELIGIBILITY from dual";
				logInfo("onTabClickCPDThreeStep","sQueryFinalElig"+sQueryFinalElig);
				output = formObject.getDataFromDB(sQueryFinalElig);
				logInfo("onTabClickCPDThreeStep","output"+output);
				String finalElig = "";
				if(output!=null && output.size()>0){
					finalElig = output.get(0).get(0);
				}
				/*String sQuerySystemDec="SELECT case when (select count(*) from acc_relation_repeater "
						+ "where wi_name='"+sWorkitemId+"' and sno is not null)=(select count(*) from usr_0_cust_txn"
						+ " where wi_name='"+sWorkitemId+"'"
						+ " and cust_sno is not null and system_dec_cpd is not null) then 'Done'"
						+ " else 'Not Done' end SYSTEM_DEC_CPD from dual";
				logInfo("onTabClickCPDThreeStep","sQuerySystemDec"+sQuerySystemDec);
				output = formObject.getDataFromDB(sQuerySystemDec);
				logInfo("onTabClickCPDThreeStep","output"+output);
				String systemDec = "";
				if(output!=null && output.size()>0){
					systemDec = output.get(0).get(0);
				}*/
				long end_Time = System.currentTimeMillis();
				if(custInfo.equalsIgnoreCase("Not Done")){
					formObject.setStyle(LABEL_APPASSESS,VISIBLE,TRUE);
				} else if (finalElig.equalsIgnoreCase("Not Done")) {
					formObject.setStyle(LABEL_APPASSESS,VISIBLE,TRUE);
				}/* else if (systemDec.equalsIgnoreCase("Not Done")) { // Is this condition allowed // doubt ?
					formObject.setStyle(LABEL_APPASSESS,VISIBLE,TRUE);
				} */else {
					formObject.setStyle(LABEL_APPASSESS,VISIBLE,FALSE);
					loadApplicationAssessmentDataCPD();
					CalculateCustomerRiskCPD();
					CalculateAppRiskCPD();
					LoadFacilitiesData(FAC_LVW_CPD);
					if(!formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only")){
						LoadOfferedProduct(PROD_SEC_OFRD_CPD_LVW);
						loadOfferedDebitCard(PROD_SEC_OFRD_CPD_LVW,FAC_OFRD_LVW_CPD);
					}
					loadRequiredDocument(DOC_REQ_QUEUE_CPD);
				}
				saveOfferedProduct("USR_0_PRODUCT_OFFERED_CPD",PROD_SEC_OFRD_CPD_LVW);
				saveFacilitiesData("USR_0_FACILITY_SELECTED_CPD",FAC_LVW_CPD);
				saveOfferedDebitCard("USR_0_DEBITCARD_OFFERED_CPD",FAC_OFRD_LVW_CPD);
				updateDataInDB("USR_0_BRMS_TRACKER","APP_ASSESSMENT_STATUS","'Success'","WI_NAME ='"+sWorkitemId+"' "
						+ "AND APP_ASSESSMENT_STATUS ='Pending'");

				if(!formObject.getValue(SCAN_MODE).toString().equalsIgnoreCase("New WMS ID")){
					loadApplicationAssessmentData();
					calculateAppRisk(); 
					CalculateCustomerRisk();
				}
			} else if(selectedSheetIndex == 10) {
				formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
				clearControls(new String[] {GROUP_TYPE, CARD_TYPE});
				logInfo("onTabClickCPDThreeStep","INSIDE Account Info");//4						
				try	{
					saveOfferedProduct("USR_0_PRODUCT_OFFERED_CPD",PROD_SEC_OFRD_CPD_LVW);
					saveFacilitiesData("USR_0_FACILITY_SELECTED_CPD",FAC_LVW_CPD);
					saveOfferedDebitCard("USR_0_DEBITCARD_OFFERED_CPD",FAC_OFRD_LVW_CPD);	
					String query = "";
					List<List<String>> output;	
					query = "Update USR_0_BRMS_TRACKER set APP_ASSESSMENT_STATUS = 'Success' where WI_NAME ='"
							+sWorkitemId+"'AND APP_ASSESSMENT_STATUS ='Pending'";
					formObject.saveDataInDB(query);
					PopulatePrivateClientQuestions();   
					LockChequebookField();	
					LockCreatedAccountRows();
					String sEtihadValue = formObject.getValue(EXISTING_ETIHAD_CUST).toString();
					logInfo("onTabClickCPDThreeStep","sEtihadValue"+sEtihadValue);
					String sEtihadNo = formObject.getValue(ETIHAD_NO).toString();
					logInfo("onTabClickCPDThreeStep","sEtihadNo"+sEtihadNo);
					if(getListCount(GROUP_TYPE)==0 || getListCount(GROUP_TYPE)==1)	{
						query = "Select GROUP_TYPE from USR_0_GROUP_TYPE order by 1";
						logInfo("onTabClickCPDThreeStep","query"+query);
						output = formObject.getDataFromDB(query);
						logInfo("onTabClickCPDThreeStep","output"+output);
						addDataInComboFromQuery(output.toString(),GROUP_TYPE);
						query = "Select CARD_TYPE from USR_0_CARD_TYPE order by 1";
						logInfo("onTabClickCPDThreeStep","query"+query);
						output = formObject.getDataFromDB(query);
						logInfo("onTabClickCPDThreeStep","output"+output);
						addDataInComboFromQuery(output.toString(),CARD_TYPE);
						query = "Select YES_NO from USR_0_YES_NO order by 1";
						logInfo("onTabClickCPDThreeStep","query"+query);
						output = formObject.getDataFromDB(query);
						logInfo("onTabClickCPDThreeStep","output"+output);
						addDataInComboFromQuery(output.toString(),EXISTING_ETIHAD_CUST);
						formObject.setValue(EXISTING_ETIHAD_CUST,sEtihadValue);
						formObject.setValue(ETIHAD_NO,sEtihadNo);
					}
					int iRows = getGridCount(ACC_RELATION);							
					String sLockDebitCard= "False";
					for(int iLoop=1;iLoop<iRows;iLoop++){//
						if(formObject.getTableCellValue(ACC_RELATION, iLoop, 9).equalsIgnoreCase("JAF"))	{
							sLockDebitCard ="True";
							break;
						}					
						if(formObject.getTableCellValue(ACC_RELATION, iLoop, 9).equalsIgnoreCase("JAO")){
							sLockDebitCard ="True";
							break;
						}
					}
					String sPrimaryCust = getPrimaryCustomerSNO();
					query = "SELECT SIGN_STYLE FROM USR_0_CUST_TXN WHERE WI_NAME ='"+sWorkitemId+"'"
							+ " AND CUST_SNO ='"+sPrimaryCust+"'";
					logInfo("onTabClickCPDThreeStep","query"+query);
					output = formObject.getDataFromDB(query);
					logInfo("onTabClickCPDThreeStep","output"+output);
					if(output.get(0).get(0).indexOf("Sign") == -1){
						sLockDebitCard ="True";
					}

					if(sLockDebitCard.equalsIgnoreCase("True")){
						formObject.setStyle(SEC_ACC_INFO_DCL,DISABLE,TRUE);
					}
					else {
						formObject.setStyle(SEC_ACC_INFO_DCL,DISABLE,FALSE);
						LoadDebitCardCombo();
					}
					if(formObject.getValue(SCAN_MODE).toString().equalsIgnoreCase("New WMS ID")) {
						query = "SELECT USERNAME,PERSONALNAME||' ' ||FAMILYNAME AS NAME FROM PDBUSER WHERE "
								+ "UPPER(USERNAME) =UPPER('"+sUserName+"')";
						logInfo("onTabClickCPDThreeStep","query"+query);
						output = formObject.getDataFromDB(query);
						logInfo("onTabClickCPDThreeStep","output"+output);
						formObject.setValue(MAKER_CODE,output.get(0).get(0));
						formObject.setValue(MAKER_NAME,output.get(0).get(1));
						query = "SELECT DEPARTMENT FROM WFFILTERTABLE WHERE UPPER(USERNAME) =UPPER('"+sUserName+"')"
								+ " AND PROCESSDEFID='"+sProcessDefId+"'";
						logInfo("onTabClickCPDThreeStep","query"+query);
						output = formObject.getDataFromDB(query);
						logInfo("onTabClickCPDThreeStep","output"+output);
						formObject.setValue(MAKER_DEPT,output.get(0).get(0));
						disableControls(new String []{BTN_ACC_INFO_GEN_TEMP,BTN_CC_GEN_TEMP});
					}
					CalculateAccTitle();
					EnableEtihadFrame();
					query = "SELECT CUST_ID,EMBOSS_NAME,CARD_TYPE_DESC,STATUS_DESC,ISSUANCE_DATE,EXPIRY_DATE FROM"
							+ " USR_0_DEBITCARD_EXISTING_CPD WHERE WI_NAME = '"+sWorkitemId+"'";		
					List<List<String>> recordList = formObject.getDataFromDB(query);
					loadListView(recordList,"CUST_ID,EMBOSS_NAME,CARD_TYPE_DESC,STATUS_DESC,ISSUANCE_DATE,EXPIRY_DATE"
							,ACC_INFO_EDC_LVW);	
					if(formObject.getValue(EXISTING_ETIHAD_CUST).toString().equalsIgnoreCase("Yes")){
						enableControls(new String[] {ETIHAD_NO,BTN_ECD_VALIDATE});
					}
					else {
						formObject.setValue(ETIHAD_NO,"");
						disableControls(new String[] {ETIHAD_NO,BTN_ECD_VALIDATE});
					}							
				}
				catch(Exception e){
					logError("onTabClickCPDThreeStep",e);
				}
			} else if(selectedSheetIndex == 11) {
				formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
				logInfo("onTabClickCPDThreeStep","INSIDE Optional Product");//5
				disableControls(new String[]{SEC_OPT_PROD,SEC_OPT_MI,SEC_OPT_OI});
				formObject.setStyle("Frame41", DISABLE, TRUE);  

			}else if(selectedSheetIndex == 13){
				formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
				logInfo("onTabClickCPDThreeStep","INSIDE Category change");
				String query = "";
				List<List<String>> output;
				query = "SELECT CUST_SEG,RM_NAME,RM_CODE FROM USR_0_CUST_TXN WHERE WI_NAME ='"+sWorkitemId+"' AND CUST_SNO='1'";
				logInfo("onTabClickCPDThreeStep","query"+query);
				output = formObject.getDataFromDB(query);
				logInfo("onTabClickCPDThreeStep","output"+output);
				formObject.addItemInCombo(OLD_RM_NAME_CAT_CHANGE, output.get(0).get(1));
				formObject.addItemInCombo(OLD_RM_CODE_CAT_CHANGE, output.get(0).get(2));
				setValuesFromDB (output,new String []{OLD_CUST_SEGMENT,OLD_RM_NAME_CAT_CHANGE,OLD_RM_CODE_CAT_CHANGE});
				formObject.setValue(MAKER_DEPARMENT_CAT_CHANGE,formObject.getValue(MAKER_DEPT).toString());
				formObject.setValue(MAKER_NAME_CAT_CHANGE,formObject.getValue(MAKER_NAME).toString());
				formObject.setValue(MAKER_CODE_CAT_CHANGE,formObject.getValue(MAKER_CODE).toString());
				String sReqType = formObject.getValue(REQUEST_TYPE).toString();
				logInfo("onTabClickCPDThreeStep","sReqType"+sReqType);
				if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only")) {	
					formObject.setStyle(SEC_SI, DISABLE, TRUE);
				}
				if(sReqType.equalsIgnoreCase("New Account with Category Change") ||
						sReqType.equalsIgnoreCase("Category Change Only")){
					String sCID = formObject.getTableCellValue(ACC_RELATION,1,2);
					query = "SELECT MAX(CATEGORY_CHANGE_DATE) AS CATEGORY_CHANGE_DATE FROM USR_0_CUST_TXN "
							+ "WHERE CUST_ID ='"+sCID+"'";
					logInfo("onTabClickCPDThreeStep","query"+query);
					addDataInComboFromQuery(query,LAST_CAT_CAHNGE_DATE);

				}
				if(sReqType.equalsIgnoreCase("New Account with Category Change")){
					formObject.setValue(SOURCE_NAME_CAT_CHANGE,formObject.getValue(SOURCE_NAME).toString());
					formObject.setValue(SOURCE_CODE_CAT_CHANGE,formObject.getValue(SOURCE_CODE).toString());
					disableControls(new String []{SOURCE_NAME_CAT_CHANGE,SOURCE_CODE_CAT_CHANGE,BTN_CC_SRC});
				}
				if(formObject.getValue(IS_CAT_BENEFIT_OTHER).toString().equalsIgnoreCase("False")){
					disableControls(new String []{CAT_BENEFIT_OTHER,IS_CAT_BENEFIT_OTHER});
					if(!formObject.getValue(NEW_CUST_SEGMENT).toString().equalsIgnoreCase("")){
						formObject.setStyle(IS_CAT_BENEFIT_OTHER,DISABLE,FALSE);
						manageCategoryChangeSection();
					}
					else {
						disableControls(new String []{IS_SALARY_TRANSFER_CAT_CHANGE,IS_MORTAGAGE_CAT_CHANGE,
								IS_INSURANCE_CAT_CHANGE, IS_TRB_CAT_CHANGE,IS_OTHERS_CAT_CHANGE,IS_VVIP,
								IS_PREVILEGE_TP_CAT_CHANGE,IS_ENTERTAINMENT_CAT_CHANGE,IS_SHOPPING_CAT_CHANGE,
								IS_SPORT_CAT_CHANGE,IS_TRAVEL_CAT_CHANGE,IS_EXCELLENCY_TP_CAT_CHANGE});
					}
				} else {
					enableControls(new String []{CAT_BENEFIT_OTHER,IS_CAT_BENEFIT_OTHER});
					manageCategoryChangeSection();
				}
				if(formObject.getValue(NEW_CUST_SEGMENT).toString().equalsIgnoreCase("")) {
					formObject.clearCombo(PROMO_CODE_CAT_CHANGE);
					formObject.setValue(PROMO_CODE_CAT_CHANGE,"");	
					formObject.clearCombo(EXCELLENCY_CENTER_CC);
					formObject.setValue(EXCELLENCY_CENTER_CC,"");	
				}
			} else if(selectedSheetIndex == 14){
				formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
				logInfo("onTabClickCPDThreeStep","INSIDE Standing Instruction");
				loadSICombos();
				//populateStndInstr();
				saveStandingInstrInfo();
				saveStandInstrInfo();
			}  else if(selectedSheetIndex == 12) {
				formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
				logInfo("onTabClickCPDThreeStep","INSIDE Delivery Preference");
				String query = "";
				List<List<String>> output;
				if((formObject.getValue(CHANNEL_TYPE).toString().equalsIgnoreCase("Alternate"))
						&&!(formObject.getValue(SOURCING_CHANNEL).toString().equalsIgnoreCase("DFC"))){
					query = "SELECT DELIVERY_MODE FROM USR_0_DELIVERY_MODE WHERE CODE='ARD'";
					logInfo("onTabClickCPDThreeStep","query"+query);
					formObject.setStyle(DEL_DELIVERY_MODE,DISABLE,FALSE);
					formObject.clearCombo(DEL_DELIVERY_MODE);
					formObject.addItemInCombo(DEL_DELIVERY_MODE,"Arrange Deliver");
					logInfo(DEL_DELIVERY_MODE,""+formObject.getValue(DEL_DELIVERY_MODE).toString());
					formObject.setStyle(DEL_DELIVERY_MODE,DISABLE,TRUE);
					formObject.clearCombo(DI_CODE);
					query = "select code from usr_0_delivery_mode where delivery_mode ="
							+ "'"+formObject.getValue(DEL_DELIVERY_MODE).toString()+"'";
					logInfo("onTabClickCPDThreeStep","query"+query);
					addDataInComboFromQuery(query,DI_CODE);

					if(formObject.getValue(DI_CODE).toString().equalsIgnoreCase("ARD")){
						query = "SELECT FINAL_STATE FROM USR_0_CUST_TXN WHERE WI_NAME ='"+sWorkitemId+"'"
								+ " AND CUST_SNO ='1'"; // why static 1?
						output = formObject.getDataFromDB(query);
						query = "Select CURRENCY from USR_0_CURRENCY order by 1";
						String sQuery = "select to_char(branch_name) AS BRANCH_NAME from USR_0_DELIVRY_BRANCH_MAPPING"
								+ " where emirates_name ='"+output.get(0).get(0)+"' AND delivery_mode='ARD'";
						output = formObject.getDataFromDB(sQuery);
						String sBranch_name = output.get(0).get(0);
						formObject.setStyle(BRNCH_OF_INSTANT_ISSUE ,DISABLE,FALSE);
						int count = getListCount(BRNCH_OF_INSTANT_ISSUE);
						for(int i = count;i>0;i--)	{
							formObject.removeItemFromCombo(BRNCH_OF_INSTANT_ISSUE, (i+1)-count);
						}
						formObject.addItemInCombo(BRNCH_OF_INSTANT_ISSUE,sBranch_name);
					}
					if(!(formObject.getValue(DEL_DELIVERY_MODE).toString().equalsIgnoreCase("Collect"))) {
						setMailingAddInToDel();
					}
					Delivery_Preferences_Tab();
					LoadInstantBranchValue();
				}
				else {
					if(!(formObject.getValue(DEL_DELIVERY_MODE).toString().equalsIgnoreCase("Collect"))) {
						setMailingAddInToDel();
					}
					Delivery_Preferences_Tab();
					LoadInstantBranchValue();
				}

				formObject.setStyle(BRNCH_OF_INSTANT_ISSUE,DISABLE ,TRUE);
			//} else if(selectedSheetIndex == 18) {
			} else if(selectedSheetIndex == 19) {// Changes for Family Banking
				String decision = "";
				formObject.setStyle(BTN_SUBMIT,DISABLE,FALSE);
				logInfo("onTabClickCPDThreeStep","INSIDE Decision");
				disableControls(new String []{IS_COMPLIANCE_NAME_SCR,IS_COMPLIANCE_RISK_ASSESS,IS_PROD_APP_REQ
						,IS_CALL_BACK_REQ});
				hideControls(new String[]{L1_APP_REQ,L2_APP_REQ,L3_APP_REQ,L4_APP_REQ});
				logInfo("onTabClickCPDThreeStep","hiding controls of levels");
				String query = "";
				String count = "";
				List<List<String>> output;
//				query = "select PREV_WS_NAME from EXT_AO where WI_NAME='"+sWorkitemId+"'";
				query = "select IS_COMPLIANCE_VISITED from EXT_AO where WI_NAME='"+sWorkitemId+"'";
				logInfo("onTabClickCPDThreeStep","query: "+query);
				output = formObject.getDataFromDB(query);
				logInfo("onTabClickCPDThreeStep","output: "+output);
				logInfo("onTabClickCPDThreeStep","FINAL_RISK_VAL_CPD: "+formObject.getValue(FINAL_RISK_VAL_CPD).toString());
				if(!(formObject.getValue(FINAL_RISK_VAL_CPD).toString().equalsIgnoreCase("Neutral Risk") || 
					    (formObject.getValue(FINAL_RISK_VAL_CPD).toString().equalsIgnoreCase("Medium Risk")))
						&& !output.get(0).get(0).equalsIgnoreCase("Yes")) {
					formObject.setValue(IS_COMPLIANCE_RISK_ASSESS, "true");
					formObject.setStyle(IS_COMPLIANCE_RISK_ASSESS, DISABLE, TRUE);
					decision = "Send To Compliance";
					sendToCompliance = true;
					logInfo("onTabClickCPDThreeStep","sendToCompliance: "+sendToCompliance);
					if(formObject.getValue(CPD_TRSD_SYS_CALC_RES).toString().equalsIgnoreCase("Match Found")) {
//						formObject.setStyle(CRO_DEC, DISABLE, TRUE);
						if(!formObject.getValue(CPD_TRSD_FINAL_DECISION).toString().equalsIgnoreCase("Returned")) {
							//formObject.setStyle(CRO_DEC, DISABLE, TRUE);//14sep2021
						}
					}
				} else {
					sendToCompliance = false;
					logInfo("onTabClickCPDThreeStep","sendToCompliance else: "+sendToCompliance);
					query = "select count(WI_NAME) as count from USR_0_RISK_ASSESSMENT_DATA_CPD where approval_req='Yes' "
							+ "and wi_name='"+sWorkitemId+"'";
					logInfo("onTabClickCPDThreeStep","query"+query);
					output = formObject.getDataFromDB(query);
					logInfo("onTabClickCPDThreeStep","output"+output);
					count = output.get(0).get(0);
					try {
						if(Integer.parseInt(count)>0) {
							formObject.setValue(IS_COMPLIANCE_RISK_ASSESS, "true");
						} else {
							formObject.setValue(IS_COMPLIANCE_RISK_ASSESS, "false");
						}
					} catch(Exception e) {
						logError("onTabClickCPDThreeStep",e);
					}
					if(formObject.getValue(CPD_TRSD_SYS_CALC_RES).toString().equalsIgnoreCase("Match Found") && 
							formObject.getValue(CPD_TRSD_FINAL_DECISION).toString().equalsIgnoreCase("Pending")) {
						formObject.setValue(CRO_DEC, "Approve");
						//formObject.setStyle(CRO_DEC, DISABLE, TRUE);//14sep2021
					}
				}				
				query = "SELECT COUNT(WI_NAME) AS COUNT FROM USR_0_CUST_TXN WHERE (BANK_DEC_CPD != 'Approved' "
						+ "AND BANK_DEC_CPD IS NOT NULL) AND WI_NAME ='"+sWorkitemId+"'";
				logInfo("onTabClickCPDThreeStep","query: "+query);
				output = formObject.getDataFromDB(query);
				logInfo("onTabClickCPDThreeStep","output: "+output);
				count = output.get(0).get(0);
				if(Integer.parseInt(count)>0){
					formObject.setValue(IS_COMPLIANCE_NAME_SCR, "true");
				} else {
					formObject.setValue(IS_COMPLIANCE_NAME_SCR, "false");
				}
				try {
					hideControls(new String[]{DOC_APPROVAL_OBTAINED,COURT_ORD_TRADE_LIC});
					query = "SELECT DOB FROM ACC_RELATION_REPEATER WHERE WI_NAME ='"+sWorkitemId+"'";	
					logInfo("onTabClickCPDThreeStep","query"+query);
					output = formObject.getDataFromDB(query);
					logInfo("onTabClickCPDThreeStep","output"+output);
					String minorDOB = output.get(0).get(0);
					logInfo("onTabClickCPDThreeStep","minorDOB"+minorDOB);
					int minorAge = CalculateAge(minorDOB);
					if((minorAge >= 18) && (minorAge <= 21)){
						query = "SELECT COUNT(1) AS COUNT FROM ACC_RELATION_REPEATER WHERE acc_relation ="
								+ " 'Guardian' AND WI_NAME ='"+sWorkitemId+"'";
						output = formObject.getDataFromDB(query);
						String guardianCount = output.get(0).get(0);
						if("0".equalsIgnoreCase(guardianCount)){
							showControls(new String[]{DOC_APPROVAL_OBTAINED,COURT_ORD_TRADE_LIC});
							formObject.setStyle(DOC_APPROVAL_OBTAINED, DISABLE, FALSE);
							formObject.setStyle(COURT_ORD_TRADE_LIC, DISABLE, FALSE);
						}
					}
					query = "SELECT PRODUCT_CODE FROM USR_0_PRODUCT_OFFERED WHERE WI_NAME ='"+sWorkitemId+"'";	
					logInfo("onTabClickCPDThreeStep","query"+query);
					output = formObject.getDataFromDB(query);
					logInfo("onTabClickCPDThreeStep","output"+output);
					for(int i=0; i<output.size(); i++) {
						String sProduct = output.get(i).get(0);
						logInfo("onTabClickCPDThreeStep","sProduct"+sProduct);
						if("871".equalsIgnoreCase(sProduct)){
							hideControls(new String[]{DOC_APPROVAL_OBTAINED,COURT_ORD_TRADE_LIC});
							break;
						}	
					}										
				} catch (Exception e) {
					logError("onTabClickCPDThreeStep",e);
				}
				try {
					query = "SELECT COUNT(A.WI_NAME) IS_MOB_CHANGE FROM USR_0_CUST_TXN A, ACC_RELATION_REPEATER B"
							+ " WHERE A.WI_NAME='"+ sWorkitemId +"' AND A.WI_NAME=B.WI_NAME "
							+ " AND A.CUST_SNO = B.SNO AND B.BANK_RELATION='Existing' "
							+ "AND A.final_mobile_no <> nvl(A.fcr_mobile_no,0) and a.final_mobile_no"
							+ " <> nvl(a.AFTER_CONT_CNTR_MOB_NO,0)";
					logInfo("onTabClickCPDThreeStep","query"+query);
					output = formObject.getDataFromDB(query);
					logInfo("onTabClickCPDThreeStep","output"+output);
					String isMobChange = output.get(0).get(0);
					logInfo("onTabClickCPDThreeStep","isMobChange"+isMobChange);
					if(Integer.parseInt(isMobChange)>0){
						formObject.setValue(MOBILE_CHANGE_FLAG,"True");
						formObject.setValue(IS_CALL_BACK_REQ, "true");
					}
					else {
						formObject.setValue(MOBILE_CHANGE_FLAG,"False");
					}
				} catch(Exception e) {
					logError("onTabClickCPDThreeStep",e);
				}
				formObject.clearCombo(CRO_DEC);
				int iCount1 = getListCount(CRO_DEC);
				logInfo("onTabClickCPDThreeStep","iCount"+iCount1);
				if(iCount1==0 || iCount1==1) {
					query = "Select to_char(WS_DECISION) from USR_0_DECISION_MASTER where ws_name "
							+ "= '"+formObject.getValue(CURR_WS_NAME).toString()+"'";
					addDataInComboFromQuery(query, CRO_DEC);
				}
				int iCount = getListCount(CRO_REJ_REASON);
				logInfo("onTabClickCPDThreeStep","iCount	"+iCount);
				if(iCount==0 || iCount==1){
					query = "Select to_char(ws_rej_reason) from usr_0_rej_reason_mast";
					addDataInComboFromQuery(query, CRO_REJ_REASON);
				} 
				if(decision.equalsIgnoreCase("Send To Compliance") && formObject.getValue("IS_UAE_PASS_AUTH_DONE").toString().equalsIgnoreCase("Y")) {
					formObject.setValue(CRO_DEC, "Send To Compliance");
				}
				confirmUAEPassAuthDone();
			} else if(selectedSheetIndex == 17) {
				fbValidation();	//family  banking tab change check
				clearFBData(); //Clearing FB tab on category change
			}
		} catch(Exception e){
			logError("onTabClickCPDThreeStep",e);
		}
	}
		
	public void CalculateCustomerRiskCPD() {
		try {
			String sQuery ="SELECT BANK_DEC_CPD FROM USR_0_CUST_TXN WHERE WI_NAME='"+sWorkitemId+"' AND BANK_DEC_CPD IS "
					+ "NOT NULL";
			List<List<String>> sOutput=formObject.getDataFromDB(sQuery);
			logInfo("","sOutput---998"+sOutput);
			int iTotalRetrived = sOutput.size();
			//String[] sRiskArray;
			String sFinalRisk ="";
			if(iTotalRetrived>0 && !(sOutput.get(0).get(0)).equalsIgnoreCase("")) {
				logInfo("","inside Calculate CPD---998");
				//sRiskArray = sOutput.get(0).get(0).split(",");
				for(int iLoop=0;iLoop<iTotalRetrived;iLoop++) {
					logInfo("","inside for Calculate CPD---998");
					if(sOutput.get(iLoop).get(0).equalsIgnoreCase("Not Approved")) {
						logInfo("","inside not eligble CPD---998");
						sFinalRisk = "Not Eligible";
						break;
					} else if(sOutput.get(iLoop).get(0).equalsIgnoreCase("Further Approval Required")) {
						logInfo("","inside apprved CPD---998");
						sFinalRisk = "Approval Required";
					} else if(sOutput.get(iLoop).get(0).equalsIgnoreCase("Pending")) {
						logInfo("","inside apprved CPD---998");
						sFinalRisk = "Pending";
					} else if(sOutput.get(iLoop).get(0).equalsIgnoreCase("Approved")) {
						logInfo("","inside 1 eligble CPD---998");
						if(!sFinalRisk.equalsIgnoreCase("Approval Required"))
						{
							logInfo("","inside 2 eligble CPD---998");
							sFinalRisk = "Eligible";
						}
					}
				}
			}
			logInfo("","sFinalRisk---"+sFinalRisk);
			formObject.setValue("FINAL_CUSTOMER_RISK_CPD",sFinalRisk);

			if(sFinalRisk.equalsIgnoreCase("Not Eligible") || sFinalRisk.equalsIgnoreCase("Pending"))
			{
				//formObject.setNGForeColor("AO_CUSTOMER_RISK_CPD",255);
			}
			else
			{
				//formObject.setNGForeColor("AO_CUSTOMER_RISK_CPD",65280);
			}
		} catch (Exception e) {
			logError("CalculateCustomerRiskCPD", e);
		}
	}
 
	public void CalculateAppRiskCPD() {	
		try {
			String sQuery ="SELECT CURRENT_RISK_BUSSINESS FROM USR_0_RISK_ASSESSMENT_DATA_CPD WHERE WI_NAME='"
					+sWorkitemId+"'";
			List<List<String>> sOutput=formObject.getDataFromDB(sQuery);
			logInfo("CalculateAppRiskCPD","sOutput----"+sOutput);
			int iTotalRetrived = sOutput.size();
			logInfo("CalculateAppRiskCPD","iTotalRetrived......."+iTotalRetrived);
			//String[] sRiskArray;
			String sFinalRisk ="";
			if(iTotalRetrived>0 && !sOutput.get(0).get(0).equalsIgnoreCase("")) {
				//sRiskArray = sOutput.get(0).get(0).split(",");
				for(int iLoop=0;iLoop<iTotalRetrived;iLoop++) {
					if(sOutput.get(iLoop).get(0).equalsIgnoreCase("Unacceptable Risk")) {
						sFinalRisk = "Unacceptable Risk";
						break;
					}
					else if(sOutput.get(iLoop).get(0).equalsIgnoreCase("PEP")) {
						sFinalRisk = "PEP";
					}
					else if(sOutput.get(iLoop).get(0).equalsIgnoreCase("Increased Risk")) {
						if(!sFinalRisk.equalsIgnoreCase("UAE-PEP")) {
							sFinalRisk = "Increased Risk";
						}
					} else if(sOutput.get(iLoop).get(0).equalsIgnoreCase("Neutral")) {
						if(!sFinalRisk.equalsIgnoreCase("UAE-PEP") && !sFinalRisk.equalsIgnoreCase("Increased Risk")) {
							sFinalRisk = "Neutral Risk";
						}
					} else if(sOutput.get(iLoop).get(0).equalsIgnoreCase("UAE-PEP")) {
						sFinalRisk = "UAE-PEP";
					} else if(sOutput.get(iLoop).get(0).equalsIgnoreCase("Non UAE-PEP")) {
						sFinalRisk = "Non UAE-PEP";
					}	else if(sOutput.get(iLoop).get(0).equalsIgnoreCase("Medium Risk")) {
						sFinalRisk = "Medium Risk";
					}

				}
			}
			if(!sFinalRisk.equalsIgnoreCase("")) {
				formObject.setValue(FINAL_RISK_VAL_CPD,sFinalRisk);
			} else {	
				String sQueryy="select max(CUST_CUR_RISK_BANK) as CUST_CUR_RISK_BANK from usr_0_risk_data where "
						+ "(entrydatetime) in (select max(entrydatetime) from usr_0_risk_data  where wi_name='"+sWorkitemId+"' and CUST_CUR_RISK_BANK is not null) and wi_name='"+sWorkitemId+"'";
				List<List<String>> sOutputt=formObject.getDataFromDB(sQueryy);

				logInfo("CalculateAppRiskCPD","sQueryy....."+sQueryy);
				logInfo("CalculateAppRiskCPD","sOutputt....."+sOutputt);
				String sCustCurRiskBank=sOutputt.get(0).get(0);
				logInfo("CalculateAppRiskCPD","sCustCurRiskBank...CH_19042017.."+sCustCurRiskBank);
				if(!sCustCurRiskBank.equalsIgnoreCase("")) {
					if(sCustCurRiskBank.equalsIgnoreCase("Unacceptable Risk")) {
						sFinalRisk = "Unacceptable Risk";
					} else if(sCustCurRiskBank.equalsIgnoreCase("PEP")) {
						sFinalRisk = "PEP";
					} else if(sCustCurRiskBank.equalsIgnoreCase("Increased Risk")) {
						if(!sFinalRisk.equalsIgnoreCase("UAE-PEP")) {
							sFinalRisk = "Increased Risk";
						}
					} else if(sCustCurRiskBank.equalsIgnoreCase("Neutral")) {
						if(!sFinalRisk.equalsIgnoreCase("UAE-PEP") && !sFinalRisk.equalsIgnoreCase("Increased Risk")) {
							sFinalRisk = "Neutral Risk";
						}
					} else if(sCustCurRiskBank.equalsIgnoreCase("UAE-PEP")) {
						sFinalRisk = "UAE-PEP";
					} else if(sCustCurRiskBank.equalsIgnoreCase("Non UAE-PEP")) {
						sFinalRisk = "Non UAE-PEP";
					}	else if(sCustCurRiskBank.equalsIgnoreCase("Medium Risk")) {
						sFinalRisk = "Medium Risk";
					}
					formObject.setValue(FINAL_RISK_VAL_CPD,sFinalRisk);
				}
			}
			//end edit by mohit on 19042017
			if(sFinalRisk.equalsIgnoreCase("Unacceptable Risk")) {
				//formObject.setNGForeColor(FINAL_RISK_VAL_CPD,255);
			} else {
				//formObject.setNGForeColor(FINAL_RISK_VAL_CPD,65280);
			}
		} catch (Exception e) {
			logError("CalculateAppRiskCPD", e);
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
	
	//function - Ayush
	
	public Boolean noChequeBookForResidenceWithoutEida() {
		logInfo("noChequeBookForResidenceWithoutEida","noChequeBookForResidenceWithoutEida starts");
		String eidaCheck="";
		String chqBookVal="";
		try{
			eidaCheck = formObject.getValue(DRP_RESEIDA).toString();
			logInfo("noChequeBookForResidenceWithoutEida","eidaCheck :"+eidaCheck);
			if((!"".equalsIgnoreCase(eidaCheck) || null != eidaCheck) && "Yes".equalsIgnoreCase(eidaCheck) ){
				//NGRepeater objChkRepeaterforprods = formObject.getNGRepeater("acc_repeater");
				int iRows = getGridCount(PRODUCT_QUEUE);//objChkRepeaterforprods.getRowCount();
				logInfo("noChequeBookForResidenceWithoutEida","rows :"+iRows);
				for(int i=1;i<iRows;i++){
					chqBookVal = formObject.getTableCellValue(PRODUCT_QUEUE, i,6);//(i,PRODUCT_QUEUE.CHEQUE_BOOK);
					logInfo("noChequeBookForResidenceWithoutEida","chqBookVal :"+chqBookVal);
					if(chqBookVal.equalsIgnoreCase("Yes")){
						logInfo("noChequeBookForResidenceWithoutEida","inside return block");
						sendMessageValuesList("","Please select no for cheque book as residence without eida flag value is yes");
						//objChkRepeaterforprods.setFocus(i,"AO_PRODUCT_QUEUE.CHEQUE_BOOK");
						return false;
					}
				}
			}
		}catch(Exception e){
			logInfo("noChequeBookForResidenceWithoutEida",e.toString());
		}
		logInfo("noChequeBookForResidenceWithoutEida","noChequeBookForResidenceWithoutEida ends");
		return true;
	}
	
	/*private boolean submitCPDThreeStepWorkItem(String data){
		int iSelectedRows = 0;//BY KISHAN
		int iProcessedCustomer=Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1; 
		int iSelectedRow =0;
		iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
		logInfo("","data iSelectedRow %%%"+iSelectedRow);
		logInfo("","data iProcessedCustomer %%%"+iProcessedCustomer);
		String sBankRelation = formObject.getTableCellValue(ACC_RELATION, 0, 7);//objChkRepeater.getValue(iSelectedRow,"AO_ACC_RELATION.BANK_RELATION");									
		String sCID = formObject.getTableCellValue(ACC_RELATION, 0, 2);//objChkRepeater.getValue(iSelectedRow,"AO_ACC_RELATION.CID");
		String sCASA= "";
		if(data.contains("%%%")) {
			logInfo("","data contains %%%"+data);
			String[] dataArr = data.split("%%%");
				logInfo("submitWorkitem","INSIDE submitWorkitem");
				if(isControlVisible(DOC_APPROVAL_OBTAINED) && isControlVisible(COURT_ORD_TRADE_LIC)){
				if (formObject.getValue(DOC_APPROVAL_OBTAINED).toString().equalsIgnoreCase("false")
				 && formObject.getValue(COURT_ORD_TRADE_LIC).toString().equalsIgnoreCase("false")) {
					logInfo("","inside if--------Please select the appropriate checkbox to complete the validation");
					sendMessageValuesList("", "Please select the appropriate checkbox to complete the validation");
					return false;
				}
				}
				if(formObject.getValue(SOURCING_CHANNEL).toString().equalsIgnoreCase("LAPS")) {
					int iCount =0;
					String query1 ="SELECT COUNT(*) AS SIGCOUNT FROM USR_0_DOC_DETAILS "
							+ "WHERE WI_NAME='"+sWorkitemId+"' AND DOC_NAME='signature'";
					logInfo("submitWorkitem","Query-- sigCO---"+query1);		
					List<List<String>> output1=formObject.getDataFromDB(query1);
					iCount = Integer.parseInt(output1.get(0).get(0));
					logInfo("submitWorkitem","iCount is :: "+iCount);
					if(iCount<1) {
						sendMessageValuesList("","Signature Not Captured. Please capture.");
						return false;
					}
				}
				boolean prodChangeFlag ;
				if(!checkProdChngForNoEligibility()){
					logInfo("submitWorkitem","INSIDE checkProdChngForNoEligibility()");
					sendMessageValuesList("","Customer is not eligible for cheque book. Please change the product.");
					return false;
				}
				if(!noChequeBookForResidenceWithoutEida()){
					return false; 
				}			
				try {
					updateTRSDDecision();
				} catch (Exception e) {
					logError("",e);
				}
				String resultTRSD=	checkTRSDResult("CPD");
				String resultTRSD1=	checkTRSDResult1("CPD");				
				logInfo("","resultTRSD CPD:"+resultTRSD1);
				
				if(resultTRSD.equalsIgnoreCase("1")  
				&& !formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Return to Originator")
				&& "Returned".equalsIgnoreCase(resultTRSD1)){
					sendMessageValuesList("","This application can only be Returned since TRSD result is Returned.");
					return false;
				}
				if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")
				 ||formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("")){
					logInfo("","CRO_DEC ::"+ formObject.getValue(CRO_DEC).toString());
					if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("New Account")){
						String acc_rel= formObject.getTableCellValue(ACC_RELATION, 0, 9);//objChkRep.getValue(1,"AO_ACC_RELATION.ACC_RELATION");
						String sQuery = "select cust_seg from usr_0_cust_txn WHERE WI_NAME ='"+sWorkitemId+"' 	and cust_sno='1'";
						logInfo("","o/p from queryyyyyy"+sQuery);
						List<List<String>> sOutput_txn = formObject.getDataFromDB(sQuery);
						logInfo("","AP select ::"+sOutput_txn.get(0).toString());
						String pri_cust_cat =sOutput_txn.get(0).get(0);
						if(!(acc_rel.equalsIgnoreCase("AUS") && acc_rel.equalsIgnoreCase("POA")) 
						&& pri_cust_cat.equalsIgnoreCase("Signatory")){
							sendMessageValuesList("","Please select another request type for Signatory Customer!!");
							return false ;
						}
					}
				} else if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Reject") 
				  || formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Return to Originator")){
					logInfo("","In Reject");
					formObject.RaiseEvent("WFSave"); //doubt
					formObject.RaiseEvent("WFDone");
					return false; //only return was written here added false too
				}
				boolean result=false;
				if(!checkNatSegment()){
					return false ;
				}
				if(!mandatoryComparisonData()){
					//sCurrTabIndex=1;  doubt
					return false;
				}
				if(!mandatoryIndividualInfo()){
					return false;
				}
				if(!mandatoryContactInfo()){
					return false;
				}
				if(!formObject.getValue(CUST_NATIONALITY).toString().equalsIgnoreCase("United Arab Emirates")){
					if(!mandatoryEmploymentInfo()){
						return false;
					}
				}else{
					if(!formObject.getValue(ED_DATE_OF_JOING).toString().equalsIgnoreCase("")){
						if(!validateFutureDate(ED_DATE_OF_JOING,"Date of Joining")){
							return false;
						}
					}
				}
				if(!MandatoryiKYC_CPD()){
					//sCurrTabIndex=1;
					return false;
				}
				if(!mandatoryAtFatca()){
					return false;
				}
				//logInfo("","TRSDclicked  ----"+TRSDclicked.size());
				boolean check = isControlEnabled(BTN_CPD_TRSD_CHK);
				if(check){
					logInfo("","inside IF CONDITION");
					sendMessageValuesList(BTN_CPD_TRSD_CHK,"Please perform TRSD Check again as some of the customer data was changed.");
					return false;
				}else{
					logInfo("","TRSDD ");
				}
				boolean crscategory = mandatoryCRSCheckcategorychange();
				if(!crscategory && formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve"))
					return false;
				if(!validatePassportType(CHECKBOX_PASSPORT_TYPE_FCR,CHECKBOX_PASSPORT_TYPE_EIDA,CHECKBOX_PASSPORT_TYPE_MANUAL,
					FCR_PASSTYPE,EIDA_PSSTYPE,MANUAL_PASSTYPE,CA018,HD_PASS_TYPE)){
						logInfo("","INSIDE DDE_CUSTACC ValidatePassportType");
						return false;
				}
				boolean result1 = false;
				result1=mandatoryCustScreenCPD();
				if(!mandatoryCustScreenCPD()){
					//sCurrTabIndex=2;
					return false;
				}
				if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("New Account with Category Change") 
					|| formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only")){
					 result =mandatoryCategoryChangeData();
					logInfo("","NEXT_7 result---"+result); 
					if(!mandatoryCategoryChangeData()){
						return false;
					}
					result=checkNatCatSegment(); 
					logInfo("","NEW VALIDATION---"+result);
					if(!checkNatCatSegment()){
						return false;
					}
				}
				try{
					logInfo("","before writing channel_type again------"+formObject.getValue(CHANNEL_TYPE).toString());
					if(formObject.getValue(CHANNEL_TYPE).toString().equalsIgnoreCase("")){
						logInfo("","writing channel_type again------");
						addDataInComboFromQuery("SELECT CHANNEL_TYPE FROM USR_0_SERVICE_CHANNEL"
						+ " WHERE SERVICE_CHANNEL ='"+formObject.getValue(SOURCING_CHANNEL).toString()+"'", CHANNEL_TYPE);
					}
					if((formObject.getValue(CHANNEL_TYPE).toString().equalsIgnoreCase("Alternate"))){
						result =mandatoryDeliveryMode_InstantIssue_OnNext();
						if(!mandatoryDeliveryMode_InstantIssue_OnNext()){
							return false;
						}
					}
				}catch(Exception e){
					logError("",e);
				}
				 iSelectedRows = Integer.parseInt(dataArr[2]);
				if(formObject.getValue(NOM_REQ)==null || formObject.getValue(NOM_REQ)==""){
				}else{
					if(formObject.getValue(NOM_REQ).toString().equalsIgnoreCase("Yes")){
						if(formObject.getValue(EXISTING_NOM_PRSN).toString().equalsIgnoreCase("Yes"))	{
							boolean rtn=nomDetailsUpdate(iSelectedRows);
							if(!rtn){ 
								return false;
							}
						}
					}
					else{
						nomDetailsInsert();
					}
				}
				try{
					String sFinalDOB = getFinalDataComparison(CHECKBOX_DOB_FCR,CHECKBOX_DOB_EIDA,CHECKBOX_DOB_MANUAL
					,FCR_DOB,EIDA_DOB,MANUAL_DOB);
					int age  = calculateAge(sFinalDOB);
					int age1 = CalculateAge1(sFinalDOB);
					String sAccRelation = formObject.getTableCellValue(ACC_RELATION, 0, 9);
					String sQueryy="select VALUE from usr_0_defaultvalue_fcr where name='Minor_Age'";
					List<List<String>> sOutputt=formObject.getDataFromDB(sQueryy);
					logInfo("","sOutputt ::"+sOutputt.get(0).get(0));
					int sMinorAge= Integer.parseInt(sOutputt.get(0).get(0));
					logInfo("","sMinorAge....."+sMinorAge);
					if(sAccRelation.equalsIgnoreCase("Minor")){
						if(age1 >=sMinorAge){			
							if(formObject.getValue(CHECKBOX_DOB_FCR).toString().equalsIgnoreCase("true")){
								sendMessageValuesList(FCR_DOB,"Date Of Birth Should Be Less Than "+sMinorAge+" Years For Minor Customer.");
							}else if(formObject.getValue(CHECKBOX_DOB_EIDA).toString().equalsIgnoreCase("true")){
								sendMessageValuesList(EIDA_DOB,"Date Of Birth Should Be Less Than "+sMinorAge+" Years For Minor Customer.");
							}else if(formObject.getValue(CHECKBOX_DOB_MANUAL).toString().equalsIgnoreCase("true")){
								sendMessageValuesList(MANUAL_DOB,"Date Of Birth Should Be Less Than "+sMinorAge+" Years For Minor Customer.");
							}
							return false;	
						}
					}else { 
						if(!formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only")){
							if(age<18){
								if(formObject.getValue(CHECKBOX_DOB_FCR).toString().equalsIgnoreCase("true")){
									sendMessageValuesList(FCR_DOB,"Date Of Birth Should Be Greater Than or equal to 18 Years.");
								}else if(formObject.getValue(CHECKBOX_DOB_EIDA).toString().equalsIgnoreCase("true")){
									sendMessageValuesList(EIDA_DOB,"Date Of Birth Should Be Greater Than or equal to 18 Years.");
								}else if(formObject.getValue(CHECKBOX_DOB_MANUAL).toString().equalsIgnoreCase("true")){
									sendMessageValuesList(MANUAL_DOB,"Date Of Birth Should Be Greater Than or equal to 18 Years.");
								}
								return false;	
							}
						}
					}
				} catch(Exception e){
					logError("",e);
				}
				if(sBankRelation.equalsIgnoreCase("Existing")){	
					List<List<String>> sOutput=formObject.getDataFromGrid("SELECT COUNT(1) AS COUNT_WI FROM "
							+ "USR_0_PRODUCT_EXISTING WHERE WI_NAME ='"+sWorkitemId+"' AND "
							+ "CUSTOMER_ID ='"+sCID+"' AND ACCOUNT_TYPE ='CASA'");
					sCASA =sOutput.get(0).get(0);						
				}
		} else{
		if(sBankRelation.equalsIgnoreCase("New") || sCASA.equalsIgnoreCase("0")){
			String output=getApplicationRiskInputXML(iProcessedCustomer);
			logInfo("","XML:"+output);
			String outputresult =socket.connectToSocket(output);
			logInfo("","outputresult--in webservice--"+outputresult);
			if(outputresult.equalsIgnoreCase("NO")){
				sendMessageValuesList("", "Selected passport holder and Non UAE Residents, "
						+ "not allowed to open Account");
				return false;
			}else if(outputresult.equalsIgnoreCase("Partial")){
				if(!data.equalsIgnoreCase("partial")){
					getReturnMessage(false,"Partial");
				}else{
				formObject.setValue("NIG_CPDMAKER","YES");
				String updatequery="update USR_0_CUST_TXN set NIGEXCEPTIONCPDMAKER='YES'"
						+ " Where WI_NAME='"+formObject.getValue("WI_Name")+"'";// AND CUST_SNO ='"+sCustNo+"'";
				formObject.saveDataInDB(updatequery);
				logInfo("","Updated Successfully"+updatequery);
				}
			}
		}
		}
		String param=sWorkitemId+"','"+sProcessName;
		List<String> paramlist =new ArrayList<>( );
		paramlist.add (PARAM_TEXT+sWorkitemId);
		paramlist.add (PARAM_TEXT+sProcessName);
		logInfo("","PROC PARAM"+param);    //need to check with yamini
		List<String>  procoutput= formObject.getDataFromStoredProcedure("SP_TRSD_EMAIL_NGF",paramlist);//param,"2");
		logInfo("","procoutput"+procoutput);
		logInfo("submitWorkItem","END OF SUBMITWORKITEM");
		return true;
	
   }*/
	
	//KRITIKA START
/*	public void addDebitCard() {
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
	}
	*/
	
	public void saveAndNextCPDThreeStep(String data){
		logInfo("saveAndNextCPDThreeStep","INSIDE saveAndNextCPDThreeStep");
		try {
//			checkUAEPassAlert();   //shahbaz
//			boolean validate = false;
			ArrayList<Boolean> TRSDclicked=new ArrayList<Boolean>();
			String[] selectedSheetInfo = null ;
			String tabCaption = "";
			int selectedSheetIndex = 0;
			if(data.indexOf(",") != -1){
				selectedSheetInfo = data.split(",");
				selectedSheetIndex = Integer.parseInt(selectedSheetInfo[1]);
			}else{
				selectedSheetInfo = new String[1];
				selectedSheetInfo[0] = data;
				selectedSheetIndex = Integer.parseInt(selectedSheetInfo[0]);
			}
		    tabCaption = selectedSheetInfo[0];
		    String ismandatory = "";
			logInfo("saveAndNextCPDThreeStep","tabCaption: "+tabCaption+", selectedSheetIndex: "+selectedSheetIndex);
			
			boolean result = false;
			if(formObject.getValue(RA_IS_UAE_RESIDENT).toString().equalsIgnoreCase("")){
				calculateResidencyStatus(RA_IS_UAE_RESIDENT);	
			}
			long start_Time1=System.currentTimeMillis();
			if(selectedSheetIndex == 0) {
				logInfo("saveAndNextCPDThreeStep","INSIDE Account Relationship");
				checkUAEPassAlert();   //shahbaz
				savePreAssessmentDetails();    //krishna
				insertDataintoProductGrid();
				String private_Client=formObject.getValue("PRIVATE_CLIENT").toString();	
			 if(private_Client.equalsIgnoreCase("Y")) {
				    getPreAssessmentFlag();
		     int returnCode = mandatoryDocAlert();
			 if (returnCode < 0){
				  sendMessageValuesList("BTN_SUBMIT", "Please Upload/Attach Exception Approval Document");
					//	return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
				   return;
					}else {
						String action = "Approved";
						String reasonForAction = "PreAssesment Detail Approved";
						String preKYCflag = formObject.getValue("PRE_ASSESMENT_FLAG").toString();
						if (preKYCflag.equalsIgnoreCase("No")){
							action = "Reject";
							reasonForAction = "Pre-Assessment Detail Unacceptable hence Rejected";
						}
						String insertDec = "INSERT INTO USR_0_AO_DEC_HIST (WI_NAME, CREATE_DAT, USERNAME, WS_DECISION, WS_COMMENTS) "
								+ " VALUES('"
								+ sWorkitemId
								+ "',SYSDATE,'"+sUserName+"','"+action+"','"+reasonForAction+"')";
						logInfo("Query for insert in hist : ", "" + insertDec);
						formObject.saveDataInDB(insertDec);
				    }
				}
				if(formObject.getValue(SOURCING_CHANNEL).toString().equalsIgnoreCase("LAPS")) {
					int iProcessedCustomer = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
					String sCustID = formObject.getTableCellValue(ACC_RELATION,iProcessedCustomer,2);
					//logInfo("saveAndNextCPDThreeStep","iProcessedCustomer"+iProcessedCustomer);
					//logInfo("saveAndNextCPDThreeStep","sCustID"+sCustID);
					setFCRValueonLoad(sCustID);
					ValidateFATCADetails("Mini");
					ValidateFATCADetails("Main");
				}
				//formObject.RaiseEvent("WFSave");
			} else if(selectedSheetIndex == 1 || selectedSheetIndex == 2 || selectedSheetIndex == 3 ||
					selectedSheetIndex == 4 || selectedSheetIndex == 5) {
				
				if(selectedSheetIndex == 1) {
					if(!noChequeBookForResidenceWithoutEida()) {
						return ; 
					}
					if(!validatePassportType(CHECKBOX_PASSPORT_TYPE_FCR,CHECKBOX_PASSPORT_TYPE_EIDA,CHECKBOX_PASSPORT_TYPE_MANUAL,
							FCR_PASSTYPE,EIDA_PSSTYPE,MANUAL_PASSTYPE,CA018,HD_PASS_TYPE)){
						formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
						return;
					}
					if(!ValidateComparisonDataCombo(CHECKBOX_VISA_STATUS_FCR,CHECKBOX_VISA_STATUS_EIDA,
							CHECKBOX_VISA_STATUS_MANUAL,FCR_VISASTATUS,EIDA_VISASTATUS,MANUAL_VISASTATUS,
							CA019,"Mandatory","Visa Status")){
						return;
					}
					if(!validateVisaStatus()) {
						formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
						return;
					}
					try {
						String sFinalDOB = getFinalDataComparison(CHECKBOX_DOB_FCR,CHECKBOX_DOB_EIDA,CHECKBOX_DOB_MANUAL,
								FCR_DOB,EIDA_DOB,MANUAL_DOB);
						int age = CalculateAge(sFinalDOB);
						int age1= CalculateAge1(sFinalDOB);
						int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
						String sAccRelation= formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 9);
						String accountOwn = formObject.getValue(ACC_OWN_TYPE).toString();
						boolean bresult = false;
						String sQueryy = "select VALUE from usr_0_defaultvalue_fcr where name='Minor_Age'";
						List<List<String>> sOutputt = formObject.getDataFromDB(sQueryy);
						int sMinorAge = Integer.parseInt(sOutputt.get(0).get(0));
						logInfo("saveAndNextCPDThreeStep","sMinorAge: "+sMinorAge);
						if(accountOwn.equalsIgnoreCase("Minor")) {	
							if(age1 >sMinorAge && sAccRelation.equalsIgnoreCase("Minor")) {
								sendMessageValuesList("","For Minor Date Of Birth Should Not Be Greater Than or equal to "+sMinorAge+" Years.");
								bresult = true;
							}
							if(age<sMinorAge && sAccRelation.equalsIgnoreCase("Guardian"))	{
								sendMessageValuesList("","For Guardian Date Of Birth Should Be Greater Than or equal to "+sMinorAge+" Years.");
								bresult = true;
							}
						}
						else if(accountOwn.equalsIgnoreCase("Joint") && age<sMinorAge){
							sendMessageValuesList("","For Joint Date Of Birth Should be greater than or equal to "+sMinorAge+" Years.");
							bresult = true;
						}
						else {
							if(!formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only")){
								if(age<18){
									sendMessageValuesList("","For Single Date Of Birth Should be greater than or equal to 18 Years.");
									bresult = true;
								}
							}
						}
						if(bresult) {
							if(formObject.getValue(CHECKBOX_DOB_FCR).toString().equalsIgnoreCase("true")){
								sendMessageValuesList(FCR_DOB,"Please fill valid DOB");
							}
							else if(formObject.getValue(CHECKBOX_DOB_EIDA).toString().equalsIgnoreCase("true")){
								sendMessageValuesList(EIDA_DOB,"Please fill valid DOB");
							}
							else if(formObject.getValue(CHECKBOX_DOB_MANUAL).toString().equalsIgnoreCase("true")){
								sendMessageValuesList(MANUAL_DOB,"Please fill valid DOB");
							}
							formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);							
							return;	
						}
						logInfo("saveAndNextCPDThreeStep", "end of try");
					} catch(Exception e){
						logError("saveAndNextCPDThreeStep",e);
					}					
					boolean result1=false;
					result1=checkNatSegment();
					logInfo("saveAndNextCPDThreeStep","NEW VALIDATION---"+result1);
					if(!result1) {
						formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
						return ;
					}
					logInfo("saveAndNextCPDThreeStep", "bfr calling mandatoryCustInfoFields");
					//result1 = mandatoryComparisonData(); 
					result1 = mandatoryCustInfoFields();
					logInfo("mandatoryCustInfoFields","mandatoryCustInfoFields---"+result1);
					if(!result1) {
						return;
					}
					String sShortName =getFinalDataComparison(CHECKBOX_SHORTNAME_FCR,CHECKBOX_SHORTNAME_EIDA,CHECKBOX_SHORTNAME_MANUAL,FCR_SHORTNAME,EIDA_SHORTNAME,MANUAL_SHORTNAME).trim();
					if(sShortName !=null  && sShortName.length() > 20){
						sendMessageValuesList(MANUAL_SHORTNAME, "Short name length must be less than or equal to 20");
						return ;
					}
					result1 = mandatoryIndividualInfo(); // 1
					logInfo("mandatoryComparisonData","COMMAND24 Individual Info result---"+result);
					if(!result1) {
						//formObject.setStyle("static_next",DISABLE,FALSE);
						return;
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
							return ;
						}
					}
					catch(Exception e){
						logError("saveAndNextCPDThreeStep",e);
					}
					/*if(!validateMobileNo(CHECKBOX_TELE_MOB_FCR,CHECKBOX_TELE_MOB_EIDA,CHECKBOX_TELE_MOB_MANUAL,
							FCR_MOBILE,EIDA_MOBILE,MANUAL_MOBILE,CA0126)) {
						if(!mobileConfirmFlag || mobileChangeFlag) {
							return;
						}
					}*/
					//private client three step
					showCustSegmentPC();
					savePreAssessmentDetails(); 
				    //DUPLICATE  ACCOUNT ISSUE ATP-433 BEHALF OF AMMENA
					insertDataintoProductGrid();
				  } else if(selectedSheetIndex == 2) {//CA094
					if(formObject.getValue(PD_CUSTSEGMENT).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(PD_CUSTSEGMENT,CA042);
						return;
					}
					logInfo("mandatoryPersonalDetailsFields","Bfr Calling mandatoryPersonalDetailsFields---");
					result = mandatoryPersonalDetailsFields();
					logInfo("mandatoryPersonalDetailsFields","mandatoryPersonalDetailsFields---"+result);
					if(!result){
						return ;
					}
					result = mandatoryContactInfo(); //2
					logInfo("BTN_SUBMIT","COMMAND24 Contact Info result---"+result);
					if(!result) {
						//formObject.setStyle("static_next",DISABLE,FALSE);
						return ;
					}
					String sFinalData = getFinalDataComparison(CHECKBOX_COUNTRY_PER_RES_FCR,CHECKBOX_COUNTRY_PER_RES_EIDA,
							CHECKBOX_COUNTRY_PER_RES_MANUAL,FCR_PER_CNTRY,EIDA_PER_CNTRY,MANUAL_PER_CNTRY);
					if(!sFinalData.equalsIgnoreCase(formObject.getValue(PERM_CNTRY).toString())){
						sendMessageValuesList(PERM_CNTRY,"Permanent Country data is not same. Please change it.");
						return;
					} else  {
						if(!formObject.getValue(ED_DATE_OF_JOING).toString().equalsIgnoreCase("")) {
							if(!validateFutureDate(ED_DATE_OF_JOING,"Date of Joining")) {
								return ;
							}
						}
					}
					if(validate == false && !formObject.getValue(IDS_REF_BY_CUST).toString().equalsIgnoreCase("")){
						sendMessageValuesList(BTN_FCR_SRCH,"Please Validate Referred by customer ID.");
						formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
						return;	
					}
				} else if(selectedSheetIndex == 3) {
					result = MandatoryiKYC_CPD(); // 3
					logInfo("BTN_SUBMIT","KYC result---"+result);
					if(!result) {
						formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
						return ;
					}
					if(!formObject.getValue(CUST_NATIONALITY).toString().equalsIgnoreCase("United Arab Emirates")){
						result = mandatoryEmploymentInfo();
						if(!result){
							formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
							return ;
						}
					}
					else {
						if(!formObject.getValue(ED_DATE_OF_JOING).toString().equalsIgnoreCase("")){
							if(!validateFutureDate(ED_DATE_OF_JOING,"Date of Joining")){
								formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
								return ;
							}
						}
					}
					if(!MandatoryEmploymentAddress()){
						formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
						return ;
					}
					saveKycMultiDropDownData();
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
						logError("saveAndNextCPDThreeStep",e);
					} 
					//}
					logInfo("saveAndNextCPDThreeStep","ismandatory"+ismandatory);
					boolean custSegmentCheck;
					if(ismandatory.equalsIgnoreCase("Yes"))
						custSegmentCheck=true;
					else
						custSegmentCheck=false;
					if(selectedSheetIndex == 4) {
						if (formObject.getValue(CRS_CITYOFBIRTH).toString().isEmpty()) {
							sendMessageValuesList(CRS_CITYOFBIRTH,"Please enter city of birth");
							return ;
						}
						result = mandatoryCRSCheck(custSegmentCheck);
						logInfo("saveAndNextCPDThreeStep","Submit CRS check result"+result);
						if(!result){
							formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
							return ;
						}  
						boolean crscategory=mandatoryCRSCheckcategorychange(); //5
						if(!crscategory && formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")) {
							return ;
						}
					} else if(selectedSheetIndex == 5) {
						result = mandatoryAtFatca();
						logInfo("","Submit validation fatca result now---"+result);
						long end_Time5=System.currentTimeMillis();
						if(!result) {
							formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
							return ;
						}
					}
				}
				/*if(selectedSheetIndex == 5) {
					boolean crscategory=mandatoryCRSCheckcategorychange(); //5
					if(!crscategory && formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")) {
						return ;
					}
				}*/
				/*else if(selectedSheetIndex == 1) {/*
					if(!noChequeBookForResidenceWithoutEida()) {
						return ; 
					}
					if(!validatePassportType(CHECKBOX_PASSPORT_TYPE_FCR,CHECKBOX_PASSPORT_TYPE_EIDA,CHECKBOX_PASSPORT_TYPE_MANUAL,
							FCR_PASSTYPE,EIDA_PSSTYPE,MANUAL_PASSTYPE,CA018,HD_PASS_TYPE)){
						logInfo("saveAndNextCPDThreeStep","INSIDE DDE_CUSTACC ValidatePassportType");
						formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
						return;
					}
					if(!ValidateComparisonDataCombo(CHECKBOX_VISA_STATUS_FCR,CHECKBOX_VISA_STATUS_EIDA,
							CHECKBOX_VISA_STATUS_MANUAL,FCR_VISASTATUS,EIDA_VISASTATUS,MANUAL_VISASTATUS,
							CA019,"Mandatory","Visa Status")){
						return;
					}
					if(!validateVisaStatus()) {
						formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
						return;
					}
					if(validate == false && !formObject.getValue(IDS_REF_BY_CUST).toString().equalsIgnoreCase("")){
						sendMessageValuesList(BTN_FCR_SRCH,"Please Validate Reffered by customer ID.");
						formObject.setStyle("NEXT_1",DISABLE,TRUE);
						return;	
					}
					try {
						String sFinalDOB = getFinalDataComparison(CHECKBOX_DOB_FCR,CHECKBOX_DOB_EIDA,CHECKBOX_DOB_MANUAL,
								FCR_DOB,EIDA_DOB,MANUAL_DOB);
						int age = CalculateAge(sFinalDOB);
						int age1= CalculateAge1(sFinalDOB);
						int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
						String sAccRelation= formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 9);
						String accountOwn = formObject.getValue(ACC_OWN_TYPE).toString();
						boolean bresult = false;
						String sQueryy = "select VALUE from usr_0_defaultvalue_fcr where name='Minor_Age'";
						List<List<String>> sOutputt = formObject.getDataFromDB(sQueryy);
						int sMinorAge = Integer.parseInt(sOutputt.get(0).get(0));
						if(accountOwn.equalsIgnoreCase("Minor")) {	
							if(age1 >=sMinorAge && sAccRelation.equalsIgnoreCase("Minor")) {
								sendMessageValuesList("","For Minor Date Of Birth Should Not Be Greater Than or equal to "+sMinorAge+" Years.");
								bresult = true;
							}
							if(age<sMinorAge && sAccRelation.equalsIgnoreCase("Guardian"))	{
								sendMessageValuesList("","For Guardian Date Of Birth Should Be Greater Than or equal to "+sMinorAge+" Years.");
								bresult = true;
							}
						}
						else if(accountOwn.equalsIgnoreCase("Joint") && age<sMinorAge){
							sendMessageValuesList("","For Joint Date Of Birth Should be greater than or equal to "+sMinorAge+" Years.");
							bresult = true;
						}
						else {
							if(!formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only")){
								if(age<18){
									sendMessageValuesList("","For Single Date Of Birth Should be greater than or equal to 18 Years.");
									bresult = true;
								}
							}
						}
						if(bresult) {
							if(formObject.getValue(CHECKBOX_DOB_FCR).toString().equalsIgnoreCase("true")){
								sendMessageValuesList(FCR_DOB,"");
							}
							else if(formObject.getValue(CHECKBOX_DOB_EIDA).toString().equalsIgnoreCase("true")){
								sendMessageValuesList(EIDA_DOB,"");
							}
							else if(formObject.getValue(CHECKBOX_DOB_MANUAL).toString().equalsIgnoreCase("true")){
								sendMessageValuesList(MANUAL_DOB,"");
							}
							formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);							
							return;	
						}
					}
					catch(Exception e){
						logError("saveAndNextCPDThreeStep",e);
					}
					
					boolean result1=false;
					result=checkNatSegment();
					logInfo("","NEW VALIDATION---"+result);
					if(!result1) {
						formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
						return ;
					}
					result1 = mandatoryComparisonData(); // 1
					logInfo("BTN_SUBMIT","COMMAND24 KYC result---"+result);
					if(!result1) {
						//sCurrTabIndex=1;
						//formObject.setStyle("static_next",DISABLE,FALSE);
						return;
					}

					result1 = mandatoryIndividualInfo(); // 1
					logInfo("BTN_SUBMIT","COMMAND24 Individual Info result---"+result);
					if(!result1) {
						//formObject.setStyle("static_next",DISABLE,FALSE);
						return;
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
							return ;
						}
					}
					catch(Exception e){
						logError("saveAndNextCPDThreeStep",e);
					}
				*////}
				if(!formObject.getValue(PD_DATEOFATTAININGMAT).toString().equalsIgnoreCase("")){
					SimpleDateFormat simpledateformat = new SimpleDateFormat(DATEFORMAT);
					Date d = new Date();
					boolean rtn = compareDateMMM(formObject.getValue(PD_DATEOFATTAININGMAT).toString(),simpledateformat.format(d).toString());
					logInfo("saveAndNextCPDThreeStep","rtn == "+rtn);
					if(!rtn) {
						sendMessageValuesList(PD_DATEOFATTAININGMAT,"Date Of Attaining Majority Should Be Future Date.");
						formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);						
						return;	
					}
				}
				long end_Time6 = System.currentTimeMillis();
				//logInfo("saveAndNextCPDThreeStep","Diff Time for Other Calls---"+Long.toString(end_Time6-end_Time5));
				saveKYCInfo();		
				saveKycMultiDropDownData();
//				savePreAssessmentDetails();    //shahbaz
				long end_Time7 = System.currentTimeMillis();
				logInfo("saveAndNextCPDThreeStep","Diff Time for SaveKYCInfo---"+Long.toString(end_Time7-end_Time6));
				saveIndividualInfo();
				long end_Time8 = System.currentTimeMillis();
				logInfo("saveAndNextCPDThreeStep","Diff Time for SaveIndividualInfo---"+Long.toString(end_Time8-end_Time7));
				saveComparisonData();
				long end_Time9 = System.currentTimeMillis();
				logInfo("saveAndNextCPDThreeStep","Diff Time for saveComparisonData---"+Long.toString(end_Time9-end_Time8));
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
						logError("saveAndNextCPDThreeStep",e);
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
							return ;
						}		
						else if(outputresult.equalsIgnoreCase("Partial")){
							logInfo("saveAndNextCPDThreeStep","outputresult: "+outputresult);
							/*
							int respose = JOptionPane.showConfirmDialog(null,"Selected passport holder"
									+ " Residents does not meet conditions,\nHence not allowed to open Account. Do you still want to proceed with account opening?",null,JOptionPane.YES_NO_OPTION);
							if(respose == JOptionPane.YES_OPTION) {
								formObject.setValue(NIG_CPDMAKER,"YES");
								String updatequery = "update USR_0_CUST_TXN set NIGEXCEPTIONCPDMAKER='YES'"
										+ " Where WI_NAME='"+formObject.getValue(WI_NAME)+"' AND CUST_SNO ='"+sCustNo+"'";
								formObject.saveDataInDB(updatequery);
							}
							else {
								return;
							}
						*/
						}
					}
				}
				catch(Exception e) {
					logError("saveAndNextCPDThreeStep",e);
				}
				if(selectedSheetIndex == 5) {
					if(formObject.getValue(SOURCING_CHANNEL).toString().equalsIgnoreCase("LAPS")) {
						/*callTRSD("CPD");
						if( formObject.getValue(CPD_TRSD_FINAL_DECISION).toString().equalsIgnoreCase("Approved"))
							formObject.setValue(CPD_FINAL_ELIGIBILITY, "Eligible");
						else
							formObject.setValue(CPD_FINAL_ELIGIBILITY, "Not Eligible");
						int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
						String sCustNo = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 0);
						try {
							logInfo("saveAndNextCPDThreeStep","TRSD clicked set true arraylist");
							TRSDclicked.set(Integer.parseInt(sCustNo)-1, true);
							logInfo("saveAndNextCPDThreeStep","TRSD clicked set true arraylist completed");
						}
						catch(Exception e){
							logError("saveAndNextCPDThreeStep",e);
						}
						insertBankDecisionFromTRSD(formObject.getValue(CPD_TRSD_FINAL_DECISION).toString());	//added by harinder
						if(formObject.getValue(CPD_TRSD_FINAL_DECISION).toString().equalsIgnoreCase("Approved")
								|| formObject.getValue(CPD_TRSD_FINAL_DECISION).toString().equalsIgnoreCase("Pending")){
							formObject.setStyle((BTN_CPD_TRSD_CHK),DISABLE,TRUE);
						}*/	
						// Added by reyaz 02-05-2023
						logInfo("CLICK","INSIDE FSK_CHECK data"+data);
						callFSKService("CPD"); 
						if(formObject.getValue("TRSD_FLAG").toString().equalsIgnoreCase("N"))
							formObject.setValue(CPD_FINAL_ELIGIBILITY, "Eligible");
						else{
							formObject.setValue(CPD_FINAL_ELIGIBILITY, "Not Eligible");
						}
						int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
						String sCustNo = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 0);
						try {
							logInfo("saveAndNextCPDThreeStep","TRSD clicked set true arraylist");
							TRSDclicked.set(Integer.parseInt(sCustNo)-1, true);
							logInfo("saveAndNextCPDThreeStep","TRSD clicked set true arraylist completed");
						}
						catch(Exception e){
							logError("saveAndNextCPDThreeStep",e);
						}
						insertBankDecisionFromTRSD(formObject.getValue(CPD_TRSD_FINAL_DECISION).toString());
						logInfo("CPD_TRSD_FINAL_DECISION: "+formObject.getValue(CPD_TRSD_FINAL_DECISION).toString(),"");				
						if(formObject.getValue("TRSD_FLAG").toString().equalsIgnoreCase("N") 
								|| formObject.getValue("TRSD_FLAG").toString().equalsIgnoreCase("Y")) {							
							formObject.setStyle(BTN_CPD_TRSD_CHK,DISABLE,TRUE);
						}  
					} else {   // added date 14/062023
						if(!"Returned".equalsIgnoreCase(formObject.getValue(CPD_TRSD_FINAL_DECISION).toString())){
						   setEnableAndDisableFskButton();
						}
					}
				}
			}
			else if(selectedSheetIndex == 7) {
				int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
				//boolean result2 = false;
				//long start_Time=System.currentTimeMillis();
				/*result = mandatoryCustScreen(); // MandatoryCustScreenCPD()
				long end_time=System.currentTimeMillis();
				if(!result){
					return ;
				}*/ // now commented for CRO
				String sCustNo = formObject.getTableCellValue(ACC_RELATION,iSelectedRow,0);
				String sQuery21 = "update USR_0_BRMS_TRACKER set SCREENING_STATUS = 'Success' where WI_NAME ="
						+ "'"+sWorkitemId+"'AND CUST_SNO='"+sCustNo+"' AND SCREENING_STATUS ='Pending'";
				formObject.saveDataInDB(sQuery21);
				saveScreeningDataCPD();
				long end_time1=System.currentTimeMillis();
			} else if(selectedSheetIndex == 12) {
					if(formObject.getValue(CHANNEL_TYPE).toString().equalsIgnoreCase("")){
						String query = "SELECT CHANNEL_TYPE FROM USR_0_SERVICE_CHANNEL WHERE SERVICE_CHANNEL"
								+ " ='"+formObject.getValue(SOURCING_CHANNEL).toString()+"'";
						logInfo("saveAndNextCPDThreeStep","query"+query);
						addDataInComboFromQuery(query,CHANNEL_TYPE);
					}
					if(formObject.getValue(CHANNEL_TYPE).toString().equalsIgnoreCase("Alternate")){
					boolean result3 = mandatoryDeliveryMode_InstantIssue_OnNext();
					if(!result3){
						return;
					}
				  }
			} else if(selectedSheetIndex == 13){
				//formObject.RaiseEvent("WFSave");
				if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("New Account with Category Change")
						|| formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only"))
				{
					boolean result1 = false;
					result1 = mandatoryCategoryChangeData();
					if(!result1) {
						return ;
					}
					result=checkNatCatSegment();
					if(!result){
						formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
						return ;
					}
				}
			} else if(selectedSheetIndex == 14){
				formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
				logInfo("onTabClickCPDThreeStep","INSIDE Standing Instruction");
				saveStandingInstrInfo();
				saveStandInstrInfo();
			} else if(selectedSheetIndex == 17) { // Family Banking				
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
			logError("Exception in saveAndNextCPDThreeStep ",e);
		}
	}
	
	public Boolean validateSIDate(String date,String controlName) {
		try {					 
			logInfo("validateSIDate","Called validateSIDate ");
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat simpledateformat = new SimpleDateFormat(DATEFORMAT);
			String scurrentDate = simpledateformat.format(calendar.getTime());
			String sDate= formObject.getValue(date).toString();
			if(sDate.equalsIgnoreCase(""))
				return true;
			logInfo("validateSIDate","date :"+sDate + "\n "+ "scurrentDate "+scurrentDate);
			if(!scurrentDate.equals("")) {
				String [] temp =scurrentDate.split("/");
				if(temp[1].length() ==3) {
					scurrentDate=temp[0]+"/"+getMonthNumber(temp[1])+"/"+temp[2];
				} else {
					scurrentDate=temp[0]+"/"+temp[1]+"/"+temp[2];
				}
			}
			try {
				String [] temp =sDate.split("/");
				if(temp.length<3) {
					sendMessageValuesList(date, "Invalid Date");
					return false;
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			Date currentDate = simpledateformat.parse(scurrentDate);
			Date nDate = simpledateformat.parse(sDate);   
			logInfo("validateSIDate","currentDate :"+currentDate +"\n "+"nDate "+nDate);
			if(nDate.compareTo(currentDate) < 0) {
				sendMessageValuesList(date, controlName+""+CA0129);
				return false;
			} else if(nDate.compareTo(currentDate) == 0) {
				sendMessageValuesList(date, controlName+"Date can not be same as system date");
				return false;
			} else if(Integer.parseInt(sDate.substring(sDate.lastIndexOf("/")+1, sDate.length()))>= 2099) {    
				sendMessageValuesList(date, controlName+" Date can not be greater than 2099");
				return false;
			}
		} catch(Exception exp) {
			logInfo("validateSIDate","Caught the exception "+exp.getMessage());
			exp.printStackTrace();
		}	
		return true;
	}

	public boolean validateCentralBankDataCPD(String sGridName) {
		if(formObject.getValue(CPD_NAME).toString().equalsIgnoreCase("")) {
			sendMessageValuesList(CPD_NAME,"Customer Name can not be blank");
			return false;
		} if(formObject.getValue(CPD_NATIONALITY).toString().equalsIgnoreCase("")) {
			sendMessageValuesList(CPD_NATIONALITY,"Nationality can not be blank");
			return false;
		} if(formObject.getValue(CPD_DOB).toString().equalsIgnoreCase("")) {
			sendMessageValuesList(CPD_DOB,"Date of Birth can not be blank");
			return false;
		} if(formObject.getValue(CPD_PASS_NO).toString().equalsIgnoreCase("")) {
			sendMessageValuesList(CPD_PASS_NO,"Passport Number can not be blank");
			return false;
		} if(formObject.getValue(CPD_PERIOD_EXP).toString().equalsIgnoreCase("")) {
			sendMessageValuesList(CPD_PERIOD_EXP,"Period of Expiry can not be blank");
			return false;
		} if(formObject.getValue(CPD_DEPT).toString().equalsIgnoreCase("")) {
			sendMessageValuesList(CPD_DEPT,"Department can not be blank");
			return false;
		}
		int iCount = getGridCount(sGridName);
		for(int row=0;row<iCount;row++) {
			if(formObject.getValue(CPD_NAME).toString().equalsIgnoreCase(formObject.getTableCellValue(sGridName,row,0))
					&& formObject.getValue(CPD_NATIONALITY).toString().equalsIgnoreCase(formObject.getTableCellValue(sGridName,row,1))
					&& formObject.getValue(CPD_DOB).toString().equalsIgnoreCase(formObject.getTableCellValue(sGridName,row,2))
					&& formObject.getValue(CPD_PASS_NO).toString().equalsIgnoreCase(formObject.getTableCellValue(sGridName,row,3))) {
				sendMessageValuesList("","You can not add duplicate row");
				return false;
			}
		}
		return true;
	}
	
	private void onSaveTabClickCPDMakerThree(int data){
		logInfo("onSaveTabClickCPDMakerThree","INSIDE onSaveTabClickCPDMakerThree");
		try{
			checkUAEPassAlert();   //shahbaz
			saveStandingInstrInfo();
			saveStandInstrInfo();
			if(formObject.getValue(NOM_REQ)==null || formObject.getValue(NOM_REQ).toString()==""){
				//empty in previous code moksh
			}else{
				if(formObject.getValue(NOM_REQ).toString().equalsIgnoreCase("Yes"))
				{
					if(formObject.getValue(EXISTING_NOM_PRSN).toString().equalsIgnoreCase("Yes"))	
					{
						boolean flg = nomDetailsUpdate(data);
						if(!flg)
						{
							return;
						}
					}
				}
				else
				{
					nomDetailsInsert();
				}
			}
			saveOfferedProduct("USR_0_PRODUCT_OFFERED_CPD",PROD_SEC_OFRD_CPD_LVW);
			saveFacilitiesData("USR_0_FACILITY_SELECTED_CPD",FAC_LVW_CPD);
			saveOfferedDebitCard("USR_0_DEBITCARD_OFFERED_CPD",FAC_OFRD_LVW_CPD);

			int sOutput = updateDataInDB("USR_0_BRMS_TRACKER","APP_ASSESSMENT_STATUS",
					"'Success'","WI_NAME ='"+sWorkitemId+"' AND APP_ASSESSMENT_STATUS ='Pending'");
			//logInfo("Save Data","sOutput "+sOutput);
			
			boolean prodChangeFlag = checkProdChngForNoEligibility();
			if(!prodChangeFlag)
			{
				sendMessageValuesList("", "Customer is not eligible for cheque book. Please change the product");
				getReturnMessage(false) ;
			}
			//NGRepeater objChkRepeater = formObject.getNGRepeater("REPEAT_FRAME");
			int fieldToFocus=Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;//Rakshita
             //			objChkRepeater.setFocus(fieldToFocus, "AO_ACC_RELATION.SNO"); //MOKSH DOUBT
			logInfo("","fieldToFocus---"+fieldToFocus);
			String currVal = formObject.getValue(ACC_TITLE).toString();
			List<List<String>> output = formObject.getDataFromDB("select acc_title from "+sExtTable+" where "
					+ "wi_name='"+sWorkitemId+"'");
			String prevVal = output.get(0).get(0);
			logInfo("onSaveClick","prevVal: "+prevVal);
			if(!prevVal.equalsIgnoreCase("") && !prevVal.equalsIgnoreCase(currVal)) {
				formObject.setValue("IS_ACC_TITLE_UPDATE", TRUE);
			}
			checkView();
			//saveKYCInfo();	
			//saveComparisonData();
			saveClientQuestionsData(); 
			//saveCRSDetails();
			//saveIndividualInfo();
			//saveIndividualContactInfo();
			//saveDuplicateData(); to be checked moksh
			//if(sCurrTabIndex==4)
			//{
				try {
					insertUdfDetails();
				} catch (Exception e)  {
					e.printStackTrace();//Rakshita
				}
			//}
		} catch(Exception e) {
			logError("Exception in onSaveTabClickCPDMakerThree ",e);
		} 
	}
	
	protected String getDocumentIndex(String sColNanme){
			try {
				String strIndex="";
				String query="select documentindex from pdbdocument where documentindex  in"
							 +"(select documentindex from pdbdocumentcontent where parentfolderindex ="
							 +"(select itemindex from ext_ao where wi_name  = '"+sWorkitemId+"'))"
							 +"and name  = 'aof_ntb'";
				logInfo("getDocumentIndex",""+query);
				List<List<String>> recordList=formObject.getDataFromDB(query);
				if(recordList!=null){
					if(recordList.size()>0)
						strIndex = recordList.get(0).get(0);
				}
				return strIndex;
			} catch (Exception e) {
				logError("",e);
				return "";
			}
    }
	
	private void prodGroupChange() {		
		if(formObject.getValue(ACC_INFO_PG).toString().equalsIgnoreCase("")){
			formObject.setValue(GROUP_TYPE,"");
			formObject.setValue(CARD_TYPE,"");
			formObject.setValue(EMBOSS_NAME,"");
			formObject.setValue(NEW_LINK,"");
			formObject.setValue(EXISTING_CARD_NO,"");
		} else if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("New Account With Category Change") 
				&& formObject.getValue(NEW_CUST_SEGMENT).toString().equalsIgnoreCase("")) {
			sendMessageValuesList(NEW_CUST_SEGMENT,"Please select New Category.");
		} else {
			String[] sSelectedProduct = formObject.getValue(ACC_INFO_PG).toString().split("_");
			String sQuery  = "SELECT COD_PROD_TYPE FROM USR_0_PRODUCT_MASTER WHERE PRODUCT_CODE ='"
					+sSelectedProduct[1]+"'";
			logInfo("ACC_INFO_PG","sQuery: "+sQuery);
			List<List<String>> sOutput = formObject.getDataFromDB(sQuery);
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
					//return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
					return;
				}
			}
			if(sGroup.isEmpty()) {
				formObject.setValue(CARD_TYPE,"");
				formObject.setValue(NEW_LINK,"");
				enableControls(new String[]{CARD_TYPE, NEW_LINK});
//				return getReturnMessage(false);
				return;
			}
			for(int i=0; i<iRows; i++) {
				sGroupType = formObject.getTableCellValue(QUEUE_DC, i, 1).toString();
				logInfo("GROUP_TYPE", "sGroupType: "+sGroupType+", sGroup: "+sGroup);
				if(sGroupType.equalsIgnoreCase(sGroup)) {
					iCount = iCount+1;
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
	
	private void rdInstDel() {
		if(formObject.getValue(RD_INST_DEL).toString().equalsIgnoreCase("No")) {
			FETCH_INFO_flag_NO=false;
			FETCH_INFO_flag=false;
			formObject.setStyle(NOM_REQ,DISABLE,TRUE);
			formObject.setStyle(EXISTING_NOM_PRSN,DISABLE,TRUE);
			formObject.clearTable(DELIVERY_PREFERENCE);
			int sOut1=updateDataInDB(sExtTable,INSTANT_DEL_NO,"'true'","WI_NAME ='"+sWorkitemId+"'");
			logInfo("","Update query output sout-----"+sOut1);
			int sOutt1=updateDataInDB(sExtTable,INSTANT_DEL_YES,"'false'","WI_NAME ='"+sWorkitemId+"'");
		} else if (formObject.getValue(RD_INST_DEL).toString().equalsIgnoreCase("Yes")) {
			logInfo("","KioskId is null.");
			int sOut1=updateDataInDB(sExtTable,INSTANT_DEL_NO,"'false'","WI_NAME ='"+sWorkitemId+"'");
			logInfo("","Update query output sout-----"+sOut1);
			int sOutt1=updateDataInDB(sExtTable,INSTANT_DEL_YES,"'true'","WI_NAME ='"+sWorkitemId+"'");
		}
	}
	
	public void setManualEida(int sheet){
		try{

			log.info("Inside setManualEida---");
			if(sheet == 1){
			if((formObject.getValue(CHECKBOX_EIDANO_MANUAL).toString().equalsIgnoreCase("true")) && 
				!formObject.getValue(MANUAL_EIDANO).toString().equalsIgnoreCase("")){
				formObject.setValue(PD_EIDANO,formObject.getValue(MANUAL_EIDANO).toString());
			}
			}
		} catch(Exception e){
			logError("setManualEida", e);
		}
	}

	private void compareAccRelationData(String wiName){
		logInfo("compareAccRelationData","compareAccRelationData Inside");
		try{
			List<List<String>> result ;
			List<List<String>> results = null ;
			int iRows = getGridCount(ACC_RELATION);
			String sQuery = "SELECT EIDA,IS_UAE_PASS_AUTH_DONE FROM ACC_RELATION_REPEATER "
					+ "WHERE WI_NAME = '"+wiName+"'";
			logInfo("compareAccRelationData","sQuery "+sQuery);
			String sQuery1 = "SELECT EIDA,IS_UAE_PASS_AUTH_DONE FROM ACC_RELATION_REPEATER "
					+ "WHERE WI_NAME = '"+sWorkitemId+"'";
			logInfo("compareAccRelationData","sQuery1 "+sQuery1);

			result = formObject.getDataFromDB(sQuery);
			results = formObject.getDataFromDB(sQuery1);
			logInfo("compareAccRelationData","result "+result);
			logInfo("compareAccRelationData","results "+results);
			for(int i=0 ; i< iRows ; i++){
				String pEida = result.get(i).get(0).toString();
				String pAuthDone = result.get(i).get(1).toString();
				logInfo("compareAccRelationData","pEida "+pEida+"pAuthDate"+pAuthDone);
				for(int j=0 ; j< iRows ; j++){
					String cEida = results.get(j).get(0).toString();
					String cAuthDone = results.get(j).get(1).toString();
					logInfo("compareAccRelationData","cEida "+cEida+"cAuthDate"+cAuthDone);
					if(pEida.equalsIgnoreCase(cEida)) {
						logInfo("compareAccRelationData","compareAccRelationData Inside Equal");
						if(!pAuthDone.equalsIgnoreCase(cAuthDone)) {
							logInfo("compareAccRelationData","compareAccRelationData Inside AuthDone Not Equal");
							updateAccRelationForLinkWi(wiName);
							populateUAEPassInfoStatus(sWorkitemId);
						}

					}
				}
			}

		}catch(Exception e){

		}
	}
	
	private boolean calculateIndRiskThreeStep() {
		int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
		/*String sQuery = "SELECT DEALS_ARMAMENT,HAWALA,FINAL_RESIDENCE_COUNTRY,PEP,PURPOSE_TAX,"
				+ "FINAL_NATIONALITY,(CASE WHEN IS_GEN_TRADE_COMP ='True' THEN 'Yes' "
				+ "WHEN IS_STONE_DEALER ='True' THEN 'Yes' WHEN IS_COMMODITY_BROKER ='True' THEN 'Yes' "
				+ "WHEN IS_REAL_ESTATE_BROKER ='True' THEN 'Yes' WHEN IS_SELF_EMPLOYED ='True' "
				+ "THEN 'Yes' ELSE 'No' END) AS EMP_NATURE_OF_BUSINESS,EMP_STATUS,IS_WORK_DEFENCE_UAE"
				+ ",IS_WORK_DEFENCE_NONUAE,DEALS_IN_WMD,SALARY_TRANSFER FROM USR_0_CUST_TXN WHERE WI_NAME ='"+sWorkitemId+"' "
						+ "AND CUST_SNO ='"+iSelectedRow+"'";
		logInfo("getIndRiskInputXML","sQuery::"+sQuery);
		List<List<String>> output = formObject.getDataFromDB(sQuery);*/
		formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
		logInfo("onTabClickCPDThreeStep","INSIDE Sanction Screening");//2	
		reKeyInsert();
		updateReKeyTemp("CPD");
		long start_Time = System.currentTimeMillis(); 
//		String sInputXML = getIndRiskInputXML(iSelectedRow);
		// AO DCRA COMMENTED //15082023
		String sInputXML = executeApplicationAssessmentRiskRetail(iSelectedRow);
		String sOutput = "";
		sOutput = socket.connectToSocket(sInputXML);
		logInfo("onTabClickCPDThreeStep", "sOutput: before "+sOutput);
		//Added by Jamshed
		
		  XMLParser xp = new XMLParser(sOutput); String finalRisk_cd =
		  xp.getValueOf("finalRisk"); logInfo("executeApplicationAssessmentRiskRetail",
		  "finalRisk_cd :  "+ finalRisk_cd);
		  
		  String finalRisk_cd_query
		  ="select risk_value from usr_0_risk_values where risk_code='"+finalRisk_cd+
		  "'"; logInfo("executeApplicationAssessmentRiskRetail","finalRisk_cd_query= "+
		  finalRisk_cd_query); List<List<String>> output_list_db
		  =formObject.getDataFromDB(finalRisk_cd_query); String
		  finalRisk_value=output_list_db.get(0).get(0);
		  logInfo("executeApplicationAssessmentRiskRetail","finalRisk_value= "+
		  finalRisk_value);
		  sOutput=finalRisk_value;
		//end
		logInfo("onTabClickCPDThreeStep", "sOutput: "+sOutput);
		long end_Time=System.currentTimeMillis();
		if(sOutput.equalsIgnoreCase("")) {
			sendMessageValuesList("","Some error occured in calculating Individual risk");
			return false;
		} else {
			if((!sOutput.equalsIgnoreCase("Unacceptable Risk") && !sOutput.equalsIgnoreCase("PEP")&&
					!sOutput.equalsIgnoreCase("UAE-PEP") && !sOutput.equalsIgnoreCase("Non UAE-PEP"))
					&& !sOutput.equalsIgnoreCase("Increased Risk") && !sOutput.equalsIgnoreCase("HIO PEP")// Added by Ameena)
					&& formObject.getValue(PD_CUSTSEGMENT).toString().equalsIgnoreCase("Private Clients")){
//				sOutput="Increased Risk"; // commented by Ameena
				sOutput="Medium Risk";
//				logInfo("onTabClickCPDThreeStep","formObject.getValue(REQUEST_TYPE).toString()	"+formObject.getValue(REQUEST_TYPE).toString());
//				logInfo("onTabClickCPDThreeStep","formObject.getValue(PD_CUSTSEGMENT).toString()	"+formObject.getValue(PD_CUSTSEGMENT).toString());
			}
//			}else if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only")
//					&& formObject.getValue(PD_CUSTSEGMENT).toString().equalsIgnoreCase("Private Clients")) {
//				sOutput="Neutral Risk";
//				logInfo("onTabClickCPDThreeStep ","Cust Segment" + formObject.getValue(PD_CUSTSEGMENT).toString());
//				logInfo("onTabClickCPDThreeStep","Request Type"+formObject.getValue(REQUEST_TYPE).toString());
//				}
			
			String sWsName = formObject.getValue(CURR_WS_NAME).toString();
			String sriskColumn ="SNO,WI_NAME,WS_NAME,CUST_CUR_RISK_BANK";
			String sriskValue = "'"+iSelectedRow+"','"+sWorkitemId+"','"+sWsName+"','"+sOutput+"'";	
			insert_Into_Usr_0_Risk_Data(sriskColumn,sriskValue);
			String sColumn = "CPD_CUST_INDI_RISK";
			String sWhere =  "WI_NAME='"+ sWorkitemId +"' and cust_sno='"+iSelectedRow+"'";
			String sValue = "'"+sOutput+"'";
			int sUpdateDecision = updateDataInDB(sCustTxnTable,sColumn,sValue,sWhere); 
			logInfo("onTabClickCPDThreeStep","sUpdateDecision"+sUpdateDecision);
			}
		return true;
	}
	
	 //shahbaz
	public void checkUAEPassAlert() {
		logInfo("checkUAEPassAlert ","INSIDE shahbaz");
		boolean UAEPassSkip = Boolean.valueOf(formObject.getValue("CHK_SkipUAEPass").toString());  
		logInfo("checkUAEPassAlert ","UAEPassSkip : " + UAEPassSkip);
		String reasonSkipUAEPass = formObject.getValue("SKIPUAEPASS_REASON").toString();
		logInfo("checkUAEPassAlert ","reasonSkipUAEPass : " + reasonSkipUAEPass);
	//	if (UAEPassSkip == true && !reasonSkipUAEPass.equalsIgnoreCase("")) {
			logInfo("checkUAEPassAlert ","Inside if ");
			dormantCustAlert();  
	//	}	
	}
	
	//Added by Shivanshu for MVN 	
	public void validateCRS() {
		//logInfo("CRS_ADD","CRS add clicked");
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
							formObject.setValue(CRS_TAXPAYERIDENTIFICATIONNUMBER,"");
							formObject.setValue(REASON_DESC, "");
							formObject.setValue(CRS_REASONNOTPROVIDINGTIN, "");
							formObject.setValue(CRS_TAX_COUNTRY, "");
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
	}
	
	
	//Added by Shivanshu for mvn
	public void validateEIDAinfo() {

		String valEIDA[] = xmlDataParser.getValueOf("Val").split("#");
		int sOutput = formObject.saveDataInDB("delete from USR_0_EIDACARD_DETAILS where WI_NAME='"+sWorkitemId+"'");
		String sColumn = "NATIONALITY,CUST_NAME,EIDANO,DOB,PASSPORTNO,PASSPORTISSDATE,PASSPORTEXPDATE,PASSPORTEXPPLACE,MOTHERNAME,ADDRESS,CITY,STATE,MOBILE,PHONE_NO,EMAIL,PROFESSION,PREFIX,EMP_NAME,GENDER,OCCUPATION,PASSPORT_TYPE,EIDA_EXP_DATE,VISA_NO,VISA_EXP_DATE,VISA_ISSUE_DATE,WI_NAME";
		String sValue="";
		String[] sTemp;
		String sTempDate = "";
		for(int i=0; i<valEIDA.length; i++) {
			sTemp = valEIDA[i].split("=");
			if(i==3 || i==5 || i==6 || i==21 || i==23) {
				logInfo("BTN_EIDA_INFO","sTemp[0]----"+sTemp[0]);
				if(!sTemp[0].equalsIgnoreCase("")) {
					sTempDate = setDateValue1(sTemp[0]);
					sValue = sValue+"'"+sTempDate+"'"+",";
				} else {
					sValue = sValue+"'"+sTemp[0]+"'"+",";
				}
			} else if(sTemp[0].indexOf("+")!=-1) {
				sValue = sValue+"'"+sTemp[0].substring(1,sTemp[0].length())+"'"+",";
			} else {
				sValue = sValue+"'"+sTemp[0]+"'"+",";
			}
		}
		String sIssuedDate ="";
		if(valEIDA[23].indexOf("=")!=0) {
			sIssuedDate = valEIDA[23].substring(0,valEIDA[23].indexOf("="));
			sTemp = sIssuedDate.split("/");	
			sIssuedDate = sTemp[0]+"/"+sTemp[1]+"/"+(Integer.parseInt(sTemp[2])-3);	
		}
		sValue=sValue+"'"+sIssuedDate+"'"+","+"'"+sWorkitemId+"'";
		logInfo("BTN_EIDA_INFO","sValue---"+sValue);
		String tmpValues=sValue.replaceAll("'","");
		tmpValues=tmpValues.replaceAll(",","");
		if(!tmpValues.equalsIgnoreCase(sWorkitemId)) {
			sOutput=insertDataIntoDB("USR_0_EIDACARD_DETAILS",sColumn,sValue);				
			logInfo("BTN_EIDA_INFO","sOutput---"+sOutput);
		}
		//logInfo("BTN_EIDA_INFO","tmpValues---"+tmpValues);
		List<List<String>> sOutput1=formObject.getDataFromDB("SELECT EIDANO,CUST_NAME,"
				+ "to_char(DOB,'DATEFORMAT') "
				+ "DOB,PASSPORTNO,to_char(EIDA_EXP_DATE,'DATEFORMAT') EIDA_EXP_DATE FROM"
				+ " USR_0_EIDACARD_DETAILS where wi_name='"+ sWorkitemId +"'");
		if(sOutput1!=null && sOutput1.size()>0){
			String sEidaNo=sOutput1.get(0).get(0);
			String sName=sOutput1.get(0).get(1);
			String sDOB=sOutput1.get(0).get(2);
			String sPassportNo=sOutput1.get(0).get(3);
			String sExpairyDate=sOutput1.get(0).get(4);
			if(!(sEidaNo.equalsIgnoreCase("") && sName.equalsIgnoreCase("")&& sDOB.equalsIgnoreCase("")
					&& sPassportNo.equalsIgnoreCase("") && sExpairyDate.equalsIgnoreCase(""))) {
				JSONArray jsonArray=new JSONArray(); 
				JSONObject obj=new JSONObject();
				obj.put("EIDANO",sEidaNo);
				obj.put("CUSTOMERNAME",sName);
				obj.put("DOB",sDOB);
				obj.put("PASSPORTNO",sPassportNo);
				obj.put("PASSPORTNO",sExpairyDate);
				jsonArray.add(obj); 
				logInfo("populateUDFGrid","jsonArray2:: "+jsonArray.toString());
				formObject.addDataToGrid(EIDA_DETAILS,jsonArray);
			}
		} else {
			sendMessageValuesList("","No Data Returned");
			formObject.clearTable(EIDA_DETAILS);
		}
	
	}

}