package com.newgen.repair;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.newgen.client.XMLParser;
import com.newgen.niplj.NIPLJ;
import com.newgen.niplj.fileformat.Tif6;
import com.newgen.niplj.io.RandomInputStream;
import com.newgen.niplj.io.RandomInputStreamSource;
import com.newgen.omni.wf.util.app.NGEjbClient;
import com.newgen.util.APCallCreateXML;
import com.newgen.util.ConnectSocket;
import com.newgen.util.Log;

import ISPack.CPISDocumentTxn;
import ISPack.ISUtil.JPISException;
import Jdts.DataObject.JPDBString;


public class RetryIntegrationCall extends Log {
	
public static String runJSP(String sWIName,	String seq, String sCabname, String sSessionId, String sUserName , String sJtsIp , String iJtsPort ) throws Exception {
	
	String retryCount="";
	String sQuery="";
	String sInput="";
	String sOutput="";
	String sRecord = "";
	String sCall ="";
	String sStatus ="";
	String returnXML="";
	String sReason ="";
	String sErrorDescription="";

	String mandatory="";
	String sResponse="";
	String RequestDt="";
	
	String callXML="";
	String sResVal="";
	String ErrorDesc="";
	String SLNO="";

	String errCase="";
	String brmsEligibleFlag="";
	String chequebookCRReflect="Yes";

	String oldREFNO="";

	//Added by Shivanshu ATP-501
	String sQueryECB = "";
	String sInputECB = "";
	String sOutputECB = "";
	String masterFlag = "";
	String ecbRestFlag=""; 

	String ecbExtractionDate = "";
	String ecbReferenceNumber = "";
	String ecbHitFlg = "";
	String totalCountCheque12M = "";
	String totalCountBouncedCheque12M =  "";
	String errorCode = "";
	String sysRefNumber = "";
	int ecbHit = 0;
	String sourcingChannel = "";
	
	ConnectSocket socket;
	
	try	{			
		String sQueryBPM = "SELECT (SELECT VALUE FROM BPM_SERVICE_CONFIG WHERE KEY = 'SOCKETIP') IP,(SELECT VALUE FROM BPM_SERVICE_CONFIG WHERE KEY = 'SOCKETPORT') PORT FROM DUAL";
		String sInputBPM = prepareAPSelectInputXml(sQueryBPM,sCabname,sSessionId);
		logger.info(" ***************Ref number Input "+sInputBPM+" *****************************");
		String sOutputBPM = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputBPM);
		logger.info(" ***************Ref number output = "+sOutputBPM+"  *****************************");
		try
		{
			logger.info("started");
			String ipBPM = sOutputBPM.substring(sOutputBPM.indexOf("<IP>")+4,sOutputBPM.indexOf("</IP>"));
			logger.info("started  1");
			int portBPM = Integer.parseInt(sOutputBPM.substring(sOutputBPM.indexOf("<PORT>")+6,sOutputBPM.indexOf("</PORT>")));
			logger.info("ipBPM : " +ipBPM);
			logger.info(" portBPM : " +portBPM);
			socket = ConnectSocket.getReference(ipBPM, portBPM);
		}
		catch(Exception e)
		{
		}

		sQuery= "SELECT REFNUMBER  FROM USR_0_INTEGRATION_OLD_REF WHERE WI_NAME = '"+sWIName+"' and call_order='"+seq+"'";
		sInput=prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
		logger.info(" ***************Ref number Input "+sInput+" *****************************");
		sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
		logger.info(" ***************Ref number output = "+sOutput+"  *****************************");
		try
		{
			oldREFNO=sOutput.substring(sOutput.indexOf("<REFNUMBER>")+11,sOutput.indexOf("</REFNUMBER>"));
		}
		catch(Exception e)
		{
			oldREFNO="";
		}
		
		//Added by Shivanshu ATP-501
		String sQuerySource= "SELECT SOURCING_CHANNEL  FROM EXT_WBG_AO WHERE WI_NAME = '"+sWIName+"'";
		String sInputSource=prepareAPSelectInputXml(sQuerySource,sCabname,sSessionId);
		logger.info(" ***************Ref number Input "+sInputSource+" *****************************");
		String sOutputSource=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputSource);
		logger.info(" ***************Ref number output = "+sOutputSource+"  *****************************");
		try
		{
			sourcingChannel=sOutputSource.substring(sOutputSource.indexOf("<SOURCING_CHANNEL>")+18,sOutputSource.indexOf("</SOURCING_CHANNEL>"));
		}
		catch(Exception e)
		{
			oldREFNO="";
		}
		
