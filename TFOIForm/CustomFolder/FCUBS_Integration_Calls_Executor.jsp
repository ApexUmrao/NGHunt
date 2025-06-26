<jsp:useBean id="customSession" class="com.newgen.custom.wfdesktop.session.WFCustomSession" scope="session"/>

<%@ page import="ISPack.ISUtil.*,ISPack.*,java.io.*,Jdts.DataObject.JPDBInteger, Jdts.DataObject.JPDBISAddress,Jdts.DataObject.*,Jdts.*,java.net.*,java.awt.*"%>
<%@ page import="com.newgen.omni.wf.util.app.*"%>
<%@ page import =" javax.xml.parsers.DocumentBuilder " %>
<%@ page import  =" javax.xml.parsers.DocumentBuilderFactory" %>
<%@ page import  =" org.w3c.dom.Document" %>
<%@ page import  =" org.w3c.dom.Element" %>
<%@ page import  =" org.w3c.dom.Node" %>
<%@ page import  =" org.w3c.dom.NodeList" %>
<%@ page import  =" org.xml.sax.InputSource" %>
<%@page  import="java.util.*"%>
<%@page  import="java.text.SimpleDateFormat"%>
<%@page  import="java.util.List"%>
<%@page  import="java.util.ArrayList"%>
<%@ page import="org.xml.sax.SAXException"%>
<%@ page import="javax.xml.parsers.ParserConfigurationException"%>
<%@ page import="java.io.IOException" %>
<%@ page import ="java.io.StringReader" %>
<%@ page import ="Jdts.DataObject.JPDBString"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.*"%>
<%@ page import ="org.apache.log4j.Logger"%>
<%@ page import ="org.apache.log4j.PropertyConfigurator"%>
<%@page import="java.awt.Image"%>
<%@page import="com.newgen.omni.wf.util.excp.NGException"%>
<%@page import="org.apache.commons.codec.binary.Base64"%>
<%@page import="java.awt.Toolkit"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.newgen.omni.jts.cmgr.XMLParser"%>
<%@ page import="com.newgen.wfmonitor.xmlapi.*"%>
<%@ page import="com.newgen.wfdesktop.xmlapi.*" %>
<%@ page import="ISPack.ISUtil.*,ISPack.*,java.io.*,Jdts.DataObject.JPDBInteger, Jdts.DataObject.JPDBISAddress,Jdts.DataObject.*,Jdts.*,java.net.*,java.awt.*"%>
<%@ page import="com.newgen.wfdesktop.util.*" %>
<%@ page import=="com.newgen.iforms.user.tfo.util.ConnectSocket"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.Properties"%>
<%@page import="org.apache.log4j.PropertyConfigurator"%>
<%@page import ="org.apache.log4j.Logger"%>

<%
initializeLogger();
sQuery="";
sInput="";
sOutput="";
String sRecord = "";
String sCall ="";
String sStatus ="";
String returnXML="";
String sReason ="";
String sErrorDescription="";
String sResponse="";
String sContractRefNumber="";

sCabname=customSession.getEngineName();
sSessionId = customSession.getDMSSessionId();
sUserName = customSession.getUserName();
sJtsIp = customSession.getJtsIp();
iJtsPort = String.valueOf(customSession.getJtsPort());

String sWIName = request.getParameter("WI_NAME");
String sCallName = request.getParameter("CALL_OPERATION");
String callXML="";
String sResVal="";
String ErrorDesc="";
String SLNO="";
String errCase;
String oldREFNO="";
String REFNO = "";

XMLParser xp = null;
int returnCode = 1;
String sExternalRefNumber = "";
String sAdvisingRefNumber = "";
String sIssuingLCRefNumber = "";
String sOperationCode = "";
String sContractCurrency = "";
String sContractAmount = "";
String sNegativeTolerance = "";
String sPositiveTolerance = "";
String sExpiryDate = "";
String sCustomerId = "";
String sProductCode = "";
String sFromPlace = "";
String sToPlace = "";
String sLastShipmentDate = "";
String sPortOfDischarge = "";
String sPortOfLoading = "";
String sGoodsDescription = "";
String sUdfFieldValue = "";
String sRequestCategoryCode = "";
String sRequestTypeCode = "";
String sRelationshipType = "";
String sProcessType = "";
String sSourceChannel = "";
String sMessageType = "";
String sLimitType = "";
String limitSerialNumber = "";
String limitPartyType = "";
String limitLinkageType = "";
String limitPercentContribution = "";
String limitAmountTag = "";
String limitLineRefNumber = "";
String limitLine = "";
String customerType = "";
limitLineCustomerId = "";
sRiskParticipation="N";
String CurrDate = "";
String sBranchCreateCode = "";
String sLimitTracking = "";
String sContractLimitTag = "";

String sStage="";
String sLcReferenceNo="";
String sLiquidationAmount="";
String sMaturityDate="";
String sTenorDays="";
String stransitDays="";
String sBaseDate="";
String sDaysAfterBaseDate="";
String sPartyType="";
String sLimitTrackFlag="";
String sAcceptComFromDate="";
String sAcceptComToDate="";
String sCode="";
String sDescription="";
String sResolved="";
String sResolvedDate="";
String sReceivedDate="";
String sLimitOperation="";
String sLiquidationDate="";
String sFCUBSCurrentDate="";

String expiryType ="";
String purposeOfMessage ="";
String transferConditions ="";
String transferable ="";
String additionalAmountInformation ="";
String purposeOfGtee="";
String expiryConditionOrEvent ="";
String termsAndCondDetails ="";
String sSWIFT_UTILITY_FLAG = "";
String fullText = "";  //ATP-526|REYAZ|13-02-2025|

