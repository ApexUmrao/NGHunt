package com.newgen.iforms.user.tfo;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.iforms.EControlOption;
import com.newgen.iforms.controls.EComboControl;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.user.config.Constants;
import com.newgen.iforms.user.config.TFOLogger;
import com.newgen.iforms.user.tfo.util.ConnectSocket;
import com.newgen.iforms.user.tfo.util.ConnectTemplateSocket;
import com.newgen.iforms.user.tfo.util.ExecuteXML;
import com.newgen.iforms.user.tfo.util.LoadConfiguration;
import com.newgen.iforms.user.tfo.util.ProtradeComplexMapping;
import com.newgen.iforms.user.tfo.util.XMLParser;
import com.newgen.omni.wf.util.excp.NGException;

import ISPack.CPISDocumentTxn;
import ISPack.ISUtil.JPDBRecoverDocData;
import ISPack.ISUtil.JPISException;
import ISPack.ISUtil.JPISIsIndex;

public class Common implements Constants {	
	public static final Logger log = TFOLogger.getInstance().getLogger();
	protected IFormReference formObject;
	String listSourceAck = "";
	String listReqTypeAck = "";
	String listReqSubAck = "";
	String issuingCenter="";
	String assignCenter = "";
	String strAmendment = "";
	String strAmendmentTnr = "";
	String strRefferTo = "";
	int swiftDecisionMaterCount = 0;
	boolean fetchLLIFlag = false;
	boolean generatePDFLLIFlag = false;
	String srcChnl = "";
	String relType = "";
	String strDecReasonLov = "";
	String requestType = "";
	String requestCategory = "";
	String requestSubmittedBy = "";
	String trnsCurrency = "";
	String sRemarksPC = "";	 
	String dList = "";
	String emptyStr = "";
	String sBaseDocDate = "";
	String sMatDate = "";
	String sBaseDocDate1 = "";
	String sMatDate1 = "";
	String WMS_CODE= "";
	String sNewInfDate1 = "";
	String sNewInfDate = "";
	int lvwSrNo;
	boolean illState = false;
	boolean emptyFlag = false;
	boolean emptyLimitFlag = false;
	static int dFinalDecisionFormLength = 0;
	static int dPCFinalDecisionFormLength = 0;
	protected String modeOfGurantee = null;
	Map<String, String> hmapAdditionalMail = new HashMap<>();
	List<List<String>> lstDec =new ArrayList<>();
	List<List<String>> listPrdCode = null; 
	List<List<String>> lstTabname = null;
	List<List<String>> listPrdCode1 = null;
	Map<String, String> referralMailMap = new HashMap<>();
	Map<String, String> mapBranch = null;
	Map<String, String> mapTab = null;
	Map<String, List<String>> accountListMap = null;
	private String decHist = "TFO_DJ_DEC_HIST";
	private String decHistCol = "WI_NAME,USER_ID,PRV_WS_NAME,CURR_WS_NAME,CREATE_DATE,USERNAME,USER_GROUPNAME,ACTION,REASON_FOR_ACTION,TRANSACTION_DATE_TIME,REMARKS,BRANCH,DUP_CHECK,ROUTE_TO,SEND_EMAIL_FLAG";
	List<List<String>> reqTypeMast = null;
	List<List<String>> reqSubByList = null;
	List<List<String>> srcChannelList = null;
	boolean reqCatClick = false;
	SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	SimpleDateFormat sdf1 = new SimpleDateFormat(DATETIME_FORMAT);
	Map<String, String> defaultMap = new HashMap<>();
	Map<String, String> yesNoMap = new HashMap<>();
	Map<String, String> amountCheckMap = new HashMap<>();
	Map<String, String> tabDescIdMap = new HashMap<>();
	Map<String, String> homeBranchMap = new HashMap<>();
	Map<String, String> currencyDecimalMap = new HashMap<>();
	Map<String, String> relatedPartyMap = new HashMap<>();
	Map<String, String> tempGenIPPortMap = new HashMap<>();
	Map<String, String> tabHandlingMap = new HashMap<>();
	Map<String, HashMap<String, String>> routeToMap = new HashMap<>();
	Map<String, HashMap<String, String>> systemActivityMap = new HashMap<>();
	Map<String, LinkedHashMap<String, String>> decisionMap = new HashMap<>();
	Map<String, HashMap<String, List<String>>> reasonMap = new HashMap<>();
	Map<String, LinkedHashMap<String,String>> branchCodeMap = new HashMap();
	Map<String, String> requestCategoryMap = new HashMap<>();
	Map<String, List<String>> brmsMap = new HashMap();
	Map<String, List<String>> trsdPartyMap = new HashMap();
	Map<String, List<String>> createAmendCoreMap = new HashMap();
	Map<String, List<String>> amendPartyMap = new HashMap();
	Map<String, String> protradeDateMap =new HashMap();
	Map<String, String> protradeCLOBMasterMap = new HashMap<String, String>();
	Map<String,Map<String,HashMap<String, String>>> protradeMappingMap = new HashMap<String,Map<String,HashMap<String, String>>>();
	boolean callsStatusFlag = true;
	int iProcessedCustomer = 0;
	String sProcessName = "";
	String sFieldName = "";
	String sUserName = "";
	String sGeneralData = "";
	String sActivityName = "";
	String sWorkitemID = "";
	String sUserIndex = "";
	String sMode = "";
	String sActivityID = "";
	protected int currentTabIndex;
	protected String currentTabSheetCaption;
	HashMap<String, String> cacheValueAtFromLoad;
	String isReadOnly = FALSE;
	String sProcessDefId = "";
	XMLParser xmlDataParser;
	Component _objApplet;
	String engineName;
	String sessionId;
	String senderId;
	String sEngineName = "";
	String sSessionId = "";
	String serverUrl = "";
	String sJTSIP = "";
	String sJTSPORT = "";
	String sJTSApp = "";
	String strItemIndex = "";
	String personName = "";
	private String tempGenSocketIp = "";
	private int tempGenSocketPort =0;
	private int iError = 0;
	boolean bCustomerDetalFetched = false;
	private String socketIP = "";
	private int socketPort = 0;
	ConnectSocket socket;
	ConnectTemplateSocket tempGenSocket;
	List<String> sendMessageList = new ArrayList<>();
	Map<String,String> pmRouteMap = new HashMap();	//CODE BY MOKSH
	Map<String, HashMap<String, String>> partyTypeMastMap = new HashMap<>();
	HashMap<String, ProtradeComplexMapping> protradeComplexMap = new HashMap<String, ProtradeComplexMapping>(); 
	HashMap<String, ArrayList<String>> TraydStreamValueMap = new HashMap<String, ArrayList<String> >();
	HashMap<String, String> bpmConfigValueMap = new HashMap<String, String>();  	//Traystream change |reyaz|atp-518|23-09-2024 
	ArrayList<String> TraydStreamValues = new  ArrayList<String>();
	HashMap<String, String> autoDocChkValueMap = new HashMap<String, String>();  	//Traystream change |reyaz|atp-518|23-09-2024 

    //DART 1132893
    //DART 1132888 AYUSH
	public Common(IFormReference formObject) {
		this.formObject = formObject;
		sActivityName = formObject.getActivityName();
		engineName = formObject.getObjGeneralData().getM_strEngineName();
		sProcessName = formObject.getProcessName();
		sessionId = formObject.getObjGeneralData().getM_strDMSSessionId();
		sUserName = formObject.getUserName();
		sFieldName = sProcessName + "_";
		serverUrl = formObject.getObjGeneralData().getM_strServletPath();
		sJTSIP = formObject.getObjGeneralData().getM_strJTSIP();
		sJTSPORT = formObject.getObjGeneralData().getM_strJTSPORT();
		sJTSApp = formObject.getObjGeneralData().getM_strAppServerType();
		strItemIndex = formObject.getObjGeneralData().getM_strFolderId();
		sWorkitemID = formObject.getObjGeneralData().getM_strProcessInstanceId();
		sUserIndex = formObject.getObjGeneralData().getM_strUserIndex();
		log.info("sUserIndex: "+sUserIndex);
		try {//DART 1129680
			defaultMap = LoadConfiguration.getInstance(formObject).getDefaultMap();
			yesNoMap=LoadConfiguration.getInstance(formObject).getYesNoMap();
			amountCheckMap=LoadConfiguration.getInstance(formObject).getAmountCheckMap();
			tabDescIdMap=LoadConfiguration.getInstance(formObject).getTabDescIdMap();
			currencyDecimalMap=LoadConfiguration.getInstance(formObject).getCurrencyDecimalMap();
			relatedPartyMap=LoadConfiguration.getInstance(formObject).getRelatedPartyMap();
			routeToMap=LoadConfiguration.getInstance(formObject).getRouteToMap();
			systemActivityMap=LoadConfiguration.getInstance(formObject).getSystemActivityMap();
			decisionMap=LoadConfiguration.getInstance(formObject).getDecisionMap();
			reasonMap=LoadConfiguration.getInstance(formObject).getReasonMap();
			requestCategoryMap=LoadConfiguration.getInstance(formObject).getRequestCategoryMap();
			tempGenIPPortMap=LoadConfiguration.getInstance(formObject).getTempGenIPPortMap();
			branchCodeMap=LoadConfiguration.getInstance(formObject).getBranchCodeMap();
			referralMailMap=LoadConfiguration.getInstance(formObject).getReferralMailMap();
			brmsMap=LoadConfiguration.getInstance(formObject).getBrmsMap();
			trsdPartyMap=LoadConfiguration.getInstance(formObject).getTrsdPartyMap();//TRSD_CHANGE
			tabHandlingMap=LoadConfiguration.getInstance(formObject).getTabHandlingMap();
			createAmendCoreMap=LoadConfiguration.getInstance(formObject).getCreateAmendCoreMap(); //TSLM CHANGE
			pmRouteMap=LoadConfiguration.getInstance(formObject).getPmRouteMap();	//TSLM CODE BY MOKSH
			partyTypeMastMap=LoadConfiguration.getInstance(formObject).getPartyTypeMastMap();	
			amendPartyMap=LoadConfiguration.getInstance(formObject).getAmendPartyMap();
			protradeMappingMap = LoadConfiguration.getInstance(formObject).getProtradeMappingMap();
			protradeDateMap = LoadConfiguration.getInstance(formObject).getProtradeDateMap();
			protradeCLOBMasterMap = LoadConfiguration.getInstance(formObject).getProtradeCLOBMasterMap();
			protradeComplexMap = LoadConfiguration.getInstance(formObject).getProtradeComplexMap();
			TraydStreamValueMap= LoadConfiguration.getInstance(formObject).getTraydStreamValueMap();  //Traystream change |reyaz|atp-518|23-09-2024
			bpmConfigValueMap= LoadConfiguration.getInstance(formObject).getloadBpmConfigCache();  //Traystream change |reyaz|atp-518|23-09-2024
			autoDocChkValueMap= LoadConfiguration.getInstance(formObject).getAutoDocChkCache();  //Traystream change |reyaz|atp-518|23-09-2024
			log.info("protradeCLOBMasterMap: "+protradeCLOBMasterMap);
			log.info("protradeComplexMap: "+protradeComplexMap);
			log.info("protradeDateMap: "+protradeDateMap);

			socketIP = defaultMap.get("SOCKETIP");
			log.info("SOCKETPORT: "+defaultMap.get("SOCKETPORT"));
			socketPort = Integer.parseInt(defaultMap.get("SOCKETPORT")); 
			socket = ConnectSocket.getReference(socketIP, socketPort);
			tempGenSocketIp = tempGenIPPortMap.get("IP");
			log.info("tempGenIPPortMap: "+tempGenIPPortMap);
			tempGenSocketPort = Integer.parseInt(tempGenIPPortMap.get("PORT")); 
			log.info("SOCKETPORT: "+tempGenSocketPort);
			tempGenSocket = ConnectTemplateSocket.getReference(tempGenSocketIp, tempGenSocketPort);
		} catch (Exception e) {
			log.error("exception in loading default map: "+e,e);
		}
	}

	public Common() {

	}
//DART 1129680 AYUSH
	public void setProperties() {
		try {
			log.info("inside setProperties >>>");
			setSenderID();
			String sQuery = "SELECT PERSONALNAME FROM PDBUSER WHERE UPPER(USERNAME) = '"+ sUserName.toUpperCase() + "'";
			List<List<String>> dbData = formObject.getDataFromDB(sQuery);
			if (!dbData.isEmpty()) {
				personName = ((String) ((List) dbData.get(0)).get(0));
			}
		} catch (Exception e) {
			log.error("exception in setProperties: " + e, e);
		}
	}

	@SuppressWarnings("unchecked")
	protected void setSenderID() {
		try {
			List<List<String>> tmpList = formObject.getDataFromDB("SELECT VALUE FROM USR_0_DEFAULTVALUE_FCR WHERE NAME ='TFO_SENDERID'");
			if (!tmpList.isEmpty()) {
				senderId = ((String) ((List) tmpList.get(0)).get(0));
			}
			log.info("SELECT VALUE FROM USR_0_DEFAULTVALUE_FCR WHERE NAME ='TFO_SENDERID'  \nSender ID " + senderId);
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}
//DART 1129680 AYUSH
	public void fetchAccountData(String cid) {
		setAccountDetails(fetchFCRCustomer(cid, "GetCustomerSummary"));
		loadAcountnumberLov();
	}

	public void loadComboWithCode(String query, String controlName){
		try {
			log.info("query="+query);
			List<List<String>> list= formObject.getDataFromDB(query);
			if (!list.isEmpty()) {
				for(int i=0;i<list.size();i++){
					log.info("data="+((String) ((List) list.get(i)).get(0))+", "+((String) ((List) list.get(i)).get(1)));
					formObject.addItemInCombo(controlName,((String) ((List) list.get(i)).get(0)), 
							((String) ((List) list.get(i)).get(1)));
				}
			}
		} catch (Exception e) {
			log.error("Exception in  loadComboWithCode: "+e,e);
		}
	}

	@SuppressWarnings("unchecked")
	public void referralLoadCombo() {
		try {
			decisionValidation(DEC_CODE);
			listPrdCode = formObject.getDataFromDB("SELECT REQ_CAT_KEY, REQ_TYPE_KEY, SRC_CHANNEL_KEY,TRNS_BRN_KEY, PRD_KEY, PRD_DESC FROM TFO_DJ_PRD_MAST");
			String strPrdQuery = "SELECT SRC_CHANNEL_KEY,TRNS_BRN_KEY,PRD_KEY,PRD_DESC FROM TFO_DJ_PRD_MAST PRD" +
					" WHERE REQ_CAT_KEY='" + formObject.getValue(REQUEST_CATEGORY).toString()+"'";
			log.info("strPrdQuery  >> " + strPrdQuery);
			listPrdCode1 = formObject.getDataFromDB(strPrdQuery);
			log.info("strPrdQuery"+listPrdCode1.size());
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}
//DART 1132890 AYUSH
	public void intializeStaticValue(){
		try {
			srcChnl = (String) formObject.getValue(SOURCE_CHANNEL);	
			relType = formObject.getValue(RELATIONSHIP_TYPE).toString();	
			requestType = formObject.getValue(REQUEST_TYPE).toString();
			requestCategory = formObject.getValue(REQUEST_CATEGORY).toString();
			trnsCurrency = formObject.getValue(TRANSACTION_CURRENCY).toString();
			requestSubmittedBy = formObject.getValue(REQUESTED_BY).toString();
			log.info("Sourcing Channel :" +srcChnl + " relType :" +relType+"requestCategory="+requestCategory);
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	protected void loadCombo(String query, String controlName) {
		try {
			log.info("query="+query);
			List<List<String>> list= formObject.getDataFromDB(query);
			if (list != null && !list.isEmpty()) {
				for(int i=0;i<list.size();i++){
					log.info("ControlName: "+controlName +": data="+list.get(i).get(0));
					formObject.addItemInCombo(controlName,list.get(i).get(0).toString());
				}
			}
		} catch (Exception e) {
			log.error("Exception in  loadCombo");
		}

	}

	@SuppressWarnings("unchecked")
	public void loadIntCodeDeconLoad() {
		try {			
			if (mapBranch == null) {
				mapBranch = loadLovIntoMap("SELECT CODE,HOME_BRANCH  FROM USR_0_HOME_BRANCH ORDER BY 1");
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		if("PP_MAKER".equalsIgnoreCase(sActivityName) || "PROCESSING MAKER".equalsIgnoreCase(sActivityName) || "PROCESSING CHECKER".equalsIgnoreCase(sActivityName)||"ReadOnly".equalsIgnoreCase(sActivityName) ||"Customer Referral Response".equalsIgnoreCase(sActivityName)){  //customer_ref_response_ppm_form
			listPrdCode = formObject.getDataFromDB("SELECT REQ_CAT_KEY, REQ_TYPE_KEY, SRC_CHANNEL_KEY,TRNS_BRN_KEY, PRD_KEY, PRD_DESC FROM TFO_DJ_PRD_MAST");
			String strPrdQuery = "SELECT SRC_CHANNEL_KEY,TRNS_BRN_KEY,PRD_KEY,PRD_DESC,REQ_CAT_KEY FROM TFO_DJ_PRD_MAST PRD " +
					"WHERE REQ_CAT_KEY='" + formObject.getValue(REQUEST_CATEGORY)+ "'";
			log.info("strPrdQuery  >> " + strPrdQuery);
			listPrdCode1 = formObject.getDataFromDB(strPrdQuery);
			if (strAmendment.isEmpty()) {
				strAmendment = setMasterValues(formObject.getDataFromDB("SELECT AM.DESCRIPTION||'###'||AM.WMS_CODE FROM TFO_DJ_AMENDMENT_MAST AM, EXT_TFO EXT WHERE AM.REQ_CAT_KEY=EXT.REQUEST_CATEGORY and EXT.WI_NAME='"+this.sWorkitemID+"'"));			
			}
			if (strAmendmentTnr.isEmpty()) {
				strAmendmentTnr = setMasterValues(formObject.getDataFromDB("SELECT AM.DESCRIPTION ||'###'|| TNR.WMS_CODE AMNDTNR FROM TFO_DJ_AMENDMENT_MAST AM, TFO_DJ_TRNS_TENOR_MAST TNR WHERE AM.TRNS_CODE=TNR.WMS_CODE"));
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> loadLovIntoMap(String query) {
		HashMap<String, String> map = null;
		try {
			List<List<String>> recordList = formObject.getDataFromDB(query);
			map = new HashMap<>();
			for (int counter = 0; counter < recordList.size(); counter++) {
				map.put(((String) ((List) recordList.get(counter)).get(0)),
						((String) ((List) recordList.get(counter)).get(1)));
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		return map;
	}

	public void loadSwiftDecisionCombo(String reqCategory, String reqType) {
		try {
			log.info("msg form value : "+ formObject.getValue(SWIFT_MESSAGE_TYPE).toString());
			String swift_dec_value = formObject.getValue(SWIFT_DEC_ON_SWIFT_MESSAGE).toString();
			log.info("swift_dec_value: "+swift_dec_value);
			String sQuery = "SELECT COUNT(1) FROM TFO_DJ_DEC_SWIFT_MESSAGE WHERE "
					+ " REQ_CAT_CODE = '"+ reqCategory + "' AND REQ_TYPE_CODE = '"+ reqType + "' "
					+ " AND MSG_TYPE = '"+ formObject.getValue(SWIFT_MESSAGE_TYPE) + "' ";
			@SuppressWarnings("unchecked")
			List<List<String>> record = formObject.getDataFromDB(sQuery);
			log.info("query for swift dec: "+sQuery);
			swiftDecisionMaterCount = Integer.parseInt(record.get(0).get(0));
			log.info("count dec list (swiftDecisionMaterCount): "+ swiftDecisionMaterCount);
			if (swiftDecisionMaterCount == 0) {
				formObject.clearCombo(SWIFT_DEC_ON_SWIFT_MESSAGE);
				formObject.addItemInCombo(SWIFT_DEC_ON_SWIFT_MESSAGE,"To be processed further", "TBPF");
				formObject.addItemInCombo(SWIFT_DEC_ON_SWIFT_MESSAGE,"Just for filling", "JFF");
			} else {
				log.info("loading combo for req cat: "+reqCategory+", req type: "+reqType);
				String sQuery2 = "SELECT DEC_DESC, DEC_CODE FROM TFO_DJ_DEC_SWIFT_MESSAGE WHERE " +

						" REQ_CAT_CODE = '"+reqCategory+"' AND REQ_TYPE_CODE = '"+reqType+"' " +
						" AND MSG_TYPE = '"+formObject.getValue(SWIFT_MESSAGE_TYPE).toString()+"' " +
						" ORDER BY SNO";
				log.info("query: "+sQuery2);
				formObject.clearCombo(SWIFT_DEC_ON_SWIFT_MESSAGE);
				loadComboWithCode(sQuery2, SWIFT_DEC_ON_SWIFT_MESSAGE);
			}
			if ("ELCB".equalsIgnoreCase(reqCategory)	&& "ELCB_AOR".equalsIgnoreCase(reqType)) {
				log.info("inside if "+ (formObject.getValue(SWIFT_MESSAGE_TYPE).toString()));
				formObject.clearCombo(SWIFT_DEC_ON_SWIFT_MESSAGE);
				formObject.addItemInCombo(SWIFT_DEC_ON_SWIFT_MESSAGE,"To be processed further", "TBPF");
				formObject.addItemInCombo(SWIFT_DEC_ON_SWIFT_MESSAGE,"Just for filling", "JFF");
				formObject.addItemInCombo(SWIFT_DEC_ON_SWIFT_MESSAGE,"To be advised to customer", "TBATC");
				formObject.setValue(SWIFT_DEC_ON_SWIFT_MESSAGE, "TBATC");
			}
			if(!swift_dec_value.isEmpty()){
				log.info("set value for SWIFT_DEC_ON_SWIFT_MESSAGE");
				formObject.setValue(SWIFT_DEC_ON_SWIFT_MESSAGE, swift_dec_value);
			}
		} catch (Exception e) {
			log.error("exception in loadSwiftDecisionCombo: " + e, e);
		}
	}

	@SuppressWarnings("unchecked")
	protected void tabHandlingMast(){
		try {
			mapTab = new HashMap<>();
			String sQuery = "Select A.TAB_NAME, B.IS_REQ, B.TAB_SEQ,B.TAB_NAME from "
					+ "TFO_DJ_TAB_MAPPING_MAST A,TFO_DJ_TAB_HANDLING_MAST B where A.TAB_KEY=B.TAB_NAME and "
					+ "B.REQUEST_CATEGORY='"+requestCategory+"' and B.activity_name='"+this.sActivityName+"'";
			log.info("Query "+sQuery);
			lstTabname = formObject.getDataFromDB(sQuery);
			log.info("Final List "+lstTabname);
			if(lstTabname !=null && !lstTabname.isEmpty()){
				for(List<String> lInner : lstTabname){
					log.info("Inner Row "+lInner);
					formObject.setTabStyle(MAIN_TAB, lInner.get(2), VISIBLE,lInner.get(1).equalsIgnoreCase("N")?FALSE:TRUE);
					mapTab.put(lInner.get(0), lInner.get(3));
				}
			}
			log.info("Map--------------------------  "+mapTab);
		} catch (NumberFormatException e) {
			log.error("Exception: ",e);
		}
	}
//DART 1132893 AYUSH
	public void setLoadRefTRVal(String event,String controlName){
		try {
			log.info("In setRefTRVal option value event "+event +" controlName " +controlName );
			if(handlingTreasuryTab()){
				if("PROCESSING MAKER".equalsIgnoreCase(sActivityName)){
					log.info("enabling treasury controls >>");
					enableControls(DISABLE_PM_IF_NON_LD);
					if(EVENT_TYPE_CLICK.equalsIgnoreCase(event) && TR_REFER_TREASURY.equalsIgnoreCase(controlName) && 
							formObject.getValue(TR_REFER_TREASURY).toString().equalsIgnoreCase("N")){
						formObject.setStyle(TR_EXCEPTIONS, DISABLE, TRUE);
						formObject.setValue(TR_EXCEPTIONS,"");
					}
					else if(EVENT_TYPE_CLICK.equalsIgnoreCase(event) && TR_REFER_TREASURY.equalsIgnoreCase(controlName) && 
							formObject.getValue(TR_REFER_TREASURY).toString().equalsIgnoreCase("Y")){
						formObject.setStyle(TR_EXCEPTIONS, DISABLE, FALSE);
						formObject.setValue(TR_EXCEPTIONS,"");
					}
					else if(EVENT_TYPE_CLICK.equalsIgnoreCase(event) && TR_SELL_CUR.equalsIgnoreCase(controlName)){
						setRateRequested();
					}
					else if(EVENT_TYPE_CLICK.equalsIgnoreCase(event) && TR_BUY_CUR.equalsIgnoreCase(controlName)){
						setRateRequested();
					}
				}
				if(EVENT_TYPE_LOAD.equalsIgnoreCase(event)){
					if("Y".equalsIgnoreCase(formObject.getValue(TR_REFER_TREASURY).toString())){
						//blank
					}
					else if("N".equalsIgnoreCase(formObject.getValue(TR_REFER_TREASURY).toString())){
						formObject.setValue(TR_EXCEPTIONS,"");
					}  					
				}  				
			} else{
				disableControls(DISABLE_PM_IF_NON_LD);
				clearControls(CLEAR_PM_IF_NON_LD);
			}
		} catch (Exception e) {
			log.error("excpetion in setLoadRefTRVal: "+e,e);
		}
	}

	protected void setLovSelect(){		
		if("IFS".equalsIgnoreCase(requestCategory) 
				|| "IFP".equalsIgnoreCase(requestCategory) 
				|| "TL".equalsIgnoreCase(requestCategory)
				|| "IFA".equalsIgnoreCase(requestCategory)
				|| "SCF".equalsIgnoreCase(requestCategory) ){ //Added by Shivanshu for SCF ATP-204
			setLovSelect(INF_CREDIT_ACC_NUM,INF_CHARGE_ACC_NUM,INF_SETTLEMENT_ACC_NUM);
		}
	}

	protected void setLovSelect(String ...controls){		
		for(String control:controls){
			try{
				log.info("isFieldsEnable "+control);
				if(!emptyAndAmountCheck(formObject.getValue(control).toString())){
					log.info("Setting LOV value in case of empty / 0 values ");
					formObject.clearCombo(control);
					formObject.setValue(control, "");						
				}
			} catch (Exception e) {
				log.error("Exception: ",e);
			}
		}		
	}

	protected boolean handlingTreasuryTab(){
		try {
			log.info("Request Category "+requestCategory + "   SReq Type "+requestType+"  TransactionCUrrency "+trnsCurrency);
			log.info(" Loan Currency  "+formObject.getValue(INF_LOAN_CURR).toString()+"  Pos Matches  "+formObject.getValue(COMP_POSITIVE_MATCH).toString());
			log.info("Settlement Currency "+formObject.getValue(INF_SETTLEMENT_ACC_CURR)+ "Charge Currency "+ formObject.getValue(INF_CHARGE_ACC_CURR).toString());  //ATP-153
			String loanCurr =formObject.getValue(INF_LOAN_CURR).toString();  //ATP-477 Shahbaz 31-05-2024
			if(("IFS".equalsIgnoreCase(requestCategory) 
					|| "IFP".equalsIgnoreCase(requestCategory)
					|| "IFA".equalsIgnoreCase(requestCategory)	//CODE BY MOKSH
					|| "SCF".equalsIgnoreCase(requestCategory) //ADDED FOR SCF ATP-205 Shivanshu
					|| "TL".equalsIgnoreCase(requestCategory)
					|| "IC".equalsIgnoreCase(requestCategory)
					|| "DBA".equalsIgnoreCase(requestCategory)
					|| "EC".equalsIgnoreCase(requestCategory)
					|| "ILC".equalsIgnoreCase(requestCategory)
					|| "ELC".equalsIgnoreCase(requestCategory)
					|| "ILCB".equalsIgnoreCase(requestCategory)
					|| "ELCB".equalsIgnoreCase(requestCategory))
					&& ("LD".equalsIgnoreCase(requestType)						
							||"PD".equalsIgnoreCase(requestType) //Added FOR SCF ATP-205 Shivanshu
							||"IC_BS".equalsIgnoreCase(requestType)
							||"DBA_STL".equalsIgnoreCase(requestType)
							||"EC_BS".equalsIgnoreCase(requestType)
							||"EC_DISC".equalsIgnoreCase(requestType)
							||"EC_CAP".equalsIgnoreCase(requestType)
							||"EC_LDDI".equalsIgnoreCase(requestType)
							||"ILCB_BS".equalsIgnoreCase(requestType)
							||"ELCB_BS".equalsIgnoreCase(requestType)
							||"ELCB_DISC".equalsIgnoreCase(requestType)
							||"ELCB_CLBP".equalsIgnoreCase(requestType)
							||"TL_LD".equalsIgnoreCase(requestType)
							||"STL".equalsIgnoreCase(requestType)       //Added for STL JIRA-153
							||"TL_STL".equalsIgnoreCase(requestType)       //Added for STL JIRA-153
							||"IFA_CTP".equalsIgnoreCase(requestType) //EDITED BY MOKSH
							||"AM".equalsIgnoreCase(requestType)	  //ATP-491 REYAZ 24-07-2024 
							||"PDD".equalsIgnoreCase(requestType)) 
					&& !(("IC".equalsIgnoreCase(requestCategory)||"DBA".equalsIgnoreCase(requestCategory)||"ILCB".equalsIgnoreCase(requestCategory)
						||"EC".equalsIgnoreCase(requestCategory)||"ELCB".equalsIgnoreCase(requestCategory)) 
									&& trnsCurrency.equalsIgnoreCase(formObject.getValue(INF_SETTLEMENT_ACC_CURR).toString()))
					&&	!("Y".equalsIgnoreCase(formObject.getValue(COMP_POSITIVE_MATCH).toString()))
				    && !("STL".equalsIgnoreCase(requestType)  &&
							(loanCurr.equalsIgnoreCase(formObject.getValue(INF_CHARGE_ACC_CURR).toString())    //ATP-477 Shahbaz 31-05-2024
							|| loanCurr.equalsIgnoreCase(formObject.getValue(INF_SETTLEMENT_ACC_CURR).toString())))
				    && !(("IFA".equalsIgnoreCase(requestCategory)||"IFS".equalsIgnoreCase(requestCategory)||"IFP".equalsIgnoreCase(requestCategory)
							||"TL".equalsIgnoreCase(requestCategory)) 
					 && trnsCurrency.equalsIgnoreCase(loanCurr) && !"STL".equalsIgnoreCase(requestType))) { //ATP-492 REYAZ 25-07-2024
				log.info("returning true from handlingTreasuryTab");
				return true;
			}
			else {
				log.info("returning false from handlingTreasuryTab");
				return false;
			}
		} catch (Exception e) {
			log.error("exception in handlingTreasuryTab: "+e,e);
			return false;
		}
	}


	@SuppressWarnings("unchecked")
	protected void loadDecReferalGrid(){
		log.info("ActivityName "+this.sActivityName+ "   Workitem ID"+this.sWorkitemID);
		log.info("");
		String sReferTo="";
		String sDate = "";
		String sEntry_Date=""; 
		StringBuilder sFinalString = new StringBuilder();
		List lst;
		List lst1;
		if("RM".equalsIgnoreCase(this.sActivityName)){
			sReferTo = "='RM'";
			sEntry_Date = "RM_ENTRY_DATE";
		}
		else if("LEGAL_TEAM".equalsIgnoreCase(this.sActivityName)){
			sReferTo = "='Legal'";
			sEntry_Date = "LEGAL_ENTRY_DATE";
		}
		else if("TSD".equalsIgnoreCase(this.sActivityName)){
			sReferTo = "='TSD'";
			sEntry_Date = "TSD_ENTRY_DATE";
		}
		else if("CUSTOMER_REVIEW".equalsIgnoreCase(this.sActivityName) ||
				"Customer Referral Response".equalsIgnoreCase(this.sActivityName)){
			sReferTo = "LIKE '%Customer%'";
			sEntry_Date = "CR_ENTRY_DATE";
		}
		else if("CORRESPONDENT_BANK".equalsIgnoreCase(this.sActivityName)){
			sReferTo = "='Correspondent Bank'";
			sEntry_Date = "CB_ENTRY_DATE";
		}
		else if("Treasury".equalsIgnoreCase(this.sActivityName)){
			sReferTo = "='Treasury'";
			sEntry_Date = "TR_ENTRY_DATE";
		}
		else if("TB Sales".equalsIgnoreCase(this.sActivityName)){
			sReferTo = "='Trade Sales'";
			sEntry_Date = "TS_ENTRY_DATE";
		}
		else if("COMPLIANCE".equalsIgnoreCase(this.sActivityName)){
			sReferTo = "='COMPLIANCE'";
			sEntry_Date = "COMP_ENTRY_DATE";
		}
		String processType=formObject.getValue(PROCESS_TYPE).toString();
		String sQuery = "";
		log.info("loadDecReferalGrid: " + processType);
		if(processType.equalsIgnoreCase("TSLM Front End") || processType.equalsIgnoreCase("PT") || processType.equalsIgnoreCase("ET")){ //ATP-379 REYAZ 01-05-2024  - //ATP-469 Shahbaz 23-05-2024
			sQuery = "SELECT REFERRALTYPE, REFCODE, REFDESC FROM TFO_DJ_TSLM_DOCUMENT_REVIEW WHERE WI_NAME='" + this.sWorkitemID + "' and FLAG_DEL='N' AND REFCODE "+sReferTo+""
					+ " UNION SELECT REFERRALTYPE, REFCODE, REFDESC FROM TFO_DJ_TSLM_REFERRAL_DETAIL WHERE WI_NAME='" + this.sWorkitemID + "'and FLAG_DEL='N' AND REFCODE "+sReferTo+""
					+ " UNION SELECT REFERRALTYPE, REFCODE, REFDESC FROM TFO_DJ_TSLM_LIMIT_CREDIT_REVIEW WHERE WI_NAME='" + this.sWorkitemID + "' and FLAG_DEL='N' AND REFCODE "+sReferTo+""
					+ " UNION Select TAB_NAME as REFERRALTYPE ,REFER_TO as REFCODE, EXCP_RMRKS as REFDESC from tfo_dj_ref_txt_vet where wi_name ='"+this.sWorkitemID+"'  and FLAG_DEL='N' and refer_to "+sReferTo+""
					+ " UNION SELECT TAB_MODULE as REFERRALTYPE ,REFFERD_TO as REFCODE, EXCP_REMARKS as REFDESC FROM TFO_DJ_FINAL_DEC_SUMMARY WHERE WI_NAME ='"+this.sWorkitemID+"' AND UPPER(REFFERD_TO) IN ('COMPLIANCE','TREASURY')";
			
		}else{
		 sQuery="Select TAB_NAME,REFER_TO,EXCP_RMRKS FROM TFO_DJ_REF_SIG_ACC where wi_name ='"+this.sWorkitemID+"' and FLAG_DEL='N' and refer_to "+sReferTo+" UNION "+
					"Select TAB_NAME,REFER_TO,EXCP_RMRKS from tfo_dj_ref_doc_rvw where wi_name ='"+this.sWorkitemID+"'  and FLAG_DEL='N' and refer_to "+sReferTo+" UNION "+
					"Select TAB_NAME,REFER_TO,EXCP_RMRKS from tfo_dj_ref_txt_vet where wi_name ='"+this.sWorkitemID+"'  and FLAG_DEL='N' and refer_to "+sReferTo+" UNION "+
					"Select TAB_NAME,REFER_TO,EXCP_RMRKS from tfo_dj_ref_lmt_crdt where wi_name ='"+this.sWorkitemID+"'  and FLAG_DEL='N' and refer_to "+sReferTo+""
					+ "UNION SELECT TAB_MODULE, REFFERD_TO, EXCP_REMARKS FROM TFO_DJ_FINAL_DEC_SUMMARY WHERE WI_NAME ='"+this.sWorkitemID+"' AND UPPER(REFFERD_TO) IN ('COMPLIANCE','TREASURY')";
		}
		lst = formObject.getDataFromDB(sQuery);
		log.info("PrevDate  "+sDate  + " query "+sQuery );
		log.info("Final String "+sFinalString+"   List "+lst);
		String sQuery1 = "Select "+sEntry_Date+" from EXT_TFO where WI_NAME ='"+this.sWorkitemID+"'";
		lst1 = formObject.getDataFromDB(sQuery1);
		log.info("List "+lst1);
		if(!lst1.isEmpty()){
			sDate = ((String) ((List)lst1.get(0)).get(0));
		}
		log.info("date  "+sDate);
		formObject.clearTable("ListViewReferral");	
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < lst.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Module Name",
					((String) ((List) lst.get(i)).get(0)));
			jsonObject.put("Refer To",
					((String) ((List) lst.get(i)).get(1)));
			jsonObject.put("Referred On", sDate);
			jsonObject.put("Exception Remarks",
					((String) ((List) lst.get(i)).get(2)));
			//jsonObject.put("Email Remarks",((String) ((List) lst.get(i)).get(2)));
			jsonArray.add(jsonObject);
		}
		formObject.addDataToGrid(LISTVIEW_REFERRAL_SUMMARY, jsonArray);
	}

	protected String getListValFromCode(List<List<String>> sDeccList, String sDecisionCode){
		try {
			String sVal="";
			log.info("Inner List "+sDeccList);
			log.info("Decision Code "+sDecisionCode);
			for(List<String> innerList : sDeccList){
				if(innerList.get(0).equalsIgnoreCase(sDecisionCode)){
					sVal = innerList.get(1);
					break;
				}
			}
			return sVal;
		} catch (Exception e) {
			log.error("exception : "+e,e);
			return "";
		}
	}

	@SuppressWarnings("unchecked")
	protected Boolean setUserDetail() {
		String str = "SELECT USERNAME,BRANCH FROM WFFILTERTABLE WHERE UPPER(USERNAME)='"
				+ sUserName.toUpperCase()
				+ "' and processname='"
				+ sProcessName + "'";
		log.info("User Detail Query: " + str);
		try {
			List<List<String>> tmpList = formObject
					.getDataFromDB("SELECT USERNAME,BRANCH FROM WFFILTERTABLE WHERE UPPER(USERNAME)='"
							+ sUserName.toUpperCase() + "' and processname='" + sProcessName + "'");
			if (tmpList != null && !tmpList.isEmpty()) {
				if (!tmpList.get(0).isEmpty() && (!tmpList.get(0).get(1).isEmpty())) {
					log.info("user branch detail " + tmpList.get(0).get(1));
					if ("Initiator".equalsIgnoreCase(sActivityName)	&& (!tmpList.get(0).get(1).isEmpty())) {
						formObject.setValue(INITIATOR_BRANCH, ((String) ((List) tmpList.get(0)).get(1)));
						formObject.setValue("INITIATOR_USERID",	((String) ((List) tmpList.get(0)).get(0)));
						String processingBranch = formObject.getValue(TFO_BRANCH_CITY).toString();
						String assignedCenter = formObject.getValue(TFO_ASSIGNED_CENTER).toString();
						log.info("user branch detail processingBranch: "+ ((String) ((List) tmpList.get(0)).get(1)));
						if (null != processingBranch && !processingBranch.equalsIgnoreCase("")) {
						} else {
							str = "SELECT PROCESSING_BRANCH_CODE FROM TFO_DJ_INIT_PROC_BRANCH_MAST WHERE UPPER(INITIATOR_BRANCH_NAME)='"
									+ ((String) ((List) tmpList.get(0)).get(1))
									.toUpperCase() + "'";
							log.info("user branch detail processingBranch: "
									+ str);
							tmpList = formObject.getDataFromDB(str);
							if (!tmpList.isEmpty() && tmpList.get(0).isEmpty() && !tmpList.get(0).get(0).isEmpty()) {
								log.info("user branch detail processingBranch code: "+ ((String) ((List) tmpList.get(0)).get(0)));
								formObject.setValue(TFO_BRANCH_CITY, ((String) ((List) tmpList.get(0)).get(0)));
								issuingCenter =  tmpList.get(0).get(0);
							} else {
								log.info("user branch detail processingBranch code being defaulted to DXB: ");
								formObject.setValue(TFO_BRANCH_CITY, "DXB");
								issuingCenter =  "DXB";
							}
						}
						log.info("branch city: "+formObject.getValue(TFO_BRANCH_CITY).toString());
						log.info("assigned center: "+formObject.getValue(TFO_ASSIGNED_CENTER).toString());
						if (null != assignedCenter && !assignedCenter.equalsIgnoreCase("")) {
							log.info("assigned center is not blank");
							assignCenter = assignedCenter;
						} else {
							log.info("setting assigned center");
							formObject.setValue(TFO_ASSIGNED_CENTER, formObject.getValue(TFO_BRANCH_CITY).toString());
							assignCenter = formObject.getValue(TFO_BRANCH_CITY).toString();
						}
						log.info("assigned center after being set: "+formObject.getValue(TFO_ASSIGNED_CENTER).toString());
						return true;
					}
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			log.error("exception in setting user details: " + e, e);
		}
		return false;
	}

	public void setOnIntroLoad(String reqType) { 
		log.info("setOnIntroLoad..req type: " + reqType);
		if ("NI".equalsIgnoreCase(reqType) 
				|| "GA".equalsIgnoreCase(reqType)
			    ||"SBLC_NI".equalsIgnoreCase(reqType)
			    ||"ELC_SLCA".equalsIgnoreCase(reqType)) {
			enableControls("CID,BUTTON_FETCH");
			log.info("applying control sets for NI or GA request type");
			formObject.applyGroup(CONTROL_SET_REQ_NI_DISABLE);
			formObject.applyGroup(CONTROL_SET_REQ_NI_ENABLE);
			formObject.setValue(TRANSACTION_REFERENCE, "");
		} else if ("GAA".equalsIgnoreCase(reqType)
				|| "AM".equalsIgnoreCase(reqType)
				|| "CC".equalsIgnoreCase(reqType)
				|| "CL".equalsIgnoreCase(reqType)
				|| "ER".equalsIgnoreCase(reqType)
				|| "EPC".equalsIgnoreCase(reqType)
				||"SBLC_AM".equalsIgnoreCase(reqType)
				||"SBLC_CR".equalsIgnoreCase(reqType)
				||"SBLC_CS".equalsIgnoreCase(reqType)
				||"SBLC_ER".equalsIgnoreCase(reqType)
				||"ELC_SLCAA".equalsIgnoreCase(reqType)
				||"ELC_SER".equalsIgnoreCase(reqType)
				||"ELC_SCL".equalsIgnoreCase(reqType)
				) {
			disableControls(CID);
			formObject.setValue(CID, "");
			formObject.applyGroup(CONTROL_SET_REQ_TYPE_GAA);
		} else {
			log.info("disabling controls for no request type");
			disableControls("CID,BUTTON_FETCH");
			formObject.applyGroup(CONTROL_SET_REQ_TYPE_GAA);
		}
		log.info("Setting TFO_CHKBX_SEND_MAIL  flag value "
				+ defaultMap.get("TFO_EMAIL_FLG"));
		if (TRUE.equalsIgnoreCase(defaultMap.get("TFO_EMAIL_FLG"))) {
			formObject.setValue(CHKBX_SEND_MAIL, TRUE);
			formObject.setValue(CHKBX_SEND_MAIL, TRUE);
		}
		log.info("Setting TFO_CHKBX_SEND_MAIL  flag value "+ defaultMap.get("TFO_ACK_GEN"));
		if (TRUE.equalsIgnoreCase(defaultMap.get("TFO_ACK_GEN"))) {
			formObject.setValue(FLG_ACK_GEN, TRUE);
			formObject.setValue(FLG_ACK_GEN, TRUE);
		} else if (FALSE.equalsIgnoreCase(defaultMap.get("TFO_ACK_GEN"))) {
			setBtnOnAckChk(EVENT_TYPE_LOAD, formObject.getValue(FLG_ACK_GEN).toString().toLowerCase());
		}
	}

	public void setBtnOnAckChk(String event, String controlValue) {
		try {
			log.info("inside setBtnOnAckChk >>> event- " + event + " ## value- " + controlValue);
			if (EVENT_TYPE_LOAD.equalsIgnoreCase(event)) {
				if (TRUE.equalsIgnoreCase(controlValue)) {
					enableControls(BUTTON_CUSTOMER_ACK + "," + FLG_ACK_GEN
							+ "," + CHKBX_SEND_MAIL);
				} else {
					disableControls(BUTTON_CUSTOMER_ACK + "," + FLG_ACK_GEN
							+ "," + CHKBX_SEND_MAIL);
					formObject.setValue(FLG_ACK_GEN, FALSE);
					formObject.setValue(CHKBX_SEND_MAIL, FALSE);
				}
			} else if (EVENT_TYPE_CLICK.equalsIgnoreCase(event)) {
				log.info("on click of customer ack: ");
				if (TRUE.equalsIgnoreCase(controlValue)) {
					enableControls(BUTTON_CUSTOMER_ACK + "," + CHKBX_SEND_MAIL);
					log.info("ack true...");
					formObject.setValue(CHKBX_SEND_MAIL, TRUE);
				} else if (FALSE.equalsIgnoreCase(controlValue)) {
					disableControls(BUTTON_CUSTOMER_ACK + "," + CHKBX_SEND_MAIL);
					log.info("ack false...");
					formObject.setValue(CHKBX_SEND_MAIL, FALSE);
				}
			} else if ("ACKCHK".equalsIgnoreCase(event)) {
				log.info("inside setBtnOnAckChk event-ACKCHK");
				if ("Y".equalsIgnoreCase(controlValue)) {
					log.info("enabling controls");
					enableControls(BUTTON_CUSTOMER_ACK + "," + FLG_ACK_GEN + "," + CHKBX_SEND_MAIL);
					formObject.setValue(CHKBX_SEND_MAIL, TRUE);
					formObject.setValue(FLG_ACK_GEN, TRUE);
				} else if ("N".equalsIgnoreCase(controlValue)) {
					log.info("enabling controls");
					disableControls(BUTTON_CUSTOMER_ACK + "," + FLG_ACK_GEN	+ "," + CHKBX_SEND_MAIL);
					formObject.setValue(CHKBX_SEND_MAIL, FALSE);
					formObject.setValue(FLG_ACK_GEN, FALSE);
				}
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
	}
	}
	public void loadReqType(String reqCatCode, String controlName,
			List<List<String>> reqTypeMast, String sProcessType) { // US3402
		log.info("Before loding req type reqCatCode" + reqCatCode);
		String tmp = "";
		if (null != reqTypeMast && !"0".equalsIgnoreCase(reqCatCode)) {
			tmp = formObject.getValue(controlName).toString();
			for (int counter = 0; counter < reqTypeMast.size(); counter++) {
				if (reqCatCode.equalsIgnoreCase(reqTypeMast.get(counter).get(0))
						&& sProcessType.equalsIgnoreCase(reqTypeMast.get(counter).get(3))) {
					formObject.addItemInCombo(controlName, reqTypeMast.get(counter).get(1), reqTypeMast.get(counter).get(2));
				}
			}
			formObject.setValue(controlName, tmp);
		} else {
			formObject.setValue(controlName, "");
		}
		log.info("After loding req type");
	}

	String setMasterValues(List mastVal) {
		try {
			log.debug("inside setMasterValues mastval.size(): "+ mastVal.size());
			StringBuilder temp = new StringBuilder();
			if (!mastVal.isEmpty()) {
				log.debug("mastval.size: " + mastVal.size());
				for (int counter = 0; counter < mastVal.size(); counter++) {
					temp.append(((String) ((List) mastVal.get(counter)).get(0)));
					temp.append("#~#");
				}
			}
			log.debug("master values: " + temp);
			return temp.toString();
		} catch (Exception e) {
			log.error("exception in setMasterValues");
		}
		return "";

	}

	boolean chkAckBtVisible(String sourceKey) {
		String tmpReqType = formObject.getValue(REQUEST_TYPE).toString();
		log.info("inside chkAckBtVisible sourceKey" + sourceKey
				+ " tmpReqType  " + tmpReqType);
		if ("GRNT".equalsIgnoreCase(formObject.getValue(REQUEST_CATEGORY)
				.toString())) {
			log.info("if GRNT ...");
			if (("A".equalsIgnoreCase(formObject.getValue(REQUESTED_BY) .toString()) || "B".equalsIgnoreCase(formObject.getValue(
					REQUESTED_BY).toString()))
					&& (flagCheck(getMasterValue(listSourceAck, sourceKey)) && flagCheck(getMasterValue(
							listReqTypeAck, tmpReqType)))) {
				return true;
			}
		} else if ("IFS".equalsIgnoreCase(formObject.getValue(REQUEST_CATEGORY)
				.toString())) {
			if (flagCheck(getMasterValue(listSourceAck, sourceKey))
					&& flagCheck(getMasterValue(listReqTypeAck, tmpReqType))
					&& flagCheck(getMasterValue(listReqSubAck, formObject
							.getValue(REQUESTED_BY).toString()))) {
				return true;
			}
		} else if ("IFP".equalsIgnoreCase(formObject.getValue(REQUEST_CATEGORY).toString()) || 
				"SCF".equalsIgnoreCase(formObject.getValue(REQUEST_CATEGORY).toString())) {
			if (flagCheck(getMasterValue(listSourceAck, sourceKey))
					&& flagCheck(getMasterValue(listReqTypeAck, tmpReqType))
					&& flagCheck(getMasterValue(listReqSubAck, formObject.getValue(REQUESTED_BY).toString()))) {
				return true;
			}//kritika start
		}else if ("IFA".equalsIgnoreCase(formObject.getValue(REQUEST_CATEGORY).toString())) {
			if (flagCheck(getMasterValue(listSourceAck, sourceKey))
					&& flagCheck(getMasterValue(listReqTypeAck, tmpReqType))
					&& flagCheck(getMasterValue(listReqSubAck, formObject.getValue(REQUESTED_BY).toString()))) {
				return true;
			}// kritika end 
		} else if ((("EC".equalsIgnoreCase(formObject.getValue(REQUEST_CATEGORY).toString())
				|| "IC".equalsIgnoreCase(formObject.getValue(REQUEST_CATEGORY).toString())
				|| "TL".equalsIgnoreCase(formObject.getValue(REQUEST_CATEGORY).toString())
				|| "ILCB".equalsIgnoreCase(formObject.getValue(REQUEST_CATEGORY).toString())
				|| "ELCB".equalsIgnoreCase(formObject.getValue(REQUEST_CATEGORY).toString())
				|| "ILC".equalsIgnoreCase(formObject.getValue(REQUEST_CATEGORY).toString())
				|| "ELC".equalsIgnoreCase(formObject.getValue(REQUEST_CATEGORY).toString()) 
				|| "DBA".equalsIgnoreCase(formObject.getValue(REQUEST_CATEGORY).toString()))) 
				&& (flagCheck(getMasterValue(listSourceAck, sourceKey))
						&& flagCheck(getMasterValue(listReqTypeAck, tmpReqType))
						&& flagCheck(getMasterValue(listReqSubAck, formObject.getValue(REQUESTED_BY).toString())))) {
			return true;
		} else if ("SG".equalsIgnoreCase(formObject.getValue(REQUEST_CATEGORY).toString())){
			if ("SG_CT".equalsIgnoreCase(formObject.getValue(REQUESTED_BY) .toString())
					&& (flagCheck(getMasterValue(listSourceAck, sourceKey)) && flagCheck(getMasterValue(listReqTypeAck, tmpReqType)))) {
				return true;
			}

		}else if ("SBLC".equalsIgnoreCase(formObject.getValue(REQUEST_CATEGORY).toString())) {//block added by mansi
			log.info("if SBLC ...");
			if (("SBLC_A".equalsIgnoreCase(formObject.getValue(REQUESTED_BY) .toString()) || "SBLC_B".equalsIgnoreCase(formObject.getValue(
					REQUESTED_BY).toString()))
					&& (flagCheck(getMasterValue(listSourceAck, sourceKey)) && flagCheck(getMasterValue(
							listReqTypeAck, tmpReqType)))) {
				return true;
			}
		}
		return false;
	}

	public String getMasterValue(String list, String val) {
		log.info("inside getMasterValue >>>");
		String tempStr = "";
		log.info("get Master List: " + list + " \n val: " + val);
		try {
			if (val != null) {
				if (list.length() > 0) {
					String[] tempArr = list.split("#~#");
					for (int counter = 0; counter < tempArr.length; counter++) {
						if (val.trim().equalsIgnoreCase(
								tempArr[counter].split("###")[0])) {
							tempStr = tempArr[counter].split("###")[1];
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		log.info("get Master List tempStr: " + tempStr);
		return tempStr;
	}

	private boolean flagCheck(String val) {
		if ("Y".equalsIgnoreCase(val) || "Yes".equalsIgnoreCase(val)) {
			return true;
		} else {
			return false;
		}
	}

	public void saveContractLimitData(){
		log.info("inside saveContractLimitData >>");
		String contractReferenceNumber="";
		if("ILCB_BL".equalsIgnoreCase(requestType)){
			log.info("if request type- ILCB_BL");
			List<String> paramlist =new ArrayList<String>( );
			paramlist.add ("Text :"+sWorkitemID);
			List statusProc = formObject.getDataFromStoredProcedure("TFO_DJ_MOVE_CONTRACT_LMT_DATA", paramlist);
		}
	}

	public String chkAmount(String controlName, String cur) {
		String msg = "";
		String currecnyFieldName="";

		try {
			if("LOAN_AMOUNT".equalsIgnoreCase(controlName)){
				currecnyFieldName="INF_LOAN_CURR";
			}else{
				currecnyFieldName="TRANSACTION_CURRENCY";
			}
			String fieldValue = formObject.getValue(controlName).toString()	.trim();
			log.info("fieldValue1  " + fieldValue);
			if (!(null == fieldValue || "".equalsIgnoreCase(fieldValue))) {
				fieldValue = fieldValue.replace(",", "");
				log.info("fieldValue  " + fieldValue);
				try {
					BigDecimal number = new BigDecimal(fieldValue);
					fieldValue = number.stripTrailingZeros().toPlainString();
					log.info("Format removed 123412234"
							+ number.stripTrailingZeros());
					formObject.setValue(controlName, fieldValue);
				} catch (Exception ex) {
					formObject.setValue(controlName, "");
					msg = ALERT_VALID_AMT;
					log.error("exception in amount bigdecimal format: " + ex,ex);
				}
				if (fieldValue.length() > 22) {
					formObject.setValue(controlName, "");
					msg = ALERT_AMT_LNGTH;
				} else {
					try {
						log.info("Modular check"
								+ retAmountWithCurrFormat(fieldValue,
										checkAmtDigit(cur,currecnyFieldName)));
						formObject.setValue(
								controlName,
								retAmountWithCurrFormat(fieldValue,
										checkAmtDigit(cur,currecnyFieldName)));
					} catch (Exception e) {
						log.error("Exception: ",e);
					}
				}
			}
		} catch (Exception ex) {
			formObject.setValue(controlName, "");
			log.error("Exception: ",ex);
		}
		return msg;
	}

	protected String retAmountWithCurrFormat(String fieldValue, int number) {
		log.info("Amount " + fieldValue + " \n Decimal Digit " + number);
		if ("Initiator".equalsIgnoreCase(sActivityName)
				|| "PP_MAKER".equalsIgnoreCase(sActivityName)) {
			try {
				double amt = Double.parseDouble(fieldValue);
				double amtMast = Double.parseDouble(amountCheckMap.get(sActivityName));
				if (amt > amtMast) {
					formObject.setValue("FLG_PPM_AMT", "Y");
				} else {
					formObject.setValue("FLG_PPM_AMT", "N");
				}
			} catch (NumberFormatException e) {
				log.error("Exception: ",e);
			}
		}
		BigDecimal Amount = new BigDecimal(fieldValue);
		NumberFormat formatter = NumberFormat.getInstance(Locale.US);
		formatter.setMaximumFractionDigits(number);
		formatter.setMinimumFractionDigits(number);
		return formatter.format(Amount);
	}

	private int checkAmtDigit(String curVal , String currLoan) {
		int dig = 0;
		String keyVal = "";
		String strCurr = "";
		if ("".equalsIgnoreCase(curVal) || curVal.isEmpty()) {
			/*strCurr = formObject.getValue(TRANSACTION_CURRENCY).toString().split("-")[0];*/
			strCurr = formObject.getValue(currLoan).toString().split("-")[0];
		} else {
			strCurr = curVal;
		}
		log.info("check digit value strCurr " + strCurr);
		if (!(null == strCurr || "".equalsIgnoreCase(strCurr) || "null".equalsIgnoreCase(strCurr))) {
			keyVal = currencyDecimalMap.get(strCurr);
			if (!keyVal.isEmpty()) {
				dig = Integer.parseInt(keyVal);
			}
		}
		return dig;
	}

	public String validateFields() {
		JSONObject jsonObject = new JSONObject();
		if ("Initiator".equalsIgnoreCase(sActivityName)) {
			String req_cat = formObject.getValue(REQUEST_CATEGORY).toString().trim();
			String req_type = formObject.getValue(REQUEST_TYPE).toString().trim();
			String req_by = formObject.getValue(REQUESTED_BY).toString().trim();
			String src_chnl = formObject.getValue(SOURCE_CHANNEL).toString().trim();
			String cust_id = formObject.getValue(CUSTOMER_ID).toString().trim();
			String cust_name = formObject.getValue(CUSTOMER_NAME).toString().trim();
			String brnch_city = formObject.getValue(TFO_BRANCH_CITY).toString().trim();
			String asgn_cntr = formObject.getValue(TFO_ASSIGNED_CENTER).toString().trim();
			String jsonData = mandtoryCheckAtInitaion();
			org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
			JSONObject json;
			try {
				json = (JSONObject) parser.parse(jsonData);
				if ((Boolean) json.get("retVal")) {
					if (req_cat.equalsIgnoreCase("")) {
						jsonObject.put("retVal", false);
						jsonObject.put("msgVal", ALERT_REQ_CAT);
						return jsonObject.toString();
					} else if (req_type.equalsIgnoreCase("")) {
						jsonObject.put("retVal", false);
						jsonObject.put("msgVal", ALERT_REL_TYPE);
						return jsonObject.toString();
					} else if (req_by.equalsIgnoreCase("")) {
						jsonObject.put("retVal", false);
						jsonObject.put("msgVal", ALERT_REQ_BY);
						return jsonObject.toString();
					} else if (src_chnl.equalsIgnoreCase("")) {
						jsonObject.put("retVal", false);
						jsonObject.put("msgVal", ALERT_SRC_CHNL);
						return jsonObject.toString();
					} else if (cust_id.equalsIgnoreCase("")) {
						jsonObject.put("retVal", false);
						jsonObject.put("msgVal", ALERT_CUST_ID);
						return jsonObject.toString();
					} else if (cust_name.equalsIgnoreCase("")) {
						jsonObject.put("retVal", false);
						jsonObject.put("msgVal", ALERT_CUST_NAME);
						return jsonObject.toString();
					} else if (brnch_city.equalsIgnoreCase("")) {
						jsonObject.put("retVal", FALSE);
						jsonObject.put("msgVal", ALERT_BRNCH_CITY);
						return jsonObject.toString();
					} else if (asgn_cntr.equalsIgnoreCase("")) {
						jsonObject.put("retVal", FALSE);
						jsonObject.put("msgVal", ALERT_ASGN_CNTR);
						return jsonObject.toString();
					} else {
						jsonObject.put("retVal", true);
						jsonObject.put("msgVal", "");
						return jsonObject.toString();
					}
				} else {
					return json.toString();
				}
			} catch (ParseException e) {
				log.error("exception in json parsing: " + e);
			}
		}
		jsonObject.put("retVal", FALSE);
		jsonObject.put("msgVal", "");
		return jsonObject.toString();
	}

	public String mandtoryCheckAtInitaion() {
		JSONObject jsonObject = new JSONObject();
		String requestType = formObject.getValue(REQUEST_TYPE).toString();
		if ((("IC_MSM".equalsIgnoreCase(requestType))
				|| ("EC_MSM".equalsIgnoreCase(requestType))
				|| ("ILC_MSM".equalsIgnoreCase(requestType))
				|| ("ELC_MSM".equalsIgnoreCase(requestType))
				|| ("ILCB_MSM".equalsIgnoreCase(requestType))
				|| ("ELCB_MSM".equalsIgnoreCase(requestType))
				|| ("GRNT_MSM".equalsIgnoreCase(requestType))
				|| ("TL_MSM".equalsIgnoreCase(requestType))
				|| ("DBA_MSM".equalsIgnoreCase(requestType))
				|| ("MISC_MSM".equalsIgnoreCase(requestType))
				|| ("IFS_MSM".equalsIgnoreCase(requestType)) 
				|| ("IFP_MSM".equalsIgnoreCase(requestType))
				|| ("SBLC_MSM".equalsIgnoreCase(requestType))//added by mansi
				&& "CID".equalsIgnoreCase(formObject.getValue(
						SWIFT_FETCH_MODULE).toString()))) {
			jsonObject.put("retVal", true);
			jsonObject.put("msgVal", "");
			return jsonObject.toString();
		} else {
			String trans_curr = formObject.getValue(TRANSACTION_CURRENCY)
					.toString().trim();
			String trans_amt = formObject.getValue(TRANSACTION_AMOUNT)
					.toString().trim();
			String rel_type = formObject.getValue(RELATIONSHIP_TYPE).toString()
					.trim();
			if (trans_curr.equalsIgnoreCase("")) {
				jsonObject.put("retVal", FALSE);
				jsonObject.put("msgVal", ALERT_TRNS_CURR);
				return jsonObject.toString();
			} else if (trans_amt.equalsIgnoreCase("")) {
				jsonObject.put("retVal", FALSE);
				jsonObject.put("msgVal", ALERT_TRNS_AMT);
				return jsonObject.toString();
			} else if (rel_type.equalsIgnoreCase("")) {
				jsonObject.put("retVal", FALSE);
				jsonObject.put("msgVal", ALERT_REL_TYPE);
				return jsonObject.toString();
			} else {
				jsonObject.put("retVal", TRUE);
				jsonObject.put("msgVal", "");
				return jsonObject.toString();
			}
		}

	}

	protected boolean checkTrnsMandate(String reqCat, String reqType) {
		if (("GRNT".equalsIgnoreCase(reqCat) // GRNT
				&& ("AM".equalsIgnoreCase(reqType) || "CC".equalsIgnoreCase(reqType)
						|| "CL".equalsIgnoreCase(reqType)
						|| "ER".equalsIgnoreCase(reqType)
						|| "EPC".equalsIgnoreCase(reqType)
						|| "GAA".equalsIgnoreCase(reqType)))
						||(("SBLC".equalsIgnoreCase(reqCat)) // SBLC //RR
								&& ("SBLC_AM".equalsIgnoreCase(reqType) || "SBLC_ER".equalsIgnoreCase(reqType)
										|| "SBLC_CR".equalsIgnoreCase(reqType)
										|| "SBLC_CS".equalsIgnoreCase(reqType)))
										|| (("IFS".equalsIgnoreCase(reqCat)) // Invoice Financing Sales
												&& ("AM".equalsIgnoreCase(reqType) || "STL"
														.equalsIgnoreCase(reqType)))
														|| (("IFP".equalsIgnoreCase(reqCat)) // Invoice Financing
																// Purchase
																&& ("AM".equalsIgnoreCase(reqType) || "STL"
																		.equalsIgnoreCase(reqType)))
																		|| (("IFA".equalsIgnoreCase(reqCat)) // IFA CODE BY MOKSH
																				// Purchase
																				&& ("AM".equalsIgnoreCase(reqType) || "STL"
																						.equalsIgnoreCase(reqType)))
																						||(("EC".equalsIgnoreCase(reqCat)) // Export Collection
																								&& ("EC_AM".equalsIgnoreCase(reqType)
																										|| "EC_AC".equalsIgnoreCase(reqType)
																										|| "EC_CA".equalsIgnoreCase(reqType)
																										|| "EC_BS".equalsIgnoreCase(reqType)
																										|| "EC_BCDR".equalsIgnoreCase(reqType)
																										|| "EC_DISC".equalsIgnoreCase(reqType) || "EC_CAP"
																										.equalsIgnoreCase(reqType)))
																										|| (("IC".equalsIgnoreCase(reqCat)) // Import Collection
																												&& ("IC_AM".equalsIgnoreCase(reqType)
																														|| "IC_AC".equalsIgnoreCase(reqType)
																														|| "IC_BS".equalsIgnoreCase(reqType)
																														|| "IC_BCDR".equalsIgnoreCase(reqType)
																														|| "IC_LD".equalsIgnoreCase(reqType)
																														|| "IC_LAM".equalsIgnoreCase(reqType)
																														|| "IC_STL".equalsIgnoreCase(reqType) || "IC_PRC"
																														.equalsIgnoreCase(reqType)))
																														|| (("DBA".equalsIgnoreCase(reqCat)) // Import Collection
																																&& ("DBA_AM".equalsIgnoreCase(reqType) || "DBA_BS"
																																		.equalsIgnoreCase(reqType)||"DBA_STL".equalsIgnoreCase(reqType)))
																																		|| (("ILCB".equalsIgnoreCase(reqCat))// Import LC Bills
																																				&& ("ILCB_AM".equalsIgnoreCase(reqType)
																																						|| "ILCB_AC".equalsIgnoreCase(reqType)
																																						|| "ILCB_BS".equalsIgnoreCase(reqType)
																																						|| "ILCB_BCDR".equalsIgnoreCase(reqType)
																																						|| "ILCB_LD".equalsIgnoreCase(reqType)
																																						|| "ILCB_LAM".equalsIgnoreCase(reqType) || "ILCB_STL"
																																						.equalsIgnoreCase(reqType)))
																																						|| (("ELCB".equalsIgnoreCase(reqCat)) // Export LC Bills
																																								&& ("ELCB_AM".equalsIgnoreCase(reqType)
																																										|| "ELCB_AC".equalsIgnoreCase(reqType)
																																										|| "ELCB_BS".equalsIgnoreCase(reqType)
																																										|| "ELCB_BCDR".equalsIgnoreCase(reqType)
																																										|| "ELCB_DISC".equalsIgnoreCase(reqType) || "ELCB_CLBP".equalsIgnoreCase(reqType)))
																																										|| (("ELC".equalsIgnoreCase(reqCat)) // Export LC
																																												&& ("ELC_LCAA".equalsIgnoreCase(reqType)
																																														|| "ELC_LCCL".equalsIgnoreCase(reqType)
																																														|| "ELC_LCC".equalsIgnoreCase(reqType)
																																														|| "ELC_AOP".equalsIgnoreCase(reqType)
																																														|| "ELC_LCT".equalsIgnoreCase(reqType) || "ELC_RSD".equalsIgnoreCase(reqType) 
																																														|| "ELC_SLCAA".equalsIgnoreCase(reqType)|| "ELC_SER".equalsIgnoreCase(reqType)|| "ELC_SCL".equalsIgnoreCase(reqType)))//added by mansi
																																														|| (("ILC".equalsIgnoreCase(reqCat)) // Import LC
																																																&& ("ILC_AM".equalsIgnoreCase(reqType) || "ILC_CL"
																																																		.equalsIgnoreCase(reqType)))
																																																		|| (("TL".equalsIgnoreCase(reqCat)) // Trade Loans
																																																				&& ("TL_AM".equalsIgnoreCase(reqType) || "TL_STL"
																																																						.equalsIgnoreCase(reqType)))) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkLengthValidation(String sFieldName, String alertMsg,
			int length) {
		try {
			String fieldValue = formObject.getValue(sFieldName).toString()
					.trim();
			if (fieldValue.length() < length) {
				return false;
			} else {
				return true;
			}

		} catch (Exception e) {
			log.error("excptn in check length: " + e, e);
		}
		return false;
	}

	public boolean checkMaxLengthValidation(String sFieldName, String alertMsg,
			int length) {
		try {
			String fieldValue = formObject.getValue(sFieldName).toString()
					.trim();
			if (sFieldName.equalsIgnoreCase(TRNS_REF_NO)) {
				if (fieldValue.length() < length) {
					return false;
				} else {
					return true;
				}
			} else {
				if (fieldValue.length() > length) {
					return false;
				} else {
					return true;
				}
			}

		} catch (Exception e) {
			log.error("exception: ",e);
		}
		return false;
	}

	public void loadRequestCategory(){
		log.info("inside loadRequestCategory Logistics..");
		if("BAU".equalsIgnoreCase(formObject.getValue(PROCESS_TYPE).toString())){
			log.info("loadProcessType- process type BAU");
			String tmp = formObject.getValue(REQUEST_CATEGORY).toString();
			formObject.clearCombo(REQUEST_CATEGORY);
			loadComboWithCode("SELECT REQUEST_CATEGORY,REQUEST_CATEGORY_ID FROM TFO_DJ_REQUEST_CATEGORY_MAST where process_type ='BAU' ORDER BY TO_NUMBER(ID)", REQUEST_CATEGORY);
			if(!tmp.equalsIgnoreCase("")){
				formObject.setValue(REQUEST_CATEGORY, tmp);
			}
		} else if("SWIFT".equalsIgnoreCase(formObject.getValue(PROCESS_TYPE).toString())){
			log.info("loadProcessType- process type SWIFT..");
			String tmp = formObject.getValue(REQUEST_CATEGORY).toString();
			formObject.clearCombo(REQUEST_CATEGORY);
			loadComboWithCode("SELECT REQUEST_CATEGORY,REQUEST_CATEGORY_ID FROM TFO_DJ_REQUEST_CATEGORY_MAST where process_type ='SWIFT' ORDER BY TO_NUMBER(ID)", REQUEST_CATEGORY);
			if(!tmp.equalsIgnoreCase("")){
				formObject.setValue(REQUEST_CATEGORY, tmp);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public String docCheck(String reqType) {
		String documentMissing = "";
		boolean retVal = true;
		boolean docChk = false;
		String retMsgAndVal = TRUE;
		String strProdDoc = "SELECT B.NAME AS DOCUMENTNAME FROM PDBDOCUMENTCONTENT A,PDBDOCUMENT B WHERE A.PARENTFOLDERINDEX='"
				+ strItemIndex + "' AND A.DOCUMENTINDEX=B.DOCUMENTINDEX";
		log.info("strProdDoc  " + strProdDoc);
		List<List<String>> listProd = formObject.getDataFromDB(strProdDoc);
		String strDoc = "SELECT DOC_TYPE FROM TFO_DJ_DOC_TYPE_MAST WHERE IS_MANDATORY='Y' AND WORKSTEP='"
				+ sActivityName + "' AND REQ_TYPE='" + reqType + "'";
		log.info("strDoc  " + strDoc);
		List<List<String>> listDoc = formObject.getDataFromDB(strDoc);
		if (listDoc != null && !listDoc.isEmpty()) {
			for (int counter = 0; counter < listDoc.size(); counter++) {
				if (docPrdCompare(
						((String) ((List) listDoc.get(counter)).get(0)),
						listProd)) {
					log.info("Attached >>>Doc Check "
							+ ((String) ((List) listDoc.get(counter)).get(0)));
					continue;
				} else {
					log.info("Not Attached Ok >>>Doc Check "
							+ ((String) ((List) listDoc.get(counter)).get(0)));
					documentMissing = ((String) ((List) listDoc.get(counter))
							.get(0));
					docChk = true;
				}
				if (docChk) {
					break;
				}
			}
			if (docChk) {
				documentMissing = documentMissing.replace("_", " ");
				retMsgAndVal = "false#Please attach documents for " + documentMissing;
			}
		}
		return retMsgAndVal;
	}

	private boolean docPrdCompare(String docName, List<List<String>> listProd) {
		log.info("DocPrdCompare docName " + docName);
		boolean bDocAtc = false;
		if (listProd != null && !listProd.isEmpty()) {
			for (int counter = 0; counter < listProd.size(); counter++) {
				if (docName.equalsIgnoreCase(listProd.get(counter).get(0))) {
					log.info("In Loop For doc prod compare"
							+ listProd.get(counter).get(0));
					bDocAtc = true;
					break;
				}
			}
		}
		return bDocAtc;
	}
	
	public boolean checkUTCCategory(){
		String reqType = formObject.getValue(REQUEST_TYPE).toString();
		String	reqCat = formObject.getValue(REQUEST_CATEGORY).toString();
		if(("IFP".equalsIgnoreCase(reqCat) || "IFS".equalsIgnoreCase(reqCat) //ADDED SCF CONDITION ATP- 204,205
				|| "IFA".equalsIgnoreCase(reqCat) || "SCF".equalsIgnoreCase(reqCat)) 
				&& ("LD".equalsIgnoreCase(reqType) || "IFA_CTP".equalsIgnoreCase(reqType) || "PD".equalsIgnoreCase(reqType)|| "PDD".equalsIgnoreCase(reqType))) {
			return true;
		}
		return false;
			
	}

	@SuppressWarnings("unchecked")
	public void saveDecHistory() {
		String groupname = "";
		String personalName = "";
		String decQuery = "";
		String strRemarks = "";
		String sDecision = "";
		String sDuplicatecheck = "";
		String strReasonforAction = "";
		String emailFlag = "";
		if (this.sActivityName.equalsIgnoreCase("PP_MAKER")
				|| this.sActivityName.equalsIgnoreCase("PROCESSING MAKER")
				|| this.sActivityName.equalsIgnoreCase("PROCESSING CHECKER")){
			if( !this.sActivityName.equalsIgnoreCase("SCC")){
			sDuplicatecheck = getDescriptionFromCode(DUP_CHK_CONFIRMATION, formObject.getValue(DUP_CHK_CONFIRMATION).toString());
		}
		}
		try {
			String sQuery = "SELECT PERSONALNAME FROM PDBUSER WHERE UPPER(USERNAME)='"+ sUserName.toUpperCase() + "'";
			List<List<String>> sOutputlist = formObject.getDataFromDB(sQuery);
			if (sOutputlist != null && !sOutputlist.isEmpty()
					&& sOutputlist.get(0).size() > 0) {
				personalName = sOutputlist.get(0).get(0);
			}
			log.info("++mail"+ formObject.getValue(CHKBX_SEND_MAIL).toString());
			if (this.sActivityName.equalsIgnoreCase("Logistics Team")|| this.sActivityName.equalsIgnoreCase("Assignment Queue")) {
				emailFlag = "NA";
			} else {
				emailFlag = formObject.getValue(CHKBX_SEND_MAIL).toString();
			}
			if ("Initiator".equalsIgnoreCase(sActivityName)) {
				decQuery = "INSERT INTO " + decHist + "( " + decHistCol
						+ " ) VALUES ('" + sWorkitemID + "','"
						+ this.sUserName.toUpperCase() + "','"
						+ formObject.getValue(PREV_WS) + "','"
						+ sActivityName + "',sysdate,'" + personalName
						+ "','" + groupname + "','" + " Workitem Initiated"
						+ "','',sysdate,'Initiated by " + sUserName + "','"
						+ formObject.getValue(INITIATOR_BRANCH)
						+ "',' ',' ','" + emailFlag + "')";
			}else {
//				if(("INTREJ".equalsIgnoreCase(formObject.getValue(DEC_CODE).toString()) ||
//						"REJ".equalsIgnoreCase(formObject.getValue(DEC_CODE).toString())) &&
//						("PP_MAKER".equalsIgnoreCase(this.sActivityName))){
//					strRemarks =  formObject.getValue(REJ_RESN_PPM).toString()
//							.replace("'", "''");
////					strRemarks = strRemarks.contains("/")?strRemarks.replaceAll("/", ""):strRemarks;
////					strRemarks = strRemarks.contains("]")?strRemarks.replaceAll("]", ""):strRemarks;
////					strRemarks = strRemarks.contains("[")?strRemarks.replaceAll("[", ""):strRemarks;
//					strReasonforAction = formObject.getValue(REMARKS).toString().replace("'", "''");
//					log.info("Inside reject strRemarks" + strRemarks);
//					log.info("Inside reject strReasonforAction" + strReasonforAction);
//				}else {
					strRemarks = getRemarks();
					strReasonforAction = getReasonForAction();
//				}
				sDecision = getDecision();
				
//				Added by Shivanshu ATP-458
				if ("MT798".equalsIgnoreCase(formObject.getValue(SWIFT_CHANNEL).toString())) {
					if (!formObject.getValue(ACCOUNT_NUMBER).toString()
							.equalsIgnoreCase(formObject.getValue(DR_ACC_NUM).toString())) {
						strRemarks = "Account selected for Charge account is different from account received via MT798.";
					}
				}
					
				decQuery = "INSERT INTO " + decHist + "( " + decHistCol
						+ " ) VALUES ('" + sWorkitemID + "','"
						+ this.sUserName.toUpperCase() + "','"
						+ formObject.getValue(PREV_WS).toString() + "','"
						+ sActivityName + "',sysdate,'" + personalName
						+ "','" + groupname + "','" + sDecision + "','"
						+ strReasonforAction + "',sysdate,'" + strRemarks
						+ "','" + formObject.getValue(INITIATOR_BRANCH)
						+ "','" + sDuplicatecheck + "','" + getRouteToVal()
						+ "','" + emailFlag + "')";
			}
			log.info("Decision " + decQuery);
			formObject.saveDataInDB(decQuery);
		} catch (Exception e) {
			log.error("exception in saveDecHistory: " + e, e);
		}
	}
	
	public void saveUTCJustfyDecHistory(){
		String groupname = "";
		String personalName = "";
		String decQuery = "";

		try {
			log.info("<<<<<<<<<inside saveUTCJustfyDecHistory>>>>>>>>>: ");
			String sQuery = "SELECT PERSONALNAME FROM PDBUSER WHERE UPPER(USERNAME)='"+ sUserName.toUpperCase() + "'";
			List<List<String>> sOutputlist = formObject.getDataFromDB(sQuery);
			if (sOutputlist != null && !sOutputlist.isEmpty()
					&& sOutputlist.get(0).size() > 0) {
				personalName = sOutputlist.get(0).get(0);
			}
			if(checkUTCCategory()){
				String decJstifyQuery = "";
				String justifyValue ="";
				if("PP_MAKER".equalsIgnoreCase(sActivityName)) {
					String sQuery1 = "SELECT utc_jstification_required FROM EXT_TFO WHERE WI_NAME='"+ sWorkitemID + "'";
					List<List<String>> sOutputlist1 = formObject.getDataFromDB(sQuery1);
					log.info("inside  sOutputlist1 "+sOutputlist1);
					if (sOutputlist1 != null && !sOutputlist1.isEmpty()
							&& sOutputlist1.get(0).size() > 0) {
						justifyValue = sOutputlist1.get(0).get(0);
						log.info("inside  justifyValue "+justifyValue);
					}
					decQuery = "INSERT INTO " + decHist + "( " + decHistCol
							+ " ) VALUES ('" + sWorkitemID + "','"
							+ this.sUserName.toUpperCase() + "','"
							+ formObject.getValue("PREV_WS") + "','"
							+ sActivityName + "',sysdate,'" + personalName
							+ "','" + groupname + "',' " + "UTC Justification"
							+"','',sysdate,'UTC Justification: " + justifyValue + "','',' ',' ','')";

					String sQuery2 = "select COUNT(*) as count from tfo_dj_dec_hist where wi_name = '"+ sWorkitemID + "' and action like '% UTC Justification%'";
					List<List<String>> sOutputlist2 = formObject.getDataFromDB(sQuery2);
					log.info("inside  sOutputlist2 "+sOutputlist2);
					int sCount = Integer.parseInt(sOutputlist2.get(0).get(0));
					if (sCount > 0) {
						String qry = "UPDATE tfo_dj_dec_hist set REMARKS = '" + justifyValue + "' WHERE WI_NAME='"+sWorkitemID+"' and action like '% UTC Justification%'";
						log.info("Saving in tfo_dj_dec_hist records query : "+qry);
						formObject.saveDataInDB(qry);
						log.info("AFTER SAVE tfo_dj_dec_hist utc record : ");
					} else {
						log.info("Decision UTC" + decQuery);
						formObject.saveDataInDB(decQuery);
					}

					log.info("Decision decBRMSQuery" + decQuery);
				}
			}
		} catch (Exception e) {
			log.error("exception in saveDecHistory: " + e, e);
		}
	}

	public String getDescriptionFromCode(String comboID, String selectedValue){
		log.info("inside getDescriptionFromCode >>");
		String description = "";
		EComboControl combo = (EComboControl) formObject.getIFormControl(comboID);
		ArrayList<EControlOption> list = combo.getM_objControlOptions().getM_arrOptions();
		for(EControlOption objOption: list){
			log.info("objOption.getM_strOptionValue(): "+objOption.getM_strOptionValue());
			if(objOption.getM_strOptionValue().equalsIgnoreCase(selectedValue)){
				log.info("found match for: "+objOption.getM_strOptionValue());
				description = objOption.getM_strOptionLabel();
				break;
			}
		}
		log.info("combo description: "+description);
		return description;
	}

	public int getListCount(String controlName){
		int count = 0;
		try {
			EComboControl combo = (EComboControl) formObject.getIFormControl(controlName);
			ArrayList<EControlOption> list = combo.getM_objControlOptions().getM_arrOptions();
			count = list.size();
		} catch (Exception e) {
			log.error("Exception in getListCount: "+e,e);
		}
		return count;
	}

	public int getGridCount(String controlName){
		int count = 0;
		try {
			JSONArray jsonArray =  formObject.getDataFromGrid(controlName);
			count = jsonArray.size();
		} catch (Exception e) {
			log.error("Exception in getListCount: "+e,e);
		}
		return count;
	}

	private String getRouteToVal() {
		try {
			if ("PP_MAKER".equalsIgnoreCase(sActivityName)
					|| "Assignment Queue".equalsIgnoreCase(sActivityName)) {
				return formObject.getValue(ROUTE_TO_PPM).toString();
			} else if ("PROCESSING MAKER".equalsIgnoreCase(sActivityName)
					|| "PROCESSING CHECKER".equalsIgnoreCase(sActivityName)) {
				return formObject.getValue(ROUTE_TO_PM).toString();
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		return "";
	}

	protected String getRemarks() {
		String sRemarks = "";
		String des = "";
	    String temp = "";
	    String temp2 = "";
		if ("Logistics Team".equalsIgnoreCase(this.sActivityName))
			sRemarks = formObject.getValue(REMARKS).toString().replace("'", "''");
		else if ("PRE PROCESSING CHECKER".equalsIgnoreCase(this.sActivityName))
			sRemarks = "";
		else if ("PP_MAKER".equalsIgnoreCase(this.sActivityName)){
			sRemarks = formObject.getValue(REMARKS).toString().replace("'", "''");
			sRemarks = this.formObject.getValue("REJ_RESN_PPM").toString().replace("'", "''");
		      des = sRemarks.replaceAll("\"", "'");
		      temp = des.replace("[", "");
		      temp2 = temp.replace("]", "");
		      sRemarks = temp2;
		}
		else if ("PROCESSING MAKER".equalsIgnoreCase(this.sActivityName)
				|| "PROCESSING CHECKER".equalsIgnoreCase(this.sActivityName)
				|| "RM".equalsIgnoreCase(this.sActivityName)
				|| "CUSTOMER_REVIEW".equalsIgnoreCase(this.sActivityName)
				|| "TSD".equalsIgnoreCase(this.sActivityName)
				|| "LEGAL_TEAM".equalsIgnoreCase(this.sActivityName)
				|| "COMPLIANCE".equalsIgnoreCase(this.sActivityName)
				|| "CORRESPONDENT_BANK".equalsIgnoreCase(this.sActivityName)
				|| "Trade Sales".equalsIgnoreCase(this.sActivityName)
				|| "TB Sales".equalsIgnoreCase(this.sActivityName)
				|| "Treasury".equalsIgnoreCase(this.sActivityName)
				||"PC PROCESSING SYSTEM".equalsIgnoreCase(sActivityName)
				||"PM PROCESSING SYSTEM".equalsIgnoreCase(sActivityName)
				||"SCC".equalsIgnoreCase(sActivityName)
				|| "Customer Referral Response".equalsIgnoreCase(this.sActivityName)
				|| "Compliance Referral Response".equalsIgnoreCase(this.sActivityName)
				|| "Trayd Stream".equalsIgnoreCase(sActivityName)) {
			sRemarks = formObject.getValue(REMARKS).toString().replace("'", "''");
		} else {
			sRemarks = "";
			}
		return sRemarks;
	}

	protected String getReasonForAction() {
		String des = "";
	    String temp = "";
	    String temp2 = "";
		String sReasonForAction = "";
		if ("Logistics Team".equalsIgnoreCase(this.sActivityName))
			sReasonForAction = "";
		else if ("PP_MAKER".equalsIgnoreCase(this.sActivityName)
				|| "PRE PROCESSING CHECKER".equalsIgnoreCase(this.sActivityName))
			sReasonForAction = formObject.getValue(REJ_RESN_PPM).toString()
			.replace("'", "''");
		else if ("PROCESSING MAKER".equalsIgnoreCase(this.sActivityName)
				|| "PROCESSING CHECKER".equalsIgnoreCase(this.sActivityName))
			sReasonForAction = "";
		else if ("RM".equalsIgnoreCase(this.sActivityName)
				|| "CUSTOMER_REVIEW".equalsIgnoreCase(this.sActivityName)
				|| "TSD".equalsIgnoreCase(this.sActivityName)
				|| "LEGAL_TEAM".equalsIgnoreCase(this.sActivityName)
				|| "CORRESPONDENT_BANK".equalsIgnoreCase(this.sActivityName)
				|| "Trade Sales".equalsIgnoreCase(this.sActivityName)
				|| "TB Sales".equalsIgnoreCase(this.sActivityName)
				|| "Treasury".equalsIgnoreCase(this.sActivityName)
				|| "COMPLIANCE".equalsIgnoreCase(this.sActivityName)
				|| "Customer Referral Response".equalsIgnoreCase(this.sActivityName)) {
			sReasonForAction = "";
		} else if ("SCC".equalsIgnoreCase(this.sActivityName)){
			sReasonForAction = getExtTableData().replace("'", "''");
		} else if ("Trayd Stream".equalsIgnoreCase(this.sActivityName)){
			sReasonForAction = this.formObject.getValue("REJ_RESN_PPM").toString().replace("'", "''");
		      des = sReasonForAction.replaceAll("\"", "'");
		      temp = des.replace("[", "");
		      temp2 = temp.replace("]", "");
		      sReasonForAction = temp2.replace("'", "");
		}
		if (sReasonForAction.trim().equalsIgnoreCase("0"))
			sReasonForAction = "";
		return sReasonForAction;
	}

	protected String getDecision() {
		String sDecision = "";
		if ("Logistics Team".equalsIgnoreCase(this.sActivityName))
			sDecision = "Document Scanned";
		else if ("PP_MAKER".equalsIgnoreCase(this.sActivityName)
				|| "DELIVERY".equalsIgnoreCase(sActivityName)
				|| "PRE PROCESSING CHECKER".equalsIgnoreCase(this.sActivityName))
			sDecision = formObject.getValue(FINAL_DESC_PPM).toString();
		else if ("PROCESSING MAKER".equalsIgnoreCase(this.sActivityName)
				|| "PROCESSING CHECKER".equalsIgnoreCase(this.sActivityName)
				|| "Compliance Referral Response".equalsIgnoreCase(this.sActivityName))
			sDecision = formObject.getValue(FINAL_DESC_PPM).toString();
		else if ("RM".equalsIgnoreCase(this.sActivityName)
				|| "CUSTOMER_REVIEW".equalsIgnoreCase(this.sActivityName)
				|| "TSD".equalsIgnoreCase(this.sActivityName)
				|| "LEGAL_TEAM".equalsIgnoreCase(this.sActivityName)
				|| "COMPLIANCE".equalsIgnoreCase(this.sActivityName)
				|| "CORRESPONDENT_BANK".equalsIgnoreCase(this.sActivityName)
				|| "Trade Sales".equalsIgnoreCase(this.sActivityName)
				|| "TB Sales".equalsIgnoreCase(this.sActivityName)
				|| "Treasury".equalsIgnoreCase(this.sActivityName))
			sDecision = formObject.getValue(DECISION).toString();
		else if ("Customer Referral Response".equalsIgnoreCase(this.sActivityName))
			sDecision = getDescriptionFromCode(DEC_CODE,formObject.getValue(DEC_CODE).toString());
		else if("PC PROCESSING SYSTEM".equalsIgnoreCase(sActivityName)
				||"PM PROCESSING SYSTEM".equalsIgnoreCase(sActivityName)
				||"SCC".equalsIgnoreCase(sActivityName)
				||"Repair Queue".equalsIgnoreCase(sActivityName)
				||"Trayd Stream".equalsIgnoreCase(sActivityName)){
			sDecision = getDescriptionFromCode(DEC_CODE,formObject.getValue(DEC_CODE).toString());
		}
		else
			sDecision = "";
		if (sDecision.trim().equalsIgnoreCase(""))
			sDecision = "";
		return sDecision;
	}

	@SuppressWarnings("unchecked")
	public void loadDecisionHistoryListView() {
		try {
			log.info("***************************Inside loadDecisionHistoryListView********************************");
			String decQuery = "SELECT to_char(CREATE_DATE,'DD/MM/YYYY HH24:MI:SS') DT ,CURR_WS_NAME, USER_ID,USERNAME,"
					+ "REF_EMAIL_ID, ACTION,REASON_FOR_ACTION,REMARKS FROM TFO_DJ_DEC_HIST WHERE "
					+ "WI_NAME ='"+ sWorkitemID	+ "' order by CREATE_DATE DESC";
			log.info("decision history query: "+decQuery);
			List recordList = formObject.getDataFromDB(decQuery);
			JSONArray jsonArray = new JSONArray();
			for (int i = 0; i < recordList.size(); i++) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("Date and Time",
						((String) ((List) recordList.get(i)).get(0)));
				jsonObject.put("Workstep",
						((String) ((List) recordList.get(i)).get(1)));
				jsonObject.put("User ID",
						((String) ((List) recordList.get(i)).get(2)));
				jsonObject.put("Username",
						((String) ((List) recordList.get(i)).get(3)));
				jsonObject.put("Referral Response Email",
						((String) ((List) recordList.get(i)).get(4)));
				jsonObject.put("Action",
						((String) ((List) recordList.get(i)).get(5)));
				jsonObject.put("Referral Tab Name",
						((String) ((List) recordList.get(i)).get(6)));
				jsonObject.put("Remarks or Exceptions",
						((String) ((List) recordList.get(i)).get(7)));

				jsonArray.add(jsonObject);
			}
			log.info("jsonarray: "+jsonArray);
			formObject.addDataToGrid("LVW_Decision_Sumary", jsonArray);
			log.info("***************************END loadDecisionHistoryListView********************************");
		} catch (Exception e) {
			log.error(",,,,,,#Exception in loadDecisionHistoryListView: "+ e, e);
		}
	}

	protected void setAckGenBtnShown(String source) {
		log.info("setAckGenBtnShown source  " + source);
		if ("Initiator".equalsIgnoreCase(sActivityName)) {
			if (chkAckBtVisible(source)) {
				formObject.setStyle(BUTTON_CUSTOMER_ACK, VISIBLE, TRUE);
				setBtnOnAckChk("ACKCHK", "Y");
			} else {
				formObject.setStyle(BUTTON_CUSTOMER_ACK, VISIBLE, FALSE);
				setBtnOnAckChk("ACKCHK", "N");
			}
		} else if ("Logistics Team".equalsIgnoreCase(sActivityName)) {
			formObject.setStyle(BUTTON_CUSTOMER_ACK, VISIBLE, FALSE);
		}
	}

	protected void dedupeCheck() {
		String sParameter = "";
		String sCID = "", sTxnCrncy = "", sTxnAmnt = "0", sReqCat = "", sReqType = "";
		sCID = formObject.getValue(CUSTOMER_ID).toString();
		sTxnCrncy = formObject.getValue(TRANSACTION_CURRENCY).toString();
		sTxnAmnt = formObject.getValue(TRANSACTION_AMOUNT).toString();
		sReqCat = formObject.getValue(REQUEST_CATEGORY).toString();
		sReqType = formObject.getValue(REQUEST_TYPE).toString();
		sParameter = sWorkitemID + "','" + sUserName.toUpperCase() + "','"
				+ sActivityName + "','" + sCID + "','" + sTxnCrncy + "','"
				+ sTxnAmnt + "','" + sReqCat + "','" + sReqType;
		log.info("Parameter " + sParameter);
		List<String> paramlist =new ArrayList<String>( );
		paramlist.add ("Text :"+sWorkitemID);
		paramlist.add ("Text :"+sUserName.toUpperCase());
		paramlist.add ("Text :"+sActivityName);
		paramlist.add ("Text :"+sCID);
		paramlist.add ("Text :"+sTxnCrncy);
		paramlist.add ("Text :"+sTxnAmnt);
		paramlist.add ("Text :"+sReqCat);
		paramlist.add ("Text :"+sReqType);
		log.info("calling procedure TFO_DJ_DEDUP");
		List statusProc = formObject.getDataFromStoredProcedure("TFO_DJ_DEDUP", paramlist);
	}

	@SuppressWarnings("unchecked")
	public String fetchTransactioDtls(String refNo, String sRequestCategory,
			String requestType, String transFieldName,
			String transRefOutFieldName, boolean otherRefNum) {
		log.info("Inside the fetchTransactioDtls  " + refNo
				+ " and req category : " + sRequestCategory
				+ ": transFieldName: " + transFieldName
				+ " transRefOutFieldName:" + transRefOutFieldName
				+ ": otherRefNum: " + otherRefNum);
		String retMsg = "";
		try {
			String whereConditionField = "GTEE_NUMBER";
			if (otherRefNum) {
				whereConditionField = "OTHER_TRAN_REF_NUM";
			}

			String strQ = "";
			List<List<String>> sOutputlist = null;
			if ("GRNT".equalsIgnoreCase(sRequestCategory)|| "SBLC".equalsIgnoreCase(sRequestCategory)//added by mansi
					||"SG_SD".equalsIgnoreCase(requestType)) {  //Shipping_Gtee_10
				strQ = "SELECT CUSTOMER_ID,COMPANY_NAME,PROFIT_CENTER_CODE,CUSTOMER_CATEGORY,EMAIL_ID,MOBILE_NUMBER,ADDRESS_LINE_1,"
						+ "ADDRESS_LINE_2,EMIRATES,PO_BOX,RM_NAME, RM_MOBILE_NUMBER,GTEE_NUMBER,CURRENCY,AMOUNT,DECODE(IS_CUSTOMER_VIP,'0',null,IS_CUSTOMER_VIP) IS_CUSTOMER_VIP,PRODUCT,"
						+ "TRNS_BRN_CODE,LC_OS_AMT,IS_ACTIVE,TO_CHAR(TO_DATE(EXP_DATE,'DD-MON-YYYY'),'YYYY-MM-DD') EXP_DATE1, TRNS_TENOR,"
						+ "DECODE(IS_VALID_ACC,0,null,IS_VALID_ACC) IS_VALID_ACC,DECODE(TRNS_THIRD_PARTY,0,null,TRNS_THIRD_PARTY) TRNS_THIRD_PARTY,DECODE(ACCOUNT_NUMBER,0,null,ACCOUNT_NUMBER) ACCOUNT_NUMBER, OTHER_TRAN_REF_NUM, ISSUING_CENTER,BRN_CODE  FROM TFO_WMS_MASTER  WHERE "
						+ whereConditionField + "='" + refNo + "'";
				log.info("Trns Detls query " + strQ);
				sOutputlist = formObject.getDataFromDB(strQ);
			}else if ("ELC".equalsIgnoreCase(sRequestCategory) && ("ELC_SLCA".equalsIgnoreCase(requestType)|| "ELC_SLCAA".equalsIgnoreCase(requestType)|| "ELC_SER".equalsIgnoreCase(requestType)|| "ELC_SCL".equalsIgnoreCase(requestType))){ //added by mansi
				strQ = "SELECT CUSTOMER_ID,COMPANY_NAME,PROFIT_CENTER_CODE,CUSTOMER_CATEGORY,EMAIL_ID,MOBILE_NUMBER,ADDRESS_LINE_1,"
						+ "ADDRESS_LINE_2,EMIRATES,PO_BOX,RM_NAME, RM_MOBILE_NUMBER,GTEE_NUMBER,CURRENCY,AMOUNT,DECODE(IS_CUSTOMER_VIP,'0',null,IS_CUSTOMER_VIP) IS_CUSTOMER_VIP,PRODUCT,"
						+ "TRNS_BRN_CODE,LC_OS_AMT,IS_ACTIVE,TO_CHAR(TO_DATE(EXP_DATE,'DD-MON-YYYY'),'YYYY-MM-DD') EXP_DATE1, TRNS_TENOR,"
						+ "DECODE(IS_VALID_ACC,0,null,IS_VALID_ACC) IS_VALID_ACC,DECODE(TRNS_THIRD_PARTY,0,null,TRNS_THIRD_PARTY) TRNS_THIRD_PARTY,DECODE(ACCOUNT_NUMBER,0,null,ACCOUNT_NUMBER) ACCOUNT_NUMBER, OTHER_TRAN_REF_NUM, ISSUING_CENTER,BRN_CODE  FROM TFO_WMS_MASTER  WHERE "
						+ whereConditionField + "='" + refNo + "'";
				log.info("Trns Detls query " + strQ);
				sOutputlist = formObject.getDataFromDB(strQ);
			} else if ("IFS".equalsIgnoreCase(sRequestCategory)
					|| "IFP".equalsIgnoreCase(sRequestCategory)
					|| "IFA".equalsIgnoreCase(sRequestCategory)  //ADDED BY KISHAN
					|| "TL".equalsIgnoreCase(sRequestCategory)
					|| "SCF".equalsIgnoreCase(sRequestCategory)) {  //ADDED FOR SCF REQ ATP 207
				strQ = "SELECT CUSTOMER_ID,COMPANY_NAME,PROFIT_CENTER_CODE,CUSTOMER_CATEGORY,EMAIL_ID,MOBILE_NUMBER,ADDRESS_LINE_1,"
						+ "ADDRESS_LINE_2,EMIRATES,PO_BOX,RM_NAME, RM_MOBILE_NUMBER,GTEE_NUMBER,CURRENCY,AMOUNT,IS_CUSTOMER_VIP,PRODUCT,"
						+ "TRNS_BRN_CODE,OS_AMT,DECODE(IS_ACTIVE,'0',null,IS_ACTIVE) IS_ACTIVE ,TENOR_DAY,DECODE(TENOR_BASE,'0',null,TENOR_BASE) TENOR_BASE, TENOR_BASE_DTLS,DECODE(CHARGE_ACC_NUM,0,null,CHARGE_ACC_NUM) CHARGE_ACC_NUM,DECODE(STTLMNT_ACC_NUM,0,null,STTLMNT_ACC_NUM) STTLMNT_ACC_NUM,DECODE(CRDT_ACC_NUM,0,null,CRDT_ACC_NUM) CRDT_ACC_NUM,"
						+ "REMITTANCE_CURR,REMITTANCE_AMT,BILL_LN_REFRNCE,LOAN_CURR,TO_CHAR(BASE_DOC_DT,'YYYY-MM-DD') BASE_DOC_DT,"
						+ "TO_CHAR(MATURITY_DATE,'YYYY-MM-DD') MATURITY_DATE ,"
						+ "PMNT_AUTH_OTH_SYS,FND_TRNSFER_FCUBS_REF,PMNT_HUB_REF,UDF_FIELD_CHK,DECODE(BILL_CUST_HLDING_ACC_US,0,null,BILL_CUST_HLDING_ACC_US) BILL_CUST_HLDING_ACC_US, OTHER_TRAN_REF_NUM, ISSUING_CENTER,BRN_CODE,BILL_TYPE ,IF_SIGHT_BILL,DISCREPANCY_INSTRUCTION,SETTLEMENT_INSTRUCTION ,STANDALONE_LOAN,PROCESSING_SYS  FROM TFO_WMS_MASTER_IF "  //Sprint_US_153 //Sprint_US_154
						+ "WHERE "
						+ whereConditionField
						+ "='"
						+ refNo
						+ "' AND REQ_CAT='" + sRequestCategory + "'";
				log.info("Trns Detls query " + strQ);
				sOutputlist = formObject.getDataFromDB(strQ);
			} else if ("ELC".equalsIgnoreCase(sRequestCategory)
					|| "ELCB_BL".equalsIgnoreCase(requestType)
					|| "SG_NILC".equalsIgnoreCase(requestType)) { //Shipping_Gtee_10        // Export LC
				if ("ELCB_BL".equalsIgnoreCase(requestType)) {
					sRequestCategory = "ELC";
				}
				strQ = "SELECT GTEE_NUMBER,CUSTOMER_ID,CURRENCY,AMOUNT,TRNS_BRN_CODE,"
						+ "TRNS_BAL,IS_ACTIVE,PRODUCT,DECODE(IS_VALID_ACC,0,null,IS_VALID_ACC) IS_VALID_ACC,DECODE(ACCOUNT_NUMBER,0,null,ACCOUNT_NUMBER) ACCOUNT_NUMBER,TO_CHAR(EXP_DATE,'YYYY-MM-DD') EXP_DATE1,"
						+ "DECODE(TRNS_THIRD_PARTY,0,null,TRNS_THIRD_PARTY) TRNS_THIRD_PARTY,LC_CORRSPNDNT_BNK,LC_CONF_AMNT, OTHER_TRAN_REF_NUM, ISSUING_CENTER,BRN_CODE FROM TFO_WMS_MASTER_IELC "
						+ "WHERE "
						+ whereConditionField
						+ "='"
						+ refNo
						+ "' AND REQ_CAT='" + sRequestCategory + "'";
				log.info("ELC Trns Detls query " + strQ);
				sOutputlist = formObject.getDataFromDB(strQ);
			} else if ("ILC".equalsIgnoreCase(sRequestCategory)
					|| "ILCB_BL".equalsIgnoreCase(requestType)) { // Import LC
				if ("ILCB_BL".equalsIgnoreCase(requestType)) {
					sRequestCategory = "ILC";
				}
				strQ = "SELECT GTEE_NUMBER,CUSTOMER_ID,CURRENCY,AMOUNT,TRNS_BRN_CODE,"
						+ "TRNS_BAL,IS_ACTIVE,PRODUCT,DECODE(IS_VALID_ACC,0,null,IS_VALID_ACC) IS_VALID_ACC,DECODE(ACCOUNT_NUMBER,0,null,ACCOUNT_NUMBER) ACCOUNT_NUMBER,TO_CHAR(EXP_DATE,'YYYY-MM-DD') EXP_DATE1,"
						+ "DECODE(TRNS_THIRD_PARTY,0,null,TRNS_THIRD_PARTY) TRNS_THIRD_PARTY,LC_CORRSPNDNT_BNK,LC_CONF_AMNT, OTHER_TRAN_REF_NUM, ISSUING_CENTER,BRN_CODE   FROM TFO_WMS_MASTER_IELC "
						+ "WHERE "
						+ whereConditionField
						+ "='"
						+ refNo
						+ "' AND REQ_CAT='" + sRequestCategory + "'";
				log.info("ILC Trns Detls query " + strQ);
				sOutputlist = formObject.getDataFromDB(strQ);
			} else if ("ILCB".equalsIgnoreCase(sRequestCategory)
					|| "ELCB".equalsIgnoreCase(sRequestCategory)
					|| "EC".equalsIgnoreCase(sRequestCategory)
					|| "IC".equalsIgnoreCase(sRequestCategory)
					|| "DBA".equalsIgnoreCase(sRequestCategory)) {
				strQ = "SELECT GTEE_NUMBER,CUSTOMER_ID,CURRENCY,AMOUNT,TRNS_BRN_CODE,TRNS_BAL,"
						+ "IS_ACTIVE,PRODUCT,TENOR_DAYS,DECODE(TENOR_BASE,'0',null,TENOR_BASE) TENOR_BASE,TENOR_BASE_DTLS,TO_CHAR(BASE_DOC_DT,'YYYY-MM-DD') BASE_DOC_DT,"
						+ "TO_CHAR(MATURITY_DATE,'YYYY-MM-DD') MATURITY_DATE,DECODE(IS_VALID_ACC,0,null,IS_VALID_ACC) IS_VALID_ACC,DECODE(STL_ACC_NUMBER,0,null,STL_ACC_NUMBER) STL_ACC_NUMBER,DECODE(CHRG_ACC_NUMBER,0,null,CHRG_ACC_NUMBER) CHRG_ACC_NUMBER,"
						+ "PAYMENT_MODE,OUR_LC_REF_NO,BILLS_CORRSPNDNT_BNK, OTHER_TRAN_REF_NUM, ISSUING_CENTER,BRN_CODE,BILL_TYPE,IF_SIGHT_BILL,DISCREPANCY_INSTRUCTION,SETTLEMENT_INSTRUCTION   FROM TFO_WMS_MASTER_IECLCB  " 
						+ "WHERE "
						+ whereConditionField
						+ "='"
						+ refNo
						+ "' AND REQ_CAT='" + sRequestCategory + "'";
				log.info("Trns Detls query " + strQ);
				sOutputlist = formObject.getDataFromDB(strQ);
			}
			log.info("OutputList " + sOutputlist);

			if (("GRNT".equalsIgnoreCase(sRequestCategory)
					|| "SBLC".equalsIgnoreCase(sRequestCategory) //RR
					|| ("ELC".equalsIgnoreCase(sRequestCategory) && ("ELC_SLCA".equalsIgnoreCase(requestType) 
							|| "ELC_SLCAA".equalsIgnoreCase(requestType)
							|| "ELC_SER".equalsIgnoreCase(requestType)|| "ELC_SCL".equalsIgnoreCase(requestType)))//added by mansi
							|| "IFP".equalsIgnoreCase(sRequestCategory)
							|| "IFS".equalsIgnoreCase(sRequestCategory)
							|| "IFA".equalsIgnoreCase(sRequestCategory) //ADDED BY KISHAN
							|| "SCF".equalsIgnoreCase(sRequestCategory) //ADDED FOR SCF REQ
							|| "TL".equalsIgnoreCase(sRequestCategory)
							||"SG_SD".equalsIgnoreCase(requestType))
							&& otherRefNum
							&& sOutputlist != null
							&& !sOutputlist.isEmpty()
							&& !sOutputlist.get(0).isEmpty()) {
				retMsg = setTransactionData(sOutputlist, sRequestCategory,
						transFieldName, transRefOutFieldName, requestType,
						otherRefNum);
			} else if (("GRNT".equalsIgnoreCase(sRequestCategory)
					|| "SBLC".equalsIgnoreCase(sRequestCategory)//RR
					|| ("ELC".equalsIgnoreCase(sRequestCategory) && ("ELC_SLCA".equalsIgnoreCase(requestType) 
							|| "ELC_SLCAA".equalsIgnoreCase(requestType)
							|| "ELC_SER".equalsIgnoreCase(requestType)|| "ELC_SCL".equalsIgnoreCase(requestType)))//added by mansi
							|| "IFP".equalsIgnoreCase(sRequestCategory)
							|| "IFS".equalsIgnoreCase(sRequestCategory) 
							|| "IFA".equalsIgnoreCase(sRequestCategory)	//ADDED BY KISHAN
							|| "SCF".equalsIgnoreCase(sRequestCategory) //ADDED FOR SCF REQ
							|| "TL".equalsIgnoreCase(sRequestCategory)
							|| "SG_SD".equalsIgnoreCase(requestType)) && !otherRefNum) { //Shipping_Gtee_11
				if (sOutputlist != null && !sOutputlist.isEmpty()&& !sOutputlist.get(0).isEmpty()) {
					retMsg = setTransactionData(sOutputlist, sRequestCategory,
							transFieldName, transRefOutFieldName, requestType,
							otherRefNum);
				} else {
					retMsg = setTransactionDataFCUBS(sOutputlist, sRequestCategory,
							transFieldName, transRefOutFieldName, requestType,
							otherRefNum);
				}
			} else if (("EC".equalsIgnoreCase(sRequestCategory)
					|| "IC".equalsIgnoreCase(sRequestCategory)
					|| "DBA".equalsIgnoreCase(sRequestCategory)
					|| "ILC".equalsIgnoreCase(sRequestCategory)
					|| "ELC".equalsIgnoreCase(sRequestCategory)
					|| "ILCB".equalsIgnoreCase(sRequestCategory) 
					|| "ELCB".equalsIgnoreCase(sRequestCategory)
					|| "SG".equalsIgnoreCase(sRequestCategory))  //Shipping_Gtee_10
					&& otherRefNum
					&& sOutputlist != null
					&& !sOutputlist.isEmpty()
					&& !sOutputlist.get(0).isEmpty()) {
				log.info(" else if case1" + sOutputlist);
				retMsg = setTransactionDataLc(sOutputlist, sRequestCategory,
						transFieldName, transRefOutFieldName, requestType,
						otherRefNum);
			} else if (("EC".equalsIgnoreCase(sRequestCategory)
					|| "IC".equalsIgnoreCase(sRequestCategory)
					|| "DBA".equalsIgnoreCase(sRequestCategory)
					|| "ILC".equalsIgnoreCase(sRequestCategory)
					|| "ELC".equalsIgnoreCase(sRequestCategory)
					|| "ILCB".equalsIgnoreCase(sRequestCategory)
					|| "ELCB".equalsIgnoreCase(sRequestCategory)
					|| "SG".equalsIgnoreCase(sRequestCategory))
					&& !otherRefNum) { ////Shipping_Gtee_10
				if (sOutputlist != null && !sOutputlist.isEmpty()
						&& !sOutputlist.get(0).isEmpty()) {
					log.info(" if case " + sOutputlist);
					retMsg = setTransactionDataLc(sOutputlist, sRequestCategory,
							transFieldName, transRefOutFieldName, requestType,
							otherRefNum);
				} else {
					log.info(" if case " + sOutputlist);
					retMsg = setTransactionDataLcFCUBS(sOutputlist, sRequestCategory,
							transFieldName, transRefOutFieldName, requestType,
							otherRefNum);
				}
			} else { 
				retMsg = "Please enter correct Transaction Ref. Number";
			}
		} catch (Exception e) {
			log.error("exception in fetchTransactionDtls: "+e,e);
		}
		log.info("return message from fetchTransactionDtls: "+retMsg);
		return retMsg;
	}

	private String setTransactionData(List<List<String>> recordList,String reqCat, String transFieldName,
			String transRefOutFieldName,String requestType, boolean otherRefNum) {
		log.info("transFieldName: " + transFieldName + "transRefOutFieldName: "+ transRefOutFieldName);
		String retMsg = "";
		if(transRefOutFieldName.contains("TXT_")){
			transRefOutFieldName = transRefOutFieldName.replace("TXT_", "");
			log.info("new transRefOutFieldName: "+transRefOutFieldName);
		}
		if(transFieldName.contains("TXT_")){
			transFieldName = transFieldName.replace("TXT_", "");
			log.info("new transFieldName: "+transFieldName);
		}
		log.info("recordList" + recordList.get(0).size());
		try {
			log.info("Start Setting data in setTransactionData"+ recordList.get(0).get(0) + " recordList.get(0).get(13) "
					+ recordList.get(0).get(13));
			formObject.setValue("PROFIT_CENTER_CODE",(recordList.get(0)).get(2));// PROFIT_CENTER_CODE
			formObject.setValue("CUSTOMER_CATEGORY", (recordList.get(0)).get(3));// CUSTOMER_CATEGORY
			formObject.setValue(FCR_CUST_EMAIL_ID, (recordList.get(0)).get(4));// EMAIL_ID
			formObject.setValue("MOBILE_NUMBER", (recordList.get(0)).get(5));// MOBILE_NUMBER
			formObject.setValue("ADDRESS_LINE_1", (recordList.get(0)).get(6));// ADDRESS_LINE_1
			formObject.setValue("ADDRESS_LINE_2", (recordList.get(0)).get(7));// ADDRESS_LINE_2
			formObject.setValue("EMIRATES", (recordList.get(0)).get(8));// EMIRATES
			formObject.setValue("PO_BOX", (recordList.get(0)).get(9));// PO_BOX
			formObject.setValue("RM_NAME", (recordList.get(0)).get(10));// RM_NAME
			formObject.setValue("RM_MOBILE_NUMBER", (recordList.get(0)).get(11));// RM_MOBILE_NUMBER
			formObject.setValue(transFieldName, (recordList.get(0)).get(12));// GTEE_NUMBER
			formObject.setValue("IS_CUSTOMER_VIP", (recordList.get(0)).get(15));// IS_CUSTOMER_VIP
			formObject.setValue(PRODUCT_TYPE, (recordList.get(0)).get(16));// PRODUCT
			formObject.setValue(RELATIONSHIP_TYPE,(recordList.get(0)).get(17));// TRNS_BRN_CODE
			if ("GRNT".equalsIgnoreCase(reqCat) && ("NI".equalsIgnoreCase(requestType)
					|| "GA".equalsIgnoreCase(requestType))) {
				formObject.setValue(TRNS_BAL, (recordList.get(0)).get(18));// OS_AMT
				formObject.setValue(TRN_STATUS, (recordList.get(0)).get(19)); // IS_ACTIVE
				formObject.setValue(TRANSACTION_CURRENCY,(recordList.get(0)).get(13));// CURRENCY
				formObject.setValue(TRANSACTION_AMOUNT,(recordList.get(0)).get(14));// AMOUNT
			} else if ("SBLC".equalsIgnoreCase(reqCat) && ("SBLC_NI".equalsIgnoreCase(requestType))){ //added by Mansi
				formObject.setValue(TRNS_BAL, (recordList.get(0)).get(18));// OS_AMT
				formObject.setValue(TRN_STATUS, (recordList.get(0)).get(19)); // IS_ACTIVE
				formObject.setValue(TRANSACTION_CURRENCY,(recordList.get(0)).get(13));// CURRENCY
				formObject.setValue(TRANSACTION_AMOUNT,(recordList.get(0)).get(14));// AMOUNT
			}else if ("ELC".equalsIgnoreCase(reqCat) && ("ELC_SLCA".equalsIgnoreCase(requestType))) {//added by mansi
				formObject.setValue(TRNS_BAL, (recordList.get(0)).get(18));// OS_AMT
				formObject.setValue(TRN_STATUS, (recordList.get(0)).get(19)); // IS_ACTIVE
				formObject.setValue(TRANSACTION_CURRENCY,(recordList.get(0)).get(13));// CURRENCY
				formObject.setValue(TRANSACTION_AMOUNT,(recordList.get(0)).get(14));// AMOUNT
			}else if (!"GRNT".equalsIgnoreCase(reqCat) && !"SBLC".equalsIgnoreCase(reqCat) 
					&& (!("ELC".equalsIgnoreCase(reqCat) && 
						 ("ELC_SLCA".equalsIgnoreCase(requestType) || "ELC_SLCAA".equalsIgnoreCase(requestType)
							|| "ELC_SER".equalsIgnoreCase(requestType)|| "ELC_SCL".equalsIgnoreCase(requestType))))
							) {//added by mansi
				log.info("INSIDE  CONDITION:noor GRNT SBLC ELC new req Type ");
				formObject.setValue(TRNS_BAL, (recordList.get(0)).get(18));// OS_AMT
				formObject.setValue(TRN_STATUS, (recordList.get(0)).get(19)); // IS_ACTIVE
				formObject.setValue(TRANSACTION_CURRENCY,(recordList.get(0)).get(13));// CURRENCY
				formObject.setValue(TRANSACTION_AMOUNT,(recordList.get(0)).get(14));// AMOUNT
			}
			if ("GRNT".equalsIgnoreCase(reqCat) || "SBLC".equalsIgnoreCase(reqCat) || ("ELC".equalsIgnoreCase(reqCat)&& ("ELC_SLCA".equalsIgnoreCase(requestType) || "ELC_SLCAA".equalsIgnoreCase(requestType)
					|| "ELC_SER".equalsIgnoreCase(requestType)|| "ELC_SCL".equalsIgnoreCase(requestType)))) {//added by mansi
				formObject.setValue(EXP_DATE, (recordList.get(0)).get(20)); // EXP_DATE1
				formObject.setValue(TRN_TENOR, (recordList.get(0)).get(21));// TRNS_TENOR
				formObject.setValue(ACCOUNT_VALID, (recordList.get(0)).get(22));// IS_VALID_ACC
				formObject.setValue(TRN_THIRD_PARTY,(recordList.get(0)).get(23));// TRNS_THIRD_PARTY
				formObject.setValue(ACCOUNT_NUMBER,(recordList.get(0)).get(24));// ACCOUNT_NUMBER
				formObject.setValue(transRefOutFieldName,(recordList.get(0)).get(25));// OTHER_TRAN_REF_NUM
				if(recordList.get(0).get(26) != null && !recordList.get(0).get(26).equalsIgnoreCase("")){
					formObject.setValue(TFO_BRANCH_CITY, (recordList.get(0)).get(26));// ISSUING_CENTER
					formObject.setValue(TFO_ASSIGNED_CENTER,(recordList.get(0)).get(26));// PROCESSING_CENTER same
				}
			} else if (("IFS".equalsIgnoreCase(reqCat))
					|| ("IFP".equalsIgnoreCase(reqCat))
					|| ("IFA".equalsIgnoreCase(reqCat))    //BY KISHAN
					|| ("SCF".equalsIgnoreCase(reqCat))    //BY SHIVANSHU FOR SCF ATP 204
					|| ("TL".equalsIgnoreCase(reqCat))) {
				log.info("INSIDE IFA CONDITION: ");
				formObject.setValue(INF_TENOR_DAYS,(recordList.get(0)).get(20));// TENOR_DAY
				if("TL_LD".equalsIgnoreCase(requestType)){ //US147		
					formObject.setValue(INF_TENOR_BASE,"OTH");// TENOR_BASE  //US147
				}else{
					formObject.setValue(INF_TENOR_BASE,(recordList.get(0)).get(21));// TENOR_BASE
				}
				log.info("INSIDE IFA CONDITION: cid	"+recordList.get(0).get(0));

				formObject.setValue(CUSTOMER_ID,(recordList.get(0)).get(0));// TENOR_BASE_DTLS
				log.info("CID VALUE	"+formObject.getValue(CUSTOMER_ID));

				formObject.setValue(INF_ACTUAL_TENOR_BASE,(recordList.get(0)).get(22));// TENOR_BASE_DTLS
				formObject.setValue(INF_CHARGE_ACC_NUM,(recordList.get(0)).get(23));// CHARGE_ACC_NUM
				formObject.setValue(INF_SETTLEMENT_ACC_NUM,(recordList.get(0)).get(24));// STTLMNT_ACC_NUM
				formObject.setValue(INF_CREDIT_ACC_NUM,(recordList.get(0)).get(25));// CRDT_ACC_NUM
				formObject.setValue("REMITTANCE_CURR",(recordList.get(0)).get(26));// REMITTANCE_CURR
				formObject.setValue("REMITTANCE_AMT",(recordList.get(0)).get(27));// REMITTANCE_AMT
				formObject.setValue(BILL_LN_REFRNCE,(recordList.get(0)).get(28));// BILL_LN_REFRNCE
				formObject.setValue(INF_LOAN_CURR,(recordList.get(0)).get(29));// LOAN_CURR
				formObject.setValue("PMNT_AUTH_OTH_SYS",(recordList.get(0)).get(32));// PMNT_AUTH_OTH_SYS
				formObject.setValue("FND_TRNSFER_FCUBS_REF",(recordList.get(0)).get(33));// FND_TRNSFER_FCUBS_REF
				formObject.setValue("PMNT_HUB_REF", (recordList.get(0)).get(34));// PMNT_HUB_REF
				formObject.setValue("UDF_FIELD_CHK",(recordList.get(0)).get(35));// UDF_FIELD_CHK
				formObject.setValue(BILL_CUST_HLDING_ACC_US,(recordList.get(0)).get(36));// BILL_CUST_HLDING_ACC_US
				formObject.setValue(transRefOutFieldName,(recordList.get(0)).get(37));// OTHER_TRAN_REF_NUM

				//log.info("INSIDE IFA CONDITION: CHECK 1");
				formObject.setValue(STANDALONE_LOAN,(recordList.get(0)).get(44));  //ADDED BY KISHAN
				//log.info("INSIDE IFA CONDITION: CHECK 2");
				if(recordList.get(0).get(45) != null && !recordList.get(0).get(45).equalsIgnoreCase("")){
					formObject.setValue(PROCESSING_SYSTEM,(recordList.get(0)).get(45)); //ADDED BY KISHAN
				//log.info("INSIDE IFA CONDITION: CHECK 3");
				} else {
					formObject.setValue(PROCESSING_SYSTEM,""); //ADDED BY KISHAN
				}
				if(recordList.get(0).get(38) != null && !recordList.get(0).get(38).equalsIgnoreCase("")){
					formObject.setValue(TFO_BRANCH_CITY, (recordList.get(0)).get(38));// ISSUING_CENTER
					formObject.setValue(TFO_ASSIGNED_CENTER,(recordList.get(0)).get(38));
				}
				try {
					log.info("Dates " + ((recordList.get(0)).get(30)));
					log.info("Dates " + ((recordList.get(0)).get(31)));
					formObject.setValue(INF_BASE_DOC_DATE, recordList.get(0).get(30));
					formObject.setValue(INF_MATURITY_DATE, recordList.get(0).get(31));
				} catch (Exception localException) {
					log.error("localException: "+localException,localException);
				}
				setCPDDetails(reqCat);
			} else if("SG_SD".equalsIgnoreCase(requestType)){	//Shipping_Gtee_11
				log.info("In setTransaction Data for SG_Scan Document");
				formObject.setValue(transRefOutFieldName,(recordList.get(0)).get(25));// OTHER_TRAN_REF_NUM
				if(!recordList.get(0).get(26).equalsIgnoreCase("")){
					formObject.setValue(TFO_BRANCH_CITY,(recordList.get(0)).get(26));// ISSUING_CENTER
					formObject.setValue(TFO_ASSIGNED_CENTER,(recordList.get(0)).get(26));// PROCESSING_CENTER
				}
			}
		} catch (Exception e1){
			log.error("Exception in setting value from outputlist setTransactionData GTEE "+ e1);
		}
		String loan_grp = formObject.getValue(IFS_LOAN_GRP).toString();
		if(("TL_LD".equalsIgnoreCase(requestType) && ("ILCB".equalsIgnoreCase(loan_grp)
				||"IC".equalsIgnoreCase(loan_grp)))){
			String sQuery = "UPDATE EXT_TFO SET BILL_TYPE='"+(recordList.get(0)).get(40)+"',"
					+ "IF_SIGHT_BILL='"+(recordList.get(0)).get(41)+"'"
					+",DISCREPANCY_INSTRUCTION='"+(recordList.get(0)).get(42)+"',SETTLEMENT_INSTRUCTION='"+(recordList.get(0)).get(43)+"'"
					+" WHERE WI_NAME='"+sWorkitemID+"'";
			log.info("Saving in ext table records query : "+sQuery);
			formObject.saveDataInDB(sQuery);
			log.info("AFTER SAVE OF CUSTOMWER IMPORT FRAME : ");
		}
		log.info("set transacntion data recordlist " + recordList);
		retMsg = setTransactionDataFCUBS(recordList, reqCat, transFieldName,
				transRefOutFieldName, requestType, otherRefNum);
		log.info("Set data without issue- CPD");
		return retMsg;
	}

	private String setTransactionDataFCUBS(List<List<String>> recordList,
			String reqCat, String transFieldName, String transRefOutFieldName,
			String requestType, boolean otherRefNum) {
		log.info("transFieldName: " + transFieldName + "transRefOutFieldName: "+ transRefOutFieldName);
		String retMsg = "";
		if(transRefOutFieldName.contains("TXT_")){
			transRefOutFieldName = transRefOutFieldName.replace("TXT_", "");
			log.info("new transRefOutFieldName: "+transRefOutFieldName);
		}
		if(transFieldName.contains("TXT_")){
			transFieldName = transFieldName.replace("TXT_", "");
			log.info("new transFieldName: "+transFieldName);
		}
		try {
			String strQ = "";
			String sFetchedBranchCode = "";
			String sContractRefNo = "";
			List<List<String>> sOutputlist = null;
			if ("GRNT".equalsIgnoreCase(reqCat)||"SBLC".equalsIgnoreCase(reqCat)//RR
					|| "IFS".equalsIgnoreCase(reqCat)|| "ELC".equalsIgnoreCase(reqCat) && ("ELC_SLCA".equalsIgnoreCase(requestType) ||"ELC_SLCAA".equalsIgnoreCase(requestType)
							|| "ELC_SER".equalsIgnoreCase(requestType)|| "ELC_SCL".equalsIgnoreCase(requestType))
							||"SG".equalsIgnoreCase(reqCat)|| "IFP".equalsIgnoreCase(reqCat) 
							|| "IFA".equalsIgnoreCase(reqCat)|| "TL".equalsIgnoreCase(reqCat)) { 
				if (otherRefNum) {
					log.info("set transacntion data otherRefNum " + otherRefNum+ " reqCat " + reqCat);
					if ("IFS".equalsIgnoreCase(reqCat)|| "IFP".equalsIgnoreCase(reqCat) 
							|| "TL".equalsIgnoreCase(reqCat) || "IFA".equalsIgnoreCase(reqCat) ) { //CODE BY MOKSH
						sFetchedBranchCode = recordList.get(0).get(39).equals("")
								? formObject.getValue(transFieldName).toString().substring(0, 3)
										: recordList.get(0).get(39);
					} else if ("GRNT".equalsIgnoreCase(reqCat)||"SBLC".equalsIgnoreCase(reqCat)
							|| "ELC".equalsIgnoreCase(reqCat) && ("ELC_SLCA".equalsIgnoreCase(requestType) 
									|| "ELC_SLCAA".equalsIgnoreCase(requestType)
									|| "ELC_SER".equalsIgnoreCase(requestType)|| "ELC_SCL".equalsIgnoreCase(requestType))
									|| "SG".equalsIgnoreCase(reqCat)) { //RR //Shipping_Gtee_11
						sFetchedBranchCode = recordList.get(0).get(27).equals("")
								? formObject.getValue("TFO_" + transFieldName).toString()
										.substring(0, 3) : recordList.get(0).get(27);
					}else if ("ELC".equalsIgnoreCase(reqCat)&& ("ELC_SLCA".equalsIgnoreCase(requestType) ||"ELC_SLCAA".equalsIgnoreCase(requestType)
							|| "ELC_SER".equalsIgnoreCase(requestType)|| "ELC_SCL".equalsIgnoreCase(requestType))) { //added by MAnsi
						sFetchedBranchCode = recordList.get(0).get(27).equals("")
								? formObject.getValue("TFO_" + transFieldName).toString()
										.substring(0, 3) : recordList.get(0).get(27);
					}

					sContractRefNo = formObject.getValue(transFieldName).toString();
				} else {
					log.info("set transacntion data otherRefNum " + otherRefNum+ " reqCat " + reqCat);
					if ("IFS".equalsIgnoreCase(reqCat)|| "IFP".equalsIgnoreCase(reqCat)
							|| "TL".equalsIgnoreCase(reqCat) || "IFA".equalsIgnoreCase(reqCat) ) {	//CODE BY MOKSH
						if (recordList != null && !recordList.isEmpty() && !formObject.getValue(transFieldName)
								.toString().equalsIgnoreCase("")) {
							sContractRefNo = formObject.getValue(transFieldName).toString();
							sFetchedBranchCode = recordList.get(0).get(39).equals("") ? sContractRefNo.substring(0, 3) : recordList.get(0)
									.get(39);
						} else {
							log.info("IFS TRNSREFNO "+ formObject.getValue(TRNS_REF_NO));
							if("TL_LD".equalsIgnoreCase(requestType)){
								sContractRefNo=formObject.getValue(BILL_LN_REFRNCE).toString();
							}else{
								sContractRefNo = formObject.getValue(TRNS_REF_NO).toString();
							}
							sFetchedBranchCode = sContractRefNo.substring(0, 3);
						}
					} else if ("GRNT".equalsIgnoreCase(reqCat)||"SBLC".equalsIgnoreCase(reqCat)) {//RR
						if (recordList != null && !recordList.isEmpty() && !formObject.getValue(transFieldName).toString().equalsIgnoreCase("")) {
							sContractRefNo = formObject.getValue(transFieldName).toString();
							sFetchedBranchCode = recordList.get(0).get(27).equals("") ? sContractRefNo.substring(0, 3) : recordList.get(0).get(27);
						} else {
							log.info("GRNT TRNSREFNO "+ formObject.getValue(TRNS_REF_NO));
							sContractRefNo = formObject.getValue(TRNS_REF_NO).toString();
							sFetchedBranchCode = sContractRefNo.substring(0, 3).toString();
						}
					} else if("ELC".equalsIgnoreCase(reqCat)&& "ELC_SLCA".equalsIgnoreCase(requestType) ||"ELC_SLCAA".equalsIgnoreCase(requestType)
							|| "ELC_SER".equalsIgnoreCase(requestType)|| "ELC_SCL".equalsIgnoreCase(requestType)) {//RR
						if (recordList != null && !recordList.isEmpty() && !formObject.getValue(transFieldName).toString().equalsIgnoreCase("")) {
							sContractRefNo = formObject.getValue(transFieldName).toString();
							sFetchedBranchCode = recordList.get(0).get(27).equals("") ? sContractRefNo.substring(0, 3) : recordList.get(0).get(27);
						} else {
							log.info("GRNT TRNSREFNO "+ formObject.getValue(TRNS_REF_NO));
							sContractRefNo = formObject.getValue(TRNS_REF_NO).toString();
							sFetchedBranchCode = sContractRefNo.substring(0, 3).toString();
						}
					}else if ("SG".equalsIgnoreCase(reqCat)) {  //Shipping_Gtee_11
						if (recordList != null && !recordList.isEmpty() && !formObject.getValue(transFieldName).toString().equalsIgnoreCase("")) {
							sContractRefNo = formObject.getValue(transFieldName).toString();
							sFetchedBranchCode = recordList.get(0).get(27).equals("") ? sContractRefNo.substring(0, 3) : recordList.get(0).get(27);
						} else {
							log.info("SG TRNSREFNO "+ formObject.getValue(TRNS_REF_NO));
							sContractRefNo = formObject.getValue(TRNS_REF_NO).toString();
							sFetchedBranchCode = sContractRefNo.substring(0, 3).toString();
						}
					}

				}
				formObject.setValue(transFieldName, sContractRefNo);
				log.info("sFetchedBranchCode " + sFetchedBranchCode);
				if (otherRefNum && recordList != null) {
					if (recordList.get(0).get(17) == null || recordList.get(0).get(17).equals("")) {
						strQ = "SELECT RELATIONSHIP_TYPE FROM TFO_DJ_BRN_REL_TYPE_MAP_MAST WHERE BRANCH_CODE='" + sFetchedBranchCode + "'";
						sOutputlist = formObject.getDataFromDB(strQ);
						log.info("Relationship Type returned " + sOutputlist.get(0).get(0));
						formObject.setValue(RELATIONSHIP_TYPE, sOutputlist.get(0).get(0));
						sOutputlist = null;
					}
				} else {
					strQ = "SELECT RELATIONSHIP_TYPE FROM TFO_DJ_BRN_REL_TYPE_MAP_MAST WHERE "+ "BRANCH_CODE='" + sFetchedBranchCode + "'";
					sOutputlist = formObject.getDataFromDB(strQ);
					log.info("Relationship Type returned "+ sOutputlist.get(0).get(0).toString());
					formObject.setValue(RELATIONSHIP_TYPE, sOutputlist.get(0).get(0).toString());
					sOutputlist = null;
				}
				log.info("strQ relationship type query" + strQ);

          //    date-4/4/2022 changed for enabling processing system(Ifs,Ifp,Ifa)
          //	if(("IFS".equalsIgnoreCase(reqCat)|| "IFP".equalsIgnoreCase(reqCat) || "IFA".equalsIgnoreCase(reqCat))){
					//formObject.setValue(PROCESSING_SYSTEM, "");
			//	}
				String sProcessing_sys = formObject.getValue(PROCESSING_SYSTEM).toString();
				if(sProcessing_sys.equalsIgnoreCase("F") || sProcessing_sys.equalsIgnoreCase("")  ){//CODE BY KISHAN FOR TSLM CHANGE	
					if (("IC_MSM".equalsIgnoreCase(requestType))
							|| ("EC_MSM".equalsIgnoreCase(requestType))
							|| ("ILC_MSM".equalsIgnoreCase(requestType))
							|| ("ELC_MSM".equalsIgnoreCase(requestType))
							|| ("ILCB_MSM".equalsIgnoreCase(requestType))
							|| ("ELCB_MSM".equalsIgnoreCase(requestType))
							|| ("GRNT_MSM".equalsIgnoreCase(requestType))
							|| ("TL_MSM".equalsIgnoreCase(requestType))
							|| ("DBA_MSM".equalsIgnoreCase(requestType))
							|| ("MISC_MSM".equalsIgnoreCase(requestType))
							|| ("IFS_MSM".equalsIgnoreCase(requestType))
							|| ("IFP_MSM".equalsIgnoreCase(requestType))
							|| ("SBLC_MSM".equalsIgnoreCase(requestType))) {//added by mansi
						strQ = "SELECT OPERATION_NAME FROM TFO_DJ_INQUIRY_OPERATION_MST WHERE "
								+ "REQUEST_CATEGORY_CODE='ALL' AND REQUEST_TYPE_CODE = 'MSM' AND BRANCH_CODE='"
								+ formObject.getValue(RELATIONSHIP_TYPE)
								+ "'AND FETCH_MODULE='"
								+ formObject.getValue(SWIFT_FETCH_MODULE)
								+ "' AND PROCESS_TYPE='"
								+ formObject.getValue(PROCESS_TYPE) + "'";
					} else {
						strQ = "SELECT OPERATION_NAME FROM TFO_DJ_INQUIRY_OPERATION_MST WHERE "
								+ "REQUEST_CATEGORY_CODE='"
								+ reqCat
								+ "' AND REQUEST_TYPE_CODE = '"
								+ formObject.getValue(REQUEST_TYPE)
								+ "' AND BRANCH_CODE='"
								+ formObject.getValue(RELATIONSHIP_TYPE)
								+ "' AND PROCESS_TYPE='"
								+ formObject.getValue(PROCESS_TYPE) + "'";
					}
					log.info("Operation name Detls query " + strQ);
					sOutputlist = formObject.getDataFromDB(strQ);
					log.info("Operation Name returned "+ sOutputlist.get(0).get(0).toString());
					if (!sOutputlist.equals(null)) {
						if (!sOutputlist.get(0).get(0).equalsIgnoreCase("NA")) {
							String returnFetchMessage[] = fetchCustomerContractData(sContractRefNo, sOutputlist.get(0).get(0).toString(), 
									sFetchedBranchCode,reqCat, requestType).split("##");
							int statusCode = Integer.parseInt(returnFetchMessage[0]);
							log.info("returnFetchMessage n status: "+returnFetchMessage.toString()+"->"+statusCode);
							if(returnFetchMessage.length>1){
								retMsg = returnFetchMessage[1];
							}
							if (statusCode == 2) {
								if (formObject.getValue(CUSTOMER_ID).equals("")) {
									//JOptionPane.showMessageDialog(null,"Unable to fetch Contract details. Kindly Enter correct Reference Number.");
									retMsg = "Unable to fetch Contract details. Kindly Enter correct Reference Number.";
								} else {
									retMsg = fetchCustomerData(formObject.getValue(CUSTOMER_ID).toString(),
											formObject.getValue(REQUEST_CATEGORY).toString(), true);
								}
							}
						} else {
							retMsg = fetchCustomerData(recordList.get(0).get(0), formObject
									.getValue(REQUEST_CATEGORY).toString(), false);
						}
					} else {
						retMsg = fetchCustomerData(recordList.get(0).get(0), formObject
								.getValue(REQUEST_CATEGORY).toString(), false);
					}
				}else{ //ELSE CONDITION FOR HANDLING TSLM CODE
					populateTSLMLoanDetails( sContractRefNo);
				}
			} else {
				retMsg = fetchCustomerData(recordList.get(0).get(0), formObject
						.getValue(REQUEST_CATEGORY).toString(), true);
			}
		} catch (Exception e) {
			log.error("Fetch inquiry service excp " + e,e);
		}
		return retMsg;
	}

	private String setTransactionDataLc(List<List<String>> recordList,
			String reqCat, String transFieldName, String transRefOutFieldName,
			String requestType, boolean otherRefNum) {
		log.info("inside setTransactionDataLc >>");
		String retMsg = "";
		if(transRefOutFieldName.contains("TXT_")){
			transRefOutFieldName = transRefOutFieldName.replace("TXT_", "");
			log.info("new transRefOutFieldName: "+transRefOutFieldName);
		}
		if(transFieldName.contains("TXT_")){
			transFieldName = transFieldName.replace("TXT_", "");
			log.info("new transFieldName: "+transFieldName);
		}
		try {
			try {
				formObject.setValue(transFieldName, (recordList.get(0)).get(0));// Gtee
				formObject.setValue(RELATIONSHIP_TYPE,
						(recordList.get(0)).get(4));// TRNS_BRN_CODE
				if (!"ILCB_BL".equalsIgnoreCase(requestType)
						&& !"ELCB_BL".equalsIgnoreCase(requestType)) {
					if (("ELC".equalsIgnoreCase(reqCat) || "ILC".equalsIgnoreCase(reqCat)) 
							&& ("ILC_NI".equalsIgnoreCase(requestType) || "ELC_LCA".equalsIgnoreCase(requestType))) {
						formObject.setValue(TRANSACTION_CURRENCY,
								(recordList.get(0)).get(2));// Currency
						formObject.setValue(TRANSACTION_AMOUNT,
								(recordList.get(0)).get(3));// Amount
						formObject.setValue(TRNS_BAL,
								(recordList.get(0)).get(5));// TRNS_BAL -
						// LC_OS_AMT
						formObject.setValue(TRN_STATUS,
								(recordList.get(0)).get(6)); // IS_ACTIVE
					} else if (!("ELC".equalsIgnoreCase(reqCat) && "ILC".equalsIgnoreCase(reqCat))
							&& ("DBA_BL".equalsIgnoreCase(requestType)
									|| "EC_BL".equalsIgnoreCase(requestType)
									|| "EC_LDDI".equalsIgnoreCase(requestType) || "IC_BL"
									.equalsIgnoreCase(requestType))) {
						formObject.setValue(TRANSACTION_CURRENCY,
								(recordList.get(0)).get(2));// Currency
						formObject.setValue(TRANSACTION_AMOUNT,
								(recordList.get(0)).get(3));// Amount
						formObject.setValue(TRNS_BAL,
								(recordList.get(0)).get(5));// TRNS_BAL -LC_OS_AMT
						formObject.setValue(TRN_STATUS,
								(recordList.get(0)).get(6)); // IS_ACTIVE
						formObject.setValue(PRODUCT_TYPE,
								(recordList.get(0)).get(7));// PRODUCT
					}
				}
				if ("ELC".equalsIgnoreCase(reqCat)
						|| "ILC".equalsIgnoreCase(reqCat)) {
					formObject.setValue(CUSTOMER_ID, ""); // customer id
					formObject.setValue(ACCOUNT_VALID,
							(recordList.get(0)).get(8));// IS_VALID_ACC
					formObject.setValue(ACCOUNT_NUMBER,
							(recordList.get(0)).get(9));// ACCOUNT_NUMBER
					formObject
					.setValue(EXP_DATE, (recordList.get(0)).get(10)); // EXP_DATE
					formObject.setValue(TRN_THIRD_PARTY,
							(recordList.get(0)).get(11));// TRNS_THIRD_PARTY
					formObject.setValue("LC_CORRSPNDNT_BNK",
							(recordList.get(0)).get(12)); // LC_CORRSPNDNT_BNK
					formObject.setValue(LC_CONF_AMNT,
							(recordList.get(0)).get(13));// LC_CONF_AMNT
					formObject.setValue(transRefOutFieldName,
							(recordList.get(0)).get(14));// OTHER_TRAN_REF_NUM
					if(recordList.get(0).get(15) != null && !recordList.get(0).get(15).equalsIgnoreCase("")){
						formObject.setValue(TFO_BRANCH_CITY,(recordList.get(0)).get(15));// ISSUING_CENTER
						formObject.setValue(TFO_ASSIGNED_CENTER,(recordList.get(0)).get(15));// PROCESSING_CENTER
					}
				} else if ("EC".equalsIgnoreCase(reqCat)
						|| "IC".equalsIgnoreCase(reqCat)
						|| "DBA".equalsIgnoreCase(reqCat)
						|| "ELCB".equalsIgnoreCase(reqCat)
						|| "ILCB".equalsIgnoreCase(reqCat) ) {
					if (!"ILCB_BL".equalsIgnoreCase(requestType)
							&& !"ELCB_BL".equalsIgnoreCase(requestType)) {
						formObject.setValue(INF_TENOR_DAYS,
								(recordList.get(0)).get(8));// TENOR_DAY
						formObject.setValue(INF_TENOR_BASE,
								(recordList.get(0)).get(9));// TENOR_BASE
						formObject.setValue(INF_ACTUAL_TENOR_BASE,
								(recordList.get(0)).get(10));// TENOR_BASE_DTLS
						formObject.setValue(INF_BASE_DOC_DATE, recordList
								.get(0).get(11)); // BASE_DOC_DATE
						formObject.setValue(INF_MATURITY_DATE, recordList
								.get(0).get(12));// MATURITY_DATE
						formObject.setValue(BILL_CUST_HLDING_ACC_US,
								(recordList.get(0)).get(13));// TFO_BILL_CUST_HLDING_ACC_US
						formObject.setValue(INF_SETTLEMENT_ACC_NUM,
								(recordList.get(0)).get(14));// STTLMNT_ACC_NUM
						formObject.setValue(INF_CHARGE_ACC_NUM,
								(recordList.get(0)).get(15));// CHARGE_ACC_NUM
						formObject.setValue(BILL_MODE_OF_PMNT,
								(recordList.get(0)).get(16));// BILL_MODE_OF_PMNT
						formObject.setValue("BILL_OUR_LC_NO",
								(recordList.get(0)).get(17));// BILL_OUR_LC_NO
						formObject.setValue("BILL_CORRSPNDNT_BNK",
								(recordList.get(0)).get(18));// TFO_BILL_CORRSPNDNT_BNK

					}

					formObject.setValue(transRefOutFieldName,
							(recordList.get(0)).get(19));// OTHER_TRAN_REF_NUM
					if(recordList.get(0).get(20) != null && !recordList.get(0).get(20).equalsIgnoreCase("")){
						formObject.setValue(TFO_BRANCH_CITY,(recordList.get(0)).get(20));// ISSUING_CENTER
						formObject.setValue(TFO_ASSIGNED_CENTER,(recordList.get(0)).get(20));// PROCESSING_CENTER
					}
				}if ("SG".equalsIgnoreCase(reqCat)) {   

					if("SG_NILC".equalsIgnoreCase(requestType)){ //Shipping_Gtee_10
						formObject.setValue(transRefOutFieldName,(recordList.get(0)).get(14));// OTHER_TRAN_REF_NUM
						if(recordList.get(0).get(15) != null && !recordList.get(0).get(15).equalsIgnoreCase("")){
							formObject.setValue(TFO_BRANCH_CITY,(recordList.get(0)).get(15));// ISSUING_CENTER
							formObject.setValue(TFO_ASSIGNED_CENTER,(recordList.get(0)).get(15));// PROCESSING_CENTER
						}
					}	
				}
			} catch (Exception e1) {
				log.error("Exception in setting value from outputlist " + e1,e1);
			}
			log.info("RecordList Size : "+recordList.get(0).size());
			if("ILCB_BS".equalsIgnoreCase(requestType) || "ILCB_AC".equalsIgnoreCase(requestType)
					||"IC_AC".equalsIgnoreCase(requestType) || "IC_BS".equalsIgnoreCase(requestType)){
				String sQuery = "UPDATE EXT_TFO SET BILL_TYPE='"+(recordList.get(0)).get(22)+"',"
						+ "IF_SIGHT_BILL='"+(recordList.get(0)).get(23)+"'"
						+" ,DISCREPANCY_INSTRUCTION='"+(recordList.get(0)).get(24)+"',SETTLEMENT_INSTRUCTION='"+(recordList.get(0)).get(25)+"'"
						+" WHERE WI_NAME='"+sWorkitemID+"'";
				log.info("Saving in ext table records query : "+sQuery);
				formObject.saveDataInDB(sQuery);
				log.info("AFTER SAVE OF CUSTOMWER IMPORT FRAME : ");
			}
			retMsg = setTransactionDataLcFCUBS(recordList, reqCat, transFieldName, transRefOutFieldName, 
					requestType, otherRefNum);
		} catch (Exception e) {
			log.error("exception in setTransactionDataLc: "+e,e);
		}
		return retMsg;
	}

	private String setTransactionDataLcFCUBS(List<List<String>> recordList,
			String reqCat, String transFieldName, String transRefOutFieldName,
			String requestType, boolean otherRefNum) {
		log.info("inside setTransactionDataLcFCUBS >>");
		if(transRefOutFieldName.contains("TXT_")){
			transRefOutFieldName = transRefOutFieldName.replace("TXT_", "");
			log.info("new transRefOutFieldName: "+transRefOutFieldName);
		}
		if(transFieldName.contains("TXT_")){
			transFieldName = transFieldName.replace("TXT_", "");
			log.info("new transFieldName: "+transFieldName);
		}
		String retMsg = "";
		try {
			String strQ = "";
			String sFetchedBranchCode = "";
			String sContractRefNo = "";
			reqCat = formObject.getValue(REQUEST_CATEGORY).toString();
			List<List<String>> sOutputlist = null;
			if ("ELC".equalsIgnoreCase(reqCat)
					|| "ILC".equalsIgnoreCase(reqCat)
					|| "EC".equalsIgnoreCase(reqCat)
					|| "IC".equalsIgnoreCase(reqCat)		
					|| "ELCB".equalsIgnoreCase(reqCat)
					|| "ILCB".equalsIgnoreCase(reqCat)
					|| "DBA".equalsIgnoreCase(reqCat)   // ILC and ELC
					|| "SG".equalsIgnoreCase(reqCat)) { //Shipping_Gtee_10
				if (otherRefNum) {
					if ("EC".equalsIgnoreCase(reqCat)							
							|| "IC".equalsIgnoreCase(reqCat)
							|| ("ELCB".equalsIgnoreCase(reqCat)
									&& !"ELCB_BL".equalsIgnoreCase(requestType))
									|| ("ILCB".equalsIgnoreCase(reqCat) 
											&& !"ILCB_BL".equalsIgnoreCase(requestType)) // US3160
											|| "DBA".equalsIgnoreCase(reqCat)
											|| "SG_SD".equalsIgnoreCase(requestType)) {
						sFetchedBranchCode = recordList.get(0).get(21)
								.equals("") ? formObject
										.getValue(transFieldName).toString()
										.substring(0, 3) : recordList.get(0).get(21);
					} else if ("ELC".equalsIgnoreCase(reqCat)
							|| "ILC".equalsIgnoreCase(reqCat)
							|| "ELCB_BL".equalsIgnoreCase(requestType)
							|| "ILCB_BL".equalsIgnoreCase(requestType)// ILC and ELC
							|| "SG_NILC".equalsIgnoreCase(requestType)) {  //Shipping_Gtee_10
						sFetchedBranchCode = recordList.get(0).get(16).equals("") ? 
								formObject.getValue(transFieldName).toString().substring(0, 3) : recordList.get(0).get(16);
					}
					sContractRefNo = formObject.getValue(transFieldName).toString();
				} else {
					if ("EC".equalsIgnoreCase(reqCat)
							|| "IC".equalsIgnoreCase(reqCat)
							|| ("ELCB".equalsIgnoreCase(reqCat) && !"ELCB_BL"
									.equalsIgnoreCase(requestType))
									|| ("ILCB".equalsIgnoreCase(reqCat) && !"ILCB_BL"
											.equalsIgnoreCase(requestType)) // US3160
											|| "DBA".equalsIgnoreCase(reqCat)
											|| "SG_SD".equalsIgnoreCase(requestType)) {
						if (recordList != null && !recordList.isEmpty() && !formObject.getValue(transFieldName)
								.toString().equalsIgnoreCase("")) {
							sContractRefNo = formObject.getValue(transFieldName).toString();
							sFetchedBranchCode = recordList.get(0).get(21).equals("") ? sContractRefNo
									.substring(0, 3) : recordList.get(0).get(21);
						} else {
							sContractRefNo = formObject.getValue(TRNS_REF_NO).toString();
							sFetchedBranchCode = sContractRefNo.substring(0, 3).toString();
						}
					} else if ("ELCB_BL".equalsIgnoreCase(requestType)
							|| "ILCB_BL".equalsIgnoreCase(requestType)
							|| "SG_NILC".equalsIgnoreCase(requestType)) {  //Shipping_Gtee_10
						if (recordList != null && !recordList.isEmpty() && !formObject.getValue(transFieldName)
								.toString().equalsIgnoreCase("")) {
							sContractRefNo = formObject.getValue(transFieldName).toString();
							sFetchedBranchCode = recordList.get(0).get(16).equals("") ? sContractRefNo
									.substring(0, 3) : recordList.get(0).get(16);
						} else {
							sContractRefNo = formObject.getValue("TXT_TRANSACTION_ADCB_LC_REFERENCE").toString();
							sFetchedBranchCode = sContractRefNo.substring(0, 3).toString();
						}
					} else if ("ELC".equalsIgnoreCase(reqCat)
							|| "ILC".equalsIgnoreCase(reqCat)) {
						if (recordList != null && !recordList.isEmpty() && !formObject.getValue(transFieldName)
								.toString().equalsIgnoreCase("")) {
							sContractRefNo = formObject.getValue(transFieldName).toString();
							sFetchedBranchCode = recordList.get(0).get(16).equals("") ? sContractRefNo
									.substring(0, 3) : recordList.get(0).get(16);
						} else {
							sContractRefNo = formObject.getValue(TRNS_REF_NO).toString();
							sFetchedBranchCode = sContractRefNo.substring(0, 3).toString();
						}
					}
				}
				formObject.setValue(transFieldName, sContractRefNo);
				log.info("sFetchedBranchCode " + sFetchedBranchCode);
				if (otherRefNum && recordList != null) {
					if (recordList.get(0).get(4).equals(null)
							|| recordList.get(0).get(4).equals("")) {
						strQ = "SELECT RELATIONSHIP_TYPE FROM TFO_DJ_BRN_REL_TYPE_MAP_MAST WHERE "
								+ "BRANCH_CODE='" + sFetchedBranchCode + "'";
						sOutputlist = formObject.getDataFromDB(strQ);
						log.info("Relationship Type returned " + sOutputlist.get(0).get(0).toString());
						formObject.setValue(RELATIONSHIP_TYPE, sOutputlist.get(0).get(0).toString());
						sOutputlist = null;
					}
				} else {
					strQ = "SELECT RELATIONSHIP_TYPE FROM TFO_DJ_BRN_REL_TYPE_MAP_MAST WHERE "
							+ "BRANCH_CODE='" + sFetchedBranchCode + "'";
					sOutputlist = formObject.getDataFromDB(strQ);
					log.info("Relationship Type returned " + sOutputlist.get(0).get(0).toString());
					formObject.setValue(RELATIONSHIP_TYPE, sOutputlist.get(0).get(0).toString());
					sOutputlist = null;
				}
				log.info("strQ relationship type query" + strQ);
				if (("IC_MSM".equalsIgnoreCase(requestType))
						|| ("EC_MSM".equalsIgnoreCase(requestType))
						|| ("ILC_MSM".equalsIgnoreCase(requestType))
						|| ("ELC_MSM".equalsIgnoreCase(requestType))
						|| ("ILCB_MSM".equalsIgnoreCase(requestType))
						|| ("ELCB_MSM".equalsIgnoreCase(requestType))
						|| ("GRNT_MSM".equalsIgnoreCase(requestType))
						|| ("TL_MSM".equalsIgnoreCase(requestType))
						|| ("DBA_MSM".equalsIgnoreCase(requestType))
						|| ("MISC_MSM".equalsIgnoreCase(requestType))
						|| ("IFS_MSM".equalsIgnoreCase(requestType))
						|| ("IFP_MSM".equalsIgnoreCase(requestType))
						|| ("SBLC_MSM".equalsIgnoreCase(requestType))) {//added by mansi
					strQ = "SELECT OPERATION_NAME FROM TFO_DJ_INQUIRY_OPERATION_MST WHERE "
							+ "REQUEST_CATEGORY_CODE='ALL' AND REQUEST_TYPE_CODE = 'MSM' AND BRANCH_CODE='"
							+ formObject.getValue(RELATIONSHIP_TYPE)
							+ "'AND FETCH_MODULE='"
							+ formObject.getValue(SWIFT_FETCH_MODULE)
							+ "' AND PROCESS_TYPE='"
							+ formObject.getValue(PROCESS_TYPE) + "'";
				} else {
					strQ = "SELECT OPERATION_NAME FROM TFO_DJ_INQUIRY_OPERATION_MST WHERE "
							+ "REQUEST_CATEGORY_CODE='"
							+ reqCat
							+ "' AND REQUEST_TYPE_CODE = '"
							+ formObject.getValue(REQUEST_TYPE).toString()
							+ "' AND BRANCH_CODE='"
							+ formObject.getValue(RELATIONSHIP_TYPE)
							+ "' AND PROCESS_TYPE='"
							+ formObject.getValue(PROCESS_TYPE) + "'";
				}
				log.info("Operation name Detls query " + strQ);
				sOutputlist = formObject.getDataFromDB(strQ);
				log.info("Operation Name returned "+ sOutputlist.get(0).get(0).toString());
				if (!sOutputlist.equals(null)) {
					if (!sOutputlist.get(0).get(0).equalsIgnoreCase("NA")) {
						String returnFetchMessage[] = fetchCustomerContractData(sContractRefNo, sOutputlist.get(0).get(0).toString(), 
								sFetchedBranchCode,formObject.getValue(REQUEST_CATEGORY).toString(), requestType).split("##");
						int statusCode = Integer.parseInt(returnFetchMessage[0]);
						log.info("return msg [] = "+returnFetchMessage.toString()+" and status code returned: "+statusCode);
						if(returnFetchMessage.length>1){
							retMsg = returnFetchMessage[1];
						}
						if (statusCode == 2) {
							if (formObject.getValue(CUSTOMER_ID).equals("")) {
								retMsg = "Unable to fetch Contract details. Kindly Enter correct Reference Number.";
							} else {
								retMsg = fetchCustomerData(
										formObject.getValue(CUSTOMER_ID)
										.toString(), formObject
										.getValue(REQUEST_CATEGORY)
										.toString(), true);
							}
						}
					} else {
						retMsg = fetchCustomerData(recordList.get(0).get(1), formObject
								.getValue(REQUEST_CATEGORY).toString(), false);
					}
				} else {
					retMsg = fetchCustomerData(recordList.get(0).get(1), formObject
							.getValue(REQUEST_CATEGORY).toString(), false);
				}
			} else {
				retMsg = fetchCustomerData(recordList.get(0).get(1), formObject
						.getValue(REQUEST_CATEGORY).toString(), true);
			}
		} catch (Exception e) {
			log.error("Fetch inquiry service excp " + e,e);
		}
		return retMsg;
	}

	@SuppressWarnings("unchecked")
	protected void setCPDDetails(String sRequestCategory) {
		try {
			String sRequestType = formObject.getValue(REQUEST_TYPE)
					.toString();
			if (("IFS".equalsIgnoreCase(sRequestCategory) || "IFP"
					.equalsIgnoreCase(sRequestCategory) || "IFA"
					.equalsIgnoreCase(sRequestCategory))//CODE BY MOKSH
					&& ("AM".equalsIgnoreCase(sRequestType))) {
				List<List<String>> lResultset = null;
				String sQuery = "";
				String sRefernce = formObject.getValue(TRANSACTION_REFERENCE)
						.toString();
				log.info("Reference  " + sRefernce);
				String sQuery1 = "Select  CP_NAME, CP_COUNTRY from TFO_CPD_MASTER where REF_NUM = '"
						+ sRefernce + "'";
				lResultset = formObject.getDataFromDB(sQuery1);
				if (lResultset != null && !lResultset.isEmpty()) {
					sQuery = "Insert All ";
					for (List<String> lInner : lResultset) {
						sQuery += "into TFO_DJ_CPD_DETAILS(WI_NAME,REF_NUM,ACTIVITY_NAME, CP_NAME,CP_COUNTRY, INSERTED_DATE) VALUES('"
								+ this.sWorkitemID
								+ "','"
								+ sRefernce
								+ "','"
								+ this.sActivityName
								+ "','"
								+ checkSplChar(lInner.get(0))
								+ "','"
								+ checkSplChar(lInner.get(1))
								+ "',"
								+ "sysdate)  ";
						log.info("Inner Query " + sQuery);
					}
					sQuery += "Select * from DUAL";
					log.info("Final Query " + sQuery);
					log.info("Query Insert " + sQuery);
					formObject.saveDataInDB(sQuery);
				}
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	public String fetchCustomerData(String cid, String reqCat,boolean bAlertRequired) {
		log.info("inside fetchCustomerData >>>"+sActivityName);
		String retMsg = "";
		retMsg = setCustomerDetail(fetchFCRCustomer(cid, "GetCustomerSummary"), cid,reqCat, bAlertRequired);
		log.info("return message from fetchCustomerData: "+retMsg);
		setCustomerICFCUBS(reqCat);  //Shipping_Gtee_89
		return retMsg;
	}

	public String fetchCustomerContractData(String sContractRefNo,String sFCUBSEnquiryMethod, String sBranchCode, String reqCategory,
			String requestType) {
		return setContractData(fetchContractData(sContractRefNo,sFCUBSEnquiryMethod,sBranchCode),reqCategory,requestType,sBranchCode);
	}

	private String setContractData(String resContractData,String reqCategory,String requestType,String sBranchCode) {
		String sContractStatus = "";
		String sTXNCurrency = "";
		String sTXNAmount = "";
		String sTXNBalance = "";
		String custid = "";
		String sMessege = "";
		String expDate = "";
		String trnTenor = "";
		String tolerance = "";
		String productCode = "";
		String guaranteeExpDate = "";
		String sConfirmedAmount = "";
		String sPositiveTolerance = "";
		String sNegativeTolerance = "";
		String sFromPlace = "";
		String sToPlace = "";
		String sLatestShipmentDate = "";
		String sPortOfDischarge = "";
		String sPortOfLoading = "";
		String sGoodsDescription = "";
		String sLinkageRefNo = "";
		String sLinkagePercent = "";
		String sMaturityDate = "";
		String sTenorDays = "";
		String sBaseDate = "";
		String sPrincipalOutStanding = "";
		String sOurLCNumber = "";
		String sShipmentFrom = "";
		String sShipmentTo = "";
		String sValueDate = "";
		String operationCode="";
		String sBaseDateDescription = "";
		String sSwiftCid = "";
		String sCustomerName =""; //US146
		String sBillStage = "";  //US159
		String sDiscountOnAccept = ""; //US362

		String sExpiryConditions= ""; //BY KISHAN
		String sExpiryType = "";
		String sTransactionAmount= "";
		String sExpiryDate = "";
		String sCounterGuaranteeExpiryDate = "";
		String txnUdfDetails =""; 
		String autoChkReq="";
		
		int iCDError = 0;
		String spartyType=""; 
		int setContractDataStatus = 0;
		int setContractDataStatus1 = 0;
		int setContractDataStatus2 = 0; //US362
		resContractData = resContractData.replace("null", "");
		log.info("resContractData   >>>>>>>>" + resContractData);
		xmlDataParser = new XMLParser(resContractData);
		String sReturnCode = xmlDataParser.getValueOf("returnCode");
		try {
			if ("0".equalsIgnoreCase(sReturnCode)) { 
				iCDError = 1;
				sContractStatus = xmlDataParser.getValueOf("contractStatus");
				sTXNCurrency = xmlDataParser.getValueOf("contractCurrency");
				sTXNAmount = xmlDataParser.getValueOf("contractAmount");
				sTXNBalance = xmlDataParser.getValueOf("outstandingLiability");
				custid = xmlDataParser.getValueOf("customerid");
				expDate = xmlDataParser.getValueOf("ExpiryDate").equals("null") ? "" :xmlDataParser.getValueOf("ExpiryDate").equals("") ? "" :xmlDataParser.getValueOf("ExpiryDate");
				guaranteeExpDate = xmlDataParser.getValueOf("guaranteeExpiryDate").equals("null") ? "" :xmlDataParser.getValueOf("guaranteeExpiryDate").equals("") ? "" :xmlDataParser.getValueOf("guaranteeExpiryDate");
				productCode = xmlDataParser.getValueOf("productCode");
				sConfirmedAmount = xmlDataParser.getValueOf("confirmedAmount");
				sPositiveTolerance = xmlDataParser.getValueOf("positiveTolerance");
				sNegativeTolerance = xmlDataParser.getValueOf("negativeTolerance");
				sFromPlace = xmlDataParser.getValueOf("fromPlace");
				sToPlace = xmlDataParser.getValueOf("toPlace");
				sLatestShipmentDate = xmlDataParser.getValueOf("latestShipmentDate");
				sPortOfDischarge = xmlDataParser.getValueOf("portOfDischarge");
				sPortOfLoading = xmlDataParser.getValueOf("portOfLoading");
				sGoodsDescription = xmlDataParser.getValueOf("goodsDescription");
				sLinkageRefNo = xmlDataParser.getValueOf("linkageReferenceNumber");
				sLinkagePercent = xmlDataParser.getValueOf("collateralPercent");
				spartyType= xmlDataParser.getValueOf("contractLimitPartyType");  
				sMaturityDate = xmlDataParser.getValueOf("MaturityDate").equals("null") ? "" :xmlDataParser.getValueOf("MaturityDate").equals("") ? "" :xmlDataParser.getValueOf("MaturityDate");
				sTenorDays = xmlDataParser.getValueOf("TenorDays");
				sPrincipalOutStanding = xmlDataParser.getValueOf("principalOutStanding");
				sOurLCNumber = xmlDataParser.getValueOf("OurLCNumber");
				sShipmentFrom = xmlDataParser.getValueOf("ShipmentFrom");
				sShipmentTo = xmlDataParser.getValueOf("ShipmentTo");
				sBaseDate = xmlDataParser.getValueOf("BaseDate").equals("null") ? "" :xmlDataParser.getValueOf("BaseDate").equals("") ? "" :xmlDataParser.getValueOf("BaseDate");				
				operationCode = xmlDataParser.getValueOf("operationCode");  
				sFromPlace = checkSplChar(sFromPlace);
				sToPlace = checkSplChar(sToPlace);
				sPortOfDischarge = checkSplChar(sPortOfDischarge);
				sPortOfLoading = checkSplChar(sPortOfLoading);
				sGoodsDescription = createNormalizedXML(sGoodsDescription);
				sShipmentFrom = checkSplChar(sShipmentFrom);
				sShipmentTo = checkSplChar(sShipmentTo);
				sBaseDate = checkSplChar(sBaseDate);
				sValueDate = xmlDataParser.getValueOf("ValueDate").equals("null") ? "" :xmlDataParser.getValueOf("ValueDate").equals("") ? "" :xmlDataParser.getValueOf("ValueDate");
				sBaseDateDescription = checkSplChar(xmlDataParser.getValueOf("BaseDateDescription"));
				sCustomerName = xmlDataParser.getValueOf("customerName");  //US146
				sBillStage = xmlDataParser.getValueOf("stage");
				sDiscountOnAccept = xmlDataParser.getValueOf("discountOnAccept"); //US362
				sExpiryType = xmlDataParser.getValueOf("expiryType");
				txnUdfDetails =xmlDataParser.getValueOf("txnUDFDetails");
				log.info("txnUdfDetails data " + txnUdfDetails);
				int count =0;
				XMLParser parser1=new XMLParser(resContractData);  //Reyaz|Party issue|13-12-2024
				XMLParser parser2=new XMLParser();
				try {
					count=parser1.getNoOfFields("txnUDFDetail");
					log.info("No of txnUDFDetail  "+count);
					for (int i = 0; i < count; ++i) {
						parser2.setInputXML(parser1.getNextValueOf("txnUDFDetail"));
						log.info("parser2  "+parser2);
						String tagName = parser2.getValueOf("userDefinedElementId").trim();
						log.info("tagName  "+tagName);
						if("AUTO_DOCCHK_REQ".equalsIgnoreCase(tagName)) {
							autoChkReq=parser2.getValueOf("userDefinedElementValue").trim();
							log.info("Inside autoChkReq data " + autoChkReq);
							break;
						}
					}
					log.info("autoChkReq " + autoChkReq);
					if("".equalsIgnoreCase(autoChkReq) || autoChkReq.isEmpty()) {
						autoChkReq ="NA";
					}
				} catch (Exception e) {
					log.error("Exception happening while parsing  txnUdfDetails  "+e.getMessage());
				}	
				
				try {
					String strQ="";
					List<List<String>> sOutputlist =null;
					strQ="SELECT TRANSACTION_DESCRIPTION FROM TFO_DJ_CONTRACT_STATUS_MAST WHERE "
							+"TRANSACTION_STATUS='"+sContractStatus+"'";
					log.info("Trns status query " + strQ);
					sOutputlist=formObject.getDataFromDB(strQ);
					if(!sOutputlist.get(0).get(0).equalsIgnoreCase("NA") &&(!("ILCB_BL".equalsIgnoreCase(requestType)|| "ELCB_BL".equalsIgnoreCase(requestType)))){ //US3165
						formObject.setValue(TRN_STATUS, sOutputlist.get(0).get(0).toString());
					}
				} catch (Exception e) {
					log.error("Set Transaction status excp "+e.getMessage());
				}	
				formObject.setValue(CUSTOMER_ID, custid);	//customer id
				sSwiftCid = (String) formObject.getValue(FIELD_SWIFT_CID);
				if(!sSwiftCid.isEmpty()){
					formObject.setValue(CUSTOMER_ID, sSwiftCid);	//customer id
				}

				if("GRNT".equalsIgnoreCase(reqCategory)|| "SBLC".equalsIgnoreCase(reqCategory)
					|| "ELC".equalsIgnoreCase(reqCategory)&& "ELC_SLCA".equalsIgnoreCase(requestType) 
					||"ELC_SLCAA".equalsIgnoreCase(requestType)|| "ELC_SER".equalsIgnoreCase(requestType)
					|| "ELC_SCL".equalsIgnoreCase(requestType)){//added by mansi
						formObject.setValue(TRANSACTION_CURRENCY, sTXNCurrency);//CURRENCY
						formObject.setValue(TRANSACTION_AMOUNT, sTXNAmount);//AMOUNT
						formObject.setValue(TRNS_BAL, sTXNBalance);//OS_AMT - LC_OS_AMT
						//update ext table records
						/*if(expDate.equals("") || expDate.equals(null)){
							trnTenor = "OE";//TRNS_TENOR
						}
						else{
							trnTenor = "FD";//TRNS_TENOR LC_CONF_AMNT
						}*/
						//sExpiryType
						if("LIMT".equalsIgnoreCase(sExpiryType)){
							trnTenor ="FD";
						}else if("UNLM".equalsIgnoreCase(sExpiryType)){
							trnTenor ="OE";
						}else if("COND".equalsIgnoreCase(sExpiryType)){
							trnTenor ="COND";
						}
						
						/*if("FIXD".equalsIgnoreCase(sExpiryType)){
							trnTenor ="LIMT";
						}else if("Open".equalsIgnoreCase(sExpiryType)){
							trnTenor ="UNLM";
						}else if("Conditional".equalsIgnoreCase(sExpiryType)){
							trnTenor ="COND";
						}*/
						if("GRNT".equalsIgnoreCase(reqCategory) && "AM".equalsIgnoreCase(requestType)){
							String sQuery = "UPDATE EXT_TFO SET GRNTEE_CNTR_EXP_DATE='"+formatToYYYYMMDD(guaranteeExpDate)+"',EXP_DATE='"+formatToYYYYMMDD(expDate)+"'"
									+" ,WS_LINKAGE_REF_NUMBER='"+sLinkageRefNo+"',WS_PERCENT='"+sLinkagePercent+"',BRN_CODE='"+sBranchCode+"'"
									+" ,PRODUCT_TYPE='"+productCode+"',TRN_TENOR='"+trnTenor+"',OPERATION_CODE_WS='"+operationCode+"' WHERE WI_NAME='"+sWorkitemID+"'";
							log.info("setContractData update ext table records query : "+sQuery);
							setContractDataStatus = formObject.saveDataInDB(sQuery);
							log.info("setContractData GRNT update : "+setContractDataStatus);
							insertAmendmnentDetails(xmlDataParser); //BY KISHAN
						}else if("SBLC".equalsIgnoreCase(reqCategory) && "SBLC_AM".equalsIgnoreCase(requestType)){//RR
							String sQuery = "UPDATE EXT_TFO SET GRNTEE_CNTR_EXP_DATE='"+formatToYYYYMMDD(guaranteeExpDate)+"',EXP_DATE='"+formatToYYYYMMDD(expDate)+"'"
									+" ,WS_LINKAGE_REF_NUMBER='"+sLinkageRefNo+"',WS_PERCENT='"+sLinkagePercent+"',BRN_CODE='"+sBranchCode+"'"
									+" ,PRODUCT_TYPE='"+productCode+"',TRN_TENOR='"+trnTenor+"',OPERATION_CODE_WS='"+operationCode+"' WHERE WI_NAME='"+sWorkitemID+"'";
							log.info("setContractData update ext table records query : "+sQuery);
							setContractDataStatus = formObject.saveDataInDB(sQuery);
							log.info("setContractData GRNT update : "+setContractDataStatus);
							insertAmendmnentDetails(xmlDataParser); //BY KISHAN
						}else if("ELC".equalsIgnoreCase(reqCategory) && "ELC_SLCAA".equalsIgnoreCase(requestType)){//added by mansi
							String sQuery = "UPDATE EXT_TFO SET GRNTEE_CNTR_EXP_DATE='"+formatToYYYYMMDD(guaranteeExpDate)+"',EXP_DATE='"+formatToYYYYMMDD(expDate)+"'"
									+" ,WS_LINKAGE_REF_NUMBER='"+sLinkageRefNo+"',WS_PERCENT='"+sLinkagePercent+"',BRN_CODE='"+sBranchCode+"'"
									+" ,PRODUCT_TYPE='"+productCode+"',TRN_TENOR='"+trnTenor+"',OPERATION_CODE_WS='"+operationCode+"' WHERE WI_NAME='"+sWorkitemID+"'";
							log.info("setContractData update ext table records query : "+sQuery);
							setContractDataStatus = formObject.saveDataInDB(sQuery);
							log.info("setContractData GRNT update : "+setContractDataStatus);
							insertAmendmnentDetails(xmlDataParser); //BY KISHAN
						}else if("GRNT".equalsIgnoreCase(reqCategory) && "GAA".equalsIgnoreCase(requestType)) {
							String sQuery="UPDATE EXT_TFO SET GRNTEE_CNTR_EXP_DATE='"+formatToYYYYMMDD(guaranteeExpDate)+"',EXP_DATE='"+formatToYYYYMMDD(expDate)+"',BRN_CODE='"+sBranchCode+"'"
									+" ,PRODUCT_TYPE='"+productCode+"',TRN_TENOR='"+trnTenor+"',OPERATION_CODE_WS='"+operationCode+"' WHERE WI_NAME='"+sWorkitemID+"'";
							log.info("setContractData update ext table records query : "+sQuery);
							setContractDataStatus = formObject.saveDataInDB(sQuery);
							log.info("else setContractData GRNT GAA update : "+setContractDataStatus);
							insertAmendmnentDetails(xmlDataParser); //BY KISHAN
						}else{
							String sQuery="UPDATE EXT_TFO SET GRNTEE_CNTR_EXP_DATE='"+formatToYYYYMMDD(guaranteeExpDate)+"',EXP_DATE='"+formatToYYYYMMDD(expDate)+"',BRN_CODE='"+sBranchCode+"'"
									+" ,PRODUCT_TYPE='"+productCode+"',TRN_TENOR='"+trnTenor+"',OPERATION_CODE_WS='"+operationCode+"' WHERE WI_NAME='"+sWorkitemID+"'";
							log.info("setContractData update ext table records query : "+sQuery);
							setContractDataStatus = formObject.saveDataInDB(sQuery);
							log.info("else setContractData GRNT AM update : "+setContractDataStatus);
						}
						insertPDDetails(xmlDataParser,requestType);
					}
				else if("ELC".equalsIgnoreCase(reqCategory) || "ILC".equalsIgnoreCase(reqCategory)){
					if(expDate.equals("") || expDate.equals(null)){ 
						trnTenor = "OE";//TRNS_TENOR
					}
					else{
						trnTenor = "FD";//TRNS_TENOR LC_CONF_AMNT LC_TOLERANCE AV NN
					}
					if((sPositiveTolerance.equals("0") || sPositiveTolerance.equals("null")||sPositiveTolerance.equals(""))       //US3110
							&& (sNegativeTolerance.equals("0") || sNegativeTolerance.equals("null")||sNegativeTolerance.equals(""))){
						tolerance = "NN";//TRNS_TENOR LC_CONF_AMNT LC_TOLERANCE AV NN
					}else if(!(sPositiveTolerance.equals("0") || sNegativeTolerance.equals("0"))){ 
						tolerance = "AV";//TRNS_TENOR
					}
					try {
						if("Y".equalsIgnoreCase(formObject.getValue("SWIFT_UTILITY_FLAG").toString())  && requestType.equalsIgnoreCase("ELC_LCAA"))
						{
							String query = "SELECT LC_ABOVE,LC_UNDER,LC_FROM_PLACE,LC_TO_PLACE,LC_PORT_OF_DISCHRG,"
									+ "LC_PORT_OF_LOADING,LC_GOODS_DESC,LC_LTST_SHIPMNT_DATE FROM EXT_TFO "
									+ "WHERE WI_NAME ='"+sWorkitemID+"'";
							log.info("sQuery TO GET ELC VALUES FROM UTILITY :" + query);
							List<List<String>> sOutputlist = formObject.getDataFromDB(query);
							if (sOutputlist != null && !sOutputlist.isEmpty() && !sOutputlist.get(0).isEmpty()) {
								if ("".equalsIgnoreCase(sOutputlist.get(0).get(0)) || null == sOutputlist.get(0).get(0))
								{
									// do nothing
								}else
								{
									sPositiveTolerance = checkSplChar(sOutputlist.get(0).get(0))  ;
								}

								if ("".equalsIgnoreCase(sOutputlist.get(0).get(1)) || null == sOutputlist.get(0).get(1))
								{
									// do nothing
								}else
								{
									sNegativeTolerance = checkSplChar(sOutputlist.get(0).get(1))  ;
								}

								if ("".equalsIgnoreCase(sOutputlist.get(0).get(2)) || null == sOutputlist.get(0).get(2))
								{
									// do nothing
								}else
								{
									sFromPlace = checkSplChar(sOutputlist.get(0).get(2))  ;
								}

								if ("".equalsIgnoreCase(sOutputlist.get(0).get(3)) || null == sOutputlist.get(0).get(3))
								{
									// do nothing
								}else
								{
									sToPlace = checkSplChar(sOutputlist.get(0).get(3))  ;
								}

								if ("".equalsIgnoreCase(sOutputlist.get(0).get(4)) || null == sOutputlist.get(0).get(4))
								{
									// do nothing
								}else
								{
									sPortOfDischarge = checkSplChar(sOutputlist.get(0).get(4))  ;
								}

								if ("".equalsIgnoreCase(sOutputlist.get(0).get(5)) || null == sOutputlist.get(0).get(5))
								{
									// do nothing
								}else
								{
									sPortOfLoading = checkSplChar(sOutputlist.get(0).get(5))  ;
								}

								if ("".equalsIgnoreCase(sOutputlist.get(0).get(6)) || null == sOutputlist.get(0).get(6))
								{
									// do nothing
								}else
								{
									sGoodsDescription = createNormalizedXML(sOutputlist.get(0).get(6))  ;
								}

								if ("".equalsIgnoreCase(sOutputlist.get(0).get(7)) || null == sOutputlist.get(0).get(7))
								{

								}else
								{
									sLatestShipmentDate = sOutputlist.get(0).get(7)  ;
								}
							}
							formObject.setValue(TRANSACTION_CURRENCY, sTXNCurrency);//CURRENCY
							formObject.setValue(TRANSACTION_AMOUNT, sTXNAmount);//AMOUNT
							formObject.setValue(TRNS_BAL, sTXNBalance);//OS_AMT - LC_OS_AMT
							String sQuery="UPDATE EXT_TFO SET EXP_DATE='"+formatToYYYYMMDD(expDate)+"',LC_TOLERANCE='"+tolerance+"',LC_ABOVE='"+sPositiveTolerance+"',LC_UNDER='"+sNegativeTolerance+"'"
									+" ,PRODUCT_TYPE='"+productCode+"',TRN_TENOR='"+trnTenor+"',LC_CONF_AMNT='"+sConfirmedAmount+"',BRN_CODE='"+sBranchCode+"'"
									+" ,LC_FROM_PLACE='"+sFromPlace+"',LC_TO_PLACE='"+sToPlace+"',LC_LTST_SHIPMNT_DATE='"+formatToYYYYMMDD(sLatestShipmentDate)+"'"
									+" ,LC_PORT_OF_DISCHRG='"+sPortOfDischarge+"',LC_PORT_OF_LOADING='"+sPortOfLoading+"',LC_GOODS_DESC="+sGoodsDescription+",OPERATION_CODE_WS='"+operationCode+"' WHERE WI_NAME='"+sWorkitemID+"'";
							log.info("setContractData update ext table records query : "+sQuery);
							setContractDataStatus = formObject.saveDataInDB(sQuery);
							log.info("else setContractData other than ILC AM update : "+setContractDataStatus);
						} else {
							formObject.setValue(TRANSACTION_CURRENCY, sTXNCurrency);//CURRENCY
							formObject.setValue(TRANSACTION_AMOUNT, sTXNAmount);//AMOUNT
							formObject.setValue(TRNS_BAL, sTXNBalance);//OS_AMT - LC_OS_AMT
							if("ILC".equalsIgnoreCase(reqCategory) && "ILC_AM".equalsIgnoreCase(requestType)){
								String sQuery="UPDATE EXT_TFO SET EXP_DATE='"+formatToYYYYMMDD(expDate)+"',LC_TOLERANCE='"+tolerance+"',LC_ABOVE='"+sPositiveTolerance+"',LC_UNDER='"+sNegativeTolerance+"'"
										+" ,PRODUCT_TYPE='"+productCode+"',TRN_TENOR='"+trnTenor+"',LC_CONF_AMNT='"+sConfirmedAmount+"',BRN_CODE='"+sBranchCode+"'"
										+" ,WS_LINKAGE_REF_NUMBER='"+sLinkageRefNo+"',WS_PERCENT='"+sLinkagePercent+"'"
										+" ,LC_FROM_PLACE='"+sFromPlace+"',LC_TO_PLACE='"+sToPlace+"',LC_LTST_SHIPMNT_DATE='"+formatToYYYYMMDD(sLatestShipmentDate)+"'"
										+" ,LC_PORT_OF_DISCHRG='"+sPortOfDischarge+"',LC_PORT_OF_LOADING='"+sPortOfLoading+"',LC_GOODS_DESC="+sGoodsDescription+",OPERATION_CODE_WS='"+operationCode+"' WHERE WI_NAME='"+sWorkitemID+"'";
								log.info("setContractData update ext table records query : "+sQuery);
								setContractDataStatus = formObject.saveDataInDB(sQuery);
								log.info("setContractData ELC update : "+setContractDataStatus);
								insertAmendmnentDetails(xmlDataParser);
							}
							else{
								String sQuery="UPDATE EXT_TFO SET EXP_DATE='"+formatToYYYYMMDD(expDate)+"',LC_TOLERANCE='"+tolerance+"',LC_ABOVE='"+sPositiveTolerance+"',LC_UNDER='"+sNegativeTolerance+"'"
										+" ,PRODUCT_TYPE='"+productCode+"',TRN_TENOR='"+trnTenor+"',LC_CONF_AMNT='"+sConfirmedAmount+"',BRN_CODE='"+sBranchCode+"'"
										+" ,LC_FROM_PLACE='"+sFromPlace+"',LC_TO_PLACE='"+sToPlace+"',LC_LTST_SHIPMNT_DATE='"+formatToYYYYMMDD(sLatestShipmentDate)+"'"
										+" ,LC_PORT_OF_DISCHRG='"+sPortOfDischarge+"',LC_PORT_OF_LOADING='"+sPortOfLoading+"',LC_GOODS_DESC="+sGoodsDescription+",OPERATION_CODE_WS='"+operationCode+"' WHERE WI_NAME='"+sWorkitemID+"'";
								log.info("setContractData update ext table records query : "+sQuery);
								setContractDataStatus = formObject.saveDataInDB(sQuery);
								log.info("else setContractData other than ILC AM update : "+setContractDataStatus);
							}
						}

						if(  "ELC_LCC".equalsIgnoreCase(requestType)       //PT_278-279
								|| "ELC_LCAA".equalsIgnoreCase(requestType)){	//PT_278-279			 	
							insertAmendmnentDetails(xmlDataParser);
						}
					} catch (Exception e) {
						log.error("ILC and ELC update on Fetch at Reception : "+e.getMessage());
					}
					insertPDDetails(xmlDataParser,requestType);
				}
				else if("IC".equalsIgnoreCase(reqCategory) || "EC".equalsIgnoreCase(reqCategory) 
						|| ("ILCB".equalsIgnoreCase(reqCategory) && !"ILCB_BL".equalsIgnoreCase(requestType))
						|| ("ELCB".equalsIgnoreCase(reqCategory) && !"ELCB_BL".equalsIgnoreCase(requestType)) 
						|| "DBA".equalsIgnoreCase(reqCategory)){
					log.info("requestType--:: starting[][]"+requestType);
					formObject.setValue(TRANSACTION_CURRENCY, sTXNCurrency);//CURRENCY
					formObject.setValue(TRANSACTION_AMOUNT, sTXNAmount);//AMOUNT
					formObject.setValue(TRNS_BAL, sTXNBalance);//OS_AMT - LC_OS_AMT
					log.info("requestType--:: mid1[][]"+requestType);
					String sQuery="UPDATE EXT_TFO SET INF_BASE_DOC_DATE='"+formatToYYYYMMDD(sBaseDate)+"',INF_MATURITY_DATE='"+formatToYYYYMMDD(sMaturityDate)+"',BRN_CODE='"+sBranchCode+"'"
							+" ,BILL_OUR_LC_NO='"+sOurLCNumber+"',BILL_PORT_OF_DISCHARGE='"+sShipmentTo+"',BILL_PORT_OF_LOADING='"+sShipmentFrom+"'" // US3170
							+" ,PRODUCT_TYPE='"+productCode+"',INF_TENOR_DAYS='"+sTenorDays+"',INF_ACTUAL_TENOR_BASE='"+sBaseDateDescription+"',OPERATION_CODE_WS='"+operationCode+"',"
							+ " IS_TS_REQUIRED = '" +autoChkReq+ "' WHERE WI_NAME='"+sWorkitemID+"'"; 
					log.info("setContractData update ext table records query modified: "+sQuery);
					log.info("requestType--:: mid2[][]"+requestType);
					formObject.setValue(TS_REQUIRED, autoChkReq);
					setContractDataStatus = formObject.saveDataInDB(sQuery);
					log.info("else setContractData "+reqCategory+" update: "+setContractDataStatus);
					insertBC_PDDetails(xmlDataParser); 
					log.info("requestType--:: value[][]"+requestType);
					if("ILCB_AC".equalsIgnoreCase(requestType) || "ILCB_BS".equalsIgnoreCase(requestType) 
							|| "IC_AC".equalsIgnoreCase(requestType) || "IC_BS".equalsIgnoreCase(requestType)
							|| "ILCB_BCDR".equalsIgnoreCase(requestType)){
						formObject.setValue("BILL_STAGE", sBillStage);
						String sQuery1 ="UPDATE EXT_TFO SET BILL_STAGE='"+sBillStage+"' WHERE WI_NAME='"+sWorkitemID+"'";
						log.info("input query: "+sQuery1);
						setContractDataStatus1 = formObject.saveDataInDB(sQuery1);
						log.info("else setContractData1 "+reqCategory+" update: "+setContractDataStatus1);
					}
					if("ILCB_AC".equalsIgnoreCase(requestType)){ 
						log.info("ILCB_AC--");//US3368
						insertDiscDetails(xmlDataParser); 
					}
					//US362
					if("ILCB_AC".equalsIgnoreCase(requestType) || "IC_AC".equalsIgnoreCase(requestType) || "ELCB_AC".equalsIgnoreCase(requestType)
							|| "ELCB_AM".equalsIgnoreCase(requestType) || "ELCB_DISC".equalsIgnoreCase(requestType) || "EC_AC".equalsIgnoreCase(requestType)
							|| "EC_AM".equalsIgnoreCase(requestType) || "EC_DISC".equalsIgnoreCase(requestType)){
						if("ELCB_AC".equalsIgnoreCase(requestType)){
							if("Y".equalsIgnoreCase(sDiscountOnAccept) || "Yes".equalsIgnoreCase(sDiscountOnAccept))
								sDiscountOnAccept = "1";
							else if("N".equalsIgnoreCase(sDiscountOnAccept) || "No".equalsIgnoreCase(sDiscountOnAccept))
								sDiscountOnAccept = "2";

							String sQuery2 ="UPDATE EXT_TFO SET LC_GOODS_DESC="+sGoodsDescription+",DISCOUNT_ON_ACCEP='"+sDiscountOnAccept+"' WHERE WI_NAME='"+sWorkitemID+"'";
							log.info("input query: "+sQuery2);
							setContractDataStatus2 = formObject.saveDataInDB(sQuery2);
							log.info("else setContractData1 "+reqCategory+" update: "+setContractDataStatus2);
						}else{
							String sQuery2 ="UPDATE EXT_TFO SET LC_GOODS_DESC='"+sGoodsDescription+"' WHERE WI_NAME='"+sWorkitemID+"'";
							log.info("input query: "+sQuery2);
							setContractDataStatus2 = formObject.saveDataInDB(sQuery2);
							log.info("else setContractData1 "+reqCategory+" update: "+setContractDataStatus2);
						}
					}
				}else if("ILCB_BL".equalsIgnoreCase(requestType)|| "ELCB_BL".equalsIgnoreCase(requestType)){ 
					formObject.setValue(TRANSACTION_CURRENCY, sTXNCurrency);
					if("APP".equalsIgnoreCase(spartyType)){spartyType="DRAWEE";}  //spartType only needed for ELCB_BL US3285
					if("BEN".equalsIgnoreCase(spartyType)){spartyType="DRAWER";}
					if("ISB".equalsIgnoreCase(spartyType)){spartyType="ISSUING BANK";}
					if("REB".equalsIgnoreCase(spartyType)){spartyType="REIMBURSING BANK";}
					String sQuery = "UPDATE EXT_TFO SET LC_LIMIT_LINE='" + sLinkageRefNo + "',LIMIT_PARTY_TYPE='" + spartyType + "',WS_CONTRACT_AMOUNT='" + sTXNAmount + 
							"',WS_POSITIVE_TOLERANCE='" + sPositiveTolerance + "',WS_EXP_DATE='" + expDate + "',WS_LC_LTST_SHIPMNT_DATE='" + sLatestShipmentDate + "', "
							+ " IS_TS_REQUIRED = '" +autoChkReq+ "' WHERE WI_NAME='" + this.sWorkitemID + "'";
					log.info("setContractData update ext table records query modified: "+sQuery);
					formObject.setValue(TS_REQUIRED, autoChkReq);
					setContractDataStatus = formObject.saveDataInDB(sQuery);
					log.info("else setContractData "+reqCategory+" update : "+setContractDataStatus);
					insertPDDetails(xmlDataParser,requestType);
					insertLCContractDetails(xmlDataParser, requestType);
				}
				else if("IFS".equalsIgnoreCase(reqCategory) || "IFP".equalsIgnoreCase(reqCategory) 
						|| "TL".equalsIgnoreCase(reqCategory) || "IFA".equalsIgnoreCase(reqCategory)){//CODE BY MOKSH
					formObject.setValue(TRNS_BAL, sPrincipalOutStanding);//OS_AMT - LC_OS_AMT
					if("TL_LD".equalsIgnoreCase(requestType)){ 			//US146
						log.info(" TRANSACTION_CURRENCY:: "+sTXNCurrency+" ");
						log.info(" TRANSACTION_AMOUNT:: "+sTXNAmount+" ");
						log.info(" CUSTOMER_NAME:: "+sCustomerName+" ");
						log.info(" RELATIONSHIP_TYPE:: "+sBranchCode+" ");
						formObject.setValue(TRANSACTION_CURRENCY, sTXNCurrency); //CURRENCY
						formObject.setValue(TRANSACTION_AMOUNT, sTXNAmount); 	 //AMOUNT
						formObject.setValue(CUSTOMER_ID, custid);				 //customer id
						formObject.setValue(CUSTOMER_NAME, sCustomerName);		 //sCustomerName
						formObject.setValue(INF_TENOR_DAYS, sTenorDays);		 //sTenorDays  //us147
						formObject.setValue(INF_MATURITY_DATE, sMaturityDate);//sMaturityDate //us147
						formObject.setValue("BILL_STAGE", sBillStage);
						String sQuery1 ="UPDATE EXT_TFO SET BILL_STAGE='"+sBillStage+"' WHERE WI_NAME='"+sWorkitemID+"'";
						log.info("input query: "+sQuery1);
						setContractDataStatus1 = formObject.saveDataInDB(sQuery1);
						log.info("else setContractData1 "+reqCategory+" update: "+setContractDataStatus1);
					}
					String sQuery="UPDATE EXT_TFO SET INF_BASE_DOC_DATE='"+formatToYYYYMMDD(sValueDate)+"',INF_MATURITY_DATE='"+formatToYYYYMMDD(sMaturityDate)+"',BRN_CODE='"+sBranchCode+"'"
							+" ,PRODUCT_TYPE='"+productCode+"',INF_TENOR_DAYS='"+sTenorDays+"' WHERE WI_NAME='"+sWorkitemID+"'";
					log.info("setContractData update ext table records query : "+sQuery);
					setContractDataStatus = formObject.saveDataInDB(sQuery);
					log.info("else setContractData "+reqCategory+" update : "+setContractDataStatus);
				}
				else if("SG".equalsIgnoreCase(reqCategory)){ 
					if("SG_NILC".equalsIgnoreCase(requestType)){
						formObject.setValue(TRANSACTION_CURRENCY, sTXNCurrency);  //Shipping_Gtee_14
						insertPDDetails(xmlDataParser,requestType);               //Shipping_Gtee_61
						formObject.setValue(TRNS_BAL, "");	//Shipping_Gtee_13
						formObject.setValue(TRN_STATUS, "");	//Shipping_Gtee_13
					}
					else if("SG_NIC".equalsIgnoreCase(requestType)){
						formObject.setValue(TRNS_BAL, "");   //Shipping_Gtee_13
						formObject.setValue(TRN_STATUS, "");	 //Shipping_Gtee_13
					}
					else if("SG_SD".equalsIgnoreCase(requestType)){

						formObject.setValue(TRANSACTION_CURRENCY, sTXNCurrency);//Shipping_Gtee_14
						formObject.setValue(TRANSACTION_AMOUNT, sTXNAmount);//Shipping_Gtee_15					 
					}
				}
				if(!("ILCB_BL".equalsIgnoreCase(requestType)|| "ELCB_BL".equalsIgnoreCase(requestType)
						|| "DBA_BL".equalsIgnoreCase(requestType)|| "EC_BL".equalsIgnoreCase(requestType)
						|| "EC_LDDI".equalsIgnoreCase(requestType)|| "IC_BL".equalsIgnoreCase(requestType)
						|| "IC_AC".equalsIgnoreCase(requestType)|| "ILCB_AC".equalsIgnoreCase(requestType))){
					insertBCContractDetails(xmlDataParser, requestType);
				}				
				iCDError = 2;
			} else {
				try {
					setAckGenBtnShown(formObject.getValue(SOURCE_CHANNEL).toString());
				}   catch (Exception e) {
					log.error("Exception in setcontract data else try catch block");
				}
				sMessege = getTagValue(resContractData, "td");
			}
		} catch (ParserConfigurationException e) {
			sMessege = "Unable to Fetch Contract Details"; 
			log.error("Exception: ",e);
		} catch (SAXException e) {
			sMessege = "Unable to Fetch Contract Details"; 
			log.error("Exception: ",e);
		} catch (IOException e) {
			sMessege = "Unable to Fetch Contract Details"; 
			log.error("Exception: ",e);
		} finally {
			if (iCDError == 1) {
				sMessege = "Unable to fetch Contract details. Kindly Enter correct Reference Number.";
			}
		}
		log.info("sMessege from Fetch Contract Details: "+sMessege+"--and- iCDError: "+iCDError);
		return iCDError+"##"+sMessege;
	}

	public String formatToYYYYMMDD(String dtExp){
		log.info("formatToYYYYMMDD "+dtExp);
		try {
			if(!dtExp.equals("")){
				SimpleDateFormat sdfEXPDate = new SimpleDateFormat("dd/MM/yyyy");
				Date dateExpDate = sdfEXPDate.parse(dtExp);
				sdfEXPDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				dtExp = sdfEXPDate.format(dateExpDate);
			}
		} catch (Exception e) {
			log.info("formatToYYYYMMDD excp "+e.getMessage());
			log.info("Exception: ",e);
		}
		return dtExp;
	}

	public void insertBCContractDetails(XMLParser xmlDataParser,String requestType){
		int count=0;
		String querystart="INSERT ALL ";
		String query1="INTO TFO_DJ_CONTRACT_LIMITS_DETAILS (PARTY_TYPE, SERIAL_NUMBER, OPERATION, CUSTOMER_NO, TYPE, LINKAGE_REFERENCE_NO, PERCENTAGE_CONTRIBUTION,AMOUNT_TAG, WINAME,INSERTIONORDERID) VALUES(";
		StringBuilder finalInsert = new StringBuilder("");
		String queryEnd = "SELECT * FROM DUAL";
		boolean flag=false;
		XMLParser parser2=new XMLParser();
		count=xmlDataParser.getNoOfFields("partyLimit");
		for(int i=0;i<count;i++)
		{
			flag=true;
			parser2.setInputXML(xmlDataParser.getNextValueOf("partyLimit"));
			String partyType="'"+parser2.getValueOf("partyType").trim()+"'";
			String serialNumber=",'"+parser2.getValueOf("serialNumber").trim()+"'";
			String operation=",'"+parser2.getValueOf("limitOperation").trim()+"'";
			String customerNumber=",'"+parser2.getValueOf("customerNumber").trim()+"','";
			String limitType=parser2.getValueOf("limitLinkageType").trim()+"','";
			String linkageReferenceNumber=parser2.getValueOf("linkageReferenceNumber").trim().toUpperCase()+"','"; //US3504
			String limitPercentContribution=parser2.getValueOf("limitPercentContribution").trim()+"','";
			String amountTag=parser2.getValueOf("amountTag").trim()+"','";
			String winame= sWorkitemID+"','";
			String insertionId=getInsertIonIdForTable("TFO_DJ_CONTRACT_LIMITS_DETAILS")+"') ";

			finalInsert.append(query1).append(partyType).append(serialNumber).append(operation).append(customerNumber).append(limitType)
			.append(linkageReferenceNumber).append(limitPercentContribution).append(amountTag).append(winame).append(insertionId);
		}
		String query = "delete from TFO_DJ_CONTRACT_LIMITS_DETAILS where winame = '"+sWorkitemID+"'";
		formObject.saveDataInDB(query);
		if(flag)
		{
			//deletion earlier records and insertion after that
			log.info(querystart+finalInsert+queryEnd);
			formObject.saveDataInDB(querystart+finalInsert+queryEnd);
		}
	}

	public String getInsertIonIdForTable(String tableName){
		String sequenceName="WFSEQ_ARRAY_";
		String seqValue=null;
		String query="select  (processdefid||'_'||extobjid)  from wfudtvarmappingtable where upper(mappedobjectname)='"+tableName.toUpperCase()+"'";
		log.info("getInsertIonIdForTable query : "+query);
		List<List<String>> sOutputlist = formObject.getDataFromDB(query);
		log.info("getInsertIonIdForTable sOutputlist : "+sOutputlist);
		if (sOutputlist != null && !sOutputlist.isEmpty() && !sOutputlist.get(0).isEmpty()) {
			sequenceName = sequenceName+sOutputlist.get(0).get(0);
		}
		query= "select "+sequenceName+".nextval from dual";
		sOutputlist = formObject.getDataFromDB(query);
		if (sOutputlist != null && !sOutputlist.isEmpty() && !sOutputlist.get(0).isEmpty()) {
			seqValue = sOutputlist.get(0).get(0);
		}
		return seqValue;
	}

	public void insertPDDetails(XMLParser xmlDataParser,String requestType){
		int count=0;
		String querystart="insert all ";
		String query1="into tfo_dj_party_details (party_type, party_desc, party_id, customer_name, address1, address2, address3,address4, country,winame,insertionorderid) values(";   //US3118
		String finalInsert="";
		String queryEnd="select * from dual";
		boolean flag=false;
		XMLParser parser2=new XMLParser();
		count=xmlDataParser.getNoOfFields("partyDetails");
		for(int i=0;i<count;i++)
		{
			flag=true;
			parser2.setInputXML(xmlDataParser.getNextValueOf("partyDetails"));
			String partyType="'"+parser2.getValueOf("partyType").trim()+"'";
			String partyDesc=",'"+parser2.getValueOf("partyDescription").trim()+"'";
			String partyID=",'"+parser2.getValueOf("customerNumber").trim()+"','";
			String customerName=checkSplChar(parser2.getValueOf("customerName").trim())+"','";
			String address1=checkSplChar(parser2.getValueOf("addressLine1").trim())+"','";
			String address2=checkSplChar(parser2.getValueOf("addressLine2").trim())+"','";
			String address3=checkSplChar(parser2.getValueOf("addressLine3").trim())+"','";
			String address4=checkSplChar(parser2.getValueOf("addressLine4").trim())+"','"; 
			String country=parser2.getValueOf("countryCode").trim()+"','";
			String wi_name= sWorkitemID+"','";
			String insertionId=getInsertIonIdForTable("TFO_DJ_PARTY_DETAILS")+"') ";
			String partyTypeforILCB="'APP','BEN','ABK','REB','AS1','RS1','AS2','AS3','AS4','AS5','AS6','AS7','AS8','AS9','A10','A11','A12','A13','A14','A15'";
			List<String> partyAllowedILCB=Arrays.asList(partyTypeforILCB.split(","));
			log.info("partyAllowedILCB="+partyAllowedILCB);
			String partyTypeforELCB="'APP','BEN','ISB','REB','AS1','RS1','AS2','AS3','AS4','AS5','AS6','AS7','AS8','AS9','A10','A11','A12','A13','A14','A15'";
			List<String> partyAllowedELCB=Arrays.asList(partyTypeforELCB.split(","));
			log.info("partyAllowedELCB="+partyAllowedELCB);
			if("ILCB_BL".equalsIgnoreCase(requestType) && partyAllowedILCB.contains(partyType))
			{
				if("'APP'".equalsIgnoreCase(partyType)){partyType="'DRAWEE'";partyDesc=",'DRAWEE'";}
				if("'BEN'".equalsIgnoreCase(partyType)){partyType="'DRAWER'";partyDesc=",'DRAWER'";}
				if("'ABK'".equalsIgnoreCase(partyType)){partyType="'NEGOTIATING BANK'";partyDesc=",'NEGOTIATING BANK'";}
				if("'REB'".equalsIgnoreCase(partyType)){partyType="'REIMBURSING BANK'";partyDesc=",'REIMBURSING BANK'";}
				finalInsert=finalInsert+query1+partyType+partyDesc+partyID+customerName+address1
						+address2+address3+address4+country+wi_name+insertionId; 
			}else if("ELCB_BL".equalsIgnoreCase(requestType) && partyAllowedELCB.contains(partyType))
			{
				if("'APP'".equalsIgnoreCase(partyType)){partyType="'DRAWEE'";partyDesc=",'DRAWEE'";}
				if("'BEN'".equalsIgnoreCase(partyType)){partyType="'DRAWER'";partyDesc=",'DRAWER'";}
				if("'ISB'".equalsIgnoreCase(partyType)){partyType="'ISSUING BANK'";partyDesc=",'ISSUING BANK'";}
				if("'REB'".equalsIgnoreCase(partyType)){partyType="'REIMBURSING BANK'";partyDesc=",'REIMBURSING BANK'";}
				finalInsert=finalInsert+query1+partyType+partyDesc+partyID+customerName+address1
						+address2+address3+address4+country+wi_name+insertionId; 
			}else if(!(("ILCB_BL".equalsIgnoreCase(requestType))||("ELCB_BL".equalsIgnoreCase(requestType)))){
				finalInsert=finalInsert+query1+partyType+partyDesc+partyID+customerName+address1
						+address2+address3+address4+country+wi_name+insertionId;
			}
		}
		String query="delete from TFO_DJ_PARTY_DETAILS where winame='"+sWorkitemID+"'";
		formObject.saveDataInDB(query);
		if(flag)
		{
			//String query="delete from TFO_DJ_PARTY_DETAILS where winame='"+sWorkitemID+"'";
			//	formObject.saveDataInDB(query);
			log.info(querystart+finalInsert+queryEnd);
			formObject.saveDataInDB(querystart+finalInsert+queryEnd);
		}
	}

	public void insertBC_PDDetails(XMLParser xmlDataParser){
		int count=0;
		String querystart="insert all ";
		String query1="into tfo_dj_party_details (party_type, party_desc, party_id, customer_name, address1, address2, address3,address4, winame,insertionorderid) values(";   //US3118
		String finalInsert="";
		String queryEnd="select * from dual";
		boolean flag=false;
		XMLParser parser2=new XMLParser();
		count=xmlDataParser.getNoOfFields("partyDetails");
		String partyDesc = "";
		for(int i=0;i<count;i++)
		{
			flag=true;
			partyDesc = "";
			parser2.setInputXML(xmlDataParser.getNextValueOf("partyDetails"));
			String partyType="'"+parser2.getValueOf("partyType").trim()+"','";
			try {
				String strQ="";
				List<List<String>> sOutputlist =null;
				strQ="SELECT PARTY_TYPE_DESC FROM TFO_DJ_PARTY_TYPE_MASTER WHERE PARTY_TYPE_CODE='"+parser2.getValueOf("partyType").trim()+"'";
				log.info("party type query " + strQ);
				sOutputlist=formObject.getDataFromDB(strQ);
				if(!sOutputlist.get(0).get(0).equalsIgnoreCase("")){
					partyDesc = sOutputlist.get(0).get(0).toString()+"','";
				}
			} catch (Exception e) {
				partyDesc = "','";
				log.error("Party desc not found for party typ excp "+parser2.getValueOf("partyType").trim()+"\n"+e.getMessage());
			}
			String partyID=parser2.getValueOf("customerNumber").trim()+"','";
			String customerName=parser2.getValueOf("customerName").trim()+"','";
			String address1=parser2.getValueOf("addressLine1").trim()+"','";
			String address2=parser2.getValueOf("addressLine2").trim()+"','";
			String address3=parser2.getValueOf("addressLine3").trim()+"','";
			String address4=parser2.getValueOf("addressLine4").trim()+"','";
			String wi_name= sWorkitemID+"','";
			String insertionId=getInsertIonIdForTable("TFO_DJ_PARTY_DETAILS")+"') ";

			finalInsert=finalInsert+query1+partyType+partyDesc+partyID+customerName+address1
					+address2+address3+address4+wi_name+insertionId; 
		}
		String query="delete from TFO_DJ_PARTY_DETAILS where winame='"+sWorkitemID+"'";
		formObject.saveDataInDB(query);
		if(flag)
		{
			//String query="delete from TFO_DJ_PARTY_DETAILS where winame='"+sWorkitemID+"'";
			//formObject.saveDataInDB(query);
			log.info(querystart+finalInsert+queryEnd);
			formObject.saveDataInDB(querystart+finalInsert+queryEnd);
		}

	}

	public void insertLCContractDetails(XMLParser xmlDataParser,String requestType){
		log.info("inside insertLCContractDetails");
		int count=0;
		String querystart="INSERT ALL ";
		String query1="INTO TFO_DJ_CONTRACT_LIMITS_DETAILS (PARTY_TYPE, SERIAL_NUMBER, OPERATION, CUSTOMER_NO, TYPE, LINKAGE_REFERENCE_NO, PERCENTAGE_CONTRIBUTION,AMOUNT_TAG, WINAME,INSERTIONORDERID) VALUES(";
		String finalInsert="";
		String queryEnd="SELECT * FROM DUAL";
		boolean flag=false;
		XMLParser parser2=new XMLParser();
		count=xmlDataParser.getNoOfFields("contractLimit");
		for(int i=0;i<count;i++)
		{
			flag=true;
			parser2.setInputXML(xmlDataParser.getNextValueOf("contractLimit"));
			String partyType="'"+parser2.getValueOf("partyType").trim()+"'";
			String serialNumber=",'"+parser2.getValueOf("serialNumber").trim()+"'";
			String operation=",'ACC'";
			String customerNumber=",'"+parser2.getValueOf("customerNumber").trim()+"','";
			String limitType=parser2.getValueOf("limitLinkageType").trim()+"','";
			String linkageReferenceNumber=parser2.getValueOf("linkageReferenceNumber").trim().toUpperCase()+"','"; //US3504
			String limitPercentContribution=parser2.getValueOf("limitPercentContribution").trim()+"',";
			String amountTag="'"+parser2.getValueOf("amountTag").trim()+"'";
			String wi_name= ",'"+sWorkitemID+"','";
			String insertionId=getInsertIonIdForTable("TFO_DJ_CONTRACT_LIMITS_DETAILS")+"') ";

			if("'APP'".equalsIgnoreCase(partyType) || "'ACC'".equalsIgnoreCase(partyType)){partyType="'DRAWEE'";}
			if("'ISB'".equalsIgnoreCase(partyType)){partyType="'ISSUING BANK'";}

			if("'LIAB_OS_AMT'".equalsIgnoreCase(amountTag) || "'CNF_LIAB_OS_AMT'".equalsIgnoreCase(amountTag)){amountTag="'BILL_OS_AMT'";}
			if("'USN_INT_AMT'".equalsIgnoreCase(amountTag)){amountTag="'USN_INT_AMT'";}

			finalInsert=finalInsert+query1+partyType+serialNumber+operation+customerNumber+limitType+linkageReferenceNumber
					+limitPercentContribution+amountTag+wi_name+insertionId; 
		}

		String query="delete from TFO_DJ_CONTRACT_LIMITS_DETAILS where winame='"+sWorkitemID+"'";
		formObject.saveDataInDB(query);

		if(flag)
		{
			//String query="delete from TFO_DJ_CONTRACT_LIMITS_DETAILS where winame='"+sWorkitemID+"'";
			//formObject.saveDataInDB(query);
			log.info(querystart+finalInsert+queryEnd);
			formObject.saveDataInDB(querystart+finalInsert+queryEnd);
		}
	}

	public void insertDiscDetails(XMLParser xmlDataParser){
		log.info("isnide insertDiscDetails"+xmlDataParser);
		int count = 0;
		String querystart = "insert all ";
		String query1 = "into tfo_dj_discreprancy_details (sno,discreprancy_code, discreprancy_description, discreprancy_resolved, discreprancy_resolved_date, discreprancy_received_date,wi_name) values(";  
		String finalInsert="";
		String queryEnd="select * from dual";
		boolean flag=false;
		XMLParser parser2=new XMLParser();
		count = xmlDataParser.getNoOfFields("DiscreprancyDetail");
		log.info("count disc"+count);
		for(int i=0;i<count;i++)
		{
			flag=true;
			log.info("next value :"+xmlDataParser.getNextValueOf("DiscreprancyDetail"));
			log.info("getvalue value :"+xmlDataParser.getValueOf("DiscreprancyDetail"));
			parser2.setInputXML(xmlDataParser.getNextValueOf("DiscreprancyDetail"));
			String code=",'"+parser2.getValueOf("DiscCode").trim()+"',to_clob('";
			String decription=parser2.getValueOf("DiscDescription").trim().replace("'", "''")+"'),'";
			String resolved=parser2.getValueOf("DiscResolved").trim()+"',TO_DATE('";
			String resolvedDate=parser2.getValueOf("DiscResolvedDate").trim()+"','dd/MM/yyyy'),TO_DATE('";
			String receivedDate=parser2.getValueOf("DiscReceivedDate").trim()+"','dd/MM/yyyy'),'";
			String winame= sWorkitemID+"') ";
			finalInsert = finalInsert+query1+(i+1)+code+decription+resolved+resolvedDate+receivedDate+winame;                       
		}

		String query="delete from tfo_dj_discreprancy_details where wi_name='"+sWorkitemID+"'";
		formObject.saveDataInDB(query);
		if(flag)
		{
			//	String query="delete from tfo_dj_discreprancy_details where wi_name='"+sWorkitemID+"'";
			//formObject.saveDataInDB(query);
			log.info(querystart+finalInsert+queryEnd);
			formObject.saveDataInDB(querystart+finalInsert+queryEnd);
		}
	}

	private String setCustomerDetail(String resCustSmry, String cid, String reqCat, boolean bAlertRequired) {
		log.info("inside setCustomerDetail >>>");
		String retMsg ="";
		String strFullName = "";
		String strRMCode = "";
		String sMessege = "";
		resCustSmry = resCustSmry.replace("null", "");
		log.info("resCustSmry   >>>>>>>>" + resCustSmry);
		xmlDataParser = new XMLParser(resCustSmry);
		String sReturnCode = xmlDataParser.getValueOf("returnCode");
		log.info("return code: "+sReturnCode);
		bCustomerDetalFetched = false;
		try {
			if ("0".equalsIgnoreCase(sReturnCode)|| "2".equalsIgnoreCase(sReturnCode)) {
				log.info("inside try block return code: "+sReturnCode);
				iError = 1;
				bCustomerDetalFetched = true;
				strFullName = xmlDataParser.getValueOf("FullName").trim();
				strRMCode = xmlDataParser.getValueOf("RMCode");
				formObject.setValue(CUSTOMER_NAME, strFullName);
				formObject.setValue("ADDRESS_LINE_1", xmlDataParser.getValueOf("AddressLine_1"));
				formObject.setValue("ADDRESS_LINE_2",xmlDataParser.getValueOf("AddressLine_2"));
				formObject.setValue("CUSTOMER_CATEGORY", xmlDataParser.getValueOf("CustCategory"));
				formObject.setValue("EMIRATES",	xmlDataParser.getValueOf("State"));
				formObject.setValue("MOBILE_NUMBER",xmlDataParser.getValueOf("Mobile"));
				formObject.setValue("PO_BOX", xmlDataParser.getValueOf("POBox"));
				formObject.setValue("PROFIT_CENTER_CODE",xmlDataParser.getValueOf("ProfitCenterCode") + " - "
						+ xmlDataParser.getValueOf("ProfitCenterName"));
				formObject.setValue("RM_CODE",xmlDataParser.getValueOf("RMCode"));
				formObject.setValue("RM_NAME",xmlDataParser.getValueOf("RMName"));
				formObject.setValue("FAX_NO", xmlDataParser.getValueOf("Fax"));
				formObject.setValue(FCR_CUST_EMAIL_ID,xmlDataParser.getValueOf("Email"));
				
				updateFetchButtonFlagInHybridCase(); //ATP-493 29-07-2024 REYAZ 
				if (isEmpty(xmlDataParser.getValueOf("FullName"))) {
					log.info("strFullName***********" + strFullName);
					formObject.setValue(CUSTOMER_ID, cid);
					formObject.setValue(FIELD_SWIFT_CID, cid);
					String ts_req = formObject.getValue(TS_REQUIRED).toString();
					log.info("ts_req   " + ts_req);
					String reqType = formObject.getValue(REQUEST_TYPE).toString();
					log.info("requestType   " + reqType);
					
					if(("ELC_LCA".equalsIgnoreCase(reqType)||"ILC_NI".equalsIgnoreCase(reqType))) {
						log.info("Checked 1 ");
						formObject.setValue(TS_REQUIRED, "");
					} else if (("".equalsIgnoreCase(ts_req) || ts_req.isEmpty())) {
						log.info("Checked 2 ");
						formObject.setValue(TS_REQUIRED, "NA");
					}
					isVIPCustomerCheck(cid);
					fetchRMDeatilfrmETL(strRMCode);
					fetchTradeEmailDetailfrmETL(cid, reqCat);
					iError = 100;
				} else {
					log.info("strFullName***********error");
					iError = 1;
				}
			} else {
				sMessege = getTagValue(resCustSmry, "td");
				log.info("message (tag value - td): "+sMessege);
			}
		} catch (ParserConfigurationException|SAXException|IOException e) {
			log.error("Exception: ",e);
		} finally {
			log.info("iError: "+iError+" ; bAlertRequired: "+bAlertRequired);
			if (iError == 0 && bAlertRequired) {
				retMsg = sMessege;
			} else if (iError == 1 && bAlertRequired) {
				retMsg = "Unable to fetch customer details. Kindly Enter correct CID";
			}
		}
		log.info("return message from setCustomerDetail: "+retMsg);
		return retMsg;
	}

	public boolean isEmpty(String str) { 
		boolean flag = ((null != str) && (str.trim().length() > 0) && (!(str.trim().equalsIgnoreCase("null"))));
		log.info("flag value : "+flag);
		return flag;
	}

	@SuppressWarnings("unchecked")
	private void fetchRMDeatilfrmETL(String rmcode) {
		try {
			String str = "SELECT RM_EMAIL,RM_PHONE FROM USR_0_RM WHERE RM_CODE ='" + rmcode + "'";
			log.info("fetchRMDeatilfrmETL   " + str);
			List<List<String>> sOutputlist = formObject.getDataFromDB("SELECT RM_EMAIL,RM_PHONE FROM USR_0_RM WHERE RM_CODE ='" + rmcode + "'");
			if (sOutputlist != null && !sOutputlist.isEmpty() && !sOutputlist.get(0).isEmpty()) {
				formObject.setValue("RM_EMAIL", sOutputlist.get(0).get(0));   
				formObject.setValue("RM_MOBILE_NUMBER", sOutputlist.get(0).get(1));
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	private void fetchTradeEmailDetailfrmETL(String cid, String reqCat) {
		try {
			formObject.setValue(TRADE_CUST_EMAIL_ID, ""); 
			String tradeFinanceService = "";

			if("GRNT".equalsIgnoreCase(reqCat)){
				tradeFinanceService="Guarantee";
			}else if("ELC".equalsIgnoreCase(reqCat)){
				tradeFinanceService="Export LC";
			}
			else if("ILCB".equalsIgnoreCase(reqCat) ||  "IC".equalsIgnoreCase(reqCat) || "DBA".equalsIgnoreCase(reqCat)){
				tradeFinanceService="Incoming Bill";
			}
			else if("ELCB".equalsIgnoreCase(reqCat) ||  "EC".equalsIgnoreCase(reqCat)){
				tradeFinanceService="Outgoing Bill";
			}
			else if("SG".equalsIgnoreCase(reqCat)){
				tradeFinanceService="Shipping Guarantee";
			}
			else if("ILC".equalsIgnoreCase(reqCat)){
				tradeFinanceService="Import LC";
			}
			else if("IFA".equalsIgnoreCase(reqCat)|| "IFS".equalsIgnoreCase(reqCat) || "IFP".equalsIgnoreCase(reqCat) || "SCF".equalsIgnoreCase(reqCat)){ //ADDED BY SCF
				tradeFinanceService="Loans";
			}
			String str = "SELECT email "
					+ "FROM TFO_DJ_TRADE_EMAIL_MAST "
					+ "WHERE CUST_ID ='"+ cid +"' and trade_finance_service='"+tradeFinanceService+"' union  all"+
					" select email  from tfo_dj_trade_email_mast where  cust_id='"+cid+"' "
							+ "and UPPER(trade_finance_service)=UPPER('ALL')";
			log.info("fetchTradeEmailDeatilfrmETL   " + str);
			log.info("Request CAtegory code " + reqCat);
			reqCat = requestCategoryMap.get(reqCat);
			log.info("Request Category Value " + reqCat);
			@SuppressWarnings("unchecked")

			List<List<String>> sOutputlist = formObject.getDataFromDB(str);
			if (sOutputlist != null && !sOutputlist.isEmpty() && !sOutputlist.get(0).isEmpty()) {
				for (List<String> list : sOutputlist) {
					if (!list.isEmpty()) {
						formObject.setValue(TRADE_CUST_EMAIL_ID, list.get(0));
						break;
					}
				}
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	public String fetchFCRCustomer(String sCustID, String sTxn) {
		String sOutput = "";
		@SuppressWarnings("unchecked")
		List<List<String>> sOutputlist = formObject.getDataFromDB("SELECT SEQ_WEBSERVICE.nextval as IDValue from DUAL");
		if (!sOutputlist.isEmpty() && !sOutputlist.get(0).isEmpty()) {
			sOutput = getFCRInputXML(sCustID, sOutputlist.get(0).get(0),"TP906079", sTxn);
			log.info("fetchFCRCustomer sOutput   " + sOutput);
			sOutput = socket.connectToSocket(sOutput);
//			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>ADCB_GETCUSTINFO</Option><returnCode>2</returnCode><errorDescription>InqCustomerInformation-111111-No Record Found</errorDescription><GetCustomerInformationRes><listOfCustomer><customer><Prefix>Mr</Prefix><FullName>SUDHANSHU SINGH</FullName><DOB></DOB><PassportNO></PassportNO><PssportIssueDate></PssportIssueDate><PassportExpDate></PassportExpDate><VisaNo></VisaNo><VisaExpDate></VisaExpDate><VisaIssueDate></VisaIssueDate><Nationality></Nationality><MotherName>MOTHER_252535</MotherName><ResCountry></ResCountry><Profession></Profession><Gender></Gender><EIDA_NO></EIDA_NO><MemoSeverity>Low</MemoSeverity><memoDesc>xUNB card written off contact RCU for details</memoDesc><domicileBranchName>AL RIGGAH ROAD</domicileBranchName><domicileBranchCode>251</domicileBranchCode><EmpName></EmpName><CustomerIC></CustomerIC><CustCategory>Corporate</CustCategory><SCPackageCode></SCPackageCode><BenefitCategory></BenefitCategory><BusinessType></BusinessType><CompanyBlacklist></CompanyBlacklist><CompanyCategory></CompanyCategory><CompanyCode></CompanyCode><CompanyName></CompanyName><CriteriaforPrivilege></CriteriaforPrivilege><CustAttritionScore></CustAttritionScore><CustBehavioralScore></CustBehavioralScore><CustBlacklistStatus></CustBlacklistStatus><CustIslamicFlag>Both</CustIslamicFlag><CustMarketingScore></CustMarketingScore><CustProfitabilityScore></CustProfitabilityScore><CustSignupDat></CustSignupDat><DateofCIDOpening>07/03/2001</DateofCIDOpening><DateofJoining></DateofJoining><Designation></Designation><EducationalQual></EducationalQual><EmployeeID></EmployeeID><TotalTouchpoint></TotalTouchpoint><TMLFlag></TMLFlag><StaffFlag>No</StaffFlag><SignatureType></SignatureType><ShortName></ShortName><SalaryAmount></SalaryAmount><RMName>Riyaz Hibtullah Attari</RMName><RMCode>COR100102</RMCode><PromotionCode></PromotionCode><ProfitCenterName>AUH Corporate</ProfitCenterName><ProfitCenterCode>290</ProfitCenterCode><MaritalStatus></MaritalStatus><EStatementSubFlag>Yes</EStatementSubFlag><EmploymentType></EmploymentType><InternetBanking>Yes</InternetBanking><SMSBanking>No</SMSBanking><IVR>Yes</IVR><MIBRegStatus>No</MIBRegStatus></customer><Accounts><Account><AcctType>AUS</AcctType><AccountNo>10517353920001</AccountNo><AccountTitle>DFGDFGD</AccountTitle><AcctStatus>ACCOUNT OPEN REGULAR</AcctStatus><BranchCode>101</BranchCode><BranchName>ABU DHABI MAIN</BranchName><ProductCode>41</ProductCode><ProductName>Business Choice Current Account Silver</ProductName><AcctBalance>0</AcctBalance><CurrencyName>AED</CurrencyName><AccountType>CASA</AccountType></Account><Account><AcctType>AUS</AcctType><AccountNo>10517353920002</AccountNo><AccountTitle>DFGDFGD</AccountTitle><AcctStatus>ACCOUNT DORMANT - CREDIT WITH OVERRIDE</AcctStatus><BranchCode>101</BranchCode><BranchName>ABU DHABI MAIN</BranchName><ProductCode>41</ProductCode><ProductName>Business Choice Current Account Silver</ProductName><AcctBalance>0</AcctBalance><CurrencyName>AED</CurrencyName><AccountType>CASA</AccountType></Account><Account><AcctType>AUS</AcctType><AccountNo>12176573920001</AccountNo><AccountTitle>SUDHANSHU SINGH</AccountTitle><AcctStatus>ACCOUNT DORMANT - CREDIT WITH OVERRIDE</AcctStatus><BranchCode>101</BranchCode><BranchName>ABU DHABI MAIN</BranchName><ProductCode>42</ProductCode><ProductName>Business Choice Current Account Gold</ProductName><AcctBalance>0</AcctBalance><CurrencyName>AED</CurrencyName><AccountType>CASA</AccountType></Account><Account><AcctType>AUS</AcctType><AccountNo>12176579920001</AccountNo><AccountTitle>SUDH SINGH</AccountTitle><AcctStatus>ACCOUNT DORMANT - CREDIT WITH OVERRIDE</AcctStatus><BranchCode>101</BranchCode><BranchName>ABU DHABI MAIN</BranchName><ProductCode>42</ProductCode><ProductName>Business Choice Current Account Gold</ProductName><AcctBalance>0</AcctBalance><CurrencyName>AED</CurrencyName><AccountType>CASA</AccountType></Account><Account><AcctType>AUS</AcctType><AccountNo>12176588920001</AccountNo><AccountTitle>ABCD</AccountTitle><AcctStatus>ACCOUNT DORMANT - CREDIT WITH OVERRIDE</AcctStatus><BranchCode>101</BranchCode><BranchName>ABU DHABI MAIN</BranchName><ProductCode>42</ProductCode><ProductName>Business Choice Current Account Gold</ProductName><AcctBalance>0</AcctBalance><CurrencyName>AED</CurrencyName><AccountType>CASA</AccountType></Account><Account><AcctType>AUS</AcctType><AccountNo>12176590920001</AccountNo><AccountTitle>USDHAN SINFGFHS</AccountTitle><AcctStatus>ACCOUNT DORMANT - CREDIT WITH OVERRIDE</AcctStatus><BranchCode>101</BranchCode><BranchName>ABU DHABI MAIN</BranchName><ProductCode>42</ProductCode><ProductName>Business Choice Current Account Gold</ProductName><AcctBalance>0</AcctBalance><CurrencyName>AED</CurrencyName><AccountType>CASA</AccountType></Account><Account><AcctType>SOW</AcctType><AccountNo>252535020001</AccountNo><AccountTitle>252535020001AC DESC</AccountTitle><AcctStatus>ACCOUNT DORMANT</AcctStatus><BranchCode>251</BranchCode><BranchName>AL RIGGAH ROAD</BranchName><ProductCode>20</ProductCode><ProductName>Current Account Commercial</ProductName><AcctBalance>-6196.65</AcctBalance><CurrencyName>AED</CurrencyName><AccountType>CASA</AccountType></Account><Account><AcctType>SOW</AcctType><AccountNo>252535021001</AccountNo><AccountTitle>252535021001AC DESC</AccountTitle><AcctStatus>ACCOUNT DORMANT</AcctStatus><BranchCode>251</BranchCode><BranchName>AL RIGGAH ROAD</BranchName><ProductCode>21</ProductCode><ProductName>Current Account Commercial</ProductName><AcctBalance>251.22</AcctBalance><CurrencyName>USD</CurrencyName><AccountType>CASA</AccountType></Account><Account><AcctType>SOW</AcctType><AccountNo>252535022001</AccountNo><AccountTitle>252535022001AC DESC</AccountTitle><AcctStatus>ACCOUNT DORMANT</AcctStatus><BranchCode>251</BranchCode><BranchName>AL RIGGAH ROAD</BranchName><ProductCode>21</ProductCode><ProductName>Current Account Commercial</ProductName><AcctBalance>1018.1</AcctBalance><CurrencyName>EUR</CurrencyName><AccountType>CASA</AccountType></Account><Account><AcctType>SOW</AcctType><AccountNo>252535022002</AccountNo><AccountTitle>252535022002AC DESC</AccountTitle><AcctStatus>ACCOUNT CLOSED</AcctStatus><BranchCode>251</BranchCode><BranchName>AL RIGGAH ROAD</BranchName><ProductCode>21</ProductCode><ProductName>Current Account Commercial</ProductName><AcctBalance>0</AcctBalance><CurrencyName>EUR</CurrencyName><AccountType>CASA</AccountType></Account><Account><AcctType>SOW</AcctType><AccountNo>252535023001</AccountNo><AccountTitle>252535023001AC DESC</AccountTitle><AcctStatus>ACCOUNT DORMANT</AcctStatus><BranchCode>251</BranchCode><BranchName>AL RIGGAH ROAD</BranchName><ProductCode>21</ProductCode><ProductName>Current Account Commercial</ProductName><AcctBalance>1330.29</AcctBalance><CurrencyName>GBP</CurrencyName><AccountType>CASA</AccountType></Account><Account><AcctType>SOW</AcctType><AccountNo>252535023002</AccountNo><AccountTitle>252535023002AC DESC</AccountTitle><AcctStatus>ACCOUNT CLOSED</AcctStatus><BranchCode>251</BranchCode><BranchName>AL RIGGAH ROAD</BranchName><ProductCode>21</ProductCode><ProductName>Current Account Commercial</ProductName><AcctBalance>0</AcctBalance><CurrencyName>GBP</CurrencyName><AccountType>CASA</AccountType></Account><Account><AcctType>SOW</AcctType><AccountNo>252535024001</AccountNo><AccountTitle>252535024001AC DESC</AccountTitle><AcctStatus>ACCOUNT CLOSED</AcctStatus><BranchCode>251</BranchCode><BranchName>AL RIGGAH ROAD</BranchName><ProductCode>21</ProductCode><ProductName>Current Account Commercial</ProductName><AcctBalance>0</AcctBalance><CurrencyName>CHF</CurrencyName><AccountType>CASA</AccountType></Account><Account><AcctType>SOW</AcctType><AccountNo>252535027001</AccountNo><AccountTitle>252535027001AC DESC</AccountTitle><AcctStatus>ACCOUNT CLOSED</AcctStatus><BranchCode>251</BranchCode><BranchName>AL RIGGAH ROAD</BranchName><ProductCode>21</ProductCode><ProductName>Current Account Commercial</ProductName><AcctBalance>0</AcctBalance><CurrencyName>JPY</CurrencyName><AccountType>CASA</AccountType></Account><Account><AcctType>SOW</AcctType><AccountNo>252535260001</AccountNo><AccountTitle>252535260001AC DESC</AccountTitle><AcctStatus>ACCOUNT DORMANT</AcctStatus><BranchCode>752</BranchCode><BranchName>IBD-AL KARAMAH BRANCH</BranchName><ProductCode>260</ProductCode><ProductName>Islamic Corporate Management Account</ProductName><AcctBalance>140011.16</AcctBalance><CurrencyName>AED</CurrencyName><AccountType>CASA</AccountType></Account><Account><AcctType>SOW</AcctType><AccountNo>252535263001</AccountNo><AccountTitle>252535263001AC DESC</AccountTitle><AcctStatus>ACCOUNT DORMANT - CREDIT WITH OVERRIDE</AcctStatus><BranchCode>752</BranchCode><BranchName>IBD-AL KARAMAH BRANCH</BranchName><ProductCode>263</ProductCode><ProductName>Mudarabah Funding Account</ProductName><AcctBalance>0</AcctBalance><CurrencyName>AED</CurrencyName><AccountType>CASA</AccountType></Account><Account><AcctType>SOW</AcctType><AccountNo>252535321001</AccountNo><AccountTitle>252535321001AC DESC</AccountTitle><AcctStatus>ACCOUNT CLOSED</AcctStatus><BranchCode>251</BranchCode><BranchName>AL RIGGAH ROAD</BranchName><ProductCode>319</ProductCode><ProductName>Call Account Commercial</ProductName><AcctBalance>0</AcctBalance><CurrencyName>GBP</CurrencyName><AccountType>CASA</AccountType></Account><Account><AcctType>SOW</AcctType><AccountNo>252535810001</AccountNo><AccountTitle>252535810001AC DESC</AccountTitle><AcctStatus>ACCOUNT DORMANT - DEBIT WITH OVERRIDE</AcctStatus><BranchCode>251</BranchCode><BranchName>AL RIGGAH ROAD</BranchName><ProductCode>810</ProductCode><ProductName>Collection Account Invoice Financing</ProductName><AcctBalance>0</AcctBalance><CurrencyName>AED</CurrencyName><AccountType>CASA</AccountType></Account><Account><AcctType>SOW</AcctType><AccountNo>252535920001</AccountNo><AccountTitle>252535920001AC DESC</AccountTitle><AcctStatus>ACCOUNT DORMANT - CREDIT WITH OVERRIDE</AcctStatus><BranchCode>251</BranchCode><BranchName>AL RIGGAH ROAD</BranchName><ProductCode>810</ProductCode><ProductName>Collection Account Invoice Financing</ProductName><AcctBalance>0</AcctBalance><CurrencyName>AED</CurrencyName><AccountType>CASA</AccountType></Account><Account><AcctType>SOW</AcctType><AccountNo>252535920002</AccountNo><AccountTitle>252535920002AC DESC</AccountTitle><AcctStatus>ACCOUNT DORMANT - CREDIT WITH OVERRIDE</AcctStatus><BranchCode>049</BranchCode><BranchName>CENTRALISED PROCESSING DEPT</BranchName><ProductCode>444</ProductCode><ProductName>PDC Discounting</ProductName><AcctBalance>0</AcctBalance><CurrencyName>AED</CurrencyName><AccountType>CASA</AccountType></Account><Account><AcctType>SOW</AcctType><AccountNo>252535920003</AccountNo><AccountTitle>252535920003AC DESC</AccountTitle><AcctStatus>ACCOUNT DORMANT - CREDIT WITH OVERRIDE</AcctStatus><BranchCode>050</BranchCode><BranchName>RETAIL ASSET CENTER</BranchName><ProductCode>839</ProductCode><ProductName>Rental Collection and Loan Payment Acct</ProductName><AcctBalance>49.38</AcctBalance><CurrencyName>AED</CurrencyName><AccountType>CASA</AccountType></Account><Account><AcctType>SOW</AcctType><AccountNo>252535920007</AccountNo><AccountTitle>252535920007AC DESC</AccountTitle><AcctStatus>ACCOUNT DORMANT - NO DEBIT</AcctStatus><BranchCode>261</BranchCode><BranchName>KHALED BIN WALEED STREET</BranchName><ProductCode>20</ProductCode><ProductName>Current Account Commercial</ProductName><AcctBalance>-12761.85</AcctBalance><CurrencyName>AED</CurrencyName><AccountType>CASA</AccountType></Account><Account><AcctType>SOW</AcctType><AccountNo>00252535C003003</AccountNo><AccountTitle></AccountTitle><AcctStatus>Closed</AcctStatus><BranchCode>050</BranchCode><BranchName>RETAIL ASSET CENTER</BranchName><ProductCode>C00300</ProductCode><ProductName>CAR LOAN COMPANIES</ProductName><AcctBalance>0</AcctBalance><CurrencyName>AED</CurrencyName><AccountType>Loan</AccountType></Account><Account><AcctType>SOW</AcctType><AccountNo>00252535C003004</AccountNo><AccountTitle></AccountTitle><AcctStatus>Closed</AcctStatus><BranchCode>050</BranchCode><BranchName>RETAIL ASSET CENTER</BranchName><ProductCode>C00300</ProductCode><ProductName>CAR LOAN COMPANIES</ProductName><AcctBalance>0</AcctBalance><CurrencyName>AED</CurrencyName><AccountType>Loan</AccountType></Account><Account><AcctType>SOW</AcctType><AccountNo>00252535H011001</AccountNo><AccountTitle></AccountTitle><AcctStatus>Open</AcctStatus><BranchCode>050</BranchCode><BranchName>RETAIL ASSET CENTER</BranchName><ProductCode>H01100</ProductCode><ProductName>ABF GREATER THAN 3 YR</ProductName><AcctBalance>55304.8</AcctBalance><CurrencyName>AED</CurrencyName><AccountType>Loan</AccountType></Account><Account><AcctType>SOW</AcctType><AccountNo>252535956001</AccountNo><AccountTitle></AccountTitle><AcctStatus>Closed</AcctStatus><BranchCode>251</BranchCode><BranchName>AL RIGGAH ROAD</BranchName><ProductCode>C00300</ProductCode><ProductName>CAR LOAN COMPANIES</ProductName><AcctBalance>0</AcctBalance><CurrencyName>AED</CurrencyName><AccountType>Loan</AccountType></Account><Account><AcctType>SOW</AcctType><AccountNo>252535956002</AccountNo><AccountTitle></AccountTitle><AcctStatus>Closed</AcctStatus><BranchCode>251</BranchCode><BranchName>AL RIGGAH ROAD</BranchName><ProductCode>C00300</ProductCode><ProductName>CAR LOAN COMPANIES</ProductName><AcctBalance>0</AcctBalance><CurrencyName>AED</CurrencyName><AccountType>Loan</AccountType></Account><Account><AcctType>SOW</AcctType><AccountNo>252535958001</AccountNo><AccountTitle></AccountTitle><AcctStatus>Closed</AcctStatus><BranchCode>251</BranchCode><BranchName>AL RIGGAH ROAD</BranchName><ProductCode>C00300</ProductCode><ProductName>CAR LOAN COMPANIES</ProductName><AcctBalance>0</AcctBalance><CurrencyName>AED</CurrencyName><AccountType>Loan</AccountType></Account><Account><AcctType>SOW</AcctType><AccountNo>252535958002</AccountNo><AccountTitle></AccountTitle><AcctStatus>Closed</AcctStatus><BranchCode>251</BranchCode><BranchName>AL RIGGAH ROAD</BranchName><ProductCode>C00300</ProductCode><ProductName>CAR LOAN COMPANIES</ProductName><AcctBalance>0</AcctBalance><CurrencyName>AED</CurrencyName><AccountType>Loan</AccountType></Account></Accounts><Addresses><Address><Type>Correspondence Address</Type><AddressLine_1>ADDRESS3</AddressLine_1><AddressLine_2>ADDRESS4</AddressLine_2><BuildingName></BuildingName><City>DUBAI</City><CompanyName></CompanyName><Country>UNITED ARAB EMIRATES</Country><Email>Email@adcb.com</Email><Phone>HOME_TEL_NO</Phone><Mobile>1234567890</Mobile><State>DUBAI</State><POBox>ADDRESS2</POBox><ZipCode>6196</ZipCode></Address><Address><Type>Office Address</Type><AddressLine_1>ADDRESS2</AddressLine_1><AddressLine_2>ADDRESS3</AddressLine_2><BuildingName></BuildingName><City>DUBAI</City><CompanyName></CompanyName><Country>UNITED ARAB EMIRATES</Country><Email></Email><Phone>E_TELEPHONE</Phone><Mobile>E_TELEX</Mobile><State>DUBAI</State><POBox>ADDRESS1</POBox><ZipCode>6196</ZipCode></Address><Address><Type>Permanent Address</Type><AddressLine_1>ADDRESS2</AddressLine_1><AddressLine_2>ADDRESS3</AddressLine_2><BuildingName></BuildingName><City>DUBAI</City><CompanyName></CompanyName><Country>UNITED ARAB EMIRATES</Country><Email></Email><Phone></Phone><Mobile></Mobile><State>DUBAI</State><POBox>ADDRESS1</POBox><ZipCode>6196</ZipCode></Address><Address><Type>Residence Address</Type><AddressLine_1></AddressLine_1><AddressLine_2></AddressLine_2><BuildingName></BuildingName><City></City><CompanyName></CompanyName><Country></Country><Email></Email><Phone></Phone><Mobile></Mobile><State></State><POBox></POBox><ZipCode></ZipCode></Address></Addresses><CreditCards></CreditCards></listOfCustomer></GetCustomerInformationRes><TotalRetrieved>1</TotalRetrieved></Output>";
		}
		return sOutput;
	}

	@SuppressWarnings("unchecked")
	private void isVIPCustomerCheck(String cid) {
		try {
			String str = "SELECT COUNT(1) CNT FROM LG_VIP_MASTER WHERE CUSTOMER_ID='"+ cid + "'";
			log.info("isVIPCustomerCheck   " + str);
			List<List<String>> sOutputlist = formObject.getDataFromDB("SELECT COUNT(1) CNT FROM LG_VIP_MASTER WHERE CUSTOMER_ID = '"+ cid + "'");
			if (sOutputlist != null && !sOutputlist.isEmpty()
					&& !sOutputlist.get(0).isEmpty()
					&& Integer.parseInt(sOutputlist.get(0).get(0)) > 0) {
				formObject.setValue("IS_CUSTOMER_VIP", "Yes");
			} else {
				formObject.setValue("IS_CUSTOMER_VIP", "No");
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	@SuppressWarnings("unchecked")
	private String fetchContractData(String sContractRef,
			String sOperationName, String sBranchCode) {
		log.info("inside fetchContractData..");
		String sInput = "";
		String sOutput="";
		List<List<String>> sOutputlist = formObject.getDataFromDB("SELECT SEQ_WEBSERVICE.nextval as IDValue from DUAL ");
		if (!sOutputlist.isEmpty() && !sOutputlist.get(0).isEmpty()) {
			sInput = getTradeLCContractInputXML(sContractRef,
					sOutputlist.get(0).get(0), (formObject.getUserName().equals("") || formObject.getUserName() == null ? "TP906079" :
						formObject.getUserName()), sOperationName, sBranchCode);
			sOutput = socket.connectToSocket(sInput);			
		}
		return sOutput;
	}

	private Document getDocument(String xml) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new InputSource(new StringReader(xml)));
		return doc;
	}

	private String getTagValue(String xml, String tag)
			throws ParserConfigurationException, SAXException, IOException {
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
								sTempValue = sTempValue
										.replace("#amp#", "&");
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

	private String getFCRInputXML(String sCustID, String sSeqNo, String sUserName, String sTxn) {
		log.info("inside getFCRInputXML >>>");
		return "<?xml version=\"1.0\"?><APWebService_Input><Option>WebService</Option><Calltype>Customer_Information</Calltype>"
		+ "<Customer>"
		+ "<CUST_ID>"+ sCustID + "</CUST_ID>"
		+ "<REF_NO>"+ sSeqNo + "</REF_NO>"
		+ "<txnType>"+ sTxn	+ "</txnType>"
		+ "<USER>"+ sUserName + "</USER>"
		+ "<WiName>"+ this.sWorkitemID + "</WiName>"
		+ "</Customer>";
	}

	private String getTradeLCContractInputXML(String sContractRef,
			String sSeqNo, String sUserName, String sOperationName,
			String sBranchCode) {
		String sInputXML = "<?xml version=\"1.0\"?><APWebService_Input><Option>WebService</Option><Calltype>WS-2.0"
				+ "</Calltype><InnerCalltype>FCUBSTradeContractOps</InnerCalltype><Customer>"
				+ "<refNo>"
				+ sSeqNo
				+ "</refNo>"
				+ "<contractRefNo>"
				+ sContractRef
				+ "</contractRefNo>"
				+ "<branchCode>"
				+ sBranchCode
				+ "</branchCode>"
				+ "<USER>"
				+ sUserName
				+ "</USER>"
				+ "<serviceName>InqCustomerTradeJourney</serviceName>"
				+ "<senderID>WMS</senderID>"
				+ "<WiName>"
				+ this.sWorkitemID
				+ "</WiName>"
				+ "<operationType>"
				+ sOperationName
				+ "</operationType>" + "</Customer>";
		log.info("input XML: "+sInputXML);
		sInputXML+="<EngineName>" + engineName + "</EngineName>"
				+ "<SessionId>" + sessionId + "</SessionId>"
				+ "</APWebService_Input>";
		log.info("input XML: "+sInputXML);
		return sInputXML;
	}

	public String checkSplChar(String str) {
		try {
			if (str.indexOf("'") > -1) {
				str = str.replace("'", "''");
			}
			if (str.indexOf("&") > -1) {
				str = str.replace("&", "'||chr(38)||'");
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		return str;
	}

	public void disableControls(String controls) {
		String cName[] = controls.split(",");
		for (int i = 0; i < cName.length; i++) {
			log.info("disabling control: " + cName[i]);
			formObject.setStyle(cName[i], DISABLE, TRUE);
		}
	}

	public void enableControls(String controls) {
		String cName[] = controls.split(",");
		for (int i = 0; i < cName.length; i++) {
			log.info("enabling control: " + cName[i]);
			formObject.setStyle(cName[i], DISABLE, FALSE);
		}
	}

	public void clearControls(String controls) {
		String cName[] = controls.split(",");
		for (int i = 0; i < cName.length; i++) {
			log.info("clearing control: " + cName[i]);
			formObject.setValue(cName[i], "");
		}
	}

	public String getReturnMessage(Boolean success, String data, String message) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", success);
		jsonObject.put("data", data);
		jsonObject.put("message", message);
		log.info("jason value : "+jsonObject.toString());
		return jsonObject.toString();
	}

	public String getReturnMessage(Boolean success, String data) {
		return getReturnMessage(success, data, "");
	}

	public String getReturnMessage(Boolean success) {
		return getReturnMessage(success, "", "");
	}

	public JSONObject getReturnMessageJSON(Boolean success, String data, String message) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", success);
		jsonObject.put("data", data);
		jsonObject.put("message", message);
		return jsonObject;
	}

	protected void setAccountDetails(String responseCustomerInfo) {
		responseCustomerInfo = responseCustomerInfo.replace("null", "");
		log.info("responseCustomerInfo " + responseCustomerInfo);
		XMLParser xpOuter = new XMLParser(responseCustomerInfo);
		String sAccCASA = "", sAccType = "";
		String sReturnCode = xpOuter.getValueOf("returnCode");
		if ("0".equalsIgnoreCase(sReturnCode) || "2".equalsIgnoreCase(sReturnCode)) {
			String sAccountNo = "", sAccountTitle = "", sAccCurrency = "";
			XMLParser xpInner = new XMLParser(CUSTOMER_ID);
			accountListMap = new HashMap<String, List<String>>();
			int rowCount = 0;
			rowCount = xpOuter.getNoOfFields("Account");
			log.info("rowcount " + rowCount);
			List<String> lInner = null;
			for (int lstitemCount = 0; lstitemCount < rowCount; lstitemCount++) {
				if (lstitemCount == 0) {
					xpInner.setInputXML(xpOuter.getFirstValueOf("Account"));
				} else {
					xpInner.setInputXML(xpOuter.getNextValueOf("Account"));
				}
				log.info("Inner Parser " + xpInner);
				sAccountNo = xpInner.getValueOf("AccountNo");
				sAccountTitle = xpInner.getValueOf("AccountTitle");
				sAccCurrency = xpInner.getValueOf("CurrencyName");
				sAccCASA = xpInner.getValueOf("AccountType");
				sAccType = xpInner.getValueOf("AcctStatus");
				log.info("Account " + sAccountNo + " Curr " + sAccCurrency);
				lInner = new ArrayList<String>();
				lInner.add(sAccountTitle);
				lInner.add(sAccCurrency);
				if ("CASA".equalsIgnoreCase(sAccCASA) && ("ACCOUNT OPENED TODAY".equalsIgnoreCase(sAccType) 
						|| "ACCOUNT OPEN REGULAR".equalsIgnoreCase(sAccType))){
					accountListMap.put(sAccountNo, lInner);					
				}
			}
		}
		log.info("Final map " + accountListMap);
	}

	protected void loadAcountnumberLov(){
		try {
			if("IFS".equalsIgnoreCase(requestCategory) || "IFP".equalsIgnoreCase(requestCategory)|| 
					"TL".equalsIgnoreCase(requestCategory)	|| "IFA".equalsIgnoreCase(requestCategory)
					|| "SCF".equalsIgnoreCase(requestCategory)){//CODE FOR SCF REQ
				loadAccntNumber(INF_CHARGE_ACC_NUM);
				loadAccntNumber(INF_SETTLEMENT_ACC_NUM);
				loadAccntNumber(INF_CREDIT_ACC_NUM);
			}
			else if("GRNT".equalsIgnoreCase(requestCategory)|| "SBLC".equalsIgnoreCase(requestCategory) || "ELC".equalsIgnoreCase(requestCategory) || "ILC".equalsIgnoreCase(requestCategory)
					// Shipping_gtee_28 //RR
					|| "SG".equalsIgnoreCase(requestCategory)){
				loadAccntNumber(ACCOUNT_NUMBER);
			}
			else if("EC".equalsIgnoreCase(requestCategory) || "ELCB".equalsIgnoreCase(requestCategory) || "IC".equalsIgnoreCase(requestCategory)
					|| "DBA".equalsIgnoreCase(requestCategory) || "ILCB".equalsIgnoreCase(requestCategory)){
				loadAccntNumber(INF_CHARGE_ACC_NUM);
				loadAccntNumber(INF_SETTLEMENT_ACC_NUM);					
			}			
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	protected void loadAccntNumber(String sControlName) {		
		formObject.clearCombo(sControlName);
		for (String sKey : accountListMap.keySet()) {
			formObject.addItemInCombo(sControlName, sKey, sKey);
			setTitleCurrency(sControlName, "GRNT_CHRG_ACC_TITLE", "GRNT_CHRG_ACC_CRNCY");
		}		
	}

	protected void setTitleCurrency(String sAccountCtrl, String sTitleControlName, String sCurrencyCtrl) {
		try {
			String sAccntNo = "";
			sAccntNo = (String) formObject.getValue(sAccountCtrl);
			log.info("Account " + sAccntNo );//ATP 153 Krishna
			if (!(sAccountCtrl == null || sAccntNo.isEmpty()|| "".equalsIgnoreCase(sAccntNo) || "0".equalsIgnoreCase(sAccntNo))) {
				formObject.setValue(sTitleControlName, accountListMap.get(sAccntNo).get(0));
				formObject.setValue(sCurrencyCtrl, accountListMap.get(sAccntNo).get(1));
			} else {
				clearControls(sTitleControlName, sCurrencyCtrl);
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	protected void clearControls(String... controlName) {
		for (String sTemp : controlName){
			log.info("clearing control: " +sTemp);
			try {
				formObject.setValue(sTemp, "");
			} catch (Exception e) {
				log.error("Exception: ",e);
			}
		}		
	}

	protected void decisionValidation(String sControlName){
		try {
			log.info("************************Inside decisionValidation****************************");
			boolean sReferalFlag = false;
			sReferalFlag = getReferalflagStatus();
			log.info("sReferal Flag returned from getReferalflagStatus()"+sReferalFlag +" Activity Name "+this.sActivityName);
			if("PROCESSING MAKER".equalsIgnoreCase(this.sActivityName)){	
				String trsdFlag = formObject.getValue(TRSD_FLAG).toString();
				if(("GA".equalsIgnoreCase(requestType) || "GAA".equalsIgnoreCase(requestType) )){
					if(sReferalFlag || "Y".equalsIgnoreCase(trsdFlag)){
						loadDecision("TXNC,RET,TXNAU,STBPD", sControlName);
						formObject.setValue(sControlName, "REF"); 
					} else{
						loadDecision("REF,RET,TXNAU,STBPD", sControlName);
						formObject.setValue(sControlName, TXNC); 
					}
				} else if("IFS".equalsIgnoreCase(requestCategory) || "IFP".equalsIgnoreCase(requestCategory)
						|| "IFA".equalsIgnoreCase(requestCategory) || "SCF".equalsIgnoreCase(requestCategory )){//CODE FOR SCF REQ
					String	sUTCGetDoc = formObject.getValue("IS_GETDOCUMENT_UTC_DONE").toString();
					log.info("Inside IS_GETDOCUMENT_UTC_DONE is sUTCGetDoc"+sUTCGetDoc);
					if("N".equalsIgnoreCase(sUTCGetDoc)){
						log.info("Inside IS_GETDOCUMENT_UTC_DONE is N");
						sReferalFlag = true;
					}
					if(sReferalFlag ||"Y".equalsIgnoreCase(trsdFlag)){
						loadDecision("TXNC,RET,REJ,TXNAU,STBPD", sControlName);
					}else{
						loadDecision("REF,REJ,TXNAU,STBPD", sControlName);
					}
					log.info("Inside sReferalFlag is :>>>>"+sReferalFlag);
				} else if("SBLC".equalsIgnoreCase(requestCategory) && "SBLC_CR".equalsIgnoreCase(requestType)){//CODE BY MOKSH
					if(sReferalFlag || "Y".equalsIgnoreCase(trsdFlag)){
						loadDecision("TXNC,TXNAU,REJ,STBPD", sControlName);
					}else{
						loadDecision("REF,REJ,TXNAU", sControlName);
					}
				} 
				else if(("GRNT_MSM".equalsIgnoreCase(requestType))||("EC_MSM".equalsIgnoreCase(requestType))||("IC_MSM".equalsIgnoreCase(requestType))||("ELC_MSM".equalsIgnoreCase(requestType))    //US3473
						||("ILC_MSM".equalsIgnoreCase(requestType))||("ELCB_MSM".equalsIgnoreCase(requestType))||("ILCB_MSM".equalsIgnoreCase(requestType))||("IFP_MSM".equalsIgnoreCase(requestType))
						||("IFS_MSM".equalsIgnoreCase(requestType))||("TL_MSM".equalsIgnoreCase(requestType))||("ELC_PA".equalsIgnoreCase(requestType))||("ELCB_AOR".equalsIgnoreCase(requestType))
						||("ILCB_AOD".equalsIgnoreCase(requestType))||("ILCB_AOP".equalsIgnoreCase(requestType))||("SBLC_MSM".equalsIgnoreCase(requestType))){
					loadDecision("REF,REJ,TXNC,RET,STBPD", sControlName);
				}else{
					if(sReferalFlag || "Y".equalsIgnoreCase(trsdFlag)){
						loadDecision("TXNC,RET,REJ,TXNAU,STBPD", sControlName);//STBP added by mansi
					} else{
						loadDecision("REF,REJ,TXNAU,STBPD", sControlName);
					}
				}
			}
			else if("PROCESSING CHECKER".equalsIgnoreCase(this.sActivityName)){
				log.info("requestType:"+requestType);
				log.info("values:"+(!("GRNT_SD".equalsIgnoreCase(requestType))));
				if(("GA".equalsIgnoreCase(requestType) || "GAA".equalsIgnoreCase(requestType) )){
					loadDecision("RPPM,DOCSSCAN,TXNAU", sControlName); //US3007  DOCSSCAN added US3473
				}
				else if (("GRNT_SD".equalsIgnoreCase(requestType) ||"IFP_SD".equalsIgnoreCase(requestType)||"ELCB_SD".equalsIgnoreCase(requestType)||"ILC_SD".equalsIgnoreCase(requestType)
						||"ELC_SD".equalsIgnoreCase(requestType)||"IC_SD".equalsIgnoreCase(requestType)||"TL_SD".equalsIgnoreCase(requestType)||
						"IFS_SD".equalsIgnoreCase(requestType)||"EC_SD".equalsIgnoreCase(requestType)||"ILCB_SD".equalsIgnoreCase(requestType)||"DBA_SD".equalsIgnoreCase(requestType)
						||"SG_SD".equalsIgnoreCase(requestType))||"SBLC_SD".equalsIgnoreCase(requestType))//added by mansi
				{
					log.info("setting DOCSSCAN");
					loadDecision("TXNAC,RPM,RPPM,TXNAU", sControlName); 
					formObject.setValue(sControlName, "DOCSSCAN");
				} //end 
				else if(("GRNT_MSM".equalsIgnoreCase(requestType))||("SBLC_MSM".equalsIgnoreCase(requestType))||("EC_MSM".equalsIgnoreCase(requestType))||("IC_MSM".equalsIgnoreCase(requestType))||("ELC_MSM".equalsIgnoreCase(requestType))    //US3473
						||("ILC_MSM".equalsIgnoreCase(requestType))||("ELCB_MSM".equalsIgnoreCase(requestType))||("ILCB_MSM".equalsIgnoreCase(requestType))||("IFP_MSM".equalsIgnoreCase(requestType))
						||("IFS_MSM".equalsIgnoreCase(requestType))||("TL_MSM".equalsIgnoreCase(requestType))||("ELC_PA".equalsIgnoreCase(requestType))||("ELCB_AOR".equalsIgnoreCase(requestType))
						||("ILCB_AOD".equalsIgnoreCase(requestType))||("ILCB_AOP".equalsIgnoreCase(requestType)))//added by mansi
				{
					loadDecision("TXNAC,RPPM,DOCSSCAN", sControlName);	
				}
				else {
					loadDecision("DOCSSCAN,TXNAU", sControlName);
				}
			}
			else if("RM".equalsIgnoreCase(this.sActivityName)&&"IFCPC".equalsIgnoreCase(requestCategory))
			{
				loadDecision("INFO", DEC_CODE); 
			}
			else if("RM".equalsIgnoreCase(this.sActivityName)
					|| "CUSTOMER_REVIEW".equalsIgnoreCase(this.sActivityName)
					|| "TSD".equalsIgnoreCase(this.sActivityName)
					|| "LEGAL_TEAM".equalsIgnoreCase(this.sActivityName)
					|| "Treasury".equalsIgnoreCase(this.sActivityName)
					|| "Trade Sales".equalsIgnoreCase(this.sActivityName)
					|| "TB Sales".equalsIgnoreCase(this.sActivityName)
					|| "CORRESPONDENT_BANK".equalsIgnoreCase(this.sActivityName)
					|| "COMPLIANCE".equalsIgnoreCase(this.sActivityName)
					|| "PM Processing System".equalsIgnoreCase(this.sActivityName)
					|| "PC Processing System".equalsIgnoreCase(this.sActivityName)
					|| "Customer Referral Response".equalsIgnoreCase(this.sActivityName)
					|| "Compliance Referral Response".equalsIgnoreCase(this.sActivityName))
			{
				loadDecision("", DEC_CODE); 
			}
			else if("DELIVERY".equalsIgnoreCase(this.sActivityName)){
				if("GRNT".equalsIgnoreCase(requestCategory)||"SBLC".equalsIgnoreCase(requestCategory)|| ("ELC".equalsIgnoreCase(requestCategory) && "ELC_SLCA".equalsIgnoreCase(requestType)||"ELC_SLCAA".equalsIgnoreCase(requestType)
						|| "ELC_SER".equalsIgnoreCase(requestType)|| "ELC_SCL".equalsIgnoreCase(requestType)))//ELC added by mansi
					loadDecision("DDDCB,DDOCC", sControlName);

				else if("IC".equalsIgnoreCase(requestCategory)||"ILCB".equalsIgnoreCase(requestCategory)||"ILC".equalsIgnoreCase(requestCategory)
						||"EC".equalsIgnoreCase(requestCategory)||"ELCB".equalsIgnoreCase(requestCategory)||"ELC".equalsIgnoreCase(requestCategory)
						||"DBA".equalsIgnoreCase(requestCategory))
					loadDecision("DCBC,TISE", sControlName);
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		log.info("************************Exiting decisionValidation****************************");
	}

	protected boolean getReferalflagStatus(){
		try {
			if("PP_MAKER".equalsIgnoreCase(this.sActivityName)){
				String sRMFlag=formObject.getValue(IS_RM_PPM).toString();
				String sTSDFlag=formObject.getValue(IS_REF_PPM).toString(); 
				String sLEGALFlag=formObject.getValue(IS_LEGAL_PPM).toString();
				String sCRFlag=formObject.getValue(IS_CR_PPM).toString();
				String sCBFlag=formObject.getValue(IS_CB_PPM).toString();
				String sTradeFlag=formObject.getValue(IS_TRADE_PPM).toString();
				if("Y".equalsIgnoreCase(sRMFlag) || "Y".equalsIgnoreCase(sTSDFlag) || "Y".equalsIgnoreCase(sLEGALFlag) || "Y".equalsIgnoreCase(sCRFlag) || "Y".equalsIgnoreCase(sCBFlag)|| "Y".equalsIgnoreCase(sTradeFlag))
					return true;
				else
					return false;
			}
			else if("PROCESSING MAKER".equalsIgnoreCase(this.sActivityName)){
				String sPMFlag = "";
				String sTreasuryFlag = "";
				sPMFlag = formObject.getValue(COMP_REF).toString();
				sTreasuryFlag = formObject.getValue(TR_REFER_TREASURY).toString();
				if("Y".equalsIgnoreCase(sPMFlag) || "Y".equalsIgnoreCase(sTreasuryFlag))
					return true;
				else
					return false;
			}
			else 
				return true;
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		return true;
	}

	protected void loadDecision(String sNotIn, String sControlName){
		try {
			String sDec ="";
			boolean flagDecExist=false;
			sDec = formObject.getValue(sControlName).toString();
			log.info("Decision Value  -- "+sDec);
			formObject.clearCombo(sControlName);
			log.info("loaddecision sNotIn: "+sNotIn);
			log.info("decisionMap.get(sActivityName): "+decisionMap.get(sActivityName));
			log.info("decisionMap.get(sActivityName).size(): "+decisionMap.get(sActivityName).size());
			for(Map.Entry<String, String> entry: decisionMap.get(sActivityName).entrySet()){
				log.info("inside for loop load decision: ");
				log.info("description: "+entry.getValue()+", code:"+entry.getKey());
				if(sNotIn.contains(entry.getKey())){
					log.info("not adding: "+entry.getValue());
					continue;
				}
				else{
					log.info("adding label: "+entry.getValue()+", code: "+entry.getKey());
					formObject.addItemInCombo(sControlName, entry.getValue(),entry.getKey());
					List tempList=new ArrayList();
					tempList.add(entry.getKey());
					tempList.add(entry.getValue());
					lstDec.add(tempList);
					log.info("lstDec list="+lstDec);
					if(sDec.equals(entry.getKey())){
						flagDecExist=true;
					}
				}
			}
			log.info("flagDecExist="+flagDecExist);
			if(flagDecExist){
				log.info("setting the value of decision stored in DB ..");
				formObject.setValue(sControlName, sDec);
			}
			else {
				formObject.setValue(sControlName, "");
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
			log.error("Exception in",e);
		}
	}

	public void loadProdLov(String srcChannel, String strTrnBrn, List<List<String>> listVal, String controlName) {
		log.info("loadProdLov  srcChannel  >>" + srcChannel + "  <<<strTrnBrn >>>" + strTrnBrn + " controlName " + controlName);
		try {
			boolean addFlag = false;
			log.info(" requestType "+ requestType + " requestCategory" + requestCategory);
			if(prdLoadCheck(requestCategory,requestType) ){ //This is used to GRNT Product
				log.info("Product List Values " + listVal.size());
				if (null !=listVal  && !listVal.isEmpty()) {
					String temp = formObject.getValue(controlName).toString();
					log.info("Control value in loadProdLov block  in issuance code"+ temp);					
					formObject.clearCombo(controlName);				
					for (int counter = 0; counter < listVal.size(); counter++) {
						if("ILC_UM".equalsIgnoreCase(requestType)){
							log.info("product code"+ listVal.get(counter).get(2));					
                            if("ILS6".equalsIgnoreCase(listVal.get(counter).get(2))){
								addFlag=true;
							}else{
								addFlag=false;
							}
						}else{
							addFlag=true;
						}
						
						if(addFlag){
							log.info("Inside addflag");
							log.info("listVal.get(counter).get(4) :"+listVal.get(counter).get(4));
							log.info("listVal.get(counter).get(0) :"+listVal.get(counter).get(0));
							log.info("listVal.get(counter).get(1) :"+listVal.get(counter).get(1));
							log.info("listVal.get(counter).get(2) :"+listVal.get(counter).get(2));
							log.info("listVal.get(counter).get(3) :"+listVal.get(counter).get(3));
							
							
						if ( requestCategory.equalsIgnoreCase(listVal.get(counter).get(4)) 
								&& !"S".equalsIgnoreCase(srcChannel) && srcChannel.equalsIgnoreCase(listVal.get(counter).get(0))
								&& strTrnBrn.equalsIgnoreCase(listVal.get(counter).get(1))) {
							log.info("Inside IF 1");
							formObject.addItemInCombo(controlName, listVal.get(counter).get(3), listVal.get(counter).get(2));
						} 
						else if ( requestCategory.equalsIgnoreCase(listVal.get(counter).get(4))
								&& "S".equalsIgnoreCase(srcChannel) && srcChannel.equalsIgnoreCase(listVal.get(counter).get(0))
								&&"ALL".equalsIgnoreCase(listVal.get(counter).get(1))) {
							log.info("Inside IF 2");
							formObject.addItemInCombo(controlName, listVal.get(counter).get(3), listVal.get(counter).get(2));
						}else if ( requestCategory.equalsIgnoreCase(listVal.get(counter).get(4))
								&& "S".equalsIgnoreCase(srcChannel) && srcChannel.equalsIgnoreCase(listVal.get(counter).get(0))
								&& strTrnBrn.equalsIgnoreCase(listVal.get(counter).get(1))) {
							log.info("Inside IF 3");
							formObject.addItemInCombo(controlName, listVal.get(counter).get(3), listVal.get(counter).get(2));
						}
						else if ((requestCategory.equalsIgnoreCase(listVal.get(counter).get(4))) 
								&& ("GA".equalsIgnoreCase(requestType) || "GAA".equalsIgnoreCase(requestType))
								&& ("NA".equalsIgnoreCase(listVal.get(counter).get(0))) 
								&& ("NA".equalsIgnoreCase(listVal.get(counter).get(1)))) {
							log.info("Inside IF 4");
							formObject.addItemInCombo(controlName, listVal.get(counter).get(3), listVal.get(counter).get(2));
						}
						else if (requestCategory.equalsIgnoreCase(listVal.get(counter).get(4)) && 
								(!"ELC_SLCA".equalsIgnoreCase(requestType)) &&
								"NA".equalsIgnoreCase(listVal.get(counter).get(0)) && strTrnBrn.equalsIgnoreCase(listVal.get(counter).get(1))) {
							formObject.addItemInCombo(controlName, listVal.get(counter).get(3), listVal.get(counter).get(2));
							log.info("not equal to ELC_SLCA:" + requestCategory);
							log.info("not equal to SLCA:" + requestCategory);
						}
					}
				}
					if(null !=temp)
						formObject.setValue(controlName, temp);
				}
			}
			else if(!prdLoadCheck(requestCategory , requestType)	){ // This is used to load product than issuance or similar to issuance case
				String sQuery = "Select CODE, CODE_DESC From TFO_DJ_PRDCODE_DESC_MAST";
				log.info("Query not Issuance "+sQuery);
				List<List<String>> sResultSet= formObject.getDataFromDB(sQuery);
				if (  null != sResultSet && !sResultSet.isEmpty()) {
					String temp = formObject.getValue(controlName).toString();
					log.info("Control value in loadProdLov block other than issuance code" +temp);
					formObject.clearCombo(controlName);

					for (int counter = 0; counter < sResultSet.size(); counter++) {
						formObject.addItemInCombo(controlName, sResultSet.get(counter).get(1), sResultSet.get(counter).get(0));
					}
					if(null !=temp)
						formObject.setValue(controlName, temp);
				}
			}
			else{
				log.info("Conditions not matched");
			}
		} catch (Exception e) {
			log.error("exception in loadProdLov: " , e);
		}
	}

	private boolean prdLoadCheck(String reqCat,String reqTy){
		log.info("Inside prdCheck load >> req cat   >> "+reqCat + " <<<   reqTy   >>" +reqTy);
		if(("GRNT".equalsIgnoreCase(reqCat) && "NI".equalsIgnoreCase(reqTy))
				|| ("SBLC".equalsIgnoreCase(reqCat) && "SBLC_NI".equalsIgnoreCase(reqTy))//RR
				|| ("ELC".equalsIgnoreCase(reqCat) && "ELC_SLCA".equalsIgnoreCase(reqTy))//added by Mansi
				|| ("IFS".equalsIgnoreCase(reqCat) && "LD".equalsIgnoreCase(reqTy))
				|| ("IFP".equalsIgnoreCase(reqCat) && "LD".equalsIgnoreCase(reqTy))
				|| ("SCF".equalsIgnoreCase(reqCat) && ("PD".equalsIgnoreCase(reqTy) ||"PDD".equalsIgnoreCase(reqTy)))//CODE FOR SCF 
				|| ("IFA".equalsIgnoreCase(reqCat) && "LD".equalsIgnoreCase(reqTy))//CODE BY MOKSH
				|| ("IC".equalsIgnoreCase(reqCat) && "IC_BL".equalsIgnoreCase(reqTy))
				|| ("DBA".equalsIgnoreCase(reqCat) && "DBA_BL".equalsIgnoreCase(reqTy))
				|| ("EC".equalsIgnoreCase(reqCat) && ("EC_BL".equalsIgnoreCase(reqTy) || "EC_LDDI".equalsIgnoreCase(reqTy) ))				
				|| ("ILCB".equalsIgnoreCase(reqCat) && "ILCB_BL".equalsIgnoreCase(reqTy))
				|| ("ELCB".equalsIgnoreCase(reqCat) && "ELCB_BL".equalsIgnoreCase(reqTy))				
				|| ("ILC".equalsIgnoreCase(reqCat) && ("ILC_NI".equalsIgnoreCase(reqTy) || "ILC_UM".equalsIgnoreCase(reqTy)))//new
				|| ("ELC".equalsIgnoreCase(reqCat) && "ELC_LCA".equalsIgnoreCase(reqTy))
				|| ("TL".equalsIgnoreCase(reqCat) && "TL_LD".equalsIgnoreCase(reqTy))				
				|| ("SG".equalsIgnoreCase(reqCat) && ("SG_NILC".equalsIgnoreCase(reqTy) || "SG_NIC".equalsIgnoreCase(reqTy))) //Shipping GTEE 27
				){
			log.info("Inside prdCheck load >> issuance case or similar to issuance");
			return true;
		}
		else{
			log.info("Inside prdCheck load >> not - issuance case or similar to issuance");
			return false;		
		}
	}

	public void loadLovOnCond(String cond, String controlName) {
		try {
			log.info("loadLovOnCond in"+strAmendmentTnr.length());
			String sTenor="";
			sTenor = formObject.getValue(TRN_TENOR).toString();
			boolean flg = false;
			if( "GAA".equalsIgnoreCase(requestType)){
				if(!("".equalsIgnoreCase(sTenor)  || "0".equalsIgnoreCase(sTenor))){
					log.info("Checking [TRN_TENOR]==>[TRUE returned]");
					formObject.setStyle(AMEND_TYPE, DISABLE, FALSE);
					flg = true;
				}
			}
			if ("AM".equalsIgnoreCase(requestType) ||"SBLC_AM".equalsIgnoreCase(requestType) || ("GAA".equalsIgnoreCase(requestType) && flg)) {
				log.info("inside if block");
				if (strAmendmentTnr.length() > 0) {
					String temp = formObject.getValue(controlName).toString();
					log.info("Controm value in loadLovOnCond block " +temp);
					log.info("cond value  " +cond);
					formObject.clearCombo(controlName);
					log.info("strAmendmentTnr ::" +strAmendmentTnr);
					String[] tempArr = strAmendmentTnr.split("#~#");
					log.info("tempArr values  " +tempArr);
					for (int counter = 0; counter < tempArr.length; counter++) {
						if (cond.equalsIgnoreCase(tempArr[counter].split("###")[1])) {
							formObject.addItemInCombo(controlName, tempArr[counter].split("###")[0], getAmndCode(tempArr[counter].split("###")[0]));
						}
					}
					if("".equals(temp)){
						formObject.setValue(controlName, temp);
					}
				}
			}
			else if( "TL_AM".equalsIgnoreCase(requestType)
					|| "ILC_AM".equalsIgnoreCase(requestType) 
					|| "ELC_LCAA".equalsIgnoreCase(requestType) && "PP_MAKER".equalsIgnoreCase(controlName) ){

				formObject.setStyle(controlName, ENABLE, TRUE);
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	public void checkOnTrnstenor(String controlName) {
		try {
			String strReqType =  formObject.getValue(REQUEST_TYPE).toString();
			log.info("checkOnTrnstenor strReqType  " + strReqType);
			String strReqCat =  formObject.getValue(REQUEST_CATEGORY).toString();
			if("GRNT".equalsIgnoreCase(strReqCat)||"SBLC".equalsIgnoreCase(strReqCat)){ //RR
				if ("FD".equalsIgnoreCase(formObject.getValue(controlName).toString()) ||
						"COND".equalsIgnoreCase(formObject.getValue(controlName).toString())) {	//mansi				
					formObject.setStyle(FIELD_GRNTEE_EXP_DATE, DISABLE, FALSE);
				} else {
					formObject.setStyle(FIELD_GRNTEE_EXP_DATE, DISABLE, TRUE);
					formObject.setValue(FIELD_GRNTEE_EXP_DATE, "");
				} 
			}
			if ("NI".equalsIgnoreCase(strReqType)||"SBLC_NI".equalsIgnoreCase(strReqType)
					||"ELC_SLCA".equalsIgnoreCase(strReqType)) {	//RR
				log.info(" issue case " + formObject.getValue(controlName));
				if ("OE".equalsIgnoreCase(formObject.getValue(controlName).toString())) {
					formObject.setValue(EXP_DATE, "");
					formObject.setStyle(EXP_DATE, DISABLE, TRUE);
					formObject.setStyle(EXPIRY_COND, DISABLE, TRUE);//mansi
				}else if ("FD".equalsIgnoreCase(formObject.getValue(controlName).toString())) {
					formObject.setStyle(EXP_DATE, DISABLE, FALSE);
					formObject.setStyle(FIELD_GRNTEE_EXP_DATE, DISABLE, FALSE);
					formObject.setStyle(EXPIRY_COND, DISABLE, TRUE);//mansi
				}else if("COND".equalsIgnoreCase(formObject.getValue(controlName).toString())){
					log.info(" BEFORE Disabling EXP_DATE FIELD CHECK 1");
					//formObject.setValue(EXP_DATE, "");
					formObject.setStyle(EXP_DATE, DISABLE, FALSE);
					formObject.setStyle(FIELD_GRNTEE_EXP_DATE, DISABLE, FALSE);
					formObject.setStyle(EXPIRY_COND, DISABLE, FALSE);//mansi
					log.info(" After Disabling EXP_DATE FIELD");
				} 
			}
			else if ("ILC_UM".equalsIgnoreCase(strReqType)){	//RR for conditional changes
				if ("OE".equalsIgnoreCase(formObject.getValue(controlName).toString())|| 
						"COND".equalsIgnoreCase(formObject.getValue(controlName).toString())) {
					formObject.setValue(EXP_DATE, "");

					formObject.setStyle(EXP_DATE, DISABLE, TRUE);
				}else if ("FD".equalsIgnoreCase(formObject.getValue(controlName).toString())) {

					formObject.setStyle(EXP_DATE, DISABLE, FALSE);
				}
			}
			
			//else if ("ILC_NI".equalsIgnoreCase(strReqType) || "ILC_UM".equalsIgnoreCase(strReqType)) {
			else if ("ILC_NI".equalsIgnoreCase(strReqType)) {
				if(!"Y".equalsIgnoreCase(formObject.getValue(PT_UTILITY_FLAG).toString()))
					formObject.setStyle(EXP_DATE, DISABLE, FALSE);
			}else if ("ELC_LCA".equalsIgnoreCase(strReqType) ) {
				formObject.setStyle(EXP_DATE, DISABLE, FALSE);
			}else {
				log.info("Other than issue---" +formObject.getValue(controlName));				
				formObject.setStyle(EXP_DATE, DISABLE, TRUE);
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	private String getAmndCode(String desc) {
		String result = "";
		try {
			log.info("result" + result);
			log.info("des" + desc);
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
			log.info("Exception in getAmndCode",e);
		}
		return result;
	}


	public void disableFieldOnDemand(String param) {
		try {
			String[] frames = param.split("####");
			for (int counter = 0; counter < frames.length; counter++) {
				log.info("disable on demand--"+frames[counter]);
				formObject.setStyle(frames[counter], DISABLE, TRUE);
			}
		} catch (NumberFormatException e) {
			log.error("Exception: ",e);
		}
	}

	public void enableFieldOnDemand(String param) {
		try {			
			String[] frames = param.split("####");
			for (int counter = 0; counter < frames.length; counter++) {
				formObject.setStyle(frames[counter], DISABLE, FALSE);
			}
		} catch (NumberFormatException e) {
			log.error("Exception: ",e);
		}
	}

	public void LoadListView(List recordList,String colnames, String sCtrolName) {
		log.info("In loadlist view " + recordList.size()+",colnames="+colnames+",sCtrolName="+sCtrolName);
		try {

			log.info("In loadlist view "+recordList);
			JSONArray jsonArray = new JSONArray();
			String[] columnArray=colnames.split(",");
			log.info("In loadlist view "+columnArray.length);
			if(recordList.size()>0){
				formObject.clearTable(sCtrolName);
			}
			for (int i = 0; i < recordList.size(); i++) {
				JSONObject jsonObject = new JSONObject();

				for(int j=0;j<columnArray.length ;j++){
					log.info("In loadlist view "+((String) ((List) recordList.get(i)).get(j)));
					jsonObject.put(columnArray[j],((String) ((List) recordList.get(i)).get(j)));
				}
				jsonArray.add(jsonObject);
			}
			formObject.addDataToGrid(sCtrolName, jsonArray);
		} catch (Exception e) {
			log.error("Exception: ",e);
			log.error("Exception in",e);
		}
	}

	public static String normalizeString(String str) {
		try {
			if (str == null) {
				return "";
			}
			if (str.trim().equalsIgnoreCase("null")) {
				return "";
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		return str.trim();
	}

	public void amountFormat(String controlName,String curVal , String currencyFieldName) {
		try {
			log.info("amounntFormat controlName "+ controlName+ "  curVal " +curVal);
			String fieldValue = normalizeString(formObject.getValue(controlName).toString());			
			log.info("amounntFormat fieldValue "+ fieldValue);
			if(!(null == fieldValue
					|| "null".equalsIgnoreCase(fieldValue) 
					|| "".equalsIgnoreCase(fieldValue)) ){
				fieldValue = fieldValue.replace(",", "");
				try {
					BigDecimal number = new BigDecimal(fieldValue);
					fieldValue = number.stripTrailingZeros().toPlainString();
					log.info("Format removed 123412234" + number.stripTrailingZeros());	
					if(null==curVal || "".equalsIgnoreCase(curVal) || "null".equalsIgnoreCase(curVal) || (null!=curVal && curVal.trim().isEmpty()) ){
						formObject.setValue(controlName, "");
					} else{
						formObject.setValue(controlName, retAmountWithCurrFormat(fieldValue, checkAmtDigit(curVal,currencyFieldName)));
					}
				} catch (Exception ex) {
					formObject.setValue(controlName, "");
					log.error("Exception in amountFormat",ex);
				}
			} else{
				formObject.setValue(controlName, "");
			}
		} catch (Exception e) {
			formObject.setValue(controlName, "");
			log.error("Exception: ",e);
			log.error("Exception in amountFormat",e);
		}
	}

	public void finalDecisionHandling(String controlName, String sDecReason) {
		try {
			log.info("*********** inside finalDecisionHandling *************");
			String sFinalDecision ="";
			sFinalDecision= formObject.getValue(controlName).toString();
			log.info("Final Decision "+sFinalDecision);
			if("PROCESSING MAKER".equalsIgnoreCase(this.sActivityName)) {
				log.info("inside activity PM..");
				if(("GA".equalsIgnoreCase(requestType) || "GAA".equalsIgnoreCase(requestType) )) {
					if("REJ".equalsIgnoreCase(sFinalDecision)) {
						loadReasonCombo(sFinalDecision,sDecReason);
					} else {
						setSelectedDisable(sDecReason);	
						formObject.setStyle(sDecReason,DISABLE,TRUE);
					}
				} else {
					setSelectedDisable(sDecReason);	
					formObject.setStyle(sDecReason,DISABLE,TRUE);
				}
				String sRequestType ="";
				formObject.setValue(FINAL_DESC_PPM, getListValFromCode(lstDec, sFinalDecision));
				sRequestType =formObject.getValue(REQUEST_TYPE).toString();
				log.info("sRequestType "+sRequestType);
				if("GRNT".equalsIgnoreCase(requestCategory)) {
					if("NI".equalsIgnoreCase(sRequestType) ||"GA".equalsIgnoreCase(sRequestType)) {
						formObject.setValue(FCUBS_CON_NO, "");
						formObject.setStyle(FCUBS_CON_NO,DISABLE,FALSE);
					}
					else {
						formObject.setValue(FCUBS_CON_NO, formObject.getValue(TRANSACTION_REFERENCE).toString());
						formObject.setStyle(FCUBS_CON_NO,DISABLE,TRUE);
					}
				}else if("SBLC".equalsIgnoreCase(requestCategory)) {	//RR
					if("SBLC_NI".equalsIgnoreCase(sRequestType)) {
						formObject.setValue(FCUBS_CON_NO, "");
						formObject.setStyle(FCUBS_CON_NO,DISABLE,FALSE);
					}
					else {
						formObject.setValue(FCUBS_CON_NO, formObject.getValue(TRANSACTION_REFERENCE).toString());
						formObject.setStyle(FCUBS_CON_NO,DISABLE,TRUE);
					}
				}
				else if("ELC".equalsIgnoreCase(requestCategory)){
					if("ELC_SLCA".equalsIgnoreCase(requestType)) {
						formObject.setValue(FCUBS_CON_NO, "");
						formObject.setStyle(FCUBS_CON_NO,DISABLE,FALSE);//code added by mansi
					}
					formObject.setValue(FCUBS_CON_NO,formObject.getValue(TRANSACTION_REFERENCE).toString());
					formObject.setStyle(FCUBS_CON_NO,DISABLE,FALSE);
				}
				if(!"RET".equalsIgnoreCase(sFinalDecision)) {
					formObject.setValue(REMARKS, "");
				}
				else {
					formObject.setValue(REMARKS, "");
				}
			} else if("PROCESSING CHECKER".equalsIgnoreCase(this.sActivityName)) {
				String set_DESC_PPM=getDescriptionFromCode(controlName, formObject.getValue(controlName).toString());
				log.info("PC: set FINAL_DESC_PPM: "+set_DESC_PPM);
				formObject.setValue(FINAL_DESC_PPM, set_DESC_PPM);
				formObject.setStyle(sDecReason,DISABLE,TRUE);
				if(!("RPM".equalsIgnoreCase(sFinalDecision)||"RPPM".equalsIgnoreCase(sFinalDecision))) {
					log.info("Final Decision "+sFinalDecision);
					formObject.setValue(REMARKS, "");
				} else {
					formObject.setValue(REMARKS, sRemarksPC);
				}
			} else if("PP_MAKER".equalsIgnoreCase(this.sActivityName)) {
				formObject.setValue(FINAL_DESC_PPM, getListValFromCode(lstDec, sFinalDecision));
				if(!sFinalDecision.isEmpty()) {
					if("REJ".equalsIgnoreCase(sFinalDecision)) {
						loadReasonCombo(sFinalDecision,sDecReason);
					} else {
						setSelectedDisable(sDecReason);	
						formObject.setStyle(sDecReason,DISABLE,TRUE);
					}
				} else {
					setSelectedDisable(sDecReason);	
					formObject.setStyle(sDecReason,DISABLE,TRUE);
				}
			}
			log.info("***********finalDecisionHandling END*************");
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	protected void loadReasonCombo(String decCode, String sDecReason) {
		try {
			String sReason = "";
			sReason = formObject.getValue(sDecReason).toString();
			log.info("sReason "+sReason);
			formObject.setStyle(sDecReason,DISABLE,FALSE);
			log.info(" dec code " + decCode);
			if(reasonMap.get(sActivityName).get(decCode)!=null) {
				log.info(" dec code " + decCode  +  " reasons " + reasonMap.get(sActivityName).get(decCode));
				formObject.clearCombo(sDecReason);
				for(int counter=0;counter<reasonMap.get(sActivityName).get(decCode).size();counter++){
					formObject.addItemInCombo(sDecReason, reasonMap.get(sActivityName).get(decCode).get(counter),
							reasonMap.get(sActivityName).get(decCode).get(counter));
				}
			}
			if(sReason.equalsIgnoreCase("0") || sReason.equalsIgnoreCase("")) {
				formObject.setValue(sDecReason, "");
			}
			else if(!(sReason.isEmpty() || sReason.equalsIgnoreCase(emptyStr))) {
				formObject.setValue(sDecReason, sReason);
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	protected void populateComplianceTab() {  
		try {
			log.info("Loading compliance tab...");
			if("Y".equalsIgnoreCase(formObject.getValue(COMP_IMB_CHK).toString())) {
				formObject.setValue("IMB_YES",TRUE);
				setSectionState(IMB_DETAIL_FRM,"C");
				setSectionLockedState(IMB_DETAIL_FRM,TRUE);
			} else if("N".equalsIgnoreCase(formObject.getValue(COMP_IMB_CHK).toString())) {
				formObject.setValue("IMB_NO",TRUE);
				setSectionState(IMB_DETAIL_FRM,"C");
				setSectionLockedState(IMB_DETAIL_FRM,TRUE);
			} else {
				setSectionState(IMB_DETAIL_FRM,"C");
				//formObject.setNGLockFrameState(IMB_DETAIL_FRM, true);
				setSectionLockedState(IMB_DETAIL_FRM,TRUE);
			}
			formObject.setValue("SHIPMENT_DATE", "");
			disableControls("Btn_Basic_Vessel_Details,Btn_Ownrship_Details,Btn_Movmnt_Details,Btn_Gen_PDF");
			if("PROCESSING CHECKER".equalsIgnoreCase(sActivityName)) {
				log.info("disabling PC fields####");
				formObject.setStyle(FRM_LLI_FIELDS, VISIBLE, FALSE);
				formObject.setStyle(FRM_LLI, "sectionstate", "expanded");
				disableControls("Btn_Basic_Vessel_Details,Btn_Ownrship_Details,Btn_Movmnt_Details,Btn_Gen_PDF,FIELD_SHIPMENT_DATE,FIELD_TXT_VESSELNAME,FIELD_TXT_VESSELFLAG,FIELD_TXT_VESSELID,FIELD_TXT_VESSELIMO");
			}
			if("Y".equalsIgnoreCase(formObject.getValue(LLI_CHK_OK).toString())) {
				log.info("inside PROCESSING CHECKER**");
				setSectionState(FRM_LLI,"E");
				enableControls(BUTTON_GENERATE_LLI_PDF);
				setSectionLockedState(FRM_LLI,FALSE);
			} else if(("X".equalsIgnoreCase(formObject.getValue(LLI_CHK_OK).toString())) || ("O".equalsIgnoreCase(formObject.getValue(LLI_CHK_OK).toString()))) {
				disableControls(BUTTON_GENERATE_LLI_PDF);
				setSectionLockedState(FRM_LLI,TRUE);
			} else {
				if("PROCESSING MAKER".equalsIgnoreCase(sActivityName)) {
					disableControls(BUTTON_GENERATE_LLI_PDF);
					setSectionLockedState(FRM_LLI,TRUE);
				}
			}
			if("PROCESSING CHECKER".equalsIgnoreCase(sActivityName)) {
				setSectionState(FRM_LLI,"E");
				setSectionLockedState(FRM_LLI,FALSE);
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	protected void setSectionState(String sectionName,String State) {
		try {
			log.info("setSectionState started for :"+sectionName+" and State is : "+State);
			if("E".equalsIgnoreCase(State))
				formObject.setStyle(sectionName, "sectionstate", "expanded"); 
			else if("C".equalsIgnoreCase(State))
				formObject.setStyle(sectionName, "sectionstate", "collapsed"); 
		} catch (Exception e) {
			log.error("[Exception]["+sWorkitemID+"]");
			log.error(e.getStackTrace());
		}
	}

	protected void setSectionLockedState(String sectionName, String state) {
		try {
			log.info("setSectionLockedState started for :"+sectionName+" and State is : "+state);
			formObject.setStyle(sectionName, "lock", state);
		} catch (Exception e) {
			log.error("[Exception]["+sWorkitemID+"]");
			log.error("exception in setSectionLockedState: "+e,e);
		}

	}

	protected void loadRouteToPPM(String sControlName, HashMap lstData) {
		try {
			String sRouteTo="";
			sRouteTo= formObject.getValue(sControlName).toString();
			formObject.clearCombo(sControlName);
			for(int i=0;i<lstData.size();i++)
				formObject.addItemInCombo(sControlName, lstData.get(i).toString(), lstData.get(i).toString());
			if(!sRouteTo.isEmpty())
				formObject.setValue(sControlName, sRouteTo);
			else
				formObject.setValue(sControlName, emptyStr);
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	protected void setSelectedDisable(String sControlName) {
		log.info("Disabling ["+sControlName+"]");
		formObject.clearCombo(sControlName);                     
		formObject.setValue(sControlName, "");
	}

	protected void duplicateCheckPPMPC(String pControlName, boolean bChange) {
		try {
			log.info("inside duplicateCheckPPMPC");
			String sDuplicateCheckConf = "";
			sDuplicateCheckConf = formObject.getValue(pControlName).toString();
			if(bChange) {
				log.info("inside duplicateCheckPPMPC>");
				formObject.setValue(DEC_CODE, "");
			}
			if("N".equalsIgnoreCase(sDuplicateCheckConf)) {
				log.info("inside duplicateCheckPPMPC>>");
				if(this.sActivityName.equalsIgnoreCase("PP_MAKER") || this.sActivityName.equalsIgnoreCase("Customer Referral Response") ||this.sActivityName.equalsIgnoreCase("ReadOnly")) //customer_ref_change_ppm_form
					formObject.setValue(DEC_CODE, "REJ");
				else if(this.sActivityName.equalsIgnoreCase("PROCESSING CHECKER"))
					formObject.setValue(DEC_CODE, "RPPM");
				disableFieldOnDemand(DEC_CODE);
			} else {
				enableFieldOnDemand(DEC_CODE);
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	public void enableDisableFieldsSigAcc(String strReqSign,String strSuffBalAvl) {
		log.info("sigControlName1  >>>> " + formObject.getValue(strReqSign) + " strSuffBalAvl " +formObject.getValue(strReqSign).hashCode());
		String Q1 = "SELECT '--Select--' REF FROM DUAL UNION SELECT TO_CHAR(REFFER_TO) REF FROM TFO_DJ_SIG_REF_MAST ORDER BY 1";
		log.info("TFO_REQ_SIGN_MAN_PPM123  >>>> " + formObject.getValue("TFO_REQ_SIGN_MAN_PPM").toString().trim());
		if ("2".equalsIgnoreCase(formObject.getValue(strReqSign).toString()) || "2".equalsIgnoreCase(formObject.getValue(strSuffBalAvl).toString())) {
			log.info("sigControlName1  >>>> " + strReqSign);
			formObject.setStyle(SIGACC_REF_TO,DISABLE,FALSE);
			formObject.setStyle("SIGACC_EXC_RMKS",DISABLE,FALSE);
			formObject.setStyle(SIGN_ADD,DISABLE,FALSE);
			formObject.setStyle(SIGN_DEL,DISABLE,FALSE);
			loadCombo(Q1, SIGACC_REF_TO);
		} else {
			formObject.setValue(SIGACC_REF_TO, "--Select--");
			formObject.setStyle(SIGACC_REF_TO,DISABLE,TRUE);
			formObject.setStyle("SIGACC_EXC_RMKS",DISABLE,TRUE);
			formObject.setStyle(SIGN_ADD,DISABLE,TRUE);
			formObject.setStyle(SIGN_DEL,DISABLE,TRUE);
		}
	}

	public void btnAccEnableDisable(String controlName) {
		try {
			String sIsAccValid = formObject.getValue(controlName).toString();
			log.info("btnAccEnableDisable  "+controlName + " Value for " +sIsAccValid +"::requestcategory :"+requestCategory);
			if(!(null == sIsAccValid || "null".equalsIgnoreCase(sIsAccValid))) {
				if("PP_MAKER".equalsIgnoreCase(sActivityName)) {
					log.info("gPPM1");
					if(!(("GRNT".equalsIgnoreCase(requestCategory)&&("CC".equalsIgnoreCase(requestType)|| "CL".equalsIgnoreCase(requestType)
							|| "ER".equalsIgnoreCase(requestType)||"EPC".equalsIgnoreCase(requestType)))
							|| ("SBLC".equalsIgnoreCase(requestCategory)&&("SBLC_CR".equalsIgnoreCase(requestType)
									|| "SBLC_CS".equalsIgnoreCase(requestType)|| "SBLC_ER".equalsIgnoreCase(requestType)))
									||("ELC".equalsIgnoreCase(requestCategory)&&("ELC_SER".equalsIgnoreCase(requestType)
											|| "ELC_SCL".equalsIgnoreCase(requestType)))))
						{
						log.info("*****INSIDE THISSS*****");
						if( ACCOUNT_VALID.equalsIgnoreCase(controlName) 
								&& ("GRNT".equalsIgnoreCase(requestCategory) || "ELC".equalsIgnoreCase(requestCategory)|| "SBLC".equalsIgnoreCase(requestCategory) //RR
										|| "ILC".equalsIgnoreCase(requestCategory)|| "SG".equalsIgnoreCase(requestCategory))) {//Shipping_gtee_28
							log.info("gPPM2");
							if("1".equalsIgnoreCase(sIsAccValid)) {
								log.info("grtn ilc elc 1");
								enableFieldOnDemand(ACCOUNT_NUMBER);
								enableDisableMultipleBtnControl("btnFetchAccMemo,btnFetchAccDetails,btnAccDtl",FALSE);
							} else {
								log.info("grtn ilc elc other than 1");
								setSelectToEmptyLov();
								disableFieldOnDemand(ACCOUNT_NUMBER);							
								enableDisableMultipleBtnControl("btnFetchAccMemo,btnFetchAccDetails,btnAccDtl",TRUE);														
								clearControls(GRNT_CHRG_ACC_TITLE,GRNT_CHRG_ACC_CRNCY,FIELD_SIGACC_ACC_TOTAL_BAL,FIELD_SIGACC_ACC_CURR_BAL, FIELD_SIGACC_DOMCL_BRN_CODE,FIELD_SIGACC_ACC_CURRENCY,SIGACC_ACC_NO);
								formObject.clearTable(QVAR_LINKED_CUST);
							}
						 }
						} else if(BILL_CUST_HLDING_ACC_US.equalsIgnoreCase(controlName) 
							&&("EC".equalsIgnoreCase(requestCategory) 
									|| "IC".equalsIgnoreCase(requestCategory)
									|| "DBA".equalsIgnoreCase(requestCategory)
									|| "ELCB".equalsIgnoreCase(requestCategory)
									|| "ILCB".equalsIgnoreCase(requestCategory))) {
						if("1".equalsIgnoreCase(sIsAccValid)) {
							log.info("ec ic ilcb elcb 1");
							enableFieldOnDemand("INF_CHARGE_ACC_NUM####INF_SETTLEMENT_ACC_NUM");
							enableDisableMultipleBtnControl("btnFetchAccMemo,btnFetchAccDetails,btnINFFetchAcc",FALSE);
						} else {
							log.info("ec ic ilcb elcb other than 1");
							setSelectToEmptyLov();
							disableFieldOnDemand("INF_CHARGE_ACC_NUM####INF_SETTLEMENT_ACC_NUM");
							enableDisableMultipleBtnControl("btnFetchAccMemo,btnFetchAccDetails,btnINFFetchAcc",TRUE);													
							clearControls(INF_CHARGE_ACC_TITLE,INF_CHARGE_ACC_CURR,INF_SETTLEMENT_ACC_TITLE,INF_SETTLEMENT_ACC_CURR,INF_CREDIT_ACC_TITLE,INF_CREDIT_ACC_CURR,FIELD_SIGACC_ACC_TOTAL_BAL,FIELD_SIGACC_ACC_CURR_BAL, FIELD_SIGACC_DOMCL_BRN_CODE,FIELD_SIGACC_ACC_CURRENCY,SIGACC_ACC_NO);
							formObject.clearTable(QVAR_LINKED_CUST);
						}
					} else if(BILL_CUST_HLDING_ACC_US.equalsIgnoreCase(controlName) &&("TL".equalsIgnoreCase(requestCategory) 
							|| "IFS".equalsIgnoreCase(requestCategory) || "IFP".equalsIgnoreCase(requestCategory)
							|| "IFA".equalsIgnoreCase(requestCategory))) {//CODE BY MOKSH
						if("1".equalsIgnoreCase(sIsAccValid)) {
							log.info("BILL_CUST_HLDING_ACC_US for TL,IFS,IFP,IFA - YES");
							enableFieldOnDemand("INF_CHARGE_ACC_NUM####INF_SETTLEMENT_ACC_NUM####INF_CREDIT_ACC_NUM");
							enableDisableMultipleBtnControl("btnFetchAccMemo,btnFetchAccDetails,btnINFFetchAcc",FALSE);
						} else {
							log.info("BILL_CUST_HLDING_ACC_US for TL,IFS,IFP,IFA - NO");
							setSelectToEmptyLov();
							disableFieldOnDemand("INF_CHARGE_ACC_NUM####INF_SETTLEMENT_ACC_NUM####INF_CREDIT_ACC_NUM");
							enableDisableMultipleBtnControl("btnFetchAccMemo,btnFetchAccDetails,btnINFFetchAcc",TRUE);													
							clearControls(INF_CHARGE_ACC_TITLE,INF_CHARGE_ACC_CURR,INF_SETTLEMENT_ACC_TITLE,INF_SETTLEMENT_ACC_CURR,INF_CREDIT_ACC_TITLE,INF_CREDIT_ACC_CURR,FIELD_SIGACC_ACC_TOTAL_BAL,FIELD_SIGACC_ACC_CURR_BAL, FIELD_SIGACC_DOMCL_BRN_CODE,FIELD_SIGACC_ACC_CURRENCY,SIGACC_ACC_NO);
							formObject.clearTable(QVAR_LINKED_CUST);
						}
					}	
				} else if("PROCESSING MAKER".equalsIgnoreCase(sActivityName)) {
					if("GRNT".equalsIgnoreCase(requestCategory) && ( "GA".equalsIgnoreCase(requestType) 
							|| "GAA".equalsIgnoreCase(requestType))) {
						if("1".equalsIgnoreCase(sIsAccValid)) {
							enableFieldOnDemand(ACCOUNT_NUMBER);
							enableDisableMultipleBtnControl(ACCOUNT_DETAILS,FALSE);
						} else {
							setSelectToEmptyLov();
							disableFieldOnDemand(ACCOUNT_NUMBER);							
							enableDisableMultipleBtnControl(ACCOUNT_DETAILS,TRUE);							
							clearControls(GRNT_ACC_CONTROLS);							
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	public void setSelectToEmptyLov() {
		try {
			if("GRNT".equalsIgnoreCase(requestCategory)
					|| "SBLC".equalsIgnoreCase(requestCategory)	//RR
					|| "ELC".equalsIgnoreCase(requestCategory) 
					|| "ILC".equalsIgnoreCase(requestCategory)
					//Shipping gtee 28
					|| "SG".equalsIgnoreCase(requestCategory)) {				
				if(!"1".equalsIgnoreCase(formObject.getValue(ACCOUNT_VALID).toString())) {
					log.info("Othern than 1 in setSelectToEmptyLov");
					setSelectinCombo(ACCOUNT_NUMBER);
				}				
			}else if("ELC".equalsIgnoreCase(requestCategory)&&("ELC_SLCA".equalsIgnoreCase(requestType)||"ELC_SLCAA".equalsIgnoreCase(requestType)
					||"ELC_SER".equalsIgnoreCase(requestType)||"ELC_SCL".equalsIgnoreCase(requestType))){	//RR			
				if(!"1".equalsIgnoreCase(formObject.getValue(ACCOUNT_VALID).toString())) {
					log.info("Othern than 1 in setSelectToEmptyLov");
					setSelectinCombo(ACCOUNT_NUMBER);
				}				
			}else if("EC".equalsIgnoreCase(requestCategory) 
					|| "IC".equalsIgnoreCase(requestCategory) 
					|| "DBA".equalsIgnoreCase(requestCategory) 
					|| "ELCB".equalsIgnoreCase(requestCategory)
					|| "ILCB".equalsIgnoreCase(requestCategory)) {
				if(!"1".equalsIgnoreCase(formObject.getValue(BILL_CUST_HLDING_ACC_US).toString())) {
					log.info("Othern than 1 in setSelectToEmptyLov");
					setSelectinCombo(INF_CHARGE_ACC_NUM);
					setSelectinCombo(INF_SETTLEMENT_ACC_NUM);
				}				
			} else if("TL".equalsIgnoreCase(requestCategory) 
					|| "IFS".equalsIgnoreCase(requestCategory) 
					|| "IFP".equalsIgnoreCase(requestCategory)
					|| "IFA".equalsIgnoreCase(requestCategory)) {//CODE BY MOKSH
				if(!"1".equalsIgnoreCase(formObject.getValue(BILL_CUST_HLDING_ACC_US).toString())) {
					log.info("Othern than YES in setSelectToEmptyLov");
					setSelectinCombo(INF_CHARGE_ACC_NUM);
					setSelectinCombo(INF_SETTLEMENT_ACC_NUM);
					setSelectinCombo(INF_CREDIT_ACC_NUM);
				}				
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	private void setSelectinCombo(String controlName) {
		try {
			log.info("in thin setSelectinCombo  " +controlName);
			formObject.clearCombo(controlName);
			formObject.setValue(controlName, emptyStr);
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	protected void enableDisableMultipleBtnControl(String buttonName,String flag) {
		try {
			String [] btnrArry=buttonName.split(",");
			for(String btn: btnrArry) {
				formObject.setStyle(btn, DISABLE,flag);
			}            
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	public String getDataFromMap(Map<String, String> mapList,String controlVal) {
		try {
			if(!controlVal.isEmpty()) {
				for (String key : mapList.keySet()) {
					if (key.equalsIgnoreCase(controlVal)) {
						return mapList.get(key);		                
					}
				}	
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		return "";
	}

	protected void setBranchCodeAtPm() {
		try { 
			String sExistingDeliveryBranch="";
			sExistingDeliveryBranch = formObject.getValue(DELIVERY_BRANCH).toString();
			log.info("Delivery Branch "+sExistingDeliveryBranch);
			if(!(sExistingDeliveryBranch.isEmpty() || "0".equalsIgnoreCase(sExistingDeliveryBranch) || "".equalsIgnoreCase(sExistingDeliveryBranch))) {
				formObject.setValue(DELIVERY_BRANCH, sExistingDeliveryBranch);
			} else {
				log.info("value of initiator branch: "+formObject.getValue(INITIATOR_BRANCH).toString().toUpperCase().trim());
				String strBrnQry = "SELECT CODE FROM USR_0_HOME_BRANCH WHERE UPPER(HOME_BRANCH) = '" + formObject.getValue(INITIATOR_BRANCH).toString().toUpperCase().trim() + "' AND ROWNUM=1";
				log.info("strBrnQry setBranchCodeAtPm >>> " + strBrnQry);
				List<List<String>> listBrnQry = formObject.getDataFromDB(strBrnQry);
				if (listBrnQry != null && !listBrnQry.isEmpty()) { 
					if (!listBrnQry.get(0).isEmpty()) {
						formObject.setValue(DELIVERY_BRANCH, listBrnQry.get(0).get(0));
					}
				}
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		} finally{
			delBranchControlDisableEnable(DELIVERY_BRANCH);
		}
	}

	private void delBranchControlDisableEnable(String controlName) {
		try {
			if("GRNT".equalsIgnoreCase(requestCategory)||"SBLC".equalsIgnoreCase(requestCategory)
					||"ELC".equalsIgnoreCase(requestCategory) && ("ELC_SLCA".equalsIgnoreCase(requestType) ||"ELC_SLCAA".equalsIgnoreCase(requestType)
							|| "ELC_SER".equalsIgnoreCase(requestType)|| "ELC_SCL".equalsIgnoreCase(requestType))) {//added by mansi

			} else if ("IFS".equalsIgnoreCase(requestCategory)) {
				disableControls(controlName);
			} else if ("IFP".equalsIgnoreCase(requestCategory)) {
				disableControls(controlName);
			} else if ("IFA".equalsIgnoreCase(requestCategory)) {//CODE BY MOKSH
				disableControls(controlName);
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	public boolean emptyAndAmountCheck(String fieldValue) {
		try {
			log.info("Inside at Queue in emptyAndAmountCheck "+fieldValue);
			if ( !(null==fieldValue
					|| (null!=fieldValue && "".equalsIgnoreCase(fieldValue.trim()) && fieldValue.trim().isEmpty())
					|| "--Select--".equalsIgnoreCase(fieldValue)
					|| "0".equalsIgnoreCase(fieldValue) 
					|| "0.000".equalsIgnoreCase(fieldValue)
					|| "0.0".equalsIgnoreCase(fieldValue)
					|| "0.00".equalsIgnoreCase(fieldValue)
					)) {
				log.info("if block in emptyAndAmountCheck ");
				return true;
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		return false;
	}

	public void checkDateFormat(String controlName) {
		try {
			String sDate = formObject.getValue(controlName).toString();
			if(null != sDate) {
				String sdateParam[] = sDate.split("/");
				if(Integer.parseInt(sdateParam[2]) < 100)
					sdateParam[2] = "20"+Integer.parseInt(sdateParam[2]);
				else if(Integer.parseInt(sdateParam[2]) < 1000)
					sdateParam[2] = "2"+Integer.parseInt(sdateParam[2]);
				formObject.setValue(controlName, sdateParam[0]+"/"+sdateParam[1]+"/"+sdateParam[2]);
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	protected boolean validateNewMaturityDate() {
		String sAmendType ="";
		sAmendType = formObject.getValue(INF_AMEND_TYPE).toString();
		if(sAmendType.equalsIgnoreCase("MDC"))
			return true;
		else
			return false;
	}

	public boolean isNewMaturityBaseDocDateAllowed() {
		if("IFS".equalsIgnoreCase(requestCategory) || "IFP".equalsIgnoreCase(requestCategory) || 
				"TL".equalsIgnoreCase(requestCategory) || "IFA".equalsIgnoreCase(requestCategory)) {//CODE BY MOKSH
			return true;
		} 

		return false;		
	}

	protected boolean checkBaseMatDatevalidation() {
		try {
			boolean result=false;
			String sBaseDocDate="", sMatDate="";
			Date dBaseDocDate=null;
			Date dMatDate=null;
			sBaseDocDate = formObject.getValue(INF_BASE_DOC_DATE).toString();
			sMatDate= formObject.getValue(INF_MATURITY_DATE).toString();
			if(sBaseDocDate.isEmpty() || sMatDate.isEmpty())
				return true;
			log.info("Date "+sBaseDocDate +"   smaturity date "+sMatDate);
			try {
				dBaseDocDate = sdf.parse(sBaseDocDate);
				dMatDate = sdf.parse(sMatDate);
				log.info("Date "+dBaseDocDate +"  Mat Date "+dMatDate);
				if(dBaseDocDate.after(dMatDate))
					result = false;
				else
					result = true;
			} catch(Exception e) {
				log.error("Exception: ",e);
			}
			return result;
		} catch (Exception e) {
			log.error("Exception: ",e);
			return false;
		}
	}

	protected void setTenorDays() {
		int loanTenorDays=0;
		String sBaseDocDat="";
		String sMatDate ="";
		Date dBaseDocDate=null;
		Date dMatDate=null;
		long days=0;
		sBaseDocDat = formObject.getValue(INF_BASE_DOC_DATE).toString();
		sMatDate = formObject.getValue(INF_MATURITY_DATE).toString();
		log.info("Tenor "+loanTenorDays +"  sBaseDocdate "+sBaseDocDat);
		if(!(sBaseDocDat.isEmpty() ||sMatDate.isEmpty())) {
			try {
				dBaseDocDate = sdf.parse(sBaseDocDat);
				dMatDate = sdf.parse(sMatDate);
				days = (dMatDate.getTime() - dBaseDocDate.getTime())/(24*60*60*1000);
				if(days>=0){					
					formObject.setValue(INF_TENOR_DAYS, String.valueOf(days));
				}
			} catch(Exception e) {
				log.error("Exception: ",e);
			}
		}
	}

	private void gteeInputFrm(String frmShow) {
		try {  			
			hideshowFrmInputTab(frmShow,true);
			hideshowFrmInputTab("IF_FRAME#IFS_BUYER#LC_FRAME",false);
			hideshowFrmInputTab("GRNT_FRM_NEW#FRM_GRTEE_LC_COMMON",true);
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	private void inFinanceInputForm(String frmShow, String sBuyersFrame) {
		try {
			log.info("Inside inFinanceInputForm");
			hideshowFrmInputTab("IF_FRAME#IF_FRAME1",true);
			hideshowFrmInputTab("GTEE_FRAME#BILL_FRAME",false);
			log.info("IF Frame Set");
			if("LD".equalsIgnoreCase(requestType) && "IFS".equalsIgnoreCase(requestCategory)) { //need to check 
				log.info("Inside if Condition");
				hideshowFrmInputTab(sBuyersFrame, true);
			} else {
				log.info("Inside else condition");
				hideshowFrmInputTab(sBuyersFrame, false);
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	protected boolean treasuryHandling() {
		boolean res=false;
		if("IC".equalsIgnoreCase(requestCategory)
				|| "EC".equalsIgnoreCase(requestCategory)
				|| "IFS".equalsIgnoreCase(requestCategory)
				|| "IFP".equalsIgnoreCase(requestCategory)
				|| "ILC".equalsIgnoreCase(requestCategory)
				|| "ELC".equalsIgnoreCase(requestCategory)
				|| "ILCB".equalsIgnoreCase(requestCategory)
				|| "ELCB".equalsIgnoreCase(requestCategory)
				|| "TL".equalsIgnoreCase(requestCategory)
				|| "DBA".equalsIgnoreCase(requestCategory)
				|| "SCF".equalsIgnoreCase(requestCategory) //ADDED FOR SCF
				|| "IFA".equalsIgnoreCase(requestCategory)) {//CODE BY MOKSH
			if("IC_BS".equalsIgnoreCase(requestType)
					||"DBA_STL".equalsIgnoreCase(requestType)
					||"EC_BS".equalsIgnoreCase(requestType)
					||"EC_DISC".equalsIgnoreCase(requestType)
					||"EC_CAP".equalsIgnoreCase(requestType)
					||"EC_LDDI".equalsIgnoreCase(requestType)
					||"ILCB_BS".equalsIgnoreCase(requestType)
					||"ELCB_BS".equalsIgnoreCase(requestType)
					||"ELCB_DISC".equalsIgnoreCase(requestType)
					||"ELCB_CLBP".equalsIgnoreCase(requestType)
					||"LD".equalsIgnoreCase(requestType)
					||"PD".equalsIgnoreCase(requestType)
					||"TL_LD".equalsIgnoreCase(requestType)
					||"PDD".equalsIgnoreCase(requestType))
				res=false; //For these cases -Treasury rate tab should be enabled
			else
				res=true;
		} else {
			res = true;
		}
		return res;
	}

	private void setRateRequested() {
		String buyCurr = formObject.getValue(TR_BUY_CUR).toString();
		String sellCurr = formObject.getValue(TR_SELL_CUR).toString();
		if((null == buyCurr || "".equals(buyCurr) || "0".equalsIgnoreCase(buyCurr))) {
			buyCurr = "";
		}
		if((null == sellCurr || "".equals(sellCurr) || "0".equalsIgnoreCase(sellCurr))) {
			sellCurr = "";
		}
		if(!"".equals(buyCurr) || !"".equals(sellCurr)) {
			formObject.setValue("TR_RATE_REQ",buyCurr +"-"+sellCurr);
		}
	}

	public void setIfTlDataOnload() { 
		try {
			if("STL".equalsIgnoreCase(requestType)
					|| "AM".equalsIgnoreCase(requestType)
					|| "TL_STL".equalsIgnoreCase(requestType)
					|| "TL_AM".equalsIgnoreCase(requestType)
					|| treasuryHandling()) {
				disableControls(DISABLE_PM_IF_NON_LD); 
				enableDisableMultipleBtnControl("FETCH_RATE",TRUE);
				clearControls(CLEAR_PM_IF_NON_LD);
				setRateRequested();
				if("STL".equalsIgnoreCase(requestType)  ||"TL_STL".equalsIgnoreCase(requestType)) { 
					disableControls("Qvar_cpd_details");
				}
			}
			if("PROCESSING MAKER".equalsIgnoreCase(sActivityName)) {
				setTreasuryTabProctectedFields();
				disableControls(SOURCE_CHANNEL);
				disableControls(RELATIONSHIP_TYPE);
				if("IFS".equalsIgnoreCase(requestCategory)||"IFP".equalsIgnoreCase(requestCategory) ||
						"TL".equalsIgnoreCase(requestCategory)||"IFA".equalsIgnoreCase(requestCategory)||
						"SCF".equalsIgnoreCase(requestCategory)) {//CODE FOR SCF
					hideshowFrmInputTab(COMP_IMB_CHK,true);
					if(!("LD".equalsIgnoreCase(requestType) || "PD".equalsIgnoreCase(requestType)
							|| "TL_LD".equalsIgnoreCase(requestType) || "PDD".equalsIgnoreCase(requestType))) { //ADDED FOR SCF REQ CAT
						formObject.setValue(FCUBS_CON_NO, formObject.getValue(TRANSACTION_REFERENCE).toString());
						disableFieldOnDemand("FCUBSConNo####BILL_LN_REFRNCE");
					}
				}
				else if("ELCB".equalsIgnoreCase(requestCategory)
						||"ILCB".equalsIgnoreCase(requestCategory)
						||"EC".equalsIgnoreCase(requestCategory)
						||"IC".equalsIgnoreCase(requestCategory)
						||"DBA".equalsIgnoreCase(requestCategory)
						||"ELC".equalsIgnoreCase(requestCategory)
						||"ILC".equalsIgnoreCase(requestCategory)) {
					hideshowFrmInputTab(COMP_IMB_CHK,true);
					if(checkTrnsMandate(requestCategory,requestType)) {
						formObject.setValue(FCUBS_CON_NO, formObject.getValue(TRANSACTION_REFERENCE).toString());
						disableFieldOnDemand("FCUBSConNo####BILL_LN_REFRNCE");
					}
				}
			} else if("PROCESSING CHECKER".equalsIgnoreCase(sActivityName)) {
				setTreasuryTabProctectedFields();
				disableControls(SOURCE_CHANNEL);
				disableControls(RELATIONSHIP_TYPE);
				if("IFS".equalsIgnoreCase(requestCategory)||"IFP".equalsIgnoreCase(requestCategory) ||
						"TL".equalsIgnoreCase(requestCategory)||"IFA".equalsIgnoreCase(requestCategory) ||
						"SCF".equalsIgnoreCase(requestCategory)) {//CODE BY MOKSH
					hideshowFrmInputTab("IMB_FRAME",true);
					formObject.setValue(PC_FCUBS_REF, formObject.getValue(TRANSACTION_REFERENCE).toString());
					log.info("disable controls ::" +requestCategory);
					log.info("disable controls ::" +requestType);
					if(checkTrnsMandate(requestCategory,requestType)) {	
						log.info("disable controls ::" +requestCategory);
						log.info("disable controls ::" +requestType);
						disableFieldOnDemand("PC_FCUBS_REF####BILL_LN_REFRNCE");
					}
				}else if("ELCB".equalsIgnoreCase(requestCategory)
						||"ILCB".equalsIgnoreCase(requestCategory)
						||"EC".equalsIgnoreCase(requestCategory)
						||"IC".equalsIgnoreCase(requestCategory)
						||"DBA".equalsIgnoreCase(requestCategory)
						||"ELC".equalsIgnoreCase(requestCategory)
						||"ILC".equalsIgnoreCase(requestCategory)) {
					hideshowFrmInputTab("IMB_FRAME",true);					
				}else if("SG".equalsIgnoreCase(requestCategory) 
						&& "SG_NIC".equalsIgnoreCase(requestType)
						|| "SG_NILC".equalsIgnoreCase(requestType)){
					formObject.setValue(FCUBS_CON_NO, formObject.getValue(TRANSACTION_REFERENCE).toString());
					disableFieldOnDemand(FCUBS_CON_NO);
				}
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	public void elcIlcInputFrm() {
		try {  			
			hideshowFrmInputTab("GTEE_FRAME#LC_FRAME#FRM_GRTEE_LC_COMMON",true);                       
			hideshowFrmInputTab("IF_FRAME#IFS_BUYER",false);
			disableFieldOnDemand(TRN_TENOR);
			disableFieldOnDemand("GRNT_CHRG_ACC_TITLE####GRNT_CHRG_ACC_CRNCY");
			if(!("ILC_AM".equalsIgnoreCase(requestType)) && !"ELC_LCC".equalsIgnoreCase(requestType)
					&& !"ELC_LCAA".equalsIgnoreCase(requestType)){ //US118
				hideshowFrmInputTab("IELC_MKR_INPUT_FRM",true);
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	public void ieccbInputFrm() {
		try {
			log.info("Inside inFinanceInputForm");
			disableFieldOnDemand(INF_LOAN_CURR);
			hideshowFrmInputTab("IF_FRAME#BILL_FRAME",true);
			hideshowFrmInputTab("GTEE_FRAME#IFS_BUYER#IF_FRAME1",false);
			hideshowFrmInputTab("BILL_FRAME", true);
			if("ILCB".equalsIgnoreCase(requestCategory) && "ILCB_BL".equalsIgnoreCase(requestType)) {
				hideshowFrmInputTab("Charges_Frame", true);
				formObject.setValue("Q_Charges_Frame.winame", sWorkitemID);
				formObject.setValue("Q_Charges_Frame.legalCurrency", "AED");
				disableFieldOnDemand("Q_Charges_Frame.legalCurrency");
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	public void setTreasuryTabProctectedFields() {
		String processType=formObject.getValue(PROCESS_TYPE).toString();
		try { 
			formObject.setValue("TR_CID",formObject.getValue(CUSTOMER_ID).toString());
			formObject.setValue("TR_CUST_NAME",formObject.getValue(CUSTOMER_NAME).toString());
			log.info("INF_SETTLEMENT_ACC_CURR: "+formObject.getValue(INF_SETTLEMENT_ACC_CURR).toString());
			log.info("INF_LOAN_CURR"+formObject.getValue(INF_LOAN_CURR).toString());
			log.info("transactionAmount: "+formObject.getValue(TRANSACTION_AMOUNT).toString());
			log.info("trnsCurrency: "+trnsCurrency);
			if("PROCESSING MAKER".equalsIgnoreCase(sActivityName)) {
				if(!emptyAndAmountCheck(formObject.getValue(TR_SELL_CUR).toString())) {
					log.info("in Set TR Tab protected control TR_SELL_CUR");
					if("IFP".equalsIgnoreCase(requestCategory)
							||"IFS".equalsIgnoreCase(requestCategory) 
							||"TL".equalsIgnoreCase(requestCategory)
							||"IC".equalsIgnoreCase(requestCategory)
							||"DBA".equalsIgnoreCase(requestCategory)
							||"ILCB".equalsIgnoreCase(requestCategory)
							||"IFA".equalsIgnoreCase(requestCategory)
							||"SCF".equalsIgnoreCase(requestCategory)) {//CHANGES FOR SCF
						log.info("set value for TR_SELL_CUR: "+trnsCurrency);
						formObject.setValue(TR_SELL_CUR,trnsCurrency);	// Sell Currency
					} else if("EC".equalsIgnoreCase(requestCategory) ||"ELCB".equalsIgnoreCase(requestCategory)){
						log.info("set value for TR_SELL_CUR: "+formObject.getValue(INF_SETTLEMENT_ACC_CURR).toString());
						formObject.setValue(TR_SELL_CUR,formObject.getValue(INF_SETTLEMENT_ACC_CURR).toString());// Sell Currency
					}
				}				
				if(!emptyAndAmountCheck(formObject.getValue(TR_BUY_CUR).toString())) {
					log.info("in Set TR Tab protected control TR_BUY_CUR");
					if("IFP".equalsIgnoreCase(requestCategory) ||"IFS".equalsIgnoreCase(requestCategory) ||
							"TL".equalsIgnoreCase(requestCategory) ||"IFA".equalsIgnoreCase(requestCategory)||
							"SCF".equalsIgnoreCase(requestCategory)) {//CODE FOR SCF
						formObject.setValue(TR_BUY_CUR,formObject.getValue(INF_LOAN_CURR).toString()); //Buy Currency
					} else if("IC".equalsIgnoreCase(requestCategory)||"DBA".equalsIgnoreCase(requestCategory) ||"ILCB".equalsIgnoreCase(requestCategory)) {
						formObject.setValue(TR_BUY_CUR,formObject.getValue(INF_SETTLEMENT_ACC_CURR).toString());//Buy Currency
					}else if("EC".equalsIgnoreCase(requestCategory) ||"ELCB".equalsIgnoreCase(requestCategory)) {
						formObject.setValue(TR_BUY_CUR,trnsCurrency);
					}
				}
				String transactionAmount = formObject.getValue(TRANSACTION_AMOUNT).toString();
				log.info("Transaction amount to be loaded = "+ transactionAmount);				
				if(!emptyAndAmountCheck(formObject.getValue(TR_SELL_AMT).toString())) {
					log.info("in Set TR Tab protected control TR_SELL_AMT");
					if(!("EC".equalsIgnoreCase(requestCategory) || "ELCB".equalsIgnoreCase(requestCategory))) {
						log.info("*****other than EC and ELCB.....sell amt loaded****");
						formObject.setValue(TR_SELL_AMT,transactionAmount);
					} else {
						formObject.setValue(TR_SELL_AMT,"");
					}
				} if(!emptyAndAmountCheck(formObject.getValue(TR_LOAN_AMT).toString())) {
					log.info("in Set TR Tab protected control TFO_TR_LOAN_AMT");
					if(("EC".equalsIgnoreCase(requestCategory) || "ELCB".equalsIgnoreCase(requestCategory))) {
						log.info("***** EC and ELCB.....buy amt loaded****");
						formObject.setValue(TR_LOAN_AMT,transactionAmount);//Buy Amount
					}else {
						formObject.setValue(TR_LOAN_AMT,"");//Buy Amount
					}
				}
				
				log.info("Ui Exchange Rate = "+ formObject.getValue(UI_EXCHANGE_RATE).toString());	
				log.info("Tr Loan Amount = "+ formObject.getValue(TR_LOAN_AMT).toString());	
				log.info("Tr Exchange Rate = "+ formObject.getValue("TR_EXCHANGE_RATE").toString());	
				if((emptyAndAmountCheck(formObject.getValue(UI_EXCHANGE_RATE).toString())||emptyAndAmountCheck(formObject.getValue(TR_LOAN_AMT).toString())
						||emptyAndAmountCheck(formObject.getValue("TR_EXCHANGE_RATE").toString()))&&processType.equalsIgnoreCase("TSLM Front End")) {

					log.info("***** Set User Input Rate Fx rate for TSLM .....");
					String userInputExrate=formObject.getValue(UI_EXCHANGE_RATE).toString();//Defect 101690
					String fxExrate=formObject.getValue("TR_EXCHANGE_RATE").toString();
					
					if(userInputExrate==null||userInputExrate.trim().isEmpty()){
						userInputExrate=fxExrate;
						formObject.setValue(UI_EXCHANGE_RATE,userInputExrate);
					}
					
					if(fxExrate==null||fxExrate.trim().isEmpty()){
						fxExrate=userInputExrate;
						formObject.setValue("TR_EXCHANGE_RATE",fxExrate);
					}
					
					Double userInputExchangerate=0.0;
					if(userInputExrate!=null&&!userInputExrate.isEmpty()){
						userInputExchangerate=Double.parseDouble(userInputExrate);
					}
					Double BuyAmt=0.0;
					Double sellAmt=0.0;
					transactionAmount =transactionAmount.replace(",", ""); //ATP-498 REYAZ 30-07-2024
					if(transactionAmount!=null&&!transactionAmount.isEmpty()){
						sellAmt=Double.parseDouble(transactionAmount);
					}
					
					BuyAmt=userInputExchangerate*sellAmt;
					
					formObject.setValue(TR_LOAN_AMT,String.valueOf(BuyAmt));//Buy Amount
				
				}
			}
			setRateRequested();
			disableFieldOnDemand("TR_CID####TR_CUST_NAME####TR_RATE_REQ####TR_EXCHANGE_RATE");
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	protected void setVisibleProDealFrame() {
		try {
			hideshowFrmInputTab("FRM_GRTEE_LC_COMMON", true);
			hideshowFrmInputTab("DEAL_DETAILS_FRAME", true);
		} catch(Exception e) {
			log.error("Exception: ",e);
		}
	}

	protected void tlInputFrm(String frmShow) {
		try {
			log.info("Inside inFinanceInputForm");
			hideshowFrmInputTab("IF_FRAME#IF_FRAME1",true);
			hideshowFrmInputTab("GTEE_FRAME#BILL_FRAME",false);			
			hideshowFrmInputTab("IFS_BUYER", false);		
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	public void inputTabFrmHideShow() {
		try {
			log.info("inside inputTabFrmHideShow..");
			handlePMFrameReqType();
			if("GRNT".equalsIgnoreCase(requestCategory)||"SBLC".equalsIgnoreCase(requestCategory)) {				
				gteeInputFrm("GTEE_FRAME");//Enter frame which you want to show
				if("PROCESSING MAKER".equalsIgnoreCase(sActivityName)) {
					makeNonEditableGteeIelcInputDtlsTab();
				}
			} else if("IFS".equalsIgnoreCase(requestCategory) || "IFP".equalsIgnoreCase(requestCategory) 
					|| "IFA".equalsIgnoreCase(requestCategory)|| "SCF".equalsIgnoreCase(requestCategory) ) {	//BY KISHAN TSLM
				log.info("Inside IFS/IFP");
				inFinanceInputForm("IF_FRAME","IFS_BUYER");//Enter frame which you want to show
				makeNonEditableIfsInputDtlsTab();
				setIfTlDataOnload();
			}else if("ELC".equalsIgnoreCase(requestCategory)&&("ELC_SLCA".equalsIgnoreCase(requestType)||"ELC_SLCAA".equalsIgnoreCase(requestType)
					||"ELC_SER".equalsIgnoreCase(requestType)||"ELC_SCL".equalsIgnoreCase(requestType))) {	//RR			
				gteeInputFrm("GTEE_FRAME");//Enter frame which you want to show
				if("PROCESSING MAKER".equalsIgnoreCase(sActivityName)) {
					makeNonEditableGteeIelcInputDtlsTab();
				}
			} else if("ELC".equalsIgnoreCase(requestCategory) || "ILC".equalsIgnoreCase(requestCategory)) {				
				elcIlcInputFrm();				
				setIfTlDataOnload();
				if("PROCESSING MAKER".equalsIgnoreCase(sActivityName)) { 
					makeNonEditableGteeIelcInputDtlsTab();
				}
			}
			else if("ELCB".equalsIgnoreCase(requestCategory) 
					|| "ILCB".equalsIgnoreCase(requestCategory)
					|| "IC".equalsIgnoreCase(requestCategory)
					|| "DBA".equalsIgnoreCase(requestCategory)
					|| "EC".equalsIgnoreCase(requestCategory)) {
				ieccbInputFrm();
				setIfTlDataOnload();
				makeNonEditableIfsInputDtlsTab();
				setTreasuryTabProctectedFields();
				setVisibleProDealFrame();
				if("PROCESSING MAKER".equalsIgnoreCase(sActivityName) || "PROCESSING CHECKER".equalsIgnoreCase(sActivityName)) {
					hideshowFrmInputTab("frm_contract_limits",true);
				} else if("PP_MAKER".equalsIgnoreCase(sActivityName)) { 
					if(!(("ILCB_BL".equalsIgnoreCase(requestType))||("ILCB_AC".equalsIgnoreCase(requestType))
							||("ELCB_BL".equalsIgnoreCase(requestType)))) {
						disableFieldOnDemand("LC_LIMIT_LINE"); 
					}
				}
				if("ILCB_AC".equalsIgnoreCase(requestType) && "PROCESSING MAKER".equalsIgnoreCase(sActivityName))  //US3366
				{
					enableFieldOnDemand(DISC_DETAILS);
				}
			} else if("TL".equalsIgnoreCase(requestCategory)) {
				tlInputFrm("IF_FRAME");
				setIfTlDataOnload();
				makeNonEditableIfsInputDtlsTab();
			}
		} catch (Exception e) {
			log.error("exception in inputTabFrmHideShow: "+e,e);
		}		
	}

	private void makeNonEditableIfsInputDtlsTab() {
		try {
			log.info("inside makeNonEditableIfsInputDtlsTab..");
			disableControls(DISABLE_PM_IF_TXT);
			disableControls(DISABLE_PM_ADVISING_TXT);
			disableControls(DISABLE_PM_IF_LOV);
			disableControls(DISABLE_PM_IF_CHECKBOX);
		} catch (Exception e) {
			log.error("exception in makeNonEditableIfsInputDtlsTab: "+e,e);
		}
	}

	protected void hideshowFrmInputTab(String frmsList,boolean bShowHide) {
		try {
			String arr[]=frmsList.split("#");
			for(int counter=0;counter <arr.length;counter++ ) {
				if(bShowHide){ 
					formObject.setStyle(arr[counter], VISIBLE, TRUE);
				}else{
					formObject.setStyle(arr[counter], VISIBLE, FALSE);
				}
			}
		} catch (Exception e) {
			log.error("excpetion in hideshowFrmInputTab: "+e,e);
		}  		
	} 

	protected void setEmptyCombo(String controlName, String controlValue) {
		log.info("setEmptyCombo  "+controlName + "   controlValue   " + controlValue);
		String fieldValue=formObject.getValue(controlName).toString();
		log.info( "mode of payment " +fieldValue );
		try {
			if(null==fieldValue || "0".equalsIgnoreCase(fieldValue)				
					|| (null!=fieldValue && "".equalsIgnoreCase(fieldValue.trim()) && fieldValue.trim().isEmpty())) {
				formObject.setValue(controlName,controlValue);
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	protected void setDecisionFlagSubmit(String ...strControlName) {
		log.info( " in setDecisionFlagSubmit strControlName " +strControlName );

		for(String sControlName:strControlName) {
			if(formObject.getValue(sControlName).toString().isEmpty()){
				log.info( " in setDecisionFlagSubmit sControlName " +sControlName );
				formObject.setValue(sControlName, "N");
				}
		}
	}

	protected void enableButtonControl(String buttonName) {
		try {
			this.formObject.setStyle(buttonName,DISABLE,FALSE);
			this.formObject.setStyle(buttonName, "backcolor","red");
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	protected void disableButtonControl(String buttonName) {
		try {
			this.formObject.setStyle(buttonName,DISABLE,TRUE);
			this.formObject.setStyle(buttonName,DISABLE ,TRUE);
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	public void submitReferalHistory() {
		String sPersonalName="";
		String sQuery = "Select PERSONALNAME From pdbuser where UPPER(username)='"+sUserName.toUpperCase()+"'" ;
		List<List<String>> lResultSet = formObject.getDataFromDB(sQuery);
		if(lResultSet != null) {
			sPersonalName = lResultSet.get(0).get(0);
		} 
		List<String> paramlist =new ArrayList<>( );
		paramlist.add ("Text :"+sWorkitemID);
		paramlist.add ("Text :"+this.sActivityName);
		paramlist.add ("Text :"+this.sActivityName);
		paramlist.add ("Text :"+this.sUserName.toUpperCase());
		paramlist.add ("Text :"+sPersonalName);
		paramlist.add ("Text :"+formObject.getValue(FINAL_DESC_PPM));
		paramlist.add ("Text :"+"");
		log.info("paramlist="+paramlist);
		List statusProc = formObject.getDataFromStoredProcedure("TFO_DJ_REF_DEC_HISTORY", paramlist);
		log.info("statusProc ="+statusProc);
	}

	public boolean validateEmptyFields(String sFieldName) {
		try {
			log.info("Validation Conrol Name :"+sFieldName);
			String fieldValue = normalizeString(this.formObject.getValue(sFieldName).toString());
			log.info("fieldValue --validateMandatoryFields  " + fieldValue);
			if (!(isEmpty(fieldValue)) || "".equalsIgnoreCase(fieldValue)) {
				return false;
			} else {
				return true;
			}

		} catch (Exception e) {
			log.error("Exception: ",e);
			return false;
		}
	}

	public boolean validateMandatoryFields(String sFieldName, String alertMsg) {
		try {
			log.info("Validation Conrol Name :"+sFieldName);
			String fieldValue = normalizeString(this.formObject.getValue(sFieldName).toString());
			log.info("fieldValue --validateMandatoryFieldsPPM  " + fieldValue);
			if (!(isEmpty(fieldValue)) || "".equalsIgnoreCase(fieldValue)
					|| emptyStr.equalsIgnoreCase(fieldValue)) {
				log.info("validateMandatoryFieldsPPM blank");
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

	public boolean contrRefLengthValidation(String sControlName) {
		String sFCUBContrRef ="";
		sFCUBContrRef = formObject.getValue(sControlName).toString();
		int strLen=0;
		strLen= sFCUBContrRef.length();
		if(!sFCUBContrRef.isEmpty()) { 
			if(strLen<16) {
				sendMessageHashMap(sControlName,"Field length cannot be less than 16");
				return false;
			} else {
				if(FCUBS_CON_NO.equalsIgnoreCase(sControlName) || PC_FCUBS_REF.equalsIgnoreCase(sControlName))
					formObject.setValue(TRANSACTION_REFERENCE, sFCUBContrRef);
				return true;
			}
		} else {
			return true;
		}
	}

	public  void sendMessageHashMap(String controlId,String Message) { 
		try {
			log.info("in sendMessagePMHashMap");
			sendMessageList.clear();
			sendMessageList.add(controlId);
			sendMessageList.add(Message);
			log.info("sendmessagelist: "+sendMessageList);
		} catch (Exception e) { 
			log.error("excpetion in sendMessageHashMap: "+e,e);
		}
	}

	protected void clearCustSignAccBal(String accNumber) {
		try { 
			if(null != accNumber && "".equalsIgnoreCase(accNumber)) {
				log.info("in clearCustSignAccBal");
				clearControls(FIELD_SIGACC_ACC_TOTAL_BAL,FIELD_SIGACC_ACC_CURR_BAL,FIELD_SIGACC_DOMCL_BRN_CODE,FIELD_SIGACC_ACC_CURRENCY,SIGACC_ACC_NO,"SIGACC_BRN_CODE","SIGACC_ACC_CURNY");
				formObject.clearTable(QVAR_LINKED_CUST);
			}			
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	public void LoadListViewFromFields(String controls,String colnames, String listView) {
		log.info("In loadlist view from form fields : "+controls+" -- colnames="+colnames+", -- gridName="+listView);
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		int selectedRow=-1;
		String[] columnArray;
		String[] controlArray;
		try {
			log.info("listview addition started"+listView);
			if(isEmpty(colnames) && isEmpty(controls)) {
				columnArray=  colnames.split(",");
				controlArray = controls.split(",");
				log.info("In loadlist view : "+columnArray.length);
				for (int i = 0; i < columnArray.length; i++) {
					
					if(columnArray[i].equalsIgnoreCase("SNo")) {
						String processType=formObject.getValue(PROCESS_TYPE).toString();
						if(processType.equalsIgnoreCase("TSLM Front End")){
							String sVesselName = formObject.getValue(TXT_LLI_VESSELNAME).toString();
							log.info("selected vessel name :"+sVesselName);
							int length = getGridCount(LISTVIEW_LLI);
							boolean flag=false;
							for(int j=0;j<length;j++){
								log.info("grid vessel name :"+formObject.getTableCellValue(LISTVIEW_LLI, j, 1));
								log.info("grid shipment date :"+formObject.getTableCellValue(LISTVIEW_LLI, j, 2));
								log.info("grid serial no :"+formObject.getTableCellValue(LISTVIEW_LLI, j, 0));
								
								if(sVesselName.equalsIgnoreCase(formObject.getTableCellValue(LISTVIEW_LLI, j, 1)) && (formObject.getTableCellValue(LISTVIEW_LLI, j, 2).isEmpty())){
									selectedRow=Integer.parseInt(formObject.getTableCellValue(LISTVIEW_LLI, j, 0).toString());
									log.info("selectedRow :"+selectedRow);
									break;
								}
							}
						}
						log.info("selectedRow :"+selectedRow);
						if(selectedRow==-1){
						jsonObject.put(columnArray[i],("".equalsIgnoreCase(formObject.getTableCellValue(listView, 0, 0)) ?
								"1":(Integer.parseInt(formObject.getTableCellValue(listView, 0, 0))+1)));
						}else{
							jsonObject.put(columnArray[i],selectedRow);
						}
					} else if(columnArray[i].equalsIgnoreCase(TFO_WI_NAME)) {
						jsonObject.put(columnArray[i],sWorkitemID);
					} else if(columnArray[i].contains("#DescriptionValue")) {
						String[] arr = columnArray[i].split("#");
						log.info("desc "+getDescriptionFromCode(controlArray[i], formObject.getValue(controlArray[i]).toString()));
						jsonObject.put(arr[0],getDescriptionFromCode(controlArray[i], formObject.getValue(controlArray[i]).toString()));
					} else { 
						jsonObject.put(columnArray[i],formObject.getValue(controlArray[i]).toString());
					}
				}
				jsonArray.add(jsonObject);
				formObject.addDataToGrid(listView, jsonArray);
			}
			log.info("json array: "+jsonArray);
		} catch (Exception e) {
			log.error("Exception in LoadListViewFromFields: "+e,e);
		}
	}

	protected void setEmailFlag(int fdReferalListCount) {
		log.info("***************Inside setEmailFlag******************"+fdReferalListCount);
		try {
			String checkBoxSendEmail=formObject.getValue(CHKBX_SEND_MAIL).toString();
			String manuallyTicketMail=formObject.getValue(MANUALLY_TICKED_MAIL).toString();
			log.info("iRowCount"+fdReferalListCount);
			log.info("MANUALLY_TICKED_MAIL="+manuallyTicketMail);
			if((null==manuallyTicketMail||"null".equalsIgnoreCase(manuallyTicketMail)|| "".equalsIgnoreCase(manuallyTicketMail))) { 
				if(fdReferalListCount>1&&FALSE.equalsIgnoreCase(checkBoxSendEmail)) {
					enableFieldOnDemand(CHKBX_SEND_MAIL);
					formObject.setValue(CHKBX_SEND_MAIL, TRUE);	
				}
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	public Boolean txnRefNoSubmitCheck() {
		boolean bFlg=true;
		try {
			String refNo="";
			String billRefNo="";
			if(("TL".equalsIgnoreCase(requestCategory) && "TL_LD".equalsIgnoreCase(requestType))
					|| ("IFP".equalsIgnoreCase(requestCategory) && "LD".equalsIgnoreCase(requestType))
					|| ("SCF".equalsIgnoreCase(requestCategory) && ("PD".equalsIgnoreCase(requestType) || "PDD".equalsIgnoreCase(requestType)))
					||("IFS".equalsIgnoreCase(requestCategory) && "LD".equalsIgnoreCase(requestType))
					||("IFA".equalsIgnoreCase(requestCategory) && "LD".equalsIgnoreCase(requestType))) {//CODE BY MOKSH
				if("PROCESSING MAKER".equalsIgnoreCase(sActivityName)) {
					refNo=formObject.getValue(TRANSACTION_REFERENCE).toString();
					billRefNo=formObject.getValue(BILL_LN_REFRNCE).toString();
				} else if("PROCESSING CHECKER".equalsIgnoreCase(sActivityName)) {
					refNo=formObject.getValue(TRANSACTION_REFERENCE).toString();
				}
			} else {			
				if("PROCESSING MAKER".equalsIgnoreCase(sActivityName)) {
					refNo=formObject.getValue(TRANSACTION_REFERENCE).toString();
				}
			}
			log.info("billRefNo  "+billRefNo + " <<< contract ref no>>" + refNo);
			if(txnRefNoFilter() && ((isEmpty(refNo) || isEmpty(billRefNo)) && (refNo.length()==16 || billRefNo.length()==16)))  {	
				List<String> paramlist =new ArrayList<>( );
				paramlist.add ("Text :"+sWorkitemID);
				paramlist.add ("Text :"+sActivityName);
				paramlist.add ("Text :"+refNo);
				paramlist.add ("Text :"+billRefNo);
				paramlist.add ("Text :"+requestCategory);
				paramlist.add ("Text :"+requestType);
				List statusProc = formObject.getDataFromStoredProcedure("TFO_DJ_DEDUPE_TXN_REFNO", paramlist);
				List<List<String>> lstDec=formObject.getDataFromDB("SELECT IS_TXN_REF_DEDUP FROM EXT_TFO WHERE WI_NAME='"+sWorkitemID+"'");
				if(null !=lstDec && !lstDec.isEmpty() && !lstDec.get(0).isEmpty() && null!=lstDec.get(0).get(0) && !"N".equalsIgnoreCase(lstDec.get(0).get(0))) {										
					sendMessageHashMap("", lstDec.get(0).get(0));
					bFlg=false;					
				}
			}			
		} catch (Exception e) {
			log.error("exception in txnRefNoSubmitCheck: "+e,e);
		}
		return bFlg;
	}

	private boolean txnRefNoFilter() {
		try {
			if(("IFS".equalsIgnoreCase(requestCategory) && "LD".equalsIgnoreCase(requestType))
					|| ("IFP".equalsIgnoreCase(requestCategory) && "LD".equalsIgnoreCase(requestType)) 
					|| ("IFA".equalsIgnoreCase(requestCategory) && "LD".equalsIgnoreCase(requestType)) //CODE BY MOKSH
					|| ("TL".equalsIgnoreCase(requestCategory) && "TL_LD".equalsIgnoreCase(requestType))
					|| ("SCF".equalsIgnoreCase(requestCategory) && ("PD".equalsIgnoreCase(requestType) || "PDD".equalsIgnoreCase(requestType))) 
					|| ("GRNT".equalsIgnoreCase(requestCategory) && ("NI".equalsIgnoreCase(requestType) || "GA".equalsIgnoreCase(requestType)))
					|| ("SBLC".equalsIgnoreCase(requestCategory) && "SBLC_NI".equalsIgnoreCase(requestType))	//RR
					|| ("ELC".equalsIgnoreCase(requestCategory) && "ELC_SLCA".equalsIgnoreCase(requestType))	//code added by mansi
					|| ("EC".equalsIgnoreCase(requestCategory) && "EC_BL".equalsIgnoreCase(requestType))
					|| ("IC".equalsIgnoreCase(requestCategory) && "IC_BL".equalsIgnoreCase(requestType))
					|| ("DBA".equalsIgnoreCase(requestCategory) && "DBA_BL".equalsIgnoreCase(requestType))
					|| ("ELCB".equalsIgnoreCase(requestCategory) && ("ELCB_BL".equalsIgnoreCase(requestType) || "ELCB_LDDI".equalsIgnoreCase(requestType)))
					|| ("ILCB".equalsIgnoreCase(requestCategory) && "ILCB_BL".equalsIgnoreCase(requestType))
					|| ("ELC".equalsIgnoreCase(requestCategory) && "ELC_LCA".equalsIgnoreCase(requestType))
					|| ("SG".equalsIgnoreCase(requestCategory) && ("SG_NILC".equalsIgnoreCase(requestType)||"SG_NIC".equalsIgnoreCase(requestType))) //Shipping_Gtee_63
					|| ("ILC".equalsIgnoreCase(requestCategory) && ("ILC_NI".equalsIgnoreCase(requestType) || "ILC_UM".equalsIgnoreCase(requestType)))) {
				return true;
			}
		} catch (Exception e) {
			log.error("exception in txnRefNoFilter: "+e,e);
		}
		return false;
	}

	protected boolean duplicateCheckValidation() {
		String sIsDup = "";
		String sDuplicateCheckConfirmation = "";
		sIsDup = formObject.getValue("IS_DEDUP").toString();
		sDuplicateCheckConfirmation = formObject.getValue(DUP_CHK_CONFIRMATION).toString();
		log.info("Dup Check "+sIsDup+" Confirmation "+sDuplicateCheckConfirmation);
		if("Y".equalsIgnoreCase(sIsDup)) {
			log.info("when dedup check is Y");
			if(sDuplicateCheckConfirmation.isEmpty() ||sDuplicateCheckConfirmation.equalsIgnoreCase("0") 
					||sDuplicateCheckConfirmation.equalsIgnoreCase("")) {
				sendMessageHashMap("", "Please select duplicate check confirmation");
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

	protected void makeNonEditableGteeIelcInputDtlsTab() {
		try {
			if(("GA".equalsIgnoreCase(requestType) || "GAA".equalsIgnoreCase(requestType) )) {
				log.info("********************For GA && GAAA case*************************");
				log.info("Disabling [DISABLE_PM_ADVISING_TXT]");
				disableControls(DISABLE_PM_ADVISING_TXT);
			} else {
				log.info("Disabling [DISABLE_PM_TXT] & [DISABLE_PM_LOV] & [btnAccDtl]");
				disableControls(DISABLE_PM_LOV);
				disableControls(DISABLE_PM_TXT);
				disableControls(ACCOUNT_DETAILS);
				
				if("ELC_SLCA".equalsIgnoreCase(requestType)){//mansi
					hideshowFrmInputTab(HIDE_PM_TXT,false);
				}
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	public void LoadListViewWithHardCodeValues(String values,String colnames, String listView) {
		log.info("In loadlist view from form fields : "+values+" -- colnames="+colnames+" -- gridName="+listView);
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		String[] columnArray;
		String[] valuesArray;
		try {
			if(isEmpty(colnames) && isEmpty(values)) {
				columnArray=  colnames.split(",");
				valuesArray = values.split("##");
				log.info("In loadlist view "+columnArray.length);
				for (int i = 0; i < valuesArray.length; i++) {
					log.info("grid column :"+columnArray[i]);
					log.info("grid value :"+valuesArray[i].toString());
					if("null".equalsIgnoreCase(valuesArray[i].toString()))
						valuesArray[i]="";
					jsonObject.put(columnArray[i],valuesArray[i].toString());
				}
				jsonArray.add(jsonObject);
				log.info("json array: "+jsonArray);
				formObject.addDataToGrid(listView, jsonArray);
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	public void handlePMFrameReqType()
	{
		try {
			String sTxnCurrency = " AND REQ_TXN_CRNCY = 'ALL'";
			String sLoanType = "and REQ_LOAN_TYPE = 'ALL'";
			String sProductType = "and req_prd_code='ALL'"; 
			if("ILCB".equalsIgnoreCase(requestCategory) || "TL".equalsIgnoreCase(requestCategory) || "IC".equalsIgnoreCase(requestCategory))
				sTxnCurrency=trnsCurrency.equalsIgnoreCase("AED")?"AND REQ_TXN_CRNCY IN ('AED','ALL')":"AND REQ_TXN_CRNCY IN ( 'ALL','NAED')";
			if("TL".equalsIgnoreCase(requestCategory))
				sLoanType = (formObject.getValue(IFS_LOAN_GRP).toString()).equalsIgnoreCase("IC")?"and REQ_LOAN_TYPE IN ('IC','ALL')":"and REQ_LOAN_TYPE IN ('ALL')";
			if(requestCategory.equalsIgnoreCase("IC")) {
				if(("T3U5".equalsIgnoreCase(formObject.getValue(PRODUCT_TYPE).toString())) || "I3U5".equalsIgnoreCase(formObject.getValue(PRODUCT_TYPE).toString())) {
					sProductType =" and req_prd_code in('"+formObject.getValue(PRODUCT_TYPE).toString()+"','ALL')";
				}
			}
			log.info("Product Type "+sProductType);
			log.info("transaction currency "+sTxnCurrency);
			log.info("Bill_Settlement_Curr "+sLoanType);
			List<List<String>> sResultList = null;
			String sQuery = "Select CONTROL_NAME, ENABLE_STATUS from TFO_DJ_FINAL_DEC_CNTRL_MAST where REQ_CAT_CODE='"
					+requestCategory+"' and REQ_TYPE_CODE = '"+requestType+"' and WORKSTEP = '"+sActivityName+"' "+
					sTxnCurrency+" "+sLoanType+" "+sProductType;
			log.info("sQuery for Field Population  "+sQuery);
			sResultList = formObject.getDataFromDB(sQuery); 
			log.info("Query for ResultList "+sResultList);
			if(sResultList != null && !sResultList.isEmpty()) {
				for(List<String> rowList:sResultList) {
					log.info("Control Info "+rowList);
					if("N".equalsIgnoreCase(rowList.get(1))) {
						disableControls(rowList.get(0));
					} 
					formObject.setStyle(rowList.get(0), VISIBLE, TRUE);
				}
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		} 
	}

	public void loadDataLOVFormLoad(String controlName){
		try {
			log.info("inside loadComboIndependently >> "+controlName);
			String controlList[]=controlName.split(",");
			for(int i=0;i<controlList.length;i++){
				String tmp = formObject.getValue(controlList[i]).toString();
				log.info("combo value from db to be set: "+tmp);
				if(!tmp.equalsIgnoreCase("")){
					log.info("adding item in combo "+controlList[i]);
					formObject.addItemInCombo(controlList[i], tmp);
					formObject.setValue(controlList[i], tmp);
				}
			} 
		} catch (Exception e) {
			log.error("exception in loadComboIndependently: "+e,e);
		}
	}

	public boolean validateMultipleMailId(String ctrName) {
		log.info("CtrName="+ctrName);
		String str1 = formObject.getValue(ctrName).toString();
		String str = str1.trim();
		if (str.equals("")) {
			return true;
		}
		String[] emailString = str.split(",");
		for (int i = 0; i < emailString.length; i++) {
			if (!validEmail(emailString[i])) {
				sendMessageHashMap(ctrName,"Please enter Valid Email Id.");
				return false;
			}
		}
		return true;
	}

	public boolean validEmail(String strNewEmail) {
		return strNewEmail.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	}

	protected void setHashMap(int finalDecisionList){
		try {

			String sKey ="";
			String sValue = "";
			log.info("finalDecisionList common ="+finalDecisionList );
			if(finalDecisionList>0) {
				log.info("finalDecisionList =1" );
				for(int cntr=0; cntr<=finalDecisionList;cntr++) {
					log.info("finalDecisionList =2" );
					sKey = formObject.getTableCellValue(LISTVIEW_FINAL_DECISION, cntr,0)+formObject.getTableCellValue(LISTVIEW_FINAL_DECISION, cntr,1);
					sValue =formObject.getTableCellValue(LISTVIEW_FINAL_DECISION, cntr,5); 
					if(sValue.isEmpty()){
						log.info("sValue="+formObject.getValue(FINAL_DECISION_GRID_ADDITIONAL_MAIL).toString());
						sValue=formObject.getValue(FINAL_DECISION_GRID_ADDITIONAL_MAIL).toString();
					}
					log.info("Key "+sKey);
					log.info(" Value "+sValue);
					hmapAdditionalMail.put(sKey, sValue);
				}
				log.info(hmapAdditionalMail);
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	public static String createNormalizedXML(String outputXml) {
		try {
			if ((outputXml != null) && (!("".equalsIgnoreCase(outputXml)))) {
				outputXml = outputXml.replace("'", "''");
				outputXml = outputXml.replace("&", "'||chr(38)||'");
				if (outputXml.length() > 3200) {				
					outputXml = breakCLOBString(outputXml);
					return outputXml;
				}
				outputXml = "'" + outputXml + "'";
				return outputXml;
			}			
		} catch (Exception ex) {
			log.error("Exception: ",ex);
		}
		return "''";
	}

	private static String breakCLOBString(String xml) {
		int itr = xml.length() / 3200;
		int mod = xml.length() % 3200;
		if (mod > 0) {
			++itr;
		}
		StringBuilder response = new StringBuilder();
		try {
			for (int i = 0; i < itr; ++i) {
				if (i == 0) {
					response.append("TO_NCLOB('" + xml.substring(0, 3200) + "')");
				} else if (i < itr - 1) {
					response.append(" || TO_NCLOB('" + xml.substring(3200 * i,3200 * (i + 1)) + "')");
				} else {
					response.append(" || TO_NCLOB('" + xml.substring(3200 * i,xml.length()) + "') ");
				}
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		return response.toString();
	}

	public void  setBranchCode(String controlName,boolean onLoad) {
		try {
			String txnBranchCode=formObject.getValue(RELATIONSHIP_TYPE).toString();
			log.info("txnBranchCode="+txnBranchCode);
			LinkedHashMap<String, String> innerMap = branchCodeMap.get(txnBranchCode);
			log.info("innerMap="+innerMap);
			String value=formObject.getValue(controlName).toString();
			if(!innerMap.isEmpty()) {
				formObject.clearCombo(controlName);
				for(Map.Entry<String, String> entry: branchCodeMap.get(txnBranchCode).entrySet()) {
					log.info("inside for loop: ");
					log.info("description: "+entry.getValue()+", code: "+entry.getKey());
					formObject.addItemInCombo(controlName,entry.getKey());
				}
			} else {
				formObject.clearCombo(controlName);
				enableFieldOnDemand(controlName);
			} if("C".equalsIgnoreCase(txnBranchCode)) {
				formObject.setValue(controlName,"299");
			} else if("I".equalsIgnoreCase(txnBranchCode)) {
				formObject.setValue(controlName,"799");
			}
			String isProtected=branchCodeMap.get(txnBranchCode).get(formObject.getValue(controlName).toString());
			log.info("isProtected="+isProtected);
			if("Y".equalsIgnoreCase(isProtected)) {
				disableFieldOnDemand(controlName);
			} else if("N".equalsIgnoreCase(isProtected)) {
				enableFieldOnDemand(controlName);
			}
			if(onLoad && (!("".equalsIgnoreCase(value)||"0".equalsIgnoreCase(value)))){
				formObject.setValue(controlName, value);
			}
		} catch(Exception e) {
			log.info("Exception is=",e);
		}
	}

	//Shipping_Gtee_89
	public String fetchFCUBSCustomerIC(String sCustID) throws Exception {
		String sOutput = "";
		@SuppressWarnings("unchecked")
		List<List<String>> sOutputlist = formObject.getDataFromDB("SELECT SEQ_WEBSERVICE.nextval as IDValue from DUAL");
		if (!sOutputlist.isEmpty() && !sOutputlist.get(0).isEmpty()) {
			sOutput = createCustomerICInputXML(sCustID, sOutputlist.get(0).get(0));
			sOutput = socket.connectToSocket(sOutput);
		}
		return sOutput;
	}
	public String createCustomerICInputXML(String sCustID, String sTxn) throws Exception {
		StringBuilder inputXml=new StringBuilder();
		try {

			inputXml.append("<?xml version=\"1.0\"?>").append("\n")
			.append("<APWebService_Input>").append("\n")
			.append("<Option>WebService</Option>").append("\n")
			.append("<EngineName>" + engineName + "</EngineName>").append("\n")
			.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
			.append("<Calltype>WS-2.0</Calltype>").append("\n")
			.append("<InnerCallType>TFO_InqCustPersonalDetails</InnerCallType>").append("\n")			
			.append("<WiName>" +sWorkitemID + "</WiName>").append("\n")
			.append("<REF_NO>" + sTxn + "</REF_NO>").append("\n")
			.append("<senderId>" + "WMS" + "</senderId>").append("\n")
			.append("<cust_id>" + sCustID + "</cust_id>").append("\n")			
			.append("</APWebService_Input>");
			log.info("input xml of TFO_InqCustPersonalDetails="+ inputXml.toString());
		} catch (Exception e) {
			log.error("Exception in",e);
		}
		return inputXml.toString();
	}
	public  void setCustomerICFCUBS(String reqCat){
		try
		{
			String requestType = formObject.getValue(REQUEST_TYPE).toString();
			if("Initiator".equalsIgnoreCase(sActivityName)&& ("SG".equalsIgnoreCase(reqCat) || "TL_LD".equalsIgnoreCase(requestType))){
				setCustomerDetails(fetchFCUBSCustomerIC(formObject.getValue(CUSTOMER_ID).toString()));

			}
		}catch(Exception e){
			log.error("Exception in",e);
		}
	}
	public  void setCustomerDetails(String sOutput){
		try
		{	
			sOutput = sOutput.replace("null", "");
			log.info("resCustSmry   >>>>>>>>" + sOutput);
			xmlDataParser = new XMLParser(sOutput);
			String sReturnCode = xmlDataParser.getValueOf("returnCode");
			log.info("return code: "+sReturnCode);

			if ("0".equalsIgnoreCase(sReturnCode)) {
				formObject.setValue(PT_CUSTOMER_IC, xmlDataParser.getValueOf("customerIC"));
			}

			log.info("sOutput="+sOutput);

		}catch(Exception e){
			log.error("Exception in",e);
		}
	}
	//end Shipping_Gtee_89
	public void saveDecHistory(String decision,String remarks) {
		String groupname = "";
		String personalName = "";
		String decQuery = "";
		String strRemarks = "";
		String sDecision = "";
		String sDuplicatecheck = "";
		String strReasonforAction = "";
		String emailFlag = "";
		if (this.sActivityName.equalsIgnoreCase("PP_MAKER")
				|| this.sActivityName.equalsIgnoreCase("PROCESSING MAKER")
				|| this.sActivityName.equalsIgnoreCase("PROCESSING CHECKER")){
			sDuplicatecheck = getDescriptionFromCode(DUP_CHK_CONFIRMATION, formObject.getValue(DUP_CHK_CONFIRMATION).toString());
		}
		try {
			String sQuery = "SELECT PERSONALNAME FROM PDBUSER WHERE UPPER(USERNAME)='"+ sUserName.toUpperCase() + "'";
			List<List<String>> sOutputlist = formObject.getDataFromDB(sQuery);
			if (sOutputlist != null && !sOutputlist.isEmpty()
					&& sOutputlist.get(0).size() > 0) {
				personalName = sOutputlist.get(0).get(0);
			}
			log.info("++mail"+ formObject.getValue(CHKBX_SEND_MAIL).toString());
			if (this.sActivityName.equalsIgnoreCase("Logistics Team")|| this.sActivityName.equalsIgnoreCase("Assignment Queue")) {
				emailFlag = "NA";
			} else {
				emailFlag = formObject.getValue(CHKBX_SEND_MAIL).toString();
			}
			sDecision=decision;
			strRemarks = remarks;
			decQuery = "INSERT INTO " + decHist + "( " + decHistCol
					+ " ) VALUES ('" + sWorkitemID + "','"
					+ this.sUserName.toUpperCase() + "','"
					+ formObject.getValue(PREV_WS).toString() + "','"
					+ sActivityName + "',sysdate,'" + personalName
					+ "','" + groupname + "','" + sDecision + "','"
					+ strReasonforAction + "',sysdate,'" + strRemarks
					+ "','" + formObject.getValue(INITIATOR_BRANCH)
					+ "','" + sDuplicatecheck + "','" + getRouteToVal()
					+ "','" + emailFlag + "')";

			log.info("Decision " + decQuery);
			formObject.saveDataInDB(decQuery);
		} catch (Exception e) {
			log.error("exception in saveDecHistory: " + e, e);
		}
	}

	public void insertAmendmnentDetails(XMLParser xmlParser){
		try {
			log.info("InsertAmendmnentDetail starts");	
			String may_Confirm =xmlParser.getValueOf("mayConfirmFlag");  
			String CONFIRMATION_INSTRUCTION="";
			String req_type = formObject.getValue(REQUEST_TYPE).toString();
			String swiftChannel = formObject.getValue(SWIFT_CHANNEL).toString();

			log.info("InsertAmendmnentDetail requestType ::  "+req_type + " & swiftChannel " + swiftChannel);
			if("AM".equalsIgnoreCase(req_type) ||"ELC_SLCAA".equalsIgnoreCase(req_type) 
				||"SBLC_AM".equalsIgnoreCase(req_type) || "GAA".equalsIgnoreCase(req_type))
				 { //ADDED BY KISHAN
					String columns = "INSERT INTO TFO_DJ_AMENDMENT_FRAME_DATA (INSERTIONORDERID,WINAME,"
							+ "FC_TRANSACTION_AMOUNT,FC_EXPIRY_DATE,"
							+ "FC_EXPIRY_TYPE,FC_EXPIRY_COND,FC_CNTR_GTEE_EXP_DATE) VALUES ";
					String expDate = xmlParser.getValueOf("ExpiryDate").equals("null") ? "" 
							 :xmlParser.getValueOf("ExpiryDate").equals("") ? "" :xmlParser.getValueOf("ExpiryDate"); 
					String insertionId = getInsertIonIdForTable("TFO_DJ_AMENDMENT_FRAME_DATA");
					String sExpiryConditions= xmlDataParser.getValueOf("expiryConditionOrEvent"); //BY KISHAN
					String sExpiryType = xmlDataParser.getValueOf("expiryType");
					String sTransactionAmount= xmlDataParser.getValueOf("contractAmount");
					String sExpiryDate = xmlDataParser.getValueOf("expiryDate");
					String sCounterGuaranteeExpiryDate="";
					if("AM".equalsIgnoreCase(req_type)) //Condition to fill value only when GRNT AM . As per Req.. .
						  sCounterGuaranteeExpiryDate = xmlDataParser.getValueOf("guaranteeExpiryDate");
					
					String trnTenor = "";
					if("LIMT".equalsIgnoreCase(sExpiryType)){
						trnTenor ="FD";
					}else if("UNLM".equalsIgnoreCase(sExpiryType)){
						trnTenor ="OE";
					}else if("COND".equalsIgnoreCase(sExpiryType)){
						trnTenor ="COND";
					}
					
					/*LIMT ( Fixed )
					UNLM ( Open )
					COND ( Conditional )*/

					String values = "('"+insertionId+"','"+sWorkitemID+"','"+sTransactionAmount+"',to_date('"+sExpiryDate+"','dd/MM/yyyy'),"
						+"'"+trnTenor+"','"+sExpiryConditions+"',to_date('"+sCounterGuaranteeExpiryDate+"','dd/MM/yyyy'))";
					String insertQuery = columns + values;
					log.info("insertAmendmnentDetail insert query :"+insertQuery);
					//String deleteQuery="delete from TFO_DJ_AMENDMENT_FRAME_DATA where WI_NAME = '"+sWorkitemID+"'";
					//log.info("insertAmendmnentDetail delete query :"+deleteQuery);
					//formObject.saveDataInDB(deleteQuery);
					//Added by SHivanshu ATp-490 MT798
					String updateQuery="UPDATE TFO_DJ_AMENDMENT_FRAME_DATA SET INSERTIONORDERID ='"+insertionId+"' , FC_TRANSACTION_AMOUNT ='"+sTransactionAmount+"' ,"
							+ " FC_EXPIRY_DATE = to_date('"+sExpiryDate+"','dd/MM/yyyy') , FC_EXPIRY_TYPE ='"+trnTenor+"' ,"
							+ " FC_EXPIRY_COND  ='"+sExpiryConditions+"' , FC_CNTR_GTEE_EXP_DATE  = to_date('"+sCounterGuaranteeExpiryDate+"','dd/MM/yyyy') "
							+ " where winame='"+sWorkitemID+"'";
					log.info("insertAmendmnentDetail update query :"+updateQuery);

					if (checkAmendmentFrameData()) {
						log.info("insertAmendmnentDetail inside insert");
					formObject.saveDataInDB(insertQuery);
					}else {
						log.info("insertAmendmnentDetail inside update");
						formObject.saveDataInDB(updateQuery);
					}
			}else{	
				if("ELC_LCAA".equalsIgnoreCase(req_type) ||"ELC_LCC".equalsIgnoreCase(req_type)){
					String soperation_code = xmlParser.getValueOf("operationCode");
					if(soperation_code.equalsIgnoreCase("ANC") || soperation_code.equalsIgnoreCase("CONFIRM")){
						CONFIRMATION_INSTRUCTION ="C";
					}else if (soperation_code.equalsIgnoreCase("ADV") && may_Confirm.equalsIgnoreCase("N")){
						CONFIRMATION_INSTRUCTION ="W";
					}else if (soperation_code.equalsIgnoreCase("ADV") && may_Confirm.equalsIgnoreCase("Y")){
						CONFIRMATION_INSTRUCTION ="M";
					}
				}
	
				String columns = "INSERT INTO TFO_DJ_AMENDMENT_FRAME_DATA (INSERTIONORDERID,WINAME,FC_TRANSACTION_AMOUNT,FC_EXPIRY_DATE,"
						+ "FC_UNDER_TOLERANCE,FC_ABOVE_TOLERANCE,FC_SHIPMENT_FROM,FC_SHIPMENT_TO,"
						+ "FC_LATEST_SHIPMENT_DATE,FC_PORT_OF_DISCHARGE,FC_PORT_OF_LOADING,FC_DESCRIPTION_OF_GOODS,"
						+ "FC_DRAFT_CREDIT_DAYS_FROM_DT,FC_DRAFT_CREDIT_DAYS_FROM_DAYS,FC_DRAFT_AMOUNT,"
						+ "FC_DRAFT_SPECIFY_OTHERS,FC_DRAFT_DRAWEE_BANK,FC_DEFERRED_PAYMENT,FC_PARTIAL_SHIPMENT,"
						+ "FC_PARTIAL_SHIPMENT_CONDITION,FC_TRANSSHIPMENT,FC_TRANSSHIPMENT_CONDITION,"
						+ "FC_SHIPMENT_PERIOD, FC_GOODS_CODE,FC_INCOTERM,FC_PLACE_OF_EXPIRY,FC_CREDIT_MODE,FC_DRAFT_TENOR,"
						+ "FC_REQUESTED_CONFIRMATION_PARTY,FC_CHARGES,FC_SPL_PAY_COND_FOR_BEN,FC_PERIOD_OF_PRESENTATION_DAYS,"
						+ "FC_PERIOD_OF_PRESENTATION_MODE,FC_CONFIRMATION_INSTRUCTION) VALUES ";
				String amount = xmlParser.getValueOf("contractAmount");
				String expDate = xmlParser.getValueOf("ExpiryDate").equals("null") ? "" :xmlParser.getValueOf("ExpiryDate").equals("") ? "" :xmlParser.getValueOf("ExpiryDate"); 
				String sPositiveTolerance = xmlParser.getValueOf("positiveTolerance");
				String sNegativeTolerance = xmlParser.getValueOf("negativeTolerance");
				String sFromPlace = xmlParser.getValueOf("fromPlace");
				String sToPlace = xmlParser.getValueOf("toPlace");
				String sLatestShipmentDate = xmlParser.getValueOf("latestShipmentDate");
				String sPortOfDischarge = xmlParser.getValueOf("portOfDischarge");
				String sPortOfLoading = xmlParser.getValueOf("portOfLoading");
				String sGoodsDescription = xmlParser.getValueOf("goodsDescription");
				String sGoodsCode = xmlParser.getValueOf("goodsCode");
				String insertionId = getInsertIonIdForTable("TFO_DJ_AMENDMENT_FRAME_DATA");
				sFromPlace = checkSplChar(sFromPlace);
				sToPlace = checkSplChar(sToPlace);
				sPortOfDischarge = checkSplChar(sPortOfDischarge);
				sPortOfLoading = checkSplChar(sPortOfLoading);
	
				//protrade_final 360-361
				String sexpiryPlace = xmlParser.getValueOf("expiryPlace");
				String screditMode = xmlParser.getValueOf("creditMode");
				String screditMode2 ="";
				/*if("P".equalsIgnoreCase("creditMode")){
					screditMode ="Sight Payment";
				}else if("A".equalsIgnoreCase(screditMode2)){
					screditMode ="Acceptance";
				}else if("D".equalsIgnoreCase(screditMode2)){
					screditMode ="Deferred Payment";
				}else if("M".equalsIgnoreCase(screditMode2)){
					screditMode ="Mixed  Payment";
				}else if("N".equalsIgnoreCase(screditMode2)){
					screditMode ="Negotiation";
				}*/
				String sdraftTenor = xmlParser.getValueOf("draftTenor");
				String screditDaysFrom = xmlParser.getValueOf("creditDaysFrom");
				String sdraftAmount = xmlParser.getValueOf("draftAmount");
				String sdrawee = xmlParser.getValueOf("drawee");
				String screditDetails = xmlParser.getValueOf("creditDetails");
	
				String spartialShipmentAllowed = xmlParser.getValueOf("partialShipmentAllowed");
				String spartialShipmentDetails = createNormalizedXML(xmlParser.getValueOf("partialShipmentDetails"));
				String stransShipmentAllowed = xmlParser.getValueOf("transShipmentAllowed");
				String stransShipmentDetails = createNormalizedXML(xmlParser.getValueOf("transShipmentDetails"));
				String srequestedConfirmationParty = xmlParser.getValueOf("requestedConfirmationParty");
				String schargesFromBeneficiary = createNormalizedXML(xmlParser.getValueOf("chargesFromBeneficiary"));
	
				String sspecialPaymemtConditionForBeneficiary = createNormalizedXML(xmlParser.getValueOf("specialPaymemtConditionForBeneficiary"));
				String speriodOfPresentationDays = xmlParser.getValueOf("periodOfPresentationDays");
				String speriodOfPresentationMode = xmlParser.getValueOf("periodOfPresentationMode");
	
				if("ILC_AM".equalsIgnoreCase(req_type) ){//protrade_final 360-361
					String soperation_code = xmlParser.getValueOf("operationCode");
					if(soperation_code.equalsIgnoreCase("ONC") || soperation_code.equalsIgnoreCase("CONFIRM")){
						CONFIRMATION_INSTRUCTION ="C";
					}else if (soperation_code.equalsIgnoreCase("OPN") && may_Confirm.equalsIgnoreCase("N")){
						CONFIRMATION_INSTRUCTION ="W";
					}else if (soperation_code.equalsIgnoreCase("OPN") && may_Confirm.equalsIgnoreCase("Y")){
						CONFIRMATION_INSTRUCTION ="M";
					}
				}
				String shipmentPeriod = xmlParser.getValueOf("shipmentPeriod");
				sGoodsDescription = createNormalizedXML(sGoodsDescription);
				if(sGoodsDescription == null || "".equalsIgnoreCase(sGoodsDescription)) {
					sGoodsDescription = "''";
				}
				String values = "('"+insertionId+"','"+sWorkitemID+"','"+amount+"',to_date('"+expDate+"','dd/MM/yyyy'),'"+sNegativeTolerance+"','"+sPositiveTolerance+"','"+sFromPlace+"','"+sToPlace+"',to_date('"+sLatestShipmentDate+"','dd/MM/yyyy'),'"+sPortOfDischarge+"','"+sPortOfLoading+"',"+sGoodsDescription+",'','"+screditDaysFrom+"','"+sdraftAmount+"','','"+sdrawee+"','"+screditDetails+"','"+spartialShipmentAllowed+
						"',"+spartialShipmentDetails+",'"+stransShipmentAllowed+"',"+stransShipmentDetails+","
						+ "'"+shipmentPeriod+"','"+sGoodsCode+"','','"+sexpiryPlace+"','"+screditMode+"','"+sdraftTenor+"','"+srequestedConfirmationParty+"',"+schargesFromBeneficiary+","+sspecialPaymemtConditionForBeneficiary+",'"+speriodOfPresentationDays+"','"+speriodOfPresentationMode+"','"+CONFIRMATION_INSTRUCTION+"')";
				String insertQuery = columns + values;
				log.info("insertAmendmnentDetail insert query :"+insertQuery);
			
				//String deleteQuery="delete from TFO_DJ_AMENDMENT_FRAME_DATA where WI_NAME = '"+sWorkitemID+"'";
				//log.info("insertAmendmnentDetail delete query :"+deleteQuery);
				//formObject.saveDataInDB(deleteQuery);
				//Added by SHivanshu ATp-490 MT798
				String updateQuery="UPDATE TFO_DJ_AMENDMENT_FRAME_DATA SET INSERTIONORDERID ='"+insertionId+"' , FC_TRANSACTION_AMOUNT ='"+amount+"' ,"
						+ " FC_EXPIRY_DATE = to_date('"+expDate+"','dd/MM/yyyy') , FC_UNDER_TOLERANCE ='"+sNegativeTolerance+"' ,"
						+ " FC_ABOVE_TOLERANCE  ='"+sPositiveTolerance+"' , FC_SHIPMENT_FROM  ='"+sFromPlace+"' , FC_SHIPMENT_TO  ='"+sToPlace+"' ," 
						+ " FC_LATEST_SHIPMENT_DATE  = to_date('"+sLatestShipmentDate+"','dd/MM/yyyy') , FC_PORT_OF_DISCHARGE ='"+sPortOfDischarge+"' ,"
						+ " FC_PORT_OF_LOADING ='"+sPortOfLoading+"' , FC_DESCRIPTION_OF_GOODS = "+sGoodsDescription+" ,"
						+ " FC_DRAFT_CREDIT_DAYS_FROM_DT = '' ,FC_DRAFT_CREDIT_DAYS_FROM_DAYS ='"+screditDaysFrom+"' ,FC_DRAFT_AMOUNT ='"+sdraftAmount+"' ,"
						+ " FC_DRAFT_SPECIFY_OTHERS ='' ,FC_DRAFT_DRAWEE_BANK ='"+sdrawee+"' ,FC_DEFERRED_PAYMENT ='"+screditDetails+"' ,FC_PARTIAL_SHIPMENT ='"+spartialShipmentAllowed+"' ,"
						+ " FC_PARTIAL_SHIPMENT_CONDITION ="+spartialShipmentDetails+" ,FC_TRANSSHIPMENT ='"+stransShipmentAllowed+"' ,FC_TRANSSHIPMENT_CONDITION ="+stransShipmentDetails+" ,"
						+ " FC_SHIPMENT_PERIOD ='"+shipmentPeriod+"' , FC_GOODS_CODE ='"+sGoodsCode+"' ,FC_INCOTERM ='' ,FC_PLACE_OF_EXPIRY ='"+sexpiryPlace+"' ,FC_CREDIT_MODE ='"+screditMode+"' ,"
						+ " FC_DRAFT_TENOR ='"+sdraftTenor+"' , FC_REQUESTED_CONFIRMATION_PARTY ='"+srequestedConfirmationParty+"' ,FC_CHARGES ="+schargesFromBeneficiary+" ,"
						+ " FC_SPL_PAY_COND_FOR_BEN ="+sspecialPaymemtConditionForBeneficiary+" ,FC_PERIOD_OF_PRESENTATION_DAYS ='"+speriodOfPresentationDays+"' ,"
						+ " FC_PERIOD_OF_PRESENTATION_MODE ='"+speriodOfPresentationMode+"' ,FC_CONFIRMATION_INSTRUCTION ='"+CONFIRMATION_INSTRUCTION+"'  "
						+ " where winame='"+sWorkitemID+"'";
				log.info("insertAmendmnentDetail update query :"+updateQuery);

				if (checkAmendmentFrameData()) {
					log.info("insertAmendmnentDetail inside insert");
				formObject.saveDataInDB(insertQuery);
				}else {
					log.info("insertAmendmnentDetail inside update");
					formObject.saveDataInDB(updateQuery);
				}
			}
			log.info("insertAmendmnentDetail ENDS");
		} catch (Exception e) {		
			log.error("exception in saveDecHistory: " + e, e);
		}
	}

	public void removeItemFromCombo(String comboId, String value){
		try {
			log.info("inside removeItemFromCombo >>");
			EComboControl combo = (EComboControl) formObject.getIFormControl(comboId);
			int itemIndex = -1;
			int counter = 0 ;
			ArrayList<EControlOption> list = combo.getM_objControlOptions().getM_arrOptions();
			for(EControlOption objOption: list){
				counter++;
				log.info("objOption.getM_strOptionValue(): "+objOption.getM_strOptionValue());
				if(objOption.getM_strOptionValue().equalsIgnoreCase(value)){
					log.info("found match for: "+objOption.getM_strOptionValue());
					itemIndex = counter;
					log.info("combo itemIndex: "+itemIndex);
					break;
				}
			}
			if(itemIndex != -1){
				log.info("CHECK 1 combo itemIndex: "+itemIndex);
				formObject.removeItemFromCombo(comboId, itemIndex);
				log.info("item removed >>");
			}
		} catch (Exception e) {
			log.error("exception: ",e);
		}
	}

	public String createChildWorkitem(){
		StringBuilder inputXml=new StringBuilder();
		try {
			log.info("inside PC createChildWorkitem");
			List<List<String>> sOutputlist = formObject.getDataFromDB("SELECT SEQ_WEBSERVICE.nextval as IDValue from DUAL");
			String txnNumber= sOutputlist.get(0).get(0);
			log.info("OutputList 0 index: "+sOutputlist.get(0).get(0));
			inputXml.append("<?xml version=\"1.0\"?>").append("\n")
			.append("<APWebService_Input>").append("\n")
			.append("<Option>WebService</Option>").append("\n")
			.append("<EngineName>" + engineName + "</EngineName>").append("\n")
			.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
			.append("<Calltype>WS-2.0</Calltype>").append("\n")
			.append("<InnerCallType>BPMModify</InnerCallType>").append("\n")			
			.append("<wiNumber>" +sWorkitemID + "</wiNumber>").append("\n")
			.append("<REF_NO>" + txnNumber + "</REF_NO>").append("\n")
			.append("<senderId>" + "WMS" + "</senderId>").append("\n")
			.append("<mode>C</mode>").append("\n")
			.append("<channelName>TFO_RULES</channelName>").append("\n")
			.append("<correlationId>"+txnNumber+"</correlationId>").append("\n")
			.append("<channelRefNumber>"+txnNumber+"</channelRefNumber>").append("\n")
			.append("<sysrefno>"+txnNumber+"</sysrefno>").append("\n")
			.append("<processName>TFO</processName>").append("\n")
			.append("</APWebService_Input>");
			log.info("input xml of createWorkitemInputXML="+ inputXml.toString());
			String sOutput = socket.connectToSocket(inputXml.toString());
			log.info("createChildWorkitem output: "+sOutput);
			XMLParser xp = new XMLParser(sOutput);
			if(Integer.parseInt(xp.getValueOf("StatusCode")) == 0){
				return getReturnMessage(true, "","");
			} else {
				return getReturnMessage(false, "",xp.getValueOf("StatusMessage"));
			}
		} catch (Exception e) {
			log.error("Exception in createChildWorkitem from PC: ",e);
			return getReturnMessage(false, "","Exception");
		}
	}

	public void insertFCUBSIntegrationCalls(String sCallOperation,String sCallName) {
		try {
			String sInsertDeleteScript = "DELETE FROM TFO_DJ_INTEGRATION_CALLS WHERE WI_NAME = '" + sWorkitemID + "'";
			formObject.saveDataInDB(sInsertDeleteScript);
			log.info("***************Inside toInsertIntegrationCalls******************");
			String columnNames = "SNO,WI_NAME,CALL_OPERATION,CALL_DT,CALL_STATUS,RETRY_COUNT,CALL_NAME";
			sInsertDeleteScript = "INSERT INTO TFO_DJ_INTEGRATION_CALLS ("+columnNames+") VALUES (1,'" + sWorkitemID + "','" + sCallOperation + "',SYSTIMESTAMP,'N','0','"+sCallName+"')";
			log.info("[sInsertScript for insertFCUBSIntegrationCalls ]"+sInsertDeleteScript);
			formObject.saveDataInDB(sInsertDeleteScript);
			log.info("Data Inserted");
		}catch(Exception e) {
			log.error("Exception: ",e);
		}

	}
	//New Workitem creation BRMS Rule ..PT From PC,PCPS,(SWIFT,BAU)-from PPM
	//US_SPRINT4_PT_148
	public String CreateNewWorkitem(){
		try{
			String processType   =null;
			String billType      =null;
			String discrpanyInst =null;
			String settlInstr    =null;
			Date maturityDate    =null;
			String ifSightBill=null;
			String targetQueue=null;
			String billLoanReference="";
			String query="";
			List<List<String>> list=null;
			List<String> listData=null;
			int output;
			Date currentDate     =sdf.parse(sdf.format(new Date())); 
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

			String maturityFlag  ="EqualOrLess";
			String linkedWI="";
			boolean ruleFlag=true;

			if("PP_MAKER".equalsIgnoreCase(sActivityName)||"PROCESSING CHECKER".equalsIgnoreCase(sActivityName)){
				processType   =formObject.getValue(PROCESS_TYPE).toString();
				billType      =formObject.getValue(BILL_TYPE).toString();
				discrpanyInst =formObject.getValue(DISCREPANCY_INSTRUCTION).toString();
				settlInstr    =formObject.getValue(SETTLEMENT_INSTRUCTION).toString();
				maturityDate  =sdf.parse(formObject.getValue(INF_MATURITY_DATE).toString());
				ifSightBill   = formObject.getValue(IF_SIGHT_BILL).toString();
				query="select LINKED_WI_NAME,BILL_LN_REFRNCE from ext_tfo where wi_name='"+this.sWorkitemID+"'";
				log.info("createNewWorkitemELCB=" + query);
				list=formObject.getDataFromDB(query);
				log.info("sOutputlist= " + list);
				if(!list.get(0).get(0).equalsIgnoreCase("")){
					linkedWI = (String) ((List) list.get(0)).get(0);
					billLoanReference=((String) ((List) list.get(0)).get(1));
				}

			}else if("PC PROCESSING SYSTEM".equalsIgnoreCase(sActivityName)){
				query="select PROCESS_TYPE,BILL_TYPE,DISCREPANCY_INSTRUCTION,SETTLEMENT_INSTRUCTION,"
						+ "INF_MATURITY_DATE,LINKED_WI_NAME,IF_SIGHT_BILL,BILL_LN_REFRNCE from ext_tfo where wi_name='"+this.sWorkitemID+"'";
				log.info("createNewWorkitemELCB=" + query);
				list=formObject.getDataFromDB(query);
				log.info("sOutputlist= " + list);
				if (!list.isEmpty()) {
					processType = ((String) ((List) list.get(0)).get(0));
					billType=((String) ((List) list.get(0)).get(1));
					discrpanyInst=((String) ((List) list.get(0)).get(2));
					settlInstr=((String) ((List) list.get(0)).get(3));
					maturityDate=sdf1.parse((String) ((List) list.get(0)).get(4));
					linkedWI=((String) ((List) list.get(0)).get(5));
					ifSightBill=((String) ((List) list.get(0)).get(6));
					billLoanReference=((String) ((List) list.get(0)).get(6));
				}
			}
			query="select count(1) from ext_tfo where linked_wi_name='"+this.sWorkitemID+"'";
			log.info("get linked winmae exist=" + query);
			list=formObject.getDataFromDB(query);
			log.info("sOutputlist= " + list);
			if(!list.get(0).get(0).equalsIgnoreCase("")){
				String value = (String) ((List) list.get(0)).get(0);
				if("1".equalsIgnoreCase(value)){
					ruleFlag=false;
				}else{
					ruleFlag=true;
				}
			}
			log.info("ruleFlag= " + ruleFlag);
			log.info("maturityDate="+maturityDate);
			log.info("currentDate="+currentDate);
			if(maturityDate.after(currentDate)){
				maturityFlag="Greater";
			}
			String key = processType+"#"+requestCategory+"#"+requestType+"#"+billType+"#"+ifSightBill+"#"+discrpanyInst+"#"+settlInstr
					+"#"+maturityFlag;	
			log.info("query"+key);
			log.info("brmsMap="+brmsMap);
			if (brmsMap.containsKey(key)) {
				listData=brmsMap.get(key);

			}
			log.info("data="+listData);
			if(("".equalsIgnoreCase(linkedWI)|| "null".equalsIgnoreCase(linkedWI)) 
					&& listData.size()>0 &&"R1".equalsIgnoreCase(listData.get(0))
					&& ruleFlag){
				/* query = "select rule_no, target_queue from TFO_DJ_BRMS_MASTER where "
					 + "process_type='"+processType+"'and request_category_code='"+requestCategory
				     +"' and request_type_code='"+requestType+"' and bill_type='"+billType+"' and discrepancy_instruction='"+
					 discrpanyInst+"' and settlement_instruction='"+settlInstr+"' and maturity_date='"+maturityFlag+"'";
				 */ 
				targetQueue=listData.get(1);

				//firing workitem creation call via jsp
				String  reqcatDesc=getDescriptionFromCode(REQUEST_CATEGORY,formObject.getValue(REQUEST_CATEGORY).toString());
				String  reqTypeDesc=getDescriptionFromCode(REQUEST_TYPE,formObject.getValue(REQUEST_TYPE).toString());
				insertFCUBSIntegrationCalls("Linked_Workitem_Creation","LinkWorkitem_"+reqcatDesc+"_"+reqTypeDesc);
				//child workitem target workstep 
				query="update ext_tfo set target_workstep='"+targetQueue+"' where wi_name='"+this.sWorkitemID+"'";
				log.info("Saving in ext table records query : "+query);
				output=formObject.saveDataInDB(query);
				log.info("Aupdate query result : "+output);
				if("PC PROCESSING SYSTEM".equalsIgnoreCase(sActivityName)){
					//firing workitem creation call
					return createChildWorkitem();
				}
				//return getReturnMessage(true, "","OpenLinkWIJSP");
				return "OpenLinkWIJSP";




			}//R2 rule only for BAU/SWIFT
			// if(listData.size()>0&&"R2".equalsIgnoreCase(listData.get(0))&&"PP_MAKER".equalsIgnoreCase(sActivityName)){
			if(listData.size()>0&&"R2".equalsIgnoreCase(listData.get(0))&&
					(!("Y".equals(formObject.getValue(PT_UTILITY_FLAG).toString())))
					&& ruleFlag){

				targetQueue=listData.get(1);

				if(!("PP_MAKER".equalsIgnoreCase(targetQueue) &&"PP_MAKER".equalsIgnoreCase(sActivityName))){
					query="update ext_tfo set target_workstep='"+targetQueue+"' where wi_name='"+this.sWorkitemID+"'";
					log.info("Saving in ext table records query : "+query);
					output=formObject.saveDataInDB(query);
					log.info("Aupdate query result : "+output);
				}
				if("ToDoList".equalsIgnoreCase(targetQueue)){
					String TXN_expiry_date = formObject.getValue(INF_MATURITY_DATE).toString();
					query="insert into BPM_EVENTGEN_TXN_DATA(insertiondatetime, wi_name, expiry_date, status_flag,PROCESS_NAME, "
							+ "SOURCING_CHANNEL, REQUESTMODE)"
							+ " values(SYSDATE,'"+this.sWorkitemID+"',TO_DATE('"+TXN_expiry_date+"','DD/MM/YYYY'),'N','TFO','TFO_RULES','X')";
					log.info("Saving in ext table records query : "+query);
					output=formObject.saveDataInDB(query);
					log.info("response : "+output);

				}
				String rout_to_remark ="Workitem routed to " +targetQueue;
				saveDecHistory("Rule R2 executed",rout_to_remark);
				discardExistingWorkitem(billLoanReference);
			}
		}catch(Exception e){
			log.error("Exception in insertIntoNotificationTxn: ",e);
		}
		return  getReturnMessage(true, "","");
	}
	public void discardExistingWorkitem(String billLoanReference){
		List<List<String>> list=null;
		String query="";
		String fieldName="";
		String fieldValue="";

		try{
			query="select field_name from TFO_DJ_RECEPTION_FETCH_MAST where req_cat_code='"+requestCategory+"' and req_type_code='"+requestType+"' and process_type='PT' and rownum=1";
			log.info("discardExistingWorkitem=" + query);
			list=formObject.getDataFromDB(query);
			log.info("sOutputlist=" + list);
			if (!list.isEmpty()) {
				fieldName=((String) ((List) list.get(0)).get(0));
			}

			if("TRANSACTION_REFERENCE".equalsIgnoreCase(fieldName)){
				fieldValue="(TRANSACTION_REFERENCE='"+formObject.getValue(TRANSACTION_REFERENCE).toString()+"' OR "
						+ "BILL_LN_REFRNCE='"+formObject.getValue(TRANSACTION_REFERENCE).toString()+"')";
			}else if("BILL_LN_REFRNCE".equalsIgnoreCase(fieldName)){
				fieldValue="(BILL_LN_REFRNCE='"+billLoanReference+"' OR TRANSACTION_REFERENCE='"+billLoanReference+"')";
			}
			query="select wi_name from ext_tfo where  CURR_WS = 'ToDoList' AND "+fieldValue+" AND (TARGET_WORKSTEP is  null or (TARGET_WORKSTEP <> 'DC_PROTRADE' AND TARGET_WORKSTEP <> 'DM_PROTRADE') )";
			log.info("discardExistingWorkitem=" + query);
			list=formObject.getDataFromDB(query);
			log.info("sOutputlist= " + list);
			if (!list.isEmpty()) {
				for (int counter = 0; counter < list.size(); counter++) {
					String wiName =  (String) ((List) list.get(counter)).get(0);
					query="update ext_tfo set target_workstep='A' where wi_name='"+wiName+"'";
					log.info("Saving in ext table records query : "+query);
					int output=formObject.saveDataInDB(query);
					log.info("Aupdate query result : "+output);
					query="insert into BPM_EVENTGEN_TXN_DATA(insertiondatetime, wi_name, expiry_date, status_flag,PROCESS_NAME, "
							+ "SOURCING_CHANNEL, REQUESTMODE)"
							+ " values(SYSDATE,'"+wiName+"',SYSDATE,'N','TFO','TRADE','D')";
					log.info("Saving in ext table records query : "+query);
					output=formObject.saveDataInDB(query);
					log.info("response : "+output);
				}
			}
		}catch(Exception e){
			log.error("Exception in discardExistingWorkitem: ",e);
		}
	}
	public boolean checkCallStatus(){
		try {
			log.info("inside checkCallStatus >>");
			String query = "select count(1) from BPM_ORCHESTRATION_STATUS where "
					+ "WI_NAME = '"+this.sWorkitemID+"' and CALL_STATUS='N'";
			List<List<String>> list = formObject.getDataFromDB(query);
			int count = Integer.parseInt(list.get(0).get(0));
			log.info("call failed count: "+count);
			if(count > 0){
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			log.error("exception in checkCallStatus: ",e);
		}
		return false;
	}

	public String getTSLMCompanyDetailsInputXML(String SCUSTID,String pCode, String stdLoanFlg, String loanCcy,
			String compyType,String sOperationName) {
		log.info("INSIDE getTSLMCompanyDetailsInputXML");
		List<List<String>> sOutputlist = formObject.getDataFromDB("SELECT SEQ_WEBSERVICE.nextval as IDValue from DUAL");
		String txnNumber= sOutputlist.get(0).get(0);
		if(!formObject.getValue(STANDALONE_LOAN).toString().equalsIgnoreCase(""))
			stdLoanFlg = (formObject.getValue(STANDALONE_LOAN).toString().equalsIgnoreCase("1"))?"Y":"N";
		log.info("compyType ::"+compyType);
		if(!compyType.equalsIgnoreCase("")){
			if(compyType.equalsIgnoreCase("B")){
				compyType = "BUYER";
				log.info("compyType ::"+compyType);
			}else if(compyType.equalsIgnoreCase("S")){
				compyType = "SELLER";
				log.info("compyType ::"+compyType);
			}else{
				compyType = "BOTH";
				log.info("compyType ::"+compyType);
			}
		}
		log.info("inside getTSLMCorpLoanDetailsInputXML >>>");
		return "<?xml version=\"1.0\"?><APWebService_Input>"
		+ "<Option>WebService</Option>"
		+"<Calltype>WS-2.0</Calltype>"
		+"<InnerCallType>TFO_TSLMCorpLoanDetails</InnerCallType>"
		+"<EngineName>" + engineName + "</EngineName>"
		+"<SessionId>" + sessionId + "</SessionId>"
		+"<serviceName>ModTSLMCorpLoanDetails</serviceName>"
		+"<serviceAction>Modify</serviceAction>"
		+"<senderId>" + "WMS" + "</senderId>"
		+"<correlationId>"+txnNumber+"</correlationId>"
		+"<channelRefNumber>"+txnNumber+"</channelRefNumber>"
		+"<sysRefNumber>"+txnNumber+"</sysRefNumber>"
		+"<CUSTOMERID>"+SCUSTID+"</CUSTOMERID>"
		+"<PRODUCTCODE>"+pCode+"</PRODUCTCODE>"
		+"<LOANCCY>"+loanCcy+"</LOANCCY>"
		+"<STDLOANFLAG>"+stdLoanFlg+"</STDLOANFLAG>"
		+"<COMPANYTYPE>"+compyType+"</COMPANYTYPE>"
		+"<operationType>"+ sOperationName + "</operationType>"
		+"<winame>"+sWorkitemID+"</winame>"
		+"</APWebService_Input>";
	}

	public String FetchTSLMCompanyDetailsOutputXML(String SCUSTID,String pCode,String stdLoanFlg,
			String loanCcy,String compyType,String sOperationName){
		String inputXML = getTSLMCompanyDetailsInputXML(SCUSTID,pCode,stdLoanFlg,loanCcy,compyType,sOperationName);
		log.info("input xml:"+inputXML);
		String sOutput = socket.connectToSocket(inputXML);
		return sOutput;
	}

	public String getTSLMLoanDetailsInputXML(String loanID,String sOperationName) {
		List<List<String>> sOutputlist = formObject.getDataFromDB("SELECT SEQ_WEBSERVICE.nextval as IDValue from DUAL");
		String txnNumber= sOutputlist.get(0).get(0);
		log.info("inside getTSLMCorpLoanDetailsInputXML >>>");
		return "<?xml version=\"1.0\"?><APWebService_Input>"
		+ "<Option>WebService</Option>"
		+"<Calltype>WS-2.0</Calltype>"
		+"<InnerCallType>TFO_TSLMCorpLoanDetails</InnerCallType>"
		+"<EngineName>" + engineName + "</EngineName>"
		+"<SessionId>" + sessionId + "</SessionId>"
		+"<serviceName>ModTSLMCorpLoanDetails</serviceName>"
		+"<serviceAction></serviceAction>"
		+"<winame>"+sWorkitemID+"</winame>"
		+"<senderId>" + "WMS" + "</senderId>"
		+"<correlationId>"+txnNumber+"</correlationId>"
		+"<channelRefNumber>"+txnNumber+"</channelRefNumber>"
		+"<sysRefNumber>"+txnNumber+"</sysRefNumber>"
		+"<loanId>"+loanID+"</loanId>"
		+"<operationType>"+ sOperationName + "</operationType>"
		+"</APWebService_Input>";
	} 

	public String FetchTSLMLoanDetailsOutputXML(String sLoanID,String sOperationName){
		String inputXML = getTSLMLoanDetailsInputXML(sLoanID,sOperationName);
		log.info("input xml:"+inputXML);
		String sOutput = socket.connectToSocket(inputXML);
		return sOutput;
	}
	public String populateTSLMLoanDetails(String sContractRefNo){
		String retMsg = "";
		log.info("inside populateTSLMLoanDetails ");
		String sLoanID = sContractRefNo;//formObject.getValue(TRNS_REF_NO).toString();
		log.info("Values ::"+ sLoanID);
		String sOperationName = "fetchTSLMLoanDtl_Oper";
		try{
			String sOutputXML = FetchTSLMLoanDetailsOutputXML(sLoanID,sOperationName);
			log.info("sOutputXML ::"+ sOutputXML);
			XMLParser xmlparser = new XMLParser(sOutputXML);
			String oLoanID = xmlparser.getValueOf("loanId");
			log.info(" oLoanID ::" + oLoanID);	
			formObject.setValue(TRNS_REF_NO,oLoanID);
			String oLoanCcy  = xmlparser.getValueOf("loanCcy");
			log.info(" loanCcy ::" + oLoanCcy);	
			formObject.setValue(INF_LOAN_CURR,oLoanCcy);
			String oProdCode  = xmlparser.getValueOf("productCode");
			log.info(" productCode ::" + oProdCode);
			formObject.setValue(PRODUCT_TYPE,oProdCode);			
			String oCID  = xmlparser.getValueOf("cid");
			log.info(" cid ::" + oCID);	
			formObject.setValue(CUSTOMER_ID,oCID);
			String oCustName  = xmlparser.getValueOf("customerName");
			log.info(" customerName ::" + oCustName);	
			formObject.setValue(CUSTOMER_NAME,oCustName);
			String oAmntFin  = xmlparser.getValueOf("amountFinanced");
			log.info(" oAmntFin ::" + oAmntFin);	
			formObject.setValue("FINANCED_AMOUNT",oAmntFin);
			String oLoanAmt  = xmlparser.getValueOf("osLoanAmt");
			log.info(" oLoanAmt ::" + oLoanAmt);
			formObject.setValue("OVERALL_OUTSTANDING_AMOUNT",oLoanAmt);			
			String oPrincAmt  = xmlparser.getValueOf("osPrincipalAmount");
			log.info(" oPrincAmt ::" + oPrincAmt);
			//formObject.setValue(INF_LOAN_CURR,oLoanID);	//not able to find		
			String oTnxCcy  = xmlparser.getValueOf("txnCcy");
			log.info(" oTnxCcy ::" + oTnxCcy);
			formObject.setValue(TRANSACTION_CURRENCY,oTnxCcy);			
			String oTxnAmt  = xmlparser.getValueOf("txnAmt");
			log.info(" oTxnAmt ::" + oTxnAmt);	
			formObject.setValue(TRANSACTION_AMOUNT,oTxnAmt);
			String oMatDate  = xmlparser.getValueOf("maturityDate");
			log.info(" oMatDate ::" + oMatDate);
			//ATP-359 DATE 03-01-2024 Krishna
			//START CODE
			String sDate="";
			Date d = new Date();
			if(oMatDate.contains(" ")){
				sDate = oMatDate.split(" ")[0];
			}else{
				sDate = oMatDate;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			d = sdf.parse(sDate);
			oMatDate = sdf1.format(d);
			//END
			formObject.setValue(INF_MATURITY_DATE,oMatDate);			
			String oLoanStatus = xmlparser.getValueOf("loanStatus");
			log.info(" oLoanStatus ::" + oLoanStatus);
			//formObject.setValue(INF_LOAN_CURR,oLoanID);		//not able to find	
			String oLoanStage  = xmlparser.getValueOf("loanStage");
			log.info(" oLoanStage ::" + oLoanStage);	
			formObject.setValue("LOAN_STAGE",oLoanStage);

			String oCustPrtyID  = xmlparser.getValueOf("counterPartyID");
			log.info(" counterPartyID ::" + oCustPrtyID);	
			String oCustPrtyName  = xmlparser.getValueOf("counterPartyName");
			log.info(" oCustPrtyName ::" + oCustPrtyName);	

			String query  ="Insert into TFO_DJ_TSLM_COUNTER_PARTY_DETAILS "
					+ "values('"+oCustPrtyID+"','"+oCustPrtyName+"','','','"+sWorkitemID+"','1','')";
			log.info("Insert TFO_DJ_TSLM_COUNTER_PARTY_DETAILS "+query);

			int retu = formObject.saveDataInDB(query);
			log.info("Insert Query TFO_DJ_TSLM_COUNTER_PARTY_DETAILS Return "+retu);		

			/*
				String sQuery  ="Insert into TFO_DJ_TSLM_LOAN_REFERENCE_DETAILS "
						+ "values('1','"+oCustPrtyID+"','"+oLoanID+"','"+sWorkitemID+"','1')";
				log.info("Insert Query TFO_DJ_TSLM_COUNTER_PARTY_DETAILS ::"+query);

				int sRetu = formObject.saveDataInDB(query);
				log.info("Insert Query TFO_DJ_TSLM_LOAN_REFERENCE_DETAILS Return "+sRetu);	*/
			//Customer detail fetch Call
			retMsg = fetchCustomerData(oCID, formObject.getValue(REQUEST_CATEGORY).toString(), false);
			log.info("fetchCustomerData"+retMsg);
			filldataInLoanRefInitiator(oCustPrtyID,oLoanID);

			log.info("END populateTSLMLoanDetails ");
		}catch(Exception e){
			log.error("Exception in populateTSLMLoanDetails: ",e);
		}
		return retMsg;
	}

	public void filldataInLoanRefInitiator(String CounterPartyID,String oLoanID){ //BY KISHAN TSLMS
		log.info("INSIDE filldataInLoanRefInitiator ");
		String Query="";
		int i ;
		String Request_CAT = formObject.getValue(REQUEST_CATEGORY).toString();
		String Request_TYPE = formObject.getValue(REQUEST_TYPE).toString();
		String Processing_System = formObject.getValue(PROCESSING_SYSTEM).toString();
		if(Processing_System.equalsIgnoreCase("T")){
			if ((Request_CAT.equalsIgnoreCase("IFA") || Request_CAT.equalsIgnoreCase("IFS")
					||Request_CAT.equalsIgnoreCase("IFP"))&& Request_TYPE.equalsIgnoreCase("AM")) {
				Query = "insert into TFO_DJ_TSLM_LOAN_REFERENCE_DETAILS values('1','"+CounterPartyID+"','"+oLoanID+"','"+sWorkitemID+"','1')";
				log.info("Insert Query  :: " + Query);
				i = formObject.saveDataInDB(Query);
				log.info("Insert Query Result :: " + i);
			}else if((Request_CAT.equalsIgnoreCase("IFA") || Request_CAT.equalsIgnoreCase("IFS") 
					||Request_CAT.equalsIgnoreCase("IFP"))&& Request_TYPE.equalsIgnoreCase("STL")){
				Query = "insert into TFO_DJ_TSLM_LOAN_REFERENCE_DETAILS values('1','"+CounterPartyID+"','"+oLoanID+"','"+sWorkitemID+"','1')";
				i = formObject.saveDataInDB(Query);
				log.info("Insert Query Result :: " + i);
			}else if(Request_CAT.equalsIgnoreCase("IFA") && Request_TYPE.equalsIgnoreCase("IFA_CTP")){
				Query = "insert into TFO_DJ_TSLM_LOAN_REFERENCE_DETAILS values('1','"+CounterPartyID+"','"+oLoanID+"','"+sWorkitemID+"','1')";
				i = formObject.saveDataInDB(Query);
				log.info("Insert Query Result :: " + i);
			}
		}
		log.info("END filldataInLoanRefInitiator ");
	}

	public void createAmendCoreTSLM(){  //BY KISHAN TSLM
		String req_cat = formObject.getValue(REQUEST_CATEGORY).toString();
		if(req_cat.equalsIgnoreCase("IFA") ||req_cat.equalsIgnoreCase("IFP") 
				||req_cat.equalsIgnoreCase("IFS") ||req_cat.equalsIgnoreCase("SCF")){ //ADDED BY SHIVANSHU FOR SCF 207
			log.info("START createAmendCoreTSLM ");
			List<String> list = null;
			String req_type = formObject.getValue(REQUEST_TYPE).toString();
			String processing_system = formObject.getValue(PROCESSING_SYSTEM).toString();
			String key= req_cat + "#" +req_type+ "#"+processing_system;
			try{
				log.info("createAmendCoreMap  ::"+createAmendCoreMap.size());
				if(createAmendCoreMap.containsKey(key)){
					list = createAmendCoreMap.get(key);
					log.info(" createAmendCoreTSLM List"+list.toString());
					log.info(" createAmendCoreTSLM List"+list.size());
					String fieldValue = list.get(0);
					String fieldState = list.get(1);
					formObject.setValue(CREATE_AMEND_CNTRCT_FCUBS, fieldValue);
					if(fieldState.equalsIgnoreCase("P")){
						log.info("Inside fieldState List P");
						formObject.setStyle(CREATE_AMEND_CNTRCT_FCUBS, DISABLE, TRUE);
					}else if(fieldState.equalsIgnoreCase("E")){
						log.info("Inside fieldState List E");
						formObject.setStyle(CREATE_AMEND_CNTRCT_FCUBS, DISABLE, FALSE);
					}
				}
			}catch(Exception e){
				log.error("Exception in createAmendCoreTSLM: ",e);
			}	
			log.info("END createAmendCoreTSLM ");
		}
	}

	public  boolean checkLoanReference(){ //BY KISHAN TSLM
		log.info("INSIDE checkLoanReference");
		int loanCount = getGridCount("TAB_TSLM_LOAN_REF");
		if(loanCount == 0){
			return false;
		}
		int broke = 0;
		for (int i = 0; i < loanCount; i++) {
			log.info("Check for loop : "+i);
			String loanValue = "";
			loanValue = formObject.getTableCellValue("TAB_TSLM_LOAN_REF",i, 2);//loan
			log.info("loanValue :: " + loanValue);
			if (loanValue.equalsIgnoreCase(null) || loanValue.equalsIgnoreCase("")) {
				log.info("Check 44");
				broke = 1;
				break;
			}
		}
		if (broke == 1) {
			log.info("Check 45");
			return false;
		}
		log.info("END checkLoanReference ");
		return true;
	}
	//PM and compliance referral response
	public void loadExcpSaveDataIntoRepeater(String tabName,String referTo,String decision,String excpRemarks){
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
	}
	//PM and compliance referral response
	public String getReferralmailId(String strRefTo){
		log.info("**************Inside getReferralmailId******************");
		try{
			String existingEmail="";
			if ("RM".equalsIgnoreCase(strRefTo)) {
				existingEmail = formObject.getValue("RM_EMAIL").toString();
			} 
			else if ("Customer".equalsIgnoreCase(strRefTo)) {
				if(formObject.getValue(TRADE_CUST_EMAIL_ID).toString().isEmpty()){
					existingEmail = formObject.getValue(FCR_CUST_EMAIL_ID).toString();
				}else{
					existingEmail = formObject.getValue(TRADE_CUST_EMAIL_ID).toString();   
				}
			}
			else
				existingEmail = referralMailMap.get(strRefTo);
			log.info("existingEmail=>"+existingEmail);
			return existingEmail;
		}catch(Exception e){
			log.error("Exception: ",e);
			return "";

		}
	}
	//PM and compliance referral response
	public void setHashMap(){
		try {
			int rowCount=getGridCount(LISTVIEW_FINAL_DECISION);
			String sKey ="",sValue=""; 
			log.info("final decision frame count : "+rowCount );
			if(rowCount>0){
				log.info("finalDecisionList =1" );
				for(int cntr=0; cntr<=rowCount;cntr++){
					log.info("finalDecisionList =2" );
					sKey = formObject.getTableCellValue(LISTVIEW_FINAL_DECISION,cntr,0)+ formObject.getTableCellValue(LISTVIEW_FINAL_DECISION,cntr,1);
					sValue = formObject.getTableCellValue(LISTVIEW_FINAL_DECISION,cntr,5);
					log.info("Key : "+sKey);
					log.info("Value : "+sValue);
					hmapAdditionalMail.put(sKey, sValue);
				}
				log.info(hmapAdditionalMail);
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	}

	/*public void getAPWebserviceForSwiftWI() {
		StringBuilder sInputXML = new StringBuilder();
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<APWebService_Input>").append("\n")
		.append("<Option>WebService</Option>").append("\n")
		.append("<EngineName>" + engineName + "</EngineName>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("<Calltype>WS-2.0</Calltype>").append("\n")
		.append("<InnerCallType>BPMModify</InnerCallType>").append("\n")			
		.append("<wiNumber> </wiNumber>").append("\n")
		.append("<REF_NO>" + "" + "</REF_NO>").append("\n")
		.append("<SENDERID>" + "WMS" + "</SENDERID>").append("\n")
		.append("<mode>"+"C"+"</mode>").append("\n")
		.append("<channelName>"+"SWIFT_WI"+"</channelName>").append("\n")           
		.append("<correlationId>"+"299MSIG20275002H"+"</correlationId>").append("\n")
		.append("<channelRefNumber>"+"299MSIG20275002H"+"</channelRefNumber>").append("\n")  
		.append("<sysrefno>"+"299MSIG20275002H"+"</sysrefno>").append("\n")
		.append("<processName>"+"TFO"+"</processName>").append("\n")
		.append("</APWebService_Input>");		
		//return sInputXML.toString();

		log.info("Input xml:"+sInputXML.toString());
		String sOutput = socket.connectToSocket(sInputXML.toString());
		log.info("Output xml:"+sInputXML.toString());
	}*/
	
	//added by mansi
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
	public String createWorkitem(String txnNumber) throws Exception {
		String sOutput = "";
		sOutput = createWorkitemInputXML(txnNumber);
		sOutput = socket.connectToSocket(sOutput);
		log.info("output="+sOutput);
		return sOutput;
	}

	public void callChildWICreation(String childReqCat, String childReqType){
		String query="";
		List<List<String>> sOutputlist =null;
		String output="";
		String reqCat=childReqCat;
		String reqType=childReqType;
		String processType = formObject.getValue(PROCESS_TYPE).toString();
		ProtradeComplexMapping cmplxMaster;
		try{
			sOutputlist = formObject.getDataFromDB("SELECT SEQ_WEBSERVICE.nextval as IDValue from DUAL");
			String txnNumber= sOutputlist.get(0).get(0);
			StringBuffer strColumns = new StringBuffer();
			StringBuffer strValues = new StringBuffer();
			Map<String,HashMap<String, String>> map = protradeMappingMap.get(reqCat+"#"+reqType+"#"+processType);

			for(Map.Entry<String, HashMap<String, String>> mapEntry: map.entrySet()){
				String key = mapEntry.getKey();
				HashMap<String, String> attributeMap = mapEntry.getValue();
				log.info("attributeMap=" + attributeMap);
				cmplxMaster=protradeComplexMap.get(key);
				log.info("cmplxMaster=" + cmplxMaster);

				StringBuffer strTableColNames= new StringBuffer();

				for( Entry<String, String> mapEntry1: attributeMap.entrySet()){
					strTableColNames.append(mapEntry1.getValue()+",");
				}
				strTableColNames.setLength(strTableColNames.length()-1);
				query ="SELECT "+strTableColNames+" from "+cmplxMaster.getComplexTableName()+" where "+cmplxMaster.getMappingAttribute()+"='"+sWorkitemID+"'";
				log.info("query=" + query);
				sOutputlist = formObject.getDataFromDB(query);
				if("EXTERNAL".equalsIgnoreCase(key)){
					query="insert into "+cmplxMaster.getStagingTableName()+"(";
					strColumns.append("requestmode, channelname,insertiondatetime,version,"
							+ "processname,channelrefnumber,sysrefno,correlationid,wi_name,request_type, "
							+ "request_category,INSERTED_BY");
					strValues.append("'C','LINK_WI',current_date,'1','TFO','"+txnNumber+"','"+txnNumber+"','"+txnNumber+"','"
							+sWorkitemID+"','"+reqType+"','"+reqCat+"','TFO_SYSTEM'");
					int counter=0;
					for( Entry<String, String> mapEntry1: attributeMap.entrySet()){

						String key1 =mapEntry1.getKey();
						log.info("key1=" + key1);
						if(!("REQUEST_CATEGORY".equalsIgnoreCase(key1)||"REQUEST_TYPE".equalsIgnoreCase(key1))){
							strColumns.append(","+key1);
							if(protradeDateMap.containsKey(key1) && protradeDateMap.get(key1).equals("Y")){
								log.info("in protradeDateMap="+sOutputlist.get(0).get(counter));
								strValues.append(",TO_DATE('"+sOutputlist.get(0).get(counter)+"','YYYY-MM-DD hh24:mi:ss')");
							}else if(protradeCLOBMasterMap.containsKey(key1)&&protradeCLOBMasterMap.get(key1).equals("Y")){
								log.info("inc rotradeCLOBMasterMap ="+sOutputlist.get(0).get(counter));
								strValues.append(","+createNormalizedXML(sOutputlist.get(0).get(counter)));
							}else{
								log.info("in else="+sOutputlist.get(0).get(counter));
								strValues.append(",'"+sOutputlist.get(0).get(counter)+"'");
							}
							log.info("strValues="+strValues);
						}
						counter++;
					}
					log.info("strColumns="+strColumns);
					log.info("strValues="+strValues);


					query=query+strColumns.toString()+") values("+strValues.toString()+")";
					log.info("query="+query);
					int value=formObject.saveDataInDB(query);
					log.info(" output value="+value);
				} else{
					
					if (!sOutputlist.isEmpty()) {
						for (int counter = 0; counter < sOutputlist.size(); counter++) {
							strColumns=new StringBuffer();
							strValues=new StringBuffer();
							query="insert into "+cmplxMaster.getStagingTableName()+"(";  
							strColumns.append("CHANNELREFNUMBER,CORRELATIONID,INSERTIONDATETIME,ROWNO");
							strValues.append("'"+txnNumber+"','"+txnNumber+"',CURRENT_TIMESTAMP,'"+(counter+1)+"'");
							int counter1=0;
							for( Entry<String, String> mapEntry1: attributeMap.entrySet()){
								String key1 =mapEntry1.getKey();
								strColumns.append(","+key1);
								if(protradeDateMap.containsKey(key1) && protradeDateMap.get(key1).equals("Y")){
									strValues.append(",TO_DATE('"+sOutputlist.get(counter).get(counter1)+"','YYYY-MM-DD hh24:mi:ss')");
								}else if(protradeCLOBMasterMap.containsKey(key1) && protradeCLOBMasterMap.get(key1).equals("Y")){
									strValues.append(","+createNormalizedXML(sOutputlist.get(counter).get(counter1)));
								}else{
									strValues.append(",'"+sOutputlist.get(counter).get(counter1)+"'");
								}
								log.info("strValues="+strValues);

								counter1++;
							}
							log.info("strColumns="+strColumns);
							log.info("strValues="+strValues);

							query=query+strColumns.toString()+") values("+strValues.toString()+")";
							log.info("query="+query);
							int value=formObject.saveDataInDB(query);
							log.info(" output value="+value);
						}
					} 
				}

			}

			handleCreateWorkitemOutput(createChildWorkitem(txnNumber));
			saveDecHistory("Notification","Child Workitem under claim settlement created successfully!");
		} catch (Exception e) {
			log.error("Exception in",e);
		}

	}
	public String createChildWIInputXML(String sTxn) throws Exception {
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
			.append("<channelName>CHILD_WI</channelName>").append("\n")
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
	
	//Added by Ayush
	public String updateDocumentStatus() throws Exception {
		log.info("Inside updateDocumentStatus");
		String sOutput = "";
		String sysref = "";
		String listView = "QVAR_utc_details";


		StringBuilder inputReqXml=new StringBuilder();
		List<List<String>> refQuery = formObject.getDataFromDB("SELECT SEQ_WEBSERVICE.nextval as IDValue from DUAL ");
		if (!refQuery.isEmpty() && !refQuery.get(0).isEmpty()) {
			sysref = refQuery.get(0).get(0);
		}
		try{
			int noOfInvoice=getGridCount(listView);
			inputReqXml.append("<?xml version=\"1.0\"?>").append("\n")
			.append("<APWebService_Input>").append("\n")
			.append("<Option>WebService</Option>").append("\n")
			.append("<Calltype>WS-2.0</Calltype>").append("\n")
			.append("<InnerCallType>updateDocumentStatus</InnerCallType>").append("\n")
			.append("<REF_NO>" + sysref +"</REF_NO>").append("\n")
			.append("<WiName>" + sWorkitemID +"</WiName>").append("\n").append("<senderID>WMS</senderID>").append("\n")
			.append("<updateDocumentStatusReqMsg>").append("\n")
			.append("<Documents>").append("\n");
			for(int i = 0 ; i< noOfInvoice ; i++){
				inputReqXml.append("<DocumentData>").append("\n")
				.append("<documentNo>"+formObject.getTableCellValue(listView, i, 0).toString().trim()+"</documentNo>").append("\n")
				.append("<UTCRefNo>"+formObject.getTableCellValue(listView, i, 9).toString().trim()+"</UTCRefNo>").append("\n")
				.append("<status>FINANCED</status>").append("\n")
				.append("<reason></reason>").append("\n")
				.append("</DocumentData>").append("\n");
			}
			inputReqXml.append("</Documents>").append("\n")
			.append("</updateDocumentStatusReqMsg>").append("\n")
			.append("</APWebService_Input>");
			log.info("Inside updateDocumentStatus"+inputReqXml);

			sOutput = socket.connectToSocket(inputReqXml.toString());
			log.info("Inside updateDocumentStatus"+sOutput);

		} catch (Exception e) {
			log.error("Exception in",e);
		} 

		return sOutput;

	}
	//Function by Ayush

	
	public String updateBatchProcessStatus() throws Exception {
		log.info("Inside updateBatchProcessStatus");
		String sOutput = "";
		String sysref = "";

		StringBuilder inputReqXml=new StringBuilder();
		List<List<String>> refQuery = formObject.getDataFromDB("SELECT SEQ_WEBSERVICE.nextval as IDValue from DUAL ");
		if (!refQuery.isEmpty() && !refQuery.get(0).isEmpty()) {
			 sysref = refQuery.get(0).get(0);
		}
		try{
			inputReqXml.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<APWebService_Input>").append("\n")
		.append("<Option>WebService</Option>").append("\n")
		.append("<Calltype>WS-2.0</Calltype>").append("\n")
		.append("<InnerCallType>updateDocumentStatus</InnerCallType>").append("\n")
		.append("<REF_NO>" + sysref +"</REF_NO>").append("\n")
		.append("<WiName>" + sWorkitemID +"</WiName>").append("\n").append("<senderID>WMS</senderID>").append("\n")
		.append("<batchNo>BT001</batchNo>").append("\n")
		.append("<mode>RULE</mode>").append("\n")
		.append("<utcRequestId>e2b4aea0-af71-11ec-9bca-af1e5aada7a6</utcRequestId>").append("\n")
		.append("</APWebService_Input>");
			log.info("Inside updateBatchProcessStatus"+inputReqXml);

		sOutput = socket.connectToSocket(inputReqXml.toString());
		log.info("Inside updateBatchProcessStatus"+sOutput);

	} catch (Exception e) {
		log.error("Exception in",e);
	} 
	
		return sOutput;
		
	}
	
	public String createChildWorkitem(String txnNumber) throws Exception {
		String sOutput = "";
		sOutput = createChildWIInputXML(txnNumber);
		sOutput = socket.connectToSocket(sOutput);
		log.info("output="+sOutput);
		return sOutput;
	}
	
	protected void setAmendFrameDataPM() {
		try {
			log.info("setAmendFrameDataPM started");
			String control[] = null;
			//sheenu	
			if("ELC_LCAA".equalsIgnoreCase(requestType) ||"ELC_LCC".equalsIgnoreCase(requestType) ||"ILC_AM".equalsIgnoreCase(requestType)){
				control = AMEND_FRAME_FIELDS.split("#");
				//hideshowFrmInputTab(AMEND_FRAME_FIELDS,true);
			}else if("GRNT".equalsIgnoreCase(requestCategory) && "AM".equalsIgnoreCase(requestType)){
				control = GRNT_AM_AMEND_FRAME_FIELDS.split("#");
				//	hideshowFrmInputTab(GRNT_AM_AMEND_FRAME_FIELDS,true);
			}else if(("ELC".equalsIgnoreCase(requestCategory) && "ELC_SLCAA".equalsIgnoreCase(requestType))){
				control = SBLC_AM_AMEND_FRAME_FIELDS.split("#");
				//hideshowFrmInputTab(SBLC_AM_AMEND_FRAME_FIELDS,true);SBLC_AM_ONLY_AMEND_FRAME_FIELDS
			}
			else if(("SBLC".equalsIgnoreCase(requestCategory) && "SBLC_AM".equalsIgnoreCase(requestType))){
				control = SBLC_AM_ONLY_AMEND_FRAME_FIELDS.split("#");
			}
			else if(("GRNT".equalsIgnoreCase(requestCategory) && "GAA".equalsIgnoreCase(requestType))){
				control = GRNT_GAA_AMEND_FRAME_FIELDS.split("#");
				//hideshowFrmInputTab(GRNT_GAA_AMEND_FRAME_FIELDS,true);
			}
			String FIN_Transaction_amount_data = formObject.getValue("Q_Amendment_Data_FIN_TRANSACTION_AMOUNT").toString();
			//sheenu	if("ELC_LCAA".equalsIgnoreCase(requestType) ||"ELC_LCC".equalsIgnoreCase(requestType) ||"ILC_AM".equalsIgnoreCase(requestType)){
			if("".equalsIgnoreCase(FIN_Transaction_amount_data) ||  null==FIN_Transaction_amount_data){
				for (int counter = 0; counter < control.length; counter++ ) {
					String showValues="LABEL_"+control[counter]+"#"+"Q_Amendment_Data_USER_"+control[counter]+"#"+"Q_Amendment_Data_PT_"+control[counter]
							+"#"+"Q_Amendment_Data_FC_"+control[counter]+"#"+"Q_Amendment_Data_FIN_"+control[counter];
					hideshowFrmInputTab(showValues,true);
					if ("".equalsIgnoreCase(formObject.getValue("Q_Amendment_Data_FIN_"+control[counter]).toString()) || null == formObject.getValue("Q_Amendment_Data_FIN_"+control[counter]).toString()){
						log.info("CONTROL : "+control[counter]);
						log.info("Q_Amendment_Data_USER_"+control[counter] +" -- USER : "+formObject.getValue("Q_Amendment_Data_USER_"+control[counter]).toString());
						log.info("Q_Amendment_Data_PT_"+control[counter] +" -- PT : "+formObject.getValue("Q_Amendment_Data_PT_"+control[counter]).toString());
						log.info("Q_Amendment_Data_FC_"+control[counter] +" -- FC : "+formObject.getValue("Q_Amendment_Data_FC_"+control[counter]).toString());
						if (!"".equalsIgnoreCase(formObject.getValue("Q_Amendment_Data_USER_"+control[counter]).toString()) && null != (formObject.getValue("Q_Amendment_Data_USER_"+control[counter]).toString()) ){
							log.info("1"+control[counter]);
							formObject.setValue("Q_Amendment_Data_FIN_"+control[counter],formObject.getValue("Q_Amendment_Data_USER_"+control[counter]).toString());
						} else if (!"".equalsIgnoreCase(formObject.getValue("Q_Amendment_Data_PT_"+control[counter]).toString()) && null != (formObject.getValue("Q_Amendment_Data_PT_"+control[counter]).toString()) ){
							log.info("2"+control[counter]);
							formObject.setValue("Q_Amendment_Data_FIN_"+control[counter],formObject.getValue("Q_Amendment_Data_PT_"+control[counter]).toString());

							if("TRANSACTION_AMOUNT".equalsIgnoreCase(control[counter])||"EXPIRY_DATE".equalsIgnoreCase(control[counter])){
								formObject.setValue("Q_Amendment_Data_USER_"+control[counter],formObject.getValue("Q_Amendment_Data_PT_"+control[counter]).toString());
							}

						} else if (!"".equalsIgnoreCase(formObject.getValue("Q_Amendment_Data_FC_"+control[counter]).toString()) && null != (formObject.getValue("Q_Amendment_Data_FC_"+control[counter]).toString()) ){
							log.info("3"+control[counter]);
							formObject.setValue("Q_Amendment_Data_FIN_"+control[counter],formObject.getValue("Q_Amendment_Data_FC_"+control[counter]).toString());
						}
						//PRODTRADE_3_119  
						if("TRANSACTION_AMOUNT".equalsIgnoreCase(control[counter])){
							formObject.setValue(NEW_TRN_AMT,formObject.getValue("Q_Amendment_Data_FIN_"+control[counter]).toString());
						}else if("EXPIRY_DATE".equalsIgnoreCase(control[counter])){
							formObject.setValue(NEW_EXP_DATE,formObject.getValue("Q_Amendment_Data_FIN_"+control[counter]).toString());
						}
						//PRODTRADE_3_119 
					}
				}
				//Swift condition
			}else{ //sheenu
				for (int counter = 0; counter < control.length; counter++ ) {
					String showValues = "LABEL_"+control[counter]+"#"+"Q_Amendment_Data_USER_"+control[counter]+"#"+"Q_Amendment_Data_PT_"+control[counter]
							+"#"+"Q_Amendment_Data_FC_"+control[counter]+"#"+"Q_Amendment_Data_FIN_"+control[counter];						 
					hideshowFrmInputTab(showValues,true);
				}
			}
			//sheenu}
			log.info("setAmendFrameDataPM ends");
		} catch (Exception e) {
			log.info("exception in saveDecHistory: " + e, e);
		}


	}
	
	public boolean validateMandatoryFieldsPMPC(String sFieldName, String alertMsg) {
		try {
			log.info("Validation Conrol Name :"+sFieldName);
			String fieldValue = normalizeString(this.formObject.getValue(sFieldName).toString());
			log.info("fieldValue --validateMandatoryFieldsPPM  " + fieldValue);
			if (!(isEmpty(fieldValue)) || "".equalsIgnoreCase(fieldValue) || emptyStr.equalsIgnoreCase(fieldValue)) {
				log.info("validateMandatoryFieldsPPM blank");
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
	protected boolean validateMandatoryExpiryCond_PMPC()  
	{
		String REQUEST_TYPE=formObject.getValue("REQUEST_TYPE").toString();
		
		if(REQUEST_TYPE.equalsIgnoreCase("AM")|| REQUEST_TYPE.equalsIgnoreCase("SBLC_AM")
				|| REQUEST_TYPE.equalsIgnoreCase("GAA") || REQUEST_TYPE.equalsIgnoreCase("ELC_SLCAA"))
		{
		if("COND".equalsIgnoreCase(formObject.getValue(Q_AMENDMENT_DATA_USER_EXPIRY_TYPE).toString()))
		{
			return validateMandatoryFieldsPMPC(Q_AMENDMENT_DATA_USER_EXPIRY_COND, "Please Enter Amendment Expiry Conditions.");
		}
		
		else if("FD".equalsIgnoreCase(formObject.getValue(Q_AMENDMENT_DATA_USER_EXPIRY_TYPE).toString()))
		{
			return validateMandatoryFieldsPMPC(Q_AMENDMENT_DATA_USER_EXPIRY_DATE, "Please Enter Amendment Expiry Date.");

		}
		}
		else if(REQUEST_TYPE.equalsIgnoreCase("SBLC_AM") || REQUEST_TYPE.equalsIgnoreCase("GAA") || REQUEST_TYPE.equalsIgnoreCase("ELC_SLCAA"))
		{
			if("COND".equalsIgnoreCase(formObject.getValue(Q_AMENDMENT_DATA_USER_EXPIRY_TYPE).toString()))
		{
			return validateMandatoryFieldsPMPC(Q_AMENDMENT_DATA_USER_EXPIRY_COND, "Please Enter Amendment Expiry Conditions.");
		}
		}
		return true;
	}
	
	public void showUTCDetails() {
		try {
			String utc_required_brms;
			double amount;
			log.info("***************Inside showUTCDetails******************");
			String selectScript = "SELECT UTC_REQUIRED_BRMS,UTC_CONVERTED_AMOUNT FROM EXT_TFO  WHERE WI_NAME = '" + sWorkitemID + "'";
			List<List<String>> sOutputlist = formObject.getDataFromDB(selectScript);
			if (sOutputlist != null && !sOutputlist.isEmpty()
					&& sOutputlist.get(0).size() > 0) {
				utc_required_brms = sOutputlist.get(0).get(0);
		//		amount = Double.parseDouble(sOutputlist.get(0).get(1)); 		// feature/stlam reyaz 26-07-2024 start
				log.info("[utc_required_brms ]"+utc_required_brms);
		//		log.info("[amount]"+ amount);		// feature/stlam reyaz 26-07-2024 start
				formObject.addItemInCombo("UTC_REQUIRED_BRMS",utc_required_brms);
				formObject.setValue("UTC_REQUIRED_BRMS", utc_required_brms);
				formObject.setValue("UTC_CONVERTED_AMOUNT", sOutputlist.get(0).get(1));
			}
			log.info("[query for showUTCDetails ]"+selectScript);
			log.info("Data Inserted");
		}catch(Exception e) {
			log.error("Exception: ",e);
		}

	}
	
	public void SetUTCFCUBProcessValues()
	{
		try{
			log.info("=======SetUTCFCUBProcessValues=========");
			String reqType = formObject.getValue(REQUEST_TYPE).toString();
			String	reqCat = formObject.getValue(REQUEST_CATEGORY).toString();
			List<List<String>> resultSet = null;
			List<List<String>> sOutput;
			int count =0;
			String sQuery ="select count(1) from TFO_DJ_CPD_DETAILS where wi_name = '"+this.sWorkitemID+"'";
			log.info("sQuery is :******"+sQuery);
			sOutput = formObject.getDataFromDB(sQuery);
			log.info("sOutput is :******"+sOutput);
			if(sOutput != null && sOutput.size()>0){
				count = Integer.parseInt(sOutput.get(0).get(0));
				log.info("count is :"+count);
				String query ="select CP_NAME,CP_COUNTRY from TFO_DJ_CPD_DETAILS where wi_name = '"+this.sWorkitemID+"'";
				log.info("sQuery is :******"+query);
				resultSet = formObject.getDataFromDB(query);
				log.info("SetUTCFCUBProcessValues===Resultset: "+resultSet);
				if("IFS".equalsIgnoreCase(requestCategory)){
					if(count == 1){
						String counterFCUBName = resultSet.get(0).get(0);
						log.info("counterTslmName= "+counterFCUBName);
						formObject.addItemInCombo("Buyer_Name",counterFCUBName);
						formObject.setValue("Buyer_Name",counterFCUBName);
					}
					else if(count > 1){
						for(int i = 0 ; i< count ; i++) {
							String custName = resultSet.get(i).get(0);
							String country = resultSet.get(i).get(1);
							String shortName = country +"-" + custName;
							log.info("shortName= "+shortName);
							formObject.addItemInCombo("Buyer_Name", shortName);
							formObject.setValue("Buyer_Name",shortName);

						}
					}
				}else if("IFP".equalsIgnoreCase(requestCategory)|| "IFA".equalsIgnoreCase(requestCategory)
						|| "SCF".equalsIgnoreCase(requestCategory)){  //ADDED FOR SCF
					if(count == 1){
						String counterTslmName = resultSet.get(0).get(0);
						log.info("counterTslmName= "+counterTslmName);
						formObject.addItemInCombo("Supplier_Name",counterTslmName);
						formObject.setValue("Supplier_Name",counterTslmName);
					}
					else if(count > 1){
						for(int cnt = 0 ; cnt< count ; cnt++) {
							String custName = resultSet.get(cnt).get(0);
							String country = resultSet.get(cnt).get(1);
							String shortName = country+"-" +  custName;
							formObject.addItemInCombo("Supplier_Name", shortName);
							formObject.setValue("Supplier_Name",shortName);

						}

					}
				}
			}
		}catch (Exception e) {
			log.error("Exception: ",e);
		}
	}
	public void SetUTCTSLMProcessValues()
	{
		try{
			log.info("=======SetUTCTSLMProcessValues=========");
			String reqType = formObject.getValue(REQUEST_TYPE).toString();
			String	reqCat = formObject.getValue(REQUEST_CATEGORY).toString();
			List<List<String>> resultSet = null;
			List<List<String>> sOutput;
			int count =0;
			String sQuery ="select count(1) from TFO_DJ_TSLM_COUNTER_PARTY_DETAILS where winame = '"+this.sWorkitemID+"' and CHECKBOX = '"+true+"'";
			log.info("sQuery is :******"+sQuery);
			sOutput = formObject.getDataFromDB(sQuery);
			log.info("sOutput is :******"+sOutput);
			if(sOutput != null && sOutput.size()>0){
				count = Integer.parseInt(sOutput.get(0).get(0));
				log.info("count is :"+count);
//				String query ="select PARTY_NAME,COUNTRY from TFO_DJ_TSLM_COUNTER_PARTY_DETAILS where winame = '"+this.sWorkitemID+"'";
				String query ="select CID,PARTY_NAME from TFO_DJ_TSLM_COUNTER_PARTY_DETAILS where winame ='"+this.sWorkitemID+"' and CHECKBOX = '"+true+"'";
				log.info("sQuery is :******"+query);
				resultSet = formObject.getDataFromDB(query);
				log.info("SetUTCTSLMProcessValues===Rseultset "+resultSet);

				if("IFS".equalsIgnoreCase(requestCategory)){
					if(count == 1){
						String counterTslmName = resultSet.get(0).get(1);
						String sCID = resultSet.get(0).get(0);
						String shortName = sCID +"-" +  counterTslmName;
						log.info("shortName= "+shortName);
						formObject.addItemInCombo("Buyer_Name",shortName);
						formObject.setValue("Buyer_Name",shortName);
					}
					else if(count > 1){
						for(int j = 0 ; j< count ; j++) {
							String sCID = resultSet.get(j).get(0);
							String sPartyName = resultSet.get(j).get(1);
							String shortName = sCID +"-" +  sPartyName;
							formObject.addItemInCombo("Buyer_Name", shortName);
							formObject.setValue("Buyer_Name",shortName);

						}
					}
				} else if("IFP".equalsIgnoreCase(requestCategory)|| "IFA".equalsIgnoreCase(requestCategory)
						|| "SCF".equalsIgnoreCase(requestCategory)){ //ADDED BYH SHIVANSHU FOR SCF 254
					if(count == 1){
						String counterTslmName = resultSet.get(0).get(1);
						String sCID = resultSet.get(0).get(0);
						String shortName = sCID +"-" +  counterTslmName;
						log.info("shortName= "+shortName);
						formObject.addItemInCombo("Supplier_Name",shortName);
						formObject.setValue("Supplier_Name",shortName);
					}
					else if(count > 1){
						for(int z = 0 ; z< count ; z++) {
							String sCID = resultSet.get(z).get(0);
							String sPartyName = resultSet.get(z).get(1);
							String shortName = sCID +"-" +  sPartyName;
							formObject.addItemInCombo("Supplier_Name", shortName);
							formObject.setValue("Supplier_Name",shortName);
						}
					}
				}
			}
		}catch (Exception e) {
			log.error("Exception: ",e);
		}
	}
	protected boolean validateInvoiceDetailsTab(){
		try { 
			requestType = formObject.getValue(REQUEST_TYPE).toString();
			requestCategory = formObject.getValue(REQUEST_CATEGORY).toString();
			log.info("REQTYPE: "+ requestType +" sActivityName: "+sActivityName+" requestCategory: "+ requestCategory);
			log.info("Inside validateInvoiceDetailsTab");

			String name = formObject.getValue(CUSTOMER_NAME).toString();
			String currency = formObject.getValue(TRANSACTION_CURRENCY).toString();
			String amount = formObject.getValue(TRANSACTION_AMOUNT).toString();
			String processingSystem = formObject.getValue(PROCESSING_SYSTEM).toString();
			log.info("validateInvoiceDetailsTab"+getGridCount("QVAR_utc_details"));
			log.info("validateInvoiceDetailsTab amount:"+amount);
			log.info("validateInvoiceDetailsTab currency:"+currency);
			formObject.addItemInCombo("Invoice_Currency",currency);
			if(formObject.getValue("Invoice_Currency").toString().equalsIgnoreCase("")){
				formObject.setValue("Invoice_Currency",currency);
			}
			if(formObject.getValue("Invoice_Amount").toString().equalsIgnoreCase("")){
				formObject.setValue("Invoice_Amount",amount);
			}
			
			formObject.setValue("Batch_No",sWorkitemID);
			String hiddenFlag = formObject.getValue("HIDDEN_START_FLAG").toString(); 
			log.info("validateInvoiceDetailsTab : hiddenFlag="+hiddenFlag);
//			if("true".equalsIgnoreCase(hiddenFlag)){
//				int i=1;
//				log.info("workitem Id" + sWorkitemID +"+_i+");
//				formObject.setValue("Batch_No",sWorkitemID+ "_" +i);
//				log.info("workitem HIDDEN_START_FLAG" + "HIDDEN_START_FLAG");
//				enableFieldOnDemand("UTC_START_CHECK");
//				formObject.setValue("HIDDEN_START_FLAG","");
//			}


			if("IFS".equalsIgnoreCase(requestCategory)){
				log.info("validateInvoiceDetailsTab : requestCategory="+requestCategory);
				log.info("validateInvoiceDetailsTab : name="+name);
				formObject.addItemInCombo("Supplier_Name", name);
				formObject.setValue("Supplier_Name",name);
				log.info("validateInvoiceDetailsTab : processingSystem="+processingSystem);
				if("T".equalsIgnoreCase(processingSystem)){
					log.info("validateInvoiceDetailsTab : processingSystem="+processingSystem);
					SetUTCTSLMProcessValues();

				} else if("F".equalsIgnoreCase(processingSystem)){
					log.info("validateInvoiceDetailsTab : processingSystem="+processingSystem);
					SetUTCFCUBProcessValues();
				}
			}
			if("IFP".equalsIgnoreCase(requestCategory)|| "IFA".equalsIgnoreCase(requestCategory) || "SCF".equalsIgnoreCase(requestCategory)){//ADDED BY SHIVANSHU FOR SCF ATP-207
				log.info("validateInvoiceDetailsTab : requestCategory="+requestCategory);
				formObject.addItemInCombo("Buyer_Name",name);
				formObject.setValue("Buyer_Name",name);
				if("T".equalsIgnoreCase(processingSystem)){
					log.info("validateInvoiceDetailsTab : processingSystem="+processingSystem);
					SetUTCTSLMProcessValues();
				} else if("F".equalsIgnoreCase(processingSystem)){
					log.info("validateInvoiceDetailsTab : processingSystem="+processingSystem);
					SetUTCFCUBProcessValues();
				}

			}


		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		return true;
	}
	public boolean mandatoryUTCValidationPM(){
		try{
			int count = getGridCount("QVAR_utc_details");
			if (count == 0){
				sendMessageHashMap("QVAR_utc_details", "Please Add data in Invoice Details Grid");
				return false;
			}

		}catch(Exception e){
			log.error("Exception: ",e);
		}
		return true;
	}
	public String submitInvoiceDetailInputXML()    
	{
		StringBuilder inputXml=new StringBuilder();
		String sysref="";
		//String inv_no="",inv_date="",inv_currency="",inv_amt="",bsName="",custBSName="",inv_count="";

		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		log.info("submitInvoiceDetailInputXML Inside.");
		String listView = "QVAR_utc_details";
		int noOfInvoice=getGridCount(listView);
		String custName=formObject.getValue("CUSTOMER_NAME").toString();
		String BorS = "";
		log.info("Get value custName::::  " + custName); 
		String reqCat=formObject.getValue("REQUEST_CATEGORY").toString();
		if(reqCat.equalsIgnoreCase("IFP")||reqCat.equalsIgnoreCase("IFA")
				||reqCat.equalsIgnoreCase("SCF")) //ADDED FOR SCF
			BorS = "BUYER";
		else
			BorS = "SUPPLIER";
		log.info("Get value BorS::::  " + BorS); 

		List<List<String>> refQuery = formObject.getDataFromDB("SELECT SEQ_WEBSERVICE.nextval as IDValue from DUAL ");
		if (!refQuery.isEmpty() && !refQuery.get(0).isEmpty()) {
			sysref = refQuery.get(0).get(0);
		}


		log.info("Start InputXML create"); 
		try 
		{	

			inputXml.append("<APWebService_Input>")
			.append("\n").append("<Option>WebService</Option>").append("\n")
			.append("<Calltype>WS-2.0</Calltype>").append("\n")
			.append("<InnerCallType>SubmitInvoiceValidation</InnerCallType>").append("\n")
			.append("<submitInvoiceDtlsReqMsg>").append("\n")
			.append("<usecaseID></usecaseID>").append("\n")
			.append("<serviceName>ModInvoiceValidation</serviceName>").append("\n")
			.append("<versionNo>1.0</versionNo>").append("\n")
			.append("<serviceAction>Modify</serviceAction>").append("\n")
			.append("<correlationID>" + sysref +"</correlationID>").append("\n")
			.append("<sysRefNumber>" + sysref +"</sysRefNumber>").append("\n")
			.append("<WiName>" + sWorkitemID +"</WiName>")
			.append("<senderID>WMS</senderID>").append("\n")
			.append("<consumer>ESB</consumer>").append("\n")
			.append("<reqTimeStamp>" +sDate+"</reqTimeStamp>").append("\n")
			.append("<repTimeStamp>" +sDate+"</repTimeStamp>").append("\n")
			.append("<username></username>").append("\n")
			.append("<credentials></credentials>").append("\n")
			.append("<returnCode></returnCode>").append("\n")
			.append("<errorDescription></errorDescription>").append("\n")
			.append("<errorDetail></errorDetail>").append("\n")
			.append("<submitInvoiceDtlsReq>").append("\n")
			.append("<batchNo>"+sWorkitemID+"</batchNo>")
			.append("<documentCount>"+noOfInvoice+"</documentCount>").append("\n")
			.append("<documentType>INVOICE</documentType>").append("\n")
			.append("<documentSubType>INVOICE</documentSubType>").append("\n")
			.append("<override>N</override>").append("\n")
			.append("<uploadType>API</uploadType>").append("\n")
			.append("<bankRefNo>").append("\n")			
			.append("<bankRefNumbers></bankRefNumbers>").append("\n")
			.append("</bankRefNo>").append("\n")
			.append("<customerDtls>").append("\n")
			.append("<customerName>"+custName.replaceAll("\\p{Pd}", "-")+"</customerName>").append("\n")			
			.append("<taxNo></taxNo>")
			.append("<buyerOrSupplier>"+BorS+"</buyerOrSupplier>").append("\n")
			.append("<tradeLicenseNo></tradeLicenseNo>").append("\n")
			.append("</customerDtls>").append("\n")
			.append("<electronicDatas>").append("\n");
			for(int i = 0 ; i< noOfInvoice ; i++)
			{
				String utcRef = formObject.getTableCellValue(listView,i,9).toString();
				log.info("utc REFERENCE:: "+utcRef);
				if("".equalsIgnoreCase(utcRef))
				{
					dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					/*Date d2=dateFormat2.parse(formObject.getTableCellValue(listView,i,1).toString());
					sDate = dateFormat.format(d2);*/
					
				inputXml.append("<electronicData>").append("\n")			
				.append("<documentNo>"+formObject.getTableCellValue(listView,i,0).toString().trim()+ "</documentNo>").append("\n")
				.append("<documentDate>"+formObject.getTableCellValue(listView,i,1).toString().trim()+"</documentDate>").append("\n")
				.append("<currency>"+formObject.getTableCellValue(listView,i,2).toString().trim()+"</currency>").append("\n")
				.append("<totalInvoiceAmount>"+formObject.getTableCellValue(listView,i,3).replace(",", "").toString().trim()+"</totalInvoiceAmount>").append("\n")	
				.append("<contractNo><contractNo>").append("\n")
				.append("<poNumber></poNumber>").append("\n")
				.append("<amountInWords></amountInWords>").append("\n")
				.append("<paymentDueDate></paymentDueDate>").append("\n")
				.append("<termsOfPayment></termsOfPayment>").append("\n")
				.append("<billingAddress></billingAddress>").append("\n")
				.append("<discount>0</discount>").append("\n")
				.append("<taxAmount>0</taxAmount>").append("\n")			
				.append("<taxNoSupplier></taxNoSupplier>").append("\n")
				.append("<grossAmount>0</grossAmount>").append("\n")
				.append("<supplierDtls>").append("\n");
				if(formObject.getTableCellValue(listView,i,4).length() > 0)
				{
					String supplierName =formObject.getTableCellValue(listView,i,4).toString();
					if(supplierName.contains("-")&& ("IFP".equalsIgnoreCase(requestCategory) || "IFA".equalsIgnoreCase(requestCategory)
							||"SCF".equalsIgnoreCase(requestCategory))){ //ATP -462 REYAZ 15-05-2024 -  //ATP-473 Shahbaz 28-05-2024 
						supplierName = supplierName.split("-")[1];
						
					}
					log.info("listView,i,4 in supplier if : "+formObject.getTableCellValue(listView,i,4).length());
					inputXml.append("<SupplierName>"+supplierName+"</SupplierName>").append("\n");		
				}
				else
				{
					log.info("listView,i,4 in supplier else");
					inputXml.append("<SupplierName></SupplierName>").append("\n");
				}
				inputXml.append("<SupplierAccountNo></SupplierAccountNo>")
				.append("<SupplierEmailAddress></SupplierEmailAddress>").append("\n")
				.append("<SupplierWebsite></SupplierWebsite>").append("\n")
				.append("<SupplierTelephone></SupplierTelephone>").append("\n")
				.append("<SupplierTrn></SupplierTrn>").append("\n")
				.append("<addressDtls>").append("\n")			
				.append("<SupplierLine1></SupplierLine1>").append("\n")
				.append("<SupplierLine2></SupplierLine2>").append("\n")
				.append("<SupplierCity></SupplierCity>").append("\n")
				.append("<SupplierCountry></SupplierCountry>").append("\n")			
				.append("<SupplierPoBox></SupplierPoBox>")	
				.append("</addressDtls>").append("\n")
				.append("</supplierDtls>").append("\n")
				.append("<buyerDtls>").append("\n");
				if(formObject.getTableCellValue(listView,i,5).length() > 0)
				{
					String buyerName =formObject.getTableCellValue(listView,i,5).toString();
					if(buyerName.contains("-") && ("IFS".equalsIgnoreCase(requestCategory))){
						buyerName = buyerName.split("-")[1];
						
					}
					log.info("listView,i,5 in buyer if : "+formObject.getTableCellValue(listView,i,5).length());
					inputXml.append("<BuyerName>"+buyerName+"</BuyerName>").append("\n");		
				}
				else
				{
					log.info("listView,i,4 in buyer else");
					inputXml.append("<BuyerName></BuyerName>").append("\n");
				}
				inputXml.append("<BuyerEmailAddress></BuyerEmailAddress>").append("\n")
				.append("<BuyerWebsite></BuyerWebsite>").append("\n")
				.append("<BuyerTelephone></BuyerTelephone>").append("\n")
				.append("<BuyerTrn></BuyerTrn>").append("\n")
				.append("<addressDtls>").append("\n")			
				.append("<BuyerLine1></BuyerLine1>").append("\n")
				.append("<BuyerLine2></BuyerLine2>").append("\n")
				.append("<BuyerCity></BuyerCity>").append("\n")
				.append("<BuyerCountry></BuyerCountry>").append("\n")			
				.append("<BuyerPoBox></BuyerPoBox>")
				.append("</addressDtls>").append("\n")
				.append("</buyerDtls>").append("\n")
				.append("<productLines>").append("\n")
				.append("<hsCode></hsCode>").append("\n")
				.append("<lineItemDescription></lineItemDescription>").append("\n")
				.append("<unitPrice>0</unitPrice>").append("\n")			
				.append("<subTotalAmount>0</subTotalAmount>").append("\n")
				.append("<quantity>0</quantity>").append("\n")
				.append("<lineNo></lineNo>").append("\n")
				.append("<uom></uom>").append("\n")	
				.append("</productLines>").append("\n")
				.append("</electronicData>").append("\n");
			}
			}
			inputXml.append("</electronicDatas>").append("\n")			
			.append("</submitInvoiceDtlsReq>").append("\n")
			.append("</submitInvoiceDtlsReqMsg>").append("\n")
			.append("</APWebService_Input>");
			log.info("input xml of submitInvoiceDetailInputXML Finalllll = "+ inputXml.toString());



		} 
		catch (Exception e) {
			log.error("Exception in",e);
		}

		return inputXml.toString();



}
 	public String submitInvoiceDetailOutputXML() {
	String sOutput = "";
	@SuppressWarnings("unchecked")
	List<List<String>> sOutputlist = formObject.getDataFromDB("SELECT SEQ_WEBSERVICE.nextval as IDValue from DUAL");
	if (!sOutputlist.isEmpty() && !sOutputlist.get(0).isEmpty()) {
		sOutput = submitInvoiceDetailInputXML();
		sOutput = socket.connectToSocket(sOutput);
	}
	return sOutput;
}
 	
 	public String submitInvoiceDetail(){

 		log.info("inside  submitInvoiceDetail ");
 		String sOperationName = "submitInvoiceDetail";
 		try{

 			String sOutputXML = submitInvoiceDetailOutputXML();
 			log.info(" sOutputXML ::"+ sOutputXML);
 			XMLParser xmlparser = new XMLParser(sOutputXML);
 			log.info(" sOutputXML ::"+ sOutputXML);
 			String smessageStatus = xmlparser.getValueOf("messageStatus");
 			String serrorDescripition  = xmlparser.getValueOf("errorDescripition");
 			String sUTCMessageId  = xmlparser.getValueOf("utcMessageId");
 			String sReturnCode = xmlparser.getValueOf("returnCode");
 			log.info("submitInvoiceDetail smessageStatus	"+smessageStatus);
 			if(smessageStatus.equalsIgnoreCase("OK")){
 				log.info("submitInvoiceDetail inside smessageStatus	"+smessageStatus);
 				disableFieldOnDemand("UTC_START_CHECK");
 				formObject.setValue("HIDDEN_START_FLAG","true");
 				updateHiddenFlag();
 				updateGetDocument();
 				formObject.setStyle("UTC_REQUIRED", DISABLE, TRUE);
 				log.info("flag"+formObject.getValue("HIDDEN_START_FLAG"));
 				log.info("submitInvoiceDetail sReturnCode	"+sReturnCode);
 				String listView = "QVAR_utc_details";
 				int end = 0;
 				int start = xmlparser.getStartIndex("submitInvoiceDtlsRes", 0, 0);
 				int deadEnd = xmlparser.getEndIndex("submitInvoiceDtlsRes", start, 0);
 				int count = xmlparser.getNoOfFields("utcRefList", start,deadEnd);
 				int noOfInvoice=getGridCount(listView);
 				Map<String, String> submitInvoiceMap = new HashMap<>();
 				for(int i=0;i<count;i++) {
 					start = xmlparser.getStartIndex("utcRefList", end, 0);
 					end = xmlparser.getEndIndex("utcRefList", start, 0);
 					String responseDocumentNo  = xmlparser.getValueOf("documentNo", start, end);
 					String sUtcRefNo  = xmlparser.getValueOf("utcRefNo", start, end);
 					submitInvoiceMap.put(responseDocumentNo, sUtcRefNo);
 					log.info("submitInvoiceDetail sUtcRefNo	"+sUtcRefNo);
 					log.info("submitInvoiceDetail responseDocumentNo	"+responseDocumentNo);
 				}
 				for(int j=0;j<noOfInvoice;j++) {
 					String gridDocumentNo = formObject.getTableCellValue(listView,j,0);
 					log.info("submitInvoiceDetail gridDocumentNo	"+gridDocumentNo);
 					if(submitInvoiceMap.containsKey(gridDocumentNo)){
 						log.info("submitInvoiceDetail value	"+submitInvoiceMap.get(gridDocumentNo));
 						formObject.setTableCellValue(listView, j, 7, submitInvoiceMap.get(gridDocumentNo));
 					}
 					if("".equalsIgnoreCase(formObject.getTableCellValue(listView, j, 7).toString())){
 						log.info("submitInvoiceDetail inside null");
 						enableFieldOnDemand("UTC_START_CHECK");
 		 				formObject.setValue("HIDDEN_START_FLAG","");
 		 				
 					}
 				}
 				
 			} 
 			saveUTCSubmitInvoiceDecHistory(smessageStatus);
 		}
 		catch(Exception e){
 			log.error("Exception in submitInvoiceDetail : ",e);
 		}
 		return sOperationName;
}
	private String getInvoiceDtlsXML( ) {
	
	{  
		   String batchNo =(String) formObject.getValue("");
	
	     log.info("getInvoiceDtlsXML >>>");
	     return "<?xml version=\"1.0\"?><APWebService_Input><Option>WebService</Option><Calltype></Calltype>"     // (call type)//
	                        + "<batchNo>"+ batchNo+ "</batchNo>";
	}	  
}
	public String getInvoiceDtlsXMLInvoiceDtlsRes() {
		String sOutput = "";
		@SuppressWarnings("unchecked")
		List<List<String>> sOutputlist = formObject.getDataFromDB("SELECT SEQ_WEBSERVICE.nextval as IDValue from DUAL");
		if (!sOutputlist.isEmpty() && !sOutputlist.get(0).isEmpty()) {
			sOutput = getInvoiceDtlsXML();
			sOutput = socket.connectToSocket(sOutput);
		}
		return sOutput;
		
	}
//	public void saveUTCJustification(){
//		try{
//			log.info("inside  saveUTCJustification ");
//			String groupname = "";
//			String personalName = "";
//			String sActivityName = "";
//			String decHist = "TFO_DJ_DEC_HIST";
//			String decHistCol = "WI_NAME,USER_ID,PRV_WS_NAME,CURR_WS_NAME,CREATE_DATE,USERNAME,USER_GROUPNAME,ACTION,REASON_FOR_ACTION,TRANSACTION_DATE_TIME,REMARKS,BRANCH,DUP_CHECK,ROUTE_TO,SEND_EMAIL_FLAG";
//			String decJstifyQuery = "";
//			String justifyValue ="";
//			String sQuery = "SELECT PERSONALNAME FROM PDBUSER WHERE UPPER(USERNAME)='"+ sUserName.toUpperCase() + "'";
//			List<List<String>> sOutputlist = formObject.getDataFromDB(sQuery);
//			log.info("inside  sOutputlist "+sOutputlist);
//			if (sOutputlist != null && !sOutputlist.isEmpty()
//					&& sOutputlist.get(0).size() > 0) {
//				personalName = sOutputlist.get(0).get(0);
//				log.info("inside  personalName "+personalName);
//			}
//			String sQuery1 = "SELECT utc_jstification_required FROM EXT_TFO WHERE WI_NAME='"+ sWorkitemID + "'";
//			List<List<String>> sOutputlist1 = formObject.getDataFromDB(sQuery1);
//			log.info("inside  sOutputlist1 "+sOutputlist1);
//			if (sOutputlist1 != null && !sOutputlist1.isEmpty()
//					&& sOutputlist1.get(0).size() > 0) {
//				justifyValue = sOutputlist1.get(0).get(0);
//				log.info("inside  justifyValue "+justifyValue);
//			}
//			decJstifyQuery = "INSERT INTO " + decHist + "( " + decHistCol
//					+ " ) VALUES ('" + sWorkitemID + "','"
//					+ this.sUserName.toUpperCase() + "','"
//					+ formObject.getValue("PREV_WS") + "','"
//					+ sActivityName + "',sysdate,'" + personalName
//					+ "','" + groupname + "',' " + "UTC Justification"
//					+"','',sysdate,'UTC Justification: " + justifyValue + "','',' ',' ','')";
//
//		
//			log.info("Decision decBRMSQuery" + decJstifyQuery);
//			formObject.saveDataInDB(decJstifyQuery);
//			
//		}catch(Exception e){
//			log.error("Exception in  saveUTCJustification: ",e);
//		}
//	}
	public void saveUTCDecision(String utcEligible,double converted_amt,String errorDescription){
		try{
		log.info("inside  getInvoiceDtls ");
		String groupname = "";
		String personalName = "";
		String decHist = "TFO_DJ_DEC_HIST";
		String decHistCol = "WI_NAME,USER_ID,PRV_WS_NAME,CURR_WS_NAME,CREATE_DATE,USERNAME,USER_GROUPNAME,ACTION,REASON_FOR_ACTION,TRANSACTION_DATE_TIME,REMARKS,BRANCH,DUP_CHECK,ROUTE_TO,SEND_EMAIL_FLAG";
		String decBRMSQuery = "";
		String decUTCAmtQuery = "";
		String sQuery = "SELECT PERSONALNAME FROM PDBUSER WHERE UPPER(USERNAME)='"+ sUserName.toUpperCase() + "'";
		List<List<String>> sOutputlist = formObject.getDataFromDB(sQuery);
		log.info("inside  sOutputlist "+sOutputlist);
		if (sOutputlist != null && !sOutputlist.isEmpty()
				&& sOutputlist.get(0).size() > 0) {
			personalName = sOutputlist.get(0).get(0);
			log.info("inside  personalName "+personalName);
		}
		decBRMSQuery = "INSERT INTO " + decHist + "( " + decHistCol
				+ " ) VALUES ('" + sWorkitemID + "','"
				+ this.sUserName.toUpperCase() + "','"
				+ sActivityName + "','"
				+ sActivityName + "',sysdate,'" + personalName
				+ "','" + groupname + "','BRMS Call Executed: " + errorDescription+ "',"
				+ "'',sysdate,'UTC Required BRMS " + utcEligible + "','',' ',' ','')";

		decUTCAmtQuery = "INSERT INTO " + decHist + "( " + decHistCol
				+ " ) VALUES ('" + sWorkitemID + "','"
				+ this.sUserName.toUpperCase() + "','"
				+ sActivityName + "','"
				+ sActivityName + "',sysdate,'" + personalName
				+ "','" + groupname + "','" + " UTC Convertion "
				+ "','',sysdate,'UTC Converted amount" + converted_amt + "','',' ',' ','')";
		
		log.info("Decision decBRMSQuery" + decBRMSQuery);
		formObject.saveDataInDB(decBRMSQuery);
		log.info("Decision decUTCAmtQuery" + decUTCAmtQuery);
		formObject.saveDataInDB(decUTCAmtQuery);
		}catch(Exception e){
			log.error("Exception in  saveUTCJustification: ",e);
		}

	}

	public String getInvoiceDtls(){

		log.info("inside  getInvoiceDtls ");
		String sOperationName = "getInvoiceDtls";       //operaStion name
		try{
			String sOutputXML = getInvoiceDtlsXMLInvoiceDtlsRes();
			log.info("sOutputXML ::"+ sOutputXML);
			XMLParser xmlparser = new XMLParser(sOutputXML);
			String smessageStatus = xmlparser.getValueOf("messageStatus");
			log.info(" smessageStatus ::" + smessageStatus);	
			String serrorDescripition  = xmlparser.getValueOf("errorDescripition");
			log.info(" serrorDescripition ::" + serrorDescripition);
			String sUTCMessageId  = xmlparser.getValueOf("UTCMessageId");
			log.info(" sUTCMessageId ::" + sUTCMessageId);
			String sdocumentNo  = xmlparser.getValueOf("documentNo");
			log.info(" sdocumentNo ::" + sdocumentNo);
			String key  = xmlparser.getValueOf("key");
			log.info(" key ::" + key);
			String utcRefNo  = xmlparser.getValueOf("utcRefNo");
			log.info(" utcRefNo ::" + utcRefNo);
			String documentType  = xmlparser.getValueOf("documentType");
			log.info(" documentType ::" + documentType);
			String documentSubType  = xmlparser.getValueOf("documentSubType");
			log.info(" documentSubType ::" + documentSubType);
			String override  = xmlparser.getValueOf("override");
			log.info(" override ::" + override);
			String ruleSetId  = xmlparser.getValueOf("ruleSetId");
			log.info(" ruleSetId ::" + ruleSetId);
			String score  = xmlparser.getValueOf("score");
			log.info(" score ::" + score);
			String status  = xmlparser.getValueOf("status");
			log.info(" status ::" + status);
			String processingStatus  = xmlparser.getValueOf("processingStatus");
			log.info(" processingStatus ::" + processingStatus);
			String uploadedBy = xmlparser.getValueOf("uploadedBy");
			log.info(" uploadedBy ::" + uploadedBy);
			String uploadOn  = xmlparser.getValueOf("uploadOn");
			log.info(" uploadOn ::" + uploadOn);
			String progress  = xmlparser.getValueOf("progress");
			log.info(" progress ::" + progress);
			String utcStatus  = xmlparser.getValueOf("utcStatus");
			log.info(" utcStatus ::" + utcStatus);
			String financialRisk  = xmlparser.getValueOf("financialRisk");
			log.info(" financialRisk ::" + financialRisk);
			String duplicate  = xmlparser.getValueOf("duplicate");
			log.info(" duplicate ::" + duplicate);
			String uploadType  = xmlparser.getValueOf("uploadType");
			log.info(" uploadType ::" + uploadType);
			String bankRefNumbers  = xmlparser.getValueOf("bankRefNumbers");
			log.info(" bankRefNumbers ::" + bankRefNumbers);
			String type  = xmlparser.getValueOf("type");
			log.info(" type ::" + type);
			String bankCode  = xmlparser.getValueOf("bankCode");
			log.info(" bankCode ::" + bankCode);
			String ruleExecutionDate  = xmlparser.getValueOf("ruleExecutionDate");
			log.info(" ruleExecutionDate ::" + ruleExecutionDate);
			String name  = xmlparser.getValueOf("name");
			log.info(" name ::" + name);
			String TaxNo  = xmlparser.getValueOf("TaxNo");
			log.info(" TaxNo ::" + TaxNo);
			String buyerOrSupplier  = xmlparser.getValueOf("buyerOrSupplier");
			log.info(" buyerOrSupplier ::" + buyerOrSupplier);
			String tradeLicenseNo  = xmlparser.getValueOf("tradeLicenseNo");
			log.info(" tradeLicenseNo ::" + tradeLicenseNo);
			String documentName  = xmlparser.getValueOf("documentName");
			log.info(" documentName ::" + documentName);
			String dockey  = xmlparser.getValueOf("key");
			log.info(" key ::" + key);
			String ruleID  = xmlparser.getValueOf("ruleID");
			log.info(" ruleID ::" + ruleID);
			String step  = xmlparser.getValueOf("step");
			log.info(" step ::" + step);
			String ruleType  = xmlparser.getValueOf("ruleType");
			log.info(" ruleType ::" + ruleType);
			String ruleCategory  = xmlparser.getValueOf("ruleCategory");
			log.info(" ruleCategory ::" + ruleCategory);
			String docstatus  = xmlparser.getValueOf("status");
			log.info(" status ::" + status);
			String isCounterParty  = xmlparser.getValueOf("isCounterParty");
			log.info(" isCounterParty ::" + isCounterParty);
			String UTCRefNo  = xmlparser.getValueOf("UTCRefNo");
			log.info(" UTCRefNo ::" + UTCRefNo);
			String outputType  = xmlparser.getValueOf("outputType");
			log.info(" outputType ::" + outputType);
			String availableScore  = xmlparser.getValueOf("availableScore");
			log.info(" availableScore ::" + availableScore);
		
		}
		catch(Exception e){
			log.error("Exception in  getInvoiceDtls: ",e);
		}
		return sOperationName;
     }
	
	public boolean getUpdateDocDetailsIntegration() {
		String sOutput = "";
		List<List<String>> sOutputlist = formObject.getDataFromDB("SELECT COUNT(1) AS COUNT FROM "
				+ "TFO_DJ_INTEGRATION_CALLS WHERE CALL_NAME = 'updateDocumentStatusUTC' AND "
				+ "WI_NAME = '"+sWorkitemID+"'");
		log.info(" sOutputlist ::" + sOutputlist);
		if (!sOutputlist.isEmpty() && !sOutputlist.get(0).isEmpty()) {
			log.info(" inside if ::" + sOutputlist);
			int sCount = Integer.parseInt(sOutputlist.get(0).get(0));
			log.info("sOutputlist ::" + sCount);
			if (sCount > 0) {
				log.info("return true ::");
				return true;
			}
		}
		return false;
	}
	
	public void saveUTCSubmitInvoiceDecHistory(String msg){
		String groupname = "";
		String personalName = "";
		String decQuery = "";
		String callName = "";
		try {
			log.info("<<<<<<<<<inside saveUTCSubmitInvoiceDecHistory>>>>>>>>>: ");
			String sQuery = "SELECT PERSONALNAME FROM PDBUSER WHERE UPPER(USERNAME)='"+ sUserName.toUpperCase() + "'";
			List<List<String>> sOutputlist = formObject.getDataFromDB(sQuery);
			if (sOutputlist != null && !sOutputlist.isEmpty()
					&& sOutputlist.get(0).size() > 0) {
				personalName = sOutputlist.get(0).get(0);
			}
			if(checkUTCCategory()){
				String decJstifyQuery = "";
				String justifyValue ="";
				
				 if("PP_MAKER".equalsIgnoreCase(this.sActivityName)){
					 callName = "Submit Invoice Call executed";
					 msg = msg;
					 if("".equalsIgnoreCase(msg)){
							msg = "Failure";
						}
				 }
				 else if("PROCESSING MAKER".equalsIgnoreCase(this.sActivityName)){
					 callName = "Submit Invoice Call executed";
					 msg = msg;
					 if("".equalsIgnoreCase(msg)){
							msg = "Failure";
						}
				 }
				 else  if("PROCESSING CHECKER".equalsIgnoreCase(this.sActivityName)){
					 callName = "update Document UTC Call executed";
					 msg = msg;
					 if("".equalsIgnoreCase(msg)){
							msg = "Failure";
						}
				 }
						
					decQuery = "INSERT INTO " + decHist + "( " + decHistCol
							+ " ) VALUES ('" + sWorkitemID + "','"
							+ this.sUserName.toUpperCase() + "','"
							+ formObject.getValue("PREV_WS") + "','"
							+ sActivityName + "',sysdate,'" + personalName
							+ "','" + groupname + "',' " + callName + "',"
							+"'',sysdate,'"+msg+"','',' ',' ','')";
						log.info("Decision UTC" + decQuery);
						formObject.saveDataInDB(decQuery);

					log.info("Decision decBRMSQuery" + decQuery);
				
			}
		} catch (Exception e) {
			log.error("exception in saveDecHistory: " + e, e);
		}
	}
	
	public void popuateInvoiceData(){
		try{
			String reqCat = formObject.getValue(REQUEST_CATEGORY).toString();
			String requestType = formObject.getValue(REQUEST_TYPE).toString();
			if ("IFS".equalsIgnoreCase(reqCat)|| "IFP".equalsIgnoreCase(reqCat)|| "IFA".equalsIgnoreCase(reqCat) || "SCF".equalsIgnoreCase(reqCat) )  { //REQ SCF ADDED
				log.info("inside reqCat");
				if("LD".equalsIgnoreCase(requestType) || "IFA_CTP".equalsIgnoreCase(requestType) || "PD".equalsIgnoreCase(requestType) || "PDD".equalsIgnoreCase(requestType)) { //REQ TYPE PD ADDED
					String query = "SELECT UTC_REF_NO,SCORE,UTC_STATUS,FINANCIAL_RISK,RULE_STEP,DUPLICATE_RISK,RULEEXECUTIONDATE,INVOICE_NUMBER  FROM tfo_dj_utc_refresh_invoices  "
							+ "where wi_name= '" +sWorkitemID + "' order by INSERTIONORDERID";

					log.info("popuateInvoiceData	query	" + query); 
					String listView = "QVAR_utc_details";
					List<List<String>> sOutputlist = formObject.getDataFromDB(query);
					log.info("popuateInvoiceData	sOutputlist	" + sOutputlist); 
					if (!sOutputlist.isEmpty()) {
						for(int i=0;i<sOutputlist.size();i++){
							String duplicateRisk = sOutputlist.get(i).get(5);
							String financialRisk = sOutputlist.get(i).get(3);
							String ruleStep = sOutputlist.get(i).get(4);
							String utcRefNo = sOutputlist.get(i).get(0);
							String score = sOutputlist.get(i).get(1);
							String utcStatus = sOutputlist.get(i).get(2);
							String ruleExec = sOutputlist.get(i).get(6);
							String invoiceNumber = sOutputlist.get(i).get(7);

							for(int j=0;j<sOutputlist.size();j++){
								log.info("popuateInvoiceData	for 2	"); 

								String gridDocumentNo = formObject.getTableCellValue(listView,j,0);

								if(gridDocumentNo.equalsIgnoreCase(invoiceNumber)){
									log.info("popuateInvoiceData	if inside 2	"); 
									formObject.setTableCellValue(listView, j, 9,duplicateRisk);
									formObject.setTableCellValue(listView, j,10,financialRisk);
									formObject.setTableCellValue(listView, j, 8,ruleStep);
									formObject.setTableCellValue(listView, j, 11,score);
									formObject.setTableCellValue(listView, j,12,utcStatus);
									formObject.setTableCellValue(listView, j, 13,ruleExec);


									break;
								}
							}

						}
					}

					String query1 = "SELECT IS_GETDOCUMENT_UTC_DONE FROM EXT_TFO "
							+ "where wi_name= '" +sWorkitemID + "'";

					log.info("popuateInvoiceData	query1	" + query1); 
					List<List<String>> sOutputlist1 = formObject.getDataFromDB(query1);
					log.info("popuateInvoiceData	sOutputlist	" + sOutputlist1); 
					if (!sOutputlist1.isEmpty()) {
						String isGetDoc = sOutputlist1.get(0).get(0);
						formObject.setValue("IS_GETDOCUMENT_UTC_DONE",isGetDoc);
					}

				} 
			}
		}
		catch (Exception e) {
			log.error("exception in saveDecHistory: " + e, e);
		}
	}

	public String checkUTCFlag(){
		String isGetDoc ="";
		try{
			String query1 = "SELECT IS_GETDOCUMENT_UTC_DONE FROM EXT_TFO "
					+ "where wi_name= '" +sWorkitemID + "'";

			log.info("popuateInvoiceData	query1	" + query1); 
			List<List<String>> sOutputlist1 = formObject.getDataFromDB(query1);
			log.info("popuateInvoiceData	sOutputlist	" + sOutputlist1); 
			if (!sOutputlist1.isEmpty()) {
				 isGetDoc = sOutputlist1.get(0).get(0);
				formObject.setValue("IS_GETDOCUMENT_UTC_DONE",isGetDoc);
			}
		}catch (Exception e) {
			log.error("exception in saveDecHistory: " + e, e);
		}
		return isGetDoc;
	}
	
public void updateGetDocument(){
	try{
		String query="update ext_tfo set IS_GETDOCUMENT_UTC_DONE = 'N' where wi_name='"+this.sWorkitemID+"'";
		log.info("updateGetDocument in ext table records query : "+query);
		int output=formObject.saveDataInDB(query);
		log.info("Aupdate query result : "+output);
		formObject.setValue("IS_GETDOCUMENT_UTC_DONE","N");
	} catch (Exception e) {
		log.error("exception in updateGetDocument: " + e, e);
	}
}


public void updateHiddenFlag(){
	try{
		String query="update ext_tfo set hidden_start_flag = 'true' where wi_name='"+this.sWorkitemID+"'";
		log.info("updateHiddenFlag in ext table records query : "+query);
		int output=formObject.saveDataInDB(query);
		log.info("updateHiddenFlag query result : "+output);
	} catch (Exception e) {
		log.error("exception in updateHiddenFlag: " + e, e);
	}
}



public boolean checkStartUTC(){
	try{
		String listView = "QVAR_utc_details";
		int noOfInvoice=getGridCount(listView);
		for(int j=0;j<noOfInvoice;j++) {
			String gridUtcRefNo = formObject.getTableCellValue(listView,j,7);
			log.info("checkStartUTC gridUtcRefNo	"+gridUtcRefNo);
			
			if(!"".equalsIgnoreCase(formObject.getTableCellValue(listView, j, 7).toString())){
				log.info("checkStartUTC inside null");
				return false;
			}
		}
	   
	 }catch (Exception e){
	log.error("Exception: ",e);
	
     }
	return true;
}

public String executeUpdateDocUTC() throws Exception {
	String sOutput = "";
	List<List<String>> sOutputlist = formObject.getDataFromDB("SELECT SEQ_WEBSERVICE.nextval as IDValue from DUAL");
	String txnNumber= sOutputlist.get(0).get(0);
	sOutput = updateDocInputXML(txnNumber);
	sOutput = socket.connectToSocket(sOutput);
	log.info("output="+sOutput);
	return sOutput;
}

public void setDataInTRSDTab(){
	log.info("in setDataInTRSDTab  : >>>>>>>");
	String strQ = "";
	List<List<String>> recordList;
	String columnName="";
	String trsdFlag="";

	try{
		strQ = "select trsd_flag from ext_tfo where wi_name='"+sWorkitemID+"'";
		log.info("loadref Query  : >>>>>>>" + strQ);
		recordList = formObject.getDataFromDB(strQ);
		log.info("loadref recordList  : >>>>>>>" + recordList);
		log.info(" recordList  : >>>>>>>" + recordList != null && !recordList.isEmpty());
		if(recordList != null && !recordList.isEmpty()){
			log.info("trsdFlag: "+recordList.get(0).get(0));
			trsdFlag=recordList.get(0).get(0).toString();
			log.info("trsdFlag: "+trsdFlag);
			formObject.setValue(TRSD_FLAG, trsdFlag);
		}
		
		strQ="update tfo_dj_trsd_screening_details set screening_performed_by = (select username from pdbuser "
					+ "WHERE upper(userindex) = upper('"+ sUserIndex +"'))"
					+ "WHERE winame = '"+sWorkitemID+"' and screening_performed_by is null";
		log.info("THE QUERYYY IS :"+strQ);
		int value = formObject.saveDataInDB(strQ);
		log.info("TFO_DJ_TRSD_SCREENING_DETAILS TRSD SCREENING PERFORMED BY: "+ value);
		
		strQ = "select entity,entity_name,trsd_screening_type,trsd_screening_result,case_id,case_number,pending_in_trsd_group,"+
				"case_closed_by,to_char(screening_date,'YYYY-MM-dd HH24:mi:ss'),"
				+ "to_char(assessment_date,'YYYY-MM-dd HH24:mi:ss'),channel_reference_no,remarks,execution_status,trsd_category,screening_performed_by"+
				" from tfo_dj_trsd_screening_details where winame='"+sWorkitemID+"'";
		log.info("loadref Query  : >>>>>>>" + strQ);
		recordList = formObject.getDataFromDB(strQ);
		log.info("recordList :>>>>>"+recordList);
		columnName="Entity,Entity_Name,TRSD_Screening_Type,TRSD Screening Result,Case ID,Case Number,Pending in TRSD Group,Case Closed By,"
				+ "Screening Date,Assessment Date,Channel Ref No,Remarks,EXECUTION_STATUS,TRSD_CATEGORY,screening_performed_by";
		LoadListView(recordList,columnName,LISTVIEW_TRSD_TABLE);

		
		strQ="update tfo_dj_trsd_screening_other_details set screening_performed_by = (select username from pdbuser "
				+ "WHERE upper(userindex) = upper('"+ sUserIndex +"'))"
				+ "WHERE winame = '"+sWorkitemID+"' and screening_performed_by is null";
		log.info("THE 2nd  QUERYYY IS :"+strQ);
		
		int value1 = formObject.saveDataInDB(strQ);
		log.info("TFO_DJ_TRSD_SCREENING_OTHER_DETAILS TRSD SCREENING PERFORMED BY: "+ value1);
		
		strQ = "select entity,entity_name,trsd_screening_type,trsd_screening_result,case_id,case_number,pending_in_trsd_group,"+
				"case_closed_by,to_char(screening_date,'YYYY-MM-dd HH24:mi:ss'),to_char(assessment_date,'YYYY-MM-dd HH24:mi:ss'),"
				+ "channel_reference_no,remarks,EXECUTION_STATUS,screening_performed_by"+
				" from tfo_dj_trsd_screening_other_details where winame='"+sWorkitemID+"'";
		log.info("loadref Query  : >>>>>>>" + strQ);
		recordList = formObject.getDataFromDB(strQ);
		log.info("recordList :>>>>>"+recordList);
		columnName="Entity,Entity_Name,TRSD_Screening_Type,TRSD Screening Result,"
				+ "Case ID,Case Number,Pending in TRSD Group,Case Closed By,Screening Date,Assessment Date,Channel Ref No,Remarks,EXECUTION_STATUS,screening_performed_by";
		LoadListView(recordList,columnName,LISTVIEW_TRSD_TABLE_OPTIONAL);
		enableDisableTRSDButton();
	}catch(Exception e){
		log.error("Exception in createNewWorkitemELCB",e);
	}
}

public void enableDisableTRSDButton(){
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
			enableFieldOnDemand("BTN_SCREENING_BROWSE");
		}else{
			disableFieldOnDemand("BTN_SCREENING_BROWSE");
			disableFieldOnDemand(BTN_START_TRSD);
		}
	}catch(Exception e){
		log.error("Exception in enableDisableTRSDButton",e);
	}
}
public String updateDocInputXML(String sTxn) throws Exception {
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
		.append("<channelName>UTC</channelName>").append("\n")
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

public void sendTSLMPushMessage(String mode){
	try{
		log.info("Inside sendTSLMPushMessage");
		String res;
		String createAmendContract = formObject.getValue(CREATE_AMEND_CNTRCT_FCUBS).toString();
		String createAmendStatus = formObject.getValue(CREATE_AMEND_STATUS_FCUBS).toString();
		String processingSystem = formObject.getValue(PROCESSING_SYSTEM).toString();
		String pmPushMsgFlag = formObject.getValue(PM_TSLM_PUSH_MSG_FLAG).toString();
		String key = requestCategory+"#"+requestType+"#"+createAmendContract+"#"+"NA";
		key = key +"#"+ processingSystem;
		log.info("In sendTSLMPushMessage key= "+ key);
		if((!"Y".equalsIgnoreCase(pmPushMsgFlag)) && "Y".equalsIgnoreCase(createAmendContract)  //only for TSLM 
				&& "T".equalsIgnoreCase(processingSystem)){
			if(pmRouteMap.containsKey(key)){
				String value = pmRouteMap.get(key);
				log.info("In sendTSLMPushMessage value= "+ value);
				if(value.equalsIgnoreCase("Y")){
					log.info("In sendTSLMPushMessage if value= Y");
					res = createTSLMPushMsg(mode);
					XMLParser xmlparser = new XMLParser(res);
					String statusCode = xmlparser.getValueOf("StatusCode");
					if("0".equalsIgnoreCase(statusCode)){
						formObject.setValue(PM_TSLM_PUSH_MSG_FLAG, "Y");
					} else {
						formObject.setValue(PM_TSLM_PUSH_MSG_FLAG, "N");
					}
				}
			}
		}
	}catch(Exception e){
		log.error("Exception in sendTSLMPushMessage",e);
	}
}
		public String createTSLMInputXML(String sTxn,String mode) throws Exception {
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
				.append("<mode>"+mode+"</mode>").append("\n")
				.append("<channelName>TSLM</channelName>").append("\n")
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

	public String createTSLMPushMsg(String mode){
		String sOutput = "";
		try{
			List<List<String>> sOutputlist = formObject.getDataFromDB("SELECT SEQ_WEBSERVICE.nextval as IDValue from DUAL");
			String txnNumber= sOutputlist.get(0).get(0);
			sOutput = createTSLMInputXML(txnNumber,mode);
			sOutput = socket.connectToSocket(sOutput);
			log.info("output="+sOutput);
		}catch(Exception e){
			log.error("Exception in",e);
		}
		return sOutput;
		}
	
	public void onSubmitReferral(){
		log.info("ActivityName "+this.sActivityName+ "   Workitem ID"+this.sWorkitemID);
		log.info("");
		String processType=formObject.getValue(PROCESS_TYPE).toString();
		log.info("processType :"+processType);
		try{
		if(processType.equalsIgnoreCase("TSLM Front End")){
		String sReferTo="";
		StringBuilder sFinalString = new StringBuilder();
		int lst;
		
		if("RM".equalsIgnoreCase(this.sActivityName)){
			sReferTo = "='RM'";
		}
		else if("LEGAL_TEAM".equalsIgnoreCase(this.sActivityName)){
			sReferTo = "='Legal'";
		}
		else if("TSD".equalsIgnoreCase(this.sActivityName)){
			sReferTo = "='TSD'";
		}
		else if("CUSTOMER_REVIEW".equalsIgnoreCase(this.sActivityName) ||
				"Customer Referral Response".equalsIgnoreCase(this.sActivityName)){
			sReferTo = "LIKE '%Customer%'";
		}
		else if("CORRESPONDENT_BANK".equalsIgnoreCase(this.sActivityName)){
			sReferTo = "='Correspondent Bank'";
		}
		else if("Treasury".equalsIgnoreCase(this.sActivityName)){
			sReferTo = "='Treasury'";
		}
		else if("TB Sales".equalsIgnoreCase(this.sActivityName)){
			sReferTo = "='Trade Sales'";
		}
		else if("COMPLIANCE".equalsIgnoreCase(this.sActivityName)){
			sReferTo = "='COMPLIANCE'";
		}
		
		String sQuery = "";
		log.info("loadDecReferalGrid: " + processType);
		
			sQuery = "UPDATE TFO_DJ_TSLM_DOCUMENT_REVIEW SET FLAG_DEL='Y' WHERE WI_NAME='" + this.sWorkitemID + "' AND REFCODE "+sReferTo+"";
			lst = formObject.saveDataInDB(sQuery);
			log.info(" query :"+sQuery +": lst :"+lst);		
		
			sQuery="UPDATE TFO_DJ_TSLM_REFERRAL_DETAIL SET FLAG_DEL='Y' WHERE WI_NAME='" + this.sWorkitemID + "' AND REFCODE "+sReferTo+"";
			lst = formObject.saveDataInDB(sQuery);
			log.info(" query :"+sQuery +": lst :"+lst);
			
			sQuery="UPDATE TFO_DJ_TSLM_LIMIT_CREDIT_REVIEW SET FLAG_DEL='Y' WHERE WI_NAME='" + this.sWorkitemID + "' AND REFCODE "+sReferTo+"";
			lst = formObject.saveDataInDB(sQuery);
			log.info(" query :"+sQuery +": lst :"+lst);
			
			setReferralStatusOnLoad("SIGN_REFERRAL_ID");
			setReferralStatusOnLoad("Doc_Review_RefID");
			setReferralStatusOnLoad("LIMIT_REFERRAL_ID");
		}
	}catch(Exception e){
		log.error("Exception in",e);
	}
	}
	
	public void setReferralStatusOnLoad(String listVwName){

		String tableName = "";
		String colName="";
		if ("SIGN_REFERRAL_ID".equalsIgnoreCase(listVwName)) {
			tableName = "TFO_DJ_TSLM_REFERRAL_DETAIL";
			colName="SUFF_BAL_AVL_PPM";
		}else if ("Doc_Review_RefID".equalsIgnoreCase(listVwName)) {
			tableName = "TFO_DJ_TSLM_DOCUMENT_REVIEW";	
			colName="DOC_REV_SUCC_PPM";
		} else if ("LIMIT_REFERRAL_ID".equalsIgnoreCase(listVwName)) {
			tableName = "TFO_DJ_TSLM_LIMIT_CREDIT_REVIEW";
			colName="LMTCRE_APP_AVL_PPM";
		}
		String status="";
		String seqNo="";
		String delFlag="";
		
		
		try{
			List<List<String>> result=formObject.getDataFromDB("SELECT SEQNO,STATUS,FLAG_DEL FROM "+tableName+" WHERE WI_NAME='"+sWorkitemID+"'");
		log.info("loadTabListView_TSLM output: "+result);
		int length = result.size();
		log.info("loadTabListView_TSLM length: "+length);
		for(int i=0;i<length;i++){
			status=result.get(i).get(1);
			seqNo=result.get(i).get(0);
			delFlag=result.get(i).get(2);
			log.info("status: "+status);
			log.info("seqNo: "+seqNo);
			log.info("delFlag: "+delFlag);
			
			if(delFlag.trim().equalsIgnoreCase("Y")){
			
			String sQuery = "UPDATE "+tableName+" SET STATUS = 'Closed'"+
					" WHERE WI_NAME = '"+sWorkitemID+"' AND SEQNO='"+seqNo+"'";
			
			log.info("updateReferralDetailsTSLM query :: "+sQuery);
			int sUpdateOutput = formObject.saveDataInDB(sQuery);
			log.info("updateReferralDetailsTSLM Output: "+sUpdateOutput);
			}
		}
		
		result=formObject.getDataFromDB("select count(0) as count from "+tableName+" WHERE status='Active' and wi_name='"+sWorkitemID+"'");
		log.info("loadTabListView_TSLM output: "+result);
		if(Integer.parseInt(result.get(0).get(0))==0){
			String sQuery = "UPDATE EXT_TFO SET "+colName+" = '1'"+
					" WHERE WI_NAME = '"+sWorkitemID+"'";
			
			log.info("updateReferralDetailsTSLM query :: "+sQuery);
			int sUpdateOutput = formObject.saveDataInDB(sQuery);
			log.info("updateReferralDetailsTSLM Output: "+sUpdateOutput);
			
			
		}
		
		
		
		}catch(Exception e){
			log.error("Exception in",e);
		}
	}
	
	public void updateReferralStatus(String listVwName,IFormReference formObject){
		
		String tableName = "";
		String colName="";
		if ("SIGN_REFERRAL_ID".equalsIgnoreCase(listVwName)) {
			tableName = "TFO_DJ_TSLM_REFERRAL_DETAIL";
			colName="SUFF_BAL_AVL_PPM";
		}else if ("Doc_Review_RefID".equalsIgnoreCase(listVwName)) {
			tableName = "TFO_DJ_TSLM_DOCUMENT_REVIEW";	
			colName="DOC_REV_SUCC_PPM";
		} else if ("LIMIT_REFERRAL_ID".equalsIgnoreCase(listVwName)) {
			tableName = "TFO_DJ_TSLM_LIMIT_CREDIT_REVIEW";
			colName="LMTCRE_APP_AVL_PPM";
		}
		try{
		List<List<String>> result=formObject.getDataFromDB("select count(0) as count from "+tableName+" WHERE status='Active' and wi_name='"+sWorkitemID+"'");
		log.info("loadTabListView_TSLM output: "+result);
		if(Integer.parseInt(result.get(0).get(0))==0){
			formObject.setValue(colName, "1");	
		}else{
			formObject.setValue(colName, "2");
		}
		}catch(Exception e){
			log.error("Exception in",e);	
		}
		}
	
	public void updateReferralStatusonChange(String listVwName,IFormReference formObject){
		
		String colName="";
		if ("SIGN_REFERRAL_ID".equalsIgnoreCase(listVwName)) {
			colName="SUFF_BAL_AVL_PPM";
		}else if ("Doc_Review_RefID".equalsIgnoreCase(listVwName)) {
			colName="DOC_REV_SUCC_PPM";
		} else if ("LIMIT_REFERRAL_ID".equalsIgnoreCase(listVwName)) {
			colName="LMTCRE_APP_AVL_PPM";
		}
		int counter=0;
		try{
			JSONArray arr=formObject.getDataFromGrid(listVwName);
			String status="";
			for(int count=0;count<arr.size();count++){
				status=formObject.getTableCellValue(listVwName, count, 5);
				
				if(status.trim().equalsIgnoreCase("Active")){
					counter=1;
					break;
				}
			}
		if(counter==0){
			formObject.setValue(colName, "1");	
			
		}else{
			formObject.setValue(colName, "2");
		}
		}catch(Exception e){
			log.error("Exception in DFGF",e);	
		}
		}

		public String callTRSD(String txnNumber) throws Exception {
			String sOutput = "";
			sOutput = createTRSDInputXML(txnNumber);
			sOutput = socket.connectToSocket(sOutput);
			log.info("output="+sOutput);
			return sOutput;
		}
		
		public String createTRSDInputXML(String sTxn) throws Exception {
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
				.append("<channelName>SANCTION_SCREENING</channelName>").append("\n")
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
		
		public String callUTC(String txnNumber) throws Exception {
			String sOutput = "";
			sOutput = createUTCInputXML(txnNumber);
			sOutput = socket.connectToSocket(sOutput);
			log.info("output="+sOutput);
			return sOutput;
		}
		
		public String createUTCInputXML(String sTxn) throws Exception {
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
				.append("<channelName>UTC_MOD_INVOICE</channelName>").append("\n")
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
		
		public String getExtTableData(){
			String sQuery = "SELECT REASON_FOR_ACTION FROM EXT_TFO WHERE " +
					"  WI_NAME = '"+this.sWorkitemID+"' ";
			List<List<String>> record = formObject.getDataFromDB(sQuery);
			String reasonAction = record.get(0).get(0);
			return reasonAction;
		}
		
//ADDED BY SHIVANSHU FOR DHL
		public int getDocumentDHL(){
			String docType = "";
			int countAWB = -1;
			int countLetter = -1;
			int returnCode = 0;
			String docFlagAWB ="";
			String docFlagLetter ="";
			String remotePresent = formObject.getValue("IS_REMOTE_PRESENTATION").toString() ;
			String courierCompany = formObject.getValue("COURIER_COMPANY").toString() ;
			
			
			try{
				log.info("Inside DHL");
					String query="Select COUNT(1),pd.name FROM PdbDocument pd inner join pdbDocumentContent pdc on pd.documentindex = pdc.documentindex "
							+ " where Upper(pd.Name) IN ('COURIER_AWB','REMITTANCE_LETTER')  "
							+ " and pdc.parentfolderindex = (select itemindex from ext_tfo where wi_name =  '"+sWorkitemID+"')"
							+ " group by pd.name";
					log.info("DHL DOCUMENT query :"+query);
					List<List<String>> docList = formObject.getDataFromDB(query);
//					log.info("document count: "+docList.size());
					if (docList != null && !docList.isEmpty()) {
				       for(int i=0;i<docList.size();i++){
				    	   docType = docList.get(i).get(1);
				    	   if ("Courier_AWB".equalsIgnoreCase(docType)) {
				    		   countAWB = Integer.parseInt(docList.get(i).get(0));
				    	   }else if ("Remittance_Letter".equalsIgnoreCase(docType)){
				    		   countLetter = Integer.parseInt(docList.get(i).get(0));
				    	   }
				       }
					log.info("document count AWB: "+countAWB + " and Remittance letter : " + countLetter);
					log.info("DHL DOCUMENT remotePresent :"+remotePresent);
					log.info("DHL DOCUMENT courierCompany :"+courierCompany);

					if(("PROCESSING MAKER".equalsIgnoreCase(sActivityName) || "DELIVERY".equalsIgnoreCase(sActivityName)) 
							&& "Y".equalsIgnoreCase(remotePresent)	&& !"Not Applicable".equalsIgnoreCase(courierCompany)){
							log.info("Inside AWB Doc");
							if (countAWB <= 0) {
								//sendMessageHashMap(BUTTON_SUBMIT, "Please add AWB Courier Document");
								docFlagAWB = "No";
								returnCode = -1;
							}else {
								docFlagAWB = "Yes";
							}
							log.info("document flag AWB: "+docFlagAWB);
							formObject.setValue("IS_AWB_DOCS_ATTACHED",docFlagAWB );
					}
					if("DELIVERY".equalsIgnoreCase(sActivityName)||"PROCESSING CHECKER".equalsIgnoreCase(sActivityName)){
						log.info("Inside Letter Doc");
						if(countLetter <= 0) {
							//sendMessageHashMap(BUTTON_SUBMIT, "Please add Remittance Letter Document");
							docFlagLetter = "No";
							returnCode = -2;
						}else {
							docFlagLetter = "Yes";
							}
						formObject.setValue("IS_DOCS_ATTACHED",docFlagLetter );
					}
				 }else {
					//ATP-375 24-01-2024 JAMSHED 
					//START
					 formObject.setValue("IS_DOCS_ATTACHED", "No");  //ADDED BY JAMSHED for ATP-375
					 formObject.setValue("IS_AWB_DOCS_ATTACHED", "No");
					//END ATP-375 24-01-2024 JAMSHED
					log.info("Inside no Doc is there");
					 if(("PROCESSING MAKER".equalsIgnoreCase(sActivityName) || "DELIVERY".equalsIgnoreCase(sActivityName)) 
								&& "Y".equalsIgnoreCase(remotePresent)	&& !"Not Applicable".equalsIgnoreCase(courierCompany)){
					 //sendMessageHashMap(BUTTON_SUBMIT, "Please add Remittance Letter or AWB Courier Document");
						log.info("For awb doc");
						 returnCode = -3;
					 }
					 if("DELIVERY".equalsIgnoreCase(sActivityName)){
						log.info("For remittance letter");
						 returnCode = -3;
					 }
				 }
				} catch(Exception e) {
					log.error("Exception: ",e);
				}
			return returnCode;
		}
		
		public String callDocumentDHL(String txnNumber, String contractRefNo) throws Exception {
			String sOutput = "";
			sOutput = createDocumentDHLInputXML(txnNumber,contractRefNo);
			sOutput = socket.connectToSocket(sOutput);
			log.info("output="+sOutput);
			return sOutput;
			
		}
		
		public String createDocumentDHLInputXML(String sTxn, String contRefNo) throws Exception {
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
				.append("<channelName>DOCUMENT_TFO</channelName>").append("\n")
				.append("<correlationId>"+contRefNo+"</correlationId>").append("\n")
				.append("<channelRefNumber>"+contRefNo+"</channelRefNumber>").append("\n")
				.append("<sysrefno>"+sTxn+"</sysrefno>").append("\n")
				.append("<processName>TFO</processName>").append("\n")
				.append("</APWebService_Input>");
				log.info("input xml of createWorkitemInputXML="+ inputXml.toString());
			} catch (Exception e) {
				log.error("Exception in",e);
			}
			return inputXml.toString();
}
public void enableDisbaleRetryButton(){
			
			int uploadedDocCount=0;
			int payloadDocCount=-1;
			String query="SELECT count(DOC.DOCUMENTINDEX) COUNT FROM PDBDOCUMENT DOC, PDBDOCUMENTCONTENT"
					+ " DOCCONTENT , PDBFOLDER FOLDER WHERE DOC.DOCUMENTINDEX = DOCCONTENT.DOCUMENTINDEX "
					+ "AND DOCCONTENT.PARENTFOLDERINDEX = FOLDER.FOLDERINDEX  AND "
					+ "FOLDER.NAME  = '"+sWorkitemID+"' AND DOC.NAME  "
					+ "IN ('Customer_Docs','E_Receipt','MICS_Docs','Approval_Docs')";
			
			List<List<String>> lresultSet=null;
			lresultSet =  formObject.getDataFromDB(query);
			log.info("Query output="+lresultSet);
			
			if(lresultSet != null&&lresultSet.size()>0&&!lresultSet.get(0).get(0).isEmpty()) {
			uploadedDocCount=Integer.parseInt(lresultSet.get(0).get(0));
			}
			query="SELECT DOCUMENT_COUNT FROM TFO_DJ_TSLM_SCF_TXN_DATA_HISTORY WHERE WI_NAME='"+sWorkitemID+"'";
			lresultSet=null;
			lresultSet =  formObject.getDataFromDB(query);
			log.info("Query output="+lresultSet);
			
			if(lresultSet != null&&lresultSet.size()>0&&!lresultSet.get(0).get(0).isEmpty()) {
			payloadDocCount=Integer.parseInt(lresultSet.get(0).get(0));
			}
			
			if(uploadedDocCount<payloadDocCount){
				formObject.setStyle(BUTTON_RETRY, VISIBLE, TRUE);
			}else{
				formObject.setStyle(BUTTON_RETRY, VISIBLE, FALSE);
			}
		}
		
		public void uploadDocFromPayload(){
			
			String NASDocFolderName = "";
			String NASDataMovePath = "";
			String NASDocPath = "";
			String channelRefNo="";
			String CabinetName = "";
			String itemIndex="";
			String CURR_WS="";
			int volumeID=0;
			String JTSIP = "";
			String serverIP="";
			String serverPort="";
			int JTSPort=0;
			
			
			Properties p = new Properties();
			StringBuilder configFile = new StringBuilder(System.getProperty("user.dir"))
			.append(System.getProperty("file.separator"))
			.append("WebServiceConf")
			.append(System.getProperty("file.separator"))
			.append("BPMModify")
			.append(System.getProperty("file.separator"))
			.append("LAPS_config.properties");
			
			
			try{
			InputStream stream = new FileInputStream(configFile.toString());
			if (stream == null) {
				log.info("LAPS_config.properties not found");
			}
			p.load(stream);
			stream.close();
			
			NASDocPath = p.getProperty("NASDocPath");
			JTSIP = p.getProperty("sJtsIp");
			JTSPort = Integer.parseInt(p.getProperty("iJtsPort"));
			volumeID = Integer.parseInt(p.getProperty("volumeID"));
			CabinetName = p.getProperty("CabinetName");
			serverIP= p.getProperty("IP");
			serverPort= p.getProperty("Port");
			NASDataMovePath= p.getProperty("NASDataMovePath");
			NASDocFolderName =NASDocPath;
			
			log.info("NASDocFolderName "+NASDocFolderName);
			log.info("JTSIP "+JTSIP);
			log.info("JTSPort "+JTSPort);
			log.info("volumeID "+volumeID);
			log.info("CabinetName "+CabinetName);
			
			}catch(IOException e){
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				String exception=sw.toString();
				log.error("exception in insertDocDecisionHistory: "+exception);
			}
			
			
			String query="SELECT CHANNELREFNUMBER FROM TFO_DJ_TSLM_SCF_TXN_DATA_HISTORY WHERE WI_NAME='"+sWorkitemID+"'";
			
			List<List<String>> lresultSet=null;
			lresultSet =  formObject.getDataFromDB(query);
			log.info("Query output="+lresultSet);
			
			channelRefNo=lresultSet.get(0).get(0);
			log.info("channelRefNo output="+channelRefNo);
			
			NASDocFolderName=NASDocFolderName+File.separatorChar+channelRefNo+File.separatorChar;
			log.info("NASDocFolderName "+NASDocFolderName);
			
			query="SELECT ITEMINDEX,CURR_WS FROM EXT_TFO WHERE WI_NAME='"+sWorkitemID+"'";
			
			lresultSet=null;
			lresultSet =  formObject.getDataFromDB(query);
			log.info("Query output="+lresultSet);
			
			itemIndex=lresultSet.get(0).get(0);
			CURR_WS=lresultSet.get(0).get(1);
			
			File docPath = new File(NASDocFolderName);
			
			String docIndex = "";
			JPISIsIndex ISINDEX = new JPISIsIndex();
			JPDBRecoverDocData JPISDEC = new JPDBRecoverDocData();
			try {
				ExecuteXML.init("WebSphere", serverIP, serverPort);
				
				List<String> fileNamesAll = new ArrayList<String>();
				File fileObj = new File(NASDocFolderName);
				log.info("fileObj.exists(): "+fileObj.exists());
				if(fileObj.exists()){
					File[] allFiles = fileObj.listFiles(new FilenameFilter() {
						@Override
						public boolean accept(File dir, String name) {
							return true;
						}
					});
					for(File file : allFiles){
						if(file.isFile()){
							if(!file.getName().substring(file.getName().lastIndexOf(".")+1).equalsIgnoreCase("pgp"))
							fileNamesAll.add(file.getName());
}
					}
					int allFileCount = fileNamesAll.size();
					log.info("fileCount : "+ allFileCount);
					log.info("fileNames : "+ fileNamesAll.toString());
					for(File file:allFiles){
					long lFileLength = file.length();
					log.info("lFileLength : "+ lFileLength);
					JPISDEC.m_cDocumentType = 'N';
					JPISDEC.m_nDocumentSize = (int) lFileLength;
					JPISDEC.m_sVolumeId = (short) (volumeID);
					String filePath = file.getPath();
					try {
						if (JPISDEC.m_nDocumentSize != 0) {
							log.info("JTSIP : "
									+ JTSIP);
							log.info("JTSPort : "
									+ JTSPort);
							log.info("documentPath : "
									+ filePath);
							log.info("volumeID : "
									+ volumeID);
							log.info("JPISDEC.m_nDocumentSize : "
									+ JPISDEC.m_nDocumentSize);
							try {
								CPISDocumentTxn.AddDocument_MT(null, JTSIP,
										(short) JTSPort, 
										CabinetName, (short) (volumeID),filePath, JPISDEC, "", ISINDEX);
							} catch (JPISException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					} catch (Exception e) {
						StringWriter sw = new StringWriter();
						PrintWriter pw = new PrintWriter(sw);
						e.printStackTrace(pw);
						String exception=sw.toString();
						log.error("exception in add doc: "+exception);
					}
					docIndex = JPISDEC.m_nDocIndex + "#" + volumeID;
				log.info("fileNamesERcpt.get(0) : "
							+ file.getName().substring(0, file.getName().indexOf(".")));
					log.info("fileNamesERcpt.get(0).indexOf(.) : "
							+ file.getName().indexOf("."));
					log.info("fileNamesERcpt.get(0).length() : "
							+ file.getName().length());
					log.info("fileNamesERcpt.get(0) extnsn : "
							+ file.getName().substring((file.getName().indexOf(".")+1), file.getName().length()));
					
					String outputXml="";
					if(file.getName().substring(0, file.getName().indexOf(".")).equalsIgnoreCase("E_Receipt")){
					outputXml = AddDocument(sessionId, itemIndex, "E_Receipt",
							file.getName().substring((file.getName().indexOf(".")+1), file.getName().length()),
							file.getName().substring(0, file.getName().indexOf(".")),
							volumeID,
							docIndex, 1, "N", String.valueOf(lFileLength),CabinetName);
					}else if(file.getName().substring(0, file.getName().indexOf(".")).equalsIgnoreCase("Customer_Docs")){
						outputXml = AddDocument(sessionId, itemIndex, "Customer_Docs",
								file.getName().substring((file.getName().indexOf(".")+1), file.getName().length()),
								file.getName().substring(0, file.getName().indexOf(".")),
								volumeID,
								docIndex, 1, "N", String.valueOf(lFileLength),CabinetName);
					}else if(file.getName().substring(0, file.getName().indexOf(".")).equalsIgnoreCase("Approval_Docs")){
						outputXml = AddDocument(sessionId, itemIndex, "Approval_Docs",
								file.getName().substring((file.getName().indexOf(".")+1), file.getName().length()),
								file.getName().substring(0, file.getName().indexOf(".")),
								volumeID,
								docIndex, 1, "N", String.valueOf(lFileLength),CabinetName);
					}else{
						outputXml = AddDocument(sessionId, itemIndex, "MICS_Docs",
								file.getName().substring((file.getName().indexOf(".")+1), file.getName().length()),
								file.getName().substring(0, file.getName().indexOf(".")),
								volumeID,
								docIndex, 1, "N", String.valueOf(lFileLength),CabinetName);
					}
					XMLParser xp = new XMLParser(outputXml);
					if("0".equalsIgnoreCase(xp.getValueOf("Status"))) {
						insertDocDecisionHistory(file.getName()+" Linked Successfully",CabinetName,CURR_WS);
						moveFile(NASDocPath,NASDataMovePath,channelRefNo);
					}else{
						insertDocDecisionHistory(file.getName()+" Linking Failed",CabinetName,CURR_WS);
					}
				}} else {
					String errorDescription = "Document folder not found";
					insertDocDecisionHistory(errorDescription,CabinetName,CURR_WS);
				}
			
			} catch (Exception e) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				String exception=sw.toString();
				log.error("exception in uploadDocFromPayload: "+exception);
				log.error(e);
			}
		}
			
			public static String AddDocument(String sessionId, String parentFolderIndex, String documentName, 
					String createdByAppName, String comment,  int volumeIndex,  String ISIndex, int noOfPages, 
					String docType, String docSize,String CabinetName) throws NGException
			{
				StringBuilder sInputXML = new StringBuilder(); 
				sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
				.append("<NGOAddDocument_Input>").append("\n")
				.append("<Option>NGOAddDocument</Option>").append("\n")
				.append("<CabinetName>"+CabinetName+"</CabinetName>").append("\n")
				.append("<UserDBId>" + sessionId + "</UserDBId>").append("\n")
				.append("<GroupIndex>0</GroupIndex>").append("\n")
				.append("<Document>").append("\n")
				.append("<VersionFlag>Y</VersionFlag>").append("\n")
				.append("<ParentFolderIndex>" + parentFolderIndex + "</ParentFolderIndex>").append("\n")
				.append("<DocumentName>" + documentName + "</DocumentName>").append("\n")
				.append("<CreatedByAppName>" + createdByAppName + "</CreatedByAppName>").append("\n")
				.append("<Comment>" + comment + "</Comment>").append("\n")
				.append("<VolumeIndex>" + volumeIndex + "</VolumeIndex>").append("\n")
				.append("<NoOfPages>" + noOfPages + "</NoOfPages>").append("\n")
				.append("<ISIndex>" + ISIndex + "</ISIndex>").append("\n")
				.append("<DocumentType>" + docType + "</DocumentType>").append("\n")
				.append("<DocumentSize>" + docSize + "</DocumentSize>").append("\n")
				.append("</Document>").append("\n")
				.append("</NGOAddDocument_Input>");
				String outputXML =  ExecuteXML.executeXml(sInputXML.toString());
				//log.info("IsSessionValid OutputXML ===>" + outputXML);
				return outputXML;   
			}
				
			public void insertDocDecisionHistory(String status,String CabinetName,String CURR_WS) {
				try {
					log.info("inside insertDocDecisionHistory");
					String valList = "'"+ sWorkitemID +"','"+sUserName+"','"+CURR_WS+"',SYSTIMESTAMP,'Document Linking','"+status+"'";
					APInsert("TFO_DJ_DEC_HIST", "WI_NAME, USERNAME, CURR_WS_NAME, CREATE_DATE, ACTION, REMARKS", valList, sessionId,CabinetName);
				} catch (Exception e) {
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e.printStackTrace(pw);
					String exception=sw.toString();
					log.error("exception in insertDocDecisionHistory: "+exception);
				}
			}
			
			public static String APInsert(String tableName,String columnName,String strValues, String sessionId,String CabinetName) throws NGException
			{

				StringBuilder sInputXML = new StringBuilder(); 
				sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
				.append("<APInsert_Input>").append("\n")
				.append("<Option>APInsert</Option>").append("\n")
				.append("<EngineName>" + CabinetName + "</EngineName>").append("\n")
				.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
				.append("<TableName>"+ tableName + "</TableName>").append("\n")
				.append("<ColName>" + columnName + "</ColName>").append("\n")
				.append( "<Values>" + strValues + "</Values>").append("\n")
				.append("</APInsert_Input>");
				String outputXML =  ExecuteXML.executeXml(sInputXML.toString());
				return outputXML;   
			}
			
			public void insertIntoNotificationTxnRemittanceLetter() {
				try{
					log.info("inside insertIntoNotificationTxnRemittanceLetter");
					String proTradeRef = "";
					String prodCode = "";
					String status = "Remittance Letter Update";
					String remarks = "";
					String sContractRefNo = formObject.getValue(TRANSACTION_REFERENCE).toString();
					//Added by shivanshu
						if ("ELCB_AM".equalsIgnoreCase(requestType) || "EC_AM".equalsIgnoreCase(requestType) 
							|| ("ELCB_BL".equalsIgnoreCase(requestType) || "EC_BL".equalsIgnoreCase(requestType))){
								String query="Select LISTAGG(pd.documentindex,'|') AS DOCUMENTINDEX  FROM PdbDocument pd "
										+ " inner join pdbDocumentContent pdc on pd.documentindex = pdc.documentindex "
										+ " where pd.Name  IN ('Remittance_Letter','Courier_AWB') "
										+ " and pdc.parentfolderindex = (select itemindex from ext_tfo where wi_name =  '"+sWorkitemID+"') ";
								log.info("DHL DOCUMENT query :"+query);
								List<List<String>> docList = formObject.getDataFromDB(query);
								log.info("document count: "+docList.size());
								remarks = sContractRefNo+"|"+docList.get(0).get(0);
								log.info("document remarks: "+remarks);
						}
					String sQuery="select PRO_TRADE_REF_NO, PRODUCT_TYPE "
							+ "from ext_tfo "
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
							String query = "INSERT INTO TFO_DJ_PROTRADE_FCDB_UPDATE (INSERTIONDATETIME,"
									+ "WI_NAME, PRO_TRADE_REF_NO, PRODUCT_CODE, STATUS, REMARKS, WS_NAME, EXECUTION_STATUS) "
									+ "VALUES (SYSDATE,'"+this.sWorkitemID+"','"+proTradeRef+"','"+prodCode+"','"+status+"'"
											+ ",'"+remarks.replaceAll("'", "''")+"','"+this.sActivityName+"','N')";
							log.info("insertIntoNotificationTxnRemittanceLetter query: " + query);
							int b = formObject.saveDataInDB(query);
							log.info("insert status: "+b);
						}
					}
				}catch(Exception e){
					log.error("Exception in insertIntoNotificationTxnRemittanceLetter : ",e);
				}
			}
			//shahbaz
			public String executeDormancyXml() throws Exception {
				log.info("inside executeDormancyXml(): ");
				StringBuilder sInputXML = new StringBuilder();
				String refNo = "";
				String sQuery = "SELECT SEQ_WEBSERVICE.nextval as REFNO from DUAL";
				log.info("seqQuery: " + sQuery);
				List<List<String>> result = formObject.getDataFromDB(sQuery);
				if (result != null && result.size() > 0) {
					refNo = result.get(0).get(0);
				}
				String customerId = formObject.getValue(CUSTOMER_ID).toString();
				log.info("executeDormancyXml(): customerId: " + customerId);
				String outputXml = "";

				try {
					sInputXML.append("<?xml version=\"1.0\"?>").append("\n").append("<APWebService_Input>").append("\n")
							.append("<Option>WebService</Option>").append("\n")
							.append("<EngineName>" + engineName + "</EngineName>").append("\n")
							.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
							.append("<Calltype>CBG</Calltype>").append("\n")
							.append("<CBGCalltype>CustomerPersonalDetailsDA</CBGCalltype>").append("\n")
							.append("<WiName>" + sWorkitemID + "</WiName>").append("\n")
							.append("<REF_NO>" + refNo + "</REF_NO>").append("\n")
							.append("<OLDREF_NO>" + refNo + "</OLDREF_NO>").append("\n")
							.append("<senderId>" + "WMSBPMENG" + "</senderId>").append("\n")
							.append("<CUST_ID>" + customerId + "</CUST_ID>").append("\n")
							.append("</APWebService_Input>");
					log.info("executeDormancyXml inputXML ===> " + sInputXML.toString());
					outputXml = socket.connectToSocket(sInputXML.toString());
					log.info("executeDormancyXml outputputXML ===> =" + outputXml);

				} catch (Exception e) {
					log.error("exception in createInputXML: ", e);
				}
				return outputXml;
			}
			
			
//			Added by Shivanshu ATP-409			
			public Boolean checkLoanValidationPM(String controlName) {
				String msg = "";
				try {
					if("SCF".equalsIgnoreCase(requestCategory) && ("PD".equalsIgnoreCase(requestType) || "PDD".equalsIgnoreCase(requestType))) {
					Date loanValDate = sdf.parse(formObject.getValue(controlName).toString());
					Date currentDate = sdf.parse(sdf.format(new Date())); 
					log.info("loanValDate  :" + loanValDate + " CurrentDate :"+currentDate);
					if ( loanValDate.after(currentDate) && null != loanValDate ) {
				        long difference =  (currentDate.getTime()-loanValDate.getTime())/86400000;
						log.info("difference in Seconds :" + difference + "No of days :"+ Math.abs(difference));
				        if( Math.abs(difference) > 5 ) {
				        	sendMessageHashMap(controlName,"Loan Value Date Cannot be Greater than 5 Days from Current Date");
							return false;
				        }
					}
					return true;
					}
				} catch (Exception ex) {
					log.error("Exception: ",ex);
				}
				return true;
			}
			

//Added by Shivanshu ATP-481
public void savePastDueDecHistory(int pastDue) {
	String groupname = "";
	String personalName = "";
	String decQuery = "";
	String strRemarks = "";
	String sDecision = "";
	String sDuplicatecheck = "";
	String strReasonforAction = "";
	String emailFlag = "";
	try {
		String sQuery = "SELECT PERSONALNAME FROM PDBUSER WHERE UPPER(USERNAME)='"+ sUserName.toUpperCase() + "'";
		List<List<String>> sOutputlist = formObject.getDataFromDB(sQuery);
		if (sOutputlist != null && !sOutputlist.isEmpty()
				&& sOutputlist.get(0).size() > 0) {
			personalName = sOutputlist.get(0).get(0);
		}
		log.info("savePastDueDecHistory : "+ formObject.getValue(CHKBX_SEND_MAIL).toString());
		if (this.sActivityName.equalsIgnoreCase("Logistics Team")|| this.sActivityName.equalsIgnoreCase("Assignment Queue")) {
			emailFlag = "NA";
		} else {
			emailFlag = formObject.getValue(CHKBX_SEND_MAIL).toString();
		}
		if (this.sActivityName.equalsIgnoreCase("PP_MAKER")
				|| this.sActivityName.equalsIgnoreCase("PROCESSING MAKER")
				|| this.sActivityName.equalsIgnoreCase("PROCESSING CHECKER")){
			if( !this.sActivityName.equalsIgnoreCase("SCC")){
			sDuplicatecheck = getDescriptionFromCode(DUP_CHK_CONFIRMATION, formObject.getValue(DUP_CHK_CONFIRMATION).toString());
		 }
		}

		if(pastDue == 1) {
			strRemarks = "Cleared";
		}else if(pastDue == 2) {
			strRemarks = "Approval Received";
		}else if(pastDue == 3){
			strRemarks = "Cancelled";
		}
		
		strReasonforAction = getReasonForAction();

		sDecision = "Customer Past Dues " + strRemarks;

			decQuery = "INSERT INTO " + decHist + "( " + decHistCol
					+ " ) VALUES ('" + sWorkitemID + "','"
					+ this.sUserName.toUpperCase() + "','"
					+ formObject.getValue(PREV_WS).toString() + "','"
					+ sActivityName + "',sysdate,'" + personalName
					+ "','" + groupname + "','" + sDecision + "','"
					+ strReasonforAction + "',sysdate,'" + strRemarks
					+ "','" + formObject.getValue(INITIATOR_BRANCH)
					+ "','" + sDuplicatecheck + "','" + getRouteToVal()
					+ "','" + emailFlag + "')";
		
		log.info("savePastDueDecHistory Query : " + decQuery);
		formObject.saveDataInDB(decQuery);
	} catch (Exception e) {
		log.error("exception in savePastDueDecHistory : " + e, e);
	  }
	}

    //ATP-493 29-07-2024 REYAZ  START	
	public void updateFetchButtonFlagInHybridCase() {
		String processtype = formObject.getValue(PROCESS_TYPE).toString();
		if ("TSLM Front End".equalsIgnoreCase(processtype)
				&& "Yes".equalsIgnoreCase(formObject.getValue(HYBRID_CUSTOMER).toString())) {
			log.info("updateFetchButtonFlagInHybridCase >>>");
			String sQuery2 = "UPDATE EXT_TFO SET FETCH_BTN_FLAG = 'T' WHERE WI_NAME = '" + sWorkitemID + "'";
			int mainCode = formObject.saveDataInDB(sQuery2);
			log.info("updateFetchButtonFlagInHybridCase mainCode: " + mainCode);
		}
	}
	//ATP-493 29-07-2024 REYAZ END
	
//Added by SHivanshu ATP-490 MT798 31-07-2024 START
		public boolean checkAmendmentFrameData() {
			log.info("Inside checkAmendmentFrameData:");
			String outputXML = "";
			String sQuery = "";
			try {
				sQuery = "SELECT COUNT(*) AS COUNT FROM TFO_DJ_AMENDMENT_FRAME_DATA WHERE WINAME = N'" + sWorkitemID + "'";
				List<List<String>> record = formObject.getDataFromDB(sQuery);
				log.info("query for swift amendment : "+sQuery);
				int sCount = Integer.parseInt(record.get(0).get(0));
				log.info("query for swift amendment : "+sCount);

				if (sCount > 0) {
					return false;
				} else {
					return true;
				}
			} catch (Exception e) {
				log.error("exception in checkAmendmentFrameData : " + e, e);
			}
			return true;
		}
//Added by SHivanshu ATP-490 MT798 31-07-2024 END
		
		// Traydstream |atp-518|reyaz|07-10-2024 starts
	public String executeRestAPIJSON(String url, String inputXML,String uUid) throws Exception {
		StringBuilder outputXML = new StringBuilder();
		HttpURLConnection conn = null;
		try {
		log.info("URL: " + url);
			URL urlName = new URL(url);
			conn = (HttpURLConnection) urlName.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("x-fapi-interaction-id", uUid);

			OutputStream os = conn.getOutputStream();
			os.write(inputXML.getBytes());
			os.flush();
		log.info("conn.getResponseCode()===> " + conn.getResponseCode());
			/*if (conn.getResponseCode() != HttpURLConnection.HTTP_OK
					&& conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
			log.info("Failed : HTTP error code :===> " + conn.getResponseCode());
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}*/
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		log.info("Output from server ...\n");
			String out;
			while ((out = br.readLine()) != null) {
				outputXML.append(out);
					// WriteToLog_showpage("Y","RestAPI output===> "+outputXML);
			}
		} catch (MalformedURLException e) {
		log.info("RestAPI exception1===> " + e.getMessage());
			outputXML.append("interaction Id :" + uUid + "  Error : "+e.getMessage());
		} catch (IOException e) {
		log.info("RestAPI exception2===> " + e.getMessage());
			outputXML.append("interaction Id :" + uUid + "  Error : "+e.getMessage());
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
			return outputXML + "";
	}

	public String getTagValueJSON(String jsonData, String tagname) {
		JSONParser parser = new JSONParser();
		try {
			JSONObject jsonObject = (JSONObject) parser.parse(jsonData);
			return searchTag(tagname, jsonObject);

		} catch (Exception e) {
		log.info("getTagValueJSON===> " + e.getMessage());
		}
			return null;
	}

	public String searchTag(String tagname, JSONObject jsonObj) {
		if (jsonObj == null || tagname == null || tagname.isEmpty())
			return null;
		for (Object key : jsonObj.keySet()) {
			String keyStr = (String) key;
			if (keyStr.equals(tagname)) {
				Object object = jsonObj.get(keyStr);
				return (object != null) ? object.toString() : null;
			}
			Object value = jsonObj.get(keyStr);
			if (value instanceof JSONObject) {
				String result = searchTag(tagname, (JSONObject) value);
				if (result != null)
					return result;
			}
			if (value instanceof JSONArray) {
				JSONArray jsonArray = (JSONArray) value;
				for (Object arrayItem : jsonArray) {
					if (arrayItem instanceof JSONObject) {
						String result = searchTag(tagname, (JSONObject) arrayItem);
						if (result != null)
							return result;
					}
				}
			}
		}
		return null;
	}

	public void deleteDirectory(String filelocation) {
		Path filePath = Paths.get(filelocation);
		Path directoryPath = filePath.getParent(); // This gets the parent directory
		try {
				// Delete the file
			Files.deleteIfExists(filePath);
		log.info("File deleted: " + filePath);
			// Delete the directory if it is empty
			if (Files.isDirectory(directoryPath) && Files.list(directoryPath).count() == 0) {
				Files.deleteIfExists(directoryPath);
			log.info("Directory deleted: " + directoryPath);
			} else {
			log.info("Directory not deleted because it is not empty: " + directoryPath);
			}
		} catch (Exception e) {
			log.error("Exception While deleteDirectory " + e.getMessage());
		}
	}
	
	 public String convertToPlainString(String sInputXML){
			StringBuffer outputxml = new StringBuffer();
				log.info("sInputXML.length() " + sInputXML.length());
			sInputXML=sInputXML.replace("SessionId","SessionIdTemp");
		    if (sInputXML.length() > 4000) {
		      int itr = sInputXML.length() / 4000;
		      int mod = sInputXML.length() % 4000;
		      if (mod > 0) {
		        ++itr;
		      }
		      	log.info("itr " + itr);
		      for (int i = 0; i < itr; ++i) {
		        	log.info("output part " + i + 1);
		        if (i == 0) {
		          outputxml.append("TO_NCLOB('" + sInputXML.substring(0, 4000) + "')");
		        }
		        else if (i < itr - 1) {
		          outputxml.append(" || TO_NCLOB('" + sInputXML.substring(4000 * i, 4000 * (i + 1)) + "')");
		        }
		        else
		        {
		          outputxml.append(" || TO_NCLOB('" + sInputXML.substring(4000 * i, sInputXML.length()) + "') ");
		        }
		      }

		    }
		    else
		    {
				//outputxml.append(sInputXML); 
		      outputxml.append("'"+sInputXML+"'");
		    }
			return outputxml.toString();
		    }
	 
		public String callTS(String txnNumber) throws Exception {
			String sOutput = "";
			sOutput = createTSInputXML(txnNumber);
			sOutput = socket.connectToSocket(sOutput);
			log.info("output="+sOutput);
			return sOutput;
		}
		
	 
		public String createTSInputXML(String sTxn) throws Exception {
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
				.append("<channelName>TRAYDSTREAM</channelName>").append("\n")
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
		
		private void moveFile(String NASDocFolderName ,String NASDataMovePath,String channelRefNo){
			String fromDocumentPath = NASDocFolderName+File.separatorChar
					+channelRefNo;
			log.info("fromChannelRefPath: "+fromDocumentPath);
			String toChannelRefPath = NASDataMovePath+channelRefNo;
			log.info("toChannelRefPath: "+toChannelRefPath);
			File file = new File(toChannelRefPath);
			if(!file.exists()){
				log.info("new inside if");
				file.mkdir();
				log.info("new directory created");
			}
			
			File from = new File(fromDocumentPath);
			File to = new File(toChannelRefPath);
			try {
				log.info("new inside try");
				Files.move(from.toPath(), to.toPath(), StandardCopyOption.REPLACE_EXISTING);
				log.info("directory moved ");
				deleteDirectory(fromDocumentPath);
				log.info("original directory deleted ");
			} catch (Exception e) {
				log.error("exception in delete document: ");
				log.error(e);
			}
		}
	// Traydstream |atp-518|reyaz|07-10-2024 ends
}