		sQuery="SELECT COUNT(1) AS COUNT_WI FROM PDBCONNECTION WHERE RANDOMNUMBER = '"+sSessionId+"'";
		sInput=prepareAPSelectInputXml(sQuery,sCabname,sSessionId);
		logger.info(" ***************Checking connection "+sInput+" *****************************");
		sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);	
		String sCount=sOutput.substring(sOutput.indexOf("<COUNT_WI>")+10,sOutput.indexOf("</COUNT_WI>"));
		if(sCount.equalsIgnoreCase("0"))
		{
			logger.info("Session Timeout");
		}
		else
		{	
			sInput=prepareAPSelectInputXml("select seq_webservice.nextval REFNO from dual",sCabname,sSessionId);
			sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
			String REFNO=sOutput.substring(sOutput.indexOf("<REFNO>")+7,sOutput.indexOf("</REFNO>"));
			logger.info(" next seq val: "+REFNO);
			if(!sWIName.equals(""))
			{
				sQuery= "SELECT CALL_NAME,STATUS,INPUT_XML,SLNO,RETRY_COUNT FROM USR_0_WBG_AO_INTEGRATION_CALLS "
						+ "WHERE WI_NAME = '"+sWIName+"' and call_order='"+seq+"' and status <>'Success' ORDER BY CALL_ORDER";
			}
			sInput=prepareAPSelectInputXml(sQuery,sCabname,sSessionId);			
			logger.info(" ***************input Fetch data from integration table "+sInput+" *****************************");		
			if(!sInput.equals(""))
			{
				sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
			}		
			logger.info(" ***************output Fetch data from integration table "+sOutput+" *****************************");	

			
			if(!sOutput.equals(""))
			{			
				String sMainCode = sOutput.substring(sOutput.indexOf("<MainCode>")+10,sOutput.indexOf("</MainCode>"));
				String runningInputXml="";
				String runningOutputXml="";
				if(sMainCode.equalsIgnoreCase("0"))	{					

					sRecord = sOutput.substring(sOutput.indexOf("<Record>")+8,sOutput.indexOf("</Record>"));
					sCall =sRecord.substring(sRecord.indexOf("<CALL_NAME>")+11,sRecord.indexOf("</CALL_NAME>"));
					sStatus =sRecord.substring(sRecord.indexOf("<STATUS>")+8,sRecord.indexOf("</STATUS>"));
					callXML=sRecord.substring(sRecord.indexOf("<INPUT_XML>")+11,sRecord.indexOf("</INPUT_XML>"));
					SLNO=sRecord.substring(sRecord.indexOf("<SLNO>")+6,sRecord.indexOf("</SLNO>"));
					retryCount=sRecord.substring(sRecord.indexOf("<RETRY_COUNT>")+13,sRecord.indexOf("</RETRY_COUNT>"));

					logger.info(" **************callXML= "+callXML+" *****************************");

					//Added by Shivanshu ATP-501
					String processName = "";
					if (sourcingChannel.equalsIgnoreCase("DSA_MANUAL")){
						processName = "BCA";
					} else {
						processName = sWIName.split("-")[0];
					}
					String callName = sCall.split("_")[0];

					sQueryECB = "SELECT FLAG FROM BPM_INTEGRATION_CALL_EXECUTION_MASTER WHERE PROCESS_NAME = '"+processName+"' AND CALL_NAME like '%"+callName+"%' ";
					sInputECB="<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+sQueryECB+"</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
					logger.info(" ***************showpage_sInputECB= "+sInputECB+" Ends_show *****************************");
					sOutputECB=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputECB);
					try
					{
						masterFlag=sOutputECB.substring(sOutputECB.indexOf("<FLAG>")+6,sOutputECB.indexOf("</FLAG>"));
						logger.info("Master Flag : "+masterFlag);
						if(!masterFlag.equalsIgnoreCase("")){
							ecbRestFlag=masterFlag;
						} else {
							ecbRestFlag="N";
						}
						logger.info("ECB REST FLAG is "+ecbRestFlag);
					}catch(Exception e){

						ecbRestFlag="N";
						logger.info("ECB REST FLAG is "+ecbRestFlag);
					}
					if(oldREFNO.equalsIgnoreCase("")){
						String sInput1=prepareAPInsertInputXml("USR_0_INTEGRATION_OLD_REF","WI_NAME,CALL_NAME,CALL_ORDER,STATUS,REFNUMBER","'" + sWIName + "','"+normalizeStringHandleComma(sCall)+"','"+seq+"','','"+REFNO+"'",sCabname,sSessionId);
						logger.info(" ***************insert input for ref number USR_0_INTEGRATION_OLD_REF "+sInput1+" *****************************");	
						sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput1);						
						logger.info(" ***************insert sOutput for ref number USR_0_INTEGRATION_OLD_REF "+sOutput+" *****************************");	
						oldREFNO = REFNO;
					}		
					//Added by Shivanshu ATP-501
					if(!ecbRestFlag.equalsIgnoreCase("Y")){					
						callXML=callXML.replaceAll("<REF_NO>#REF_NO#</REF_NO>","<REF_NO>"+REFNO+"</REF_NO><OLDREF_NO>"+oldREFNO+"</OLDREF_NO>");
						callXML=callXML.replaceAll("<sysRefNumber>#REF_NO#</sysRefNumber>","<sysRefNumber>"+REFNO+"</sysRefNumber>");
					}					
					logger.info(" callXML after ref replace: "+callXML);
					if((sCall.contains("FATCA_AUS_COMMIT") || sCall.contains("CustomerModification_AUS")) && callXML.contains("#CUST")){
						String sQueryAus = "SELECT CUSTID FROM USR_0_WBG_AO_AUS WHERE WI_NAME = '"+sWIName+"' ORDER BY TO_NUMBER(AUSSERNO) ASC";		
						String sInputAus = prepareAPSelectInputXml(sQueryAus,sCabname,sSessionId);			
						logger.info(" ***************input Fetch AUS CUSTID from EXT_WBG_AO table "+sInputAus+" *****************************");	
						String ProcNameAus = "ADCB_INTEGRATION_WBG_PKG.updateAUSCustId";						
						if(!sInputAus.equals(""))
						{
							//sOutput=WFCallBroker.execute(sInputAus,sJtsIp,iJtsPort,1);
							sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputAus);
							XMLParser xp = new XMLParser(sOutput);
							int start = xp.getStartIndex("Records", 0, 0);
							int deadEnd = xp.getEndIndex("Records", start, 0);
							int noOfFields = xp.getNoOfFields("Record", start,deadEnd);
							int end = 0;
							if(noOfFields > 0){
								for(int i = 1; i <= noOfFields; ++i){
									start = xp.getStartIndex("Record", end, 0);
									end = xp.getEndIndex("Record", start, 0);		
									String cidAus = xp.getValueOf("CUSTID", start, end);
									callXML=callXML.replaceAll("#CUST_"+i+"#",cidAus);
									String ProcParamsAus = "'"+sWIName+"','#CUST_"+i+"#','"+cidAus+"','"+cidAus+"','"+i+"'";
									String sInputProc="<?xml version=\"1.0\"?><APProcedure_Input><Option>APProcedure</Option><ProcName>"+ProcNameAus+"</ProcName><Params>"+ProcParamsAus+"</Params><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APUpdate_Input>";								
									logger.info(" ***************ap procedure input "+sInputProc);
									//String sOutputProc = WFCallBroker.execute(sInputProc,sJtsIp,iJtsPort,1);
									String sOutputProc=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputProc);									
									logger.info(" ***************ap procedure sOutput"+sOutputProc);
								}
							}
						}


					}	

					if(sCall.contains("CustomerModification_WBG")){
						String CIDandDate = getCIDandCIDCreationDate(sWIName,sJtsIp,iJtsPort,sSessionId,sCabname);
						String[] CIDandDateArr =  CIDandDate.split("#");
						logger.info(" **************CID AND DATETIME= "+CIDandDate+" " + sCall+" ***************************");	
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-mm");
						String existCust = CIDandDateArr[0];

						String sInput1 = prepareAPSelectInputXml("SELECT to_char(to_date(acc_open_date,'DD/MM/YYYY  hh24:mi:ss'),'YYYY-MM-DD') as acc_open_date  from usr_0_wbg_ao_acc_info WHERE WI_NAME = '"+sWIName+"'",sCabname,sSessionId);
						logger.info(" In sInput get date "+ sInput1);
						String paramOutput1=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput1);
						String reqDate = paramOutput1.substring(paramOutput1.indexOf("<ACC_OPEN_DATE>")+15,paramOutput1.indexOf("</ACC_OPEN_DATE>"));
						logger.info("------------ ACC_OPEN_DATE --------"+ reqDate);
						//callXML=callXML.replaceAll("#ACC_OPEN_DATE#",sdf.format(sdf.parse(CIDandDateArr[2])));
						//logger.info("------------After date update replace call xml --------"+ callXML);

						if(existCust.equalsIgnoreCase("NTB"))
						{
							callXML=callXML.replaceAll("#CID_CREATION_DATE#",sdf.format(sdf.parse(CIDandDateArr[2])));
							callXML=callXML.replaceAll("#CustomerID#",CIDandDateArr[1]);
						}
						else
						{
							callXML=callXML.replaceAll("#CustomerID#",CIDandDateArr[1]);
						}


					} else if(sCall.contains("AddCASAAccount_WBG")){

						String sQuery2= "SELECT CUSTID FROM EXT_WBG_AO WHERE WI_NAME = '"+sWIName+"'";		
						String sInput2 = prepareAPSelectInputXml(sQuery2,sCabname,sSessionId);			
						logger.info(" ***************input Fetch CUSTID from EXT_WBG_AO table "+sInput2+" *****************************");		
						if(!sInput2.equals(""))
						{
							sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput2);
						}
						callXML=callXML.replaceAll("#CustomerID#",sOutput.substring(sOutput.indexOf("<CUSTID>")+8,sOutput.indexOf("</CUSTID>")));

						callXML=callXML.replaceAll("<origRefNo>#ORIG_REF_NO#</origRefNo>","<origRefNo>9999"+REFNO+"</origRefNo>");

						String ProcParams_casa = "'#ORIG_REF_NO#','9999"+REFNO+"','"+sWIName+"','"+seq+"'";
						sInput="<?xml version=\"1.0\"?><APProcedure_Input><Option>APProcedure</Option><ProcName>ADCB_INTEGRATION_WBG_PKG.updateCASARequest</ProcName><Params>"+ProcParams_casa+"</Params><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APUpdate_Input>";								
						sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);	
						logger.info(" ***************ap procedure sOutput"+sOutput);

					}else if (sCall.contains("ModifyCASAAccount_WBG")){

						String sQuery2= "SELECT CUSTID FROM EXT_WBG_AO WHERE WI_NAME = '"+sWIName+"'";		
						String sInput2 = prepareAPSelectInputXml(sQuery2,sCabname,sSessionId);			
						logger.info(" ***************input Fetch CUSTID from EXT_WBG_AO table "+sInput2+" *****************************");		
						if(!sInput2.equals(""))
						{
							sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput2);
						}
						callXML=callXML.replaceAll("#CustomerID#",sOutput.substring(sOutput.indexOf("<CUSTID>")+8,sOutput.indexOf("</CUSTID>")));


						String sQuery_modacc = "SELECT ACC_NO FROM USR_0_WBG_AO_ACC_INFO WHERE WI_NAME = '"+sWIName+"' ORDER BY SNO ASC";
						String sInput2_modacc = prepareAPSelectInputXml(sQuery_modacc,sCabname,sSessionId);
						String sOutput_modacc;					

						logger.info(" ***************input Fetch ACCOUNT NO from USR_0_WBG_AO_ACC_INFO table "+sInput2_modacc+" *****************************");		
						if(!sInput2_modacc.equals(""))
						{
							sOutput_modacc=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput2_modacc);
							XMLParser xp1 = new XMLParser(sOutput_modacc);
							int start = xp1.getStartIndex("Records", 0, 0);
							int deadEnd = xp1.getEndIndex("Records", start, 0);
							int noOfFields = xp1.getNoOfFields("Record", start,deadEnd);
							int end = 0;
							if(noOfFields > 0){
								for(int i = 1; i <= noOfFields; ++i){
									start = xp1.getStartIndex("Record", end, 0);
									end = xp1.getEndIndex("Record", start, 0);				
									callXML=callXML.replaceAll("#accountNumber_"+i+"#",xp1.getValueOf("ACC_NO", start, end));
								}
							}
						}
						callXML=callXML.replaceAll("<origRefNo>#ORIG_REF_NO#</origRefNo>","<origRefNo>9999"+REFNO+"</origRefNo>");

						String ProcParams_modcasa = "'#ORIG_REF_NO#','9999"+REFNO+"','"+sWIName+"','"+seq+"'";
						sInput="<?xml version=\"1.0\"?><APProcedure_Input><Option>APProcedure</Option><ProcName>ADCB_INTEGRATION_WBG_PKG.updateCASARequest</ProcName><Params>"+ProcParams_modcasa+"</Params><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APUpdate_Input>";								
						logger.info(" ***************ap procedure input "+sInput);
						sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);	
						logger.info(" ***************ap procedure sOutput"+sOutput);

						String sQuery22= "SELECT CUSTID FROM USR_0_WBG_AO_AUS WHERE WI_NAME = '"+sWIName+"' AND (REQUESTCLASSIFICATION <> 'CARD' OR REQUESTCLASSIFICATION IS NULL)";		
						String sInput22 = prepareAPSelectInputXml(sQuery22,sCabname,sSessionId);			
						logger.info(" ***************input Fetch AUS CUSTID from EXT_WBG_AO table "+sInput22+" *****************************");		
						if(!sInput22.equals(""))
						{
							sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput22);
							XMLParser xp = new XMLParser(sOutput);
							int start = xp.getStartIndex("Records", 0, 0);
							int deadEnd = xp.getEndIndex("Records", start, 0);
							int noOfFields = xp.getNoOfFields("Record", start,deadEnd);
							int end = 0;
							if(noOfFields > 0){
								for(int i = 1; i <= noOfFields; ++i){
									start = xp.getStartIndex("Record", end, 0);
									end = xp.getEndIndex("Record", start, 0);				
									callXML=callXML.replaceAll("#CustomerID_"+i+"#",xp.getValueOf("CUSTID", start, end));
								}
							}
						}

					} 
					else if(sCall.contains("InqAccountDetails") ){
						String sQuery_modacc = "SELECT ACC_NO FROM USR_0_WBG_AO_ACC_INFO WHERE WI_NAME = '"+sWIName+"' ORDER BY SNO ASC";
						logger.info("InqAccountDetails : sQuery_modacc :"+sQuery_modacc);
						String sInput2_modacc = prepareAPSelectInputXml(sQuery_modacc,sCabname,sSessionId);
						logger.info("InqAccountDetails : sInput2_modacc :"+sInput2_modacc);
						if(!sInput2_modacc.equals(""))
						{
							String sOutput_modacc=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput2_modacc);
							logger.info("InqAccountDetails : sOutput_modacc :"+sOutput_modacc);
							XMLParser xp1 = new XMLParser(sOutput_modacc);
							int start = xp1.getStartIndex("Records", 0, 0);
							int deadEnd = xp1.getEndIndex("Records", start, 0);
							int noOfFields = xp1.getNoOfFields("Record", start,deadEnd);
							logger.info("InqAccountDetails : noOfFields :"+noOfFields);
							int end = 0;
							if(noOfFields > 0){
								for(int i = 1; i <= noOfFields; ++i){
									start = xp1.getStartIndex("Record", end, 0);
									end = xp1.getEndIndex("Record", start, 0);				
									callXML=callXML.replaceAll("#ACCOUNT_NO_"+i+"#",xp1.getValueOf("ACC_NO", start, end));
									logger.info("InqAccountDetails : callXML :"+callXML);
								}
							}
						}
						logger.info("InqAccountDetails : final xml :"+callXML);
						//sOutput = socket.connectToSocket(callXML);
						//logger.info("InqAccountDetails : output :"+sOutput);

					}
					else if(sCall.contains("LeadUpdate_WBG")){
						String sQuery2= "SELECT TO_CHAR(SYSDATE,'MM/dd/yyyy') AS CLOSUREACCOUNTOPENDATE FROM DUAL";		
						String sInput2 = prepareAPSelectInputXml(sQuery2,sCabname,sSessionId);			
						logger.info(" ***************input Fetch ACC_OPEN_DATE from USR_0_WBG_AO_ACC_INFO table "+sInput2+" *****************************");		
						if(!sInput2.equals(""))
						{
							sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput2);
						}
						callXML=callXML.replaceAll("#CLOSUREACCOUNTOPENDATE#",sOutput.substring(sOutput.indexOf("<CLOSUREACCOUNTOPENDATE>")+24,sOutput.indexOf("</CLOSUREACCOUNTOPENDATE>")));

						sQuery2= "SELECT ACC_NO FROM (SELECT ACC_NO FROM USR_0_WBG_AO_ACC_INFO WHERE WI_NAME = '"+ sWIName +"' ORDER BY SNO DESC) WHERE ROWNUM <= 1";		
						sInput2 = prepareAPSelectInputXml(sQuery2,sCabname,sSessionId);			
						logger.info(" ***************input Fetch ACC_NO from USR_0_WBG_AO_ACC_INFO table "+sInput2+" *****************************");		
						if(!sInput2.equals(""))
						{
							sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput2);
						}
						callXML=callXML.replaceAll("#CLOSUREACCOUNTNUMBER#",sOutput.substring(sOutput.indexOf("<ACC_NO>")+8,sOutput.indexOf("</ACC_NO>")));
					}else if(sCall.contains("SMSRegistration_WBG")){

						String sQuery2= "SELECT ACC_NO FROM (SELECT ACC_NO FROM USR_0_WBG_AO_ACC_INFO WHERE WI_NAME = '"+ sWIName +"' ORDER BY SNO DESC) WHERE ROWNUM <= 1";		
						String sInput2 = prepareAPSelectInputXml(sQuery2,sCabname,sSessionId);			
						logger.info(" ***************input Fetch CUSTID from EXT_WBG_AO table "+sInput2+" *****************************");		
						if(!sInput2.equals(""))
						{
							sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput2);
						}
						callXML=callXML.replaceAll("#DefaultAccountNumber#",sOutput.substring(sOutput.indexOf("<ACC_NO>")+8,sOutput.indexOf("</ACC_NO>")));

						sQuery2 = "select custid from ext_wbg_ao where wi_name='"+sWIName+"'";
						sInput2 = prepareAPSelectInputXml(sQuery2,sCabname,sSessionId);
						logger.info(" ***************input Fetch CUSTID from EXT_WBG_AO table "+sInput2+" *****************************");		
						if(!sInput2.equals(""))
						{
							sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput2);
						}
						logger.info(" ***************input Fetch CUSTID from EXT_WBG_AO table "+sOutput+" *****************************");		
						//callXML=callXML.replaceAll("#CustomerId#",sOutput.substring(sOutput.indexOf("<CUST_ID>")+9,sOutput.indexOf("</CUST_ID>")));
						callXML=callXML.replaceAll("#CustomerId#", getTagValueFromXML(sOutput,"CUSTID",""));

					}
					logger.info(" **************After replacing ref no= "+callXML + sCall+" ***************************");	

					if(!sStatus.equalsIgnoreCase("running"))
					{
						logger.info(" ***************update status for running call output xml>>>>>>>>>>>>>>");	
						runningInputXml=prepareAPUpdateInputXml("USR_0_WBG_AO_INTEGRATION_CALLS","status","'running'","wi_name='"+sWIName+"' and call_order='"+seq+"'",sCabname,sSessionId);
						logger.info(" ***************update status for runningInputXml>>>>>>>>>>>>>>>"+runningInputXml+" *****************************\n");	
						runningOutputXml=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",runningInputXml);
						logger.info(" ***************update status for running call output xml>>>>>>>>>>>>>>"+runningOutputXml);	
						if(sCall.contains("SIGNATURE_UPLOAD")){
							File file1 = new File(System.getProperty("file.separator")+"appl"+System.getProperty("file.separator")+"WMS");
							if (!file1.exists()) 
							{
								if (file1.mkdir()) 
								{

								} 
								else 
								{

								}
							}
							logger.info(" ***************Inside sign updload>>>>>>>>>>>>>>");	
							String ImageIndex = callXML.substring(callXML.indexOf("<IMAGEINDEX>")+12,callXML.indexOf("</IMAGEINDEX>"));
							String VolumeId = callXML.substring(callXML.indexOf("<VOLID>")+7,callXML.indexOf("</VOLID>"));
							logger.info(" ***************Inside 1111sign updload>>>>>>>>>>>>>>"+ImageIndex  + VolumeId );
							JPDBString oSiteName = new JPDBString("adcbedmsuatsite");
							logger.info(" ***************Inside2222 sign updload>>>>>>>>>>>>>>"+ImageIndex  + VolumeId );
							logger.info(" ***************Inside2222 oSiteName updload>>>>>>>>>>>>>>"+oSiteName  );
							try{
								CPISDocumentTxn.GetDocInFile_MT(null,sJtsIp,Short.parseShort(iJtsPort),sCabname,Short.parseShort("1"), Short.parseShort("8"), Integer.parseInt(ImageIndex),null,"/appl/WMS/"+sWIName+"_signature.tif",oSiteName);
							}catch(Exception e){
								logger.info("Exception from CPISDocumentTxn() method : " + e.toString());
								throw new Exception(e);
							} catch (JPISException e) {
								logger.info("Exception from CPISDocumentTxn() method : " + e.toString());
								e.printStackTrace();
							}
							logger.info(" ***************Inside22662222 afeter doculoand file" );
							String encodedBytes = downloadGIF("signature","/appl/WMS/",sWIName+"_signature.tif");
							logger.info(" ***************afeter doculoand file" );
							logger.info(" encodedBytes"+encodedBytes);	
							callXML=callXML.replaceAll("DUMMY_CUST_SIGN",encodedBytes);
							callXML = replaceCustomerIdInSignatureCall(callXML, sWIName , sJtsIp,iJtsPort,sCabname,sSessionId);

							// Edit by Naga - WebService Calls uing Socket 01_12_2020
							logger.info(" ***************xml SIGNATURE_UPLOAD call ::: "+callXML);
							sOutput = ConnectSocket.connectToSocket(callXML);
							logger.info(" ***************sOutput SIGNATURE_UPLOAD  ::: "+sOutput);
							//sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",callXML);
						}
						else if(sCall.contains("ChequeBookHandoff_WBG"))
						{
							if(chequebookCRReflect.equalsIgnoreCase("No"))
							{
								sOutput="<Option>ChequeBookHandoff_WBG</Option><Output><returnCode>0</returnCode><Status>Success</Status><errorDescription>Skipped</errorDescription>";
							}
							else
							{
								String sQuerychq= "SELECT PRODCODE, ACC_NO, TO_CHAR(TO_DATE(ACC_OPEN_DATE,'DD/MM/YYYY HH24:MI:SS'),'DD/MM/YYYY') AS ACC_OPEN_DATE, NO_OF_LEAVES FROM USR_0_WBG_AO_ACC_INFO WBG JOIN USR_0_PRODUCT_MASTER PROD ON WBG.PRODCODE= PROD.PRODUCT_CODE WHERE WI_NAME='"+sWIName+"' and PROD.CHEQUE_BOOK_FAC='Y' ORDER BY PRODCODE ASC";
								logger.info(" ***************input  sQuerychq  :"+sQuerychq+" *****************************");	
								if(!sQuerychq.equals(""))	
								{
									logger.info(" ***************inside if : *****************************");	
									String sInputchq=prepareAPSelectInputXml(sQuerychq,sCabname,sSessionId);
									sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputchq);
									logger.info(" ***************sOutput   ------------:"+sOutput);
									int totalRetrieved = Integer.parseInt(sOutput.substring(sOutput.indexOf("<TotalRetrieved>")+16,sOutput.indexOf("</TotalRetrieved>")));
									logger.info(" ***************totalRetrieved   ------------:"+totalRetrieved);

									if(totalRetrieved>0)
									{
										String query= "SELECT ELIGIBLECHQLEAVES FROM CHQBOOKVALIDATIONDATA WHERE WI_NAME = '"+sWIName+"' and rownum=1 order by createddate desc";
										logger.info(" ***************input  query  :"+query+" *****************************");	

										String NO_OF_LEAVES="";
										if(!query.equals(""))	
										{
											logger.info(" ***************inside if : *****************************");	
											String input = prepareAPSelectInputXml(query,sCabname,sSessionId);
											String output=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",input);
											logger.info(" ***************output   ------------:"+output);
											totalRetrieved = Integer.parseInt(output.substring(output.indexOf("<TotalRetrieved>")+16,output.indexOf("</TotalRetrieved>")));
											logger.info(" ***************totalRetrieved   ------------:"+totalRetrieved);
											if(totalRetrieved>0)
											{
												String eligibility=output.substring(output.indexOf("<ELIGIBLECHQLEAVES>")+19,output.indexOf("</ELIGIBLECHQLEAVES>"));

												query= "SELECT ChqLeaves FROM USR_0_CHQLEAVES WHERE Eligibility = '"+eligibility+"'";			
												logger.info(" ***************input  query  :"+query+" *****************************");	

												if(!query.equals(""))	
												{
													logger.info(" ***************inside if : *****************************");	
													input = prepareAPSelectInputXml(query,sCabname,sSessionId);
													output=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",input);
													logger.info(" ***************output   ------------:"+output);
													totalRetrieved = Integer.parseInt(output.substring(output.indexOf("<TotalRetrieved>")+16,output.indexOf("</TotalRetrieved>")));
													logger.info(" ***************totalRetrieved   ------------:"+totalRetrieved);
													if(totalRetrieved>0)
													{
														NO_OF_LEAVES=output.substring(output.indexOf("<CHQLEAVES>")+11,output.indexOf("</CHQLEAVES>"));

														if(NO_OF_LEAVES.equalsIgnoreCase("BAU"))
														{
															NO_OF_LEAVES=sOutput.substring(sOutput.indexOf("<NO_OF_LEAVES>")+14,sOutput.indexOf("</NO_OF_LEAVES>"));
														}
													}
												}

												XMLParser xp1 = new XMLParser(sOutput);
												int start = xp1.getStartIndex("Records", 0, 0);
												int deadEnd = xp1.getEndIndex("Records", start, 0);
												int noOfFields = xp1.getNoOfFields("Record", start,deadEnd);
												int end = 0;

												if(noOfFields > 0){
													for(int i = 1; i <= noOfFields; ++i){
														start = xp1.getStartIndex("Record", end, 0);
														end = xp1.getEndIndex("Record", start, 0);

														String ACC_NO=xp1.getValueOf("ACC_NO", start, end);
														String ACC_OPEN_DATE=xp1.getValueOf("ACC_OPEN_DATE", start, end);

														String sInput2=prepareAPUpdateInputXml("CHQBOOKVALIDATIONDATA","ACCOUNTNO","'"+ACC_NO+"'","WI_NAME='"+sWIName+"' and accountno is null and rownum=1",sCabname,sSessionId);
														logger.info(" ***************update CHQBOOKVALIDATIONDATA "+sInput2+" *****************************");
														String sOutput2=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput2);						
														logger.info(" ***************update sOutput for CHQBOOKVALIDATIONDATA "+sOutput2+" *****************************");

														String ProcParams_Chq = "'"+sWIName+"','1','WBG','"+ACC_NO+"','"+ACC_OPEN_DATE+"','"+NO_OF_LEAVES+"','U'";

														logger.info(" *************ProcParams_Chq : *****************************:"+ProcParams_Chq);

														String sInput_chq="<?xml version=\"1.0\"?><APProcedure_Input><Option>APProcedure</Option><ProcName>CHEQUEBOOKHANDOFF</ProcName><Params>"+ProcParams_Chq+"</Params><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APUpdate_Input>";								
														logger.info(" ***************ap procedure input CHEQUEBOOKHANDOFF"+sInput_chq);
														String sOutput_chq=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput_chq);	
														logger.info(" ***************ap procedure sOutput_chq CHEQUEBOOKHANDOFF"+sOutput_chq);
														String mainCode=sOutput_chq.substring(sOutput_chq.indexOf("<MainCode>")+10,sOutput_chq.indexOf("</MainCode>"));
														logger.info(" ***************maincode"+mainCode);

														if (("0").equalsIgnoreCase(mainCode))
														{
															sOutput="<Option>ChequeBookHandoff_WBG</Option><Output><returnCode>0</returnCode><Status>Success</Status><errorDescription>Success</errorDescription>"+sOutput_chq;
															logger.info(" ***************ap procedure sOutput if :"+sOutput);
														}else{
															sOutput="<Option>ChequeBookHandoff_WBG</Option><Output><returnCode>-1</returnCode><Status>Error while Executing</Status><errorDescription>Error while Executing</errorDescription>"+sOutput_chq;
															logger.info(" ***************ap procedure sOutput else :"+sOutput);
														}
													}
												}
											}
											else
											{
												sOutput="<Option>ChequeBookHandoff_WBG</Option><Output><returnCode>0</returnCode><Status>Success</Status><errorDescription>Skipped. Account without chequebook facility.</errorDescription>";
											}
										}
									}else{
										sOutput="<Option>ChequeBookHandoff_WBG</Option><Output><returnCode>-1</returnCode><Status>Error while Executing</Status><errorDescription>Error while Executing</errorDescription>";
									}
								}
							}

						}
						else if(sCall.contains("ChequeBook_"))
						{
							logger.info("Inside cheque book else if");
							callXML = replaceParametersInChequeBookCall(callXML, sWIName , sJtsIp,iJtsPort,sCabname,sSessionId);
							logger.info("Returned from cheque book else if : "+callXML);

							sOutput = ConnectSocket.connectToSocket(callXML);
						}
						else if(sCall.contains("AddDebitCard_"))
						{
							logger.info("Inside debit card else if");
							callXML = replaceParametersInDebitCardCall(callXML, sWIName , sJtsIp,iJtsPort,sCabname,sSessionId);
							logger.info("Returned from cheque book else if : "+callXML);
							String sQuery2= "SELECT AUS.CUSTID FROM USR_0_WBG_AO_AUS AUS inner join EXT_WBG_AO E on E.wi_name =AUS.wi_name WHERE E.WI_NAME ='"+sWIName+"' AND E.SOURCING_CHANNEL ='ITQAN_M' ORDER BY TO_NUMBER(AUS.AUSSERNO) ASC";	
							String sInput2 = prepareAPSelectInputXml(sQuery2,sCabname,sSessionId);	
							logger.info(" ***************input Fetch CUST ID from USR_0_WBG_AO_AUS table "+sInput2+" *****************************");
							if(!sInput2.equals(""))
							{
								sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput2);
								logger.info("Add debit card : sOutput :"+sOutput);
								XMLParser xp1 = new XMLParser(sOutput);
								int start = xp1.getStartIndex("Records", 0, 0);
								int deadEnd = xp1.getEndIndex("Records", start, 0);
								int noOfFields = xp1.getNoOfFields("Record", start,deadEnd);
								logger.info("Add debit card : noOfFields :"+noOfFields);
								int end = 0;
								if(noOfFields > 0){
									for(int i = 1; i <= noOfFields; ++i){
										start = xp1.getStartIndex("Record", end, 0);
										end = xp1.getEndIndex("Record", start, 0);
										String cid = xp1.getValueOf("CUSTID",start,end);
										logger.info("Add debit card : cid :"+cid);
										logger.info("Add debit card : Before callXML :"+callXML);
										callXML=callXML.replaceAll("#CUST_"+i+"#",cid);
										logger.info("Add debit card : After callXML :"+callXML);
									}
								}	
							}
							sOutput = ConnectSocket.connectToSocket(callXML);
							logger.info("Add debit card outputxml : "+sOutput);
						} else if(sCall.contains("CRS_AUS_COMMIT"))
						{
							logger.info(" inside CRS_AUS_COMMIT : ");
							String sQuery2= "SELECT CUSTID FROM USR_0_WBG_AO_AUS WHERE WI_NAME ='"+sWIName+"' ORDER BY TO_NUMBER(AUSSERNO) ASC";	
							String sInput2 = prepareAPSelectInputXml(sQuery2,sCabname,sSessionId);	
							logger.info(" ***************input Fetch CUST ID from USR_0_WBG_AO_AUS table "+sInput2+" *****************************");
							if(!sInput2.equals(""))
							{
								sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput2);
								logger.info("CRS_AUS_COMMIT : sOutput :"+sOutput);
								XMLParser xp1 = new XMLParser(sOutput);
								int start = xp1.getStartIndex("Records", 0, 0);
								int deadEnd = xp1.getEndIndex("Records", start, 0);
								int noOfFields = xp1.getNoOfFields("Record", start,deadEnd);
								logger.info("CRS_AUS_COMMIT : noOfFields :"+noOfFields);
								int end = 0;
								if(noOfFields > 0){
									for(int i = 1; i <= noOfFields; ++i){
										start = xp1.getStartIndex("Record", end, 0);
										end = xp1.getEndIndex("Record", start, 0);
										String cid = xp1.getValueOf("CUSTID",start,end);
										logger.info("CRS_AUS_COMMIT : cid :"+cid);
										logger.info("CRS_AUS_COMMIT : Before callXML :"+callXML);
										callXML=callXML.replaceAll("#CUST_"+i+"#",cid);
										logger.info("CRS_AUS_COMMIT : After callXML :"+callXML);
									}
								}	
							}
							sOutput = ConnectSocket.connectToSocket(callXML);
							logger.info("CRS_AUS_COMMIT outputxml : "+sOutput);

						} else if(sCall.contains("SEND_EMAIL"))
						{
							logger.info(" inside send email : ");
							String sQuery2= "SELECT CUSTID,DEBIT_CARD_NO FROM USR_0_WBG_AO_AUS WHERE WI_NAME ='"+sWIName+"' ORDER BY TO_NUMBER(AUSSERNO) ASC";	
							String sInput2 = prepareAPSelectInputXml(sQuery2,sCabname,sSessionId);			
							logger.info(" ***************input Fetch DEBIT_CARD_NO from USR_0_WBG_AO_AUS table "+sInput2+" *****************************");		
							if(!sInput2.equals(""))
							{
								sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput2);
								logger.info("SEND_EMAIL : sOutput :"+sOutput);
								Date date = new Date();
								SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
								String newdate = sdf.format(date);
								logger.info("SEND_EMAIL : new date :"+newdate);
								XMLParser xp1 = new XMLParser(sOutput);
								int start = xp1.getStartIndex("Records", 0, 0);
								int deadEnd = xp1.getEndIndex("Records", start, 0);
								int noOfFields = xp1.getNoOfFields("Record", start,deadEnd);
								logger.info("SEND_EMAIL : noOfFields :"+noOfFields);
								int end = 0;
								if(noOfFields > 0){
									for(int i = 1; i <= noOfFields; ++i){
										start = xp1.getStartIndex("Record", end, 0);
										end = xp1.getEndIndex("Record", start, 0);
										String cid = xp1.getValueOf("CUSTID",start,end);
										String debitCardNo = xp1.getValueOf("DEBIT_CARD_NO",start,end);
										//	String maskValue = maskDebitCardNo(debitCardNo);
										//	logger.info("SEND_EMAIL : cid :"+cid);
										logger.info("SEND_EMAIL : debitCardNo :"+debitCardNo);
										//	logger.info("SEND_EMAIL : maskValue :"+maskValue);
										//	String emailText =cid + "|" + maskValue + "|" + newdate;
										callXML=callXML.replaceAll("#CUST_"+i+"#",cid);
										logger.info("SEND_EMAIL : callXML :"+callXML);
										callXML=callXML.replaceAll("#EMAILTEXT_"+i+"#",debitCardNo);
										logger.info("SEND_EMAIL : callXML :"+callXML);


									}
								}	
							}
							sOutput = ConnectSocket.connectToSocket(callXML);
							logger.info("SEND_EMAIL outputxml : "+sOutput);

						}

						else if(sCall.indexOf("ECBValidation_WBG")>=0)
						{

							if(chequebookCRReflect.equalsIgnoreCase("No"))
							{
								sOutput="<Option>ECBValidation_WBG</Option><Output><returnCode>0</returnCode><Status>Success</Status><errorDescription>Skipped</errorDescription>";
								ecbRestFlag="N";
							}
							else
							{
								String query= "SELECT COUNT(DISTINCT(PRODCODE)) AS COUNT FROM USR_0_WBG_AO_ACC_INFO WBG JOIN USR_0_PRODUCT_MASTER PROD ON WBG.PRODCODE= PROD.PRODUCT_CODE WHERE PROD.CHEQUE_BOOK_FAC='Y' AND  WI_NAME = '"+sWIName+"'";
								logger.info(" ***************input  query  :"+query+" *****************************");		

								String sInputchq=prepareAPSelectInputXml(query,sCabname,sSessionId);
								String sOutput_modacc=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputchq);
								logger.info(" ***************sOutput   ------------:"+sOutput_modacc);
								int count = Integer.parseInt(sOutput_modacc.substring(sOutput_modacc.indexOf("<COUNT>")+7,sOutput_modacc.indexOf("</COUNT>")));
								logger.info(" ***************count   ------------:"+count);
								if(count>0)
								{
									// Edit by Naga - WebService Calls uing Socket 01-12-2020
									//Added by Shivanshu ATP-501
									if(!ecbRestFlag.equalsIgnoreCase("Y")){
										sOutput = ConnectSocket.connectToSocket(callXML);
										//sOutput=NGEjbClient.getSharedInstance().makeCall(callXML,sJtsIp,iJtsPort,"WebSphere");
									}
									//Added by Shivanshu ATP-501
									else {

										String url = "";	
										String clientId = "";	
										String clientSecret = "";	


										try {
											String query11 = "SELECT KEY, VALUE FROM BPM_CONFIG WHERE PROCESS_NAME ='WBG' AND KEY IN ('AECB_URL', 'AECB_CLIENT_ID', 'AECB_CLIENT_SECRET')";
											logger.info("Query: "+ query11);
											String inputXML=prepareAPSelectInputXml(query11, sCabname, sSessionId);
											String outputXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",inputXML);
											String mainCode = outputXML.substring(outputXML.indexOf("<MainCode>")+10,outputXML.indexOf("</MainCode>"));

											if(mainCode.equalsIgnoreCase("0")) {					
												logger.info("inside  maincode 0");
												int totalRetrieved = Integer.parseInt(outputXML.substring(outputXML.indexOf("<TotalRetrieved>")+16,outputXML.indexOf("</TotalRetrieved>")));
												logger.info(" ***************totalRetrieved   ------------:"+totalRetrieved);
												if(totalRetrieved>0) {
													XMLParser xp1 = new XMLParser(outputXML);
													int start = xp1.getStartIndex("Records", 0, 0);
													int deadEnd = xp1.getEndIndex("Records", start, 0);
													int noOfFields = xp1.getNoOfFields("Record", start, deadEnd);
													int end = 0;
													if(noOfFields > 0){
														for(int i = 1; i <= noOfFields; ++i) {
															start = xp1.getStartIndex("Record", end, 0);
															end = xp1.getEndIndex("Record", start, 0);

															String key = xp1.getValueOf("KEY", start, end);
															String value = xp1.getValueOf("VALUE", start, end);

															if ("AECB_URL".equals(key)) {
																url = value;
															} else if ("AECB_CLIENT_ID".equals(key)) {
																clientId = value;
															} else if ("AECB_CLIENT_SECRET".equals(key)) {
																clientSecret = value;
															}
														}
													}
												}
											}
										} catch (Exception e) {
											logger.info("Exception in getting key value data: "+ e.getMessage());
											e.printStackTrace();
										}


										String refInput="<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>select seq_webservice.nextval REFNO from dual</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
										String refOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",refInput);
										String refECBNO = refOutput.substring(refOutput.indexOf("<REFNO>")+7,refOutput.indexOf("</REFNO>"));
										logger.info(" refECBNO : "+refECBNO);
										sOutput = executeRestAPIJSON(url,clientId,clientSecret,callXML,sWIName+"_"+refECBNO);
										//callXML=callXML.replaceAll("\r\n","");
										try{		
											logger.info(" sOutput : "+sOutput);
											JsonElement jsonElement = JsonParser.parseString(sOutput);
											JsonObject jsonObject = jsonElement.getAsJsonObject();

											// Accessing values directly from JsonObject
											JsonElement dataElement = jsonObject.get("Data");
											JsonObject data = dataElement != null && !dataElement.isJsonNull() && dataElement.isJsonObject() ? dataElement.getAsJsonObject() : null;

											if (data != null) {
												JsonElement requestDataElement = data.get("RequestData");
												logger.info("ecb requestData: " + data.get("RequestData"));
												JsonObject requestData = requestDataElement != null && !requestDataElement.isJsonNull() && requestDataElement.isJsonObject() ? requestDataElement.getAsJsonObject() : null;

												if (requestData != null) {
													sysRefNumber = requestData.has("SysRefNumber") ? requestData.get("SysRefNumber").getAsString() : "DefaultSysRefNumber";
													errorCode = requestData.has("ErrorCode") ? requestData.get("ErrorCode").getAsString() : "DefaultErrorCode";
													ErrorDesc = requestData.has("ErrorDesc") ? requestData.get("ErrorDesc").getAsString() : "DefaultErrorDesc";

													logger.info("sysRefNumber: " + sysRefNumber);
													logger.info("errorCode: " + errorCode);
													logger.info("errorDesc: " + ErrorDesc);
												} else {
													logger.info("requestData is null or not an object");
												}

												JsonElement reportSummaryElement = data.get("ReportSummary");
												JsonObject reportSummary = reportSummaryElement != null && !reportSummaryElement.isJsonNull() && reportSummaryElement.isJsonObject() ? reportSummaryElement.getAsJsonObject() : null;

												if (reportSummary != null) {
													ecbExtractionDate = reportSummary.has("EcbExtractionDate") ? reportSummary.get("EcbExtractionDate").getAsString() : "DefaultDate";
													ecbReferenceNumber = reportSummary.has("EcbReferenceNumber") ? reportSummary.get("EcbReferenceNumber").getAsString() : "DefaultEcbRefNumber";
													ecbHitFlg = reportSummary.has("EcbHitFlg") ? reportSummary.get("EcbHitFlg").getAsString() : "DefaultHitFlag";

													logger.info("ecbExtractionDate: " + ecbExtractionDate);
													logger.info("ecbReferenceNumber: " + ecbReferenceNumber);
													logger.info("ecbHitFlg: " + ecbHitFlg);
												} else {
													logger.info("reportSummary is null or not an object");
												}

												JsonElement customerChequeInsightsElement = data.get("CustomerChequeInsights");
												JsonObject customerChequeInsights = customerChequeInsightsElement != null && !customerChequeInsightsElement.isJsonNull() && customerChequeInsightsElement.isJsonObject() ? customerChequeInsightsElement.getAsJsonObject() : null;

												if (customerChequeInsights != null) {
													totalCountCheque12M = customerChequeInsights.has("TotalCountCheque12M") ? customerChequeInsights.get("TotalCountCheque12M").getAsString() : "DefaultTotalCountCheque12M";
													totalCountBouncedCheque12M = customerChequeInsights.has("TotalCountBouncedCheque12M") ? customerChequeInsights.get("TotalCountBouncedCheque12M").getAsString() : "DefaultTotalCountBouncedCheque12M";
													//totalCountBouncedCheque12M = Integer.parseInt(totalCountBouncedCheque12M);

													logger.info("totalCountCheque12M: " + totalCountCheque12M);
													logger.info("totalCountBouncedCheque12M: " + totalCountBouncedCheque12M);
												} else {
													logger.info("customerChequeInsights is null or not an object");
												}

											} else {
												logger.info("data is null or not an object");
											}
										} catch (JsonSyntaxException e) {
											e.printStackTrace();
										}					

										if(ecbHitFlg.equalsIgnoreCase("Y")){
											ecbHit = 1;
										}
										errCase="0";
										sResVal = "";						
									}
								}
								else
								{
									sOutput="<Option>ECBValidation_WBG</Option><Output><returnCode>0</returnCode><Status>Success</Status><errorDescription>Skipped. Account without chequebook facility.</errorDescription>";
									ecbRestFlag="N";
								}
							}

						}
						else if(sCall.contains("BRMSEligibility_WBG")){
							if(chequebookCRReflect.equalsIgnoreCase("No"))
							{
								sOutput="<Option>BRMSEligibility_WBG</Option><Output><returnCode>0</returnCode><Status>Success</Status><errorDescription>Skipped</errorDescription>";
							}
							else
							{
								String sQuery5= "SELECT NOCHQBOUNCE, HITNOHIT FROM CHQBOOKVALIDATIONDATA WHERE WI_NAME = '"+sWIName+"' and rownum=1 order by createddate desc";
								String sInput2 = prepareAPSelectInputXml(sQuery5,sCabname,sSessionId);			
								logger.info("*************input Fetch NOCHQBOUNCE,HITNOHIT from CHQBOOKVALIDATIONDATA table "+sInput2+" **************************");		
								if(!sInput2.equals(""))
								{
									sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput2);

									logger.info(" **************fetch noOfChequeBounceInLast12Months and HitOrNoHit= "+sOutput+" **************************");

									if(!sOutput.equals(""))
									{			
										String mainCode = sOutput.substring(sOutput.indexOf("<MainCode>")+10,sOutput.indexOf("</MainCode>"));
										int totalRetrieved = Integer.parseInt(sOutput.substring(sOutput.indexOf("<TotalRetrieved>")+16,sOutput.indexOf("</TotalRetrieved>")));
										logger.info(" ***************totalRetrieved   ------------:"+totalRetrieved);

										if(totalRetrieved>0)
										{
											callXML=callXML.replaceAll("#Last12Months#",sOutput.substring(sOutput.indexOf("<NOCHQBOUNCE>")+13,sOutput.indexOf("</NOCHQBOUNCE>")));
											callXML=callXML.replaceAll("#HitOrNoHit#",sOutput.substring(sOutput.indexOf("<HITNOHIT>")+10,sOutput.indexOf("</HITNOHIT>")));

											// Edit by Naga - WebService Calls uing Socket 01_10_2020									
											sOutput = ConnectSocket.connectToSocket(callXML);
											//sOutput=NGEjbClient.getSharedInstance().makeCall(callXML,sJtsIp,iJtsPort,"WebSphere");
										}
										else
										{
											sOutput="<Option>BRMSEligibility_WBG</Option><Output><returnCode>0</returnCode><Status>Success</Status><errorDescription>Skipped. Account without chequebook facility</errorDescription>";
										}
									}
								}
							}
						}else{
							// Edit by Naga - WebService Calls uing Socket 01_10_2020
							logger.info(" ***************callXML   ------------:"+callXML);						
							sOutput = ConnectSocket.connectToSocket(callXML);
							//sOutput=NGEjbClient.getSharedInstance().makeCall(callXML,sJtsIp,iJtsPort,"WebSphere");	
						}

						logger.info(" ***************update status for running call Webservice output xml>>>>>>>>>>>>>>"+sOutput);
						//Added by SHivanshu ATP-501
						if(!ecbRestFlag.equalsIgnoreCase("Y") ){
							callXML=callXML.replaceAll("\r\n","");
							if (sCall.contains("Sanction_Screening_FSK")) {
								sStatus=sOutput.substring(sOutput.indexOf("<StatusCode>")+12,sOutput.indexOf("</StatusCode>"));
								logger.info(" **************311>>>>>>>>>>>>>>");
								sReason = sOutput.substring(sOutput.indexOf("<WINumber>")+10,sOutput.indexOf("</WINumber>"));
								ErrorDesc=sOutput.substring(sOutput.indexOf("<StatusMessage>")+15,sOutput.indexOf("</StatusMessage>"));	
							} else {
								sStatus=sOutput.substring(sOutput.indexOf("<returnCode>")+12,sOutput.indexOf("</returnCode>"));
								logger.info(" **************312>>>>>>>>>>>>>>");					
								ErrorDesc=sOutput.substring(sOutput.indexOf("<errorDescription>")+18,sOutput.indexOf("</errorDescription>"));	
							}
							logger.info(" **************313>>>>>>> sStatus : "+ sStatus +" & ErrorDesc : " +ErrorDesc);					

						} else {
							logger.info("ErrorDesc ECB ---# #"+ErrorDesc);
							if (ErrorDesc.equalsIgnoreCase("Success")){
								sStatus = "0";
							} else {
								sStatus = "-1";
							}
						}	
						if(sOutput.indexOf("<Reason>") >0)
						{
							sReason = sOutput.substring(sOutput.indexOf("<Reason>")+8,sOutput.indexOf("</Reason>"));
						}					
						if(sOutput.indexOf("<errorDescription>") >0)
						{
							logger.info("error description tag found---------");
							sErrorDescription = sOutput.substring(sOutput.indexOf("<errorDescription>")+18,sOutput.indexOf("</errorDescription>"));
							logger.info("sErrorDescription is:"+sErrorDescription);
						}

						if(sCall.indexOf("DEBITCARD_UPGRADE_DOWNGRADE")>=0 && ErrorDesc.indexOf("100901")>=0)
						{
							sOutput=sOutput.substring(0,sOutput.indexOf("<returnCode>")+12)+"0"+sOutput.substring(sOutput.indexOf("</returnCode>"),sOutput.length());
							errCase="1";
							sStatus="0";
						}
						else if(sCall.indexOf("SIGNATURE_UPLOAD")>=0 && sReason.indexOf("Image Already Exists")>=0)
						{
							sOutput=sOutput.substring(0,sOutput.indexOf("<returnCode>")+12)+"0"+sOutput.substring(sOutput.indexOf("</returnCode>"),sOutput.length());
							ErrorDesc =sReason;
							errCase="1";
							sStatus="0";
						}					
						else if(sCall.indexOf("KYC_UPDATE")>=0 && sErrorDescription.indexOf("Record already exists")>=0)
						{
							logger.info("condtn satisfied:"); 
							sOutput=sOutput.substring(0,sOutput.indexOf("<returnCode>")+12)+"0"+sOutput.substring(sOutput.indexOf("</returnCode>"),sOutput.length());
							logger.info("sOutput]]]]:"+sOutput); 
							ErrorDesc =sErrorDescription;
							errCase="1";
							sStatus="0";
						}
						else if(ErrorDesc.contains("Skipped") && (sCall.contains("ECBValidation_WBG") || sCall.contains("BRMSEligibility_WBG")))
						{
							sStatus = "0";
							sResVal = "";
							logger.info("sOutput contains skipped"); 
						}	
						else if(sCall.indexOf("ECBValidation_WBG")>=0)
						{
							String localDBSearchReq="";	
							String ReferenceNumber="";
							String userID="";
							String userName="";
							String tlno="";
							if(chequebookCRReflect.equalsIgnoreCase("No"))
							{
								sStatus = "0";
								ErrorDesc = "Skipped";
								sResVal = "";
							}
							else
							{
								//Added by Shivanshu ATP-501					 
								if(!ecbRestFlag.equalsIgnoreCase("Y")){
									sStatus=sOutput.substring(sOutput.indexOf("<returnCode>")+12,sOutput.indexOf("</returnCode>"));
									ErrorDesc=sOutput.substring(sOutput.indexOf("<errorDescription>")+18,sOutput.indexOf("</errorDescription>"));
									sResVal = "";
									logger.info("before localDBSearchReq :  ");

									localDBSearchReq=callXML.substring(callXML.indexOf("<LocalDBSearchReq>")+18,callXML.indexOf("</LocalDBSearchReq>"));	
									ReferenceNumber=callXML.substring(callXML.indexOf("<ReferenceNumber>")+17,callXML.indexOf("</ReferenceNumber>"));
									userID=callXML.substring(callXML.indexOf("<UserID>")+8,callXML.indexOf("</UserID>"));
									userName=callXML.substring(callXML.indexOf("<UserName>")+10,callXML.indexOf("</UserName>"));
									tlno=callXML.substring(callXML.indexOf("<TradeLicenseNumber>")+20,callXML.indexOf("</TradeLicenseNumber>"));

									logger.info("localDBSearchReq :  "+localDBSearchReq);
									logger.info("ReferenceNumber :  "+ReferenceNumber);
								}
								String params="<EIDA>#<TradeLicenseNumber>#<noOfChequeBounceInLast12Months>#<lastChequeBouncedDate>#<HitNoHit>";
								String[]param=params.split("#");
								if(sStatus.trim().equalsIgnoreCase("0"))	
								{

									String[] val=new String[param.length];
									//Added by Shivanshu ATP-501					 
									if(!ecbRestFlag.equalsIgnoreCase("Y")){
										for(int i=0;i<param.length;i++)
										{
											val[i]=sOutput.substring(sOutput.indexOf(param[i])+param[i].length(),sOutput.indexOf(param[i].replace("<","</")));
											logger.info(param[i]+"--INSIDE FOR LOOP--"+val[i]);

											if(param[i].equals("<EIDA>") && val[i].equals("null"))
											{
												logger.info("<EIDA> is null");
												val[i]="";
											}
										}
									}
									String CID = "";
									String sQuerychq= "SELECT CUSTID FROM EXT_WBG_AO WHERE WI_NAME = '"+sWIName+"'";				
									logger.info(" ***************input  sQuerychq  :"+sQuerychq+" *****************************");		

									String sInputchq=prepareAPSelectInputXml(sQuerychq,sCabname,sSessionId);
									String sOutput_modacc=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputchq);
									logger.info(" ***************sOutput   ------------:"+sOutput_modacc);
									int totalRetrieved = Integer.parseInt(sOutput_modacc.substring(sOutput_modacc.indexOf("<TotalRetrieved>")+16,sOutput_modacc.indexOf("</TotalRetrieved>")));
									logger.info(" ***************totalRetrieved   ------------:"+totalRetrieved);
									if(totalRetrieved>0)
									{
										CID = sOutput_modacc.substring(sOutput_modacc.indexOf("<CUSTID>")+8,sOutput_modacc.indexOf("</CUSTID>"));
									}


									sQuerychq= "SELECT PRODCODE, ACC_NO FROM USR_0_WBG_AO_ACC_INFO WBG JOIN USR_0_PRODUCT_MASTER PROD ON WBG.PRODCODE= PROD.PRODUCT_CODE WHERE PROD.CHEQUE_BOOK_FAC='Y' AND  WI_NAME = '"+sWIName+"' ORDER BY PRODCODE";
									logger.info(" ***************input  sQuerychq  :"+sQuerychq+" *****************************");		

									sInputchq=prepareAPSelectInputXml(sQuerychq,sCabname,sSessionId);
									sOutput_modacc=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputchq);
									logger.info(" ***************sOutput   ------------:"+sOutput_modacc);
									totalRetrieved = Integer.parseInt(sOutput_modacc.substring(sOutput_modacc.indexOf("<TotalRetrieved>")+16,sOutput_modacc.indexOf("</TotalRetrieved>")));
									logger.info(" ***************totalRetrieved   ------------:"+totalRetrieved);
									if(totalRetrieved>0)
									{
										XMLParser xp1 = new XMLParser(sOutput_modacc);
										int start = xp1.getStartIndex("Records", 0, 0);
										int deadEnd = xp1.getEndIndex("Records", start, 0);
										int noOfFields = xp1.getNoOfFields("Record", start,deadEnd);
										int end = 0;
										if(noOfFields > 0){
											for(int i = 1; i <= noOfFields; ++i){
												start = xp1.getStartIndex("Record", end, 0);
												end = xp1.getEndIndex("Record", start, 0);

												String ACC_NO=xp1.getValueOf("ACC_NO", start, end);
												String sInput1 = "";
												//Added by Shivanshu ATP-501
												if(!ecbRestFlag.equalsIgnoreCase("Y")){
													sInput1=prepareAPInsertInputXml("CHQBOOKVALIDATIONDATA","WI_NAME,WMSID,SOURCE,CID,ACCOUNTNO,EIDANO,TRADELNO,NOCHQBOUNCE,LATESTCHQBOUNCEDATE,ECBDATE,CREATEDDATE,MODIFYDATE,LOCALDB,ECBREFNO,ECBUSER,BPMUSER,HITNOHIT","'" + sWIName + "','1','WBG','"+CID+"','"+ACC_NO+"','"+val[0]+"','"+tlno+"','"+val[2]+"','"+val[3]+"',sysdate,sysdate,sysdate,'"+localDBSearchReq+"','"+ReferenceNumber+"','"+userID+"','"+userName+"','"+val[4]+"'",sCabname,sSessionId);
													//Added by Shivanshu ATP-501
												}else {
													sInput1=prepareAPInsertInputXml("CHQBOOKVALIDATIONDATA","WI_NAME,WMSID,SOURCE,CID,ACCOUNTNO,EIDANO,TRADELNO,NOCHQBOUNCE,LATESTCHQBOUNCEDATE,ECBDATE,CREATEDDATE,MODIFYDATE,LOCALDB,ECBREFNO,ECBUSER,BPMUSER,HITNOHIT","'" + sWIName + "','1','WBG','"+CID+"','"+ACC_NO+"','','','"+totalCountBouncedCheque12M+"','',sysdate,sysdate,sysdate,'Y','"+ecbReferenceNumber+"','','','"+ecbHit+"'",sCabname,sSessionId);
												}
												logger.info(" ***************insert in CHQBOOKVALIDATIONDATA "+sInput1+" *****************************");
												String sOutput2=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput1);						
												logger.info(" ***************insert sOutput for data in CHQBOOKVALIDATIONDATA "+sOutput2+" *****************************");	
											}
										}
									}

									errCase="0";
								}
							}
							//}
						}

						else if(sCall.indexOf("BRMSEligibility_WBG")>=0)
						{
							if(chequebookCRReflect.equalsIgnoreCase("No"))
							{
								sStatus = "0";
								ErrorDesc = "Skipped";
								sResVal = "";
							}
							else
							{
								sStatus=sOutput.substring(sOutput.indexOf("<returnCode>")+12,sOutput.indexOf("</returnCode>"));
								ErrorDesc=sOutput.substring(sOutput.indexOf("<errorDescription>")+18,sOutput.indexOf("</errorDescription>"));
								sResVal = "";
								logger.info("before eligibility :  ");
								if(sStatus.trim().equalsIgnoreCase("0"))
								{		
									errCase="0";
									String eligibility=sOutput.substring(sOutput.indexOf("<eligibility>")+13,sOutput.indexOf("</eligibility>"));
									logger.info("eligibility :  "+eligibility);

									if(eligibility.equalsIgnoreCase("N"))
									{
										sStatus = "-1";
										ErrorDesc = "Customer is not eligible to open an account. Bounced cheques are more than 3.";
										sResVal = "";
										brmsEligibleFlag="N";
									}
								}
							}
						}
						else if(sCall.indexOf("ChequeBookHandoff_WBG")>=0 && chequebookCRReflect.equalsIgnoreCase("No"))
						{
							sStatus = "0";
							ErrorDesc = "Skipped";
							sResVal = "";
						}
						else if(sCall.indexOf("SaveFatca_WBG")>=0 && sErrorDescription.indexOf("Record already exists")>-1)
						{
							logger.info("condtn satisfied FATCA:"); 
							sOutput=sOutput.substring(0,sOutput.indexOf("<returnCode>")+12)+"0"+sOutput.substring(sOutput.indexOf("</returnCode>"),sOutput.length());
							logger.info("sOutput FATCA]]]]:"+sOutput); 
							ErrorDesc =sErrorDescription;
							errCase="1";
							sStatus="0";
							logger.info("ErrorDesc:"+ErrorDesc+"--errCase--"+errCase+"--sStatus--"+sStatus); 
						}
						else if(sCall.indexOf("SMSRegistration_WBG")>=0) 
						{
							sStatus=sOutput.substring(sOutput.indexOf("<returnCode>")+12,sOutput.indexOf("</returnCode>"));
							ErrorDesc=sOutput.substring(sOutput.indexOf("<errorDescription>")+18,sOutput.indexOf("</errorDescription>"));
							if(ErrorDesc.toUpperCase().contains("CUSTOMER HAS BEEN ALREADY REGISTERED FOR SMS BANKING.") || ErrorDesc.toUpperCase().contains("FAILED TO REGISTER THE CUSTOMER. PLEASE TRY LATER.")){
								sStatus = "0";
							}
						} 
						else if(sCall.indexOf("EstatementRegistration_WBG_")>=0) 
						{
							sStatus=sOutput.substring(sOutput.indexOf("<returnCode>")+12,sOutput.indexOf("</returnCode>"));
							ErrorDesc=sOutput.substring(sOutput.indexOf("<errorDescription>")+18,sOutput.indexOf("</errorDescription>"));
							if(ErrorDesc.toUpperCase().contains("NON MODIFIABLE FIELD EXISTS IN REQUEST")){
								sStatus = "0";
							}
						}
						else if(sCall.indexOf("ModMIBRegistration_WBG")>=0) 
						{
							sStatus=sOutput.substring(sOutput.indexOf("<returnCode>")+12,sOutput.indexOf("</returnCode>"));
							ErrorDesc=sOutput.substring(sOutput.indexOf("<errorDescription>")+18,sOutput.indexOf("</errorDescription>"));

						}
						//Added by SHIvanshu for FSK HANDLING
						else if(sCall.indexOf("Sanction_Screening_FSK")>=0
								&& ErrorDesc.indexOf("Workitem Created Successfully")>=0) {
							String trsdFlag = "";
							String outputEXT = APCallCreateXML.APSelect("SELECT TRSD_FLAG FROM EXT_WBG_AO WHERE WI_NAME = '"+sWIName+"'");
							XMLParser xp = new XMLParser(outputEXT);
							logger.info("BPM_WBG_RETRY_EVENT outputEXT TotalRetrieved :" + xp.getValueOf("TotalRetrieved"));
							int noOfFields = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
							if (noOfFields == 1) {
								trsdFlag = xp.getValueOf("TRSD_FLAG");
							}
							logger.info("BPM_WBG_RETRY_EVENT outputEXT trsdFlag :" + trsdFlag);
							if (trsdFlag.equalsIgnoreCase("N") || trsdFlag.equalsIgnoreCase("A")) {
								errCase="0";
								sStatus="0";
								sErrorDescription = ErrorDesc;
							} else {
								errCase="1";
								sStatus="-1";
								sErrorDescription = "Match Case Found. Move WI To TRSD Queue";
							}
						}
						else{
							logger.info("inside elseeErrorDesc:"+ErrorDesc+"--errCase--"+errCase+"--sStatus--"+sStatus); 
							if(sOutput.indexOf("<Status>")>0)
							{
								logger.info("status>0"); 
								ErrorDesc=ErrorDesc+"|"+sOutput.substring(sOutput.indexOf("<Status>")+8,sOutput.indexOf("</Status>"));
								logger.info(" afterstatus>0"); 
							}
							if(sOutput.indexOf("<Reason>")>0)
							{
								logger.info("Reason>0"); 
								ErrorDesc=ErrorDesc+"|"+sReason;
							}
							errCase="0";
							logger.info("errCase=0"); 
						}
						logger.info("before returnXML");

						returnXML=sOutput;
						logger.info("middle returnXML"); 
						returnXML=returnXML.replace(",","");
						logger.info("after returnXML"); 
						//Added by Shivanshu ATP-501
						//if(!ecbRestFlag.equalsIgnoreCase("Y")){
						String tableName = "";
						if (sourcingChannel.equalsIgnoreCase("ITQAN")){
							tableName = "USR_0_WBG_ITQAN_INTEGRATION_CONFIG";
						} else {
							tableName = "USR_0_WBG_AO_INTGRTION_CONFIG";
						}
						sInput=prepareAPSelectInputXml("select OUTPUTTAGS,RETURNLABEL,UPDATEPROC,PROCPARAMS,OUTPUTQUERY from "+tableName+" where instr('"+normalizeStringHandleComma(sCall)+"',calltype)>0",sCabname,sSessionId);
						logger.info(" ***************select for USR_0_WBG_AO_INTGRTION_CONFIG call "+sInput+" *****************************\n");	
						String paramOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
						logger.info(" ***************select for USR_0_WBG_AO_INTGRTION_CONFIG call "+paramOutput+" *****************************\n");
						String noRecord = paramOutput.substring(paramOutput.indexOf("<TotalRetrieved>")+16,paramOutput.indexOf("</TotalRetrieved>"));
						logger.info( "call name :: "+sCall+", noRecord value :: "+noRecord+"\n");

						if(!noRecord.equalsIgnoreCase("0"))
						{				

							String params=paramOutput.substring(paramOutput.indexOf("<OUTPUTTAGS>")+12,paramOutput.indexOf("</OUTPUTTAGS>"));
							String outputquery="";
							outputquery=paramOutput.substring(paramOutput.indexOf("<OUTPUTQUERY>")+13,paramOutput.indexOf("</OUTPUTQUERY>"));
							String returnLabel=paramOutput.substring(paramOutput.indexOf("<RETURNLABEL>")+13,paramOutput.indexOf("</RETURNLABEL>"));						
							String ProcName=paramOutput.substring(paramOutput.indexOf("<UPDATEPROC>")+12,paramOutput.indexOf("</UPDATEPROC>"));
							String ProcParams=paramOutput.substring(paramOutput.indexOf("<PROCPARAMS>")+12,paramOutput.indexOf("</PROCPARAMS>"));

							String[]param=params.split("#");
							logger.info( "call name :: "+sCall+", sStatus value :: "+sStatus+"\n");
							logger.info( "call name :: "+sCall+", errCase value :: "+errCase+"\n");
							logger.info( "call name :: "+sCall+", param length value :: "+param.length+"\n");

							if((sStatus.trim().equalsIgnoreCase("0") || brmsEligibleFlag.equals("N")) && errCase.equalsIgnoreCase("0"))	{

								String[] val=new String[param.length];
								//Added by Shivanshu ATP-501
								if(!ecbRestFlag.equalsIgnoreCase("Y")){
									for(int i=0;i<param.length;i++)
									{
										logger.info("INSIDE FOR LOPP " + param[i] +  "   sOutput "  + sOutput);
										val[i]=sOutput.substring(sOutput.indexOf(param[i])+param[i].length(),sOutput.indexOf(param[i].replace("<","</")));
										logger.info("INSIDE FOR LOPP--1"+val[i]);
										ProcParams=ProcParams.replace("$param"+(i+1)+"$",val[i]);
										outputquery=outputquery.replace("$param"+(i+1)+"$",val[i]);
									}
								} else {
									val[0] = ErrorDesc;
								}	
								logger.info(" ***************select for USR_0_WBG_AO_INTGRTION_CONFIG call "+outputquery+" *****************************\n");
								String outputValue="";
								if(!outputquery.equalsIgnoreCase(""))
								{
									logger.info(" ***************select for USR_0_WBG_AO_INTGRTION_CONFIG call1 "+outputquery+" *****************************\n");
									sInput=prepareAPSelectInputXml(outputquery,sCabname,sSessionId);								
									sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);	
									outputValue=sOutput.substring(sOutput.indexOf("<DESCRIPTION>")+13,sOutput.indexOf("</DESCRIPTION>"));
								}							
								if(outputValue.equalsIgnoreCase(""))
								{
									outputValue=val[0];
								}


								sInput=prepareAPUpdateInputXml("USR_0_WBG_AO_INTEGRATION_CALLS","response","'"+returnLabel+outputValue+"'","wi_name='"+sWIName+"' and call_order='"+ seq+"'",sCabname,sSessionId);

								logger.info(" *************** sInput  "+sInput+" *****************************\n");	
								sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);	
								logger.info(" *************** sOutput  "+sOutput+" *****************************\n");	

								sResVal=returnLabel+outputValue;
								if(!ProcName.equalsIgnoreCase(""))
								{						
									ProcParams=ProcParams.replace("$winame$",sWIName);
									logger.info(" ***************ap ProcParams input "+ProcParams);
									ProcParams=ProcParams.replace("$slno$",SLNO);
									logger.info(" ***************ap ProcParams input "+ProcParams);
									ProcParams=ProcParams.replace("$serno$",seq);
									logger.info(" ***************ap ProcParams input "+ProcParams);
									sInput="<?xml version=\"1.0\"?><APProcedure_Input><Option>APProcedure</Option><ProcName>"+ProcName+"</ProcName><Params>"+ProcParams+"</Params><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APUpdate_Input>";								
									logger.info(" ***************ap procedure input "+sInput);
									sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);	
									logger.info(" ***************ap procedure sOutput"+sOutput);
								}
							}

						}
						//}
						String status="";
						if(sStatus.trim().equalsIgnoreCase("1")||sStatus.trim().equalsIgnoreCase("")||sStatus.trim().equalsIgnoreCase("2") ||sStatus.trim().equalsIgnoreCase("-1"))
							status="Error";
						else
							status="Success";				


						ErrorDesc=ErrorDesc.replaceAll(",","");	
						if(status.equalsIgnoreCase("Success") && errCase.equalsIgnoreCase("0"))
							ErrorDesc="";
						else if(errCase.equalsIgnoreCase("1"))
						{
							ErrorDesc="Ref No:"+REFNO+"|"+ErrorDesc;
						}
						else
							ErrorDesc="Ref No:"+REFNO+"|"+ErrorDesc;

						if(ErrorDesc.indexOf("Record already exists")>=0)
							status="Success";		

						sInput=prepareAPUpdateInputXml("USR_0_WBG_AO_INTEGRATION_CALLS","status,output_xml,ERRORDESC","'"+status+"','"+returnXML +"','"+ErrorDesc+"'","wi_name='"+sWIName+"' and call_order='"+ seq+"' and call_name='"+ normalizeStringHandleComma(sCall)+"'",sCabname,sSessionId);
						logger.info(" ***************Integration Update sInput"+sInput);
						sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
						logger.info(" ***************Integration Update sOutput"+sOutput);
						logger.info(" ***************jsp sOutput:"+sStatus+"#"+sResVal+"#"+ErrorDesc);					
						logger.info(sStatus+"#"+sResVal+"#"+ErrorDesc);					

					}
				}		
			}
			else{
			}
		}
		
	}
				
	
	catch(Exception e)
	{
		sInput=prepareAPUpdateInputXml("USR_0_WBG_AO_INTEGRATION_CALLS","status","'Error'","wi_name='"+sWIName+"' and call_order='"+ seq+"' and call_name='"+ normalizeStringHandleComma(sCall)+"'",sCabname,sSessionId);
		sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
		logger.info(" ***************Exception ### "+sOutput+" *****************************");
		sInput=prepareAPInsertInputXml("USR_0_INTEGRATION_VALUES","wi_name,exception_error","'" + sWIName + "','"+e.getMessage()+"'",sCabname,sSessionId);
		logger.info(" ***************Exception "+sInput+" *****************************");
		sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
		logger.info("Error#-1#"+e.getMessage());
		e.printStackTrace();
	}
	return sStatus;
}


