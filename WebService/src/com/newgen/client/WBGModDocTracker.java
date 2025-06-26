package com.newgen.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.doctrc.stub.ModWBGCustomerOnboardingStub;
import com.newgen.doctrc.stub.ModWBGCustomerOnboardingStub.HeaderType;
import com.newgen.doctrc.stub.ModWBGCustomerOnboardingStub.ModDocumentTrackerDtlsReqMsg;
import com.newgen.doctrc.stub.ModWBGCustomerOnboardingStub.ModDocumentTrackerDtlsReq_type0;
import com.newgen.doctrc.stub.ModWBGCustomerOnboardingStub.ModDocumentTrackerDtlsResMsg;

public class WBGModDocTracker extends WebServiceHandler{

	static  String sWSDLPath="";

	static  String dbuser="";
	static  String edmsDB="";
	static  String dbpass="";
	static  String dburl="";

	DBConnection con;
	WebServiceHandler handler;

	public String modDocTracker(String sInputXML){
		handler= new WebServiceHandler();
		con=new DBConnection();
		String sReturnCode="";
		String sErrorDesc="";
		String sErrorDetail="";
		String customRes="";
		XMLParser xmlDataParser=null;
		XMLParser inqXml=null;
		String status="Failure";
		String xmlInput="";		
		String addDocRes="";
		String sDate="";
		try {


			LogGEN.writeTrace("ModDocTrc_Log",sInputXML);
			xmlDataParser= new XMLParser(sInputXML);	
			String oldDocIndex=xmlDataParser.getValueOf("oldDocIndex");			
			String senderId=xmlDataParser.getValueOf("SENDERID");
			String orgLeadRefNo=xmlDataParser.getValueOf("leadRefNo");
			String userName=xmlDataParser.getValueOf("uploadedBy");
			LogGEN.writeTrace("ModDocTrc_Log","oldDocIndex  >>  "+oldDocIndex + " << senderId >>> "+senderId + " <<< orgLeadRefNo >> " +orgLeadRefNo + " <<< userName >>> "+userName);			
			String inqRes=new WBGDocInquiry().inqDocTracker(xmlDataParser.getValueOf("WI_NAME"),senderId,orgLeadRefNo,oldDocIndex,userName);
			inqXml= new XMLParser(inqRes);	
			if("0".equalsIgnoreCase(inqXml.getValueOf("returnCode"))){					
				String newLeadRefNo=inqXml.getValueOf("NewleadRefNo");
				LogGEN.writeTrace("ModDocTrc_Log"," <<<< newLeadRefNo  >>>  " +newLeadRefNo);
				if(newLeadRefNo!=null && !newLeadRefNo.isEmpty()){
					sDate= getCurDate();
					loadWSDLDtls(handler,"MOD_DOC_TRACKER");
					loadJSTDtls(handler,"JTS");
					String sendId=senderId;			
					ModWBGCustomerOnboardingStub stub = new ModWBGCustomerOnboardingStub(sWSDLPath);
					ModDocumentTrackerDtlsReqMsg reqMsg= new ModDocumentTrackerDtlsReqMsg();
					reqMsg.setHeader(setHeaderDtls(sDate,setSenderId(sendId)));
					ModDocumentTrackerDtlsReq_type0  reqType=new ModDocumentTrackerDtlsReq_type0();
					reqType.setCustomerId(xmlDataParser.getValueOf("customerId"));
					reqType.setCustomerName(xmlDataParser.getValueOf("customerName"));
					reqType.setDocumentFileType(xmlDataParser.getValueOf("documentFileType"));
					reqType.setDocumentIndex(xmlDataParser.getValueOf("documentIndex"));
					reqType.setDocumentName(xmlDataParser.getValueOf("documentName"));
					reqType.setDocumentSource(xmlDataParser.getValueOf("documentSource"));
					reqType.setDocumentType(xmlDataParser.getValueOf("documentType"));
					reqType.setEntityType(xmlDataParser.getValueOf("entityType"));
					reqType.setLeadRefNo(newLeadRefNo);
					reqType.setMode(xmlDataParser.getValueOf("mode"));
					reqType.setParentRef(xmlDataParser.getValueOf("parentRef"));
					reqType.setRemarks(xmlDataParser.getValueOf("remarks"));
					reqType.setStatus(xmlDataParser.getValueOf("status"));
					reqType.setUploadChannel(xmlDataParser.getValueOf("uploadChannel"));
					reqType.setUploadedBy(xmlDataParser.getValueOf("uploadedBy"));
					reqType.setUploadedDate(sDate);			
					reqMsg.setModDocumentTrackerDtlsReq(reqType);
					xmlInput=stub.getInputModXml(reqMsg);
					ModDocumentTrackerDtlsResMsg resMsg =stub.modDocumentTrackerDtls_Oper(reqMsg);			
					addDocRes=stub.orgModResDoc;
					LogGEN.writeTrace("ModDocTrc_Log","SOAP Request "+xmlInput +"\n <<<<<<  Soap Response >>>"+addDocRes);
					sReturnCode=resMsg.getHeader().getReturnCode();
					sErrorDetail=resMsg.getHeader().getErrorDetail();
					sErrorDesc= resMsg.getHeader().getErrorDescription();
					if("0".equalsIgnoreCase(sReturnCode)){
						customRes=getSuccessMsg(xmlDataParser.getValueOf("Calltype"),sErrorDesc,sErrorDetail,sReturnCode,
								resMsg.getModDocumentTrackerDtlsRes().getLeadRefNo(),xmlDataParser.getValueOf("documentIndex"));
						status="Success";
					}else{
						customRes=getErrorMsg(xmlDataParser.getValueOf("Calltype"),sErrorDesc,sErrorDetail,sReturnCode);
					}
				}else{
					customRes=getErrorMsg(xmlDataParser.getValueOf("Calltype"),"","","-1");	
				}
			}
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			LogGEN.writeTrace("ModDocTrc_Log",sw.toString());
			sReturnCode="-1";
			sErrorDetail=e.getMessage();
			customRes=getErrorMsg(xmlDataParser.getValueOf("Calltype"),sErrorDesc,sErrorDetail,sReturnCode);			
			e.printStackTrace();
		}finally{
			LogGEN.writeTrace("AddDocTrc_Log","outputXML.trim().length() :"+customRes.trim().length());				
			if(customRes.trim().length()<1)
			{
				customRes=getErrorMsg(xmlDataParser.getValueOf("Calltype"),"","","-1");	
			}
			executeClobDocTrc(handler, 
					xmlDataParser.getValueOf("WI_NAME"), 
					xmlDataParser.getValueOf("Calltype"),
					xmlDataParser.getValueOf("USERNAME"), 
					status, sDate, xmlInput, addDocRes);
		}
		return customRes;
	}


