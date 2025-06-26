<%@page import="java.util.*,java.text.*,java.io.*,java.net.*,java.nio.*"%>
<%@page import="com.newgen.wfdesktop.xmlapi.*,com.newgen.wfdesktop.session.*"%>
<%@page import="com.newgen.omni.jts.cmgr.XMLParser"%>
<%@ page import =" javax.xml.parsers.DocumentBuilder " %>
<%@ page import  =" javax.xml.parsers.DocumentBuilderFactory" %>
<%@ page import  =" org.xml.sax.InputSource" %>
<%@ page import="org.xml.sax.SAXException"%>
<%@ page import="javax.xml.parsers.ParserConfigurationException"%>
<%@ page import="com.newgen.omni.wf.util.app.*"%> 
<%@ page import ="com.google.gson.*" %>
<%@ page import ="javax.xml.bind.DatatypeConverter" %>

<jsp:useBean id="customSession" class="com.newgen.custom.wfdesktop.session.WFCustomSession" scope="session"/>

<%
String sQuery="";
String sInput="";
String sOutput="";
String sRecord = "";
String sCall ="";
String sStatus ="";
String returnXML="";
String sReason ="";
String sErrorDescription="";

String sWIName = request.getParameter("WI_NAME");
String seq = request.getParameter("seq");

String sCabname=customSession.getEngineName();
String sSessionId = customSession.getDMSSessionId();
String sUserName = customSession.getUserName();
String sJtsIp = customSession.getJtsIp();
String iJtsPort = String.valueOf(customSession.getJtsPort());

String callXML="";
String sResVal="";
String ErrorDesc="";
String SLNO="";

String errCase="";
String brmsEligibleFlag="";
String chequeBookCR="Yes";
String performECBFlag="";
String performECBReason="";
String CID="";
String custAccRelation="";
String src="";
String typ="";
String wmsId="";
String sourcingChannel="";
//Added by Shivanshu ATP-501
String sQueryECB = "";
String sInputECB = "";
String sOutputECB = "";

String ecbExtractionDate = "";
String ecbReferenceNumber = "";
String ecbHitFlg = "";
String totalCountCheque12M = "";
String totalCountBouncedCheque12M =  "";
int ecbHit = 0;

String masterFlag = "";
String ecbRestFlag=""; 
String isChqBook="0";
int noOfCust=0;

String accNo="";
String oldREFNO="";

