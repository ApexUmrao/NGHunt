package com.newgen.ao.laps.util;

import java.io.IOException;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import org.apache.axis2.context.MessageContext;
import org.apache.axis2.wsdl.WSDLConstants;

import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.omni.jts.srvr.NGDBConnection;
public class LapsAudit {
	private String tableName = "NG_AO_LAPS_AUDIT_TRAIL";
	  private String Query = "INSERT INTO " + this.tableName + " (SYSREFNO, WI_NAME, AUDIT_TIME,AUDIT_TYPE, AUDIT_XML) VALUES (?,?,?,?,?)";

	  public boolean serviceRequestAuditLog(String sysRefNumber, String wi_name, String type)
	  {
	    try {
	    	LapsModifyLogger.logMe(1, "in serviceRequestAuditLog");
	      MessageContext messagecontext = MessageContext.getCurrentMessageContext();
	      LapsModifyLogger.logMe(1, "in Serervice name "+messagecontext.getOperationContext().getMessageContext(WSDLConstants.MESSAGE_LABEL_IN_VALUE).getEnvelope().toString().trim());
	      LapsModifyLogger.logMe(1, "in Serervice name "+messagecontext.getAxisService().toString());
			 
	      Timestamp date = new Timestamp(System.currentTimeMillis());
	      String reqXML = messagecontext.getOperationContext().getMessageContext("In").getEnvelope().toString().trim();
	      LapsModifyLogger.logMe(1, "in serviceRequestAuditLog"+reqXML);
		    
	      captureSoapRequest(sysRefNumber, wi_name, date, type, reqXML);
	    }
	    catch (IOException e) {
	    	LapsModifyLogger.logMe(2, e);
	    } catch (Exception e) {
	    	LapsModifyLogger.logMe(2, e);
	    }
	    return true;
	  }

	  private void captureSoapRequest(String refno, String wi_name,  Timestamp reqTime, String type, String reqXMl) {
	    Connection conn = null;
	    PreparedStatement opstmt = null;
	    try {
	    	LapsModifyLogger.logMe(1, "Befor databse values");
	      conn = NGDBConnection.getDBConnection(LapsConfigurations.getInstance().CabinetName, "");
	      LapsModifyLogger.logMe(1, "Connection Successful => query is " + this.Query);
	      Clob reqXmlClob = conn.createClob();
	      reqXmlClob.setString(1L, reqXMl);
	      opstmt = conn.prepareStatement(this.Query);
	      opstmt.setString(1, refno);
	      opstmt.setString(2, wi_name);
	      opstmt.setTimestamp(3, reqTime);
	      opstmt.setString(4, type);
	      opstmt.setClob(5, reqXmlClob);
	      opstmt.executeUpdate();
	      opstmt.close();
	      opstmt = null;
	      conn.close();
	      conn = null;
	    } catch (Exception e) {
	    	LapsModifyLogger.logMe(2, e);
	    }
	    finally
	    {
	      try {
	        if (opstmt != null)
	        {
	          opstmt.close();
	        }
	        if (conn != null)
	        {
	          conn.close();
	        }
	      }
	      catch (Exception e)
	      {
	    	  LapsModifyLogger.logMe(2, e);
	      }
	    }
	  }
}
