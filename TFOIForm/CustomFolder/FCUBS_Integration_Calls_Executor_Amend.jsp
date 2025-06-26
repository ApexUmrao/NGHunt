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
String sBillStage = "";

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
String sOperationCode = "";
String sAmendType = "";
String sNewTransactionAmount = "";
String sProductCode = "";
String sNewExpiryDate = "";
String sRequestCategoryCode = "";
String sRequestTypeCode = "";
String sRelationshipType = "";
String limitSerialNumber = "";
String limitPartyType = "";
String limitLinkageType = "";
String limitPercentContribution = "";
String limitAmountTag = "";
String limitLineRefNumber = "";
String customerType = "";
limitLineCustomerId = "";
String CurrDate = "";
String sBranchCreateCode = "";
String sCurrentDateValue= "";
String sMaturityDateValue="";
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

String sSwiftProcStatus = "";

try
{   //socket = ConnectSocket.getReference("10.101.109.182", 4444);	
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
	    WriteToLog_showpage("Y","amendment jsp starts");
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
			sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
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
					sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
					REFNO = sOutput.substring(sOutput.indexOf("<REFNO>")+"<REFNO>".length(),sOutput.indexOf("</REFNO>"));							
					/*sQuery = "SELECT ISSUING_BANK_LC_NO,OPERATION_CODE,TRANSACTION_CURRENCY,TRANSACTION_AMOUNT,LC_UNDER,LC_ABOVE,TO_CHAR(TO_TIMESTAMP(EXP_DATE,'YYYY-MM-DD HH24:MI:SS.FF3'),'DD/MM/YYYY') EXP_DATE,CUSTOMER_ID,PRODUCT_TYPE,LC_FROM_PLACE,LC_TO_PLACE ,TO_CHAR(TO_TIMESTAMP(LC_LTST_SHIPMNT_DATE,'YYYY-MM-DD HH24:MI:SS.FF3'),'DD/MM/YYYY') LC_LTST_SHIPMNT_DATE,LC_PORT_OF_DISCHRG,LC_PORT_OF_LOADING,replace(replace(LC_GOODS_DESC,'<![CDATA[',''),']]>','') as LC_GOODS_DESC,BRANCH_CITY,REQUEST_CATEGORY, REQUEST_TYPE,RELATIONSHIP_TYPE,PRO_TRADE_REF_NO,TRN_THIRD_PARTY ,TO_CHAR(TO_TIMESTAMP(GRNTEE_CNTR_EXP_DATE,'YYYY-MM-DD HH24:MI:SS.FF3'),'DD/MM/YYYY')  GRNTEE_CNTR_EXP_DATE,TRANSACTION_ADCB_LC_REFERENCE,TO_CHAR(TO_TIMESTAMP(INF_MATURITY_DATE,'YYYY-MM-DD HH24:MI:SS.FF3'),'DD/MM/YYYY') INF_MATURITY_DATE,INF_TENOR_DAYS,TO_CHAR(TO_TIMESTAMP(INF_BASE_DOC_DATE,'YYYY-MM-DD HH24:MI:SS.FF3'),'DD/MM/YYYY') INF_BASE_DOC_DATE,INF_TENOR_BASE,INF_ACTUAL_TENOR_BASE,DISCREPANCY_DTLS,LC_LIMIT_LINE,LIMIT_PARTY_TYPE,BILL_PRODUCT_CODE,NEW_TRN_AMT, AMEND_TYPE,TO_CHAR(TO_TIMESTAMP(NEW_EXP_DATE,'YYYY-MM-DD HH24:MI:SS.FF3'),'DD/MM/YYYY') NEW_EXP_DATE,TRANSACTION_REFERENCE,SWIFT_PROCESSING_STATUS,SWIFT_MESSAGE_TYPE,BILL_STAGE ,PRO_TRADE_SETTLEMENT_INST,TO_CHAR(ACCEPTANCE_MSG_DATE,'DD/MM/YYYY') AS ACCEPTANCE_MSG_DATE FROM EXT_TFO WHERE WI_NAME = '"+ sWIName +"'";
					*/
					sQuery = "SELECT ISSUING_BANK_LC_NO,OPERATION_CODE,TRANSACTION_CURRENCY,TRANSACTION_AMOUNT,LC_UNDER,LC_ABOVE,TO_CHAR(TO_TIMESTAMP(EXP_DATE,'YYYY-MM-DD HH24:MI:SS.FF3'),'DD/MM/YYYY') EXP_DATE,CUSTOMER_ID,PRODUCT_TYPE,LC_FROM_PLACE,LC_TO_PLACE ,TO_CHAR(TO_TIMESTAMP(LC_LTST_SHIPMNT_DATE,'YYYY-MM-DD HH24:MI:SS.FF3'),'DD/MM/YYYY') LC_LTST_SHIPMNT_DATE,LC_PORT_OF_DISCHRG,LC_PORT_OF_LOADING,replace(replace(LC_GOODS_DESC,'<![CDATA[',''),']]>','') as LC_GOODS_DESC,BRANCH_CITY,REQUEST_CATEGORY, REQUEST_TYPE,RELATIONSHIP_TYPE,PRO_TRADE_REF_NO,TRN_THIRD_PARTY ,TO_CHAR(TO_TIMESTAMP(GRNTEE_CNTR_EXP_DATE,'YYYY-MM-DD HH24:MI:SS.FF3'),'DD/MM/YYYY')  GRNTEE_CNTR_EXP_DATE,TRANSACTION_ADCB_LC_REFERENCE,TO_CHAR(TO_TIMESTAMP(INF_MATURITY_DATE,'YYYY-MM-DD HH24:MI:SS.FF3'),'DD/MM/YYYY') INF_MATURITY_DATE,INF_TENOR_DAYS,TO_CHAR(TO_TIMESTAMP(INF_BASE_DOC_DATE,'YYYY-MM-DD HH24:MI:SS.FF3'),'DD/MM/YYYY') INF_BASE_DOC_DATE,INF_TENOR_BASE,INF_ACTUAL_TENOR_BASE,DISCREPANCY_DTLS,LC_LIMIT_LINE,LIMIT_PARTY_TYPE,BILL_PRODUCT_CODE,NEW_TRN_AMT, AMEND_TYPE,TO_CHAR(TO_TIMESTAMP(NEW_EXP_DATE,'YYYY-MM-DD HH24:MI:SS.FF3'),'DD/MM/YYYY') NEW_EXP_DATE,TRANSACTION_REFERENCE,SWIFT_PROCESSING_STATUS,SWIFT_MESSAGE_TYPE,BILL_STAGE ,PRO_TRADE_SETTLEMENT_INST,TO_CHAR(ACCEPTANCE_MSG_DATE,'DD/MM/YYYY') AS ACCEPTANCE_MSG_DATE  FROM EXT_TFO WHERE WI_NAME = '"+ sWIName +"'";
					sInput = prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
					WriteToLog_showpage("Y"," ***************Ref number Input "+sInput+" *****************************");
					sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
					WriteToLog_showpage("Y"," ***************Ref number output = "+sOutput+"  *****************************");
					try
					{					
						sRequestCategoryCode = sOutput.substring(sOutput.indexOf("<REQUEST_CATEGORY>")+"<REQUEST_CATEGORY>".length(),sOutput.indexOf("</REQUEST_CATEGORY>"));
						sRequestTypeCode = sOutput.substring(sOutput.indexOf("<REQUEST_TYPE>")+"<REQUEST_TYPE>".length(),sOutput.indexOf("</REQUEST_TYPE>"));
						String messageType=sOutput.substring(sOutput.indexOf("<SWIFT_MESSAGE_TYPE>")+"<SWIFT_MESSAGE_TYPE>".length(),sOutput.indexOf("</SWIFT_MESSAGE_TYPE>"));
						String processingStatus=sOutput.substring(sOutput.indexOf("<SWIFT_PROCESSING_STATUS>")+"<SWIFT_PROCESSING_STATUS>".length(),sOutput.indexOf("</SWIFT_PROCESSING_STATUS>"));
						if("ELC".equalsIgnoreCase(sRequestCategoryCode) && "ELC_LCAA".equalsIgnoreCase(sRequestTypeCode))
						{						
							
						   sOperationCode = sOutput.substring(sOutput.indexOf("<OPERATION_CODE>")+"<OPERATION_CODE>".length(),sOutput.indexOf("</OPERATION_CODE>"));
							//sContractAmount = sOutput.substring(sOutput.indexOf("<TRANSACTION_AMOUNT>")+"<TRANSACTION_AMOUNT>".length(),sOutput.indexOf("</TRANSACTION_AMOUNT>"));
							sAmendType = sOutput.substring(sOutput.indexOf("<AMEND_TYPE>")+"<AMEND_TYPE>".length(),sOutput.indexOf("</AMEND_TYPE>"));
							//sNewTransactionAmount = sOutput.substring(sOutput.indexOf("<NEW_TRN_AMT>")+"<NEW_TRN_AMT>".length(),sOutput.indexOf("</NEW_TRN_AMT>"));
							//sNegativeTolerance = sOutput.substring(sOutput.indexOf("<LC_UNDER>")+"<LC_UNDER>".length(),sOutput.indexOf("</LC_UNDER>"));
							//sPositiveTolerance = sOutput.substring(sOutput.indexOf("<LC_ABOVE>")+"<LC_ABOVE>".length(),sOutput.indexOf("</LC_ABOVE>"));
							//sExpiryDate = sOutput.substring(sOutput.indexOf("<EXP_DATE>")+"<EXP_DATE>".length(),sOutput.indexOf("</EXP_DATE>"));
							//sNewExpiryDate = sOutput.substring(sOutput.indexOf("<NEW_EXP_DATE>")+"<NEW_EXP_DATE>".length(),sOutput.indexOf("</NEW_EXP_DATE>"));

							sContractRefNumber = sOutput.substring(sOutput.indexOf("<TRANSACTION_REFERENCE>")+"<TRANSACTION_REFERENCE>".length(),sOutput.indexOf("</TRANSACTION_REFERENCE>"));
							sProductCode = sOutput.substring(sOutput.indexOf("<PRODUCT_TYPE>")+"<PRODUCT_TYPE>".length(),sOutput.indexOf("</PRODUCT_TYPE>"));
							//sFromPlace = sOutput.substring(sOutput.indexOf("<LC_FROM_PLACE>")+"<LC_FROM_PLACE>".length(),sOutput.indexOf("</LC_FROM_PLACE>"));
							//sToPlace = sOutput.substring(sOutput.indexOf("<LC_TO_PLACE>")+"<LC_TO_PLACE>".length(),sOutput.indexOf("</LC_TO_PLACE>"));
							//sLastShipmentDate = sOutput.substring(sOutput.indexOf("<LC_LTST_SHIPMNT_DATE>")+"<LC_LTST_SHIPMNT_DATE>".length(),sOutput.indexOf("</LC_LTST_SHIPMNT_DATE>"));
							//sPortOfDischarge = sOutput.substring(sOutput.indexOf("<LC_PORT_OF_DISCHRG>")+"<LC_PORT_OF_DISCHRG>".length(),sOutput.indexOf("</LC_PORT_OF_DISCHRG>"));
							//sPortOfLoading = sOutput.substring(sOutput.indexOf("<LC_PORT_OF_LOADING>")+"<LC_PORT_OF_LOADING>".length(),sOutput.indexOf("</LC_PORT_OF_LOADING>"));
							//sGoodsDescription = sOutput.substring(sOutput.indexOf("<LC_GOODS_DESC>")+"<LC_GOODS_DESC>".length(),sOutput.indexOf("</LC_GOODS_DESC>"));
							
							
							if("ELC_IV".equalsIgnoreCase(sAmendType)||"ELC_DV".equalsIgnoreCase(sAmendType)||"ELC_CT".equalsIgnoreCase(sAmendType)){
							  sContractAmount=sNewTransactionAmount;
							  }
							  if("ELC_EED".equalsIgnoreCase(sAmendType)||"ELC_CED".equalsIgnoreCase(sAmendType)||"ELC_CT".equalsIgnoreCase(sAmendType)){
							   sExpiryDate=sNewExpiryDate;
							   }
							 sRelationshipType = sOutput.substring(sOutput.indexOf("<RELATIONSHIP_TYPE>")+"<RELATIONSHIP_TYPE>".length(),sOutput.indexOf("</RELATIONSHIP_TYPE>"));  
							sBranchCreateCode = fetchBranchCode(sRelationshipType , sWIName);	
							
							fetchAmendmentTabData(sWIName);
							
							if(sCallName.equalsIgnoreCase("modTradeLCContract_Oper")){							
								//callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeLCContract_Oper</operationType><correlationID>342343</correlationID><modTradeLCContractReq><requestType>A</requestType><operationCode>" + sOperationCode + "</operationCode><contractAmount>" + sContractAmount + "</contractAmount><negativeTolerance>" + sNegativeTolerance + "</negativeTolerance><positiveTolerance>" + sPositiveTolerance + "</positiveTolerance><expiryDate>" + sExpiryDate + "</expiryDate><productCode>" + sProductCode + "</productCode><contractRefNumber>" + sContractRefNumber + "</contractRefNumber><partyDetails><partyDetail><partyType>BEN</partyType></partyDetail></partyDetails><shipmentDetails><fromPlace>" + sFromPlace + "</fromPlace><toPlace>" + sToPlace + "</toPlace><lastShipmentDate>" + sLastShipmentDate + "</lastShipmentDate><portOfDischarge>" + sPortOfDischarge + "</portOfDischarge><portOfLoading>" + sPortOfLoading + "</portOfLoading></shipmentDetails><goodsDetails><goodsDescription>" + sGoodsDescription + "</goodsDescription></goodsDetails><branchCode>"+sBranchCreateCode+"</branchCode><wmsDetails><workItemNumber>" + sWIName + "</workItemNumber></wmsDetails></modTradeLCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";
								
								 callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeLCContract_Oper</operationType><correlationID>342343</correlationID><modTradeLCContractReq><requestType>A</requestType><operationCode>" + sOperationCode + "</operationCode><contractAmount>" + sContractAmount + "</contractAmount><negativeTolerance>" + sNegativeTolerance + "</negativeTolerance><positiveTolerance>" + sPositiveTolerance + "</positiveTolerance><expiryDate>" + sExpiryDate + "</expiryDate><productCode>" + sProductCode + "</productCode><contractRefNumber>" + sContractRefNumber + "</contractRefNumber><partyDetails><partyDetail><partyType>BEN</partyType></partyDetail></partyDetails><shipmentDetails><fromPlace>" + sFromPlace + "</fromPlace><toPlace>" + sToPlace + "</toPlace><lastShipmentDate>" + sLastShipmentDate + "</lastShipmentDate><portOfDischarge>" + sPortOfDischarge + "</portOfDischarge><portOfLoading>" + sPortOfLoading + "</portOfLoading><partialShipmentAllowed>" + sPartialShipmentAllowed + "</partialShipmentAllowed><transshipmentAllowed>" + sTransShipmentAllowed + "</transshipmentAllowed></shipmentDetails><goodsDetails><goodsDescription>" + sGoodsDescription + "</goodsDescription></goodsDetails><branchCode>"+sBranchCreateCode+"</branchCode><wmsDetails><workItemNumber>" + sWIName + "</workItemNumber></wmsDetails><placeOfExpiry>" + sExpiryPlace + "</placeOfExpiry><settlementMonth>" + sCreditMode + "</settlementMonth><paymentDate>" + sCreditDetails + "</paymentDate><mayConfirm>" + sMayConfirmFlag + "</mayConfirm><requestedConfirmationParty>" + sRequestedConfirmationParty + "</requestedConfirmationParty><chargesFromBenef>" + sChargesFromBeneficiary + "</chargesFromBenef><presentationPeriod>" + sPeriodOfPresentationDays + "</presentationPeriod><presentationNarrative>" + sPeriod_Of_Presentation_Mode + "</presentationNarrative></modTradeLCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";
								 

								WriteToLog_showpage("Y","modTradeLCContract_Oper sOutput: "+ callXML);
								sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sCallName +"',"+ convertToPlainString(callXML) +",'"+ REFNO +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","modTradeLCContract_Oper callXML TFO_DJ_INTEGRATION_CALLS_DTLS: "+ sQuery + " *****************************");	
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);						
								WriteToLog_showpage("Y"," insert modTradeLCContract_Oper sOutput: "+ sOutput);											
								
								sOutput = socket.connectToSocket(callXML);
								WriteToLog_showpage("Y","modTradeLCContract_Oper callXML sOutput"+ sOutput);
								
								String queryUpdateXML = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'" + sOutput + "'","WI_NAME = '"+ sWIName +"'  and REF_NUM = '"+REFNO+"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update sOutputUpdateXML input: "+ queryUpdateXML);
								String sOutputUpdateXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",queryUpdateXML);
								WriteToLog_showpage("Y","update sOutputUpdateXML output: " + sOutputUpdateXML);
								
								xp = new XMLParser(sOutput);
								returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
								String nextValue ="";
								if(returnCode == 0){
									sErrorDescription = xp.getValueOf("errorDescription");
									sResponse = "Success";
									sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
									WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
									sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
									WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
									out.println(returnCode+"#"+sResponse+"#"+sErrorDescription);
								} else {
									sErrorDescription = xp.getValueOf("errorDescription");
									sResponse = "Failure";
									sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
									WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
									sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
									WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
									out.println("1#"+sResponse+"#"+sErrorDescription);
								}
							}
						}
						else if("ELC".equalsIgnoreCase(sRequestCategoryCode) && "ELC_LCC".equalsIgnoreCase(sRequestTypeCode))
						{						
							
						   sOperationCode = sOutput.substring(sOutput.indexOf("<OPERATION_CODE>")+"<OPERATION_CODE>".length(),sOutput.indexOf("</OPERATION_CODE>"));
							//sContractAmount = sOutput.substring(sOutput.indexOf("<TRANSACTION_AMOUNT>")+"<TRANSACTION_AMOUNT>".length(),sOutput.indexOf("</TRANSACTION_AMOUNT>"));
							sAmendType = sOutput.substring(sOutput.indexOf("<AMEND_TYPE>")+"<AMEND_TYPE>".length(),sOutput.indexOf("</AMEND_TYPE>"));
							//sNegativeTolerance = sOutput.substring(sOutput.indexOf("<LC_UNDER>")+"<LC_UNDER>".length(),sOutput.indexOf("</LC_UNDER>"));
							//sPositiveTolerance = sOutput.substring(sOutput.indexOf("<LC_ABOVE>")+"<LC_ABOVE>".length(),sOutput.indexOf("</LC_ABOVE>"));
							//sExpiryDate = sOutput.substring(sOutput.indexOf("<EXP_DATE>")+"<EXP_DATE>".length(),sOutput.indexOf("</EXP_DATE>"));

							sContractRefNumber = sOutput.substring(sOutput.indexOf("<TRANSACTION_REFERENCE>")+"<TRANSACTION_REFERENCE>".length(),sOutput.indexOf("</TRANSACTION_REFERENCE>"));
							sProductCode = sOutput.substring(sOutput.indexOf("<PRODUCT_TYPE>")+"<PRODUCT_TYPE>".length(),sOutput.indexOf("</PRODUCT_TYPE>"));
							//sFromPlace = sOutput.substring(sOutput.indexOf("<LC_FROM_PLACE>")+"<LC_FROM_PLACE>".length(),sOutput.indexOf("</LC_FROM_PLACE>"));
							//sToPlace = sOutput.substring(sOutput.indexOf("<LC_TO_PLACE>")+"<LC_TO_PLACE>".length(),sOutput.indexOf("</LC_TO_PLACE>"));
							//sLastShipmentDate = sOutput.substring(sOutput.indexOf("<LC_LTST_SHIPMNT_DATE>")+"<LC_LTST_SHIPMNT_DATE>".length(),sOutput.indexOf("</LC_LTST_SHIPMNT_DATE>"));
							//sPortOfDischarge = sOutput.substring(sOutput.indexOf("<LC_PORT_OF_DISCHRG>")+"<LC_PORT_OF_DISCHRG>".length(),sOutput.indexOf("</LC_PORT_OF_DISCHRG>"));
							//sPortOfLoading = sOutput.substring(sOutput.indexOf("<LC_PORT_OF_LOADING>")+"<LC_PORT_OF_LOADING>".length(),sOutput.indexOf("</LC_PORT_OF_LOADING>"));
							//sGoodsDescription = sOutput.substring(sOutput.indexOf("<LC_GOODS_DESC>")+"<LC_GOODS_DESC>".length(),sOutput.indexOf("</LC_GOODS_DESC>"));
							
							sRelationshipType = sOutput.substring(sOutput.indexOf("<RELATIONSHIP_TYPE>")+"<RELATIONSHIP_TYPE>".length(),sOutput.indexOf("</RELATIONSHIP_TYPE>"));
							sBranchCreateCode = fetchBranchCode(sRelationshipType , sWIName);
							
							fetchAmendmentTabData(sWIName);
							
							
							
							if(sCallName.equalsIgnoreCase("modTradeLCContract_Oper")){							
								//callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeLCContract_Oper</operationType><correlationID>342343</correlationID><modTradeLCContractReq><requestType>A</requestType><operationCode>" + sOperationCode + "</operationCode><contractAmount>" + sContractAmount + "</contractAmount><negativeTolerance>" + sNegativeTolerance + "</negativeTolerance><positiveTolerance>" + sPositiveTolerance + "</positiveTolerance><expiryDate>" + sExpiryDate + "</expiryDate><productCode>" + sProductCode + "</productCode><contractRefNumber>" + sContractRefNumber + "</contractRefNumber><partyDetails><partyDetail><partyType>BEN</partyType></partyDetail></partyDetails><shipmentDetails><fromPlace>" + sFromPlace + "</fromPlace><toPlace>" + sToPlace + "</toPlace><lastShipmentDate>" + sLastShipmentDate + "</lastShipmentDate><portOfDischarge>" + sPortOfDischarge + "</portOfDischarge><portOfLoading>" + sPortOfLoading + "</portOfLoading></shipmentDetails><goodsDetails><goodsDescription>" + sGoodsDescription + "</goodsDescription></goodsDetails><branchCode>"+sBranchCreateCode+"</branchCode><wmsDetails><workItemNumber>" + sWIName + "</workItemNumber></wmsDetails></modTradeLCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";
								  callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeLCContract_Oper</operationType><correlationID>342343</correlationID><modTradeLCContractReq><requestType>A</requestType><operationCode>" + sOperationCode + "</operationCode><contractAmount>" + sContractAmount + "</contractAmount><negativeTolerance>" + sNegativeTolerance + "</negativeTolerance><positiveTolerance>" + sPositiveTolerance + "</positiveTolerance><expiryDate>" + sExpiryDate + "</expiryDate><productCode>" + sProductCode + "</productCode><contractRefNumber>" + sContractRefNumber + "</contractRefNumber><partyDetails><partyDetail><partyType>BEN</partyType></partyDetail></partyDetails><shipmentDetails><fromPlace>" + sFromPlace + "</fromPlace><toPlace>" + sToPlace + "</toPlace><lastShipmentDate>" + sLastShipmentDate + "</lastShipmentDate><portOfDischarge>" + sPortOfDischarge + "</portOfDischarge><portOfLoading>" + sPortOfLoading + "</portOfLoading><partialShipmentAllowed>" + sPartialShipmentAllowed + "</partialShipmentAllowed><transshipmentAllowed>" + sTransShipmentAllowed + "</transshipmentAllowed></shipmentDetails><goodsDetails><goodsDescription>" + sGoodsDescription + "</goodsDescription></goodsDetails><branchCode>"+sBranchCreateCode+"</branchCode><wmsDetails><workItemNumber>" + sWIName + "</workItemNumber></wmsDetails><placeOfExpiry>" + sExpiryPlace + "</placeOfExpiry><settlementMonth>" + sCreditMode + "</settlementMonth><paymentDate>" + sCreditDetails + "</paymentDate><mayConfirm>" + sMayConfirmFlag + "</mayConfirm><requestedConfirmationParty>" + sRequestedConfirmationParty + "</requestedConfirmationParty><chargesFromBenef>" + sChargesFromBeneficiary + "</chargesFromBenef><presentationPeriod>" + sPeriodOfPresentationDays + "</presentationPeriod><presentationNarrative>" + sPeriod_Of_Presentation_Mode + "</presentationNarrative></modTradeLCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";

								WriteToLog_showpage("Y","modTradeLCContract_Oper sOutput: "+ callXML);
								sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sCallName +"',"+ convertToPlainString(callXML) +",'"+ REFNO +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","modTradeLCContract_Oper callXML TFO_DJ_INTEGRATION_CALLS_DTLS: "+ sQuery + " *****************************");	
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);						
								WriteToLog_showpage("Y"," insert modTradeLCContract_Oper sOutput: "+ sOutput);											
								
								sOutput = socket.connectToSocket(callXML);
								WriteToLog_showpage("Y","modTradeLCContract_Oper callXML sOutput"+ sOutput);
								String queryUpdateXML = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'" + sOutput + "'","WI_NAME = '"+ sWIName +"'  and REF_NUM = '"+REFNO+"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update sOutputUpdateXML input: "+ queryUpdateXML);
								String sOutputUpdateXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",queryUpdateXML);
								WriteToLog_showpage("Y","update sOutputUpdateXML output: " + sOutputUpdateXML);
								xp = new XMLParser(sOutput);
								returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
								String nextValue ="";
								if(returnCode == 0){
									sErrorDescription = xp.getValueOf("errorDescription");
									sResponse = "Success";
									sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
									WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
									sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
									WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
									out.println(returnCode+"#"+sResponse+"#"+sErrorDescription);
								} else {
									sErrorDescription = xp.getValueOf("errorDescription");
									sResponse = "Failure";
									sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
									WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
									sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
									WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
									out.println("1#"+sResponse+"#"+sErrorDescription);
								}
							}
						}
						else if("ELC".equalsIgnoreCase(sRequestCategoryCode) && "ELC_LCA".equalsIgnoreCase(sRequestTypeCode)
							&&(("700".equalsIgnoreCase(messageType))||("710".equalsIgnoreCase(messageType)))&& "P".equalsIgnoreCase(processingStatus))
						{						
							
							sContractRefNumber = sOutput.substring(sOutput.indexOf("<TRANSACTION_REFERENCE>")+"<TRANSACTION_REFERENCE>".length(),sOutput.indexOf("</TRANSACTION_REFERENCE>"));
							
							sBranchCreateCode = fetchBranchCode(sRelationshipType , sWIName);
							
							if(sCallName.equalsIgnoreCase("modTradeContractStatus_Oper")){							
								callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeContractStatus_Oper</operationType><correlationID>342343</correlationID><modTradeContractStatusReq><branchCode>" + sBranchCreateCode + "</branchCode><contractRefNumber>" + sContractRefNumber + "</contractRefNumber><workItemNumber>" + sWIName + "</workItemNumber></modTradeContractStatusReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";

								WriteToLog_showpage("Y","modTradeLCContract_Oper sOutput: "+ callXML);
								sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sCallName +"',"+ convertToPlainString(callXML) +",'"+ REFNO +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","modTradeLCContract_Oper callXML TFO_DJ_INTEGRATION_CALLS_DTLS: "+ sQuery + " *****************************");	
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);						
								WriteToLog_showpage("Y"," insert modTradeLCContract_Oper sOutput: "+ sOutput);											
								
								sOutput = socket.connectToSocket(callXML);
								WriteToLog_showpage("Y","modTradeLCContract_Oper callXML sOutput"+ sOutput);
								String queryUpdateXML = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'" + sOutput + "'","WI_NAME = '"+ sWIName +"'  and REF_NUM = '"+REFNO+"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update sOutputUpdateXML input: "+ queryUpdateXML);
								String sOutputUpdateXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",queryUpdateXML);
								WriteToLog_showpage("Y","update sOutputUpdateXML output: " + sOutputUpdateXML);
								xp = new XMLParser(sOutput);
								returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
								String nextValue ="";
								if(returnCode == 0){
									sErrorDescription = xp.getValueOf("errorDescription");
									sResponse = "Success";
									sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
									WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
									sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
									WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
									out.println(returnCode+"#"+sResponse+"#"+sErrorDescription);
								} else {
									sErrorDescription = xp.getValueOf("errorDescription");
									sResponse = "Failure";
									sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
									WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
									sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
									WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
									out.println("1#"+sResponse+"#"+sErrorDescription);
								}
							}
						}
						else if("ELC".equalsIgnoreCase(sRequestCategoryCode) && "ELC_LCA".equalsIgnoreCase(sRequestTypeCode))
						{						
							
						   sSwiftProcStatus = sOutput.substring(sOutput.indexOf("<SWIFT_PROCESSING_STATUS>")+"<SWIFT_PROCESSING_STATUS>".length(),sOutput.indexOf("</SWIFT_PROCESSING_STATUS>"));
						   sOperationCode = sOutput.substring(sOutput.indexOf("<OPERATION_CODE>")+"<OPERATION_CODE>".length(),sOutput.indexOf("</OPERATION_CODE>"));
							sContractAmount = sOutput.substring(sOutput.indexOf("<TRANSACTION_AMOUNT>")+"<TRANSACTION_AMOUNT>".length(),sOutput.indexOf("</TRANSACTION_AMOUNT>"));
							sAmendType = sOutput.substring(sOutput.indexOf("<AMEND_TYPE>")+"<AMEND_TYPE>".length(),sOutput.indexOf("</AMEND_TYPE>"));
							sNegativeTolerance = sOutput.substring(sOutput.indexOf("<LC_UNDER>")+"<LC_UNDER>".length(),sOutput.indexOf("</LC_UNDER>"));
							sPositiveTolerance = sOutput.substring(sOutput.indexOf("<LC_ABOVE>")+"<LC_ABOVE>".length(),sOutput.indexOf("</LC_ABOVE>"));
							sExpiryDate = sOutput.substring(sOutput.indexOf("<EXP_DATE>")+"<EXP_DATE>".length(),sOutput.indexOf("</EXP_DATE>"));

							sContractRefNumber = sOutput.substring(sOutput.indexOf("<TRANSACTION_REFERENCE>")+"<TRANSACTION_REFERENCE>".length(),sOutput.indexOf("</TRANSACTION_REFERENCE>"));
							sProductCode = sOutput.substring(sOutput.indexOf("<PRODUCT_TYPE>")+"<PRODUCT_TYPE>".length(),sOutput.indexOf("</PRODUCT_TYPE>"));
							sFromPlace = sOutput.substring(sOutput.indexOf("<LC_FROM_PLACE>")+"<LC_FROM_PLACE>".length(),sOutput.indexOf("</LC_FROM_PLACE>"));
							sToPlace = sOutput.substring(sOutput.indexOf("<LC_TO_PLACE>")+"<LC_TO_PLACE>".length(),sOutput.indexOf("</LC_TO_PLACE>"));
							sLastShipmentDate = sOutput.substring(sOutput.indexOf("<LC_LTST_SHIPMNT_DATE>")+"<LC_LTST_SHIPMNT_DATE>".length(),sOutput.indexOf("</LC_LTST_SHIPMNT_DATE>"));
							sPortOfDischarge = sOutput.substring(sOutput.indexOf("<LC_PORT_OF_DISCHRG>")+"<LC_PORT_OF_DISCHRG>".length(),sOutput.indexOf("</LC_PORT_OF_DISCHRG>"));
							sPortOfLoading = sOutput.substring(sOutput.indexOf("<LC_PORT_OF_LOADING>")+"<LC_PORT_OF_LOADING>".length(),sOutput.indexOf("</LC_PORT_OF_LOADING>"));
							sGoodsDescription = sOutput.substring(sOutput.indexOf("<LC_GOODS_DESC>")+"<LC_GOODS_DESC>".length(),sOutput.indexOf("</LC_GOODS_DESC>"));
							
							sRelationshipType = sOutput.substring(sOutput.indexOf("<RELATIONSHIP_TYPE>")+"<RELATIONSHIP_TYPE>".length(),sOutput.indexOf("</RELATIONSHIP_TYPE>"));
							sBranchCreateCode = fetchBranchCode(sRelationshipType , sWIName);
							
							if(sCallName.equalsIgnoreCase("modTradeLCContract_Oper")){							
								callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeLCContract_Oper</operationType><correlationID>342343</correlationID><modTradeLCContractReq><requestType>A</requestType><operationCode>" + sOperationCode + "</operationCode><contractAmount>" + sContractAmount + "</contractAmount><negativeTolerance>" + sNegativeTolerance + "</negativeTolerance><positiveTolerance>" + sPositiveTolerance + "</positiveTolerance><expiryDate>" + sExpiryDate + "</expiryDate><productCode>" + sProductCode + "</productCode><contractRefNumber>" + sContractRefNumber + "</contractRefNumber><partyDetails><partyDetail><partyType>BEN</partyType></partyDetail></partyDetails><shipmentDetails><fromPlace>" + sFromPlace + "</fromPlace><toPlace>" + sToPlace + "</toPlace><lastShipmentDate>" + sLastShipmentDate + "</lastShipmentDate><portOfDischarge>" + sPortOfDischarge + "</portOfDischarge><portOfLoading>" + sPortOfLoading + "</portOfLoading></shipmentDetails><goodsDetails><goodsDescription>" + sGoodsDescription + "</goodsDescription></goodsDetails><branchCode>"+sBranchCreateCode+"</branchCode><wmsDetails><workItemNumber>" + sWIName + "</workItemNumber></wmsDetails></modTradeLCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";
								
								WriteToLog_showpage("Y","modTradeLCContract_Oper sOutput: "+ callXML);
								sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sCallName +"',"+ convertToPlainString(callXML) +",'"+ REFNO +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","modTradeLCContract_Oper callXML TFO_DJ_INTEGRATION_CALLS_DTLS: "+ sQuery + " *****************************");	
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);						
								WriteToLog_showpage("Y"," insert modTradeLCContract_Oper sOutput: "+ sOutput);											
								
								sOutput = socket.connectToSocket(callXML);
								WriteToLog_showpage("Y","modTradeLCContract_Oper callXML sOutput"+ sOutput);
								String queryUpdateXML = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'" + sOutput + "'","WI_NAME = '"+ sWIName +"'  and REF_NUM = '"+REFNO+"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update sOutputUpdateXML input: "+ queryUpdateXML);
								String sOutputUpdateXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",queryUpdateXML);
								WriteToLog_showpage("Y","update sOutputUpdateXML output: " + sOutputUpdateXML);
								xp = new XMLParser(sOutput);
								returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
								String nextValue ="";
								if(returnCode == 0){
									sErrorDescription = xp.getValueOf("errorDescription");
									sResponse = "Success";
									sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
									WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
									sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
									WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
									out.println(returnCode+"#"+sResponse+"#"+sErrorDescription);
								} else {
									sErrorDescription = xp.getValueOf("errorDescription");
									sResponse = "Failure";
									sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
									WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
									sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
									WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
									out.println("1#"+sResponse+"#"+sErrorDescription);
								}
							}
						}
						
						else if("ILC".equalsIgnoreCase(sRequestCategoryCode) && "ILC_AM".equalsIgnoreCase(sRequestTypeCode))
						{						
							
						   sOperationCode = sOutput.substring(sOutput.indexOf("<OPERATION_CODE>")+"<OPERATION_CODE>".length(),sOutput.indexOf("</OPERATION_CODE>"));
							//sContractAmount = sOutput.substring(sOutput.indexOf("<TRANSACTION_AMOUNT>")+"<TRANSACTION_AMOUNT>".length(),sOutput.indexOf("</TRANSACTION_AMOUNT>"));
							sAmendType = sOutput.substring(sOutput.indexOf("<AMEND_TYPE>")+"<AMEND_TYPE>".length(),sOutput.indexOf("</AMEND_TYPE>"));
							//sNewTransactionAmount = sOutput.substring(sOutput.indexOf("<NEW_TRN_AMT>")+"<NEW_TRN_AMT>".length(),sOutput.indexOf("</NEW_TRN_AMT>"));
							//sNegativeTolerance = sOutput.substring(sOutput.indexOf("<LC_UNDER>")+"<LC_UNDER>".length(),sOutput.indexOf("</LC_UNDER>"));
						//	sPositiveTolerance = sOutput.substring(sOutput.indexOf("<LC_ABOVE>")+"<LC_ABOVE>".length(),sOutput.indexOf("</LC_ABOVE>"));
							//sExpiryDate = sOutput.substring(sOutput.indexOf("<EXP_DATE>")+"<EXP_DATE>".length(),sOutput.indexOf("</EXP_DATE>"));
							//sNewExpiryDate = sOutput.substring(sOutput.indexOf("<NEW_EXP_DATE>")+"<NEW_EXP_DATE>".length(),sOutput.indexOf("</NEW_EXP_DATE>"));

							sContractRefNumber = sOutput.substring(sOutput.indexOf("<TRANSACTION_REFERENCE>")+"<TRANSACTION_REFERENCE>".length(),sOutput.indexOf("</TRANSACTION_REFERENCE>"));
							sProductCode = sOutput.substring(sOutput.indexOf("<PRODUCT_TYPE>")+"<PRODUCT_TYPE>".length(),sOutput.indexOf("</PRODUCT_TYPE>"));
							//sFromPlace = sOutput.substring(sOutput.indexOf("<LC_FROM_PLACE>")+"<LC_FROM_PLACE>".length(),sOutput.indexOf("</LC_FROM_PLACE>"));
							//sToPlace = sOutput.substring(sOutput.indexOf("<LC_TO_PLACE>")+"<LC_TO_PLACE>".length(),sOutput.indexOf("</LC_TO_PLACE>"));
							//sLastShipmentDate = sOutput.substring(sOutput.indexOf("<LC_LTST_SHIPMNT_DATE>")+"<LC_LTST_SHIPMNT_DATE>".length(),sOutput.indexOf("</LC_LTST_SHIPMNT_DATE>"));
							//sPortOfDischarge = sOutput.substring(sOutput.indexOf("<LC_PORT_OF_DISCHRG>")+"<LC_PORT_OF_DISCHRG>".length(),sOutput.indexOf("</LC_PORT_OF_DISCHRG>"));
							//sPortOfLoading = sOutput.substring(sOutput.indexOf("<LC_PORT_OF_LOADING>")+"<LC_PORT_OF_LOADING>".length(),sOutput.indexOf("</LC_PORT_OF_LOADING>"));
							//sGoodsDescription = sOutput.substring(sOutput.indexOf("<LC_GOODS_DESC>")+"<LC_GOODS_DESC>".length(),sOutput.indexOf("</LC_GOODS_DESC>"));
							String thirdParty=sOutput.substring(sOutput.indexOf("<TRN_THIRD_PARTY>")+"<TRN_THIRD_PARTY>".length(),sOutput.indexOf("</TRN_THIRD_PARTY>"));
							
							
							
							/* if("ILC_IV".equalsIgnoreCase(sAmendType)||"ILC_DV".equalsIgnoreCase(sAmendType)||"ILC_CT".equalsIgnoreCase(sAmendType)){
							  sContractAmount=sNewTransactionAmount;
							  }
							  if("ILC_EED".equalsIgnoreCase(sAmendType)||"ILC_CED".equalsIgnoreCase(sAmendType)||"ILC_CT".equalsIgnoreCase(sAmendType)){
							   sExpiryDate=sNewExpiryDate;
							   }
							   */
							   if("1".equalsIgnoreCase(thirdParty)){  //1-yes
								   sPartyType="ACC";
							   }else{
								   sPartyType="APP"; 
							   }
							   sRelationshipType = sOutput.substring(sOutput.indexOf("<RELATIONSHIP_TYPE>")+"<RELATIONSHIP_TYPE>".length(),sOutput.indexOf("</RELATIONSHIP_TYPE>"));
							sBranchCreateCode = fetchBranchCode(sRelationshipType , sWIName);
							fetchAmendmentTabData(sWIName);
							
							
							if(sCallName.equalsIgnoreCase("modTradeLCContract_Oper")){							
								callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeLCContract_Oper</operationType><correlationID>342343</correlationID><modTradeLCContractReq><requestType>A</requestType><operationCode>" + sOperationCode + "</operationCode><contractAmount>" + sContractAmount + "</contractAmount><negativeTolerance>" + sNegativeTolerance + "</negativeTolerance><positiveTolerance>" + sPositiveTolerance + "</positiveTolerance><expiryDate>" + sExpiryDate + "</expiryDate><productCode>" + sProductCode + "</productCode><contractRefNumber>" + sContractRefNumber + "</contractRefNumber><partyDetails><partyDetail><partyType>"+sPartyType+"</partyType></partyDetail></partyDetails><shipmentDetails><fromPlace>" + sFromPlace + "</fromPlace><toPlace>" + sToPlace + "</toPlace><lastShipmentDate>" + sLastShipmentDate + "</lastShipmentDate><portOfDischarge>" + sPortOfDischarge + "</portOfDischarge><portOfLoading>" + sPortOfLoading + "</portOfLoading><partialShipmentAllowed>" + sPartialShipmentAllowed + "</partialShipmentAllowed><transshipmentAllowed>" + sTransShipmentAllowed + "</transshipmentAllowed></shipmentDetails>"+
								//<drafts><draft><draftTenor></draftTenor><creditDaysFrom></creditDaysFrom><drawee></drawee><draftAmount></draftAmount></draft></drafts>
								"<goodsDetails><goodsDescription>" + sGoodsDescription + "</goodsDescription></goodsDetails><branchCode>"+sBranchCreateCode+"</branchCode><wmsDetails><workItemNumber>" + sWIName + "</workItemNumber></wmsDetails><placeOfExpiry>" + sExpiryPlace + "</placeOfExpiry><settlementMonth>" + sCreditMode + "</settlementMonth><paymentDate>" + sCreditDetails + "</paymentDate><mayConfirm>" + sMayConfirmFlag + "</mayConfirm><requestedConfirmationParty>" + sRequestedConfirmationParty + "</requestedConfirmationParty><chargesFromBenef>" + sChargesFromBeneficiary + "</chargesFromBenef><presentationPeriod>" + sPeriodOfPresentationDays + "</presentationPeriod><presentationNarrative>" + sPeriod_Of_Presentation_Mode + "</presentationNarrative></modTradeLCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";
								
								  //callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeLCContract_Oper</operationType><correlationID>342343</correlationID><modTradeLCContractReq><requestType>A</requestType><operationCode>" + sOperationCode + "</operationCode><contractAmount>" + sContractAmount + "</contractAmount><negativeTolerance>" + sNegativeTolerance + "</negativeTolerance><positiveTolerance>" + sPositiveTolerance + "</positiveTolerance><expiryDate>" + sExpiryDate + "</expiryDate><productCode>" + sProductCode + "</productCode><contractRefNumber>" + sContractRefNumber + "</contractRefNumber><partyDetails><partyDetail><partyType>"+sPartyType+"</partyType></partyDetail></partyDetails><shipmentDetails><fromPlace>" + sFromPlace + "</fromPlace><toPlace>" + sToPlace + "</toPlace><lastShipmentDate>" + sLastShipmentDate + "</lastShipmentDate><portOfDischarge>" + sPortOfDischarge + "</portOfDischarge><portOfLoading>" + sPortOfLoading + "</portOfLoading></shipmentDetails><goodsDetails><goodsDescription>" + sGoodsDescription + "</goodsDescription></goodsDetails><branchCode>"+sBranchCreateCode+"</branchCode><wmsDetails><workItemNumber>" + sWIName + "</workItemNumber></wmsDetails></modTradeLCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";

								WriteToLog_showpage("Y","modTradeLCContract_Oper sOutput: "+ callXML);
								sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sCallName +"',"+ convertToPlainString(callXML) +",'"+ REFNO +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","modTradeLCContract_Oper callXML TFO_DJ_INTEGRATION_CALLS_DTLS: "+ sQuery + " *****************************");	
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);						
								WriteToLog_showpage("Y"," insert modTradeLCContract_Oper sOutput: "+ sOutput);											
								
								sOutput = socket.connectToSocket(callXML);
								WriteToLog_showpage("Y","modTradeLCContract_Oper callXML sOutput"+ sOutput);
								String queryUpdateXML = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'" + sOutput + "'","WI_NAME = '"+ sWIName +"'  and REF_NUM = '"+REFNO+"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update sOutputUpdateXML input: "+ queryUpdateXML);
								String sOutputUpdateXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",queryUpdateXML);
								WriteToLog_showpage("Y","update sOutputUpdateXML output: " + sOutputUpdateXML);
								xp = new XMLParser(sOutput);
								returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
								String nextValue ="";
								if(returnCode == 0){
									sErrorDescription = xp.getValueOf("errorDescription");
									sResponse = "Success";
									sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
									WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
									sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
									WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
									out.println(returnCode+"#"+sResponse+"#"+sErrorDescription);
								} else {
									sErrorDescription = xp.getValueOf("errorDescription");
									sResponse = "Failure";
									sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
									WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
									sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
									WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
									out.println("1#"+sResponse+"#"+sErrorDescription);
								}
							}
						}else if("GRNT".equalsIgnoreCase(sRequestCategoryCode) && "AM".equalsIgnoreCase(sRequestTypeCode))
						{						
							WriteToLog_showpage("Y","inside grnt");
							sOperationCode = sOutput.substring(sOutput.indexOf("<OPERATION_CODE>")+"<OPERATION_CODE>".length(),sOutput.indexOf("</OPERATION_CODE>"));
							WriteToLog_showpage("Y","sOperationCode :"+	sOperationCode);
							sContractAmount = sOutput.substring(sOutput.indexOf("<TRANSACTION_AMOUNT>")+"<TRANSACTION_AMOUNT>".length(),sOutput.indexOf("</TRANSACTION_AMOUNT>"));
							WriteToLog_showpage("Y","sContractAmount :"+sContractAmount);
							sAmendType = sOutput.substring(sOutput.indexOf("<AMEND_TYPE>")+"<AMEND_TYPE>".length(),sOutput.indexOf("</AMEND_TYPE>"));
							WriteToLog_showpage("Y","sAmendType :"+sAmendType);
							sNewTransactionAmount = sOutput.substring(sOutput.indexOf("<NEW_TRN_AMT>")+"<NEW_TRN_AMT>".length(),sOutput.indexOf("</NEW_TRN_AMT>"));
							WriteToLog_showpage("Y","sNewTransactionAmount :"+sNewTransactionAmount);
							sNegativeTolerance = sOutput.substring(sOutput.indexOf("<LC_UNDER>")+"<LC_UNDER>".length(),sOutput.indexOf("</LC_UNDER>"));
							WriteToLog_showpage("Y","sNegativeTolerance :"+sNegativeTolerance);
							sPositiveTolerance = sOutput.substring(sOutput.indexOf("<LC_ABOVE>")+"<LC_ABOVE>".length(),sOutput.indexOf("</LC_ABOVE>"));
							WriteToLog_showpage("Y","sPositiveTolerance :"+sPositiveTolerance);
							sExpiryDate = sOutput.substring(sOutput.indexOf("<EXP_DATE>")+"<EXP_DATE>".length(),sOutput.indexOf("</EXP_DATE>"));
							WriteToLog_showpage("Y","sExpiryDate :"+sExpiryDate);
							sNewExpiryDate = sOutput.substring(sOutput.indexOf("<NEW_EXP_DATE>")+"<NEW_EXP_DATE>".length(),sOutput.indexOf("</NEW_EXP_DATE>"));
							WriteToLog_showpage("Y","sNewExpiryDate :"+sNewExpiryDate);
							sProductCode = sOutput.substring(sOutput.indexOf("<PRODUCT_TYPE>")+"<PRODUCT_TYPE>".length(),sOutput.indexOf("</PRODUCT_TYPE>"));
							WriteToLog_showpage("Y","sProductCode :"+sProductCode);
							sContractRefNumber = sOutput.substring(sOutput.indexOf("<TRANSACTION_REFERENCE>")+"<TRANSACTION_REFERENCE>".length(),sOutput.indexOf("</TRANSACTION_REFERENCE>"));
							WriteToLog_showpage("Y","sContractRefNumber :"+sContractRefNumber);
							sProductCode = sOutput.substring(sOutput.indexOf("<PRODUCT_TYPE>")+"<PRODUCT_TYPE>".length(),sOutput.indexOf("</PRODUCT_TYPE>"));
							WriteToLog_showpage("Y","sProductCode :"+sProductCode);
							sFromPlace = "";
							sToPlace = "";
							sLastShipmentDate ="";
							sPortOfDischarge = "";
							sPortOfLoading = "";
							sGoodsDescription ="";
							String thirdParty=sOutput.substring(sOutput.indexOf("<TRN_THIRD_PARTY>")+"<TRN_THIRD_PARTY>".length(),sOutput.indexOf("</TRN_THIRD_PARTY>"));
							WriteToLog_showpage("Y","thirdParty :"+thirdParty);
							
							   if("IV".equalsIgnoreCase(sAmendType)||"DV".equalsIgnoreCase(sAmendType)||"CT".equalsIgnoreCase(sAmendType)||"OFT".equalsIgnoreCase(sAmendType)||"FOT".equalsIgnoreCase(sAmendType)){
							   sContractAmount=sNewTransactionAmount;
							   }
							  if("OF".equalsIgnoreCase(sAmendType)||"EE".equalsIgnoreCase(sAmendType)||"CE".equalsIgnoreCase(sAmendType)||"OFT".equalsIgnoreCase(   sAmendType)||"CT".equalsIgnoreCase(sAmendType)){
							   sExpiryDate=sNewExpiryDate;
							  }else if("FO".equalsIgnoreCase(sAmendType)||"FOT".equalsIgnoreCase(sAmendType)){
							   sExpiryDate="";
							  }
							 String productList="T551,T552,T553,T554,T555,T556,T557,T558,T575";
							 WriteToLog_showpage("Y","productData=: "+ Arrays.asList(productList.split(",")));
							 List productData=Arrays.asList(productList.split(","));
							 WriteToLog_showpage("Y","productData=: "+ productData);
						
							 if (productData.contains(sProductCode)) {
								 sPartyType = "APB";
							  WriteToLog_showpage("Y","customerType1=: "+ sPartyType);}
							 else if("1".equalsIgnoreCase(thirdParty))    //1-yes
							 {
							 sPartyType = "ACC";
							}else{
							  sPartyType = "APP";
						
							 }
							
							 sRelationshipType = sOutput.substring(sOutput.indexOf("<RELATIONSHIP_TYPE>")+"<RELATIONSHIP_TYPE>".length(),sOutput.indexOf("</RELATIONSHIP_TYPE>"));
							sBranchCreateCode = fetchBranchCode(sRelationshipType , sWIName);
							 WriteToLog_showpage("Y","just before xml");
							  fetchAmendmentTabData(sWIName);
							if(sCallName.equalsIgnoreCase("modTradeLCContract_Oper")){							
								//callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeLCContract_Oper</operationType><correlationID>342343</correlationID><modTradeLCContractReq><requestType>A</requestType><operationCode>" + sOperationCode + "</operationCode><contractAmount>" + sContractAmount + "</contractAmount><negativeTolerance>" + sNegativeTolerance + "</negativeTolerance><positiveTolerance>" + sPositiveTolerance + "</positiveTolerance><expiryDate>" + sExpiryDate + "</expiryDate><productCode>" + sProductCode + "</productCode><contractRefNumber>" + sContractRefNumber + "</contractRefNumber><partyDetails><partyDetail><partyType>"+sPartyType+"</partyType></partyDetail></partyDetails><shipmentDetails><fromPlace>" + sFromPlace + "</fromPlace><toPlace>" + sToPlace + "</toPlace><lastShipmentDate>" + sLastShipmentDate + "</lastShipmentDate><portOfDischarge>" + sPortOfDischarge + "</portOfDischarge><portOfLoading>" + sPortOfLoading + "</portOfLoading></shipmentDetails><goodsDetails><goodsDescription>" + sGoodsDescription + "</goodsDescription></goodsDetails><branchCode>"+sBranchCreateCode+"</branchCode><wmsDetails><workItemNumber>" + sWIName + "</workItemNumber></wmsDetails></modTradeLCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";
								callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeLCContract_Oper</operationType><correlationID>342343</correlationID><modTradeLCContractReq><requestType>A</requestType><operationCode>" + sOperationCode + "</operationCode><contractAmount>" + sContractAmount + "</contractAmount><negativeTolerance>" + sNegativeTolerance + "</negativeTolerance><positiveTolerance>" + sPositiveTolerance + "</positiveTolerance><expiryDate>" + sExpiryDate + "</expiryDate><productCode>" + sProductCode + "</productCode><contractRefNumber>" + sContractRefNumber + "</contractRefNumber><partyDetails><partyDetail><partyType>"+sPartyType+"</partyType></partyDetail></partyDetails><shipmentDetails><fromPlace>" + sFromPlace + "</fromPlace><toPlace>" + sToPlace + "</toPlace><lastShipmentDate>" + sLastShipmentDate + "</lastShipmentDate><portOfDischarge>" + sPortOfDischarge + "</portOfDischarge><portOfLoading>" + sPortOfLoading + "</portOfLoading><partialShipmentAllowed></partialShipmentAllowed><transshipmentAllowed></transshipmentAllowed></shipmentDetails><drafts><draft><draftTenor></draftTenor><creditDaysFrom></creditDaysFrom><drawee></drawee><draftAmount></draftAmount></draft></drafts><goodsDetails><goodsDescription>" + sGoodsDescription + "</goodsDescription></goodsDetails><branchCode>"+sBranchCreateCode+"</branchCode><wmsDetails><workItemNumber>" + sWIName + "</workItemNumber></wmsDetails><placeOfExpiry></placeOfExpiry><settlementMonth></settlementMonth><paymentDate></paymentDate><mayConfirm>N</mayConfirm><requestedConfirmationParty></requestedConfirmationParty><chargesFromBenef></chargesFromBenef><presentationPeriod></presentationPeriod><presentationNarrative></presentationNarrative><expiryConditionOrEvent>" + sFinExpiryCond + "</expiryConditionOrEvent><expiryType>" + sFinExpiryType + "</expiryType><counterGuaranteeExpiryDate>" + sFinCntrGteeExpDate + "</counterGuaranteeExpiryDate><termsAndCondDtls><termsAndCondDtl><type>Local Guarantee</type><description>"+ sFinOtherAmendment +"</description></termsAndCondDtl></termsAndCondDtls></modTradeLCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";
								
								WriteToLog_showpage("Y","modTradeLCContract_Oper sOutput: "+ callXML);
								sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sCallName +"',"+ convertToPlainString(callXML) +",'"+ REFNO +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","modTradeLCContract_Oper callXML TFO_DJ_INTEGRATION_CALLS_DTLS: "+ sQuery + " *****************************");	
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);						
								WriteToLog_showpage("Y"," insert modTradeLCContract_Oper sOutput: "+ sOutput);											
								
								sOutput = socket.connectToSocket(callXML);
								WriteToLog_showpage("Y","modTradeLCContract_Oper callXML sOutput"+ sOutput);
								String queryUpdateXML = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'" + sOutput + "'","WI_NAME = '"+ sWIName +"'  and REF_NUM = '"+REFNO+"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update sOutputUpdateXML input: "+ queryUpdateXML);
								String sOutputUpdateXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",queryUpdateXML);
								WriteToLog_showpage("Y","update sOutputUpdateXML output: " + sOutputUpdateXML);
								xp = new XMLParser(sOutput);
								returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
								String nextValue ="";
								if(returnCode == 0){
									sErrorDescription = xp.getValueOf("errorDescription");
									sResponse = "Success";
									sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
									WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
									sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
									WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
									out.println(returnCode+"#"+sResponse+"#"+sErrorDescription);
								} else {
									sErrorDescription = xp.getValueOf("errorDescription");
									sResponse = "Failure";
								//	sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
									WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
									sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
									out.println("1#"+sResponse+"#"+sErrorDescription);
									//out.println("0#"+"Success"+"#"+sErrorDescription);
								}
							}
						}else if("GRNT".equalsIgnoreCase(sRequestCategoryCode) && "GAA".equalsIgnoreCase(sRequestTypeCode))
						{						
							
						   sOperationCode = sOutput.substring(sOutput.indexOf("<OPERATION_CODE>")+"<OPERATION_CODE>".length(),sOutput.indexOf("</OPERATION_CODE>"));
							sContractAmount = sOutput.substring(sOutput.indexOf("<TRANSACTION_AMOUNT>")+"<TRANSACTION_AMOUNT>".length(),sOutput.indexOf("</TRANSACTION_AMOUNT>"));
							sAmendType = sOutput.substring(sOutput.indexOf("<AMEND_TYPE>")+"<AMEND_TYPE>".length(),sOutput.indexOf("</AMEND_TYPE>"));
							sNewTransactionAmount = sOutput.substring(sOutput.indexOf("<NEW_TRN_AMT>")+"<NEW_TRN_AMT>".length(),sOutput.indexOf("</NEW_TRN_AMT>"));
							sNegativeTolerance = sOutput.substring(sOutput.indexOf("<LC_UNDER>")+"<LC_UNDER>".length(),sOutput.indexOf("</LC_UNDER>"));
							sPositiveTolerance = sOutput.substring(sOutput.indexOf("<LC_ABOVE>")+"<LC_ABOVE>".length(),sOutput.indexOf("</LC_ABOVE>"));
							sExpiryDate = sOutput.substring(sOutput.indexOf("<EXP_DATE>")+"<EXP_DATE>".length(),sOutput.indexOf("</EXP_DATE>"));
							sNewExpiryDate = sOutput.substring(sOutput.indexOf("<NEW_EXP_DATE>")+"<NEW_EXP_DATE>".length(),sOutput.indexOf("</NEW_EXP_DATE>"));
							sProductCode = sOutput.substring(sOutput.indexOf("<PRODUCT_TYPE>")+"<PRODUCT_TYPE>".length(),sOutput.indexOf("</PRODUCT_TYPE>"));
							sContractRefNumber = sOutput.substring(sOutput.indexOf("<TRANSACTION_REFERENCE>")+"<TRANSACTION_REFERENCE>".length(),sOutput.indexOf("</TRANSACTION_REFERENCE>"));
							sProductCode = sOutput.substring(sOutput.indexOf("<PRODUCT_TYPE>")+"<PRODUCT_TYPE>".length(),sOutput.indexOf("</PRODUCT_TYPE>"));
							sFromPlace = "";
							sToPlace = "";
							sLastShipmentDate ="";
							sPortOfDischarge = "";
							sPortOfLoading = "";
							sGoodsDescription ="";
							
							
							   if("IV".equalsIgnoreCase(sAmendType)||"DV".equalsIgnoreCase(sAmendType)||"CT".equalsIgnoreCase(sAmendType)||"OFT".equalsIgnoreCase(sAmendType)||"FOT".equalsIgnoreCase(sAmendType)){
							   sContractAmount=sNewTransactionAmount;
							   }
							  if("OF".equalsIgnoreCase(sAmendType)||"EE".equalsIgnoreCase(sAmendType)||"CE".equalsIgnoreCase(sAmendType)||"OFT".equalsIgnoreCase(   sAmendType)||"CT".equalsIgnoreCase(sAmendType)){
							   sExpiryDate=sNewExpiryDate;
							  }else if("FO".equalsIgnoreCase(sAmendType)||"FOT".equalsIgnoreCase(sAmendType)){
							   sExpiryDate="";
							  }
							 sPartyType="ISB";
							 sRelationshipType = sOutput.substring(sOutput.indexOf("<RELATIONSHIP_TYPE>")+"<RELATIONSHIP_TYPE>".length(),sOutput.indexOf("</RELATIONSHIP_TYPE>"));
							sBranchCreateCode = fetchBranchCode(sRelationshipType , sWIName);
							
							fetchAmendmentTabData(sWIName);
							if(sCallName.equalsIgnoreCase("modTradeLCContract_Oper"))
							{							
								//callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeLCContract_Oper</operationType><correlationID>342343</correlationID><modTradeLCContractReq><requestType>A</requestType><operationCode>" + sOperationCode + "</operationCode><contractAmount>" + sContractAmount + "</contractAmount><negativeTolerance>" + sNegativeTolerance + "</negativeTolerance><positiveTolerance>" + sPositiveTolerance + "</positiveTolerance><expiryDate>" + sExpiryDate + "</expiryDate><productCode>" + sProductCode + "</productCode><contractRefNumber>" + sContractRefNumber + "</contractRefNumber><partyDetails><partyDetail><partyType>"+sPartyType+"</partyType></partyDetail></partyDetails><shipmentDetails><fromPlace>" + sFromPlace + "</fromPlace><toPlace>" + sToPlace + "</toPlace><lastShipmentDate>" + sLastShipmentDate + "</lastShipmentDate><portOfDischarge>" + sPortOfDischarge + "</portOfDischarge><portOfLoading>" + sPortOfLoading + "</portOfLoading></shipmentDetails><goodsDetails><goodsDescription>" + sGoodsDescription + "</goodsDescription></goodsDetails><branchCode>"+sBranchCreateCode+"</branchCode><wmsDetails><workItemNumber>" + sWIName + "</workItemNumber></wmsDetails></modTradeLCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";
                                 
								 callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeLCContract_Oper</operationType><correlationID>342343</correlationID><modTradeLCContractReq><requestType>A</requestType><operationCode>" + sOperationCode + "</operationCode><contractAmount>" + sContractAmount + "</contractAmount><negativeTolerance>" + sNegativeTolerance + "</negativeTolerance><positiveTolerance>" + sPositiveTolerance + "</positiveTolerance><expiryDate>" + sExpiryDate + "</expiryDate><productCode>" + sProductCode + "</productCode><contractRefNumber>" + sContractRefNumber + "</contractRefNumber><partyDetails><partyDetail><partyType>"+sPartyType+"</partyType></partyDetail></partyDetails><shipmentDetails><fromPlace>" + sFromPlace + "</fromPlace><toPlace>" + sToPlace + "</toPlace><lastShipmentDate>" + sLastShipmentDate + "</lastShipmentDate><portOfDischarge>" + sPortOfDischarge + "</portOfDischarge><portOfLoading>" + sPortOfLoading + "</portOfLoading><partialShipmentAllowed></partialShipmentAllowed><transshipmentAllowed></transshipmentAllowed></shipmentDetails><drafts><draft><draftTenor></draftTenor><creditDaysFrom></creditDaysFrom><drawee></drawee><draftAmount></draftAmount></draft></drafts><goodsDetails><goodsDescription>" + sGoodsDescription + "</goodsDescription></goodsDetails><branchCode>"+sBranchCreateCode+"</branchCode><wmsDetails><workItemNumber>" + sWIName + "</workItemNumber></wmsDetails><placeOfExpiry></placeOfExpiry><settlementMonth></settlementMonth><paymentDate></paymentDate><mayConfirm>N</mayConfirm><requestedConfirmationParty></requestedConfirmationParty><chargesFromBenef></chargesFromBenef><presentationPeriod></presentationPeriod><presentationNarrative></presentationNarrative><expiryConditionOrEvent>" + sFinExpiryCond + "</expiryConditionOrEvent><expiryType>" + sFinExpiryType + "</expiryType><counterGuaranteeExpiryDate>" + sFinCntrGteeExpDate + "</counterGuaranteeExpiryDate><termsAndCondDtls><termsAndCondDtl><type>Local Guarantee</type><description>"+ sFinOtherAmendment +"</description></termsAndCondDtl></termsAndCondDtls></modTradeLCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";
								WriteToLog_showpage("Y","modTradeLCContract_Oper sOutput: "+ callXML);
								sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sCallName +"',"+ convertToPlainString(callXML) +",'"+ REFNO +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","modTradeLCContract_Oper callXML TFO_DJ_INTEGRATION_CALLS_DTLS: "+ sQuery + " *****************************");	
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);						
								WriteToLog_showpage("Y"," insert modTradeLCContract_Oper sOutput: "+ sOutput);											
								
								sOutput = socket.connectToSocket(callXML);
								WriteToLog_showpage("Y","modTradeLCContract_Oper callXML sOutput"+ sOutput);
								String queryUpdateXML = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'" + sOutput + "'","WI_NAME = '"+ sWIName +"'  and REF_NUM = '"+REFNO+"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update sOutputUpdateXML input: "+ queryUpdateXML);
								String sOutputUpdateXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",queryUpdateXML);
								WriteToLog_showpage("Y","update sOutputUpdateXML output: " + sOutputUpdateXML);
								xp = new XMLParser(sOutput);
								returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
								String nextValue ="";
								if(returnCode == 0){
									sErrorDescription = xp.getValueOf("errorDescription");
									sResponse = "Success";
									sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
									WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
									sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
									WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
									out.println(returnCode+"#"+sResponse+"#"+sErrorDescription);
								} else {
									sErrorDescription = xp.getValueOf("errorDescription");
									sResponse = "Failure";
									sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
									WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
									sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
									WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
									out.println("1#"+sResponse+"#"+sErrorDescription);
								}
							}
						
						}else if("ELC".equalsIgnoreCase(sRequestCategoryCode) && "ELC_SLCAA".equalsIgnoreCase(sRequestTypeCode))
						{						
							WriteToLog_showpage("Y","inside grnt");
							sOperationCode = sOutput.substring(sOutput.indexOf("<OPERATION_CODE>")+"<OPERATION_CODE>".length(),sOutput.indexOf("</OPERATION_CODE>"));
							WriteToLog_showpage("Y","sOperationCode :"+	sOperationCode);
							sContractAmount = sOutput.substring(sOutput.indexOf("<TRANSACTION_AMOUNT>")+"<TRANSACTION_AMOUNT>".length(),sOutput.indexOf("</TRANSACTION_AMOUNT>"));
							WriteToLog_showpage("Y","sContractAmount :"+sContractAmount);
							sAmendType = sOutput.substring(sOutput.indexOf("<AMEND_TYPE>")+"<AMEND_TYPE>".length(),sOutput.indexOf("</AMEND_TYPE>"));
							WriteToLog_showpage("Y","sAmendType :"+sAmendType);
							sNewTransactionAmount = sOutput.substring(sOutput.indexOf("<NEW_TRN_AMT>")+"<NEW_TRN_AMT>".length(),sOutput.indexOf("</NEW_TRN_AMT>"));
							WriteToLog_showpage("Y","sNewTransactionAmount :"+sNewTransactionAmount);
							sNegativeTolerance = sOutput.substring(sOutput.indexOf("<LC_UNDER>")+"<LC_UNDER>".length(),sOutput.indexOf("</LC_UNDER>"));
							WriteToLog_showpage("Y","sNegativeTolerance :"+sNegativeTolerance);
							sPositiveTolerance = sOutput.substring(sOutput.indexOf("<LC_ABOVE>")+"<LC_ABOVE>".length(),sOutput.indexOf("</LC_ABOVE>"));
							WriteToLog_showpage("Y","sPositiveTolerance :"+sPositiveTolerance);
							sExpiryDate = sOutput.substring(sOutput.indexOf("<EXP_DATE>")+"<EXP_DATE>".length(),sOutput.indexOf("</EXP_DATE>"));
							WriteToLog_showpage("Y","sExpiryDate :"+sExpiryDate);
							sNewExpiryDate = sOutput.substring(sOutput.indexOf("<NEW_EXP_DATE>")+"<NEW_EXP_DATE>".length(),sOutput.indexOf("</NEW_EXP_DATE>"));
							WriteToLog_showpage("Y","sNewExpiryDate :"+sNewExpiryDate);
							sProductCode = sOutput.substring(sOutput.indexOf("<PRODUCT_TYPE>")+"<PRODUCT_TYPE>".length(),sOutput.indexOf("</PRODUCT_TYPE>"));
							WriteToLog_showpage("Y","sProductCode :"+sProductCode);
							sContractRefNumber = sOutput.substring(sOutput.indexOf("<TRANSACTION_REFERENCE>")+"<TRANSACTION_REFERENCE>".length(),sOutput.indexOf("</TRANSACTION_REFERENCE>"));
							WriteToLog_showpage("Y","sContractRefNumber :"+sContractRefNumber);
							sProductCode = sOutput.substring(sOutput.indexOf("<PRODUCT_TYPE>")+"<PRODUCT_TYPE>".length(),sOutput.indexOf("</PRODUCT_TYPE>"));
							WriteToLog_showpage("Y","sProductCode :"+sProductCode);
							sFromPlace = "";
							sToPlace = "";
							sLastShipmentDate ="";
							sPortOfDischarge = "";
							sPortOfLoading = "";
							sGoodsDescription ="";
							String thirdParty=sOutput.substring(sOutput.indexOf("<TRN_THIRD_PARTY>")+"<TRN_THIRD_PARTY>".length(),sOutput.indexOf("</TRN_THIRD_PARTY>"));
							WriteToLog_showpage("Y","thirdParty :"+thirdParty);
							
							   if("IV".equalsIgnoreCase(sAmendType)||"DV".equalsIgnoreCase(sAmendType)||"CT".equalsIgnoreCase(sAmendType)||"OFT".equalsIgnoreCase(sAmendType)||"FOT".equalsIgnoreCase(sAmendType)){
							   sContractAmount=sNewTransactionAmount;
							   }
							  if("OF".equalsIgnoreCase(sAmendType)||"EE".equalsIgnoreCase(sAmendType)||"CE".equalsIgnoreCase(sAmendType)||"OFT".equalsIgnoreCase(   sAmendType)||"CT".equalsIgnoreCase(sAmendType)){
							   sExpiryDate=sNewExpiryDate;
							  }else if("FO".equalsIgnoreCase(sAmendType)||"FOT".equalsIgnoreCase(sAmendType)){
							   sExpiryDate="";
							  }
							 String productList="T551,T552,T553,T554,T555,T556,T557,T558,T575";
							 WriteToLog_showpage("Y","productData=: "+ Arrays.asList(productList.split(",")));
							 List productData=Arrays.asList(productList.split(","));
							 WriteToLog_showpage("Y","productData=: "+ productData);
						
							 if (productData.contains(sProductCode)) {
								 sPartyType = "APB";
							  WriteToLog_showpage("Y","customerType1=: "+ sPartyType);}
							 else if("1".equalsIgnoreCase(thirdParty))    //1-yes
							 {
							 sPartyType = "ACC";
							}else{
							  sPartyType = "APP";
						
							 }
							
							 sRelationshipType = sOutput.substring(sOutput.indexOf("<RELATIONSHIP_TYPE>")+"<RELATIONSHIP_TYPE>".length(),sOutput.indexOf("</RELATIONSHIP_TYPE>"));
							sBranchCreateCode = fetchBranchCode(sRelationshipType , sWIName);
							 WriteToLog_showpage("Y","just before xml");
							 fetchAmendmentTabData(sWIName);
							if(sCallName.equalsIgnoreCase("modTradeLCContract_Oper")){							
								//callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeLCContract_Oper</operationType><correlationID>342343</correlationID><modTradeLCContractReq><requestType>A</requestType><operationCode>" + sOperationCode + "</operationCode><contractAmount>" + sContractAmount + "</contractAmount><negativeTolerance>" + sNegativeTolerance + "</negativeTolerance><positiveTolerance>" + sPositiveTolerance + "</positiveTolerance><expiryDate>" + sExpiryDate + "</expiryDate><productCode>" + sProductCode + "</productCode><contractRefNumber>" + sContractRefNumber + "</contractRefNumber><partyDetails><partyDetail><partyType>"+sPartyType+"</partyType></partyDetail></partyDetails><shipmentDetails><fromPlace>" + sFromPlace + "</fromPlace><toPlace>" + sToPlace + "</toPlace><lastShipmentDate>" + sLastShipmentDate + "</lastShipmentDate><portOfDischarge>" + sPortOfDischarge + "</portOfDischarge><portOfLoading>" + sPortOfLoading + "</portOfLoading></shipmentDetails><goodsDetails><goodsDescription>" + sGoodsDescription + "</goodsDescription></goodsDetails><branchCode>"+sBranchCreateCode+"</branchCode><wmsDetails><workItemNumber>" + sWIName + "</workItemNumber></wmsDetails></modTradeLCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";
								callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeLCContract_Oper</operationType><correlationID>342343</correlationID><modTradeLCContractReq><requestType>A</requestType><operationCode>" + sOperationCode + "</operationCode><contractAmount>" + sContractAmount + "</contractAmount><negativeTolerance>" + sNegativeTolerance + "</negativeTolerance><positiveTolerance>" + sPositiveTolerance + "</positiveTolerance><expiryDate>" + sExpiryDate + "</expiryDate><productCode>" + sProductCode + "</productCode><contractRefNumber>" + sContractRefNumber + "</contractRefNumber><partyDetails><partyDetail><partyType>"+sPartyType+"</partyType></partyDetail></partyDetails><shipmentDetails><fromPlace>" + sFromPlace + "</fromPlace><toPlace>" + sToPlace + "</toPlace><lastShipmentDate>" + sLastShipmentDate + "</lastShipmentDate><portOfDischarge>" + sPortOfDischarge + "</portOfDischarge><portOfLoading>" + sPortOfLoading + "</portOfLoading><partialShipmentAllowed></partialShipmentAllowed><transshipmentAllowed></transshipmentAllowed></shipmentDetails><drafts><draft><draftTenor></draftTenor><creditDaysFrom></creditDaysFrom><drawee></drawee><draftAmount></draftAmount></draft></drafts><goodsDetails><goodsDescription>" + sGoodsDescription + "</goodsDescription></goodsDetails><branchCode>"+sBranchCreateCode+"</branchCode><wmsDetails><workItemNumber>" + sWIName + "</workItemNumber></wmsDetails><placeOfExpiry></placeOfExpiry><settlementMonth></settlementMonth><paymentDate></paymentDate><mayConfirm>N</mayConfirm><requestedConfirmationParty></requestedConfirmationParty><chargesFromBenef></chargesFromBenef><presentationPeriod></presentationPeriod><presentationNarrative></presentationNarrative><expiryConditionOrEvent>" + sFinExpiryCond + "</expiryConditionOrEvent><expiryType>" + sFinExpiryType + "</expiryType><counterGuaranteeExpiryDate>" + sFinCntrGteeExpDate + "</counterGuaranteeExpiryDate><termsAndCondDtls><termsAndCondDtl><type>Local Guarantee</type><description>"+ sFinOtherAmendment +"</description></termsAndCondDtl></termsAndCondDtls></modTradeLCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";
								
								WriteToLog_showpage("Y","modTradeLCContract_Oper sOutput: "+ callXML);
								sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sCallName +"',"+ convertToPlainString(callXML) +",'"+ REFNO +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","modTradeLCContract_Oper callXML TFO_DJ_INTEGRATION_CALLS_DTLS: "+ sQuery + " *****************************");	
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);						
								WriteToLog_showpage("Y"," insert modTradeLCContract_Oper sOutput: "+ sOutput);											
								
								sOutput = socket.connectToSocket(callXML);
								WriteToLog_showpage("Y","modTradeLCContract_Oper callXML sOutput"+ sOutput);
								String queryUpdateXML = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'" + sOutput + "'","WI_NAME = '"+ sWIName +"'  and REF_NUM = '"+REFNO+"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update sOutputUpdateXML input: "+ queryUpdateXML);
								String sOutputUpdateXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",queryUpdateXML);
								WriteToLog_showpage("Y","update sOutputUpdateXML output: " + sOutputUpdateXML);
								xp = new XMLParser(sOutput);
								returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
								String nextValue ="";
								if(returnCode == 0){
									sErrorDescription = xp.getValueOf("errorDescription");
									sResponse = "Success";
									sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
									WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
									sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
									WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
									out.println(returnCode+"#"+sResponse+"#"+sErrorDescription);
								} else {
									sErrorDescription = xp.getValueOf("errorDescription");
									sResponse = "Failure";
								//	sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
									WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
									sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
									out.println("1#"+sResponse+"#"+sErrorDescription);
									//out.println("0#"+"Success"+"#"+sErrorDescription);
								}
							}
						}else if("SBLC".equalsIgnoreCase(sRequestCategoryCode) && "SBLC_AM".equalsIgnoreCase(sRequestTypeCode))
						{						
							WriteToLog_showpage("Y","inside SBLC");
							sOperationCode = sOutput.substring(sOutput.indexOf("<OPERATION_CODE>")+"<OPERATION_CODE>".length(),sOutput.indexOf("</OPERATION_CODE>"));
							WriteToLog_showpage("Y","sOperationCode :"+	sOperationCode);
							sContractAmount = sOutput.substring(sOutput.indexOf("<TRANSACTION_AMOUNT>")+"<TRANSACTION_AMOUNT>".length(),sOutput.indexOf("</TRANSACTION_AMOUNT>"));
							WriteToLog_showpage("Y","sContractAmount :"+sContractAmount);
							sAmendType = sOutput.substring(sOutput.indexOf("<AMEND_TYPE>")+"<AMEND_TYPE>".length(),sOutput.indexOf("</AMEND_TYPE>"));
							WriteToLog_showpage("Y","sAmendType :"+sAmendType);
							sNewTransactionAmount = sOutput.substring(sOutput.indexOf("<NEW_TRN_AMT>")+"<NEW_TRN_AMT>".length(),sOutput.indexOf("</NEW_TRN_AMT>"));
							WriteToLog_showpage("Y","sNewTransactionAmount :"+sNewTransactionAmount);
							sNegativeTolerance = sOutput.substring(sOutput.indexOf("<LC_UNDER>")+"<LC_UNDER>".length(),sOutput.indexOf("</LC_UNDER>"));
							WriteToLog_showpage("Y","sNegativeTolerance :"+sNegativeTolerance);
							sPositiveTolerance = sOutput.substring(sOutput.indexOf("<LC_ABOVE>")+"<LC_ABOVE>".length(),sOutput.indexOf("</LC_ABOVE>"));
							WriteToLog_showpage("Y","sPositiveTolerance :"+sPositiveTolerance);
							sExpiryDate = sOutput.substring(sOutput.indexOf("<EXP_DATE>")+"<EXP_DATE>".length(),sOutput.indexOf("</EXP_DATE>"));
							WriteToLog_showpage("Y","sExpiryDate :"+sExpiryDate);
							sNewExpiryDate = sOutput.substring(sOutput.indexOf("<NEW_EXP_DATE>")+"<NEW_EXP_DATE>".length(),sOutput.indexOf("</NEW_EXP_DATE>"));
							WriteToLog_showpage("Y","sNewExpiryDate :"+sNewExpiryDate);
							sProductCode = sOutput.substring(sOutput.indexOf("<PRODUCT_TYPE>")+"<PRODUCT_TYPE>".length(),sOutput.indexOf("</PRODUCT_TYPE>"));
							WriteToLog_showpage("Y","sProductCode :"+sProductCode);
							sContractRefNumber = sOutput.substring(sOutput.indexOf("<TRANSACTION_REFERENCE>")+"<TRANSACTION_REFERENCE>".length(),sOutput.indexOf("</TRANSACTION_REFERENCE>"));
							WriteToLog_showpage("Y","sContractRefNumber :"+sContractRefNumber);
							sProductCode = sOutput.substring(sOutput.indexOf("<PRODUCT_TYPE>")+"<PRODUCT_TYPE>".length(),sOutput.indexOf("</PRODUCT_TYPE>"));
							WriteToLog_showpage("Y","sProductCode :"+sProductCode);
							sFromPlace = "";
							sToPlace = "";
							sLastShipmentDate ="";
							sPortOfDischarge = "";
							sPortOfLoading = "";
							sGoodsDescription ="";
							String thirdParty=sOutput.substring(sOutput.indexOf("<TRN_THIRD_PARTY>")+"<TRN_THIRD_PARTY>".length(),sOutput.indexOf("</TRN_THIRD_PARTY>"));
							WriteToLog_showpage("Y","thirdParty :"+thirdParty);
							
							   if("IV".equalsIgnoreCase(sAmendType)||"DV".equalsIgnoreCase(sAmendType)||"CT".equalsIgnoreCase(sAmendType)||"OFT".equalsIgnoreCase(sAmendType)||"FOT".equalsIgnoreCase(sAmendType)){
							   sContractAmount=sNewTransactionAmount;
							   }
							  if("OF".equalsIgnoreCase(sAmendType)||"EE".equalsIgnoreCase(sAmendType)||"CE".equalsIgnoreCase(sAmendType)||"OFT".equalsIgnoreCase(   sAmendType)||"CT".equalsIgnoreCase(sAmendType)){
							   sExpiryDate=sNewExpiryDate;
							  }else if("FO".equalsIgnoreCase(sAmendType)||"FOT".equalsIgnoreCase(sAmendType)){
							   sExpiryDate="";
							  }
							 String productList="T551,T552,T553,T554,T555,T556,T557,T558,T575";
							 WriteToLog_showpage("Y","productData=: "+ Arrays.asList(productList.split(",")));
							 List productData=Arrays.asList(productList.split(","));
							 WriteToLog_showpage("Y","productData=: "+ productData);
						
							 if (productData.contains(sProductCode)) {
								 sPartyType = "APB";
							  WriteToLog_showpage("Y","customerType1=: "+ sPartyType);}
							 else if("1".equalsIgnoreCase(thirdParty))    //1-yes
							 {
							 sPartyType = "ACC";
							}else{
							  sPartyType = "APP";
						
							 }
							
							 sRelationshipType = sOutput.substring(sOutput.indexOf("<RELATIONSHIP_TYPE>")+"<RELATIONSHIP_TYPE>".length(),sOutput.indexOf("</RELATIONSHIP_TYPE>"));
							sBranchCreateCode = fetchBranchCode(sRelationshipType , sWIName);
							 WriteToLog_showpage("Y","just before xml");
							 fetchAmendmentTabData(sWIName);
							if(sCallName.equalsIgnoreCase("modTradeLCContract_Oper")){							
								//callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeLCContract_Oper</operationType><correlationID>342343</correlationID><modTradeLCContractReq><requestType>A</requestType><operationCode>" + sOperationCode + "</operationCode><contractAmount>" + sContractAmount + "</contractAmount><negativeTolerance>" + sNegativeTolerance + "</negativeTolerance><positiveTolerance>" + sPositiveTolerance + "</positiveTolerance><expiryDate>" + sExpiryDate + "</expiryDate><productCode>" + sProductCode + "</productCode><contractRefNumber>" + sContractRefNumber + "</contractRefNumber><partyDetails><partyDetail><partyType>"+sPartyType+"</partyType></partyDetail></partyDetails><shipmentDetails><fromPlace>" + sFromPlace + "</fromPlace><toPlace>" + sToPlace + "</toPlace><lastShipmentDate>" + sLastShipmentDate + "</lastShipmentDate><portOfDischarge>" + sPortOfDischarge + "</portOfDischarge><portOfLoading>" + sPortOfLoading + "</portOfLoading></shipmentDetails><goodsDetails><goodsDescription>" + sGoodsDescription + "</goodsDescription></goodsDetails><branchCode>"+sBranchCreateCode+"</branchCode><wmsDetails><workItemNumber>" + sWIName + "</workItemNumber></wmsDetails></modTradeLCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";
								callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeLCContract_Oper</operationType><correlationID>342343</correlationID><modTradeLCContractReq><requestType>A</requestType><operationCode>" + sOperationCode + "</operationCode><contractAmount>" + sContractAmount + "</contractAmount><negativeTolerance>" + sNegativeTolerance + "</negativeTolerance><positiveTolerance>" + sPositiveTolerance + "</positiveTolerance><expiryDate>" + sExpiryDate + "</expiryDate><productCode>" + sProductCode + "</productCode><contractRefNumber>" + sContractRefNumber + "</contractRefNumber><partyDetails><partyDetail><partyType>"+sPartyType+"</partyType></partyDetail></partyDetails><shipmentDetails><fromPlace>" + sFromPlace + "</fromPlace><toPlace>" + sToPlace + "</toPlace><lastShipmentDate>" + sLastShipmentDate + "</lastShipmentDate><portOfDischarge>" + sPortOfDischarge + "</portOfDischarge><portOfLoading>" + sPortOfLoading + "</portOfLoading><partialShipmentAllowed></partialShipmentAllowed><transshipmentAllowed></transshipmentAllowed></shipmentDetails><drafts><draft><draftTenor></draftTenor><creditDaysFrom></creditDaysFrom><drawee></drawee><draftAmount></draftAmount></draft></drafts><goodsDetails><goodsDescription>" + sGoodsDescription + "</goodsDescription></goodsDetails><branchCode>"+sBranchCreateCode+"</branchCode><wmsDetails><workItemNumber>" + sWIName + "</workItemNumber></wmsDetails><placeOfExpiry></placeOfExpiry><settlementMonth></settlementMonth><paymentDate></paymentDate><mayConfirm>N</mayConfirm><requestedConfirmationParty></requestedConfirmationParty><chargesFromBenef></chargesFromBenef><presentationPeriod></presentationPeriod><presentationNarrative></presentationNarrative><expiryConditionOrEvent>" + sFinExpiryCond + "</expiryConditionOrEvent><expiryType>" + sFinExpiryType + "</expiryType><counterGuaranteeExpiryDate>" + sFinCntrGteeExpDate + "</counterGuaranteeExpiryDate><termsAndCondDtls><termsAndCondDtl><type>Local Guarantee</type><description>"+ sFinOtherAmendment +"</description></termsAndCondDtl></termsAndCondDtls></modTradeLCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";
								
								WriteToLog_showpage("Y","modTradeLCContract_Oper sOutput: "+ callXML);
								sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sCallName +"',"+ convertToPlainString(callXML) +",'"+ REFNO +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","modTradeLCContract_Oper callXML TFO_DJ_INTEGRATION_CALLS_DTLS: "+ sQuery + " *****************************");	
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);						
								WriteToLog_showpage("Y"," insert modTradeLCContract_Oper sOutput: "+ sOutput);											
								
								sOutput = socket.connectToSocket(callXML);
								WriteToLog_showpage("Y","modTradeLCContract_Oper callXML sOutput"+ sOutput);
								String queryUpdateXML = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'" + sOutput + "'","WI_NAME = '"+ sWIName +"'  and REF_NUM = '"+REFNO+"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update sOutputUpdateXML input: "+ queryUpdateXML);
								String sOutputUpdateXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",queryUpdateXML);
								WriteToLog_showpage("Y","update sOutputUpdateXML output: " + sOutputUpdateXML);
								xp = new XMLParser(sOutput);
								returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
								String nextValue ="";
								if(returnCode == 0){
									sErrorDescription = xp.getValueOf("errorDescription");
									sResponse = "Success";
									sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
									WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
									sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
									WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
									out.println(returnCode+"#"+sResponse+"#"+sErrorDescription);
								} else {
									sErrorDescription = xp.getValueOf("errorDescription");
									sResponse = "Failure";
								//	sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
									WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
									sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
									out.println("1#"+sResponse+"#"+sErrorDescription);
									//out.println("0#"+"Success"+"#"+sErrorDescription);
								}
							}
						}else if("ILCB".equalsIgnoreCase(sRequestCategoryCode) && "ILCB_AC".equalsIgnoreCase(sRequestTypeCode))
						{
							  sContractRefNumber = sOutput.substring(sOutput.indexOf("<TRANSACTION_REFERENCE>")+"<TRANSACTION_REFERENCE>".length(),sOutput.indexOf("</TRANSACTION_REFERENCE>"));
							WriteToLog_showpage("Y","sContractRefNumber: " + sContractRefNumber);
							
							sOperationCode = sOutput.substring(sOutput.indexOf("<OPERATION_CODE>")+"<OPERATION_CODE>".length(),sOutput.indexOf("</OPERATION_CODE>"));
							WriteToLog_showpage("Y","sOperationCode: " + sOperationCode);
							
							
							sMaturityDate=sOutput.substring(sOutput.indexOf("<INF_MATURITY_DATE>")+"<INF_MATURITY_DATE>".length(),sOutput.indexOf("</INF_MATURITY_DATE>"));
							
							//sStage = sOutput.substring(sOutput.indexOf("<BILL_STAGE>")+"<BILL_STAGE>".length(),sOutput.indexOf("</BILL_STAGE>"));
							
							sGoodsDescription = sOutput.substring(sOutput.indexOf("<LC_GOODS_DESC>")+"<LC_GOODS_DESC>".length(),sOutput.indexOf("</LC_GOODS_DESC>"));

							
							 sRelationshipType = sOutput.substring(sOutput.indexOf("<RELATIONSHIP_TYPE>")+"<RELATIONSHIP_TYPE>".length(),sOutput.indexOf("</RELATIONSHIP_TYPE>"));
							sBranchCreateCode = fetchBranchCode(sRelationshipType , sWIName);
							String sFinalMaturityDate = calculateFinalDate(sMaturityDate,sFCUBSCurrentDate);
							WriteToLog_showpage("Y","sBranchCreateCode: " + sBranchCreateCode);
							sStage="FIN";
							sLimitTrackFlag="Y";
							
							if(sCallName.equalsIgnoreCase("modTradeBCContract_Oper")){
							callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeBCContract_Oper</operationType><correlationID>342343</correlationID><modTradeBCContractReq><requestType>A</requestType><contractRefNumber>"+sContractRefNumber+"</contractRefNumber><operation>" + sOperationCode + "</operation><stage>"+sStage+"</stage><maturityDate>"+sFinalMaturityDate+"</maturityDate><limitTracking>" + sLimitTrackFlag +"</limitTracking>"+setPartyLimit(sWIName,sRequestCategoryCode,sLimitTrackFlag)+"<interestfromDate>"+sFCUBSCurrentDate+"</interestfromDate><interestToDate>"+sFinalMaturityDate+"</interestToDate><acceptComFromDate>"+sFCUBSCurrentDate+"</acceptComFromDate><acceptComToDate>"+sFinalMaturityDate+"</acceptComToDate><internalRemarks>"+setProTradeRemark(sWIName)+
							"</internalRemarks><branchCode>" + sBranchCreateCode + "</branchCode><acceptanceMessageDate>"+sFCUBSCurrentDate+"</acceptanceMessageDate>"+setDiscreprancyDetail(sWIName,sRequestTypeCode,sFCUBSCurrentDate)+"<workItemNumber>" + sWIName + "</workItemNumber><goodsDetails><goodsCode>OTHERS</goodsCode><goodsDescription>" + sGoodsDescription + "</goodsDescription></goodsDetails></modTradeBCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"
							+ sSessionId +"</SessionId><APWebService_Input>";
				
							WriteToLog_showpage("Y","modTradeBCContract_Oper sOutput: "+ callXML);
								sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sCallName +"',"+ convertToPlainString(callXML) +",'"+ REFNO +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","modTradeBCContract_Oper callXML TFO_DJ_INTEGRATION_CALLS_DTLS: "+ sQuery + " *****************************");	
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);						
								WriteToLog_showpage("Y"," insert modTradeBCContract_Oper sOutput: "+ sOutput);											
								
								sOutput = socket.connectToSocket(callXML);
								WriteToLog_showpage("Y","modTradeBCContract_Oper callXML sOutput"+ sOutput);
								String queryUpdateXML = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'" + sOutput + "'","WI_NAME = '"+ sWIName +"'  and REF_NUM = '"+REFNO+"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update sOutputUpdateXML input: "+ queryUpdateXML);
								String sOutputUpdateXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",queryUpdateXML);
								WriteToLog_showpage("Y","update sOutputUpdateXML output: " + sOutputUpdateXML);
								xp = new XMLParser(sOutput);
								returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
								String nextValue ="";
								if(returnCode == 0){
									sErrorDescription = xp.getValueOf("errorDescription");
									sResponse = "Success";
									sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
									WriteToLog_showpage("Y","update integration query modTradeBCContract_Oper: "+ sQuery);
									sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
									WriteToLog_showpage("Y","update integration query modTradeBCContract_Oper: " + sOutput);
									out.println(returnCode+"#"+sResponse+"#"+sErrorDescription);
								} else {
									sErrorDescription = xp.getValueOf("errorDescription");
									sResponse = "Failure";
									sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
									WriteToLog_showpage("Y","update integration query modTradeBCContract_Oper: "+ sQuery);
									sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
									WriteToLog_showpage("Y","update integration query modTradeBCContract_Oper: " + sOutput);
									out.println("1#"+sResponse+"#"+sErrorDescription);
								}
							
							}
						}
						else if("ELCB".equalsIgnoreCase(sRequestCategoryCode) && "ELCB_AC".equalsIgnoreCase(sRequestTypeCode))
						{
							sContractRefNumber = sOutput.substring(sOutput.indexOf("<TRANSACTION_REFERENCE>")+"<TRANSACTION_REFERENCE>".length(),sOutput.indexOf("</TRANSACTION_REFERENCE>"));
							WriteToLog_showpage("Y","sContractRefNumber: " + sContractRefNumber);
							
							sOperationCode = sOutput.substring(sOutput.indexOf("<OPERATION_CODE>")+"<OPERATION_CODE>".length(),sOutput.indexOf("</OPERATION_CODE>"));
							WriteToLog_showpage("Y","sOperationCode: " + sOperationCode);
							
							sMaturityDate=sOutput.substring(sOutput.indexOf("<INF_MATURITY_DATE>")+"<INF_MATURITY_DATE>".length(),sOutput.indexOf("</INF_MATURITY_DATE>"));
							sGoodsDescription = sOutput.substring(sOutput.indexOf("<LC_GOODS_DESC>")+"<LC_GOODS_DESC>".length(),sOutput.indexOf("</LC_GOODS_DESC>"));

						
							
							 sRelationshipType = sOutput.substring(sOutput.indexOf("<RELATIONSHIP_TYPE>")+"<RELATIONSHIP_TYPE>".length(),sOutput.indexOf("</RELATIONSHIP_TYPE>"));
							sBranchCreateCode = fetchBranchCode(sRelationshipType , sWIName);
							String sFinalMaturityDate = calculateFinalDate(sMaturityDate,sFCUBSCurrentDate);
							WriteToLog_showpage("Y","sBranchCreateCode: " + sBranchCreateCode);
							sStage="FIN";
							sLimitTrackFlag=getLimitTrackingFlag(sWIName);
							sCode="";
							sDescription="";
							sResolved="";
							sResolvedDate="";
							sReceivedDate="";
							if(sCallName.equalsIgnoreCase("modTradeBCContract_Oper")){
							callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeBCContract_Oper</operationType><correlationID>342343</correlationID><modTradeBCContractReq><requestType>A</requestType><contractRefNumber>"+sContractRefNumber+"</contractRefNumber><operation>" + sOperationCode + "</operation><stage>"+sStage+"</stage><maturityDate>"+sFinalMaturityDate+"</maturityDate><limitTracking>" + sLimitTrackFlag +"</limitTracking>"+setPartyLimit(sWIName,sRequestCategoryCode,sLimitTrackFlag)+"<interestfromDate>"+sFCUBSCurrentDate+"</interestfromDate><interestToDate>"+sFinalMaturityDate+"</interestToDate><acceptComFromDate>"+"</acceptComFromDate><acceptComToDate></acceptComToDate><internalRemarks>"+setProTradeRemark(sWIName)+
							"</internalRemarks><branchCode>" + sBranchCreateCode + "</branchCode><acceptanceMessageDate>"+sFCUBSCurrentDate+"</acceptanceMessageDate><contractDiscs><contractDisc><code>"+sCode+"</code><description>"+sDescription+"</description><resolved>"+sResolved+"</resolved><resolvedDate>"+sResolvedDate+"</resolvedDate><receivedDate>"+sReceivedDate+"</receivedDate></contractDisc></contractDiscs>"+"<workItemNumber>" + sWIName + "</workItemNumber><goodsDetails><goodsCode>OTHERS</goodsCode><goodsDescription>" + sGoodsDescription + "</goodsDescription></goodsDetails></modTradeBCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"
							+ sSessionId +"</SessionId><APWebService_Input>";
				
							WriteToLog_showpage("Y","modTradeBCContract_Oper sOutput: "+ callXML);
								sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sCallName +"',"+ convertToPlainString(callXML) +",'"+ REFNO +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","modTradeBCContract_Oper callXML TFO_DJ_INTEGRATION_CALLS_DTLS: "+ sQuery + " *****************************");	
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);						
								WriteToLog_showpage("Y"," insert modTradeBCContract_Oper sOutput: "+ sOutput);											
								
								sOutput = socket.connectToSocket(callXML);
								WriteToLog_showpage("Y","modTradeBCContract_Oper callXML sOutput"+ sOutput);
								String queryUpdateXML = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'" + sOutput + "'","WI_NAME = '"+ sWIName +"'  and REF_NUM = '"+REFNO+"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update sOutputUpdateXML input: "+ queryUpdateXML);
								String sOutputUpdateXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",queryUpdateXML);
								WriteToLog_showpage("Y","update sOutputUpdateXML output: " + sOutputUpdateXML);
								xp = new XMLParser(sOutput);
								returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
								String nextValue ="";
								if(returnCode == 0){
									sErrorDescription = xp.getValueOf("errorDescription");
									sResponse = "Success";
									sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
									WriteToLog_showpage("Y","update integration query modTradeBCContract_Oper: "+ sQuery);
									sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
									WriteToLog_showpage("Y","update integration query modTradeBCContract_Oper: " + sOutput);
									out.println(returnCode+"#"+sResponse+"#"+sErrorDescription);
								} else {
									sErrorDescription = xp.getValueOf("errorDescription");
									sResponse = "Failure";
									//sResponse = "Success";
									sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
									WriteToLog_showpage("Y","update integration query modTradeBCContract_Oper: "+ sQuery);
									sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
									WriteToLog_showpage("Y","update integration query modTradeBCContract_Oper: " + sOutput);
									out.println("1#"+sResponse+"#"+sErrorDescription);
									//out.println("0#"+sResponse+"#"+sErrorDescription);
								}
							
							}
						}
						else if("IC".equalsIgnoreCase(sRequestCategoryCode) && "IC_AC".equalsIgnoreCase(sRequestTypeCode))
						{
							
							sContractRefNumber = sOutput.substring(sOutput.indexOf("<TRANSACTION_REFERENCE>")+"<TRANSACTION_REFERENCE>".length(),sOutput.indexOf("</TRANSACTION_REFERENCE>"));
							WriteToLog_showpage("Y","sContractRefNumber: " + sContractRefNumber);
							
							sOperationCode = sOutput.substring(sOutput.indexOf("<OPERATION_CODE>")+"<OPERATION_CODE>".length(),sOutput.indexOf("</OPERATION_CODE>"));
							WriteToLog_showpage("Y","sOperationCode: " + sOperationCode);
							
							sMaturityDate=sOutput.substring(sOutput.indexOf("<INF_MATURITY_DATE>")+"<INF_MATURITY_DATE>".length(),sOutput.indexOf("</INF_MATURITY_DATE>"));
							
							sProductCode = sOutput.substring(sOutput.indexOf("<PRODUCT_TYPE>")+"<PRODUCT_TYPE>".length(),sOutput.indexOf("</PRODUCT_TYPE>"));
							WriteToLog_showpage("Y","sProductCode: " + sProductCode);
							sGoodsDescription = sOutput.substring(sOutput.indexOf("<LC_GOODS_DESC>")+"<LC_GOODS_DESC>".length(),sOutput.indexOf("</LC_GOODS_DESC>"));

							 sRelationshipType = sOutput.substring(sOutput.indexOf("<RELATIONSHIP_TYPE>")+"<RELATIONSHIP_TYPE>".length(),sOutput.indexOf("</RELATIONSHIP_TYPE>"));
							sBranchCreateCode = fetchBranchCode(sRelationshipType , sWIName);
							String sFinalMaturityDate = calculateFinalDate(sMaturityDate,sFCUBSCurrentDate);
							WriteToLog_showpage("Y","sBranchCreateCode: " + sBranchCreateCode);
							sStage="FIN";
							
							if("T3U5".equalsIgnoreCase(sProductCode) || "I3U5".equalsIgnoreCase(sProductCode))
							{
								sLimitTrackFlag="Y";
							}else{
								sLimitTrackFlag="N";
							}
							 SimpleDateFormat sdformatter = new SimpleDateFormat("dd/MM/yyyy");
							  Date currentDate = sdformatter.parse(sFCUBSCurrentDate);
							  Date maturityDate = sdformatter.parse(sMaturityDate);
							  WriteToLog_showpage("Y","currentDate: " + sdformatter.format(currentDate));
							  WriteToLog_showpage("Y","maturityDT: " + sdformatter.format(maturityDate));
							 
							if(currentDate.compareTo(maturityDate) > 0) {
								WriteToLog_showpage("Y","current date is greater ");
							 sCurrentDateValue= "";
							 sMaturityDateValue=""; 
							  }else{
								  sCurrentDateValue= sFCUBSCurrentDate;
							 sMaturityDateValue=sMaturityDate;  
							  }
								
							if(sCallName.equalsIgnoreCase("modTradeBCContract_Oper")){
							callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeBCContract_Oper</operationType><correlationID>342343</correlationID><modTradeBCContractReq><requestType>A</requestType><contractRefNumber>"+sContractRefNumber+"</contractRefNumber><operation>" + sOperationCode + "</operation><stage>"+sStage+"</stage><maturityDate>"+sFinalMaturityDate+"</maturityDate><limitTracking>" + sLimitTrackFlag +"</limitTracking>"+setPartyLimit(sWIName,sRequestCategoryCode,sLimitTrackFlag)+"<interestfromDate>"+sFCUBSCurrentDate+"</interestfromDate><interestToDate>"+sFinalMaturityDate+"</interestToDate><internalRemarks>"+setProTradeRemark(sWIName)+"</internalRemarks><branchCode>" + sBranchCreateCode + "</branchCode><acceptComFromDate></acceptComFromDate><acceptComToDate></acceptComToDate><acceptanceMessageDate>"+sFCUBSCurrentDate+"</acceptanceMessageDate><contractDiscs><contractDisc><code></code><description></description><resolved></resolved><resolvedDate></resolvedDate><receivedDate></receivedDate></contractDisc></contractDiscs><workItemNumber>" + sWIName + "</workItemNumber><goodsDetails><goodsCode>OTHERS</goodsCode><goodsDescription>" + sGoodsDescription + "</goodsDescription></goodsDetails></modTradeBCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";
							
							WriteToLog_showpage("Y","modTradeBCContract_Oper sOutput: "+ callXML);
								sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sCallName +"',"+ convertToPlainString(callXML) +",'"+ REFNO +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","modTradeBCContract_Oper callXML TFO_DJ_INTEGRATION_CALLS_DTLS: "+ sQuery + " *****************************");	
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);						
								WriteToLog_showpage("Y"," insert modTradeBCContract_Oper sOutput: "+ sOutput);											
								
								sOutput = socket.connectToSocket(callXML);
								WriteToLog_showpage("Y","modTradeBCContract_Oper callXML sOutput"+ sOutput);
								String queryUpdateXML = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'" + sOutput + "'","WI_NAME = '"+ sWIName +"'  and REF_NUM = '"+REFNO+"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update sOutputUpdateXML input: "+ queryUpdateXML);
								String sOutputUpdateXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",queryUpdateXML);
								WriteToLog_showpage("Y","update sOutputUpdateXML output: " + sOutputUpdateXML);
								xp = new XMLParser(sOutput);
								returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
								String nextValue ="";
								if(returnCode == 0){
									sErrorDescription = xp.getValueOf("errorDescription");
									sResponse = "Success";
									sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
									WriteToLog_showpage("Y","update integration query modTradeBCContract_Oper: "+ sQuery);
									sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
									WriteToLog_showpage("Y","update integration query modTradeBCContract_Oper: " + sOutput);
									out.println(returnCode+"#"+sResponse+"#"+sErrorDescription);
								} else {
									sErrorDescription = xp.getValueOf("errorDescription");
									sResponse = "Failure";
									sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
									WriteToLog_showpage("Y","update integration query modTradeBCContract_Oper: "+ sQuery);
									sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
									WriteToLog_showpage("Y","update integration query modTradeBCContract_Oper: " + sOutput);
									out.println("1#"+sResponse+"#"+sErrorDescription);
								}
							}
						}
						else if("EC".equalsIgnoreCase(sRequestCategoryCode) && "EC_AC".equalsIgnoreCase(sRequestTypeCode))
						{
							sContractRefNumber = sOutput.substring(sOutput.indexOf("<TRANSACTION_REFERENCE>")+"<TRANSACTION_REFERENCE>".length(),sOutput.indexOf("</TRANSACTION_REFERENCE>"));
							WriteToLog_showpage("Y","sContractRefNumber: " + sContractRefNumber);
							
							sOperationCode = sOutput.substring(sOutput.indexOf("<OPERATION_CODE>")+"<OPERATION_CODE>".length(),sOutput.indexOf("</OPERATION_CODE>"));
							WriteToLog_showpage("Y","sOperationCode: " + sOperationCode);
							
							sMaturityDate=sOutput.substring(sOutput.indexOf("<INF_MATURITY_DATE>")+"<INF_MATURITY_DATE>".length(),sOutput.indexOf("</INF_MATURITY_DATE>"));
							sGoodsDescription = sOutput.substring(sOutput.indexOf("<LC_GOODS_DESC>")+"<LC_GOODS_DESC>".length(),sOutput.indexOf("</LC_GOODS_DESC>"));

							 sRelationshipType = sOutput.substring(sOutput.indexOf("<RELATIONSHIP_TYPE>")+"<RELATIONSHIP_TYPE>".length(),sOutput.indexOf("</RELATIONSHIP_TYPE>"));
							sBranchCreateCode = fetchBranchCode(sRelationshipType , sWIName);
							String sFinalMaturityDate = calculateFinalDate(sMaturityDate,sFCUBSCurrentDate);
							WriteToLog_showpage("Y","sBranchCreateCode: " + sBranchCreateCode);
							sStage="FIN";
							sLimitTrackFlag=getLimitTrackingFlag(sWIName);
							
							SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");
							  Date currentDT = sdformat.parse(sFCUBSCurrentDate);
							  Date maturityDT = sdformat.parse(sMaturityDate);
							  WriteToLog_showpage("Y","currentDT: " + sdformat.format(currentDT));
							  WriteToLog_showpage("Y","maturityDT: " + sdformat.format(maturityDT));
							 
							if(currentDT.compareTo(maturityDT) > 0) {
								WriteToLog_showpage("Y","current date is greater ");
							 sCurrentDateValue= "";
							 sMaturityDateValue=""; 
							  }else{
								  sCurrentDateValue= sFCUBSCurrentDate;
							 sMaturityDateValue=sMaturityDate;  
							  }
								
							if(sCallName.equalsIgnoreCase("modTradeBCContract_Oper"))
							{
							callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeBCContract_Oper</operationType><correlationID>342343</correlationID><modTradeBCContractReq><requestType>A</requestType><contractRefNumber>"+sContractRefNumber+"</contractRefNumber><operation>" + sOperationCode + "</operation><stage>"+sStage+"</stage><maturityDate>"+sFinalMaturityDate+"</maturityDate><limitTracking>" + sLimitTrackFlag +"</limitTracking>"+setPartyLimit(sWIName,sRequestCategoryCode,sLimitTrackFlag)+"<interestfromDate>"+sFCUBSCurrentDate+"</interestfromDate><interestToDate>"+sFinalMaturityDate+"</interestToDate><internalRemarks>"+setProTradeRemark(sWIName)+"</internalRemarks><branchCode>" + sBranchCreateCode + "</branchCode><acceptanceMessageDate>"+sFCUBSCurrentDate+"</acceptanceMessageDate>"+"<contractDiscs><contractDisc><code></code><description></description><resolved></resolved><resolvedDate>"+
							"</resolvedDate><receivedDate></receivedDate></contractDisc></contractDiscs>"+"<workItemNumber>" + sWIName + "</workItemNumber><goodsDetails><goodsCode>OTHERS</goodsCode><goodsDescription>" + sGoodsDescription + "</goodsDescription></goodsDetails></modTradeBCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";
							
							WriteToLog_showpage("Y","modTradeBCContract_Oper sOutput: "+ callXML);
								sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sCallName +"',"+ convertToPlainString(callXML) +",'"+ REFNO +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","modTradeBCContract_Oper callXML TFO_DJ_INTEGRATION_CALLS_DTLS: "+ sQuery + " *****************************");	
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);						
								WriteToLog_showpage("Y"," insert modTradeBCContract_Oper sOutput: "+ sOutput);											
								
								sOutput = socket.connectToSocket(callXML);
								WriteToLog_showpage("Y","modTradeBCContract_Oper callXML sOutput"+ sOutput);
								String queryUpdateXML = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'" + sOutput + "'","WI_NAME = '"+ sWIName +"'  and REF_NUM = '"+REFNO+"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update sOutputUpdateXML input: "+ queryUpdateXML);
								String sOutputUpdateXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",queryUpdateXML);
								WriteToLog_showpage("Y","update sOutputUpdateXML output: " + sOutputUpdateXML);
								xp = new XMLParser(sOutput);
								returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
								String nextValue ="";
								if(returnCode == 0){
									sErrorDescription = xp.getValueOf("errorDescription");
									sResponse = "Success";
									sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
									WriteToLog_showpage("Y","update integration query modTradeBCContract_Oper: "+ sQuery);
									sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
									WriteToLog_showpage("Y","update integration query modTradeBCContract_Oper: " + sOutput);
									out.println(returnCode+"#"+sResponse+"#"+sErrorDescription);
								} else {
									sErrorDescription = xp.getValueOf("errorDescription");
									sResponse = "Failure";
									sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
									WriteToLog_showpage("Y","update integration query modTradeBCContract_Oper: "+ sQuery);
									sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
									WriteToLog_showpage("Y","update integration query modTradeBCContract_Oper: " + sOutput);
									out.println("1#"+sResponse+"#"+sErrorDescription);
								}
							
							}
						}else if("ELCB".equalsIgnoreCase(sRequestCategoryCode) && "ELCB_AM".equalsIgnoreCase(sRequestTypeCode))
						{
							sContractRefNumber = sOutput.substring(sOutput.indexOf("<TRANSACTION_REFERENCE>")+"<TRANSACTION_REFERENCE>".length(),sOutput.indexOf("</TRANSACTION_REFERENCE>"));
							WriteToLog_showpage("Y","sContractRefNumber: " + sContractRefNumber);
							
							sOperationCode = sOutput.substring(sOutput.indexOf("<OPERATION_CODE>")+"<OPERATION_CODE>".length(),sOutput.indexOf("</OPERATION_CODE>"));
							WriteToLog_showpage("Y","sOperationCode: " + sOperationCode);
							
							sMaturityDate=sOutput.substring(sOutput.indexOf("<INF_MATURITY_DATE>")+"<INF_MATURITY_DATE>".length(),sOutput.indexOf("</INF_MATURITY_DATE>"));
							
							 sRelationshipType = sOutput.substring(sOutput.indexOf("<RELATIONSHIP_TYPE>")+"<RELATIONSHIP_TYPE>".length(),sOutput.indexOf("</RELATIONSHIP_TYPE>"));
							 sProTradeSettlementInst=sOutput.substring(sOutput.indexOf("<PRO_TRADE_SETTLEMENT_INST>")+"<PRO_TRADE_SETTLEMENT_INST>".length(),sOutput.indexOf("</PRO_TRADE_SETTLEMENT_INST>")); 
							 sGoodsDescription=sOutput.substring(sOutput.indexOf("<LC_GOODS_DESC>")+"<LC_GOODS_DESC>".length(),sOutput.indexOf("</LC_GOODS_DESC>"));
							 String sAccMessageDate=sOutput.substring(sOutput.indexOf("<ACCEPTANCE_MSG_DATE>")+"<ACCEPTANCE_MSG_DATE>".length(),sOutput.indexOf("</ACCEPTANCE_MSG_DATE>"));
							sBranchCreateCode = fetchBranchCode(sRelationshipType , sWIName);
							String sFinalMaturityDate = calculateFinalDate(sMaturityDate,sFCUBSCurrentDate);
							WriteToLog_showpage("Y","sBranchCreateCode: " + sBranchCreateCode);
							sStage="FIN";
							sLimitTrackFlag=getLimitTrackingFlag(sWIName);
							
							SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");
							  Date currentDT = sdformat.parse(sFCUBSCurrentDate);
							  Date maturityDT = sdformat.parse(sMaturityDate);
							  WriteToLog_showpage("Y","currentDT: " + sdformat.format(currentDT));
							  WriteToLog_showpage("Y","maturityDT: " + sdformat.format(maturityDT));
							 
							if(currentDT.compareTo(maturityDT) > 0) {
								WriteToLog_showpage("Y","current date is greater ");
							 sCurrentDateValue= "";
							 sMaturityDateValue=""; 
							  }else{
								  sCurrentDateValue= sFCUBSCurrentDate;
							 sMaturityDateValue=sMaturityDate;  
							  }
							if(!(null==sProTradeSettlementInst ||"".equalsIgnoreCase(sProTradeSettlementInst))){
								sGoodsDescription=sProTradeSettlementInst;
							}else if((null==sProTradeSettlementInst ||"".equalsIgnoreCase(sProTradeSettlementInst))&&(null==sGoodsDescription ||"".equalsIgnoreCase(sGoodsDescription))){
								sGoodsDescription="NA";
							}
							sRiskParticipation=checkPartyTypeContract(sWIName);
							if(sCallName.equalsIgnoreCase("modTradeBCContract_Oper"))
							{
							callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeBCContract_Oper</operationType><correlationID>342343</correlationID><modTradeBCContractReq><requestType>A</requestType><contractRefNumber>"+sContractRefNumber+"</contractRefNumber><operation>" + sOperationCode + "</operation><stage>"+sStage+"</stage><maturityDate>"+sFinalMaturityDate+"</maturityDate><limitTracking>" + sLimitTrackFlag +"</limitTracking>"+setPartyLimit(sWIName,sRequestCategoryCode,sLimitTrackFlag)+"<interestfromDate>"+sFCUBSCurrentDate+"</interestfromDate><interestToDate>"+sFinalMaturityDate+"</interestToDate><internalRemarks></internalRemarks><branchCode>" + sBranchCreateCode + "</branchCode><acceptanceMessageDate>"+sAccMessageDate+"</acceptanceMessageDate><riskParticipation>"+sRiskParticipation+"</riskParticipation><"+"<contractDiscs><contractDisc><code></code><description></description><resolved></resolved><resolvedDate>"+
							"</resolvedDate><receivedDate></receivedDate></contractDisc></contractDiscs><acceptComFromDate></acceptComFromDate><acceptComToDate></acceptComToDate><goodsCode>OTHERS</goodsCode><goodsDescription>" + sGoodsDescription + "</goodsDescription>"+"<workItemNumber>" + sWIName + "</workItemNumber></modTradeBCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";
							
							WriteToLog_showpage("Y","modTradeBCContract_Oper sOutput: "+ callXML);
								sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sCallName +"',"+ convertToPlainString(callXML) +",'"+ REFNO +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","modTradeBCContract_Oper callXML TFO_DJ_INTEGRATION_CALLS_DTLS: "+ sQuery + " *****************************");	
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);						
								WriteToLog_showpage("Y"," insert modTradeBCContract_Oper sOutput: "+ sOutput);											
								
								sOutput = socket.connectToSocket(callXML);
								WriteToLog_showpage("Y","modTradeBCContract_Oper callXML sOutput"+ sOutput);
								String queryUpdateXML = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'" + sOutput + "'","WI_NAME = '"+ sWIName +"'  and REF_NUM = '"+REFNO+"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update sOutputUpdateXML input: "+ queryUpdateXML);
								String sOutputUpdateXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",queryUpdateXML);
								WriteToLog_showpage("Y","update sOutputUpdateXML output: " + sOutputUpdateXML);
								xp = new XMLParser(sOutput);
								returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
								String nextValue ="";
								if(returnCode == 0){
									sErrorDescription = xp.getValueOf("errorDescription");
									sResponse = "Success";
									sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
									WriteToLog_showpage("Y","update integration query modTradeBCContract_Oper: "+ sQuery);
									sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
									WriteToLog_showpage("Y","update integration query modTradeBCContract_Oper: " + sOutput);
									out.println(returnCode+"#"+sResponse+"#"+sErrorDescription);
								} else {
									sErrorDescription = xp.getValueOf("errorDescription");
									sResponse = "Failure";
									sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
									WriteToLog_showpage("Y","update integration query modTradeBCContract_Oper: "+ sQuery);
									sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
									WriteToLog_showpage("Y","update integration query modTradeBCContract_Oper: " + sOutput);
									out.println("1#"+sResponse+"#"+sErrorDescription);
								}
							
							}
						}
						else if("ELCB".equalsIgnoreCase(sRequestCategoryCode) && "ELCB_DISC".equalsIgnoreCase(sRequestTypeCode))
						{
							sContractRefNumber = sOutput.substring(sOutput.indexOf("<TRANSACTION_REFERENCE>")+"<TRANSACTION_REFERENCE>".length(),sOutput.indexOf("</TRANSACTION_REFERENCE>"));
							WriteToLog_showpage("Y","sContractRefNumber: " + sContractRefNumber);
							
							sOperationCode = sOutput.substring(sOutput.indexOf("<OPERATION_CODE>")+"<OPERATION_CODE>".length(),sOutput.indexOf("</OPERATION_CODE>"));
							WriteToLog_showpage("Y","sOperationCode: " + sOperationCode);
							
							sMaturityDate=sOutput.substring(sOutput.indexOf("<INF_MATURITY_DATE>")+"<INF_MATURITY_DATE>".length(),sOutput.indexOf("</INF_MATURITY_DATE>"));
							
							 sRelationshipType = sOutput.substring(sOutput.indexOf("<RELATIONSHIP_TYPE>")+"<RELATIONSHIP_TYPE>".length(),sOutput.indexOf("</RELATIONSHIP_TYPE>"));
							 sProTradeSettlementInst=sOutput.substring(sOutput.indexOf("<PRO_TRADE_SETTLEMENT_INST>")+"<PRO_TRADE_SETTLEMENT_INST>".length(),sOutput.indexOf("</PRO_TRADE_SETTLEMENT_INST>")); 
							 sGoodsDescription=sOutput.substring(sOutput.indexOf("<LC_GOODS_DESC>")+"<LC_GOODS_DESC>".length(),sOutput.indexOf("</LC_GOODS_DESC>"));
							 String sAccMessageDate=sOutput.substring(sOutput.indexOf("<ACCEPTANCE_MSG_DATE>")+"<ACCEPTANCE_MSG_DATE>".length(),sOutput.indexOf("</ACCEPTANCE_MSG_DATE>"));
							sBranchCreateCode = fetchBranchCode(sRelationshipType , sWIName);
							String sFinalMaturityDate = calculateFinalDate(sMaturityDate,sFCUBSCurrentDate);
							WriteToLog_showpage("Y","sBranchCreateCode: " + sBranchCreateCode);
							sStage="FIN";
							sLimitTrackFlag=getLimitTrackingFlag(sWIName);
							
							SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");
							  Date currentDT = sdformat.parse(sFCUBSCurrentDate);
							  Date maturityDT = sdformat.parse(sMaturityDate);
							  WriteToLog_showpage("Y","currentDT: " + sdformat.format(currentDT));
							  WriteToLog_showpage("Y","maturityDT: " + sdformat.format(maturityDT));
							 
							if(currentDT.compareTo(maturityDT) > 0) {
								WriteToLog_showpage("Y","current date is greater ");
							 sCurrentDateValue= "";
							 sMaturityDateValue=""; 
							  }else{
								  sCurrentDateValue= sFCUBSCurrentDate;
							 sMaturityDateValue=sMaturityDate;  
							  }
							if(!(null==sProTradeSettlementInst ||"".equalsIgnoreCase(sProTradeSettlementInst))){
								sGoodsDescription=sProTradeSettlementInst;
							}else if((null==sProTradeSettlementInst ||"".equalsIgnoreCase(sProTradeSettlementInst))&&(null==sGoodsDescription ||"".equalsIgnoreCase(sGoodsDescription))){
								sGoodsDescription="NA";
							}
							sRiskParticipation=checkPartyTypeContract(sWIName);
							if(sCallName.equalsIgnoreCase("modTradeBCContract_Oper"))
							{
							callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeBCContract_Oper</operationType><correlationID>342343</correlationID><modTradeBCContractReq><requestType>A</requestType><contractRefNumber>"+sContractRefNumber+"</contractRefNumber><operation>" + sOperationCode + "</operation><stage>"+sStage+"</stage><maturityDate>"+sFinalMaturityDate+"</maturityDate><limitTracking>" + sLimitTrackFlag +"</limitTracking>"+setPartyLimit(sWIName,sRequestCategoryCode,sLimitTrackFlag)+"<interestfromDate>"+sFCUBSCurrentDate+"</interestfromDate><interestToDate>"+sFinalMaturityDate+"</interestToDate><internalRemarks></internalRemarks><branchCode>" + sBranchCreateCode + "</branchCode><acceptanceMessageDate>"+sAccMessageDate+"</acceptanceMessageDate><riskParticipation>"+sRiskParticipation+"</riskParticipation><"+"<contractDiscs><contractDisc><code></code><description></description><resolved></resolved><resolvedDate>"+
							"</resolvedDate><receivedDate></receivedDate></contractDisc></contractDiscs><acceptComFromDate></acceptComFromDate><acceptComToDate></acceptComToDate><goodsCode>OTHERS</goodsCode><goodsDescription>" + sGoodsDescription + "</goodsDescription>"+"<workItemNumber>" + sWIName + "</workItemNumber></modTradeBCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";
							
							WriteToLog_showpage("Y","modTradeBCContract_Oper sOutput: "+ callXML);
								sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sCallName +"',"+ convertToPlainString(callXML) +",'"+ REFNO +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","modTradeBCContract_Oper callXML TFO_DJ_INTEGRATION_CALLS_DTLS: "+ sQuery + " *****************************");	
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);						
								WriteToLog_showpage("Y"," insert modTradeBCContract_Oper sOutput: "+ sOutput);											
								
								sOutput = socket.connectToSocket(callXML);
								WriteToLog_showpage("Y","modTradeBCContract_Oper callXML sOutput"+ sOutput);
								String queryUpdateXML = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'" + sOutput + "'","WI_NAME = '"+ sWIName +"'  and REF_NUM = '"+REFNO+"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update sOutputUpdateXML input: "+ queryUpdateXML);
								String sOutputUpdateXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",queryUpdateXML);
								WriteToLog_showpage("Y","update sOutputUpdateXML output: " + sOutputUpdateXML);
								xp = new XMLParser(sOutput);
								returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
								String nextValue ="";
								if(returnCode == 0){
									sErrorDescription = xp.getValueOf("errorDescription");
									sResponse = "Success";
									sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
									WriteToLog_showpage("Y","update integration query modTradeBCContract_Oper: "+ sQuery);
									sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
									WriteToLog_showpage("Y","update integration query modTradeBCContract_Oper: " + sOutput);
									out.println(returnCode+"#"+sResponse+"#"+sErrorDescription);
								} else {
									sErrorDescription = xp.getValueOf("errorDescription");
									sResponse = "Failure";
									sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
									WriteToLog_showpage("Y","update integration query modTradeBCContract_Oper: "+ sQuery);
									sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
									WriteToLog_showpage("Y","update integration query modTradeBCContract_Oper: " + sOutput);
									out.println("1#"+sResponse+"#"+sErrorDescription);
								}
							
							}
						}
							else if("EC".equalsIgnoreCase(sRequestCategoryCode) && "EC_DISC".equalsIgnoreCase(sRequestTypeCode))
						{
							sContractRefNumber = sOutput.substring(sOutput.indexOf("<TRANSACTION_REFERENCE>")+"<TRANSACTION_REFERENCE>".length(),sOutput.indexOf("</TRANSACTION_REFERENCE>"));
							WriteToLog_showpage("Y","sContractRefNumber: " + sContractRefNumber);
							
							sOperationCode = sOutput.substring(sOutput.indexOf("<OPERATION_CODE>")+"<OPERATION_CODE>".length(),sOutput.indexOf("</OPERATION_CODE>"));
							WriteToLog_showpage("Y","sOperationCode: " + sOperationCode);
							
							sMaturityDate=sOutput.substring(sOutput.indexOf("<INF_MATURITY_DATE>")+"<INF_MATURITY_DATE>".length(),sOutput.indexOf("</INF_MATURITY_DATE>"));
							
							 sRelationshipType = sOutput.substring(sOutput.indexOf("<RELATIONSHIP_TYPE>")+"<RELATIONSHIP_TYPE>".length(),sOutput.indexOf("</RELATIONSHIP_TYPE>"));
							 sProTradeSettlementInst=sOutput.substring(sOutput.indexOf("<PRO_TRADE_SETTLEMENT_INST>")+"<PRO_TRADE_SETTLEMENT_INST>".length(),sOutput.indexOf("</PRO_TRADE_SETTLEMENT_INST>")); 
							 sGoodsDescription=sOutput.substring(sOutput.indexOf("<LC_GOODS_DESC>")+"<LC_GOODS_DESC>".length(),sOutput.indexOf("</LC_GOODS_DESC>"));
							 String sAccMessageDate=sOutput.substring(sOutput.indexOf("<ACCEPTANCE_MSG_DATE>")+"<ACCEPTANCE_MSG_DATE>".length(),sOutput.indexOf("</ACCEPTANCE_MSG_DATE>"));
							sBranchCreateCode = fetchBranchCode(sRelationshipType , sWIName);
							String sFinalMaturityDate = calculateFinalDate(sMaturityDate,sFCUBSCurrentDate);
							WriteToLog_showpage("Y","sBranchCreateCode: " + sBranchCreateCode);
							sStage="FIN";
							sLimitTrackFlag=getLimitTrackingFlag(sWIName);
							
							SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");
							  Date currentDT = sdformat.parse(sFCUBSCurrentDate);
							  Date maturityDT = sdformat.parse(sMaturityDate);
							  WriteToLog_showpage("Y","currentDT: " + sdformat.format(currentDT));
							  WriteToLog_showpage("Y","maturityDT: " + sdformat.format(maturityDT));
							 
							if(currentDT.compareTo(maturityDT) > 0) {
								WriteToLog_showpage("Y","current date is greater ");
							 sCurrentDateValue= "";
							 sMaturityDateValue=""; 
							  }else{
								  sCurrentDateValue= sFCUBSCurrentDate;
							 sMaturityDateValue=sMaturityDate;  
							  }
							if(!(null==sProTradeSettlementInst ||"".equalsIgnoreCase(sProTradeSettlementInst))){
								sGoodsDescription=sProTradeSettlementInst;
							}else if((null==sProTradeSettlementInst ||"".equalsIgnoreCase(sProTradeSettlementInst))&&(null==sGoodsDescription ||"".equalsIgnoreCase(sGoodsDescription))){
								sGoodsDescription="NA";
							}
							sRiskParticipation=checkPartyTypeContract(sWIName);
							if(sCallName.equalsIgnoreCase("modTradeBCContract_Oper"))
							{
							callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeBCContract_Oper</operationType><correlationID>342343</correlationID><modTradeBCContractReq><requestType>A</requestType><contractRefNumber>"+sContractRefNumber+"</contractRefNumber><operation>" + sOperationCode + "</operation><stage>"+sStage+"</stage><maturityDate>"+sFinalMaturityDate+"</maturityDate><limitTracking>" + sLimitTrackFlag +"</limitTracking>"+setPartyLimit(sWIName,sRequestCategoryCode,sLimitTrackFlag)+"<interestfromDate>"+sFCUBSCurrentDate+"</interestfromDate><interestToDate>"+sFinalMaturityDate+"</interestToDate><internalRemarks></internalRemarks><branchCode>" + sBranchCreateCode + "</branchCode><acceptanceMessageDate>"+sAccMessageDate+"</acceptanceMessageDate><riskParticipation>"+sRiskParticipation+"</riskParticipation><"+"<contractDiscs><contractDisc><code></code><description></description><resolved></resolved><resolvedDate>"+
							"</resolvedDate><receivedDate></receivedDate></contractDisc></contractDiscs><acceptComFromDate></acceptComFromDate><acceptComToDate></acceptComToDate><goodsCode>OTHERS</goodsCode><goodsDescription>" + sGoodsDescription + "</goodsDescription>"+"<workItemNumber>" + sWIName + "</workItemNumber></modTradeBCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";
							
							WriteToLog_showpage("Y","modTradeBCContract_Oper sOutput: "+ callXML);
								sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sCallName +"',"+ convertToPlainString(callXML) +",'"+ REFNO +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","modTradeBCContract_Oper callXML TFO_DJ_INTEGRATION_CALLS_DTLS: "+ sQuery + " *****************************");	
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);						
								WriteToLog_showpage("Y"," insert modTradeBCContract_Oper sOutput: "+ sOutput);											
								
								sOutput = socket.connectToSocket(callXML);
								WriteToLog_showpage("Y","modTradeBCContract_Oper callXML sOutput"+ sOutput);
								String queryUpdateXML = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'" + sOutput + "'","WI_NAME = '"+ sWIName +"'  and REF_NUM = '"+REFNO+"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update sOutputUpdateXML input: "+ queryUpdateXML);
								String sOutputUpdateXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",queryUpdateXML);
								WriteToLog_showpage("Y","update sOutputUpdateXML output: " + sOutputUpdateXML);
								xp = new XMLParser(sOutput);
								returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
								String nextValue ="";
								if(returnCode == 0){
									sErrorDescription = xp.getValueOf("errorDescription");
									sResponse = "Success";
									sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
									WriteToLog_showpage("Y","update integration query modTradeBCContract_Oper: "+ sQuery);
									sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
									WriteToLog_showpage("Y","update integration query modTradeBCContract_Oper: " + sOutput);
									out.println(returnCode+"#"+sResponse+"#"+sErrorDescription);
								} else {
									sErrorDescription = xp.getValueOf("errorDescription");
									sResponse = "Failure";
									sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
									WriteToLog_showpage("Y","update integration query modTradeBCContract_Oper: "+ sQuery);
									sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
									WriteToLog_showpage("Y","update integration query modTradeBCContract_Oper: " + sOutput);
									out.println("1#"+sResponse+"#"+sErrorDescription);
								}
							
							}
						}
						else if("EC".equalsIgnoreCase(sRequestCategoryCode) && "EC_AM".equalsIgnoreCase(sRequestTypeCode))
						{
							sContractRefNumber = sOutput.substring(sOutput.indexOf("<TRANSACTION_REFERENCE>")+"<TRANSACTION_REFERENCE>".length(),sOutput.indexOf("</TRANSACTION_REFERENCE>"));
							WriteToLog_showpage("Y","sContractRefNumber: " + sContractRefNumber);
							
							sOperationCode = sOutput.substring(sOutput.indexOf("<OPERATION_CODE>")+"<OPERATION_CODE>".length(),sOutput.indexOf("</OPERATION_CODE>"));
							WriteToLog_showpage("Y","sOperationCode: " + sOperationCode);
							
							sMaturityDate=sOutput.substring(sOutput.indexOf("<INF_MATURITY_DATE>")+"<INF_MATURITY_DATE>".length(),sOutput.indexOf("</INF_MATURITY_DATE>"));
							
							 sRelationshipType = sOutput.substring(sOutput.indexOf("<RELATIONSHIP_TYPE>")+"<RELATIONSHIP_TYPE>".length(),sOutput.indexOf("</RELATIONSHIP_TYPE>"));
							 sProTradeSettlementInst=sOutput.substring(sOutput.indexOf("<PRO_TRADE_SETTLEMENT_INST>")+"<PRO_TRADE_SETTLEMENT_INST>".length(),sOutput.indexOf("</PRO_TRADE_SETTLEMENT_INST>")); 
							 sGoodsDescription=sOutput.substring(sOutput.indexOf("<LC_GOODS_DESC>")+"<LC_GOODS_DESC>".length(),sOutput.indexOf("</LC_GOODS_DESC>"));
							 String sAccMessageDate=sOutput.substring(sOutput.indexOf("<ACCEPTANCE_MSG_DATE>")+"<ACCEPTANCE_MSG_DATE>".length(),sOutput.indexOf("</ACCEPTANCE_MSG_DATE>"));
							sBranchCreateCode = fetchBranchCode(sRelationshipType , sWIName);
							String sFinalMaturityDate = calculateFinalDate(sMaturityDate,sFCUBSCurrentDate);
							WriteToLog_showpage("Y","sBranchCreateCode: " + sBranchCreateCode);
							sStage="FIN";
							sLimitTrackFlag=getLimitTrackingFlag(sWIName);
							
							SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");
							  Date currentDT = sdformat.parse(sFCUBSCurrentDate);
							  Date maturityDT = sdformat.parse(sMaturityDate);
							  WriteToLog_showpage("Y","currentDT: " + sdformat.format(currentDT));
							  WriteToLog_showpage("Y","maturityDT: " + sdformat.format(maturityDT));
							 
							if(currentDT.compareTo(maturityDT) > 0) {
								WriteToLog_showpage("Y","current date is greater ");
							 sCurrentDateValue= "";
							 sMaturityDateValue=""; 
							  }else{
								  sCurrentDateValue= sFCUBSCurrentDate;
							 sMaturityDateValue=sMaturityDate;  
							  }
							if(!(null==sProTradeSettlementInst ||"".equalsIgnoreCase(sProTradeSettlementInst))){
								sGoodsDescription=sProTradeSettlementInst;
							}else if((null==sProTradeSettlementInst ||"".equalsIgnoreCase(sProTradeSettlementInst))&&(null==sGoodsDescription ||"".equalsIgnoreCase(sGoodsDescription))){
								sGoodsDescription="NA";
							}
							sRiskParticipation=checkPartyTypeContract(sWIName);
							if(sCallName.equalsIgnoreCase("modTradeBCContract_Oper"))
							{
							callXML = "<APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCallType>TFO_CreateAmendContract</InnerCallType><WIName>"+ sWIName +"</WIName><SENDERID>WMS</SENDERID><REF_NO>"+ REFNO +"</REF_NO><operationType>modTradeBCContract_Oper</operationType><correlationID>342343</correlationID><modTradeBCContractReq><requestType>A</requestType><contractRefNumber>"+sContractRefNumber+"</contractRefNumber><operation>" + sOperationCode + "</operation><stage>"+sStage+"</stage><maturityDate>"+sFinalMaturityDate+"</maturityDate><limitTracking>" + sLimitTrackFlag +"</limitTracking>"+setPartyLimit(sWIName,sRequestCategoryCode,sLimitTrackFlag)+"<interestfromDate>"+sFCUBSCurrentDate+"</interestfromDate><interestToDate>"+sFinalMaturityDate+"</interestToDate><internalRemarks></internalRemarks><branchCode>" + sBranchCreateCode + "</branchCode><acceptanceMessageDate>"+sAccMessageDate+"</acceptanceMessageDate><riskParticipation>"+sRiskParticipation+"</riskParticipation><"+"<contractDiscs><contractDisc><code></code><description></description><resolved></resolved><resolvedDate>"+
							"</resolvedDate><receivedDate></receivedDate></contractDisc></contractDiscs><acceptComFromDate></acceptComFromDate><acceptComToDate></acceptComToDate><goodsCode>OTHERS</goodsCode><goodsDescription>" + sGoodsDescription + "</goodsDescription>"+"<workItemNumber>" + sWIName + "</workItemNumber></modTradeBCContractReq><EngineName>"+ sCabname +"</EngineName><SessionId>"+ sSessionId +"</SessionId><APWebService_Input>";
							
							WriteToLog_showpage("Y","modTradeBCContract_Oper sOutput: "+ callXML);
								sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sCallName +"',"+ convertToPlainString(callXML) +",'"+ REFNO +"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","modTradeBCContract_Oper callXML TFO_DJ_INTEGRATION_CALLS_DTLS: "+ sQuery + " *****************************");	
								sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);						
								WriteToLog_showpage("Y"," insert modTradeBCContract_Oper sOutput: "+ sOutput);											
								
								sOutput = socket.connectToSocket(callXML);
								WriteToLog_showpage("Y","modTradeBCContract_Oper callXML sOutput"+ sOutput);
								String queryUpdateXML = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'" + sOutput + "'","WI_NAME = '"+ sWIName +"'  and REF_NUM = '"+REFNO+"'", sCabname, sSessionId);
								WriteToLog_showpage("Y","update sOutputUpdateXML input: "+ queryUpdateXML);
								String sOutputUpdateXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",queryUpdateXML);
								WriteToLog_showpage("Y","update sOutputUpdateXML output: " + sOutputUpdateXML);
								xp = new XMLParser(sOutput);
								returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
								String nextValue ="";
								if(returnCode == 0){
									sErrorDescription = xp.getValueOf("errorDescription");
									sResponse = "Success";
									sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
									WriteToLog_showpage("Y","update integration query modTradeBCContract_Oper: "+ sQuery);
									sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
									WriteToLog_showpage("Y","update integration query modTradeBCContract_Oper: " + sOutput);
									out.println(returnCode+"#"+sResponse+"#"+sErrorDescription);
								} else {
									sErrorDescription = xp.getValueOf("errorDescription");
									sResponse = "Failure";
									sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
									WriteToLog_showpage("Y","update integration query modTradeBCContract_Oper: "+ sQuery);
									sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
									WriteToLog_showpage("Y","update integration query modTradeBCContract_Oper: " + sOutput);
									out.println("1#"+sResponse+"#"+sErrorDescription);
								}
							
							}
						}
					}catch(Exception e)
					{
						e.printStackTrace();
						out.println("Exception is"+e.getMessage());
						WriteToLog_showpage("Y","Exception : " +e.getMessage());
					}			
			}else {
				WriteToLog_showpage("Y","RETRY:"+sOutput);	
				callXML = sOutput.substring(sOutput.indexOf("<CALL_XML>")+10,sOutput.indexOf("</CALL_XML>"));
				WriteToLog_showpage("Y","RETRY1");	
				sInput = prepareAPSelectInputXml("SELECT SEQ_WEBSERVICE.NEXTVAL REFNO FROM DUAL", sCabname, sSessionId);
				sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
				REFNO = sOutput.substring(sOutput.indexOf("<REFNO>")+7,sOutput.indexOf("</REFNO>"));
				callXML = callXML.replaceAll(oldREFNO,REFNO);
				WriteToLog_showpage("Y", sCallName+"mBCContract_Oper sOutput: "+ callXML);
				sQuery = prepareAPInsertInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","WI_NAME, CALL_NAME, CALL_XML, REF_NUM","'" + sWIName + "','"+ sCallName +"',"+ convertToPlainString(callXML) +",'"+ REFNO +"'", sCabname, sSessionId);
				WriteToLog_showpage("Y","modTradeBCContract_Oper callXML TFO_DJ_INTEGRATION_CALLS_DTLS: "+ sQuery + " *****************************");	
				sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);						
				WriteToLog_showpage("Y"," insert modTradeBCContract_Oper sOutput: "+ sOutput);	
				sOutput = socket.connectToSocket(callXML);
				WriteToLog_showpage("Y",sCallName+" callXML sOutput"+ sOutput);
				
				String queryUpdateXML = prepareAPUpdateInputXml("TFO_DJ_INTEGRATION_CALLS_DTLS","OUTPUT_XML","'" + sOutput + "'","WI_NAME = '"+ sWIName +"'  and REF_NUM = '"+REFNO+"'", sCabname, sSessionId);
				WriteToLog_showpage("Y","update sOutputUpdateXML input: "+ queryUpdateXML);
				String sOutputUpdateXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",queryUpdateXML);
				WriteToLog_showpage("Y","update sOutputUpdateXML output: " + sOutputUpdateXML);
				
				xp = new XMLParser(sOutput);
				returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
				String nextValue ="";
				if(returnCode == 0){
				sErrorDescription = xp.getValueOf("errorDescription");
				sResponse = "Success";
				sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
				WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
				sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
				WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: " + sOutput);
				out.println(returnCode+"#"+sResponse+"#"+sErrorDescription);
				} else {
						sErrorDescription = xp.getValueOf("errorDescription");
						sResponse = "Failure";
						sQuery = prepareAPUpdateInputXml("EXT_TFO","CREATE_AMEND_STATUS_FCUBS","'" + sResponse + "'","WI_NAME = '"+ sWIName +"'", sCabname, sSessionId);
						WriteToLog_showpage("Y","update integration query modTradeLCContract_Oper: "+ sQuery);
						sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sQuery);
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
ConnectSocket socket;
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
String sContractAmount = "";
String sNegativeTolerance = "";
String sPositiveTolerance = "";
String sExpiryDate = "";
String sFromPlace = "";
String sToPlace = "";
String sLastShipmentDate = "";
String sPortOfDischarge = "";
String sPortOfLoading = "";
String sGoodsDescription = "";	
String sProTradeSettlementInst="";
String sRiskParticipation="N";
String sExpiryPlace = "";
String sCreditMode = "";
String sDraftTenor= "";
String sCreditDaysFrom="";
String sDraftAmount = "";
String sDrawee = "";
String sCreditDetails = "";
String sPartialShipmentAllowed = "";
String sPartialShipmentDetails= "";
String sTransShipmentAllowed="";
String sTransShipmentDetails = "";
String sMayConfirmFlag = "N";
String sRequestedConfirmationParty = "";
String sChargesFromBeneficiary = "";
String sSpecialPaymentConditionForBeneficiary= "";
String sPeriodOfPresentationDays="";
String sPeriod_Of_Presentation_Mode = "";
String sFinExpiryCond="";
String sFinExpiryType="";
String sFinCntrGteeExpDate="";
String sFinOtherAmendment="";

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
private String prepareAPSelectInputXml(String sQuery, String sCabname, String sSessionId){	
	return "<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId><QueryString>"+sQuery+"</QueryString></APSelectWithColumnNames_Input>";
}

