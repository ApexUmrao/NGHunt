package com.newgen.cbg.utils;

import java.io.IOException;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import org.apache.axis2.wsdl.WSDLConstants;
import com.newgen.cbg.logger.DSCOPLogMe;
import com.newgen.omni.jts.srvr.NGDBConnection;

public class DSCOPAudit {

	private String tableName = "USR_0_DSCOP_AUDIT_TRAIL";
	private String Query = "INSERT INTO " + tableName + " (SYSREFNO, WI_NAME, STAGEID, REQTIME, REQXML, EVENT_MODE) VALUES (?,?,?,?,?,?)";
	
	public boolean serviceRequestAuditLog(String sysRefNumber,String stageId,String wi_name, String event)
    {
       try {
    	   DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"in serviceRequestAuditLog");
           org.apache.axis2.context.MessageContext messagecontext = org.apache.axis2.context.MessageContext.getCurrentMessageContext();
            Timestamp date = new Timestamp(System.currentTimeMillis());
            DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"in serviceRequestAuditLog");
            String reqXML = messagecontext.getOperationContext().getMessageContext(WSDLConstants.MESSAGE_LABEL_IN_VALUE).getEnvelope().toString().trim();
            DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"in serviceRequestAuditLog Before Calling captureSoapRequest");
            captureSoapRequest(sysRefNumber,wi_name,stageId,date,reqXML, event);
            DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"in serviceRequestAuditLog After execution captureSoapRequest");   
          }catch (IOException e) {
                DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
          }catch (Exception e) {
                DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
          }
       return true;  
    }

	private void captureSoapRequest(String  refno, String wi_name, String stageId, Timestamp reqTime,String reqXMl, String event){
          Connection conn= null;          
          PreparedStatement opstmt = null;
          try {
	           DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Befor databse values");
	           conn = NGDBConnection.getDBConnection(DSCOPConfigurations.getInstance().CabinetName, "");           
	           DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Connection Successful => query is "+Query + "ref no is "+ refno);
	           Clob reqXmlClob = conn.createClob();
	           DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Connection Successful => Create CLOB Done");
	           reqXmlClob.setString(1, reqXMl);
	           opstmt=conn.prepareStatement(Query);
	           opstmt.setString(1, refno);
	           opstmt.setString(2, wi_name);          
	           opstmt.setInt(3, Integer.parseInt(stageId.equals("")?"0":stageId));
	           opstmt.setTimestamp(4, reqTime);
	           opstmt.setClob(5, reqXmlClob);
	           opstmt.setString(6, event);
	           opstmt.executeUpdate(); 
	           DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Connection Successful => Clob Update Done");
	           opstmt.close();
	           opstmt = null;
	           conn.close();
	           DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Connection Close => Sttatement Close");
	           conn = null;
           } catch (Exception e) {
                DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,e);
         }
        finally
        {       
    		try {
                if(opstmt!= null)
                {
                      opstmt.close();
                }
                if(conn!= null)
	            {
	               	conn.close();
	            }
                     
    		} catch(Exception e) 
    		{                      
    			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,e);
            }
        }
    }
}