try
{	
    //socket = ConnectSocket.getReference("10.101.109.182", 4444);
	String sQueryBPM = "SELECT (SELECT VALUE FROM BPM_SERVICE_CONFIG WHERE KEY = 'SOCKETIP') IP,(SELECT VALUE FROM BPM_SERVICE_CONFIG WHERE KEY = 'SOCKETPORT') PORT FROM DUAL";
	String sInputBPM = prepareAPSelectInputXml(sQueryBPM,sCabname,sSessionId);
	WriteToLog_showpage("Y"," ***************Ref number Input "+sInputBPM+" *****************************");
	String sOutputBPM = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputBPM);
	WriteToLog_showpage("Y"," ***************Ref number output = "+sOutputBPM+"  *****************************");
	try
	{
		WriteToLog_showpage("Y","started");
		String ipBPM = sOutputBPM.substring(sOutputBPM.indexOf("<IP>")+4,sOutputBPM.indexOf("</IP>"));
		WriteToLog_showpage("Y","started  1");
		int portBPM = Integer.parseInt(sOutputBPM.substring(sOutputBPM.indexOf("<PORT>")+6,sOutputBPM.indexOf("</PORT>")));
		WriteToLog_showpage("Y","ipBPM : " +ipBPM);
		WriteToLog_showpage("Y"," portBPM : " +portBPM);
		socket = ConnectSocket.getReference(ipBPM, portBPM);
	}
	catch(Exception e)
	{
	}
	sQuery="SELECT COUNT(1) AS COUNT_WI FROM PDBCONNECTION WHERE RANDOMNUMBER = '"+sSessionId+"'";
	sInput=prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
	WriteToLog_showpage("Y"," ***************Checking connection "+sInput+" *****************************");
	sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);	
	String sCount=sOutput.substring(sOutput.indexOf("<COUNT_WI>")+10,sOutput.indexOf("</COUNT_WI>"));
	if(sCount.equalsIgnoreCase("0"))
	{
		out.println("Session Timeout");
	}
	else
	{	
		sQuery = "SELECT REF_NUM, CALL_XML FROM TFO_DJ_INTEGRATION_CALLS_DTLS WHERE WI_NAME = '"+ sWIName +"' AND CALL_NAME = '"+ sCallName +"'";
		sInput = prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
		WriteToLog_showpage("Y"," ***************Ref number Input "+sInput+" *****************************");
		sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);;
		WriteToLog_showpage("Y"," ***************Ref number output = "+sOutput+"  *****************************");
		try
		{
			oldREFNO = sOutput.substring(sOutput.indexOf("<REF_NUM>")+9,sOutput.indexOf("</REF_NUM>"));
		}
		catch(Exception e)
		{
			oldREFNO = "";
		}
		
		if(!sWIName.equals(""))
		{
			sFCUBSCurrentDate = fetchFCUBSDate();
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date dt = new Date();
			CurrDate = formatter.format(dt);
			//if(oldREFNO.equals("")){
				if(true){
				sInput = prepareAPSelectInputXml("SELECT SEQ_WEBSERVICE.NEXTVAL REFNO FROM DUAL",sCabname,sSessionId);
				sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);;
				REFNO = sOutput.substring(sOutput.indexOf("<REFNO>")+"<REFNO>".length(),sOutput.indexOf("</REFNO>"));							
				sQuery = "SELECT ISSUING_BANK_LC_NO,OPERATION_CODE,TRANSACTION_CURRENCY,ACCOUNT_NUMBER,TO_CHAR(EFFECTIVE_DATE,'DD/MM/YYYY') EFFECTIVE_DATE,TRANSACTION_AMOUNT,LC_UNDER,LC_ABOVE,TO_CHAR(TO_TIMESTAMP(EXP_DATE,'YYYY-MM-DD HH24:MI:SS.FF3'),'DD/MM/YYYY') EXP_DATE,CUSTOMER_ID,PRODUCT_TYPE,LC_FROM_PLACE,LC_TO_PLACE ,TO_CHAR(TO_TIMESTAMP(LC_LTST_SHIPMNT_DATE,'YYYY-MM-DD HH24:MI:SS.FF3'),'DD/MM/YYYY') LC_LTST_SHIPMNT_DATE,LC_PORT_OF_DISCHRG,LC_PORT_OF_LOADING,replace(replace(LC_GOODS_DESC,'<![CDATA[',''),']]>','') as LC_GOODS_DESC,BRANCH_CITY,REQUEST_CATEGORY, REQUEST_TYPE,RELATIONSHIP_TYPE,PRO_TRADE_REF_NO,TRN_THIRD_PARTY,TO_CHAR(TO_TIMESTAMP(GRNTEE_CNTR_EXP_DATE,'YYYY-MM-DD HH24:MI:SS.FF3'),'DD/MM/YYYY')  GRNTEE_CNTR_EXP_DATE,TRANSACTION_ADCB_LC_REFERENCE,TO_CHAR(TO_TIMESTAMP(INF_MATURITY_DATE,'YYYY-MM-DD HH24:MI:SS.FF3'),'DD/MM/YYYY') INF_MATURITY_DATE,INF_TENOR_DAYS,TO_CHAR(TO_TIMESTAMP(INF_BASE_DOC_DATE,'YYYY-MM-DD HH24:MI:SS.FF3'),'DD/MM/YYYY') INF_BASE_DOC_DATE,(SELECT  TENOR_BASE_DESC FROM TFO_DJ_LN_TNR_MAST WHERE TENOR_BASE_KEY=INF_TENOR_BASE AND ROWNUM=1) AS INF_TENOR_BASE,INF_ACTUAL_TENOR_BASE,DISCREPANCY_DTLS,LC_LIMIT_LINE,LIMIT_PARTY_TYPE,BILL_PRODUCT_CODE,PROCESS_TYPE,ADVISING_BANK_REF,SWIFT_MESSAGE_TYPE,TRANSFERABLE,CONFN_INTRN,EXP_PLACE,CUST_REF_NO,TRANSSHIPMENT,SHIPMENT_PERIOD,PARTIAL_SHIPMENT,PARTIAL_SHIPMENT_CONDITIONAL,TRANSSHIPMENT_CONDITIONAL,CHARGE_TYPE,INCOTERMS,PERIOD_PRESENT_DAYS ,INSTRCTN_TO_BANK,COURIER_COMPANY,COURIER_AWB_NUMBER,DECODE(DISCOUNT_ON_ACCEP,'2','No','1','Yes',DISCOUNT_ON_ACCEP) AS DISCOUNT_ON_ACCEP,PRO_TRADE_SETTLEMENT_INST,BILL_DEAL_NUMBER,BILL_NAME_OF_VESSEL,BILL_PORT_OF_LOADING,BILL_PORT_OF_DISCHARGE,INF_CHARGE_ACC_NUM,CREDIT_MODE,SPEC_PAYMENT_CONDN,REQ_CONF_PARTY,ADDN_CONDITION,PERIOD_PRESENT_MODE,DEFERRED_PAYMENT,SWIFT_PROCESSING_STATUS,TRN_TENOR,FCUBS_PUR_OF_MSG,TRANSFER_CONDITIONS,TRANSFER_INDICATOR,ADDN_AMT_INFO,PUR_OF_GTEE,EXPIRY_COND,SOURCE_CHANNEL,SWIFT_UTILITY_FLAG,IS_TS_REQUIRED FROM EXT_TFO WHERE WI_NAME = '"+ sWIName +"'";		        	sInput = prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
				WriteToLog_showpage("Y"," ***************Ref number Input "+sInput+" *****************************");
				sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);;
				WriteToLog_showpage("Y"," ***************Ref number output = "+sOutput+"  *****************************");
				// |ATP-526|REYAZ|13-02-2025| START
				String sOuery5 ="SELECT LISTAGG(WMS_DISCREPANCY_DTLS,CHR(10)) AS FULL_TEXT FROM TFO_DJ_TS_DISCREPANCY_DETAILS WHERE WI_NAME ='"+sWIName+"'";
				String sInput5 = prepareAPSelectInputXml(sOuery5,sCabname,sSessionId);
				String sOutput5 = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput5);
				WriteToLog_showpage("Y"," ***************sOutput5 = "+sOutput5+"  *****************************");
				try{
					fullText = sOutput5.substring(sOutput5.indexOf("<FULL_TEXT>")+"<FULL_TEXT>".length(),sOutput5.indexOf("</FULL_TEXT>"));
				}catch(Exception e){
					WriteToLog_showpage("Y","Exception 5 : " +e.getMessage());
				}
				// |ATP-526|REYAZ|13-02-2025| END
				try
				{					
					sRequestCategoryCode = sOutput.substring(sOutput.indexOf("<REQUEST_CATEGORY>")+"<REQUEST_CATEGORY>".length(),sOutput.indexOf("</REQUEST_CATEGORY>"));
					sRequestTypeCode = sOutput.substring(sOutput.indexOf("<REQUEST_TYPE>")+"<REQUEST_TYPE>".length(),sOutput.indexOf("</REQUEST_TYPE>"));
					sProcessType = sOutput.substring(sOutput.indexOf("<PROCESS_TYPE>")+"<PROCESS_TYPE>".length(),sOutput.indexOf("</PROCESS_TYPE>"));
					sSourceChannel = sOutput.substring(sOutput.indexOf("<SOURCE_CHANNEL>")+"<SOURCE_CHANNEL>".length(),sOutput.indexOf("</SOURCE_CHANNEL>"));
					sSWIFT_UTILITY_FLAG = sOutput.substring(sOutput.indexOf("<SWIFT_UTILITY_FLAG>")+"<SWIFT_UTILITY_FLAG>".length(),sOutput.indexOf("</SWIFT_UTILITY_FLAG>"));
					if("ELC".equalsIgnoreCase(sRequestCategoryCode) && "ELC_LCA".equalsIgnoreCase(sRequestTypeCode) && "BAU".equalsIgnoreCase(sProcessType))
					{						
						sExternalRefNumber = sOutput.substring(sOutput.indexOf("<ISSUING_BANK_LC_NO>")+"<ISSUING_BANK_LC_NO>".length(),sOutput.indexOf("</ISSUING_BANK_LC_NO>"));
						sOperationCode = sOutput.substring(sOutput.indexOf("<OPERATION_CODE>")+"<OPERATION_CODE>".length(),sOutput.indexOf("</OPERATION_CODE>"));
						sContractCurrency = sOutput.substring(sOutput.indexOf("<TRANSACTION_CURRENCY>")+"<TRANSACTION_CURRENCY>".length(),sOutput.indexOf("</TRANSACTION_CURRENCY>"));
						sContractAmount = sOutput.substring(sOutput.indexOf("<TRANSACTION_AMOUNT>")+"<TRANSACTION_AMOUNT>".length(),sOutput.indexOf("</TRANSACTION_AMOUNT>"));
						sNegativeTolerance = sOutput.substring(sOutput.indexOf("<LC_UNDER>")+"<LC_UNDER>".length(),sOutput.indexOf("</LC_UNDER>"));
						sPositiveTolerance = sOutput.substring(sOutput.indexOf("<LC_ABOVE>")+"<LC_ABOVE>".length(),sOutput.indexOf("</LC_ABOVE>"));
						sExpiryDate = sOutput.substring(sOutput.indexOf("<EXP_DATE>")+"<EXP_DATE>".length(),sOutput.indexOf("</EXP_DATE>"));
						sCustomerId = sOutput.substring(sOutput.indexOf("<CUSTOMER_ID>")+"<CUSTOMER_ID>".length(),sOutput.indexOf("</CUSTOMER_ID>"));
						sProductCode = sOutput.substring(sOutput.indexOf("<PRODUCT_TYPE>")+"<PRODUCT_TYPE>".length(),sOutput.indexOf("</PRODUCT_TYPE>"));
						sFromPlace = sOutput.substring(sOutput.indexOf("<LC_FROM_PLACE>")+"<LC_FROM_PLACE>".length(),sOutput.indexOf("</LC_FROM_PLACE>"));
						sToPlace = sOutput.substring(sOutput.indexOf("<LC_TO_PLACE>")+"<LC_TO_PLACE>".length(),sOutput.indexOf("</LC_TO_PLACE>"));
						sLastShipmentDate = sOutput.substring(sOutput.indexOf("<LC_LTST_SHIPMNT_DATE>")+"<LC_LTST_SHIPMNT_DATE>".length(),sOutput.indexOf("</LC_LTST_SHIPMNT_DATE>"));
						sPortOfDischarge = sOutput.substring(sOutput.indexOf("<LC_PORT_OF_DISCHRG>")+"<LC_PORT_OF_DISCHRG>".length(),sOutput.indexOf("</LC_PORT_OF_DISCHRG>"));
						sPortOfLoading = sOutput.substring(sOutput.indexOf("<LC_PORT_OF_LOADING>")+"<LC_PORT_OF_LOADING>".length(),sOutput.indexOf("</LC_PORT_OF_LOADING>"));
						sGoodsDescription = sOutput.substring(sOutput.indexOf("<LC_GOODS_DESC>")+"<LC_GOODS_DESC>".length(),sOutput.indexOf("</LC_GOODS_DESC>"));
						sUdfFieldValue = sOutput.substring(sOutput.indexOf("<BRANCH_CITY>")+"<BRANCH_CITY>".length(),sOutput.indexOf("</BRANCH_CITY>"));
						String sUdfFieldValue1 = sOutput.substring(sOutput.indexOf("<IS_TS_REQUIRED>")+"<IS_TS_REQUIRED>".length(),sOutput.indexOf("</IS_TS_REQUIRED>"));
						sRelationshipType = sOutput.substring(sOutput.indexOf("<RELATIONSHIP_TYPE>")+"<RELATIONSHIP_TYPE>".length(),sOutput.indexOf("</RELATIONSHIP_TYPE>"));
						sLimitType = sOutput.substring(sOutput.indexOf("<LIMIT_PARTY_TYPE>")+"<LIMIT_PARTY_TYPE>".length(),sOutput.indexOf("</LIMIT_PARTY_TYPE>"));
						customerType = "BEN";
						HashMap limitCreditRvwChecklist = getCreditReviwChecklistData(sRequestCategoryCode,sRequestTypeCode,sWIName);
						String limitCheckAlongAdvising = (String)limitCreditRvwChecklist.get("ELC_add_cnfm_along_adv");
						String specifyLimitLine = (String)limitCreditRvwChecklist.get("ELC_spec_limit_line");
						String partyDetails=getPartyDetailsforCreateAmend(sLimitType,sRequestTypeCode,sWIName,"","");
						if ("Yes".equalsIgnoreCase(limitCheckAlongAdvising)) {
							sLimitTracking = "Y";
							limitSerialNumber = "1";
							limitPartyType = sLimitType;
							limitLinkageType = "F";
							limitPercentContribution = "100";
							limitAmountTag = "CNF_LIAB_OS_AMT";
							limitLineRefNumber = specifyLimitLine.toUpperCase();
							sContractLimitTag = "<contractLimits><contractLimit><limitSerialNumber>" + limitSerialNumber + "</limitSerialNumber><limitPartyType>" + limitPartyType + "</limitPartyType><limitLinkageType>" + limitLinkageType + "</limitLinkageType><limitPercentContribution>" + limitPercentContribution + "</limitPercentContribution><limitAmountTag>" + limitAmountTag + "</limitAmountTag><limitLineCustomerId>" + this.limitLineCustomerId + "</limitLineCustomerId><limitLineRefNumber>" + limitLineRefNumber + "</limitLineRefNumber></contractLimit></contractLimits>";
						}
						else{
							sLimitTracking = "N";
							limitSerialNumber = "";
							limitPartyType = "";
							limitLinkageType = "";
							limitPercentContribution = "";
							limitAmountTag = "";
							limitLineRefNumber = "";
							sContractLimitTag = "";
						}
						sBranchCreateCode = fetchBranchCode(sRelationshipType , sWIName);
							WriteToLog_showpage("Y","modTradeLCContract_Oper AFTEERR sBranchCreateCode: "+ sCallName);
						if(sCallName.equalsIgnoreCase("modTradeLCContract_Oper")){							
							callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeLCContract_Oper</operationType><correlationID>342343</correlationID><modTradeLCContractReq><requestType>C</requestType><externalRefNumber>" + sExternalRefNumber + "</externalRefNumber><operationCode>" + sOperationCode + "</operationCode><transferable>N</transferable><settlementType>N</settlementType><settlementMonth></settlementMonth><guaranteeType></guaranteeType><contractCurrency>" + sContractCurrency + "</contractCurrency><contractAmount>" + sContractAmount + "</contractAmount><negativeTolerance>" + sNegativeTolerance + "</negativeTolerance><positiveTolerance>" + sPositiveTolerance + "</positiveTolerance><benefConfirmation>N</benefConfirmation><maxLiabilityAmount></maxLiabilityAmount><expiryDate>" + sExpiryDate + "</expiryDate><issueDate>" + CurrDate + "</issueDate><closureType>A</closureType><customerType>" + customerType + "</customerType><customerId>" + sCustomerId + "</customerId><remark>"+setProTradeRemark(sWIName)+"</remark><customerName></customerName><customerRefNumber>" + sExternalRefNumber + "</customerRefNumber><limitTracking>" + sLimitTracking + "</limitTracking><amendementFlag>N</amendementFlag><languageCode>ENG</languageCode><amendmentNumber>0</amendmentNumber><mayConfirm></mayConfirm><effectiveDate>" + CurrDate + "</effectiveDate><limitTrackTenorType>L</limitTrackTenorType><productCode>" + sProductCode + "</productCode><productType></productType><issuanceRequest></issuanceRequest><ruleNarrative></ruleNarrative><stopDate>" + sExpiryDate + "</stopDate><userReferenceNumber></userReferenceNumber><branchCode>" + sBranchCreateCode + "</branchCode><partyDetails>" +partyDetails+ "</partyDetails><partyOtherAddresses>" + getPartyOtherDetails(sWIName) + "</partyOtherAddresses><shipmentDetails><fromPlace>" + sFromPlace + "</fromPlace><toPlace>" + sToPlace + "</toPlace><lastShipmentDate>" + sLastShipmentDate + "</lastShipmentDate><portOfDischarge>" + sPortOfDischarge + "</portOfDischarge><portOfLoading>" + sPortOfLoading + "</portOfLoading></shipmentDetails><goodsDetails><goodsCode>OTHERS</goodsCode><goodsDescription>" + sGoodsDescription + "</goodsDescription></goodsDetails><txnUDFDetails><UDFFieldName>TXN_ISSUING_CENTER</UDFFieldName><udfFieldValue>" + sUdfFieldValue + "</udfFieldValue><UDFFieldName>AUTO_DOCCHK_REQ</UDFFieldName><udfFieldValue>" + sUdfFieldValue1 + "</udfFieldValue></txnUDFDetails><collateralDetails><collateralCurrency></collateralCurrency><collateralPercent></collateralPercent></collateralDetails>" + sContractLimitTag + "<provisionCus><provisionForReceivableComponent>Y</provisionForReceivableComponent></provisionCus><forceDebitCus><forceDebitAccount>Y</forceDebitAccount></forceDebitCus><wmsDetails><workItemNumber>" + sWIName + "</workItemNumber></wmsDetails><contrMstr><counterGuaranteeExpiryDate></counterGuaranteeExpiryDate></contrMstr></modTradeLCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";
							WriteToLog_showpage("Y","modTradeLCContract_Oper sOutput: "+ callXML);
							sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sCallName +"',"+ convertToPlainString(callXML) +",'"+ REFNO +"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","modTradeLCContract_Oper callXML TFO_DJ_INTEGRATION_CALLS_DTLS: "+ sQuery + " *****************************");	
							sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;						
							WriteToLog_showpage("Y"," insert modTradeLCContract_Oper sOutput: "+ sOutput);											
							sOutput = socket.connectToSocket(callXML);
							WriteToLog_showpage("Y","modTradeLCContract_Oper callXML sOutput"+ sOutput);
							
							String queryUpdateXML = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'" + sOutput + "'","WI_NAME = '"+ sWIName +"'  and REF_NUM = '"+REFNO+"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","update sOutputUpdateXML input: "+ queryUpdateXML);
							String sOutputUpdateXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",queryUpdateXML);;
							WriteToLog_showpage("Y","update sOutputUpdateXML output: " + sOutputUpdateXML);
								
							
							xp = new XMLParser(sOutput);
							returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
							String nextValue ="";
							if(returnCode == 0){
								sErrorDescription = xp.getValueOf("errorDescription");
								sContractRefNumber = xp.getValueOf("contractRefNumber");
								sResponse = "Success";
								sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS,TRANSACTION_REFERENCE","'" + sResponse + "','" + sContractRefNumber + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
								out.println(returnCode+"#"+sResponse+"#"+sErrorDescription);
							} else {
								sErrorDescription = xp.getValueOf("errorDescription");
								sResponse = "Failure";
								sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
								out.println("1#"+sResponse+"#"+sErrorDescription);
							}
						}
					}
					else if("ELC".equalsIgnoreCase(sRequestCategoryCode) && "ELC_LCA".equalsIgnoreCase(sRequestTypeCode) && "SWIFT".equalsIgnoreCase(sProcessType))
					{   
				        String adviceData="";				
						sIssuingLCRefNumber = sOutput.substring(sOutput.indexOf("<ISSUING_BANK_LC_NO>")+"<ISSUING_BANK_LC_NO>".length(),sOutput.indexOf("</ISSUING_BANK_LC_NO>"));
						sAdvisingRefNumber = sOutput.substring(sOutput.indexOf("<ADVISING_BANK_REF>")+"<ADVISING_BANK_REF>".length(),sOutput.indexOf("</ADVISING_BANK_REF>"));
						sOperationCode = sOutput.substring(sOutput.indexOf("<OPERATION_CODE>")+"<OPERATION_CODE>".length(),sOutput.indexOf("</OPERATION_CODE>"));
						sContractCurrency = sOutput.substring(sOutput.indexOf("<TRANSACTION_CURRENCY>")+"<TRANSACTION_CURRENCY>".length(),sOutput.indexOf("</TRANSACTION_CURRENCY>"));
						sContractAmount = sOutput.substring(sOutput.indexOf("<TRANSACTION_AMOUNT>")+"<TRANSACTION_AMOUNT>".length(),sOutput.indexOf("</TRANSACTION_AMOUNT>"));
						sNegativeTolerance = sOutput.substring(sOutput.indexOf("<LC_UNDER>")+"<LC_UNDER>".length(),sOutput.indexOf("</LC_UNDER>"));
						sPositiveTolerance = sOutput.substring(sOutput.indexOf("<LC_ABOVE>")+"<LC_ABOVE>".length(),sOutput.indexOf("</LC_ABOVE>"));
						sExpiryDate = sOutput.substring(sOutput.indexOf("<EXP_DATE>")+"<EXP_DATE>".length(),sOutput.indexOf("</EXP_DATE>"));
						sCustomerId = sOutput.substring(sOutput.indexOf("<CUSTOMER_ID>")+"<CUSTOMER_ID>".length(),sOutput.indexOf("</CUSTOMER_ID>"));
						sProductCode = sOutput.substring(sOutput.indexOf("<PRODUCT_TYPE>")+"<PRODUCT_TYPE>".length(),sOutput.indexOf("</PRODUCT_TYPE>"));
						sFromPlace = sOutput.substring(sOutput.indexOf("<LC_FROM_PLACE>")+"<LC_FROM_PLACE>".length(),sOutput.indexOf("</LC_FROM_PLACE>"));
						sToPlace = sOutput.substring(sOutput.indexOf("<LC_TO_PLACE>")+"<LC_TO_PLACE>".length(),sOutput.indexOf("</LC_TO_PLACE>"));
						sLastShipmentDate = sOutput.substring(sOutput.indexOf("<LC_LTST_SHIPMNT_DATE>")+"<LC_LTST_SHIPMNT_DATE>".length(),sOutput.indexOf("</LC_LTST_SHIPMNT_DATE>"));
						sPortOfDischarge = sOutput.substring(sOutput.indexOf("<LC_PORT_OF_DISCHRG>")+"<LC_PORT_OF_DISCHRG>".length(),sOutput.indexOf("</LC_PORT_OF_DISCHRG>"));
						sPortOfLoading = sOutput.substring(sOutput.indexOf("<LC_PORT_OF_LOADING>")+"<LC_PORT_OF_LOADING>".length(),sOutput.indexOf("</LC_PORT_OF_LOADING>"));
						sGoodsDescription = sOutput.substring(sOutput.indexOf("<LC_GOODS_DESC>")+"<LC_GOODS_DESC>".length(),sOutput.indexOf("</LC_GOODS_DESC>"));
						sUdfFieldValue = sOutput.substring(sOutput.indexOf("<BRANCH_CITY>")+"<BRANCH_CITY>".length(),sOutput.indexOf("</BRANCH_CITY>"));
						sRelationshipType = sOutput.substring(sOutput.indexOf("<RELATIONSHIP_TYPE>")+"<RELATIONSHIP_TYPE>".length(),sOutput.indexOf("</RELATIONSHIP_TYPE>"));
						sMessageType = sOutput.substring(sOutput.indexOf("<SWIFT_MESSAGE_TYPE>")+"<SWIFT_MESSAGE_TYPE>".length(),sOutput.indexOf("</SWIFT_MESSAGE_TYPE>"));
						sLimitType = sOutput.substring(sOutput.indexOf("<LIMIT_PARTY_TYPE>")+"<LIMIT_PARTY_TYPE>".length(),sOutput.indexOf("</LIMIT_PARTY_TYPE>"));
						String sProcessingStatus=sOutput.substring(sOutput.indexOf("<SWIFT_PROCESSING_STATUS>")+"<SWIFT_PROCESSING_STATUS>".length(),sOutput.indexOf("</SWIFT_PROCESSING_STATUS>"));
						String sUdfFieldValue1 = sOutput.substring(sOutput.indexOf("<IS_TS_REQUIRED>")+"<IS_TS_REQUIRED>".length(),sOutput.indexOf("</IS_TS_REQUIRED>"));
						
						customerType = "BEN";
						HashMap limitCreditRvwChecklist = getCreditReviwChecklistData(sRequestCategoryCode,sRequestTypeCode,sWIName);
						String limitCheckAlongAdvising = (String)limitCreditRvwChecklist.get("ELC_add_cnfm_along_adv");
						String specifyLimitLine = (String)limitCreditRvwChecklist.get("ELC_spec_limit_line");
						String partyDetails=getPartyDetailsforCreateAmend(sLimitType,sRequestTypeCode,sWIName,"","");
						if ("ANC".equalsIgnoreCase(sOperationCode)) {
							sLimitTracking = "Y";
							limitSerialNumber = "1";
							limitPartyType = sLimitType;
							limitLinkageType = "F";
							limitPercentContribution = "100";
							limitAmountTag = "CNF_LIAB_OS_AMT";
							limitLineRefNumber = specifyLimitLine.toUpperCase();
							sContractLimitTag = "<contractLimits><contractLimit><limitSerialNumber>" + limitSerialNumber + "</limitSerialNumber><limitPartyType>" + limitPartyType + "</limitPartyType><limitLinkageType>" + limitLinkageType + "</limitLinkageType><limitPercentContribution>" + limitPercentContribution + "</limitPercentContribution><limitAmountTag>" + limitAmountTag + "</limitAmountTag><limitLineCustomerId>" + this.limitLineCustomerId + "</limitLineCustomerId><limitLineRefNumber>" + limitLineRefNumber + "</limitLineRefNumber></contractLimit></contractLimits>";
						}
						else{
							sLimitTracking = "N";
							limitSerialNumber = "";
							limitPartyType = "";
							limitLinkageType = "";
							limitPercentContribution = "";
							limitAmountTag = "";
							limitLineRefNumber = "";
							sContractLimitTag = "";
						}
						sBranchCreateCode = fetchBranchCode(sRelationshipType , sWIName);
						if("720".equalsIgnoreCase(sMessageType) || "710".equalsIgnoreCase(sMessageType)){
							sExternalRefNumber = sAdvisingRefNumber;
						}
						else{
							sExternalRefNumber = sIssuingLCRefNumber;
						}
						String sTransferable = getSWIFTTXNDetails(sWIName,"replace(replace(FORM_DOC_CRD,'<![CDATA[',''),']]>','')");
						if(("700".equalsIgnoreCase(sMessageType) || "710".equalsIgnoreCase(sMessageType)) && ("IRREVOCABLE TRANSFERABLE".equalsIgnoreCase(sTransferable) || "IRREVOC TRANS STANDBY".equalsIgnoreCase(sTransferable))){
							sTransferable = "Y";
						}
						else{
							sTransferable = "N";
						}
						if("720".equalsIgnoreCase(sMessageType) && "IRREVOC TRANS STANDBY".equalsIgnoreCase(sTransferable)){
							sTransferable = "Y";
						}
						else{
							sTransferable = "N";
						}
						String sAVLBY = getSWIFTTXNDetails(sWIName,"replace(replace(AVL_BY,'<![CDATA[',''),']]>','')");
						if("By Negotiation".equalsIgnoreCase(sAVLBY)){
							sAVLBY = "N";
						}
						else if("By Def Payment".equalsIgnoreCase(sAVLBY)){
							sAVLBY = "D";
						}
						else if("By Acceptance".equalsIgnoreCase(sAVLBY)){
							sAVLBY = "A";
						}
						else if("By Mixed Payment".equalsIgnoreCase(sAVLBY)){
							sAVLBY = "M";
						}
						else if("By payment".equalsIgnoreCase(sAVLBY)){
							sAVLBY = "P";
						}
						String sPaymentDate = "";
						if(!"".equals(getSWIFTTXNDetails(sWIName,"MXD_PYMT_DTLS"))){
							sPaymentDate = getSWIFTTXNDetails(sWIName,"replace(replace(MXD_PYMT_DTLS,'<![CDATA[',''),']]>','')");
						}
						else if(!"".equals(getSWIFTTXNDetails(sWIName,"NGN_DFR_PYMT_DTLS"))){
							sPaymentDate = getSWIFTTXNDetails(sWIName,"replace(replace(NGN_DFR_PYMT_DTLS,'<![CDATA[',''),']]>','')");
						}
						String sMayConfirm = "N";
						if("May confirm".equalsIgnoreCase(getSWIFTTXNDetails(sWIName,"replace(replace(CNF_INSTR,'<![CDATA[',''),']]>','')"))){
							sMayConfirm = "Y";
						}
						else{
							sMayConfirm = "N";
						}
						String sTransShipment = getSWIFTTXNDetails(sWIName,"replace(replace(TRANSHIPMENT,'<![CDATA[',''),']]>','')");
						if("Allowed".equalsIgnoreCase(sTransShipment)){
							sTransShipment = "Y";
						}
						else if("Not Allowed".equalsIgnoreCase(sTransShipment)){
							sTransShipment = "N";
						}
						else if("Conditional".equalsIgnoreCase(sTransShipment)){
							sTransShipment = "A";
						}
						else{
							sTransShipment = "B";
						}
						String sPartialShipment = getSWIFTTXNDetails(sWIName,"replace(replace(PRTL_SHPMTS,'<![CDATA[',''),']]>','')");
						if("Allowed".equalsIgnoreCase(sPartialShipment)){
							sPartialShipment = "Y";
						}
						else if("Not Allowed".equalsIgnoreCase(sPartialShipment)){
							sPartialShipment = "N";
						}
						else if("Conditional".equalsIgnoreCase(sPartialShipment)){
							sPartialShipment = "A";
						}
						else{
							sPartialShipment = "B";
						}
						String sFreeFormatTextTag = "";
						if(!"".equals(getSWIFTTXNDetails(sWIName,"ADD_CONDITIONS"))){
							sFreeFormatTextTag = "<freeFormatTextDtl><freeFormatTextCode>UPLD_COND_1</freeFormatTextCode><freeFormatTextDescription>" + ""+getSWIFTTXNDetails(sWIName,"replace(replace(ADD_CONDITIONS,'<![CDATA[',''),']]>','')")+ "</freeFormatTextDescription></freeFormatTextDtl>";
						}
						 if(!"".equals(getSWIFTTXNDetails(sWIName,"INSTR_TOPAY_ACP_NEG_BNK"))){
							sFreeFormatTextTag = sFreeFormatTextTag + "<freeFormatTextDtl><freeFormatTextCode>INSTRUCTION</freeFormatTextCode><freeFormatTextDescription>" + getSWIFTTXNDetails(sWIName,"replace(replace(INSTR_TOPAY_ACP_NEG_BNK,'<![CDATA[',''),']]>','')") + "</freeFormatTextDescription></freeFormatTextDtl>";
						}
						 if(!"".equals(getSWIFTTXNDetails(sWIName,"SEN_REC_INFO"))){
							sFreeFormatTextTag = sFreeFormatTextTag + "<freeFormatTextDtl><freeFormatTextCode>SND2RECINFO</freeFormatTextCode><freeFormatTextDescription>" + getSWIFTTXNDetails(sWIName,"replace(replace(SEN_REC_INFO,'<![CDATA[',''),']]>','')") + "</freeFormatTextDescription></freeFormatTextDtl>";
						}
						if((("700".equalsIgnoreCase(sMessageType)||"710".equalsIgnoreCase(sMessageType))&& "R".equalsIgnoreCase(sProcessingStatus))
					        ||"720".equalsIgnoreCase(sMessageType))
							{
							   adviceData=setAdviceDetails(sOperationCode,sWIName);
							}
						if(sCallName.equalsIgnoreCase("modTradeLCContract_Oper")){							
							callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeLCContract_Oper</operationType><correlationID>342343</correlationID><modTradeLCContractReq><requestType>C</requestType><externalRefNumber>" + sExternalRefNumber + "</externalRefNumber><operationCode>" + sOperationCode + "</operationCode><transferable>"+sTransferable+"</transferable><settlementType>N</settlementType><settlementMonth>"+sAVLBY+"</settlementMonth><guaranteeType></guaranteeType><contractCurrency>" + sContractCurrency + "</contractCurrency><contractAmount>" + sContractAmount + "</contractAmount><negativeTolerance>" + sNegativeTolerance + "</negativeTolerance><positiveTolerance>" + sPositiveTolerance + "</positiveTolerance><benefConfirmation>N</benefConfirmation><maxLiabilityAmount></maxLiabilityAmount><expiryDate>" + sExpiryDate + "</expiryDate><placeOfExpiry>" + getSWIFTTXNDetails(sWIName,"PLACE_EXP") + "</placeOfExpiry><issueDate>" + getSWIFTTXNDetails(sWIName,"TO_CHAR(DT_ISSUE,'DD/MM/YYYY')") + "</issueDate><closureType>A</closureType><customerType>" + customerType + "</customerType><customerId>" + sCustomerId + "</customerId><customerRefDate>" + getSWIFTTXNDetails(sWIName,"TO_CHAR(DT_ISSUE,'DD/MM/YYYY')") + "</customerRefDate><remark>"+setProTradeRemark(sWIName)+"</remark><paymentDate>"+sPaymentDate+"</paymentDate><chargesFromBenef>"+getSWIFTTXNDetails(sWIName,"replace(replace(DTLS_CHRG,'<![CDATA[',''),']]>','')")+"</chargesFromBenef><customerName></customerName><customerRefNumber>" + sIssuingLCRefNumber + "</customerRefNumber><limitTracking>" + sLimitTracking + "</limitTracking><amendementFlag>N</amendementFlag><creditAvailableWith>"+getSWIFTTXNDetails(sWIName,"replace(replace(AVL_WITH,'<![CDATA[',''),']]>','')")+"</creditAvailableWith><languageCode>ENG</languageCode><amendmentNumber>0</amendmentNumber><mayConfirm>"+sMayConfirm+"</mayConfirm><effectiveDate>" + getSWIFTTXNDetails(sWIName,"TO_CHAR(DT_ISSUE,'DD/MM/YYYY')") + "</effectiveDate><limitTrackTenorType>L</limitTrackTenorType><productCode>" + sProductCode + "</productCode><productType></productType><presentationPeriod>" + getSWIFTTXNDetails(sWIName,"PERIOD_PTN_DAY") + "</presentationPeriod><presentationNarrative>"+getSWIFTTXNDetails(sWIName,"replace(replace(PERIOD_PTN_DES,'<![CDATA[',''),']]>','')")+"</presentationNarrative><issuanceRequest></issuanceRequest><ruleNarrative></ruleNarrative><applicableRule>" + getSWIFTTXNDetails(sWIName,"replace(replace(APPLICABLE_RULES,'<![CDATA[',''),']]>','')") + "</applicableRule><stopDate>" + sExpiryDate + "</stopDate><userReferenceNumber>" + sIssuingLCRefNumber + "</userReferenceNumber><branchCode>" + sBranchCreateCode + "</branchCode><partyDetails>" +partyDetails + "</partyDetails><partyOtherAddresses></partyOtherAddresses><shipmentDetails><fromPlace>" + sFromPlace + "</fromPlace><toPlace>" + sToPlace + "</toPlace><lastShipmentDate>" + sLastShipmentDate + "</lastShipmentDate><transShipment>" + sTransShipment + "</transShipment><shipmentPeriod>" + getSWIFTTXNDetails(sWIName,"replace(replace(SHIPMENT_PERIOD,'<![CDATA[',''),']]>','')") + "</shipmentPeriod><portOfDischarge>" + sPortOfDischarge + "</portOfDischarge><portOfLoading>" + sPortOfLoading + "</portOfLoading><partialShipment>" + sPartialShipment + "</partialShipment></shipmentDetails><documentDetails><documentDetail><documentCode>UPLD_700_46</documentCode><documentType>O</documentType><documentDescription>" + getSWIFTTXNDetails(sWIName,"replace(replace(DOC_REQ,'<![CDATA[',''),']]>','')") + "</documentDescription></documentDetail></documentDetails><goodsDetails><goodsCode>OTHERS</goodsCode><goodsDescription>" + sGoodsDescription + "</goodsDescription></goodsDetails><freeFormatTextDtls>"+sFreeFormatTextTag+"</freeFormatTextDtls><txnUDFDetails><UDFFieldName>TXN_ISSUING_CENTER</UDFFieldName><udfFieldValue>" + sUdfFieldValue + "</udfFieldValue><UDFFieldName>AUTO_DOCCHK_REQ</UDFFieldName><udfFieldValue>" + sUdfFieldValue1 + "</udfFieldValue></txnUDFDetails><collateralDetails><collateralCurrency></collateralCurrency><collateralPercent></collateralPercent></collateralDetails>" + sContractLimitTag + "<provisionCus><provisionForReceivableComponent>Y</provisionForReceivableComponent></provisionCus><forceDebitCus><forceDebitAccount>Y</forceDebitAccount></forceDebitCus><wmsDetails><workItemNumber>" + sWIName + "</workItemNumber></wmsDetails><contrMstr><counterGuaranteeExpiryDate></counterGuaranteeExpiryDate></contrMstr><adviceDetails>"+adviceData+"</adviceDetails></modTradeLCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";
							WriteToLog_showpage("Y","modTradeLCContract_Oper sOutput: "+ callXML);
							sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sCallName +"',"+ convertToPlainString(callXML) +",'"+ REFNO +"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","modTradeLCContract_Oper callXML TFO_DJ_INTEGRATION_CALLS_DTLS: "+ sQuery + " *****************************");	
							sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;						
							WriteToLog_showpage("Y"," insert modTradeLCContract_Oper sOutput: "+ sOutput);											
							
							sOutput = socket.connectToSocket(callXML);
							WriteToLog_showpage("Y","modTradeLCContract_Oper callXML sOutput"+ sOutput);
							
							String queryUpdateXML = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'" + sOutput + "'","WI_NAME = '"+ sWIName +"'  and REF_NUM = '"+REFNO+"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","update sOutputUpdateXML input: "+ queryUpdateXML);
							String sOutputUpdateXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",queryUpdateXML);;
							WriteToLog_showpage("Y","update sOutputUpdateXML output: " + sOutputUpdateXML);
								
							
							xp = new XMLParser(sOutput);
							returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
							String nextValue ="";
							if(returnCode == 0){
								sErrorDescription = xp.getValueOf("errorDescription");
								sContractRefNumber = xp.getValueOf("contractRefNumber");
								sResponse = "Success";
								sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS,TRANSACTION_REFERENCE","'" + sResponse + "','" + sContractRefNumber + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
								out.println(returnCode+"#"+sResponse+"#"+sErrorDescription);
							} else {
								sErrorDescription = xp.getValueOf("errorDescription");
								sResponse = "Failure";
								sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
								out.println("1#"+sResponse+"#"+sErrorDescription);
							}
						}
					}
					else if("ILC".equalsIgnoreCase(sRequestCategoryCode) && "ILC_NI".equalsIgnoreCase(sRequestTypeCode))
					{
						String sTransferable="";
						String sCustInstr ="";
						String sPlaceOfExpiry ="";
						String sCustomerRefNo = "";
						String sTransShipment ="";
						String sShipmentPeriod ="";
						String sPartialShipment ="";
						String sPartialShipmentDets ="";
						String sTransshipmentsDtls ="";
						String sChargesFromBenef ="";
						String sIncoTerm ="";
						String sPeriodPresentationDays = "";
						//String sDraftSerialNo = "";
						//String sDraftTenor = "";
						//String sCreditDaysFrom = "";
						//String sPercentageAmount = "";
						//String sDrawee ="";
						//String sDraftAmount = "";
						String sDraftSpecifyOthers = "";
						String sDocDetls = "";
						String sDraftDetls = "";
						
						sProcessType = sOutput.substring(sOutput.indexOf("<PROCESS_TYPE>")+"<PROCESS_TYPE>".length(),sOutput.indexOf("</PROCESS_TYPE>"));
						sExternalRefNumber = sOutput.substring(sOutput.indexOf("<PRO_TRADE_REF_NO>")+"<PRO_TRADE_REF_NO>".length(),sOutput.indexOf("</PRO_TRADE_REF_NO>"));
						sOperationCode = sOutput.substring(sOutput.indexOf("<OPERATION_CODE>")+"<OPERATION_CODE>".length(),sOutput.indexOf("</OPERATION_CODE>"));
						sContractCurrency = sOutput.substring(sOutput.indexOf("<TRANSACTION_CURRENCY>")+"<TRANSACTION_CURRENCY>".length(),sOutput.indexOf("</TRANSACTION_CURRENCY>"));
						sContractAmount = sOutput.substring(sOutput.indexOf("<TRANSACTION_AMOUNT>")+"<TRANSACTION_AMOUNT>".length(),sOutput.indexOf("</TRANSACTION_AMOUNT>"));
						sNegativeTolerance = sOutput.substring(sOutput.indexOf("<LC_UNDER>")+"<LC_UNDER>".length(),sOutput.indexOf("</LC_UNDER>"));
						sPositiveTolerance = sOutput.substring(sOutput.indexOf("<LC_ABOVE>")+"<LC_ABOVE>".length(),sOutput.indexOf("</LC_ABOVE>"));
						sExpiryDate = sOutput.substring(sOutput.indexOf("<EXP_DATE>")+"<EXP_DATE>".length(),sOutput.indexOf("</EXP_DATE>"));
						sCustomerId = sOutput.substring(sOutput.indexOf("<CUSTOMER_ID>")+"<CUSTOMER_ID>".length(),sOutput.indexOf("</CUSTOMER_ID>"));
						sProductCode = sOutput.substring(sOutput.indexOf("<PRODUCT_TYPE>")+"<PRODUCT_TYPE>".length(),sOutput.indexOf("</PRODUCT_TYPE>"));
						sFromPlace = sOutput.substring(sOutput.indexOf("<LC_FROM_PLACE>")+"<LC_FROM_PLACE>".length(),sOutput.indexOf("</LC_FROM_PLACE>"));
						sToPlace = sOutput.substring(sOutput.indexOf("<LC_TO_PLACE>")+"<LC_TO_PLACE>".length(),sOutput.indexOf("</LC_TO_PLACE>"));
						sLastShipmentDate = sOutput.substring(sOutput.indexOf("<LC_LTST_SHIPMNT_DATE>")+"<LC_LTST_SHIPMNT_DATE>".length(),sOutput.indexOf("</LC_LTST_SHIPMNT_DATE>"));
						sPortOfDischarge = sOutput.substring(sOutput.indexOf("<LC_PORT_OF_DISCHRG>")+"<LC_PORT_OF_DISCHRG>".length(),sOutput.indexOf("</LC_PORT_OF_DISCHRG>"));
						sPortOfLoading = sOutput.substring(sOutput.indexOf("<LC_PORT_OF_LOADING>")+"<LC_PORT_OF_LOADING>".length(),sOutput.indexOf("</LC_PORT_OF_LOADING>"));
						sGoodsDescription = sOutput.substring(sOutput.indexOf("<LC_GOODS_DESC>")+"<LC_GOODS_DESC>".length(),sOutput.indexOf("</LC_GOODS_DESC>"));
						sUdfFieldValue = sOutput.substring(sOutput.indexOf("<BRANCH_CITY>")+"<BRANCH_CITY>".length(),sOutput.indexOf("</BRANCH_CITY>"));
						sRelationshipType = sOutput.substring(sOutput.indexOf("<RELATIONSHIP_TYPE>")+"<RELATIONSHIP_TYPE>".length(),sOutput.indexOf("</RELATIONSHIP_TYPE>"));
						
						sTransferable = sOutput.substring(sOutput.indexOf("<TRANSFERABLE>")+"<TRANSFERABLE>".length(),sOutput.indexOf("</TRANSFERABLE>"));
						sCustInstr = sOutput.substring(sOutput.indexOf("<CONFN_INTRN>")+"<CONFN_INTRN>".length(),sOutput.indexOf("</CONFN_INTRN>"));
						sPlaceOfExpiry = sOutput.substring(sOutput.indexOf("<EXP_PLACE>")+"<EXP_PLACE>".length(),sOutput.indexOf("</EXP_PLACE>"));
						sCustomerRefNo = sOutput.substring(sOutput.indexOf("<CUST_REF_NO>")+"<CUST_REF_NO>".length(),sOutput.indexOf("</CUST_REF_NO>"));
						sTransShipment = sOutput.substring(sOutput.indexOf("<TRANSSHIPMENT>")+"<TRANSSHIPMENT>".length(),sOutput.indexOf("</TRANSSHIPMENT>"));
						sShipmentPeriod = sOutput.substring(sOutput.indexOf("<SHIPMENT_PERIOD>")+"<SHIPMENT_PERIOD>".length(),sOutput.indexOf("</SHIPMENT_PERIOD>"));
						sPartialShipment = sOutput.substring(sOutput.indexOf("<PARTIAL_SHIPMENT>")+"<PARTIAL_SHIPMENT>".length(),sOutput.indexOf("</PARTIAL_SHIPMENT>"));
						sPartialShipmentDets = sOutput.substring(sOutput.indexOf("<PARTIAL_SHIPMENT_CONDITIONAL>")+"<PARTIAL_SHIPMENT_CONDITIONAL>".length(),sOutput.indexOf("</PARTIAL_SHIPMENT_CONDITIONAL>"));
						sTransshipmentsDtls = sOutput.substring(sOutput.indexOf("<TRANSSHIPMENT_CONDITIONAL>")+"<TRANSSHIPMENT_CONDITIONAL>".length(),sOutput.indexOf("</TRANSSHIPMENT_CONDITIONAL>"));
						sChargesFromBenef = sOutput.substring(sOutput.indexOf("<CHARGE_TYPE>")+"<CHARGE_TYPE>".length(),sOutput.indexOf("</CHARGE_TYPE>"));
						sIncoTerm = sOutput.substring(sOutput.indexOf("<INCOTERMS>")+"<INCOTERMS>".length(),sOutput.indexOf("</INCOTERMS>"));
						sPeriodPresentationDays = sOutput.substring(sOutput.indexOf("<PERIOD_PRESENT_DAYS>")+"<PERIOD_PRESENT_DAYS>".length(),sOutput.indexOf("</PERIOD_PRESENT_DAYS>"));
						String sAccountNumber  = sOutput.substring(sOutput.indexOf("<ACCOUNT_NUMBER>")+"<ACCOUNT_NUMBER>".length(),sOutput.indexOf("</ACCOUNT_NUMBER>"));
						sCreditMode=sOutput.substring(sOutput.indexOf("<CREDIT_MODE>")+"<CREDIT_MODE>".length(),sOutput.indexOf("</CREDIT_MODE>"));
						sSpecialPaymentConditionForBeneficiary=sOutput.substring(sOutput.indexOf("<SPEC_PAYMENT_CONDN>")+"<SPEC_PAYMENT_CONDN>".length(),sOutput.indexOf("</SPEC_PAYMENT_CONDN>"));
						sRequestedConfirmationParty=sOutput.substring(sOutput.indexOf("<REQ_CONF_PARTY>")+"<REQ_CONF_PARTY>".length(),sOutput.indexOf("</REQ_CONF_PARTY>"));

						//sDraftTenor = sOutput.substring(sOutput.indexOf("<DRAFT_TENOR>")+"<DRAFT_TENOR>".length(),sOutput.indexOf("</DRAFT_TENOR>"));
						//sCreditDaysFrom = sOutput.substring(sOutput.indexOf("<DRAFTCREDITDAYS_DAYS>")+"<DRAFTCREDITDAYS_DAYS>".length(),sOutput.indexOf("</DRAFTCREDITDAYS_DAYS>"));
						//sDrawee = sOutput.substring(sOutput.indexOf("<DRAFT_DRAWEE_BANK>")+"<DRAFT_DRAWEE_BANK>".length(),sOutput.indexOf("</DRAFT_DRAWEE_BANK>"));
						//sDraftAmount = sOutput.substring(sOutput.indexOf("<DRAFT_AMOUNT>")+"<DRAFT_AMOUNT>".length(),sOutput.indexOf("</DRAFT_AMOUNT>"));
						//sDraftSpecifyOthers = sOutput.substring(sOutput.indexOf("<DRAFT_SPECIFY_OTHERS>")+"<DRAFT_SPECIFY_OTHERS>".length(),sOutput.indexOf("</DRAFT_SPECIFY_OTHERS>"));
						String addCond=sOutput.substring(sOutput.indexOf("<ADDN_CONDITION>")+"<ADDN_CONDITION>".length(),sOutput.indexOf("</ADDN_CONDITION>"));
						String sTrnThirdParty=sOutput.substring(sOutput.indexOf("<TRN_THIRD_PARTY>")+"<TRN_THIRD_PARTY>".length(),sOutput.indexOf("</TRN_THIRD_PARTY>"));
						String sPresentationNarrative=sOutput.substring(sOutput.indexOf("<PERIOD_PRESENT_MODE>")+"<PERIOD_PRESENT_MODE>".length(),sOutput.indexOf("</PERIOD_PRESENT_MODE>"));
						String sPaymentDate=sOutput.substring(sOutput.indexOf("<DEFERRED_PAYMENT>")+"<DEFERRED_PAYMENT>".length(),sOutput.indexOf("</DEFERRED_PAYMENT>"));
						String sUdfFieldValue1 = sOutput.substring(sOutput.indexOf("<IS_TS_REQUIRED>")+"<IS_TS_REQUIRED>".length(),sOutput.indexOf("</IS_TS_REQUIRED>"));
						
						sDocDetls = getDocumentDetailsforCreateAmend(sWIName,sRequestTypeCode);
						sDraftDetls  = getDraftDetailsforCreateAmend(sWIName);
						
						
						//getDocumentDetailsforCreateAmend(sWIName);
		
						if("1".equalsIgnoreCase(sTrnThirdParty))    //1-yes
						{
						customerType = "ACC";
						}else{
							customerType = "APP";
						}
						WriteToLog_showpage("Y"," ***************customerType: "+customerType+" *****************************");
						
						String sMayConfirm = "N";
						if("MA".equalsIgnoreCase(sCustInstr)){
							sMayConfirm = "Y";
						}else{
							sMayConfirm = "N";
						}
						WriteToLog_showpage("Y"," ***************sMayConfirm: "+sMayConfirm+" *****************************");
						
						if("".equalsIgnoreCase(sTransferable)){
							sTransferable = "N";
						}
						
						
						
						/*if(!"".equalsIgnoreCase(sDraftTenor) || !"".equalsIgnoreCase(sCreditDaysFrom) || !"".equalsIgnoreCase(sDrawee) ||!"".equalsIgnoreCase(sDraftAmount)){
							sDraftSerialNo = "1";
						}
						WriteToLog_showpage("Y"," ***************sDraftSerialNo: "+sDraftSerialNo+" *****************************");*/
										
						/*if("Others".equalsIgnoreCase(sCreditDaysFrom)){
							sCreditDaysFrom = sDraftSpecifyOthers;
						}else if("".equalsIgnoreCase(sCreditDaysFrom) && "".equalsIgnoreCase(sDraftSpecifyOthers)){
							sCreditDaysFrom = "";
						}
						WriteToLog_showpage("Y"," ***************sCreditDaysFrom: "+sCreditDaysFrom+" *****************************");*/
										
						/*if(!"".equalsIgnoreCase(sDraftAmount)){
							sPercentageAmount = "A";
						}else{
							sPercentageAmount = "";
						}
						WriteToLog_showpage("Y"," ***************sPercentageAmount: "+sPercentageAmount+" *****************************");*/
						Boolean adviceFlag=false;
	                    StringBuilder adviceData = new StringBuilder();
						if(!"".equalsIgnoreCase(sPartialShipmentDets) &&  sPartialShipmentDets!=null){
							adviceFlag=true;
						}else if(!"".equalsIgnoreCase(sTransshipmentsDtls) &&  sTransshipmentsDtls!=null){
							adviceFlag=true;
						}else if(!"".equalsIgnoreCase(sSpecialPaymentConditionForBeneficiary) &&  sSpecialPaymentConditionForBeneficiary!=null){
							adviceFlag=true;
						}else if(!"".equalsIgnoreCase(addCond) &&  addCond!=null){
							adviceFlag=true;
						}
						if(adviceFlag){
							 adviceData.append("<adviceDetail>");
			                 adviceData.append("<messageType>"); adviceData.append("LC_INSTRUMENT"); adviceData.append("</messageType>");
			                 adviceData.append("<messageParty>"); adviceData.append("ABK"); adviceData.append("</messageParty>");
			                 String partyID=getPartyIdFromPartyType("'ABK'",sWIName,"ILC_NI");
		                     adviceData.append("<messagePartyId>"); adviceData.append(partyID); adviceData.append("</messagePartyId>");
                             adviceData.append("</adviceDetail>");
						}else{
							 adviceData.append("<adviceDetail>");
			                 adviceData.append("<messageType>"); adviceData.append(""); adviceData.append("</messageType>");
			                 adviceData.append("<messageParty>"); adviceData.append(""); adviceData.append("</messageParty>");
			                 adviceData.append("<messagePartyId>"); adviceData.append(""); adviceData.append("</messagePartyId>");
                             adviceData.append("</adviceDetail>");
						}
						//fetchAmendmentTabData(sWIName);
						limitSerialNumber = "1";
						limitPartyType = customerType;
						limitLinkageType = "F";
						limitPercentContribution = "100";
						limitAmountTag = "LIAB_OS_AMT";
						HashMap limitCreditRvwChecklist = getCreditReviwChecklistData(sRequestCategoryCode,sRequestTypeCode,sWIName);
						limitLineRefNumber = (String)limitCreditRvwChecklist.get("ILC_Lmt_line");
						String collateralPercent=(String)limitCreditRvwChecklist.get("ILC_Cash_Mrgn_Prcnt");
						sBranchCreateCode = fetchBranchCode(sRelationshipType , sWIName);
						WriteToLog_showpage("Y"," ***************sCallName: "+sCallName.toString()+" *****************************");
                        String sFreeFormatTextTag = "";
						if(!"".equals(addCond)){
							sFreeFormatTextTag = "<freeFormatTextDtl><freeFormatTextCode>OTHER</freeFormatTextCode><freeFormatTextDescription>"+addCond+ "</freeFormatTextDescription></freeFormatTextDtl>";
						}
						 if(!"".equals(sPartialShipmentDets)){
							sFreeFormatTextTag = sFreeFormatTextTag + "<freeFormatTextDtl><freeFormatTextCode>PARTSHMTCON</freeFormatTextCode><freeFormatTextDescription>" + sPartialShipmentDets + "</freeFormatTextDescription></freeFormatTextDtl>";
						}
						 if(!"".equals(sTransshipmentsDtls)){
							sFreeFormatTextTag = sFreeFormatTextTag + "<freeFormatTextDtl><freeFormatTextCode>TRANSSHMTCON</freeFormatTextCode><freeFormatTextDescription>" + sTransshipmentsDtls + "</freeFormatTextDescription></freeFormatTextDtl>";
						}
						if(!"".equals(sSpecialPaymentConditionForBeneficiary)){
							sFreeFormatTextTag = sFreeFormatTextTag + "<freeFormatTextDtl><freeFormatTextCode>49GSPLPMTBEN</freeFormatTextCode><freeFormatTextDescription>" + sSpecialPaymentConditionForBeneficiary + "</freeFormatTextDescription></freeFormatTextDtl>";
						}
						if("SWIFT".equalsIgnoreCase(sProcessType)){
							sPeriodPresentationDays=getSWIFTTXNDetails(sWIName,"PERIOD_PTN_DAY");
						}
						if(sCallName.equalsIgnoreCase("modTradeLCContract_Oper")){
							callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeLCContract_Oper</operationType><correlationID>342343</correlationID><modTradeLCContractReq><requestType>C</requestType><externalRefNumber>"+sExternalRefNumber+"</externalRefNumber><operationCode>" + sOperationCode + "</operationCode><transferable>" + sTransferable + "</transferable><settlementType>N</settlementType><settlementMonth>" + sCreditMode + "</settlementMonth><guaranteeType></guaranteeType><contractCurrency>" + sContractCurrency + "</contractCurrency><contractAmount>" + sContractAmount + "</contractAmount><negativeTolerance>" + sNegativeTolerance + "</negativeTolerance><positiveTolerance>" + sPositiveTolerance + "</positiveTolerance><benefConfirmation>N</benefConfirmation><expiryDate>" + sExpiryDate + "</expiryDate><placeOfExpiry>" + sPlaceOfExpiry + "</placeOfExpiry><issueDate>" + CurrDate + "</issueDate><closureType>A</closureType><customerType>" + customerType + "</customerType><customerId>" + sCustomerId + "</customerId><remark>"+setProTradeRemark(sWIName)+"</remark><customerRefNumber>" +sCustomerRefNo+ "</customerRefNumber><limitTracking>Y</limitTracking><chargesFromBenef>" + sChargesFromBenef+ " </chargesFromBenef><amendementFlag>N</amendementFlag><languageCode>ENG</languageCode><amendmentNumber>0</amendmentNumber><mayConfirm>" +sMayConfirm+ "</mayConfirm><effectiveDate>" + CurrDate + "</effectiveDate><limitTrackTenorType>L</limitTrackTenorType><productCode>" + sProductCode + "</productCode><incoTerm>" +sIncoTerm+ "</incoTerm><issuanceRequest></issuanceRequest><stopDate>" + sExpiryDate + "</stopDate><branchCode>" + sBranchCreateCode + "</branchCode><partyDetails>" + getPartyDetailsforCreateAmend(customerType,sRequestTypeCode,sWIName,"","") + "</partyDetails><partyOtherAddresses>" + getPartyOtherDetails(sWIName) + "</partyOtherAddresses><shipmentDetails><fromPlace>" + sFromPlace + "</fromPlace><toPlace>" + sToPlace + "</toPlace><lastShipmentDate>" + sLastShipmentDate + "</lastShipmentDate><transShipment>" + sTransShipment + "</transShipment><shipmentPeriod>" +sShipmentPeriod+ "</shipmentPeriod><portOfDischarge>" + sPortOfDischarge + "</portOfDischarge><portOfLoading>" + sPortOfLoading + "</portOfLoading><partialShipment>" +sPartialShipment+ "</partialShipment><partialShipmentDetails></partialShipmentDetails><transshipmentDetails></transshipmentDetails></shipmentDetails><documentDetails>" +sDocDetls+ "</documentDetails><goodsDetails><goodsCode>OTHERS</goodsCode><goodsDescription>" + sGoodsDescription + "</goodsDescription></goodsDetails><txnUDFDetails><UDFFieldName>TXN_ISSUING_CENTER</UDFFieldName><udfFieldValue>" + sUdfFieldValue + "</udfFieldValue><UDFFieldName>AUTO_DOCCHK_REQ</UDFFieldName><udfFieldValue>" + sUdfFieldValue1 + "</udfFieldValue></txnUDFDetails><contractLimits><contractLimit><limitSerialNumber>" + limitSerialNumber + "</limitSerialNumber><limitPartyType>" + limitPartyType + "</limitPartyType><limitLinkageType>" + limitLinkageType + "</limitLinkageType><limitPercentContribution>" + limitPercentContribution + "</limitPercentContribution><limitAmountTag>" + limitAmountTag + "</limitAmountTag><limitLineCustomerId>" + this.limitLineCustomerId + "</limitLineCustomerId><limitLineRefNumber>" + limitLineRefNumber + "</limitLineRefNumber></contractLimit></contractLimits><drafts>" +sDraftDetls+ "</drafts><collateralDetails><collateralCurrency>"+sContractCurrency+"</collateralCurrency><collateralPercent>"+collateralPercent+"</collateralPercent></collateralDetails><provisionCus><provisionForReceivableComponent>Y</provisionForReceivableComponent></provisionCus><forceDebitCus><forceDebitAccount>Y</forceDebitAccount></forceDebitCus><wmsDetails><workItemNumber>" + sWIName + "</workItemNumber></wmsDetails><presentationPeriod>" + sPeriodPresentationDays + "</presentationPeriod><adviceDetails>"+adviceData.toString()+"</adviceDetails><Account>"+sAccountNumber+"</Account><requestedConfirmationParty>" + sRequestedConfirmationParty + "</requestedConfirmationParty><contrMstr><counterGuaranteeExpiryDate></counterGuaranteeExpiryDate></contrMstr><freeFormatTextDtls>"+sFreeFormatTextTag+"</freeFormatTextDtls><presentationNarrative>"+sPresentationNarrative+"</presentationNarrative><paymentDate>"+sPaymentDate+"</paymentDate></modTradeLCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";							WriteToLog_showpage("Y","modTradeLCContract_Oper sOutput: "+ callXML);
							sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sCallName +"',"+ convertToPlainString(callXML) +",'"+ REFNO +"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","modTradeLCContract_Oper callXML TFO_DJ_INTEGRATION_CALLS_DTLS: "+ sQuery + " *****************************");	
							sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;						
							WriteToLog_showpage("Y"," insert modTradeLCContract_Oper sOutput: "+ sOutput);											
							
							sOutput = socket.connectToSocket(callXML);
							WriteToLog_showpage("Y","modTradeLCContract_Oper callXML sOutput"+ sOutput);
							
							String queryUpdateXML = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'" + sOutput + "'","WI_NAME = '"+ sWIName +"'  and REF_NUM = '"+REFNO+"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","update sOutputUpdateXML input: "+ queryUpdateXML);
							String sOutputUpdateXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",queryUpdateXML);;
							WriteToLog_showpage("Y","update sOutputUpdateXML output: " + sOutputUpdateXML);
							
							xp = new XMLParser(sOutput);
							returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
							String nextValue ="";
							if(returnCode == 0){
								sErrorDescription = xp.getValueOf("errorDescription");
								sContractRefNumber = xp.getValueOf("contractRefNumber");
								sResponse = "Success";
								sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS,TRANSACTION_REFERENCE","'" + sResponse + "','" + sContractRefNumber + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
								out.println(returnCode+"#"+sResponse+"#"+sErrorDescription);
							} else {
								sErrorDescription = xp.getValueOf("errorDescription");
								sResponse = "Failure";
								//sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS,TRANSACTION_REFERENCE","'" + sResponse + "','" + "353343" + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);

								sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
								out.println("1#"+sResponse+"#"+sErrorDescription);
							}
						}
					}else if("ILC".equalsIgnoreCase(sRequestCategoryCode) && "ILC_UM".equalsIgnoreCase(sRequestTypeCode))
					{
						sExternalRefNumber = sOutput.substring(sOutput.indexOf("<PRO_TRADE_REF_NO>")+"<PRO_TRADE_REF_NO>".length(),sOutput.indexOf("</PRO_TRADE_REF_NO>"));
						sOperationCode = sOutput.substring(sOutput.indexOf("<OPERATION_CODE>")+"<OPERATION_CODE>".length(),sOutput.indexOf("</OPERATION_CODE>"));
						sContractCurrency = sOutput.substring(sOutput.indexOf("<TRANSACTION_CURRENCY>")+"<TRANSACTION_CURRENCY>".length(),sOutput.indexOf("</TRANSACTION_CURRENCY>"));
						sContractAmount = sOutput.substring(sOutput.indexOf("<TRANSACTION_AMOUNT>")+"<TRANSACTION_AMOUNT>".length(),sOutput.indexOf("</TRANSACTION_AMOUNT>"));
						sNegativeTolerance = sOutput.substring(sOutput.indexOf("<LC_UNDER>")+"<LC_UNDER>".length(),sOutput.indexOf("</LC_UNDER>"));
						sPositiveTolerance = sOutput.substring(sOutput.indexOf("<LC_ABOVE>")+"<LC_ABOVE>".length(),sOutput.indexOf("</LC_ABOVE>"));
						sExpiryDate = sOutput.substring(sOutput.indexOf("<EXP_DATE>")+"<EXP_DATE>".length(),sOutput.indexOf("</EXP_DATE>"));
						sCustomerId = sOutput.substring(sOutput.indexOf("<CUSTOMER_ID>")+"<CUSTOMER_ID>".length(),sOutput.indexOf("</CUSTOMER_ID>"));
						sProductCode = sOutput.substring(sOutput.indexOf("<PRODUCT_TYPE>")+"<PRODUCT_TYPE>".length(),sOutput.indexOf("</PRODUCT_TYPE>"));
						sFromPlace ="";
						sToPlace = "";
						sLastShipmentDate = "";
						sPortOfDischarge ="";
						sPortOfLoading ="";
						sGoodsDescription = sOutput.substring(sOutput.indexOf("<LC_GOODS_DESC>")+"<LC_GOODS_DESC>".length(),sOutput.indexOf("</LC_GOODS_DESC>"));
						sUdfFieldValue = sOutput.substring(sOutput.indexOf("<BRANCH_CITY>")+"<BRANCH_CITY>".length(),sOutput.indexOf("</BRANCH_CITY>"));
						sRelationshipType = sOutput.substring(sOutput.indexOf("<RELATIONSHIP_TYPE>")+"<RELATIONSHIP_TYPE>".length(),sOutput.indexOf("</RELATIONSHIP_TYPE>"));
						 expiryType =  sOutput.substring(sOutput.indexOf("<TRN_TENOR>")+"<TRN_TENOR>".length(),sOutput.indexOf("</TRN_TENOR>"));
                        
						if("Fixed".equalsIgnoreCase(expiryType)){
							expiryType ="LIMT";
						}else if("Open".equalsIgnoreCase(expiryType)){
							expiryType ="UNLM";
						}else if("Conditional ".equalsIgnoreCase(expiryType)){
							expiryType ="COND";
						}
						customerType = "APP";
						limitSerialNumber = "1";
						limitPartyType = "APP";
						limitLinkageType = "F";
						limitPercentContribution = "100";
						limitAmountTag = "LIAB_OS_AM";
						
						HashMap limitCreditRvwChecklist = getCreditReviwChecklistData(sRequestCategoryCode,sRequestTypeCode,sWIName);
						limitLineRefNumber = (String)limitCreditRvwChecklist.get("ILC_Lmt_line");
						sBranchCreateCode = fetchBranchCode(sRelationshipType , sWIName);
						
						if(sCallName.equalsIgnoreCase("modTradeLCContract_Oper")){
							callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeLCContract_Oper</operationType><correlationID>342343</correlationID><modTradeLCContractReq><requestType>C</requestType><externalRefNumber></externalRefNumber><operationCode>" + sOperationCode + "</operationCode><transferable>N</transferable><settlementType>N</settlementType><guaranteeType>"+"</guaranteeType><contractCurrency>" + sContractCurrency + "</contractCurrency><contractAmount>" + sContractAmount + "</contractAmount><negativeTolerance></negativeTolerance><positiveTolerance></positiveTolerance><benefConfirmation>N</benefConfirmation><expiryDate>" + sExpiryDate + "</expiryDate><issueDate>" + CurrDate + "</issueDate><closureType>M</closureType><customerType>" + customerType + "</customerType><customerId>" + sCustomerId + "</customerId><remark>"+setProTradeRemark(sWIName)+"</remark><customerName></customerName><customerRefNumber>"+ "</customerRefNumber><limitTracking>N</limitTracking><amendementFlag>N</amendementFlag><languageCode>ENG</languageCode><amendmentNumber>0</amendmentNumber><mayConfirm>N</mayConfirm><effectiveDate>" + CurrDate + "</effectiveDate><limitTrackTenorType>L</limitTrackTenorType><productCode>" + sProductCode + "</productCode><issuanceRequest></issuanceRequest><stopDate>" + sExpiryDate + "</stopDate><branchCode>" + sBranchCreateCode + "</branchCode><partyDetails>" + getPartyDetailsforCreateAmend(customerType,sRequestTypeCode,sWIName,"","") + "</partyDetails><shipmentDetails><fromPlace>" + sFromPlace + "</fromPlace><toPlace>" + sToPlace + "</toPlace><lastShipmentDate>" + sLastShipmentDate + "</lastShipmentDate><portOfDischarge>" + sPortOfDischarge + "</portOfDischarge><portOfLoading>" + sPortOfLoading + "</portOfLoading></shipmentDetails><goodsDetails><goodsCode></goodsCode><goodsDescription></goodsDescription></goodsDetails><txnUDFDetails><UDFFieldName>TXN_ISSUING_CENTER</UDFFieldName><udfFieldValue>" + sUdfFieldValue + "</udfFieldValue></txnUDFDetails><collateralDetails><collateralCurrency></collateralCurrency><collateralPercent></collateralPercent></collateralDetails><provisionCus><provisionForReceivableComponent>Y</provisionForReceivableComponent></provisionCus><forceDebitCus><forceDebitAccount>Y</forceDebitAccount></forceDebitCus><wmsDetails><workItemNumber>" + sWIName + "</workItemNumber></wmsDetails><contrMstr><counterGuaranteeExpiryDate></counterGuaranteeExpiryDate></contrMstr><expiryType>"+expiryType+"</expiryType></modTradeLCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";
							WriteToLog_showpage("Y","modTradeLCContract_Oper sOutput: "+ callXML);
							sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sCallName +"',"+ convertToPlainString(callXML) +",'"+ REFNO +"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","modTradeLCContract_Oper callXML TFO_DJ_INTEGRATION_CALLS_DTLS: "+ sQuery + " *****************************");	
							sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;						
							WriteToLog_showpage("Y"," insert modTradeLCContract_Oper sOutput: "+ sOutput);											
							
							sOutput = socket.connectToSocket(callXML);
							WriteToLog_showpage("Y","modTradeLCContract_Oper callXML sOutput"+ sOutput);
							
							String queryUpdateXML = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'" + sOutput + "'","WI_NAME = '"+ sWIName +"'  and REF_NUM = '"+REFNO+"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","update sOutputUpdateXML input: "+ queryUpdateXML);
							String sOutputUpdateXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",queryUpdateXML);;
							WriteToLog_showpage("Y","update sOutputUpdateXML output: " + sOutputUpdateXML);
							
							xp = new XMLParser(sOutput);
							returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
							String nextValue ="";
							if(returnCode == 0){
								sErrorDescription = xp.getValueOf("errorDescription");
								sContractRefNumber = xp.getValueOf("contractRefNumber");
								sResponse = "Success";
								sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS,TRANSACTION_REFERENCE","'" + sResponse + "','" + sContractRefNumber + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
								out.println(returnCode+"#"+sResponse+"#"+sErrorDescription);
							} else {
								sErrorDescription = xp.getValueOf("errorDescription");
								sResponse = "Failure";
								sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
								out.println("1#"+sResponse+"#"+sErrorDescription);
							}
					}
					}else if("GRNT".equalsIgnoreCase(sRequestCategoryCode) && "NI".equalsIgnoreCase(sRequestTypeCode))
					{
						String sDocDetls="";
						sExternalRefNumber = sOutput.substring(sOutput.indexOf("<PRO_TRADE_REF_NO>")+"<PRO_TRADE_REF_NO>".length(),sOutput.indexOf("</PRO_TRADE_REF_NO>"));
						sOperationCode = sOutput.substring(sOutput.indexOf("<OPERATION_CODE>")+"<OPERATION_CODE>".length(),sOutput.indexOf("</OPERATION_CODE>"));
						sContractCurrency = sOutput.substring(sOutput.indexOf("<TRANSACTION_CURRENCY>")+"<TRANSACTION_CURRENCY>".length(),sOutput.indexOf("</TRANSACTION_CURRENCY>"));
						sContractAmount = sOutput.substring(sOutput.indexOf("<TRANSACTION_AMOUNT>")+"<TRANSACTION_AMOUNT>".length(),sOutput.indexOf("</TRANSACTION_AMOUNT>"));
						sNegativeTolerance = sOutput.substring(sOutput.indexOf("<LC_UNDER>")+"<LC_UNDER>".length(),sOutput.indexOf("</LC_UNDER>"));
						sPositiveTolerance = sOutput.substring(sOutput.indexOf("<LC_ABOVE>")+"<LC_ABOVE>".length(),sOutput.indexOf("</LC_ABOVE>"));
						sExpiryDate = sOutput.substring(sOutput.indexOf("<EXP_DATE>")+"<EXP_DATE>".length(),sOutput.indexOf("</EXP_DATE>"));
						sCustomerId = sOutput.substring(sOutput.indexOf("<CUSTOMER_ID>")+"<CUSTOMER_ID>".length(),sOutput.indexOf("</CUSTOMER_ID>"));
						sProductCode = sOutput.substring(sOutput.indexOf("<PRODUCT_TYPE>")+"<PRODUCT_TYPE>".length(),sOutput.indexOf("</PRODUCT_TYPE>"));
						sFromPlace = sOutput.substring(sOutput.indexOf("<LC_FROM_PLACE>")+"<LC_FROM_PLACE>".length(),sOutput.indexOf("</LC_FROM_PLACE>"));
						sToPlace = sOutput.substring(sOutput.indexOf("<LC_TO_PLACE>")+"<LC_TO_PLACE>".length(),sOutput.indexOf("</LC_TO_PLACE>"));
						sLastShipmentDate = sOutput.substring(sOutput.indexOf("<LC_LTST_SHIPMNT_DATE>")+"<LC_LTST_SHIPMNT_DATE>".length(),sOutput.indexOf("</LC_LTST_SHIPMNT_DATE>"));
						sPortOfDischarge = sOutput.substring(sOutput.indexOf("<LC_PORT_OF_DISCHRG>")+"<LC_PORT_OF_DISCHRG>".length(),sOutput.indexOf("</LC_PORT_OF_DISCHRG>"));
						sPortOfLoading = sOutput.substring(sOutput.indexOf("<LC_PORT_OF_LOADING>")+"<LC_PORT_OF_LOADING>".length(),sOutput.indexOf("</LC_PORT_OF_LOADING>"));
						sGoodsDescription = sOutput.substring(sOutput.indexOf("<LC_GOODS_DESC>")+"<LC_GOODS_DESC>".length(),sOutput.indexOf("</LC_GOODS_DESC>"));
						sUdfFieldValue = sOutput.substring(sOutput.indexOf("<BRANCH_CITY>")+"<BRANCH_CITY>".length(),sOutput.indexOf("</BRANCH_CITY>"));
						sRelationshipType = sOutput.substring(sOutput.indexOf("<RELATIONSHIP_TYPE>")+"<RELATIONSHIP_TYPE>".length(),sOutput.indexOf("</RELATIONSHIP_TYPE>"));
						String counterGuaranteeExpiryDate=sOutput.substring(sOutput.indexOf("<GRNTEE_CNTR_EXP_DATE>")+"<GRNTEE_CNTR_EXP_DATE>".length(),sOutput.indexOf("</GRNTEE_CNTR_EXP_DATE>"));
						
						String txnThirdParty=sOutput.substring(sOutput.indexOf("<TRN_THIRD_PARTY>")+"<TRN_THIRD_PARTY>".length(),sOutput.indexOf("</TRN_THIRD_PARTY>"));
						String sAccountNumber  = sOutput.substring(sOutput.indexOf("<ACCOUNT_NUMBER>")+"<ACCOUNT_NUMBER>".length(),sOutput.indexOf("</ACCOUNT_NUMBER>"));
						String sEffectiveDate  = sOutput.substring(sOutput.indexOf("<EFFECTIVE_DATE>")+"<EFFECTIVE_DATE>".length(),sOutput.indexOf("</EFFECTIVE_DATE>"));
						expiryType =  sOutput.substring(sOutput.indexOf("<TRN_TENOR>")+"<TRN_TENOR>".length(),sOutput.indexOf("</TRN_TENOR>"));
						purposeOfMessage = sOutput.substring(sOutput.indexOf("<FCUBS_PUR_OF_MSG>")+"<FCUBS_PUR_OF_MSG>".length(),sOutput.indexOf("</FCUBS_PUR_OF_MSG>"));
						transferConditions =sOutput.substring(sOutput.indexOf("<TRANSFER_CONDITIONS>")+"<TRANSFER_CONDITIONS>".length(),sOutput.indexOf("</TRANSFER_CONDITIONS>"));
						transferable = sOutput.substring(sOutput.indexOf("<TRANSFER_INDICATOR>")+"<TRANSFER_INDICATOR>".length(),sOutput.indexOf("</TRANSFER_INDICATOR>"));
					    additionalAmountInformation =sOutput.substring(sOutput.indexOf("<ADDN_AMT_INFO>")
					       +"<ADDN_AMT_INFO>".length(),sOutput.indexOf("</ADDN_AMT_INFO>"));
						purposeOfGtee =sOutput.substring(sOutput.indexOf("<PUR_OF_GTEE>")+"<PUR_OF_GTEE>".length(),sOutput.indexOf("</PUR_OF_GTEE>"));
						expiryConditionOrEvent =sOutput.substring(sOutput.indexOf("<EXPIRY_COND>")+"<EXPIRY_COND>".length(),sOutput.indexOf("</EXPIRY_COND>"));
						
						if("FD".equalsIgnoreCase(expiryType)){
							expiryType ="LIMT";
						}else if("OE".equalsIgnoreCase(expiryType)){
							expiryType ="UNLM";
						}else if("COND".equalsIgnoreCase(expiryType)){
							expiryType ="COND";
						}
						
						if("Request".equalsIgnoreCase(purposeOfMessage)){
							purposeOfMessage ="R";
						}else if("Issue".equalsIgnoreCase(purposeOfMessage)){
							purposeOfMessage ="I";
						}else{
							purposeOfMessage ="";
						}
						
						if("true".equalsIgnoreCase(transferable)){
							transferable ="Y";
						}else if("false".equalsIgnoreCase(transferable)){
							transferable ="N";
						}else{
							transferable ="";
						}
						WriteToLog_showpage("Y","transferable : =: "+ transferable);
						if("".equalsIgnoreCase(sEffectiveDate)){
							sEffectiveDate = CurrDate;
						}
						HashMap limitCreditRvwChecklist = getCreditReviwChecklistData(sRequestCategoryCode,sRequestTypeCode,sWIName);
						String collateralPercent = (String)limitCreditRvwChecklist.get("Cash_mrgn_Perc");
						
						//ATP-376 24-01-2024  REYAZ
						//START CODE 
						//String productList="T551,T552,T553,T554,T555,T556,T557,T558,T575";
						String productList="T551,T552,T553,T554,T555,T556,T557,T558,T575,T577,T578,T579,T580";
						//END CODE
						WriteToLog_showpage("Y","productData=: "+ Arrays.asList(productList.split(",")));
						List productData=Arrays.asList(productList.split(","));
						WriteToLog_showpage("Y","productData=: "+ productData);
						
						 if (productData.contains(sProductCode)) {customerType = "APB";
						 WriteToLog_showpage("Y","customerType1=: "+ customerType);}
						 else if("1".equalsIgnoreCase(txnThirdParty))    //1-yes
						{
							customerType = "OBP";
						}else{
							customerType = "APP";
						
						}
						WriteToLog_showpage("Y","customerType=: "+ customerType);
						limitSerialNumber = "1";
						limitPartyType =customerType;
						limitLinkageType = "F";
						limitPercentContribution = "100";
						limitAmountTag = "LIAB_OS_AMT";
						limitLineRefNumber = (String)limitCreditRvwChecklist.get("Limt_Line");;
						sBranchCreateCode = fetchBranchCode(sRelationshipType , sWIName);
						
						String fftDettails="";
						fftDettails=FetchFFTData(sWIName);
						if("SWIFT".equalsIgnoreCase(sProcessType) && "Y".equalsIgnoreCase(sSWIFT_UTILITY_FLAG) )//WORK FOR CASES FOR WHICH MAPPING IN MAP 
						{
							WriteToLog_showpage("Y"," calling SetSwiftMQWIFFtData");
							fftDettails  = SetSwiftMQWIFFtData(sWIName,fftDettails);
						}
						if(!"".equalsIgnoreCase(purposeOfGtee)){
							fftDettails=fftDettails+"<freeFormatTextDtl><freeFormatTextCode>45LTRNDTLS</freeFormatTextCode>"+
							"<freeFormatTextDescription>"+purposeOfGtee+"</freeFormatTextDescription></freeFormatTextDtl>";
							fftDettails=fftDettails+"<freeFormatTextDtl><freeFormatTextCode>45LTRNDTSEQC</freeFormatTextCode>"+
							"<freeFormatTextDescription>"+purposeOfGtee+"</freeFormatTextDescription></freeFormatTextDtl>";
						}
						String adviceDetails=fetchAdviceDetails(sWIName,"NI");
						termsAndCondDetails= fetchTermsDetails(sWIName);
						sDocDetls =getDocumentDetailsforCreateAmend(sWIName,sRequestTypeCode);
						if(sCallName.equalsIgnoreCase("modTradeLCContract_Oper")){
							if("".equalsIgnoreCase(limitLineRefNumber)){
							callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeLCContract_Oper</operationType><correlationID>342343</correlationID><modTradeLCContractReq><requestType>C</requestType><externalRefNumber>" + sExternalRefNumber + "</externalRefNumber><operationCode>" + sOperationCode + "</operationCode><settlementType></settlementType><guaranteeType>"+getProductGTEETypeCode(sProductCode,sRequestCategoryCode,sRequestTypeCode)+"</guaranteeType><contractCurrency>" + sContractCurrency + "</contractCurrency><contractAmount>" + sContractAmount + "</contractAmount><negativeTolerance></negativeTolerance><positiveTolerance></positiveTolerance><benefConfirmation>N</benefConfirmation><expiryDate>" + sExpiryDate + "</expiryDate><issueDate>" + CurrDate + "</issueDate><closureType>M</closureType><customerType>" + customerType + "</customerType><customerId>" + sCustomerId + "</customerId><customerRefNumber></customerRefNumber><limitTracking>N</limitTracking><amendementFlag>N</amendementFlag><languageCode>ENG</languageCode><amendmentNumber>0</amendmentNumber><mayConfirm>N</mayConfirm><effectiveDate>" + sEffectiveDate + "</effectiveDate><limitTrackTenorType>L</limitTrackTenorType><productCode>" + sProductCode + "</productCode><issuanceRequest>R</issuanceRequest><stopDate>" + sExpiryDate + "</stopDate><branchCode>" + sBranchCreateCode + "</branchCode><partyDetails>" + getPartyDetailsforCreateAmend(customerType,sRequestTypeCode,sWIName,sProcessType,sSWIFT_UTILITY_FLAG) + "</partyDetails><partyOtherAddresses>" + getPartyOtherDetails(sWIName) + "</partyOtherAddresses><remark>"+setProTradeRemark(sWIName)+"</remark><shipmentDetails><fromPlace></fromPlace><toPlace></toPlace><lastShipmentDate></lastShipmentDate><portOfDischarge></portOfDischarge><portOfLoading></portOfLoading></shipmentDetails><documentDetails>" +sDocDetls+ "</documentDetails><goodsDetails><goodsCode></goodsCode><goodsDescription></goodsDescription></goodsDetails><txnUDFDetails><UDFFieldName>GTEE_ISSUED_AT</UDFFieldName><udfFieldValue>" + sUdfFieldValue + "</udfFieldValue></txnUDFDetails>"+
							"<contrMstr><counterGuaranteeExpiryDate>" + counterGuaranteeExpiryDate + "</counterGuaranteeExpiryDate></contrMstr>"+
							"<collateralDetails><collateralCurrency>"+sContractCurrency+"</collateralCurrency><collateralPercent>"+collateralPercent+"</collateralPercent></collateralDetails>"+"<provisionCus><provisionForReceivableComponent>Y</provisionForReceivableComponent></provisionCus><forceDebitCus><forceDebitAccount>Y</forceDebitAccount></forceDebitCus><wmsDetails><workItemNumber>" + sWIName + "</workItemNumber></wmsDetails><freeFormatTextDtls>"+fftDettails+"</freeFormatTextDtls><adviceDetails>"+adviceDetails+"</adviceDetails><Account>"+sAccountNumber+"</Account><expiryType>"+expiryType+"</expiryType><purposeOfMessage>"+purposeOfMessage+"</purposeOfMessage><transferConditions>"+transferConditions+"</transferConditions><transferable>"+transferable+"</transferable><additionalAmountInformation>"+additionalAmountInformation+"</additionalAmountInformation><expiryConditionOrEvent>"+expiryConditionOrEvent+"</expiryConditionOrEvent><termsAndCondDtls>"+termsAndCondDetails+"</termsAndCondDtls></modTradeLCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";
						}
						else{
						
							callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeLCContract_Oper</operationType><correlationID>342343</correlationID><modTradeLCContractReq><requestType>C</requestType><externalRefNumber>" + sExternalRefNumber + "</externalRefNumber><operationCode>" + sOperationCode + "</operationCode><settlementType></settlementType><guaranteeType>"+getProductGTEETypeCode(sProductCode,sRequestCategoryCode,sRequestTypeCode)+"</guaranteeType><contractCurrency>" + sContractCurrency + "</contractCurrency><contractAmount>" + sContractAmount + "</contractAmount><negativeTolerance></negativeTolerance><positiveTolerance></positiveTolerance><benefConfirmation>N</benefConfirmation><expiryDate>" + sExpiryDate + "</expiryDate><issueDate>" + CurrDate + "</issueDate><closureType>M</closureType><customerType>" + customerType + "</customerType><customerId>" + sCustomerId + "</customerId><customerRefNumber></customerRefNumber><limitTracking>Y</limitTracking><amendementFlag>N</amendementFlag><languageCode>ENG</languageCode><amendmentNumber>0</amendmentNumber><mayConfirm>N</mayConfirm><effectiveDate>" + sEffectiveDate + "</effectiveDate><limitTrackTenorType>L</limitTrackTenorType><productCode>" + sProductCode + "</productCode><issuanceRequest>R</issuanceRequest><stopDate>" + sExpiryDate + "</stopDate><branchCode>" + sBranchCreateCode + "</branchCode><partyDetails>" + getPartyDetailsforCreateAmend(customerType,sRequestTypeCode,sWIName,sProcessType,sSWIFT_UTILITY_FLAG) + "</partyDetails><partyOtherAddresses>" + getPartyOtherDetails(sWIName) + "</partyOtherAddresses><remark>"+setProTradeRemark(sWIName)+"</remark><shipmentDetails><fromPlace></fromPlace><toPlace></toPlace><lastShipmentDate></lastShipmentDate><portOfDischarge></portOfDischarge><portOfLoading></portOfLoading></shipmentDetails><documentDetails>" +sDocDetls+ "</documentDetails><goodsDetails><goodsCode></goodsCode><goodsDescription></goodsDescription></goodsDetails><txnUDFDetails><UDFFieldName>GTEE_ISSUED_AT</UDFFieldName><udfFieldValue>" + sUdfFieldValue + "</udfFieldValue></txnUDFDetails><contractLimits><contractLimit><limitSerialNumber>" + limitSerialNumber + "</limitSerialNumber><limitPartyType>" + limitPartyType + "</limitPartyType><limitLinkageType>" + limitLinkageType + "</limitLinkageType><limitPercentContribution>" + limitPercentContribution + "</limitPercentContribution><limitAmountTag>" + limitAmountTag + "</limitAmountTag><limitLineCustomerId>" + this.limitLineCustomerId + "</limitLineCustomerId><limitLineRefNumber>" + limitLineRefNumber + "</limitLineRefNumber></contractLimit></contractLimits>"+
							"<contrMstr><counterGuaranteeExpiryDate>" + counterGuaranteeExpiryDate + "</counterGuaranteeExpiryDate></contrMstr>"+
							"<collateralDetails><collateralCurrency>"+sContractCurrency+"</collateralCurrency><collateralPercent>"+collateralPercent+"</collateralPercent></collateralDetails>"+"<provisionCus><provisionForReceivableComponent>Y</provisionForReceivableComponent></provisionCus><forceDebitCus><forceDebitAccount>Y</forceDebitAccount></forceDebitCus><wmsDetails><workItemNumber>" + sWIName + "</workItemNumber></wmsDetails><freeFormatTextDtls>"+fftDettails+"</freeFormatTextDtls><adviceDetails>"+adviceDetails+"</adviceDetails><Account>"+sAccountNumber+"</Account><expiryType>"+expiryType+"</expiryType><purposeOfMessage>"+purposeOfMessage+"</purposeOfMessage><transferConditions>"+transferConditions+"</transferConditions><transferable>"+transferable+"</transferable><additionalAmountInformation>"+additionalAmountInformation+"</additionalAmountInformation><expiryConditionOrEvent>"+expiryConditionOrEvent+"</expiryConditionOrEvent><termsAndCondDtls>"+termsAndCondDetails+"</termsAndCondDtls></modTradeLCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";
						}
						//	callXML="<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype>"
                        //       + "<InnerCallType>LapsResponse</InnerCallType><SessionId>"+sSessionId+"</SessionId><EngineName>"+sCabname+"</EngineName>"
			               //    + "<PushMessage><ChannelResponse><referralAndLoanStatusMsg><header><mode>N</mode><channelName>TSLM</channelName><sysrefno>98765432</sysrefno><processName>TFO</processName><requestDateTime>18/10/2020 14:52:00</requestDateTime><correlationId>1606210868660</correlationId><version>1.0</version></header><referralAndLoanStatus><stage>P</stage><userId>ADCBAE:TESTMAKER</userId><wmsId>TF-00000001205-REQUEST</wmsId><tslmId>1021</tslmId><wiTxnStatus>Initiated</wiTxnStatus><referralList>    <referal><seqNo>1</seqNo><trxType>INVOICE</trxType><trxId>1024</trxId><cpcid>256012</cpcid><referralUnit>OP</referralUnit><referralDesc>Invoice referral</referralDesc><comment>Incorrect invoice tenor</comment>    </referal>    <referal><seqNo>2</seqNo><trxType>INVOICE</trxType><trxId>1025</trxId><cpcid>256013</cpcid><referralUnit>OP</referralUnit><referralDesc>Invoice referral</referralDesc><comment>Incorrect cp info</comment>    </referal></referralList></referralAndLoanStatus>"
			           //        + "</referralAndLoanStatusMsg></ChannelResponse></PushMessage></APWebService_Input>";
							WriteToLog_showpage("Y","modTradeLCContract_Oper sOutput: "+ callXML);
							sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sCallName +"',"+ convertToPlainString(callXML) +",'"+ REFNO +"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","modTradeLCContract_Oper callXML TFO_DJ_INTEGRATION_CALLS_DTLS: "+ sQuery + " *****************************");	
							sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;						
							WriteToLog_showpage("Y"," insert modTradeLCContract_Oper sOutput: "+ sOutput);	
											
							
							sOutput = socket.connectToSocket(callXML);
							WriteToLog_showpage("Y","modTradeLCContract_Oper callXML sOutput"+ sOutput);
							
							String queryUpdateXML = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'" + sOutput + "'","WI_NAME = '"+ sWIName +"'  and REF_NUM = '"+REFNO+"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","update sOutputUpdateXML input: "+ queryUpdateXML);
							String sOutputUpdateXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",queryUpdateXML);;
							WriteToLog_showpage("Y","update sOutputUpdateXML output: " + sOutputUpdateXML);
							
							xp = new XMLParser(sOutput);
							returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
							String nextValue ="";
							if(returnCode == 0){
								sErrorDescription = xp.getValueOf("errorDescription");
								sContractRefNumber = xp.getValueOf("contractRefNumber");
								sResponse = "Success";
								sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS,TRANSACTION_REFERENCE","'" + sResponse + "','" + sContractRefNumber + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
								out.println(returnCode+"#"+sResponse+"#"+sErrorDescription);
							} else {
								sErrorDescription = xp.getValueOf("errorDescription");
								sResponse = "Failure";
								sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
								out.println("1#"+sResponse+"#"+sErrorDescription);
							}
						}
					}else if("GRNT".equalsIgnoreCase(sRequestCategoryCode) && "GA".equalsIgnoreCase(sRequestTypeCode))
					{
						sExternalRefNumber = sOutput.substring(sOutput.indexOf("<PRO_TRADE_REF_NO>")+"<PRO_TRADE_REF_NO>".length(),sOutput.indexOf("</PRO_TRADE_REF_NO>"));
						sOperationCode = sOutput.substring(sOutput.indexOf("<OPERATION_CODE>")+"<OPERATION_CODE>".length(),sOutput.indexOf("</OPERATION_CODE>"));
						sContractCurrency = sOutput.substring(sOutput.indexOf("<TRANSACTION_CURRENCY>")+"<TRANSACTION_CURRENCY>".length(),sOutput.indexOf("</TRANSACTION_CURRENCY>"));
						sContractAmount = sOutput.substring(sOutput.indexOf("<TRANSACTION_AMOUNT>")+"<TRANSACTION_AMOUNT>".length(),sOutput.indexOf("</TRANSACTION_AMOUNT>"));
						sNegativeTolerance ="";
						sPositiveTolerance ="";
						sExpiryDate = sOutput.substring(sOutput.indexOf("<EXP_DATE>")+"<EXP_DATE>".length(),sOutput.indexOf("</EXP_DATE>"));
						sCustomerId = sOutput.substring(sOutput.indexOf("<CUSTOMER_ID>")+"<CUSTOMER_ID>".length(),sOutput.indexOf("</CUSTOMER_ID>"));
						sProductCode = sOutput.substring(sOutput.indexOf("<PRODUCT_TYPE>")+"<PRODUCT_TYPE>".length(),sOutput.indexOf("</PRODUCT_TYPE>"));
						sFromPlace = "";
						sToPlace = "";
						sLastShipmentDate = "";
						sPortOfDischarge = "";
						sPortOfLoading = "";
						sGoodsDescription ="";
						sUdfFieldValue = sOutput.substring(sOutput.indexOf("<BRANCH_CITY>")+"<BRANCH_CITY>".length(),sOutput.indexOf("</BRANCH_CITY>"));
						sRelationshipType = sOutput.substring(sOutput.indexOf("<RELATIONSHIP_TYPE>")+"<RELATIONSHIP_TYPE>".length(),sOutput.indexOf("</RELATIONSHIP_TYPE>"));
						
						String txnThirdParty=sOutput.substring(sOutput.indexOf("<TRN_THIRD_PARTY>")+"<TRN_THIRD_PARTY>".length(),sOutput.indexOf("</TRN_THIRD_PARTY>"));
						
						expiryType =  sOutput.substring(sOutput.indexOf("<TRN_TENOR>")+"<TRN_TENOR>".length(),sOutput.indexOf("</TRN_TENOR>"));
						purposeOfMessage = sOutput.substring(sOutput.indexOf("<FCUBS_PUR_OF_MSG>")+"<FCUBS_PUR_OF_MSG>".length(),sOutput.indexOf("</FCUBS_PUR_OF_MSG>"));
						transferConditions =sOutput.substring(sOutput.indexOf("<TRANSFER_CONDITIONS>")+"<TRANSFER_CONDITIONS>".length(),sOutput.indexOf("</TRANSFER_CONDITIONS>"));
						transferable = sOutput.substring(sOutput.indexOf("<TRANSFER_INDICATOR>")+"<TRANSFER_INDICATOR>".length(),sOutput.indexOf("</TRANSFER_INDICATOR>"));
					    additionalAmountInformation =sOutput.substring(sOutput.indexOf("<ADDN_AMT_INFO>")
					       +"<ADDN_AMT_INFO>".length(),sOutput.indexOf("</ADDN_AMT_INFO>"));
						purposeOfGtee =sOutput.substring(sOutput.indexOf("<PUR_OF_GTEE>")+"<PUR_OF_GTEE>".length(),sOutput.indexOf("</PUR_OF_GTEE>"));
						expiryConditionOrEvent =sOutput.substring(sOutput.indexOf("<EXPIRY_COND>")+"<EXPIRY_COND>".length(),sOutput.indexOf("</EXPIRY_COND>"));
						
						if("FD".equalsIgnoreCase(expiryType)){
							expiryType ="LIMT";
						}else if("OE".equalsIgnoreCase(expiryType)){
							expiryType ="UNLM";
						}else if("COND".equalsIgnoreCase(expiryType)){
							expiryType ="COND";
						}
						
						if("Request".equalsIgnoreCase(purposeOfMessage)){
							purposeOfMessage ="R";
						}else if("Issue".equalsIgnoreCase(purposeOfMessage)){
							purposeOfMessage ="I";
						}else{
							purposeOfMessage ="";
						}
						
						if("true".equalsIgnoreCase(transferable)){
							transferable ="Y";
						}else if("false".equalsIgnoreCase(transferable)){
							transferable ="N";
						}else{
							transferable ="";
						}
						
						HashMap limitCreditRvwChecklist = getCreditReviwChecklistData(sRequestCategoryCode,sRequestTypeCode,sWIName);
						String collateralPercent = (String)limitCreditRvwChecklist.get("Cash_mrgn_Perc");
						customerType = "ISB";
						
						 
						WriteToLog_showpage("Y","customerType=: "+ customerType);
						limitSerialNumber = "1";
						limitPartyType =customerType;
						limitLinkageType = "F";
						limitPercentContribution = "100";
						limitAmountTag = "LIAB_OS_AMT";
						limitLineRefNumber = (String)limitCreditRvwChecklist.get("Limt_Line");;
						sBranchCreateCode = fetchBranchCode(sRelationshipType , sWIName);
						String adviceDetails=fetchAdviceDetails(sWIName,"GA");//Mansi
						termsAndCondDetails= fetchTermsDetails(sWIName);
						String sDocDetls =getDocumentDetailsforCreateAmend(sWIName,sRequestTypeCode);

						if(sCallName.equalsIgnoreCase("modTradeLCContract_Oper")){
							callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeLCContract_Oper</operationType><correlationID>342343</correlationID><modTradeLCContractReq><requestType>C</requestType><externalRefNumber>" + sExternalRefNumber + "</externalRefNumber><operationCode>" + sOperationCode + "</operationCode><settlementType>N</settlementType><guaranteeType>"+getProductGTEETypeCode(sProductCode,sRequestCategoryCode,sRequestTypeCode)+"</guaranteeType><contractCurrency>" + sContractCurrency + "</contractCurrency><contractAmount>" + sContractAmount + "</contractAmount><negativeTolerance></negativeTolerance><positiveTolerance></positiveTolerance><benefConfirmation>N</benefConfirmation><expiryDate>" + sExpiryDate + "</expiryDate><issueDate>" + CurrDate + "</issueDate><closureType>M</closureType><customerType>" + customerType + "</customerType><customerId>" + sCustomerId + "</customerId><customerRefNumber></customerRefNumber><limitTracking>N</limitTracking><amendementFlag>N</amendementFlag><languageCode>ENG</languageCode><amendmentNumber>0</amendmentNumber><mayConfirm>N</mayConfirm><effectiveDate>" + CurrDate + "</effectiveDate><limitTrackTenorType>L</limitTrackTenorType><productCode>" + sProductCode + "</productCode><issuanceRequest>I</issuanceRequest><stopDate>" + sExpiryDate + "</stopDate><branchCode>" + sBranchCreateCode + "</branchCode><partyDetails>" + getPartyDetailsforCreateAmend(customerType,sRequestTypeCode,sWIName,sProcessType,sSWIFT_UTILITY_FLAG) + "</partyDetails><partyOtherAddresses>" + getPartyOtherDetails(sWIName) + "</partyOtherAddresses><remark>"+setProTradeRemark(sWIName)+"</remark><shipmentDetails><fromPlace></fromPlace><toPlace></toPlace><lastShipmentDate></lastShipmentDate><portOfDischarge></portOfDischarge><portOfLoading></portOfLoading></shipmentDetails><goodsDetails><goodsCode></goodsCode><goodsDescription></goodsDescription></goodsDetails><txnUDFDetails><UDFFieldName>GTEE_ISSUED_AT</UDFFieldName><udfFieldValue>" + sUdfFieldValue + "</udfFieldValue></txnUDFDetails>"+
							"<contrMstr><counterGuaranteeExpiryDate></counterGuaranteeExpiryDate></contrMstr>"+
							"<collateralDetails><collateralCurrency></collateralCurrency><collateralPercent></collateralPercent></collateralDetails>"+"<provisionCus><provisionForReceivableComponent>Y</provisionForReceivableComponent></provisionCus><forceDebitCus><forceDebitAccount>Y</forceDebitAccount></forceDebitCus><wmsDetails><workItemNumber>" + sWIName + "</workItemNumber></wmsDetails><expiryType>"+expiryType+"</expiryType><purposeOfMessage>"+purposeOfMessage+"</purposeOfMessage><transferConditions>"+transferConditions+"</transferConditions><transferable>"+transferable+"</transferable><additionalAmountInformation>"+additionalAmountInformation+"</additionalAmountInformation><expiryConditionOrEvent>"+expiryConditionOrEvent+"</expiryConditionOrEvent><termsAndCondDtls>"+termsAndCondDetails+"</termsAndCondDtls><adviceDetails>"+adviceDetails+"</adviceDetails><documentDetails>" +sDocDetls+ "</documentDetails></modTradeLCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";
							WriteToLog_showpage("Y","modTradeLCContract_Oper sOutput: "+ callXML);
							sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sCallName +"',"+ convertToPlainString(callXML) +",'"+ REFNO +"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","modTradeLCContract_Oper callXML TFO_DJ_INTEGRATION_CALLS_DTLS: "+ sQuery + " *****************************");	
							sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;						
							WriteToLog_showpage("Y"," insert modTradeLCContract_Oper sOutput: "+ sOutput);	
											
							
							sOutput = socket.connectToSocket(callXML);
							WriteToLog_showpage("Y","modTradeLCContract_Oper callXML sOutput"+ sOutput);
							
							String queryUpdateXML = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'" + sOutput + "'","WI_NAME = '"+ sWIName +"'  and REF_NUM = '"+REFNO+"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","update sOutputUpdateXML input: "+ queryUpdateXML);
							String sOutputUpdateXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",queryUpdateXML);;
							WriteToLog_showpage("Y","update sOutputUpdateXML output: " + sOutputUpdateXML);
							
							xp = new XMLParser(sOutput);
							returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
							String nextValue ="";
							if(returnCode == 0){
								sErrorDescription = xp.getValueOf("errorDescription");
								sContractRefNumber = xp.getValueOf("contractRefNumber");
								sResponse = "Success";
								sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS,TRANSACTION_REFERENCE","'" + sResponse + "','" + sContractRefNumber + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
								out.println(returnCode+"#"+sResponse+"#"+sErrorDescription);
							} else {
								sErrorDescription = xp.getValueOf("errorDescription");
								sResponse = "Failure";
								sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
								out.println("1#"+sResponse+"#"+sErrorDescription);
							}
						}
					}else if("ELC".equalsIgnoreCase(sRequestCategoryCode) && "ELC_SLCA".equalsIgnoreCase(sRequestTypeCode))
					{
						sExternalRefNumber = sOutput.substring(sOutput.indexOf("<PRO_TRADE_REF_NO>")+"<PRO_TRADE_REF_NO>".length(),sOutput.indexOf("</PRO_TRADE_REF_NO>"));
						sOperationCode = sOutput.substring(sOutput.indexOf("<OPERATION_CODE>")+"<OPERATION_CODE>".length(),sOutput.indexOf("</OPERATION_CODE>"));
						sContractCurrency = sOutput.substring(sOutput.indexOf("<TRANSACTION_CURRENCY>")+"<TRANSACTION_CURRENCY>".length(),sOutput.indexOf("</TRANSACTION_CURRENCY>"));
						sContractAmount = sOutput.substring(sOutput.indexOf("<TRANSACTION_AMOUNT>")+"<TRANSACTION_AMOUNT>".length(),sOutput.indexOf("</TRANSACTION_AMOUNT>"));
						sNegativeTolerance = sOutput.substring(sOutput.indexOf("<LC_UNDER>")+"<LC_UNDER>".length(),sOutput.indexOf("</LC_UNDER>"));
						sPositiveTolerance = sOutput.substring(sOutput.indexOf("<LC_ABOVE>")+"<LC_ABOVE>".length(),sOutput.indexOf("</LC_ABOVE>"));
						sExpiryDate = sOutput.substring(sOutput.indexOf("<EXP_DATE>")+"<EXP_DATE>".length(),sOutput.indexOf("</EXP_DATE>"));
						sCustomerId = sOutput.substring(sOutput.indexOf("<CUSTOMER_ID>")+"<CUSTOMER_ID>".length(),sOutput.indexOf("</CUSTOMER_ID>"));
						sProductCode = sOutput.substring(sOutput.indexOf("<PRODUCT_TYPE>")+"<PRODUCT_TYPE>".length(),sOutput.indexOf("</PRODUCT_TYPE>"));
						sFromPlace = sOutput.substring(sOutput.indexOf("<LC_FROM_PLACE>")+"<LC_FROM_PLACE>".length(),sOutput.indexOf("</LC_FROM_PLACE>"));
						sToPlace = sOutput.substring(sOutput.indexOf("<LC_TO_PLACE>")+"<LC_TO_PLACE>".length(),sOutput.indexOf("</LC_TO_PLACE>"));
						sLastShipmentDate = sOutput.substring(sOutput.indexOf("<LC_LTST_SHIPMNT_DATE>")+"<LC_LTST_SHIPMNT_DATE>".length(),sOutput.indexOf("</LC_LTST_SHIPMNT_DATE>"));
						sPortOfDischarge = sOutput.substring(sOutput.indexOf("<LC_PORT_OF_DISCHRG>")+"<LC_PORT_OF_DISCHRG>".length(),sOutput.indexOf("</LC_PORT_OF_DISCHRG>"));
						sPortOfLoading = sOutput.substring(sOutput.indexOf("<LC_PORT_OF_LOADING>")+"<LC_PORT_OF_LOADING>".length(),sOutput.indexOf("</LC_PORT_OF_LOADING>"));
						sGoodsDescription = sOutput.substring(sOutput.indexOf("<LC_GOODS_DESC>")+"<LC_GOODS_DESC>".length(),sOutput.indexOf("</LC_GOODS_DESC>"));
						sUdfFieldValue = sOutput.substring(sOutput.indexOf("<BRANCH_CITY>")+"<BRANCH_CITY>".length(),sOutput.indexOf("</BRANCH_CITY>"));
						sRelationshipType = sOutput.substring(sOutput.indexOf("<RELATIONSHIP_TYPE>")+"<RELATIONSHIP_TYPE>".length(),sOutput.indexOf("</RELATIONSHIP_TYPE>"));
						String counterGuaranteeExpiryDate=sOutput.substring(sOutput.indexOf("<GRNTEE_CNTR_EXP_DATE>")+"<GRNTEE_CNTR_EXP_DATE>".length(),sOutput.indexOf("</GRNTEE_CNTR_EXP_DATE>"));
						
						String txnThirdParty=sOutput.substring(sOutput.indexOf("<TRN_THIRD_PARTY>")+"<TRN_THIRD_PARTY>".length(),sOutput.indexOf("</TRN_THIRD_PARTY>"));
						String sAccountNumber  = sOutput.substring(sOutput.indexOf("<ACCOUNT_NUMBER>")+"<ACCOUNT_NUMBER>".length(),sOutput.indexOf("</ACCOUNT_NUMBER>"));
						String sEffectiveDate  = sOutput.substring(sOutput.indexOf("<EFFECTIVE_DATE>")+"<EFFECTIVE_DATE>".length(),sOutput.indexOf("</EFFECTIVE_DATE>"));
						   expiryType =  sOutput.substring(sOutput.indexOf("<TRN_TENOR>")+"<TRN_TENOR>".length(),sOutput.indexOf("</TRN_TENOR>"));
						purposeOfMessage = sOutput.substring(sOutput.indexOf("<FCUBS_PUR_OF_MSG>")+"<FCUBS_PUR_OF_MSG>".length(),sOutput.indexOf("</FCUBS_PUR_OF_MSG>"));
						transferConditions =sOutput.substring(sOutput.indexOf("<TRANSFER_CONDITIONS>")+"<TRANSFER_CONDITIONS>".length(),sOutput.indexOf("</TRANSFER_CONDITIONS>"));
						transferable = sOutput.substring(sOutput.indexOf("<TRANSFER_INDICATOR>")+"<TRANSFER_INDICATOR>".length(),sOutput.indexOf("</TRANSFER_INDICATOR>"));
					    additionalAmountInformation =sOutput.substring(sOutput.indexOf("<ADDN_AMT_INFO>")
					       +"<ADDN_AMT_INFO>".length(),sOutput.indexOf("</ADDN_AMT_INFO>"));
						purposeOfGtee =sOutput.substring(sOutput.indexOf("<PUR_OF_GTEE>")+"<PUR_OF_GTEE>".length(),sOutput.indexOf("</PUR_OF_GTEE>"));
						expiryConditionOrEvent =sOutput.substring(sOutput.indexOf("<EXPIRY_COND>")+"<EXPIRY_COND>".length(),sOutput.indexOf("</EXPIRY_COND>"));
						
						if("FD".equalsIgnoreCase(expiryType)){
							expiryType ="LIMT";
						}else if("OE".equalsIgnoreCase(expiryType)){
							expiryType ="UNLM";
						}else if("COND".equalsIgnoreCase(expiryType)){
							expiryType ="COND";
						}
						
						if("Request".equalsIgnoreCase(purposeOfMessage)){
							purposeOfMessage ="R";
						}else if("Issue".equalsIgnoreCase(purposeOfMessage)){
							purposeOfMessage ="I";
						}else{
							purposeOfMessage ="";
						}
						
						if("true".equalsIgnoreCase(transferable)){
							transferable ="Y";
						}else if("false".equalsIgnoreCase(transferable)){
							transferable ="N";
						}else{
							transferable ="";
						}
						if("".equalsIgnoreCase(sEffectiveDate)){
							sEffectiveDate = CurrDate;
						}
						HashMap limitCreditRvwChecklist = getCreditReviwChecklistData(sRequestCategoryCode,sRequestTypeCode,sWIName);
						//String collateralPercent = (String)limitCreditRvwChecklist.get("Cash_mrgn_Perc");
						String collateralPercent ="";
						
						String productList="T551,T552,T553,T554,T555,T556,T557,T558,T575";
						WriteToLog_showpage("Y","productData=: "+ Arrays.asList(productList.split(",")));
						List productData=Arrays.asList(productList.split(","));
						WriteToLog_showpage("Y","productData=: "+ productData);
						
						/* if (productData.contains(sProductCode)) {
							 customerType = "APB";
						 WriteToLog_showpage("Y","customerType1=: "+ customerType);
						 }
						else if("1".equalsIgnoreCase(txnThirdParty))    //1-yes
						{
							customerType = "ACC";
						}else{
							customerType = "APP";
						
						} */
						customerType = "BEN";
						WriteToLog_showpage("Y","customerType=: "+ customerType);
						limitSerialNumber = "1";
						limitPartyType =customerType;
						limitLinkageType = "F";
						limitPercentContribution = "100";
						limitAmountTag = "LIAB_OS_AMT";
						limitLineRefNumber = (String)limitCreditRvwChecklist.get("ELC_spec_limit_line");;
						sBranchCreateCode = fetchBranchCode(sRelationshipType , sWIName);
						
						String fftDettails="";
						fftDettails=FetchFFTData(sWIName);
						if(!"".equalsIgnoreCase(purposeOfGtee)){
							fftDettails=fftDettails+"<freeFormatTextDtl><freeFormatTextCode>45LTRNDTLS</freeFormatTextCode>"+
							"<freeFormatTextDescription>"+purposeOfGtee+"</freeFormatTextDescription></freeFormatTextDtl>";
							fftDettails=fftDettails+"<freeFormatTextDtl><freeFormatTextCode>45LTRNDTSEQC</freeFormatTextCode>"+
							"<freeFormatTextDescription>"+purposeOfGtee+"</freeFormatTextDescription></freeFormatTextDtl>";
						}
						String adviceDetails=fetchAdviceDetails(sWIName,"ELC_SLCA");//NI
						termsAndCondDetails= fetchTermsDetails(sWIName);
						if(sCallName.equalsIgnoreCase("modTradeLCContract_Oper")){
							callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeLCContract_Oper</operationType><correlationID>342343</correlationID><modTradeLCContractReq><requestType>C</requestType><externalRefNumber>" + sExternalRefNumber + "</externalRefNumber><operationCode>" + sOperationCode + "</operationCode><transferable>N</transferable><settlementType></settlementType><guaranteeType>"+getProductGTEETypeCode(sProductCode,sRequestCategoryCode,sRequestTypeCode)+"</guaranteeType><contractCurrency>" + sContractCurrency + "</contractCurrency><contractAmount>" + sContractAmount + "</contractAmount><negativeTolerance></negativeTolerance><positiveTolerance></positiveTolerance><benefConfirmation>N</benefConfirmation><expiryDate>" + sExpiryDate + "</expiryDate><issueDate>" + CurrDate + "</issueDate><closureType>M</closureType><customerType>" + customerType + "</customerType><customerId>" + sCustomerId + "</customerId><customerRefNumber></customerRefNumber><limitTracking>N</limitTracking><amendementFlag>N</amendementFlag><languageCode>ENG</languageCode><amendmentNumber>0</amendmentNumber><mayConfirm>N</mayConfirm><effectiveDate>" + sEffectiveDate + "</effectiveDate><limitTrackTenorType>L</limitTrackTenorType><productCode>" + sProductCode + "</productCode><issuanceRequest>R</issuanceRequest><stopDate>" + sExpiryDate + "</stopDate><branchCode>" + sBranchCreateCode + "</branchCode><partyDetails>" + getPartyDetailsforCreateAmend(customerType,sRequestTypeCode,sWIName,"","") + "</partyDetails><partyOtherAddresses>" + getPartyOtherDetails(sWIName) + "</partyOtherAddresses><remark>"+setProTradeRemark(sWIName)+"</remark><shipmentDetails><fromPlace></fromPlace><toPlace></toPlace><lastShipmentDate></lastShipmentDate><portOfDischarge></portOfDischarge><portOfLoading></portOfLoading></shipmentDetails><goodsDetails><goodsCode></goodsCode><goodsDescription></goodsDescription></goodsDetails><txnUDFDetails><UDFFieldName>TXN_ISSUING_CENTER</UDFFieldName><udfFieldValue>" + sUdfFieldValue + "</udfFieldValue></txnUDFDetails>"+
							"<contrMstr><counterGuaranteeExpiryDate>" + counterGuaranteeExpiryDate + "</counterGuaranteeExpiryDate></contrMstr>"+
                            "<provisionCus><provisionForReceivableComponent>Y</provisionForReceivableComponent></provisionCus><forceDebitCus><forceDebitAccount>Y</forceDebitAccount></forceDebitCus><wmsDetails><workItemNumber>" + sWIName + "</workItemNumber></wmsDetails><freeFormatTextDtls>"+fftDettails+"</freeFormatTextDtls><adviceDetails>"+adviceDetails+"</adviceDetails><Account>"+sAccountNumber+"</Account><expiryType>"+expiryType+"</expiryType><purposeOfMessage>"+purposeOfMessage+"</purposeOfMessage><transferConditions>"+transferConditions+"</transferConditions><transferable>"+transferable+"</transferable><additionalAmountInformation>"+additionalAmountInformation+"</additionalAmountInformation><expiryConditionOrEvent>"+expiryConditionOrEvent+"</expiryConditionOrEvent><termsAndCondDtls>"+termsAndCondDetails+"</termsAndCondDtls></modTradeLCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";
						//	callXML="<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype>"
                        //       + "<InnerCallType>LapsResponse</InnerCallType><SessionId>"+sSessionId+"</SessionId><EngineName>"+sCabname+"</EngineName>"
			               //    + "<PushMessage><ChannelResponse><referralAndLoanStatusMsg><header><mode>N</mode><channelName>TSLM</channelName><sysrefno>98765432</sysrefno><processName>TFO</processName><requestDateTime>18/10/2020 14:52:00</requestDateTime><correlationId>1606210868660</correlationId><version>1.0</version></header><referralAndLoanStatus><stage>P</stage><userId>ADCBAE:TESTMAKER</userId><wmsId>TF-00000001205-REQUEST</wmsId><tslmId>1021</tslmId><wiTxnStatus>Initiated</wiTxnStatus><referralList>    <referal><seqNo>1</seqNo><trxType>INVOICE</trxType><trxId>1024</trxId><cpcid>256012</cpcid><referralUnit>OP</referralUnit><referralDesc>Invoice referral</referralDesc><comment>Incorrect invoice tenor</comment>    </referal>    <referal><seqNo>2</seqNo><trxType>INVOICE</trxType><trxId>1025</trxId><cpcid>256013</cpcid><referralUnit>OP</referralUnit><referralDesc>Invoice referral</referralDesc><comment>Incorrect cp info</comment>    </referal></referralList></referralAndLoanStatus>"
			           //        + "</referralAndLoanStatusMsg></ChannelResponse></PushMessage></APWebService_Input>";
							WriteToLog_showpage("Y","modTradeLCContract_Oper sOutput: "+ callXML);
							sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sCallName +"',"+ convertToPlainString(callXML) +",'"+ REFNO +"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","modTradeLCContract_Oper callXML TFO_DJ_INTEGRATION_CALLS_DTLS: "+ sQuery + " *****************************");	
							sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;						
							WriteToLog_showpage("Y"," insert modTradeLCContract_Oper sOutput: "+ sOutput);	
											
							
							sOutput = socket.connectToSocket(callXML);
							WriteToLog_showpage("Y","modTradeLCContract_Oper callXML sOutput"+ sOutput);
							
							String queryUpdateXML = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'" + sOutput + "'","WI_NAME = '"+ sWIName +"'  and REF_NUM = '"+REFNO+"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","update sOutputUpdateXML input: "+ queryUpdateXML);
							String sOutputUpdateXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",queryUpdateXML);;
							WriteToLog_showpage("Y","update sOutputUpdateXML output: " + sOutputUpdateXML);
							
							xp = new XMLParser(sOutput);
							returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
							String nextValue ="";
							if(returnCode == 0){
								sErrorDescription = xp.getValueOf("errorDescription");
								sContractRefNumber = xp.getValueOf("contractRefNumber");
								sResponse = "Success";
								sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS,TRANSACTION_REFERENCE","'" + sResponse + "','" + sContractRefNumber + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
								out.println(returnCode+"#"+sResponse+"#"+sErrorDescription);
							} else {
								sErrorDescription = xp.getValueOf("errorDescription");
								sResponse = "Failure";
								sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
								out.println("1#"+sResponse+"#"+sErrorDescription);
							}
						}
					}else if("SBLC".equalsIgnoreCase(sRequestCategoryCode) && "SBLC_NI".equalsIgnoreCase(sRequestTypeCode))
					{
						WriteToLog_showpage("Y","INSIDE SBLC CONDITION CHECK 1");
						sExternalRefNumber = sOutput.substring(sOutput.indexOf("<PRO_TRADE_REF_NO>")+"<PRO_TRADE_REF_NO>".length(),sOutput.indexOf("</PRO_TRADE_REF_NO>"));
						sOperationCode = sOutput.substring(sOutput.indexOf("<OPERATION_CODE>")+"<OPERATION_CODE>".length(),sOutput.indexOf("</OPERATION_CODE>"));
						sContractCurrency = sOutput.substring(sOutput.indexOf("<TRANSACTION_CURRENCY>")+"<TRANSACTION_CURRENCY>".length(),sOutput.indexOf("</TRANSACTION_CURRENCY>"));
						sContractAmount = sOutput.substring(sOutput.indexOf("<TRANSACTION_AMOUNT>")+"<TRANSACTION_AMOUNT>".length(),sOutput.indexOf("</TRANSACTION_AMOUNT>"));
						sNegativeTolerance = sOutput.substring(sOutput.indexOf("<LC_UNDER>")+"<LC_UNDER>".length(),sOutput.indexOf("</LC_UNDER>"));
						sPositiveTolerance = sOutput.substring(sOutput.indexOf("<LC_ABOVE>")+"<LC_ABOVE>".length(),sOutput.indexOf("</LC_ABOVE>"));
						sExpiryDate = sOutput.substring(sOutput.indexOf("<EXP_DATE>")+"<EXP_DATE>".length(),sOutput.indexOf("</EXP_DATE>"));
						sCustomerId = sOutput.substring(sOutput.indexOf("<CUSTOMER_ID>")+"<CUSTOMER_ID>".length(),sOutput.indexOf("</CUSTOMER_ID>"));
						sProductCode = sOutput.substring(sOutput.indexOf("<PRODUCT_TYPE>")+"<PRODUCT_TYPE>".length(),sOutput.indexOf("</PRODUCT_TYPE>"));
						sFromPlace = sOutput.substring(sOutput.indexOf("<LC_FROM_PLACE>")+"<LC_FROM_PLACE>".length(),sOutput.indexOf("</LC_FROM_PLACE>"));
						sToPlace = sOutput.substring(sOutput.indexOf("<LC_TO_PLACE>")+"<LC_TO_PLACE>".length(),sOutput.indexOf("</LC_TO_PLACE>"));
						sLastShipmentDate = sOutput.substring(sOutput.indexOf("<LC_LTST_SHIPMNT_DATE>")+"<LC_LTST_SHIPMNT_DATE>".length(),sOutput.indexOf("</LC_LTST_SHIPMNT_DATE>"));
						sPortOfDischarge = sOutput.substring(sOutput.indexOf("<LC_PORT_OF_DISCHRG>")+"<LC_PORT_OF_DISCHRG>".length(),sOutput.indexOf("</LC_PORT_OF_DISCHRG>"));
						sPortOfLoading = sOutput.substring(sOutput.indexOf("<LC_PORT_OF_LOADING>")+"<LC_PORT_OF_LOADING>".length(),sOutput.indexOf("</LC_PORT_OF_LOADING>"));
						sGoodsDescription = sOutput.substring(sOutput.indexOf("<LC_GOODS_DESC>")+"<LC_GOODS_DESC>".length(),sOutput.indexOf("</LC_GOODS_DESC>"));
						sUdfFieldValue = sOutput.substring(sOutput.indexOf("<BRANCH_CITY>")+"<BRANCH_CITY>".length(),sOutput.indexOf("</BRANCH_CITY>"));
						sRelationshipType = sOutput.substring(sOutput.indexOf("<RELATIONSHIP_TYPE>")+"<RELATIONSHIP_TYPE>".length(),sOutput.indexOf("</RELATIONSHIP_TYPE>"));
						String counterGuaranteeExpiryDate=sOutput.substring(sOutput.indexOf("<GRNTEE_CNTR_EXP_DATE>")+"<GRNTEE_CNTR_EXP_DATE>".length(),sOutput.indexOf("</GRNTEE_CNTR_EXP_DATE>"));
						
						String txnThirdParty=sOutput.substring(sOutput.indexOf("<TRN_THIRD_PARTY>")+"<TRN_THIRD_PARTY>".length(),sOutput.indexOf("</TRN_THIRD_PARTY>"));
						String sAccountNumber  = sOutput.substring(sOutput.indexOf("<ACCOUNT_NUMBER>")+"<ACCOUNT_NUMBER>".length(),sOutput.indexOf("</ACCOUNT_NUMBER>"));
						String sEffectiveDate  = sOutput.substring(sOutput.indexOf("<EFFECTIVE_DATE>")+"<EFFECTIVE_DATE>".length(),sOutput.indexOf("</EFFECTIVE_DATE>"));
						  expiryType =  sOutput.substring(sOutput.indexOf("<TRN_TENOR>")+"<TRN_TENOR>".length(),sOutput.indexOf("</TRN_TENOR>"));
						purposeOfMessage = sOutput.substring(sOutput.indexOf("<FCUBS_PUR_OF_MSG>")+"<FCUBS_PUR_OF_MSG>".length(),sOutput.indexOf("</FCUBS_PUR_OF_MSG>"));
						transferConditions =sOutput.substring(sOutput.indexOf("<TRANSFER_CONDITIONS>")+"<TRANSFER_CONDITIONS>".length(),sOutput.indexOf("</TRANSFER_CONDITIONS>"));
						transferable = sOutput.substring(sOutput.indexOf("<TRANSFER_INDICATOR>")+"<TRANSFER_INDICATOR>".length(),sOutput.indexOf("</TRANSFER_INDICATOR>"));
					    additionalAmountInformation =sOutput.substring(sOutput.indexOf("<ADDN_AMT_INFO>")
					       +"<ADDN_AMT_INFO>".length(),sOutput.indexOf("</ADDN_AMT_INFO>"));
						purposeOfGtee =sOutput.substring(sOutput.indexOf("<PUR_OF_GTEE>")+"<PUR_OF_GTEE>".length(),sOutput.indexOf("</PUR_OF_GTEE>"));
						expiryConditionOrEvent =sOutput.substring(sOutput.indexOf("<EXPIRY_COND>")+"<EXPIRY_COND>".length(),sOutput.indexOf("</EXPIRY_COND>"));
					
						WriteToLog_showpage("Y","ExpiryType : "+ expiryType);
						if("FD".equalsIgnoreCase(expiryType)){
							expiryType ="LIMT";
						}else if("OE".equalsIgnoreCase(expiryType)){
							expiryType ="UNLM";
						}else if("COND".equalsIgnoreCase(expiryType)){
							expiryType ="COND";
						}
						
						if("Request".equalsIgnoreCase(purposeOfMessage)){
							purposeOfMessage ="R";
						}else if("Issue".equalsIgnoreCase(purposeOfMessage)){
							purposeOfMessage ="I";
						}else{
							purposeOfMessage ="";
						}
						
						if("true".equalsIgnoreCase(transferable)){
							transferable ="Y";
						}else if("false".equalsIgnoreCase(transferable)){
							transferable ="N";
						}else{
							transferable ="";
						}
						if("".equalsIgnoreCase(sEffectiveDate)){
							sEffectiveDate = CurrDate;
						}
						HashMap limitCreditRvwChecklist = getCreditReviwChecklistData(sRequestCategoryCode,sRequestTypeCode,sWIName);
						String collateralPercent = (String)limitCreditRvwChecklist.get("ILC_Cash_Mrgn_Prcnt");
						
						
						if((!"SWIFT".equalsIgnoreCase(sProcessType) || "S".equalsIgnoreCase(sSourceChannel)) && "1".equalsIgnoreCase(txnThirdParty)) 
						{
							customerType = "OBP";
						}else if("SWIFT".equalsIgnoreCase(sProcessType) ||  "S".equalsIgnoreCase(sSourceChannel))
						{
							customerType = "APB";
						}else{
							customerType = "APP";
						
						}
						WriteToLog_showpage("Y","customerType=: "+ customerType);
						limitSerialNumber = "1";
						limitPartyType =customerType;
						limitLinkageType = "F";
						limitPercentContribution = "100";
						limitAmountTag = "LIAB_OS_AMT";
						limitLineRefNumber = (String)limitCreditRvwChecklist.get("Limt_Line");
						limitLine = (String)limitCreditRvwChecklist.get("ILC_Lmt_line");
						sBranchCreateCode = fetchBranchCode(sRelationshipType , sWIName);
						
						String fftDettails="";
						fftDettails=FetchFFTData(sWIName);
						
						if("SWIFT".equalsIgnoreCase(sProcessType) && "Y".equalsIgnoreCase(sSWIFT_UTILITY_FLAG) )//WORK FOR CASES FOR WHICH MAPPING IN MAP 
						{
							WriteToLog_showpage("Y"," calling SetSwiftMQWIFFtData");
							fftDettails  = SetSwiftMQWIFFtData(sWIName,fftDettails);
						}
						if(!"".equalsIgnoreCase(purposeOfGtee)){
							fftDettails=fftDettails+"<freeFormatTextDtl><freeFormatTextCode>45LTRNDTLS</freeFormatTextCode>"+
							"<freeFormatTextDescription>"+purposeOfGtee+"</freeFormatTextDescription></freeFormatTextDtl>";
							fftDettails=fftDettails+"<freeFormatTextDtl><freeFormatTextCode>45LTRNDTSEQC</freeFormatTextCode>"+
							"<freeFormatTextDescription>"+purposeOfGtee+"</freeFormatTextDescription></freeFormatTextDtl>";
						}
						String adviceDetails=fetchAdviceDetails(sWIName,"SBLC_NI");//"NI");
						termsAndCondDetails= fetchTermsDetails(sWIName);
						String sDocDetls =getDocumentDetailsforCreateAmend(sWIName,sRequestTypeCode);
						if(sCallName.equalsIgnoreCase("modTradeLCContract_Oper")){
							callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeLCContract_Oper</operationType><correlationID>342343</correlationID><modTradeLCContractReq><requestType>C</requestType><externalRefNumber>" + sExternalRefNumber + "</externalRefNumber><operationCode>" + sOperationCode + "</operationCode><transferable>N</transferable><settlementType></settlementType><guaranteeType>"+getProductGTEETypeCode(sProductCode,sRequestCategoryCode,sRequestTypeCode)+"</guaranteeType><contractCurrency>" + sContractCurrency + "</contractCurrency><contractAmount>" + sContractAmount + "</contractAmount><negativeTolerance></negativeTolerance><positiveTolerance></positiveTolerance><benefConfirmation>N</benefConfirmation><expiryDate>" + sExpiryDate + "</expiryDate><issueDate>" + CurrDate + "</issueDate><closureType>M</closureType><customerType>" + customerType + "</customerType><customerId>" + sCustomerId + "</customerId><customerRefNumber></customerRefNumber><limitTracking>Y</limitTracking><amendementFlag>N</amendementFlag><languageCode>ENG</languageCode><amendmentNumber>0</amendmentNumber><mayConfirm>N</mayConfirm><effectiveDate>" + sEffectiveDate + "</effectiveDate><limitTrackTenorType>L</limitTrackTenorType><productCode>" + sProductCode + "</productCode><issuanceRequest>R</issuanceRequest><stopDate>" + sExpiryDate + "</stopDate><branchCode>" + sBranchCreateCode + "</branchCode><partyDetails>" + getPartyDetailsforCreateAmend(customerType,sRequestTypeCode,sWIName,"","") + "</partyDetails><partyOtherAddresses>" + getPartyOtherDetails(sWIName) + "</partyOtherAddresses><remark>"+setProTradeRemark(sWIName)+"</remark><shipmentDetails><fromPlace></fromPlace><toPlace></toPlace><lastShipmentDate></lastShipmentDate><portOfDischarge></portOfDischarge><portOfLoading></portOfLoading></shipmentDetails><goodsDetails><goodsCode></goodsCode><goodsDescription></goodsDescription></goodsDetails><txnUDFDetails><UDFFieldName>TXN_ISSUING_CENTER</UDFFieldName><udfFieldValue>" + sUdfFieldValue + "</udfFieldValue></txnUDFDetails><contractLimits><contractLimit><limitSerialNumber>" + limitSerialNumber + "</limitSerialNumber><limitPartyType>" + limitPartyType + "</limitPartyType><limitLinkageType>" + limitLinkageType + "</limitLinkageType><limitPercentContribution>" + limitPercentContribution + "</limitPercentContribution><limitAmountTag>" + limitAmountTag + "</limitAmountTag><limitLineCustomerId>" + sCustomerId + "</limitLineCustomerId><limitLineRefNumber>" + limitLine + "</limitLineRefNumber></contractLimit></contractLimits>"+
							"<contrMstr><counterGuaranteeExpiryDate>" + counterGuaranteeExpiryDate + "</counterGuaranteeExpiryDate></contrMstr>"+
							"<collateralDetails><collateralCurrency>"+sContractCurrency+"</collateralCurrency><collateralPercent>"+collateralPercent+"</collateralPercent></collateralDetails>"+"<provisionCus><provisionForReceivableComponent>Y</provisionForReceivableComponent></provisionCus><forceDebitCus><forceDebitAccount>Y</forceDebitAccount></forceDebitCus><wmsDetails><workItemNumber>" + sWIName + "</workItemNumber></wmsDetails><freeFormatTextDtls>"+fftDettails+"</freeFormatTextDtls><adviceDetails>"+adviceDetails+"</adviceDetails><Account>"+sAccountNumber+"</Account><expiryType>"+expiryType+"</expiryType><purposeOfMessage>"+purposeOfMessage+"</purposeOfMessage><transferConditions>"+transferConditions+"</transferConditions><transferable>"+transferable+"</transferable><additionalAmountInformation>"+additionalAmountInformation+"</additionalAmountInformation><expiryConditionOrEvent>"+expiryConditionOrEvent+"</expiryConditionOrEvent><termsAndCondDtls>"+termsAndCondDetails+"</termsAndCondDtls><documentDetails>" +sDocDetls+ "</documentDetails></modTradeLCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";
						//	callXML="<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype>"
                        //       + "<InnerCallType>LapsResponse</InnerCallType><SessionId>"+sSessionId+"</SessionId><EngineName>"+sCabname+"</EngineName>"
			               //    + "<PushMessage><ChannelResponse><referralAndLoanStatusMsg><header><mode>N</mode><channelName>TSLM</channelName><sysrefno>98765432</sysrefno><processName>TFO</processName><requestDateTime>18/10/2020 14:52:00</requestDateTime><correlationId>1606210868660</correlationId><version>1.0</version></header><referralAndLoanStatus><stage>P</stage><userId>ADCBAE:TESTMAKER</userId><wmsId>TF-00000001205-REQUEST</wmsId><tslmId>1021</tslmId><wiTxnStatus>Initiated</wiTxnStatus><referralList>    <referal><seqNo>1</seqNo><trxType>INVOICE</trxType><trxId>1024</trxId><cpcid>256012</cpcid><referralUnit>OP</referralUnit><referralDesc>Invoice referral</referralDesc><comment>Incorrect invoice tenor</comment>    </referal>    <referal><seqNo>2</seqNo><trxType>INVOICE</trxType><trxId>1025</trxId><cpcid>256013</cpcid><referralUnit>OP</referralUnit><referralDesc>Invoice referral</referralDesc><comment>Incorrect cp info</comment>    </referal></referralList></referralAndLoanStatus>"
			           //        + "</referralAndLoanStatusMsg></ChannelResponse></PushMessage></APWebService_Input>";
							WriteToLog_showpage("Y","Sanal :"+ callXML);
							sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sCallName +"',"+ convertToPlainString(callXML) +",'"+ REFNO +"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","modTradeLCContract_Oper callXML TFO_DJ_INTEGRATION_CALLS_DTLS: "+ sQuery + " *****************************");	
							sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;						
							WriteToLog_showpage("Y"," insert modTradeLCContract_Oper sOutput: "+ sOutput);	
											
							
							sOutput = socket.connectToSocket(callXML);
							WriteToLog_showpage("Y","modTradeLCContract_Oper callXML sOutput"+ sOutput);
							
							String queryUpdateXML = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'" + sOutput + "'","WI_NAME = '"+ sWIName +"'  and REF_NUM = '"+REFNO+"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","update sOutputUpdateXML input: "+ queryUpdateXML);
							String sOutputUpdateXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",queryUpdateXML);;
							WriteToLog_showpage("Y","update sOutputUpdateXML output: " + sOutputUpdateXML);
							
							xp = new XMLParser(sOutput);
							returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
							String nextValue ="";
							if(returnCode == 0){
								sErrorDescription = xp.getValueOf("errorDescription");
								sContractRefNumber = xp.getValueOf("contractRefNumber");
								sResponse = "Success";
								sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS,TRANSACTION_REFERENCE","'" + sResponse + "','" + sContractRefNumber + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
								out.println(returnCode+"#"+sResponse+"#"+sErrorDescription);
							} else {
								sErrorDescription = xp.getValueOf("errorDescription");
								sResponse = "Failure";
								sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
								out.println("1#"+sResponse+"#"+sErrorDescription);
							}
						}
					}else if("ILCB".equalsIgnoreCase(sRequestCategoryCode) && "ILCB_BL".equalsIgnoreCase(sRequestTypeCode))
					{
						sExternalRefNumber = sOutput.substring(sOutput.indexOf("<PRO_TRADE_REF_NO>")+"<PRO_TRADE_REF_NO>".length(),sOutput.indexOf("</PRO_TRADE_REF_NO>"));
						sOperationCode = sOutput.substring(sOutput.indexOf("<OPERATION_CODE>")+"<OPERATION_CODE>".length(),sOutput.indexOf("</OPERATION_CODE>"));
						sContractCurrency = sOutput.substring(sOutput.indexOf("<TRANSACTION_CURRENCY>")+"<TRANSACTION_CURRENCY>".length(),sOutput.indexOf("</TRANSACTION_CURRENCY>"));
						sContractAmount = sOutput.substring(sOutput.indexOf("<TRANSACTION_AMOUNT>")+"<TRANSACTION_AMOUNT>".length(),sOutput.indexOf("</TRANSACTION_AMOUNT>"));
						sCustomerId = sOutput.substring(sOutput.indexOf("<CUSTOMER_ID>")+"<CUSTOMER_ID>".length(),sOutput.indexOf("</CUSTOMER_ID>"));
						sProductCode = sOutput.substring(sOutput.indexOf("<PRODUCT_TYPE>")+"<PRODUCT_TYPE>".length(),sOutput.indexOf("</PRODUCT_TYPE>"));
						sUdfFieldValue = sOutput.substring(sOutput.indexOf("<BRANCH_CITY>")+"<BRANCH_CITY>".length(),sOutput.indexOf("</BRANCH_CITY>"));
						sRelationshipType = sOutput.substring(sOutput.indexOf("<RELATIONSHIP_TYPE>")+"<RELATIONSHIP_TYPE>".length(),sOutput.indexOf("</RELATIONSHIP_TYPE>"));
						//new fields
						sLcReferenceNo=sOutput.substring(sOutput.indexOf("<TRANSACTION_ADCB_LC_REFERENCE>")+"<TRANSACTION_ADCB_LC_REFERENCE>".length(),sOutput.indexOf("</TRANSACTION_ADCB_LC_REFERENCE>"));
						sLiquidationAmount="";
						sMaturityDate=sOutput.substring(sOutput.indexOf("<INF_MATURITY_DATE>")+"<INF_MATURITY_DATE>".length(),sOutput.indexOf("</INF_MATURITY_DATE>"));
						String tenorDays=sOutput.substring(sOutput.indexOf("<INF_TENOR_DAYS>")+"<INF_TENOR_DAYS>".length(),sOutput.indexOf("</INF_TENOR_DAYS>"));
						String tenorBase=sOutput.substring(sOutput.indexOf("<INF_TENOR_BASE>")+"<INF_TENOR_BASE>".length(),sOutput.indexOf("</INF_TENOR_BASE>"));
						String actualTenorBase=sOutput.substring(sOutput.indexOf("<INF_ACTUAL_TENOR_BASE>")+"<INF_ACTUAL_TENOR_BASE>".length(),sOutput.indexOf("</INF_ACTUAL_TENOR_BASE>"));
						String discrepancyDetails=sOutput.substring(sOutput.indexOf("<DISCREPANCY_DTLS>")+"<DISCREPANCY_DTLS>".length(),sOutput.indexOf("</DISCREPANCY_DTLS>"));
						sBaseDate=sOutput.substring(sOutput.indexOf("<INF_BASE_DOC_DATE>")+"<INF_BASE_DOC_DATE>".length(),sOutput.indexOf("</INF_BASE_DOC_DATE>"));
						String limitRefNumber=sOutput.substring(sOutput.indexOf("<LC_LIMIT_LINE>")+"<LC_LIMIT_LINE>".length(),sOutput.indexOf("</LC_LIMIT_LINE>"));
						sPartyType="DRAWEE";
						sRiskParticipation=checkPartyTypeContract(sWIName);
						if("T3U1".equalsIgnoreCase(sProductCode)||"I3U1".equalsIgnoreCase(sProductCode))
						{
							sTenorDays=tenorDays;
							stransitDays="0";
							sDaysAfterBaseDate=tenorBase;
							if("Others".equalsIgnoreCase(sDaysAfterBaseDate)){
								sDaysAfterBaseDate=actualTenorBase;
							}
						}else if("T3S1".equalsIgnoreCase(sProductCode)||"I3S1".equalsIgnoreCase(sProductCode))
						{
							sTenorDays="0";
							stransitDays=tenorDays;
							sDaysAfterBaseDate="";
						}
						HashMap docReviewChecklist = getDocReviwChecklistData(sRequestCategoryCode,sRequestTypeCode,sWIName);
						String docCreditCompiled = (String)docReviewChecklist.get("ILCB_DOC_CRD_COMPLIED");
						
						if("Yes".equalsIgnoreCase(docCreditCompiled)){
							sStage="FIN";
							sLimitTrackFlag="Y";
							sAcceptComFromDate=sFCUBSCurrentDate;
							sAcceptComToDate=sMaturityDate;
							sCode="";
							sDescription="";
							sResolved="";
							sResolvedDate="";
							sReceivedDate="";
							limitSerialNumber = "1";
							sLimitOperation="ACC";
							limitPartyType ="DRAWEE";
							limitLinkageType = "F";
							limitPercentContribution = "100";
							limitAmountTag = "BILL_OS_AMT";
							limitLineRefNumber=limitRefNumber;
							limitLineCustomerId=sCustomerId;
						}else{
							sStage="INI";
							sLimitTrackFlag="N";
							sAcceptComFromDate="";
							sAcceptComToDate="";
							sCode="ACW";
							//|ATP-526|REYAZ|13-02-2024| START
							//sDescription=discrepancyDetails;
							sDescription = "".equals(discrepancyDetails) ? fullText : discrepancyDetails;
							//|ATP-526|REYAZ|13-02-2024| END
							sResolved="N";
							sResolvedDate="";
							sReceivedDate=CurrDate;
							limitSerialNumber = "";
							sLimitOperation="";
							limitPartyType ="";
							limitLinkageType = "";
							limitPercentContribution = "";
							limitAmountTag = "";
							limitLineRefNumber="";
							limitLineCustomerId="";
						}
						

						sBranchCreateCode = fetchBranchCode(sRelationshipType , sWIName);
						if(sCallName.equalsIgnoreCase("modTradeBCContract_Oper")){
							callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeBCContract_Oper</operationType><correlationID>342343</correlationID><modTradeBCContractReq><requestType>C</requestType><externalRefNumber>" + sExternalRefNumber + "</externalRefNumber><userRefNumber></userRefNumber><productCode>" + sProductCode + "</productCode><operation>" + sOperationCode + "</operation><stage>"+sStage+"</stage><lcCustomer>" + sCustomerId + "</lcCustomer><lcReferenceNo>" + sLcReferenceNo + "</lcReferenceNo><liquidationAmount>"+sLiquidationAmount+"</liquidationAmount><billCurrency>" + sContractCurrency + "</billCurrency><billAmount>" + sContractAmount + "</billAmount><valueDate>"+sFCUBSCurrentDate+"</valueDate><maturityDate>"+sMaturityDate+"</maturityDate><tenorDays>"+sTenorDays+"</tenorDays>"+
							"<transitDays>"+stransitDays+"</transitDays><baseDate>"+sBaseDate+"</baseDate><daysAfterBaseDate>"+sDaysAfterBaseDate+
							"</daysAfterBaseDate><creditValueDate>" + sFCUBSCurrentDate + "</creditValueDate><debitValueDate>" + sFCUBSCurrentDate + "</debitValueDate>"+
							"<transactionDate>" + sFCUBSCurrentDate + "</transactionDate><liquidationDate>"+sLiquidationDate+"</liquidationDate><customerId>" + sCustomerId + "</customerId><bcPartyType>"+sPartyType+"</bcPartyType><operAcceptanceFlag>N</operAcceptanceFlag><limitTracking>"+sLimitTrackFlag+
							"</limitTracking><reDiscountFlag>N</reDiscountFlag><statusControlFlag>M</statusControlFlag><autoLiquidateFlag>M</autoLiquidateFlag>"+
							"<advanceByLoan>N</advanceByLoan><allowRollover>N</allowRollover><linkToLoan>N</linkToLoan><lcRefMessageFlag>N</lcRefMessageFlag>"+
							"<interestfromDate>"+sFCUBSCurrentDate+"</interestfromDate><interestToDate>"+sMaturityDate+"</interestToDate><graceDays>0</graceDays>"+
							"<allowPrepayment>N</allowPrepayment><acceptComFromDate>"+sAcceptComFromDate+"</acceptComFromDate><acceptComToDate>"+sAcceptComToDate+"</acceptComToDate><docOriginalReceived>N</docOriginalReceived><docDuplicateReceived>N</docDuplicateReceived><ackReceived>N"+
							"</ackReceived><ourChargesRefused>N</ourChargesRefused><internalRemarks>"+setProTradeRemark(sWIName)+"</internalRemarks>"+setPartyLimit(sWIName,sRequestCategoryCode,sLimitTrackFlag)+
							"<claimAdviseInSwift>N</claimAdviseInSwift><liquidateUsingCollateral>N</liquidateUsingCollateral><brokerageToBePaidByUs>N"+
							"</brokerageToBePaidByUs><settlementAmount>N</settlementAmount><riskParticipation>"+sRiskParticipation+"</riskParticipation><contractDiscs><contractDisc><code>"+sCode+"</code>"+
							"<description>"+sDescription+"</description><resolved>"+sResolved+"</resolved><resolvedDate>"+sResolvedDate+"</resolvedDate>"+
							"<receivedDate>"+sReceivedDate+"</receivedDate></contractDisc></contractDiscs><txnUDFDetails><UDFFieldName>TXN_ISSUING_CENTER</UDFFieldName><udfFieldValue>" + sUdfFieldValue + "</udfFieldValue></txnUDFDetails><wmsDetails><workItemNumber>" + sWIName + "</workItemNumber></wmsDetails>"+"<branchCode>" + sBranchCreateCode + "</branchCode></modTradeBCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId></APWebService_Input>";
							WriteToLog_showpage("Y","modTradeBCContract_Oper sOutput: "+ callXML);
							sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sCallName +"',"+ convertToPlainString(callXML) +",'"+ REFNO +"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","modTradeBCContract_Oper callXML TFO_DJ_INTEGRATION_CALLS_DTLS: "+ sQuery + " *****************************");	
							sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;						
							WriteToLog_showpage("Y"," insert modTradeBCContract_Oper sOutput: "+ sOutput);	
											
							
							sOutput = socket.connectToSocket(callXML);
							WriteToLog_showpage("Y","modTradeBCContract_Oper callXML sOutput"+ sOutput);
							
							String queryUpdateXML = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'" + sOutput + "'","WI_NAME = '"+ sWIName +"'  and REF_NUM = '"+REFNO+"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","update sOutputUpdateXML input: "+ queryUpdateXML);
							String sOutputUpdateXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",queryUpdateXML);;
							WriteToLog_showpage("Y","update sOutputUpdateXML output: " + sOutputUpdateXML);
							
							xp = new XMLParser(sOutput);
							returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
							String nextValue ="";
							if(returnCode == 0){
								sErrorDescription = xp.getValueOf("errorDescription");
								sContractRefNumber = xp.getValueOf("contractReference");
								sResponse = "Success";
								sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS,TRANSACTION_REFERENCE","'" + sResponse + "','" + sContractRefNumber + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
								out.println(returnCode+"#"+sResponse+"#"+sErrorDescription);
							} else {
								sErrorDescription = xp.getValueOf("errorDescription");
								sResponse = "Failure";
								sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
								out.println("1#"+sResponse+"#"+sErrorDescription);
							}
						}
					}
					else if("DBA".equalsIgnoreCase(sRequestCategoryCode) && "DBA_BL".equalsIgnoreCase(sRequestTypeCode))
					{
						sExternalRefNumber = sOutput.substring(sOutput.indexOf("<PRO_TRADE_REF_NO>")+"<PRO_TRADE_REF_NO>".length(),sOutput.indexOf("</PRO_TRADE_REF_NO>"));
						sOperationCode = sOutput.substring(sOutput.indexOf("<OPERATION_CODE>")+"<OPERATION_CODE>".length(),sOutput.indexOf("</OPERATION_CODE>"));
						sContractCurrency = sOutput.substring(sOutput.indexOf("<TRANSACTION_CURRENCY>")+"<TRANSACTION_CURRENCY>".length(),sOutput.indexOf("</TRANSACTION_CURRENCY>"));
						sContractAmount = sOutput.substring(sOutput.indexOf("<TRANSACTION_AMOUNT>")+"<TRANSACTION_AMOUNT>".length(),sOutput.indexOf("</TRANSACTION_AMOUNT>"));
						sCustomerId = sOutput.substring(sOutput.indexOf("<CUSTOMER_ID>")+"<CUSTOMER_ID>".length(),sOutput.indexOf("</CUSTOMER_ID>"));
						sProductCode = sOutput.substring(sOutput.indexOf("<PRODUCT_TYPE>")+"<PRODUCT_TYPE>".length(),sOutput.indexOf("</PRODUCT_TYPE>"));
						sUdfFieldValue = sOutput.substring(sOutput.indexOf("<BRANCH_CITY>")+"<BRANCH_CITY>".length(),sOutput.indexOf("</BRANCH_CITY>"));
						sRelationshipType = sOutput.substring(sOutput.indexOf("<RELATIONSHIP_TYPE>")+"<RELATIONSHIP_TYPE>".length(),sOutput.indexOf("</RELATIONSHIP_TYPE>"));
						
						sLiquidationAmount="";
						sMaturityDate=sOutput.substring(sOutput.indexOf("<INF_MATURITY_DATE>")+"<INF_MATURITY_DATE>".length(),sOutput.indexOf("</INF_MATURITY_DATE>"));
						String tenorDays=sOutput.substring(sOutput.indexOf("<INF_TENOR_DAYS>")+"<INF_TENOR_DAYS>".length(),sOutput.indexOf("</INF_TENOR_DAYS>"));
						String tenorBase=sOutput.substring(sOutput.indexOf("<INF_TENOR_BASE>")+"<INF_TENOR_BASE>".length(),sOutput.indexOf("</INF_TENOR_BASE>"));
						String actualTenorBase=sOutput.substring(sOutput.indexOf("<INF_ACTUAL_TENOR_BASE>")+"<INF_ACTUAL_TENOR_BASE>".length(),sOutput.indexOf("</INF_ACTUAL_TENOR_BASE>"));
						sBaseDate=sOutput.substring(sOutput.indexOf("<INF_BASE_DOC_DATE>")+"<INF_BASE_DOC_DATE>".length(),sOutput.indexOf("</INF_BASE_DOC_DATE>"));
						sPartyType="DRAWEE";
						
						if("Others".equalsIgnoreCase(tenorBase))
						{
							sDaysAfterBaseDate=actualTenorBase;
						}
						else
						{
							sDaysAfterBaseDate=tenorBase;
						}
						sStage="FIN";
						sLimitTrackFlag="N";

						sBranchCreateCode = fetchBranchCode(sRelationshipType , sWIName);
						if(sCallName.equalsIgnoreCase("modTradeBCContract_Oper")){
							callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeBCContract_Oper</operationType><correlationID>342343</correlationID><modTradeBCContractReq><requestType>C</requestType><externalRefNumber>" + sExternalRefNumber + "</externalRefNumber><userRefNumber></userRefNumber><productCode>" + sProductCode + "</productCode><operation>" + sOperationCode + "</operation><stage>"+sStage+"</stage><billCurrency>" + sContractCurrency + "</billCurrency><billAmount>" + sContractAmount + "</billAmount><valueDate>" + sFCUBSCurrentDate + "</valueDate><maturityDate>"+sMaturityDate+"</maturityDate><tenorDays>"+tenorDays+"</tenorDays>"+
							"<transitDays>0</transitDays><baseDate>" + sBaseDate + "</baseDate><daysAfterBaseDate>"+sDaysAfterBaseDate+
							"</daysAfterBaseDate><creditValueDate>" + sFCUBSCurrentDate + "</creditValueDate><debitValueDate>" + sFCUBSCurrentDate + "</debitValueDate>"+
							"<transactionDate>" + sFCUBSCurrentDate + "</transactionDate><liquidationDate>"+sLiquidationDate+"</liquidationDate><customerId>" + sCustomerId + "</customerId><bcPartyType>"+sPartyType+"</bcPartyType><operAcceptanceFlag>N</operAcceptanceFlag><limitTracking>" + sLimitTrackFlag +"</limitTracking><reDiscountFlag>N</reDiscountFlag><statusControlFlag>M</statusControlFlag><autoLiquidateFlag>M</autoLiquidateFlag>"+
							"<advanceByLoan>N</advanceByLoan><allowRollover>N</allowRollover><linkToLoan>N</linkToLoan><lcRefMessageFlag>N</lcRefMessageFlag>"+
							"<interestfromDate>"+CurrDate+"</interestfromDate><interestToDate>"+sMaturityDate+"</interestToDate><graceDays>0</graceDays>"+
							"<allowPrepayment>N</allowPrepayment><docOriginalReceived>N</docOriginalReceived><docDuplicateReceived>N</docDuplicateReceived><ackReceived>N"+
							"</ackReceived><ourChargesRefused>N</ourChargesRefused><internalRemarks>"+setProTradeRemark(sWIName)+"</internalRemarks>"+
							"<claimAdviseInSwift>N</claimAdviseInSwift><liquidateUsingCollateral>N</liquidateUsingCollateral><brokerageToBePaidByUs>N"+							"</brokerageToBePaidByUs><settlementAmount>N</settlementAmount><riskParticipation>N</riskParticipation><txnUDFDetails><UDFFieldName>TXN_ISSUING_CENTER</UDFFieldName><udfFieldValue>" + sUdfFieldValue + "</udfFieldValue></txnUDFDetails><wmsDetails><workItemNumber>" + sWIName + "</workItemNumber></wmsDetails><branchCode>" + sBranchCreateCode + "</branchCode><contractParties>" + getContractPartyDetailsforCreate(sWIName) + "</contractParties></modTradeBCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId></APWebService_Input>";
							WriteToLog_showpage("Y","modTradeBCContract_Oper sOutput: "+ callXML);
							sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sCallName +"',"+ convertToPlainString(callXML) +",'"+ REFNO +"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","modTradeBCContract_Oper callXML TFO_DJ_INTEGRATION_CALLS_DTLS: "+ sQuery + " *****************************");	
							sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;						
							WriteToLog_showpage("Y"," insert modTradeBCContract_Oper sOutput: "+ sOutput);	
											
							
							sOutput = socket.connectToSocket(callXML);
							WriteToLog_showpage("Y","modTradeBCContract_Oper callXML sOutput"+ sOutput);
							
							String queryUpdateXML = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'" + sOutput + "'","WI_NAME = '"+ sWIName +"'  and REF_NUM = '"+REFNO+"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","update sOutputUpdateXML input: "+ queryUpdateXML);
							String sOutputUpdateXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",queryUpdateXML);;
							WriteToLog_showpage("Y","update sOutputUpdateXML output: " + sOutputUpdateXML);
							
							
							xp = new XMLParser(sOutput);
							returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
							String nextValue ="";
							if(returnCode == 0){
								sErrorDescription = xp.getValueOf("errorDescription");
								sContractRefNumber = xp.getValueOf("contractReference");
								sResponse = "Success";
								sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS,TRANSACTION_REFERENCE","'" + sResponse + "','" + sContractRefNumber + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
								out.println(returnCode+"#"+sResponse+"#"+sErrorDescription);
							} else {
								sErrorDescription = xp.getValueOf("errorDescription");
								sResponse = "Failure";
								sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
								out.println("1#"+sResponse+"#"+sErrorDescription);
							}
						}
					}
					else if("EC".equalsIgnoreCase(sRequestCategoryCode) && "EC_BL".equalsIgnoreCase(sRequestTypeCode))
					{
						String schargAccountNo = "";
						String sportOfLoading ="";
						String spostOfDischarge ="";
						String snameOfVessel ="";
						String sdealNumber ="";
						
						String chargeAccountNo = sOutput.substring(sOutput.indexOf("<INF_CHARGE_ACC_NUM>")+"<INF_CHARGE_ACC_NUM>".length(),sOutput.indexOf("</INF_CHARGE_ACC_NUM>"));
						String portOfLoading   = sOutput.substring(sOutput.indexOf("<BILL_PORT_OF_LOADING>")+"<BILL_PORT_OF_LOADING>".length(),sOutput.indexOf("</BILL_PORT_OF_LOADING>"));
						String portOfDischarge = sOutput.substring(sOutput.indexOf("<BILL_PORT_OF_DISCHARGE>")+"<BILL_PORT_OF_DISCHARGE>".length(),sOutput.indexOf("</BILL_PORT_OF_DISCHARGE>"));
						String nameOfVessel = sOutput.substring(sOutput.indexOf("<BILL_NAME_OF_VESSEL>")+"<BILL_NAME_OF_VESSEL>".length(),sOutput.indexOf("</BILL_NAME_OF_VESSEL>"));
						String dealNumber = sOutput.substring(sOutput.indexOf("<BILL_DEAL_NUMBER>")+"<BILL_DEAL_NUMBER>".length(),sOutput.indexOf("</BILL_DEAL_NUMBER>"));
						
	
						if(!portOfLoading.equals("") && !portOfLoading.equals(null)){
						    sportOfLoading = "<shipmentFrom>"+portOfLoading+"</shipmentFrom>";
						}
						if(!nameOfVessel.equals("") && !nameOfVessel.equals(null)){
							snameOfVessel = "<goodsCarrier>"+nameOfVessel+"</goodsCarrier>";
						} 
						if(!portOfDischarge.equals("") && !portOfDischarge.equals(null)){
							spostOfDischarge="<shipmentTo>"+portOfDischarge+"</shipmentTo>";
						}
						
						if(!chargeAccountNo.equals("") && !chargeAccountNo.equals(null)){
							schargAccountNo="<Account>"+chargeAccountNo+"</Account>";
						}
						
						if(!dealNumber.equals("") && !dealNumber.equals(null)){
							sdealNumber="<negotiatedDealRefNo>"+dealNumber+"</negotiatedDealRefNo>";
						}
						
						
						String goodsCode = "";
						String goodsDecsriptionelcbbl = "";
						String ECBLNEWTAGS ="";
						String protsettlinstbank ="";
						String insttobankelcbbl="";
						String sUdfFieldValue1="";
						String sUdfFieldValue2="";
						String sUdfFieldValue3="";
							protsettlinstbank =  sOutput.substring(sOutput.indexOf("<PRO_TRADE_SETTLEMENT_INST>")+"<PRO_TRADE_SETTLEMENT_INST>".length(),sOutput.indexOf("</PRO_TRADE_SETTLEMENT_INST>"));
							if(!protsettlinstbank.isEmpty()){
								goodsCode = "<goodsCode>OTHERS</goodsCode>";
								goodsDecsriptionelcbbl = "<goodsDescription>"+protsettlinstbank+"</goodsDescription>";
							}
							insttobankelcbbl =  sOutput.substring(sOutput.indexOf("<INSTRCTN_TO_BANK>")+"<INSTRCTN_TO_BANK>".length(),sOutput.indexOf("</INSTRCTN_TO_BANK>"));
							sUdfFieldValue1 = sOutput.substring(sOutput.indexOf("<COURIER_COMPANY>")+"<COURIER_COMPANY>".length(),sOutput.indexOf("</COURIER_COMPANY>"));
							sUdfFieldValue2 =  sOutput.substring(sOutput.indexOf("<COURIER_AWB_NUMBER>")+"<COURIER_AWB_NUMBER>".length(),sOutput.indexOf("</COURIER_AWB_NUMBER>"));
							sUdfFieldValue3 =  sOutput.substring(sOutput.indexOf("<DISCOUNT_ON_ACCEP>")+"<DISCOUNT_ON_ACCEP>".length(),sOutput.indexOf("</DISCOUNT_ON_ACCEP>"));
							// adviceDetails=fetchAdviceDetails(sWIName,sRequestTypeCode);

						
						
						sExternalRefNumber = sOutput.substring(sOutput.indexOf("<PRO_TRADE_REF_NO>")+"<PRO_TRADE_REF_NO>".length(),sOutput.indexOf("</PRO_TRADE_REF_NO>"));
						sOperationCode = sOutput.substring(sOutput.indexOf("<OPERATION_CODE>")+"<OPERATION_CODE>".length(),sOutput.indexOf("</OPERATION_CODE>"));
						sContractCurrency = sOutput.substring(sOutput.indexOf("<TRANSACTION_CURRENCY>")+"<TRANSACTION_CURRENCY>".length(),sOutput.indexOf("</TRANSACTION_CURRENCY>"));
						sContractAmount = sOutput.substring(sOutput.indexOf("<TRANSACTION_AMOUNT>")+"<TRANSACTION_AMOUNT>".length(),sOutput.indexOf("</TRANSACTION_AMOUNT>"));
						sCustomerId = sOutput.substring(sOutput.indexOf("<CUSTOMER_ID>")+"<CUSTOMER_ID>".length(),sOutput.indexOf("</CUSTOMER_ID>"));
						sProductCode = sOutput.substring(sOutput.indexOf("<PRODUCT_TYPE>")+"<PRODUCT_TYPE>".length(),sOutput.indexOf("</PRODUCT_TYPE>"));
						sUdfFieldValue = sOutput.substring(sOutput.indexOf("<BRANCH_CITY>")+"<BRANCH_CITY>".length(),sOutput.indexOf("</BRANCH_CITY>"));
						sRelationshipType = sOutput.substring(sOutput.indexOf("<RELATIONSHIP_TYPE>")+"<RELATIONSHIP_TYPE>".length(),sOutput.indexOf("</RELATIONSHIP_TYPE>"));
						
						sLiquidationAmount="";
						sMaturityDate=sOutput.substring(sOutput.indexOf("<INF_MATURITY_DATE>")+"<INF_MATURITY_DATE>".length(),sOutput.indexOf("</INF_MATURITY_DATE>"));
						String tenorDays=sOutput.substring(sOutput.indexOf("<INF_TENOR_DAYS>")+"<INF_TENOR_DAYS>".length(),sOutput.indexOf("</INF_TENOR_DAYS>"));
						String tenorBase=sOutput.substring(sOutput.indexOf("<INF_TENOR_BASE>")+"<INF_TENOR_BASE>".length(),sOutput.indexOf("</INF_TENOR_BASE>"));
						String actualTenorBase=sOutput.substring(sOutput.indexOf("<INF_ACTUAL_TENOR_BASE>")+"<INF_ACTUAL_TENOR_BASE>".length(),sOutput.indexOf("</INF_ACTUAL_TENOR_BASE>"));
						sBaseDate=sOutput.substring(sOutput.indexOf("<INF_BASE_DOC_DATE>")+"<INF_BASE_DOC_DATE>".length(),sOutput.indexOf("</INF_BASE_DOC_DATE>"));
						sPartyType="DRAWER";
						
						if("T3U4".equalsIgnoreCase(sProductCode))
						{
							sTenorDays=tenorDays;
							stransitDays="0";
							sDaysAfterBaseDate=tenorBase;
							if("EC_O".equalsIgnoreCase(sDaysAfterBaseDate)){
								sDaysAfterBaseDate=actualTenorBase;
							}
						}else if("T3S4".equalsIgnoreCase(sProductCode))
						{
							sTenorDays="0";
							stransitDays=tenorDays;
							sDaysAfterBaseDate="";
						}
						sStage="FIN";
						sLimitTrackFlag="N";

						sBranchCreateCode = fetchBranchCode(sRelationshipType , sWIName);
						String sDocDetls =getDocumentDetailsforCreateAmend(sWIName,sRequestTypeCode);
						String freeFormatTextDesc="Please note the documents have been dispatched to Issuing bank / Collecting bank Vide "+sUdfFieldValue1+" in the AWB No:"+sUdfFieldValue2;
                        String fftCode="ELCACKOUR";
				        StringBuilder adviceDetails = new StringBuilder();

						if(!"".equalsIgnoreCase(fftCode)){
							  String partyID=getPartyIdFromPartyType("'DRAWER'",sWIName,sRequestTypeCode);
                              adviceDetails.append("<adviseRequired>"); adviceDetails.append("ACKNOWLEDGEMENT"); adviceDetails.append("</adviseRequired>");
			                  adviceDetails.append("<adviceParty>"); adviceDetails.append("DRAWER"); adviceDetails.append("</adviceParty>");
			                  adviceDetails.append("<advicePartyId>"); adviceDetails.append(partyID); adviceDetails.append("</advicePartyId>");
						}else{
							  adviceDetails.append("<adviseRequired>"); adviceDetails.append(""); adviceDetails.append("</adviseRequired>");
			                  adviceDetails.append("<adviceParty>"); adviceDetails.append(""); adviceDetails.append("</adviceParty>");
			                  adviceDetails.append("<advicePartyId>"); adviceDetails.append(""); adviceDetails.append("</advicePartyId>");
						}
						
			             adviceDetails.toString();
						if(sCallName.equalsIgnoreCase("modTradeBCContract_Oper")){
							callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeBCContract_Oper</operationType><correlationID>342343</correlationID><modTradeBCContractReq><requestType>C</requestType><externalRefNumber>" + sExternalRefNumber + "</externalRefNumber><userRefNumber></userRefNumber><productCode>" + sProductCode + "</productCode><operation>" + sOperationCode + "</operation><stage>"+sStage+"</stage><billCurrency>" + sContractCurrency + "</billCurrency><billAmount>" + sContractAmount + "</billAmount><valueDate>" + sFCUBSCurrentDate + "</valueDate><maturityDate>"+sMaturityDate+"</maturityDate><tenorDays>"+sTenorDays+"</tenorDays>"+
							"<transitDays>"+stransitDays+"</transitDays><baseDate>" + sBaseDate + "</baseDate><daysAfterBaseDate>"+sDaysAfterBaseDate+
							"</daysAfterBaseDate><creditValueDate>" + sFCUBSCurrentDate + "</creditValueDate><debitValueDate>" + sFCUBSCurrentDate + "</debitValueDate>"+
							"<transactionDate>" + sFCUBSCurrentDate + "</transactionDate><liquidationDate>"+sLiquidationDate+"</liquidationDate><customerId>" + sCustomerId + "</customerId><bcPartyType>"+sPartyType+"</bcPartyType><operAcceptanceFlag>N</operAcceptanceFlag><limitTracking>" + sLimitTrackFlag +"</limitTracking><reDiscountFlag>N</reDiscountFlag><statusControlFlag>M</statusControlFlag><autoLiquidateFlag>M</autoLiquidateFlag>"+"<advanceByLoan>N</advanceByLoan><allowRollover>N</allowRollover><linkToLoan>N</linkToLoan><lcRefMessageFlag>N</lcRefMessageFlag><graceDays>0</graceDays>"+"<allowPrepayment>N</allowPrepayment><docOriginalReceived>N</docOriginalReceived><docDuplicateReceived>N</docDuplicateReceived><ackReceived>N"+
							"</ackReceived><ourChargesRefused>N</ourChargesRefused><internalRemarks>"+insttobankelcbbl+"</internalRemarks>"+
							"<claimAdviseInSwift>N</claimAdviseInSwift><liquidateUsingCollateral>N</liquidateUsingCollateral><brokerageToBePaidByUs>N"+							"</brokerageToBePaidByUs><settlementAmount>N</settlementAmount><riskParticipation>N</riskParticipation><goodsDetails>"+goodsCode+goodsDecsriptionelcbbl+"</goodsDetails>"+schargAccountNo+sdealNumber+"<shipmentDetails>"+spostOfDischarge+snameOfVessel+sportOfLoading+"</shipmentDetails><documentDetails>" +sDocDetls+ "</documentDetails><txnUDFDetails><UDFFieldName>TXN_ISSUING_CENTER</UDFFieldName><udfFieldValue>" + sUdfFieldValue + "</udfFieldValue><UDFFieldName>COURIER_COMPANY</UDFFieldName><udfFieldValue>" + sUdfFieldValue1 + "</udfFieldValue><UDFFieldName>COURIER_AWB_NUMBER</UDFFieldName><udfFieldValue>" + sUdfFieldValue2 + "</udfFieldValue></txnUDFDetails><wmsDetails><workItemNumber>" + sWIName + "</workItemNumber></wmsDetails><freeFormatTextDtls><freeFormatTextDtl><freeFormatTextCode>ELCACKOUR</freeFormatTextCode><freeFormatTextDescription>"+freeFormatTextDesc+"</freeFormatTextDescription></freeFormatTextDtl></freeFormatTextDtls><branchCode>" + sBranchCreateCode + "</branchCode><contractParties>" + getContractPartyDetailsforCreate(sWIName) + "</contractParties>"+adviceDetails+"</modTradeBCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId></APWebService_Input>";
							WriteToLog_showpage("Y","modTradeBCContract_Oper sOutput: "+ callXML);
							sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sCallName +"',"+ convertToPlainString(callXML) +",'"+ REFNO +"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","modTradeBCContract_Oper callXML TFO_DJ_INTEGRATION_CALLS_DTLS: "+ sQuery + " *****************************");	
							sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;						
							WriteToLog_showpage("Y"," insert modTradeBCContract_Oper sOutput: "+ sOutput);	
											
							
							sOutput = socket.connectToSocket(callXML);
							WriteToLog_showpage("Y","modTradeBCContract_Oper callXML sOutput"+ sOutput);
							
							String queryUpdateXML = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'" + sOutput + "'","WI_NAME = '"+ sWIName +"'  and REF_NUM = '"+REFNO+"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","update sOutputUpdateXML input: "+ queryUpdateXML);
							String sOutputUpdateXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",queryUpdateXML);;
							WriteToLog_showpage("Y","update sOutputUpdateXML output: " + sOutputUpdateXML);
							
							xp = new XMLParser(sOutput);
							returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
							String nextValue ="";
							if(returnCode == 0){
								sErrorDescription = xp.getValueOf("errorDescription");
								sContractRefNumber = xp.getValueOf("contractReference");
								sResponse = "Success";
								sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS,TRANSACTION_REFERENCE","'" + sResponse + "','" + sContractRefNumber + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
								out.println(returnCode+"#"+sResponse+"#"+sErrorDescription);
							} else {
								sErrorDescription = xp.getValueOf("errorDescription");
								sResponse = "Failure";
								sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
								out.println("1#"+sResponse+"#"+sErrorDescription);
							}
						}
					}else if("EC".equalsIgnoreCase(sRequestCategoryCode) &&  "EC_LDDI".equalsIgnoreCase(sRequestTypeCode))
					{
						String schargAccountNo = "";
						String sportOfLoading ="";
						String spostOfDischarge ="";
						String snameOfVessel ="";
						String sdealNumber ="";
						
						String chargeAccountNo = sOutput.substring(sOutput.indexOf("<INF_CHARGE_ACC_NUM>")+"<INF_CHARGE_ACC_NUM>".length(),sOutput.indexOf("</INF_CHARGE_ACC_NUM>"));
						String portOfLoading   = sOutput.substring(sOutput.indexOf("<BILL_PORT_OF_LOADING>")+"<BILL_PORT_OF_LOADING>".length(),sOutput.indexOf("</BILL_PORT_OF_LOADING>"));
						String portOfDischarge = sOutput.substring(sOutput.indexOf("<BILL_PORT_OF_DISCHARGE>")+"<BILL_PORT_OF_DISCHARGE>".length(),sOutput.indexOf("</BILL_PORT_OF_DISCHARGE>"));
						String nameOfVessel = sOutput.substring(sOutput.indexOf("<BILL_NAME_OF_VESSEL>")+"<BILL_NAME_OF_VESSEL>".length(),sOutput.indexOf("</BILL_NAME_OF_VESSEL>"));
						String dealNumber = sOutput.substring(sOutput.indexOf("<BILL_DEAL_NUMBER>")+"<BILL_DEAL_NUMBER>".length(),sOutput.indexOf("</BILL_DEAL_NUMBER>"));
						
	
						if(!portOfLoading.equals("") && !portOfLoading.equals(null)){
						    sportOfLoading = "<shipmentFrom>"+portOfLoading+"</shipmentFrom>";
						}
						if(!nameOfVessel.equals("") && !nameOfVessel.equals(null)){
							snameOfVessel = "<goodsCarrier>"+nameOfVessel+"</goodsCarrier>";
						} 
						if(!portOfDischarge.equals("") && !portOfDischarge.equals(null)){
							spostOfDischarge="<shipmentTo>"+portOfDischarge+"</shipmentTo>";
						}
						
						if(!chargeAccountNo.equals("") && !chargeAccountNo.equals(null)){
							schargAccountNo="<Account>"+chargeAccountNo+"</Account>";
						}
						
						if(!dealNumber.equals("") && !dealNumber.equals(null)){
							sdealNumber="<negotiatedDealRefNo>"+dealNumber+"</negotiatedDealRefNo>";
						}
						
						
						String goodsCode = "";
						String goodsDecsriptionelcbbl = "";
						String ECBLNEWTAGS ="";
						String protsettlinstbank ="";
						String insttobankelcbbl="";
						String sUdfFieldValue1="";
						String sUdfFieldValue2="";
						String sUdfFieldValue3="";
						String adviceDetails="";
						sExternalRefNumber = sOutput.substring(sOutput.indexOf("<PRO_TRADE_REF_NO>")+"<PRO_TRADE_REF_NO>".length(),sOutput.indexOf("</PRO_TRADE_REF_NO>"));
						sOperationCode = sOutput.substring(sOutput.indexOf("<OPERATION_CODE>")+"<OPERATION_CODE>".length(),sOutput.indexOf("</OPERATION_CODE>"));
						sContractCurrency = sOutput.substring(sOutput.indexOf("<TRANSACTION_CURRENCY>")+"<TRANSACTION_CURRENCY>".length(),sOutput.indexOf("</TRANSACTION_CURRENCY>"));
						sContractAmount = sOutput.substring(sOutput.indexOf("<TRANSACTION_AMOUNT>")+"<TRANSACTION_AMOUNT>".length(),sOutput.indexOf("</TRANSACTION_AMOUNT>"));
						sCustomerId = sOutput.substring(sOutput.indexOf("<CUSTOMER_ID>")+"<CUSTOMER_ID>".length(),sOutput.indexOf("</CUSTOMER_ID>"));
						sProductCode = sOutput.substring(sOutput.indexOf("<PRODUCT_TYPE>")+"<PRODUCT_TYPE>".length(),sOutput.indexOf("</PRODUCT_TYPE>"));
						sUdfFieldValue = sOutput.substring(sOutput.indexOf("<BRANCH_CITY>")+"<BRANCH_CITY>".length(),sOutput.indexOf("</BRANCH_CITY>"));
						sRelationshipType = sOutput.substring(sOutput.indexOf("<RELATIONSHIP_TYPE>")+"<RELATIONSHIP_TYPE>".length(),sOutput.indexOf("</RELATIONSHIP_TYPE>"));
						
						sLiquidationAmount="";
						sMaturityDate=sOutput.substring(sOutput.indexOf("<INF_MATURITY_DATE>")+"<INF_MATURITY_DATE>".length(),sOutput.indexOf("</INF_MATURITY_DATE>"));
						String tenorDays=sOutput.substring(sOutput.indexOf("<INF_TENOR_DAYS>")+"<INF_TENOR_DAYS>".length(),sOutput.indexOf("</INF_TENOR_DAYS>"));
						String tenorBase=sOutput.substring(sOutput.indexOf("<INF_TENOR_BASE>")+"<INF_TENOR_BASE>".length(),sOutput.indexOf("</INF_TENOR_BASE>"));
						String actualTenorBase=sOutput.substring(sOutput.indexOf("<INF_ACTUAL_TENOR_BASE>")+"<INF_ACTUAL_TENOR_BASE>".length(),sOutput.indexOf("</INF_ACTUAL_TENOR_BASE>"));
						sBaseDate=sOutput.substring(sOutput.indexOf("<INF_BASE_DOC_DATE>")+"<INF_BASE_DOC_DATE>".length(),sOutput.indexOf("</INF_BASE_DOC_DATE>"));
						sPartyType="DRAWER";
						
						if("T3U4".equalsIgnoreCase(sProductCode))
						{
							sTenorDays=tenorDays;
							stransitDays="0";
							sDaysAfterBaseDate=tenorBase;
							if("EC_O".equalsIgnoreCase(sDaysAfterBaseDate)){
								sDaysAfterBaseDate=actualTenorBase;
							}
						}else if("T3S4".equalsIgnoreCase(sProductCode))
						{
							sTenorDays="0";
							stransitDays=tenorDays;
							sDaysAfterBaseDate="";
						}
						sStage="FIN";
						sLimitTrackFlag="N";

						sBranchCreateCode = fetchBranchCode(sRelationshipType , sWIName);
						String sDocDetls =getDocumentDetailsforCreateAmend(sWIName,sRequestTypeCode);
						String freeFormatTextDesc="Please note the documents have been dispatched to Issuing bank / Collecting bank Vide "+sUdfFieldValue1+" in the AWB No:"+sUdfFieldValue2;

						if(sCallName.equalsIgnoreCase("modTradeBCContract_Oper")){
							callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeBCContract_Oper</operationType><correlationID>342343</correlationID><modTradeBCContractReq><requestType>C</requestType><externalRefNumber>" + sExternalRefNumber + "</externalRefNumber><userRefNumber></userRefNumber><productCode>" + sProductCode + "</productCode><operation>" + sOperationCode + "</operation><stage>"+sStage+"</stage><billCurrency>" + sContractCurrency + "</billCurrency><billAmount>" + sContractAmount + "</billAmount><valueDate>" + sFCUBSCurrentDate + "</valueDate><maturityDate>"+sMaturityDate+"</maturityDate><tenorDays>"+sTenorDays+"</tenorDays>"+
							"<transitDays>"+stransitDays+"</transitDays><baseDate>" + sBaseDate + "</baseDate><daysAfterBaseDate>"+sDaysAfterBaseDate+
							"</daysAfterBaseDate><creditValueDate>" + sFCUBSCurrentDate + "</creditValueDate><debitValueDate>" + sFCUBSCurrentDate + "</debitValueDate>"+
							"<transactionDate>" + sFCUBSCurrentDate + "</transactionDate><liquidationDate>"+sLiquidationDate+"</liquidationDate><customerId>" + sCustomerId + "</customerId><bcPartyType>"+sPartyType+"</bcPartyType><operAcceptanceFlag>N</operAcceptanceFlag><limitTracking>" + sLimitTrackFlag +"</limitTracking><reDiscountFlag>N</reDiscountFlag><statusControlFlag>M</statusControlFlag><autoLiquidateFlag>M</autoLiquidateFlag>"+"<advanceByLoan>N</advanceByLoan><allowRollover>N</allowRollover><linkToLoan>N</linkToLoan><lcRefMessageFlag>N</lcRefMessageFlag><graceDays>0</graceDays>"+"<allowPrepayment>N</allowPrepayment><docOriginalReceived>N</docOriginalReceived><docDuplicateReceived>N</docDuplicateReceived><ackReceived>N"+
							"</ackReceived><ourChargesRefused>N</ourChargesRefused><internalRemarks>"+insttobankelcbbl+"</internalRemarks>"+
							"<claimAdviseInSwift>N</claimAdviseInSwift><liquidateUsingCollateral>N</liquidateUsingCollateral><brokerageToBePaidByUs>N"+							"</brokerageToBePaidByUs><settlementAmount>N</settlementAmount><riskParticipation>N</riskParticipation><goodsDetails>"+goodsCode+goodsDecsriptionelcbbl+"</goodsDetails>"+schargAccountNo+sdealNumber+"<shipmentDetails>"+spostOfDischarge+snameOfVessel+sportOfLoading+"</shipmentDetails><documentDetails>" +sDocDetls+ "</documentDetails><txnUDFDetails><UDFFieldName>TXN_ISSUING_CENTER</UDFFieldName><udfFieldValue>" + sUdfFieldValue + "</udfFieldValue><UDFFieldName>COURIER_COMPANY</UDFFieldName><udfFieldValue>" + sUdfFieldValue1 + "</udfFieldValue><UDFFieldName>COURIER_AWB_NUMBER</UDFFieldName><udfFieldValue>" + sUdfFieldValue2 + "</udfFieldValue></txnUDFDetails><wmsDetails><workItemNumber>" + sWIName + "</workItemNumber></wmsDetails><freeFormatTextDtls><freeFormatTextDtl><freeFormatTextCode>ELCACKOUR</freeFormatTextCode><freeFormatTextDescription>"+freeFormatTextDesc+"</freeFormatTextDescription></freeFormatTextDtl></freeFormatTextDtls><branchCode>" + sBranchCreateCode + "</branchCode><contractParties>" + getContractPartyDetailsforCreate(sWIName) + "</contractParties>"+adviceDetails+"</modTradeBCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId></APWebService_Input>";
							WriteToLog_showpage("Y","modTradeBCContract_Oper sOutput: "+ callXML);
							sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sCallName +"',"+ convertToPlainString(callXML) +",'"+ REFNO +"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","modTradeBCContract_Oper callXML TFO_DJ_INTEGRATION_CALLS_DTLS: "+ sQuery + " *****************************");	
							sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;						
							WriteToLog_showpage("Y"," insert modTradeBCContract_Oper sOutput: "+ sOutput);	
											
							
							sOutput = socket.connectToSocket(callXML);
							WriteToLog_showpage("Y","modTradeBCContract_Oper callXML sOutput"+ sOutput);
							
							String queryUpdateXML = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'" + sOutput + "'","WI_NAME = '"+ sWIName +"'  and REF_NUM = '"+REFNO+"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","update sOutputUpdateXML input: "+ queryUpdateXML);
							String sOutputUpdateXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",queryUpdateXML);;
							WriteToLog_showpage("Y","update sOutputUpdateXML output: " + sOutputUpdateXML);
							
							xp = new XMLParser(sOutput);
							returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
							String nextValue ="";
							if(returnCode == 0){
								sErrorDescription = xp.getValueOf("errorDescription");
								sContractRefNumber = xp.getValueOf("contractReference");
								sResponse = "Success";
								sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS,TRANSACTION_REFERENCE","'" + sResponse + "','" + sContractRefNumber + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
								out.println(returnCode+"#"+sResponse+"#"+sErrorDescription);
							} else {
								sErrorDescription = xp.getValueOf("errorDescription");
								sResponse = "Failure";
								sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
								out.println("1#"+sResponse+"#"+sErrorDescription);
							}
						}
					}
					else if("ELCB".equalsIgnoreCase(sRequestCategoryCode) && "ELCB_BL".equalsIgnoreCase(sRequestTypeCode))
					{
						String schargAccountNo = "";
						String sportOfLoading ="";
						String spostOfDischarge ="";
						String snameOfVessel ="";
						String sdealNumber ="";
						
						String chargeAccountNo = sOutput.substring(sOutput.indexOf("<INF_CHARGE_ACC_NUM>")+"<INF_CHARGE_ACC_NUM>".length(),sOutput.indexOf("</INF_CHARGE_ACC_NUM>"));
						String portOfLoading   = sOutput.substring(sOutput.indexOf("<BILL_PORT_OF_LOADING>")+"<BILL_PORT_OF_LOADING>".length(),sOutput.indexOf("</BILL_PORT_OF_LOADING>"));
						String portOfDischarge = sOutput.substring(sOutput.indexOf("<BILL_PORT_OF_DISCHARGE>")+"<BILL_PORT_OF_DISCHARGE>".length(),sOutput.indexOf("</BILL_PORT_OF_DISCHARGE>"));
						String nameOfVessel = sOutput.substring(sOutput.indexOf("<BILL_NAME_OF_VESSEL>")+"<BILL_NAME_OF_VESSEL>".length(),sOutput.indexOf("</BILL_NAME_OF_VESSEL>"));
						String dealNumber = sOutput.substring(sOutput.indexOf("<BILL_DEAL_NUMBER>")+"<BILL_DEAL_NUMBER>".length(),sOutput.indexOf("</BILL_DEAL_NUMBER>"));
						
						
						if(!portOfLoading.equals("") && !portOfLoading.equals(null)){
						    sportOfLoading = "<shipmentFrom>"+portOfLoading+"</shipmentFrom>";
						}
						if(!nameOfVessel.equals("") && !nameOfVessel.equals(null)){
							snameOfVessel = "<goodsCarrier>"+nameOfVessel+"</goodsCarrier>";
						} 
						if(!portOfDischarge.equals("") && !portOfDischarge.equals(null)){
							spostOfDischarge="<shipmentTo>"+portOfDischarge+"</shipmentTo>";
						}
						
						if(!chargeAccountNo.equals("") && !chargeAccountNo.equals(null)){
							schargAccountNo="<Account>"+chargeAccountNo+"</Account>";
						}
						
						if(!dealNumber.equals("") && !dealNumber.equals(null)){
							sdealNumber="<negotiatedDealRefNo>"+dealNumber+"</negotiatedDealRefNo>";
						}
						
						String protsettlinstbank =  sOutput.substring(sOutput.indexOf("<PRO_TRADE_SETTLEMENT_INST>")+"<PRO_TRADE_SETTLEMENT_INST>".length(),sOutput.indexOf("</PRO_TRADE_SETTLEMENT_INST>"));
						String goodsCode = "";
						String goodsDecsriptionelcbbl = "";
						if(!protsettlinstbank.isEmpty()){
							goodsCode = "<goodsCode>OTHERS</goodsCode>";
							goodsDecsriptionelcbbl = "<goodsDescription>"+protsettlinstbank+"</goodsDescription>";
						}
						String insttobankelcbbl =  sOutput.substring(sOutput.indexOf("<INSTRCTN_TO_BANK>")+"<INSTRCTN_TO_BANK>".length(),sOutput.indexOf("</INSTRCTN_TO_BANK>"));
						String sUdfFieldValue1 = sOutput.substring(sOutput.indexOf("<COURIER_COMPANY>")+"<COURIER_COMPANY>".length(),sOutput.indexOf("</COURIER_COMPANY>"));
						String sUdfFieldValue2 =  sOutput.substring(sOutput.indexOf("<COURIER_AWB_NUMBER>")+"<COURIER_AWB_NUMBER>".length(),sOutput.indexOf("</COURIER_AWB_NUMBER>"));
						String sUdfFieldValue3 =  sOutput.substring(sOutput.indexOf("<DISCOUNT_ON_ACCEP>")+"<DISCOUNT_ON_ACCEP>".length(),sOutput.indexOf("</DISCOUNT_ON_ACCEP>"));
						
						
						
						sExternalRefNumber = sOutput.substring(sOutput.indexOf("<PRO_TRADE_REF_NO>")+"<PRO_TRADE_REF_NO>".length(),sOutput.indexOf("</PRO_TRADE_REF_NO>"));
						sOperationCode = sOutput.substring(sOutput.indexOf("<OPERATION_CODE>")+"<OPERATION_CODE>".length(),sOutput.indexOf("</OPERATION_CODE>"));
						sContractCurrency = sOutput.substring(sOutput.indexOf("<TRANSACTION_CURRENCY>")+"<TRANSACTION_CURRENCY>".length(),sOutput.indexOf("</TRANSACTION_CURRENCY>"));
						sContractAmount = sOutput.substring(sOutput.indexOf("<TRANSACTION_AMOUNT>")+"<TRANSACTION_AMOUNT>".length(),sOutput.indexOf("</TRANSACTION_AMOUNT>"));
						sCustomerId = sOutput.substring(sOutput.indexOf("<CUSTOMER_ID>")+"<CUSTOMER_ID>".length(),sOutput.indexOf("</CUSTOMER_ID>"));
						sProductCode = sOutput.substring(sOutput.indexOf("<PRODUCT_TYPE>")+"<PRODUCT_TYPE>".length(),sOutput.indexOf("</PRODUCT_TYPE>"));
						sUdfFieldValue = sOutput.substring(sOutput.indexOf("<BRANCH_CITY>")+"<BRANCH_CITY>".length(),sOutput.indexOf("</BRANCH_CITY>"));
						sRelationshipType = sOutput.substring(sOutput.indexOf("<RELATIONSHIP_TYPE>")+"<RELATIONSHIP_TYPE>".length(),sOutput.indexOf("</RELATIONSHIP_TYPE>"));
						//new fields
						sLcReferenceNo=sOutput.substring(sOutput.indexOf("<TRANSACTION_ADCB_LC_REFERENCE>")+"<TRANSACTION_ADCB_LC_REFERENCE>".length(),sOutput.indexOf("</TRANSACTION_ADCB_LC_REFERENCE>"));
						sLiquidationAmount="";
						sMaturityDate=sOutput.substring(sOutput.indexOf("<INF_MATURITY_DATE>")+"<INF_MATURITY_DATE>".length(),sOutput.indexOf("</INF_MATURITY_DATE>"));
						String tenorDays=sOutput.substring(sOutput.indexOf("<INF_TENOR_DAYS>")+"<INF_TENOR_DAYS>".length(),sOutput.indexOf("</INF_TENOR_DAYS>"));
						String tenorBase=sOutput.substring(sOutput.indexOf("<INF_TENOR_BASE>")+"<INF_TENOR_BASE>".length(),sOutput.indexOf("</INF_TENOR_BASE>"));
						String actualTenorBase=sOutput.substring(sOutput.indexOf("<INF_ACTUAL_TENOR_BASE>")+"<INF_ACTUAL_TENOR_BASE>".length(),sOutput.indexOf("</INF_ACTUAL_TENOR_BASE>"));
						String discrepancyDetails=sOutput.substring(sOutput.indexOf("<DISCREPANCY_DTLS>")+"<DISCREPANCY_DTLS>".length(),sOutput.indexOf("</DISCREPANCY_DTLS>"));
						sBaseDate=sOutput.substring(sOutput.indexOf("<INF_BASE_DOC_DATE>")+"<INF_BASE_DOC_DATE>".length(),sOutput.indexOf("</INF_BASE_DOC_DATE>"));
						String limitRefNumber=sOutput.substring(sOutput.indexOf("<LC_LIMIT_LINE>")+"<LC_LIMIT_LINE>".length(),sOutput.indexOf("</LC_LIMIT_LINE>"));
						String lclimitPartyType=sOutput.substring(sOutput.indexOf("<LIMIT_PARTY_TYPE>")+"<LIMIT_PARTY_TYPE>".length(),sOutput.indexOf("</LIMIT_PARTY_TYPE>"));
						sPartyType="DRAWER";
						String partyLimitCountAvailable = getLimitTrackingFlag(sWIName);
						sRiskParticipation=checkPartyTypeContract(sWIName);
						if("T3U3".equalsIgnoreCase(sProductCode) || "I3U3".equalsIgnoreCase(sProductCode) || "T3U6".equalsIgnoreCase(sProductCode) || "T3U7".equalsIgnoreCase(sProductCode))
						{
							sTenorDays=tenorDays;
							stransitDays="0";
							sDaysAfterBaseDate=tenorBase;
							if("ELCB_O".equalsIgnoreCase(sDaysAfterBaseDate)){
								sDaysAfterBaseDate=actualTenorBase;
							}
						}else if("T3S3".equalsIgnoreCase(sProductCode) || "I3S3".equalsIgnoreCase(sProductCode) || "T3S5".equalsIgnoreCase(sProductCode))
						{
							sTenorDays="0";
							stransitDays=tenorDays;
							sDaysAfterBaseDate="";
						}
						if(!"".equalsIgnoreCase(limitRefNumber)){
							sLimitTrackFlag = "Y";
						}
						else{
							sLimitTrackFlag = "N";
						}
						sStage="FIN";
						if("Y".equalsIgnoreCase(sLimitTrackFlag)){	
							limitSerialNumber = "1";
							sLimitOperation="ACC";
							if("".equalsIgnoreCase(lclimitPartyType)){
								limitPartyType = "DRAWEE"; // check for limit pary type field value newly added
							}
							else{
								limitPartyType = lclimitPartyType;
							}							
							limitLinkageType = "F";
							limitPercentContribution = "100";
							limitAmountTag = "BILL_OS_AMT";
							limitLineRefNumber=limitRefNumber;
							limitLineCustomerId=getPartyIdFromLimitPartyType(limitPartyType,sWIName);
						}else{
							limitSerialNumber = "";
							sLimitOperation="";
							limitPartyType ="";
							limitLinkageType = "";
							limitPercentContribution = "";
							limitAmountTag = "";
							limitLineRefNumber="";
							limitLineCustomerId="";
						}
                        String udf3Data=	"";					
                        if(!("T3U3".equalsIgnoreCase(sProductCode) || "T3U6".equalsIgnoreCase(sProductCode) || "T3U7".equalsIgnoreCase(sProductCode)||"I3U3".equalsIgnoreCase(sProductCode) ))
						{
							sUdfFieldValue3="";
							udf3Data="";
						}else{
							udf3Data="<UDFFieldName>DISCOUNT_ON_ACCEP</UDFFieldName><udfFieldValue>" + sUdfFieldValue3 + "</udfFieldValue>";
						}
						sBranchCreateCode = fetchBranchCode(sRelationshipType , sWIName);
						String sDocDetls =getDocumentDetailsforCreateAmend(sWIName,sRequestTypeCode);
						String freeFormatTextDesc="Please note the documents have been dispatched to Issuing bank / Collecting bank Vide "+sUdfFieldValue1+" in the AWB No:"+sUdfFieldValue2;
						String fftCode="ELCACKOUR";
				        StringBuilder adviceDetails = new StringBuilder();

						if(!"".equalsIgnoreCase(fftCode)){
							  String partyID=getPartyIdFromPartyType("'DRAWER'",sWIName,sRequestTypeCode);
                              adviceDetails.append("<adviseRequired>"); adviceDetails.append("ACKNOWLEDGEMENT"); adviceDetails.append("</adviseRequired>");
			                  adviceDetails.append("<adviceParty>"); adviceDetails.append("DRAWER"); adviceDetails.append("</adviceParty>");
			                  adviceDetails.append("<advicePartyId>"); adviceDetails.append(partyID); adviceDetails.append("</advicePartyId>");
						}else{
							  adviceDetails.append("<adviseRequired>"); adviceDetails.append(""); adviceDetails.append("</adviseRequired>");
			                  adviceDetails.append("<adviceParty>"); adviceDetails.append(""); adviceDetails.append("</adviceParty>");
			                  adviceDetails.append("<advicePartyId>"); adviceDetails.append(""); adviceDetails.append("</advicePartyId>");
						}
						
			             adviceDetails.toString();
						if(sCallName.equalsIgnoreCase("modTradeBCContract_Oper")){
							callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeBCContract_Oper</operationType><correlationID>342343</correlationID><modTradeBCContractReq><requestType>C</requestType><externalRefNumber>" + sExternalRefNumber + "</externalRefNumber><userRefNumber></userRefNumber><productCode>" + sProductCode + "</productCode><operation>" + sOperationCode + "</operation><stage>"+sStage+"</stage><lcCustomer>" + sCustomerId + "</lcCustomer><lcReferenceNo>" + sLcReferenceNo + "</lcReferenceNo><liquidationAmount>"+sLiquidationAmount+"</liquidationAmount><billCurrency>" + sContractCurrency + "</billCurrency><billAmount>" + sContractAmount + "</billAmount><valueDate>"+sFCUBSCurrentDate+"</valueDate><maturityDate>"+sMaturityDate+"</maturityDate><tenorDays>"+sTenorDays+"</tenorDays><transitDays>"+stransitDays+"</transitDays><baseDate>"+sBaseDate+"</baseDate><daysAfterBaseDate>"+sDaysAfterBaseDate+
							"</daysAfterBaseDate><creditValueDate>" + sFCUBSCurrentDate + "</creditValueDate><debitValueDate>" + sFCUBSCurrentDate + "</debitValueDate>"+
							"<transactionDate>" + sFCUBSCurrentDate + "</transactionDate><liquidationDate>"+sLiquidationDate+"</liquidationDate><customerId>" + sCustomerId + "</customerId><bcPartyType>"+sPartyType+"</bcPartyType><operAcceptanceFlag>N</operAcceptanceFlag><limitTracking>"+sLimitTrackFlag+
							"</limitTracking><reDiscountFlag>N</reDiscountFlag><statusControlFlag>M</statusControlFlag><autoLiquidateFlag>M</autoLiquidateFlag>"+
							"<advanceByLoan>N</advanceByLoan><allowRollover>N</allowRollover><linkToLoan>N</linkToLoan><lcRefMessageFlag>N</lcRefMessageFlag>"+
							"<interestfromDate>"+sFCUBSCurrentDate+"</interestfromDate><interestToDate>"+sMaturityDate+"</interestToDate><graceDays>0</graceDays>"+
							"<allowPrepayment>N</allowPrepayment><docOriginalReceived>N</docOriginalReceived><docDuplicateReceived>N</docDuplicateReceived><ackReceived>N"+
							"</ackReceived><ourChargesRefused>N</ourChargesRefused><internalRemarks>"+insttobankelcbbl+"</internalRemarks>"+setPartyLimit(sWIName,sRequestCategoryCode,partyLimitCountAvailable)+
							"<claimAdviseInSwift>N</claimAdviseInSwift><liquidateUsingCollateral>N</liquidateUsingCollateral><brokerageToBePaidByUs>N"+"</brokerageToBePaidByUs><settlementAmount>N</settlementAmount><riskParticipation>"+sRiskParticipation+"</riskParticipation><goodsDetails>"+goodsCode+goodsDecsriptionelcbbl+"</goodsDetails>"+schargAccountNo+sdealNumber+"<shipmentDetails>"+spostOfDischarge+snameOfVessel+sportOfLoading+"</shipmentDetails><documentDetails>" +sDocDetls+ "</documentDetails><txnUDFDetails><UDFFieldName>TXN_ISSUING_CENTER</UDFFieldName><udfFieldValue>" + sUdfFieldValue + "</udfFieldValue><UDFFieldName>COURIER_COMPANY</UDFFieldName><udfFieldValue>" + sUdfFieldValue1 + "</udfFieldValue><UDFFieldName>COURIER_AWB_NUMBER</UDFFieldName><udfFieldValue>" + sUdfFieldValue2 + "</udfFieldValue>"+udf3Data+"</txnUDFDetails><wmsDetails><workItemNumber>" + sWIName + "</workItemNumber></wmsDetails><freeFormatTextDtls><freeFormatTextDtl><freeFormatTextCode>"+fftCode+"</freeFormatTextCode><freeFormatTextDescription>"+freeFormatTextDesc+"</freeFormatTextDescription></freeFormatTextDtl></freeFormatTextDtls>"+adviceDetails+"<branchCode>" + sBranchCreateCode + "</branchCode></modTradeBCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId></APWebService_Input>";
							WriteToLog_showpage("Y","modTradeBCContract_Oper sOutput: "+ callXML);
							sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sCallName +"',"+ convertToPlainString(callXML) +",'"+ REFNO +"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","modTradeBCContract_Oper callXML TFO_DJ_INTEGRATION_CALLS_DTLS: "+ sQuery + " *****************************");	
							sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;						
							WriteToLog_showpage("Y"," insert modTradeBCContract_Oper sOutput: "+ sOutput);	
											
							
							sOutput = socket.connectToSocket(callXML);
							WriteToLog_showpage("Y","modTradeBCContract_Oper callXML sOutput"+ sOutput);
							
							String queryUpdateXML = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'" + sOutput + "'","WI_NAME = '"+ sWIName +"'  and REF_NUM = '"+REFNO+"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","update sOutputUpdateXML input: "+ queryUpdateXML);
							String sOutputUpdateXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",queryUpdateXML);;
							WriteToLog_showpage("Y","update sOutputUpdateXML output: " + sOutputUpdateXML);
							
							xp = new XMLParser(sOutput);
							returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
							String nextValue ="";
							if(returnCode == 0){
								sErrorDescription = xp.getValueOf("errorDescription");
								sContractRefNumber = xp.getValueOf("contractReference");
								sResponse = "Success";
								sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS,TRANSACTION_REFERENCE","'" + sResponse + "','" + sContractRefNumber + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
								out.println(returnCode+"#"+sResponse+"#"+sErrorDescription);
							} else {
								sErrorDescription = xp.getValueOf("errorDescription");
								sResponse = "Failure";
								sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
								out.println("1#"+sResponse+"#"+sErrorDescription);
							}
						}
					}
					else if("IC".equalsIgnoreCase(sRequestCategoryCode) && "IC_BL".equalsIgnoreCase(sRequestTypeCode))
					{
						sExternalRefNumber = sOutput.substring(sOutput.indexOf("<PRO_TRADE_REF_NO>")+"<PRO_TRADE_REF_NO>".length(),sOutput.indexOf("</PRO_TRADE_REF_NO>"));
						sOperationCode = sOutput.substring(sOutput.indexOf("<OPERATION_CODE>")+"<OPERATION_CODE>".length(),sOutput.indexOf("</OPERATION_CODE>"));
						sContractCurrency = sOutput.substring(sOutput.indexOf("<TRANSACTION_CURRENCY>")+"<TRANSACTION_CURRENCY>".length(),sOutput.indexOf("</TRANSACTION_CURRENCY>"));
						sContractAmount = sOutput.substring(sOutput.indexOf("<TRANSACTION_AMOUNT>")+"<TRANSACTION_AMOUNT>".length(),sOutput.indexOf("</TRANSACTION_AMOUNT>"));
						sCustomerId = sOutput.substring(sOutput.indexOf("<CUSTOMER_ID>")+"<CUSTOMER_ID>".length(),sOutput.indexOf("</CUSTOMER_ID>"));
						sProductCode = sOutput.substring(sOutput.indexOf("<PRODUCT_TYPE>")+"<PRODUCT_TYPE>".length(),sOutput.indexOf("</PRODUCT_TYPE>"));
						sUdfFieldValue = sOutput.substring(sOutput.indexOf("<BRANCH_CITY>")+"<BRANCH_CITY>".length(),sOutput.indexOf("</BRANCH_CITY>"));
						sRelationshipType = sOutput.substring(sOutput.indexOf("<RELATIONSHIP_TYPE>")+"<RELATIONSHIP_TYPE>".length(),sOutput.indexOf("</RELATIONSHIP_TYPE>"));
						
						sLiquidationAmount="";
						sMaturityDate=sOutput.substring(sOutput.indexOf("<INF_MATURITY_DATE>")+"<INF_MATURITY_DATE>".length(),sOutput.indexOf("</INF_MATURITY_DATE>"));
						String tenorDays=sOutput.substring(sOutput.indexOf("<INF_TENOR_DAYS>")+"<INF_TENOR_DAYS>".length(),sOutput.indexOf("</INF_TENOR_DAYS>"));
						String tenorBase=sOutput.substring(sOutput.indexOf("<INF_TENOR_BASE>")+"<INF_TENOR_BASE>".length(),sOutput.indexOf("</INF_TENOR_BASE>"));
						String actualTenorBase=sOutput.substring(sOutput.indexOf("<INF_ACTUAL_TENOR_BASE>")+"<INF_ACTUAL_TENOR_BASE>".length(),sOutput.indexOf("</INF_ACTUAL_TENOR_BASE>"));
						sBaseDate=sOutput.substring(sOutput.indexOf("<INF_BASE_DOC_DATE>")+"<INF_BASE_DOC_DATE>".length(),sOutput.indexOf("</INF_BASE_DOC_DATE>"));
						sPartyType="DRAWEE";
						
						
						if("T3U5".equalsIgnoreCase(sProductCode) || "I3U5".equalsIgnoreCase(sProductCode) || "T3U2".equalsIgnoreCase(sProductCode) || "I3U2".equalsIgnoreCase(sProductCode))
						{
							sTenorDays=tenorDays;
							sStage="INI";
							stransitDays="0";
							sDaysAfterBaseDate=tenorBase;
							if("IC_O".equalsIgnoreCase(sDaysAfterBaseDate)){
								sDaysAfterBaseDate=actualTenorBase;
							}
						}
						else if("T3S2".equalsIgnoreCase(sProductCode) || "I3S2".equalsIgnoreCase(sProductCode))
						{
							sTenorDays="0";
							sStage="FIN";
							stransitDays=tenorDays;
							sDaysAfterBaseDate="";
						}
						
						sLimitTrackFlag="N";

						sBranchCreateCode = fetchBranchCode(sRelationshipType , sWIName);
						if(sCallName.equalsIgnoreCase("modTradeBCContract_Oper")){
							callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeBCContract_Oper</operationType><correlationID>342343</correlationID><modTradeBCContractReq><requestType>C</requestType><externalRefNumber>" + sExternalRefNumber + "</externalRefNumber><userRefNumber></userRefNumber><productCode>" + sProductCode + "</productCode><operation>" + sOperationCode + "</operation><stage>"+sStage+"</stage><billCurrency>" + sContractCurrency + "</billCurrency><billAmount>" + sContractAmount + "</billAmount><valueDate>" + sFCUBSCurrentDate + "</valueDate><maturityDate>"+sMaturityDate+"</maturityDate><tenorDays>"+sTenorDays+"</tenorDays>"+
							"<transitDays>"+stransitDays+"</transitDays><baseDate>" + sBaseDate + "</baseDate><daysAfterBaseDate>"+sDaysAfterBaseDate+
							"</daysAfterBaseDate><creditValueDate>" + sFCUBSCurrentDate + "</creditValueDate><debitValueDate>" + sFCUBSCurrentDate + "</debitValueDate>"+
							"<transactionDate>" + sFCUBSCurrentDate + "</transactionDate><liquidationDate>"+sLiquidationDate+"</liquidationDate><customerId>" + sCustomerId + "</customerId><bcPartyType>"+sPartyType+"</bcPartyType><operAcceptanceFlag>N</operAcceptanceFlag><limitTracking>" + sLimitTrackFlag +"</limitTracking><reDiscountFlag>N</reDiscountFlag><statusControlFlag>M</statusControlFlag><autoLiquidateFlag>M</autoLiquidateFlag>"+
							"<advanceByLoan>N</advanceByLoan><allowRollover>N</allowRollover><linkToLoan>N</linkToLoan><lcRefMessageFlag>N</lcRefMessageFlag>"+
							"<graceDays>0</graceDays>"+
							"<allowPrepayment>N</allowPrepayment><docOriginalReceived>N</docOriginalReceived><docDuplicateReceived>N</docDuplicateReceived><ackReceived>N"+
							"</ackReceived><ourChargesRefused>N</ourChargesRefused><internalRemarks>"+setProTradeRemark(sWIName)+"</internalRemarks>"+
							"<claimAdviseInSwift>N</claimAdviseInSwift><liquidateUsingCollateral>N</liquidateUsingCollateral><brokerageToBePaidByUs>N"+							"</brokerageToBePaidByUs><settlementAmount>N</settlementAmount><riskParticipation>N</riskParticipation><txnUDFDetails><UDFFieldName>TXN_ISSUING_CENTER</UDFFieldName><udfFieldValue>" + sUdfFieldValue + "</udfFieldValue></txnUDFDetails><wmsDetails><workItemNumber>" + sWIName + "</workItemNumber></wmsDetails><branchCode>" + sBranchCreateCode + "</branchCode><contractParties>" + getContractPartyDetailsforCreate(sWIName) + "</contractParties></modTradeBCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId></APWebService_Input>";
							WriteToLog_showpage("Y","modTradeBCContract_Oper sOutput: "+ callXML);
							sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sCallName +"',"+ convertToPlainString(callXML) +",'"+ REFNO +"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","modTradeBCContract_Oper callXML TFO_DJ_INTEGRATION_CALLS_DTLS: "+ sQuery + " *****************************");	
							sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;						
							WriteToLog_showpage("Y"," insert modTradeBCContract_Oper sOutput: "+ sOutput);	
											
							
							sOutput = socket.connectToSocket(callXML);
							WriteToLog_showpage("Y","modTradeBCContract_Oper callXML sOutput"+ sOutput);
							
							String queryUpdateXML = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'" + sOutput + "'","WI_NAME = '"+ sWIName +"'  and REF_NUM = '"+REFNO+"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","update sOutputUpdateXML input: "+ queryUpdateXML);
							String sOutputUpdateXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",queryUpdateXML);;
							WriteToLog_showpage("Y","update sOutputUpdateXML output: " + sOutputUpdateXML);
							
							xp = new XMLParser(sOutput);
							returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
							String nextValue ="";
							if(returnCode == 0){
								sErrorDescription = xp.getValueOf("errorDescription");
								sContractRefNumber = xp.getValueOf("contractReference");
								sResponse = "Success";
								sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS,TRANSACTION_REFERENCE","'" + sResponse + "','" + sContractRefNumber + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
								out.println(returnCode+"#"+sResponse+"#"+sErrorDescription);
							} else {
								sErrorDescription = xp.getValueOf("errorDescription");
								sResponse = "Failure";
								sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
								out.println("1#"+sResponse+"#"+sErrorDescription);
							}
						}
					}
					else if("IFP".equalsIgnoreCase(sRequestCategoryCode) && "LD".equalsIgnoreCase(sRequestTypeCode))
					{
						sExternalRefNumber = sOutput.substring(sOutput.indexOf("<PRO_TRADE_REF_NO>")+"<PRO_TRADE_REF_NO>".length(),sOutput.indexOf("</PRO_TRADE_REF_NO>"));
						sOperationCode = sOutput.substring(sOutput.indexOf("<OPERATION_CODE>")+"<OPERATION_CODE>".length(),sOutput.indexOf("</OPERATION_CODE>"));
						sContractCurrency = sOutput.substring(sOutput.indexOf("<TRANSACTION_CURRENCY>")+"<TRANSACTION_CURRENCY>".length(),sOutput.indexOf("</TRANSACTION_CURRENCY>"));
						sContractAmount = sOutput.substring(sOutput.indexOf("<TRANSACTION_AMOUNT>")+"<TRANSACTION_AMOUNT>".length(),sOutput.indexOf("</TRANSACTION_AMOUNT>"));
						sCustomerId = sOutput.substring(sOutput.indexOf("<CUSTOMER_ID>")+"<CUSTOMER_ID>".length(),sOutput.indexOf("</CUSTOMER_ID>"));
						sProductCode = sOutput.substring(sOutput.indexOf("<BILL_PRODUCT_CODE>")+"<BILL_PRODUCT_CODE>".length(),sOutput.indexOf("</BILL_PRODUCT_CODE>"));
						sUdfFieldValue = sOutput.substring(sOutput.indexOf("<BRANCH_CITY>")+"<BRANCH_CITY>".length(),sOutput.indexOf("</BRANCH_CITY>"));
						sRelationshipType = sOutput.substring(sOutput.indexOf("<RELATIONSHIP_TYPE>")+"<RELATIONSHIP_TYPE>".length(),sOutput.indexOf("</RELATIONSHIP_TYPE>"));
						
						sLiquidationAmount="";
						sMaturityDate=sOutput.substring(sOutput.indexOf("<INF_MATURITY_DATE>")+"<INF_MATURITY_DATE>".length(),sOutput.indexOf("</INF_MATURITY_DATE>"));
						String tenorDays=sOutput.substring(sOutput.indexOf("<INF_TENOR_DAYS>")+"<INF_TENOR_DAYS>".length(),sOutput.indexOf("</INF_TENOR_DAYS>"));
						String tenorBase=sOutput.substring(sOutput.indexOf("<INF_TENOR_BASE>")+"<INF_TENOR_BASE>".length(),sOutput.indexOf("</INF_TENOR_BASE>"));
						String actualTenorBase=sOutput.substring(sOutput.indexOf("<INF_ACTUAL_TENOR_BASE>")+"<INF_ACTUAL_TENOR_BASE>".length(),sOutput.indexOf("</INF_ACTUAL_TENOR_BASE>"));
						sBaseDate=sOutput.substring(sOutput.indexOf("<INF_BASE_DOC_DATE>")+"<INF_BASE_DOC_DATE>".length(),sOutput.indexOf("</INF_BASE_DOC_DATE>"));
						sPartyType="DRAWEE";
						sStage="FIN";
						sTenorDays="0";
						stransitDays="0";
						sDaysAfterBaseDate="";
						
						sLimitTrackFlag="N";

						sBranchCreateCode = fetchBranchCode(sRelationshipType , sWIName);
						if(sCallName.equalsIgnoreCase("modTradeBCContract_Oper")){
							callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeBCContract_Oper</operationType><correlationID>342343</correlationID><modTradeBCContractReq><requestType>C</requestType><externalRefNumber>" + sExternalRefNumber + "</externalRefNumber><userRefNumber></userRefNumber><productCode>" + sProductCode + "</productCode><operation>" + sOperationCode + "</operation><stage>"+sStage+"</stage><liquidationAmount>" + sContractAmount + "</liquidationAmount><billCurrency>" + sContractCurrency + "</billCurrency><billAmount>" + sContractAmount + "</billAmount><valueDate>" + sFCUBSCurrentDate + "</valueDate><maturityDate>"+sFCUBSCurrentDate+"</maturityDate><tenorDays>"+sTenorDays+"</tenorDays>"+
							"<transitDays>"+stransitDays+"</transitDays><baseDate>" + sFCUBSCurrentDate + "</baseDate><daysAfterBaseDate>"+sDaysAfterBaseDate+
							"</daysAfterBaseDate><creditValueDate>" + sFCUBSCurrentDate + "</creditValueDate><debitValueDate>" + sFCUBSCurrentDate + "</debitValueDate>"+
							"<transactionDate>" + sFCUBSCurrentDate + "</transactionDate><liquidationDate>"+sFCUBSCurrentDate+"</liquidationDate><customerId>" + sCustomerId + "</customerId><bcPartyType>"+sPartyType+"</bcPartyType><operAcceptanceFlag>N</operAcceptanceFlag><limitTracking>" + sLimitTrackFlag +"</limitTracking><reDiscountFlag>N</reDiscountFlag><statusControlFlag>M</statusControlFlag><autoLiquidateFlag>M</autoLiquidateFlag>"+
							"<advanceByLoan>Y</advanceByLoan><allowRollover>N</allowRollover><linkToLoan>N</linkToLoan><lcRefMessageFlag>N</lcRefMessageFlag>"+
							"<graceDays>0</graceDays>"+
							"<allowPrepayment>N</allowPrepayment><docOriginalReceived>N</docOriginalReceived><docDuplicateReceived>N</docDuplicateReceived><ackReceived>N"+
							"</ackReceived><ourChargesRefused>N</ourChargesRefused><internalRemarks>"+setProTradeRemark(sWIName)+"</internalRemarks>"+
							"<claimAdviseInSwift>N</claimAdviseInSwift><liquidateUsingCollateral>N</liquidateUsingCollateral><brokerageToBePaidByUs>N"+							"</brokerageToBePaidByUs><settlementAmount>N</settlementAmount><riskParticipation>N</riskParticipation><wmsDetails><workItemNumber>" + sWIName + "</workItemNumber></wmsDetails><branchCode>" + sBranchCreateCode + "</branchCode><contractParties>" + getContractPartyDetailsforIFPCreate(sCustomerId,sWIName) + "</contractParties></modTradeBCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId></APWebService_Input>";
							WriteToLog_showpage("Y","modTradeBCContract_Oper sOutput: "+ callXML);
							sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sCallName +"',"+ convertToPlainString(callXML) +",'"+ REFNO +"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","modTradeBCContract_Oper callXML TFO_DJ_INTEGRATION_CALLS_DTLS: "+ sQuery + " *****************************");	
							sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;						
							WriteToLog_showpage("Y"," insert modTradeBCContract_Oper sOutput: "+ sOutput);	
											
							
							sOutput = socket.connectToSocket(callXML);
							WriteToLog_showpage("Y","modTradeBCContract_Oper callXML sOutput"+ sOutput);
							
							String queryUpdateXML = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'" + sOutput + "'","WI_NAME = '"+ sWIName +"'  and REF_NUM = '"+REFNO+"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","update sOutputUpdateXML input: "+ queryUpdateXML);
							String sOutputUpdateXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",queryUpdateXML);;
							WriteToLog_showpage("Y","update sOutputUpdateXML output: " + sOutputUpdateXML);
							
							xp = new XMLParser(sOutput);
							returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
							String nextValue ="";
							if(returnCode == 0){
								sErrorDescription = xp.getValueOf("errorDescription");
								sContractRefNumber = xp.getValueOf("contractReference");
								sResponse = "Success";
								
								sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS,TRANSACTION_REFERENCE,BILL_LN_REFRNCE","'" + sResponse + "','" + sContractRefNumber +"','"+sContractRefNumber+"'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
								out.println(returnCode+"#"+sResponse+"#"+sErrorDescription);
							} else {
								sErrorDescription = xp.getValueOf("errorDescription");
								sResponse = "Failure";
								sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
								out.println("1#"+sResponse+"#"+sErrorDescription);
							}
						}
					}else if("IFA".equalsIgnoreCase(sRequestCategoryCode) && "LD".equalsIgnoreCase(sRequestTypeCode))
					{
						sExternalRefNumber = sOutput.substring(sOutput.indexOf("<PRO_TRADE_REF_NO>")+"<PRO_TRADE_REF_NO>".length(),sOutput.indexOf("</PRO_TRADE_REF_NO>"));
						sOperationCode = sOutput.substring(sOutput.indexOf("<OPERATION_CODE>")+"<OPERATION_CODE>".length(),sOutput.indexOf("</OPERATION_CODE>"));
						sContractCurrency = sOutput.substring(sOutput.indexOf("<TRANSACTION_CURRENCY>")+"<TRANSACTION_CURRENCY>".length(),sOutput.indexOf("</TRANSACTION_CURRENCY>"));
						sContractAmount = sOutput.substring(sOutput.indexOf("<TRANSACTION_AMOUNT>")+"<TRANSACTION_AMOUNT>".length(),sOutput.indexOf("</TRANSACTION_AMOUNT>"));
						sCustomerId = sOutput.substring(sOutput.indexOf("<CUSTOMER_ID>")+"<CUSTOMER_ID>".length(),sOutput.indexOf("</CUSTOMER_ID>"));
						sProductCode = sOutput.substring(sOutput.indexOf("<BILL_PRODUCT_CODE>")+"<BILL_PRODUCT_CODE>".length(),sOutput.indexOf("</BILL_PRODUCT_CODE>"));
						sUdfFieldValue = sOutput.substring(sOutput.indexOf("<BRANCH_CITY>")+"<BRANCH_CITY>".length(),sOutput.indexOf("</BRANCH_CITY>"));
						sRelationshipType = sOutput.substring(sOutput.indexOf("<RELATIONSHIP_TYPE>")+"<RELATIONSHIP_TYPE>".length(),sOutput.indexOf("</RELATIONSHIP_TYPE>"));
						
						sLiquidationAmount="";
						sMaturityDate=sOutput.substring(sOutput.indexOf("<INF_MATURITY_DATE>")+"<INF_MATURITY_DATE>".length(),sOutput.indexOf("</INF_MATURITY_DATE>"));
						String tenorDays=sOutput.substring(sOutput.indexOf("<INF_TENOR_DAYS>")+"<INF_TENOR_DAYS>".length(),sOutput.indexOf("</INF_TENOR_DAYS>"));
						String tenorBase=sOutput.substring(sOutput.indexOf("<INF_TENOR_BASE>")+"<INF_TENOR_BASE>".length(),sOutput.indexOf("</INF_TENOR_BASE>"));
						String actualTenorBase=sOutput.substring(sOutput.indexOf("<INF_ACTUAL_TENOR_BASE>")+"<INF_ACTUAL_TENOR_BASE>".length(),sOutput.indexOf("</INF_ACTUAL_TENOR_BASE>"));
						sBaseDate=sOutput.substring(sOutput.indexOf("<INF_BASE_DOC_DATE>")+"<INF_BASE_DOC_DATE>".length(),sOutput.indexOf("</INF_BASE_DOC_DATE>"));
						sPartyType="DRAWEE";
						sStage="FIN";
						sTenorDays="0";
						stransitDays="0";
						sDaysAfterBaseDate="";
						
						sLimitTrackFlag="N";

						sBranchCreateCode = fetchBranchCode(sRelationshipType , sWIName);
						if(sCallName.equalsIgnoreCase("modTradeBCContract_Oper")){
							callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeBCContract_Oper</operationType><correlationID>342343</correlationID><modTradeBCContractReq><requestType>C</requestType><externalRefNumber>" + sExternalRefNumber + "</externalRefNumber><userRefNumber></userRefNumber><productCode>" + sProductCode + "</productCode><operation>" + sOperationCode + "</operation><stage>"+sStage+"</stage><liquidationAmount>" + sContractAmount + "</liquidationAmount><billCurrency>" + sContractCurrency + "</billCurrency><billAmount>" + sContractAmount + "</billAmount><valueDate>" + sFCUBSCurrentDate + "</valueDate><maturityDate>"+sFCUBSCurrentDate+"</maturityDate><tenorDays>"+sTenorDays+"</tenorDays>"+
							"<transitDays>"+stransitDays+"</transitDays><baseDate>" + sFCUBSCurrentDate + "</baseDate><daysAfterBaseDate>"+sDaysAfterBaseDate+
							"</daysAfterBaseDate><creditValueDate>" + sFCUBSCurrentDate + "</creditValueDate><debitValueDate>" + sFCUBSCurrentDate + "</debitValueDate>"+
							"<transactionDate>" + sFCUBSCurrentDate + "</transactionDate><liquidationDate>"+sFCUBSCurrentDate+"</liquidationDate><customerId>" + sCustomerId + "</customerId><bcPartyType>"+sPartyType+"</bcPartyType><operAcceptanceFlag>N</operAcceptanceFlag><limitTracking>" + sLimitTrackFlag +"</limitTracking><reDiscountFlag>N</reDiscountFlag><statusControlFlag>M</statusControlFlag><autoLiquidateFlag>M</autoLiquidateFlag>"+
							"<advanceByLoan>Y</advanceByLoan><allowRollover>N</allowRollover><linkToLoan>N</linkToLoan><lcRefMessageFlag>N</lcRefMessageFlag>"+
							"<graceDays>0</graceDays>"+
							"<allowPrepayment>N</allowPrepayment><docOriginalReceived>N</docOriginalReceived><docDuplicateReceived>N</docDuplicateReceived><ackReceived>N"+
							"</ackReceived><ourChargesRefused>N</ourChargesRefused><internalRemarks>"+setProTradeRemark(sWIName)+"</internalRemarks>"+
							"<claimAdviseInSwift>N</claimAdviseInSwift><liquidateUsingCollateral>N</liquidateUsingCollateral><brokerageToBePaidByUs>N"+							"</brokerageToBePaidByUs><settlementAmount>N</settlementAmount><riskParticipation>N</riskParticipation><wmsDetails><workItemNumber>" + sWIName + "</workItemNumber></wmsDetails><branchCode>" + sBranchCreateCode + "</branchCode><contractParties>" + getContractPartyDetailsforIFPCreate(sCustomerId,sWIName) + "</contractParties></modTradeBCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId></APWebService_Input>";
							WriteToLog_showpage("Y","modTradeBCContract_Oper sOutput: "+ callXML);
							sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sCallName +"',"+ convertToPlainString(callXML) +",'"+ REFNO +"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","modTradeBCContract_Oper callXML TFO_DJ_INTEGRATION_CALLS_DTLS: "+ sQuery + " *****************************");	
							sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;						
							WriteToLog_showpage("Y"," insert modTradeBCContract_Oper sOutput: "+ sOutput);	
											
							
							sOutput = socket.connectToSocket(callXML);
							WriteToLog_showpage("Y","modTradeBCContract_Oper callXML sOutput"+ sOutput);
							
							String queryUpdateXML = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'" + sOutput + "'","WI_NAME = '"+ sWIName +"'  and REF_NUM = '"+REFNO+"'", sCabname, sSessionId);
							WriteToLog_showpage("Y","update sOutputUpdateXML input: "+ queryUpdateXML);
							String sOutputUpdateXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",queryUpdateXML);;
							WriteToLog_showpage("Y","update sOutputUpdateXML output: " + sOutputUpdateXML);
							
							xp = new XMLParser(sOutput);
							returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
							String nextValue ="";
							if(returnCode == 0){
								sErrorDescription = xp.getValueOf("errorDescription");
								sContractRefNumber = xp.getValueOf("contractReference");
								sResponse = "Success";
								
								sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS,TRANSACTION_REFERENCE,BILL_LN_REFRNCE","'" + sResponse + "','" + sContractRefNumber +"','"+sContractRefNumber+"'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
								out.println(returnCode+"#"+sResponse+"#"+sErrorDescription);
							} else {
								sErrorDescription = xp.getValueOf("errorDescription");
								sResponse = "Failure";
								sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;
								WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
								out.println("1#"+sResponse+"#"+sErrorDescription);
							}
						}
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
					out.println("Exception is"+e.getMessage());
					WriteToLog_showpage("Y","Exception : " +e.getMessage());
				}
			}
			else {
				WriteToLog_showpage("Y","RETRY:"+sOutput);	
				callXML = sOutput.substring(sOutput.indexOf("<CALL_XML>")+10,sOutput.indexOf("</CALL_XML>"));
				WriteToLog_showpage("Y","RETRY1");	
				sInput = prepareAPSelectInputXml("SELECT SEQ_WEBSERVICE.NEXTVAL REFNO FROM DUAL", sCabname, sSessionId);
				sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);;
				REFNO = sOutput.substring(sOutput.indexOf("<REFNO>")+7,sOutput.indexOf("</REFNO>"));
				callXML = callXML.replaceAll(oldREFNO,REFNO);
				WriteToLog_showpage("Y", sCallName+"mBCContract_Oper sOutput: "+ callXML);
				sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sCallName +"',"+ convertToPlainString(callXML) +",'"+ REFNO +"'", sCabname, sSessionId);
				WriteToLog_showpage("Y","modTradeBCContract_Oper callXML TFO_DJ_INTEGRATION_CALLS_DTLS: "+ sQuery + " *****************************");	
				sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;						
				WriteToLog_showpage("Y"," insert modTradeBCContract_Oper sOutput: "+ sOutput);	
				sOutput = socket.connectToSocket(callXML);
				WriteToLog_showpage("Y",sCallName+" callXML sOutput"+ sOutput);
				
				String queryUpdateXML = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'" + sOutput + "'","WI_NAME = '"+ sWIName +"'  and REF_NUM = '"+REFNO+"'", sCabname, sSessionId);
				WriteToLog_showpage("Y","update sOutputUpdateXML input: "+ queryUpdateXML);
				String sOutputUpdateXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",queryUpdateXML);;
				WriteToLog_showpage("Y","update sOutputUpdateXML output: " + sOutputUpdateXML);
				
				xp = new XMLParser(sOutput);
				returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
				String nextValue ="";
				if(returnCode == 0){
					sErrorDescription = xp.getValueOf("errorDescription");
					if(sCallName.equalsIgnoreCase("modTradeBCContract_Oper"))
					{
						sContractRefNumber = xp.getValueOf("contractReference");
					}
					else{
						sContractRefNumber = xp.getValueOf("contractRefNumber");
					}
					sResponse = "Success";
					sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS,TRANSACTION_REFERENCE","'" + sResponse + "','" + sContractRefNumber + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
					WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
					sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;
					WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
					out.println(returnCode+"#"+sResponse+"#"+sErrorDescription);
					} else {
					sErrorDescription = xp.getValueOf("errorDescription");
					sResponse = "Failure";
					sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
					WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
					sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);;
					WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
					out.println("1#"+sResponse+"#"+sErrorDescription);
							}
			}			
		}
	}
}
catch(Exception e)
{
	sInput=prepareAPInsertInputXml("USR_0_INTEGRATION_VALUES","wi_name,exception_error","'" + sWIName + "','"+e.getMessage()+"'",sCabname,sSessionId);
	WriteToLog_showpage("Y"," ***************Exception "+sInput+" *****************************");
	sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
	out.println("Error#FAILED#"+e.getMessage());
	e.printStackTrace();
}

