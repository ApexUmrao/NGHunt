<%@page import="javax.print.DocFlavor.STRING"%>
<%@page import="com.newgen.omni.jts.cmgr.XMLParser"%>
<%@page import="java.util.*,java.text.*"%>
<%@page import="com.newgen.wfdesktop.xmlapi.*,java.io.*,java.util.*"%>
<jsp:useBean id="customSession" class="com.newgen.custom.wfdesktop.session.WFCustomSession" scope="session"/>
<%@ page import="com.newgen.omni.wf.util.app.*"%> 

<html>
<head>
<title>Application Summary</title>
<style>
th {
	border: 1px solid Gainsboro;
}
.FormLabel {
	background-color: #D3D3D3;
	word-wrap:break-word;
	width:10%;
	FONT-FAMILY:Calibri;
	font-size:13px;
}
.FormValue {
	background-color: #F0EFEF;
	word-wrap:break-word;
	width:25%;
	FONT-FAMILY:Calibri;
	font-size:13px;
}
br{
	height:5px;
}
.divHead{
	background-color:#DBEEDE;font:bold 8pt DejaVu Sans;color:blue;vertical-align:middle;text-align:left;
}
.divColl{
	background-color:#E1E1E1;font:8pt DejaVu Sans;color:black;vertical-align:middle;text-align:left;
}
.InternalTableHeader{
	color:White;
	font-size: 13pt;
	FONT-FAMILY: Calibri;
	
}
.APMainFrameTable
{
	margin:0px;	
	border-color:blue;
	border-width: 1px;
	border-style:solid;
	padding:10px;
	background-color:#F7F7F7;
	
}
.collapsible{
	background-color:#777;
	color: white;
	cursor: pointer;
	padding: 0px;
	width: 100px;
	border: none;
	text-align: left;
	outline:none;
}
.active, .collapsible:hover{
	background-color:#555;
}
.APCellwhiteBoldHead
{
	background-color:#BA1B18; COLOR:#ffffff ;
	FONT-FAMILY: Calibri;FONT-WEIGHT: normal;font-size: 13pt;
	padding-left: 2px;
	vertical-align:middle;
	text-align:left;	
	
	width:100%;
	font-size=16px;
}
.content{
	padding: 0 18px;
	display: none;
	overflow: hidden;
	background-color:#f1f1f1;
}
.APCellwhiteBold
{
	BACKGROUND-COLOR:#FFFFFF; COLOR:#000000 ;
	FONT-FAMILY: Calibri;FONT-WEIGHT: normal;font-size: 9pt;
	padding-left: 2px;
	vertical-align:middle;
	text-align:left;
	display:inline;
}
table,td{
			  border-collapse: collapse;
			  font-size:15px;
			  width="50px";
			  border: 0.1px solid white;
			}
