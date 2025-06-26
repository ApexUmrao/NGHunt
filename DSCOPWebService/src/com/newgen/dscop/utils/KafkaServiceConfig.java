package com.newgen.dscop.utils;
 import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.newgen.AESEncryption;
import com.newgen.dscop.utils.KafkaServiceConfig;
import com.newgen.client.LogGEN;
import com.newgen.dscop.client.DSCOPServiceHandler;
import com.newgen.dscop.client.WebServiceConfig;

public class KafkaServiceConfig extends DSCOPServiceHandler {

	static HashMap<String, String> kafkaMap = new HashMap<String, String>();

	private static KafkaServiceConfig kafkaServiceConfig = null;

	private KafkaServiceConfig() {

	}

	@SuppressWarnings("unchecked")
	public static KafkaServiceConfig getInstance() {
		LogGEN.writeTrace("CBG_Log", "inside kafka service config instance:");

		if (null == kafkaServiceConfig) {
			kafkaServiceConfig = new KafkaServiceConfig();
			LogGEN.writeTrace("CBG_Log", "kafka service config instance is created:");
			if (kafkaMap.isEmpty()) {
				LogGEN.writeTrace("CBG_Log", "inside kafkamap is empty:");
				getKafkaConfig();
			}
		}
		return kafkaServiceConfig;
	}

	private static Map<String, String> getKafkaConfig() {

		Connection conn = null;
		ResultSet rs = null;
		Statement st = null;
		LogGEN.writeTrace("CBG_Log", "inside get kafka config :");

		try {
			// readCabProperty();
			if (currentCabPropertyMap == null) {
				currentCabPropertyMap = new HashMap<String, String>();
			}
			if(kafkaMap.isEmpty() || currentCabPropertyMap.isEmpty())
			{
			    readCabProperty();
			String dburl = (String) currentCabPropertyMap.get("DBURL");
			String dbuser = (String) currentCabPropertyMap.get("USER");
			String dbpass = (String) currentCabPropertyMap.get("PASS");
			LogGEN.writeTrace("CBG_Log", "creating Connection...");
			String fetchConfigQry = ("SELECT KEY,VALUE FROM BPM_KAFKA_PROPERTIES where KAFKA_TYPE='Producer'");
			LogGEN.writeTrace("CBG_Log", "creating new conf map Query :" + fetchConfigQry);
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
			rs = st.executeQuery(fetchConfigQry);

			if (rs != null) {
				while (rs.next()) {

					kafkaMap.put(rs.getString("KEY"), rs.getString("VALUE"));
				}
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
			conn.close();
			conn = null;
			}	
	} catch (Exception e) {
			LogGEN.writeTrace("CBG_Log", "Exception e...readLogfigValues" + e);
			LogGEN.writeTrace("CBG_Log", "Exception e...readLonfigValues" + e.getStackTrace());
			e.printStackTrace();
		} finally {
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
		return kafkaMap;
	}

	public HashMap<String, String> getKafkConfig() {
		return kafkaMap;
	}
	private static void readCabProperty()
	{
		//currentCabPropertyMap.clear();
		String INI=System.getProperty("user.dir") + 
				System.getProperty("file.separator") + "WebServiceConf/CBG/CBG_config.properties";
		try
		{
			Properties p = new Properties();
			StringBuilder configFile = new StringBuilder(System.getProperty("user.dir"))
			.append(System.getProperty("file.separator"))
			.append("WebServiceConf")
			.append(System.getProperty("file.separator"))
			.append("CBG")
			.append(System.getProperty("file.separator"))
			.append("CBG_config.properties");
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

}
