package com.newgen.dscop.client;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.activation.DataHandler;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqCustAcctMiscDtlsStub;
import com.newgen.dscop.stub.InqCustAcctMiscDtlsStub.CBDetails_type0;
import com.newgen.dscop.stub.InqCustAcctMiscDtlsStub.CBRDetails_type0;
import com.newgen.dscop.stub.InqCustAcctMiscDtlsStub.CustomerCBRDetails_type0;
import com.newgen.dscop.stub.InqCustAcctMiscDtlsStub.CustomerChequeBookDetails_type0;
import com.newgen.dscop.stub.InqCustAcctMiscDtlsStub.CustomerRelationDetails_type0;
import com.newgen.dscop.stub.InqCustAcctMiscDtlsStub.CustomerRelations_type0;
import com.newgen.dscop.stub.InqCustAcctMiscDtlsStub.HeaderType;
import com.newgen.dscop.stub.InqCustAcctMiscDtlsStub.InqCustAcctMiscDtlsReqMsg;
import com.newgen.dscop.stub.InqCustAcctMiscDtlsStub.InqCustAcctMiscDtlsReq_type0;
import com.newgen.dscop.stub.InqCustAcctMiscDtlsStub.InqCustAcctMiscDtlsResMsg;
import com.newgen.dscop.stub.InqCustAcctMiscDtlsStub.InqCustAcctMiscDtlsRes_type0;
import com.newgen.dscop.stub.InqCustAcctMiscDtlsStub.MisCodeDetails_type0;
import com.newgen.dscop.stub.InqCustAcctMiscDtlsStub.MisCodes_type0;