.innerTable
{
	width:100%;border: 0.1px solid white;
}
table{width:95%}
</style>
</head>
<body onbeforeunload="ConfirmClose()" onunload="HandleOnClose()" >
	<FORM name="mainFrm" action="javascript:selectandreturn()">
		<div style="vertical-align: top;padding-left: 20px">
							<Center style="color : #BA1B18;FONT-FAMILY: Calibri;font-size: 16pt"><b>Application Summary - TFO</b></Center>
						<%!
		private String prepareAPSelectInputXml(String sQuery, String sCabname, String sSessionId){	
				return "<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+sQuery+"</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
			}
		public String getTagValues(String sXML, String sTagName) 
		{  
			String sTagValues = "";
			String sStartTag = "<" + sTagName + ">";
			String sEndTag = "</" + sTagName + ">";
			String tempXML = sXML;
			tempXML=tempXML.replaceAll("&","#amp#");
			try
			{
			
				for(int i=0;i<sXML.split(sEndTag).length-1;i++) 
				{
					if(tempXML.indexOf(sStartTag) != -1) 
					{
						sTagValues += tempXML.substring(tempXML.indexOf(sStartTag) + sStartTag.length(), tempXML.indexOf(sEndTag));
						//System.out.println("sTagValues"+sTagValues);
						tempXML=tempXML.substring(tempXML.indexOf(sEndTag) + sEndTag.length(), tempXML.length());
					}
					if(tempXML.indexOf(sStartTag) != -1) 
					{    
						sTagValues +=",";
						//System.out.println("sTagValues"+sTagValues);
					}
				}
				if(sTagValues.indexOf("#amp#")!= -1)
				{
					System.out.println("Index found");
					sTagValues =sTagValues.replaceAll("#amp#", "&");
				}
				//System.out.println(" Final sTagValues"+sTagValues);
			}
			catch(Exception e) 
			{   
			}
			sTagValues = replaceEscapeCharacters(sTagValues);
				return sTagValues;
		}
		
		public String replaceEscapeCharacters(String sTagValues){
			
			String sRes=sTagValues;
			try{
				if(sTagValues.length()>0){
					sRes=sRes.replaceAll("<","&lt");
					sRes=sRes.replaceAll(">","&gt");
					sRes=sRes.replaceAll("'","&#039");
					sRes=sRes.replaceAll("'","&#034");
				}
				
			}catch(Exception e){
				
			}
			return sRes;
		}
		
		
		public ArrayList<ArrayList<String>> getTableData(String sOutputXML, String ...sParameters){
			ArrayList<ArrayList<String>> arrL = new ArrayList<ArrayList<String>>();
			ArrayList<String> ArrIntenal = null;
			System.out.println("OutputXML  "+sOutputXML);
			String sRecord="";
			String sMainCode;
			if(!sOutputXML.equals(""))
			{
				sMainCode = sOutputXML.substring(sOutputXML.indexOf("<MainCode>")+10,sOutputXML.indexOf("</MainCode>"));
				System.out.println("sMainCode  "+sMainCode);
				if(sMainCode.equalsIgnoreCase("0"))
				{		
						while((sOutputXML.indexOf("<Record>")>-1))
						{	
							ArrIntenal = new ArrayList<String>();
							sRecord = sOutputXML.substring(sOutputXML.indexOf("<Record>")+8,sOutputXML.indexOf("</Record>"));
							System.out.println("sRecord  "+sRecord);
							for(String sParameter:sParameters){
								String sTempString = sRecord.substring(sRecord.indexOf("<"+sParameter+">")+sParameter.length()+2,sRecord.indexOf("</"+sParameter+">"));
								System.out.println("sTempString  "+sTempString);
								sTempString=sTempString.replaceAll("\n","<br>");
								System.out.println("sTempString  "+sTempString);
								ArrIntenal.add(sTempString);
							}
							System.out.println("internalList  "+ArrIntenal);
							arrL.add(ArrIntenal);
							System.out.println("MasterList  "+arrL);
							sOutputXML=sOutputXML.substring(sOutputXML.indexOf("</Record>")+"</Record>".length());
						}
					}
				
				}
			return arrL;
		}
	

		public String getQueryFormLoadPC(String strTFO_ReqCategory,String strTFO_REQUEST_TYPE, String strTFO_trnsCurr, String sLoanGroup, String sPrdType){
			try{
				String sTxnCurrency = " AND REQ_TXN_CRNCY = 'ALL'";
				String sLoanType = "and REQ_LOAN_TYPE = 'ALL'";
				String sProductType = "and req_prd_code='ALL'";
				String sQuery="";
				if (("ILCB".equalsIgnoreCase(strTFO_ReqCategory)) || ("TL".equalsIgnoreCase(strTFO_ReqCategory)) || ("IC".equalsIgnoreCase(strTFO_ReqCategory)))
					sTxnCurrency = (strTFO_trnsCurr.equalsIgnoreCase("AED")) ? "AND REQ_TXN_CRNCY IN ('AED','ALL')" : "AND REQ_TXN_CRNCY IN ( 'ALL','NAED')";
				if ("TL".equalsIgnoreCase(strTFO_ReqCategory)) {
					sLoanType = (sLoanGroup.equalsIgnoreCase("IC")) ? "and REQ_LOAN_TYPE IN ('IC','ALL')" : "and REQ_LOAN_TYPE IN ('ALL')";
				}
			
				if ((strTFO_ReqCategory.equalsIgnoreCase("IC")) && ((("T3U5".equalsIgnoreCase(sPrdType)) || ("I3U5".equalsIgnoreCase(sPrdType))))) {
					sProductType = " and req_prd_code in('" + sPrdType + "','ALL')";
				}
			
				System.out.println("Product Type " + sProductType);
				System.out.println("transaction currency " + sTxnCurrency);
				System.out.println("Bill_Settlement_Curr " + sLoanType);
				sQuery = "Select CONTROL_DESC, EXT_CONTROL from TFO_DJ_FINAL_DEC_CNTRL_MAST where  WORKSTEP = 'PROCESSING CHECKER' and REQ_CAT_CODE='"+strTFO_ReqCategory + "' and REQ_TYPE_CODE = '" + strTFO_REQUEST_TYPE + "' " + 
								sTxnCurrency + sLoanType + sProductType;
				return sQuery;
			}catch(Exception e){
				return "ExceptionRaised";
			}
		}
	
		
		public String getQueryFormLoadPM(String strTFO_ReqCategory,String strTFO_REQUEST_TYPE, String strTFO_trnsCurr, String sLoanGroup, String sPrdType){
			try{
				String sTxnCurrency = " AND REQ_TXN_CRNCY = 'ALL'";
				String sLoanType = "and REQ_LOAN_TYPE = 'ALL'";
				String sProductType = "and req_prd_code='ALL'";
				String sQuery="";
				if (("ILCB".equalsIgnoreCase(strTFO_ReqCategory)) || ("TL".equalsIgnoreCase(strTFO_ReqCategory)) || ("IC".equalsIgnoreCase(strTFO_ReqCategory)))
					sTxnCurrency = (strTFO_trnsCurr.equalsIgnoreCase("AED")) ? "AND REQ_TXN_CRNCY IN ('AED','ALL')" : "AND REQ_TXN_CRNCY IN ( 'ALL','NAED')";
				if ("TL".equalsIgnoreCase(strTFO_ReqCategory)) {
					sLoanType = (sLoanGroup.equalsIgnoreCase("IC")) ? "and REQ_LOAN_TYPE IN ('IC','ALL')" : "and REQ_LOAN_TYPE IN ('ALL')";
				}
			
				if ((strTFO_ReqCategory.equalsIgnoreCase("IC")) && ((("T3U5".equalsIgnoreCase(sPrdType)) || ("I3U5".equalsIgnoreCase(sPrdType))))) {
					sProductType = " and req_prd_code in('" + sPrdType + "','ALL')";
				}
			
				System.out.println("Product Type " + sProductType);
				System.out.println("transaction currency " + sTxnCurrency);
				System.out.println("Bill_Settlement_Curr " + sLoanType);
				sQuery = "Select CONTROL_DESC, EXT_CONTROL from TFO_DJ_FINAL_DEC_CNTRL_MAST where  WORKSTEP = 'PROCESSING MAKER' and REQ_CAT_CODE='"+strTFO_ReqCategory + "' and REQ_TYPE_CODE = '" + strTFO_REQUEST_TYPE + "' " + 
								sTxnCurrency + sLoanType + sProductType;
				return sQuery;
			}catch(Exception e){
				return "ExceptionRaised";
			}
		}
		
		public boolean treasuryTabHandling(String sReqCatCode, String sReqTpeCode, String sCompRef, String sTrnsCur, String sLoanCur, String sSettlementCurr){
			if((sReqCatCode.equalsIgnoreCase("IFS") || sReqCatCode.equalsIgnoreCase("IFP")
				||sReqCatCode.equalsIgnoreCase("EC") || sReqCatCode.equalsIgnoreCase("ELCB")
				||sReqCatCode.equalsIgnoreCase("IC") || sReqCatCode.equalsIgnoreCase("ILCB")
				||sReqCatCode.equalsIgnoreCase("TL") || sReqCatCode.equalsIgnoreCase("DBA")) 
			&& (sReqTpeCode.equalsIgnoreCase("LD") || sReqTpeCode.equalsIgnoreCase("IC_BS")
				||sReqTpeCode.equalsIgnoreCase("EC_BS") || sReqTpeCode.equalsIgnoreCase("EC_DISC")
				||sReqTpeCode.equalsIgnoreCase("EC_CAP") || sReqTpeCode.equalsIgnoreCase("EC_LDDI")
				||sReqTpeCode.equalsIgnoreCase("ILCB_BS") || sReqTpeCode.equalsIgnoreCase("ELCB_BS")
				||sReqTpeCode.equalsIgnoreCase("ELCB_DISC") || sReqTpeCode.equalsIgnoreCase("ELCB_CLBP")
				||sReqTpeCode.equalsIgnoreCase("TL_LD") ||sReqTpeCode.equalsIgnoreCase("DBA_STL")) 
			&& (!sCompRef.equalsIgnoreCase("Y"))
			&& (!(sTrnsCur.equalsIgnoreCase(sLoanCur)||((sReqCatCode.equalsIgnoreCase("IC") || sReqCatCode.equalsIgnoreCase("DBA") ||sReqCatCode.equalsIgnoreCase("ILCB")
													 ||sReqCatCode.equalsIgnoreCase("EC") || sReqCatCode.equalsIgnoreCase("ELCB")) && sTrnsCur.equalsIgnoreCase(sSettlementCurr)))))
													 return true;
			else
				return false;
		}
	
		%>

						<%
		//String sTabName =request.getParameter("TabName");
		//String sThirdParty =request.getParameter("IsThirdParty");
		System.out.println("Called ");

		String sWiName=request.getParameter("winame");
		String sTable="";
		String sReqCatCode="",sReqTpeCode="",sLoanCur="",sCompRef="",sTrnsCur="",sDuplicateCheck="",sSettlementCurr="",sloanGroup="",sPrdType="",processing_sys="";
		String sInputXML ="";
		String sOutputXML="";
		String sOutputMasterXML="";
		String sCabname=customSession.getEngineName();
        String sSessionId = customSession.getDMSSessionId();
        String sUserName = customSession.getUserName();
        String sJtsIp = customSession.getJtsIp();
        String iJtsPort = String.valueOf(customSession.getJtsPort());
		String fromCabinet="";
		String sLiveCount="";
		
		System.out.println("sCabname "+sCabname+" sUserName "+sUserName+" sJtsIp "+sJtsIp);
		sLiveCount="SELECT COUNT(1) CNT FROM EXT_TFO WHERE WI_NAME  = N'"+sWiName+"'";
		sInputXML = prepareAPSelectInputXml(sLiveCount,sCabname, sSessionId);
		if(!sInputXML.isEmpty())
			sOutputXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXML);
		if("0".equals(getTagValues(sOutputXML, "CNT")))
		{
			fromCabinet="ADWMS006.";
		}else{
			fromCabinet="";
		}
		String returnStr="";
		String aryCurr[]=null;
		String sQuery1="Select REQUEST_CATEGORY REQ_CAT_CODE,IFS_LOAN_GRP,PRODUCT_TYPE AS PRD_CODE,IS_DEDUP,INF_LOAN_CURR LOAN_CURR,COMP_POSITIVE_MATCH COMP_REF1,TRANSACTION_CURRENCY TRNS_CUR ,REQUEST_TYPE REQ_TYPE_CODE,initiator_branch,initiator_userid,req_date_time,wi_name,(Select req_subby from TFO_DJ_REQ_SUB_BY_MAST where req_subby_key= requested_by and req_cat_code=ext.REQUEST_CATEGORY and process_type=ext.process_type  and rownum=1) REQUESTED_BY,(Select request_category from TFO_DJ_REQUEST_CATEGORY_MAST where request_category_id=ext.REQUEST_CATEGORY and rownum=1) REQUEST_CATEGORY,(Select request_type from TFO_DJ_REQUEST_TYPE_MAST where request_type_id=ext.request_type and req_cat_code=ext.REQUEST_CATEGORY and process_type=ext.process_type and rownum=1) REQUEST_TYPE,transaction_reference,FND_TRNSFER_FCUBS_REF,PMNT_HUB_REF,BILL_LN_REFRNCE,customer_id, (Select CURR_CODE||'-'||currency from USR_0_CURRENCY where curr_code=ext.transaction_currency) TRANSACTION_CURRENCY,transaction_amount,customer_name,(Select source_channel from TFO_DJ_SOURCE_CHANNEL_MAST where source_key=ext.source_channel and req_cat_code=ext.REQUEST_CATEGORY ) SOURCE_CHANNEL,(Select brn_code from TFO_DJ_TRNS_BRN_CODE_MAST where brn_key=ext.relationship_type) relationship_type,(Select home_branch from USR_0_HOME_BRANCH where code=ext.delivery_branch) delivery_branch,(CASE WHEN ext.request_category IN('GRNT') and ext.REQUEST_TYPE IN ('NI') THEN  (Select to_char(prd_desc) from tfo_dj_prd_mast where req_cat_key = ext.REQUEST_CATEGORY and req_type_key = ext.request_type and src_channel_key = ext.source_channel and  prd_key = ext.PRODUCT_TYPE and rownum=1) WHEN ext.request_category IN('IFS','IFP','IC','ILC','ILCB','EC','ELC','ELCB','TL') and ext.REQUEST_TYPE IN ('LD','IC_BL','EC_BL','EC_LDDI','ILCB_BL','ELCB_BL','ILC_NI','ELC_LCA','TL_LD')  THEN  (Select to_char(prd_desc) from tfo_dj_prd_mast where req_cat_key = ext.REQUEST_CATEGORY and  prd_key = ext.PRODUCT_TYPE and rownum=1) ELSE (Select CODE_DESC from TFO_DJ_PRDCODE_DESC_MAST where code = ext.PRODUCT_TYPE and rownum=1) END) AS PRODUCT_TYPE,(Select description from TFO_DJ_TRNS_TENOR_MAST where wms_code = ext.trn_tenor) trn_tenor,SUBSTR(EXP_DATE,0,INSTR(EXP_DATE,':')-4) EXP_DATE ,(Select yes_no from USR_0_YES_NO where unique_id=ext.is_acc_valid) is_acc_valid,account_number,(CASE WHEN EXT.REQUEST_CATEGORY = 'GRNT'  THEN (Select DESCRIPTION from tfo_dj_amendment_mast where  WMS_CODE=EXT.AMEND_TYPE and trns_code = ext.TRN_TENOR and req_cat_key= ext.request_category ) WHEN EXT.REQUEST_CATEGORY IN('ILC','ELC')  THEN (Select DESCRIPTION from tfo_dj_amendment_mast where  WMS_CODE=EXT.AMEND_TYPE and req_cat_key= ext.request_category ) ELSE N'NA' END ) AMEND_TYPE,new_trn_amt,to_char(to_timestamp(new_exp_date, 'YYYY-MM-DD HH24:MI:SS.FF3'), 'dd-mm-yyyy') new_exp_date,(Select yes_no from USR_0_YES_NO where unique_id=ext.trn_third_party) trn_third_party,(Select CURR_CODE||'-'||currency from USR_0_CURRENCY where curr_code=ext.INF_LOAN_CURR) INF_LOAN_CURR,(CASE WHEN EXT.REQUEST_CATEGORY IN('IFS','IFP','TL','IC','ILCB','EC','ELCB') THEN (Select DESCRIPTION from tfo_dj_amendment_mast where  WMS_CODE=EXT.INF_AMEND_TYPE and  req_cat_key= ext.request_category ) ELSE N'NA' END ) INF_AMEND_TYPE, to_char(to_timestamp(INF_NEW_MATURITY_DATE, 'YYYY-MM-DD HH24:MI:SS.FF3'), 'dd-mm-yyyy') INF_NEW_MATURITY_DATE,INF_TENOR_DAYS,(SELECT TENOR_BASE_DESC FROM TFO_DJ_LN_TNR_MAST WHERE REQ_CAT_KEY=ext.REQUEST_CATEGORY AND TENOR_BASE_KEY=ext.INF_TENOR_BASE) INF_TENOR_BASE, to_char(to_timestamp(INF_BASE_DOC_DATE, 'YYYY-MM-DD HH24:MI:SS.FF3'), 'dd-mm-yyyy') INF_BASE_DOC_DATE, INF_ACTUAL_TENOR_BASE, to_char(to_timestamp(INF_MATURITY_DATE, 'YYYY-MM-DD HH24:MI:SS.FF3'), 'dd-mm-yyyy') INF_MATURITY_DATE , DECODE(INF_CHARGE_ACC_NUM,'0','',INF_CHARGE_ACC_NUM) AS INF_CHARGE_ACC_NUM, INF_CHARGE_ACC_TITLE,INF_CHARGE_ACC_CURR, DECODE(INF_SETTLEMENT_ACC_NUM,'0','',INF_SETTLEMENT_ACC_NUM) AS INF_SETTLEMENT_ACC_NUM , INF_SETTLEMENT_ACC_TITLE,INF_SETTLEMENT_ACC_CURR,DECODE(INF_CREDIT_ACC_NUM,'0','',INF_CREDIT_ACC_NUM) AS INF_CREDIT_ACC_NUM , INF_CREDIT_ACC_TITLE,INF_CREDIT_ACC_CURR, REMITTANCE_CURR, REMITTANCE_AMT, (CASE WHEN BC_CALL_DONE = 'Y' THEN 'Yes' WHEN BC_CALL_DONE='N' THEN 'No' ELSE '' END ) BC_CALL_DONE, BC_REMAKS_NOT_DONE, BC_CALL_INFO, to_char(trunc(BC_DATE_OF_CALL),'DD-MM-YYYY') BC_DATE_OF_CALL, BC_RSN_OF_CALL, BC_NAME_OF_CALLER, BC_TIME_OF_CALL, BC_BUYER_NAME, BC_CONTACT_PERSON, BC_BUYER_CONTACT_NUM,(Select CURR_CODE||'-'||currency from USR_0_CURRENCY where curr_code=ext.BC_INVOICE_CURR)  BC_INVOICE_CURR, BC_INVOICE_AMT, BC_INVOICE_NUMBER,customer_category,fcr_cust_email_id,profit_center_code,address_line_1,address_line_2,mobile_number,po_box,fax_no,emirates,rm_name,rm_mobile_number,is_customer_vip,rm_email,trade_cust_email_id,(Select DESCRIPTION from TFO_DJ_YES_NO_NA_MAST where code=ext.REQ_SIGN_MAN_PPM and REQ_CAT_CODE=ext.REQUEST_CATEGORY) REQ_SIGN_MAN_PPM,(Select DESCRIPTION from TFO_DJ_YES_NO_NA_MAST where code=ext.SUFF_BAL_AVL_PPM and REQ_CAT_CODE=ext.REQUEST_CATEGORY) SUFF_BAL_AVL_PPM,(Select DESCRIPTION from TFO_DJ_YES_NO_NA_MAST where code=ext.DOC_REV_SUCC_PPM and REQ_CAT_CODE=ext.REQUEST_CATEGORY) DOC_REV_SUCC_PPM,(Select description from tfo_dj_txt_format_mast where unique_id=ext.TXT_FORMAT_PPM) TXT_FORMAT_PPM, (Select DESCRIPTION from TFO_DJ_YES_NO_NA_MAST where code=ext.TXT_REQ_APP_PPM and REQ_CAT_CODE=ext.REQUEST_CATEGORY) TXT_REQ_APP_PPM, (Select DESCRIPTION from TFO_DJ_YES_NO_NA_MAST where code=ext.LMTCRE_APP_AVL_PPM and REQ_CAT_CODE=ext.REQUEST_CATEGORY) LMTCRE_APP_AVL_PPM,(CASE WHEN COMP_CHK_DONE = 'Y' THEN 'Yes' WHEN COMP_CHK_DONE='N' THEN 'No' ELSE '' END ) COMP_CHK_DONE,(CASE WHEN COMP_POSITIVE_MATCH = 'Y'  THEN 'Yes' WHEN COMP_POSITIVE_MATCH='A' THEN 'Approval Received' WHEN COMP_POSITIVE_MATCH='N' THEN 'No' ELSE '' END ) COMP_POSITIVE_MATCH,(CASE WHEN COMP_IMB_CHK = 'Y'  THEN 'Yes' WHEN COMP_IMB_CHK='N' THEN 'Not Applicable' WHEN COMP_IMB_CHK='DOW' THEN 'Done outside WMS' WHEN COMP_IMB_CHK='DA' THEN 'Deferral Approval' ELSE '' END ) COMP_IMB_CHK,(CASE WHEN COMP_REF = 'Y' THEN 'Yes' ELSE 'No' END ) COMP_REF,COMP_EXP_REMARKS,TR_TREASURY_DEAL_NUM,TR_EXCHANGE_RATE,TR_LOAN_AMT,(CASE WHEN TR_REFER_TREASURY='Y' THEN 'Yes' ELSE 'No' END) TR_REFER_TREASURY, DECODE(BILL_CUST_HLDING_ACC_US,'0','',(SELECT YES_NO FROM USR_0_YES_NO WHERE UNIQUE_ID = EXT.BILL_CUST_HLDING_ACC_US )) AS BILL_CUST_HLDING_ACC_US, DECODE(BILL_RVSD_DOC_AVL,'0','',(SELECT YES_NO FROM USR_0_YES_NO WHERE UNIQUE_ID = EXT.BILL_RVSD_DOC_AVL)) AS BILL_RVSD_DOC_AVL, DECODE(BILL_MODE_OF_PMNT,'0','',(SELECT PMNT_MODE FROM TFO_DJ_MODE_PMNT WHERE PMNT_MODE_CODE = EXT.BILL_MODE_OF_PMNT )) AS BILL_MODE_OF_PMNT, BILL_OUR_LC_NO, BILL_CORRSPNDNT_BNK, DECODE(LC_DOC_COURIER,'0','',(SELECT DESCRIPTION FROM TFO_DJ_YES_NO_MAST WHERE CODE = EXT.LC_DOC_COURIER)) AS LC_DOC_COURIER, LC_CONF_AMNT, LC_CORRSPNDNT_BNK, DECODE(GRNT_CHRG_ACC_TITLE,'0','',GRNT_CHRG_ACC_TITLE) AS GRNT_CHRG_ACC_TITLE, GRNT_CHRG_ACC_CRNCY, (Select CURR_CODE||'-'||currency from USR_0_CURRENCY where curr_code=ext.TR_SELL_CUR) AS TR_SELL_CUR, (Select CURR_CODE||'-'||currency from USR_0_CURRENCY where curr_code=ext.TR_BUY_CUR) AS TR_BUY_CUR, TR_SELL_AMT, TR_RATE_REQ, UI_EXCHANGE_RATE, TR_EXCEPTIONS,DECODE(SWIFT_PREPARED,'true','Yes','No')SWIFT_PREPARED,DECODE(LMT_BOOKED,'true','Yes','No')LMT_BOOKED,DECODE(CA_CHCKED,'Y','Yes','NA','Not Applicable','')CA_CHCKED,DECODE(IS_STAGE_CHANGED,'true','Yes','No')IS_STAGE_CHANGED, DECODE(BLCK_LMTS_AVL,'true','Yes','No')BLCK_LMTS_AVL,DECODE(IS_MSG_PREPARED,'true','Yes','No')IS_MSG_PREPARED,DECODE(ARE_TRCRS_DISABLED,'true','Yes','No')ARE_TRCRS_DISABLED,DECODE(IS_M202_PREPARED,'true','Yes','No')IS_M202_PREPARED, DECODE(IS_PMNT_HUB_CMPLTD,'true','Yes','No')IS_PMNT_HUB_CMPLTD,DECODE(UDF_FIELD_CHK,'true','Yes','No')UDF_FIELD_CHK, DECODE(IS_EMAIL_READY,'true','Yes','No')IS_EMAIL_READY,DECODE(ELC_REIMB_CL_PER_PAY,'true','Yes','No')ELC_REIMB_CL_PER_PAY,DECODE(ELC_ENS_COR_LIM_LINES,'true','Yes','No')ELC_ENS_COR_LIM_LINES,DECODE(ELC_CHRG_COL_PER_ICG_ICIEC,'true','Yes','No')ELC_CHRG_COL_PER_ICG_ICIEC,DECODE(ELC_SWFT_IB_CHRG_COFM,'true','Yes','No')ELC_SWFT_IB_CHRG_COFM,DECODE(ELC_COL_CHRG_CUST,'true','Yes','No')ELC_COL_CHRG_CUST,DECODE(ELC_PREP_ASS_LETTER,'true','Yes','No')ELC_PREP_ASS_LETTER,DECODE(ELC_SWFT_IB_AOP_DTLS,'true','Yes','No')ELC_SWFT_IB_AOP_DTLS,DECODE(EC_CHRG_COL_SPL_PRC_AVL,'true','Yes','No')EC_CHRG_COL_SPL_PRC_AVL,DECODE(EC_DOC_BNK_PER_CUST_INST,'true','Yes','No')EC_DOC_BNK_PER_CUST_INST,DECODE(EC_ENS_COR_LMT_LINES,'true','Yes','No')EC_ENS_COR_LMT_LINES,DECODE(EC_COR_CUST_AC_FCUBS,'true','Yes','No')EC_COR_CUST_AC_FCUBS,DECODE(EC_CHK_CLIEU_LST,'true','Yes','No')EC_CHK_CLIEU_LST,DECODE(EC_ENS_IF_TRD_TR_LOANS,'true','Yes','No')EC_ENS_IF_TRD_TR_LOANS,DECODE(EC_CNFM_FEE_COL_ICG_APRVAL,'true','Yes','No')EC_CNFM_FEE_COL_ICG_APRVAL,DECODE(ELCB_CHRG_COL_SPL_PRC_AVL,'true','Yes','No')ELCB_CHRG_COL_SPL_PRC_AVL,DECODE(ELCB_DBT_CORR_AC_FEE,'true','Yes','No')ELCB_DBT_CORR_AC_FEE,DECODE(ELCB_DOC_BNK_PER_CUST_INST,'true','Yes','No')ELCB_DOC_BNK_PER_CUST_INST,DECODE(ELCB_NECES_PRC_APRVL,'true','Yes','No')ELCB_NECES_PRC_APRVL,DECODE(ELCB_ENS_COR_LMT_LINES,'true','Yes','No')ELCB_ENS_COR_LMT_LINES,DECODE(ELCB_CHK_ACC_NO_CRDT,'true','Yes','No')ELCB_CHK_ACC_NO_CRDT,DECODE(ILCB_PRC_TXN_FCUBS_COR_MRG,'true','Yes','No')ILCB_PRC_TXN_FCUBS_COR_MRG,DECODE(ILCB_MT_PREP_SENT_COR_BNK,'true','Yes','No')ILCB_MT_PREP_SENT_COR_BNK,DECODE(ILCB_PAY_HUB_CMPL,'true','Yes','No')ILCB_PAY_HUB_CMPL,DECODE(ELC_ENS_LMT_INT_OIL_LC,'0','',(SELECT DESCRIPTION FROM TFO_DJ_YES_NA_MAST WHERE CODE = EXT.ELC_ENS_LMT_INT_OIL_LC)) AS ELC_ENS_LMT_INT_OIL_LC,DECODE(ELC_SILENT_CONF,'0','',(SELECT DESCRIPTION FROM TFO_DJ_YES_NA_MAST WHERE CODE = EXT.ELC_SILENT_CONF)) AS ELC_SILENT_CONF,DECODE(ELC_CON_FCC_ICIEC_COV,'0','',(SELECT DESCRIPTION FROM TFO_DJ_YES_NA_MAST WHERE CODE = EXT.ELC_CON_FCC_ICIEC_COV)) AS ELC_CON_FCC_ICIEC_COV,DECODE(ELC_UPDT_COMM_GL_TRACK,'0','',(SELECT DESCRIPTION FROM TFO_DJ_YES_NA_MAST WHERE CODE = EXT.ELC_UPDT_COMM_GL_TRACK)) AS ELC_UPDT_COMM_GL_TRACK,DECODE(EC_CONT_FCUBS_ICIEC_COV,'0','',(SELECT DESCRIPTION FROM TFO_DJ_YES_NA_MAST WHERE CODE = EXT.EC_CONT_FCUBS_ICIEC_COV)) AS EC_CONT_FCUBS_ICIEC_COV,DECODE(EC_UPDT_SUSP_TRCK_ICIEC_PAY_GL,'0','',(SELECT DESCRIPTION FROM TFO_DJ_YES_NA_MAST WHERE CODE = EXT.EC_UPDT_SUSP_TRCK_ICIEC_PAY_GL)) AS EC_UPDT_SUSP_TRCK_ICIEC_PAY_GL,DECODE(ELCB_ENS_IF_TRD_TR_LOANS,'0','',(SELECT DESCRIPTION FROM TFO_DJ_YES_NO_NA_MAST WHERE CODE = EXT.ELCB_ENS_IF_TRD_TR_LOANS and REQ_CAT_CODE=ext.REQUEST_CATEGORY )) AS ELCB_ENS_IF_TRD_TR_LOANS, DISCREPANCY_DTLS, DECODE(EC_VER_TXN_DTL_AUTH,'true','Yes','No')EC_VER_TXN_DTL_AUTH,DECODE(EC_CHK_CORR_BNK_ADD_AWB,'true','Yes','No')EC_CHK_CORR_BNK_ADD_AWB, DECODE(ILCB_AUTH_FCUBS_TEL_SWFT_MSG,'true','Yes','No')ILCB_AUTH_FCUBS_TEL_SWFT_MSG,DECODE(ELCB_VER_TENOR_DUE_DT,'true','Yes','No')ELCB_VER_TENOR_DUE_DT,DECODE(ELCB_CHK_CORR_BNK_ADD_AWB,'true','Yes','No')ELCB_CHK_CORR_BNK_ADD_AWB,DECODE(ELCB_COR_REIMB_PAY_INST,'true','Yes','No')ELCB_COR_REIMB_PAY_INST,DECODE(SWIFT_LMT_VERIFY,'true','Yes','No')SWIFT_LMT_VERIFY,DECODE(ELCB_CRDT_ADV_EMAIL_CUST,'0','',(SELECT DESCRIPTION FROM TFO_DJ_YES_NO_NA_MAST WHERE CODE = EXT.ELCB_CRDT_ADV_EMAIL_CUST and REQ_CAT_CODE=ext.REQUEST_CATEGORY )) AS ELCB_CRDT_ADV_EMAIL_CUST,DECODE(ELC_VER_CHRGS_ADV_MSG_AUTH,'true','Yes','No')ELC_VER_CHRGS_ADV_MSG_AUTH,DECODE(ELC_TXN_FILE_MRKD_AOP,'true','Yes','No')ELC_TXN_FILE_MRKD_AOP,DECODE(ELC_ATC_SWIFT_ACK_CPY_ADV_CUST,'true','Yes','No')ELC_ATC_SWIFT_ACK_CPY_ADV_CUST,DECODE(ELC_ORIG_LC_ENDO_TRANS_AMT,'true','Yes','No')ELC_ORIG_LC_ENDO_TRANS_AMT, DECODE(LLI_CHK_OK,'Y','Yes','N', 'No', 'X', 'Not Applicable', 'O', 'Done outside WMS','')LLI_CHK_OK,BRANCH_CITY, ASSIGNED_CENTER, TRANSACTION_UNB_REFERENCE, TRANSACTION_ADCB_LC_REFERENCE, TRANSACTION_UNB_LC_REFERENCE ,PT_ASSIGNED_MAKER,PT_CUSTOMER_IC,PRO_TRADE_REF_NO,IS_REMOTE_PRESENTATION,PMNT_AUTH_OTH_SYS,DECODE(STANDALONE_LOAN,'1','Yes','2','No')STANDALONE_LOAN,DECODE(PROCESSING_SYSTEM,'T','TSLM','F','FCUBS')PROCESSING_SYSTEM,FINANCED_AMOUNT,OVERALL_OUTSTANDING_AMOUNT,FUNDING_REQUIRED,IF_PURCHASE_PRODUCT_CODE,ADDITIONAL_LOAN_AMOUNT,LOAN_STAGE,DECODE(TSLM_ANY_PAST_DUE_CID,'1','Yes','2','No')TSLM_ANY_PAST_DUE_CID,DECODE(FCUBS_ANY_PAST_DUE_CID,'1','Yes','2','No')FCUBS_ANY_PAST_DUE_CID,DECODE(TSLM_COMPANY_TYPE,'B','Buyer','S','Seller','BT','Both')TSLM_COMPANY_TYPE,COURIER_COMPANY,LOAN_AMOUNT,DECODE(TSLM_REFERRAL_FLAG,'1','Yes','2','No')TSLM_REFERRAL_FLAG,COURIER_AWB_NUMBER,DISCOUNT_ON_ACCEP,UTC_REQUIRED_BRMS,UTC_CONVERTED_AMOUNT,UTC_REQUIRED,UTC_JSTIFICATION_REQUIRED,CUSTOMER_REFERENCE,ASSET_ID,HYBRID_CUSTOMER,SUPPORTING_DOCS_REQ_PER_CREDIT_APPROVAL,LIABILITY_ID,PAST_DUE_LIABILITY,LEGALIZATION_CHARGES_DETAIL,EDAS_APPROVAL,EDAS_REFERENCE,LEGALIZATION_CHARGES,LOAN_EFFECTIVE_DATE,FX_CONTRACT_RATE,REASON_LOAN,TRANS_AMT,LOAN_PRCNPL_OS_AMT,FULLY_STL_TNX,ACCUM_INTEREST,IS_100PCT_CASHBACK,CASHMRG_ACCNO,LL_CODE,AMENDED_EFFECTIVE_DATE,IS_TS_REQUIRED,SKIP_TS_FLAG,TS_JUSTIFICATION  FROM "+fromCabinet+"EXT_TFO EXT WHERE WI_NAME=N'"+sWiName+"'"; //ATP-474 31-MAY-2024 --JAMSHED  AMENDED_EFFECTIVE_DATE
		System.out.println("App Summar  Query "+sQuery1);
		sInputXML = prepareAPSelectInputXml(sQuery1,sCabname, sSessionId);
		System.out.println("App Summar Ip XML "+sInputXML);

	
		System.out.println("initiated");
	
		
		System.out.println("done");
		if(!sInputXML.isEmpty())
			sOutputMasterXML =NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXML);
		System.out.println("sOutputXML11 For JSP  Application Summary "+sOutputMasterXML);
			
		String sOutputXMLChargesframe="";
		sQuery1 ="Select DECODE(LEGAL_CURR,'0','',LEGAL_CURR)LEGAL_CURR, LEGAL_AMT,DECODE(LEGAL_CHRGS_BORNE_BY,'0','',LEGAL_CHRGS_BORNE_BY)LEGAL_CHRGS_BORNE_BY, DECODE(PENALTY_CURR,'0','',PENALTY_CURR)PENALTY_CURR ,PENALTY_AMT,DECODE(PENALTY_CHRGS_BORNE_BY,'0','',PENALTY_CHRGS_BORNE_BY)PENALTY_CHRGS_BORNE_BY ,DECODE(OVRDRWN_CURR,'0','',OVRDRWN_CURR)OVRDRWN_CURR ,OVRDRWN_AMT, DECODE(OVRDRWN_CHRGS_BORNE_BY,'0','',OVRDRWN_CHRGS_BORNE_BY) OVRDRWN_CHRGS_BORNE_BY , DECODE(OTHER_CURR,'0','',OTHER_CURR)OTHER_CURR ,OTHER_AMT,DECODE(OTHER_CHRGS_BORNE_BY,'0','',OTHER_CHRGS_BORNE_BY)OTHER_CHRGS_BORNE_BY ,REMARKS from "+fromCabinet+"tfo_dj_charges_frame_dtls where WI_NAME = '"+sWiName+"'";
		sInputXML = prepareAPSelectInputXml(sQuery1,sCabname, sSessionId);
		System.out.println("App Summar Ip XML Charges Frame "+sInputXML);
		if(!sInputXML.isEmpty())
				sOutputXMLChargesframe =NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXML);
		System.out.println("output XML App SummryCharges "+sOutputXMLChargesframe);
		
		
		sReqCatCode = getTagValues(sOutputMasterXML, "REQ_CAT_CODE");
		sReqTpeCode = getTagValues(sOutputMasterXML, "REQ_TYPE_CODE");
		sLoanCur= getTagValues(sOutputMasterXML, "LOAN_CURR");
		sCompRef=getTagValues(sOutputMasterXML, "COMP_REF1");
		sTrnsCur=getTagValues(sOutputMasterXML, "TRNS_CUR");
		sloanGroup=getTagValues(sOutputMasterXML, "IFS_LOAN_GRP");
		sPrdType=getTagValues(sOutputMasterXML, "PRD_CODE");
		sDuplicateCheck=getTagValues(sOutputMasterXML, "IS_DEDUP");
		sSettlementCurr=getTagValues(sOutputMasterXML, "INF_SETTLEMENT_ACC_CURR");
		processing_sys=getTagValues(sOutputMasterXML, "PROCESSING_SYSTEM");
		
		ArrayList<ArrayList<String>> arrSigAccref=null;
		String sInputXmlSigAccRef="",sOutputMasterXMLSigAccRef="";
		String sQuery2="Select REFER_TO, EXCP_RMRKS from "+fromCabinet+"tfo_dj_ref_sig_acc where WI_NAME='"+sWiName+"'";
		sInputXmlSigAccRef=prepareAPSelectInputXml(sQuery2,sCabname, sSessionId);	
		if(!sInputXmlSigAccRef.isEmpty())
			sOutputMasterXMLSigAccRef =NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXmlSigAccRef);
		arrSigAccref = getTableData(sOutputMasterXMLSigAccRef,"REFER_TO","EXCP_RMRKS");
		
		System.out.println("arrSigAccref "+arrSigAccref);
		
		String sInputXmlProcessingMaker="",sOutputMasterXMLProcessingMaker="";
		sQuery2 = getQueryFormLoadPM(sReqCatCode,sReqTpeCode,sTrnsCur,sloanGroup,sPrdType);
		System.out.println("Processing maker Query"+sQuery2);
		sInputXmlProcessingMaker=prepareAPSelectInputXml(sQuery2,sCabname, sSessionId);
		if(!sInputXmlProcessingMaker.isEmpty())
			sOutputMasterXMLProcessingMaker = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXmlProcessingMaker);
		XMLParser parser = new XMLParser(sOutputMasterXMLProcessingMaker);
		
		System.out.println("Processing maker "+sOutputMasterXMLProcessingMaker);
		
		String sInputXmlProcessingChecker="",sOutputMasterXMLProcessingChecker="";
		sQuery2 = getQueryFormLoadPC(sReqCatCode,sReqTpeCode,sTrnsCur,sloanGroup,sPrdType);
		System.out.println("Processing maker Query"+sQuery2);
		sInputXmlProcessingChecker=prepareAPSelectInputXml(sQuery2,sCabname, sSessionId);
		if(!sInputXmlProcessingChecker.isEmpty())
			sOutputMasterXMLProcessingChecker = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXmlProcessingChecker);
		XMLParser parserChecker = new XMLParser(sOutputMasterXMLProcessingChecker);
		
		System.out.println("Processing checker "+sOutputMasterXMLProcessingChecker);
		
		ArrayList<ArrayList<String>> arrDocRvwRec=null;
		String sInputXmlDocRvwRec="",sOutputMasterXMLDocRvwRec="";
		sQuery2="Select DOC_RVW_GDLINES, RESPONSE from "+fromCabinet+"tfo_dj_doc_rvw_records where WI_NAME='"+sWiName+"'  ORDER BY INSERTIONORDERID";
		sInputXmlDocRvwRec=prepareAPSelectInputXml(sQuery2,sCabname, sSessionId);
		if(!sInputXmlDocRvwRec.isEmpty())
			sOutputMasterXMLDocRvwRec =NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXmlDocRvwRec);
		arrDocRvwRec = getTableData(sOutputMasterXMLDocRvwRec,"DOC_RVW_GDLINES","RESPONSE");

		
		ArrayList<ArrayList<String>> arrDocRvwRef=null;
		String sInputXmlDocRvwRef="",sOutputMasterXMLDocRvwRef="";
		sQuery2="Select REFER_TO, EXCP_RMRKS from "+fromCabinet+"tfo_dj_ref_doc_rvw where WI_NAME='"+sWiName+"'";
		sInputXmlDocRvwRef=prepareAPSelectInputXml(sQuery2,sCabname, sSessionId);
		if(!sInputXmlDocRvwRef.isEmpty())
			sOutputMasterXMLDocRvwRef =NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXmlDocRvwRef);
		arrDocRvwRef = getTableData(sOutputMasterXMLDocRvwRef,"REFER_TO","EXCP_RMRKS");

		ArrayList<ArrayList<String>> arrTxtVetRef=null;
		String sInputXmlTxtVetRef="",sOutputMasterXMLTxtVetRef="";
		sQuery2="Select REFER_TO, EXCP_RMRKS from "+fromCabinet+"tfo_dj_ref_txt_vet where WI_NAME='"+sWiName+"'";
		sInputXmlTxtVetRef=prepareAPSelectInputXml(sQuery2,sCabname, sSessionId);
		if(!sInputXmlTxtVetRef.isEmpty())
			sOutputMasterXMLTxtVetRef =NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXmlTxtVetRef);
		arrTxtVetRef = getTableData(sOutputMasterXMLTxtVetRef,"REFER_TO","EXCP_RMRKS");
	
			
		ArrayList<ArrayList<String>> arrLmtCrdtRec=null;	
		String sInputXmlLmtCrdtRec="",sOutputMasterXMLLmtCrdtRec="";
		sQuery2="Select LT_DOC_RVW_GDLINES, LT_RESPONSE from "+fromCabinet+"tfo_dj_lmt_crdt_records where WI_NAME='"+sWiName+"' ORDER BY INSERTIONORDERID";
		sInputXmlLmtCrdtRec=prepareAPSelectInputXml(sQuery2,sCabname, sSessionId);
		if(!sInputXmlLmtCrdtRec.isEmpty())
			sOutputMasterXMLLmtCrdtRec =NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXmlLmtCrdtRec);
		arrLmtCrdtRec = getTableData(sOutputMasterXMLLmtCrdtRec,"LT_DOC_RVW_GDLINES","LT_RESPONSE");
	
			
		ArrayList<ArrayList<String>> arrLmtCrdtRef=null;	
		String sInputXmlLmtCrdtRef="",sOutputMasterXMLLmtCrdtRef="";
		sQuery2="Select REFER_TO, EXCP_RMRKS from "+fromCabinet+"tfo_dj_ref_lmt_crdt where WI_NAME='"+sWiName+"'";
		sInputXmlLmtCrdtRef=prepareAPSelectInputXml(sQuery2,sCabname, sSessionId);
		if(!sInputXmlLmtCrdtRef.isEmpty())
			sOutputMasterXMLLmtCrdtRef =NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXmlLmtCrdtRef);
		arrLmtCrdtRef = getTableData(sOutputMasterXMLLmtCrdtRef,"REFER_TO","EXCP_RMRKS");

		
		ArrayList<ArrayList<String>> arrRefSummary=null;	
		String sInputXmlRefSummary="",sOutputMasterXMLRefSummary="";
		sQuery2="Select TAB_MODULE,REFFERD_TO,DECISION,EXCP_REMARKS,EXISTING_MAIL,NEW_MAIL from "+fromCabinet+"tfo_dj_final_dec_summary  where WI_NAME='"+sWiName+"'";
		sInputXmlRefSummary=prepareAPSelectInputXml(sQuery2,sCabname, sSessionId);
		if(!sInputXmlRefSummary.isEmpty())
			sOutputMasterXMLRefSummary =NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXmlRefSummary);
		arrRefSummary = getTableData(sOutputMasterXMLRefSummary,"TAB_MODULE","REFFERD_TO","DECISION","EXCP_REMARKS","EXISTING_MAIL","NEW_MAIL");
	
		ArrayList<ArrayList<String>> arrDiscrepanciesDetails=null;	
		String sInputXmlDisDetails="",sOutputMasterXMLDisDetails="";
		sQuery2="SELECT SNO,TS_DISCREPANCY_DTLS,WMS_DISCREPANCY_DTLS  FROM TFO_DJ_TS_DISCREPANCY_DETAILS  WHERE WI_NAME ='"+sWiName+"'";
		sInputXmlDisDetails=prepareAPSelectInputXml(sQuery2,sCabname, sSessionId);
		if(!sInputXmlDisDetails.isEmpty())
			sOutputMasterXMLDisDetails =NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXmlDisDetails);
		arrDiscrepanciesDetails = getTableData(sOutputMasterXMLDisDetails,"SNO","TS_DISCREPANCY_DTLS","WMS_DISCREPANCY_DTLS");	
			
		ArrayList<ArrayList<String>> arrDecHist=null;	
		String sInputXmlDecHist="",sOutputMasterXMLDecHist="";
		sQuery2="SELECT to_char(CREATE_DATE,'DD/MM/YYYY HH24:MI:SS') DT ,CURR_WS_NAME, USER_ID,USERNAME," +
					"REF_EMAIL_ID, ACTION,DECODE(DUP_CHECK,'--Select--','',DUP_CHECK) DUP_CHECK,DECODE(SEND_EMAIL_FLAG,'True','Yes','False','No','NA') SEND_EMAIL_FLAG,REASON_FOR_ACTION,REMARKS FROM "+fromCabinet+"TFO_DJ_DEC_HIST WHERE " +
					"WI_NAME ='"+sWiName+"' order by CREATE_DATE DESC";
		sInputXmlDecHist=prepareAPSelectInputXml(sQuery2,sCabname, sSessionId);
		if(!sInputXmlDecHist.isEmpty())
			sOutputMasterXMLDecHist =NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXmlDecHist);
		arrDecHist = getTableData(sOutputMasterXMLDecHist,"DT","CURR_WS_NAME","USER_ID","USERNAME","REF_EMAIL_ID","ACTION","DUP_CHECK","SEND_EMAIL_FLAG","REASON_FOR_ACTION","REMARKS");
		
		ArrayList<ArrayList<String>> arrCPDtls=null;
		String sInputXmlCPDtls="",sOutputMasterXMLCPDtls="";
		sQuery2="SELECT CP_NAME, CP_COUNTRY FROM "+fromCabinet+"TFO_DJ_CPD_DETAILS WHERE WI_NAME ='"+sWiName+"'";
		sInputXmlCPDtls=prepareAPSelectInputXml(sQuery2,sCabname, sSessionId);
		if(!sInputXmlCPDtls.isEmpty())
			sOutputMasterXMLCPDtls =NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXmlCPDtls);
		arrCPDtls=getTableData(sOutputMasterXMLCPDtls,"CP_NAME","CP_COUNTRY");
		
		
		ArrayList<ArrayList<String>> arrTSLMCPDtls=null;	//BY KISHAN TSLM
		String sInputXmlTSLMCPDtls="",sOutputMasterXMLTSLMCPDtls="";
		sQuery2="SELECT CHECKBOX,CID,PARTY_NAME,COUNTRY,NOA FROM "+fromCabinet+"TFO_DJ_TSLM_COUNTER_PARTY_DETAILS WHERE WINAME ='"+sWiName+"'";
		sInputXmlTSLMCPDtls=prepareAPSelectInputXml(sQuery2,sCabname, sSessionId);
		if(!sInputXmlTSLMCPDtls.isEmpty())
			sOutputMasterXMLTSLMCPDtls =NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXmlTSLMCPDtls);
		arrTSLMCPDtls=getTableData(sOutputMasterXMLTSLMCPDtls,"CHECKBOX","CID","PARTY_NAME","COUNTRY","NOA");
		
		ArrayList<ArrayList<String>> arrINVTSLMCPDtls=null;	//BY KISHAN TSLM
		String sInputXmlINVTSLMCPDtls="",sOutputMasterXMLINVTSLMCPDtls="";
		sQuery2="SELECT INVOICENO FROM "+fromCabinet+"TFO_DJ_TSLM_INVOICE_NO_STA_LOAN WHERE WINAME ='"+sWiName+"'";
		sInputXmlINVTSLMCPDtls=prepareAPSelectInputXml(sQuery2,sCabname, sSessionId);
		if(!sInputXmlINVTSLMCPDtls.isEmpty())
			sOutputMasterXMLINVTSLMCPDtls =NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXmlINVTSLMCPDtls);
		arrINVTSLMCPDtls=getTableData(sOutputMasterXMLINVTSLMCPDtls,"INVOICENO");
		
		ArrayList<ArrayList<String>> arrTSLMREFDtls=null;	//BY KISHAN TSLM
		String sInputXmlTSLMREFDtls="",sOutputMasterXMLTSLMREFDtls="";
		sQuery2="SELECT SEQNO,TRANSTYPE,TRANSID,COUNTERPARTYCID,REFCODE,REFDESC,USERCMNT FROM "+fromCabinet+"TFO_DJ_TSLM_REFERRAL_DETAILS WHERE WINAME ='"+sWiName+"'";
		sInputXmlTSLMREFDtls=prepareAPSelectInputXml(sQuery2,sCabname, sSessionId);
		if(!sInputXmlTSLMREFDtls.isEmpty())
			sOutputMasterXMLTSLMREFDtls =NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXmlTSLMREFDtls);
		arrTSLMREFDtls=getTableData(sOutputMasterXMLTSLMREFDtls,"SEQNO","TRANSTYPE","TRANSID","COUNTERPARTYCID","REFCODE","REFDESC","USERCMNT");
		
		ArrayList<ArrayList<String>> arrTSLONREFDtls=null;	//BY KISHAN TSLM
		String sInputXmlTSLONREFDtls="",sOutputMasterXMLTSLONREFDtls="";
		sQuery2="SELECT SNO,CID,LOANREFNO FROM "+fromCabinet+"TFO_DJ_TSLM_LOAN_REFERENCE_DETAILS WHERE WINAME ='"+sWiName+"'";
		sInputXmlTSLONREFDtls=prepareAPSelectInputXml(sQuery2,sCabname, sSessionId);
		if(!sInputXmlTSLONREFDtls.isEmpty())
			sOutputMasterXMLTSLONREFDtls =NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXmlTSLONREFDtls);
		arrTSLONREFDtls=getTableData(sOutputMasterXMLTSLONREFDtls,"SNO","CID","LOANREFNO");
		
		ArrayList<ArrayList<String>> arrDupDtls=null;
		String sInputXmlDupDtls="",sOutputMasterXMLDupDtls="";
		sQuery2="SELECT WI_NAME FROM "+fromCabinet+"TFO_DJ_DEDUP_DTLS WHERE NEW_WI='"+sWiName+"'";
		sInputXmlDupDtls=prepareAPSelectInputXml(sQuery2,sCabname, sSessionId);
		if(!sInputXmlDupDtls.isEmpty())
			sOutputMasterXMLDupDtls =NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXmlDupDtls);
		arrDupDtls=getTableData(sOutputMasterXMLDupDtls,"WI_NAME");
		
		
		ArrayList<ArrayList<String>> arrCounterPartydtls=null;	//BY KISHAN TSLM
		String sInputXmlCounterPartydtls="",sOutputMasterXMLCounterPartydtls="";
		sQuery2="SELECT COUNTERPARTY_CODE, COUNTERPARTY_NAME,INDUSTRY_CODE,INDUSTRY_DESCRIPTION, COUNTRY_CODE,PHONE,EMAIL  FROM "+fromCabinet+"tfo_dj_tslm_bio_details WHERE WI_NAME ='"+sWiName+"'";
		sInputXmlCounterPartydtls=prepareAPSelectInputXml(sQuery2,sCabname, sSessionId);
		if(!sInputXmlCounterPartydtls.isEmpty())
			sOutputMasterXMLCounterPartydtls =NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXmlCounterPartydtls);
		arrCounterPartydtls=getTableData(sOutputMasterXMLCounterPartydtls,"COUNTERPARTY_CODE","COUNTERPARTY_NAME","INDUSTRY_CODE","INDUSTRY_DESCRIPTION","COUNTRY_CODE","PHONE","EMAIL");
		
		ArrayList<ArrayList<String>> arrScreeningDetails=null;	//BY SHIVANSHU 
		String sInputXmlScreening="",sOutputMasterXMLScreening="";
		sQuery2="SELECT ACTION,REASON_FOR_ACTION,REMARKS FROM "+fromCabinet+"TFO_DJ_DEC_HIST WHERE " +
					"WI_NAME ='"+sWiName+"'  AND CURR_WS_NAME = 'SCC' order by CREATE_DATE DESC";
		sInputXmlScreening=prepareAPSelectInputXml(sQuery2,sCabname, sSessionId);
		if(!sInputXmlScreening.isEmpty())
			sOutputMasterXMLScreening =NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXmlScreening);
		arrScreeningDetails = getTableData(sOutputMasterXMLScreening,"ACTION","REASON_FOR_ACTION","REMARKS");	
		
	%>
			