private static String prepareAPSelectInputXml(String sQuery, String sCabname, String sSessionId){	
	return "<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+sQuery+"</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
}

private static String prepareAPInsertInputXml(String tableName,String colname,String sValues, String sCabname, String sSessionId){	
	return "<?xml version=\"1.0\"?><APInsert_Input><Option>APInsert</Option><TableName>"+tableName+"</TableName><ColName>"+colname+"</ColName><Values>"+sValues+"</Values><EngineName>" + sCabname + "</EngineName>" +    "<SessionId>" + sSessionId + "</SessionId></APInsert_Input>";
}

private static String prepareAPUpdateInputXml(String tableName,String colname,String sValues,String whrcls, String sCabname, String sSessionId){	
	return "<?xml version=\"1.0\"?><APUpdate_Input><Option>APUpdate</Option><TableName>"+tableName+"</TableName><ColName>"+colname+"</ColName><Values>"+sValues+"</Values><WhereClause>"+whrcls+"</WhereClause><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APUpdate_Input>";
}

private static String prepareAPProcInputXml(String ProcName,String ProcParams, String sCabname, String sSessionId){	

	return "<?xml version=\"1.0\"?><APProcedure_Input><Option>APProcedure</Option><ProcName>"+ProcName+"</ProcName><Params>"+ProcParams+"</Params><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APUpdate_Input>";
}