try
{	WriteToLog_showpage("webservice SOCKET_IP_ADDRESS: "+SOCKET_IP_ADDRESS+", SOCKET_PORT: "+SOCKET_PORT);
	if (SOCKET_IP_ADDRESS.isEmpty() && SOCKET_PORT == 0) {
		initSocket(sCabname, sSessionId, sJtsIp, iJtsPort);
	}
	

	sQuery= "SELECT SOURCING_CHANNEL  FROM EXT_AO WHERE WI_NAME = '"+sWIName+"'";
	sInput="<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+sQuery+"</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
	WriteToLog_showpage(" ***************showpage_sInput= "+sInput+" Ends_show *****************************");
	sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
	
	out.println("ref number query output"+sOutput);
	
	try
	{
		sourcingChannel=sOutput.substring(sOutput.indexOf("<SOURCING_CHANNEL>")+18,sOutput.indexOf("</SOURCING_CHANNEL>"));
		WriteToLog_showpage("Sourcing Channel is "+sourcingChannel);
	}

	catch(Exception e)
	{
		sourcingChannel="";
		WriteToLog_showpage("Sourcing Channel is "+sourcingChannel);
	}
	
	sQuery= "select COUNT(*) AS COUNT from usr_0_laps_call_out where WI_NAME='"+sWIName+"' and call_name='ECBExclusion'";
	sInput="<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+sQuery+"</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
	WriteToLog_showpage(" ***************showpage_sInput= "+sInput+" Ends_show *****************************");
	sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
	
	out.println("ref number query output"+sOutput);
	try
	{
		isChqBook=sOutput.substring(sOutput.indexOf("<COUNT>")+7,sOutput.indexOf("</COUNT>"));
		WriteToLog_showpage("isChqBook Channel is "+isChqBook);
	}

	catch(Exception e)
	{
		isChqBook="0";
		WriteToLog_showpage("isChqBook Channel is "+isChqBook);
	}

	sQuery= "select COUNT(*) AS COUNT from usr_0_laps_call_out where WI_NAME='"+sWIName+"' and call_name='CreateCASA'";
	sInput="<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+sQuery+"</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
	WriteToLog_showpage(" ***************showpage_sInput= "+sInput+" Ends_show *****************************");
	sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);

	out.println("ref number query output"+sOutput);
	try
	{
		int totalRetrieved = Integer.parseInt(sOutput.substring(sOutput.indexOf("<COUNT>")+7,sOutput.indexOf("</COUNT>")));
		WriteToLog_showpage(" *************** usr_0_laps_call_out totalRetrieved   ------------:"+totalRetrieved);
		if(totalRetrieved>0)
						{
		WriteToLog_showpage(" *************** inside totalRetrieved  ------------:"+totalRetrieved);
		accNo=sOutput.substring(sOutput.indexOf("<COUNT>")+7,sOutput.indexOf("</COUNT>"));
						}
		WriteToLog_showpage("accNo Channel is "+accNo);
	}

	catch(Exception e)
	{
		accNo="0";
		WriteToLog_showpage("catch accNo Channel is "+accNo);
	}


	sInput="<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>SELECT COUNT(1) AS COUNT_WI FROM PDBCONNECTION WHERE RANDOMNUMBER = '"+sSessionId+"'</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
	sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
	String sCount=sOutput.substring(sOutput.indexOf("<COUNT_WI>")+10,sOutput.indexOf("</COUNT_WI>"));
	
	if(sCount.equalsIgnoreCase("0"))

	{
		out.println("Session Timeout");

	}
	else
	{
		sInput="<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>select seq_webservice.nextval REFNO from dual</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
		sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
		String REFNO=sOutput.substring(sOutput.indexOf("<REFNO>")+7,sOutput.indexOf("</REFNO>"));

		if(!sWIName.equals(""))
		{
			sQuery= "SELECT CALL_NAME,STATUS,input_xml,SLNO FROM USR_0_INTEGRATION_CALLS WHERE WI_NAME = '"+sWIName+"' and call_order='"+seq+"' and status <>'Success' ORDER BY CALL_ORDER";
			WriteToLog_showpage("sQuery CALL_NAME,STATUS,input_xml,SLNO "+sQuery);
		}
				
		sInput="<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+sQuery+"</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
		
		WriteToLog_showpage("sInput CALL_NAME,STATUS,input_xml,SLNO "+sInput);
		
		if(!sInput.equals(""))
		{
			sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
			WriteToLog_showpage("sOutput CALL_NAME,STATUS,input_xml,SLNO "+sOutput);
		}
		
		if(!sOutput.equals(""))
		{
			
			
			
			String sMainCode = sOutput.substring(sOutput.indexOf("<MainCode>")+10,sOutput.indexOf("</MainCode>"));
			WriteToLog_showpage("sMainCode CALL_NAME,STATUS,input_xml,SLNO "+sMainCode);
			String runningInputXml="";
			String runningOutputXml="";
			//out.println(sOutput);
			
			if(sMainCode.equalsIgnoreCase("0"))
			{
				try{
				//int totalRetrieved = Integer.parseInt(sOutput.substring(sOutput.indexOf("<TotalRetrieved>")+16,sOutput.indexOf("</TotalRetrieved>")));
				//WriteToLog_showpage(" total sOutput = "+totalRetrieved+"**");
				sRecord = sOutput.substring(sOutput.indexOf("<Record>")+8,sOutput.indexOf("</Record>"));
				WriteToLog_showpage(" total sOutput sRecord= "+sRecord+"**");
				sCall =sRecord.substring(sRecord.indexOf("<CALL_NAME>")+11,sRecord.indexOf("</CALL_NAME>"));
				WriteToLog_showpage(" total sOutput sCall= "+sCall+"**");
				sStatus =sRecord.substring(sRecord.indexOf("<STATUS>")+8,sRecord.indexOf("</STATUS>"));
				WriteToLog_showpage(" total sOutput sStatus= "+sStatus+"**");
				callXML=sRecord.substring(sRecord.indexOf("<INPUT_XML>")+11,sRecord.indexOf("</INPUT_XML>"));
				WriteToLog_showpage(" total sOutput callXML= "+callXML+"**");
				SLNO=sRecord.substring(sRecord.indexOf("<SLNO>")+6,sRecord.indexOf("</SLNO>"));
				
				}catch(Exception e){
					sourcingChannel="";
					WriteToLog_showpage("Sourcing Channel is "+sourcingChannel);
				}
									
				WriteToLog_showpage(" **************callXML= "+callXML+" Ends_show *****************************");
				WriteToErrorLog_showpage(" **************oldREFNO= "+oldREFNO+" oldREFNO *****************************");
				
				
				
				sQuery= "SELECT REFNUMBER  FROM USR_0_INTEGRATION_OLD_REF WHERE WI_NAME = '"+sWIName+"' and CALL_NAME='"+sCall+"'";
				sInput="<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+sQuery+"</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
				WriteToLog_showpage(" ***************showpage_sInput= "+sInput+" Ends_show *****************************");
				WriteToErrorLog_showpage(" **************oldREFNO= "+sInput+" oldREFNO *****************************");
				sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
				
				out.println("ref number query output"+sOutput);

				try
				{
					oldREFNO=sOutput.substring(sOutput.indexOf("<REFNUMBER>")+11,sOutput.indexOf("</REFNUMBER>"));
				}

				catch(Exception e)
				{
					oldREFNO="";
				}
				WriteToLog_showpage(" ***************oldREFNO= "+oldREFNO+" oldREFNO *****************************");
				WriteToErrorLog_showpage(" **************oldREFNO= "+oldREFNO+" oldREFNO *****************************");
				
					//Added by Shivanshu ATP-501
     String processName = sWIName.split("-")[0];
	 String callInitial = sCall.split("_")[0];
     String callFinal = sCall.split("_")[1];
     String callName = callInitial+"_"+callFinal;
     sQueryECB = "SELECT FLAG FROM BPM_INTEGRATION_CALL_EXECUTION_MASTER WHERE PROCESS_NAME = '"+processName+"' AND CALL_NAME like '%"+callName+"%' ";
	 sInputECB="<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+sQueryECB+"</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
	 WriteToLog_showpage(" ***************showpage_sInputECB= "+sInputECB+" Ends_show *****************************");
	 sOutputECB=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputECB);
		try
	{
		masterFlag=sOutputECB.substring(sOutputECB.indexOf("<FLAG>")+6,sOutputECB.indexOf("</FLAG>"));
		WriteToLog_showpage("Master Flag : "+masterFlag);
		if(!masterFlag.equalsIgnoreCase("")){
		  ecbRestFlag="Y";
		} else {
		  ecbRestFlag="N";
		}
       WriteToLog_showpage("ECB REST FLAG is "+sourcingChannel);
	}catch(Exception e){
	
		ecbRestFlag="N";
		WriteToLog_showpage("ECB REST FLAG is "+ecbRestFlag);
	}
				
				if(oldREFNO.trim().equalsIgnoreCase(""))
				{
					String sInput1="<?xml version=\"1.0\"?><APInsert_Input><Option>APInsert</Option><TableName>USR_0_INTEGRATION_OLD_REF</TableName>" +    "<ColName>WI_NAME,CALL_NAME,CALL_ORDER,STATUS,REFNUMBER</ColName><Values>'" + sWIName + "','"+sCall+"','"+seq+"','','"+REFNO+"'</Values><EngineName>" + sCabname + "</EngineName>" +    "<SessionId>" + sSessionId + "</SessionId></APInsert_Input>";	
					WriteToErrorLog_showpage(" **************callXML= "+sInput1+" Ends_show *****************************");					
					sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput1);
					sMainCode = sOutput.substring(sOutput.indexOf("<MainCode>")+10,sOutput.indexOf("</MainCode>"));
					WriteToErrorLog_showpage(" **************callXML= "+sOutput+" Ends_show *****************************");
					if(sMainCode.equalsIgnoreCase("0"))
					{
						WriteToErrorLog_showpage(" **************callXML= "+callXML+" Ends_show *****************************");
					}
					oldREFNO = REFNO;
				}
				
				callXML=callXML.replaceAll("<REF_NO>#REF_NO#</REF_NO>","<REF_NO>"+REFNO+"</REF_NO><OLDREF_NO>"+oldREFNO+"</OLDREF_NO>");
				
				if(!sStatus.equalsIgnoreCase("running") && !callXML.equalsIgnoreCase(""))
				{
					runningInputXml="<?xml version=\"1.0\"?><APUpdate_Input><Option>APUpdate</Option><TableName>usr_0_integration_calls</TableName><ColName>status</ColName><Values>'running'</Values><WhereClause>wi_name='"+sWIName+"' and call_order='"+ seq+"'</WhereClause><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APUpdate_Input>";
					WriteToLog_showpage(" ***************input  runningInputXml  :"+runningInputXml+" *****************************");	
					
					runningOutputXml=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",runningInputXml);
										WriteToLog_showpage(" ***************input  runningInputXml  :"+runningOutputXml+" *****************************");	
										

					if(callXML.indexOf("#CUST_"+SLNO+"#")>0)
					{
						//changes to handle ## issue
						String queryHash="<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>select CID_ACCNO from usr_0_integration_call_values where wi_name='"+sWIName+"' and calltype like '%CUSTOMER_CREATION_"+SLNO+"%' and sno='"+SLNO+"'</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
						WriteToLog_showpage(" ***************input  queryHash  :"+queryHash+" *****************************");	

						String sOutputHash=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",queryHash);
						WriteToLog_showpage(" ***************input  sOutputHash  :"+sOutputHash+" *****************************");	
						String custHash =sOutputHash.substring(sOutputHash.indexOf("<CID_ACCNO>")+11,sOutputHash.indexOf("</CID_ACCNO>"));
						WriteToLog_showpage(" ***************input  custHash  :"+custHash+" *****************************");	
						callXML=callXML.replaceAll("#CUST_"+SLNO+"#",custHash);
						WriteToLog_showpage(" ***************input  callXML  :"+callXML+" *****************************");	

					}
					//Modified by Shivanshu ATP-501
					if(sCall.contains("BRMS_ECB_EXCLUSION") || (sCall.contains("ECB_VALIDATION") && !ecbRestFlag.equalsIgnoreCase("Y")) || sCall.contains("BRMS_ELIGIBILITY") || sCall.contains("BRMS_JOINTCUSTOMER") || sCall.contains("CHEQUEBOOK_HANDOFF"))
					{
						src=callXML.substring(callXML.indexOf("<SENDERID>")+10,callXML.indexOf("</SENDERID>"));
						WriteToLog_showpage("src :  "+src);
						typ=callXML.substring(callXML.indexOf("<AOType>")+8,callXML.indexOf("</AOType>"));
						WriteToLog_showpage("AO Type :  "+typ);
						wmsId=callXML.substring(callXML.indexOf("<WMSID>")+8,callXML.indexOf("</WMSID>"));
						WriteToLog_showpage("WMS ID :  "+wmsId);
						performECBFlag=callXML.substring(callXML.indexOf("<PerformValidation>")+19,callXML.indexOf("</PerformValidation>"));
						WriteToLog_showpage("performECBFlag :  "+performECBFlag);
						noOfCust = Integer.parseInt(callXML.substring(callXML.indexOf("<NoOfCust>")+10,callXML.indexOf("</NoOfCust>")));
						WriteToLog_showpage("noOfCust :  "+noOfCust);
						
						if(sCall.contains("BRMS_ECB_EXCLUSION"))
						{
							performECBReason=callXML.substring(callXML.indexOf("<PerformValidationReason>")+25,callXML.indexOf("</PerformValidationReason>"));
							WriteToLog_showpage("performECBReason :  "+performECBReason);
							custAccRelation=callXML.substring(callXML.indexOf("<AccRelation>")+13,callXML.indexOf("</AccRelation>"));
							WriteToLog_showpage("custAccRelation :  "+custAccRelation);
							CID=callXML.substring(callXML.indexOf("<CID>")+5,callXML.indexOf("</CID>"));
							WriteToLog_showpage("CID :  "+CID);
						}
					}
					
					if(sCall.contains("CHEQUEBOOK_HANDOFF"))
					{							
						/*String sQuerychq= "SELECT DISTINCT(PROD_CODE), ACC_NO FROM USR_0_PRODUCT_SELECTED AO JOIN USR_0_PRODUCT_MASTER PROD ON AO.PROD_CODE= PROD.PRODUCT_CODE WHERE PROD.CHEQUE_BOOK_FAC='Y' AND WI_NAME='"+sWIName+"' ORDER BY PROD_CODE";*/
						String sQuerychq= "SELECT PROD_CODE, ACC_NO FROM USR_0_PRODUCT_SELECTED AO JOIN USR_0_PRODUCT_MASTER PROD ON AO.PROD_CODE= PROD.PRODUCT_CODE WHERE WI_NAME='"+sWIName+"'  AND PROD.CHEQUE_BOOK_FAC='Y' and prod.currency_code=AO.currency ORDER BY PROD_CODE";
						WriteToLog_showpage(" ***************input  sQuerychq  :"+sQuerychq+" *****************************");		
												
						String sInputchq=prepareAPSelectInputXml(sQuerychq,sCabname,sSessionId);
						String sOutput_modacc=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputchq);
						WriteToLog_showpage(" ***************sOutput   ------------:"+sOutput_modacc);
						int totalRetrieved = Integer.parseInt(sOutput_modacc.substring(sOutput_modacc.indexOf("<TotalRetrieved>")+16,sOutput_modacc.indexOf("</TotalRetrieved>")));
						WriteToLog_showpage(" ***************totalRetrieved   ------------:"+totalRetrieved);
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
									
									for(int j = 1; j <= noOfCust; j++)
									{
										String sInput2=prepareAPUpdateInputXml("CHQBOOKVALIDATIONDATA","ACCOUNTNO","'"+ACC_NO+"'","WI_NAME='"+sWIName+"' and CUSTSNO='"+j+"' and accountno is null and rownum=1",sCabname,sSessionId);
										WriteToLog_showpage(" ***************update CHQBOOKVALIDATIONDATA "+sInput2+" *****************************");
										String sOutput2=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput2);						
										WriteToLog_showpage(" ***************update sOutput for CHQBOOKVALIDATIONDATA "+sOutput2+" *****************************");
											
											
										sInput2=prepareAPUpdateInputXml("USR_0_ECB_EXCLUSION","ACCOUNTNO","'"+ACC_NO+"'","WI_NAME='"+sWIName+"' and CUSTSNO='"+j+"' and accountno is null and rownum=1",sCabname,sSessionId);
										WriteToLog_showpage(" ***************update USR_0_ECB_EXCLUSION "+sInput2+" *****************************");
										sOutput2=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput2);						
										WriteToLog_showpage(" ***************update sOutput for USR_0_ECB_EXCLUSION "+sOutput2+" *****************************");
									}
								}
							}
						}
						
						if(chequeBookCR.equalsIgnoreCase("No"))
						{
							sOutput="<Option>CHEQUEBOOK_HANDOFF</Option><Output><returnCode>0</returnCode><Status>Skipped</Status><errorDescription>CHEQUEBOOKCR_EXCLUDE</errorDescription>";
						}
						else if(performECBFlag.equalsIgnoreCase("False"))
						{
							sOutput="<Option>CHEQUEBOOK_HANDOFF</Option><Output><returnCode>0</returnCode><Status>Skipped</Status><errorDescription>PERFORM_VALIDATION_EXCLUDE</errorDescription>";
						}
						else if(!isChqBook.equalsIgnoreCase("0") && sourcingChannel.equalsIgnoreCase("LAPS")){
							WriteToLog_showpage("inside my work :  ");

							sOutput="<Option>CHEQUEBOOK_HANDOFF</Option><Output><returnCode>0</returnCode><Status>Skipped</Status><errorDescription>CHEQUEBOOKCALL_SKIPPED</errorDescription>";
							
						}
						
						else
						{
							String currws=callXML.substring(callXML.indexOf("<Workstep>")+10,callXML.indexOf("</Workstep>"));
							WriteToLog_showpage("currws :  "+currws);

							String queryChk="SELECT PROCESSFLAG FROM CHEQUEBOOKHANDOFFDATA WHERE WORKITEMNO='"+sWIName+ "'";
							WriteToLog_showpage("queryChk-----" + queryChk);
							String inputChk = prepareAPSelectInputXml(queryChk,sCabname,sSessionId);
							String outputChk=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",inputChk);
							WriteToLog_showpage("outputChk :  "+outputChk);							
							totalRetrieved = Integer.parseInt(outputChk.substring(outputChk.indexOf("<TotalRetrieved>")+16,outputChk.indexOf("</TotalRetrieved>")));
							WriteToLog_showpage("totalRetrieved :  "+totalRetrieved);
							String processFlag="";
							
							if(totalRetrieved>0) {
								processFlag=outputChk.substring(outputChk.indexOf("<PROCESSFLAG>")+13,outputChk.indexOf("</PROCESSFLAG>"));
							}
							
							if(totalRetrieved>0 && currws.equalsIgnoreCase("CPD Checker") && typ.equalsIgnoreCase("AO Branch") && processFlag!=null && !processFlag.equals("") && !processFlag.equalsIgnoreCase("U"))
							{								
								sOutput="<Option>CHEQUEBOOK_HANDOFF</Option><Output><returnCode>0</returnCode><Status>Skipped</Status><errorDescription>CHEQUEBOOK_HANDOFF_DONE</errorDescription>";
							}
							else 
							{
								String query= "SELECT CHEQUEBOOK_ELIGIBILITY AS ELIGIBLECHQLEAVES FROM EXT_AO WHERE WI_NAME = '"+sWIName+"'";			
								WriteToLog_showpage(" ***************input  query  :"+query+" *****************************");	
								
								if(!query.equals(""))	
								{
									String NO_OF_LEAVES="";
									
									WriteToLog_showpage(" ***************inside if : *****************************");	
									String input = prepareAPSelectInputXml(query,sCabname,sSessionId);
									String output=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",input);
									WriteToLog_showpage(" ***************output   ------------:"+output);
									
									if(!output.equals(""))
									{
										sMainCode = output.substring(output.indexOf("<MainCode>")+10,output.indexOf("</MainCode>"));
										
										if(sMainCode.equalsIgnoreCase("0"))
										{
											String eligibility=output.substring(output.indexOf("<ELIGIBLECHQLEAVES>")+19,output.indexOf("</ELIGIBLECHQLEAVES>"));
											
											if(eligibility!=null && !eligibility.equals(""))
											{
												query= "SELECT ChqLeaves FROM USR_0_CHQLEAVES WHERE Eligibility='"+eligibility+"'";			
												WriteToLog_showpage(" ***************input  query  :"+query+" *****************************");	

													if(!query.equals(""))	
													{
														WriteToLog_showpage(" ***************inside if : ***************************");	
														input = prepareAPSelectInputXml(query,sCabname,sSessionId);
														output=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",input);
														WriteToLog_showpage(" ***************output   ------------:"+output);
														
														if(!output.equals(""))
														{
															sMainCode = output.substring(output.indexOf("<MainCode>")+10,output.indexOf("</MainCode>"));
															if(sMainCode.equalsIgnoreCase("0"))
															{
																NO_OF_LEAVES=output.substring(output.indexOf("<CHQLEAVES>")+11,output.indexOf("</CHQLEAVES>"));
																WriteToLog_showpage(" ***************** sOutput NO_OF_LEAVES :"+NO_OF_LEAVES+" *************************");
													
																if(NO_OF_LEAVES.equalsIgnoreCase("BAU"))
																{
																	sQuerychq= "SELECT VALUE FROM USR_0_DEFAULTVALUE_FCR WHERE NAME ='NoOfLeavesRequested'";			
																	WriteToLog_showpage(" ***************input  sQuerychq  :"+sQuerychq+" *****************************");		
																	if(!sQuerychq.equals(""))	
																	{
																		WriteToLog_showpage(" ***************inside if : *****************************");	
																		sInputchq=prepareAPSelectInputXml(sQuerychq,sCabname,sSessionId);
																		sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputchq);
																		WriteToLog_showpage(" ***************sOutput   ------------:"+sOutput);
														
																		if(!output.equals(""))
																		{
																			sMainCode = output.substring(output.indexOf("<MainCode>")+10,output.indexOf("</MainCode>"));
																			if(sMainCode.equalsIgnoreCase("0"))
																			{
																				NO_OF_LEAVES=sOutput.substring(sOutput.indexOf("<VALUE>")+7,sOutput.indexOf("</VALUE>"));
																			}
																		}
																	}
																}
															}
														}
														
														
													}
													WriteToLog_showpage(" *****************NO_OF_LEAVES :"+NO_OF_LEAVES+" *************************");
											
													sQuerychq= "SELECT DISTINCT(PROD_CODE), ACC_NO, TO_CHAR(TO_DATE(ACC_OPEN_DT,'DD/MM/YY HH24:MI:SS'),'DD/MM/YYYY') AS ACC_OPEN_DT, PROD_DESC FROM USR_0_PRODUCT_SELECTED AO JOIN USR_0_PRODUCT_MASTER PROD ON AO.PROD_CODE= PROD.PRODUCT_CODE WHERE PROD.CHEQUE_BOOK_FAC='Y' AND  WI_NAME='"+sWIName+"' ORDER BY PROD_CODE";
													WriteToLog_showpage(" ***************input  sQuerychq  :"+sQuerychq+" *****************************");
													
													sInputchq=prepareAPSelectInputXml(sQuerychq,sCabname,sSessionId);
													sOutput_modacc=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputchq);
													WriteToLog_showpage(" ***************sOutput   ------------:"+sOutput_modacc);
													totalRetrieved = Integer.parseInt(sOutput_modacc.substring(sOutput_modacc.indexOf("<TotalRetrieved>")+16,sOutput_modacc.indexOf("</TotalRetrieved>")));
													WriteToLog_showpage(" ***************totalRetrieved   ------------:"+totalRetrieved);
													
													if(totalRetrieved>0)
													{
														XMLParser xp1 = new XMLParser(sOutput_modacc);
														int start = xp1.getStartIndex("Records", 0, 0);
														int deadEnd = xp1.getEndIndex("Records", start, 0);
														int noOfFields = xp1.getNoOfFields("Record", start,deadEnd);
														int end = 0;
														if(noOfFields > 0){
														for(int i = 1; i <= noOfFields; ++i)
														{
																start = xp1.getStartIndex("Record", end, 0);
																end = xp1.getEndIndex("Record", start, 0);
																
																String ACC_NO=xp1.getValueOf("ACC_NO", start, end);
																String ACC_OPEN_DATE=xp1.getValueOf("ACC_OPEN_DT", start, end);
																String PROD_DESC=xp1.getValueOf("PROD_DESC", start, end);
																
																String ProcParams_Chq = "'"+sWIName+"','"+wmsId+"','"+typ+"','"+ACC_NO+"','"+ACC_OPEN_DATE+"','"+NO_OF_LEAVES+"','U'";
																	
																WriteToLog_showpage(" *************ProcParams_Chq : *****************************:"+ProcParams_Chq);
												
																String sInput_chq="<?xml version=\"1.0\"?><APProcedure_Input><Option>APProcedure</Option><ProcName>CHEQUEBOOKHANDOFF</ProcName><Params>"+ProcParams_Chq+"</Params><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APUpdate_Input>";
																WriteToLog_showpage(" ***************ap procedure input CHEQUEBOOKHANDOFF"+sInput_chq);
																String sOutput_chq=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput_chq);	
																WriteToLog_showpage(" ***************ap procedure sOutput CHEQUEBOOKHANDOFF"+sOutput_chq);
																String mainCode=sOutput_chq.substring(sOutput_chq.indexOf("<MainCode>")+10,sOutput_chq.indexOf("</MainCode>"));
																WriteToLog_showpage(" ***************maincode"+mainCode);

																if (("0").equalsIgnoreCase(mainCode))
																{
																	sOutput="<Option>ChequeBookHandoff</Option><Output><returnCode>0</returnCode><Status>Success</Status><errorDescription>Success</errorDescription>"+sOutput_chq;
																	WriteToLog_showpage(" ***************ap procedure sOutput if :"+sOutput);
																}
																else{
																sOutput="<Option>ChequeBookHandoff</Option><Output><returnCode>-1</returnCode><Status>Error while Executing</Status><errorDescription>Account : "+PROD_DESC+"</errorDescription>"+sOutput_chq;
																WriteToLog_showpage(" ***************ap procedure sOutput else :"+sOutput);
																	}
																}
															}
													}
													
													else{
														sOutput="<Option>ChequeBookHandoff</Option><Output><returnCode>0</returnCode><Status>Skipped. Account without Chequebook Facility</Status><errorDescription>Skipped</errorDescription>";
													}
											}
											else
											{
												sOutput="<Option>ChequeBookHandoff</Option><Output><returnCode>0</returnCode><Status>Skipped</Status><errorDescription>BRMS_RESULT_EXCLUDE</errorDescription>";
											}
										}
										else
										{
											sOutput="<Option>ChequeBookHandoff</Option><Output><returnCode>-1</returnCode><Status>Skipped</Status><errorDescription>Skipped</errorDescription>";
										}
									}
								}
							}
						}
					}
					else if(sCall.contains("ECB_VALIDATION"))
					{
						if(chequeBookCR.equalsIgnoreCase("No"))
						{
							sOutput="<Option>ECB_VALIDATION</Option><Output><returnCode>0</returnCode><Status>Skipped</Status><errorDescription>CHEQUEBOOKCR_EXCLUDE</errorDescription>";
							
							ecbRestFlag = "N";
						}
						else if(performECBFlag.equalsIgnoreCase("False"))
						{
							sOutput="<Option>ECB_VALIDATION</Option><Output><returnCode>0</returnCode><Status>Skipped</Status><errorDescription>PERFORM_VALIDATION_EXCLUDE</errorDescription>";
							
							ecbRestFlag = "N";
						}
						else if(!isChqBook.equalsIgnoreCase("0") && sourcingChannel.equalsIgnoreCase("LAPS"))
						{							WriteToLog_showpage("inside my work :  ");

							sOutput="<Option>ECB_VALIDATION</Option><Output><returnCode>0</returnCode><Status>Skipped</Status><errorDescription>CHEQUEBOOKCALL_SKIPPED</errorDescription>";
							
							ecbRestFlag = "N";
						}
						else {
						String query= "";
						if(noOfCust>1)
						{
							query= "SELECT ECB_EXCLUSION from CHQBOOKVALIDATIONDATA where WI_NAME='"+sWIName+"' and CUSTSNO='"+SLNO+"' and ECB_EXCLUSION='Include'";
						}
						else
						{
							query= "SELECT ECB_EXCLUSION from CHQBOOKVALIDATIONDATA where WI_NAME='"+sWIName+"' and ECB_EXCLUSION='Include'";
						}
						WriteToLog_showpage(" ***************input  query  :"+query+" *****************************");	
						
						if(!query.equals(""))	
						{
							WriteToLog_showpage(" ***************inside if : *****************************");	
							String input = prepareAPSelectInputXml(query,sCabname,sSessionId);
							String output=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",input);
							WriteToLog_showpage(" ***************output   ------------:"+output);
							
							if(!output.equals(""))
							{
								int totalRetrieved = Integer.parseInt(output.substring(output.indexOf("<TotalRetrieved>")+16,output.indexOf("</TotalRetrieved>")));
								if(totalRetrieved > 0)
								{
								//Added by Shivanshu ATP-501
								if(!ecbRestFlag.equalsIgnoreCase("Y")){ 
									out.println(callXML);
									sOutput = connectToSocket(callXML);
									callXML=callXML.replaceAll("\r\n","");
						//Added by Shivanshu ATP-501
					 }else {
					     WriteToLog_showpage(" ***************input  callXML  :"+callXML+" *****************************");
						
							
							//String url = "http://10.101.166.245:9080/ecbwebapi/api/v1/EcbChequeScore/GetEcbChequeScore";	
							String refInput="<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>select seq_webservice.nextval REFNO from dual</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
							String refOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",refInput);
							String refECBNO = refOutput.substring(refOutput.indexOf("<REFNO>")+7,refOutput.indexOf("</REFNO>"));
							WriteToLog_showpage(" refECBNO : "+refECBNO);
							
							String url = "";	
							String clientId = "";	
							String clientSecret = "";
							
							try {
								String queryConfig = "SELECT KEY, VALUE FROM BPM_CONFIG WHERE PROCESS_NAME ='AO' AND KEY IN ('AECB_URL', 'AECB_CLIENT_ID', 'AECB_CLIENT_SECRET')";
								WriteToLog_showpage("Query: "+ queryConfig);
								String inputXMLConfig=prepareAPSelectInputXml(queryConfig, sCabname, sSessionId);
								String outputXMLConfig = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",inputXMLConfig);
								
								String mainCodeConfig = outputXMLConfig.substring(outputXMLConfig.indexOf("<MainCode>")+10,outputXMLConfig.indexOf("</MainCode>"));
			
								if(mainCodeConfig.equalsIgnoreCase("0")) {	
									int totalRetrievedConfig = Integer.parseInt(outputXMLConfig.substring(outputXMLConfig.indexOf("<TotalRetrieved>")+16,outputXMLConfig.indexOf("</TotalRetrieved>")));
					
									if(totalRetrievedConfig>0) {
										XMLParser xpconfig = new XMLParser(outputXMLConfig);
										int start = xpconfig.getStartIndex("Records", 0, 0);
										int deadEnd = xpconfig.getEndIndex("Records", start, 0);
										int noOfFields = xpconfig.getNoOfFields("Record", start, deadEnd);
										int end = 0;
										if(noOfFields > 0){
											for(int i = 1; i <= noOfFields; ++i) {
												start = xpconfig.getStartIndex("Record", end, 0);
												end = xpconfig.getEndIndex("Record", start, 0);
														
												String key = xpconfig.getValueOf("KEY", start, end);
												String value = xpconfig.getValueOf("VALUE", start, end);
									
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
								WriteToLog_showpage("Exception: "+ e.getMessage());
					
							}
							
					     	sOutput = executeRestAPIJSON(url,clientId,clientSecret,callXML,sWIName+"_"+refECBNO);
							//callXML=callXML.replaceAll("\r\n","");
							
		try{				
							JsonElement jsonElement = JsonParser.parseString(sOutput);
          JsonObject jsonObject = jsonElement.getAsJsonObject();

          // Accessing values directly from JsonObject
          JsonElement dataElement = jsonObject.get("Data");
          JsonObject data = dataElement != null && !dataElement.isJsonNull() && dataElement.isJsonObject() ? dataElement.getAsJsonObject() : null;

          if (data != null) {
              JsonElement requestDataElement = data.get("RequestData");
			   WriteToLog_showpage("ecb requestData: " + data.get("RequestData"));
              JsonObject requestData = requestDataElement != null && !requestDataElement.isJsonNull() && requestDataElement.isJsonObject() ? requestDataElement.getAsJsonObject() : null;

              if (requestData != null) {
                  String sysRefNumber = requestData.has("SysRefNumber") ? requestData.get("SysRefNumber").getAsString() : "DefaultSysRefNumber";
                  String errorCode = requestData.has("ErrorCode") ? requestData.get("ErrorCode").getAsString() : "DefaultErrorCode";
                  ErrorDesc = requestData.has("ErrorDesc") ? requestData.get("ErrorDesc").getAsString() : "DefaultErrorDesc";

                 WriteToLog_showpage("sysRefNumber: " + sysRefNumber);
                 WriteToLog_showpage("errorCode: " + errorCode);
                 WriteToLog_showpage("errorDesc: " + ErrorDesc);
              } else {
                 WriteToLog_showpage("requestData is null or not an object");
              }

              JsonElement reportSummaryElement = data.get("ReportSummary");
              JsonObject reportSummary = reportSummaryElement != null && !reportSummaryElement.isJsonNull() && reportSummaryElement.isJsonObject() ? reportSummaryElement.getAsJsonObject() : null;

              if (reportSummary != null) {
                   ecbExtractionDate = reportSummary.has("EcbExtractionDate") ? reportSummary.get("EcbExtractionDate").getAsString() : "DefaultDate";
                   ecbReferenceNumber = reportSummary.has("EcbReferenceNumber") ? reportSummary.get("EcbReferenceNumber").getAsString() : "DefaultEcbRefNumber";
					ecbHitFlg = reportSummary.has("EcbHitFlg") ? reportSummary.get("EcbHitFlg").getAsString() : "DefaultHitFlag";
					
                 WriteToLog_showpage("ecbExtractionDate: " + ecbExtractionDate);
                 WriteToLog_showpage("ecbReferenceNumber: " + ecbReferenceNumber);
				  WriteToLog_showpage("ecbHitFlg: " + ecbHitFlg);
              } else {
                 WriteToLog_showpage("reportSummary is null or not an object");
              }

              JsonElement customerChequeInsightsElement = data.get("CustomerChequeInsights");
              JsonObject customerChequeInsights = customerChequeInsightsElement != null && !customerChequeInsightsElement.isJsonNull() && customerChequeInsightsElement.isJsonObject() ? customerChequeInsightsElement.getAsJsonObject() : null;

              if (customerChequeInsights != null) {
                   totalCountCheque12M = customerChequeInsights.has("TotalCountCheque12M") ? customerChequeInsights.get("TotalCountCheque12M").getAsString() : "DefaultTotalCountCheque12M";
                   totalCountBouncedCheque12M = customerChequeInsights.has("TotalCountBouncedCheque12M") ? customerChequeInsights.get("TotalCountBouncedCheque12M").getAsString() : "DefaultTotalCountBouncedCheque12M";
                   //totalCountBouncedCheque12M = Integer.parseInt(totalCountBouncedCheque12M);
				   
                 WriteToLog_showpage("totalCountCheque12M: " + totalCountCheque12M);
                 WriteToLog_showpage("totalCountBouncedCheque12M: " + totalCountBouncedCheque12M);
              } else {
                 WriteToLog_showpage("customerChequeInsights is null or not an object");
              }

          } else {
             WriteToLog_showpage("data is null or not an object");
          }
		} catch (JsonSyntaxException e) {
          e.printStackTrace();
      }				
                                                      if(ecbHitFlg.equalsIgnoreCase("Y")){
							ecbHit = 1;
						      }	
							
					  }
					}
								else
								{
									WriteToLog_showpage(" ***************inside skipped condition *****************************");
									sOutput="<Option>ECB_VALIDATION</Option><Output><returnCode>0</returnCode><Status>Skipped</Status><errorDescription>BRMS_RESULT_EXCLUDE</errorDescription>";
									
									ecbRestFlag = "N";
								}
							}
						}
						}

					}
					else if(sCall.contains("BRMS_ELIGIBILITY"))
					{
						if(chequeBookCR.equalsIgnoreCase("No"))
						{
							sOutput="<Option>BRMS_ELIGIBILITY</Option><Output><returnCode>0</returnCode><Status>Skipped</Status><errorDescription>CHEQUEBOOKCR_EXCLUDE</errorDescription>";
						}
						else if(performECBFlag.equalsIgnoreCase("False"))
						{
							sOutput="<Option>BRMS_ELIGIBILITY</Option><Output><returnCode>0</returnCode><Status>Skipped</Status><errorDescription>PERFORM_VALIDATION_EXCLUDE</errorDescription>";
						}
						else if(!isChqBook.equalsIgnoreCase("0") && sourcingChannel.equalsIgnoreCase("LAPS")){
															WriteToLog_showpage("inside my work :  ");
						sOutput="<Option>BRMS_ELIGIBILITY</Option><Output><returnCode>0</returnCode><Status>Skipped</Status><errorDescription>CHEQUEBOOKCALL_SKIPPED</errorDescription>";
						}

						else {
						String query= "";
						if(noOfCust>1)
						{
							query= "SELECT ECB_EXCLUSION from CHQBOOKVALIDATIONDATA where WI_NAME='"+sWIName+"' and CUSTSNO='"+SLNO+"' and ECB_EXCLUSION='Include'";
						}
						else
						{
							query= "SELECT ECB_EXCLUSION from CHQBOOKVALIDATIONDATA where WI_NAME='"+sWIName+"' and ECB_EXCLUSION='Include'";
						}			
						WriteToLog_showpage(" ***************input  query  :"+query+" *****************************");	
						
						if(!query.equals(""))	
						{
							WriteToLog_showpage(" ***************inside if : *****************************");	
							String input = prepareAPSelectInputXml(query,sCabname,sSessionId);
							String output=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",input);
							WriteToLog_showpage(" ***************output   ------------:"+output);
							
							if(!output.equals(""))
							{
								int totalRetrieved = Integer.parseInt(output.substring(output.indexOf("<TotalRetrieved>")+16,output.indexOf("</TotalRetrieved>")));
								if(totalRetrieved > 0)
								{
									WriteToLog_showpage("************* inside BRMSEligibility **************************");
									String sQuery5= "SELECT NOCHQBOUNCE, HITNOHIT FROM CHQBOOKVALIDATIONDATA WHERE WI_NAME = '"+sWIName+"' and CUSTSNO='"+SLNO+"' and rownum=1 order by createddate desc";
									String sInput2 = prepareAPSelectInputXml(sQuery5,sCabname,sSessionId);			
									WriteToLog_showpage("*************input Fetch NOCHQBOUNCE,HITNOHIT from CHQBOOKVALIDATIONDATA table "+sInput2+" **************************");		
									if(!sInput2.equals(""))
									{
										sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput2);
							
										WriteToLog_showpage(" **************fetch noOfChequeBounceInLast12Months and HitOrNoHit= "+sOutput+" **************************");
							
										if(!sOutput.equals(""))
										{			
											String mainCode = sOutput.substring(sOutput.indexOf("<MainCode>")+10,sOutput.indexOf("</MainCode>"));
											if(mainCode.equalsIgnoreCase("0"))	{
												callXML=callXML.replaceAll("#Last12Months#",sOutput.substring(sOutput.indexOf("<NOCHQBOUNCE>")+13,sOutput.indexOf("</NOCHQBOUNCE>")));
												callXML=callXML.replaceAll("#HitOrNoHit#",sOutput.substring(sOutput.indexOf("<HITNOHIT>")+10,sOutput.indexOf("</HITNOHIT>")));
											}
										}
									}
						
									sOutput = connectToSocket(callXML);
									
									callXML=callXML.replaceAll("\r\n","");
								}
								else
								{
									WriteToLog_showpage(" ***************inside skipped condition *****************************");
									sOutput="<Option>BRMS_ELIGIBILITY</Option><Output><returnCode>0</returnCode><Status>Skipped</Status><errorDescription>BRMS_RESULT_EXCLUDE</errorDescription>";
								}
							}
						}
						}
					}
					else if(sCall.contains("BRMS_JOINTCUSTOMER"))
					{
					if(chequeBookCR.equalsIgnoreCase("No"))
						{
							sOutput="<Option>BRMS_JOINTCUSTOMER</Option><Output><returnCode>0</returnCode><Status>Skipped</Status><errorDescription>CHEQUEBOOKCR_EXCLUDE</errorDescription>";
						}
						else if(performECBFlag.equalsIgnoreCase("False"))
						{
							sOutput="<Option>BRMS_JOINTCUSTOMER</Option><Output><returnCode>0</returnCode><Status>Skipped</Status><errorDescription>PERFORM_VALIDATION_EXCLUDE</errorDescription>";
						}
						else if(!isChqBook.equalsIgnoreCase("0") && sourcingChannel.equalsIgnoreCase("LAPS"))
						{							WriteToLog_showpage("inside my work :  ");

							sOutput="<Option>BRMS_JOINTCUSTOMER</Option><Output><returnCode>0</returnCode><Status>Skipped</Status><errorDescription>CHEQUEBOOKCALL_SKIPPED</errorDescription>";
						}
						else {
						WriteToLog_showpage(" **************inside BRMS_JOINTCUSTOMER call **************************");
												
						String sQuery5= "SELECT distinct(CUSTSNO) AS CUSTSNO,ELIGIBLECHQLEAVES FROM CHQBOOKVALIDATIONDATA WHERE WI_NAME = '"+sWIName+"' and ECB_EXCLUSION='Include' order by CUSTSNO asc";
						
						String sInput2 = prepareAPSelectInputXml(sQuery5,sCabname,sSessionId);			
						WriteToLog_showpage("*************input Fetch CUSTSNO,ELIGIBLECHQLEAVES from CHQBOOKVALIDATIONDATA table "+sInput2+" **************************");		
						if(!sInput2.equals(""))
						{
							sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput2);
							
							WriteToLog_showpage(" **************fetch CUSTSNO and ELIGIBLECHQLEAVES= "+sOutput+" **************************");
							
							if(!sOutput.equals(""))
							{
								int totalRetrieved = Integer.parseInt(sOutput.substring(sOutput.indexOf("<TotalRetrieved>")+16,sOutput.indexOf("</TotalRetrieved>")));
								if(totalRetrieved>0){

									StringBuffer inputxml = new StringBuffer();
									inputxml.append("<Ecb_JointCustomer>");
									
									XMLParser xp1 = new XMLParser(sOutput);
									int start = xp1.getStartIndex("Records", 0, 0);
									int deadEnd = xp1.getEndIndex("Records", start, 0);
									int noOfFields = xp1.getNoOfFields("Record", start,deadEnd);
									int end = 0;
									if(noOfFields > 0){
										for(int i = 1; i <= noOfFields; ++i){
											start = xp1.getStartIndex("Record", end, 0);
											end = xp1.getEndIndex("Record", start, 0);

											String custno = xp1.getValueOf("CUSTSNO", start, end);
											String elgblty = xp1.getValueOf("ELIGIBLECHQLEAVES", start, end);
											inputxml.append("\n").append("<customerEligibility" +custno+ ">"+elgblty+"</customerEligibility" +custno+ ">");
											WriteToLog_showpage("INSIDE FOR LOOP--1 custno :: "+custno+", eligibility :: "+elgblty);
										}
									}
																		
									inputxml.append("\n").append("</Ecb_JointCustomer>");
									
									callXML=callXML.replaceAll("#Ecb_JointCustomer#",inputxml.toString());
									
									
									sOutput = connectToSocket(callXML);
									
									callXML=callXML.replaceAll("\r\n","");
								}
								else
								{
									WriteToLog_showpage(" ***************inside skipped condition *****************************");
									sOutput="<Option>BRMS_JOINTCUSTOMER</Option><Output><returnCode>0</returnCode><Status>Skipped</Status><errorDescription>BRMS_RESULT_EXCLUDE</errorDescription>";
								}
							}
						}
						}
					}
					else if(sCall.contains("BRMS_ECB_EXCLUSION"))
					{
						WriteToLog_showpage("inside brms eee call :  ");

						if(chequeBookCR.equalsIgnoreCase("No"))
						{
							WriteToLog_showpage("inside my work noooo:  ");

							sOutput="<Option>BRMS_ECB_EXCLUSION</Option><Output><returnCode>0</returnCode><Status>Skipped</Status><errorDescription>CHEQUEBOOKCR_EXCLUDE</errorDescription>";
						}
						else if(!isChqBook.equalsIgnoreCase("0") && sourcingChannel.equalsIgnoreCase("LAPS")){
														WriteToLog_showpage("inside my work :  ");

							sOutput="<Option>BRMS_ECB_EXCLUSION</Option><Output><returnCode>0</returnCode><Status>Skipped</Status><errorDescription>CHEQUEBOOKCALL_SKIPPED</errorDescription>";
						
						}
						else if(performECBFlag.equalsIgnoreCase("False"))
						{
							WriteToLog_showpage("inside my work :  false");

							String userName=callXML.substring(callXML.indexOf("<UserName>")+10,callXML.indexOf("</UserName>"));
							WriteToLog_showpage("userName :  "+userName);
						
							String sInput1=prepareAPInsertInputXml("USR_0_ECB_EXCLUSION","WI_NAME,WMSID,SOURCE,CUSTSNO,CID,EXCLREASON,CREATEDDATE,BPMUSER","'" + sWIName + "','"+wmsId+"','"+typ+"','"+SLNO+"','"+CID+"','"+performECBReason+"',sysdate,'"+userName+"'",sCabname,sSessionId);
							WriteToLog_showpage(" ***************insert in USR_0_ECB_EXCLUSION "+sInput1+" *****************************");
							String sOutput1=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput1);						
							WriteToLog_showpage(" ***************insert sOutput for data in USR_0_ECB_EXCLUSION "+sOutput1+" ********************");
								
							sOutput="<Option>BRMS_ECB_EXCLUSION</Option><Output><returnCode>0</returnCode><Status>Skipped</Status><errorDescription>PERFORM_VALIDATION_EXCLUDE</errorDescription>";
						}
						else {
							WriteToLog_showpage("inside my work : no cond satis ");

							
							sOutput = connectToSocket(callXML);
							
							WriteToLog_showpage("callXML: "+ callXML);
							WriteToLog_showpage("sJtsIp: "+ sJtsIp);
							WriteToLog_showpage("iJtsPort: "+ iJtsPort);
							
							
							
							WriteToLog_showpage("sOutput: "+ sOutput);
							callXML=callXML.replaceAll("\r\n","");
						}
					}
					
					else if(sCall.contains("ACCOUNT_CREATION"))
					{
						 if(!accNo.equalsIgnoreCase("0") && sourcingChannel.equalsIgnoreCase("LAPS")){
						
							WriteToLog_showpage("inside ACCOUNT_CREATION ans :  ");

							sOutput="<Option>ACCOUNT_CREATION</Option><Output><returnCode>0</returnCode><Status>Skipped</Status><errorDescription>CHEQUEBOOKCALL_SKIPPED</errorDescription>";
						}
						else {
							
							sOutput = connectToSocket(callXML);
							
							callXML=callXML.replaceAll("\r\n","");
						}
					} else if (sCall.contains("MAINTAIN_FAMILY") && callXML.contains("#FAMILYDETAILS#")){
						//sahil
						String sQueryFamily  = "SELECT CID, RELATIONSHIP FROM USR_0_FAMILY_MEMBER_DETAILS WHERE WI_NAME = '"+sWIName+"' AND (CUST_STATUS != 'D'  OR CUST_STATUS is NULL) AND upper(RELATIONSHIP) != 'HEAD OF FAMILY'"; 
						// AND upper(RELATIONSHIP) != 'HEAD OF FAMILY'
						String sInputFamily = prepareAPSelectInputXml(sQueryFamily,sCabname,sSessionId);			
						WriteToLog_showpage("sInputFamily :"+sInputFamily+" **************************");	
						if(!sInputFamily.equals(""))
						{
							String sOutputFamily=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputFamily);
							WriteToLog_showpage("sOutputFamily :"+sOutputFamily+" **************************");
							if(!sOutputFamily.equals(""))
							{
								int totalRetrieved = Integer.parseInt(sOutputFamily.substring(sOutputFamily.indexOf("<TotalRetrieved>")+16,sOutputFamily.indexOf("</TotalRetrieved>")));
								if(totalRetrieved>0){
									StringBuffer replaceTag = new StringBuffer();
									XMLParser xp1 = new XMLParser(sOutputFamily);
									int start = xp1.getStartIndex("Records", 0, 0);
									int deadEnd = xp1.getEndIndex("Records", start, 0);
									int noOfFields = xp1.getNoOfFields("Record", start,deadEnd);
									int end = 0;
									if(noOfFields > 0){
										for(int i = 1; i <= noOfFields; ++i){
											start = xp1.getStartIndex("Record", end, 0);
											end = xp1.getEndIndex("Record", start, 0);

											String FAMILY_CID = xp1.getValueOf("CID", start, end);
											String FAMILY_RELATION = xp1.getValueOf("RELATIONSHIP", start, end);
											replaceTag.append("\n").append("<FamilyMember><customerId>"+FAMILY_CID+"</customerId><relationship>"+FAMILY_RELATION+"</relationship></FamilyMember>");
										}
									}
									WriteToLog_showpage("replaceTag :"+replaceTag.toString()+" **************************");
									callXML = callXML.replaceAll("#FAMILYDETAILS#",replaceTag.toString());
									if(sStatus.equalsIgnoreCase("Error")) {
										sInput="<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>select seq_webservice.nextval REFNO from dual</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
										sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
										XMLParser xp2 = new XMLParser(callXML);
										String reftemp1 = xp2.getValueOf("REF_NO");
										WriteToLog_showpage("MAINTAIN_FAMILY reftemp: "+reftemp1);
										REFNO = sOutput.substring(sOutput.indexOf("<REFNO>")+7,sOutput.indexOf("</REFNO>"));
										WriteToLog_showpage("MAINTAIN_FAMILY callXML 1: "+callXML);
										callXML=callXML.replaceAll("<REF_NO>"+reftemp1+"</REF_NO>","<REF_NO>"+REFNO+"</REF_NO>");
										WriteToLog_showpage("MAINTAIN_FAMILY callXML 2: "+callXML);
									}
									sOutput = connectToSocket(callXML);
									callXML=callXML.replaceAll("\r\n","");
									
								}
							}
						}
					} else{						
						sOutput = connectToSocket(callXML);
						callXML=callXML.replaceAll("\r\n","");
						
						}
					WriteToLog_showpage("callXML---# #"+callXML);
					WriteToLog_showpage("Output---# #"+sOutput);
					//out.println(callXML);
					
					//Added by SHivanshu ATP-501
					if(!ecbRestFlag.equalsIgnoreCase("Y") ){
					sStatus=sOutput.substring(sOutput.indexOf("<returnCode>")+12,sOutput.indexOf("</returnCode>"));
					ErrorDesc=sOutput.substring(sOutput.indexOf("<errorDescription>")+18,sOutput.indexOf("</errorDescription>"));
					} else {
						WriteToLog_showpage("ErrorDesc ECB ---# #"+ErrorDesc);
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
					//fatca issue handling 5/1/2022
					if(sOutput.contains("Record already exists") && sCall.indexOf("FATCA_UPDATE")>=0)
					{
						sErrorDescription="";
						String refsInput="<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>select seq_webservice.nextval REFNO from dual</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
						sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",refsInput);
						String newREFNO=sOutput.substring(sOutput.indexOf("<REFNO>")+7,sOutput.indexOf("</REFNO>"));
						callXML=callXML.replace(">ADD<",">UPD<");
						callXML=callXML.replace(REFNO,newREFNO);
						sOutput = connectToSocket(callXML);
						callXML=callXML.replaceAll("\r\n","");
						sStatus=sOutput.substring(sOutput.indexOf("<returnCode>")+12,sOutput.indexOf("</returnCode>"));
						ErrorDesc=sOutput.substring(sOutput.indexOf("<errorDescription>")+18,sOutput.indexOf("</errorDescription>"));
					}
					
					
					//Added by Sahiba
					if(sOutput.indexOf("<errorDescription>") >0)
					{
					    WriteToLog_showpage("error description tag found---------");
						sErrorDescription = sOutput.substring(sOutput.indexOf("<errorDescription>")+18,sOutput.indexOf("</errorDescription>"));
						WriteToLog_showpage("sErrorDescription is:"+sErrorDescription);
					}
										    WriteToLog_showpage("inside webserviceCall sStatus: "+sStatus+", sCall: "+sCall);
					if(sCall.indexOf("DEBITCARD_UPGRADE_DOWNGRADE")>=0 && ErrorDesc.indexOf("100901")>=0)
					{
						sOutput=sOutput.substring(0,sOutput.indexOf("<returnCode>")+12)+"0"+sOutput.substring(sOutput.indexOf("</returnCode>"),sOutput.length());
						errCase="1";
						sStatus="0";
					}
					else if(sCall.indexOf("SIGNATURE_UPLOAD")>=0 && sStatus.equalsIgnoreCase("2"))
					{
						//------Retry
						sInput="<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>select seq_webservice.nextval REFNO from dual</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
						sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
						String reftemp=REFNO;
						REFNO=sOutput.substring(sOutput.indexOf("<REFNO>")+7,sOutput.indexOf("</REFNO>"));
						callXML=callXML.replaceAll("<REF_NO>"+reftemp+"</REF_NO>","<REF_NO>"+REFNO+"</REF_NO>");
						// Edit by Abhay - WebService Calls uing Socket 22_10_2020
						sOutput = connectToSocket(callXML);
						//sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",callXML);						
						sStatus=sOutput.substring(sOutput.indexOf("<returnCode>")+12,sOutput.indexOf("</returnCode>"));
						ErrorDesc=sOutput.substring(sOutput.indexOf("<errorDescription>")+18,sOutput.indexOf("</errorDescription>"));
						if(sOutput.indexOf("<Reason>") >0)
						{
							sReason = sOutput.substring(sOutput.indexOf("<Reason>")+8,sOutput.indexOf("</Reason>"));
						}
						errCase="0";
						//sStatus="0";
						//--------========================================================
					}
					else if(sCall.indexOf("SIGNATURE_UPLOAD")>=0 && sReason.indexOf("Image Already Exists")>=0)
					{
						
						
						
						sOutput=sOutput.substring(0,sOutput.indexOf("<returnCode>")+12)+"0"+sOutput.substring(sOutput.indexOf("</returnCode>"),sOutput.length());
						ErrorDesc =sReason;
						errCase="1";
						sStatus="0";
					}
					
					//Added by Sahiba
					//WriteToLog_showpage("sCall---------"+sCall);
					else if(sCall.indexOf("KYC_UPDATE")>=0 && sErrorDescription.indexOf("Record already exists")>=0)
					{
					    WriteToLog_showpage("condtn satisfied:"); 
						sOutput=sOutput.substring(0,sOutput.indexOf("<returnCode>")+12)+"0"+sOutput.substring(sOutput.indexOf("</returnCode>"),sOutput.length());
						WriteToLog_showpage("sOutput]]]]:"+sOutput); 
						ErrorDesc =sErrorDescription;
						errCase="1";
						sStatus="0";
					}
				
					else if(sCall.indexOf("FATCA_UPDATE")>=0 && sErrorDescription.indexOf("Record already exists")>=0)
					{
					    WriteToLog_showpage("condtn satisfied FATCA:"); 
						sOutput=sOutput.substring(0,sOutput.indexOf("<returnCode>")+12)+"0"+sOutput.substring(sOutput.indexOf("</returnCode>"),sOutput.length());
						WriteToLog_showpage("sOutput FATCA]]]]:"+sOutput); 
						ErrorDesc =sErrorDescription;
						errCase="";
						sStatus="0";
						WriteToLog_showpage("ErrorDesc:"+ErrorDesc+"--errCase--"+errCase+"--sStatus--"+sStatus); 
					}
//Start edit by Shivani - Chequebook CR 28062020
					else if(ErrorDesc.equalsIgnoreCase("CHEQUEBOOKCR_EXCLUDE") || ErrorDesc.equalsIgnoreCase("EXISTING_CUSTOMER") || ErrorDesc.equalsIgnoreCase("PERFORM_VALIDATION_EXCLUDE") || ErrorDesc.equalsIgnoreCase("BRMS_RESULT_EXCLUDE"))
					{
						sStatus = "0";
						ErrorDesc = "Skipped";
						sResVal = "";
					}
					else if(ErrorDesc.equalsIgnoreCase("CHEQUEBOOK_HANDOFF_DONE"))
					{
						sStatus = "0";
						ErrorDesc = "Skipped. Chequebook Handoff already processed.";
						sResVal = "";
					}
					else if(ErrorDesc.equalsIgnoreCase("CHEQUEBOOKCALL_SKIPPED"))
					{
						
						sStatus = "0";
						ErrorDesc = "Skipped. Call already executed.";
						sResVal = "";
					}
					else if(ErrorDesc.indexOf("Customer has been already registered for sms banking")>=0 && sCall.indexOf("SMS_REGISTRATION")>=0 )
					{
						
						sStatus = "0";
						ErrorDesc = "Customer is already registered.";
						sResVal = "";
					}
					else if(sCall.indexOf("BRMS_ECB_EXCLUSION")>=0 && sStatus.trim().equals("0"))
					{
						WriteToLog_showpage("before ecbExclusionStatus :- ");

						String passType=callXML.substring(callXML.indexOf("<passType>")+10,callXML.indexOf("</passType>"));
						WriteToLog_showpage("passType :  "+passType);
						String userName=callXML.substring(callXML.indexOf("<UserName>")+10,callXML.indexOf("</UserName>"));
						WriteToLog_showpage("userName :  "+userName);
						String ecbExclusionStatus=sOutput.substring(sOutput.indexOf("<ecbExclusionStatus>")+20,sOutput.indexOf("</ecbExclusionStatus>"));
						WriteToLog_showpage("Ecb Exclusion Status :  "+ecbExclusionStatus);
						
						
						if(ecbExclusionStatus.equals(""))
						{
							sStatus = "-1";
							ErrorDesc=sOutput.substring(sOutput.indexOf("<responseDesc>")+14,sOutput.indexOf("</responseDesc>"));
							sResVal = "";
						}
					else
						{
							/*String sQuerychq= "SELECT DISTINCT(PROD_CODE), ACC_NO FROM USR_0_PRODUCT_SELECTED AO JOIN USR_0_PRODUCT_MASTER PROD ON AO.PROD_CODE= PROD.PRODUCT_CODE WHERE PROD.CHEQUE_BOOK_FAC='Y' AND WI_NAME='"+sWIName+"' ORDER BY PROD_CODE";*/
							String sQuerychq= "SELECT PROD_CODE, ACC_NO FROM USR_0_PRODUCT_SELECTED AO JOIN USR_0_PRODUCT_MASTER PROD ON AO.PROD_CODE= PROD.PRODUCT_CODE WHERE WI_NAME='"+sWIName+"' AND PROD.CHEQUE_BOOK_FAC='Y' AND PROD.CURRENCY_CODE=AO.CURRENCY ORDER BY PROD_CODE";
							WriteToLog_showpage(" ***************input  sQuerychq  :"+sQuerychq+" *****************************");		
												
							String sInputchq=prepareAPSelectInputXml(sQuerychq,sCabname,sSessionId);
							String sOutput_modacc=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputchq);
							WriteToLog_showpage(" ***************sOutput   ------------:"+sOutput_modacc);
							int totalRetrieved = Integer.parseInt(sOutput_modacc.substring(sOutput_modacc.indexOf("<TotalRetrieved>")+16,sOutput_modacc.indexOf("</TotalRetrieved>")));
							WriteToLog_showpage(" ***************totalRetrieved   ------------:"+totalRetrieved);
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
																			
										if(ecbExclusionStatus.equalsIgnoreCase("Exclude"))
										{
											String sInput1=prepareAPInsertInputXml("USR_0_ECB_EXCLUSION","WI_NAME,WMSID,SOURCE,CUSTSNO,CID,ACCOUNTNO,EXCLREASON,CREATEDDATE,BPMUSER","'" + sWIName + "','"+wmsId+"','"+typ+"','"+SLNO+"','"+CID+"','"+ACC_NO+"','"+ecbExclusionStatus+"',sysdate,'"+userName+"'",sCabname,sSessionId);
											WriteToLog_showpage(" ***************insert in USR_0_ECB_EXCLUSION "+sInput1+" *****************************");
											String sOutput1=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput1);						
											WriteToLog_showpage(" ***************insert sOutput for data in USR_0_ECB_EXCLUSION "+sOutput1+" ***************************");
											
											String sInput2=prepareAPInsertInputXml("CHQBOOKVALIDATIONDATA","WI_NAME,WMSID,PROCESSFLAG,SOURCE,CUSTSNO,CID,ACCOUNTNO,ACCTRELTYPE,PASSPORTTYPE,ECB_EXCLUSION,ECBDATE,CREATEDDATE,MODIFYDATE","'" + sWIName + "','"+wmsId+"','U','"+typ+"','"+SLNO+"','"+CID+"','"+ACC_NO+"','"+custAccRelation+"','"+passType+"','"+ecbExclusionStatus+"',sysdate,sysdate,sysdate",sCabname,sSessionId);
											WriteToLog_showpage(" ***************insert in CHQBOOKVALIDATIONDATA "+sInput2+" *****************************");
											String sOutput2=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput2);						
											WriteToLog_showpage(" ***************insert sOutput for data in CHQBOOKVALIDATIONDATA "+sOutput2+" *****************************");
										}
										else
										{
											String sInput2=prepareAPInsertInputXml("CHQBOOKVALIDATIONDATA","WI_NAME,WMSID,SOURCE,CUSTSNO,CID,ACCOUNTNO,ACCTRELTYPE,PASSPORTTYPE,ECB_EXCLUSION,ECBDATE,CREATEDDATE,MODIFYDATE","'" + sWIName + "','"+wmsId+"','"+typ+"','"+SLNO+"','"+CID+"','"+ACC_NO+"','"+custAccRelation+"','"+passType+"','"+ecbExclusionStatus+"',sysdate,sysdate,sysdate",sCabname,sSessionId);
											WriteToLog_showpage(" ***************insert in CHQBOOKVALIDATIONDATA "+sInput2+" *****************************");
											String sOutput2=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput2);						
											WriteToLog_showpage(" ***************insert sOutput for data in CHQBOOKVALIDATIONDATA "+sOutput2+" *****************************");
										}
									}
								}
							}								
							
							errCase="0";
						}
						
							WriteToLog_showpage(" ***************sStatus  :"+sStatus+" *****************************");		
							WriteToLog_showpage(" **************ErrorDesc  :"+ErrorDesc+" *****************************");		
					}
					else if(sCall.indexOf("ECB_VALIDATION")>=0 && sStatus.trim().equals("0"))
					{
					 if(!ecbRestFlag.equalsIgnoreCase("Y")){
						WriteToLog_showpage("inside ECB_VALIDATION XML:  ");

						String emiratesId=callXML.substring(callXML.indexOf("<EmiratesId>")+12,callXML.indexOf("</EmiratesId>"));
						String localDBSearchReq=callXML.substring(callXML.indexOf("<LocalDBSearchReq>")+18,callXML.indexOf("</LocalDBSearchReq>"));
						String userID=callXML.substring(callXML.indexOf("<UserID>")+8,callXML.indexOf("</UserID>"));
						String userName=callXML.substring(callXML.indexOf("<UserName>")+10,callXML.indexOf("</UserName>"));

						WriteToLog_showpage("localDBSearchReq :  "+localDBSearchReq);
						WriteToLog_showpage("userName :  "+userName);

						String params="<ReferenceNumber>#<TradeLicenseNumber>#<noOfChequeBounceInLast12Months>#<lastChequeBouncedDate>#<HitNoHit>";
						String[]param=params.split("#");
						
							String[] val=new String[param.length];
							for(int i=0;i<param.length;i++)
							{
								WriteToLog_showpage("INSIDE FOR LOOP " + param[i]);
								val[i]=sOutput.substring(sOutput.indexOf(param[i])+param[i].length(),sOutput.indexOf(param[i].replace("<","</")));
								WriteToLog_showpage("INSIDE FOR LOOP--1"+val[i]);
							}
						
						String sInput2=prepareAPUpdateInputXml("CHQBOOKVALIDATIONDATA","EIDANO,TRADELNO,NOCHQBOUNCE,LATESTCHQBOUNCEDATE,LOCALDB,ECBREFNO,ECBUSER,BPMUSER,HITNOHIT","'"+emiratesId+"','"+val[1]+"','"+val[2]+"','"+val[3]+"','"+localDBSearchReq+"','"+val[0]+"','"+userID+"','"+userName+"','"+val[4]+"'","WI_NAME='"+sWIName+"' and CUSTSNO='"+SLNO+"'",sCabname,sSessionId);
						WriteToLog_showpage(" ***************update CHQBOOKVALIDATIONDATA "+sInput2+" *****************************");
						String sOutput2=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput2);						
						WriteToLog_showpage(" ***************update sOutput for CHQBOOKVALIDATIONDATA "+sOutput2+" *****************************");
							
						errCase="0";
					}
					//Added by Shivanshu ATP-501
					else {
						
						String sInput2=prepareAPUpdateInputXml("CHQBOOKVALIDATIONDATA","EIDANO,TRADELNO,NOCHQBOUNCE,LATESTCHQBOUNCEDATE,LOCALDB,ECBREFNO,ECBUSER,BPMUSER,HITNOHIT","'','','"+totalCountBouncedCheque12M+"','','','"+ecbReferenceNumber+"','','','"+ecbHit+"'","WI_NAME='"+sWIName+"'",sCabname,sSessionId);
						WriteToLog_showpage(" ***************insert in ECB JSON CHQBOOKVALIDATIONDATA "+sInput2+" *****************************");
						String sOutput2=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput2);						
						WriteToLog_showpage(" ***************insert sOutput for data in CHQBOOKVALIDATIONDATA "+sOutput2+" *****************************");
                          errCase="0";
					}
					}
					else if(sCall.indexOf("BRMS_ELIGIBILITY")>=0 && sStatus.trim().equals("0"))
					{
						WriteToLog_showpage("before eligibility :  ");		
						errCase="0";
						String eligibility=sOutput.substring(sOutput.indexOf("<eligibility>")+13,sOutput.indexOf("</eligibility>"));
						WriteToLog_showpage("eligibility :  "+eligibility);
						
						if(eligibility.equals(""))
						{
							sStatus = "-1";
							ErrorDesc=sOutput.substring(sOutput.indexOf("<responseDesc>")+14,sOutput.indexOf("</responseDesc>"));
							sResVal = "";
						}
						else
						{
							if(noOfCust==1)
							{
								String sInput2=prepareAPUpdateInputXml("CHQBOOKVALIDATIONDATA","ELIGIBLECHQLEAVES,PROCESSFLAG","'"+eligibility+"','U'","wi_name='"+sWIName+"' and CUSTSNO="+SLNO+"",sCabname,sSessionId);
								WriteToLog_showpage(" ***************update in CHQBOOKVALIDATIONDATA "+sInput2+" *****************************");
								String sOutput2=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput2);						
								WriteToLog_showpage(" ***************update sOutput for data in CHQBOOKVALIDATIONDATA "+sOutput2+" *****************************");
							
								String sInput3=prepareAPUpdateInputXml("EXT_AO","CHEQUEBOOK_ELIGIBILITY","'"+eligibility+"'","wi_name='"+sWIName+"'",sCabname,sSessionId);
								WriteToLog_showpage(" ***************update in EXT_AO "+sInput3+" *****************************");
								String sOutput3=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput3);						
								WriteToLog_showpage(" ***************update sOutput for data in EXT_AO "+sOutput3+" *****************************");
							
								if(eligibility.equalsIgnoreCase("N"))
								{
									sStatus = "-1";
									ErrorDesc = "Customer is not eligible to open an account. Bounced cheques are more than 3.";
									sResVal = "";
								}
							}
							else
							{
								String sInput2=prepareAPUpdateInputXml("CHQBOOKVALIDATIONDATA","ELIGIBLECHQLEAVES","'"+eligibility+"'","wi_name='"+sWIName+"' and CUSTSNO="+SLNO+"",sCabname,sSessionId);
								WriteToLog_showpage(" ***************update in CHQBOOKVALIDATIONDATA "+sInput2+" *****************************");
								String sOutput2=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput2);						
								WriteToLog_showpage(" ***************update sOutput for data in CHQBOOKVALIDATIONDATA "+sOutput2+" *****************************");
							}
						}
					}
					else if(sCall.indexOf("BRMS_JOINTCUSTOMER")>=0 && sStatus.trim().equals("0"))
					{
						WriteToLog_showpage("before eligibility :  ");
								
						errCase="0";
						String eligibility=sOutput.substring(sOutput.indexOf("<eligibility>")+13,sOutput.indexOf("</eligibility>"));
						WriteToLog_showpage("joint eligibility :  "+eligibility);
						
						if(eligibility.equals(""))
						{
							sStatus = "-1";
							ErrorDesc=sOutput.substring(sOutput.indexOf("<responseDesc>")+14,sOutput.indexOf("</responseDesc>"));
							sResVal = "";
						}
						else
						{
							String sInput2=prepareAPUpdateInputXml("CHQBOOKVALIDATIONDATA","PROCESSFLAG","'U'","wi_name='"+sWIName+"' AND UPPER(ELIGIBLECHQLEAVES) IN ('P','F','N')",sCabname,sSessionId);
							WriteToLog_showpage(" ***************update in CHQBOOKVALIDATIONDATA "+sInput2+" *****************************");
							String sOutput2=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput2);						
							WriteToLog_showpage(" ***************update sOutput for data in CHQBOOKVALIDATIONDATA "+sOutput2+" *****************************");
								
							String sInput3=prepareAPUpdateInputXml("EXT_AO","CHEQUEBOOK_ELIGIBILITY","'"+eligibility+"'","wi_name='"+sWIName+"'",sCabname,sSessionId);
							WriteToLog_showpage(" ***************update in EXT_AO "+sInput3+" *****************************");
							String sOutput3=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput3);						
							WriteToLog_showpage(" ***************update sOutput for data in EXT_AO "+sOutput3+" *****************************");
						
							if(eligibility.equalsIgnoreCase("N"))
							{
								sStatus = "-1";
								ErrorDesc = "Customer is not eligible to open an account. Bounced cheques are more than 3.";
								sResVal = "";
								brmsEligibleFlag="N";
							}
						}
					}else if (sCall.indexOf("FETCH_FAMILY")>=0 && sStatus.trim().equals("0")){
						WriteToLog_showpage(" ***************starts");
						XMLParser xp1 = new XMLParser(sOutput);
						int start = xp1.getStartIndex("FamilyDetails", 0, 0);
						int deadEnd = xp1.getEndIndex("FamilyDetails", start, 0);
						int noOfFields = xp1.getNoOfFields("FamilyMember", start, deadEnd);
						int end = 0;
						if(noOfFields > 0){
							for(int i = 1; i <= noOfFields; ++i) {
								start = xp1.getStartIndex("FamilyMember", end, 0);
								end = xp1.getEndIndex("FamilyMember", start, 0);	
								String customerId = xp1.getValueOf("customerId", start, end);
								String relationship = xp1.getValueOf("relationship", start, end);
								WriteToLog_showpage("customerId : "+customerId);
								WriteToLog_showpage("relationship : "+relationship);
								String sInput2=prepareAPInsertInputXml("USR_0_FAMILY_MEMBER_DETAILS","WI_NAME,CID,RELATIONSHIP,NEW_EXISTING","'" + sWIName + "','"+customerId+"','"+relationship+"','Existing'",sCabname,sSessionId);
								WriteToLog_showpage(" ***************insert in CHQBOOKVALIDATIONDATA "+sInput2+" *****************************");
								String sOutput2=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput2);						
								WriteToLog_showpage(" output "+sOutput2+" *****************************");
							}
						}
					}else
					{
						if(sOutput.indexOf("<Status>")>0)
						{
							ErrorDesc=ErrorDesc+"|"+sOutput.substring(sOutput.indexOf("<Status>")+8,sOutput.indexOf("</Status>"));
						}
						if(sOutput.indexOf("<Reason>")>0)
						{
							ErrorDesc=ErrorDesc+"|"+sReason;
						}
						errCase="0";
					}
					//End edit by Shivani - Chequebook CR 28062020

					returnXML=sOutput;

					returnXML=returnXML.replace(",","");
					

					//get call paratmeters for processing
					sInput="<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>select OUTPUTTAGS,RETURNLABEL,UPDATEPROC,PROCPARAMS,OUTPUTQUERY from usr_0_integration_config where instr('"+sCall+"',calltype)>0</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
					
					WriteToLog_showpage("sInputsss---# #"+sInput);
					String paramOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
					

					///if there is no output then dont do anything..just return output



					String noRecord = paramOutput.substring(paramOutput.indexOf("<TotalRetrieved>")+16,paramOutput.indexOf("</TotalRetrieved>"));
					
					if(!noRecord.equalsIgnoreCase("0"))
					{				
						
						String params=paramOutput.substring(paramOutput.indexOf("<OUTPUTTAGS>")+12,paramOutput.indexOf("</OUTPUTTAGS>"));
						String outputquery="";
						outputquery=paramOutput.substring(paramOutput.indexOf("<OUTPUTQUERY>")+13,paramOutput.indexOf("</OUTPUTQUERY>"));
						String returnLabel=paramOutput.substring(paramOutput.indexOf("<RETURNLABEL>")+13,paramOutput.indexOf("</RETURNLABEL>"));

						
						String ProcName=paramOutput.substring(paramOutput.indexOf("<UPDATEPROC>")+12,paramOutput.indexOf("</UPDATEPROC>"));
						String ProcParams=paramOutput.substring(paramOutput.indexOf("<PROCPARAMS>")+12,paramOutput.indexOf("</PROCPARAMS>"));

						WriteToLog_showpage("noRecord111111---# #"+params);
						WriteToLog_showpage("noRecord---# #"+noRecord);
						String[]param=params.split("#");
						WriteToLog_showpage("ProcName---# #"+ProcName);
							
						//if(sStatus.trim().equalsIgnoreCase("0") && errCase.equalsIgnoreCase("0") )
						//Edit by Shivani - Chequebook CR 28062020 //ATP-501 by Shivanshu
						if((sStatus.trim().equalsIgnoreCase("0") || brmsEligibleFlag.equals("N")) && errCase.equalsIgnoreCase("0"))
						{
							String outputValue="";
							WriteToLog_showpage("noRecord111---# #"+sStatus);
							String[] val=new String[param.length];
							WriteToLog_showpage("monga12---# #"+ProcParams);
							WriteToLog_showpage("monga13---# #"+sOutput);
							WriteToLog_showpage("monga14---# #"+outputquery);
						  if (!ecbRestFlag.equalsIgnoreCase("Y")) {
							for(int i=0;i<param.length;i++)
							{
								WriteToLog_showpage("monga15---# #"+param[i]);
								val[i]=sOutput.substring(sOutput.indexOf(param[i])+param[i].length(),sOutput.indexOf(param[i].replace("<","</")));
								WriteToLog_showpage("monga val---# #"+val[i]);
								ProcParams=ProcParams.replace("$param"+(i+1)+"$",val[i]);
								outputquery=outputquery.replace("$param"+(i+1)+"$",val[i]);
								WriteToLog_showpage("ProcParams---# #"+ProcParams);
								WriteToLog_showpage("noRecord1111112---# #"+outputquery);
							}
							
							WriteToLog_showpage("noRecord1112---# #"+outputquery);
							if(!outputquery.equalsIgnoreCase(""))
							{
								sInput="<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+outputquery+"</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
								sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);	
								outputValue=sOutput.substring(sOutput.indexOf("<DESCRIPTION>")+13,sOutput.indexOf("</DESCRIPTION>"));
								WriteToLog_showpage("noRecord1113---# #"+outputValue);
							}
							
							if(outputValue.equalsIgnoreCase(""))
							{
							
								outputValue=val[0];
							}
						 } else {
							 outputValue=ErrorDesc;
						 }

							sInput="<?xml version=\"1.0\"?><APUpdate_Input><Option>APUpdate</Option><TableName>usr_0_integration_calls</TableName><ColName>response</ColName><Values>'"+returnLabel+outputValue+"'</Values><WhereClause>wi_name='"+sWIName+"' and call_order='"+ seq+"'</WhereClause><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APUpdate_Input>";	
							sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);	
							WriteToLog_showpage("sOutputsOutput---# #"+sOutput);
							WriteToLog_showpage("ProcName1---# #"+ProcName);
							sResVal=returnLabel+outputValue;
							WriteToLog_showpage("ProcName2--- #"+ProcName);
							if(!ProcName.equalsIgnoreCase(""))
							{						
								WriteToLog_showpage("ProcName3---# #"+ProcName);
								ProcParams=ProcParams.replace("$winame$",sWIName);
								WriteToLog_showpage("sWIName---# #"+sWIName);
								ProcParams=ProcParams.replace("$slno$",SLNO);
								WriteToLog_showpage("SLNO---# #"+SLNO);
								ProcParams=ProcParams.replace("$serno$",seq);
								WriteToLog_showpage("seq---# #"+seq);
								sInput="<?xml version=\"1.0\"?><APProcedure_Input><Option>APProcedure</Option><ProcName>"+ProcName+"</ProcName><Params>"+ProcParams+",'"+sCall+"'"+"</Params><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APUpdate_Input>";	
								WriteToLog_showpage("sInput---# #"+sInput);								
								sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);	
								WriteToLog_showpage("sOutput---# #"+sOutput);
							}

						}
						WriteToLog_showpage("not if---# #");	
					}
					String status="";
					if(sStatus.trim().equalsIgnoreCase("1")||sStatus.trim().equalsIgnoreCase("")||sStatus.trim().equalsIgnoreCase("2") ||sStatus.trim().equalsIgnoreCase("-1"))
						status="Error";
					else
						status="Success";				
					
					
					
					WriteToLog_showpage("not if-1--# #"+status);	
					WriteToLog_showpage("not if-1--ErrorDesc 1# #"+ErrorDesc);
					ErrorDesc=ErrorDesc.replaceAll(",","");	
					WriteToLog_showpage("not if-1--ErrorDesc 2# #"+ErrorDesc);
					if(status.equalsIgnoreCase("Success") && errCase.equalsIgnoreCase("0"))
						//WriteToLog_showpage("Inside 1# #");
						ErrorDesc="";
					else if(errCase.equalsIgnoreCase("1"))
					{
						//WriteToLog_showpage("Inside 2# #");
						ErrorDesc="Ref No:"+REFNO+"|"+ErrorDesc;
					}
					else
						//WriteToLog_showpage("Inside 3# #");
						ErrorDesc="Ref No:"+REFNO+"|"+ErrorDesc;
						
						
						// Code end here Old_Refe_number_01
						WriteToLog_showpage("Outside ErrorDesc# #"+ErrorDesc);
					sInput="<?xml version=\"1.0\"?><APUpdate_Input><Option>APUpdate</Option><TableName>usr_0_integration_calls</TableName><ColName>status,output_xml,ERRORDESC</ColName><Values>'"+status+"','"+returnXML +"','"+ErrorDesc+"'</Values><WhereClause>wi_name='"+sWIName+"' and call_order='"+ seq+"' and call_name='"+ sCall+"'</WhereClause><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APUpdate_Input>";	
					WriteToLog_showpage("final APupdate sInput# #"+sInput);
					sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);	
                 WriteToLog_showpage("final APupdate sOutput# #"+sOutput);					
					out.println(sStatus+"#"+sResVal+"#"+ErrorDesc);
					//sOutput=sOutput.substring(sOutput.indexOf("</Record>")+"</Record>".length());
					
				}
			}		
		}
		else
		{
		}



	}

}
catch(Exception e)
{
	sInput="<?xml version=\"1.0\"?><APInsert_Input><Option>APInsert</Option><TableName>USR_0_INTEGRATION_VALUES</TableName>" +
    "<ColName>wi_name,exception_error</ColName><Values>'" + sWIName + "','"+e.getMessage()+"'</Values><EngineName>" + sCabname + "</EngineName>" +
    "<SessionId>" + sSessionId + "</SessionId></APInsert_Input>";								
	sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
	WriteToLog_showpage("Running Issue 21112023 Start in catch");
	sInput="<?xml version=\"1.0\"?><APUpdate_Input><Option>APUpdate</Option><TableName>usr_0_integration_calls</TableName><ColName>status</ColName><Values>'Error'</Values><WhereClause>wi_name='"+sWIName+"' and call_order='"+ seq+"' </WhereClause><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APUpdate_Input>";	
	WriteToLog_showpage("final APupdate sInput# #"+sInput);
	sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);	
	WriteToLog_showpage("Running Issue 21112023 End in catch");
	e.printStackTrace();
	WriteToLog_showpage(StackTraceToString_showpage(e));
	WriteToLog_showpage("Error#-1#"+e.getMessage());
	WriteToLog_showpage("In Exception---# #"+sOutput);
}