private String prepareAPInsertInputXml(String tableName,String colname,String sValues, String sCabname, String sSessionId){	
	return "<?xml version=\"1.0\"?><APInsert_Input><Option>APInsert</Option><EngineName>" + sCabname + "</EngineName>" +    "<SessionId>" + sSessionId + "</SessionId><TableName>"+tableName+"</TableName><ColName>"+colname+"</ColName><Values>"+sValues+"</Values></APInsert_Input>";
}

private String prepareAPUpdateInputXml(String tableName,String colname,String sValues,String whrcls, String sCabname, String sSessionId){	
	return "<?xml version=\"1.0\"?><APUpdate_Input><Option>APUpdate</Option><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId><TableName>"+tableName+"</TableName><ColName>"+colname+"</ColName><Values>"+sValues+"</Values><WhereClause>"+whrcls+"</WhereClause></APUpdate_Input>";
}

private String prepareAPProcInputXml(String ProcName,String ProcParams, String sCabname, String sSessionId){	
	return "<?xml version=\"1.0\"?><APProcedure_Input><Option>APProcedure</Option><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId><ProcName>"+ProcName+"</ProcName><Params>"+ProcParams+"</Params></APUpdate_Input>";
}

private String prepareAPDeleteXml(String tableName,String whereClause, String sCabname, String sSessionId){
	return "<?xml version=1.0?><APDelete_Input><Option>APDelete</Option><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId><TableName>"+tableName+"</TableName><WhereClause>"+whereClause+"</WhereClause></APDelete_Input>";

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
		   sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
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
	
		 sLimitPartyType="DRAWEE";
		 sLimitLinkageType="F";
		 sLimitOperation="ACC";
		 sLimitPercentContribution="1";
		 sLimitLineCustomerId="DUMMYCID";
		 sLimitSerialNumber="99";
		 sLimitLineRefNumber="DUMMYLINE";
		 slimitAmountTag="BILL_OS_AMT";
	
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
		sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
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
private String setDiscreprancyDetail(String sWINumber, String sRequestType,String sFCUBSCurrentDate)
{
	
	String sCode="";
	String sDescription="";
	String sReceivedDate="";
	
	StringBuilder sPartyLimit = new StringBuilder();
	try{
	 if("ILCB_AC".equalsIgnoreCase(sRequestType))
	{
		sPartyLimit.append("<contractDiscs>");
		sQuery = "  SELECT DISCREPRANCY_CODE ,DISCREPRANCY_DESCRIPTION,TO_CHAR(DISCREPRANCY_RECEIVED_DATE,'DD/MM/YYYY') DISCREPRANCY_RECEIVED_DATE  from TFO_DJ_DISCREPRANCY_DETAILS WHERE WI_NAME = '"+ sWINumber +"'";
		sInput = prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
		WriteToLog_showpage("Y"," ***************TFO_DJ_DISCREPRANCY_DETAILS Input "+sInput+" *****************************");
		sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
		WriteToLog_showpage("Y"," ***************TFO_DJ_DISCREPRANCY_DETAILS sOutput "+sOutput+" *****************************");
		XMLParser xmlDataParser1 = new XMLParser();
		XMLParser xmlDataParser2 = new XMLParser();
		xmlDataParser1.setInputXML(sOutput);
		int iLimitCount = xmlDataParser1.getNoOfFields("Record");
		WriteToLog_showpage("Y","limit count " +iLimitCount );
		for (int i = 0; i < iLimitCount; ++i) {
			xmlDataParser2.setInputXML(xmlDataParser1.getNextValueOf("Record"));
			
			
		 sCode= xmlDataParser2.getValueOf("DISCREPRANCY_CODE");
		 sDescription =  xmlDataParser2.getValueOf("DISCREPRANCY_DESCRIPTION");
		 sReceivedDate= xmlDataParser2.getValueOf("DISCREPRANCY_RECEIVED_DATE");
		 
		sPartyLimit.append("<contractDisc>");
		sPartyLimit.append("<code>"+sCode+"</code>");
		sPartyLimit.append("<description>"+sDescription+"</description>");
		sPartyLimit.append("<resolved>Y</resolved>");
		sPartyLimit.append("<resolvedDate>"+sFCUBSCurrentDate+"</resolvedDate>");
		sPartyLimit.append("<receivedDate>"+sReceivedDate+"</receivedDate>");
	     sPartyLimit.append("</contractDisc>");
		}
	sPartyLimit.append("</contractDiscs>");	
	}	
	}catch(Exception e)
	{
		 WriteToLog_showpage("Y"," Excpt setPartyLimit" + e);
	}
	return sPartyLimit.toString();
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
	  sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
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
	  sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
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
private String getPartyDetailsforCreateAmend(String customerType, String sRequestType, String sWINumber) 
{
    StringBuilder sbPartyAddList = new StringBuilder();
    try {
		String country="";
		sQuery = "SELECT PARTY_ID,PARTY_TYPE,CUSTOMER_NAME,replace(replace(ADDRESS1,'<![CDATA[',''),']]>','') AS ADDRESS1,replace(replace(ADDRESS2,'<![CDATA[',''),']]>','') AS ADDRESS2,replace(replace(ADDRESS3,'<![CDATA[',''),']]>','') AS ADDRESS3,replace(replace(ADDRESS4,'<![CDATA[',''),']]>','') AS ADDRESS4,COUNTRY,replace(replace(ADDRESS,'<![CDATA[',''),']]>','') AS ADDRESS FROM TFO_DJ_PARTY_DETAILS WHERE WINAME = '"+ sWINumber +"'";
		sInput = prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
		WriteToLog_showpage("Y"," ***************getPartyDetailsforCreateAmend Input "+sInput+" *****************************");
		sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
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
        sbPartyAddList.append("</partyDetail>");
        if (("NI".equalsIgnoreCase(sRequestType)) && (customerType.equalsIgnoreCase(partyType))){
          this.limitLineCustomerId = partyID;
		}
		if ((("ILC_NI".equalsIgnoreCase(sRequestType))||("ILC_NI".equalsIgnoreCase(sRequestType)) )&& (customerType.equalsIgnoreCase(partyType))){
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
		sQuery = "SELECT PARTY_ID,PARTY_TYPE,CUSTOMER_NAME,replace(replace(ADDRESS1,'<![CDATA[',''),']]>','') AS ADDRESS1,replace(replace(ADDRESS2,'<![CDATA[',''),']]>','') AS  ADDRESS2,replace(replace(ADDRESS3,'<![CDATA[',''),']]>','') AS ADDRESS3,replace(replace(ADDRESS4,'<![CDATA[',''),']]>','') AS ADDRESS4,COUNTRY,replace(replace(ADDRESS,'<![CDATA[',''),']]>','') AS ADDRESS FROM TFO_DJ_PARTY_DETAILS WHERE WINAME = '"+ sWINumber +"'";
		sInput = prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
		WriteToLog_showpage("Y"," ***************getPartyDetailsforCreateAmend Input "+sInput+" *****************************");
		sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
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
      WriteToLog_showpage("Y"," Excpt getPartyDetailsforCreateAmend " + e);
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
		sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
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
private String getPartyIdFromPartyType(String limitPartyType, String sWINumber) 
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
				break;
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
		sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);	
		sBranchCode = sOutput.substring(sOutput.indexOf("<BRN_CODE>")+"<BRN_CODE>".length(),sOutput.indexOf("</BRN_CODE>"));
		/*if ("".equalsIgnoreCase(sBranchCode)) {
			if (("C".equalsIgnoreCase(Relationship_Type)) || ("Conventional".equalsIgnoreCase(Relationship_Type)))
			{
				sBranchCode = "299";
			}
			else
				sBranchCode = "799";
		}*/
	}
	catch (Exception e) {
      WriteToLog_showpage("Y"," Excpt fetchBranchCode " + e);
    }
	return sBranchCode;
}

private String fetchAmendmentTabData(String sWorkItemNo){
	
	try{
		sQuery="SELECT FIN_TRANSACTION_AMOUNT, FIN_UNDER_TOLERANCE,FIN_PLACE_OF_EXPIRY, FIN_CREDIT_MODE, FIN_DRAFT_TENOR, FIN_DRAFT_CREDIT_DAYS_FROM_DAYS, FIN_DRAFT_AMOUNT, FIN_DRAFT_DRAWEE_BANK, FIN_DEFERRED_PAYMENT, FIN_PARTIAL_SHIPMENT, FIN_PARTIAL_SHIPMENT_CONDITION, FIN_TRANSSHIPMENT, FIN_TRANSSHIPMENT_CONDITION, FIN_CONFIRMATION_INSTRUCTION, FIN_REQUESTED_CONFIRMATION_PARTY, FIN_CHARGES, FIN_SPL_PAY_COND_FOR_BEN, FIN_PERIOD_OF_PRESENTATION_DAYS, FIN_PERIOD_OF_PRESENTATION_MODE, FIN_ABOVE_TOLERANCE,TO_CHAR(FIN_EXPIRY_DATE,'DD/MM/YYYY') AS  FIN_EXPIRY_DATE, FIN_SHIPMENT_FROM,FIN_SHIPMENT_TO, TO_CHAR(FIN_LATEST_SHIPMENT_DATE,'DD/MM/YYYY') AS FIN_LATEST_SHIPMENT_DATE, FIN_PORT_OF_DISCHARGE, FIN_PORT_OF_LOADING, FIN_DESCRIPTION_OF_GOODS ,FIN_EXPIRY_COND,FIN_EXPIRY_TYPE,TO_CHAR(FIN_CNTR_GTEE_EXP_DATE,'DD/MM/YYYY') as FIN_CNTR_GTEE_EXP_DATE,FIN_OTHER_AMENDMENTS FROM TFO_DJ_AMENDMENT_FRAME_DATA WHERE WINAME= '"+sWorkItemNo+"'";
		sInput=prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
		WriteToLog_showpage("Y"," ***************fetchBranchCode in EXT "+sInput+" *****************************");
		sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);	
		WriteToLog_showpage("Y"," ***************output in EXT "+sOutput+" *****************************");
		sContractAmount = sOutput.substring(sOutput.indexOf("<FIN_TRANSACTION_AMOUNT>")+"<FIN_TRANSACTION_AMOUNT>".length(),sOutput.indexOf("</FIN_TRANSACTION_AMOUNT>"));
        sExpiryDate = sOutput.substring(sOutput.indexOf("<FIN_EXPIRY_DATE>")+"<FIN_EXPIRY_DATE>".length(),sOutput.indexOf("</FIN_EXPIRY_DATE>"));
        sNegativeTolerance = sOutput.substring(sOutput.indexOf("<FIN_UNDER_TOLERANCE>")+"<FIN_UNDER_TOLERANCE>".length(),sOutput.indexOf("</FIN_UNDER_TOLERANCE>"));
		sPositiveTolerance = sOutput.substring(sOutput.indexOf("<FIN_ABOVE_TOLERANCE>")+"<FIN_ABOVE_TOLERANCE>".length(),sOutput.indexOf("</FIN_ABOVE_TOLERANCE>"));
		sFromPlace = sOutput.substring(sOutput.indexOf("<FIN_SHIPMENT_FROM>")+"<FIN_SHIPMENT_FROM>".length(),sOutput.indexOf("</FIN_SHIPMENT_FROM>"));
		sToPlace = sOutput.substring(sOutput.indexOf("<FIN_SHIPMENT_TO>")+"<FIN_SHIPMENT_TO>".length(),sOutput.indexOf("</FIN_SHIPMENT_TO>"));
		sLastShipmentDate = sOutput.substring(sOutput.indexOf("<FIN_LATEST_SHIPMENT_DATE>")+"<FIN_LATEST_SHIPMENT_DATE>".length(),sOutput.indexOf("</FIN_LATEST_SHIPMENT_DATE>"));
		sPortOfDischarge = sOutput.substring(sOutput.indexOf("<FIN_PORT_OF_DISCHARGE>")+"<FIN_PORT_OF_DISCHARGE>".length(),sOutput.indexOf("</FIN_PORT_OF_DISCHARGE>"));
		sPortOfLoading = sOutput.substring(sOutput.indexOf("<FIN_PORT_OF_LOADING>")+"<FIN_PORT_OF_LOADING>".length(),sOutput.indexOf("</FIN_PORT_OF_LOADING>"));
		sGoodsDescription = sOutput.substring(sOutput.indexOf("<FIN_DESCRIPTION_OF_GOODS>")+"<FIN_DESCRIPTION_OF_GOODS>".length(),sOutput.indexOf("</FIN_DESCRIPTION_OF_GOODS>"));
		sExpiryPlace = sOutput.substring(sOutput.indexOf("<FIN_PLACE_OF_EXPIRY>")+"<FIN_PLACE_OF_EXPIRY>".length(),sOutput.indexOf("</FIN_PLACE_OF_EXPIRY>"));
		sCreditMode = sOutput.substring(sOutput.indexOf("<FIN_CREDIT_MODE>")+"<FIN_CREDIT_MODE>".length(),sOutput.indexOf("</FIN_CREDIT_MODE>"));
		sDraftTenor = sOutput.substring(sOutput.indexOf("<FIN_DRAFT_TENOR>")+"<FIN_DRAFT_TENOR>".length(),sOutput.indexOf("</FIN_DRAFT_TENOR>"));
		sCreditDaysFrom = sOutput.substring(sOutput.indexOf("<FIN_DRAFT_CREDIT_DAYS_FROM_DAYS>")+"<FIN_DRAFT_CREDIT_DAYS_FROM_DAYS>".length(),sOutput.indexOf("</FIN_DRAFT_CREDIT_DAYS_FROM_DAYS>"));
		sDraftAmount = sOutput.substring(sOutput.indexOf("<FIN_DRAFT_AMOUNT>")+"<FIN_DRAFT_AMOUNT>".length(),sOutput.indexOf("</FIN_DRAFT_AMOUNT>"));
		sDrawee = sOutput.substring(sOutput.indexOf("<FIN_DRAFT_DRAWEE_BANK>")+"<FIN_DRAFT_DRAWEE_BANK>".length(),sOutput.indexOf("</FIN_DRAFT_DRAWEE_BANK>"));
		sCreditDetails = sOutput.substring(sOutput.indexOf("<FIN_DEFERRED_PAYMENT>")+"<FIN_DEFERRED_PAYMENT>".length(),sOutput.indexOf("</FIN_DEFERRED_PAYMENT>"));
		sPartialShipmentAllowed = sOutput.substring(sOutput.indexOf("<FIN_PARTIAL_SHIPMENT>")+"<FIN_PARTIAL_SHIPMENT>".length(),sOutput.indexOf("</FIN_PARTIAL_SHIPMENT>"));
		sPartialShipmentDetails = sOutput.substring(sOutput.indexOf("<FIN_PARTIAL_SHIPMENT_CONDITION>")+"<FIN_PARTIAL_SHIPMENT_CONDITION>".length(),sOutput.indexOf("</FIN_PARTIAL_SHIPMENT_CONDITION>"));
		sTransShipmentAllowed = sOutput.substring(sOutput.indexOf("<FIN_TRANSSHIPMENT>")+"<FIN_TRANSSHIPMENT>".length(),sOutput.indexOf("</FIN_TRANSSHIPMENT>"));
		sTransShipmentDetails = sOutput.substring(sOutput.indexOf("<FIN_TRANSSHIPMENT_CONDITION>")+"<FIN_TRANSSHIPMENT_CONDITION>".length(),sOutput.indexOf("</FIN_TRANSSHIPMENT_CONDITION>"));
		sMayConfirmFlag = sOutput.substring(sOutput.indexOf("<FIN_CONFIRMATION_INSTRUCTION>")+"<FIN_CONFIRMATION_INSTRUCTION>".length(),sOutput.indexOf("</FIN_CONFIRMATION_INSTRUCTION>"));
		sRequestedConfirmationParty = sOutput.substring(sOutput.indexOf("<FIN_REQUESTED_CONFIRMATION_PARTY>")+"<FIN_REQUESTED_CONFIRMATION_PARTY>".length(),sOutput.indexOf("</FIN_REQUESTED_CONFIRMATION_PARTY>"));
		sChargesFromBeneficiary = sOutput.substring(sOutput.indexOf("<FIN_CHARGES>")+"<FIN_CHARGES>".length(),sOutput.indexOf("</FIN_CHARGES>"));
		sSpecialPaymentConditionForBeneficiary = sOutput.substring(sOutput.indexOf("<FIN_SPL_PAY_COND_FOR_BEN>")+"<FIN_SPL_PAY_COND_FOR_BEN>".length(),sOutput.indexOf("</FIN_SPL_PAY_COND_FOR_BEN>"));
		sPeriodOfPresentationDays = sOutput.substring(sOutput.indexOf("<FIN_PERIOD_OF_PRESENTATION_DAYS>")+"<FIN_PERIOD_OF_PRESENTATION_DAYS>".length(),sOutput.indexOf("</FIN_PERIOD_OF_PRESENTATION_DAYS>"));
		sPeriod_Of_Presentation_Mode = sOutput.substring(sOutput.indexOf("<FIN_PERIOD_OF_PRESENTATION_MODE>")+"<FIN_PERIOD_OF_PRESENTATION_MODE>".length(),sOutput.indexOf("</FIN_PERIOD_OF_PRESENTATION_MODE>"));
		sFinExpiryCond = sOutput.substring(sOutput.indexOf("<FIN_EXPIRY_COND>")+"<FIN_EXPIRY_COND>".length(),sOutput.indexOf("</FIN_EXPIRY_COND>"));
		sFinExpiryType = sOutput.substring(sOutput.indexOf("<FIN_EXPIRY_TYPE>")+"<FIN_EXPIRY_TYPE>".length(),sOutput.indexOf("</FIN_EXPIRY_TYPE>"));
		sFinCntrGteeExpDate = sOutput.substring(sOutput.indexOf("<FIN_CNTR_GTEE_EXP_DATE>")+"<FIN_CNTR_GTEE_EXP_DATE>".length(),sOutput.indexOf("</FIN_CNTR_GTEE_EXP_DATE>"));
		sFinOtherAmendment	=sOutput.substring(sOutput.indexOf("<FIN_OTHER_AMENDMENTS>")+"<FIN_OTHER_AMENDMENTS>".length(),sOutput.indexOf("</FIN_OTHER_AMENDMENTS>"));
			if("FD".equalsIgnoreCase(sFinExpiryType)){
				sFinExpiryType ="LIMT";
			}else if("OE".equalsIgnoreCase(sFinExpiryType)){
				sFinExpiryType ="UNLM";
			}else if("COND".equalsIgnoreCase(sFinExpiryType)){
				sFinExpiryType ="COND";
		}
						WriteToLog_showpage("Y"," ***************sMayConfirmFlag= "+sMayConfirmFlag+" *****************************");

							if("M".equalsIgnoreCase(sMayConfirmFlag)){
									sMayConfirmFlag = "Y";
							}else{
								    sMayConfirmFlag = "N";
							}
				WriteToLog_showpage("Y"," ***************sMayConfirmFlag after= "+sMayConfirmFlag+" *****************************");

		WriteToLog_showpage("Y"," ***************fetchBranchCode in EXT "+sOutput+" *****************************");
	}
	catch (Exception e) {
      WriteToLog_showpage("Y"," Excpt fetchBranchCode " + e);
    }
	return "";
}
private String fetchFCUBSDate(){
	String sFCUBSDate = "";
	try{
		sQuery="SELECT (CASE WHEN OT.DEF_VALUE='U' THEN (SELECT TO_CHAR(INN.DEF_VALUE) FROM TFO_DJ_DEFAULT_MAST INN WHERE INN.DEF_NAME='FCUBS_BACK_DATE' )"+
				"ELSE TO_CHAR(SYSDATE,'DD/MM/YYYY') END) FCUBS_DT FROM TFO_DJ_DEFAULT_MAST OT WHERE  OT.DEF_NAME ='FCUBS_DATE'";
		String sInputdt=prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
		WriteToLog_showpage("Y"," ***************fetchBranchCode in EXT "+sInputdt+" *****************************");
		String sOutputdt=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputdt);	
		WriteToLog_showpage("Y"," ***************fetchBranchCode output in EXT "+sOutputdt+" *****************************");
		sFCUBSDate = sOutputdt.substring(sOutputdt.indexOf("<FCUBS_DT>")+"<FCUBS_DT>".length(),sOutputdt.indexOf("</FCUBS_DT>"));
			WriteToLog_showpage("Y","date :"+sFCUBSDate);
		return sFCUBSDate;
		
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
		sbPartyAddList.append("<contractParty>");
		sbPartyAddList.append("<partyType>"); sbPartyAddList.append("DRAWEE"); sbPartyAddList.append("</partyType>");
		sbPartyAddList.append("<partyId>"); sbPartyAddList.append(sCustomerId); sbPartyAddList.append("</partyId>");
		sbPartyAddList.append("<partyLanguage>"); sbPartyAddList.append("ENG"); sbPartyAddList.append("</partyLanguage>");
		sbPartyAddList.append("</contractParty>");
		sbPartyAddList.append("<contractParty>");
		sbPartyAddList.append("<partyType>"); sbPartyAddList.append("DRAWER"); sbPartyAddList.append("</partyType>");
		sbPartyAddList.append("<partyId>"); sbPartyAddList.append("100000"); sbPartyAddList.append("</partyId>");
		sbPartyAddList.append("<partyName>"); sbPartyAddList.append(fetchIFPDraweeCounterPartyName(sWINumber)); sbPartyAddList.append("</partyName>");
		sbPartyAddList.append("<partyLanguage>"); sbPartyAddList.append("ENG"); sbPartyAddList.append("</partyLanguage>");
		sbPartyAddList.append("</contractParty>");		
    }	
	catch (Exception e){
      WriteToLog_showpage("Y"," Excpt getPartyDetailsforCreateAmend " + e);
    } 
    return sbPartyAddList.toString();
}

