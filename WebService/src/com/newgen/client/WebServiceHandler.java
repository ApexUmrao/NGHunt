package com.newgen.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.newgen.AESEncryption;
import com.newgen.cbg.client.CBGServiceHandler;
import com.newgen.dscop.client.DSCOPServiceHandler;
import com.newgen.ws2.client.WebServiceHandler_2;

public class WebServiceHandler 
{	
	@SuppressWarnings("rawtypes")
	public static Map currentCabPropertyMap=new HashMap();
	public String loggedinuser="";
	
/**
 * Function written to read property file
 * @author gupta.ashish
 * 
 */
	
	@SuppressWarnings("unchecked")
	public void readCabProperty(String sectionName) throws Exception
	{
		currentCabPropertyMap.clear();
		RandomAccessFile raf;
		String INI="Config.properties";
		LogGEN.writeTrace("Log","Section readCabProperty---");
		System.out.println("Section readCabProperty---");
		try
		{
			raf= new RandomAccessFile(INI,"rw");
			for(String line = raf.readLine(); line != null; line = raf.readLine())
			{
				if(line==null||line.equalsIgnoreCase(""))
					continue;
				line = line.trim();
				
				if(line.charAt(0) != '[' || line.charAt(line.length() - 1) != ']' || !line.substring(1, line.length() - 1).equalsIgnoreCase(sectionName))
				{
					continue;
				}
				
				String sSection=line.substring(1,line.length()-1);
				LogGEN.writeTrace("Log","Section Called---"+sSection);
				LogGEN.writeTrace("Log","Section Name from Config---"+sectionName);
				
				System.out.println("Section Called---"+sSection);
				System.out.println("Section Name from Config---"+sectionName);
				
				if(sSection.equalsIgnoreCase(sectionName))
				{
					LogGEN.writeTrace("Log","Section  Matched for "+sectionName);
					System.out.println("Section  Matched for "+sectionName);
					for(line = raf.readLine(); line != null; line = raf.readLine())
					{
						if(line==null||line.equalsIgnoreCase(""))
							continue;
						
						line = line.trim();
						
						if(line.charAt(0) == '[')
						{
							break;
						}
						int i = line.indexOf('=');
						
						String temp="";
						temp = line.substring(0, i);
						currentCabPropertyMap.put(temp.trim(),line.substring(i+1));
					}
					LogGEN.writeTrace("Log","End Section  Matched for "+sectionName);
					System.out.println("End Section  Matched for "+sectionName);
					break;
				}
			}
			raf.close();
		}
		catch(Exception e)
		{
			LogGEN.writeTrace("Log", "ReadPRoperty Eceptioon "+e);
		}
	 }
	
	@SuppressWarnings("unchecked")
	public void readCabPropertyOffshore(String sectionName) throws IOException
	{
		currentCabPropertyMap.clear();
		RandomAccessFile raf;
		String INI="defaultoutput.properties";
		
		try
		{
			LogGEN.writeTrace("Log", "readCabPropertyOffshore starts121212 ---");
			raf= new RandomAccessFile(INI,"rw");
			LogGEN.writeTrace("Log","inside readCabPropertyOffshore starts---before for loop");
			for(String line = raf.readLine(); line != null; line = raf.readLine())
			{
				if(line==null||line.equalsIgnoreCase(""))
					continue;
				LogGEN.writeTrace("Log","inside readCabPropertyOffshore starts--- before line.trim()"+line);
				line = line.trim();
				LogGEN.writeTrace("Log","inside readCabPropertyOffshore starts--- after line.trim()"+line);
				if(line.charAt(0) != '[' || line.charAt(line.length() - 1) != ']' || !line.substring(1, line.length() - 1).equalsIgnoreCase(sectionName))
				{
					continue;
				}
				
				String sSection=line.substring(1,line.length()-1);
				LogGEN.writeTrace("Log", "mohit11--sSection---"+sSection);
				
				if(sSection.equalsIgnoreCase(sectionName))
				{
					for(line = raf.readLine(); line != null; line = raf.readLine())
					{
						if(line==null||line.equalsIgnoreCase(""))
							continue;
						
						line = line.trim();
						if(line.charAt(0) == '[')
						{
							break;
						}
						int i = line.indexOf('=');
						String temp="";
						temp = line.substring(0, i);
						
						currentCabPropertyMap.put(temp.trim(),line.substring(i+1));
						LogGEN.writeTrace("Log", (String)currentCabPropertyMap.get("Output"));
					}
					break;
				}
			}
			LogGEN.writeTrace("Log","inside readCabPropertyOffshore starts---after for loop");
			raf.close();
		}
		catch(Exception e)
		{
			LogGEN.writeTrace("Log", "ReadPRopertyOffshore Exception "+e);
		}
	 }