%>
<%!
	String SOCKET_IP_ADDRESS = "";
    Integer SOCKET_PORT = 0;
   
	public void initSocket(String sCabname, String sSessionId, String sJtsIp, String iJtsPort) {
		try {
			String query = "SELECT KEY, VALUE FROM NG_UTILITY_SOCKET_CONFIG";
			WriteToLog_showpage("Query: "+ query);
			
			String inputXML=prepareAPSelectInputXml(query, sCabname, sSessionId);
			String outputXML = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",inputXML);
			
			String mainCode = outputXML.substring(outputXML.indexOf("<MainCode>")+10,outputXML.indexOf("</MainCode>"));
			
			if(mainCode.equalsIgnoreCase("0")) {					
				WriteToLog_showpage("inside  maincode 0");
				int totalRetrieved = Integer.parseInt(outputXML.substring(outputXML.indexOf("<TotalRetrieved>")+16,outputXML.indexOf("</TotalRetrieved>")));
				WriteToLog_showpage(" ***************totalRetrieved   ------------:"+totalRetrieved);
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
				
							if ("IP".equals(key)) {
								SOCKET_IP_ADDRESS = value;
							} else if ("PORT".equals(key)) {
								SOCKET_PORT = Integer.parseInt(value);
							}
						}
					}
				}
				
				WriteToLog_showpage("IP: "+ SOCKET_IP_ADDRESS);
				WriteToLog_showpage("PORT: "+ SOCKET_PORT);
			} else {
				WriteToLog_showpage("Error in initSocket"+ mainCode);
			}
		} catch (Exception e) {
			WriteToLog_showpage("Exception in initSocket: "+ e.getMessage());
			e.printStackTrace();
		}
	}
   
    public String connectToSocket(String data) {
        String result="";
		
		WriteToLog_showpage("IP: "+ SOCKET_IP_ADDRESS);
		WriteToLog_showpage("PORT: "+ SOCKET_PORT);
		
        try(Socket s = new Socket(SOCKET_IP_ADDRESS,SOCKET_PORT);
                DataInputStream din=new DataInputStream(s.getInputStream());
                DataOutputStream dout=new DataOutputStream(s.getOutputStream())) {
                writeDataToSocket(dout,data);
                result=readDataFromSocket(din);
            } catch (IOException e) {
                WriteToLog_showpage("Error in socket read/write "+ e.getMessage());
                WriteToLog_showpage(StackTraceToString_showpage(e));
            }
        return result;
    }
   
    public boolean writeDataToSocket(DataOutputStream dataOutputStream, String data) {
        boolean bFlag = false;
        try {
            if (data != null && data.length() > 0) {
                dataOutputStream.write(data.getBytes("UTF-8"));
                bFlag=true;
            }
			//out.println("Socket write");
        } catch (Exception e) {
            WriteToLog_showpage("Error in socket write "+ e.getMessage());
            WriteToLog_showpage(StackTraceToString_showpage(e));
        }
        return bFlag;
    }

    public String readDataFromSocket(DataInputStream dataInputStream) {
        StringBuilder data = new StringBuilder();
        try {
            byte[] buffer = new byte[99999];
            int length = dataInputStream.read(buffer, 0, 99999);
            byte[] arrayBytes = new byte[length];
            System.arraycopy(buffer, 0, arrayBytes, 0, length);
            data.append(new String(arrayBytes, "UTF-8"));
            int len = 0;
            while ((len = dataInputStream.read(buffer)) > 0) {
                arrayBytes = new byte[len];
                System.arraycopy(buffer, 0, arrayBytes, 0, len);
                data.append(new String(arrayBytes, "UTF-8"));
                if (dataInputStream.available()<=0)
                    break;
            }
        } catch (Exception e) {
            WriteToLog_showpage("Error in socket read "+ e.getMessage());
            WriteToLog_showpage(StackTraceToString_showpage(e));
        }
        return data.toString();
    }

