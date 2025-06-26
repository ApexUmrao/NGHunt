package com.newgen.ao.laps.implementation;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.omni.jts.srvr.NGDBConnection;

public class BPMMandatoryCheck {

	public static String isMandatoryCheck(String cabinetname, String channelRefNo, String correlationId){
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside isMandatoryCheck: ");
		boolean isValid = true;
		String retMsg = "";
		Connection con = null;
		try {
	 		con = NGDBConnection.getDBConnection(cabinetname, "APProcedure");
			CallableStatement cstmt = null;
			cstmt = con.prepareCall("{call BPM_MANDATORY_CHECK(?,?,?,?)}");
			cstmt.setString(1, channelRefNo);
			cstmt.setString(2, correlationId);
			cstmt.registerOutParameter(3, Types.INTEGER);
			cstmt.registerOutParameter(4, Types.VARCHAR);
			cstmt.execute();
			int isDD = cstmt.getInt(3);
			retMsg = cstmt.getString(4);
			if(isDD == 0){
				isValid = true;
			}
			else {
				isValid = false;
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"valid: "+isValid);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"retMsg: "+retMsg);
			NGDBConnection.closeDBConnection(con, "APProcedure");
			con = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(con != null){
				try {
					if(!(con.getAutoCommit())){
						con.rollback();
						con.setAutoCommit(true);
					}
					NGDBConnection.closeDBConnection(con, "APProcedure");
					con = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return isValid+"###"+retMsg;
	}
	
	public static String isMandatoryCheck_TSLM(String cabinetname, String channelRefNo, String correlationId,String mode){
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside isMandatoryCheck_TSLM: ");
		boolean isValid = true;
		String retMsg = "";
		Connection con = null;
		try {
	 		con = NGDBConnection.getDBConnection(cabinetname, "APProcedure");
			CallableStatement cstmt = null;
			cstmt = con.prepareCall("{call BPM_TSLM_MANDATORY_CHECK(?,?,?,?,?)}");
			cstmt.setString(1, channelRefNo);
			cstmt.setString(2, correlationId);
			cstmt.setString(3, mode);
			cstmt.registerOutParameter(4, Types.INTEGER);
			cstmt.registerOutParameter(5, Types.VARCHAR);
			cstmt.execute();
			int isDD = cstmt.getInt(4);
			retMsg = cstmt.getString(5);
			if(isDD == 0){
				isValid = true;
			}
			else {
				isValid = false;
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"valid: "+isValid);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"retMsg: "+retMsg);
			NGDBConnection.closeDBConnection(con, "APProcedure");
			con = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(con != null){
				try {
					if(!(con.getAutoCommit())){
						con.rollback();
						con.setAutoCommit(true);
					}
					NGDBConnection.closeDBConnection(con, "APProcedure");
					con = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return isValid+"###"+retMsg;
	}

}
