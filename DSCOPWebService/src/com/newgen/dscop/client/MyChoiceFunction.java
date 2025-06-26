package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.ModMyChoiceFunctionsStub;
import com.newgen.dscop.stub.ModMyChoiceFunctionsStub.AddMyChoiceLeadReqMsg;
import com.newgen.dscop.stub.ModMyChoiceFunctionsStub.AddMyChoiceLeadReq_type0;
import com.newgen.dscop.stub.ModMyChoiceFunctionsStub.AddMyChoiceLeadResMsg;
import com.newgen.dscop.stub.ModMyChoiceFunctionsStub.AddMyChoiceLeadRes_type0;
import com.newgen.dscop.stub.ModMyChoiceFunctionsStub.Attributes_type0;
import com.newgen.dscop.stub.ModMyChoiceFunctionsStub.HeaderType;
import com.newgen.dscop.stub.ModMyChoiceFunctionsStub.MyChoiceLeadInformation_type0;
import com.newgen.dscop.stub.ModMyChoiceFunctionsStub.MyChoiceProductsInformation_type0;
import com.newgen.dscop.stub.ModMyChoiceFunctionsStub.ProductAttributes_type0;
import com.newgen.dscop.stub.ModMyChoiceFunctionsStub.Products_type1;

public class MyChoiceFunction extends DSCOPServiceHandler{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_put=null;

