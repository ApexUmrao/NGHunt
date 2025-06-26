package com.newgen.iforms.user.tfo;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Hashtable;

import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import com.ibm.mq.MQQueue;
import com.ibm.mq.jms.JMSC;
import com.ibm.mq.jms.MQQueueConnection;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.mq.jms.MQQueueSender;
import com.ibm.mq.jms.MQQueueSession;
import com.sun.net.ssl.internal.ssl.Provider;

public class PushMessageTSLM extends Common{
	//DART 1132892
	public void pushTSLM(){
		StringBuffer strMesg  = new StringBuffer();;
	
		strMesg.append("<?xml version=1.0 encoding=UTF-8 standalone=yes?>"+
            "<referralAndLoanStatusMsg>"+
            "<header>"+
	        "<mode>N</mode>"+
	        "<channelName>TSLM</channelName>"+
	        "<sysrefno>98765432</sysrefno>"+
	        "<processName>TFO</processName>"+
	        "<requestDateTime>18/10/2020 14:52:00</requestDateTime>"+
	        "<correlationId>1606210868660</correlationId>"+
	        " <version>1.0</version>"+
	        "</header>"+
	        "<referralAndLoanStatus>"+
	        "<stage>C</stage>"+
	        "<userId>ADCBAE:TESTCHECKER</userId>"+
	        "<wmsId>TF-00000143681-REQUEST</wmsId>"+
	        "<tslmId>1021</tslmId>"+
	        "<wiTxnStatus>Maker Processed Disbursement</wiTxnStatus>"+
	        "<loanList>"+
		        "<loan>"+
			        "<cpcid>52145401</cpcid>"+
			        "<loanId>ADCBAE20L0000002I000</loanId>"+
			        "<loanTxnId>ADCBAE20L0000002I000</loanTxnId>"+
			        "<loanCcy>AED</loanCcy>"+
			        "<loanAmt>30000</loanAmt>"+
			        "<trxType>LOAN</trxType>"+
			        "<loanTxnStatus>Approved</loanTxnStatus>"+
			        "<referralFlag>N</referralFlag>"+
		        "</loan>"+
		        "<loan>"+
			        "<cpcid>52145402</cpcid>"+
			        "<loanId>ADCBAE20L0000004I000</loanId>"+
			        "<loanTxnId>ADCBAE20L0000004I000</loanTxnId>"+
			        "<loanCcy>AED</loanCcy>"+
			        "<loanAmt>50000</loanAmt>"+
			        "<trxType>LOAN</trxType>"+
			        "<loanTxnStatus>Referred</loanTxnStatus>"+
			        "<referralFlag>Y</referralFlag>"+
		        "</loan>"+
	        "</loanList>"+
	        "<referralList>"+
		        "<referal>"+
		        "<seqNo>1</seqNo>"+
		        "<trxType>LOAN</trxType>"+
		        "<trxId>ADCBAE20L0000004I000</trxId>"+
		        "<cpcid>52145402</cpcid>"+
		        "<referralUnit>OP</referralUnit>"+
		        "<referralDesc>Loan referral</referralDesc>"+
		        "<comment>Incorrect loan tenor</comment>"+
		        "</referal>"+
		        "<referal>"+
		        "<seqNo>2</seqNo>"+
		        "<trxType>LOAN</trxType>"+
		        "<trxId>ADCBAE20L0000004I000</trxId>"+
		        "<cpcid>52145402</cpcid>"+
		        "<referralUnit>OP</referralUnit>"+
		        "<referralDesc>Loan referral</referralDesc>"+
		        "<comment>Incorrect loan tenor</comment>"+
		        "</referal>"+
	        "</referralList>"+
	        " </referralAndLoanStatus>"+
         "</referralAndLoanStatusMsg>");
			String strMsg = strMesg.toString();
	   TextMessage message = null;
	   try {
		 log.info("Inside pushTSLM Method");
		 //Class.forName("com.sun.net.ssl.internal.ssl.Provider");
		 log.info("CHECK 1");
		 String ServerProviderURL = "iiop://" + "" + "10.101.109.60" + ":9810";
		 log.info("CHECK 23"+ServerProviderURL);
		 System.out.println("JSSE is installed correctly");
		 QueueConnection queConn = null;
		 Queue queue = null;
		 QueueConnectionFactory conFactory = null;
		 QueueSession queSession = null;
		 log.info("CHECK 13");
		    
		 Hashtable<String, String> icf = new Hashtable<String, String>();
	     icf.put("java.naming.factory.initial", "com.ibm.websphere.naming.WsnInitialContextFactory");
	     icf.put("java.naming.provider.url", ServerProviderURL);
	     InitialContext context = new InitialContext(icf);
	     log.info("CHECK 23");
	     
	     conFactory = (QueueConnectionFactory)context.lookup("jms/tslmstowms");
         log.info("CHECK 43");
	     
         queue = (Queue)context.lookup("jms/tf_tslms_2_wms");
         log.info("CHECK 33");
         
         queConn = conFactory.createQueueConnection();
         queSession = queConn.createQueueSession(false, 3);
         log.info("CHECK 53");
         
         QueueSender queSender = queSession.createSender((javax.jms.Queue) queue);          
         queSender.setDeliveryMode(1);
         message = queSession.createTextMessage(strMsg);
         queSender.send((Message)message);
         //LogGEN.writeTrace("PushMessage", "Message Sent JMS Test GKR : " + message.getText());
         //insertIntoAuditTable(strMsg, "success", sessionID, callType);
         queConn.close();
         log.info("CHECK 63");
		/* KeyStore ks = KeyStore.getInstance("JKS");
		   char[] KSPW = {'a'}; //password for certificate
		   ks.load(new FileInputStream(""),KSPW);
		   KeyStore  trustStore = KeyStore.getInstance("JKS");
		   trustStore.load(new FileInputStream(""),null);*/
		/*String SSLCIPHERSUITE = "TLS_RSA_WITH_AES_128_CBC_SHA256";
		String INI = System.getProperty("user.dir")+ System.getProperty("file.separator")
				+ "WebServiceConf/kishan.txt";
		log.info("CHECK 2");
		InputStream is = new FileInputStream(INI);
		log.info("CHECK 3");
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		log.info("CHECK 4");
		Certificate caCert = cf.generateCertificate(is);
		log.info("CHECK 5");
		TrustManagerFactory trustManagerFactory = TrustManagerFactory
				.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		log.info("CHECK 6");
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		log.info("CHECK 7");
		ks.load(null);
		log.info("CHECK 8");
		ks.setCertificateEntry("caCert", caCert);
		log.info("CHECK 9");
		//       KeyManagerFactory keyManagerFactory = 
		//       		KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		//       
		//       trustManagerFactory.init(trustStore);
		//       keyManagerFactory.init(ks,KSPW);
		SSLContext sslContext = SSLContext.getInstance("SSLv3");
		log.info("CHECK 10");
		sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
		log.info("CHECK 11");
		// sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(),null); 
		MQQueueConnectionFactory connectionfactory = null;
		connectionfactory.setSSLSocketFactory(sslContext.getSocketFactory());
		connectionfactory.setTransportType(JMSC.MQJMS_TP_CLIENT_MQ_TCPIP);
		connectionfactory.setQueueManager("TSLMWMSINTGRATE"); //TSLMWMSINTGRATE
		connectionfactory.setHostName("10.101.107.236"); //10.101.107.236
		connectionfactory.setChannel("CHNL.TO.TSLMWMS"); //CHNL.TO.TSLMWMS
		connectionfactory.setPort(1498); //1498
		connectionfactory.setSSLFipsRequired(false);
		connectionfactory.setSSLCipherSuite(SSLCIPHERSUITE);
		log.info("CHECK 12");
		MQQueueConnection connection = (MQQueueConnection) connectionfactory
				.createQueueConnection("mqadmin", "Adcb1234");
		log.info("CHECK 13");
		MQQueueSession session = (MQQueueSession) connection
				.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		log.info("CHECK 14");
		MQQueue mqQueue = (MQQueue) session.createQueue("TF.TSLM.2.WMS"); 
		log.info("CHECK 15");//TF.TSLM.2.WMS jms/tf_wms_2_tslms
		MQQueueSender sender = (MQQueueSender) session.createSender((Queue) mqQueue);
		log.info("CHECK 16");
		message = session.createTextMessage(strMsg);
		log.info("CHECK 17");
		sender.send((Message) message);
		log.info("CHECK 18");
		// LogGEN.writeTrace("PushMessage", "Message Sent JMS Test GKR : " + message.getText());
		// insertIntoAuditTable(strMsg, "success", sessionID, callType);
		connection.close();
		log.info("END pushTSLM Method");*/
	} catch (Exception e) {
		// TODO: handle exception
		log.info("PushMessageTSLm="+e);
		}
	}
}
