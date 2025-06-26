/*----------------------------------------------------------------------------------------------------
		NEWGEN SOFTWARE TECHNOLOGIES LIMITED
Group					  : AP2
Product / Project		  : ADCB 
Module					  : CRS 6.1
File Name				  : AddCRSDetails.java
Author					  : Harinder Pal Singh
Date written (DD/MM/YYYY) : 06 August,2018
Description				  : Client stub for ADD CRS Webservice.
----------------------------------------------------------------------------------------------------
CHANGE HISTORY
-------------------------------------------------------------------------------------------------------
Problem No/CR No   Change Date   Changed By    Change Description
------------------------------------------------------------------------------------------------------
NA						NA			 NA					NA
-----------------------------------------------------------------------------------------------------*/

package com.newgen.client;
/**
 * @Date : 05 july 2018
 * @Purpose : CR July 2018
 * 6.1. CRS Fetch Call 
 * */


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.newgen.AESEncryption;
import com.newgen.stub.InqCommonReportingStandardsStub;
import com.newgen.stub.InqCommonReportingStandardsStub.AddCRSDetailsReqMsg;
import com.newgen.stub.InqCommonReportingStandardsStub.AddCRSDetailsReq_type0;
import com.newgen.stub.InqCommonReportingStandardsStub.AddCRSDetailsResMsg;
import com.newgen.stub.InqCommonReportingStandardsStub.CustomerDetails_type0;
import com.newgen.stub.InqCommonReportingStandardsStub.DocumentDetails_type0;
import com.newgen.stub.InqCommonReportingStandardsStub.Documents_type0;
import com.newgen.stub.InqCommonReportingStandardsStub.HeaderType;
import com.newgen.stub.InqCommonReportingStandardsStub.TaxResidenceCountries_type0;
import com.newgen.stub.InqCommonReportingStandardsStub.TaxResidenceCountryDtls_type0;

public class AddCRSDetails extends WebServiceHandler {

	static String sWSDLPath="";
	static String sCabinet="";
	static String sUser="";
	static String sPassword="";
	static boolean sLoginReq=false;
	static long lTimeOut = 30000;
	String sOrgRes="";
	String xmlInput="";
	static String dburl="";
	static String dbuser="";
	static String dbpass="";
	static String sOutput= "";
	XMLParser xmlDataParser = new XMLParser();

	WebServiceHandler sHandler;