	@SuppressWarnings("finally")
	public String AddMyChoiceFunction(String sInputXML) 
	{

		LogGEN.writeTrace("CBG_Log", "Fuction called AddMyChoiceFunction");
		LogGEN.writeTrace("CBG_Log", "AddMyChoiceFunction sInputXML---");
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		LogGEN.writeTrace("CBG_Log", "AddMyChoiceFunction sInputXML---" +sInputXML);
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
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("MyChoiceFunction");
			LogGEN.writeTrace("CBG_Log", "MyChoiceFunction WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "MyChoiceFunction WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "MyChoiceFunction CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "MyChoiceFunction USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "MyChoiceFunction PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "MyChoiceFunction LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "MyChoiceFunction TIME_OUT: "+lTimeOut);

			String ref_no=xmlDataParser.getValueOf("REF_NO");

			String leadTypeID = xmlDataParser.getValueOf("leadTypeId");
			String serviceTypeID = xmlDataParser.getValueOf("serviceTypeId");
			String channelID = xmlDataParser.getValueOf("channelId");
			String status = xmlDataParser.getValueOf("status");
			String agentCode = xmlDataParser.getValueOf("agentCode");
			String itqanAssgmntQueue = xmlDataParser.getValueOf("itqanAssgmntQueue");
			String createDate = xmlDataParser.getValueOf("createDate");
			String bankingTypeId = xmlDataParser.getValueOf("bankingTypeId");
			String isNTBCust = xmlDataParser.getValueOf("isNTBCust");
			String custId = xmlDataParser.getValueOf("custId");
			String custName = xmlDataParser.getValueOf("custName");
			String custLang = xmlDataParser.getValueOf("custLang");
			String custEmail = xmlDataParser.getValueOf("custEmail");
			String custMobile = xmlDataParser.getValueOf("custMobile");
			String custMonthlyIncome = xmlDataParser.getValueOf("custMonthlyIncome");
			String docIdType = xmlDataParser.getValueOf("docIdType");
			String docIdNo = xmlDataParser.getValueOf("docIdNo");
			String bundleBonusTp = xmlDataParser.getValueOf("bundleBonusTp");
			String monthlyTpDraw = xmlDataParser.getValueOf("monthlyTpDraw");
			String totalNewTPs = xmlDataParser.getValueOf("totalNewTPs");
			String remarks = xmlDataParser.getValueOf("remarks");
			String nationCode = xmlDataParser.getValueOf("nationCode");
			String amountType = xmlDataParser.getValueOf("amountType");
			String serviceTypeName = xmlDataParser.getValueOf("serviceTypeName");

			ModMyChoiceFunctionsStub mychoice_function_stub=new ModMyChoiceFunctionsStub(sWSDLPath);
			AddMyChoiceLeadReqMsg add_mychoice_lead_req_msg = new AddMyChoiceLeadReqMsg();
			AddMyChoiceLeadReq_type0 add_mychoice_lead_req = new AddMyChoiceLeadReq_type0();
			MyChoiceLeadInformation_type0 add_mychoice_lead_info = new MyChoiceLeadInformation_type0();

			HeaderType Header_Input = new HeaderType();

			Header_Input.setServiceName("ModMyChoiceFunctions");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Addition");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setUsername(custId);
			add_mychoice_lead_req_msg.setHeader(Header_Input);

			add_mychoice_lead_info.setLeadTypeId(leadTypeID);
			add_mychoice_lead_info.setServiceTypeId(serviceTypeID);
			add_mychoice_lead_info.setChannelId(channelID);
			add_mychoice_lead_info.setStatus(status);
			add_mychoice_lead_info.setAgentCode(agentCode);
			add_mychoice_lead_info.setItqanAssgmntQueue(itqanAssgmntQueue);
			add_mychoice_lead_info.setCreateDate(createDate);
			add_mychoice_lead_info.setBankingTypeId(bankingTypeId);
			add_mychoice_lead_info.setIsNTBCust(isNTBCust);
			add_mychoice_lead_info.setCustId(custId);
			add_mychoice_lead_info.setCustName(custName);
			add_mychoice_lead_info.setCustLang(custLang);
			add_mychoice_lead_info.setCustEmail(custEmail);
			add_mychoice_lead_info.setCustMobile(custMobile);
			add_mychoice_lead_info.setCustMonthlyIncome(custMonthlyIncome);
			add_mychoice_lead_info.setDocIdType(docIdType);
			add_mychoice_lead_info.setDocIdNo(docIdNo);
			add_mychoice_lead_info.setBundleBonusTp(bundleBonusTp);
			add_mychoice_lead_info.setMonthlyTpDraw(monthlyTpDraw);
			add_mychoice_lead_info.setTotalNewTPs(totalNewTPs);
			add_mychoice_lead_info.setRemarks(remarks);
			add_mychoice_lead_info.setAmountType(amountType);
			add_mychoice_lead_info.setNationCode(nationCode);
			add_mychoice_lead_info.setServiceTypeName(serviceTypeName);

			MyChoiceProductsInformation_type0 add_mychoice_prod_info = new MyChoiceProductsInformation_type0();
			
			
			int noOfFields = xmlDataParser.getNoOfFields("products");
			LogGEN.writeTrace("CBG_Log", "products count :"+noOfFields);
			for (int noofTag = 0; noofTag < noOfFields; ++noofTag)
			{	
				Products_type1 products = new Products_type1();
				ProductAttributes_type0 productAttribute = new ProductAttributes_type0();
				
				String productXml = xmlDataParser.getNextValueOf("products");
				XMLParser xmlDataParser2 = new XMLParser();
				xmlDataParser2.setInputXML(productXml);
				String bundleId = xmlDataParser2.getValueOf("bundleId");
				String bundleName = xmlDataParser2.getValueOf("bundleName");
				String productCatId = xmlDataParser2.getValueOf("productCatId");
				String productId = xmlDataParser2.getValueOf("productId");
				String productLeadName = xmlDataParser2.getValueOf("productLeadName");
				String productCatPromoCode = xmlDataParser2.getValueOf("productCatPromoCode");
				String productCatName = xmlDataParser2.getValueOf("productCatName");

				int noOfProdAttrFields = xmlDataParser2.getNoOfFields("productAttributes");
				for (int noofProdAttrTag = 0; noofProdAttrTag < noOfProdAttrFields; ++noofProdAttrTag)
				{
					int start = xmlDataParser2.getStartIndex("productAttributes", 0, 0);
					int deadEnd = xmlDataParser2.getEndIndex("productAttributes", start, 0);
					int noOfAttributes = xmlDataParser2.getNoOfFields("attributes", start,deadEnd);
					int end = 0;
					for(int i = 0; i < noOfAttributes; ++i)
					{
						Attributes_type0 attri = new Attributes_type0();

						start = xmlDataParser2.getStartIndex("attributes", end, 0);
						end = xmlDataParser2.getEndIndex("attributes", start, 0);
						String attributeKey = xmlDataParser2.getValueOf("attributeKey", start, end);
						String attributeValue = xmlDataParser2.getValueOf("attributeValue", start, end);

						attri.setAttributeKey(attributeKey);
						attri.setAttributeValue(attributeValue);
						productAttribute.addAttributes(attri);
					}
				}

				products.setBundleName(bundleId);
				products.setBundleName(bundleName);
				products.setProductCatId(productCatId);
				products.setProductLeadName(productLeadName);
				products.setProductId(productId);
				products.setProductCatPromoCode(productCatPromoCode);
				products.setProductCatName(productCatName);
				products.setProductAttributes(productAttribute);
				add_mychoice_prod_info.addProducts(products);
			}
			add_mychoice_lead_info.setMyChoiceProductsInformation(add_mychoice_prod_info);
			add_mychoice_lead_req.setMyChoiceLeadInformation(add_mychoice_lead_info);
			add_mychoice_lead_req_msg.setAddMyChoiceLeadReq(add_mychoice_lead_req);
			System.out.println("MyChoice Function input xml "+mychoice_function_stub.getInputXml(add_mychoice_lead_req_msg));
			xmlInput=mychoice_function_stub.getInputXml(add_mychoice_lead_req_msg);
			System.out.println("MyChoice Function xmlInput xml  "+xmlInput);
			mychoice_function_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);