private static String getCIDandCIDCreationDate(String sWIName,String sJtsIp,String iJtsPort,String sSessionId,String sCabname){
	logger.info(" In getCIDandCIDCreationDate "+ sWIName);
	String result="";
	try{
		
		String sInput1 = prepareAPSelectInputXml("select EXISTING_CUST from ext_wbg_ao where wi_name='"+sWIName+"'",sCabname,sSessionId);
		logger.info(" In sInput get cid "+ sInput1);
		String paramOutput1=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput1);
		String existingCust = paramOutput1.substring(paramOutput1.indexOf("<EXISTING_CUST>")+15,paramOutput1.indexOf("</EXISTING_CUST>"));
		if(existingCust.equalsIgnoreCase("NTB"))
		{
			String  sInput=prepareAPSelectInputXml("select response,request_datetime from usr_0_wbg_ao_integration_calls where call_name like '%CustomerCreation_WBG%' and status='Success' and wi_name='"+sWIName+"' and rownum <= 1",sCabname,sSessionId);
			logger.info(" In sInput get cid "+ sInput);
			String paramOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
		
			String noRecord = paramOutput.substring(paramOutput.indexOf("<TotalRetrieved>")+16,paramOutput.indexOf("</TotalRetrieved>"));
			String sRecord = paramOutput.substring(paramOutput.indexOf("<Record>")+8,paramOutput.indexOf("</Record>"));
			logger.info(" In getCIDandCIDCreationDate noRecord "+ noRecord);
			if(!noRecord.equalsIgnoreCase("0"))
			{
					//CUST ID:10657391
					result = sRecord.substring(sRecord.indexOf("<RESPONSE>")+10,sRecord.indexOf("</RESPONSE>"));
					logger.info("result response  "+ result);
					result = result.replaceAll("CUST ID:","");
					logger.info("result after replace  "+ result);
					result = existingCust+"#"+result+"#"+sRecord.substring(sRecord.indexOf("<REQUEST_DATETIME>")+18,sRecord.indexOf("</REQUEST_DATETIME>"));
					logger.info("result last  "+ result);
			}	
		}
		else
		{
				
				String sInput2 = prepareAPSelectInputXml("select CUSTID from ext_wbg_ao where wi_name='"+sWIName+"'",sCabname,sSessionId);
				logger.info(" In sInput get cid "+ sInput2);
				String paramOutput2=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput2);
				String noRecord1 = paramOutput2.substring(paramOutput2.indexOf("<TotalRetrieved>")+16,paramOutput2.indexOf("</TotalRetrieved>"));
				if(!noRecord1.equalsIgnoreCase("0"))
				{
				result=paramOutput2.substring(paramOutput2.indexOf("<CUSTID>")+8,paramOutput2.indexOf("</CUSTID>"));
				result=existingCust+"#"+result+"#"+"";
				logger.info("result last  "+ result);
				}
		}
	}
	catch(Exception e){
		e.printStackTrace();
	}
	
	
	return result;
}