	public String fetchCRSDetails(String sInputXML)
	{
		String Status="";
		LogGEN.writeTrace("Log", "Fuction called AddCRSdetails");
		
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";		
		
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		LogGEN.writeTrace("Log", "input:"+sInputXML);

		String sCustomerID="";
		String sWiname="";
		String sessionID="",call_type="";
		try {
			sHandler= new WebServiceHandler();
			xmlDataParser.setInputXML(sInputXML);
			loadWSDLDtls(sHandler);
			LogGEN.writeTrace("Log", "read Property successfully");
			sCustomerID= xmlDataParser.getValueOf("CustID");	
			String custType=xmlDataParser.getValueOf("CustType");
			String ref_no=xmlDataParser.getValueOf("REF_NO"); 
			sWiname =xmlDataParser.getValueOf("WiName");
			String firstName=xmlDataParser.getValueOf("FIRST_NAME");
			String lastName=xmlDataParser.getValueOf("LAST_NAME");
			String birthCity=xmlDataParser.getValueOf("CITY_OF_BIRTH");
			String clsId=xmlDataParser.getValueOf("CLASS_ID");
			String crsObt=xmlDataParser.getValueOf("CERT_OBTAINED");
			String clsDt=xmlDataParser.getValueOf("CLASS_DATE");
			String certDt=xmlDataParser.getValueOf("CERT_DATE");
			sessionID= xmlDataParser.getValueOf("SessionId");
			call_type=xmlDataParser.getValueOf("Calltype");
			LogGEN.writeTrace("Log", "Fuction called ref_no"+ref_no +sWiname+ firstName+lastName+birthCity+birthCity+clsId+crsObt+clsDt+sessionID+call_type);
			InqCommonReportingStandardsStub stub= new InqCommonReportingStandardsStub(sWSDLPath);
			AddCRSDetailsReq_type0 requestMsg= new AddCRSDetailsReq_type0();
			AddCRSDetailsReqMsg reMsg=new AddCRSDetailsReqMsg();
			AddCRSDetailsResMsg CRSDetailsResMsg=new AddCRSDetailsResMsg();
			CustomerDetails_type0 custDetails=new CustomerDetails_type0();
			reMsg.setHeader(setHeaderDtls(sDate,ref_no,xmlDataParser.getValueOf("SENDERID")));

			//setting values for fetch input call
			requestMsg.setCustomerId(sCustomerID);
			requestMsg.setCustomerType(custType);
			
			if("".equalsIgnoreCase(firstName) || null==firstName)	//modified by harinder on 22102018
			{
				custDetails.setCustFirstName("NFN");
			}else
			{
				custDetails.setCustFirstName(firstName);
			}
			custDetails.setCustLastName(lastName);
			if(birthCity==null || birthCity.isEmpty() || "null".equalsIgnoreCase(birthCity))
				birthCity="";
			custDetails.setCustBirthCity(birthCity);
			custDetails.setClassificationId(clsId);
			custDetails.setCrsCertFormObtained(crsObt);
			custDetails.setClassificationDate(clsDt);
			custDetails.setMakerDate(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));//added by harish			
			custDetails.setChannel(setCRSChannelId(sWiname));//added by harish
			custDetails.setMakerId("WMSMAKER");
			custDetails.setCheckerId("WMSCHECKER");
			custDetails.setCheckerDate(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
			custDetails.setResidenceAddressConfirmationStatus("Y");

			stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			requestMsg.setCustomerDetails(custDetails);

				
			String TaxDetails="";
			TestXML xml1=new TestXML();

			try {
				TaxDetails=xml1.getTagValue(sInputXML, "TaxDetails");
			} catch (ParserConfigurationException e) {			
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			}

			String[] taxinfo;
			if(!TaxDetails.equalsIgnoreCase(""))
			{
				taxinfo=TaxDetails.split(";");
			}
			else
				taxinfo=new String[0];		


			TaxResidenceCountryDtls_type0 taxdetailsinput =new TaxResidenceCountryDtls_type0();
			TaxResidenceCountries_type0[] taxdetinp=new TaxResidenceCountries_type0[taxinfo.length];

			for(int i=0;i<taxinfo.length;i++)
			{
				taxinfo[i]=taxinfo[i]+"$";
				String[] tagval=taxinfo[i].split(",");				
				taxdetinp[i]=new TaxResidenceCountries_type0();
				taxdetinp[i].setTaxResidenceCountry(tagval[0]);;
				taxdetinp[i].setReasonId(tagval[1]);				
				taxdetinp[i].setTaxpayerIdNumber(setTinNumber(tagval[2].replace("$", "")));//added by harish for tin if blank
				taxdetinp[i].setReasonDesc(tagval[3].replace("$", ""));    //added by harinder on 12102018				
			}
			taxdetailsinput.setTaxResidenceCountries(taxdetinp);
			requestMsg.setTaxResidenceCountryDtls(taxdetailsinput);
			DocumentDetails_type0 docDtlsType=setDocCrs(sWiname, sCustomerID,certDt);
			if(docDtlsType!=null){
				requestMsg.setDocumentDetails(docDtlsType);	
			}
			reMsg.setAddCRSDetailsReq(requestMsg);
			CRSDetailsResMsg =stub.addCRSDetails_Oper(reMsg);
			xmlInput=stub.reqXml;
			LogGEN.writeTrace("Log", "Org input:"+xmlInput);
			sOrgRes= stub.resCRS;
			LogGEN.writeTrace("Log", "org response:"+sOrgRes);
			
			HeaderType header= CRSDetailsResMsg.getHeader();
			sReturnCode=  header.getReturnCode();
			sErrorDetail=header.getErrorDetail();
			sErrorDesc = header.getErrorDescription();

			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{

				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<Output>"+
				"<Option>ADD_CRS_DETAILS</Option>" +
				"<returnCode>"+sReturnCode+"</returnCode>" +
				"<errorDescription>"+sErrorDetail+"</errorDescription>"+
				"<Status>"+sErrorDesc+"</Status>" +		
				"<errorDesc>"+sErrorDesc+"</errorDesc>" +						
				"</Output>";				
			} else
			{
				sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
				"<Output>" +
				"<Option>ADD_CRS_DETAILS</Option>" +
				"<returnCode>"+sReturnCode+"</returnCode>" +
				"<errorDesc>"+sErrorDesc+"</errorDesc>" +	
				"<errorDescription>"+sErrorDetail+"</errorDescription>"+
				"</Output>";
			}
		} catch (IOException e) {
			sErrorDetail=e.getMessage();
			sReturnCode="-1";
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADD_CRS_DETAILS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to fetch CRS Details</td></Output>";
			e.printStackTrace();
			e.printStackTrace();
		}finally{

			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><ADD_CRS_DETAILS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to fetch CRS Details </td></Output>";
			}			
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
				Status="Success";
			else
				Status="Failure";
			loadJSTDtls(sHandler,"JTS");			


			//sCabinet=xmlDataParser.getValueOf("EngineName");
			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			try
			{
				dbpass=AESEncryption.decrypt(dbpass);
				DBConnection con=new DBConnection();
				String Query="insert into usr_0_other_calls_web_log(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+sWiname+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
				"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
				"?,sysdate,'"+  Status + "')";
				LogGEN.writeTrace("Log"," Add  Query : finally :"+Query);
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,xmlInput.replaceAll("'", "''"),sOrgRes.replaceAll("'", "''"));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return sOutput;

	}

