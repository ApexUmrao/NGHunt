/*
---------------------------------------------------------------------------------------------------------
                    	NEWGEN SOFTWARE TECHNOLOGIES LIMITED
Group                                   : Application -Projects
Project/Product                         : ADCB_CBG
Application                             : CBG Process
Module                                  : Web Service Client
File Name                               : DBConnection.java
Author                                  : Gautam Rajbhar
Date (DD/MM/YYYY)                       : 11-09-2019
Description                             : Common File for DB Connection.
-------------------------------------------------------------------------------------------------------
CHANGE HISTORY
-------------------------------------------------------------------------------------------------------
Problem No/CR No   Change Date   Changed By    Change Description
------------------------------------------------------------------------------------------------------

-----------------------------------------------------------------------------------------------------*/
package com.newgen.push.message;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


import oracle.jdbc.OraclePreparedStatement;

public class DBConnection {
	
	public String execute(String url, String user, String pass, String Query)
	  {
	    String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
	    String Data = "";
	    try
	    {
	      Class.forName(driver);
	      System.out.println("After database values");
	      Connection conn = DriverManager.getConnection(url, user, pass);
	      System.out.println("Connection Successful");
	      Statement st = conn.createStatement();
	      ResultSet rs = st.executeQuery(Query);
	      rs.close();
	      BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "Insert Successful");
	      st.close();
	      conn.close();
	    }
	    catch (Exception e)
	    {
	      System.out.println("Excepiton --> " + e);
	      BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, e.getMessage());
	      return "ERROR";
	    }
	    return Data;
	  }

	  public String executeClob(String url, String user, String pass, String Query, String input)
	  {
	    String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
	    Connection conn = null;
	    OraclePreparedStatement opstmt = null;
	    String Data = "";
	    try
	    {
	      BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "Before database values");
	      Class.forName(driver);
	      BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "After databse values");
	      conn = DriverManager.getConnection(url, user, pass);
	      BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "Connection Successful");
	      BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "input :  :" + input);
	      BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "query :  " + Query);
	      opstmt = (OraclePreparedStatement)conn.prepareStatement(Query);
	      opstmt.setStringForClob(1, input);
	      opstmt.executeUpdate();
	      BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "After Execute");
	    }
	    catch (Exception e)
	    {
	      System.out.println("Excepiton --> " + e);
	      BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, e.getMessage());
	      return "ERROR";
	    } finally {
	      try {
	        if (opstmt != null)
	          opstmt.close();
	      }
	      catch (Exception e2) {
	        e2.printStackTrace();
	      }
	      try {
	        if (conn != null)
	          conn.close();
	      }
	      catch (Exception e2) {
	        e2.printStackTrace();
	      }
	    }
	    return Data;
	  }
}