	public String callType(String sInputXML) throws Exception
	{
		String inputxml=sInputXML;	
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(inputxml);
		
		inputxml=inputxml.replaceAll("\r\n","");
		
		String CallType=xmlDataParser.getValueOf("Calltype");
		LogGEN.writeTrace("Log", "CallType VALUE "+CallType);
		LogGEN.writeTrace("CBG_Req_log", "*****Calling CBG Handler ***** "+ xmlDataParser.getValueOf("WINAME") + " call => "+xmlDataParser.getValueOf("CBGCallType"));
		LogGEN.writeTrace("DSCOP_Req_log", "*****Calling DSCOP Handler ***** "+ xmlDataParser.getValueOf("WINAME") + " call => "+xmlDataParser.getValueOf("DSCOPCallType"));

		try
		{
		//Change for CBG 		
		if("CBG".equalsIgnoreCase(CallType)){
			LogGEN.writeTrace("CBG_Req_log", "*****Calling CBG Handler 2 ***** "+ xmlDataParser.getValueOf("WINAME") + " call => "+xmlDataParser.getValueOf("CBGCallType"));				
			try {		
				CBGServiceHandler  cbgHandler= new CBGServiceHandler();				
				return cbgHandler.callHandler(inputxml);
			} catch (Exception e) {
				LogGEN.writeTrace("CBG_Req_log", "Exception Before calling CBG Handler "+ e.getMessage());
			}
		}
			readCabProperty("JTS");	
			LogGEN.writeTrace("Log","Reading Environment Property");
			readCabProperty("ENVIRONMENT");
		}
		catch(Exception e)
		{	
			LogGEN.writeTrace("Log", "Exception in CallType() while reading ENVIRONMENT cab:"+e);
		}

		///ADDED BY SHIVANSHU
		try
		{
		//Change for DSCOP
		if("DSCOP".equalsIgnoreCase(CallType)){
			LogGEN.writeTrace("DSCOP_Req_log", "*****Calling DSCOP Handler 2 ***** "+ xmlDataParser.getValueOf("WI_NAME") 
			                   + " call => "+xmlDataParser.getValueOf("DSCOPCallType"));
			try {
				DSCOPServiceHandler  dscopHandler= new DSCOPServiceHandler();
				return dscopHandler.callHandler(inputxml);
			} catch (Exception e) {
				LogGEN.writeTrace("DSCOP_Req_log", "Exception Before calling DSCOP Handler "+ e.getMessage());
			}
		}
			readCabProperty("JTS");
			LogGEN.writeTrace("Log","Reading Environment Property");
			readCabProperty("ENVIRONMENT");
		}
		catch(Exception e)
		{
			LogGEN.writeTrace("Log", "Exception in CallType() while reading ENVIRONMENT cab:"+e);
		}
        ///ENDED BY SHIVANSHU
		
		String location=(String)currentCabPropertyMap.get("location");
		LogGEN.writeTrace("Log","Location is: "+location);
		
		if(location != null && "offshore".equalsIgnoreCase(location)){
			String output;
			
			String InnerCallType="";
		try
		{
		InnerCallType=xmlDataParser.getValueOf("InnerCallType");
		LogGEN.writeTrace("Log", "InnerCallType VALUE "+InnerCallType);
		}
		catch(Exception e)
		{	
			LogGEN.writeTrace("Log", "Exception in InnerCallType while reading ENVIRONMENT cab:"+e);
		}
		
			try
			{
				if(CallType.equalsIgnoreCase("Customer_Information"))
				{
					readCabPropertyOffshore(xmlDataParser.getValueOf("txnType"));
				}
				else if(CallType.equalsIgnoreCase("Individual_Risk")|| CallType.equalsIgnoreCase("Eligibility_Analysis")|| CallType.equalsIgnoreCase("Product_Eligibility"))
				{
					readCabPropertyOffshore(CallType);
				} 
				else if (CallType.equalsIgnoreCase("CreateCustomerID_COB")|| CallType.equalsIgnoreCase("SaveFATCADetails_COB")) {
					readCabPropertyOffshore(CallType);
				}
				else if (InnerCallType.equalsIgnoreCase("WBG_BRMS_RULES_RESPONSE") || InnerCallType.equalsIgnoreCase("WBG_ECB_REPORT_DETAILS") || InnerCallType.equalsIgnoreCase("WBG_Modify_Customer")) {
					readCabPropertyOffshore(InnerCallType);
				}
				else
				{
					Random random_number= new Random();
					int x=random_number.nextInt(100);
					System.out.println("Random Number: "+x);
					readCabPropertyOffshore(CallType+"_Pass");
				}
				
			}
			catch(Exception e)
			{
				LogGEN.writeTrace("Log", "Exception in CallType() in offshore block:"+e);
			}
			
			if(CallType.equalsIgnoreCase("Customer_MIS_Information"))
			{
				String id=xmlDataParser.getValueOf("CUST_ID");
				output=(String)currentCabPropertyMap.get("Output");
				System.out.println("Offshore output success:  "+output);
				 
				xmlDataParser.setInputXML(output);
				output = output.replaceAll(xmlDataParser.getValueOf("CUST_ID"),id);
				LogGEN.writeTrace("Log", "Final Ouptut:"+output);
				//System.out.println("Output after change: "+output);
				return output;		 
			}
			else if(CallType.equalsIgnoreCase("ADD_Account"))
			{
				String id=xmlDataParser.getValueOf("productCode");
				System.out.println("Offshore output success:  "+id);
				output=(String)currentCabPropertyMap.get("Output");
				System.out.println("Offshore output success:  "+output);
				 
				xmlDataParser.setInputXML(output);
				
				if(!xmlDataParser.getValueOf("productCode").equalsIgnoreCase(""))
				{
					output = output.replace(xmlDataParser.getValueOf("productCode"),id);
				}
				//System.out.println("Output after change: "+output);
				LogGEN.writeTrace("Log", "Final Ouptut:"+output);
				return output;		 
			}
			else if(CallType.equalsIgnoreCase("Customer_Information"))
			{
				 String id=xmlDataParser.getValueOf("CUST_ID");				 				 
				 String TypeOfTxn=xmlDataParser.getValueOf("txnType");
				
				 if(TypeOfTxn.equalsIgnoreCase("GetCustomerSummary"))
				 {
					output=(String)currentCabPropertyMap.get("Output");
					//return output;
				 }
				 else
				 {
					output=(String)currentCabPropertyMap.get("Output");
					 
					xmlDataParser.setInputXML(output);
					output = output.replaceAll(xmlDataParser.getValueOf("CUST_ID"),id);
					System.out.println("Output after change: "+output);
					//return output;
				 }
				 LogGEN.writeTrace("Log", "Final Ouptut:"+output);
				 return output;
			}
			else if(CallType.equalsIgnoreCase("Eligibility_Analysis"))
			{
				LogGEN.writeTrace("Log", "Eligibility_Analysis");		
				EligibilityAnalysis elig = new EligibilityAnalysis();
				return elig.EligibilityAnalysisCalculate(inputxml);
			}
			else if(CallType.equalsIgnoreCase("DFCListFetch")){
				LogGEN.writeTrace("Log","mohit11=====inside DFCListFetch==== ");
				String cat=xmlDataParser.getValueOf("customerCategory");
				String grp=xmlDataParser.getValueOf("cardProductGroup");
				output=(String)currentCabPropertyMap.get("Output");
				LogGEN.writeTrace("Log","mohit11=====inside DFCListFetch==== Offshore output success:  DFCListFetch : "+output);
				 
				xmlDataParser.setInputXML(output);
				output = output.replaceAll(xmlDataParser.getValueOf("customerCategory"),cat);
				output=output.replaceAll(xmlDataParser.getValueOf("cardProductGroup"),grp);
				LogGEN.writeTrace("Log", "Final Ouptut:"+output);
				LogGEN.writeTrace("Log","mohit11=====inside DFCListFetch==== Output after change: "+output);
				return output;
			}
			else if(CallType.equalsIgnoreCase("Base24")){
				LogGEN.writeTrace("Log","mohit11=====inside Base24==== ");
				String card=xmlDataParser.getValueOf("DebitCardNumber");
				output=(String)currentCabPropertyMap.get("Output");
				LogGEN.writeTrace("Log","mohit11=====inside Base24====Offshore output success Base24:  "+output);
				 
				xmlDataParser.setInputXML(output);
				output = output.replaceAll(xmlDataParser.getValueOf("DebitCardNumber"),card);
				LogGEN.writeTrace("Log", "Final Ouptut:"+output);
				LogGEN.writeTrace("Log","mohit11=====inside Base24====OffshoreOutput after change: "+output);
				return output;
			}
			
				//ADDED BY : Nishant Parmar (24-02-2016) Begin ---
				else if(CallType.equalsIgnoreCase("CustEIDAInfo")){
					LogGEN.writeTrace("Log","Nishant24=====inside CustEIDAInfo==== ");
					String EIDANumber = xmlDataParser.getValueOf("EIDANum");

					output=(String)currentCabPropertyMap.get("Output");
					LogGEN.writeTrace("Log","Nishant24=====inside CustEIDAInfo====Offshore output success:  "+output);
					 
					xmlDataParser.setInputXML(output);
					output = output.replaceAll(xmlDataParser.getValueOf("eidaNo"),EIDANumber);
					
					LogGEN.writeTrace("Log", "Final Ouptut of CustEIDAInfo : "+output);
					LogGEN.writeTrace("Log","Nishant24=====inside CustEIDAInfo====Output after change: "+output);
					return output;
				}
				else if(CallType.equalsIgnoreCase("CustomerInfo")){
					LogGEN.writeTrace("Log","Nishant24=====inside CustomerInfo==== ");
					String EIDANumber = xmlDataParser.getValueOf("EIDANum");

					output=(String)currentCabPropertyMap.get("Output");
					LogGEN.writeTrace("Log","Nishant24=====inside CustomerInfo====Offshore output success:  "+output);
					 
					xmlDataParser.setInputXML(output);
					output = output.replaceAll(xmlDataParser.getValueOf("eidaNo"),EIDANumber);
					
					LogGEN.writeTrace("Log", "Final Ouptut of CustomerInfo : "+output);
					LogGEN.writeTrace("Log","Nishant24=====inside CustomerInfo====Output after change: "+output);
					return output;
				}
				else if(CallType.equalsIgnoreCase("ChqLeavesIssue")){
					LogGEN.writeTrace("Log","Nishant24=====inside ChqLeavesIssue==== ");
					String custId = xmlDataParser.getValueOf("CUST_ID");

					output=(String)currentCabPropertyMap.get("Output");
					LogGEN.writeTrace("Log","Nishant24=====inside ChqLeavesIssue====Offshore output success:  "+output);
					 
					xmlDataParser.setInputXML(output);
					output = output.replaceAll(xmlDataParser.getValueOf("CUST_ID"),custId);
					
					LogGEN.writeTrace("Log", "Final Ouptut of ChqLeavesIssue : "+output);
					LogGEN.writeTrace("Log","Nishant24=====inside ChqLeavesIssue====Output after change: "+output);
					return output;
				} 
				else if(CallType.equalsIgnoreCase("CreateCustomerID_COB"))
				{
					LogGEN.writeTrace("Log","Mukul=====--CreateCustomerID_COB==== ");
					output=(String)currentCabPropertyMap.get("Output");
					LogGEN.writeTrace("Log","Mukul===== CreateCustomerID_COB==== Output"+output);
					return output;
				}
				else if(CallType.equalsIgnoreCase("SaveFATCADetails_COB"))
				{
					LogGEN.writeTrace("Log","Mukul=====--SaveFATCADetails_COB==== ");
					output=(String)currentCabPropertyMap.get("Output");
					LogGEN.writeTrace("Log","Mukul===== SaveFATCADetails_COB==== Output"+output);
					return output;
				}
				else if(CallType.equalsIgnoreCase("WS-2.0"))
				{
					LogGEN.writeTrace("Log","Mukul=====inside WS-2.0==== ");
					if(InnerCallType.equalsIgnoreCase("WBG_BRMS_RULES_RESPONSE"))
					{
						LogGEN.writeTrace("Log","Mukul=====inside WS-2.0--WBG_BRMS_RULES_RESPONSE==== ");
						output=(String)currentCabPropertyMap.get("Output");
						LogGEN.writeTrace("Log","Mukul===== WBG_BRMS_RULES_RESPONSE==== Output"+output);
						return output;
					}
					else if(InnerCallType.equalsIgnoreCase("WBG_ECB_REPORT_DETAILS"))
					{
						LogGEN.writeTrace("Log","Mukul=====inside WS-2.0--WBG_ECB_REPORT_DETAILS==== ");
						output=(String)currentCabPropertyMap.get("Output");
						LogGEN.writeTrace("Log","Mukul===== WBG_ECB_REPORT_DETAILS==== Output"+output);
						return output;
					}
					else if(InnerCallType.equalsIgnoreCase("WBG_Modify_Customer"))
					{
						LogGEN.writeTrace("Log","Mukul=====inside WS-2.0--WBG_Modify_Customer==== ");
						output=(String)currentCabPropertyMap.get("Output");
						LogGEN.writeTrace("Log","Mukul===== WBG_Modify_Customer==== Output"+output);
						return output;
					}
					else
					{
						return "";
					}
				}
				//--- End
			//--END
			
			else
			{
				output=(String)currentCabPropertyMap.get("Output");
				LogGEN.writeTrace("Log", "Final Ouptut else block:"+output);
				return output;
			}	
			
		}
		else
		{ //code in else already exist
			if(CallType.equalsIgnoreCase("TemplateGeneration")) {	
				LogGEN.writeTrace("Log", "TemplateGeneration");		
				TemplateGeneration templateGeneration = new TemplateGeneration();
				return templateGeneration.generateTemplate(inputxml);
			} else if(CallType.equalsIgnoreCase("Customer_Information"))
			{
				LogGEN.writeTrace("Log", "Customer_Information");		
				CustomerInformation Cust_Info= new CustomerInformation();
				return Cust_Info.FetchCustomerInformation(inputxml);	
			}
			else if(CallType.equalsIgnoreCase("Customer_MIS_Information"))
			{
				LogGEN.writeTrace("Log", "Customer_MIS_Information");	
				
				CustomerMisDetails Cust_MIS_Info= new CustomerMisDetails();
				return Cust_MIS_Info.FetchCustomerMISInformation(inputxml);	
			}
			else if(CallType.equalsIgnoreCase("Debit_Card_Information"))
			{
				LogGEN.writeTrace("Log", "Debit_Card_Information");		
				DebitCardDetails Debit_Card_Info= new DebitCardDetails();
				return Debit_Card_Info.FetchDebitCardDetails(inputxml);	
			}
			else if(CallType.equalsIgnoreCase("ADD_Customer"))
			{
				LogGEN.writeTrace("Log", "ADD_Customer");		
				AddCustomer add_cust= new AddCustomer();
				return add_cust.AddCustomerToFCR(inputxml);	
			}
			else if(CallType.equalsIgnoreCase("ADD_Account"))
			{
				LogGEN.writeTrace("Log", "ADD_Account");		
				AddCASAAccount add_acc= new AddCASAAccount();
				return add_acc.AddAccountToFCR(inputxml);	
			}
			else if(CallType.equalsIgnoreCase("Etihad_Guest_Information"))
			{
				LogGEN.writeTrace("Log", "Etihad_Guest_Information");		
				EtihadEnquiry etihad_enq= new EtihadEnquiry();
				return etihad_enq.inqEtidahGuest(inputxml);	
			}
			else if(CallType.equalsIgnoreCase("ADD_ETIHAD_GUEST"))
			{
				LogGEN.writeTrace("Log", "ADD_ETIHAD_GUEST");		
				AddEtihadGuest etihad_enq= new AddEtihadGuest();
				return etihad_enq.Add_Etihad_Guest(inputxml);	
			}
			else if(CallType.equalsIgnoreCase("Modify_Customer"))
			{
				LogGEN.writeTrace("Log", "Modify_Customer");		
				ModifyCustomer mod_cust= new ModifyCustomer();
				return mod_cust.ModifyCustomerToFCR(inputxml);	
			}
			else if(CallType.equalsIgnoreCase("Search_By_Debit"))
			{
				LogGEN.writeTrace("Log", "Search_By_Debit");		
				inqDebitCard debit_inq= new inqDebitCard();
				return debit_inq.DebitInquiry(inputxml)	;
			}
			else if(CallType.equalsIgnoreCase("Lead_Generation"))
			{
				LogGEN.writeTrace("Log", "Lead_Generation");		
				Lead_Generation lead= new Lead_Generation();
				return lead.Generate_Lead(inputxml);
			}
			else if(CallType.equalsIgnoreCase("Add_Debit_Card_Issuence"))
			{
				System.out.println("mohit11-----inside Add_Debit_Card_Issuence");
				LogGEN.writeTrace("Log", "Add_Debit_Card_Issuence");
				System.out.println("mohit11-----after LogGEN.writeTrace Add_Debit_Card_Issuence");
				AddDebitCard add_debit_card= new AddDebitCard();
				System.out.println("mohit11-----after LogGEN.writeTrace Add_Debit_Card_Issuence inputxmlis"+inputxml);
				return add_debit_card.add_debit_request(inputxml);
			}
			else if(CallType.equalsIgnoreCase("Modify_Debit_Card"))
			{
				LogGEN.writeTrace("Log", "Modify_Debit_Card");		
				ModifyDebitCard mod_debit= new ModifyDebitCard();
				return mod_debit.Modify_Debit_Card(inputxml);
			}
			else if(CallType.equalsIgnoreCase("Add_SMS_Reg"))
			{
				LogGEN.writeTrace("Log", "Modify_Debit_Card");		
				ModSMSReg mod_sms= new ModSMSReg();
				return mod_sms.add_sms_reg(inputxml);
			}
			else if(CallType.equalsIgnoreCase("Card_Shipment_Enquiry"))
			{
				LogGEN.writeTrace("Log", "Card_Shipment_Enquiry");		
				inqShipment ship= new inqShipment();
				return ship.Card_Shipment_Enquiry(inputxml);
			}
			else if(CallType.equalsIgnoreCase("ADD_Shipment"))
			{
				LogGEN.writeTrace("Log", "ADD_Shipment");		
				AddShipment ship= new AddShipment();
				return ship.Add_Shipment(inputxml);
			}
			else if(CallType.equalsIgnoreCase("SEND_SMS"))
			{
				LogGEN.writeTrace("Log", "ADD_Shipment");		
				SendSMS sms= new SendSMS();
				return sms.send_sms(inputxml);
			}
			else if(CallType.equalsIgnoreCase("Modify_Account"))
			{
				LogGEN.writeTrace("Log", "Modify_Account");		
				modifyCustAcctDetails acc= new modifyCustAcctDetails();
				return acc.modify_account(inputxml);
			}
			else if(CallType.equalsIgnoreCase("Black_List"))
			{
				LogGEN.writeTrace("Log", "Black_List");		
				inqBlackList black= new inqBlackList();
				return black.getBlackList(inputxml);
			}
			else if(CallType.equalsIgnoreCase("Funds_Transfer"))
			{
				LogGEN.writeTrace("Log", "Black_List");		
				FundsTransfer funds= new FundsTransfer();
				return funds.transfer_funds(inputxml);
			}
			else if(CallType.equalsIgnoreCase("Rate_Convert"))
			{
				LogGEN.writeTrace("Log", "Black_List");		
				ConversionRate convert= new ConversionRate();
				return convert.rate_convert(inputxml);
			}
			else if(CallType.equalsIgnoreCase("Individual_Risk"))
			{
				LogGEN.writeTrace("Log", "Individual_Risk");		
				IndividualRisk iRisk = new IndividualRisk();
				return iRisk.IndividualRiskCalculate(inputxml);
			}
			else if(CallType.equalsIgnoreCase("Cheque_Book"))
			{
				LogGEN.writeTrace("Log", "Cheque_Book");		
				ChequeBook cheque = new ChequeBook();
				return cheque.addCheque(inputxml);
			}
			else if(CallType.equalsIgnoreCase("email_subcription"))
			{
				LogGEN.writeTrace("Log", "email_subcription");		
				ESubcription email_sub = new ESubcription();
				return email_sub.email_subcription(inputxml);
			}
			else if(CallType.equalsIgnoreCase("cust_banca"))
			{
				LogGEN.writeTrace("Log", "cust_banca");		
				inqBancaService Banca_inq = new inqBancaService();
				return Banca_inq .banca_service(inputxml);
			}
			else if(CallType.equalsIgnoreCase("estatement_registration"))
			{
				LogGEN.writeTrace("Log", "estatement_registration");		
				ModifyEstatement e_sub = new ModifyEstatement();
				return e_sub.email_subcription(inputxml);
			}
			else if(CallType.equalsIgnoreCase("Eligibility_Analysis"))
			{
				LogGEN.writeTrace("Log", "Eligibility_Analysis");		
				EligibilityAnalysis elig = new EligibilityAnalysis();
				return elig.EligibilityAnalysisCalculate(inputxml);
			}
			else if(CallType.equalsIgnoreCase("Product_Eligibility"))
			{
				LogGEN.writeTrace("Log", "Product_Eligibility");		
				ProductEligibility prod = new ProductEligibility();
				return prod.ProductEligibilityCalculate(inputxml);
			}
			else if(CallType.equalsIgnoreCase("EMDS_DOCS"))
			{
				LogGEN.writeTrace("Log", "EMDS_DOCS");		
				EDMSDOC prod = new EDMSDOC();
				return prod.GetEDMSQuery(inputxml);
			}
			else if(CallType.equalsIgnoreCase("CRM_UPDATE"))
			{
				LogGEN.writeTrace("Log", "CRM_UPDATE");		
				CRMUpdate crm = new CRMUpdate();
				inputxml=xmlDataParser.getValueOf("inputXML");			
				return crm.updateData(inputxml);
			}		
			else if(CallType.equalsIgnoreCase("CreateCustomerID_COB"))
			{
				LogGEN.writeTrace("COB_Log", "Inside WebServiceHandler-CreateCustomerID");		
				CreateCustomerID_COB target = new CreateCustomerID_COB();
				LogGEN.writeTrace("COB_Log", "Returning to WFCUSTOM for call CreateCustomerID");
				return target.createCustomerIDInFCR_COB(inputxml);
			}	
			else if(CallType.equalsIgnoreCase("FetchCRSDetails"))    //added by harinder as per requirement for 6.1 module
			{
				LogGEN.writeTrace("Log", "FetchCRSDetails");		
				FetchCRSDetails FetchCRS = new FetchCRSDetails();
				return FetchCRS.fetchCRSDetails(inputxml);
			}
			else if(CallType.equalsIgnoreCase("FetchLAPSResult"))   //added by harinder as per requirement for 6.11 module
			{
				LogGEN.writeTrace("Log", "FetchLAPSResult");		
				InqCustApplicationDtl fetchLAPSResult = new InqCustApplicationDtl();
				return fetchLAPSResult.dedupeEIDALaps(inputxml);
			}	
			else if(CallType.equalsIgnoreCase("FetchCustomerBalance"))  //added by harinder as per requirement for 6.9 module
			{
				LogGEN.writeTrace("Log", "FetchCustomerBalance");		
				FetchCustomerBalance fetchCustomerBalance = new FetchCustomerBalance();
				return fetchCustomerBalance.fetchAccBalance(inputxml);
			}	
			else if(CallType.equalsIgnoreCase("FetchFATCADetails"))
			{
				LogGEN.writeTrace("Log", "FetchFATCADetails");		
				FetchFATCADetails FetchFatca = new FetchFATCADetails();
				return FetchFatca.getFATCADetails(inputxml);
			}
			else if(CallType.equalsIgnoreCase("ValidateFATCAMini"))
			{
				LogGEN.writeTrace("Log", "ValidateFATCAMini");		
				ValidateFATCAMini MiniFatca = new ValidateFATCAMini();
				return MiniFatca.getFATCAStatus(inputxml);
			}
			else if(CallType.equalsIgnoreCase("ValidateFATCAMain"))
			{
				LogGEN.writeTrace("Log", "ValidateFATCAMain");		
				ValidateFATCAFull MainFatca = new ValidateFATCAFull();
				return MainFatca.getFATCAStatus(inputxml);
			}			
			else if(CallType.equalsIgnoreCase("SaveFATCADetails"))
			{
				LogGEN.writeTrace("Log", "SaveFATCADetails");		
				SaveFATCADetails SaveFatca = new SaveFATCADetails();
				return SaveFatca.saveFATCADetailsStatus(inputxml);
			}			
			else if(CallType.equalsIgnoreCase("SaveEIDADetails"))
			{
				LogGEN.writeTrace("Log", "SaveEIDADetails");		
				SaveEIDADetails SaveFatca = new SaveEIDADetails();
				return SaveFatca.saveEIDADetailsStatus(inputxml);
			}			
			else if(CallType.equalsIgnoreCase("SaveKYCDetails"))
			{
				LogGEN.writeTrace("Log", "SaveKYCDetails");		
				SaveKYCDetails SaveFatca = new SaveKYCDetails();
				return SaveFatca.saveKYCDetailsStatus(inputxml);
			}			
			else if(CallType.equalsIgnoreCase("SaveNomineeDetails"))
			{
				LogGEN.writeTrace("Log", "SaveFATCADetails");		
				SaveNomineedDetails SaveFatca = new SaveNomineedDetails();
				return SaveFatca.saveNomineeDetailsStatus(inputxml);
			}
			else if(CallType.equalsIgnoreCase("ValidateFATCAMini_COB"))
			{
				LogGEN.writeTrace("COB_Log", "Inside WebServiceHandler-ValidateFATCAMini_COB");		
				ValidateFATCAMini_COB target = new ValidateFATCAMini_COB();
				LogGEN.writeTrace("COB_Log", "Returning to WFCUSTOM for call ValidateFATCAMini_COB");
				return target.getFATCAStatus(inputxml);
			}
			else if(CallType.equalsIgnoreCase("ValidateFATCAFull_COB"))
			{
				LogGEN.writeTrace("COB_Log", "Inside WebServiceHandler-ValidateFATCAFull_COB");		
				ValidateFATCAFull_COB target = new ValidateFATCAFull_COB();
				LogGEN.writeTrace("COB_Log", "Returning to WFCUSTOM for call ValidateFATCAFull_COB");
				return target.getFATCAStatus(inputxml);
			}
			else if(CallType.equalsIgnoreCase("SaveFATCADetails_COB"))
			{
				LogGEN.writeTrace("COB_Log", "Inside WebServiceHandler-SaveFATCADetails_COB");		
				SaveFATCADetails_COB target = new SaveFATCADetails_COB();
				LogGEN.writeTrace("COB_Log", "Returning to WFCUSTOM for call SaveFATCADetails_COB");
				return target.saveFATCADetailsStatus(inputxml);
			}
			else if(CallType.equalsIgnoreCase("SignatureUpoadToFlexSystem_COB"))
			{
				LogGEN.writeTrace("COB_Log", "Inside WebServiceHandler-SignatureUpoadToFlexSystem_COB");		
				modifyCustAcctDetails_COB addSignature = new modifyCustAcctDetails_COB();
				LogGEN.writeTrace("COB_Log", "Returning to WFCUSTOM for call SignatureUpoadToFlexSystem_COB");
				return addSignature.modify_account(inputxml);
				// OLD FILE
				//SignatureUploadToFlex_COB target = new SignatureUploadToFlex_COB();	
				//target.signatureInjection(inputxml);
			}
			else if(CallType.equalsIgnoreCase("Application_Eligibility"))
			{
				LogGEN.writeTrace("Log", "Application_Eligibility");		
				Application_Eligibility AppElig = new Application_Eligibility();
				return AppElig.ApplicationEligibilityCalculate(inputxml);
			}
			else if(CallType.equalsIgnoreCase("PasswordDecrypt"))
			{
				LogGEN.writeTrace("Log", "PasswordDecrypt");		
				return getDecryptedPassword(inputxml);
			}
			
			//Additions by Nishant Parmar (27-01-2016):
			//BEGIN--
			
			else if(CallType.equalsIgnoreCase("DebitCard_New")){
				System.out.println("mohit11----inside DebitCard_New --- ");
				LogGEN.writeTrace("Log", "DebitCard_New");
				System.out.println("mohit11----after LogGEN.writeTrace DebitCard_New --- ");
				AddDebitCardIssue addDebitCardIssue = new AddDebitCardIssue();
				System.out.println("mohit11----after addDebitCardIssue DebitCard_New inputxmlis ----"+inputxml);
				return addDebitCardIssue.AddDebitCards(inputxml);
			}
			else if(CallType.equalsIgnoreCase("InqDebitCardPrintStatus")){
				LogGEN.writeTrace("Log", "*****InqDebitCardPrintStatus*****");
				InqSBKDebitCards inqSBKDebitCards = new InqSBKDebitCards();
				return inqSBKDebitCards.SBKDebitEnquiry(inputxml);
			}
			//("InstantDebitCard")){
			else if(CallType.equalsIgnoreCase("DFCListFetch")){
				System.out.println("mohit11----inside DFCListFetch --- ");
				LogGEN.writeTrace("Log", "DFCListFetch");
				System.out.println("mohit11----after LogGEN.writeTrace DFCListFetch --- ");
				InqSBKDebitCards inqSBKDebitCards = new InqSBKDebitCards();
				System.out.println("mohit11----after inqSBKDebitCards DFCListFetch inputxmlis ----"+inputxml);
				return inqSBKDebitCards.SBKDebitEnquiry(inputxml);
			}
			else if(CallType.equalsIgnoreCase("Base24")){
				System.out.println("mohit11----inside Base24 --- ");
				LogGEN.writeTrace("Log", "Base24");
				System.out.println("mohit11----fter LogGEN.writeTrace Base24 --- ");
				ModSBKDebitCards modSBKDebitCards = new ModSBKDebitCards();
				System.out.println("mohit11----after modSBKDebitCards Base24 inputxmlis ----"+inputxml);
				return modSBKDebitCards.ModifySBKDebitCards(inputxml);
			}
			else if(CallType.equalsIgnoreCase("ModDebitCardPrintStatus")){
				LogGEN.writeTrace("Log", "*****ModDebitCardPrintStatus*****");
				ModSBKDebitCards modSBKDebitCards = new ModSBKDebitCards();
				return modSBKDebitCards.ModifySBKDebitCards(inputxml);
			}
				//NEW ADDITIONS ON (24-02-2016) BEGIN ---
			else if(CallType.equalsIgnoreCase("ChqLeavesIssue")){
				LogGEN.writeTrace("Log", "*****ChqLeavesIssue*****");
				InqSBKCustomer inqSBKCustomer = new InqSBKCustomer();
				return inqSBKCustomer.SBKCustomer(inputxml);
			}
			else if(CallType.equalsIgnoreCase("CustEIDAInfo")){
				LogGEN.writeTrace("Log", "*****CustEIDAInfo*****");
				InqSBKCustomer inqSBKCustomer = new InqSBKCustomer();
				return inqSBKCustomer.SBKCustomer(inputxml);
			}
			else if(CallType.equalsIgnoreCase("CustomerInfo")){
				LogGEN.writeTrace("Log", "*****CustomerInfo*****");
				InqSBKCustomer inqSBKCustomer = new InqSBKCustomer();
				return inqSBKCustomer.SBKCustomer(inputxml);
			}
			//Added on 05-04-2016:
			else if(CallType.equalsIgnoreCase("KioskCustActivities")){
				LogGEN.writeTrace("Log", "*****CustomerInfo*****");
				InqSBKCustomer inqSBKCustomer = new InqSBKCustomer();
				return inqSBKCustomer.SBKCustomer(inputxml);
			}
			//Added Below code for WBG 04/02/2018
			else if(CallType.equalsIgnoreCase("AddCRS")){
				LogGEN.writeTrace("Log", "*****CRS*****");
				WBG_AddCRS addCRS= new WBG_AddCRS();
				return addCRS.addCRS(inputxml);
			}
			else if(CallType.equalsIgnoreCase("ADD_Entity_Customer_WBG") 
					|| CallType.equalsIgnoreCase("ADD_Customer_AUS_WBG")){
				LogGEN.writeTrace("Log", "*****ADD_Customer_WBG*****");
				WBG_AddCustomer addCustomer= new WBG_AddCustomer();
				return addCustomer.addCustomer(sInputXML);
			}
			else if(CallType.equalsIgnoreCase("SaveFatca_WBG")){
				LogGEN.writeTrace("Log", "*****SaveFatca_WBG*****");
				WBG_AddFatca addFatca= new WBG_AddFatca();
				return addFatca.addFatcaDtls(inputxml);
			}			
			else if(CallType.equalsIgnoreCase("Fetch_Doc_Tracker")){
				LogGEN.writeTrace("Log", "*****Fetch Document Tracker *****");
				WBGDocumentTracker  fetchDocTrc= new WBGDocumentTracker();
				return fetchDocTrc.fetchDocument(inputxml);
			}else if(CallType.equalsIgnoreCase("Add_Doc_tracker")){
				LogGEN.writeTrace("Log", "*****Add Document Tracker *****");
				WBGAddDocTracker  addDocTrc= new WBGAddDocTracker();
				return addDocTrc.addDocTracker(sInputXML);
			}else if(CallType.equalsIgnoreCase("Mod_Doc_Tracker")){
				LogGEN.writeTrace("Log", "*****Modify Document Tracker *****");
				WBGModDocTracker  modDocTrc= new WBGModDocTracker();
				return modDocTrc.modDocTracker(sInputXML);
			}//End code for WBG 04/02/2018
			else if(CallType.equalsIgnoreCase("TFO_AcctDetailsICCS")){
				LogGEN.writeTrace("Log", "*****TFO_AcctDetailsICCS *****");
				TFO_AcctDetailsICCS  tfo_AcctDetailsICCS= new TFO_AcctDetailsICCS();
				return tfo_AcctDetailsICCS.FetchAcctDetailsICCS(sInputXML);
			}//End code for TFO 09/09/2018 
			else if(CallType.equalsIgnoreCase("TFO_CustSignInfo")){
				LogGEN.writeTrace("Log", "*****TFO_CustSignInfo *****");
				TFO_CustSignatureInfo  tfo_CustSignatureInfo= new TFO_CustSignatureInfo();
				return tfo_CustSignatureInfo.fetchSignature(sInputXML);
			}//End code for TFO 09/09/2018 //TFO_CustSignInfo			
			else if("CALL_TRSD_CREATE".equalsIgnoreCase(CallType) || "CALL_TRSD_ENQUIRY".equalsIgnoreCase(CallType)){
				LogGEN.writeTrace("Log", "*****Calling TRSD Handler *****");
				TRSDCall  trsd= new TRSDCall();
				return trsd.callTRSD(inputxml);
			}
			else if(CallType.equalsIgnoreCase("ADD_CRS_DETAILS")){
				LogGEN.writeTrace("Log", "*****CRS*****");
				AddCRSDetails addCRS= new AddCRSDetails();
				return addCRS.fetchCRSDetails(inputxml);
			}
			else if(CallType.equalsIgnoreCase("FETCH_CUSTOMER_BALANCE")){
				LogGEN.writeTrace("Log", "*****FETCH_CUSTOMER_BALANCE*****");
				FetchCustomerBalance addCRS= new FetchCustomerBalance();
				return addCRS.fetchAccBalance(inputxml);
			}
			else if("WS-2.0".equalsIgnoreCase(CallType)){
				LogGEN.writeTrace("Log", "*****Calling WebService-2.0 *****");				 
				WebServiceHandler_2 webServiceHandler_2 = new WebServiceHandler_2();
				return webServiceHandler_2.executeInnerCalls(inputxml);				
			}
			else
			{
				return ""; 
			}
		}
		//return ""; 
	}
	