	private static void loadWSDLDtls(WebServiceHandler sHandler){
		try {
			sHandler.readCabProperty("ADD_CRS_DETAILS");
			sWSDLPath=(String)currentCabPropertyMap.get("WSDL_PATH");
			sCabinet=(String)currentCabPropertyMap.get("CABINET");
			sUser=(String)currentCabPropertyMap.get("USER");
			sLoginReq=Boolean.valueOf((String)currentCabPropertyMap.get("LOGIN_REQ"));
			sPassword=(String)currentCabPropertyMap.get("PASSWORD");
			lTimeOut=Long.valueOf((String)currentCabPropertyMap.get("TIMEOUT_INTERVAL"));
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			LogGEN.writeTrace("AO_CRS_Log",sw.toString());
			e.printStackTrace();			
		}
	}
	 
	//CH_25092018_001 START Harinder
	private String uploadDocToEDMS(String wi_name,String custid) throws Exception
	{
		String docAddOutput="";
		try{
			LogGEN.writeTrace("Log","Upload to edms start");
			sHandler.readCabProperty("JTS");
			String wmsIP=(String)currentCabPropertyMap.get("IP");
			String wmsPort=(String)currentCabPropertyMap.get("PORT");
			// UPDATED BY ABHAY AS A CONSEQUENCE OF WEBSERVICE CALLS NOW BEING EXECUTED THROUGH SOCKET 04_01_2021
			String dburl=(String)currentCabPropertyMap.get("DBURL");
			String dbuser=(String)currentCabPropertyMap.get("USER");
			String dbpass=(String)currentCabPropertyMap.get("PASS");
			LogGEN.writeTrace("Log","WMS properties values: "+wmsIP+wmsPort);
			LogGEN.writeTrace("Log","WMS properties values: "+dburl+dbuser+dbpass);
			
			sHandler.readCabProperty("EDMS");
			LogGEN.writeTrace("Log","Upload to edms start 2");
			String edmsIP=(String)currentCabPropertyMap.get("IP");
			LogGEN.writeTrace("Log","Upload to edms start 3");
			String edmsPort=(String)currentCabPropertyMap.get("PORT");
			LogGEN.writeTrace("Log","Upload to edms start 4");
			String edmsCabinet=(String)currentCabPropertyMap.get("CABINET");
			LogGEN.writeTrace("Log","Upload to edms start 5");
			String edmsUsername=(String)currentCabPropertyMap.get("USERNAME");
			LogGEN.writeTrace("Log","Upload to edms start 6");
			LogGEN.writeTrace("Log","Password fetched encrypted: "+(String)currentCabPropertyMap.get("PASSWORD"));
			String edmsPassword=AESEncryption.decrypt((String)currentCabPropertyMap.get("PASSWORD"));
			
			// UPDATED BY ABHAY AS A CONSEQUENCE OF WEBSERVICE CALLS NOW BEING EXECUTED THROUGH SOCKET 04_01_2021
			String edmsdburl=(String)currentCabPropertyMap.get("DBURL");
			String edmsdbuser=(String)currentCabPropertyMap.get("USER");
			String edmsdbpass=(String)currentCabPropertyMap.get("PASS");
			LogGEN.writeTrace("Log","EDMS properties values: "+edmsdburl+" "+edmsdbuser+" "+edmsdbpass);
			
			// COMMENTED DUE TO SECURITY ISSUE
//			LogGEN.writeTrace("Log","Password fetched after decryption: "+edmsPassword);
			LogGEN.writeTrace("Log","Upload to edms start 7");
			String edmsSessionID="";
			LogGEN.writeTrace("Log","edms properties values: "+edmsIP+edmsCabinet+edmsPort+edmsUsername);
			String ownerIndex="";
			
			// UPDATED BY ABHAY AS A CONSEQUENCE OF WEBSERVICE CALLS NOW BEING EXECUTED THROUGH SOCKET 04_01_2021
			 try
			 {
				 dbpass=AESEncryption.decrypt(dbpass);
			 }
			 catch(Exception e)
			 {
				 LogGEN.writeTrace("Log","Error while decrypting password");
			 }
			 try
			 {
				 edmsdbpass=AESEncryption.decrypt(edmsdbpass);
			 }
			 catch(Exception e)
			 {
				 LogGEN.writeTrace("Log","Error while decrypting edms password");
			 }
			
			String sQuery="";
			
			if(wi_name.substring(0,3).equalsIgnoreCase("COB"))     //added by harinder on 22102018
			{
				sQuery="SELECT A.IMAGEINDEX IMAGEINDEX, A.DOCUMENTINDEX DOCUMENTINDEX ,A.NOOFPAGES NOOFPAGES,A.NAME NAME, A.DOCUMENTSIZE DOCUMENTSIZE, A.DOCUMENTTYPE DOCUMENTTYPE, A.APPNAME APPNAME, A.VOLUMEID VOLUMEID,''CUST_ID FROM PDBDOCUMENT a,pdbfolder b,pdbdocumentcontent c WHERE UPPER(A.NAME) ='CRS_FORM' AND A.DOCUMENTINDEX=c.documentindex and c.parentfolderindex=b.folderindex AND b.name ='"+wi_name+"'";
			}else
			{
				sQuery="SELECT A.IMAGEINDEX IMAGEINDEX, A.DOCUMENTINDEX DOCUMENTINDEX ,A.NOOFPAGES NOOFPAGES,A.NAME NAME," +
						" A.DOCUMENTSIZE DOCUMENTSIZE, A.DOCUMENTTYPE DOCUMENTTYPE, A.APPNAME APPNAME, A.VOLUMEID VOLUMEID," +
						"b.CID CUST_ID FROM USR_0_DOC_DETAILS DOC,PDBDOCUMENT A,ACC_RELATION_REPEATER b WHERE UPPER(A.NAME) ='CRS_FORM' " +
						"AND A.DOCUMENTINDEX= DOC.DOC_INDEX AND DOC.WI_NAME ='"+wi_name+"' AND DOC.comments=b.sno and b.CID='"+custid+"' and DOC.WI_NAME=b.WI_NAME";
			}
			
			LogGEN.writeTrace("Log","Document Detail for CRS Query"+sQuery);
			String sOutput="";
			DBConnection conn = new DBConnection();
			
			try {
				// UPDATED BY ABHAY AS A CONSEQUENCE OF WEBSERVICE CALLS NOW BEING EXECUTED THROUGH SOCKET 04_01_2021
				sOutput = conn.executeSelectXML("jdbc:oracle:thin:@" + dburl, dbuser, dbpass, sQuery);
				//sOutput = DBConnection.executeSelectToWMS(sCabinet,sQuery);//(Prepare_APSelectXml(sQuery, sCabinet, sessionID), wmsIP,Integer.parseInt(wmsPort));--
			} catch (Exception e) {
				printCusomExcdeption(e,"Document Detail from edms");
			}
			LogGEN.writeTrace("Log","Document Detail for CRS sOutput"+sOutput);			//making connection with edms cabinet and getting session id 				

			xmlDataParser.setInputXML(sOutput);
			if(!"0".equalsIgnoreCase(xmlDataParser.getValueOf("TotalRetrieved"))){
				String IMAGEINDEX=xmlDataParser.getValueOf("IMAGEINDEX");
				String DOCUMENTINDEX=xmlDataParser.getValueOf("DOCUMENTINDEX");
				String CUST_ID=xmlDataParser.getValueOf("CUST_ID");
				String NOOFPAGES=xmlDataParser.getValueOf("NOOFPAGES");
				String NAME=xmlDataParser.getValueOf("NAME");
				String DOCUMENTSIZE=xmlDataParser.getValueOf("DOCUMENTSIZE");
				String DOCUMENTTYPE=xmlDataParser.getValueOf("DOCUMENTTYPE");
				String APPNAME=xmlDataParser.getValueOf("APPNAME");
				String VOLUMEID=xmlDataParser.getValueOf("VOLUMEID");
				LogGEN.writeTrace("Log","Document details of  "+IMAGEINDEX+DOCUMENTINDEX+CUST_ID+NOOFPAGES+NAME+DOCUMENTSIZE+DOCUMENTTYPE+APPNAME+VOLUMEID);
				sOutput=executeSocket(getConnect(edmsCabinet,edmsUsername,edmsPassword),edmsIP,Integer.parseInt(edmsPort),0);
				LogGEN.writeTrace("Log","EDMS Connection output xml: "+sOutput);
				xmlDataParser.setInputXML(sOutput);
				edmsSessionID=emptyCheck(xmlDataParser.getValueOf("UserDBId"));
				ownerIndex=emptyCheck(xmlDataParser.getValueOf("LoginUserIndex"));
				
				// making folder heriarchy
				sQuery="SELECT FOLDERINDEX FROM PDBFOLDER WHERE PARENTFOLDERINDEX='0' AND UPPER(NAME)='CUSTOMER LIST'";
				LogGEN.writeTrace("Log","EDMS Query 1: "+sQuery);
				// UPDATED BY ABHAY AS A CONSEQUENCE OF WEBSERVICE CALLS NOW BEING EXECUTED THROUGH SOCKET 04_01_2021
				sOutput =  conn.executeSelectXML("jdbc:oracle:thin:@" + edmsdburl, edmsdbuser, edmsdbpass, sQuery);
//				sOutput=DBConnection.executeSelectToWMS(edmsCabinet,sQuery);//executeAPI(Prepare_APSelectXml("SELECT FOLDERINDEX FROM PDBFOLDER WHERE PARENTFOLDERINDEX='0' AND NAME='CUSTOMER LIST'",edmsCabinet, edmsSessionID),edmsIP,Integer.parseInt(edmsPort));
				LogGEN.writeTrace("Log","CustomerlistFolderIndex output xml: "+sOutput);				
				xmlDataParser.setInputXML(sOutput);
				if(!"0".equalsIgnoreCase(xmlDataParser.getValueOf("TotalRetrieved"))){
					String CustomerlistFolderIndex=xmlDataParser.getValueOf("FOLDERINDEX");
					sQuery="SELECT FOLDERINDEX FROM PDBFOLDER WHERE PARENTFOLDERINDEX='"+CustomerlistFolderIndex+"' AND NAME='"+CUST_ID+"'";
					LogGEN.writeTrace("Log","EDMS Query 2: "+sQuery);
					// UPDATED BY ABHAY AS A CONSEQUENCE OF WEBSERVICE CALLS NOW BEING EXECUTED THROUGH SOCKET 04_01_2021
					sOutput =  conn.executeSelectXML("jdbc:oracle:thin:@" + edmsdburl, edmsdbuser, edmsdbpass, sQuery);
//					sOutput=DBConnection.executeSelectToWMS(edmsCabinet,sQuery);//executeAPI(Prepare_APSelectXml("select folderindex from pdbfolder where parentfolderindex='"+CustomerlistFolderIndex+"' and name='"+CUST_ID+"'",edmsCabinet, edmsSessionID),edmsIP,Integer.parseInt(edmsPort));
					LogGEN.writeTrace("Log","CustIdFolderIndex output xml: "+sOutput);
					xmlDataParser.setInputXML(sOutput);
					String CustIdFolderIndex="";
					if(!"0".equalsIgnoreCase(xmlDataParser.getValueOf("TotalRetrieved"))){
						CustIdFolderIndex=xmlDataParser.getValueOf("FOLDERINDEX");
					}
					if("".equalsIgnoreCase(CustIdFolderIndex)|| null==CustIdFolderIndex)
					{
						sOutput=executeSocket(getAddFolderInputXml(edmsCabinet,CUST_ID, edmsSessionID,CustomerlistFolderIndex),edmsIP,Integer.parseInt(edmsPort),0);//executeAPI(getAddFolderInputXml(edmsCabinet,CUST_ID, edmsSessionID,CustomerlistFolderIndex),edmsIP,Integer.parseInt(edmsPort));
						LogGEN.writeTrace("Log","Add custid folder output xml: "+sOutput);
						xmlDataParser.setInputXML(sOutput);
						if("0".equalsIgnoreCase(xmlDataParser.getValueOf("Status"))){
							CustIdFolderIndex=xmlDataParser.getValueOf("FolderIndex");
						}
					}
					sQuery="SELECT FOLDERINDEX FROM PDBFOLDER WHERE PARENTFOLDERINDEX='"+CustIdFolderIndex+"' AND UPPER(NAME)='"+"Bank Wide Documents".toUpperCase()+"'";
					LogGEN.writeTrace("Log","EDMS Query 3: "+sQuery);
					// UPDATED BY ABHAY AS A CONSEQUENCE OF WEBSERVICE CALLS NOW BEING EXECUTED THROUGH SOCKET 04_01_2021
					sOutput =  conn.executeSelectXML("jdbc:oracle:thin:@" + edmsdburl, edmsdbuser, edmsdbpass, sQuery);
//					sOutput=DBConnection.executeSelectToWMS(edmsCabinet,sQuery);//executeAPI(Prepare_APSelectXml("SELECT FOLDERINDEX FROM PDBFOLDER WHERE PARENTFOLDERINDEX='"+CustIdFolderIndex+"' AND NAME='"+"Bank Wide Documents"+"'",edmsCabinet, edmsSessionID),edmsIP,Integer.parseInt(edmsPort));
					LogGEN.writeTrace("Log","BankWideDocsFolderIndex output xml: "+sOutput);
					xmlDataParser.setInputXML(sOutput);
					String BankWideDocsFolderIndex="";
					if(!"0".equalsIgnoreCase(xmlDataParser.getValueOf("TotalRetrieved"))){
						BankWideDocsFolderIndex=xmlDataParser.getValueOf("FOLDERINDEX");
					}
					if("".equalsIgnoreCase(BankWideDocsFolderIndex)|| null==BankWideDocsFolderIndex)
					{
						sOutput=executeSocket(getAddFolderInputXml(edmsCabinet,"Bank Wide Documents", edmsSessionID,CustIdFolderIndex),edmsIP,Integer.parseInt(edmsPort),0);
						LogGEN.writeTrace("Log","Add Bank Wide Documents folder output xml: "+sOutput);
						xmlDataParser.setInputXML(sOutput);
						if("0".equalsIgnoreCase(xmlDataParser.getValueOf("Status"))){
							BankWideDocsFolderIndex=xmlDataParser.getValueOf("FolderIndex");
						}
					}
					String finalFolderIndex=BankWideDocsFolderIndex;    //final folder index where we will add our documnet
					LogGEN.writeTrace("Log","finalFolderIndex: "+finalFolderIndex);
					if(!finalFolderIndex.isEmpty()){
						String addDocInputXml=getAddDocumentInput(edmsCabinet,edmsSessionID,finalFolderIndex, NOOFPAGES, NAME, DOCUMENTTYPE, DOCUMENTSIZE, APPNAME, ""+IMAGEINDEX+"#"+VOLUMEID+"#","CRS Document added by Webservice",ownerIndex);
						LogGEN.writeTrace("Log","Add Document input xml: "+addDocInputXml);
						docAddOutput=executeSocket(addDocInputXml,edmsIP,Integer.parseInt(edmsPort),0);
						//LogGEN.writeTrace("Log","Add Document Output xml: "+docAddOutput);
					}				
				}				
			}			
		}catch(Exception e)
		{
			e.printStackTrace();
			LogGEN.writeTrace("Log","exception mssg: "+e.getMessage());
			LogGEN.writeTrace("Log","exception mssg: "+e);
		}
		return docAddOutput;

	}

