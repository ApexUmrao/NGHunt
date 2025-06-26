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

public class WBGDocInquiry extends WebServiceHandler{

	static  String sWSDLPath="";
	static  String cabinetName="";
	static  String dbuser="";
	static  String edmsDB="";
	static  String dbpass="";
	static  String dburl="";	
	private String docList="";
	private String xmlInput="";
	private String sOrgRes ="";	
	DBConnection con;
	WebServiceHandler handler;
	
	
	public  String inqDocTracker(String wiName,String senderId,String leadRefNo,String oldDocIndex,String userName){
		handler= new WebServiceHandler();
		con=new DBConnection();
		String sDate="";	
		String status="Failure";
		FetchDocumentTrackerDtlsResMsg resMsg=null;
		try {
			LogGEN.writeTrace("inqDocTracker","  <<< wiName >>> "+wiName +" <<< leadRefNo >>>" +leadRefNo+" <<< senderId >>>" +senderId +" <<< oldDocIndex >>>" +oldDocIndex+" <<< userName >>>" +userName);
			sDate=getCurDate();			
			loadWSDLDtls(handler,"DOCUMENT_TRACKER");
			loadJSTDtls(handler,"JTS");
			InqWBGCustomerOnboardingStub stub = new InqWBGCustomerOnboardingStub(sWSDLPath);
			FetchDocumentTrackerDtlsReqMsg dctDtlsReqMsg= new FetchDocumentTrackerDtlsReqMsg();
			dctDtlsReqMsg.setHeader(setHeaderDtls(sDate,senderId));
			FetchDocumentTrackerDtlsReq_type0 doctType= new FetchDocumentTrackerDtlsReq_type0();
			doctType.setLeadRefNo(leadRefNo);
			dctDtlsReqMsg.setFetchDocumentTrackerDtlsReq(doctType);		
			xmlInput=stub.getInputXML(dctDtlsReqMsg);
			LogGEN.writeTrace("inqDocTracker","  xmlInput "+xmlInput);
			resMsg= stub.fetchDocumentTrackerDtls_Oper(dctDtlsReqMsg);
			sOrgRes=stub.orgResDoc;
			LogGEN.writeTrace("inqDocTracker","  sOrgRes "+sOrgRes);
			if(resMsg!=null){
				FetchDocumentTrackerDtlsRes_type0 docResType= resMsg.getFetchDocumentTrackerDtlsRes();
				if(docResType!=null){
					DocumentDtls_type0 resDocType=docResType.getDocumentDtls();
					if(resDocType!=null){
						if("0".equalsIgnoreCase(resMsg.getHeader().getReturnCode())){
							Documents_type0 [] docArr = resDocType.getDocuments();
							String newLeadRef=documentTrackerXML(
									docArr, resMsg.getHeader().getReturnCode(),
									resMsg.getHeader().getErrorDescription(), 
									resMsg.getHeader().getErrorDetail(),oldDocIndex);
							docList=getSuccessMsg(resMsg.getHeader().getErrorDescription(),
									resMsg.getHeader().getErrorDetail(),resMsg.getHeader().getReturnCode(),newLeadRef);
							status="Success";
						}else{
							docList=getErrorMsg(resMsg.getHeader().getErrorDescription(),
									resMsg.getHeader().getErrorDetail(),resMsg.getHeader().getReturnCode());							
						}											
					}
				}			
			}
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			LogGEN.writeTrace("inqDocTracker",sw.toString());
			docList=getErrorMsg(resMsg.getHeader().getErrorDescription(),"","-1");			
		}finally{
			LogGEN.writeTrace("inqDocTracker","outputXML.trim().length() :"+docList.trim().length());				
			if(docList.trim().length()<1)
			{
				docList=getErrorMsg(resMsg.getHeader().getErrorDescription(),"","-1");	
			}		
			executeClobDocTrc(handler, 
					wiName, 
					"inqDocTracker",
					userName, 
					status, sDate, xmlInput, sOrgRes);
		}
		
		return docList;
	}
	private void executeClobDocTrc(WebServiceHandler webHandler,String winame, String call_type,String userName,String Status,String sDate,String xmlInput,String sOrgRes){
		try {
			
			String inputXml=(xmlInput.replaceAll("&lt;", "<")).replaceAll("&gt;", ">");
			LogGEN.writeTrace("inqDocTracker"," executeClobDocTrc inputXml :  :"+inputXml);
			LogGEN.writeTrace("inqDocTracker"," executeClobDocTrc sOrgRes :  :"+sOrgRes);
			String Query="insert into usr_0_other_calls_web_log(winame,userid,request_type,request,requestdatetime,response," +
			"responsedatetime,status) values('"+winame+"','"+ userName +"','"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
			"?,sysdate,'"+  Status + "')";
			LogGEN.writeTrace("inqDocTracker"," Add  Query : finally :"+Query);
			con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrgRes.replaceAll("'", "''"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String documentTrackerXML(Documents_type0 []docArr,String sReturnCode,String sErrorDesc,String sErrorDetail ,String oldDocIndex){
		String modLeadRefNo="";		
		try {			
			for(int counter=0; counter <docArr.length ; counter++){
				if(oldDocIndex.equalsIgnoreCase(docArr[counter].getDocumentIndex())){
					modLeadRefNo=docArr[counter].getLeadRefNo();
					LogGEN.writeTrace("inqDocTracker"," modLeadRefNo  :"+modLeadRefNo);
					break;
				}		
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modLeadRefNo;
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
		LogGEN.writeTrace("inqDocTracker", "uniqueNo : " + uniqueNo);
		return uniqueNo;
	}
	private static void loadWSDLDtls(WebServiceHandler sHandler,String tagName){
		try {
			sHandler.readCabProperty(tagName);		
			sWSDLPath = (String)currentCabPropertyMap.get("WSDL_PATH");
			cabinetName = (String)currentCabPropertyMap.get("CABINET");	
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

	private String getCurDate(){
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return dateFormat.format(d);
	}
	private String getErrorMsg(String errDesc, String errDtl, String retCode){		
		StringBuffer sb= new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<Output><Option>Fetch_Doc_Tracker</Option>"); 
		sb.append("<returnCode>");
		sb.append(retCode);
		sb.append("</returnCode>"); 
		sb.append("<errorDescription>");
		sb.append(errDesc);
		sb.append("</errorDescription>"); 
		sb.append("<errorDetail>");
		sb.append(errDtl);
		sb.append("</errorDetail>"); 
		sb.append("<td>Unable to Fetch Document from Document Tracker</td></Output>");
		return sb.toString();
	}
	private String getSuccessMsg(String errDesc, String errDtl, String retCode,String leadRefNo){
		StringBuffer sb= new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");		
		sb.append("<NewleadRefNo>"+leadRefNo+"</NewleadRefNo>");
		sb.append("<returnCode>");
		sb.append(retCode);
		sb.append("</returnCode>"); 
		sb.append("<errorDescription>");
		sb.append(errDesc);
		sb.append("</errorDescription>"); 
		sb.append("<errorDetail>");
		sb.append(errDtl);
		sb.append("</errorDetail>"); 
		return sb.toString();
	}
}
