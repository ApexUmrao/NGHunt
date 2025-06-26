package com.newgen.client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.stub.AddCasaAccountStub;
import com.newgen.stub.AddCasaAccountStub.AccountCBRCodes_type0;
import com.newgen.stub.AddCasaAccountStub.AddCasaAccountReq2_type0;
import com.newgen.stub.AddCasaAccountStub.AddCasaAccountReqMsg;
import com.newgen.stub.AddCasaAccountStub.AddCasaAccountReqMsgChoice_type0;
import com.newgen.stub.AddCasaAccountStub.AddCasaAccountRes2_type0;
import com.newgen.stub.AddCasaAccountStub.AddCasaAccountResMsg;
import com.newgen.stub.AddCasaAccountStub.AddCasaAccountResMsgChoice_type0;
import com.newgen.stub.AddCasaAccountStub.CBRCodes_type0;
import com.newgen.stub.AddCasaAccountStub.CustRel_type0;
import com.newgen.stub.AddCasaAccountStub.CustomerRelation_type0;
import com.newgen.stub.AddCasaAccountStub.CustomerRelation_type1;
import com.newgen.stub.AddCasaAccountStub.HeaderType;
import com.newgen.stub.AddCasaAccountStub.PrimaryRel_type0;
import com.newgen.stub.AddCasaAccountStub.PrimaryRelation_type0;