	private void executeClobDocTrc(WebServiceHandler webHandler,String winame, String call_type,String userName,String Status,String sDate,String xmlInput,String sOrgRes){
		try {

			String inputXml=(xmlInput.replaceAll("&lt;", "<")).replaceAll("&gt;", ">");
			String Query="insert into usr_0_other_calls_web_log(winame,userid,request_type,request,requestdatetime,response," +
			"responsedatetime,status) values('"+winame+"','"+ userName +"','"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
			"?,sysdate,'"+  Status + "')";
			LogGEN.writeTrace("ModDocTrc_Log"," Add  Query : finally :"+Query);
			con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrgRes.replaceAll("'", "''"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private HeaderType setHeaderDtls(String sDate,String senderid){
		HeaderType headerType= new HeaderType();
		headerType.setUsecaseID("1234");
		headerType.setServiceName("ModWBGCustomerOnboarding");
		headerType.setVersionNo("1.0");
		headerType.setServiceAction("Modify");
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
		LogGEN.writeTrace("ModDocTrc_Log", "uniqueNo : " + uniqueNo);
		return uniqueNo;
	}
	private static void loadWSDLDtls(WebServiceHandler sHandler,String tagName){
		try {
			sHandler.readCabProperty(tagName);		
			sWSDLPath = (String)currentCabPropertyMap.get("WSDL_PATH");			
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

	private String getErrorMsg(String calName,String errDesc, String errDtl, String retCode){		
		StringBuffer sb= new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<Output><Option>"+calName+"</Option>"); 		
		sb.append("<returnCode>");
		sb.append(retCode);
		sb.append("</returnCode>"); 
		sb.append("<errorDescription>");
		sb.append(errDesc);
		sb.append("</errorDescription>"); 
		sb.append("<errorDetail>");
		sb.append(errDtl);
		sb.append("</errorDetail>"); 
		sb.append("<td>Unable to Modify Document into Document Tracker</td></Output>");
		return sb.toString();
	}
	private String getSuccessMsg(String calName,String errDesc, String errDtl, String retCode,String leadRefNo,String documentIndex){
		StringBuffer sb= new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<Output><Option>"+calName+"</Option>");
		sb.append("<leadRefNo>"+leadRefNo+"</leadRefNo>");
		sb.append("<documentIndex>"+documentIndex+"</documentIndex>");
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
