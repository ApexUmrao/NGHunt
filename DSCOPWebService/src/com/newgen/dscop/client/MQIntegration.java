package com.newgen.dscop.client;

import java.util.Hashtable;
import java.util.Map;

import javax.jms.DeliveryMode;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.MQConfig;


public class MQIntegration extends DSCOPServiceHandler{
	private String ServerIP;
	private String ServerJNDIPort;
	private String ServerProviderURL;

	public String MQPutGetMessage(String strMsg, String requestName)
	{
		LogGEN.writeTrace("CBG_Log", "Entered in MQIntegration MQPutGetMessage Method");

		LogGEN.writeTrace("CBG_Log", "Reading mqconfig...");
		Map<String,Map<String,String>> mqConfig = MQConfig.getInstance().getCBGMQConfig();

		Map<String,String> map = mqConfig.get(requestName);
		String sJMSQueueName = (String)map.get("JMS_QUEUE_NAME");
		String sJMSQCFName = (String)map.get("JMS_QCF_NAME");

		LogGEN.writeTrace("CBG_Log", "Reading qmgrName... " + sJMSQueueName + " - " + sJMSQCFName);
		this.ServerIP = ((String)currentCabPropertyMap.get("IP"));
		this.ServerJNDIPort = "9810"; //((String)currentCabPropertyMap.get("Port"));
		LogGEN.writeTrace("CBG_Log", "Reading currentCabPropertyMap ... " + this.ServerIP + " - " + this.ServerJNDIPort);
		this.ServerProviderURL = "iiop://" + this.ServerIP + ":" + this.ServerJNDIPort;
		LogGEN.writeTrace("CBG_Log", "ServerProviderURL ... " + this.ServerProviderURL);

		QueueConnection queConn = null;
		Queue queue = null;
		QueueConnectionFactory conFactory = null;
		QueueSession queSession = null;
		TextMessage message = null;

		boolean bValidSession = true;
		try
		{
			if (bValidSession)
			{
				Hashtable<String,String> icf = new Hashtable<String,String>();
				icf.put("java.naming.factory.initial", "com.ibm.websphere.naming.WsnInitialContextFactory");
		        icf.put("java.naming.provider.url", this.ServerProviderURL);

				InitialContext context = new InitialContext(icf);
				try
				{
					LogGEN.writeTrace("CBG_Log", "context : " + context);
					queue = (Queue)context.lookup(sJMSQueueName);
					LogGEN.writeTrace("CBG_Log", "queue : " + queue);
					conFactory = (QueueConnectionFactory)context.lookup(sJMSQCFName);
					LogGEN.writeTrace("CBG_Log", "conFactory : " + conFactory);
					queConn = conFactory.createQueueConnection();
					LogGEN.writeTrace("CBG_Log", "queConn : " + queConn);
					queSession = queConn.createQueueSession(false, Session.DUPS_OK_ACKNOWLEDGE);
					LogGEN.writeTrace("CBG_Log", "queSession : " + queSession);
					QueueSender queSender = queSession.createSender(queue);
					LogGEN.writeTrace("CBG_Log", "queSender " + queSender);
					queSender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
					LogGEN.writeTrace("CBG_Log", "queSender : " + queSender);
					message = queSession.createTextMessage(strMsg);
					LogGEN.writeTrace("CBG_Log", "message : " + message);
					queSender.send(message);
					LogGEN.writeTrace("CBG_Log", "Message Sent JMS : " + message.getText());

					queConn.close();
				} catch (Exception e) {
					LogGEN.writeTrace("CBG_Log", "Message Sent JMS excp: " + e.getStackTrace());
					System.out.println(e);
					e.printStackTrace();
					return "1";
				}
			}

			try
			{
				if (queConn != null) {
					queConn.close();
				}
			}
			catch (Exception e)
			{
				LogGEN.writeTrace("CBG_Log", "queConn.disconnect" + e.getStackTrace());
				System.out.println(e);
				e.printStackTrace();
			}
		}
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "queConn.disconnect2" + e.getStackTrace());
			System.out.println(e);
			e.printStackTrace();
			return "1";
		}
		return "0";
	}
	
	private String removeRFHHeaders(String ipString)
	  {
	    try
	    {
	      LogGEN.writeTrace("CBG_Log", "Inside Remove RFH Header");
	      if (ipString.indexOf("</jms>") > -1)
	      {
	        LogGEN.writeTrace("CBG_Log", "JMS is there");
	        return ipString.substring(0, ipString.indexOf("RFH")).trim() + ipString.substring(ipString.indexOf("</jms>") + 6).trim();
	      }

	      LogGEN.writeTrace("CBG_Log", "JMS is not there");
	      return ipString;
	    }
	    catch (Exception e)
	    {
	      LogGEN.writeTrace("CBG_Log", "Some Exception in removing RFH Header"); }
	    return ipString;
	  }
}