	private String getConnect(String sCabinetName, String sUserName, String sUserPassword)
	{
		StringBuilder sb= new StringBuilder();
		sb.append("<?xml version=\"1.0\"?>");
		sb.append("<NGOConnectCabinet_Input>");
		sb.append("<Option>NGOConnectCabinet</Option>");
		sb.append("<CabinetName>" + sCabinetName + "</CabinetName>");
		sb.append("<UserName>" + sUserName + "</UserName>");
		sb.append("<UserPassword>" + sUserPassword + "</UserPassword>");
		sb.append("<CurrentDateTime></CurrentDateTime>");
		sb.append("<UserExist>N</UserExist>");
		sb.append("<MainGroupIndex>0</MainGroupIndex>");
		sb.append("<UserType>S</UserType>");
		sb.append("<Locale>en-us</Locale>");
		sb.append("<ApplicationName>N</ApplicationName>");
		sb.append("<Hook>Omnidocs Admin</Hook>");
		sb.append("</NGOConnectCabinet_Input>");       
		return sb.toString();
	}

	public String inputXml(String FolderName, String sessionID,String folderindex)
	{
		StringBuffer strbuffer = new StringBuffer();		
		strbuffer.append("<?xml version=\"1.0\"?>");		
		strbuffer.append("<NGOAddFolder_Input>");
		strbuffer.append("<Option>NGOAddFolder</Option>");
		strbuffer.append("<CabinetName>newcabrbb</CabinetName>");
		strbuffer.append("<UserDBId>" + sessionID + "</UserDBId>");
		strbuffer.append("<Folder>");              
		strbuffer.append("<ParentFolderIndex>" + folderindex + "</ParentFolderIndex>");            
		strbuffer.append("<FolderName>" + FolderName + "</FolderName>");
		strbuffer.append("<CreationDateTime></CreationDateTime>");
		strbuffer.append("<AccessType>S</AccessType>");
		strbuffer.append("<ImageVolumeIndex>16</ImageVolumeIndex>");
		strbuffer.append("<FolderType>G</FolderType>");
		strbuffer.append("<Location>G</Location>");
		strbuffer.append("<Comment></Comment>");
		strbuffer.append("<Owner>xyz</Owner>");
		strbuffer.append("<LogGeneration>Y</LogGeneration>");
		strbuffer.append("<EnableFTSFlag>Y</ EnableFTSFlag>");
		strbuffer.append("<DuplicateName>N</DuplicateName>");
		strbuffer.append("</Folder>");
		strbuffer.append("</NGOAddFolder_Input>");				

		System.out.println("input xml for addition of folder"+strbuffer);

		return strbuffer.toString();	     	
	}

