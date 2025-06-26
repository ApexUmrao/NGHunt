package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.MQIntegration;
import com.newgen.client.XMLParser;

public class MQECBReport extends DSCOPServiceHandler {
	String xmlInput = "";
	String sOrgRes  = "";

	private String dburl  = "";
	private String dbuser = "";
	private String dbpass = "";

	public String ecbReport(String inputXML) {

		LogGEN.writeTrace("CBG_Log", "Function Called : ecbReport MQ");

		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(inputXML);

		String winame = xmlDataParser.getValueOf("WIName");
		String sessionID = xmlDataParser.getValueOf("SessionId");
		String call_type = xmlDataParser.getValueOf("DSCOPCallType");

		String ref_no = xmlDataParser.getValueOf("REF_NO");
		String senderID = xmlDataParser.getValueOf("SenderId");
		String EnquiryType = xmlDataParser.getValueOf("EnquiryType");
		String ConsentFlag = xmlDataParser.getValueOf("ConsentFlag");
		String ContractType = xmlDataParser.getValueOf("ContractType");
		String NoOfDays_DedupeChk = xmlDataParser.getValueOf("NoOfDays_DedupeChk");
		String ForceNewfetch = xmlDataParser.getValueOf("ForceNewfetch");
		String ReportType = xmlDataParser.getValueOf("ReportType");
		String Role = xmlDataParser.getValueOf("Role");
		String UserID = xmlDataParser.getValueOf("UserID");
		String DOB= xmlDataParser.getValueOf("DOB");
		String EmiratesId=xmlDataParser.getValueOf("EmiratesId");
		String FullName=xmlDataParser.getValueOf("FullName");
		String Gender=xmlDataParser.getValueOf("Gender");
		String Nationality=xmlDataParser.getValueOf("Nationality");
		String Passport=xmlDataParser.getValueOf("Passport");
		String PassportExpiryDate=xmlDataParser.getValueOf("PassportExpiryDate");
		String PrimaryMobileNo=xmlDataParser.getValueOf("PrimaryMobileNo");
		String NoOfInstallments=xmlDataParser.getValueOf("NoOfInstallments");
		String TotalAmount=xmlDataParser.getValueOf("TotalAmount");
		String CreditLimit=xmlDataParser.getValueOf("CreditLimit");
		String ReferenceNumber=xmlDataParser.getValueOf("ReferenceNumber");

		Date d = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);

		this.dburl = (String)currentCabPropertyMap.get("DBURL");
		this.dbuser = (String)currentCabPropertyMap.get("USER");
		this.dbpass = (String)currentCabPropertyMap.get("PASS");

		LogGEN.writeTrace("CBG_Log", "ecbReport MQ DBUrl : " + this.dburl);
		LogGEN.writeTrace("CBG_Log", "ecbReport MQ DBUser: " + this.dbuser);
		LogGEN.writeTrace("CBG_Log", "ecbReport MQ DBPass: " + this.dbpass);

		String returnCode = "";
		String errorDesc  = "";
		String output     = "";