	public static String execute(String inputXMLString, String jtsIP, int jtsPort, int debug)
    throws IOException, Exception
  {
    String recieveBuffer = new String(new byte[0], "8859_1");
    int i = 0;
    Socket sock = null;
    while (i < 3)
    {
      try
      {
        sock = new Socket(jtsIP, jtsPort);
      }
      catch (Exception e)
      {
        ++i;
        if (i == 1)
        {
          GregorianCalendar gc = new GregorianCalendar();
          String strDate = gc.get(5) + "-" + (gc.get(2) + 1) + "-" + gc.get(1) + " " + gc.get(11) + ":" + gc.get(12);
          System.out.println("Input:"+inputXMLString);
          System.out.println("Error while connecting to JTS running at " + jtsIP + ":" + jtsPort + " at time " + strDate);
          ByteArrayOutputStream s = new ByteArrayOutputStream();
          PrintStream ps = new PrintStream(s);
          e.printStackTrace(ps);
          System.out.println(s.toString());
          if(s!=null){
          s.close();}
          if(ps!=null){
          ps.close(); }
        }
        if (i == 3)
          throw new Exception("Workflow Server Down");
        Thread.sleep(100L);
        
      }
     
    }

    long l1 = System.currentTimeMillis();
    XMLParser parser = new XMLParser();
    parser.setInputXML(inputXMLString);
    String tempName = parser.getValueOf("Option");

    recieveBuffer = readWriteObject(inputXMLString, sock);
    String strTemp = " Time Taken in " + tempName + " is  :   " + (System.currentTimeMillis() - l1);

    if (debug == 0) {
      System.out.println("\n Omniflow Web Client Call - " + strTemp + "\nData Sent in bytes= " + inputXMLString.length() + "   Data Received= " + recieveBuffer.length());
    }
    sock.close();

    if (debug == 1)
    {
      System.out.println(strTemp + "Data Sent in bytes= " + inputXMLString.length() + "   Data Received= " + recieveBuffer.length() +  "\n" +inputXMLString + "\n" + recieveBuffer + "\r\n\r\n");
    }

    return recieveBuffer;
}
private static String readWriteObject(String inputXMLString, Socket sock) throws IOException
{
	    DataOutputStream oOut = new DataOutputStream(new BufferedOutputStream(sock.getOutputStream()));
	    DataInputStream oIn = new DataInputStream(new BufferedInputStream(sock.getInputStream()));
	    byte[] SendStream = inputXMLString.getBytes("8859_1");
	    int strLen = SendStream.length;
	    oOut.writeInt(strLen);
	    oOut.write(SendStream, 0, strLen);
	    oOut.flush();
	    int length = 0;
	    length = oIn.readInt();
	    byte[] readStream = new byte[length];

	    oIn.readFully(readStream);
	    String stroutputXML = new String(readStream, "8859_1");

	    return stroutputXML;
}
	