	public String getAddDocumentInput(String sCabinetName,String sUserDBID,String sParentFolderIndex,
			String sNoOfPages,String sDocumentName,String sDocType,String sDocumentSize,
			String sCreatedbyAppName,String sISIndex,String sComments,String ownerIndex)
	{
		return "<?xml version=\"1.0\"?>" +
		"<NGOAddDocument_Input>" +
		"<Option>NGOAddDocument</Option>" +
		"<BypassHookTag></BypassHookTag>" +
		"<CabinetName>"+sCabinetName+"</CabinetName>" +
		"<UserDBId>"+sUserDBID+"</UserDBId>" +
		"<GroupIndex>0</GroupIndex>" +
		"<Document>" +
		"<ParentFolderIndex>"+sParentFolderIndex+"</ParentFolderIndex>" +
		"<NoOfPages>"+sNoOfPages+"</NoOfPages>" +
		"<AccessType>I</AccessType>" +
		"<DocumentName>"+sDocumentName+"</DocumentName>" +
		"<CreationDateTime></CreationDateTime>" +
		"<DocumentType>"+sDocType+"</DocumentType>" +
		"<DocumentSize>"+sDocumentSize+"</DocumentSize>" +
		"<CreatedByAppName>"+sCreatedbyAppName+"</CreatedByAppName>" +
		"<ISIndex>"+sISIndex+"</ISIndex>" +
		"<ODMADocumentIndex></ODMADocumentIndex>" +
		"<Comment>"+sComments+"</Comment>" +
		"<EnableLog>Y</EnableLog>" +
		"<FTSFlag>PP</FTSFlag>" +
		"<Keywords></Keywords>" +
		"<OwnerIndex>"+ownerIndex+"</OwnerIndex>"+
		"</Document>" +
		"</NGOAddDocument_Input>";

	}