<table border=1>
<tr><td class="APCellwhiteBoldHead">			
<b >Request Details</b>	
</td>
</tr>
<tr><td>		

				<Table  border=1 id="AccountSearch_ICBS_Table" class="innerTable">
				<tr>
				<td class="FormLabel">Initiator Branch</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "INITIATOR_BRANCH")%>
				</td>
				<td class="FormLabel">Initiator User ID</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "INITIATOR_USERID")%></td>
				<td class="FormLabel">Receipt Date Time</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "REQ_DATE_TIME")%></td>
								
				</tr>
				<tr>
				<td class="FormLabel">WMS ID</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "WI_NAME")%>
				</td>		
				<td class="FormLabel">Request Submitted by</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "REQUESTED_BY")%></td>
				<td class="FormLabel">Request Category</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "REQUEST_CATEGORY")%></td>					
				</tr>	
				<tr>
				<td class="FormLabel">Request Type</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "REQUEST_TYPE")%>
				</td>
				<td class="FormLabel">ADCB Contract Ref.</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "TRANSACTION_REFERENCE")%>
				</td>
				<td class="FormLabel">CID</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "CUSTOMER_ID")%>
				</td>
				</tr>
				<tr>				
				<td class="FormLabel">Currency</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "TRANSACTION_CURRENCY")%>
				</td>
				<td class="FormLabel">Amount</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "TRANSACTION_AMOUNT")%>
				</td>				
				<td class="FormLabel">Customer Name</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "CUSTOMER_NAME")%>
				</td>				
				</tr>
				<tr>
				<td class="FormLabel">Source Channel</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "SOURCE_CHANNEL")%>
				</td>
				<td class="FormLabel">Transaction Branch Code</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "RELATIONSHIP_TYPE")%>
				</td>							
				<td class="FormLabel">Transaction Delivery Branch</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "DELIVERY_BRANCH")%>
				</td>				
				</tr>
				<tr>
				<td class="FormLabel">Issuing Center</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "BRANCH_CITY")%>
				</td>
				<td class="FormLabel">Processing Center</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "ASSIGNED_CENTER")%>
				</td>
				<td class="FormLabel"></td>
				<td class="FormValue"></td>				
				</tr>
				<tr>
				<td class="FormLabel">Other/xUNB Ref</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "TRANSACTION_UNB_REFERENCE")%>
				</td>
				<td class="FormLabel">ADCB LC Ref</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "TRANSACTION_ADCB_LC_REFERENCE")%>
				</td>
				<td class="FormLabel">Other/xUNB LC Ref</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "TRANSACTION_UNB_LC_REFERENCE")%>
				</td>
				</tr>
				<tr>
				<td class="FormLabel">Assigned Maker</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "PT_ASSIGNED_MAKER")%>
				</td>
				<td class="FormLabel">Customer IC</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "PT_CUSTOMER_IC")%>
				</td>
				<td class="FormLabel">Pro Trade Ref No</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "PRO_TRADE_REF_NO")%>
				</td>
				</tr>
				<!-- ATP-387 12-FEB-24 Jamshed  -->
				<tr>
					<td class="FormLabel">Issuance against 100% Cash Backed</td>
					<td class="FormValue">
					<%=getTagValues(sOutputMasterXML, "IS_100PCT_CASHBACK")%>
					</td>
				
					<td class="FormLabel">Cash Margin Account Number</td>
					<td class="FormValue">
					<%=getTagValues(sOutputMasterXML, "CASHMRG_ACCNO")%>
					</td>
					
					<td class="FormLabel">Limit Line Number (Existing)</td>
					<td class="FormValue">
					<%=getTagValues(sOutputMasterXML, "LL_CODE")%>
					</td>
				</tr>
				<tr>
					<td class="FormLabel">Skip TS Flag</td>
					<td class="FormValue">
					<%=getTagValues(sOutputMasterXML, "SKIP_TS_FLAG")%>
					</td>
					
						
					<td class="FormLabel">TS Justification</td>
					<td class="FormValue">
					<%=getTagValues(sOutputMasterXML, "TS_JUSTIFICATION")%>
					</td>
					
					<td class="FormLabel">Is TS Required</td>
					<td class="FormValue">
					<%=getTagValues(sOutputMasterXML, "IS_TS_REQUIRED")%>
					</td>
				</tr>
				<!-- ATP-387 12-FEB-24 Jamshed ends -->
				<tr>
				<td class="FormLabel">Remote Presentation</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "IS_REMOTE_PRESENTATION")%>
				</td>
				<%if(processing_sys.equalsIgnoreCase("TSLM")){%>
				<td class="FormLabel">TSLM Workitem ID</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "TSLM_WORK_ITEM_ID")%>
				</td>
				<%}%>
				<td class="FormLabel">Processing System</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "PROCESSING_SYSTEM")%>
				</td>	
				</tr>
				<tr>
				<%if(processing_sys.equalsIgnoreCase("TSLM")){%>
				<td class="FormLabel">Standalone Loan</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "STANDALONE_LOAN")%>
				</td>
				<%}%>
				<td class="FormLabel">BRMS UTC check required?</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "UTC_REQUIRED_BRMS")%>
				</td>
				<td class="FormLabel">Converted Transaction Amount(AED)</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "UTC_CONVERTED_AMOUNT")%>
				</td>
				</tr>
				<tr>
				<td class="FormLabel">UTC Check Required?</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "UTC_REQUIRED")%>
				</td>
				<td class="FormLabel">Justification for No UTC check</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "UTC_JSTIFICATION_REQUIRED")%>
				</td>
				<%if(sReqCatCode.equalsIgnoreCase("IFA")|| sReqCatCode.equalsIgnoreCase("IFS")|| sReqCatCode.equalsIgnoreCase("IFP")){%>
				<td class="FormLabel">TSLM customer reference</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "CUSTOMER_REFERENCE")%>
				</td>
				</tr>
				<tr>
				<td class="FormLabel">TSLM front end Asset ID</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "ASSET_ID")%>
				</td>
				
				<td class="FormLabel">Hybrid customer flag</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "HYBRID_CUSTOMER")%>
				</td>
				<td class="FormLabel">Supporting docs required per Credit Approval</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "SUPPORTING_DOCS_REQ_PER_CREDIT_APPROVAL")%>
				</td>
				</tr>
				<tr>
				<td class="FormLabel">Loan Value date</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "LOAN_EFFECTIVE_DATE")%>
				</td>
				
				
				<td class="FormLabel">System fetched Exchange rate</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "FX_CONTRACT_RATE")%>
				</td>
				
				<td class="FormLabel">User input Exchange rate</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "UI_EXCHANGE_RATE")%>
				</td>
				</tr>
				<tr>
				<td class="FormLabel">Loan Amount</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "LOAN_AMOUNT")%>
				</td>
				
				<td class="FormLabel">Liability ID</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "LIABILITY_ID")%>
				</td> 
				<%}%>
				</tr>
				<tr>
				
				<%if(sReqCatCode.equalsIgnoreCase("IFP")){%>
				<td class="FormLabel">TSLM Legalization Charges details from customer</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "LEGALIZATION_CHARGES_DETAIL")%>
				</td>
				
				<td class="FormLabel">EDAS Approval status</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "EDAS_APPROVAL")%>
				</td>
				
				
				<td class="FormLabel">EDAS_Reference</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "EDAS_REFERENCE")%>
				</td>
				</tr>
				<tr>
				<td class="FormLabel">Legalization Charges as per EDAS</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "LEGALIZATION_CHARGES")%>
				</td>
				<%}%>
				</tr>
				</table><br>
	</td></tr>
	

				<tr><td class="APCellwhiteBoldHead">
				<b>Customer Details</b>
				</td></tr>
				<tr><td>

				<Table  border=1 id="AccountSearch_ICBS_Table2" class="innerTable">
				<tr>
				<td class="FormLabel">Customer Category</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "CUSTOMER_CATEGORY")%></td>
				<td class="FormLabel" left>Email ID</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "FCR_CUST_EMAIL_ID")%></td>
				<td class="FormLabel">Profit Centre</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "PROFIT_CENTER_CODE")%></td>				
				</tr>
				<tr>
				<td class="FormLabel">Address Line 1</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "ADDRESS_LINE_1")%>
				</td>		
				<td class="FormLabel">Address Line 2</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "ADDRESS_LINE_2")%></td>
				<td class="FormLabel">Mobile Number</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "MOBILE_NUMBER")%></td>					
				</tr>	
				<tr>
				<td class="FormLabel">PO Box</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "PO_BOX")%>
				</td>
				<td class="FormLabel">Fax Number</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "FAX_NO")%>
				</td>
				<td class="FormLabel">Emirates</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "EMIRATES")%>
				</td>
				</tr>	
				<tr>				
				<td class="FormLabel">RM Name</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "RM_NAME")%>
				</td>
				<td class="FormLabel">Mobile Number</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "RM_MOBILE_NUMBER")%>
				</td>				
				<td class="FormLabel">Is Customer VIP</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "IS_CUSTOMER_VIP")%></td>				
				</tr>
				<tr>
				<td class="FormLabel">RM email</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "RM_EMAIL")%>
				</td>
				<td class="FormLabel">Trade Customer Email ID</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "TRADE_CUST_EMAIL_ID")%>
				</td>
				<%if(processing_sys.equalsIgnoreCase("TSLM")){%>	
				<td class="FormLabel">Company Type</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "TSLM_COMPANY_TYPE")%>
				</td>
				<%}%>
				</tr>
				<!--ATP-463 19-JUN-2024 --JAMSHED START -->
				<tr>
					<td class="FormLabel">Any Past due available under the respective Liability ID of the Customer</td>
					<td class="FormValue">
					<%=getTagValues(sOutputMasterXML, "PAST_DUE_LIABILITY")%>
					</td>
				</tr>
				<!--ATP-463 19-JUN-2024 --JAMSHED END -->
				<%if(processing_sys.equalsIgnoreCase("TSLM")){%>	
				<tr>
				<td class="FormLabel">Any TSLM Past Due Available in CID?</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "TSLM_ANY_PAST_DUE_CID")%>
				</td>
				<td class="FormLabel">Any FCUBS Past Due Available in CID?</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "FCUBS_ANY_PAST_DUE_CID")%>
				</tr>
				<%}%>
				</table><br>
			</td></tr>	

	

