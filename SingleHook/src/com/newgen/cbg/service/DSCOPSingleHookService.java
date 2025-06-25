package com.newgen.cbg.service;

import java.util.HashMap;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;

import com.newgen.cbg.core.CoreEvent;
import com.newgen.cbg.core.IEventHandler;
import com.newgen.cbg.implementation.SingleUserConnection;
import com.newgen.cbg.logger.DSCOPDBLogMe;
import com.newgen.cbg.logger.DSCOPLogMe;
import com.newgen.cbg.request.ApplicationAttributes;
import com.newgen.cbg.request.BulkEnquiryApplicationAttributes;
import com.newgen.cbg.request.BulkEnquiryAtributes;
import com.newgen.cbg.request.CBGSingleHookRequest;
import com.newgen.cbg.response.BulkEnquiryResponse;
import com.newgen.cbg.response.CBGSingleHookResponse;
import com.newgen.cbg.testjourney.TJ_Event;
import com.newgen.cbg.utils.APCallCreateXML;
import com.newgen.cbg.utils.DSCOPAudit;
import com.newgen.cbg.utils.DSCOPConfigurations;
import com.newgen.cbg.utils.DSCOPUtils;
import com.newgen.omni.jts.cmgr.XMLParser;

@WebService(serviceName="DSCOPSingleHookService")
public class DSCOPSingleHookService
{
	private String SYSREFNO;
	private String MODE;
	private String WI_NAME;
	private String RequestDateTime;
	private String Stage;
	private String SourcingChannel;
	private String SourcingCenter;
	private String DeviceID;
	private String IP;
	private String OSType;
	private String OSVersion;
	private String AppVersion;
	private String DeviceModal;
	private String ApplicationName;
	private String Language;
	private String LeadNumber;
	private String ApplicationJourney;
	private String ApplicationVersion;
	private ApplicationAttributes[] ApplicationAttributes;
	private CBGSingleHookRequest request;
	private String sessionId;
	private String statusFromRequest;

	//  private ResourceBundle pCodes;
	//  private Map<String, String> mReqParamMap = new LinkedHashMap();
	//  private ArrayList<String> aValuesNotAllowed = new ArrayList();
	private CBGSingleHookResponse response = new CBGSingleHookResponse();

	@WebMethod(operationName="SingleHook")
	@HandlerChain(file="Handlers.xml")
	public CBGSingleHookResponse singleHook(CBGSingleHookRequest request)
	{
		try
		{
			this.request = request;
			DSCOPLogMe.getInstance();
			DSCOPDBLogMe.getInstance();
			DSCOPConfigurations.getInstance();
			fetchRequestParameters(request);

			DSCOPAudit cbgaudit = new DSCOPAudit();
			cbgaudit.serviceRequestAuditLog(this.SYSREFNO, this.Stage, this.WI_NAME, this.MODE);
			cbgaudit = null;

			//      loadResourceBundle();
			//      loadValidationConst();

			SingleUserConnection instance = SingleUserConnection.getInstance(DSCOPConfigurations.getInstance().loginCount);
			this.sessionId = instance.getSession(DSCOPConfigurations.getInstance().CabinetName, DSCOPConfigurations.getInstance().UserName, DSCOPConfigurations.getInstance().Password);
			if (isTestJourney()) {
				IEventHandler event = new TJ_Event();
				CBGSingleHookResponse responseObj = event.dispatchEvent(new CoreEvent(request, this.sessionId, this.Stage, this.WI_NAME, this.SYSREFNO, this.Language, this.ApplicationName, this.RequestDateTime, this.SourcingCenter, this.SourcingChannel, this.DeviceID, this.IP, this.OSType, this.OSVersion, this.AppVersion, this.DeviceModal, this.ApplicationJourney, this.ApplicationVersion));
				return responseObj;
			}

			this.response = triggerEvent(this.MODE);
		}
		catch (Exception e)
		{
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}
		//    finally
		//    {
		//      this.mReqParamMap.clear();
		//    }
		return this.response;
	}

	private boolean isTestJourney() {
		boolean flag = false;
		try {
			String outputXML = APCallCreateXML.APSelect("SELECT TJ_FLAG, TJ_WI_NAME, TJ_MOBILE FROM USR_0_CBG_TEST_J_ADMIN_MASTER WHERE SOURCING_CHANNEL = '" + this.SourcingChannel + "' AND TRUNC(SYSDATE) BETWEEN TRUNC(TJ_START_DT) AND TRUNC(TJ_END_DT) ORDER BY ACTION_DATETIME DESC");
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if ((mainCode == 0) && 
					(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0)) {
				String TJ_FLAG = xp.getValueOf("TJ_FLAG");
				String TJ_WI_NAME = xp.getValueOf("TJ_WI_NAME");
				String TJ_MOBILE = xp.getValueOf("TJ_MOBILE");
				HashMap<String, String> attributeMap = DSCOPUtils.getInstance().requestToAttributeMap(this.request.getApplicationAttributes());
				//        String mobileNumber = (this.SourcingChannel.equals("CBGAPP")) ? (String)attributeMap.get("CUSTOMER_MOBILE_NO") : (String)attributeMap.get("CUSTOMER_ID");
				String mobileNumber = (String)attributeMap.get("CUSTOMER_MOBILE_NO");
				String customerID = (String)attributeMap.get("CUSTOMER_ID");
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TJ_MOBILE: " + TJ_MOBILE + ", TJ_WI_NAME: " + TJ_WI_NAME + ", WI_NAME:" + this.WI_NAME + ", mobileNumber: " + mobileNumber);
				if (TJ_WI_NAME.equalsIgnoreCase(this.WI_NAME)) {
					flag = true;
				}
				else if ((TJ_FLAG.equalsIgnoreCase("Yes")) && (TJ_MOBILE.equals(mobileNumber)))
					flag = true;
			}
		}
		catch (Exception e)
		{
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "flag: " + flag);
		return flag;
	}