public static String replaceParametersInDebitCardCall(String callXml, String sWIName , String sJtsIp, String iJtsPort,String sCabname,String sSessionId)
{
	try{
		logger.info("replaceParametersInDebitCardCall : callXml : "+callXml);
		String cardPrimaryAccountNumber = getTagValueFromXML(callXml,"CardPrimaryAccount","");
		logger.info("cardPrimaryAccountNumber "+cardPrimaryAccountNumber);
		
		if(cardPrimaryAccountNumber.contains("#CardPrimaryAccountNumber_"))
		{
			String accountInfoQuery = "SELECT A.ACC_NO "
					+ "FROM USR_0_WBG_AO_ACC_INFO A, EXT_WBG_AO B WHERE A.WI_NAME = B.WI_NAME AND B.WI_NAME = '"
					+ sWIName
					+ "' "
					+ "AND UPPER(DEBIT_CARD_REQUEST) = UPPER('Y') ORDER BY A.SNO";
			
					
			logger.info("accountInfoQuery "+accountInfoQuery);
			String temp = "";
			int counter =-1;
			if(cardPrimaryAccountNumber.contains("#CardPrimaryAccountNumber_"))
			{
				temp = cardPrimaryAccountNumber.split("_")[1];
				logger.info("temp: "+temp);
				counter =Integer.parseInt(temp.substring(0,1));
				logger.info("counter : "+counter);
			}
			
			String sInputXML = prepareAPSelectInputXml(accountInfoQuery,sCabname,sSessionId);
			logger.info("sInputXML : "+sInputXML);
			String sOutputXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXML);
			logger.info("sOutputXML : "+sOutputXML);
			String outputTag = getTagValueFromXML(sOutputXML,"Output","");
			logger.info("outputTag : "+outputTag);
			int totalRows = Integer.parseInt(getTagValueFromXML(outputTag,"TotalRetrieved",""));
			logger.info("totalRows : "+totalRows);
			String recordsTag =  getTagValueFromXML(outputTag,"Records","");
			logger.info("recordsTag : "+recordsTag);
			String pos ="0";
			String id = "";
			for(int i=1;i<=totalRows;i++)
			{
				logger.info("i : "+i);
				String record = getTagValueFromXML(recordsTag,"Record",pos);
				logger.info("record : "+record);
			
				if(i==counter)
				{
					if(cardPrimaryAccountNumber.contains("#CardPrimaryAccountNumber_"))
					{
						id= getTagValueFromXML(record,"ACC_NO","");
						String toBeReplaced="#CardPrimaryAccountNumber_"+counter+"#";
						logger.info("id "+id);
						logger.info("toBeReplaced "+toBeReplaced);
						callXml=callXml.replaceAll(toBeReplaced,id);
						logger.info("final debit callXml "+callXml);
						
					}
					break;
					
				}
				pos=Integer.toString(record.length()-1);
				logger.info("pos : "+pos);
			
			}
			
		}
		
			
		logger.info("callXml : "+callXml);
		
		return callXml;
	}
	catch(Exception e)
	{
		logger.info("ERROR: "+e.getMessage());
		e.printStackTrace();
	}
	return null;
}

