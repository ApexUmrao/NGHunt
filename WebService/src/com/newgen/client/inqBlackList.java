package com.newgen.client;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.stub.InqBlackListSearchStub;
import com.newgen.stub.InqBlackListSearchStub.InqBlackListSearchReqMsg;
import com.newgen.stub.InqBlackListSearchStub.InqBlackListSearchReq_type0;
import com.newgen.stub.InqBlackListSearchStub.InqBlackListSearchResMsg;
import com.newgen.stub.InqBlackListSearchStub.HeaderType;
import com.newgen.stub.InqBlackListSearchStub.InqBlackListSearchRes_type0;
import com.newgen.stub.InqBlackListSearchStub.MatchRecords_type0;
import com.newgen.stub.InqBlackListSearchStub.Record_type0;

public class inqBlackList extends WebServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	protected int iMatchCount = 0;
	String xmlInput="";
	//String sOrg_Output=null;//Added By Harish For inserting original mssg
	/**
	 * Function written to fetch customer information
	 * @author gupta.ashish
	 * @param sInputXML
	 * @return
	 */

		@SuppressWarnings("finally")
		public String getBlackList(String sInputXML) 
		{
			LogGEN.writeTrace("Log", "Fuction called BlackList");
			LogGEN.writeTrace("Log", "sInputXML---"+sInputXML);
			XMLParser xmlDataParser = new XMLParser();
			xmlDataParser.setInputXML(sInputXML);
			String sOutput="";
			String sReturnCode= "";
			String sErrorDetail="";
			String sErrorDesc = "";
			String outputXML="";
			WebServiceHandler sHandler= new WebServiceHandler();
			Date d= new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String sDate = dateFormat.format(d);
			try
			{
				
				sHandler.readCabProperty("Black_List");				
				sWSDLPath=(String)currentCabPropertyMap.get("WSDL_PATH");
				sCabinet=(String)currentCabPropertyMap.get("CABINET");
				sUser=(String)currentCabPropertyMap.get("USER");
				sLoginReq=Boolean.valueOf((String)currentCabPropertyMap.get("LOGIN_REQ"));
				sPassword=(String)currentCabPropertyMap.get("PASSWORD");
				lTimeOut=Long.valueOf((String)currentCabPropertyMap.get("TIMEOUT_INTERVAL"));
				iMatchCount=Integer.valueOf((String)currentCabPropertyMap.get("MATCH_COUNT"));
				
				LogGEN.writeTrace("Log", "read Property successfully");
				LogGEN.writeTrace("Log", "WSDL PATH---- "+(String)currentCabPropertyMap.get("WSDL_PATH"));
				LogGEN.writeTrace("Log", "CABINET---- "+(String)currentCabPropertyMap.get("CABINET"));
				LogGEN.writeTrace("Log", "USER---- "+(String)currentCabPropertyMap.get("USER"));
				LogGEN.writeTrace("Log", "PASSWORD---- "+(String)currentCabPropertyMap.get("PASSWORD"));
				LogGEN.writeTrace("Log", "LOGIN_REQ---- "+(String)currentCabPropertyMap.get("LOGIN_REQ"));
				LogGEN.writeTrace("Log", "TIME_OUT---- "+(String)currentCabPropertyMap.get("TIMEOUT_INTERVAL"));
				LogGEN.writeTrace("Log", "MATCH_COUNT---- "+iMatchCount);
				
				String sCustomerID= xmlDataParser.getValueOf("CUST_ID");
				String ref_no=xmlDataParser.getValueOf("REF_NO");
				LogGEN.writeTrace("Log", "sCustomerID---"+sCustomerID);
				
				LogGEN.writeTrace("Log", "sDate---"+sDate);
				
				InqBlackListSearchStub black_stub=new InqBlackListSearchStub(sWSDLPath);
				InqBlackListSearchReqMsg black_Req_Msg = new InqBlackListSearchReqMsg();				
				InqBlackListSearchResMsg black_Rep_Msg=new InqBlackListSearchResMsg();
			
				HeaderType Header_Input = new HeaderType();
				
				LogGEN.writeTrace("Log", "All Objects created");
				
				Header_Input.setUsecaseID("1234");
				Header_Input.setServiceName("InqBlackListSearch");
				Header_Input.setVersionNo("1.0");
				Header_Input.setServiceAction("Inquiry");
				Header_Input.setSysRefNumber(ref_no);
				Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
				Header_Input.setReqTimeStamp(sDate);
				Header_Input.setUsername(sCustomerID);
				Header_Input.setCredentials(loggedinuser);	
				InqBlackListSearchReq_type0 black_req=new InqBlackListSearchReq_type0();
				
				black_req.setCustomerName(xmlDataParser.getValueOf("customerName"));
				black_req.setNationality(xmlDataParser.getValueOf("nationality"));
				black_req.setDateOfBirth(xmlDataParser.getValueOf("dateOfBirth"));
				black_req.setPassportNumber(xmlDataParser.getValueOf("passportNumber"));
				black_req.setResVisaNumber(xmlDataParser.getValueOf("resVisaNumber"));
				black_req.setPOBox(xmlDataParser.getValueOf("POBox"));
				black_req.setTelephoneNumber(xmlDataParser.getValueOf("telephoneNumber"));
				black_req.setRegistrationNumber(xmlDataParser.getValueOf("registrationNumber"));
				black_req.setTradeLicenseNumber(xmlDataParser.getValueOf("tradeLicenseNumber"));
				black_req.setMobileNumber(xmlDataParser.getValueOf("mobileNumber"));
				black_req.setRegistrationDate(xmlDataParser.getValueOf("registrationDate"));
				black_req.setMotherMaidenName(xmlDataParser.getValueOf("motherMaidenName"));
				black_req.setSearchCriteria(xmlDataParser.getValueOf("searchCriteria"));
				black_req.setNameSearchMode(xmlDataParser.getValueOf("nameSearchMode"));
				black_req.setListType(xmlDataParser.getValueOf("listType"));
				
				black_Req_Msg.setHeader(Header_Input);
				black_Req_Msg.setInqBlackListSearchReq(black_req)	;	    
			    LogGEN.writeTrace("Log", "All values set");
			    
			    xmlInput=black_stub.getinputXML(black_Req_Msg);
			    black_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			    black_Rep_Msg=black_stub.inqBlackListSearch_Oper(black_Req_Msg);
			    System.out.println(black_stub.getinputXml(black_Req_Msg));
			    outputXML=black_stub.outputXML;
			    
			    Header_Input= black_Rep_Msg.getHeader();
			    sReturnCode= Header_Input.getReturnCode();
			    sErrorDetail=Header_Input.getErrorDetail();
			    sErrorDesc = Header_Input.getErrorDescription();
			    
			    LogGEN.writeTrace("Log", "Return Code---"+sReturnCode);
			    LogGEN.writeTrace("Log", "Error Detail---"+sErrorDetail);
			    LogGEN.writeTrace("Log", "Error Description---"+sErrorDesc);
			    System.out.println(sErrorDetail);
			    System.out.println(sReturnCode);
			    System.out.println(sErrorDesc);
			    if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
				{			    
			    	LogGEN.writeTrace("Log", "Successful Result");
			    	InqBlackListSearchRes_type0 res=new InqBlackListSearchRes_type0();
			    		res=black_Rep_Msg.getInqBlackListSearchRes();
				    	sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
							"<Option>Black_List</Option>" +
							"<returnCode>"+sReturnCode+"</returnCode>" +
							"<errorDescription>"+sErrorDetail+"</errorDescription>"+
							"<BlackListRes>"+
								"<matchCount>"+ res.getMatchCount() +"</matchCount>";
				    		if(!res.getMatchCount().equalsIgnoreCase("0"))
				    		{
					    		MatchRecords_type0 match=new MatchRecords_type0();
					    		System.out.println("gdfgdfgdf"+res.getMatchCount());
					    		match=res.getMatchRecords();
						    	Record_type0[] rec;
						    	//COde change for Black LIst slowness Harish
						    	rec=match.getRecord();
						    	if(iMatchCount == 0 || iMatchCount >= rec.length)
						    	{
						    		iMatchCount = rec.length;
						    	}
						    	//COde change for Black LIst slowness Harish	
						    	for(int i=0;i<iMatchCount;i++)
					    		{
					    			sOutput+="<Records><BlackListType>"+ rec[i].getBlackListType() +"</BlackListType>"+
					    			"<CustomerId>"+ rec[i].getCustomerId() +"</CustomerId>"+
					    			"<CustomerName>"+ rec[i].getCustomerName() +"</CustomerName>"+
					    			"<CustomerType>"+ rec[i].getCustomerType() +"</CustomerType>"+
					    			"<DateOfBirth>"+ rec[i].getDateOfBirth() +"</DateOfBirth>"+
					    			"<EmployerName>"+ rec[i].getEmployerName() +"</EmployerName>"+
					    			"<Gender>"+ rec[i].getGender() +"</Gender>"+
					    			"<MobileNumber>"+ rec[i].getMobileNumber() +"</MobileNumber>"+
					    			"<MotherMaidenName>"+ rec[i].getMotherMaidenName() +"</MotherMaidenName>"+
					    			"<Nationality>"+ rec[i].getNationality() +"</Nationality>"+
					    			"<Notes>"+ rec[i].getNotes().replace("'","''") +"</Notes>"+
					    			"<PassportNumber>"+ rec[i].getPassportNumber() +"</PassportNumber>"+
					    			"<POBox>"+ rec[i].getPOBox() +"</POBox>"+
					    			"<PriorityNumber>"+ rec[i].getPriorityNumber() +"</PriorityNumber>"+
					    			"<RegistrationDate>"+ rec[i].getRegistrationDate() +"</RegistrationDate>"+
					    			"<ResVisaNumber>"+ rec[i].getResVisaNumber() +"</ResVisaNumber>"+
					    			"<TelephoneNumber>"+ rec[i].getTelephoneNumber() +"</TelephoneNumber></Records>";
					    		}
				    		}
				    		sOutput+="</BlackListRes>"+	
							"</Output>";
				}
			    else
				{
			    	LogGEN.writeTrace("Log", "Failed");
			    	sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Black_List</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to get black list.</td></Output>";
				}
			}
			catch (Exception e)
			{
				LogGEN.writeTrace("Log", "Error in Web Serviice :"+e.toString());
				LogGEN.writeTrace("Log", "Error Trace in Web Serviice :"+e.getStackTrace());
				if(!sReturnCode.equalsIgnoreCase("0"))
				{
					sReturnCode="-1";
					sErrorDetail=e.getMessage();
				}
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Black_List</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to to get black list.</td></Output>";
				e.printStackTrace();
			}
			finally
			{
				LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput.trim().length());				
				if(sOutput.trim().length()<1)
				{
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Black_List</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to to get black list.</td></Output>";
				}
				
				
				
				String dburl=(String)currentCabPropertyMap.get("DBURL");
				String dbuser=(String)currentCabPropertyMap.get("USER");
				String dbpass=(String)currentCabPropertyMap.get("PASS");
				
				//sOrg_Output=xmlInput;
				String inputXml=xmlInput;
				LogGEN.writeTrace("Log", inputXml);
				String winame=xmlDataParser.getValueOf("WiName");
				String sessionID= xmlDataParser.getValueOf("SessionId");
				String call_type1=xmlDataParser.getValueOf("Calltype");
				sCabinet=xmlDataParser.getValueOf("EngineName");
				dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				
				 try
				 {
					 dbpass=AESEncryption.decrypt(dbpass);
				 }
				 catch(Exception e)
				 {
					 LogGEN.writeTrace("Log", "Error inserting---"+e.getMessage());
				 }
				 DBConnection con=new DBConnection();
				 //String retVal=con.execute("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query);
				
				 
				 String Query="insert into usr_0_other_calls_web_log(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
						"and randomnumber='"+ sessionID +"'),'"+call_type1+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
						"?,sysdate,'"+  sReturnCode + "')";
				 LogGEN.writeTrace("Log","outputXML : finally :"+sOutput);
				 con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),outputXML.replaceAll("'", "''"));
				
				 
				
				
				
				/*DBUtil dbcon=new DBUtil();
				 try {
					sHandler.readCabProperty("JTS");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				 String IP=(String)currentCabPropertyMap.get("IP");
					String Port=(String)currentCabPropertyMap.get("PORT");
					String dburl=(String)currentCabPropertyMap.get("DBURL");
					String dbuser=(String)currentCabPropertyMap.get("USER");
					String dbpass=(String)currentCabPropertyMap.get("PASS");
					
					
					String inputXml=xmlInput;
					LogGEN.writeTrace("Log", inputXml);
					String winame=xmlDataParser.getValueOf("WiName");
					String sessionID= xmlDataParser.getValueOf("SessionId");
					String call_type=xmlDataParser.getValueOf("Calltype");
					sCabinet=xmlDataParser.getValueOf("EngineName");
					dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					Date d1=new Date();
					String sDate1 = dateFormat.format(d1);
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
						 LogGEN.writeTrace("Log", "Error Password:----------------------------"+e.getMessage());
					 }
					 DBConnection con=new DBConnection();
					 String retVal=	"";//con.execute("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query);
					
					 if(retVal.equalsIgnoreCase("ERROR"))
					 {
						 LogGEN.writeTrace("Log", "Error:----------------------------");
						 Query="insert into usr_0_other_calls_web_log(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
							"and randomnumber='"+ sessionID +"'),'"+call_type+"','"+ inputXml.replaceAll("'", "''") +"',to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
							"'',sysdate,'"+  Status + "')";

						 con.execute("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query);
						 int len=(int) Math.ceil(( outputxml.replaceAll("'", "''").length())/2000);
						 int i=1;
						 String outTmp="";
						 while(i<=len)
						 {
							 if(i==len)
							 {
								 outTmp=outputxml.substring(0,outputxml.length()); 
							 }
							 else
							 {
								 outTmp=outputxml.substring(0,2000);
							 }
							 
							 i++;
							 Query="update usr_0_other_calls_web_log set response=response||'"+outTmp+"' where winame='"+winame+"' and request_type='"+call_type+"' and requestdatetime=to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss')";
							 con.execute("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query);
							 if(i!=len)
							 {
								 outputxml=outputxml.substring(2000);
							 }
						 }
						 
					 }*/
				LogGEN.writeTrace("Log","outputXML : finally :"+sOutput);
				return sOutput;			
			}
		}
}