<% if(sReqCatCode.equalsIgnoreCase("IFS") || sReqCatCode.equalsIgnoreCase("IFP") || sReqCatCode.equalsIgnoreCase("IFA")){%>
<tr><td class="APCellwhiteBoldHead">
<b>Counter Party Detail</b></td></tr>
	<tr><td>
				<Table border=1 id="TableCPDtls1" class="innerTable">
				<tr><td colspan="2">
				<table style="table-layout:fixed" border=0 align="Center" class="innerTable">
					<tr bgColor=#ffffff>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=620px><b>Counter Party Name</b></th>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=620px><b>Counter Party Country</b></th>
					</tr>
					<%
					for(ArrayList<String> arrInn : arrCPDtls){
						%>
					<TR>	
						<%
							for(String sData: arrInn){
								%>
								<TD class="FormValue" style="word-wrap:break-word;"><%=sData%></TD>
							<%	
							}
						%>
					</TR>	
					<%
						}				
					%>
				</table>
					</td></tr>
				</table><br>
			</td></tr>

<%if(processing_sys.equalsIgnoreCase("TSLM")){%>			
<tr><td class="APCellwhiteBoldHead">
<b>TSLM Counter Party Details</b></td></tr>
	<tr><td>

				<Table border=1 id="TableCPDtls1" class="innerTable">
				<tr><td colspan="2">
				<table style="table-layout:fixed" border=0 align="Center" class="innerTable">
					<tr bgColor=#ffffff>
						<td class ="checkbox" bgColor="#BA1B18" width=148px><center><input type = "checkbox" /></center></td>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=248px><b>Counter Party CID</b></th>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=248px><b>Counter Party Name</b></th>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=248px><b>Counter Party Country</b></th>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=346px><b>Notice of Acknowledgement</b></th>
					</tr>
					<%for(ArrayList<String> arrInn : arrTSLMCPDtls){%>
					<TR>	
						<%for(int i=0;i<arrInn.size();i++){%>
						<%if(i == 0){%>
						<%if(arrInn.get(i).equals("true")){%>
						<td class ="checkbox"  width=148px><center><input type = "checkbox" checked disabled = "disabled"/></center></td>
						<%}else{%>
						<td class ="checkbox"  width=148px><center><input type = "checkbox" unchecked disabled/></center></td>
						<%}%>
						<%}else {%>
						<TD class="FormValue" style="word-wrap:break-word;"><%=arrInn.get(i)%></TD>
						<%}%>
						<%}%>
					</TR>	
					<%}%>
				</table>
					</td></tr>
				</table><br>
			</td></tr>			

	<%}%>
<%}%>