public class InqCustAcctMiscDtls extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_put="";

	public String inqCustAcctMiscDtls(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Function called InqCustAcctMiscDtls");
		LogGEN.writeTrace("CBG_Log", "InqCustAcctMiscDtls sInputXML---");
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";
		String sOutput= "";

		DSCOPServiceHandler sHandler= new DSCOPServiceHandler();
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		try
		{
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("InqCustAcctMiscDtls");
			LogGEN.writeTrace("CBG_Log", "InqCustAcctMiscDtls WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "InqCustAcctMiscDtls WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "InqCustAcctMiscDtls CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "InqCustAcctMiscDtls USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "InqCustAcctMiscDtls PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "InqCustAcctMiscDtls LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "InqCustAcctMiscDtls TIME_OUT: "+lTimeOut);

			String ref_no=xmlDataParser.getValueOf("REF_NO");
			String customerID = xmlDataParser.getValueOf("customerId");

			InqCustAcctMiscDtlsStub request_stub=new InqCustAcctMiscDtlsStub(sWSDLPath);
			InqCustAcctMiscDtlsReqMsg inq_cust_dtls = new InqCustAcctMiscDtlsReqMsg();			
			InqCustAcctMiscDtlsReq_type0 inq_cust_type0 = new InqCustAcctMiscDtlsReq_type0();
			InqCustAcctMiscDtlsResMsg inq_cust_response = new InqCustAcctMiscDtlsResMsg();
			InqCustAcctMiscDtlsRes_type0 inq_cust_res_type0 = new InqCustAcctMiscDtlsRes_type0();

			HeaderType Header_Input = new HeaderType();
			Header_Input.setServiceName("InqCustAcctMiscDtls");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);

			inq_cust_type0.setCustomerId(customerID);
			inq_cust_dtls.setInqCustAcctMiscDtlsReq(inq_cust_type0);
			inq_cust_dtls.setHeader(Header_Input);
			inq_cust_response = request_stub.inqCustAcctMiscDtls_Oper(inq_cust_dtls);

			xmlInput= request_stub.getInputXML(inq_cust_dtls);
			Header_Input = inq_cust_response.getHeader();
			inq_cust_res_type0 = inq_cust_response.getInqCustAcctMiscDtlsRes();
			LogGEN.writeTrace("CBG_Log", "InqCustAcctMiscDtls xmlInput xml : "+xmlInput);
			request_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			sOrg_put=request_stub.outputXML;
			LogGEN.writeTrace("CBG_Log", "InqCustAcctMiscDtlsResMsg sOrg_put: "+sOrg_put);

			sReturnCode =  Header_Input.getReturnCode();
			sErrorDetail = Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();
			String custID = inq_cust_res_type0.getCustomerId();
			String customerSignatureFlag = inq_cust_res_type0.getCustomerSignatureFlag();
			String customerSignatureType = inq_cust_res_type0.getCustomerSignatureType();
			String customerSignaturePresent = inq_cust_res_type0.getCustomerSignaturePresent();
			String customerPhotoPresent = inq_cust_res_type0.getCustomerPhotoPresent();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			String customerSignature="";
			try{
				DataHandler dh = inq_cust_res_type0.getCustomerSignature();
				dh.writeTo(bos);
				customerSignature = new String(bos.toByteArray());
				LogGEN.writeTrace("CBG_Log", "InqCustAcctMiscDtlsResMsg customerSignature3: "+customerSignature);
			}
			catch(Exception e){
				LogGEN.writeTrace("CBG_Log", "Exception in getCustomerSignature"+e);
			}

			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				StringBuilder misCodesdetail = new StringBuilder();
				StringBuilder CBRDetail = new StringBuilder();
				StringBuilder CBDetail = new StringBuilder();
				StringBuilder customerRelation = new StringBuilder();
				if(inq_cust_res_type0 != null){
					MisCodeDetails_type0 miscodeDetails = inq_cust_res_type0.getMisCodeDetails();
					MisCodes_type0 miscode[] = miscodeDetails.getMisCodes();
					if(miscode != null){
						for(MisCodes_type0 detailsType0 : miscode) {
							String misCodeType = detailsType0.getMisCodeType();
							String misCodeNumber = detailsType0.getMisCodeNumber();
							String misCodeText = detailsType0.getMisCodeText();

							misCodesdetail.append("<misCodes>").append("\n")
							.append("<misCodeType>"+misCodeType+"</misCodeType>").append("\n")
							.append("<misCodeNumber>"+misCodeNumber+"</misCodeNumber>").append("\n")
							.append("<misCodeText>"+misCodeText+"</misCodeText>").append("\n")
							.append("</misCodes>").append("\n");
						}
					}
					CustomerCBRDetails_type0 customerCBRDetails = inq_cust_res_type0.getCustomerCBRDetails();
					//Changes 21st Sept - Put null condition					
					if(customerCBRDetails!=null)
					{
						CBRDetails_type0 CBRDetails[] = customerCBRDetails.getCBRDetails();
						if(CBRDetails != null){
							for(CBRDetails_type0 detailsType0 : CBRDetails) {
								String custAccountNumber = detailsType0.getCustAccountNumber();
								String CBRField = detailsType0.getCBRField();
								String CBRLable = detailsType0.getCBRLable();
								String CBRValue = detailsType0.getCBRValue();
								String CBRValueDescription = detailsType0.getCBRValueDescription();

								CBRDetail.append("<CBRDetails>").append("\n")
								.append("<custAccountNumber>"+custAccountNumber+"</custAccountNumber>").append("\n")
								.append("<CBRField>"+CBRField+"</CBRField>").append("\n")
								.append("<CBRLable>"+CBRLable+"</CBRLable>").append("\n")
								.append("<CBRValue>"+CBRValue+"</CBRValue>").append("\n")
								.append("<CBRValueDescription>"+CBRValueDescription+"</CBRValueDescription>").append("\n")
								.append("</CBRDetails>").append("\n");
							}
						}
					}					
					CustomerChequeBookDetails_type0 customerChequeBookDetails = inq_cust_res_type0.getCustomerChequeBookDetails();
					if(customerChequeBookDetails != null){
						CBDetails_type0 CBDetails[] = customerChequeBookDetails.getCBDetails();
						if(CBDetails != null){
							for(CBDetails_type0 detailsType0 : CBDetails) {
								String custAccountNum = detailsType0.getCustAccountNumber();
								String CBIssueDate = detailsType0.getCBIssueDate();
								String CBStatus = detailsType0.getCBStatus();
								String CBSerialNumber = detailsType0.getCBSerialNumber();

								CBDetail.append("<CBDetails>").append("\n")
								.append("<custAccountNumber>"+custAccountNum+"</custAccountNumber>").append("\n")
								.append("<CBIssueDate>"+CBIssueDate+"</CBIssueDate>").append("\n")
								.append("<CBStatus>"+CBStatus+"</CBStatus>").append("\n")
								.append("<CBSerialNumber>"+CBSerialNumber+"</CBSerialNumber>").append("\n")
								.append("</CBDetails>").append("\n");
							}
						}
					}
					CustomerRelationDetails_type0 customerRelationDetails = inq_cust_res_type0.getCustomerRelationDetails();
					if(customerRelationDetails != null){
						CustomerRelations_type0 customerRelations[] = customerRelationDetails.getCustomerRelations();
						if(customerRelations != null){
							for(CustomerRelations_type0 detailsType0 : customerRelations) {
								String customerId = detailsType0.getCustomerId();
								String custFullName = detailsType0.getCustFullName();
								String custRelationCode = detailsType0.getCustRelationCode();
								String custRelationText = detailsType0.getCustRelationText();
								String custInvRelationCode = detailsType0.getCustInvRelationCode();
								String custInvRelationText = detailsType0.getCustInvRelationText();
								String flagPrimarySecondary = detailsType0.getFlagPrimarySecondary();

								customerRelation.append("<customerRelations>").append("\n")
								.append("<customerId>"+customerId+"</customerId>").append("\n")
								.append("<custFullName>"+custFullName+"</custFullName>").append("\n")
								.append("<custRelationCode>"+custRelationCode+"</custRelationCode>").append("\n")
								.append("<custRelationText>"+custRelationText+"</custRelationText>").append("\n")
								.append("<custInvRelationCode>"+custInvRelationCode+"</custInvRelationCode>").append("\n")
								.append("<custInvRelationText>"+custInvRelationText+"</custInvRelationText>").append("\n")
								.append("<flagPrimarySecondary>"+flagPrimarySecondary+"</flagPrimarySecondary>").append("\n")
								.append("</customerRelations>").append("\n");
							}
						}
					}
				}
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>InqCustAcctMiscDtls</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<InqCustAcctMiscDtlsRes>"+
						"<customerId>"+custID+"</customerId>" +
						"<customerSignatureFlag>"+customerSignatureFlag+"</customerSignatureFlag>"+
						"<customerSignatureType>"+customerSignatureType+"</customerSignatureType>" +
						"<customerSignaturePresent>"+customerSignaturePresent+"</customerSignaturePresent>"+
						"<customerPhotoPresent>"+customerPhotoPresent+"</customerPhotoPresent>" +
						"<customerSignature>"+customerSignature+"</customerSignature>"+
						"<misCodeDetails>"+
						misCodesdetail+
						"</misCodeDetails>"+
						"<customerCBRDetails>"+
						CBRDetail+
						"</customerCBRDetails>"+
						"<customerChequeBookDetails>"+
						CBDetail+
						"</customerChequeBookDetails>"+
						"<customerRelationDetails>"+
						customerRelation+
						"</customerRelationDetails>"+	
						"</InqCustAcctMiscDtlsRes>"+	
						"</Output>";
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqCustAcctMiscDtls</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><Status>ERROR</Status><td>Unable to Inquiry Cust Acct Misc Dtls Details</td></Output>";
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Service :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDesc=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqCustAcctMiscDtls</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to Inquiry Cust Acct Misc Dtls Details</Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqCustAcctMiscDtls</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to Inquiry Cust Acct Misc Dtls Details</Output>";
			}