%>


		
<%!
String sRequestedConfirmationParty = "";
String sSpecialPaymentConditionForBeneficiary= "";
String sCreditMode = "";
String sQuery1="";
String sInput1="";
String sOutput1="";
String sQuery="";
String sInput="";
String sOutput="";
String sUserName="";
String sCabname="";
String sSessionId ="";
String sJtsIp ="";
String iJtsPort = "";
String limitLineCustomerId = "";
String sRiskParticipation  ="N";
ConnectSocket socket;


private String prepareAPSelectInputXml(String sQuery, String sCabname, String sSessionId){	
	return "<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+sQuery+"</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
}

private String prepareAPInsertInputXml(String tableName,String colname,String sValues, String sCabname, String sSessionId){	
	return "<?xml version=\"1.0\"?><APInsert_Input><Option>APInsert</Option><TableName>"+tableName+"</TableName><ColName>"+colname+"</ColName><Values>"+sValues+"</Values><EngineName>" + sCabname + "</EngineName>" +    "<SessionId>" + sSessionId + "</SessionId></APInsert_Input>";
}

private String prepareAPUpdateInputXml(String tableName,String colname,String sValues,String whrcls, String sCabname, String sSessionId){	
	return "<?xml version=\"1.0\"?><APUpdate_Input><Option>APUpdate</Option><TableName>"+tableName+"</TableName><ColName>"+colname+"</ColName><Values>"+sValues+"</Values><WhereClause>"+whrcls+"</WhereClause><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APUpdate_Input>";
}

