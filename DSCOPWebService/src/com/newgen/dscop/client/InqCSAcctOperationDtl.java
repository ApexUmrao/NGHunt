package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqCSAcctOperationDtlStub;
import com.newgen.dscop.stub.InqCSAcctOperationDtlStub.GetAcctOperationDetailsReq;
import com.newgen.dscop.stub.InqCSAcctOperationDtlStub.GetAcctOperationDetailsReqMsg;
import com.newgen.dscop.stub.InqCSAcctOperationDtlStub.GetAcctOperationDetailsReq_type0;
import com.newgen.dscop.stub.InqCSAcctOperationDtlStub.HeaderType;

public class InqCSAcctOperationDtl extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected long lTimeOut = 30000;
	String sOrg_Output=null;

	String xmlInput="";
	@SuppressWarnings("finally")
	public String inqCSAcctOperationDtl(String sInputXML) 
	{

		LogGEN.writeTrace("CBG_Log", "Fuction called InqCustomerLendingDtls");
		LogGEN.writeTrace("CBG_Log", "InqCustomerLendingDtls sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";
		String sOutput= "";

		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		DSCOPServiceHandler sHandler= new DSCOPServiceHandler();
		try
		{

			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("InqCSAcctOperationDtl");
			LogGEN.writeTrace("CBG_Log", "InqCSAcctOperationDtl WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "InqCSAcctOperationDtl WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "InqCSAcctOperationDtl CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "InqCSAcctOperationDtl USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "InqCSAcctOperationDtl PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "InqCSAcctOperationDtl LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "InqCSAcctOperationDtl TIME_OUT: "+lTimeOut);

			String ref_no=xmlDataParser.getValueOf("REF_NO");
			String custAcctNumber=xmlDataParser.getValueOf("CustAcctNumber");
			String siFlag=xmlDataParser.getValueOf("SIFlag");
			String holdFlag=xmlDataParser.getValueOf("HoldFlag");
			String sweepInFlag=xmlDataParser.getValueOf("SweepInFlag");
			String sweepOutFlag=xmlDataParser.getValueOf("SweepOutFlag");
			LogGEN.writeTrace("CBG_Log", "ref_no :"+ref_no);
			LogGEN.writeTrace("CBG_Log", "ref_no :"+ref_no);
			LogGEN.writeTrace("CBG_Log", "custAcctNumber :"+custAcctNumber);
			LogGEN.writeTrace("CBG_Log", "siFlag :"+siFlag);
			LogGEN.writeTrace("CBG_Log", "holdFlag :"+holdFlag);
			LogGEN.writeTrace("CBG_Log", "sweepInFlag :"+sweepInFlag);
			LogGEN.writeTrace("CBG_Log", "sweepOutFlag :"+sweepOutFlag);

			InqCSAcctOperationDtlStub acctBasic =new InqCSAcctOperationDtlStub(sWSDLPath);
			GetAcctOperationDetailsReq_type0 getAcctBasicDetailsReq_type0 = new GetAcctOperationDetailsReq_type0();
			GetAcctOperationDetailsReq getAcctBasicDetailsReq  = new GetAcctOperationDetailsReq();
			GetAcctOperationDetailsReqMsg getAcctBasicDetailsReqMsg = new GetAcctOperationDetailsReqMsg();
			HeaderType Header_Input = new HeaderType();

			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("InqCSAcctOperationDtl");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("senderID")));
			Header_Input.setConsumer(sHandler.setSenderId(xmlDataParser.getValueOf("consumer")));
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setCredentials(loggedinuser);
			Header_Input.setUsername(loggedinuser);


			if(siFlag != null || siFlag != "" || !siFlag.isEmpty())
			{
				getAcctBasicDetailsReq_type0.setSIFlag(siFlag);
			}
			if(sweepInFlag != null || sweepInFlag != "" || !sweepInFlag.isEmpty())
			{
				getAcctBasicDetailsReq_type0.setSweepInFlag(sweepInFlag);
			}
			if(custAcctNumber != null || custAcctNumber != "" || !custAcctNumber.isEmpty())
			{
				getAcctBasicDetailsReq_type0.setCustAcctNumber(custAcctNumber);
			}
			if(holdFlag != null || holdFlag != "" || !holdFlag.isEmpty())
			{
				getAcctBasicDetailsReq_type0.setHoldFlag(holdFlag);
			}
			if(sweepOutFlag != null || sweepOutFlag != "" || !sweepOutFlag.isEmpty())
			{
				getAcctBasicDetailsReq_type0.setSweepOutFlag(sweepOutFlag);
			}

			LogGEN.writeTrace("CBG_Log", "All values set11");

			getAcctBasicDetailsReq.setGetAcctOperationDetailsReq(getAcctBasicDetailsReq_type0);
			getAcctBasicDetailsReqMsg.setGetAcctOperationDetailsReq(getAcctBasicDetailsReq_type0);
			getAcctBasicDetailsReqMsg.setHeader(Header_Input);

			acctBasic._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			xmlInput=acctBasic.getInputXML(getAcctBasicDetailsReqMsg);
			LogGEN.writeTrace("CBG_Log", "inupt xml : "+xmlInput);
			String res_msg= acctBasic.inqCSAcctOperationDtl_Oper(getAcctBasicDetailsReqMsg);
			sOrg_Output = acctBasic.outputXML;
			LogGEN.writeTrace("CBG_Log", "output xml : "+sOrg_Output);
			com.newgen.omni.jts.cmgr.XMLParser parser = new com.newgen.omni.jts.cmgr.XMLParser(sOrg_Output);
			sReturnCode = parser.getValueOf("returnCode");
			sErrorDesc = parser.getValueOf("errorDescription");
			sErrorDetail = parser.getValueOf("errorDetail");
			
			
			
			/*HeaderType Header_output = new HeaderType();
			Header_output = res_msg.getHeader();

			sReturnCode=  Header_output.getReturnCode();
			sErrorDetail=Header_output.getErrorDetail();
			sErrorDesc = Header_output.getErrorDescription();*/
			LogGEN.writeTrace("CBG_Log", "sReturnCode :"+sReturnCode);
			LogGEN.writeTrace("CBG_Log", "sErrorDetail :"+sErrorDetail);
			LogGEN.writeTrace("CBG_Log", "sErrorDesc :"+sErrorDesc);

			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				/*GetAcctOperationDetailsRes_type0 fetchAccRes = new GetAcctOperationDetailsRes_type0();
				fetchAccRes=res_msg.getGetAcctOperationDetailsRes();

				sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>InqCSAcctOperationDtl</Option>" +
						"<header>"+
						"<usecaseID>"+Header_output.getUsecaseID()+"</usecaseID>"+
						"<serviceName>"+Header_output.getServiceName()+"</serviceName>"+
						"<versionNo>"+Header_output.getVersionNo()+"</versionNo>"+
						"<serviceAction>"+Header_output.getServiceAction()+"</serviceAction>"+
						"<correlationID>"+Header_output.getCorrelationID()+"</correlationID>"+
						"<sysRefNumber>"+Header_output.getSysRefNumber()+"</sysRefNumber>"+
						"<consumer>"+Header_output.getConsumer()+"</consumer>"+
						"<reqTimeStamp>"+Header_output.getRepTimeStamp()+"</reqTimeStamp>"+
						"<username>"+Header_output.getUsername()+"</username>"+
						"<credentials>"+Header_output.getCredentials()+"</credentials>"+
						"<returnCode>"+Header_output.getReturnCode()+"</returnCode>"+
						"<errorDescription>"+Header_output.getErrorDescription()+"</errorDescription>"+
						"<errorDetail>"+Header_output.getErrorDetail()+"</errorDetail>"+
						"</header>";

				if (fetchAccRes != null) 
				{	
					Acct_Ops_Details_type0  accType0 = fetchAccRes.getAcct_Ops_Details();
					sOutput += "<resultSet>"+
								"<Record>"+
							"<CHQRETURNED6MONTHS>"+accType0.getChqReturnedLastSixmonth()+"</CHQRETURNED6MONTHS>"+
							"<CHQRETURNED3MONTHS>"+accType0.getChqReturnedLastThreeMonth()+"</CHQRETURNED3MONTHS>"+
							"</Record>"+
							"</resultSet>";
					ChequeBookReqDetails_type0[]  chkDetails = accType0.getChequeBookReqDetails();
					sOutput +=  "<resultSet>"+
							"<ChequeBookReqDetails>";
							
					for (int i=0;i<chkDetails.length ; i++)
					{
						sOutput += "<Record>"+
								"<LastChequeBookIssueDate>"+chkDetails[i].getChequeBookReqDate()+"</LastChequeBookIssueDate>"+
								"</Record>";
					}
					
					sOutput +="</ChequeBookReqDetails></resultSet></Output>";
				}
				else
				{
					sOutput +=  "<accounts>No Data Found</accounts>"+
							"</acct_Basic_Details>"+
							"</GetAcctBasicDetailsRes></Output>";
				}*/
				sOutput = sOrg_Output;
			}
			else
			{
				try
				{

					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqCSAcctOperationDtl</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Reason></Reason><Status>Unable to perform InqCSAcctOperationDtl service</Status></Output>";
				}
				catch(Exception e)
				{
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqCSAcctOperationDtl</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Reason></Reason><Status>Unable to perform InqCSAcctOperationDtl service</Status></Output>";

				}
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqCSAcctOperationDtl</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>Unable to perform InqCSAcctOperationDtl service</Status></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());	

			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqCSAcctOperationDtl</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>Unable to perform InqCSAcctOperationDtl service</Status></Output>";
			}
			String Status="";
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
			{
				Status="Success";
			}
			else
				Status="Failure";

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

			try
			{
				dbpass=AESEncryption.decrypt(dbpass);
			}
			catch(Exception e)
			{
				LogGEN.writeTrace("CBG_Log", "Error Trace in AESEncryption :"+e.getStackTrace());
			}
			DBConnection con=new DBConnection();

			String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
			LogGEN.writeTrace("CBG_Log"," perform CS service finally :"+Query);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_Output.replaceAll("'", "''"));
			} catch (Exception e2) {
				// TODO: handle exception
			}
			return sOutput;			
		}
	}
}