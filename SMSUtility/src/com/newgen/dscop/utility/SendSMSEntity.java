package com.newgen.dscop.utility;

import com.newgen.omni.jts.cmgr.XMLParser;

public class SendSMSEntity {

	private String WI_NAME;
	private String customerID;
	private String acctNumber;
	private String transactionType;
	private String sendAsChannel;
	private String smsTemplateID;
	private String smsTextValues;
	private String mobileNumber;
	private String emailTemplated; 
	private String emailTextValue; 
	private String emailAddress; 
	private String flexiFiller1; 
	private String flexiFiller2; 
	private String flexiFiller3; 
	private String flexiFiller4; 
	private String flexiFiller5; 
	private String ENTRY_DATE_TIME;
	private String PROCESSED_DATE_TIME;
	private String status;
	private String languagePref;
	public SendSMSEntity(XMLParser xp, int start, int end){
		
		customerID= xp.getValueOf("CUSTOMERID", start, end);
		acctNumber=xp.getValueOf("ACCTNUMBER", start, end);
		transactionType= xp.getValueOf("TRANSACTIONTYPE", start, end);
		sendAsChannel=xp.getValueOf("SENDASCHANNEL", start, end);
		smsTemplateID= xp.getValueOf("SMSTEMPLATEID", start, end);
		smsTextValues=xp.getValueOf("SMSTEXTVALUES", start, end);
		mobileNumber= xp.getValueOf("MOBILENUMBER", start, end);
		emailTemplated=xp.getValueOf("EMAILTEMPLATEID", start, end);
		emailTextValue= xp.getValueOf("EMAILTEXTVALUES", start, end);
		emailAddress=xp.getValueOf("EMAILADDRESS", start, end);
		flexiFiller1= xp.getValueOf("FLEXIFILLER1", start, end);
		flexiFiller2= xp.getValueOf("FLEXIFILLER2", start, end);
		flexiFiller3= xp.getValueOf("FLEXIFILLER3", start, end);
		flexiFiller4= xp.getValueOf("FLEXIFILLER4", start, end);
		flexiFiller5= xp.getValueOf("FLEXIFILLER5", start, end);
		status=xp.getValueOf("STATUS", start, end);
		WI_NAME=xp.getValueOf("WI_NAME", start, end);
		ENTRY_DATE_TIME=xp.getValueOf("ENTRY_DATE_TIME", start, end);
		PROCESSED_DATE_TIME=xp.getValueOf("PROCESSED_DATE_TIME", start, end);
		languagePref=xp.getValueOf("PREFFEREDLANGUAGE", start, end);
	}

	public String getWI_NAME() {
		return WI_NAME;
	}

	public String getCustomerID() {
		return customerID;
	}

	public String getAcctNumber() {
		return acctNumber;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public String getSendAsChannel() {
		return sendAsChannel;
	}

	public String getSmsTemplateID() {
		return smsTemplateID;
	}

	public String getSmsTextValues() {
		return smsTextValues;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public String getEmailTemplated() {
		return emailTemplated;
	}

	public String getEmailTextValue() {
		return emailTextValue;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public String getFlexiFiller1() {
		return flexiFiller1;
	}

	public String getFlexiFiller2() {
		return flexiFiller2;
	}

	public String getFlexiFiller3() {
		return flexiFiller3;
	}

	public String getFlexiFiller4() {
		return flexiFiller4;
	}

	public String getFlexiFiller5() {
		return flexiFiller5;
	}

	public String getENTRY_DATE_TIME() {
		return ENTRY_DATE_TIME;
	}

	public String getPROCESSED_DATE_TIME() {
		return PROCESSED_DATE_TIME;
	}

	public String getStatus() {
		return status;
	}

	public String getLanguagePref() {
		return languagePref;
	}	
}



