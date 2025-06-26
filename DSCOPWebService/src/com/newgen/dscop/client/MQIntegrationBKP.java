package com.newgen.dscop.client;

import java.util.Map;
import java.util.UUID;

import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.msg.client.wmq.v6.base.internal.MQC;
import com.newgen.dscop.client.LogGEN;


public class MQIntegrationBKP {

	public String MQPutGetMessage(String strMsg)
	{
		LogGEN.writeTrace("CBG_Log","Entered in Integration Method");

		LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
		Map<String, String> mqConfig = null;//MQConfig.getInstance().getCBGMQConfig();

		String qmgrName = (String)mqConfig.get("QMGR_NAME");
		String qNameSource = (String)mqConfig.get("QNAME_SOURCE");
		String qNameDest = (String)mqConfig.get("QNAME_DESTINATION");
		String qNameErr = (String)mqConfig.get("QNAME_ERROR");
		String hostName = (String)mqConfig.get("HOSTNAME");
		String channel = (String)mqConfig.get("CHANNEL");
		String port = (String)mqConfig.get("PORT");
		String userId = (String)mqConfig.get("USERID");
		String password = (String)mqConfig.get("PASSWORD");
		String timeout = (String)mqConfig.get("TIMEOUT_INTERVAL");
		LogGEN.writeTrace("CBG_Log", "Reading password..."+password);
		LogGEN.writeTrace("CBG_Log", "Reading qmgrName..."+qmgrName);
		MQMessage message = new MQMessage();
		MQQueueManager mqQueueManager = null;
		MQQueue queue = null;

		String uid = "";

		try
		{
			try
			{
				LogGEN.writeTrace("CBG_Log","inside MQPutGetMessageWithCorr 2 : "+strMsg);

				MQEnvironment.hostname = hostName;
				MQEnvironment.channel  = channel;
				MQEnvironment.port 	   = Integer.parseInt(port);
				MQEnvironment.userID   = userId;
				MQEnvironment.password = password;

				mqQueueManager = new MQQueueManager(qmgrName);

				LogGEN.writeTrace("CBG_Log","MQEnvironment.hostname : " + MQEnvironment.hostname);
				LogGEN.writeTrace("CBG_Log","MQEnvironment.channel : " + MQEnvironment.channel);
				LogGEN.writeTrace("CBG_Log","MQEnvironment.port : " + MQEnvironment.port);
				LogGEN.writeTrace("CBG_Log","MQEnvironment.userID : " + MQEnvironment.userID);
				LogGEN.writeTrace("CBG_Log","MQEnvironment.password : " + MQEnvironment.password);
				LogGEN.writeTrace("CBG_Log","MessageID:" + message.messageId);
			}
			catch(MQException mqExp)
			{
				LogGEN.writeTrace("CBG_Log",mqExp.getMessage());
				LogGEN.writeTrace("CBG_Log",mqExp.getLocalizedMessage());
			}catch (Exception exc) {         					        
				LogGEN.writeTrace("CBG_Log",exc.getMessage());
			}

			// Put Message to source queue

			LogGEN.writeTrace("CBG_Log","Before Putting Message in queue : ");
			uid = UUID.randomUUID().toString();
			try
			{
				int openOption = MQC.MQOO_OUTPUT | MQC.MQOO_FAIL_IF_QUIESCING; // open options for browse & share

				LogGEN.writeTrace("CBG_Log","qNameSource : " + qNameSource);
				LogGEN.writeTrace("CBG_Log","qNameDest : " + qNameDest);

				queue = mqQueueManager.accessQueue(qNameSource,openOption,null,null,null);

				message.replyToQueueName =  qNameDest;
				//to set the Corrleation as a unique No which we check when we receive the response (It will require when Message ID not coming as Coorelationid in response)
				message.correlationId = uid.getBytes();

				LogGEN.writeTrace("CBG_Log","CorrelationId :" + message.correlationId);

				message.format = MQC.MQFMT_STRING;
				message.writeString(strMsg);

				MQPutMessageOptions pmo = new MQPutMessageOptions();
				queue.put(message,pmo);
				queue.close();
				return "0";
			}
			catch(MQException mqExp)
			{
				try{
					mqQueueManager.disconnect();
				}catch(Exception e){}
				LogGEN.writeTrace("CBG_Log",mqExp.getMessage());
				return "1";
			}catch (java.io.IOException Exp) {
				try{
					mqQueueManager.disconnect();
				}catch(Exception e){}
				LogGEN.writeTrace("CBG_Log",Exp.getMessage());
				return "1";
			}catch (Exception exc) {     
				try{
					mqQueueManager.disconnect();
				}catch(Exception e){}
				LogGEN.writeTrace("CBG_Log",exc.getMessage());
				return "1";
			} finally {
				try {
					if(queue != null){
						queue.close();
					}					
				}catch(Exception e){}

				try{
					if(mqQueueManager != null){
						mqQueueManager.disconnect();
					}					
				}catch(Exception e){}
			}
		}
		catch(Exception e)	
		{
			LogGEN.writeTrace("CBG_Log",e.getMessage());
			return "1";
		}
	}
}