//			LogGEN.writeTrace("CBG_Log","outputXML : finally :"+sOutput);

			String Status="";
			if(sReturnCode.equalsIgnoreCase("0"))
			{
				Status="Success";
			}
			else if(sReturnCode.equalsIgnoreCase("2"))
				Status="Partial Success";
			else
				Status="Failure";

			try {
				//sHandler.readCabProperty("JTS");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	

			String dburl=(String)currentCabPropertyMap.get("DBURL");
			String dbuser=(String)currentCabPropertyMap.get("USER");
			String dbpass=(String)currentCabPropertyMap.get("PASS");

			String inputXml=xmlInput;
			LogGEN.writeTrace("CBG_Log", inputXml);
			String winame=xmlDataParser.getValueOf("WiName");
			LogGEN.writeTrace("CBG_Log", "WINAME:"+winame);
			String sessionID= xmlDataParser.getValueOf("SessionId");
			String call_type1=xmlDataParser.getValueOf("DSCOPCallType");
			sCabinet=xmlDataParser.getValueOf("EngineName");
			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

			try
			{
				dbpass=AESEncryption.decrypt(dbpass);
			}
			catch(Exception e)
			{

			}
//			DBConnection con=new DBConnection();
//			String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
//					"and randomnumber='"+ sessionID +"'),'"+call_type1+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
//					"?,sysdate,'"+  Status + "')";
//			LogGEN.writeTrace("CBG_Log","InqCustAcctMiscDtls  Query : finally :"+Query);
//			LogGEN.writeTrace("CBG_Log","sOrg_put : finally :"+sOrg_put);
			try {
//				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_put.replaceAll("'", "''"));
			} catch (Exception e2) {
				LogGEN.writeTrace("CBG_Log","InqCustAcctMiscDtls  Exception: finally :"+e2.getStackTrace());
			}
		}
		return sOutput;			
	}
}