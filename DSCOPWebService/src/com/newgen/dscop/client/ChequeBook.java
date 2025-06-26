package com.newgen.dscop.client;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.AddCBRequestStub;
import com.newgen.dscop.stub.AddCBRequestStub.HeaderType;
import com.newgen.dscop.stub.AddCBRequestStub.SRChequeBookReq2_type0;
import com.newgen.dscop.stub.AddCBRequestStub.SRChequeBookReqMsg;
import com.newgen.dscop.stub.AddCBRequestStub.SRChequeBookReqMsgChoice_type0;
import com.newgen.dscop.stub.AddCBRequestStub.SRChequeBookRes2_type0;
import com.newgen.dscop.stub.AddCBRequestStub.SRChequeBookResMsg;
import com.newgen.dscop.stub.AddCBRequestStub.SRChequeBookResMsgChoice_type0;


public class ChequeBook extends DSCOPServiceHandler{

	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_Chk_ouput=null;
	
	/**
	 * Function written to fetch customer information
	 * @author gupta.ashish
	 * @param sInputXML
	 * @return
	 */

		@SuppressWarnings("finally")
		public String addCheque(String sInputXML) 
		{
			LogGEN.writeTrace("CBG_Log", "Fuction called Cheque_Book");
			LogGEN.writeTrace("CBG_Log", "ChequeBook sInputXML---"+sInputXML);
			XMLParser xmlDataParser = new XMLParser();
			xmlDataParser.setInputXML(sInputXML);
			String sOutput="";
			String sReturnCode= "";
			String sErrorDetail="";
			String sErrorDesc = "";
			DSCOPServiceHandler sHandler= new DSCOPServiceHandler();
			Date d= new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String sDate = dateFormat.format(d);
			try
			{
				
				//sHandler.readCabProperty("Cheque_Book");				
				LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
				ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("Cheque_Book");
				LogGEN.writeTrace("CBG_Log", "Cheque_Book WebServiceConfig Map : "  +wsConfig.toString());
				sWSDLPath=(String)wsConfig.get(0);
				sCabinet=(String)wsConfig.get(1);
				sUser=(String)wsConfig.get(2);
				sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
				sPassword=(String)wsConfig.get(4);
				lTimeOut=Long.valueOf((String)wsConfig.get(5));
				LogGEN.writeTrace("CBG_Log", "Cheque_Book WSDL PATH: "+sWSDLPath);
				LogGEN.writeTrace("CBG_Log", "Cheque_Book CABINET: "+sCabinet);
				LogGEN.writeTrace("CBG_Log", "Cheque_Book USER: "+sUser);
				LogGEN.writeTrace("CBG_Log", "Cheque_Book PASSWORD: "+sPassword);
				LogGEN.writeTrace("CBG_Log", "Cheque_Book LOGIN_REQ: "+sLoginReq);
				LogGEN.writeTrace("CBG_Log", "Cheque_Book TIME_OUT: "+lTimeOut);
				
				String sCustomerID= xmlDataParser.getValueOf("CUST_ID");
				//String sessionID= xmlDataParser.getValueOf("SessionId");
				//String winame=xmlDataParser.getValueOf("WiName");
				String ref_no=xmlDataParser.getValueOf("REF_NO");
				String oldRefNo=xmlDataParser.getValueOf("OLDREF_NO"); //change for old ref no
				sCabinet=xmlDataParser.getValueOf("EngineName");
				LogGEN.writeTrace("CBG_Log", "sCustomerID---"+sCustomerID);
				
				
				LogGEN.writeTrace("CBG_Log", "sDate---"+sDate);
				//sDate="06/08/2013 18:33:10";
				AddCBRequestStub cheque_book=new AddCBRequestStub(sWSDLPath);
				SRChequeBookReqMsg cheque_book_Req_Msg = new SRChequeBookReqMsg();
				SRChequeBookResMsg res_msg=new SRChequeBookResMsg();
				SRChequeBookReqMsgChoice_type0 req=new SRChequeBookReqMsgChoice_type0();
				SRChequeBookReq2_type0 r=new SRChequeBookReq2_type0();
				
				HeaderType Header_Input = new HeaderType();
				
				
				Header_Input.setUsecaseID("1234");
				Header_Input.setServiceName("AddCBRequest");
				Header_Input.setVersionNo("2.0");
				Header_Input.setServiceAction("Addition");
				Header_Input.setSysRefNumber(ref_no);
				Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
				Header_Input.setReqTimeStamp(sDate);
				Header_Input.setUsername(sCustomerID);
				Header_Input.setCredentials(loggedinuser);			  
				
				r.setCheckerId("WMSUSER");
				r.setChequeBookIssueDate(xmlDataParser.getValueOf("ChequeBookIssueDate"));
				r.setChequeBookSerialNo(xmlDataParser.getValueOf("ChequeBookSerialNo"));
				r.setChequeBookStatus(xmlDataParser.getValueOf("ChequeBookStatus"));
				r.setChequeEndNumber(xmlDataParser.getValueOf("ChequeEndNumber"));
				r.setChequePaidStatus(xmlDataParser.getValueOf("ChequePaidStatus"));
				r.setChequeStartNumber(xmlDataParser.getValueOf("ChequeStartNumber"));
				r.setCustAccountNumber(xmlDataParser.getValueOf("CustAccountNumber"));
				r.setFlagChequeBookType(xmlDataParser.getValueOf("FlagChequeBookType"));
				r.setFlagChequeType(xmlDataParser.getValueOf("FlagChequeType"));
				r.setFlagPRN(xmlDataParser.getValueOf("FlagPRN"));
				r.setFlagServiceChargesWaiver(xmlDataParser.getValueOf("FlagServiceChargesWaiver"));
				r.setMaintenanceOption(xmlDataParser.getValueOf("MaintenanceOption"));
				r.setMakerId("WMSUSER");
				r.setUpdateSerialNo(xmlDataParser.getValueOf("UpdateSerialNo"));
				r.setNoOfLeavesRequested(xmlDataParser.getValueOf("NoOfLeavesRequested"));
				r.setOrigRefNumber(oldRefNo);	//change for old ref no
				req.setSRChequeBookReq2(r);
				
				cheque_book_Req_Msg.setSRChequeBookReqMsgChoice_type0(req);
				cheque_book_Req_Msg.setHeader(Header_Input);
			    
			    LogGEN.writeTrace("CBG_Log", "All values set");
			    
			    xmlInput=cheque_book.getinputXML(cheque_book_Req_Msg);
			    
			    cheque_book._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			    res_msg=cheque_book.addCBRequest_Oper(cheque_book_Req_Msg);
			    sOrg_Chk_ouput=cheque_book.resAddChkMsg;
			    Header_Input= res_msg.getHeader();
			    sReturnCode= Header_Input.getReturnCode();
			    sErrorDetail=Header_Input.getErrorDetail();
			    sErrorDesc = Header_Input.getErrorDescription();
			    
			    LogGEN.writeTrace("CBG_Log", "Return Code---"+sReturnCode);
			    LogGEN.writeTrace("CBG_Log", "Error Detail---"+sErrorDetail);
			    LogGEN.writeTrace("CBG_Log", "Error Description---"+sErrorDesc);
			    
			    
			    
			    if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
				{			    
			    	LogGEN.writeTrace("CBG_Log", "Successful Result");
			    	SRChequeBookResMsgChoice_type0 res_ch=new SRChequeBookResMsgChoice_type0();
			    	res_ch=res_msg.getSRChequeBookResMsgChoice_type0();
			    	SRChequeBookRes2_type0 res=new SRChequeBookRes2_type0();
			    	res=res_ch.getSRChequeBookRes2();
				    
				 
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
					"<Output>"+
						"<Option>Cheque_Book</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<returnCode>"+sReturnCode+"</returnCode><errorDetail>"+sErrorDetail+"</errorDetail>";						
						sOutput+="<ChequeBookSerialNo>"+res.getChequeBookSerialNo()+"</ChequeBookSerialNo>"+
						"<ChequeBookStatus>"+res.getChequeBookStatus()+"</ChequeBookStatus>"+
						"<ChequeEndNumber>"+res.getChequeEndNumber()+"</ChequeEndNumber>"+
						"<ChequePaidStatus>"+res.getChequePaidStatus()+"</ChequePaidStatus>"+
						"<ChequeStartNumber>"+res.getChequeStartNumber()+"</ChequeStartNumber>"+
						"<CustAccountNumber>"+res.getCustAccountNumber()+"</CustAccountNumber>"+
						"<FlagChequeBookType>"+res.getFlagChequeBookType()+"</FlagChequeBookType>"+
						"<FlagChequeType>"+res.getFlagChequeType()+"</FlagChequeType>"+
						"<FlagPRN>"+res.getFlagPRN()+"</FlagPRN>"+
						"<FlagServiceChargesWaiver>"+res.getFlagServiceChargesWaiver()+"</FlagServiceChargesWaiver>"+						
						"<NoOfLeavesRequested>"+res.getNoOfLeavesRequested()+"</NoOfLeavesRequested>"+
						"<UpdateSerialNo>"+res.getUpdateSerialNo()+"</UpdateSerialNo>"+
						"<Status>"+res.getStatus()+"</Status>"+
						"<Reason>"+res.getReason()+"</Reason>";
						sOutput+="</Output>";
						System.out.println(sOutput);
						LogGEN.writeTrace("CBG_Log", "Output XML--- "+sOutput);
				}
			    else
				{
			    	SRChequeBookResMsgChoice_type0 res_ch=new SRChequeBookResMsgChoice_type0();
			    	res_ch=res_msg.getSRChequeBookResMsgChoice_type0();
			    	SRChequeBookRes2_type0 res=new SRChequeBookRes2_type0();
			    	res=res_ch.getSRChequeBookRes2();
			    	LogGEN.writeTrace("CBG_Log", "Failed");
			    	sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>Cheque_Book</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>"+res.getStatus()+"</Status><Reason>"+res.getReason()+"</Reason><td>Unable to issue cheque book.</td></Output>";
				}
			    
			    
				
					 
			    
			}
			catch (Exception e)
			{
				LogGEN.writeTrace("CBG_Log", "Error in Web Serviice :"+e.toString());
				LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :"+e.getStackTrace());
				sReturnCode="1";
				//sErrorDetail=e.getMessage();
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>Cheque_Book</Option><returnCode>-1</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to issue cheque book.</td></Output>";
				e.printStackTrace();
			}
			finally
			{
				LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
				if(sOutput.trim().length()<1)
				{
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>Cheque_Book</Option><returnCode>-1</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to issue cheque book.</td></Output>";
				}
				String Status="";
				if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
				{
					Status="Success";
				}
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
					String sessionID= xmlDataParser.getValueOf("SessionId");
					String call_type=xmlDataParser.getValueOf("DSCOPCallType");
					sCabinet=xmlDataParser.getValueOf("EngineName");
					
					dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					// String outputxml=sOutput;
					 /*String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
						"and randomnumber='"+ sessionID +"'),'"+call_type+"','"+ inputXml.replaceAll("'", "''") +"',to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
						"'"+ outputxml.replaceAll("'", "''") +"',sysdate,'"+  Status + "')";
*/
					 try
					 {
						 dbpass=AESEncryption.decrypt(dbpass);
					 }
					 catch(Exception e)
					 {
						 
					 }
					 DBConnection con=new DBConnection();

					 String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
						"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
						"?,sysdate,'"+  Status + "')";
					 LogGEN.writeTrace("CBG_Log"," Add Cheque Query : finally :"+Query);
//					 LogGEN.writeTrace("CBG_Log","sOrg_Chk_ouput : finally :"+sOrg_Chk_ouput);
					 try {
						 con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_Chk_ouput.replaceAll("'", "''"));
					} catch (Exception e2) {
						// TODO: handle exception
						e2.getMessage();
					}
				LogGEN.writeTrace("CBG_Log","outputXML : finally :"+sOutput);
				return sOutput;			
			}
		}
}
