package com.newgen.iforms.user.ao;

import java.io.File; 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.newgen.iforms.EControl;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.iforms.user.config.ConstantAlerts;
import com.newgen.iforms.user.config.Constants;
import com.newgen.iforms.user.ao.Common;
import com.newgen.iforms.user.ao.util.XMLParser;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.newgen.mvcbeans.model.WorkdeskModel;
import com.newgen.mvcbeans.model.wfobjects.WorkItemBean;

public class AccountRelation extends Common implements Constants,ConstantAlerts,
IFormServerEventHandler {

	String sOnLoad = FALSE;
	int sCurrTabIndex;

	public AccountRelation(IFormReference formObject) {
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
	public String executeServerEvent(IFormReference formObject,String controlName, String eventType, String data) {
		logInfo("onLoadAcctRelation","Inside executeServerEvent Account_Relation >");
		logInfo("onLoadAcctRelation","Event: " + eventType + ", Control Name: " + controlName+ ", Data: " + data);
		sendMessageList.clear();
		logInfo("onLoadAcctRelation","sendmessagelist49: "+sendMessageList);
		String retMsg = getReturnMessage(true,controlName);
		try {
			if (eventType.equalsIgnoreCase(EVENT_TYPE_LOAD)) {
				int gridCount = getGridCount(ACC_RELATION);//ACC_RELATION
				logInfo("onLoadAcctRelation","SEC_ACC_REL gridCount :: "+ gridCount);
				if(gridCount>0){
					if(((String) formObject.getValue(ACC_OWN_TYPE)).equalsIgnoreCase("Minor") || ((String) formObject.getValue(ACC_OWN_TYPE)).equalsIgnoreCase("Single")){
//						formObject.setStyle(TABLE33_ACC_RELATION,DISABLE,TRUE);
					}
				}
				setRepeaterRowInOrder();
				updateAccRelAuthData(sWorkitemId);
				//added on 03/10/2022 by sudansu
				//setBankRelnData();
				String sQuery="select PROD_CODE,CURRENCY from usr_0_product_selected where wi_name='"+sWorkitemId+"'";
				logInfo("onLoadAcctRelation","usr_0_product_selectedmohit  sQuery acc relation..."+sQuery);
				List<List<String>> sOutput=formObject.getDataFromDB(sQuery);
				logInfo("onLoadAcctRelation","sOutput..."+sOutput);	
				populateAuditSearch(SEARCH_DETAILS_LVW);
				String sQuery1 = "SELECT SUM(COUNT_WI) AS COUNT_WI FROM (SELECT COUNT(WI_NAME) AS COUNT_WI FROM USR_0_INTEGRATION_CALLS WHERE WI_NAME ='"+ sWorkitemId +"' AND (CALL_NAME LIKE 'CUSTOMER_CREATION%' OR CALL_NAME LIKE 'CUSTOMER_MODIFY%' OR CALL_NAME LIKE 'ACCOUNT_CREATION%') AND STATUS='Success' UNION SELECT COUNT(WI_NAME) AS COUNT_WI FROM "+sExtTable+" WHERE WI_NAME ='"+ sWorkitemId +"' AND (COMP_DEC ='Negative Advisory' OR ACCOUNT_INFO_CHECKER_DEC ='Permanent Reject - Discard'))";
				List sOutput1=formObject.getDataFromDB(sQuery1);
				List<List<String>> list = formObject.getDataFromDB(sQuery1);
				if (list != null && !(list.size()>0)) {
					String countWI = list.get(0).get(0);
					if(!countWI.equalsIgnoreCase("0")){
						sendMessageValuesList(ACC_RELATION,countWI);
						int iRows = getGridCount(ACC_RELATION);
						for(int i=1;i<iRows;i++){
							formObject.setStyle(TABLE33_BANK_RELATION,DISABLE,TRUE);
//							formObject.setStyle(TABLE33_ACC_RELATION,DISABLE,TRUE);
						}
					}
				} else {
					int iRows = getGridCount(ACC_RELATION);  //SEC_ACC_REL
					for(int i=1;i<iRows;i++){
						formObject.setStyle(TABLE33_BANK_RELATION,DISABLE,TRUE);
					}
				}		
				if (getGridCount(LISTVIEW_DECISION) == 0) {
					String sQuery2 = "SELECT CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,CHANNEL,WS_NAME,WS_COMMENTS FROM (SELECT TO_CHAR(CREATE_DAT,'DD/MM/YYYY HH:MI:SS AM')CREATE_DAT, USERID, USERNAME, GROUP_NAME, WS_DECISION,REJ_REASON, CHANNEL, WS_NAME, WS_COMMENTS,TO_CHAR(CREATE_DAT,'YYYYMMDDHH24:MI:SS') A FROM USR_0_AO_DEC_HIST WHERE WI_NAME = '"
							+ sWorkitemId + "') ORDER BY A";
					List recordList = formObject.getDataFromDB(sQuery2);
					loadListView(recordList,
							"CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,CHANNEL,WS_NAME,WS_COMMENTS",LISTVIEW_DECISION);
				}	
				populateUAEPassInfoStatus(sWorkitemId);
//				setHeaderAccRelData();
//				setWMSId();
				//added on 03/10/2022 by sudansu
				setUaePassDecisionCombo();
				updateAccRelGridFromUaePassInfo(sWorkitemId);
				populatePreAssesmentDetails();

			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)) {
				if (ONCLICKTAB1.equalsIgnoreCase(controlName)) {
					if (getGridCount(LISTVIEW_DECISION) == 0) {
						String sQuery1 = "SELECT CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,CHANNEL,WS_NAME,WS_COMMENTS FROM (SELECT TO_CHAR(CREATE_DAT,'DD/MM/YYYY HH:MI:SS AM')CREATE_DAT, USERID, USERNAME, GROUP_NAME, WS_DECISION,REJ_REASON, CHANNEL, WS_NAME, WS_COMMENTS,TO_CHAR(CREATE_DAT,'YYYYMMDDHH24:MI:SS') A FROM USR_0_AO_DEC_HIST WHERE WI_NAME = '"
								+ sWorkitemId + "') ORDER BY A";
						List recordList = formObject.getDataFromDB(sQuery1);
						loadListView(recordList, 
								"CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,CHANNEL,WS_NAME,WS_COMMENTS",LISTVIEW_DECISION);
					}	
				} else if(controlName.equalsIgnoreCase(CRO_DEC)){
					if(controlName.equalsIgnoreCase(CRO_DEC) && ((String) formObject.getValue(CRO_DEC)).equalsIgnoreCase("Approve")){
						formObject.setStyle(CRO_REJ_REASON,ENABLE,FALSE);
					} else {
						formObject.setStyle(CRO_REJ_REASON,ENABLE,TRUE);
					}
				} else if(controlName.equalsIgnoreCase(BTN_FETCH_EIDA_INFO)){
					logInfo("BTN_FETCH_EIDA_INFO","data : "+data);
					String sResult = data;
					if(sResult.equalsIgnoreCase("")) {
						sendMessageValuesList("", "No Data Returned");
					} else if(sResult.equalsIgnoreCase("Error while Reading!!")){
						sendMessageValuesList("", "Error while Reading Card");
					} else {
						/*	logInfo("BTN_FETCH_EIDA_INFO","XML PArser ");
						xmlDataParser.setInputXML(sResult);
						logInfo("BTN_FETCH_EIDA_INFO","XML PArser gettign value ");
						String valEIDA[] = xmlDataParser.getValueOf("Val").split("#");
						String sValue="";
						String sEIDANo="";
						String sOutput ="";
						String[] sTemp;
						String sColumn = "NATIONALITY,CUST_NAME,EIDANO,DOB,PASSPORTNO,PASSPORTISSDATE,PASSPORTEXPDATE,"
								+ "PASSPORTEXPPLACE,MOTHERNAME,ADDRESS,CITY,STATE,MOBILE,PHONE_NO,EMAIL,PROFESSION,PREFIX,"
								+ "EMP_NAME,GENDER,OCCUPATION,PASSPORT_TYPE,EIDA_EXP_DATE,VISA_NO,VISA_EXP_DATE,VISA_ISSUE_DATE,WI_NAME";
						for(int i=0; i<valEIDA.length; i++)
						{
							sTemp = valEIDA[i].split("=");
							if(i==3 || i==5 || i==6 || i==21 || i==23)
							{
								logInfo("BTN_FETCH_EIDA_INFO","sTemp[0]----"+sTemp[0]);
								if(!sTemp[0].equalsIgnoreCase(""))
								{
									sValue = sValue+convertInTo_To_Date(sTemp[0])+",";
								}
								else
								{
									sValue = sValue+"'"+sTemp[0]+"'"+",";
								}
							}
							else if(i==11)
							{
								sValue = sValue+"'"+sTemp[0].toUpperCase()+"'"+",";
							}
							else if(sTemp[0].indexOf("+")!=-1)
							{
								sValue = sValue+"'"+sTemp[0].substring(1,sTemp[0].length())+"'"+",";
							}
							else
							{
								sValue = sValue+"'"+sTemp[0]+"'"+",";
								if(i==2)
								{
									sEIDANo = sTemp[0];
								}
							}
						}
						String sIssuedDate ="";
						if(valEIDA[23].indexOf("=")!=0)
						{
							sIssuedDate = valEIDA[23].substring(0,valEIDA[23].indexOf("="));
							sTemp = sIssuedDate.split("/");	
							sIssuedDate = sTemp[0]+"/"+getMonthAlpha(sTemp[1])+"/"+(Integer.parseInt(sTemp[2])-3);	
						}
						sValue=sValue+"'"+sIssuedDate+"'"+","+"'"+sWorkitemId+"'";
						logInfo("BTN_FETCH_EIDA_INFO","sValue---"+sValue);
						String tmpValues=sValue.replaceAll("'","");
						tmpValues=tmpValues.replaceAll(",","");
						if(!tmpValues.equalsIgnoreCase(sWorkitemId))
						{
							String delQuery = "DELETE FROM USR_0_EIDACARD_DETAILS WHERE WI_NAME  = '"+sWorkitemId+"' AND  EIDANO ='"+sEIDANo+"'";
							logInfo("BTN_FETCH_EIDA_INFO"," delQuery : " + delQuery);
							formObject.saveDataInDB(delQuery);
							insertDataIntoDB("USR_0_EIDACARD_DETAILS",sColumn,sValue);						
						}
						String sQuery = "SELECT EIDANO,CUST_NAME"
							+ ",to_char(DOB,'"+DATEFORMAT+"') DOB,PASSPORTNO"
									+ ",to_char(EIDA_EXP_DATE,'"+DATEFORMAT+"') "
											+ "EIDA_EXP_DATE,(SELECT COUNTRY FROM USR_0_COUNTRY_MAST WHERE COUNTRY_CODE  = (SELECT COUNTRY_CODE FROM USR_0_EIDA_COUNTRY WHERE EIDA_CODE = NATIONALITY)) NATIONALITY FROM USR_0_EIDACARD_DETAILS where wi_name='"+ sWorkitemId +"' AND EIDANO ='"+sEIDANo+"'";
						logInfo("BTN_FETCH_EIDA_INFO","sQuery : "+sQuery);
						List<List<String>> sOutput1 = formObject.getDataFromDB(sQuery);
						if(sOutput1 != null  && sOutput1.size()>0 ){
							String sEidaNo = sOutput1.get(0).get(0);
							String sName = sOutput1.get(0).get(1);
							String sDOB = sOutput1.get(0).get(2);
							String sPassportNo = sOutput1.get(0).get(3);
							String sExpairyDate = sOutput1.get(0).get(4);
							String sNationality = sOutput1.get(0).get(5);
							if(sNationality!=null){
								sNationality = sNationality.toUpperCase();
							}
							String columns = "EIDANO,CUSTOMERNAME,DOB,PASSPORTNO,EIDAEXPDATE";
							String values = sEidaNo +"##"+sName +"##"+sDOB +"##"+ sPassportNo+"##"+sExpairyDate;
							if(!(sEidaNo.equalsIgnoreCase("") && sName.equalsIgnoreCase("")&& 
									sDOB.equalsIgnoreCase("")&& sPassportNo.equalsIgnoreCase("") 
									&& sExpairyDate.equalsIgnoreCase(""))){
								LoadListViewWithHardCodeValues("eida_details", columns, values);
								formObject.setValue(NEW_CUST_NAME,sName);
								formObject.setValue(NEW_CUST_DOB,sDOB);
								formObject.setValue(NEW_CUST_NATIONALITY,sNationality);
						}else
						{
							sendMessageValuesList("", "No Data Returned");
							formObject.clearTable("eida_details");
						}
					}else
					{
						sendMessageValuesList("", "No Data Returned");
						formObject.clearTable("eida_details");
					}

					}*/

						String snationality = "";
						String scustomername = "";
						String seidanumber = "";
						String sdob = "";
						String spassportnumber = "";
						String spassportissuedate = "";
						String spassportexpirydate = "";
						String spassportexpiryplace = "";
						String smothername = "";
						String saddress = "";
						String scity = "";
						String sstate = "";
						String smobile = "";
						String sphonenumber = "";
						String semail = "";
						String sprofession = "";
						String sprefix = "";
						String semployeename = "";
						String sgender = "";
						String soccupation = "";
						String spassporttype = "";
						String seidaexpirydate = "";
						String svisanumber = "";
						String svisaexpirydate = "";
						String svisaissuedate  = "";
						JSONParser parser = new JSONParser();
						JSONObject jobj;
						try {
							jobj = (JSONObject)parser.parse(data);
							snationality = ((String)jobj.get("nationality") != null ?  ((String)jobj.get("nationality")).toUpperCase() : "");
							scustomername = ((String)jobj.get("fullName") != null ?  ((String)jobj.get("fullName")).toUpperCase() : "");
							spassportexpiryplace = ((String)jobj.get("passportCountry") != null ?  ((String)jobj.get("passportCountry")).toUpperCase() : "");
							smothername = ((String)jobj.get("motherFullName") != null ?  ((String)jobj.get("motherFullName")).toUpperCase() : "");
							scity = ((String)jobj.get("city") != null ?  ((String)jobj.get("city")).toUpperCase() : "");
							sstate = ((String)jobj.get("emirate") != null ?  ((String)jobj.get("emirate")).toUpperCase() : "");
							smobile = ((String)jobj.get("mobile") != null ?  ((String)jobj.get("mobile")).toUpperCase() : "");
							sprofession = ((String)jobj.get("occupation") != null ?  ((String)jobj.get("occupation")).toUpperCase() : "");
							semployeename = ((String)jobj.get("companyName") != null ?  ((String)jobj.get("companyName")).toUpperCase() : "");
							soccupation = ((String)jobj.get("occupation") != null ?  ((String)jobj.get("occupation")).toUpperCase() : "");
							spassporttype = ((String)jobj.get("passportType") != null ?  ((String)jobj.get("passportType")).toUpperCase() : "");
							sgender = ((String)jobj.get("sex") != null ?  ((String)jobj.get("sex")).toUpperCase() : "");

							seidanumber = ((String)jobj.get("eidaNumber") != null ?  ((String)jobj.get("eidaNumber")) : "");
							sdob = ((String)jobj.get("dob") != null ?  ((String)jobj.get("dob")) : "");
							spassportnumber = ((String)jobj.get("passportNumber") != null ?  ((String)jobj.get("passportNumber")) : "");
							spassportissuedate = ((String)jobj.get("passportIssueDate") != null ?  ((String)jobj.get("passportIssueDate")) : "");
							spassportexpirydate = ((String)jobj.get("passportExpiryDate") != null ?  ((String)jobj.get("passportExpiryDate")) : "");
							saddress = ((String)jobj.get("pobox") != null ?  ((String)jobj.get("pobox")) : "");
							sphonenumber = ((String)jobj.get("phoneNumber") != null ?  ((String)jobj.get("phoneNumber")) : "");
							semail = ((String)jobj.get("email") != null ?  ((String)jobj.get("email")) : "");
							sprefix = ((String)jobj.get("title") != null ?  ((String)jobj.get("title")) : "");
							seidaexpirydate = ((String)jobj.get("expiryDate") != null ?  ((String)jobj.get("expiryDate")) : "");
							svisanumber = ((String)jobj.get("residencyNumber") != null ?  ((String)jobj.get("residencyNumber")) : "");
							svisaexpirydate = ((String)jobj.get("residencyExpiryDate") != null ?  ((String)jobj.get("residencyExpiryDate")) : "");

							/*
							seidanumber = (String)jobj.get("eidaNumber");
							sdob = (String)jobj.get("dob");
							spassportnumber = (String)jobj.get("passportNumber");
							spassportissuedate = (String)jobj.get("passportIssueDate");
							spassportexpirydate = (String)jobj.get("passportExpiryDate");
							saddress = (String)jobj.get("pobox");
							sphonenumber = (String)jobj.get("phoneNumber");
							semail = (String)jobj.get("email");
							sprefix = (String)jobj.get("title");
							seidaexpirydate = (String)jobj.get("expiryDate");
							svisanumber = (String)jobj.get("residencyNumber");
							svisaexpirydate = (String)jobj.get("residencyExpiryDate"); */

							/*

							spassportexpiryplace = ((String)jobj.get("passportCountry")).toUpperCase();
							smothername = ((String)jobj.get("motherFullName")).toUpperCase();
							scity = ((String)jobj.get("city")).toUpperCase();
							sstate = ((String)jobj.get("emirate")).toUpperCase();
							smobile = ((String)jobj.get("mobile")).toUpperCase();
							sprofession = ((String)jobj.get("occupation")).toUpperCase();
							semployeename = ((String)jobj.get("companyName")).toUpperCase();
							soccupation = ((String)jobj.get("occupation")).toUpperCase();
							spassporttype = ((String)jobj.get("passportType")).toUpperCase();
							sgender = (String)jobj.get("sex");

							 */
							//scustomername = ((String)jobj.get("fullName")).toUpperCase();

							if(sdob != null) {
								sdob = sdob.replaceAll("-", "/");
							}

							if(spassportissuedate != null) {
								spassportissuedate = spassportissuedate.replaceAll("-", "/");
							}

							if(spassportexpirydate != null) {
								spassportexpirydate = spassportexpirydate.replaceAll("-", "/");
							} 
							if(seidaexpirydate != null) {
								seidaexpirydate = seidaexpirydate.replaceAll("-", "/");
							} 
							if(svisaexpirydate != null) {
								svisaexpirydate = svisaexpirydate.replaceAll("-", "/");
							}
							svisaissuedate  = "";//(String)jobj.get("visaissuedate");
							if(svisaissuedate != null) {
								svisaissuedate = svisaissuedate.replaceAll("-", "/");
							}

							if(snationality != null){
								snationality = snationality.toUpperCase();
							}
							if(scity!=null){
								scity = scity.toUpperCase();
							}
							if(sstate!=null){
								sstate = sstate.toUpperCase();
							}
							if("M".equalsIgnoreCase(sgender)){
								sgender = "Male";
							}else if ("F".equalsIgnoreCase(sgender)){
								sgender = "Female";
							}

							if(scustomername != null){
								scustomername = scustomername.replace(",", " ").replace("  ", " ").toUpperCase();
							}
							if(smothername != null){
								smothername = smothername.replace(",", " ").replace("  ", " ").toUpperCase();
							}

							/*	snationality = (String)jobj.get("nationality");
							scustomername = (String)jobj.get("customername");
							seidanumber = (String)jobj.get("eidanumber");
							sdob = (String)jobj.get("dob");
							if(sdob != null) {
								sdob = sdob.replaceAll("-", "/");
							}
							spassportnumber = (String)jobj.get("passportnumber");
							spassportissuedate = (String)jobj.get("passportissuedate");
							if(spassportissuedate != null) {
								spassportissuedate = spassportissuedate.replaceAll("-", "/");
							}
							spassportexpirydate = (String)jobj.get("passportexpirydate");
							if(spassportexpirydate != null) {
								spassportexpirydate = spassportexpirydate.replaceAll("-", "/");
							}
							spassportexpiryplace = (String)jobj.get("passportexpiryplace");
							smothername = (String)jobj.get("mothername");
							saddress = (String)jobj.get("address");
							scity = (String)jobj.get("city");
							sstate = (String)jobj.get("state");
							smobile = (String)jobj.get("mobile");
							sphonenumber = (String)jobj.get("phonenumber");
							semail = (String)jobj.get("email");
							sprofession = (String)jobj.get("profession");
							sprefix = (String)jobj.get("prefix");
							semployeename = (String)jobj.get("employeename");
							sgender = (String)jobj.get("gender");
							soccupation = (String)jobj.get("occupation");
							spassporttype = (String)jobj.get("passporttype");
							seidaexpirydate = (String)jobj.get("eidaexpirydate");
							if(seidaexpirydate != null) {
								seidaexpirydate = seidaexpirydate.replaceAll("-", "/");
							}
							svisanumber = (String)jobj.get("visanumber");
							svisaexpirydate = (String)jobj.get("visaexpirydate");
							if(svisaexpirydate != null) {
								svisaexpirydate = svisaexpirydate.replaceAll("-", "/");
							}
							svisaissuedate  = (String)jobj.get("visaissuedate");
							if(svisaissuedate != null) {
								svisaissuedate = svisaissuedate.replaceAll("-", "/");
							}

							if(snationality != null){
								snationality = snationality.toUpperCase();
							}
							if(scity!=null){
								scity = scity.toUpperCase();
							}
							if(sstate!=null){
								sstate = sstate.toUpperCase();
							} */
						} catch (Exception e) {
							sendMessageValuesList("", "No Data Returned");
							formObject.clearTable("eida_details");
							logError(BTN_FETCH_EIDA_INFO, e);
						}
						logInfo("BTN_FETCH_EIDA_INFO","snationality : "+snationality);
						logInfo("BTN_FETCH_EIDA_INFO","scustomername : "+scustomername);
						logInfo("BTN_FETCH_EIDA_INFO","seidanumber : "+seidanumber);
						logInfo("BTN_FETCH_EIDA_INFO","sdob : "+sdob);
						logInfo("BTN_FETCH_EIDA_INFO","spassportnumber : "+spassportnumber);
						logInfo("BTN_FETCH_EIDA_INFO","spassportissuedate : "+spassportissuedate);
						logInfo("BTN_FETCH_EIDA_INFO","spassportexpirydate : "+spassportexpirydate);
						logInfo("BTN_FETCH_EIDA_INFO","spassportexpiryplace : "+spassportexpiryplace);
						logInfo("BTN_FETCH_EIDA_INFO","smothername : "+smothername);
						logInfo("BTN_FETCH_EIDA_INFO","saddress : "+saddress);
						logInfo("BTN_FETCH_EIDA_INFO","scity : "+scity);
						logInfo("BTN_FETCH_EIDA_INFO","sstate : "+sstate);
						logInfo("BTN_FETCH_EIDA_INFO","smobile : "+smobile);
						logInfo("BTN_FETCH_EIDA_INFO","sphonenumber : "+sphonenumber);
						logInfo("BTN_FETCH_EIDA_INFO","semail : "+semail);
						logInfo("BTN_FETCH_EIDA_INFO","sprofession : "+sprofession);
						logInfo("BTN_FETCH_EIDA_INFO","sprefix : "+sprefix);
						logInfo("BTN_FETCH_EIDA_INFO","semployeename : "+semployeename);
						logInfo("BTN_FETCH_EIDA_INFO","sgender : "+sgender);
						logInfo("BTN_FETCH_EIDA_INFO","soccupation : "+soccupation);
						logInfo("BTN_FETCH_EIDA_INFO","spassporttype : "+spassporttype);
						logInfo("BTN_FETCH_EIDA_INFO","seidaexpirydate : "+seidaexpirydate);
						logInfo("BTN_FETCH_EIDA_INFO","svisanumber : "+svisanumber);
						logInfo("BTN_FETCH_EIDA_INFO","svisaexpirydate : "+svisaexpirydate);
						logInfo("BTN_FETCH_EIDA_INFO","svisaissuedate  : "+svisaissuedate);
						String sColumn = "NATIONALITY,CUST_NAME,EIDANO,DOB,PASSPORTNO,PASSPORTISSDATE,PASSPORTEXPDATE,"
								+ "PASSPORTEXPPLACE,MOTHERNAME,ADDRESS,CITY,STATE,MOBILE,PHONE_NO,EMAIL,PROFESSION,PREFIX,"
								+ "EMP_NAME,GENDER,OCCUPATION,PASSPORT_TYPE,EIDA_EXP_DATE,VISA_NO,VISA_EXP_DATE,VISA_ISSUE_DATE,WI_NAME";
						String sValue = "'"+snationality+"','"+scustomername+"','"+seidanumber+"'"
								+ ",to_date('" +sdob+"','DD/MM/YYYY'),'"+spassportnumber+"',to_date('" + spassportissuedate+"','DD/MM/YYYY'),"
								+ "to_date('" + spassportexpirydate+"','DD/MM/YYYY'),'"+spassportexpiryplace+"','"+smothername+"',"
								+ "'"+saddress+"','"+scity+"','"+sstate+"',"
								+ "'"+smobile+"','"+sphonenumber+
								"','"+semail+"','"+sprofession+"','"+sprefix+"',"
								+ "'"+semployeename+"','"+sgender+"','"+soccupation+"','"+spassporttype+"',"
								+ "to_date('"+seidaexpirydate+"','DD/MM/YYYY'),'"+svisanumber+"',"
								+ "to_date('"+svisaexpirydate+"','DD/MM/YYYY'),to_date('"+svisaissuedate+"','DD/MM/YYYY'),'"+sWorkitemId+"'";
						sValue = sValue.replaceAll("null", "").replaceAll("NULL", "");
						String delQuery = "DELETE FROM USR_0_EIDACARD_DETAILS WHERE WI_NAME  = '"+sWorkitemId+"' AND  EIDANO ='"+seidanumber+"'";
						logInfo("BTN_FETCH_EIDA_INFO"," delQuery : " + delQuery);
						formObject.saveDataInDB(delQuery);
						insertDataIntoDB("USR_0_EIDACARD_DETAILS",sColumn,sValue);
						String columns = "EIDANO,CUSTOMERNAME,DOB,PASSPORTNO,EIDAEXPDATE";
						String values = seidanumber +"##"+scustomername +"##"+sdob +"##"+ spassportnumber+"##"+seidaexpirydate;
						if(!(seidanumber.equalsIgnoreCase("") && scustomername.equalsIgnoreCase("")&& 
								sdob.equalsIgnoreCase("")&& spassportnumber.equalsIgnoreCase("") 
								&& seidaexpirydate.equalsIgnoreCase(""))){
							LoadListViewWithHardCodeValues("eida_details", columns, values);
							formObject.setValue(NEW_CUST_NAME,scustomername);
							formObject.setValue(NEW_CUST_DOB,sdob);
							formObject.setValue(NEW_CUST_NATIONALITY,snationality); 
						}else
						{
							sendMessageValuesList("", "No Data Returned");
							formObject.clearTable("eida_details");
						}
						/*	String ref = "";
						String sQuery = "SELECT REF_NO from USR_0_AO_BIO_REF WHERE WI_NAME = '"+sWorkitemId+"'";
						logInfo("BTN_FETCH_EIDA_INFO", "sQuery : "+sQuery);
						List<List<String>> list = formObject.getDataFromDB(sQuery);
						logInfo("BTN_FETCH_EIDA_INFO", "list : "+list);
						if(list != null && list.size() > 0){ 
							ref = list.get(0).get(0);
							String sOutput1 = "SELECT SEQ_WEBSERVICE.nextval as ID from DUAL";
							List<List<String>> list1 = formObject.getDataFromDB(sOutput1);
							StringBuilder inputXML = new StringBuilder();
							inputXML.append("<?xml version=\"1.0\"?>").append("\n")
							.append("<APWebService_Input>").append("\n")
							.append("<Option>WebService</Option>").append("\n")
							.append("<EngineName>"+sEngineName+ "</EngineName>").append("\n")
							.append("<winame>" + sWorkitemId + "</winame>").append("\n")
							.append("<Calltype>WS-2.0</Calltype>").append("\n")
							.append("<InnerCallType>InqCustEmiratesIDAuthDtls</InnerCallType>").append("\n")
							.append("<REF_NO>" + list1.get(0).get(0) + "</REF_NO>").append("\n")
							.append("<OLDREF_NO>"+ list1.get(0).get(0) +"</OLDREF_NO>").append("\n")
							.append("<senderID>WMSBPMENG</senderID>").append("\n")
							.append("<InqCustEmiratesIDAuthDtlsReq>").append("\n")
							.append("<channelId>WMSBPM</channelId>").append("\n")
							.append("<channelReferenceNumber>"+ref+"</channelReferenceNumber>").append("\n")
							.append("<inquryType>C</inquryType>").append("\n")
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
						String sNationality = xp.getValueOf("nationality");
						String sCity = xp.getValueOf("homeAddressCityDesc");
						String sState = xp.getValueOf("homeAddressEmirateDesc");
						if(sNationality!=null){
							sNationality = sNationality.toUpperCase();
						}
						if(sCity!=null){
							sCity = sCity.toUpperCase();
						}
						if(sState!=null){
							sState = sState.toUpperCase();
						}
						String sColumn = "NATIONALITY,CUST_NAME,EIDANO,DOB,PASSPORTNO,PASSPORTISSDATE,PASSPORTEXPDATE,"
								+ "PASSPORTEXPPLACE,MOTHERNAME,ADDRESS,CITY,STATE,MOBILE,PHONE_NO,EMAIL,PROFESSION,PREFIX,"
								+ "EMP_NAME,GENDER,OCCUPATION,PASSPORT_TYPE,EIDA_EXP_DATE,VISA_NO,VISA_EXP_DATE,VISA_ISSUE_DATE,WI_NAME";
						String sValue = "'"+sNationality+"','"+xp.getValueOf("eidaUserName")+"','"+xp.getValueOf("eidaCardNumber")+"'"
								+ ",to_date('" + dateOfBirth+"','DD/MM/YYYY'),'"+xp.getValueOf("passportNumber")+"',to_date('" + passportIssueDate+"','DD/MM/YYYY'),"
								+ "to_date('" + passportExpiryDate+"','DD/MM/YYYY'),'"+xp.getValueOf("passportCountryDesc")+"','"+xp.getValueOf("motherFirstName")+"',"
								+ "'"+xp.getValueOf("homeAddressBuilding")+"','"+sCity+"','"+sState+"',"
								+ "'"+xp.getValueOf("homeAddressMobilePhoneNumber")+"','"+xp.getValueOf("homeAddressResidentPhoneNumber")+
								"','"+xp.getValueOf("homeAddressEmail")+"','"+xp.getValueOf("occupation")+"','"+xp.getValueOf("title")+"',"
								+ "'"+xp.getValueOf("workAddressCompanyName")+"','"+xp.getValueOf("gender")+"','"+xp.getValueOf("occupation")+"','"+xp.getValueOf("passportType")+"',"
								+ "to_date('"+xp.getValueOf("expiryDate")+"','DD/MM/YYYY'),'','','','"+sWorkitemId+"'";
						String delQuery = "DELETE FROM USR_0_EIDACARD_DETAILS WHERE WI_NAME  = '"+sWorkitemId+"' AND  EIDANO ='"+xp.getValueOf("eidaCardNumber")+"'";
						logInfo("BTN_FETCH_EIDA_INFO"," delQuery : " + delQuery);
						formObject.saveDataInDB(delQuery);
						insertDataIntoDB("USR_0_EIDACARD_DETAILS",sColumn,sValue);
						String sEidaNo = xp.getValueOf("eidaCardNumber");
						String sName = xp.getValueOf("eidaUserName");
						String sDOB = dateOfBirth;
						String sPassportNo = xp.getValueOf("passportNumber");
						String sExpairyDate = xp.getValueOf("expiryDate");

						String columns = "EIDANO,CUSTOMERNAME,DOB,PASSPORTNO,EIDAEXPDATE";
						String values = sEidaNo +"##"+sName +"##"+sDOB +"##"+ sPassportNo+"##"+sExpairyDate;
						if(!(sEidaNo.equalsIgnoreCase("") && sName.equalsIgnoreCase("")&& 
								sDOB.equalsIgnoreCase("")&& sPassportNo.equalsIgnoreCase("") 
								&& sExpairyDate.equalsIgnoreCase(""))){
							LoadListViewWithHardCodeValues("eida_details", columns, values);
							formObject.setValue(NEW_CUST_NAME,sName);
							formObject.setValue(NEW_CUST_DOB,sDOB);
							formObject.setValue(NEW_CUST_NATIONALITY,sNationality);
						}else
						{
							sendMessageValuesList("", "No Data Returned");
							formObject.clearTable("eida_details");
						}
					}else
					{
						sendMessageValuesList("", "No Data Returned");
						formObject.clearTable("eida_details");
					}
				}*/
					} 
				} else if (controlName.equalsIgnoreCase(READ_EIDA)){
					String sQuery = "select CBG_EIDA_REFNO.nextval REFNO from dual";
					String refNo = "";
					List<List<String>> recordList = formObject.getDataFromDB(sQuery);
					if (recordList != null && recordList.size() > 0 )
					{
						logInfo("BTN_FETCH_EIDA_INFO"," refNo list =>  " + recordList.toString());
						refNo = recordList.get(0).get(0);
					}
					logInfo("BTN_FETCH_EIDA_INFO","  ref no " + refNo);
					String sQuery1 = "Insert into USR_0_AO_BIO_REF (WI_NAME,REF_NO) values('" + sWorkitemId + "','" + refNo + "')";
					int result = this.formObject.saveDataInDB(sQuery1);
					logInfo("BTN_FETCH_EIDA_INFO"," result : " + result);
					return getReturnMessage(true, refNo);

				} else if(controlName.equalsIgnoreCase(BTN_SEARCH_CUSTOMER) && eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)) {
					String cid="";
					String sDebitCard="";
					String sCreditCard="";
					String email="";
					String mob="";					
					String name="" ;
					String dob="";
					String cust_ic="";
					String sNationality="";					
					String passport="";
					String sOutput="";
					String sReturnCode="";
					String sQuery= "";
					String cid_trim="";
					String eIDA="";
					formObject.clearTable(SEARCH_DETAILS_LVW);
					cid=(String) formObject.getValue(SEARCH_CID);
					cid_trim=cid.replaceAll("^0+",""); 
					if(((String) formObject.getValue(HD_FCR_SEARCH)).equalsIgnoreCase("")) {
						sendMessageValuesList("",CA010);
						return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
					}
					if(((String) formObject.getValue(HD_FCR_SEARCH)).equalsIgnoreCase("Customer Details")) {
						name=formObject.getValue(HD_NAME).toString();
						passport=formObject.getValue(SEARCH_PASS_NO).toString();
						dob=((String) formObject.getValue(SEARCH_DOB)).toUpperCase();
						email=formObject.getValue(HD_EMAIL).toString();
						mob=formObject.getValue(SEARCH_MOB_NO).toString();
						passport=formObject.getValue(SEARCH_PASS_NO).toString();
						cust_ic=formObject.getValue(HD_CUST_IC).toString();
						sNationality=formObject.getValue(SEARCH_NATIONALITY).toString();
						eIDA=formObject.getValue(SEARCH_EIDA_CARD_NO).toString();
						String whereCls="";
						String whereCls1="";
						String whereCls2="";
						boolean flag = false;
						if(eIDA.equalsIgnoreCase("")&& cid.equalsIgnoreCase("")&& passport.equalsIgnoreCase("")){
							sendMessageValuesList("", CR0001);
							return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
						}
						if(!passport.equalsIgnoreCase("") && passport.contains("*")){
							sendMessageValuesList(SEARCH_PASS_NO, CA0110);	
							return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
						} else if(!passport.equalsIgnoreCase("") && passport.trim().length()<3){
							sendMessageValuesList(SEARCH_PASS_NO, CA0111);
							return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
						}
						if(!cid.trim().equalsIgnoreCase("")){
							if(flag){
								whereCls=whereCls+"A.cust_id='"+cid_trim+"'";
								whereCls1=whereCls1+"CUSTOMER_ID='"+cid_trim+"'";
							} else {
								flag=true;
								whereCls=" A.cust_id='"+cid_trim+"'";
								whereCls1=whereCls1+"CUSTOMER_ID='"+cid_trim+"'";
							}
						} else if(!eIDA.trim().equalsIgnoreCase("")){
							if(flag){
								whereCls=whereCls+"A.EIDA_NO='"+eIDA+"'";
								whereCls1=whereCls1+"eida_number='"+eIDA+"'";
							} else {
								flag=true;
								whereCls=" A.EIDA_NO='"+eIDA+"'";
								whereCls1=whereCls1+"eida_number='"+eIDA+"'";
							}
						}
						boolean found=false;
						if(!cid.trim().equalsIgnoreCase("")){
							whereCls2=" cust_id='"+cid.trim()+"' ";
							found=true;
						}
						if(!passport.trim().equalsIgnoreCase("")){
							if(found)
								whereCls2+=" or upper(final_Pass_no)='"+passport.toUpperCase()+"' ";
							else
								whereCls2+=" upper(final_Pass_no)='"+passport.toUpperCase()+"' ";
							found=true;
						}
						if(!eIDA.trim().equalsIgnoreCase("")){
							if(found)
								whereCls2+=" or final_eida_no='"+eIDA+"'";
							else
								whereCls2+=" final_eida_no='"+eIDA+"'";
						}
						if(!whereCls2.equalsIgnoreCase("")){
							whereCls2="("+whereCls2+")  AND   exists (select 1 from ext_ao b where curr_ws_name in "+ 
									" ('RM','QDE_Cust_Info','QDE_Acc_Info_Chk','QDE_ Account_Info',"+
									"'Product Approver','Level4 Approved','Level3 Approved','Level2 Approved',"+
									"'Level1 Approved','DDE_Cust_Info','DDE_Account_Info','DDE_Acc_Info_Chk',"+
									"'Customer_Screen','CPD Maker','CPD Checker','Contact_Center_CPD','Contact Center Team',"+
									"'Compliance Approver','Application_Assessment','Account_Relation') and b.wi_name = a.wi_name)";
						}
						savePopulateSearch(0,whereCls,whereCls1,whereCls2,SEARCH_DETAILS_LVW,sAuditTable);
						formObject.setValue("search_flag",TRUE);
					}
					int	iListViewRows = getGridCount(SEARCH_DETAILS_LVW);
					if(iListViewRows==0){
						formObject.setValue(NEW_CUST_NAME,name);
						formObject.setValue(NEW_CUST_DOB,dob);
						formObject.setValue(NEW_CUST_NATIONALITY,sNationality);
					}
					logInfo("onClickAcctRel","Customer details populated");
				} else if(controlName.equalsIgnoreCase(HD_FCR_SEARCH) && eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)){
					try{
						String criteria =formObject.getValue(HD_FCR_SEARCH).toString();
						if (criteria.equalsIgnoreCase("Customer Details")){
							showControls(SHOWCTRLSFCRSERCUSTDET);
							enableControls(ENBCTRLSFCRSERCUSTDET);
						}
						if(criteria.equalsIgnoreCase("Customer Credit or Debit Card")){
							showControls(SHOWCTRLSFCRSERCCDC);
							formObject.setStyle(HD_DEBIT_NO,ENABLE,TRUE);
							formObject.setStyle(HD_CREDIT_NO,ENABLE,TRUE);
						}
						if(criteria.equalsIgnoreCase("")){
							showControls(SHOWFCRSERFIELDS);
							formObject.setStyle(HD_DEBIT_NO,ENABLE,TRUE);
							formObject.setStyle(HD_CREDIT_NO,ENABLE,TRUE);
						}
					} catch(Exception e){
						logError("Exception in [HD_FCR_SEARCH BTN_CLICK]",e);
					}
				} else if(controlName.equalsIgnoreCase(BTN_SEARCH_CLEAR) && eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)){
					logInfo("onClickAcctRel","Inside BTN_SEARCH_CLEAR");
					formObject.clearTable(SEARCH_DETAILS_LVW);
					clearControls(BTNSERBLANKFIELDS);
					moveToAuditHist();
				} else if(controlName.equalsIgnoreCase(SEARCH_ADD_CUST_INFO) && eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)){
					logInfo("onClickAcctRel","Inside SEARCH_ADD_CUST_INFO");
					int selectedRowIndex = 0;
					String sReqType=formObject.getValue(REQUEST_TYPE).toString();
					int iListViewRows = getGridCount(SEARCH_DETAILS_LVW);
					if(!data.equalsIgnoreCase("undefined")){
						selectedRowIndex = Integer.parseInt(data);
					}
					logInfo("onClickAcctRel","SEARCH_ADD_CUST_INFO Data :: "+ selectedRowIndex);
					if(iListViewRows==0){
						if(sReqType.equalsIgnoreCase("New Account")){
							sendMessageValuesList(BTN_SEARCH_CUSTOMER, "Please Perform Search First.");
							return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));	
						} else {
							sendMessageValuesList(BTN_SEARCH_CUSTOMER, "For request type Category Change Only and New Account with Category Change, first customer should be existing customer.");
							return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));	
						}
					}
					if(iListViewRows>0 && data.equalsIgnoreCase("undefined")) {
						sendMessageValuesList(SEARCH_DETAILS_LVW, "Please Select a row from grid.");
						return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));	
					}
					if(data.length()>=0) {
						logInfo("onClickAcctRel","data :: "+data);
						String sNO="";
						String sCustomerID= formObject.getTableCellValue(SEARCH_DETAILS_LVW,selectedRowIndex,0).replaceAll("^0+","");
						String sCustomerName= formObject.getTableCellValue(SEARCH_DETAILS_LVW,selectedRowIndex,1);
						String sMobile= formObject.getTableCellValue(SEARCH_DETAILS_LVW,selectedRowIndex,6);					
						String sEIDANo= formObject.getTableCellValue(SEARCH_DETAILS_LVW,selectedRowIndex,8);					
						String sNationality= formObject.getTableCellValue(SEARCH_DETAILS_LVW,selectedRowIndex,4);					
						String sDOB= formObject.getTableCellValue(SEARCH_DETAILS_LVW,selectedRowIndex,7);					
						String sMemo= formObject.getTableCellValue(SEARCH_DETAILS_LVW,selectedRowIndex,9);	
						String sEmail= formObject.getTableCellValue(SEARCH_DETAILS_LVW,selectedRowIndex,5);	
						logInfo("onClickAcctRel","sCustomerID:: "+sCustomerID+" sCustomerName ::"+sCustomerName);
						logInfo("onClickAcctRel","sMobile:: "+sMobile+" sNationality ::"+sNationality);
						int iRows = getGridCount(ACC_RELATION);
						logInfo("onClickAcctRel","iRows==="+iRows);
						int age= calculateAge(sDOB);					   				
						if(iRows==1 && sReqType.equalsIgnoreCase("Category Change Only")) {							
							sendMessageValuesList(SEARCH_ADD_CUST_INFO, "Only 1 customer allowed for Category Change Only request type.");
							return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));	
						}						
						for(int iLoop=0;iLoop<iRows;iLoop++){
							if(sCustomerID.equalsIgnoreCase(formObject.getTableCellValue(ACC_RELATION,iLoop,2))){
								sendMessageValuesList(SEARCH_DETAILS_LVW, "This customer is already added in the grid.");
								return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
							}
						}
						if(sMemo.equalsIgnoreCase("H")){
							sendMessageValuesList("", "You can not add this customer as memo is high");
							return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
						}
						if(sCustomerName.equalsIgnoreCase("")){
							sendMessageValuesList("", CA0115);
							return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
						}
						//Modifed By SHivanshu ATP-472
						if(sCustomerName.length()<2){
							sendMessageValuesList("", CA0134);
							return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
						}					
						if(sCustomerName.length()>75){
							sendMessageValuesList("", CA0138);
							return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
						}
						if(sNationality.equalsIgnoreCase("")){
							sendMessageValuesList("", CA0116);
							return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
						}
						if(sDOB.equalsIgnoreCase("")){
							sendMessageValuesList("", CA0117);
							return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
						}
						int sMinorAge=0;
						String sQueryy="select VALUE from usr_0_defaultvalue_fcr where name='Minor_Age'";
						List<List<String>> list = formObject.getDataFromDB(sQueryy);
						if (list != null && !list.isEmpty()) {
							for(int i=0;i<list.size();i++){
								logInfo("onClickAcctRel","data : "+list.get(i));
								sMinorAge = Integer.parseInt(list.get(i).get(0));
							}
						}
						logInfo("onClickAcctRel","sMinorAge....."+sMinorAge);
						if(((String) formObject.getValue(ACC_OWN_TYPE)).equalsIgnoreCase("Joint") && age<sMinorAge){
							sendMessageValuesList("", "Date Of Birth Should be greater than or equal to "+sMinorAge+" Years.");
							return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
						}
						//ATP-411 20022024
						if(!(sReqType.equalsIgnoreCase("Category Change Only")||sReqType.equalsIgnoreCase("Downgrade"))){
							if(((String) formObject.getValue(ACC_OWN_TYPE)).equalsIgnoreCase("Single") && age<18){
								sendMessageValuesList("", "Date Of Birth Should be greater than or equal to 18 Years.");
								return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
							}
						}
						if(iRows>0){
							sNO = formObject.getTableCellValue(ACC_RELATION,iRows-1,0);
							logInfo("onClickAcctRel","sNO 1:: "+sNO);
							sNO=(Integer.parseInt(sNO)+1)+"";
							logInfo("onClickAcctRel","sNO 2:: "+sNO);
						} else {
							sNO="1";
						}						
						if(sDOB.indexOf(" ")>0){
							logInfo("onClickAcctRel","in DOB");
							String [] temp =sDOB.split(" ");
							temp=temp[0].split("-");
							sDOB=temp[2]+"/"+temp[1]+"/"+temp[0];
						}
						logInfo("onClickAcctRel","sNO:: "+sNO);
						logInfo("onClickAcctRel","iRows:: "+iRows);
						logInfo("onClickAcctRel","addition in account relation grid starts");
						String values = sNO+"##"+sCustomerName+"##"+sCustomerID+"##"+sMobile+"##"+sEIDANo+"##"+sDOB+"##"+sNationality+"##"+"Existing"+"##"+""+"##"+""+"##"+"FCR"+"##"+sWorkitemId+"##"+sEmail;
						LoadListViewWithHardCodeValues(ACC_RELATION, ACCRELGRIDCOLNAMES, values);
						logInfo("onClickAcctRel","addition in account relation grid ends");
						if(((String) formObject.getValue(ACC_OWN_TYPE)).equalsIgnoreCase("Joint")){
							if(iRows>=1) {
								formObject.setTableCellValue(ACC_RELATION, iRows, 9, "JAO");
							} else {
								formObject.setTableCellValue(ACC_RELATION, iRows, 9, "JAF");
							}
						} else if(((String) formObject.getValue(ACC_OWN_TYPE)).equalsIgnoreCase("Single")){
							logInfo("onClickAcctRel","iRows---"+iRows);
//							formObject.setTableCellValue(ACC_RELATION, iRows, 8, "Single");
							if(iRows==0){
								formObject.setTableCellValue(ACC_RELATION, iRows, 9,"SOW");
							} else {
								formObject.setTableCellValue(ACC_RELATION,iRows, 9,"AUS");
							}
						} else if(((String) formObject.getValue(ACC_OWN_TYPE)).equalsIgnoreCase("Minor")) {
							logInfo("onClickAcctRel","iRows---"+iRows);
							if(iRows==0) {
								formObject.setTableCellValue(ACC_RELATION, iRows, 9,"Minor");
							} else {
								formObject.setTableCellValue(ACC_RELATION, iRows, 9,"Guardian");
							}
						}
						if(iRows==0 && ((String) formObject.getValue(REQUEST_TYPE)).equalsIgnoreCase("New Account with Category Change")) {
							enableControls(BTNADDCUSTINFOFIELDSDIS);
							//hideControls(BTNADDCUSTINFOBTNSHIDE);		
							// commented above hide method CQRN-0000177298
							// below change to make Add manual button visibility true - Gautam 26/10/2021
							showControls(BTNADDCUSTINFOBTNSHIDE);			
						}
						formObject.setValue(NO_OF_CUST_SEARCHED,sNO);
						iListViewRows = getGridCount(EIDA_DETAILS);
						sEIDANo = formObject.getTableCellValue(EIDA_DETAILS, 0, 0);
						if(iListViewRows>0){
							String sQuery = "update USR_0_EIDACARD_DETAILS set CUST_ID='"+sCustomerID+"' where WI_NAME ='"+sWorkitemId+"' and EIDANO='"+sEIDANo+"'";							
							int result = formObject.saveDataInDB(sQuery);
							logInfo("onClickAcctRel","sOutput....."+result);
						}
						setFieldsBlank();						
					}
				} else if(controlName.equalsIgnoreCase(BTN_ADD_MANUALLY) && eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)){	
					logInfo("onClickAcctRel","Inside BTN_ADD_MANUALLY:: "+BTN_ADD_MANUALLY);
					String sNO="";
					String sCustomerName= formObject.getValue(NEW_CUST_NAME).toString();				
					String sNationality=formObject.getValue(NEW_CUST_NATIONALITY).toString();					
					String sDOB= formObject.getValue(NEW_CUST_DOB).toString();
					String sEida= formObject.getValue("UAE_PASS_INFO_EIDA").toString();			
					String sEmail= formObject.getValue("UAE_PASS_INFO_EMAIL").toString();			
					String sMobNo= formObject.getValue("UAE_PASS_INFO_MOBNO").toString();
						
					int age=calculateAge(sDOB);					
					if(sCustomerName.equalsIgnoreCase("")){
						sendMessageValuesList(NEW_CUST_NAME, CA011);
						return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
					}				
					//Modifed By SHivanshu ATP-472
					if(sCustomerName.length()<2){
						sendMessageValuesList(NEW_CUST_NAME, CA0134);	
						return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
					}
					if(sCustomerName.length()>75){
						sendMessageValuesList(NEW_CUST_NAME, CA0138);	
						return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
					}
					if(sDOB.equalsIgnoreCase("")){
						sendMessageValuesList(NEW_CUST_DOB, CA012);
						return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));

					} 
					else {
						if(!validateDOB(NEW_CUST_DOB)) {
							logInfo("onClickAcctRel","After Change");
							return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
						}
					}
					if(sNationality.equalsIgnoreCase("")) {
						sendMessageValuesList(NEW_CUST_NATIONALITY, CA013);	
						return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
					}
					int sMinorAge=0;
					String bankRel = "";
					int iRows = getGridCount(ACC_RELATION);
					logInfo("onClickAcctRel","iRows : " + iRows);
					String sQueryy="select VALUE from usr_0_defaultvalue_fcr where name='Minor_Age'";
					List<List<String>> list = formObject.getDataFromDB(sQueryy);
					if (list != null && !list.isEmpty()) {
						for(int i=0;i<list.size();i++){
							logInfo("onClickAcctRel","data : "+list.get(i));
							sMinorAge = Integer.parseInt(list.get(i).get(0));
						}
					}