<% if(sReqCatCode.equalsIgnoreCase("EC") || sReqCatCode.equalsIgnoreCase("IC") || sReqCatCode.equalsIgnoreCase("DBA") || sReqCatCode.equalsIgnoreCase("ELCB") || sReqCatCode.equalsIgnoreCase("ILCB")){%>


<tr><td class="APCellwhiteBoldHead">
<b>Input Field Details</b>
</td></tr>
<tr><td>
<tr><td>

				<Table  border=1 id="AccountSearch_ICBS_Table1" class="innerTable">
				<tr>
				<td class="FormLabel">Product Type</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "PRODUCT_TYPE")%></td>
				<td class="FormLabel">Loan Currency</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "INF_LOAN_CURR")%></td>
				<td class="FormLabel">Amendment Type</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "INF_AMEND_TYPE")%></td>
				</tr>
				<tr>
				<td class="FormLabel">New Maturity Date</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "INF_NEW_MATURITY_DATE")%></td>
				<td class="FormLabel">Tenor Days</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "INF_TENOR_DAYS")%></td>
				<td class="FormLabel">Tenor Base</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "INF_TENOR_BASE")%></td>				
				</tr>
				<tr>
				<td class="FormLabel">Base Document Date</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "INF_BASE_DOC_DATE")%></td>
				<td class="FormLabel">Tenor Base Details</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "INF_ACTUAL_TENOR_BASE")%></td>
				<td class="FormLabel">Maturity Date</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "INF_MATURITY_DATE")%></td>				
				</tr>
				<tr>
				<td class="FormLabel">Customer Holding Acc with us?</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "BILL_CUST_HLDING_ACC_US")%></td>
				<td class="FormLabel">Charge Account Number</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "INF_CHARGE_ACC_NUM")%></td>
				<td class="FormLabel">Charge Account Title</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "INF_CHARGE_ACC_TITLE")%></td>				
				</tr>
				<tr>
				<td class="FormLabel">Charge Account Currency</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "INF_CHARGE_ACC_CURR")%></td>
				<td class="FormLabel">Settlement Account Number</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "INF_SETTLEMENT_ACC_NUM")%></td>
				<td class="FormLabel">Settlement Account Title</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "INF_SETTLEMENT_ACC_TITLE")%></td>				
				</tr>
				<tr>
				<td class="FormLabel">Settlement Account Currency</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "INF_SETTLEMENT_ACC_CURR")%></td>
				<td class="FormLabel">Revised Document Available</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "BILL_RVSD_DOC_AVL")%></td>
				<td class="FormLabel">Remote Presentation</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "IS_REMOTE_PRESENTATION")%></td>				
				</tr>
				<tr>
				<td class="FormLabel">Courier Company</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "COURIER_COMPANY")%></td>
				<td class="FormLabel">Courier AWB Number</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "COURIER_AWB_NUMBER")%></td>
				<td class="FormLabel">Mode of Payment</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "BILL_MODE_OF_PMNT")%></td>				
				</tr>
				<tr>
				<td class="FormLabel">Our LC No.</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "BILL_OUR_LC_NO")%></td>
				<td class="FormLabel">Correspondent Bank</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "BILL_CORRSPNDNT_BNK")%></td>							
				</tr>							
				</table><br>
	</td></tr>

