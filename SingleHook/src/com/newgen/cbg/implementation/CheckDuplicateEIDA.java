package com.newgen.cbg.implementation;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import com.newgen.cbg.logger.DSCOPLogMe;
import com.newgen.cbg.response.CBGSingleHookResponse;
import com.newgen.cbg.utils.APCallCreateXML;
import com.newgen.omni.jts.srvr.NGDBConnection;
import com.newgen.omni.wf.util.xml.XMLParser;

public class CheckDuplicateEIDA {
	public static boolean isDuplicate(String eidaCardNumber, String cabinetname, CBGSingleHookResponse responseObj){
		boolean isDeDupe = true;
		Connection con = null;
		try {
	 		con = NGDBConnection.getDBConnection(cabinetname, "APProcedure");
			CallableStatement cstmt = null;
			cstmt = con.prepareCall("{call DEDUPE(?,?,?,?,?,?,?)}");
			cstmt.setString(1, "E");//Mode
			cstmt.setString(2, "");//MobileNo
			cstmt.setString(3, eidaCardNumber);//EIDANo
			cstmt.setString(4, "");//PASSPORTNo
			cstmt.setString(5, "");//Nationality
			cstmt.setString(6, "");//DOB
			cstmt.registerOutParameter(7, Types.INTEGER);
			cstmt.execute();
			int isDD = cstmt.getInt(7);
			if(isDD == -1){
				isDeDupe = true;
			}
			else {
				isDeDupe = false;
			}
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"EIDA DEDUPE: "+isDeDupe);
			NGDBConnection.closeDBConnection(con, "APProcedure");
			con = null;
			if(isDeDupe){
				String outputXML = APCallCreateXML.APSelect("SELECT STAGE_NAME, LEAD_REF_NO, WI_NAME  FROM EXT_CBG_CUST_ONBOARDING WHERE EIDA_CARD_NO = '"+ eidaCardNumber +"'");
				XMLParser xp = new XMLParser(outputXML);
				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				if(mainCode == 0){
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"TotalRetrieved: "+Integer.parseInt(xp.getValueOf("TotalRetrieved")));
					if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
						responseObj.setWSName(xp.getValueOf("STAGE_NAME"));
						responseObj.setLeadNumber(xp.getValueOf("LEAD_REF_NO"));					
						responseObj.setWI_NAME(xp.getValueOf("WI_NAME"));
					}
				}
			}
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