	//  private boolean validateInputXML()
	//  {
	//    for (Map.Entry mReqParam : this.mReqParamMap.entrySet())
	//    {
	//      if (!(this.aValuesNotAllowed.contains(mReqParam.getValue())))
	//        continue;
	//      this.response.setStatusCode("1");
	//      this.response.setStatusMessage(((String)mReqParam.getKey()) + " " + this.pCodes.getString("-11004"));
	//      return false;
	//    }
	//
	//    if ((!(this.MODE.equalsIgnoreCase("C"))) && (!(this.MODE.equalsIgnoreCase("M"))) && (!(this.MODE.equalsIgnoreCase("E"))) && (!(this.MODE.equalsIgnoreCase("V"))) && 
	//      (!(this.MODE.equalsIgnoreCase("D"))) && (!(this.MODE.equalsIgnoreCase("U"))) && (!(this.MODE.equalsIgnoreCase("X"))))
	//    {
	//      this.response.setStatusCode("1");
	//      this.response.setStatusMessage(this.pCodes.getString("-11021"));
	//      return false;
	//    }
	//    return true;
	//  }

	//  private void loadValidationConst() {
	//    this.aValuesNotAllowed.add(null);
	//    this.aValuesNotAllowed.add("null");
	//    this.aValuesNotAllowed.add("?");
	//    this.aValuesNotAllowed.add("");
	//    this.mReqParamMap.put("MODE", this.MODE);
	//    this.mReqParamMap.put("SYSREFNO", this.SYSREFNO);
	//    this.mReqParamMap.put("RequestDateTime", this.RequestDateTime);
	//    this.mReqParamMap.put("Stage", this.Stage);
	//    this.mReqParamMap.put("SourcingChannel", this.SourcingChannel);
	//    this.mReqParamMap.put("SourcingCenter", this.SourcingCenter);
	//    this.mReqParamMap.put("DeviceID", this.DeviceID);
	//    this.mReqParamMap.put("IP", this.IP);
	//    this.mReqParamMap.put("OSType", this.OSType);
	//    this.mReqParamMap.put("OSVersion", this.OSVersion);
	//    this.mReqParamMap.put("AppVersion", this.AppVersion);
	//    this.mReqParamMap.put("DeviceModal", this.DeviceModal);
	//    this.mReqParamMap.put("ApplicationName", this.ApplicationName);
	//    this.mReqParamMap.put("Language", this.Language);
	//  }

	//  private void loadResourceBundle() {
	//    this.pCodes = PropertyResourceBundle.getBundle("com.newgen.cbg.config.StatusCodes");
	//    if (this.pCodes == null)
	//      DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, "Error in loading status codes");
	//    else
	//      DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Status Codes loaded successfully");
	//  }

	private void fetchRequestParameters(CBGSingleHookRequest req)
	{
		this.SYSREFNO = ((req.getSYSREFNO() == null) ? "" : req.getSYSREFNO().trim());
		this.MODE = ((req.getMODE() == null) ? "" : req.getMODE().trim());
		this.WI_NAME = ((req.getWI_NAME() == null) ? "" : req.getWI_NAME().trim());
		this.RequestDateTime = ((req.getRequestDateTime() == null) ? "" : req.getRequestDateTime().trim());
		this.Stage = ((req.getStage() == null) ? "" : req.getStage().trim());
		this.SourcingChannel = ((req.getSourcingChannel() == null) ? "" : req.getSourcingChannel().trim());
		this.SourcingCenter = ((req.getSourcingCenter() == null) ? "" : req.getSourcingCenter().trim());
		this.DeviceID = ((req.getDeviceID() == null) ? "" : req.getDeviceID().trim());
		this.IP = ((req.getIP() == null) ? "" : req.getIP().trim());
		this.OSType = ((req.getOSType() == null) ? "" : req.getOSType().trim());
		this.OSVersion = ((req.getOSVersion() == null) ? "" : req.getOSVersion().trim());
		this.AppVersion = ((req.getAppVersion() == null) ? "" : req.getAppVersion().trim());
		this.DeviceModal = ((req.getDeviceModal() == null) ? "" : req.getDeviceModal().trim());
		this.ApplicationName = ((req.getApplicationName() == null) ? "" : req.getApplicationName().trim());
		this.Language = ((req.getLanguage() == null) ? "" : req.getLanguage().trim());
		this.LeadNumber = ((req.getLeadNumber() == null) ? "" : req.getLeadNumber().trim());
		this.ApplicationJourney = ((req.getApplicationJourney() == null) ? "" : req.getApplicationJourney().trim());
		this.ApplicationVersion = ((req.getApplicationVersion() == null) ? "" : req.getApplicationVersion().trim());
		this.ApplicationAttributes = req.getApplicationAttributes();
	}

	private <T> T instantiate(String className, Class<T> type)
	{
		try
		{
			return type.cast(Class.forName("com.newgen.cbg.event." + className + "_Event").newInstance());
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}
		return null;
	}

	private CBGSingleHookResponse triggerEvent(String eventName) throws Exception {
		try {
			IEventHandler event = (IEventHandler)instantiate(eventName, IEventHandler.class);
			CBGSingleHookResponse responseObj = event.dispatchEvent(new CoreEvent(this.request, this.sessionId, this.Stage, this.WI_NAME, this.SYSREFNO, this.Language, this.ApplicationName, this.RequestDateTime, this.SourcingCenter, this.SourcingChannel, this.DeviceID, this.IP, this.OSType, this.OSVersion, this.AppVersion, this.DeviceModal, this.ApplicationJourney, this.ApplicationVersion));
			return responseObj;
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}
		return null;
	}

