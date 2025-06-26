package com.newgen.client;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import oracle.jdbc.OraclePreparedStatement;

import com.newgen.omni.jts.srvr.NGDBConnection;


public class DBConnection 
{
	public String execute(String url,String user,String pass,String Query)
	{
		String driver = "oracle.jdbc.driver.OracleDriver";
		 String Data="";
	    try
	    {
	    	 Class.forName(driver);
	    	 System.out.println("After databse values");
	    	 Connection conn = DriverManager.getConnection(url, user, pass);
	    	 System.out.println("Connection Successful");
	    	 Statement st = conn.createStatement();
			 ResultSet rs = st.executeQuery(Query);
			 rs.close();
			 LogGEN.writeTrace("Log", "Insert Successful");
			 st.close();
			 conn.close();
			
	    }
	    catch(Exception e)
	    {
	    	System.out.println("Excepiton--"+e);
	    	 LogGEN.writeTrace("Log", e.getMessage());
	    	 return "ERROR";
	    }
	    return Data;
	}	
	
	public String executeClob(String url,String user,String pass,String Query,String input,String content)
	{
		String driver = "oracle.jdbc.driver.OracleDriver";
		Connection conn=null;
		OraclePreparedStatement opstmt=null;
		String Data="";
	    try
	    {
	    	 LogGEN.writeTrace("Log", "Befor databse values");
	    	 Class.forName(driver);
	    	 System.out.println("After databse values");
	    	 LogGEN.writeTrace("Log", "After databse values");
	    	 conn = DriverManager.getConnection(url, user, pass);
	    	 System.out.println("Connection Successful");
	    	 LogGEN.writeTrace("Log", "Connection Successful");
	    	 LogGEN.writeTrace("Log","input :  :"+input);
	    	 LogGEN.writeTrace("Log", "content :  "+content);
	    	 opstmt=(OraclePreparedStatement)conn.prepareStatement(Query);
	    	 opstmt.setStringForClob(1, input);
	    	 opstmt.setStringForClob(2, content);
	    	 opstmt.executeUpdate();	    	
			 LogGEN.writeTrace("Log", "After Execute");			 
			
	    }
	    catch(Exception e)
	    {
	    	System.out.println("Excepiton--"+e);
	    	 LogGEN.writeTrace("Log", e.getMessage());
	    	 return "ERROR";
	    }finally{
	    	try {
				if(opstmt!=null){
					opstmt.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			try {
				if(conn!=null){
					conn.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
	    }
	    return Data;
	}
	
	public String executeProc(String url,String user,String pass,String Query,String Params,int outparam,String type)
	{
		String driver = "oracle.jdbc.driver.OracleDriver";
	    try
	    {
	    	 LogGEN.writeTrace("Log", "Befor databse values");
	    	 Class.forName(driver);
	    	 System.out.println("After databse values");
	    	 LogGEN.writeTrace("Log", "After databse values");
	    	 Connection conn = DriverManager.getConnection(url, user, pass);
	    	 System.out.println("Connection Successful");
	    	 LogGEN.writeTrace("Log", "Connection Successful");
	    	 CallableStatement st = conn.prepareCall(Query);
	    	 System.out.println(Params);
	    	 String[] args=Params.split(":;");
	    	for(int i=1;i<=args.length;i++)
	    	{
	    		st.setString(i, args[i-1].trim());
	    		System.out.println(args[i-1]);
	    	}
	    	if(type.equalsIgnoreCase("number"))
	    		st.registerOutParameter(outparam, Types.INTEGER);
	    	else if(type.equalsIgnoreCase("string"))
	    		st.registerOutParameter(outparam,Types.VARCHAR);
	    	System.out.println("Executed%%%%%%%%%%234234%%%%%%%%%%%");
			 ResultSet rs = st.executeQuery();
			 System.out.println("Executed%%%%%%%%%%%%%%%%%%%%%");
			 String count=st.getString(outparam);
			// ResultSetMetaData rsmd = rs.getMetaData();
			// int NumOfCol=rsmd.getColumnCount();
			 rs.close();
			 LogGEN.writeTrace("Log", "After Execute");
			// System.out.println("Number of Columns="+NumOfCol);	
			 st.close();
			 conn.close();
			return count;
	    }
	    catch(Exception e)
	    {
	    	System.out.println("Excepiton--"+e);
	    	 LogGEN.writeTrace("Log", e.getMessage());
	    	 return "ERROR";
	    }
	  
	}	
	public String executeSelect(String url,String user,String pass,String Query)
	{
		String driver = "oracle.jdbc.driver.OracleDriver";
	    //String url = "jdbc:oracle:thin:@10.146.166.190:1840:WMS";
		//String username = "adwms001";
		//String password = "adwms001";
		 String Data="";
	    try
	    {
	    	 LogGEN.writeTrace("Log", "Befor databse values");
	    	 Class.forName(driver);
	    	 System.out.println("After databse values");
	    	 LogGEN.writeTrace("Log", "After databse values");
	    	 Connection conn = DriverManager.getConnection(url, user, pass);
	    	 System.out.println("Connection Successful");
	    	 LogGEN.writeTrace("Log", "Connection Successful");
	    	 Statement st = conn.createStatement();
			 ResultSet rs = st.executeQuery(Query);
			 while(rs.next())
			 {
				 Data=rs.getString(1).toString();
			 }
			// ResultSetMetaData rsmd = rs.getMetaData();
			// int NumOfCol=rsmd.getColumnCount();
			 LogGEN.writeTrace("Log", "After Execute");
			// System.out.println("Number of Columns="+NumOfCol);
			 rs.close();
			 st.close();
			 conn.close();
			 return Data;
			
	    }
	    catch(Exception e)
	    {
	    	System.out.println("Excepiton--"+e);
	    	 LogGEN.writeTrace("Log", e.getMessage());
	    }
	    return Data;
	}	
	
	public String executeSelectTR(String url,String user,String pass,String Query)
	{
		String driver = "oracle.jdbc.driver.OracleDriver";
	    //String url = "jdbc:oracle:thin:@10.146.166.190:1840:WMS";
		//String username = "adwms001";
		//String password = "adwms001";
		 String Data="";
		 System.out.println(Query);
	    try
	    {
	    	
	    	 Class.forName(driver);	    	
	    	 Connection conn = DriverManager.getConnection(url, user, pass);	    	
	    	 Statement st = conn.createStatement();
			 ResultSet rs = st.executeQuery(Query);
			
			 while(rs.next())
			 {
				 
				 Data+="<Record><FI3>"+rs.getString(1).toString()+"</FI3><Rdate>"+rs.getString(2).toString()+"</Rdate><Docindex>"+rs.getString(3).toString()+"</Docindex></Record>";
			 }
			// ResultSetMetaData rsmd = rs.getMetaData();
			// int NumOfCol=rsmd.getColumnCount();
			 LogGEN.writeTrace("Log", "After Execute");
			// System.out.println("Number of Columns="+NumOfCol);
			 rs.close();
			 st.close();
			 conn.close();
			 return Data;
			
	    }
	    catch(Exception e)
	    {
	    	System.out.println("Excepiton--"+e);
	    	 LogGEN.writeTrace("Log", e.getMessage());
	    }
	    return Data;
	}
	
	public void executeBatch(String url,String user,String pass,String inputXML,String sysRefNo,String leafRefNo){
		PreparedStatement pstmt=null;
		Connection conn=null;
		try {
			WFXmlResponse xmlResponse = new WFXmlResponse(inputXML);			
			WFXmlList lWfXml = xmlResponse.createList("DocumentsDtls","Documents");
			String driver = "oracle.jdbc.driver.OracleDriver";
			Class.forName(driver);  	 
			LogGEN.writeTrace("Log", "After databse values");
			conn = DriverManager.getConnection(url, user, pass);
			String inserQuery="INSERT INTO USR_0_WBG_TMP_DOC (LEAD_REFNO,SYSREFNO,ENTITYTYPE,CUSTOMERID,CUSTOMERNAME,DOCUMENTNAME," +
					"DOCEXT,DOCUMENTTYPE,DOCUMENTINDEX,UPLOADEDDATE,PARENTREF,IMAGEINDEX,VOLUMEID,NOOFPAGES,DOCSIZE,CUST_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(inserQuery);
			for (int i = 0; lWfXml.hasMoreElements(true); lWfXml.skip(true), i++){
				pstmt.setString(1,leafRefNo);
				pstmt.setString(2,sysRefNo);				
				pstmt.setString(3,lWfXml.getVal("EntityType"));
				pstmt.setString(4,lWfXml.getVal("CustomerId"));
				pstmt.setString(5,lWfXml.getVal("CustomerName"));
				pstmt.setString(6,lWfXml.getVal("DocumentName"));
				pstmt.setString(7,lWfXml.getVal("DocExt"));				
				pstmt.setString(8,lWfXml.getVal("DocumentType"));				
				pstmt.setString(9,lWfXml.getVal("DocumentIndex"));				
				pstmt.setString(10,lWfXml.getVal("UploadedDate"));				
				pstmt.setString(11,lWfXml.getVal("ParentRef"));				
				pstmt.setString(12,lWfXml.getVal("IMAGEINDEX"));
				pstmt.setString(13,lWfXml.getVal("VOLUMEID"));
				pstmt.setString(14,lWfXml.getVal("NOOFPAGES"));
				pstmt.setString(15,lWfXml.getVal("DOCUMENTSIZE"));
				pstmt.setString(16,lWfXml.getVal("CustomerId"));
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			conn.commit();			
		} catch (ClassNotFoundException e2) {
			StringWriter sw = new StringWriter();
			e2.printStackTrace(new PrintWriter(sw));
			LogGEN.writeTrace("Log",sw.toString());
			e2.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			LogGEN.writeTrace("Log",sw.toString());
			e.printStackTrace();
		}finally
		{
			try {
				
				if(pstmt!=null){
					pstmt.close();
				}
				if(conn!=null){
					conn.close();
				}				
			} catch (SQLException e2) {
				StringWriter sw = new StringWriter();
				e2.printStackTrace(new PrintWriter(sw));
				LogGEN.writeTrace("Log",sw.toString());
			}catch (Exception e2) {
				StringWriter sw = new StringWriter();
				e2.printStackTrace(new PrintWriter(sw));
				LogGEN.writeTrace("Log",sw.toString());
			}		
		}

	}

	// DEPENDECY OF THIS FUNCTION HAS BEEN COMPLETELY REMOVED BY ABHAY 06_01_2021
	/*public String executeOnEDMS(String edmsDB, String docIndex) throws SQLException
	{
		String driver = "oracle.jdbc.driver.OracleDriver";
		ResultSet localResultSet = null;
		Statement localStatement = null;
		ResultSetMetaData localResultSetMetaData = null;
		StringBuffer localStringBuffer2 = new StringBuffer();
		StringBuffer localStringBuffer1= new StringBuffer();
		Connection conn =null;
		String sQuery="";

		try
		{
			sQuery="SELECT DOCUMENTINDEX ,IMAGEINDEX, VOLUMEID,NOOFPAGES, DOCUMENTSIZE,APPNAME FROM PDBDOCUMENT WHERE DOCUMENTINDEX IN ("+docIndex+")";
			LogGEN.writeTrace("Log",sQuery);

			Class.forName(driver);			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@10.101.108.86:1898:EDMS", "ADDMS001", "ADDMS001");
			//conn=NGDBConnection.getDBConnection(edmsDB, "Oracle");
			LogGEN.writeTrace("Log","connection success");
			localStatement = conn.createStatement();
			localResultSet = localStatement.executeQuery(sQuery);
			localResultSetMetaData = localResultSet.getMetaData();
			LogGEN.writeTrace("Log",localResultSetMetaData.toString());
			int i = localResultSetMetaData.getColumnCount();
			int j = 0;
			while (localResultSet.next()) {
				localStringBuffer2.append("\n\t\t<Record>");

				for (int k = 1; k <= i; ++k) {	        	
					localStringBuffer2.append("\n\t\t\t<" + localResultSetMetaData.getColumnName(k) + ">");
					localStringBuffer2.append(removeNULL(localResultSet.getString(k)));
					localStringBuffer2.append("</" + localResultSetMetaData.getColumnName(k) + ">");
					LogGEN.writeTrace("Log",localStringBuffer2.toString());
				}
				LogGEN.writeTrace("Log",localStringBuffer2.toString());
				localStringBuffer2.append("\n\t\t</Record>");
				j += 1;
			}
			String  str1 = localStringBuffer2.toString();
			str1 = localStringBuffer2.toString();
			str1 = "\n\t<Records>" + str1 + "\n\t</Records>";
			str1 = str1 + "\n\t<TotalRetrieved>" + j + "</TotalRetrieved>";
			localStringBuffer1.append("<Exception>\n<MainCode>0</MainCode>\n</Exception>\n");
			localStringBuffer1.append("<Output>" + str1 + "\n</Output>");
			//System.out.println("Log Insert Successful"+str1);
			LogGEN.writeTrace("Log",str1);
			conn.close();
		}
		catch (Throwable localException4) {
			conn.close();
			localStringBuffer1.append("<Exception>\n<MainCode>" + localException4 + "</MainCode>\n</Exception>\n");
			localStringBuffer1.append("<Output>" + localException4.getMessage() + "\n</Output>\n");
			localException4.printStackTrace();
		}
		/*catch (Throwable localException5)
		{
			localStringBuffer1.append("<Exception>\n<MainCode>9</MainCode>\n</Exception>\n");
			localStringBuffer1.append("<Output>" + localException5.toString() + "\n</Output>\n");			
		}finally{
			try {
				if(localResultSet!=null)
					localResultSet.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if(localStatement!=null)
				localStatement.close();
			}
			catch (Throwable e) {
				e.printStackTrace();
			}
			try {
				if(conn!=null)
					NGDBConnection.closeDBConnection(conn, "Oracle");
			}
			catch (Throwable e) {
				e.printStackTrace();
			}
		}
		LogGEN.writeTrace("Log",localStringBuffer1.toString());
		return localStringBuffer1.toString();
	}*/
	
	private static String removeNULL(String paramString) {
		if (paramString == null) {
			return "";
		}
		return paramString.trim();
	}
	
//	public static String executeSelectToWMS(String wmsDb, String sQuery)
	// UPDATED BY ABHAY AS A CONSEQUENCE OF WEBSERVICE CALLS NOW BEING EXECUTED THROUGH SOCKET 04_01_2021
	public String executeSelectXML(String url, String user, String pass, String sQuery)
	{
		//String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
		ResultSet localResultSet = null;
		Statement localStatement = null;
		ResultSetMetaData localResultSetMetaData = null;
		StringBuffer localStringBuffer2 = new StringBuffer();
		StringBuffer localStringBuffer1= new StringBuffer();
		Connection conn =null;
		

		try
		{
			//sQuery="SELECT DOCUMENTINDEX ,IMAGEINDEX, VOLUMEID,NOOFPAGES, DOCUMENTSIZE,APPNAME FROM PDBDOCUMENT WHERE DOCUMENTINDEX IN ("+docIndex+")";
		//	Class.forName(driver);			
			//conn = DriverManager.getConnection(url, user, pass);
			String driver = "oracle.jdbc.driver.OracleDriver";
			Class.forName(driver);  	 
			LogGEN.writeTrace("Log", "After databse values");
			conn = DriverManager.getConnection(url, user, pass);
			
//			conn=NGDBConnection.getDBConnection(wmsDb, "Oracle");
			localStatement = conn.createStatement();
			localResultSet = localStatement.executeQuery(sQuery);
			localResultSetMetaData = localResultSet.getMetaData();
			int i = localResultSetMetaData.getColumnCount();
			int j = 0;
			while (localResultSet.next()) {
				localStringBuffer2.append("\n\t\t<Record>");

				for (int k = 1; k <= i; ++k) {	        	
					localStringBuffer2.append("\n\t\t\t<" + localResultSetMetaData.getColumnName(k) + ">");
					localStringBuffer2.append(removeNULL(localResultSet.getString(k)));
					localStringBuffer2.append("</" + localResultSetMetaData.getColumnName(k) + ">");
				}

				localStringBuffer2.append("\n\t\t</Record>");
				j += 1;
			}
			String  str1 = localStringBuffer2.toString();
			str1 = localStringBuffer2.toString();
			str1 = "\n\t<Records>" + str1 + "\n\t</Records>";
			str1 = str1 + "\n\t<TotalRetrieved>" + j + "</TotalRetrieved>";
			localStringBuffer1.append("<Exception>\n<MainCode>0</MainCode>\n</Exception>\n");
			localStringBuffer1.append("<Output>" + str1 + "\n</Output>");
			//System.out.println("Log Insert Successful"+str1);
			LogGEN.writeTrace("Log",str1);
			
		}
		catch (SQLException localException4) {
			localStringBuffer1.append("<Exception>\n<MainCode>" + localException4.getErrorCode() + "</MainCode>\n</Exception>\n");
			localStringBuffer1.append("<Output>" + localException4.getMessage() + "\n</Output>\n");
			
		}
		catch (Exception localException5)
		{
			localStringBuffer1.append("<Exception>\n<MainCode>9</MainCode>\n</Exception>\n");
			localStringBuffer1.append("<Output>" + localException5.toString() + "\n</Output>\n");			
		}finally{
			try {
				if(localResultSet!=null)
					localResultSet.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if(localStatement!=null)
				localStatement.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if(conn!=null)
					conn.close();
//					NGDBConnection.closeDBConnection(conn, "Oracle");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		return localStringBuffer1.toString();
	}
}
