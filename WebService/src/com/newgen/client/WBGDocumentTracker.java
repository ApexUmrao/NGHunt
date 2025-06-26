package com.newgen.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.doctrc.stub.InqWBGCustomerOnboardingStub;
import com.newgen.doctrc.stub.InqWBGCustomerOnboardingStub.DocumentDtls_type0;
import com.newgen.doctrc.stub.InqWBGCustomerOnboardingStub.Documents_type0;
import com.newgen.doctrc.stub.InqWBGCustomerOnboardingStub.FetchDocumentTrackerDtlsReqMsg;
import com.newgen.doctrc.stub.InqWBGCustomerOnboardingStub.FetchDocumentTrackerDtlsReq_type0;
import com.newgen.doctrc.stub.InqWBGCustomerOnboardingStub.FetchDocumentTrackerDtlsResMsg;
import com.newgen.doctrc.stub.InqWBGCustomerOnboardingStub.FetchDocumentTrackerDtlsRes_type0;
import com.newgen.doctrc.stub.InqWBGCustomerOnboardingStub.HeaderType;



public class WBGDocumentTracker extends WebServiceHandler{
	static  String sWSDLPath="";

	static  String dbuser="";
	static  String edmsDB="";
	static  String dbpass="";
	static  String dburl="";	
	private String docList="";
	private String sReturnCode="";
	private String sErrorDesc="";
	private String sErrorDetail="";	
	private String call_type="";
	private String xmlInput="";
	private String sOrgRes ="";
	private String winame="";
	private String sDate="";
	private String sysRefNo="";
	private String userName="";
	private String docIndex="";
	DBConnection con;
	WebServiceHandler handler;
	
	public String fetchDocument(String sInputXML){
		handler= new WebServiceHandler();
		con=new DBConnection();
		try {
			LogGEN.writeTrace("WBG_Log",sInputXML);
			XMLParser xmlDataParser = new XMLParser();
			xmlDataParser.setInputXML(sInputXML);
			
			String sLeadRefNo=xmlDataParser.getValueOf("LEAD_REFNO");
			winame=sLeadRefNo;			
			userName=xmlDataParser.getValueOf("USERNAME");
			//sessionID=xmlDataParser.getValueOf("SESSIONID");
			call_type=xmlDataParser.getValueOf("CALL_NAME");
			sysRefNo=xmlDataParser.getValueOf("SYSTEMREFNO");
			Date d= new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			sDate= dateFormat.format(d);
			loadWSDLDtls(handler,"DOCUMENT_TRACKER");
			loadJSTDtls(handler,"JTS");
			InqWBGCustomerOnboardingStub stub = new InqWBGCustomerOnboardingStub(sWSDLPath);
			FetchDocumentTrackerDtlsReqMsg dctDtlsReqMsg= new FetchDocumentTrackerDtlsReqMsg();
			dctDtlsReqMsg.setHeader(setHeaderDtls(sDate,xmlDataParser.getValueOf("SENDERID")));
			FetchDocumentTrackerDtlsReq_type0 doctType= new FetchDocumentTrackerDtlsReq_type0();
			doctType.setLeadRefNo(sLeadRefNo);
			dctDtlsReqMsg.setFetchDocumentTrackerDtlsReq(doctType);		
			xmlInput=stub.getInputXML(dctDtlsReqMsg);
			FetchDocumentTrackerDtlsResMsg resMsg= stub.fetchDocumentTrackerDtls_Oper(dctDtlsReqMsg);
			sOrgRes=stub.orgResDoc;
			sReturnCode=resMsg.getHeader().getReturnCode();
			sErrorDesc=resMsg.getHeader().getErrorDescription();
			sErrorDetail=resMsg.getHeader().getErrorDetail();
			String imgDX="";
			if(resMsg!=null){
				FetchDocumentTrackerDtlsRes_type0 docResType= resMsg.getFetchDocumentTrackerDtlsRes();
				if(docResType!=null){
					DocumentDtls_type0 resDocType=docResType.getDocumentDtls();
					if(resDocType!=null){
						Documents_type0 [] docArr = resDocType.getDocuments();
						docIndex=getDocIndex(docArr);
						if(!docIndex.isEmpty()){
							
							// UPDATED BY ABHAY AS A CONSEQUENCE OF WEBSERVICE CALLS NOW BEING EXECUTED THROUGH SOCKET 06_01_2021
							String sQuery="SELECT DOCUMENTINDEX ,IMAGEINDEX, VOLUMEID,NOOFPAGES, DOCUMENTSIZE,APPNAME FROM PDBDOCUMENT WHERE DOCUMENTINDEX IN ("+docIndex+")";

							handler.readCabProperty("EDMS");
							String edmsdburl=(String)currentCabPropertyMap.get("DBURL");
							String edmsdbuser=(String)currentCabPropertyMap.get("USER");
							String edmsdbpass=(String)currentCabPropertyMap.get("PASS");
							LogGEN.writeTrace("Log","EDMS properties values: "+edmsdburl+" "+edmsdbuser+" "+edmsdbpass);
							
							 try
							 {
								 edmsdbpass=AESEncryption.decrypt(edmsdbpass);
							 }
							 catch(Exception e)
							 {
								 LogGEN.writeTrace("Log","Error while decrypting edms password");
							 }
							 
							imgDX =  con.executeSelectXML("jdbc:oracle:thin:@" + edmsdburl, edmsdbuser, edmsdbpass, sQuery);
//							imgDX=con.executeOnEDMS(edmsDB,docIndex);
							
							LogGEN.writeTrace("WBG_Log","imgDX EDMS XML>>>>"+imgDX);
							docList=documentTrackerXML(docArr, sReturnCode, sErrorDesc, sErrorDetail,imgDX);
							LogGEN.writeTrace("WBG_Log","Document XML>>>>"+docList);
							con.executeBatch("jdbc:oracle:thin:@"+dburl, dbuser, dbpass, docList, sysRefNo, sLeadRefNo);
						}else {
						
							docList="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output>"+
							"<Option>Fetch_Doc_Tracker</Option>"+
							"<returnCode>"+sReturnCode+"</returnCode>" +		
							"<errorDesc>"+sErrorDesc+"</errorDesc>" +
							"<errorDescription>"+sErrorDetail+"</errorDescription>" +
							"<DocumentsDtls>"+			
							"<Records>0</Records>"+
							"</DocumentsDtls>"+
							"</Output>";
						}
												
					}
				}
			}
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			LogGEN.writeTrace("WBG_Log",sw.toString());
			sReturnCode="-1";
			sErrorDetail=e.getMessage();
			docList="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>Fetch_Doc_Tracker</Option>" +
			"<returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>" +
			"<td>Unable to Fetch Document from Document Tracker</td></Output>";
			e.printStackTrace();
		}finally{
			LogGEN.writeTrace("Log","outputXML.trim().length() :"+docList.trim().length());				
			if(docList.trim().length()<1)
			{
				docList="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>Fetch_Doc_Tracker</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to Save FATCA Details.</td></Output>";
			}
			String Status="";
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
				Status="Success";
			else
				Status="Failure";
			executeClobDocTrc(handler, winame, call_type, Status, sDate, xmlInput, sOrgRes);
		}
		return docList;		
	}