	@WebMethod(operationName="bulkEnquiry")
	@HandlerChain(file="Handlers.xml")
	public BulkEnquiryResponse bulkEnquiry(CBGSingleHookRequest request) {
		BulkEnquiryResponse bulkResponse = new BulkEnquiryResponse();
		String outputXML = "";
		String totalRecordSelect = "";
		try
		{
			DSCOPLogMe.getInstance();
			DSCOPDBLogMe.getInstance();
			DSCOPConfigurations.getInstance();
			fetchRequestParameters(request);

			DSCOPAudit cbgaudit = new DSCOPAudit();
			cbgaudit.serviceRequestAuditLog(this.SYSREFNO, "0", "BulkEnquiry", this.MODE);
			cbgaudit = null;

			SingleUserConnection instance = SingleUserConnection.getInstance(DSCOPConfigurations.getInstance().loginCount);
			this.sessionId = instance.getSession(DSCOPConfigurations.getInstance().CabinetName, DSCOPConfigurations.getInstance().UserName, DSCOPConfigurations.getInstance().Password);
			ApplicationAttributes[] attributes = request.getApplicationAttributes();
			HashMap<String, String> attributeMap = DSCOPUtils.getInstance().requestToAttributeMap(attributes);
			
			String dsa = (String)attributeMap.get("DSA_CODE")!=null?(String)attributeMap.get("DSA_CODE").toUpperCase():"";
			statusFromRequest = (String)attributeMap.get("STATUS")!=null?(String)attributeMap.get("STATUS").toUpperCase():"";

			if(dsa.equalsIgnoreCase("BCA")){
				return bulkEnquiryBCA(request);
			} 
			if(statusFromRequest.equalsIgnoreCase("16") || statusFromRequest.equalsIgnoreCase("17") || 
					statusFromRequest.equalsIgnoreCase("18")){
				return bulkEnquiryAO(request);
			}
			if(request.getSourcingChannel().equalsIgnoreCase("ADCBCOP")){
				return new COPDashBoardService().bulkEnquiry(request);
			}

			String query = "SELECT WI_NAME, CUSTOMER_MOBILE_NO, PASSPORT_FULL_NAME, CUSTOMER_ID, PRODUCT_DESC, STATUS, INITIATION_DATETIME, STAGE_NAME, "
					+ "ADD_COMMTS, CUSTOMER_SEGMENT, REQUESTED_DATETIME FROM "
					+ "(SELECT WI_NAME, CUSTOMER_MOBILE_NO, NVL(PASSPORT_FULL_NAME, UPPER(CUSTOMER_FULL_NAME)) AS PASSPORT_FULL_NAME, CUSTOMER_ID, PRODUCT_DESC, WI_STATUS AS STATUS, STAGE_NAME, ADD_COMMTS, "
					+ "CUSTOMER_SEGMENT, REQUESTED_DATETIME, INITIATION_DATETIME, RANK() OVER (ORDER BY INITIATION_DATETIME DESC) AS BATCH "
					+ "FROM EXT_CBG_CUST_ONBOARDING ";

			String whereClause = "";
			int batchFirst = 1;
			int batchLast = 1;
			String totalRecordQuery = "SELECT COUNT(1) AS TOTALCOUNT FROM EXT_CBG_CUST_ONBOARDING ";
			String totalCountWhere = "";

			if (attributeMap.isEmpty())
			{
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Complete query1 :" + query + " ORDER BY  INITIATION_DATETIME DESC");
				outputXML = APCallCreateXML.APSelect(query + " ORDER BY  INITIATION_DATETIME DESC");
				totalRecordSelect = APCallCreateXML.APSelect(totalRecordQuery + " ORDER BY  INITIATION_DATETIME DESC");
			}
			else
			{
				String toDate = (String)attributeMap.get("TO_DATE");
				String fromDate = (String)attributeMap.get("FROM_DATE");
				int batchSize = Integer.parseInt((((String)attributeMap.get("BATCH_SIZE")).equals("")) ? "0" : (String)attributeMap.get("BATCH_SIZE"));
				int startFrom = Integer.parseInt((((String)attributeMap.get("START_FROM")).equals("")) ? "0" : (String)attributeMap.get("START_FROM"));
				 dsa = (String)attributeMap.get("DSA_CODE")!=null?(String)attributeMap.get("DSA_CODE").toUpperCase():"";
				String status = (String)attributeMap.get("STATUS");
				String orderBy = (String)attributeMap.get("ORDER_BY");
				
				if ((batchSize != 0) && (startFrom > 0))
				{
					batchLast = batchSize + startFrom;
					batchFirst = startFrom;
				}

				if ((batchSize != 0) && (((startFrom == 0)))) {
					batchLast = batchSize;
					batchFirst = 0;
				}

				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Attributes :" + toDate + ", " + fromDate + ", " + batchSize + ", " + startFrom + ", " + dsa + ", " + status + ", " + orderBy);

				if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())) && (!(orderBy.isEmpty())) && (!(status.isEmpty())) && (!(status.equalsIgnoreCase("ALL"))) 
						&& (!(dsa.isEmpty())) && (batchSize != 0) && (startFrom != 0))
				{
					whereClause = "WHERE SOURCING_CHANNEL = 'MYCHOICE' AND TRUNC(INITIATION_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY') AND UPPER(DSA_PROMO_CODE) LIKE '%" + dsa + "' AND WI_STATUS LIKE '%" + status + "'" + 
							"ORDER BY INITIATION_DATETIME " + orderBy + ") WHERE BATCH >= " + batchFirst + " AND BATCH < " + batchLast;
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE CLAUSE 1: " + whereClause);

					totalCountWhere = "WHERE SOURCING_CHANNEL = 'MYCHOICE' AND TRUNC(INITIATION_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY') AND UPPER(DSA_PROMO_CODE) LIKE '%" + dsa + "' AND WI_STATUS LIKE '%" + status + "'" + 
							"ORDER BY INITIATION_DATETIME " + orderBy;
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 1: " + totalCountWhere);
				}
				else if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())) && (!(orderBy.isEmpty())) && (!(status.isEmpty())) && (!(status.equalsIgnoreCase("ALL")))
						&& (!(dsa.isEmpty())) && (batchSize == 0) && (startFrom == 1))
				{
					whereClause = "WHERE SOURCING_CHANNEL = 'MYCHOICE' AND TRUNC(INITIATION_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY') AND UPPER(DSA_PROMO_CODE) LIKE '%" + dsa + "' AND WI_STATUS LIKE '%" + status + "'" + 
							"ORDER BY INITIATION_DATETIME " + orderBy;
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE CLAUSE 2: " + totalCountWhere);

					totalCountWhere = "WHERE SOURCING_CHANNEL = 'MYCHOICE' AND TRUNC(INITIATION_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY') AND UPPER(DSA_PROMO_CODE) LIKE '%" + dsa + "'AND WI_STATUS LIKE '%" + status + "'" +  
							"ORDER BY INITIATION_DATETIME " + orderBy ;
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 2: " + totalCountWhere);
				}
				else if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())) && (!(orderBy.isEmpty())) && (!(status.isEmpty())) && (!(status.equalsIgnoreCase("ALL"))) 
						&& (!(dsa.isEmpty())) && (batchSize != 0) && (startFrom == 0))
				{
					whereClause = "WHERE SOURCING_CHANNEL = 'MYCHOICE' AND TRUNC(INITIATION_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY') AND UPPER(DSA_PROMO_CODE) LIKE '%" + dsa + "' AND WI_STATUS LIKE '%" + status + "'" + 
							"ORDER BY INITIATION_DATETIME " + orderBy + ") WHERE BATCH >= " + batchFirst + " AND BATCH < " + batchLast;
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE CLAUSE 3: " + whereClause);

					totalCountWhere = "WHERE SOURCING_CHANNEL = 'MYCHOICE' AND TRUNC(INITIATION_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') AND TO_DATE('" + toDate + "','DD-MM-YYYY')";
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 3: " + totalCountWhere);
				}
				else if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())) && (!(orderBy.isEmpty())) && (!(status.isEmpty())) && (!(status.equalsIgnoreCase("ALL"))) 
						&& (!(dsa.isEmpty())))
				{
					whereClause = "WHERE SOURCING_CHANNEL = 'MYCHOICE' AND TRUNC(INITIATION_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY') AND UPPER(DSA_PROMO_CODE) LIKE '%" + dsa + "' AND WI_STATUS LIKE '%" + status + "'" +
							"ORDER BY INITIATION_DATETIME " + orderBy + " " ;
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE CLAUSE 4: " + whereClause);

					totalCountWhere = "WHERE SOURCING_CHANNEL = 'MYCHOICE' AND TRUNC(INITIATION_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY') " + 
							"AND WI_STATUS LIKE '%" + status + "' AND UPPER(DSA_PROMO_CODE) LIKE '%" + dsa + "'";
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 4: " + totalCountWhere);
				}
				else if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())) && (!(orderBy.isEmpty())) && (!(status.isEmpty())) && (!(status.equalsIgnoreCase("ALL"))) 
						&& (batchSize != 0) && (startFrom != 0))
				{
					whereClause = "WHERE SOURCING_CHANNEL = 'MYCHOICE' AND TRUNC(INITIATION_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY') AND WI_STATUS LIKE '%" + status + "' ORDER BY INITIATION_DATETIME " + orderBy + ") " + 
							"WHERE BATCH >= " + batchFirst + " AND BATCH < " + batchLast;
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE 5: " + whereClause);

					totalCountWhere = "WHERE SOURCING_CHANNEL = 'MYCHOICE' AND TRUNC(INITIATION_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY')" + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY')";
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 5: " + totalCountWhere);
				}
				else if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())) && (!(status.isEmpty())) && (!(status.equalsIgnoreCase("ALL"))) && (!(dsa.isEmpty())))
				{
					whereClause = "WHERE SOURCING_CHANNEL = 'MYCHOICE' AND TRUNC(INITIATION_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY') AND UPPER(DSA_PROMO_CODE) LIKE '%" + dsa + "' AND WI_STATUS LIKE '%" + status + "')";
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE CLAUSE 6:" + whereClause);

					totalCountWhere = "WHERE SOURCING_CHANNEL = 'MYCHOICE' AND TRUNC(INITIATION_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') AND TO_DATE('" + toDate + "','DD-MM-YYYY') " + 
							"AND WI_STATUS LIKE '%" + status + "' AND UPPER(DSA_PROMO_CODE) LIKE '%" + dsa + "'";
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 6: " + totalCountWhere);
				}
				else if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())) && (!(status.isEmpty())) && (!(status.equalsIgnoreCase("ALL"))))
				{
					whereClause = "WHERE SOURCING_CHANNEL = 'MYCHOICE' AND TRUNC(INITIATION_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY') AND WI_STATUS LIKE '%" + status + "')";
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE CLAUSE 7:" + whereClause);

					totalCountWhere = "WHERE SOURCING_CHANNEL = 'MYCHOICE' AND TRUNC(INITIATION_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY') AND WI_STATUS LIKE '%" + status + "')";
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 7: " + totalCountWhere);
				}
				else if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())) && (!(orderBy.isEmpty())) && (!(dsa.isEmpty())) && (batchSize != 0) && (startFrom != 0))
				{
					whereClause = "WHERE SOURCING_CHANNEL = 'MYCHOICE' AND TRUNC(INITIATION_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY') AND UPPER(DSA_PROMO_CODE) LIKE '%" + dsa + "' ORDER BY " + 
							"INITIATION_DATETIME " + orderBy + ") WHERE BATCH >= " + batchFirst + " AND BATCH < " + batchLast;
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE 8: " + whereClause);

					totalCountWhere = "WHERE SOURCING_CHANNEL = 'MYCHOICE' AND TRUNC(INITIATION_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY') AND UPPER(DSA_PROMO_CODE) LIKE '%" + dsa + "'";
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 8: " + totalCountWhere);
				}
				else if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())) && (!(orderBy.isEmpty())) && (batchSize != 0) && (startFrom != 0))
				{
					whereClause = "WHERE SOURCING_CHANNEL = 'MYCHOICE' AND TRUNC(INITIATION_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY') ORDER BY INITIATION_DATETIME " + orderBy + ") WHERE BATCH >= " + batchFirst + " " + 
							"AND BATCH < " + batchLast;
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE 9: " + whereClause);

					totalCountWhere = "WHERE SOURCING_CHANNEL = 'MYCHOICE' AND TRUNC(INITIATION_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY')";
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 9: " + totalCountWhere);
				}
				else if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())) && (!(orderBy.isEmpty())) && (batchSize != 0) && (startFrom == 0))
				{
					whereClause = "WHERE SOURCING_CHANNEL = 'MYCHOICE' AND TRUNC(INITIATION_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY') ORDER BY INITIATION_DATETIME " + orderBy + ") WHERE BATCH >= " + batchFirst + " " + 
							"AND BATCH < " + batchLast;
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE 10: " + whereClause); 

					totalCountWhere = "WHERE SOURCING_CHANNEL = 'MYCHOICE' AND TRUNC(INITIATION_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY')";
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 10: " + totalCountWhere);
				}
				else if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())) && (!(dsa.isEmpty())))
				{
					whereClause = "WHERE SOURCING_CHANNEL = 'MYCHOICE' AND TRUNC(INITIATION_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY') AND UPPER(DSA_PROMO_CODE) LIKE '%" + dsa + "') ";
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE CLAUSE 11:" + whereClause);

					totalCountWhere = "WHERE SOURCING_CHANNEL = 'MYCHOICE' AND TRUNC(INITIATION_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') AND TO_DATE('" + toDate + "','DD-MM-YYYY') " + 
							"AND UPPER(DSA_PROMO_CODE) LIKE '%" + dsa + "'";
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 11: " + totalCountWhere);
				}
				else if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())) && (!(orderBy.isEmpty())))
				{
					whereClause = "WHERE SOURCING_CHANNEL = 'MYCHOICE' AND TRUNC(INITIATION_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY') ORDER BY INITIATION_DATETIME " + orderBy + ")";
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE CLAUSE 12:" + whereClause);

					totalCountWhere = "WHERE SOURCING_CHANNEL = 'MYCHOICE' AND TRUNC(INITIATION_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY')";
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 12: " + totalCountWhere);
				}
				else if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())))
				{
					whereClause = "WHERE SOURCING_CHANNEL = 'MYCHOICE' AND TRUNC(INITIATION_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY') ORDER BY INITIATION_DATETIME " + orderBy + ")";
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE CLAUSE 13:" + whereClause);

					totalCountWhere = "WHERE SOURCING_CHANNEL = 'MYCHOICE' AND TRUNC(INITIATION_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY')";
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 13: " + totalCountWhere);
				}
				else
				{
					whereClause = "WHERE SOURCING_CHANNEL = 'MYCHOICE')";
					totalCountWhere = "WHERE SOURCING_CHANNEL = 'MYCHOICE'";
				}
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Complete query2 :" + query + whereClause);
				outputXML = APCallCreateXML.APSelect(query + whereClause);
				totalRecordSelect = APCallCreateXML.APSelect(totalRecordQuery + totalCountWhere);
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD Query: " + totalRecordSelect);
			}

			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			XMLParser xp2 = new XMLParser(totalRecordSelect);

			BulkEnquiryAtributes[] bulkAttributes = null;
			if (mainCode == 0) {
				int totalCount = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
				bulkAttributes = new BulkEnquiryAtributes[totalCount];
				if (totalCount > 0) {
					int start = xp.getStartIndex("Records", 0, 0);
					int deadEnd = xp.getEndIndex("Records", start, 0);
					int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
					int end = 0;
					for (int i = 0; i < noOfFields; ++i) {
						start = xp.getStartIndex("Record", end, 0);
						end = xp.getEndIndex("Record", start, 0);
						String wiName = xp.getValueOf("WI_NAME", start, end);
						String mobileNumber = xp.getValueOf("CUSTOMER_MOBILE_NO", start, end);
						String customerName = xp.getValueOf("PASSPORT_FULL_NAME", start, end);
						String customerID = xp.getValueOf("CUSTOMER_ID", start, end);
						String productName = xp.getValueOf("PRODUCT_DESC", start, end);
						String statusCode = xp.getValueOf("STATUS", start, end);
						String stageName = xp.getValueOf("STAGE_NAME", start, end);
						String submitDate = xp.getValueOf("INITIATION_DATETIME", start, end);
						String comments = xp.getValueOf("ADD_COMMTS", start, end);
						String segment = xp.getValueOf("CUSTOMER_SEGMENT", start, end);
						String remarksDateTime = xp.getValueOf("REQUESTED_DATETIME", start, end);

						BulkEnquiryAtributes attr = new BulkEnquiryAtributes();
						attr.setWiName(wiName);
						attr.setMobileNumber(mobileNumber);
						attr.setCustomerName(customerName);
						attr.setCustomerID(customerID);
						attr.setStatus(statusCode);
						attr.setSubmitDate(submitDate);
						attr.setProductName(productName);
						attr.setCurrentWorkStep(stageName);
						attr.setComments(comments);
						attr.setSegment(segment);
						attr.setRemarksDateTime(remarksDateTime);
						/*attr.setIban("AE020090004001079346500");
						attr.setAccountNumber("48766464544");
						attr.setBranchCode("101");
						attr.setBranchName("ABU DHABI MAIN");
						attr.setDashboardType("BCA");*/
						bulkAttributes[i] = attr;
					}
					String totalCountRecords = xp2.getValueOf("TOTALCOUNT");
					bulkResponse.setTOTALCOUNT(totalCountRecords);
					bulkResponse.setFETCHCOUNT(Integer.toString(noOfFields));
					bulkResponse.setStatusCode("0");
					bulkResponse.setStatusMessage("DATA FETCH SUCCESSFULLTY");
				}
				else {
					bulkResponse.setTOTALCOUNT(String.valueOf(0));
					bulkResponse.setFETCHCOUNT(String.valueOf(0));
					bulkResponse.setStatusCode("0");
					bulkResponse.setStatusMessage("NO DATA FOUND");
				}
			}
			BulkEnquiryApplicationAttributes[] attrDetails = new BulkEnquiryApplicationAttributes[1];
			BulkEnquiryApplicationAttributes bulkattr = new BulkEnquiryApplicationAttributes();
			bulkattr.setBulkEnquiryDetails(bulkAttributes);

			attrDetails[0] = bulkattr;
			bulkResponse.setApplicationAttributes(attrDetails);
		}
		catch (Exception e)
		{
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}
		//    finally
		//    {
		//      this.mReqParamMap.clear();
		//    }
		return bulkResponse;
	}
		
	public BulkEnquiryResponse bulkEnquiryBCA(CBGSingleHookRequest request) {
		BulkEnquiryResponse bulkResponse = new BulkEnquiryResponse();
		String outputXML = "";
		String outputXMLTotalCount = "";

		String totalRecordSelect = "";
		try
		{
			DSCOPLogMe.getInstance();
			DSCOPDBLogMe.getInstance();
			DSCOPConfigurations.getInstance();
			fetchRequestParameters(request);

			DSCOPAudit cbgaudit = new DSCOPAudit();
			cbgaudit.serviceRequestAuditLog(this.SYSREFNO, "0", "BulkEnquiryBCA", this.MODE);
			cbgaudit = null;

			SingleUserConnection instance = SingleUserConnection.getInstance(DSCOPConfigurations.getInstance().loginCount);
			this.sessionId = instance.getSession(DSCOPConfigurations.getInstance().CabinetName, DSCOPConfigurations.getInstance().UserName, DSCOPConfigurations.getInstance().Password);

			ApplicationAttributes[] attributes = request.getApplicationAttributes();
			HashMap<String, String> attributeMap = DSCOPUtils.getInstance().requestToAttributeMap(attributes);

			String query = "SELECT C.DECISION,C.PREV_WS,C.REMARKS,C.CURR_WS,C.CUSTID,C.CUSTFULLNAME,C.OFFNUMBER,"
					+ "C.PRODDESC,C.WI_NAME,'N' AS STATUS,C.IBAN_NUMBER,C.ACC_NO,C.HOME_BR_CODE,C.BRNAME,"
					+ "TRUNC(C.WMS_INITDATETIME) AS WMS_INITDATETIME from"
					+ "(SELECT A.DECISION,A.PREV_WS,A.REMARKS,A.CURR_WS,A.CUSTID,A.CUSTFULLNAME,A.OFFNUMBER,"
					+ "B.PRODDESC,A.WI_NAME,'N' AS STATUS,B.IBAN_NUMBER,B.ACC_NO,A.HOME_BR_CODE,A.BRNAME,"
					+ "TRUNC(A.WMS_INITDATETIME) AS WMS_INITDATETIME ,RANK() OVER (ORDER BY WMS_INITDATETIME DESC) "
					+ "AS BATCH FROM EXT_WBG_AO A ,USR_0_WBG_AO_ACC_INFO B  ";

			String whereClause = "";
			String whereTotalClause = "";


			if (attributeMap.isEmpty())
			{
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Complete query1 :" + query + " ORDER BY  WMS_INITDATETIME DESC");
				outputXML = APCallCreateXML.APSelect(query + " WHERE A.WI_NAME = B.WI_NAME AND "
						+ "A.SOURCING_CHANNEL in ('MY_CHOICE') ORDER BY  WMS_INITDATETIME DESC");
			}
			else
			{
				String toDate = (String)attributeMap.get("TO_DATE");
				String fromDate = (String)attributeMap.get("FROM_DATE");
				int batchSize = Integer.parseInt((((String)attributeMap.get("BATCH_SIZE")).equals("")) ? "0" : (String)attributeMap.get("BATCH_SIZE"));
				int startFrom = Integer.parseInt((((String)attributeMap.get("START_FROM")).equals("")) ? "0" : (String)attributeMap.get("START_FROM"));
				String dsa = (String)attributeMap.get("DSA_CODE")!=null?(String)attributeMap.get("DSA_CODE").toUpperCase():"";
				String status = (String)attributeMap.get("STATUS");
				String orderBy = (String)attributeMap.get("ORDER_BY");
				int batchLast = 1;
				int batchFirst = 1;
				if ((batchSize != 0) && (startFrom > 0)) {
					batchLast = batchSize + startFrom;
					batchFirst = startFrom;
				}

				if ((batchSize != 0) && (((startFrom == 0)))) {
					batchLast = batchSize;
					batchFirst = 0;
				}
				if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty()))) {
					whereClause = " WHERE sourcingdsacode = '"+dsa+"' and  A.WI_NAME = B.WI_NAME AND "
							+ "A.SOURCING_CHANNEL in ('MYCHOICE') "
							+ "AND TRUNC(A.WMS_INITDATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY') "
							+ "ORDER BY WMS_INITDATETIME " + orderBy + ") C WHERE BATCH >= " + batchFirst + " AND BATCH < " + batchLast;
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE CLAUSE 1: " + whereClause);


				}
				outputXMLTotalCount = APCallCreateXML.APSelect("select count(1) as totalcount from ext_wbg_ao WHERE SOURCING_CHANNEL in ('MY_CHOICE') ");

				outputXML = APCallCreateXML.APSelect(query + whereClause);
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD Query: " + outputXMLTotalCount);
			}

			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));

			BulkEnquiryAtributes[] bulkAttributes = null;
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD mainCode: " + mainCode);
			if (mainCode == 0) {
				int totalCount = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
				bulkAttributes = new BulkEnquiryAtributes[totalCount];
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD totalCount: " + totalCount);
				if (totalCount > 0) {
					int start = xp.getStartIndex("Records", 0, 0);
					int deadEnd = xp.getEndIndex("Records", start, 0);
					int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
					int end = 0;
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD noOfFields: " + noOfFields);
					for (int i = 0; i < noOfFields; ++i) {
						start = xp.getStartIndex("Record", end, 0);
						end = xp.getEndIndex("Record", start, 0);
						String REMARKS = xp.getValueOf("REMARKS", start, end);
						String CURR_WS = xp.getValueOf("CURR_WS", start, end);
						String CUSTID = xp.getValueOf("CUSTID", start, end);
						String CUSTFULLNAME = xp.getValueOf("CUSTFULLNAME", start, end);
						String wiName = xp.getValueOf("WI_NAME", start, end);
						String PRODDESC = xp.getValueOf("PRODDESC", start, end);
						String IBAN_NUMBER = xp.getValueOf("IBAN_NUMBER", start, end);
						String HOME_BR_CODE = xp.getValueOf("HOME_BR_CODE", start, end);
						String ACC_NO = xp.getValueOf("ACC_NO", start, end);
						String BRNAME = xp.getValueOf("BRNAME", start, end);
						String WMS_INITDATETIME = xp.getValueOf("WMS_INITDATETIME", start, end);
						String STATUS = xp.getValueOf("STATUS", start, end);
						String PREV_WS = xp.getValueOf("PREV_WS", start, end);
						String DECISION = xp.getValueOf("DECISION", start, end);
						if(CURR_WS.equalsIgnoreCase("DSA_MAKER")){
							STATUS = "U";
						} else if(CURR_WS.equalsIgnoreCase("Exit") && !DECISION.equalsIgnoreCase("Approve")){
							STATUS = "E";
						} else if(PREV_WS.equalsIgnoreCase("AWS_BES_Checker2") && !ACC_NO.equalsIgnoreCase("")
								&& DECISION.equalsIgnoreCase("Approve")){
							STATUS = "A";
						} else if (DECISION.equalsIgnoreCase("Reject") || DECISION.equalsIgnoreCase("Refer") ||
						DECISION.equalsIgnoreCase("Send to Conact Center") || DECISION.equalsIgnoreCase("Return") 
						|| DECISION.equalsIgnoreCase("Send To TRSD Screening") || DECISION.equalsIgnoreCase("Refer to Checker1")){
							STATUS = "I";
						}
						
						BulkEnquiryAtributes attr = new BulkEnquiryAtributes();
						attr.setComments(REMARKS);
						attr.setWiName(wiName);
						attr.setCustomerName(CUSTFULLNAME);
						attr.setCustomerID(CUSTID);
						attr.setStatus(STATUS);
						attr.setSubmitDate(WMS_INITDATETIME);
						attr.setProductName(PRODDESC);
						attr.setCurrentWorkStep(CURR_WS);
						/*attr.setIban(IBAN_NUMBER);
						attr.setAccountNumber(ACC_NO);
						attr.setBranchCode(HOME_BR_CODE);
						attr.setBranchName(BRNAME);*/
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD ACC_NO: " + ACC_NO);
						//attr.setDashboardType("BCA");
						bulkAttributes[i] = attr;
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD bulkAttributes: " + bulkAttributes[0]);

					}
					XMLParser xp2 = new XMLParser(outputXMLTotalCount);
					String totalCountRecords = xp2.getValueOf("TOTALCOUNT");
					bulkResponse.setTOTALCOUNT(totalCountRecords);
					bulkResponse.setFETCHCOUNT(Integer.toString(noOfFields));
					bulkResponse.setStatusCode("0");
					bulkResponse.setStatusMessage("DATA FETCH SUCCESSFULLY");
					//bulkResponse.setApplicationName("DIGITAL");  //  on pre production

					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD totalCountRecords: " + totalCountRecords);
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD noOfFields: " + noOfFields);

				}
				else {
					bulkResponse.setTOTALCOUNT(String.valueOf(0));
					bulkResponse.setFETCHCOUNT(String.valueOf(0));
					bulkResponse.setStatusCode("0");
					bulkResponse.setStatusMessage("NO DATA FOUND");
				}
			}
			BulkEnquiryApplicationAttributes[] attrDetails = new BulkEnquiryApplicationAttributes[1];
			BulkEnquiryApplicationAttributes bulkattr = new BulkEnquiryApplicationAttributes();
			bulkattr.setBulkEnquiryDetails(bulkAttributes);

			attrDetails[0] = bulkattr;
			bulkResponse.setApplicationAttributes(attrDetails);
		}
		catch (Exception e)
		{
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}
		//    finally
		//    {
		//      this.mReqParamMap.clear();
		//    }
		return bulkResponse;
	}
	
	public BulkEnquiryResponse bulkEnquiryAO(CBGSingleHookRequest request) {
		BulkEnquiryResponse bulkResponse = new BulkEnquiryResponse();
		String outputXML = "";
		String accRelation = "";
		String accOwnType = "";


		String outputXMLTotalCount = "";
		try
		{
			DSCOPLogMe.getInstance();
			DSCOPDBLogMe.getInstance();
			DSCOPConfigurations.getInstance();
			fetchRequestParameters(request);
			DSCOPAudit cbgaudit = new DSCOPAudit();
			cbgaudit.serviceRequestAuditLog(this.SYSREFNO, "0", "BulkEnquiryAO", this.MODE);
			cbgaudit = null;
			SingleUserConnection instance = SingleUserConnection.getInstance(DSCOPConfigurations.getInstance().loginCount);
			this.sessionId = instance.getSession(DSCOPConfigurations.getInstance().CabinetName, DSCOPConfigurations.getInstance().UserName, DSCOPConfigurations.getInstance().Password);
			ApplicationAttributes[] attributes = request.getApplicationAttributes();
			HashMap<String, String> attributeMap = DSCOPUtils.getInstance().requestToAttributeMap(attributes);
			String dsaCode = (String)attributeMap.get("DSA_CODE")!=null?(String)attributeMap.get("DSA_CODE").toUpperCase():"";
			if(statusFromRequest.equalsIgnoreCase("16")){
				accRelation = "'SOW','Primary'";
				accOwnType = "SINGLE";
				
			} else if(statusFromRequest.equalsIgnoreCase("17")){
				accRelation = "'JAF','Primary'";
				accOwnType = "JOINT";

			} else if(statusFromRequest.equalsIgnoreCase("18")){
				accRelation = "'Guardian'";
				accOwnType = "MINOR";

			} else {
				accRelation = "'SOW','JAF','Guardian'";
			}
			String query = "select PROCESS, NAME,MOBILE,PRODUCT,INITIATEDTIME,WI_NAME,ACC_RELATION FROM "
					+ "(select a.PROCESS, b.NAME,b.MOBILE,a.PRODUCT,a.INITIATEDTIME, a.WI_NAME,b.ACC_RELATION ,"
					+ "RANK() OVER (ORDER BY INITIATEDTIME DESC)"
					+ " AS BATCH from bpm_cop_lead_details a, acc_relation_repeater b , EXT_AO C where "
					+ "a.wi_name=b.wi_name AND C.wi_name=b.wi_name AND a.wi_name=C.wi_name and a.EIDANUMBER=b.eida and"
					+ " b.acc_relation in ("+accRelation+") AND UPPER(STATUS) = UPPER('AssignToCustomer') AND a.EIDANUMBER IS NOT NULL "
					+ "AND B.eida IS NOT NULL AND UPPER(C.ACC_OWN_TYPE) = '"+accOwnType+"' AND UPPER(A.DSA_CODE) = UPPER('"+dsaCode+"')";

			String whereClause = "";
			if (attributeMap.isEmpty()) {
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Complete query1 :" + query + " ORDER BY  INITIATEDTIME DESC)");
				outputXML = APCallCreateXML.APSelect(query + " ORDER BY  a.INITIATEDTIME DESC)");
			} else {
				String toDate = (String)attributeMap.get("TO_DATE");
				String fromDate = (String)attributeMap.get("FROM_DATE");
				int batchSize = Integer.parseInt((((String)attributeMap.get("BATCH_SIZE")).equals("")) ? "0" : (String)attributeMap.get("BATCH_SIZE"));
				int startFrom = Integer.parseInt((((String)attributeMap.get("START_FROM")).equals("")) ? "0" : (String)attributeMap.get("START_FROM"));
				String dsa = (String)attributeMap.get("DSA_CODE")!=null?(String)attributeMap.get("DSA_CODE").toUpperCase():"";
				String status = (String)attributeMap.get("STATUS");
				String orderBy = (String)attributeMap.get("ORDER_BY");
				int batchLast = 1;
				int batchFirst = 1;
				if ((batchSize != 0) && (startFrom > 0)) {
					batchLast = batchSize + startFrom;
					batchFirst = startFrom;
				}

				if ((batchSize != 0) && (((startFrom == 0)))) {
					batchLast = batchSize;
					batchFirst = 0;
				}
				if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty()))) {
					whereClause = " AND TRUNC(A.INITIATEDTIME) BETWEEN TO_DATE('"+fromDate+"','DD-MM-YYYY') AND TO_DATE('"+toDate+"','DD-MM-YYYY') "
							+ "ORDER BY INITIATEDTIME "+orderBy+") D WHERE BATCH >= "+batchFirst+" AND BATCH < "+batchLast;
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE CLAUSE 1: " + whereClause);
				}
				outputXML = APCallCreateXML.APSelect(query + whereClause);
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD Query: " + outputXMLTotalCount);
			}
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			BulkEnquiryAtributes[] bulkAttributes = null;
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD mainCode: " + mainCode);
			if (mainCode == 0) {
				int totalCount = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
				String totalCountRecords = xp.getValueOf("TotalRetrieved");
				bulkAttributes = new BulkEnquiryAtributes[totalCount];
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD totalCount: " + totalCount);
				if (totalCount > 0) {
					int start = xp.getStartIndex("Records", 0, 0);
					int deadEnd = xp.getEndIndex("Records", start, 0);
					int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
					int end = 0;
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD noOfFields: " + noOfFields);
					for (int i = 0; i < noOfFields; ++i) {
						start = xp.getStartIndex("Record", end, 0);
						end = xp.getEndIndex("Record", start, 0);
						String process = xp.getValueOf("PROCESS", start, end);
						String name = xp.getValueOf("NAME", start, end);
						String mobileNumber = xp.getValueOf("MOBILE", start, end);
						String PRODDESC = xp.getValueOf("PRODUCT", start, end);
						String initiatedTime = xp.getValueOf("INITIATEDTIME", start, end);
						String wiName = xp.getValueOf("WI_NAME", start, end);
						String accRel = xp.getValueOf("ACC_RELATION", start, end);
						String STATUS = "";
						if(accRel.equalsIgnoreCase("Guardian")){
							STATUS = "18";
						} else if(accRel.equalsIgnoreCase("JAO") || accRel.equalsIgnoreCase("JOF") || accRel.equalsIgnoreCase("JAF") || accRel.equalsIgnoreCase("JOO")){
							STATUS = "17";
						} else {
							STATUS = "16";
						} 
						
						BulkEnquiryAtributes attr = new BulkEnquiryAtributes();
						attr.setComments("");
						attr.setCurrentWorkStep("UAE Pass Pending");
						attr.setCustomerID("");
						attr.setCustomerName(name);
						attr.setMobileNumber(mobileNumber);
						attr.setProductName(PRODDESC);
						attr.setRemarksDateTime(initiatedTime);
						attr.setSegment("");
						attr.setStatus(STATUS);
						attr.setSubmitDate(initiatedTime);
						attr.setWiName(wiName);
						/*attr.setIban(IBAN_NUMBER);
						attr.setAccountNumber(ACC_NO);
						attr.setBranchCode(HOME_BR_CODE);
						attr.setBranchName(BRNAME);*/
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD ACC_NO: ");
						//attr.setDashboardType("BCA");
						bulkAttributes[i] = attr;
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD bulkAttributes: " + bulkAttributes[0]);

					}
					//XMLParser xp2 = new XMLParser(outputXMLTotalCount);
					//String totalCountRecords = xp2.getValueOf("TOTALCOUNT");

					bulkResponse.setTOTALCOUNT(totalCountRecords);
					bulkResponse.setFETCHCOUNT(Integer.toString(noOfFields));
					bulkResponse.setStatusCode("0");
					bulkResponse.setStatusMessage("DATA FETCH SUCCESSFULLY");
					//bulkResponse.setApplicationName("DIGITAL");  //  on pre production

					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD totalCountRecords: " + totalCountRecords);
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD noOfFields: " + noOfFields);

				}
				else {
					bulkResponse.setTOTALCOUNT(String.valueOf(0));
					bulkResponse.setFETCHCOUNT(String.valueOf(0));
					bulkResponse.setStatusCode("0");
					bulkResponse.setStatusMessage("NO DATA FOUND");
				}
			}
			BulkEnquiryApplicationAttributes[] attrDetails = new BulkEnquiryApplicationAttributes[1];
			BulkEnquiryApplicationAttributes bulkattr = new BulkEnquiryApplicationAttributes();
			bulkattr.setBulkEnquiryDetails(bulkAttributes);

			attrDetails[0] = bulkattr;
			bulkResponse.setApplicationAttributes(attrDetails);
		}
		catch (Exception e)
		{
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}
		//    finally
		//    {
		//      this.mReqParamMap.clear();
		//    }
		return bulkResponse;
	
	}
}