package com.newgen.cbg.callhandler;

import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import com.newgen.cbg.logger.DSCOPLogMe;

public class DSCOPSingleHookLogger implements SOAPHandler<SOAPMessageContext>{

	@Override
	public void close(MessageContext arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean handleFault(SOAPMessageContext arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Inside SOAP HANDLER");
		Boolean isRequest = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		SOAPMessage soapMsg = context.getMessage();
		if(isRequest){
			String request = soapMsg.toString();
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Inside SOAP HANDLER: REQUEST: "+ request);
		} else{
			String response = soapMsg.toString();
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Inside SOAP HANDLER: REQUEST: "+ response);
		}
		return true;
	}

	@Override
	public Set<QName> getHeaders() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
