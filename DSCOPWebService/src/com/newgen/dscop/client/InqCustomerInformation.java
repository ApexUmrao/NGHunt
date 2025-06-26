package com.newgen.dscop.client;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqCustomerInformationStub;
import com.newgen.dscop.stub.InqCustomerInformationStub.Accounts_type0;
import com.newgen.dscop.stub.InqCustomerInformationStub.Address_type0;
import com.newgen.dscop.stub.InqCustomerInformationStub.CreditCards_type0;
import com.newgen.dscop.stub.InqCustomerInformationStub.Customer_type0;
import com.newgen.dscop.stub.InqCustomerInformationStub.Customer_type0E;
import com.newgen.dscop.stub.InqCustomerInformationStub.GetCustomerInformationReqMsg;
import com.newgen.dscop.stub.InqCustomerInformationStub.GetCustomerInformationReq_type0;
import com.newgen.dscop.stub.InqCustomerInformationStub.GetCustomerInformationResMsg;
import com.newgen.dscop.stub.InqCustomerInformationStub.GetCustomerInformationRes_type0;
import com.newgen.dscop.stub.InqCustomerInformationStub.HeaderType;
import com.newgen.dscop.stub.InqCustomerInformationStub.ListOfAccounts_type0;
import com.newgen.dscop.stub.InqCustomerInformationStub.ListOfAddress_type0;
import com.newgen.dscop.stub.InqCustomerInformationStub.ListOfCreditCards_type0;
import com.newgen.dscop.stub.InqCustomerInformationStub.ListOfCustomer_type0;
import com.newgen.dscop.stub.InqCustomerInformationStub.ListOfCustomer_type0E;
import com.newgen.dscop.stub.InqCustomerInformationStub.ListOfUDFDetails_type0;
import com.newgen.dscop.stub.InqCustomerInformationStub.UDFDetails_type0;

