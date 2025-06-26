package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.MQIntegration;
import com.newgen.client.XMLParser;

public class InternalDBRETB extends DSCOPServiceHandler {
	String xmlInput = "";
	String sOrgRes  = "";

	private String dburl  = "";
	private String dbuser = "";
	private String dbpass = "";

	public String internalDBRETB(String inputXML) {

		LogGEN.writeTrace("CBG_Log", "Function Called : internalDBRETB");

		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(inputXML);

		String winame = xmlDataParser.getValueOf("WIName");
		String sessionID = xmlDataParser.getValueOf("SessionId");
		String call_type = xmlDataParser.getValueOf("DSCOPCallType");

		String ref_no = xmlDataParser.getValueOf("REF_NO");
		String senderID = xmlDataParser.getValueOf("SenderId");
		String customerID = xmlDataParser.getValueOf("CustomerID");
		String monthlyIncome = xmlDataParser.getValueOf("MonthlyIncome");

		Date d = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);

		this.dburl = (String)currentCabPropertyMap.get("DBURL");
		this.dbuser = (String)currentCabPropertyMap.get("USER");
		this.dbpass = (String)currentCabPropertyMap.get("PASS");

		LogGEN.writeTrace("CBG_Log", "internalDBRETB MQ DBUrl : " + this.dburl);
		LogGEN.writeTrace("CBG_Log", "internalDBRETB MQ DBUser: " + this.dbuser);
		LogGEN.writeTrace("CBG_Log", "internalDBRETB MQ DBPass: " + this.dbpass);

		String returnCode = "";
		String errorDesc  = "";
		String output     = "";


		try {
			LogGEN.writeTrace("CBG_Log", "Creating InputXML");
			StringBuilder inputXml = new StringBuilder();

			inputXml.append("<?xml version=\"1.0\"?>")
			.append("<FetchInternalDBRRequest>")
			.append("<SysRefNumber>"+ref_no+"</SysRefNumber>") 
			.append("<SenderID>"+senderID+"</SenderID>") 
			.append("<ReqTimeStamp>"+sDate+"</ReqTimeStamp>")
			.append("<CustomerDetails>")
			.append("<CID>"+customerID+"</CID>")
			.append("<Income>"+monthlyIncome+"</Income>")
			.append("</CustomerDetails>") 
			.append("</FetchInternalDBRRequest>");

			xmlInput = inputXml.toString();
			LogGEN.writeTrace("CBG_Log", "MQIntegration xmlInput :" + xmlInput);  
			MQIntegration mq = new MQIntegration();
			sOrgRes = mq.MQPutGetMessage(xmlInput,"InternalDBRETB");
			LogGEN.writeTrace("CBG_Log", "MQIntegration response :" + sOrgRes);            
		} catch (Exception e) {
			LogGEN.writeTrace("CBG_Log", "Error in MQ Service :" + e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in MQ Service :" + e.getStackTrace());

			errorDesc = e.getMessage();
			returnCode = "-2";

			output = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InternalDBRETB</Option><Output><returnCode>" + returnCode + "</returnCode><errorDescription>" + errorDesc + "</errorDescription></Output>";

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
			LogGEN.writeTrace("CBG_Log"," InternalDBRETB Application Detail Report NI Query : finally :"+Query);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass, Query, this.xmlInput.replaceAll("'", "''"),"");
			} catch (Exception e) {
				LogGEN.writeTrace("CBG_Log", "Exception InternalDBRETB sFinalQuery : " + e.getMessage());
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