<br>
<%}%>
<% if(sReqCatCode.equalsIgnoreCase("ILC") || sReqCatCode.equalsIgnoreCase("ELC")){%>
	

<tr><td class="APCellwhiteBoldHead">
<b>Input Field Details</b>
</td></tr>
<tr><td>
				<Table  border=1 id="AccountSearch_ICBS_Table1" class="innerTable">
				<tr>
				<td class="FormLabel">Product Type</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "PRODUCT_TYPE")%></td>
				<td class="FormLabel">Transaction Tenor</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "trn_tenor")%></td>
				<td class="FormLabel">Expiry Date</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "EXP_DATE")%></td>
				</tr>
				<tr>
				<td class="FormLabel">Amendment Type</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "AMEND_TYPE")%></td>
				<td class="FormLabel">Customer Holding Acc with us?</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "IS_ACC_VALID")%></td>
				<td class="FormLabel">Third Party Transaction</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "TRN_THIRD_PARTY")%></td>				
				</tr>
				<tr>
				<td class="FormLabel">Charge Account Number</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "ACCOUNT_NUMBER")%></td>
				<td class="FormLabel">Charge Account Title</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "GRNT_CHRG_ACC_TITLE")%></td>
				<td class="FormLabel">Charge Account Currency</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "GRNT_CHRG_ACC_CRNCY")%></td>				
				</tr>
				<tr>
				<td class="FormLabel">New Transaction Amount</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "NEW_TRN_AMT")%></td>
				<td class="FormLabel">New Expiry Date</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "NEW_EXP_DATE")%></td>
				<td class="FormLabel">Any Document to be Couriered?</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "LC_DOC_COURIER")%></td>				
				</tr>
				<tr>
				<td class="FormLabel">Confirmation Amount</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "LC_CONF_AMNT")%></td>
				<td class="FormLabel">Correspondent Bank</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "LC_CORRSPNDNT_BNK")%></td>							
				</tr>
										
				</table><br>
			</td></tr>

<%}%>
<% if(sReqCatCode.equalsIgnoreCase("GRNT")){%>	
<tr><td class="APCellwhiteBoldHead">
<b>Input Field Details</b>
</td></tr>
<tr><td>
				<Table  border=1 id="AccountSearch_ICBS_Table1" class="innerTable">
				<tr>
				<td class="FormLabel">Product Type</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "PRODUCT_TYPE")%>
				</td>
				<td class="FormLabel">Transaction Tenor</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "TRN_TENOR")%></td>
				<td class="FormLabel">Expiry date</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "EXP_DATE")%></td>
								
				</tr>
				<tr>
				<td class="FormLabel">Valid Acc No</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "IS_ACC_VALID")%>
				</td>		
				<td class="FormLabel">Account Number</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "ACCOUNT_NUMBER")%></td>
				<td class="FormLabel">Amendment type</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "AMEND_TYPE")%></td>					
				</tr>	
				<tr>
				<td class="FormLabel">New Transaction Amount</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "NEW_TRN_AMT")%>
				</td>
				<td class="FormLabel">New Expiry Date</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "NEW_EXP_DATE")%>
				</td>
				<td class="FormLabel">Third party transaction</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "TRN_THIRD_PARTY")%>
				</td>
				</tr>				
				</table><br>
			</td></tr>

<%}%>
<% if(sReqCatCode.equalsIgnoreCase("IFS") || sReqCatCode.equalsIgnoreCase("IFP") ||sReqCatCode.equalsIgnoreCase("IFA")|| sReqCatCode.equalsIgnoreCase("TL")){%>
<tr><td class="APCellwhiteBoldHead">
<b>Input Field Details</b>
</td></tr>
<tr><td>
				<Table  border=1 id="AccountSearch_ICBS_Table1" class="innerTable">
				<tr>
				<td class="FormLabel">Product Code</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "PRODUCT_TYPE")%>
				</td>
				<td class="FormLabel">Loan Currency</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "INF_LOAN_CURR")%></td>
				<td class="FormLabel">Amendment Type</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "INF_AMEND_TYPE")%></td>								
				</tr>
				<tr>
				<td class="FormLabel" >New Maturity Date</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "INF_NEW_MATURITY_DATE")%>
				</td>		
				<td class="FormLabel" >Tenor Days</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "INF_TENOR_DAYS")%></td>
				<td class="FormLabel" >Tenor Base</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "INF_TENOR_BASE")%></td>					
				</tr>	
				<tr>
				<td class="FormLabel">Base Document Date</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "INF_BASE_DOC_DATE")%>
				</td>
				<td class="FormLabel">Tenor Base Details</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "INF_ACTUAL_TENOR_BASE")%>
				</td>
				<td class="FormLabel">Maturity Date</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "INF_MATURITY_DATE")%>
				</td>
				</tr>
				<tr>
				<td class="FormLabel">Customer Holding Acc with us?</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "BILL_CUST_HLDING_ACC_US")%></td>				
				<td class="FormLabel">Charge Account No.</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "INF_CHARGE_ACC_NUM")%>
				</td>
				<td class="FormLabel">Charge Account Title</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "INF_CHARGE_ACC_TITLE")%>
				</td>
				</tr>
				<tr>
				<td class="FormLabel">Charge Account Currency</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "INF_CHARGE_ACC_CURR")%>
				</td>
				<td class="FormLabel">Settlement Account No.</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "INF_SETTLEMENT_ACC_NUM")%>
				</td>
				<td class="FormLabel">Settlement Account Title</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "INF_SETTLEMENT_ACC_TITLE")%>
				</td>
				</tr>
				<tr>
				<td class="FormLabel">Settlement Account Currency</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "INF_SETTLEMENT_ACC_CURR")%>
				</td>
				<td class="FormLabel">Credit Account No.</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "INF_CREDIT_ACC_NUM")%>
				</td>
				<td class="FormLabel">Credit Account Title</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "INF_CREDIT_ACC_TITLE")%>
				</td>
				</tr>  
				<tr>
				<td class="FormLabel">Credit Account Currency</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "INF_CREDIT_ACC_CURR")%>
				</td>
				<td class="FormLabel">Remittance Currency</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "REMITTANCE_CURR")%>
				</td>
				<td class="FormLabel">Remittance Amount</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "REMITTANCE_AMT")%>
				</td>
				
				</tr>
				<!--ATP-474 31-MAY-2024 --JAMSHED START -->
				<%if(sReqTpeCode.equalsIgnoreCase("AM")){%>
				<tr>
				<td class="FormLabel">Amended Effective Date</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "AMENDED_EFFECTIVE_DATE")%></td>							
				</tr>
				<%}%>
				<!--ATP-474 31-MAY-2024 --JAMSHED END -->
				</table><br>
			</td></tr>