public static String replaceParametersInChequeBookCall(String callXml, String sWIName , String sJtsIp, String iJtsPort,String sCabname,String sSessionId)
{
	
	try{
		
		logger.info("replaceParametersInChequeBookCall : callXml : "+callXml);
		String customerNumber = getTagValueFromXML(callXml,"customerNumber","");
		logger.info("customerNumber "+customerNumber);
		String accountNumber = getTagValueFromXML(callXml,"accountNumber","");
		logger.info("accountNumber "+accountNumber);
		if(customerNumber.contains("#CustomerNumber_") || accountNumber.contains("#AccountNumber_"))
		{
			String accountInfoQuery = "SELECT B.CUSTID,A.ACC_NO "
					+ "FROM USR_0_WBG_AO_ACC_INFO A, EXT_WBG_AO B "
					+ "WHERE A.WI_NAME = B.WI_NAME AND B.WI_NAME = '"
					+ sWIName
					+ "' AND UPPER(CHEQUEREQ) = UPPER('Y') ORDER BY A.SNO";
					
			logger.info("accountInfoQuery "+accountInfoQuery);
			String temp = "";
			if(customerNumber.contains("#CustomerNumber_"))
			{
				temp = customerNumber.split("_")[1];
			}
			else
			{
				temp = accountNumber.split("_")[1];
			}
			//String temp = customerNumber.split("_")[1];
			logger.info("temp: "+temp);
			int counter =Integer.parseInt(temp.substring(0,1));
			logger.info("counter : "+counter);
			String sInputXML = prepareAPSelectInputXml(accountInfoQuery,sCabname,sSessionId);
			logger.info("sInputXML : "+sInputXML);
			String sOutputXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXML);
			logger.info("sOutputXML : "+sOutputXML);
			String outputTag = getTagValueFromXML(sOutputXML,"Output","");
			logger.info("outputTag : "+outputTag);
			int totalRows = Integer.parseInt(getTagValueFromXML(outputTag,"TotalRetrieved",""));
			logger.info("totalRows : "+totalRows);
			String recordsTag =  getTagValueFromXML(outputTag,"Records","");
			logger.info("recordsTag : "+recordsTag);
			String pos ="0";
			String id = "";
			for(int i=1;i<=totalRows;i++)
			{
				logger.info("i : "+i);
				String record = getTagValueFromXML(recordsTag,"Record",pos);
				logger.info("record : "+record);
			
				if(i==counter)
				{
					if(customerNumber.contains("#CustomerNumber_"))
					{
						id= getTagValueFromXML(record,"CUSTID","");
						String toBeReplaced="#CustomerNumber_"+counter+"#";
						logger.info("id "+id);
						logger.info("toBeReplaced "+toBeReplaced);
						callXml=callXml.replaceAll(toBeReplaced,id);
						logger.info("final callXml "+callXml);
						
					}
					if(accountNumber.contains("#AccountNumber_"))
					{
						id= getTagValueFromXML(record,"ACC_NO","");
						String toBeReplaced="#AccountNumber_"+counter+"#";
						logger.info("id "+id);
						logger.info("toBeReplaced "+toBeReplaced);
						callXml=callXml.replaceAll(toBeReplaced,id);
						logger.info("final callXml "+callXml);
						
					}
					break;
					
				}
				pos=Integer.toString(record.length()-1);
				logger.info("pos : "+pos);
			
			}
			
		}
		
		logger.info("callXml : "+callXml);
		
		return callXml;
	}
	catch(Exception e)
	{
		logger.info("ERROR: "+e.getMessage());
		e.printStackTrace();
	}
	return null;
	
}