			AddMyChoiceLeadResMsg add_mychoice_res_msg= mychoice_function_stub.addMyChoiceLead_Oper(add_mychoice_lead_req_msg);
			sOrg_put=mychoice_function_stub.outputXML;
			LogGEN.writeTrace("CBG_Log", "MyChoiceLead_Oper sOrg_put: "+sOrg_put);

			Header_Input=add_mychoice_res_msg.getHeader();
			sReturnCode=  Header_Input.getReturnCode();
			sErrorDetail=Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();
			AddMyChoiceLeadRes_type0 res = new AddMyChoiceLeadRes_type0();
			res = add_mychoice_res_msg.getAddMyChoiceLeadRes();
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>MyChoiceFunction</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<applicationRefNumber>" + res.getApplicationRefNumber()+"</applicationRefNumber>"+
						"</Output>";
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>MyChoiceFunction</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><Status>ERROR</Status><td>Unable to add MyChoice Function app data.</td></Output>";
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDesc=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_ADDCUST</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to add MyChoice  Function app data.</Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_ADDCUST</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to add MyChoice Function app data.</Output>";
			}

			LogGEN.writeTrace("CBG_Log","outputXML : finally :"+sOutput);


			String Status="";
			if(sReturnCode.equalsIgnoreCase("0"))
			{
				Status="Success";
			}
			else if(sReturnCode.equalsIgnoreCase("2"))
				Status="Pratial Success";
			else
				Status="Failure";

			try {

			} catch (Exception e) {

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

			DBConnection con=new DBConnection();
			String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type1+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
			LogGEN.writeTrace("CBG_Log","Modify Cudtomer  Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","sOrg_put : finally :"+sOrg_put);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_put.replaceAll("'", "''"));
			} catch (Exception e2) {
				LogGEN.writeTrace("CBG_Log","ModifyCudtomer  Exception: finally :"+e2.getStackTrace());
			}
			return sOutput;			
		}
	}
}