private String prepareAPProcInputXml(String ProcName,String ProcParams, String sCabname, String sSessionId){	
	return "<?xml version=\"1.0\"?><APProcedure_Input><Option>APProcedure</Option><ProcName>"+ProcName+"</ProcName><Params>"+ProcParams+"</Params><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APUpdate_Input>";
}

private String prepareAPDeleteXml(String tableName,String whereClause, String sCabname, String sSessionId){
	return "<?xml version=1.0?><APDelete_Input><Option>APDelete</Option><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId><TableName>"+tableName+"</TableName><WhereClause>"+whereClause+"</WhereClause></APDelete_Input>";

}

private HashMap<String, String> getCreditReviwChecklistData(String RequestCategory, String RequestType, String WINumber)
{
    HashMap map = null;
    try {
      WriteToLog_showpage("Y","Inside getCreditReviwChecklistData");
      String sGTMODMast = "";
      List recordList = null;
      map = new HashMap();
      String sQuery1 = "SELECT MAPPED_CONTROLNAME,LT_RESPONSE,RECORDS.LT_DOC_RVW_GDLINES FROM TFO_DJ_DOC_RVW_MAST MAST JOIN TFO_DJ_LMT_CRDT_RECORDS RECORDS ON MAST.DOC_RVW= RECORDS.LT_DOC_RVW_GDLINES WHERE TAB_NAME='LmtCrdt' AND REQ_CAT_CODE='" + RequestCategory + "' AND REQ_TYPE_CODE='" + RequestType + "' AND WI_NAME='" + WINumber + "'";
      WriteToLog_showpage("Y"," Query " + sQuery1);
	  sInput = prepareAPSelectInputXml(sQuery1,sCabname,sSessionId);
	  WriteToLog_showpage("Y"," ***************getCreditReviwChecklistData Input "+sInput+" *****************************");
	  sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);;
	  WriteToLog_showpage("Y"," ***************getCreditReviwChecklistData sOutput "+sOutput+" *****************************");
	  XMLParser xmlDataParser1 = new XMLParser();
	  XMLParser xmlDataParser2 = new XMLParser();
	  xmlDataParser1.setInputXML(sOutput);
	  int iCreditRvwCount = xmlDataParser1.getNoOfFields("Record");
	  if (iCreditRvwCount > 0) {
		  for (int i = 0; i < iCreditRvwCount; ++i) {
				xmlDataParser2.setInputXML(xmlDataParser1.getNextValueOf("Record"));
				WriteToLog_showpage("Y","Credit Type " + xmlDataParser2.getValueOf("LT_RESPONSE").trim());
				map.put(xmlDataParser2.getValueOf("MAPPED_CONTROLNAME"), xmlDataParser2.getValueOf("LT_RESPONSE"));
		  }
	  }
    }
    catch (Exception localException)
    {
		WriteToLog_showpage("Y"," Excpt getCreditReviwChecklistData " + localException);
    }
    return map;
}