public class InqCustomerInformation extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	
		@SuppressWarnings("finally")
		public String FetchCustomerInformation(String sInputXML) 
		{
			LogGEN.writeTrace("CBG_Log", "Fuction called CustomerInformation");
			LogGEN.writeTrace("CBG_Log", "sInputXML---"+sInputXML);
			XMLParser xmlDataParser = new XMLParser();
			xmlDataParser.setInputXML(sInputXML);
			String sOutput="";
			String sReturnCode= "";
			String sErrorDetail="";
			String sErrorDesc = "";
			String sOrg_Output="";			
			Date d = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String sDate = dateFormat.format(d);
			try
			{
				
				LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
				ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("InqCustomerInformation");
				LogGEN.writeTrace("CBG_Log", "InqCustomerInformation WebServiceConfig Map : "  +wsConfig.toString());
				sWSDLPath = (String)wsConfig.get(0);
				sCabinet = (String)wsConfig.get(1);
				sUser = (String)wsConfig.get(2);
				sLoginReq = Boolean.valueOf((String)wsConfig.get(3));
				sPassword = (String)wsConfig.get(4);
				lTimeOut = Long.valueOf((String)wsConfig.get(5));
				LogGEN.writeTrace("CBG_Log", "InqCustomerInformation WSDL PATH: "+sWSDLPath);
				LogGEN.writeTrace("CBG_Log", "InqCustomerInformation CABINET: "+sCabinet);
				LogGEN.writeTrace("CBG_Log", "InqCustomerInformation USER: "+sUser);
				LogGEN.writeTrace("CBG_Log", "InqCustomerInformation PASSWORD: "+sPassword);
				LogGEN.writeTrace("CBG_Log", "InqCustomerInformation LOGIN_REQ: "+sLoginReq);
				LogGEN.writeTrace("CBG_Log", "InqCustomerInformation TIME_OUT: "+lTimeOut);
				LogGEN.writeTrace("CBG_Log", "sDate---"+sDate);
				
				String sCustomerID= xmlDataParser.getValueOf("CUST_ID");
				String ref_no=xmlDataParser.getValueOf("REF_NO");
				sCabinet=xmlDataParser.getValueOf("EngineName");
				String senderId = xmlDataParser.getValueOf("SenderId");
				LogGEN.writeTrace("CBG_Log", "sCustomerID---"+sCustomerID);
				
				
				LogGEN.writeTrace("CBG_Log", "sDate---"+sDate+" sWSDLPath "+sWSDLPath);
				//sDate="06/08/2013 18:33:10";
				InqCustomerInformationStub Cust_Info=null;
				try
				{
				Cust_Info=new InqCustomerInformationStub(sWSDLPath);
				}
				catch(Exception e)
				{
					LogGEN.writeTrace("CBG_Log", "Error---"+e.getMessage());
				}
				GetCustomerInformationReqMsg Cust_Info_Req_Msg = new GetCustomerInformationReqMsg();
				GetCustomerInformationReq_type0 Cust_Req_Type=new GetCustomerInformationReq_type0();				
				GetCustomerInformationResMsg Cust_Info_Rep_Msg=new GetCustomerInformationResMsg();
				GetCustomerInformationRes_type0 Cust_Info_Res= new GetCustomerInformationRes_type0();
				HeaderType Header_Input = new HeaderType();
				ListOfCustomer_type0E Cust_List= new ListOfCustomer_type0E();
				ListOfCustomer_type0 Cust_List_0;
				Customer_type0E Cust_Type=new Customer_type0E();
				Customer_type0[] Cust_Type1;
				LogGEN.writeTrace("CBG_Log", "All Objects created");				
							  
				
				String typeofTxn=xmlDataParser.getValueOf("txnType");
				if(typeofTxn.equalsIgnoreCase(""))
					typeofTxn="GetCustomerSummary";
				Cust_Info_Req_Msg.setHeader(setHeaderDtls(sDate, ref_no, senderId));	
				Cust_Type.setCustomerId(sCustomerID);
				Cust_Type.setTypeOfTxn(typeofTxn);				
				Cust_List.setCustomer(Cust_Type);	
				Cust_Req_Type.setListOfCustomer(Cust_List);
			    Cust_Info_Req_Msg.setGetCustomerInformationReq(Cust_Req_Type);		
			    
			    LogGEN.writeTrace("CBG_Log", "All values set");
			    
				xmlInput=Cust_Info.getInputXml(Cust_Info_Req_Msg);
				LogGEN.writeTrace("CBG_Log", "xmlInput---"+xmlInput);
				Cust_Info._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			    Cust_Info_Rep_Msg=Cust_Info.getCustomerInformation_Oper(Cust_Info_Req_Msg);
			    sOrg_Output=Cust_Info.resMsg;
			    Header_Input= Cust_Info_Rep_Msg.getHeader();
			    sReturnCode= Header_Input.getReturnCode();
			    sErrorDetail=Header_Input.getErrorDetail();
			    sErrorDesc = Header_Input.getErrorDescription();
			    
			    LogGEN.writeTrace("CBG_Log", "Return Code---"+sReturnCode);
			    LogGEN.writeTrace("CBG_Log", "sOrg_Output---"+sOrg_Output);
			    LogGEN.writeTrace("CBG_Log", "Error Detail---"+sErrorDetail);
			    LogGEN.writeTrace("CBG_Log", "Error Description---"+sErrorDesc);
			    
			    
			    String Output1="";
			    if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
				{			    
			    	LogGEN.writeTrace("CBG_Log", "Successful Result");
			    	Cust_Info_Res=Cust_Info_Rep_Msg.getGetCustomerInformationRes();
			    	Cust_List_0= Cust_Info_Res.getListOfCustomer(); 				   
				    Cust_Type1=Cust_List_0.getCustomer();
				    
				
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
					"<Output>"+
						"<Option>InqCustomerInformation</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<GetCustomerInformationRes>"+
							"<listOfCustomer>";
					
					for(int i=0;i<Cust_Type1.length;i++)
					{
						sOutput+="<customer>" +
						"<Prefix>"+Cust_Type1[0].getPrefix()+"</Prefix>"+
						"<FullName>"+Cust_Type1[0].getFullName()+"</FullName>"+
						"<DOB>"+Cust_Type1[0].getDateofBirth()+"</DOB>"+
						"<PassportNO>"+Cust_Type1[0].getPassportNo()+"</PassportNO>"+
						"<PssportIssueDate>"+Cust_Type1[0].getIssueDate()+"</PssportIssueDate>"+
						"<PassportExpDate>"+Cust_Type1[0].getExpiryDate()+"</PassportExpDate>"+
						"<VisaNo>"+Cust_Type1[0].getVisaNo()+"</VisaNo>"+
						"<VisaExpDate>"+Cust_Type1[0].getVisaExpiryDate()+"</VisaExpDate>"+
						"<VisaIssueDate>"+Cust_Type1[0].getVisaIssueDate()+"</VisaIssueDate>"+
						"<Nationality>"+Cust_Type1[0].getNationalityName()+"</Nationality>"+						
						"<MotherName>"+Cust_Type1[0].getMotherMaidenName()+"</MotherName>"+
						"<ResCountry>"+Cust_Type1[0].getCountryofResidence()+"</ResCountry>"+
						"<Profession>"+Cust_Type1[0].getProfession()+"</Profession>"+
						"<Gender>"+Cust_Type1[0].getGender()+"</Gender>"+
						"<EIDA_NO>"+Cust_Type1[0].getUAENationalID()+"</EIDA_NO>"+
						"<MemoSeverity>"+Cust_Type1[0].getMemoSeverity()+"</MemoSeverity>"+
						"<memoDesc>"+Cust_Type1[0].getMemoDesc()+"</memoDesc>"+
						"<domicileBranchName>"+Cust_Type1[0].getDomicileBranchName()+"</domicileBranchName>"+
						"<domicileBranchCode>"+Cust_Type1[0].getDomicileBranchCode()+"</domicileBranchCode>";
						
						if(Cust_Type1[0].getNameofEmployer() != null)
						{
							sOutput+="<EmpName>"+Cust_Type1[0].getNameofEmployer().replaceAll("<", "&lt;")+"</EmpName>";
						}
						else
						{
							sOutput+="<EmpName>"+Cust_Type1[0].getNameofEmployer()+"</EmpName>";
						}
						
						sOutput+="<CustomerIC>"+Cust_Type1[0].getCustomerIC()+"</CustomerIC>"+
						"<CustCategory>"+Cust_Type1[0].getCustCategory()+"</CustCategory>"+
						"<SCPackageCode>"+Cust_Type1[0].getSCPackageCode()+"</SCPackageCode>"+
						"<BenefitCategory>"+Cust_Type1[0].getBenefitCategory()+"</BenefitCategory>"+
						"<BusinessType>"+Cust_Type1[0].getBusinessType()+"</BusinessType>"+
						"<CompanyBlacklist>"+Cust_Type1[0].getCompanyBlacklist()+"</CompanyBlacklist>"+
						"<CompanyCategory>"+Cust_Type1[0].getCompanyCategory()+"</CompanyCategory>"+
						"<CompanyCode>"+Cust_Type1[0].getCompanyCode()+"</CompanyCode>";
						
						if(Cust_Type1[0].getCompanyName() != null)
						{
							sOutput+="<CompanyName>"+Cust_Type1[0].getCompanyName().replaceAll("<", "&lt;")+"</CompanyName>";
						}
						else
						{
							sOutput+="<CompanyName>"+Cust_Type1[0].getCompanyName()+"</CompanyName>";
						}
						
						sOutput+="<CriteriaforPrivilege>"+Cust_Type1[0].getCriteriaforPrivilege()+"</CriteriaforPrivilege>"+
						"<CustAttritionScore>"+Cust_Type1[0].getCustAttritionScore()+"</CustAttritionScore>"+
						"<CustBehavioralScore>"+Cust_Type1[0].getCustBehavioralScore()+"</CustBehavioralScore>"+
						"<CustBlacklistStatus>"+Cust_Type1[0].getCustBlacklistStatus()+"</CustBlacklistStatus>"+
						"<CustIslamicFlag>"+Cust_Type1[0].getCustIslamicFlag()+"</CustIslamicFlag>"+
						"<CustMarketingScore>"+Cust_Type1[0].getCustMarketingScore()+"</CustMarketingScore>"+
						"<CustProfitabilityScore>"+Cust_Type1[0].getCustProfitabilityScore()+"</CustProfitabilityScore>"+
						"<CustSignupDat>"+Cust_Type1[0].getCustSignupDat()+"</CustSignupDat>"+
						"<DateofCIDOpening>"+Cust_Type1[0].getDateofCIDOpening()+"</DateofCIDOpening>"+
						"<DateofJoining>"+Cust_Type1[0].getDateofJoining()+"</DateofJoining>"+
						"<Designation>"+Cust_Type1[0].getDesignation()+"</Designation>"+
						"<EducationalQual>"+Cust_Type1[0].getEducationalQual()+"</EducationalQual>"+
						"<EmployeeID>"+Cust_Type1[0].getEmployeeID()+"</EmployeeID>"+
						"<TotalTouchpoint>"+Cust_Type1[0].getTotalTouchpoint()+"</TotalTouchpoint>"+
						"<TMLFlag>"+Cust_Type1[0].getTMLFlag()+"</TMLFlag>"+
						"<StaffFlag>"+Cust_Type1[0].getStaffFlag()+"</StaffFlag>"+
						"<SignatureType>"+Cust_Type1[0].getSignatureType()+"</SignatureType>"+
						"<ShortName>"+Cust_Type1[0].getShortName()+"</ShortName>"+
						"<SalaryAmount>"+Cust_Type1[0].getSalaryAmount()+"</SalaryAmount>"+
						"<RMName>"+Cust_Type1[0].getRMName()+"</RMName>"+
						"<RMCode>"+Cust_Type1[0].getRMCode()+"</RMCode>"+
						"<PromotionCode>"+Cust_Type1[0].getPromotionCode()+"</PromotionCode>"+
						"<ProfitCenterName>"+Cust_Type1[0].getProfitCenterName()+"</ProfitCenterName>"+
						"<ProfitCenterCode>"+Cust_Type1[0].getProfitCenterCode()+"</ProfitCenterCode>"+
						"<MaritalStatus>"+Cust_Type1[0].getMaritalStatus()+"</MaritalStatus>"+
						"<EStatementSubFlag>"+Cust_Type1[0].getEStatementSubFlag()+"</EStatementSubFlag>"+
						"<EmploymentType>"+Cust_Type1[0].getEmploymentType()+"</EmploymentType>"+
						"<InternetBanking>"+Cust_Type1[0].getInternetBanking()+"</InternetBanking>"+
						"<SMSBanking>"+Cust_Type1[0].getSMSBanking()+"</SMSBanking>"+
						"<IVR>"+Cust_Type1[0].getIVR()+"</IVR>"+
						"<MIBRegStatus>"+Cust_Type1[0].getMIBRegStatus()+"</MIBRegStatus>"+						
						/*"<City>"+Address_2[0].getCity()+"</City>"+
						"<POBox>"+Address_2[0].getPOBox()+"</POBox>"+
						"<State>"+Address_2[0].getState()+"</State>"+
						"<Country>"+Address_2[0].getCountry()+"</Country>"+
						"<Phone>"+Address_2[0].getPhoneNo()+"</Phone>"+
						"<Mobile>"+Address_2[0].getMobileNo()+"</Mobile>"+
						"<Email>"+Address_2[0].getEMail()+"</Email>"+	*/					
						"</customer>" ;
						
					if(typeofTxn.equalsIgnoreCase("GetCustomerDetails"))
					{
						try
						{
							LogGEN.writeTrace("CBG_Log", "DUF VALUES");
							ListOfUDFDetails_type0 udf=new ListOfUDFDetails_type0();
							udf=Cust_Type1[0].getListOfUDFDetails();
							UDFDetails_type0[] u= udf.getUDFDetails();
							sOutput+="<UDFS>" ;
							for(int j=0;j<u.length;j++)
							{
								sOutput+="<UDF>"+
								"<UDFLabel>"+ u[j].getUDFLabel()+"</UDFLabel>"+
								"<UDFValue>"+u[j].getUDFValue()+"</UDFValue></UDF>";
							}
							sOutput+="</UDFS>" ;
						}
						catch(Exception ex)
						{
							LogGEN.writeTrace("CBG_Log", "DUF VALUES ERROR");
							sOutput+="</UDFS>" ;
						}
					}	
						
						System.out.println(sOutput);
						if(!xmlDataParser.getValueOf("txnType").equalsIgnoreCase("GetCustomerDetails"))
						{
							ListOfAccounts_type0 accounts=new ListOfAccounts_type0();
							accounts=Cust_Type1[0].getListOfAccounts();
							Accounts_type0[] acc=accounts.getAccounts();
							sOutput+="<Accounts>";
							try
							{
								for(int j=0;j<acc.length;j++)
								{
									
									sOutput+="<Account><AcctType>"+acc[j].getAcctType()+"</AcctType>"+
									"<AccountNo>"+acc[j].getAccountNo()+"</AccountNo>"+
									"<AccountTitle>"+acc[j].getAccountTitle()+"</AccountTitle>"+
									"<AcctStatus>"+acc[j].getAcctStatus()+"</AcctStatus>"+
									"<BranchCode>"+acc[j].getBranchCode()+"</BranchCode>"+
									"<BranchName>"+acc[j].getBranchName()+"</BranchName>"+
									"<ProductCode>"+acc[j].getProductCode()+"</ProductCode>"+
									"<ProductName>"+acc[j].getProductName()+"</ProductName>"+
									"<AcctBalance>"+acc[j].getAcctBalance()+"</AcctBalance>"+									
									"<CurrencyName>"+acc[j].getCurrencyName()+"</CurrencyName>" +
									"<AccountType>"+acc[j].getType()+"</AccountType>"+
									"</Account>";
										
								}
							}
							catch(Exception ee)
							{
								
							}
							sOutput+="</Accounts>";
							ListOfAddress_type0 address=new ListOfAddress_type0();
							address=Cust_Type1[0].getListOfAddress();
							Address_type0[] add=address.getAddress();
							sOutput+="<Addresses>";
							try
							{
								for(int j=0;j<add.length;j++)
								{
									
									sOutput+="<Address>" +
									"<Type>"+add[j].getType()+"</Type>"+
									"<AddressLine_1>"+add[j].getAddressLine_1()+"</AddressLine_1>"+
									"<AddressLine_2>"+add[j].getAddressLine_2()+"</AddressLine_2>"+
									"<BuildingName>"+add[j].getBuildingName()+"</BuildingName>"+
									"<City>"+add[j].getCity()+"</City>";
									
									if(add[j].getCompanyName() != null)
									{
										sOutput+="<CompanyName>"+add[j].getCompanyName().replaceAll("<", "&lt;")+"</CompanyName>";
									}
									else
									{
										sOutput+="<CompanyName>"+add[j].getCompanyName()+"</CompanyName>";
									}
									
									
									sOutput+="<Country>"+add[j].getCountry()+"</Country>"+
									"<Email>"+add[j].getEMail()+"</Email>"+
									"<Phone>"+add[j].getPhoneNo()+"</Phone>"+	
									"<Mobile>"+add[j].getMobileNo()+"</Mobile>"+
									"<State>"+add[j].getState()+"</State>"+
									"<POBox>"+add[j].getPOBox()+"</POBox>"+
									"<ZipCode>"+add[j].getZipCode()+"</ZipCode></Address>";
										
								}
							
							}
							catch(Exception ee)
							{
								
							}							
							sOutput+="</Addresses>";							
							ListOfCreditCards_type0 ccards=new ListOfCreditCards_type0();
							ccards=Cust_Type1[0].getListOfCreditCards();
							CreditCards_type0[] cc=ccards.getCreditCards();
							Output1=sOutput;
							sOutput+="<CreditCards>";
							Output1=sOutput;
							try
							{
								for(int j=0;j<cc.length;j++)
								{
									
									sOutput+="<Creditcard>" +									
									"<CardNumber>"+cc[j].getCardNumber()+"</CardNumber>"+
									"<Card_name>"+cc[j].getCard_name()+"</Card_name>"+
									"<CardType>"+cc[j].getCardType()+"</CardType>"+
									"<status>"+cc[j].getStatus()+"</status>"+
									"<ProductName>"+cc[j].getProductName()+"</ProductName>"+
									"<VPlusAccountNumber>"+cc[j].getVPlusAccountNumber()+"</VPlusAccountNumber>"+
									"</Creditcard>";
									
									Output1+="<Creditcard>" +									
									"<CardNumber>XXXXXXXXXXXXXXXX</CardNumber>"+
									"<Card_name>"+cc[j].getCard_name()+"</Card_name>"+
									"<CardType>"+cc[j].getCardType()+"</CardType>"+
									"<status>"+cc[j].getStatus()+"</status>"+
									"<ProductName>"+cc[j].getProductName()+"</ProductName>"+
									"<VPlusAccountNumber>"+cc[j].getVPlusAccountNumber()+"</VPlusAccountNumber>"+
									"</Creditcard>";
										
								}
							
							}
							catch(Exception ee)
							{
								
							}
							Output1+="</CreditCards>";
							sOutput+="</CreditCards>";	
							
						}
					}
					
						sOutput+="</listOfCustomer>" +
						"</GetCustomerInformationRes>" +
						"<TotalRetrieved>1</TotalRetrieved>" +
						"</Output>";
						//System.out.println(sOutput);
						LogGEN.writeTrace("CBG_Log", "Output XML--- "+sOutput);
				}
			    else
				{
			    	LogGEN.writeTrace("CBG_Log", "Failed");
			    	sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>InqCustomerInformation</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to fetch data from flex. Either CID is invalid or Integration server is down.</td></Output>";
				}
			  
			}
			catch (Exception e)
			{
				LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
				LogGEN.writeTrace("CBG_Log", "Error Trace in Web Service :"+e);
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>InqCustomerInformation</Option><returnCode>-1</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to fetch data from flex. Either CID is invalid or Integration server is down.</td></Output>";
				e.printStackTrace();
			}
			finally
			{
				LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
				if(sOutput.trim().length()<1)
				{
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>InqCustomerInformation</Option><returnCode>-1</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to fetch data from flex. Either CID is invalid or Integration server is down.</td></Output>";
				}
				String Status="";
				if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2")) {
					Status="Success";
				}
				else {
					Status="Failure";
				}
				
				
//				try {
//					//sHandler.readCabProperty("JTS");
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}	
				
					String dburl=(String)currentCabPropertyMap.get("DBURL");
					String dbuser=(String)currentCabPropertyMap.get("USER");
					String dbpass=(String)currentCabPropertyMap.get("PASS");
					
					//sOrg_Output=xmlInput;
					String inputXml=xmlInput;
					LogGEN.writeTrace("CBG_Log", inputXml);
					String winame=xmlDataParser.getValueOf("WiName");
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
						 LogGEN.writeTrace("CBG_Log", "Error inserting---"+e.getMessage());
					 }
					 DBConnection con=new DBConnection();
					 //String retVal=con.execute("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query);
					
					 
					 String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
							"and randomnumber='"+ sessionID +"'),'"+call_type1+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
							"?,sysdate,'"+  Status + "')";
					 LogGEN.writeTrace("CBG_Log","outputXML : finally :"+sOutput);
					 con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_Output.replaceAll("'", "''"));
					
					 
					
				//LogGEN.writeTrace("CBG_Log","outputXML : finally :"+sOutput);
				return sOutput;			
			}
		}
		
		private HeaderType setHeaderDtls(String sDate,String ref_no,String senderId){
			LogGEN.writeTrace("CBG_Log", "inside setHeaderDtls function ");
			HeaderType headerType= new HeaderType();		
			headerType.setUsecaseID("1234");
			headerType.setServiceName("InqCustomerInformation");
			headerType.setVersionNo("1.0");
			headerType.setServiceAction("Inquiry");
			headerType.setSysRefNumber(ref_no);
			headerType.setSenderID(senderId);
			headerType.setReqTimeStamp(sDate);
			headerType.setUsername(loggedinuser);
			headerType.setCredentials(loggedinuser);
			LogGEN.writeTrace("CBG_Log", "setHeaderDtls complete ");
			return headerType;
		}
}