public static String replaceCustomerIdInSignatureCall(String callXml, String sWIName , String sJtsIp, String iJtsPort,String sCabname,String sSessionId)
{
	
	try{
		logger.info("replaceCustomerIdInSignatureCall : callXml : "+callXml);
		String inputXML = callXml;
		logger.info("replaceCustomerIdInSignatureCall : inputXML : "+inputXML);
		String custId = getTagValueFromXML(inputXML,"CUST_ID","");
		//String custId = getTagValueFromXML("","CUST_ID","");
		
		logger.info("custId 121212: "+custId);
	
		if(custId.contains("#CustomerID_"))
		{
			logger.info("custId 12121211: "+custId);
			String temp = custId.split("_")[1];
			logger.info("custId 12121233: "+temp);
			int counter =Integer.parseInt(temp.substring(0,1));
			logger.info("custId 12121244: "+custId);
			logger.info("counter : "+counter);
		
			String query = "SELECT DOC.IMAGEINDEX IMAGEINDX,DOC.VOLUMEID VOLID,AUS.CUSTFULLNAME_IT CUSTNAME,"
							+ "AUS.CUSTID CIF ,AUS.AUSSERNO SRNO FROM USR_0_WBG_AO_AUS AUS ,"
							+ "USR_0_WBG_AO_DOC DOC WHERE AUS.WI_NAME =DOC.WI_NAME  AND "
							+ "AUS.CUSTFULLNAME_IT=DOC.CUSTOMERNAME AND "
							+ "DOC.DOCUMENTNAME='Signature' AND DOC.WI_NAME='"
							+ sWIName + "' "
							+ "ORDER BY AUS.AUSSERNO";
							
			logger.info("query : "+query);
			
			String sInputXML = prepareAPSelectInputXml(query,sCabname,sSessionId);
			logger.info("sInputXML : "+sInputXML);
			String sOutputXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXML);
			logger.info("sOutputXML : "+sOutputXML);
			String outputTag = getTagValueFromXML(sOutputXML,"Output","");
			logger.info("outputTag : "+outputTag);
			int totalRows = Integer.parseInt(getTagValueFromXML(outputTag,"TotalRetrieved",""));
			logger.info("totalRows : "+totalRows);
			String recordsTag =  getTagValueFromXML(outputTag,"Records","");
			logger.info("recordsTag : "+recordsTag);
			
			String id = "";
			int startindex=0;
			int endindex=0;
			for(int i=1;i<=totalRows;i++)
			{
				String pos ="0";
				logger.info("i : "+i);
				String record = getTagValueFromXML(recordsTag,"Record",pos);
				logger.info("record : "+record);
				
				startindex = recordsTag.indexOf("</Record>")+9;
				endindex = recordsTag.lastIndexOf("</Record>")+9;
		
				recordsTag=recordsTag.substring(startindex, endindex);
				logger.info("recordsTag updated :  : "+recordsTag);
			
				if(i==counter)
				{
					id= getTagValueFromXML(record,"CIF","");
					String toBeReplaced="#CustomerID_"+counter+"#";
					logger.info("id "+id);
					logger.info("toBeReplaced "+toBeReplaced);
					callXml=callXml.replaceAll(toBeReplaced,id);
					logger.info("final callXml "+callXml);
					return callXml;
				}
				pos=Integer.toString(record.length()-1);
				logger.info("pos : "+pos);
			
			}
			
					
		}
		logger.info("custId does not contains " +custId);
		
	}
	catch(Throwable e)
	{
		logger.info(e.getMessage());
		e.printStackTrace();
		
	}
	return callXml;
						
}