private HashMap<String, String> getDocReviwChecklistData(String RequestCategory, String RequestType, String WINumber)
{
    HashMap map = null;
    try {
      WriteToLog_showpage("Y","Inside getCreditReviwChecklistData");
      String sGTMODMast = "";
      List recordList = null;
      map = new HashMap();
      String sQuery1 = "SELECT MAPPED_CONTROLNAME,RESPONSE,RECORDS.DOC_RVW_GDLINES FROM TFO_DJ_DOC_RVW_MAST MAST JOIN tfo_dj_doc_rvw_records RECORDS"+ 
       " ON MAST.DOC_RVW= RECORDS.DOC_RVW_GDLINES WHERE TAB_NAME='DocRvw' AND REQ_CAT_CODE='" + RequestCategory + "' AND REQ_TYPE_CODE='" + RequestType + "' AND WI_NAME='" + WINumber + "'";
      WriteToLog_showpage("Y"," Query " + sQuery1);
	  sInput = prepareAPSelectInputXml(sQuery1,sCabname,sSessionId);
	  WriteToLog_showpage("Y"," ***************getCreditReviwChecklistData Input "+sInput+" *****************************");
	  sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);;
	  WriteToLog_showpage("Y"," ***************getCreditReviwChecklistData sOutput "+sOutput+" *****************************");
	  XMLParser xmlDataParser1 = new XMLParser();
	  XMLParser xmlDataParser2 = new XMLParser();
	  xmlDataParser1.setInputXML(sOutput);
	  int iCreditRvwCount = xmlDataParser1.getNoOfFields("Record");
	  if (iCreditRvwCount > 0) {
		  for (int i = 0; i < iCreditRvwCount; ++i) {
				xmlDataParser2.setInputXML(xmlDataParser1.getNextValueOf("Record"));
				WriteToLog_showpage("Y","Credit Type " + xmlDataParser2.getValueOf("RESPONSE").trim());
				map.put(xmlDataParser2.getValueOf("MAPPED_CONTROLNAME"), xmlDataParser2.getValueOf("RESPONSE"));
		  }
	  }
    }
    catch (Exception localException)
    {
		WriteToLog_showpage("Y"," Excpt getCreditReviwChecklistData " + localException);
    }
    return map;
}
private String getPartyDetailsforCreateAmend(String customerType, String sRequestType, String sWINumber,String sProcessType,String sSWIFT_UTILITY_FLAG) 
{
    StringBuilder sbPartyAddList = new StringBuilder();
	
    try {
		String country="";
		sQuery = "SELECT PARTY_ID,PARTY_TYPE,CUSTOMER_NAME,replace(replace(ADDRESS1,'<![CDATA[',''),']]>','') AS ADDRESS1,replace(replace(ADDRESS2,'<![CDATA[',''),']]>','') AS  ADDRESS2,replace(replace(ADDRESS3,'<![CDATA[',''),']]>','') AS ADDRESS3,replace(replace(ADDRESS4,'<![CDATA[',''),']]>','') AS ADDRESS4,COUNTRY,replace(replace(ADDRESS,'<![CDATA[',''),']]>','') AS ADDRESS FROM TFO_DJ_PARTY_DETAILS WHERE WINAME = '"+ sWINumber +"'";
		sInput = prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
		WriteToLog_showpage("Y"," ***************getPartyDetailsforCreateAmend Input "+sInput+" *****************************");
		sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);;
		WriteToLog_showpage("Y"," ***************getPartyDetailsforCreateAmend sOutput "+sOutput+" *****************************");
		XMLParser xmlDataParser1 = new XMLParser();
		XMLParser xmlDataParser2 = new XMLParser();
		xmlDataParser1.setInputXML(sOutput);
		int iPartiesCount = xmlDataParser1.getNoOfFields("Record");
		for (int i = 0; i < iPartiesCount; ++i) {
			xmlDataParser2.setInputXML(xmlDataParser1.getNextValueOf("Record"));
			WriteToLog_showpage("Y","Party Type " + xmlDataParser2.getValueOf("PARTY_TYPE").trim());
        String partyID = xmlDataParser2.getValueOf("PARTY_ID");
        String partyType = xmlDataParser2.getValueOf("PARTY_TYPE");
		
        sbPartyAddList.append("<partyDetail>");
        sbPartyAddList.append("<partyType>"); sbPartyAddList.append(partyType); sbPartyAddList.append("</partyType>");
        sbPartyAddList.append("<partyId>"); sbPartyAddList.append(partyID); sbPartyAddList.append("</partyId>");
        sbPartyAddList.append("<partyLanguageCode>"); sbPartyAddList.append("ENG"); sbPartyAddList.append("</partyLanguageCode>");

        if ("100000".equalsIgnoreCase(partyID)) {
			String mediaAddress=xmlDataParser2.getValueOf("ADDRESS");
			if(!(null==mediaAddress ||"".equalsIgnoreCase(mediaAddress))){
				sbPartyAddList.append("<partyCustAddress1>"); sbPartyAddList.append(mediaAddress); sbPartyAddList.append("</partyCustAddress1>");
			}
			else{
				sbPartyAddList.append("<partyCustAddress1>"); sbPartyAddList.append(xmlDataParser2.getValueOf("ADDRESS1")); sbPartyAddList.append("</partyCustAddress1>");
			}
			if("0".equalsIgnoreCase(xmlDataParser2.getValueOf("COUNTRY"))){
				country="";
			}else{
				country=xmlDataParser2.getValueOf("COUNTRY");
			}
          sbPartyAddList.append("<partyCustomerName>"); sbPartyAddList.append(xmlDataParser2.getValueOf("CUSTOMER_NAME")); sbPartyAddList.append("</partyCustomerName>");
          sbPartyAddList.append("<partyCustAddress2>"); sbPartyAddList.append(xmlDataParser2.getValueOf("ADDRESS2")); sbPartyAddList.append("</partyCustAddress2>");
          sbPartyAddList.append("<partyCustAddress3>"); sbPartyAddList.append(xmlDataParser2.getValueOf("ADDRESS3")); sbPartyAddList.append("</partyCustAddress3>");
          sbPartyAddList.append("<partyCustAddress4>"); sbPartyAddList.append(xmlDataParser2.getValueOf("ADDRESS4")); sbPartyAddList.append("</partyCustAddress4>");
		  sbPartyAddList.append("<countryCode>"); sbPartyAddList.append(country); sbPartyAddList.append("</countryCode>");
        }else{
			sbPartyAddList.append("<partyCustomerName></partyCustomerName>");
			sbPartyAddList.append("<partyCustAddress1></partyCustAddress1>");
			sbPartyAddList.append("<partyCustAddress2></partyCustAddress2>");
			sbPartyAddList.append("<partyCustAddress3></partyCustAddress3>");
			sbPartyAddList.append("<partyCustAddress4></partyCustAddress4>");
			sbPartyAddList.append("<countryCode></countryCode>");	
		}
		
		if("SWIFT".equalsIgnoreCase(sProcessType) && "Y".equalsIgnoreCase(sSWIFT_UTILITY_FLAG)){ //ADDED BY KISHAN 29/11/21
				String msgType = getSWIFTTXNDetails(sWINumber,"MSG_TYPE");
				String purposeMsg_A = getSWIFTTXNDetails(sWINumber,"PURPOSE_MESSAGE_A");
				String form_undertaking_b = getSWIFTTXNDetails(sWINumber,"FORM_UNDERTAKING_B");
				String demand_type = getSWIFTTXNDetails(sWINumber,"DEMAND_TYPE");
				String TXN_REF_NO_B = getSWIFTTXNDetails(sWINumber,"TXN_REF_NO_B");
				String DATE_ISSUE_B = getSWIFTTXNDetails(sWINumber,"DATE_ISSUE_B");
				WriteToLog_showpage("Y",msgType + purposeMsg_A + form_undertaking_b + demand_type + TXN_REF_NO_B + DATE_ISSUE_B);
				if(msgType.equalsIgnoreCase("760") && form_undertaking_b.equalsIgnoreCase("DGAR") && demand_type.equalsIgnoreCase("NA")){
					if(purposeMsg_A.equalsIgnoreCase("ISSU") && partyType.equalsIgnoreCase("ISB")){
						sbPartyAddList.append("<customerRefNumber>");
						sbPartyAddList.append(TXN_REF_NO_B);
						sbPartyAddList.append("</customerRefNumber>");
						sbPartyAddList.append("<customerRefDate>");
						sbPartyAddList.append(DATE_ISSUE_B);
						sbPartyAddList.append("</customerRefDate>");
					}else if(purposeMsg_A.equalsIgnoreCase("ISCO")  && partyType.equalsIgnoreCase("APB")){
						sbPartyAddList.append("<customerRefNumber>");
						sbPartyAddList.append(TXN_REF_NO_B);
						sbPartyAddList.append("</customerRefNumber>");
						sbPartyAddList.append("<customerRefDate>");
						sbPartyAddList.append(DATE_ISSUE_B);
						sbPartyAddList.append("</customerRefDate>");
					} else if(purposeMsg_A.equalsIgnoreCase("ICCO")  && partyType.equalsIgnoreCase("APB")){
						sbPartyAddList.append("<customerRefNumber>");
						sbPartyAddList.append(TXN_REF_NO_B);
						sbPartyAddList.append("</customerRefNumber>");
						sbPartyAddList.append("<customerRefDate>");
						sbPartyAddList.append(DATE_ISSUE_B);
						sbPartyAddList.append("</customerRefDate>");
					}
				}
		}
        sbPartyAddList.append("</partyDetail>");
        if (("NI".equalsIgnoreCase(sRequestType)) && (customerType.equalsIgnoreCase(partyType))){
          this.limitLineCustomerId = partyID;
		}else if (("SBLC_NI".equalsIgnoreCase(sRequestType)) && (customerType.equalsIgnoreCase(partyType))){
          this.limitLineCustomerId = partyID;
		}else if (("ELC_SLCA".equalsIgnoreCase(sRequestType)) && (customerType.equalsIgnoreCase(partyType))){
          this.limitLineCustomerId = partyID;
		}
	    if ((("ILC_NI".equalsIgnoreCase(sRequestType))||("ILC_NI".equalsIgnoreCase(sRequestType)) )&& (customerType.equalsIgnoreCase(partyType))){
          this.limitLineCustomerId = partyID;
		}
		 if (("ELC_LCA".equalsIgnoreCase(sRequestType)) && (customerType.equalsIgnoreCase(partyType))){
          this.limitLineCustomerId = partyID;
		}
      }	  
    }
    catch (Exception e) {
      WriteToLog_showpage("Y"," Excpt getPartyDetailsforCreateAmend " + e);
    }
    return sbPartyAddList.toString();
}