//					String sQueryy1="select CUST_ID from EXT_AO where nWI_NAME='"+sWorkitemId+"'";//Commented for invalid column on 27042023(Performanceissue) by Ameena
					String sQueryy1="select CUST_ID from EXT_AO where WI_NAME='"+sWorkitemId+"'";
					List<List<String>> list1 = formObject.getDataFromDB(sQueryy1);
					String custID ="";
					if (list1 != null && !list1.isEmpty()) {
						for(int j=0;j<list1.size();j++){
							logInfo("onClickAcctRel","data : CUST_ID"+list1.get(j).get(0));
							custID = list1.get(j).get(0);
						}
					}
					if(custID.equalsIgnoreCase("")) {
						bankRel = "New";
					} else {
						bankRel = "Existing";
					}
					logInfo("onClickAcctRel","sMinorAge....."+sMinorAge);
					if(((String) formObject.getValue(ACC_OWN_TYPE)).equalsIgnoreCase("Joint") && age<sMinorAge){
						sendMessageValuesList("", "Date Of Birth Should be greater than or equal to "+sMinorAge+" Years.");
						return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
					}
					if(((String) formObject.getValue(ACC_OWN_TYPE)).equalsIgnoreCase("Single") && age<18){
						sendMessageValuesList("","Date Of Birth Should be greater than or equal to 18 Years.");
						return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
					}
					//Added by Shivanshu ATP-472 29-05-24
					if(!sEida.startsWith("784")){
						sendMessageValuesList("UAE_PASS_INFO_EIDA", "Eida Number must be start with 784");
						return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
					}
					if(!(sMobNo.startsWith("971") || sMobNo.startsWith("+971") || sMobNo.startsWith("00971"))){
						sendMessageValuesList("UAE_PASS_INFO_MOBNO","Mobile Number must be start with 971");
						return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
					}
					//End ATP-472
					if(iRows>0){
						sNO = formObject.getTableCellValue(ACC_RELATION,iRows-1,0);
						logInfo("onClickAcctRel","sNO....."+sNO);
						sNO=(Integer.parseInt(sNO)+1)+"";
						logInfo("onClickAcctRel","sNO+1....."+sNO);
					} else {
						sNO="1";
					}					
					logInfo("onClickAcctRel","Inside BTN Add Manually :: "+sNO);
					JSONArray jsonArray=new JSONArray(); 
					JSONObject obj=new JSONObject();
					obj.put("SNO",sNO);
					obj.put("NAME",sCustomerName);
					obj.put("CID","");
					obj.put("MOBILE",sMobNo);
					obj.put("EIDANO",sEida);
					obj.put("DOB",sDOB);
					obj.put("NATIONALITY",sNationality);
					obj.put("BANKREL",bankRel);
					obj.put("CUSTREL","");
					obj.put("ACCTREL","");
					obj.put("SEARCHTYPE","MANUAL");
					obj.put("WI_NAME",sWorkitemId);
					obj.put("EMAIL",sEmail);
					jsonArray.add(obj); 
					logInfo("onClickAcctRel","jsonArray2:: "+jsonArray.toString());
					formObject.addDataToGrid(ACC_RELATION,jsonArray);
					if(((String) formObject.getValue(ACC_OWN_TYPE)).equalsIgnoreCase("Joint")) {
						if(iRows>=1) {
							formObject.setTableCellValue(ACC_RELATION, iRows, 9, "JAO");
							//formObject.setTableCellValue(ACC_RELATION, iRows, 10, "JAO");
						} else {
							//formObject.setTableCellValue(ACC_RELATION, iRows, 10, "JAF");
							formObject.setTableCellValue(ACC_RELATION, iRows, 9, "JAF");
						}
					} else if(((String) formObject.getValue(ACC_OWN_TYPE)).equalsIgnoreCase("Single")) {
//						formObject.setTableCellValue(ACC_RELATION, iRows, 9, "Single");
						if(iRows==0) {
							//formObject.setTableCellValue(ACC_RELATION, iRows, 10, "SOW");
							formObject.setTableCellValue(ACC_RELATION, iRows, 9, "SOW");
						} else {
							//formObject.setTableCellValue(ACC_RELATION, iRows, 10, "AUS");
							formObject.setTableCellValue(ACC_RELATION, iRows, 9, "AUS");
						}
					} else if(((String) formObject.getValue(ACC_OWN_TYPE)).equalsIgnoreCase("Minor")) {
						logInfo("onClickAcctRel","iRows---"+iRows);
						if(iRows==0) {
							//formObject.setTableCellValue(ACC_RELATION, iRows, 10, "Minor");
							formObject.setTableCellValue(ACC_RELATION, iRows, 9, "Minor");
						} else {
							//formObject.setTableCellValue(ACC_RELATION, iRows, 10, "Guardian");
							formObject.setTableCellValue(ACC_RELATION, iRows, 9, "Guardian");
						}
					}
					formObject.setValue(NO_OF_CUST_SEARCHED,sNO);
					setFieldsBlank();
					clearControls(new String[]{"UAE_PASS_INFO_EMAIL","UAE_PASS_INFO_EIDA","UAE_PASS_INFO_MOBNO"});

				} else if(controlName.equalsIgnoreCase(BTN_ADD_EIDA_INFO) && eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)) {
					int iListViewRows1 = getGridCount(EIDA_DETAILS);
					int iListViewSelectedRow = 0;
					if(!data.equalsIgnoreCase("undefined")){
						iListViewSelectedRow = Integer.parseInt(data);
					}
					if(iListViewRows1==0) {
						sendMessageValuesList(BTN_SEARCH_CUSTOMER, "Please Perform Search First.");
						return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
					}
					if(iListViewRows1>0 && data.equalsIgnoreCase("undefined")){
						sendMessageValuesList(EIDA_DETAILS, "Please Select a row from grid.");
						return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
					}
					if(data.length()>=0){	
						String sNO="";
						String sCustomerName= formObject.getTableCellValue(EIDA_DETAILS,iListViewSelectedRow,1);				
						String sEIDANo= formObject.getTableCellValue(EIDA_DETAILS,iListViewSelectedRow,0);				
						String sDOB= formObject.getTableCellValue(EIDA_DETAILS,iListViewSelectedRow,2);	
						int age=calculateAge(sDOB);
						int iRows = getGridCount(ACC_RELATION);
						logInfo("onClickAcctRel","iRows==="+iRows);						
						for(int iLoop=1;iLoop<iRows;iLoop++){
							if(sEIDANo.equalsIgnoreCase(formObject.getTableCellValue(ACC_RELATION, iLoop, 4)));//(iLoop,"AO_acc_relation.eida_no"))){
							sendMessageValuesList(EIDA_DETAILS, "This customer is already added in the grid.");
							return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
						}					
						if(sCustomerName.equalsIgnoreCase("")){
							sendMessageValuesList("", CA0115);
							return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
						}
						//Modifed By SHivanshu ATP-472
						if(sCustomerName.length()<2){
							sendMessageValuesList("", CA0134);
							return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
						}
						if(sCustomerName.length()>75){
							sendMessageValuesList("", CA0138);
							return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
						}
						if(sEIDANo.equalsIgnoreCase("")){
							sendMessageValuesList("", CA0118);
							return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
						}
						if(sDOB.equalsIgnoreCase("")){
							sendMessageValuesList("", CA0117);
							return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
						}
						if(iRows!=0){
							sNO = formObject.getTableCellValue(ACC_RELATION,iRows-1,0);
							sNO=(Integer.parseInt(sNO)+1)+"";
						} else {
							sNO="1";
						}
						int sMinorAge=0;
						String sQueryy="select VALUE from usr_0_defaultvalue_fcr where name='Minor_Age'";
						List<List<String>> list = formObject.getDataFromDB(sQueryy);
						if (list != null && !list.isEmpty()) {
							for(int i=0;i<list.size();i++){
								logInfo("onClickAcctRel","data : "+list.get(i));
								sMinorAge = Integer.parseInt(list.get(i).get(0));
							}
						}
						if(((String) formObject.getValue(ACC_OWN_TYPE)).equalsIgnoreCase("Joint") && age<sMinorAge) {
							sendMessageValuesList("", "Date Of Birth Should be greater than or equal to "+sMinorAge+" Years.");
							return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));

						}
						//ATP-411 20022024
						if(((String) formObject.getValue(ACC_OWN_TYPE)).equalsIgnoreCase("Single") && age<18 && 
								!((String) formObject.getValue(REQUEST_TYPE)).equalsIgnoreCase("Downgrade")){
							sendMessageValuesList("","Date Of Birth Should be greater than or equal to 18 Years.");
							return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
						}
						logInfo("onClickAcctRel","Inside BTN_ADD_EIDA_INFO :: "+sNO);
						JSONArray jsonArray=new JSONArray(); 
						JSONObject obj=new JSONObject();
						obj.put("SNO",sNO);
						obj.put("NAME",sCustomerName);
						obj.put("CID","");
						obj.put("MOBILE","");
						obj.put("EIDANO",sEIDANo);
						obj.put("DOB",sDOB);
						obj.put("NATIONALITY","");
						obj.put("BANKREL","New");
						obj.put("CUSTREL","");
						obj.put("ACCTREL","");
						obj.put("SEARCHTYPE","EIDA");
						obj.put("WI_NAME",sWorkitemId);
						jsonArray.add(obj); 
						logInfo("onClickAcctRel","jsonArray3:: "+jsonArray.toString());
						formObject.addDataToGrid(ACC_RELATION,jsonArray);
						if(((String) formObject.getValue(ACC_OWN_TYPE)).equalsIgnoreCase("Joint")){
							if(iRows>=1) {
								//formObject.setTableCellValue(ACC_RELATION, iRows, 10, "JAO");
								formObject.setTableCellValue(ACC_RELATION, iRows, 9, "JAO");
							} else {
								//formObject.setTableCellValue(ACC_RELATION, iRows, 10, "JAF");
								formObject.setTableCellValue(ACC_RELATION, iRows, 9, "JAF");
							}
						} else if(((String) formObject.getValue(ACC_OWN_TYPE)).equalsIgnoreCase("Single")){
							formObject.setTableCellValue(ACC_RELATION, iRows, 8, "Single");
							if(iRows==0) {
								//formObject.setTableCellValue(ACC_RELATION, iRows, 10, "SOW");
								formObject.setTableCellValue(ACC_RELATION, iRows, 9, "SOW");
							} else {
								//formObject.setTableCellValue(ACC_RELATION, iRows, 10, "AUS");
								formObject.setTableCellValue(ACC_RELATION, iRows, 9, "AUS");
							}
						} else if(((String) formObject.getValue(ACC_OWN_TYPE)).equalsIgnoreCase("Minor")) {
							logInfo("onClickAcctRel","iRows---"+iRows);
							if(iRows==0) {
							//	formObject.setTableCellValue(ACC_RELATION, iRows, 10, "Minor");
								formObject.setTableCellValue(ACC_RELATION, iRows, 9, "Minor");
							} else {
							//	formObject.setTableCellValue(ACC_RELATION, iRows, 10, "Guardian");
								formObject.setTableCellValue(ACC_RELATION, iRows, 9, "Guardian");
							}
						}
						if(iRows==0 && ((String) formObject.getValue(REQUEST_TYPE)).equalsIgnoreCase("New Account with Category Change")) {
							enableControls(BTNADDEIDAINFOFIELDSDIS);
							hideControls(BTNADDEIDAINFOBTNSHIDE);
						}
						formObject.setValue(NO_OF_CUST_SEARCHED,sNO);
						setFieldsBlank();
					}
				} else if(controlName.equalsIgnoreCase("DELETEROW") && eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)){		
					int iRows = getGridCount(ACC_RELATION);
					int iSelectedRow = Integer.parseInt(data);
					String sCustomerID= formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 2);//Value(iSelectedRow,"AO_acc_relation.cid");
					String wiName=sWorkitemId;//= formObject.getObjGeneralData().getM_strProcessInstanceId();//formObject.getTableCellValue(ACC_RELATION, 0, 12);
					String delSNo=formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 0);
					String sOutput="";	
					int result = 0;
					logInfo("onClickAcctRel","iRows---"+iRows);
					logInfo("onClickAcctRel","iSelectedRow---"+data);					
					if(iRows == 0){
						sendMessageValuesList(ACC_RELATION, "Please add a row first in the grid.");
						return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
					} else {
						if(!sCustomerID.equalsIgnoreCase("")){
							sOutput = "delete from USR_0_PRODUCT_EXISTING where CUSTOMER_ID='"+sCustomerID+"'";
							result = formObject.saveDataInDB(sOutput);
							logInfo("onClickAcctRel","Row deleted---"+result);
						}					
						if(iRows== (iSelectedRow+1)){
							logInfo("onClickAcctRel","getGridCount::"+getGridCount(ACC_RELATION));
							String outPutDel="delete from USR_0_CUST_TXN where WI_NAME='"+wiName+"' and CUST_SNO='"+delSNo+"'";
							result = formObject.saveDataInDB(outPutDel);
							logInfo("onClickAcctRel","outPutDel = "+result);
							outPutDel = "delete from USR_0_CHANGE_TRACKER where WI_NAME='"+wiName+"' and CUST_SNO='"+delSNo+"'";
							result = formObject.saveDataInDB(outPutDel);
							logInfo("onClickAcctRel","outPutDel = "+result);
							iRows = getGridCount(ACC_RELATION);
							formObject.setValue(NO_OF_CUST_SEARCHED,(iRows)+"");
						} else {
							logInfo("onClickAcctRel","getGridCount::"+getGridCount(ACC_RELATION));
							logInfo("onClickAcctRel","iSelectedRow::"+iSelectedRow);
							logInfo("onClickAcctRel","sNo---"+formObject.getTableCellValue(ACC_RELATION,iSelectedRow,0));
							String sNo= delSNo;//;//objChkRepeater.getValue(iSelectedRow,"AO_acc_relation.sno");
							logInfo("onClickAcctRel","sNo---"+sNo);
							int iNo=Integer.parseInt(sNo);
							String outPutDel = "delete from USR_0_CUST_TXN where WI_NAME='"+wiName+"' and CUST_SNO='"+delSNo+"'";
							result = formObject.saveDataInDB(outPutDel);
							logInfo("onClickAcctRel","outPutDel = "+result);
							outPutDel = "delete from USR_0_CHANGE_TRACKER where WI_NAME='"+wiName+"' and CUST_SNO='"+delSNo+"'";
							result = formObject.saveDataInDB(outPutDel);
							logInfo("onClickAcctRel","outPutDel = "+result);
							int iNoUpdate=Integer.parseInt(sNo);
							logInfo("onClickAcctRel","iNoUpdate = "+iNoUpdate);
							while(iNo!=iRows) {
								iNoUpdate++;
								formObject.setTableCellValue(ACC_RELATION, iNo-1, 0,Integer.toString(iNo));
								sOutput = "update acc_relation_repeater set SNO = '"+iNo+"' where WI_NAME= '"+wiName+"' and SNO='"+iNoUpdate+"'";
								logInfo("onClickAcctRel"," sOutput acc_relation_repeater: " + sOutput);
								result = formObject.saveDataInDB(sOutput);
								logInfo("onClickAcctRel","sOutput Update acc_relation Table---"+result);
								sOutput = "update USR_0_CUST_TXN set CUST_SNO = '"+iNo+"' where WI_NAME= '"+wiName+"' and CUST_SNO='"+iNoUpdate+"'";
								logInfo("onClickAcctRel"," sOutput USR_0_CUST_TXN: " + sOutput);
								result = formObject.saveDataInDB(sOutput);
								logInfo("onClickAcctRel","sOutput Update Cust Table---"+result);
								sOutput = "update USR_0_CHANGE_TRACKER set CUST_SNO = '"+iNo+"' where WI_NAME= '"+wiName+"' and CUST_SNO='"+iNoUpdate+"'";
								logInfo("onClickAcctRel"," sOutput USR_0_CHANGE_TRACKER: " + sOutput);
								result = formObject.saveDataInDB(sOutput);
								logInfo("onClickAcctRel","sOutput Update Cust Table---"+result);
								iNo++;
							}	
							iRows = getGridCount(ACC_RELATION);
							formObject.setValue(NO_OF_CUST_SEARCHED,(iRows)+"");
						}
						if(iRows==1 && (((String) formObject.getValue(REQUEST_TYPE)).equalsIgnoreCase("New Account with Category Change")|| ((String) formObject.getValue(REQUEST_TYPE)).equalsIgnoreCase("Category Change Only"))) {
							enableControls(BTNADDEIDAINFOFIELDSDIS);
							hideControls(BTNADDEIDAINFOBTNSHIDE);
						}
					}
				} else if(controlName.equalsIgnoreCase(TABCLICK) && eventType.equalsIgnoreCase("Click")) {
//					boolean result = validateRelationData();
//					logInfo("onClickAcctRel","validateRelationData New======= "+result);
				   //AO DCRA COMMENTED//15082023
					savePreAssessmentDetails();
					String PrevStage =checkpreviousStage();
					if(PrevStage.equalsIgnoreCase("Introduction")){
						insertDataintoProductGrid();
					}
					//Upgrade  AND Category Change Only changes
					if(!(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade") || 
							formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only"))) {
					String private_Client=formObject.getValue("PRIVATE_CLIENT").toString();	
					if(private_Client.equalsIgnoreCase("Y")) {
					    getPreAssessmentFlag();
					}
					    int returnCode = mandatoryDocAlert();
						if (returnCode < 0){
							sendMessageValuesList("BTN_SUBMIT", "Please Upload/Attach Exception Approval Document");
							return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));	
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
					if(!((String) formObject.getValue(CRO_DEC)).equalsIgnoreCase("Reject") && !validateRelationData()) {
						return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
					}
//					if(!((String) formObject.getValue(CRO_DEC)).equalsIgnoreCase("Send To UaePass Pending")){
//						formObject.setValue("CRO_DEC", "");
//					}
				} else if(controlName.equalsIgnoreCase("FETCH_UAE_PASS_INFO")){

					if(callEidaValidation()){
						implementUaePassLogic("");
						updateAccRelGridFromUaePassInfo(sWorkitemId);					
						populateUAEPassInfoStatus(sWorkitemId);
						String isUAEPassAuthFlag = "";
						String isUAEPassAuthDone = "";
						//formObject.setStyle("CRO_DEC","disable", "false");
						formObject.setValue("IS_UAE_PASS_INFO_CLICKED","Y");
						formObject.setStyle("FETCH_UAE_PASS_INFO","disable", "true");
//						setSkipUAEFlag();
						List<List<String>> ls = formObject.getDataFromDB("SELECT UAE_PASS_AUTH_FLAG , "
								+ "IS_UAE_PASS_AUTH_DONE FROM EXT_AO WHERE WI_NAME = '"+sWorkitemId+"'");
						logInfo("FETCH_UAE_PASS_INFO","ls "+ls.toString());
						if(ls!=null && ls.size()>0){
							isUAEPassAuthFlag = ls.get(0).get(0);
							isUAEPassAuthDone = ls.get(0).get(1);
							formObject.setValue("UAE_PASS_AUTH_FLAG", isUAEPassAuthFlag);
							formObject.setValue("IS_UAE_PASS_AUTH_DONE", isUAEPassAuthDone);
						}
						logInfo("FETCH_UAE_PASS_INFO","isUAEPassAuthFlag "+isUAEPassAuthFlag+"::isUAEPassAuthDone::"+isUAEPassAuthDone);
						if("Y".equalsIgnoreCase(isUAEPassAuthFlag)){
							if("Y".equalsIgnoreCase(isUAEPassAuthDone)){
								formObject.setStyle("FETCH_UAE_PASS_INFO","disable", "true");
								formObject.clearCombo("CRO_DEC");
								formObject.addItemInCombo("CRO_DEC", "Approve");
								formObject.addItemInCombo("CRO_DEC", "Reject");
							}
						}


						formObject.setStyle("FETCH_UAE_PASS_INFO","disable", "true");
					} else {
						sendMessageValuesList("","Eida Not Valid");	
						formObject.setValue("CRO_DEC","Reject");
						formObject.setStyle("CRO_DEC","disable", "true");
						return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));

					}
				}else if(controlName.equalsIgnoreCase(BTN_SUBMIT) && eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)) {
//					validateRelationData();
					//AO DCRA COMMENTED//15082023
					//add by shahbaz
					logInfo("BTN_SUBMIT & EVENT_TYPE_CLICK ", "INSIDE");
					boolean UAEPassSkip=Boolean.valueOf(formObject.getValue("CHK_SkipUAEPass").toString());
					logInfo("BTN_SUBMIT & EVENT_TYPE_CLICK", "UAEPassSkip: " +UAEPassSkip);
					String reasonSkipUAEPass= formObject.getValue("SKIPUAEPASS_REASON").toString();
					logInfo("EVENT_TYPE_CLICK", "reasonSkipUAEPass: " +reasonSkipUAEPass);
					if(UAEPassSkip == true && reasonSkipUAEPass.equalsIgnoreCase("")) {
						logInfo("EVENT_TYPE_CLICK", " Skip UAE Pass Alert: ");
						sendMessageValuesList("reasonSkipUAEPass", "Please select Reason For Skipping UAE Pass");
					}
					if(((String) formObject.getValue(REQUEST_TYPE)).equalsIgnoreCase("New Account")   //add by shahbaz
							|| ((String) formObject.getValue(REQUEST_TYPE)).equalsIgnoreCase("New Account with Category Change")
							|| ((String) formObject.getValue(REQUEST_TYPE)).equalsIgnoreCase("Category Change Only")
							|| ((String) formObject.getValue(REQUEST_TYPE)).equalsIgnoreCase("Upgrade")){
						String reqType=formObject.getValue(REQUEST_TYPE).toString();
						logInfo("EVENT_TYPE_CLICK" , "Shahbaz--> reqType -- " +reqType);
						dormantCustAlert();	
						logInfo("EVENT_TYPE_CLICK" , "after calling shahbaz dormantCustAlert methed");
					}
					savePreAssessmentDetails();
					String PrevStage =checkpreviousStage();;
					if(PrevStage.equalsIgnoreCase("Introduction")){
					insertDataintoProductGrid();
					}
					String private_Client=formObject.getValue("PRIVATE_CLIENT").toString();	
					if(private_Client.equalsIgnoreCase("Y")) {
					    getPreAssessmentFlag();
					    int returnCode = mandatoryDocAlert();
						if (returnCode < 0){
							sendMessageValuesList("BTN_SUBMIT", "Please Upload/Attach Exception Approval Document");
							return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));	
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
					if(((String) formObject.getValue(CRO_DEC)).equalsIgnoreCase("Reject")){
						String rejectionReason=formObject.getValue(CRO_REJ_REASON).toString();
						String rejectionRemarks=formObject.getValue(CRO_REMARKS).toString();
						if(rejectionReason.equalsIgnoreCase("")){
							sendMessageValuesList(CRO_REJ_REASON, "Please Select Rejection Reason.");
							return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
						}
						if(rejectionRemarks.equalsIgnoreCase("")){
							sendMessageValuesList(CRO_REMARKS, "Please Fill Remarks.");
							return getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
						}
					} else if(!validateRelationData()) {
						return "";
					}
					createHistory();
					createAllHistory();
					formObject.setValue("WI_COMPLETED_FROM",sActivityName);	
					if(formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("0")) {
						formObject.setValue("FIRST_SELECTED_ROW_INDEX","0");	
						//						String sUpdateFirstSelected="update "+sExtTable+" set FIRST_SELECTED_ROW_INDEX='"
						//								+formObject.getValue(SELECTED_ROW_INDEX)+"' Where WI_NAME='"+ sWorkitemId +"'";
						//						log.info("sUpdateFirstSelected"+sUpdateFirstSelected);
						//						formObject.saveDataInDB(sUpdateFirstSelected);
					}
					if( formObject.getValue(SELECTED_ROW_INDEX) == null || formObject.getValue(SELECTED_ROW_INDEX) == "") {
						formObject.setValue("FIRST_SELECTED_ROW_INDEX","0");
					}
					if(formObject.getValue("WI_COMPLETED_FROM").toString().equalsIgnoreCase("Lead_info")) {
					} else {			
						String sProcName = "SP_TemplateGenerationEmailDt";
						List<String> paramlist = new ArrayList<String>();
						paramlist.add ("Text :"+sWorkitemId);
						List sOutput1 = formObject.getDataFromStoredProcedure(sProcName,paramlist);
						log.info("Output AT accrelation CALLING FOR EMAIL DATE  :"+sOutput1);
					}
					String sQuery="select PROD_CODE,CURRENCY from usr_0_product_selected where wi_name='"+sWorkitemId+"'";      
					List<List<String>> sOutput=formObject.getDataFromDB(sQuery);
				}
				
				logInfo("BTN_SUBMIT","ACCOUNT RELATION- FIRST_SELECTED_ROW_INDEX value: "
						+formObject.getValue("FIRST_SELECTED_ROW_INDEX").toString());
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_CHANGE)) {
				/*if(eventType.equalsIgnoreCase(EVENT_TYPE_CHANGE)) {
					if(controlName.equalsIgnoreCase("table33_acc_relation")) {
						if(((String) formObject.getValue(ACC_OWN_TYPE)).equalsIgnoreCase("Joint")) {
							int iRows = getGridCount(ACC_RELATION);
							for(int iLoop=0;iLoop<1;iLoop++) {
								String accrelation = formObject.getTableCellValue(ACC_RELATION, iLoop, 9);
								if(!(accrelation.equalsIgnoreCase("JOF")||accrelation.equalsIgnoreCase("JAF"))) {
									sendMessageValuesList(ACC_RELATION, "Primary Customer In Case Of Joint Can Only Be JOF Or JAF.");
									formObject.setTableCellValue(ACC_RELATION, iLoop, 9, "JAF");

								}
							}
						}
					}
				}*/
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_LOSTFOCUS)) {
				if(controlName.equalsIgnoreCase("table33_sno")){
					logInfo("onClickAcctRel","pControlValue"+data);
					formObject.setValue(SELECTED_ROW_INDEX, (Integer.parseInt(data))+"");
					String sUpdateFirstSelected="update "+sExtTable+" set FIRST_SELECTED_ROW_INDEX='"
							+formObject.getValue(SELECTED_ROW_INDEX)+"' Where WI_NAME='"+ sWorkitemId +"'";
					int result = formObject.saveDataInDB(sUpdateFirstSelected);
					logInfo("onClickAcctRel","sUpdateFirstSelected..."+result);
				}
			}
		} catch (Exception e) {
			log.error("Exception in Event- " + eventType + "control name- "+ controlName + ": ", e);
		} finally {
			logInfo("onClickAcctRel","sendMessageList="+sendMessageList);
			if(!sendMessageList.isEmpty()){
				return  getReturnMessage(false,controlName,sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1).toString());
			}
		}
		return retMsg;
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