<%if(sReqTpeCode.equalsIgnoreCase("LD")){%>
<tr><td>
<b class="APCellwhiteBoldHead">Buyer's Calling</b>
</td></tr>
<tr><td>
				<Table  border=1 id="AccountSearch_ICBS_Table1" class="innerTable">
				<tr>
				<td class="FormLabel">Buyer Calling Done?</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "BC_CALL_DONE")%>
				</td>
				<td class="FormLabel">Remarks/Reason if buyer Calling Not Done</td>
				<td width=250px><%=getTagValues(sOutputMasterXML, "BC_REMAKS_NOT_DONE")%></td>
				<td class="FormLabel">Call Information</td>
				<td width=250px><%=getTagValues(sOutputMasterXML, "BC_CALL_INFO")%></td>
								
				</tr>
				<tr>
				<td class="FormLabel">Date of call</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "BC_DATE_OF_CALL")%>
				</td>		
				<td class="FormLabel">Reason of call</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "BC_RSN_OF_CALL")%></td>
				<td class="FormLabel">Name of the caller</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "BC_NAME_OF_CALLER")%></td>					
				</tr>	
				<tr>
				<td class="FormLabel">Time of call</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "BC_TIME_OF_CALL")%>
				</td>
				<td class="FormLabel">Buyer Name</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "BC_BUYER_NAME")%>
				</td>
				<td class="FormLabel">Contact person</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "BC_CONTACT_PERSON")%>
				</td>
				</tr>

				<tr>
				<td class="FormLabel">Buyer contact Number</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "BC_BUYER_CONTACT_NUM")%>
				</td>
				<td class="FormLabel">Invoice currency</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "BC_INVOICE_CURR")%>
				</td>
				<td class="FormLabel">Invoice Amount</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "BC_INVOICE_AMT")%>
				</td>
				</tr>	

				<tr>
				<td class="FormLabel">Invoice Number</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "BC_INVOICE_NUMBER")%>			
				</td>
				<td></td>
				<td>		
				</td>
				<td></td>
				<td>				
				</td>
				</tr>					
				</table><br>
			</td></tr>

<%}%>
<%}%>
 <%if(processing_sys.equalsIgnoreCase("TSLM")){%>			
<% if(sReqCatCode.equalsIgnoreCase("IFS") || sReqCatCode.equalsIgnoreCase("IFP") ||sReqCatCode.equalsIgnoreCase("IFA")|| sReqCatCode.equalsIgnoreCase("TL")){%>
<tr><td class="APCellwhiteBoldHead">
<b>FinTrade Settlement and Amendment Details</b>
</td></tr>
<tr><td>
				<Table  border=1 id="AccountSearch_ICBS_Table1" class="innerTable">
				<tr>
				<td class="FormLabel">O/s Principal Amount</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "LOAN_PRCNPL_OS_AMT")%></td>
				<td class="FormLabel">O/s Interest/Profit Amount</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "ACCUM_INTEREST")%></td>
				<td class="FormLabel">Fully Settle Transaction</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "FULLY_STL_TNX")%></td>								
				</tr>
				<tr>
				<%if(sReqTpeCode.equalsIgnoreCase("STL")){%>
				<td class="FormLabel" >Total Payment Amount</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "TRANS_AMT")%></td>	
				<%}%>
				<%if(sReqTpeCode.equalsIgnoreCase("AM")){%>
				<td class="FormLabel" >Reason to extend loan tenor</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "REASON_LOAN")%></td>	
				<%}%>
				</tr>
				</table><br>
			</td></tr>
<%}%>
<%}%>
<% if(sReqCatCode.equalsIgnoreCase("IFCPC")){%>
<tr><td class="APCellwhiteBoldHead">
<b>Counter Party Details</b>
</td></tr>
<tr><td>
		
				<table  style="table-layout:fixed" border=1 align="Center" class="innerTable">
					<tr bgColor=#ffffff>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=150px><b>Counter Party Code</b></th>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=150px><b>Counter Party Name</b></th>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=100px><b>Industy Code</b></th>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=150px><b>Industry Description</b></th>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=150px><b>Country Code</b></th>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=150px><b>Phone</b></th>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=150px><b>Email</b></th> 
					</tr>
					<%
					for(ArrayList<String> arrInn : arrCounterPartydtls){
						%>
					<TR>	
						<%
							for(String sData: arrInn){
								%>
								<TD class="FormValue" style="word-wrap:break-word;"><%=sData%></TD>
							<%	
							}
						%>
					</TR>	
					<%
						}				
					%>
				</table>
					<br>
			</td></tr>
<%}%>	
<% if(sReqCatCode.equalsIgnoreCase("IFCPC")){%>
<tr><td class="APCellwhiteBoldHead">
<b>Internal Stake Holder Confirmation on Counter Party Creation</b>
</td></tr>
<tr><td>
		<input type ="checkbox" >
		<label >I hereby CONFIRM that all necessary approvals have been obtained for this Counter Party Creation as per DLA and as Stipulated in the Approved Facility Terms and Conditions of the Customer. Refer attached the approvals.</label>
					
			</td></tr>
<%}%>
	
	
<%if(sReqTpeCode.equalsIgnoreCase("ILCB_BL")){%>
<tr><td class="APCellwhiteBoldHead">
<b>Charges Details</b>
</td></tr>
<tr><td>
				<Table  border=1 class="innerTable">
				<tr>
				<td class="FormLabel">Legalization Currency</td>
				<td class="FormValue">
				<%=getTagValues(sOutputXMLChargesframe, "LEGAL_CURR")%>
				</td>
				<td class="FormLabel">Legalization Amount</td>
				<td class="FormValue"><%=getTagValues(sOutputXMLChargesframe, "LEGAL_AMT")%></td>
				<td class="FormLabel">Legalization Charges Borne by</td>
				<td class="FormValue"><%=getTagValues(sOutputXMLChargesframe, "LEGAL_CHRGS_BORNE_BY")%></td>				
				</tr>
				
				<tr>
				<td class="FormLabel">Penalty Currency</td>
				<td class="FormValue">
				<%=getTagValues(sOutputXMLChargesframe, "PENALTY_CURR")%>
				</td>
				<td class="FormLabel">Penalty Amount</td>
				<td class="FormValue"><%=getTagValues(sOutputXMLChargesframe, "PENALTY_AMT")%></td>
				<td class="FormLabel">Penalty Charges Borne by</td>
				<td class="FormValue"><%=getTagValues(sOutputXMLChargesframe, "PENALTY_CHRGS_BORNE_BY")%></td>				
				</tr>
				
				<tr>
				<td class="FormLabel">Overdrawn Currency</td>
				<td class="FormValue">
				<%=getTagValues(sOutputXMLChargesframe, "OVRDRWN_CURR")%>
				</td>
				<td class="FormLabel">Overdrawn Amount</td>
				<td class="FormValue"><%=getTagValues(sOutputXMLChargesframe, "OVRDRWN_AMT")%></td>
				<td class="FormLabel">Overdrawn Charges Borne by</td>
				<td class="FormValue"><%=getTagValues(sOutputXMLChargesframe, "OVRDRWN_CHRGS_BORNE_BY")%></td>				
				</tr>
				
				<tr>
				<td class="FormLabel">Other charge Currency</td>
				<td class="FormValue">
				<%=getTagValues(sOutputXMLChargesframe, "OTHER_CURR")%>
				</td>
				<td class="FormLabel">Other charge Amount</td>
				<td class="FormValue"><%=getTagValues(sOutputXMLChargesframe, "OTHER_AMT")%></td>
				<td class="FormLabel">Other charge Borne by</td>
				<td class="FormValue"><%=getTagValues(sOutputXMLChargesframe, "OTHER_CHRGS_BORNE_BY")%></td>				
				</tr>
				
				
				<tr>
				<td class="FormLabel">Remarks</td>
				<td colspan="5" class="FormValue"><%=getTagValues(sOutputXMLChargesframe, "REMARKS")%></td>			
				</tr>					
				</table><br>
			</td></tr>

<%}%>

<% if(sReqCatCode.equalsIgnoreCase("IFS") || sReqCatCode.equalsIgnoreCase("IFP") || sReqCatCode.equalsIgnoreCase("IFA")){%>
<%if(sReqTpeCode.equalsIgnoreCase("LD")){%>
<%if(processing_sys.equalsIgnoreCase("TSLM")){%>	
<tr><td class="APCellwhiteBoldHead">
<b>TSLM Loan Input Details</b>
</td></tr>
<tr><td>

				<Table  border=1 id="AccountSearch_ICBS_Table2" class="innerTable">
				<tr>
				<td class="FormLabel">Financed Amount</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "FINANCED_AMOUNT")%></td>
				<td class="FormLabel" left>Overall Outstanding Amount</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "OVERALL_OUTSTANDING_AMOUNT")%></td>
				<td class="FormLabel">Loan Stage</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "LOAN_STAGE")%></td>				
				</tr>		
				<tr>
				<td class="FormLabel">IF Purchase Product Code</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "IF_PURCHASE_PRODUCT_CODE")%></td>
				<td class="FormLabel">Funding Required</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "FUNDING_REQUIRED")%></td>
				<td class="FormLabel">Additional Loan Amount</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "ADDITIONAL_LOAN_AMOUNT")%></td>
				</tr>
				<tr>
				<td class="FormLabel">Loan Amount</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "LOAN_AMOUNT")%></td>
				</tr>
				</table><br>
			</td></tr>
<%}%>			
<%}%>
<%}%>

<tr><td class="APCellwhiteBoldHead">
<b>Signature and A/C balance check</b>
</td></tr>

<tr><td>
		
				<Table border=1 id="AccountSearch_ICBS_Table2" class="innerTable">
				<tr>
				<td class="FormLabel">Request Signed as per mandate</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "REQ_SIGN_MAN_PPM")%>
				</td>
				<td class="FormLabel">Sufficient balance available</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "SUFF_BAL_AVL_PPM")%></td>
				<td class="FormLabel"></td>
				<td class="FormValue"></td>				
				</tr>
				</table><br>
			</td></tr>

<tr><td class="APCellwhiteBoldHead">
<b>Document Review</b>
</td></tr>
<tr><td>
		
				<Table  border=1 id="AccountSearch_ICBS_Table3" class="innerTable">
				<tr>
				<td class="FormLabel">Doc Review Successful</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "DOC_REV_SUCC_PPM")%>
				</td>
				<td  width=170px></td>
				<td  width=250px></td>
				<td  width=170px></td>
				<td  width=250px></td>				
				</tr>
				<tr>
				<td colspan="6"></td>
				</tr>
				<tr><td colspan="6">
				<table border=1 align="Left" style = "table-layout: fixed;" class="innerTable">
					<tr bgColor=#ffffff>
						<th class="InternalTableHeader" bgColor="#BA1B18" width=650px><b>Document Review Guidelines</b></th>
						<th class="InternalTableHeader" bgColor="#BA1B18" width=650px><b>Response</b></th>
					</tr>
					<%
					for(ArrayList<String> arrInn : arrDocRvwRec){
						%>
					<TR>	
						<%
							for(int i=0;i<arrInn.size();i++){
								String strRes="";
								if(!arrInn.get(i).isEmpty())
									strRes=arrInn.get(i);
								else{
									strRes=" ";
								}
								%>
								<TD class="FormValue"><%=strRes%></TD>
							<%	
							}
						%>
					</TR>	
					<%
						}	
					if(sReqCatCode.equalsIgnoreCase("ILCB") || sReqCatCode.equalsIgnoreCase("ELCB")){
					%>
					<TR>
						<td class="FormValue">Discrepancy Details</td>
						<td class="FormValue" style = "word-wrap: break-word"><%=getTagValues(sOutputMasterXML, "DISCREPANCY_DTLS")%></td>						
					</TR>
					<% } %>
				</table>
				</td></tr>
			</TD>	
		</TR>
	</table><br>
</td></tr>

<% if(sReqCatCode.equalsIgnoreCase("GRNT")){%>	
<tr><td class="APCellwhiteBoldHead">
<b>Text Vetting</b>
</td></tr>

<tr><td>
		
				<Table  border=1 id="AccountSearch_ICBS_Table4" class="innerTable">
				<tr>
				<td class="FormLabel">Text Format</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "TXT_FORMAT_PPM")%>
				</td>
				<td class="FormLabel">Text Approval Required</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "TXT_REQ_APP_PPM")%></td>
				<td class="FormLabel"></td>
				<td class="FormValue"></td>				
				</tr>			
				</table><br>
			</td></tr>

<%}%>
<tr><td class="APCellwhiteBoldHead">
<b>Any Approval Required for Counter Party Creation</b>
</td></tr>
<tr><td>
		
				<Table  border=1 id="AccountSearch_ICBS_Table5" class="innerTable">
				<tr>
				<td class="FormLabel">Approval Required for Counter Party Creation</td>
				<td class="FormValue">
				<%=getTagValues(sOutputMasterXML, "LMTCRE_APP_AVL_PPM")%>
				</td>
				<td width=170px></td>
				<td width=250px></td>
				<td width=170px></td>
				<td width=250px></td>				
				</tr>	
				<tr>
				<td colspan="6"></td>
				</tr>
				<tr><td colspan="6">
				<table border=0 class="APMainFrameTable" align="Center" class="innerTable">
					<tr bgColor=#ffffff>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=640px><b>Any Approval Required for Counter Party Creation Guidelines</b></th>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=640px><b>Response</b></th>
					</tr>
					<%
					for(ArrayList<String> arrInn : arrLmtCrdtRec){
						%>
					<TR>	
						<%
							for(String sData: arrInn){
								%>
								<TD class="FormValue"><%=sData%></TD>
							<%	
							}
						%>
					</TR>	
					<%
						}				
					%>
					</td></tr>
				</table>
			
				</table><br>
			</td></tr>

<tr><td class="APCellwhiteBoldHead">
<b>Compliance Screening</b>
</td></tr>
<tr><td>
		
				<Table  border=1 id="AccountSearch_ICBS_Table7" class="innerTable">
				<tr>
				<td class="FormLabel">Compliance Check Done (IBLS & DOWJONES)</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "COMP_CHK_DONE")%></td>
				<td class="FormLabel">Any Positive Matches ?  <br/>(IBLS or DOWJONES or LLI or IMB)</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "COMP_POSITIVE_MATCH")%></td>
				<td class="FormLabel">LLI Check to be Performed ?</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "LLI_CHK_OK")%></td>				
				</tr>
				<tr>
				<td class="FormLabel">IMB Check to be Performed?</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "COMP_IMB_CHK")%></td>
				<td class="FormValue" width=170px ></td>
				<td class="FormValue"></td>
				<td class="FormValue" width=170px></td>
				<td class="FormValue"></td>			
				</tr>
				</table><br>
			
