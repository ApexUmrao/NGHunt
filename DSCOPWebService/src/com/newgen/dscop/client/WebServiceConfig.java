package com.newgen.dscop.client;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;

public class WebServiceConfig extends DSCOPServiceHandler {

	long mapLoadTime=0;
	private static WebServiceConfig webSrvConfig;
	private Map<String,ArrayList<String>> webSrvConfMap = new HashMap<String,ArrayList<String>>();
	private WebServiceConfig(){

	}

	public static synchronized WebServiceConfig getInstance(){
		if(webSrvConfig == null){
			synchronized(WebServiceConfig.class){
				if(webSrvConfig == null){
					webSrvConfig = new WebServiceConfig();
				}
			}
		}
		return webSrvConfig;
	}	

	protected void readWebSrvConfig()
	{
		Connection conn = null;
	    ResultSet rs = null;
	    Statement st = null;
		ArrayList<String> tempList = null;	
		try {
			//readCabProperty();
			if(currentCabPropertyMap == null){
				currentCabPropertyMap = new HashMap<String,String>();
			}

			if(webSrvConfMap.isEmpty() || currentCabPropertyMap.isEmpty() || loadConfigChanges())
			{
				readCabProperty();
				String dburl = (String)currentCabPropertyMap.get("DBURL");
				String dbuser = (String)currentCabPropertyMap.get("USER");
				String dbpass = (String)currentCabPropertyMap.get("PASS");
				
				LogGEN.writeTrace("CBG_Log", "creating Connection...");

				String fetchWSConfigQry = "SELECT CALL_NAME,WSDL_PATH,CABINET,WS_USER,LOGIN_REQ,WS_PASS,TIMEOUT_INTERVAL FROM USR_0_CBG_WS_PROP";
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
						tempList = new ArrayList<String>();
						tempList.add(rs.getString("WSDL_PATH"));
						tempList.add(rs.getString("CABINET"));
						tempList.add(rs.getString("WS_USER"));
						tempList.add(rs.getString("LOGIN_REQ"));
						tempList.add(rs.getString("WS_PASS"));
						tempList.add(rs.getString("TIMEOUT_INTERVAL"));
						webSrvConfMap.put(rs.getString("CALL_NAME"), tempList);
					}
				}
				mapLoadTime=  TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis());
				LogGEN.writeTrace("CBG_Log", "created new conf map at " + mapLoadTime);

				rs.close();rs = null;
				st.close();st = null;
				conn.close();conn = null;

			}
		} catch (Exception e) {
			LogGEN.writeTrace("CBG_Log", "Exception e...readWebSrvConfig"+e);
			LogGEN.writeTrace("CBG_Log", "Exception e...readWebSrvConfig"+e.getStackTrace());
			e.printStackTrace();
		}	
		finally{
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

	void readCabProperty()
	{
		//currentCabPropertyMap.clear();
		String INI=System.getProperty("user.dir") + 
				System.getProperty("file.separator") + "WebServiceConf/DSCOP/DSCOP_config.properties";
		try
		{
			Properties p = new Properties();
			StringBuilder configFile = new StringBuilder(System.getProperty("user.dir"))
			.append(System.getProperty("file.separator"))
			.append("WebServiceConf")
			.append(System.getProperty("file.separator"))
			.append("DSCOP")
			.append(System.getProperty("file.separator"))
			.append("DSCOP_config.properties");
			InputStream stream = new FileInputStream(configFile.toString());
			p.load(stream);
			stream.close();
			currentCabPropertyMap.put("CabinetName",p.getProperty("CabinetName"));
			currentCabPropertyMap.put("JtsIp",p.getProperty("JtsIp"));
			currentCabPropertyMap.put("JtsPort",p.getProperty("JtsPort"));
			currentCabPropertyMap.put("IP",p.getProperty("IP"));
			currentCabPropertyMap.put("Port",p.getProperty("Port"));
			currentCabPropertyMap.put("Server",p.getProperty("Server"));
			currentCabPropertyMap.put("Username",p.getProperty("Username"));
			currentCabPropertyMap.put("Password",p.getProperty("Password"));
			currentCabPropertyMap.put("LoginCount",p.getProperty("LoginCount"));
			currentCabPropertyMap.put("ProcessDefId",p.getProperty("ProcessDefId"));
			currentCabPropertyMap.put("ServletPath",p.getProperty("ServletPath"));
			currentCabPropertyMap.put("DBURL",p.getProperty("DBURL"));
			currentCabPropertyMap.put("USER",p.getProperty("USER"));
			currentCabPropertyMap.put("PASS",p.getProperty("PASS"));
			currentCabPropertyMap.put("Encrypt",p.getProperty("Encrypt"));
			currentCabPropertyMap.put("Decrypt",p.getProperty("Decrypt"));
			currentCabPropertyMap.put("Decrypt602",p.getProperty("Decrypt602"));
			currentCabPropertyMap.put("LogPath",p.getProperty("LogPath"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		LogGEN.writeTrace("CBG_Log", "Config for CBG ===> " + currentCabPropertyMap.toString());
	}	

	private boolean loadConfigChanges()
	{
		boolean reCreateCache = false;
		try {
			long mins =  TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis());
			LogGEN.writeTrace("CBG_Log", "last map load time ..."+mapLoadTime + " current time " + mins);
			mins = mins - mapLoadTime;
			if(mins > 30 ){
				LogGEN.writeTrace("CBG_Log", "time to recheck config...");
				reCreateCache = true;
				//webSrvConfMap.clear();;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reCreateCache;
	}	
	private Map<String, ArrayList<String>> getConfig(){
		LogGEN.writeTrace("CBG_Log", "started getCBGWSConfig");
		Map<String, ArrayList<String>> WSConfMap = null;
		WSConfMap = this.webSrvConfMap;
		return WSConfMap;
	}


	public Map<String, ArrayList<String>> getCBGWSConfig()
	{
		LogGEN.writeTrace("CBG_Log", "started getCBGWSConfig");
		Map<String, ArrayList<String>> finalWebSrvConfMap = null;
		try {
			readWebSrvConfig();
			finalWebSrvConfMap = getConfig();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finalWebSrvConfMap;
	}
}