public static String getTagValueFromXML(String xml, String tagName,String position)
{
	logger.info("getTagValueFromXML ");
	int tagNameLength=tagName.length();
	int pos =0;
	if(!position.equals("")) {
		pos = Integer.parseInt(position);
	}
	
	String value = 
		xml.substring(xml.indexOf("<"+tagName+">",pos)+tagNameLength+2,
					  xml.indexOf("</"+tagName+">",pos));
					  

	logger.info("value :"+value);
    return value;
	
		//return "";

}
public String maskDebitCardNo(String debitCardNo)
{
		logger.info("inside maskDebitCardNo ");
		int total = debitCardNo.length();
		int strtlen = 6; int endlen = 4;
		int masklen = total - (strtlen+endlen);
		StringBuffer sb = new StringBuffer(debitCardNo.substring(0, strtlen));
		for(int i=0;i<masklen; i++){
			sb.append("*");
		}
		sb.append(debitCardNo.substring(strtlen+masklen,total));
		String masked = sb.toString();
		logger.info("masked :"+masked);
    return masked;
}

public static String downloadGIF(String sDocName,String sTemppath, String snewFile) throws Exception
    {
		
		logger.info("sTemppath : "+sTemppath);
		StringBuilder sEncodedBytes = new StringBuilder();
		double totalSize = 0;
		if (sDocName.equalsIgnoreCase("signature")) {
			File file = null;
			FileInputStream fis = null;
			ByteArrayOutputStream bos = null;
			RandomInputStream ris = null;
			File inputFile = null;
			
			try {				
				String outFolder = "";
				logger.info("sTemppath : "+sTemppath);
				logger.info("snewFile : "+snewFile);				
				RandomInputStreamSource riss = new RandomInputStreamSource(sTemppath + snewFile);
				ris = riss.getStream();
				logger.info("after riss.getStream()");				
				int imageType = NIPLJ.getFileFormat(ris);
				logger.info("imageType : "+imageType);
				
				//if image type is not TIFF (1) or JPEG(3), then throw Unsupported file format exception.
				if (!(imageType == 1 || imageType==3)) {
					logger.info(">> Unsupported File Format << will be thrown.");
					throw new Exception("Unsupported File Format.");
				}else if (imageType == 0) {
					logger.info(">> Unknown File Format << will be thrown.");
					throw new Exception("Unknown File Format.");
				}				
				int pagecount = 1;				
				if(imageType==1){
					pagecount = Tif6.getPageCount(ris);
					logger.info("pagecount for TIFF : "+pagecount);
				}
				logger.info("Final pagecount : "+pagecount);				
				ris.setCurrentPosition(0);				
				logger.info("After Setting current position");				
				for (int i = 1; i <= pagecount; i++) {
					logger.info(">>>>>>>>>>>>>>>> Inside For Loop with value of i : "+i);				
					outFolder =  sTemppath+snewFile;
					logger.info("outFolder for i = ["+i+"] : "+outFolder);
					file = new File(outFolder);
					double sizeInBytes = file.length();
					logger.info("sizeInBytes of file ["+outFolder+"] for counter ["+i+"] : "+sizeInBytes);					
					totalSize = totalSize + sizeInBytes; 					
					fis = new FileInputStream(file);
					bos = new ByteArrayOutputStream();
					byte[] buf = new byte[1024];
					long size = 0;
					try {
						for (int readNum; (readNum = fis.read(buf)) != -1;) {							
							bos.write(buf, 0, readNum);				
							size = size + readNum;
						}						
						byte[] encodedBytes = Base64.encodeBase64(bos.toByteArray());
						sEncodedBytes.append(new String(encodedBytes));
						logger.info("sEncodedBytes : " + sEncodedBytes.toString());
						logger.info("Exception 964 ");
					} catch (IOException ex) {
						logger.info("Exception 966 ");
						logger.info("Exception While Converting Image to Base24 Encoded String: "+ex.toString());
						throw new Exception(ex);
						
					}
				}
				
			} catch (Exception e) {
				logger.info("Exception 974 ");
				logger.info("Exception from downloadGIF() method : " + e.toString());
				throw new Exception(e);
			} finally {
				logger.info("before free memory ");
				  try {
                   Thread mainThread = Thread.currentThread();
                   mainThread.sleep(2000);
				   } catch (Exception ex) 
						{
						}
				NIPLJ.freeMemory();
				logger.info("after free memory ");
				if (ris != null) {
					try {
						ris.close();
					} catch (IOException e) {
						e.printStackTrace();
						logger.info("Exception 984 ");
						logger.info("IOException: " + e.toString());
					}
				}
				if (bos != null) {
					try {
						bos.flush();
						bos.close();
					} catch (IOException e) {
						e.printStackTrace();
						logger.info("Exception 994 ");
						logger.info("Exception: " + e.toString());
					}
				}
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
						logger.info("Exception 966 ");
						logger.info("IOException: " + e.toString());
					}
				}
				if (file != null) {
					boolean isDeleted = file.delete();
					logger.info("isDeleted>>" + isDeleted);
				}
				inputFile = new File(sTemppath + snewFile);
				logger.info("Input File Path::" + sTemppath + snewFile);
				if (inputFile != null && inputFile.exists()) {
					
					logger.info("inputFile != null && inputFile.exists()");
				} else {
					logger.info("Main file is NULL");	
				}
			}

		}
		String retVal = sEncodedBytes.toString();
		logger.info("retVal : "+retVal);	
		
		double sizeInKb = totalSize / 1024;
		
		logger.info("sizeInKb : "+sizeInKb);	
		
		if (sizeInKb > 16) {
			logger.info(">> Size Exceeded 16 KB << exception will be thrown and Encoded String will be made blank.");
			retVal="Size of Signature Image Exceeds 16 KB. Please Re-Crop the image again and associate.";
			
		}	
		
		return retVal;
	}
	public static String normalizeStringHandleComma(String str)
	  {
	    try
	    {
	      if (str == null)
	        return "";
	      if (str.trim().equalsIgnoreCase("null")) {
	        return "";
	      }
	      if ((str.trim().contains("'"))){
	      return str.replaceAll("'", "''");
	      }
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    return str.trim();
	  } 

//Added by SHivanshu ATP-501			
	public static  String executeRestAPIJSON(String url,String ClientId,String ClientSecret, String inputXML, String wiName) throws Exception{
		StringBuilder outputXML = new StringBuilder();
		HttpURLConnection conn=null;
		try {
			logger.info("URL: "+ url);
			URL urlName = new URL(url);
			conn =  (HttpURLConnection) urlName.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("x-fapi-interaction-id", wiName);
			logger.info("Final inputXML: "+ inputXML);
			String authorization = ClientId + ":" + ClientSecret;
			logger.info("authorization: "+ authorization);
			String encodedAuthorization =  DatatypeConverter.printBase64Binary(authorization.getBytes());
			logger.info("encodedAuthorization: "+ encodedAuthorization);
			conn.setRequestProperty("Authorization", "Basic "+ encodedAuthorization);


			OutputStream os = conn.getOutputStream();
			os.write(inputXML.getBytes());
			os.flush();
			logger.info("conn.getResponseCode()===> "+conn.getResponseCode());
			if(conn.getResponseCode() != HttpURLConnection.HTTP_OK && conn.getResponseCode() != HttpURLConnection.HTTP_CREATED){
				logger.info("Failed : HTTP error code :===> "+conn.getResponseCode());
				throw new RuntimeException("Failed : HTTP error code : "+conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			logger.info("Output from server ...\n");
			String out ;
			while ((out = br.readLine()) != null) {
				outputXML.append(out);
//				logger.info("RestAPI output===> "+outputXML);
			}
		} catch (MalformedURLException e) {
			logger.info("RestAPI exception1===> "+e.getMessage());
		}catch(IOException e) {
			logger.info("RestAPI exception2===> "+e.getMessage());
		}
		finally{
			if(conn!=null){
				conn.disconnect();
			}
		}
		return outputXML+"";
	} 	


}


