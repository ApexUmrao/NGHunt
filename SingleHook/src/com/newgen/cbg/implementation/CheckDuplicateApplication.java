package com.newgen.cbg.implementation;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import com.newgen.cbg.logger.DSCOPLogMe;
import com.newgen.omni.jts.srvr.NGDBConnection;

public class CheckDuplicateApplication {

	public static boolean isDuplicate(String mobileNumber, String cabinetname, String CustomerId, String Mode){
		boolean isDeDupe = true;
		Connection con = null;
		try {
	 		con = NGDBConnection.getDBConnection(cabinetname, "APProcedure");
			CallableStatement cstmt = null;
			cstmt = con.prepareCall("{call DEDUPE(?,?,?,?,?,?,?,?)}");
			cstmt.setString(1, Mode);//Mode
			if("MIB".equals(Mode)){
				cstmt.setString(2, CustomerId);//CID
			} else {
				cstmt.setString(2, mobileNumber);//MobileNo
			}	
			cstmt.setString(3, "");//EIDANo
			cstmt.setString(4, "");//PASSPORTNo
			cstmt.setString(5, "");//Nationality
			cstmt.setString(6, "");//DOB
			cstmt.setString(7, "");//WINAME
			cstmt.registerOutParameter(8, Types.INTEGER);
			cstmt.execute();
			int isDD = cstmt.getInt(8);
			if(isDD == -1){
				isDeDupe = true;
			}
			else {
				isDeDupe = false;
			}
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"DEDUPE: "+isDeDupe);
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
		return isDeDupe;
	}
}
