package com.newgen.dscop.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.MQConfig;
import com.newgen.dscop.client.WebServiceConfig;

public class MQConfig extends DSCOPServiceHandler {

	private static MQConfig mqConfig;
	private Map<String,Map<String,String>> mqConfMap = null;
	long mapLoadTime = 0L;

	public static synchronized MQConfig getInstance()
	{
		if (mqConfig == null) {
			synchronized (MQConfig.class) {
				if (mqConfig == null) {
					mqConfig = new MQConfig();
				}
			}
		}
		return mqConfig;
	}

	public Map<String,Map<String,String>> getCBGMQConfig()
	{
		LogGEN.writeTrace("CBG_Log", "inside getCBGMQConfig");
		Map<String,Map<String,String>> finalMQConfMap = null;
		try 
		{
			readMQConfig();
			finalMQConfMap = this.mqConfMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finalMQConfMap;
	}

	protected void readMQConfig() 
	{
		LogGEN.writeTrace("CBG_Log", "inside readMQConfig");

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			if(currentCabPropertyMap.get("CabinetName").toString().equalsIgnoreCase(""))	{
				WebServiceConfig.getInstance().readCabProperty();
			}

			if ((this.mqConfMap != null) && (!(loadConfigChanges()))) {
				conn = null;
			}
			else {
				this.mqConfMap = new HashMap();
				LogGEN.writeTrace("CBG_Log", "creating Connection...MQConfig");

				String dburl = (String)currentCabPropertyMap.get("DBURL");
				String dbuser = (String)currentCabPropertyMap.get("USER");
				String dbpass = (String)currentCabPropertyMap.get("PASS");

				String fetchWSConfigQry = "SELECT JMS_QUEUE_NAME,JMS_QCF_NAME,REQUEST_NAME FROM CBG_MQ_PROP";
				LogGEN.writeTrace("CBG_Log", "creating new conf map Query :" + fetchWSConfigQry);
				LogGEN.writeTrace("CBG_Log", "DBURL :" + dburl);
				LogGEN.writeTrace("CBG_Log", "USER :" + dbuser);
				LogGEN.writeTrace("CBG_Log", "PASS :" + dbpass);
				LogGEN.writeTrace("CBG_Log", "Befor databse values");
				Class.forName("oracle.jdbc.driver.OracleDriver");
				System.out.println("After databse values");
				LogGEN.writeTrace("CBG_Log", "After databse values");
				String pass = AESEncryption.decrypt(dbpass);
				conn = DriverManager.getConnection("jdbc:oracle:thin:@" + dburl, dbuser, pass);
				LogGEN.writeTrace("CBG_Log", "Connection Successful");
				System.out.println("Connection Successful");
				st = conn.createStatement();
				rs = st.executeQuery(fetchWSConfigQry);
				if(rs != null )
				{							
					while(rs.next()){
						
						Map<String,String> map = new HashMap<String,String>();
						map.put("JMS_QUEUE_NAME", rs.getString("JMS_QUEUE_NAME"));
						map.put("JMS_QCF_NAME", rs.getString("JMS_QCF_NAME"));
						
						this.mqConfMap.put(rs.getString("REQUEST_NAME"), map);
					}
				}
				mapLoadTime=  TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis());
				LogGEN.writeTrace("CBG_Log", "created new conf map at " + mapLoadTime);
				LogGEN.writeTrace("CBG_Log", "MQConfig mqConfMap: " + mqConfMap);

				rs.close(); rs = null;
				st.close(); st = null;
				conn.close(); 
			}
		}
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Exception in readMQConfig: " + e.getMessage());
			e.printStackTrace();
		}
		finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}

				if (st != null) {
					st.close();
					st = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	private boolean loadConfigChanges()
	{
		boolean reCreateCache = false;
		try {
			long mins = TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis());
			LogGEN.writeTrace("CBG_Log", "last map load time ..." + this.mapLoadTime + " current time " + mins);
			mins -= this.mapLoadTime;
			if (mins > 30L) {
				LogGEN.writeTrace("CBG_Log", "time to recheck config...");
				reCreateCache = true;
				this.mqConfMap.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reCreateCache; 
	} 
}
