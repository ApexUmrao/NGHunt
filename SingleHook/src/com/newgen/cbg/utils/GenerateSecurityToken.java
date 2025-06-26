package com.newgen.cbg.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import com.newgen.cbg.logger.DSCOPLogMe;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.wf.util.excp.NGException;

public class GenerateSecurityToken {

	public static String createKey(String sessionId,String WI_NAME,HashMap<String, String> attributeMap,
			HashMap<String,Integer> modeExpiry) throws NGException{
		StringBuffer keyValue = new StringBuffer();
		String encryptedKey = "";
		String outputXml = "";
		String mode = "";
		int expiry = -1;
		try {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[createKey]sessionId : " + sessionId);
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[createKey]workitemNumber : " + WI_NAME);
			outputXml = APCallCreateXML.APSelect("SELECT COUNT(1) AS COUNT FROM BPM_AUTHENTICATION_TXN_DATA WHERE WI_NAME = '"+WI_NAME+"'");
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[createKey]outputXml : " + outputXml);
			if(outputXml != null && !"".equals(outputXml)){
				XMLParser xp = new XMLParser(outputXml);
				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				if(mainCode == 0) {
					int count = Integer.parseInt(xp.getValueOf("COUNT"));
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[createKey]count : " + count);
					if(count == 0) {
						for (Map.Entry<String, String> entrySet : attributeMap.entrySet()){
							String key = entrySet.getKey();
							String value = entrySet.getValue();
							DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[createKey]key : " + key);
							DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[createKey]keyValue : " + value);
							if (value != null && !"".equals(value)) {
								keyValue = keyValue.append(value);
							} else {
								keyValue = keyValue.append((char)25);
							}
						}
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[createKey]Final KeyValue : " + keyValue);
						encryptedKey = DigestUtils.sha256Hex(keyValue.toString());
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[createKey]encryptedKey : " + encryptedKey);
						for (Map.Entry<String , Integer> entry : modeExpiry.entrySet()){
							mode = entry.getKey();
							expiry = entry.getValue();
							DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[createKey]mode : " + mode);
							DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[createKey]expiry : " + expiry);
							outputXml = APCallCreateXML.APInsert("BPM_AUTHENTICATION_TXN_DATA", "WI_NAME,EVENT,KEY,ACTION_DATETIME,EXPIRYTIME",
									"'"+WI_NAME+"','"+mode+"','"+encryptedKey+"',SYSDATE,SYSDATE+("+expiry+"/1440)", sessionId);
							DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[createKey]outputXml : " + outputXml);
						}	
					} else {
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[createKey]return false - count is not zero: "+count);
						outputXml = APCallCreateXML.APSelect("SELECT KEY FROM BPM_AUTHENTICATION_TXN_DATA WHERE WI_NAME='"+WI_NAME+"'");
						xp = new XMLParser(outputXml);
						encryptedKey = xp.getValueOf("KEY");
					}
				} else {
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[createKey]return false - maincode is not zero: "+mainCode);
				}
			} else {
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[createKey]return false - issue with outputxml: "+outputXml);
			} 
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Exception : " + e.toString());
		}
		return encryptedKey;
	}

	public static String createCCSSOKey(String sessionId,String WI_NAME,String json,HashMap<String,Integer> modeExpiry) throws NGException{
		String encryptedKey = "";
		String outputXml = "";
		String mode = "";
		int expiry = -1;
		try {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[createCCSSOKey]sessionId : " + sessionId);
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[createCCSSOKey]workitemNumber : " + WI_NAME);
			outputXml = APCallCreateXML.APSelect("SELECT KEY FROM BPM_AUTHENTICATION_TXN_DATA WHERE WI_NAME = '"+WI_NAME+"'");
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[createKey]outputXml : " + outputXml);
			if(outputXml != null && !"".equals(outputXml)){
				XMLParser xp = new XMLParser(outputXml);
				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				if(mainCode == 0) {
					encryptedKey = xp.getValueOf("KEY");
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[createKey]encryptedKey : " + encryptedKey);
					if("".equals(encryptedKey)) {
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[createKey]Final KeyValue : " + json);
						encryptedKey = Base64.encodeBase64String(AESEncryption.encrypt(json).getBytes());
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[createKey]encryptedKey : " + encryptedKey);
						for (Map.Entry<String , Integer> entry : modeExpiry.entrySet()){
							mode = entry.getKey();
							expiry = entry.getValue();
							DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[createKey]mode : " + mode);
							DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[createKey]expiry : " + expiry);
							outputXml = APCallCreateXML.APInsert("BPM_AUTHENTICATION_TXN_DATA", "WI_NAME,EVENT,KEY,ACTION_DATETIME,EXPIRYTIME",
									"'"+WI_NAME+"','"+mode+"','"+encryptedKey+"',SYSDATE,SYSDATE+("+expiry+"/1440)", sessionId);
							DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[createKey]outputXml : " + outputXml);
						}	
					}
				} else {
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[createKey]return false - maincode is not zero: "+mainCode);
				}
			} else {
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[createKey]return false - issue with outputxml: "+outputXml);
			} 
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Exception : " + e.toString());
		}
		return encryptedKey;
	}

	public static boolean validatekey(String WI_NAME, String key){
		String outputXml = "";
		try {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[validatekey]workitemNumber : " + WI_NAME);
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[validatekey]key : " + key);
			outputXml = APCallCreateXML.APSelect("SELECT KEY FROM BPM_AUTHENTICATION_TXN_DATA WHERE WI_NAME" + " = '"+WI_NAME+"'");
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[validatekey]outputXml : " + outputXml);
			if (outputXml != null && !"".equals(outputXml)) {
				XMLParser xp = new XMLParser(outputXml);
				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[validatekey]mainCode : " + mainCode);
				if(mainCode == 0) {
					String securityToken = xp.getValueOf("KEY");
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[validatekey]securityToken : " + securityToken);
					String SECURITY_TOKEN ="";
					int strLen = securityToken.length();
					int partSize = strLen/2;

					if(strLen % 2 != 0){
						SECURITY_TOKEN =securityToken;
					}
					else{
						SECURITY_TOKEN = securityToken.substring(0, partSize);
					}
					
					if(key.equals(SECURITY_TOKEN)){
						return true;
					}
				}else {
					return false;
				}
			} else{
				return false;
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Exception : " + e.toString());
		} 
		return false;
	}
}
