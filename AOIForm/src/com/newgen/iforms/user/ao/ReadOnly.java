package com.newgen.iforms.user.ao;

import java.io.File;
import java.util.ArrayList;
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

public class ReadOnly extends Common implements Constants, IFormServerEventHandler{

	public ReadOnly(IFormReference formObject) {
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
		log.info("Inside executeServerEvent read >");
		log.info("Event: " + eventType + ", Control Name: " + controlName + ", Data: " + data);
		sendMessageList.clear();
		logInfo("onLoadQueryWS","sendmessagelist49: "+sendMessageList);
		String retMsg = getReturnMessage(true,controlName);
		try {
			if(formObject.getValue(CURR_WS_NAME).toString().equalsIgnoreCase("CPD Maker") || 
					formObject.getValue(CURR_WS_NAME).toString().equalsIgnoreCase("CPD Checker")){
				if(isRestrictDisplay()){
					return getReturnMessage(false,controlName,"The user is not authorized to access Staff Data.");
				}
			}

			if(eventType.equalsIgnoreCase(EVENT_TYPE_LOAD)) {
				eventOnLoadQuery(controlName,eventType,data);
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)) {
				if(TABCLICK.equalsIgnoreCase(controlName)) {
					logInfo("Inside onClickEventCPDMakerFourStep EVENT_TYPE_CLICK  controlName1: "
							,controlName+" eventType: "+eventType+" data: "+data);
					onTabCPDMakerFourStep(data);
				} else if(SAVE_TAB_CLICK.equalsIgnoreCase(controlName)) {

				} else if(controlName.equalsIgnoreCase(BTN_VIEW)) {
					logInfo("BTN_VIEW","INSIDE");
					int iSelectedRow = Integer.parseInt((String) formObject.getValue(SELECTED_ROW_INDEX))+1; 
					formObject.clearTable(LVW_DEDUPE_RESULT);
					loadDedupeSearchData(sWorkitemId);
					String sQuery1 = "select dedupe_selected_index from USR_0_CUST_TXN Where WI_NAME='"+ sWorkitemId+"' and cust_sno='"+iSelectedRow+"'";
					logInfo("onClickEventQuery","sQuery1@@@@"+sQuery1);
					List<List<String>> sOutput=formObject.getDataFromDB(sQuery1);
					if(sOutput!=null && sOutput.size()>0){
						int  index1=Integer.parseInt(sOutput.get(0).get(0));
						int[] intA = new int[1];
						intA[0] =index1;
						return getReturnMessage(true, controlName, Integer.toString(index1));
					}
				} 
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_CHANGE)) {

			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_LOSTFOCUS)) {  

			}
		} finally {
			logInfo("onClickQuery","sendMessageList="+sendMessageList);
			if(!sendMessageList.isEmpty()){
				return  getReturnMessage(false,controlName,sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1).toString());
			}
		}
		return retMsg;
	}

	/*public String onClickEventQuery(String controlName,String eventType,String data){
		if(controlName.equalsIgnoreCase(BTN_VIEW)) {
			int iSelectedRow = Integer.parseInt((String) formObject.getValue(SELECTED_ROW_INDEX))+1; 
			formObject.clearTable(LVW_DEDUPE_RESULT);
			loadDedupeSearchData(sWorkitemId);
			String sQuery1 = "select dedupe_selected_index from USR_0_CUST_TXN Where WI_NAME='"+ sWorkitemId+"' and cust_sno='"+iSelectedRow+"'";
			logInfo("onClickEventQuery","sQuery1@@@@"+sQuery1);
            List<List<String>> sOutput=formObject.getDataFromDB(sQuery1);
            if(sOutput!=null && sOutput.size()>0){
            int  index1=Integer.parseInt(sOutput.get(0).get(0));
            int[] intA = new int[1];
            intA[0] =index1;
            return getReturnMessage(true, controlName, Integer.toString(index1));
            }
            //setRowSelectionInListView(LVW_DEDUPE_RESULT,intA);  
            //getReturnMessage()
			//formObject.setNGLVWSelectedRows(LVW_DEDUPE_RESULT,intA);
		}
		return "";
	}*/
	
	public void onTabCPDMakerFourStep(String data) {
		logInfo("onTabCPDMakerFourStep", "INSIDE");
		List<List<String>> recordList;
		String[] selectedSheetInfo = data.split(",");
		String tabCaption = selectedSheetInfo[0];
		int selectedSheetIndex = Integer.parseInt(selectedSheetInfo[1]);
		int sCurrTabIndex = selectedSheetIndex;
		boolean isCustInfoVisit=false;
	    formObject.setStyle(VIEW,DISABLE,FALSE);

		if(selectedSheetIndex == 1 || selectedSheetIndex == 2 || selectedSheetIndex == 3 || selectedSheetIndex == 4) {
			setDataQueryMode(sActivityName);
		} else if(selectedSheetIndex == 5 || selectedSheetIndex == 6 || selectedSheetIndex == 7) {
			logInfo("onTabCPDMakerFourStep","selectedSheetIndex: "+selectedSheetIndex+
					"scan mode : "+formObject.getValue(SCAN_MODE).toString());
			try { 
				loadCPDcustdata();
				if(!formObject.getValue(SCAN_MODE).toString().equalsIgnoreCase("New WMS ID")) {
					populateScreeningDataCRO();
					populateScreeningDataCPD();
				} else {
					populateScreeningDataCPD();
				}
			} catch(Exception e) {
				logError("tabClickQuery", e);;
			}
			populateTRSD();
			populateTRSDCPD();
			populateTRSDRemarks();
		} 
	}

	private void eventOnLoadQuery(String controlName,String  eventType,String data) {
		logInfo("Inside eventOnLoadCPDMakerFourStep  eventType: " , eventType + ", Control Name: " + controlName + ", Data: " + data);

		try
		{
			//setTabVisibleQuery(sActivityName);   //kdd
			setDataQueryMode(sActivityName);
			setFormDisable();
			setTemp_usr_0_product_selected();
            populateCRSData();
            populateTRSD();
            populateUAEPassInfoStatus(sWorkitemId);
    		logInfo("Inside decision  eventType: ","");
    		formObject.setValue(TXT_CUSTOMERNAME, formObject.getTableCellValue(ACC_RELATION, 0,1).toString());//(1, "AO_acc_relation.NAME"));
			formObject.setValue(TXT_DOB, formObject.getTableCellValue(ACC_RELATION, 0, 5).toString());//(1, "AO_acc_relation.DOB"));
			String cid = formObject.getTableCellValue(ACC_RELATION, 0, 2).toString();
    		logInfo("Inside decision  CID1: ",cid);
			if(!formObject.getTableCellValue(ACC_RELATION, 0, 2).toString().equalsIgnoreCase("")){
	    		logInfo("Inside decision  CID: ",cid);
				formObject.setValue(TXT_CUSTOMERID,cid);//(1, "AO_acc_relation.cid"));
			} else {
	    		logInfo("Inside decision  CID1: ",cid);
				formObject.setValue(TXT_CUSTOMERID,"");//(1, "AO_acc_relation.cid"));

			}
            if(getGridCount(DECISION_LVW) == 0) {
        		logInfo("Inside decision  eventType: ","");
				String sQuery = "SELECT CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,CHANNEL,WS_NAME,WS_COMMENTS FROM (SELECT TO_CHAR(CREATE_DAT,'DD/MM/YYYY HH:MI:SS AM')CREATE_DAT, USERID, USERNAME, GROUP_NAME, WS_DECISION,REJ_REASON, CHANNEL, WS_NAME, WS_COMMENTS,TO_CHAR(CREATE_DAT,'YYYYMMDDHH24:MI:SS') A FROM USR_0_AO_DEC_HIST WHERE WI_NAME = '"+sWorkitemId+"') ORDER BY A";
				List<List<String>> recordList = formObject.getDataFromDB(sQuery);
				log.info("decision history query "+sQuery);
				loadListView(recordList,"CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,CHANNEL,WS_NAME,WS_COMMENTS",DECISION_LVW);
				//loadListView(recordList,"CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,CHANNEL,WS_NAME,WS_COMMENTS",'DECISION_HISTORY');							
			}
            if(getGridCount("DECISION_HISTORY") == 0) {
				logInfo("DECISION_HISTORY","DECISION_HISTORY: "+getGridCount("DECISION_HISTORY"));
				String sQuery1="SELECT CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,CHANNEL,WS_NAME,WS_COMMENTS FROM (SELECT TO_CHAR(CREATE_DAT,'DD/MM/YYYY HH:MI:SS AM') CREATE_DAT, USERID, USERNAME, GROUP_NAME, WS_DECISION,REJ_REASON, CHANNEL,WS_NAME,WS_COMMENTS,TO_CHAR(CREATE_DAT,'YYYYMMDDHH24:MI:SS') A FROM USR_0_AO_DEC_HIST WHERE WI_NAME = '"+sWorkitemId+"') ORDER BY A";
				logInfo("DECISIONTAB",sQuery1);
				List<List<String>> recordList = formObject.getDataFromDB(sQuery1); 
				loadListView(recordList,"CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,CHANNEL,WS_NAME,WS_COMMENTS","DECISION_HISTORY");							
				formObject.setStyle(DEC_STORAGE_DETAILS,VISIBLE,TRUE);
			} 
            if(!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")){
                int custNo = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
                String tempQuery="SELECT GRADUATION_DATE, SPECIAL_CUSTOMER_IDENTIFIER FROM USR_0_UDF_DETAILS WHERE ACC_RELATION_SERIALNO='"+custNo+"' AND WI_NAME='"+sWorkitemId+"'";
                List<List<String>> listContent= formObject.getDataFromDB(tempQuery);
                if(listContent.size()>0){
                    populateUDFGrid(tempQuery);
                } 
            }
            clearUdfGrid();
                   
    	    formObject.setStyle(VIEW,DISABLE,FALSE);
			loadECBChqBookValidation();
			populateStndInstr();
		}
		catch(Exception e)
		{
			logError("eventOnLoadQuery", e);;
		}
    
	}

	public void setDataQueryMode(String sActivity)
	{
		try	{
		String sQuery ="";		
		
		if(!sActivity.equalsIgnoreCase(ACTIVITY_QUERY))
	{
		if(sActivityName.equalsIgnoreCase(ACTIVITY_ACCOUNT_RELATION))
		{
			formObject.addItemInCombo("srch_nation","");
			formObject.addItemInCombo("MAN_NATIONALITY","");
			formObject.addItemInCombo("FCR_SEARCH","");	
			formObject.addItemInCombo(CRO_DEC,"");
			formObject.addItemInCombo(CRO_REJ_REASON,"");
		}
		else if(sActivityName.equalsIgnoreCase(ACTIVITY_DDE_CUST_INFO) || sActivityName.equalsIgnoreCase(ACTIVITY_CUST_SCREEN))
		{
			setDDEModeCombos();
			populatePersonalDataCPD();
			populateRiskData();
			populateKYCData();
			populateKycMultiDrop();
			populatePreAssesmentDetails();  //shahbaz
			populatePepAssesmentDetails();
			populateComparisonFields();
			populateTRSD();
			if(sActivityName.equalsIgnoreCase(ACTIVITY_CUST_SCREEN))
			{
				populateScreeningDataCRO();			
			}
		}
		else if(sActivityName.equalsIgnoreCase(ACTIVITY_QDE_CUST_INFO) || sActivityName.equalsIgnoreCase(ACTIVITY_CUST_SCREEN_QDE))
		{
			setCustScreeningCombos();
			populateComparisonFields();
			populateRiskDataQDE();
			populatePepAssesmentDetails(); //AO DCRA
			populatePreAssesmentDetails(); //Added by Jamshed
			populateTRSD();
			if(sActivityName.equalsIgnoreCase(ACTIVITY_CUST_SCREEN_QDE))
			{
				populateScreeningDataCRO();			
			}
			
		}
		else if(sActivityName.equalsIgnoreCase(ACTIVITY_APP_ASSESSMENT))
		{
			loadApplicationAssessmentData();
			loadApplicationAssessmentDataCPD();	
		} else if(sActivityName.equalsIgnoreCase(ACTIVITY_DDE_ACCOUNT_INFO) || sActivityName.equalsIgnoreCase(ACTIVITY_DDE_ACCOUNT_INFO_CHECK)) {
			setDDEModeCombos();
			populatePersonalDataCPD();
			populateRiskData();
			populateKYCData();
			populateKycMultiDrop();
			populateComparisonFields();
			populateScreeningDataCRO();	
			loadApplicationAssessmentData();
			populateTRSD();
			populatePreAssesmentDetails(); //Jamshed
			populatePepAssesmentDetails();
			if(getGridCount(FAC_OFRD_LVW_CRO) == 0) {
				String sQuery3  =  "SELECT CUST_ID,CUST_NAME,CARD_TYPE FROM USR_0_DEBITCARD_OFFERED WHERE WI_NAME = '"+sWorkitemId+"'";		
				List<List<String>> recordList = formObject.getDataFromDB(sQuery3);
				logInfo(" sQuery3 ",sQuery3); 
				loadListView(recordList,"CUST_ID,CUST_NAME,CARD_TYPE",FAC_OFRD_LVW_CRO);
			}
			if(getGridCount (PROD_OFRD_CRO_LVW)  == 0) {
				String sQuery4  =  "SELECT PRODUCT_CODE,PRODUCT_DESC,CURRENCY_DESC,'' FROM USR_0_PRODUCT_OFFERED WHERE WI_NAME = '"+sWorkitemId+"' ORDER BY TO_NUMBER(PRODUCT_CODE)";				
				List<List<String>> recordList = formObject.getDataFromDB(sQuery4);
				logInfo(" sQuery4 ",sQuery4); 
				loadListView(recordList,"PRODUCT_CODE,PRODUCT_DESCRIPTION,CURRENCY,PROD_ACC_OPENING",PROD_OFRD_CRO_LVW);
			}
			if(getGridCount(FAC_LVW_CRO) == 0) {
				String sQuery5 = "SELECT CID,FACILITY,DESCRIPTION FROM USR_0_FACILITY_SELECTED WHERE WI_NAME = '"+sWorkitemId+"' ORDER BY FACILITY";
				List<List<String>> recordList = formObject.getDataFromDB(sQuery5);
				logInfo(" sQuery5 ",sQuery5); 
				loadListView(recordList,"CID,FACILITY,DESCRIPTION",FAC_LVW_CRO);
			}

		}
		else if(sActivityName.equalsIgnoreCase(ACTIVITY_QDE_ACCOUNT_INFO) || sActivityName.equalsIgnoreCase(ACTIVITY_QDE_ACCOUNT_INFO_CHECK))
		{
			setCustScreeningCombos();
			populateComparisonFields();
			populateRiskDataQDE();
			populatePreAssesmentDetails(); //Jamshed
			populatePepAssesmentDetails();
			populateScreeningDataCRO();	
			loadApplicationAssessmentData();
			populateTRSD();
			if(getGridCount(FAC_OFRD_LVW_CRO) == 0) {
				String sQuery3  =  "SELECT CUST_ID,CUST_NAME,CARD_TYPE FROM USR_0_DEBITCARD_OFFERED WHERE WI_NAME = '"+sWorkitemId+"'";		
				List<List<String>> recordList = formObject.getDataFromDB(sQuery3);
				logInfo(" sQuery3 ",sQuery3); 
				loadListView(recordList,"CUST_ID,CUST_NAME,CARD_TYPE",FAC_OFRD_LVW_CRO);
			}
			if(getGridCount (PROD_OFRD_CRO_LVW)  == 0) {
				String sQuery4  =  "SELECT PRODUCT_CODE,PRODUCT_DESC,CURRENCY_DESC,'' FROM USR_0_PRODUCT_OFFERED WHERE WI_NAME = '"+sWorkitemId+"' ORDER BY TO_NUMBER(PRODUCT_CODE)";				
				List<List<String>> recordList = formObject.getDataFromDB(sQuery4);
				logInfo(" sQuery4 ",sQuery4); 
				loadListView(recordList,"PRODUCT_CODE,PRODUCT_DESCRIPTION,CURRENCY,PROD_ACC_OPENING",PROD_OFRD_CRO_LVW);
			}
			if(getGridCount(FAC_LVW_CRO) == 0) {
				String sQuery5 = "SELECT CID,FACILITY,DESCRIPTION FROM USR_0_FACILITY_SELECTED WHERE WI_NAME = '"+sWorkitemId+"' ORDER BY FACILITY";
				List<List<String>> recordList = formObject.getDataFromDB(sQuery5);
				logInfo(" sQuery5 ",sQuery5); 
				loadListView(recordList,"CID,FACILITY,DESCRIPTION",FAC_LVW_CRO);
			}

		}
		else
		{
			setCPDCheckerCombos();//
			//setOtherCombosReadOnly();// kdd
			populatePersonalDataCPD();//
			populateRiskData();//
			populateKYCData();//
			populatePreAssesmentDetails();  //Jamshed
			populatePepAssesmentDetails();
			populateComparisonFields();//
			populateTRSD();
			populateTRSDCPD();
			String sQueryTnx = "";
			String sQueryUnionHist = "";

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
			if(sActivityName.equalsIgnoreCase(ACTIVITY_MAIL_ROOM)||sActivityName.equalsIgnoreCase(ACTIVITY_BULK_EOD_CHECKER)||sActivityName.equalsIgnoreCase(ACTIVITY_QUERY)|| sActivityName.equalsIgnoreCase(ACTIVITY_WORK_EXIT) || sActivityName.equalsIgnoreCase("Close Request"))
			{
				PopulateScreeningDataCRO_History();
				populateScreeningDataCPD_History();
				loadApplicationAssessmentData_History();
				loadApplicationAssessmentDataCPD_History();	
				loadSICombos_History();

			}
			else
			{
				populateScreeningDataCRO();
				populateScreeningDataCPD();
				loadApplicationAssessmentData();
				loadApplicationAssessmentDataCPD();	
				loadSICombos();
			}


			populateStndInstr();
			if(sActivityName.equalsIgnoreCase("MailRoomOperation")||sActivityName.equalsIgnoreCase("Bulk EOD Checker")||sActivityName.equalsIgnoreCase("Query1")|| sActivityName.equalsIgnoreCase("Work Exit1")|| sActivityName.equalsIgnoreCase("Close Request"))
			{
			}
			else
			{
			}
		}
		}
		else
		{	
			setCPDCheckerCombos();//
			//setOtherCombosReadOnly();//  kdd
			populatePersonalDataCPD();//
			populateRiskData();//
			populateKYCData();//
			populateComparisonFields();//
			populatePepAssesmentDetails(); //AO DCRA
			populatePreAssesmentDetails(); //Added by Jamshed
			//populateTRSD();
			if(sActivityName.equalsIgnoreCase("MailRoomOperation")||sActivityName.equalsIgnoreCase("Bulk EOD Checker")||sActivityName.equalsIgnoreCase("Query1")|| sActivityName.equalsIgnoreCase("Work Exit1")|| sActivityName.equalsIgnoreCase("Close Request"))
			{
				PopulateScreeningDataCRO_History();
				populateScreeningDataCPD_History();
				loadApplicationAssessmentData_History();
				loadApplicationAssessmentDataCPD_History();	
				loadSICombos_History();
			}
			else
			{
			populateScreeningDataCRO();
			populateScreeningDataCPD();
			loadApplicationAssessmentData();
			loadApplicationAssessmentDataCPD();	
			loadSICombos();
			}
			
			
			populateStndInstr();
			
			populateTRSD();
			populateTRSDCPD();
			//bikash...eligibility
			System.out.println("TRSD=========================");
			if( formObject.getValue("trsd_approvedResult").toString().equalsIgnoreCase("Approved")) //kdd
					formObject.setValue("Combo2", "Eligible");
			else
				formObject.setValue("Combo2", "Not Eligible");  //kdd
			String sQueryTnx="";
			String sQueryUnionHist="";
			if(sActivityName.equalsIgnoreCase("MailRoomOperation")||sActivityName.equalsIgnoreCase("Bulk EOD Checker")||sActivityName.equalsIgnoreCase("Query1")|| sActivityName.equalsIgnoreCase("Work Exit1")|| sActivityName.equalsIgnoreCase("Close Request"))
			{
			}
			else
			{
			}
		}
	}
		catch(Exception e)
		{
			logError("EXCETTION ",e);
		}
	}
	
	public void setFormDisable()
	{
		frame81_CPD_Disable();
		Frame2_Disable();
		disableControls(new String[]{SEC_PERSONAL_DET,SEC_DEL_INST,SEC_ACC_INFO_ECD,SEC_ACC_INFO_DCL,SEC_ACC_INFO_PD,SEC_SI,SEC_CAT_CHNG});
//		formObject.setNGEnable("Frame19", false);
//		formObject.setNGEnable("Frame24", false);
//		formObject.setNGEnable("Frame6", false);
//		formObject.setNGEnable("Frame23", false);
//		formObject.setNGEnable("Frame78", false);
//		formObject.setNGEnable("Frame35", false);
	}
	
	public void populateScreeningDataCPD_History()
	{
		logInfo("PopulateScreeningDataCRO_History","INSIDE");
		int iSelectedRow = 0;
		String sCustNo = "";
		iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
		sCustNo = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 0);//acc_relation.sno
		logInfo("inside PopulateScreeningDataCRO_History sCustNo = ",sCustNo);
		String sQueryTnx="";
		String sQueryUnionHist="";
		logInfo("inside PopulateScreeningDataCRO_History sCustNo = ",sCustNo);
		String sQuery  = "SELECT SYSTEM_DEC_CPD,BANK_DEC_CPD,BLACKLIST_DEC_CPD,WORLD_CHECK_DEC_CPD,BAD_CHECK_DEC_CPD, FINAL_ELIGIBILITY_CPD,"
				+ "BLACKLIST_REMARKS_CPD,BAD_CHECK_REMARKS_CPD,WORLD_CHECK_REMARKS_CPD,SYSTEM_REMARKS_CPD FROM USR_0_CUST_TXN WHERE WI_NAME= '"
				+ ""+sWorkitemId+"' AND CUST_SNO = N'"+sCustNo+"'" ;
		logInfo("inside PopulateScreeningDataCRO_History sQuery1 = ",sQuery);
		List<List<String>> sOutput = formObject.getDataFromDB(sQuery);
		logInfo("inside PopulateScreeningDataCRO_History sOutput1 = ",sOutput.toString());
		setValuesFromDB(sOutput, new String[]{CRO_SYS_DEC,CPD_BANK_DECISION,CPD_CHK_MATCH_FOUND,CPD_MTCH_FOUND,CPD_FINAL_ELIGIBILITY,
			CPD_CHK_REMARKS,CPD_REMARKS,SANC_WRLD_CHK_REMARKS,CPD_RISK_ASSESS_MARKS});

		

		sQueryTnx ="SELECT CUST_NAME,NATIONALITY,DOB,PASSPORT_NO,'',BLACKLIST_TYPE FROM USR_0_BLACKLIST_DATA WHERE WI_NAME='"+sWorkitemId+"' AND BLACKLIST_TYPE IN (SELECT CHECKTYPE FROM USR_0_CHECK_TYPE WHERE TYPE='INT')";
		sQueryUnionHist =" union all SELECT CUST_NAME,NATIONALITY,DOB,PASSPORT_NO,'',BLACKLIST_TYPE FROM USR_0_BLACKLIST_DATA_CPD_HIST WHERE WI_NAME='"+sWorkitemId+"' AND BLACKLIST_TYPE IN (SELECT CHECKTYPE FROM USR_0_CHECK_TYPE WHERE TYPE='INT')";
		sQuery=sQueryTnx+sQueryUnionHist;
		logInfo("inside PopulateScreeningDataCRO_History sQuery2 = ",sQuery);
		List<List<String>> recordList = formObject.getDataFromDB(sQuery);
		logInfo("inside PopulateScreeningDataCRO_History recordList1 = ",recordList.toString());
		loadListView(recordList,"Name,Nationality,DOB,Passport_No,Reason,Department",CPD_CHK_INT_BLK_LVW);
 
		sQueryTnx ="SELECT CUST_NAME,NATIONALITY,DOB,PASSPORT_NO,'',BLACKLIST_TYPE FROM USR_0_BLACKLIST_DATA_CPD WHERE WI_NAME='"+sWorkitemId+"' AND BLACKLIST_TYPE IN (SELECT CHECKTYPE FROM USR_0_CHECK_TYPE WHERE TYPE='EXT')";
		sQueryUnionHist =" union all SELECT CUST_NAME,NATIONALITY,DOB,PASSPORT_NO,'',BLACKLIST_TYPE FROM USR_0_BLACKLIST_DATA_HIST WHERE WI_NAME='"+sWorkitemId+"' AND BLACKLIST_TYPE IN (SELECT CHECKTYPE FROM USR_0_CHECK_TYPE WHERE TYPE='EXT')";
		sQuery=sQueryTnx+sQueryUnionHist;
		logInfo("inside PopulateScreeningDataCRO_History sQuery3 = ",sQuery);
		recordList = formObject.getDataFromDB(sQuery);
		logInfo("inside PopulateScreeningDataCRO_History recordList2 = ",recordList.toString());
		loadListView(recordList,"Name,Nationality,DOB,Passport_No,Reason,Department",CPD_HD2_LVW);

		sQuery ="SELECT CUST_NAME,NATIONALITY,DOB,PASSPORT_NO,EXPIRY_PERIOD,DEPARTMENT FROM USR_0_CENTRAL_BANK_DATA_CPD WHERE WI_NAME='"+sWorkitemId+"' AND CUST_SNO = '"+sCustNo+"'";
		logInfo("inside PopulateScreeningDataCRO_History sQuery4 = ",sQuery);
		recordList = formObject.getDataFromDB(sQuery);
		logInfo("inside PopulateScreeningDataCRO_History recordList3 = ",recordList.toString());
		loadListView(recordList,"Name,Nationality,DOB,Passport_No,Reason,Department",CPD_CNTRL_BNK_BAD_LVW);
		
		sQuery ="SELECT CUST_ID,CUST_NAME,CURRENT_RISK_SYSTEM,CURRENT_RISK_BUSSINESS,PREVIOUS_RISK, FCR_RISK, APPROVAL_REQ,RISK_CLASSIFICATION FROM USR_0_RISK_ASSESSMENT_DATA WHERE WI_NAME='"+sWorkitemId+"' AND SNO = '"+sCustNo+"'";
		logInfo("inside PopulateScreeningDataCRO_History sQuery5 = ",sQuery);
		recordList = formObject.getDataFromDB(sQuery);
		logInfo("inside PopulateScreeningDataCRO_History recordList4 = ",recordList.toString());
		setValuesFromDB(sOutput, new String[]{SANCT_RISK_CID,SANCT_RISK_NAME,SANCT_RISK_CURRENT_RSK_SYSTEM,SANCT_RISK_CURRENT_RSK_BANK,
				SANCT_RISK_PREVIOUS_RSK, SANCT_RISK_FCR_RSK,SANCT_RISK_COMPL_APP_REQ,SANCT_RISK_RSK_CLSF});
		logInfo("in PopulateScreeningDataCRO_History","before calling set_Values_From_Usr_0_Risk_Data ");
		set_Values_From_Usr_0_Risk_Data();

		if(!sActivityName.equalsIgnoreCase("CPD Maker"))
		{
			sQuery ="SELECT CUST_ID,CUST_NAME,CURRENT_RISK_SYSTEM,CURRENT_RISK_BUSSINESS,PREVIOUS_RISK, FCR_RISK, APPROVAL_REQ,RISK_CLASSIFICATION FROM USR_0_RISK_ASSESSMENT_DATA WHERE WI_NAME='"+sWorkitemId+"' AND SNO = '"+sCustNo+"'";
			logInfo("inside PopulateScreeningDataCRO_History sQuery5 = ",sQuery);
			recordList = formObject.getDataFromDB(sQuery);
			logInfo("inside PopulateScreeningDataCRO_History recordList4 = ",recordList.toString());
			setValuesFromDB(sOutput, new String[]{CPD_RISK_CID,CPD_RISK_NAME,CPD__RISK_CURRENT_RSK_SYSTEM,CPD_RISK_CURRENT_RSK_BANK,
					CPD_RISK_PREVIOUS_RSK,CPD_RISK_FCR_RSK,CPD_RISK_RSK_CLSF,CPD_RISK_INITIAL_ASSESS_DATE,CPD_RISK_COMPL_APP_REQ });
			logInfo("in PopulateScreeningDataCRO_History","before calling set_Values_From_Usr_0_Risk_Data ");
			set_Values_From_Usr_0_Risk_Data();
		}
	}
	
	public void loadApplicationAssessmentDataCPD_History()	{
		logInfo ("LoadApplicationAssessmentDataCPD_History","INSIDE LoadApplicationAssessmentDataCPD_History");
		int iPrimaryCust = Integer.parseInt(getPrimaryCustomerSNO());
		String sCustID  = formObject.getTableCellValue(ACC_RELATION, iPrimaryCust, 2);
		logInfo("LoadApplicationAssessmentDataCPD_History","sCustID"+sCustID);
		String sAccRelation  =formObject.getTableCellValue(ACC_RELATION, iPrimaryCust, 7);
		logInfo("LoadApplicationAssessmentDataCPD_History","sAccRelation"+sAccRelation);
		String sQueryTnx = "";
		String sQueryUnionHist = "";
		String sQuery= "SELECT A.CID,B.CUST_NAME,A.ACC_RELATION,B.CURRENT_RISK_SYSTEM,B.CURRENT_RISK_BUSSINESS FROM ACC_RELATION_REPEATER A,USR_0_RISK_ASSESSMENT_DATA_CPD B WHERE A.WI_NAME=B.WI_NAME AND A.SNO=B.SNO AND A.WI_NAME = '"+sWorkitemId+"' ORDER BY TO_NUMBER(A.SNO)";
		logInfo("LoadApplicationAssessmentDataCPD_History","sQuery"+sQuery);
		List<List<String>> recordList = formObject.getDataFromDB(sQuery);
		logInfo("LoadApplicationAssessmentDataCPD_History","recordList"+recordList);
		loadListView(recordList,"CID,CUSTOMER_NAME,ACCOUNT_RELATIONSHIP,SYS_DEFINED_RISK,BANK_DEFINED_RISK",SEC_RISK_ASSESS_CPD_LVW);
		sQuery= "SELECT B.CUST_ID,B.CUST_FULL_NAME,B.final_eligibility_cpd,A.ACC_RELATION, B.final_eligibility_cpd FROM ACC_RELATION_REPEATER A,USR_0_CUST_TXN B WHERE A.WI_NAME ='"+sWorkitemId+"' and A.WI_NAME=B.WI_NAME AND A.SNO=B.CUST_SNO ORDER BY TO_NUMBER(A.SNO)";
		logInfo("LoadApplicationAssessmentDataCPD_History","sQuery"+sQuery);
		recordList = formObject.getDataFromDB(sQuery);
		logInfo("LoadApplicationAssessmentDataCPD_History","recordList"+recordList);
		loadListView(recordList,"CUST_ID,CUST_FULL_NAME,TRSD_ASSESSMENT,ACC_RELATIONSHIP,ELIGIBILITY",ELIG_LVW_CPD);
		if(sActivityName.equalsIgnoreCase("CPD Maker") && sAccRelation.equalsIgnoreCase("Existing")){
			if(getGridCount(FAC_EXST_LVW_CPD)==0){
				loadExistingDebitCard("USR_0_DEBITCARD_EXISTING_CPD",FAC_EXST_LVW_CPD,sCustID);
			}
		}
		if(sAccRelation.equalsIgnoreCase("Existing")){
			sQueryTnx = "SELECT PRODUCT_CODE,PRODUCT_NAME,CURRENCY FROM USR_0_PRODUCT_EXISTING WHERE WI_NAME ='"+sWorkitemId+"'  AND CUSTOMER_ID='"+sCustID+"'";
			sQueryUnionHist= " union all SELECT PRODUCT_CODE,PRODUCT_NAME,CURRENCY FROM USR_0_PRODUCT_EXISTING_HIST WHERE WI_NAME ='"+sWorkitemId+"'  AND CUSTOMER_ID='"+sCustID+"'";	
			sQuery=sQueryTnx+sQueryUnionHist;
			logInfo("LoadApplicationAssessmentDataCPD_History","sQuery"+sQuery);
			recordList = formObject.getDataFromDB(sQuery);
			logInfo("LoadApplicationAssessmentDataCPD_History","recordList"+recordList);
			loadListView(recordList,"Product Code,Product Name,Currency",PROD_SEC_EXIST_CPD_LVW);
			sQueryTnx = "SELECT CUST_ID,EMBOSS_NAME,CARD_TYPE_DESC,STATUS_DESC,ISSUANCE_DATE,EXPIRY_DATE FROM USR_0_DEBITCARD_EXISTING_CPD WHERE WI_NAME = '"+sWorkitemId+"'  AND CUST_ID='"+sCustID+"'";
			sQueryUnionHist = " union all SELECT CUST_ID,EMBOSS_NAME,CARD_TYPE_DESC,STATUS_DESC,ISSUANCE_DATE,EXPIRY_DATE FROM USR_0_DEBITCARD_EXISTING_CPD_h WHERE WI_NAME = '"+sWorkitemId+"'  AND CUST_ID='"+sCustID+"'";
			sQuery=sQueryTnx+sQueryUnionHist;
			logInfo("LoadApplicationAssessmentDataCPD_History","sQuery"+sQuery);
			recordList = formObject.getDataFromDB(sQuery);
			logInfo("LoadApplicationAssessmentDataCPD_History","recordList"+recordList);
			loadListView(recordList,"CUST_ID,EMBOSS_NAME,CARD_TYPE_DESC,STATUS_DESC,ISSUANCE_DATE,EXPIRY_DATE","FAC_EXST_LVW_CPD");	
			sQuery = "SELECT PRODUCT_NAME,CURRENCY FROM USR_0_BANQUADETAILS WHERE WI_NAME ='"+sWorkitemId+"' AND CUSTOMER_ID='"+sCustID+"'";
			logInfo("LoadApplicationAssessmentDataCPD_History","sQuery"+sQuery);
			recordList  = formObject.getDataFromDB(sQuery);
			logInfo("LoadApplicationAssessmentDataCPD_History","recordList"+recordList);
			String[] sAllProducts;
			String sEachProducts;
			//int iTotalRetrived = Integer.parseInt(getTagValues(sOutput,"TotalRetrieved"));
			int iTotalRetrived = recordList.size();
			try {
				if(iTotalRetrived!=0){
					//sAllProducts = getTagValue(sOutput,"Record").split(";");
					for(int i=0;i<iTotalRetrived;i++)
					{
						//sEachProducts = sAllProducts[i].split(",");
						//						sOutput="<ListItems><ListItem><SubItem></SubItem><SubItem>"+sEachProducts[0]+
						//								"</SubItem><SubItem>"+sEachProducts[1]+"</SubItem></ListItem></ListItems>";
						loadListView(recordList,"PRODUCT_CODE,CURRENCY",PROD_CRO_LVW);
					}
				}
			}
			catch(Exception e){
				logError("LoadApplicationAssessmentDataCPD_History", e);
			}
		}
	}
	
	public void loadApplicationAssessmentData_History()	{
		logInfo ("LoadApplicationAssessmentData_History","INSIDE LoadApplicationAssessmentData_History");
		int iPrimaryCust = Integer.parseInt(getPrimaryCustomerSNO());
		String sCustID  = formObject.getTableCellValue(ACC_RELATION, iPrimaryCust, 2);
		logInfo("LoadApplicationAssessmentData_History","sCustID"+sCustID);
		String sAccRelation  =formObject.getTableCellValue(ACC_RELATION, iPrimaryCust, 7);
		logInfo("LoadApplicationAssessmentData_History","sAccRelation"+sAccRelation);
		String sQueryTnx = "";
		String sQueryUnionHist = "";
		String sQuery= "SELECT A.CID,B.CUST_NAME,A.ACC_RELATION,B.CURRENT_RISK_SYSTEM,B.CURRENT_RISK_BUSSINESS FROM ACC_RELATION_REPEATER A,USR_0_RISK_ASSESSMENT_DATA_CPD B WHERE A.WI_NAME=B.WI_NAME AND A.SNO=B.SNO AND A.WI_NAME = '"+sWorkitemId+"' ORDER BY TO_NUMBER(A.SNO)";
		logInfo("LoadApplicationAssessmentData_History","sQuery"+sQuery);
		List<List<String>> recordList = formObject.getDataFromDB(sQuery);
		logInfo("LoadApplicationAssessmentData_History","recordList"+recordList);
		loadListView(recordList,"CID,CUSTOMER_NAME,ACCOUNT_RELATIONSHIP,SYS_DEFINED_RISK,BANK_DEFINED_RISK",RISK_ASSESS_LVW);
		sQuery= "SELECT B.CUST_ID,B.CUST_FULL_NAME,B.final_eligibility_cpd,A.ACC_RELATION, B.final_eligibility_cpd FROM ACC_RELATION_REPEATER A,USR_0_CUST_TXN B WHERE A.WI_NAME ='"+sWorkitemId+"' and A.WI_NAME=B.WI_NAME AND A.SNO=B.CUST_SNO ORDER BY TO_NUMBER(A.SNO)";
		logInfo("LoadApplicationAssessmentData_History","sQuery"+sQuery);
		recordList = formObject.getDataFromDB(sQuery);
		logInfo("LoadApplicationAssessmentData_History","recordList"+recordList);
		loadListView(recordList,"CUST_ID,CUST_FULL_NAME,TRSD_ASSESSMENT,ACC_RELATIONSHIP,ELIGIBILITY",ELIG_LVW_CRO);
		if(sActivityName.equalsIgnoreCase("CPD Maker") && sAccRelation.equalsIgnoreCase("Existing")){
			if(getGridCount(FAC_EXST_LVW_CPD)==0){
				loadExistingDebitCard("USR_0_DEBITCARD_EXISTING_CPD",FAC_EXST_LVW_CRO,sCustID);
			}
		}
		if(sAccRelation.equalsIgnoreCase("Existing")){
			sQueryTnx = "SELECT PRODUCT_CODE,PRODUCT_NAME,CURRENCY FROM USR_0_PRODUCT_EXISTING WHERE WI_NAME ='"+sWorkitemId+"'  AND CUSTOMER_ID='"+sCustID+"'";
			sQueryUnionHist= " union all SELECT PRODUCT_CODE,PRODUCT_NAME,CURRENCY FROM USR_0_PRODUCT_EXISTING_HIST WHERE WI_NAME ='"+sWorkitemId+"'  AND CUSTOMER_ID='"+sCustID+"'";	
			sQuery=sQueryTnx+sQueryUnionHist;
			logInfo("LoadApplicationAssessmentData_History","sQuery"+sQuery);
			recordList = formObject.getDataFromDB(sQuery);
			logInfo("LoadApplicationAssessmentData_History","recordList"+recordList);
			loadListView(recordList,"Product Code,Product Name,Currency",PROD_CRO_LVW);
			sQueryTnx = "SELECT CUST_ID,EMBOSS_NAME,CARD_TYPE_DESC,STATUS_DESC,ISSUANCE_DATE,EXPIRY_DATE FROM USR_0_DEBITCARD_EXISTING_CPD WHERE WI_NAME = '"+sWorkitemId+"'  AND CUST_ID='"+sCustID+"'";
			sQueryUnionHist = " union all SELECT CUST_ID,EMBOSS_NAME,CARD_TYPE_DESC,STATUS_DESC,ISSUANCE_DATE,EXPIRY_DATE FROM USR_0_DEBITCARD_EXISTING_CPD_h WHERE WI_NAME = '"+sWorkitemId+"'  AND CUST_ID='"+sCustID+"'";
			sQuery=sQueryTnx+sQueryUnionHist;
			logInfo("LoadApplicationAssessmentData_History","sQuery"+sQuery);
			recordList = formObject.getDataFromDB(sQuery);
			logInfo("LoadApplicationAssessmentData_History","recordList"+recordList);
			loadListView(recordList,"CUST_ID,EMBOSS_NAME,CARD_TYPE_DESC,STATUS_DESC,ISSUANCE_DATE,EXPIRY_DATE",FAC_EXST_LVW_CRO);	
			sQuery = "SELECT PRODUCT_NAME,CURRENCY FROM USR_0_BANQUADETAILS WHERE WI_NAME ='"+sWorkitemId+"' AND CUSTOMER_ID='"+sCustID+"'";
			logInfo("LoadApplicationAssessmentData_History","sQuery"+sQuery);
			recordList  = formObject.getDataFromDB(sQuery);
			logInfo("LoadApplicationAssessmentData_History","recordList"+recordList);
			String[] sAllProducts;
			String sEachProducts;
			//int iTotalRetrived = Integer.parseInt(getTagValues(sOutput,"TotalRetrieved"));
			int iTotalRetrived = recordList.size();
			try {
				if(iTotalRetrived!=0){
					//sAllProducts = getTagValue(sOutput,"Record").split(";");
					for(int i=0;i<iTotalRetrived;i++)
					{
						//sEachProducts = sAllProducts[i].split(",");
						//						sOutput="<ListItems><ListItem><SubItem></SubItem><SubItem>"+sEachProducts[0]+
						//								"</SubItem><SubItem>"+sEachProducts[1]+"</SubItem></ListItem></ListItems>";
						loadListView(recordList,"PRODUCT_CODE,CURRENCY",PROD_CRO_LVW);
					}
				}
			}
			catch(Exception e){
				logError("LoadApplicationAssessmentData_History", e);
			}
		}
	}
	
	public void setTabVisibleQuery(String sActivity) {
		if(!sActivity.equalsIgnoreCase("QUERY1")) {
		
		if(sActivityName.equalsIgnoreCase("QDE_Cust_Info") || sActivityName.equalsIgnoreCase("DDE_Cust_Info"))
		{}		
		else if(sActivityName.equalsIgnoreCase("Customer_Screen") || sActivityName.equalsIgnoreCase("Customer_Screen_QDE"))
		{}
		else if(sActivityName.equalsIgnoreCase("Application_Assessment"))
		{}		
		else if(sActivityName.equalsIgnoreCase("QDE_ Account_Info") || sActivityName.equalsIgnoreCase("QDE_Acc_Info_Chk"))
		{}
		else
		{
			formObject.setTabStyle("tab3","0",ENABLE,TRUE);
			formObject.setTabStyle("tab3","1",ENABLE,TRUE);
			formObject.setTabStyle("tab3","2",ENABLE,TRUE);
			formObject.setTabStyle("tab3","3",ENABLE,TRUE);
			formObject.setTabStyle("tab3","4",ENABLE,TRUE);
			formObject.setTabStyle("tab3","5",ENABLE,FALSE);
			formObject.setTabStyle("tab3","6",ENABLE,TRUE);
			formObject.setTabStyle("tab3","7",ENABLE,FALSE);
			formObject.setTabStyle("tab3","8",ENABLE,TRUE);
			
			if(!formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("New Account"))
			{
				formObject.setTabStyle("tab3","7",ENABLE,TRUE);
			}
		}
	}
		else
		{
			formObject.setTabStyle("tab3","0",ENABLE,TRUE);
			formObject.setTabStyle("tab3","1",ENABLE,TRUE);
			formObject.setTabStyle("tab3","2",ENABLE,TRUE);
			formObject.setTabStyle("tab3","3",ENABLE,TRUE);
			formObject.setTabStyle("tab3","4",ENABLE,TRUE);
			formObject.setTabStyle("tab3","5",ENABLE,FALSE);
			formObject.setTabStyle("tab3","6",ENABLE,TRUE);
			formObject.setTabStyle("tab3","7",ENABLE,FALSE);
			formObject.setTabStyle("tab3","8",ENABLE,TRUE);
			formObject.setTabStyle("tab3","9",ENABLE,TRUE);
			try{
			if(!formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("New Account"))
			{
				formObject.setTabStyle("tab3","7",ENABLE,TRUE);
			}
			}catch(Exception e){

			}
			
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

}