</td></tr>

<% if(treasuryTabHandling(sReqCatCode, sReqTpeCode, sCompRef, sTrnsCur, sLoanCur, sSettlementCurr)){%>	

<tr><td class="APCellwhiteBoldHead">
<b>Treasury Rate</b>
</td></tr>
<tr><td>
		
				<Table  border=1 id="AccountSearch_ICBS_Table7" class="innerTable">
					<tr>
					<td class="FormLabel">Customer ID</td>
					<td class="FormValue">
					<%=getTagValues(sOutputMasterXML, "CUSTOMER_ID")%>
					</td>
					<td class="FormLabel">Customer Name</td>
					<td class="FormValue"><%=getTagValues(sOutputMasterXML, "CUSTOMER_NAME")%></td>
					<td class="FormLabel">Sell Currency</td>
					<td class="FormValue"><%=getTagValues(sOutputMasterXML, "TR_SELL_CUR")%></td>
					</tr>				
					<tr>
					<td class="FormLabel">Sell Amount</td>
					<td class="FormValue">
					<%=getTagValues(sOutputMasterXML, "TR_SELL_AMT")%>
					</td>
					<td class="FormLabel">Buy Currency</td>
					<td class="FormValue"><%=getTagValues(sOutputMasterXML, "TR_BUY_CUR")%></td>
					<td class="FormLabel">Buy Amount</td>
					<td class="FormValue"><%=getTagValues(sOutputMasterXML, "TR_LOAN_AMT")%></td>
					</tr>	
					
					<tr>
					<td class="FormLabel">Rate Requested</td>
					<td class="FormValue">
					<%=getTagValues(sOutputMasterXML, "TR_RATE_REQ")%>
					</td>
					<td class="FormLabel">Treasury Deal Number</td>
					<td class="FormValue"><%=getTagValues(sOutputMasterXML, "TR_TREASURY_DEAL_NUM")%></td>
					<td class="FormLabel">Fetched Exchange Rate</td>
					<td class="FormValue"><%=getTagValues(sOutputMasterXML, "TR_EXCHANGE_RATE")%></td>
					</tr>
					
					<tr>
					<td class="FormLabel">User Inputted Exchange Rate</td>
					<td class="FormValue"><%=getTagValues(sOutputMasterXML, "UI_EXCHANGE_RATE")%></td>
					<td class="FormLabel">Refer to Treasury</td>
					<td class="FormValue"><%=getTagValues(sOutputMasterXML, "TR_REFER_TREASURY")%></td>
					<td class="FormLabel">Exceptions</td>
					<td class="FormValue"><%=getTagValues(sOutputMasterXML, "TR_EXCEPTIONS")%></td>
					</tr>
					
				</table><br>
			</td></tr>
<%}%>

<tr><td class="APCellwhiteBoldHead">
<b>Referral Summary</b>
</td></tr>
<tr><td>
		
				<table  style="table-layout:fixed" border=1 align="Center" class="innerTable">
					<tr bgColor=#ffffff>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=100px><b>Module</b></th>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=100px><b>Referred To</b></th>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=100px><b>Decision</b></th>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=500px><b>Exception Remarks</b></th>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=220px><b>Existing Email</b></th>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=220px><b>Additional Email ID</b></th>
					</tr>
					<%
					for(ArrayList<String> arrInn : arrRefSummary){
						%>
					<TR>	
						<%
							for(String sData: arrInn){
								%>
								<TD class="FormValue" style="word-wrap:break-word;"><%=sData%></TD>
							<%	
							}
						%>
					</TR>	
					<%
						}				
					%>
				</table>
					<br>
			</td></tr>

<% if(sReqCatCode.equalsIgnoreCase("IFS") || sReqCatCode.equalsIgnoreCase("IFP") || sReqCatCode.equalsIgnoreCase("IFA")){%>
<%if(sReqTpeCode.equalsIgnoreCase("LD")){%>
<%if(processing_sys.equalsIgnoreCase("TSLM")){%>	
<tr><td class="APCellwhiteBoldHead">
<b>TSLM Referral Details</b>
</td></tr>
<tr><td>
				<Table  border=1 id="AccountSearch_ICBS_Table9" class="innerTable">
				<tr>
				<td class="FormLabel">TSLM Referral Flag</td>
				<td class="FormValue"><%=getTagValues(sOutputMasterXML, "TSLM_REFERRAL_FLAG")%></td>				
				</tr>
				</table><br>
				<tr><td>
		
				<table  style="table-layout:fixed" border=1 align="Center" class="innerTable">
					<tr bgColor=#ffffff>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=100px><b>Seq. No.</b></th>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=206px><b>TSLM Transaction Type</b></th>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=180px><b>TSLM Transaction ID</b></th>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=180px><b>TSLM Counter Party CID</b></th>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=206px><b>TSLM Referral Code</b></th>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=206px><b>TSLM Referral Description</b></th>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=206px><b>TSLM User Comment on Referral</b></th>
					</tr>
					<%
					for(ArrayList<String> arrInn : arrTSLMREFDtls){
						%>
					<TR>	
						<%
							for(String sData: arrInn){
								%>
								<TD class="FormValue" style="word-wrap:break-word;"><%=sData%></TD>
							<%	
							}
						%>
					</TR>	
					<%
						}				
					%>
				</table>
					<br>
			</td></tr>
<%}%>
<%}%>
<%if(processing_sys.equalsIgnoreCase("TSLM")){%>			
<tr><td class="APCellwhiteBoldHead">
<b>TSLM Loan Reference Details</b>
</td></tr>
<tr><td>
		
				<table  style="table-layout:fixed" border=1 align="Center" class="innerTable">
					<tr bgColor=#ffffff>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=100px><b>Sr No.</b></th>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=206px><b>Counter Party CID</b></th>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=180px><b>TSLM Loan Reference No.</b></th>
					</tr>
					<%
					for(ArrayList<String> arrInn : arrTSLONREFDtls){
						%>
					<TR>	
						<%
							for(String sData: arrInn){
								%>
								<TD class="FormValue" style="word-wrap:break-word;"><%=sData%></TD>
							<%	
							}
						%>
					</TR>	
					<%
						}				
					%>
				</table>
					<br>
			</td></tr>
<%}%>	
<%}%>		
					<%
						int start, end, deadend;
						start = parser.getStartIndex("RECORDS", 0, 0);
						end = 0;
						deadend = parser.getEndIndex("RECORDS", start, 0); 
						int count = parser.getNoOfFields("RECORD", start, deadend);
						    if (count > 0){
								
								
							%>
<tr><td class="APCellwhiteBoldHead">
<b>Processing Maker</b>
</td></tr>
<tr><td>
		
				<Table  border=1 id="AccountSearch_ICBS_Table7" class="innerTable">
					<%for (int counter = 0; counter < count; ++counter) {
								start = parser.getStartIndex("RECORD", end, 0);
								end = parser.getEndIndex("RECORD", start, 0);%>
					<tr>
								<td class="FormLabel" width=900px><%=parser.getValueOf("CONTROL_DESC", start, end)%></td>
								<td class="FormValue" width=100px><%=getTagValues(sOutputMasterXML, parser.getValueOf("EXT_CONTROL", start, end))%></td>
								</tr>
								<%
							}	
					%>

				</table>
					<br>
			</td></tr>

							<%}%>
							

							

							
<%
						int start1, end1, deadend1;
						start1 = parserChecker.getStartIndex("RECORDS", 0, 0);
						end1 = 0;
						deadend1 = parserChecker.getEndIndex("RECORDS", start1, 0); 
						int count1 = parserChecker.getNoOfFields("RECORD", start1, deadend1);
						    if (count1 > 0){
								
								
								%>
	<tr><td class="APCellwhiteBoldHead">
<b >Processing checker</b>
</td></tr>
<tr><td>
		
				<Table width=1280px border=2 class="innerTable">
					
					
					<%for (int counter = 0; counter < count1; ++counter) {
								start1 = parserChecker.getStartIndex("RECORD", end1, 0);
								end1 = parserChecker.getEndIndex("RECORD", start1, 0);
					%>
					<tr>
								<td class="FormLabel" width=900px><%=parserChecker.getValueOf("CONTROL_DESC", start1, end1)%></td>
								<td class="FormValue" width=100px><%=getTagValues(sOutputMasterXML, parserChecker.getValueOf("EXT_CONTROL", start1, end1))%></td>
								</tr>
								<%
							}	
					%>

				</table>
					<br>
			</td></tr>

							<%}%>

<tr><td class="APCellwhiteBoldHead">
<b>Screening Compliance Check</b>
</td></tr>
<tr><td>
		
				<table  style="table-layout:fixed" border=1 align="Center" class="innerTable">
					<tr bgColor=#ffffff>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=400px><b>Decision</b></th>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=400px><b>Reason</b></th>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=400px><b>Remarks</b></th>
					</tr>
					<%
					for(ArrayList<String> arrInn : arrScreeningDetails){
						%>
					<TR>	
						<%
							for(String sData: arrInn){
								%>
								<TD class="FormValue" style="word-wrap:break-word;"><%=sData%></TD>
							<%	
							}
						%>
					</TR>	
					<%
						}				
					%>
				</table>
					<br>
			</td></tr>							


<% if(sDuplicateCheck.equalsIgnoreCase("Y")){%>	
<tr><td class="APCellwhiteBoldHead">
<b>Possible Duplicate Transactions</b>
</td></tr>
<tr><td>
		
				<table width=300px style="table-layout:fixed" border=1 align="Center" class="innerTable">
					<tr bgColor=#ffffff>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=295px><b>Workitem Number</b></th>
					</tr>
					<%
					for(ArrayList<String> arrInn : arrDupDtls){
						%>
					<TR>	
						<%
							for(String sData: arrInn){
								%>
								<TD class="FormValue" style="word-wrap:break-word;"><%=sData%></TD>
							<%	
							}
						%>
					</TR>	
					<%
						}				
					%>
				</table>
					<br>
			</td></tr>

<%}%>
<tr><td class="APCellwhiteBoldHead">
<b>Decision Summary</b>
</td></tr>
<tr><td>
		
				<Table  border=1 id="AccountSearch_ICBS_Table8" class="innerTable">
					<table border=0 align="Center" class="innerTable">
					<tr >
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=150px><b>Date and Time</b></th>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=150px><b>Workstep</b></th>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=100px><b>UserID</b></th>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=150px><b>Username</b></th>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=150px><b>Referral response Email ID</b></th>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=150px><b>Action</b></th>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=150px><b>Invoice Duplicate check decision</b></th>     
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=150px><b>Duplicate check decision</b></th>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=150px><b>Send Email Decision</b></th>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=150px><b>Referral Tab Name</b></th>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=300px><b>Remarks or exceptions</b></th>
					</tr>
					<%
					for(ArrayList<String> arrInn : arrDecHist){
						%>
					<TR>	
						<%
							for(int i=0;i<arrInn.size();i++){
								String sData=" ";
								if(arrInn.get(i).isEmpty())
									sData = " ";
								else
									sData = arrInn.get(i);
							
								%>
								<TD class="FormValue"><%=sData%></TD>
							<%	
							}
						%>
					</TR>	
					<%
						}				
					%>
				</table>	
				</table><br>
			</td></tr>
			
<tr><td class="APCellwhiteBoldHead">
<b>Discrepancies  Details</b>
</td></tr>
<tr><td>
		
				<Table  border=1 id="AccountSearch_ICBS_Table8" class="innerTable">
					<table border=0 align="Center" class="innerTable">
					<tr >
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=150px><b>Seq No.</b></th>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=150px><b>TS Description Discrepancies</b></th>
						<th class="InternalTableHeader"  bgColor="#BA1B18" width=100px><b>Discrepancies</b></th>
					</tr>
					<%
					for(ArrayList<String> arrInn : arrDiscrepanciesDetails){
						%>
					<TR>	
						<%
							for(int i=0;i<arrInn.size();i++){
								String sData=" ";
								if(arrInn.get(i).isEmpty())
									sData = " ";
								else
									sData = arrInn.get(i);
							
								%>
								<TD class="FormValue"><%=sData%></TD>
							<%	
							}
						%>
					</TR>	
					<%
						}				
					%>
				</table>	
				</table><br>
			</td></tr>			
			
<% if(sReqCatCode.equalsIgnoreCase("IFCPC")){%>
<tr><table border=0  class="APCellwhiteBoldHead">
<th class="APCellwhiteBoldHead" ><b>Internal Stake Holder Confirmation on Counter Party Creation</b></th>
<br></table></tr>
<tr><td>
		<input type ="checkbox" >
		<label >I hereby CONFIRM that all necessary approvals have been obtained for this Counter Party Creation as per DLA and as Stipulated in the Approved Facility Terms and Conditions of the Customer. Refer attached the approvals.</label>
				<br>	
			</td></tr>
<%}%>
</table>
<Script>
function method1(){

      content.style.display = "block";

	

}
function printData(){
	
	window.print();
}

</Script>
<Center><button type="button" style="width: 110px; border-radius:20px; color : white; background-color:#BA1B18;"
								color="#BA1B18" onclick='printData();'><b>Print</b></button></Center>
			
		</div>
	</FORM>
	
	
	








</body>
</html>
