package com.newgen;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.enumeration.search.FolderTraversal;
import microsoft.exchange.webservices.data.core.enumeration.service.ConflictResolutionMode;
import microsoft.exchange.webservices.data.core.enumeration.service.DeleteMode;
import microsoft.exchange.webservices.data.core.service.folder.Folder;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.core.service.schema.FolderSchema;
import microsoft.exchange.webservices.data.property.complex.EmailAddress;
import microsoft.exchange.webservices.data.property.complex.EmailAddressCollection;
import microsoft.exchange.webservices.data.property.complex.FolderId;
import microsoft.exchange.webservices.data.search.FindFoldersResults;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.FolderView;
import microsoft.exchange.webservices.data.search.ItemView;
import microsoft.exchange.webservices.data.search.filter.SearchFilter;

import org.apache.log4j.Logger;




public class EmailApprovalUtility
{
	static public int enablelogging=0;
	static public int ProcessMode=-1;
	static public String EmailAccountPassword="";
	static public String EmailAccount="";
	static String sMessage="";
	static boolean sFlag=false;	
	static String body="";
	static String sEmailSmtp;
	static String sSecEmailSmtp;
	static String emailFolder;
	static int iEmailPort;
	String trnsFetchData="";
	boolean bTrnsFetchFlag=false;
	String trnsUData="";
	boolean bTrnsUFlag=false;
	String qWmsFetchCall="";
	boolean bQWmsFetchCall=false;
	String wmsCompleteData="";
	boolean bWmsCompleteData=false;
	private long prevUpdateTime = System.currentTimeMillis();
	static HashMap<String ,String> mapGrp=null;
	String wmsLockData="";
	boolean bWmsLockData=false;
	int dcCpFlag=0; //Distortion and completion flag
	public int retryCount = 0;
	XMLParser xmlParser=null;
	static public String MovedEmailFolder="";
	private static Logger logger = Logger.getLogger("consoleLogger");

	public static void main(String[] args) 
	{ 
		new EmailApprovalUtility().startReadMailBox();
	}

	private EmailExchange setExchangeConncetToMailServer(){
		EmailExchange emailexchange = null;
		try {
			emailexchange = EmailExchange.getInstance();
			writeCustLog(enablelogging,"session id validity for trylog5: TradeWorkflow.Test");
			writeCustLog(enablelogging, "After setting session property");
			try {
				emailexchange.getService().setPreAuthenticate(true);
				writeCustLog(enablelogging,"After setPreAuthentication");
				emailexchange.getService().validate();
				writeCustLog(enablelogging,"After EWS Validation ");
			} catch (Exception e) {
				Thread.sleep(LoadConfiguration.reconnectInterval);
				try {
					emailexchange.getService().setPreAuthenticate(true);
					writeCustLog(enablelogging,"After setPreAuthentication");
					emailexchange.getService().validate();
					writeCustLog(enablelogging,"After EWS Validation ");
				} catch (Exception e1) {
					handleUtilityException(e1.toString());
					Common.setCustomServiceStatus("-25033","Stopped",0+"");
					System.exit(0);
				}
			}
			writeCustLog(enablelogging, "After connect");
		} 
		catch (Exception e) { 
			customException("setExchangeConncetToMailServer MessagingException ",e);
		}
		return emailexchange;
	}
	