	public String getMonthNumber(String e) 
	{
		String letter;		
		if(e.equalsIgnoreCase( "Jan")||e.equalsIgnoreCase( "janv."))  letter="01"; 
		else if(e.equalsIgnoreCase(  "Feb")||e.equalsIgnoreCase( "f�vr."))  letter="02"; 
		else if(e.equalsIgnoreCase( "Mar")||e.equalsIgnoreCase( "mars"))  letter="03"; 
		else if(e.equalsIgnoreCase( "Apr")||e.equalsIgnoreCase( "avr."))  letter="04"; 
		else if(e.equalsIgnoreCase( "May")||e.equalsIgnoreCase( "mai"))  letter="05"; 
		else if(e.equalsIgnoreCase( "Jun")||e.equalsIgnoreCase( "juin"))  letter="06"; 
		else if(e.equalsIgnoreCase( "Jul")||e.equalsIgnoreCase( "juil."))  letter="07"; 
		else if(e.equalsIgnoreCase( "Aug")||e.equalsIgnoreCase( "ao�t"))  letter="08"; 
		else if(e.equalsIgnoreCase( "Sep")||e.equalsIgnoreCase( "sept."))  letter="09"; 
		else if(e.equalsIgnoreCase( "Oct")||e.equalsIgnoreCase( "oct."))  letter="10"; 
		else if(e.equalsIgnoreCase( "Nov")||e.equalsIgnoreCase( "nov."))  letter="11"; 
		else   letter="12"; 	
		
		return letter;
	}
			
