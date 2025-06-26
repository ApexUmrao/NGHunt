package com.newgen.ws.service;

import java.awt.List;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
//import java.util.PropertyResourceBundle;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessOrder;

import org.apache.axiom.om.ds.ParserInputStreamDataSource;
import org.apache.axis.session.Session;
import org.apache.axis2.wsdl.WSDLConstants;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import adminclient.OSASecurity;

import com.newgen.ws.util.ConnectSocket;
import com.newgen.ws.util.ExecuteXML;
import com.newgen.omni.jts.srvr.NGDBConnection;
import com.newgen.omni.wf.util.excp.NGException;
import com.newgen.wbg.ws.crs.request.EntityControlPersonsDtls;
import com.newgen.wbg.ws.crs.request.TaxResidenceCountriesDtls;
import com.newgen.wbg.ws.fatca.request.FatcaOwnerShipDtls;
import com.newgen.wbg.ws.kyc.request.AffiliatedEntityInfo;
import com.newgen.wbg.ws.kyc.request.MajorClientsDtls;
import com.newgen.wbg.ws.kyc.request.MajorSuppliersDtls;
import com.newgen.wbg.ws.kyc.request.OwnershipDetails;
import com.newgen.wbg.ws.kyc.request.RISKDetails; //Added by SHivanshu ATP-426
import com.newgen.wbg.ws.kyc.request.RISKInfo;
import com.newgen.wbg.ws.kyc.request.SubsidiariesDetails;
import com.newgen.wbg.ws.kyc.request.UBODetails;
//import com.newgen.wfdesktop.xmlapi.WFCallBroker;
import com.newgen.wfdesktop.xmlapi.WFXmlList;
import com.newgen.wfdesktop.xmlapi.WFXmlResponse;
import com.newgen.ws.exception.WbgAccountOpenningException;
import com.newgen.ws.response.PushPullResponse;
import com.newgen.ws.response.respHeader;
import com.newgen.ws.util.XMLParser;
import com.newgen.ws.request.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.regex.Pattern;

public class AddWBGAOService extends WbgConst {

	protected static Logger log = Logger.getLogger(AddWBGAOService.class);
	PushPullResponse response = null;
	respHeader respHead = null;// new respHeader();
	XMLParser xp = null;// =new XMLParser();
	int start = 0;
	int end = 0;
	int deadend = 0;
	String sValues;
	int refNumber;
	int case_num_found = 0;
 
