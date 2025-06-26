package com.newgen.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import com.newgen.AESEncryption;

public class TemplateGeneration extends WebServiceHandler {
	
	// Constants
	public static final String NG_UTILITY_TMP_GEN_CONFIG = "NG_UTILITY_TMP_GEN_CONFIG";
	public static final String IP = "IP";
	public static final String PORT = "PORT";
	public static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
	
	public enum Status {
		ERROR("-100", "Some error occurred while generating Templates. Please try again later or contact app support team."),
		MISC_EXCEPTION("-1000", "");
		
		private final String code;
		private final String msg;
		
		private Status(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}
		
		public String getCode() {
			return code;
		}
		
		public String getMsg() {
			return msg;
		}
		
	}
	
	protected static HashMap<String, String> tmpGenConfig = new HashMap<>();
	
	static {
		initTmpGenConfig();
	}
	
	public static void initTmpGenConfig() {
		LogGEN.writeTrace("Log", "Inside initTmpGenConfig >");
		WebServiceHandler handler = new WebServiceHandler();
		String query = "SELECT KEY, VALUE FROM "+ NG_UTILITY_TMP_GEN_CONFIG +" WHERE KEY IN ('"+ IP +"', '"+ PORT +"')"; 
		try {
			Class.forName(DB_DRIVER);
			handler.readCabProperty("JTS");	
			String dburl = (String)currentCabPropertyMap.get("DBURL");
			String dbuser = (String)currentCabPropertyMap.get("USER");
			String dbpass = (String)currentCabPropertyMap.get("PASS");
			dbpass=AESEncryption.decrypt(dbpass);
	   	 	try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@"+dburl, dbuser, dbpass);
	   	 			Statement statement = connection.createStatement();
	   	 			ResultSet resultSet = statement.executeQuery(query)) {
				while(resultSet.next()) {
					String key = resultSet.getString("KEY");
					String value = resultSet.getString("VALUE");
					LogGEN.writeTrace("Log", "KEY: "+ key +", VALUE: "+ value);
					tmpGenConfig.put(key, value);
				}
				LogGEN.writeTrace("Log", "Template generation config initialized");
	   	 	} catch (SQLException e) {
				LogGEN.writeTrace("Log", "SQLException in initTmpGenConfig"+ e.getMessage());
				System.out.println("SQLException in initTmpGenConfig"+ e.getMessage());
				e.printStackTrace();
			}
		} catch (Exception e) {
			LogGEN.writeTrace("Log", "Exception in initTmpGenConfig"+ e.getMessage());
			System.out.println("Exception in initTmpGenConfig"+ e.getMessage());
			e.printStackTrace();
		}
	}
	
	public String generateTemplate(String inputXML) {
		LogGEN.writeTrace("Log", "Inside generateTemplate >");
		String outputXML = "";
		XMLParser xmlParser = new XMLParser();
		xmlParser.setInputXML(inputXML);
		try {
			String templateInputXML = "<TemplateGeneration_Input>"
				+"<Option>TemplateGeneration</Option>"
				+"<Data>"
				+"<ProcessName>"+ xmlParser.getValueOf("ProcessName") +"</ProcessName>"
				+"<WorkitemName>"+ xmlParser.getValueOf("WorkitemName") +"</WorkitemName>"
				+"<Category>"+ xmlParser.getValueOf("Category") +"</Category>"
				+"</Data>"
				+"</TemplateGeneration_Input>";
			outputXML = connectToSocket(templateInputXML);
		} catch (Exception exception) {
			LogGEN.writeTrace("Log", "Exception in generateTemplate: "+ exception.getMessage());
			exception.printStackTrace();
		} finally {
			if (outputXML.isEmpty()) {
				outputXML = "<TemplateGeneration_Output>"
						+ "<Option>TemplateGeneration</Option>"
						+ "<Data>"
						+"<ProcessName>"+ xmlParser.getValueOf("ProcessName") +"</ProcessName>"
						+"<WorkitemName>"+ xmlParser.getValueOf("WorkitemName") +"</WorkitemName>"
						+"<Category>"+ xmlParser.getValueOf("Category") +"</Category>"
						+ "</Data>"
						+ "<Status>"+ Status.ERROR.getCode() +"</Status>"
						+ "<UserDescription>"+ Status.ERROR.getMsg() +"</UserDescription>"
						+ "<Response>"
						+ "<SubErrorCode>["+ Status.MISC_EXCEPTION.getCode() +"]</SubErrorCode>"
						+ "<Description>[Unable to receive Template Generation Output XML.]</Description>"
						+ "<Templates>"
						+ "<Success>[]</Success>"
						+ "<Error>[]</Error>"
						+ "</Templates>"
						+ "</Response>"
						+ "</TemplateGeneration_Output>";
			}
		}
		return outputXML;
	}

	public String connectToSocket(String data) {
		LogGEN.writeTrace("Log", "Inside connectToSocket >");
		String result="";
		try(Socket s = new Socket(tmpGenConfig.get("IP"), Integer.parseInt(tmpGenConfig.get("PORT")));
			DataInputStream din=new DataInputStream(s.getInputStream());
			DataOutputStream dout=new DataOutputStream(s.getOutputStream())) {
			writeDataToSocket(dout,data);
			result=readDataFromSocket(din);
			LogGEN.writeTrace("Log", "Result: "+ result);
		} catch (IOException e) {
			LogGEN.writeTrace("Log", "Exception in connectToSocket: "+ e.getMessage());
			e.printStackTrace();
		}
		return result;	
	}
	
	public boolean writeDataToSocket(DataOutputStream dataOutputStream, String data) {
		LogGEN.writeTrace("Log", "Inside writeDataToSocket >");
		boolean bFlag = false;
		try {
			if (data != null && data.length() > 0) {
				dataOutputStream.write(data.getBytes(StandardCharsets.UTF_8));
				bFlag=true;
			}
		} catch (Exception e) {
			LogGEN.writeTrace("Log", "Exception in writeDataToSocket: "+ e.getMessage());
			e.printStackTrace();
		}
		return bFlag;
	}

  public String readDataFromSocket(DataInputStream dataInputStream) {
	  LogGEN.writeTrace("Log", "Inside readDataFromSocket >");
	  StringBuilder data = new StringBuilder();
	  try {
          byte[] buffer = new byte[99999];
          int length = dataInputStream.read(buffer, 0, 99999);
          byte[] arrayBytes = new byte[length];
          System.arraycopy(buffer, 0, arrayBytes, 0, length);
          data.append(new String(arrayBytes, StandardCharsets.UTF_8));
          int len = 0;
          while ((len = dataInputStream.read(buffer)) > 0) {
              arrayBytes = new byte[len];
              System.arraycopy(buffer, 0, arrayBytes, 0, len);
              data.append(new String(arrayBytes, StandardCharsets.UTF_8));
              if (dataInputStream.available()<=0)
                  break;
          }
      } catch (Exception e) {
			LogGEN.writeTrace("Log", "Exception in readDataFromSocket: "+ e.getMessage());
			e.printStackTrace();
      }
	  
	  LogGEN.writeTrace("Log", "Data: "+ data.toString());
	  
      return data.toString();
  }
		
}