	public static void main(String ar[])  	
  	{
		int ref_no=0;
		try
		{
			FileReader file=new FileReader("ref.txt");
			char[] output=new char[100];
			int red= file.read(output);
			String xx="";
			for(int i=0;i<red;i++)
			{
				xx+=output[i];
			}
		
			System.out.println("refrence number::::"+xx);
			ref_no=Integer.parseInt(xx)+1;
			file.close();
			FileWriter writ=new FileWriter("ref.txt");
			writ.write(ref_no+"");
			writ.close();
		}
		catch(Exception e){}
		System.out.println("Utility Started..... ");
		LogGEN.writeTrace("Log", "Utility Started..... ");
		try
		{
			WebServiceHandler WSHandler = new WebServiceHandler();			
			WSHandler.callType("<?xml version=\"1.0\"?><APWebService_Input><Option>WebService</Option><Calltype>Application_Eligibility</Calltype><Customer><resident_Country>KUWAIT</resident_Country><empStatus>Employed</empStatus><nationality>NIGERIA</nationality><salary>34234</salary><tml_Nontml>No</tml_Nontml><REF_NO>82589</REF_NO><userName>CPD_MAKER1</userName><WiName>Account-0000003027-Opening</WiName></Customer><EngineName>activecab</EngineName><SessionId>583698877</SessionId></APWebService_Input>");
			System.out.println("Utility Stopped Successfully.");
			
		}
		catch(Exception ex)
		{
			System.out.println("******************************");
			System.out.println("********CHECK LOGS************");
			System.out.println("******************************");
			System.out.println("Utility stopped with Error.");
			ex.printStackTrace();
			LogGEN.writeTrace("Log", getStackTrace(ex));
		}
  	}	
	/*Function for writing StackTrace in file.*/
	public static String getStackTrace(Throwable aThrowable) 
	{
	    final Writer result = new StringWriter();
	    final PrintWriter printWriter = new PrintWriter(result);
	    aThrowable.printStackTrace(printWriter);
	    return result.toString();
	}
	
	public String getDecryptedPassword(String sInputXML)
	{
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		
		String sDecryptedPassword ="";
		try
		{
			sDecryptedPassword=AESEncryption.decrypt(xmlDataParser.getValueOf("Password"));
		}
		catch(Exception e)
		{
			getStackTrace(e);
		}
		
		return sDecryptedPassword;
	}
	
	public String setSenderId(String id){
		if(id == null || "null".equalsIgnoreCase(id.trim()) || "".equalsIgnoreCase(id.trim())){
			return "WMS";
		}else{
			return id;
		}		
	}
	
}

