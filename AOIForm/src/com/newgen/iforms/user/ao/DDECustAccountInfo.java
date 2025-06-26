package com.newgen.iforms.user.ao;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import org.json.simple.JSONArray;

import com.newgen.iforms.EControl;
import com.newgen.iforms.EControlStyle;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.IControl;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.iforms.user.ao.util.XMLParser;
import com.newgen.iforms.user.config.Constants;
import com.newgen.mvcbeans.model.WorkdeskModel;

import java.util.List;

public class DDECustAccountInfo extends Common implements Constants, IFormServerEventHandler{
	boolean bFormLoaded = false; //sOnLoad new variable name
	public boolean validate = false;
	int iEtihadValidate = 3;

	public DDECustAccountInfo(IFormReference formObject) {
		super(formObject);
	}

	@Override
	public void beforeFormLoad(FormDef arg0, IFormReference arg1) {
		log.info("Inside beforeFormLoad >>>");
		log.info("WorkItem Name: " + sWorkitemId);
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
		log.info("Inside executeServerEvent DDECustAccountInfo >>>");
		log.info("Event: " + eventType + ", Control Name: " + controlName + ", Data: " + data);
		sendMessageList.clear();
		String retMsg = getReturnMessage(true,controlName);
		try {
			if(eventType.equalsIgnoreCase(EVENT_TYPE_LOAD)) {
				onDDEFormLoad();
				populateUAEPassInfoStatus(sWorkitemId);
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)) {
				if("VALIDATE_PRODUCT_MODIFY".equalsIgnoreCase(controlName)) {
					if(checkFundTransferFields()) {
						return retMsg;
					}
				} else if("PRODUCT_ROW_CLICK".equalsIgnoreCase(controlName)) {
					enableDisableChequebookField();
					enableDisableProductFields();
				} else if(controlName.equalsIgnoreCase(BTN_SI_ADD)) {
					logInfo("onClickEventCPDMakerFourStep","Inside");
					if(formObject.getValue(SI_DEB_ACC_NO).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(SI_DEB_ACC_NO, "Please select Debit to Product");
						return getReturnMessage(false);
					}
					if(formObject.getValue(SI_CURRENCY).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(SI_CURRENCY, "Please select Currency");
						return getReturnMessage(false);
					}
					if(formObject.getValue(SI_CRED_PROD).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(SI_CRED_PROD, "Please select Credit to Product");
						return getReturnMessage(false);
					}
					if(formObject.getValue(SI_FRST_PAYMNT).toString().trim().equalsIgnoreCase("")) {
						sendMessageValuesList(SI_FRST_PAYMNT, "Please select First Payment Date");
						return getReturnMessage(false);
					} else {
						boolean bReturn = validateSIDate(SI_FRST_PAYMNT,"First Payment ");
						if(bReturn == false) {
							return getReturnMessage(false);
						}
					}
					if(formObject.getValue(SI_LST_PAYMNT).toString().trim().equalsIgnoreCase("")) {
						sendMessageValuesList(SI_LST_PAYMNT, "Please select Last Payment Date");
						return getReturnMessage(false);
					} else {
						boolean bReturn =  validateSIDate(SI_LST_PAYMNT,"Last Payment ");
						if(bReturn == false) {
							return getReturnMessage(false);
						}
						if(!formObject.getValue(SI_LST_PAYMNT).toString().equalsIgnoreCase("")
								&& !formObject.getValue(SI_FRST_PAYMNT).toString().equalsIgnoreCase("")) {
							boolean isFuture = compareDateMMM(formObject.getValue(SI_LST_PAYMNT).toString(),formObject.getValue(SI_FRST_PAYMNT).toString());
							if(!isFuture) {
								sendMessageValuesList("", "Last Payment Date Should Be Greater Then First Payment Date.");
								return getReturnMessage(false);
							}
						}
						if(formObject.getValue(SI_PERIOD).toString().equalsIgnoreCase("")) {
							sendMessageValuesList(SI_PERIOD, "Please select Period");
							return getReturnMessage(false);
						}
						if(formObject.getValue(SI_TRNS_AMT).toString().equalsIgnoreCase("")) {
							sendMessageValuesList(SI_TRNS_AMT, "Please fill Transfer Amount");
							return getReturnMessage(false);
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
						return getReturnMessage(false);
					}
					if(formObject.getValue(SI_CURRENCY).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(SI_CURRENCY, "Please select Currency");
						return getReturnMessage(false);
					}
					if(formObject.getValue(SI_CRED_PROD).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(SI_CRED_PROD, "Please select Product Description");
						return getReturnMessage(false);
					}
					if(formObject.getValue(SI_FRST_PAYMNT).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(SI_FRST_PAYMNT, "Please select First Payment");
						return getReturnMessage(false);
					} else {
						boolean bReturn = validateSIDate(SI_FRST_PAYMNT,"First Payment Date");
						if(bReturn == false) {
							return getReturnMessage(false);
						}
					}
					if(formObject.getValue(SI_LST_PAYMNT).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(SI_LST_PAYMNT, "Please select Last Payment Date");
						return getReturnMessage(false);
					}
					if(!formObject.getValue(SI_LST_PAYMNT).toString().equalsIgnoreCase("") 
							&& !formObject.getValue(SI_FRST_PAYMNT).toString().equalsIgnoreCase("")) {
						boolean isFuture= compareDateMMM(formObject.getValue(SI_LST_PAYMNT).toString(),
								formObject.getValue(SI_FRST_PAYMNT).toString());
						if(!isFuture) {
							sendMessageValuesList("", "Last Payment Date Should Be Greater Then First Payment Date.");
							return getReturnMessage(false);
						}
					}
					if(formObject.getValue(SI_PERIOD).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(SI_PERIOD, "Please select Period");
						return getReturnMessage(false);
					}
					if(formObject.getValue(SI_TRNS_AMT).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(SI_TRNS_AMT, "Please fill Transfer Amount");
						return getReturnMessage(false);
					}
					return "true"; 
					
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
				} else if(PRODUCT_QUEUE.equalsIgnoreCase(controlName) || "PROD_CODE".equalsIgnoreCase(controlName)) {
					if(addProductInGrid()) {
						return getReturnMessage(true, controlName);
					}
				} else if("PRODUCT_QUEUE_POST".equalsIgnoreCase(controlName)) {
					EnableEtihadFrame();
					LoadDebitCardCombo();
					EnableFamilyReffered();
				} else if(TABCLICK.equalsIgnoreCase(controlName)) {
					/*if(sActivityName.equalsIgnoreCase(ACTIVITY_DDE_ACCOUNT_INFO)) {
						editButtonVisible();
					}*/
					onTabClick(data);
				} else if(controlName.equalsIgnoreCase(DC_BTN_ADD)){
					if(addDebitCard()){
						return  getReturnMessage(true, DC_BTN_ADD);
					}
				} else if(controlName.equalsIgnoreCase("DELETEPRODUCTROW")){
					updateCustSnoInProductGrid(Integer.parseInt(data));
				} else if("RETRY".equalsIgnoreCase(controlName) || "BTN_DOC_GEN_RETRY".equalsIgnoreCase(controlName)) {
					if(data.equalsIgnoreCase("true") || data.equalsIgnoreCase("yes")) {
						if(generateTemplate()) {
							logInfo("CLICK EVENT RETRY BUTTON","inside if condition of generateTemplate");
							executeAPTemplate("Other");
							refreshDocRepeater();
							refreshWorkitemWithDoc();
							formObject.setStyle(BTN_SUBMIT,DISABLE,TRUE);
						}
					}
				} else if(BTN_DOC_GEN_REFRESH.equalsIgnoreCase(controlName)) {
					logInfo("CLICK EVENT REFRESH","after clicking refresh");
						refreshDocRepeater();
						refreshWorkitemWithDoc();
				} else if(SAVE_TAB_CLICK.equalsIgnoreCase(controlName)) {
					onSaveTabClickDDE(Integer.parseInt(data));
				} else if("saveNextTabClick".equalsIgnoreCase(controlName)) {
					onSaveTabClickDDE(Integer.parseInt(data.split(",")[1]));
					if(saveAndNextValidations((String)data.split(",")[1])) {
						logInfo("saveNextTabClick","saveAndNextValidations successful");
						return getReturnMessage(true, controlName);
					}
				} else if(controlName.equalsIgnoreCase(BTN_ECD_VALIDATE)){
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
					} else if(sReturnCode.equalsIgnoreCase("01")){
						if(!sCustomerID.equalsIgnoreCase("") && sReturnValue.equalsIgnoreCase(sCustomerID)){
							sendMessageValuesList("","Etihad Number Validated Successfully.");
							//iEtihadValidate=2;
							formObject.setValue(IS_ETIHAD,"2");
						} else{
							sendMessageValuesList(ETIHAD_NO,"Etihad Number is already associated with customer ID "+sReturnValue+"\n"+"Please enter a new Etihad Number");
							formObject.setValue(ETIHAD_NO,"");
						}
					} else {
						sendMessageValuesList(ETIHAD_NO,"Etihad Number is not valid. Please enter a new Etihad Number");
						formObject.setValue(ETIHAD_NO,"");
					}
				} else if (VIEW.equalsIgnoreCase(controlName)){
					String selectedIndex = String.valueOf(setDedupeView());
					return getReturnMessage(true, selectedIndex, "DedupeSelectedIndex");
				} else if (controlName.equalsIgnoreCase("AccInfo_UdfList")) {  //ACCOUNT INFO SAVE AND NECT BUTTON
					logInfo("AccInfo_UdfList","Inside udf_addRow");
					logInfo("AccInfo_UdfList","ACC_INFO_UDF_FIELD: " 
							+ formObject.getValue("table95_UDF Field").toString());
					if (formObject.getValue("table95_UDF Field").toString().equalsIgnoreCase("")) {
						sendMessageValuesList("table95_UDF Field","Select a UDF field first.");
						//						return false;
					} else {
						if (UdfUniquenessCheck(formObject.getValue("table95_UDF Field").toString())) {
							logInfo("AccInfo_UdfList","Inside udf_addRow 1");
							if (formObject.getValue("table95_UDF Field").toString().equalsIgnoreCase("Graduation Date")) {
								logInfo("AccInfo_UdfList","Inside udf_addRow 2");
								if (validateJavaDate(formObject.getValue("table95_UDF Value").toString())) {
									logInfo("AccInfo_UdfList","Inside udf_addRo 3");
									/*String colnames = "UDF_FIELD,UDF_VALUE";
									String values = formObject.getValue(ACC_INFO_UDF_FIELD)+ "##"
											+ formObject.getValue(ACC_INFO_UDF_VALUE);
									//LoadListViewWithHardCodeValues("ACCINFO_UDF_LIST", colnames, values);
									formObject.setValue(ACC_INFO_UDF_VALUE, "");*/
								} else {
									sendMessageValuesList("table95_UDF Value","Date entered by you is not valid");
								}
							} else {
								logInfo("UDF_ADDROW","Inside UDF_ADDROW 4");
								/*String colnames = "UDF_FIELD,UDF_VALUE";
								String values = formObject.getValue(ACC_INFO_UDF_FIELD)+ "##" 
										+ formObject.getValue(ACC_INFO_UDF_VALUE);*/
								//LoadListViewWithHardCodeValues("ACCINFO_UDF_LIST", colnames, values);
//								formObject.setValue(ACC_INFO_UDF_VALUE, "");
							}
						} else {
							sendMessageValuesList("table95_UDF Field",formObject.getValue("table95_UDF Field").toString()
									+ " field already exist");
						}
					}
				}  else if(controlName.equalsIgnoreCase(BTN_FCR_SRCH)) {
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
				} else if (MANUAL_VISANO.equalsIgnoreCase(controlName)){
					setVisaNoManual();
				} else if ("Command55".equalsIgnoreCase(controlName)) {// should be save button 
					if(formObject.getValue(NOM_REQ)==null || formObject.getValue(NOM_REQ) == ""){

					} else{
						if(formObject.getValue(NOM_REQ).toString().equalsIgnoreCase("Yes"))
						{
							if(formObject.getValue(EXISTING_NOM_PRSN).toString().equalsIgnoreCase("Yes"))	
							{
								boolean flg = nomDetailsUpdate(Integer.parseInt(data));
								if(!flg)
								{
									getReturnMessage(false, "");
								}
							}
							else if(formObject.getValue(EXISTING_NOM_PRSN).toString().equalsIgnoreCase("No"))
							{
								nomDetailsInsert();
							}
						}
					}
				} else if(controlName.equalsIgnoreCase("PRODUCT_QUEUE.MODE_OF_FUNDING")) //check grid row in js
				{
					String sQuery= "SELECT ACC_NO FROM USR_0_PRODUCT_EXISTING WHERE WI_NAME ='"+sWorkitemId
							+"' AND ACC_STATUS IN (SELECT DESCRIPTION FROM USR_0_ACCOUNT_STATUS_CODE WHERE CODE "
							+ "IN ('6','8')) AND CUSTOMER_ID='"+data+"'";
					List<List<String>> sOutput = formObject.getDataFromDB(sQuery);
					String sDebitAccNo = sOutput.get(0).get(0);
					return getReturnMessage(true, sDebitAccNo, sDebitAccNo);
				} else if(controlName.equalsIgnoreCase(REMOVE_PRODUCT)) {
					if(removeProducts(Integer.parseInt(data))){
						return getReturnMessage(true, controlName);
					}
				} else if(controlName.equalsIgnoreCase(NEW_RM_CODE_CAT_CHANGE)) {
					String rm_code = formObject.getValue(NEW_RM_CODE_CAT_CHANGE).toString();
					String myQuery = "select rm_name from usr_0_rm where rm_code='"+rm_code+"'";
					List<List<String>> sOutput = formObject.getDataFromDB(myQuery);
					String rm_name = sOutput.get(0).get(0);
					formObject.setValue(NEW_RM_NAME_CAT_CHANGE,rm_name);
				} else if(controlName.equalsIgnoreCase(EDIT)) {
					logInfo("executeServerEvent","EDIT BUTTON");
					//formObject.setStyle(EDIT, DISABLE, TRUE);
					String sUpdateDecision="update "+sExtTable+" set back_route_flag='true' Where WI_NAME='"+ sWorkitemId +"'";
					int sout = formObject.saveDataInDB(sUpdateDecision);
					logInfo("executeServerEvent","sUpdateDecision: "+sUpdateDecision+" sout: "+sout);
					formObject.setValue(NO_OF_CUST_PROCESSED,"0");
					formObject.setValue(CUST_PROCESSED, "0");
					sBackRoute = TRUE;
					//return getReturnMessage(true, "completeWi", "completeWi");
					//formObject.RaiseEvent("WFDone");
				}else if(controlName.equalsIgnoreCase(CT_BTN_EIDA_REFRESH)){
					if("DIRECT-FIRST".equalsIgnoreCase(data)){
						String sQuery = "select CBG_EIDA_REFNO.nextval REFNO from dual";
						String refNo = "";
						List<List<String>> recordList = formObject.getDataFromDB(sQuery);
						if (recordList != null && recordList.size() > 0 )
						{
							logInfo("BTN_FETCH_EIDA_INFO"," refNo list =>  " + recordList.toString());
							refNo = recordList.get(0).get(0);
						}
						logInfo("BTN_FETCH_EIDA_INFO","  ref no " + refNo);
						String sQuery1 = "Insert into USR_0_AO_BIO_REF values('" + sWorkitemId + "','" + refNo + "')";
						int result = formObject.saveDataInDB(sQuery1);
						logInfo("BTN_FETCH_EIDA_INFO"," result : " + result);
						return getReturnMessage(true, refNo, "DIRECT-FIRST");
					} else if("DIRECT-SECOND".equalsIgnoreCase(data)){
						String ref_no = "";
						String sOutput = "SELECT SEQ_WEBSERVICE.nextval as ID from DUAL";
						List<List<String>> list = formObject.getDataFromDB(sOutput);
						String sOutput1 = "SELECT MAX(REF_NO)  from USR_0_AO_BIO_REF WHERE WI_NAME = '"+sWorkitemId+"'";
						List<List<String>> list1 = formObject.getDataFromDB(sOutput1);
						if(list1  != null && list1.size() >0){
							ref_no = list1.get(0).get(0);
						}
						StringBuilder inputXML = new StringBuilder();
						inputXML.append("<?xml version=\"1.0\"?>").append("\n")
						.append("<APWebService_Input>").append("\n")
						.append("<Option>WebService</Option>").append("\n")
						.append("<EngineName>"+sEngineName+ "</EngineName>").append("\n")
						.append("<winame>" + sWorkitemId + "</winame>").append("\n")
						.append("<Calltype>CBG</Calltype>").append("\n")
						.append("<CBGCallType>Inq_Cust_EmriatesID</CBGCallType>").append("\n")
						.append("<REF_NO>" + list.get(0).get(0) + "</REF_NO>").append("\n")
						.append("<OLDREF_NO>"+ list.get(0).get(0) +"</OLDREF_NO>").append("\n")
						.append("<senderID>WMSBPMENG</senderID>").append("\n")
						.append("<InqCustEmiratesIDAuthDtlsReq>").append("\n")
						.append("<channelId>WMSBPM</channelId>").append("\n")
						.append("<channelReferenceNumber>"+ref_no+"</channelReferenceNumber>").append("\n")
						.append("<inquryType>B</inquryType>").append("\n")
						.append("<photoRequestFlag>N</photoRequestFlag>").append("\n")	
						.append("</InqCustEmiratesIDAuthDtlsReq>").append("\n")
						.append("</APWebService_Input>");
						logInfo("BTN_FETCH_EIDA_INFO"," inputXML : " + inputXML.toString());
						String outputXML = socket.connectToSocket(inputXML.toString());
						logInfo("BTN_FETCH_EIDA_INFO"," outputXML : " + outputXML);
						if(outputXML != null){
							XMLParser xp = new XMLParser(outputXML);
							String dateOfBirth = xp.getValueOf("dateOfBirth").replaceAll("-", "/");
							String passportIssueDate = xp.getValueOf("passportIssueDate").replaceAll("-", "/");
							String passportExpiryDate = xp.getValueOf("passportExpiryDate").replaceAll("-", "/");
							formObject.addItemInCombo(EIDA_NATIONALITY,xp.getValueOf("nationality"));
							formObject.setValue(EIDA_NATIONALITY,xp.getValueOf("nationality"));
							formObject.setValue(EIDA_FULL_NAME,xp.getValueOf("eidaUserName"));
							formObject.setValue(EIDA_EIDA_NO,xp.getValueOf("eidaCardNumber"));
							formObject.setValue(EIDA_DOB,dateOfBirth);
							formObject.setValue(EIDA_PASS_NO,xp.getValueOf("passportNumber"));
							formObject.setValue(EIDA_PASS_ISSUE_DATE,passportIssueDate);
							formObject.setValue(EIDA_PASS_EXP_DATE,passportExpiryDate);
							formObject.setValue(EIDA_PASS_ISSUE_PLACE,xp.getValueOf("passportCountryDesc"));
							formObject.setValue(MOTHER_NAME_EIDA,xp.getValueOf("motherFirstName"));
							formObject.setValue(EIDA_ADDRESS,xp.getValueOf("homeAddressBuilding"));
							formObject.addItemInCombo(EIDA_CITY,xp.getValueOf("homeAddressCityDesc"));
							formObject.setValue(EIDA_CITY,xp.getValueOf("homeAddressCityDesc"));
							formObject.addItemInCombo(EIDA_STATE,xp.getValueOf("homeAddressEmirateDesc"));
							formObject.setValue(EIDA_STATE,xp.getValueOf("homeAddressEmirateDesc"));
							formObject.setValue(EIDA_PHONE_NO,xp.getValueOf("homeAddressMobilePhoneNumber"));
							formObject.setValue(EIDA_MOBILE_NO,xp.getValueOf("homeAddressResidentPhoneNumber"));
							formObject.setValue(EIDA_EMAIL,xp.getValueOf("homeAddressEmail"));
							formObject.setValue(EIDA_PROFESSION,xp.getValueOf("occupation"));
							formObject.addItemInCombo(EIDA_PREFIX,xp.getValueOf("title"));
							formObject.setValue(EIDA_PREFIX,xp.getValueOf("title"));
							formObject.setValue(EIDA_EMPLYR_NAME,xp.getValueOf("workAddressCompanyName"));
							formObject.setValue(EIDA_GENDER,xp.getValueOf("gender"));
							formObject.setValue(EIDA_OCC_TYPE,xp.getValueOf("occupation"));
							formObject.addItemInCombo(EIDA_PASSPORT_TYPE,xp.getValueOf("passportType"));
							formObject.setValue(EIDA_PASSPORT_TYPE,xp.getValueOf("passportType"));
							formObject.setValue(EIDA_VISANO,"");
							formObject.setValue(EIDA_VISAEXPDATE,"");
						}
					} else if("ALTERNATE-FIRST".equalsIgnoreCase(data)){
						String sCustID = "";
						logInfo("ALTERNATE-FIRST","inside --- if EIDA refresh button is clicked and AO_CHANNEL_TYPE is alternate ");
						if(!formObject.getValue(CHECKBOX_EIDANO_MANUAL).toString().equalsIgnoreCase("True"))
						{
							sendMessageValuesList(CHECKBOX_EIDANO_MANUAL, "Please enter EIDA Number Manually");
						}
						else if (formObject.getValue(CHECKBOX_EIDANO_MANUAL).toString().equalsIgnoreCase("True"))
						{
							String sEidaNo = formObject.getValue(EIDANO_MANUAL).toString();
							logInfo("ALTERNATE-FIRST","EIDA NUMBER MANUAL--------"+sEidaNo);
							if(sEidaNo.equalsIgnoreCase(""))
							{
								sendMessageValuesList(EIDANO_MANUAL, "Please enter EIDA Number");
							}
							else 
							{
								logInfo("ALTERNATE-FIRST","before EIDANO_MANUAL.length===15");
								if(formObject.getValue(EIDANO_MANUAL).toString().length()!= 15)
								{
									logInfo("ALTERNATE-FIRST","inside length is not equal to=====15");
									sendMessageValuesList(EIDANO_MANUAL, "The EIDA number should be 15 digits");
								}
								if(formObject.getValue(EIDANO_MANUAL).toString().length() == 15)
								{
									logInfo("ALTERNATE-FIRST","inside formObject.getNGValue(EIDANO_MANUAL).length() == 15");
									String sQuery1 = "SELECT CUST_ID FROM USR_0_CUST_TXN WHERE WI_NAME='"+sWorkitemId+"' AND CUST_SNO='1'";			
									List<List<String>> sOutput1 = formObject.getDataFromDB(sQuery1);
									if(sOutput1 !=null && sOutput1.size() > 0){
										sCustID = sOutput1.get(0).get(0);
									}
									logInfo("ALTERNATE-FIRST","calling setEIDAListInputXML------sEidaNo="+sEidaNo+" and sCustID="+sCustID);
									setEIDAListInputXML(sEidaNo,sCustID);
								}
							}

						}
						
					}
				} else if(controlName.equalsIgnoreCase(FETCH_INFO)) { //DP
					executeFetch(data);
					if(controlName.equalsIgnoreCase(FETCH_INFO)  && formObject.getValue(RD_INST_DEL).toString().equalsIgnoreCase("Yes")) {
						logInfo("CHANGE EVENT: FETCH_INFO","Fetch info button clicked after no radio button is clicked........111111");
						formObject.setStyle(NOM_REQ, DISABLE, TRUE);
						formObject.setStyle(EXISTING_NOM_PRSN, DISABLE, TRUE);
						formObject.clearTable(DELIVERY_PREFERENCE);
					}
				} else if (controlName.equalsIgnoreCase(INSTANT_DEL)) {//add INSTANT_DEL in ext_table or use INSTANT_DEL_YES for mapping
					if(formObject.getValue(INSTANT_DEL).toString().equalsIgnoreCase("Y")) {
						//						formObject.setValue(INSTANT_DEL_YES,FALSE);
						//						formObject.setValue(INSTANT_DEL_NO,TRUE);
						//						logInfo("","19/04/2016 YES value is === "+formObject.getValue(INSTANT_DEL_YES));
						//						logInfo("","19/04/2016 NO value is === "+formObject.getValue(INSTANT_DEL_NO));
						FETCH_INFO_flag_NO = false;
						FETCH_INFO_flag = false;
						String[] cName = {BRNCH_OF_INSTANT_ISSUE,"TEXT131",DEL_MODE,DEL_EXCELLENCY_CNTR,DEL_BRNCH_COURIER,
								DEL_BRNCH_NAME,NOM_REQ,EXISTING_NOM_PRSN};
						disableControls(cName);
						formObject.clearTable(DELIVERY_PREFERENCE);
					} else if(formObject.getValue(INSTANT_DEL).toString().equalsIgnoreCase("N")) {
						String[] cName = {NOM_REQ,"TEXT131",DEL_MODE
								,DEL_EXCELLENCY_CNTR,DEL_BRNCH_COURIER,DEL_BRNCH_NAME,BRNCH_OF_INSTANT_ISSUE,NOM_REQ,
								EXISTING_NOM_PRSN};
						enableControls(cName);
						disableControls(new String[]{BRNCH_OF_INSTANT_ISSUE,EXISTING_NOM_PRSN,});
						formObject.setValue(NOM_REQ, "");
						Frame48_CPD_ENable();					
					}
				} else if(controlName.equalsIgnoreCase(CT_BTN_REFRESH)) {	
					setFCRValueonLoad(data);
				} else if(controlName.equalsIgnoreCase(CT_BTN_RESETALL)) {
					setManualFieldsBlank();
				}
				if(controlName.equalsIgnoreCase(BTN_DEDUPE_SEARCH)) {
					if (formObject.getValue(MANUAL_NAME).toString().equalsIgnoreCase("") 
							&& formObject.getValue(FCR_NAME).toString().equalsIgnoreCase("")
							&& formObject.getValue(EIDA_NAME).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(MANUAL_NAME, CA011);
					} 
					int iProcessedCustomer = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()); 
					checkDuplicate(iProcessedCustomer); 
					String sUpdateDedupe = "update USR_0_CUST_TXN set IS_DEDUPE_CLICK='true' Where WI_NAME='" 
							+ sWorkitemId +"' and cust_sno='"+(iProcessedCustomer+1)+"'";
					logInfo("Click BTN_DEDUPE_SEARCH", "sUpdateDedupe: "+sUpdateDedupe);
					formObject.saveDataInDB(sUpdateDedupe);
				} if(controlName.equalsIgnoreCase(TABCLICK) && "1".equalsIgnoreCase(data)) {		
					checkView();
					saveKYCInfo();
//					saveKycMultiDropDownData();
//					savePepAssessmentDetails();   //Ao Dcra
//					savePreAssessmentDetails();    //shahbaz
					saveComparisonData();
					saveIndividualInfo();
					saveIndividualContactInfo();	
					saveClientQuestionsData();  
					saveCRSDetails(); 
				} else if (controlName.equalsIgnoreCase(TABCLICK) && "4".equalsIgnoreCase(data)){				
					if(sActivityName.equalsIgnoreCase(ACTIVITY_DDE_ACCOUNT_INFO)) {
						boolean prodChangeFlag = checkProdChngForNoEligibility();
						if(!prodChangeFlag) {
							sendMessageValuesList("", "Customer is not eligible for cheque book. Please change the product");		
							getReturnMessage(false) ;
						}
						insertUdfDetails();	
					}
				} else if (controlName.equalsIgnoreCase(TABCLICK) && "5".equalsIgnoreCase(data)) {
					boolean prodChangeFlag = checkProdChngForNoEligibility();
					if(!prodChangeFlag) {
						sendMessageValuesList("", "Customer is not eligible for cheque book. Please change the product");		
						getReturnMessage(false) ;
					}
				} else if (controlName.equalsIgnoreCase(TABCLICK) && "5".equalsIgnoreCase(data)) {
					if(sActivityName.equalsIgnoreCase(ACTIVITY_DDE_ACCOUNT_INFO)) {
						boolean prodChangeFlag = checkProdChngForNoEligibility();
						if(!prodChangeFlag) {
							sendMessageValuesList("", "Customer is not eligible for cheque book. Please change the product");		
							getReturnMessage(false) ;
						}
					}
				} else if(controlName.equalsIgnoreCase(DEL_MODE)) {
					System.out.println("In side DEL_MODE_YES");
					if(formObject.getValue("DEL_MODE").toString().equalsIgnoreCase("Y")){
						System.out.println("In side DEL_MODE_YES IF ");
						formObject.setStyle(NEW_DEL_MODE, DISABLE, FALSE);
						formObject.setStyle(CHANNEL_NAME, DISABLE, FALSE);

					} else {
						System.out.println("In side DEL_MODE_YES Else");
						formObject.setStyle(NEW_DEL_MODE, DISABLE, TRUE);
						formObject.setStyle(CHANNEL_NAME, DISABLE, TRUE);	
					}
				} else if(controlName.equalsIgnoreCase(NEW_DEL_MODE)) {
					if(formObject.getValue(NEW_DEL_MODE).toString().equalsIgnoreCase("Internal Courier")) {
						formObject.clearCombo(CHANNEL_NAME);
						formObject.addItemInCombo(CHANNEL_NAME, "Aramex");
					} else if(formObject.getValue(NEW_DEL_MODE).toString().equalsIgnoreCase("Branch")) {
						String branch = "Select HOME_BRANCH from usr_0_home_branch order by 1";
						loadCombo(branch,CHANNEL_NAME);
					} else if(formObject.getValue("new_del_mode").toString().equalsIgnoreCase("Excellency")) {
						String excellency = "select sourcing_center from usr_0_sourcing_center where sourcing_channel ='Excellency' order by 1";
						loadCombo(excellency,CHANNEL_NAME);
					} else {
						formObject.clearCombo(CHANNEL_NAME);
					}
				} else if(controlName.equalsIgnoreCase(DEL_STATE)) {
					if(formObject.getValue(DEL_STATE).toString().equalsIgnoreCase("Others")) {
						formObject.setValue(DEL_STATE_OTHER, "");
						formObject.setStyle(DEL_STATE_OTHER, DISABLE, FALSE);

					} else {
						formObject.setValue(DEL_STATE_OTHER, "");
						formObject.setStyle(DEL_STATE_OTHER, DISABLE, TRUE);
					}
				} else if (controlName.equalsIgnoreCase(udf_addRow)) {
					if("".equalsIgnoreCase(formObject.getValue(UDF_Field).toString())) {
						sendMessageValuesList(UDF_Field, "Select a UDF field first");
					} else {
						if(UdfUniquenessCheck(formObject.getValue(UDF_Field).toString())) {
							if(formObject.getValue(UDF_Field).toString().equalsIgnoreCase("Graduation Date")) {
								if(validateJavaDate(formObject.getValue(UDF_Value).toString())) {
									//String sOutput= "<ListItems><ListItem><SubItem>"+formObject.getValue("UDF_Field")+"</SubItem><SubItem>"+formObject.getValue("UDF_Value")+"</SubItem></ListItem></ListItems>";			
									//formObject.NGAddListItem("AccInfo_UdfList",sOutput);
									formObject.setValue(UDF_Value,"");
								} else {
									sendMessageValuesList("", "Date entered by you is not valid");
								}
							} else {
								String sOutput= "<ListItems><ListItem><SubItem>"+formObject.getValue("UDF_Field")+"</SubItem><SubItem>"+formObject.getValue("UDF_Value")+"</SubItem></ListItem></ListItems>";			
								//	formObject.NGAddListItem("AccInfo_UdfList",sOutput);
								//	formObject.setValue("UDF_Value","");
							}
						} else {
							sendMessageValuesList("", formObject.getValue(UDF_Field)+" field already exist");
						}
					}
				} else if (controlName.equalsIgnoreCase(UDF_Field)) {
					if(formObject.getValue(UDF_Field).toString().equalsIgnoreCase("Graduation Date")) {
						formObject.setValue(UDF_Value,"DD/MM/YYYY");
					} else {
						formObject.setValue(UDF_Value,"");
					}	
				} else if (controlName.equalsIgnoreCase(udf_delRow)) {
					int gridIndex = getListCount("AccInfo_UdfList");
					System.out.println("---------CRS GRID INDEX----------: "+gridIndex);
					if(gridIndex>=0) {
						//formObject.removeItemFromCombo("AccInfo_UdfList","AccInfo_UdfList"));	
					} else {
						sendMessageValuesList("AccInfo_UdfList","Please select a row first");
					}
				}  else if(controlName.equalsIgnoreCase("CRS_CERTIFICATION_OBTAINED_0") && "true".equalsIgnoreCase((String) formObject.getValue("CRS_CERTIFICATION_OBTAINED_0"))) {
					formObject.setValue("CRS_Classification","UPDATED-DOCUMENTED");
				} else if(controlName.equalsIgnoreCase("CRS_CERTIFICATION_OBTAINED_1") && "true".equalsIgnoreCase((String) formObject.getValue("CRS_CERTIFICATION_OBTAINED_1"))) {
					formObject.setValue("CRS_Classification","UPDATED-UNDOCUMENTED");
				} else if(BTN_SUBMIT.equalsIgnoreCase(controlName) && data.contains("%%%")) {
					logInfo("submitWorkitem","Inside submitWorkitem");
					if(submitWorkitem(controlName,eventType) && submitDDEValidations(data)) {
						if(ACTIVITY_DDE_ACCOUNT_INFO.equalsIgnoreCase(sActivityName)) {
							retMsg = confirmOnSubmitInForLoop();
							if(retMsg.equalsIgnoreCase(TRUE)) {
								return getReturnMessage(true, controlName, CA008);
								/*if(postSubmitAccountInfoValidation()) {
									return getReturnMessage(true, controlName);
								}*/
							} else if(!retMsg.isEmpty()) {
								return retMsg;
							}
						} else {
							return getReturnMessage(true, controlName);
						}
					}
				} else if(BTN_SUBMIT.equalsIgnoreCase(controlName) && data.equalsIgnoreCase("confirmSubmit") 
						&& ACTIVITY_DDE_ACCOUNT_INFO.equalsIgnoreCase(sActivityName)) {
					if(postSubmitAccountInfoValidation() && submitDDEValidations(data)) { // added submitddevalidation for product chekc Gautam 20-05-2022
						return getReturnMessage(true, controlName);
					}
				} else if(BTN_SUBMIT.equalsIgnoreCase(controlName) && data.equalsIgnoreCase("confirmAppRisk")
						&& ACTIVITY_DDE_CUST_INFO.equalsIgnoreCase(sActivityName)) {
					int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
					String sCustNo = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 0);
					formObject.setValue(NIG_MAKER,"YES");
					String updateQuery = "update USR_0_CUST_TXN set NIGEXCEPTIONMAKER='YES' Where "
							+ "WI_NAME='"+formObject.getValue(WI_NAME).toString()+"' AND CUST_SNO ='"+sCustNo+"'";
					formObject.saveDataInDB(updateQuery);
					logInfo("submitWorkitem","Updated Successfully query: "+updateQuery);
					// DCRA CHANGES FOR Upgrade and CCO
					if(!(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only") ||
						 formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade"))){
					if(calculateIndRiskDDE() && submitDDEValidations(data)) {
						return getReturnMessage(true, controlName);
					      }
					}
					if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only") ||
							 formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade")){
					 if( submitDDEValidations(data)) {
						return getReturnMessage(true, controlName);
					}
				  }
				} else if(BTN_SUBMIT.equalsIgnoreCase(controlName) && data.contains("@@@") 
						&& ACTIVITY_DDE_ACCOUNT_INFO.equalsIgnoreCase(sActivityName)) {
					String[] dataArr = data.split("@@@");
					if("FOR_YES".equalsIgnoreCase(dataArr[0])) {
						postSubmitAccountInfoValidation();
					} else {
						sendMessageValuesList(PRODUCT_QUEUE, "");
					}
				} else if (controlName.equalsIgnoreCase(BTN_VALIDATE)) {
					ValidateFATCADetails("Mini");
					formObject.setStyle(COMBODOC, DISABLE, FALSE);
				} else if (controlName.equalsIgnoreCase(BTN_VALIDATEFATCA)) {
					ValidateFATCADetails("Main");
				} else if (controlName.equalsIgnoreCase(BTN_SELECT_CUST)) {
					fillDuplicateData(data);
				} else if(controlName.equalsIgnoreCase(FETCH_BALANCE)) {
					logInfo("fetch_balance","INSIDE fetch_balance");
					getRealTimeDetails();
				}else if(controlName.equalsIgnoreCase("DELETEGRID")){
					checkCRS();
				} else if(BTN_FG_VALIDATE.equalsIgnoreCase(controlName)) {	// Changes for Family Banking
					if(validateFBFetch()){
						familyBankingCalls();
					}
				} else if (controlName.equalsIgnoreCase(CRS_ADD)) {  //CRS TAB ADD BUTTON
					logInfo("CRS_ADD","CRS add clicked");
					logInfo("Gautam","<<<< 68 >>>>>>>>>");
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
										String colnames = "Sno,Country_Of_Tax_Residency,TaxPayer_Identification_Number,Reason_For_Not_Providing_TIN,Reason_Description";
										String values = Integer.toString(gridCount + 1) + "##"
												+ formObject.getValue(CRS_TAX_COUNTRY).toString()
												+ "##"
												+ formObject.getValue(CRS_TAXPAYERIDENTIFICATIONNUMBER).toString()
												+ "##"
												+ formObject.getValue(CRS_REASONNOTPROVIDINGTIN).toString()
												+ "##"
												+ formObject.getValue(REASON_DESC).toString();
										LoadListViewWithHardCodeValues(CRS_TAXCOUNTRYDETAILS, colnames, values);
										/*formObject.setValue(CRS_TAXPAYERIDENTIFICATIONNUMBER,"");
									formObject.setValue(REASON_DESC, "");*/
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
										String colnames = "Sno,Country_Of_Tax_Residency,TaxPayer_Identification_Number,Reason_For_Not_Providing_TIN,Reason_Description";
										String values = Integer.toString(gridCount + 1)
												+ "##"
												+ formObject.getValue(CRS_TAX_COUNTRY).toString()
												+ "##"
												+ formObject.getValue(CRS_TAXPAYERIDENTIFICATIONNUMBER).toString()
												+ "##"
												+ formObject.getValue(CRS_REASONNOTPROVIDINGTIN).toString()
												+ "##"
												+ formObject.getValue(REASON_DESC).toString();
										LoadListViewWithHardCodeValues(CRS_TAXCOUNTRYDETAILS, colnames,values);
										/*formObject.setValue(CRS_TAXPAYERIDENTIFICATIONNUMBER,"");
									formObject.setValue(REASON_DESC, "");*/
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
					logInfo("Gautam","<<<< 69 >>>>>>>>>");
					checkCRS();
				} else if(controlName.equalsIgnoreCase(BTN_APP_LEVEL_RISK)){//Addeed by reyaz 14-09-2023
					logInfo("DDECustAccountInfo","Inside BTN_APP_LEVEL_RISK click ");
					calculateIndRiskDDE();
					updateRiskAssessmentData();
					fieldValueUsr_0_Risk_Data();// to be made
					calculateAppRisk();
				} 
				if(sActivityName.equalsIgnoreCase(ACTIVITY_DDE_CUST_INFO))
				{
					fillManualDataInBelowFields(controlName,formObject.getValue(controlName).toString());
					fillFCRDataInBelowFields(controlName,formObject.getValue(controlName).toString());
					fillEIDADataInBelowFields(controlName,formObject.getValue(controlName).toString());
				}	

