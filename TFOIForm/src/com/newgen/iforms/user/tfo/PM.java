package com.newgen.iforms.user.tfo;

import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.newgen.iforms.EControl;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.iforms.user.config.Constants;
import com.newgen.iforms.user.tfo.util.XMLParser;
import com.newgen.mvcbeans.model.WorkdeskModel;

public class PM extends Common implements Constants, IFormServerEventHandler{
	public  static String lcLimitLine="";  
	public String createAmendOperType="";
	private boolean bCallELCFCUBSContractService = false;
	private String decHist = "TFO_DJ_DEC_HIST";
	private String decHistCol = "WI_NAME,USER_ID,PRV_WS_NAME,CURR_WS_NAME,CREATE_DATE,USERNAME,USER_GROUPNAME,ACTION,REASON_FOR_ACTION,TRANSACTION_DATE_TIME,REMARKS,BRANCH,DUP_CHECK,ROUTE_TO,SEND_EMAIL_FLAG";
	String callRequestType = "";
	boolean bSubmit = true;
	public PM(IFormReference formObject) {
		super(formObject);
	}

	@Override
	public void beforeFormLoad(FormDef arg0, IFormReference arg1) {

	}

	public String executeCustomService(FormDef arg0, IFormReference arg1,
			String arg2, String arg3, String arg4) {
		return null;
	}

	@Override
	public JSONArray executeEvent(FormDef arg0, IFormReference arg1, String arg2, String arg3) {
		return null;
	}

	@Override
	public String executeServerEvent(IFormReference formObject,
			String controlName, String eventType, String data) {
		log.info("Inside executeServerEvent PM");
		log.info("Event: " + eventType + ", Control Name: " + controlName
				+ ", Data: " + data);
		sendMessageList.clear();
		String retMsg = getReturnMessage(true);
		Boolean success = true;
		try {
			boolean sOnClick = false;
			if (eventType.equalsIgnoreCase(EVENT_TYPE_LOAD)) {
				log.info("First ELSE IF Inside executeServerEvent PM==1  "+"eventType===="+eventType);
				if (CONTROL_NAME_FORM.equalsIgnoreCase(controlName)) {
					String winame = formObject.getValue("WI_NAME").toString();
					log.info("Inside form load event -> WorkItem Name: "+ winame);
					
					Boolean view = setUserDetail(); 
					formLoadDataPM();
					boolean openJSPOnFormLoad = checkCallStatus();
					if(openJSPOnFormLoad){
						return getReturnMessage(true, controlName, "openRepairJSP");
					}
					retMsg = getReturnMessage(view, controlName);
					String req_cat =  formObject.getValue(REQUEST_CATEGORY).toString();
					createAmendCoreTSLM(); //BY KISHAN TSLM
					fillLoanRefrenceListView(); //BY KISHAN TSLM
//					showUTCDetails();  //added by reyaz 5082022
				} else if (PM_DECISION_HISTORY.equalsIgnoreCase(controlName)) {
					log.info("loading decision history ...");
					loadDecisionHistoryListView();
				} else if (BUTTON_SUBMIT.equalsIgnoreCase(controlName)) {
					loadJSPData();
				}

			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)) {
				log.info("First ELSE IF Inside executeServerEvent PM==2 "+"eventType===="+eventType);
				if (BUTTON_SUBMIT.equalsIgnoreCase(controlName)
						&& data.contains("###") && bSubmit) {
					setEmailCompliance(); //Added by Shivanshu for Email
					if ("TXNAU".equalsIgnoreCase(formObject.getValue(DEC_CODE).toString())) {
						bSubmit = false;
						saveDecHistory();
						setTargetWorkstep();
						sendTSLMPushMessage("C");
                        checkTrsdFlag();
						if("Y".equals(formObject.getValue(PT_UTILITY_FLAG).toString())) {
							insertIntoNotificationTxn();
						}
					} 
					else if("SBLC".equalsIgnoreCase(requestCategory) && "SBLC_CR".equalsIgnoreCase(requestType)
							&& "STBPD".equalsIgnoreCase(formObject.getValue(DEC_CODE).toString())){
						log.info("Calling createNewWorkitemSBLC_CR Method");
						createNewWorkitemSBLC_CR();
                        checkTrsdFlag();
					}
					else {
						bSubmit = false;
						Boolean callExecution = true;
						callRequestType = "";
						if (submitPMValidations(data)) {
							log.info("submitPMValidations returned true");
							if (TXNC.equalsIgnoreCase(formObject.getValue(DEC_CODE).toString()) 
									&& !"Y".equalsIgnoreCase(formObject.getValue(CREATE_AMEND_CNTRCT_FCUBS).toString())) {
								if(!formObject.getValue(PROCESSING_SYSTEM).toString().equalsIgnoreCase("T")){
									if (validateMandatoryFieldsPM(FCUBS_CON_NO,"Please enter FCUBS contract number")) {
										String sFlexCubeRefNo = "";
										sFlexCubeRefNo = formObject.getValue(FCUBS_CON_NO).toString();
										if (sFlexCubeRefNo.length() != 16) {
											sendMessageHashMap(FCUBS_CON_NO,"Invalid Transaction reference number");
											callExecution = false;
											return retMsg;
											
										} else {
											formObject.setValue(TRANSACTION_REFERENCE,formObject.getValue(FCUBS_CON_NO).toString());
										}
									}
								}else {
									callExecution = false;
									if (!sendMessageList.isEmpty()) {
										return getReturnMessage(false, controlName,sendMessageList.get(0).toString() + "###"
												+ sendMessageList.get(1).toString());
									}
								}
							}
							if ("REF".equalsIgnoreCase(formObject.getValue(DEC_CODE).toString())) {
								submitReferalHistory();
								callExecution = callContractWebService();
							} else if (TXNC.equalsIgnoreCase(formObject.getValue(DEC_CODE).toString())) {
								callExecution = callContractWebService();
							}
							
							checkTrsdFlag();
							log.info("callExecution: "+callExecution);
							if (callExecution) {
								log.info("WF Done Called stopped for a while");
								log.info("callRequestType: "+callRequestType);
								if(callRequestType.equalsIgnoreCase("")){
									saveDecHistory();
									if("Y".equals(formObject.getValue(PT_UTILITY_FLAG).toString())) {
										insertIntoNotificationTxn();
									}
								}
								return getReturnMessage(true, controlName,callRequestType);
							} else {
								log.info("Jsp wll not open call execution failed");
								String showNotificationAlert="";
								disableControls(BUTTON_SUBMIT);
								saveDecHistory();
								if("Y".equals(formObject.getValue(PT_UTILITY_FLAG).toString())) {
									insertIntoNotificationTxn();
								}
								if(TXNC.equalsIgnoreCase(formObject.getValue(DEC_CODE).toString())){
									setTargetWorkstep(); 
									String processType=formObject.getValue(PROCESS_TYPE).toString();
									if(!processType.equalsIgnoreCase("TSLM Front End")){
										sendTSLMPushMessage("C");
									}
									//ATP-495 29-07-2024 REYAZ START
									else if(processType.equalsIgnoreCase("TSLM Front End") && "Yes".equalsIgnoreCase(formObject.getValue(HYBRID_CUSTOMER).toString())){
										sendTSLMPushMessage("C");
									}
									//ATP-495 29-07-2024 REYAZ END
									else if(processType.equalsIgnoreCase("TSLM Front End")){
										//ATP-332 DATE -19-12-2023 REYAZ
										String res =createTSLMPushMsg("R");
										XMLParser xmlparser = new XMLParser(res);
										String statusCode = xmlparser.getValueOf("StatusCode");
										if("0".equalsIgnoreCase(statusCode)){
											formObject.setValue(PM_TSLM_PUSH_MSG_FLAG, "Y");
										} else {
											formObject.setValue(PM_TSLM_PUSH_MSG_FLAG, "N");
										}
										
									}
									
									/*if(sWorkitemID.equalsIgnoreCase("TF-00000143666-REQUEST")){
										log.info("IN PUSH TSLM CONDITION");
										PushMessageTSLM obj = new PushMessageTSLM();
										obj.pushTSLM();
									}*/
									showNotificationAlert= createNewWorkitemELCB();
								}
								
								return getReturnMessage(true, controlName,showNotificationAlert);
							}
						} else {
							if (!sendMessageList.isEmpty()) {
								return getReturnMessage(false, controlName,sendMessageList.get(0).toString() + "###"
										+ sendMessageList.get(1).toString());
							}
						}
					}
					
					
				} else if (BUTTON_SF_CLOSE.equalsIgnoreCase(controlName)) {
					log.info("on submit button click and bCallELCFCUBSContractService true..");
					Boolean callExecution = true;
					callExecution = callContractWebService2();
					setTargetWorkstep();
					//sendTSLMPushMessage();
					if (callExecution) {
						log.info("callContractWebService2 true");
						saveDecHistory();
						if("Y".equals(formObject.getValue(PT_UTILITY_FLAG).toString())) {
							insertIntoNotificationTxn();
						}
						String showNotificationAlert=createNewWorkitemELCB();
						return getReturnMessage(true,showNotificationAlert);
					} else {
						log.info("call in callContractWebService2 execution failed");
						enableControls(BUTTON_SUBMIT);
						bSubmit = true;
						return getReturnMessage(false);
					}
				} else if (FETCH_DETAILS.equalsIgnoreCase(controlName)) {
					fetchAccountData(formObject.getValue(CUSTOMER_ID).toString());
				} else if(ACCOUNT_DETAILS.equalsIgnoreCase(controlName)){
					log.info("before calling fetchAccountData..");
					fetchAccountData(formObject.getValue(CUSTOMER_ID).toString());
				} else if (BUTTON_ADD_PARTY_DETAILS.equalsIgnoreCase(controlName)) {
					if (validateMandatoryPDTab() && validateIsPartyExist()) {
						LoadListViewFromFields(FORM_CONTROLS_PARTY,	LISTVIEW_CONTROLS_PARTY, LISTVIEW_PARTY);
						clearControls(FORM_CONTROLS_PARTY);
					}
				} else if (BUTTON_ADD_CONTRACT_DETAILS
						.equalsIgnoreCase(controlName)) {
					if (validateMandatoryCLTab()) {
						LoadListViewFromFields(FORM_CONTROLS_CONTROL_LIMITS,LISTVIEW_CONTROL_LIMITS,LISTVIEW_CONTRACT_LIMITS);
						clearControls(FORM_CONTROLS_CONTROL_LIMITS);
					}
				} else if (BUTTON_SAVE.equalsIgnoreCase(controlName)) {
					setHashMap();
					return retMsg;

				} else if (BUTTON_ADD_LLI.equalsIgnoreCase(controlName)) {
					
					String sVesselName = formObject.getValue("TXT_VESSELNAME").toString();
					
					log.info("Vessel Name " + sVesselName);
					if (!(sVesselName.isEmpty() || sVesselName.trim()
							.equalsIgnoreCase(""))) {
						if (!duplicateVesselCheck(sVesselName,
								LISTVIEW_LLI)) {
							formObject.setValue("TXT_SNO",String.valueOf(getLLISeriallNumber(LISTVIEW_LLI)));
							formObject.setValue("TXT_WINAME", this.sWorkitemID);
							LoadListViewFromFields(FORM_CONTROLS_LLI,LISTVIEW_LLI_CONTROLS, LISTVIEW_LLI);
							clearControls(FORM_CONTROLS_LLI);
							disableControls("Btn_Basic_Vessel_Details,Btn_Ownrship_Details,Btn_Movmnt_Details");
							DeleteOldDataOnFetch();
							clearControls(TXT_LLI_VESSELNAME, LLI_SHIPMENT_DATE);
							
							return retMsg; 
						} else {
							sendMessageHashMap(LISTVIEW_LLI,"Vessel name already exist");
						}
					} else {
						sendMessageHashMap(LISTVIEW_LLI,"Vessel name can't left blank");
					}
				}else if (BUTTON_FETCH_LLI.equalsIgnoreCase(controlName)) {
					String sVesselName = formObject.getValue(TXT_LLI_VESSELNAME).toString();
					if ("".equalsIgnoreCase(data) || null == data) {
						deleteTaskList();
						if (validateMandatoryFieldsPM(TXT_LLI_VESSELNAME,"Please enter Vessel Name.")
								&& validateMandatoryFieldsPM(LLI_SHIPMENT_DATE,"Please enter Shipped on Date.")
								&& validateShipmentdate(LLI_SHIPMENT_DATE)) {
							if (duplicateVesselCheck( formObject.getValue(TXT_LLI_VESSELNAME).toString(), LISTVIEW_LLI)) {
								sendMessageHashMap(LISTVIEW_LLI,"Vessel name already exist");
								clearControls(TXT_LLI_VESSELNAME,LLI_SHIPMENT_DATE);
							} else {
								String sRespnse = "";
								fetchLLIFlag = true;
								int NoOfMovementDetailCalls = getNoOfMovementDetails(
										formObject.getValue(LLI_SHIPMENT_DATE).toString(), sVesselName);
								toInsertIntegrationCalls(1, 1, 1,NoOfMovementDetailCalls, sVesselName);
								log.info("lli message sent first time");
								return getReturnMessage(false);
							}
						}
					} else {
						String sRespnse = data;
						log.info("[sRespnse]" + sRespnse);
						if (sRespnse.equalsIgnoreCase("Failed")) {
							deleteUnusedRecord(sVesselName);
						} else {
							log.info("[[Fetch successful]]");
							//DeleteOldDataOnFetch();
							showDataOnFetch(formObject);
							enableControls("Btn_Add_LLI,Btn_Del_LLI");
						}
					}
				} else if (BUTTON_PARTY_DETAIL_FETCH.equalsIgnoreCase(controlName)) {
					log.info("inside btnPDFetch");
					if (fetchValidatePartiesField()) {
						disableControls(BUTTON_PARTY_DETAIL_FETCH);
						log.info("inside btnPDFetch CID not null: "
								+ formObject.getValue(PD_CUSTOMER_ID).toString());
						if (validateMandatoryFieldsPM(PD_CUSTOMER_ID,"Please Enter CID")
								&& checkLengthValidation(PD_CUSTOMER_ID,
										"Please enter correct CID", 6)) {
							clearPartyDetailsField(BUTTON_PARTY_DETAIL_FETCH);
							fetchCustomerPDData(formObject.getValue(PD_CUSTOMER_ID).toString(),formObject.getValue(REQUEST_CATEGORY).toString());
						}
						enableControls(BUTTON_PARTY_DETAIL_FETCH);
						return retMsg;
					}
				} else if (TAB_CLICK.equalsIgnoreCase(controlName)) {
					log.info("tab click event starts");
					implementTabNavigationPM(data);
					log.info("tab click event ends");
				} else if(MANUAL_TAB_CLICK.equalsIgnoreCase(controlName)) {
					log.info("tab manually event starts" +data);
					String enableSection = executeTabClickCommand(data);//enableSection
					if(enableSection != null && enableSection.equalsIgnoreCase("enableSection")){//06/12/21 By Kishan
						return getReturnMessage(true,controlName,enableSection);
					}
					log.info("tab manually event ends");
					
				} else if(TR_FETCH_RATE_BUTTON.equalsIgnoreCase(controlName)){
					setFxRate();
				} else if("BTN_GEN_PDF".equalsIgnoreCase(controlName)){
					return getReturnMessage(true, controlName, personName);
				}else if(BTN_START_TRSD.equalsIgnoreCase(controlName)){
					if(checkTRSDOptionalMandatoryValidation()){ //Added by reyaz 17-05-2023
					return callTRSDService(controlName);
					}
				}else if(BTN_IFRAME_CLOSE.equalsIgnoreCase(controlName)){
					setDataInTRSDTab();
				}else if (TRSD_SCREENING_DATA_BTN.equalsIgnoreCase(controlName)){
					return getReturnMessage(true,sUserIndex);
				}
				else if("UTC_ON_LOAD_INVOICE".equalsIgnoreCase(controlName)){
					String utc_required_brms = "";
					String procesSystem = "";
					String standAlone = "";
					log.info("***************Inside UTC_ON_LOAD_INVOICE on PM******************");
					String sQry = "SELECT UTC_REQUIRED_BRMS,PROCESSING_SYSTEM,STANDALONE_LOAN FROM EXT_TFO WHERE WI_NAME = '" + sWorkitemID + "'";
					List<List<String>> sOutputlist = formObject.getDataFromDB(sQry);
					log.info("UTC_ON_LOAD_INVOICE query : "+sOutputlist);
					if (sOutputlist != null && !sOutputlist.isEmpty()
							&& sOutputlist.get(0).size() > 0) {
						utc_required_brms = sOutputlist.get(0).get(0);
						procesSystem = sOutputlist.get(0).get(1);
						standAlone = sOutputlist.get(0).get(2);
					} 
//					if(("Yes".equalsIgnoreCase(utc_required_brms)) || (("T".equalsIgnoreCase(procesSystem)) 
//							&& ("2".equalsIgnoreCase(standAlone)))){
//						log.info("Start validateInvoiceDetailsTab");
					validateInvoiceDetailsTab();
					log.info("End validateInvoiceDetailsTab");
//					}
				}else if ("UTC_START_CHECK".equalsIgnoreCase(controlName)){
					log.info("UTC_START_CHECK starts");
					submitInvoiceDetail();
					log.info("UTC_START_CHECK ends");
				} else if ("UTC_BTN_REFRESH".equalsIgnoreCase(controlName)){
					log.info("UTC_BTN_REFRESH starts");
					popuateInvoiceData();
					log.info("UTC_BTN_REFRESH ends");
				} else if ("IS_GETDOCUMENT_UTC_DONE".equalsIgnoreCase(controlName)){
					log.info("IS_GETDOCUMENT_UTC_DONE starts");
					String flagVal = checkUTCFlag();
					log.info("IS_GETDOCUMENT_UTC_DONE flagVal"+flagVal);
					if(flagVal.equalsIgnoreCase("N")){
						formObject.clearCombo(formObject.getValue(DEC_CODE).toString());
					loadDecision("TXNC,RET,REJ,,STBPD,TXNAU", "DEC_CODE");
					}
					else if(flagVal.equalsIgnoreCase("Y")){
						formObject.clearCombo(formObject.getValue(DEC_CODE).toString());
					loadDecision("REF,REJ,TXNAU,STBPD", DEC_CODE);
					}
				} else if ("Load_Decision".equalsIgnoreCase(controlName)){
					log.info("Load_Decision starts");
					formObject.clearCombo(formObject.getValue(DEC_CODE).toString());
					loadDecision("REF,REJ,TXNAU,STBPD", DEC_CODE);
					
				}else if ("startUTCDone".equalsIgnoreCase(controlName)){
					log.info("startUTCDone INSIDE");
	
					if(!checkStartUTC()){
						disableControls("Invoice_No,table183_INVOICE_DATE,Invoice_Amount,Invoice_Currency,Supplier_Name,Buyer_Name,table64_INVOICE_DATE");
						sendMessageHashMap("", "Invoice Data cannot be Modified As Start UTC is Already Done");
					}
				} else if ("CheckDecision".equalsIgnoreCase(controlName)){
					log.info("CheckDecision INSIDE");
					String flagVal = checkUTCFlag();
					if(flagVal.equalsIgnoreCase("Y")){
						formObject.clearCombo(formObject.getValue(DEC_CODE).toString());
						loadDecision("REF,REJ,TXNAU,STBPD", DEC_CODE);
						String alert = "UTC:Please Select Decision";
						return getReturnMessage(true, controlName,alert);
					}
				} else if ("BTN_SCREENING_BROWSE".equalsIgnoreCase(controlName)){
					log.info("BTN_SCREENING_BROWSE starts");
					//ATP-367 17-01-2024 REYAZ
					//START CODE 
					loadTrsdDataAfterExcelUpload();
					setDataInTRSDTab();
					//END CODE
					log.info("BTN_SCREENING_BROWSE ends");
				}else if ("BTN_UTC_SCORE".equalsIgnoreCase(controlName)) {
					log.info("Inside Fetch Utc Score ");
					 executeUpdateDocUTC();	
					log.info("End Fetch Utc Score  ");
				}
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_CHANGE)) {
				log.info("First ELSE IF Inside executeServerEvent PM==3 "+"eventType===="+eventType);
				if (ACCOUNT_NUMBER.equalsIgnoreCase(controlName)) {
					setTitleCurrency(controlName,"GRNT_CHRG_ACC_TITLE","GRNT_CHRG_ACC_CRNCY");
					clearCustSignAccBal(formObject.getValue(ACCOUNT_NUMBER).toString());
				} else if (ACCOUNT_VALID.equalsIgnoreCase(controlName)) {
					btnAccEnableDisable(controlName);
				} else if (DEC_CODE.equalsIgnoreCase(controlName)) {
					finalDecisionHandling(controlName, REJ_RESN_PPM);
					changeCreateAmendField();
					changeFCUBSContractNumber(FCUBS_CON_NO);
				} else if (CREATE_AMEND_CNTRCT_FCUBS.equalsIgnoreCase(controlName)) {
					changeFCUBSContractNumber(FCUBS_CON_NO);
					fillLoanRefrenceListView(); //BY KISHAN TSLM
				} else if (OPERATION_CODE.equalsIgnoreCase(controlName)) {
					setLimitPartyType(LIMIT_PARTY_TYPE);

				} else if (DUP_CHK_CONFIRMATION.equalsIgnoreCase(controlName)) {
					duplicateCheckConfirmation(controlName, true);
				} else if (LLI_CHK_OK.equalsIgnoreCase(controlName)) {
					if (formObject.getValue(LLI_CHK_OK).toString().equalsIgnoreCase("Y")) {
						if (formObject.getValue(COMP_POSITIVE_MATCH).toString().equalsIgnoreCase("Y")) {
							formObject.applyGroup(CONTROL_SET_COMPLIANCE_CHECK_DISABLE_FIELDS);
							formObject.resetGroup(CONTROL_SET_COMPLIANCE_CHECK_DISABLE_FIELDS);
							setLoadRefTRVal(EVENT_TYPE_CLICK, controlName);
						}
						setRefToComp("");
					} else if (formObject.getValue(LLI_CHK_OK).toString().equalsIgnoreCase("X")) {
						if (formObject.getValue(COMP_POSITIVE_MATCH).toString().equalsIgnoreCase("Y")) {
							formObject.applyGroup(CONTROL_SET_COMPLIANCE_CHECK_DISABLE_FIELDS);
							formObject.resetGroup(CONTROL_SET_COMPLIANCE_CHECK_DISABLE_FIELDS);
							setLoadRefTRVal(EVENT_TYPE_CLICK, controlName);
						}
						setRefToComp("X");
					} else if (formObject.getValue(LLI_CHK_OK).toString().equalsIgnoreCase("O")) {
						if (formObject.getValue(COMP_POSITIVE_MATCH).toString().equalsIgnoreCase("Y")) {
							formObject.applyGroup(CONTROL_SET_COMPLIANCE_CHECK_DISABLE_FIELDS);
							formObject.resetGroup(CONTROL_SET_COMPLIANCE_CHECK_DISABLE_FIELDS);
							setLoadRefTRVal(EVENT_TYPE_CLICK, controlName);
						}
						setRefToComp("O");
					} 
				} else if (COMP_POSITIVE_MATCH.equalsIgnoreCase(controlName)) {
					if (formObject.getValue(COMP_POSITIVE_MATCH).toString().equalsIgnoreCase("Y")) {
						setRefToComp("Y");
						formObject.applyGroup(CONTROL_SET_COMPLIANCE_CHECK_ENABLE_FIELDS);
						setLoadRefTRVal(EVENT_TYPE_CLICK, controlName);
					} else if (formObject.getValue(COMP_POSITIVE_MATCH).toString().equalsIgnoreCase("N")) {
						setRefToComp("N");
						formObject.applyGroup(CONTROL_SET_COMPLIANCE_CHECK_DISABLE_FIELDS);
						formObject.resetGroup(CONTROL_SET_COMPLIANCE_CHECK_DISABLE_FIELDS);
						setLoadRefTRVal(EVENT_TYPE_CLICK, controlName);
					} else if (formObject.getValue(COMP_POSITIVE_MATCH).toString().equalsIgnoreCase("A")) {
						setRefToComp("A");
						formObject.applyGroup(CONTROL_SET_COMPLIANCE_CHECK_DISABLE_FIELDS);
						formObject.resetGroup(CONTROL_SET_COMPLIANCE_CHECK_DISABLE_FIELDS);
						setLoadRefTRVal(EVENT_TYPE_CLICK, controlName);
					}
				} else if (COMP_REF.equalsIgnoreCase(controlName)) {
					if (formObject.getValue(COMP_REF).toString().equalsIgnoreCase("Y")) {
						formObject.applyGroup(CONTROL_SET_COMPLIANCE_CHECK_ENABLE_FIELDS);
					} else if (formObject.getValue(COMP_REF).toString().equalsIgnoreCase("N")) {
						formObject.applyGroup(CONTROL_SET_COMPLIANCE_CHECK_DISABLE_FIELDS);
						formObject.resetGroup(CONTROL_SET_COMPLIANCE_CHECK_DISABLE_FIELDS);
					}
				} else if (TR_REFER_TREASURY.equalsIgnoreCase(controlName)) {
					setLoadRefTRVal(EVENT_TYPE_CLICK, controlName);
				} else if(controlName.equalsIgnoreCase(RELATIONSHIP_TYPE)) {
					setBranchCode(COMBOX_BRN_CODE,false);
				} else if (TSLM_INVOICE_CHK_CONFIRM.equalsIgnoreCase(controlName)) {	//code by Moksh
					duplicateCheckConfirmation(controlName, true);
				} 
			} else if(eventType.equalsIgnoreCase(EVENT_TYPE_LOSTFOCUS))
				log.info("First ELSE IF Inside executeServerEvent PM==4 "+"eventType===="+eventType);
			{ 
				if (FINAL_DECISION_GRID_ADDITIONAL_MAIL.equalsIgnoreCase(controlName)) {
					log.info("Check Lost Focus");
					validateMultipleMailId(controlName);
					int finalDecisionList=Integer.parseInt(data);
					setHashMap(finalDecisionList);
				}else if("LOAN_AMOUNT".equalsIgnoreCase(controlName)){
					String msg = chkAmount("LOAN_AMOUNT", "");
					if (!msg.equalsIgnoreCase(""))
						success = false;
					retMsg = getReturnMessage(success, controlName, msg);
					return retMsg;

				}else if(controlName.equalsIgnoreCase(Q_AMENDMENT_DATA_USER_CNTR_GTEE_EXP_DATE)){
					 if(checkExpDateValidationPM()){
						 formObject.setValue(Q_AMENDMENT_DATA_USER_CNTR_GTEE_EXP_DATE, "");
						 formObject.setValue(Q_AMENDMENT_DATA_FIN_CNTR_GTEE_EXP_DATE, "");	
					 }
				}else if(controlName.equalsIgnoreCase("LOAN_VAL_DATE")){  //Added by Shivanshu ATP-409
					checkLoanValidationPM("LOAN_VAL_DATE");
				}
			}
		} 
		catch (Exception e) {
			log.error("executeServerEvent Exception: ",e);
		} finally{
			log.info("sendMessageList="+sendMessageList);
			log.info("bSubmit="+bSubmit);
			if(!sendMessageList.isEmpty()){
				if(!bSubmit){
					bSubmit=true;
				}
				return  getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1).toString());
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

	private void finalDecisionHandlingPM(String controlName, String sDecReason){
		log.info("*********** inside finalDecisionHandlingPM *************");
		String sFinalDecision = formObject.getValue(controlName).toString();
		log.info("Final Decision "+sFinalDecision);
		try {
			if(("GA".equalsIgnoreCase(requestType) || "GAA".equalsIgnoreCase(requestType))){
				if("REJ".equalsIgnoreCase(sFinalDecision)){
					loadReasonCombo(sFinalDecision,sDecReason);
				} else{
					setSelectedDisable(sDecReason);	
					formObject.setStyle(sDecReason,"disable","true");
				}
			}
			else {
				setSelectedDisable(sDecReason);	
				formObject.setStyle(sDecReason,"disable","true");
			}
			formObject.setValue(FINAL_DESC_PPM, getListValFromCode(lstDec, sFinalDecision));
			String sRequestType = formObject.getValue(REQUEST_TYPE).toString();
			log.info("sRequestType "+sRequestType);
			if("GRNT".equalsIgnoreCase(requestCategory)){
				if("NI".equalsIgnoreCase(sRequestType) || "GA".equalsIgnoreCase(sRequestType)) {
					formObject.setValue(FCUBS_CON_NO, "");
					formObject.setStyle(FCUBS_CON_NO,"disable",FALSE);
				} else {
					formObject.setValue(FCUBS_CON_NO, formObject.getValue(TRANSACTION_REFERENCE).toString());
					formObject.setStyle(FCUBS_CON_NO,"disable",TRUE);
				}
			}else if("SBLC".equalsIgnoreCase(requestCategory)){//RR
				if("SBLC_NI".equalsIgnoreCase(sRequestType)){
					formObject.setValue(FCUBS_CON_NO, "");
					formObject.setStyle(FCUBS_CON_NO,"disable",FALSE);
				}
				else {
					formObject.setValue(FCUBS_CON_NO, formObject.getValue(TRANSACTION_REFERENCE).toString());
					formObject.setStyle(FCUBS_CON_NO,"disable",TRUE);
				}
			}else if("ELC".equalsIgnoreCase(requestCategory)){//RR
				if("ELC_SLCA".equalsIgnoreCase(sRequestType)){
					formObject.setValue(FCUBS_CON_NO, "");
					formObject.setStyle(FCUBS_CON_NO,"disable",FALSE);
				}
				else if("ELC_SLCAA".equalsIgnoreCase(sRequestType)||"ELC_SER".equalsIgnoreCase(sRequestType)
						||"ELC_SCL".equalsIgnoreCase(sRequestType)) {
					formObject.setValue(FCUBS_CON_NO, formObject.getValue(TRANSACTION_REFERENCE).toString());
					formObject.setStyle(FCUBS_CON_NO,"disable",TRUE);
				}
			}
			formObject.setValue(REMARKS, "");
		} catch(Exception e){
			log.error("finalDecisionHandlingPM Exception: ", e);
		}
	}

	public void pmPcFieldFrmOnLoad(String requestType) {
		log.info("*****************Inside pmFieldFrmOnLoad***********************");		
		if("GRNT".equalsIgnoreCase(requestCategory)){
			if ("GA".equalsIgnoreCase(requestType)) {
				log.info("## GA ##");
				formObject.setValue(PRODUCT_TYPE, "T414");
				String isAccValid = formObject.getValue(ACCOUNT_VALID).toString();
				if(null == isAccValid || "0".equalsIgnoreCase(isAccValid)||"".equalsIgnoreCase(isAccValid)){
					formObject.setValue(ACCOUNT_VALID, "2");
				}
				disableControls(AMEND_TYPE); 
			} else if ("GAA".equalsIgnoreCase(requestType)) {
				log.info("## GAA ##");
				formObject.setValue(PRODUCT_TYPE, "T414");
				disableControls("EXP_DATE,TRN_TENOR");	
			} 
		} else if ("SG".equalsIgnoreCase(requestCategory)) { //Shipping GTEE 27
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
		log.info("*****************END pmFieldFrmOnLoad***********************");
	}

	public void setOperationCode(){
		log.info("in  setOperationCode");
		String query="";
		String productCode=(String) formObject.getValue(PRODUCT_TYPE);
		String operationCode= (String) formObject.getValue(OPERATION_CODE);
		if(("IFS".equalsIgnoreCase(requestCategory))||("TL".equalsIgnoreCase(requestCategory))
				||(("IFP".equalsIgnoreCase(requestCategory)) &&(("AM".equalsIgnoreCase(requestType))
						||("STL".equalsIgnoreCase(requestType))||("IFP_SD".equalsIgnoreCase(requestType)))) ){	
			log.info("operation code- select");
			formObject.setValue(OPERATION_CODE,""); 
			disableControls(OPERATION_CODE);
		}
		else 
		{
			query="select operation_code from tfo_dj_operation_def_mast where request_category_code='"+requestCategory+"' and request_type_code='"+requestType+"'";
			log.info("query REEGGG:"+query);
			List<List<String>> autoPopulateVal= formObject.getDataFromDB(query);
			if(!autoPopulateVal.isEmpty()){
				log.info("autoPopulateVal.size(): "+autoPopulateVal.size()+", autoPopulateVal.get(0).get(0): "+
						autoPopulateVal.get(0).get(0));
				log.info("queryyyyyyyy ABCCC");
				log.info("queryyyyyyyy ABCCC"+autoPopulateVal);
				log.info("queryyyyyyyy ABCCC"+autoPopulateVal.toString());
				log.info("queryyyyyyyy ABCCC"+autoPopulateVal.toArray());
				if(autoPopulateVal.get(0).get(0).equalsIgnoreCase("NA")){
					if("ELC_LCA".equalsIgnoreCase(requestType))
					{
						query="select response from tfo_dj_doc_rvw_records where doc_rvw_gdlines='Whether we are adding Confirmation along with advising ?' and wi_name='"+this.sWorkitemID+"'";
						List<List<String>> value= formObject.getDataFromDB(query);
						log.info("query: "+query);
						if(!value.isEmpty()){
							if(value.get(0).get(0).equalsIgnoreCase("Yes") && operationCode.equalsIgnoreCase("")){
								formObject.setValue(OPERATION_CODE,"ANC");  
							} else if(value.get(0).get(0).equalsIgnoreCase("No") && operationCode.equalsIgnoreCase("")){
								formObject.setValue(OPERATION_CODE,"ADV");  
							}
						}
					} else if("EC_BL".equalsIgnoreCase(requestType) || "EC_LDDI".equalsIgnoreCase(requestType)){
						if("T3S4".equalsIgnoreCase(productCode))
						{
							formObject.setValue(OPERATION_CODE,"COL"); 
							disableControls(OPERATION_CODE);    
						}else if("T3U4".equalsIgnoreCase(productCode))
						{
							formObject.setValue(OPERATION_CODE,"ACC");  
							disableControls(OPERATION_CODE);    
						}
					} else if("ELCB_BL".equalsIgnoreCase(requestType)){
						if(("T3S3".equalsIgnoreCase(productCode))||("T3S5".equalsIgnoreCase(productCode))||("I3S3".equalsIgnoreCase(productCode)))
						{
							formObject.setValue(OPERATION_CODE,"COL");  
							disableControls(OPERATION_CODE);    
						}else if(("T3U3".equalsIgnoreCase(productCode))||("I3U3".equalsIgnoreCase(productCode))||("T3U6".equalsIgnoreCase(productCode))||("T3U7".equalsIgnoreCase(productCode)))
						{
							formObject.setValue(OPERATION_CODE,"ACC");  
							disableControls(OPERATION_CODE);   
						}
					} else if("ILCB_BL".equalsIgnoreCase(requestType)){
						if(("T3S1".equalsIgnoreCase(productCode))||("I3S1".equalsIgnoreCase(productCode)))
						{
							formObject.setValue(OPERATION_CODE,"PAY");  
							disableControls(OPERATION_CODE);   
						}else if(("T3U1".equalsIgnoreCase(productCode))||("I3U1".equalsIgnoreCase(productCode)))
						{
							formObject.setValue(OPERATION_CODE,"ACC"); 
							disableControls(OPERATION_CODE);   
						}
					}
				} else {
					log.info("queryyyyyyyy 123");
					if("0".equalsIgnoreCase(operationCode) || "".equalsIgnoreCase(operationCode))
					{
						log.info("queryyyyyyyy");
						formObject.setValue(OPERATION_CODE,autoPopulateVal.get(0).get(0)); 
						log.info("formObject.setValue(OPERATION_CODE)  ::" +formObject.getValue(OPERATION_CODE).toString());
					}		
				}
			} else{
				query = "select operation_code_ws from ext_tfo where wi_name = '"+this.sWorkitemID+"'";
				List<List<String>> value = formObject.getDataFromDB(query);
				log.info("query from else: "+query);
				log.info("value size: "+value.size());
				if(!("ILC_AM".equalsIgnoreCase(requestType))){
					disableControls(OPERATION_CODE); 
				}
				if("0".equalsIgnoreCase(operationCode) || "".equalsIgnoreCase(operationCode)){
					if(!value.isEmpty()){
						log.info("value.get(0).get(0): "+value.get(0).get(0));
						formObject.setValue(OPERATION_CODE,value.get(0).get(0));
					}
				}
			}
		}
		if(("NI".equalsIgnoreCase(requestType))||("GA".equalsIgnoreCase(requestType))
				||("DBA_BL".equalsIgnoreCase(requestType))||("IC_BL".equalsIgnoreCase(requestType))
				||("IFP".equalsIgnoreCase(requestCategory)&&"LD".equalsIgnoreCase(requestType))
						||("SCF".equalsIgnoreCase(requestCategory)&&("PD".equalsIgnoreCase(requestType)||"PDD".equalsIgnoreCase(requestType)))//ADDED FOR SCF //ATP - 204,205
						||("IFA".equalsIgnoreCase(requestCategory)&&"LD".equalsIgnoreCase(requestType))){ 
			disableControls(OPERATION_CODE); 
		}
		log.info("formObject.setValue(OPERATION_CODE)  22::" +formObject.getValue(OPERATION_CODE).toString());
	}

	private String loadComplianceTabData() {
		log.info("loadComplianceTabData for PM started");
		String sectionEnable = ""; //6/12/21
		try {
			if((null == formObject.getValue(COMP_REF).toString()
					|| "".equalsIgnoreCase(formObject.getValue(COMP_REF).toString()))
					|| (formObject.getValue(COMP_REF).toString()).equalsIgnoreCase("N")){
				setSectionState("Frame15","C"); 
				setRefToComp("N");
				formObject.applyGroup(CONTROL_SET_COMPLIANCE_CHECK_DISABLE_FIELDS);
				formObject.resetGroup(CONTROL_SET_COMPLIANCE_CHECK_DISABLE_FIELDS);

			}else {
				//setSectionState("Frame15","E"); //06/12/21
				//setSectionState(FRM_LLI,"E"); //06/12/21
				setRefToComp("Y");
				formObject.applyGroup(CONTROL_SET_COMPLIANCE_CHECK_ENABLE_FIELDS);
				sectionEnable = "enableSection"; //6/12/21
			}
			log.info("formObject.getValue(COMP_REF).toString(): "+formObject.getValue(COMP_REF).toString());
			if (formObject.getValue(COMP_REF).toString().equalsIgnoreCase("Y")) {
				enableControls(COMP_EXP_REMARKS);
			}
			else {
				formObject.setValue(COMP_EXP_REMARKS, "");
				disableControls(COMP_EXP_REMARKS);
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		} 
		log.info("loadComplianceTabData for PM ends");
		return sectionEnable; //6/12/21
	}

	private void setRefToComp(String strYes){
		log.info("******************Inside setRefToComp************************");
		/*try {
			if("Y".equalsIgnoreCase(strYes)){
				log.info("setting Y");
				formObject.setValue(COMP_REF, strYes);
				enableControls(COMP_EXP_REMARKS);
				disableControls("COMP_REF,COMP_REF_1");
			}else if(!"Y".equalsIgnoreCase(strYes)){
				formObject.setValue(COMP_REF, "");
				disableControls("COMP_REF,COMP_EXP_REMARKS");
				formObject.setValue(COMP_EXP_REMARKS, "");
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}    	*/
	}

	private void makeNonEditableIfsInputDtlsTab(){
		try {
			disableControls(DISABLE_PM_IF_TXT);
			disableControls(DISABLE_PM_ADVISING_TXT);
			disableControls(DISABLE_PM_IF_LOV);
			disableControls(DISABLE_PM_IF_CHECKBOX);
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}
	
	public void loadBillProductCode(){
		String branchCode=(String) formObject.getValue(RELATIONSHIP_TYPE);
		log.info("in LoadBillProductCode branchCode="+branchCode);
		if(("IFP".equalsIgnoreCase(requestCategory))&&("LD".equalsIgnoreCase(requestType))
			&&("C".equalsIgnoreCase(branchCode)) ){
			log.info("in LoadBillProductCode if");
			setBillProductCode();     
		}else if(("IFA".equalsIgnoreCase(requestCategory))&&("LD".equalsIgnoreCase(requestType))
				&&("C".equalsIgnoreCase(branchCode)) ){
				log.info("in LoadBillProductCode if");
				setBillProductCode();     
		}else if(("IFA".equalsIgnoreCase(requestCategory)) &&("LD".equalsIgnoreCase(requestType))){
			enableControls(BILL_PRODUCT_CODE);
		}else{
			log.info("in LoadBillProductCode else");
			formObject.setValue(BILL_PRODUCT_CODE,""); 
			disableControls(BILL_PRODUCT_CODE);
		}
	}

	public void setBillProductCode(){
		String productCode = (String) formObject.getValue(PRODUCT_TYPE);
		if(("L128".equalsIgnoreCase(productCode))||("L155".equalsIgnoreCase(productCode)))
		{
			formObject.setValue(BILL_PRODUCT_CODE,"T3S7");
		}
		else if(("L092".equalsIgnoreCase(productCode))||("L154".equalsIgnoreCase(productCode)))
		{
			formObject.setValue(BILL_PRODUCT_CODE,"T3S9");
		}

	}
	public void duplicateCheckConfirmation(String controlName, boolean bChange){

		try {
			log.info("[duplicateCheckConfirmation]:");
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
				if(sDecision.isEmpty()||sDecision.trim().equalsIgnoreCase(""))
					formObject.setValue(DEC_CODE, "");
			}
		} catch (Exception e) {
			log.error("exception in duplicateCheckConfirmation: ",e);
		}
		finalDecisionHandling(DEC_CODE, REJ_RESN_PPM);
		changeCreateAmendField();
		changeFCUBSContractNumber(FCUBS_CON_NO);
	}

	public void getModeOfGuarantee(){
		try{
			if(("ILC".equalsIgnoreCase(requestCategory))||("ELC".equalsIgnoreCase(requestCategory))
					||("GRNT".equalsIgnoreCase(requestCategory))||("SBLC".equalsIgnoreCase(requestCategory))){//RR	
				log.info("Inside getModeOfGuarantee");
				String sGTMODMast = "";
				List<List<String>> sResultSet = null;
				String sQuery1 =  "SELECT MODE_OF_GTEE from EXT_TFO where WI_NAME = '"+this.sWorkitemID+"'";
				log.info("Query "+sQuery1);
				sResultSet = formObject.getDataFromDB(sQuery1);
				log.info("Rseultset "+sResultSet);
				if(sResultSet != null){
					if(!sResultSet.get(0).get(0).isEmpty())
						sGTMODMast = sResultSet.get(0).get(0);
				}
				log.info("Mode Of Gtee "+sGTMODMast);
				modeOfGurantee=sGTMODMast;
			}

		}catch(Exception e){
			log.error("Exception: ",e);
		}
	}

	public void setAmendmentList(String controlName){
		log.info("inside setAmendmentList >>");
		if("GAA".equalsIgnoreCase(requestType)){
			String tenor=formObject.getValue(TRN_TENOR).toString();
			if(("".equalsIgnoreCase(tenor))||("null".equalsIgnoreCase(tenor))||("0".equalsIgnoreCase(tenor))){
				formObject.setValue(controlName, "");
			}
			else{
				loadLovOnCond(strAmendmentTnr,tenor,controlName);
			}
		}
	}

	public void loadLovOnCond(String listVal, String cond, String controlName) {
		log.info(" loadLovonClick List " + listVal + " \n control " + controlName + " \n cond " + cond);
		try {
			String sTenor="";
			sTenor = formObject.getValue(TRN_TENOR).toString();
			log.info("[TFO_TRN_TENOR] "+sTenor);
			boolean flg = false;
			if( "GAA".equalsIgnoreCase(requestType)){
				if(!("".equalsIgnoreCase(sTenor)  || "0".equalsIgnoreCase(sTenor))){
					log.info("Checking [TFO_TRN_TENOR]==>[TRUE returned]");
					enableControls("AMEND_TYPE");
					flg = true;
				}
			}
			if ("AM".equalsIgnoreCase(requestType) || ("GAA".equalsIgnoreCase(requestType) && flg)) {

				log.info("inside if block");
				if (listVal.length() > 0) {
					String temp = formObject.getValue(controlName).toString();
					log.info("Controm value in loadLovOnCond block " +temp);
					if (getListCount(controlName) > 0) {
						formObject.setValue(controlName, "");
					}
					String[] tempArr = listVal.split("#~#");
					for (int counter = 0; counter < tempArr.length; counter++) {
						if (cond.equalsIgnoreCase(tempArr[counter].split("###")[1])) {
							formObject.addItemInCombo(controlName, tempArr[counter].split("###")[0], getAmndCode(tempArr[counter].split("###")[0]));
						}
					}
					if(null != temp){
						formObject.setValue(controlName, temp);
					}

				}
			}else if( "TL_AM".equalsIgnoreCase(requestType)
					|| "ILC_AM".equalsIgnoreCase(requestType)
					|| "ELC_LCAA".equalsIgnoreCase(requestType) && "PP_MAKER".equalsIgnoreCase(sActivityName) ){
				enableControls(controlName);
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	private String getAmndCode(String desc) {
		String result = "";
		try {
			if (!strAmendment.isEmpty() && !desc.isEmpty()) {
				String[] tempArr = strAmendment.split("#~#");
				for (int counter = 0; counter < tempArr.length; counter++) {
					if (desc.equalsIgnoreCase(tempArr[counter].split("###")[0])) {
						result = tempArr[counter].split("###")[1];
						log.info("result getAmndCode " + result);
						break;
					}
				}
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		return result;
	}

	public void changeFCUBSContractNumber(String controlName){
		String createAmendValue=formObject.getValue(CREATE_AMEND_CNTRCT_FCUBS).toString();
		if("Y".equalsIgnoreCase(createAmendValue)){
			//formObject.setValue(controlName, "");
			disableControls(controlName);
		}else{
			enableControls(controlName);
		}
	}

	public void setLimitPartyType(String controlName){

		String operationCode= formObject.getValue(OPERATION_CODE).toString();
		String limitPartyType=formObject.getValue(controlName).toString();
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
			String query="Select LC_LIMIT_LINE from ext_tfo where wi_name='"+this.sWorkitemID+"'";;
			lcLimitLine=getQueryData(query);
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

	public String getQueryData(String query){
		String value = "";

		try{
			log.info("Inside getModeOfGuarantee");
			List<List<String>> sResultSet = null;
			log.info("Query "+query);
			sResultSet = formObject.getDataFromDB(query);
			log.info("Rseultset "+sResultSet);
			if(sResultSet != null){
				if(!sResultSet.get(0).isEmpty() && !sResultSet.get(0).get(0).isEmpty()){
					value = sResultSet.get(0).get(0);
				}
			}
		}catch(Exception e){
			log.error("Exception: ",e);
		}
		log.info("value: "+value);
		return value;
	}

	public void	setContractLimits(){
		String productType=formObject.getValue(PRODUCT_TYPE).toString();	
		String customerId=formObject.getValue(CUSTOMER_ID).toString();
		if(("IC_AC".equalsIgnoreCase(requestType))&& 
				("T3U5".equalsIgnoreCase(productType)||"I3U5".equalsIgnoreCase(productType))){
			formObject.setValue(CL_SERIAL_NUMBER,"1");
			formObject.setValue(CL_COMBO_OPERATION,"COL");
			formObject.setValue(CL_COMBO_PARTY_TYPE,"DRAWEE");
			formObject.setValue(CL_CUSTOMER_NO,customerId);
			formObject.setValue(CL_COMBO_TYPE,"F");
			formObject.setValue(CL_PER_CONTRIBUTION,"100");
			formObject.setValue(CL_COMBO_AMOUNT_TAG,"BILL_OS_AMT");			
		}else if("DBA_BL".equalsIgnoreCase(requestType)){              
			formObject.setValue(CL_SERIAL_NUMBER,"1");
			formObject.setValue(CL_COMBO_OPERATION,"ACC");
			formObject.setValue(CL_COMBO_PARTY_TYPE,"DRAWEE");
			formObject.setValue(CL_CUSTOMER_NO,customerId);
			formObject.setValue(CL_COMBO_TYPE,"F");
			formObject.setValue(CL_PER_CONTRIBUTION,"100");
			formObject.setValue(CL_COMBO_AMOUNT_TAG,"BILL_OS_AMT");
		}else if("ILCB_AC".equalsIgnoreCase(requestType)){           
			String gridListName=LISTVIEW_CONTRACT_LIMITS;
			int length = getGridCount(gridListName);
			if(length==0){
				String contractRefNo=formObject.getValue(TRANSACTION_REFERENCE).toString();
				String otherReferenceNo=formObject.getValue("TRANSACTION_UNB_REFERENCE").toString();
				String query = " SELECT SERIAL_NUMBER,OPERATION,PARTY_TYPE,CUSTOMER_NO,TYPE,"
						+ "LINKAGE_REFERENCE_NO,PERCENTAGE_CONTRIBUTION,AMOUNT_TAG FROM TFO_DJ_CONTRACT_LIMITS_MASTER WHERE TRANSACTION_REFERENCE ='"+contractRefNo+"'";       
				loadListViewContractLimits(query,gridListName);
			}
		}
	}

	public void loadListViewContractLimits(String query, String gridID){
		try {
			log.info("inside loadListViewContractLimits >>");
			log.info("query: "+query);
			List list = formObject.getDataFromDB(query);
			JSONArray jsonArray = new JSONArray();
			for (int i = 0; i < list.size(); i++) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("SerialNumber",
						((String) ((List) list.get(i)).get(0)));
				jsonObject.put("Operation",
						((String) ((List) list.get(i)).get(1)));
				jsonObject.put("PartyType",
						((String) ((List) list.get(i)).get(2)));
				jsonObject.put("CustomerNo",
						((String) ((List) list.get(i)).get(3)));
				jsonObject.put("Type",
						((String) ((List) list.get(i)).get(4)));
				jsonObject.put("LinkageReferenceNo",
						((String) ((List) list.get(i)).get(5)));
				jsonObject.put("PercentContribution",
						((String) ((List) list.get(i)).get(6)));
				jsonObject.put("AmountTag",
						((String) ((List) list.get(i)).get(7)));
				jsonArray.add(jsonObject);
			}
			log.info("jsonArray: "+jsonArray);
			formObject.addDataToGrid(gridID, jsonArray);
		} catch (Exception e) {
			log.error("exception in loadListViewContractLimits: ",e);
		}
	}

	public void loadPartyDetailsField(){
		String thirdPartyValue=formObject.getValue(TRN_THIRD_PARTY).toString();
		String customerId=formObject.getValue(CUSTOMER_ID).toString();
		String productType=formObject.getValue(PRODUCT_TYPE).toString();
		String processType=formObject.getValue(PROCESS_TYPE).toString();
		String sourceChannel=formObject.getValue(SOURCE_CHANNEL).toString();
		log.info("thirdPartyValue ="+thirdPartyValue);
		log.info("customerId ="+customerId);
		log.info("requestCategory ="+requestCategory);
		
		if (("GRNT".equalsIgnoreCase(requestCategory)&& "NI".equalsIgnoreCase(requestType)))
				{
				 
					if(! productType.contains("T5") && "1".equalsIgnoreCase(thirdPartyValue))
								{   
									formObject.setValue(PD_CUSTOMER_ID,customerId); 
									formObject.setValue(PARTY_TYPE,"OBP");
									String partyType = formObject.getValue(PARTY_TYPE).toString();
									formObject.setValue(PARTY_DESC, partyType);
									formObject.setValue(TABLE6_PARTY_DESC,getDescriptionFromCode(PARTY_DESC, formObject.getValue(PARTY_DESC).toString()));
								}
								
					else if(!productType.contains("T5") && "2".equalsIgnoreCase(thirdPartyValue))
								{   
									formObject.setValue(PD_CUSTOMER_ID,customerId); 
									formObject.setValue(PARTY_TYPE,"APP");
									String partyType = formObject.getValue(PARTY_TYPE).toString();
									formObject.setValue(PARTY_DESC, partyType);
									formObject.setValue(TABLE6_PARTY_DESC,getDescriptionFromCode(PARTY_DESC, formObject.getValue(PARTY_DESC).toString()));
								}

					else if(productType.contains("T5"))
								{   
									formObject.setValue(PD_CUSTOMER_ID,customerId); 
									formObject.setValue(PARTY_TYPE,"APB");
									String partyType = formObject.getValue(PARTY_TYPE).toString();
									formObject.setValue(PARTY_DESC, partyType);
									formObject.setValue(TABLE6_PARTY_DESC,getDescriptionFromCode(PARTY_DESC, formObject.getValue(PARTY_DESC).toString()));
							}
				}
		else if(("ELC".equalsIgnoreCase(requestCategory)&& "ELC_SLCA".equalsIgnoreCase(requestType)))
		{
			 
			if(! productType.contains("T5") && "1".equalsIgnoreCase(thirdPartyValue))
						{   
							formObject.setValue(PD_CUSTOMER_ID,customerId); 
							formObject.setValue(PARTY_TYPE,"OBP");
							String partyType = formObject.getValue(PARTY_TYPE).toString();
							formObject.setValue(PARTY_DESC, partyType);
							formObject.setValue(TABLE6_PARTY_DESC,getDescriptionFromCode(PARTY_DESC, formObject.getValue(PARTY_DESC).toString()));
						}
						
			else if(!productType.contains("T5") && "2".equalsIgnoreCase(thirdPartyValue))
						{   
							formObject.setValue(PD_CUSTOMER_ID,customerId); 
							formObject.setValue(PARTY_TYPE,"BEN");
							String partyType = formObject.getValue(PARTY_TYPE).toString();
							formObject.setValue(PARTY_DESC, partyType);
							formObject.setValue(TABLE6_PARTY_DESC,getDescriptionFromCode(PARTY_DESC, formObject.getValue(PARTY_DESC).toString()));
						}

			else if(productType.contains("T5"))
						{   
							formObject.setValue(PD_CUSTOMER_ID,customerId); 
							formObject.setValue(PARTY_TYPE,"APB");
							String partyType = formObject.getValue(PARTY_TYPE).toString();
							formObject.setValue(PARTY_DESC, partyType);
							formObject.setValue(TABLE6_PARTY_DESC,getDescriptionFromCode(PARTY_DESC, formObject.getValue(PARTY_DESC).toString()));
						}
		}
		else if ("SBLC".equalsIgnoreCase(requestCategory)&& "SBLC_NI".equalsIgnoreCase(requestType))
		{
			if((!("SWIFT".equalsIgnoreCase(processType) || "S".equalsIgnoreCase(sourceChannel))) && "1".equalsIgnoreCase(thirdPartyValue))
			{
				formObject.setValue(PD_CUSTOMER_ID,customerId); 
				formObject.setValue(PARTY_TYPE,"OBP");
				String partyType = formObject.getValue(PARTY_TYPE).toString();
				formObject.setValue(PARTY_DESC, partyType);
				formObject.setValue(TABLE6_PARTY_DESC,getDescriptionFromCode(PARTY_DESC, formObject.getValue(PARTY_DESC).toString()));
			}
			
			else if((!("SWIFT".equalsIgnoreCase(processType) || "S".equalsIgnoreCase(sourceChannel))) && "2".equalsIgnoreCase(thirdPartyValue))
			{
				formObject.setValue(PD_CUSTOMER_ID,customerId); 
				formObject.setValue(PARTY_TYPE,"APP");
				String partyType = formObject.getValue(PARTY_TYPE).toString();
				formObject.setValue(PARTY_DESC, partyType);
				formObject.setValue(TABLE6_PARTY_DESC,getDescriptionFromCode(PARTY_DESC, formObject.getValue(PARTY_DESC).toString()));
			}
			
			else if("SWIFT".equalsIgnoreCase(processType) || "S".equalsIgnoreCase(sourceChannel))
			{
				formObject.setValue(PD_CUSTOMER_ID,customerId); 
				formObject.setValue(PARTY_TYPE,"APB");
				String partyType = formObject.getValue(PARTY_TYPE).toString();
				formObject.setValue(PARTY_DESC, partyType);
				formObject.setValue(TABLE6_PARTY_DESC,getDescriptionFromCode(PARTY_DESC, formObject.getValue(PARTY_DESC).toString()));
			}
		}
		else if ("GRNT".equalsIgnoreCase(requestCategory)&& "GA".equalsIgnoreCase(requestType))
		{
			formObject.setValue(PD_CUSTOMER_ID,customerId); 
			formObject.setValue(PARTY_TYPE,"ISB");
			String partyType = formObject.getValue(PARTY_TYPE).toString();
			formObject.setValue(PARTY_DESC, partyType);
			formObject.setValue(TABLE6_PARTY_DESC,getDescriptionFromCode(PARTY_DESC, formObject.getValue(PARTY_DESC).toString()));
		}
		else if(("ILC".equalsIgnoreCase(requestCategory))||("GRNT".equalsIgnoreCase(requestCategory))||("SBLC".equalsIgnoreCase(requestCategory))){//RR
			if("1".equalsIgnoreCase(thirdPartyValue))  
			{   
				formObject.setValue(PD_CUSTOMER_ID,customerId); 
				formObject.setValue(PARTY_TYPE,"ACC");
				String partyType = formObject.getValue(PARTY_TYPE).toString();
				formObject.setValue(PARTY_DESC, partyType);
				formObject.setValue(TABLE6_PARTY_DESC,getDescriptionFromCode(PARTY_DESC, formObject.getValue(PARTY_DESC).toString()));
			}
			else if("2".equalsIgnoreCase(thirdPartyValue))
			{ 
				formObject.setValue(PD_CUSTOMER_ID,customerId);
				formObject.setValue(PARTY_TYPE,"APP");
				String partyType = formObject.getValue(PARTY_TYPE).toString();
				formObject.setValue(PARTY_DESC, partyType);
				formObject.setValue(TABLE6_PARTY_DESC,getDescriptionFromCode(PARTY_DESC, formObject.getValue(PARTY_DESC).toString()));
			}
		}else if("ELC".equalsIgnoreCase(requestCategory)&&("ELC_SLCA".equalsIgnoreCase(requestType)||"ELC_SLCAA".equalsIgnoreCase(requestType)
				||"ELC_SER".equalsIgnoreCase(requestType)||"ELC_SCL".equalsIgnoreCase(requestType))){//RR
			if("1".equalsIgnoreCase(thirdPartyValue))  
			{   
				formObject.setValue(PD_CUSTOMER_ID,customerId); 
				formObject.setValue(PARTY_TYPE,"ACC");
				String partyType = formObject.getValue(PARTY_TYPE).toString();
				formObject.setValue(PARTY_DESC, partyType);
				formObject.setValue(TABLE6_PARTY_DESC,getDescriptionFromCode(PARTY_DESC, formObject.getValue(PARTY_DESC).toString()));
			}
			else if("2".equalsIgnoreCase(thirdPartyValue))
			{ 
				formObject.setValue(PD_CUSTOMER_ID,customerId);
				formObject.setValue(PARTY_TYPE,"APP");
				String partyType = formObject.getValue(PARTY_TYPE).toString();
				formObject.setValue(PARTY_DESC, partyType);
				formObject.setValue(TABLE6_PARTY_DESC,getDescriptionFromCode(PARTY_DESC, formObject.getValue(PARTY_DESC).toString()));
			}
		}
		else if("ELC".equalsIgnoreCase(requestCategory))
		{
			formObject.setValue(PD_CUSTOMER_ID,customerId);
			formObject.setValue(PARTY_TYPE,"BEN");
			String partyType = formObject.getValue(PARTY_TYPE).toString();
			formObject.setValue(PARTY_DESC, partyType);
			formObject.setValue(TABLE6_PARTY_DESC,getDescriptionFromCode(PARTY_DESC, formObject.getValue(PARTY_DESC).toString()));
		}
		else if(("EC".equalsIgnoreCase(requestCategory))||("ELCB".equalsIgnoreCase(requestCategory)))
		{  
			formObject.setValue(PD_CUSTOMER_ID,customerId);
			formObject.setValue(PARTY_TYPE,"DRAWER");
			String partyType = formObject.getValue(PARTY_TYPE).toString();
			formObject.setValue(PARTY_DESC, partyType);
			formObject.setValue(TABLE6_PARTY_DESC,getDescriptionFromCode(PARTY_DESC, formObject.getValue(PARTY_DESC).toString()));
		}
		else if(("IC".equalsIgnoreCase(requestCategory))||("ILCB".equalsIgnoreCase(requestCategory))
				||("DBA".equalsIgnoreCase(requestCategory)))
		{
			formObject.setValue(PD_CUSTOMER_ID,customerId);
			formObject.setValue(PARTY_TYPE,"DRAWEE");
			String partyType = formObject.getValue(PARTY_TYPE).toString();
			formObject.setValue(PARTY_DESC, partyType);
			formObject.setValue(TABLE6_PARTY_DESC,getDescriptionFromCode(PARTY_DESC, formObject.getValue(PARTY_DESC).toString()));
		}
	}

	private void disableTabsForSwift(){
		if(("IC_MSM".equalsIgnoreCase(requestType))||("EC_MSM".equalsIgnoreCase(requestType))||("ILC_MSM".equalsIgnoreCase(requestType))
				||("ELC_MSM".equalsIgnoreCase(requestType))||("ILCB_MSM".equalsIgnoreCase(requestType))||("ELCB_MSM".equalsIgnoreCase(requestType))
				||("GRNT_MSM".equalsIgnoreCase(requestType))||("TL_MSM".equalsIgnoreCase(requestType))||("DBA_MSM".equalsIgnoreCase(requestType))
				||("MISC_MSM".equalsIgnoreCase(requestType))||("IFS_MSM".equalsIgnoreCase(requestType))||("IFP_MSM".equalsIgnoreCase(requestType))
				||("ELC_PA".equalsIgnoreCase(requestType))||("ELCB_AOR".equalsIgnoreCase(requestType))||("ILCB_AOD".equalsIgnoreCase(requestType))
				||("ILCB_AOP".equalsIgnoreCase(requestType))||("ILCB_AOD".equalsIgnoreCase(requestType))||("SBLC_MSM".equalsIgnoreCase(requestType))){ //RR

			disableControls(DISABLE_PC_PD_DETAILS);
			disableControls(DISABLE_PC_CONTRACT_LIMIT);
			disableControls("btnModifyPartyDetails,btnAddPartyDetails,btnPDFetch"); 
			formObject.setValue(ROUTE_TO_PM, "ADCB Checker");
		}
	}

	private void setGoodDescription(){
		try{
			String sQuery = "SELECT SWIFT_UTILITY_FLAG FROM EXT_TFO WHERE WI_NAME = '"+this.sWorkitemID+"' ";
			List<List<String>> record = formObject.getDataFromDB(sQuery);
			String swiftUtilityFlag = record.get(0).get(0);
			if("Y".equalsIgnoreCase(swiftUtilityFlag)){
				sQuery = "SELECT DESC_GD_SVC FROM TFO_DJ_SWIFT_TXN_DETAILS WHERE WI_NAME = '"+this.sWorkitemID+"' ";
				record = formObject.getDataFromDB(sQuery);
				String value=record.get(0).get(0);
				log.info("DESC_GD_SVC="+value);
				if(value!=null || (!"".equalsIgnoreCase(value))){
					formObject.setValue("LC_GOODS_DESC", value);
				}
				disableControls("LC_GOODS_DESC");
			}
		}
		catch(Exception e){
			log.error("Exception: ",e);
		}
	}

	private void setCreateAMendLov(){
		log.info("setCreateAMendLov ");
		List<List<String>> recordList = null;
		try{
			String sQuery1 =  "select operation_type from tfo_dj_create_amend_ed_mast "+
					" where  request_category_code='"+requestCategory+"' and request_type_code='"+requestType+"'";
			log.info("Query "+sQuery1);
			recordList = formObject.getDataFromDB(sQuery1);
			log.info("recordlist: "+recordList);
			if(recordList != null && !recordList.isEmpty()){
				createAmendOperType=recordList.get(0).get(0);
				log.info("createAmendOperType: "+createAmendOperType);
			}

			if("NA".equalsIgnoreCase(createAmendOperType)){
				formObject.clearCombo(CREATE_AMEND_CNTRCT_FCUBS);
				formObject.addItemInCombo(CREATE_AMEND_CNTRCT_FCUBS,"NA");

			}
			/*else {
				formObject.removeItemFromCombo(CREATE_AMEND_CNTRCT_FCUBS,1);  
			}
			 */
		}catch(Exception e){
			log.error("Exception: ",e);
		}
	}

	private void loadCheckAmendField(){
		log.info("inside loadCheckAmendField>>");
		String createAmendValue=formObject.getValue(CREATE_AMEND_CNTRCT_FCUBS).toString();
		disableControls(CREATE_AMEND_STATUS_FCUBS);
		List<List<String>> recordList = null;
		try{
			String relationshipType=formObject.getValue(RELATIONSHIP_TYPE).toString();
			if(SWIFT.equalsIgnoreCase(fetchProcessType())
					&& (!("EC_AC".equalsIgnoreCase(requestType))||("ELC_LCA".equalsIgnoreCase(requestType))||("ELC_LCAA".equalsIgnoreCase(requestType))||("ELC_LCC".equalsIgnoreCase(requestType))||("ELCB_AC".equalsIgnoreCase(requestType))
							||("GRNT".equalsIgnoreCase(requestCategory) &&("NI".equalsIgnoreCase(requestType)
									||"AM".equalsIgnoreCase(requestType)||"GA".equalsIgnoreCase(requestType)
									||"GAA".equalsIgnoreCase(requestType)))
									||("SBLC".equalsIgnoreCase(requestCategory)&&("SBLC_NI".equalsIgnoreCase(requestType)||"SBLC_AM".equalsIgnoreCase(requestType)))
									||("ELC".equalsIgnoreCase(requestCategory)&&("ELC_SLCA".equalsIgnoreCase(requestType)||"ELC_SLCAA".equalsIgnoreCase(requestType)))
							)){	//RR
				log.info("when process is swift..");
				formObject.setValue(CREATE_AMEND_CNTRCT_FCUBS,"NA");
				disableControls(CREATE_AMEND_CNTRCT_FCUBS);
			}
			else if("IFP".equalsIgnoreCase(requestCategory)  
					&&"LD".equalsIgnoreCase(requestType)&&"I".equalsIgnoreCase(relationshipType)){
				log.info("IFP req category");
				formObject.setValue(CREATE_AMEND_CNTRCT_FCUBS,"N");
				disableControls(CREATE_AMEND_CNTRCT_FCUBS);
			}else if("SCF".equalsIgnoreCase(requestCategory)  
					&&("PD".equalsIgnoreCase(requestType) || "PDD".equalsIgnoreCase(requestType))&&"I".equalsIgnoreCase(relationshipType)){ ///ADDED BY SHIVANSHU FOR SCF //ATP - 204,205
				log.info("SCF req category");
				formObject.setValue(CREATE_AMEND_CNTRCT_FCUBS,"N");
				disableControls(CREATE_AMEND_CNTRCT_FCUBS);
			}
			else{
				String sourceChannel=formObject.getValue(SOURCE_CHANNEL).toString();
				String sQuery1 =  "select default_value,is_protected_yes_no,operation_type from tfo_dj_create_amend_ed_mast "+
						" where  request_category_code='"+requestCategory+"' and request_type_code='"+requestType+"' and (source_channel_code='"+sourceChannel+"' or source_channel_code='NA')";
				log.info("Query "+sQuery1);
				recordList = formObject.getDataFromDB(sQuery1);
				log.info("Resultset "+recordList);
				if(recordList != null && !recordList.isEmpty()){
					formObject.setValue(CREATE_AMEND_CNTRCT_FCUBS,recordList.get(0).get(0));
					if("Yes".equalsIgnoreCase(recordList.get(0).get(1)))
					{
						disableControls(CREATE_AMEND_CNTRCT_FCUBS);
					}else{
						enableControls(CREATE_AMEND_CNTRCT_FCUBS);
					}
				}
				changeCreateAmendField();
			}
		}catch(Exception e){
			log.error("Exception: ",e);	
		}
	}

	public String fetchProcessType(){
		log.info("in fetchProcessType");
		String sProcessType = "";
		try
		{
			String strq="SELECT PROCESS_TYPE FROM EXT_TFO WHERE WI_NAME='"+sWorkitemID+"'";
			log.info(strq);
			List<List<String>> sOutputlist = formObject.getDataFromDB(strq);
			if(sOutputlist != null && !sOutputlist.isEmpty() && !sOutputlist.get(0).isEmpty()){
				sProcessType = sOutputlist.get(0).get(0);
			}			
		}
		catch(Exception e)
		{
			log.error("Exception: ",e);
		}
		return sProcessType;			
	}

	public void changeCreateAmendField(){
		log.info("changeCreateAmendField function called");
		String sourceChannel=formObject.getValue(SOURCE_CHANNEL).toString();
		String decCode=formObject.getValue(DEC_CODE).toString();
		String relationshipType=formObject.getValue(RELATIONSHIP_TYPE).toString();

		if("IFP".equalsIgnoreCase(requestCategory)                                 
				&&"LD".equalsIgnoreCase(requestType)&&"I".equalsIgnoreCase(relationshipType)){
			formObject.setValue(CREATE_AMEND_CNTRCT_FCUBS,"N");
			disableControls(CREATE_AMEND_CNTRCT_FCUBS);
		}else if("SCF".equalsIgnoreCase(requestCategory)  
				&&("PD".equalsIgnoreCase(requestType) || "PDD".equalsIgnoreCase(requestType))&&"I".equalsIgnoreCase(relationshipType)){  //ADDEd BY SHIVANSHU FOR SCF //ATP - 204,205
			log.info("SCF req category");
			formObject.setValue(CREATE_AMEND_CNTRCT_FCUBS,"N");
			disableControls(CREATE_AMEND_CNTRCT_FCUBS);
		}else if("SG".equalsIgnoreCase(requestCategory)){   //Shipping_Gtee_62
			formObject.setValue(CREATE_AMEND_CNTRCT_FCUBS,"N");
			disableControls(CREATE_AMEND_CNTRCT_FCUBS);
		}else
		{
			/*else  if(!(("P".equalsIgnoreCase(sourceChannel)) || ("EC_P".equalsIgnoreCase(sourceChannel))
				|| ("IC_P".equalsIgnoreCase(sourceChannel))|| ("ILCB_P".equalsIgnoreCase(sourceChannel))
				|| ("ELCB_P".equalsIgnoreCase(sourceChannel))|| ("ILC_P".equalsIgnoreCase(sourceChannel))
				|| ("DBA_P".equalsIgnoreCase(sourceChannel)))
				)
		{*/
			if("C".equalsIgnoreCase(createAmendOperType)||"A".equalsIgnoreCase(createAmendOperType))
			{	log.info("setting value for CREATE_AMEND_CNTRCT_FCUBS: "+createAmendOperType);
			log.info("decCode: "+decCode);
			getDescriptionFromCode(CREATE_AMEND_CNTRCT_FCUBS,"PT");
			if("RET".equalsIgnoreCase(decCode)){
				log.info(" in ret decCode: ");
				formObject.setValue(CREATE_AMEND_CNTRCT_FCUBS,"N");
				disableControls(CREATE_AMEND_CNTRCT_FCUBS);
			}else if(TXNC.equalsIgnoreCase(decCode)){
				formObject.setValue(CREATE_AMEND_CNTRCT_FCUBS,"Y");
				enableControls(CREATE_AMEND_CNTRCT_FCUBS);
			}else if("REF".equalsIgnoreCase(decCode)&& "A".equalsIgnoreCase(createAmendOperType)){
				formObject.setValue(CREATE_AMEND_CNTRCT_FCUBS,"N");
				disableControls(CREATE_AMEND_CNTRCT_FCUBS);
			}else if("REF".equalsIgnoreCase(decCode)&& "C".equalsIgnoreCase(createAmendOperType)){
				formObject.setValue(CREATE_AMEND_CNTRCT_FCUBS,"N");
				enableControls(CREATE_AMEND_CNTRCT_FCUBS);
			}	
			}
		}
		if((!"TXNC".equalsIgnoreCase(decCode))&&("700".equalsIgnoreCase(formObject.getValue(SWIFT_MESSAGE_TYPE).toString()) || "710".equalsIgnoreCase(formObject.getValue(SWIFT_MESSAGE_TYPE).toString())) && 
				"SWIFT".equalsIgnoreCase(fetchProcessType()) && "P".equalsIgnoreCase(formObject.getValue(SWIFT_PROCESSING_STATUS).toString())){
			formObject.setValue(CREATE_AMEND_CNTRCT_FCUBS, "PT");
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

		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	private void setTargetWorkstep(){
		String createAmendValue = formObject.getValue(CREATE_AMEND_CNTRCT_FCUBS).toString();
		String createAmendStatus = formObject.getValue(CREATE_AMEND_STATUS_FCUBS).toString();
		String processingSys = formObject.getValue(PROCESSING_SYSTEM).toString();
		String sQuery = "";
		log.info("[setTargetWorkstep]["+sWorkitemID+"]: CREATE_AMEND_CNTRCT_FCUBS = "+ createAmendValue +": CREATE_AMEND_STATUS_FCUBS = "+ createAmendStatus);
		try{
			if("TXNAU".equalsIgnoreCase(formObject.getValue(DEC_CODE).toString())){ 
				sQuery = "UPDATE EXT_TFO SET FINAL_DECISION_PM = (select target_queue from tfo_dj_pm_route_mast "
						+ "WHERE request_category_code = '"+ requestCategory +"' and request_type_code = '"+ requestType +"'"
						+ " and (processing_system = 'NA' OR processing_system = '"+processingSys+"') AND rownum=1) "
						+ "WHERE WI_NAME = '"+sWorkitemID+"'";				
			}
			else {
				sQuery = "UPDATE EXT_TFO SET FINAL_DECISION_PM = (select target_queue from tfo_dj_pm_route_mast "
						+ "WHERE request_category_code = '"+ requestCategory +"' and request_type_code = '"+ requestType +"' and "
						+ "CREATE_AMEND_CNTRCT_FCUBS = '"+ createAmendValue +"' and (UPPER(CREATE_AMEND_STATUS_FCUBS) = UPPER('"+ createAmendStatus +"') "
						+ "OR CREATE_AMEND_STATUS_FCUBS = 'NA')"
						+ " and (processing_system = 'NA' OR processing_system = '"+processingSys+"') AND rownum=1 "
						+") where WI_NAME = '"+ sWorkitemID +"'";			
			}
			log.info("[setTargetWorkstep] ["+sWorkitemID+"]: setContractData update ext table records query : "+ sQuery);
			int value = formObject.saveDataInDB(sQuery);
			log.info("[setTargetWorkstep] ["+sWorkitemID+"]: setContractData GRNT update : "+ value);
		} catch(Exception e){		
			log.error("Exception: ",e);			
		}

	}

	private void chkTransactionTenor(String sRequestType, String pControlName) {
		log.info("*********************Inside ChkTransactionTenor*******************************");
		if ("GA".equalsIgnoreCase(sRequestType)) {
			log.info(" GA case " + formObject.getValue(pControlName).toString());
			disableControls("NEW_EXP_DATE####NEW_TRN_AMT");
			if ("OE".equalsIgnoreCase(formObject.getValue(pControlName).toString())) {
				log.info("OE");
				formObject.setValue(EXP_DATE, "");
				disableControls(EXP_DATE);
			} else if ("FD".equalsIgnoreCase(formObject.getValue(pControlName).toString())) {
				log.info("FD");
				enableControls(EXP_DATE);
			}
		} else if("GAA".equalsIgnoreCase(sRequestType)) {
			log.info("GAA" +formObject.getValue(pControlName).toString());
			disableControls(EXP_DATE);
		}
		log.info("*********************END ChkTransactionTenor*******************************");

	}
	public boolean implementTabNavigationPM(String data) {
		log.info(">>>>>>>>>> Inside navigation method <<<<<<<<<< -- A");
		String a[] =data.split(",");
		String button=a[0];
		String sheetID=a[1];
		int counterListCount=Integer.parseInt(a[2]);
		log.info("in button="+button+"sheetID="+sheetID+"listCount=");
		try { 
			if (sheetID.equalsIgnoreCase(PM_VERIFY_SHEET_ID)) {   //verfiy details
				log.info("Tab0-1");
				if (validateVarifyDtlsTab()) {
					disableFieldOnDemand(BUTTON_SUBMIT);
				} else {
					disableFieldOnDemand(BUTTON_SUBMIT);
					return false;
				}
			} else if (sheetID.equalsIgnoreCase(PM_INPUT_SHEET_ID)) {  //input details
				log.info("Tab1-2");
				if(pmValidateInputFrm(formObject.getValue(REQUEST_TYPE).toString())){
					disableFieldOnDemand(BUTTON_SUBMIT);
				}else{
					disableFieldOnDemand(BUTTON_SUBMIT);
					return false;
				}

			} else if (sheetID.equalsIgnoreCase(PM_CUSTOMER_SHEET_ID)) {   //Customer Details
				disableFieldOnDemand(BUTTON_SUBMIT);
			}else if (sheetID.equalsIgnoreCase(PM_COUNTER_SHEET_ID)) { //Counter Party Details
				/*	log.info("Tab3 -4");
					if (validateCPDTab(counterListCount,"add_Qvar_cpd_details")) {
						disableFieldOnDemand(BUTTON_SUBMIT);
					} else {
						disableFieldOnDemand(BUTTON_SUBMIT);
						return false;
					}*/

				String request_type = formObject.getValue(REQUEST_TYPE).toString();
				log.info("Tab3 -4 COUNTER DET");
				if (request_type.equalsIgnoreCase("LD")   
						&& formObject.getValue(PROCESSING_SYSTEM).toString().equalsIgnoreCase("F")) {
					if( validateCPDTab(counterListCount,"add_Qvar_cpd_details")){
						log.info("Tab3 -4 COUNTER DET ENTER 11");
						disableFieldOnDemand(BUTTON_SUBMIT);
					}else {
						log.info("Tab3 -4 COUNTER DET ENTER 12");
						disableFieldOnDemand(BUTTON_SUBMIT);
						return false;
					}	
				}
			}
			else if (sheetID.equalsIgnoreCase(PM_COMPLIANCE_SHEET_ID)) { //Compliance
				log.info("Tab4-5");
				if (validateCompTab() && checkComplianceValidation() && validateLLIFrame() && checkTRSDValidation()) {
					log.info("Inside the if");
					disableFieldOnDemand(BUTTON_SUBMIT);
				} else {
					disableFieldOnDemand(BUTTON_SUBMIT);
					return false;
				}				
				
			} else if (sheetID.equalsIgnoreCase(PM_TREASURY_SHEET_ID)) {//treasury					
				log.info("Tab5-6");
				if(!((("IFS".equalsIgnoreCase(requestCategory) || "IFP".equalsIgnoreCase(requestCategory)
						|| "IFA".equalsIgnoreCase(requestCategory) || "SCF".equalsIgnoreCase(requestCategory))  //ATP - 204,205
						&& ("STL".equalsIgnoreCase(requestType))) || (("GRNT".equalsIgnoreCase(requestCategory)) 
								&& ("CC".equalsIgnoreCase(requestType)||"EPC".equalsIgnoreCase(requestType)))
						)){	
					// ATP-434 REYAZ 28-03-2024
					// START CODE
					String trSellCur = formObject.getValue(TR_SELL_CUR).toString();
					String trBuyCur = formObject.getValue(TR_BUY_CUR).toString();
					if (("SCF".equalsIgnoreCase(requestCategory) && ("PD".equalsIgnoreCase(requestType) || "PDD".equalsIgnoreCase(requestType))
						   && (trSellCur.equalsIgnoreCase(trBuyCur)) && !"".equalsIgnoreCase(trBuyCur)
						   && !"".equalsIgnoreCase(trSellCur))) {
						log.info("Inside SCF buy currency and Sell currency same ");	
					} 
					// END CODE
					else if(treasuryTabValidation() && validateTrTabAndRepeaterData() && exchangeRateValidation()){
						enableFieldOnDemand(BUTTON_SUBMIT);
						//SAHIL PROD ISSUE
						log.info("Calling Dup check");
						duplicateCheckConfirmation(DUP_CHK_CONFIRMATION, false);
						log.info("Final decision Tab click");
						setEmailFlag();
					}else{
						disableFieldOnDemand(BUTTON_SUBMIT);
						return false;
					}
				}else if (("IFS".equalsIgnoreCase(requestCategory) || "IFP".equalsIgnoreCase(requestCategory)
						|| "IFA".equalsIgnoreCase(requestCategory)|| "SCF".equalsIgnoreCase(requestCategory) )  //ATP - 204,205
						&& ("STL".equalsIgnoreCase(requestType))){
					log.info("Calling Dup check");
					duplicateCheckConfirmation(DUP_CHK_CONFIRMATION, false);
					duplicateCheckConfirmation(TSLM_INVOICE_CHK_CONFIRM, false);	//code by Moksh
					log.info("Final decision Tab click");
					setEmailFlag();
					enableFieldOnDemand(BUTTON_SUBMIT);
				}
				else{
					disableFieldOnDemand(BUTTON_SUBMIT); 
				} 
			}else if (sheetID.equalsIgnoreCase(PM_PARTY_SHEET_ID)) {    //PARTY

				if(checkMandatoryPD()&& checkContractLimitTabData() && validatePartyCountry()) //Modified by Shivanshu ATP-458 
				{
					enableControls(BUTTON_SUBMIT);
					insertIntoTrsdtable();
					log.info("INSIDE  : Party save and next");
					LoadListViewOfVesselScreening();
					LoadListViewOfPartyCountryScreening();
					//LoadListViewOfPartyScreening();
					executeTabClickCommand(PM_DEC_SHEET_ID); //Final decision referral and decsion load
				}else{
					disableFieldOnDemand(BUTTON_SUBMIT);
					return false;
				}
			}else if (sheetID.equalsIgnoreCase(PM_TRSD_SHEET_ID)) {	//TRSD TAB CLICK
				log.info("INSIDE  :  Sheet No 9");
				if(validateIsEntityTRSDExist() && checkTRSDExecutionError()){
					insertIntoTrsdtable();
					executeTabClickCommand(PM_DEC_SHEET_ID); //Final decision referral and decsion load
					enableControls(BUTTON_SUBMIT);
					String trsdFlag = formObject.getValue(TRSD_FLAG).toString();
					if(pmCompReferalPopulationControl()){
						if(trsdFlag == null || "".equalsIgnoreCase(trsdFlag)){ 
							sendMessageHashMap("", "Please complete the cases pending in Screening tab");
							return false;	
						}
					}
				}
			}  else {
					//disableFieldOnDemand(BUTTON_SUBMIT);
					return false;
				}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		return true;
	}

	//added by Mansi
	private boolean validateMandatoryExpiryCondPM()  
	{
		if("BAU".equalsIgnoreCase(normalizeString(formObject.getValue(PROCESS_TYPE).toString())) &&
		"COND".equalsIgnoreCase(normalizeString(formObject.getValue(TRN_TENOR).toString())))
	     {
			return validateMandatoryFieldsPM(EXPIRY_COND, "Please Enter Expiry Conditions.");
		}
		return true;
	}
	
		
	private boolean validateMandatoryFieldsPM(String sFieldName, String alertMsg) {
		try {
			log.info("Validation Conrol Name :"+sFieldName);
			String fieldValue = formObject.getValue(sFieldName).toString();
			log.info("fieldValue --validateMandatoryFieldsPMPPM  " + fieldValue);
			if (!(isEmpty(fieldValue)) || "".equalsIgnoreCase(fieldValue)
					|| "0".equalsIgnoreCase(fieldValue)
					|| "0.00".equalsIgnoreCase(fieldValue)
					|| "0.0".equalsIgnoreCase(fieldValue)
					|| emptyStr.equalsIgnoreCase(fieldValue)) {
				log.info("validateMandatoryFieldsPMPPM blank");
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

	private boolean validateVarifyDtlsTab() {
		if("Y".equalsIgnoreCase(formObject.getValue(PT_UTILITY_FLAG).toString())){  //PT 369
			return (validateMandatoryFieldsPM(SOURCE_CHANNEL, "Please Select Source Channel")
					&& validateMandatoryFieldsPM(RELATIONSHIP_TYPE, "Please Select Transcation Branch Code"));
		}else 
			return (validateMandatoryFieldsPM(SOURCE_CHANNEL, "Please Select Source Channel")
					&& validateMandatoryFieldsPM(RELATIONSHIP_TYPE, "Please Select Transcation Branch Code")
					&& validateMandatoryFieldsPM(DELIVERY_BRANCH, "Please Select Transcation Delivery Branch"));
	}

	private boolean pmValidateInputFrm(String reqTpe) {
		log.info("****************************Inside pmValidateInputFrm*****Validating the input form(GA &GAA)*****************************");
		String reqCat = formObject.getValue(REQUEST_CATEGORY).toString();
		String processing_sys = formObject.getValue(PROCESSING_SYSTEM).toString();
		if ("GA".equalsIgnoreCase(reqTpe)) {
			log.info("validating for GA");
			if (validateMandatoryFieldsPM(PRODUCT_TYPE, "Please Select Product Code.")
					&& validateMandatoryFieldsPM(TRN_TENOR, "Please Select Expiry Type.")
					&& pmAssociatedControlCheck(reqTpe, TRN_TENOR, EXP_DATE, "Please Enter Expiry Date.")
					&& validateMandatoryFieldsPM(ACCOUNT_VALID, "Please Select Valid Account No.")
					&& pmAssociatedControlCheck(formObject.getValue(SOURCE_CHANNEL).toString(),
					formObject.getValue(ACCOUNT_VALID).toString(), ACCOUNT_NUMBER, "Please Fetch Account Number.")
					&& validateMandatoryExpiryCondPM()) {// by mansi
				return true;
			} else {
				return false;
			}
		} else if ("GAA".equalsIgnoreCase(reqTpe)) {
			log.info("validating for GAA");
			return (validateMandatoryFieldsPM(PRODUCT_TYPE, "Please Select Product Code.")
					&& validateMandatoryFieldsPM(TRN_TENOR, "Please Select Expiry Type.")
					&& validateMandatoryFieldsPM(ACCOUNT_VALID, "Please Select Valid Account No.")
					&& pmAssociatedControlCheck(formObject.getValue(SOURCE_CHANNEL).toString(),
							formObject.getValue(ACCOUNT_VALID).toString(), ACCOUNT_NUMBER, "Please Fetch Account Number.")
							&& validateMandatoryFieldsPM(AMEND_TYPE, "Please Select Amendment Type.")
							&& pmNonAssociatedControlCheck(reqTpe, formObject.getValue(AMEND_TYPE).toString(),
									NEW_TRN_AMT, "Please Enter New Transaction Amount.")
									&& pmNonAssociatedControlCheck(reqTpe, formObject.getValue(AMEND_TYPE).toString(),
											NEW_EXP_DATE, "Please Select New Expiry Date.")
											&& validateMandatoryExpiryCond_PMPC());
		}else if ("AM".equalsIgnoreCase(reqTpe)|| ("SBLC_AM".equalsIgnoreCase(reqTpe))
				|| ("ELC_SLCAA".equalsIgnoreCase(reqTpe))) {
			return (validateMandatoryExpiryCond_PMPC());
		} /*else if ("LD".equalsIgnoreCase(reqTpe) && ("IFP".equalsIgnoreCase(reqCat)
				|| "IFA".equalsIgnoreCase(reqCat) || "IFS".equalsIgnoreCase(reqCat))
				&& processing_sys.equalsIgnoreCase("T")){// CODE BY KISHAN TSLM 
			log.info("validating for Loan Amount");
			if(validateMandatoryFieldsPM(TSLM_LOAN_AMOUNT, "Please Enter Loan Amount.")){
				return true;
			}else{
				return false;
			} 
		}*/else if ("NI".equalsIgnoreCase(reqTpe) && requestCategory.equalsIgnoreCase("GRNT")) {
			log.info("validating for NI");
			return (validateMandatoryFieldsPM(PURPOSE_OF_MESSAGE, "Please Select Purpose of Message."));
		}
		else {
			log.info("returning true in else part");
			return true;
		}
	}


	private boolean pmNonAssociatedControlCheck(String param1, String param2, String checkControl, String msg) {
		log.info(" pmNonAssociatedControlCheck param1  >> " + param1 + " param2 " + param2 + "  checkControl " + checkControl + "  msg " + msg);
		if ("GA".equalsIgnoreCase(param1) || "GAA".equalsIgnoreCase(param1)) {
			if ((("EE".equalsIgnoreCase(param2)) || ("CE".equalsIgnoreCase(param2)) || ("OF".equalsIgnoreCase(param2)))
					&& NEW_EXP_DATE.equalsIgnoreCase(checkControl)) {
				formObject.setValue(NEW_TRN_AMT, "");
				return validateMandatoryFieldsPM(checkControl, msg);
			} else if (NEW_TRN_AMT.equalsIgnoreCase(checkControl)
					&& ("IV".equalsIgnoreCase(param2) || "DV".equalsIgnoreCase(param2))) {
				formObject.setValue(NEW_EXP_DATE, "");
				return validateMandatoryFieldsPM(checkControl, msg);
			}
		} else {
			formObject.setValue(NEW_TRN_AMT, "");
			formObject.setValue(NEW_EXP_DATE, "");
		}
		return true;
	}

	public boolean pmAssociatedControlCheck(String param1, String param2, String checkControl, String msg) {
		log.info("inside pmAssociatedControlCheck >>");
		log.info("param1  >> " + param1 + " param2 " + param2 + "  checkControl " + checkControl + "  msg " + checkControl);
		if ("GA".equalsIgnoreCase(param1) && "FD".equalsIgnoreCase(formObject.getValue(param2).toString())) {
			if (validateMandatoryFieldsPM(checkControl, msg)) {
				return true;
			} else {
				return false;
			}
		} else if ("GA".equalsIgnoreCase(param1) && "OE".equalsIgnoreCase(formObject.getValue(param2).toString())) {
			return true;
		} else if (!(("GA".equalsIgnoreCase(param1)) || ("GAA".equalsIgnoreCase(param1))) 
				&& "FD".equalsIgnoreCase(formObject.getValue(param2).toString())) {
			if (validateMandatoryFieldsPM(checkControl, msg)) {
				return true;
			} else {
				return false;
			}
		} else if (("GA".equalsIgnoreCase(param1)) && ("OE".equalsIgnoreCase(formObject.getValue(param2).toString())||
				"COND".equalsIgnoreCase(formObject.getValue(param2).toString()))){//by mansi
			return true;
		} else if (!(("GA".equalsIgnoreCase(param1)) || ("GAA".equalsIgnoreCase(param1))) && ("OE".equalsIgnoreCase(formObject.getValue(param2).toString())||
				"COND".equalsIgnoreCase(formObject.getValue(param2).toString()))){//by mansi) {
			return true;
		} else if (!"S".equalsIgnoreCase(param1)
				&& !("No".equalsIgnoreCase(param2) || "2".equalsIgnoreCase(param2))) {
			if (validateMandatoryFieldsPM(checkControl, msg)) {
				return true;
			} else {
				return false;
			}
		} else if (("Yes".equalsIgnoreCase(param2) || "1".equalsIgnoreCase(param2))) {
			return validateMandatoryFieldsPM(checkControl, msg);
		}
		return true;
	}

	protected boolean submitPMValidations(String data){
		try { 
			String strDecision="", strRemarks="",strDecReason="";
			strDecision= formObject.getValue(DEC_CODE).toString();
			strRemarks= formObject.getValue(REMARKS).toString();
			strDecReason = formObject.getValue(REJ_RESN_PPM).toString();
			// ATP-434 REYAZ 28-03-2024
			// START CODE
			String trSellCur = formObject.getValue(TR_SELL_CUR).toString();
			String trBuyCur = formObject.getValue(TR_BUY_CUR).toString();
			log.info("submitPMValidations  strDecision " + strDecision);
			if(duplicateCheckValidation()){
				if(!(strDecision.isEmpty() || strDecision.equalsIgnoreCase("0") || strDecision.equalsIgnoreCase(""))){
					if((strDecision.equalsIgnoreCase(TXNC))
							&& (requestType.equalsIgnoreCase("GA")?validateVarifyDtlsTab():
								requestType.equalsIgnoreCase("GAA")?validateVarifyDtlsTab():true )
								&& (requestType.equalsIgnoreCase("GA")?pmValidateInputFrm(requestType):
									requestType.equalsIgnoreCase("GAA")?pmValidateInputFrm(requestType):true)
									&& (((requestCategory.equalsIgnoreCase("IFA") || requestCategory.equalsIgnoreCase("IFS") 
											|| requestCategory.equalsIgnoreCase("IFP") || requestCategory.equalsIgnoreCase("SCF")) //ATP - 204,205
											&& (requestType.equalsIgnoreCase("LD") ||requestType.equalsIgnoreCase("PD") ||requestType.equalsIgnoreCase("PDD")))
											?pmValidateInputFrm(requestType):true)	
											&& validateCompTab()
											&&(!("SCF".equalsIgnoreCase(requestCategory) && ("PD".equalsIgnoreCase(requestType) || "PDD".equalsIgnoreCase(requestType))
													   && (trSellCur.equalsIgnoreCase(trBuyCur)) && !"".equalsIgnoreCase(trBuyCur)
													   && !"".equalsIgnoreCase(trSellCur))? treasuryTabValidation():true) 
											&&(!("SCF".equalsIgnoreCase(requestCategory) && ("PD".equalsIgnoreCase(requestType) || "PDD".equalsIgnoreCase(requestType))
													   && (trSellCur.equalsIgnoreCase(trBuyCur)) && !"".equalsIgnoreCase(trBuyCur)
													   && !"".equalsIgnoreCase(trSellCur))? validateTreasurySellAmnt(strDecision):true) 
											&&(!("SCF".equalsIgnoreCase(requestCategory) && ("PD".equalsIgnoreCase(requestType) || "PDD".equalsIgnoreCase(requestType))
													   && (trSellCur.equalsIgnoreCase(trBuyCur)) && !"".equalsIgnoreCase(trBuyCur)
													   && !"".equalsIgnoreCase(trSellCur))? validateTreasuryLoanAmnt(strDecision):true) 
											&& ContractReferenceValidationPM() 
											&& contrRefLengthValidation(FCUBS_CON_NO) 
											&& validateMandatoryFieldsPM(ROUTE_TO_PM, "Please select Route To.")
											&& txnRefNoSubmitCheck()
											&& mandatoryFieldsILC_PM()
											&& mandatoryFieldsIC_PM()
											&& mandatoryFieldsTL()
											&& mandatoryFieldsEC_PM()
											&& mandatoryFieldsILCBPM()
											&& mandatoryFieldsELCB_PM()
											&& mandatoryFieldsELC_PM()
											&& validateLLIFrame()
											&& checkMandatoryPD()                    
											&& checkMandatoryOperationCode()         
											&& checkMandatoyBillProductCode()        
											&& checkMandatoryLimitPartyType(data) 	
											&& checkContractLimitTabData() 
											&& mandatoryFieldLoanAmount()
											&& checkReferralCount()
											&& validateIsEntityTRSDExist()
											&& checkTRSDExecutionError()
											&& validateLoanReference()
											//&& validateMandatoryFieldsPM(FCUBS_PUR_OF_MSG,"Please select FCUBS Purpose of Message.")//santhosh
											&&PMvalidateMandatoryPurposeMessage()//RR
											&&PMDocumentValidation()//added shivanshu
											&&checkLoanValidationPM("LOAN_VAL_DATE") //ATP-409 by Shivanshu
							){     
						return true; 	   

					}else if(("REF".equalsIgnoreCase(strDecision)) && (requestType.equalsIgnoreCase("GA")?validateVarifyDtlsTab():
						requestType.equalsIgnoreCase("GAA")?validateVarifyDtlsTab():true)
						&& (requestType.equalsIgnoreCase("GA")?pmValidateInputFrm(requestType):
							requestType.equalsIgnoreCase("GAA")?pmValidateInputFrm(requestType):true)
							&& (((requestCategory.equalsIgnoreCase("IFA") || requestCategory.equalsIgnoreCase("IFS") 
									|| requestCategory.equalsIgnoreCase("IFP") || requestCategory.equalsIgnoreCase("SCF")) //ATP - 204,205
									&& (requestType.equalsIgnoreCase("LD") || requestType.equalsIgnoreCase("PD") || requestType.equalsIgnoreCase("PDD")))
									?pmValidateInputFrm(requestType):true)	
									&& validateCompTab()
									&& treasuryTabValidation()
									&& validateTreasurySellAmnt(strDecision)
									&& validateLLIFrame()
									&& checkMandatoryPD()                   
									&& checkMandatoryOperationCode()         
									&& checkMandatoyBillProductCode()        
									&& checkMandatoryLimitPartyType(data)       
									&& checkContractLimitTabData() 
									&& mandatoryFieldLoanAmount()
									&& checkReferralCount()
									&& validateIsEntityTRSDExist()
									&& checkTRSDExecutionError()
									&& validateLoanReference()) {  
						return true; 
					}

					else if(strDecision.equalsIgnoreCase("RET")){
						if(strRemarks.isEmpty() || strRemarks.equalsIgnoreCase("")){
							sendMessageHashMap(REMARKS, "Enter remarks for return");
							return false;
						}
						else
							return true;
					}
					else if(strDecision.equalsIgnoreCase("STBPD")){ //added by mansi
							return true;
					}
					else if(strDecision.equalsIgnoreCase("REJ")){					
						if(strDecReason.isEmpty()|| strDecReason.equalsIgnoreCase("0")|| strDecReason.equalsIgnoreCase("")){
							sendMessageHashMap(REJ_RESN_PPM, "Please select reason for rejection.");
							return false;
						}
						else
							return true;	
					}
					else
						return false;

				}
				else{
					sendMessageHashMap(DEC_CODE, "Please select Decision");
					return false;
				}
			} else{
				return false;
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
			return false;
		}

	}

	private boolean checkTRSDExecutionError() {
		try {
			log.info("*********Inside checkTRSDExecutionError start***********");
			String sQuery = "SELECT TRSD_FLAG FROM EXT_TFO WHERE WI_NAME  = '"+sWorkitemID+"' ";
			log.info("sQuery : "+sQuery);
			List<List<String>> sOutput = formObject.getDataFromDB(sQuery);
			log.info("sOutput : "+sOutput);
			if(sOutput != null && sOutput.size() > 0){
				String flag = sOutput.get(0).get(0);
				if ("P".equalsIgnoreCase(flag)){
					sendMessageHashMap("", "Screening error. Please execute FSK again");
					return false;		
				}else if(pmCompReferalPopulationControl()){
					if(flag == null || "".equalsIgnoreCase(flag)){ 
						sendMessageHashMap("", "Please complete the cases pending in Screening tab");
						return false;	
					}
					else{
					// ATP-489 REYAZ 04-07-2024 START
					/*	String sQuer = "select execution_status from tfo_dj_trsd_screening_details where winame='"+sWorkitemID+"'";
						log.info("sQuer : "+sQuer);
						List<List<String>> sOut;
						sOut = formObject.getDataFromDB(sQuer);
						log.info("sOut : "+sOut);

						if(sOut != null && sOut.size() > 0){

							for(int i=0;i<sOut.size();i++){ 
								String executionStatus=sOut.get(i).get(0).toString();
								if ("N".equalsIgnoreCase(executionStatus)){
									sendMessageHashMap("", "Please complete the cases pending in Screening tab");
									return false;
								}
							}
						}

						int iRowCount = getGridCount(LISTVIEW_TRSD_TABLE_OPTIONAL);
						for (int count = 0; count < iRowCount; count++) {
							String executionStatus = formObject.getTableCellValue(LISTVIEW_TRSD_TABLE, count,13);
							if ("N".equalsIgnoreCase(executionStatus)){
								sendMessageHashMap("", "Please complete the cases pending in Screening tab");
								return false;
							}
						}

						String sQuery1 = "select execution_status from  tfo_dj_trsd_screening_other_details where winame='"+sWorkitemID+"'";
						log.info("sQuery1 : "+sQuery1);
						List<List<String>> sOutput1;
						sOutput1 = formObject.getDataFromDB(sQuery1);
						log.info("sOutput1 : "+sOutput1);

						if(sOutput1 != null && sOutput1.size() > 0){

							for(int i=0;i<sOutput1.size();i++){ 

								String executionStatus=sOutput1.get(i).get(0).toString();
								if ("N".equalsIgnoreCase(executionStatus)){
									sendMessageHashMap("", "Please complete the cases pending in Screening tab");
									return false;
								}
							}
						}
						*/
						int iRowCount = getGridCount(LISTVIEW_TRSD_TABLE);
						for (int count = 0; count < iRowCount; count++) {
							String executionStatus = formObject.getTableCellValue(LISTVIEW_TRSD_TABLE, count,13);
							if ("N".equalsIgnoreCase(executionStatus)){
								sendMessageHashMap("", "Please complete the cases pending in Screening tab");
								return false;
							}
						}
						
						iRowCount = getGridCount(LISTVIEW_TRSD_TABLE_OPTIONAL);
						for (int count = 0; count < iRowCount; count++) {
							String executionStatus = formObject.getTableCellValue(LISTVIEW_TRSD_TABLE_OPTIONAL, count,12);
							if ("N".equalsIgnoreCase(executionStatus)){
								sendMessageHashMap("", "Please complete the cases pending in Screening tab");
								return false;
							}
						}
						// ATP-489 REYAZ 04-07-2024 END
						return true;
					}
				}else {
					return true;
				}
			}
			log.info("*********Inside checkTRSDExecutionError ends***********");
			return true;
		} catch (NumberFormatException e) {
			log.error("Exception: ",e);
		}
		return true;
	}

	private boolean checkReferralCount() {
		String referTo = "";
		String sQuery = "";
		String trsryRef = "";
		String compRef = "";
		List<List<String>> sOutput;
		try {
			log.info("*********Inside checkTRTreasury***********");
			sQuery = "SELECT TR_REFER_TREASURY,COMP_REF FROM EXT_TFO WHERE WI_NAME  = '"+sWorkitemID+"' ";
			log.info("sQuery : "+sQuery);
			sOutput = formObject.getDataFromDB(sQuery);
			if(sOutput != null){
				trsryRef = sOutput.get(0).get(0);
				compRef = sOutput.get(0).get(1);
			}
			log.info(sWorkitemID + " : trsryRef : "+trsryRef);
			log.info(sWorkitemID + " : compRef : "+compRef);
			if ("Y".equalsIgnoreCase(trsryRef) || "Y".equalsIgnoreCase(compRef)){
				if("Y".equalsIgnoreCase(trsryRef)){
					referTo = "Treasury";
				}else if ("Y".equalsIgnoreCase(compRef)){
					referTo = "Compliance";
				}
				sQuery = "SELECT COUNT(1) AS COUNT FROM TFO_DJ_FINAL_DEC_SUMMARY WHERE REFFERD_TO = '"+referTo+"' AND WI_NAME  = '"+sWorkitemID+"' ";
				log.info("sQuery : "+sQuery);
				sOutput = formObject.getDataFromDB(sQuery);
				log.info("sOutput : "+sOutput);
				if(sOutput != null){
					int count = Integer.parseInt(sOutput.get(0).get(0));
					if (count > 0){
						return true;
					} else {
						sendMessageHashMap("", "Please add referral again");
						return false;		
					}
				}
				log.info("*********Inside checkTRTreasury ends***********");
			}else {
				return true;
			}



		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean validateTreasurySellAmnt(String Decision){
		try {
			log.info("*********Inside validateTreasurySellAmnt***********");
			if(handlingTreasuryTab()){
				if(Decision.equalsIgnoreCase(TXNC)){
					log.info("****************TXNC Decision*******************");
					if(("EC".equalsIgnoreCase(requestCategory)
							|| "IC".equalsIgnoreCase(requestCategory)
							|| "DBA".equalsIgnoreCase(requestCategory)
							|| "ILCB".equalsIgnoreCase(requestCategory) 
							|| "ELCB".equalsIgnoreCase(requestCategory) 
							|| "TL".equalsIgnoreCase(requestCategory)
							|| "IFS".equalsIgnoreCase(requestCategory)
							|| "IFA".equalsIgnoreCase(requestCategory)//Code By Rakshita
							|| "IFP".equalsIgnoreCase(requestCategory)
							|| "SCF".equalsIgnoreCase(requestCategory))) //ADDED BY SHIVANSHU SCF//ATP - 204,205
					{
						log.info("****************All request categories*******************");
						if(validateNumberFields(TR_SELL_AMT, "Sell Amount cannot be blank.")
								&& validateMandatoryFieldsPM(UI_EXCHANGE_RATE, "User Inputted Exchange Rate cannot be blank.") ){
							return true;
						}else{
							enableControls(LIST_VIEW_BUTTONS);
							disableControls(BUTTON_SUBMIT);	
							return false;
						}
					}else{
						return true;
					}
				}else if(("REF".equalsIgnoreCase(Decision))){
					log.info("****************REF Decision*******************");
					if(!("EC".equalsIgnoreCase(requestCategory) 
							|| "ELCB".equalsIgnoreCase(requestCategory))){
						if(validateNumberFields(TR_SELL_AMT, "Sell Amount cannot be blank.")){
							return true;
						}else{
							enableControls(LIST_VIEW_BUTTONS);
							disableControls(BUTTON_SUBMIT);	
							return false;
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
		} catch (Exception e) {
			log.error("excpetion in validateTreasurySellAmnt: ",e);
			enableControls(LIST_VIEW_BUTTONS);
			disableControls(BUTTON_SUBMIT);	
			return false;
		}
	}

	public boolean validateTreasuryLoanAmnt(String Decision){
		try {
			log.info("********************Inside validateTreasuryLoanAmnt**********************");
			
			if(handlingTreasuryTab()){
				if ("EC".equalsIgnoreCase(requestCategory) || "ELCB".equalsIgnoreCase(requestCategory)){
					if(validateNumberFields(TR_LOAN_AMT, "Buy Amount cannot be blank.")){
						enableControls(LIST_VIEW_BUTTONS2);
						disableControls(BUTTON_NEXT);	
						return true;
					}else{
						enableControls(LIST_VIEW_BUTTONS);
						disableControls(BUTTON_SUBMIT);	
						return false;
					}
				}else if((Decision.equalsIgnoreCase(TXNC)) && !"EC".equalsIgnoreCase(requestCategory) && !"ELCB".equalsIgnoreCase(requestCategory)){
					if(validateNumberFields(TR_LOAN_AMT, "Buy Amount cannot be blank.")){
						enableControls(LIST_VIEW_BUTTONS2);
						disableControls(BUTTON_NEXT);
						return true;
					}else{
						enableControls(LIST_VIEW_BUTTONS);
						disableControls(BUTTON_SUBMIT);	
						return false;
					}
				}else{
					enableControls(LIST_VIEW_BUTTONS2);
					disableControls(BUTTON_NEXT);
					return true;
				}

			}else{
				enableControls(LIST_VIEW_BUTTONS2);
				disableControls(BUTTON_NEXT);
				return true;
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
			return false;
		}
	}

	private boolean validateNumberFields(String sFieldName, String alertMsg){
		try {
			log.info("Validation Conrol for Numbers :"+sFieldName);
			String fieldValue = "";
			fieldValue = this.formObject.getValue(sFieldName).toString();
			log.info("Value  "+fieldValue);
			if(fieldValue.isEmpty() || fieldValue.equalsIgnoreCase("")
					|| "0".equalsIgnoreCase(fieldValue) || "0.000".equalsIgnoreCase(fieldValue)
					|| "0.0".equalsIgnoreCase(fieldValue)
					|| "0.00".equalsIgnoreCase(fieldValue)){
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

	protected boolean ContractReferenceValidationPM(){
		String processing_system = formObject.getValue(PROCESSING_SYSTEM).toString();
		enableControls(LIST_VIEW_BUTTONS2);
		disableControls(BUTTON_NEXT);
		String createAmend=formObject.getValue(CREATE_AMEND_CNTRCT_FCUBS).toString();
		if("IFP".equalsIgnoreCase(requestCategory)&&("Y".equalsIgnoreCase(createAmend)
				||"PT".equalsIgnoreCase(createAmend))){
			return true;
		}else if("IFS".equalsIgnoreCase(requestCategory) || "IFP".equalsIgnoreCase(requestCategory)    
				|| "TL".equalsIgnoreCase(requestCategory)||"IFA".equalsIgnoreCase(requestCategory)){

			if(!(processing_system.equalsIgnoreCase("T"))) {
				if ("IFP".equalsIgnoreCase(requestCategory)|| ("TL".equalsIgnoreCase(requestCategory)
						&& !("BC".equalsIgnoreCase(formObject.getValue(IFS_LOAN_GRP).toString()) 
								|| "MRPA".equalsIgnoreCase(formObject.getValue(IFS_LOAN_GRP).toString())))) {
					if (!validateMandatoryFieldsPM(BILL_LN_REFRNCE,"Please enter Bill/Loan Reference number.")) {
						return false;
					}
					if (!contrRefLengthValidation(BILL_LN_REFRNCE)) {
						return false;
					}
				}
			}	
			if (!((processing_system.equalsIgnoreCase("T")) && "LD".equalsIgnoreCase(requestType)
					&&("IFS".equalsIgnoreCase(requestCategory) || "IFP".equalsIgnoreCase(requestCategory)))) {
				if ("false".equalsIgnoreCase(formObject.getValue("UDF_FIELD_CHK").toString())) {
					sendMessageHashMap("UDF_FIELD_CHK","Please check if the Necessary UDF fields updated in FCUBS. ");
					return false;
				}
			}

		}
		/*else if("IFS".equalsIgnoreCase(requestCategory) || "IFP".equalsIgnoreCase(requestCategory)
				|| "IFA".equalsIgnoreCase(requestCategory)    //Code by Rakshita
				|| "TL".equalsIgnoreCase(requestCategory)){


			//			if(!("IFP".equalsIgnoreCase(requestCategory))){
			if("IFP".equalsIgnoreCase(requestCategory) || ("TL".equalsIgnoreCase(requestCategory) 
					&& !("BC".equalsIgnoreCase(formObject.getValue(IFS_LOAN_GRP).toString()) || 
							"MRPA".equalsIgnoreCase(formObject.getValue(IFS_LOAN_GRP).toString())))) {
				if(!validateMandatoryFieldsPM(BILL_LN_REFRNCE, "Please enter Bill/Loan Reference number.")){
					return false;
				}if(!contrRefLengthValidation(BILL_LN_REFRNCE)){
					return false;
				}
			}
		}	if((("IFP".equalsIgnoreCase(requestCategory))&& ("LD".equalsIgnoreCase(requestType)))
				||(("IFS".equalsIgnoreCase(requestCategory))&& ("LD".equalsIgnoreCase(requestType)))
				&& !formObject.getValue(PROCESSING_SYSTEM).toString().equalsIgnoreCase("T")){
			if("false".equalsIgnoreCase(formObject.getValue("UDF_FIELD_CHK").toString())){//CODE BY KRITIKA
				sendMessageHashMap("UDF_FIELD_CHK", "Please check if the Necessary UDF fields updated in FCUBS. ");
				return false;
			}
		}*/
		return true;
	}



	protected boolean mandatoryFieldsILC_PM(){
		try {
			enableControls(LIST_VIEW_BUTTONS2);
			disableControls(BUTTON_NEXT);

			if("ILC".equalsIgnoreCase(requestCategory)){
				String createAmend=formObject.getValue(CREATE_AMEND_CNTRCT_FCUBS).toString();
				if("PROCESSING MAKER".equalsIgnoreCase(sActivityName)){
					if(("ILC_NI".equalsIgnoreCase(requestType) || "ILC_AM".equalsIgnoreCase(requestType))
							&&("Y".equalsIgnoreCase(createAmend)||"PT".equalsIgnoreCase(createAmend) ||"ET".equalsIgnoreCase(createAmend))){  //ATP-469 Shahbaz 23-05-2024
						return true;
					}
					else if(("ILC_NI".equalsIgnoreCase(requestType) || "ILC_AM".equalsIgnoreCase(requestType) 
							|| "ILC_UM".equalsIgnoreCase(requestType)) 
							&& "false".equalsIgnoreCase(formObject.getValue("SWIFT_PREPARED").toString())){
						sendMessageHashMap("SWIFT_PREPARED", "Please check SWIFT status.");
						return false;
					}else if(("ILC_NI".equalsIgnoreCase(requestType) || "ILC_AM".equalsIgnoreCase(requestType) 
							|| "ILC_UM".equalsIgnoreCase(requestType)) 
							&& "false".equalsIgnoreCase(formObject.getValue("LMT_BOOKED").toString())){
						sendMessageHashMap("LMT_BOOKED", "Please check Limt booked, Commision debit status");
						return false;
					}
					else
						return true;
				} else{
					return true;
				}

			}else{
				return true;
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
			return false;
		}
	}

	protected boolean mandatoryFieldsIC_PM(){
		try {
			if("IC".equalsIgnoreCase(requestCategory)){
				formObject.getValue(INF_SETTLEMENT_ACC_CURR);
				if("PROCESSING MAKER".equalsIgnoreCase(sActivityName)){
					String createAmend=formObject.getValue(CREATE_AMEND_CNTRCT_FCUBS).toString();
					if("IC_AC".equalsIgnoreCase(requestType)&&("Y".equalsIgnoreCase(createAmend)
							||"PT".equalsIgnoreCase(createAmend))){
						return true;
					}
					else if("IC_AC".equalsIgnoreCase(requestType) 
							&& "false".equalsIgnoreCase(formObject.getValue("IS_STAGE_CHANGED").toString())){
						sendMessageHashMap("IS_STAGE_CHANGED", "Please check Stage Changed Status.");
						return false;
					}else if("IC_AC".equalsIgnoreCase(requestType) 
							&& ("T3U5".equalsIgnoreCase(formObject.getValue(PRODUCT_TYPE).toString())
									||"I3U5".equalsIgnoreCase(formObject.getValue(PRODUCT_TYPE).toString())) 
									&& "false".equalsIgnoreCase(formObject.getValue("BLCK_LMTS_AVL").toString())){
						sendMessageHashMap("BLCK_LMTS_AVL", "Please check Block Limit and avalisation in FCUBS status");
						return false;
					}else if("IC_AC".equalsIgnoreCase(requestType) 
							&& ("T3U5".equalsIgnoreCase(formObject.getValue(PRODUCT_TYPE).toString())
									||"I3U5".equalsIgnoreCase(formObject.getValue(PRODUCT_TYPE).toString())) 
									&& "false".equalsIgnoreCase(formObject.getValue("IS_MSG_PREPARED").toString())){
						sendMessageHashMap("IS_MSG_PREPARED", "Please check avalisation message status");
						return false;
					}else if("IC_AC".equalsIgnoreCase(requestType) 
							&& "false".equalsIgnoreCase(formObject.getValue("ARE_TRCRS_DISABLED").toString())){
						sendMessageHashMap("ARE_TRCRS_DISABLED", "Please check Tracers disabled status");
						return false;
					}else if("IC_BS".equalsIgnoreCase(requestType) 
							&& (!("AED".equalsIgnoreCase(trnsCurrency))) 
							&& "false".equalsIgnoreCase(formObject.getValue("IS_M202_PREPARED").toString())){
						sendMessageHashMap("IS_M202_PREPARED", "Please check MT202 Preparation status");
						return false;
					}else if("IC_BS".equalsIgnoreCase(requestType) 
							&& ("AED".equalsIgnoreCase(trnsCurrency)) 
							&& "false".equalsIgnoreCase(formObject.getValue("IS_PMNT_HUB_CMPLTD").toString())){
						sendMessageHashMap("IS_PMNT_HUB_CMPLTD", "Please check Payment HUB Status status");
						return false;
					}else if("IC_BS".equalsIgnoreCase(requestType) 
							&& "false".equalsIgnoreCase(formObject.getValue("IS_EMAIL_READY").toString())){
						sendMessageHashMap("IS_EMAIL_READY", "Please check Email ready status");
						return false;
					}
					else
						return true;
				}else{
					return true;
				}

			}else{
				return true;
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
			return false;
		}
	}

	private boolean mandatoryFieldsTL(){
		try {
			if("TL".equalsIgnoreCase(requestCategory)){
				log.info("Currency "+trnsCurrency);
				if("PROCESSING MAKER".equalsIgnoreCase(sActivityName)){
					if((!("AED".equalsIgnoreCase(trnsCurrency))) && ("TL_LD".equalsIgnoreCase(requestType)) 
							&& "false".equalsIgnoreCase(formObject.getValue("IS_M202_PREPARED").toString())){
						sendMessageHashMap("IS_M202_PREPARED", "Please check MT202 Preparation status");
						return false;
					}else if(("AED".equalsIgnoreCase(trnsCurrency)) && ("TL_LD".equalsIgnoreCase(requestType)) 
							&& "false".equalsIgnoreCase(formObject.getValue("IS_PMNT_HUB_CMPLTD").toString())){
						sendMessageHashMap("IS_PMNT_HUB_CMPLTD", "Please check Payment HUB status");
						return false;
					}else if("TL_LD".equalsIgnoreCase(requestType) 
							&& "IC".equalsIgnoreCase(formObject.getValue(IFS_LOAN_GRP).toString()) 
							&& "false".equalsIgnoreCase(formObject.getValue("IS_EMAIL_READY").toString())){
						sendMessageHashMap("IS_EMAIL_READY", "Please check Email ready status");
						return false;
					}
					else
						return true;
				}else{
					return true;
				}

			}else{
				return true;
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
			return false;
		}
	}

	protected boolean mandatoryFieldsEC_PM(){
		try {
			enableControls(LIST_VIEW_BUTTONS2);
			disableControls(BUTTON_NEXT);
			if("EC".equalsIgnoreCase(requestCategory)){
				if("PROCESSING MAKER".equalsIgnoreCase(sActivityName)){
					if(("EC_DISC".equalsIgnoreCase(requestType) || "EC_CA".equalsIgnoreCase(requestType)) 
							&& "false".equalsIgnoreCase(formObject.getValue("EC_ENS_COR_LMT_LINES").toString())){
						sendMessageHashMap("EC_ENS_COR_LMT_LINES", "Please check Ensure the correct limit lines.");
						return false;
					}else if("EC_DISC".equalsIgnoreCase(requestType) 
							&& "false".equalsIgnoreCase(formObject.getValue("EC_COR_CUST_AC_FCUBS").toString())){
						sendMessageHashMap("EC_COR_CUST_AC_FCUBS", "Please check correct Customer A/C selected status.");
						return false;
					}else if("EC_DISC".equalsIgnoreCase(requestType) 
							&& "false".equalsIgnoreCase(formObject.getValue("EC_CHK_CLIEU_LST").toString())){
						sendMessageHashMap("EC_CHK_CLIEU_LST", "Please check CLIEU waiver list status.");
						return false;
					}else if("EC_DISC".equalsIgnoreCase(requestType) 
							&& "false".equalsIgnoreCase(formObject.getValue("EC_ENS_IF_TRD_TR_LOANS").toString())){
						sendMessageHashMap("EC_ENS_IF_TRD_TR_LOANS", "Please check Ensure to inform IF trade status.");
						return false;
					}else if("EC_CA".equalsIgnoreCase(requestType) 
							&& "false".equalsIgnoreCase(formObject.getValue("EC_CNFM_FEE_COL_ICG_APRVAL").toString())){
						sendMessageHashMap("EC_CNFM_FEE_COL_ICG_APRVAL", "Please check Confirmation fee collected status.");
						return false;
					}else if("EC_CA".equalsIgnoreCase(requestType)){
						if (validateMandatoryFieldsPM("EC_CONT_FCUBS_ICIEC_COV", "Please check T574 contract booked status.")
								&& validateMandatoryFieldsPM("EC_UPDT_SUSP_TRCK_ICIEC_PAY_GL", "Please check Update suspense tracker status.")) {
							return true;
						} else {
							return false;
						}
					}else if("EC_BL".equalsIgnoreCase(requestType)){  //PT US 143
						return 	validateMandatoryFieldsPM(COURIER_COMPANY, "Please enter Courier Company.")
								&& validateMandatoryFieldsPM(COURIER_AWB_NUMBER, "Please enter Courier AWB Number.");
					}
					else
						return true;
				} else{
					return true;
				}
			} else {
				return true;
			}
		}catch (Exception e) {
			log.error("exception in mandatoryFieldsEC_PM: ",e);
			return false;
		}
	}

	protected boolean mandatoryFieldsILCBPM(){
		try {
			if("ILCB".equalsIgnoreCase(requestCategory)){
				if("PROCESSING MAKER".equalsIgnoreCase(sActivityName)){
					if("ILCB_BS".equalsIgnoreCase(requestType) 
							&& "false".equalsIgnoreCase(formObject.getValue("ILCB_PRC_TXN_FCUBS_COR_MRG").toString())){
						sendMessageHashMap("ILCB_PRC_TXN_FCUBS_COR_MRG", "Please check transaction processed in FCUBS with correct margin status.");
						return false;
					}else if(!"AED".equalsIgnoreCase(trnsCurrency) && "ILCB_BS".equalsIgnoreCase(requestType) 
							&& "false".equalsIgnoreCase(formObject.getValue("ILCB_MT_PREP_SENT_COR_BNK").toString())){
						sendMessageHashMap("ILCB_MT_PREP_SENT_COR_BNK", "Please check MT202 Preparation status.");
						return false;
					}else if("AED".equalsIgnoreCase(trnsCurrency) && "ILCB_BS".equalsIgnoreCase(requestType) 
							&& "false".equalsIgnoreCase(formObject.getValue("ILCB_PAY_HUB_CMPL").toString())){
						sendMessageHashMap("ILCB_PAY_HUB_CMPL", "Please check Payment HUB status.");
						return false;
					}
					else
						return true;
				} else {
					return true;
				}
			}else{
				return true;
			}
		}catch (Exception e) {
			log.error("exception in mandatoryFieldsILCB_PM: ",e);
			return false;
		}
	}

	protected boolean mandatoryFieldsELCB_PM(){
		String decision = formObject.getValue(DEC_CODE).toString();
		String productCode = formObject.getValue(PRODUCT_TYPE).toString();

		try {
			log.info("mandatoryFieldsELCB_PM");
			if("ELCB".equalsIgnoreCase(requestCategory)){
				if("PROCESSING MAKER".equalsIgnoreCase(sActivityName)){
					if("ELCB_DISC".equalsIgnoreCase(requestType) 
							&& "false".equalsIgnoreCase(formObject.getValue("ELCB_NECES_PRC_APRVL").toString())){
						sendMessageHashMap("ELCB_NECES_PRC_APRVL", "Please check necessary pricing approval status.");
						return false;
					}else if("ELCB_DISC".equalsIgnoreCase(requestType) 
							&& "false".equalsIgnoreCase(formObject.getValue("ELCB_ENS_COR_LMT_LINES").toString())){
						sendMessageHashMap("ELCB_ENS_COR_LMT_LINES", "Please check correct limit lines status.");
						return false;
					}else if("ELCB_DISC".equalsIgnoreCase(requestType) 
							&& "false".equalsIgnoreCase(formObject.getValue("ELCB_CHK_ACC_NO_CRDT").toString())){
						sendMessageHashMap("ELCB_CHK_ACC_NO_CRDT", "Please check A/C no to credit and manual advice status.");
						return false;
					}else if("ELCB_DISC".equalsIgnoreCase(requestType)){
						if (validateMandatoryFieldsPM("ELCB_ENS_IF_TRD_TR_LOANS", "Please select Ensure to inform IF trade status.")){
							return true;
						} else {
							return false;
						}
					}else if("ELCB_BL".equalsIgnoreCase(requestType) && "TXNC".equalsIgnoreCase(decision)){
						if("T3U3".equalsIgnoreCase(productCode) || "T3U6".equalsIgnoreCase(productCode) || "T3U7".equalsIgnoreCase(productCode) || "I3U3".equalsIgnoreCase(productCode)){
							return validateMandatoryFieldsPM(COURIER_COMPANY,"Please select Courier Company.")
									&& validateMandatoryFieldsPM(COURIER_AWB_NUMBER,"Please select Courier AWB Number.")
									&&validateMandatoryFieldsPM(DISCOUNT_ON_ACCEP,"Please select Discount Upon Acceptance.");	
						}else
							return validateMandatoryFieldsPM(COURIER_COMPANY,"Please select Courier Company.")
									&& validateMandatoryFieldsPM(COURIER_AWB_NUMBER,"Please select Courier AWB Number.");
					}else if("ELCB_AC".equalsIgnoreCase(requestType) && "TXNC".equalsIgnoreCase(decision)
							&& ("T3U3".equalsIgnoreCase(productCode) || "T3U6".equalsIgnoreCase(productCode) || "T3U7".equalsIgnoreCase(productCode) || "I3U3".equalsIgnoreCase(productCode))){ //PROTRDE_138
						return	validateMandatoryFieldsPM(DISCOUNT_ON_ACCEP,"Please select Discount Upon Acceptance.");
					}
					else
						return true;
				} else{
					return true;
				}

			}else{
				return true;
			}
		} catch (Exception e) {
			log.error("exception in mandatoryFieldsELCB_PM: ",e);
			return false;
		}
	}

	protected boolean mandatoryFieldsELC_PM(){
		try {
			if("ELC".equalsIgnoreCase(requestCategory)){
				if("PROCESSING MAKER".equalsIgnoreCase(sActivityName)){
					if("ELC_LCC".equalsIgnoreCase(requestType) 
							&& "false".equalsIgnoreCase(formObject.getValue("ELC_REIMB_CL_PER_PAY").toString())){
						sendMessageHashMap("ELC_REIMB_CL_PER_PAY", "Please check Reimbursement clauses status.");
						return false;
					}else if("ELC_LCC".equalsIgnoreCase(requestType) 
							&& "false".equalsIgnoreCase(formObject.getValue("ELC_ENS_COR_LIM_LINES").toString())){
						sendMessageHashMap("ELC_ENS_COR_LIM_LINES", "Please check correct limit lines status.");
						return false;
					}else if("ELC_LCC".equalsIgnoreCase(requestType) 
							&& "false".equalsIgnoreCase(formObject.getValue("ELC_CHRG_COL_PER_ICG_ICIEC").toString())){
						sendMessageHashMap("ELC_CHRG_COL_PER_ICG_ICIEC", "Please check Charge collected/claimed status.");
						return false;
					}else if("ELC_LCC".equalsIgnoreCase(requestType) 
							&& "false".equalsIgnoreCase(formObject.getValue("ELC_SWFT_IB_CHRG_COFM").toString())){
						sendMessageHashMap("ELC_SWFT_IB_CHRG_COFM", "Please check Swift to IB status.");
						return false;
					}else if("ELC_AOP".equalsIgnoreCase(requestType) 
							&& "false".equalsIgnoreCase(formObject.getValue("ELC_COL_CHRG_CUST").toString())){
						sendMessageHashMap("ELC_COL_CHRG_CUST", "Please check collect charges from customer status.");
						return false;
					}else if("ELC_AOP".equalsIgnoreCase(requestType) 
							&& "false".equalsIgnoreCase(formObject.getValue("ELC_PREP_ASS_LETTER").toString())){
						sendMessageHashMap("ELC_PREP_ASS_LETTER", "Please check assignment letter preparation status.");
						return false;
					}else if("ELC_AOP".equalsIgnoreCase(requestType) 
							&& "false".equalsIgnoreCase(formObject.getValue("ELC_SWFT_IB_AOP_DTLS").toString())){
						sendMessageHashMap("ELC_SWFT_IB_AOP_DTLS", "Please check Swift to IB with AOP details status.");
						return false;
					}else if("ELC_LCC".equalsIgnoreCase(requestType)){
						if (validateMandatoryFieldsPM("ELC_ENS_LMT_INT_OIL_LC", "Please check limit booked for interest portion status.")
								&& validateMandatoryFieldsPM("ELC_SILENT_CONF", "Please check if silent conf status.")
								&& validateMandatoryFieldsPM("ELC_CON_FCC_ICIEC_COV", "Please check T574 contract booked status.")
								&& validateMandatoryFieldsPM("ELC_UPDT_COMM_GL_TRACK", "Please check Update commission in respective GL fields status.")) {
							return true;
						} else {
							return false;
						}
					}
					else
						return true;
				} else{
					return true;
				}
			}else{
				return true;
			}
		} catch (Exception e) {
			log.error("exception in mandatoryFieldsELC_PM: ",e);
			return false;
		}
	}

	public boolean checkMandatoryOperationCode(){//RR
		if(("GRNT".equalsIgnoreCase(requestCategory))||"SBLC".equalsIgnoreCase(requestCategory)||("ILC".equalsIgnoreCase(requestCategory))
				||("ELC".equalsIgnoreCase(requestCategory)) ||("EC".equalsIgnoreCase(requestCategory))
				||("IC".equalsIgnoreCase(requestCategory))  ||("ELCB".equalsIgnoreCase(requestCategory))
				||("ILCB".equalsIgnoreCase(requestCategory))||("DBA".equalsIgnoreCase(requestCategory))
				||((("IFP".equalsIgnoreCase(requestCategory))&& ("LD".equalsIgnoreCase(requestType)))
						||(("IFA".equalsIgnoreCase(requestCategory))&& ("LD".equalsIgnoreCase(requestType)))
						||(("SCF".equalsIgnoreCase(requestCategory))&& ("PD".equalsIgnoreCase(requestType) || "PDD".equalsIgnoreCase(requestType))) //ATP - 204,205
						&& !formObject.getValue(PROCESSING_SYSTEM).toString().equalsIgnoreCase("T"))){ //By KISHAN TSLM
			return	validateMandatoryFieldsPM(OPERATION_CODE, "Please select Operation Code.");
		}
		return true;
	}

	public boolean checkMandatoyBillProductCode(){
		String branchCode=formObject.getValue(RELATIONSHIP_TYPE).toString();//code by kritika 
		if(!formObject.getValue(PROCESSING_SYSTEM).toString().equalsIgnoreCase("T")
				&&(("IFP".equalsIgnoreCase(requestCategory))&&("LD".equalsIgnoreCase(requestType)))
				&&("C".equalsIgnoreCase(branchCode)) ){
			return	validateMandatoryFieldsPM(BILL_PRODUCT_CODE, "Please select Bill Product Code.");
		}
		return true;
	}

	public boolean checkMandatoryLimitPartyType(String data){
		boolean flag=true;
		String operationCode= formObject.getValue(OPERATION_CODE).toString();
		if("ELC_LCA".equalsIgnoreCase(requestType)&& "ANC".equalsIgnoreCase(operationCode))
		{
			flag=validateMandatoryFieldsPM(LIMIT_PARTY_TYPE, "Please select Limit Party Type.");
			if(flag){
				if(!validatePartyExist(data)){
					sendMessageHashMap("", "Limit Party Type information is not available in Parties tab");
					return false;
				}else{
					return true;
				}
			}
		}else if("ELCB_BL".equalsIgnoreCase(requestType))
		{
			if(!("".equalsIgnoreCase(PM.lcLimitLine)||"null".equalsIgnoreCase(PM.lcLimitLine))){
				flag=validateMandatoryFieldsPM(LIMIT_PARTY_TYPE, "Please select Limit Party Type.");
				if(flag){
					if(!validatePartyExist(data)){ 
						sendMessageHashMap("", "Limit Party Type information is not available in Parties tab");
						return false;
					}else{
						return true;
					}
				}
			}
		}
		return flag;
	}

	private Boolean callContractWebService(){

		log.info("callContractWebService"+requestType);
		bCallELCFCUBSContractService=false;
		try {
			String  reqcatDesc=getDescriptionFromCode(REQUEST_CATEGORY,formObject.getValue(REQUEST_CATEGORY).toString());
			String  reqTypeDesc=getDescriptionFromCode(REQUEST_TYPE,formObject.getValue(REQUEST_TYPE).toString());

			if(("ELC_LCA".equalsIgnoreCase(requestType))
					&& ("SWIFT".equalsIgnoreCase(fetchProcessType()) 
							&& ("P".equalsIgnoreCase(formObject.getValue(SWIFT_PROCESSING_STATUS).toString())))) {
				String sContractStatus = formObject.getValue(CREATE_AMEND_STATUS_FCUBS).toString();
				String sCreatAmendValue = formObject.getValue(CREATE_AMEND_CNTRCT_FCUBS).toString();
				if("Y".equalsIgnoreCase(sCreatAmendValue) &&  
						("".equalsIgnoreCase(sContractStatus)
								|| "Fail".equalsIgnoreCase(sContractStatus)
								|| "Failure".equalsIgnoreCase(sContractStatus))){
					String messageType= formObject.getValue(SWIFT_MESSAGE_TYPE).toString();
					if("700".equalsIgnoreCase(messageType)||"710".equalsIgnoreCase(messageType)){
						insertFCUBSIntegrationCalls("modTradeContractStatus_Oper","Contract_Amendment_"+reqcatDesc+"_"+reqTypeDesc);
					}
					else{
						insertFCUBSIntegrationCalls("modTradeLCContract_Oper","Contract_Amendment_"+reqcatDesc+"_"+reqTypeDesc);
					}
					bCallELCFCUBSContractService = true;	
					callRequestType = "A";
				}

			}
			else if(("ELC_LCA".equalsIgnoreCase(requestType))
					||("ILC_NI".equalsIgnoreCase(requestType)) || ("ILC_UM".equalsIgnoreCase(requestType))
					||("NI".equalsIgnoreCase(requestType)) || ("GA".equalsIgnoreCase(requestType))
					||("SBLC_NI".equalsIgnoreCase(requestType)) || ("ELC_SLCA".equalsIgnoreCase(requestType))
					) {
				log.info("callContractWebService"+requestType);
				String sContractStatus = formObject.getValue(CREATE_AMEND_STATUS_FCUBS).toString();
				String sCreatAmendValue = formObject.getValue(CREATE_AMEND_CNTRCT_FCUBS).toString();
				log.info("sContractStatus="+sContractStatus+""+"sCreatAmendValue="+sCreatAmendValue);
				if("Y".equalsIgnoreCase(sCreatAmendValue) &&  
						("".equalsIgnoreCase(sContractStatus)
								|| "Fail".equalsIgnoreCase(sContractStatus)
								|| "Failure".equalsIgnoreCase(sContractStatus))){
					insertFCUBSIntegrationCalls("modTradeLCContract_Oper","Contract_Creation_"+reqcatDesc+"_"+reqTypeDesc);
					bCallELCFCUBSContractService = true;	
					callRequestType = "C";
				}

			}else if ("ILCB_BL".equalsIgnoreCase(requestType) || "DBA_BL".equalsIgnoreCase(requestType)
					|| "EC_BL".equalsIgnoreCase(requestType) || "EC_LDDI".equalsIgnoreCase(requestType)
					|| "ELCB_BL".equalsIgnoreCase(requestType) || "IC_BL".equalsIgnoreCase(requestType)
					|| ("IFP".equalsIgnoreCase(requestCategory) && "LD".equalsIgnoreCase(requestType)
							&&"F".equalsIgnoreCase(formObject.getValue(PROCESSING_SYSTEM).toString()))
					|| ("IFA".equalsIgnoreCase(requestCategory) && "LD".equalsIgnoreCase(requestType)
									&&"F".equalsIgnoreCase(formObject.getValue(PROCESSING_SYSTEM).toString()))){ 
				log.info("inside this 1234");
				String sContractStatus = formObject.getValue(CREATE_AMEND_STATUS_FCUBS).toString();
				String sCreatAmendValue = formObject.getValue(CREATE_AMEND_CNTRCT_FCUBS).toString();
				if("Y".equalsIgnoreCase(sCreatAmendValue) &&  
						("".equalsIgnoreCase(sContractStatus)
								|| "Fail".equalsIgnoreCase(sContractStatus)
								|| "Failure".equalsIgnoreCase(sContractStatus))){
					log.info("inside this 5678");
					insertFCUBSIntegrationCalls("modTradeBCContract_Oper","Contract_Creation_"+reqcatDesc+"_"+reqTypeDesc);
					bCallELCFCUBSContractService = true;	
					callRequestType = "C";
				}
			}else if(("ELC".equalsIgnoreCase(requestCategory) && "ELC_LCAA".equalsIgnoreCase(requestType))
					|| ("ELC".equalsIgnoreCase(requestCategory) && "ELC_LCC".equalsIgnoreCase(requestType))
					|| ("ILC".equalsIgnoreCase(requestCategory) && "ILC_AM".equalsIgnoreCase(requestType))
					||("GRNT".equalsIgnoreCase(requestCategory) && "AM".equalsIgnoreCase(requestType))
					||("GRNT".equalsIgnoreCase(requestCategory) && "GAA".equalsIgnoreCase(requestType))
					||("SBLC".equalsIgnoreCase(requestCategory) && "SBLC_AM".equalsIgnoreCase(requestType))//RR
					||("ELC".equalsIgnoreCase(requestCategory) && "ELC_SLCAA".equalsIgnoreCase(requestType)))//RR
			{

				String sContractStatus = formObject.getValue(CREATE_AMEND_STATUS_FCUBS).toString();
				String sCreatAmendValue = formObject.getValue(CREATE_AMEND_CNTRCT_FCUBS).toString();
				if("Y".equalsIgnoreCase(sCreatAmendValue) &&  
						("".equalsIgnoreCase(sContractStatus)
								|| "Fail".equalsIgnoreCase(sContractStatus)
								|| "Failure".equalsIgnoreCase(sContractStatus))){
					insertFCUBSIntegrationCalls("modTradeLCContract_Oper","Contract_Amendment_"+reqcatDesc+"_"+reqTypeDesc);
					bCallELCFCUBSContractService = true;	
					callRequestType = "A";
				}
			}else if(("ILCB".equalsIgnoreCase(requestCategory) && "ILCB_AC".equalsIgnoreCase(requestType))||
					("ELCB".equalsIgnoreCase(requestCategory) && "ELCB_AC".equalsIgnoreCase(requestType))||
					("IC".equalsIgnoreCase(requestCategory) && "IC_AC".equalsIgnoreCase(requestType))||
					("EC".equalsIgnoreCase(requestCategory) && "EC_AC".equalsIgnoreCase(requestType))||
					("ELCB".equalsIgnoreCase(requestCategory) && "ELCB_AM".equalsIgnoreCase(requestType))||
					("ELCB".equalsIgnoreCase(requestCategory) && "ELCB_DISC".equalsIgnoreCase(requestType))||
					("EC".equalsIgnoreCase(requestCategory) && "EC_AM".equalsIgnoreCase(requestType))||
					("EC".equalsIgnoreCase(requestCategory) && "EC_DISC".equalsIgnoreCase(requestType)))
			{ 
				String sContractStatus = formObject.getValue(CREATE_AMEND_STATUS_FCUBS).toString();
				String sCreatAmendValue = formObject.getValue(CREATE_AMEND_CNTRCT_FCUBS).toString();
				if("Y".equalsIgnoreCase(sCreatAmendValue) &&  
						("".equalsIgnoreCase(sContractStatus)
								|| "Fail".equalsIgnoreCase(sContractStatus)
								|| "Failure".equalsIgnoreCase(sContractStatus))){
					insertFCUBSIntegrationCalls("modTradeBCContract_Oper","Contract_Amendment_"+reqcatDesc+"_"+reqTypeDesc);
					bCallELCFCUBSContractService = true;	
					callRequestType = "A";
				}
			}
		} catch (Exception e) {
			log.error("exception in callContractWebService: ",e);
		}
		return bCallELCFCUBSContractService;
	}

	private Boolean callContractWebService2(){
		try {
			log.info("bCallELCFCUBSContractService="+bCallELCFCUBSContractService);
			log.info("CREATE_AMEND_STATUS_FCUBS="+formObject.getValue(CREATE_AMEND_STATUS_FCUBS).toString());
			if(bCallELCFCUBSContractService){
				{ 
					log.info("[[Create Contract successful]]");

					String sResponseQuery = "SELECT CREATE_AMEND_STATUS_FCUBS,TRANSACTION_REFERENCE FROM EXT_TFO WHERE wi_name='" + this.sWorkitemID + "'";
					log.info("sBranchQuery " + sResponseQuery);
					List<List<String>> recordList = formObject.getDataFromDB(sResponseQuery);
					if (recordList != null && !recordList.isEmpty() && !recordList.get(0).isEmpty() 
							&& recordList.get(0).get(0).length() > 0) {
						if(recordList.get(0).get(0).contains("Success")){
							formObject.setValue(CREATE_AMEND_STATUS_FCUBS, "Success");
							formObject.setValue(TRANSACTION_REFERENCE, recordList.get(0).get(1));
							formObject.setValue(FCUBS_CON_NO, recordList.get(0).get(1));
							if ("IFP".equalsIgnoreCase(requestCategory) && ("LD".equalsIgnoreCase(requestType))) {
								this.formObject.setValue(BILL_LN_REFRNCE, recordList.get(0).get(1));
							}
							return true;
						}
						else
						{
							formObject.setValue(CREATE_AMEND_STATUS_FCUBS, "Failure");
							return false;
						}
					}
					return false;
				}
			}else{
				return true;
			}			
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		return true;
	}
	public boolean validatePartyExist(String data){
		String partyType=this.formObject.getValue(LIMIT_PARTY_TYPE).toString();
		log.info("LIMIT_PARTY_TYPE="+partyType);
		int length = Integer.parseInt(data.split("###")[0]);
		for(int i=0;i<length;i++){
			log.info("Party Type "+formObject.getTableCellValue(LISTVIEW_PARTY, i, 0));
			if(partyType.equalsIgnoreCase(formObject.getTableCellValue(LISTVIEW_PARTY, i, 0))){
				return true;
			}
		}
		return false;
	}

	private boolean validateCPDTab(int cpdListCount,String buttonId){
		try {
			log.info("Inside validateCPDTab");
			if(("PP_MAKER".equalsIgnoreCase(sActivityName)
					||"PROCESSING MAKER".equalsIgnoreCase(sActivityName))
					&& ("IFS".equalsIgnoreCase(requestCategory) 
							|| "IFA".equalsIgnoreCase(requestCategory)//Code by Rakshita
							|| "IFP".equalsIgnoreCase(requestCategory))
							&& ("LD".equalsIgnoreCase(requestType) 
									|| "AM".equalsIgnoreCase(requestType)
									)){
				log.info("List ocunt for couter party detail tab  : "+cpdListCount);
				if(!(0< cpdListCount)){
					sendMessageHashMap(buttonId, "Please add counter party details in the grid at Counter party details tab");

					return false;
				}
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		return true;
	}

	private boolean validateCompTab(){
		try {
			log.info("*******************Inside validateCompTab********************");
			enableControls(LIST_VIEW_BUTTONS3);
			enableControls(BUTTON_SUBMIT);

			if(!"STL".equalsIgnoreCase(requestType)){
				if(compCheckTab()){
					log.info("compCheckTab validation is successful");
					if(validateOptionBtn()){
						log.info("validateOptionBtn validation is successful");
						populateComplianceTab();
						log.info("populateComplianceTab is successful");
						if ("GRNT".equalsIgnoreCase(requestCategory)||"SBLC".equalsIgnoreCase(requestCategory)){//RR
							enableControls(LIST_VIEW_BUTTONS4);
							disableControls(BUTTON_NEXT);
						}else if("ELC".equalsIgnoreCase(requestCategory)&&("ELC_SLCA".equalsIgnoreCase(requestType)||"ELC_SLCAA".equalsIgnoreCase(requestType)
								||"ELC_SER".equalsIgnoreCase(requestType)||"ELC_SCL".equalsIgnoreCase(requestType))){//RR
							enableControls(LIST_VIEW_BUTTONS4);
							disableControls(BUTTON_NEXT);
						}else if ("IFS".equalsIgnoreCase(requestCategory) ||"IFP".equalsIgnoreCase(requestCategory)
								|| "IFA".equalsIgnoreCase(requestCategory) || "SCF".equalsIgnoreCase(requestCategory)  //ATP - 204,205
								||"IC".equalsIgnoreCase(requestCategory) ||"ILC".equalsIgnoreCase(requestCategory)
								||"ILCB".equalsIgnoreCase(requestCategory) ||"EC".equalsIgnoreCase(requestCategory)
								||"ELC".equalsIgnoreCase(requestCategory) ||"ELCB".equalsIgnoreCase(requestCategory)
								||"TL".equalsIgnoreCase(requestCategory)||"DBA".equalsIgnoreCase(requestCategory)){
							enableControls(LIST_VIEW_BUTTONS3);
							disableControls(BUTTON_SUBMIT);
						}
						log.info("Returning true");
						return true;
					}else{
						log.info("validateOptionBtn validation failed");
						if ("GRNT".equalsIgnoreCase(requestCategory)||"SBLC".equalsIgnoreCase(requestCategory)){//RR
							enableControls(LIST_VIEW_BUTTONS4);
							disableControls(BUTTON_NEXT);
						}else if ("ELC".equalsIgnoreCase(requestCategory)&&("ELC_SLCA".equalsIgnoreCase(requestType)||"ELC_SLCAA".equalsIgnoreCase(requestType)
								||"ELC_SER".equalsIgnoreCase(requestType)||"ELC_SCL".equalsIgnoreCase(requestType))){//RR
							enableControls(LIST_VIEW_BUTTONS4);
							disableControls(BUTTON_NEXT);
						}else if ("IFS".equalsIgnoreCase(requestCategory) ||"IFP".equalsIgnoreCase(requestCategory)
								|| "IFA".equalsIgnoreCase(requestCategory) || "SCF".equalsIgnoreCase(requestCategory)  //ATP - 204,205
								||"IC".equalsIgnoreCase(requestCategory) ||"ILC".equalsIgnoreCase(requestCategory)
								||"ILCB".equalsIgnoreCase(requestCategory) ||"EC".equalsIgnoreCase(requestCategory)
								||"ELC".equalsIgnoreCase(requestCategory) ||"ELCB".equalsIgnoreCase(requestCategory)
								||"TL".equalsIgnoreCase(requestCategory) ||"DBA".equalsIgnoreCase(requestCategory)){
							enableControls(LIST_VIEW_BUTTONS3);
							disableControls(BUTTON_SUBMIT);
						}
						return false;
					}
				}
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		if("GRNT".equalsIgnoreCase(requestCategory)||"SBLC".equalsIgnoreCase(requestCategory)){//RR
			enableControls(LIST_VIEW_BUTTONS4);
			disableControls(BUTTON_NEXT);
		}else if("ELC".equalsIgnoreCase(requestCategory)&&("ELC_SLCA".equalsIgnoreCase(requestType)||"ELC_SLCAA".equalsIgnoreCase(requestType)
				||"ELC_SER".equalsIgnoreCase(requestType)||"ELC_SCL".equalsIgnoreCase(requestType))){//RR
			enableControls(LIST_VIEW_BUTTONS4);
			disableControls(BUTTON_NEXT);
		}else if("IFS".equalsIgnoreCase(requestCategory) ||"IFP".equalsIgnoreCase(requestCategory)
				|| "IFA".equalsIgnoreCase(requestCategory) || "SCF".equalsIgnoreCase(requestCategory)  //ATP - 204,205
				||"IC".equalsIgnoreCase(requestCategory) ||"ILC".equalsIgnoreCase(requestCategory)
				||"ILCB".equalsIgnoreCase(requestCategory) ||"EC".equalsIgnoreCase(requestCategory)
				||"ELC".equalsIgnoreCase(requestCategory) ||"ELCB".equalsIgnoreCase(requestCategory)
				||"TL".equalsIgnoreCase(requestCategory)||"DBA".equalsIgnoreCase(requestCategory)){
			enableControls(LIST_VIEW_BUTTONS3);
			disableControls(BUTTON_SUBMIT);
		}
		return true;
	}

	private boolean compCheckTab(){
		try {
			log.info("*******************Inside compCheckTab******************");
			String reqType=  formObject.getValue(REQUEST_TYPE).toString();
			String sRevisedDoc = formObject.getValue(BILL_RVSD_DOC_AVL).toString();
			log.info("comp Check Tab" +reqType);
			log.info("comp Check Tab" +requestCategory);
			log.info("comp Check Tab" +sRevisedDoc);
			try {
				if("GRNT".equalsIgnoreCase(requestCategory) ){
					if("NI".equalsIgnoreCase(reqType)
							||"AM".equalsIgnoreCase(reqType)
							|| "ER".equalsIgnoreCase(reqType)
							|| "CL".equalsIgnoreCase(reqType)
							|| "GA".equalsIgnoreCase(reqType)
							|| "GAA".equalsIgnoreCase(reqType)){
						enableControls(COMP_CHECK_CONTROLS);												
						return true; 		
					}else{
						disableControls(COMP_CHECK_CONTROLS);	
						return false;
					}
				}else if("SBLC".equalsIgnoreCase(requestCategory)){//RR
					if("SBLC_NI".equalsIgnoreCase(reqType)||"SBLC_AM".equalsIgnoreCase(reqType)|| "SBLC_ER".equalsIgnoreCase(reqType)
							|| "SBLC_CR".equalsIgnoreCase(reqType)||"SBLC_CS".equalsIgnoreCase(reqType)){
						enableControls(COMP_CHECK_CONTROLS);												
						return true;
					}else{
						disableControls(COMP_CHECK_CONTROLS);	
						return false;
					}
				}else if("ELC".equalsIgnoreCase(requestCategory)&&("ELC_SLCA".equalsIgnoreCase(requestType)||"ELC_SLCAA".equalsIgnoreCase(requestType)
						||"ELC_SER".equalsIgnoreCase(requestType)||"ELC_SCL".equalsIgnoreCase(requestType))){//RR
					enableControls(COMP_CHECK_CONTROLS);												
					return true;

				}else if("TL".equalsIgnoreCase(requestCategory)&& ("TL_LD".equalsIgnoreCase(requestType) || "TL_AM".equalsIgnoreCase(requestType)) && ("BC".equalsIgnoreCase(  formObject.getValue(IFS_LOAN_GRP).toString()) 
						|| "MRPA".equalsIgnoreCase(formObject.getValue(IFS_LOAN_GRP).toString()))){//RR for TL_LD
					log.info("***ENABLING COMP CHECK CONTROLS FOR TL***");
					enableControls(COMP_CHECK_CONTROLS);
					enableControls(COMP_IMB_CHK);
					return true;

				}else if("IFS".equalsIgnoreCase(requestCategory)  || "IFA".equalsIgnoreCase(requestCategory)
						|| "IFP".equalsIgnoreCase(requestCategory)|| "SCF".equalsIgnoreCase(requestCategory)){
					log.info("INSIDE SCF");
					if("LD".equalsIgnoreCase(reqType) || "PD".equalsIgnoreCase(reqType) || "AM".equalsIgnoreCase(reqType) 
						|| "PDD".equalsIgnoreCase(reqType) || ("IFA".equalsIgnoreCase(requestCategory)&&"IFA_CTP".equalsIgnoreCase(reqType))){	//EDITED BY SHIVANSHU FOR COMP REF //ATP - 204,205
						//||"AM".equalsIgnoreCase(reqType)){							
						log.info("INSIDE PD");
						enableControls(COMP_CHECK_CONTROLS);
						enableControls(COMP_IMB_CHK);
						return true; 		
					}else{
						log.info("OUTSIDE ELSE PD SCF");
						disableControls(COMP_CHECK_CONTROLS);	
						disableControls(COMP_IMB_CHK);
						return false;
					}
				}else if(("IC".equalsIgnoreCase(requestCategory) && ("IC_BL".equalsIgnoreCase(reqType)||"IC_BS".equalsIgnoreCase(reqType)))
						|| ("EC".equalsIgnoreCase(requestCategory) && ("EC_BL".equalsIgnoreCase(reqType) || "EC_BS".equalsIgnoreCase(reqType)))
						|| ("ILCB".equalsIgnoreCase(requestCategory) && ("ILCB_BL".equalsIgnoreCase(reqType) || "ILCB_BS".equalsIgnoreCase(reqType)))
						|| ("ELCB".equalsIgnoreCase(requestCategory) && ("ELCB_BL".equalsIgnoreCase(reqType) || "ELCB_BS".equalsIgnoreCase(reqType)))
						){						
						log.info("INSIDE IC EC ILCB ELCB");
						enableControls(COMP_CHECK_CONTROLS);
						enableControls(COMP_IMB_CHK);
						return true; 		
				}else if("ILC".equalsIgnoreCase(requestCategory)  || "ELC".equalsIgnoreCase(requestCategory)
						|| "IC".equalsIgnoreCase(requestCategory) || "DBA".equalsIgnoreCase(requestCategory)
						|| "EC".equalsIgnoreCase(requestCategory) || "ELCB".equalsIgnoreCase(requestCategory)
						|| "ILCB".equalsIgnoreCase(requestCategory)){
					if("ILC_NI".equalsIgnoreCase(reqType) || "ILC_UM".equalsIgnoreCase(reqType)
							||"ILC_AM".equalsIgnoreCase(reqType)  ||"ELC_LCA".equalsIgnoreCase(reqType)
							||"ELC_LCAA".equalsIgnoreCase(reqType)||"ELC_AOP".equalsIgnoreCase(reqType)
							||"ELC_LCT".equalsIgnoreCase(reqType) ||"ELC_RSD".equalsIgnoreCase(reqType)
							||"DBA_BL".equalsIgnoreCase(reqType)  ||"EC_LDDI".equalsIgnoreCase(reqType)
							||"EC_DISC".equalsIgnoreCase(reqType) ||"ELCB_DISC".equalsIgnoreCase(reqType)){ //ATP-453 REYAZ 26-04-2024

						enableControls(COMP_CHECK_CONTROLS);	
						enableControls(COMP_IMB_CHK);
						return true; 		
					}else{
						if(("IC_AM".equalsIgnoreCase(reqType) && "1".equalsIgnoreCase(sRevisedDoc))
								||("DBA_AM".equalsIgnoreCase(reqType) && "1".equalsIgnoreCase(sRevisedDoc))
								||("EC_AM".equalsIgnoreCase(reqType) && "1".equalsIgnoreCase(sRevisedDoc))
								||("ILCB_AM".equalsIgnoreCase(reqType) && "1".equalsIgnoreCase(sRevisedDoc))
								||("ELCB_AM".equalsIgnoreCase(reqType) && "1".equalsIgnoreCase(sRevisedDoc))){	
							enableControls(COMP_CHECK_CONTROLS);							
							enableControls(COMP_IMB_CHK);
							return true; 		
						}else{							
							disableControls(COMP_CHECK_CONTROLS);
							disableControls(COMP_IMB_CHK);
							return false;
						}
					}
				}else if("SG_NIC".equalsIgnoreCase(reqType) ||"SG_NILC".equalsIgnoreCase(reqType)){ //Shipping_gtee_60
					log.info("in SG_NIC or SG_NILC");
					enableControls(COMP_CHECK_CONTROLS);
					return true;
				}else {
					log.info("disabling field in compliance screening tab..");
					disableControls(COMP_CHECK_CONTROLS);	
					disableControls(COMP_IMB_CHK);
					return false;
				}
			} catch (Exception e) {
				log.error("Exception: ",e);
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		return false;
	}

	private boolean validateOptionBtn() {
		try {
			log.info("*******************Inside validateOptionBtn******************");
			log.info("Start adding at repeter New ");			
			String strTabName = tabDescIdMap.get("CompScr");
			String strRefTo = tabDescIdMap.get("CompRefTo");
			if(chkCompValidateTab(COMP_CHK_DONE,"Please select compliance check done from compliance screening tab.")
					&& chkCompValidateTab(COMP_POSITIVE_MATCH,"Please select any positive match from compliance screening tab.") 
					&& chkCompValidateTab(COMP_IMB_CHK,"Please select IMB check performed from compliance screening tab.")
					&& chkCompValidateTab(LLI_CHK_OK,"Please select LLI Check is OK from compliance screening tab.")){
				if("Y".equalsIgnoreCase(formObject.getValue(COMP_REF).toString())
						|| "N".equalsIgnoreCase(formObject.getValue(LLI_CHK_OK).toString())){
					setRefToComp("Y");    				
					if(chkCompValidateTab(COMP_EXP_REMARKS,"Please enter exception to compliance from compliance screening tab.")){
						try {
							log.info("validateOptionBtn For Comp Save data ");   
							log.info("grid kyu delete hua bhai 2460");
							formObject.clearTable(LISTVIEW_FINAL_DECISION);
							loadExcpSaveDataIntoRepeater(strTabName,strRefTo,"Yes",formObject.getValue(COMP_EXP_REMARKS).toString());
						} catch (Exception e) {
							log.error("Exception: ",e);
						}
						return true;
					}else{
						return false;
					}
				}else if(!"Y".equalsIgnoreCase(formObject.getValue(COMP_POSITIVE_MATCH).toString()) && !"N".equalsIgnoreCase(formObject.getValue(LLI_CHK_OK).toString())){
					try {
						setRefToComp("N");  
						log.info("validateOptionBtn For Comp remove data ");
						removeDataFromRepeater(strTabName,strRefTo);
					} catch (Exception e) {
						log.error("Exception: ",e);
					}
				}
			}else{    		
				return false;
			}    		
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		return true;
	}

	private boolean chkCompValidateTab(String control, String msg){
		log.info("*********************In chkCompValidateTab*************** control="+control + "************* message+" +msg+"***********");
		try {
			if(COMP_CHK_DONE.equalsIgnoreCase(control) && !validateMandatoryFieldsPM(COMP_CHK_DONE,msg)){
				return false ;
			}else if(COMP_POSITIVE_MATCH.equalsIgnoreCase(control) && !validateMandatoryFieldsPM(COMP_POSITIVE_MATCH,msg)){
				return false ;
			}else if(COMP_EXP_REMARKS.equalsIgnoreCase(control) && !validateMandatoryFieldsPM(COMP_EXP_REMARKS,msg)){
				return false ;
			}else if( (!("GRNT".equalsIgnoreCase(requestCategory)||"SG".equalsIgnoreCase(requestCategory)|| "SBLC".equalsIgnoreCase(requestCategory)
					||("ELC".equalsIgnoreCase(requestCategory)&&("ELC_SLCA".equalsIgnoreCase(requestType) || "ELC_SLCAA".equalsIgnoreCase(requestType)
							||"ELC_SER".equalsIgnoreCase(requestType)||"ELC_SCL".equalsIgnoreCase(requestType)))))
					&& compCheckTab() && COMP_IMB_CHK.equalsIgnoreCase(control)  //SHipping_Gtee_60
					&& !validateMandatoryFieldsPM(COMP_IMB_CHK,msg)){
				log.info("IMB Checked");
				return false ;
			}else if(("IFS".equalsIgnoreCase(requestCategory)
					|| "IFA".equalsIgnoreCase(requestCategory)
					|| "IFP".equalsIgnoreCase(requestCategory))
					&& ("Y".equalsIgnoreCase(formObject.getValue(TR_REFER_TREASURY).toString()))
					&& TR_EXCEPTIONS.equalsIgnoreCase(control) 
					&& !validateMandatoryFieldsPM(TR_EXCEPTIONS,msg)){

				return false ;
			}else if(LLI_CHK_OK.equalsIgnoreCase(control) && !validateMandatoryFieldsPM(LLI_CHK_OK,msg)){
				return false ;
			}else{
				return true;
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		return true;
	}
	/*private void loadExcpSaveDataIntoRepeater(String tabName,String referTo,String decision,String excpRemarks){
		String email="";
		String additionalMailId="";
		String repeaterKey = "";

		email = getReferralmailId(referTo);
		log.info("****************Inside loadExcpSaveDataIntoRepeater*********** tabName  >>> "+tabName + "*********** referTo " +referTo + " *********** decision " +decision +"|8********* excpRemarks " +excpRemarks + "********* email  " +email );        
		try { 
			log.info("Before Adding Data into repeater");
			repeaterKey = tabName +referTo; 
			log.info("repater key "+repeaterKey);
			if(hmapAdditionalMail.containsKey(repeaterKey))
				additionalMailId = hmapAdditionalMail.get(repeaterKey);
			log.info("additionalMailId="+additionalMailId);
			String values = tabName+"##"+referTo+"##"+decision+"##"+excpRemarks+"##"+email+"##"+additionalMailId;
			//+"##"+sWorkitemID;
			LoadListViewWithHardCodeValues(values, LISTVIEW_FINAL_DECISION_COLUMNS, LISTVIEW_FINAL_DECISION);
			setHashMap();
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}*/

	private String removeDataFromRepeater(String moduleName,String refTo){
		String email="";
		try {
			ArrayList list=new ArrayList();
			//int arrayOfIndex[] = new int[1];

			log.info("grid kyu delete hua bhai 2568"+list.size()); //pta nahi bhai
			//	formObject.clearTable(LISTVIEW_FINAL_DECISION);
			JSONArray jsonArray =  formObject.getDataFromGrid(LISTVIEW_FINAL_DECISION);
			for(int count = 0 ; count < jsonArray.size() ; count++)
			{	
				String module = formObject.getTableCellValue(LISTVIEW_FINAL_DECISION, count,0);
				String referTo = formObject.getTableCellValue(LISTVIEW_FINAL_DECISION, count,1);
				log.info("moduleName : "+moduleName);
				log.info("module : "+module);
				log.info("referTo : "+referTo);
				log.info("refTo : "+refTo);
				if(moduleName.equalsIgnoreCase(module) && referTo.equalsIgnoreCase(refTo))
				{
					list.add(count);
					//	arrayOfIndex[0] = count;
					log.info("count : "+count);
					break;
				}
			} 
			log.info("grid kyu delete hua bhai 2584"+list.size());
			if(list.size()>0){
				int arrayOfIndex[] = new int[list.size()];
                for(int i=0;i<list.size();i++){
					arrayOfIndex[i] =(int) list.get(i);
				}
               formObject.deleteRowsFromGrid(LISTVIEW_FINAL_DECISION, arrayOfIndex);
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}

		return email;
	}
	private boolean checkComplianceValidation(){
		log.info("****************Inside checkComplianceValidation**********************");
		try { 
			if(formObject.getValue("POS_MATCHES_OPTION_YES").toString().equalsIgnoreCase("true")){

				if(validateMandatoryFieldsPM("COMP_INST_BNK_NAME_CNTRY", "Please enter instructing Bank name.")
						&& validateMandatoryFieldsPM("COMP_ADVS_BNK_NAME_CNTRY", "Please enter Advising Bank name.")
						&& validateMandatoryFieldsPM("COMP_CUST_APP_NAME_CNTRY", "Please enter Applicant name.")
						&& validateMandatoryFieldsPM("COMP_BENFC_NAME_CNTRY", "Please enter Beneficiary name.")
						&& validateMandatoryFieldsPM("COMP_PRPS_OF_TXN", "Please enter purpose of transaction.")
						&& validateMandatoryFieldsPM("COMP_ORGN_OF_GDS", "Please enter origin of goods.")
						&& validateMandatoryFieldsPM("COMP_PORT_OF_LDING", "Please enter port of landing.")
						&& validateMandatoryFieldsPM("COMP_PORT_OF_DSCHRG", "Please enter port of discharge.")
						&& validateMandatoryFieldsPM("COMP_ON_BRD_DATE", "Please enter on board date.")
						&& validateMandatoryFieldsPM("COMP_VESSEL_NAME", "Please enter vessel name.")
						&& validateMandatoryFieldsPM("COMP_END_USE_OF_GDS", "Please enter end use of goods.")
						&& validateMandatoryFieldsPM("COMP_NAME_ADDR_RUS_UKR", "Please enter name of Russian/Ukranian entities.")
						&& validateMandatoryFieldsPM("COMP_IMB_PORT", "Please enter IBM Report details.")
						) {
					log.info("Compliance info validation done");
					return true;
				}
				else {
					return false;
				}
			}
			else {
				return true;
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
			return false;
		}
	}
	public boolean validateLLIFrame() {
		String sResult = null;
		log.info("***************Inside validateLLIFrame********************");
		if((formObject.getValue(LLI_CHK_OK).toString().equalsIgnoreCase("Y"))) { 
			if(getGridCount(LISTVIEW_LLI) > 0) {
				enableControls("Btn_Add_LLI,Btn_Del_LLI");
				if(formObject.getValue(LLI_CHK_OK).toString().equalsIgnoreCase("Y")){
					enableControls(BUTTON_GENERATE_LLI_PDF);

					sResult = docCountLLI();
					log.info("### sResult-->no of LLI doc attached already" + sResult);
					if(sResult!=null && Integer.parseInt(sResult) == 0) {
						sendMessageHashMap("", "Please generate the PDF with LLI details");
						return false;
					}
				}else {
					disableControls(BUTTON_GENERATE_LLI_PDF);
				}
				return (((formObject.getValue(LLI_CHK_OK).toString()).equalsIgnoreCase("Y") )?validateGrid(LISTVIEW_LLI, "Please provide vessel details."):true);
			}else {

				disableControls("Btn_Basic_Vessel_Details,Btn_Ownrship_Details,Btn_Movmnt_Details");
				sendMessageHashMap(sFieldName, "Please provide vessel details");
				return false;
			}
		}else {
			return true;
		}


	}
	private String docCountLLI() {
		String lliDocCount = "";
		try {
			String sQuery = " select count(1) from pdbdocument where documentindex in ( select documentindex from pdbdocumentcontent where parentfolderindex = ( select folderindex from pdbfolder where name = '"+sWorkitemID+"')) and name  ='LLI'";
			log.info(sQuery);
			List<List<String>> tmpList = formObject.getDataFromDB(sQuery);
			if(!tmpList.isEmpty()) {
				log.info("[tmpList]=>"+tmpList);
				lliDocCount =  tmpList.get(0).get(0);
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		return lliDocCount;
	}

	private boolean validateGrid(String sGridName, String alertMsg) {
		try {
			log.info("***************Inside validateGrid***"+sGridName+"*****alert="+alertMsg+"**********");
			if(getGridCount(sGridName) > 0) {
				log.info("Size greater than 0");
				return true;
			}else {  
				sendMessageHashMap(sGridName, alertMsg);
				return false; 
			}

		} catch (Exception e) {
			log.error("Exception: ",e);
			return false;
		}
	}
	public boolean treasuryTabValidation(){
		log.info("Inside treasuryTabValidation:"+formObject.getValue(TRSD_FLAG).toString());
		
		try {
			if(formObject.getValue(TRSD_FLAG).toString().equalsIgnoreCase("Y")){
				log.info("Inside if treasuryTabValidation TRSD_FLAG Check ");
				return true;
			} else if(handlingTreasuryTab()){
				return validateTRTab();
			} else{
				enableControls(LIST_VIEW_BUTTONS4);
				disableControls(BUTTON_NEXT);
				return true;
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		return true;
	}

	private boolean validateTrTabAndRepeaterData(){
		try {
			String strTabName,strRefTo;
			strTabName = tabDescIdMap.get("Trsry");
			strRefTo = tabDescIdMap.get("Trsr");
			if(!"Y".equalsIgnoreCase(formObject.getValue(COMP_POSITIVE_MATCH).toString())){				
				if("Y".equalsIgnoreCase(formObject.getValue(TR_REFER_TREASURY).toString())){
					if(chkCompValidateTab(TR_EXCEPTIONS,"Please enter exception remarks for treasury.")){
						log.info("grid kyu delete hua bhai 2710");
						formObject.clearTable(LISTVIEW_FINAL_DECISION);
						treasuryTabException();
						return true;
					}else{
						return false;
					}
				}else{
					strTabName =  tabDescIdMap.get("Trsry");
					strRefTo =  tabDescIdMap.get("Trsr");
					removeDataFromRepeater(strTabName,strRefTo);

				}
			}
		}catch (Exception e) {
			log.error("Exception: ",e);
			return false;
		}
		return true;
	}	
	private void treasuryTabException(){
		try {
			log.info("Adding data from treasuryTabException into final decision tab");
			String strTabName =  tabDescIdMap.get("Trsry");
			String strRefTo =  tabDescIdMap.get("Trsr");
			loadExcpSaveDataIntoRepeater(strTabName,strRefTo,"Yes",formObject.getValue(TR_EXCEPTIONS).toString());

		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	private boolean validateTRTab(){
		try {
			log.info("Validating TR Tab");

			setRateRequested();
			if(!validateMandatoryFieldsPM("TR_CUST_NAME", "Customer name cannot be blank.")){
				return false;
			}
			else if(!validateMandatoryFieldsPM("TR_CID", "CID cannot be blank.")){
				return false;
			}
			else if(!validateMandatoryFieldsPM(TR_SELL_CUR, "Sell Currency cannot be blank.")){
				return false;
			}
			else if(!validateMandatoryFieldsPM(TR_BUY_CUR, "Buy Currency cannot be blank.")){
				return false;
			}
			else if(!validateMandatoryFieldsPM("TR_RATE_REQ", "Rate Requested cannot be blank.")){
				return false;
			}
			else if(!validateMandatoryFieldsPM(TR_REFER_TREASURY, "Please select refer to treasury from treasury rate tab")){
				return false ;
			}else if ("Y".equalsIgnoreCase(formObject.getValue(TR_REFER_TREASURY).toString()) && !validateMandatoryFieldsPM(TR_EXCEPTIONS, "Please enter exception at treasury rate tab")){
				return false ;
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		enableControls(LIST_VIEW_BUTTONS4);
		disableControls(BUTTON_NEXT);
		return true;
	}
	private void setRateRequested() {
		String buyCurr = formObject.getValue(TR_BUY_CUR).toString();
		String sellCurr = formObject.getValue(TR_SELL_CUR).toString();
		if((null == buyCurr || "".equals(buyCurr) || "0".equalsIgnoreCase(buyCurr))){
			buyCurr = "";
		}
		if((null == sellCurr || "".equals(sellCurr) || "0".equalsIgnoreCase(sellCurr))){
			sellCurr = "";
		}
		if(!"".equals(buyCurr) || !"".equals(sellCurr)){
			formObject.setValue("TR_RATE_REQ",buyCurr +"-"+sellCurr);
		}
	}
	private boolean exchangeRateValidation(){
		if(!validateTextFieldForNumberValue(formObject.getValue(UI_EXCHANGE_RATE).toString(), 20, 10)){
			formObject.setValue(UI_EXCHANGE_RATE,"");
			sendMessageHashMap(UI_EXCHANGE_RATE, "Please enter a valid number.");
			return false;
		}else{
			return true;
		}
	}
	private boolean validateTextFieldForNumberValue(String val, int preDot, int postDot)
	{
		try
		{
			if(!val.trim().isEmpty()){
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
				} else if(val.length() > preDot) {
					return false;
				}else if(val.indexOf(".")==0 && (val.substring(val.indexOf(".") + 1,val.length())).length() > postDot){
					return false;
				}
			}

		}
		catch (Exception e) {
			log.error("Exception: ",e);
			return false;
		}

		return true;
	}

	private boolean checkMandatoryPD(){

		try {
			ArrayList al=new ArrayList();
			String thirdPartyValue=formObject.getValue(TRN_THIRD_PARTY).toString();
			String productType=formObject.getValue(PRODUCT_TYPE).toString();
			String soucingChannel=formObject.getValue(SOURCE_CHANNEL).toString();

			log.info("thirdPartyValue ="+thirdPartyValue);
			log.info("requestCategory ="+requestCategory);
			log.info("modeOfGtee ="+modeOfGurantee);	//RR(for SBLC_MSM)
			if(("GRNT_MSM".equalsIgnoreCase(requestType))||("SBLC_MSM".equalsIgnoreCase(requestType))||("EC_MSM".equalsIgnoreCase(requestType))||("IC_MSM".equalsIgnoreCase(requestType))||("ELC_MSM".equalsIgnoreCase(requestType))    
					||("ILC_MSM".equalsIgnoreCase(requestType))||("ELCB_MSM".equalsIgnoreCase(requestType))||("ILCB_MSM".equalsIgnoreCase(requestType))||("IFP_MSM".equalsIgnoreCase(requestType))
					||("IFS_MSM".equalsIgnoreCase(requestType))||("TL_MSM".equalsIgnoreCase(requestType))||("ELC_PA".equalsIgnoreCase(requestType))||("ELCB_AOR".equalsIgnoreCase(requestType))
					||("ELCB_AOR".equalsIgnoreCase(requestType))||("ILCB_AOD".equalsIgnoreCase(requestType))||("ILCB_AOP".equalsIgnoreCase(requestType))	) 
			{
				return true;
			}
			else if(("P".equalsIgnoreCase(soucingChannel))||("EC_P".equalsIgnoreCase(soucingChannel))||("IC_P".equalsIgnoreCase(soucingChannel))||("ILCB_P".equalsIgnoreCase(soucingChannel))||     
					("ELCB_P".equalsIgnoreCase(soucingChannel))||("ILC_P".equalsIgnoreCase(soucingChannel))||("DBA_P".equalsIgnoreCase(soucingChannel))){
				return true;
			}
			else if(("GRNT".equalsIgnoreCase(requestCategory))||("ILC".equalsIgnoreCase(requestCategory))
					||("SBLC".equalsIgnoreCase(requestCategory))	//RR
					||("ELC".equalsIgnoreCase(requestCategory)) ||("EC".equalsIgnoreCase(requestCategory))
					||("IC".equalsIgnoreCase(requestCategory))  ||("ELCB".equalsIgnoreCase(requestCategory))
					||("ILCB".equalsIgnoreCase(requestCategory))||("DBA".equalsIgnoreCase(requestCategory))  
					){//RR(3025)
				{
					if("ILC".equalsIgnoreCase(requestCategory)){
						if("1".equalsIgnoreCase(thirdPartyValue))  //1 yes
						{   
							al.add("ACC");
							al.add("APP");
							al.add("BEN");

						}else if("2".equalsIgnoreCase(thirdPartyValue)){ //2 no

							al.add("APP");
							al.add("BEN");
						}
					}else if("ELC".equalsIgnoreCase(requestCategory)&&(!("ELC_SLCAA".equalsIgnoreCase(requestType)
							||"ELC_SER".equalsIgnoreCase(requestType)||"ELC_SCL".equalsIgnoreCase(requestType)))){	//RR

						al.add("ISB");
						al.add("APP");
						al.add("BEN");

					}else if("ELC".equalsIgnoreCase(requestCategory)&&"ELC_SLCA".equalsIgnoreCase(requestType)){	//RR
						al.add("APP");
						al.add("BEN");
						if("1".equalsIgnoreCase(thirdPartyValue))  //1 yes
						{   
							al.add("ACC");
						}
						if(productType.contains("T5")){
							al.add("APB");
						} if("T414".equalsIgnoreCase(productType)){
							al.add("ISB");
						} if( (!("Paper Guarantee".equalsIgnoreCase(modeOfGurantee)))&&                                  
								(!("GA".equalsIgnoreCase(requestType)||"GAA".equalsIgnoreCase(requestType)))  ){ 
							al.add("ABK");
						}

					}else if("GRNT".equalsIgnoreCase(requestCategory)||"SBLC".equalsIgnoreCase(requestCategory)){//RR
						
						if ("NI".equalsIgnoreCase(requestType))
							{
									log.info("****inside GRNT ISSUANCE***");
									String productKey = "";
									String Key = "";
									List<String> listData=null;
									String purposeMessage=formObject.getValue(PURPOSE_OF_MESSAGE).toString();
									String sourceChannel="NA";

									if(productType.contains("T5"))
										productKey="Y";
									else 
										productKey= "NA";
									
									Key = requestCategory+"#"+requestType+"#"+productKey+"#"+purposeMessage+"#"+thirdPartyValue+"#"+
											modeOfGurantee+"#"+sourceChannel;
									log.info("key: "+Key);
									listData=amendPartyMap.get(Key);
									log.info("purposeMessage: "+purposeMessage);
									log.info("map is :"+amendPartyMap);
									
									if(listData.size()> 0){
										for(String Obj:listData)
										{
											if(!al.contains(Obj)){
												al.add(Obj);
												log.info("Obj: "+Obj);
											}
										}
									}
							}
						else if ("SBLC_NI".equalsIgnoreCase(requestType))
						{
							log.info("****inside SBLC ISSUANCE***");
							String productKey ="NA";
							String Key = "";
							List<String> listData=null;
							String purposeMessage=formObject.getValue(PURPOSE_OF_MESSAGE).toString();
							String sourceChannel=formObject.getValue(SOURCE_CHANNEL).toString();
							String modeOfGurantee ="NA";

							Key = requestCategory+"#"+requestType+"#"+productKey+"#"+purposeMessage+"#"+thirdPartyValue+"#"+
									modeOfGurantee+"#"+sourceChannel;
							log.info("key: "+Key);
							listData=amendPartyMap.get(Key);
							log.info("purposeMessage: "+purposeMessage);
							log.info("map is :"+amendPartyMap);
							
							if(listData.size()> 0){
								for(String Obj:listData)
								{
									if(!al.contains(Obj)){
										al.add(Obj);
										log.info("Obj: "+Obj);
									}
								}
							}
						}else if ("GA".equalsIgnoreCase(requestType))	//RR for GRNT_GA
						{
							al.add("ISB");
							al.add("APP");
							al.add("BEN");
						}
						else{
						al.add("APP");
						al.add("BEN");
						if("1".equalsIgnoreCase(thirdPartyValue))  //1 yes
						{   
							al.add("ACC");
						}
						if(productType.contains("T5")){
							al.add("APB");
						} if("T414".equalsIgnoreCase(productType)){
							al.add("ISB");
						} if( (!("Paper Guarantee".equalsIgnoreCase(modeOfGurantee)))&&                                  
								(!("GA".equalsIgnoreCase(requestType)||"GAA".equalsIgnoreCase(requestType)))  ){ 
							al.add("ABK");
						}if("1".equalsIgnoreCase(thirdPartyValue)&& ("Counter Guarantee".equalsIgnoreCase(modeOfGurantee)||
								"Guarantee Advised thru Other Bank".equalsIgnoreCase(modeOfGurantee))&& 
								("NI".equalsIgnoreCase(requestType)||"SBLC_NI".equalsIgnoreCase(requestType))){	//RR
							al.add("OBP");
						}
						}
					}else if("EC".equalsIgnoreCase(requestCategory)){
						al.add("DRAWER");
						al.add("DRAWEE");
						al.add("COLLECTING BANK");
					}else if(("IC".equalsIgnoreCase(requestCategory))||("DBA".equalsIgnoreCase(requestCategory))){  
						al.add("DRAWER");
						al.add("DRAWEE");
						al.add("REMITTING BANK");
					}else if("ELCB".equalsIgnoreCase(requestCategory)&&(!("ELCB_BL".equalsIgnoreCase(requestType)))){  
						al.add("DRAWER");
						al.add("DRAWEE");
						al.add("ISSUING BANK");
					}else if("ILCB".equalsIgnoreCase(requestCategory)&&(!("ILCB_BL".equalsIgnoreCase(requestType)))){  
						al.add("DRAWER");
						al.add("DRAWEE");
						al.add("NEGOTIATING BANK");
					}                                                                                           
					return  checkListPD(al,LISTVIEW_PARTY);
				}
			}
			else{
				return true;	 
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
			return true;
		}

	}
	private boolean checkListPD(ArrayList containList, String sComplexName){  

		try {
			int length = getGridCount(sComplexName);
			String requestType = formObject.getValue(REQUEST_TYPE).toString();
			String requestCat = formObject.getValue(REQUEST_CATEGORY).toString();

			if(containList.isEmpty())
			{
				return true;
			}

			for(int i=0;i<length;i++){
				log.info("Party Type "+formObject.getTableCellValue(sComplexName, i, 0));
				if(containList.contains(formObject.getTableCellValue(sComplexName, i, 0))){
					containList.remove(formObject.getTableCellValue(sComplexName, i, 0));
				}
			}
			log.info("containList size="+containList.size());
			if ("GRNT".equalsIgnoreCase(requestCat) && ("CL".equalsIgnoreCase(requestType)
					||"CC".equalsIgnoreCase(requestType) || "EPC".equalsIgnoreCase(requestType)
					||"ER".equalsIgnoreCase(requestType) ||"AM".equalsIgnoreCase(requestType))){//mansi
				return true;
			}
			else if("SBLC".equalsIgnoreCase(requestCat) && "SBLC_AM".equalsIgnoreCase(requestType)){//santhosh
				return true;
			}
			else if(containList.isEmpty()){
				return true;
			}else{
				sendMessageHashMap(sComplexName,"Mandatory Party/Parties not flled "+containList.toString());
				return false;
			}
		} catch (HeadlessException e) {
			log.error("Exception: ",e);
			return true;
		}
	}
	private boolean checkContractLimitTabData(){

		String productType=formObject.getValue(PRODUCT_TYPE).toString();	
		if(("GRNT_MSM".equalsIgnoreCase(requestType))||("EC_MSM".equalsIgnoreCase(requestType))||("IC_MSM".equalsIgnoreCase(requestType))||("ELC_MSM".equalsIgnoreCase(requestType))    
				||("ILC_MSM".equalsIgnoreCase(requestType))||("ELCB_MSM".equalsIgnoreCase(requestType))||("ILCB_MSM".equalsIgnoreCase(requestType))||("IFP_MSM".equalsIgnoreCase(requestType))
				||("IFS_MSM".equalsIgnoreCase(requestType))||("TL_MSM".equalsIgnoreCase(requestType))||("ELC_PA".equalsIgnoreCase(requestType))||("ELCB_AOR".equalsIgnoreCase(requestType))
				||("ELCB_AOR".equalsIgnoreCase(requestType))||("ILCB_AOD".equalsIgnoreCase(requestType))||("ILCB_AOP".equalsIgnoreCase(requestType))
				||("SBLC_MSM".equalsIgnoreCase(requestType))) //RR
		{
			return true;
		}
		else if( (("IC_AC".equalsIgnoreCase(requestType))&& 
				("T3U5".equalsIgnoreCase(productType)||"I3U5".equalsIgnoreCase(productType)))
				||"DBA_BL".equalsIgnoreCase(requestType)      
				||"ILCB_AC".equalsIgnoreCase(requestType)){   
			int length = getGridCount(LISTVIEW_CONTRACT_LIMITS);
			if(length>0){
				return true;
			}else{
				sendMessageHashMap(LISTVIEW_CONTRACT_LIMITS, "Please enter data for Contract Limits");
				return false;
			}
		}
		return true;

	}
	private boolean validateMandatoryPDTab() { 
		if(validateMandatoryFieldsPM(PD_CUSTOMER_NAME, "Please Enter Customer Name")  
				&& validateMandatoryFieldsPM(PD_PARTY_ID, "Please Enter Party Id")  
				&& validateMandatoryFieldsPM(PARTY_TYPE, "Please Select Party Type")
				&& validateNumberFieldsPM(PARTY_DESC, "Please Enter Party Description")
				&& validateNumberFieldsPM(PD_PARTY_COUNTRY, "Please Select Country Name")) {
			return true;
		}
		else{
			return false;
		}
	}
	private boolean validateNumberFieldsPM(String sFieldName, String alertMsg) {
		try {
			String fieldValue = normalizeString(this.formObject.getValue(sFieldName).toString());
			if (!(isEmpty(fieldValue)) || fieldValue.equalsIgnoreCase("--Select--")
					|| "0".equalsIgnoreCase(fieldValue) || "0.000".equalsIgnoreCase(fieldValue)
					|| "0.0".equalsIgnoreCase(fieldValue)
					|| "0.00".equalsIgnoreCase(fieldValue)) {
				sendMessageHashMap(sFieldName, alertMsg);
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		return false;
	}
	private boolean validateIsPartyExist(){
		String partyType=formObject.getValue(PARTY_TYPE).toString();
		log.info("partyType :"+partyType);
		String gridListName=LISTVIEW_PARTY;
		int length = getGridCount(gridListName);
		log.info("length : " + length);
		for(int i=0;i<length;i++){ 
			log.info("value : "+formObject.getTableCellValue(gridListName, i, 0));
			if(partyType.equalsIgnoreCase(formObject.getTableCellValue(gridListName, i, 0))) 
			{
				log.info("inside kundli found"); 
				sendMessageHashMap(sFieldName,"Party Type already exist");
				return false;
			}
		}
		return true;
	}

	private boolean validateMandatoryCLTab() { 
		log.info("inside validateMandatoryCLTab >> ");
		if(validateMandatoryFieldsPM(CL_SERIAL_NUMBER, "Please Enter Serial Number") && validateMandatoryFieldsPM(CL_CUSTOMER_NO, "Please Enter Customer No")
				&& validateMandatoryFieldsPM(CL_PER_CONTRIBUTION, "Please Enter % Contribution") && validateMandatoryFieldsPM(CL_COMBO_OPERATION, "Please Enter Operation")  
				&& validateMandatoryFieldsPM(CL_COMBO_TYPE, "Please Enter Type")   && validateMandatoryFieldsPM(CL_COMBO_AMOUNT_TAG, "Please Enter Amount Tag")  
				&& validateMandatoryFieldsPM(CL_COMBO_PARTY_TYPE, "Please Select Party Type") && validateNumberFieldsPM("cl_linkageRefNo", "Please Enter Linkage Reference Number")) {
			return true;
		}
		else{
			return false;
		}
	}

	public boolean duplicateVesselCheck(String sVesselName, String sComplexName){
		int length = getGridCount(sComplexName);
		boolean flag=false;
		for(int i=0;i<length;i++){
			log.info("Name "+formObject.getTableCellValue(sComplexName, i, 1));
			log.info("Shipment date : "+formObject.getTableCellValue(sComplexName, i, 2));
			if(sVesselName.equalsIgnoreCase(formObject.getTableCellValue(sComplexName, i, 1)) && !(formObject.getTableCellValue(sComplexName, i, 2).isEmpty())){
				flag = true;
				break;
			}
		}
		log.info("Result "+flag);
		return flag;
	}
	public int getLLISeriallNumber(String sComplexname){
		int length =0;
		length = getGridCount(sComplexname)+1;
		log.info("length of List "+length);
		return length;
	}
	protected void deleteTaskList() {
		try {
			String sQuery="";
			for(String tableName : TABLENAMELIST ){
				sQuery = "DELETE FROM "+tableName+" WHERE WI_NAME ='"+this.sWorkitemID+"' and UPPER(VESSEL_NAME)NOT IN(Select UPPER(VESSEL_NAME) from TFO_DJ_LLI_VESSEL_DTLS where WI_NAME='"+this.sWorkitemID+"')";
				log.info("Delete "+sQuery);
				formObject.saveDataInDB(sQuery);
			}
		} catch (Exception e) {
			log.error("Exception: ",e);

		}
	}
	protected boolean validateShipmentdate(String sControlname){
		Date Shipmentdate=null;
		Date currDt = null;
		boolean res=true;
		try{
			currDt =new Date();

			Calendar cal = Calendar.getInstance();
			cal.setTime(currDt);
			cal.add(Calendar.YEAR, -1);

			Shipmentdate = sdf.parse(formObject.getValue(sControlname).toString());
			log.info("Date "+currDt+"  ShipmentDate "+Shipmentdate);
			if(Shipmentdate.after(currDt)){
				res = false;
				sendMessageHashMap(sControlname,"Shipped on Date cannot be future date");
			}
			if(Shipmentdate.before(cal.getTime())){
				res = false;
				sendMessageHashMap(sControlname,"Shipped on Date can't be older than an Year");
			}

		}catch(Exception e){
			log.error("Exception: ",e);
		}
		return res;
	}
	private int getNoOfMovementDetails( String sShipmentDate, String sVesselName) {		
		log.info("************************ getNoOfMovementDetails ****************************");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		int count = 0;
		String sInsertScript = "";
		Date currentDate = null;
		Date shipmentDate = null;
		try {
			currentDate = sdf.parse(sdf.format(new Date())); 
			shipmentDate = sdf.parse(sShipmentDate);
		} catch (ParseException e) {
			log.error("Exception: ",e);
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(shipmentDate);
		cal.add(Calendar.MONTH, -1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(currentDate);

		Calendar cal3 = Calendar.getInstance();
		cal3.setTime(currentDate);
		cal3.add(Calendar.MONTH, 1);

		if((shipmentDate.compareTo(currentDate) <= 0)){
			while((cal.getTime()).compareTo(cal2.getTime()) < 0) {
				Date fromDate = cal.getTime();
				cal.add(Calendar.MONTH, 1);
				cal.add(Calendar.DATE, -1);
				if(cal.getTime().compareTo(cal2.getTime()) < 0) {
					count++;
					sInsertScript = "INSERT INTO TFO_DJ_LLI_MVMNT_DTLS (SNO,WI_NAME,VESSEL_NAME,FROM_MVMNT_DATE,TO_MVMNT_DATE)"
							+ " VALUES('"+count+"','"+sWorkitemID+"','"+sVesselName+"','"+sdf.format(fromDate)+"','"+sdf.format(cal.getTime())+"')";
					log.info(sInsertScript);
					formObject.saveDataInDB(sInsertScript);
					log.info("Data Inserted"+count);
				}else {
					count++;
					sInsertScript = "INSERT INTO TFO_DJ_LLI_MVMNT_DTLS (SNO,WI_NAME,VESSEL_NAME,FROM_MVMNT_DATE,TO_MVMNT_DATE)"
							+ " VALUES('"+count+"','"+sWorkitemID+"','"+sVesselName+"','"+sdf.format(fromDate)+"','"+sdf.format(cal2.getTime())+"')";
					log.info(sInsertScript);
					formObject.saveDataInDB(sInsertScript);
					log.info("Data Inserted"+count);
				}
				cal.add(Calendar.DATE, 1);
			}
			if((cal.getTime()).compareTo(cal2.getTime()) == 0) {
				count++;
				sInsertScript = "INSERT INTO TFO_DJ_LLI_MVMNT_DTLS (SNO,WI_NAME,VESSEL_NAME,FROM_MVMNT_DATE,TO_MVMNT_DATE)"
						+ " VALUES('"+count+"','"+sWorkitemID+"','"+sVesselName+"','"+sdf.format(cal.getTime())+"','"+sdf.format(cal.getTime())+"')";
				log.info(sInsertScript);
				formObject.saveDataInDB(sInsertScript);
				log.info("Data Inserted"+count);
			}
			count++;
			sInsertScript = "INSERT INTO TFO_DJ_LLI_MVMNT_DTLS (SNO,WI_NAME,VESSEL_NAME,FROM_MVMNT_DATE,TO_MVMNT_DATE)"
					+ " VALUES('"+count+"','"+sWorkitemID+"','"+sVesselName+"','"+sdf.format(cal2.getTime())+"','"+sdf.format(cal3.getTime())+"')";
			log.info(sInsertScript);
			formObject.saveDataInDB(sInsertScript);
			log.info("Data Inserted"+count);

			log.info("Total no of entries =>"+count);
			return count;
		}
		else {
			return 0;
		}
	}	
	private void toInsertIntegrationCalls(int noOfBVDCalls, int noOfOwnershipCalls, int noOfETACalls, int noOfMovementCalls, String sVesselName) {
		try {
			log.info("***************Inside toInsertIntegrationCalls******************");
			String columnNames = "SNO,WI_NAME,CALL_NAME,CALL_DT,CALL_STATUS,RETRY_COUNT,VESSEL_NAME";
			ArrayList<String> callNameList = new ArrayList<String>();
			callNameList.add("BasicVesselDetails");
			callNameList.add("OwnershipDetails");
			callNameList.add("CompanyDetails");
			callNameList.add("ETADetails");
			callNameList.addAll(getCallNamesList(noOfMovementCalls,"MovementDetails"));

			for(int i = 0; i < callNameList.size(); i++) {
				String sInsertScript = "INSERT INTO TFO_DJ_INTEGRATION_CALLS ("+columnNames+") VALUES ('"+(i+1)+"','" + sWorkitemID + "','" + callNameList.get(i) + "',sysdate,'N','0','"+sVesselName+"')";
				log.info("[sInsertScript for Integration Calls]"+sInsertScript);
				formObject.saveDataInDB(sInsertScript);
				log.info("Data Inserted"+i);
			}

		}catch(Exception e) {
			log.error("Exception: ",e);
		}

	}
	private ArrayList<String> getCallNamesList(int noOfCalls,String callName) {
		ArrayList<String> callNameList = new ArrayList<String>();
		for(int i = 1; i < noOfCalls; i++) {
			if(i == noOfCalls) {
				callNameList.add(callName+"_"+i+"_Last");
			}else {
				callNameList.add(callName+"_"+i);
			} 
		}
		return callNameList;
	}
	protected void deleteUnusedRecord(String sVesselname) {
		deleteData(sVesselname, TABLENAMELIST);
	}
	protected void deleteData(String sVesselName, String ...tableNameArr){
		try {
			log.info("***************Inside deleteData******************");
			for(String tableName:tableNameArr){
				deleteRecords(tableName, this.sWorkitemID, sVesselName);
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}
	protected void deleteRecords(String sTableName, String sWiname, String sVesslname){
		log.info("***************Inside deleteRecords******************");
		try {
			String sQuery = "DELETE FROM "+sTableName;
			String sWhere = " WHERE WI_NAME='"+sWiname+"' and UPPER(VESSEL_NAME)='"+sVesslname.toUpperCase()+"'";
			sQuery +=sWhere;
			log.info("[sQuery]"+sQuery);
			formObject.saveDataInDB(sQuery);
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}
	private void showDataOnFetch(IFormReference formObject) {
		try {
			log.info("************showDataOnFetch***************");
			String sQuery = ""; 
			String vesselName = formObject.getValue(TXT_LLI_VESSELNAME).toString();
			String shipmentDate = formObject.getValue(LLI_SHIPMENT_DATE).toString(); 
			sQuery = "Select (select country_full_name from tfo_dj_lli_country_mast where country_code = a.VESSEL_FLAG) VESSEL_FLAG,VESSEL_ID,VESSEL_IMO from TFO_DJ_LLI_BASIC_VSSL_DTLS a where WI_NAME='"+sWorkitemID+"' AND UPPER(VESSEL_NAME)='"+vesselName.toUpperCase()+"'";
			log.info("[sQuery]=>"+sQuery);
			List<List<String>> tmpList=null;
			tmpList = formObject.getDataFromDB(sQuery);
			if(!tmpList.isEmpty()) {
				log.info("[tmpList]=>"+tmpList);
				formObject.setValue("TXT_VESSELNAME", vesselName);
				formObject.setValue("SHIPMENT_DATE", shipmentDate);
				formObject.setValue("TXT_VESSELFLAG", tmpList.get(0).get(0));
				formObject.setValue("TXT_VESSELID", tmpList.get(0).get(1));
				formObject.setValue("TXT_VESSELIMO", tmpList.get(0).get(2));
			}else {
				log.info("[tmpList]=> IS EMPTY");
			}

		}catch(Exception e) {
			log.error("Exception: ",e);
		} 
	}
	
	private void DeleteOldDataOnFetch(){
		try {
			log.info("************DeleteOldDataOnFetch***************");
			String sVesselName = formObject.getValue(TXT_LLI_VESSELNAME).toString();
			int length = getGridCount(LISTVIEW_LLI);
			boolean flag=false;
			for(int i=0;i<length;i++){
				log.info("Name "+formObject.getTableCellValue(LISTVIEW_LLI, i, 1));
				log.info("Shipment date : "+formObject.getTableCellValue(LISTVIEW_LLI, i, 2));
				if(sVesselName.equalsIgnoreCase(formObject.getTableCellValue(LISTVIEW_LLI, i, 1)) && (formObject.getTableCellValue(LISTVIEW_LLI, i, 2).isEmpty())){
					formObject.deleteRowsFromGrid(LISTVIEW_LLI, new int[]{i});
					break;
				}
			}

		}catch(Exception e) {
			log.error("Exception: ",e);
		} 
	}
	private boolean fetchValidatePartiesField(){
		if(validateMandatoryFieldsPM(PD_CUSTOMER_ID,"Please enter Customer ID")){
			return true;
		}else{
			return false;
		}
	}
	private void clearPartyDetailsField(String value){
		log.info("in clearPartyDeatilsField");
		if("BTNDELETEPARTYDETAILS".equalsIgnoreCase(value)){
			formObject.setValue(PARTY_TYPE,"");
			formObject.setValue(PARTY_DESC, formObject.getValue(PARTY_TYPE).toString());
			formObject.setValue(PD_CUSTOMER_ID,""); 
		}
		formObject.setValue(PD_PARTY_ID,"");
		formObject.setValue(PD_CUSTOMER_NAME,"");
		formObject.setValue("TFO_PD_ADDRESS1","");
		formObject.setValue("TFO_PD_ADDRESS2","");
		formObject.setValue("TFO_PD_ADDRESS3","");
		formObject.setValue("TFO_PD_ADDRESS4","");
		formObject.setValue("TFO_PD_COUNTRY","");
		formObject.setValue("TFO_MEDIA_TYPE","");
		formObject.setValue("TFO_PD_ADDRESS","");
	}
	private void fetchCustomerPDData(String cid, String reqCat) {
		setCustomerPDDetail(fetchFCRCustomer(cid, "GetCustomerSummary"), cid, reqCat);
	}
	private void setCustomerPDDetail(String resCustSmry, String cid, String reqCat) {

		String strFullName = "";
		String sMessege = "";
		int iPDError = 0;
		resCustSmry = resCustSmry.replaceAll("null", "");
		log.info("resCustSmry   >>>>>>>>" + resCustSmry);
		xmlDataParser = new XMLParser(resCustSmry);
		String sReturnCode = xmlDataParser.getValueOf("returnCode");
		try {
			if ("0".equalsIgnoreCase(sReturnCode) || "2".equalsIgnoreCase(sReturnCode)) {
				strFullName = xmlDataParser.getValueOf("FullName").trim();
				formObject.setValue(PD_CUSTOMER_NAME, strFullName);
				formObject.setValue("TFO_PD_ADDRESS2", xmlDataParser.getValueOf("POBox"));
				formObject.setValue("TFO_PD_ADDRESS3", xmlDataParser.getValueOf("AddressLine_1"));
				formObject.setValue("TFO_PD_ADDRESS4", xmlDataParser.getValueOf("AddressLine_2"));
				iPDError = 2;
				if(!(isEmpty(strFullName))) { 
					iPDError = 1;
				}else if(iPDError ==2){
					formObject.setValue(PD_PARTY_ID,formObject.getValue(PD_CUSTOMER_ID).toString());
				}
			} else {
				sMessege = getTagValue(resCustSmry, "td");
			}
		} catch (ParserConfigurationException e) {
			log.error("Exception: ",e);
			iPDError = 1;
		} catch (SAXException e) {
			log.error("Exception: ",e);
			iPDError = 1;
		} catch (IOException e) {
			log.error("Exception: ",e);
			iPDError = 1;
		} finally {
			if (iPDError == 0) {
				sendMessageHashMap(PD_CUSTOMER_ID,sMessege);
			} else if (iPDError == 1) {
				sendMessageHashMap(PD_CUSTOMER_ID,"Unable to fetch Party details. Kindly Enter correct CID");
			} else if (iPDError == 2) {
				formObject.setValue(PD_CUSTOMER_ID,"");
				sendMessageHashMap(PD_CUSTOMER_ID,"Party details fetched Successfully");
			}
		}
	}

	private String getTagValue(String xml, String tag) throws ParserConfigurationException, SAXException, IOException {
		xml = xml.replace("&", "#amp#");
		xml = xml.replace(";", "#col#");
		xml = xml.replace(",", "#Comma#");
		Document doc = getDocument(xml);
		NodeList nodeList = doc.getElementsByTagName(tag);
		String value = "";

		int length = nodeList.getLength();
		log.info("length---" + length);

		if (length > 0) {
			String sTempValue = "";
			for (int j = 0; j < length; j++) {
				Node node = nodeList.item(j);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					NodeList childNodes = node.getChildNodes();
					int count = childNodes.getLength();

					for (int i = 0; i < count; i++) {
						Node item = childNodes.item(i);
						if (item.getNodeType() == Node.ELEMENT_NODE) {
							sTempValue = item.getTextContent();

							if (sTempValue.indexOf("#amp#") != -1) {
								log.info("Index found");
								sTempValue = sTempValue.replace("#amp#", "&");
							}

							value += sTempValue + ",";
						} else if (item.getNodeType() == Node.TEXT_NODE) {
							value = item.getNodeValue();
							if (value.indexOf("#amp#") != -1) {
								log.info("Index found");
								value = value.replace("#amp#", "&");
								value = value.replace("#col#", ";");
								value = value.replace("#Comma#", ",");
							}
							return value;
						}
					}
					if (!value.equalsIgnoreCase("")) {
						value = value.substring(0, value.length() - 1);
						value = value + ";";
					}

				} else if (node.getNodeType() == Node.TEXT_NODE) {
					value = node.getNodeValue();
					if (value.indexOf("#amp#") != -1) {
						log.info("Index found");
						value = value.replace("#amp#", "&");
						value = value.replace("#col#", ";");
						value = value.replace("#Comma#", ",");
					}
					return value;
				}
			}

			if (!value.equalsIgnoreCase("")) {
				value = value.substring(0, value.length() - 1);
			}
			return value;
		}
		return "";
	}
	private Document getDocument(String xml) throws ParserConfigurationException, SAXException, IOException {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		return db.parse(new InputSource(new StringReader(xml)));
	}

	@Override
	public void updateDataInWidget(IFormReference arg0, String arg1) {
		//unused
	}

	@Override
	public String validateDocumentConfiguration(String arg0, String arg1,
			File arg2, Locale arg3) {

		return null;
	}
	private  void loadJSPData(){
		log.info("in loadJSPData");
		String query="";
		try{			
			query="	select CALL_NAME,to_char(CALL_DT,'dd/mm/yyyy hh:mi:ss') CALL_DATE, CALL_STATUS,"
					+ " STATUS , ERROR_DESCRIPTION,CALL_OPERATION,'"+callRequestType +"' as callRequestType  from TFO_DJ_INTEGRATION_CALLS WHERE WI_NAME ='"+this.sWorkitemID+"' AND CALL_STATUS = 'N'";
			log.info("in loadJSPData query"+query);

			List<List<String>> recordList= formObject.getDataFromDB(query);
			log.info("in loadJSPData response"+recordList);
			String colnames="callName,requestDateTime,callStatus,response,errorDetail,callOperation,callRequestType";
			LoadListView(recordList, colnames, "subForm_Final_Decision_ListView");
		}catch(Exception e){
			log.error("in loadJSPData Exception",e);
		}
	}

	private void loadAccountNumberAtPM(){
		try {
			log.info("inside loadAccountNumberAtPM >> ");
			String tmp = formObject.getValue(ACCOUNT_NUMBER).toString();
			log.info("account number value: "+tmp);
			if(!tmp.equalsIgnoreCase("")){
				log.info("adding item in combo account number");
				formObject.addItemInCombo(ACCOUNT_NUMBER, tmp);
				formObject.setValue(ACCOUNT_NUMBER, tmp);
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}


	public String executeTabClickCommand(String tabID) {
		try { 
			log.info("Inside executeTabClickCommand : " + tabID);
			boolean chkTreasury = true;
			boolean chkParty = true;
			if ((PM_CUSTOMER_SHEET_ID.equalsIgnoreCase(tabID)  || PM_COUNTER_SHEET_ID.equalsIgnoreCase(tabID) )) {
				enableControls(LIST_VIEW_BUTTONS);
				disableControls(BUTTON_SUBMIT);
			} 
			else if(PM_INPUT_SHEET_ID.equalsIgnoreCase(tabID)) {
				chkTransactionTenor(requestType,TRN_TENOR);
				enableControls(LIST_VIEW_BUTTONS);
				disableControls(BUTTON_SUBMIT);
			} 
			else if(PM_COMPLIANCE_SHEET_ID.equalsIgnoreCase(tabID)) {
				String enableSection = loadComplianceTabData();
				enableControls(LIST_VIEW_BUTTONS);
				disableControls(BUTTON_SUBMIT);
				if(enableSection.equalsIgnoreCase("enableSection")){
					return enableSection;
				}
			} 
			else if(PM_VERIFY_SHEET_ID.equalsIgnoreCase(tabID)) {
				enableControls("btnNext,btnSave");
				disableControls("btnPrevious,btnSubmit");
			} 
			else if(PM_TREASURY_SHEET_ID.equalsIgnoreCase(tabID)) {
				String trsdFlag = formObject.getValue(TRSD_FLAG).toString();
				if(pmCompReferalPopulationControl()){ //Checking TRSD required or not
					if(!formObject.getValue(PROCESS_TYPE).toString().equalsIgnoreCase("TSLM Front End")){
						if(!(trsdFlag.equalsIgnoreCase("N")||trsdFlag.equalsIgnoreCase("A"))){  //N means no trsd found,A= means trsd found and approved 
							sendMessageHashMap("", "Please complete the cases pending in Screening tab");
						}
					}
				}
				enableControls(LIST_VIEW_BUTTONS3);
				disableControls(BUTTON_SUBMIT);
			} 
			else if(PM_TRSD_SHEET_ID.equalsIgnoreCase(tabID)) {
				//compliance/party should be mandatory only if compliance is mandatory
				if(pmCompReferalPopulationControl())
				{
					log.info("pmCompReferalPopulationControl returned true");
					if(!(validateOptionBtn())){
						log.info("validateOptionBtn returned false");
						chkParty = false;
						enableControls(LIST_VIEW_BUTTONS);
						disableControls(BUTTON_SUBMIT);
					}
					else
					{
						log.info("validateOptionBtn returned true");
						enableControls(LIST_VIEW_BUTTONS2);
						disableControls(BUTTON_NEXT);
					}
					//Changes ATP-458 Shivanshu
				if(chkParty && checkMandatoryPD()&& checkContractLimitTabData() && validatePartyCountry()){ 
						insertIntoTrsdtable();
						LoadListViewOfVesselScreening();
					    LoadListViewOfPartyCountryScreening();
					  //  LoadListViewOfPartyScreening();
					}
				} else
				{
					log.info("pmCompReferalPopulationControl returned false");
					enableControls(LIST_VIEW_BUTTONS2);

				}

				disableControls(BUTTON_SUBMIT);
			}
			else if(PM_DEC_SHEET_ID.equalsIgnoreCase(tabID)) {
				if(pmCompReferalPopulationControl())
				{
					log.info("pmCompReferalPopulationControl returned true");
					if(!(validateOptionBtn())){
						log.info("validateOptionBtn returned false");
						chkTreasury = false;
						enableControls(LIST_VIEW_BUTTONS);
						disableControls(BUTTON_SUBMIT);
					}
					else
					{
						log.info("validateOptionBtn returned true");
						enableControls(LIST_VIEW_BUTTONS2);
						disableControls(BUTTON_NEXT);
					}

				}
				else
				{
					log.info("pmCompReferalPopulationControl returned false");
					enableControls(LIST_VIEW_BUTTONS2);
					disableControls(BUTTON_NEXT);
				}
				if (chkTreasury) {
					if (pmTreasuryReferalPopulation())
					{
						log.info("pmTreasuryReferalPopulation returned true");
						if (exchangeRateValidation()){
							log.info("exchangeRateValidation returned true");
							if (!(validateTrTabAndRepeaterData())){
								enableControls(LIST_VIEW_BUTTONS);
								disableControls(BUTTON_SUBMIT);
							}else {

								enableControls(LIST_VIEW_BUTTONS2);
								disableControls(BUTTON_NEXT);
							}
						}
						else {
							enableControls(LIST_VIEW_BUTTONS);
							disableControls(BUTTON_SUBMIT);
						}

					}
					else {
						log.info("pmTreasuryReferalPopulation returned false");
						enableControls(LIST_VIEW_BUTTONS2);
						disableControls(BUTTON_NEXT);
					}	
				}
				log.info("Calling Dup check");
				duplicateCheckConfirmation(DUP_CHK_CONFIRMATION, false);
				log.info("Final decision Tab click");
				setEmailFlag();
				String requestCategory = formObject.getValue(REQUEST_CATEGORY).toString();
				popuateInvoiceData();
				
			}
			else if(PM_PARTY_SHEET_ID.equalsIgnoreCase(tabID)) { 
				disableControls("btnModifyPartyDetails");
				enableControls(LIST_VIEW_BUTTONS);
				disableControls(BUTTON_SUBMIT);
			}
			else if(PM_INVOICE_DETAIL_SHEET_ID.equalsIgnoreCase(tabID)) {   //invoice details
				popuateInvoiceData();
		}

		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		return "";
	}
	protected boolean pmCompReferalPopulationControl(){
		String sRevisedDoc="";
		sRevisedDoc = formObject.getValue(BILL_RVSD_DOC_AVL).toString();
		if((("IFS".equalsIgnoreCase(requestCategory)|| "IFA".equalsIgnoreCase(requestCategory)
				||"IFP".equalsIgnoreCase(requestCategory) ||"SCF".equalsIgnoreCase(requestCategory))&& ("LD".equalsIgnoreCase(requestType) || "IFA_CTP".equalsIgnoreCase(requestType) || "PD".equalsIgnoreCase(requestType) ||  "AM".equalsIgnoreCase(requestType) || "PDD".equalsIgnoreCase(requestType))) 
				||(("GRNT".equalsIgnoreCase(requestCategory)) && ("NI".equalsIgnoreCase(requestType)||"CL".equalsIgnoreCase(requestType)
						||"AM".equalsIgnoreCase(requestType)||"ER".equalsIgnoreCase(requestType)
						||"GA".equalsIgnoreCase(requestType)||"GAA".equalsIgnoreCase(requestType)))
						||(("SBLC".equalsIgnoreCase(requestCategory)) && ("SBLC_NI".equalsIgnoreCase(requestType)||"SBLC_CR".equalsIgnoreCase(requestType)||"SBLC_CS".equalsIgnoreCase(requestType)
								||"SBLC_AM".equalsIgnoreCase(requestType)||"SBLC_ER".equalsIgnoreCase(requestType)))  //RR
								||(("IC".equalsIgnoreCase(requestCategory)) && ("IC_BL".equalsIgnoreCase(requestType) ||("IC_AM".equalsIgnoreCase(requestType) && "1".equalsIgnoreCase(sRevisedDoc)) ))
								||(("DBA".equalsIgnoreCase(requestCategory)) && ("DBA_BL".equalsIgnoreCase(requestType) ||("DBA_AM".equalsIgnoreCase(requestType) && "1".equalsIgnoreCase(sRevisedDoc))))
								||(("EC".equalsIgnoreCase(requestCategory)) && ("EC_BL".equalsIgnoreCase(requestType)||"EC_LDDI".equalsIgnoreCase(requestType)||("EC_AM".equalsIgnoreCase(requestType) && "1".equalsIgnoreCase(sRevisedDoc)) ))
								||(("ILC".equalsIgnoreCase(requestCategory)) && ("ILC_NI".equalsIgnoreCase(requestType)||"ILC_AM".equalsIgnoreCase(requestType)||"ILC_UM".equalsIgnoreCase(requestType)))
								||(("ELC".equalsIgnoreCase(requestCategory)) && ("ELC_LCA".equalsIgnoreCase(requestType)||"ELC_LCAA".equalsIgnoreCase(requestType)
										||"ELC_AOP".equalsIgnoreCase(requestType)||"ELC_LCT".equalsIgnoreCase(requestType)||"ELC_SLCA".equalsIgnoreCase(requestType)
										||"ELC_SLCAA".equalsIgnoreCase(requestType)||"ELC_SER".equalsIgnoreCase(requestType)||"ELC_SCL".equalsIgnoreCase(requestType)	//RR
										||"ELC_RSD".equalsIgnoreCase(requestType)))
										||(("ELCB".equalsIgnoreCase(requestCategory)) && ("ELCB_BL".equalsIgnoreCase(requestType)||("ELCB_AM".equalsIgnoreCase(requestType) && "1".equalsIgnoreCase(sRevisedDoc))))
										||"TL".equalsIgnoreCase(requestCategory)&& ("TL_LD".equalsIgnoreCase(requestType) ||"TL_AM".equalsIgnoreCase(requestType) )&& ("BC".equalsIgnoreCase(formObject.getValue(IFS_LOAN_GRP).toString())   //FEATURE/SCFMVP2.5 REYAZ 24/07/2024
												|| "MRPA".equalsIgnoreCase(formObject.getValue(IFS_LOAN_GRP).toString()))
										||(("ILCB".equalsIgnoreCase(requestCategory)) && ("ILCB_BL".equalsIgnoreCase(requestType) || ("ILCB_AM".equalsIgnoreCase(requestType) && "1".equalsIgnoreCase(sRevisedDoc))))
										||(("SG".equalsIgnoreCase(requestCategory)) && ("SG_NILC".equalsIgnoreCase(requestType)||"SG_NIC".equalsIgnoreCase(requestType)))
				)
			return true;
		else 
			return false;
	}

	protected boolean pmTreasuryReferalPopulation(){
		log.info("***************Inside pmTreasuryReferalPopulation****************");
		if((("IFS".equalsIgnoreCase(requestCategory)|| "IFA".equalsIgnoreCase(requestCategory)//Code by Rakshita
				||"IFP".equalsIgnoreCase(requestCategory))&& ("LD".equalsIgnoreCase(requestType)
						||"AM".equalsIgnoreCase(requestType)))
						||(("IC".equalsIgnoreCase(requestCategory))&& ("IC_BS".equalsIgnoreCase(requestType)))
						||(("DBA".equalsIgnoreCase(requestCategory))&& ("DBA_STL".equalsIgnoreCase(requestType)))
						||(("EC".equalsIgnoreCase(requestCategory))&& ("EC_BS".equalsIgnoreCase(requestType)||"EC_DISC".equalsIgnoreCase(requestType)
								||"EC_CAP".equalsIgnoreCase(requestType)||"EC_LDDI".equalsIgnoreCase(requestType)))
								||(("ILCB".equalsIgnoreCase(requestCategory))&& ("ILCB_BS".equalsIgnoreCase(requestType)))
								||(("ELCB".equalsIgnoreCase(requestCategory)) && ("ELCB_BS".equalsIgnoreCase(requestType)||"ELCB_DISC".equalsIgnoreCase(requestType)||"ELCB_CLBP".equalsIgnoreCase(requestType)))
								||(("TL".equalsIgnoreCase(requestCategory))&& ("TL_LD".equalsIgnoreCase(requestType))))
			return true;
		else 
			return false;
	}


	protected void setEmailFlag(){
		log.info("***************Inside setEmailFlag******************");

		try {
			int iRowCount =getGridCount(LISTVIEW_FINAL_DECISION);
			String checkBoxSendEmail=formObject.getValue(CHKBX_SEND_MAIL).toString();

			log.info("iRowCount"+iRowCount);
			log.info("MANUALLY_TICKED_MAIL="+formObject.getValue(MANUALLY_TICKED_MAIL).toString());
			if((null==formObject.getValue(MANUALLY_TICKED_MAIL)||"null".equalsIgnoreCase(formObject.getValue(MANUALLY_TICKED_MAIL).toString())
					|| "".equalsIgnoreCase(formObject.getValue(MANUALLY_TICKED_MAIL).toString())
					)
					){ 
				if (iRowCount>1&&"false".equalsIgnoreCase(checkBoxSendEmail))
				{
					log.info("val--"+formObject.getValue(CHKBX_SEND_MAIL));
					enableControls(CHKBX_SEND_MAIL);
					formObject.setValue(CHKBX_SEND_MAIL, "true");	
				}
			}

		} catch (Exception e) {

			log.error("Exception: ",e);
		}
	}

	public void setFxRate(){
		try {
			String sOutput=fetchExchnageRate();
			if(!sOutput.isEmpty()){
				sOutput = sOutput.replaceAll("null", "");
				log.info("sOutput   >>>>>>>>" + sOutput);
				xmlDataParser = new XMLParser(sOutput);
				String sReturnCode = xmlDataParser.getValueOf("returnCode");
				if ("0".equalsIgnoreCase(sReturnCode) || "2".equalsIgnoreCase(sReturnCode)) {
					log.info("FX rate returned successfully");
					String sellRate = xmlDataParser.getValueOf("sellRate");
					boolean bSetExRate=false;
					if("Base Rate".equalsIgnoreCase(xmlDataParser.getValueOf("rateMethod"))){
						sendMessageHashMap("", "Exchange Rates not Available in Rate Server");
					}else if("Contract Reference Rate".equalsIgnoreCase(xmlDataParser.getValueOf("rateMethod"))){
						bSetExRate=true;
						sendMessageHashMap("", "Exchange Rates for Deal number successfully fetched from Rate Server");
					}else if("Customer Relationship Rate".equalsIgnoreCase(xmlDataParser.getValueOf("rateMethod"))){
						bSetExRate=true;
						sendMessageHashMap("", "Customer Specific Exchange Rates successfully fetched from Rate Server");
					}					
					if(bSetExRate && null != sellRate && !"".equalsIgnoreCase(sellRate)){
						formObject.setValue("TR_EXCHANGE_RATE", sellRate);
						formObject.setValue(UI_EXCHANGE_RATE, sellRate);
						log.info("Exchange rate set.");
					}else{
						log.info("Exchange rate not set.");
					}
				}else {
					sendMessageHashMap("", xmlDataParser.getValueOf("errorDescription"));
				}
			}			
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}
	private String fetchExchnageRate(){
		try {
			String trDealNo = formObject.getValue("TR_TREASURY_DEAL_NUM").toString();
			String trCid = formObject.getValue(CUSTOMER_ID).toString();
			String trLoanAmt = "";
			String trFrmCur = formObject.getValue(TR_BUY_CUR).toString();
			String trToCur = formObject.getValue(TR_SELL_CUR).toString();
			String refNo = "";			
			List<List<String>> sOutputlist = formObject.getDataFromDB("SELECT SEQ_WEBSERVICE.nextval as IDValue from DUAL ");			
			if (!sOutputlist.isEmpty() && !sOutputlist.get(0).isEmpty()) {				
				refNo = sOutputlist.get(0).get(0);				
			}
			return socket.connectToSocket(prepareFXRateXml(trDealNo, trCid, trLoanAmt, trFrmCur, trToCur,refNo,senderId));
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		return  "";
	}

	private String prepareFXRateXml(String trDealNo,String trCid,String trLoanAmt,
			String trFrmCur,String trToCur,String refNo,String senderid){
		StringBuffer sb=null;
		try {
			DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
			Date dt = new Date();
			String currDate = formatter.format(dt);

			sb= new StringBuffer();
			sb.append("<?xml version=\"1.0\"?><APWebService_Input><Option>WebService</Option>");		
			sb.append("<Calltype>WS-2.0</Calltype><InnerCallType>inqFxRate</InnerCallType>");
			sb.append("<REF_NO>"+refNo+"</REF_NO>" );
			sb.append("<senderID>"+senderid+"</senderID>");
			sb.append("<RequestDateTime>"+dt+"</RequestDateTime>");
			sb.append("<fromCurrency>"+trFrmCur+"</fromCurrency>");
			sb.append("<toCurrency>"+trToCur+"</toCurrency>");
			sb.append("<amount>"+trLoanAmt+"</amount>");
			sb.append("<customerType></customerType>");
			sb.append("<algorithm>1</algorithm>");
			if(!("".equalsIgnoreCase(trDealNo) || trDealNo.isEmpty())){
				sb.append("<dealReferenceNumber>"+trDealNo+"</dealReferenceNumber>");
			}else{
				sb.append("<dealReferenceNumber/>");
			}
			if(!("".equalsIgnoreCase(trCid) || trCid.isEmpty())){
				sb.append("<customerId>"+trCid+"</customerId>");
			}else{
				sb.append("<customerId/>");
			}					
			sb.append("<sourceSystem>PHUB</sourceSystem>");
			sb.append("<valueDate>"+currDate+"</valueDate>");
			sb.append("<winame>"+sWorkitemID+"</winame>");			
			log.info("input xml fetch rate"+ sb.toString());

		} catch (Exception e) {			
			log.error("Exception: ",e);
		}
		return sb.toString();
	}
	private String createNewWorkitemELCB(){ //PROTRADE_140
		String query="";
		List<List<String>> sOutputlist =null;
		String output="";
		String processType="BAU";
		String reqCat="ELCB";
		String reqType="ELCB_DISC";
		String reqSubmitted="ELCB_ADCB";
		String sourceChannel="ELCB_B";
		String initatorBranch=formObject.getValue(INITIATOR_BRANCH).toString();
		String initatorUserID=formObject.getValue(INITIATOR_USERID).toString();
		String branchCity=formObject.getValue(TFO_BRANCH_CITY).toString();
		String assignedCenter=formObject.getValue(TFO_ASSIGNED_CENTER).toString();
		String trasactionRefernce=formObject.getValue(TRANSACTION_REFERENCE).toString();
		String discountOnAccep=formObject.getValue(DISCOUNT_ON_ACCEP).toString();
		String winame = formObject.getValue("WI_NAME").toString();
		try{
			if("ELCB_AC".equalsIgnoreCase(requestType)&&"1".equalsIgnoreCase(discountOnAccep)){
				query="select LINKED_WI_NAME from ext_tfo where wi_name='"+winame+"'";
				log.info("createNewWorkitemELCB=" + query);
				sOutputlist=formObject.getDataFromDB(query);
				log.info("sOutputlist= " + sOutputlist);
				if(!sOutputlist.get(0).get(0).equalsIgnoreCase("")){
					output = sOutputlist.get(0).get(0).toString();
				}
				if("".equalsIgnoreCase(output)||"null".equalsIgnoreCase(output)){
					sOutputlist = formObject.getDataFromDB("SELECT SEQ_WEBSERVICE.nextval as IDValue from DUAL");
					String txnNumber= sOutputlist.get(0).get(0);
					query="insert into tfo_dj_protrade_txn_data(requestmode, channelname,insertiondatetime,version,processname,process_type, channelrefnumber, sysrefno,"
							+"correlationid,wi_name, request_type, request_category, transaction_reference,INITIATOR_BRANCH,INITIATOR_USERID, "
							+" branch_city, assigned_center, requested_by, source_channel) "
							+ "values('C','LINK_WI',current_date,'1','TFO','BAU','"+txnNumber+"','"+txnNumber+"','"+txnNumber+"','"+winame+"','"
							+reqType+"','"+reqCat+"','"+trasactionRefernce+"','"+initatorBranch+"','"+initatorUserID+"','"
							+branchCity+"','"+assignedCenter+"','"+reqSubmitted+"','"+sourceChannel+"')";
					log.info("query="+query);
					int value=formObject.saveDataInDB(query);
					log.info(" output value="+value);

					handleCreateWorkitemOutput(createWorkitem(txnNumber));
					saveDecHistory("Notification","1. As DISCOUNT_ON_ACCEP is Yes, a discounting request work item will be automatically created, post completion of acceptance step.2. The auto created work item will be available in PPM queue and can be retrieved either by Web deskstop or in Dash board. Ensure to discount this bill.");
					return "showNotificationAlert";
				}
			}
		}catch(Exception e){
			log.error("Exception in createNewWorkitemELCB",e);
		}
		return "false";
	}
	public String createWorkitemInputXML(String sTxn) throws Exception {
		StringBuilder inputXml=new StringBuilder();
		try {

			inputXml.append("<?xml version=\"1.0\"?>").append("\n")
			.append("<APWebService_Input>").append("\n")
			.append("<Option>WebService</Option>").append("\n")
			.append("<EngineName>" + engineName + "</EngineName>").append("\n")
			.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
			.append("<Calltype>WS-2.0</Calltype>").append("\n")
			.append("<InnerCallType>BPMModify</InnerCallType>").append("\n")			
			.append("<wiNumber>" +sWorkitemID + "</wiNumber>").append("\n")
			.append("<REF_NO>" + sTxn + "</REF_NO>").append("\n")
			.append("<senderId>" + "WMS" + "</senderId>").append("\n")
			.append("<mode>C</mode>").append("\n")
			.append("<channelName>LINK_WI</channelName>").append("\n")
			.append("<correlationId>"+sTxn+"</correlationId>").append("\n")
			.append("<channelRefNumber>"+sTxn+"</channelRefNumber>").append("\n")
			.append("<sysrefno>"+sTxn+"</sysrefno>").append("\n")
			.append("<processName>TFO</processName>").append("\n")
			.append("</APWebService_Input>");
			log.info("input xml of createWorkitemInputXML="+ inputXml.toString());
		} catch (Exception e) {
			log.error("Exception in",e);
		}
		return inputXml.toString();
	}
	/*
	public String createWorkitem(String txnNumber) throws Exception {
		String sOutput = "";
		sOutput = createWorkitemInputXML(txnNumber);
		sOutput = socket.connectToSocket(sOutput);
		log.info("output="+sOutput);
		return sOutput;
	}
	public  void handleCreateWorkitemOutput(String sOutput){
		try
		{	
			sOutput = sOutput.replace("null", "");
			log.info("handleCreateWorkitemOutput   >>>>>>>>" + sOutput);
			xmlDataParser = new XMLParser(sOutput);
			String sReturnCode = xmlDataParser.getValueOf("StatusCode");
			String linkedWorkitemNumber= xmlDataParser.getValueOf("WINumber");
			log.info("return code: "+sReturnCode);

			if ("0".equalsIgnoreCase(sReturnCode)&&!linkedWorkitemNumber.isEmpty()) {

				String query="update ext_tfo set LINKED_WI_NAME='"+linkedWorkitemNumber+"' where wi_name='"+sWorkitemID+"'";
				log.info("query update ext_tfo = "+ query);
				int value = formObject.saveDataInDB(query);
				log.info("query handleCreateWorkitemOutput= "+ value);
			}

			log.info("sOutput="+sOutput);

		}catch(Exception e){
			log.error("Exception in",e);
		}
	}
*/
	private void insertIntoNotificationTxn() {
		try{
			log.info("inside insertIntoNotificationTxn");
			String proTradeRef = "";
			String prodCode = "";
			String status = "";
			String remarks = "";
			String decision = formObject.getValue(DEC_CODE).toString();
			String sQuery="select PRO_TRADE_REF_NO, PRODUCT_TYPE "
					+ "from ext_tfo"
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
					if(decision.equalsIgnoreCase("REF")) {
						status = "Pending Approval";
					} else if(decision.equalsIgnoreCase("TXNC") || decision.equalsIgnoreCase("TXNAU")) {
						status = "Transaction under process";
					}
					if(!status.isEmpty()){
						String query = "INSERT INTO TFO_DJ_PROTRADE_FCDB_UPDATE (INSERTIONDATETIME,"
								+ "WI_NAME, PRO_TRADE_REF_NO, PRODUCT_CODE, STATUS, REMARKS, WS_NAME, EXECUTION_STATUS) "
								+ "VALUES (SYSDATE,'"+this.sWorkitemID+"','"+proTradeRef+"','"+prodCode+"','"+status+"'"
								+ ",'"+remarks.replaceAll("'", "''")+"','"+this.sActivityName+"','N')";
						log.info("insertIntoNotificationTxn query: " + query);
						int b = formObject.saveDataInDB(query);
						log.info("insert status: "+b);
					} else {
						log.info("insertIntoNotificationTxn not updated for decision: " + decision);
					}
				}
			}
		}catch(Exception e){
			log.error("Exception in insertIntoNotificationTxn: ",e);
		}
	}

	@SuppressWarnings({ "unchecked", "unchecked" })
	private void insertIntoTrsdtable() {  //TRSD_CHANGE  //TRSD_TABLE_OPTIONAL
		try{
			String query =	null;
			List<List<String>> listData;
			String trsdWI=formObject.getValue(TRSD_WI_NAME).toString();
			if(compCheckTab()){
				log.info("yooooooo");
				if(trsdWI==null || "".equalsIgnoreCase(trsdWI))
				{
					formObject.clearTable("TRSD_TABLE");
					log.info("HELLOOOOOO");
					query = "select party_type,party_desc as entity,customer_name as entity_type,'Organisation' as screening_type"
							+ " from TFO_DJ_PARTY_DETAILS where winame = '"+sWorkitemID+"'"; //CHANGE BY KISHAN 14/12/21 ADDED CUSTOMER_NAME column
					listData = formObject.getDataFromDB(query);
					addDataIntoTRSD("party",listData);
					if(formObject.getValue(LLI_CHK_OK).toString().equalsIgnoreCase("Y")){
						query = "select  vessel_flg,'Vessel Name' as entity ,upper(vessel_name) as entity_type ,'Vessel' as screening_type from TFO_DJ_LLI_VESSEL_DTLS where wi_name='"+sWorkitemID+"'";
						listData = formObject.getDataFromDB(query);
						addDataIntoTRSD("vessel",listData);
						log.info("listdata: "+listData);
					}
					addDataIntoTRSDOptional();
					
				}else {   //checking is there any party modifification in party GRID
					addDataIntoTRSDOptional();//should go to addDataIntoTRSDOptional's else condition only //BY KISHAN
					query = "select party_type,party_desc as entity,customer_name as entity_type,'Organisation' as screening_type"
							+ " from TFO_DJ_PARTY_DETAILS where winame = '"+sWorkitemID+"'";
					listData = formObject.getDataFromDB(query);
					addDataIntoTRSD("partyAfterTrsd",listData);
				}
				enableDisableTRSDButton();
			}else{
				formObject.setTabStyle("tab1", "7", "disable", "true");
			}
		}catch(Exception e){
			log.error("Error in  insertIntoTrsdtable FUNCTION ");
		}

	}

	private void addDataIntoTRSDOptional(){
		try{
			int iRowCount =getGridCount(LISTVIEW_TRSD_TABLE_OPTIONAL);
			log.info("iRowCount LISTVIEW_TRSD_TABLE_OPTIONAL:"+iRowCount+" requestType:"+requestType);
			JSONArray jsonArray =new JSONArray();
			if(iRowCount==0){
				//add data into additional grid
				if("SG_NIC".equalsIgnoreCase(requestType)||"DBA".equalsIgnoreCase(requestCategory)){
					String CustomerName=formObject.getValue(CUSTOMER_NAME).toString();
					addDataEntity("DRAWEE/BUYER",CustomerName,jsonArray);
					formObject.addDataToGrid(LISTVIEW_TRSD_TABLE_OPTIONAL,jsonArray); 
//				}else if("EC".equalsIgnoreCase(requestCategory)){
//					String CustomerName=formObject.getValue(CUSTOMER_NAME).toString();
//					addDataEntity("DRAWER/SELLER",CustomerName,jsonArray);
//					formObject.addDataToGrid(LISTVIEW_TRSD_TABLE_OPTIONAL,jsonArray); 
				}else if("IFP".equalsIgnoreCase(requestCategory)||"IFA".equalsIgnoreCase(requestCategory) ||"SCF".equalsIgnoreCase(requestCategory)){  //ATP - 204,205
					String CustomerName=formObject.getValue(CUSTOMER_NAME).toString();
					addDataEntity("DRAWEE/BUYER",CustomerName,jsonArray);

					String processingSystem = formObject.getValue(PROCESSING_SYSTEM).toString();
					if(processingSystem.equalsIgnoreCase("F")){
						int length = getGridCount(Qvar_cpd_details);
						for(int i=0;i<length;i++){
							String partyname = formObject.getTableCellValue(Qvar_cpd_details, i, 0);
							String partycountry = formObject.getTableCellValue(Qvar_cpd_details, i, 1); //Added by reyaz 17-05-2023
							addDataEntity("DRAWER/SELLER",partyname,jsonArray);
							addDataEntity("DRAWER/SELLER COUNTRY",partycountry,jsonArray);  //Added by reyaz 17-05-2023
						}
					}
					else if(processingSystem.equalsIgnoreCase("T")){
						int length = getGridCount(Q_TSLm_Counter_Dets);
						for(int i=0;i<length;i++){
							String checkbox = formObject.getTableCellValue(Q_TSLm_Counter_Dets, i, 4);
							 log.info("checkbox for counter party : " + checkbox);
							String partyname = formObject.getTableCellValue(Q_TSLm_Counter_Dets, i, 1);
							String partycountry = formObject.getTableCellValue(Q_TSLm_Counter_Dets, i, 2);
							if(checkbox.equalsIgnoreCase(TRUE)){
								addDataEntity("DRAWER/SELLER",partyname,jsonArray);
								addDataEntity("DRAWER/SELLER COUNTRY",partycountry,jsonArray);  //Added by reyaz 17-05-2023
							}
						}
					}
					formObject.addDataToGrid(LISTVIEW_TRSD_TABLE_OPTIONAL,jsonArray);
				}else if("IFS".equalsIgnoreCase(requestCategory)){
					String CustomerName=formObject.getValue(CUSTOMER_NAME).toString();
					addDataEntity("DRAWER/SELLER",CustomerName,jsonArray);

					String processingSystem = formObject.getValue(PROCESSING_SYSTEM).toString();
					if(processingSystem.equalsIgnoreCase("F")){
						int length = getGridCount(Qvar_cpd_details);
						for(int i=0;i<length;i++){
							String partyname = formObject.getTableCellValue(Qvar_cpd_details, i, 0);
							String partycountry = formObject.getTableCellValue(Qvar_cpd_details, i, 1); //Added by reyaz 17-05-2023
							addDataEntity("DRAWEE/BUYER",partyname,jsonArray);
							addDataEntity("DRAWEE/BUYER COUNTRY",partycountry,jsonArray);  //Added by reyaz 17-05-2023

						}
					}
					else if(processingSystem.equalsIgnoreCase("T")){
						int length = getGridCount(Q_TSLm_Counter_Dets);
						for(int i=0;i<length;i++){
							String checkbox = formObject.getTableCellValue(Q_TSLm_Counter_Dets, i, 4);
							String partyname = formObject.getTableCellValue(Q_TSLm_Counter_Dets, i, 1);
							String partycountry = formObject.getTableCellValue(Q_TSLm_Counter_Dets, i, 2); //Added by reyaz 17-05-2023
							if(checkbox.equalsIgnoreCase(TRUE)){
								addDataEntity("DRAWEE/BUYER",partyname,jsonArray);
								addDataEntity("DRAWEE/BUYER COUNTRY",partycountry,jsonArray);  //Added by reyaz 17-05-2023

							}
						}
					}
					formObject.addDataToGrid(LISTVIEW_TRSD_TABLE_OPTIONAL,jsonArray);
				}else if("TL".equalsIgnoreCase(requestCategory)&& "TL_LD".equalsIgnoreCase(requestType)){	//RR for TL_LD
				 log.info("hello1");
				 String CustomerName=formObject.getValue(CUSTOMER_NAME).toString();
				 log.info("hello2");
				 addDataEntity("OBLIGOR NAME",CustomerName,jsonArray);
				 addDataEntity("GRANTOR NAME","",jsonArray);  ///Added by Shivanshu FOR Trade Loans
				 log.info("hello3");
				 formObject.addDataToGrid(LISTVIEW_TRSD_TABLE_OPTIONAL,jsonArray);
				 log.info("hello4");
				}
				addMandatoryScreening(); //added date 10-07-2023
			}else{ //ADDED BY KISHAN FOR NO NEW UPDATED ROW IN TRSD OPTIONAL ISSUE  ::77272
				log.info("INSIDE NEW TRSD UPDATED CONDITION ELSE");
				HashMap<String,String> trsdOptionalListMap = new HashMap<String,String>();
				HashMap<String,String> trsdlistStatusmap =  new HashMap<String,String>();
				JSONArray jsonArrayNew =  formObject.getDataFromGrid(LISTVIEW_TRSD_TABLE_OPTIONAL);
				List deleteRowList=new ArrayList<>();
				log.info("jsonArrayNew "+jsonArrayNew.toString());
				log.info("jsonArrayNew "+jsonArrayNew.size());
				
				log.info("CHECK1 ");
				for(int count = 0 ; count < jsonArrayNew.size() ; count++){	 //size = 4
					String entity = formObject.getTableCellValue(LISTVIEW_TRSD_TABLE_OPTIONAL, count,0);
					String entityname = formObject.getTableCellValue(LISTVIEW_TRSD_TABLE_OPTIONAL, count,1);
					String screeningType = formObject.getTableCellValue(LISTVIEW_TRSD_TABLE_OPTIONAL, count,2);
					String executionStatus = formObject.getTableCellValue(LISTVIEW_TRSD_TABLE_OPTIONAL, count,12);
					trsdlistStatusmap.put(entityname, executionStatus);
					trsdOptionalListMap.put(entityname.trim(),Integer.toString(count));//add(entityName);
				}	
				log.info("TRSDOPTIONAL ARRAY LIST :: "+trsdOptionalListMap);
				 if("IFP".equalsIgnoreCase(requestCategory)||"IFA".equalsIgnoreCase(requestCategory) ||"SCF".equalsIgnoreCase(requestCategory)){  //ATP - 204,205
					String processingSystem = formObject.getValue(PROCESSING_SYSTEM).toString();
					if(processingSystem.equalsIgnoreCase("T")){
						log.info("CHECK 6 ");
						int length = getGridCount(Q_TSLm_Counter_Dets);
						for(int i=0;i<length;i++){
							String checkbox = formObject.getTableCellValue(Q_TSLm_Counter_Dets, i, 4);
							String entityName = formObject.getTableCellValue(Q_TSLm_Counter_Dets, i, 1).trim();
							String partycountry = formObject.getTableCellValue(Q_TSLm_Counter_Dets, i, 2); //Added by shivanshu 03-07-2023
							if(checkbox.equalsIgnoreCase(TRUE)){
								log.info(" TRUE CASE trsdOptionalListMap.keySet() ::" +trsdOptionalListMap);
								log.info(" TRUE CASE entityName ::" +entityName);
								log.info(" TRUE CASE contains.keySet() ::" +
								trsdOptionalListMap.containsKey(entityName));
								if(!(trsdOptionalListMap.containsKey(entityName))){
									log.info(" TRUE CASE ADDING VALUE ::" +entityName);
									addDataEntity("DRAWER/SELLER",entityName.trim(),jsonArray);
									addDataEntity("DRAWEE/BUYER COUNTRY",partycountry,jsonArray);  //Added by shivanshu 03-07-2023

								}
							}else{// CHECK IF ROW'S TRSD HAPPEND OR NOT , IF NOT THEN DELETE
								log.info("FALSE CASE trsdOptionalListMap.keySet() ::" +trsdOptionalListMap.keySet());
								log.info("FASLE CASE entityName ::" +entityName);
								if(trsdOptionalListMap.containsKey(entityName)){//trsdOptionalListMap.keySet().contains(entityName)){
									String rowid = trsdOptionalListMap.get(entityName);
									if(trsdlistStatusmap.get(entityName).equalsIgnoreCase("N")){
										deleteRowList.add(rowid);
										}
								}
							}
						}
					}
					formObject.addDataToGrid(LISTVIEW_TRSD_TABLE_OPTIONAL,jsonArray);
				}else if("IFS".equalsIgnoreCase(requestCategory)){
					log.info("CHECK 7 ");
					String processingSystem = formObject.getValue(PROCESSING_SYSTEM).toString();
					if(processingSystem.equalsIgnoreCase("T")){
						log.info("CHECK 8 ");
						int length = getGridCount(Q_TSLm_Counter_Dets);
						for(int i=0;i<length;i++){
							String checkbox = formObject.getTableCellValue(Q_TSLm_Counter_Dets, i, 4);
							String entityName = formObject.getTableCellValue(Q_TSLm_Counter_Dets, i, 1).trim();
							String partycountry = formObject.getTableCellValue(Q_TSLm_Counter_Dets, i, 2); //Added by shivanshu 03-07-2023
							if(checkbox.equalsIgnoreCase(TRUE)){
								log.info(" TRUE CASE trsdOptionalListMap.keySet() ::" +trsdOptionalListMap);
								log.info(" TRUE CASE entityName ::" +entityName);
								log.info(" TRUE CASE contains.keySet() ::" +
								trsdOptionalListMap.containsKey(entityName));
								if(!(trsdOptionalListMap.containsKey(entityName))){
									log.info(" TRUE CASE ADDING VALUE ::" +entityName);
									addDataEntity("DRAWEE/BUYER",entityName,jsonArray);
								}
							}else{// CHECK IF ROW'S TRSD HAPPEND OR NOT , IF NOT THEN DELETE
								log.info("FALSE CASE trsdOptionalListMap.keySet() ::" +trsdOptionalListMap.keySet());
								log.info("FASLE CASE entityName ::" +entityName);
								if(trsdOptionalListMap.containsKey(entityName)){//trsdOptionalListMap.keySet().contains(entityName)){
									String rowid = trsdOptionalListMap.get(entityName);
									if(trsdlistStatusmap.get(entityName).equalsIgnoreCase("N")){
										deleteRowList.add(rowid);}
								}
							}
						}
					}
					formObject.addDataToGrid(LISTVIEW_TRSD_TABLE_OPTIONAL,jsonArray);
				 }
				 log.info("jsonArray= "+jsonArray);
				 log.info("deleteRowsAarray numbers  "+deleteRowList);
				
				 if(deleteRowList.size()>0){
					log.info("deleteRowsAarray "+deleteRowList.size());
					int[] deleteRowsAarray=new int[deleteRowList.size()];
					for(int i=0;i<deleteRowList.size();i++){
						int p = Integer.parseInt((String) deleteRowList.get(i));
						deleteRowsAarray[i]=p;
					}
					log.info("After for loop deleteRowsAarray "+deleteRowList.size());
					formObject.deleteRowsFromGrid(LISTVIEW_TRSD_TABLE_OPTIONAL,deleteRowsAarray);
				}
				addMandatoryScreening(); //ATP-481 --JAMSHED 13-JUN-2024
			}
		}catch(Exception e){
			log.error("Error in  insertIntoTrsdtable FUNCTION " + e);
		}
	}
	
	private void addDataIntoTRSD(String partyOrVessel,List<List<String>> partyDetails){
		try{
			List<String> listData=null;
			log.info("partyOrVessel 11"+partyOrVessel);

			JSONArray jsonArray = new JSONArray();
			Map<String,String> partyDescMap=new HashMap<String,String>();
			if(partyOrVessel.equalsIgnoreCase("party")||partyOrVessel.equalsIgnoreCase("partyAfterTrsd")){
				log.info("trsdPartyMap="+trsdPartyMap);
				log.info("CHECK 11");
				if (trsdPartyMap.containsKey(requestCategory)) {
					listData=trsdPartyMap.get(requestCategory);
				}else{
					listData = new ArrayList<String>();
				}
				if("GRNT".equalsIgnoreCase(requestCategory)||"SBLC".equalsIgnoreCase(requestCategory)){//RR
					String thirdPartyValue=formObject.getValue(TRN_THIRD_PARTY).toString();
					String productType=formObject.getValue(PRODUCT_TYPE).toString();
					log.info("modeOfGurantee="+modeOfGurantee+"thirdPartyValue="+thirdPartyValue+"productType="+productType);
					if("Guarantee Advised Thru Other Bank".equalsIgnoreCase(modeOfGurantee)
							||"Counter Guarantee".equalsIgnoreCase(modeOfGurantee)){
						listData.add("ABK");
					}
					if("1".equalsIgnoreCase(thirdPartyValue))  //1 yes
					{   
						listData.add("ACC");
					}
					if(productType.contains("T5")){
						listData.add("APB");
					}

				}else if("ELC".equalsIgnoreCase(requestCategory)&&("ELC_SLCA".equalsIgnoreCase(requestType)||"ELC_SLCAA".equalsIgnoreCase(requestType)
						||"ELC_SER".equalsIgnoreCase(requestType)||"ELC_SCL".equalsIgnoreCase(requestType))){//RR
					String thirdPartyValue=formObject.getValue(TRN_THIRD_PARTY).toString();
					String productType=formObject.getValue(PRODUCT_TYPE).toString();
					log.info("modeOfGurantee="+modeOfGurantee+"thirdPartyValue="+thirdPartyValue+"productType="+productType);
					if("Guarantee Advised Thru Other Bank".equalsIgnoreCase(modeOfGurantee)
							||"Counter Guarantee".equalsIgnoreCase(modeOfGurantee)){
						listData.add("ABK");
					}
					if("1".equalsIgnoreCase(thirdPartyValue))  //1 yes
					{   
						listData.add("ACC");
					}
					if(productType.contains("T5")){
						listData.add("APB");
					}

				} else if("ILC".equalsIgnoreCase(requestCategory)){
					String thirdPartyValue=formObject.getValue(TRN_THIRD_PARTY).toString();
					if("1".equalsIgnoreCase(thirdPartyValue))  //1 yes
					{   
						listData.add("ACC");
					}
				} else if("SG_NILC".equalsIgnoreCase(requestType)){
					listData.add("APP");
					listData.add("BEN");
					listData.add("ABK");
				} /*else if("SG_NIC".equalsIgnoreCase(requestType)){
					listData.add("DRAWER");
					listData.add("DRAWEE");
				}*/
			}
			log.info("listData="+listData);

			if("vessel".equalsIgnoreCase(partyOrVessel)||"party".equalsIgnoreCase(partyOrVessel)){
				for(int counter = 0; counter <partyDetails.size(); counter++){
					String party =  (String) ((List) partyDetails.get(counter)).get(0);
					log.info("party "+party);
					log.info(" 13/01/22 :partyOrVessel :"+partyOrVessel);
					boolean addflag=false;
					String trsdCategory="";
					if(partyOrVessel.equalsIgnoreCase("party")&&listData.contains(party)){
						log.info(" 13/01/22 :partyOrVessel 11 :"+partyOrVessel);
						addflag=true;
						trsdCategory="Party";
						log.info(" 13/01/22 :partyOrVessel  22:"+trsdCategory);
					} else if(partyOrVessel.equalsIgnoreCase("party")){
						log.info(" 13/01/22 :partyOrVessel :"+partyOrVessel);
						addflag=false;
					} else {
						addflag=true;
						trsdCategory="Vessel";
						log.info(" 13/01/22 :trsdCategory 33 :"+trsdCategory);
					}
					String entity =  (String) ((List) partyDetails.get(counter)).get(1);
					String EntityName =  (String) ((List) partyDetails.get(counter)).get(2);
					String screeningType = (String) ((List) partyDetails.get(counter)).get(3);
					
					if(addflag){
						jsonArray.addAll(addVesselOwner(entity,EntityName,screeningType,trsdCategory));
						/*	JSONObject childGridRowObject=new JSONObject();
						childGridRowObject.put("Entity", entity);
						childGridRowObject.put("Entity_Name", EntityName);
						childGridRowObject.put("TRSD_Screening_Type", screeningType);
						childGridRowObject.put("EXECUTION_STATUS","N");
						childGridRowObject.put("TRSD_CATEGORY",trsdCategory);
						log.info("END OF  insertIntoTrsdtable FUNCTION "+childGridRowObject.toString());
						jsonArray.add(childGridRowObject);*/
						//addflag=false;
					}
					/*if("vessel".equalsIgnoreCase(partyOrVessel)){
						String query ="select relationship_type,(select relationtype from tfo_dj_lli_relationship_mast where relationcode=relationship_type and rownum=1) "
								+ "as entity,(upper(vessel_name)||'/'||vessel_id) as entity_type,'Organisation' as screening_type from tfo_dj_lli_ownership_dtls  "
								+ "where wi_name='"+sWorkitemID+"'  and upper(vessel_name)=upper('"+EntityName+"')";
						log.info("query="+query);
						List<List<String>> listData1 = formObject.getDataFromDB(query);
						for(int counter1 = 0; counter1 <listData1.size(); counter1++){
							JSONObject childGridRowObject=new JSONObject();
							entity =  (String) ((List) listData1.get(counter1)).get(1);
							EntityName =  (String) ((List) listData1.get(counter1)).get(2);
							screeningType =  (String) ((List) listData1.get(counter1)).get(3);
							childGridRowObject.put("Entity", entity);
							childGridRowObject.put("Entity_Name", EntityName);
							childGridRowObject.put("TRSD_Screening_Type",screeningType);
							childGridRowObject.put("TRSD_Screening_Type",screeningType);
							childGridRowObject.put("EXECUTION_STATUS","N");
							childGridRowObject.put("TRSD_CATEGORY","Owner");
							log.info("END OF  insertIntoTrsdtable FUNCTION "+childGridRowObject.toString());
							jsonArray.add(childGridRowObject);
						}

					}*/
				}
				log.info("END OF Insert Into Trsdtable FUNCTION "+jsonArray.toString());
				formObject.addDataToGrid("TRSD_TABLE",jsonArray);
			}else if("partyAfterTrsd".equalsIgnoreCase(partyOrVessel)){
				Map<String, HashMap<String, ArrayList<String>>> trsdListMap=new HashMap<String,HashMap<String,ArrayList<String>>>();
				List deleteRowList=new ArrayList<>();
				JSONArray jsonArrayNew =  formObject.getDataFromGrid(LISTVIEW_TRSD_TABLE);
				log.info("jsonArrayNew "+jsonArrayNew.toString());
				log.info("jsonArrayNew "+jsonArrayNew.size());

				for(int count = 0 ; count < jsonArrayNew.size() ; count++)
				{	
					String entity = formObject.getTableCellValue(LISTVIEW_TRSD_TABLE, count,0);
					String entityName = formObject.getTableCellValue(LISTVIEW_TRSD_TABLE, count,2).trim();
					String trsdCategory = formObject.getTableCellValue(LISTVIEW_TRSD_TABLE, count,1);
					log.info("trsdCategory= "+trsdCategory);
					if(!"Party".equalsIgnoreCase(trsdCategory)){
						String a[]=trsdCategory.split("\\(");
						log.info("vesselName "+a[1].substring(0,a[1].length()-1).trim());//a[1].length()-1.trim());
						trsdCategory="Vessel";
						String entityNew=entity;
						entity=a[1].substring(0,a[1].length()-1).trim();//(a[1].length()-1).trim();
						entityName=entityNew;
					}	
					
					ArrayList list;
					log.info("partyDesc="+entity+"customerName="+entityName+"trsdCategory="+trsdCategory);
					log.info("customerName "+entityName);
					if(trsdListMap.containsKey(trsdCategory)){
						HashMap innerhashMap=trsdListMap.get(trsdCategory);
						if(innerhashMap.containsKey(entity)){
							list=(ArrayList) innerhashMap.get(entity);
						}else{
							list=new ArrayList<String>();
						}
						list.add(entityName.trim());
						list.add(count);
						innerhashMap.put(entity,list);
						trsdListMap.put(trsdCategory,innerhashMap);
					} else {
						HashMap innerhashMap=new HashMap();
						list=new ArrayList<String>();
						list.add(entityName.trim());
						list.add(count);
						innerhashMap.put(entity,list);
						trsdListMap.put(trsdCategory,innerhashMap);
					}
				}
				log.info("trsdListMap "+trsdListMap);
				HashMap<String,ArrayList<String>> trsdpartyMapNew=trsdListMap.get("Party");
				HashMap<String,ArrayList<String>> trsdVesselMap=trsdListMap.get("Vessel");
				List partyList=new ArrayList<String>();
				for(int counter = 0; counter <partyDetails.size(); counter++){
					log.info("partyType "+(String) ((List) partyDetails.get(counter)).get(0));
					String partyDesc=(String) ((List) partyDetails.get(counter)).get(1); //partydesc
					String customerName=(String) ((List) partyDetails.get(counter)).get(2); //customerName
					partyList.add(partyDesc);
					if(listData.contains((String) ((List) partyDetails.get(counter)).get(0))){
						log.info("partyDesc "+trsdpartyMapNew);
						log.info("customerName "+customerName);
						log.info("partyDesc "+partyDesc);
						if(!trsdpartyMapNew.isEmpty() && trsdpartyMapNew.containsKey(partyDesc)){
							List list=(List) trsdpartyMapNew.get(partyDesc);
							String trsdCustomerName=(String) list.get(0);
							log.info("trsdCustomerName= "+trsdCustomerName);
							log.info("customerName= "+customerName);
							if(!customerName.trim().equalsIgnoreCase(trsdCustomerName.trim())){
								deleteRowList.add(list.get(1));
								jsonArray.addAll(addVesselOwner(partyDesc, customerName, "Organisation", "Party"));
								/*	JSONObject childGridRowObject=new JSONObject();
								childGridRowObject.put("Entity", partyDesc);
								childGridRowObject.put("Entity_Name", customerName);
								childGridRowObject.put("TRSD_Screening_Type","Organisation");
								childGridRowObject.put("EXECUTION_STATUS","N");
								childGridRowObject.put("TRSD_CATEGORY","Party");
								log.info("END OF  insertIntoTrsdtable FUNCTION "+childGridRowObject.toString());
								jsonArray.add(childGridRowObject);*/
							}
						}else{
							jsonArray.addAll(addVesselOwner( partyDesc, customerName, "Organisation", "Party"));
							//formObject.deleteRowsFromGrid(LISTVIEW_TRSD_TABLE,a);
							/*	JSONObject childGridRowObject=new JSONObject();
							childGridRowObject.put("Entity", partyDesc);
							childGridRowObject.put("Entity_Name", customerName);
							childGridRowObject.put("TRSD_Screening_Type","Organisation");
							childGridRowObject.put("EXECUTION_STATUS","N");
							childGridRowObject.put("TRSD_CATEGORY","Party");
							log.info("END OF  insertIntoTrsdtable FUNCTION "+childGridRowObject.toString());
							jsonArray.add(childGridRowObject);*/
						}
					}
				}
				log.info("END OF  partyList FUNCTION "+partyList);
				log.info("Check 1.");
				//checking for deletion of any party
				if(trsdpartyMapNew != null && trsdpartyMapNew.size()>0){
					log.info("trsdpartyMapNew.keySet(). :: " +trsdpartyMapNew.keySet());
					for(String key:trsdpartyMapNew.keySet()){
						if(partyList.size()>0 && !partyList.contains(key)){
							log.info("END OF key "+key);
							ArrayList list=trsdpartyMapNew.get(key);
							log.info("list "+list);
							deleteRowList.add(list.get(1));
						}
					}
				}
				log.info("After trsdpartyMapNew Map condition.");
				
				//checking for any new vessel added 
				List vesselNameList=new ArrayList<String>();
				String query = "select  vessel_flg,'Vessel Name' as entity ,upper(vessel_name) as entity_type ,"
						+ "'Vessel' as screening_type from TFO_DJ_LLI_VESSEL_DTLS where wi_name='"+sWorkitemID+"'";
				List<List<String>> listData1 = formObject.getDataFromDB(query);
				for(int counter1 = 0; counter1 <listData1.size(); counter1++){
					String entity =  (String) ((List) listData1.get(counter1)).get(1);
					String vesselName =  (String) ((List) listData1.get(counter1)).get(2);
					String screeningType =  (String) ((List) listData1.get(counter1)).get(3);
					vesselNameList.add(vesselName);
					log.info("vesselName"+vesselName);
					log.info("trsdVesselMap="+trsdVesselMap);
					if(trsdVesselMap!=null){
						log.info("vessel exist or not"+trsdVesselMap.containsKey(vesselName));
						if(!trsdVesselMap.containsKey(vesselName)){
							jsonArray.addAll(addVesselOwner(entity,vesselName,screeningType,"Vessel"));
						}
					}else{
						jsonArray.addAll(addVesselOwner(entity,vesselName,screeningType,"Vessel"));
					}
				}
				//checking for deletion of any vessel
				if(trsdVesselMap!=null){
					for(String key:trsdVesselMap.keySet()){
						if(!vesselNameList.contains(key)){
							log.info("END OF key "+key);
							ArrayList list=trsdVesselMap.get(key);
							log.info("list "+list);

							for(int i=1;i<list.size();i++)
							{
								log.info("value i="+i+list.get(i));
								deleteRowList.add(list.get(i));
								i=i+1;
							}
						}
					}
				}
				log.info("jsonArray= "+jsonArray);
				log.info("deleteRowsAarray "+deleteRowList.size());
				if(deleteRowList.size()>0){
					int[] deleteRowsAarray=new int[deleteRowList.size()];
					for(int i=0;i<deleteRowList.size();i++){
						log.info("END OF  insertIntoTrsdtable FUNCTION "+deleteRowList.get(i));
						deleteRowsAarray[i]=(int) (deleteRowList.get(i));
					}
					formObject.deleteRowsFromGrid(LISTVIEW_TRSD_TABLE,deleteRowsAarray);
				}
				formObject.addDataToGrid("TRSD_TABLE",jsonArray); 
			}
		}
		catch(Exception e){
			log.error("Exception in addDataIntoTRSD",e);
		}
	}

	private JSONArray addVesselOwner(String entity,String entityName,String screeningType,String trsdCategory){
		JSONArray jsonArray=new JSONArray();
		entityName =entityName.trim();
		try{
			JSONObject childGridRowObject=new JSONObject();
			log.info("entity="+entity);
			log.info("entityName="+entityName);
			log.info("addVesselOwner 13/01/22 : wi :"+sWorkitemID);
			log.info("addVesselOwner 13/01/22 : trsdCategory :"+trsdCategory);
			if("Vessel".equalsIgnoreCase(trsdCategory)){
				childGridRowObject.put("Entity", entity);
				childGridRowObject.put("Entity_Name", entityName);
				childGridRowObject.put("TRSD_Screening_Type", screeningType);
				childGridRowObject.put("EXECUTION_STATUS","N");
				childGridRowObject.put("TRSD_CATEGORY",trsdCategory+" -("+entityName+")");
				jsonArray.add(childGridRowObject);
				String query ="select relationship_type,(select relationtype from tfo_dj_lli_relationship_mast where relationcode=relationship_type and rownum=1) "
						+ "as entity,company_name as entity_type,'Organisation' as screening_type from tfo_dj_lli_ownership_dtls  "
						+ "where wi_name='"+sWorkitemID+"' and  relationship_type in ('BO','CO','IM','RO','TM') and upper(vessel_name)=upper('"+entityName+"')";
				log.info("query="+query);
				List<List<String>> listData1 = formObject.getDataFromDB(query);
				for(int counter1 = 0; counter1 <listData1.size(); counter1++){
					childGridRowObject=new JSONObject();
					entity =  (String) ((List) listData1.get(counter1)).get(1);
					String name =  (String) ((List) listData1.get(counter1)).get(2);
					screeningType =  (String) ((List) listData1.get(counter1)).get(3);
					childGridRowObject.put("Entity", entity);
					childGridRowObject.put("Entity_Name", name.trim());
					childGridRowObject.put("TRSD_Screening_Type",screeningType);
					childGridRowObject.put("TRSD_Screening_Type",screeningType);
					childGridRowObject.put("EXECUTION_STATUS","N");
					childGridRowObject.put("TRSD_CATEGORY","Owner"+" -("+entityName+")");
					log.info("END OF  insertIntoTrsdtable FUNCTION "+childGridRowObject.toString());
					jsonArray.add(childGridRowObject);
				}
			} else if(!(entityName.equalsIgnoreCase("") || entityName == null ||entityName.equalsIgnoreCase("null")) 
					&& !(entity.equalsIgnoreCase("") || entity == null || entity.equalsIgnoreCase("null")) ){//15/12/21 only condition //15/12/21
				log.info("addVesselOwner 13/01/22 : wi Inside COndition :"+sWorkitemID);
				log.info("addVesselOwner 29/12/21 : wi :"+sWorkitemID);
				childGridRowObject.put("Entity", entity);
				childGridRowObject.put("Entity_Name", entityName.trim());
				childGridRowObject.put("TRSD_Screening_Type", screeningType);
				childGridRowObject.put("EXECUTION_STATUS","N");
				childGridRowObject.put("TRSD_CATEGORY",trsdCategory);
				log.info("addVesselOwner 29/12/21"+"wi :"+sWorkitemID+ ": childGridRowObject :"+childGridRowObject);
				jsonArray.add(childGridRowObject);
			}

		}
		catch(Exception e){
			log.error("Exception in addDataIntoTRSD",e);
		}
		return jsonArray;
	}
	
	public String callTRSDService(String controlName)
	{
		String query="";
		List<List<String>> sOutputlist =null;
		String output="";
		int count = 0;
		try
		{
			refreshData();
			String trsdWI=formObject.getValue(TRSD_WI_NAME).toString();
			if(trsdWI==null ||"".equalsIgnoreCase(trsdWI)){
				query="select max(count_1) from( select count(1) as count_1 from tfo_dj_trsd_screening_details where  winame='"+sWorkitemID+"'"
					 +" union"
					+ " select count(1) as count_1 from tfo_dj_trsd_screening_other_details where winame='"+sWorkitemID+"') a";
				log.info("queryTrsd=" + query);
				sOutputlist=formObject.getDataFromDB(query);
				log.info("sOutputlist= " + sOutputlist);
				if(!sOutputlist.get(0).get(0).equalsIgnoreCase("")){
					count = Integer.parseInt(sOutputlist.get(0).get(0).toString());
				}
				if(count==0){
					return getReturnMessage(false,controlName,controlName+"### Please save the TRSD Data");	
				}
			}
			
			sOutputlist = formObject.getDataFromDB("SELECT FSK_SEQ.nextval as IDValue from DUAL");
			String txnNumber= sOutputlist.get(0).get(0);
			output=callTRSD(txnNumber);
			log.info("output= " + output);
			XMLParser xpOuter = new XMLParser(output);
			String sAccCASA = "", sAccType = "";
			String sReturnCode = xpOuter.getValueOf("StatusCode");
			String trsdWorkitemNumber = xpOuter.getValueOf("WINumber");
			if("0".equalsIgnoreCase(sReturnCode)){
				formObject.setValue(TRSD_WI_NAME, trsdWorkitemNumber);
				return getReturnMessage(true,controlName,"showTRSDJSP"+"#"+sUserIndex);
			} else {
					// return getReturnMessage(false,controlName,controlName+"### TRSD not working");
				    return getReturnMessage(false,controlName,controlName+"### Screening in Progress Please recheck after sometime");
				}
		}catch(Exception e){
			log.error("Exception in createNewWorkitemELCB",e);
		}
		return getReturnMessage(true,controlName,"showTRSDJSP"+"#"+sUserIndex);
	}
	

	private boolean checkTRSDValidation(){
		try{
			String isReq = "";
			String key=requestCategory+sActivityName+"TR";
			/*String sQuery = "select is_req from tfo_dj_tab_handling_mast where  request_category='"+requestCategory+"' "
					+ "and activity_name='PROCESSING MAKER' and tab_name='TR'";
			log.info("Query "+sQuery);
			List<List<String>> recordList = formObject.getDataFromDB(sQuery);
			log.info("Final List "+recordList);
			if(recordList !=null && !recordList.isEmpty()){
				isReq=recordList.get(0).get(0);
			}*/
			isReq=tabHandlingMap.get(key);
			log.info("isReq="+isReq);
			if("Y".equalsIgnoreCase(isReq)){
				String trsdFlag = formObject.getValue(TRSD_FLAG).toString();
				if(pmCompReferalPopulationControl()){ //Checking TRSD required or not
					insertIntoTrsdtable();
					LoadListViewOfVesselScreening();  //added date 10-07-2023
					if(!formObject.getValue(PROCESS_TYPE).toString().equalsIgnoreCase("TSLM Front End")){
						if(!(trsdFlag.equalsIgnoreCase("N")||trsdFlag.equalsIgnoreCase("A"))){  //N means no trsd found,A= means trsd found and approved 
							//insertIntoTrsdtable();
							sendMessageHashMap("SKIP_TREASRY_FLAG", "Please complete the cases pending in Screening tab");
							return false;
						}
					}
				}
			}

		}catch(Exception e){
			log.error("Exception in createNewWorkitemELCB",e);
		}
		return true;
	}

	public void formLoadDataPM(){
		try {
			setProperties();
			intializeStaticValue();
			loadIntCodeDeconLoad();
			decisionValidation(DEC_CODE);
			loadProdLov(srcChnl, relType, listPrdCode1, PRODUCT_TYPE);
			if(!formObject.getValue(PROCESS_TYPE).toString().equalsIgnoreCase("TSLM Front End")){
			setBranchCodeAtPm();
			}
			pmPcFieldFrmOnLoad(requestType);
			tabHandlingMast();
			finalDecisionHandlingPM(DEC_CODE, REJ_RESN_PPM);
			compCheckTab();
			populateComplianceTab();
			loadComplianceTabDataWrap();//loadComplianceTabData(); //06/12/21 By Kishan
			inputTabFrmHideShow();
			setLoadRefTRVal(EVENT_TYPE_LOAD, "");
			duplicateCheckConfirmation(DUP_CHK_CONFIRMATION, false);
			loadPartyDetailsField();
			getModeOfGuarantee();
			disableControls(DISABLE_SWIFT_FIELDS);
			setAmendmentList(AMEND_TYPE);
			setOperationCode();
			loadBillProductCode();
			setCreateAMendLov();
			loadCheckAmendField();
			changeFCUBSContractNumber(FCUBS_CON_NO);
			setLimitPartyType(LIMIT_PARTY_TYPE);
			setContractLimits();
			disableTabsForSwift();
			setGoodDescription();
			setSelectToEmptyLov();
			disableControls(DISABLE_LLI_TXT_FIELDS);
			setLovSelect();
			disableInitCustFrm(); 
			loadAccountNumberAtPM();
			changeCreateAmendField();
			setBranchCode(COMBOX_BRN_CODE,true);
			duplicateCheckConfirmation(TSLM_INVOICE_CHK_CONFIRM, false);	//code by MOKSH
			if(!formObject.getValue(PROCESS_TYPE).toString().equalsIgnoreCase("PT")){
				removeItemFromCombo(SOURCE_CHANNEL, "IPT");
			}
			if(!formObject.getValue(PROCESS_TYPE).toString().equalsIgnoreCase("ET")){  //ATP-469 Shahbaz 04-06-2024
				removeItemFromCombo(SOURCE_CHANNEL, "IET");
			}
			setPartyDesciption(); //sheenu
			showAmendFrameFieldsPM(); //
			SetDefaultValAnyDoc_SBLC_AM();//santhosh
			setAmendFrameDataPM();
			//setTrsdFlag();
			setDataInTRSDTab(); 
			checkSummaryDataForEmail(); //Shivanshu
			setTsRequired();  //Traydstream |atp-520|reyaz|04-11-2024
			log.info("formObject.setValue(OPERATION_CODE) After java Loading :: " +formObject.getValue(OPERATION_CODE).toString());
		} catch (Exception e) {
			log.error("Exception in formLoadDataPM",e);
		}
	}

	
	/*private void enableDisableTRSDButton(){
		try{
			boolean enableFlag=false;
			JSONArray jsonArrayNew =  formObject.getDataFromGrid(LISTVIEW_TRSD_TABLE);
			JSONArray jsonArrayOptional =  formObject.getDataFromGrid(LISTVIEW_TRSD_TABLE_OPTIONAL);

			log.info("jsonArrayNew :"+jsonArrayNew.toString());
			log.info("jsonArrayOptional: "+jsonArrayOptional.toString());

			for(int count = 0 ; count < jsonArrayNew.size() ; count++)
			{	
				String executionStatus = formObject.getTableCellValue(LISTVIEW_TRSD_TABLE, count,13);
				if(!"Y".equalsIgnoreCase(executionStatus)){
					enableFlag=true;
					break;
				}
			}
			for(int count = 0 ; count < jsonArrayOptional.size() ; count++)
			{	
				String executionStatus = formObject.getTableCellValue(LISTVIEW_TRSD_TABLE_OPTIONAL, count,12);
				log.info("trsd screening executionStatus= "+executionStatus);
				if(!"Y".equalsIgnoreCase(executionStatus)){
					enableFlag=true;
					break;
				}
			}
			if(enableFlag){
				enableFieldOnDemand(BTN_START_TRSD);
			}else{
				disableFieldOnDemand(BTN_START_TRSD);
			}
		}catch(Exception e){
			log.error("Exception in enableDisableTRSDButton",e);
		}
	}*/

	private void fillLoanRefrenceListView(){   //BY KISHAN TSLM
		log.info("INSIDE fillLoanRefrenceListView ");
		String Request_CAT = formObject.getValue(REQUEST_CATEGORY).toString();
		String Request_TYPE = formObject.getValue(REQUEST_TYPE).toString();
		String Processing_System = formObject.getValue(PROCESSING_SYSTEM).toString();
		String createAmendValue = formObject.getValue("CREATE_AMEND_CNTRCT_FCUBS").toString();
		String processType=formObject.getValue(PROCESS_TYPE).toString();
		String loadNo=formObject.getValue("TRANSACTION_REFERENCE").toString();
		log.info("processType:  "+processType+" Request_CAT :"+Request_CAT+" Request_TYPE :"+Request_TYPE+" loadNo : "+loadNo);
		
		if (((Request_CAT.equalsIgnoreCase("IFA") || Request_CAT.equalsIgnoreCase("IFS") ||Request_CAT.equalsIgnoreCase("IFP")  ||Request_CAT.equalsIgnoreCase("SCF"))  //ATP - 204,205
				&& (Request_TYPE.equalsIgnoreCase("LD") || Request_TYPE.equalsIgnoreCase("PD")  || Request_TYPE.equalsIgnoreCase("PDD")) && Processing_System.equalsIgnoreCase("T"))
				||(Request_CAT.equalsIgnoreCase("IFA")&&Request_TYPE.equalsIgnoreCase("IFA_CTP")&&processType.equalsIgnoreCase("TSLM Front End"))) {
			//formObject.setStyle("TAB_TSLM_LOAN_REF", DISABLE, FALSE);
			
			if (createAmendValue.equalsIgnoreCase("N")||(Request_CAT.equalsIgnoreCase("IFA")&&Request_TYPE.equalsIgnoreCase("IFA_CTP")&&processType.equalsIgnoreCase("TSLM Front End"))) {
				try {
					int counterPartyCount = getGridCount("Q_TSLm_Counter_Dets");
					JSONArray jsonArrayCounterParty = new JSONArray();
					int Counter  = 1;
					for (int i = 0; i < counterPartyCount; i++) {
						String Selected = "";
						Selected = formObject.getTableCellValue("Q_TSLm_Counter_Dets", i, 4);//CheckBox Value
						if(Selected.equalsIgnoreCase("true")){
							String counterPartyList = "";
							JSONObject jsonObjectCounter = new JSONObject();
							counterPartyList = formObject.getTableCellValue("Q_TSLm_Counter_Dets", i, 0);//SrNo
							log.info("counterPartyListData " + counterPartyList);
							jsonObjectCounter.put("SrNo",Counter );
							jsonObjectCounter.put("Counter Party CID",counterPartyList );
							if(Request_CAT.equalsIgnoreCase("IFA")&&Request_TYPE.equalsIgnoreCase("IFA_CTP")&&processType.equalsIgnoreCase("TSLM Front End")){
								jsonObjectCounter.put("TSLMloanref",loadNo );
							}
							
							jsonArrayCounterParty.add(jsonObjectCounter);
							Counter++;
						}
					}
					formObject.clearTable("TAB_TSLM_LOAN_REF");
					log.info("jsonArrayCounterParty Size:: "+ jsonArrayCounterParty.size());
					formObject.addDataToGrid("TAB_TSLM_LOAN_REF",jsonArrayCounterParty);
				} catch (Exception e) {
					log.error("Exception in enableDisableTRSDButton", e);
				}
			}else {
				formObject.setStyle("TAB_TSLM_LOAN_REF", DISABLE, TRUE);
				formObject.clearTable("TAB_TSLM_LOAN_REF");//commented for not deleting data(07-10-22) Ajeet
			}
		}else{
			formObject.setStyle("TAB_TSLM_LOAN_REF", DISABLE, TRUE);
			log.info("ELSE CONDITION OF fillLoanRefrenceListView ");
		}
		log.info("END fillLoanRefrenceListView ");
	}

	private boolean validateLoanReference(){  //BY KISHAN TSLM
		log.info("INSIDE validateLoanReference");
		String Req_CAT = formObject.getValue(REQUEST_CATEGORY).toString();
		String Req_TYPE = formObject.getValue(REQUEST_TYPE).toString();
		String Processing_System = formObject.getValue(PROCESSING_SYSTEM).toString();
		String createAmendValue = formObject.getValue(CREATE_AMEND_CNTRCT_FCUBS).toString();

		if (Req_CAT.equalsIgnoreCase("IFA")|| Req_CAT.equalsIgnoreCase("IFS")|| Req_CAT.equalsIgnoreCase("IFP") || Req_CAT.equalsIgnoreCase("SCF")){   ////ATP - 204,205
			if (Processing_System.equalsIgnoreCase("T")) {
				if ((Req_TYPE.equalsIgnoreCase("LD") || Req_TYPE.equalsIgnoreCase("PD") || Req_TYPE.equalsIgnoreCase("PDD")) && createAmendValue.equalsIgnoreCase("N")) {
					if(!checkLoanReference()){
						sendMessageHashMap("TAB_TSLM_LOAN_REF", "Please Fill data in TSLM Loan Reference Grid");
						return false;
					}
				}/*else if(Req_CAT.equalsIgnoreCase("IFA") && Req_TYPE.equalsIgnoreCase("IFA_CTP")
						&& (createAmendValue.equalsIgnoreCase("N") || createAmendValue.equalsIgnoreCase("Y"))){
					int loanCount = getGridCount("TAB_TSLM_LOAN_REF");
					if(loanCount == 0){
						sendMessageHashMap("TAB_TSLM_LOAN_REF", "Please Add data in TSLM Loan Reference Grid");
						return false;
					}
				}*/
				else if( Req_TYPE.equalsIgnoreCase("AM")
						&& (createAmendValue.equalsIgnoreCase("N") || createAmendValue.equalsIgnoreCase("Y"))){
					int loanCount = getGridCount("TAB_TSLM_LOAN_REF");
					if(loanCount == 0){
						sendMessageHashMap("TAB_TSLM_LOAN_REF", "Please Add data in TSLM Loan Reference Grid");
						return false;
					}
				}else if( Req_TYPE.equalsIgnoreCase("STL")
						&& (createAmendValue.equalsIgnoreCase("N") || createAmendValue.equalsIgnoreCase("Y"))){
					int loanCount = getGridCount("TAB_TSLM_LOAN_REF");
					if(loanCount == 0){
						sendMessageHashMap("TAB_TSLM_LOAN_REF", "Please Add data in TSLM Loan Reference Grid");
						return false;
					}
				}
			}
		}
		log.info("END validateLoanReference");
		return true;
	}

	private void addDataEntity(String entity,String entityName,JSONArray jsonArray){
		JSONObject childGridRowObject=new JSONObject();
		childGridRowObject.put("Entity", entity);
		childGridRowObject.put("Entity_Name",entityName.trim());
		childGridRowObject.put("TRSD_Screening_Type","Organisation");
		childGridRowObject.put("EXECUTION_STATUS","N");
		jsonArray.add(childGridRowObject);
	}
	
	private void removeDataEntity(String entity,String entityName,JSONArray jsonArray){
		/*JSONObject childGridRowObject=new JSONObject();
		childGridRowObject.put("Entity", entity);
		childGridRowObject.put("Entity_Name",entityName.trim());
		childGridRowObject.put("TRSD_Screening_Type","Organisation");
		childGridRowObject.put("EXECUTION_STATUS","N");*/
		log.info("Inside removeDataEntity ");
		for(int i =0; i<jsonArray.size(); i++){
			JSONObject childGridRowObject2 = (JSONObject) jsonArray.get(i);
			String name = (String) childGridRowObject2.get("Entity_Name");
			
			log.info("Values  ::" + childGridRowObject2 +" :: " + name);
			if(name.equalsIgnoreCase(entityName)){
				log.info("Index got : "+ i);
				log.info("Matched Case : "+ childGridRowObject2);
				jsonArray.remove(i); //Remove by Index
				break;
			}
		}
		log.info("End removeDataEntity ");
		//jsonArray.remove(jsonArray.indexOf(childGridRowObject));
	}

	private boolean  validateIsEntityTRSDExist(){
		try{
			int length = getGridCount(LISTVIEW_TRSD_TABLE_OPTIONAL);
			ArrayList tableArrayList =new ArrayList();
			ArrayList mandatoryEntityList= new ArrayList();
			log.info("in validateIsEntityTRSDExist");
			if(compCheckTab()){
				if("SG_NILC".equalsIgnoreCase(requestType)||"SG_NIC".equalsIgnoreCase(requestType)
						||"TL_LD".equalsIgnoreCase(requestType)
						||"DBA".equalsIgnoreCase(requestCategory)||"EC".equalsIgnoreCase(requestCategory)
						||"IFP".equalsIgnoreCase(requestCategory)||"IFS".equalsIgnoreCase(requestCategory)
						||"IFA".equalsIgnoreCase(requestCategory)
						||"SCF".equalsIgnoreCase(requestCategory)){   //ATP - 204,205

					for(int i=0;i<length;i++){ 
						tableArrayList.add(formObject.getTableCellValue(LISTVIEW_TRSD_TABLE_OPTIONAL, i, 0));
					}
				}
				if("SG_NILC".equalsIgnoreCase(requestType))
				{
					mandatoryEntityList.add("Shipping company name or customs authority for Air Way bill");
				}else if("SG_NIC".equalsIgnoreCase(requestType)){
					mandatoryEntityList.add("Shipping company name or customs authority for Air Way bill");
					mandatoryEntityList.add("DRAWER/SELLER");
					mandatoryEntityList.add("DRAWEE/BUYER");
				}else if("TL_LD".equalsIgnoreCase(requestType)){	//RR for tl_ld
					
					if("MRPA".equalsIgnoreCase(formObject.getValue(IFS_LOAN_GRP).toString())) 
							
					{
						mandatoryEntityList.add("GRANTOR NAME");
						mandatoryEntityList.add("OBLIGOR NAME");
					}
					else if("BC".equalsIgnoreCase(formObject.getValue(IFS_LOAN_GRP).toString())) 
					{
						mandatoryEntityList.add("GRANTOR NAME");
						mandatoryEntityList.add("OBLIGOR NAME");
					}
				}else if("DBA".equalsIgnoreCase(requestCategory)){
					mandatoryEntityList.add("DRAWEE/BUYER");
//				}else if("EC".equalsIgnoreCase(requestCategory)){
//					mandatoryEntityList.add("DRAWER/SELLER");
				}else if("IFS".equalsIgnoreCase(requestCategory)||"IFP".equalsIgnoreCase(requestCategory)
						||"IFA".equalsIgnoreCase(requestCategory) ||"SCF".equalsIgnoreCase(requestCategory)){  //ATP - 204,205
					mandatoryEntityList.add("DRAWER/SELLER");
					mandatoryEntityList.add("DRAWEE/BUYER");
				}

				log.info("in validateIsEntityTRSDExist"+tableArrayList.toString());
				log.info("in mandatoryEntityList"+mandatoryEntityList.toString());

				if(tableArrayList.size()>0){
					for(int i=0;i<mandatoryEntityList.size();i++){ 
						if(!tableArrayList.contains(mandatoryEntityList.get(i))){
							sendMessageHashMap("","Please add mandatory entity ("+mandatoryEntityList.toString()+"'");
							return false;
						}
					}
				} else if(mandatoryEntityList.size()>0){
					sendMessageHashMap("","Please add mandatory entity ("+mandatoryEntityList.toString()+"'");
					return false;
				}
			}}catch(Exception e){
				log.error("Exception in enableDisableTRSDButton",e);
			}
		return true;
	}

	private void setPartyDesciption(){ //sheenu
		try{
			Map partyTypeDesc = partyTypeMastMap.get(requestCategory);
			log.info("requestCategory in setPartyDesciption :"+requestCategory+partyTypeDesc.size());
			int length = getGridCount(LISTVIEW_PARTY);
			for(int i=0;i<length;i++){
				String partyDesc = formObject.getTableCellValue(LISTVIEW_PARTY, i, 1);
				log.info("Party desc before="+partyDesc);
				if((partyDesc == null || "".equalsIgnoreCase(partyDesc))&& partyTypeDesc.size()>0){
					String partyDescription = (String) partyTypeDesc.get(formObject.getTableCellValue(LISTVIEW_PARTY, i, 0));
					log.info("Party desc after="+partyDescription);
					formObject.setTableCellValue(LISTVIEW_PARTY, i, 1, partyDescription);
				}/*else{
					break;
				}*/
			}
		}catch(Exception e){
			log.error("Exception in setPartyDesciption",e);
		}
	}
	
	private boolean PMvalidateMandatoryPurposeMessage()  
	{
		if(("SBLC".equalsIgnoreCase(requestCategory) && "SBLC_NI".equalsIgnoreCase(requestType))
					|| ("ELC".equalsIgnoreCase(requestCategory) && "ELC_SLCA".equalsIgnoreCase(requestType))
					|| ("ILC".equalsIgnoreCase(requestCategory) && "ILC_UM".equalsIgnoreCase(requestType)))
	     {
			return validateMandatoryFieldsPM(FCUBS_PUR_OF_MSG,"Please select FCUBS Purpose of Message.");
		}else if(("GRNT".equalsIgnoreCase(requestCategory) && "NI".equalsIgnoreCase(requestType))){
			return validateMandatoryFieldsPM(PURPOSE_OF_MESSAGE,"Please select Purpose of Message.")
					&& validateMandatoryFieldsPM(FCUBS_PUR_OF_MSG,"Please select FCUBS Purpose of Message.");
		}
		return true;
	}
	private boolean PMDocumentValidation() {  //ADDED BY SHIVANSHU FOR DHL  
		if("ELCB_BL".equalsIgnoreCase(requestType) || "EC_BL".equalsIgnoreCase(requestType)){
			log.info("INSIDE courier AWB document alert ");
			int returnCode = getDocumentDHL();
			if (returnCode == -1 || returnCode == -3 ) {
				sendMessageHashMap(BUTTON_SUBMIT,"Attach AWB Courier Document");
				return false;
			}
			return true;
		}
		return true;
	}	
	private void showAmendFrameFieldsPM(){
		log.info("Inside showAmendFrameFieldsPM" );
		String control[] = null;
		try{
			if("ELC_LCAA".equalsIgnoreCase(requestType) ||"ELC_LCC".equalsIgnoreCase(requestType) ||"ILC_AM".equalsIgnoreCase(requestType)){
				control = AMEND_FRAME_FIELDS.split("#");
				//hideshowFrmInputTab(AMEND_FRAME_FIELDS,true);
			}else if("GRNT".equalsIgnoreCase(requestCategory) && "AM".equalsIgnoreCase(requestType)){
				control = GRNT_AM_AMEND_FRAME_FIELDS.split("#");
				//hideshowFrmInputTab(GRNT_AM_AMEND_FRAME_FIELDS,true);
            }else if(("ELC".equalsIgnoreCase(requestCategory) && "ELC_SLCAA".equalsIgnoreCase(requestType))){
				control = SBLC_AM_AMEND_FRAME_FIELDS.split("#");
			//	hideshowFrmInputTab(SBLC_AM_AMEND_FRAME_FIELDS,true);
            }
            else if(("SBLC".equalsIgnoreCase(requestCategory) && "SBLC_AM".equalsIgnoreCase(requestType))){
				control = SBLC_AM_ONLY_AMEND_FRAME_FIELDS.split("#");
			}
            else if(("GRNT".equalsIgnoreCase(requestCategory) && "GAA".equalsIgnoreCase(requestType))){
				control = GRNT_GAA_AMEND_FRAME_FIELDS.split("#");
				//hideshowFrmInputTab(GRNT_GAA_AMEND_FRAME_FIELDS,true);
            }
			for (int counter = 0; counter < control.length; counter++ ) {
				  String showValues="LABEL_"+control[counter]+"#"+"Q_Amendment_Data_USER_"+control[counter]+"#"+"Q_Amendment_Data_PT_"+control[counter]
						            +"#"+"Q_Amendment_Data_FC_"+control[counter]+"#"+"Q_Amendment_Data_FIN_"+control[counter];
				  log.info("showValues =" + showValues);
				  hideshowFrmInputTab(showValues,true);
			}
			log.info("END showAmendFrameFieldsPM" );
		}catch(Exception e){
			log.error("Exception in showAmendFrameFieldsPM",e);
		}
	}
	
	private String createNewWorkitemSBLC_CR(){ //PROTRADE_140
		String query="";
		List<List<String>> sOutputlist =null;
		String output="";
		String reqCat="SBLC";
		String reqType="SBLC_CS";
		
		try{
			if("SBLC_CR".equalsIgnoreCase(requestType)){
				query="select LINKED_WI_NAME from ext_tfo where wi_name='"+sWorkitemID+"'";
				log.info("createNewWorkitemSBLC_CR=" + query);
				sOutputlist=formObject.getDataFromDB(query);
				log.info("sOutputlist= " + sOutputlist);
				if(!sOutputlist.get(0).get(0).equalsIgnoreCase("")){
					output = sOutputlist.get(0).get(0).toString();
				}
				if("".equalsIgnoreCase(output)||"null".equalsIgnoreCase(output)){
					callChildWICreation(reqCat,reqType);

					/*sOutputlist = formObject.getDataFromDB("SELECT SEQ_WEBSERVICE.nextval as IDValue from DUAL");
					String txnNumber= sOutputlist.get(0).get(0);
					query="insert into tfo_dj_protrade_txn_data(requestmode, channelname,insertiondatetime,version,processname,process_type, channelrefnumber, sysrefno,"
							+"correlationid,wi_name, request_type, request_category, transaction_reference,INITIATOR_BRANCH,INITIATOR_USERID, "
							+" branch_city, assigned_center, requested_by, source_channel) "
							+ "values('C','LINK_WI',current_date,'1','TFO','BAU','"+txnNumber+"','"+txnNumber+"','"+txnNumber+"','"+winame+"','"
							+reqType+"','"+reqCat+"','"+trasactionRefernce+"','"+initatorBranch+"','"+initatorUserID+"','"
							+branchCity+"','"+assignedCenter+"','"+reqSubmitted+"','"+sourceChannel+"')";
					log.info("query="+query);
					int value=formObject.saveDataInDB(query);
					log.info(" output value="+value);

					handleCreateWorkitemOutput(createWorkitem(txnNumber));
					saveDecHistory("Notification","Child Workitem under claim settlement created successfully!");
					return "showNotificationAlert";*/
				}
			}
		}catch(Exception e){
			log.error("Exception in createNewWorkitemSBLC_CR",e);
		}
		return "false";
	}
	
	//santhosh
	public void SetDefaultValAnyDoc_SBLC_AM(){
		if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("SBLC_AM")){
			hideshowFrmInputTab(SBLC_AM_LC_FRAME_FIELDS,true);
			hideshowFrmInputTab(SBLC_AM_LC_FRAME_HIDE_FIELDS,false);
			disableControls(LC_DOC_COURIER);
		}
	}

	//Mansi 
		protected boolean checkDateValidationPM(String sUDate, String sUCntrDate){
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

	protected Boolean checkExpDateValidationPM(){
		log.info("Inside checkExpDateValidation");

		String msg="";
		String gteecntrexpdate=formObject.getValue(Q_AMENDMENT_DATA_USER_CNTR_GTEE_EXP_DATE).toString();
		String usercntrexpdate=formObject.getValue(Q_AMENDMENT_DATA_USER_EXPIRY_DATE).toString();

		if ("AM".equalsIgnoreCase(requestType)) {
			
			if(checkDateValidationPM(usercntrexpdate,gteecntrexpdate)) {
				log.info("Inside checkDateValidation for showing alert");
				msg=ALERT_CNTRGTEE_DATE;
				sendMessageHashMap(gteecntrexpdate,msg);
				log.info("getting message"+msg);
				return true;
			}
		}
		return false;
	}
	
	private void setTrsdFlag(){
		log.info("in setTrsdFlag");
		try{
			String query="select trsd_flag from ext_tfo where wi_name='"+sWorkitemID+"'";
			log.info("query REEGGG:"+query);
			List<List<String>> outputData= formObject.getDataFromDB(query);
			if(!outputData.isEmpty()){
				log.info("outputData.size(): "+outputData.size()+", outputData.get(0).get(0): "+
						outputData.get(0).get(0));
				formObject.setValue(TRSD_FLAG,outputData.get(0).get(0));
			}
		}catch(Exception e){
			log.error("Exception: ",e);
		}
	}
	
	private void loadComplianceTabDataWrap(){//06/12/21 BY KISHAN
		String enableSection = loadComplianceTabData();
		if(enableSection.equalsIgnoreCase("enableSection")){
			setSectionState("Frame15","E"); //06/12/21
			setSectionState(FRM_LLI,"E"); //06/12/21
		}
	}
	
	private void checkTrsdFlag(){
		log.info("in checkTrsdFlag"+formObject.getValue(TRSD_FLAG).toString());
		String output = "";
		boolean rejectStatus = false;
		boolean returnStatus = false;
		String trsdFlagExt ="";
		String isTrsdCompleted = "";
		String wiName =formObject.getValue(TRSD_WI_NAME).toString();
		
		try{
			String query="select trsd_flag,IS_TRSD_COMPLETE from ext_tfo where wi_name='"+sWorkitemID+"'";
			log.info("query :"+query);
			List<List<String>> tmpList = formObject.getDataFromDB(query);
			log.info("select count: "+tmpList.size());
			if (!tmpList.isEmpty()) {
				trsdFlagExt = tmpList.get(0).get(0);
				isTrsdCompleted = tmpList.get(0).get(1);
			}
			 query ="SELECT  count(1) as dataCount from BPM_TRSD_DETAILS  WHERE WI_NAME='"+wiName+"'"
					+" and (discard_flag='N' OR discard_flag is null) AND TRSD_CASE_NUM IS NOT NULL"
					+" and trsd_decision is  null";
			log.info("query :"+query);
			 tmpList = formObject.getDataFromDB(query);
			log.info("select count: "+tmpList);
			if (!tmpList.isEmpty()) {
				int dataCount = Integer.parseInt(tmpList.get(0).get(0).toString());
				if(dataCount>0){
					output = "Y";
				} else {
				  query ="SELECT  TRSD_DECISION from BPM_TRSD_DETAILS  WHERE WI_NAME = '"
						  +wiName+"' and (discard_flag='N' OR discard_flag is null) AND TRSD_CASE_NUM IS NOT NULL";
					log.info("query :"+query);

					tmpList = formObject.getDataFromDB(query);
					log.info("query :"+tmpList);
					if (!tmpList.isEmpty()) {
						for (int counter = 0; counter < tmpList.size(); counter++) {
					
					     String trsdDecision = ((String) ((List) tmpList.get(counter)).get(0));
							  log.info( "trsd_decision: "+trsdDecision);
							if(trsdDecision.equalsIgnoreCase("Reject")) {
								rejectStatus = true;
								break;
							} else if(trsdDecision.equalsIgnoreCase("Return")) {
								returnStatus = true; 
							}
						}
					}

					if ((!rejectStatus) && (!returnStatus)) {
						output = "A";
					} else if (rejectStatus) {
						output = "J"; 
					} else if (returnStatus) {
						output = "R";
					}
					}
				log.info( "output: "+output+"trsdFlag :"+trsdFlagExt+"isTrsdCompleted :"+isTrsdCompleted);
				if(output!=null && 
						(!trsdFlagExt.equalsIgnoreCase(output))
						&&"Y".equalsIgnoreCase(isTrsdCompleted)){
					query = "update ext_tfo set trsd_flag='"+output+"' where wi_name='"+sWorkitemID+"'";
					log.info( "query: "+query);
					int value = formObject.saveDataInDB(query);
					 log.info( "value: "+value);
				}
				if("Y".equalsIgnoreCase(output)){
					//insert data into bpmevent gen
					query="insert into BPM_EVENTGEN_TXN_DATA(insertiondatetime, wi_name, expiry_date, status_flag,PROCESS_NAME, "
							+ "SOURCING_CHANNEL, REQUESTMODE)"
							+ " values(SYSDATE,'"+this.sWorkitemID+"',(SYSDATE+(5/(24*60))),'N','TFO','SANCTION_SCREENING_VALIDATION','C')";
					log.info("Saving in ext table records query : "+query);
					output = formObject.saveDataInDB(query)+"";
					log.info("response : "+output);
				}
				
			}
			
		}catch(Exception e){
			log.error("Exception: ",e);
		}
	}
	//DART 1132944 AYUSH
	//DART 1132057
	public void refreshData(){
		log.info("Inside refreshData: "+ sWorkitemID);
		try {
			int iRowCount = getGridCount(LISTVIEW_TRSD_TABLE_OPTIONAL); 
			for(int count = 0 ; count < iRowCount ; count++){	 
				String entity = formObject.getTableCellValue(LISTVIEW_TRSD_TABLE_OPTIONAL, count,0);
				String entityname = formObject.getTableCellValue(LISTVIEW_TRSD_TABLE_OPTIONAL, count,1);
				String screeningType = formObject.getTableCellValue(LISTVIEW_TRSD_TABLE_OPTIONAL, count,2);
				String channelRefNum = formObject.getTableCellValue(LISTVIEW_TRSD_TABLE_OPTIONAL, count,10);
				String executionStatus = formObject.getTableCellValue(LISTVIEW_TRSD_TABLE_OPTIONAL, count,12);
				entity =entity.replace("'", "''");  //added by reyaz 5/06/2023
				entityname =entityname.replace("'", "''");  //added by shivanshu 19/06/2023
				log.info("Inside refreshData Field Values: "+ entity +" : "+entityname+" : "+screeningType+" : "+channelRefNum+" : "+executionStatus);
				if(channelRefNum.equals("") && executionStatus.equals("N")){
					log.info("New Entry");
					String query = "insert into tfo_dj_trsd_screening_other_details(insertionorderid, winame, entity, entity_name,"
							+ " trsd_screening_type, channel_reference_no, execution_status)"
							+ " values('"+getInserIdForTable("WFSEQ_ARRAY_TRSD_ID")+"','"+sWorkitemID+"','"+ entity +"','"+ 
							entityname+"','"+ screeningType +"','"+ channelRefNum +"', 'N')";
					log.info("Inserting in tfo_dj_trsd_screening_other_details records query : "+query);
					int output = formObject.saveDataInDB(query);
					log.info("response : "+output);
					
				} else if(executionStatus.equals("N")) {
					log.info("Delete and Insert Entry");
					String query = "delete from tfo_dj_trsd_screening_other_details where winame = '"+ sWorkitemID +"' "
							+ "and channel_reference_no = '"+ channelRefNum +"'";
					log.info("Deletion from tfo_dj_trsd_screening_other_details query : "+query);
					int output = formObject.saveDataInDB(query);
					log.info("deletion response : "+output);
					query = "insert into tfo_dj_trsd_screening_other_details(insertionorderid, winame, entity, entity_name,"
							+ " trsd_screening_type, channel_reference_no, execution_status)"
							+ " values('"+getInserIdForTable("WFSEQ_ARRAY_TRSD_ID")+"','"+sWorkitemID+"','"+ entity +"','"+ 
							entityname+"','"+ screeningType +"','"+ channelRefNum +"', 'N')";
					log.info("Inserting in tfo_dj_trsd_screening_other_details records query : "+query);
					output = formObject.saveDataInDB(query);
					log.info("insertion response : "+output);
				}
			}
				
			iRowCount = getGridCount(LISTVIEW_TRSD_TABLE); 
			for(int count = 0 ; count < iRowCount ; count++){	 
				String entity = formObject.getTableCellValue(LISTVIEW_TRSD_TABLE, count,0);
				String entityname = formObject.getTableCellValue(LISTVIEW_TRSD_TABLE, count,2);
				String screeningType = formObject.getTableCellValue(LISTVIEW_TRSD_TABLE, count,3);
				String channelRefNum = formObject.getTableCellValue(LISTVIEW_TRSD_TABLE, count,11);
				String executionStatus = formObject.getTableCellValue(LISTVIEW_TRSD_TABLE, count,13);
				String trsdCategory = formObject.getTableCellValue(LISTVIEW_TRSD_TABLE, count,1);
				entity =entity.replace("'", "''");  //added by reyaz 5/06/2023
				entityname =entityname.replace("'", "''");  //added by shivanshu 19/06/2023
				trsdCategory =trsdCategory.replace("'", "''");
				log.info(LISTVIEW_TRSD_TABLE + ": Inside refreshData Field Values: "+ entity +" : "+entityname+" : "+screeningType+
						" : "+channelRefNum+" : "+executionStatus + " : "+trsdCategory);
				if(channelRefNum.equals("") && executionStatus.equals("N")){
					log.info(LISTVIEW_TRSD_TABLE + ":New Entry");
					String query = "insert into tfo_dj_trsd_screening_details(insertionorderid, winame, entity, trsd_category, entity_name,"
							+ " trsd_screening_type, channel_reference_no, execution_status)"
							+ " values('"+getInserIdForTable("WFSEQ_ARRAY_TRSD_ID")+"','"+sWorkitemID+"','"+ entity +"','"+ 
							trsdCategory + "','"+entityname+"','"+ screeningType +"','"+ channelRefNum +"', 'N')";
					log.info("Inserting in tfo_dj_trsd_screening_details records query : "+query);
					int output = formObject.saveDataInDB(query);
					log.info("response : "+output);
					
				} else if(executionStatus.equals("N")) {
					log.info(LISTVIEW_TRSD_TABLE + ":Delete and Insert Entry");
					String query = "delete from tfo_dj_trsd_screening_details where winame = '"+ sWorkitemID +"' "
							+ "and channel_reference_no = '"+ channelRefNum +"'";
					log.info("Deletion from tfo_dj_trsd_screening_details query : "+query);
					int output = formObject.saveDataInDB(query);
					log.info("deletion response : "+output);
					query = "insert into tfo_dj_trsd_screening_details(insertionorderid, winame, entity, trsd_category, entity_name,"
							+ " trsd_screening_type, channel_reference_no, execution_status)"
							+ " values('"+getInserIdForTable("WFSEQ_ARRAY_TRSD_ID")+"','"+sWorkitemID+"','"+ entity +"','"+ 
							trsdCategory +"','"+entityname+"','"+ screeningType +"','"+ channelRefNum +"', 'N')";
					log.info("Inserting in tfo_dj_trsd_screening_details records query : "+query);
					output = formObject.saveDataInDB(query);
					log.info("insertion response : "+output);
				}
			} 
		} catch(Exception e){
			log.error("Exception in Refresh Data",e);
		}
		
	}
	public String getInserIdForTable(String seqName){
		String seqValue=null;
		String query="";
		query= "select "+seqName+".nextval from dual";
		log.info("query  :"+query);
		List<List<String>> sOutputlist  = formObject.getDataFromDB(query);
		log.info("sOutputlist  :"+sOutputlist);

		if (sOutputlist != null && !sOutputlist.isEmpty() && !sOutputlist.get(0).isEmpty()) {
			seqValue = sOutputlist.get(0).get(0);
		}
		return seqValue;
	}
	
	// added by reyaz
	public boolean mandatoryFieldLoanAmount(){
		try{
		     if (("LD".equalsIgnoreCase(requestType) ||"PD".equalsIgnoreCase(requestType) ||"PDD".equalsIgnoreCase(requestType)) && ("IFP".equalsIgnoreCase(requestCategory)                
				|| "IFA".equalsIgnoreCase(requestCategory) || "IFS".equalsIgnoreCase(requestCategory) ||"SCF".equalsIgnoreCase(requestCategory))  //ATP - 204,205
				&& formObject.getValue(PROCESSING_SYSTEM).toString().equalsIgnoreCase("T")){
			                 log.info("validating for Loan Amount");
			    return validateMandatoryFieldsPM(TSLM_LOAN_AMOUNT, "Please Enter Loan Amount.");
				      
	             }
		   
		 }catch (Exception e){
		log.error("Exception: ",e);
		
	     }
		return true;
	}	
	
//	12-05-2023
private void LoadListViewOfVesselScreening() {
		try {
			log.info("***************Inside toInsertVesselScreening******************");
			String entity ="";
			String trsdCategory ="";
			String entityname ="";
			String screeningType ="";
			String channelRefNum ="";
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			
			// Basic details
			String sQuery1 = "Select VESSEL_NAME,TRIM((select Country_full_name from TFO_DJ_LLI_COUNTRY_MAST where country_code = "
					+ "a.VESSEL_FLAG)) VESSEL_FLAG from  TFO_DJ_LLI_BASIC_VSSL_DTLS a where (select Country_full_name from"
					+ " TFO_DJ_LLI_COUNTRY_MAST where country_code = a.VESSEL_FLAG) not in(select entity_name from "
					+ "tfo_dj_trsd_screening_details where winame ='"+sWorkitemID+"' and entity ='Vessel Flag') and"
					+ " a.WI_NAME = '"+sWorkitemID+"'";
			log.info("sQuery1 : "+sQuery1);
			List<List<String>> sOutput1;
			sOutput1 = formObject.getDataFromDB(sQuery1);
			log.info("sOutput1 : "+sOutput1);

			if(sOutput1 != null && sOutput1.size() > 0){
				for(int i=0;i<sOutput1.size();i++){ 
					jsonObject = new JSONObject();
					trsdCategory =sOutput1.get(i).get(0).toString();
					entityname =sOutput1.get(i).get(1).toString();
					screeningType ="Organisation";  
					entity ="Vessel Flag";
					jsonObject.put("Entity", entity);
					jsonObject.put("Entity_Name", entityname);
					jsonObject.put("TRSD_Screening_Type", screeningType);
					jsonObject.put("EXECUTION_STATUS","N");
					jsonObject.put("TRSD_CATEGORY","Flag"+" -("+trsdCategory+")");
					log.info("END OF  LoadListViewOfVesselScreening Basic "+jsonObject.toString());
					jsonArray.add(jsonObject);
				}
			}
			
			// Ownerships details
			sQuery1 = "SELECT  DISTINCT TRIM(a.COUNTRY_NAME),a.VESSEL_NAME  FROM tfo_dj_lli_ownership_dtls a where TRIM(a.country_name) not "
					+ "in(select entity_name from tfo_dj_trsd_screening_details where winame = '"+sWorkitemID+"' and"
					+ " entity ='Vessel Owner''s Country') and a.WI_NAME  = '"+sWorkitemID+"' AND a.COUNTRY_NAME IS NOT NULL";
			log.info("sQuery1 : "+sQuery1);
			sOutput1 = formObject.getDataFromDB(sQuery1);
			log.info("sOutput1 : "+sOutput1);

			if(sOutput1 != null && sOutput1.size() > 0){
				for(int i=0;i<sOutput1.size();i++){ 
					jsonObject = new JSONObject();
					entityname =sOutput1.get(i).get(0).toString();
					trsdCategory =sOutput1.get(i).get(1).toString();
					screeningType ="Organisation";
					entity ="Vessel Owner's Country";
					jsonObject.put("Entity", entity);
					jsonObject.put("Entity_Name", entityname);
					jsonObject.put("TRSD_Screening_Type", screeningType);
					jsonObject.put("EXECUTION_STATUS","N");
					jsonObject.put("TRSD_CATEGORY","Owner's Country"+" -("+trsdCategory+")");
					log.info("END OF LoadListViewOfVesselScreening Ownerships "+jsonObject.toString());
					jsonArray.add(jsonObject);
				}
			
			}
			
			 //Port calling details
//			sQuery1 = "SELECT   DISTINCT(PLACE),VESSEL_NAME from tfo_dj_lli_mvmnt_dtls_calling a where a.PLACE not in(select "
//					+ "entity_name from tfo_dj_trsd_screening_details where winame = '"+sWorkitemID+"' and entity "
//					+ "='Vessel Movement Ports') and a.WI_NAME  = '"+sWorkitemID+"' AND a.PLACE IS NOT NULL AND a.MOVEMENT_TTYPE not like '%Passed%'";
			sQuery1 = "SELECT   DISTINCT TRIM(PLACE),VESSEL_NAME from tfo_dj_lli_mvmnt_dtls_calling a "
					+ "  where TRIM(a.PLACE) not in(select entity_name from tfo_dj_trsd_screening_details "
					+ "  where winame = '"+sWorkitemID+"' and entity ='Vessel Movement Ports') and "
					+ "  TRIM(a.PLACE) not in(SELECT  DISTINCT(NEAREST_PLACE) FROM tfo_dj_lli_mvmnt_dtls_sighting"
					+ "  where wi_name ='"+sWorkitemID+"' and VESSEL_TYPE = 'stopped') and "
					+ "  a.WI_NAME  = '"+sWorkitemID+"' AND a.PLACE IS NOT NULL ";
			log.info("sQuery1 : "+sQuery1);
			sOutput1 = formObject.getDataFromDB(sQuery1);
			log.info("sOutput1 : "+sOutput1);

			if(sOutput1 != null && sOutput1.size() > 0){
				for(int i=0;i<sOutput1.size();i++){ 
					jsonObject = new JSONObject();
					entityname =sOutput1.get(i).get(0).toString();
					trsdCategory =sOutput1.get(i).get(1).toString();
					screeningType ="Organisation";
					entity ="Vessel Movement Ports";
					jsonObject.put("Entity", entity);
					jsonObject.put("Entity_Name", entityname);
					jsonObject.put("TRSD_Screening_Type", screeningType);
					jsonObject.put("EXECUTION_STATUS","N");
					jsonObject.put("TRSD_CATEGORY","Movement Ports"+" -("+trsdCategory+")");
					log.info("END OF LoadListViewOfVesselScreening Port Caliing  "+jsonObject.toString());
					jsonArray.add(jsonObject);
					
				}
			}
			
			// Port sighting details
			sQuery1 = "SELECT  DISTINCT TRIM(NEAREST_PLACE),VESSEL_NAME FROM tfo_dj_lli_mvmnt_dtls_sighting a where TRIM(a.NEAREST_PLACE) not"
					+ " in(select entity_name from tfo_dj_trsd_screening_details where winame ='"+sWorkitemID+"' and entity"
				    + " ='Vessel Movement Ports') and a.WI_NAME  = '"+sWorkitemID+"' AND a.NEAREST_PLACE IS NOT NULL  AND a.VESSEL_TYPE = 'stopped'";
			log.info("sQuery1 : "+sQuery1);
			sOutput1 = formObject.getDataFromDB(sQuery1);
			log.info("sOutput1 : "+sOutput1);

			if(sOutput1 != null && sOutput1.size() > 0){
				for(int i=0;i<sOutput1.size();i++){ 
					jsonObject = new JSONObject();
					entityname =sOutput1.get(i).get(0).toString();
					trsdCategory =sOutput1.get(i).get(1).toString();
					screeningType ="Organisation";
					entity ="Vessel Movement Ports";
					
					jsonObject.put("Entity", entity);
					jsonObject.put("Entity_Name", entityname);
					jsonObject.put("TRSD_Screening_Type", screeningType);
					jsonObject.put("EXECUTION_STATUS","N");
					jsonObject.put("TRSD_CATEGORY","Movement Ports"+" -("+trsdCategory+")");
					log.info("END OF LoadListViewOfVesselScreening Port Sighting "+jsonObject.toString());
					jsonArray.add(jsonObject);
					
				}
			}
			
			// Country calling details
//			sQuery1 = "SELECT DISTINCT(COUNTRY),VESSEL_NAME FROM tfo_dj_lli_mvmnt_dtls_calling a where a.COUNTRY not in "
//					+ "(select entity_name from tfo_dj_trsd_screening_details where winame ='"+sWorkitemID+"' and entity "
//					+ "='Vessel Movement Country') and a.WI_NAME   ='"+sWorkitemID+"' AND a.COUNTRY IS NOT NULL AND a.MOVEMENT_TTYPE not like '%Passed%'";
			sQuery1 = "SELECT DISTINCT TRIM(COUNTRY),VESSEL_NAME FROM tfo_dj_lli_mvmnt_dtls_calling a "
					+ "  where TRIM(a.COUNTRY) not in (select entity_name from tfo_dj_trsd_screening_details "
					+ "  where winame ='"+sWorkitemID+"' and entity ='Vessel Movement Country') "
					+ "  AND TRIM(a.COUNTRY) not in (SELECT DISTINCT(NEAREST_COUNTRY) FROM tfo_dj_lli_mvmnt_dtls_sighting "
					+ "  where WI_NAME  ='"+sWorkitemID+"' AND VESSEL_TYPE = 'stopped')"
					+ "  and a.WI_NAME   ='"+sWorkitemID+"' AND a.COUNTRY IS NOT NULL";
			log.info("sQuery1 : "+sQuery1);
			sOutput1 = formObject.getDataFromDB(sQuery1);
			log.info("sOutput1 : "+sOutput1);

			if(sOutput1 != null && sOutput1.size() > 0){
				for(int i=0;i<sOutput1.size();i++){ 
					jsonObject = new JSONObject();
					entityname =sOutput1.get(i).get(0).toString();
					trsdCategory =sOutput1.get(i).get(1).toString();
					screeningType ="Organisation";
					entity ="Vessel Movement Country";
					jsonObject.put("Entity", entity);
					jsonObject.put("Entity_Name", entityname);
					jsonObject.put("TRSD_Screening_Type", screeningType);
					jsonObject.put("EXECUTION_STATUS","N");
					jsonObject.put("TRSD_CATEGORY","Movement Country"+" -("+trsdCategory+")");
					log.info("END OF LoadListViewOfVesselScreening Country Calling " +jsonObject.toString());
					jsonArray.add(jsonObject);
				}
			}
			
			//Country sighting details
			sQuery1 = "SELECT DISTINCT TRIM(NEAREST_COUNTRY),VESSEL_NAME FROM tfo_dj_lli_mvmnt_dtls_sighting a where TRIM(a.NEAREST_COUNTRY)"
					+ " not in (select entity_name from tfo_dj_trsd_screening_details where winame ='"+sWorkitemID+"' and"
					+ " entity ='Vessel Movement Country') and a.WI_NAME  ='"+sWorkitemID+"' AND a.NEAREST_COUNTRY IS NOT NULL  AND a.VESSEL_TYPE = 'stopped'";
			log.info("sQuery1 : "+sQuery1);
			sOutput1 = formObject.getDataFromDB(sQuery1);
			log.info("sOutput1 : "+sOutput1);

			if(sOutput1 != null && sOutput1.size() > 0){
				for(int i=0;i<sOutput1.size();i++){ 
					jsonObject = new JSONObject();
					entityname =sOutput1.get(i).get(0).toString();
					trsdCategory =sOutput1.get(i).get(1).toString();
					screeningType ="Organisation";
					entity ="Vessel Movement Country";
					jsonObject.put("Entity", entity);
					jsonObject.put("Entity_Name", entityname);
					jsonObject.put("TRSD_Screening_Type", screeningType);
					jsonObject.put("EXECUTION_STATUS","N");
					jsonObject.put("TRSD_CATEGORY","Movement Country"+" -("+trsdCategory+")");
					log.info("END OF LoadListViewOfVesselScreening Country Sighting "+jsonObject.toString());
					jsonArray.add(jsonObject);
					
				}
			}
			log.info("Vessel FINAL jason array :: "+jsonArray.toString());
			formObject.addDataToGrid("TRSD_TABLE",jsonArray);
			
		}catch(Exception e) {
			log.error("Exception: ",e);
		}

	}

	
//added by reyaz	16-05-2023
private void LoadListViewOfPartyCountryScreening() {
	String entityname ="";
	String entity ="";
	List<List<String>> sOutput1;
	JSONArray jsonArray = new JSONArray();
	log.info("Inside LoadListViewOfPartyCountryScreening ");
		try {
			List<String> partyList =new ArrayList<String>();
			JSONObject jsonObject = new JSONObject();
			String sQuery1 = "SELECT ENTITY_NAME FROM TFO_DJ_TRSD_SCREENING_DETAILS WHERE WINAME = '"+sWorkitemID+"' AND  EXECUTION_STATUS ='Y' ";
			log.info("sQuery1 : "+sQuery1);
			List<List<String>> ListData = formObject.getDataFromDB(sQuery1);
			for(int i=0;i<ListData.size();i++){
				partyList.add(ListData.get(i).get(0).toString());
			}
			log.info("partyList : "+partyList);
			String sQuery2 = "Select Distinct (select country from USR_0_COUNTRY_MAST where country_code = a.Country) Country,PARTY_DESC from"
					+ "  TFO_DJ_PARTY_DETAILS a where WINAME = '"+sWorkitemID+"' AND Country is not null";
			log.info("sQuery2 : "+sQuery2);
			sOutput1 = formObject.getDataFromDB(sQuery2);
			log.info("sOutput1 : "+sOutput1);

			if(sOutput1 != null && sOutput1.size() > 0){
				for(int i=0;i<sOutput1.size();i++){ 
					entityname =sOutput1.get(i).get(0).toString();
					entity  =sOutput1.get(i).get(1).toString();
					log.info("partyList :::: "+entityname);
					if(!"".equalsIgnoreCase(entityname)  && entityname != null){
						if(!partyList.contains(entityname)){
							log.info("****Party***** ");
							jsonObject = new JSONObject();
							jsonObject.put("Entity",entity +" Country");
							jsonObject.put("Entity_Name", entityname);
							jsonObject.put("TRSD_Screening_Type", "Organisation");
							jsonObject.put("EXECUTION_STATUS","N");
							jsonObject.put("TRSD_CATEGORY","Party");
							log.info("END OF  LoadListViewOfPartyCountryScreening "+jsonObject.toString());
						    jsonArray.add(jsonObject);
						}
					}
					
				}
			}
			log.info("Final json array ::: "+jsonArray);
			formObject.addDataToGrid(LISTVIEW_TRSD_TABLE, jsonArray);
			
		} catch(Exception e) {
			log.error("Exception: ",e);
		}

}

// Added by reyaz 17-05-2023
private boolean checkTRSDOptionalMandatoryValidation() {
	try {
		String LoanGrp = formObject.getValue("IFS_LOAN_GRP").toString();
		boolean flag0 = false;
		boolean flag1 = false;
		boolean flag2 = false;
		boolean flag3 = false;
		boolean flag4 = false;
		boolean flag5 = false;
		boolean flag6 = false;
		boolean flag7 = false;
		boolean flag8 = false;
		boolean flag9 = false;
		boolean flag10 = false;
		boolean flag11 = false;
		boolean flag12 = false;
		boolean flag13 = false;
		boolean flag14 = false;
		boolean flag15 = false;
		boolean flag16 = false;
		int iRowCount = getGridCount(LISTVIEW_TRSD_TABLE_OPTIONAL);
		for (int count = 0; count < iRowCount; count++) {
			String entity = formObject.getTableCellValue(LISTVIEW_TRSD_TABLE_OPTIONAL, count, 0);
			String entityName = formObject.getTableCellValue(LISTVIEW_TRSD_TABLE_OPTIONAL, count, 1);
			log.info("checkTRSDOptionalMandatoryValidation Entity " + entity);
			log.info("checkTRSDOptionalMandatoryValidation Entity Name " + entityName);
			if ("".equalsIgnoreCase(entityName)) {
				flag0 = true;
			}
			if ("DRAWEE/BUYER".equalsIgnoreCase(entity)) {
				flag1 = true;
			} else if ("DRAWER/SELLER".equalsIgnoreCase(entity)) {
				flag2 = true;
			} else if ("DRAWER/SELLER COUNTRY".equalsIgnoreCase(entity)) {
				flag3 = true;
			} else if ("DRAWEE/BUYER COUNTRY".equalsIgnoreCase(entity)) {
				flag4 = true;
			} else if ("Port of Loading/Airport of Departure".equalsIgnoreCase(entity)) {
				flag5 = true;
			} else if ("Port of Discharge/Airport of Destination".equalsIgnoreCase(entity)) {
				flag6 = true;
			} else if ("Place of Taking in Charge/Dispatch from___ /Place of Receipt".equalsIgnoreCase(entity)) {
				flag7 = true;
			} else if ("Place of Final Destination/For Transportation to__/Place of Delivery"
					.equalsIgnoreCase(entity)) {
				flag8 = true;
			} else if ("Country of Origin".equalsIgnoreCase(entity)) {
				flag9 = true;
			} else if ("Beneficiary Bank Name".equalsIgnoreCase(entity)) {
				flag10 = true;
			} else if ("Beneficiary Bank Country".equalsIgnoreCase(entity)) {
				flag11 = true;
			} else if ("Beneficiary Bank Correspondence's Name".equalsIgnoreCase(entity)) {
				flag12 = true;
			} else if ("Beneficiary Bank Correspondence's Country".equalsIgnoreCase(entity)) {
				flag13 = true;
			} else if ("Goods/Services".equalsIgnoreCase(entity)) {
				flag14 = true;
			} else if ("OBLIGOR NAME".equalsIgnoreCase(entity)) {
				flag15 = true;
			} else if ("GRANTOR NAME".equalsIgnoreCase(entity)) {
				flag16 = true;
			}
		}
		if (!("GRNT".equalsIgnoreCase(requestCategory))) {
			if (!flag5) {
				sendMessageHashMap(BTN_START_TRSD,
						"Please add atleast one entries for Port of Loading/Airport of Departure in Optional Entities grid");
				return false;
			} else if (!flag6) {
				sendMessageHashMap(BTN_START_TRSD,
						"Please add atleast one entries for Port of Discharge/Airport of Destination in Optional Entities grid");
				return false;
			} else if (!flag7) {
				sendMessageHashMap(BTN_START_TRSD,
						"Please add atleast one entries for Place of Taking in Charge/Dispatch from___ /Place of Receipt in Optional Entities grid");
				return false;
			} else if (!flag8) {
				sendMessageHashMap(BTN_START_TRSD,
						"Please add atleast one entries for Place of Final Destination/For Transportation to__/Place of Delivery in Optional Entities grid");
				return false;
			} else if (!flag9) {
				sendMessageHashMap(BTN_START_TRSD,
						"Please add atleast one entries for Country of Origin in Optional Entities grid");
				return false;
			}
			if (flag0) {
				sendMessageHashMap(BTN_START_TRSD, "Please fill entity name in Optional Entities grid");
				return false;
			}

		} 
		if ("IFP".equalsIgnoreCase(requestCategory) || "IFA".equalsIgnoreCase(requestCategory)
				|| "IFS".equalsIgnoreCase(requestCategory) || "SCF".equalsIgnoreCase(requestCategory)) { // ATP -
																											// 204,205

			if (!flag1) {
				sendMessageHashMap(BTN_START_TRSD,
						"Please add atleast one entries for Drawee/Buyer in Optional Entities grid");
				return false;
			} else if (!flag2) {
				sendMessageHashMap(BTN_START_TRSD,
						"Please add atleast one entries for Drawer/Seller in Optional Entities grid");
				return false;
			} else if (!flag3 && !"IFS".equalsIgnoreCase(requestCategory)) {
				sendMessageHashMap(BTN_START_TRSD,
						"Please add atleast one entries for Drawer/Seller Country in Optional Entities grid");
				return false;
			} else if (!flag4 && "IFS".equalsIgnoreCase(requestCategory)) {
				sendMessageHashMap(BTN_START_TRSD,
						"Please add atleast one entries for Drawee/Buyer Country in Optional Entities Grid");
				return false;
			} else if (!flag10 && !"IFS".equalsIgnoreCase(requestCategory)) {
				sendMessageHashMap(BTN_START_TRSD,
						"Please add atleast one entries for Beneficiary Bank Name in Optional Entities grid");
				return false;
			} else if (!flag11 && !"IFS".equalsIgnoreCase(requestCategory)) {
				sendMessageHashMap(BTN_START_TRSD,
						"Please add atleast one entries for Beneficiary Bank Country in Optional Entities grid");
				return false;
			} else if (!flag12 && !"IFS".equalsIgnoreCase(requestCategory)) {
				sendMessageHashMap(BTN_START_TRSD,
						"Please add atleast one entries for Beneficiary Bank Correspondence's Name in Optional Entities grid");
				return false;
			} else if (!flag13 && !"IFS".equalsIgnoreCase(requestCategory)) {
				sendMessageHashMap(BTN_START_TRSD,
						"Please add atleast one entries for Beneficiary Bank Correspondence's Country in Optional Entities grid");
				return false;
			}
			if (flag0) {
				sendMessageHashMap(BTN_START_TRSD, "Please fill entity name in Optional Entities grid");
				return false;
			}
		}
		if ("TL".equalsIgnoreCase(requestCategory) && "TL_LD".equalsIgnoreCase(requestType)
				&& ("MRPA".equalsIgnoreCase(LoanGrp) || "BC".equalsIgnoreCase(LoanGrp))) {
			if (!flag10) {
				sendMessageHashMap(BTN_START_TRSD,
						"Please add atleast one entries for Beneficiary Bank Name in Optional Entities grid");
				return false;
			} else if (!flag11) {
				sendMessageHashMap(BTN_START_TRSD,
						"Please add atleast one entries for Beneficiary Bank Country in Optional Entities grid");
				return false;
			} else if (!flag12) {
				sendMessageHashMap(BTN_START_TRSD,
						"Please add atleast one entries for Beneficiary Bank Correspondence's Name in Optional Entities grid");
				return false;
			} else if (!flag13) {
				sendMessageHashMap(BTN_START_TRSD,
						"Please add atleast one entries for Beneficiary Bank Correspondence's Country in Optional Entities grid");
				return false;
			} else if (!flag15) {
				sendMessageHashMap(BTN_START_TRSD,
						"Please add atleast one entries for OBLIGOR NAME in Optional Entities grid");
				return false;
			} else if (!flag16) {
				sendMessageHashMap(BTN_START_TRSD,
						"Please add atleast one entries for GRANTOR NAME in Optional Entities grid");
				return false;
			}
			if (flag0) {
				sendMessageHashMap(BTN_START_TRSD, "Please fill entity name in Optional Entities grid");
				return false;
			}
		} else {
			iRowCount = getGridCount(LISTVIEW_TRSD_TABLE_OPTIONAL);
			for (int count = 0; count < iRowCount; count++) {
				String entityName = formObject.getTableCellValue(LISTVIEW_TRSD_TABLE_OPTIONAL, count, 1);
				log.info("checkTRSDOptionalMandatoryValidation Entity Name " + entityName);
				if ("".equalsIgnoreCase(entityName)) {
					flag0 = true;
				}
			}
			if (flag0) {
				sendMessageHashMap(BTN_START_TRSD, "Please fill entity name in Other Entities grid");
				return false;
			}
		}

		if (!flag14) {
			sendMessageHashMap(BTN_START_TRSD,
					"Please add atleast one entries for Goods/Services in Optional Entities grid");
			return false;
		}

	} catch (Exception e) {
		log.error("Exception in checkTRSDOptionalMandatoryValidation", e);
	}
	return true;
}
	
   // ATP-367 18-01-2024 REYAZ
	public void loadTrsdDataAfterExcelUpload() {
		try {
			log.info("***************Inside loadTrsdDataAfterExcelUpload******************");
			int iRowCount = getGridCount(LISTVIEW_TRSD_TABLE_OPTIONAL);
			for (int i = 0; i < iRowCount; i++) {
				String entity = formObject.getTableCellValue(LISTVIEW_TRSD_TABLE_OPTIONAL, i, 0);
				String entityname = formObject.getTableCellValue(LISTVIEW_TRSD_TABLE_OPTIONAL, i, 1);
				String screeningType = formObject.getTableCellValue(LISTVIEW_TRSD_TABLE_OPTIONAL, i, 2);
				String executionStatus = formObject.getTableCellValue(LISTVIEW_TRSD_TABLE_OPTIONAL, i, 12);
				String channelRefNum = formObject.getTableCellValue(LISTVIEW_TRSD_TABLE_OPTIONAL, i, 10);
				log.info("executionStatus " + executionStatus);
				log.info("channelRefNum " + channelRefNum);
				if (channelRefNum.equals("") && executionStatus.equals("N")) {
					String query = "insert into tfo_dj_trsd_screening_other_details(insertionorderid, winame, entity, entity_name,"
							+ " trsd_screening_type, channel_reference_no, execution_status)" + " values('"
							+ getInserIdForTable("WFSEQ_ARRAY_TRSD_ID") + "','" + sWorkitemID + "','" + entity + "','"
							+ entityname + "','" + screeningType + "','" + getInserIdForTable("ARRAY_FSK_ID")
							+ "', 'N')";
					log.info("Inserting in tfo_dj_trsd_screening_other_details records : " + query);
					int output = formObject.saveDataInDB(query);
					log.info(" loadTrsdDataAfterExcelUpload response : " + output);
				}
			}
		} catch (Exception e) {
			log.error("Exception: ", e);
		}
	}

//Added by Shivanshu
private void addMandatoryScreening(){
	try{
		JSONArray jsonArray =new JSONArray();
		String LoanGrp = formObject.getValue("IFS_LOAN_GRP").toString();
		String processType=formObject.getValue(PROCESS_TYPE).toString();
		boolean flag0 = false;
		boolean flag1 = false;
		boolean flag2 = false;
		boolean flag3 = false;
		boolean flag4 = false;
		boolean flag5 = false;
		boolean flag6 = false;
		boolean flag7 = false;
		boolean flag8 = false;
		boolean flag9 = false;
		boolean flag10 = false;
		boolean flag11 = false;
		boolean flag12 = false;
		boolean flag13 = false;
		boolean flag14 = false;
		boolean flag15 = false;
		boolean flag16 = false;
		Set<String> entityNameFlags = new HashSet<>(Arrays.asList(
			    "DRAWEE/BUYER", "DRAWER/SELLER", "DRAWER/SELLER COUNTRY", "DRAWEE/BUYER COUNTRY",
			    "Port of Loading/Airport of Departure", "Port of Discharge/Airport of Destination",
			    "Place of Taking in Charge/Dispatch from___ /Place of Receipt",
			    "Place of Final Destination/For Transportation to__/Place of Delivery", 
			    "Country of Origin", "Beneficiary Bank Name", "Beneficiary Bank Country",
			    "Beneficiary Bank Correspondence's Name", "Beneficiary Bank Correspondence's Country",
			    "Goods/Services", "OBLIGOR NAME", "GRANTOR NAME"
			));
		int iRowCount = getGridCount(LISTVIEW_TRSD_TABLE_OPTIONAL);
		for (int count = 0; count < iRowCount; count++) {
		    String entity = formObject.getTableCellValue(LISTVIEW_TRSD_TABLE_OPTIONAL, count, 0);
		    String entityName = formObject.getTableCellValue(LISTVIEW_TRSD_TABLE_OPTIONAL, count, 1);

		    log.info("checkTRSDOptionalMandatoryValidation Entity " + entity);
		    log.info("checkTRSDOptionalMandatoryValidation Entity Name " + entityName);
		    
		    if (entityNameFlags.contains(entity)) {
		        switch (entity) {
		            case "DRAWEE/BUYER":
		                flag1 = true;
		                break;
		            case "DRAWER/SELLER":
		                flag2 = true;
		                break;
		            case "DRAWER/SELLER COUNTRY":
		                flag3 = true;
		                break;
		            case "DRAWEE/BUYER COUNTRY":
		                flag4 = true;
		                break;
		            case "Port of Loading/Airport of Departure":
		                flag5 = true;
		                break;
		            case "Port of Discharge/Airport of Destination":
		                flag6 = true;
		                break;
		            case "Place of Taking in Charge/Dispatch from___ /Place of Receipt":
		                flag7 = true;
		                break;
		            case "Place of Final Destination/For Transportation to__/Place of Delivery":
		                flag8 = true;
		                break;
		            case "Country of Origin":
		                flag9 = true;
		                break;
		            case "Beneficiary Bank Name":
		                flag10 = true;
		                break;
		            case "Beneficiary Bank Country":
		                flag11 = true;
		                break;
		            case "Beneficiary Bank Correspondence's Name":
		                flag12 = true;
		                break;
		            case "Beneficiary Bank Correspondence's Country":
		                flag13 = true;
		                break;
		            case "Goods/Services":
		                flag14 = true;
		                break;
		            case "OBLIGOR NAME":
		                flag15 = true;
		                break;
		            case "GRANTOR NAME":
		                flag16 = true;
		                break;
		            default:
		                // Do nothing
		        }
		    }
		}
		//Modified by Shivanshu ATP-458
		if(("SWIFT".equalsIgnoreCase(processType))){
			String fromPlace = "";
			String toPlace = "";
			String sQuery = "SELECT LC_PORT_OF_LOADING, LC_PORT_OF_DISCHRG, LC_FROM_PLACE, LC_TO_PLACE   from EXT_TFO where wi_name ='"+ sWorkitemID +"'";
			log.info("sQuery : "+sQuery);
			List<List<String>> sOutput1 = formObject.getDataFromDB(sQuery);
			log.info("sOutput1 : "+sOutput1);
			if(sOutput1 != null && sOutput1.size() > 0){
				String loadingname =sOutput1.get(0).get(0).toString();
				String dischargename =sOutput1.get(0).get(1).toString();
				 fromPlace =sOutput1.get(0).get(2).toString();
				 toPlace =sOutput1.get(0).get(3).toString();

				if(!flag5)  
				addDataEntity("Port of Loading/Airport of Departure", loadingname, jsonArray);
				if(!flag6)
				addDataEntity("Port of Discharge/Airport of Destination", dischargename, jsonArray);
			}
			if(!flag7) 
				addDataEntity("Place of Taking in Charge/Dispatch from___ /Place of Receipt", fromPlace, jsonArray);
			
			if(!flag8) 
				addDataEntity("Place of Final Destination/For Transportation to__/Place of Delivery", toPlace, jsonArray);
			
			if(!flag9) 
			addDataEntity("Country of Origin", "", jsonArray);
			
			if(!flag14) 
			addDataEntity("Goods/Services", "", jsonArray);
			
			
			formObject.addDataToGrid("TRSD_TABLE_OPTIONAL", jsonArray);
			log.info("addMandatoryScreening : Adding Swift Interface Value: " + jsonArray);
		} else {
			if (!("GRNT".equalsIgnoreCase(requestCategory))) {
				log.info("addMandatoryScreening : Inside non Gurantee");
				if(!flag5)  
				addDataEntity("Port of Loading/Airport of Departure", "", jsonArray);
				if(!flag6)
				addDataEntity("Port of Discharge/Airport of Destination", "", jsonArray);
				if(!flag7)
				addDataEntity("Place of Taking in Charge/Dispatch from___ /Place of Receipt", "", jsonArray);
				if(!flag8)
				addDataEntity("Place of Final Destination/For Transportation to__/Place of Delivery", "", jsonArray);
				if(!flag9)
				addDataEntity("Country of Origin", "", jsonArray);
				log.info("addMandatoryScreening : before Goods/Services");
			}
			if ("IFP".equalsIgnoreCase(requestCategory) || "IFA".equalsIgnoreCase(requestCategory)
					|| "IFS".equalsIgnoreCase(requestCategory) || "SCF".equalsIgnoreCase(requestCategory)) {
				log.info("addMandatoryScreening : Inside invoice finance");
				if (!flag1)
					addDataEntity("DRAWEE/BUYER", "", jsonArray);
				if (!flag2)
					addDataEntity("DRAWER/SELLER", "", jsonArray);
				if (!flag3 && !"IFS".equalsIgnoreCase(requestCategory))
					addDataEntity("DRAWER/SELLER COUNTRY", "", jsonArray);
				if (!flag4 && "IFS".equalsIgnoreCase(requestCategory))
					addDataEntity("DRAWEE/BUYER COUNTRY", "", jsonArray);
				if (!flag10 && !"IFS".equalsIgnoreCase(requestCategory))
				addDataEntity("Beneficiary Bank Name", "", jsonArray);
				if (!flag11 && !"IFS".equalsIgnoreCase(requestCategory))
				addDataEntity("Beneficiary Bank Country", "", jsonArray);
				if (!flag12 && !"IFS".equalsIgnoreCase(requestCategory))
				addDataEntity("Beneficiary Bank Correspondence's Name", "", jsonArray);
				if (!flag13 && !"IFS".equalsIgnoreCase(requestCategory))
				addDataEntity("Beneficiary Bank Correspondence's Country", "", jsonArray);
			}
			if ("TL".equalsIgnoreCase(requestCategory) 
					&& "TL_LD".equalsIgnoreCase(requestType) 
					&& ("MRPA".equalsIgnoreCase(LoanGrp) || "BC".equalsIgnoreCase(LoanGrp))) {
				log.info("addMandatoryScreening : Inside trade loans");
				if(!flag10)
				addDataEntity("Beneficiary Bank Name", "", jsonArray);
				if(!flag11)
				addDataEntity("Beneficiary Bank Country", "", jsonArray);
				if(!flag12)
				addDataEntity("Beneficiary Bank Correspondence's Name", "", jsonArray);
				if(!flag13)
				addDataEntity("Beneficiary Bank Correspondence's Country", "", jsonArray);
			}

			if(!flag14)
			addDataEntity("Goods/Services", "", jsonArray);
			formObject.addDataToGrid("TRSD_TABLE_OPTIONAL", jsonArray);
			log.info("addMandatoryScreening : Adding: " + jsonArray);
		}
	} catch(Exception e) {
		log.error("Exception: ",e);
	}
}
  
//Added by Shivanshu
public void setEmailCompliance(){
	String mailTO = "";
	String processType=formObject.getValue(PROCESS_TYPE).toString();
	String trsdFlag=formObject.getValue(TRSD_FLAG).toString();
	try {
		if ("P".equalsIgnoreCase(trsdFlag)){
		String sQry = "SELECT GROUP_EMAIL FROM TFO_DJ_REQUEST_TYPE_MAST WHERE REQ_CAT_CODE = '"+requestCategory+"' "
				+ " AND PROCESS_TYPE = '"+processType+"' AND FOLDER_STRUC_NAME = 'All Category'"
						+ " AND GROUP_EMAIL IS NOT NULL" ; 
		List<List<String>> sOutputlist = formObject.getDataFromDB(sQry);
		log.info("EMAIL FSK query : "+sQry+" output -"+sOutputlist);
		if (sOutputlist != null && !sOutputlist.isEmpty()&& sOutputlist.get(0).size() > 0) {
			mailTO = sOutputlist.get(0).get(0); 
		}else {
			mailTO = "RakeshR.ext@adcb.com";
		}
		log.info("EMAIL FSK query :mail to - "+mailTO);
		String Qry = "SELECT REFFERD_TO FROM TFO_DJ_FINAL_DEC_SUMMARY WHERE WI_NAME = '"+sWorkitemID+"' ";	 
		List<List<String>> sOutputFSKlist = formObject.getDataFromDB(Qry);
		log.info("EMAIL FSK query : "+Qry+" output -"+sOutputFSKlist);
		if (sOutputFSKlist == null || sOutputFSKlist.isEmpty() || sOutputFSKlist.get(0).get(0).isEmpty()) {
			String insertionID =  getInsertIonIdForTable("TFO_DJ_FINAL_DEC_SUMMARY");
			String query = "Insert into TFO_DJ_FINAL_DEC_SUMMARY (INSERTIONORDERID,EXCP_REMARKS,REFFERD_TO,DECISION,EXISTING_MAIL,WI_NAME)"
							+ "VALUES ('"+insertionID+"','Case Rejected With Remarks','Customer','Y','"+mailTO+"','"+sWorkitemID+"')";
			log.info("insert query: " + query);
			int b = formObject.saveDataInDB(query);
			log.info("insert status: "+b);
			}
		}
	} catch(Exception e) {
		log.error("Exception: ",e);
	}
}

public void checkSummaryDataForEmail(){
	String processType=formObject.getValue(PROCESS_TYPE).toString();
	String prevWS=formObject.getValue(PREV_WS).toString();
	try {
		if ("SCC".equalsIgnoreCase(prevWS) || "TRSD Action".equalsIgnoreCase(prevWS)){
		String sQry = "SELECT REFFERD_TO FROM TFO_DJ_FINAL_DEC_SUMMARY WHERE WI_NAME = '"+sWorkitemID+"'  "; 
		List<List<String>> sOutputlist = formObject.getDataFromDB(sQry);
		log.info("checkSummaryDataForEmail query : "+sQry+" output -"+sOutputlist);
		if (sOutputlist != null && !sOutputlist.isEmpty() && sOutputlist.get(0).size() > 0) {
			String sQueryDelete = "DELETE FROM TFO_DJ_FINAL_DEC_SUMMARY WHERE WI_NAME ='"+sWorkitemID+"' ";
			log.info("Delete "+sQueryDelete);
			int result = formObject.saveDataInDB(sQueryDelete);
			log.info("insert status: "+result);
			}
		}
	} catch(Exception e) {
		log.error("Exception: ",e);
	}
}

//Added by Shivansh ATP-458 18-07-2024 START
private boolean validatePartyCountry(){
	String swiftChannel=formObject.getValue(SWIFT_CHANNEL).toString();
	String partyDesc = "";
	String partyCountry = "";
	if (swiftChannel.equalsIgnoreCase("MT798")) {
		log.info(" Swift Channel : " + swiftChannel);
		String gridListName = LISTVIEW_PARTY;
		int length = getGridCount(gridListName);
		log.info("length : " + length);
		for (int i = 0; i < length; i++) {
			partyDesc = formObject.getTableCellValue(gridListName, i, 1);
			partyCountry = formObject.getTableCellValue(gridListName, i, 8);
			log.info(" value : " + partyDesc + partyCountry);
			if (partyCountry == null || "".equalsIgnoreCase(partyCountry)) {
				sendMessageHashMap(sFieldName, "Please fill " + partyDesc + " Country Name");
				return false;
			}
		}
		return true;
	}
	return true;
}
//Added by Shivansh ATP-458 18-07-2024 END

//Traydstream |atp-520|reyaz|04-11-2024 start
private void setTsRequired(){
	try {
		log.info("inside setTsRequired  PM >> ");
		String custId = formObject.getValue(CUSTOMER_ID).toString();
		String key =custId+"#"+requestCategory+"#"+requestType;
		log.info("key: "+key);
		String value =autoDocChkValueMap.get(key);
		log.info("value: "+value);
		if(value != null && !value.equalsIgnoreCase("")){
			formObject.setValue(TS_REQUIRED,value);
			disableControls(TS_REQUIRED);
		}else {
			formObject.setValue(TS_REQUIRED,"NO");
		}
	} catch (Exception e) {
		log.error("Exception: ",e);
	}
}
//Traydstream |atp-520|reyaz|04-11-2024  end

//added by reyaz	16-05-2023
private void LoadListViewOfPartyScreening() {
	String entityname ="";
	String entity ="";
	List<List<String>> sOutput1;
	JSONArray jsonArray = new JSONArray();
	log.info("Inside LoadListViewOfPartyScreening ");
	try {
		String processType=formObject.getValue(PROCESS_TYPE).toString();
		if("BAU".equalsIgnoreCase(processType) && ("ILCB_BS".equalsIgnoreCase(requestType) || "ELCB_BS".equalsIgnoreCase(requestType)
				|| "IC_BS".equalsIgnoreCase(requestType) || "EC_BS".equalsIgnoreCase(requestType))) {
			List<String> partyList =new ArrayList<String>();
			JSONObject jsonObject = new JSONObject();
			String sQuery1 = "SELECT ENTITY_NAME FROM TFO_DJ_TRSD_SCREENING_DETAILS WHERE WINAME = '"+sWorkitemID+"' AND  EXECUTION_STATUS ='Y' ";
			log.info("sQuery1 : "+sQuery1);
			List<List<String>> ListData = formObject.getDataFromDB(sQuery1);
			for(int i=0;i<ListData.size();i++){
				partyList.add(ListData.get(i).get(0).toString());
			}
			log.info("partyList : "+partyList);
			String sQuery2 = "Select Distinct CUSTOMER_NAME, PARTY_DESC from  TFO_DJ_PARTY_DETAILS  where WINAME = '"+sWorkitemID+"' AND CUSTOMER_NAME is not null";
			log.info("sQuery2 : "+sQuery2);
			sOutput1 = formObject.getDataFromDB(sQuery2);
			log.info("sOutput1 : "+sOutput1);

			if(sOutput1 != null && sOutput1.size() > 0){
				for(int i=0;i<sOutput1.size();i++){ 
					entityname =sOutput1.get(i).get(0).toString();
					entity  =sOutput1.get(i).get(1).toString();
					log.info("partyList :::: "+entityname);
					if(!"".equalsIgnoreCase(entityname)  && entityname != null){
						if(!partyList.contains(entityname)){
							log.info("****Party***** ");
							jsonObject = new JSONObject();
							jsonObject.put("Entity",entity);
							jsonObject.put("Entity_Name", entityname);
							jsonObject.put("TRSD_Screening_Type", "Organisation");
							jsonObject.put("EXECUTION_STATUS","N");
							jsonObject.put("TRSD_CATEGORY","Party");
							log.info("END OF  LoadListViewOfPartyScreening "+jsonObject.toString());
							jsonArray.add(jsonObject);
						}
					}

				}
			}
			log.info("Final json array ::: "+jsonArray);
			formObject.addDataToGrid(LISTVIEW_TRSD_TABLE, jsonArray);
		}
	} catch(Exception e) {
		log.error("Exception: in LoadListViewOfPartyScreening ",e);
	}

}


}