	private void executeClobDocTrc(WebServiceHandler webHandler,String winame, String call_type,String Status,String sDate,String xmlInput,String sOrgRes){
		try {
			
			String inputXml=(xmlInput.replaceAll("&lt;", "<")).replaceAll("&gt;", ">");
			String Query="insert into usr_0_other_calls_web_log(winame,userid,request_type,request,requestdatetime,response," +
			"responsedatetime,status) values('"+winame+"','"+ userName +"','"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
			"?,sysdate,'"+  Status + "')";
			LogGEN.writeTrace("WBG_Log"," Add  Query : finally :"+Query);
			con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrgRes.replaceAll("'", "''"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private String getDocIndex(Documents_type0 []docArr){
		StringBuffer sDocIndx= new StringBuffer();
		for(int counter=0; counter <docArr.length ; counter++){
			if(!("null".equalsIgnoreCase(docArr[counter].getDocumentIndex()) 
					||"".equalsIgnoreCase(docArr[counter].getDocumentIndex())
					|| docArr[counter].getDocumentIndex()==null )){
				if (docArr.length == 1){
					sDocIndx.append("'"+docArr[counter].getDocumentIndex()+"'");
				} else {
				if(counter==0)
					sDocIndx.append("'"+docArr[counter].getDocumentIndex()+"','");
				else if(counter ==docArr.length-1)
					sDocIndx.append(docArr[counter].getDocumentIndex()+"'");
				else 
					sDocIndx.append(docArr[counter].getDocumentIndex()+"','");
				}
			}			
		}
		LogGEN.writeTrace("WBG_Log"," sDocIndx  >>>> :"+sDocIndx.toString());
		return sDocIndx.toString();
	}
	private String documentTrackerXML(Documents_type0 []docArr,String sReturnCode,String sErrorDesc,String sErrorDetail ,String imgDX){
		StringBuilder sBuffer= new StringBuilder();
		
		try {
			int cont=0;
			sBuffer.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><Output>");
			sBuffer.append("<Option>Fetch_Doc_Tracker</Option>");
			sBuffer.append("<returnCode>"+sReturnCode+"</returnCode>" );		
			sBuffer.append("<errorDesc>"+sErrorDesc+"</errorDesc>" );
			sBuffer.append("<errorDescription>"+sErrorDetail+"</errorDescription>" );
			sBuffer.append("<DocumentsDtls>");
			for(int counter=0; counter <docArr.length ; counter++){
				cont=cont+1;
				
				sBuffer.append("<Documents>");
				sBuffer.append("<LeadRefNo>");
				sBuffer.append(docArr[counter].getLeadRefNo());
				sBuffer.append("</LeadRefNo>");
				sBuffer.append("<EntityType>");
				sBuffer.append(docArr[counter].getEntityType());
				sBuffer.append("</EntityType>");
				sBuffer.append("<CustomerId>");
				sBuffer.append(docArr[counter].getCustomerId());
				sBuffer.append("</CustomerId>");
				sBuffer.append("<CustomerName>");
				sBuffer.append(docArr[counter].getCustomerName());
				sBuffer.append("</CustomerName>");				
				sBuffer.append("<DocumentName>");
				sBuffer.append(docArr[counter].getDocumentName());
				sBuffer.append("</DocumentName>");
//				sBuffer.append("<DocExt>");
//				sBuffer.append(docArr[counter].getDocumentFileType());
//				sBuffer.append("</DocExt>");
				sBuffer.append("<DocumentType>");
				sBuffer.append(docArr[counter].getDocumentType());
				sBuffer.append("</DocumentType>");
				sBuffer.append("<DocumentIndex>");
				sBuffer.append(docArr[counter].getDocumentIndex());
				sBuffer.append("</DocumentIndex>");
				sBuffer.append(getImgIndexDtls(docArr[counter].getDocumentIndex(),imgDX));				
				sBuffer.append("<UploadedDate>");
				sBuffer.append(docArr[counter].getUploadedDate());
				sBuffer.append("</UploadedDate>");
				sBuffer.append("<ParentRef>");
				sBuffer.append(docArr[counter].getParentRef());
				sBuffer.append("</ParentRef>");				
				sBuffer.append("</Documents>");
				
			}
			sBuffer.append("<Records>");
			sBuffer.append(cont);
			sBuffer.append("</Records>");
			sBuffer.append("</DocumentsDtls>");
			sBuffer.append("</Output>");
			//docIndex=sDocIndx.toString();			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sBuffer.toString();
	}
	
	
	private String getImgIndexDtls(String docIndx,String xml){
		StringBuilder sb= new StringBuilder();
		WFXmlResponse xmlResponse = new WFXmlResponse(xml);			
		WFXmlList lWfXml = xmlResponse.createList("Records","Record");
		for (int i = 0; lWfXml.hasMoreElements(true); lWfXml.skip(true), i++){
			if(docIndx.equalsIgnoreCase(lWfXml.getVal("DOCUMENTINDEX"))){
				sb.append("<IMAGEINDEX>");
				sb.append(lWfXml.getVal("IMAGEINDEX"));
				sb.append("</IMAGEINDEX>");
				sb.append("<VOLUMEID>");
				sb.append(lWfXml.getVal("VOLUMEID"));
				sb.append("</VOLUMEID>");
				sb.append("<NOOFPAGES>");
				sb.append(lWfXml.getVal("NOOFPAGES"));
				sb.append("</NOOFPAGES>");
				sb.append("<DOCUMENTSIZE>");
				sb.append(lWfXml.getVal("DOCUMENTSIZE"));
				sb.append("</DOCUMENTSIZE>");				
				sb.append("<DocExt>");
				sb.append(lWfXml.getVal("APPNAME"));
				sb.append("</DocExt>");
				break;
			}
		}		
		return sb.toString();
	}
	private HeaderType setHeaderDtls(String sDate,String senderid){
		HeaderType headerType= new HeaderType();
		headerType.setUsecaseID("1234");
		headerType.setServiceName("InqWBGCustomerOnboarding");
		headerType.setVersionNo("1.0");
		headerType.setServiceAction("Inquiry");
		headerType.setCorrelationID("");
		headerType.setSysRefNumber(getUniqueNo());
		headerType.setSenderID(handler.setSenderId(senderid)); 
		headerType.setConsumer("");
		headerType.setReqTimeStamp(sDate);
		headerType.setUsername("WMS"); 
		return headerType;
	}
	private String getUniqueNo()
	{
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHMMS");
		String uniqueNo = sdf.format(date);
		LogGEN.writeTrace("WBG_Log", "uniqueNo : " + uniqueNo);
		return uniqueNo;
	}
	private static void loadWSDLDtls(WebServiceHandler sHandler,String tagName){
		try {
			sHandler.readCabProperty(tagName);		
			sWSDLPath = (String)currentCabPropertyMap.get("WSDL_PATH");
//			sCabinet = (String)currentCabPropertyMap.get("CABINET");
//			sUser = (String)currentCabPropertyMap.get("USER");
//			sLoginReq = Boolean.valueOf((String)currentCabPropertyMap.get("LOGIN_REQ"));
//			sPassword = (String)currentCabPropertyMap.get("PASSWORD");
//			lTimeOut=Long.valueOf((String)currentCabPropertyMap.get("TIMEOUT_INTERVAL"));			
		} catch (Exception e) {
			e.printStackTrace();			
		}
	}
	private static void loadJSTDtls(WebServiceHandler sHandler,String tagName){
		try {
			sHandler.readCabProperty(tagName);
			dburl=(String)currentCabPropertyMap.get("DBURL");
			dbuser=(String)currentCabPropertyMap.get("USER");
			dbpass=(String)currentCabPropertyMap.get("PASS");
			dbpass=AESEncryption.decrypt(dbpass);
			sHandler.readCabProperty("EDMS");
			edmsDB=(String)currentCabPropertyMap.get("CABINET");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


