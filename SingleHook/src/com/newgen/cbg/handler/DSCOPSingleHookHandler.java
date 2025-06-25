package com.newgen.cbg.handler;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import com.newgen.cbg.logger.DSCOPLogMe;

public class DSCOPSingleHookHandler  implements SOAPHandler<SOAPMessageContext>
{

	@Override
	public void close(MessageContext arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean handleFault(SOAPMessageContext arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean handleMessage(SOAPMessageContext smc) {
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Inside DSCOPSingleHookHandler");
		Boolean out = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"DSCOPSingleHookHandler out: "+ out);
		SOAPMessage message = smc.getMessage();
		ByteArrayOutputStream bout = new ByteArrayOutputStream(); 
		try{
			if(out.booleanValue()){
				message.writeTo(bout);
				String xml =bout.toString();
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Response: "+ xml);
			}
			else{
				message.writeTo(bout);
				String xml =bout.toString();
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Request: "+ xml);
			}
			return true;
		}
		catch(Exception e){
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,"Exception: "+ e.getMessage());
			return false;
		}
	}

	@Override
	public Set<QName> getHeaders() {
		return Collections.emptySet();
	}
}