				//COMMAND17->delete button QUEUE_DC
				//SAVE AND NEXT--yamini
				//Standing Instruction/on SUBMIT
				saveStandingInstrInfo();
				saveStandInstrInfo();

			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_CHANGE)) {
				logInfo("CHANGE EVENT- " + controlName, "INSIDE");
				fillManualDataInBelowFields(controlName, formObject.getValue(controlName).toString());
				if(controlName.equalsIgnoreCase("table94_trnsfr_from_acc_no")) {
//					int iRows = getGridCount(PRODUCT_QUEUE);				
//					int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()); 
					String sMode = formObject.getValue("table94_mode_of_funding").toString();
					if(!sMode.equalsIgnoreCase("")) {
						String sAccNo = formObject.getValue("table94_trnsfr_from_acc_no").toString();
						String sQuery= "SELECT DISTINCT ACC_BALANCE,CURRENCY FROM USR_0_PRODUCT_EXISTING WHERE "
								+ "WI_NAME ='"+sWorkitemId+"' AND ACC_NO = '"+sAccNo+"' ";
						List<List<String>> sOutput=formObject.getDataFromDB(sQuery);
						if(sOutput.size()>0) {
							String sAccBalance = sOutput.get(0).get(0);
							String sCurrency = sOutput.get(0).get(1);
							logInfo("PRODUCT_QUEUE.TRNSFR_FROM_ACC_NO","sOutput: "+sOutput);
							logInfo("PRODUCT_QUEUE.TRNSFR_FROM_ACC_NO","sAccBalance: "+sAccBalance+", sCurrency: "
									+sCurrency);
							formObject.setValue("table94_from_acc_bal",sAccBalance);
							formObject.setValue("table94_trnsfr_from_currency",sCurrency);
						}
					} else {
						sendMessageValuesList(PRODUCT_QUEUE,"Please Select Mode of Transfer first");
					}
//					if(iRows>0) {}
				} else if("table94_mode_of_funding".equalsIgnoreCase(controlName)) {
					manageFundTransfer();
				} else if(controlName.equalsIgnoreCase(ADDITIONAL_SOURCES_INCOME_AED)){
					logInfo("ADDITIONAL_SOURCES_INCOME_AED", "inside ADDITIONAL_SOURCES_INCOME_AED: ");
					additionalSource();
		    	}else if(controlName.equalsIgnoreCase("LISTVIEW_PUR_ACC_RELATION")){
					logInfo("LISTVIEW_PUR_ACC_RELATION", "inside QVAR_ACC_POURPOSE: ");
					accountPurpose();
				} else if(MANUAL_PREFIX.equalsIgnoreCase(controlName)) {
					setGender();
					formObject.setValue(CUST_GENDER, formObject.getValue(MANUAL_GENDER).toString());
				} else if(controlName.equalsIgnoreCase("CRS_CERTIFICATION_OBTAINED_0") && "true".equalsIgnoreCase((String) formObject.getValue("CRS_CERTIFICATION_OBTAINED_0"))) {
					formObject.setValue("CRS_Classification","UPDATED-DOCUMENTED");
				} else if(controlName.equalsIgnoreCase("CRS_CERTIFICATION_OBTAINED_1") && "true".equalsIgnoreCase((String) formObject.getValue("CRS_CERTIFICATION_OBTAINED_1"))) {
					formObject.setValue("CRS_Classification","UPDATED-UNDOCUMENTED");
				}else if(COMBODOC.equalsIgnoreCase(controlName) 
						&& "W8BEN".equalsIgnoreCase(formObject.getValue(COMBODOC).toString())) {
					formObject.setValue(FAT_CUST_CLASSIFICATION, "NON-US PERSON");
				} else if(CP_CITY.equalsIgnoreCase(controlName) || PA_CITY.equalsIgnoreCase(controlName) 
						|| RA_CITY.equalsIgnoreCase(controlName)) { //load also 
					manageCity(controlName);
				} else if(PD_CUSTSEGMENT.equalsIgnoreCase(controlName)) {
					setRMCode();
					manageInternalSection();
					if(formObject.getValue(SELECTED_ROW_INDEX).toString().isEmpty()) {
						formObject.setValue(SELECTED_ROW_INDEX,"0");
					}
					int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
					String sAccRelation = formObject.getTableCellValue(ACC_RELATION, fieldToFocus, 9);
					String bnk_relation = formObject.getTableCellValue(ACC_RELATION, fieldToFocus, 7);
					if(sAccRelation.equalsIgnoreCase("Existing")) {
						Frame22_CPD_Disable();
					}
					managePromoCode();
					if(bnk_relation.equalsIgnoreCase("Existing") || bnk_relation.equalsIgnoreCase("New")) {
						String segment = formObject.getValue(PD_CUSTSEGMENT).toString();
						if(bnk_relation.equalsIgnoreCase("Existing")) {
							disableControls(new String[]{IDS_OTH_CB_OTHERS, IDS_BNFT_CB_OTHERS});
							formObject.setValue(IDS_OTH_CB_OTHERS, FALSE);
							formObject.setValue(IDS_BNFT_CB_OTHERS, "");
							if(segment.equalsIgnoreCase("Aspire") || segment.equalsIgnoreCase("Privilege") 
									|| segment.equalsIgnoreCase("Excellency") || segment.equalsIgnoreCase("Emirati") 
									|| segment.equalsIgnoreCase("Emirati Excellency")){
								formObject.setStyle(RM_CODE, DISABLE, TRUE);	
							}					
						}
						if(bnk_relation.equalsIgnoreCase("New")) {	
							formObject.setStyle(PRO_CODE, DISABLE, FALSE);	
							formObject.setStyle(EXCELLENCY_CNTR, DISABLE, FALSE);	
							if(segment.equalsIgnoreCase("Aspire")) {
								getPromoCode("Aspire");	
								visibleOnSegmentBasis("Aspire");
							} else if(segment.equalsIgnoreCase("Privilege") || segment.equalsIgnoreCase("Emirati")) {
								getPromoCode(segment);
								visibleOnSegmentBasis("Privilege");
							} else if(segment.equalsIgnoreCase("Excellency") 
									|| segment.equalsIgnoreCase("Emirati Excellency")) {
								getPromoCode(segment);
								visibleOnSegmentBasis("Excellency"); 
							} else if(segment.equalsIgnoreCase("Signatory")) {
								int sno = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
								if(!(formObject.getTableCellValue(ACC_RELATION, sno, 9).equalsIgnoreCase("AUS") 
										|| formObject.getTableCellValue(ACC_RELATION, sno, 9).equalsIgnoreCase("POA"))) {
									sendMessageValuesList("","Signatory not allowed. Please select another type.");
									formObject.setValue(PD_CUSTSEGMENT, ""); 
									formObject.setValue(RM_CODE, "");
									formObject.setValue(RM_NAME, "");
								} else {
									getPromoCode("Signatory");
									visibleOnSegmentBasis("Signatory");	
								} 
							} else if(segment.equalsIgnoreCase("Private Clients")) {								
								getPromoCode("Private Clients");
								visibleOnSegmentBasis("Private Clients");
							} else if(segment.equalsIgnoreCase("Simplylife")) {
								getPromoCode("Simplylife");
								visibleOnSegmentBasis("Simplylife");
							}
							if(!segment.isEmpty()) {
								formObject.setStyle(IDS_OTH_CB_OTHERS,DISABLE, FALSE);		
								formObject.setValue(IDS_OTH_CB_OTHERS, FALSE);
								formObject.setStyle(IDS_BNFT_CB_OTHERS, DISABLE, TRUE);
								formObject.setValue(IDS_BNFT_CB_OTHERS,"");
							} else {
								disableControls(new String[]{IDS_OTH_CB_OTHERS, IDS_BNFT_CB_OTHERS, PRO_CODE, EXCELLENCY_CNTR});
								formObject.setValue(IDS_OTH_CB_OTHERS,FALSE);
								formObject.setValue(IDS_BNFT_CB_OTHERS,"");
								showControls(new String[]{IDS_PC_CB_TP, IDS_PC_CB_TRAVEL, IDS_PC_CB_SPORT, IDS_PC_CB_SHOPPING, IDS_PC_CB_ENTERTAINMENT, 
										IDS_CB_SAL_TRANSFER, IDS_CB_MORTGAGES, IDS_CB_INSURANCE, IDS_CB_TRB, IDS_CB_OTHERS, IDS_CB_VVIP, IDS_BNFT_CB_TP});
								//								formObject.setNGVisible("Label128",true);
								//								formObject.setNGVisible("Label134",true);
								//								formObject.setNGVisible("Label133",true);
							}
						} 
						if(bnk_relation.equalsIgnoreCase("Existing")) {
							String seg = formObject.getValue(PD_CUSTSEGMENT).toString();
							segmentSelectionForExistingcustomer(seg);
						}
					}
				} else if(MANUAL_FIRSTNAME.equalsIgnoreCase(controlName) || 
						MANUAL_LASTNAME.equalsIgnoreCase(controlName)) { 
					formObject.setValue(MANUAL_NAME,formObject.getValue(MANUAL_FIRSTNAME).toString()+" "
							+formObject.getValue(MANUAL_LASTNAME).toString());
					formObject.setValue(CRS_FIRSTNAME,formObject.getValue(MANUAL_FIRSTNAME).toString());
					formObject.setValue(CRS_LASTNAME,formObject.getValue(MANUAL_LASTNAME).toString());
					formObject.setValue(PD_FULLNAME,formObject.getValue(MANUAL_NAME).toString());
					if(TRUE.equalsIgnoreCase(formObject.getValue(CHECKBOX_SHORTNAME_MANUAL ).toString())){
						shortnamefunctionality();
					}
				} else if(MANUAL_NAME.equalsIgnoreCase(controlName) 
						&& TRUE.equalsIgnoreCase(formObject.getValue(CHECKBOX_SHORTNAME_MANUAL ).toString())){
					shortnamefunctionality();
				} else if(MANUAL_VISASTATUS.equalsIgnoreCase(controlName)) {
					if(returnVisaStatus().equalsIgnoreCase("Residency Visa")) {
						formObject.setStyle(DRP_RESEIDA, DISABLE, FALSE);
					} else {
						formObject.setStyle(DRP_RESEIDA, DISABLE, TRUE);
					}
					if(ACTIVITY_DDE_CUST_INFO.equalsIgnoreCase(sActivityName)) {  //onload
						if("Under Processing".equalsIgnoreCase(returnVisaStatus()) && !bFormLoaded) {
							int status = updateDataInDB(sCustTxnTable, "VISA_NO", "'MDSA'", "cust_sno = '"+getPrimaryCustomerSNO()+"' "
									+ "AND WI_NAME = '"+sWorkitemId+"'");
							logInfo("Change Event: MANUAL_VISA_STATUS", "update status: "+status);
							formObject.setValue(CHECKBOX_VISA_NO_MANUAL, TRUE);
							String[] uncheckFields = {CHECKBOX_VISA_NO_FCR, CHECKBOX_VISA_NO_EIDA};
							uncheckCheckBoxes(uncheckFields);
							if(TRUE.equalsIgnoreCase(formObject.getValue(CHECKBOX_SELECTALL_MANUAL).toString()) 
									|| TRUE.equalsIgnoreCase(formObject.getValue(CHECKBOX_VISA_NO_MANUAL).toString())) {
								formObject.setValue(MANUAL_VISANO, "MDSA");
								formObject.setStyle(MANUAL_VISANO, DISABLE, TRUE);
								formObject.setValue(HD_VISA_NO, formObject.getValue(MANUAL_VISANO).toString());
							}
							sendMessageValuesList("", CA0172);
						}
					}
				} else if("COMBO34".equalsIgnoreCase(controlName)) { //hidden pass frame
					if(!returnVisaStatus().equalsIgnoreCase("Residency Visa")) {
						formObject.setStyle(DRP_RESEIDA, DISABLE, TRUE);
						formObject.setValue(DRP_RESEIDA, "");
					} else {
						formObject.setStyle(DRP_RESEIDA, DISABLE, FALSE);
					}
				} else if(EMP_STATUS.equalsIgnoreCase(controlName) 
						&& ACTIVITY_DDE_CUST_INFO.equalsIgnoreCase(sActivityName) 
						&& !formObject.getValue(controlName).toString().isEmpty()) {
					String empStatus = formObject.getValue(controlName).toString();
					if(empStatus.equalsIgnoreCase("Employed")) {
						String[] uncheckFields = {RA_CB_GEN_TRDNG_CMPNY, RA_CB_PRECIOUS_STONE_DEALER, 
								RA_CB_BULLN_COMMDTY_BROKR, RA_CB_REAL_STATE_BROKR, RA_CB_USD_AUTO_DEALER};
						uncheckCheckBoxes(uncheckFields);
						/*String[] disableFields = {ED_NATURE_OF_BUSNS, SI_DEB_ACC_NO, ED_NO_UAE_OVRS_BRNCH, 
								ED_COMP_WEBSITE, BUSINESS_NATURE_SECTION_FRM};*/
						String[] disableFields = {ED_NATURE_OF_BUSNS, SI_DEB_ACC_NO, ED_NO_UAE_OVRS_BRNCH, 
								ED_COMP_WEBSITE};
						disableControls(disableFields);
						disableControls(BUSINESS_NATURE_SECTION);
						formObject.setValue(ED_NO_UAE_OVRS_BRNCH, "");
						if(TRUE.equalsIgnoreCase(formObject.getValue(CHK_EMP_DETAIL).toString())) {
							formObject.setStyle(ED_EMP_TYPE, DISABLE, FALSE);
						}
						if(TRUE.equalsIgnoreCase(formObject.getValue(CHK_RISK_ASS).toString())) {
							String[] enableFields = {RA_IS_CUST_WRKNG_UAE, RA_IS_CUST_WRKNG_NON_UAE};
							enableControls(enableFields);
						}
					} else if(empStatus.equalsIgnoreCase("Self Employed")) {
						String[] enableFields = {ED_NATURE_OF_BUSNS, SI_DEB_ACC_NO, ED_NO_UAE_OVRS_BRNCH, 
								ED_COMP_WEBSITE,ED_PERC_OF_OWNRSHP};
						enableControls(enableFields);
						enableControls(BUSINESS_NATURE_SECTION);
						String[] clearFields = {RA_IS_CUST_WRKNG_UAE, RA_IS_CUST_WRKNG_NON_UAE, ED_EMP_TYPE};
						clearControls(clearFields);
						String[] disableFields = {ED_EMP_TYPE, RA_IS_CUST_WRKNG_UAE, RA_IS_CUST_WRKNG_NON_UAE};
						disableControls(disableFields);
					} else {
						String[] disableFields = {ED_NATURE_OF_BUSNS, SI_DEB_ACC_NO, ED_NO_UAE_OVRS_BRNCH, 
								ED_COMP_WEBSITE, ED_EMP_TYPE, RA_IS_CUST_WRKNG_UAE, RA_IS_CUST_WRKNG_NON_UAE,DEALS_IN_WMD};
						disableControls(disableFields);
						/*enableControls(BUSINESS_NATURE_SECTION);*/
						disableControls(BUSINESS_NATURE_SECTION);
						formObject.setValue(ED_EMP_TYPE, "");
						if(empStatus.equalsIgnoreCase("Salaried")) {
							String[] enableFields = {RA_IS_CUST_WRKNG_UAE, RA_IS_CUST_WRKNG_NON_UAE};
							enableControls(enableFields);
							formObject.setValue(ED_NO_UAE_OVRS_BRNCH, "");
							formObject.setStyle(ED_NO_UAE_OVRS_BRNCH, DISABLE, FALSE);
							String[] uncheckFields = {RA_CB_GEN_TRDNG_CMPNY, RA_CB_PRECIOUS_STONE_DEALER, 
									RA_CB_BULLN_COMMDTY_BROKR, RA_CB_REAL_STATE_BROKR, RA_CB_USD_AUTO_DEALER};
							uncheckCheckBoxes(uncheckFields);
						}
					}
					formObject.setValue(BN_OTHERS,"");
					formObject.setValue(RA_CB_OTHERS, FALSE);
					formObject.setStyle(BN_OTHERS, DISABLE, TRUE);
					if(!empStatus.equalsIgnoreCase("Self Employed")) {
						formObject.setStyle(RA_CB_OTHERS, DISABLE, TRUE);
					} else {
						formObject.setStyle(RA_CB_OTHERS, DISABLE, FALSE);
					}
				} else if(FCR_EMPLYR_NAME.equalsIgnoreCase(controlName) 
						|| EIDA_EMPLYR_NAME.equalsIgnoreCase(controlName) 
						|| MANUAL_EMPLYR_NAME.equalsIgnoreCase(controlName)) {
					String sIsFCREmpName = formObject.getValue(CHECKBOX_EMP_NAME_FCR).toString();
					String sIsEIDAEmpName = formObject.getValue(CHECKBOX_EMP_NAME_EIDA).toString();
					String sIsManualEmpName = formObject.getValue(CHECKBOX_EMP_NAME_MANUAL).toString();
					String sFCREmpName = formObject.getValue(FCR_EMPLYR_NAME).toString();
					String sEIDAEmpName = formObject.getValue(EIDA_EMPLYR_NAME).toString();
					String sManualEmpName = formObject.getValue(MANUAL_EMPLYR_NAME).toString();
					String sFinalEmpName = getFinalData(sIsFCREmpName,sIsEIDAEmpName,sIsManualEmpName,sFCREmpName,
							sEIDAEmpName,sManualEmpName);
					if(formObject.getValue(EMP_STATUS).toString().isEmpty()) {
						if(!sFinalEmpName.isEmpty()) {
							formObject.setValue(EMP_STATUS, "Employed");
						}
					}
						List<List<String>> sOutput = formObject.getDataFromDB("SELECT CD_STATUS FROM USR_0_EMPLOYER_MASTER"
								+ " WHERE EMP_NAME ='"+formObject.getValue(controlName)+"'");
						logInfo("","Emp Mast Output---"+sOutput);
						formObject.setValue(ED_CB_TML,"False");
						formObject.setValue(ED_CB_NON_TML,"False");
						if(sOutput != null && sOutput.size()>0){
						if(sOutput.get(0).get(0).equalsIgnoreCase("1") ||sOutput.get(0).get(0).equalsIgnoreCase("2"))
						{
							formObject.setValue(ED_CB_TML,"True");
						}
						else
						{
							formObject.setValue(ED_CB_NON_TML,"True");
						}
						}

						///added by Sourav on 6June for EmployerInfo
						logInfo("","Inside EMPNAME");
						String EmpName=formObject.getValue(EMPNAME).toString();
						if(!(formObject.getValue(EMPNAME).toString().equalsIgnoreCase("")) 
								&& !(formObject.getValue(EMPNAME).toString().equalsIgnoreCase("OTHERS"))){
							logInfo("","When EmpName is not empty");
							String sqString = "Select CD_ADDRESS1,CD_PO_BOX_NO,CD_CITY,CD_STATE from"
									+ " company_details where CD_NAME like '%"+sFinalEmpName+"%'";
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
							formObject.setValue(EMP_STATE_OTHERS,"");
							formObject.setValue(EMP_CITY_OTHERS,"");
						}
					
					
				} else if(FCR_RESIDENT.equalsIgnoreCase(controlName) || EIDA_RESIDENT.equalsIgnoreCase(controlName) 
						|| MANUAL_RESIDENT.equalsIgnoreCase(controlName) 
						|| FCR_NATIONALITY.equalsIgnoreCase(controlName) 
						|| EIDA_NATIONALITY.equalsIgnoreCase(controlName) 
						|| MANUAL_NATIONALITY.equalsIgnoreCase(controlName)) {	
					manageResidencyAndVisaStatus(RA_IS_UAE_RESIDENT,"COMBO34");
				} else if(MANUAL_CNTRY.equalsIgnoreCase(controlName)) {
					manageCityStateManual(formObject.getValue(controlName).toString());
					formObject.setValue(CPD_RISK_ASSESS_MARKS, "");
					formObject.setValue(OTHER_CORR_CITY, "");
				} else if(RES_CNTRY.equalsIgnoreCase(controlName)) {
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
					} else if(!formObject.getValue(controlName).toString().equalsIgnoreCase("")) {
						formObject.clearCombo(RES_STATE);
						formObject.addItemInCombo(RES_STATE, OTHERTHENUAESTATES,OTHERTHENUAESTATES);
						formObject.setValue(RES_STATE,sState);
						formObject.clearCombo(RA_CITY);
						logInfo("RES_CNTRY","Lict COunt---"+getListCount(RA_CITY));
						formObject.addItemInCombo(RA_CITY,"OTHERS");
						formObject.setValue(RA_CITY,sCity);
						disableControls(new String[]{RES_MAKANI});
					}
				} else if(CORR_CNTRY.equalsIgnoreCase(controlName)) {
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
				} else if(PERM_CNTRY.equalsIgnoreCase(controlName)) {
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
				} else if(ACTIVITY_DDE_CUST_INFO.equalsIgnoreCase(sActivityName) 
						&& controlName.equalsIgnoreCase(FAT_CUST_CLASSIFICATION)) {
					Calendar calendar = Calendar.getInstance(); 
					SimpleDateFormat simpledateformat = new SimpleDateFormat(DATEFORMAT);
					String scurrentDate = simpledateformat.format(calendar.getTime());
					formObject.setValue(DATEPICKERCUST, scurrentDate);
				} //text79//text80-->Kritika (ACTIVITY_DDE_CUST_INFO)
				else if(ACTIVITY_DDE_ACCOUNT_INFO.equalsIgnoreCase(sActivityName) 
						&& controlName.equalsIgnoreCase(NEW_DEL_MODE)) {
					if(formObject.getValue(NEW_DEL_MODE).toString().equalsIgnoreCase("Internal Courier")){
						formObject.clearCombo(CHANNEL_NAME);
						formObject.addItemInCombo(CHANNEL_NAME, "Aramex");
					} else if(formObject.getValue(NEW_DEL_MODE).toString().equalsIgnoreCase("Branch")){
						String branch = "Select HOME_BRANCH from usr_0_home_branch order by 1";
						List<List<String>> result = formObject.getDataFromDB(branch);
						formObject.setValue(CHANNEL_NAME, result.get(0).get(0));
					}
					else if(formObject.getValue(NEW_DEL_MODE).toString().equalsIgnoreCase("Excellency")){
						String excellency = "select sourcing_center from usr_0_sourcing_center where sourcing_channel ='Excellency' order by 1";
						List<List<String>> result = formObject.getDataFromDB(excellency);
						formObject.setValue(CHANNEL_NAME, result.get(0).get(0));
						formObject.addItemInCombo(CHANNEL_NAME,CHANNEL_NAME);
					}else{
						formObject.clearCombo(CHANNEL_NAME);
					}
				} else if(controlName.equalsIgnoreCase(NEW_RM_CODE_CAT_CHANGE)) {
					logInfo("sdrf", "ddf");
					String rmCode = formObject.getValue(NEW_RM_CODE_CAT_CHANGE).toString();
					String sQuery = "select rm_name from usr_0_rm where rm_code='"+rmCode+"'";
					List<List<String>> sOutput = formObject.getDataFromDB(sQuery);
					String rmName = sOutput.get(0).get(0);
					formObject.setValue(NEW_RM_NAME_CAT_CHANGE,rmName);
				} else if(controlName.equalsIgnoreCase(PD_ANY_CHNG_CUST_INFO)) {
					String controlValue = formObject.getValue(controlName).toString();
					String sAccRelation= "";
					if(!formObject.getValue(SELECTED_ROW_INDEX).toString().isEmpty()) {
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
						sAccRelation = formObject.getTableCellValue(ACC_RELATION, fieldToFocus, 9);
					}
					manageCustomerDetailChange(controlValue);	
					manageFATCAFieldsEnable(formObject.getValue(PD_ANY_CHNG_CUST_INFO).toString(), sAccRelation);
					String segment = formObject.getValue(PD_CUSTSEGMENT).toString();
					getPromoCode(segment);
				} else if(controlName.equalsIgnoreCase(PA_SAMEAS)) {
					String controlValue = formObject.getValue(controlName).toString();
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
						//formObject.setValue(RA_STREET, formObject.getValue(CP_STREET).toString());
						formObject.setValue(PA_VILLAFLATNO, formObject.getValue(CP_FLOOR).toString());
						formObject.setValue(PA_BUILDINGNAME, formObject.getValue(CP_POBOXNO).toString());
						formObject.setValue(PA_STREET,formObject.getValue(CP_STREET).toString()); 
						//formObject.setValue(PERM_MAKANI,formObject.getValue(COR_MAKANI).toString()); //CONTACT_DETAILS_MAKANI_NO
						formObject.setValue(CONTACT_DETAILS_MAKANI_NO,formObject.getValue(COR_MAKANI).toString());
					} else {
						/*String[] clearFields = {PERM_COUNTRY, PERM_STATE, PA_STREET, RA_VILLAFLATNO, PA_CITY, PA_OTHERS,
								PA_BUILDINGNAME, CONTACT_DETAILS_CITY_OTHERS, PERM_MAKANI};*/ 
						//CONTACT_DETAILS_CITY_OTHERS,PERM_MAKANI,RA_VILLAFLATNO 
						String[] clearFields = {PERM_COUNTRY, PERM_STATE, PA_STREET, PA_VILLAFLATNO, PA_CITY, PA_OTHERS,
								PA_BUILDINGNAME, OTHER_PERM_CITY, CONTACT_DETAILS_MAKANI_NO};
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
						//formObject.setValue(OTHER_PERM_CITY, formObject.getValue(OTHER_CORR_CITY).toString());
						formObject.setValue(RES_MAKANI, formObject.getValue(COR_MAKANI).toString());	// added by harinder
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
						formObject.setValue(RES_CNTRY, formObject.getValue(PERM_CNTRY).toString());
						formObject.setValue(RES_STATE, formObject.getValue(PERM_STATE).toString());
						formObject.setValue(RA_BUILDINGNAME, formObject.getValue(PA_BUILDINGNAME).toString());
						formObject.setValue(RA_CITY, formObject.getValue(PA_CITY).toString());
						formObject.setValue(RA_OTHERS, formObject.getValue(PA_OTHERS).toString());
						//formObject.setValue(OTHER_RES_CITY, formObject.getValue(OTHER_PERM_CITY));
						//formObject.setValue(OTHER_PERM_CITY, formObject.getValue(OTHER_RESIDENTIAL_CITY).toString());
						//formObject.setValue("Text95", formObject.getValue(PA_OTHERS).toString());
						formObject.setValue(OTHER_RES_CITY,formObject.getValue(OTHER_PERM_CITY).toString());
						//formObject.setValue(RES_MAKANI,formObject.getValue(PERM_MAKANI).toString());  
						formObject.setValue(RES_MAKANI,formObject.getValue(CONTACT_DETAILS_MAKANI_NO).toString());  
					}  else {
						formObject.setValue(RES_CNTRY,"");
						formObject.setValue(RES_STATE,"");
						formObject.setValue(RA_BUILDINGNAME, "");
						formObject.setValue(RA_VILLAFLATNO,"");
						formObject.setValue(RA_CITY, "");
						formObject.setValue(RA_OTHERS,"");
						formObject.setValue(RA_STREET,"");
						formObject.setValue(OTHER_RES_CITY,""); 
						formObject.setValue(RES_MAKANI,"");	
					} 
				} else if(ACTIVITY_DDE_ACCOUNT_INFO.equalsIgnoreCase(sActivityName) 
						&& controlName.equalsIgnoreCase(DFC_STATIONERY_AVAIL)) {
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
				}else if (RD_INST_DEL.equalsIgnoreCase(controlName)){
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
				}else if (NOM_REQ.equalsIgnoreCase(controlName)){
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
				}
				
				/* else if(PD_CUSTSEGMENT.equalsIgnoreCase(controlName)) {
					manageInternalSection();
					if(formObject.getValue(SELECTED_ROW_INDEX).toString().isEmpty()) {
						formObject.setValue(SELECTED_ROW_INDEX,"0");
					}
					int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
					String sAccRelation = formObject.getTableCellValue(ACC_RELATION, fieldToFocus, 9);
					String bnk_relation = formObject.getTableCellValue(ACC_RELATION, fieldToFocus, 7);
					if(sAccRelation.equalsIgnoreCase("Existing")) {
						Frame22_CPD_Disable();
					}
					managePromoCode();
					if(bnk_relation.equalsIgnoreCase("Existing") || bnk_relation.equalsIgnoreCase("New")) {
						String segment = formObject.getValue(PD_CUSTSEGMENT).toString();
						if(bnk_relation.equalsIgnoreCase("Existing")) {
							disableControls(new String[]{IDS_OTH_CB_OTHERS, IDS_BNFT_CB_OTHERS});
							formObject.setValue(IDS_OTH_CB_OTHERS, FALSE);
							formObject.setValue(IDS_BNFT_CB_OTHERS, "");
							if(segment.equalsIgnoreCase("Aspire") || segment.equalsIgnoreCase("Privilege") 
									|| segment.equalsIgnoreCase("Excellency") || segment.equalsIgnoreCase("Emirati") 
									|| segment.equalsIgnoreCase("Emirati Excellency")){
								formObject.setStyle(RM_CODE, DISABLE, TRUE);	
							}					
						}
						if(bnk_relation.equalsIgnoreCase("New")) {	
							formObject.setStyle(PRO_CODE, DISABLE, FALSE);	
							formObject.setStyle(EXCELLENCY_CNTR, DISABLE, FALSE);	
							if(segment.equalsIgnoreCase("Aspire")) {
								getPromoCode("Aspire");	
								visibleOnSegmentBasis("Aspire");
							} else if(segment.equalsIgnoreCase("Privilege") || segment.equalsIgnoreCase("Emirati")) {
								getPromoCode(segment);
								visibleOnSegmentBasis("Privilege");
							} else if(segment.equalsIgnoreCase("Excellency") 
									|| segment.equalsIgnoreCase("Emirati Excellency")) {
								getPromoCode(segment);
								visibleOnSegmentBasis("Excellency"); 
							} else if(segment.equalsIgnoreCase("Signatory")) {
								int sno = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
								if(!(formObject.getTableCellValue(ACC_RELATION, sno, 9).equalsIgnoreCase("AUS") 
										|| formObject.getTableCellValue(ACC_RELATION, sno, 9).equalsIgnoreCase("POA"))) {
									sendMessageValuesList("","Signatory not allowed. Please select another type.");
									formObject.setValue(PD_CUSTSEGMENT, ""); 
									formObject.setValue(RM_CODE, "");
									formObject.setValue(RM_NAME, "");
								} else {
									getPromoCode("Signatory");
									visibleOnSegmentBasis("Signatory");	
								} 
							} else if(segment.equalsIgnoreCase("Private Clients")) {								
								getPromoCode("Private Clients");
								visibleOnSegmentBasis("Private Clients");
							} else if(segment.equalsIgnoreCase("Simplylife")) {
								getPromoCode("Simplylife");
								visibleOnSegmentBasis("Simplylife");
							}
							if(!segment.isEmpty()) {
								formObject.setStyle(IDS_OTH_CB_OTHERS,DISABLE, FALSE);		
								formObject.setValue(IDS_OTH_CB_OTHERS, FALSE);
								formObject.setStyle(IDS_BNFT_CB_OTHERS, DISABLE, TRUE);
								formObject.setValue(IDS_BNFT_CB_OTHERS,"");
							} else {
								disableControls(new String[]{IDS_OTH_CB_OTHERS, IDS_BNFT_CB_OTHERS, PRO_CODE, EXCELLENCY_CNTR});
								formObject.setValue(IDS_OTH_CB_OTHERS,FALSE);
								formObject.setValue(IDS_BNFT_CB_OTHERS,"");
								showControls(new String[]{IDS_PC_CB_TP, IDS_PC_CB_TRAVEL, IDS_PC_CB_SPORT, IDS_PC_CB_SHOPPING, IDS_PC_CB_ENTERTAINMENT, 
										IDS_CB_SAL_TRANSFER, IDS_CB_MORTGAGES, IDS_CB_INSURANCE, IDS_CB_TRB, IDS_CB_OTHERS, IDS_CB_VVIP, IDS_BNFT_CB_TP});
								//								formObject.setNGVisible("Label128",true);
								//								formObject.setNGVisible("Label134",true);
								//								formObject.setNGVisible("Label133",true);
							}
						} 
						if(bnk_relation.equalsIgnoreCase("Existing")) {
							String seg = formObject.getValue(PD_CUSTSEGMENT).toString();
							segmentSelectionForExistingcustomer(seg);
						}
					}
				} */else if(ACC_INFO_PG.equalsIgnoreCase(controlName)) {	
					if(formObject.getValue(ACC_INFO_PG).toString().isEmpty()) {
						clearControls(new String[]{GROUP_TYPE, CARD_TYPE, EMBOSS_NAME, NEW_LINK, EXISTING_CARD_NO});
					} else if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("New Account With Category Change") 
							&& formObject.getValue(NEW_CUST_SEGMENT).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(NEW_CUST_SEGMENT,"Please select New Category.");
					} else {						
						String[] sSelectedProduct = formObject.getValue(ACC_INFO_PG).toString().split("_");
						String sQuery  = "SELECT COD_PROD_TYPE FROM USR_0_PRODUCT_MASTER WHERE PRODUCT_CODE ='"
								+sSelectedProduct[1]+"'";
						logInfo("ACC_INFO_PG","sQuery: "+sQuery);
						List<List<String>> sOutput = formObject.getDataFromDB(sQuery);
						logInfo("ACC_INFO_PG","sOutput: "+sOutput.toString());
						String sCategory = "";
						if(sOutput.size()>0) {
							sCategory = sOutput.get(0).get(0);
						}
						if(!sCategory.isEmpty()) {
							if(sCategory.equalsIgnoreCase("I")) {
								formObject.setValue(GROUP_TYPE, "Islamic");
								formObject.setStyle(GROUP_TYPE, DISABLE, TRUE);
							} else {
								sQuery  = "SELECT SUB_PRODUCT_TYPE FROM USR_0_PRODUCT_TYPE_MASTER WHERE "
										+ "PRODUCT_CODE ='"+sSelectedProduct[1]+"'";
								sOutput = formObject.getDataFromDB(sQuery);
								sCategory = sOutput.get(0).get(0);
								if(!sCategory.equalsIgnoreCase("")) {
									if(sCategory.equalsIgnoreCase("Etihad")) {
										formObject.setValue(GROUP_TYPE,"Etihad");
										formObject.setStyle(GROUP_TYPE, DISABLE, TRUE);
									} else {
										formObject.setValue(GROUP_TYPE, "");
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
								return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));							}
						}
						if(sGroup.isEmpty()) {
							formObject.setValue(CARD_TYPE,"");
							formObject.setValue(NEW_LINK,"");
							enableControls(new String[]{CARD_TYPE, NEW_LINK});
							return getReturnMessage(false);
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
				} else if(GROUP_TYPE.equalsIgnoreCase(controlName) 
						&& !formObject.getValue(GROUP_TYPE).toString().isEmpty()) {
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
							return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));							}
					}
					if(sGroup.isEmpty()) {
						formObject.setValue(CARD_TYPE,"");
						formObject.setValue(NEW_LINK,"");
						enableControls(new String[]{CARD_TYPE, NEW_LINK});
						return getReturnMessage(false);
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
				} else if(controlName.equalsIgnoreCase(GROUP_TYPE)){
					formObject.setValue(CARD_TYPE,"");
					formObject.setValue(NEW_LINK,"");
					formObject.setStyle(CARD_TYPE,DISABLE,FALSE);
					formObject.setStyle(NEW_LINK,DISABLE,FALSE);
				} else if(NEW_LINK.equalsIgnoreCase(controlName)) {
					String controlValue = formObject.getValue(controlName).toString();
					List<List<String>> sOutput;
					String sQuery = "";
					String sGroup = "";
					String sGroupType = "";					
					int iRows = getGridCount(ACC_INFO_EDC_LVW);
					int iCount = 0;
					if(controlValue.equalsIgnoreCase("Link")) {
						String sProdCode = "";
						String sNewLink = "";
						String sProd = formObject.getValue(ACC_INFO_PG).toString();
						int iCount1 = Integer.parseInt(sProd.split("_")[2]);
						String productCurr = "";
						try {
							productCurr = formObject.getTableCellValue(PRODUCT_QUEUE, iCount1, "CURRENCY").toString();	
						} catch(Exception e) {

						}
						sGroup = formObject.getValue(GROUP_TYPE).toString();
						sQuery ="SELECT distinct(CARD_NO) FROM USR_0_DEBITCARD_EXISTING a,"
								+ "USR_0_DC_ACC_MAPPING_EXISTING b,usr_0_product_existing c where "
								+ "a.WI_NAME ='"+sWorkitemId+"' and a.wi_name= b.wi_name and "
								+ "card_no=DEBIT_CARD_NO AND STATUS IN ('0','1') and b.linked_acc_no= c.acc_no "
								+ "and c.product_code in (SELECT PRODUCT_CODE FROM USR_0_PRODUCT_TYPE_MASTER "
								+ "WHERE PROCESS_TYPE ='Onshore') and a.wi_name=c.wi_name and a.currency='"
								+productCurr+"'";
						formObject.setValue(CARD_TYPE,"");
						sOutput = formObject.getDataFromDB(sQuery);
						String sLinkCard = sOutput.get(0).get(0);
						formObject.clearCombo(EXISTING_CARD_NO);
						if(!sLinkCard.isEmpty()) {
							String sTempCard[] = sLinkCard.split(",");
							for(int i=0; i<sTempCard.length; i++) {
								formObject.addItemInCombo(EXISTING_CARD_NO, sTempCard[i]);
							}
						}
						int sno=0;
						for(int i=0;i<iRows;i++) {	
							sProdCode = formObject.getTableCellValue(QUEUE_DC, i, 0).toString();							
							sNewLink = formObject.getTableCellValue(QUEUE_DC, i, 4).toString();							
							sGroupType = formObject.getTableCellValue(QUEUE_DC, i, 1).toString();
							if("New".equalsIgnoreCase(sNewLink)) {
								sno=sno+1;
							}
							if(!sProdCode.equalsIgnoreCase(sProd) && !sNewLink.equalsIgnoreCase("Link") 
									&& sGroupType.equalsIgnoreCase(sGroup)) {
								formObject.addItemInCombo(EXISTING_CARD_NO,"CARD_"+sno);
							}
						}
						formObject.setStyle(EMBOSS_NAME, DISABLE, TRUE);
						formObject.setStyle(EXISTING_CARD_NO, DISABLE, FALSE);
					} else {
						String sCustID = getPrimaryCustomerID();
						formObject.clearCombo(EXISTING_CARD_NO);
						formObject.setValue(EXISTING_CARD_NO,"");
						formObject.setStyle(EMBOSS_NAME, DISABLE, FALSE);
						formObject.setStyle(EXISTING_CARD_NO, DISABLE, TRUE);
						int iCount1 = 0;
						sGroup = formObject.getValue(GROUP_TYPE).toString();
						for(int i=1; i<iRows; i++) {
							sGroupType = formObject.getTableCellValue(QUEUE_DC, i,GROUP_TYPE).toString();
							if(sGroupType.equalsIgnoreCase(sGroup)) {
								iCount1 = iCount1+1;
								break;
							}
						}
						if(sCustID.isEmpty() && iCount1 == 0) {
							formObject.setValue(CARD_TYPE,"Primary");
							formObject.setStyle(CARD_TYPE, DISABLE, TRUE);
						} else if(iCount1 != 0) {
							formObject.setValue(CARD_TYPE,"Supplementary");
							formObject.setStyle(CARD_TYPE, DISABLE, TRUE);
						} else {
							sQuery = "SELECT COUNT(WI_NAME) as COUNT_WI FROM USR_0_DEBITCARD_EXISTING "
									+ "WHERE WI_NAME ='"+sWorkitemId+"' AND CUST_ID='"+sCustID+"' AND "
									+ "STATUS NOT IN ('2','3','4') AND PRODUCT_GROUP IN (SELECT DISTINCT "
									+ "GROUPID FROM USR_0_CARD_PRODUCT_GROUP  WHERE PRODTYPE = '"+sGroup+"')";
							sOutput = formObject.getDataFromDB(sQuery);
							iCount1 = Integer.parseInt(sOutput.get(0).get(0));
							if(iCount1==0) {
								formObject.setValue(CARD_TYPE,"Primary");
							} else {
								formObject.setValue(CARD_TYPE,"Supplementary");
							}
							formObject.setStyle(CARD_TYPE, DISABLE, TRUE);
						}
					}
					formObject.setValue(EXISTING_CARD_NO, "");
				} else if(EMPNAME.equalsIgnoreCase(controlName) 
						&& !formObject.getValue(controlName).toString().isEmpty()) {
					String controlValue = formObject.getValue(controlName).toString();
					List<List<String>> sOutput = formObject.getDataFromDB("SELECT CD_STATUS FROM "
							+ "USR_0_EMPLOYER_MASTER WHERE EMP_NAME ='"+controlValue+"'");
					formObject.setValue(ED_CB_TML,FALSE);
					formObject.setValue(ED_CB_NON_TML,FALSE);
					if(sOutput.get(0).get(0).equalsIgnoreCase("1") || sOutput.get(0).get(0).equalsIgnoreCase("2")) {
						formObject.setValue(ED_CB_TML,TRUE);
					} else {
						formObject.setValue(ED_CB_NON_TML,TRUE);
					}
				} else if(controlName.equalsIgnoreCase(SI_DEB_ACC_NO)) {
					fetchCurrency(formObject.getValue(controlName).toString());	
				} else if(controlName.equalsIgnoreCase(NEW_RM_CODE_CAT_CHANGE)) {
					String rm_code = formObject.getValue(NEW_RM_CODE_CAT_CHANGE).toString();
					String myQuery = "SELECT RM_NAME FROM USR_0_RM WHERE RM_CODE='"+rm_code+"'";
					List<List<String>> sOutput = formObject.getDataFromDB(myQuery);
					String rm_name = sOutput.get(0).get(0);
					formObject.setValue(NEW_RM_NAME_CAT_CHANGE,rm_name);
				} else if(controlName.equalsIgnoreCase(NEW_RM_NAME_CAT_CHANGE)) {
					String sRMName = formObject.getValue(NEW_RM_NAME_CAT_CHANGE).toString().replaceAll("'", "''");
					String myQuery = "SELECT RM_CODE FROM USR_0_RM WHERE RM_NAME='"+sRMName+"'";
					List<List<String>> sOutput = formObject.getDataFromDB(myQuery);
					formObject.setValue(NEW_RM_CODE_CAT_CHANGE, sOutput.get(0).get(0));
				} else if(controlName.equalsIgnoreCase(COMBODOC)) {
					String controlValue = formObject.getValue(controlName).toString();
					if(controlValue.equalsIgnoreCase("W9")) {
						formObject.setStyle(FAT_SSN, DISABLE, FALSE);
						formObject.setValue(DATEPICKERW8,"");
						formObject.setStyle(DATEPICKERW8, DISABLE, TRUE);
					} else if(controlValue.equalsIgnoreCase("W8BEN")) {
						Calendar calendar = Calendar.getInstance();
						SimpleDateFormat simpledateformat = new SimpleDateFormat(DATEFORMAT);
						String scurrentDate = simpledateformat.format(calendar.getTime());
						formObject.setValue(FAT_SSN,"");
						formObject.setStyle(FAT_SSN, DISABLE, TRUE);
						formObject.setStyle(DATEPICKERW8, DISABLE, FALSE);
						formObject.setValue(DATEPICKERW8, scurrentDate);
					} else {
						formObject.setValue(FAT_SSN,"");
						formObject.setValue(DATEPICKERW8,"");
						disableControls(new String[]{FAT_SSN, DATEPICKERW8});
					}
					String sFinalCountryOfBirth = getFinalDataComparison(CHECKBOX_COB_FCR,CHECKBOX_COB_EIDA,CHECKBOX_COB_MANUAL,
							FCR_COUNTRYBIRTH,EIDA_COUNTRYBIRTH,MANUAL_COUNTRYBIRTH);
					if(sFinalCountryOfBirth.equalsIgnoreCase("UNITED STATES")) {
						List<List<String>> sOutput = formObject.getDataFromDB("SELECT FATCA_CLASSIFICATION FROM "
								+ "USR_0_DOC_MASTER WHERE DOC_NAME ='"+controlValue+"'");
						formObject.setValue(FAT_CUST_CLASSIFICATION, sOutput.get(0).get(0));
					} else {
						if(formObject.getValue(INDICIACOMBO).toString().equalsIgnoreCase("No")) {
							formObject.setValue(FAT_CUST_CLASSIFICATION,"NON-US PERSON");
						} else if (formObject.getValue(INDICIACOMBO).toString().equalsIgnoreCase("Yes")) {
							formObject.setValue(FAT_CUST_CLASSIFICATION,"US PERSON");
						}
					}
				} else if(controlName.equalsIgnoreCase(FAT_US_PERSON)) {
					String controlValue = formObject.getValue(controlName).toString();
					if(controlValue.equalsIgnoreCase("Yes")) {
						formObject.setValue(FAT_LIABLE_TO_PAY_TAX,"YES");
						formObject.setStyle(FAT_LIABLE_TO_PAY_TAX, DISABLE, TRUE);
					} else {
						formObject.setValue(FAT_LIABLE_TO_PAY_TAX, "");
						if(formObject.getValue(CHECKBOX_FATCA).toString().equalsIgnoreCase(TRUE)) {
							formObject.setStyle(FAT_LIABLE_TO_PAY_TAX, DISABLE, FALSE);
						}
					}
				} else if((controlName.equalsIgnoreCase(FCR_COUNTRYBIRTH)) 
						|| (controlName.equalsIgnoreCase(EIDA_COUNTRYBIRTH))
						|| (controlName.equalsIgnoreCase(MANUAL_COUNTRYBIRTH))) {
					String sFinalCountryOfBirth = getFinalDataComparison(CHECKBOX_COB_FCR,CHECKBOX_COB_EIDA,CHECKBOX_COB_MANUAL,
							FCR_COUNTRYBIRTH,EIDA_COUNTRYBIRTH,MANUAL_COUNTRYBIRTH);
					if(sFinalCountryOfBirth.equalsIgnoreCase("UNITED STATES")) {
						formObject.clearCombo(COMBODOC);
						formObject.addItemInCombo(COMBODOC, "W8BEN");
						formObject.addItemInCombo(COMBODOC, "W9");
					} else {
						formObject.clearCombo(COMBODOC);
						formObject.setValue(FAT_CUST_CLASSIFICATION, "");
						formObject.addItemInCombo(COMBODOC, "NA");
						formObject.addItemInCombo(COMBODOC, "W8BEN");
						formObject.addItemInCombo(COMBODOC, "W9");
					}
					formObject.setStyle(COMBODOC, DISABLE, FALSE);
				} else if(controlName.equalsIgnoreCase(NEW_CUST_SEGMENT)) {						
					if(formObject.getValue(NEW_CUST_SEGMENT).toString().isEmpty()) {
						uncheckCheckBoxes(new String[]{IS_SALARY_TRANSFER_CAT_CHANGE, IS_MORTAGAGE_CAT_CHANGE, 
								IS_INSURANCE_CAT_CHANGE, IS_TRB_CAT_CHANGE, IS_OTHERS_CAT_CHANGE, IS_VVIP, 
								IS_PREVILEGE_TP_CAT_CHANGE, IS_ENTERTAINMENT_CAT_CHANGE, IS_SHOPPING_CAT_CHANGE,
								IS_SPORT_CAT_CHANGE, IS_TRAVEL_CAT_CHANGE, IS_EXCELLENCY_TP_CAT_CHANGE,
								IS_CAT_BENEFIT_OTHER});
						disableControls(new String[]{IS_SALARY_TRANSFER_CAT_CHANGE, IS_MORTAGAGE_CAT_CHANGE, 
								IS_INSURANCE_CAT_CHANGE, IS_TRB_CAT_CHANGE, IS_OTHERS_CAT_CHANGE, IS_VVIP, 
								IS_PREVILEGE_TP_CAT_CHANGE, IS_ENTERTAINMENT_CAT_CHANGE, IS_SHOPPING_CAT_CHANGE,
								IS_SPORT_CAT_CHANGE, IS_TRAVEL_CAT_CHANGE, IS_EXCELLENCY_TP_CAT_CHANGE,
								IS_CAT_BENEFIT_OTHER, CAT_BENEFIT_OTHER});
						formObject.setValue(CAT_BENEFIT_OTHER, "");
						showControls(new String[]{IS_SALARY_TRANSFER_CAT_CHANGE, IS_MORTAGAGE_CAT_CHANGE, 
								IS_INSURANCE_CAT_CHANGE, IS_TRB_CAT_CHANGE, IS_OTHERS_CAT_CHANGE, IS_VVIP, 
								IS_PREVILEGE_TP_CAT_CHANGE, IS_ENTERTAINMENT_CAT_CHANGE, IS_SHOPPING_CAT_CHANGE,
								IS_SPORT_CAT_CHANGE, IS_TRAVEL_CAT_CHANGE, IS_EXCELLENCY_TP_CAT_CHANGE,
								IS_CAT_BENEFIT_OTHER, CAT_BENEFIT_OTHER});
						//						formObject.setNGVisible("Label299",true);
						//						formObject.setNGVisible("Label298",true);
						//						formObject.setNGVisible("Label297",true);							
					} else {
						uncheckCheckBoxes(new String[]{IS_SALARY_TRANSFER_CAT_CHANGE, IS_MORTAGAGE_CAT_CHANGE, 
								IS_INSURANCE_CAT_CHANGE, IS_TRB_CAT_CHANGE, IS_OTHERS_CAT_CHANGE, IS_VVIP, 
								IS_PREVILEGE_TP_CAT_CHANGE, IS_ENTERTAINMENT_CAT_CHANGE, IS_SHOPPING_CAT_CHANGE,
								IS_SPORT_CAT_CHANGE, IS_TRAVEL_CAT_CHANGE, IS_EXCELLENCY_TP_CAT_CHANGE,
								IS_CAT_BENEFIT_OTHER});
						enableControls(new String[]{IS_SALARY_TRANSFER_CAT_CHANGE, IS_MORTAGAGE_CAT_CHANGE, 
								IS_INSURANCE_CAT_CHANGE, IS_TRB_CAT_CHANGE, IS_OTHERS_CAT_CHANGE, IS_VVIP, 
								IS_PREVILEGE_TP_CAT_CHANGE, IS_ENTERTAINMENT_CAT_CHANGE, IS_SHOPPING_CAT_CHANGE,
								IS_SPORT_CAT_CHANGE, IS_TRAVEL_CAT_CHANGE, IS_EXCELLENCY_TP_CAT_CHANGE,
								IS_CAT_BENEFIT_OTHER, CAT_BENEFIT_OTHER});
						formObject.setValue(CAT_BENEFIT_OTHER, "");
					}
					manageCategoryChangeSection();
					if(!formObject.getValue(NEW_CUST_SEGMENT).toString().isEmpty()) {
						formObject.setStyle(IS_CAT_BENEFIT_OTHER, DISABLE, FALSE);		
						formObject.setValue(IS_CAT_BENEFIT_OTHER, FALSE);
						formObject.setStyle(CAT_BENEFIT_OTHER, DISABLE, TRUE);
						formObject.setValue(CAT_BENEFIT_OTHER, "");
					} else {
						formObject.clearCombo(PROMO_CODE_CAT_CHANGE);
						formObject.clearCombo(EXCELLENCY_CENTER_CC);
					}
					clearFBData();	//Family Banking
				} else if (IS_CAT_BENEFIT_OTHER.equalsIgnoreCase(controlName)){
					isCatBenefitOther();
				} else if (IS_SALARY_TRANSFER_CAT_CHANGE.equalsIgnoreCase(controlName)){
					if(formObject.getValue(IS_SALARY_TRANSFER_CAT_CHANGE).toString().equalsIgnoreCase(TRUE))
					{
						formObject.setValue(IS_CAT_BENEFIT_OTHER,FALSE);
						formObject.setValue(CAT_BENEFIT_OTHER,"");
						formObject.setStyle(CAT_BENEFIT_OTHER,ENABLE,FALSE);
					}
				} else if (IS_MORTAGAGE_CAT_CHANGE.equalsIgnoreCase(controlName)){
					if(formObject.getValue(IS_MORTAGAGE_CAT_CHANGE).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(IS_CAT_BENEFIT_OTHER,FALSE);
						formObject.setValue(CAT_BENEFIT_OTHER,"");
						formObject.setStyle(CAT_BENEFIT_OTHER,ENABLE,FALSE);
					}
				} else if (IS_INSURANCE_CAT_CHANGE.equalsIgnoreCase(controlName)){
					if(formObject.getValue(IS_INSURANCE_CAT_CHANGE).toString().equalsIgnoreCase(TRUE))
					{
						formObject.setValue(IS_CAT_BENEFIT_OTHER,FALSE);
						formObject.setValue(CAT_BENEFIT_OTHER,"");
						formObject.setStyle(CAT_BENEFIT_OTHER,ENABLE,FALSE);
					}
				} else if (IS_TRB_CAT_CHANGE.equalsIgnoreCase(controlName)){
					if(formObject.getValue(IS_TRB_CAT_CHANGE).toString().equalsIgnoreCase(TRUE))
					{
						formObject.setValue(IS_CAT_BENEFIT_OTHER,FALSE);
						formObject.setValue(CAT_BENEFIT_OTHER,"");
						formObject.setStyle(CAT_BENEFIT_OTHER,ENABLE,FALSE);
					}
				} else if (IS_OTHERS_CAT_CHANGE.equalsIgnoreCase(controlName)){
					if(formObject.getValue(IS_OTHERS_CAT_CHANGE).toString().equalsIgnoreCase(TRUE))
					{
						formObject.setValue(IS_CAT_BENEFIT_OTHER,FALSE);
						formObject.setValue(CAT_BENEFIT_OTHER,"");
						formObject.setStyle(CAT_BENEFIT_OTHER,ENABLE,FALSE);
					}	
				} else if (IS_VVIP.equalsIgnoreCase(controlName)){
					if(formObject.getValue(IS_VVIP).toString().equalsIgnoreCase(TRUE))
					{
						formObject.setValue(IS_CAT_BENEFIT_OTHER,FALSE);
						formObject.setValue(CAT_BENEFIT_OTHER,"");
						formObject.setStyle(CAT_BENEFIT_OTHER,ENABLE,FALSE);
					}
				} else if (IS_PREVILEGE_TP_CAT_CHANGE.equalsIgnoreCase(controlName)){
					if(formObject.getValue(IS_PREVILEGE_TP_CAT_CHANGE).toString().equalsIgnoreCase(TRUE))
					{
						formObject.setValue(IS_CAT_BENEFIT_OTHER,FALSE);
						formObject.setValue(CAT_BENEFIT_OTHER,"");
						formObject.setStyle(CAT_BENEFIT_OTHER,ENABLE,FALSE);
					}
				} else if (IS_CAT_BENEFIT_OTHER.equalsIgnoreCase(controlName)){
					if(formObject.getValue(IS_ENTERTAINMENT_CAT_CHANGE).toString().equalsIgnoreCase(TRUE))
					{
						formObject.setValue(IS_CAT_BENEFIT_OTHER,FALSE);
						formObject.setValue(CAT_BENEFIT_OTHER,"");
						formObject.setStyle(CAT_BENEFIT_OTHER,ENABLE,FALSE);
					}
				} else if (IS_SHOPPING_CAT_CHANGE.equalsIgnoreCase(controlName)){
					if(formObject.getValue(IS_SHOPPING_CAT_CHANGE).toString().equalsIgnoreCase(TRUE))
					{
						formObject.setValue(IS_CAT_BENEFIT_OTHER,FALSE);
						formObject.setValue(CAT_BENEFIT_OTHER,"");
						formObject.setStyle(CAT_BENEFIT_OTHER,ENABLE,FALSE);
					}
				} else if (IS_SPORT_CAT_CHANGE.equalsIgnoreCase(controlName)){
					if(formObject.getValue(IS_SPORT_CAT_CHANGE).toString().equalsIgnoreCase(TRUE))
					{
						formObject.setValue(IS_CAT_BENEFIT_OTHER,FALSE);
						formObject.setValue(CAT_BENEFIT_OTHER,"");
						formObject.setStyle(CAT_BENEFIT_OTHER,ENABLE,FALSE);
					}
				} else if (IS_TRAVEL_CAT_CHANGE.equalsIgnoreCase(controlName)){
					if(formObject.getValue(IS_TRAVEL_CAT_CHANGE).toString().equalsIgnoreCase(TRUE))
					{
						formObject.setValue(IS_CAT_BENEFIT_OTHER,FALSE);
						formObject.setValue(CAT_BENEFIT_OTHER,"");
						formObject.setStyle(CAT_BENEFIT_OTHER,ENABLE,FALSE);
					}
				} else if (IS_EXCELLENCY_TP_CAT_CHANGE.equalsIgnoreCase(controlName)){
					if(formObject.getValue(IS_EXCELLENCY_TP_CAT_CHANGE).toString().equalsIgnoreCase(TRUE))
					{
						formObject.setValue(IS_CAT_BENEFIT_OTHER,FALSE);
						formObject.setValue(CAT_BENEFIT_OTHER,"");
						formObject.setStyle(CAT_BENEFIT_OTHER,ENABLE,FALSE);
					}
				} else if (controlName.equalsIgnoreCase(ED_CB_SAL_AED)) {
					formObject.setValue(ED_SAL_AED,formObject.getValue(ED_ANNUAL_INC).toString());
					if(formObject.getValue(ED_CB_SAL_AED).toString().equalsIgnoreCase(TRUE)) {
						formObject.setStyle(ED_SAL_AED,DISABLE,FALSE);
					} else {
						formObject.setStyle(ED_SAL_AED,DISABLE,TRUE);
					}
				} else if(controlName.equalsIgnoreCase(CK_PER_DET)) { 
					if(formObject.getValue(CK_PER_DET).toString().equalsIgnoreCase(TRUE)) {
						int iProcessedCustomer = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());			
						String sBankRelation = formObject.getTableCellValue(ACC_RELATION, iProcessedCustomer, 7);
						Frame18_CPD_ENable();
						if(!formObject.getValue(CUST_PREFIX).toString().equalsIgnoreCase("Others")){
							formObject.setStyle(PD_OTHERS, DISABLE, TRUE);
						}
						if(!formObject.getValue(MARITAL_STATUS).toString().equalsIgnoreCase("Others")){
							formObject.setStyle(PD_MARITALSTATUSOTHER, DISABLE, TRUE);
						}
						enableControls(new String[]{CK_PER_DET, PD_SHORTNAME});
						if(!formObject.getValue(ACC_OWN_TYPE).toString().equalsIgnoreCase("Minor")) {
							formObject.setStyle(PD_DATEOFATTAININGMAT, DISABLE, TRUE);
						}
						if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only")) {
							formObject.setStyle(PD_CUSTSEGMENT, DISABLE, TRUE);
						}					
						if(sBankRelation.equalsIgnoreCase("Existing")) {
							formObject.setStyle(PD_CUSTSEGMENT, DISABLE, TRUE);
						}
						if(! returnVisaStatus().equalsIgnoreCase("Residency Visa")) {
							formObject.setStyle(DRP_RESEIDA, DISABLE, TRUE);
						} else {
							formObject.setStyle(DRP_RESEIDA, DISABLE, FALSE);
						}
					} else {
						Frame18_CPD_Disable();
						formObject.setStyle(CK_PER_DET, DISABLE, FALSE);
						formObject.setStyle(DRP_RESEIDA, DISABLE, TRUE);
					} 
				} else if(controlName.equalsIgnoreCase(CHK_CONTACT_DET)) { 
					if(formObject.getValue(CHK_CONTACT_DET).toString().equalsIgnoreCase(TRUE)) {
						Frame20_CPD_ENable();
					} else {
						Frame20_CPD_Disable();
					}
					manageCity(CP_CITY);
					manageCity(PA_CITY);
					manageCity(RA_CITY);
					formObject.setStyle(CHK_CONTACT_DET, DISABLE, FALSE);
				} else if(controlName.equalsIgnoreCase(CHK_PASSPORT_DET)) { 
					if(formObject.getValue(CHK_PASSPORT_DET).toString().equalsIgnoreCase(TRUE)) {
						Frame21_CPD_ENable();
					} else {
						Frame21_CPD_Disable();
					}
					formObject.setStyle(CHK_PASSPORT_DET, DISABLE, FALSE);
				} else if(controlName.equalsIgnoreCase(CHK_INTERNAL_SEC)) {
					if(formObject.getValue(CHK_INTERNAL_SEC).toString().equalsIgnoreCase(TRUE)) {
						Frame22_CPD_ENable();
						formObject.setStyle(PRO_CODE, DISABLE, FALSE);
						formObject.setStyle(IDS_PROF_CENTER_CODE, DISABLE, TRUE);
					} else {
						Frame22_CPD_Disable();
					}
					formObject.setStyle(CHK_INTERNAL_SEC, DISABLE, FALSE);
				} else if(controlName.equalsIgnoreCase(CHK_GEN_INFO)) { 
					if(formObject.getValue(CHK_GEN_INFO).toString().equalsIgnoreCase(TRUE)) {
						Frame28_CPD_ENable();
					} else {
						Frame28_CPD_Disable();
					} 
					formObject.setStyle(GI_EXST_SINCE, DISABLE, TRUE);
					formObject.setStyle(CHK_GEN_INFO, DISABLE, FALSE);
				} else if(controlName.equalsIgnoreCase(CHK_EMP_DETAIL)) { 
					if(formObject.getValue(CHK_EMP_DETAIL).toString().equalsIgnoreCase(TRUE)) {
						Frame27_CPD_ENable();
					} else {
						Frame27_CPD_Disable();
					}
					formObject.setStyle(CHK_EMP_DETAIL, DISABLE, FALSE);
				} else if(controlName.equalsIgnoreCase(CHK_FUNDING_INFO)) { 
					if(formObject.getValue(CHK_FUNDING_INFO).toString().equalsIgnoreCase(TRUE)) {
						Frame30_CPD_ENable();
					} else {
						Frame30_CPD_Disable();
					}
					formObject.setStyle(CHK_FUNDING_INFO, DISABLE, FALSE);
				} else if(controlName.equalsIgnoreCase(CHK_RISK_ASS)) { 
					if(formObject.getValue(CHK_RISK_ASS).toString().equalsIgnoreCase(TRUE)) {
						riskAssessmentSectionEnable();
					} else {
						Frame25_CPD_Disable();
					} 
					formObject.setStyle(CHK_RISK_ASS, DISABLE, FALSE);
				} else if(controlName.equalsIgnoreCase(CHECKBOX_FATCA)) { 
					if(formObject.getValue(CHECKBOX_FATCA).toString().equalsIgnoreCase(TRUE)) {
						enableFATCACPD();
					} else {
						frameFatcaCpdDisable();
					}
					formObject.setStyle(CHECKBOX_FATCA, DISABLE, FALSE);
				} else if(controlName.equalsIgnoreCase(CHK_EDD)) { 
					if(formObject.getValue(CHK_EDD).toString().equalsIgnoreCase(TRUE)) {
						Frame31_CPD_ENable();
					} else {
						Frame31_CPD_Disable();
					}
					formObject.setStyle(CHK_EDD, DISABLE, FALSE);
				} else if(controlName.equalsIgnoreCase(CHK_BANKING_RELATION)) { 
					if(formObject.getValue(CHK_BANKING_RELATION).toString().equalsIgnoreCase(TRUE)) {
						Frame32_CPD_ENable();
					} else {
						Frame32_CPD_Disable();
					}
					formObject.setStyle(CHK_BANKING_RELATION, DISABLE, FALSE);
				} else if(controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_TYPE_MANUAL) || controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_TYPE_FCR) 
						|| controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_TYPE_EIDA)) {
					if(controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_TYPE_MANUAL)) {
						toggleCheckbox(controlName,CHECKBOX_PASSPORT_TYPE_FCR,CHECKBOX_PASSPORT_TYPE_EIDA);
					} else if(controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_TYPE_EIDA)) {
						toggleCheckbox(controlName,CHECKBOX_PASSPORT_TYPE_FCR,CHECKBOX_PASSPORT_TYPE_MANUAL);
					} else if(controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_TYPE_FCR)) {
						toggleCheckbox(controlName,CHECKBOX_PASSPORT_TYPE_MANUAL,CHECKBOX_PASSPORT_TYPE_EIDA);
					}
					manageManualFields(CHECKBOX_PASSPORT_TYPE_MANUAL,MANUAL_PASSTYPE);
				} else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_STATUS_FCR) || controlName.equalsIgnoreCase(CHECKBOX_VISA_STATUS_EIDA) 
						|| controlName.equalsIgnoreCase(CHECKBOX_VISA_STATUS_MANUAL)) {
					if(controlName.equalsIgnoreCase(CHECKBOX_VISA_STATUS_FCR)) {
						toggleCheckbox(controlName,CHECKBOX_VISA_STATUS_EIDA,CHECKBOX_VISA_STATUS_MANUAL);
					} else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_STATUS_MANUAL)) {
						toggleCheckbox(controlName,CHECKBOX_VISA_STATUS_EIDA,CHECKBOX_VISA_STATUS_FCR);
					} else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_STATUS_EIDA)) {
						toggleCheckbox(controlName,CHECKBOX_VISA_STATUS_FCR,CHECKBOX_VISA_STATUS_MANUAL);
					}
					manageManualFields(CHECKBOX_VISA_STATUS_MANUAL,MANUAL_VISASTATUS);
					if( returnVisaStatus().equalsIgnoreCase("Residency Visa")) {
						formObject.setStyle(DRP_RESEIDA, DISABLE, FALSE);
					} else {
						formObject.setStyle(DRP_RESEIDA, DISABLE, TRUE);
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_COB_FCR) || controlName.equalsIgnoreCase(CHECKBOX_COB_EIDA) 
						|| controlName.equalsIgnoreCase(CHECKBOX_COB_MANUAL)) {
					if(controlName.equalsIgnoreCase(CHECKBOX_COB_FCR)) {
						toggleCheckbox(controlName,CHECKBOX_COB_EIDA,CHECKBOX_COB_MANUAL);
					} else if(controlName.equalsIgnoreCase(CHECKBOX_COB_EIDA)) {
						toggleCheckbox(controlName,CHECKBOX_COB_FCR,CHECKBOX_COB_MANUAL);
					} else if(controlName.equalsIgnoreCase(CHECKBOX_COB_MANUAL)) {
						toggleCheckbox(controlName,CHECKBOX_COB_FCR,CHECKBOX_COB_EIDA);
					}
					manageManualFields(CHECKBOX_COB_MANUAL,MANUAL_COUNTRYBIRTH);
				} else if(controlName.equalsIgnoreCase(CHECKBOX_SELECTALL_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_SELECTALL_EIDA ,CHECKBOX_SELECTALL_MANUAL);
					manageFCRCheckBoxes();
					manageEIDACheckBoxes();
					manageManualCheckBoxes();
					setManualFieldsEnable();
					setManualFieldsLock();
					if(formObject.getValue(CHECKBOX_SELECTALL_FCR).toString().equalsIgnoreCase(TRUE)) {
						setFCRDataInBelowFields();
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_PREFIX_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_PREFIX_EIDA,CHECKBOX_PREFIX_MANUAL);
					manageManualFields(CHECKBOX_PREFIX_MANUAL,MANUAL_PREFIX);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(CUST_PREFIX, formObject.getValue(FCR_PREFIX).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_FULLNAME_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_FULLNAME_EIDA,CHECKBOX_FULLNAME_MANUAL);
					manageManualFields(CHECKBOX_FULLNAME_MANUAL,MANUAL_NAME);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(PD_FULLNAME, formObject.getValue(FCR_NAME).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_SHORTNAME_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_SHORTNAME_EIDA,CHECKBOX_SHORTNAME_MANUAL );
					manageManualFields(CHECKBOX_SHORTNAME_MANUAL ,MANUAL_SHORTNAME);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(PD_SHORTNAME, formObject.getValue(FCR_SHORTNAME).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_FIRSTNAME_FCR))	{
					toggleCheckbox(controlName,CHECKBOX_FIRSTNAME_EIDA,CHECKBOX_FIRSTNAME_MANUAL);
					manageManualFields(CHECKBOX_FIRSTNAME_MANUAL,MANUAL_FIRSTNAME);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(CRS_FIRSTNAME,formObject.getValue(FCR_FIRSTNAME).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_FIRSTNAME_EIDA))	{
					toggleCheckbox(controlName,CHECKBOX_FIRSTNAME_FCR,CHECKBOX_FIRSTNAME_MANUAL);
					manageManualFields(CHECKBOX_FIRSTNAME_MANUAL,MANUAL_FIRSTNAME);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(CRS_FIRSTNAME,formObject.getValue(EIDA_FIRSTNAME).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_FIRSTNAME_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_FIRSTNAME_FCR,CHECKBOX_FIRSTNAME_EIDA);
					manageManualFields(CHECKBOX_FIRSTNAME_MANUAL,MANUAL_FIRSTNAME);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(CRS_FIRSTNAME,formObject.getValue(MANUAL_FIRSTNAME).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_LASTNAME_FCR)) {
					logInfo("CHANGE EVENT- CHECKBOX_LASTNAME_FCR", "value: "
							+formObject.getValue(CHECKBOX_LASTNAME_FCR).toString());
					toggleCheckbox(controlName,CHECKBOX_LASTNAME_EIDA,CHECKBOX_LASTNAME_MANUAL);
					manageManualFields(CHECKBOX_LASTNAME_MANUAL,MANUAL_LASTNAME);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(CRS_LASTNAME,formObject.getValue(FCR_LASTNAME).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_LASTNAME_EIDA))	{
					toggleCheckbox(controlName,CHECKBOX_LASTNAME_FCR,CHECKBOX_LASTNAME_MANUAL);
					manageManualFields(CHECKBOX_LASTNAME_MANUAL,MANUAL_LASTNAME);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(CRS_LASTNAME,formObject.getValue(EIDA_LASTNAME).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_LASTNAME_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_LASTNAME_FCR,CHECKBOX_LASTNAME_EIDA);
					manageManualFields(CHECKBOX_LASTNAME_MANUAL,MANUAL_LASTNAME);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(CRS_LASTNAME,formObject.getValue(MANUAL_LASTNAME).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_DOB_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_DOB_EIDA,CHECKBOX_DOB_MANUAL);
					manageManualFields(CHECKBOX_DOB_MANUAL,MANUAL_DOB);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(PD_DOB,formObject.getValue(FCR_DOB).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_NO_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_PASSPORT_NO_EIDA,CHECKBOX_PASSPORT_NO_MANUAL);
					manageManualFields(CHECKBOX_PASSPORT_NO_MANUAL,MANUAL_PASSPORTNO);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(HD_PASSPORT_NO,formObject.getValue(FCR_PASSPORTNO).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_PASS_ISS_DT_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_PASS_ISS_DT_EIDA,CHECKBOX_PASS_ISS_DT_MANUAL);
					manageManualFields(CHECKBOX_PASS_ISS_DT_MANUAL,MANUAL_PASSPORTISSDATE);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(HD_PASS_ISS_DATE,formObject.getValue(FCR_PASSPORTISSDATE).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_PASS_EXP_DT_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_PASS_EXP_DT_EIDA,CHECKBOX_PASS_EXP_DT_MANUAL);
					manageManualFields(CHECKBOX_PASS_EXP_DT_MANUAL,MANUAL_PASSPORTEXPDATE);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(HD_PASS_EXP_DATE,formObject.getValue(FCR_PASSPORTEXPDATE).toString());
					}
				} else if(controlName.equalsIgnoreCase("CHECK17")) {
					toggleCheckbox(controlName,"CHECK62","CHECK39");
					manageManualFields("CHECK39",PASSPORTEXPPLACE_MANUAL);
				} else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_NO_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_VISA_NO_EIDA,CHECKBOX_VISA_NO_MANUAL);
					manageManualFields(CHECKBOX_VISA_NO_MANUAL,MANUAL_VISANO);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(HD_VISA_NO,formObject.getValue(FCR_VISANO).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_ISSUE_DATE_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_VISA_ISSUE_DATE_EIDA,CHECKBOX_VISA_ISSUE_DATE_MANUAL);
					manageManualFields(CHECKBOX_VISA_ISSUE_DATE_MANUAL,MANUAL_VISAISSDATE);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(HD_VISA_ISSUE_DATE,formObject.getValue(FCR_VISAISSDATE).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_EXPIRY_DATE_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_VISA_EXPIRY_DATE_EIDA,CHECKBOX_VISA_EXPIRY_DATE_MANUAL);
					manageManualFields(CHECKBOX_VISA_EXPIRY_DATE_MANUAL,MANUAL_VISAEXPDATE);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(HD_EXP_DATE,formObject.getValue(FCR_VISAEXPDATE).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_NATIONALITY_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_NATIONALITY_EIDA,CHECKBOX_NATIONALITY_MANUAL);
					manageManualFields(CHECKBOX_NATIONALITY_MANUAL,MANUAL_NATIONALITY);
					autoSetFatca(controlName);
					manageResidencyAndVisaStatus(RA_IS_UAE_RESIDENT,"COMBO34");
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(CUST_NATIONALITY,formObject.getValue(FCR_NATIONALITY).toString());
					}
					if("UNITED ARAB EMIRATES".equalsIgnoreCase(formObject.getValue(CUST_NATIONALITY).toString())) {
						enableControls(new String[]{COR_MAKANI, PERM_MAKANI, RES_MAKANI});
					} else {
						disableControls(new String[]{COR_MAKANI, PERM_MAKANI, RES_MAKANI});
					} 
				} else if(controlName.equalsIgnoreCase(CHECKBOX_MOTHERSNAME_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_MOTHERSNAME_EIDA,CHECKBOX_MOTHERSNAME_MANUAL );
					manageManualFields(CHECKBOX_MOTHERSNAME_MANUAL ,MANUAL_MOTHERNAME);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(PD_MOTHERMAIDENNAME,formObject.getValue(FCR_MOTHERSNAME).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_EIDANO_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_EIDANO_EIDA,CHECKBOX_EIDANO_MANUAL);
					manageManualFields(CHECKBOX_EIDANO_MANUAL,MANUAL_EIDANO);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(PD_EIDANO,formObject.getValue(FCR_EIDANO).toString());
						setEIDA();
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_CORR_POB_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_CORR_POB_EIDA,CHECKBOX_CORR_POB_MANUAL);
					manageManualFields(CHECKBOX_CORR_POB_MANUAL,MANUAL_ADDRESS);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(CP_POBOXNO,formObject.getValue(FCR_ADDRESS).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_CITY_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_CITY_EIDA,CHECKBOX_CITY_MANUAL);
					manageManualFields(CHECKBOX_CITY_MANUAL,MANUAL_CITY);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(CP_CITY,formObject.getValue(FCR_CITY).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_STATE_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_STATE_EIDA,CHECKBOX_STATE_MANUAL);
					manageManualFields(CHECKBOX_STATE_MANUAL,MANUAL_STATE);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(CORR_STATE,formObject.getValue(FCR_STATE).toString());
						if(formObject.getValue(FCR_STATE).toString().equalsIgnoreCase(OTHERS)) {
							formObject.setStyle(CP_OTHERS, DISABLE, FALSE);
						}
					} 
				} else if(controlName.equalsIgnoreCase(CHECKBOX_CNTRY_OF_CORR_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_CNTRY_OF_CORR_EID,CHECKBOX_CNTRY_OF_CORR_MANUAL );
					manageManualFields(CHECKBOX_CNTRY_OF_CORR_MANUAL ,MANUAL_CNTRY);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(CORR_CNTRY,formObject.getValue(FCR_CNTRY).toString());
						if(formObject.getValue(FCR_CNTRY).toString().equalsIgnoreCase("UNITED ARAB EMIRATES")) {
							formObject.setStyle(COR_MAKANI, DISABLE, FALSE);
						} else {
							formObject.setStyle(COR_MAKANI, DISABLE, TRUE);
						}
					} 
				} else if(controlName.equalsIgnoreCase(CHECKBOX_COUNTRY_PER_RES_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_COUNTRY_PER_RES_EIDA,CHECKBOX_COUNTRY_PER_RES_MANUAL);
					manageManualFields(CHECKBOX_COUNTRY_PER_RES_MANUAL,MANUAL_PER_CNTRY);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(RES_CNTRY, formObject.getValue(FCR_PER_CNTRY).toString());
						if(formObject.getValue(FCR_PER_CNTRY).toString().equalsIgnoreCase("UNITED ARAB EMIRATES")) {
							formObject.setStyle(RES_MAKANI, DISABLE, FALSE);
						} else {
							formObject.setStyle(RES_MAKANI, DISABLE, TRUE);
						}
					} 
				} else if(controlName.equalsIgnoreCase(CHECKBOX_TELE_RES_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_TELE_RES_EIDA,CHECKBOX_TELE_RES_MANUAL);
					manageManualFields(CHECKBOX_TELE_RES_MANUAL,MANUAL_PH);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(CP_PHONENO,formObject.getValue(FCR_PH).toString());
					} 
				} else if(controlName.equalsIgnoreCase(CHECKBOX_TELE_MOB_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_TELE_MOB_EIDA,CHECKBOX_TELE_MOB_MANUAL);
					manageManualFields(CHECKBOX_TELE_MOB_MANUAL,MANUAL_MOBILE);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(CP_MOBILE,formObject.getValue(FCR_MOBILE).toString());
					} 
				} else if(controlName.equalsIgnoreCase(CHECKBOX_EMAIL_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_EMAIL_EIDA,CHECKBOX_EMAIL_MANUAL);
					manageManualFields(CHECKBOX_EMAIL_MANUAL,MANUAL_EMAIL);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(CP_EMAIL,formObject.getValue(FCR_EMAIL).toString());
					} 
				} else if(controlName.equalsIgnoreCase(CHECKBOX_PROFESSION_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_PROFESSION_EIDA,CHECKBOX_PROFESSION_MANUAL);
					manageManualFields(CHECKBOX_PROFESSION_MANUAL,MANUAL_PROFESSION,BTNPROFESSION);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(PROF_CODE,formObject.getValue(FCR_PROFESSION).toString());
						formObject.setValue(PROFESION,formObject.getValue(FCR_PROFESSION).toString());
					} 
				} else if(controlName.equalsIgnoreCase(CHECKBOX_GENDER_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_GENDER_EIDA,CHECKBOX_GENDER_MANUAL);
					manageManualFields(CHECKBOX_GENDER_MANUAL,MANUAL_GENDER);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(CUST_GENDER,formObject.getValue(FCR_GENDER).toString());
					} 
				} else if(controlName.equalsIgnoreCase(CHECKBOX_EMP_NAME_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_EMP_NAME_EIDA,CHECKBOX_EMP_NAME_MANUAL);
					manageManualFields(CHECKBOX_EMP_NAME_MANUAL,MANUAL_EMPLYR_NAME,BTNEMLOYERNAME);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(EMPNAME,formObject.getValue(FCR_EMPLYR_NAME).toString());
						List<List<String>> result = formObject.getDataFromDB("SELECT CD_STATUS FROM "
								+ "USR_0_EMPLOYER_MASTER WHERE EMP_NAME ='"+formObject.getValue(FCR_EMPLYR_NAME)+"'");
						formObject.setValue(ED_CB_TML,FALSE);
						formObject.setValue(ED_CB_NON_TML,FALSE);
						if(result.get(0).get(0).equalsIgnoreCase("1") || result.get(0).get(0).equalsIgnoreCase("2")) {
							formObject.setValue(ED_CB_TML,TRUE);
						} else {
							formObject.setValue(ED_CB_NON_TML,TRUE);
						}
					} 
				} else if(controlName.equalsIgnoreCase(CHECKFCR)) {
					toggleCheckbox(controlName,CHECKEIDA,CHECKMANUAL);
					manageManualFields(CHECKMANUAL,MANUAL_RESIDENT);
					manageResidencyAndVisaStatus(RA_IS_UAE_RESIDENT,"COMBO34");
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(RES_CNTRY,formObject.getValue(FCR_RESIDENT).toString());
						if(formObject.getValue(FCR_RESIDENT).toString().equalsIgnoreCase("UNITED ARAB EMIRATES")) {
							formObject.setStyle(CONTACT_DETAILS_MAKANI_NO, DISABLE, FALSE);
						} else {
							formObject.setStyle(CONTACT_DETAILS_MAKANI_NO, DISABLE, TRUE);
						}
					} 
				} else if(controlName.equalsIgnoreCase(CHECKBOX_SELECTALL_EIDA )) {
					toggleCheckbox(controlName,CHECKBOX_SELECTALL_FCR,CHECKBOX_SELECTALL_MANUAL);
					manageFCRCheckBoxes();
					manageEIDACheckBoxes();
					manageManualCheckBoxes();
					setManualFieldsEnable();
					setManualFieldsLock();
					if(formObject.getValue(CHECKBOX_SELECTALL_EIDA ).toString().equalsIgnoreCase(TRUE)) {
						setEIDADataInBelowFields();
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_PREFIX_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_PREFIX_FCR,CHECKBOX_PREFIX_MANUAL);
					manageManualFields(CHECKBOX_PREFIX_MANUAL,MANUAL_PREFIX);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(CUST_PREFIX,formObject.getValue(EIDA_PREFIX).toString());
					} 
				} else if(controlName.equalsIgnoreCase(CHECKBOX_FULLNAME_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_FULLNAME_FCR,CHECKBOX_FULLNAME_MANUAL);
					manageManualFields(CHECKBOX_FULLNAME_MANUAL,MANUAL_NAME);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(PD_FULLNAME,formObject.getValue(EIDA_NAME).toString());
					} 
				} else if(controlName.equalsIgnoreCase(CHECKBOX_SHORTNAME_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_SHORTNAME_FCR,CHECKBOX_SHORTNAME_MANUAL );
					manageManualFields(CHECKBOX_SHORTNAME_MANUAL ,MANUAL_SHORTNAME);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE))
					{
						formObject.setValue(PD_SHORTNAME,formObject.getValue(EIDA_SHORTNAME).toString());
					} 
				} else if(controlName.equalsIgnoreCase(CHECKBOX_SHORTNAME_MANUAL )) {
					toggleCheckbox(controlName,CHECKBOX_SHORTNAME_FCR,CHECKBOX_SHORTNAME_EIDA);
					manageManualFields(CHECKBOX_SHORTNAME_MANUAL ,MANUAL_SHORTNAME);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(PD_SHORTNAME,formObject.getValue(MANUAL_SHORTNAME).toString());
					} 
				} else if(controlName.equalsIgnoreCase(CHECKBOX_DOB_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_DOB_FCR,CHECKBOX_DOB_MANUAL);
					manageManualFields(CHECKBOX_DOB_MANUAL,MANUAL_DOB);
				} else if(controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_NO_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_PASSPORT_NO_FCR,CHECKBOX_PASSPORT_NO_MANUAL);
					manageManualFields(CHECKBOX_PASSPORT_NO_MANUAL,MANUAL_PASSPORTNO);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(HD_PASSPORT_NO,formObject.getValue(EIDA_PASSPORTNO).toString());
					} 
				} else if(controlName.equalsIgnoreCase(CHECKBOX_PASS_ISS_DT_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_PASS_ISS_DT_FCR,CHECKBOX_PASS_ISS_DT_MANUAL);	
					manageManualFields(CHECKBOX_PASS_ISS_DT_MANUAL,MANUAL_PASSPORTISSDATE);	
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(HD_PASS_ISS_DATE,formObject.getValue(EIDA_PASSPORTISSDATE).toString());
					} 
				} else if(controlName.equalsIgnoreCase(CHECKBOX_PASS_EXP_DT_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_PASS_EXP_DT_FCR,CHECKBOX_PASS_EXP_DT_MANUAL);
					manageManualFields(CHECKBOX_PASS_EXP_DT_MANUAL,MANUAL_PASSPORTEXPDATE);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(HD_PASS_EXP_DATE,formObject.getValue(EIDA_PASSPORTEXPDATE).toString());
					} 
				} else if(controlName.equalsIgnoreCase("CHECK62")) {
					toggleCheckbox(controlName,"CHECK17","CHECK39");
					manageManualFields("CHECK39",PASSPORTEXPPLACE_MANUAL);
				} else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_NO_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_VISA_NO_FCR,CHECKBOX_VISA_NO_MANUAL);
					manageManualFields(CHECKBOX_VISA_NO_MANUAL,MANUAL_VISANO);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(HD_VISA_NO,formObject.getValue(EIDA_VISANO).toString());
					} 
				} else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_ISSUE_DATE_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_VISA_ISSUE_DATE_FCR,CHECKBOX_VISA_ISSUE_DATE_MANUAL);
					manageManualFields(CHECKBOX_VISA_ISSUE_DATE_MANUAL,MANUAL_VISAISSDATE);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(HD_VISA_ISSUE_DATE,formObject.getValue(EIDA_VISAISSDATE).toString());
					} 
				} else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_EXPIRY_DATE_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_VISA_EXPIRY_DATE_FCR,CHECKBOX_VISA_EXPIRY_DATE_MANUAL);
					manageManualFields(CHECKBOX_VISA_EXPIRY_DATE_MANUAL,MANUAL_VISAEXPDATE);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(HD_EXP_DATE,formObject.getValue(EIDA_VISAEXPDATE).toString());
					} 
				} else if(controlName.equalsIgnoreCase(CHECKBOX_NATIONALITY_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_NATIONALITY_FCR,CHECKBOX_NATIONALITY_MANUAL);
					manageManualFields(CHECKBOX_NATIONALITY_MANUAL,MANUAL_NATIONALITY);
					autoSetFatca(controlName);
					manageResidencyAndVisaStatus(RA_IS_UAE_RESIDENT,"COMBO34");
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(CUST_NATIONALITY,formObject.getValue(EIDA_NATIONALITY).toString());
					}
					if("UNITED ARAB EMIRATES".equalsIgnoreCase(formObject.getValue(CUST_NATIONALITY).toString())) {
						enableControls(new String[]{COR_MAKANI, PERM_MAKANI, RES_MAKANI});
					} else {
						disableControls(new String[]{COR_MAKANI, PERM_MAKANI, RES_MAKANI});
					} 
				} else if(controlName.equalsIgnoreCase(CHECKBOX_MOTHERSNAME_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_MOTHERSNAME_FCR,CHECKBOX_MOTHERSNAME_MANUAL );
					manageManualFields(CHECKBOX_MOTHERSNAME_MANUAL ,MANUAL_MOTHERNAME);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(PD_MOTHERMAIDENNAME,formObject.getValue(EIDA_MOTHERNAME).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_EIDANO_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_EIDANO_FCR,CHECKBOX_EIDANO_MANUAL);
					manageManualFields(CHECKBOX_EIDANO_MANUAL,MANUAL_EIDANO);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(PD_EIDANO,formObject.getValue(EIDA_EIDANO).toString());
						setEIDA();
					} 
				} else if(controlName.equalsIgnoreCase(CHECKBOX_CORR_POB_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_CORR_POB_FCR,CHECKBOX_CORR_POB_MANUAL);
					manageManualFields(CHECKBOX_CORR_POB_MANUAL,MANUAL_ADDRESS);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(CP_POBOXNO,formObject.getValue(EIDA_ADDRESS).toString());
					} 
				} else if(controlName.equalsIgnoreCase(CHECKBOX_CITY_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_CITY_FCR,CHECKBOX_CITY_MANUAL);
					manageManualFields(CHECKBOX_CITY_MANUAL,MANUAL_CITY);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(CP_CITY,formObject.getValue(EIDA_CITY).toString());
					} 
				} else if(controlName.equalsIgnoreCase(CHECKBOX_STATE_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_STATE_FCR,CHECKBOX_STATE_MANUAL);
					manageManualFields(CHECKBOX_STATE_MANUAL,MANUAL_STATE);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(CORR_STATE,formObject.getValue(EIDA_STATE).toString());
						if(formObject.getValue(EIDA_STATE).toString().equalsIgnoreCase(OTHERS)) {
							formObject.setStyle(CP_OTHERS, DISABLE, FALSE);
						}
					} 
				} else if(controlName.equalsIgnoreCase(CHECKBOX_CNTRY_OF_CORR_EID)) {
					toggleCheckbox(controlName,CHECKBOX_CNTRY_OF_CORR_FCR,CHECKBOX_CNTRY_OF_CORR_MANUAL );
					manageManualFields(CHECKBOX_CNTRY_OF_CORR_MANUAL ,MANUAL_CNTRY);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(CORR_CNTRY,formObject.getValue(EIDA_CNTRY).toString());
						if(formObject.getValue(EIDA_CNTRY).toString().equalsIgnoreCase("UNITED ARAB EMIRATES"))
							formObject.setStyle(COR_MAKANI, DISABLE, FALSE);
						else
							formObject.setStyle(COR_MAKANI, DISABLE, TRUE);
					} 
				} else if(controlName.equalsIgnoreCase(CHECKBOX_COUNTRY_PER_RES_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_COUNTRY_PER_RES_FCR,CHECKBOX_COUNTRY_PER_RES_MANUAL);
					manageManualFields(CHECKBOX_COUNTRY_PER_RES_MANUAL,MANUAL_PER_CNTRY);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(RES_CNTRY, formObject.getValue(EIDA_PER_CNTRY).toString()); 
						if(formObject.getValue(EIDA_PER_CNTRY).toString().equalsIgnoreCase("UNITED ARAB EMIRATES"))
							formObject.setStyle(RES_MAKANI, DISABLE, FALSE);
						else
							formObject.setStyle(RES_MAKANI, DISABLE, TRUE);
					} 
				} else if(controlName.equalsIgnoreCase(CHECKBOX_TELE_RES_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_TELE_RES_FCR,CHECKBOX_TELE_RES_MANUAL);
					manageManualFields(CHECKBOX_TELE_RES_MANUAL,MANUAL_PH);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(CP_PHONENO,formObject.getValue(EIDA_PH).toString());
					} 
				} else if(controlName.equalsIgnoreCase(CHECKBOX_TELE_MOB_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_TELE_MOB_FCR,CHECKBOX_TELE_MOB_MANUAL);
					manageManualFields(CHECKBOX_TELE_MOB_MANUAL,MANUAL_MOBILE);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(CP_MOBILE,formObject.getValue(EIDA_MOBILE).toString());
					} 
				} else if(controlName.equalsIgnoreCase(CHECKBOX_EMAIL_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_EMAIL_FCR,CHECKBOX_EMAIL_MANUAL);
					manageManualFields(CHECKBOX_EMAIL_MANUAL,MANUAL_EMAIL);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(CP_EMAIL,formObject.getValue(EIDA_EMAIL).toString());
					} 
				} else if(controlName.equalsIgnoreCase(CHECKBOX_PROFESSION_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_PROFESSION_FCR,CHECKBOX_PROFESSION_MANUAL);
					manageManualFields(CHECKBOX_PROFESSION_MANUAL,MANUAL_PROFESSION,BTNPROFESSION);

					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE))
					{
						formObject.setValue(PROF_CODE,formObject.getValue(EIDA_PROFESSION).toString());
						formObject.setValue(PROFESION,formObject.getValue(EIDA_PROFESSION).toString());
					} 
				} else if(controlName.equalsIgnoreCase(CHECKBOX_GENDER_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_GENDER_FCR,CHECKBOX_GENDER_MANUAL);
					manageManualFields(CHECKBOX_GENDER_MANUAL,MANUAL_GENDER);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(CUST_GENDER,formObject.getValue(EIDA_GENDER).toString());
					} 
				} else if(controlName.equalsIgnoreCase(CHECKBOX_EMP_NAME_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_EMP_NAME_FCR,CHECKBOX_EMP_NAME_MANUAL);
					manageManualFields(CHECKBOX_EMP_NAME_MANUAL,MANUAL_EMPLYR_NAME,BTNEMLOYERNAME);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(EMPNAME,formObject.getValue(EIDA_EMPLYR_NAME).toString());
					} 
					List<List<String>> result = formObject.getDataFromDB("SELECT CD_STATUS FROM USR_0_EMPLOYER_MASTER"
							+ " WHERE EMP_NAME ='"+formObject.getValue(EIDA_EMPLYR_NAME)+"'");
					formObject.setValue(ED_CB_TML,FALSE);
					formObject.setValue(ED_CB_NON_TML,FALSE);
					if(result.get(0).get(0).equalsIgnoreCase("1") || result.get(0).get(0).equalsIgnoreCase("2")) {
						formObject.setValue(ED_CB_TML,TRUE);
					} else {
						formObject.setValue(ED_CB_NON_TML,TRUE);
					} 
				} else if(controlName.equalsIgnoreCase(CHECKEIDA)) {
					toggleCheckbox(controlName,CHECKFCR,CHECKMANUAL);
					manageManualFields(CHECKMANUAL,MANUAL_RESIDENT);
					manageResidencyAndVisaStatus(RA_IS_UAE_RESIDENT,"COMBO34");
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(RES_CNTRY,formObject.getValue(EIDA_RESIDENT).toString());
						if(formObject.getValue(EIDA_RESIDENT).toString().equalsIgnoreCase("UNITED ARAB EMIRATES"))
							formObject.setStyle(CONTACT_DETAILS_MAKANI_NO, DISABLE, FALSE);
						else
							formObject.setStyle(CONTACT_DETAILS_MAKANI_NO, DISABLE, TRUE);
					} 
				} else if(controlName.equalsIgnoreCase(CHECKBOX_SELECTALL_MANUAL)) {
					logInfo("CHANGE EVENT","CHECKBOX_SELECTALL_MANUAL");
					toggleCheckbox(controlName,CHECKBOX_SELECTALL_FCR,CHECKBOX_SELECTALL_EIDA );
					logInfo("CHANGE EVENT","toggleCheckbox");
					manageFCRCheckBoxes();
					logInfo("CHANGE EVENT","manageFCRCheckBoxes");
					manageEIDACheckBoxes();
					logInfo("CHANGE EVENT","manageEIDACheckBoxes");
					setManualFieldsEnable();		
					logInfo("CHANGE EVENT","setManualFieldsEnable");
					manageManualCheckBoxes();
					logInfo("CHANGE EVENT","manageManualCheckBoxes");
					logInfo("CHANGE EVENT","VALUE : "+formObject.getValue(CHECKBOX_SELECTALL_MANUAL).toString());
					if(formObject.getValue(CHECKBOX_SELECTALL_MANUAL).toString().equalsIgnoreCase(TRUE)) {
						logInfo("CHANGE EVENT","setManualDataInBelowFields");
						setManualDataInBelowFields();
					} 
				} else if(controlName.equalsIgnoreCase(CHECKBOX_PREFIX_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_PREFIX_FCR,CHECKBOX_PREFIX_EIDA);
					manageManualFields(CHECKBOX_PREFIX_MANUAL,MANUAL_PREFIX);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(CUST_PREFIX,formObject.getValue(MANUAL_PREFIX).toString());
					} 
				} else if(controlName.equalsIgnoreCase(CHECKBOX_FULLNAME_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_FULLNAME_FCR,CHECKBOX_FULLNAME_EIDA);
					manageManualFields(CHECKBOX_FULLNAME_MANUAL,MANUAL_NAME);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(PD_FULLNAME,formObject.getValue(MANUAL_NAME).toString());
					} 
				} else if(controlName.equalsIgnoreCase(CHECKBOX_DOB_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_DOB_FCR,CHECKBOX_DOB_EIDA);
					manageManualFields(CHECKBOX_DOB_MANUAL,MANUAL_DOB);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(PD_DOB,formObject.getValue(MANUAL_DOB).toString());
					}
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(PD_DOB,formObject.getValue(MANUAL_DOB).toString());
					} 
				} else if(controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_NO_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_PASSPORT_NO_FCR,CHECKBOX_PASSPORT_NO_EIDA);
					manageManualFields(CHECKBOX_PASSPORT_NO_MANUAL,MANUAL_PASSPORTNO);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(HD_PASSPORT_NO,formObject.getValue(MANUAL_PASSPORTNO).toString());
					} 
				} else if(controlName.equalsIgnoreCase(CHECKBOX_PASS_ISS_DT_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_PASS_ISS_DT_FCR,CHECKBOX_PASS_ISS_DT_EIDA);	
					manageManualFields(CHECKBOX_PASS_ISS_DT_MANUAL,MANUAL_PASSPORTISSDATE);
				} else if(controlName.equalsIgnoreCase(CHECKBOX_PASS_EXP_DT_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_PASS_EXP_DT_FCR,CHECKBOX_PASS_EXP_DT_EIDA);
					manageManualFields(CHECKBOX_PASS_EXP_DT_MANUAL,MANUAL_PASSPORTEXPDATE);
				} else if(controlName.equalsIgnoreCase("CHECK39")) { // not found
					toggleCheckbox(controlName,"CHECK17","CHECK62");
					manageManualFields("CHECK39",PASSPORTEXPPLACE_MANUAL);
				} else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_NO_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_VISA_NO_FCR,CHECKBOX_VISA_NO_EIDA);
					manageManualFields(CHECKBOX_VISA_NO_MANUAL,MANUAL_VISANO);
					if(formObject.getValue(CHECKBOX_VISA_NO_MANUAL).toString().equalsIgnoreCase(TRUE) 
							&& formObject.getValue(MANUAL_VISANO).toString().equalsIgnoreCase("MDSA")) {
						formObject.setStyle(MANUAL_VISANO, DISABLE, TRUE);
					}
					if(formObject.getValue(CHECKBOX_VISA_NO_MANUAL).toString().equalsIgnoreCase(TRUE) 
							&& formObject.getValue(MANUAL_VISANO).toString().equalsIgnoreCase("")) {
						formObject.setStyle(MANUAL_VISANO, DISABLE, FALSE);
					}
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
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
					manageResidencyAndVisaStatus(RA_IS_UAE_RESIDENT,"COMBO34");//not found
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(CUST_NATIONALITY,formObject.getValue(MANUAL_NATIONALITY).toString());
					}
					if("UNITED ARAB EMIRATES".equalsIgnoreCase(formObject.getValue(CUST_NATIONALITY).toString())) {
						enableControls(new String[]{COR_MAKANI, PERM_MAKANI, RES_MAKANI});
					} else {
						disableControls(new String[]{COR_MAKANI, PERM_MAKANI, RES_MAKANI});
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_MOTHERSNAME_MANUAL )) {
					toggleCheckbox(controlName,CHECKBOX_MOTHERSNAME_FCR,CHECKBOX_MOTHERSNAME_EIDA);
					manageManualFields(CHECKBOX_MOTHERSNAME_MANUAL ,MANUAL_MOTHERNAME);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(PD_MOTHERMAIDENNAME,formObject.getValue(MANUAL_MOTHERNAME).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_EIDANO_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_EIDANO_FCR,CHECKBOX_EIDANO_EIDA);
					manageManualFields(CHECKBOX_EIDANO_MANUAL,MANUAL_EIDANO);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(PD_EIDANO,formObject.getValue(MANUAL_EIDANO).toString());
						setEIDA();
					}
					if (formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)){
						logInfo("","Inside EIDANO_MANUAL  TRUE");
						formObject.setStyle(MANUAL_EIDANO, DISABLE, FALSE);
						logInfo("","Inside EIDANO_MANUAL  TRUE"+MANUAL_EIDANO);
						enableControls(new String[] {MANUAL_EIDANO});
					}
					if (formObject.getValue(controlName).toString().equalsIgnoreCase(FALSE)){
						logInfo("","Inside EIDANO_MANUAL FALSE ");
						formObject.setStyle(MANUAL_EIDANO, DISABLE, TRUE);
						logInfo("","Inside EIDANO_MANUAL  TRUE"+MANUAL_EIDANO);
						disableControls(new String[] {MANUAL_EIDANO});
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_CORR_POB_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_CORR_POB_FCR,CHECKBOX_CORR_POB_EIDA);
					manageManualFields(CHECKBOX_CORR_POB_MANUAL,MANUAL_ADDRESS);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(CP_POBOXNO,formObject.getValue(MANUAL_ADDRESS).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_CITY_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_CITY_FCR,CHECKBOX_CITY_EIDA);
					manageManualFields(CHECKBOX_CITY_MANUAL,MANUAL_CITY);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(CP_CITY,formObject.getValue(MANUAL_CITY).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_STATE_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_STATE_FCR,CHECKBOX_STATE_EIDA);
					manageManualFields(CHECKBOX_STATE_MANUAL,MANUAL_STATE);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(CORR_STATE,formObject.getValue(MANUAL_STATE).toString());
						if(formObject.getValue(MANUAL_STATE).toString().equalsIgnoreCase(OTHERS)) {
							formObject.setStyle(CP_OTHERS, DISABLE, FALSE);
						}
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_CNTRY_OF_CORR_MANUAL )) {
					toggleCheckbox(controlName,CHECKBOX_CNTRY_OF_CORR_FCR,CHECKBOX_CNTRY_OF_CORR_EID);
					manageManualFields(CHECKBOX_CNTRY_OF_CORR_MANUAL ,MANUAL_CNTRY);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(CORR_CNTRY,formObject.getValue(MANUAL_CNTRY).toString());
						if(formObject.getValue(MANUAL_CNTRY).toString().equalsIgnoreCase("UNITED ARAB EMIRATES"))
							formObject.setStyle(COR_MAKANI, DISABLE, FALSE);
						else
							formObject.setStyle(COR_MAKANI, DISABLE, TRUE);
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_COUNTRY_PER_RES_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_COUNTRY_PER_RES_FCR,CHECKBOX_COUNTRY_PER_RES_EIDA);
					manageManualFields(CHECKBOX_COUNTRY_PER_RES_MANUAL,MANUAL_PER_CNTRY);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(RES_CNTRY, formObject.getValue(MANUAL_PER_CNTRY).toString());
						if(formObject.getValue(MANUAL_PER_CNTRY).toString().equalsIgnoreCase("UNITED ARAB EMIRATES"))
							formObject.setStyle(RES_MAKANI, DISABLE, FALSE);
						else
							formObject.setStyle(RES_MAKANI, DISABLE, TRUE);
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
				} else if(controlName.equalsIgnoreCase(CHECKBOX_EMP_NAME_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_EMP_NAME_FCR,CHECKBOX_EMP_NAME_EIDA);
					manageManualFields(CHECKBOX_EMP_NAME_MANUAL,MANUAL_EMPLYR_NAME,BTNEMLOYERNAME); 
				} else if(controlName.equalsIgnoreCase(CHECKMANUAL)) {
					toggleCheckbox(controlName,CHECKFCR,CHECKEIDA);
					manageManualFields(CHECKMANUAL,MANUAL_RESIDENT);
					manageResidencyAndVisaStatus(RA_IS_UAE_RESIDENT,"COMBO34");
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(PERM_CNTRY,formObject.getValue(MANUAL_RESIDENT).toString());
						if(formObject.getValue(MANUAL_RESIDENT).toString().equalsIgnoreCase("UNITED ARAB EMIRATES"))
							formObject.setStyle(CONTACT_DETAILS_MAKANI_NO, DISABLE, FALSE);
						else
							formObject.setStyle(CONTACT_DETAILS_MAKANI_NO, DISABLE, TRUE);
					}
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
				} else if(controlName.equalsIgnoreCase(IS_SALARY_TRANSFER_CAT_CHANGE)) {
					toggleCheckboxTP(controlName,IS_MORTAGAGE_CAT_CHANGE,IS_INSURANCE_CAT_CHANGE,IS_TRB_CAT_CHANGE,IS_OTHERS_CAT_CHANGE,IS_VVIP);
					managePromoCodeCatChange();
				} else if(controlName.equalsIgnoreCase(IS_MORTAGAGE_CAT_CHANGE)) {
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
				} else if(controlName.equalsIgnoreCase(IS_PREVILEGE_TP_CAT_CHANGE)) {
					toggleCheckboxTP(controlName,IS_TRAVEL_CAT_CHANGE,IS_SPORT_CAT_CHANGE,IS_SHOPPING_CAT_CHANGE,IS_ENTERTAINMENT_CAT_CHANGE);
				} else if(controlName.equalsIgnoreCase(IS_TRAVEL_CAT_CHANGE)) {
					toggleCheckboxTP(controlName,IS_PREVILEGE_TP_CAT_CHANGE,IS_SPORT_CAT_CHANGE,IS_SHOPPING_CAT_CHANGE,IS_ENTERTAINMENT_CAT_CHANGE);
				} else if(controlName.equalsIgnoreCase(IS_SPORT_CAT_CHANGE)) {
					toggleCheckboxTP(controlName,IS_PREVILEGE_TP_CAT_CHANGE,IS_TRAVEL_CAT_CHANGE,IS_SHOPPING_CAT_CHANGE,IS_ENTERTAINMENT_CAT_CHANGE);
				} else if(controlName.equalsIgnoreCase(IS_SHOPPING_CAT_CHANGE)) {
					toggleCheckboxTP(controlName,IS_PREVILEGE_TP_CAT_CHANGE,IS_TRAVEL_CAT_CHANGE,IS_SPORT_CAT_CHANGE,IS_ENTERTAINMENT_CAT_CHANGE);
				} else if(controlName.equalsIgnoreCase(IS_ENTERTAINMENT_CAT_CHANGE)) {
					toggleCheckboxTP(controlName,IS_PREVILEGE_TP_CAT_CHANGE,IS_TRAVEL_CAT_CHANGE,IS_SPORT_CAT_CHANGE,IS_SHOPPING_CAT_CHANGE);
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
				} else if(controlName.equalsIgnoreCase(CHECKBOX_COUNTRY_RES_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_COUNTRY_RES_FCR,CHECKBOX_COUNTRY_RES_MANUAL);
					manageManualFields(CHECKBOX_COUNTRY_RES_MANUAL,MANUAL_RESIDENT);
					manageResidencyAndVisaStatus(RA_IS_UAE_RESIDENT,"COMBO34");
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(RES_CNTRY,formObject.getValue(EIDA_RESIDENT).toString());
						if(formObject.getValue(EIDA_RESIDENT).toString().equalsIgnoreCase("UNITED ARAB EMIRATES"))
							formObject.setStyle(CONTACT_DETAILS_MAKANI_NO, DISABLE, FALSE);
						else
							formObject.setStyle(CONTACT_DETAILS_MAKANI_NO, DISABLE, TRUE);
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_COUNTRY_RES_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_COUNTRY_RES_EIDA,CHECKBOX_COUNTRY_RES_MANUAL);
					manageManualFields(CHECKBOX_COUNTRY_RES_MANUAL,MANUAL_RESIDENT);
					manageResidencyAndVisaStatus(RA_IS_UAE_RESIDENT,"COMBO34");
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE))
					{
						formObject.setValue(RES_CNTRY,formObject.getValue(FCR_RESIDENT).toString());

						if(formObject.getValue(FCR_RESIDENT).toString().equalsIgnoreCase("UNITED ARAB EMIRATES"))
							formObject.setStyle(CONTACT_DETAILS_MAKANI_NO, DISABLE, FALSE);
						else
							formObject.setStyle(CONTACT_DETAILS_MAKANI_NO, DISABLE, TRUE);
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_COUNTRY_RES_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_COUNTRY_RES_FCR,CHECKBOX_COUNTRY_RES_EIDA);
					manageManualFields(CHECKBOX_COUNTRY_RES_MANUAL,MANUAL_RESIDENT);
					manageResidencyAndVisaStatus(RA_IS_UAE_RESIDENT,"COMBO34");

					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(RES_CNTRY,formObject.getValue(MANUAL_RESIDENT).toString());
						if(formObject.getValue(MANUAL_RESIDENT).toString().equalsIgnoreCase("UNITED ARAB EMIRATES"))
							formObject.setStyle(CONTACT_DETAILS_MAKANI_NO, DISABLE, FALSE);
						else
							formObject.setStyle(CONTACT_DETAILS_MAKANI_NO, DISABLE, TRUE);
					}
				} else if( controlName.equalsIgnoreCase(ED_MONTHLY_INCM) ){
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
				} else if(controlName.equalsIgnoreCase(ED_ANNUAL_INC)){
					String sAnnualSalary = formObject.getValue(ED_ANNUAL_INC).toString();					
					try {
						int iAnnualSal = Integer.parseInt(sAnnualSalary);
						int finalsalary = iAnnualSal/12;
						logInfo("executeServerEvent", "finalsalary: "+finalsalary);
						formObject.setValue(ED_MONTHLY_INCM,finalsalary+"");
						formObject.setValue(ED_SAL_AED,(String) formObject.getValue(ED_ANNUAL_INC));
					} catch(Exception ex){
						logInfo("executeServerEvent", "Exception in Event- " + eventType + "control name- " +controlName+ ": ");
						logError("executeServerEvent", ex);
					}
				} else if(controlName.equalsIgnoreCase(CRS_CB)) {
					if(formObject.getValue(CRS_CB).toString().equalsIgnoreCase("true"))
						formObject.setStyle(SEC_CRS_DETAILS, DISABLE, FALSE);
					else
						formObject.setStyle(SEC_CRS_DETAILS, DISABLE, TRUE);	
				} else if(controlName.equalsIgnoreCase(RA_CB_OTHERS)) {
					if(formObject.getValue(RA_CB_OTHERS).toString().equalsIgnoreCase("True")) {
						//formObject.setStyle(RA_OTHERS, DISABLE, FALSE);
						formObject.setValue(RA_CB_GEN_TRDNG_CMPNY,FALSE);
						formObject.setValue(RA_CB_PRECIOUS_STONE_DEALER,FALSE);
						formObject.setValue(RA_CB_BULLN_COMMDTY_BROKR,FALSE);
						formObject.setValue(RA_CB_REAL_STATE_BROKR,FALSE);
						formObject.setValue(RA_CB_USD_AUTO_DEALER,FALSE);
					    //BN_OTHERS, FINANCIAL_BROKERS, NOTARY_PUBLIC
						formObject.setValue(FINANCIAL_BROKERS,FALSE);
						formObject.setValue(NOTARY_PUBLIC,FALSE);
						formObject.setValue(SOCIAL_MEDIA_INFLUNCER,FALSE);
						formObject.setStyle(BN_OTHERS, DISABLE, FALSE);
					} else {
						/*formObject.setValue(RA_OTHERS,"");
						formObject.setStyle(RA_OTHERS, DISABLE, TRUE);*/	
						formObject.setValue(BN_OTHERS,"");
						formObject.setStyle(BN_OTHERS, DISABLE, TRUE);
					}
				} else if(controlName.equalsIgnoreCase(RA_CB_GEN_TRDNG_CMPNY)) {
					if(formObject.getValue(RA_CB_OTHERS).toString().equalsIgnoreCase("True")) {
						formObject.setValue(RA_CB_OTHERS,FALSE);
						/*formObject.setValue(RA_OTHERS,"");
						formObject.setStyle(RA_OTHERS, DISABLE, TRUE);*/
						formObject.setValue(BN_OTHERS,"");
						formObject.setStyle(BN_OTHERS, DISABLE, TRUE);
					}
				} else if(controlName.equalsIgnoreCase(RA_CB_PRECIOUS_STONE_DEALER)) {
					if(formObject.getValue(RA_CB_OTHERS).toString().equalsIgnoreCase("True")) {
						formObject.setValue(RA_CB_OTHERS,FALSE);
						/*formObject.setValue(RA_OTHERS,"");
						formObject.setStyle(RA_OTHERS, DISABLE, TRUE);*/
						formObject.setValue(BN_OTHERS,"");
						formObject.setStyle(BN_OTHERS, DISABLE, TRUE);
					}
				} else if(controlName.equalsIgnoreCase(RA_CB_BULLN_COMMDTY_BROKR)) {
					if(formObject.getValue(RA_CB_OTHERS).toString().equalsIgnoreCase("True")) {
						formObject.setValue(RA_CB_OTHERS,FALSE);
						/*formObject.setValue(RA_OTHERS,"");
						formObject.setStyle(RA_OTHERS, DISABLE, TRUE);*/
						formObject.setValue(BN_OTHERS,"");
						formObject.setStyle(BN_OTHERS, DISABLE, TRUE);
					}
				} else if(controlName.equalsIgnoreCase(RA_CB_REAL_STATE_BROKR)) {
					if(formObject.getValue(RA_CB_OTHERS).toString().equalsIgnoreCase("True")) {
						formObject.setValue(RA_CB_OTHERS,FALSE);
						/*formObject.setValue(RA_OTHERS,"");
						formObject.setStyle(RA_OTHERS, DISABLE, TRUE);*/
						formObject.setValue(BN_OTHERS,"");
						formObject.setStyle(BN_OTHERS, DISABLE, TRUE);
					}
				} else if(controlName.equalsIgnoreCase(RA_CB_USD_AUTO_DEALER)) {
					if(formObject.getValue(RA_CB_OTHERS).toString().equalsIgnoreCase("True")) {
						formObject.setValue(RA_CB_OTHERS,FALSE);
						/*formObject.setValue(RA_OTHERS,"");
						formObject.setStyle(RA_OTHERS, DISABLE, TRUE);*/
						formObject.setValue(BN_OTHERS,"");
						formObject.setStyle(BN_OTHERS, DISABLE, TRUE);
					}
				} else if(controlName.equalsIgnoreCase(FINANCIAL_BROKERS)) {
					if(formObject.getValue(RA_CB_OTHERS).toString().equalsIgnoreCase("True")) {
						formObject.setValue(RA_CB_OTHERS,FALSE);
						/*formObject.setValue(RA_OTHERS,"");
						formObject.setStyle(RA_OTHERS, DISABLE, TRUE);*/
						formObject.setValue(BN_OTHERS,"");
						formObject.setStyle(BN_OTHERS, DISABLE, TRUE);
					}
				} else if(controlName.equalsIgnoreCase(NOTARY_PUBLIC)) {
					if(formObject.getValue(RA_CB_OTHERS).toString().equalsIgnoreCase("True")) {
						formObject.setValue(RA_CB_OTHERS,FALSE);
						/*formObject.setValue(RA_OTHERS,"");
						formObject.setStyle(RA_OTHERS, DISABLE, TRUE);*/
						formObject.setValue(BN_OTHERS,"");
						formObject.setStyle(BN_OTHERS, DISABLE, TRUE);
					}
				} else if(controlName.equalsIgnoreCase(SOCIAL_MEDIA_INFLUNCER)) {
					if(formObject.getValue(RA_CB_OTHERS).toString().equalsIgnoreCase("True")) {
						formObject.setValue(RA_CB_OTHERS,FALSE);
						/*formObject.setValue(RA_OTHERS,"");
						formObject.setStyle(RA_OTHERS, DISABLE, TRUE);*/
						formObject.setValue(BN_OTHERS,"");
						formObject.setStyle(BN_OTHERS, DISABLE, TRUE);
					}
				} else if(controlName.equalsIgnoreCase(IDS_OTH_CB_OTHERS)) {
					if(formObject.getValue(IDS_OTH_CB_OTHERS).toString().equalsIgnoreCase("True")) {
						formObject.setStyle(IDS_BNFT_CB_OTHERS, DISABLE, TRUE);
						uncheckCheckBoxes(new String[]{IDS_CB_SAL_TRANSFER,IDS_CB_MORTGAGES,IDS_CB_INSURANCE,
								IDS_CB_TRB,IDS_CB_OTHERS,IDS_CB_VVIP});
						if(formObject.getValue(PD_CUSTSEGMENT).toString().equalsIgnoreCase("Privilege") 
								|| formObject.getValue(PD_CUSTSEGMENT).toString().equalsIgnoreCase("Emirati")) {
							uncheckCheckBoxes(new String[]{IDS_PC_CB_TP,IDS_PC_CB_TRAVEL,IDS_PC_CB_SPORT,
									IDS_PC_CB_SHOPPING,IDS_PC_CB_ENTERTAINMENT});
						} else if(formObject.getValue(PD_CUSTSEGMENT).toString().equalsIgnoreCase("Emirati Excellency") 
								|| formObject.getValue(PD_CUSTSEGMENT).toString().equalsIgnoreCase("Excellency") 
								|| formObject.getValue(PD_CUSTSEGMENT).toString().equalsIgnoreCase("Private Clients")) {
							formObject.setValue(IDS_BNFT_CB_TP,FALSE);
						}
					} else {
						formObject.setValue(IDS_BNFT_CB_OTHERS,"");
						formObject.setStyle(IDS_BNFT_CB_OTHERS, DISABLE, TRUE);				
					}
				} else if(controlName.equalsIgnoreCase(MANUAL_DOB)) {
					if(formObject.getValue(CHECKBOX_DOB_MANUAL).toString().equalsIgnoreCase("True")) {
						formObject.setValue(PD_DOB,formObject.getValue(controlName).toString());
					}
				} else if(controlName.equalsIgnoreCase(MANUAL_PASSPORTISSDATE)) {
					formObject.setValue(HD_PASS_ISS_DATE,formObject.getValue(controlName).toString());
				} else if(controlName.equalsIgnoreCase(MANUAL_PASSPORTEXPDATE)) {
					formObject.setValue(HD_PASS_EXP_DATE,formObject.getValue(controlName).toString());
				} else if(controlName.equalsIgnoreCase(MANUAL_VISAISSDATE)) {
					formObject.setValue(HD_VISA_ISSUE_DATE,formObject.getValue(controlName).toString());
				} else if(controlName.equalsIgnoreCase(MANUAL_VISAEXPDATE)) {
					formObject.setValue(HD_EXP_DATE,formObject.getValue(controlName).toString());
				} else if(controlName.equalsIgnoreCase(PER_INC_TAX_CON_1) ){
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
				}else if(controlName.equalsIgnoreCase(EMPNAME) 
						&& !formObject.getValue(controlName).toString().equalsIgnoreCase("")) {
					List<List<String>> sOutput = formObject.getDataFromDB("SELECT CD_STATUS FROM USR_0_EMPLOYER_MASTER WHERE EMP_NAME ='"+formObject.getValue(controlName)+"'");
					logInfo("","Emp Mast Output---"+sOutput);
					formObject.setValue(ED_CB_TML,"False");
					formObject.setValue(ED_CB_NON_TML,"False");
					if(sOutput.get(0).get(0).equalsIgnoreCase("1") ||sOutput.get(0).get(0).equalsIgnoreCase("2"))
					{
						formObject.setValue(ED_CB_TML,"True");
					}
					else
					{
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
					}
					else{
						formObject.setValue(EMP_STREET,"");
						formObject.setValue(EMP_PO_BOX,"");
						formObject.setValue(EMP_CITY,"");
						formObject.setValue(EMP_STATE,"");
						formObject.setValue(EMP_COUNTRY,"");
					}
				} else if(controlName.equalsIgnoreCase(ED_CB_INVSTMNT_RETN_AED)) {
					if(formObject.getValue(ED_CB_INVSTMNT_RETN_AED).toString().equalsIgnoreCase("true")) {
						formObject.setStyle(ED_INVSTMNT_RETN_AED, DISABLE, FALSE);
					} else {
						formObject.setStyle(ED_INVSTMNT_RETN_AED, DISABLE, TRUE);
					}
				} else if(controlName.equalsIgnoreCase(ED_CB_INHT_AED)) {
					if(formObject.getValue(ED_CB_INHT_AED).toString().equalsIgnoreCase("true")) {
						formObject.setStyle(ED_INHT_AED, DISABLE, FALSE);
					} else {
						formObject.setStyle(ED_INHT_AED, DISABLE, TRUE);
					}
				} else if(controlName.equalsIgnoreCase(ED_CB_REAL_INC_AED)) {
					if(formObject.getValue(ED_CB_REAL_INC_AED).toString().equalsIgnoreCase("true")) {
						formObject.setStyle(ED_REAL_INC_AED, DISABLE, FALSE);
					} else {
						formObject.setStyle(ED_REAL_INC_AED, DISABLE, TRUE);
					}
					
				} else if(controlName.equalsIgnoreCase(ED_CB_SALE_OF_ASST)) {
					if(formObject.getValue(ED_CB_SALE_OF_ASST).toString().equalsIgnoreCase("true")) {
						formObject.setStyle(ED_SALE_OF_ASST, DISABLE, FALSE);
					} else {
						formObject.setStyle(ED_SALE_OF_ASST, DISABLE, TRUE);
					}
				} else if(controlName.equalsIgnoreCase(ED_CB_OTHERS)) {
					if(formObject.getValue(ED_CB_OTHERS).toString().equalsIgnoreCase("true")) {
						formObject.setStyle(ED_OTHERS, DISABLE, FALSE);
					} else {
						formObject.setStyle(ED_OTHERS, DISABLE, TRUE);
					}
				}else if(controlName.equalsIgnoreCase("LISTVIEW_PUR_ACC_RELATION")){
					logInfo("LISTVIEW_PUR_ACC_RELATION", "inside LISTVIEW_PUR_ACC_RELATION: ");
					accountPurpose();
		    	}else if(controlName.equalsIgnoreCase(ADDITIONAL_SOURCES_INCOME_AED)){
					logInfo("ADDITIONAL_SOURCES_INCOME_AED", "inside ADDITIONAL_SOURCES_INCOME_AED: ");
					additionalSource();
				//Added by Shivanshu CRS TIN COUNTRY
				}else if(controlName.equalsIgnoreCase(CRS_TAX_COUNTRY)){
					logInfo("CRS_TAX_COUNTRY", "inside CRS_TAX_COUNTRY : ");
					disableFieldCRSCountry();
				}
				if(controlName.equalsIgnoreCase(CHECKBOX_NATIONALITY_FCR)|| controlName.equalsIgnoreCase(FCR_NATIONALITY)||
						controlName.equalsIgnoreCase(CHECKBOX_NATIONALITY_EIDA)||controlName.equalsIgnoreCase(CHECKBOX_NATIONALITY_MANUAL)||
						controlName.equalsIgnoreCase(EIDA_NATIONALITY)||controlName.equalsIgnoreCase(MANUAL_NATIONALITY)||
						controlName.equalsIgnoreCase(CHECKBOX_CNTRY_OF_CORR_FCR)||controlName.equalsIgnoreCase(FCR_CNTRY)||controlName.equalsIgnoreCase(CHECKBOX_CNTRY_OF_CORR_EID)||
						controlName.equalsIgnoreCase(CHECKBOX_CNTRY_OF_CORR_MANUAL )||controlName.equalsIgnoreCase(EIDA_CNTRY)||
						controlName.equalsIgnoreCase(MANUAL_CNTRY)||controlName.equalsIgnoreCase(CHECKBOX_TELE_RES_FCR)||controlName.equalsIgnoreCase(FCR_PH)||
						controlName.equalsIgnoreCase(CHECKBOX_TELE_RES_EIDA)||controlName.equalsIgnoreCase(CHECKBOX_TELE_RES_MANUAL)||controlName.equalsIgnoreCase(EIDA_PH)||
						controlName.equalsIgnoreCase(MANUAL_PH)||controlName.equalsIgnoreCase(CHECKBOX_TELE_MOB_FCR)||
						controlName.equalsIgnoreCase(FCR_MOBILE)||controlName.equalsIgnoreCase(CHECKBOX_TELE_MOB_EIDA)||controlName.equalsIgnoreCase(CHECKBOX_TELE_MOB_MANUAL)||
						controlName.equalsIgnoreCase(EIDA_MOBILE)||controlName.equalsIgnoreCase(MANUAL_MOBILE)||
						controlName.equalsIgnoreCase(CHECKFCR)||controlName.equalsIgnoreCase(FCR_RESIDENT)||
						controlName.equalsIgnoreCase(CHECKEIDA)||controlName.equalsIgnoreCase(CHECKMANUAL)||
						controlName.equalsIgnoreCase(EIDA_RESIDENT)||controlName.equalsIgnoreCase(MANUAL_RESIDENT)|| 
						controlName.equalsIgnoreCase(FAT_US_PERSON)||controlName.equalsIgnoreCase(FAT_LIABLE_TO_PAY_TAX)||
						controlName.equalsIgnoreCase(CNTRY_OF_BIRTH)||
						controlName.equalsIgnoreCase(POACOMBO)||
						controlName.equalsIgnoreCase(INDICIACOMBO)||controlName.equalsIgnoreCase(FAT_SSN)||
						controlName.equalsIgnoreCase(FAT_CUST_CLASSIFICATION)||controlName.equalsIgnoreCase(DATEPICKERCUST)||
						controlName.equalsIgnoreCase(DATEPICKERW8)||controlName.equalsIgnoreCase(COMBODOC)||
						controlName.equalsIgnoreCase(CHECKBOX_COB_FCR)||controlName.equalsIgnoreCase(CHECKBOX_COB_EIDA)||
						controlName.equalsIgnoreCase(CHECKBOX_COB_MANUAL)||controlName.equalsIgnoreCase(FCR_COUNTRYBIRTH)||
						controlName.equalsIgnoreCase(EIDA_COUNTRYBIRTH)||controlName.equalsIgnoreCase(MANUAL_COUNTRYBIRTH)) {
					if(!formObject.getValue(SELECTED_ROW_INDEX).toString().isEmpty()) {
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
						String sAccRelation= formObject.getTableCellValue(ACC_RELATION, fieldToFocus, 7).toString();
						logInfo("CHANGE EVENT","control name: "+controlName+", sAccRelation------"+sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
					if(controlName.equalsIgnoreCase(MANUAL_MOBILE) &&
							"United Arab Emirates".equalsIgnoreCase(checkCountry())) {
						mobileChangeFlag = true;
						String sNumber=formObject.getValue(MANUAL_MOBILE).toString();
						//Added by Shivanshu ATP-472
						if(!(sNumber.startsWith("971") || sNumber.startsWith("+971") || sNumber.startsWith("00971"))) {
							sendMessageValuesList("","Mobile Number does not start with 971");
						}
					}
				}
				if(ACTIVITY_DDE_CUST_INFO.equalsIgnoreCase(sActivityName) && (controlName.equalsIgnoreCase(CHECKBOX_NATIONALITY_FCR) 
						|| controlName.equalsIgnoreCase(FCR_NATIONALITY) || controlName.equalsIgnoreCase(CHECKBOX_NATIONALITY_EIDA)
						|| controlName.equalsIgnoreCase(CHECKBOX_NATIONALITY_MANUAL) || controlName.equalsIgnoreCase(EIDA_NATIONALITY)
						|| controlName.equalsIgnoreCase(MANUAL_NATIONALITY) 
						|| controlName.equalsIgnoreCase(CHECKBOX_CNTRY_OF_CORR_FCR) || controlName.equalsIgnoreCase(FCR_CNTRY) 
						|| controlName.equalsIgnoreCase(CHECKBOX_CNTRY_OF_CORR_EID) || controlName.equalsIgnoreCase(CHECKBOX_CNTRY_OF_CORR_MANUAL ) 
						|| controlName.equalsIgnoreCase(EIDA_CNTRY) || controlName.equalsIgnoreCase(MANUAL_CNTRY)
						|| controlName.equalsIgnoreCase(CHECKBOX_TELE_RES_FCR) || controlName.equalsIgnoreCase(FCR_PH) 
						|| controlName.equalsIgnoreCase(CHECKBOX_TELE_RES_EIDA) || controlName.equalsIgnoreCase(CHECKBOX_TELE_RES_MANUAL) 
						|| controlName.equalsIgnoreCase(EIDA_PH) || controlName.equalsIgnoreCase(MANUAL_PH) 
						|| controlName.equalsIgnoreCase(CHECKBOX_TELE_MOB_FCR) || controlName.equalsIgnoreCase(FCR_MOBILE) 
						|| controlName.equalsIgnoreCase(CHECKBOX_TELE_MOB_EIDA) || controlName.equalsIgnoreCase(CHECKBOX_TELE_MOB_MANUAL) 
						|| controlName.equalsIgnoreCase(EIDA_MOBILE) || controlName.equalsIgnoreCase(MANUAL_MOBILE)
						|| controlName.equalsIgnoreCase(CHECKFCR) || controlName.equalsIgnoreCase(FCR_RESIDENT) 
						|| controlName.equalsIgnoreCase(CHECKEIDA) || controlName.equalsIgnoreCase(CHECKMANUAL) 
						|| controlName.equalsIgnoreCase(EIDA_RESIDENT) 
						|| controlName.equalsIgnoreCase(MANUAL_RESIDENT) || controlName.equalsIgnoreCase(FAT_US_PERSON)
						|| controlName.equalsIgnoreCase(FAT_LIABLE_TO_PAY_TAX) || controlName.equalsIgnoreCase(CNTRY_OF_BIRTH) 
						|| controlName.equalsIgnoreCase(POACOMBO) || controlName.equalsIgnoreCase(INDICIACOMBO)
						|| controlName.equalsIgnoreCase(FAT_SSN) || controlName.equalsIgnoreCase(FAT_CUST_CLASSIFICATION)
						|| controlName.equalsIgnoreCase(DATEPICKERCUST) 
						|| controlName.equalsIgnoreCase(DATEPICKERW8) || controlName.equalsIgnoreCase(COMBODOC)
						|| controlName.equalsIgnoreCase(CHECKBOX_COB_FCR) || controlName.equalsIgnoreCase(CHECKBOX_COB_EIDA) 
						|| controlName.equalsIgnoreCase(CHECKBOX_COB_MANUAL) || controlName.equalsIgnoreCase(FCR_COUNTRYBIRTH)
						|| controlName.equalsIgnoreCase(EIDA_COUNTRYBIRTH) 
						|| controlName.equalsIgnoreCase(MANUAL_COUNTRYBIRTH))) { 
					if(!formObject.getValue(SELECTED_ROW_INDEX).toString().isEmpty()) {
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION, fieldToFocus, 7);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				}



				//			else if(controlName.equalsIgnoreCase(ETIHAD_NO) && pEventName.equalsIgnoreCase("KeyPress"))
				//			{
				//				iEtihadValidate=0;
				//				formObject.setValue("IS_ETIHAD", "0");
				//			}
				//				} else if("custom_RM_CODE".equalsIgnoreCase(controlName)) {
				//					setProfitCode();
				//				}
				//load
				if("MDSA".equalsIgnoreCase(formObject.getValue(MANUAL_VISANO).toString())
						&& ! "Under Processing".equalsIgnoreCase(returnVisaStatus())) {
					int status = updateDataInDB(sCustTxnTable, "VISA_NO", "''", "cust_sno = '"+getPrimaryCustomerSNO()+"' "
							+ "AND WI_NAME = '"+sWorkitemId+"'");
					logInfo("Change Event: MANUAL_VISA_STATUS", "update status: "+status);
					formObject.setValue(CHECKBOX_VISA_NO_MANUAL,TRUE);
					formObject.setValue(CHECKBOX_VISA_NO_FCR,FALSE);
					formObject.setValue(CHECKBOX_VISA_NO_EIDA,FALSE);
					formObject.setStyle(MANUAL_VISANO, DISABLE, FALSE);
					if(TRUE.equalsIgnoreCase(formObject.getValue(CHECKBOX_SELECTALL_MANUAL).toString()) 
							|| TRUE.equalsIgnoreCase(formObject.getValue(CHECKBOX_VISA_NO_MANUAL).toString())) {
						formObject.setValue(MANUAL_VISANO,"");
						formObject.setValue(HD_VISA_NO, formObject.getValue(MANUAL_VISANO).toString());
					}
					sendMessageValuesList("", CA0172);
				}
				//fillManualDataInBelowFields(controlName, formObject.getValue(controlName).toString());
				
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_LOSTFOCUS)) {  

				if(controlName.equalsIgnoreCase(ACC_RELATION)) {
					String controlValue = formObject.getValue(controlName).toString();
					formObject.setValue(SELECTED_ROW_INDEX, (Integer.parseInt(controlValue)-1)+"");
				} else if(controlName.equalsIgnoreCase("COMBO3")) {
					if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("New Account With Category Change") 
							&& formObject.getValue(NEW_CUST_SEGMENT).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(NEW_CUST_SEGMENT, "Please select New Category"); 
					}
				}
			} else if("handlingJSPData".equalsIgnoreCase(eventType)) {
				  if(controlName.equalsIgnoreCase(BTN_PRD_SEARCH)) {
						searchProductList("USR_0_PRODUCT_OFFERED", data);
					} 
			}
		} catch (Exception e) {
			logInfo("executeServerEvent", "Exception in Event- " + eventType + "control name- " +controlName+ ": ");
			logError("executeServerEvent", e);
		} finally {
			logInfo("executeServerEvent", sendMessageList.toString());
			if(!sendMessageList.isEmpty()) {
				if(sendMessageList.get(1).contains(" does not start with 971") ||
						sendMessageList.get(1).equalsIgnoreCase("Mobile number is not of 12 digits") || 
						sendMessageList.get(1).equalsIgnoreCase("Residence Telephone Number is not of 11 digits")) {
					return getReturnMessage(true,controlName,sendMessageList.get(0)+"###"
							+ sendMessageList.get(1));
				}
				return getReturnMessage(false,controlName,sendMessageList.get(0).toString()+"###"
						+ sendMessageList.get(1));
			}
		}
		return retMsg;		
	}

	@SuppressWarnings("unchecked")
	private void onDDEFormLoad() {
		try {
			String decision = "";
			String sCompDec = "";
			String sCurrentDate = "";
			try {
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat simpledateformat = new SimpleDateFormat(DATEFORMAT);
				sCurrentDate = simpledateformat.format(calendar.getTime());
				logInfo("onDDEFormLoad", "scurrentDate: "+sCurrentDate);
			} catch(Exception ex) {
				logError("onDDEFormLoad",ex);
			}
			loadSICombos();
			int iProcessedCustomer = 0;
			Map<Integer,Integer> mp = new HashMap<Integer,Integer>();
			setDefaultPermanentField();
			String query = "select cro_dec, comp_dec from "+sExtTable+" where wi_name ='"+sWorkitemId+"'";
			log.info("onDDEFormLoad query 1= "+query);
			List<List<String>> result = formObject.getDataFromDB(query);
			if(result != null && result.size() > 0) { 
				decision = result.get(0).get(0);
				sCompDec = result.get(0).get(1);
			}
			query = "SELECT DOB FROM ACC_RELATION_REPEATER WHERE WI_NAME =N'"+sWorkitemId+"'";	
			log.info("onDDEFormLoad query 2= "+query);
			result = formObject.getDataFromDB(query);
			String minorDOB = result.get(0).get(0);
			int minorAge = calculateAge(minorDOB);
			if((minorAge >= 18) && (minorAge <= 21)) {
				query = "SELECT COUNT(1) AS COUNT FROM ACC_RELATION_REPEATER WHERE WI_NAME = '"+sWorkitemId+"' "
						+ "AND acc_relation = 'Guardian'";
				result = formObject.getDataFromDB(query);
				String guardianCount = result.get(0).get(0);
				if("0".equalsIgnoreCase(guardianCount)){
					String[] show = {DOC_APPROVAL_OBTAINED,COURT_ORD_TRADE_LIC};
					showControls(show);
					formObject.setStyle(DOC_APPROVAL_OBTAINED, DISABLE, FALSE);
					formObject.setStyle(COURT_ORD_TRADE_LIC, DISABLE, FALSE);
				}
			}
			query = "SELECT PRODUCT_CODE FROM USR_0_PRODUCT_OFFERED WHERE WI_NAME =N'"+sWorkitemId+"'";	
			result = formObject.getDataFromDB(query);
			logInfo("onDDEFormLoad", "query result: "+result.toString());
			if(result.size() > 0 && null != result.get(0).get(0)) {
				String sProduct = result.get(0).get(0);
				log.info("sProduct----"+sProduct);
				if("871".equalsIgnoreCase(sProduct)){
					String[] show = {DOC_APPROVAL_OBTAINED,COURT_ORD_TRADE_LIC};
					hideControls(show);
				}
			}
            //Frame52_Disable();
			//setAutoFilledFieldsLocked(); called in js Already
			if(ACTIVITY_DDE_CUST_INFO.equalsIgnoreCase(sActivityName)) {
				
				formObject.setValue(CRS_CERTIFICATION_OBTAINED, "Yes");
				setDefaultPermanentField();
				int selectedRow= 0;
				try {
					selectedRow= Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
				} catch(Exception e) {
					logError("onDDEFormLoad", e);
				}
				//				query = "select FIRST_SELECTED_ROW_INDEX from "+sExtTable+" where wi_name ='"+sWorkitemId+"'";
				//				logInfo("onDDEFormLoad", "qryFirstSelected: " + query);
				//				result = formObject.getDataFromDB(query);
				logInfo("onDDEFormLoad","outFirstSelected---"+formObject.getValue("FIRST_SELECTED_ROW_INDEX").toString());
				//				int firstSelected = Integer.parseInt(result.get(0).get(0));
				int counter=0;
				query = "select FIRST_SELECTED_ROW_INDEX from "+ sExtTable+ " where wi_name ='"
						+ sWorkitemId + "'";
				result = formObject.getDataFromDB(query);
				logInfo("onDDEFormLoad","query FirstSelected---"+query);
				logInfo("onDDEFormLoad","result FirstSelected---"+result.toString());
				try {
//					if(!formObject.getValue("FIRST_SELECTED_ROW_INDEX").toString().isEmpty()) {
					if(result.size() > 0) {
//						int firstSelected = Integer.parseInt(formObject.getValue("FIRST_SELECTED_ROW_INDEX").toString());
						int firstSelected = Integer.parseInt(result.get(0).get(0));
						if(!formObject.getValue(NO_OF_CUST_SEARCHED).toString().isEmpty()) {
							int customerSearched = Integer.parseInt(formObject.getValue(NO_OF_CUST_SEARCHED).toString());
							for(int i=0;i<customerSearched;i++) {
								logInfo("onDDEFormLoad", "#################### In For Loop #################");
								if(counter != 0) {
									firstSelected = firstSelected+1;
								}
								if(firstSelected == customerSearched) {
									firstSelected = 0;
								}
								counter++;
								logInfo("onDDEFormLoad", "#################### selectedRow ################# "+firstSelected);
								mp.put(counter, firstSelected);
								logInfo("onDDEFormLoad", "#################### mp.size() ############## "+mp.size());
							}
						}
					}
				} catch(Exception e) {
					logError("onDDEFormLoad", e);
				}
				if(!formObject.getValue(CUST_PROCESSED).toString().isEmpty()) {
					int custProcessed = Integer.parseInt(formObject.getValue(CUST_PROCESSED).toString());
					if(mp.get(custProcessed) != null) {
						selectedRow = (Integer)mp.get(custProcessed);
						log.info("$$$$$$$$$$$ Final selectedRow $$$$$$$$$$$$$$ "+selectedRow);
						formObject.setValue(SELECTED_ROW_INDEX, String.valueOf(selectedRow));
						iProcessedCustomer = selectedRow;
					}
				}
				//				try
				//				{				
				//					objChkRepeater.setFocus(iProcessedCustomer+1,"ACC_RELATION.NAME");
				//				}
				//				catch(Exception e) {
				//					log.error("Exception: ",e);
				//				}
				frame81_CPD_Disable();
				populatePersonalData();
				populateRiskData();
				populateKYCData();	
				populateKycMultiDrop();
				populatePreAssesmentDetails();
				populatePrivateClientQuestions(); 
				populateComparisonFields();
				managePromoCode();//newly added
				populatePepAssesmentDetails(); //AO DCRA
				if(FALSE.equalsIgnoreCase(formObject.getValue(RA_CB_OTHERS).toString())) {
					formObject.setStyle(RA_OTHERS, DISABLE, TRUE);
				} else {
					formObject.setStyle(RA_OTHERS, DISABLE, FALSE);
				}
				if(FALSE.equalsIgnoreCase(formObject.getValue(IDS_OTH_CB_OTHERS).toString())) {
					formObject.setStyle(IDS_BNFT_CB_OTHERS, DISABLE, TRUE);
					formObject.setStyle(IDS_OTH_CB_OTHERS, DISABLE, TRUE);
					if(!"".equalsIgnoreCase(formObject.getValue(PD_CUSTSEGMENT).toString())) {
						formObject.setStyle(IDS_OTH_CB_OTHERS, DISABLE, FALSE);
					}
				} else {
					String[] enable = {IDS_BNFT_CB_OTHERS,IDS_OTH_CB_OTHERS};
					enableControls(enable);
				}
				if(formObject.getValue(PD_CUSTSEGMENT).toString().isEmpty()) {
					String[] clear = {PRO_CODE,EXCELLENCY_CNTR};
					clearControls(clear);
				}
				if(!formObject.getValue(SELECTED_ROW_INDEX).toString().isEmpty()) {
					int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
					String bankRelation = formObject.getTableCellValue(ACC_RELATION, fieldToFocus, 7);
					if(bankRelation.equalsIgnoreCase("Existing")) {
						formObject.setStyle(IDS_OTH_CB_OTHERS, DISABLE, TRUE);
					}
					if(FALSE.equalsIgnoreCase(formObject.getValue(CK_PER_DET).toString())) {
						formObject.setStyle(DRP_RESEIDA, DISABLE, TRUE);
					} else if( returnVisaStatus().equalsIgnoreCase("Residency Visa")) {
						formObject.setStyle(DRP_RESEIDA, DISABLE, FALSE);
					} else {
						formObject.setStyle(DRP_RESEIDA, DISABLE, TRUE);
						formObject.setValue(DRP_RESEIDA,"");
					}
				}
				String[] disable = {NIG_MAKER, NIG_CHECKER};
				disableControls(disable);
				if(TRUE.equalsIgnoreCase(formObject.getValue(CHECKBOX_SELECTALL_MANUAL).toString())) {
					manageManualCheckBoxes();
				} else {
					setManualFieldsLock();
				}
				query = "SELECT CUST_ID,CUST_NAME,CUST_IC,CUST_PASSPORT,CUST_EMAIL,CUST_MOBILE,'','',"
						+ "to_char(CUST_DOB,'"+ DATEFORMAT +"'),"
						+ "CUST_EIDA,CUST_NATIONALITY FROM USR_0_DUPLICATE_SEARCH_DATA WHERE WI_NAME='"
						+sWorkitemId+"' AND CUST_SNO ='"+iProcessedCustomer+"'";
				logInfo("onDDEFormLoad", "USR_0_DUPLICATE_SEARCH_DATA query: "+query);
				result = formObject.getDataFromDB(query);
				if(result.size() > 0) {
					String listviewValues = result.get(0).get(0)+"##"+result.get(0).get(1)+"##"+result.get(0).get(2)+"##"
							+result.get(0).get(3)+"##"+result.get(0).get(4)+"##"+result.get(0).get(5)+"##"
							+result.get(0).get(6)+"##"+result.get(0).get(7)+"##"+result.get(0).get(8)+"##"
							+result.get(0).get(9)+"##"+result.get(0).get(10);
					LoadListViewWithHardCodeValues(LVW_DEDUPE_RESULT, COLUMNS_LVW_DEDUPE_RESULT, listviewValues);
				}
				if(formObject.getValue(RELIGION).toString().isEmpty()) {
					formObject.setValue(RELIGION, OTHERS);
				}
				if(formObject.getValue(MARITAL_STATUS).toString().isEmpty()) {
					formObject.setValue(MARITAL_STATUS, "Single");
				}
				//shahbaz
				String bank_Relationship = formObject.getTableCellValue(ACC_RELATION, 0, 7); 
				logInfo("populateLastKycDate ", "bank_Relationship: " + bank_Relationship);
				
				if(!bank_Relationship.equalsIgnoreCase("Existing")){
				formObject.addItemInCombo(GI_DATE_KYC_PREP, sCurrentDate);
				formObject.setValue(GI_DATE_KYC_PREP, sCurrentDate);
				}
				setCustomerRelation();
				String sDOB = getFinalDataComparison(CHECKBOX_DOB_FCR,CHECKBOX_DOB_EIDA,CHECKBOX_DOB_MANUAL,FCR_DOB,EIDA_DOB,MANUAL_DOB)
						.trim().toUpperCase();
				formObject.setValue(PD_DOB,sDOB);
				String sNationality = getFinalDataComparison(CHECKBOX_NATIONALITY_FCR,CHECKBOX_NATIONALITY_EIDA,CHECKBOX_NATIONALITY_MANUAL,FCR_NATIONALITY,
						EIDA_NATIONALITY,MANUAL_NATIONALITY).trim();
				formObject.setValue(CUST_NATIONALITY,sNationality);
				String sFinalCountry = getFinalData(formObject.getValue(CHECKBOX_CNTRY_OF_CORR_FCR).toString(),
						formObject.getValue(CHECKBOX_CNTRY_OF_CORR_EID).toString(), formObject.getValue(CHECKBOX_CNTRY_OF_CORR_MANUAL ).toString(),
						formObject.getValue(FCR_CNTRY).toString(), formObject.getValue(EIDA_CNTRY).toString(),
						formObject.getValue(MANUAL_CNTRY).toString());
				String sFinalResidentNew = getFinalData(formObject.getValue(CHECKFCR).toString(),
						formObject.getValue(CHECKEIDA).toString(), formObject.getValue(CHECKMANUAL).toString(),
						formObject.getValue(FCR_RESIDENT).toString(), formObject.getValue(EIDA_RESIDENT).toString(),
						formObject.getValue(MANUAL_RESIDENT).toString());
				String sFinalPermanentCountry = getFinalData(formObject.getValue(CHECKBOX_COUNTRY_PER_RES_FCR).toString(),
						formObject.getValue(CHECKBOX_COUNTRY_PER_RES_EIDA).toString(),formObject.getValue(CHECKBOX_COUNTRY_PER_RES_MANUAL).toString(),
						formObject.getValue(FCR_PER_CNTRY).toString(),formObject.getValue(EIDA_PER_CNTRY).toString(),
						formObject.getValue(MANUAL_PER_CNTRY).toString());
				log.info("--disable makani --:"+sNationality);
				if(!"UNITED ARAB EMIRATES".equalsIgnoreCase(sFinalCountry)) {
					formObject.setStyle(COR_MAKANI, DISABLE, TRUE);
				}
				if(!"UNITED ARAB EMIRATES".equalsIgnoreCase(sFinalResidentNew)) {
					formObject.setStyle(CONTACT_DETAILS_MAKANI_NO, DISABLE, TRUE);
				}
				if(!"UNITED ARAB EMIRATES".equalsIgnoreCase(sFinalPermanentCountry)) {
					formObject.setStyle(RES_MAKANI, DISABLE, TRUE);
				}
				if(sNationality.equalsIgnoreCase("UNITED STATES")) {
					formObject.setValue(FAT_US_PERSON, "YES");
					formObject.setValue(FAT_LIABLE_TO_PAY_TAX, "YES");
					String[] disable1 = {FAT_US_PERSON, FAT_LIABLE_TO_PAY_TAX};
					disableControls(disable1);
				} else if(formObject.getValue(FAT_US_PERSON).toString().isEmpty()) {
					formObject.setValue(FAT_US_PERSON, "");
					formObject.setValue(FAT_LIABLE_TO_PAY_TAX,"");
					String[] enable1 = {FAT_US_PERSON, FAT_LIABLE_TO_PAY_TAX};
					enableControls(enable1);
				}
				if(!formObject.getValue(FCR_NAME).toString().isEmpty()) {
					formObject.setStyle(PD_CUSTSEGMENT, DISABLE, TRUE);
				} else {
					formObject.setStyle(PD_CUSTSEGMENT, DISABLE, FALSE);
				}
				if(formObject.getValue(GI_IS_CUST_VIP).toString().isEmpty()) {
					formObject.setValue(GI_IS_CUST_VIP, "No");
				}
				if(formObject.getValue(RA_IS_CUST_DEALNG_ARMAMNT).toString().isEmpty()) {
					formObject.setValue(RA_IS_CUST_DEALNG_ARMAMNT, "No");
				}
				if(formObject.getValue(RA_IS_CUST_DEALNG_ARMAMNT).toString().isEmpty()) {
					formObject.setValue(RA_IS_CUST_DEALNG_ARMAMNT, "No");
				}
				if(formObject.getValue(RA_IS_CUST_DEALNG_HAWALA).toString().isEmpty()) {
					formObject.setValue(RA_IS_CUST_DEALNG_HAWALA, "No");
				}
				if(formObject.getValue(RA_PRPSE_TAX_EVSN).toString().isEmpty()) {
					formObject.setValue(RA_PRPSE_TAX_EVSN, "No");
				}
				if(formObject.getValue(RA_IS_CUST_PEP).toString().isEmpty()) {
					formObject.setValue(RA_IS_CUST_PEP, "No");
				}
				if("New Account".equalsIgnoreCase(formObject.getValue(REQUEST_TYPE).toString())) {
					String acc_rel = formObject.getTableCellValue(ACC_RELATION,1,9);
					logInfo("onDDEFormLoad","@@@@@acc_rel@@2@@22  = "+acc_rel);
					String priCustCat = formObject.getValue(PD_CUSTSEGMENT).toString();
					logInfo("onDDEFormLoad","Customer segment------------::::"+priCustCat);
					if(!(acc_rel.equalsIgnoreCase("AUS") && acc_rel.equalsIgnoreCase("POA")) 
							&& priCustCat.equalsIgnoreCase("Signatory")) {
						logInfo("onDDEFormLoad","inside if--------");
						sendMessageValuesList("", "Primary Signatory Customer can't open a new account");
					}
				}
				if(TRUE.equalsIgnoreCase(formObject.getValue(CHECKBOX_VISA_NO_MANUAL).toString()) 
						&& (!"MDSA".equalsIgnoreCase(formObject.getValue(MANUAL_VISANO).toString()))) {
					logInfo("onDDEFormLoad"," in if "+formObject.getValue(CHECKBOX_VISA_NO_MANUAL).toString());
					formObject.setStyle(MANUAL_VISANO, DISABLE, FALSE);
				}
				saveKYCInfo();
//				saveKycMultiDropDownData();
		//		savePepAssessmentDetails();  //Ao Dcra
//				savePreAssessmentDetails();    //shahbaz
				saveComparisonData();
				saveIndividualInfo();
				saveIndividualContactInfo();
				log.info("outside SIGN"+formObject.getValue(SIGN_STYLE1).toString());
				if("".equalsIgnoreCase(formObject.getValue(SIGN_STYLE1).toString())) {
					formObject.setValue(SIGN_STYLE1, "Signature in English");
					logInfo("onDDEFormLoad","INSIDE SIGN "+formObject.getValue(SIGN_STYLE1));
				}
				//Jamshed Ao additional
				String private_clnt_flg="";
				String pri_query ="select private_client from ext_ao where wi_name='"+sWorkitemId+"'";
				logInfo("On load QDE_Cust_Info","pri_query= "+ pri_query);
				List<List<String>> pri_clnt_list =formObject.getDataFromDB(pri_query); 
				if (pri_clnt_list != null && pri_clnt_list.size() > 0) {
					private_clnt_flg=pri_clnt_list.get(0).get(0); 
					logInfo("On load QDE_Cust_Info","private_clnt_flg= "+ private_clnt_flg);
				}
				
				if ((formObject.getValue(REQUEST_TYPE).toString()).equalsIgnoreCase("New Account") || 
						(formObject.getValue(REQUEST_TYPE).toString()).equalsIgnoreCase("New Account with Category Change")) {
					if(private_clnt_flg !=null && !private_clnt_flg.equalsIgnoreCase("") && private_clnt_flg.equalsIgnoreCase("Y")){
						formObject.setValue(PD_CUSTSEGMENT, "Private Clients");
						formObject.setStyle(PD_CUSTSEGMENT, DISABLE, TRUE);
					}
				}
				
			} else if(ACTIVITY_DDE_ACCOUNT_INFO.equalsIgnoreCase(sActivityName)) {
				//editButtonVisible();
				prefLang();
				//objChkRepeater.setFocus(1,"AO_ACC_RELATION.NAME");//check focus on load
				Frame22_CPD_Disable();
				logInfo("Onload DDE ACCT Info","populatePrivateClientQuestions");
				populatePrivateClientQuestions();
				formObject.setStyle(SEC_INT_DETAIL, DISABLE, FALSE);
				formObject.setTabStyle("tab3", "7", DISABLE, TRUE);
                //formObject.setTabStyle("tab3", "8", DISABLE, TRUE);
				String req_type = formObject.getValue(REQUEST_TYPE).toString();
				logInfo("Onload DDE ACCT Info","req_type");
				if(req_type.equalsIgnoreCase("New Account with Category Change") 
						|| req_type.equalsIgnoreCase("Category Change Only")
						|| req_type.equalsIgnoreCase("Upgrade")) {	
					//disableControls(new String[]{"SEC_CAT_CHNG","SEC_INTERNAL_DETL"});
					enableControls(new String[]{SEC_CAT_CHNG,SEC_INTERNAL_DETL});
					//ADDED  BY KRISHNA
					if(req_type.equalsIgnoreCase("Category Change Only") || req_type.equalsIgnoreCase("Upgrade")) {
						formObject.setTabStyle("tab3", "8", DISABLE, TRUE);
					}
				} else {
					formObject.setStyle(SEC_CAT_CHNG, DISABLE, TRUE);
					formObject.setTabStyle("tab3", "8", DISABLE, FALSE);
				}
				setDDEModeCombos();
				//formObject.NGFocus("AO_ACC_TITLE");//check focus on load
				formObject.setValue(TXT_CUSTOMERNAME, formObject.getTableCellValue(ACC_RELATION, 0,1).toString());//(1, "AO_acc_relation.NAME"));
				formObject.setValue(TXT_DOB, formObject.getTableCellValue(ACC_RELATION, 0, 5).toString());//(1, "AO_acc_relation.DOB"));
				formObject.setValue(CUST_ID, formObject.getTableCellValue(ACC_RELATION, 0, 2).toString());//(1, "AO_acc_relation.cid"));
				String sDOB = getFinalDataComparison(CHECKBOX_DOB_FCR,CHECKBOX_DOB_EIDA,CHECKBOX_DOB_MANUAL,FCR_DOB,EIDA_DOB,MANUAL_DOB)
						.trim().toUpperCase();
				formObject.setValue(PD_DOB,sDOB);
				String sQuery = "SELECT USERNAME,PERSONALNAME||' ' ||FAMILYNAME AS NAME FROM PDBUSER WHERE UPPER(USERNAME)= "
						+ "UPPER('"+sUserName+"')";
				List<List<String>> sOutput = formObject.getDataFromDB(sQuery);
				if(sOutput != null && sOutput.size() > 0){
					formObject.setValue("MAKER_CODE",sOutput.get(0).get(0));
					formObject.setValue("MAKER_NAME",sOutput.get(0).get(1));
				}
				clearUdfGrid();
				int custNo = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
				String sQuery2 = "SELECT GRADUATION_DATE, SPECIAL_CUSTOMER_IDENTIFIER FROM USR_0_UDF_DETAILS WHERE "
						+ "WI_NAME = N'"+sWorkitemId+"' AND ACC_RELATION_SERIALNO = '"+(custNo+1)+"'";
				populateUDFGrid(sQuery2);
				sQuery = "SELECT DEPARTMENT FROM WFFILTERTABLE WHERE UPPER(USERNAME) = UPPER('"+sUserName+"') AND PROCESSDEFID = '"+sProcessDefId+"'";				
				sOutput = formObject.getDataFromDB(sQuery);
				formObject.setValue("MAKER_DEPT",((sOutput != null && sOutput.size() > 0)  ? sOutput.get(0).get(0).toString() : ""));
				disableControls(new String[]{"NOM_REQ"});
				setRepeaterRowInOrder();
				//disable tab fb
				fbValidation();	//family banking
				populatePOANationality(); //Jamshed
				populateKycMultiDrop();
				additionalSource(); 
			}
			//			formObject.setNGVisible("Pic1", false);
			//setTabVisible();//done from front end- sheet handling of tab
			//formObject.setStyle("Frame4", false);  // not  able to find
			formObject.setStyle(SEC_OPT_PROD_CRO, DISABLE, TRUE);
			formObject.setStyle(SEC_DOC_REQ_CRO, DISABLE, TRUE);
			formObject.setStyle(SEC_CAT_CHNG, DISABLE, TRUE);
			String sQuery = "SELECT CALL_NAME FROM USR_0_INTEGRATION_CALLS WHERE WI_NAME = N'"+ sWorkitemId +"' AND STATUS='Success'";
			List<List<String>> sOutput = formObject.getDataFromDB(sQuery);
			String sCallName  = "";
			if(sOutput != null && sOutput.size() > 0) {
				sCallName  = sOutput.get(0).get(0);
			}
			if(formObject.getValue(CHECKBOX_FATCA).toString().equalsIgnoreCase(TRUE)) {
				FrameFATCA_CPD_Enable();
			}
			if(formObject.getValue(ED_SET_FLG).toString().equalsIgnoreCase("")) {
				formObject.setValue(ED_SET_FLG,"No");
			}
			if(decision.equalsIgnoreCase("Permanent Reject - Discard") 
					|| sCompDec.equalsIgnoreCase("Negative Advisory")) {
				formObject.applyGroup("CONTROL_SET_FORM");
				disableControls(new String[]{NEG_INFO,CRO_DEC});
				//				formObject.setStyle("AO_FATF", false);//to be checked by fardeen
				//				formObject.setStyle("AO_KYC", false);//to be checked by fardeen
				enableControls(new String[]{CRO_DEC,CRO_REMARKS,CRO_REJ_REASON,BUTTON_SUBMIT});
				//				formObject.setStyle("Command24", true);//Fardeen plz check
				frame81_CPD_Disable();
				manualFrameCPDDisable();//manual_frame_CPD_disable();
				//				formObject.setStyle("Frame4", false);//Fardeen plz check
				//				formObject.setNGLocked("Frame4", false);//fardeen plz check
				if(sActivityName.equalsIgnoreCase("DDE_Cust_Info"))	{
					Frame2_Disable();
					disableControls(new String[]{MANUAL_DOB,MANUAL_PASSPORTISSDATE,MANUAL_PASSPORTEXPDATE,MANUAL_PROFESSION,MANUAL_EMPLYR_NAME,ACC_INFO_PG,
							PD_ANY_CHNG_CUST_INFO,EXISTING_ETIHAD_CUST,GROUP_TYPE,NEW_LINK});
				} else if(ACTIVITY_DDE_ACCOUNT_INFO.equalsIgnoreCase(sActivityName)) {
					/*formObject.setStyle("Frame2", false);
					formObject.setNGLocked("Frame2", false);
					formObject.setStyle("Frame34",false);
					formObject.setNGLocked("Frame34", false);
					formObject.setStyle("Frame63",false);
					formObject.setNGLocked("Frame63", false);Sanal Fardeen unable to find 3 frames*/
					//					formObject.setStyle("AO_NOM_REQ",false);
					//					formObject.setNGLocked("AO_NOM_REQ",false);
					formObject.clearCombo(DFC_STATIONERY_AVAIL);//22/04/2016
					Frame_delivery();
					Frame48_CPD_Disable();
					disableControls(new String[]{SEC_CAT_CHNG,SEC_SI,SEC_DECISION_LAST,CRO_REMARKS});
				}
			}
			//Start Edit For Return To Originator Enable Change
			if((sCallName.contains("CUSTOMER_CREATION") || sCallName.contains("ACCOUNT_CREATION")) 
					&& sActivityName.equalsIgnoreCase("DDE_Account_Info")) {
				logInfo("onDDEFormLoad","Inside Returned Case After Account Creation or customer creation call.");
				formObject.setStyle(PRODUCT_QUEUE, "readonly", TRUE);
				//				formObject.setStyle("search", false);
				//				formObject.setStyle("add_product", false);
				//				formObject.setStyle("remove", false);
				formObject.setStyle(SEARCH, DISABLE, TRUE);
			}
			if(decision.equalsIgnoreCase("Permanent Reject - Discard") || sCompDec.equalsIgnoreCase("Negative Advisory")) {
				formObject.clearCombo(CRO_DEC);
				formObject.addItemInCombo(CRO_DEC,"Permanent Reject - Discard");
				formObject.setValue(CRO_DEC,"");
				enableControls(new String[]{CRO_REMARKS});
			}
			frame23_CPD_Disable();			
			setTemp_usr_0_product_selected();
			deleteECBCallsDetails();
			mp.clear();//Yamini please check this
			try {
				setRMCode();
				//setProfitCode(); //Sanal its empty in old code
			} catch (Exception e) {
				logError("onDDEFormLoad", e);
			}
			enableControls(new String[]{DRP_RESEIDA,RA_CARRYNG_EID_CARD});
			if(formObject.getValue(PD_ANY_CHNG_CUST_INFO).toString().equalsIgnoreCase("Yes")){
				String segment = formObject.getValue(PD_CUSTSEGMENT).toString();
				getPromoCode(segment);
			}
			if(formObject.getValue(CHK_EMP_DETAIL).toString().equalsIgnoreCase(TRUE)) {
				Frame27_CPD_ENable();
			} else {
				Frame27_CPD_Disable();
			}
			logInfo("LOAD","combo value : "+formObject.getValue(ED_CUST_CRS_BRDR_PAYMENT).toString());
			if(formObject.getValue(ED_CUST_CRS_BRDR_PAYMENT).toString().equalsIgnoreCase("Yes")) {
				logInfo("LOAD","enable combo value");
				String[] controls = {ED_CNTRY_PYMT_RECV,ED_PURPSE_CRS_BRDR_PAYMENT,ED_ANTCPATD_CRS_BRDER_PYMT,ED_ANTCPATD_MNTHVAL_BRDER_PYMT,ED_NO_UAE_OVRS_BRNCH};
				enableControls(controls);
			} else {
				logInfo("LOAD","disable combo value");
				formObject.setValue(ED_CNTRY_PYMT_RECV, "");
				formObject.setValue(ED_PURPSE_CRS_BRDR_PAYMENT, "");
				formObject.setValue(ED_ANTCPATD_CRS_BRDER_PYMT, "");
				formObject.setValue(ED_ANTCPATD_MNTHVAL_BRDER_PYMT, "");
				formObject.setValue(ED_NO_UAE_OVRS_BRNCH, "");
				String[] controls = {ED_CNTRY_PYMT_RECV,ED_PURPSE_CRS_BRDR_PAYMENT,ED_ANTCPATD_CRS_BRDER_PYMT,ED_ANTCPATD_MNTHVAL_BRDER_PYMT,ED_NO_UAE_OVRS_BRNCH};
				disableControls(controls);
			}
			if (formObject.getValue(RESIDENCY_STATUS).toString().equalsIgnoreCase("")) {
				calculateResidencyStatus(RESIDENCY_STATUS);
			}
			if(formObject.getValue(SALARY_TRANSFER).toString().equals("")){
				formObject.setValue(SALARY_TRANSFER,"No");
			}
			checkCRS();
			LoadInstantDelivery();
			populateStndInstr();
			logInfo("onDDEFormLoad", "DDE Form Load Complete.");
		} catch(Exception e) {
			logError("onDDEFormLoad", e);
		}
	}

	private void onTabClick(String data) {
		try {
			logInfo("onTabClick", "INSIDE");
			String[] selectedSheetInfo = data.split(",");
			String tabCaption = selectedSheetInfo[0];
			int selectedSheetIndex = Integer.parseInt(selectedSheetInfo[1]);
			String sQuery = "";
			String sCurrentDate = "";
			try {
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat simpledateformat = new SimpleDateFormat(DATEFORMAT);
				sCurrentDate = simpledateformat.format(calendar.getTime());
				logInfo("onDDEFormLoad", "scurrentDate: "+sCurrentDate);
			} catch(Exception ex) {
				logError("onDDEFormLoad",ex);
			}
			List<List<String>> result;
			logInfo("onTabClick", "activity: "+sActivityName+", tabCaption: "+tabCaption+", selectedSheetIndex: "
					+selectedSheetIndex);
			if(selectedSheetIndex == 0 || selectedSheetIndex == 1 || selectedSheetIndex == 2 || selectedSheetIndex == 3 
					|| selectedSheetIndex == 4 || selectedSheetIndex == 5) {
				logInfo("onTabClick", "when selected sheet is 1: "+tabCaption);
				if(ACTIVITY_DDE_ACCOUNT_INFO.equalsIgnoreCase(sActivityName)) {
					frameFatcaCpdDisable();//FrameFATCA_CPD_Disable renamed to frameFatcaCpdDisable
					//clearComparisonFields();//moved to js
					//clearPersonalData();//moved to js fardeen to check the sequence
					//clearKYCData();//moved to js fardeen to check the sequence
					//clearRiskData();
					setDDEModeCombos();
					resetRekey();
					populateComparisonFields();
					populatePersonalData();
					populateRiskData();
					populateKYCData();  		
					populateKycMultiDrop();
					populatePreAssesmentDetails();  //shahbaz
					populatePrivateClientQuestions();  
					populatePepAssesmentDetails(); //AO DCRA
					loadDedupeSearchData(sWorkitemId);
					int iProcessedCustomer = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
					String name = formObject.getTableCellValue(ACC_RELATION, iProcessedCustomer, 1);
					String dob = formObject.getTableCellValue(ACC_RELATION, iProcessedCustomer, 5);
					String cust_id = formObject.getTableCellValue(ACC_RELATION, iProcessedCustomer, 2);
					if(!name.trim().isEmpty()) {
						formObject.setValue(TXT_CUSTOMERNAME, name);
					}
					if(!dob.trim().isEmpty()) {
						formObject.setValue(TXT_DOB, dob);
					}
					if(!cust_id.trim().isEmpty()) {
						formObject.setValue(TXT_CUSTOMERID, cust_id);
					}
					populatePOANationality(); //Jamshed
				} else if(ACTIVITY_DDE_CUST_INFO.equalsIgnoreCase(sActivityName)) {
					logInfo("onTabClick", "ACTIVITY_DDE_CUST_INFO");
					setPermCntry();
					setResCntry();
					setCorrCntry();
					enableFATCACPD();//FrameFATCA_CPD_Enable();
					loadDedupeSearchData(sWorkitemId);
					manageInternalSection();						
					int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
					String sBankRelation = formObject.getTableCellValue(ACC_RELATION, fieldToFocus, 7);
					String sAccRelation = formObject.getTableCellValue(ACC_RELATION, fieldToFocus, 9);
					manageCustomerChangeCheckboxes(sBankRelation,sAccRelation);
					manageCustomerDetailChange(formObject.getValue(PD_ANY_CHNG_CUST_INFO).toString());
					logInfo("onTabClick", "cust_prefix");
					if(!formObject.getValue(CUST_PREFIX).toString().equalsIgnoreCase("Others")){
						formObject.setStyle(PD_OTHERS, DISABLE, TRUE);
					}
					if(!formObject.getValue(MARITAL_STATUS).toString().equalsIgnoreCase("Others")){
						formObject.setStyle(PD_MARITALSTATUSOTHER, DISABLE, TRUE);
					}
					if(!formObject.getValue(EMPNAME).toString().equalsIgnoreCase("Others")){
						formObject.setStyle(ED_EMPNAME, DISABLE, TRUE);
					}
					if(!formObject.getValue("profesion").toString().equalsIgnoreCase("Others")){
						formObject.setStyle(ED_OTHER, DISABLE, TRUE);
					}
					if("Existing".equalsIgnoreCase(sAccRelation)) {
						Frame22_CPD_Disable();
					}
					if(formObject.getValue(FATCAMINI).toString().isEmpty() && "New".equalsIgnoreCase(sBankRelation)) {
						formObject.setStyle(BTN_VALIDATE, DISABLE, FALSE);
					}
					String[] disable = {RELIGION, MARITAL_STATUS, ED_CB_TML, ED_CB_NON_TML};
					disableControls(disable);
					if(FALSE.equalsIgnoreCase(formObject.getValue(CHECKBOX_STATE_MANUAL).toString())) {
						formObject.setStyle(MANUAL_STATE, DISABLE, TRUE);
					}
					if(formObject.getValue(ED_MONTHLY_INCM).toString().isEmpty()) {
						formObject.setValue(ED_MONTHLY_INCM,"0");
					}
					if(FALSE.equalsIgnoreCase(formObject.getValue(CHECKBOX_SELECTALL_MANUAL).toString())) {
						setManualFieldsLock();
					}
					try {
						//shahbaz
						String bank_Relationship = formObject.getTableCellValue(ACC_RELATION, 0, 7); 
						logInfo("populateLastKycDate ", "bank_Relationship: " + bank_Relationship);
						
						if(!bank_Relationship.equalsIgnoreCase("Existing")){
							if(formObject.getValue(GI_DATE_KYC_PREP).toString().isEmpty()) {
								formObject.addItemInCombo(GI_DATE_KYC_PREP, sCurrentDate);
								formObject.setValue(GI_DATE_KYC_PREP, sCurrentDate);
							}
						}
					} catch(Exception ex) {
						logError("onTabClick", ex);
					}
					//formObject.NGFocus(MANUAL_PREFIX);
					if(FALSE.equalsIgnoreCase(formObject.getValue(CK_PER_DET).toString())) {
						logInfo("onTabClick","INSIDE IF RESIDENT");
						formObject.setStyle(DRP_RESEIDA, DISABLE, TRUE);					
					} else if("Residency Visa".equalsIgnoreCase(returnVisaStatus())) {
						logInfo("onTabClick","INSIDE ELSE IF RESIDENT");
						formObject.setStyle(DRP_RESEIDA, DISABLE, FALSE);
					} else {
						logInfo("onTabClick","INSIDE ELSE RESIDENT");
						formObject.setStyle(DRP_RESEIDA, DISABLE, TRUE);	
						formObject.setValue(DRP_RESEIDA,"");
					}
					//formObject.setStyle(MANUAL_EIDANO, DISABLE, FALSE);
					if(selectedSheetIndex == 4) {
						if(formObject.getValue(CRS_CERTIFICATION_OBTAINED).toString().equalsIgnoreCase("Yes")) {
							formObject.setValue(CRS_CLASSIFICATION,"UPDATED-DOCUMENTED");
						} 
					}
					if(selectedSheetIndex == 2) {
						setEIDAInPersonalAndKYCTab();
						verifyChequeBook();
						String proCode = formObject.getValue(PRO_CODE).toString();
						getPromoCode(formObject.getValue(PD_CUSTSEGMENT).toString());
						if(!proCode.isEmpty()) {
							formObject.setValue(PRO_CODE, proCode);
						}
					}
				}
				checkView();
				saveKYCInfo();
//				saveKycMultiDropDownData();
//				savePepAssessmentDetails();  //Ao Dcra
//				savePreAssessmentDetails();    //shahbaz
				saveComparisonData();
				saveIndividualInfo();
				saveIndividualContactInfo();
				//				saveDuplicateData(); //empty in old code	
				saveClientQuestionsData(); 
				saveCRSDetails(); 
			} else if(selectedSheetIndex == 6) {	
				populateTRSD();
				if("Approved".equalsIgnoreCase(formObject.getValue("trsd_approvedResult").toString())) {
					formObject.setValue(ED_CB_NON_TML, "Eligible");// sanction
				} else {
					formObject.setValue(ED_CB_NON_TML, "Not Eligible");// sanction
				}
				if(ACTIVITY_DDE_ACCOUNT_INFO.equalsIgnoreCase(sActivityName)) {
					setDDEModeCombos();
					formObject.setStyle(TRSD_CHECK, DISABLE, TRUE);
					populateScreeningDataCRO();
				}
				formObject.setStyle(CALCULATE, DISABLE, TRUE);
			} else if(selectedSheetIndex == 7) {	
				loadApplicationAssessmentData();
				if(getGridCount(FAC_OFRD_LVW_CRO) == 0) {
					String sQuery3  =  "SELECT CUST_ID,CUST_NAME,CARD_TYPE FROM USR_0_DEBITCARD_OFFERED WHERE WI_NAME = '"+sWorkitemId+"'";		
					List<List<String>> recordList = formObject.getDataFromDB(sQuery3);
					log.info(sQuery3);
					loadListView(recordList,"CUST_ID,CUST_NAME,CARD_TYPE",FAC_OFRD_LVW_CRO);
				}
				if(getGridCount (PROD_OFRD_CRO_LVW)  == 0) {
					String sQuery4  =  "SELECT PRODUCT_CODE,PRODUCT_DESC,CURRENCY_DESC,'' FROM USR_0_PRODUCT_OFFERED WHERE WI_NAME = '"+sWorkitemId+"' ORDER BY TO_NUMBER(PRODUCT_CODE)";				
					List<List<String>> recordList = formObject.getDataFromDB(sQuery4);
					log.info(sQuery4);
					loadListView(recordList,"PRODUCT_CODE,PRODUCT_DESCRIPTION,CURRENCY,PROD_ACC_OPENING",PROD_OFRD_CRO_LVW);
				}
				if(getGridCount(FAC_LVW_CRO) == 0) {
					String sQuery5 = "SELECT CID,FACILITY,DESCRIPTION FROM USR_0_FACILITY_SELECTED WHERE WI_NAME = '"+sWorkitemId+"' ORDER BY FACILITY";
					List<List<String>> recordList = formObject.getDataFromDB(sQuery5);
					log.info(sQuery5);
					loadListView(recordList,"CID,FACILITY,DESCRIPTION",FAC_LVW_CRO);
				}
			} else if(selectedSheetIndex == 8) {
				clearControls(new String[] {GROUP_TYPE, CARD_TYPE});
				populatePrivateClientQuestions();//PopulatePrivateClientQuestions(); 
				LockChequebookField();
				LockCreatedAccountRows();
				EnableEtihadFrame();
				CalculateAccTitle();
				int iRows = getGridCount(ACC_RELATION);
				String sLockDebitCard= FALSE;
				for(int iLoop=1; iLoop < iRows; iLoop++) {
					if("JAF".equalsIgnoreCase(formObject.getTableCellValue(ACC_RELATION, iLoop, 9))) {
						sLockDebitCard =TRUE;
						break;
					}					
					if("JAO".equalsIgnoreCase(formObject.getTableCellValue(ACC_RELATION, iLoop, 9))) {
						sLockDebitCard =TRUE;
						break;
					}
				}
				String sPrimaryCust = getPrimaryCustomerSNO();
				sQuery = "SELECT SIGN_STYLE FROM USR_0_CUST_TXN WHERE WI_NAME ='"+sWorkitemId+"' AND "
						+ "CUST_SNO ='"+sPrimaryCust+"'";
				result = formObject.getDataFromDB(sQuery);
				if(result.get(0).get(0).indexOf("Sign") == -1) {
					sLockDebitCard =TRUE;
				}
				if(TRUE.equalsIgnoreCase(sLockDebitCard)) {
					formObject.setStyle(SEC_ACC_INFO_DCL, DISABLE, TRUE);
				} else {
					formObject.setStyle(SEC_ACC_INFO_DCL, DISABLE, FALSE);
					LoadDebitCardCombo();
				}
				if("Yes".equalsIgnoreCase(formObject.getValue(EXISTING_ETIHAD_CUST).toString())) {
					String[] enable = {ETIHAD_NO, BTN_ECD_VALIDATE};
					enableControls(enable);
				} else {
					formObject.setValue(ETIHAD_NO,"");
					String[] disable = {ETIHAD_NO, BTN_ECD_VALIDATE};
					disableControls(disable);
				}
			} else if(selectedSheetIndex == 9) {
				formObject.setStyle("Frame41", DISABLE, TRUE);//opt tab full - frame41
//				formObject.setTabStyle(arg0, arg1, arg2, arg3);
			} else if(selectedSheetIndex == 16) {
				fbValidation();
				clearFBData();
			}else if(selectedSheetIndex == 11) {//added by krishna
				if(sActivityName.equalsIgnoreCase(ACTIVITY_DDE_ACCOUNT_INFO)) {	
					logInfo("inside category change ","tab details: ");
					if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade")){
						formObject.setStyle(OLD_CUST_SEGMENT,DISABLE, TRUE);
						}
					loadNewCust();
					}
			}
			if(tabCaption.equalsIgnoreCase("Delivery Preferences")) {
				int iCount = getListCount(EXISTING_NOM_PRSN);
				if(iCount == 0 || iCount == 1) {
					//to be added on form check
					/*	formObject.getNGDataFromDataSource("Select COUNTRY from usr_0_country_mast order by 1",1,DEL_CNTRY);
					addItemsDropDown(DEL_CNTRY,DEL_CNTRY);
					formObject.getNGDataFromDataSource("Select delivery_mode from usr_0_delivery_mode order by 1",1,DEL_DELIVERY_MODE);
					addItemsDropDown(DEL_DELIVERY_MODE,DEL_DELIVERY_MODE);
					formObject.getNGDataFromDataSource("Select delivery_mode from usr_0_delivery_mode order by 1",1,NEW_DEL_MODE);
					addItemsDropDown(NEW_DEL_MODE,NEW_DEL_MODE);
					formObject.getNGDataFromDataSource("Select YES_NO from USR_0_YES_NO order by 1",1,NOM_REQ);
					addItemsDropDown(NOM_REQ,NOM_REQ);
					//formObject.getNGDataFromDataSource("Select to_char(YES_NO) from USR_0_YES_NO",1,INSTANT_DEL_NO);
					addItemsDropDown(NOM_REQ,INSTANT_DEL_NO);
					formObject.getNGDataFromDataSource("Select Branch from usr_0_brnch_of_instant_issue order by 1",1,BRNCH_OF_INSTANT_ISSUE);
					addItemsDropDown(BRNCH_OF_INSTANT_ISSUE,BRNCH_OF_INSTANT_ISSUE);
					formObject.getNGDataFromDataSource("Select STATE from usr_0_state_mast order by 1",1,DEL_STATE);
					addItemsDropDown(DEL_STATE,DEL_STATE); */
				}
				setMailingAddInToDel();
				Frame_delivery();
			} else if(tabCaption.equalsIgnoreCase("Standing Instruction")) {
				if(getListCount(SI_PERIOD)==0 || getListCount(SI_PERIOD)==1) {
					//to be added on form check
					/*	formObject.getNGDataFromDataSource("Select to_char(period) from usr_0_si_period order by UNIQUE_ID",1,SI_PERIOD);
					String sQuery="Select CURRENCY from USR_0_CURRENCY order by 1";
					formObject.getNGDataFromDataSource(sQuery,1,SI_CURRENCY); */
					//to be added on form check
					/*		addItemsDropDown(SI_CURRENCY,SI_CURRENCY);
					addItemsDropDown(SI_CURRENCY,SWP_OUT_CURRENCY);
					addItemsDropDown(SI_CURRENCY,SWP_IN_CURRENCY);*/
				}
				loadSICombos();//LoadSICombos();
				populateStndInstr();//PopulateStndInstr();
				if(sActivityName.equalsIgnoreCase(ACTIVITY_DDE_ACCOUNT_INFO)) {
					fbValidation();	//family  banking tab change check
				}
			} else if(tabCaption.equalsIgnoreCase("Document Generation")) {
				logInfo("onTabClick","####### rEFRESH BUTTON CLICK ###########");
				int iRows = getGridCount("frame58");
				logInfo("onTabClick","####### Row count in grid ###########"+iRows);
				for(int k=1; k<iRows; k++) {
					if("Y".equalsIgnoreCase(formObject.getTableCellValue("frame58", k,"TEMP_GEN_QUEUE.FTP_STATUS").toString())) {
						logInfo("onTabClick","#######enabling buttons###########");
						//objChkRepeater1.setEnabled(k, "Command25", true); check in js
					}
				}
			} else if(tabCaption.equalsIgnoreCase("Category Change")) {					
				String sReqType = formObject.getValue(REQUEST_TYPE).toString();
				String  privateClient ="";
				String Query ="select private_client from ext_ao where wi_name='"+sWorkitemId+"'";
				logInfo("On load QDE_Cust_Info","pri_query= "+ Query);
				List<List<String>> List =formObject.getDataFromDB(Query); 
				if (List != null && List.size() > 0) {
					privateClient =List.get(0).get(0); 
					logInfo("On load QDE_Cust_Info","private_clnt_flg= "+ privateClient);
				}if (sReqType.equalsIgnoreCase("New Account With Category Change") && privateClient.equalsIgnoreCase("Y")) {
					//	sQuery ="SELECT  RM_NAME, RM_CODE FROM USR_0_CUST_TXN WHERE WI_NAME ='"+sWorkitemId+"' "
					//			+ "AND CUST_SNO='1'";
					sQuery ="SELECT RM_NAME,RM_CODE FROM USR_0_CUST_TXN WHERE WI_NAME "
							+ "='"+sWorkitemId+"' AND CUST_SNO=(SELECT SELECTED_ROW_INDEX FROM EXT_AO WHERE "
			       			 + " WI_NAME = '"+sWorkitemId+"') + 1";
				}
				else {
					sQuery ="SELECT CUST_SEG, RM_NAME, RM_CODE FROM USR_0_CUST_TXN WHERE WI_NAME ='"+sWorkitemId+"' AND CUST_SNO=(SELECT SELECTED_ROW_INDEX FROM EXT_AO WHERE "
							+ " WI_NAME = '"+sWorkitemId+"') + 1";
				}
				result = formObject.getDataFromDB(sQuery);
				logInfo("onTabClick","sOutput----"+result.toString());
				formObject.addItemInCombo(OLD_RM_NAME_CAT_CHANGE, result.get(0).get(1));
				formObject.addItemInCombo(OLD_RM_CODE_CAT_CHANGE, result.get(0).get(2));
				String newCust= result.get(0).get(0);//Added by krishna
				if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade")
				&& newCust.equalsIgnoreCase("Private Clients")){
					formObject.setStyle(NEW_CUST_SEGMENT,DISABLE, TRUE);
				}
				if (sReqType.equalsIgnoreCase("New Account With Category Change") && privateClient.equalsIgnoreCase("Y")) {
					String[] listOfColumns = {OLD_RM_NAME_CAT_CHANGE, OLD_RM_CODE_CAT_CHANGE};
					setValuesFromDB(result, listOfColumns);
					logInfo("OLD_CUST_SEGMENT","OLD_CUST_SEGMENT= "+result);
				}else {
				String[] listOfColumns = {OLD_CUST_SEGMENT, OLD_RM_NAME_CAT_CHANGE, OLD_RM_CODE_CAT_CHANGE};
				setValuesFromDB(result, listOfColumns);
					logInfo("OLD_CUST_SEGMENT","OLD_CUST_SEGMENT= "+result);
				}
				formObject.setValue(MAKER_DEPARMENT_CAT_CHANGE, formObject.getValue(MAKER_DEPT).toString());
				formObject.setValue(MAKER_NAME_CAT_CHANGE, formObject.getValue(MAKER_NAME).toString());
				formObject.setValue(MAKER_CODE_CAT_CHANGE, formObject.getValue(MAKER_CODE).toString());
				String sCID = formObject.getTableCellValue(ACC_RELATION, 1, 2);
				sQuery = "SELECT MAX(CATEGORY_CHANGE_DATE) AS CATEGORY_CHANGE_DATE FROM USR_0_CUST_TXN "
						+ "WHERE CUST_ID ='"+sCID+"'";
				result = formObject.getDataFromDB(sQuery);
				formObject.setValue(LAST_CAT_CAHNGE_DATE, result.get(0).get(0));
				if(sReqType.equalsIgnoreCase("New Account with Category Change")) {
					formObject.setValue(SOURCE_NAME_CAT_CHANGE, formObject.getValue(SOURCE_NAME).toString());
					formObject.setValue(SOURCE_CODE_CAT_CHANGE,formObject.getValue(SOURCE_CODE).toString());
					String[] disable = {SOURCE_NAME_CAT_CHANGE, SOURCE_CODE_CAT_CHANGE, SRC_NAME_BTN};
					disableControls(disable);
				}
				if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only") && 
						formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade")) {
					formObject.setStyle(SEC_SI, DISABLE, TRUE);
				}
				if(FALSE.equalsIgnoreCase(formObject.getValue(IS_CAT_BENEFIT_OTHER).toString())) {
					String[] disable = {IS_PROD_APP_REQ, IS_CAT_BENEFIT_OTHER};
					disableControls(disable);
					if(!formObject.getValue(NEW_CUST_SEGMENT).toString().isEmpty()) {
						formObject.setStyle(IS_CAT_BENEFIT_OTHER, DISABLE, FALSE);
						manageCategoryChangeSection();		
					} else {
						String[] disable1 = {IS_SALARY_TRANSFER_CAT_CHANGE, IS_MORTAGAGE_CAT_CHANGE, 
								IS_INSURANCE_CAT_CHANGE, IS_TRB_CAT_CHANGE, IS_OTHERS_CAT_CHANGE, 
								IS_VVIP, IS_PREVILEGE_TP_CAT_CHANGE, IS_ENTERTAINMENT_CAT_CHANGE, 
								IS_SHOPPING_CAT_CHANGE, IS_SPORT_CAT_CHANGE, IS_TRAVEL_CAT_CHANGE, 
								IS_EXCELLENCY_TP_CAT_CHANGE};
						disableControls(disable1);
					}
				} else {
					String[] disable1 = {IS_PROD_APP_REQ, IS_CAT_BENEFIT_OTHER};
					disableControls(disable1);
					logInfo("onTabClick","calling manageCategoryChangeSection");
					manageCategoryChangeSection();
				}
				if(formObject.getValue(NEW_CUST_SEGMENT).toString().isEmpty()) {
					formObject.clearCombo(PROMO_CODE_CAT_CHANGE);
					formObject.clearCombo(EXCELLENCY_CENTER_CC);
				}
			} else if(tabCaption.equalsIgnoreCase("Decision")) {
				if(ACTIVITY_DDE_ACCOUNT_INFO.equalsIgnoreCase(sActivityName)) {
					sQuery = "select count(WI_NAME) as count from USR_0_RISK_ASSESSMENT_DATA where "
							+ "approval_req='Yes' and wi_name='"+sWorkitemId+"'";
					result = formObject.getDataFromDB(sQuery);
					String count = result.get(0).get(0);
					try {
						if(Integer.parseInt(count) > 0) {
							formObject.setValue(IS_COMPLIANCE_RISK_ASSESS, TRUE);
						} else {
							formObject.setValue(IS_COMPLIANCE_RISK_ASSESS, FALSE);
						}
					} catch(Exception e) {
						logError("onTabClick", e);;
					}
					sQuery = "SELECT COUNT(WI_NAME) AS COUNT FROM USR_0_CUST_TXN WHERE (BANK_DEC != 'Approved' AND "
							+ "BANK_DEC IS NOT NULL) AND WI_NAME ='"+sWorkitemId+"'";
					result = formObject.getDataFromDB(sQuery);
					count = result.get(0).get(0);
					if(Integer.parseInt(count) > 0) {
						formObject.setValue(IS_COMPLIANCE_NAME_SCR, TRUE);
					} else {
						formObject.setValue(IS_COMPLIANCE_NAME_SCR, FALSE);
					}
					if(TRUE.equalsIgnoreCase(formObject.getValue(MOBILE_CHANGE_FLAG).toString())) {
						formObject.setValue(IS_CALL_BACK_REQ, TRUE);
					} else {
						formObject.setValue(IS_CALL_BACK_REQ, FALSE);
					}
				}
				String[] disable = {IS_COMPLIANCE_NAME_SCR, IS_COMPLIANCE_RISK_ASSESS, IS_PROD_APP_REQ, 
						IS_CALL_BACK_REQ};
				disableControls(disable);
				int iCount = getListCount(CRO_DEC);
				//on form check
				/*		if(iCount == 0 || iCount == 1) {
					formObject.getNGDataFromDataSource("Select '' from dual Union Select to_char(WS_DECISION) from USR_0_DECISION_MASTER where ws_name='"+formObject.getValue("CURR_WS_NAME")+"'",1,CRO_DEC);
				}
				iCount =formObject.getNGListCount(CRO_REJ_REASON);
				if(iCount == 0 || iCount == 1) {
					formObject.getNGDataFromDataSource("Select '' from dual Union Select to_char(ws_rej_reason) from usr_0_rej_reason_mast",1,CRO_REJ_REASON);
				}
				if(formObject.getNGListCount("LISTVIEW_DECISION") == 0) {
					List<List<String>> sOutput = formObject.getDataFromDB(arg0);
					LoadListView("SELECT CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,CHANNEL,WS_NAME,WS_COMMENTS FROM (SELECT TO_CHAR(CREATE_DAT,'DD/MM/YYYY HH:MI:SS AM')CREATE_DAT, USERID, USERNAME, GROUP_NAME, WS_DECISION,REJ_REASON, CHANNEL, WS_NAME, WS_COMMENTS,TO_CHAR(CREATE_DAT,'YYYYMMDDHH24:MI:SS') A FROM USR_0_DEC_HIST WHERE WI_NAME = '"+sWorkitemId+"') ORDER BY A",9,"LISTVIEW_DECISION","0,1,2,3,4,5,6,7,8");							
				} */
			} else if(tabCaption.equalsIgnoreCase("Family Banking")) {
				clearFBData();//Clearing FB tab on category change
			}
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
	public String getCustomFilterXML(FormDef arg0, IFormReference arg1,	String arg2) {
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
		//blank
	}

	@Override
	public String validateDocumentConfiguration(String arg0, String arg1, File arg2, Locale arg3) {
		return null;
	}

	@Override
	public JSONArray validateSubmittedForm(FormDef arg0, IFormReference arg1, String arg2) {
		return null;
	}

	private void setDefaultPermanentField(){
		log.info("[Inside setDefaultPermanentField]");
		setDefaultValue(SET_PERMANENT_DETAILS_CONTROLS_LIST);
	}

	private void setDefaultValue(String[] controlNames) {
		for (int i = 0; i < controlNames.length; i++) {
			if(formObject.getValue(controlNames[i]).toString().isEmpty()){
				formObject.setValue(controlNames[i], "N/A");
			}
		}
	}

	public void frameFatcaCpdDisable() {
		logInfo("frameFatcaCpdDisable","INSIDE");
		String[] listOfFields = {CNTRY_OF_BIRTH,FAT_US_PERSON,FAT_LIABLE_TO_PAY_TAX,FAT_SSN,FAT_CUST_CLASSIFICATION,POACOMBO,
				COMBODOC,INDICIACOMBO,DATEPICKERCUST,DATEPICKERW8,BTN_VALIDATE,BTN_VALIDATEFATCA};//CNTRY_OF_BIRTH TBC SANAL
		disableControls(listOfFields);	
	}


	

	/*public void LoadDebitCardCombo() {
		HashMap<String , String> hmap = new HashMap<String, String>();
		try	{
			int iRows = getGridCount(PRODUCT_QUEUE);
			formObject.clearCombo(ACC_INFO_PG);
			String sQuery = "";
			List<List<String>> sOutput = null;
			String sAllProduct = "";
			String sPrimaryCust = getPrimaryCustomerSNO();
			sQuery = "SELECT CUST_SEG,DECODE(STAFF_FLAG,'Yes','Y','N') AS STAFF_FLAG FROM USR_0_CUST_TXN WHERE CUST_SNO = '"+sPrimaryCust+"' AND WI_NAME ='"+sWorkitemId+"'";
			sOutput = formObject.getDataFromDB(sQuery);
			String sSegment = sOutput.get(0).get(0);//"CUST_SEG");
			String sStaff = sOutput.get(0).get(1);//"STAFF_FLAG");

			if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("New Account With Category Change")) {
				sSegment = formObject.getValue(NEW_CUST_SEGMENT).toString();
			}

			for(int i=1;i<iRows;i++) {
				sQuery = "SELECT PRODUCT_DESC,PRODUCT_CODE FROM USR_0_PRODUCT_MASTER WHERE currency_code='"+formObject.getTableCellValue(PRODUCT_QUEUE, i, "CURRENCY").toString()+"' and PRODUCT_CODE = '"+formObject.getTableCellValue(PRODUCT_QUEUE, i, "PROD_CODE").toString()+"' AND ATM_FLAG='Y' AND CURRENCY_CODE IN (SELECT DISTINCT CURRENCY_CODE  FROM USR_0_DEBITCARD_MASTER WHERE PROCESS_TYPE ='Onshore' AND CUST_CATEGORY ='"+sSegment+"' AND STAFF_FLAG = '"+sStaff+"') and rownum=1 ORDER BY TO_NUMBER(PRODUCT_CODE)";
				sOutput = formObject.getDataFromDB(sQuery);
				System.out.println("sOutput----- mohit 14022017...."+sOutput);
				if(sOutput.size() > 0) {
					formObject.addItemInCombo(ACC_INFO_PG,sOutput.get(0).get(0)+"_"+sOutput.get(0).get(1)+"_"+i);
				}
			}

			formObject.setValue(ACC_INFO_PG,"");

			//	objChkRepeater = formObject.getNGRepeater("queue_dc");
			iRows = getGridCount(QUEUE_DC);

			for(int i=1;i<iRows;i++)
			{
				//yamini check in js
				
				objChkRepeater.setEditable(i,"QUEUE_DC.GROUP_TYPE",false);     
				objChkRepeater.setEditable(i,"QUEUE_DC.CARD_TYPE",false);
				objChkRepeater.setEditable(i,"QUEUE_DC.PROD_GRP",false);
				objChkRepeater.setEditable(i,"QUEUE_DC.EMBOSS_NAME",false);
				objChkRepeater.setEditable(i,"QUEUE_DC.NEW_LINK",false);
				objChkRepeater.setEditable(i,"QUEUE_DC.EXISTING_CARD_NO",false);
				objChkRepeater.setEditable(i,"QUEUE_DC.CARD_NO",false); 
			}
		} catch(Exception e) {
			e.printStackTrace();
		}  finally {
			hmap.clear();
		}
	}*/

	public int setDedupeView(){
		List<List<String>> sOutput;
		int index1 = -1;
		try {
			logInfo("setDedupeView","inside view 5");
			formObject.clearTable(LVW_DEDUPE_RESULT);
			int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
			String sQuery= "SELECT CUST_ID,CUST_NAME,CUST_IC,CUST_PASSPORT,CUST_EMAIL,CUST_MOBILE,'','',"
					+ "to_date(CUST_DOB,'"+DATEFORMAT+"'),"
					+ "CUST_EIDA,CUST_NATIONALITY,system_type FROM USR_0_DUPLICATE_SEARCH_DATA"
					+ " WHERE WI_NAME='"+sWorkitemId+"' AND CUST_SNO ='"+iSelectedRow+"'";
			sOutput = formObject.getDataFromDB(sQuery);
			loadListView(sOutput, COLUMNS_LVW_DEDUPE_RESULT, LVW_DEDUPE_RESULT);
			String sQuery1 = "select dedupe_selected_index from USR_0_CUST_TXN Where WI_NAME='"+ sWorkitemId +"'"
					+ " and cust_sno='"+iSelectedRow+"'";
			logInfo("setDedupeView","sQuery1@@@@"+sQuery1);
			sOutput = formObject.getDataFromDB(sQuery1);	
			logInfo("setDedupeView",sOutput.toString());
			index1 = Integer.parseInt(sOutput.get(0).get(0));
			getReturnMessage(true, sOutput.get(0).get(0), "DedupeSelectedIndex");
		} catch (NumberFormatException e) {
			logError("setDedupeView",e);
		}
		return index1;
	}

	public void Frame2_Disable() { 
		disableControls(new String[]{CRO_DEC,CRO_REJ_REASON,CRO_REMARKS});		
	}

	public void clearUdfGrid() {
		formObject.clearTable("AccInfo_UdfList");
	}
	
	private boolean submitDDEValidations(String data) {
		logInfo("submitDDEValidations",	"INSIDE");
		if(formObject.getValue(IS_ETIHAD).toString().equalsIgnoreCase("1")) {
			updateDataInDB(sExtTable,"ETIHAD_UPDATE_REQ","Yes","WI_NAME ='"+sWorkitemId+"'");
		}
		List<List<String>> output;
		if(sActivityName.equalsIgnoreCase(ACTIVITY_DDE_CUST_INFO)) {
			if(sBackRoute.equalsIgnoreCase("True")) {
				return true;
			}
			if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Reject")) {
				if(formObject.getValue(CRO_REJ_REASON).toString().equalsIgnoreCase("")) {
					sendMessageValuesList(CRO_REJ_REASON, "Please select rejection reason.");
					formObject.setStyle(BTN_SUBMIT, DISABLE, FALSE);
					return false;
				} else if(formObject.getValue(CRO_REMARKS).toString().equalsIgnoreCase("")) {
					sendMessageValuesList(CRO_REMARKS,"Please enter remarks.");
					formObject.setStyle(BTN_SUBMIT, DISABLE, FALSE);
					return false;
				} else {
					createHistory();
					createAllHistory();
					updateProfitCentre();
					return true;
				}
			}
			try {
				String sQuery1="SELECT COUNT(A.WI_NAME) IS_MOB_CHANGE FROM USR_0_CUST_TXN A, ACC_RELATION_REPEATER B "
						+ "WHERE A.WI_NAME='"+ sWorkitemId +"' AND A.WI_NAME=B.WI_NAME  AND A.CUST_SNO =B.SNO AND "
						+ "B.BANK_RELATION='Existing' AND A.final_mobile_no <> nvl(A.fcr_mobile_no,0) and "
						+ "a.final_mobile_no <> nvl(a.AFTER_CONT_CNTR_MOB_NO,0)";
				output = formObject.getDataFromDB(sQuery1);
				String isMobChange = output.get(0).get(0);
				if(Integer.parseInt(isMobChange)>0) {
					formObject.setValue(MOBILE_CHANGE_FLAG, "True");
				} else {
					formObject.setValue(MOBILE_CHANGE_FLAG, "False");
				}
			} catch(Exception e) {
				logError("onSubmitDDE", e);
			}
			reKeyInsert();
			updateReKeyTemp("CRO");
			return true;
		} else if(sActivityName.equalsIgnoreCase(ACTIVITY_DDE_ACCOUNT_INFO)) {	
			refreshDocRepeater();
			if(!sBackRoute.equalsIgnoreCase("True")) {
				if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("")) {
					sendMessageValuesList(CRO_DEC,"Please select user decision.");
					formObject.setStyle(BTN_SUBMIT, DISABLE, FALSE);
					return false;
				}						
				String sQuery = "";
				if(!formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Permanent Reject - Discard")) {
					output = formObject.getDataFromDB("SELECT ACCOUNT_INFO_CHECKER_DEC FROM "+sExtTable+" WHERE "
							+ "WI_NAME ='"+sWorkitemId+"'");
					if(output.size() > 0 && null != output.get(0)) {
						if(output.get(0).get(0).equalsIgnoreCase("Permanent Reject - Discard")) {
							sendMessageValuesList(CRO_DEC,"Please select decision Permanent Reject as CRM decision is Permanent Reject");
							formObject.setStyle(BTN_SUBMIT, DISABLE, FALSE);
							return false;
						}
						if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("New Account with Category Change") 
								|| formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only")
								|| formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade")) {
							boolean result = mandatoryCategoryChangeData();
							if(!result) {
								formObject.setStyle(BTN_SUBMIT, DISABLE, FALSE);
								return false;
							}
							result = checkNatCatSegment();
							if(!result) {
								formObject.setStyle(BTN_SUBMIT, DISABLE, FALSE);
								return false;
							}
							/*if(validateFBFetch()) { // family Banking submit validation
								   isValidateFBDone();
							}*///FB SUPPRESSED
							if(validateFBFetch()) { // family Banking submit validation
								   isValidateFBDone();
							}//FB ADDED
						if (formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade")) {
							if(!checkMandatoryDoc(data)) {
								formObject.setStyle(BTN_SUBMIT, DISABLE, FALSE);
								return false;
							}
						  }
						}
					}
				}
				if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Permanent Reject - Discard")) {
					String rejectionReason = formObject.getValue(CRO_REJ_REASON).toString();
					String rejectionRemarks = formObject.getValue(CRO_REMARKS).toString();
					if(rejectionReason.equalsIgnoreCase("")) {
						sendMessageValuesList(CRO_REJ_REASON, "Please Select Rejection Reason.");
						formObject.setStyle(BTN_SUBMIT, DISABLE, FALSE);
						return false;
					}
					if(rejectionRemarks.equalsIgnoreCase("")) {
						sendMessageValuesList(CRO_REMARKS, "Please Fill Remarks.");
						formObject.setStyle(BTN_SUBMIT, DISABLE, FALSE);
						return false;
					}
				}
				if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Send To Compliance") 
						|| formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Send To Product Approver")) {
					if(!checkMandatoryDoc(data)) {
						formObject.setStyle(BTN_SUBMIT, DISABLE, FALSE);
						return false;
					}
				}
				if(!(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only") 
				  || formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Permanent Reject - Discard")  
				  ||  formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade"))) {//added by krishna
					if(formObject.getValue(ACC_TITLE).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(ACC_TITLE, "Please fill Account Title.");
						return false;
					}
					int iRows = getGridCount(PRODUCT_QUEUE);	
					boolean isEtihad = false;					
					String sProdCode = "";
					String sChequebook = "";
					if(!formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade")){
					if(iRows < 1) {
						sendMessageValuesList(PRODUCT_QUEUE, "Please add atleast one product.");
						return false;
					}
					}
					sQuery= "SELECT PRODUCT_CODE FROM USR_0_PRODUCT_OFFERED WHERE WI_NAME ='"+sWorkitemId+"'";	
					output = formObject.getDataFromDB(sQuery);
					String sProduct = "";
					if(output.size() > 0) {
						for(int i=0; i<output.size(); i++) {
							sProduct = sProduct+output.get(i).get(0)+",";
						}
					}
					logInfo("submitDDEValidations", "product list offered: "+sProduct);
					for(int i=0;i<iRows;i++) {
						sProdCode = formObject.getTableCellValue(PRODUCT_QUEUE, i, 1);
						sChequebook = formObject.getTableCellValue(PRODUCT_QUEUE, i, 6);
						logInfo("submitDDEValidations", "row: "+i+", sProdCode: "+sProdCode+", sChequebook: "
								+sChequebook);
						if(sProdCode.equalsIgnoreCase("")) {
							sendMessageValuesList(PRODUCT_QUEUE, "Blank Row is not allowed.");
							//formObject.setNGSelectedTab("Tab5",4); //handle in js
							return false;
						}
						if(sProduct.indexOf(sProdCode) == -1) {
							sendMessageValuesList(PRODUCT_QUEUE,"Product code "
									+sProdCode+" is not matching in the offered product list");
							//formObject.setNGSelectedTab("Tab5",4); //handle in js
							return false;
						}
						if(sChequebook.isEmpty()) {
							sendMessageValuesList(PRODUCT_QUEUE, "Please fill Chequebook required.");
							//formObject.setNGSelectedTab("Tab5",4); //handle in js
							return false;
						}
						formObject.setTableCellValue(PRODUCT_QUEUE, i, 14, String.valueOf(i+1));
					}
					for(int i=1; i<iRows; i++) {
						sProdCode = formObject.getTableCellValue(PRODUCT_QUEUE, i, 1);
						sQuery = "SELECT SUB_PRODUCT_TYPE FROM USR_0_PRODUCT_TYPE_MASTER WHERE PRODUCT_CODE ='"+sProdCode+"'";
						output = formObject.getDataFromDB(sQuery);
						if(output.size() > 0 && output.get(0) != null && !output.get(0).get(0).equalsIgnoreCase("")) {
							if(output.get(0).get(0).equalsIgnoreCase("Etihad")) {
								isEtihad = true;
								break;
							}
						}
					}
					if(isEtihad) {
						if(formObject.getValue(EXISTING_ETIHAD_CUST).toString().isEmpty()) {
							sendMessageValuesList(EXISTING_ETIHAD_CUST, "Please Select Etihad Status.");
							return false;
						} else if(formObject.getValue(EXISTING_ETIHAD_CUST).toString().equalsIgnoreCase("Yes")) {
							if(formObject.getValue(ETIHAD_NO).toString().equalsIgnoreCase("")) {
								sendMessageValuesList(ETIHAD_NO,"Please fill Etihad Number.");
								return false;
							} else if(formObject.getValue(ETIHAD_NO).toString().equalsIgnoreCase("0")) {
								sendMessageValuesList(ETIHAD_NO,"Please validate Etihad Number.");
								return false;
							}
						}
					}
					if(formObject.getValue(SOURCE_NAME).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(SOURCE_NAME, "Please Select Source Name.");
						return false;
					}
					if(formObject.getValue(SOURCE_CODE).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(SOURCE_CODE,"Please Select Source Code.");
						formObject.setStyle(BTN_SUBMIT, DISABLE, FALSE);
						return false;
					}
					saveStandingInstrInfo();
					saveStandInstrInfo();
					boolean result = validateDebitDetails();				
					if(!result) {
						formObject.setStyle(BTN_SUBMIT, DISABLE, FALSE);
						return false;
					}
					iRows = getGridCount(QUEUE_DC);
					sProdCode ="";
					for(int iDC=1; iDC<iRows; iDC++) {
						formObject.setTableCellValue(QUEUE_DC, iDC, 8, String.valueOf(iDC));
					}						
				}
				try {
					if(formObject.getValue(CHANNEL_TYPE).toString().equalsIgnoreCase("Alternate")
							&& sActivityName.equalsIgnoreCase("DDE_Account_Info")) {
						/*if(!FETCH_INFO_flag) {
							if(formObject.getValue("AO_INSTANT_DEL_NO").toString().equalsIgnoreCase("true")) {
								int reply1 = JOptionPane.showConfirmDialog(null,NGFUserResourceMgr.getResourceString_val("CA0174") , null, JOptionPane.YES_NO_OPTION);	
								if(reply1 == JOptionPane.NO_OPTION) {
									System.out.println("inside =====reply1 == JOptionPane.NO_OPTION");
									formObject.NGFocus("FETCH_INFO");
									formObject.setNGEnable("Edit",true);
									formObject.setNGEnable("SUBMIT_1",true);
									formObject.setNGEnable("Command24",true);
									formObject.setNGEnable("static_submit",true);
									formObject.setNGEnable("static_next",true);
									return 0;
								}
							}
						}*/
						if(formObject.getValue(INSTANT_DEL_YES).toString().equalsIgnoreCase("true") 
								&& formObject.getValue(DFC_STATIONERY_AVAIL).toString().equalsIgnoreCase("")) {
							sendMessageValuesList(DFC_STATIONERY_AVAIL,"Please select DFC Stationary Available");
							//edit button enable removed
							formObject.setStyle(BTN_SUBMIT, DISABLE, FALSE);
							return false;
						}
						sQuery = "SELECT COUNT(1) AS COUNT FROM USR_0_AO_DEBITCARD WHERE LODGEMENT_REF_NO='"+formObject.getValue(LODGEMENT_NO)+"' and CARDTYPE is not null";
						output = formObject.getDataFromDB(sQuery);
						int sCount = Integer.parseInt(output.get(0).get(0));
						if(sCount != 0) {
							String sQuery2 = "SELECT COUNT(1) AS COUNT FROM DEBIT_CARD_REP WHERE WI_NAME='"+sWorkitemId+"'";
							output = formObject.getDataFromDB(sQuery);
							int sCount2 = Integer.parseInt(output.get(0).get(0));
							if(sCount2==0) {
								sendMessageValuesList("","User has requested debit card.");
							}
						}
					}
				} catch(Exception e) {	
					logError("", e);
				}
			}
			/*int iRows = getGridCount(PRODUCT_QUEUE);
			output = formObject.getDataFromDB("SELECT DISTINCT PRODUCT_CODE,PRODUCT_NAME,CURRENCY FROM "
					+ "USR_0_PRODUCT_EXISTING WHERE WI_NAME ='"+sWorkitemId+"'");
			int iTotalRetrived = output.size();
			if(iTotalRetrived != 0 && null != output.get(0)) {
				try {
					for(int i=0; i<iTotalRetrived; i++) {
						for (int j=1; j<iRows; j++) {
							if(formObject.getTableCellValue(PRODUCT_QUEUE, j, 1).equalsIgnoreCase(output.get(i).get(0))
									&& formObject.getTableCellValue(PRODUCT_QUEUE, j, 2).equalsIgnoreCase(output.get(i).get(1))
									&& formObject.getTableCellValue(PRODUCT_QUEUE, j, 3).equalsIgnoreCase(output.get(i).get(2)) {
								int reply;
								reply=JOptionPane.showConfirmDialog(null,"Product with following details already added,"
										+ " Do you still want to add \n Code:"+output.get(i).get(0)+" \n Description:"
										+output.get(i).get(1)+" \n Currency:"+output.get(i).get(2)+"",null, JOptionPane.YES_NO_OPTION);
								if(reply==JOptionPane.YES_OPTION) {
									break;
								} else {
									formObject.NGFocus("acc_repeater");
									return false;
								}
							}
						}
					}
				} catch (Exception ex) {
					logError("", ex);
				}

			}*///yamini in js
			//submitAfterConfirmation--from js to java again for DDE Account Info
		}
		return true;
	}
	
	public boolean postSubmitAccountInfoValidation() {//complete for dde account info after cust info
		try {
			logInfo("postSubmitAccountInfoValidation", "INSIDE");
			if(sBackRoute.equalsIgnoreCase("True")) {
				return true;
			}
			createHistory();
			createAllHistory();
			updateProfitCentre();
			String sUpdateEtihad = "";
			if(formObject.getValue(IS_ETIHAD).toString().equalsIgnoreCase("1")) {
				sUpdateEtihad ="Yes";
			}
			String sCustNo = getPrimaryCustomerSNO();
			String sQuery11 = "SELECT CUST_FULL_NAME FROM USR_0_CUST_TXN WHERE WI_NAME ='"+sWorkitemId+"' AND "
					+ "CUST_SNO='"+sCustNo+"'";
			List<List<String>> output = formObject.getDataFromDB(sQuery11);
			logInfo("postSubmitAccountInfoValidation", "sQuery11: "+sQuery11);
			logInfo("postSubmitAccountInfoValidation", "output: "+output);
			String sCustName = output.get(0).get(0);
			String dec = formObject.getValue(CRO_DEC).toString();
//			String created_by =  xmlDataParser.getValueOf("UserName").toUpperCase();
			String created_by = sUserName;
			String sValue = "";
			Date d= new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			String sDate = dateFormat.format(d);     
			String sQ2 = "select decode (bank_relation,'New','NTB', bank_relation) as pri_bank_relation "
					+ "from acc_relation_repeater where sno=1 and wi_name='"+sWorkitemId+"' ";
			logInfo("postSubmitAccountInfoValidation", "sQ2: "+sQ2);
			output = formObject.getDataFromDB(sQ2); 
			logInfo("postSubmitAccountInfoValidation", "output: "+output);
			String pri_bank_rel = "";
			if(output.size()>0) {
				pri_bank_rel = output.get(0).get(0);
			}
			if(formObject.getValue(IS_ETIHAD).toString().equalsIgnoreCase("1")) {
				logInfo("postSubmitAccountInfoValidation", "ETIHAD is YES");
				sValue = "'"+ dec +"'"+(char)25+"'"+ sActivityName +"'"+(char)25+"'"+ sUpdateEtihad +"'"+(char)25+"'"
						+created_by+"'"+(char)25+"'"+sDate+"'"+(char)25+"'"+sCustName+"'"+(char)25+"'"+pri_bank_rel+"'";
				updateDataInDB(sExtTable,"ACCOUNT_INFO_MAKER_DEC,WI_COMPLETED_FROM,ETIHAD_UPDATE_REQ,CRO_NAME,"
						+ "CRO_SUBMIT_DATE,PRI_CUST_NAME,pri_bank_relation ",sValue,"WI_NAME = '"+sWorkitemId+"'");	
			} else {
				sValue = "'"+ dec +"'"+(char)25+"'"+ sActivityName +"'"+(char)25+"'"+created_by+"'"+(char)25+"'"+sDate
						+"'"+(char)25+"'"+sCustName+"'"+(char)25+"'"+pri_bank_rel+"'";
				updateDataInDB(sExtTable,"ACCOUNT_INFO_MAKER_DEC,WI_COMPLETED_FROM,CRO_NAME,CRO_SUBMIT_DATE,"
						+ "PRI_CUST_NAME,pri_bank_relation ",sValue,"WI_NAME = '"+sWorkitemId+"'");
			}
			if(formObject.getValue(ACC_OWN_TYPE).toString().equalsIgnoreCase("Joint")) {
				String sQuery1 = "select c.cust_seg from usr_0_cust_txn c, acc_relation_repeater r where r.wi_name ='"+ sWorkitemId +"' and r.wi_name=c.wi_name and r.sno=c.cust_sno  and (r.acc_relation='JAF' or r.acc_relation='JOF')";
				List<List<String>> output1 = formObject.getDataFromDB(sQuery1);
				logInfo("postSubmitAccountInfoValidation", "sQuery1: "+sQuery1);
				logInfo("postSubmitAccountInfoValidation", "output1: "+output1);
				if(output.size()>0) {
					String val = output1.get(0).get(0);
					String sUpdateDecision1 = "update "+sExtTable+" set PRI_CUST_SEGMENT='"+val+"' where wi_name='"+ sWorkitemId +"'";
					formObject.saveDataInDB(sUpdateDecision1);
				}
			}
			String sProcName="SP_TemplateGenerationEmailDt";
			List<String> paramlist = new ArrayList<String>();
			paramlist.add ("Text :"+sWorkitemId);
			List sOutput1 = formObject.getDataFromStoredProcedure(sProcName,paramlist);
			String sCustSeg = formObject.getValue(NEW_CUST_SEGMENT).toString();
			if(sCustSeg == null) {
				sCustSeg = "";
			}
	//	private client risk krishna
		//	if(sCustSeg.equalsIgnoreCase("Private Clients")) {
		//		CalculateRiskCategoryChange();
		//	}
			tempUsrZeroProductSelected();
		} catch (Exception e) {
			logError("postSubmitAccountInfoValidation", e);
		} 
		return true;
	}
	
	private boolean submitWorkitem(String controlName, String eventType) {
		List<List<String>> sOutput;
		logInfo("submitWorkitem","INSIDE");
		if(isControlVisible(DOC_APPROVAL_OBTAINED) && isControlVisible(COURT_ORD_TRADE_LIC)){
			if (formObject.getValue(DOC_APPROVAL_OBTAINED).toString().equalsIgnoreCase("false")
					&& formObject.getValue(COURT_ORD_TRADE_LIC).toString().equalsIgnoreCase("false")) {
				sendMessageValuesList("", "Please select the appropriate checkbox to complete the validation");	
				return false;
			}
		}
		if(sActivityName.equalsIgnoreCase(ACTIVITY_DDE_ACCOUNT_INFO)) {
			/*
			//Jamshed
			String sData  = formObject.getValue(CRO_DEC).toString();
			String vig_query = "select pri_cust_segment,final_risk_val,is_vigilance_visited from ext_ao where wi_name='"+sWorkitemId+ "'";
			logInfo("CRO_DEC","vig_query= "+vig_query);
			List<List<String>> vig_list=formObject.getDataFromDB(vig_query);
			String pri_cust_segment="";
			String final_risk_val="";
			String is_vigilance_visited="";
			if (vig_list != null && vig_list.size() > 0){
				pri_cust_segment=vig_list.get(0).get(0);
				final_risk_val=vig_list.get(0).get(1);
				is_vigilance_visited=vig_list.get(0).get(2);
			}
			if(! formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade") && 
					! formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Downgrade") && 
					! formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Family Banking")){
				
				if(! formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("New Account with Category Change") &&  
						! formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only") ){
			if((final_risk_val!=null && final_risk_val.equalsIgnoreCase("Medium Risk")) && 
					(pri_cust_segment!=null && pri_cust_segment.equalsIgnoreCase("Private Clients")) &&
					!(sData.equalsIgnoreCase("Send To Vigilance")) && !(sData.equalsIgnoreCase("Permanent Reject - Discard")) &&
					!(is_vigilance_visited.equalsIgnoreCase("Yes"))){
				sendMessageValuesList(CRO_DEC,"Please select Send To Vigilance decision for Medium Risk and Private Clients ");
				return false;
					}
				}else{
					String newSegment= formObject.getValue(NEW_CUST_SEGMENT).toString();
					if((final_risk_val!=null && final_risk_val.equalsIgnoreCase("Medium Risk")) && 
							(newSegment!=null && newSegment.equalsIgnoreCase("Private Clients")) &&
							!(sData.equalsIgnoreCase("Send To Vigilance")) && !(sData.equalsIgnoreCase("Permanent Reject - Discard")) &&
							!(is_vigilance_visited.equalsIgnoreCase("Yes"))){
						sendMessageValuesList(CRO_DEC,"Please select Send To Vigilance decision for Medium Risk and Private Clients ");
						return false;
					}
				}
			} */if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade")){
				String newSegment= formObject.getValue(NEW_CUST_SEGMENT).toString();
				logInfo("croDec","Downgrade"+newSegment);
				String croDec = formObject.getValue(CRO_DEC).toString();
				logInfo("croDec","Downgrade"+croDec);
				if ((newSegment==null||newSegment.equalsIgnoreCase(""))&& (croDec.equalsIgnoreCase("Approve")||croDec.equalsIgnoreCase("Send To Compliance"))){
					sendMessageValuesList(CRO_DEC,"Customer is already enrolled with highest segment hence segment upgrade is not possible, Kindly reject the case.");
				}
			}
		//Jamshed end
			
			boolean prodChangeFlag = checkProdChngForNoEligibility();
			if(!prodChangeFlag) {
				logInfo("submitWorkitem","inside if--------");
				sendMessageValuesList("", "Customer is not eligible for cheque book. Please change the product");	
				return false;
			} 
			insertUdfDetails();	
			logInfo("submitWorkitem","inside crs mandatoryCRSCheckcategorychange");
			boolean crscategory = mandatoryCRSCheckcategorychange();
			if(!crscategory) {
				return false;
			}
			String resultTRSD=	checkTRSDResult("CRO");
			logInfo("submitWorkitem","resultTRSD:"+resultTRSD);
			if(resultTRSD.equalsIgnoreCase("2")  && !formObject.getValue(CRO_DEC).toString()
					.equalsIgnoreCase("Permanent Reject - Discard")) {
				sendMessageValuesList("", "This application can only be Rejected since TRSD result is Rejected.");
				return false;
			} else if(resultTRSD.equalsIgnoreCase("1")  && !formObject.getValue(CRO_DEC).toString()
					.equalsIgnoreCase("Return to Originator")) {
				sendMessageValuesList("","This application can only be Returned since TRSD result is Returned.");
				return false;
			}
		} else if(sActivityName.equalsIgnoreCase(ACTIVITY_DDE_CUST_INFO)) {
			boolean result = false;
			logInfo("submitWorkitem","before crs mandatoryCRSCheckcategorychange "+formObject.getValue(CRO_DEC).toString());
			if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve") || 
					formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("")){
				logInfo("submitWorkitem","AO_CRO_DEC----"+ formObject.getValue(CRO_DEC));
				if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("New Account")) {
					int sno = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
					String acc_rel= formObject.getTableCellValue(ACC_RELATION, sno, "ACC_RELATION").toString();
					logInfo("submitWorkitem","acc_rel: "+acc_rel);
					String sQuery = "select cust_seg from usr_0_cust_txn WHERE WI_NAME ='"+sWorkitemId+"' 	and cust_sno='1'";
					logInfo("submitWorkitem","query: "+sQuery);
					sOutput = formObject.getDataFromDB(sQuery);
					logInfo("submitWorkitem","sOutput: "+sOutput);
					String pri_cust_cat = sOutput.get(0).get(0);
					logInfo("submitWorkitem","pri_cust_cat: "+pri_cust_cat);
					if(!(acc_rel.equalsIgnoreCase("AUS") && acc_rel.equalsIgnoreCase("POA")) 
							&& pri_cust_cat.equalsIgnoreCase("Signatory")) {
						logInfo("submitWorkitem","!(acc_rel.equalsIgnoreCase(AUS) || acc_rel.equalsIgnoreCase(POA))===="+!(acc_rel.equalsIgnoreCase("AUS") || acc_rel.equalsIgnoreCase("POA")));
						logInfo("submitWorkitem"," pri_cust_cat.equalsIgnoreCase(Signatory)" + pri_cust_cat.equalsIgnoreCase("Signatory"));
						logInfo("submitWorkitem","inside if--------");
						sendMessageValuesList("","Primary signatory Customer can't open a new account!!");
						//formObject.NGMakeFormReadOnly();
						return false;
					}
				}
			}
			if(!formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Reject")) {
				if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Reject")) {
					sBackRoute="True";
				}
				if(formObject.getValue(RA_IS_UAE_RESIDENT).toString().equalsIgnoreCase("")) {
					calculateResidencyStatus(RA_IS_UAE_RESIDENT);	
				}
				result = checkNatSegment();
				logInfo("submitWorkitem","NEW VALIDATION---"+result);
				if(!result) {
					return false;
				}
				if(!mandatoryComparisonData()) {
					return false;
				}
				if(!mandatoryIndividualInfo()) {
					return false;
				}
				if(!mandatoryContactInfo()) {
					return false;
				}
				
				/*if(validate==false && !formObject.getValue(IDS_REF_BY_CUST).toString().equalsIgnoreCase("")) {
					sendMessageValuesList(BTN_FCR_SRCH,"Please Validate Reffered by customer ID.");
					return false;	
				}*/
				String sFinalPermanentCountry = getFinalData(formObject.getValue(CHECKBOX_COUNTRY_PER_RES_FCR).toString(),
						formObject.getValue(CHECKBOX_COUNTRY_PER_RES_EIDA).toString(),formObject.getValue(CHECKBOX_COUNTRY_PER_RES_MANUAL).toString(),
						formObject.getValue(FCR_PER_CNTRY).toString(),formObject.getValue(EIDA_PER_CNTRY).toString(),
						formObject.getValue(MANUAL_PER_CNTRY).toString());
				if(!sFinalPermanentCountry.equalsIgnoreCase(formObject.getValue(PERM_CNTRY).toString())) {
					sendMessageValuesList(PERM_CNTRY,"Permanent Country data is not same. Please change it.");
					return false;
				}
				if(!formObject.getValue(CUST_NATIONALITY).toString().equalsIgnoreCase("United Arab Emirates")) {
					if(!mandatoryEmploymentInfo()) {
						return false;
					}
				} else {
					if(!formObject.getValue(ED_DATE_OF_JOING).toString().equalsIgnoreCase("")) {
						if(!validateFutureDate(ED_DATE_OF_JOING,"Date of Joining")) {
							return false;
						}
					}
				}
				if(!mandatoryiKYCDDE()) {
					return false;
				}
				String custSegment = formObject.getValue(PD_CUSTSEGMENT).toString(); 
				sOutput = formObject.getDataFromDB("select iscrsmandatory from usr_0_cust_segment where cust_segment='"
						+custSegment+"'");
				String ismandatory = "";
				try {
					ismandatory = sOutput.get(0).get(0);
				} catch (Exception e1) {
					logError("submitWorkitem", e1);
				} 
				logInfo("submitWorkitem","CUST Segment result: "+ismandatory);
				boolean custSegmentCheck;
				if(ismandatory.equalsIgnoreCase("Yes"))
					custSegmentCheck=true;
				else
					custSegmentCheck=false;
				
				if(formObject.getValue(RA_CARRYNG_EID_CARD).toString().equalsIgnoreCase("Yes") &&
						formObject.getValue(PD_EIDANO).toString().equalsIgnoreCase("")) {
					return false;
				}
				if(!validatePassportType(CHECKBOX_PASSPORT_TYPE_FCR,CHECKBOX_PASSPORT_TYPE_EIDA,CHECKBOX_PASSPORT_TYPE_MANUAL,FCR_PASSTYPE,EIDA_PSSTYPE,
						MANUAL_PASSTYPE,CA018,HD_PASS_TYPE)) {
					logInfo("submitWorkitem","INSIDE DDE_CUSTACC ValidatePassportType");
					return false;
				}
				if(!ValidateComparisonDataCombo(CHECKBOX_VISA_STATUS_FCR,CHECKBOX_VISA_STATUS_EIDA,
						CHECKBOX_VISA_STATUS_MANUAL,FCR_VISASTATUS,EIDA_VISASTATUS,
						MANUAL_VISASTATUS,CA019,"Mandatory","Visa Status")) {
					return false;
				}
				if(!validateVisaStatus()) {
					return false;
				}
				if(!mandatoryAtFatca()) {
					return false;
				}
				if(!mandatoryCRSCheck(custSegmentCheck)) {
					return false;
				}
				if(getGridCount(CRS_TAXCOUNTRYDETAILS) > 0 )
				{
					for(int i=0 ; i<getGridCount(CRS_TAXCOUNTRYDETAILS);i++)
					{
						String countryOfTaxResidency=formObject.getTableCellValue(CRS_TAXCOUNTRYDETAILS,i,1);
						logInfo("submit","Value of selected Tax Residency country"+ countryOfTaxResidency);
						if (countryOfTaxResidency.equalsIgnoreCase("UNITED ARAB EMIRATES")) {
							result = MandatoryCRSDueDiligence();
							System.out.println("MandatoryCRSDueDiligence result---"+result);
							if(!result)
							{
								return false;
							}
							break;
						}
					}
				}
				if(!checkSalaryTransfer()){
					return false;
				}
				try {
					String sFinalDOB = getFinalDataComparison(CHECKBOX_DOB_FCR,CHECKBOX_DOB_EIDA,CHECKBOX_DOB_MANUAL,
							FCR_DOB,EIDA_DOB,MANUAL_DOB);
					int age = CalculateAge(sFinalDOB);
					int age1 = CalculateAge1(sFinalDOB);
					String sAccRelation = formObject.getTableCellValue(ACC_RELATION, 
							Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()), 9);
					String accountOwn = formObject.getValue(ACC_OWN_TYPE).toString();
					boolean bresult = false;
					String sQueryy = "select VALUE from usr_0_defaultvalue_fcr where name='Minor_Age'";
					List<List<String>> sOutputt = formObject.getDataFromDB(sQueryy);
					logInfo("submitWorkitem","sOutputt------"+sOutputt);
					int sMinorAge = Integer.parseInt(sOutputt.get(0).get(0));
					logInfo("submitWorkitem","sMinorAge....."+sMinorAge);
					if(accountOwn.equalsIgnoreCase("Minor")) {
						if(age1 >sMinorAge && sAccRelation.equalsIgnoreCase("Minor")) {
							sendMessageValuesList("","For Minor Date Of Birth Should Not Be Greater Than or equal to "+sMinorAge+" Years.");
							bresult = true;
						}
						if(age<sMinorAge && sAccRelation.equalsIgnoreCase("Guardian")) {
							sendMessageValuesList("","For Guardian Date Of Birth Should Be Greater Than or equal to "+sMinorAge+" Years.");
							bresult = true;
						}
					} else if(accountOwn.equalsIgnoreCase("Joint") && age<sMinorAge) {
						sendMessageValuesList("","For Joint Date Of Birth Should be greater than or equal to "+sMinorAge+" Years.");
						bresult = true;
					} else {
						if(!formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only")) {
							if(age<18) {
								sendMessageValuesList("","For Single Date Of Birth Should be greater than or equal to 18 Years.");
								bresult = true;
							}
						}
					}
					/*if(bresult) {
						if(formObject.getValue(CHECKBOX_DOB_FCR).equalsIgnoreCase("true")) {
							formObject.NGFocus(FCR_DOB);
						} else if(formObject.getValue(CHECKBOX_DOB_EIDA).equalsIgnoreCase("true")) {
							formObject.NGFocus(EIDA_DOB);
						} else if(formObject.getValue(CHECKBOX_DOB_MANUAL).equalsIgnoreCase("true")) {
							formObject.NGFocus(MANUAL_DOB);
						}
						return false;
					}*///do in js
				} catch(Exception e) {
					logError("submitWorkitem", e);
				}
				try {
					int sSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;				
					String sQuery1 = "SELECT IS_DEDUPE_CLICK FROM USR_0_CUST_TXN WHERE WI_NAME ='"+sWorkitemId+"' and "
							+ "cust_sno='"+sSelectedRow+"'";
					logInfo("submitWorkitem"," sQuery1 dedupeDone: "+sQuery1);
					List<List<String>> output1 = formObject.getDataFromDB(sQuery1);
					logInfo("submitWorkitem"," output1 dedupeDone"+output1);
					if(output1.size()>0 && null != output1.get(0)) {
						
					}
					String dedupeDone = output1.get(0).get(0);
					logInfo("submitWorkitem","dedupeDone"+dedupeDone);
					if(!dedupeDone.equalsIgnoreCase("true")) {
						sendMessageValuesList(BTN_DEDUPE_SEARCH,"Please Do Dedupe Search For This Customer.");
						return false;
					}
				} catch(Exception e) {
					logError("submitWorkitem", e);
				}
				try {
					String sAccRelation = formObject.getTableCellValue(ACC_RELATION, 
							Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()), 9);
					if(sAccRelation.equalsIgnoreCase("Minor")) {
						if(!minorDateCompDOB()) {
							return false;
						}
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
				if(!checkNatCatSegment()) {
					return false;
				}
				saveKYCInfo();
//				saveKycMultiDropDownData();
//				savePepAssessmentDetails(); //AO Dcra
//				savePreAssessmentDetails();    //shahbaz
				saveComparisonData();
				saveIndividualInfo();
				saveIndividualContactInfo();
//				saveDuplicateData();//blank in old
				saveClientQuestionsData();
				saveCRSDetails();
				logInfo("submitWorkitem","In CUSTOMER_INFO after save");
				String sCustNo = "";
				int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
				sCustNo = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 0);
				String sBankRelation = formObject.getTableCellValue(ACC_RELATION,iSelectedRow,7);									
				String sCID = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 2);
				String sCASA = "";
				if(sBankRelation.equalsIgnoreCase("Existing")) {	
					List<List<String>> output = formObject.getDataFromDB("SELECT COUNT(1) AS COUNT_WI FROM "
							+ "USR_0_PRODUCT_EXISTING WHERE WI_NAME ='"+sWorkitemId+"' AND CUSTOMER_ID ='"+sCID+"' "
							+ "AND ACCOUNT_TYPE ='CASA'");
					sCASA = output.get(0).get(0);
					logInfo("submitWorkitem","CASA Value:"+sCASA);
				}
				if(sBankRelation.equalsIgnoreCase("New") || sCASA.equalsIgnoreCase("0")) {
					String input = getApplicationRiskInputXML(iSelectedRow);
					logInfo("submitWorkitem","XML:"+input);
					String outputResult = socket.connectToSocket(input);
					logInfo("submitWorkitem","outputResult: "+outputResult);
					if(outputResult.equalsIgnoreCase("NO")) {
						sendMessageValuesList("", "Selected passport holder and Non UAE Residents, not allowed to open "
								+ "Account");
						return false;
					} else if(outputResult.equalsIgnoreCase("Partial")) {/*//yamini-do in js
						int respose=JOptionPane.showConfirmDialog(null,"Selected passport holder Residents does not meet conditions,\nHence not allowed to open Account. Do you still want to proceed with account opening?",null,JOptionPane.YES_NO_OPTION);
						if(respose==JOptionPane.YES_OPTION) {
							formObject.setValue("NIG_MAKER","YES");
							String updatequery = "update USR_0_CUST_TXN set NIGEXCEPTIONMAKER='YES' Where "
									+ "WI_NAME='"+formObject.getValue("AO_WI_Name")+"' AND CUST_SNO ='"+sCustNo+"'";
							formObject.saveDataInDB(updatequery);
							logInfo("submitWorkitem","Updated Successfully");
						} else {
							return false;
						}
					*/
						sendMessageList.clear();
						sendMessageValuesList("", "Selected passport holder Residents does not meet conditions,\nHence "
								+ "not allowed to open Account. Do you still want to proceed with account opening?");
						return false;
					}
				}
				if(!(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only") ||
				    formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade"))){
				if(!calculateIndRiskDDE()) {
					return false;
				}
			}
	       	}
	}