private String getContractPartyDetailsforCreate(String sWINumber) 
{
    StringBuilder sbPartyAddList = new StringBuilder();
    try {
		String country="";
		sQuery = "SELECT PARTY_ID,PARTY_TYPE,CUSTOMER_NAME,replace(replace(ADDRESS1,'<![CDATA[',''),']]>','') AS ADDRESS1,replace(replace(ADDRESS2,'<![CDATA[',''),']]>','') AS  ADDRESS2,replace(replace(ADDRESS3,'<![CDATA[',''),']]>','') AS ADDRESS3,replace(replace(ADDRESS4,'<![CDATA[',''),']]>','') AS ADDRESS4,COUNTRY,replace(replace(ADDRESS,'<![CDATA[',''),']]>','') AS  ADDRESS FROM TFO_DJ_PARTY_DETAILS WHERE WINAME = '"+ sWINumber +"'";
		sInput = prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
		WriteToLog_showpage("Y"," ***************getContractPartyDetailsforCreate Input "+sInput+" *****************************");
		sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);;
		WriteToLog_showpage("Y"," ***************getContractPartyDetailsforCreate sOutput "+sOutput+" *****************************");
		XMLParser xmlDataParser1 = new XMLParser();
		XMLParser xmlDataParser2 = new XMLParser();
		xmlDataParser1.setInputXML(sOutput);
		int iPartiesCount = xmlDataParser1.getNoOfFields("Record");
		for (int i = 0; i < iPartiesCount; ++i) {
			xmlDataParser2.setInputXML(xmlDataParser1.getNextValueOf("Record"));
			WriteToLog_showpage("Y","Party Type " + xmlDataParser2.getValueOf("PARTY_TYPE").trim());
        String partyID = xmlDataParser2.getValueOf("PARTY_ID");
        String partyType = xmlDataParser2.getValueOf("PARTY_TYPE");
        sbPartyAddList.append("<contractParty>");
        sbPartyAddList.append("<partyType>"); sbPartyAddList.append(partyType); sbPartyAddList.append("</partyType>");
        sbPartyAddList.append("<partyId>"); sbPartyAddList.append(partyID); sbPartyAddList.append("</partyId>");
        sbPartyAddList.append("<partyLanguage>"); sbPartyAddList.append("ENG"); sbPartyAddList.append("</partyLanguage>");

        if ("100000".equalsIgnoreCase(partyID)) {
			String mediaAddress=xmlDataParser2.getValueOf("ADDRESS");
			if(!(null==mediaAddress ||"".equalsIgnoreCase(mediaAddress))){
				sbPartyAddList.append("<partyAddress1>"); sbPartyAddList.append(mediaAddress); sbPartyAddList.append("</partyAddress1>");
			}
			else{
				sbPartyAddList.append("<partyAddress1>"); sbPartyAddList.append(xmlDataParser2.getValueOf("ADDRESS1")); sbPartyAddList.append("</partyAddress1>");
			}
			if("0".equalsIgnoreCase(xmlDataParser2.getValueOf("COUNTRY"))){
				country="";
			}else{
				country=xmlDataParser2.getValueOf("COUNTRY");
			}
          sbPartyAddList.append("<partyName>"); sbPartyAddList.append(xmlDataParser2.getValueOf("CUSTOMER_NAME")); sbPartyAddList.append("</partyName>");
          sbPartyAddList.append("<partyAddress2>"); sbPartyAddList.append(xmlDataParser2.getValueOf("ADDRESS2")); sbPartyAddList.append("</partyAddress2>");
          sbPartyAddList.append("<partyAddress3>"); sbPartyAddList.append(xmlDataParser2.getValueOf("ADDRESS3")); sbPartyAddList.append("</partyAddress3>");
          sbPartyAddList.append("<partyAddress4>"); sbPartyAddList.append(xmlDataParser2.getValueOf("ADDRESS4")); sbPartyAddList.append("</partyAddress4>");
		  sbPartyAddList.append("<partyCountry>"); sbPartyAddList.append(country); sbPartyAddList.append("</partyCountry>");
        }else{
			sbPartyAddList.append("<partyName></partyName>");
			sbPartyAddList.append("<partyAddress1></partyAddress1>");
			sbPartyAddList.append("<partyAddress2></partyAddress2>");
			sbPartyAddList.append("<partyAddress3></partyAddress3>");
			sbPartyAddList.append("<partyAddress4></partyAddress4>");
			sbPartyAddList.append("<partyCountry></partyCountry>");
          
		}
        sbPartyAddList.append("</contractParty>");
      }	  
    }
    catch (Exception e) {
      WriteToLog_showpage("Y"," Excpt getContractPartyDetailsforCreate " + e);
    }
    return sbPartyAddList.toString();
}

