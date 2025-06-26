package com.newgen.push.message;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import oracle.jdbc.OraclePreparedStatement;
import adminclient.OSASecurity;

import com.newgen.AESEncryption;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.wf.util.excp.NGException;
import com.newgen.push.message.channel.ProTrade;

public class RequestMessage implements MessageListener, MessageDrivenBean {
	public Map<String, String> currentCabMQPropertyMap = null;
	private static final long serialVersionUID = 1L;
	private String username;
	private String cabinetName;
	private String password;
	private String dburl;
	private String dbuser;
	private String dbpass;
	private String sessionId;
	private String mode; 
	private String channelName; 
	private String channelRefNumber; 
	private String userId; 
	private String sysrefno; 
	private String requestDateTime;  
	private String correlationId; 
	private String version; 
	private String processName; 
	private String initiateFromActivityId;
	private String initiateFromActivityName;
	private String exceptionFrom;
	private String exceptionTo;
	private String exceptionCC;
	private String exceptionSubject;
	private String exceptionMail;
	private String sessionInterval;

	public RequestMessage() {
		BPMReqLogMe.getInstance().intitalizeLog();
		initialize();
	}

	public void onMessage(Message msg) {
		BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO,"BPMRequestMessageMQDBRReceiver:  msg " + msg);
		try {
			TextMessage textMessage = (TextMessage)msg;
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO,"textMessage.getText() " + textMessage.getText() +" Message ID "+textMessage.getJMSMessageID());
			processMessage(textMessage.getText(),textMessage.getJMSMessageID());
		} catch (JMSException e) {
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO,"BPMRequestMessageMQPaymentFileReceiver:  in onMessage JMSException occured" + e.getMessage());
		} catch (Exception e) {
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO,"BPMRequestMessageMQPaymentFileReceiver:  in onMessage general Exception occured");	
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO,e);			
		}
	}

	@Override
	public void ejbRemove() throws EJBException {
		// TODO Auto-generated method stub

	}

	protected void initialize() {
		try {
			readCabProperty();
			this.cabinetName = ((String)currentCabMQPropertyMap.get("CabinetName"));
			this.username = ((String)currentCabMQPropertyMap.get("Username"));
			this.password = decryptPassword(((String)currentCabMQPropertyMap.get("Password")));
			this.dburl = ((String)currentCabMQPropertyMap.get("DBURL"));
			this.dbuser = ((String)currentCabMQPropertyMap.get("USER"));
			this.dbpass = ((String)currentCabMQPropertyMap.get("PASS"));
			this.initiateFromActivityId = ((String)currentCabMQPropertyMap.get("InitiateFromActivityId"));
			this.initiateFromActivityName = ((String)currentCabMQPropertyMap.get("InitiateFromActivityName"));
			this.exceptionTo = ((String)currentCabMQPropertyMap.get("exceptionTo"));
			this.exceptionFrom = ((String)currentCabMQPropertyMap.get("exceptionFrom"));
			this.exceptionCC = ((String)currentCabMQPropertyMap.get("exceptionCC"));
			this.exceptionSubject = ((String)currentCabMQPropertyMap.get("exceptionSubject"));
			this.exceptionMail = ((String)currentCabMQPropertyMap.get("exceptionMail"));
			this.sessionInterval = ((String)currentCabMQPropertyMap.get("sessionInterval"));
			String serverip = ((String)currentCabMQPropertyMap.get("IP"));
			int jndiport = Integer.parseInt( ((String)currentCabMQPropertyMap.get("Port")));
			int processDefId = Integer.parseInt( ((String)currentCabMQPropertyMap.get("ProcessDefId")));
			ProdCreateXML.init(this.cabinetName, processDefId, initiateFromActivityId, initiateFromActivityName);
			APCallCreateXML.init(this.cabinetName, processDefId, exceptionFrom, exceptionTo, exceptionCC, 
					exceptionSubject, exceptionMail);
			ExecuteXML.init("WebSphere", serverip, String.valueOf(jndiport));
		} catch (Exception e) {
			e.printStackTrace();
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_ERROR, e.getMessage());
		}
	}

	private String decryptPassword(String pass) {			
		int len = pass.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[(i / 2)] = 
					(byte)((Character.digit(pass.charAt(i), 16) << 4) + 
							Character.digit(pass.charAt(i + 1), 16));
		}
		String password = OSASecurity.decode(data, "UTF-8");
		return password;
	} 

	@Override
	public void setMessageDrivenContext(MessageDrivenContext arg0)
			throws EJBException {
		// TODO Auto-generated method stub		
	}

	private void readCabProperty() {
		currentCabMQPropertyMap = new HashMap<String, String>();
		String INI = System.getProperty("user.dir") + System.getProperty("file.separator") + "WebServiceConf/BPMRequestMessage/BPMRequestMessage_config.properties";
		BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "Config for BPMRequestMessage  path ===> " + INI);
		try
		{
			Properties p = new Properties();
			StringBuilder configFile = new StringBuilder(System.getProperty("user.dir"))
			.append(System.getProperty("file.separator"))
			.append("WebServiceConf")
			.append(System.getProperty("file.separator"))
			.append("BPMRequestMessage")
			.append(System.getProperty("file.separator"))
			.append("BPMRequestMessage_config.properties");

			InputStream stream = new FileInputStream(configFile.toString());
			p.load(stream);
			stream.close();
			currentCabMQPropertyMap.put("DBURL", p.getProperty("DBURL"));
			currentCabMQPropertyMap.put("USER", p.getProperty("USER"));
			currentCabMQPropertyMap.put("PASS", p.getProperty("PASS"));
			currentCabMQPropertyMap.put("CabinetName", p.getProperty("CabinetName"));
			currentCabMQPropertyMap.put("IP", p.getProperty("IP"));
			currentCabMQPropertyMap.put("Port", p.getProperty("Port"));
			currentCabMQPropertyMap.put("Username", p.getProperty("Username"));
			currentCabMQPropertyMap.put("Password", p.getProperty("Password"));
			currentCabMQPropertyMap.put("ProcessDefId", p.getProperty("ProcessDefId"));
			currentCabMQPropertyMap.put("InitiateFromActivityId", p.getProperty("InitiateFromActivityId"));
			currentCabMQPropertyMap.put("InitiateFromActivityName", p.getProperty("InitiateFromActivityName"));
			currentCabMQPropertyMap.put("exceptionTo", p.getProperty("exceptionTo"));
			currentCabMQPropertyMap.put("exceptionFrom", p.getProperty("exceptionFrom"));
			currentCabMQPropertyMap.put("exceptionCC", p.getProperty("exceptionCC"));
			currentCabMQPropertyMap.put("exceptionSubject", p.getProperty("exceptionSubject"));
			currentCabMQPropertyMap.put("exceptionMail", p.getProperty("exceptionMail"));
			currentCabMQPropertyMap.put("sessionInterval", p.getProperty("sessionInterval"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_ERROR, "Exception in readCabProperty " + e);
		}
		BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "Config for BPMRequestMessage ===> " + currentCabMQPropertyMap.toString());
	}

	public void processMessage(String message,String message_ID) throws Exception
	{
		XMLParser xp = new XMLParser(message);
		try {
			this.dbpass = AESEncryption.decrypt(this.dbpass);
		}
		catch (Exception e) {
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_ERROR, "Exception while decrypting password : "+e.getMessage());
		}

		try {
	//sheenu		String sMessageInsertQuery = "insert into BPM_MESSAGE_AUDIT(MQ_MESSAGEID, INCOMINGMESSAGE, MESSAGEDATETIME) values('" + message_ID + "',?,SYSTIMESTAMP)";
//			executeMQClob("jdbc:oracle:thin:@" + this.dburl, this.dbuser, this.dbpass, sMessageInsertQuery, message.replaceAll("'", "''"));
	//sheenu		executeMQClob(this.dburl, this.dbuser, this.dbpass, sMessageInsertQuery, message.replaceAll("'", "''"));
		} catch (Exception e) {
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_ERROR, "Exception while inserting incoming message : "+e.getMessage());
		}

		try {
			sessionId = SingleUserConnection.getInstance(100).getSession(this.cabinetName, this.username, 
					this.password, this.sessionInterval);
		} catch (NGException e) {
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_ERROR, e.getMessage());
		}
 //sheenu
		try{
			String strColumns1 ="MQ_MESSAGEID, INCOMINGMESSAGE, MESSAGEDATETIME";
			String strValues1 ="'"+message_ID+"',"+createNormalizedXML(message)+",SYSTIMESTAMP";
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "strColumns:"+strColumns1+"strValues:"+strValues1);	

			String opXml1 = APCallCreateXML.APInsert("BPM_MESSAGE_AUDIT", strColumns1, 
					strValues1, sessionId);
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "opXml:"+opXml1);	

		}catch(Exception e){
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_ERROR, e.getMessage());

		}
		this.mode = xp.getValueOf("mode");
		this.channelName = xp.getValueOf("channelName");
		this.channelRefNumber = xp.getValueOf("channelRefNumber");
		this.userId = xp.getValueOf("userId");
		this.sysrefno = xp.getValueOf("sysrefno");
		this.requestDateTime = xp.getValueOf("requestDateTime");
		this.correlationId = xp.getValueOf("correlationId");
		this.version = xp.getValueOf("version");
		this.processName = xp.getValueOf("processName");
		StringBuffer strColumns = new StringBuffer();
		StringBuffer strValues = new StringBuffer();
		strColumns.append("REQUESTMODE,channelName,channelRefNumber,userId,sysrefno,requestDateTime,correlationId,version,processName,insertiondatetime,");
		strValues.append("'"+ mode+"','"+ channelName+"','"+ channelRefNumber+"','"+ userId+"','"+ sysrefno+"','"+ requestDateTime+"','"+ correlationId+"','"+ version+"','"+ processName+"',SYSDATE,");
		BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "channelName: " + this.channelName);		
		if("PROTRADE".equals(channelName)) {
			try {
				String outputXml = APCallCreateXML.APSelect("SELECT count(1) as ROWCOUNT "
						+ "FROM TFO_DJ_PROTRADE_TXN_DATA WHERE CHANNELREFNUMBER = '"+channelRefNumber
						+ "' AND CORRELATIONID = '"+correlationId+"' AND REQUESTMODE = '"+mode+"'");
				XMLParser xpCount = new XMLParser(outputXml);
				int mainCode = Integer.parseInt(xpCount.getValueOf("MainCode"));
				BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "mainCode:"+mainCode+"ROWCOUNT :"
				+Integer.parseInt(xpCount.getValueOf("ROWCOUNT")));	

				if (mainCode == 0 && Integer.parseInt(xpCount.getValueOf("ROWCOUNT")) == 0) {
					BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "inside if processing");
                    ProTrade.startProcessing(message, strColumns.toString(), strValues.toString(), sessionId, this.cabinetName, this.username, 
							this.password, this.sessionInterval);
				} else {
					BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "inside else processing");
                    BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "Duplicate Record");	
					APCallCreateXML.triggerExceptionMail("TFO_DJ_PROTRADE_TXN_DATA", "Duplicate Record", 
							channelRefNumber, correlationId, sessionId);
				}
			} catch (Exception e) {
				BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_ERROR, "exception in fetching row count: ");
				BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_ERROR, e);
			}
		}

	}

	public String executeMQClob(String url, String user, String pass, String Query, String response) {
		BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "inside executeMQClob");
		BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "url: "+url+"; user: "+user+"; pass: "+pass);
		//String driver = "jdbc.odbc.JdbcOdbcDriver";
		String driver = "oracle.jdbc.driver.OracleDriver";
		Connection conn = null;
		OraclePreparedStatement opstmt = null;
		String Data = "";
		try
		{
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pass);
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "Connection Successful");
			opstmt = (OraclePreparedStatement)conn.prepareStatement(Query);
			opstmt.setStringForClob(1, response);
			opstmt.executeUpdate();
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "After Execute");
		}
		catch (Exception e)
		{			
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_ERROR, "exception in executeMQClob: "+e.getMessage());
			return "ERROR";
		} finally {
			try {
				if (opstmt != null)
					opstmt.close();
			}
			catch (Exception e2) {
				BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_ERROR, "1- exception in executeMQClob: "+e2);
			}
			try {
				if (conn != null)
					conn.close();
			}
			catch (Exception e2) {
				BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_ERROR,"2- exception in executeMQClob: ");
				BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_ERROR,e2);
			}
		}
		return Data;
	}
	private static  String createNormalizedXML(String outputXml) {
		try {
			if ((outputXml != null) && (!("".equalsIgnoreCase(outputXml)))) {
				outputXml = outputXml.replace("'", "''");
				outputXml = outputXml.replace("&", "'||chr(38)||'");
				if (outputXml.length() > 3200) {				
					outputXml = breakCLOBString(outputXml);
					return outputXml;
				}
				outputXml = "TO_NCLOB('" + outputXml + "')";
				return outputXml;
			}			
		} catch (Exception ex) {
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_ERROR, ex);	 				
		}
		return "''";
	}
	private static  String breakCLOBString(String xml) {
		int itr = xml.length() / 3200;
		int mod = xml.length() % 3200;
		if (mod > 0) {
			++itr;
		}
		StringBuilder response = new StringBuilder();
		try {
			for (int i = 0; i < itr; ++i) {
				if (i == 0) {
					response.append("TO_NCLOB('" + xml.substring(0, 3200) + "')");
				} else if (i < itr - 1) {
					response.append(" || TO_NCLOB('" + xml.substring(3200 * i,3200 * (i + 1)) + "')");
				} else {
					response.append(" || TO_NCLOB('" + xml.substring(3200 * i,xml.length()) + "') ");
				}
			}
		} catch (Exception e) {
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_ERROR, e);	 				
		}
		return response.toString();
	}

}
