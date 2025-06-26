package com.newgen.client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.newgen.AESEncryption;

public class CRMUpdate  extends WebServiceHandler
{
	public String updateData(String sInputXML)
	{		
		System.out.println("sInput XML ----"+sInputXML);
		WebServiceHandler sHandler= new WebServiceHandler();
		try {
			sHandler.readCabProperty("JTS");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		String dburl=(String)currentCabPropertyMap.get("DBURL");
		String dbuser=(String)currentCabPropertyMap.get("USER");
		String dbpass=(String)currentCabPropertyMap.get("PASS");	
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		
		String custid=xmlDataParser.getValueOf("customerid");
		String resilandline=xmlDataParser.getValueOf("residenceLandlineno");
		String residenceBuildingName=xmlDataParser.getValueOf("residenceBuildingName");
		String residenceStreetName=xmlDataParser.getValueOf("residenceStreetName");
		String residenceFlatNo=xmlDataParser.getValueOf("residenceFlatNo");
		String residenceArea=xmlDataParser.getValueOf("residenceArea");
		String residenceCity=xmlDataParser.getValueOf("residenceCity");
		String residenceState=xmlDataParser.getValueOf("residenceState");
		String residenceCountry=xmlDataParser.getValueOf("residenceCountry");
		String residencePOBox=xmlDataParser.getValueOf("residencePOBox");
		String residencePhone=xmlDataParser.getValueOf("residencePhone");
		String personalPhone=xmlDataParser.getValueOf("personalPhone");
		String personalMobile=xmlDataParser.getValueOf("personalMobile");
		String personalFax=xmlDataParser.getValueOf("personalFax");
		String personalEMail=xmlDataParser.getValueOf("personalEMail");
		String service=xmlDataParser.getValueOf("msgFunction");
		System.out.println("*****************************************************");
		
		String params=custid+":; "+resilandline+":; "+residenceBuildingName+":; "+residenceStreetName+":; "+residenceFlatNo+":; "+residenceArea+
		":;"+residenceCity+":; "+residenceState+":; "+residenceCountry+":; "+residencePOBox+":; "+residencePhone+":; "+personalPhone+":; "+personalMobile+
		":;"+personalFax+":; "+personalEMail+":;";
	
		LogGEN.writeTrace("Log", "11111111111111111111%%%%%%%%%%%%");
		String Query="";
		String count="";
		String output="";
		DBConnection con=new DBConnection();
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = "";
		try
		 {
			
			dbpass=AESEncryption.decrypt(dbpass);
		 }
		 catch(Exception e)
		 {
			 LogGEN.writeTrace("ArchieveLogs", e.getMessage());
		 }
		 
		if(service.equalsIgnoreCase("MNT_CustomerContactDetails"))
		{
			Query="{call adcbcrmpackage.MNTCustContact(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";		
			count=con.executeProc("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,params,16,"number");
			LogGEN.writeTrace("Log", "CRM UPDATE0 "+dburl+" " +dbuser);
			sDate = dateFormat.format(d);
			output="<?xml version=\"1.0\" encoding=\"UTF-8\"?><ADCBService><Header><msgFunction>MNT_CustomerContactDetails</msgFunction>"+
	        "<reqpostdatetimeStamp>"+xmlDataParser.getValueOf("reqpostdatetimeStamp")+"</reqpostdatetimeStamp>"+
	        "<respostdatetimeStamp>"+sDate+"</respostdatetimeStamp>"+
	        "<requestextSystemID>"+xmlDataParser.getValueOf("requestextSystemID")+"</requestextSystemID>"+
	        "<txnsystemname>"+xmlDataParser.getValueOf("txnsystemname")+"</txnsystemname>"+
	        "<trackingId>"+ xmlDataParser.getValueOf("trackingId")+"</trackingId>"+
	        "<userId/><flexField1/><flexField2/><flexField3/><flexField4/><flexField5/><MainCode>0</MainCode><errorCode>0</errorCode><errorDesc>SUCCESS</errorDesc>"+
	        "</Header><body><Reply_srvreq><Rep_MNT_CustomerContactDetails><customerid>"+xmlDataParser.getValueOf("customerid")+"</customerid></Rep_MNT_CustomerContactDetails></Reply_srvreq></body></ADCBService>";
			LogGEN.writeTrace("Log", "CRM UPDATE "+output);
			LogGEN.writeTrace("Log", "CRM UPDATE1 "+params);
			Query="{call adcbcrmpackage.UpdateLogTable(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";		
			params="0:; "+custid+":; "+xmlDataParser.getValueOf("trackingId")+":; "+xmlDataParser.getValueOf("requestextSystemID")+":; "+xmlDataParser.getValueOf("trackingId")+":; "+xmlDataParser.getValueOf("reqpostdatetimeStamp")+":; "+
			service+":; "+sInputXML+":; "+output+":;0:; :;"+sDate+":;I";
			count=con.executeProc("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,params,14,"string");
			LogGEN.writeTrace("Log", "CRM UPDATE2 "+count);
		}
		else
		{
			Query="{call adcbcrmpackage.MNTCustContact(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";		
			count=con.executeProc("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,params,16,"number");
		}
		
		LogGEN.writeTrace("Log","outputXML : finally :"+output);
		System.out.println("ret:"+count);
		return output;			
	}

}