	public String getAddFolderInputXml(String cabinet,String FolderName, String sessionID,String parentfolderindex)
	{
		return "<?xml version=\"1.0\"?><NGOAddFolder_Input><Comment>ByP@$$M@kerChecker</Comment>" +
		"<Option>NGOAddFolder</Option><CabinetName>"+cabinet+"</CabinetName>" +
		"<UserDBId>"+ sessionID +"</UserDBId><Folder>" +
		"<ParentFolderIndex>"+ parentfolderindex +"</ParentFolderIndex>" +
		"<FolderName>"+FolderName+"</FolderName>" +
		"<CreationDateTime></CreationDateTime>" +
		"<AccessType>I</AccessType><ImageVolumeIndex></ImageVolumeIndex>" +
		"<FolderType>G</FolderType><Location>G</Location>" +
		"<Comment>Cretaed through AddCRSDetails Webservice Client</Comment><EnableFTSFlag>Y</EnableFTSFlag>" +
		"<NoOfDocuments>0</NoOfDocuments>" +
		"<NoOfSubFolders>0</NoOfSubFolders>" +
		"<DataDefinition></DataDefinition></Folder>" +
		"</NGOAddFolder_Input>";      
	}

	//CH_25092018_001 END Harinder

	private HeaderType setHeaderDtls(String sDate,String ref_no,String senderid){
		HeaderType headerType= new HeaderType();		
		headerType.setUsecaseID("1234");
		headerType.setServiceName("InqCommonReportingStandards");
		headerType.setVersionNo("1.0");
		headerType.setServiceAction("Inquiry");
		headerType.setSysRefNumber(ref_no);			
		headerType.setSenderID(sHandler.setSenderId(senderid)); 
		headerType.setReqTimeStamp(sDate);
		headerType.setUsername("TP100066");
		headerType.setCredentials("TP100066");
		return headerType;
	}