	private void startReadMailBox(){
		try{
			if(!readPropertiesFile())
				return ;
			ArrayList<String> aUID2 = new ArrayList<String>();
			
			while(true){
				int i = 0;				
				int Max_Count = 20;
				int Min_Count = 20;
				int BatchSize=20;
				String subWiName="";												//  Work item variable 
				String sRemarks="";													//	Email Comment variable
				String subDecName="";												// 	Decision variable
				String subWsName="";												//	Activity Name variable
				String sSubject="";													//  To Get Subject from mail
				String tokenData="";												// 	Token Data variable
				String sSender="";													//  To Get Sender from mail
				String msgBody="";													//  To Get Message body from mail
				String sSenderId="";												//	Parse Sender Id			
				Folder inbox;
				EmailExchange emailexchange = null;
				EmailMessage amessage = null;
				EmailAddressCollection cc_recipients = null;
	            EmailAddressCollection bcc_recipients = null;
	            String final_cc_recipients = "";
	            String final_bcc_recipients = "";
				
				try{
					boolean isSessionValid = isSessionIdValid(LoadConfiguration.sessionID);
					writeCustLog(enablelogging,"session id validity for: ");
					if(!isSessionValid){
						connectToWorkFlow();
					}					
					emailexchange = setExchangeConncetToMailServer();
				    inbox = Folder.bind(emailexchange.getService(), WellKnownFolderName.Inbox);
					ItemView view = new ItemView(emailexchange.getNUMBER_EMAILS_FETCH());
		            FindItemsResults<Item> finResults  = emailexchange.getService().findItems(inbox.getId(), view);
		            writeCustLog(enablelogging," finResults :"+finResults.getTotalCount() +":ITEMS : " +finResults.getItems());
		            ArrayList<Item> items = finResults.getItems();
		            writeCustLog(enablelogging,"items.size() :"+items.size());
					
					writeCustLog(enablelogging,"Message fetching successful ::"
							+ " messages found--"+items.size()+"Before Max count="+Max_Count + 
							"\n before Min_Count="+Min_Count);					
					if(items.size() < Max_Count){
						Max_Count = items.size();			
						Min_Count=0;
					}
					if (ProcessMode==0){
						Max_Count = items.size();
						Min_Count=0;
					}
					if (ProcessMode==0 && items.size() > Max_Count)
					{			   
						Min_Count=items.size()-BatchSize;
						Max_Count=items.size();
					}
					writeCustLog(enablelogging,"After Max count="+Max_Count + "\n before Min_Count="+Min_Count);													
					if(Max_Count > 0){
						for(i=(Max_Count-1); i >=Min_Count ; i--){
							System.out.println("Couter "+i);
							try{
								String sUID = items.get(i).getId().toString();   
								amessage = EmailMessage.bind(emailexchange.getService(), items.get(i).getId());
								if(aUID2.contains(sUID)){
									amessage.delete(DeleteMode.HardDelete);//.setFlag(javax.mail.Flags.Flag.DELETED, true); 		// If message are to be deleted
									writeCustLog(enablelogging,"Mail mark for delete....!!!");
								}
								writeCustLog(enablelogging,"Message UID for message "+i+" --"+sUID );
								if(sUID == null){
									Common.setCustomServiceStatus("-14007","No more Workitems available",0+"");
									continue;
								}
								Common.setCustomServiceStatus("14017","Processing Workitem",1+"");
								sSubject=amessage.getSubject().toString();				
								sSender=amessage.getSender().toString();					
								msgBody=amessage.getBody().toString().replaceAll("\\<.*?>","").replace("&nbsp;", "");
								String recp_adrs= amessage.getReceivedBy().getAddress().toString();
								sSenderId=getSenderId(sSender);								
							    cc_recipients = amessage.getCcRecipients();
	                            bcc_recipients = amessage.getBccRecipients();
	                            final_cc_recipients = getRecipientsAsString(cc_recipients);
	                            final_bcc_recipients = getRecipientsAsString(bcc_recipients);
	                            
	                         // Log the recipients
	                            writeCustLog(enablelogging, "CC Recipients: " + final_cc_recipients);
	                            writeCustLog(enablelogging, "BCC Recipients: " + final_bcc_recipients);
	                            writeCustLog(enablelogging, "Source EmailId: " + EmailAccount);
	                            this.writeCustLog(enablelogging, "sSubject :: " + sSubject + " sSender ::" + sSender + " msgBody :" + msgBody + " recp_adrs :" + recp_adrs + " cc_recipients :" + final_cc_recipients + " bcc_recipients :" + final_bcc_recipients);
								dcCpFlag=0;
								writeCustLog(enablelogging,"subDecName :: "+subDecName);
							
								moveEmailToExistingFolderAndMarkUnread(emailexchange.getService(), amessage, MovedEmailFolder);

							}catch(Throwable e){
								//System.out.println(e);
								e.printStackTrace();
								//customException("Exception while reading mail box",e);
							}finally{
								writeCustLog(enablelogging,"finally :: ");

								try {
									
									writeCustLog(enablelogging,"reyaz  :: ");
									inserClobData(subWiName, subWsName, subWsName, sSenderId, msgBody, sSubject,subDecName,sRemarks,tokenData,dcCpFlag,final_cc_recipients,final_bcc_recipients,EmailAccount);
									subWiName="";												//  Work item variable 
									sRemarks="";												//	Email Comment variable
									subDecName="";												// 	Decision variable
									subWsName="";												//	Activity Name variable
									sSubject="";												//  To Get Subject from mail
									tokenData="";												// 	Token Data variable
									sSender="";													//  To Get Sender from mail
									msgBody="";													//  To Get Message body from mail
									sSenderId="";												//	Parse Sender Id

								} catch (Exception e) {
									customException("Error while caliing inserClobData ",e);
								}
							}
						}
					}else {
						Common.setCustomServiceStatus("-14007","No more Workitems available",0+"");
						try {
						
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				catch(Exception exception){
					customException("Exception in startReadMailBox ",exception);
				}
				finally{
					try {
						Thread.sleep(LoadConfiguration.sleepTime);
						System.out.println("new thread...... .... ...");
					} 
					catch (InterruptedException ignore) {
						customException("InterruptedException ",ignore);
					}
				}
			}
		}
		catch(Exception exception)
		{
			customException("startReadMailBox ",exception);
			Common.setCustomServiceStatus("-25033","Stopped",0+"");
			handleUtilityException(exception.toString());
			System.exit(0);
		}

	}
	private boolean isSessionIdValid(String sessionId) {
		String sOutputXML;
		String sInputXML;
		try {
			logger.info("[isSessionIdValid]searchTimestamp : " + prevUpdateTime);
			long prevTime = System.currentTimeMillis() - LoadConfiguration.sessionInterval;
			logger.info("[isSessionIdValid]prevTime : " + prevTime);
			if (prevUpdateTime < prevTime) {
				prevUpdateTime = System.currentTimeMillis();
			sInputXML = GenerateXml.procCallInputXml(LoadConfiguration.cabinetName ,sessionId,"BPM_SESSION_UPDATE","'"+sessionId+"'");
			sOutputXML =  ExecuteXML.executeXML(sInputXML.toString());
			logger.info("WFSESSIONVIEW update : "+sOutputXML);
			}
			writeCustLog(enablelogging,"session id check starts for : "+sessionId);
			sOutputXML = GenerateXml.IsSessionValid(sessionId, LoadConfiguration.cabinetName);
			writeCustLog(enablelogging,"session id check sOutputXML : "+sOutputXML);
			XMLParser xp=new XMLParser(sOutputXML);   
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if (mainCode != 0){
				return false;
			}
		} catch (Exception e) {
			customException("Error  while reading config file...", e);
		}
		return true;
	}
	
	
	private void handleUtilityException(String exception) {
		String inputXml = "";
		String outputXML = "";
		String mailBody = "";
		try {
			exception = exception.replaceAll("'", "''"); 
			writeCustLog(enablelogging,"Inside handleLoginException");
			writeCustLog(enablelogging, "cab: "+LoadConfiguration.cabinetName);
			writeCustLog(enablelogging, "session: "+LoadConfiguration.sessionID);
			writeCustLog(enablelogging, "exception: "+exception);
			inputXml = GenerateXml.apInsertInputXml(LoadConfiguration.cabinetName, LoadConfiguration.sessionID,
					"BPM_UTILITY_EXCEPTION", "UTILITY_NAME,EXCEPTION_TIME,EXCEPTION_DESC", "'EmailApprovalUtility',SYSDATE,'"+exception+"'");
			writeCustLog(enablelogging, "outputXml: "+inputXml);
			outputXML = ExecuteXML.executeXML(inputXml.toString());
			writeCustLog(enablelogging, "outputXml: "+outputXML);
			mailBody = LoadConfiguration.exceptionMail.replaceAll("&<UTILITYNAME>&", "Email Approval Utility");
			mailBody = mailBody.replaceAll("&<REASON>&", exception);
			inputXml = GenerateXml.apInsertInputXml(LoadConfiguration.cabinetName, LoadConfiguration.sessionID,
					"WFMAILQUEUETABLE", "MAILFROM,MAILTO,MAILCC,MAILSUBJECT,MAILMESSAGE,MAILCONTENTTYPE,ATTACHMENTISINDEX,ATTACHMENTNAMES,ATTACHMENTEXTS,MAILPRIORITY,MAILSTATUS,STATUSCOMMENTS,LOCKEDBY,SUCCESSTIME,LASTLOCKTIME,INSERTEDBY,MAILACTIONTYPE,INSERTEDTIME,PROCESSDEFID,PROCESSINSTANCEID,WORKITEMID,ACTIVITYID,NOOFTRIALS",
					"'"+LoadConfiguration.exceptionFrom+"','"+LoadConfiguration.exceptionTo+"','"+LoadConfiguration.exceptionCC+"'" +
							",'"+LoadConfiguration.exceptionSubject+"','"+mailBody+"'" +
					",'text/html;charset=UTF-8','','','',1,'N','','','','','MailUtilItyUser1','TRIGGER',SYSDATE,'10','EmailUtility','1','10','0'");
			writeCustLog(enablelogging, "outputXml: "+inputXml);
			outputXML = ExecuteXML.executeXML(inputXml.toString());
			writeCustLog(enablelogging, "outputXml: "+outputXML);
		} catch (Exception exc) {
			customException("handleLoginException MessagingException ",exc);
		}

	}

	public boolean readPropertiesFile(){
		try{
			writeCustLog(enablelogging,"Start read successfully");
			LoadConfiguration.sUserDir=System.getProperty("user.dir");
			Properties config= new Properties();
			config.load(new FileInputStream(LoadConfiguration.sUserDir+File.separator+"Config"+File.separator+"Utility.properties"));

			LoadConfiguration.cabinetName=config.getProperty("cabinetName").trim();
			LoadConfiguration.jtsIP=config.getProperty("jtsIP").trim();
			LoadConfiguration.jtsPort=Integer.parseInt(config.getProperty("jtsPort").trim());
			LoadConfiguration.userName=config.getProperty("userName").trim();			
			LoadConfiguration.userPassword=customDecypt(config.getProperty("userPassword").trim());										
			LoadConfiguration.processdefID=config.getProperty("processdefID").trim();			
			LoadConfiguration.poolingTime=Long.parseLong(config.getProperty("poolingTime").trim());			
			LoadConfiguration.reconnectInterval=Long.parseLong(config.getProperty("reconnectInterval").trim());			
			LoadConfiguration.logEnable=config.getProperty("logEnable").trim();
			LoadConfiguration.EmailAccount=config.getProperty("emailUserName").trim();			
			LoadConfiguration.EmailAccountPassword=customDecypt(config.getProperty("emailPassword").trim());
			LoadConfiguration.emailPop3=config.getProperty("emailSMTP").trim();
			LoadConfiguration.emailFolder=config.getProperty("emailFolder").trim();
			LoadConfiguration.emailPort=config.getProperty("emailPort").trim();
			LoadConfiguration.emialProtocal=config.getProperty("emialProtocal").trim();	
			LoadConfiguration.emailTokenLine=config.getProperty("emailTokenLine").trim();
			LoadConfiguration.emailEndTokenLine=config.getProperty("emailEndTokenLine").trim();
			LoadConfiguration.startComment=config.getProperty("startComment").trim();
			LoadConfiguration.endComment=config.getProperty("endComment").trim();
			LoadConfiguration.buttonDecision =config.getProperty("buttonDecision").trim();
			LoadConfiguration.dbUrl=config.getProperty("dbUrl").trim();
			LoadConfiguration.dbUser=config.getProperty("dbUser").trim();
			LoadConfiguration.dbPassword=customDecypt(config.getProperty("dbPassword")).trim();
			LoadConfiguration.masterLog= config.getProperty("masterLog").trim();
			LoadConfiguration.ngApiLog= config.getProperty("NGApiLog").trim();
			LoadConfiguration.sleepTime=Long.parseLong(config.getProperty("sleepTime"));
			LoadConfiguration.exceptionFrom = config.getProperty("exceptionFrom").trim();
			LoadConfiguration.exceptionTo = config.getProperty("exceptionTo").trim();
			LoadConfiguration.exceptionCC = config.getProperty("exceptionCC").trim();
			LoadConfiguration.exceptionSubject = config.getProperty("exceptionSubject").trim();
			LoadConfiguration.exceptionMail = config.getProperty("exceptionMail").trim();
			LoadConfiguration.domain= config.getProperty("domain").trim();
			LoadConfiguration.exchangeServerURL= config.getProperty("exchangeServerURL").trim();
			LoadConfiguration.sessionInterval = Long.parseLong(config.getProperty("sessionInterval").trim()) * 60 * 1000;
			EmailAccount=LoadConfiguration.EmailAccount;
			EmailAccountPassword=LoadConfiguration.EmailAccountPassword;
			enablelogging=2;
			ProcessMode=0;
			LoadConfiguration.MovedEmail = config.getProperty("MovedEmail").trim();
			MovedEmailFolder =LoadConfiguration.MovedEmail;
			writeCustLog(enablelogging,"ini read successfully" + "\n LoadConfiguration.cabinetName "+LoadConfiguration.cabinetName
					+"\n LoadConfiguration.jtsIP "+LoadConfiguration.jtsIP
					+"\n LoadConfiguration.jtsPort "+LoadConfiguration.jtsPort
					+"\n wmsUserName "+LoadConfiguration.userName
					+"\n dbUrl "+LoadConfiguration.dbUrl
					+"\n dbUser "+LoadConfiguration.dbUser
					//+"\n dbPassword "+LoadConfiguration.dbPassword					
					//+"\n wmsUserPassword "+LoadConfiguration.userPassword
					+"\n processdefID "+LoadConfiguration.processdefID
					+"\n poolingTime "+LoadConfiguration.poolingTime
					+"\n reconnectInterval "+LoadConfiguration.reconnectInterval
					+"\n logEnable "+LoadConfiguration.logEnable					
					+"\n EmailAccount "+LoadConfiguration.EmailAccount
					+"\n EmailAccountPassword "+LoadConfiguration.EmailAccountPassword
					+"\n emailPop3 "+LoadConfiguration.emailPop3
					+"\n emailFolder "+LoadConfiguration.emailFolder
					+"\n emailTokenLine "+LoadConfiguration.emailTokenLine
					+"\n emailEndTokenLine "+LoadConfiguration.emailEndTokenLine
					+"\n startComment "+LoadConfiguration.startComment
					+"\n endComment "+LoadConfiguration.endComment
					+"\n buttonDecision "+LoadConfiguration.buttonDecision
					+"\n MovedEmailFolder "+LoadConfiguration.MovedEmail);
			writeCustLog(enablelogging,"ini read successfully" );
			try {
				File myObj = new File("WFServiceConfig.ini");
				Scanner myReader = new Scanner(myObj);
				while (myReader.hasNextLine()) {
					String data = myReader.nextLine();
					if(data != null && !"".equalsIgnoreCase(data))
					{
						LoadConfiguration.pid=data.split("=")[1];
					}
				}
				myReader.close();
				writeCustLog(enablelogging, "pid : "+LoadConfiguration.pid);
			} catch (FileNotFoundException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
			try {
				ExecuteXML.init("Websphere", LoadConfiguration.jtsIP, String.valueOf(LoadConfiguration.jtsPort));
				String sOutputXML= connectToWorkFlow();
				if(checkMainCode(sOutputXML)){
					LoadConfiguration.sessionID=Common.getTagValue(sOutputXML,"SessionId");
					customLog(enablelogging,"Connected to WMS and session id is "+LoadConfiguration.sessionID );					
					Common.setCustomServiceStatus("14011","Started","0");
					Common.setCustomServiceStatus("-14007","No more Workitems available",0+"");
					return true;
				}
			} catch (Exception e) {
				customException("Exception occur while conencting the workflow",e);				
			}			
			return true;
		}catch(Exception e){
			customException("Error while reading config readPropertiesFile ",e);
			return false;
		}
	}	

	private String connectToWorkFlow()
	{
		logger.info("Trying to Connect WorkFlow...");		
		try 
		{
			String sInputXML=GenerateXml.getWMConnectInputXML(LoadConfiguration.cabinetName,LoadConfiguration.userName,LoadConfiguration.userPassword);
			writeCustLog(enablelogging,ModifyInputXML(sInputXML));
			String sOutputXML="";
			sOutputXML = Common.exceuteApi(sInputXML);
			writeCustLog(enablelogging,"WMConnect call output  :  "+sOutputXML);
			if(checkMainCode(sOutputXML)){
				LoadConfiguration.sessionID = Common.getTagValue(sOutputXML,"SessionId");	
			}
			return sOutputXML;
		}
		catch (Exception e)	{
			customException("Exception occur while conencting the workflow",e);	
			return "";
		}		
	}

	public String ModifyInputXML(String sInputXML)
	{
		String sNewInputXML=sInputXML.replace(sInputXML.substring(sInputXML.indexOf("<Password>")+10, sInputXML.indexOf("</Password>")),"");
		return sNewInputXML;
	}
	

	
	private  String customDecypt(String encyptTxt){
		try {
			if(encyptTxt!=null){
				encyptTxt=encyptTxt.trim();
				return AESEncryption.decrypt(encyptTxt);
			}			
		} catch (Exception e) {
			customException("Exception customDecypt",e);
		}
		return "";
	}



	private boolean checkMainCode(String sOutputXML){
		if(sOutputXML==null || "".equalsIgnoreCase(sOutputXML)){
			return false;
		}else if("0".equalsIgnoreCase(Common.getTagValue(sOutputXML,"MainCode"))){
			return true;
		}else{			
			return false;
		}
	}

	
	private void writeCustLog(int enablelogging,String msg)
	{
		logger.info(msg);
	}

	private void customLog(int enablelogging,String msg)
	{
		if("Y".equalsIgnoreCase(LoadConfiguration.logEnable))
			logger.info(msg);
		else if("Y".equalsIgnoreCase(LoadConfiguration.masterLog))
			logger.info(msg);
	}
	

	private String getSenderId(String sSender){
		try {
			customLog(enablelogging, "Sender Email id Detail "+sSender);
			if(!sSender.isEmpty() && sSender.indexOf(">") >-1 && sSender.indexOf("<") >-1 )
				sSender=sSender.substring(sSender.indexOf("<")+1,sSender.indexOf(">"));			
		} catch (Exception e) {
			customException("Exception getSenderId",e);
		}
		if(sSender!=null)
			sSender=sSender.trim();
		return sSender;
	}

	// Helper method to convert EmailAddressCollection to a comma-separated String
	private static String getRecipientsAsString(EmailAddressCollection recipients) {
	    if (recipients != null && recipients.getCount() > 0) {
	        StringBuilder sb = new StringBuilder();
	        
	        // Iterate through the recipients and append their addresses to the StringBuilder
	        for (EmailAddress address : recipients) {
	            sb.append(address.getAddress()).append(", ");
	        }
	        
	        // Remove the trailing comma and space by checking the length before removing
	        if (sb.length() > 2) {
	            sb.setLength(sb.length() - 2); // Remove the last ", "
	        }

	        return sb.toString();
	    } else {
	        return "";
	    }
	}
	
	
	public void moveEmailToExistingFolderAndMarkUnread(ExchangeService service, EmailMessage message, String targetFolderName) {
		try {
			FolderId targetFolderId = null;


			writeCustLog(enablelogging, "moveEmailToExistingFolderAndMarkUnread");
			// Search for existing folder named "MovedEmail"
			FolderView folderView = new FolderView(50);
			folderView.setTraversal(FolderTraversal.Deep);
			SearchFilter searchFilter = new SearchFilter.IsEqualTo(FolderSchema.DisplayName, targetFolderName);
			FindFoldersResults findFolderResults = service.findFolders(WellKnownFolderName.MsgFolderRoot,searchFilter,folderView);

			if (!findFolderResults.getFolders().isEmpty()) {
				targetFolderId = findFolderResults.getFolders().get(0).getId();

				// Move the message
				Item movedItem = message.move(targetFolderId);

				// Reload the moved item as EmailMessage
				EmailMessage movedEmail = EmailMessage.bind(service, movedItem.getId());

				// Mark as unread
				movedEmail.setIsRead(false);
				movedEmail.update(ConflictResolutionMode.AutoResolve);

				writeCustLog(enablelogging, "Email moved to '" + targetFolderName + "' and marked as UNREAD.");
			} else {
				writeCustLog(enablelogging, "Folder '" + targetFolderName + "' not found. Email not moved.");
			}
		} catch (Exception e) {
			customException("Exception while moving and marking email unread to folder: " + targetFolderName, e);
		}
	}


	private void customException(String called, Exception e){
		try {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			writeCustLog(enablelogging,"Exception occur 0 >>>><<<<<"+called +" >>> \n"+sw.toString() );
		} catch (Exception e1) {
			e.printStackTrace();
		}
	}
	
	 private void inserClobData(String wiName, String activityName, String refTo, String resEmailId, String emailBody, String subject, String decision, String resRemarks, String tokenRcv, int distortionId, String cc, String bcc,String souceEmailId) {
	        writeCustLog(enablelogging, "inserClobData ");
   
		 try {
			 writeCustLog(enablelogging, "inserClobData "+resEmailId+" "+emailBody); 
			 if (!resEmailId.isEmpty() && !emailBody.isEmpty()) {
		         writeCustLog(enablelogging, "reyaz ");
		        String Query = "INSERT INTO TFO_DJ_EMAIL_REF_RESPNS_LOG (WI_NAME,ACTIVITYNAME,REFERRED_TO,RECEIVEDFROM_MAIL_ID,EMAIL_CONTENT,EMAIL_SUBJECT,ACTIONRECEIVED,RESPONSEREMARKS,TOKENRECEIVED,DISTORTION_ID,CC_EMAIL_IDS,BCC_EMAIL_IDS,SOURCE_EMAILID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		        executeClob(Query, wiName, activityName, refTo, resEmailId, emailBody, subject, decision, resRemarks, tokenRcv, distortionId, cc, bcc,souceEmailId);
		      } else {
		        writeCustLog(enablelogging, "inserClobData- empty values for resEmailId , emailBody ");
		      } 
		    } catch (Exception e) {
		      customException("inserClobData", e);
		    } 
		  }
	 
	 private void executeClob(String Query, String wiName, String activityName, String refTo, String resEmailId, String emailBody, String subject, String decision, String resRemarks, String tokenRcv, int distortionId, String cc, String bcc,String souceEmailId) {
		    String driver = "oracle.jdbc.driver.OracleDriver";
		    Connection conn = null;
		    PreparedStatement opstmt = null;
		    try {
		      writeCustLog(enablelogging, "Befor databse values");
		      Class.forName(driver);
		      writeCustLog(enablelogging, "After databse values");
		      conn = DriverManager.getConnection(LoadConfiguration.dbUrl, LoadConfiguration.dbUser, LoadConfiguration.dbPassword);
		      writeCustLog(enablelogging, "Connection Successful");
		      writeCustLog(enablelogging, "wiName :  :" + wiName);
		      opstmt = conn.prepareStatement(Query);
		      opstmt.setString(1, wiName);
		      opstmt.setString(2, activityName);
		      opstmt.setString(3, refTo);
		      opstmt.setString(4, resEmailId);
		      opstmt.setString(5, emailBody);
		      opstmt.setString(6, subject);
		      opstmt.setString(7, decision);
		      opstmt.setString(8, resRemarks);
		      opstmt.setString(9, tokenRcv);
		      opstmt.setInt(10, distortionId);
		      opstmt.setString(11, cc);
		      opstmt.setString(12, bcc);
		      opstmt.setString(13, souceEmailId);
		      opstmt.executeUpdate();
		      writeCustLog(enablelogging, "After Execute");
		    } catch (Exception e) {
		      customException("Error while Inserting in response logger", e);
		    } finally {
		      try {
		        if (opstmt != null)
		          opstmt.close(); 
		      } catch (Exception e) {
		        customException("Error while Closing PreparedStatement in response logger", e);
		      } 
		      try {
		        if (conn != null)
		          conn.close(); 
		      } catch (Exception e) {
		        customException("Error while Closing Connection in response logger", e);
		      } 
		    } 
	 }
}