private String fetchIFPDraweeCounterPartyName(String sWorkItemNo){
	String sCounterPartyName = "";
	try{
		sQuery="SELECT SUBSTR(CP_NAME,0,105) AS COUNTER_PARTY_NAME FROM TFO_DJ_CPD_DETAILS WHERE WI_NAME='"+sWorkItemNo+"' AND SNO =(SELECT MIN(TO_NUMBER(SNO)) FROM TFO_DJ_CPD_DETAILS WHERE WI_NAME ='"+sWorkItemNo+"')";
		sInput=prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
		WriteToLog_showpage("Y"," ***************fetchIFPDraweeCounterPartyName in EXT "+sInput+" *****************************");
		sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);	
		sCounterPartyName = sOutput.substring(sOutput.indexOf("<COUNTER_PARTY_NAME>")+"<COUNTER_PARTY_NAME>".length(),sOutput.indexOf("</COUNTER_PARTY_NAME>"));
	}
	catch (Exception e) {
      WriteToLog_showpage("Y"," Excpt fetchIFPDraweeCounterPartyName " + e);
    }
	return sCounterPartyName;
}

private String getPartyOtherDetails(String sWIName) 
{
    StringBuilder sbPartyAddList = new StringBuilder();
    try {
		String mediaType="";
		sQuery = "SELECT MEDIA_TYPE,replace(replace(ADDRESS,'<![CDATA[',''),']]>','') AS  ADDRESS FROM TFO_DJ_PARTY_DETAILS WHERE WINAME = '"+ sWIName +"'";
		sInput = prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
		WriteToLog_showpage("Y"," ***************getPartyOtherDetails Input "+sInput+" *****************************");
		sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
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
 
private String setProTradeRemark(String sWINumber)
{
	try 
	{
		sQuery = "SELECT INSTRCTN_TO_BANK,PRO_TRADE_SETTLEMENT_INST FROM EXT_TFO WHERE WI_NAME = '"+ sWINumber +"'";
		sInput = prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
		WriteToLog_showpage("Y"," ***************setProTradeRemark Input "+sInput+" *****************************");
		sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
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
		sOutput = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
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

public String calculateFinalDate(String sMaturityDate, String sCurrentDate)
{
	String sFinalDate ="";
	WriteToLog_showpage("Y","Date before Addition: "+sMaturityDate);
	//Current Date
	//Specifying date format that matches the given date
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	//Compare Dates
	Date dCurrentDate = null;
	Date dMaturityDate = null;
	try {
		dCurrentDate = sdf.parse(sCurrentDate);
	} catch (ParseException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	}
	try {
		dMaturityDate = sdf.parse(sMaturityDate);
	} catch (ParseException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	if(dMaturityDate.after(dCurrentDate)){
		WriteToLog_showpage("Y","dMaturityDate is after dCurrentDate");
		sFinalDate = sMaturityDate;           
	}
	// before() will return true if and only if dCurrentDate is before dMaturityDate
	if(dMaturityDate.equals(dCurrentDate) || dMaturityDate.before(dCurrentDate)){
		WriteToLog_showpage("Y","dMaturityDate is before dCurrentDate");
		Calendar c = Calendar.getInstance();
		try{
		   //Setting the date to the given date
		   c.setTime(sdf.parse(sCurrentDate));
		}catch(ParseException e){
			e.printStackTrace();
		 }    		   
		//Number of Days to add
		c.add(Calendar.DATE, 1);  
		//Date after adding the days to the given date
		sFinalDate = sdf.format(c.getTime());  
		//Displaying the new Date after addition of Days
		WriteToLog_showpage("Y","Date after Addition: "+sFinalDate);
	}		
	return sFinalDate;
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
		
	Logger log = Logger.getLogger(_FCUBS_5F_Integration_5F_Calls_5F_Executor_5F_Amend.class);
	
	private void WriteToLog_showpage(String flag,String strOutput){
		log.info(strOutput);
	}

 /*
 
private void WriteToLog_showpage(String flag,String strOutput)
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
			strFilePath.append("FCUBS_Integration_Calls_Executor_Amend"+DtString+".xml");
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
}
*/

private static String StackTraceToString_showpage(Exception e) {
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        e.printStackTrace(printWriter);
        return result.toString();
}

private String characterHandler(String str) {
        
        return str.replaceAll("'","''");
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
		
		sQuery = "SELECT DRAFT_NO,DRAFT_TENOR,DRAFT_CREDIT_DAYS_FROM,TO_CHAR(DRAFT_CREDIT_DAYS_DATE,'DD/MM/YYYY') DRAFT_CREDIT_DAYS_DATE,DRAFT_DRAWEE_BANK,DRAFT_AMOUNT FROM TFO_DJ_DRAFT_DETAILS WHERE WINAME = '" +sWINumber+ "'";
		
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
			draftSno = xmlDataParser2.getValueOf("DRAFT_NO");
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
			
			if(!"".equalsIgnoreCase(draftTenor) || !"".equalsIgnoreCase(draftCreditDaysFrom) || !"".equalsIgnoreCase(draftDraweeBank) ||!"".equalsIgnoreCase(draftAmount)){
				draftSno = "1";
			}
						
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