//		formObject.RaiseEvent("WFDone");	//do in js
		return true;
	}
	
	private boolean calculateIndRiskDDE() {
		int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
	// AO DCRA COMMENTED //15082023
	//	String sInputXML = getIndRiskInputXML(iSelectedRow);
		String sInputXML = executeApplicationAssessmentRiskRetail(iSelectedRow); 
		String sOutputXml = "";
		try {
			sOutputXml = socket.connectToSocket(sInputXML);
			//Added by Jamshed
			  XMLParser xp = new XMLParser(sOutputXml); 
                          String finalRisk_cd =xp.getValueOf("finalRisk"); 
                          logInfo("executeApplicationAssessmentRiskRetail","finalRisk_cd :  "+ finalRisk_cd);
			  
			  String finalRisk_cd_query ="select risk_value from usr_0_risk_values where risk_code='"+finalRisk_cd+"'"; 
			logInfo("executeApplicationAssessmentRiskRetail","finalRisk_cd_query= "+ finalRisk_cd_query);
			List<List<String>> output_list_db =formObject.getDataFromDB(finalRisk_cd_query); 
			String finalRisk_value=output_list_db.get(0).get(0); 
			logInfo("executeApplicationAssessmentRiskRetail","finalRisk_value= "+ finalRisk_value);
			  sOutputXml=finalRisk_value;
			
			
		} catch (Exception e) {
			logError("submitWorkitem", e);
		}
		logInfo("submitWorkitem","sOutput--in webservice--"+sOutputXml);
		if(null == sOutputXml || sOutputXml.isEmpty()) {
			sendMessageValuesList("","Some error occured in calculating Individual risk");
			return false;
		} else {
//			if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only") && 
//					formObject.getValue(NEW_CUST_SEGMENT).toString().equalsIgnoreCase("Private Clients")){
//				sOutputXml="Neutral Risk";
//				logInfo("onDDEButtonsubmit ","Cust Segment" + formObject.getValue(PD_CUSTSEGMENT).toString());
//				logInfo("onDDEButtonsumbit","Request Type"+formObject.getValue(REQUEST_TYPE).toString());
//			}
//				else	
			if((!sOutputXml.equalsIgnoreCase("Unacceptable Risk") && !sOutputXml.equalsIgnoreCase("PEP") 
					&& !sOutputXml.equalsIgnoreCase("UAE-PEP") && !sOutputXml.equalsIgnoreCase("Non UAE-PEP")
					&& !sOutputXml.equalsIgnoreCase("Increased Risk") && !sOutputXml.equalsIgnoreCase("HIO PEP")) // Added by Ameena
					&& formObject.getValue(PD_CUSTSEGMENT).toString().equalsIgnoreCase("Private Clients")) {
//				sOutputXml = "Increased Risk";		 // commented by Ameena
				sOutputXml = "Medium Risk";
		}
			String sWsName = formObject.getValue(CURR_WS_NAME).toString();
			String sriskColumn = "SNO,WI_NAME,WS_NAME,CUST_CUR_RISK_BANK";
			String sriskValue = "'"+iSelectedRow+"','"+sWorkitemId+"','"+sWsName+"','"+sOutputXml+"'";	
			logInfo("submitWorkitem","sriskColumn.."+sriskColumn);
			logInfo("submitWorkitem","sriskValue.."+sriskValue);
			insert_Into_Usr_0_Risk_Data(sriskColumn,sriskValue);
			String sUpdateDecision = "update USR_0_CUST_TXN set CUST_INDI_RISK='"+ sOutputXml +"' Where "
					+ "WI_NAME='"+ sWorkitemId +"' AND CUST_SNO ='"+iSelectedRow+"'";
			logInfo("submitWorkitem","sUpdateDecision: "+sUpdateDecision);
			formObject.saveDataInDB(sUpdateDecision);
		}
		return true;
	}
	
	private void onSaveTabClickDDE(int tabId) {
		logInfo("onSaveTabClickDDE","tabId---->>>>>>> "+tabId);
		if(tabId == 1 || tabId == 2 || tabId == 3 || tabId == 4 || tabId == 5) {
			saveKYCInfo();
//			saveKycMultiDropDownData();
			if(tabId == 3) {
			savePepAssessmentDetails();
			}// Ao Dcra
//			savePreAssessmentDetails();    //shahbaz
			saveComparisonData();
			saveIndividualInfo();
			saveIndividualContactInfo();
			saveClientQuestionsData();
			saveCRSDetails(); 
		} else if(tabId == 8) {
			if(sActivityName.equalsIgnoreCase(ACTIVITY_DDE_ACCOUNT_INFO)) {
				boolean prodChangeFlag = checkProdChngForNoEligibility();
				if(!prodChangeFlag) {
					sendMessageValuesList("", "Customer is not eligible for cheque book. Please change the product");
					return;
				}
				insertUdfDetails();	
			}
		}
		//Jamshed
		else if(tabId == 15){
			logInfo("onSaveTabClickDDE","Inside 15 sheetid tabId---->>>>>>> "+tabId);
			if(sActivityName.equalsIgnoreCase(ACTIVITY_DDE_ACCOUNT_INFO)){
				String vig_query = "select pri_cust_segment,final_risk_val from ext_ao where wi_name='"+sWorkitemId+ "'";
				logInfo("onSaveTabClickDDE","vig_query= "+vig_query);
				List<List<String>> vig_list=formObject.getDataFromDB(vig_query);
				String pri_cust_segment="";
				String final_risk_val="";
				if (vig_list != null && vig_list.size() > 0){
					pri_cust_segment=vig_list.get(0).get(0);
					final_risk_val=vig_list.get(0).get(1);
				}
				logInfo("onSaveTabClickDDE","pri_cust_segment, final_risk_val <==> "+pri_cust_segment+", "+final_risk_val);
				
				
				if(!(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade") ||
					 formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Downgrade") || 
					 formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Family Banking"))){
					
					if(!( formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("New Account with Category Change") ||  
						  formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only"))){
				if(pri_cust_segment.equalsIgnoreCase("Private Clients") && final_risk_val.equalsIgnoreCase("Medium Risk")){
							formObject.addItemInCombo(CRO_DEC, "Send To Vigilance");
					logInfo("onSaveTabClickDDE","Send To Vigilance Added>>>>>>>");
				}
					}else{
						String newSegment= formObject.getValue(NEW_CUST_SEGMENT).toString();
						if(newSegment!=null && !newSegment.equalsIgnoreCase("") &&
								newSegment.equalsIgnoreCase("Private Clients") && final_risk_val.equalsIgnoreCase("Medium Risk")){
							formObject.addItemInCombo(CRO_DEC, "Send To Vigilance");
							logInfo("onSaveTabClickDDE","Send To Vigilance Added>>>>>>>");
						}
					}
				}
			}
		}
//		onTabClick(","+tabId);
	}
	
	private boolean saveAndNextValidations(String data) {
		logInfo("saveAndNextValidations", "INSIDE, data: "+data);
		int sheetIndex = Integer.parseInt(data);
		String sAccRelation = "";
		String sBankRelation = "";
		try {
			int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
			sAccRelation = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 9);
			sBankRelation = formObject.getTableCellValue(ACC_RELATION,iSelectedRow, 7);
		} catch(Exception e) {
			logError("saveAndNextValidations", e);
		}
		if(sheetIndex == 17){
			logInfo("saveAndNextValidation", "decision");
			formObject.clearCombo(CRO_DEC);
			int iCount =getListCount(CRO_DEC);
			if(iCount == 0) {
				String sQuery1 = "Select to_char(WS_DECISION) from USR_0_DECISION_MASTER where ws_name="
						+ "'"+formObject.getValue(CURR_WS_NAME).toString()+"'"
						+ " order by to_char(WS_DECISION)";
				addDataInComboFromQuery(sQuery1, CRO_DEC);
			}

		}
		if(sActivityName.equalsIgnoreCase(ACTIVITY_DDE_CUST_INFO)){
			if(sheetIndex == 1) {
				logInfo("saveAndNextValidations", "sheet 1");
				if(!ValidateComparisonDataCombo(CHECKBOX_PREFIX_FCR,CHECKBOX_PREFIX_EIDA,CHECKBOX_PREFIX_MANUAL,FCR_PREFIX,
						EIDA_PREFIX,MANUAL_PREFIX,CA037,"Mandatory","Prefix")) {
					return false;
				}
				if(formObject.getValue(MANUAL_FIRSTNAME).toString().trim().equalsIgnoreCase("")) {
					sendMessageValuesList(MANUAL_FIRSTNAME,"Please Fill First Name.");
					return false;
				} 
				if(formObject.getValue(MANUAL_LASTNAME).toString().trim().equalsIgnoreCase("")) {
					sendMessageValuesList(MANUAL_LASTNAME,"Please Fill Last Name.");
					return false;
				} 
				if(!ValidateComparisonData(CHECKBOX_FULLNAME_FCR,CHECKBOX_FULLNAME_EIDA,CHECKBOX_FULLNAME_MANUAL,FCR_NAME,
						EIDA_NAME,MANUAL_NAME,CA011,"Mandatory","Full Name")) {
					return false;
				}
				if(!ValidateComparisonData(CHECKBOX_SHORTNAME_FCR,CHECKBOX_SHORTNAME_EIDA,CHECKBOX_SHORTNAME_MANUAL,
						FCR_SHORTNAME,EIDA_SHORTNAME,MANUAL_SHORTNAME,CA0184,"Mandatory","Short Name")) {
					return false;
				}
				String sShortName =getFinalDataComparison(CHECKBOX_SHORTNAME_FCR,CHECKBOX_SHORTNAME_EIDA,CHECKBOX_SHORTNAME_MANUAL,FCR_SHORTNAME,EIDA_SHORTNAME,MANUAL_SHORTNAME).trim();
				logInfo("saveAndNextValidation", "sShortName: "+sShortName+", length: "+sShortName.length());
				if(sShortName !=null  && sShortName.length() > 20){
					sendMessageValuesList(MANUAL_SHORTNAME, "Short name length must be less than or equal to 20");
					return false;
				}
				if(!ValidateName(CHECKBOX_FULLNAME_FCR,CHECKBOX_FULLNAME_EIDA,CHECKBOX_FULLNAME_MANUAL,FCR_NAME,EIDA_NAME,
						MANUAL_NAME,CA0134,CA0138)) {
					return false;
				}
				if(sBankRelation.equalsIgnoreCase("New")) {
					if(!ValidateComparisonData(CHECKBOX_MOTHERSNAME_FCR,CHECKBOX_MOTHERSNAME_EIDA,
							CHECKBOX_MOTHERSNAME_MANUAL,FCR_MOTHERSNAME,EIDA_MOTHERNAME,
							MANUAL_MOTHERNAME,CA0123,"Mandatory","Mother Name")) {
						return false;
					}
				} else {
					if(!ValidateComparisonData(CHECKBOX_MOTHERSNAME_FCR,CHECKBOX_MOTHERSNAME_EIDA,CHECKBOX_MOTHERSNAME_MANUAL,FCR_MOTHERSNAME,EIDA_MOTHERNAME,
							MANUAL_MOTHERNAME,CA0123,"Optional","Mother Name")) {
						return false;
					}
				}
				if(!ValidateName(CHECKBOX_MOTHERSNAME_FCR,CHECKBOX_MOTHERSNAME_EIDA,CHECKBOX_MOTHERSNAME_MANUAL,
						FCR_MOTHERSNAME,EIDA_MOTHERNAME,MANUAL_MOTHERNAME,CA0139,CA0140)) {
					return false;
				}
				if(!ValidateComparisonData(CHECKBOX_EIDANO_FCR,CHECKBOX_EIDANO_EIDA,CHECKBOX_EIDANO_MANUAL,FCR_EIDANO,
						EIDA_EIDANO,MANUAL_EIDANO,CA0167,"Optional","EIDA number")) {
					return false;
				}
				if(!validateEidaNo(CHECKBOX_EIDANO_FCR,CHECKBOX_EIDANO_EIDA,CHECKBOX_EIDANO_MANUAL,FCR_EIDANO,EIDA_EIDANO,MANUAL_EIDANO,CA0171)) {
					return false;
				}	
				if(!ValidateComparisonData(CHECKBOX_CORR_POB_FCR,CHECKBOX_CORR_POB_EIDA,CHECKBOX_CORR_POB_MANUAL,
						FCR_ADDRESS,EIDA_ADDRESS,MANUAL_ADDRESS,CA048,"Mandatory","PO Box")) {
					return false;
				}
				if(!ValidateComparisonDataCombo(CHECKBOX_CNTRY_OF_CORR_FCR,CHECKBOX_CNTRY_OF_CORR_EIDA,
						CHECKBOX_CNTRY_OF_CORR_MANUAL,FCR_CNTRY,EIDA_CNTRY,MANUAL_CNTRY,
						CA020,"Mandatory","Country of Correspondence Address")) {
					return false;
				}
				if(!ValidateComparisonDataCombo(CHECKFCR,CHECKEIDA,CHECKMANUAL,FCR_RESIDENT,EIDA_RESIDENT,
						MANUAL_RESIDENT,CA0155,"Mandatory","Residential Address Country")) {
					return false;
				}
				if(!ValidateComparisonDataComboForDot(CHECKBOX_COB_FCR,CHECKBOX_COB_EIDA,CHECKBOX_COB_MANUAL,
						FCR_COUNTRYBIRTH,EIDA_COUNTRYBIRTH,MANUAL_COUNTRYBIRTH,CA0178,"Mandatory","Country of Birth")) {
					return false;
				}
				if(!ValidateComparisonDataCombo(CHECKBOX_COUNTRY_PER_RES_FCR,CHECKBOX_COUNTRY_PER_RES_EIDA,
						CHECKBOX_COUNTRY_PER_RES_MANUAL,FCR_PER_CNTRY,EIDA_PER_CNTRY,
						MANUAL_PER_CNTRY,CA074,"Mandatory","Country of Permanant Residence")) {
					return false;
				}
				if(!ValidateComparisonDataComboForDot(CHECKBOX_CNTRY_OF_CORR_FCR,CHECKBOX_CNTRY_OF_CORR_EIDA,CHECKBOX_CNTRY_OF_CORR_MANUAL,FCR_CNTRY,EIDA_CNTRY,
						MANUAL_CNTRY,CA0181,"Mandatory","Country of Correspondence address")) {
					return false;
				}
				if(!ValidateComparisonDataComboForDot(CHECKBOX_COUNTRY_PER_RES_FCR,CHECKBOX_COUNTRY_PER_RES_EIDA,CHECKBOX_COUNTRY_PER_RES_MANUAL,FCR_PER_CNTRY,
						EIDA_PER_CNTRY,MANUAL_PER_CNTRY,CA0182,"Mandatory","Country of Permanent address")) {
					return false;
				}
				if(!ValidateComparisonDataComboForDot(CHECKFCR,CHECKEIDA,CHECKMANUAL,FCR_RESIDENT,
						EIDA_RESIDENT,MANUAL_RESIDENT,CA0183,"Mandatory","Country of Residence address")) {
					return false;
				}
				if(!ValidateComparisonData(CHECKBOX_TELE_RES_FCR,CHECKBOX_TELE_RES_EIDA,CHECKBOX_TELE_RES_MANUAL,FCR_PH,EIDA_PH,MANUAL_PH,CA057,
						"Optional","Phone number")) {
					return false;
				}
				if(flag_phone_start.equalsIgnoreCase("true")){
					if(!ValidateMobileNoStart(CHECKBOX_TELE_RES_MANUAL,MANUAL_PH,"Mandatory",CA0161,"Residence Phone Number")) {
						return false;
					}
				}
				if(!ValidatePhoneNo(CHECKBOX_TELE_RES_FCR,CHECKBOX_TELE_RES_EIDA,CHECKBOX_TELE_RES_MANUAL,FCR_PH,EIDA_PH,MANUAL_PH,CA0127)) {
					return false;
				}
				if(!ValidateComparisonData(CHECKBOX_TELE_MOB_FCR,CHECKBOX_TELE_MOB_EIDA,CHECKBOX_TELE_MOB_MANUAL,FCR_MOBILE,EIDA_MOBILE,MANUAL_MOBILE,
						CA059,"Mandatory","Mobile number")) {
					return false;
				}
				if(flag_phone_start.equalsIgnoreCase("true")) {
					if(!ValidateMobileNoStart(CHECKBOX_TELE_MOB_MANUAL,MANUAL_MOBILE,"Optional",CA0161,"Mobile Number ")) {
						return false;
					}
				}
				if(!validateMobileNo(CHECKBOX_TELE_MOB_FCR,CHECKBOX_TELE_MOB_EIDA,CHECKBOX_TELE_MOB_MANUAL,FCR_MOBILE,EIDA_MOBILE,MANUAL_MOBILE,CA0126)) {
					return false;
				}
				if(sBankRelation.equalsIgnoreCase("New")) {
					if(!ValidateComparisonData(CHECKBOX_EMAIL_FCR,CHECKBOX_EMAIL_EIDA,CHECKBOX_EMAIL_MANUAL,FCR_EMAIL,EIDA_EMAIL,MANUAL_EMAIL,
							CA054,"Optional","Email ID")) {
						return false;
					}
				} else {
					if(!ValidateComparisonData(CHECKBOX_EMAIL_FCR,CHECKBOX_EMAIL_EIDA,CHECKBOX_EMAIL_MANUAL,FCR_EMAIL,EIDA_EMAIL,MANUAL_EMAIL,
							CA054,"Optional","Email ID")) {
						return false;
					}
				} 
				//Added by Shivanshu ATP-472
				if(!ValidateName(CHECKBOX_FIRSTNAME_FCR,CHECKBOX_FIRSTNAME_EIDA,CHECKBOX_FIRSTNAME_MANUAL,
						FCR_FIRSTNAME,EIDA_FIRSTNAME,MANUAL_FIRSTNAME,"First Name "+CA0205,"First Name "+CA0206)) {
					return false;
				}
				if(!ValidateName(CHECKBOX_LASTNAME_FCR,CHECKBOX_LASTNAME_EIDA,CHECKBOX_LASTNAME_MANUAL,
						FCR_LASTNAME,EIDA_LASTNAME,MANUAL_LASTNAME,"Last Name "+CA0205,"Last Name "+CA0206)) {
					return false;
				}
				if(!ValidateName(CHECKBOX_SHORTNAME_FCR,CHECKBOX_SHORTNAME_EIDA,CHECKBOX_SHORTNAME_MANUAL,
						FCR_SHORTNAME,EIDA_SHORTNAME,MANUAL_SHORTNAME,"Short Name "+CA0205,"Short Name "+CA0206)) {
					return false;
				}
				//END ATP-472
				if(!ValidateComparisonData(CHECKBOX_DOB_FCR,CHECKBOX_DOB_EIDA,CHECKBOX_DOB_MANUAL,FCR_DOB,EIDA_DOB,MANUAL_DOB,
						CA012,"Mandatory","Date of Birth")) {
					return false;
				}
				if(!validateDOB(CHECKBOX_DOB_FCR,CHECKBOX_DOB_EIDA,CHECKBOX_DOB_MANUAL,FCR_DOB,EIDA_DOB,MANUAL_DOB)) {
					return false;
				}
				if(!ValidateComparisonData(CHECKBOX_PASSPORT_NO_FCR,CHECKBOX_PASSPORT_NO_EIDA,CHECKBOX_PASSPORT_NO_MANUAL,FCR_PASSPORTNO,EIDA_PASSPORTNO,
						MANUAL_PASSPORTNO,CA0120,"Mandatory","Passport number")) {
					return false;
				}
				if(!ValidateComparisonData(CHECKBOX_PASS_ISS_DT_FCR,CHECKBOX_PASS_ISS_DT_EIDA,CHECKBOX_PASS_ISS_DT_MANUAL,FCR_PASSPORTISSDATE,
						EIDA_PASSPORTISSDATE,MANUAL_PASSPORTISSDATE,CA0121,"Mandatory","Passport Issue Date")) {
					return false;
				}
				if(!validateFutureDates(CHECKBOX_PASS_ISS_DT_FCR,CHECKBOX_PASS_ISS_DT_EIDA,CHECKBOX_PASS_ISS_DT_MANUAL,FCR_PASSPORTISSDATE,
						EIDA_PASSPORTISSDATE,MANUAL_PASSPORTISSDATE,"Passport Issue")) {
					return false;
				}
				if(!ValidateComparisonData(CHECKBOX_PASS_EXP_DT_FCR,CHECKBOX_PASS_EXP_DT_EIDA,CHECKBOX_PASS_EXP_DT_MANUAL,FCR_PASSPORTEXPDATE,
						EIDA_PASSPORTEXPDATE,MANUAL_PASSPORTEXPDATE,CA0122,"Mandatory","Passport Expiry Date")) {
					return false;
				}
				if(!validatePastDates(CHECKBOX_PASS_EXP_DT_FCR,CHECKBOX_PASS_EXP_DT_EIDA,CHECKBOX_PASS_EXP_DT_MANUAL,FCR_PASSPORTEXPDATE,
						EIDA_PASSPORTEXPDATE,MANUAL_PASSPORTEXPDATE,"Passport Expiry")) {
					return false;
				}
				if(!ValidateComparisonDataCombo(CHECKBOX_NATIONALITY_FCR,CHECKBOX_NATIONALITY_EIDA,CHECKBOX_NATIONALITY_MANUAL,FCR_NATIONALITY,
						EIDA_NATIONALITY,MANUAL_NATIONALITY,CA013,"Mandatory","Nationality")) {
					return false;
				}	
				if(!ValidateComparisonData(CHECKBOX_VISA_NO_FCR,CHECKBOX_VISA_NO_EIDA,CHECKBOX_VISA_NO_MANUAL,FCR_VISANO,EIDA_VISANO,MANUAL_VISANO,
						CA0135,"Optional","Visa No")) {
					return false;
				}
				if(!ValidateComparisonData(CHECKBOX_VISA_ISSUE_DATE_FCR,CHECKBOX_VISA_ISSUE_DATE_EIDA,CHECKBOX_VISA_ISSUE_DATE_MANUAL,FCR_VISAISSDATE,EIDA_VISAISSDATE,
						MANUAL_VISAISSDATE,CA0136,"Optional","Visa Issue Date")) {
					return false;
				}
				if(!validateFutureDates(CHECKBOX_VISA_ISSUE_DATE_FCR,CHECKBOX_VISA_ISSUE_DATE_EIDA,CHECKBOX_VISA_ISSUE_DATE_MANUAL,FCR_VISAISSDATE,EIDA_VISAISSDATE,
						MANUAL_VISAISSDATE,"Visa Issue")) {
					return false;
				}
				if(!ValidateComparisonData(CHECKBOX_VISA_EXPIRY_DATE_FCR,CHECKBOX_VISA_EXPIRY_DATE_EIDA,CHECKBOX_VISA_EXPIRY_DATE_MANUAL,FCR_VISAEXPDATE,EIDA_VISAEXPDATE,
						MANUAL_VISAEXPDATE,CA0137,"Optional","Visa Expiry Date")) {
					return false;
				}
				if(!validatePastDates(CHECKBOX_VISA_EXPIRY_DATE_FCR,CHECKBOX_VISA_EXPIRY_DATE_EIDA,CHECKBOX_VISA_EXPIRY_DATE_MANUAL,FCR_VISAEXPDATE,EIDA_VISAEXPDATE,
						MANUAL_VISAEXPDATE,"Visa Expiry")) {	
					return false;
				}		
				if(!sAccRelation.equalsIgnoreCase("Minor")) {
					if(!ValidateComparisonDataCombo(CHECKBOX_PROFESSION_FCR,CHECKBOX_PROFESSION_EIDA,CHECKBOX_PROFESSION_MANUAL,FCR_PROFESSION,
							EIDA_PROFESSION,MANUAL_PROFESSION,CA075,"Mandatory","Profession")) {
						return false;
					}
				}
				if(!ValidateComparisonDataCombo(CHECKBOX_GENDER_FCR,CHECKBOX_GENDER_EIDA,CHECKBOX_GENDER_MANUAL,FCR_GENDER,EIDA_GENDER,
						MANUAL_GENDER,CA041,"Mandatory","Gender")) { 
					return false;
				}
				if(!ValidateComparisonData(CHECKBOX_EMP_NAME_FCR,CHECKBOX_EMP_NAME_EIDA,CHECKBOX_EMP_NAME_MANUAL,FCR_EMPLYR_NAME,EIDA_EMPLYR_NAME,
						MANUAL_EMPLYR_NAME,CA0145,"Optional","Employer Name")) {
					return false;
				}
				if(!validatePassportType(CHECKBOX_PASSPORT_TYPE_FCR,CHECKBOX_PASSPORT_TYPE_EIDA,CHECKBOX_PASSPORT_TYPE_MANUAL,FCR_PASSTYPE,EIDA_PSSTYPE,
						MANUAL_PASSTYPE,CA018,HD_PASS_TYPE)) {
					return false;
				}
				if(!ValidateComparisonDataCombo(CHECKBOX_VISA_STATUS_FCR,CHECKBOX_VISA_STATUS_EIDA,
						CHECKBOX_VISA_STATUS_MANUAL,FCR_VISASTATUS,EIDA_VISASTATUS,
						MANUAL_VISASTATUS,CA019,"Mandatory","Visa Status")) {
					return false;
				}
				int sSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;				
				String sQuery1 = "SELECT IS_DEDUPE_CLICK FROM USR_0_CUST_TXN WHERE WI_NAME ='"+sWorkitemId+"' and "
						+ "cust_sno='"+sSelectedRow+"'";
				logInfo("saveAndNextValidations"," sQuery1 dedupeDone: "+sQuery1);
				List<List<String>> output1 = formObject.getDataFromDB(sQuery1);
				logInfo("saveAndNextValidations"," output1 dedupeDone"+output1);
				String dedupeDone = "false";
				if(output1.size()>0) {
					dedupeDone = output1.get(0).get(0);
				}
				logInfo("saveAndNextValidations","dedupeDone"+dedupeDone);
				if(!dedupeDone.equalsIgnoreCase("true")) {
					sendMessageValuesList(BTN_DEDUPE_SEARCH,"Please Do Dedupe Search For This Customer.");
					return false;
				}
				if(!validateVisaStatus()) {
					return false;
				}
				logInfo("saveAndNextValidations"," cust_prefix dedupeDone: ");
				if(!formObject.getValue(CUST_PREFIX).toString().equalsIgnoreCase("Others")){
					formObject.setStyle(PD_OTHERS, DISABLE, TRUE);
				}
				if(!formObject.getValue(MARITAL_STATUS).toString().equalsIgnoreCase("Others")){
					formObject.setStyle(PD_MARITALSTATUSOTHER, DISABLE, TRUE);
				}
// 				Gurwinder 18082023 alert fix please fill mobile number
				if (formObject.getValue(CHECKBOX_TELE_MOB_FCR).toString()
						.equalsIgnoreCase(TRUE)) {
					formObject.setValue(CP_MOBILE, formObject.getValue(FCR_MOBILE).toString());
				}
				if (formObject.getValue(CHECKBOX_TELE_MOB_EIDA).toString()
						.equalsIgnoreCase(TRUE)) {
					formObject.setValue(CP_MOBILE, formObject.getValue(EIDA_MOBILE).toString());
				}
				if (formObject.getValue(CHECKBOX_TELE_MOB_MANUAL).toString()
						.equalsIgnoreCase("True")) {
					formObject.setValue(CP_MOBILE, formObject.getValue(MANUAL_MOBILE).toString());
				}
//		 		Gurwinder 18082023 alert fix please fill mobile number
				/*if(!validateMobileNo(CHECKBOX_TELE_MOB_FCR,CHECKBOX_TELE_MOB_EIDA,CHECKBOX_TELE_MOB_MANUAL,
						FCR_MOBILE,EIDA_MOBILE,MANUAL_MOBILE,CA0126)) {
					if(!mobileConfirmFlag || mobileChangeFlag) {
						return false;
					}
				}*/
			} else if(sheetIndex == 2) {
				logInfo("saveAndNextValidations", "sheet 2");
				if(formObject.getValue(CUST_PREFIX).toString().equalsIgnoreCase("")) {
					sendMessageValuesList(CUST_PREFIX,CA037);
					return false;
				} 
				if(formObject.getValue(CUST_PREFIX).toString().equalsIgnoreCase("Others")) {
					if(formObject.getValue(PD_OTHERS).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(PD_OTHERS,CA038);
						return false;
					}
				} 
				if(!formObject.getValue(CUST_PREFIX).toString().equalsIgnoreCase("")) {
					if(!formObject.getValue(CUST_PREFIX).toString().equalsIgnoreCase("Others")) {
						formObject.setStyle(PD_OTHERS, DISABLE, TRUE);
					}
				} 

				if(formObject.getValue(PD_FULLNAME).toString().equalsIgnoreCase("")) {
					sendMessageValuesList(PD_FULLNAME,CA011);
					return false;
				} 
				if(formObject.getValue(PD_DOB).toString().equalsIgnoreCase("")) {
					sendMessageValuesList(PD_DOB,CA012);
					return false;
				} else {
					if(!validateDOB(PD_DOB)) {
						return false;
					}
				} 
				if(formObject.getValue(CUST_NATIONALITY).toString().equalsIgnoreCase("")) {
					sendMessageValuesList(CUST_NATIONALITY,CA013);
					return false;
				} 
				if(sBankRelation.equalsIgnoreCase("New")) {
					if(formObject.getValue(PD_MOTHERMAIDENNAME).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(PD_MOTHERMAIDENNAME,CA040);
						return false;
					}
				}
				if(formObject.getValue(CUST_GENDER).toString().equalsIgnoreCase("")) {
					sendMessageValuesList(CUST_GENDER,CA041);
					return false;
				} 
				if(formObject.getValue(PD_CUSTSEGMENT).toString().equalsIgnoreCase("")) {
					sendMessageValuesList(PD_CUSTSEGMENT,CA042);
					return false;
				}
				if(formObject.getValue(RES_CNTRY).toString().equalsIgnoreCase("") 
						|| formObject.getValue(RES_CNTRY).toString().equalsIgnoreCase(".")) {
					sendMessageValuesList(RES_CNTRY,CA0185);
					return false;
				}
				if(formObject.getValue(PERM_CNTRY).toString().equalsIgnoreCase("") 
						|| formObject.getValue(PERM_CNTRY).toString().equalsIgnoreCase(".")) {
					sendMessageValuesList(PERM_CNTRY, CA0186);
					return false;
				}
				if(formObject.getValue(CORR_CNTRY).toString().equalsIgnoreCase("") 
						|| formObject.getValue(CORR_CNTRY).toString().equalsIgnoreCase(".")) {
					sendMessageValuesList(CORR_CNTRY,CA0187);
					return false;
				}
				String sVisaType = returnVisaStatus();
				if(sVisaType.equalsIgnoreCase("Residency Visa")
						&& formObject.getValue(DRP_RESEIDA).toString().equalsIgnoreCase("")) {
					sendMessageValuesList(DRP_RESEIDA,"Please select Value of Resident without EIDA");
					return false;
				}
				if(formObject.getValue(DRP_RESEIDA).toString().equalsIgnoreCase("no") 
						&& sVisaType.equalsIgnoreCase("Residency Visa")) {
					if(!ValidateComparisonData(CHECKBOX_EIDANO_FCR,CHECKBOX_EIDANO_EIDA,CHECKBOX_EIDANO_MANUAL,FCR_EIDANO,EIDA_EIDANO,
							MANUAL_EIDANO,CA0167,"Mandatory","EIDA number")) {
						return false;
					}
				}	
				if(!mandatoryContactInfo()) {
					return false;
				}
				if(!validateOfficeNo(CP_TELEOFFICE,CR0003)) {
					return false;
				}
				String sFinalPermanentCountry = getFinalData(formObject.getValue(CHECKBOX_COUNTRY_PER_RES_FCR).toString(),
						formObject.getValue(CHECKBOX_COUNTRY_PER_RES_EIDA).toString(),formObject.getValue(CHECKBOX_COUNTRY_PER_RES_MANUAL).toString(),
						formObject.getValue(FCR_PER_CNTRY).toString(),formObject.getValue(EIDA_PER_CNTRY).toString(),
						formObject.getValue(MANUAL_PER_CNTRY).toString());
				if(!sFinalPermanentCountry.equalsIgnoreCase(formObject.getValue(PERM_CNTRY).toString())) {
					sendMessageValuesList(PERM_CNTRY,"Permanent Country data is not same. Please change it.");
					return false;
				}
				if(!formObject.getValue(CUST_NATIONALITY).toString().equalsIgnoreCase("United Arab Emirates")) {
					if(!mandatoryEmploymentInfo()) {
						return false;
					}
				}
			} else if(sheetIndex == 3) {
				logInfo("saveAndNextValidations", "sheet 3");
				if(!mandatoryiKYCDDE()) {
					return false;
				}
				if(!checkSalaryTransfer()){
					return false;
				}
				saveKycMultiDropDownData();
			} else if(sheetIndex == 5) {
				logInfo("saveAndNextValidations", "sheet 4");
				/*if(formObject.getValue(CITYBIRTH_MANUAL).toString().equalsIgnoreCase("")) {
				logInfo("saveAndNextValidations","Inside CITYBIRTH_MANUAL Condition");
				sendMessageValuesList(CITYBIRTH_MANUAL,"Please enter city of birth");
				return true;
			}
			if(formObject.getValue(CITYBIRTH_MANUAL).toString().equalsIgnoreCase("")) {
				sendMessageValuesList(CITYBIRTH_MANUAL,"Please enter city of birth");
				return true;
			}*/
				if(!mandatoryAtFatca()) {
					return false;
				}			
			} else if(sheetIndex == 4) {
				logInfo("saveAndNextValidations", "sheet 5");
				String custSegment = formObject.getValue(PD_CUSTSEGMENT).toString(); 
				List<List<String>> output = formObject.getDataFromDB("select iscrsmandatory from usr_0_cust_segment "
						+ "where cust_segment='"+custSegment+"'");
				String ismandatory = "";
				if(output.size()>0) {
					ismandatory = output.get(0).get(0);
				}
				logInfo("saveAndNextValidations","CUST Segment result: "+ismandatory);
				boolean custSegmentCheck;
				if(ismandatory.equalsIgnoreCase("Yes"))
					custSegmentCheck=true;
				else
					custSegmentCheck=false;
				if(!mandatoryCRSCheck(custSegmentCheck)) {
					return false;
				}
				if(getGridCount(CRS_TAXCOUNTRYDETAILS) > 0 ) {
					for(int i=0 ; i<getGridCount(CRS_TAXCOUNTRYDETAILS);i++) {
						String countryOfTaxResidency=formObject.getTableCellValue(CRS_TAXCOUNTRYDETAILS,i,1);
						logInfo("saveAndNextValidations","Value of selected Tax Residency country"+ countryOfTaxResidency);
						if (countryOfTaxResidency.equalsIgnoreCase("UNITED ARAB EMIRATES")) {
							if(!MandatoryCRSDueDiligence()) {
								return false;
							}
							break;
						}
					}
				}
			}
		} else if(sActivityName.equalsIgnoreCase(ACTIVITY_DDE_ACCOUNT_INFO)) {
			logInfo("Inside SaveandNextValidation","sActivityName: "+sActivityName);
			String sQuery ="";
			List<List<String>> output = null;
			if(sheetIndex == 8) {  //Acct info tab
				if(!(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only") ||
						formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Permanent Reject - Discard")||
							formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade"))) {
					// added above condition 
					if(formObject.getValue(ACC_TITLE).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(ACC_TITLE, "Please fill Account Title.");
						return false;
					}
					int iRows = getGridCount(PRODUCT_QUEUE);	
					boolean isEtihad = false;					
					String sProdCode = "";
					String sChequebook = "";
					if(!formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade")){
					if(iRows < 1) {
						sendMessageValuesList(PRODUCT_QUEUE, "Please add atleast one product.");
						return false;
					}
				}
					sQuery= "SELECT PRODUCT_CODE FROM USR_0_PRODUCT_OFFERED WHERE WI_NAME ='"+sWorkitemId+"'";	
					output = formObject.getDataFromDB(sQuery);
					String sProduct = "";
					if(output.size() > 0) {
						for(int i=0; i<output.size(); i++) {
							sProduct = sProduct+output.get(i).get(0)+",";
						}
					}
					logInfo("submitDDEValidations", "product list offered: "+sProduct);
					for(int i=0;i<iRows;i++) {
						sProdCode = formObject.getTableCellValue(PRODUCT_QUEUE, i, 1);
						sChequebook = formObject.getTableCellValue(PRODUCT_QUEUE, i, 6);
						logInfo("submitDDEValidations", "row: "+i+", sProdCode: "+sProdCode+", sChequebook: "
								+sChequebook);
						if(sProdCode.equalsIgnoreCase("")) {
							sendMessageValuesList(PRODUCT_QUEUE, "Blank Row is not allowed.");
							//formObject.setNGSelectedTab("Tab5",4); //handle in js
							return false;
						}
						if(sProduct.indexOf(sProdCode) == -1) {
							sendMessageValuesList(PRODUCT_QUEUE,"Product code "
									+sProdCode+" is not matching in the offered product list");
							//formObject.setNGSelectedTab("Tab5",4); //handle in js
							return false;
						}
						if(sChequebook.isEmpty()) {
							sendMessageValuesList(PRODUCT_QUEUE, "Please fill Chequebook required.");
							//formObject.setNGSelectedTab("Tab5",4); //handle in js
							return false;
						}
						formObject.setTableCellValue(PRODUCT_QUEUE, i, 14, String.valueOf(i+1));
					}
					for(int i=0; i<iRows; i++) {
						sProdCode = formObject.getTableCellValue(PRODUCT_QUEUE, i, 1);
						sQuery = "SELECT SUB_PRODUCT_TYPE FROM USR_0_PRODUCT_TYPE_MASTER WHERE PRODUCT_CODE ='"+sProdCode+"'";
						output = formObject.getDataFromDB(sQuery);
						if(output.size() > 0 && output.get(0) != null && !output.get(0).get(0).equalsIgnoreCase("")) {
							if(output.get(0).get(0).equalsIgnoreCase("Etihad")) {
								isEtihad = true;
								break;
							}
						}
					}
					if(isEtihad) {
						if(formObject.getValue(EXISTING_ETIHAD_CUST).toString().isEmpty()) {
							sendMessageValuesList(EXISTING_ETIHAD_CUST, "Please Select Etihad Status.");
							return false;
						} else if(formObject.getValue(EXISTING_ETIHAD_CUST).toString().equalsIgnoreCase("Yes")) {
							if(formObject.getValue(ETIHAD_NO).toString().equalsIgnoreCase("")) {
								sendMessageValuesList(ETIHAD_NO,"Please fill Etihad Number.");
								return false;
							} else if(formObject.getValue(ETIHAD_NO).toString().equalsIgnoreCase("0")) {
								sendMessageValuesList(ETIHAD_NO,"Please validate Etihad Number.");
								return false;
							}
						}
					}
					if(formObject.getValue(SOURCE_NAME).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(SOURCE_NAME, "Please Select Source Name.");
						return false;
					}
					if(formObject.getValue(SOURCE_CODE).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(SOURCE_CODE,"Please Select Source Code.");
						formObject.setStyle(BTN_SUBMIT, DISABLE, FALSE);
						return false;
					}
					boolean result = validateDebitDetails();				
					if(!result) {
						formObject.setStyle(BTN_SUBMIT, DISABLE, FALSE);
						return false;
					}	
				}
				try {
					if(formObject.getValue(CHANNEL_TYPE).toString().equalsIgnoreCase("Alternate")
							&& sActivityName.equalsIgnoreCase("DDE_Account_Info")) {
						if(formObject.getValue(INSTANT_DEL_YES).toString().equalsIgnoreCase("true") 
								&& formObject.getValue(DFC_STATIONERY_AVAIL).toString().equalsIgnoreCase("")) {
							sendMessageValuesList(DFC_STATIONERY_AVAIL,"Please select DFC Stationary Available");
							//edit button enable removed
							formObject.setStyle(BTN_SUBMIT, DISABLE, FALSE);
							return false;
						}
						sQuery = "SELECT COUNT(1) AS COUNT FROM USR_0_AO_DEBITCARD WHERE LODGEMENT_REF_NO='"+formObject.getValue(LODGEMENT_NO)+"' and CARDTYPE is not null";
						output = formObject.getDataFromDB(sQuery);
						int sCount = Integer.parseInt(output.get(0).get(0));
						if(sCount != 0) {
							String sQuery2 = "SELECT COUNT(1) AS COUNT FROM DEBIT_CARD_REP WHERE WI_NAME='"+sWorkitemId+"'";
							output = formObject.getDataFromDB(sQuery);
							int sCount2 = Integer.parseInt(output.get(0).get(0));
							if(sCount2==0) {
								sendMessageValuesList("","User has requested debit card.");
							}
						}
					} 
				} catch(Exception e) {	
					logError("", e);
				}
				boolean prodChangeFlag = checkProdChngForNoEligibility();
				if(!prodChangeFlag) {
					sendMessageValuesList("", "Customer is not eligible for cheque book. Please change the product");
					return false;
				}
				insertUdfDetails();	
			} else if(sheetIndex == 12 || sheetIndex == 10){
				saveStandingInstrInfo();
				saveStandInstrInfo();
			}  else if(sheetIndex == 11) { // category change tab
				if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase
						("New Account with Category Change") || 
						formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase
						("Category Change Only")
						|| formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade")) {
					boolean resultCategory = mandatoryCategoryChangeData();
					if(!resultCategory) {
						return false;
					}
					resultCategory = checkNatCatSegment();
					if(!resultCategory) {
						return false;
					}
				}
			} else if(sheetIndex == 16) { // Family Banking 
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
		}
		return true;
	}
	
}
