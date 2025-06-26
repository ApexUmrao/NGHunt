/**
----------------------------------------------------------------------------------------------------
				NEWGEN SOFTWARE TECHNOLOGIES LIMITED
	Group						:	AP2
	Product / Project			:	Trade Finance
	Module						:	iFormUserTFO
	File Name					:	Initiator.java
	Author						:	
	Date written (DD/MM/YYYY)	:	07-Oct 2020
	Description					:	iForm File for Initiator Workstep
----------------------------------------------------------------------------------------------------
				CHANGE HISTORY
----------------------------------------------------------------------------------------------------
 Date	(DD/MM/YYYY)		 Change By	 			Change ID/Bug Id				Change Description (Bug No. (If Any))

----------------------------------------------------------------------------------------------------
 */
package com.newgen.iforms.user.tfo;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.math.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

import com.newgen.iforms.EControl;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.iforms.user.config.Constants;
import com.newgen.iforms.user.tfo.util.XMLParser;
import com.newgen.mvcbeans.model.WorkdeskModel;
//DART 1129678
public class Initiator extends Common implements Constants,
		IFormServerEventHandler {
	boolean sOnClick = false;
	boolean bSubmit = true;

	public Initiator(IFormReference formObject) {
		
		super(formObject);
	}

	@Override
	public void beforeFormLoad(FormDef arg0, IFormReference formObject) {
		log.info("Inside beforeFormLoad >>>");
		String workitemName = formObject.getObjGeneralData()
				.getM_strProcessInstanceId();
		log.info("WorkItem Name: " + workitemName);
	}
//DART 1129714 AYUSH
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
	//DART 1129714 AYUSH
	@Override
	public String executeServerEvent(IFormReference formObject,
			String controlName, String eventType, String data) {
		log.info("Inside executeServerEvent >");
		log.info("Event: " + eventType + ", Control Name: " + controlName
				+ ", Data: " + data);
		String retMsg = getReturnMessage(true);
		sendMessageList.clear();
		String msg = "";
		Boolean success = true;

		try {
			if (eventType.equalsIgnoreCase(EVENT_TYPE_LOAD)) {
				if (CONTROL_NAME_FORM.equalsIgnoreCase(controlName) && data.isEmpty()) {
					String wiName = formObject.getValue("WI_NAME").toString();
					log.info("Inside form load event -- WorkItem Name: "+ wiName);
					Boolean view = true;
					view = setUserDetail();
					//ATP-SCFMVP2.5 --JAMSHED 16-07-2024 START
					if("Yes".equalsIgnoreCase(formObject.getValue(HYBRID_CUSTOMER).toString())) {
						formObject.setValue(TFO_ASSIGNED_CENTER,"");
					}
					//ATP-SCFMVP2.5 --JAMSHED 16-07-2024 END
					initLoadCombo();
					setFetchModule(formObject, SWIFT_FETCH_MODULE);
					if ("".equalsIgnoreCase(formObject.getValue(REQ_DATE_TIME).toString())
						|| null == formObject.getValue(REQ_DATE_TIME).toString()) {
							formObject.setValue(REQ_DATE_TIME, getDate());
					}
					loadProcessType(formObject);
					log.debug("before disabling fields..");
					setOnIntroLoad(formObject.getValue(REQUEST_TYPE).toString());
					onClickReqCatAtIntro(formObject,formObject.getValue(REQUEST_CATEGORY).toString());
					setAckGenBtnShown(formObject,formObject.getValue(SOURCE_CHANNEL).toString());
					setDefaultSwiftDecision(formObject,formObject.getValue(REQUEST_CATEGORY).toString(),
					formObject.getValue(REQUEST_TYPE).toString());
					log.debug("fillProtradeSwiftDelivInst.. Check 1");
				//	fillProtradeSwiftDelivInst(); //Changed By kishan
				//	fillSwiftAmountINCDECB();//Changed by kishan
					if (Boolean.FALSE.equals(view)) {
						formObject.applyGroup(CONTROL_SET_READONLY);
					}
					boolean openJSPOnFormLoad = checkCallStatus(); //Added by Kishan for Swift change
					if(openJSPOnFormLoad){
						return getReturnMessage(true, controlName, "openRepairJSP");
					}
					//ATP-459 11-JUL-2024 --JAMSHED START
					if("Yes".equalsIgnoreCase(formObject.getValue(HYBRID_CUSTOMER).toString())) {
						formObject.setStyle(BUTTON_RETRY, VISIBLE, TRUE);
					}else {
					if(formObject.getValue(PROCESS_TYPE).toString().equalsIgnoreCase("TSLM Front End")){
						enableDisbaleRetryButton();	
					}else{
						formObject.setStyle(BUTTON_RETRY, VISIBLE, FALSE);
					}
					}
					//ATP-459 11-JUL-2024 --JAMSHED END
					
					retMsg = getReturnMessage(view, controlName);
					
				} else if (CONTROL_NAME_FORM.equalsIgnoreCase(controlName)
						&& data.equalsIgnoreCase("post")) {
					enableFetchFields(formObject, false);
					return getReturnMessage(true, controlName, "post");
				}
				disableFieldForDormantCustomer(); ////ATP-416 01-03-2024 --JAMSHED
			} else if (controlName.equalsIgnoreCase(BUTTON_FETCH)
					&& eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)) {
				//String Proc_system = formObject.getValue("PROCESSING_SYSTEM").toString();
				//if(!Proc_system.equalsIgnoreCase("T")){
					try {//DART 1129675 AYUSH
						log.info("inside btnCidFetch >>>");
						//getAPWebserviceForSwiftWI();//testing purpose
						String cid = formObject.getValue(CID).toString().trim();
						String[] tranRefNumFields = enableFetchFields(formObject,true);
						formObject.setStyle(BUTTON_FETCH, DISABLE, TRUE);
						if (tranRefNumFields != null&& tranRefNumFields[0].equals(CID)) {
							log.info("inside btnCidFetch CID not null: "+ formObject.getValue(CID).toString());
							formObject.setStyle(TRANSACTION_CURRENCY, DISABLE,FALSE); // TL_LD
							if (!cid.equalsIgnoreCase("")&& checkLengthValidation(CID, ENTER_CID_MSG, 6)) {
								String fetchMsg = fetchCustomerData(formObject.getValue(CID).toString(), formObject
										.getValue(REQUEST_CATEGORY).toString(),true);
								
								if (!fetchMsg.equalsIgnoreCase("")) {
									success = false;
									msg = "," + fetchMsg;
									retMsg = getReturnMessage(success, controlName,msg);
									formObject.setStyle(BUTTON_FETCH, DISABLE,FALSE);
									return retMsg;
								}
							} else if (cid.equalsIgnoreCase("")) {
								success = false;
								msg = CID + "," + ALERT_CUST_ID;
								formObject.setStyle(CID, ENABLE, TRUE); // us149
								retMsg = getReturnMessage(success, controlName, msg);
								formObject.setStyle(BUTTON_FETCH, DISABLE, FALSE);
								return retMsg;
							} else if (!checkLengthValidation(CID, ENTER_CID_MSG, 6)) {
								success = false;
								msg = CID + ",Please enter correct CID";
								formObject.setStyle(CID, ENABLE, TRUE);// us149
								retMsg = getReturnMessage(success, controlName, msg);
								formObject.setStyle(BUTTON_FETCH, DISABLE, FALSE);
								return retMsg;
							}
						}
						//DART 1129675 AYUSH
						else if (tranRefNumFields != null) {//DART 1129677 AYUSH
							boolean validFlag = false;
							boolean emptyFlag = false;
							String transRefFieldName = "";
							String transRefOutFieldName = "";
							if (!formObject.getValue(REQUEST_CATEGORY).toString().equalsIgnoreCase("TL")
							 && !formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("TL_LD")) { // US146
								log.info("1- tranRefNumFields[0] "+ formObject.getValue(tranRefNumFields[0]).toString()
										+ "2- tranRefNumFields[1] "+ formObject.getValue(tranRefNumFields[1]).toString());
								if (formObject.getValue(tranRefNumFields[0]).toString().equalsIgnoreCase("")
										&& formObject.getValue(tranRefNumFields[1]).toString().equalsIgnoreCase("")) {
									emptyFlag = true;
									transRefFieldName = tranRefNumFields[0];
								} else if (!formObject.getValue(tranRefNumFields[0]).toString().equalsIgnoreCase("")
										&& !formObject.getValue(tranRefNumFields[1]).toString().equalsIgnoreCase("")) {
									validFlag = false;
									transRefFieldName = tranRefNumFields[0];
								} else {
									validFlag = true;
									if (!formObject.getValue(tranRefNumFields[0]).toString().equalsIgnoreCase("")) {
										log.info("interchanging trans ref fields");
										transRefOutFieldName = tranRefNumFields[1];
										transRefFieldName = tranRefNumFields[0];
									} else {
										log.info("not interchanging trans ref fields");
										transRefOutFieldName = tranRefNumFields[0];
										transRefFieldName = tranRefNumFields[1];
									}
								}
							} else {// US146
								log.info("CASE OF TL");
								if (formObject.getValue(tranRefNumFields[0]).toString().equalsIgnoreCase("")) {
									emptyFlag = true;
								}
								log.info("INITIATOR requestType: "+requestType);
								if("TL_LD".equalsIgnoreCase(requestType) 
									&& formObject.getValue(IFS_LOAN_GRP).toString().equalsIgnoreCase("")) {
									emptyFlag = true;
								}
								validFlag = true;
								transRefFieldName = tranRefNumFields[0];
							}
							log.info("inside btnCidFetch tranRefNumFields emptyFlag: " + emptyFlag);
							log.info("inside btnCidFetch tranRefNumFields validFlag: " + validFlag);
							log.info("inside btnCidFetch tranRefNumFields transRefFieldName:" + transRefFieldName);
							log.info("inside btnCidFetch tranRefNumFields transRefFieldName value:" 
									+ formObject.getValue(transRefFieldName).toString());
							log.info("inside btnCidFetch tranRefNumFields transRefOutFieldName: " + transRefOutFieldName);
							if (emptyFlag) {
								log.info("when empty flag true ..");
								if (formObject.getValue(REQUEST_CATEGORY).toString().equalsIgnoreCase("TL")
										&& formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("TL_LD")) {// us146
									log.info("when Loan Group");
									if (formObject.getValue(IFS_LOAN_GRP).toString().trim().equalsIgnoreCase("")) {
										success = false;
										msg = IFS_LOAN_GRP + "," + ALERT_LOAN_GRP;
										retMsg = getReturnMessage(success,controlName, msg);
										return retMsg;
									}
								}
								//DART 1132891
								if (transRefFieldName.equalsIgnoreCase(CID)) {
									log.info("when CID_txt");
									if (formObject.getValue(transRefFieldName).toString().trim().equalsIgnoreCase("")) {
										success = false;
										msg = ALERT_CUST_ID;
										retMsg = getReturnMessage(success,controlName, msg);
										return retMsg;
									}//DART 1132072 AYUSH
								} else {
									log.info("formObject.getValue(transRefFieldName).toString(): "
											+ formObject.getValue(transRefFieldName).toString());
									if (formObject.getValue(transRefFieldName)
											.toString().equalsIgnoreCase("")) {
										log.info("show alert for trans ref number");
										success = false;
										msg = TRANSACTION_REFERENCE + ","+ ALERT_TRNS_REF;
										retMsg = getReturnMessage(success,controlName, msg);
										return retMsg;
									}
								}

							} else if (!validFlag) {//DART 1129711 AYUSH
								success = false;
								msg = transRefFieldName
										+ ","
										+ "Please Enter Single Transaction Ref. Number";
								retMsg = getReturnMessage(success, controlName, msg);
								return retMsg;
							} else {
								if ((transRefFieldName.equalsIgnoreCase(CID) && (checkLengthValidation(
										CID, ENTER_CID_MSG, 6)))
										|| checkMaxLengthValidation(
												transRefFieldName, MAX_LENGTH_MSG,
												16)) {
									log.info("inside btnCidFetch FINAL FETCH BLOCK");
									log.info("transRefFieldName: "
											+ transRefFieldName + " $$ "
											+ "transRefOutFieldName:"
											+ transRefOutFieldName);
									boolean otherRefNum = false;
									String refNo = formObject.getValue(
											transRefFieldName).toString();
									if (!transRefFieldName.equals(TRNS_REF_NO)
											&& !transRefFieldName
													.equalsIgnoreCase("TXT_TRANSACTION_ADCB_LC_REFERENCE")) {
										otherRefNum = true;
									}
									if (transRefFieldName.equals(TRNS_REF_NO)) {
										transRefFieldName = TRANSACTION_REFERENCE;
									}
									if (transRefOutFieldName.equals(TRNS_REF_NO)) {
										transRefOutFieldName = TRANSACTION_REFERENCE;
									}
									// if(!transRefOutFieldName.equals(TRANSACTION_REFERENCE)
									// &&
									// !transRefOutFieldName.equals("TRANSACTION_ADCB_LC_REFERENCE")){
									/*if (!formObject.getValue(REQUEST_CATEGORY)
											.toString().equalsIgnoreCase("TL")
											&& !formObject.getValue(REQUEST_TYPE)
													.toString()
													.equalsIgnoreCase("TL_LD")) {// US146*/
									if (!formObject.getValue(REQUEST_TYPE)
													.toString()
													.equalsIgnoreCase("TL_LD")) {// US146*/
										if (!transRefOutFieldName.equals(TRANSACTION_REFERENCE)
												&& !transRefOutFieldName.equals("TXT_TRANSACTION_ADCB_LC_REFERENCE")) {
											log.info("IF transFieldName: "
													+ transRefFieldName
													+ "transRefOutFieldName: "
													+ transRefOutFieldName);
											String fetchMsg = fetchTransactioDtls(refNo,
													formObject.getValue(REQUEST_CATEGORY).toString(),
													formObject.getValue(REQUEST_TYPE).toString(),
													transRefFieldName,
													transRefOutFieldName,
													otherRefNum);
											if (!fetchMsg.equalsIgnoreCase("")) {
												success = false;
												msg = "," + fetchMsg;
												retMsg = getReturnMessage(success,controlName, msg);
												formObject.setStyle(BUTTON_FETCH,DISABLE, FALSE);
												return retMsg;
											}
										} else {
											log.info("ELSE transFieldName: "
													+ transRefFieldName
													+ "transRefOutFieldName: "
													+ transRefOutFieldName);
											String fetchMsg = fetchTransactioDtls(
													refNo,
													formObject.getValue(REQUEST_CATEGORY).toString(), formObject
															.getValue(REQUEST_TYPE).toString(),
													transRefOutFieldName,
													transRefFieldName, otherRefNum);
											if (!fetchMsg.equalsIgnoreCase("")) {
												success = false;
												msg = "," + fetchMsg;
												retMsg = getReturnMessage(success,
														controlName, msg);
												formObject.setStyle(BUTTON_FETCH,
														DISABLE, FALSE);
												return retMsg;
											}
										}
									} else { // US146
										// if(transRefOutFieldName.equals("BILL_LN_REFRNCE")){
										log.info("IF ADCB Bill Ref: "
												+ transRefFieldName + "");
										formObject.setStyle(BILL_LN_REFRNCE,
												ENABLE, TRUE);
										String fetchMsg = fetchTransactioDtls(refNo,
												formObject.getValue(REQUEST_CATEGORY).toString(), formObject
												.getValue(REQUEST_TYPE).toString(),BILL_LN_REFRNCE, "", false);
										if (!fetchMsg.equalsIgnoreCase("")) {
											success = false;
											msg = "," + fetchMsg;
											retMsg = getReturnMessage(success,controlName, msg);
											formObject.setStyle(BUTTON_FETCH,DISABLE, FALSE);
											return retMsg;
										}
										// }
									}
									String chckTrnAmt = chkAmount(
											TRANSACTION_AMOUNT, "");
									if (!chckTrnAmt.equalsIgnoreCase("")) {
										msg = TRANSACTION_AMOUNT + "," + chckTrnAmt;
										retMsg = getReturnMessage(success,
												controlName, msg);
										return retMsg;
									}
									String chckTrnBal = chkAmount(TRNS_BAL, "");
									if (!chckTrnBal.equalsIgnoreCase("")) {
										msg = "TRNS_BAL," + chckTrnBal;
										retMsg = getReturnMessage(success,
												controlName, msg);
										return retMsg;
									}
									String chckConfAmt = chkAmount(LC_CONF_AMNT, "");
									if (!chckTrnAmt.equalsIgnoreCase("")) {
										msg = "LC_CONF_AMNT," + chckConfAmt;
										retMsg = getReturnMessage(success,
												controlName, msg);
										return retMsg;
									}
								} else if (!checkLengthValidation(CID,
										ENTER_CID_MSG, 6)) {
									success = false;
									msg = CID + "," + ENTER_CID_MSG;
									retMsg = getReturnMessage(success, controlName,
											msg);
									return retMsg;
								} else if (!checkMaxLengthValidation(
										transRefFieldName, MAX_LENGTH_MSG, 16)) {
									success = false;
									msg = transRefFieldName + "," + MAX_LENGTH_MSG;
									retMsg = getReturnMessage(success, controlName,
											msg);
									return retMsg;
								}
							}
						}
						// ATP-416 REYAZ 27-02-2024
						// START CODE
						if(checkdormantCustomer()){ 
							success = false;
							msg = BUTTON_FETCH + ","+"Please note subject customer "+formObject.getValue(CUSTOMER_ID).toString()+" is Dormant or InActive in our Core banking system. Please follow dormant"
									+ " customer activation process to change customer status to Active to proceed with the journey. Contact Branch for activation.";
							retMsg = getReturnMessage(success, BUTTON_FETCH, msg);
							formObject.setStyle(BUTTON_SUBMIT, DISABLE, TRUE);
							formObject.setStyle(BUTTON_CUSTOMER_ACK, DISABLE, TRUE);
							return retMsg;
						} else {
							formObject.setStyle(BUTTON_SUBMIT, DISABLE, FALSE);
							formObject.setStyle(BUTTON_CUSTOMER_ACK, DISABLE, FALSE);
						}
						// END CODE
						formObject.setStyle(BUTTON_FETCH, DISABLE, FALSE);
						success = true;
						msg = "";
						retMsg = getReturnMessage(success, controlName, msg);
						return retMsg;
					} catch (Exception e) {
						log.error("exception in fetch button: ", e);
					}		
				/*}else{
					String Output = populateTSLMLoanDetails();
					log.info("TLMS WEBSERVICE OUTPUT" + Output);
				}*/
			} else if (BUTTON_CUSTOMER_ACK.equalsIgnoreCase(controlName)
					&& EVENT_TYPE_CLICK.equalsIgnoreCase(eventType)) {
				log.info("generating ACK");
				tempGenConfig(formObject);
				log.info("ACK generated");
			}
			//ATP-459 11-JUL-2024 --JAMSHED START
			else if(BUTTON_RETRY.equalsIgnoreCase(controlName) 
					&& EVENT_TYPE_CLICK.equalsIgnoreCase(eventType)){
				uploadDocFromPayload();
			} 
			//ATP-459 11-JUL-2024 --JAMSHED END
			else if (FLG_ACK_GEN.equalsIgnoreCase(controlName)
					&& EVENT_TYPE_CHANGE.equalsIgnoreCase(eventType)) {
				log.info("before calling setBtnOnAckChk..ack value: "
						+ formObject.getValue(FLG_ACK_GEN).toString()
								.toLowerCase());
				setBtnOnAckChk(EVENT_TYPE_CLICK,
						formObject.getValue(FLG_ACK_GEN).toString()
								.toLowerCase());
				return retMsg;
			} else if (REQUEST_CATEGORY.equalsIgnoreCase(controlName)
					&& EVENT_TYPE_CHANGE.equalsIgnoreCase(eventType)) {
				log.info("Request Category Code"
						+ formObject.getValue(REQUEST_CATEGORY).toString());
				reqCatClick = false;
				onChangeReqCatAtIntro(formObject,
						formObject.getValue(REQUEST_CATEGORY).toString());

				setAckGenBtnShown(formObject.getValue(SOURCE_CHANNEL)
						.toString());
				setFetchModule(formObject, SWIFT_FETCH_MODULE); // 26oct
				disableFetchFields(formObject);
				enableFetchFields(formObject, false);
				if (SWIFT.equalsIgnoreCase(formObject.getValue(PROCESS_TYPE)
						.toString())) {
					loadSwiftDecisionCombo(formObject
							.getValue(REQUEST_CATEGORY).toString(), formObject
							.getValue(REQUEST_TYPE).toString());
					setDefaultSwiftDecision(formObject,
							formObject.getValue(REQUEST_CATEGORY).toString(),
							formObject.getValue(REQUEST_TYPE).toString());
					enableDisableSwiftFields(formObject);
				}
			} else if (REQUEST_TYPE.equalsIgnoreCase(controlName)
					&& EVENT_TYPE_CHANGE.equalsIgnoreCase(eventType)) {
				log.info("Click Event of TFO_REQUEST_TYPE control");
				setFetchModule(formObject, SWIFT_FETCH_MODULE);
				onClickReqCatAtIntro(formObject,formObject.getValue(REQUEST_CATEGORY).toString());
				setAckGenBtnShown(formObject.getValue(SOURCE_CHANNEL).toString());
				disableFetchFields(formObject);
				enableFetchFields(formObject, false);
				if (SWIFT.equalsIgnoreCase(formObject.getValue(PROCESS_TYPE).toString())) {
					loadSwiftDecisionCombo(formObject.getValue(REQUEST_CATEGORY).toString(), formObject
							.getValue(REQUEST_TYPE).toString());
					setDefaultSwiftDecision(formObject,formObject.getValue(REQUEST_CATEGORY).toString(),
							formObject.getValue(REQUEST_TYPE).toString());
					enableDisableSwiftFields(formObject);
				}
				String reqCatDesc = getDescriptionFromCode(REQUEST_CATEGORY,
						formObject.getValue(REQUEST_CATEGORY).toString());
				log.info("req cat description from getDescriptionFromCode: "
						+ reqCatDesc);
			} else if (SWIFT_MESSAGE_TYPE.equalsIgnoreCase(controlName)
					&& EVENT_TYPE_CHANGE.equalsIgnoreCase(eventType)) {
				loadSwiftDecisionCombo(formObject.getValue(REQUEST_CATEGORY)
						.toString(), formObject.getValue(REQUEST_TYPE)
						.toString());
				setDefaultSwiftDecision(formObject,
						formObject.getValue(REQUEST_CATEGORY).toString(),
						formObject.getValue(REQUEST_TYPE).toString());
			} else if (SOURCE_CHANNEL.equalsIgnoreCase(controlName)
					&& EVENT_TYPE_CHANGE.equalsIgnoreCase(eventType)) {
				setAckGenBtnShown(formObject.getValue(SOURCE_CHANNEL)
						.toString());
			} else if (REQUESTED_BY.equalsIgnoreCase(controlName)
					&& EVENT_TYPE_CHANGE.equalsIgnoreCase(eventType)) {
				setAckGenBtnShown(formObject.getValue(SOURCE_CHANNEL)
						.toString());
			} else if (PROCESS_TYPE.equalsIgnoreCase(controlName)
					&& EVENT_TYPE_CHANGE.equalsIgnoreCase(eventType)) { // US3388
				disableFetchFields(formObject);
				enableFetchFields(formObject, false);
				if (SWIFT.equalsIgnoreCase(formObject.getValue(PROCESS_TYPE)
						.toString())) {
					loadSwiftDecisionCombo(formObject
							.getValue(REQUEST_CATEGORY).toString(), formObject
							.getValue(REQUEST_TYPE).toString());
					setDefaultSwiftDecision(formObject,
							formObject.getValue(REQUEST_CATEGORY).toString(),
							formObject.getValue(REQUEST_TYPE).toString());
					enableDisableSwiftFields(formObject);
				}
			} else if (SWIFT_FETCH_MODULE.equalsIgnoreCase(controlName)) {
				disableFetchFields(formObject);
				enableFetchFields(formObject, false);

			} else if (SWIFT_PROCESSING_STATUS.equalsIgnoreCase(controlName)
					&& EVENT_TYPE_CHANGE.equalsIgnoreCase(eventType)) {
				disableFetchFields(formObject);
				enableFetchFields(formObject, false);
			} else if (TRANSACTION_AMOUNT.equalsIgnoreCase(controlName)
					&& EVENT_TYPE_LOSTFOCUS.equalsIgnoreCase(eventType)) {
				log.info("Small v" + formObject.getValue(TRANSACTION_AMOUNT).toString());
				msg = chkAmount(TRANSACTION_AMOUNT, "");
				if (!msg.equalsIgnoreCase(""))
					success = false;
				retMsg = getReturnMessage(success, controlName, msg);
				return retMsg;
			} else if (BUTTON_SUBMIT.equalsIgnoreCase(controlName)
					&& EVENT_TYPE_CLICK.equalsIgnoreCase(eventType)) {
				log.info("Inside Submit");
				executeUTCCalls(formObject);
				//ATP-463 16-MAY-2024 --JAMSHED START
				PPM ppm=new PPM(formObject);
				ppm.fetchPastDueLiability();
				//ATP-463 16-MAY-2024 --JAMSHED END
				if (isAckGenerated(formObject)) {
					log.info("bSubmit= "
							+ bSubmit);
					if (bSubmit) {
						bSubmit = false;
						formObject.setStyle(BUTTON_SUBMIT, DISABLE, TRUE);
						log.info("Final Submit");
						formObject.setValue(DEC_CODE, "App");
						formObject.setValue(DECISION, "Accept");
						List<String> paramlist = new ArrayList<>();
						paramlist.add("Text:" + sWorkitemID);
						paramlist.add("Text:Y");
						log.info("Final Submit" + sWorkitemID);
						List<String> statusProc = formObject
								.getDataFromStoredProcedure(
										"TFO_DJ_EMAIL_CONFIG", paramlist);
						log.info("getDataFromStoredProcedure TFO_DJ_EMAIL_CONFIG list returned: "
								+ statusProc);
						dedupeCheck();
						saveDecHistory();
						if(checkSwiftDocument()){
							bSubmit = true;
							log.info("sendMessageList=" + sendMessageList);
							if (!sendMessageList.isEmpty()) {
								return getReturnMessage(false, "", sendMessageList.get(0)
										+ "###" + sendMessageList.get(1));
							}
						}//RR
						
						//ATP-493,ATP-494 29-07-2024 REYAZ  STA
						if(validateFetchButton()){
							bSubmit = true;
							log.info("sendMessageList=" + sendMessageList);
							if (!sendMessageList.isEmpty()) {
								return getReturnMessage(false, "", sendMessageList.get(0)
										+ "###" + sendMessageList.get(1));
							}
						}
						
						if(checkHybridCustomerDocument()){
							bSubmit = true;
							log.info("sendMessageList=" + sendMessageList);
							if (!sendMessageList.isEmpty()) {
								return getReturnMessage(false, "", sendMessageList.get(0)
										+ "###" + sendMessageList.get(1));
							}
						}
						//ATP-493,ATP-494 29-07-2024 REYAZ  END
						submitSWIFT(formObject, sWorkitemID);
						log.info("Final workitem name" + sWorkitemID);
						updateFetchTransactionOrches(sWorkitemID);
						//fillSwiftAmountINCDECB(); //25/11/21
						//fillProtradeSwiftDelivInst(); //25/11/21
						success = true;
						retMsg = getReturnMessage(success);
						return retMsg;
					}
				} else {
					success = false;
					retMsg = getReturnMessage(success);
					return retMsg;
				}
			} else if (BUTTON_DISCARD.equalsIgnoreCase(controlName)
					&& EVENT_TYPE_CLICK.equalsIgnoreCase(eventType)) {
				saveDecHistory();
			} else if (IFS_LOAN_GRP.equalsIgnoreCase(controlName)
					&& EVENT_TYPE_CHANGE.equalsIgnoreCase(eventType)) { // US145
				formObject.setValue(BILL_LN_REFRNCE, ""); // US146
				formObject.setValue(CID, ""); // US146
				enableFetchFields(formObject, false);
			}
			log.info("sendMessageList=" + sendMessageList);
			if (!sendMessageList.isEmpty()) {
				return getReturnMessage(false, "", sendMessageList.get(0)
						+ "###" + sendMessageList.get(1));
			}
		} catch (Exception e) {
			log.error("Exception in Event- " + eventType + "control name- "
					+ controlName + ": " + e, e);
		}
		return retMsg;
	}

	public void disableFetchFields(IFormReference formObject) {
		log.info("disableFetchFields");
		try {
			formObject.setValue(CID, "");
			formObject.setValue(TRNS_REF_NO, "");
			formObject.setValue("TXT_TRANSACTION_UNB_REFERENCE", "");
			formObject.setValue("TXT_TRANSACTION_ADCB_LC_REFERENCE", "");
			formObject.setValue("TXT_TRANSACTION_UNB_LC_REFERENCE", "");
			formObject.setValue("TRANSACTION_UNB_REFERENCE", "");
			formObject.setValue("TRANSACTION_ADCB_LC_REFERENCE", "");
			formObject.setValue("TRANSACTION_UNB_LC_REFERENCE", "");
			formObject.applyGroup(CONTROL_SET_DISABLEFETCHFIELDS);
		} catch (Exception e) {
			log.error("exception in disableFetchFields: " + e, e);
		}
	}

	@SuppressWarnings({ "unchecked" })
	private void setFetchModule(IFormReference formObject, String controlName) {
		try {
			log.info("inside setFetchModule >>>");
			String requestCat = formObject.getValue(REQUEST_CATEGORY)
					.toString();
			String processType = formObject.getValue(PROCESS_TYPE).toString();
			List<List<String>> recordList = null;
			String sQuery1 = "SELECT DEFAULT_VALUE FROM TFO_DJ_FETCH_MODULE_DEF_MAST WHERE REQUEST_CATEGORY_CODE='"
					+ requestCat + "'";
			log.info("Query: " + sQuery1);
			recordList = formObject.getDataFromDB(sQuery1);
			if (!recordList.isEmpty() && processType.equalsIgnoreCase(SWIFT)) {
				log.info("setFetchModule default value: "
						+ ((String) ((List) recordList.get(0)).get(0)));
				formObject.setValue(controlName,
						((String) ((List) recordList.get(0)).get(0)));
			} else {
				formObject.setValue(controlName, "");
			}
			log.info("inside setFetchModule, req type: "
					+ formObject.getValue(REQUEST_TYPE).toString());
			if (processType.equalsIgnoreCase(SWIFT)
					&& formObject.getValue(REQUEST_TYPE).toString()
							.contains("_MSM")) {
				formObject.setStyle(controlName, DISABLE, FALSE);
			} else {
				log.debug("else part swift and not msm..");
				formObject.setStyle(controlName, DISABLE, TRUE);
			}
		} catch (Exception e) {
			log.error("exception in setFetchModule: " + e, e);
		}
	}

	public void loadProcessType(IFormReference formObject) {
		try {
			log.info("inside loadProcessType >>>");
			String processsType = formObject.getValue(PROCESS_TYPE).toString();
			log.info("processsType=" + processsType);
			String strq = "SELECT SWIFT_UTILITY_FLAG FROM EXT_TFO WHERE WI_NAME='"
					+ sWorkitemID + "'";
			log.info(strq);
			List<List<String>> sOutputlist = formObject.getDataFromDB(strq);
			if (sOutputlist != null && !sOutputlist.isEmpty()) {
				log.info("when SWIFT_UTILITY_FLAG="
						+ ((String) ((List) sOutputlist.get(0)).get(0)));
				if ("Y".equalsIgnoreCase(((String) ((List) sOutputlist.get(0))
						.get(0)))) {
					formObject.setValue(PROCESS_TYPE, SWIFT);
					formObject.setStyle(PROCESS_TYPE, DISABLE, TRUE);
				} else if (("".equalsIgnoreCase(processsType))
						|| ("null".equalsIgnoreCase(processsType))) {
					formObject.setValue(PROCESS_TYPE, "BAU");
					formObject.setStyle(PROCESS_TYPE, DISABLE, TRUE);
				}
			} else if ("".equalsIgnoreCase(processsType)
					|| "null".equalsIgnoreCase(processsType)) {
				log.info("loadProcessType- set process type BAU by default..");
				formObject.setValue(PROCESS_TYPE, "BAU");
				formObject.setStyle(PROCESS_TYPE, DISABLE, FALSE);
				formObject.removeItemFromCombo(PROCESS_TYPE, 4);
			}
			if (!formObject.getValue(PT_UTILITY_FLAG).equals("Y")) {
				formObject.removeItemFromCombo(PROCESS_TYPE, 3);
			}
			log.info("process type value after being set: "
					+ formObject.getValue(PROCESS_TYPE).toString());
			if ("BAU".equalsIgnoreCase(formObject.getValue(PROCESS_TYPE)
					.toString())
					|| "PT".equalsIgnoreCase(formObject.getValue(PROCESS_TYPE)
							.toString())) {
				log.info("loadProcessType- process type BAU/PT: "
						+ formObject.getValue(PROCESS_TYPE).toString());
				String tmp = formObject.getValue(REQUEST_CATEGORY).toString();
				formObject.clearCombo(REQUEST_CATEGORY);
				loadComboWithCode(
						"SELECT REQUEST_CATEGORY,REQUEST_CATEGORY_ID FROM TFO_DJ_REQUEST_CATEGORY_MAST where "
								+ "process_type ='"
								+ formObject.getValue(PROCESS_TYPE).toString()
								+ "' ORDER BY TO_NUMBER(ID)", REQUEST_CATEGORY);
				if (!tmp.equalsIgnoreCase("")) {
					formObject.setValue(REQUEST_CATEGORY, tmp);
				}
			} else if (SWIFT.equalsIgnoreCase(formObject.getValue(PROCESS_TYPE)
					.toString())) {
				log.info("loadProcessType- process type SWIFT..");
				String tmp = formObject.getValue(REQUEST_CATEGORY).toString();
				formObject.clearCombo(REQUEST_CATEGORY);
				loadComboWithCode(
						"SELECT REQUEST_CATEGORY,REQUEST_CATEGORY_ID FROM TFO_DJ_REQUEST_CATEGORY_MAST where process_type ='SWIFT' ORDER BY TO_NUMBER(ID)",
						REQUEST_CATEGORY);
				if (!tmp.equalsIgnoreCase("")) {
					formObject.setValue(REQUEST_CATEGORY, tmp);
				}
			}
		} catch (Exception e) {
			log.error("exception in loadprocesstype: " + e, e);
		}

	}

	@SuppressWarnings({ "unchecked" })
	private void onClickReqCatAtIntro(IFormReference formObject, String reqCat) {
		try {
			log.info("inside onClickReqCatAtIntro >>>");
			if (null != reqCat) {
				log.info("if req cat is not null..value: " + reqCat);
				if (listSourceAck.isEmpty()) {
					log.debug("before setting listSourceAck ..");
					listSourceAck = setMasterValues(formObject
							.getDataFromDB("SELECT SOURCE_KEY||'###'||IS_ACK FROM TFO_DJ_SOURCE_CHANNEL_MAST WHERE REQ_CAT_CODE='"
									+ reqCat + "'"));
				}
				log.info("Query for Source After call" + listSourceAck);
				if (listReqTypeAck.isEmpty()) {
					listReqTypeAck = setMasterValues(formObject
							.getDataFromDB("SELECT REQUEST_TYPE_ID||'###'||NEW_ISSUANCE_YES_NO FROM TFO_DJ_REQUEST_TYPE_MAST WHERE REQ_CAT_CODE='"
									+ reqCat + "'"));
				}
				log.info("Query for Request Type After call" + listReqTypeAck);
				if (listReqSubAck.isEmpty()) {
					listReqSubAck = setMasterValues(formObject
							.getDataFromDB("SELECT REQ_SUBBY_KEY||'###'||IS_ACK FROM TFO_DJ_REQ_SUB_BY_MAST WHERE REQ_CAT_CODE='"
									+ reqCat + "'"));
				}
				log.info("Query for Request Type After call" + listReqTypeAck);
			}
		} catch (Exception e) {
			log.error("Exception:", e);
		}
	}

	protected void setAckGenBtnShown(IFormReference formObject, String source) {
		log.info("setAckGenBtnShown source  " + source);
		if ("Initiator".equalsIgnoreCase(sActivityName)) {
			if (chkAckBtVisible(source)) {
				log.info("making customer ack button visible");
				formObject.setStyle(BUTTON_CUSTOMER_ACK, VISIBLE, TRUE);
				setBtnOnAckChk("ACKCHK", "Y");
			} else {
				log.info("making customer ack button visible chkackbtvisible returned false");
				formObject.setStyle(BUTTON_CUSTOMER_ACK, VISIBLE, FALSE);
				setBtnOnAckChk("ACKCHK", "N");
			}
		}
	}
	//Added by Ayush
	public void initiatorhybridcombo(IFormReference formObject){
		log.info("Ayush - inside initiatorhybridcombo >>>" );
		String processtype = formObject.getValue(PROCESS_TYPE).toString();
		if(processtype.equalsIgnoreCase("TSLM Front End")){
			log.info("Ayush - inside if - TSLM Front End initiatorhybridcombo >>>" );
			//Enable Hybrid Customer at Initiator
			formObject.setStyle("HYBRID_CUST_INIT", ENABLE, TRUE);
			}
	}
	
	//Added by Ayush