	public PushPullResponse addWBGAOSrvRes(AccountOpening req) {
 
		try {
			respHead = new respHeader();
			xp = new XMLParser();
			response = new PushPullResponse();
			// initialize Logger
			log.debug("initialize the looger");
			initializeLogger();
			// Load file configuration
			log.debug("initialize the configuration");
			loadConfiguration();
			ExecuteXML.init("WebSphere", sJtsIp, String.valueOf(iJtsPort));
			// Load Resource Bundle
			log.debug("loading the resouce bundle");
		//	loadResourceBundle();
			// Fetching request parameters
			log.debug("calling fetch parameter");
			setRequestParameter(req, 0);
			serviceRequestAuditLog();
			initializeSocket();
			// fetchRequestParameters(req,0);
			log.debug("validating input mandatory fields");
			boolean bValidate = validateInputXML(req, 0);
			log.debug("validating input mandatory fields bValidate >> "
					+ bValidate);
			if (bValidate == true) {

				sUserDBId = connectToServer();
				if (sUserDBId.isEmpty()) {
					setErrorResponseMsg("-11088", "");
					throw new WbgAccountOpenningException("-11088",
							getStatuMessageFromCode("-11088"), "");
				}
				try{
					xDocTrc = excecuteFetchDocTracker(leadRefNumber, sysRefNumber,
							username);
					log.info("xDocTrc: "+xDocTrc);
					log.info("Bfr calling validateDocTrcRes");
					validateDocTrcRes(xDocTrc);
					log.info("After calling validateDocTrcRes");
					log.info("Bfr calling checkDocLList");
					log.info("Bfr calling checkDocLList xDocTrc: "+xDocTrc);
					String docVal = checkDocLList(xDocTrc);
					log.info("After calling checkDocLList");
					log.info("docVal: "+docVal);
					if (!checkNullval(docVal)) {
						rollBackData("TMPDOC");
						setErrorResponseMsg("-11087", docVal);
						throw new WbgAccountOpenningException("-11087",
								getStatuMessageFromCode("-11060"), docVal);
					}
				} catch(Exception e){
				log.error("Exception in excecuteFetchDocTracker",e);	
				}
				log.info("NTBEntityFound"+NTBEntityFound);

				if(NTBEntityFound)
				{
					if("".equalsIgnoreCase(compCB1Code))
					{ 
						log.info("delete rollback compCB1Code");
						rollBackData("ALL");
						setErrorResponseMsg("-11091", "");
						throw new WbgAccountOpenningException("-11091",
								getStatuMessageFromCode("-11091"), "");
					
					}else if ("".equalsIgnoreCase(compCB2Division))
					{
						log.info("delete rollback compCB2Division");
						rollBackData("ALL");
						setErrorResponseMsg("-11092", "");
						throw new WbgAccountOpenningException("-11092",
								getStatuMessageFromCode("-11092"), "");
					
					}else if ("".equalsIgnoreCase(compCB2GrpId))
					{
						log.info("delete rollback compCB2GrpId");
						rollBackData("ALL");
						setErrorResponseMsg("-11093", "");
						throw new WbgAccountOpenningException("-11093",
								getStatuMessageFromCode("-11093"), "");
					
					}else if ("".equalsIgnoreCase(compCB2PeerId))
					{
						log.info("delete rollback compCB2PeerId");
						rollBackData("ALL");
						setErrorResponseMsg("-11094", "");
						throw new WbgAccountOpenningException("-11094",
								getStatuMessageFromCode("-11094"), "");
					
					}else if ("".equalsIgnoreCase(compCB2Code))
					{
						log.info("delete rollback compCB2PeerId");
						rollBackData("ALL");
						setErrorResponseMsg("-11095", "");
						throw new WbgAccountOpenningException("-11095",
						       getStatuMessageFromCode("-11095"), "");
					
					}else if ("".equalsIgnoreCase(Website))
					{				
						log.info("delete rollback compCB2PeerId");
						rollBackData("ALL");
						setErrorResponseMsg("-11096", "");
						throw new WbgAccountOpenningException("-11096",
						       getStatuMessageFromCode("-11096"), "");
					 
					}else if ("".equalsIgnoreCase(OffNumber))
					{
						log.info("delete rollback compCB2PeerId");
						rollBackData("ALL");
						setErrorResponseMsg("-11097", "");
						throw new WbgAccountOpenningException("-11097",
						       getStatuMessageFromCode("-11097"), "");
					 
					}else if ("".equalsIgnoreCase(OffNumber2))
					{
						log.info("delete rollback compCB2PeerId");
						rollBackData("ALL");
						setErrorResponseMsg("-11098", "");
						throw new WbgAccountOpenningException("-11098",
						       getStatuMessageFromCode("-11098"), "");
					 
					}else if ("".equalsIgnoreCase(Mobile))
					{
						log.info("delete rollback compCB2PeerId");
						rollBackData("ALL");
						setErrorResponseMsg("-11099", "");
						throw new WbgAccountOpenningException("-11099",
						       getStatuMessageFromCode("-11099"), "");
						
					}else if ("".equalsIgnoreCase(Email))
					{
						log.info("delete rollback compCB2PeerId");
						rollBackData("ALL");
						setErrorResponseMsg("-11100", "");
						throw new WbgAccountOpenningException("-11100",
						       getStatuMessageFromCode("-11100"), "");
						
					}else if ("".equalsIgnoreCase(compProfitCenter))
					{
						log.info("delete rollback compCB2PeerId");
						rollBackData("ALL");
						setErrorResponseMsg("-11101", "");
						throw new WbgAccountOpenningException("-11101",
						       getStatuMessageFromCode("-11101"), "");
					 
						
					}
					
				}
				
				
				
				
				
				sOutputXML = insertWBGHeader();
				xp.setInputXML(sOutputXML);
				if (!xp.getValueOf("MainCode").trim()
						.equalsIgnoreCase(SUCCESS_STATUS)) {
					setErrorResponseMsg("-11060", xp.getValueOf("Output")
							.trim());
					throw new WbgAccountOpenningException("-11060",
							getStatuMessageFromCode("-11060"), xp.getValueOf("Output")
									.trim());
				}
				sOutputXML = insertWBGCompany();
				xp.setInputXML(sOutputXML);
				if (!xp.getValueOf("MainCode").trim()
						.equalsIgnoreCase(SUCCESS_STATUS)) {
					rollBackData("HEAD");
					setErrorResponseMsg("-11061", xp.getValueOf("Output")
							.trim());
					throw new WbgAccountOpenningException("-11061",
							getStatuMessageFromCode("-11061"), xp.getValueOf("Output")
									.trim());
				}
				sOutputXML = fetchAusCustomerDtls(req, 0);
				log.info("sOutputXML :"+sOutputXML);
				xp.setInputXML(sOutputXML);
				if (!xp.getValueOf("MainCode").trim()
						.equalsIgnoreCase(SUCCESS_STATUS)) {
					rollBackData("AUS");
					setErrorResponseMsg("-11062", "");
					throw new WbgAccountOpenningException("-11062",
							getStatuMessageFromCode("-11062"), "");
				}
				if (req.getCustomers() != null && !"".equalsIgnoreCase(req.getCustomers().toString())) {
					if (req.getCustomers().getCutomerPersonalDtls() != null &&
							!"".equalsIgnoreCase(req.getCustomers().getCutomerPersonalDtls().toString())) {
						log.info("Eida No"+eidaNo);
						if("".equalsIgnoreCase(eidaNo) && requestClassification.equalsIgnoreCase("CARD")){
							rollBackData("ALL");
							setErrorResponseMsg("-4071","");
							  throw new WbgAccountOpenningException("-4071",
							  getStatuMessageFromCode("-4071"), "");
						}
						log.info("Request Classification"+requestClassification);
						if (requestClassification.equalsIgnoreCase("CARD") && (custID!=null || !"".equalsIgnoreCase(custID))
								&&  (custUpgradeflg!=null || !"".equalsIgnoreCase(custUpgradeflg)) 
								&& (embossName == null || "".equalsIgnoreCase(embossName))){
							  rollBackData("ALL");
						      setErrorResponseMsg("-4067","");
							  throw new WbgAccountOpenningException("-4067",
							  getStatuMessageFromCode("-4067"), "");
						}
						log.info("RM Code"+rmCode);
						if (requestClassification.equalsIgnoreCase("CARD") && (rmCode == null || "".equalsIgnoreCase(rmCode))){
							  rollBackData("ALL");
							  setErrorResponseMsg("-4070","");
							  throw new WbgAccountOpenningException("-4070",
							  getStatuMessageFromCode("-4070"), "");
						}
					}
				}
				log.info("NTBAusFound"+NTBAusFound);
				if (NTBAusFound && "".equalsIgnoreCase(profitCenter))
				{
					rollBackData("AUS");
					rollBackData("ALL");
					setErrorResponseMsg("-11102", "");
					throw new WbgAccountOpenningException("-11102",
							getStatuMessageFromCode("-11102"),"");
				}
				sOutputXML = fetchWMSAccount(req, 0);
				xp.setInputXML(sOutputXML);
				if (!xp.getValueOf("MainCode").trim()
						.equalsIgnoreCase(SUCCESS_STATUS)) {
					rollBackData("COMP");
					setErrorResponseMsg("-11065", xp.getValueOf("Output")
							.trim());
					throw new WbgAccountOpenningException("-11065",
							getStatuMessageFromCode("-11065"), xp.getValueOf("Output")
									.trim());
				}
				sOutputXML = fetchWMSUBODetails(req, 0);
				xp.setInputXML(sOutputXML);
				if (!xp.getValueOf("MainCode").trim()
						.equalsIgnoreCase(SUCCESS_STATUS)) {
					rollBackData("ACC");
					setErrorResponseMsg("-11066", xp.getValueOf("Output")
							.trim());
					throw new WbgAccountOpenningException("-11066",
							getStatuMessageFromCode("-11066"), xp.getValueOf("Output")
									.trim());
				}
				// CR2019 handled keycontatcts array
				sOutputXML = fetchKeyContactDetails(req, 0);
				xp.setInputXML(sOutputXML);
				if (!xp.getValueOf("MainCode").trim()
						.equalsIgnoreCase(SUCCESS_STATUS)) {
					rollBackData("KEYCONTACTS");
					setErrorResponseMsg("-11089", xp.getValueOf("Output")
							.trim());
					throw new WbgAccountOpenningException("-11089",
							getStatuMessageFromCode("-11089"), xp.getValueOf("Output")
									.trim());
				}
				// end

				// sharan added new method
				// String ApplcnAtt = fetchAttributes(req, 0);
				// log.debug("fetchAttributes ApplcnAtt "+ApplcnAtt);
				// end

				sOutputXML = fetchFatcaOwnership(req, 0);
				xp.setInputXML(sOutputXML);
				if (!xp.getValueOf("MainCode").trim()
						.equalsIgnoreCase(SUCCESS_STATUS)) {
					rollBackData("FATCAOWNR");
					setErrorResponseMsg("-11067", xp.getValueOf("Output")
							.trim());
					throw new WbgAccountOpenningException("-11067",
							getStatuMessageFromCode("-11067"), xp.getValueOf("Output")
									.trim());
				}

				sOutputXML = fetchCntrlPerTaxResCntryDtls(req, 0);
				xp.setInputXML(sOutputXML);
				if (!xp.getValueOf("MainCode").trim()
						.equalsIgnoreCase(SUCCESS_STATUS)) {
					rollBackData("CRSTAX");
					setErrorResponseMsg("-11068", xp.getValueOf("Output")
							.trim());
					throw new WbgAccountOpenningException("-11068",
							getStatuMessageFromCode("-11068"), xp.getValueOf("Output")
									.trim());
				}

				sOutputXML = fetchCrsTaxResCntryDtls(req, 0);
				xp.setInputXML(sOutputXML);
				if (!xp.getValueOf("MainCode").trim()
						.equalsIgnoreCase(SUCCESS_STATUS)) {
					rollBackData("CRSTAX");
					setErrorResponseMsg("-11068", xp.getValueOf("Output")
							.trim());
					throw new WbgAccountOpenningException("-11068",
							getStatuMessageFromCode("-11068"), xp.getValueOf("Output")
									.trim());
				}
				sOutputXML = fetchCntryControlPersonDtls(req, 0);
				xp.setInputXML(sOutputXML);
				if (!xp.getValueOf("MainCode").trim()
						.equalsIgnoreCase(SUCCESS_STATUS)) {
					rollBackData("CP");
					setErrorResponseMsg("-11069", xp.getValueOf("Output")
							.trim());
					throw new WbgAccountOpenningException("-11069",
							getStatuMessageFromCode("-11069"), xp.getValueOf("Output")
									.trim());
				}
				sOutputXML = insertKYCGeneric(req, 0);
				xp.setInputXML(sOutputXML);
				if (!xp.getValueOf("MainCode").trim()
						.equalsIgnoreCase(SUCCESS_STATUS)) {
					rollBackData("KYC");
					setErrorResponseMsg("-11064", xp.getValueOf("Output")
							.trim());
					throw new WbgAccountOpenningException("-11064",
							getStatuMessageFromCode("-11064"), xp.getValueOf("Output")
									.trim());
				}
				sOutputXML = fetchOwnershipDtls(req, 0);
				xp.setInputXML(sOutputXML);
				if (!xp.getValueOf("MainCode").trim()
						.equalsIgnoreCase(SUCCESS_STATUS)) {
					rollBackData("KYCOWNERSHIPINFO");
					setErrorResponseMsg("-11070", xp.getValueOf("Output")
							.trim());
					throw new WbgAccountOpenningException("-11070",
							getStatuMessageFromCode("-11070"), xp.getValueOf("Output")
									.trim());
				}
				sOutputXML = fetchSubsidiariesInfo(req, 0);
				xp.setInputXML(sOutputXML);
				if (!xp.getValueOf("MainCode").trim()
						.equalsIgnoreCase(SUCCESS_STATUS)) {
					rollBackData("SUBSIDIARIES");
					setErrorResponseMsg("-11071", xp.getValueOf("Output")
							.trim());
					throw new WbgAccountOpenningException("-11071",
							getStatuMessageFromCode("-11071"), xp.getValueOf("Output")
									.trim());
				}
				sOutputXML = fetchKYCUboInfo(req, 0);
				xp.setInputXML(sOutputXML);
				if (!xp.getValueOf("MainCode").trim()
						.equalsIgnoreCase(SUCCESS_STATUS)) {
					rollBackData("KYCUBODETAILS");
					setErrorResponseMsg("-11072", xp.getValueOf("Output")
							.trim());
					throw new WbgAccountOpenningException("-11072",
							getStatuMessageFromCode("-11072"), xp.getValueOf("Output")
									.trim());
				}
				sOutputXML = fetchMajorClientsInfo(req, 0);
				xp.setInputXML(sOutputXML);
				if (!xp.getValueOf("MainCode").trim()
						.equalsIgnoreCase(SUCCESS_STATUS)) {
					rollBackData("MAJORCLIENT");
					setErrorResponseMsg("-11073", xp.getValueOf("Output")
							.trim());
					throw new WbgAccountOpenningException("-11073",
							getStatuMessageFromCode("-11073"), xp.getValueOf("Output")
									.trim());
				}
				sOutputXML = fetchMajorSuppliersInfo(req, 0);
				xp.setInputXML(sOutputXML);
				if (!xp.getValueOf("MainCode").trim()
						.equalsIgnoreCase(SUCCESS_STATUS)) {
					rollBackData("MAJORSUPP");
					setErrorResponseMsg("-11074", xp.getValueOf("Output")
							.trim());
					throw new WbgAccountOpenningException("-11074",
							getStatuMessageFromCode("-11074"), xp.getValueOf("Output")
									.trim());
				}
				sOutputXML = fetchAffiliatedEntityDtls(req, 0);
				xp.setInputXML(sOutputXML);
				if (!xp.getValueOf("MainCode").trim()
						.equalsIgnoreCase(SUCCESS_STATUS)) {
					rollBackData("AFFILIATE");
					setErrorResponseMsg("-11075", xp.getValueOf("Output")
							.trim());
					throw new WbgAccountOpenningException("-11075",
							getStatuMessageFromCode("-11075"), xp.getValueOf("Output")
									.trim());
				}
				//Added by Shivanshu FOR insertion in KYCRisk Details ATP-426
				sOutputXML = fetchKYCRiskDetails(req, 0);
				xp.setInputXML(sOutputXML);
				if (!xp.getValueOf("MainCode").trim()
						.equalsIgnoreCase(SUCCESS_STATUS)) {
					rollBackData("KYCRISK");
					setErrorResponseMsg("-11103", xp.getValueOf("Output")
							.trim());
					throw new WbgAccountOpenningException("-11103",
							getStatuMessageFromCode("-11103"), xp.getValueOf("Output")
									.trim());
				}
				//Added by Shivanshu FOR ACTIVE ACCOUNT FLAG
				if ("".equalsIgnoreCase(ActiveAccountFlag)){
					log.info("delete rollback ActiveAccountFlag");
					rollBackData("ALL");
					setErrorResponseMsg("-4072", "");
					throw new WbgAccountOpenningException("-4072",
							getStatuMessageFromCode("-4072"), "");

				}
				
			//Sanal Grover 02062022 
		HashMap<String,ArrayList> custDoc  = getMandatoryDocument(xDocTrc);
				log.debug("custDoc -> "+ custDoc);
		if (req.getCustomers() != null && !"".equalsIgnoreCase(req.getCustomers().toString())) {
		   if (req.getCustomers().getCutomerPersonalDtls() != null &&
				!"".equalsIgnoreCase(req.getCustomers().getCutomerPersonalDtls().toString())) {
			   if(requestClassification.equalsIgnoreCase("CARD")){
					for(Map.Entry<String, ArrayList> entry : custDoc.entrySet()){
						String name = entry.getKey();
						ArrayList<String> docList = entry.getValue();
						boolean passport = false;
						boolean eida = false;
						for(String docName : docList){
							log.debug("custDoc -> docName -> "+ docName);
	
							if(docName.equals("EIDA")){
								eida = true;
							} else if(docName.equals("Passport")){
								passport = true;
							}					
							if(passport && eida){
								break;
							}
						}					
							if(!eida){
								rollBackData("ALL");
								setErrorResponseMsg("-4068", "");
								throw new WbgAccountOpenningException("-4068",
										getStatuMessageFromCode("-4068"), xp.getValueOf("Output")
												.trim());
							}
							if(!passport){
								rollBackData("ALL");
								setErrorResponseMsg("-4069", "");
								throw new WbgAccountOpenningException("-4069",
										getStatuMessageFromCode("-4069"), xp.getValueOf("Output")
												.trim());
							}
							
				      }
			      }
		      }
		 }
	
				strUploadWi = upldWorkitem();
				xp.setInputXML(strUploadWi);
				if (xp.getValueOf("MainCode").trim()
						.equalsIgnoreCase(SUCCESS_STATUS)) {
					log.info("inside success ");
					String wiName = xp.getValueOf("ProcessInstanceId");
					log.info("wiName"+wiName);
					FolderIndex = xp.getValueOf("FolderIndex");
					setSuccessResponse(xp.getValueOf("ProcessInstanceId"));
					insertDataIntoProcessTab(xp.getValueOf("ProcessInstanceId"));
					insertDecAuditTrail(xp.getValueOf("ProcessInstanceId"));
					// Added by reyaz 29-11-2022
					if("YES".equalsIgnoreCase(reworkValue)){
					   updateLeadLastReturnValue(wiName);
					}
					new Thread(){
						public void run(){
							//Commenting for AutoRepair Shivanshu ATP-521
//							    AddWBGAOService.log.info("Before generateEventTrsd ");
//				                AddWBGAOService.this.generateEventTrsd(wiName);
				                AddWBGAOService.log.info("After generateEventTrsd " + wiName);
				                AddWBGAOService.this.WMUnlockWorkItem(wiName);
				                AddWBGAOService.log.info("After WMUnlockWorkItem ");
				                AddWBGAOService.this.generateEvent(wiName);
				                AddWBGAOService.log.info("After generateEvent ");
								// AutoRepair Shivanshu ATP-521
				        		WMGetWorkItem(wiName, 1);
								WMCompleteWorkItem(wiName, 1);

						}
					}.start();
						
				} else {
					rollBackData("ALL");
					setErrorResponseMsg("-11080", xp.getValueOf("Output")
							.trim());
					throw new WbgAccountOpenningException("-11080",
							getStatuMessageFromCode("-11080"), xp.getValueOf("Output")
									.trim());
				}

			} else if (bValidate == false) {
				return response;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in Account Opening: ", e);
		} finally {
			deleteConnection();
			mReqParamMap.clear();
			aValuesNotAllowed.clear();
		}
		return response;
	}


	public String WMUnlockWorkItem(String processInstanceId) {
		try{
			StringBuilder sInputXML = new StringBuilder(); 
			sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
			.append("<WMUnlockWorkItem_Input>").append("\n")
			.append("<Option>WMUnlockWorkItem</Option>").append("\n")
			.append("<SessionId>" + sUserDBId + "</SessionId>").append("\n")
			.append("<EngineName>" + sCabinetName + "</EngineName>").append("\n")
			.append("<UnlockOption>W</UnlockOption>").append("\n")
			.append("<ProcessInstanceId>" + processInstanceId + "</ProcessInstanceId>").append("\n")
			.append("<WorkitemId>1</WorkitemId>").append("\n")
			.append("</WMUnlockWorkItem_Input>");
			String outputXML = executeAPI(sInputXML.toString());
			log.debug("WMUnlockWorkItem OutputXML ===>" + outputXML);
			return outputXML;
		} catch (Exception ex) {
			log.debug("Error in WMUnlockWorkItem(): " + ex.getMessage());
		}
		return "";
	}


	private void initializeSocket() {
		try {
			log.debug("Getting  initializeSocket");
			String sQuery = "SELECT (SELECT VALUE FROM BPM_SERVICE_CONFIG WHERE KEY = 'SOCKETIP') IP,(SELECT VALUE FROM BPM_SERVICE_CONFIG WHERE KEY = 'SOCKETPORT') PORT FROM DUAL";
			sInputXML = getDBSelectInput(sQuery, sCabinetName, sUserDBId);
			// log.debug("sInputXML is "+sInputXML);
			try {
				sOutputXML = executeAPI(sInputXML);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// log.debug("sOutputXML is "+sOutputXML);
			xp.setInputXML(sOutputXML);
			if (xp.getValueOf("MainCode").trim().equalsIgnoreCase(SUCCESS_STATUS)) {
				String socketIP =  xp.getValueOf("IP");
				String socketPort =  xp.getValueOf("PORT");
				socket = ConnectSocket.getReference(socketIP, Integer.parseInt(socketPort));
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			log.debug("Error in initializeSocket" + e.getMessage());
			}
	}


	private String getDate() {
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal1 = Calendar.getInstance();
		return dateFormat1.format(cal1.getTime());

	}

	private boolean isValidDate(String inDate) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setLenient(false);
		try {
			if (inDate.length() > 9) {
				if (inDate != null) {
					dateFormat.parse(inDate.trim());
				}
			} else {
				return false;
			}

		} catch (Exception pe) {
			log.debug("error::" + pe.getMessage());
			return false;
		}
		return true;
	}

	private String getDBInsertInput(String sTableName, String sColNames,
			String sValues, String sEngineName, String sSessionId) {
		return "<?xml version=\"1.0\"?><APInsert_Input><Option>APInsert</Option><TableName>"
				+ sTableName
				+ "</TableName>"
				+ "<ColName>"
				+ sColNames
				+ "</ColName>"
				+ "<Values>"
				+ sValues
				+ "</Values>"
				+ "<EngineName>"
				+ sEngineName
				+ "</EngineName>"
				+ "<SessionId>"
				+ sSessionId
				+ "</SessionId>"
				+ "</APInsert_Input>";
	}

	private static void initializeLogger() {
		Properties p = new Properties();
		String log4JPropertyFile = System.getProperty("user.dir")
				+ System.getProperty("file.separator") + "WebServiceConf/WBG"
				+ System.getProperty("file.separator") + "log4j.properties";
		log.debug("log4JPropertyFile" + log4JPropertyFile);
		log.debug("Path of log4j file: " + log4JPropertyFile);
		try {
			p.load(new FileInputStream(log4JPropertyFile));
			PropertyConfigurator.configure(p);
			log.debug("Logger is configured successfully");

		} catch (IOException e) {
			log.debug("Exception in initializeLogger: " + e);
		}
	}

	private static void loadConfiguration() {
		Properties p = new Properties();

		String sConfigFile = System.getProperty("user.dir")
				+ System.getProperty("file.separator") + "WebServiceConf/WBG"
				+ System.getProperty("file.separator")
				+ "WBG_config.properties";
		try {
			p.load(new FileInputStream(sConfigFile));
			log.debug("Configuration loaded successfuly");
			sCabinetName = p.getProperty("CabinetName");
			sJtsIp = p.getProperty("JtsIp");
			iJtsPort = Integer.parseInt(p.getProperty("JtsPort"));
			sUsername = p.getProperty("Username");
			sIniActName = p.getProperty("DelIniActName");
			sIniActId = p.getProperty("DelIniActId");
			sProcessDefId = p.getProperty("ProcessDefId");
			iUserCount = Integer.parseInt(p.getProperty("LoggedInUserCount"));
			optDocList = p.getProperty("OptDocList");
			sPassEnc = p.getProperty("PASSENC");
			trsdURL = p.getProperty("TRSDURL");
			if ("Y".equalsIgnoreCase(sPassEnc)) {
				sPassword = decryptPassword(p.getProperty("Password"));
			} else if ("N".equalsIgnoreCase(sPassEnc)) {
				sPassword = p.getProperty("Password");
			}
			isCallDocTrc = p.getProperty("IS_CALL_DOCTRC").trim();
			log.debug("CabinetName: " + sCabinetName + ", JtsIp: " + sJtsIp
					+ ", JtsPort: " + iJtsPort);
		//	String socketIP = p.getProperty("socketIp");
		//	String socketPort = p.getProperty("socketPort");
			
		} catch (IOException e) {
			log.error("Failure in loading configuration:", e);
		}
	}

	private static String decryptPassword(String pass) {
		log.debug("pass is" + pass);
		int len = pass.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[(i / 2)] = (byte) ((Character.digit(pass.charAt(i), 16) << 4) + Character
					.digit(pass.charAt(i + 1), 16));
		}
		log.debug("data is :" + data);
		String password = OSASecurity.decode(data, "UTF-8");

		return password;
	}

	private static String getConnectInput(String sUserName, String sUserPassword) {
		return "<?xml version=\"1.0\"?><NGOConnectCabinet_Input><Option>NGOConnectCabinet</Option><CabinetName>"
				+ sCabinetName
				+ "</CabinetName>"
				+ "<UserName>"
				+ sUserName
				+ "</UserName>"
				+ "<UserPassword>"
				+ sUserPassword
				+ "</UserPassword>"
				+ "<WebserviceCall>Y</WebserviceCall>"
				+ "<UserType>U</UserType>"
				+ "<UserExist>Y</UserExist>"
				+ "<Locale></Locale>" + "</NGOConnectCabinet_Input>";
	}

	private String executeAPI(String sInputXML1) throws IOException, Exception {
		log.debug("Inside Execute API");
		sOutputXML = "";
		long i = System.currentTimeMillis();
		log.debug("i is" + i);
		log.debug("InputXML: " + sInputXML1);
		//sOutputXML = WFCallBroker.execute(sInputXML1, sJtsIp, iJtsPort, 0);
		sOutputXML = ExecuteXML.executeXML(sInputXML1);
		log.debug("After sOutputXML" + sOutputXML);
		long j = System.currentTimeMillis();
		iTotalTime = iTotalTime + (j - i);
		log.debug("Total time taken till now: " + iTotalTime + " ms.");
		return sOutputXML;
	}

	private String executeConnectionAPI(String sInputXML1) throws IOException,
			Exception {
		sOutputXML = "";
	//	sOutputXML = WFCallBroker.execute(sInputXML, sJtsIp, iJtsPort, 0);
		sOutputXML = ExecuteXML.executeXML(sInputXML);
		return sOutputXML;
	}

	private String getDBSelectInput(String sQuery, String sEngineName,
			String sSessionId) {
		return "<?xml version=\1.0\"?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><EngineName>"
				+ sEngineName
				+ "</EngineName> "
				+ "<SessionId>"
				+ sSessionId
				+ "</SessionId>"
				+ "<QueryString>"
				+ sQuery
				+ "</QueryString>"
				+ "</APSelectWithColumnNames_Input>";
	}

	private String getDBDeleteInput(String sTableName, String sWhere,
			String sEngineName, String sSessionId) {
		return "<?xml version=\"1.0\"?><APDelete_Input><Option>APDelete</Option><TableName>"
				+ sTableName
				+ "</TableName>"
				+ "<WhereClause>"
				+ sWhere
				+ "</WhereClause>"
				+ "<EngineName>"
				+ sEngineName
				+ "</EngineName>"
				+ "<SessionId>"
				+ sSessionId
				+ "</SessionId>"
				+ "</APDelete_Input>";
	}

	private String connectToServer() throws WbgAccountOpenningException,
			IOException, Exception {
		log.debug("Inside connectToServer");
		String sUserDBid = "";
		int count = countCurrentCunnections();

		if (count >= iUserCount) {
			response.setStatus("Fail");
			response.setErrorCode("-11057");
			response.setDescription(getStatuMessageFromCode("-11057"));
			throw new WbgAccountOpenningException("-11057",
					getStatuMessageFromCode("-11057"), "");
		}
		// check for existing connection of same user
		sUserDBid = getExistingConnection();
		log.debug("sUserDBid is:" + sUserDBid);
		// make a new connection if existing not present
		if (sUserDBid.equalsIgnoreCase("")) {
			sUserDBid = makeNewConnection();
		}
		if (sUserDBid.equalsIgnoreCase("")) {
			response.setStatus("Fail");
			response.setErrorCode("-11060");
			response.setDescription(getStatuMessageFromCode("-11060"));
			throw new WbgAccountOpenningException("-11060",
					getStatuMessageFromCode("-11060"), "");
		}
		// make entry in connection table
		updateConnections(sUserDBid);

		return sUserDBid;
	}

	private int countCurrentCunnections() throws WbgAccountOpenningException,
			IOException, Exception {
		log.debug("Getting  number of current connections");
		String sQuery = "SELECT COUNT(1) AS COUNT FROM " + CONNECTIONS;
		sInputXML = getDBSelectInput(sQuery, sCabinetName, sUserDBId);
		// log.debug("sInputXML is "+sInputXML);
		sOutputXML = executeAPI(sInputXML);
		// log.debug("sOutputXML is "+sOutputXML);
		xp.setInputXML(sOutputXML);
		if (xp.getValueOf("MainCode").trim().equalsIgnoreCase(SUCCESS_STATUS)) {
			int count = Integer.parseInt(xp.getValueOf("COUNT"));
			return count;
		} else {
			response.setStatus("Fail");
			response.setErrorCode("-11058");
			response.setDescription(getStatuMessageFromCode("-11058"));
			throw new WbgAccountOpenningException("-11058",
					getStatuMessageFromCode("-11058"), xp.getValueOf("Output").trim());
		}
	}

	private String makeNewConnection() throws WbgAccountOpenningException,
			IOException, Exception {
		log.debug("Inside makeNewConnection method");
		String sUserDBid = "";
		sInputXML = getConnectInput(sUsername, sPassword);
		sOutputXML = executeConnectionAPI(sInputXML);
		xp.setInputXML(sOutputXML);
		String sStatus = xp.getValueOf("Status");
		log.debug("sStatus is " + sStatus);
		String sError = xp.getValueOf("Error");
		log.debug("sError is " + sError);
		if (sStatus.trim().equalsIgnoreCase(SUCCESS_STATUS)) {
			sUserDBid = xp.getValueOf("UserDBId").trim();
			log.debug("Connected to server: " + sUserDBid);
		} else if (sStatus.trim().equalsIgnoreCase("-50167")) {
			log.debug("User already logged in so calling getExistingConnection method ");
			sUserDBid = getExistingConnection();
		} else {
			response.setStatus("Fail");
			response.setErrorCode("-4003");
			response.setDescription(getStatuMessageFromCode("-4003"));
			throw new WbgAccountOpenningException(sStatus,
					"Error in connecting to server", sError);
		}
		return sUserDBid;
	}

	private void deleteConnection() {
		log.debug("deleting entry from connection table");
		if (!sUserDBId.isEmpty()) {
			sInputXML = getDBDeleteInput(CONNECTIONS, "LEAD_REFNO='"
					+ leadRefNumber + "'", sCabinetName, sUserDBId);
			try {
				log.debug("Inside try");
				executeAPI(sInputXML);
				log.debug(" After execute API");
			} catch (IOException e) {
				log.debug("Inside IOException");
				log.error("IOException in delete connection", e);
				log.debug(" After IOException");
			} catch (Exception e) {
				log.debug("Inside Exception");
				log.error("Exception in delete connection", e);
				log.debug("After Exception");
			}
		}

	}

	private String getExistingConnection() throws WbgAccountOpenningException,
			IOException, Exception {
		log.debug("Inside Existing connection method");
		String sUserDBid = "";
		String sQuery = "SELECT RANDOMNUMBER FROM PDBCONNECTION WHERE USERINDEX IN (SELECT USERINDEX FROM PDBUSER WHERE UPPER(USERNAME)='"
				+ sUsername.toUpperCase() + "')";
		sInputXML = getDBSelectInput(sQuery, sCabinetName, sUserDBid);

		sOutputXML = executeAPI(sInputXML);
		xp.setInputXML(sOutputXML);
		if (xp.getValueOf("MainCode").trim().equalsIgnoreCase(SUCCESS_STATUS)) {
			if (xp.getValueOf("TotalRetrieved").trim().equalsIgnoreCase("0")) {
				return sUserDBid;
			} else {
				sUserDBid = xp.getValueOf("RANDOMNUMBER");
				log.debug("Connection fetched from pdbconnection: " + sUserDBid);
				// sQuery="SELECT USERINDEX FROM PDBUSER WHERE UPPER(USERNAME)='"+sUsername.toUpperCase()+"' AND PASSWORD='"+sPassword+"'";
				sQuery = "SELECT USERINDEX FROM PDBUSER WHERE UPPER(USERNAME)='"
						+ sUsername.toUpperCase() + "'";
				sInputXML = getDBSelectInput(sQuery, sCabinetName, sUserDBid);
				// log.debug("InputXml of getExistingConnection:"+sInputXML);
				sOutputXML = executeAPI(sInputXML);
				// log.debug("OutputXml of getExistingConnection:"+sOutputXML);
				xp.setInputXML(sOutputXML);
				if (xp.getValueOf("MainCode").trim()
						.equalsIgnoreCase(SUCCESS_STATUS)) {
					if (xp.getValueOf("TotalRetrieved").trim()
							.equalsIgnoreCase("0")) {
						response.setStatus("Fail");
						response.setErrorCode("-11056");
						response.setDescription(getStatuMessageFromCode("-11056"));
						throw new WbgAccountOpenningException("-11056",
								getStatuMessageFromCode("-11056"), "");
					} else {
						return sUserDBid;
					}
				} else {
					response.setStatus("Fail");
					response.setErrorCode("-11055");
					response.setDescription(getStatuMessageFromCode("-110055"));
					throw new WbgAccountOpenningException("-11055",
							getStatuMessageFromCode("-11055"), xp.getValueOf("Output")
									.trim());
				}

			}
		} else {
			response.setStatus("Fail");
			response.setErrorCode("-11054");
			response.setDescription(getStatuMessageFromCode("-11054"));
			throw new WbgAccountOpenningException("-11054",
					getStatuMessageFromCode("-11054"), xp.getValueOf("Output").trim());
		}
	}

/*	private void loadResourceBundle() {

		log.debug("inside loadResourceBundle");
		pCodes = PropertyResourceBundle.getBundle("com.newgen.ws.config.statusCodes");
		log.debug("inside loadResourceBundle after getBundle ");
		if (pCodes == null) {
			log.error("Error in loading status codes");
		} else {
			log.error("Status Codes loaded successfully");
		}
	}
*/
	private void updateConnections(String sUserDBid)
			throws WbgAccountOpenningException, IOException, Exception {
		log.debug("updating connection table");
		sInputXML = getDBInsertInput(CONNECTIONS,
				"LEAD_REFNO,USERNAME,SESSIONID", "'" + leadRefNumber + "','"
						+ sUsername + "','" + sUserDBid + "'", sCabinetName,
				sUserDBid);
		sOutputXML = executeAPI(sInputXML);
		xp.setInputXML(sOutputXML);
		if (!xp.getValueOf("MainCode").trim().equalsIgnoreCase(SUCCESS_STATUS)) {
			response.setStatus("Fail");
			response.setErrorCode("-11059");
			response.setDescription(getStatuMessageFromCode("-11059"));
			throw new WbgAccountOpenningException("-11059",
					getStatuMessageFromCode("-11059"), xp.getValueOf("Output").trim());
		}
	}
	//Changes by Shivanshu ATP-426
	private String fetchWMSUBODetails(AccountOpening req, int i) {
		try {
			if (req.getUBOwnerDetails() != null) {
				if (req.getUBOwnerDetails().getUBOInfo() != null) {
					if (req.getUBOwnerDetails().getUBOInfo().length > 0) {
						UBOInfo1 = req.getUBOwnerDetails().getUBOInfo();
						String tabName = "USR_0_WBG_TMP_UBOREL";
						int iUbo = 0;
						String colName = "LEAD_REFNO,SNO,CUSTOMERRELATIONSHIP,DOB,NAME,NATIONALITY,SHARES,"
								+ "GENDER,SECONDNATIONATLITY,EIDNMBR,EIDEXPDATE,PASSPORTNUMBER,PASSPORTEXPDATE,"
								+ "PASSPORTISSDATE,VISANUMBER,VISAEXPDATE,PEPFLAG,DOLEVEL,COUNTRYOFRESIDENCE,"
								+ "COUNTRYOFINCORPORATION,SYSREFNO";
						String CustomerRelationship = "", DOB = "", Name = "", Nationality = "", Share = "", Gender = "";
						//Added By Shivanshu ATP-426
						String secondNationality = "", EIDNmber = "", EIDExpDate = "", passportNumber = "", 
								passportExpDate = "",passportIssDate = "", visaNumber = "", visaExpDate = "",
								pepFlag = "", DOLevel = "",countryOfResidence = "", countryofIncorporation = ""; 

						for (int counter = 0; counter < UBOInfo1.length; counter++) {
							iUbo = iUbo + 1;
							CustomerRelationship = checkReqData(UBOInfo1[counter]
									.getCustomerRelationship());
							DOB = checkReqData(UBOInfo1[counter].getDOB() == null ? ""
									: UBOInfo1[counter].getDOB().trim());
							Name = checkReqData(UBOInfo1[counter].getName() == null ? ""
									: UBOInfo1[counter].getName().trim());
							Nationality = checkReqData(UBOInfo1[counter].getNationality() == null ? ""
									: UBOInfo1[counter].getNationality().trim());
							Share = checkReqData(UBOInfo1[counter].getShare() == null ? ""
									: UBOInfo1[counter].getShare().trim());
							Gender = checkReqData(UBOInfo1[counter].getGender() == null ? ""
									: UBOInfo1[counter].getGender().trim());
							secondNationality = checkReqData((UBOInfo1[counter].getSecondNationality() == null) ? "" 
									: UBOInfo1[counter].getSecondNationality().trim());
							EIDNmber = checkReqData((UBOInfo1[counter].getEIDNmber() == null) ? ""  
									: UBOInfo1[counter].getEIDNmber().trim());
							EIDExpDate = checkReqData((UBOInfo1[counter].getEIDExpDate() == null) ? ""  
									: UBOInfo1[counter].getEIDExpDate().trim());
							passportNumber = checkReqData((UBOInfo1[counter].getPassportNumber() == null) ? "" 
									: UBOInfo1[counter].getPassportNumber().trim());
							passportExpDate = checkReqData((UBOInfo1[counter].getPassportExpDate() == null) ? "" 
									: UBOInfo1[counter].getPassportExpDate().trim());
							passportIssDate = checkReqData((UBOInfo1[counter].getPassportIssDate() == null) ? "" 
									: UBOInfo1[counter].getPassportIssDate().trim());
							visaNumber = checkReqData((UBOInfo1[counter].getVisaNumber() == null) ? "" 
									: UBOInfo1[counter].getVisaNumber().trim());
							visaExpDate = checkReqData((UBOInfo1[counter].getVisaExpDate() == null) ? "" 
									: UBOInfo1[counter].getVisaExpDate().trim());
							pepFlag = checkReqData((UBOInfo1[counter].getPepFlag() == null) ? "" 
									: UBOInfo1[counter].getPepFlag().trim());
							DOLevel = checkReqData((UBOInfo1[counter].getDOLevel() == null) ? "" 
									: UBOInfo1[counter].getDOLevel().trim());
							countryOfResidence = checkReqData((UBOInfo1[counter].getCountryOfResidence() == null) ? "" 
									: UBOInfo1[counter].getCountryOfResidence().trim());
							countryofIncorporation = checkReqData((UBOInfo1[counter].getCountryofIncorporation() == null) ? ""  
									: UBOInfo1[counter].getCountryofIncorporation().trim());			

							sOutputXML = insertUBORelDtls(tabName, colName,
									iUbo, CustomerRelationship, DOB, Name,
										Nationality, Share, Gender, secondNationality, 
										EIDNmber, EIDExpDate, passportNumber, passportExpDate,
										passportIssDate, visaNumber, visaExpDate, pepFlag, 
							            DOLevel, countryOfResidence, countryofIncorporation);
								
							xp.setInputXML(sOutputXML);
							if (!xp.getValueOf("MainCode").trim()
									.equalsIgnoreCase(SUCCESS_STATUS)) {
								return sOutputXML;
							}
						}
					}
				}
			}
		} catch (Exception E) {
			log.debug("Error in fetchWMSUBODetails" + E.getMessage());
		}
		return sOutputXML;
	}

	private String fetchWMSAccount(AccountOpening req, int i) {
		try {
			String tabName = "USR_0_WBG_TMP_ACC_INFO";
			// added by sheenu CR2019 start
			// String
			// colName="LEAD_REFNO,SNO,ACCBRANCH,ACCSTATUS,AVBAL,CHEQUEREQ,DEBITACCNO,DEBITAMT,DEBITCURR,MODEFUNDING,PRODCODE,PRODCURR,SRVCHRGPKG,SYSREFNO";

			String colName = "LEAD_REFNO,SNO,ACCBRANCH,ACCSTATUS,AVBAL,CHEQUEREQ,DEBITACCNO,DEBITAMT,DEBITCURR,MODEFUNDING,PRODCODE,PRODCURR,SRVCHRGPKG,"
					+ "DEBIT_CARD_REQUEST"
					+ ",OPERATING_INST,MODE_OF_OPERATION,ACCOUNT_TYPE,ACCOUNT_TITLE,CARD_HOLDER_NAME,DEBIT_COMP_NAME,ESTMT_FLAG,ACCNT_IVR_FLAG,WAIVER_CHARGES_FLAG,SYSREFNO,"
					+ "DEBIT_CARD_PRODUCT_GROUP,NO_OF_CHEQUE_BOOKS,NO_OF_LEAVES,CHARGES,CHARGES_WAIVED_FLAG,WAIVER_REASON,OTHER_REASON,CUST_PO_BOX,CUST_ADDRESS_LINE_1,"
					+ "CUST_ADDRESS_LINE_2,CUST_COUNTRY,CUST_STATE_EMIRATE,CUST_CITY,CUST_MOBILE_NUMBER,DELIVERY_MODE,COURIERCUSTOMER_3RDPARTY,COLLECT_BRANCH,THIRD_PARTY_NAME,"
					+ "THIRD_PARTY_MOBILE,PHOTO_ID_TYPE,PHOTO_ID_NO,ATM_FLAG,SI_FLAG,IVR_FACILITY,IB_FACILITY,POS_FACILITY";

			// end
			if (req.getAccountDetails() != null) {
				if (req.getAccountDetails().getAccountInfo() != null) {
					AccountInfo = req.getAccountDetails().getAccountInfo();
					int iAcc = 0;
					for (int counter = 0; counter < AccountInfo.length; counter++) {
						iAcc = iAcc + 1;
						AccBranch = checkReqData(AccountInfo[counter]
								.getAccBranch() == null ? ""
								: AccountInfo[counter].getAccBranch().trim());
						AccStatus = checkReqData(AccountInfo[counter]
								.getAccStatus() == null ? ""
								: AccountInfo[counter].getAccStatus().trim());
						AvBal = checkReqData(AccountInfo[counter].getAvBal() == null ? ""
								: AccountInfo[counter].getAvBal().trim());
						ChequeReq = checkReqData(AccountInfo[counter]
								.getChequeReq() == null ? ""
								: AccountInfo[counter].getChequeReq().trim());

						DebitAccNo = checkReqData(AccountInfo[counter]
								.getDebitAccNo() == null ? ""
								: AccountInfo[counter].getDebitAccNo().trim());
						DebitAmt = checkReqData(AccountInfo[counter]
								.getDebitAmt() == null ? ""
								: AccountInfo[counter].getDebitAmt().trim());
						DebitCurr = checkReqData(AccountInfo[counter]
								.getDebitCurr() == null ? ""
								: AccountInfo[counter].getDebitCurr().trim());
						ModeFunding = checkReqData(AccountInfo[counter]
								.getModeFunding() == null ? ""
								: AccountInfo[counter].getModeFunding().trim());
						ProdCode = checkReqData(AccountInfo[counter]
								.getProdCode() == null ? ""
								: AccountInfo[counter].getProdCode().trim());
						ProdCurr = checkReqData(AccountInfo[counter]
								.getProdCurr() == null ? ""
								: AccountInfo[counter].getProdCurr().trim());
						srvChrgPkg = checkReqData(AccountInfo[counter]
								.getServiceChargePkg() == null ? ""
								: AccountInfo[counter].getServiceChargePkg()
										.trim());
						// /added by sheenu CR2019
						debitCardReq = checkReqData(AccountInfo[counter]
								.getDebitCardReq() == null ? ""
								: AccountInfo[counter].getDebitCardReq().trim());
						// debitCardReq="N";
						operatingInstructions = checkReqData(AccountInfo[counter]
								.getOperatingInstructions() == null ? ""
								: AccountInfo[counter]
										.getOperatingInstructions().trim());
						modeOfOperation = checkReqData(AccountInfo[counter]
								.getModeOfOperation() == null ? ""
								: AccountInfo[counter].getModeOfOperation()
										.trim());
						accountType = checkReqData(AccountInfo[counter]
								.getAccountType() == null ? ""
								: AccountInfo[counter].getAccountType().trim());
						accountTitle = checkReqData(AccountInfo[counter]
								.getAccountTitle() == null ? ""
								: AccountInfo[counter].getAccountTitle().trim());
						cardHolderName = checkReqData(AccountInfo[counter]
								.getCardHolderName() == null ? ""
								: AccountInfo[counter].getCardHolderName()
										.trim());
						debitCompName = checkReqData(AccountInfo[counter]
								.getDebitCompName() == null ? ""
								: AccountInfo[counter].getDebitCompName()
										.trim());
						eStmtFlag = checkReqData(AccountInfo[counter]
								.geteStmtFlag() == null ? ""
								: AccountInfo[counter].geteStmtFlag().trim());
						accntIVRFlag = checkReqData(AccountInfo[counter]
								.getAccntIVRFlag() == null ? ""
								: AccountInfo[counter].getAccntIVRFlag().trim());
						waiverChargesFlag = checkReqData(AccountInfo[counter]
								.getWaiverChargesFlag() == null ? ""
								: AccountInfo[counter].getWaiverChargesFlag()
										.trim());
						// added by sahil for new tags in account 8-apr-2020
						cardProductGroup = checkReqData(AccountInfo[counter]
								.getCardProductGroup() == null ? ""
								: AccountInfo[counter].getCardProductGroup()
										.trim());
						noofchequeBooks = checkReqData(AccountInfo[counter]
								.getNoofchequeBooks() == null ? ""
								: AccountInfo[counter].getNoofchequeBooks()
										.trim());
						bookSize = checkReqData(AccountInfo[counter]
								.getBookSize() == null ? ""
								: AccountInfo[counter].getBookSize().trim());
						Charges = checkReqData(AccountInfo[counter]
								.getCharges() == null ? ""
								: AccountInfo[counter].getCharges().trim());
						ChargesWaivedFlag = checkReqData(AccountInfo[counter]
								.getChargesWaivedFlag() == null ? ""
								: AccountInfo[counter].getChargesWaivedFlag()
										.trim());
						WaiverReason = checkReqData(AccountInfo[counter]
								.getWaiverReason() == null ? ""
								: AccountInfo[counter].getWaiverReason().trim());
						OtherReason = checkReqData(AccountInfo[counter]
								.getOtherReason() == null ? ""
								: AccountInfo[counter].getOtherReason().trim());
						customerAddresszip = checkReqData(AccountInfo[counter]
								.getCustomerAddresszip() == null ? ""
								: AccountInfo[counter].getCustomerAddresszip()
										.trim());
						customerAddress1 = checkReqData(AccountInfo[counter]
								.getCustomerAddress1() == null ? ""
								: AccountInfo[counter].getCustomerAddress1()
										.trim());
						customerAddress2 = checkReqData(AccountInfo[counter]
								.getCustomerAddress2() == null ? ""
								: AccountInfo[counter].getCustomerAddress2()
										.trim());
						customerAddressCountry = checkReqData(AccountInfo[counter]
								.getCustomerAddressCountry() == null ? ""
								: AccountInfo[counter]
										.getCustomerAddressCountry().trim());
						customerAddressStateEmirate = checkReqData(AccountInfo[counter]
								.getCustomerAddressStateEmirate() == null ? ""
								: AccountInfo[counter]
										.getCustomerAddressStateEmirate()
										.trim());
						customerAddressCity = checkReqData(AccountInfo[counter]
								.getCustomerAddressCity() == null ? ""
								: AccountInfo[counter].getCustomerAddressCity()
										.trim());
						customerMobileNo = checkReqData(AccountInfo[counter]
								.getCustomerMobileNo() == null ? ""
								: AccountInfo[counter].getCustomerMobileNo()
										.trim());
						flagDeliveryMode = checkReqData(AccountInfo[counter]
								.getFlagDeliveryMode() == null ? ""
								: AccountInfo[counter].getFlagDeliveryMode()
										.trim());
						CouriertoCustomer_3rdParty = checkReqData(AccountInfo[counter]
								.getCouriertoCustomer_3rdParty() == null ? ""
								: AccountInfo[counter]
										.getCouriertoCustomer_3rdParty().trim());
						CollectBranch = checkReqData(AccountInfo[counter]
								.getCollectBranch() == null ? ""
								: AccountInfo[counter].getCollectBranch()
										.trim());
						thirdPartyName = checkReqData(AccountInfo[counter]
								.getThirdPartyName() == null ? ""
								: AccountInfo[counter].getThirdPartyName()
										.trim());
						thirdPartyMobileNo = checkReqData(AccountInfo[counter]
								.getThirdPartyMobileNo() == null ? ""
								: AccountInfo[counter].getThirdPartyMobileNo()
										.trim());
						photoIdType = checkReqData(AccountInfo[counter]
								.getPhotoIdType() == null ? ""
								: AccountInfo[counter].getPhotoIdType().trim());
						photoIdNo = checkReqData(AccountInfo[counter]
								.getPhotoIdNo() == null ? ""
								: AccountInfo[counter].getPhotoIdNo().trim());

						// more new tags - sahil
						ATM_Flag = checkReqData(AccountInfo[counter]
								.getATM_Flag() == null ? ""
								: AccountInfo[counter].getATM_Flag().trim());
						SI_Flag = checkReqData(AccountInfo[counter]
								.getSI_Flag() == null ? ""
								: AccountInfo[counter].getSI_Flag().trim());
						IVR_Facility = checkReqData(AccountInfo[counter]
								.getIVR_Facility() == null ? ""
								: AccountInfo[counter].getIVR_Facility().trim());
						IB_Facility = checkReqData(AccountInfo[counter]
								.getIB_Facility() == null ? ""
								: AccountInfo[counter].getIB_Facility().trim());
						POS_Facility = checkReqData(AccountInfo[counter]
								.getPOS_Facility() == null ? ""
								: AccountInfo[counter].getPOS_Facility().trim());

						log.debug("cardProductGroup -- value:"
								+ cardProductGroup);
						log.debug("noofchequeBooks -- value:" + noofchequeBooks);
						log.debug("bookSize -- value:" + bookSize);
						log.debug("Charges -- value:" + Charges);
						log.debug("ChargesWaivedFlag -- value:"
								+ ChargesWaivedFlag);
						log.debug("OtherReason -- value:" + OtherReason);
						log.debug("WaiverReason -- value:" + WaiverReason);
						log.debug("customerAddresszip -- value:"
								+ customerAddresszip);
						log.debug("customerAddress1 -- value:"
								+ customerAddress1);
						log.debug("customerAddress2 -- value:"
								+ customerAddress2);
						log.debug("customerAddressCountry -- value:"
								+ customerAddressCountry);
						log.debug("customerAddressStateEmirate -- value:"
								+ customerAddressStateEmirate);
						log.debug("customerAddressCity -- value:"
								+ customerAddressCity);
						log.debug("customerMobileNo -- value:"
								+ customerMobileNo);
						log.debug("flagDeliveryMode -- value:"
								+ flagDeliveryMode);
						log.debug("CouriertoCustomer_3rdParty -- value;"
								+ CouriertoCustomer_3rdParty);
						log.debug("CollectBranch -- value:" + CollectBranch);
						log.debug("thirdPartyName -- value" + thirdPartyName);
						log.debug("thirdPartyMobileNo -- value:"
								+ thirdPartyMobileNo);
						log.debug("photoIdType -- value:" + photoIdType);
						log.debug("photoIdNo -- value:" + photoIdNo);

						log.debug("ATM_Flag -- value :" + ATM_Flag);
						log.debug("SI_Flag -- value :" + SI_Flag);
						log.debug("IVR_Facility -- value :" + IVR_Facility);
						log.debug("IB_Facility -- value :" + IB_Facility);
						log.debug("POS_Facility -- value :" + POS_Facility);

						sOutputXML = insertAccInfoDtls(tabName, colName, iAcc,
								AccBranch, AccStatus, AvBal, ChequeReq,
								DebitAccNo, DebitAmt, DebitCurr, ModeFunding,
								ProdCode, ProdCurr, srvChrgPkg, debitCardReq,
								operatingInstructions, modeOfOperation,
								accountType, accountTitle, cardHolderName,
								debitCompName, eStmtFlag, accntIVRFlag,
								waiverChargesFlag, cardProductGroup,
								noofchequeBooks, bookSize, Charges,
								ChargesWaivedFlag, WaiverReason, OtherReason,
								customerAddresszip, customerAddress1,
								customerAddress2, customerAddressCountry,
								customerAddressStateEmirate,
								customerAddressCity, customerMobileNo,
								flagDeliveryMode, CouriertoCustomer_3rdParty,
								CollectBranch, thirdPartyName,
								thirdPartyMobileNo, photoIdType, photoIdNo,
								ATM_Flag, SI_Flag, IVR_Facility, IB_Facility,
								POS_Facility);
						xp.setInputXML(sOutputXML);
						if (!xp.getValueOf("MainCode").trim()
								.equalsIgnoreCase(SUCCESS_STATUS)) {
							return sOutputXML;
						}
					}
				}
			}
		} catch (Exception E) {
			log.debug("Error in fetchWMSCrsGeneric" + E.getMessage());
		}
		return sOutputXML;

	}

	private void fetchWMSCompany(AccountOpening req, int i) {

		try {
			// Fetching Compant Details
			Company = req.getCompany();
			if (Company != null) {
				CompanyInformation = Company.getCompanyInformation();
				if (CompanyInformation != null) {
					CompanyDetails = CompanyInformation.getCompanyDetails();
					if (CompanyDetails != null) {
						CompIC = checkReqData((CompanyDetails.getCompIC() == null) ? ""
								: CompanyDetails.getCompIC().trim());
						compCustType = checkReqData((CompanyDetails
								.getCustType() == null) ? "" : CompanyDetails
								.getCustType().trim());
						CompCat = checkReqData((CompanyDetails.getCompCat() == null) ? ""
								: CompanyDetails.getCompCat().trim());
						CompID = checkReqData((CompanyDetails.getCompID() == null) ? ""
								: CompanyDetails.getCompID().trim());
						if("".equalsIgnoreCase(CompID))
						{
							NTBEntityFound=true;
						}
						CompFullName = checkReqData((CompanyDetails
								.getCompFullName() == null) ? ""
								: CompanyDetails.getCompFullName().trim());
						CompPrefix = checkReqData((CompanyDetails
								.getCompPrefix() == null) ? "" : CompanyDetails
								.getCompPrefix().trim());
						compBrCode = HomeBranch;
						compCntryIncp = checkReqData((CompanyDetails
								.getCntryIncp() == null) ? "" : CompanyDetails
								.getCntryIncp().trim());
						TlNo = checkReqData((CompanyDetails.getTlNo() == null) ? ""
								: CompanyDetails.getTlNo().trim());
						TlIssDt = checkReqData((CompanyDetails.getTlIssDt() == null) ? ""
								: CompanyDetails.getTlIssDt().trim());
						TlExpDt = checkReqData((CompanyDetails.getTlExpDt() == null) ? ""
								: CompanyDetails.getTlExpDt().trim());
						compCmMemNo = checkReqData((CompanyDetails.getCmMemNo() == null) ? ""
								: CompanyDetails.getCmMemNo().trim());
						compIncorpDate = checkReqData((CompanyDetails
								.getIncorpDate() == null) ? "" : CompanyDetails
								.getIncorpDate().trim());
						compSysRiskCls = checkReqData((CompanyDetails
								.getSysRiskCls() == null) ? "" : CompanyDetails
								.getSysRiskCls().trim());
						compRMRiskCls = checkReqData((CompanyDetails
								.getRMRiskCls() == null) ? "" : CompanyDetails
								.getRMRiskCls().trim());
						compRMRiskClsDt = checkReqData((CompanyDetails
								.getRMRiskClsDt() == null) ? ""
								: CompanyDetails.getRMRiskClsDt().trim());
						compCB1Code = checkReqData((CompanyDetails.getCB1Code() == null) ? ""
								: CompanyDetails.getCB1Code().trim());
						compCB2Division = checkReqData((CompanyDetails
								.getCB2Division() == null) ? ""
								: CompanyDetails.getCB2Division().trim());
						compCB2GrpId = checkReqData((CompanyDetails
								.getCB2GrpId() == null) ? "" : CompanyDetails
								.getCB2GrpId().trim());
						compCB2PeerId = checkReqData((CompanyDetails
								.getCB2PeerId() == null) ? "" : CompanyDetails
								.getCB2PeerId().trim());
						compCB2Code = checkReqData((CompanyDetails.getCB2Code() == null) ? ""
								: CompanyDetails.getCB2Code().trim());
						compTypBusDesc = checkReqData((CompanyDetails
								.getTypBusDesc() == null) ? "" : CompanyDetails
								.getTypBusDesc().trim());
						compProfitCenter = checkReqData((CompanyDetails
								.getProfitCenter() == null) ? ""
								: CompanyDetails.getProfitCenter().trim());
						compAcctCls = checkReqData((CompanyDetails
								.getComAcctCls() == null) ? "" : CompanyDetails
								.getComAcctCls().trim());
						cRMName = checkReqData((CompanyDetails.getRMName() == null) ? ""
								: CompanyDetails.getRMName().trim());
						cRMCode = checkReqData((CompanyDetails.getRMCode() == null) ? ""
								: CompanyDetails.getRMCode().trim());
						compAnSaleRevnue = checkReqData((CompanyDetails
								.getAnSaleRevnue() == null) ? ""
								: CompanyDetails.getAnSaleRevnue().trim());
						compNoOfEmp = checkReqData((CompanyDetails.getNoOfEmp() == null) ? ""
								: CompanyDetails.getNoOfEmp().trim());
						compBankName1 = checkReqData((CompanyDetails
								.getBankName1() == null) ? "" : CompanyDetails
								.getBankName1().trim());
						compBankName2 = checkReqData((CompanyDetails
								.getBankName2() == null) ? "" : CompanyDetails
								.getBankName2().trim());
						compBankName3 = checkReqData((CompanyDetails
								.getBankName3() == null) ? "" : CompanyDetails
								.getBankName3().trim());
						compCityCntry1 = checkReqData((CompanyDetails
								.getCityCntry1() == null) ? "" : CompanyDetails
								.getCityCntry1().trim());
						compCityCntry2 = checkReqData((CompanyDetails
								.getCityCntry2() == null) ? "" : CompanyDetails
								.getCityCntry2().trim());
						compCityCntry3 = checkReqData((CompanyDetails
								.getCityCntry3() == null) ? "" : CompanyDetails
								.getCityCntry3().trim());
						compAppRMName = checkReqData((CompanyDetails
								.getAppRMName() == null) ? "" : CompanyDetails
								.getAppRMName().trim());
						compAppRMCode = checkReqData((CompanyDetails
								.getAppRMCode() == null) ? "" : CompanyDetails
								.getAppRMCode().trim());
						compSecRMName = checkReqData((CompanyDetails
								.getSecRMName() == null) ? "" : CompanyDetails
								.getSecRMName().trim());
						compSecRMCode = checkReqData((CompanyDetails
								.getSecRMCode() == null) ? "" : CompanyDetails
								.getSecRMCode().trim());
						compAccMOrgNRcvd = checkReqData((CompanyDetails
								.getAccMOrgNRcvd() == null) ? ""
								: CompanyDetails.getAccMOrgNRcvd().trim());
						compdt1SrOrgNRcvd = checkReqData((CompanyDetails
								.getDt1SrOrgNRcvd() == null) ? ""
								: CompanyDetails.getDt1SrOrgNRcvd().trim());
						compCSCFrmRcvd = checkReqData((CompanyDetails
								.getCSCFrmRcvd() == null) ? "" : CompanyDetails
								.getCSCFrmRcvd().trim());
						compSrcTurnOver = checkReqData((CompanyDetails
								.getSrcTurnOver() == null) ? ""
								: CompanyDetails.getSrcTurnOver().trim());
						
						compSrvRemarks = checkReqData((CompanyDetails
								.getSrvRemarks() == null) ? "" : CompanyDetails
								.getSrvRemarks().trim());
						compCustSrvChrgPkg = checkReqData((CompanyDetails
								.getCustServChargePkg() == null) ? ""
								: CompanyDetails.getCustServChargePkg().trim());
						// sahil
						MobileNumber = checkReqData((CompanyDetails
								.getMobileNumber() == null) ? ""
								: CompanyDetails.getMobileNumber().trim());
						RegPlaceKey = checkReqData((CompanyDetails
								.getRegPlaceKey() == null) ? ""
								: CompanyDetails.getRegPlaceKey().trim());
						RegPlaceValue = checkReqData((CompanyDetails
								.getRegPlaceValue() == null) ? ""
								: CompanyDetails.getRegPlaceValue().trim());
						EconomicActivityKey = checkReqData((CompanyDetails
								.getEconomicActivityKey() == null) ? ""
								: CompanyDetails.getEconomicActivityKey()
										.trim());
						EconomicActivityValue = checkReqData((CompanyDetails
								.getEconomicActivityValue() == null) ? ""
								: CompanyDetails.getEconomicActivityValue()
										.trim());
						log.debug("value of MobileNumber" + MobileNumber);
						log.debug("value of RegPlaceKey" + RegPlaceKey);
						log.debug("value of RegPlaceValue" + RegPlaceValue);
						log.debug("value of EconomicActivityKey"
								+ EconomicActivityKey);
						log.debug("value of EconomicActivityValue"
								+ EconomicActivityValue);

						// sharan new tags 01/07/2020
						noOfSignatures = checkReqData((CompanyDetails
								.getnoOfSignatures() == null) ? ""
								: CompanyDetails.getnoOfSignatures().trim());
						tlIssuingAuthority = checkReqData((CompanyDetails
								.gettlIssuingAuthority() == null) ? ""
								: CompanyDetails.gettlIssuingAuthority().trim());
						tlUpdateFlag = checkReqData((CompanyDetails
								.gettlUpdateFlag() == null) ? ""
								: CompanyDetails.gettlUpdateFlag().trim());

						log.debug("value of noOfSignatures " + noOfSignatures);
						log.debug("value of tlIssuingAuthority "
								+ tlIssuingAuthority);
						log.debug("value of tlUpdateFlag " + tlUpdateFlag);
						
						//Added by Shivanshu 
						digiSignFlag = checkReqData((CompanyDetails
								.getDigiSignFlag() == null) ? "" : CompanyDetails
								.getDigiSignFlag().trim());
						
						digiSignReason = checkReqData((CompanyDetails
								.getDigiSignReason() == null) ? "" : CompanyDetails
								.getDigiSignReason().trim());
						
						log.debug("value of digiSignFlag " + digiSignFlag);
						log.debug("value of digiSignReason " + digiSignReason);
						
						//Ended Shivanshu
					}
				}
			}
			// Address Details
			// Correspondent Address
			AddressCorrDets = Company.getCompanyInformation()
					.getAddressCorrDets();
			if (AddressCorrDets != null) {
				compCorr_AddrsLine1 = "P O Box "
						+ checkReqData((AddressCorrDets.getAddrsLine1() == null) ? ""
								: AddressCorrDets.getAddrsLine1().trim());
				compCorr_AddrsLine2 = checkReqData((AddressCorrDets
						.getAddrsLine2() == null) ? "" : AddressCorrDets
						.getAddrsLine2().trim());
				compCorr_AddrsLine3 = checkReqData((AddressCorrDets
						.getAddrsLine3() == null) ? "" : AddressCorrDets
						.getAddrsLine3().trim());
				compCorr_State = chkStateReqData((AddressCorrDets.getState() == null) ? ""
						: AddressCorrDets.getState().trim());
				compCorr_City = checkReqData((AddressCorrDets.getCity() == null) ? ""
						: AddressCorrDets.getCity().trim());
				compCorr_Country = checkReqData((AddressCorrDets.getCountry() == null) ? ""
						: AddressCorrDets.getCountry().trim());
			}

			// Physical Address
			AddressPhyDets = Company.getCompanyInformation()
					.getAddressPhyDets();
			if (AddressPhyDets != null) {
				compPhy_AddrsLine1 = "P O Box "
						+ checkReqData((AddressPhyDets.getAddrsLine1() == null) ? ""
								: AddressPhyDets.getAddrsLine1().trim());
				compPhy_AddrsLine2 = checkReqData((AddressPhyDets
						.getAddrsLine2() == null) ? "" : AddressPhyDets
						.getAddrsLine2().trim());
				compPhy_AddrsLine3 = checkReqData((AddressPhyDets
						.getAddrsLine3() == null) ? "" : AddressPhyDets
						.getAddrsLine3().trim());
				compPhy_Country = checkReqData((AddressPhyDets.getCountry() == null) ? ""
						: AddressPhyDets.getCountry().trim());
				compPhy_State = chkStateReqData((AddressPhyDets.getState() == null) ? ""
						: AddressPhyDets.getState().trim());
				compPhy_City = checkReqData((AddressPhyDets.getCity() == null) ? ""
						: AddressPhyDets.getCity().trim());

			}
			// Incorporation Details
			IncorporationDets = Company.getCompanyInformation()
					.getIncorporationDets();
			if (IncorporationDets != null) {
				incpCountry = checkReqData(IncorporationDets.getCountry() == null ? ""
						: IncorporationDets.getCountry().trim());
				incpCity = checkReqData(IncorporationDets.getCity() == null ? ""
						: IncorporationDets.getCity().trim());
				incpState = checkReqData(IncorporationDets.getState() == null ? ""
						: IncorporationDets.getState().trim());
			}
			// Contact Info
			ContactInfo = Company.getCompanyInformation().getContactInfo();
			if (ContactInfo != null) {
				OffNumber = checkReqData((ContactInfo.getOffNumber() == null) ? ""
						: ContactInfo.getOffNumber().trim());
				OffNumber2 = checkReqData((ContactInfo.getOffNumber2() == null) ? ""
						: ContactInfo.getOffNumber2().trim());
				Mobile = checkReqData((ContactInfo.getMobile() == null) ? ""
						: ContactInfo.getMobile().trim());
				Fax = checkReqData((ContactInfo.getFax() == null) ? ""
						: ContactInfo.getFax().trim());
				Email = checkReqData((ContactInfo.getEmail() == null) ? ""
						: ContactInfo.getEmail().trim());
				Website = checkReqData((ContactInfo.getWebsite() == null) ? ""
						: ContactInfo.getWebsite().trim());
			}
			// Fatca Details
			Fatca = Company.getFatca();
			if (Fatca != null) {
				FatcaDetails = Company.getFatca().getFatcaDetails();
				if (FatcaDetails != null) {
					fatca_fatcaUpdateFlag = checkReqData((FatcaDetails
							.getFatcaUpdateFlag() == null) ? "" : FatcaDetails
							.getFatcaUpdateFlag().trim());
					fatca_customerClsfctn = checkReqData((FatcaDetails
							.getCustomerClsfctn() == null) ? "" : FatcaDetails
							.getCustomerClsfctn().trim());
					fatca_natureOfEntity = checkReqData((FatcaDetails
							.getNatureOfEntity() == null) ? "" : FatcaDetails
							.getNatureOfEntity().trim());
					fatca_typeOfEntity = checkReqData((FatcaDetails
							.getTypeOfEntity() == null) ? "" : FatcaDetails
							.getTypeOfEntity().trim());
					fatca_FATCAStatus = checkReqData((FatcaDetails
							.getFATCAStatus() == null) ? "" : FatcaDetails
							.getFATCAStatus().trim());
					fatca_documentCollected = checkReqData((FatcaDetails
							.getDocumentCollected() == null) ? ""
							: FatcaDetails.getDocumentCollected().trim());
					fatca_dateOfDocument = checkReqData((FatcaDetails
							.getDateOfDocument() == null) ? "" : FatcaDetails
							.getDateOfDocument().trim());
					fatca_idntfctnNumRequired = checkReqData((FatcaDetails
							.getIdntfctnNumRequired() == null) ? ""
							: FatcaDetails.getIdntfctnNumRequired().trim());
					fatca_idntfctnNumber = checkReqData((FatcaDetails
							.getIdntfctnNumber() == null) ? "" : FatcaDetails
							.getIdntfctnNumber().trim());
					fatca_customerClsfctnDate = checkReqData((FatcaDetails
							.getCustomerClsfctnDate() == null) ? ""
							: FatcaDetails.getCustomerClsfctnDate().trim());
					fatca_customerFATCAClsfctnDate = checkReqData((FatcaDetails
							.getCustomerFATCAClsfctnDate() == null) ? ""
							: FatcaDetails.getCustomerFATCAClsfctnDate().trim());
					
				}
			}
			log.info("CrsDetails"+CrsDetails);
			CrsDetails = req.getCompany().getCrsDetails();
			if (CrsDetails != null) {
				log.info("CrsDetails not null");
				CrsGeneric = CrsDetails.getCrsGeneric();
				log.info("CrsGeneric"+CrsGeneric);
				if (CrsGeneric != null) {
					log.info("CrsGeneric not null");
					crsUpdateFlag = checkReqData(CrsGeneric.getCrsUpdateFlag()
							.trim() == null ? "" : CrsGeneric
							.getCrsUpdateFlag().trim());
					crsChannel = checkReqData(CrsGeneric.getChannel() == null ? ""
							: CrsGeneric.getChannel().trim());
					crsClassificationDate = checkReqData(CrsGeneric
							.getClassificationDate() == null ? "" : CrsGeneric
							.getClassificationDate().trim());
					crsClassificationId = checkReqData(CrsGeneric
							.getClassificationId() == null ? "" : CrsGeneric
							.getClassificationId().trim());
					crsCertFormObtained = checkReqData(CrsGeneric
							.getCrsCertFormObtained() == null ? "" : CrsGeneric
							.getCrsCertFormObtained().trim());
					crsCustBirthCity = checkReqData(CrsGeneric
							.getCustBirthCity() == null ? "" : CrsGeneric
							.getCustBirthCity().trim());
					crsCustFirstName = checkReqData(CrsGeneric
							.getCustFirstName() == null ? "" : CrsGeneric
							.getCustFirstName().trim());
					crsCustId = checkReqData(CrsGeneric.getCustId() == null ? ""
							: CrsGeneric.getCustId().trim());
					crsCustLastName = checkReqData(CrsGeneric.getCustLastName() == null ? ""
							: CrsGeneric.getCustLastName().trim());
					crs_CustomerType = checkReqData(CrsGeneric
							.getCustomerType() == null ? "" : CrsGeneric
							.getCustomerType().trim());
					crsEntityTypeId = checkReqData(CrsGeneric.getEntityTypeId() == null ? ""
							: CrsGeneric.getEntityTypeId().trim());
					crsMakerDate = checkReqData(CrsGeneric.getMakerDate() == null ? ""
							: CrsGeneric.getMakerDate().trim());
					crsMakerId = checkReqData(CrsGeneric.getMakerId() == null ? ""
							: CrsGeneric.getMakerId().trim());
					crsResidenceAddressConfirmationStatus = checkReqData(CrsGeneric
							.getResidenceAddressConfirmationStatus() == null ? ""
							: CrsGeneric
									.getResidenceAddressConfirmationStatus()
									.trim());
					crsTermsAndCondAccepted = checkReqData(CrsGeneric
							.getTermsAndCondAccepted() == null ? ""
							: CrsGeneric.getTermsAndCondAccepted().trim());
					crsRelRefNo = checkReqData(CrsGeneric.getCrsRelRefNo() == null ? ""
							: CrsGeneric.getCrsRelRefNo().trim());
					crsCertificationDate = checkReqData(CrsGeneric
							.getCrsCertificationDate().trim() == null ? ""
							: CrsGeneric.getCrsCertificationDate().trim());
					
				}
			}

			log.debug("completing the fetch company");
		} catch (Exception E) {
			log.debug("Error in fetchWMSCompany" + E.getMessage());
		}

	}

	private void fetchWMSHeader(AccountOpening req, int i) {

		try {
			WmsHeader header = req.getWmsHeader();
			SourceChannel = checkReqData(header.getSourceChannel());
			SourcingCentre = checkReqData(header.getSourcingCentre());
			LeadRefNo = checkReqData(header.getLeadRefNo());
			leadRefNumber = LeadRefNo;
			LeadSubDtTm = checkReqData(header.getLeadSubDtTm());
			HomeBranch = checkReqData(header.getHomeBranch());
			IterationCount = checkReqData(header.getIterationCount());
			Iteration = checkReqData(header.getIteration());
			ReqType = checkReqData(header.getReqType());
			QueueName = checkReqData(header.getQueueName());
			InitiatorName = checkReqData(header.getInitiatorName());
			AppRiskCls = checkReqData(header.getAppRiskCls());
			LastProcessedByUserName = checkReqData(header.getLastProcessedByUserName());
			compRMName = checkReqData(header.getRMName());
			compRMCode = checkReqData(header.getRMCode());
			RMEmail = checkReqData(header.getRMEmail());
			RMMobileNo = checkReqData(header.getRMMobileNo());
			RMMngName = checkReqData(header.getRMMngName());
			RMMngCode = checkReqData(header.getRMMngCode());
			RMMngEmail = checkReqData(header.getRMMngEmail());
			// CR2019
			InitiatorBy = checkReqData(header.getInitiatorBy());
			InitiatedDept = checkReqData(header.getInitiatedDept());
			SourcingDSACode = checkReqData(header.getSourcingDSACode());
			SourceOfLead = checkReqData(header.getSourceOfLead());
			leadType = checkReqData(header.getleadType());
			// end
			//added by reyaz 25/11/2022
			String[] spliMessage = Iteration.split(Pattern.quote("|"));
			for(int j=0;j<spliMessage.length;j++){
				if(j==0){
					itrationValue = spliMessage[0];
				} else if(j==1){
					reworkValue = spliMessage[1];
				} else if(j==2){
					duallaneValue = spliMessage[2];
				}
			}
			log.info("itrationValue  :" +itrationValue);
			log.info("reworkValue  :" +reworkValue);
			log.info("duallaneValue  :" +duallaneValue);
			
			
			if("Y".equalsIgnoreCase(reworkValue)){
				reworkValue ="Yes";
			} else if("N".equalsIgnoreCase(reworkValue)){
				reworkValue ="No";
			}
			
			if("Y".equalsIgnoreCase(duallaneValue)){
				duallaneValue ="Yes";
			} else if("N".equalsIgnoreCase(duallaneValue)){
				duallaneValue ="No";
			}
		
			log.debug("completing fetchWMSHeader ");
		} catch (Exception E) {
			log.debug("Error in fetchWMSHeader" + E.getMessage());
		}

	}

	private String insertKYCGeneric(AccountOpening req, int i) {
		try {
			String tabName = "USR_0_WBG_TMP_KYC";
			String colName = "LEAD_REFNO,ISTRADECONTRACT,ISUTILITYBILL,ISBANKACCSTMT,ISPHONEBILL,OTHER,ISCBPAYMENT,PURPOSEOFPAYMENT,NOOFPAYMENTPERMONTH,MONTHLYVALPAYMENT,CNTRYMADETORCVDFROM,CASHCDM,ISCASHCDM,ISCHEQUESDRAFT,CHEQUESDRAFT,ISTRANSFERS,TRANSFERS,ISSERVICEREQ,SERVICEPROVIDERNAME,INCORPORATIONPLACE,FORMATIONDATE,PHYBUSINESSLOCATION,ISANNUALINCOMEACTUALAED,ISANNUALINCOMEESTAED,PRIMARYTRADELOCATION,NOOFUAEBRANCH,UAEBRACHPRIMARYLOCATION,NOOFNONUAEBRANCH,NONUAEBRACHPRIMARYLOCATION,NOOFEMP,WEBSITE,TOTALASSETAED,ANNUALSALREVNUEAED,NETPROFITAED,ISCLIENTVISIT,DATEOFVISIT,LOCATIONOFVISIT,ADCBSTAFFNAME,PERSONMETWITH,TYPEOFCOMPANY,TYPEOFCOMSETUP,TYPEOFCOMPSTRENGTH,TYPEOFCOMPLOCATION,ACTIVITYCONDUCTED,ISOPTSUBENTITY,OPTSUBENTITY,RMASSESSMENT,CIF,PROFILINGPURPOSE,RELATIONSHIPSINCE,BUSINESSNATURE,PURPOSEAOADCB,ACTIVEACCOUNTFLAG,PURPOSEAOADCBOTHERS,ISDUALGOODS,DUALGOODSTYPE,ISOFFSHORELOCATION,ISFREEZONE,ISAMLCFTPOLICY,ISNUAETRADARMWEAPONS,ISNUAEDEFRELEQUP,ISHAWALA,ISSANCTIONED,ISINCRISKJURI,ISSHELLCOMPANY,ISDEEMEDTAXEVASION,ISBUSLNKSANCTIONED,ISVERTUALFLEXIDESK,ISNOMINEEOWNSHIPSTR,ISISSUEDBEARERSHARE,ISVERTUALCRYPTOCUR,ISNUAEOWNEDENTY,ISNUAEPEP,ISPEPRISKQ3,ISOVERALLRISKINC,ISOVERALLRISKUNACT,ISUBOHAWALA,ISUBOBUSTRADLNKSANCTIONED,SOURCEOFFUND,KYCSIGNOFF,BGHNAME,BUHNAME,LMNAME,STAFFNAME,SYSREFNO";
			sOutputXML = insertKYCGenericDetails(tabName, colName,
					IsTradeContract, IsUtilityBill, IsBankAccStmt, IsPhoneBill,
					Others, IsCBPayment, PurposeOfPayment, NoOfPaymentPerMonth,
					MonthlyValPayment, CntryMadetoRcvdFrom, CashCDM, IsCashCDM,
					IsChequesDraft, ChequesDraft, IsTransfers, Transfers,
					IsServiceReq, ServiceProviderName, IncorporationPlace,
					FormationDate, PhyBusinessLocation,
					IsAnnualIncomeActualAED, ISAnnualIncomeEstAED,
					PrimaryTradeLocation, NoofUaeBranch,
					UaeBrachPrimaryLocation, NoOfNonUaeBranch,
					NonUaeBrachPrimaryLocation, compNoOfEmp, Website,
					TotalAssetAED, AnnualSalRevnueAED, NetProfitAED,
					IsClientVisit, DateOfVisit, LocationOfVisit, AdcbStaffName,
					PersonMetWith, TypeOfCompany, TypeOfComSetup,
					TypeOfCompStrength, TypeOfCompLocation, ActivityConducted,
					IsOptSubEntity, OptSubEntity, RmAssessment, CIF,
					ProfilingPurpose, RelationshipSince, BusinessNature,
					PurposeAOADCB, ActiveAccountFlag ,PurposeAOADCBOthers,
					IsDualGoods,DualGoodsType,IsOffshoreLocation, IsFreezone,
					IsAMLCFTPolicy, IsNUaeTradArmWeapons, IsNUaedefRelEqup,
					IsHawala, IsSanctioned, IsIncRiskJuri, IsShellCompany,
					IsDeemedTaxEvasion, IsBusLnkSanctioned, IsVertualFlexiDesk,
					IsNomineeOwnShipStr, IsIssuedBearerShare,
					IsVertualCryptoCur, IsNUaeOwnedEnty, IsNUaePep,IsPEPRiskQ3,
					IsOverAllRiskInc, IsOverAllRiskUnact, IsUboHawala,
					IsUboBusTradLnkSanctioned, SourceOfFund, bGHName, bUHName,
					lMName, staffName);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sOutputXML;
	}

	private String fetchAusCustomerDtls(AccountOpening req, int i) {
		// String docAus="AUS";
		/*
		 * String nameUpdateFlag = ""; String countryResidenceFlag = ""; String
		 * countryCorrespondanceFlag = ""; String passportUpdateFlag = "";
		 * String visaUpdateFlag = "";
		 */
		// AUSattrib[] AUSattrib;

		if (req.getCustomers() != null) {
			if (req.getCustomers().getCutomerPersonalDtls() != null) {
				try {
					CutomerPersonalDtls = req.getCustomers()
							.getCutomerPersonalDtls();
					String colAusName = "LEAD_REFNO,AUSSERNO,CUSTTYPE,ADDRESS,ADDLINE1,ADDLINE2,CORCNTRY,RESCNTRY,CUSTCAT,CUSTFULLNAME,CUSTIC,CUSTID,CUSTPREFIX,DOB,EIDAISSDT,EIDAEXPDT,EIDANO,GENDER,HOMEBRCODE,MARITALSTS,NATIONALITY,PROFITCENTER,PASSEXPDT,PASSISSDT,PASSPORT,SIGNTYP,ESTATE,TELLANDLINE,TELMOB,TOWN,VISAEXPDT,EXPDT,VISANO,VISAISSDT,ZIP,SIG_SEQ_NO,SIG_UPD_REQ,EMAILID,NAME_UPDATE_FLAG,COUNTRY_RESIDENCE_FLAG,COUNTRY_CORRESPONDENCE_FLAG,PASSPORT_UPDATE_FLAG,VISA_UPDATE_FLAG,MOBILE_UPDATE_FLAG,EMAIL_UPDATE_FLAG,CUST_ID,SYSREFNO,REQUESTCLASSIFICATION,RMCODE,CUSTUPGRADEFLG,EMBOSSNAME,CARDPRODUCTGROUP,INSTANTFLAG,CUSTOMERCLASSIFICATION,SECONDNATIONALITY,PEPFLAG,EXTRAFIELD1,EXTRAFIELD2";  //Added by SHivanshu ATP-426
					String tabName = "USR_0_WBG_TMP_AUS";
					// String docTabName="USR_0_WBG_TMP_DOC";
					// String
					// docColName="LEAD_REFNO,SNO,ENTAUSTYP,DOCNAME,DOCTYPE,DOCUMENTFOR,DOCUMENTINDEX,UPLOADDT,SYSREFNO";
					int icust = 0;
					for (CutomerPersonalDtls cust : CutomerPersonalDtls) {
						icust = icust + 1;
						custtype = checkReqData(cust.getCusttype());
						address = checkReqData(cust.getAddress());
						addLine1 = checkReqData(cust.getAddressLine1());
						addLine2 = checkReqData(cust.getAddressLine2());
						corCntry = checkReqData(cust.getCorrCntry() == null ? ""
								: cust.getCorrCntry().trim());
						ResCntry = checkReqData(cust.getCountryRes() == null ? ""
								: cust.getCountryRes().trim());
						custCat = checkReqData(cust.getCustCat() == null ? ""
								: cust.getCustCat().trim());
						custFullName = checkReqData(cust.getCustFullName() == null ? ""
								: cust.getCustFullName().trim());
						custIC = checkReqData(cust.getCustIC() == null ? ""
								: cust.getCustIC().trim());
						custID = checkReqData(cust.getCustID() == null ? ""
								: cust.getCustID().trim());
						if ("".equalsIgnoreCase(custID))
						{
							NTBAusFound=true;
						}
						custPrefix = checkReqData(cust.getCustPrefix() == null ? ""
								: cust.getCustPrefix().trim());
						dob = checkReqData(cust.getDOB() == null ? "" : cust
								.getDOB().trim());
						eidaIssDt = checkReqData(cust.getEIDAIssueDate() == null ? ""
								: cust.getEIDAIssueDate().trim());
						eidaExpDt = checkReqData(cust.getEIDAExpiryDate() == null ? ""
								: cust.getEIDAExpiryDate().trim());
						eidaNo = checkReqData(cust.getEIDANumber() == null ? ""
								: cust.getEIDANumber().trim());
						gender = checkReqData(cust.getGender() == null ? ""
								: cust.getGender().trim());
						homeBrCode = HomeBranch;
						maritalSts = checkReqData(cust.getMaritalSts() == null ? ""
								: cust.getMaritalSts().trim());
						nationality = checkReqData(cust.getNationality() == null ? ""
								: cust.getNationality().trim());
						profitCenter = checkReqData(cust.getProfitCenter() == null ? ""
								: cust.getProfitCenter().trim());
						passExpDt = checkReqData(cust.getPassportExpiryDate() == null ? ""
								: cust.getPassportExpiryDate().trim());
						passIssDt = checkReqData(cust.getPassportIssueDate() == null ? ""
								: cust.getPassportIssueDate().trim());
						passport = checkReqData(cust.getPassportNo() == null ? ""
								: cust.getPassportNo().trim());
						signTyp = checkReqData(cust.getSignatureType() == null ? ""
								: cust.getSignatureType().trim());
						state = checkReqData(cust.getState() == null ? ""
								: cust.getState().trim());
						telLandLine = chkPhoneNumber(checkReqData(cust
								.getTelLandLine() == null ? "" : cust
								.getTelLandLine().trim()));
						telMob = chkPhoneNumber(checkReqData(cust.getTelMob() == null ? ""
								: cust.getTelMob().trim()));
						town = checkReqData(cust.getTown() == null ? "" : cust
								.getTown().trim());
						visaExpDt = checkReqData(cust.getVisaExpiryDate() == null ? ""
								: cust.getVisaExpiryDate().trim());
						visaNo = checkReqData(cust.getVisaNo() == null ? ""
								: cust.getVisaNo().trim());
						visaIssDt = checkReqData(cust.getVisaIssueDate() == null ? ""
								: cust.getVisaIssueDate().trim());
						zip = checkReqData(cust.getZip() == null ? "" : cust
								.getZip().trim());
						sigSeqNo = checkReqData(cust.getsigSeqNo() == null ? ""
								: cust.getsigSeqNo().trim());
						sigUpdReq = checkReqData(cust.getsigUpdReq() == null ? ""
								: cust.getsigUpdReq().trim());
						emailId = checkReqData(cust.getemailId() == null ? ""
								: cust.getemailId().trim());
						nameUpdateFlag = checkReqData(cust.getnameUpdateFlag() == null ? ""
								: cust.getnameUpdateFlag().trim());
						countryResidenceFlag = checkReqData(cust
								.getcountryResidenceFlag() == null ? "" : cust
								.getcountryResidenceFlag().trim());
						countryCorrespondanceFlag = checkReqData(cust
								.getcountryCorrespondanceFlag() == null ? ""
								: cust.getcountryCorrespondanceFlag().trim());
						passportUpdateFlag = checkReqData(cust
								.getpassportUpdateFlag() == null ? "" : cust
								.getpassportUpdateFlag().trim());
						visaUpdateFlag = checkReqData(cust.getvisaUpdateFlag() == null ? ""
								: cust.getvisaUpdateFlag().trim());
						mobileUpdateFlag = checkReqData(cust
								.getmobileUpdateFlag() == null ? "" : cust
								.getmobileUpdateFlag().trim());
						emailUpdateFlag = checkReqData(cust
								.getemailUpdateFlag() == null ? "" : cust
								.getemailUpdateFlag().trim());
						requestClassification = checkReqData(cust
								.getRequestClassification() == null ? "AUS" : cust
								.getRequestClassification().trim());
						rmCode = checkReqData(cust
								.getRmCode() == null ? "" : cust
								.getRmCode().trim());
						custUpgradeflg = checkReqData(cust
								.getCustUpgradeflg() == null ? "" : cust
								.getCustUpgradeflg().trim());
						embossName = checkReqData(cust
								.getEmbossName() == null ? "" : cust
								.getEmbossName().trim());
						cardProductGroup = checkReqData(cust
								.getCardProductGroup() == null ? "" : cust
								.getCardProductGroup().trim());
						instantFlag = checkReqData(cust
								.getInstantFlag() == null ? "" : cust
								.getInstantFlag().trim());
						customerClassification = checkReqData(cust
								.getCustomerClassification() == null ? "" : cust
								.getCustomerClassification().trim());
                                                //Added by SHivanshu ATP-426
						secondNationality = checkReqData(cust
								.getSecondNationality() == null ? "" : cust
								.getSecondNationality().trim());
						pepFlag = checkReqData(cust
								.getPepFlag() == null ? "" : cust
								.getPepFlag().trim());
						extrafield1 = checkReqData(cust
								.getExtrafield1() == null ? "" : cust
								.getExtrafield1().trim());
						extrafield2 = checkReqData(cust
								.getExtrafield2() == null ? "" : cust
								.getExtrafield2().trim());

						if (mobileUpdateFlag.equalsIgnoreCase("Y")) {
							mobileCCFlag = mobileUpdateFlag;
						}
						
						/*
						 * attribAUSDetails =
						 * cust.getappAUSAttributes().getattribAUSDetails();
						 * 
						 * log.debug("attribAUSDetails : "+attribAUSDetails);
						 * 
						 * int ApplcnAtt = 0; for (int counter = 0; counter <
						 * attribAUSDetails.length; counter++) {
						 * 
						 * log.debug("attribAUSDetails length : "+attribAUSDetails
						 * .length);
						 * 
						 * ApplcnAtt = ApplcnAtt +1; AUSattrib =
						 * attribAUSDetails[counter].getAUSattrib(); for (int
						 * counter1 = 0; counter1 < AUSattrib.length;
						 * counter1++) {
						 * 
						 * log.debug("AUSattrib length : "+AUSattrib.length);
						 * 
						 * attribAUSKey = checkReqData(
						 * AUSattrib[counter1].getAttribAUSKey() == null ? "" :
						 * AUSattrib[counter1].getAttribAUSKey().trim());
						 * attribAUSValue = checkReqData(
						 * AUSattrib[counter1].getattribAUSValue() == null ? ""
						 * : AUSattrib[counter1].getattribAUSValue().trim());
						 * 
						 * log.debug("attribAUSKey : "+attribAUSKey);
						 * log.debug("attribAUSValue : "+attribAUSValue);
						 * 
						 * if(!attribAUSKey.equalsIgnoreCase("")) {
						 * if(attribAUSKey.equalsIgnoreCase("NameUpdateFlag")) {
						 * nameUpdateFlag = attribAUSValue; } else
						 * if(attribAUSKey
						 * .equalsIgnoreCase("CountryResidenceFlag")) {
						 * countryResidenceFlag = attribAUSValue; } else
						 * if(attribAUSKey
						 * .equalsIgnoreCase("CountryCorrespondanceFlag")) {
						 * countryCorrespondanceFlag = attribAUSValue; } else
						 * if(
						 * attribAUSKey.equalsIgnoreCase("PassportUpdateFlag"))
						 * { passportUpdateFlag = attribAUSValue; } else
						 * if(attribAUSKey.equalsIgnoreCase("VisaUpdateFlag")) {
						 * visaUpdateFlag = attribAUSValue; } } } }
						 */
                        //Added by SHivanshu ATP-426
						sOutputXML = insertAUSDetails(tabName, colAusName,
								icust + "", custtype, address, addLine1,
								addLine2, corCntry, ResCntry, custCat,
								custFullName, custIC, custID, custPrefix, dob,
								eidaIssDt, eidaExpDt, eidaNo, gender,
								homeBrCode, maritalSts, nationality,
								profitCenter, passExpDt, passIssDt, passport,
								signTyp, state, telLandLine, telMob, town,
								visaExpDt, visaNo, visaIssDt, zip, sigSeqNo,
								sigUpdReq, emailId, nameUpdateFlag,
								countryResidenceFlag,
								countryCorrespondanceFlag, passportUpdateFlag,
								visaUpdateFlag, mobileUpdateFlag,
								emailUpdateFlag, custIC, custPrefix, dob,
								passport, signTyp, custID, homeBrCode,
								nationality, passExpDt, telMob, corCntry,
								ResCntry, custCat, custFullName, visaExpDt,
								telLandLine, visaNo, visaIssDt, passIssDt,
								requestClassification,rmCode,custUpgradeflg,embossName,
								cardProductGroup,instantFlag,compAcctCls,secondNationality,pepFlag,extrafield1,extrafield2);
						xp.setInputXML(sOutputXML);
						if (!xp.getValueOf("MainCode").trim()
								.equalsIgnoreCase(SUCCESS_STATUS)) {
							return sOutputXML;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
		return sOutputXML;
	}

	private String fetchFatcaOwnership(AccountOpening req, int i) {
		if (req.getCompany() != null) {
			try {
				if (req.getCompany().getFatca() != null) {
					if (req.getCompany().getFatca().getFatcaOwnerShipInfo() != null) {
						if (req.getCompany().getFatca().getFatcaOwnerShipInfo()
								.getFatcaOwnerShipDtls() != null) {

							String OwnerName = "", OwnerNumber = "", OwnershipAddress = "", OwnershipSharePercentage = "", OwnerTINorSSN = "", OwnerW9Availability = "";
							String tabName = "USR_0_WBG_TMP_FATCAOWNERDTLS";
							String colName = "LEAD_REFNO,OWNERNAME,OWNERNUMBER,OWNERSHIPADDRESS,OWNERSHIPSHAREPERCENTAGE,OWNERTINORSSN,OWNERW9AVAILABILITY,SYSREFNO";
							if (req.getCompany().getFatca()
									.getFatcaOwnerShipInfo()
									.getFatcaOwnerShipDtls().length > 0) {
								FatcaOwnerShipDtls = req.getCompany()
										.getFatca().getFatcaOwnerShipInfo()
										.getFatcaOwnerShipDtls();
								for (FatcaOwnerShipDtls fownr : FatcaOwnerShipDtls) {
									sOutputXML = "";
									OwnerName = checkReqData(fownr
											.getOwnerName() == null ? ""
											: fownr.getOwnerName().trim());
									OwnerNumber = checkReqData(fownr
											.getOwnerNumber() == null ? ""
											: fownr.getOwnerNumber().trim());
									OwnershipAddress = checkReqData(fownr
											.getOwnershipAddress() == null ? ""
											: fownr.getOwnershipAddress()
													.trim());
									OwnershipSharePercentage = checkReqData(fownr
											.getOwnershipSharePercentage() == null ? ""
											: fownr.getOwnershipSharePercentage()
													.trim());
									OwnerTINorSSN = checkReqData(fownr
											.getOwnerTINorSSN() == null ? ""
											: fownr.getOwnerTINorSSN().trim());
									OwnerW9Availability = checkReqData(fownr
											.getOwnerW9Availability() == null ? ""
											: fownr.getOwnerW9Availability()
													.trim());
									if (avoidEmptyInsert(OwnerName,
											OwnerNumber, OwnershipAddress,
											OwnershipSharePercentage,
											OwnerTINorSSN, OwnerW9Availability)) {
										sOutputXML = insertFatcaOwnerDtls(
												tabName, colName, OwnerName,
												OwnerNumber, OwnershipAddress,
												OwnershipSharePercentage,
												OwnerTINorSSN,
												OwnerW9Availability);
										xp.setInputXML(sOutputXML);
										if (!xp.getValueOf("MainCode")
												.trim()
												.equalsIgnoreCase(
														SUCCESS_STATUS)) {
											return sOutputXML;
										}
									} else {
										sOutputXML = retMainCode();
									}
								}
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sOutputXML;
	}

	private String fetchAffiliatedEntityDtls(AccountOpening req, int i) {
		if (req.getCompany().getKYC().getAffiliatedEntityDtls() != null) {
			try {
				if (req.getCompany().getKYC().getAffiliatedEntityDtls()
						.getAffiliatedEntityInfos() != null) {
					if (req.getCompany().getKYC().getAffiliatedEntityDtls()
							.getAffiliatedEntityInfos().length > 0) {
						String Address = "", CleitnRelation = "", IncorpPlace = "", LegalCountry = "", Name = "";
						String tabName = "USR_0_WBG_TMP_AFFENTITYDTLS";
						int cnt = 0;
						String colName = "LEAD_REFNO,SNO,ADDRESS,CLEITNRELATION,INCORPPLACE,LEGALCOUNTRY,NAME,SYSREFNO";
						affiliatedEntityInfos = req.getCompany().getKYC()
								.getAffiliatedEntityDtls()
								.getAffiliatedEntityInfos();
						for (AffiliatedEntityInfo aff : affiliatedEntityInfos) {
							cnt = cnt + 1;
							Address = checkReqData(aff.getAddress() == null ? ""
									: aff.getAddress().trim());
							CleitnRelation = checkReqData(aff
									.getCleitnRelation() == null ? "" : aff
									.getCleitnRelation().trim());
							IncorpPlace = checkReqData(aff.getIncorpPlace() == null ? ""
									: aff.getIncorpPlace().trim());
							LegalCountry = checkReqData(aff.getLegalCountry() == null ? ""
									: aff.getLegalCountry().trim());
							Name = checkReqData(aff.getName() == null ? ""
									: aff.getName().trim());
							sOutputXML = insertAffEntityDtls(tabName, colName,
									cnt, Address, CleitnRelation, IncorpPlace,
									LegalCountry, Name);
							xp.setInputXML(sOutputXML);
							if (!xp.getValueOf("MainCode").trim()
									.equalsIgnoreCase(SUCCESS_STATUS)) {
								return sOutputXML;
							}
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sOutputXML;
	}

	private String fetchMajorClientsInfo(AccountOpening req, int i) {
		try {
			if (req.getCompany().getKYC().getMajorClientsInfo()
					.getMajorClientsDtls() != null) {
				if (req.getCompany().getKYC().getMajorClientsInfo()
						.getMajorClientsDtls().length > 0) {
					String Address = "", MajType = "Client", IncorpPlace = "", LineOfBus = "", Name = "";
					String tabName = "USR_0_WBG_TMP_MAJORDTLS";
					String colName = "LEAD_REFNO,SNO,ADDRESS ,MAJTYPE ,INCORPPLACE ,LINEOFBUS ,NAME,SYSREFNO";
					majorClientsDtls = req.getCompany().getKYC()
							.getMajorClientsInfo().getMajorClientsDtls();
					int cnt = 0;
					for (MajorClientsDtls majCli : majorClientsDtls) {
						cnt = cnt + 1;
						Address = checkReqData(majCli.getAddress() == null ? ""
								: majCli.getAddress().trim());
						IncorpPlace = checkReqData(majCli.getIncorpPlace() == null ? ""
								: majCli.getIncorpPlace().trim());
						LineOfBus = checkReqData(majCli.getLineOfBus() == null ? ""
								: majCli.getLineOfBus().trim());
						Name = checkReqData(majCli.getName() == null ? ""
								: majCli.getName().trim());
						sOutputXML = insertMajorClientDtls(tabName, colName,
								cnt, Address, MajType, IncorpPlace, LineOfBus,
								Name);
						xp.setInputXML(sOutputXML);
						if (!xp.getValueOf("MainCode").trim()
								.equalsIgnoreCase(SUCCESS_STATUS)) {
							return sOutputXML;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sOutputXML;
	}

	private String fetchMajorSuppliersInfo(AccountOpening req, int i) {

		try {
			if (req.getCompany().getKYC().getMajorSuppliersInfo()
					.getMajorSuppliersDtls() != null) {
				if (req.getCompany().getKYC().getMajorSuppliersInfo()
						.getMajorSuppliersDtls().length > 0) {
					String Address = "", MajType = "Supplier", IncorpPlace = "", LineOfBus = "", Name = "";
					String tabName = "USR_0_WBG_TMP_MAJORDTLS";
					String colName = "LEAD_REFNO,SNO,ADDRESS ,MAJTYPE ,INCORPPLACE ,LINEOFBUS ,NAME,SYSREFNO";
					majorSuppliersDtls = req.getCompany().getKYC()
							.getMajorSuppliersInfo().getMajorSuppliersDtls();
					int cnt = 0;
					for (MajorSuppliersDtls majSup : majorSuppliersDtls) {
						cnt = cnt + 1;
						Address = checkReqData(majSup.getAddress() == null ? ""
								: majSup.getAddress().trim());
						IncorpPlace = checkReqData(majSup.getIncorpPlace() == null ? ""
								: majSup.getIncorpPlace().trim());
						LineOfBus = checkReqData(majSup.getLineOfBus() == null ? ""
								: majSup.getLineOfBus().trim());
						Name = checkReqData(majSup.getName() == null ? ""
								: majSup.getName().trim());
						sOutputXML = insertMajorSupDtls(tabName, colName, cnt,
								Address, MajType, IncorpPlace, LineOfBus, Name);
						xp.setInputXML(sOutputXML);
						if (!xp.getValueOf("MainCode").trim()
								.equalsIgnoreCase(SUCCESS_STATUS)) {
							return sOutputXML;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sOutputXML;

	}

	private String fetchOwnershipDtls(AccountOpening req, int i) {

		try {
			if (req.getCompany().getKYC().getOwnershipInfo()
					.getOwnershipDetails() != null) {
				if (req.getCompany().getKYC().getOwnershipInfo()
						.getOwnershipDetails().length > 0) {
					ownershipDetails = req.getCompany().getKYC()
							.getOwnershipInfo().getOwnershipDetails();
					String Address = "", IsPep = "", LegalCntry = "", Name = "", Nationality = "", Share = "";
					String tabName = "USR_0_WBG_TMP_OWNERSHIPINFO";
					String colName = "LEAD_REFNO,SNO,ADDRESS,ISPEP,LEGALCNTRY,NAME,NATIONALITY,SHARES,SYSREFNO";
					int cnt = 0;
					for (OwnershipDetails ownr : ownershipDetails) {
						cnt = cnt + 1;
						Address = checkReqData(ownr.getAddress() == null ? ""
								: ownr.getAddress().trim());
						IsPep = checkReqData(ownr.getIsPep() == null ? ""
								: ownr.getIsPep().trim());
						LegalCntry = checkReqData(ownr.getLegalCntry() == null ? ""
								: ownr.getLegalCntry().trim());
						Name = checkReqData(ownr.getName() == null ? "" : ownr
								.getName().trim());
						Nationality = checkReqData(ownr.getNationality() == null ? ""
								: ownr.getNationality().trim());
						Share = checkReqData(ownr.getShare() == null ? ""
								: ownr.getShare().trim());
						sOutputXML = insertOwnershipInfoDtls(tabName, colName,
								cnt, Address, IsPep, LegalCntry, Name,
								Nationality, Share);
						xp.setInputXML(sOutputXML);
						if (!xp.getValueOf("MainCode").trim()
								.equalsIgnoreCase(SUCCESS_STATUS)) {
							return sOutputXML;
						}
					}
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return sOutputXML;

	}

	private String fetchSubsidiariesInfo(AccountOpening req, int i) {

		try {
			if (req.getCompany().getKYC().getSubsidiariesInfo()
					.getSubsidiariesDetails() != null) {
				if (req.getCompany().getKYC().getSubsidiariesInfo()
						.getSubsidiariesDetails().length > 0) {
					subsidiariesDetails = req.getCompany().getKYC()
							.getSubsidiariesInfo().getSubsidiariesDetails();
					String Address = "", BusinessNature1 = "", IncorpPlace = "", Name = "", Share = "";
					String tabName = "USR_0_WBG_TMP_SUBSIDIARIES";
					String colName = "LEAD_REFNO,SNO,ADDRESS,BUSINESSNATURE,INCORPPLACE,NAME,SHARES,SYSREFNO";
					int cnt = 0;
					for (SubsidiariesDetails ssd : subsidiariesDetails) {
						cnt = cnt + 1;
						Address = checkReqData(ssd.getAddress() == null ? ""
								: ssd.getAddress().trim());
						BusinessNature1 = checkReqData(ssd.getBusinessNature() == null ? ""
								: ssd.getBusinessNature().trim());
						IncorpPlace = checkReqData(ssd.getIncorpPlace() == null ? ""
								: ssd.getIncorpPlace().trim());
						Name = checkReqData(ssd.getName() == null ? "" : ssd
								.getName().trim());
						Share = checkReqData(ssd.getShare() == null ? "" : ssd
								.getShare());
						sOutputXML = insertSubsidiariesDetailsDtls(tabName,
								colName, cnt, Address, BusinessNature1,
								IncorpPlace, Name, Share);
						xp.setInputXML(sOutputXML);
						if (!xp.getValueOf("MainCode").trim()
								.equalsIgnoreCase(SUCCESS_STATUS)) {
							return sOutputXML;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sOutputXML;

	}

	private String fetchKYCUboInfo(AccountOpening req, int i) {
		try {
			if (req.getCompany().getKYC().getUboInfo().getUboDetails() != null) {
				if (req.getCompany().getKYC().getUboInfo().getUboDetails().length > 0) {
					String isPep = "", LegalCntry = "", Name = "", Nationality = "", Share = "";
					String tabName = "USR_0_WBG_TMP_KYCUBODETAILS";
					String colName = "LEAD_REFNO,SNO,ISPEP,LEGALCNTRY,NAME,NATIONALITY,SHARES,SYSREFNO";
					uboDetails = req.getCompany().getKYC().getUboInfo()
							.getUboDetails();
					int cnt = 0;
					for (UBODetails ubod : uboDetails) {
						cnt = cnt + 1;
						isPep = checkReqData(ubod.getIsPep() == null ? ""
								: ubod.getIsPep().trim());
						LegalCntry = checkReqData(ubod.getLegalCntry() == null ? ""
								: ubod.getLegalCntry().trim());
						Name = checkReqData(ubod.getName() == null ? "" : ubod
								.getName().trim());
						Nationality = checkReqData(ubod.getNationality() == null ? ""
								: ubod.getNationality().trim());
						Share = checkReqData(ubod.getShare() == null ? ""
								: ubod.getShare().trim());
						sOutputXML = insertKYCUBODtls(tabName, colName, cnt,
								isPep, LegalCntry, Name, Nationality, Share);
						xp.setInputXML(sOutputXML);
						if (!xp.getValueOf("MainCode").trim()
								.equalsIgnoreCase(SUCCESS_STATUS)) {
							return sOutputXML;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sOutputXML;
	}

	private String fetchCntryControlPersonDtls(AccountOpening req, int i) {
		try {
			if (req.getCompany().getCrsDetails().getEntityControlPersonInfo() != null) {
				if (req.getCompany().getCrsDetails()
						.getEntityControlPersonInfo()
						.getEntityControlPersonsDtls().length > 0) {
					String CPBirthCity = "", CPBirthCountry = "", CPBuildingName = "", CPCity = "", CPControlTypeId = "", CPCountry = "", CPDateOfBirth = "", CPEmirate = "", CPFirstName = "", CPFlatVillaNo = "", CPId = "", CPLastName = "", CPPrimaryKey = "", CPReasonId = "", CPStreet = "", CPTaxpayerIdNo = "", CPTaxResCountry = "", CPReasonDes = "";
					String tabName = "USR_0_WBG_TMP_CP_DETLS";
					int iCP = 0;
					String colName = "Lead_RefNo,SNO,CPBirthCity,CPBirthCountry,CPBuildingName,CPCity,CPControlTypeId,CPCountry,CPDateOfBirth,CPEmirate,CPFirstName,CPFlatVillaNo,CPId,CPLastName,CPPrimaryKey,CPReasonId,CPStreet,CPTaxpayerIdNo,CPTaxResCountry,reason_desc,SysrefNo";
					entityControlPersonsDtls = req.getCompany().getCrsDetails()
							.getEntityControlPersonInfo()
							.getEntityControlPersonsDtls();
					for (EntityControlPersonsDtls ecp : entityControlPersonsDtls) {
						iCP = iCP + 1;
						CPBirthCity = checkReqData(ecp
								.getControlPersonBirthCity() == null ? "" : ecp
								.getControlPersonBirthCity().trim());
						CPBirthCountry = checkReqData(ecp
								.getControlPersonBirthCountry() == null ? ""
								: ecp.getControlPersonBirthCountry().trim());
						CPBuildingName = checkReqData(ecp
								.getControlPersonBuildingName() == null ? ""
								: ecp.getControlPersonBuildingName().trim());
						CPCity = checkReqData(ecp.getControlPersonCity() == null ? ""
								: ecp.getControlPersonCity().trim());
						CPControlTypeId = checkReqData(ecp
								.getControlPersonControlTypeId() == null ? ""
								: ecp.getControlPersonControlTypeId().trim());
						CPCountry = checkReqData(ecp.getControlPersonCountry() == null ? ""
								: ecp.getControlPersonCountry().trim());
						CPDateOfBirth = checkReqData(ecp
								.getControlPersonDateOfBirth() == null ? ""
								: ecp.getControlPersonDateOfBirth().trim());
						CPEmirate = chkStateReqData(ecp
								.getControlPersonEmirate() == null ? "" : ecp
								.getControlPersonEmirate().trim()); // Raghav
																	// Need to
																	// confirm
																	// for this
						CPFirstName = checkReqData(ecp
								.getControlPersonFirstName() == null ? "" : ecp
								.getControlPersonFirstName().trim());
						CPFlatVillaNo = checkReqData(ecp
								.getControlPersonFlatVillaNo() == null ? ""
								: ecp.getControlPersonFlatVillaNo().trim());
						CPId = iCP + "";// (ecp.getControlPersonId() == null ?
										// "" :
										// ecp.getControlPersonId().trim());
						CPLastName = checkReqData(ecp
								.getControlPersonLastName() == null ? "" : ecp
								.getControlPersonLastName().trim());
						CPPrimaryKey = iCP + "";// (ecp.getControlPersonPrimaryKey()
												// == null ? "" :
												// ecp.getControlPersonPrimaryKey().trim());
						CPStreet = checkReqData(ecp.getControlPersonStreet() == null ? ""
								: ecp.getControlPersonStreet().trim());
						/*
						 * controlPersonTaxResCountryDtls =
						 * ecp.getcontrolPersonTaxResCountryInfo
						 * ().getcontrolPersonTaxResCountryDtls(); int
						 * cntrlpsnltaxescntrydtls = 0; for (int counter = 0;
						 * counter < controlPersonTaxResCountryDtls.length;
						 * counter++) { cntrlpsnltaxescntrydtls =
						 * cntrlpsnltaxescntrydtls + 1; CPReasonId
						 * =checkReqData(
						 * controlPersonTaxResCountryDtls[counter]
						 * .getControlPersonReasonId()== null ? "" :
						 * controlPersonTaxResCountryDtls
						 * [counter].getControlPersonReasonId().trim());
						 * CPTaxpayerIdNo =
						 * checkReqData(controlPersonTaxResCountryDtls
						 * [counter].getControlPersonTaxpayerIdNo() == null ? ""
						 * : controlPersonTaxResCountryDtls[counter].
						 * getControlPersonTaxpayerIdNo().trim());
						 * CPTaxResCountry =
						 * checkReqData(controlPersonTaxResCountryDtls
						 * [counter].getControlPersonTaxResCountry() == null ?
						 * "" : controlPersonTaxResCountryDtls[counter].
						 * getControlPersonTaxResCountry().trim()); CPReasonDes
						 * =
						 * checkReqData(controlPersonTaxResCountryDtls[counter]
						 * .getControlPersonReasonIdDesc() == null ? "" :
						 * controlPersonTaxResCountryDtls
						 * [counter].getControlPersonReasonIdDesc().trim());
						 * log.
						 * debug("Inside controlPersonTaxResCountryDtls "+CPReasonId
						 * );
						 * log.debug("Inside controlPersonTaxResCountryDtls "+
						 * CPTaxpayerIdNo);
						 * log.debug("Inside controlPersonTaxResCountryDtls "
						 * +CPTaxResCountry);
						 * log.debug("Inside controlPersonTaxResCountryDtls "
						 * +CPReasonDes); }
						 */
						// CPReasonId =
						// checkReqData(ecp.getControlPersonReasonId() == null ?
						// "" : ecp.getControlPersonReasonId().trim());
						// CPTaxpayerIdNo =
						// checkReqData(ecp.getControlPersonTaxpayerIdNo() ==
						// null ? "" :
						// ecp.getControlPersonTaxpayerIdNo().trim());
						// CPTaxResCountry =
						// checkReqData(ecp.getControlPersonTaxResCountry() ==
						// null ? "" :
						// ecp.getControlPersonTaxResCountry().trim());
						// CPReasonDes =
						// checkReqData(ecp.getControlPersonReasonIdDesc() ==
						// null ? "" :
						// ecp.getControlPersonReasonIdDesc().trim());
						sOutputXML = insertControlPersonDtls(tabName, colName,
								iCP, CPBirthCity, CPBirthCountry,
								CPBuildingName, CPCity, CPControlTypeId,
								CPCountry, CPDateOfBirth, CPEmirate,
								CPFirstName, CPFlatVillaNo, CPId, CPLastName,
								CPPrimaryKey, CPReasonId, CPStreet,
								CPTaxpayerIdNo, CPTaxResCountry, CPReasonDes);
						xp.setInputXML(sOutputXML);
						if (!xp.getValueOf("MainCode").trim()
								.equalsIgnoreCase(SUCCESS_STATUS)) {
							return sOutputXML;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sOutputXML;
	}

	private String fetchCntrlPerTaxResCntryDtls(AccountOpening req, int i) {

		String CPReasonId = "", CPTaxpayerIdNo = "", CPTaxResCountry = "", CPReasonDes = "", CPPrimaryKey = "";
		String taxTab = "USR_0_WBG_TMP_CNTRL_PER_TAXRES";
		String taxCol = "LEAD_REFNO,SNO,TAXRESIDENCECOUNTRY,TAXPAYERIDNUMBER,REASONID,REASONDESC,CNTRLPERPRIMARYKEY,SYSREFNO";
		try {
			if (req.getCompany().getCrsDetails().getEntityControlPersonInfo() != null) {
				if (req.getCompany().getCrsDetails()
						.getEntityControlPersonInfo()
						.getEntityControlPersonsDtls().length > 0) {
					entityControlPersonsDtls = req.getCompany().getCrsDetails()
							.getEntityControlPersonInfo()
							.getEntityControlPersonsDtls();
					int iCP = 0;
					for (EntityControlPersonsDtls ecp : entityControlPersonsDtls) {
						iCP = iCP + 1;

						CPPrimaryKey = iCP + "";
						controlPersonTaxResCountryDtls = ecp
								.getcontrolPersonTaxResCountryInfo()
								.getcontrolPersonTaxResCountryDtls();
						int cntrlpsnltaxescntrydtls = 0;
						for (int counter = 0; counter < controlPersonTaxResCountryDtls.length; counter++) {
							cntrlpsnltaxescntrydtls = cntrlpsnltaxescntrydtls + 1;
							CPReasonId = checkReqData(controlPersonTaxResCountryDtls[counter]
									.getControlPersonReasonId() == null ? ""
									: controlPersonTaxResCountryDtls[counter]
											.getControlPersonReasonId().trim());
							CPTaxpayerIdNo = checkReqData(controlPersonTaxResCountryDtls[counter]
									.getControlPersonTaxpayerIdNo() == null ? ""
									: controlPersonTaxResCountryDtls[counter]
											.getControlPersonTaxpayerIdNo()
											.trim());
							CPTaxResCountry = checkReqData(controlPersonTaxResCountryDtls[counter]
									.getControlPersonTaxResCountry() == null ? ""
									: controlPersonTaxResCountryDtls[counter]
											.getControlPersonTaxResCountry()
											.trim());
							CPReasonDes = checkReqData(controlPersonTaxResCountryDtls[counter]
									.getControlPersonReasonIdDesc() == null ? ""
									: controlPersonTaxResCountryDtls[counter]
											.getControlPersonReasonIdDesc()
											.trim());
							log.debug("Inside controlPersonTaxResCountryDtls "
									+ CPReasonId);
							log.debug("Inside controlPersonTaxResCountryDtls "
									+ CPTaxpayerIdNo);
							log.debug("Inside controlPersonTaxResCountryDtls "
									+ CPTaxResCountry);
							log.debug("Inside controlPersonTaxResCountryDtls "
									+ CPReasonDes);
							if (avoidEmptyInsert(CPReasonId, CPTaxpayerIdNo,
									CPTaxResCountry)) {
								sOutputXML = insertCntrlPersonTaxResDtls(
										taxTab, taxCol,
										cntrlpsnltaxescntrydtls,
										CPTaxResCountry, CPTaxpayerIdNo,
										CPReasonId, CPReasonDes, CPPrimaryKey);
								xp.setInputXML(sOutputXML);
								if (!xp.getValueOf("MainCode").trim()
										.equalsIgnoreCase(SUCCESS_STATUS)) {
									return sOutputXML;
								}
							} else {
								sOutputXML = retMainCode();
							}
						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sOutputXML;
	}

	private String fetchCrsTaxResCntryDtls(AccountOpening req, int i) {
		String ReasonDesc = "", ReasonId = "", ReportableFlag = "", TaxResidenceCountry = "", TaxpayerIdNumber = "";
		String taxTab = "USR_0_WBG_TMP_CRS_TAXRES";
		String taxCol = "LEAD_REFNO,SNO,REASONDESC ,REASONID ,REPORTABLEFLAG ,TAXRESIDENCECOUNTRY ,TAXPAYERIDNUMBER ,SYSREFNO";
		if (req.getCompany() != null) {
			if (req.getCompany().getCrsDetails() != null) {
				try {
					if (req.getCompany().getCrsDetails()
							.getTaxResidenceCountryInfo() != null) {
						int iCrsT = 0;
						if (req.getCompany().getCrsDetails()
								.getTaxResidenceCountryInfo()
								.getTaxResidenceCountriesDtls().length > 0) {
							taxResidenceCountriesDtls = req.getCompany()
									.getCrsDetails()
									.getTaxResidenceCountryInfo()
									.getTaxResidenceCountriesDtls();
							for (TaxResidenceCountriesDtls trc : taxResidenceCountriesDtls) {
								sOutputXML = "";
								iCrsT = iCrsT + 1;
								ReasonDesc = checkReqData(trc.getReasonDesc() == null ? ""
										: trc.getReasonDesc().trim());
								ReasonId = checkReqData(trc.getReasonId() == null ? ""
										: trc.getReasonId().trim());
								ReportableFlag = checkReqData(trc
										.getReportableFlag() == null ? "" : trc
										.getReportableFlag().trim());
								TaxResidenceCountry = checkReqData(trc
										.getTaxResidenceCountry() == null ? ""
										: trc.getTaxResidenceCountry().trim());
								TaxpayerIdNumber = checkReqData(trc
										.getTaxpayerIdNumber() == null ? ""
										: trc.getTaxpayerIdNumber().trim());
								if (avoidEmptyInsert(ReasonDesc, ReasonId,
										ReportableFlag, TaxResidenceCountry)) {
									sOutputXML = insertCrsTaxResDtls(taxTab,
											taxCol, iCrsT, ReasonDesc,
											ReasonId, ReportableFlag,
											TaxResidenceCountry,
											TaxpayerIdNumber);
									xp.setInputXML(sOutputXML);
									if (!xp.getValueOf("MainCode").trim()
											.equalsIgnoreCase(SUCCESS_STATUS)) {
										return sOutputXML;
									}
								} else {
									sOutputXML = retMainCode();
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return sOutputXML;
	}

	private String insertAUSDetails(String tabName, String ColName,
			String srno, String custtype, String address, String addLine1,
			String addLine2, String corCntry, String ResCntry, String custCat,
			String custFullName, String custIC, String custID,
			String custPrefix, String dob, String eidaIssDt, String eidaExpDt,
			String eidaNo, String gender, String homeBrCode, String maritalSts,
			String nationality, String profitCenter, String passExpDt,
			String passIssDt, String passport, String signTyp, String state,
			String telLandLine, String telMob, String town, String visaExpDt,
			String visaNo, String visaIssDt, String zip, String sigSeqNo,
			String sigUpdReq, String emailId, String nameUpdateFlag,
			String countryResidenceFlag, String countryCorrespondanceFlag,
			String passportUpdateFlag, String visaUpdateFlag,
			String mobileUpdateFlag, String emailUpdateFlag, String custIC_IT,
			String custPrefix_IT, String dob_IT, String passport_IT,
			String signTyp_IT, String custID_IT, String homeBrCode_IT,
			String nationality_IT, String passExpDt_IT, String telMob_IT,
			String corCntry_IT, String ResCntry_IT, String custCat_IT,
			String custFullName_IT, String visaExpDt_IT, String telLandLine_IT,
			String visaNo_IT, String visaIssDt_IT, String passIssDt_IT,
			String requestClassification, String rmCode, String custUpgradeflg,String embossName,
			String cardProductGroup, String instantFlag,String compAcctCls,String secondNationality,String pepFlag,String extrafield1,String extrafield2) { //Added by SHivanshu ATP-426
		sOutputXML = "";
		// String colName
		// ="LEAD_REFNO,CUSTTYPE,ADDRESS,ADDLINE1,ADDLINE2,CORCNTRY,RESCNTRY,CUSTCAT,CUSTFULLNAME,CUSTIC,CUSTID,CUSTPREFIX,DOB,EIDAISSDT,EIDAEXPDT,EIDANO,GENDER,HOMEBRCODE,MARITALSTS,NATIONALITY,PROFITCENTER,PASSEXPDT,PASSISSDT,PASSPORT,SIGNTYP,STATE,TELLANDLINE,TELMOB,TOWN,VISAEXPDT,VISANO,VISAISSDT,ZIP,SIG_SEQ_NO,SIG_UPD_REQ,EMAILID,NAME_UPDATE_FLAG,COUNTRY_RESIDENCE_FLAG,COUNTRY_CORRESPONDANCE_FLAG,PASSPORT_UPDATE_FLAG,VISA_UPDATE_FLAG,SYSREFNO";
		String tempValues = "" + LeadRefNo + "#~#" + srno + "#~#" + custtype
				+ "#~#" + address + "#~#" + addLine1 + "#~#" + addLine2 + "#~#"
				+ corCntry + "#~#" + ResCntry + "#~#" + custCat + "#~#"
				+ custFullName + "#~#" + custIC + "#~#" + custID + "#~#"
				+ custPrefix + "#~#" + dob + "#~#" + eidaIssDt + "#~#"
				+ eidaExpDt + "#~#" + eidaNo + "#~#" + gender + "#~#"
				+ homeBrCode + "#~#" + maritalSts + "#~#" + nationality + "#~#"
				+ profitCenter + "#~#" + passExpDt + "#~#" + passIssDt + "#~#"
				+ passport + "#~#" + signTyp + "#~#" + state + "#~#"
				+ telLandLine + "#~#" + telMob + "#~#" + town + "#~#"
				+ visaExpDt + "#~#" + visaExpDt + "#~#" + visaNo + "#~#"
				+ visaIssDt + "#~#" + zip + "#~#" + sigSeqNo + "#~#"
				+ sigUpdReq + "#~#" + emailId + "#~#" + nameUpdateFlag + "#~#"
				+ countryResidenceFlag + "#~#" + countryCorrespondanceFlag
				+ "#~#" + passportUpdateFlag + "#~#" + visaUpdateFlag + "#~#"
				+ mobileUpdateFlag + "#~#" + emailUpdateFlag + "#~#" + custID
				+ "#~#" + sysRefNumber + "#~#" + requestClassification +"#~#" + rmCode
				+ "#~#" + custUpgradeflg + "#~#" + embossName + "#~#" + cardProductGroup
				+ "#~#" + instantFlag + "#~#" + compAcctCls + "#~#" + secondNationality + "#~#" + pepFlag+ "#~#" + extrafield1 + "#~#" + extrafield2; //Added by SHivanshu ATP-426
		String sValues = removeLastCamma(prepareColValues(tempValues));
		sInputXML = getDBInsertInput(tabName, ColName, sValues, sCabinetName,
				sUserDBId);
		try {
			sOutputXML = executeAPI(sInputXML);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sOutputXML;
	}

	protected String insertDocDetails(String tabName, String ColName,
			String docEntyAus, String iDoc, String DocName, String DocType,
			String DocumentFor, String DocumentIndex, String UploadDt) {
		sOutputXML = "";
		String tempValues = "" + LeadRefNo + "#~#" + docEntyAus + "#~#" + iDoc
				+ "#~#" + DocName + "#~#" + DocType + "#~#" + DocumentFor
				+ "#~#" + DocumentIndex + "#~#" + UploadDt + "#~#"
				+ sysRefNumber;
		String sValues = removeLastCamma(prepareColValues(tempValues));
		sInputXML = getDBInsertInput(tabName, ColName, sValues, sCabinetName,
				sUserDBId);
		try {
			sOutputXML = executeAPI(sInputXML);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sOutputXML;
	}

	private String prepareColValues(String tempValues) {

		StringBuffer sbBuffer = new StringBuffer();
		String tmp = "";
		if (tempValues.indexOf("#~#") > -1) {
			String[] temp = tempValues.split("#~#", -1);
			for (int counter = 0; counter < temp.length; counter++) {
				tmp = checkSplChar(temp[counter]);
				sbBuffer.append("'");
				sbBuffer.append(tmp);
				sbBuffer.append("',");
			}
		}
		return sbBuffer.toString();
	}

	private String checkSplChar(String str) {

		if (str.indexOf("'") > -1) {
			str = str.replaceAll("'", "''");
		}
		if (str.indexOf("&") > -1) {
			str = str.replaceAll("&", "'||chr(38)||'");
		}
		return str;
	}

	private void fetchHeader(AccountOpening req, int i) {
		if (req.getHeader() != null) {
			Header = req.getHeader();
			usecaseID = checkReqData(Header.getUsecaseID());
			versionNo = checkReqData(Header.getVersionNo());
			serviceAction = checkReqData(Header.getServiceAction());
			correlationID = checkReqData(Header.getCorrelationID());
			senderID = checkReqData(Header.getSenderID());
			consumer = checkReqData(Header.getConsumer());
			repTimeStamp = checkReqData(Header.getRepTimeStamp());
			username = checkReqData(Header.getUsername());
			credentials = checkReqData(Header.getCredentials());
			sysRefNumber = checkReqData(Header.getSysRefNumber());
			serviceName = checkReqData(Header.getServiceName());
			reqTimeStamp = checkReqData(Header.getReqTimeStamp());
			returnCode = checkReqData(Header.getReturnCode());

			log.debug("Inside Fetch Request Parameters");

		}
	}

	private String insertKYCGenericDetails(String tabName, String colName,
			String IsTradeContract, String IsUtilityBill, String IsBankAccStmt,
			String IsPhoneBill, String Others, String IsCBPayment,
			String PurposeOfPayment, String NoOfPaymentPerMonth,
			String MonthlyValPayment, String CntryMadetoRcvdFrom,
			String CashCDM, String IsCashCDM, String IsChequesDraft,
			String ChequesDraft, String IsTransfers, String Transfers,
			String IsServiceReq, String ServiceProviderName,
			String IncorporationPlace, String FormationDate,
			String PhyBusinessLocation, String IsAnnualIncomeActualAED,
			String ISAnnualIncomeEstAED, String PrimaryTradeLocation,
			String NoofUaeBranch, String UaeBrachPrimaryLocation,
			String NoOfNonUaeBranch, String NonUaeBrachPrimaryLocation,
			String NoOfEmp, String Website, String TotalAssetAED,
			String AnnualSalRevnueAED, String NetProfitAED,
			String IsClientVisit, String DateOfVisit, String LocationOfVisit,
			String AdcbStaffName, String PersonMetWith, String TypeOfCompany,
			String TypeOfComSetup, String TypeOfCompStrength,
			String TypeOfCompLocation, String ActivityConducted,
			String IsOptSubEntity, String OptSubEntity, String RmAssessment,
			String CIF, String ProfilingPurpose, String RelationshipSince,
			String BusinessNature, String PurposeAOADCB, String ActiveAccountFlag,
			String PurposeAOADCBOthers, String IsDualGoods, String DualGoodsType,
			String IsOffshoreLocation, String IsFreezone,
			String IsAMLCFTPolicy, String IsNUaeTradArmWeapons,
			String IsNUaedefRelEqup, String IsHawala, String IsSanctioned,
			String IsIncRiskJuri, String IsShellCompany,
			String IsDeemedTaxEvasion, String IsBusLnkSanctioned,
			String IsVertualFlexiDesk, String IsNomineeOwnShipStr,
			String IsIssuedBearerShare, String IsVertualCryptoCur,
			String IsNUaeOwnedEnty, String IsNUaePep, String IsPEPRiskQ3,
			String IsOverAllRiskInc, String IsOverAllRiskUnact, String IsUboHawala,
			String IsUboBusTradLnkSanctioned, String SourceOfFund,
			String bGHName, String bUHName, String lMName, String staffName) {
		sOutputXML = "";
		String tempValues = "" + LeadRefNo + "#~#" + IsTradeContract + "#~#"
				+ IsUtilityBill + "#~#" + IsBankAccStmt + "#~#" + IsPhoneBill
				+ "#~#" + Others + "#~#" + IsCBPayment + "#~#"
				+ PurposeOfPayment + "#~#" + NoOfPaymentPerMonth + "#~#"
				+ MonthlyValPayment + "#~#" + CntryMadetoRcvdFrom + "#~#"
				+ CashCDM + "#~#" + IsCashCDM + "#~#" + IsChequesDraft + "#~#"
				+ ChequesDraft + "#~#" + IsTransfers + "#~#" + Transfers
				+ "#~#" + IsServiceReq + "#~#" + ServiceProviderName + "#~#"
				+ IncorporationPlace + "#~#" + FormationDate + "#~#"
				+ PhyBusinessLocation + "#~#" + IsAnnualIncomeActualAED + "#~#"
				+ ISAnnualIncomeEstAED + "#~#" + PrimaryTradeLocation + "#~#"
				+ NoofUaeBranch + "#~#" + UaeBrachPrimaryLocation + "#~#"
				+ NoOfNonUaeBranch + "#~#" + NonUaeBrachPrimaryLocation + "#~#"
				+ NoOfEmp + "#~#" + Website + "#~#" + TotalAssetAED + "#~#"
				+ AnnualSalRevnueAED + "#~#" + NetProfitAED + "#~#"
				+ IsClientVisit + "#~#" + DateOfVisit + "#~#" + LocationOfVisit
				+ "#~#" + AdcbStaffName + "#~#" + PersonMetWith + "#~#"
				+ TypeOfCompany + "#~#" + TypeOfComSetup + "#~#"
				+ TypeOfCompStrength + "#~#" + TypeOfCompLocation + "#~#"
				+ ActivityConducted + "#~#" + IsOptSubEntity + "#~#"
				+ OptSubEntity + "#~#" + RmAssessment + "#~#" + CIF + "#~#"
				+ ProfilingPurpose + "#~#" + RelationshipSince + "#~#"
				+ BusinessNature + "#~#" + PurposeAOADCB + "#~#" + ActiveAccountFlag  + "#~#" 
				+ PurposeAOADCBOthers + "#~#" + IsDualGoods + "#~#" + DualGoodsType + "#~#"
				+ IsOffshoreLocation + "#~#" + IsFreezone + "#~#"
				+ IsAMLCFTPolicy + "#~#" + IsNUaeTradArmWeapons + "#~#"
				+ IsNUaedefRelEqup + "#~#" + IsHawala + "#~#" + IsSanctioned
				+ "#~#" + IsIncRiskJuri + "#~#" + IsShellCompany + "#~#"
				+ IsDeemedTaxEvasion + "#~#" + IsBusLnkSanctioned + "#~#"
				+ IsVertualFlexiDesk + "#~#" + IsNomineeOwnShipStr + "#~#"
				+ IsIssuedBearerShare + "#~#" + IsVertualCryptoCur + "#~#"
				+ IsNUaeOwnedEnty + "#~#" + IsNUaePep + "#~#" + IsPEPRiskQ3 + "#~#" 
				+ IsOverAllRiskInc + "#~#" + IsOverAllRiskUnact + "#~#"
				+ IsUboHawala + "#~#" + IsUboBusTradLnkSanctioned + "#~#"
				+ SourceOfFund + "#~#" + kycSignOff + "#~#" + bGHName + "#~#"
				+ bUHName + "#~#" + lMName + "#~#" + staffName + "#~#"
				+ sysRefNumber;
		String sValues = removeLastCamma(prepareColValues(tempValues));
		sInputXML = getDBInsertInput(tabName, colName, sValues, sCabinetName,
				sUserDBId);
		try {
			sOutputXML = executeAPI(sInputXML);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sOutputXML;
	}

	private void rollBackData(String event) throws IOException, Exception {
		deleteDataRollBack(event);
	}

	// sheenu CR2019 5 parameters added

	private String insertAccInfoDtls(String tabName, String colName, int iAcc,
			String AccBranch, String AccStatus, String AvBal, String ChequeReq,
			String DebitAccNo, String DebitAmt, String DebitCurr,
			String ModeFunding, String ProdCode, String ProdCurr,
			String srvChrgPkg, String debitCardReq,
			String operatingInstructions, String modeOfOperation,
			String accountType, String accountTitle, String cardHolderName,
			String debitCompName, String eStmtFlag, String accntIVRFlag,
			String waiverChargesFlag, String cardProductGroup,
			String noofchequeBooks, String bookSize, String Charges,
			String ChargesWaivedFlag, String WaiverReason, String OtherReason,
			String customerAddresszip, String customerAddress1,
			String customerAddress2, String customerAddressCountry,
			String customerAddressStateEmirate, String customerAddressCity,
			String customerMobileNo, String flagDeliveryMode,
			String CouriertoCustomer_3rdParty, String CollectBranch,
			String thirdPartyName, String thirdPartyMobileNo,
			String photoIdType, String photoIdNo, String ATM_Flag,
			String SI_Flag, String IVR_Facility, String IB_Facility,
			String POS_Facility) {
		String tempValues = "" + LeadRefNo + "#~#" + iAcc + "#~#" + AccBranch
				+ "#~#" + AccStatus + "#~#" + AvBal + "#~#" + ChequeReq + "#~#"
				+ DebitAccNo + "#~#" + DebitAmt + "#~#" + DebitCurr + "#~#"
				+ ModeFunding + "#~#" + ProdCode + "#~#" + ProdCurr + "#~#"
				+ srvChrgPkg + "#~#" + debitCardReq + "#~#"
				+ operatingInstructions + "#~#" + modeOfOperation + "#~#"
				+ accountType + "#~#" + accountTitle + "#~#" + cardHolderName
				+ "#~#" + debitCompName + "#~#" + eStmtFlag + "#~#"
				+ accntIVRFlag + "#~#" + waiverChargesFlag + "#~#"
				+ sysRefNumber + "#~#" + cardProductGroup + "#~#"
				+ noofchequeBooks + "#~#" + bookSize + "#~#" + Charges + "#~#"
				+ ChargesWaivedFlag + "#~#" + WaiverReason + "#~#"
				+ OtherReason + "#~#" + customerAddresszip + "#~#"
				+ customerAddress1 + "#~#" + customerAddress2 + "#~#"
				+ customerAddressCountry + "#~#" + customerAddressStateEmirate
				+ "#~#" + customerAddressCity + "#~#" + customerMobileNo
				+ "#~#" + flagDeliveryMode + "#~#" + CouriertoCustomer_3rdParty
				+ "#~#" + CollectBranch + "#~#" + thirdPartyName + "#~#"
				+ thirdPartyMobileNo + "#~#" + photoIdType + "#~#" + photoIdNo
				+ "#~#" + ATM_Flag + "#~#" + SI_Flag + "#~#" + IVR_Facility
				+ "#~#" + IB_Facility + "#~#" + POS_Facility;

		String sValues = removeLastCamma(prepareColValues(tempValues));
		sInputXML = getDBInsertInput(tabName, colName, sValues, sCabinetName,
				sUserDBId);
		try {
			sOutputXML = executeAPI(sInputXML);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sOutputXML;
	}

	// added sharan CR 21-07-2020
	private String insertCntrlPersonTaxResDtls(String tabName, String colName,
			int iCrsT, String TaxResidenceCountry, String TaxpayerIdNumber,
			String ReasonId, String ReasonDesc, String cntrlPerPrimaryKey) {
		String tempValues = "" + LeadRefNo + "#~#" + iCrsT + "#~#"
				+ TaxResidenceCountry + "#~#" + TaxpayerIdNumber + "#~#"
				+ ReasonId + "#~#" + ReasonDesc + "#~#" + cntrlPerPrimaryKey
				+ "#~#" + sysRefNumber;
		String sValues = removeLastCamma(prepareColValues(tempValues));
		sInputXML = getDBInsertInput(tabName, colName, sValues, sCabinetName,
				sUserDBId);
		try {
			sOutputXML = executeAPI(sInputXML);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sOutputXML;
	}

	private String insertCrsTaxResDtls(String tabName, String colName,
			int iCrsT, String ReasonDesc, String ReasonId,
			String ReportableFlag, String TaxResidenceCountry,
			String TaxpayerIdNumber) {
		String tempValues = "" + LeadRefNo + "#~#" + iCrsT + "#~#" + ReasonDesc
				+ "#~#" + ReasonId + "#~#" + ReportableFlag + "#~#"
				+ TaxResidenceCountry + "#~#" + TaxpayerIdNumber + "#~#"
				+ sysRefNumber;
		String sValues = removeLastCamma(prepareColValues(tempValues));
		sInputXML = getDBInsertInput(tabName, colName, sValues, sCabinetName,
				sUserDBId);
		try {
			sOutputXML = executeAPI(sInputXML);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sOutputXML;
	}

	private String insertControlPersonDtls(String tabName, String colName,
			int iCp, String CPBirthCity, String CPBirthCountry,
			String CPBuildingName, String CPCity, String CPControlTypeId,
			String CPCountry, String CPDateOfBirth, String CPEmirate,
			String CPFirstName, String CPFlatVillaNo, String CPId,
			String CPLastName, String CPPrimaryKey, String CPReasonId,
			String CPStreet, String CPTaxpayerIdNo, String CPTaxResCountry,
			String reasonDesc) {
		String tempValues = "" + LeadRefNo + "#~#" + iCp + "#~#" + CPBirthCity
				+ "#~#" + CPBirthCountry + "#~#" + CPBuildingName + "#~#"
				+ CPCity + "#~#" + CPControlTypeId + "#~#" + CPCountry + "#~#"
				+ CPDateOfBirth + "#~#" + CPEmirate + "#~#" + CPFirstName
				+ "#~#" + CPFlatVillaNo + "#~#" + CPId + "#~#" + CPLastName
				+ "#~#" + CPPrimaryKey + "#~#" + CPReasonId + "#~#" + CPStreet
				+ "#~#" + CPTaxpayerIdNo + "#~#" + CPTaxResCountry + "#~#"
				+ reasonDesc + "#~#" + sysRefNumber;
		String sValues = removeLastCamma(prepareColValues(tempValues));
		sInputXML = getDBInsertInput(tabName, colName, sValues, sCabinetName,
				sUserDBId);
		try {
			sOutputXML = executeAPI(sInputXML);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sOutputXML;
	}

	private String insertFatcaOwnerDtls(String tabName, String colName,
			String OwnerName, String OwnerNumber, String OwnershipAddress,
			String OwnershipSharePercentage, String OwnerTINorSSN,
			String OwnerW9Availability) {
		String tempValues = "" + LeadRefNo + "#~#" + OwnerName + "#~#"
				+ OwnerNumber + "#~#" + OwnershipAddress + "#~#"
				+ OwnershipSharePercentage + "#~#" + OwnerTINorSSN + "#~#"
				+ OwnerW9Availability + "#~#" + sysRefNumber;
		String sValues = removeLastCamma(prepareColValues(tempValues));
		sInputXML = getDBInsertInput(tabName, colName, sValues, sCabinetName,
				sUserDBId);
		try {
			sOutputXML = executeAPI(sInputXML);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sOutputXML;

	}

	private String insertAffEntityDtls(String tabName, String colName, int cnt,
			String Address, String CleitnRelation, String IncorpPlace,
			String LegalCountry, String Name) {
		String tempValues = "" + LeadRefNo + "#~#" + cnt + "#~#" + Address
				+ "#~#" + CleitnRelation + "#~#" + IncorpPlace + "#~#"
				+ LegalCountry + "#~#" + Name + "#~#" + sysRefNumber;
		String sValues = removeLastCamma(prepareColValues(tempValues));
		sInputXML = getDBInsertInput(tabName, colName, sValues, sCabinetName,
				sUserDBId);
		try {
			sOutputXML = executeAPI(sInputXML);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sOutputXML;

	}

	private String insertMajorClientDtls(String tabName, String colName,
			int cnt, String Address, String MajType, String IncorpPlace,
			String LineOfBus, String Name) {
		String tempValues = "" + LeadRefNo + "#~#" + cnt + "#~#" + Address
				+ "#~#" + MajType + "#~#" + IncorpPlace + "#~#" + LineOfBus
				+ "#~#" + Name + "#~#" + sysRefNumber;
		String sValues = removeLastCamma(prepareColValues(tempValues));
		sInputXML = getDBInsertInput(tabName, colName, sValues, sCabinetName,
				sUserDBId);
		try {
			sOutputXML = executeAPI(sInputXML);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sOutputXML;
	}

	private String insertMajorSupDtls(String tabName, String colName, int cnt,
			String Address, String MajType, String IncorpPlace,
			String LineOfBus, String Name) {
		String tempValues = "" + LeadRefNo + "#~#" + cnt + "#~#" + Address
				+ "#~#" + MajType + "#~#" + IncorpPlace + "#~#" + LineOfBus
				+ "#~#" + Name + "#~#" + sysRefNumber;
		String sValues = removeLastCamma(prepareColValues(tempValues));
		sInputXML = getDBInsertInput(tabName, colName, sValues, sCabinetName,
				sUserDBId);
		try {
			sOutputXML = executeAPI(sInputXML);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sOutputXML;
	}

	private String insertOwnershipInfoDtls(String tabName, String colName,
			int cnt, String Address, String IsPep, String LegalCntry,
			String Name, String Nationality, String Share) {
		String tempValues = "" + LeadRefNo + "#~#" + cnt + "#~#" + Address
				+ "#~#" + IsPep + "#~#" + LegalCntry + "#~#" + Name + "#~#"
				+ Nationality + "#~#" + Share + "#~#" + sysRefNumber;
		String sValues = removeLastCamma(prepareColValues(tempValues));
		sInputXML = getDBInsertInput(tabName, colName, sValues, sCabinetName,
				sUserDBId);
		try {
			sOutputXML = executeAPI(sInputXML);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sOutputXML;

	}

	private String insertSubsidiariesDetailsDtls(String tabName,
			String colName, int cnt, String Address, String BusinessNature,
			String IncorpPlace, String Name, String Share) {
		String tempValues = "" + LeadRefNo + "#~#" + cnt + "#~#" + Address
				+ "#~#" + BusinessNature + "#~#" + IncorpPlace + "#~#" + Name
				+ "#~#" + Share + "#~#" + sysRefNumber;
		String sValues = removeLastCamma(prepareColValues(tempValues));
		sInputXML = getDBInsertInput(tabName, colName, sValues, sCabinetName,
				sUserDBId);
		try {
			sOutputXML = executeAPI(sInputXML);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sOutputXML;

	}

	private String insertKYCUBODtls(String tabName, String colName, int cnt,
			String isPep, String LegalCntry, String Name, String Nationality,
			String Share) {
		String tempValues = "" + LeadRefNo + "#~#" + cnt + "#~#" + isPep
				+ "#~#" + LegalCntry + "#~#" + Name + "#~#" + Nationality
				+ "#~#" + Share + "#~#" + sysRefNumber;
		String sValues = removeLastCamma(prepareColValues(tempValues));
		sInputXML = getDBInsertInput(tabName, colName, sValues, sCabinetName,
				sUserDBId);
		try {
			sOutputXML = executeAPI(sInputXML);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sOutputXML;

	}

	//Added by SHivanshu ATP-426
	private String insertKYCRISKDtls(String tabName, String colName, int cnt,String A1LegalEntity, String A2EntityType, String A3Industry, String A4AccountOpeningPurpose, String A5AgeofBusiness, String A6ComplexOwnershipStructure, String 
			A7AccountinOtherBanks,String A8PepStatus, String B1CountryofIncorporation, String B2Jurisdiction, String B3CountryofOperation, String B4Nationality, String B5Residence, String 
			C1Onboarding, String C2Product, String C3Channel) {
		String tempValues = "" + LeadRefNo + "#~#" + cnt + "#~#" + sysRefNumber + "#~#" + A1LegalEntity + "#~#" + A2EntityType + "#~#" + A3Industry
				+ "#~#" + A4AccountOpeningPurpose + "#~#" + A5AgeofBusiness + "#~#" + A6ComplexOwnershipStructure + "#~#" + A7AccountinOtherBanks + "#~#" +A8PepStatus + "#~#"+ B1CountryofIncorporation
				+ "#~#" + B2Jurisdiction + "#~#" + B3CountryofOperation + "#~#" + B4Nationality + "#~#" + B5Residence + "#~#" + C1Onboarding + "#~#" + C2Product + "#~#" + C3Channel;
		String sValues = removeLastCamma(prepareColValues(tempValues));
		sInputXML = getDBInsertInput(tabName, colName, sValues, sCabinetName,
				sUserDBId);
		try {
			sOutputXML = executeAPI(sInputXML);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sOutputXML;

	}
//Changes by SHivanshu ATP-426
	private String insertUBORelDtls(String tabName, String colName, int iUbo,
			String CustomerRelationship, String DOB, String Name,
			String Nationality, String Share, String Gender, 
			String secondNationality, String EIDNmber, String EIDExpDate,
			String passportNumber, String passportExpDate, String passportIssDate,
			String visaNumber, String visaExpDate, String pepFlag, String DOLevel,
			String countryOfResidence, String countryofIncorporation) {
		String tempValues = "" + LeadRefNo + "#~#" + iUbo + "#~#"
				+ CustomerRelationship + "#~#" + DOB + "#~#" + Name + "#~#"
				+ Nationality + "#~#" + Share + "#~#" + Gender + "#~#"
				+ secondNationality + "#~#" + EIDNmber + "#~#" + EIDExpDate + "#~#" 
				+ passportNumber + "#~#" + passportExpDate + "#~#" + passportIssDate + "#~#" 
				+ visaNumber + "#~#" + visaExpDate + "#~#" + pepFlag + "#~#" 
				+ DOLevel + "#~#" + countryOfResidence + "#~#" + countryofIncorporation + "#~#"
				+ sysRefNumber;
		String sValues = removeLastCamma(prepareColValues(tempValues));
		sInputXML = getDBInsertInput(tabName, colName, sValues, sCabinetName,
				sUserDBId);
		try {
			sOutputXML = executeAPI(sInputXML);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sOutputXML;

	}

	private boolean validateInputXML(AccountOpening req, int i)
			throws ParseException {
		return validateHeaderInnput(req, i);
	}

	private boolean validateLeadSubDt(String tmp) {
		boolean bRs = false;
		// String outDt=convertDateWidTime(tmp);
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"dd/MM/yyyy HH:mm:ss");
		dateFormat.setLenient(false);
		try {
			if (tmp != null) {
				dateFormat.parse(tmp.trim());
				bRs = true;
			}
		} catch (Exception pe) {
			pe.printStackTrace();
			bRs = false;
		}
		return bRs;
	}

	@SuppressWarnings("rawtypes")
	private boolean validateHeaderInnput(AccountOpening req, int i) {

		if (!IterationCount.isEmpty() && (Integer.parseInt(IterationCount) > 0)
				&& QueueName.isEmpty()) {
			setErrorResponseMsg("-4066", "");
			return false;
		}
		String temp = "";
		temp = LeadSubDtTm;
		if (!validateLeadSubDt(temp)) {
			setErrorResponseMsg("-4061", "");
			return false;
		}
		for (Map.Entry mReqParam : mReqParamMap.entrySet()) {
			if (!(aValuesNotAllowed.contains(mReqParam.getValue()))) {
				continue;
			}
			setErrorResponseMsg("-3042", ((String) mReqParam.getKey())
					+ " is mandatory");
			return false;
		}
		if(!"N".equalsIgnoreCase(fatca_fatcaUpdateFlag) || NTBEntityFound)
		{
		if (!("NA".equalsIgnoreCase(fatca_documentCollected) || isEmptyStr(fatca_documentCollected))) {
			if (isEmptyStr(fatca_dateOfDocument)) {
				setErrorResponseMsg("-3042", "dateOfDocument" + " is mandatory");
				return false;
			}
		}
		if (!"NA".equalsIgnoreCase(fatca_idntfctnNumRequired)) {
			if (isEmptyStr(fatca_idntfctnNumber)) {
				setErrorResponseMsg("-3042", "IdentificationNumber"
						+ " is mandatory");
				return false;
			}
		}
		}
		temp = TlIssDt;
		if (!isValidDate(temp)) {
			setErrorResponseMsg("-4007", "");
			return false;
		}
		temp = TlExpDt;
		if (!isValidDate(temp)) {
			setErrorResponseMsg("-4008", "");

			return false;
		}
		temp = compIncorpDate;
		if (!isValidDate(temp)) {
			setErrorResponseMsg("-4006", "");

			return false;
		}
		temp = FormationDate;
		if (!temp.isEmpty() && !isValidDate(temp)) {
			setErrorResponseMsg("-4009", "");

			return false;
		}
		temp = DateOfVisit;
		if (!temp.isEmpty() && !isValidDate(temp)) {
			setErrorResponseMsg("-4010", "");

			return false;
		}
		if (req.getCustomers() != null) {
			if (req.getCustomers().getCutomerPersonalDtls() != null) {
				CutomerPersonalDtls = req.getCustomers()
						.getCutomerPersonalDtls();
				if (CutomerPersonalDtls == null
						|| CutomerPersonalDtls.length < 0) {
					setErrorResponseMsg("-3063",
							"Authorized Signatory is mandatory");
					return false;
				}
			}
		}

		if (req.getCustomers() != null) {
			if (req.getCustomers().getCutomerPersonalDtls() != null) {
				CutomerPersonalDtls = req.getCustomers()
						.getCutomerPersonalDtls();
				if (CutomerPersonalDtls == null
						|| CutomerPersonalDtls.length < 0) {
					setErrorResponseMsg("-3063", "");
					return false;
				}
				log.debug("Info getCutomerPersonalDtls");
				for (CutomerPersonalDtls cust : CutomerPersonalDtls) {
					if (!checkReqData(cust.getCustID()).isEmpty()
							&& !checkReqData(cust.getCustFullName()).isEmpty()
							&& !checkReqData(cust.getCusttype()).isEmpty()) {
						continue;
					} else if (validateAUSAddress(cust)
							&& validateAUSPersonalDtls(cust)) {
						continue;
					} else {
						return false;
					}
				}
			}
		}

		if (req.getAccountDetails() != null) {
			if (req.getAccountDetails().getAccountInfo() != null) {
				AccountInfo = req.getAccountDetails().getAccountInfo();
				if (AccountInfo == null || AccountInfo.length < 0) {
					setErrorResponseMsg("-3064",
							"AccountInformation is mandatory");
					return false;
				}
				for (AccountInfo acc : AccountInfo) {
					temp = (acc.getProdCode() == null) ? "" : acc.getProdCode()
							.trim();
					log.debug("ProdCode has value: " + temp);
					if (!(aValuesNotAllowed.contains(temp))) {
						continue;
					}
					setErrorResponseMsg("-3061", "ProdCode is mandatory");
					return false;
				}
			}
		}
		return true;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void loadValidationConst() {
		aValuesNotAllowed = new ArrayList();
		mReqParamMap = new LinkedHashMap();
		aValuesNotAllowed.add(null);
		aValuesNotAllowed.add("null");
		aValuesNotAllowed.add("?");
		aValuesNotAllowed.add("");
		mReqParamMap.put("sysRefNumber", sysRefNumber);
		mReqParamMap.put("IterationCount", IterationCount);
		mReqParamMap.put("SourceChannel", SourceChannel);
		mReqParamMap.put("SourcingCentre", SourcingCentre);
		mReqParamMap.put("LeadRefNumber", LeadRefNo);
		mReqParamMap.put("LeadSubmissionDate", LeadSubDtTm);
		mReqParamMap.put("HomeBranch", HomeBranch);
		mReqParamMap.put("ReqType", ReqType);
		mReqParamMap.put("InitiatorName", InitiatorName);
		mReqParamMap.put("AppRiskCls", AppRiskCls);
		mReqParamMap.put("RMName", compRMName);
		mReqParamMap.put("RMCode", compRMCode);
		mReqParamMap.put("RMEmail", RMEmail);
		// CR2019
		// mReqParamMap.put("InitiatorBy", InitiatorBy);
		// mReqParamMap.put("InitiatedDept", InitiatedDept);
		// mReqParamMap.put("SourcingDSACode", SourcingDSACode);
		// mReqParamMap.put("SourceOfLead", SourceOfLead);
		// Company Information
		mReqParamMap.put("CustoryType", compCustType);
		mReqParamMap.put("Categoty", CompCat);
		mReqParamMap.put("CompanyFullName", CompFullName);
		mReqParamMap.put("Perfix", CompPrefix);
		mReqParamMap.put("BranchName", compBrCode);
		mReqParamMap.put("CountryofIncorporation", compCntryIncp);
		mReqParamMap.put("TradeLicenseNo", TlNo);
		mReqParamMap.put("TradeLicenseIssueDate", TlIssDt);
		mReqParamMap.put("TradeLicenseExpiryDate", TlExpDt);
		// mReqParamMap.put("CommercialMembershipNumber",compCmMemNo);
		mReqParamMap.put("IncorporationDate", compIncorpDate);
		mReqParamMap.put("SystemRisk", compSysRiskCls);
		mReqParamMap.put("RMRisk", compRMRiskCls);
		mReqParamMap.put("RiskClassificationDate", compRMRiskClsDt);
		
		/*mReqParamMap.put("CB1Code", compCB1Code);
		mReqParamMap.put("CB2Code", compCB2Code);
		mReqParamMap.put("CB2Division", compCB2Division);
		mReqParamMap.put("CB2GorupId", compCB2GrpId);
		mReqParamMap.put("CB2PeerId", compCB2PeerId); 
		mReqParamMap.put("ProfitCenter", compProfitCenter);
		mReqParamMap.put("OffNumber", OffNumber);
		mReqParamMap.put("OffNumber2", OffNumber2);
		mReqParamMap.put("Mobile", Mobile);
		mReqParamMap.put("Email", Email);
		mReqParamMap.put("Website", Website);
		*/
		mReqParamMap.put("TypeOfBusiness", compTypBusDesc);
		
		mReqParamMap.put("AccountClassification", compAcctCls);
		mReqParamMap.put("CorrAddressLine1", compCorr_AddrsLine1);
		//mReqParamMap.put("CorrAddressLine2", compCorr_AddrsLine2);
		//mReqParamMap.put("CorrAddressLine3", compCorr_AddrsLine3);

		//mReqParamMap.put("corr_City", compCorr_City);
		//mReqParamMap.put("corr_Country", compCorr_Country);
		// if("AE".equalsIgnoreCase(compCorr_Country)){
		// mReqParamMap.put("corr_State",compCorr_State);
		// }
		mReqParamMap.put("Phy_AddrsLine1", compPhy_AddrsLine1);
		//mReqParamMap.put("Phy_AddrsLine2", compPhy_AddrsLine2);
		//mReqParamMap.put("Phy_AddrsLine3", compPhy_AddrsLine3);
		//mReqParamMap.put("Phy_Country", compPhy_Country);
		//mReqParamMap.put("Phy_City", compPhy_City);
		// if("AE".equalsIgnoreCase(compPhy_Country)){
		// mReqParamMap.put("Phy_State",compPhy_State);
		// }
		// mReqParamMap.put("IncorporationCountry ",incpCountry);
		// mReqParamMap.put("IncorporationCity",incpCity);
		// mReqParamMap.put("IncorporationState",incpState);
		
		// mReqParamMap.put("Fax",Fax);
		

		// Facta
		if(!"N".equalsIgnoreCase(fatca_fatcaUpdateFlag) || NTBEntityFound)
		{
		mReqParamMap.put("ClientCounterParty", fatca_customerClsfctn);
		mReqParamMap.put("natureOfEntity", fatca_natureOfEntity);
		mReqParamMap.put("typeOfEntity", fatca_typeOfEntity);
		mReqParamMap.put("FATCAStatus", fatca_FATCAStatus);
		mReqParamMap.put("documentCollected", fatca_documentCollected);
		// mReqParamMap.put("dateOfDocument",fatca_dateOfDocument);
		mReqParamMap.put("IdentificationNumberCollected",
				fatca_idntfctnNumRequired);
		// mReqParamMap.put("IdentificationNumber",fatca_idntfctnNumber);
		mReqParamMap.put("CustomerClassificationDate",
				fatca_customerClsfctnDate);
		mReqParamMap.put("FatcaClssificationForReportingPurpose",
				fatca_customerFATCAClsfctnDate);
		// mReqParamMap.put("fatcaUpdateFlag", fatca_fatcaUpdateFlag);
		}
		// CRS
		log.info("crsUpdateFlag :"+crsUpdateFlag+" NTBEntityFound : "+NTBEntityFound);
		if (!"N".equalsIgnoreCase(crsUpdateFlag) || NTBEntityFound)
		{
		mReqParamMap.put("channel", crsChannel);
		mReqParamMap.put("CRSClassificationDate", crsClassificationDate);
		mReqParamMap.put("CRSClassificationID", crsClassificationId);
		mReqParamMap.put("CRSRelRefNo", crsRelRefNo);
		mReqParamMap.put("CRSCertificationDate", crsCertificationDate);
		// mReqParamMap.put("CRSUpdateFlag", crsUpdateFlag);
		mReqParamMap.put("CRSFormCertificationObtained", crsCertFormObtained);
		// mReqParamMap.put("BirthCity",crsCustBirthCity);
		// mReqParamMap.put("FirstNaem",crsCustFirstName);
		// mReqParamMap.put("CRSID",crsCustId);
		// mReqParamMap.put("LastName",crsCustLastName);
		mReqParamMap.put("Type", crs_CustomerType);
		mReqParamMap.put("EntityTypeId", crsEntityTypeId);
		mReqParamMap.put("makerDate", crsMakerDate);
		mReqParamMap.put("makerId", crsMakerId);
		mReqParamMap.put("residenceAddressConfirmationStatus",
				crsResidenceAddressConfirmationStatus);
		mReqParamMap.put("termsAndCondAccepted", crsTermsAndCondAccepted);
		}
		log.debug("completing the loan constant");

	}

	private void setRequestParameter(AccountOpening req, int i) {
		fetchHeader(req, i);
		fetchWMSHeader(req, i);
		fetchWMSCompany(req, i);
		setKYCGenericParam(req, i);
		loadValidationConst();
	}

	private void setKYCGenericParam(AccountOpening req, int i) {

		try {
			KYC = req.getCompany().getKYC();
			KYCGeneric = KYC.getKycGeneric();
			if (KYC != null && KYCGeneric != null) {

				AddressProofInfo = KYCGeneric.getAddressProofInfo();
				if (AddressProofInfo != null) {
					IsTradeContract = checkReqData(AddressProofInfo
							.getIsTradeContract() == null ? ""
							: AddressProofInfo.getIsTradeContract().trim());
					IsUtilityBill = checkReqData(AddressProofInfo
							.getIsUtilityBill() == null ? "" : AddressProofInfo
							.getIsUtilityBill().trim());
					IsBankAccStmt = checkReqData(AddressProofInfo
							.getIsBankAccStmt() == null ? "" : AddressProofInfo
							.getIsBankAccStmt().trim());
					IsPhoneBill = checkReqData(AddressProofInfo
							.getIsPhoneBill() == null ? "" : AddressProofInfo
							.getIsPhoneBill());
					Others = checkReqData(AddressProofInfo.getOthers() == null ? ""
							: AddressProofInfo.getOthers());
				}
				AntiAccActivityInfo = KYCGeneric.getAntiAccActivityInfo();
				if (AntiAccActivityInfo != null) {
					CorssBorderPaymentsDtls = AntiAccActivityInfo
							.getCorssBorderPaymentsDtls();
					if (CorssBorderPaymentsDtls != null) {
						IsCBPayment = checkReqData(CorssBorderPaymentsDtls
								.getIsCBPayment() == null ? ""
								: CorssBorderPaymentsDtls.getIsCBPayment()
										.trim());
						PurposeOfPayment = checkReqData(CorssBorderPaymentsDtls
								.getPurposeOfPayment() == null ? ""
								: CorssBorderPaymentsDtls.getPurposeOfPayment());
						NoOfPaymentPerMonth = checkReqData(CorssBorderPaymentsDtls
								.getNoOfPaymentPerMonth() == null ? ""
								: CorssBorderPaymentsDtls
										.getNoOfPaymentPerMonth().trim());
						MonthlyValPayment = checkReqData(CorssBorderPaymentsDtls
								.getMonthlyValPayment() == null ? ""
								: CorssBorderPaymentsDtls
										.getMonthlyValPayment().trim());
						CntryMadetoRcvdFrom = checkReqData(CorssBorderPaymentsDtls
								.getCntryMadetoRcvdFrom() == null ? ""
								: CorssBorderPaymentsDtls
										.getCntryMadetoRcvdFrom());
					}
					ExpectedMonthlyDepositsDtls = AntiAccActivityInfo
							.getExpectedMonthlyDepositsDtls();
					if (ExpectedMonthlyDepositsDtls != null) {
						CashCDM = checkReqData(ExpectedMonthlyDepositsDtls
								.getCashCDM() == null ? ""
								: ExpectedMonthlyDepositsDtls.getCashCDM()
										.trim());
						IsCashCDM = checkReqData(ExpectedMonthlyDepositsDtls
								.getIsCashCDM() == null ? ""
								: ExpectedMonthlyDepositsDtls.getIsCashCDM()
										.trim());
						IsChequesDraft = checkReqData(ExpectedMonthlyDepositsDtls
								.getIsChequesDraft() == null ? ""
								: ExpectedMonthlyDepositsDtls
										.getIsChequesDraft().trim());
						ChequesDraft = checkReqData(ExpectedMonthlyDepositsDtls
								.getChequesDraft() == null ? ""
								: ExpectedMonthlyDepositsDtls.getChequesDraft()
										.trim());
						IsTransfers = checkReqData(ExpectedMonthlyDepositsDtls
								.getIsTransfers() == null ? ""
								: ExpectedMonthlyDepositsDtls.getIsTransfers()
										.trim());
						Transfers = checkReqData(ExpectedMonthlyDepositsDtls
								.getTransfers() == null ? ""
								: ExpectedMonthlyDepositsDtls.getTransfers()
										.trim());
					}
				}

				CashPickupService = KYCGeneric.getCashPickupService();
				if (CashPickupService != null) {
					IsServiceReq = checkReqData(CashPickupService
							.getIsServiceReq() == null ? "" : CashPickupService
							.getIsServiceReq().trim());
					ServiceProviderName = checkReqData(CashPickupService
							.getServiceProviderName() == null ? ""
							: CashPickupService.getServiceProviderName().trim());
				}
				CommercialInfo = KYCGeneric.getCommercialInfo();
				if (CommercialInfo != null) {
					IncorporationPlace = checkReqData(CommercialInfo
							.getIncorporationPlace() == null ? ""
							: CommercialInfo.getIncorporationPlace().trim());
					FormationDate = checkReqData(CommercialInfo
							.getFormationDate() == null ? "" : CommercialInfo
							.getFormationDate().trim());
					PhyBusinessLocation = checkReqData(CommercialInfo
							.getPhyBusinessLocation() == null ? ""
							: CommercialInfo.getPhyBusinessLocation().trim());
					IsAnnualIncomeActualAED = checkReqData(CommercialInfo
							.getIsAnnualIncomeActualAED() == null ? ""
							: CommercialInfo.getIsAnnualIncomeActualAED()
									.trim());
					ISAnnualIncomeEstAED = checkReqData(CommercialInfo
							.getISAnnualIncomeEstAED() == null ? ""
							: CommercialInfo.getISAnnualIncomeEstAED().trim());
					PrimaryTradeLocation = checkReqData(CommercialInfo
							.getPrimaryTradeLocation() == null ? ""
							: CommercialInfo.getPrimaryTradeLocation().trim());
					NoofUaeBranch = checkReqData(CommercialInfo
							.getNoofUaeBranch() == null ? "" : CommercialInfo
							.getNoofUaeBranch().trim());
					UaeBrachPrimaryLocation = checkReqData(CommercialInfo
							.getUaeBrachPrimaryLocation() == null ? ""
							: CommercialInfo.getUaeBrachPrimaryLocation()
									.trim());
					NoOfNonUaeBranch = checkReqData(CommercialInfo
							.getNoOfNonUaeBranch() == null ? ""
							: CommercialInfo.getNoOfNonUaeBranch().trim());
					NonUaeBrachPrimaryLocation = checkReqData(CommercialInfo
							.getNonUaeBrachPrimaryLocation() == null ? ""
							: CommercialInfo.getNonUaeBrachPrimaryLocation()
									.trim());
					compNoOfEmp = checkReqData(CommercialInfo.getNoOfEmp() == null ? ""
							: CommercialInfo.getNoOfEmp().trim());
					Website = checkReqData(CommercialInfo.getWebsite() == null ? ""
							: CommercialInfo.getWebsite().trim());
					TotalAssetAED = checkReqData(CommercialInfo
							.getTotalAssetAED() == null ? "" : CommercialInfo
							.getTotalAssetAED().trim());
					AnnualSalRevnueAED = checkReqData(CommercialInfo
							.getAnnualSalRevnueAED() == null ? ""
							: CommercialInfo.getAnnualSalRevnueAED().trim());
					NetProfitAED = checkReqData(CommercialInfo
							.getNetProfitAED() == null ? "" : CommercialInfo
							.getNetProfitAED().trim());
				}
				EnhancedDueDeligenceInfo = KYCGeneric
						.getEnhancedDueDeligenceInfo();
				if (EnhancedDueDeligenceInfo != null) {
					IsClientVisit = checkReqData(EnhancedDueDeligenceInfo
							.getIsClientVisit() == null ? ""
							: EnhancedDueDeligenceInfo.getIsClientVisit()
									.trim());
					DateOfVisit = checkReqData(EnhancedDueDeligenceInfo
							.getDateOfVisit() == null ? ""
							: EnhancedDueDeligenceInfo.getDateOfVisit().trim());
					LocationOfVisit = checkReqData(EnhancedDueDeligenceInfo
							.getLocationOfVisit() == null ? ""
							: EnhancedDueDeligenceInfo.getLocationOfVisit()
									.trim());
					AdcbStaffName = checkReqData(EnhancedDueDeligenceInfo
							.getAdcbStaffName() == null ? ""
							: EnhancedDueDeligenceInfo.getAdcbStaffName()
									.trim());
					PersonMetWith = checkReqData(EnhancedDueDeligenceInfo
							.getPersonMetWith() == null ? ""
							: EnhancedDueDeligenceInfo.getPersonMetWith()
									.trim());
					TypeOfCompany = checkReqData(EnhancedDueDeligenceInfo
							.getTypeOfCompany() == null ? ""
							: EnhancedDueDeligenceInfo.getTypeOfCompany()
									.trim());
					TypeOfComSetup = checkReqData(EnhancedDueDeligenceInfo
							.getTypeOfComSetup() == null ? ""
							: EnhancedDueDeligenceInfo.getTypeOfComSetup()
									.trim());
					TypeOfCompStrength = checkReqData(EnhancedDueDeligenceInfo
							.getTypeOfCompStrength() == null ? ""
							: EnhancedDueDeligenceInfo.getTypeOfCompStrength()
									.trim());
					TypeOfCompLocation = checkReqData(EnhancedDueDeligenceInfo
							.getTypeOfCompLocation() == null ? ""
							: EnhancedDueDeligenceInfo.getTypeOfCompLocation()
									.trim());
					ActivityConducted = checkReqData(EnhancedDueDeligenceInfo
							.getActivityConducted() == null ? ""
							: EnhancedDueDeligenceInfo.getActivityConducted()
									.trim());
					IsOptSubEntity = checkReqData(EnhancedDueDeligenceInfo
							.getIsOptSubEntity() == null ? ""
							: EnhancedDueDeligenceInfo.getIsOptSubEntity()
									.trim());
					OptSubEntity = checkReqData(EnhancedDueDeligenceInfo
							.getOptSubEntity() == null ? ""
							: EnhancedDueDeligenceInfo.getOptSubEntity().trim());
					RmAssessment = checkReqData(EnhancedDueDeligenceInfo
							.getRmAssessment() == null ? ""
							: EnhancedDueDeligenceInfo.getRmAssessment().trim());
				}
				GenInformation = KYCGeneric.getGeneralInfo();
				if (GenInformation != null) {
					CIF = checkReqData(GenInformation.getCIF() == null ? ""
							: GenInformation.getCIF().trim());
					ProfilingPurpose = checkReqData(GenInformation
							.getProfilingPurpose() == null ? ""
							: GenInformation.getProfilingPurpose().trim());
					RelationshipSince = checkReqData(GenInformation
							.getRelationshipSince() == null ? ""
							: GenInformation.getRelationshipSince().trim());
					BusinessNature = checkReqData(GenInformation
							.getBusinessNature() == null ? "" : GenInformation
							.getBusinessNature().trim());
					PurposeAOADCB = checkReqData(GenInformation
							.getPurposeAOADCB() == null ? "" : GenInformation
							.getPurposeAOADCB().trim());
					//Added by Shivanshu
					ActiveAccountFlag = checkReqData(GenInformation
							.getActiveAccountFlag() == null ? "" : GenInformation
							.getActiveAccountFlag().trim());
					PurposeAOADCBOthers = checkReqData(GenInformation
							.getPurposeAOADCBOthers() == null ? "" : GenInformation
							.getPurposeAOADCBOthers().trim());
					IsDualGoods = checkReqData(GenInformation
							.getIsDualGoods() == null ? "" : GenInformation
							.getIsDualGoods().trim());
					DualGoodsType = checkReqData(GenInformation
							.getDualGoodsType() == null ? "" : GenInformation
							.getDualGoodsType().trim());

				}
				OverAllRisk = (KYCGeneric.getOverAllRisk() == null ? ""
						: KYCGeneric.getOverAllRisk().trim());
				kycRiskAssessment = KYCGeneric.getRiskAssessment();
				if (kycRiskAssessment != null) {
					LegEntRiskAssessment = kycRiskAssessment
							.getLegEntRiskAssessment();
					if (LegEntRiskAssessment != null) {
						IsOffshoreLocation = checkReqData(LegEntRiskAssessment
								.getIsOffshoreLocation() == null ? ""
								: LegEntRiskAssessment.getIsOffshoreLocation()
										.trim());
						IsFreezone = checkReqData(LegEntRiskAssessment
								.getIsFreezone() == null ? ""
								: LegEntRiskAssessment.getIsFreezone().trim());
						IsAMLCFTPolicy = checkReqData(LegEntRiskAssessment
								.getIsAMLCFTPolicy() == null ? ""
								: LegEntRiskAssessment.getIsAMLCFTPolicy()
										.trim());
						IsNUaeTradArmWeapons = checkReqData(LegEntRiskAssessment
								.getIsNUaeTradArmWeapons() == null ? ""
								: LegEntRiskAssessment
										.getIsNUaeTradArmWeapons().trim());
						IsNUaedefRelEqup = checkReqData(LegEntRiskAssessment
								.getIsNUaedefRelEqup() == null ? ""
								: LegEntRiskAssessment.getIsNUaedefRelEqup()
										.trim());
						IsHawala = checkReqData(LegEntRiskAssessment
								.getIsHawala() == null ? ""
								: LegEntRiskAssessment.getIsHawala().trim());
						IsSanctioned = checkReqData(LegEntRiskAssessment
								.getIsSanctioned() == null ? ""
								: LegEntRiskAssessment.getIsSanctioned().trim());
						IsIncRiskJuri = checkReqData(LegEntRiskAssessment
								.getIsIncRiskJuri() == null ? ""
								: LegEntRiskAssessment.getIsIncRiskJuri()
										.trim());
						IsShellCompany = checkReqData(LegEntRiskAssessment
								.getIsShellCompany() == null ? ""
								: LegEntRiskAssessment.getIsShellCompany()
										.trim());
						IsDeemedTaxEvasion = checkReqData(LegEntRiskAssessment
								.getIsDeemedTaxEvasion() == null ? ""
								: LegEntRiskAssessment.getIsDeemedTaxEvasion()
										.trim());
						IsBusLnkSanctioned = checkReqData(LegEntRiskAssessment
								.getIsBusLnkSanctioned() == null ? ""
								: LegEntRiskAssessment.getIsBusLnkSanctioned()
										.trim());
						IsVertualFlexiDesk = checkReqData(LegEntRiskAssessment
								.getIsVertualFlexiDesk() == null ? ""
								: LegEntRiskAssessment.getIsVertualFlexiDesk()
										.trim());
						IsNomineeOwnShipStr = checkReqData(LegEntRiskAssessment
								.getIsNomineeOwnShipStr() == null ? ""
								: LegEntRiskAssessment.getIsNomineeOwnShipStr()
										.trim());
						IsIssuedBearerShare = checkReqData(LegEntRiskAssessment
								.getIsIssuedBearerShare() == null ? ""
								: LegEntRiskAssessment.getIsIssuedBearerShare()
										.trim());
						IsVertualCryptoCur = checkReqData(LegEntRiskAssessment
								.getIsVertualCryptoCur() == null ? ""
								: LegEntRiskAssessment.getIsVertualCryptoCur()
										.trim());
						IsNUaeOwnedEnty = checkReqData(KYC.getKycGeneric()
								.getRiskAssessment().getIsNUaeOwnedEnty() == null ? ""
								: KYC.getKycGeneric().getRiskAssessment()
										.getIsNUaeOwnedEnty().trim());
						IsNUaePep = checkReqData(KYC.getKycGeneric()
								.getRiskAssessment().getIsNUaePep() == null ? ""
								: KYC.getKycGeneric().getRiskAssessment()
										.getIsNUaePep().trim());
						//Added by Shivanshu
						IsPEPRiskQ3 = checkReqData(KYC.getKycGeneric()
								.getRiskAssessment().getIsPEPRiskQ3() == null ? ""
								: KYC.getKycGeneric().getRiskAssessment()
										.getIsPEPRiskQ3().trim());
					}
					UboRiskAssessment = kycRiskAssessment
							.getUboRiskAssessment();
					if (UboRiskAssessment != null) {
						IsOverAllRiskInc = checkReqData(UboRiskAssessment
								.getIsOverAllRiskInc() == null ? ""
								: UboRiskAssessment.getIsOverAllRiskInc()
										.trim());
						IsOverAllRiskUnact = checkReqData(UboRiskAssessment
								.getIsOverAllRiskUnact() == null ? ""
								: UboRiskAssessment.getIsOverAllRiskUnact()
										.trim());
						IsUboHawala = checkReqData(UboRiskAssessment
								.getIsUboHawala() == null ? ""
								: UboRiskAssessment.getIsUboHawala().trim());
						IsUboBusTradLnkSanctioned = checkReqData(UboRiskAssessment
								.getIsUboBusTradLnkSanctioned() == null ? ""
								: UboRiskAssessment
										.getIsUboBusTradLnkSanctioned().trim());
					}
				}
				SourceOfFund = (KYCGeneric.getSourceOfFund() == null ? "" : KYC
						.getKycGeneric().getSourceOfFund().trim());
				kycSignOff = KYCGeneric.getSignOff();
				if (kycSignOff != null) {
					bGHName = checkReqData(kycSignOff.getbGHName() == null ? ""
							: kycSignOff.getbGHName().trim());
					bUHName = checkReqData(kycSignOff.getbUHName() == null ? ""
							: kycSignOff.getbUHName().trim());
					lMName = checkReqData(kycSignOff.getlMName() == null ? ""
							: kycSignOff.getlMName().trim());
					staffName = checkReqData(kycSignOff.getStaffName() == null ? ""
							: kycSignOff.getStaffName().trim());
				}
			}
			log.debug("completing the fetch kyc generic");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String insertWBGHeader() throws WbgAccountOpenningException,
			IOException, Exception {
		log.debug("in USR_0_AO_Header");
		String sTable = "USR_0_WBG_TMP_HEADER";
		String sOutputXML = null;
		// CR2019 start
		String sColumn = "LEAD_REFNO,SOURCECHANNEL,SOURCINGCENTRE,LEADSUBDTTM,HOMEBRANCH,ITERATIONCOUNT,ITERATION,REQTYPE,QUEUENAME,INITIATORNAME,APPRISKCLS,LASTPROCESSEDBYUSERNAME,RMNAME,RMCODE,RMEMAIL,RMMNGNAME,RMMNGCODE,RMMNGEMAIL,SYSREFNO,INITIATORBY,INITIATEDDEPT,SOURCINGDSACODE,SOURCEOFLEAD,LEAD_TYPE,REWORK_FLAG,DUAL_LANE_FLAG";
		String tempValues = "" + LeadRefNo + "#~#" + SourceChannel + "#~#"
				+ SourcingCentre + "#~#" + LeadSubDtTm + "#~#" + HomeBranch
				+ "#~#" + IterationCount + "#~#" + itrationValue +"#~#" + ReqType
				+ "#~#" + QueueName + "#~#" + InitiatorName + "#~#"
				+ AppRiskCls + "#~#" + LastProcessedByUserName + "#~#"
				+ compRMName + "#~#" + compRMCode + "#~#" + RMEmail + "#~#"
				+ RMMngName + "#~#" + RMMngCode + "#~#" + RMMngEmail + "#~#"
				+ sysRefNumber + "#~#" + InitiatorBy + "#~#" + InitiatedDept
				+ "#~#" + SourcingDSACode + "#~#" + SourceOfLead + "#~#"
				+ leadType + "#~#" + reworkValue + "#~#" + duallaneValue;
		// end

		String sValues = removeLastCamma(prepareColValues(tempValues));
		String sInputXML = getDBInsertInput(sTable, sColumn, sValues,
				sCabinetName, sUserDBId);
		// log.debug("sInputXML:"+sInputXML);
		sOutputXML = executeAPI(sInputXML);
		 log.debug("sOUTPUTXML:>>"+sOutputXML);
		return sOutputXML;
	}

	private String insertWBGCompany() throws WbgAccountOpenningException,
			IOException, Exception {
		log.debug("in insertWBGCompany");

		String sTable = "USR_0_WBG_TMP_COMPANY";
		String sOutputXML = null;
		String sColumn = "LEAD_REFNO,COMPIC, COMPCAT, COMPID, COMPFULLNAME, COMPPREFIX, BRCODE, CNTRYINCP, TLNO, TLISSDT, TLEXPDT, CMMEMNO, INCORPDATE, SYSRISKCLS, RMRISKCLS, RMRISKCLSDT, CB1CODE, CB2DIVISION, CB2GRPID, CB2PEERID, TYPBUSDESC, PROFITCENTER, COMACCTCLS, RMNAME, RMCODE, ANSALEREVNUE, NOOFEMP, BANKNAME1, BANKNAME2, BANKNAME3, CITYCNTRY1, CITYCNTRY2, CITYCNTRY3, APPRMNAME, APPRMCODE, SECRMNAME, SECRMCODE, ACCMORGNRCVD, DT1SRORGNRCVD, CSCFRMRCVD, SRCTURNOVER,CUSTTYPE,CORR_ADDRSLINE1,CORR_ADDRSLINE2,CORR_ADDRSLINE3,CORR_STATE,CORR_CITY,CORR_COUNTRY,PHY_ADDRSLINE1,PHY_ADDRSLINE2,PHY_ADDRSLINE3,PHY_COUNTRY,PHY_STATE,PHY_CITY,INCPCOUNTRY,INCPCITY,INCPSTATE,OFFNUMBER,OFFNUMBER2,MOBILE,FAX,EMAIL,WEBSITE,FATCA_CUSTCLSFCTN,FATCA_NATUREOFENTITY,FATCA_TYPEOFENTITY,FATCA_FATCASTATUS,FATCA_DOCCOLLECTED,FATCA_DATEOFDOCUMENT,FATCA_IDNTFCTNNUMREQ,FATCA_IDNTFCTNNUMBER,FATCA_CUSTCLSFCTNDT,FATCA_CUSTFATCACLSFCTNDATE,FATCA_UPDATEFLAG,CRSCHANNEL,CRSCLASSIFICATIONDATE,CRSCLASSIFICATIONID,CRSCERTFORMOBTAINED,CRSCUSTBIRTHCITY,CRSCUSTFIRSTNAME,CRSCUSTID,CRSCUSTLASTNAME,CRS_CUSTOMERTYPE,CRSENTITYTYPEID,CRSMAKERDATE,CRSMAKERID,CRSRESADDCONFIRM,CRSTCACCEPTED,CRSRELREFNO,CRSCERTIFICATIONDATE,CRSUPDATEFLAG,CB2CODE,SRV_REMARKS,CUST_SRV_CHRG_PKG,SYSREFNO,NO_OF_SIGNATURES,TL_ISSUING_AUTHORITY,TL_UPDATE_FLAG,DIGISIGNFLAG,DIGISIGNREASON"; //Added by Shivanshu
		String tempValues = "" + LeadRefNo + "#~#" + CompIC + "#~#" + CompCat
				+ "#~#" + CompID + "#~#" + CompFullName + "#~#" + CompPrefix
				+ "#~#" + compBrCode + "#~#" + compCntryIncp + "#~#" + TlNo
				+ "#~#" + TlIssDt + "#~#" + TlExpDt + "#~#" + compCmMemNo
				+ "#~#" + compIncorpDate + "#~#" + compSysRiskCls + "#~#"
				+ compRMRiskCls + "#~#" + compRMRiskClsDt + "#~#" + compCB1Code
				+ "#~#" + compCB2Division + "#~#" + compCB2GrpId + "#~#"
				+ compCB2PeerId + "#~#" + compTypBusDesc + "#~#"
				+ compProfitCenter + "#~#" + compAcctCls + "#~#" + compRMName
				+ "#~#" + compRMCode + "#~#" + compAnSaleRevnue + "#~#"
				+ compNoOfEmp + "#~#" + compBankName1 + "#~#" + compBankName2
				+ "#~#" + compBankName3 + "#~#" + compCityCntry1 + "#~#"
				+ compCityCntry2 + "#~#" + compCityCntry3 + "#~#"
				+ compAppRMName + "#~#" + compAppRMCode + "#~#" + compSecRMName
				+ "#~#" + compSecRMCode + "#~#" + compAccMOrgNRcvd + "#~#"
				+ compdt1SrOrgNRcvd + "#~#" + compCSCFrmRcvd + "#~#"
				+ compSrcTurnOver + "#~#" + compCustType + "#~#"
				+ compCorr_AddrsLine1 + "#~#" + compCorr_AddrsLine2 + "#~#"
				+ compCorr_AddrsLine3 + "#~#" + compCorr_State + "#~#"
				+ compCorr_City + "#~#" + compCorr_Country + "#~#"
				+ compPhy_AddrsLine1 + "#~#" + compPhy_AddrsLine2 + "#~#"
				+ compPhy_AddrsLine3 + "#~#" + compPhy_Country + "#~#"
				+ compPhy_State + "#~#" + compPhy_City + "#~#" + incpCountry
				+ "#~#" + incpCity + "#~#" + incpState + "#~#" + OffNumber
				+ "#~#" + OffNumber2 + "#~#" + Mobile + "#~#" + Fax + "#~#"
				+ Email + "#~#" + Website + "#~#" + fatca_customerClsfctn
				+ "#~#" + fatca_natureOfEntity + "#~#" + fatca_typeOfEntity
				+ "#~#" + fatca_FATCAStatus + "#~#" + fatca_documentCollected
				+ "#~#" + fatca_dateOfDocument + "#~#"
				+ fatca_idntfctnNumRequired + "#~#" + fatca_idntfctnNumber
				+ "#~#" + fatca_customerClsfctnDate + "#~#"
				+ fatca_customerFATCAClsfctnDate + "#~#"
				+ fatca_fatcaUpdateFlag + "#~#" + crsChannel + "#~#"
				+ crsClassificationDate + "#~#" + crsClassificationId + "#~#"
				+ crsCertFormObtained + "#~#" + crsCustBirthCity + "#~#"
				+ crsCustFirstName + "#~#" + crsCustId + "#~#"
				+ crsCustLastName + "#~#" + crs_CustomerType + "#~#"
				+ crsEntityTypeId + "#~#" + crsMakerDate + "#~#" + crsMakerId
				+ "#~#" + crsResidenceAddressConfirmationStatus + "#~#"
				+ crsTermsAndCondAccepted + "#~#" + crsRelRefNo + "#~#"
				+ crsCertificationDate + "#~#" + crsUpdateFlag + "#~#"
				+ compCB2Code + "#~#" + compSrvRemarks + "#~#"
				+ compCustSrvChrgPkg + "#~#" + sysRefNumber + "#~#"
				+ noOfSignatures + "#~#" + tlIssuingAuthority + "#~#"
				+ tlUpdateFlag + "#~#" + digiSignFlag + "#~#"
				+ digiSignReason;                                     //Modified By Shivanshu
		String sValues = removeLastCamma(prepareColValues(tempValues));
		String sInputXML = getDBInsertInput(sTable, sColumn, sValues,
				sCabinetName, sUserDBId);
		// log.debug("sInputXML:"+sInputXML);
		sOutputXML = executeAPI(sInputXML);
		// log.debug("sOUTPUTXML:"+sOutputXML);
		return sOutputXML;
	}

	private String removeLastCamma(String str) {
		str = str.substring(0, str.length() - 1);
		return str;
	}

	private String WFUploadWorkItem(String sOption, String sEngineName,
			String sSessionID, String sProcessDefId,
			String sValidationRequired, String sAttributes, String sTemp,
			String sIniActName, String sIniActId) {
		return "<?xml version=\"1.0\"?>"
				+ "<WFUploadWorkItem_Input><Option>WFUploadWorkItem</Option>"
				+ "<EngineName>"
				+ sEngineName
				+ "</EngineName>"
				+ "<SessionId>"
				+ sSessionID
				+ "</SessionId>"
				+ "<ValidationRequired></ValidationRequired>"
				+ "<ProcessDefId>"
				+ sProcessDefId
				+ "</ProcessDefId>"
				+ "<InitiateAlso>N</InitiateAlso>"
				+ "<DataDefName></DataDefName>"
				+ "<Documents>"
				+ sTemp
				+ "</Documents>"
				+ "<InitiateFromActivityId>"
				+ sIniActId
				+ "</InitiateFromActivityId>"
				+ "<InitiateFromActivityName>"
				+ sIniActName
				+ "</InitiateFromActivityName>"
				+ "<Attributes>"
				+ sAttributes + "</Attributes>" + "</WFUploadWorkItem_Input>";

	}

	private String insertDataIntoProcessTab(String wiName) throws IOException,
			Exception {
	//	sOutputXML = WFCallBroker.execute( procCallinputXml(procMoveUpdate, "'" + LeadRefNo + "','" + sysRefNumber + "','" + wiName + "','" + FolderIndex + "'"), sJtsIp, iJtsPort, 1);
		sOutputXML = ExecuteXML.executeXML(procCallinputXml(procMoveUpdate, "'" + LeadRefNo + "','" + sysRefNumber + "','" + wiName + "','" + FolderIndex + "'"));
		log.debug("OUTPUT" + sOutputXML);
		return sOutputXML;
	}

	private String deleteDataRollBack(String event) throws IOException,
			Exception {
		log.debug("deleteDataRollBack CALLED");
		//sOutputXML = WFCallBroker.execute( procCallinputXml(procDelete, "'" + LeadRefNo + "','" + sysRefNumber + "','" + event + "'"), sJtsIp, iJtsPort, 1);
		sOutputXML = ExecuteXML.executeXML(procCallinputXml(procDelete, "'" + LeadRefNo + "','"
				+ sysRefNumber + "','" + event + "'"));
		return sOutputXML;
	}

	protected String convertDateWidTime(String inDate) {
		String outDate = "";
		if (!inDate.isEmpty()) {
			if (inDate.length() >= 19) {
				String dd = inDate.substring(0, 2);
				String mm = inDate.substring(3, 5);
				String yyyy = inDate.substring(6, 10);
				String time = inDate.substring(10, inDate.length());
				outDate = yyyy + "-" + mm + "-" + dd + time;

			}
		}
		return outDate;
	}

	private String calendarDate(String inDate) {
		String outDate = "";
		if (!inDate.isEmpty()) {
			if (inDate.length() >= 10) {
				String dd = inDate.substring(0, 2);
				String mm = inDate.substring(3, 5);
				String yyyy = inDate.substring(6, 10);
				outDate = yyyy + "-" + mm + "-" + dd;
			}

		}
		return outDate;
	}

	private String upldWorkitem() throws IOException, Exception {
		log.debug("INSIDE CREATE WORK ITEM");
		String sOption = "WFUploadWorkItem";
		String sTemp = getDocTempDtls(xDocTrc) + existingDocList();
		// String sTemp="";
		sOutputXML = "";
		StringBuffer WFAttributeList = new StringBuffer();

		WFAttributeList.append(setAttributeHeader());
		WFAttributeList.append(setCompanyAttribute());
		log.debug("INPUT PARAMETERS BE LIKE:" + sOption + "WFAttributeList"
				+ WFAttributeList.toString());
		
		if(!NTBAusFound)
		{
			sInputXML = WFUploadWorkItem(sOption, sCabinetName, sUserDBId,
						sProcessDefId, "", WFAttributeList.toString(), sTemp,
						sIniActName, sIniActId);
				// log.debug("INPUT "+sInputXML);
			sOutputXML = executeAPI(sInputXML);
			
		}
		else if(NTBAusFound && sTemp.contains("Signature_Card"))
		{
			sInputXML = WFUploadWorkItem(sOption, sCabinetName, sUserDBId,
					sProcessDefId, "", WFAttributeList.toString(), sTemp,
					sIniActName, sIniActId);
			// log.debug("INPUT "+sInputXML);
			sOutputXML = executeAPI(sInputXML);
		}else
		{
			rollBackData("ALL");
			setErrorResponseMsg("-11090","");
			sOutputXML = "";
			throw new WbgAccountOpenningException("-11090",
					getStatuMessageFromCode("-11090"), "");
		}
		// log.debug("OUTPUT"+sOutputXML);
		return sOutputXML;
	}

	private String setCompanyAttribute() {
		String sPrvsRisk = getPrvsRisk();
		StringBuffer compAttrb = new StringBuffer();
		log.debug("INSIDE setCompanyAttribute ");

		String EXISTING_CUST = "";

		if (!CompID.equalsIgnoreCase("")) {
			log.debug("CompID " + CompID.equalsIgnoreCase(""));
			EXISTING_CUST = "ETB";
		} else {
			EXISTING_CUST = "NTB";
		}
		log.debug("CUSTOMERFLAG" + EXISTING_CUST);

		compAttrb.append("CUST_TYPE");
		compAttrb.append(fieldSep);
		compAttrb.append(compCustType);
		compAttrb.append(recordSep);

		compAttrb.append("CCUST_SRV_CHRG_PKG");
		compAttrb.append(fieldSep);
		compAttrb.append(compCustSrvChrgPkg);
		compAttrb.append(recordSep);

		compAttrb.append("CUSTIC");
		compAttrb.append(fieldSep);
		compAttrb.append(CompIC);
		compAttrb.append(recordSep);

		compAttrb.append("CUSTCAT");
		compAttrb.append(fieldSep);
		compAttrb.append(CompCat);
		compAttrb.append(recordSep);

		compAttrb.append("CUSTID");
		compAttrb.append(fieldSep);
		compAttrb.append(CompID);
		compAttrb.append(recordSep);

		compAttrb.append("CUSTFULLNAME");
		compAttrb.append(fieldSep);
		compAttrb.append(CompFullName);
		compAttrb.append(recordSep);

		compAttrb.append("PREFIX");
		compAttrb.append(fieldSep);
		compAttrb.append(CompPrefix);
		compAttrb.append(recordSep);

		compAttrb.append("BRNAME");
		compAttrb.append(fieldSep);
		compAttrb.append(HomeBranch);
		compAttrb.append(recordSep);

		compAttrb.append("CNTRYINCP");
		compAttrb.append(fieldSep);
		compAttrb.append(compCntryIncp);
		compAttrb.append(recordSep);

		compAttrb.append("TLNO");
		compAttrb.append(fieldSep);
		compAttrb.append(TlNo);
		compAttrb.append(recordSep);

		compAttrb.append("TLISSDT");
		compAttrb.append(fieldSep);
		compAttrb.append(calendarDate(TlIssDt));
		compAttrb.append(recordSep);

		compAttrb.append("TLEXPDT");
		compAttrb.append(fieldSep);
		compAttrb.append(calendarDate(TlExpDt));
		compAttrb.append(recordSep);

		compAttrb.append("CMMEMNO");
		compAttrb.append(fieldSep);
		compAttrb.append(compCmMemNo);
		compAttrb.append(recordSep);

		compAttrb.append("OFFNUMBER2");
		compAttrb.append(fieldSep);
		compAttrb.append(chkPhoneNumber(OffNumber2));
		compAttrb.append(recordSep);

		compAttrb.append("OFFNUMBER");
		compAttrb.append(fieldSep);
		compAttrb.append(chkPhoneNumber(OffNumber));
		compAttrb.append(recordSep);

		compAttrb.append("MOBILE");
		compAttrb.append(fieldSep);
		compAttrb.append(chkPhoneNumber(Mobile));
		compAttrb.append(recordSep);

		compAttrb.append("FAX");
		compAttrb.append(fieldSep);
		compAttrb.append(Fax);
		compAttrb.append(recordSep);

		compAttrb.append("EMAIL");
		compAttrb.append(fieldSep);
		compAttrb.append(Email);
		compAttrb.append(recordSep);

		compAttrb.append("WEBSITE");
		compAttrb.append(fieldSep);
		compAttrb.append(Website);
		compAttrb.append(recordSep);

		compAttrb.append("CORRADDRSLINE1");
		compAttrb.append(fieldSep);
		compAttrb.append(compCorr_AddrsLine1);
		compAttrb.append(recordSep);

		compAttrb.append("CORRADDRSLINE2");
		compAttrb.append(fieldSep);
		compAttrb.append(compCorr_AddrsLine2);
		compAttrb.append(recordSep);

		compAttrb.append("CORRADDRSLINE3");
		compAttrb.append(fieldSep);
		compAttrb.append(compCorr_AddrsLine3);
		compAttrb.append(recordSep);

		compAttrb.append("CORRSTATE");
		compAttrb.append(fieldSep);
		compAttrb.append(compCorr_State);
		compAttrb.append(recordSep);

		compAttrb.append("CORRCITY");
		compAttrb.append(fieldSep);
		compAttrb.append(compCorr_City);
		compAttrb.append(recordSep);

		compAttrb.append("CORRCOUNTRY");
		compAttrb.append(fieldSep);
		compAttrb.append(compCorr_Country);
		compAttrb.append(recordSep);

		compAttrb.append("PHYADDRSLINE1");
		compAttrb.append(fieldSep);
		compAttrb.append(compPhy_AddrsLine1);
		compAttrb.append(recordSep);

		compAttrb.append("PHYCORRADDRSLINE2");
		compAttrb.append(fieldSep);
		compAttrb.append(compPhy_AddrsLine2);
		compAttrb.append(recordSep);

		compAttrb.append("PHYCORRADDRSLINE3");
		compAttrb.append(fieldSep);
		compAttrb.append(compPhy_AddrsLine3);
		compAttrb.append(recordSep);

		compAttrb.append("PHYSTATE");
		compAttrb.append(fieldSep);
		compAttrb.append(compPhy_State);
		compAttrb.append(recordSep);

		compAttrb.append("PHYCITY");
		compAttrb.append(fieldSep);
		compAttrb.append(compPhy_City);
		compAttrb.append(recordSep);

		compAttrb.append("PHYCOUNTRY");
		compAttrb.append(fieldSep);
		compAttrb.append(compPhy_Country);
		compAttrb.append(recordSep);

		compAttrb.append("RELTYPE");
		compAttrb.append(fieldSep);
		compAttrb.append(compAcctCls);
		compAttrb.append(recordSep);

		compAttrb.append("TYPEOFBUSS");
		compAttrb.append(fieldSep);
		compAttrb.append(compTypBusDesc);
		compAttrb.append(recordSep);

		compAttrb.append("RMRISKCLS");
		compAttrb.append(fieldSep);
		compAttrb.append(compRMRiskCls);
		compAttrb.append(recordSep);

		compAttrb.append("SYSRISKCLS");
		compAttrb.append(fieldSep);
		compAttrb.append(compSysRiskCls);
		compAttrb.append(recordSep);

		compAttrb.append("RISKCLSDATE");
		compAttrb.append(fieldSep);
		compAttrb.append(calendarDate(compRMRiskClsDt));
		compAttrb.append(recordSep);

		compAttrb.append("CB1CODE");
		compAttrb.append(fieldSep);
		compAttrb.append(compCB1Code);
		compAttrb.append(recordSep);

		compAttrb.append("CB2GRPID");
		compAttrb.append(fieldSep);
		compAttrb.append(compCB2GrpId);
		compAttrb.append(recordSep);

		compAttrb.append("CB2PEERID");
		compAttrb.append(fieldSep);
		compAttrb.append(compCB2PeerId);
		compAttrb.append(recordSep);

		compAttrb.append("CB2DIVISION");
		compAttrb.append(fieldSep);
		compAttrb.append(compCB2Division);
		compAttrb.append(recordSep);

		compAttrb.append("FATCA_CLIENTPRTY");
		compAttrb.append(fieldSep);
		compAttrb.append(fatca_customerClsfctn);
		compAttrb.append(recordSep);

		compAttrb.append("FATCA_NATENTITY");
		compAttrb.append(fieldSep);
		compAttrb.append(fatca_natureOfEntity);
		compAttrb.append(recordSep);

		compAttrb.append("FATCA_TYPENTITY");
		compAttrb.append(fieldSep);
		compAttrb.append(fatca_typeOfEntity);
		compAttrb.append(recordSep);

		compAttrb.append("FATCASTATUS");
		compAttrb.append(fieldSep);
		compAttrb.append(fatca_FATCAStatus);
		compAttrb.append(recordSep);

		compAttrb.append("FATCA_DOCCOLLECTED");
		compAttrb.append(fieldSep);
		compAttrb.append(fatca_documentCollected);
		compAttrb.append(recordSep);

		compAttrb.append("FATCA_DOCDT");
		compAttrb.append(fieldSep);
		compAttrb.append(fatcaDocDate(fatca_dateOfDocument));
		compAttrb.append(recordSep);

		compAttrb.append("FATCA_IDNUMCOL");
		compAttrb.append(fieldSep);
		compAttrb.append(fatca_idntfctnNumRequired);
		compAttrb.append(recordSep);

		compAttrb.append("FATCA_IDNUM");
		compAttrb.append(fieldSep);
		compAttrb.append(fatca_idntfctnNumber);
		compAttrb.append(recordSep);

		// compAttrb.append("PHYADDRSLINE4");
		// compAttrb.append(fieldSep);
		// compAttrb.append(compPhy_AddrsLine4);
		// compAttrb.append(recordSep);
		compAttrb.append("ANNLSALREV");
		compAttrb.append(fieldSep);
		compAttrb.append(compAnSaleRevnue);
		compAttrb.append(recordSep);

		compAttrb.append("PROFITCENTER");
		compAttrb.append(fieldSep);
		compAttrb.append(compProfitCenter);
		compAttrb.append(recordSep);

		compAttrb.append("NOOFEMP");
		compAttrb.append(fieldSep);
		compAttrb.append(compNoOfEmp);
		compAttrb.append(recordSep);

		compAttrb.append("BANKNAME1");
		compAttrb.append(fieldSep);
		compAttrb.append(compBankName1);
		compAttrb.append(recordSep);

		compAttrb.append("BANKNAME2");
		compAttrb.append(fieldSep);
		compAttrb.append(compBankName2);
		compAttrb.append(recordSep);

		compAttrb.append("BANKNAME3");
		compAttrb.append(fieldSep);
		compAttrb.append(compBankName3);
		compAttrb.append(recordSep);

		compAttrb.append("CITYCOUN1");
		compAttrb.append(fieldSep);
		compAttrb.append(compCityCntry1);
		compAttrb.append(recordSep);

		compAttrb.append("CITYCOUN2");
		compAttrb.append(fieldSep);
		compAttrb.append(compCityCntry2);
		compAttrb.append(recordSep);

		compAttrb.append("CITYCOUN3");
		compAttrb.append(fieldSep);
		compAttrb.append(compCityCntry3);
		compAttrb.append(recordSep);

		compAttrb.append("INCDT");
		compAttrb.append(fieldSep);
		compAttrb.append(calendarDate(compIncorpDate));
		compAttrb.append(recordSep);

		compAttrb.append("APPRMNAME");
		compAttrb.append(fieldSep);
		compAttrb.append(compAppRMName);
		compAttrb.append(recordSep);

		compAttrb.append("APPRMCODE");
		compAttrb.append(fieldSep);
		compAttrb.append(compAppRMCode);
		compAttrb.append(recordSep);

		compAttrb.append("SECRMNAME");
		compAttrb.append(fieldSep);
		compAttrb.append(compSecRMName);
		compAttrb.append(recordSep);

		compAttrb.append("SECRMCODE");
		compAttrb.append(fieldSep);
		compAttrb.append(compSecRMCode);
		compAttrb.append(recordSep);

		compAttrb.append("ACCMORGNRCVD");
		compAttrb.append(fieldSep);
		compAttrb.append(compAccMOrgNRcvd);
		compAttrb.append(recordSep);

		compAttrb.append("DT1SRORGNRCVD");
		compAttrb.append(fieldSep);
		compAttrb.append(compdt1SrOrgNRcvd);
		compAttrb.append(recordSep);

		compAttrb.append("CSCFRMRCVD");
		compAttrb.append(fieldSep);
		compAttrb.append(compCSCFrmRcvd);
		compAttrb.append(recordSep);

		compAttrb.append("SRCTURNOVER");
		compAttrb.append(fieldSep);
		compAttrb.append(compSrcTurnOver);
		compAttrb.append(recordSep);

		compAttrb.append("INC_CITY");
		compAttrb.append(fieldSep);
		compAttrb.append(incpCity);
		compAttrb.append(recordSep);

		compAttrb.append("INC_STATE");
		compAttrb.append(fieldSep);
		compAttrb.append(incpState);
		compAttrb.append(recordSep);

		compAttrb.append("INC_COUNTRY");
		compAttrb.append(fieldSep);
		compAttrb.append(incpCountry);
		compAttrb.append(recordSep);

		compAttrb.append("CRSCHANNEL");
		compAttrb.append(fieldSep);
		compAttrb.append(crsChannel);
		compAttrb.append(recordSep);

		compAttrb.append("CRSCLASSIFICATIONDATE");
		compAttrb.append(fieldSep);
		compAttrb.append(calendarDate(crsClassificationDate));
		compAttrb.append(recordSep);

		compAttrb.append("CRSCLASSIFICATIONID");
		compAttrb.append(fieldSep);
		compAttrb.append(crsClassificationId);
		compAttrb.append(recordSep);

		compAttrb.append("CRSCERTFORMOBTAINED");
		compAttrb.append(fieldSep);
		compAttrb.append(crsCertFormObtained);
		compAttrb.append(recordSep);

		compAttrb.append("CRSCUSTBIRTHCITY");
		compAttrb.append(fieldSep);
		compAttrb.append(crsCustBirthCity);
		compAttrb.append(recordSep);

		compAttrb.append("CRSCUSTFIRSTNAME");
		compAttrb.append(fieldSep);
		compAttrb.append(crsCustFirstName);
		compAttrb.append(recordSep);

		compAttrb.append("CRSCUSTID");
		compAttrb.append(fieldSep);
		compAttrb.append(crsCustId);
		compAttrb.append(recordSep);

		compAttrb.append("CRSCUSTLASTNAME");
		compAttrb.append(fieldSep);
		compAttrb.append(crsCustLastName);
		compAttrb.append(recordSep);

		compAttrb.append("CRS_CUSTOMERTYPE");
		compAttrb.append(fieldSep);
		compAttrb.append(crs_CustomerType);
		compAttrb.append(recordSep);

		compAttrb.append("CRSENTITYTYPEID");
		compAttrb.append(fieldSep);
		compAttrb.append(crsEntityTypeId);
		compAttrb.append(recordSep);

		compAttrb.append("CRSMAKERDATE");
		compAttrb.append(fieldSep);
		compAttrb.append(calendarDate(crsMakerDate));
		compAttrb.append(recordSep);

		compAttrb.append("CRSMAKERID");
		compAttrb.append(fieldSep);
		compAttrb.append(crsMakerId);
		compAttrb.append(recordSep);

		compAttrb.append("CRSRESADDCONFIRM");
		compAttrb.append(fieldSep);
		compAttrb.append(crsResidenceAddressConfirmationStatus);
		compAttrb.append(recordSep);

		compAttrb.append("CRSTCACCEPTED");
		compAttrb.append(fieldSep);
		compAttrb.append(crsTermsAndCondAccepted);
		compAttrb.append(recordSep);

		compAttrb.append("FATCA_CUSTCLSFCTNDT");
		compAttrb.append(fieldSep);
		compAttrb.append(calendarDate(fatca_customerClsfctnDate));// Date
		compAttrb.append(recordSep);

		compAttrb.append("FATCA_CUSTFATCACLSFCTNDATE");
		compAttrb.append(fieldSep);
		compAttrb.append(fatca_customerFATCAClsfctnDate);
		compAttrb.append(recordSep);

		compAttrb.append("CRSRELREFNO");
		compAttrb.append(fieldSep);
		compAttrb.append(crsRelRefNo);
		compAttrb.append(recordSep);

		compAttrb.append("CRSCERTIFICATIONDATE");
		compAttrb.append(fieldSep);
		compAttrb.append(calendarDate(crsCertificationDate));// Date
		compAttrb.append(recordSep);

		compAttrb.append("CB2CODE");
		compAttrb.append(fieldSep);
		compAttrb.append(compCB2Code);
		compAttrb.append(recordSep);

		compAttrb.append("SRV_REMARKS");
		compAttrb.append(fieldSep);
		compAttrb.append(compSrvRemarks);
		compAttrb.append(recordSep);

		compAttrb.append("PREV_RISK");
		compAttrb.append(this.fieldSep);
		compAttrb.append(sPrvsRisk);
		compAttrb.append(this.recordSep);

		// added by naga for new column customertypeflag 30/06/2020

		compAttrb.append("EXISTING_CUST");
		compAttrb.append(this.fieldSep);
		compAttrb.append(EXISTING_CUST);
		compAttrb.append(this.recordSep);

		compAttrb.append("CRSUPDATEFLAG");
		compAttrb.append(this.fieldSep);
		compAttrb.append(crsUpdateFlag);
		compAttrb.append(this.recordSep);

		compAttrb.append("FATCA_UPDATEFLAG");
		compAttrb.append(this.fieldSep);
		compAttrb.append(fatca_fatcaUpdateFlag);
		compAttrb.append(this.recordSep);

		// added by sharan new tags 01/07/2020

		compAttrb.append("TL_ISSUING_AUTHORITY");
		compAttrb.append(this.fieldSep);
		compAttrb.append(tlIssuingAuthority);
		compAttrb.append(this.recordSep);

		compAttrb.append("TL_UPDATE_FLAG");
		compAttrb.append(this.fieldSep);
		compAttrb.append(tlUpdateFlag);
		compAttrb.append(this.recordSep);

		compAttrb.append("NO_OF_SIGNATURES");
		compAttrb.append(this.fieldSep);
		compAttrb.append(noOfSignatures);
		compAttrb.append(this.recordSep);

		//Added by Shivanshu
		compAttrb.append("DIGISIGNFLAG");
		compAttrb.append(this.fieldSep);
		compAttrb.append(digiSignFlag);
		compAttrb.append(this.recordSep);

		compAttrb.append("DIGISIGNREASON");
		compAttrb.append(this.fieldSep);
		compAttrb.append(digiSignReason);
		compAttrb.append(this.recordSep);
		
		// mobile falg update 16/08/2020

		compAttrb.append("MOBILE_CC_FLAG");
		compAttrb.append(this.fieldSep);
		compAttrb.append(mobileCCFlag);
		compAttrb.append(this.recordSep);

		compAttrb.append("MOBILE_VERIFICATION_FLAG");
		compAttrb.append(this.fieldSep);
		compAttrb.append("N");
		compAttrb.append(this.recordSep);
		// compCB2Code
		// added by sahil for new tags 8-apr-2020

		compAttrb.append("COMP_MOBILE_NUMBER");
		compAttrb.append(this.fieldSep);
		compAttrb.append(MobileNumber);
		compAttrb.append(this.recordSep);

		compAttrb.append("COMP_REGISTRATION_PLACE_KEY");
		compAttrb.append(this.fieldSep);
		compAttrb.append(RegPlaceKey);
		compAttrb.append(this.recordSep);

		// compAttrb.append("COMP_REGISTRATION_PLACE_VALUE");
		// compAttrb.append(this.fieldSep);
		// compAttrb.append(RegPlaceValue);
		// compAttrb.append(this.recordSep);

		compAttrb.append("COMP_ECONOMIC_ACTIVITY_KEY");
		compAttrb.append(this.fieldSep);
		compAttrb.append(EconomicActivityKey);
		compAttrb.append(this.recordSep);

		
		compAttrb.append("COMP_ECONOMIC_ACTIVITY_VALUE");
		compAttrb.append(this.fieldSep);
		compAttrb.append(EconomicActivityValue);
		compAttrb.append(this.recordSep);
		 
		return compAttrb.toString();
	}

	private String setAttributeHeader() {

		StringBuffer headerAttrb = new StringBuffer();

		headerAttrb.append("PREV_WS");
		headerAttrb.append(fieldSep);
		headerAttrb.append(QueueName);
		headerAttrb.append(recordSep);

		headerAttrb.append("LEAD_REF_NO");
		headerAttrb.append(fieldSep);
		headerAttrb.append(LeadRefNo);
		headerAttrb.append(recordSep);

		headerAttrb.append("SOURCING_CHANNEL");
		headerAttrb.append(fieldSep);
		headerAttrb.append(SourceChannel);
		headerAttrb.append(recordSep);

		headerAttrb.append("SOURCING_CENTER");
		headerAttrb.append(fieldSep);
		headerAttrb.append(SourcingCentre);
		headerAttrb.append(recordSep);

		headerAttrb.append("LEAD_SUBMIT_DATE");
		headerAttrb.append(fieldSep);
		headerAttrb.append(LeadSubDtTm);
		headerAttrb.append(recordSep);

		headerAttrb.append("HOME_BRANCH");
		headerAttrb.append(fieldSep);
		headerAttrb.append(HomeBranch);
		headerAttrb.append(recordSep);
		
		

		headerAttrb.append("ITRCOUNT");
		headerAttrb.append(fieldSep);
		headerAttrb.append(IterationCount);
		headerAttrb.append(recordSep);

		headerAttrb.append("ITERATION");
		headerAttrb.append(fieldSep);
		headerAttrb.append(itrationValue);
		headerAttrb.append(recordSep);

		headerAttrb.append("REQ_TYPE");
		headerAttrb.append(fieldSep);
		headerAttrb.append(ReqType);
		headerAttrb.append(recordSep);

		// headerAttrb.append("CURR_WS");
		// headerAttrb.append(fieldSep);
		// headerAttrb.append(QueueName);
		// headerAttrb.append(recordSep);
		headerAttrb.append("INITIATORNAME");
		headerAttrb.append(fieldSep);
		headerAttrb.append(InitiatorName);
		headerAttrb.append(recordSep);

		headerAttrb.append("APP_RISK");
		headerAttrb.append(fieldSep);
		headerAttrb.append(compRMRiskCls);
		headerAttrb.append(recordSep);

		headerAttrb.append("LAST_PRCE_UNAME");
		headerAttrb.append(fieldSep);
		headerAttrb.append(LastProcessedByUserName);
		headerAttrb.append(recordSep);

		headerAttrb.append("RM_CODE");
		headerAttrb.append(fieldSep);
		headerAttrb.append(compRMCode);
		headerAttrb.append(recordSep);

		headerAttrb.append("RM_NAME");
		headerAttrb.append(fieldSep);
		headerAttrb.append(compRMName);
		headerAttrb.append(recordSep);

		headerAttrb.append("RM_EMAIL");
		headerAttrb.append(fieldSep);
		headerAttrb.append(RMEmail);
		headerAttrb.append(recordSep);

		headerAttrb.append("RMMNGNAME");
		headerAttrb.append(fieldSep);
		headerAttrb.append(RMMngName);
		headerAttrb.append(recordSep);

		headerAttrb.append("RMMNGEMAIL");
		headerAttrb.append(fieldSep);
		headerAttrb.append(RMMngEmail);
		headerAttrb.append(recordSep);

		// CR2019
		headerAttrb.append("INITIATORBY");
		headerAttrb.append(fieldSep);
		headerAttrb.append(InitiatorBy);
		headerAttrb.append(recordSep);

		headerAttrb.append("INITIATEDDEPT");
		headerAttrb.append(fieldSep);
		headerAttrb.append(InitiatedDept);
		headerAttrb.append(recordSep);

		headerAttrb.append("SOURCINGDSACODE");
		headerAttrb.append(fieldSep);
		headerAttrb.append(SourcingDSACode);
		headerAttrb.append(recordSep);

		headerAttrb.append("RM_MOBILENO");
		headerAttrb.append(fieldSep);
		headerAttrb.append(RMMobileNo);
		headerAttrb.append(recordSep);

		headerAttrb.append("TRSD_FLAG");
		headerAttrb.append(fieldSep);
		headerAttrb.append(""); //Added by Shivanshu ATP-521	
		headerAttrb.append(recordSep);

		headerAttrb.append("SOURCEOFLEAD");
		headerAttrb.append(fieldSep);
		headerAttrb.append(SourceOfLead);
		headerAttrb.append(recordSep);
		
		// added by sharan new tag 01/07/2020

		headerAttrb.append("LEAD_TYPE");
		headerAttrb.append(fieldSep);
		headerAttrb.append(leadType);
		headerAttrb.append(recordSep);
		
		// added by reyaz 28-11-2022
		headerAttrb.append("REWORK_FLAG");
		headerAttrb.append(fieldSep);
		headerAttrb.append(reworkValue);
		headerAttrb.append(recordSep);

		headerAttrb.append("DUAL_LANE_FLAG");
		headerAttrb.append(fieldSep);
		headerAttrb.append(duallaneValue);
		headerAttrb.append(recordSep);
		
		// end
		return headerAttrb.toString();
	}

	@SuppressWarnings("unused")
	private boolean validateFatcaOwnershipInfo(FatcaOwnerShipDtls ftca) {
		if (isEmptyStr(ftca.getOwnerNumber())) {
			setErrorResponseMsg("-4030", "");

			return false;
		} else if (isEmptyStr(ftca.getOwnerName())) {
			setErrorResponseMsg("-4031", "");

			return false;
		} else if (isEmptyStr(ftca.getOwnershipAddress())) {
			setErrorResponseMsg("-4032", "");

			return false;
		} else if (isEmptyStr(ftca.getOwnershipSharePercentage())) {
			setErrorResponseMsg("-4033", "");

			return false;
		} else if (isEmptyStr(ftca.getOwnerTINorSSN())) {
			setErrorResponseMsg("-4034", "");

			return false;
		} else if (isEmptyStr(ftca.getOwnerW9Availability())) {
			setErrorResponseMsg("-4035", "");

			return false;
		}

		return true;
	}

	@SuppressWarnings("unused")
	private boolean validateCrsCPInfo(EntityControlPersonsDtls ecpd) {
		if (isEmptyStr(ecpd.getControlPersonBirthCity())) {
			setErrorResponseMsg("-4037", "");
			return false;
		} else if (isEmptyStr(ecpd.getControlPersonBirthCountry())) {
			setErrorResponseMsg("-4038", "");
			return false;
		} else if (isEmptyStr(ecpd.getControlPersonBuildingName())) {
			setErrorResponseMsg("-4039", "");
			response.setStatus("Fail");

			return false;
		} else if (isEmptyStr(ecpd.getControlPersonCity())) {
			setErrorResponseMsg("-4040", "");

			return false;
		} else if (isEmptyStr(ecpd.getControlPersonCountry())) {
			setErrorResponseMsg("-4041", "");

			return false;
		} else if (isEmptyStr(ecpd.getControlPersonDateOfBirth())) {
			setErrorResponseMsg("-4042", "");
			return false;
		} // else if(isEmptyStr(ecpd.getControlPersonEmirate())){
			// setErrorResponseMsg("-4043","");
			// return false;
			// }
		else if (isEmptyStr(ecpd.getControlPersonFirstName())) {
			setErrorResponseMsg("-4044", "");
			return false;
		} else if (isEmptyStr(ecpd.getControlPersonFlatVillaNo())) {
			setErrorResponseMsg("-4045", "");
			return false;
		} else if (isEmptyStr(ecpd.getControlPersonLastName())) {
			setErrorResponseMsg("-4046", "");
			return false;
		} else if (isEmptyStr(ecpd.getControlPersonStreet())) {
			setErrorResponseMsg("-4047", "");
			return false;
		} else if (isEmptyStr(ecpd.getControlPersonControlTypeId())) {
			setErrorResponseMsg("-4048", "");
			return false;
		} else if (isEmptyStr(ecpd.getControlPersonId())) {
			setErrorResponseMsg("-4049", "");
			return false;
		} else if (isEmptyStr(ecpd.getControlPersonPrimaryKey())) {
			setErrorResponseMsg("-4050", "");
			return false;
		} /*
		 * else if (isEmptyStr(ecpd.getControlPersonReasonId())) {
		 * setErrorResponseMsg("-4051", ""); return false; } else if
		 * (isEmptyStr(ecpd.getControlPersonTaxpayerIdNo())) {
		 * setErrorResponseMsg("-4052", ""); return false; } else if
		 * (isEmptyStr(ecpd.getControlPersonTaxResCountry())) {
		 * setErrorResponseMsg("-4053", ""); return false; }
		 */
		return true;
	}

	@SuppressWarnings("unused")
	private boolean validateCrsTaxResidanceInfo(TaxResidenceCountriesDtls trcd) {
		if (isEmptyStr(trcd.getTaxpayerIdNumber())) {
			setErrorResponseMsg("-4055", "");
			return false;
		} else if (isEmptyStr(trcd.getTaxResidenceCountry())) {
			setErrorResponseMsg("-4056", "");
			return false;
		} else if (isEmptyStr(trcd.getReasonId())) {
			setErrorResponseMsg("-4057", "");
			return false;
		} else if (isEmptyStr(trcd.getReasonDesc())) {
			setErrorResponseMsg("-4058", "");
			return false;
		}
		return true;
	}

	private boolean validateAUSPersonalDtls(CutomerPersonalDtls cust) {
		if (isEmptyStr(cust.getCustCat())) {
			setErrorResponseMsg("-4022", "");
			return false;
		} else if (isEmptyStr(cust.getCustFullName())) {
			setErrorResponseMsg("-4023", "");
			return false;
		} else if (isEmptyStr(cust.getCustPrefix())) {
			setErrorResponseMsg("-4024", "");
			return false;
		} else if (isEmptyStr(cust.getCusttype())) {
			setErrorResponseMsg("-4025", "");
			return false;
		} /*else if ( NTBAusFound && isEmptyStr(cust.getProfitCenter())) {
			setErrorResponseMsg("-4028", "");
			return false;
		} */else if (isEmptyStr(cust.getSignatureType())) {
			setErrorResponseMsg("-4029", "");
			return false;
		} else if (isEmptyStr(cust.getDOB())) {
			setErrorResponseMsg("-3054", "");
			return false;
		} else if (!isValidDate(cust.getDOB())) {
			setErrorResponseMsg("-3087", "");
			return false;
		} else if (isEmptyStr(cust.getGender())) {
			setErrorResponseMsg("-3057", "");
			return false;
		} else if (isEmptyStr(cust.getTelMob())) {
			setErrorResponseMsg("-4063", "");
			return false;
		} else if (isEmptyStr(cust.getTelLandLine())) {
			setErrorResponseMsg("-4062", "");
			return false;
		} else if (isEmptyStr(cust.getPassportNo())) {
			setErrorResponseMsg("-4064", "");
			return false;
		} else if (validateWithEmptycheck(cust.getPassportNo())
				&& !isValidDate(cust.getPassportExpiryDate())) {

			setErrorResponseMsg("-3091", "");
			return false;
		} else if (validateWithEmptycheck(cust.getVisaNo())
				&& !isValidDate(cust.getVisaExpiryDate())) {

			setErrorResponseMsg("-3089", "");
			return false;
		}
		return true;
	}

	private boolean validateWithEmptycheck(String str) {
		str = checkReqData(str);
		if (!str.isEmpty()) {
			return true;
		}
		return false;
	}

	private boolean validateAUSAddress(CutomerPersonalDtls cust) {
		// if(isEmptyStr(cust.getAddress())){
		// setErrorResponseMsg("-4011","");
		// return false;
		// }else if(isEmptyStr(cust.getAddressLine1())){
		// setErrorResponseMsg("-4012","");
		// return false;
		// }else if(isEmptyStr(cust.getAddressLine2())){
		// setErrorResponseMsg("-4013","");
		// return false;
		// }else
		if (isEmptyStr(cust.getCorrCntry())) {
			setErrorResponseMsg("-4016", "");
			return false;
		} else if (isEmptyStr(cust.getCountryRes())) {
			setErrorResponseMsg("-4017", "");
			return false;
		} else if (isEmptyStr(cust.getNationality())) {
			setErrorResponseMsg("-4018", "");
			return false;
		}
		// else if(isEmptyStr(cust.getState())){
		// setErrorResponseMsg("-4019","");
		// return false;
		// }else if(isEmptyStr(cust.getTown())){
		// setErrorResponseMsg("-4020","");
		// return false;
		// }

		return true;
	}

	private boolean isEmptyStr(String str) {
		if (str != null && str.isEmpty()) {
			return true;
		}
		return false;
	}

	private boolean serviceRequestAuditLog() throws WbgAccountOpenningException {
		try {
			org.apache.axis2.context.MessageContext messagecontext = org.apache.axis2.context.MessageContext
					.getCurrentMessageContext();
			log.info("Getting HTTP Headers");
			HttpServletRequest httpReq = (HttpServletRequest) messagecontext
					.getProperty("transport.http.servletRequest");
			String sHostIP = httpReq.getHeader("HOSTIP");
			String sHostName = httpReq.getHeader("HOSTNAME");
			String sRemoteIP = httpReq.getRemoteAddr();
			String sRemoteHost = httpReq.getRemoteHost();
			InetAddress inetAddress = InetAddress.getByName(sRemoteIP);
			String sComputerName = inetAddress.getHostName();
			log.info("Computer Name: " + sComputerName);
			if (sComputerName.equalsIgnoreCase("localhost")) {
				sComputerName = InetAddress.getLocalHost()
						.getCanonicalHostName();
			}
			log.info("Computer Name: " + sComputerName + ", sHostIP: "
					+ sHostIP + ", sHostName: " + sHostName + ", sRemoteIP: "
					+ sRemoteIP + ", sRemoteHost: " + sRemoteHost);
			log.info("Below is the list of all HTTP headers");
			if (sHostIP == null || sHostIP.equalsIgnoreCase("null")
					|| sHostIP.trim().equalsIgnoreCase("")) {
				sHostIP = sRemoteIP;
			}
			if (sHostName == null || sHostName.equalsIgnoreCase("null")
					|| sHostName.trim().equalsIgnoreCase("")) {
				sHostName = sComputerName;
			}
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			String date = sdf.format(d);

			String reqXML = messagecontext.getOperationContext()
					.getMessageContext(WSDLConstants.MESSAGE_LABEL_IN_VALUE)
					.getEnvelope().toString().trim();
			log.info("reqXML" + reqXML);
			String result = captureSoapRequest(sysRefNumber, sHostName,
					sHostIP, consumer, username, sUsername, "AccountOpening",
					date, reqXML);
			if (!SUCCESS_STATUS.equalsIgnoreCase(result)) {
				response.setStatus("Fail");
				response.setErrorCode("-11084");
				response.setDescription(getStatuMessageFromCode("-11084"));
				response.setMessage(xmlobj.getValueOf("Output").trim());
				throw new WbgAccountOpenningException("-11084",
						getStatuMessageFromCode("-11084"), xmlobj.getValueOf("Output")
								.trim());
			}

		} catch (IOException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}
		return true;
	}

	private void setErrorResponseMsg(String errorCode, String errorMsg) {

		response.setStatus("Fail");
		response.setErrorCode(errorCode);
		if (errorMsg != null && errorMsg.trim().isEmpty()) {
			response.setMessage(getStatuMessageFromCode(errorCode));
		} else {
			response.setMessage(errorMsg);
		}
		response.setDescription(getStatuMessageFromCode(errorCode));
		respHead.setRerrorDetail(getStatuMessageFromCode(errorCode));
		respHead.setRerrorDescription(getStatuMessageFromCode(errorCode));
		repTimeStamp = getDate();
		respHead.setRreqTimeStamp(reqTimeStamp);
		respHead.setRrepTimeStamp(repTimeStamp);
		respHead.setRconsumer(consumer);
		respHead.setRcorrelationID(correlationID);
		respHead.setRsysRefNumber(sysRefNumber);
		respHead.setRcredentials(credentials);
		respHead.setRreturnCode(returnCode);
		respHead.setRsenderID(senderID);
		respHead.setRserviceAction(serviceAction);
		respHead.setRserviceName(serviceName);
		respHead.setRsysRefNumber(sysRefNumber);
		respHead.setRusecaseID(usecaseID);
		respHead.setRusername(username);
		respHead.setRversionNo(versionNo);
		response.setRespHeader(respHead);
	}

	private String inputXMLFetchDocTracker(String leadRefNo,
			String systemRefNo, String userName) {

		String inputXml = "<?xml version=\"1.0\"?>\n"
				+ "<APWebService_Input>\n" + "<Option>WebService</Option>\n"
				+ "<Calltype>Fetch_Doc_Tracker</Calltype>\n"
				+ "<WI_NAME></WI_NAME>\n" + "<SYSTEMREFNO>" + systemRefNo
				+ "</SYSTEMREFNO>\n" + "<EngineName>" + sCabinetName
				+ "</EngineName>\n" + "<LEAD_REFNO>" + leadRefNo
				+ "</LEAD_REFNO>\n" + "<USERNAME>" + userName + "</USERNAME>\n";
		return inputXml;
	}

	private String excecuteFetchDocTracker(String leadRefNo,
			String systemRefNo, String userName) {

		try {
			sInputXML = inputXMLFetchDocTracker(leadRefNo, systemRefNo,
					userName);
			log.debug( "fetch doc tracker input : "+sInputXML);
			//sOutputXML = executeAPI(sInputXML);
			sOutputXML = socket.connectToSocket(sInputXML);
			log.debug( "fetch doc tracker output : "+sOutputXML);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sOutputXML;
	}

	private void validateDocTrcRes(String inputXML)
			throws WbgAccountOpenningException {
		log.info("Inside validateDocTrcRes");
		if (leadRefNumber.startsWith("LRNMO")) {
			return;
		}
		xp.setInputXML(inputXML);
		int count = 0;
		try {
			count = Integer.parseInt(xp.getValueOf("Records"));
			log.info("Inside validateDocTrcRes count: "+count);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		log.info("Inside validateDocTrcRes returnCode "+xp.getValueOf("returnCode"));
		if (!(xp.getValueOf("returnCode").trim()
				.equalsIgnoreCase(SUCCESS_STATUS) || xp
				.getValueOf("returnCode").trim().equalsIgnoreCase("Success"))) {
			setErrorResponseMsg("-11085", xp.getValueOf("Output").trim());
			throw new WbgAccountOpenningException("-11085",
					getStatuMessageFromCode("-11085"), xp.getValueOf("Output").trim());

		} else if ((xp.getValueOf("returnCode").trim()
				.equalsIgnoreCase(SUCCESS_STATUS) || xp
				.getValueOf("returnCode").trim().equalsIgnoreCase("Success"))) {
			if (count < 1) {
				setErrorResponseMsg("-11086", xp.getValueOf("Output").trim());
				throw new WbgAccountOpenningException("-11086",
						getStatuMessageFromCode("-11086"), xp.getValueOf("Output")
								.trim());
			}
		}
	}

	private void setSuccessResponse(String strWorkitemName) {
		repTimeStamp = getDate();
		response.setStatus("Success");
		response.setDescription("Success");
		response.setErrorCode("0");
		respHead.setRreqTimeStamp(reqTimeStamp);
		respHead.setRrepTimeStamp(repTimeStamp);
		respHead.setRconsumer(consumer);
		respHead.setRcorrelationID(correlationID);
		respHead.setRsysRefNumber(sysRefNumber);
		respHead.setRcredentials(credentials);
		respHead.setRreturnCode(returnCode);
		respHead.setRsenderID(senderID);
		respHead.setRserviceAction(serviceAction);
		respHead.setRserviceName(serviceName);
		respHead.setRsysRefNumber(sysRefNumber);
		respHead.setRusecaseID(usecaseID);
		respHead.setRusername(username);
		respHead.setRversionNo(versionNo);
		response.setWorkItemNo(strWorkitemName);
		response.setRespHeader(respHead);
	}

	private String getDocTempDtls(String xmlList) {
		log.debug("GetDocTemp Details inside");
		int i = 21;
		int j = 25;
		StringBuffer sBuffer = new StringBuffer();
		try {
		WFXmlResponse xmlResponse = new WFXmlResponse(xmlList);
		log.debug("GetDocTemp Details inside2");

		WFXmlList lWfXml = xmlResponse.createList("DocumentsDtls", "Documents");
		log.debug("GetDocTemp Details inside3");

			for (int k = 0; lWfXml.hasMoreElements(true); lWfXml.skip(true), k++) {
				// if
				// ("Reviewed".equalsIgnoreCase(lWfXml.getVal("DocumentStatus").toString()))
				// {
				sBuffer.append(lWfXml.getVal("DocumentName"));
				sBuffer.append((char) i);
				sBuffer.append(lWfXml.getVal("IMAGEINDEX") + "#"
						+ lWfXml.getVal("VOLUMEID"));
				sBuffer.append((char) i);
				sBuffer.append(lWfXml.getVal("NOOFPAGES"));
				sBuffer.append((char) i);
				sBuffer.append(lWfXml.getVal("DOCUMENTSIZE"));
				sBuffer.append((char) i);
				sBuffer.append(lWfXml.getVal("DocExt"));
				sBuffer.append((char) i);
				sBuffer.append(checkLength(checkReqData(lWfXml
						.getVal("CustomerName"))));
				sBuffer.append((char) j);
				// } else {
				// log.debug("Other than Reviewed Documents are: " +
				// lWfXml.getVal("DocumentName"));
				// }
	
			}
		} catch(Throwable e){
			log.error("Exception", e);
			log.debug("GetDocTemp Details Exception" + e);
		}
		log.debug("GetDocTemp Details " + sBuffer.toString());
		return sBuffer.toString();
	}

	private String checkLength(String tagName) {
		if (tagName != null) {
			if (tagName.length() > 50) {
				return tagName.substring(0, 49);

			} else {
				return tagName;
			}
		}
		return "";
	}

	private String checkDocLList(String xmlList) {
		log.info("Inside checkDocLList" +xmlList);
		try{
			boolean imgCheck = false;
			StringBuffer sBuffer = new StringBuffer();
			WFXmlResponse xmlResponse = new WFXmlResponse(xmlList);
			log.info("Inside checkDocLList xmlResponse "+xmlResponse);
			WFXmlList lWfXml = xmlResponse.createList("DocumentsDtls", "Documents");
			log.info("Inside checkDocLList WFXmlList "+lWfXml);
			for (int k = 0; lWfXml.hasMoreElements(true); lWfXml.skip(true), k++) {
				if (checkNullval(lWfXml.getVal("IMAGEINDEX"))
						|| checkNullval(lWfXml.getVal("VOLUMEID"))
						|| checkNullval(lWfXml.getVal("NOOFPAGES"))
						|| checkNullval(lWfXml.getVal("DOCUMENTSIZE"))
						|| checkNullval(lWfXml.getVal("DocExt"))) {
					sBuffer.append(lWfXml.getVal("CustomerName"));
					sBuffer.append("-");
					sBuffer.append(lWfXml.getVal("DocumentName"));
					sBuffer.append("\n");
					log.info("checkDocLList" + sBuffer.toString());
					imgCheck = true;
					continue;
				}
			}
			log.info("checkDocLList imgCheck: " + imgCheck);
			if (imgCheck) {
				log.info("checkDocLList" + sBuffer.toString());
			}
			log.info("checkDocLList" + sBuffer.toString());
			return sBuffer.toString();
		} catch(Throwable e){
			try {
				e.printStackTrace(new PrintStream(new FileOutputStream("debug.txt")));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.info("checkDocLList" + e);
			e.printStackTrace();
		}
		return "";
	}

	private boolean checkNullval(String val) {
		log.info("checkNullval: "+val);
		if ((val == null || "null".equalsIgnoreCase(val) || ""
				.equalsIgnoreCase(val.trim()))) {
			return true;
		}
		return false;
	}

	private String captureSoapRequest(String refno, String hostName,
			String hostIp, String sourceApp, String appUser, String userName,
			String funName, String reqTime, String reqXMl) {
		// String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
		Connection conn = null;
		PreparedStatement opstmt = null;
		String Data = "";
		try {
			log.info("Befor databse values");
			// Class.forName(driver);
			log.info("After databse values");
			// conn = DriverManager.getConnection(dburl, dbuser, dbpass);
			conn = NGDBConnection.getDBConnection(sCabinetName, "");
			Clob clob = conn.createClob();
			log.info("Connection Successful" + Query);
			opstmt = conn.prepareStatement(Query);
			opstmt.setString(1, refno);
			opstmt.setString(2, hostName);
			opstmt.setString(3, hostIp);
			opstmt.setString(4, sourceApp);
			opstmt.setString(5, appUser);
			opstmt.setString(6, userName);
			opstmt.setString(7, funName);
			// opstmt.setString(8, reqTime);
			clob.setString(1, reqXMl);
			opstmt.setClob(8, clob);
			opstmt.executeUpdate();
			log.info("After Execute");
			Data = "0";
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			Data = "-1";
		} finally {
			try {
				if (opstmt != null) {
					opstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				Data = "-1";
			}
		}
		return Data;

	}

	private String fatcaDocDate(String fatca_dateOfDocument) {
		if (!("NA".equalsIgnoreCase(fatca_documentCollected) || isEmptyStr(fatca_dateOfDocument))) {
			return calendarDate(fatca_dateOfDocument);
		}
		return "";

	}

	private String insertDecAuditTrail(String ProcessID) {
		log.debug("INSIDE insertDecAuditTrail");
		try {
			log.debug("in USR_0_WBG_AO_DEC_HIST");
			String sTable = "USR_0_WBG_AO_DEC_HIST";
			String sOutputXML = null;
			String sColumn = "WI_NAME,LEAD_REFNO,USERID,USERNAME,PRV_WS_NAME,WS_NAME,WS_DECISION,"
					+ "REJ_REASON,WS_COMMENTS,DEC_DATE_TIME,QUEUE_ENTRY_DATE_TIME";

			String dec = "Initiation";
			if (0 < Integer.parseInt(IterationCount)) {
				dec = "Resubmission";
			}
			String sValues = "'" + ProcessID + "','" + LeadRefNo + "','"
					+ LastProcessedByUserName + "','" + LastProcessedByUserName
					+ "','','Introduction','" + dec + "','','"
					+ checkSplChar(compSrvRemarks) + "',sysdate,sysdate";

			String sInputXML = "<?xml version=\"1.0\"?><APInsert_Input><Option>APInsert</Option><TableName>"
					+ sTable
					+ "</TableName>"
					+ "<ColName>"
					+ sColumn
					+ "</ColName>"
					+ "<Values>"
					+ sValues
					+ "</Values>"
					+ "<EngineName>"
					+ sCabinetName
					+ "</EngineName>"
					+ "<SessionId>"
					+ sUserDBId
					+ "</SessionId>"
					+ "</APInsert_Input>";

			// log.debug("sInputXML:"+sInputXML);
			sOutputXML = executeAPI(sInputXML);
			// log.debug("sOUTPUTXML:"+sOutputXML);
			return sOutputXML;

		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return "";
	}

	private String checkReqData(String str) {

		if (str == null || "undefined".equalsIgnoreCase(str.trim())
				|| "null".equalsIgnoreCase(str.trim())
				|| "".equalsIgnoreCase(str.trim())
				|| "?".equalsIgnoreCase(str.trim())) {
			return "";
		}
		return str.trim();
	}

	private String chkStateReqData(String str) {

		if (str == null || "undefined".equalsIgnoreCase(str.trim())
				|| "null".equalsIgnoreCase(str.trim())
				|| "".equalsIgnoreCase(str.trim())
				|| "?".equalsIgnoreCase(str.trim())) {
			return "OTHERS";
		}
		return str.trim();
	}

	private boolean avoidEmptyInsert(String... arg) {
		for (String str : arg) {
			if (!str.isEmpty()) {
				return true;
			}
		}
		return false;
	}

	private String retMainCode() {
		return "<MainCode>0</MainCode>";
	}

	private String existingDocList() {
		if ("N".equalsIgnoreCase(isCallDocTrc)) {
			if (Integer.parseInt(IterationCount) > 0) {
				String sWiname = getWorkItemDetail();
				if (!sWiname.isEmpty()) {
					try {
						sOutputXML = executeAPI(procCallinputXml(
								procOldDocMove, "'" + LeadRefNo + "','"
										+ sysRefNumber + "'"));
						log.info("Copying doc table document to temp doc table for Existing DocCheckList"
								+ sOutputXML);
						String sQuery = "SELECT DOCUMENTNAME AS DOCNAME,IMAGEINDEX AS DIMGINDX,VOLUMEID AS DOCVOLID,NOOFPAGES AS DOCPAGE, DOCSIZE AS DOCSIZE1,DOCEXT AS DOCAPP,CUSTOMERNAME AS DOCCMNT"
								+ " FROM USR_0_WBG_AO_DOC WHERE WI_NAME ='"
								+ sWiname
								+ "' AND DOCUMENTNAME IN ("
								+ optDocList + ") AND SYSNAME='WMS'";
						sInputXML = getDBSelectInput(sQuery, sCabinetName,
								sUserDBId);
						sOutputXML = executeAPI(sInputXML);
						return prepareExistingDocList(sOutputXML);
					} catch (Exception e) {
						log.error("Existing doc check", e);
					}
				}
			}
		}

		return "";
	}

	private String prepareExistingDocList(String xmlList) {
		int i = 21;
		int j = 25;
		StringBuilder sBuffer = new StringBuilder();
		WFXmlResponse xmlResponse = new WFXmlResponse(xmlList);
		WFXmlList lWfXml = xmlResponse.createList("Records", "Record");
		for (int k = 0; lWfXml.hasMoreElements(true); lWfXml.skip(true), k++) {
			sBuffer.append(checkReqData(lWfXml.getVal("DOCNAME")));
			sBuffer.append((char) i);
			sBuffer.append(lWfXml.getVal("DIMGINDX") + "#"
					+ lWfXml.getVal("DOCVOLID"));
			sBuffer.append((char) i);
			sBuffer.append(checkLength(checkReqData(lWfXml.getVal("DOCPAGE"))));
			sBuffer.append((char) i);
			sBuffer.append(checkLength(checkReqData(lWfXml.getVal("DOCSIZE1"))));
			sBuffer.append((char) i);
			sBuffer.append(checkLength(checkReqData(lWfXml.getVal("DOCAPP"))));
			sBuffer.append((char) i);
			sBuffer.append(checkLength(checkReqData(lWfXml.getVal("DOCCMNT"))));
			sBuffer.append((char) j);
		}
		return sBuffer.toString();
	}

	private String getWorkItemDetail() {
		try {
			String sQuery = "SELECT EXT.WI_NAME as W_ITEMINDEX FROM EXT_WBG_AO EXT WHERE EXT.LEAD_REF_NO ="
					+ "'"
					+ leadRefNumber
					+ "' AND FINAL_STATUS = 'Return' AND  EXT.LAST_ACTIONDATE =(SELECT MAX(LAST_ACTIONDATE) FROM EXT_WBG_AO "
					+ "WHERE LEAD_REF_NO ='" + leadRefNumber + "')";

			sInputXML = getDBSelectInput(sQuery, sCabinetName, sUserDBId);
			sOutputXML = executeAPI(sInputXML);
			xp.setInputXML(sOutputXML);
			return checkReqData(xp.getValueOf("W_ITEMINDEX"));
		} catch (IOException e) {
			log.error("Existing doc check getWorkItemDetail 1", e);
		} catch (Exception e) {
			log.error("Existing doc check getWorkItemDetail 2", e);
		}
		return "";
	}

	private String procCallinputXml(String ProcName, String params) {
		return "<?xml version=\"1.0\"?><APProcedure_Input><Option>APProcedure</Option>"
				+ "<ProcName>"
				+ ProcName
				+ "</ProcName>"
				+ "<Params>"
				+ params
				+ "</Params>"
				+ "<EngineName>"
				+ sCabinetName
				+ "</EngineName>"
				+ "<SessionId>"
				+ sUserDBId
				+ "</SessionId></APProcedure_Input>";
	}

	private String chkPhoneNumber(String phNo) {
		if (!phNo.isEmpty()) {
			if (phNo.startsWith("971")) {
				return phNo;
			} else if (!phNo.startsWith("971")) {
				return "00" + phNo;
			}
		}
		return "";
	}

	private String getPrvsRisk() {
		try {
			log.debug("Inside getPrvsRisk API");
			if (Integer.parseInt(this.IterationCount) > 0) {
				String sQuery = "SELECT EXT.APP_RISK AS RISK FROM EXT_WBG_AO EXT WHERE EXT.LEAD_REF_NO ='"
						+ this.leadRefNumber
						+ "' AND FINAL_STATUS = 'Return' AND  EXT.LAST_ACTIONDATE =(SELECT MAX(LAST_ACTIONDATE) FROM EXT_WBG_AO "
						+ "WHERE LEAD_REF_NO ='" + this.leadRefNumber + "')";
				String sInputXML = getDBSelectInput(sQuery, sCabinetName,
						this.sUserDBId);
				String sOutputXML = executeAPI(sInputXML);
				this.xp.setInputXML(sOutputXML);
				return checkReqData(this.xp.getValueOf("RISK"));
			}
		} catch (IOException e) {
			log.error("Existing Risk Check getPrvsRisk  1", e);
		} catch (Exception e) {
			log.error("Existing Risk Check getPrvsRisk 2", e);
		}
		return "";
	}

	// new added sharan 02/07/2020
	/*
	 * private String fetchAttributes(AccountOpening req, int i) {
	 * 
	 * if
	 * (req.getApplicationAttributes().getAttributeDetails().getAttributes()!=
	 * null) { try { if
	 * (req.getApplicationAttributes().getAttributeDetails().getAttributes()!=
	 * null) { if
	 * (req.getApplicationAttributes().getAttributeDetails().getAttributes
	 * ().length > 0) { Attributes =
	 * req.getApplicationAttributes().getAttributeDetails().getAttributes();
	 * //Attributes1 = req.getApplicationAttributes().getAttributeDetails(); int
	 * iattr = 0; for (int counter = 0; counter < Attributes.length; counter++)
	 * { iattr = iattr + 1; //for(int counter1 = 0; counter1 <
	 * Attributes1.length; counter1++) { attributeKey =
	 * checkReqData(Attributes[counter].getAttributeKey() == null ? "" :
	 * Attributes[counter].getAttributeKey().trim()); attributeValue =
	 * checkReqData(Attributes[counter].getAttributeValue() == null ? "" :
	 * Attributes[counter].getAttributeValue().trim());
	 * log.debug("attributeKey " + attributeKey); log.debug("attributeValue " +
	 * attributeValue); } } } } catch (Exception e) {
	 * log.debug("Error in FetchAttributes" + e.getMessage()); } } return ""; }
	 */
	// CR2019
	private String fetchKeyContactDetails(AccountOpening req, int i) {
		try {
			if (req.getKeyContactsDetails() != null) {
				if (req.getKeyContactsDetails().getKeyContactsInfoObj() != null) {
					if (req.getKeyContactsDetails().getKeyContactsInfoObj().length > 0) {
						KeyContactInfo1 = req.getKeyContactsDetails()
								.getKeyContactsInfoObj();
						String tabName = "USR_0_WBG_TMP_KEY_CONTACTS";
						int iUbo = 0;
						String colName = "LEAD_REFNO,SNO,TITLE,NATIONALITY,NAME,CONTROLLING_INTEREST,DOB,SYSREFNO";
						String title = "", nationality = "", name = "", controllingInterest = "", dob = "";
						for (int counter = 0; counter < KeyContactInfo1.length; counter++) {
							iUbo = iUbo + 1;
							title = checkReqData(KeyContactInfo1[counter]
									.getTitle());
							nationality = checkReqData(KeyContactInfo1[counter]
									.getNationality() == null ? ""
									: KeyContactInfo1[counter].getNationality()
											.trim());
							name = checkReqData(KeyContactInfo1[counter]
									.getName() == null ? ""
									: KeyContactInfo1[counter].getName().trim());
							controllingInterest = checkReqData(KeyContactInfo1[counter]
									.getControllingInterest() == null ? ""
									: KeyContactInfo1[counter]
											.getControllingInterest().trim());
							dob = checkReqData(KeyContactInfo1[counter]
									.getDOB() == null ? ""
									: KeyContactInfo1[counter].getDOB().trim());

							sOutputXML = insertKeyContatctsDtls(tabName,
									colName, iUbo, title, nationality, name,
									controllingInterest, dob);
							xp.setInputXML(sOutputXML);
							if (!xp.getValueOf("MainCode").trim()
									.equalsIgnoreCase(SUCCESS_STATUS)) {
								return sOutputXML;
							}
						}
					}
				}
			}
		} catch (Exception E) {
			log.debug("Error in fetchKeyContactDetails" + E.getMessage());
		}
		return sOutputXML;
	}

	private String insertKeyContatctsDtls(String tabName, String colName,
			int iUbo, String title, String nationality, String name,
			String controllingInterest, String dob) {
		String tempValues = "" + LeadRefNo + "#~#" + iUbo + "#~#" + title
				+ "#~#" + nationality + "#~#" + name + "#~#"
				+ controllingInterest + "#~#" + dob + "#~#" + sysRefNumber;
		String sValues = removeLastCamma(prepareColValues(tempValues));
		sInputXML = getDBInsertInput(tabName, colName, sValues, sCabinetName,
				sUserDBId);
		try {
			sOutputXML = executeAPI(sInputXML);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sOutputXML;

	}// /end

	// checking only date format and date should not be mandatory
	private boolean isCheckDate(String inDate) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setLenient(false);
		try {
			if (inDate.length() > 9) {
				if (inDate != null) {
					dateFormat.parse(inDate.trim());
				}
			} else {
				return true;
			}

		} catch (Exception pe) {
			log.debug("error::" + pe.getMessage());
			return false;
		}
		return true;
	}

	private String executeRestAPI(String url, String inputXML) throws Exception {
		StringBuffer outputXML = new StringBuffer("");
		HttpURLConnection conn = null;
		try {
			log.debug("URL ..." + url);
			URL urlName = new URL(url);
			conn = (HttpURLConnection) urlName.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/xml");

			OutputStream os = conn.getOutputStream();
			os.write(inputXML.getBytes());
			os.flush();
			log.debug("conn.getResponseCode()===> " + conn.getResponseCode());
			log.debug(" HttpURLConnection.HTTP_CREATED===> "
					+ HttpURLConnection.HTTP_OK);
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));

			log.debug("Output from server ...\n");
			String out;
			while ((out = br.readLine()) != null) {
				outputXML.append(out);
			}
			log.debug("RestAPI output===> " + outputXML);
			// } catch (MalformedURLException e) {
			// log.debug("RestAPI exception1===> " + e.getMessage());
			// } catch (IOException e) {
			// log.debug("RestAPI exception2===> " + e.getMessage());
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return outputXML.toString();
	}

	private String executeRestMultipartAPI(String url, String inputXML)
			throws Exception {
		String outputXML = "";
		HttpURLConnection conn = null;
		try {
			log.debug("URL MULTIPART  ..." + url);
			URL urlName = new URL("http://10.146.168.130:9700/WMS_TFO");
			conn = (HttpURLConnection) urlName.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String JSONInputString = "{'req_process':'create_new_enquiry_with_note','api_version':'v1.0','api_token':'ELZwV8ACgC6PZvfIlF2TJFDV3tyDg01ZROyXPAVI8r813PcRPcCmEjTKIX2B','bl_name':'MSCUEJ031979','enquiry_type':'bol_check','note_subject':'ADCBLCNUMBER','subject':'VesselName','vr_name':'MSC%2520ALICE%2520V.%2520OH821R','output':'JSON%20HTTP%2F1.1%20Content-Type%3A%20application%2Fjson%20Content-Length%3A%200%20Host%3A%2010.146.168.130%3A9700%20Connection%3A%20Keep-Alive%20User-Agent%3A%20Apache-HttpClient%2F4.1.1%20%28java%201.5%29','cced_emails_c':'kirubakaran.ponnusamy%40adcb.com','description':'%20Testing%20create%20New%20from%20ADCB%20HTTP%2F1.1%20Accept-Encoding%3A%20gzip%2Cdeflate%20Host%3A%2010.146.168.130%3A9700%20Connection%3A%20Keep-Alive%20User-Agent%3A%20Apache-HttpClient%2F4.1.1%20%28java%201.5%29'}";
			byte[] input = JSONInputString.getBytes("utf-8");
			OutputStream os = conn.getOutputStream();
			os.write(input, 0, input.length);
			os.flush();
			log.debug("conn.getResponseCode()===> " + conn.getResponseCode());
			log.debug(" HttpURLConnection.HTTP_CREATED===> "
					+ HttpURLConnection.HTTP_OK);
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));

			log.debug("Output from server ...\n");
			String out;
			while ((out = br.readLine()) != null) {
				outputXML = out;
				log.debug("RestAPI output===> " + outputXML);
			}
		} catch (MalformedURLException e) {
			log.debug("RestAPI exception1===> " + e.getMessage());
		} catch (IOException e) {
			log.debug("RestAPI exception2===> " + e.getMessage());
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return outputXML;
	}

	private String getTRSDResponse(String winame) {
		try {
			String name = "";
			String dob = "";
			String gender = "";
			String nationality = "";
			String customerRelationship = "";
			String requestClassification = "";
			StringBuilder trsdReq = new StringBuilder();
			String response = "";
			boolean trsdCaseError = false;
			boolean trsdCaseFound = false;
			boolean trsdSuccess = true;
			boolean[] caseFound;
			long batchId = Long.parseLong(getBatchNum());
			long clientID = getRefNum();
			String channelName = "WBG";
			trsdReq.append(
					"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
					.append("<sdQuery>")
					.append("<types>")
					.append("<type>ORGANISATION</type>")
					.append("</types>")
					.append("<name>" + normalizeString(CompFullName)
							+ "</name>")
					.append("<nationalityName>"
							+ normalizeString(compCntryIncp)
							+ "</nationalityName>")
					.append("<createCaseOnMatches>CREATECASE</createCaseOnMatches>")
					.append("<field1>" + channelName + "</field1>")
					.append("<clientID>" + clientID + "</clientID>")
					.append("<field2>" + winame + "</field2>")
					.append("</sdQuery>");
			log.debug("TRSD Request Entity==> " + trsdReq);
			insertTRSDXmlAudit(winame, trsdReq.toString(), "Request", clientID
					+ "", "S");
			saveTRSDDetails(leadRefNumber, winame, batchId, channelName,
					clientID, CompFullName, "NA", compCntryIncp, "NA",
					"ORGANISATION", "COMPANY_ENTITY");
			try {
				response = executeRestAPI(trsdURL, trsdReq.toString());
			} catch (Exception e) {
				trsdSuccess = false;
				log.debug("Error in getTRSDResponse() company: "
						+ e.getMessage());
				e.printStackTrace();
			}
			log.debug("TRSD response company==> " + response
					+ "trsdsuccessflag " + trsdSuccess);
			caseFound = updateTRSDDetail(response, winame, trsdSuccess,
					clientID + "");
			log.debug("$$" + caseFound[1] + "--" + caseFound[0] + "3439");
			if (caseFound[1] == true) {
				log.debug("///3442");
				trsdCaseError = true;
			} else if (caseFound[0] == true) {
				trsdCaseFound = true;
			}

			if (UBOInfo1 != null) {
				for (int counter = 0; counter < UBOInfo1.length; counter++) {
					name = checkReqData(UBOInfo1[counter].getName() == null ? ""
							: UBOInfo1[counter].getName().trim());
					nationality = checkReqData(UBOInfo1[counter]
							.getNationality() == null ? "" : UBOInfo1[counter]
							.getNationality().trim());
					dob = checkReqData(UBOInfo1[counter].getDOB() == null ? ""
							: UBOInfo1[counter].getDOB().trim());
					customerRelationship = checkReqData(UBOInfo1[counter]
							.getCustomerRelationship());
					gender = checkReqData(UBOInfo1[counter].getGender() == null ? ""
							: UBOInfo1[counter].getGender().trim());
					clientID = getRefNum();
					trsdReq.setLength(0);
					trsdReq.append(
							"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
							.append("<sdQuery>")
							.append("<types>")
							.append("<type>INDIVIDUAL</type>")
							.append("</types>")
							.append("<createCaseOnMatches>CREATECASE</createCaseOnMatches>")
							.append("<field1>" + channelName + "</field1>")
							.append("<clientID>" + clientID + "</clientID>")
							.append("<field2>" + winame + "</field2>")
							.append("<indDob>" + dob + "</indDob>")
							.append("<name>" + normalizeString(name)
									+ "</name>")
							.append("<nationalityName>"
									+ normalizeString(nationality)
									+ "</nationalityName>")
							.append("<indGender>" + normalizeString(gender)
									+ "</indGender>").append("</sdQuery>");
					log.debug("TRSD Request UBOInfo==> " + trsdReq);
					insertTRSDXmlAudit(winame, trsdReq.toString(), "Request",
							clientID + "", "S");
					saveTRSDDetails(
							leadRefNumber,
							winame,
							batchId,
							channelName,
							clientID,
							name,
							dob,
							nationality,
							gender,
							customerRelationship.equalsIgnoreCase("Owner") ? "ORGANISATION"
									: "INDIVIDUAL", customerRelationship);
					try {
						response = executeRestAPI(trsdURL, trsdReq.toString());
					} catch (Exception e) {
						trsdSuccess = false;
						log.debug("Error in getTRSDResponse() UBOInfo1: "
								+ e.getMessage());
						e.printStackTrace();
					}
					log.debug("TRSD response UBOInfo1==> " + response);
					caseFound = updateTRSDDetail(response, winame, trsdSuccess,
							clientID + "");
					log.debug("$$" + caseFound[1] + "--" + caseFound[0]
							+ "3484");
					if (caseFound[1] == true) {
						log.debug("///3487");
						trsdCaseError = true;

					} else if (caseFound[0] == true) {
						trsdCaseFound = true;
					}
				}
			}

			if (CutomerPersonalDtls != null) {
				for (CutomerPersonalDtls cust : CutomerPersonalDtls) {
					name = checkReqData(cust.getCustFullName() == null ? ""
							: cust.getCustFullName().trim());
					dob = checkReqData(cust.getDOB() == null ? "" : cust
							.getDOB().trim());
					nationality = checkReqData(cust.getNationality() == null ? ""
							: cust.getNationality().trim());
					gender = checkReqData(cust.getGender() == null ? "" : cust
							.getGender().trim());
					requestClassification = checkReqData(cust
							.getRequestClassification() == null ? "AUS" : cust
							.getRequestClassification().trim());
					clientID = getRefNum();
					trsdReq.setLength(0);
					trsdReq.append(
							"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
							.append("<sdQuery>")
							.append("<types>")
							.append("<type>INDIVIDUAL</type>")
							.append("</types>")
							.append("<createCaseOnMatches>CREATECASE</createCaseOnMatches>")
							.append("<field1>" + channelName + "</field1>")
							.append("<clientID>" + clientID + "</clientID>")
							.append("<field2>" + winame + "</field2>")
							.append("<indDob>" + dob + "</indDob>")
							.append("<name>" + normalizeString(name)
									+ "</name>")
							.append("<nationalityName>"
									+ normalizeString(nationality)
									+ "</nationalityName>")
							.append("<indGender>" + gender + "</indGender>")
							.append("</sdQuery>");
					log.debug("TRSD Request CustomerPersonalDtls==> " + trsdReq);
					insertTRSDXmlAudit(winame, trsdReq.toString(), "Request",
							clientID + "", "S");
					if(requestClassification.equalsIgnoreCase("CARD")){
						saveTRSDDetails(leadRefNumber, winame, batchId,
							channelName, clientID, name, dob, nationality,
							gender, "INDIVIDUAL", "CARD");
					}
					else {
						saveTRSDDetails(leadRefNumber, winame, batchId,
								channelName, clientID, name, dob, nationality,
								gender, "INDIVIDUAL", "AUS");
					} 
					try {
						response = executeRestAPI(trsdURL, trsdReq.toString());
					} catch (Exception e) {
						trsdSuccess = false;
						log.debug("Error in getTRSDResponse() EntityControlPersonalDtls: "
								+ e.getMessage());
						e.printStackTrace();
					}
					log.debug("TRSD response EntityControlPersonalDtls==> "
							+ response);
					caseFound = updateTRSDDetail(response, winame, trsdSuccess,
							clientID + "");
					log.debug("$$" + caseFound[1] + "--" + caseFound[0]
							+ "3531");
					if (caseFound[1] == true) {
						log.debug("///3534");
						trsdCaseError = true;

					} else if (caseFound[0] == true) {
						trsdCaseFound = true;
					}
				}
			}

			if (KeyContactInfo1 != null) {
				for (int counter = 0; counter < KeyContactInfo1.length; counter++) {
					name = checkReqData(KeyContactInfo1[counter].getName() == null ? ""
							: KeyContactInfo1[counter].getName().trim());
					dob = checkReqData(KeyContactInfo1[counter].getDOB() == null ? ""
							: KeyContactInfo1[counter].getDOB().trim());
					nationality = checkReqData(KeyContactInfo1[counter]
							.getNationality() == null ? ""
							: KeyContactInfo1[counter].getNationality().trim());
					gender = "NA";
					clientID = getRefNum();
					trsdReq.setLength(0);
					trsdReq.append(
							"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
							.append("<sdQuery>")
							.append("<types>")
							.append("<type>INDIVIDUAL</type>")
							.append("</types>")
							.append("<createCaseOnMatches>CREATECASE</createCaseOnMatches>")
							.append("<field1>" + channelName + "</field1>")
							.append("<clientID>" + clientID + "</clientID>")
							.append("<field2>" + winame + "</field2>")
							.append("<indDob>" + dob + "</indDob>")
							.append("<name>" + normalizeString(name)
									+ "</name>")
							.append("<nationalityName>"
									+ normalizeString(nationality)
									+ "</nationalityName>")
							.append("</sdQuery>");
					log.debug("TRSD Request KeyContactInfo1==> " + trsdReq);
					insertTRSDXmlAudit(winame, trsdReq.toString(), "Request",
							clientID + "", "S");
					saveTRSDDetails(leadRefNumber, winame, batchId,
							channelName, clientID, name, dob, nationality,
							gender, "INDIVIDUAL", "SENIOR MANAGEMENT");
					try {
						response = executeRestAPI(trsdURL, trsdReq.toString());
					} catch (Exception e) {
						trsdSuccess = false;
						log.debug("Error in getTRSDResponse() EntityControlPersonalDtls: "
								+ e.getMessage());
						e.printStackTrace();
					}
					log.debug("TRSD response KeyContactInfo1==> " + response);
					caseFound = updateTRSDDetail(response, winame, trsdSuccess,
							clientID + "");
					log.debug("$$" + caseFound[1] + "--" + caseFound[0]
							+ "3577");
					if (caseFound[1] == true) {
						log.debug("///3580");
						trsdCaseError = true;

					} else if (caseFound[0] == true) {
						trsdCaseFound = true;
					}
				}
			}

			if (entityControlPersonsDtls != null) {
				for (EntityControlPersonsDtls ecp : entityControlPersonsDtls) {
					name = checkReqData(ecp.getControlPersonFirstName() == null ? ""
							: ecp.getControlPersonFirstName().trim())
							+ " "
							+ checkReqData(ecp.getControlPersonLastName() == null ? ""
									: ecp.getControlPersonLastName().trim());
					dob = checkReqData(ecp.getControlPersonDateOfBirth() == null ? ""
							: ecp.getControlPersonDateOfBirth().trim());
					nationality = checkReqData(ecp
							.getControlPersonBirthCountry() == null ? "" : ecp
							.getControlPersonBirthCountry().trim());

					gender = "NA";
					clientID = getRefNum();
					trsdReq.setLength(0);
					trsdReq.append(
							"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
							.append("<sdQuery>")
							.append("<types>")
							.append("<type>INDIVIDUAL</type>")
							.append("</types>")
							.append("<createCaseOnMatches>CREATECASE</createCaseOnMatches>")
							.append("<field1>" + channelName + "</field1>")
							.append("<clientID>" + clientID + "</clientID>")
							.append("<field2>" + winame + "</field2>")
							.append("<indDob>" + dob + "</indDob>")
							.append("<name>" + normalizeString(name)
									+ "</name>")
							.append("<nationalityName>"
									+ normalizeString(nationality)
									+ "</nationalityName>")
							.append("</sdQuery>");
					log.debug("TRSD Request EntityControlPersonalDtls==> "
							+ trsdReq);
					insertTRSDXmlAudit(winame, trsdReq.toString(), "Request",
							clientID + "", "S");
					saveTRSDDetails(leadRefNumber, winame, batchId,
							channelName, clientID, name, dob, nationality,
							gender, "INDIVIDUAL", "CONTROLLING PERSON");
					try {
						response = executeRestAPI(trsdURL, trsdReq.toString());
					} catch (Exception e) {
						trsdSuccess = false;
						log.debug("Error in getTRSDResponse() EntityControlPersonalDtls: "
								+ e.getMessage());
						e.printStackTrace();
					}
					log.debug("TRSD response EntityControlPersonalDtls==> "
							+ response);
					caseFound = updateTRSDDetail(response, winame, trsdSuccess,
							clientID + "");
					log.debug("$$" + caseFound[1] + "--" + caseFound[0]
							+ "3624");
					if (caseFound[1] == true) {
						log.debug("///3627");
						trsdCaseError = true;

					} else if (caseFound[0] == true) {
						trsdCaseFound = true;
					}
				}
			}

			if (affiliatedEntityInfos != null) {
				for (AffiliatedEntityInfo aff : affiliatedEntityInfos) {
					name = checkReqData(aff.getName() == null ? "" : aff
							.getName().trim());
					nationality = checkReqData(aff.getIncorpPlace() == null ? ""
							: aff.getIncorpPlace().trim());
					dob = "NA";
					gender = "NA";
					clientID = getRefNum();
					trsdReq.setLength(0);
					trsdReq.append(
							"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
							.append("<sdQuery>")
							.append("<types>")
							.append("<type>ORGANISATION</type>")
							.append("</types>")
							.append("<createCaseOnMatches>CREATECASE</createCaseOnMatches>")
							.append("<field1>" + channelName + "</field1>")
							.append("<clientID>" + clientID + "</clientID>")
							.append("<field2>" + winame + "</field2>")
							.append("<name>" + normalizeString(name)
									+ "</name>")
							.append("<nationalityName>"
									+ normalizeString(checkReqData(aff
											.getIncorpPlace() == null ? ""
											: aff.getIncorpPlace().trim()))
									+ "</nationalityName>")
							.append("</sdQuery>");
					log.debug("TRSD Request AffiliatedEntityInfo ==> "
							+ trsdReq);
					insertTRSDXmlAudit(winame, trsdReq.toString(), "Request",
							clientID + "", "S");
					saveTRSDDetails(leadRefNumber, winame, batchId,
							channelName, clientID, name, dob, nationality,
							gender, "ORGANISATION", "AFFILIATE");
					try {
						response = executeRestAPI(trsdURL, trsdReq.toString());
					} catch (Exception e) {
						trsdSuccess = false;
						log.debug("Error in getTRSDResponse() AffiliatedEntityInfo: "
								+ e.getMessage());
						e.printStackTrace();
					}
					log.debug("TRSD response AffiliatedEntityInfo==> "
							+ response);
					caseFound = updateTRSDDetail(response, winame, trsdSuccess,
							clientID + "");
					log.debug("$$" + caseFound[1] + "--" + caseFound[0]
							+ "3669");
					if (caseFound[1] == true) {
						log.debug("///3672");
						trsdCaseError = true;

					} else if (caseFound[0] == true) {
						trsdCaseFound = true;
					}

				}
			}

			if (subsidiariesDetails != null) {
				for (SubsidiariesDetails ssd : subsidiariesDetails) {
					name = checkReqData(ssd.getName() == null ? "" : ssd
							.getName().trim());
					nationality = checkReqData(ssd.getIncorpPlace() == null ? ""
							: ssd.getIncorpPlace().trim());
					dob = "NA";
					gender = "NA";
					clientID = getRefNum();
					trsdReq.setLength(0);
					trsdReq.append(
							"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
							.append("<sdQuery>")
							.append("<types>")
							.append("<type>ORGANISATION</type>")
							.append("</types>")
							.append("<createCaseOnMatches>CREATECASE</createCaseOnMatches>")
							.append("<field1>" + channelName + "</field1>")
							.append("<clientID>" + clientID + "</clientID>")
							.append("<field2>" + winame + "</field2>")
							.append("<name>" + normalizeString(name)
									+ "</name>")
							.append("<nationalityName>"
									+ normalizeString(nationality)
									+ "</nationalityName>")
							.append("</sdQuery>");
					log.debug("TRSD Request SubsidiariesDetails==> " + trsdReq);
					insertTRSDXmlAudit(winame, trsdReq.toString(), "Request",
							clientID + "", "S");
					saveTRSDDetails(leadRefNumber, winame, batchId,
							channelName, clientID, name, dob, nationality,
							gender, "ORGANISATION", "SUBSIDIARY");
					try {
						response = executeRestAPI(trsdURL, trsdReq.toString());
					} catch (Exception e) {
						trsdSuccess = false;
						log.debug("Error in getTRSDResponse() SubsidiariesDetails: "
								+ e.getMessage());
						e.printStackTrace();
					}
					log.debug("TRSD response SubsidiariesDetails==> "
							+ response);
					caseFound = updateTRSDDetail(response, winame, trsdSuccess,
							clientID + "");
					log.debug("$$" + caseFound[1] + "--" + caseFound[0]
							+ "3715");
					if (caseFound[1] == true) {
						log.debug("///3718");
						trsdCaseError = true;

					} else if (caseFound[0] == true) {
						trsdCaseFound = true;
					}
				}
			}

			if (majorSuppliersDtls != null) {
				for (MajorSuppliersDtls majSup : majorSuppliersDtls) {
					name = checkReqData(majSup.getName() == null ? "" : majSup
							.getName().trim());
					nationality = checkReqData(majSup.getIncorpPlace() == null ? ""
							: majSup.getIncorpPlace().trim());
					dob = "NA";
					gender = "NA";
					clientID = getRefNum();
					trsdReq.setLength(0);
					trsdReq.append(
							"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
							.append("<sdQuery>")
							.append("<types>")
							.append("<type>ORGANISATION</type>")
							.append("</types>")
							.append("<createCaseOnMatches>CREATECASE</createCaseOnMatches>")
							.append("<field1>" + channelName + "</field1>")
							.append("<clientID>" + clientID + "</clientID>")
							.append("<field2>" + winame + "</field2>")
							.append("<name>" + normalizeString(name)
									+ "</name>")
							.append("<nationalityName>"
									+ normalizeString(nationality)
									+ "</nationalityName>")
							.append("</sdQuery>");
					log.debug("TRSD Request MajorSuppliersDtls==> " + trsdReq);
					insertTRSDXmlAudit(winame, trsdReq.toString(), "Request",
							clientID + "", "S");
					saveTRSDDetails(leadRefNumber, winame, batchId,
							channelName, clientID, name, dob, nationality,
							gender, "ORGANISATION", "MAJOR SUPPLIER");
					try {
						response = executeRestAPI(trsdURL, trsdReq.toString());
					} catch (Exception e) {
						trsdSuccess = false;
						log.debug("Error in getTRSDResponse() MajorSuppliersDtls: "
								+ e.getMessage());
						e.printStackTrace();
					}
					log.debug("TRSD response MajorSuppliersDtls==> " + response);
					caseFound = updateTRSDDetail(response, winame, trsdSuccess,
							clientID + "");
					log.debug("$$" + caseFound[1] + "--" + caseFound[0]
							+ "3760");
					if (caseFound[1] == true) {
						log.debug("///3763");
						trsdCaseError = true;
					} else if (caseFound[0] == true) {
						trsdCaseFound = true;
					}

				}
			}

			if (majorClientsDtls != null) {
				for (MajorClientsDtls majCli : majorClientsDtls) {
					name = checkReqData(majCli.getName() == null ? "" : majCli
							.getName().trim());
					nationality = checkReqData(majCli.getIncorpPlace() == null ? ""
							: majCli.getIncorpPlace().trim());
					dob = "NA";
					gender = "NA";
					clientID = getRefNum();
					trsdReq.setLength(0);
					trsdReq.append(
							"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
							.append("<sdQuery>")
							.append("<types>")
							.append("<type>ORGANISATION</type>")
							.append("</types>")
							.append("<createCaseOnMatches>CREATECASE</createCaseOnMatches>")
							.append("<field1>" + channelName + "</field1>")
							.append("<clientID>" + clientID + "</clientID>")
							.append("<field2>" + winame + "</field2>")
							.append("<name>" + normalizeString(name)
									+ "</name>")
							.append("<nationalityName>"
									+ normalizeString(nationality)
									+ "</nationalityName>")
							.append("</sdQuery>");
					log.debug("TRSD Request ==> majorClientsDtls" + trsdReq);
					insertTRSDXmlAudit(winame, trsdReq.toString(), "Request",
							clientID + "", "S");
					saveTRSDDetails(leadRefNumber, winame, batchId,
							channelName, clientID, name, dob, nationality,
							gender, "ORGANISATION", "MAJOR CLIENT");
					try {
						response = executeRestAPI(trsdURL, trsdReq.toString());
					} catch (Exception e) {
						trsdSuccess = false;
						log.debug("Error in getTRSDResponse() majorClientsDtls: "
								+ e.getMessage());
						e.printStackTrace();
					}
					log.debug("TRSD response majorClientsDtls==> " + response);
					caseFound = updateTRSDDetail(response, winame, trsdSuccess,
							clientID + "");
					log.debug("$$" + caseFound[1] + "--" + caseFound[0]
							+ "3805");
					if (caseFound[1] == true) {
						log.debug("///3805");
						trsdCaseError = true;

					} else if (caseFound[0] == true) {
						trsdCaseFound = true;
					}
				}
			}
			// trsdReq.append("</queries>")
			// .append("</sdQueries>");

			// insertTRSDXmlAudit(winame, trsdReq.toString(), "Request");

			// try {
			// //response =
			// executeRestAPI("http://10.101.107.15:8080/transwatchwebapp/webresources/sdqueryservice/searches",
			// trsdReq.toString());
			// response = executeRestAPI(trsdURL, trsdReq.toString());
			// }catch(Exception e){
			// trsdSuccess = false;
			// log.debug("Error in getTRSDResponse(): " + e.getMessage());
			// e.printStackTrace();
			// }
			// log.debug("TRSD response ==> " + response);
			// boolean[] caseFound = updateTRSDDetails(response, winame);

			log.debug("///trsdSuccess" + trsdSuccess);
			log.debug("///trsdCaseError" + trsdCaseError);
			log.debug("///trsdCaseFound" + trsdCaseFound);
			if (trsdSuccess) {
				if (!trsdCaseError && !trsdCaseFound) {
					WMGetWorkItem(winame, 1);
					WFSetAttributes(winame, 1, "N","");
					addTRSDAudit(winame, leadRefNumber, "SUCCESS");
				} else if (trsdCaseError) {
					WMGetWorkItem(winame, 1);
					WFSetAttributes(winame, 1, "P","");
					addTRSDAudit(winame, leadRefNumber,
							"TRSD PENDING FOR RETRY");
				} else if (trsdCaseFound) {
					checkTrsdResponse(winame);
					addTRSDAudit(winame, leadRefNumber, "MATCH FOUND");
				} else {
					WMGetWorkItem(winame, 1);
					WFSetAttributes(winame, 1, "P","");
					addTRSDAudit(winame, leadRefNumber, "FAILURE");
				}
				// updateTRSDXml(response,winame);
				// saveTRSDResponse(response,winame);
				// WFSetAttributes(winame, 1, "N");
				WFSetAttributes(winame, 1, "","Y");
//				WMCompleteWorkItem(winame, 1);
			} else {
				WMGetWorkItem(winame, 1);
				WFSetAttributes(winame, 1, "P","");
				addTRSDAudit(winame, leadRefNumber, "TRSD PENDING FOR RETRY");
				WFSetAttributes(winame, 1, "","Y");
//				WMCompleteWorkItem(winame, 1);
			}
				//insert data into bpmevent gen
				String sTableBPM = "BPM_EVENTGEN_TXN_DATA";
				String sColumnBPM = "insertiondatetime, wi_name, expiry_date,status_flag,PROCESS_NAME,SOURCING_CHANNEL, REQUESTMODE";
				String sValuesBPM = "systimestamp,'"+winame+"',(SYSDATE+(5/(24*60))) ,'N','WBG','SANCTION_SCREENING_VALIDATION','C'";
				sInputXML = "<?xml version=\"1.0\"?><APInsert_Input><Option>APInsert</Option><TableName>"
						+ sTableBPM
						+ "</TableName>"
						+ "<ColName>"
						+ sColumnBPM
						+ "</ColName>"
						+ "<Values>"
						+ sValuesBPM
						+ "</Values>"
						+ "<EngineName>"
						+ sCabinetName
						+ "</EngineName>"
						+ "<SessionId>"
						+ sUserDBId
						+ "</SessionId>"
						+ "</APInsert_Input>";
				log.info("insert sInputXML trsd xml " + sInputXML);

				sOutputXML = executeAPI(sInputXML);
				log.info("insert trsd xml" + sOutputXML);
			// executeRestMultipartAPI("","");
			// XMLParser xp = new XMLParser(response);
			return response;
		} catch (Throwable ex) {
			log.debug("Error in getTRSDResponse(): " + ex.getMessage());
			ex.printStackTrace();
		}

		return "";
	}

	private long getRefNum() {
		try {
			String inputXml = getDBSelectInput(
					"SELECT SEQ_WBG_TRSD.NEXTVAL AS SYSREFNO FROM DUAL",
					sCabinetName, sUserDBId);
			String outputXml = executeAPI(inputXml);
			XMLParser xp = new XMLParser(outputXml);
			long sysNum = Long.parseLong(xp.getValueOf("SYSREFNO"));
			// refNumber = Integer.parseInt(sysNum);
			return sysNum;
		} catch (Exception ex) {
			log.debug("Error in getRefNum(): " + ex.getMessage());
		}
		return 0l;
	}

	public String WMGetWorkItem(String ProcessInstanceId, int WorkitemId) {
		try {
			StringBuilder sInputXML = new StringBuilder();
			sInputXML
					.append("<?xml version=\"1.0\"?>")
					.append("\n")
					.append("<WMGetWorkItem_Input>")
					.append("\n")
					.append("<Option>WMGetWorkItem</Option>")
					.append("\n")
					.append("<SessionId>" + sUserDBId + "</SessionId>")
					.append("\n")
					.append("<EngineName>" + sCabinetName + "</EngineName>")
					.append("\n")
					.append("<ProcessInstanceId>" + ProcessInstanceId
							+ "</ProcessInstanceId>").append("\n")
					.append("<WorkitemId>" + WorkitemId + "</WorkitemId>")
					.append("\n").append("</WMGetWorkItem_Input>");
			String outputXML = executeAPI(sInputXML.toString());
			log.debug("WMGetWorkItem OutputXML ===>" + outputXML);
			int mainCode = Integer.parseInt(new XMLParser(outputXML)
					.getValueOf("MainCode"));

			return outputXML;
		} catch (Exception ex) {
			log.debug("Error in WMGetWorkItem(): " + ex.getMessage());
		}
		return "";
	}

	public String WMCompleteWorkItem(String ProcessInstanceId, int WorkitemId) {
		try {
			StringBuilder sInputXML = new StringBuilder();
			sInputXML
					.append("<?xml version=\"1.0\"?>")
					.append("\n")
					.append("<WMCompleteWorkItem_Input>")
					.append("\n")
					.append("<Option>WMCompleteWorkItem</Option>")
					.append("\n")
					.append("<SessionId>" + sUserDBId + "</SessionId>")
					.append("\n")
					.append("<EngineName>" + sCabinetName + "</EngineName>")
					.append("\n")
					.append("<ProcessInstanceId>" + ProcessInstanceId
							+ "</ProcessInstanceId>").append("\n")
					.append("<WorkitemId>" + WorkitemId + "</WorkitemId>")
					.append("\n").append("</WMCompleteWorkItem_Input>");
			// Lock Workitem
			// WMGetWorkItem(ProcessInstanceId, WorkitemId);
			String outputXML = executeAPI(sInputXML.toString());
			log.debug("WMCompleteWorkItem OutputXML ===>" + outputXML);
			return outputXML;
		} catch (Exception ex) {
			log.debug("Error in WMCompleteWorkItem(): " + ex.getMessage());
		}
		return "";
	}
	
	
	public String WFSetAttributes(String processInstanceid, int WorkitemId,
			String trsdFlag,String completeFlag) {
		String tag = "";
		String val = "";
		try {
			if(!"".equalsIgnoreCase(trsdFlag) && null != trsdFlag){
				tag = "TRSD_FLAG";
				val = trsdFlag;
			} else if(!"".equalsIgnoreCase(completeFlag) && null != completeFlag){
				tag = "IS_TRSD_COMPLETE";
				val = completeFlag;
			}

			StringBuilder sInputXML = new StringBuilder();
			sInputXML
			.append("<?xml version=\"1.0\"?>")
			.append("\n")
			.append("<WMAssignWorkItemAttributes_Input>")
			.append("\n")
			.append("<Option>WMAssignWorkItemAttributes</Option>")
			.append("\n")
			.append("<SessionId>" + sUserDBId + "</SessionId>")
			.append("\n")
			.append("<EngineName>" + sCabinetName + "</EngineName>")
			.append("\n")
			.append("<ProcessDefId>" + sProcessDefId
					+ "</ProcessDefId>")
					.append("\n")
					.append("<ProcessInstanceId>" + processInstanceid
							+ "</ProcessInstanceId>")
							.append("\n")
							.append("<WorkitemId>" + WorkitemId + "</WorkitemId>")
							.append("\n")
							.append("<UserDefVarFlag>Y</UserDefVarFlag>")
							.append("\n")
							.append("<ReminderFlag>N</ReminderFlag>")
							.append("\n")
							.append("<Attributes><"+tag+">" + val
									+ "</"+tag+"></Attributes>").append("\n")
									.append("</WMAssignWorkItemAttributes_Input>");
			// Lock Workitem
			WMGetWorkItem(processInstanceid, WorkitemId);
			String outputXML = executeAPI(sInputXML.toString());
			log.debug("WFSetAttributes OutputXML ===>" + outputXML);
			return outputXML;
		} catch (Exception ex) {
			log.debug("Error in WFSetAttributes(): " + ex.getMessage());
		}
		return "";
	}


	public void insertTRSDXmlAudit(String wi_name, String xml,
			String audit_type, String trsd_client_id, String trsd_status) {

		try {
			log.debug("inside insertTRSDXml");
			String sTable = "USR_0_WBG_AO_TRSD_AUDIT";
			String sColumn = "WI_NAME,TRSD_AUDIT_TIME,TRSD_AUDIT_TYPE,TRSD_AUDIT_XML,TRSD_CLIENT_ID,TRSD_STATUS";
			xml = xml.replace("'", "''");
			xml = createNormalizedXML(xml);
			if (audit_type.equalsIgnoreCase("Request")) {
				sValues = "'" + wi_name + "',systimestamp,'REQ'," + xml + ",'"
						+ trsd_client_id + "','S'";
			} else if (audit_type.equalsIgnoreCase("Response")) {
				sValues = "'" + wi_name + "',systimestamp,'RES'," + xml + ",'"
						+ trsd_client_id + "','" + trsd_status + "'";
			}
			sInputXML = "<?xml version=\"1.0\"?><APInsert_Input><Option>APInsert</Option><TableName>"
					+ sTable
					+ "</TableName>"
					+ "<ColName>"
					+ sColumn
					+ "</ColName>"
					+ "<Values>"
					+ sValues
					+ "</Values>"
					+ "<EngineName>"
					+ sCabinetName
					+ "</EngineName>"
					+ "<SessionId>"
					+ sUserDBId
					+ "</SessionId>"
					+ "</APInsert_Input>";
			// log.debug("insert sInputXML trsd xml " + sInputXML);

			sOutputXML = executeAPI(sInputXML);

			// log.debug("insert trsd xml" + sOutputXML);

		} catch (Exception e) {
			log.debug("Error in insertTRSDXml(): " + e.getMessage());

		}

	}

	public void saveTRSDDetails(String leadRefNo, String winame, long batch_id,
			String channelName, long ClientID, String name, String DOB,
			String nationality, String gender, String type, String entityType) {
		try {
			log.debug("inside saveTRSDResponse");
			String tableName = "USR_0_WBG_AO_TRSD_INFO";
			String columnName = "LEAD_REFNO, WI_NAME, BATCH_ID, TRSD_CHANNEL_NAME, TRSD_STATUS, TRSD_CLIENT_ID, TRSD_NAME, TRSD_DOB, TRSD_NATIONALITY, TRSD_GENDER, TRSD_TYPE, TRSD_ENTITY_TYPE, TRSD_SCREENING_DATE";
			name = name.replaceAll("'", "''");
			sValues = "'" + leadRefNo + "','" + winame + "'," + batch_id + ",'"
					+ channelName + "','N'," + ClientID + ",'" + name + "','"
					+ DOB + "','" + nationality + "','" + gender + "','" + type
					+ "','" + entityType + "', SYSDATE";
			sInputXML = "<?xml version=\"1.0\"?><APInsert_Input><Option>APInsert</Option><TableName>"
					+ tableName
					+ "</TableName>"
					+ "<ColName>"
					+ columnName
					+ "</ColName>"
					+ "<Values>"
					+ sValues
					+ "</Values>"
					+ "<EngineName>"
					+ sCabinetName
					+ "</EngineName>"
					+ "<SessionId>"
					+ sUserDBId
					+ "</SessionId>"
					+ "</APInsert_Input>";
			log.debug("insert sInputXML trsd xml " + sInputXML);

			sOutputXML = executeAPI(sInputXML);
			log.debug("insert trsd xml" + sOutputXML);

		} catch (Exception e) {
			log.debug("Error in insertTRSDXml(): " + e.getMessage());

		}

	}

	public void addTRSDAudit(String winame, String leadRefNo, String status) {
		log.debug("INSIDE insertTRSDAuditTrail");
		try {
			log.debug("in USR_0_WBG_AO_DEC_HIST");
			String sTable = "USR_0_WBG_AO_DEC_HIST";
			String sOutputXML = null;
			String sColumn = "WI_NAME,LEAD_REFNO,USERID,USERNAME,PRV_WS_NAME,WS_NAME,WS_DECISION,"
					+ "REJ_REASON,WS_COMMENTS,DEC_DATE_TIME,QUEUE_ENTRY_DATE_TIME";

			String sValues = "'" + winame + "','" + LeadRefNo
					+ "','TRSD_USER','TRSD_USER','','Introduction','" + status
					+ "','','',sysdate,sysdate";

			String sInputXML = "<?xml version=\"1.0\"?><APInsert_Input><Option>APInsert</Option><TableName>"
					+ sTable
					+ "</TableName>"
					+ "<ColName>"
					+ sColumn
					+ "</ColName>"
					+ "<Values>"
					+ sValues
					+ "</Values>"
					+ "<EngineName>"
					+ sCabinetName
					+ "</EngineName>"
					+ "<SessionId>"
					+ sUserDBId
					+ "</SessionId>"
					+ "</APInsert_Input>";

			// log.debug("sInputXML:"+sInputXML);
			sOutputXML = executeAPI(sInputXML);
			// log.debug("sOUTPUTXML:"+sOutputXML);

		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
	}

	public boolean[] updateTRSDDetail(String Response, String winame,
			Boolean trsdSuccess, String clientIDFromReq) {
		boolean[] out = new boolean[2];
		out[0] = false;
		out[1] = false;
		String case_code = "";
		String case_num = "";
		String client_id = "";
		int case_num_found = 0;
		int statusCode = 0;

		String trsd_status = "";
		try {
			log.debug("inside saveTRSDResponse" + Response);

			log.debug("valuee for success" + trsdSuccess);
			if (trsdSuccess && !(null == Response)
					&& !"".equalsIgnoreCase(Response)) {
				XMLParser xmlDataParser = new XMLParser();
				xmlDataParser.setInputXML(Response);

				if (clientIDFromReq.equalsIgnoreCase(xmlDataParser
						.getValueOf("clientID"))) {
					String sOutputXML = null;
					case_code = xmlDataParser.getValueOf("caseCode");
					case_num = xmlDataParser.getValueOf("caseNum");
					client_id = xmlDataParser.getValueOf("clientID");
					statusCode = Integer.parseInt(xmlDataParser
							.getValueOf("statusCode"));
					log.debug("insert case_code case_num" + case_code + "$"
							+ case_num + "$" + statusCode);

					if (statusCode != 0) {
						out[1] = true;
						trsd_status = "E";
						// break;
					} else if (!("".equalsIgnoreCase(case_num) || null == case_num)) {

						trsd_status = "P";
						case_num_found = 1;

					} else {
						trsd_status = "N";
					}

					String sTable_info = "USR_0_WBG_AO_TRSD_INFO";
					String sColumn = "TRSD_CASE_CODE,TRSD_CASE_NUM,TRSD_STATUS";
					String sValues = "'" + case_code + "','" + case_num + "','"
							+ trsd_status + "'";
					String sWhere = "wi_name = N'" + winame
							+ "' and trsd_client_id ='" + client_id + "'";
					String input_info = "<?xml version=\"1.0\"?>"
							+ "<APUpdate_Input><Option>APUpdate</Option>"
							+ "<TableName>" + sTable_info + "</TableName>"
							+ "<ColName>" + sColumn + "</ColName>" + "<Values>"
							+ sValues + "</Values>" + "<WhereClause>" + sWhere
							+ "</WhereClause>" + "<EngineName>" + sCabinetName
							+ "</EngineName>" + "<SessionId>" + sUserDBId
							+ "</SessionId>" + "</APUpdate_Input>";

					log.debug("insert sInputXML " + input_info);

					String sOutputXML_info = executeAPI(input_info);

					log.debug("insert trsd output" + sOutputXML_info);

					insertTRSDXmlAudit(winame, Response, "Response", client_id
							+ "", trsd_status);

				} else {
					out[1] = true;
					trsd_status = "E";
					insertTRSDXmlAudit(winame, Response, "Response", client_id
							+ "", trsd_status);
					String sTable_info = "USR_0_WBG_AO_TRSD_INFO";
					String sColumn = "TRSD_CASE_CODE,TRSD_CASE_NUM,TRSD_STATUS";
					String sValues = "'" + case_code + "','" + case_num + "','"
							+ trsd_status + "'";
					String sWhere = "wi_name = N'" + winame
							+ "' and trsd_client_id ='" + clientIDFromReq + "'";
					String input_info = "<?xml version=\"1.0\"?>"
							+ "<APUpdate_Input><Option>APUpdate</Option>"
							+ "<TableName>" + sTable_info + "</TableName>"
							+ "<ColName>" + sColumn + "</ColName>" + "<Values>"
							+ sValues + "</Values>" + "<WhereClause>" + sWhere
							+ "</WhereClause>" + "<EngineName>" + sCabinetName
							+ "</EngineName>" + "<SessionId>" + sUserDBId
							+ "</SessionId>" + "</APUpdate_Input>";

					log.debug("insert sInputXML " + input_info);

					String sOutputXML_info = executeAPI(input_info);

					log.debug("insert trsd output" + sOutputXML_info);

				}
			} else {
				out[1] = true;
				trsd_status = "E";
				insertTRSDXmlAudit(winame, Response, "Response",
						client_id + "", trsd_status);
				String sTable_info = "USR_0_WBG_AO_TRSD_INFO";
				String sColumn = "TRSD_CASE_CODE,TRSD_CASE_NUM,TRSD_STATUS";
				String sValues = "'" + case_code + "','" + case_num + "','"
						+ trsd_status + "'";
				String sWhere = "wi_name = N'" + winame
						+ "' and trsd_client_id ='" + clientIDFromReq + "'";
				String input_info = "<?xml version=\"1.0\"?>"
						+ "<APUpdate_Input><Option>APUpdate</Option>"
						+ "<TableName>" + sTable_info + "</TableName>"
						+ "<ColName>" + sColumn + "</ColName>" + "<Values>"
						+ sValues + "</Values>" + "<WhereClause>" + sWhere
						+ "</WhereClause>" + "<EngineName>" + sCabinetName
						+ "</EngineName>" + "<SessionId>" + sUserDBId
						+ "</SessionId>" + "</APUpdate_Input>";

				log.debug("insert sInputXML " + input_info);

				String sOutputXML_info = executeAPI(input_info);

				log.debug("insert trsd output" + sOutputXML_info);
			}

			if (out[1]) {
				log.debug("inside status code check");
				// WFSetAttributes(winame, 1, "P");
				return out;
			} else if (case_num_found == 1) {
				log.debug("inside case num");
				// WFSetAttributes(winame, 1, "Y");
				out[0] = true;
				return out;
			}

		} catch (Exception ex) {
			log.debug("Error in saveTRSDResponse(): " + ex.getMessage());
			out[0] = true;
			return out;
		}
		return out;
	}

	public boolean[] updateTRSDDetails(String Response, String winame) {
		boolean[] out = new boolean[2];
		out[0] = false;
		out[1] = false;
		try {
			log.debug("inside saveTRSDResponse" + Response);
			XMLParser xmlDataParser = new XMLParser();
			xmlDataParser.setInputXML(Response);

			String sTable = "USR_0_WBG_AO_TRSD_INFO";
			String sOutputXML = null;

			String case_code;
			String case_num;
			String client_id;
			int case_num_found = 0;
			int statusCode = 0;

			start = xmlDataParser.getStartIndex("resultSets", 0, 0);
			deadend = xmlDataParser.getEndIndex("resultSets", start, 0);
			int count = xmlDataParser.getNoOfFields("sdResultSet", start,
					deadend);
			log.debug("count" + count);
			for (int i = 0; i < count; i++) {
				start = xmlDataParser.getStartIndex("sdResultSet", end, 0);
				end = xmlDataParser.getEndIndex("sdResultSet", start, 0);
				case_code = xmlDataParser.getValueOf("caseCode", start, end);
				case_num = xmlDataParser.getValueOf("caseNum", start, end);
				client_id = xmlDataParser.getValueOf("clientID", start, end);
				statusCode = Integer.parseInt(xmlDataParser.getValueOf(
						"statusCode", start, end));
				log.debug("insert case_code case_num" + case_code + "$"
						+ case_num + "$" + statusCode);

				if (statusCode != 0) {
					out[1] = true;
					// break;
				}

				if (!("".equalsIgnoreCase(case_num) || null == case_num)) {
					String sColumn = "TRSD_CASE_CODE,TRSD_CASE_NUM,TRSD_STATUS";
					String sValues = "'" + case_code + "','" + case_num
							+ "','P'";
					String sWhere = "wi_name = N'" + winame
							+ "' and trsd_client_id ='" + client_id + "'";
					String input_update = "<?xml version=\"1.0\"?>"
							+ "<APUpdate_Input><Option>APUpdate</Option>"
							+ "<TableName>" + sTable + "</TableName>"
							+ "<ColName>" + sColumn + "</ColName>" + "<Values>"
							+ sValues + "</Values>" + "<WhereClause>" + sWhere
							+ "</WhereClause>" + "<EngineName>" + sCabinetName
							+ "</EngineName>" + "<SessionId>" + sUserDBId
							+ "</SessionId>" + "</APUpdate_Input>";

					log.debug("insert sInputXML " + input_update);

					sOutputXML = executeAPI(input_update);

					log.debug("insert trsd output" + sOutputXML);
					case_num_found = 1;
				}

			}
			if (out[1]) {
				log.debug("inside status code check");
				WFSetAttributes(winame, 1, "P","");
				return out;
			} else if (case_num_found == 1) {
				log.debug("inside case num");
				WFSetAttributes(winame, 1, "Y","");
				out[0] = true;
				return out;
			}
		} catch (Exception ex) {
			log.debug("Error in saveTRSDResponse(): " + ex.getMessage());
			out[0] = true;
			return out;
		}

		return out;
	}

	public String createNormalizedXML(String outputXml) {
		try {
			if (outputXml.length() > 4000) {
				int itr = outputXml.length() / 4000;
				int mod = outputXml.length() % 4000;
				if (mod > 0) {
					++itr;
				}
				StringBuilder response = new StringBuilder();
				try {
					for (int i = 0; i < itr; i++) {
						if (i == 0) {
							response.append("TO_NCLOB('"
									+ outputXml.substring(0, 4000) + "')");
						} else if (i < itr - 1) {
							response.append(" || TO_NCLOB('"
									+ outputXml.substring((4000 * i),
											4000 * (i + 1)) + "')");

						} else {
							response.append(" || TO_NCLOB('"
									+ outputXml.substring((4000 * i),
											outputXml.length()) + "') ");

						}
					}
				} catch (Exception e) {
					log.debug("Error in createNormalizedXML(): "
							+ e.getMessage());
				}
				outputXml = response.toString();
				return outputXml;
			} else {
				outputXml = "'" + outputXml + "'";
				return outputXml;
			}

		} catch (Exception ex) {
			log.debug("Error in saveTRSDResponse(): " + ex.getMessage());
		}
		return "";
	}

	private String getBatchNum() {
		try {
			String inputXml = getDBSelectInput(
					"SELECT WBG_TRSD_BATCHID.NEXTVAL AS SYSREFNO FROM DUAL",
					sCabinetName, sUserDBId);
			String outputXml = executeAPI(inputXml);
			XMLParser xp = new XMLParser(outputXml);
			String sysNum = xp.getValueOf("SYSREFNO");
			return sysNum;
		} catch (Exception ex) {
			log.debug("Error in getRefNum(): " + ex.getMessage());
		}
		return "";
	}

	private String normalizeString(String val) {
		return val.replace("&", "&amp;");
	}
	private void checkTrsdResponse(String winame) {
		String trsd_decision = "";
		Boolean batchStatus = true;
		boolean rejectStatus = false;
		boolean returnStatus = false;
		String flag = "";
		try {
			String sQuery = "SELECT WI_NAME, BATCH_ID, TRSD_DECISION, COUNT(1) AS COUNT FROM USR_0_WBG_AO_TRSD_INFO WHERE TRSD_CASE_NUM IS NOT NULL GROUP BY BATCH_ID, WI_NAME, TRSD_DECISION HAVING BATCH_ID = "
							+ "(SELECT BATCH_ID FROM USR_0_WBG_AO_TRSD_INFO WHERE WI_NAME = N'" + winame + "' AND ROWNUM =1)";
			sInputXML = getDBSelectInput(sQuery, sCabinetName, sUserDBId);
			log.debug("sInputXML is "+sInputXML);
			sOutputXML = executeAPI(sInputXML);
			log.debug("sOutputXML is "+sOutputXML);
			xp.setInputXML(sOutputXML);
			int start = xp.getStartIndex("Records", 0, 0);
			int deadEnd = xp.getEndIndex("Records", start, 0);
			int noOfFields = xp.getNoOfFields("Record",start, deadEnd);
			int end = 0;
			if(noOfFields > 0){
				for(int i = 0; i < noOfFields; ++i){
					start = xp.getStartIndex("Record", end, 0);
					end = xp.getEndIndex("Record", start, 0);
					trsd_decision = xp.getValueOf("TRSD_DECISION", start, end);
					log.debug("Inside doActionWBG trsd_decision: "+trsd_decision);
					if(trsd_decision.equalsIgnoreCase("")){
						batchStatus = false;                                
					}  else if(trsd_decision.equalsIgnoreCase("Reject")){
						rejectStatus = true;
					} else if(trsd_decision.equalsIgnoreCase("Return")){
						returnStatus = true;
					}
					log.debug("Inside doActionWBG batchStatus: "+batchStatus);
					log.debug("Inside doActionWBG rejectStatus: "+rejectStatus);
					log.debug("Inside doActionWBG returnStatus: "+returnStatus);
					if(!batchStatus && (rejectStatus || returnStatus)){
						break;
					}
				}
				if(batchStatus){
					if(!rejectStatus && !returnStatus){
						flag = "A";
					} else if(rejectStatus){
						flag = "J";
					} else if(returnStatus){
						flag = "R";
					} 
				} else {
					flag = "Y";
				}
				WMGetWorkItem(winame, 1);
				WFSetAttributes(winame, 1, flag,"");
			}
		} catch (Exception e) {
			log.debug("Error in checkTrsdResponse(): " + e.getMessage());
		}
	}
	
	
	private String generateEvent(String winame){
		log.info("inside generateEvent ");
		sInputXML = getAPWebserviceRequest(winame,leadRefNumber,"ITQAN", "ITQAN", "C");
		log.info("insert inputXML of WBG " + sInputXML);
		try {
			sOutputXML = executeAPI(sInputXML);
			log.info(" sOutputXML of WBG " + sOutputXML);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in generate event: ", e);
		}
		return sOutputXML;
	}
	
	 private String generateEventTrsd(String winame) {
		    log.info("inside generateEvent ");
		    this.sInputXML = getAPWebserviceRequest(winame, this.leadRefNumber, "WBG", "SANCTION_SCREENING", "C");
		    log.info("insert inputXML of WBG " + this.sInputXML);
		    log.info("inputXML of WBG " + this.leadRefNumber);
		    try {
		      this.sOutputXML = executeAPI(this.sInputXML);
		      log.info(" sOutputXML of WBG " + this.sOutputXML);
		    } catch (Exception e) {
		      e.printStackTrace();
		      log.error("Exception in generate event: ", e);
		    } 
		    return this.sOutputXML;
		  }
	
	public String getAPWebserviceRequest(String wiName,String refNo,
			String processName, String sourcingChannel, String mode) {
		StringBuilder sInputXML = new StringBuilder();
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<APWebService_Input>").append("\n")
		.append("<Option>WebService</Option>").append("\n")
		.append("<EngineName>" + sCabinetName + "</EngineName>").append("\n")
		.append("<SessionId>" + sUserDBId + "</SessionId>").append("\n")
		.append("<Calltype>WS-2.0</Calltype>").append("\n")
		.append("<InnerCallType>BPMModify</InnerCallType>").append("\n")			
		.append("<wiNumber>" +wiName + "</wiNumber>").append("\n")
		.append("<REF_NO>" + refNo + "</REF_NO>").append("\n")
		.append("<SENDERID>" + "WMS" + "</SENDERID>").append("\n")
		.append("<mode>"+mode+"</mode>").append("\n")
		.append("<channelName>"+sourcingChannel+"</channelName>").append("\n")           
		.append("<correlationId>"+refNo+"</correlationId>").append("\n")
		.append("<channelRefNumber>"+refNo+"</channelRefNumber>").append("\n")  
		.append("<sysrefno>"+refNo+"</sysrefno>").append("\n")
		.append("<processName>"+processName+"</processName>").append("\n")
		.append("</APWebService_Input>");	
		return sInputXML.toString();
	}
	
	public HashMap<String,ArrayList> getMandatoryDocument(String xmlList){
		log.debug("getMandatoryDocument inside");
		StringBuffer sBuffer = new StringBuffer();
		HashMap<String,ArrayList> custDoc = new HashMap<>();
		try {			
			WFXmlResponse xmlResponse = new WFXmlResponse(xmlList);
			log.debug("getMandatoryDocument inside2");
			ArrayList<String> doccustomername = new ArrayList<String>();
	        String reqCls="";
			WFXmlList lWfXml = xmlResponse.createList("DocumentsDtls", "Documents");
			log.debug("getMandatoryDocument inside3");
			for (CutomerPersonalDtls cust : CutomerPersonalDtls) {
				String name = checkReqData(cust.getCustFullName() == null ? ""
						: cust.getCustFullName().trim());
				String requestClassification = checkReqData(cust
						.getRequestClassification() == null ? "AUS" : cust
						.getRequestClassification().trim());
				if(requestClassification.equalsIgnoreCase("AUS")){
					log.debug("getMandatoryDocument inside AUS");
					continue;
				}
				doccustomername.add(name);
				reqCls = requestClassification;
				custDoc.put(name, new ArrayList<>());
				log.debug("getMandatoryDocument inside 4" +custDoc);
			}
			for (int k = 0; lWfXml.hasMoreElements(true); lWfXml.skip(true), k++) {	
				log.debug("getMandatoryDocument inside 5");
				String name = checkReqData(lWfXml.getVal("CustomerName"));
				log.debug("getMandatoryDocument inside 5 name" +name);
				log.debug("getMandatoryDocument inside 5 doccustomername" +doccustomername);
				if(doccustomername.contains(name) && "CARD".equalsIgnoreCase(reqCls)){
					log.debug("getMandatoryDocument inside 6 ");
					String docName = lWfXml.getVal("DocumentName");
					ArrayList docList = custDoc.get(name);
					if(docList != null){
						log.debug("getMandatoryDocument inside 7");
						docList.add(docName);
						custDoc.put(name, docList);	
						log.debug("getMandatoryDocument inside 7" +custDoc);
					} else {
						log.debug("getMandatoryDocument inside 8");
						docList = new ArrayList<>();
						docList.add(docName);
						custDoc.put(name, docList);
						log.debug("getMandatoryDocument inside 8" +custDoc);
					}
					log.debug("getMandatoryDocument inside 9");
				}
				log.debug("getMandatoryDocument inside 10");
			}
		} catch(Throwable e){
			log.error("Exception", e);
			log.debug("getMandatoryDocument Exception" + e);
		}
		return custDoc;
	}
	// Added by reyaz 29-11-2022
	private void updateLeadLastReturnValue(String wiName) {
	  try {	
		  log.info("inside updateLeadLastReturnValue ");
			String sTable_info = "EXT_WBG_AO";
			String sColumn = "LEAD_LAST_RETURN";
			String inputXml = getDBSelectInput(
					"Select lead_approve_by from (Select userid as lead_approve_by from USR_0_WBG_AO_DEC_HIST WHERE WI_NAME =(select WI_NAME from (Select WI_NAME from  ext_wbg_ao where WI_NAME is not null and sourcing_channel='Itqan' and curr_ws ='Exit' and lead_ref_no = '" + LeadRefNo + "'  order by WI_NAME desc) where rownum =1) and (WS_DECISION='Return to RM' or WS_DECISION='Return') order by dec_date_time asc) where rownum =1",
					sCabinetName, sUserDBId);
			log.info(" Value updateLeadLastReturnValue :" +inputXml);
			String outputXml = executeAPI(inputXml);
			log.info(" Value updateLeadLastReturnValue :" +outputXml);
			XMLParser xp = new XMLParser(outputXml);
			String sValues ="'" + xp.getValueOf("lead_approve_by") + "'";
			log.info(" Value updateLeadLastReturnValue :" +sValues);
			String sWhere = "wi_name = N'" + wiName + "'";
			String input_info = "<?xml version=\"1.0\"?>"
					+ "<APUpdate_Input><Option>APUpdate</Option>"
					+ "<TableName>" + sTable_info + "</TableName>"
					+ "<ColName>" + sColumn + "</ColName>" + "<Values>"
					+ sValues + "</Values>" + "<WhereClause>" + sWhere
					+ "</WhereClause>" + "<EngineName>" + sCabinetName
					+ "</EngineName>" + "<SessionId>" + sUserDBId
					+ "</SessionId>" + "</APUpdate_Input>";
			
			log.info(" inputXml updateLeadLastReturnValue :" +input_info);
			String sOutputXML_info = executeAPI(input_info);
			log.info("Output updateLeadLastReturnValue" + sOutputXML_info);
		
	    } catch(Exception e){
				log.error("Exception in updateLeadLastReturnValue",e);	
		}
	}
	//Added by Shivanshu ATP-426
		private String fetchKYCRiskDetails(AccountOpening req, int i) {
			try {
				if (req.getCompany() != null && KYC != null) {
					  if (KYC.getRiskInfo() != null) {	
						  if (KYC.getRiskInfo().getRiskDetails() != null) {
							riskDetails = KYC.getRiskInfo().getRiskDetails();
							String tabName = "USR_0_WBG_TMP_KYCRISKDETAILS";

						String colName = "LEAD_REFNO,SNO,SYSREFNO,A1LEGALENTITY,A2ENTITYTYPE,A3INDUSTRY,"
								+ "A4ACCOUNTOPENINGPURPOSE,A5AGEOFBUSINESS,A6COMPLEXOWNERSHIPSTRUCTURE,"
								+ "A7ACCOUNTINOTHERBANKS,A8PEPSTATUS,B1COUNTRYOFINCORPORATION,B2JURISDICTION,"
								+ "B3COUNTRYOFOPERATION,B4NATIONALITY,B5RESIDENCE,C1ONBOARDING,C2PRODUCT,C3CHANNEL";

							int iKyc = 0;
							for (RISKDetails risk : riskDetails) {
								iKyc = iKyc + 1;
								A1LegalEntity = checkReqData(risk.getA1LegalEntity()  == null ? ""
										: risk.getA1LegalEntity());
								A2EntityType = checkReqData(risk.getA2EntityType() == null ? ""
										: risk.getA2EntityType());
								A3Industry = checkReqData(risk.getA3Industry() == null ? ""
										: risk.getA3Industry());
								A4AccountOpeningPurpose = checkReqData(risk.getA4AccountOpeningPurpose() == null ? ""
										: risk.getA4AccountOpeningPurpose());
								A6ComplexOwnershipStructure = checkReqData(risk.getA6ComplexOwnershipStructure() == null ? ""
										: risk.getA6ComplexOwnershipStructure().trim());
								A7AccountinOtherBanks = checkReqData(risk.getA7AccountinOtherBanks() == null ? ""
										: risk.getA7AccountinOtherBanks().trim());
								A8PepStatus = checkReqData(risk.getA8PepStatus() == null ? ""
										: risk.getA8PepStatus().trim());
								B1CountryofIncorporation = checkReqData(risk.getB1CountryofIncorporation() == null ? ""
										: risk.getB1CountryofIncorporation().trim());
								B2Jurisdiction = checkReqData(risk.getB2Jurisdiction() == null ? ""
										: risk.getB2Jurisdiction().trim());
								B3CountryofOperation = checkReqData(risk.getB3CountryofOperation() == null ? ""
										: risk.getB3CountryofOperation().trim());
								B4Nationality = checkReqData(risk.getB4Nationality() == null ? ""
										: risk.getB4Nationality().trim());	
								B5Residence = checkReqData(risk.getB5Residence() == null ? ""
										: risk.getB5Residence().trim());
								C1Onboarding = checkReqData(risk.getC1Onboarding() == null ? ""
										: risk.getC1Onboarding().trim());
								C2Product = checkReqData(risk.getC2Product() == null ? ""
										: risk.getC2Product().trim());
								C3Channel = checkReqData(risk.getC3Channel() == null ? ""
										: risk.getC3Channel().trim());

								sOutputXML = insertKYCRISKDtls(tabName, colName, iKyc, A1LegalEntity,  A2EntityType,  
										A3Industry,  A4AccountOpeningPurpose,  A5AgeofBusiness,  A6ComplexOwnershipStructure,
										A7AccountinOtherBanks,A8PepStatus, B1CountryofIncorporation,  B2Jurisdiction,  B3CountryofOperation,
										B4Nationality,  B5Residence, C1Onboarding,  C2Product,  C3Channel); 
									
									xp.setInputXML(sOutputXML);
									if (!xp.getValueOf("MainCode").trim()
											.equalsIgnoreCase(SUCCESS_STATUS)) {
										return sOutputXML;
									}
							}
						}
					}
				  }
			} catch (Exception E) {
				log.debug("Error in fetchKYCRiskDetails" + E.getMessage());
			}
			return sOutputXML;
		}
		
}