		try {
			LogGEN.writeTrace("CBG_Log", "Creating InputXML");
			LogGEN.writeTrace("CBG_Log", "ContractType : "+ContractType);
			LogGEN.writeTrace("CBG_Log", "ReferenceNumber : "+ReferenceNumber);
			StringBuilder inputXml = new StringBuilder();
			if("CARD".equals(ContractType)){
				inputXml.append("<?xml version=\"1.0\"?>")
				.append("<fetchECBReportDtlsReq>")
				.append("<sysRefNumber>"+ref_no+"</sysRefNumber>") 
				.append("<senderID>"+senderID+"</senderID>") 
				.append("<reqTimeStamp>"+sDate+"</reqTimeStamp>")
				.append("<EnquiryType>"+EnquiryType+"</EnquiryType>")
				.append("<ConsentFlag>"+ConsentFlag+"</ConsentFlag>")
				.append("<ContractType>"+ContractType+"</ContractType>")
				.append("<NoOfDays_DedupeChk>"+NoOfDays_DedupeChk+"</NoOfDays_DedupeChk>")
				.append("<ForceNewfetch>"+ForceNewfetch+"</ForceNewfetch>")
				.append("<ReferenceNumber>"+ReferenceNumber+"</ReferenceNumber>")
				.append("<ReportType>"+ReportType+"</ReportType>")
				.append("<Role>"+Role+"</Role>")
				.append("<UserID>"+UserID+"</UserID>")
				.append("<CustomerDetails>")
				.append("<Individual>")
				.append("<DOB>"+DOB+"</DOB>")
				.append("<EmiratesId>"+EmiratesId+"</EmiratesId>")
				.append("<FullName>"+FullName+"</FullName>")
				.append("<Gender>"+Gender+"</Gender>")
				.append("<Nationality>"+Nationality+"</Nationality>")
				.append("<Passport>"+Passport+"</Passport>")
				.append("<PassportExpiryDate>"+PassportExpiryDate+"</PassportExpiryDate>")
				.append("<PreviousPassport>")
				.append("<Passport1/>") 
				.append("</PreviousPassport>") 
				.append("<PrimaryMobileNo>"+PrimaryMobileNo+"</PrimaryMobileNo>")
				.append("</Individual>")
				.append("<NotInstallment>")
				.append("<CreditLimit>"+CreditLimit+"</CreditLimit>") 
				.append("</NotInstallment>") 
				.append("</CustomerDetails>") 
				.append("</fetchECBReportDtlsReq>");
			}
			else{
				inputXml.append("<?xml version=\"1.0\"?>")
				.append("<fetchECBReportDtlsReq>")
				.append("<sysRefNumber>"+ref_no+"</sysRefNumber>") 
				.append("<senderID>"+senderID+"</senderID>") 
				.append("<reqTimeStamp>"+sDate+"</reqTimeStamp>")
				.append("<EnquiryType>"+EnquiryType+"</EnquiryType>")
				.append("<ConsentFlag>"+ConsentFlag+"</ConsentFlag>")
				.append("<ContractType>"+ContractType+"</ContractType>")
				.append("<NoOfDays_DedupeChk>"+NoOfDays_DedupeChk+"</NoOfDays_DedupeChk>")
				.append("<ForceNewfetch>"+ForceNewfetch+"</ForceNewfetch>")
				.append("<ReferenceNumber>"+ReferenceNumber+"</ReferenceNumber>")
				.append("<ReportType>"+ReportType+"</ReportType>")
				.append("<Role>"+Role+"</Role>")
				.append("<UserID>"+UserID+"</UserID>")
				.append("<CustomerDetails>")
				.append("<Individual>")
				.append("<DOB>"+DOB+"</DOB>")
				.append("<EmiratesId>"+EmiratesId+"</EmiratesId>")
				.append("<FullName>"+FullName+"</FullName>")
				.append("<Gender>"+Gender+"</Gender>")
				.append("<Nationality>"+Nationality+"</Nationality>")
				.append("<Passport>"+Passport+"</Passport>")
				.append("<PassportExpiryDate>"+PassportExpiryDate+"</PassportExpiryDate>")
				.append("<PreviousPassport>")
				.append("<Passport1/>") 
				.append("</PreviousPassport>") 
				.append("<PrimaryMobileNo>"+PrimaryMobileNo+"</PrimaryMobileNo>")
				.append("</Individual>")
				.append("<Installment>")
				.append("<NoOfInstallments>"+NoOfInstallments+"</NoOfInstallments>") 
				.append("<TotalAmount>"+TotalAmount+"</TotalAmount>") 
				.append("</Installment>") 
				.append("</CustomerDetails>") 
				.append("</fetchECBReportDtlsReq>");
			}

			xmlInput = inputXml.toString();
			LogGEN.writeTrace("CBG_Log", "MQIntegration xmlInput :" + xmlInput);  
			MQIntegration mq = new MQIntegration();
			sOrgRes = mq.MQPutGetMessage(xmlInput,"ECBMQ");
			LogGEN.writeTrace("CBG_Log", "MQIntegration response :" + sOrgRes);            
		} catch (Exception e) {
			LogGEN.writeTrace("CBG_Log", "Error in MQ Service :" + e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in MQ Service :" + e.getStackTrace());

			errorDesc = e.getMessage();
			returnCode = "-2";

			output = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>MQECBReport</Option><Output><returnCode>" + returnCode + "</returnCode><errorDescription>" + errorDesc + "</errorDescription></Output>";

			e.printStackTrace();
		} finally {			
			try
			{
				this.dbpass = AESEncryption.decrypt(this.dbpass);
			}
			catch(Exception e)
			{
				e.printStackTrace(); 
			}

			DBConnection con = new DBConnection();

			String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'')";
			LogGEN.writeTrace("CBG_Log"," ECB Application Detail Report NI Query : finally :"+Query);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass, Query, this.xmlInput.replaceAll("'", "''"),"");
			} catch (Exception e) {
				LogGEN.writeTrace("CBG_Log", "Exception MQECBReport sFinalQuery : " + e.getMessage());
			}

			if(sOrgRes.equalsIgnoreCase("1")){
				return "<returnCode>1</returnCode><errorDescription>Failure</errorDescription>";
			}
			else if(sOrgRes.equalsIgnoreCase("0")){
				return "<returnCode>0</returnCode><errorDescription>Success</errorDescription>";
			}
		}		
		return output;
	}
}