private String getPartyIdFromLimitPartyType(String limitPartyType, String sWINumber) 
{
    String partyID = "";
    try {		
		sQuery = "SELECT PARTY_ID,PARTY_TYPE FROM TFO_DJ_PARTY_DETAILS WHERE WINAME = '"+ sWINumber +"'";
		sInput = prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
		WriteToLog_showpage("Y"," ***************getPartyIdFromLimitPartyType Input "+sInput+" *****************************");
		sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);;
		WriteToLog_showpage("Y"," ***************getPartyIdFromLimitPartyType sOutput "+sOutput+" *****************************");
		XMLParser xmlDataParser1 = new XMLParser();
		XMLParser xmlDataParser2 = new XMLParser();
		xmlDataParser1.setInputXML(sOutput);
		int iPartiesCount = xmlDataParser1.getNoOfFields("Record");
		for (int i = 0; i < iPartiesCount; ++i) {
			xmlDataParser2.setInputXML(xmlDataParser1.getNextValueOf("Record"));
			WriteToLog_showpage("Y","Party Type " + xmlDataParser2.getValueOf("PARTY_TYPE").trim());
			if(limitPartyType.equalsIgnoreCase(xmlDataParser2.getValueOf("PARTY_TYPE").trim())){
				partyID = xmlDataParser2.getValueOf("PARTY_ID");
				break;
			}
		}	  
    }
    catch (Exception e) {
      WriteToLog_showpage("Y"," Excpt getPartyIdFromLimitPartyType " + e);
    }
    return partyID;
}

private String fetchBranchCode(String Relationship_Type,String sWorkItemNo){
	String sBranchCode = "";
	try{
		sQuery="SELECT BRN_CODE FROM EXT_TFO WHERE WI_NAME = '"+sWorkItemNo+"'";
		sInput=prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
		WriteToLog_showpage("Y"," ***************fetchBranchCode in EXT "+sInput+" *****************************");
		sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);;	
		WriteToLog_showpage("Y"," ***************fetchBranchCode in sOutput "+sOutput+" *****************************");
		sBranchCode = sOutput.substring(sOutput.indexOf("<BRN_CODE>")+"<BRN_CODE>".length(),sOutput.indexOf("</BRN_CODE>"));
		WriteToLog_showpage("Y"," ***************fetchBranchCode in sBranchCode "+sBranchCode+" *****************************");
		/*if ("".equalsIgnoreCase(sBranchCode)) {
			if (("C".equalsIgnoreCase(Relationship_Type)) || ("Conventional".equalsIgnoreCase(Relationship_Type)))
			{
				sBranchCode = "299";
			}
			else
				sBranchCode = "799";
		} */
	}
	catch (Exception e) {
      WriteToLog_showpage("Y"," Excpt fetchBranchCode " + e);
    }
	WriteToLog_showpage("Y"," ***************fetchBranchCode in sBranchCode RETURN "+sBranchCode+" *****************************");
	return sBranchCode;
}

private String fetchAmendmentTabData(String sWorkItemNo){
	
	try{
		sQuery="SELECT FIN_REQUESTED_CONFIRMATION_PARTY, FIN_SPL_PAY_COND_FOR_BEN,FIN_CREDIT_MODE FROM TFO_DJ_AMENDMENT_FRAME_DATA WHERE WINAME = '"+sWorkItemNo+"'";
		sInput=prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
		WriteToLog_showpage("Y"," ***************fetchAmendmentTabData in input "+sInput+" *****************************");
		sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
		WriteToLog_showpage("Y"," ***************fetchAmendmentTabData in output "+sOutput+" *****************************");		
		WriteToLog_showpage("Y"," ***************fetchAmendmentTabData in kdd *****************************");
		sRequestedConfirmationParty = sOutput.substring(sOutput.indexOf("<FIN_REQUESTED_CONFIRMATION_PARTY>")+"<FIN_REQUESTED_CONFIRMATION_PARTY>".length(),sOutput.indexOf("</FIN_REQUESTED_CONFIRMATION_PARTY>"));
				WriteToLog_showpage("Y"," ***************fetchAmendmentTabData in kdd1 *****************************");

		sSpecialPaymentConditionForBeneficiary = sOutput.substring(sOutput.indexOf("<FIN_SPL_PAY_COND_FOR_BEN>")+"<FIN_SPL_PAY_COND_FOR_BEN>".length(),sOutput.indexOf("</FIN_SPL_PAY_COND_FOR_BEN>"));
		sCreditMode = sOutput.substring(sOutput.indexOf("<FIN_CREDIT_MODE>")+"<FIN_CREDIT_MODE>".length(),sOutput.indexOf("</FIN_CREDIT_MODE>"));
				WriteToLog_showpage("Y"," ***************fetchAmendmentTabData in kdd2 *****************************");

		
		WriteToLog_showpage("Y"," ***************fetchAmendmentTabData in output "+sOutput+" *****************************");

	}
	catch (Exception e) {
      WriteToLog_showpage("Y"," Excpt fetchBranchCode " + e);
    }
	return "";
}


private String fetchFCUBSDate(){
	WriteToLog_showpage("Y"," ***************fetchBranchCode in EXTfetchFCUBSDate *****************************");
	String sFCUBSDate = "";
	try{
		sQuery="SELECT (CASE WHEN OT.DEF_VALUE='U' THEN (SELECT TO_CHAR(INN.DEF_VALUE) FROM TFO_DJ_DEFAULT_MAST INN WHERE INN.DEF_NAME='FCUBS_BACK_DATE' )"+
				"ELSE TO_CHAR(SYSDATE,'DD/MM/YYYY') END) FCUBS_DT FROM TFO_DJ_DEFAULT_MAST OT WHERE  OT.DEF_NAME ='FCUBS_DATE'";
		sInput=prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
		WriteToLog_showpage("Y"," ***************fetchBranchCode in EXT "+sInput+" *****************************");
		sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);;	
		sFCUBSDate = sOutput.substring(sOutput.indexOf("<FCUBS_DT>")+"<FCUBS_DT>".length(),sOutput.indexOf("</FCUBS_DT>"));
	}
	catch (Exception e) {
      WriteToLog_showpage("Y"," Excpt fetchBranchCode " + e);
    }
	return sFCUBSDate;
}

private String getContractPartyDetailsforIFPCreate(String sCustomerId, String sWINumber) 
{
    StringBuilder sbPartyAddList = new StringBuilder();
    try {
        HashMap map=fetchIFPDraweeCounterPartyName(sWINumber);	
		sbPartyAddList.append("<contractParty>");
		sbPartyAddList.append("<partyType>"); sbPartyAddList.append("DRAWEE"); sbPartyAddList.append("</partyType>");
		sbPartyAddList.append("<partyId>"); sbPartyAddList.append(sCustomerId); sbPartyAddList.append("</partyId>");
		sbPartyAddList.append("<partyLanguage>"); sbPartyAddList.append("ENG"); sbPartyAddList.append("</partyLanguage>");
		sbPartyAddList.append("</contractParty>");
		sbPartyAddList.append("<contractParty>");
		sbPartyAddList.append("<partyType>"); sbPartyAddList.append("DRAWER"); sbPartyAddList.append("</partyType>");
		sbPartyAddList.append("<partyId>"); sbPartyAddList.append("100000"); sbPartyAddList.append("</partyId>");
		sbPartyAddList.append("<partyName>"); sbPartyAddList.append(map.get("COUNTER_PARTY_NAME")); sbPartyAddList.append("</partyName>");
		sbPartyAddList.append("<partyLanguage>"); sbPartyAddList.append("ENG"); sbPartyAddList.append("</partyLanguage>");
		sbPartyAddList.append("<partyCountry>"); sbPartyAddList.append(map.get("COUNTER_COUNTRY_NAME")); sbPartyAddList.append("</partyCountry>");
		sbPartyAddList.append("</contractParty>");		
    }	
	catch (Exception e){
      WriteToLog_showpage("Y"," Excpt getPartyDetailsforCreateAmend " + e);
    } 
    return sbPartyAddList.toString();
}

private String getLimitTrackingFlag(String wi_name)
{
	String limitFlag="N";
	int count = 0;
	XMLParser xmlDataParser = new XMLParser();
	try{
		   sQuery  = "SELECT COUNT(1) AS CNT FROM TFO_DJ_CONTRACT_LIMITS_DETAILS WHERE WINAME  = '"+wi_name+"'";
		  WriteToLog_showpage("Y"," Query " + sQuery);
		   sInput = prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
		  WriteToLog_showpage("Y"," ***************getLimitTrackingFlag Input "+sInput+" *****************************");
		   sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);;
		  WriteToLog_showpage("Y"," ***************getLimitTrackingFlag sOutput "+sOutput+" *****************************");
		  xmlDataParser.setInputXML(sOutput);
		  int record = xmlDataParser.getNoOfFields("Record");
		  if (record > 0)
			count = Integer.parseInt(xmlDataParser.getValueOf("CNT"));
		
		if(count>0)
			limitFlag="Y";
			
	} catch (Exception e)
    {
		WriteToLog_showpage("Y"," Excpt getLimitTrackingFlag " + e);
    }
    return limitFlag;
	
}

private String setPartyLimit(String sWINumber, String sRequestCategoryCode,String limitFlag)
{
	
	String sLimitPartyType="";
	String sLimitLinkageType="";
	String sLimitOperation="";
	String sLimitPercentContribution="";
	String sLimitLineCustomerId="";
	String sLimitSerialNumber="";
	String sLimitLineRefNumber="";
	String slimitAmountTag="";
	StringBuilder sPartyLimit = new StringBuilder();
	try{
	if("N".equalsIgnoreCase(limitFlag))
	{
	
		 sLimitPartyType="";
		 sLimitLinkageType="";
		 sLimitOperation="";
		 sLimitPercentContribution="";
		 sLimitLineCustomerId="";
		 sLimitSerialNumber="";
		 sLimitLineRefNumber="";
		 slimitAmountTag="";
	
		sPartyLimit.append("<partyLimits>");
		sPartyLimit.append("<partyLimit>");
		sPartyLimit.append("<limitSerialNumber>"+sLimitSerialNumber+"</limitSerialNumber>");
		sPartyLimit.append("<limitPartyType>"+sLimitPartyType+"</limitPartyType>");
		sPartyLimit.append("<limitLinkageType>"+sLimitLinkageType+"</limitLinkageType>");
		sPartyLimit.append("<limitOperation>"+sLimitOperation+"</limitOperation>");
		sPartyLimit.append("<limitPercentContribution>"+sLimitPercentContribution+"</limitPercentContribution>");
		sPartyLimit.append("<limitLineCustomerId>"+sLimitLineCustomerId+"</limitLineCustomerId>");
		sPartyLimit.append("<limitAmountTag>"+slimitAmountTag+"</limitAmountTag>");
		sPartyLimit.append("<limitLineRefNumber>"+sLimitLineRefNumber+"</limitLineRefNumber>");
		sPartyLimit.append("</partyLimit>");
		sPartyLimit.append("</partyLimits>");
	
	}else if("Y".equalsIgnoreCase(limitFlag))
	{
		sPartyLimit.append("<partyLimits>");
		sQuery = "SELECT SERIAL_NUMBER,OPERATION,PARTY_TYPE,CUSTOMER_NO,TYPE,LINKAGE_REFERENCE_NO,PERCENTAGE_CONTRIBUTION,AMOUNT_TAG FROM TFO_DJ_CONTRACT_LIMITS_DETAILS WHERE WINAME = '"+ sWINumber +"'";
		sInput = prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
		WriteToLog_showpage("Y"," ***************setPartyLimit Input "+sInput+" *****************************");
		sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);;
		WriteToLog_showpage("Y"," ***************setPartyLimit sOutput "+sOutput+" *****************************");
		XMLParser xmlDataParser1 = new XMLParser();
		XMLParser xmlDataParser2 = new XMLParser();
		xmlDataParser1.setInputXML(sOutput);
		int iLimitCount = xmlDataParser1.getNoOfFields("Record");
		WriteToLog_showpage("Y","limit count " +iLimitCount );
		for (int i = 0; i < iLimitCount; ++i) {
			xmlDataParser2.setInputXML(xmlDataParser1.getNextValueOf("Record"));
			WriteToLog_showpage("Y","Limit Type " + xmlDataParser2.getValueOf("PARTY_TYPE").trim());
			
		 sLimitPartyType= xmlDataParser2.getValueOf("PARTY_TYPE");
		 sLimitLinkageType =  xmlDataParser2.getValueOf("TYPE");
		 sLimitOperation= xmlDataParser2.getValueOf("OPERATION");
		 sLimitPercentContribution= xmlDataParser2.getValueOf("PERCENTAGE_CONTRIBUTION");
		 sLimitLineCustomerId= xmlDataParser2.getValueOf("CUSTOMER_NO");
		 sLimitSerialNumber= xmlDataParser2.getValueOf("SERIAL_NUMBER");
		 sLimitLineRefNumber= xmlDataParser2.getValueOf("LINKAGE_REFERENCE_NO");
		 slimitAmountTag= xmlDataParser2.getValueOf("AMOUNT_TAG");
		 sPartyLimit.append("<partyLimit>");
		sPartyLimit.append("<limitSerialNumber>"+sLimitSerialNumber+"</limitSerialNumber>");
		sPartyLimit.append("<limitPartyType>"+sLimitPartyType+"</limitPartyType>");
		sPartyLimit.append("<limitLinkageType>"+sLimitLinkageType+"</limitLinkageType>");
		sPartyLimit.append("<limitOperation>"+sLimitOperation+"</limitOperation>");
		sPartyLimit.append("<limitPercentContribution>"+sLimitPercentContribution+"</limitPercentContribution>");
		sPartyLimit.append("<limitLineCustomerId>"+sLimitLineCustomerId+"</limitLineCustomerId>");
		sPartyLimit.append("<limitAmountTag>"+slimitAmountTag+"</limitAmountTag>");
		sPartyLimit.append("<limitLineRefNumber>"+sLimitLineRefNumber+"</limitLineRefNumber>");
		sPartyLimit.append("</partyLimit>");
		
		
		}
	sPartyLimit.append("</partyLimits>");	
	}	
	}catch(Exception e)
	{
		 WriteToLog_showpage("Y"," Excpt setPartyLimit" + e);
	}
	return sPartyLimit.toString();
}
private String checkPartyTypeContract(String sWINumber){
    String returnValue="N";
	int count = 0;
	XMLParser xmlDataParser = new XMLParser();
	try{
		   sQuery  = "SELECT count(1) as value FROM TFO_DJ_PARTY_DETAILS WHERE WINAME = '"+sWINumber+"' and party_type like 'RS%'";
		  WriteToLog_showpage("Y"," Query " + sQuery);
		   sInput = prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
		  WriteToLog_showpage("Y"," ***************checkPartyTypeContract Input "+sInput+" *****************************");
		   sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);;
		  WriteToLog_showpage("Y"," ***************checkPartyTypeContract sOutput "+sOutput+" *****************************");
		  xmlDataParser.setInputXML(sOutput);
		  int record = xmlDataParser.getNoOfFields("Record");
		  if (record > 0)
			count = Integer.parseInt(xmlDataParser.getValueOf("value"));
		
		if(count>0)
			returnValue="Y";
			
	} catch (Exception e)
    {
		WriteToLog_showpage("Y"," Excpt getLimitTrackingFlag " + e);
    }
    return returnValue;
	
}
private HashMap fetchIFPDraweeCounterPartyName(String sWorkItemNo){
	String sCounterPartyName = "";
    String sCounterCountryName="";
	HashMap map = null;
	try{
		map=new HashMap();
		sQuery="SELECT SUBSTR(CP_NAME,0,105) AS COUNTER_PARTY_NAME,CP_COUNTRY AS COUNTER_COUNTRY_NAME FROM TFO_DJ_CPD_DETAILS WHERE WI_NAME='"+sWorkItemNo+"' AND SNO =(SELECT MIN(TO_NUMBER(SNO)) FROM TFO_DJ_CPD_DETAILS WHERE WI_NAME ='"+sWorkItemNo+"')";
		sInput=prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
		WriteToLog_showpage("Y"," ***************fetchIFPDraweeCounterPartyName in EXT "+sInput+" *****************************");
		sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);;	
		sCounterPartyName = sOutput.substring(sOutput.indexOf("<COUNTER_PARTY_NAME>")+"<COUNTER_PARTY_NAME>".length(),sOutput.indexOf("</COUNTER_PARTY_NAME>"));
		sCounterCountryName = sOutput.substring(sOutput.indexOf("<COUNTER_COUNTRY_NAME>")+"<COUNTER_COUNTRY_NAME>".length(),sOutput.indexOf("</COUNTER_COUNTRY_NAME>"));
		map.put("COUNTER_PARTY_NAME",sCounterPartyName);
		map.put("COUNTER_COUNTRY_NAME",sCounterCountryName);
	}
	catch (Exception e) {
      WriteToLog_showpage("Y"," Excpt fetchIFPDraweeCounterPartyName " + e);
    }
	return map;
}

private String getPartyOtherDetails(String sWIName) 
{
    StringBuilder sbPartyAddList = new StringBuilder();
    try {
		String mediaType="";
		sQuery = "SELECT MEDIA_TYPE,replace(replace(ADDRESS,'<![CDATA[',''),']]>','') AS ADDRESS FROM TFO_DJ_PARTY_DETAILS WHERE WINAME = '"+ sWIName +"'";
		sInput = prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
		WriteToLog_showpage("Y"," ***************getPartyOtherDetails Input "+sInput+" *****************************");
		sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);;
		WriteToLog_showpage("Y"," ***************getPartyOtherDetails sOutput "+sOutput+" *****************************");
		XMLParser xmlDataParser1 = new XMLParser();
		XMLParser xmlDataParser2 = new XMLParser();
		xmlDataParser1.setInputXML(sOutput);
		int iPartiesCount = xmlDataParser1.getNoOfFields("Record");
		for (int i = 0; i < iPartiesCount; ++i) {
			
			if("0".equalsIgnoreCase(xmlDataParser2.getValueOf("MEDIA_TYPE"))){
				mediaType="";
			}else{
				mediaType=xmlDataParser2.getValueOf("MEDIA_TYPE");
			}
			xmlDataParser2.setInputXML(xmlDataParser1.getNextValueOf("Record"));
			sbPartyAddList.append("<partyOtherAddress>");
			sbPartyAddList.append("<mediaType>"); sbPartyAddList.append(mediaType); sbPartyAddList.append("</mediaType>");
			sbPartyAddList.append("<mediaAddress>"); sbPartyAddList.append(xmlDataParser2.getValueOf("ADDRESS")); sbPartyAddList.append("</mediaAddress>");
			sbPartyAddList.append("</partyOtherAddress>");
      }
    }
    catch (Exception e) {
      WriteToLog_showpage("Y"," Excpt getPartyOtherDetails " + e);
    }
    return sbPartyAddList.toString();
}