public void setFieldsBlank()
{
	clearControls(ARSETFIELDSBLANK);
	formObject.clearTable(EIDA_DETAILS);
}



//Added by reyaz 11092023
public void insertCloseRequestinDecTable()
{ 
	List<List<String>> decList = null;
	logInfo("For insertion in DEC History", "");
	String decHist = "select * from USR_0_AO_DEC_HIST where wi_name= '"
			+ sWorkitemId
			+ "'"
			+ " and  ws_decision='ASSESMENT FAILED'";
	logInfo("Query for decHist : ", "" + decHist);
	decList = formObject.getDataFromDB(decHist);
	if (decList == null || decList.isEmpty()) {
		String insertDec = "INSERT INTO USR_0_AO_DEC_HIST (WI_NAME, CREATE_DAT, USERNAME, WS_DECISION, WS_COMMENTS) "
				+ " VALUES('"
				+ sWorkitemId
				+ "',SYSDATE,'SYSTEM','ASSESMENT FAILED','Case Not Qualified in Assesment')";
		logInfo("Query for insert in hist : ", "" + insertDec);
		formObject.saveDataInDB(insertDec);
}
}

//Added by reyaz 11092023
public String  checkpreviousStage()
{ 
	String prevStage ="";
	try {
		String query2 ="SELECT PREVIOUSSTAGE FROM WFINSTRUMENTTABLE WHERE PROCESSINSTANCEID ='" + this.sWorkitemId + "'";
		logInfo("insertDataintoProductGrid","query2= "+ query2);
		List<List<String>> PREVIOUSSTAGE =formObject.getDataFromDB(query2); 
		if (PREVIOUSSTAGE != null && PREVIOUSSTAGE.size() > 0) {
			prevStage=PREVIOUSSTAGE.get(0).get(0); 
		}
	} catch(Exception e) {
		log.error("Exception in checkpreviousStage: ", e);
	}
	return prevStage;
}


}