//Added by Shivanshu ATP-501
public  String executeRestAPIJSON(String url, String ClientId, String ClientSecret, String inputXML, String wiName) throws Exception{
		StringBuilder outputXML = new StringBuilder();
		HttpURLConnection conn=null;
		try {
			WriteToLog_showpage("URL: "+ url);
			URL urlName = new URL(url);
			conn =  (HttpURLConnection) urlName.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("x-fapi-interaction-id", wiName);

			String authorization = ClientId + ":" + ClientSecret;
			WriteToLog_showpage("authorization: "+ authorization);
			String encodedAuthorization =  DatatypeConverter.printBase64Binary(authorization.getBytes());
			WriteToLog_showpage("encodedAuthorization: "+ encodedAuthorization);
			conn.setRequestProperty("Authorization", "Basic "+ encodedAuthorization);
			
			OutputStream os = conn.getOutputStream();
			os.write(inputXML.getBytes());
			os.flush();
			WriteToLog_showpage("conn.getResponseCode()===> "+conn.getResponseCode());
			if(conn.getResponseCode() != HttpURLConnection.HTTP_OK && conn.getResponseCode() != HttpURLConnection.HTTP_CREATED){
				WriteToLog_showpage("Failed : HTTP error code :===> "+conn.getResponseCode());
				throw new RuntimeException("Failed : HTTP error code : "+conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			WriteToLog_showpage("Output from server ...\n");
			String out ;
			while ((out = br.readLine()) != null) {
				outputXML.append(out);
//				WriteToLog_showpage("RestAPI output===> "+outputXML);
			}
		} catch (MalformedURLException e) {
			WriteToLog_showpage("RestAPI exception1===> "+e.getMessage());
		}catch(IOException e) {
			WriteToLog_showpage("RestAPI exception2===> "+e.getMessage());
		}
		finally{
			if(conn!=null){
				conn.disconnect();
			}
		}
		return outputXML+"";
	} 
%>

<%!

private String prepareAPSelectInputXml(String sQuery, String cabname, String sSessionId){	
	return "<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+sQuery+"</QueryString><EngineName>"+cabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
}

private String prepareAPInsertInputXml(String tableName,String colname,String sValues, String cabname, String sSessionId){	
	return "<?xml version=\"1.0\"?><APInsert_Input><Option>APInsert</Option><TableName>"+tableName+"</TableName><ColName>"+colname+"</ColName><Values>"+sValues+"</Values><EngineName>" + cabname + "</EngineName>" +    "<SessionId>" + sSessionId + "</SessionId></APInsert_Input>";
}

private String prepareAPUpdateInputXml(String tableName,String colname,String sValues,String whrcls, String cabname, String sSessionId){	
	return "<?xml version=\"1.0\"?><APUpdate_Input><Option>APUpdate</Option><TableName>"+tableName+"</TableName><ColName>"+colname+"</ColName><Values>"+sValues+"</Values><WhereClause>"+whrcls+"</WhereClause><EngineName>"+cabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APUpdate_Input>";
}


private void WriteToLog_showpage(String strOutput)
{
    StringBuffer str = new StringBuffer();
    str.append(DateFormat.getDateTimeInstance(0,2).format(new java.util.Date()));
    str.append("\n");
    str.append(strOutput);
    str.append("\n");

    StringBuffer strFilePath = null;
    String tmpFilePath="";

    Calendar calendar=new GregorianCalendar();
    String DtString=String.valueOf(""+calendar.get(Calendar.DAY_OF_MONTH) +(calendar.get(Calendar.MONTH) + 1) + calendar.get(Calendar.YEAR));
    try 
    {
        strFilePath = new StringBuffer(50);
        strFilePath.append(System.getProperty("user.dir"));
        strFilePath.append(File.separatorChar);
		strFilePath.append("ApplicationLogs");
		strFilePath.append(File.separatorChar);
		strFilePath.append("JSPLogs");
		strFilePath.append(File.separatorChar);
        strFilePath.append("AO");
        strFilePath.append(File.separatorChar);
        strFilePath.append("WebServiceCall");
        strFilePath.append(File.separatorChar);
        strFilePath.append("WebServiceCall_AO"+DtString+".xml");
        tmpFilePath = strFilePath.toString();
        
        BufferedWriter out = new BufferedWriter(new FileWriter(tmpFilePath, true));
        
        out.write(str.toString());
        out.close();
    } catch (Exception exception) {
    } finally {
        strFilePath = null;
    }
    
}


private void WriteToErrorLog_showpage(String strOutput)
{
    StringBuffer str = new StringBuffer();
    str.append(DateFormat.getDateTimeInstance(0,2).format(new java.util.Date()));
    str.append("\n");
    str.append(strOutput);
    str.append("\n");

    StringBuffer strFilePath = null;
    String tmpFilePath="";

    Calendar calendar=new GregorianCalendar();
    String DtString=String.valueOf(""+calendar.get(Calendar.DAY_OF_MONTH) +(calendar.get(Calendar.MONTH) + 1) + calendar.get(Calendar.YEAR));
    try 
    {
        strFilePath = new StringBuffer(50);
        strFilePath.append(System.getProperty("user.dir"));
        strFilePath.append(File.separatorChar);
		strFilePath.append("ApplicationLogs");
		strFilePath.append(File.separatorChar);
		strFilePath.append("JSPLogs");
		strFilePath.append(File.separatorChar);
        strFilePath.append("AO");
        strFilePath.append(File.separatorChar);
        strFilePath.append("WebServiceCall");
        strFilePath.append(File.separatorChar);
        strFilePath.append("WebServiceCall_Error_AO"+DtString+".xml");
        tmpFilePath = strFilePath.toString();
        
        BufferedWriter out = new BufferedWriter(new FileWriter(tmpFilePath, true));
        
        out.write(str.toString());
        out.close();
    } catch (Exception exception) {
    } finally {
        strFilePath = null;
    }
    
}

private static String StackTraceToString_showpage(Exception e) {
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        e.printStackTrace(printWriter);
        return result.toString();
        }
%>