//	public void hybridpopulate(IFormReference formObject){
//		log.info("Ayush - inside hybridpopulate >>>" );
//		String hybridcomboval =formObject.getValue("HYBRID_CUST_INIT").toString();
//		if(hybridcomboval.equalsIgnoreCase("No")){
//			log.info("Ayush - inside if No- hybridpopulate >>>" );
//			formObject.setStyle("HYBRID_CUST_PPM", ENABLE, FALSE);
//			formObject.setStyle("HYBRID_CUST_PC", ENABLE, FALSE);
//		}
//	}

	private String[] enableFetchFields(IFormReference formObject,boolean returnData) {
		String[] fieldList = new String[0];
		String reqType = formObject.getValue(REQUEST_TYPE).toString();
		String reqCat = formObject.getValue(REQUEST_CATEGORY).toString();
		String processType = formObject.getValue(PROCESS_TYPE).toString();

		try {
			log.info("inside enableFetchFields >>>" + reqCat + "reqType="+ reqType);
			String enableFieldNames = "";
			if ("ELC_LCA".equalsIgnoreCase(formObject.getValue(REQUEST_TYPE)
					.toString())
					&& SWIFT.equalsIgnoreCase(formObject.getValue(PROCESS_TYPE)
							.toString())
					&& ("700".equalsIgnoreCase(formObject.getValue(
							SWIFT_MESSAGE_TYPE).toString()) || "710"
							.equalsIgnoreCase(formObject.getValue(
									SWIFT_MESSAGE_TYPE).toString()))) {
				String processingStatus = formObject.getValue(
						SWIFT_PROCESSING_STATUS).toString();
				log.info("processingStatus: " + processingStatus);
				if ("P".equalsIgnoreCase(processingStatus)) {
					enableFieldNames = "TrnsRefNo#~#TXT_TRANSACTION_UNB_REFERENCE";
					disableFieldOnDemand(CID);
				} else if ("R".equalsIgnoreCase(processingStatus)) {
					enableFieldNames = CID;
					disableFieldOnDemand("TrnsRefNo#~#TRANSACTION_UNB_REFERENCE");
				}
			}
			else if (reqCat.equalsIgnoreCase("TL")
					&& reqType.equalsIgnoreCase("TL_LD")) { // US 145
				disableFieldOnDemand("CID_Txt####BILL_LN_REFRNCE####TRANSACTION_CURRENCY");
				log.info("enableFetchFields of TL CASE: "
						+ "SELECT FIELD_NAME FROM TFO_DJ_RECEPTION_FETCH_MAST_LOAN_GROUP WHERE REQ_CAT_CODE = '"
						+ formObject.getValue(REQUEST_CATEGORY)
						+ "' AND "
						+ "REQ_TYPE_CODE = '"
						+ formObject.getValue(REQUEST_TYPE)
						+ "' AND (SWIFT_FETCH_MODULE='NA' OR SWIFT_FETCH_MODULE='"
						+ formObject.getValue(SWIFT_FETCH_MODULE)
						+ "') AND PROCESS_TYPE='"
						+ formObject.getValue(PROCESS_TYPE) + "'"
						+ "AND Loan_Group ='"
						+ formObject.getValue(IFS_LOAN_GRP) + "' ");
				enableFieldNames = setMasterValues(formObject
						.getDataFromDB("SELECT FIELD_NAME FROM TFO_DJ_RECEPTION_FETCH_MAST_LOAN_GROUP "
								+ "WHERE REQ_CAT_CODE = '"
								+ formObject.getValue(REQUEST_CATEGORY)
								+ "' AND "
								+ "REQ_TYPE_CODE = '"
								+ formObject.getValue(REQUEST_TYPE)
								+ "' AND (SWIFT_FETCH_MODULE='NA' OR SWIFT_FETCH_MODULE='"
								+ formObject.getValue(SWIFT_FETCH_MODULE)
								+ "') AND PROCESS_TYPE='"
								+ formObject.getValue(PROCESS_TYPE)
								+ "' "
								+ "AND Loan_Group ='"
								+ formObject.getValue(IFS_LOAN_GRP) + "'"));
				log.info("Changing return Data fro TL Case");
				returnData = false;
			} else {
				log.info("enableFetchFields: "
						+ "SELECT FIELD_NAME FROM TFO_DJ_RECEPTION_FETCH_MAST WHERE REQ_CAT_CODE = '"
						+ formObject.getValue(REQUEST_CATEGORY)
						+ "' AND "
						+ "REQ_TYPE_CODE = '"
						+ formObject.getValue(REQUEST_TYPE)
						+ "' AND (SWIFT_FETCH_MODULE='NA' OR SWIFT_FETCH_MODULE='"
						+ formObject.getValue(SWIFT_FETCH_MODULE)
						+ "') AND PROCESS_TYPE='"
						+ formObject.getValue(PROCESS_TYPE) + "'");
				enableFieldNames = setMasterValues(formObject
						.getDataFromDB("SELECT FIELD_NAME FROM TFO_DJ_RECEPTION_FETCH_MAST WHERE REQ_CAT_CODE = '"
								+ formObject.getValue(REQUEST_CATEGORY)
								+ "' AND "
								+ "REQ_TYPE_CODE = '"
								+ formObject.getValue(REQUEST_TYPE)
								+ "' AND (SWIFT_FETCH_MODULE='NA' OR SWIFT_FETCH_MODULE='"
								+ formObject.getValue(SWIFT_FETCH_MODULE)
								+ "') AND PROCESS_TYPE='"
								+ formObject.getValue(PROCESS_TYPE) + "'"));
			}
			log.info("enableFetchFields enableFieldNames: " + enableFieldNames);
			fieldList = enableFieldNames.split("#~#");
			if (!returnData) {
				formObject.setStyle(BUTTON_FETCH, DISABLE, FALSE);
				for (int counter = 0; counter < fieldList.length; counter++) {
					log.info("working enableFetchFields fieldName: "
							+ fieldList[counter]);
					formObject.setStyle(fieldList[counter], DISABLE, FALSE);
				}
			}
			return fieldList;
		} catch (Exception e) {
			log.error("exception in enableFetchFields: ", e);
		}
		return fieldList;
	}

	public void setDefaultSwiftDecision(IFormReference formObject,
			String reqCategory, String reqType) {
		String sQuery;
		String defValue = "";
		String tableName = "";
		String makeDisable = "";
		String sWhere = "";
		try {
			log.info("msg form value setDefaultSwiftDecision: "
					+ formObject.getValue(SWIFT_MESSAGE_TYPE).toString());
			log.info("swiftDecisionMaterCount :" + swiftDecisionMaterCount);
			if (swiftDecisionMaterCount == 0) {
				tableName = "TFO_DJ_SWIFT_DEFAULT_DEC_MAST";
				sWhere = "";
			} else {
				tableName = "TFO_DJ_DEC_SWIFT_MESSAGE";
				sWhere = " AND REQ_CAT_CODE = '" + reqCategory
						+ "' AND REQ_TYPE_CODE = '" + reqType + "' ";

			}
			sQuery = "SELECT DEFAULT_VALUE,IS_DISABLE FROM " + tableName
					+ " WHERE MSG_TYPE = '"
					+ formObject.getValue(SWIFT_MESSAGE_TYPE).toString() + "' "
					+ sWhere + "";
			log.info("sQuery setDefaultSwiftDecision : " + sQuery);
			@SuppressWarnings("unchecked")
			List<List<String>> record = formObject.getDataFromDB(sQuery);
			if ((!record.isEmpty())
					&& !"".equalsIgnoreCase(formObject.getValue(
							SWIFT_MESSAGE_TYPE).toString())
					&& null != formObject.getValue(SWIFT_MESSAGE_TYPE)
							.toString()) {
				defValue = ((String) ((List) record.get(0)).get(0));
				log.info("set swift decision as ..." + defValue);
				makeDisable = record.get(0).get(1);
				if (!"".equalsIgnoreCase(defValue) && null != defValue) {
					formObject.setValue(SWIFT_DEC_ON_SWIFT_MESSAGE, defValue);
					if ("Y".equalsIgnoreCase(makeDisable)) {
						formObject.setStyle(SWIFT_DEC_ON_SWIFT_MESSAGE,
								DISABLE, TRUE);
					} else {
						formObject.setStyle(SWIFT_DEC_ON_SWIFT_MESSAGE,
								DISABLE, FALSE);
					}
				} else
					formObject.setStyle(SWIFT_DEC_ON_SWIFT_MESSAGE, DISABLE,
							FALSE);
			} else {
				formObject.setStyle(SWIFT_DEC_ON_SWIFT_MESSAGE, DISABLE, FALSE);
			}

			log.info("TFO_SWIFT_DEC_ON_SWIFT_MESSAGE="
					+ formObject.getValue(SWIFT_DEC_ON_SWIFT_MESSAGE)
							.toString());
			log.info("TFO_SWIFT_DEC_ON_SWIFT_MESSAGE="
					+ "JFF".equalsIgnoreCase(formObject.getValue(
							SWIFT_DEC_ON_SWIFT_MESSAGE).toString()));

			if ("JFF".equalsIgnoreCase(formObject.getValue(
					SWIFT_DEC_ON_SWIFT_MESSAGE).toString())) {
				log.info("efefe in JFF");
				formObject.setStyle(SWIFT_REASON_FOR_FILING, DISABLE, FALSE);
			} else {
				log.info("elseif  in JFF");
				formObject.setStyle(SWIFT_REASON_FOR_FILING, DISABLE, TRUE);
			}
			log.info("reqCategory1:" + reqCategory);
			log.info("reqType1:" + reqType);
			if (("ELCB".equalsIgnoreCase(reqCategory))
					&& ("ELCB_AOR".equalsIgnoreCase(reqType))) {
				log.info("inside if set fun "
						+ this.formObject.getValue(SWIFT_MESSAGE_TYPE));
				formObject.setValue(FIELD_TFO_DEC_ON_SWIFT_MESSAGE, "TBATC");
			}
			setReasonForFillDeafult();

		} catch (Exception e) {
			log.error("excpetion in setdefaultSwiftDecision: " + e, e);
		}

	}

	private void setReasonForFillDeafult() {
		try {
			log.info("inside setReasonForFillDeafult");
			String sQuery = "SELECT DEFAULT_VALUE,SET_BRANCH_CITY FROM TFO_DJ_SWIFT_REASON_FILL_DEF WHERE MSG_TYPE = '"
					+ this.formObject.getValue(SWIFT_MESSAGE_TYPE).toString()
					+ "' ";
			List<List<String>> record = this.formObject.getDataFromDB(sQuery);
			log.info("sQuery" + sQuery);
			if ((record != null) && (!record.isEmpty())) {
				String defVal = (String) ((List) record.get(0)).get(0);
				log.info("def_val : " + defVal);
				if ((!("".equalsIgnoreCase(defVal))) && (defVal != null)) {
					log.info("inside not null" + defVal);
					this.formObject.setValue(SWIFT_REASON_FOR_FILING, defVal);
					log.info("flag : "
							+ ((String) ((List) record.get(0)).get(1)));
					if ("Y".equalsIgnoreCase((String) ((List) record.get(0))
							.get(1))) {
						String sQueryIss = "SELECT BRANCH_CITY FROM TFO_DJ_SWIFT_TXN_DETAILS WHERE WI_NAME  = '"
								+ this.sWorkitemID + "'";
						List<List<String>> recordIss = this.formObject
								.getDataFromDB(sQueryIss);
						log.info("sQuery" + sQueryIss);
						if ((recordIss != null) && (!recordIss.isEmpty())) {
							log.info("table val :"
									+ ((String) ((List) recordIss.get(0))
											.get(0)));
							if ((!(""
									.equalsIgnoreCase((String) ((List) recordIss
											.get(0)).get(0))))
									&& (((List) recordIss.get(0)).get(0) != null)) {
								this.formObject.setValue(TFO_BRANCH_CITY,
										(String) ((List) recordIss.get(0))
												.get(0));
								this.formObject.setValue(TFO_ASSIGNED_CENTER,
										(String) ((List) recordIss.get(0))
												.get(0));
							} else {
								this.formObject.setValue(TFO_BRANCH_CITY, "");
								this.formObject.setValue(TFO_ASSIGNED_CENTER,
										"");
							}
						} else {
							this.formObject.setValue(TFO_BRANCH_CITY, "");
							this.formObject.setValue(TFO_ASSIGNED_CENTER, "");
						}
					} else {
						this.formObject.setValue(TFO_BRANCH_CITY, "");
						this.formObject.setValue(TFO_ASSIGNED_CENTER, "");
					}
				} else {
					this.formObject.setValue(TFO_BRANCH_CITY,
							this.issuingCenter);
					this.formObject.setValue(TFO_ASSIGNED_CENTER,
							this.assignCenter);
				}
			}
		} catch (Exception e) {
			log.error("Exception in setReasonForFillDeafult: ", e);
		}
	}

	private void submitSWIFT(IFormReference formObject, String sWorkitemID) {
		try {
			log.info("submitSWIFT: " + sWorkitemID);
			String nextWorkstep = "";
			String processType = formObject.getValue(PROCESS_TYPE).toString();
			if (SWIFT.equalsIgnoreCase(processType)) {
				String query = "SELECT WORKSTEP FROM TFO_DJ_SWIFT_MOVEMENT_MAST WHERE REQUEST_CATEGORY_CODE='"
						+ formObject.getValue(REQUEST_CATEGORY).toString()
						+ "' AND REQUEST_TYPE_CODE='"
						+ formObject.getValue(REQUEST_TYPE).toString()
						+ "' AND (SWIFT_DEC_ON_SWIFT_MESSAGE='NA' OR SWIFT_DEC_ON_SWIFT_MESSAGE='"
						+ formObject.getValue("SWIFT_DEC_ON_SWIFT_MESSAGE")
								.toString() + "')";
				log.info("update query :" + query);
				List<List<String>> sOutputlist = formObject
						.getDataFromDB(query);
				if (sOutputlist != null && !sOutputlist.isEmpty()
						&& !sOutputlist.get(0).isEmpty()) {
					nextWorkstep = sOutputlist.get(0).get(0);

					query = "UPDATE EXT_TFO SET NEXT_WORKSTEP_INITIATOR='"
							+ nextWorkstep + "' WHERE WI_NAME='" + sWorkitemID
							+ "'";
					log.info("update query=: " + query);
					int updateStatus = formObject.saveDataInDB(query);
					log.info("update status: " + updateStatus);
				}
			}
			if ("ELC".equalsIgnoreCase(formObject.getValue(REQUEST_CATEGORY)
					.toString())
					&& "ELC_LCA".equalsIgnoreCase(formObject.getValue(
							REQUEST_TYPE).toString())
					&& "760".equalsIgnoreCase(formObject.getValue(
							SWIFT_MESSAGE_TYPE).toString())) {
				log.info("APPLICABLE RULE MATCHED");
				String queryApp = "UPDATE TFO_DJ_SWIFT_TXN_DETAILS SET APPLICABLE_RULES='OTHR' WHERE WI_NAME='"
						+ sWorkitemID + "'";
				log.info("update query=: " + queryApp);
				int updateStatus = formObject.saveDataInDB(queryApp);
				log.info("updateStatus :" + updateStatus);
			}
		} catch (Exception e) {
			log.error("exception in submitSWIFT: " + e, e);
		}
	}

	private void onChangeReqCatAtIntro(IFormReference formObject, String reqCat) {
		try {
			if (null != reqCat) {
				listSourceAck = setMasterValues(formObject
						.getDataFromDB("SELECT SOURCE_KEY||'###'||IS_ACK FROM TFO_DJ_SOURCE_CHANNEL_MAST WHERE REQ_CAT_CODE='"
								+ reqCat + "'"));
				log.info("onChangeReqCatAtIntro Query for Source After call"
						+ listSourceAck);
				listReqTypeAck = setMasterValues(formObject
						.getDataFromDB("SELECT REQUEST_TYPE_ID||'###'||NEW_ISSUANCE_YES_NO FROM TFO_DJ_REQUEST_TYPE_MAST WHERE REQ_CAT_CODE='"
								+ reqCat + "'"));
				log.info("onChangeReqCatAtIntro Query for Request Type After call"
						+ listReqTypeAck);
				listReqSubAck = setMasterValues(formObject
						.getDataFromDB("SELECT REQ_SUBBY_KEY||'###'||IS_ACK FROM TFO_DJ_REQ_SUB_BY_MAST WHERE REQ_CAT_CODE='"
								+ reqCat + "'"));
				log.info(" onChangeReqCatAtIntro Query for Request Type After call"
						+ listReqTypeAck);
			}
		} catch (Exception e) {
			log.error("Exception:", e);
		}
	}

	public void enableDisableSwiftFields(IFormReference formObject) {
		try {
			if (SWIFT.equalsIgnoreCase(formObject.getValue(PROCESS_TYPE)
					.toString())) {
				if ("Y".equalsIgnoreCase(formObject
						.getValue(SWIFT_UTILITY_FLAG).toString())) // US3394
				{
					formObject.applyGroup(CONTROL_SET_SWIFT_FIELDS);
					enableControls(SWIFT_DEC_ON_SWIFT_MESSAGE + ","
							+ SWIFT_REASON_FOR_FILING);
				} else {
					formObject.applyGroup(CONTROL_SET_SWIFT_FIELDS_ENABLE);
					disableControls(SWIFT_REASON_FOR_FILING);
				}
				if ("JFF".equalsIgnoreCase(formObject.getValue(
						SWIFT_DEC_ON_SWIFT_MESSAGE).toString())) {
					enableControls(SWIFT_REASON_FOR_FILING);
				} else {
					disableControls(SWIFT_REASON_FOR_FILING);

				}
				if (formObject.getValue(REQUEST_TYPE).toString()
						.contains("_MSM")) {
					enableControls(SWIFT_FETCH_MODULE);
				}
			} else if ("BAU".equalsIgnoreCase(formObject.getValue(PROCESS_TYPE)
					.toString())) {
				formObject.applyGroup(CONTROL_SET_SWIFT_FIELDS);
				disableControls(SWIFT_FETCH_MODULE + ","
						+ SWIFT_REASON_FOR_FILING);
			}

		} catch (Exception e) {
			log.error("excpetion in enableDisableSwiftFields: " + e, e);
		}

	}

	@SuppressWarnings("unchecked")
	private boolean isAckGenerated(IFormReference formObject) {
		boolean isAck = false;
		try {
			log.info("Ack Gen Flag value "
					+ formObject.getValue(FLG_ACK_GEN).toString());
			if (TRUE.equalsIgnoreCase(formObject.getValue(FLG_ACK_GEN)
					.toString())
					&& chkAckBtVisible(formObject.getValue(SOURCE_CHANNEL)
							.toString())) {
				String strq = "SELECT IS_ACK_GEN FROM EXT_TFO WHERE WI_NAME = '"
						+ sWorkitemID
						+ "' and "
						+ "SOURCE_CHANNEL='"
						+ formObject.getValue(SOURCE_CHANNEL).toString() + "'";
				log.info(strq);
				List<List<String>> sOutputlist = formObject.getDataFromDB(strq);
				if (!sOutputlist.isEmpty()
						&& !sOutputlist.get(0).isEmpty()
						&& "Generated"
								.equalsIgnoreCase(((String) ((List) sOutputlist
										.get(0)).get(0)))) {
					isAck = true;
				}
			} else {
				isAck = true;
			}
		} catch (Exception e) {
			log.error("Exception: ", e);
		}
		return isAck;
	}

	private String getDate() {
		String today = "";
		Date date = Calendar.getInstance().getTime();
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		today = formatter.format(date);
		return today;
	}

	public String tempGenConfig(IFormReference formObject) {

		String status = "";
		String strparamNameN = "N";
		String strparamNameY = "Y";
		try {
			List<String> paramlist = new ArrayList<>();
			paramlist.add(PARAM_TEXT + sWorkitemID);
			paramlist.add(PARAM_TEXT + strparamNameN + "");
			List<List<String>> procReturnN = formObject
					.getDataFromStoredProcedure("TFO_DJ_ACK_TEMP_GEN",paramlist);
			log.info("procReturnN: " + procReturnN);
			String sOutputN = executeTemplateGeneration(formObject,
					strparamNameN);
			String[] tempArrN = sOutputN.split("##");
			XMLParser tempOutputN = new XMLParser(tempArrN[0]);
			String sResponse = tempOutputN.getValueOf("Status");
			log.info("Status : " + tempOutputN.getValueOf("Status"));
			// sResponse = "Success";
			if ("0".equalsIgnoreCase(sResponse)) {
				String ngoAddOutput = refreshWorkitemWithDoc(formObject,
						tempArrN[1]);
				// sendMessageHashMap("ngoAddOutput", ngoAddOutput);
			}
			List<String> paramlist1 = new ArrayList<>();
			paramlist1.add(PARAM_TEXT + sWorkitemID);
			paramlist1.add(PARAM_TEXT + strparamNameY + "");
			List<List<String>> procReturnY = formObject
					.getDataFromStoredProcedure("TFO_DJ_ACK_TEMP_GEN",
							paramlist1);
			log.info("procReturnY: " + procReturnY);
			executeTemplateGeneration(formObject, strparamNameY);
		} catch (Exception e) {
			log.error("exception in tempGenConfig: " + e, e);
		}
		return status;
	}

	private String refreshWorkitemWithDoc(IFormReference formObject,
			String docType) {
		String ngoAddDocOutput = "";
		String sQuery = "";
		String docIndex = "";
		try {
			log.info("refreshWorkitemWithDoc starts refreshed");
			sQuery = "select parentfolderindex, documentorderno, documentindex, versionnumber, owner, imageindex, documentsize,"
					+ "commnt from (select b.parentfolderindex as parentfolderindex, documentorderno as documentorderno, "
					+ "b.documentindex as documentindex, a.versionnumber as versionnumber, a.owner as owner,"
					+ "a.imageindex||'#'||a.volumeid as imageindex, a.documentsize as documentsize, a.commnt as commnt "
					+ "from pdbdocument a, pdbdocumentcontent b, pdbfolder c where a.name='"
					+ docType
					+ "'  and b.parentfolderindex=c.folderindex  "
					+ "and c.name='"
					+ sWorkitemID
					+ "' and a.documentindex=b.documentindex  order by documentorderno desc) where rownum=1";
			log.info("document data query : " + sQuery);
			@SuppressWarnings("rawtypes")
			List debugList = formObject
					.getDataFromDB("select parentfolderindex,documentorderno,documentindex,versionnumber,owner, imageindex,documentsize,"
							+ "commnt from (select b.parentfolderindex as parentfolderindex, documentorderno as documentorderno,"
							+ "b.documentindex as documentindex,a.versionnumber as versionnumber, a.owner as owner,"
							+ "a.imageindex||'#'||a.volumeid as imageindex,a.documentsize as documentsize,a.commnt as commnt "
							+ "from pdbdocument a,pdbdocumentcontent b,pdbfolder c where a.name='"
							+ docType
							+ "'  and "
							+ "b.parentfolderindex=c.folderindex  and c.name='"
							+ sWorkitemID
							+ "' and a.documentindex=b.documentindex  "
							+ "order by documentorderno desc) where rownum=1");
			log.info("debug list: " + debugList);
			log.info("formObject.getValue(PROCESS_TYPE): "
					+ formObject.getValue(PROCESS_TYPE));
			@SuppressWarnings("unchecked")
			List<List<String>> sOutputlistDoc = formObject
					.getDataFromDB("select parentfolderindex,documentorderno,documentindex,versionnumber,owner, imageindex,documentsize,"
							+ "commnt from (select b.parentfolderindex as parentfolderindex, documentorderno as documentorderno,"
							+ "b.documentindex as documentindex,a.versionnumber as versionnumber, a.owner as owner,"
							+ "a.imageindex||'#'||a.volumeid as imageindex,a.documentsize as documentsize,a.commnt as commnt "
							+ "from pdbdocument a,pdbdocumentcontent b,pdbfolder c where a.name='"
							+ docType
							+ "'  and "
							+ "b.parentfolderindex=c.folderindex  and c.name='"
							+ sWorkitemID
							+ "' and a.documentindex=b.documentindex  "
							+ "order by documentorderno desc) where rownum=1");
			log.info("size : " + sOutputlistDoc.size());
			log.info("list : " + sOutputlistDoc);
			if (!sOutputlistDoc.isEmpty()) {
				docIndex = sOutputlistDoc.get(0).get(2);
				ngoAddDocOutput = "<?xml version=\"1.0\"?><NGOAddDocument_Output><Option>NGOAddDocument</Option>"
						+ "<Status>0</Status><Document><LoginUserRights>1111111111</LoginUserRights>"
						+ "<ParentFolderIndex>"
						+ sOutputlistDoc.get(0).get(0)
						+ "</ParentFolderIndex><DocOrderNo>"
						+ sOutputlistDoc.get(0).get(1)
						+ "</DocOrderNo>"
						+ "<DocumentIndex>"
						+ sOutputlistDoc.get(0).get(2)
						+ "</DocumentIndex><DocumentVersionNo>"
						+ sOutputlistDoc.get(0).get(3)
						+ "</DocumentVersionNo>"
						+ "<DocumentName>"
						+ docType
						+ "</DocumentName><OwnerIndex>"
						+ sOutputlistDoc.get(0).get(4)
						+ "</OwnerIndex><CreationDateTime>2015-06-26 11:11:38</CreationDateTime>"
						+ "<RevisedDateTime>2015-06-26 11:11:38</RevisedDateTime><AccessDateTime>2015-06-26 11:11:38</AccessDateTime>"
						+ "<VersionFlag>N</VersionFlag><AccessType>I</AccessType><DocumentType>N</DocumentType><CreatedByApp>0</CreatedByApp>"
						+ "<ISIndex>"
						+ sOutputlistDoc.get(0).get(5)
						+ "</ISIndex><NoOfPages>0</NoOfPages><DocumentSize>"
						+ sOutputlistDoc.get(0).get(6)
						+ "</DocumentSize>"
						+ "<FTSDocumentIndex>0</FTSDocumentIndex><ODMADocumentIndex>not defined</ODMADocumentIndex><EnableLog>N</EnableLog>"
						+ "<DocumentLock>N</DocumentLock><Comment>"
						+ sOutputlistDoc.get(0).get(7)
						+ "</Comment><Author>ashish3</Author><FTSFlag>PP</FTSFlag>"
						+ "<DocStatus>A</DocStatus><ExpiryDateTime>2115-06-26 11:11:38</ExpiryDateTime><FinalizedFlag>N</FinalizedFlag>"
						+ "<FinalizedDateTime>2115-06-26 11:11:38</FinalizedDateTime><CheckoutStatus>N</CheckoutStatus>"
						+ "<ACLMoreFlag>N</ACLMoreFlag><PullPrintFlag>N</PullPrintFlag><MainGroupIndex>0</MainGroupIndex>"
						+ "<ThumbNailFlag>N</ThumbNailFlag><ISSecureFlag>N</ISSecureFlag><Owner>ashish3</Owner>"
						+ "<CreatedByAppName>pdf</CreatedByAppName></Document></NGOAddDocument_Output>";
				log.info("ngo add document output : " + ngoAddDocOutput);
				sQuery = "Update ext_tfo set IS_ACK_GEN = 'Generated' where wi_name = '"
						+ sWorkitemID + "'";
				log.info("document update query" + sQuery);
				formObject.saveDataInDB(sQuery);
			}
			log.info("refreshWorkitemWithDoc ends");
		} catch (Exception e) {
			log.error("exception in refreshWorkitemWithDoc: ", e);
		}
		sendMessageHashMap("ngoAddOutput###" + docIndex,
				ngoAddDocOutput.toString());
		return ngoAddDocOutput;
	}

	private void initLoadCombo() {
		try {
			log.info("inside initLoadCombo >> request category: "
					+ formObject.getValue(REQUEST_CATEGORY).toString());
			loadSwiftDecisionCombo(formObject.getValue(REQUEST_CATEGORY)
					.toString(), formObject.getValue(REQUEST_TYPE).toString());
		} catch (Exception e) {
			log.error("Exception: ", e);
		}
	}
	
	public boolean checkSwiftDocument()
	{
		String processType = formObject.getValue(PROCESS_TYPE).toString();
		String requestType = formObject.getValue(REQUEST_TYPE).toString();
		String requestCategory = formObject.getValue(REQUEST_CATEGORY).toString();
		String ts_req = formObject.getValue(TS_REQUIRED).toString();
		log.info("checkSwiftDocument"+"ProcessType " +processType +"requestType " +requestType +"requestCategory " +requestCategory +"ts_req "+ ts_req);
		TraydStreamValues = TraydStreamValueMap.get(processType + "#" + requestCategory + "#" + requestType+ "#" + ts_req);
		if(("SWIFT".equalsIgnoreCase(processType)&& "Y".equalsIgnoreCase(formObject.getValue(SWIFT_UTILITY_FLAG).toString())) 
			 || (TraydStreamValues != null && !TraydStreamValues.isEmpty()))
		{
			String sQuery = "";
			List<List<String>> sOutput;
			
			/*sQuery = "SELECT COUNT(1) AS COUNT FROM PDBDOCUMENT DOC, PDBDOCUMENTCONTENT DOCCONTENT , PDBFOLDER FOLDER "
										+ "WHERE DOC.NAME='Customer_Request' and FOLDER.NAME  = '"+sWorkitemID+"' ";
			*/
			sQuery ="select count(1) from PDBDOCUMENT  where   documentindex in ("
					+"select documentindex from PDBDOCUMENTCONTENT where parentfolderindex="
					+ "(select itemindex from ext_tfo where wi_name='"+sWorkitemID+"')"
					+") and  NAME='Customer_Request'";
			log.info("sQuery is :******"+sQuery);
			sOutput = formObject.getDataFromDB(sQuery);
			log.info("sOutput is :******"+sOutput);
			if(sOutput != null && sOutput.size()>0){
				int count = Integer.parseInt(sOutput.get(0).get(0));
				log.info("count is :"+count);
	
				if (count > 0){
					log.info("***in checkSwiftDocument***");
					return false;
				} else {
						sendMessageHashMap("", "Please Add Customer_Request Document.");
						return true;		
				}
			}
		}
		return false;
	}

	public String executeTemplateGeneration(IFormReference formObject,
			String pwd) {
		String sOutput = "";
		String docName = "";
		try {
			log.info("getTemplateInputXML Starts");
			List<List<String>> sOutputlistN = formObject
					.getDataFromDB(templateQueryFilter(
							formObject.getValue(REQUEST_CATEGORY).toString()
									.trim(),
							formObject.getValue(REQUEST_TYPE).toString().trim(),
							formObject.getValue(REQUESTED_BY).toString().trim(),
							formObject.getValue(RELATIONSHIP_TYPE).toString()
									.trim(), pwd));
			log.info("Output List " + sOutputlistN);
			if (sOutputlistN != null && !sOutputlistN.isEmpty()
					&& !sOutputlistN.get(0).isEmpty()) {
				docName = sOutputlistN.get(0).get(2);
				log.info("docName : " + docName);
			}
			String templateInputXMl = getTemplateInputXML();
			sOutput = tempGenSocket.connectToSocket(templateInputXMl);
			log.info("getTemplateInputXML ends with output : " + sOutput);
		} catch (Exception e) {
			log.error("exception in executeTemplateGeneration: ", e);
		}
		return sOutput + "##" + docName;
	}

	private String getTemplateInputXML() {
		String sInputXML = "";
		try {
			log.info("getTemplateInputXML Starts");
			sInputXML = "<TemplateGeneration_Input><Option>TemplateGeneration</Option>"
					+ "<Data>"
					+ "<ProcessName>TFO</ProcessName>"
					+ "<WorkitemName>"
					+ sWorkitemID
					+ "</WorkitemName>"
					+ "<Category>Acknowledgement_Letter</Category>"
					+ "</Data>"
					+ "</TemplateGeneration_Input>";
			log.info("getTemplateInputXML ends with inputXML : " + sInputXML);
		} catch (Exception e) {
			log.error("exception in getTemplateInputXML: ", e);
		}
		return sInputXML;
	}

	private void updateFetchTransactionOrches(String winame){
		log.info("INSIDE  updateFetchTransactionOrches");
		String sQuery="";
		try {
			sQuery = "update BPM_ORCHESTRATION_STATUS set call_status = 'X' "
					+ " where call_name = 'FetchTransactionDetails' and  call_status = 'N' "
					+ " and wi_name = '"+winame+"'";
			log.info("INSIDE  sQuery="+sQuery);

			List<List<String>> obj = formObject.getDataFromDB(sQuery);
		} catch (Exception e) {
			log.error("exception in getTemplateInputXML: ", e);
		}
		log.info("END OF updateFetchTransactionOrches");
	}
	
	private String templateQueryFilter(String reqCat, String sRequestType,
			String sRequestedBy, String sRelationShip, String pwdType) {
		String strQueryN = "";
		try {
			strQueryN = "SELECT DOC_NAME,TEMPLATE_NAME,DOC_TYPE FROM TFO_DJ_TEMP_CONFIG WHERE PWDTYPE = '"
					+ pwdType
					+ "' "
					+ "AND REQ_CAT_CODE='"
					+ reqCat
					+ "' AND REQ_BY = '"
					+ sRequestedBy
					+ "' "
					+ " AND REL_TYPE = '" + sRelationShip + "' ";
			if ("GRNT".equalsIgnoreCase(reqCat)) {

				if ("A".equalsIgnoreCase(sRequestedBy)
						&& "C".equalsIgnoreCase(sRelationShip)
						&& ("NI".equalsIgnoreCase(sRequestType) || "CC"
								.equalsIgnoreCase(sRequestType))) {
					strQueryN = strQueryN
							+ WHERE_REQ_TYPE
							+ formObject.getValue(REQUEST_TYPE).toString()
									.trim() + "'";
				} else if ("A".equalsIgnoreCase(sRequestedBy)
						&& "I".equalsIgnoreCase(sRelationShip)
						&& ("NI".equalsIgnoreCase(sRequestType) || "CC"
								.equalsIgnoreCase(sRequestType))) {
					strQueryN = strQueryN
							+ WHERE_REQ_TYPE
							+ formObject.getValue(REQUEST_TYPE).toString()
									.trim() + "'";
				} else if (("A".equalsIgnoreCase(sRequestedBy) && "C"
						.equalsIgnoreCase(sRelationShip))
						|| ("A".equalsIgnoreCase(sRequestedBy) && "I"
								.equalsIgnoreCase(sRelationShip))
						|| ("B".equalsIgnoreCase(sRequestedBy) && "C"
								.equalsIgnoreCase(sRelationShip))
						|| ("B".equalsIgnoreCase(sRequestedBy) && "I"
								.equalsIgnoreCase(sRelationShip))) {
					strQueryN = strQueryN + " AND REQ_TYPE = 'Any'";
				}
			} else if ("IFP".equalsIgnoreCase(reqCat) || "SCF".equalsIgnoreCase(reqCat)) {
				strQueryN = strQueryN + WHERE_REQ_TYPE
						+ formObject.getValue(REQUEST_TYPE).toString().trim()
						+ "'";
			} else if ("IFS".equalsIgnoreCase(reqCat)) {
				strQueryN = strQueryN + WHERE_REQ_TYPE
						+ formObject.getValue(REQUEST_TYPE).toString().trim()
						+ "'";
			} else if ("EC".equalsIgnoreCase(reqCat)) {
				strQueryN = strQueryN + WHERE_REQ_TYPE
						+ formObject.getValue(REQUEST_TYPE).toString().trim()
						+ "'";
			} else if ("IC".equalsIgnoreCase(reqCat)) {
				strQueryN = strQueryN + WHERE_REQ_TYPE
						+ formObject.getValue(REQUEST_TYPE).toString().trim()
						+ "'";
			} else if ("DBA".equalsIgnoreCase(reqCat)) {
				strQueryN = strQueryN + WHERE_REQ_TYPE
						+ formObject.getValue(REQUEST_TYPE).toString().trim()
						+ "'";
			} else if ("ELC".equalsIgnoreCase(reqCat)) {
				strQueryN = strQueryN + WHERE_REQ_TYPE
						+ formObject.getValue(REQUEST_TYPE).toString().trim()
						+ "'";
			} else if ("ILC".equalsIgnoreCase(reqCat)) {
				strQueryN = strQueryN + WHERE_REQ_TYPE
						+ formObject.getValue(REQUEST_TYPE).toString().trim()
						+ "'";
			} else if ("ILCB".equalsIgnoreCase(reqCat)) {
				strQueryN = strQueryN + WHERE_REQ_TYPE
						+ formObject.getValue(REQUEST_TYPE).toString().trim()
						+ "'";
			} else if ("ELCB".equalsIgnoreCase(reqCat)) {
				strQueryN = strQueryN + WHERE_REQ_TYPE
						+ formObject.getValue(REQUEST_TYPE).toString().trim()
						+ "'";
			} else if ("TL".equalsIgnoreCase(reqCat)) {
				strQueryN = strQueryN + WHERE_REQ_TYPE
						+ formObject.getValue(REQUEST_TYPE).toString().trim()
						+ "'";
			}
			log.info("Final Temaplete query from templateQuery is  "
					+ strQueryN);
		} catch (Exception e) {
			log.error("exception in templateQuery: " + e, e);
		}
		return strQueryN;
	}
	
	public void  fillProtradeSwiftDelivInst(){ //Changed By kishan
		log.info("Inside fillProtradeSwiftDelivInst");
		try {
			String wiName = formObject.getValue("WI_NAME").toString();
			String Query = "Select DELIVERY_INSTR_C,DELIVERY_TO_C from EXT_TFO where wi_name = '"+wiName+"'";
			log.info(" fillProtradeSwiftDelivInst Query :" +Query);
			List<List<String>> obj = formObject.getDataFromDB(Query);
			String processType=formObject.getValue(PROCESS_TYPE).toString();
			String swiftUtilityFlag = formObject.getValue(SWIFT_UTILITY_FLAG).toString();
			String requestType = formObject.getValue(REQUEST_TYPE).toString();
			String requestCat = formObject.getValue(REQUEST_CATEGORY).toString();
			if(processType.equalsIgnoreCase("SWIFT") && swiftUtilityFlag.equalsIgnoreCase("Y")
					&& obj.get(0)!=null){
				log.info("fillProtradeSwiftDelivInst if check");
				String delivery_inst = obj.get(0).get(0);
				String delivery_to =  obj.get(0).get(1);
				
				/*String sQuery = "Insert into TFO_DJ_AMENDMENT_FRAME_DATA (PT_DELIVERY_INSTR) values('"+value+"')"
						+ " where wi_name = '"+wiName+"'";
				log.info("sQuery fillProtradeSwiftDelivInst : "+sQuery);
				formObject.getDataFromDB(sQuery);*/
				
				if(!(delivery_inst == null || delivery_inst.equalsIgnoreCase(""))
						|| !(delivery_inst ==null  || delivery_to.equalsIgnoreCase(""))){
					if((requestCat.equalsIgnoreCase("GRNT") && 
							(requestType.equalsIgnoreCase("GA") || requestType.equalsIgnoreCase("NI")))
							|| (requestCat.equalsIgnoreCase("SBLC") && requestType.equalsIgnoreCase("SBLC_NI"))
							|| (requestCat.equalsIgnoreCase("ELC") && requestType.equalsIgnoreCase("ELC_SLCA"))){
						String value = delivery_inst + "+" +delivery_to;
						String sQuery = "Update EXT_TFO set DELIVERY_INSTR = '"+value+"'"
								+ " where wi_name = '"+wiName+"'";
						log.info("sQuery fillProtradeSwiftDelivInst : "+sQuery);
						formObject.getDataFromDB(sQuery);
						//formObject.setValue("DELIVERY_INSTR",value);
					}else{
						String value = delivery_inst + "+" +delivery_to;
						//formObject.setValue("Q_Amendment_Data_USER_CNTR_GTEE_EXP_DATE",value);
						String sQuery = "Update TFO_DJ_AMENDMENT_FRAME_DATA set PT_TRANSACTION_AMOUNT =  '"+value+"'"
								+ " where wi_name = '"+wiName+"'";
						log.info("sQuery fillProtradeSwiftDelivInst : "+sQuery);
						formObject.getDataFromDB(sQuery);
						log.info("sQuery fillProtradeSwiftDelivInst : ");
					}	
				}
				log.info("check fillProtradeSwiftDelivInst 1");
			}else{
				log.info("fillProtradeSwiftDelivInst if conditions did not satisfy.");
			}
		} catch (Exception e) {
			log.error("exception in fillProtradeSwiftDelivInst: " + e, e);
		}
		log.info("END fillProtradeSwiftDelivInst");
	}
	
	public void  fillSwiftAmountINCDECB(){ //Changed By kishan
		log.info("Inside fillProtradeSwiftDelivInst");
		try {
			String wiName = formObject.getValue("WI_NAME").toString();
			String Query = "Select AMOUNT_INC_B,AMOUNT_DEC_B from EXT_TFO where wi_name = '"+wiName+"'";
			log.info(" fillSwiftAmountINCDECB Query :" +Query);
			List<List<String>> obj = formObject.getDataFromDB(Query);
			/*log.info(" fillSwiftAmountINCDECB obj  :" +obj.isEmpty());
			log.info(" fillSwiftAmountINCDECB obj.get(0)  :" +obj);*/
			String processType=formObject.getValue(PROCESS_TYPE).toString();
			String swiftUtilityFlag = formObject.getValue(SWIFT_UTILITY_FLAG).toString();
			if(processType.equalsIgnoreCase("SWIFT") && swiftUtilityFlag.equalsIgnoreCase("Y")
					&& obj.get(0)!=null){
				log.info("fillSwiftAmountINCDECB if check");
				String amountINC_B = obj.get(0).get(0);
				String amountDEC_B =  obj.get(0).get(1);

				if(!(amountINC_B == null || amountINC_B.equalsIgnoreCase("")) ){
					log.info(" fillSwiftAmountINCDECB if Condition 1");
					int value =0;
					String INC ="";// formObject.getValue("Q_AMENDMENT_DATA_FC_TRANSACTION_AMOUNT").toString();
					String sQuery = "select FC_TRANSACTION_AMOUNT from TFO_DJ_AMENDMENT_FRAME_DATA"
							+ " where wi_name = '"+wiName+"'";
					log.info("sQuery fillProtradeSwiftDelivInst : "+sQuery);
					List<List<String>> obj2 = formObject.getDataFromDB(sQuery);
					if(obj2.get(0) !=null){
						INC = obj2.get(0).get(0);
					}
					if(!INC.equalsIgnoreCase("")){
						value = Integer.parseInt(INC);//+ "+" +;
					}
					value += Integer.parseInt(amountINC_B);
					//formObject.setValue("Q_Amendment_Data_PT_TRANSACTION_AMOUNT",Integer.toString(value));
					sQuery = "Update TFO_DJ_AMENDMENT_FRAME_DATA set PT_TRANSACTION_AMOUNT =  '"+value+"'"
							+ " where wi_name = '"+wiName+"'";
					log.info("sQuery fillProtradeSwiftDelivInst : "+sQuery);
					formObject.getDataFromDB(sQuery);
					log.info(" fillSwiftAmountINCDECB if Condition 1 End");
				}else if(!(amountDEC_B ==null  || amountDEC_B.equalsIgnoreCase(""))){
					log.info(" fillSwiftAmountINCDECB if Condition 2");
					int value = 0;
					String DEC ="";// formObject.getValue("Q_AMENDMENT_DATA_FC_TRANSACTION_AMOUNT").toString();
					String sQuery = "select FC_TRANSACTION_AMOUNT from TFO_DJ_AMENDMENT_FRAME_DATA"
							+ " where wi_name = '"+wiName+"'";
					log.info("sQuery fillProtradeSwiftDelivInst : "+sQuery);
					List<List<String>> obj2 = formObject.getDataFromDB(sQuery);
					if(obj2.get(0) !=null){
						DEC = obj2.get(0).get(0);
					}
					if(!DEC.equalsIgnoreCase("")){
						value = Integer.parseInt(DEC);//+ "+" +;
					}
					value -= Integer.parseInt(amountDEC_B);
					sQuery = "Update TFO_DJ_AMENDMENT_FRAME_DATA set PT_TRANSACTION_AMOUNT =  '"+value+"'"
							+ " where wi_name = '"+wiName+"'";
					log.info("sQuery fillProtradeSwiftDelivInst : "+sQuery);
					formObject.getDataFromDB(sQuery);
					log.info(" fillSwiftAmountINCDECB if Condition 2 End");
				}
				log.info("check fillSwiftAmountINCDECB 1");
			}else{
				log.info(" fillSwiftAmountINCDECB if conditions did not satisfy.");
			}
		} catch (Exception e) {
			log.error("Exception in fillSwiftAmountINCDECB: " + e, e);
		}
		log.info("END fillSwiftAmountINCDECB");
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
	public String setMaskedValue(String arg0, String arg1) {
		return arg1;
	}

	@Override
	public JSONArray validateSubmittedForm(FormDef arg0, IFormReference arg1,
			String arg2) {
		return null;
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
	public void updateDataInWidget(IFormReference arg0, String arg1) {
		// unused
	}

	@Override
	public String validateDocumentConfiguration(String arg0, String arg1,
			File arg2, Locale arg3) {
		return null;
	}
	public String getUTCEligibility(double amount) {
		log.info("getUTCEligibility INSIDE");
		String sInput = getUTCEligibilityRequestXml(engineName, sessionId,amount);
		String sOutput = "";
		try {
			if (!sInput.isEmpty() && sInput != "") {
				sOutput = socket.connectToSocket(sInput);
				log.info("getUTCEligibilityRequestXml ExecuteWebservice_getUTCEligibilityRequestXml:"
						+ sOutput);
			}
		} catch (Exception e) {
			log.error("Exception: ", e);
		}
		log.info("getUTCEligibilityRequestXml Exit");
		return sOutput;
	}

	public String getUTCEligibilityRequestXml(
			String engineName,
			String sessionId,double amount) {
		StringBuffer inputXML = new StringBuffer();
		try {
			List<List<String>> sOutputlist = formObject.getDataFromDB("SELECT SEQ_WEBSERVICE.nextval as IDValue from DUAL");
			String refNo= sOutputlist.get(0).get(0);
			List<List<String>> sOutput = formObject.getDataFromDB("select profit_center_code from ext_tfo where wi_name = '"+this.sWorkitemID+"'");
			String str1= sOutput.get(0).get(0);
			String profitCentre = str1.substring(0,3);
			log.info(" inside profitCentre "+profitCentre);
			log.info("getUTCEligibilityRequestXml setting getUTCEligibilityRequestXml");
			inputXML.append("<?xml version=\"1.0\"?>").append("\n").append("<APWebService_Input>")
					.append("\n").append("<Option>WebService</Option>").append("\n").append(
							"<Calltype>WS-2.0</Calltype>").append("\n").append(
							"<InnerCallType>WBG_BRMS_RULES_RESPONSE</InnerCallType>").append("\n")
							.append("<serviceName>InqBusinessRules</serviceName>").append("\n")
							.append("<serviceAction>Inquiry</serviceAction>").append("\n")
							.append("<REF_NO>" + refNo + "</REF_NO>").append("\n")
							.append("<WIName>" + sWorkitemID + "</WIName>").append("\n").append(
							"<SessionId>" + sessionId + "</SessionId>").append("\n").append(
							"<EngineName>" + engineName + "</EngineName>").append("\n").append(
							"<SENDERID>UTC</SENDERID>").append("\n").append(" <username>appuser</username>")
							.append("\n").append("<credentials>appuser</credentials>");
			
			inputXML.append("\n")
					.append("<InqBusinessRulesReq>")
					.append("<ruleFlowGroup>TFO-UTC</ruleFlowGroup>")
					.append("\n")
					.append("<requestChannelName>WMS</requestChannelName>")
					.append("\n")
					.append("<Eligibility>")
					.append("<PAYLOAD type=\"UTC_ELIGIBILITY\"><UTC_ELIGIBILITY>"
						+ "<REQUEST_CATEGORY>"+ formObject.getValue(REQUEST_CATEGORY).toString() +"</REQUEST_CATEGORY>"
						+ "<PROFIT_CENTER_CODE>"+profitCentre+"</PROFIT_CENTER_CODE>"
						+ "<TRANSACTION_AMOUNT>"+ amount +"</TRANSACTION_AMOUNT></UTC_ELIGIBILITY></PAYLOAD>"
						+ "</Eligibility>").append("\n")
						.append("</InqBusinessRulesReq>")
					.append("</APWebService_Input>");
			log.info("getUTCEligibilityRequestXml  created ===> " + inputXML.toString());
		} catch (Exception e) {
	        
			log.error("Exception: ", e);
		}
		return inputXML.toString();
	}

	public void executeUTCCalls(IFormReference formObject){
		log.info("executeUTCCalls Inside");
		try{
			String reqCat = formObject.getValue(REQUEST_CATEGORY).toString();
			String requestType = formObject.getValue(REQUEST_TYPE).toString();
			String relType = formObject.getValue(RELATIONSHIP_TYPE).toString();
			String transactionAmount =  formObject.getValue(TRANSACTION_AMOUNT).toString().replaceAll(",", "");
			String transCurr = formObject.getValue(TRANSACTION_CURRENCY).toString().trim();

			log.info("reqCat value : "+reqCat);
			log.info("requestType value : "+requestType);
			log.info("relType value : "+relType);
			log.info("transactionAmount value : "+transactionAmount);
			log.info("transCurr value : "+transCurr);

			String brnCode = "";
			String errorDescription  = "";
			double converted_amount;//change by Ayush - convert  to bigdecimal type
			double converted_amt = 0;
			double book_rate = 0;
			boolean executeCall = true;
			String utcEligible = "";
			String outputXml ="";
			XMLParser xp = null;
			if ("IFS".equalsIgnoreCase(reqCat)|| "IFP".equalsIgnoreCase(reqCat)|| "IFA".equalsIgnoreCase(reqCat) 
					|| "SCF".equalsIgnoreCase(reqCat) )  {
				log.info("inside reqCat");
				if("LD".equalsIgnoreCase(requestType) || "IFA_CTP".equalsIgnoreCase(requestType) 
						|| "PD".equalsIgnoreCase(requestType) || "PDD".equalsIgnoreCase(requestType)) {     ///Added FOR SCF NEW REQ CAT AND TYPE TO RUN UTC BRMS ATP -254
					log.info("inside requestType");
					if("C".equalsIgnoreCase(relType)){
						brnCode = "299";
					}
					else if("I".equalsIgnoreCase(relType)){
						brnCode = "799";
					}
					if(!formObject.getValue(TRANSACTION_CURRENCY).toString().equalsIgnoreCase("AED")){
						String sQuery = "SELECT BOOK_RATE FROM STG_FXRATE WHERE FROM_CCY = '"+transCurr.toUpperCase()+"' AND BRANCH_CODE='"+brnCode+"'";
						List<List<String>> resultSet = formObject.getDataFromDB(sQuery);
						log.info("sQuery value : "+sQuery);
						log.info("resultSet	"+resultSet);

						if (resultSet != null && resultSet.size()>0) {
							log.info("book_rate value : "+book_rate);
							book_rate  = Double.parseDouble(resultSet.get(0).get(0));
							log.info("book_rate value : "+book_rate);
							double amount = Double.parseDouble(transactionAmount);
							converted_amount = book_rate * amount;
							//Added By Ayush
						//	converted_amount=Double.parseDouble(new BigDecimal(transactionAmount).toPlainString());
						//	converted_amount=Double.parseDouble(new BigDecimal(transactionAmount).toBigInteger().toString());
							
							String roundoff= String.format("%.2f", converted_amount);
							converted_amt = Double.parseDouble(roundoff);
							log.info("converted_amount value : "+converted_amt);
							//Integer.parseInt(arg0)
						} else {
							executeCall = false;
							converted_amt =0;
							utcEligible = "Yes";
							log.info("inside else utcEligible value : "+utcEligible);

							formObject.setValue("UTC_CONVERTED_AMOUNT","0");
							formObject.setValue("UTC_REQUIRED_BRMS", "Yes");
//							String query="update ext_tfo set utc_converted_amount = '0',utc_required_brms = 'Yes' where wi_name='"+this.sWorkitemID+"'";
//							log.info("Saving in ext table records query : "+query);
//							int output=formObject.saveDataInDB(query);
						}

					} else {
						//converted_amt =  Double.parseDouble(transactionAmount);
						converted_amt= Double.parseDouble(transactionAmount);
						//Added By Ayush
						log.info(transactionAmount);
						log.info(converted_amt);

						converted_amount=Double.parseDouble(new BigDecimal(transactionAmount).toBigInteger().toString());
						//converted_amt=Double.parseDouble(new BigDecimal(transactionAmount).toPlainString());
						log.info(converted_amt);
					}
					if(executeCall){
						outputXml = getUTCEligibility(converted_amt);
						log.info("getBRMSEligibility output:"+outputXml);
						xp = new XMLParser(outputXml);
						 errorDescription = xp.getValueOf("errorDescription");

						log.info("OutputXml is null"+xp.equals(""));
						if(xp.getValueOf("STATUS_CODE").equalsIgnoreCase("0") ){
							if((xp.getValueOf("UTC_ELIGIBILITY_OUTPUT").toUpperCase()).equalsIgnoreCase("TRUE")){
								utcEligible = "Yes";
							} else if((xp.getValueOf("UTC_ELIGIBILITY_OUTPUT").toUpperCase()).equalsIgnoreCase("FALSE")){
								utcEligible = "No";
							}
						}
//						else {
//							String columnNames = "SNO,WI_NAME,CALL_NAME,CALL_DT,CALL_STATUS,RETRY_COUNT,call_operation";
//							log.info("executeUTCBRMSCalls integration starts");
//							int i=0;
//							String sInsertScript = "INSERT INTO TFO_DJ_INTEGRATION_CALLS ("+columnNames+") VALUES "
//									+ "('"+(i+1)+"','" + sWorkitemID + "','executeUTCBRMSCalls',sysdate,'N','0','executeUTCBRMSCalls')";
//							log.info("[sInsertScript for Integration Calls executeUTCBRMSCalls]"+sInsertScript);
//							formObject.saveDataInDB(sInsertScript);
//							log.info("Data Inserted"+i);
//
//						
//						}

					}
					//Query change - convertedAmount
					String query="update ext_tfo set utc_converted_amount = '"+converted_amt+"',"
							+ "utc_book_rate = '"+book_rate+"',utc_required_brms = '"+utcEligible+"' where wi_name='"+this.sWorkitemID+"'";
					log.info("Saving in ext table records query : "+query);
					int output=formObject.saveDataInDB(query);
					log.info("Aupdate query result : "+output);
					saveUTCDecision(utcEligible,converted_amt,errorDescription);
				}
			}
		} catch (Exception e) {
			log.error("Exception: ", e);
		}
	}	

	//shahbaz
	public boolean checkdormantCustomer() {
		log.info("Inside checkdormantCustomer() : ");
		String dormant_customer = "";
		try {
			String custStatus = "";
			String outputXml = executeDormancyXml();
			XMLParser xp = new XMLParser(outputXml);
			int returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
			log.info("checkdormantCustomer : returnCode: " + returnCode);
			if (returnCode == 0) {
				custStatus = xp.getValueOf("custStatus");
				log.info("inside If : custStatus: " + custStatus);
				if ("D".equalsIgnoreCase(custStatus) || "I".equalsIgnoreCase(custStatus)) {
					dormant_customer = "Y";
				} else {
					dormant_customer = "N";
				}
			}
			log.info("checkdormantCustomer : dormant_customer: " + dormant_customer);
			if (dormant_customer.equalsIgnoreCase("Y")) {
				log.info("checkdormantCustomer --: Inside If ");
				String personalName = "";
				String decHist = "TFO_DJ_DEC_HIST";
				String decHistCol = "WI_NAME,USER_ID,CURR_WS_NAME,CREATE_DATE,USERNAME,USER_GROUPNAME,ACTION,REASON_FOR_ACTION,"
						+ "TRANSACTION_DATE_TIME,REMARKS";
				String sQuery = "SELECT PERSONALNAME FROM PDBUSER WHERE UPPER(USERNAME)='" + sUserName.toUpperCase()+ "'";
				List<List<String>> sOutputlist = formObject.getDataFromDB(sQuery);
				if (sOutputlist != null && !sOutputlist.isEmpty() && sOutputlist.get(0).size() > 0) {
					personalName = sOutputlist.get(0).get(0);
				}
				String decQuery = "INSERT INTO " + decHist + "( " + decHistCol + " ) VALUES ('" + sWorkitemID + "','"
						          + this.sUserName.toUpperCase() + "','" + sActivityName + "',sysdate,'" + personalName + "','"
						          + sActivityName + "','" + dormant_customer
						          + "','Dormance Customer Found',sysdate,'Initiated by " + sUserName + "')";

				log.info("Decision --: " + decQuery);
				formObject.saveDataInDB(decQuery);
			    return true;			
			}
		} catch (Exception e) {
			log.error("Exception: ", e);
		}
		log.info("dormantPopupShow --: Outside If else block ");
		return false;

	}
	
	//ATP-416 01-03-2024 --JAMSHED
	public void disableFieldForDormantCustomer() {
		String dormancy_flg="";
		String Query = "select action,reason_for_action from (select action,reason_for_action from tfo_dj_dec_hist where wi_name='"+sWorkitemID+"' and action='Y' and reason_for_action='Dormance Customer Found' order by create_date desc) where rownum=1";
		log.info(" disableFieldForDormantCustomer Query :" +Query);
		List<List<String>> res_list = formObject.getDataFromDB(Query);
		
		if (res_list != null && res_list.size()>0) {
			log.info("res_list : "+res_list);
			dormancy_flg = res_list.get(0).get(0);
		}
		
		if(dormancy_flg.equalsIgnoreCase("Y")) {
			log.info(" Inside disable check>>>>>>>>>>");
			formObject.setStyle(BUTTON_CUSTOMER_ACK, DISABLE, TRUE);
			formObject.setStyle(BUTTON_SUBMIT, DISABLE, TRUE);
		}
	}
	//ATP-416 01-03-2024 --JAMSHED ENDS

	//ATP-493 29-07-2024 REYAZ  START	
	public boolean validateFetchButton(){
		String processType=formObject.getValue(PROCESS_TYPE).toString();
		String hybridCustomer=formObject.getValue(HYBRID_CUSTOMER).toString();
		log.info("validateFetchButton: " + processType);
		if (processType.equalsIgnoreCase("TSLM Front End") && hybridCustomer.equalsIgnoreCase("Yes")) {
			String sQuery = "SELECT FETCH_BTN_FLAG FROM EXT_TFO WHERE WI_NAME = '"+ sWorkitemID + "'";
			List<List<String>> sOutputlist = formObject.getDataFromDB(sQuery);
			String flag = sOutputlist.get(0).get(0);  
			if (!flag.equalsIgnoreCase("T")) {
				sendMessageHashMap(BUTTON_FETCH,"Please click  Fetch Button");
				return true;
			}
		}
		return false;
	}
	//ATP-493 29-07-2024 REYAZ 	END
	
	//ATP-494 29-07-2024 REYAZ  START
	public boolean checkHybridCustomerDocument()
	{
		String processType = formObject.getValue(PROCESS_TYPE).toString();
		String hybridCustomer=formObject.getValue(HYBRID_CUSTOMER).toString();
		if("TSLM Front End".equalsIgnoreCase(processType)  && hybridCustomer.equalsIgnoreCase("Yes"))
		{
			String sQuery = "";
			List<List<String>> sOutput;
			sQuery ="select count(1) from PDBDOCUMENT  where   documentindex in ("
					+"select documentindex from PDBDOCUMENTCONTENT where parentfolderindex="
					+ "(select itemindex from ext_tfo where wi_name='"+sWorkitemID+"')"
					+")";
			log.info("sQuery is :******"+sQuery);
			sOutput = formObject.getDataFromDB(sQuery);
			log.info("sOutput is :******"+sOutput);
			if(sOutput != null && sOutput.size()>0){
				int count = Integer.parseInt(sOutput.get(0).get(0));
				log.info("count is :"+count);
	
				if (count > 0){
					log.info("***in checkHybridCustomerDocument***");
					return false;
				} else {
						sendMessageHashMap(BUTTON_RETRY, "Please Click on Re Fetch Documents button to  Add Document.");
						return true;		
				}
			}
		}
		return false;
	}
	//ATP-494 29-07-2024 REYAZ  END


//	public String getTSLMEligibility(double amount) {
//		log.info("getTSLMEligibility INSIDE");
//		String sInput = getTSLMEligibilityRequestXml(engineName, sessionId,
//				amount);
//		String sOutput = "";
//		try {
//			if (!sInput.isEmpty() && sInput != "") {
//				sOutput = socket.connectToSocket(sInput);
//				log.info("getTSLMEligibilityRequestXml ExecuteWebservice_getTSLMEligibilityRequestXml:"
//						+ sOutput);
//			}
//		} catch (Exception e) {
//			log.error("Exception: ", e);
//		}
//		log.info("getTSLMEligibilityRequestXml Exit");
//		return sOutput;
//	}
//
//	public String getTSLMEligibilityRequestXml(String engineName,
//			String sessionId, double amount) {
//		StringBuffer inputXML = new StringBuffer();
//		try {
//			List<List<String>> sOutputlist = formObject
//					.getDataFromDB("SELECT SEQ_WEBSERVICE.nextval as IDValue from DUAL");
//			String refNo = sOutputlist.get(0).get(0);
//			List<List<String>> sOutput = formObject
//					.getDataFromDB("select profit_center_code from ext_tfo where wi_name = '"
//							+ this.sWorkitemID + "'");
//			String str1 = sOutput.get(0).get(0);
//			String profitCentre = str1.substring(0, 3);
//			log.info(" inside profitCentre " + profitCentre);
//			log.info("getTSLMEligibilityRequestXml setting getTSLMEligibilityRequestXml");
//			inputXML.append("<?xml version=\"1.0\"?>")
//					.append("\n")
//					.append("<APWebService_Input>")
//					.append("\n")
//					.append("<Option>WebService</Option>")
//					.append("\n")
//					.append("<Calltype>WS-2.0</Calltype>")
//					.append("\n")
//					.append("<InnerCallType>WBG_BRMS_RULES_RESPONSE</InnerCallType>")
//					.append("\n")
//					.append("<serviceName>InqBusinessRules</serviceName>")
//					.append("\n")
//					.append("<serviceAction>Inquiry</serviceAction>")
//					.append("\n").append("<REF_NO>" + refNo + "</REF_NO>")
//					.append("\n")
//					.append("<WIName>" + sWorkitemID + "</WIName>")
//					.append("\n")
//					.append("<SessionId>" + sessionId + "</SessionId>")
//					.append("\n")
//					.append("<EngineName>" + engineName + "</EngineName>")
//					.append("\n").append("<SENDERID>UTC</SENDERID>")
//					.append("\n").append(" <username>appuser</username>")
//					.append("\n").append("<credentials>appuser</credentials>");
//
//			inputXML.append("\n")
//					.append("<InqBusinessRulesReq>")
//					.append("<ruleFlowGroup>TFO-UTC</ruleFlowGroup>")
//					.append("\n")
//					.append("<requestChannelName>WMS</requestChannelName>")
//					.append("\n")
//					.append("<Eligibility>")
//					.append("<PAYLOAD type=\"UTC_ELIGIBILITY\"><UTC_ELIGIBILITY>"
//							+ "<REQUEST_CATEGORY>"
//							+ formObject.getValue(REQUEST_CATEGORY).toString()
//							+ "</REQUEST_CATEGORY>"
//							+ "<PROFIT_CENTER_CODE>"
//							+ profitCentre
//							+ "</PROFIT_CENTER_CODE>"
//							+ "<TRANSACTION_AMOUNT>"
//							+ amount
//							+ "</TRANSACTION_AMOUNT></UTC_ELIGIBILITY></PAYLOAD>"
//							+ "</Eligibility>").append("\n")
//					.append("</InqBusinessRulesReq>")
//					.append("</APWebService_Input>");
//			log.info("getTSLMEligibilityRequestXml  created ===> "
//					+ inputXML.toString());
//		} catch (Exception e) {
//
//			log.error("Exception: ", e);
//		}
//		return inputXML.toString();
//	}

}