public class AddCASAAccount extends WebServiceHandler
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
	public String AddAccountToFCR(String sInputXML) 
	{
	
		LogGEN.writeTrace("Log", "Fuction called AddAccount");
		LogGEN.writeTrace("Log", "sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";
		String sOutput= "";
		String customerId=xmlDataParser.getValueOf("customerId");
		String productCode=xmlDataParser.getValueOf("productCode");
		String customerBranchCode=xmlDataParser.getValueOf("customerBranchCode");
		String accountStatusCode=xmlDataParser.getValueOf("accountStatusCode");
		String flagEmpAccount=xmlDataParser.getValueOf("flagEmpAccount");
		String flagATM=xmlDataParser.getValueOf("flagATM");
		String flagHoldMail=xmlDataParser.getValueOf("flagHoldMail");
		String flagMailAddCtrl=xmlDataParser.getValueOf("flagMailAddCtrl");
		String ctrlLeaves=xmlDataParser.getValueOf("ctrlLeaves");
		String statementCopies=xmlDataParser.getValueOf("statementCopies");
		String makerId=xmlDataParser.getValueOf("makerId");
		String checkerId=xmlDataParser.getValueOf("checkerId");
		String accTitle=xmlDataParser.getValueOf("accountTitle");
		String operatingIns=xmlDataParser.getValueOf("operatingInstruction");
		String oldRefNo=xmlDataParser.getValueOf("OLDREF_NO"); //change for old ref no
		///FCR changes
		String Record_Status=xmlDataParser.getValueOf("Record_Status");
		String No_Debit=xmlDataParser.getValueOf("No_Debit");
		String No_Credit=xmlDataParser.getValueOf("No_Credit");
		String Dormant=xmlDataParser.getValueOf("Dormant");
		String Frozen=xmlDataParser.getValueOf("Frozen");
		String Acc_Curr=xmlDataParser.getValueOf("Acc_Curr");
		String flagIB=xmlDataParser.getValueOf("flagIB");
		String flagIVR=xmlDataParser.getValueOf("flagIVR");
		String flagPOS=xmlDataParser.getValueOf("flagPOS");
		String currency=xmlDataParser.getValueOf("currency");
		
		String cbr="";
		TestXML xml1=new TestXML();
		try 
		{
			 cbr=xml1.getTagValue(sInputXML, "CBRCode");
			
		} 
		catch (Exception e1) 
		{
			System.out.println("errror11111111111111");
		}
		String[]cbrcode;
		if(!cbr.equalsIgnoreCase(""))
		{
			cbrcode=cbr.split(";");
		}
		else
			cbrcode=new String[0];
		
		
		
		String rels="";
		///get relations
		//TestXML xml1=new TestXML();
		try 
		{
			 rels=xml1.getTagValue(sInputXML, "CustRel");
			
		} 
		catch (Exception e1) 
		{
			System.out.println("errror11111111111111");
		}
		
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		WebServiceHandler sHandler= new WebServiceHandler();
		try
		{
			sHandler.readCabProperty("ADD_Account");
			
			sWSDLPath=(String)currentCabPropertyMap.get("WSDL_PATH");
			sCabinet=(String)currentCabPropertyMap.get("CABINET");
			sUser=(String)currentCabPropertyMap.get("USER");
			sLoginReq=Boolean.valueOf((String)currentCabPropertyMap.get("LOGIN_REQ"));
			sPassword=(String)currentCabPropertyMap.get("PASSWORD");
			lTimeOut=Long.valueOf((String)currentCabPropertyMap.get("TIMEOUT_INTERVAL"));
			
			LogGEN.writeTrace("Log", "read Property successfully");
			LogGEN.writeTrace("Log", "WSDL PATH---- "+(String)currentCabPropertyMap.get("WSDL_PATH"));
			LogGEN.writeTrace("Log", "CABINET---- "+(String)currentCabPropertyMap.get("CABINET"));
			LogGEN.writeTrace("Log", "USER---- "+(String)currentCabPropertyMap.get("USER"));
			LogGEN.writeTrace("Log", "PASSWORD---- "+(String)currentCabPropertyMap.get("PASSWORD"));
			LogGEN.writeTrace("Log", "LOGIN_REQ---- "+(String)currentCabPropertyMap.get("LOGIN_REQ"));
			LogGEN.writeTrace("Log", "TIME_OUT---- "+(String)currentCabPropertyMap.get("TIMEOUT_INTERVAL"));
			
			String sCustomerID= xmlDataParser.getValueOf("CUST_ID");	
			String ref_no=xmlDataParser.getValueOf("REF_NO");
			String codMod=xmlDataParser.getValueOf("maintainenceOption");
			String Accno=xmlDataParser.getValueOf("custAccountNumber");
			LogGEN.writeTrace("Log", "sCustomerID---"+sCustomerID);
			
			AddCasaAccountStub add_acc_stub=new AddCasaAccountStub(sWSDLPath);
			//AddCasaAccountReq_type0 add_cust_req=new AddCasaAccountReq_type0();
			AddCasaAccountReq2_type0 add_cust_req=new AddCasaAccountReq2_type0();
			AddCasaAccountReqMsg add_acc_req_msg=new AddCasaAccountReqMsg();
			
			HeaderType Header_Input = new HeaderType();
			
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("AddCasaAccount");
			Header_Input.setVersionNo("2.0");
			Header_Input.setServiceAction("Addition");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setUsername(sCustomerID);
			Header_Input.setCredentials(loggedinuser);
			
			LogGEN.writeTrace("Log", "All values set11");
			
			add_acc_req_msg.setHeader(Header_Input);
			
			AddCasaAccountReqMsgChoice_type0 req=new AddCasaAccountReqMsgChoice_type0();
			
			add_cust_req.setCustomerId(customerId);
			add_cust_req.setAccountStatusCode(accountStatusCode);
			add_cust_req.setCheckerId(checkerId);
			add_cust_req.setCtrlLeaves(ctrlLeaves);
			add_cust_req.setCustomerBranchCode(customerBranchCode);
			add_cust_req.setFlagATM(flagATM);
			add_cust_req.setFlagEmpAccount(flagEmpAccount);
			add_cust_req.setFlagHoldMail(flagHoldMail);
			add_cust_req.setFlagMailAddCtrl(flagMailAddCtrl);
			add_cust_req.setMakerId(makerId);
			add_cust_req.setProductCode(productCode);
			add_cust_req.setStatementCopies(statementCopies);
			add_cust_req.setAccountTitle(accTitle);
			add_cust_req.setOperatingInstruction(operatingIns);
			add_cust_req.setMaintenanceOption(codMod);
			add_cust_req.setAccountNumber(Accno);
			add_cust_req.setOrigRefNumber(oldRefNo);
			add_cust_req.setRecordStatus(Record_Status);
			add_cust_req.setFlagNoCredit(No_Credit);
			add_cust_req.setFlagNoDebit(No_Debit);
			add_cust_req.setFlagAcctDormant(Dormant);
			add_cust_req.setFlagAcctFrozen(Frozen);
			add_cust_req.setAccountCcyCode(Acc_Curr);
			add_cust_req.setFlagIB(flagIB);
			add_cust_req.setFlagIVR(flagIVR);
			add_cust_req.setFlagPOS(flagPOS);
			add_cust_req.setAccountCcyCode(currency);
			
			
			String[]relations;
			if(!rels.equalsIgnoreCase(""))
			{
				relations=rels.split(";");
			}
			else
				relations=new String[0];
			
			LogGEN.writeTrace("Log", rels);
			LogGEN.writeTrace("Log", "234r"+relations.length );
			
			CustomerRelation_type1 cust=new CustomerRelation_type1();
			PrimaryRelation_type0 ptype=new PrimaryRelation_type0();
			PrimaryRel_type0[] pritype=new PrimaryRel_type0[1];
			
			pritype[0]=new PrimaryRel_type0();
			pritype[0].setAccountNumber("");
			pritype[0].setRelationship(relations[0].split(",")[1]);
			LogGEN.writeTrace("Log", "running" );
			ptype.setPrimaryRel(pritype);
			
			LogGEN.writeTrace("Log", "Relation:"+relations[0].split(",")[1]);
			CustomerRelation_type0 ctype=new CustomerRelation_type0();
			CustRel_type0[] c=new CustRel_type0[relations.length-1];
			for(int i=1;i<relations.length;i++)
			{	
				String[] r=relations[i].split(",");
				c[i-1]=new CustRel_type0();
				c[i-1].setRelatedCustomerId(r[0]);
				c[i-1].setCustomerRelationship(r[1]);
			}
			ctype.setCustRel(c);
			cust.setPrimaryRelation(ptype);
			cust.setCustomerRelation(ctype);
			add_cust_req.setCustomerRelation(cust);
			
			AccountCBRCodes_type0 cod=new AccountCBRCodes_type0();
			CBRCodes_type0[] cbr1=new CBRCodes_type0[cbrcode.length];
			for(int i=0;i<cbrcode.length;i++)
			{
				String[] r=cbrcode[i].split(",");
				cbr1[i]=new CBRCodes_type0();
				cbr1[i].setCBRCode(r[0]);
				cbr1[i].setCBRValue(r[1]);
			}
			
			
			cod.setCBRCodes(cbr1);
			add_cust_req.setAccountCBRCodes(cod);
			
			
			req.setAddCasaAccountReq2(add_cust_req);
			
			add_acc_req_msg.setAddCasaAccountReqMsgChoice_type0(req);
			add_acc_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			xmlInput=add_acc_stub.getinputXML(add_acc_req_msg);
			AddCasaAccountResMsg add_acc_res_msg= add_acc_stub.addCasaAccount_Oper(add_acc_req_msg);
			sOrg_Output=add_acc_stub.resCasaMsg;
			Header_Input=add_acc_res_msg.getHeader();
			sReturnCode=  Header_Input.getReturnCode();
			sErrorDetail=Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();
			System.out.println(sErrorDetail);
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				AddCasaAccountResMsgChoice_type0 add_cust_res=new AddCasaAccountResMsgChoice_type0();
				add_cust_res=add_acc_res_msg.getAddCasaAccountResMsgChoice_type0();
				AddCasaAccountRes2_type0 add_res=new AddCasaAccountRes2_type0();
				add_res=add_cust_res.getAddCasaAccountRes2();
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<Output>"+
					"<Option>ADD_Account</Option>" +
					"<returnCode>"+sReturnCode+"</returnCode>" +
					"<errorDescription>"+sErrorDetail+"</errorDescription>"+
					"<AddAccountRes>"+
						"<customerId>"+ add_res.getCustomerId() +"</customerId>"+
						"<newAccountNumber>"+add_res.getNewAccountNumber()+"</newAccountNumber>"+
						"<accountTitle>"+add_res.getAccountTitle()+"</accountTitle>"+						
						"<productCode>"+add_res.getProductCode()+"</productCode>"+
						"<currency>"+add_res.getAccountCcyCode()+"</currency>"+
						"<customerBranchCode>"+add_res.getCustomerBranchCode()+"</customerBranchCode>"+
						"<accountNewStatus>"+ add_res.getRecordStatus()+add_res.getFlagNoDebit()+add_res.getFlagNoCredit()+add_res.getFlagAcctDormant()+add_res.getFlagAcctFrozen()+"</accountNewStatus>"+
						"<accountStatusCode>"+add_res.getAccountStatusCode()+"</accountStatusCode>"+
						"<flagEmpAccount>"+add_res.getFlagEmpAccount()+"</flagEmpAccount>"+
						"<flagATM>"+add_res.getFlagATM()+"</flagATM>"+
						"<flagHoldMail>"+add_res.getFlagHoldMail()+"</flagHoldMail>"+
						"<flagMailAddCtrl>"+add_res.getFlagMailAddCtrl()+"</flagMailAddCtrl>"+
						"<ctrlLeaves>"+add_res.getCtrlLeaves()+"</ctrlLeaves>"+
						"<statementCopies>"+add_res.getStatementCopies()+"</statementCopies>"+
						"<NewAccountIBAN>"+add_res.getNewAccountIBAN()+"</NewAccountIBAN>"+
						"<makerId>"+add_res.getMakerId()+"</makerId>"+
						"<checkerId>"+add_res.getCheckerId()+"</checkerId>"+
						"<status>"+add_res.getStatus()+"</status>"+
						"<reason>"+add_res.getReason()+"</reason>"+
						"<UKAccNumber>"+add_res.getNewAccountNumberUK()+"</UKAccNumber>";
						//CustomerRelation_type1 cust_rel=new CustomerRelation_type1();
						//cust_rel=add_res.getCustomerRelation();
					//	CustRel_type1[] rel;
						System.out.println(sOutput);
						//rel=cust_rel.getCustRel();
						/*sOutput+="<customerRelation>";
						for(int i=0;i<rel.length;i++)
						{
							sOutput+="<custRel>"+
							"<customerId>"+ rel[i].getCustomerId()+"</customerId>"+
							"<accountCustRelation>"+ rel[i].getAccountCustRelation()+"</accountCustRelation>"+
							"<custRel>";
						}*/
						sOutput+="</customerRelation>";
				sOutput+="</AddAccountRes>"+	
					"</Output>";
			}
			else
			{
				try
				{
				AddCasaAccountResMsgChoice_type0 add_cust_res=new AddCasaAccountResMsgChoice_type0();
				add_cust_res=add_acc_res_msg.getAddCasaAccountResMsgChoice_type0();
				AddCasaAccountRes2_type0 add_res=new AddCasaAccountRes2_type0();
				add_res=add_cust_res.getAddCasaAccountRes2();
				
				
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADD_Account</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Reason>"+add_res.getStatus()+"</Reason><Status>"+add_res.getReason()+"</Status><td>Unable to add Account.</td></Output>";
				}
				catch(Exception e)
				{
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADD_Account</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Reason></Reason><Status></Status><td>Unable to add Account.</td></Output>";

			}
			}
			
				 
				 
						
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADD_Account</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to add Account.</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput.trim().length());	
			LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput);	

			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADD_Account</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to add Account.</td></Output>";
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
				LogGEN.writeTrace("Log", inputXml);
				String winame=xmlDataParser.getValueOf("WiName");
				String sessionID= xmlDataParser.getValueOf("SessionId");
				String call_type=xmlDataParser.getValueOf("Calltype");
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
				  /**
				  * //Added By Harish For inserting original mssg
				  * Date			:	17 Mar 2015
				  * Description 	:	Replace execute with executeClob
				  */
				 String Query="insert into usr_0_acct_creation_web_log(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
				 LogGEN.writeTrace("Log","ADD CASA Account Query : finally :"+Query);
				 LogGEN.writeTrace("Log","sOrg_Output : finally :"+sOrg_Output);
				 try {
					 con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_Output.replaceAll("'", "''"));
				} catch (Exception e2) {
					// TODO: handle exception
				}
				
			 //End Here
			return sOutput;			
		}
	}
}