private String setAdviceDetails(String operationCode,String sWorkItemNo){
	StringBuilder adviceData = new StringBuilder();
	boolean  adviceFlag = false;
	String partyID="";
	try{
		sQuery="SELECT ADD_CONDITIONS,INSTR_TOPAY_ACP_NEG_BNK,SEN_REC_INFO FROM TFO_DJ_SWIFT_TXN_DETAILS WHERE WI_NAME='"+sWorkItemNo+"'";
		sInput=prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
		WriteToLog_showpage("Y"," ***************setAdviceDetails in EXT "+sInput+" *****************************"+operationCode);
		sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);	
		 String addnCondition = sOutput.substring(sOutput.indexOf("<ADD_CONDITIONS>")+"<ADD_CONDITIONS>".length(),sOutput.indexOf("</ADD_CONDITIONS>"));
		 String instrToPay = sOutput.substring(sOutput.indexOf("<INSTR_TOPAY_ACP_NEG_BNK>")+"<INSTR_TOPAY_ACP_NEG_BNK>".length(),sOutput.indexOf("</INSTR_TOPAY_ACP_NEG_BNK>"));
		 String senRecInfo = sOutput.substring(sOutput.indexOf("<SEN_REC_INFO>")+"<SEN_REC_INFO>".length(),sOutput.indexOf("</SEN_REC_INFO>"));

		                if(addnCondition!=null && !"".equalsIgnoreCase(addnCondition)){
							adviceFlag=true;
						}else if(instrToPay!=null && !"".equalsIgnoreCase(instrToPay)){
							adviceFlag=true;
						}else if(senRecInfo!=null && !"".equalsIgnoreCase(senRecInfo)){
							adviceFlag=true;
						}
						if(adviceFlag){
							 partyID=getPartyIdFromPartyType("'ATB'",sWorkItemNo,"ELC_LCA");
						     if(partyID!=null && !"".equalsIgnoreCase(partyID)){
							 adviceData.append("<adviceDetail>");
			                 adviceData.append("<messageType>"); adviceData.append("ADV_THIRD_BANK"); adviceData.append("</messageType>");
			                 adviceData.append("<messageParty>"); adviceData.append("ATB"); adviceData.append("</messageParty>");
		                     adviceData.append("<messagePartyId>"); adviceData.append(partyID); adviceData.append("</messagePartyId>");
                             adviceData.append("</adviceDetail>");
							} else if("ADV".equalsIgnoreCase(operationCode)){
							 partyID=getPartyIdFromPartyType("'BEN'",sWorkItemNo,"ELC_LCA");
							 adviceData.append("<adviceDetail>");
			                 adviceData.append("<messageType>"); adviceData.append("ISB_BEN_CL"); adviceData.append("</messageType>");
			                 adviceData.append("<messageParty>"); adviceData.append("BEN"); adviceData.append("</messageParty>");
		                     adviceData.append("<messagePartyId>"); adviceData.append(partyID); adviceData.append("</messagePartyId>");
                             adviceData.append("</adviceDetail>");
						    } else if("ANC".equalsIgnoreCase(operationCode)||"CNF".equalsIgnoreCase(operationCode)){
							 partyID=getPartyIdFromPartyType("'BEN'",sWorkItemNo,"ELC_LCA");
							 adviceData.append("<adviceDetail>");
			                 adviceData.append("<messageType>"); adviceData.append("ADVICE_CL"); adviceData.append("</messageType>");
			                 adviceData.append("<messageParty>"); adviceData.append("BEN"); adviceData.append("</messageParty>");
		                     adviceData.append("<messagePartyId>"); adviceData.append(partyID); adviceData.append("</messagePartyId>");
                             adviceData.append("</adviceDetail>");
							}
							 
						} 
	}
	catch (Exception e) {
      WriteToLog_showpage("Y"," Excpt fetchBranchCode " + e);
    }
	return adviceData.toString();
}
private String getSWIFTTXNDetails(String sWIName, String sColumnName) 
{
    String sformDocCRD = "";
    try {
		sQuery = "SELECT "+sColumnName+" FROM TFO_DJ_SWIFT_TXN_DETAILS WHERE WI_NAME = '"+ sWIName +"'";
		sInput = prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
		WriteToLog_showpage("Y"," ***************getSWIFTTXNDetails Input "+sInput+" *****************************");
		sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);;
		WriteToLog_showpage("Y"," ***************getSWIFTTXNDetails sOutput "+sOutput+" *****************************");
		XMLParser xmlDataParser1 = new XMLParser();
		xmlDataParser1.setInputXML(sOutput);			
		sformDocCRD = xmlDataParser1.getValueOf(sColumnName);      
    }
    catch (Exception e) {
      WriteToLog_showpage("Y"," Excpt getSWIFTTXNDetails " + e);
    }
    return sformDocCRD;
 }
 
private String setProTradeRemark(String sWINumber)
{
	try 
	{
		sQuery = "SELECT INSTRCTN_TO_BANK,PRO_TRADE_SETTLEMENT_INST FROM EXT_TFO WHERE WI_NAME = '"+ sWINumber +"'";
		sInput = prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
		WriteToLog_showpage("Y"," ***************setProTradeRemark Input "+sInput+" *****************************");
		sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);;
		WriteToLog_showpage("Y"," ***************setProTradeRemark sOutput "+sOutput+" *****************************");
		XMLParser xmlDataParser1 = new XMLParser();
		xmlDataParser1.setInputXML(sOutput);
		String proInstructions = xmlDataParser1.getValueOf("INSTRCTN_TO_BANK");
		String proSettlement = xmlDataParser1.getValueOf("PRO_TRADE_SETTLEMENT_INST");
		if ((((proInstructions == null) || (proInstructions.equals("")))) && (((proSettlement == null) || (proSettlement.equals("")))))
		{
			return "";
		}
		return normalizeString(proInstructions) + "," + normalizeString(proSettlement);
	}
	catch (Exception e)
	{
	  WriteToLog_showpage("Y"," Excpt setProTradeRemark " + e);
	}
	return "";
}
 
private String getProductDesc(String productCode)
{
	try 
	{
		sQuery = "select PRD_DESC from tfo_dj_prd_mast where prd_key='"+ productCode +"'";
		sInput = prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
		WriteToLog_showpage("Y"," ***************setProTradeRemark Input "+sInput+" *****************************");
		sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);;
		WriteToLog_showpage("Y"," ***************setProTradeRemark sOutput "+sOutput+" *****************************");
		XMLParser xmlDataParser1 = new XMLParser();
		xmlDataParser1.setInputXML(sOutput);
		String proInstructions = xmlDataParser1.getValueOf("PRD_DESC");
		
		
		return normalizeString(proInstructions.split(":")[1].trim());
	}
	catch (Exception e)
	{
	  WriteToLog_showpage("Y"," Excpt setProTradeRemark " + e);
	}
	return "";
}

private String getProductGTEETypeCode(String productCode,String requestCategory,String requestType)
{
	try 
	{
		sQuery = "select GTEE_TYPE_CODE from TFO_DJ_GTEE_TYPE_CODE_MAST where request_category='"+ requestCategory +"' and request_type='"+ requestType +"' and (PRODUCT_CODE='"+ productCode +"' or  PRODUCT_CODE='ALL')";
		sInput = prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
		WriteToLog_showpage("Y"," ***************setProTradeRemark Input "+sInput+" *****************************");
		sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);;
		WriteToLog_showpage("Y"," ***************setProTradeRemark sOutput "+sOutput+" *****************************");
		XMLParser xmlDataParser1 = new XMLParser();
		xmlDataParser1.setInputXML(sOutput);
		String proInstructions = xmlDataParser1.getValueOf("GTEE_TYPE_CODE");
		
		
		return proInstructions;
	}
	catch (Exception e)
	{
	  WriteToLog_showpage("Y"," Excpt setProTradeRemark " + e);
	}
	return "";
}

private String convertToPlainString(String sInputXML){
	StringBuffer outputxml = new StringBuffer();
	WriteToLog_showpage("Y"," sInputXML.length() " + sInputXML.length());
	sInputXML=sInputXML.replace("SessionId","SessionIdTemp");
    if (sInputXML.length() > 4000) {
      int itr = sInputXML.length() / 4000;
      int mod = sInputXML.length() % 4000;
      if (mod > 0) {
        ++itr;
      }
      WriteToLog_showpage("Y"," itr " + itr);
      for (int i = 0; i < itr; ++i) {
        WriteToLog_showpage("Y"," output part " + i + 1);
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
public String normalizeString(String str) {
    try {
      if ((str == null) ||(str.trim().equalsIgnoreCase("null"))) {
        return "";
      }
	}
    catch (Exception e) {
      WriteToLog_showpage("Y"," Excpt normalizeString " + e);
    }
    return str.trim();
}
 
 
 public void initializeLogger(){
			try{
				Properties properties = new Properties();
				String log4JPropertyFile =  System.getProperty("user.dir") +  System.getProperty("file.separator") + "NGConfig" + System.getProperty("file.separator") + "TFO" +  System.getProperty("file.separator") +  "log4jJSP.properties";
				properties.load(new FileInputStream(log4JPropertyFile));
				PropertyConfigurator.configure(properties);
			}catch(Exception e){
				e.printStackTrace();
			}
		} 
		
	Logger log = Logger.getLogger(_FCUBS_5F_Integration_5F_Calls_5F_Executor.class);
	
	private void WriteToLog_showpage(String flag,String strOutput){
		log.info(strOutput);
	}
	
	
/*private void WriteToLog_showpage(String flag,String strOutput)
{
    StringBuffer str = new StringBuffer();
    str.append(DateFormat.getDateTimeInstance(0,2).format(new java.util.Date()));
    str.append("\n");
    str.append(strOutput);
    str.append("\n");

    StringBuffer strFilePath = null;
    String tmpFilePath="";
    try 
    {
		
		if("Y".equalsIgnoreCase(flag) || "I".equalsIgnoreCase(flag)){
			Calendar calendar=new GregorianCalendar();
			String DtString=String.valueOf(""+calendar.get(Calendar.DAY_OF_MONTH) +(calendar.get(Calendar.MONTH) + 1) + calendar.get(Calendar.YEAR));
			strFilePath = new StringBuffer(50);
			strFilePath.append(System.getProperty("user.dir"));
			strFilePath.append(File.separatorChar);
			strFilePath.append("ApplicationLogs");
			strFilePath.append(File.separatorChar);
			strFilePath.append("ProcessLogs");
			strFilePath.append(File.separatorChar);
			strFilePath.append("TFO");
			strFilePath.append(File.separatorChar);
			strFilePath.append("JSPLogs");
			strFilePath.append(File.separatorChar);
			strFilePath.append("FCUBS_Integration_Calls_Executor"+DtString+".xml");
			tmpFilePath = strFilePath.toString();
			
			//PrintStream out = new PrintStream(new FileOutputStream(tmpFilePath), true);
			BufferedWriter out = new BufferedWriter(new FileWriter(tmpFilePath, true));
			
			out.write(str.toString());
			out.close();
			
		}
    } catch (Exception exception) {
    } finally {
        strFilePath = null;
    }    
}*/

private static String StackTraceToString_showpage(Exception e) {
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        e.printStackTrace(printWriter);
        return result.toString();
}

private String characterHandler(String str) {
        
        return str.replaceAll("'","''");
}


private String getDocumentDetailsforCreateAmend(String sWINumber,String reqType) 
{
    StringBuilder sbDocAddList = new StringBuilder();
    try {
		String documentCode = "";
		String documentDescription = "";
		String documentCopy  = "";
		String documentOriginal = "";
		sQuery = "SELECT DOCUMENT_TYPE,DOCUMENT_DESCRIPTION,DOCUMENT_COPIES,DOCUMENT_ORIGINAL FROM TFO_DJ_PROTRADE_DOCUMENT_DETAIL WHERE WINAME = '" +sWINumber+ "'";
		
		sInput = prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
		WriteToLog_showpage("Y"," ***************getDocumentDetailsforCreateAmend Input "+sInput+" *****************************");
		sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);;
		WriteToLog_showpage("Y"," ***************getDocumentDetailsforCreateAmend sOutput "+sOutput+" *****************************");
		
		XMLParser xmlDataParser1 = new XMLParser();
		XMLParser xmlDataParser2 = new XMLParser();
		xmlDataParser1.setInputXML(sOutput);
		int iDocCount = xmlDataParser1.getNoOfFields("Record");
		WriteToLog_showpage("Y"," ***************getDocumentDetailsforCreateAmend iDocCount "+iDocCount+" *****************************");
		
		
		for (int i = 0; i < iDocCount; ++i) {
			xmlDataParser2.setInputXML(xmlDataParser1.getNextValueOf("Record"));
			WriteToLog_showpage("Y","Document Type " + xmlDataParser2.getValueOf("DOCUMENT_TYPE").trim());
			documentCode = xmlDataParser2.getValueOf("DOCUMENT_TYPE");
			documentDescription = xmlDataParser2.getValueOf("DOCUMENT_DESCRIPTION");
			documentCopy  = xmlDataParser2.getValueOf("DOCUMENT_COPIES");
			documentOriginal  = xmlDataParser2.getValueOf("DOCUMENT_ORIGINAL");
			String docOrg="N";
			if(!"".equalsIgnoreCase(documentOriginal)){
				docOrg="Y";
			}
			if("ILC_NI".equalsIgnoreCase(reqType)){
			sbDocAddList.append("<documentDetail>");
			sbDocAddList.append("<documentCode>"); sbDocAddList.append(documentCode); sbDocAddList.append("</documentCode>");
			sbDocAddList.append("<documentDescription>"); sbDocAddList.append(documentDescription); sbDocAddList.append("</documentDescription>");
			sbDocAddList.append("<documentCopy>"); sbDocAddList.append(documentCopy); sbDocAddList.append("</documentCopy>");
			sbDocAddList.append("<documentOriginal>"); sbDocAddList.append(docOrg); sbDocAddList.append("</documentOriginal>");
            sbDocAddList.append("<numberOfOriginals>"); sbDocAddList.append(documentOriginal); sbDocAddList.append("</numberOfOriginals>");
			sbDocAddList.append("</documentDetail>");
		}else{
			sbDocAddList.append("<documentDetail>");
			sbDocAddList.append("<documentCode>"); sbDocAddList.append(documentCode); sbDocAddList.append("</documentCode>");
			sbDocAddList.append("<documentDescription>"); sbDocAddList.append(documentDescription); sbDocAddList.append("</documentDescription>");
			sbDocAddList.append("<firstMailCopy>"); sbDocAddList.append(documentCopy); sbDocAddList.append("</firstMailCopy>");
			sbDocAddList.append("<firstMailOriginal>"); sbDocAddList.append(documentOriginal); sbDocAddList.append("</firstMailOriginal>");
			sbDocAddList.append("</documentDetail>");
			}
			
		}
		
		
	}catch (Exception e) {
      WriteToLog_showpage("Y"," Excpt getDocumentDetailsforCreateAmend " + e);
    }
	WriteToLog_showpage("Y"," ***************getDocumentDetailsforCreateAmend sbDocAddList "+sbDocAddList.toString()+" *****************************");
    return sbDocAddList.toString();
}

private String FetchFFTData(String sWorkItemNo){
	    StringBuilder sFFTDetailList = new StringBuilder();
		XMLParser xmlDataParser1 = new XMLParser();
		XMLParser xmlDataParser2 = new XMLParser();
		String sFFTCode="";
		String sFFTDesc="";
	try{
		sQuery="select FFT_CODE,FFT_DESC from TFO_DJ_PROTRADE_FFT_DETAILS WHERE WINAME = '"+sWorkItemNo+"'";
		sInput=prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
		WriteToLog_showpage("Y"," ****SUDH***********fetchBranchCode in EXT "+sInput+" ************SUDH*****************");
		sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);	
        xmlDataParser1.setInputXML(sOutput);
		int iDocCount = xmlDataParser1.getNoOfFields("Record");
		WriteToLog_showpage("Y"," ***************FetchFFTData iDocCount "+iDocCount+" *****************************");
		
		
		for (int i = 0; i < iDocCount; ++i) {
			xmlDataParser2.setInputXML(xmlDataParser1.getNextValueOf("Record"));
			sFFTCode = xmlDataParser2.getValueOf("FFT_CODE");
			sFFTDesc  = xmlDataParser2.getValueOf("FFT_DESC");
			sFFTDetailList.append("<freeFormatTextDtl>");
			sFFTDetailList.append("<freeFormatTextCode>"); sFFTDetailList.append(sFFTCode); sFFTDetailList.append("</freeFormatTextCode>");
			sFFTDetailList.append("<freeFormatTextDescription>"); sFFTDetailList.append(sFFTDesc); sFFTDetailList.append("</freeFormatTextDescription>");
			sFFTDetailList.append("</freeFormatTextDtl>");
		}
	}catch (Exception e) {
      WriteToLog_showpage("Y"," Excpt fetchBranchCode " + e);
    }
	return sFFTDetailList.toString();
} 

private String getPartyIdFromPartyType(String limitPartyType, String sWINumber,String requestType) 
{
    String partyID = "";
    try {		
		sQuery = "SELECT PARTY_ID,PARTY_TYPE FROM TFO_DJ_PARTY_DETAILS WHERE WINAME = '"+ sWINumber +"' and party_type in("+limitPartyType+")  order by party_type ";
		sInput = prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
		WriteToLog_showpage("Y"," ***************getPartyIdFromLimitPartyType Input "+sInput+" *****************************");
		String sOutput1 = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);;
		WriteToLog_showpage("Y"," ***************getPartyIdFromLimitPartyType sOutput "+sOutput1+" *****************************");
		XMLParser xmlDataParser1 = new XMLParser();
		XMLParser xmlDataParser2 = new XMLParser();
		xmlDataParser1.setInputXML(sOutput1);
		int iPartiesCount = xmlDataParser1.getNoOfFields("Record");
		for (int i = 0; i < iPartiesCount; ++i) {
			xmlDataParser2.setInputXML(xmlDataParser1.getNextValueOf("Record"));
			WriteToLog_showpage("Y","Party Type " + xmlDataParser2.getValueOf("PARTY_TYPE").trim());
				partyID = xmlDataParser2.getValueOf("PARTY_ID");
				if("NI".equalsIgnoreCase(requestType)){
					partyID=partyID+","+xmlDataParser2.getValueOf("PARTY_TYPE").trim();
				}else if ("SBLC_NI".equalsIgnoreCase(requestType)){
					partyID=partyID+","+xmlDataParser2.getValueOf("PARTY_TYPE").trim();
				}else if ("GA".equalsIgnoreCase(requestType)){
					partyID=partyID+","+xmlDataParser2.getValueOf("PARTY_TYPE").trim();
				}else if ("ELC_SLCA".equalsIgnoreCase(requestType)){
					partyID=partyID+","+xmlDataParser2.getValueOf("PARTY_TYPE").trim();
				}
				break;
			
		}	  
    }
    catch (Exception e) {
      WriteToLog_showpage("Y"," Excpt getPartyIdFromLimitPartyType " + e);
    }
    return partyID;
}

private String SetSwiftMQWIFFtData(String sWIName,String fftData){ //WORK FOR CASES FOR WHICH MAPPING IN MAP 
			WriteToLog_showpage("Y"," INSIDE SetSwiftMQWIFFtData");
			StringBuilder sFFTDetailList = new StringBuilder();
			String DOC_PRESENINSTR_C = getSWIFTTXNDetails(sWIName,"DOC_PRESENINSTR_C"); // DATA in DOC_PRESENINSTR_C present only for cases which its meant to be
			
			if(checkforDOC_PRESENINSTR_C_Case(sWIName)){
				WriteToLog_showpage("Y"," INSIDE SetSwiftMQWIFFtData check 441");
				if(DOC_PRESENINSTR_C != null || !DOC_PRESENINSTR_C.equalsIgnoreCase("")){
					WriteToLog_showpage("Y"," SetSwiftMQWIFFtData if condition check 1");
					sFFTDetailList.append("<freeFormatTextDtl>");
					sFFTDetailList.append("<freeFormatTextCode>"); sFFTDetailList.append("45CDOCPRSDTL"); sFFTDetailList.append("</freeFormatTextCode>");
					sFFTDetailList.append("<freeFormatTextDescription>"); sFFTDetailList.append(DOC_PRESENINSTR_C); sFFTDetailList.append("</freeFormatTextDescription>");
					sFFTDetailList.append("</freeFormatTextDtl>");
					WriteToLog_showpage("Y"," SetSwiftMQWIFFtData created XML ");
				}else{
					WriteToLog_showpage("Y"," INSIDE SetSwiftMQWIFFtData check else 441");
					WriteToLog_showpage("Y"," SetSwiftMQWIFFtData if condition check 1");
					sFFTDetailList.append("<freeFormatTextDtl>");
					sFFTDetailList.append("<freeFormatTextCode>"); sFFTDetailList.append("45CDOCPRSDTL"); sFFTDetailList.append("</freeFormatTextCode>");
					sFFTDetailList.append("<freeFormatTextDescription>"); sFFTDetailList.append(""); sFFTDetailList.append("</freeFormatTextDescription>");
					sFFTDetailList.append("</freeFormatTextDtl>");
					WriteToLog_showpage("Y"," SetSwiftMQWIFFtData created XML ");
				}
				if(!sFFTDetailList.toString().equalsIgnoreCase("")){
					WriteToLog_showpage("Y"," SetSwiftMQWIFFtData if condition check 2"+sFFTDetailList.toString());
					return sFFTDetailList.toString();
				}
			}
			WriteToLog_showpage("Y"," END SetSwiftMQWIFFtData");
			return fftData;
		}

private boolean checkforDOC_PRESENINSTR_C_Case(String sWIName){
		String msgType = getSWIFTTXNDetails(sWIName,"MSG_TYPE");
		String purposeMsg_A = getSWIFTTXNDetails(sWIName,"PURPOSE_MESSAGE_A");
		String form_undertaking_b = getSWIFTTXNDetails(sWIName,"FORM_UNDERTAKING_B");
		String demand_type = getSWIFTTXNDetails(sWIName,"DEMAND_TYPE");
		
		WriteToLog_showpage("Y"," Values : "+msgType + " : "+purposeMsg_A + " : "+form_undertaking_b + " : "+demand_type + " : ");
		
		 if(msgType.equalsIgnoreCase("760")){
			if(purposeMsg_A.equalsIgnoreCase("ICCO") && form_undertaking_b.equalsIgnoreCase("DGAR") && demand_type.equalsIgnoreCase("NA")){
				return true;
			}else if(purposeMsg_A.equalsIgnoreCase("ISCO") && form_undertaking_b.equalsIgnoreCase("DGAR") && demand_type.equalsIgnoreCase("NA")){
				return true;
			}else if(purposeMsg_A.equalsIgnoreCase("ICCO") && form_undertaking_b.equalsIgnoreCase("STBY") &&  demand_type.equalsIgnoreCase("NA")){
				return true;
			}else if(purposeMsg_A.equalsIgnoreCase("ISCO") && form_undertaking_b.equalsIgnoreCase("STBY") &&  demand_type.equalsIgnoreCase("NA")){
				return true;
			}
		 }
		 return false;
}


private String fetchAdviceDetails(String sWorkItemNo,String requestType){
	    StringBuilder sFFTDetailList = new StringBuilder();
		XMLParser xmlDataParser1 = new XMLParser();
		XMLParser xmlDataParser2 = new XMLParser();
		String sFFTCode="";
		String sFFTDesc="";
		String sOutput1="";
		String partyID="";
		String partyType="";
		String data="";
	try{
		sQuery="select FFT_CODE,FFT_DESC from TFO_DJ_PROTRADE_FFT_DETAILS WHERE WINAME = '"+sWorkItemNo+"' and FFT_CODE is not null";
		sInput=prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
		WriteToLog_showpage("Y"," ****SUDH***********fetchBranchCode in EXT "+sInput+" ************SUDH*****************");
		sOutput1=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);	
        xmlDataParser1.setInputXML(sOutput1);
		int iDocCount = xmlDataParser1.getNoOfFields("Record");
		WriteToLog_showpage("Y"," ***************FetchFFTData iDocCount "+iDocCount+" *****************************");
		
		data=getPartyIdFromPartyType("'ABK','BEN'",sWorkItemNo,requestType);
		if(data.contains(",")){
			String[] dataArray=data.split(",");
			partyID=dataArray[0];
			partyType=dataArray[1];
		}
		if("NI".equalsIgnoreCase(requestType)){
			if(iDocCount>0){
				sFFTDetailList.append("<adviceDetail>");
				sFFTDetailList.append("<messageType>"); sFFTDetailList.append("GUARANTEE"); sFFTDetailList.append("</messageType>");
				sFFTDetailList.append("<messageParty>"); sFFTDetailList.append(partyType); sFFTDetailList.append("</messageParty>");
				sFFTDetailList.append("<messagePartyId>"); sFFTDetailList.append(partyID); sFFTDetailList.append("</messagePartyId>");
				sFFTDetailList.append("</adviceDetail>");
			}
			else{
				sFFTDetailList.append("<adviceDetail>");
				sFFTDetailList.append("<messageType>"); sFFTDetailList.append("GUARANTEE"); sFFTDetailList.append("</messageType>");
				sFFTDetailList.append("<messageParty>"); sFFTDetailList.append(partyType); sFFTDetailList.append("</messageParty>");
				sFFTDetailList.append("<messagePartyId>"); sFFTDetailList.append(partyID); sFFTDetailList.append("</messagePartyId>");
				sFFTDetailList.append("</adviceDetail>");
			}
		}else if("ELCB_BL".equalsIgnoreCase(requestType)||"EC_BL".equalsIgnoreCase(requestType)){
			if(iDocCount>0){
				partyID=getPartyIdFromPartyType("'DRAWER'",sWorkItemNo,requestType);

				sFFTDetailList.append("<adviseRequired>"); sFFTDetailList.append("ACKNOWLEDGEMENT"); sFFTDetailList.append("</adviseRequired>");
				sFFTDetailList.append("<adviceParty>"); sFFTDetailList.append("DRAWER"); sFFTDetailList.append("</adviceParty>");
				sFFTDetailList.append("<advicePartyId>"); sFFTDetailList.append(partyID); sFFTDetailList.append("</advicePartyId>");
			   
			}else{
				sFFTDetailList.append("<adviseRequired>"); sFFTDetailList.append(""); sFFTDetailList.append("</adviseRequired>");
				sFFTDetailList.append("<adviceParty>"); sFFTDetailList.append(""); sFFTDetailList.append("</adviceParty>");
				sFFTDetailList.append("<advicePartyId>"); sFFTDetailList.append(""); sFFTDetailList.append("</advicePartyId>");
			}
		}else if("SBLC_NI".equalsIgnoreCase(requestType)){
			WriteToLog_showpage("Y"," ***************FetchFFTData SBLC ===============");
			sFFTDetailList.append("<adviceDetail>");
			sFFTDetailList.append("<messageType>"); sFFTDetailList.append("GUARANTEE"); sFFTDetailList.append("</messageType>");
			sFFTDetailList.append("<messageParty>"); sFFTDetailList.append(partyType); sFFTDetailList.append("</messageParty>");
			sFFTDetailList.append("<messagePartyId>"); sFFTDetailList.append(partyID); sFFTDetailList.append("</messagePartyId>");
            sFFTDetailList.append("</adviceDetail>");
		}
		else if("GA".equalsIgnoreCase(requestType)){
			WriteToLog_showpage("Y"," ***************FetchFFTData GA ===============");
			sFFTDetailList.append("<adviceDetail>");
			sFFTDetailList.append("<messageType>"); sFFTDetailList.append("GUARANTEE"); sFFTDetailList.append("</messageType>");
			sFFTDetailList.append("<messageParty>"); sFFTDetailList.append(partyType); sFFTDetailList.append("</messageParty>");
			sFFTDetailList.append("<messagePartyId>"); sFFTDetailList.append(partyID); sFFTDetailList.append("</messagePartyId>");
            sFFTDetailList.append("</adviceDetail>");
		}
		else if("ELC_SLCA".equalsIgnoreCase(requestType)){
			WriteToLog_showpage("Y"," ***************FetchFFTData ELC_SLCA ===============");
			sFFTDetailList.append("<adviceDetail>");
			sFFTDetailList.append("<messageType>"); sFFTDetailList.append("GUARANTEE"); sFFTDetailList.append("</messageType>");
			sFFTDetailList.append("<messageParty>"); sFFTDetailList.append(partyType); sFFTDetailList.append("</messageParty>");
			sFFTDetailList.append("<messagePartyId>"); sFFTDetailList.append(partyID); sFFTDetailList.append("</messagePartyId>");
            sFFTDetailList.append("</adviceDetail>");
		}
	}catch (Exception e) {
      WriteToLog_showpage("Y"," Excpt fetchBranchCode " + e);
    }
	return sFFTDetailList.toString();
} 

private String fetchTermsDetails(String sWorkItemNo){
	    StringBuilder sFFTDetailList = new StringBuilder();
		XMLParser xmlDataParser1 = new XMLParser();
		XMLParser xmlDataParser2 = new XMLParser();
		String sType="";
		String sDesc="";
		String sOutput1="";
		
	try{
		sQuery="select TYPE,DESCRIPTION from TFO_DJ_TERMS_COND_DETAILS where  WINAME = '"+sWorkItemNo+"'";
		sInput=prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
		WriteToLog_showpage("Y"," ****SUDH***********fetchBranchCode in EXT "+sInput+" ************SUDH*****************");
		sOutput1=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);	
        xmlDataParser1.setInputXML(sOutput1);
		int iDocCount = xmlDataParser1.getNoOfFields("Record");
		WriteToLog_showpage("Y"," ***************FetchFFTData iDocCount "+iDocCount+" *****************************");
		for (int i = 0; i < iDocCount; ++i) {
			xmlDataParser2.setInputXML(xmlDataParser1.getNextValueOf("Record"));
			sType = xmlDataParser2.getValueOf("TYPE");
			sDesc  = xmlDataParser2.getValueOf("DESCRIPTION");
			
			if("Guarantee".equalsIgnoreCase(sType)){
				sType="U";
			}else if("Local Guarantee".equalsIgnoreCase(sType)){
				sType="L";
			}else {
			   sType ="";	
			}
			sFFTDetailList.append("<termsAndCondDtl>");
			sFFTDetailList.append("<type>"); sFFTDetailList.append(sType); sFFTDetailList.append("</type>");
			sFFTDetailList.append("<description>"); sFFTDetailList.append(sDesc); sFFTDetailList.append("</description>");
            sFFTDetailList.append("</termsAndCondDtl>");
		}
		
	}catch (Exception e) {
      WriteToLog_showpage("Y"," Excpt fetchBranchCode " + e);
    }
	return sFFTDetailList.toString();
} 

private String getDocumentDetailsforCreateAmend_elcb_bl(String sWINumber) 
{
    StringBuilder sbDocAddList = new StringBuilder();
    try {
		String documentCode = "";
		String documentDescription = "";
		String documentCopy  = "";
		
		sQuery = "SELECT DOCUMENT_TYPE,DOCUMENT_DESCRIPTION,DOCUMENT_COPIES FROM TFO_DJ_PROTRADE_DOCUMENT_DETAIL WHERE WINAME = '" +sWINumber+ "'";
		
		sInput = prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
		WriteToLog_showpage("Y"," ***************getDocumentDetailsforCreateAmend Input "+sInput+" *****************************");
		sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);;
		WriteToLog_showpage("Y"," ***************getDocumentDetailsforCreateAmend sOutput "+sOutput+" *****************************");
		
		XMLParser xmlDataParser1 = new XMLParser();
		XMLParser xmlDataParser2 = new XMLParser();
		xmlDataParser1.setInputXML(sOutput);
		int iDocCount = xmlDataParser1.getNoOfFields("Record");
		WriteToLog_showpage("Y"," ***************getDocumentDetailsforCreateAmend iDocCount "+iDocCount+" *****************************");
		
		
		for (int i = 0; i < iDocCount; ++i) {
			xmlDataParser2.setInputXML(xmlDataParser1.getNextValueOf("Record"));
			WriteToLog_showpage("Y","Document Type " + xmlDataParser2.getValueOf("DOCUMENT_TYPE").trim());
			documentCode = xmlDataParser2.getValueOf("DOCUMENT_TYPE");
			documentDescription = xmlDataParser2.getValueOf("DOCUMENT_DESCRIPTION");
			documentCopy  = xmlDataParser2.getValueOf("DOCUMENT_COPIES");
			
			sbDocAddList.append("<documentDetail>");
			sbDocAddList.append("<documentCode>"); sbDocAddList.append(documentCode); sbDocAddList.append("</documentCode>");
			sbDocAddList.append("<documentDescription>"); sbDocAddList.append(documentDescription); sbDocAddList.append("</documentDescription>");
			sbDocAddList.append("<documentCopy>"); sbDocAddList.append(documentCopy); sbDocAddList.append("</documentCopy>");
			
			sbDocAddList.append("</documentDetail>");
		}
		
		
	}catch (Exception e) {
      WriteToLog_showpage("Y"," Excpt getDocumentDetailsforCreateAmend_elcb_bl " + e);
    }
	WriteToLog_showpage("Y"," ***************getDocumentDetailsforCreateAmend_elcb_bl sbDocAddList "+sbDocAddList.toString()+" *****************************");
    return sbDocAddList.toString();
}

private String getDraftDetailsforCreateAmend(String sWINumber) 
{
    StringBuilder sbDraftAddList = new StringBuilder();
    try {
		String draftSno = "";
		String draftTenor = "";
		String draftCreditDaysFrom = "";
		String draftDate  = "";
		String draftDraweeBank = "";
		String draftAmount = "";
		String sPercentageAmount = "";
		String sDraftSpecifyOthers = "";
		
		sQuery = "SELECT ROWNUM,DRAFT_NO,DRAFT_TENOR,DRAFT_CREDIT_DAYS_FROM,TO_CHAR(DRAFT_CREDIT_DAYS_DATE,'DD/MM/YYYY') DRAFT_CREDIT_DAYS_DATE,DRAFT_DRAWEE_BANK,DRAFT_AMOUNT FROM TFO_DJ_DRAFT_DETAILS WHERE WINAME = '" +sWINumber+ "'";
		
		sInput = prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
		WriteToLog_showpage("Y"," ***************getDraftDetailsforCreateAmend Input "+sInput+" *****************************");
		sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);;
		WriteToLog_showpage("Y"," ***************getDraftDetailsforCreateAmend sOutput "+sOutput+" *****************************");
		
		
		sQuery1 = "SELECT DRAFT_SPECIFY_OTHERS FROM EXT_TFO WHERE WI_NAME = '"+sWINumber+"'";
		sInput1 = prepareAPSelectInputXml(sQuery1,sCabname,sSessionId);
		WriteToLog_showpage("Y"," ***************DRAFT_SPECIFY_OTHERS"+sInput1+" *****************************");
		sOutput1 = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput1);;
		WriteToLog_showpage("Y"," ***************DRAFT_SPECIFY_OTHERS = "+sOutput1+"  *****************************");
		sDraftSpecifyOthers = sOutput1.substring(sOutput1.indexOf("<DRAFT_SPECIFY_OTHERS>")+"<DRAFT_SPECIFY_OTHERS>".length(),sOutput1.indexOf("</DRAFT_SPECIFY_OTHERS>"));
		WriteToLog_showpage("Y"," ***************kdd = "+sDraftSpecifyOthers+"  *****************************");

				

		
		
		XMLParser xmlDataParser1 = new XMLParser();
		XMLParser xmlDataParser2 = new XMLParser();
		xmlDataParser1.setInputXML(sOutput);
		int iDraftCount = xmlDataParser1.getNoOfFields("Record");
		WriteToLog_showpage("Y"," ***************getDraftDetailsforCreateAmend iDraftCount "+iDraftCount+" *****************************");
		
		for (int i = 0; i < iDraftCount; ++i) {
			xmlDataParser2.setInputXML(xmlDataParser1.getNextValueOf("Record"));
			WriteToLog_showpage("Y","Draft Tenor " + xmlDataParser2.getValueOf("DRAFT_TENOR").trim());
			draftSno = xmlDataParser2.getValueOf("ROWNUM");
			draftTenor = xmlDataParser2.getValueOf("DRAFT_TENOR");
			draftCreditDaysFrom = xmlDataParser2.getValueOf("DRAFT_CREDIT_DAYS_FROM");
			draftDate  = xmlDataParser2.getValueOf("DRAFT_CREDIT_DAYS_DATE");
			draftDraweeBank  = xmlDataParser2.getValueOf("DRAFT_DRAWEE_BANK");
			draftAmount  = xmlDataParser2.getValueOf("DRAFT_AMOUNT");
			
						if("Others".equalsIgnoreCase(draftCreditDaysFrom)){
							draftCreditDaysFrom = sDraftSpecifyOthers;
						}else if("".equalsIgnoreCase(draftCreditDaysFrom) && "".equalsIgnoreCase(sDraftSpecifyOthers)){
							draftCreditDaysFrom = "";
						}
						WriteToLog_showpage("Y"," ***************draftCreditDaysFrom: "+draftCreditDaysFrom+" *****************************");
										
						if(!"".equalsIgnoreCase(draftAmount)){
							sPercentageAmount = "A";
						}else{
							sPercentageAmount = "";
						}
						WriteToLog_showpage("Y"," ***************sPercentageAmount: "+sPercentageAmount+" *****************************");
			
			/*if(!"".equalsIgnoreCase(draftTenor) || !"".equalsIgnoreCase(draftCreditDaysFrom) || !"".equalsIgnoreCase(draftDraweeBank) ||!"".equalsIgnoreCase(draftAmount)){
				draftSno = "1";
			}
			*/
						
			sbDraftAddList.append("<draft>");
			sbDraftAddList.append("<draftSerialNumber>"); sbDraftAddList.append(draftSno); sbDraftAddList.append("</draftSerialNumber>");
			sbDraftAddList.append("<draftTenor>"); sbDraftAddList.append(draftTenor); sbDraftAddList.append("</draftTenor>");
			sbDraftAddList.append("<creditDaysFrom>"); sbDraftAddList.append(draftCreditDaysFrom); sbDraftAddList.append("</creditDaysFrom>");
			sbDraftAddList.append("<draftDate>"); sbDraftAddList.append(draftDate); sbDraftAddList.append("</draftDate>");
			sbDraftAddList.append("<drawee>"); sbDraftAddList.append(draftDraweeBank); sbDraftAddList.append("</drawee>");
			sbDraftAddList.append("<draftAmount>"); sbDraftAddList.append(draftAmount); sbDraftAddList.append("</draftAmount>");
			sbDraftAddList.append("<percentageAmount>"); sbDraftAddList.append(sPercentageAmount); sbDraftAddList.append("</percentageAmount>");
			sbDraftAddList.append("<draftAmount>"); sbDraftAddList.append(draftAmount); sbDraftAddList.append("</draftAmount>");
			sbDraftAddList.append("</draft>");
		}
	}catch (Exception e) {
      WriteToLog_showpage("Y"," Excpt getDraftDetailsforCreateAmend " + e);
    }
	WriteToLog_showpage("Y"," ***************getDraftDetailsforCreateAmend sbDraftAddList "+sbDraftAddList.toString()+" *****************************");
    return sbDraftAddList.toString();
}


%>
