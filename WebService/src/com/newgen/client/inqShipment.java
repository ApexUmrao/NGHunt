package com.newgen.client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.stub.InqShipmentTrackingStub;
import com.newgen.stub.InqShipmentTrackingStub.HeaderType;
import com.newgen.stub.InqShipmentTrackingStub.InqShipmentTrackingReqMsg;
import com.newgen.stub.InqShipmentTrackingStub.InqShipmentTrackingReq_type0;
import com.newgen.stub.InqShipmentTrackingStub.Shipments_type0;

public class inqShipment extends WebServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	String xmlInput="";
	
	/**
	 * Function written to fetch customer information
	 * @author gupta.ashish
	 * @param sInputXML
	 * @return
	 */

		@SuppressWarnings("finally")
		public String Card_Shipment_Enquiry(String sInputXML) 
		{
			LogGEN.writeTrace("Log", "Fuction called Card_Shipment_Enquiry");
			LogGEN.writeTrace("Log", "sInputXML---"+sInputXML);
			XMLParser xmlDataParser = new XMLParser();
			xmlDataParser.setInputXML(sInputXML);
			String sOutput="";
			String sReturnCode= "";
			String sErrorDesc = "";
			WebServiceHandler sHandler= new WebServiceHandler();
			Date d= new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String sDate = dateFormat.format(d);
			try
			{
				
				sHandler.readCabProperty("Card_Shipment_Enquiry");				
				sWSDLPath=(String)currentCabPropertyMap.get("WSDL_PATH");
				sCabinet=(String)currentCabPropertyMap.get("CABINET");
				sUser=(String)currentCabPropertyMap.get("USER");
				sLoginReq=Boolean.valueOf((String)currentCabPropertyMap.get("LOGIN_REQ"));
				sPassword=(String)currentCabPropertyMap.get("PASSWORD");
				
				LogGEN.writeTrace("Log", "read Property successfully");
				LogGEN.writeTrace("Log", "WSDL PATH---- "+(String)currentCabPropertyMap.get("WSDL_PATH"));
				LogGEN.writeTrace("Log", "CABINET---- "+(String)currentCabPropertyMap.get("CABINET"));
				LogGEN.writeTrace("Log", "USER---- "+(String)currentCabPropertyMap.get("USER"));
				LogGEN.writeTrace("Log", "PASSWORD---- "+(String)currentCabPropertyMap.get("PASSWORD"));
				LogGEN.writeTrace("Log", "LOGIN_REQ---- "+(String)currentCabPropertyMap.get("LOGIN_REQ"));
						
				String sCustomerID= xmlDataParser.getValueOf("CUST_ID");
				String ref_no=xmlDataParser.getValueOf("REF_NO");
				String waybill=xmlDataParser.getValueOf("waybillNumber");
				LogGEN.writeTrace("Log", "sCustomerID---"+sCustomerID);
				
				
				//sDate="06/08/2013 18:33:10";
				LogGEN.writeTrace("Log", "sDate---"+sDate);
				
				InqShipmentTrackingStub ship_stub=new InqShipmentTrackingStub(sWSDLPath);
				InqShipmentTrackingReq_type0 ship_Req = new InqShipmentTrackingReq_type0();				
				InqShipmentTrackingReqMsg ship_Req_Msg=new InqShipmentTrackingReqMsg();
			
				HeaderType Header_Input = new HeaderType();
				
				LogGEN.writeTrace("Log", "All Objects created");
				
				Header_Input.setUsecaseID("1234");
				Header_Input.setServiceName("InqShipmentTracking");
				Header_Input.setVersionNo("1.0");
				Header_Input.setServiceAction("Inquiry");
				Header_Input.setSysRefNumber(ref_no);
				Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
				Header_Input.setReqTimeStamp(sDate);
				Header_Input.setUsername(sCustomerID);
				Header_Input.setCredentials(loggedinuser);
				
				Shipments_type0 shipments=new Shipments_type0();
				LogGEN.writeTrace("Log", "waybill:"+waybill);
				
				shipments.setWaybillNumber(waybill);
				LogGEN.writeTrace("Log", "waybill:"+waybill);
				//ship_Req.setShipments(shipments);
				ship_Req.addShipments(shipments);
				ship_Req_Msg.setHeader(Header_Input);
				ship_Req_Msg.setInqShipmentTrackingReq(ship_Req);
			  
			   /* java.beans.XMLEncoder enc=new XMLEncoder(new BufferedOutputStream(new FileOutputStream("c:/ashish/input.xml")));
			    enc.writeObject(debi_Info_Req_Msg);
			    enc.close();
			    
			 
			    */
				LogGEN.writeTrace("Log", "All Objects created...");
				xmlInput=ship_stub.getinputXML(ship_Req_Msg);
			    String ship_Rep_Msg=ship_stub.inqShipmentTracking_Oper(ship_Req_Msg);
			    ship_Rep_Msg=ship_Rep_Msg.replace("ns1:", "");
			    ship_Rep_Msg=ship_Rep_Msg.replace("ns0:", "");
			    ship_Rep_Msg=ship_Rep_Msg.replace("waybillNumber", "WaybillNumber");
			    ship_Rep_Msg=ship_Rep_Msg.replace("updateCode", "UpdateCode");
			    ship_Rep_Msg=ship_Rep_Msg.replace("updateDateTime", "UpdateDateTime");
			    ship_Rep_Msg=ship_Rep_Msg.replace("updateDescription", "UpdateDescription");
			    ship_Rep_Msg=ship_Rep_Msg.replace("updateLocation", "UpdateLocation");
			    ship_Rep_Msg=ship_Rep_Msg.replace("problemCode", "ProblemCode");
			    ship_Rep_Msg=ship_Rep_Msg.replace("comments", "Comments");
			    
			    sOutput=ship_Rep_Msg;
			    sReturnCode=sOutput.substring(sOutput.indexOf("<returnCode>")+12,sOutput.indexOf("</returnCode>"));
			    
			
			    
			    LogGEN.writeTrace("Log", "All Objects created1");
			 
			}
			catch (Exception e)
			{
				LogGEN.writeTrace("Log", "Error in Web Serviice :"+e.toString());
				LogGEN.writeTrace("Log", "Error Trace in Web Serviice :"+e.getStackTrace());
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>Card_Shipment_Enquiry</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to Fetch Shipment Details.</td></Output>";
				e.printStackTrace();
			}
			finally
			{
				LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput.trim().length());				
				if(sOutput.trim().length()<1)
				{
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>Card_Shipment_Enquiry</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to Fetch Shipment Details.</td></Output>";
				}
				String Status="";
				if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
				{
					Status="Success";
				}
				else
					Status="Failure";
				
				 try {
					sHandler.readCabProperty("JTS");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				 
					String dburl=(String)currentCabPropertyMap.get("DBURL");
					String dbuser=(String)currentCabPropertyMap.get("USER");
					String dbpass=(String)currentCabPropertyMap.get("PASS");
					
					
					String inputXml=xmlInput;
					//LogGEN.writeTrace("Log", inputXml);
					String winame=xmlDataParser.getValueOf("WiName");
					String sessionID= xmlDataParser.getValueOf("SessionId");
					String call_type=xmlDataParser.getValueOf("Calltype");
					sCabinet=xmlDataParser.getValueOf("EngineName");
					dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					
					 String outputxml=sOutput;
					 String Query="insert into usr_0_other_calls_web_log(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
						"and randomnumber='"+ sessionID +"'),'"+call_type+"','"+ inputXml.replaceAll("'", "''") +"',to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
						"'"+ outputxml.replaceAll("'", "''") +"',sysdate,'"+  Status + "')";

					 try
					 {
						 dbpass=AESEncryption.decrypt(dbpass);
					 }
					 catch(Exception e)
					 {
						 
					 }
					 DBConnection con=new DBConnection();
					 try
					 {
						con.execute("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query);
					 }
					 catch(Exception ee){}
				LogGEN.writeTrace("Log","outputXML : finally :"+sOutput);
				return sOutput;			
			}
		}
}
