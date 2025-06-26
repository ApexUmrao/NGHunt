package com.newgen;

import java.net.URI;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;


public class EmailExchange {
	private static EmailExchange emailexchange;
	
	private static ExchangeService service;
	private static Integer NUMBER_EMAILS_FETCH = 20; // only latest 20 email
	
	public  ExchangeService getService() {
		return service;
	}

	public static void setService(ExchangeService service) {
		EmailExchange.service = service;
	}

	public  Integer getNUMBER_EMAILS_FETCH() {
		return NUMBER_EMAILS_FETCH;
	}

	public static void setNUMBER_EMAILS_FETCH(Integer nUMBER_EMAILS_FETCH) {
		NUMBER_EMAILS_FETCH = nUMBER_EMAILS_FETCH;
	}
	
	static {
	    try {
	        service = new ExchangeService(ExchangeVersion.Exchange2010_SP1);
	        service.setUrl(new URI(LoadConfiguration.exchangeServerURL));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public EmailExchange( String email, String pwd, String domain) {
		ExchangeCredentials credentials = new WebCredentials(email, pwd, domain);
	    service.setCredentials(credentials);
	}
	
	public static EmailExchange getInstance(){
		if(emailexchange == null){
			emailexchange = new EmailExchange(LoadConfiguration.EmailAccount,
					LoadConfiguration.EmailAccountPassword,LoadConfiguration.domain);
		}
		return emailexchange;
	}
}