	private static void loadJSTDtls(WebServiceHandler sHandler,String tagName){
		try {
			sHandler.readCabProperty(tagName);
			dburl=(String)currentCabPropertyMap.get("DBURL");
			dbuser=(String)currentCabPropertyMap.get("USER");
			dbpass=(String)currentCabPropertyMap.get("PASS");
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			LogGEN.writeTrace("Log",sw.toString());
			e.printStackTrace();
		}
	}

	private void printCusomExcdeption(Exception e,String msg){
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		LogGEN.writeTrace("Log",sw.toString());

	}
	private String executeSocket(String inputXMLString, String jtsIP, int jtsPort, int debug) throws IOException, Exception 
	{
		String recieveBuffer = new String(new byte[0], "8859_1");
		Socket sock = null;
		try {
			sock = new Socket();
			sock.connect(new InetSocketAddress( jtsIP, jtsPort));			
			recieveBuffer = readWriteObject(inputXMLString, sock);
		}catch (Exception e){
			printCusomExcdeption(e, "executeSocket");
		}finally{
			if(sock!=null){
				sock.close();
			}
		}
		return recieveBuffer;
	}
	private String readWriteObject(String inputXMLString, Socket sock) throws IOException
	{
		String stroutputXML="";
		try {
			DataOutputStream oOut = new DataOutputStream(new BufferedOutputStream(sock.getOutputStream()));
			DataInputStream oIn = new DataInputStream(new BufferedInputStream(sock.getInputStream()));
			byte[] SendStream = inputXMLString.getBytes("8859_1");
			int strLen = SendStream.length;
			oOut.writeInt(strLen);
			oOut.write(SendStream, 0, strLen);
			oOut.flush();
			int length = 0;
			length = oIn.readInt();
			byte[] readStream = new byte[length];
			oIn.readFully(readStream);
			stroutputXML = new String(readStream, "8859_1");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return stroutputXML;
	}

	private String emptyCheck(String str){
		if(str!=null && !str.isEmpty()){
			return str.trim();
		}
		return str.trim();

	}
	
	private DocumentDetails_type0 setDocCrs(String sWiname,String sCustomerID,String certDt){		
		DocumentDetails_type0 docDtlsType0= null;
		try {
			String addDocRespose=uploadDocToEDMS(sWiname,sCustomerID);
			LogGEN.writeTrace("Log","Add Document Response "+addDocRespose);
			if(!addDocRespose.isEmpty()){
				XMLParser xml= new XMLParser(addDocRespose);
				if("0".equalsIgnoreCase(xml.getValueOf("Status"))){
					docDtlsType0=new  DocumentDetails_type0();
					Documents_type0 docType= new Documents_type0();
					docType.setDocCRSRefNo(sWiname);
					docType.setDocIndex(xml.getValueOf("DocumentIndex"));
					docType.setDocName("CRS_From");
					docType.setDocRefNo(xml.getValueOf("DocumentIndex"));
					docType.setDocCRSCertDate(certDt);
					docDtlsType0.addDocuments(docType);
					//requestMsg.setDocumentDetails(docDtlsType0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return docDtlsType0;
	}
	
	private String setCRSChannelId(String winame){
		if("COB".equalsIgnoreCase(winame.split("-")[0])){
			return "COB-WMS";
		}else if("AO".equalsIgnoreCase(winame.split("-")[0])){
			return "AO-WMS";
		}else if("OffshoreAcc".equalsIgnoreCase(winame.split("-")[0])){
			return "OAO-WMS";
		}
		return "WMS";
	}
	
	private String setTinNumber(String tin){
		if(tin.isEmpty()){
			//return "N/A";
			return "";
		}
		return tin;
		
	}
}
