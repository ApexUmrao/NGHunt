package com.newgen.client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.stub.AddShipmentCreationStub;
import com.newgen.stub.AddShipmentCreationStub.Address;
import com.newgen.stub.AddShipmentCreationStub.Contact;
import com.newgen.stub.AddShipmentCreationStub.Dimensions;
import com.newgen.stub.AddShipmentCreationStub.HeaderType;
import com.newgen.stub.AddShipmentCreationStub.AddShipmentCreationReqMsg;
import com.newgen.stub.AddShipmentCreationStub.AddShipmentCreationReq_type0;
import com.newgen.stub.AddShipmentCreationStub.Money;
import com.newgen.stub.AddShipmentCreationStub.Party;
import com.newgen.stub.AddShipmentCreationStub.Shipment;
import com.newgen.stub.AddShipmentCreationStub.ShipmentDetails;
import com.newgen.stub.AddShipmentCreationStub.Weight;

public class AddShipment extends WebServiceHandler
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
		public String Add_Shipment(String sInputXML) 
		{
			LogGEN.writeTrace("Log", "Fuction called ADD_Shipment");
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
				
				sHandler.readCabProperty("ADD_Shipment");				
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
				LogGEN.writeTrace("Log", "sCustomerID---"+sCustomerID);
				
				
				//sDate="06/08/2013 18:33:10";
				LogGEN.writeTrace("Log", "sDate---"+sDate);
				
				AddShipmentCreationStub ship_stub=new AddShipmentCreationStub(sWSDLPath);
				AddShipmentCreationReq_type0 ship_Req = new AddShipmentCreationReq_type0();				
				AddShipmentCreationReqMsg ship_Req_Msg=new AddShipmentCreationReqMsg();
			
				HeaderType Header_Input = new HeaderType();
				
				LogGEN.writeTrace("Log", "All Objects created");
				
				Header_Input.setUsecaseID("1234");
				Header_Input.setServiceName("AddShipmentCreation");
				Header_Input.setVersionNo("1.0");
				Header_Input.setServiceAction("Addition");
				Header_Input.setSysRefNumber(ref_no);
				Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
				Header_Input.setReqTimeStamp(sDate);
				Header_Input.setUsername(sCustomerID);
				Header_Input.setCredentials(loggedinuser);
				
				Shipment shipment=new Shipment();
				Party party=new Party();
				party.setAccountNumber(xmlDataParser.getValueOf("accountNumber"));
				Address address=new Address();
				address.setLine1(xmlDataParser.getValueOf("line1"));
				address.setLine2(xmlDataParser.getValueOf("line2"));
				address.setLine3(xmlDataParser.getValueOf("line3"));
				address.setCity(xmlDataParser.getValueOf("city"));
				address.setCountryCode(xmlDataParser.getValueOf("countryCode"));
				address.setStateOrProvinceCode(xmlDataParser.getValueOf("stateOrProvinceCode"));
				address.setPostCode(xmlDataParser.getValueOf("postCode"));
				party.setPartyAddress(address);
				
				Contact contact=new Contact();
				contact.setPersonName(xmlDataParser.getValueOf("personName"));
				contact.setDepartment(xmlDataParser.getValueOf("department"));
				contact.setEmailAddress(xmlDataParser.getValueOf("emailAddress"));
				contact.setCellPhone(xmlDataParser.getValueOf("cellPhone"));
				contact.setPhoneNumber1(xmlDataParser.getValueOf("phoneNumber1"));
				contact.setPhoneNumber2(xmlDataParser.getValueOf("phoneNumber2"));
				contact.setFaxNumber(xmlDataParser.getValueOf("faxNumber"));
				contact.setType(xmlDataParser.getValueOf("type"));
				party.setContact(contact);
				
				
				/////////---------------consignee
				
				Party con_party=new Party();
				con_party.setAccountNumber(xmlDataParser.getValueOf("con_accountNumber"));
				
				Address con_address=new Address();
				con_address.setLine1(xmlDataParser.getValueOf("con_line1"));
				con_address.setLine2(xmlDataParser.getValueOf("con_line2"));
				con_address.setLine3(xmlDataParser.getValueOf("con_line3"));
				con_address.setCity(xmlDataParser.getValueOf("con_city"));
				con_address.setCountryCode(xmlDataParser.getValueOf("con_countryCode"));
				con_address.setStateOrProvinceCode(xmlDataParser.getValueOf("con_stateOrProvinceCode"));
				con_address.setPostCode(xmlDataParser.getValueOf("con_postCode"));
				con_party.setPartyAddress(con_address);
				
				Contact con_contact=new Contact();
				
				con_contact.setPersonName(xmlDataParser.getValueOf("con_personName"));
				con_contact.setCompanyName(xmlDataParser.getValueOf("con_companyName"));
				con_contact.setDepartment(xmlDataParser.getValueOf("con_department"));
				con_contact.setEmailAddress(xmlDataParser.getValueOf("con_emailAddress"));
				con_contact.setCellPhone(xmlDataParser.getValueOf("con_cellPhone"));
				con_contact.setPhoneNumber1(xmlDataParser.getValueOf("con_phoneNumber1"));
				con_contact.setPhoneNumber2(xmlDataParser.getValueOf("con_phoneNumber2"));
				con_contact.setType(xmlDataParser.getValueOf("con_type"));
				con_contact.setFaxNumber(xmlDataParser.getValueOf("con_faxNumber"));
				
				con_party.setContact(con_contact);
				
				ShipmentDetails ship_det=new ShipmentDetails();
				
				
				Weight weight=new Weight();
				weight.setUnit(xmlDataParser.getValueOf("unit"));
				weight.setValue( Double.parseDouble(xmlDataParser.getValueOf( "value")));
				
				ship_det.setActualWeight(weight);
				ship_det.setDescriptionOfGoods(xmlDataParser.getValueOf("descriptionOfGoods"));
				ship_det.setGoodsOriginCountry(xmlDataParser.getValueOf( "goodsOriginCountry"));
				ship_det.setNumberOfPieces(Integer.parseInt(xmlDataParser.getValueOf("numberOfPieces")));
				ship_det.setProductGroup(xmlDataParser.getValueOf("productGroup"));
				ship_det.setProductType(xmlDataParser.getValueOf("productType"));
				ship_det.setPaymentType(xmlDataParser.getValueOf("paymentType"));
				ship_det.setPaymentOptions(xmlDataParser.getValueOf("paymentOptions"));
				Money money=new Money();
				money.setCurrencyCode(xmlDataParser.getValueOf("currencyCode"));
				money.setValue(Double.parseDouble( xmlDataParser.getValueOf("money")));
				ship_det.setCustomsValueAmount(money);
				
				shipment.setConsignee(con_party);
				
				
				Date dt=new Date();
				Calendar cal=Calendar.getInstance();
				cal.setTime(dt);
				shipment.setShippingDateTime(cal);
				
				Dimensions dim=new Dimensions();
				dim.setHeight(Double.parseDouble( xmlDataParser.getValueOf("height")));
				dim.setLength(Double.parseDouble(  xmlDataParser.getValueOf("length")));
				dim.setWidth(Double.parseDouble(  xmlDataParser.getValueOf("width")));
				dim.setUnit(  xmlDataParser.getValueOf("dunit"));
				ship_det.setDimensions(dim);
				shipment.setDetails(ship_det);
				shipment.setShipper(party);
				
				
			//	trans.set
			//	ship_Req.setTransaction(trans);
				
				ship_Req.setShipment(shipment);
				ship_Req_Msg.setHeader(Header_Input);
				ship_Req_Msg.setAddShipmentCreationReq(ship_Req);
				
				//AddShipmentCreationResMsg ship_res_msg= ship_stub.addShipmentCreation_Oper(ship_Req_Msg);
				xmlInput=ship_stub.getinputXML(ship_Req_Msg);
				String ship_res_msg= ship_stub.addShipmentCreation_Oper(ship_Req_Msg);
				
				System.out.println("Output"+ship_res_msg);
			  
			   ship_res_msg=ship_res_msg.replace("ns1:", "");
			   ship_res_msg=ship_res_msg.replace("ns0:", "");
			   ship_res_msg=ship_res_msg.replace("shipmentId", "WaybillNumber");
			   sOutput=ship_res_msg;
			   LogGEN.writeTrace("Log", sOutput);
			   sReturnCode=sOutput.substring(sOutput.indexOf("<returnCode>")+12,sOutput.indexOf("</returnCode>"));
				LogGEN.writeTrace("Log", "All Objects created...");
			   // InqShipmentTrackingResMsg ship_Rep_Msg=ship_stub.inqShipmentTracking_Oper(ship_Req_Msg);
			    LogGEN.writeTrace("Log", "All Objects created1");
			  //  java.beans.XMLEncoder enc=new XMLEncoder(new BufferedOutputStream(new FileOutputStream("c:/ashish/input.xml")));
			   // enc.writeObject(debi_Info_Rep_Msg);
			   // enc.close();
			    //Header_Input= ship_res_msg.getHeader();
			   /* sReturnCode= Header_Input.getReturnCode();
			    sErrorDetail=Header_Input.getErrorDetail();
			    sErrorDesc = Header_Input.getErrorDescription();
			    
			    LogGEN.writeTrace("Log", "Return Code---"+sReturnCode);
			    LogGEN.writeTrace("Log", "Error Detail---"+sErrorDetail);
			    LogGEN.writeTrace("Log", "Error Description---"+sErrorDesc);
			    
			    
			    
			    if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
				{			    
			    	LogGEN.writeTrace("Log", "Successful Result");
			    	AddShipmentCreationRes_type0 res=new AddShipmentCreationRes_type0();
			    	//res=ship_res_msg.getAddShipmentCreationRes();
			    	ProcessedShipment pship= res.getProcessedShipment();
			    	
			    	
				    	sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
							"<Option>Card_Shipment_Enquiry</Option>" +
							"<returnCode>"+sReturnCode+"</returnCode>" +
							"<errorDescription>"+sErrorDetail+"</errorDescription>"+
							"<ShipmentRes>"+
								"<WaybillNumber>"+ pship.getShipmentId() +"</WaybillNumber>"+																
							"</ShipmentRes>"+	
							"</Output>";
			    	
					LogGEN.writeTrace("Log", "Output XML--- "+sOutput);
				}
			    else
				{
			    	LogGEN.writeTrace("Log", "Failed");
			    	sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADD_Shipment</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to add shipment.</td></Output>";
				}
			    */
			    
			    
			}
			catch (Exception e)
			{
				LogGEN.writeTrace("Log", "Error in Web Serviice :"+e.toString());
				LogGEN.writeTrace("Log", "Error Trace in Web Serviice :"+e.getStackTrace());
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>ADD_Shipment</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to add shipment.</td></Output>";
				e.printStackTrace();
			}
			finally
			{
				LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput.trim().length());				
				if(sOutput.trim().length()<1)
				{
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>ADD_Shipment</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to add shipment.</td></Output>";
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
					 catch(Exception ee)
					 {
						 
					 }
				LogGEN.writeTrace("Log","outputXML : finally :"+sOutput);
				return sOutput;			
			}
		}
}
