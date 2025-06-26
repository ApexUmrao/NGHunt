/**
 * BUG-ID		Changed DT		Changed By		Description
 */
package com.newgen.cbg.calls;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import com.newgen.cbg.callhandler.CallEntity;
import com.newgen.cbg.callhandler.CallHandler;
import com.newgen.cbg.core.ICallExecutor;
import com.newgen.cbg.logger.DSCOPDBLogMe;
import com.newgen.cbg.logger.DSCOPLogMe;
import com.newgen.cbg.utils.APCallCreateXML;
import com.newgen.cbg.utils.DSCOPConfigurations;
import com.newgen.cbg.utils.DSCOPUtils;
import com.newgen.cbg.utils.ExecuteXML;
import com.newgen.omni.jts.cmgr.XMLParser;

public class SuppCustomerInformation implements ICallExecutor, Callable<String> {
	private String sessionId;
	private String wiName;
	private String primaryCid;
	private String callStatus;
	private int stageID;
	private int returnCode;
	private String errorDetail;
	private String errorDescription;	
	private String customerId;
	private String txnType="GetCustomerSummary";
	private String status;
	private String reason;
	private String refNo;
	private String senderID;
	private boolean prevStageNoGo;
	private Map<String, String> defaultMap;
	private CallEntity callEntity;
	private String auditCallName = "DSCI";
	private String homeEmirates = "";
	private String customerType = "ETB";
	private String resiCountryDesc = "";
	private String emplPoBox= "";
	private String emplAddLine2= "";
	private String emplAddLine3= "";
	private String homePoBox= "";
	private String homeAddLine2= "";
	private String homeAddLine3= "";
	private String resPoBox= "";
	private String flatNo= "";
	private String buildingName= "";
	private String streetName= "";
	private String residenceArea= "";
	private String emplCity= "";
	private String emplCountryDesc= "";
	private String emplEmiratesCode= "";
	private String resiCountry= "";
	private String emplCountry= "";
	private String homeCountry= "";
	private String homeCity="";
	private String homeCountryDesc="";
	private String resiEmirates="";
	private String cdCity="";
	private String cdState="";
	private String cdCountry=""; 
	private String resiCity="";
	private String cdflat="";
	private String cdstreet="";
	private String homeBranch="";
	String outputXML1="";
	String outputXml;
	private String primaryCardNo="";
	public SuppCustomerInformation(Map<String, String> defaultMap, String sessionId, String stageId,String wiName, Boolean prevStageNoGo, CallEntity callEntity){
		this.wiName=wiName;
		stageID = Integer.parseInt(stageId);
		this.sessionId = sessionId;
		this.prevStageNoGo = prevStageNoGo;
		this.defaultMap = defaultMap;
		this.callEntity = callEntity;
		try {
			primaryCid=defaultMap.get("primaryCid");
			senderID = defaultMap.get("SENDER_ID");
			primaryCardNo=defaultMap.get("primaryCardNo");
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"SuppCustomerInformation customerId: "+primaryCid +" isNTBCust: "+customerType +" senderID: "+senderID+ "primaryCard:"+primaryCardNo);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "DSCI100", e, sessionId);
		}
	}
	
	@Override
	public String call() throws Exception {
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"SuppCustomerInformation call: inside");
		String inputXml = "";
	    outputXml = "<returnCode>0</returnCode>";
		try {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"SuppCustomerInformation call: insideTry");
			if(!primaryCid.isEmpty() && customerType.equalsIgnoreCase("ETB")){
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"SuppCustomerInformation call:" +customerType);
				inputXml = createInputXML();
				if(!prevStageNoGo){
					outputXml = ExecuteXML.executeWebServiceSocket(inputXml);
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SuppCustomerInformation outputXml ===> " + outputXml);
					if(outputXml==null || outputXml.equalsIgnoreCase("")){
						outputXml= "<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>";
					}
					responseHandler(outputXml, inputXml);
				} else {
					callStatus = "N";
					updateCallOutput(inputXml);
				}
			} else {
				callStatus = "X";
				errorDescription = "CustomerID not Generated/NTB Customer - CALL SKIPPED";
				updateCallOutput(inputXml);
			}
			
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "DSCI100", e, sessionId);
		}
		return outputXml;
	}

	@Override
	public String createInputXML() throws Exception {
		StringBuilder inputXml=new StringBuilder();
		try {
			refNo = DSCOPUtils.getInstance().generateSysRefNumber();
			inputXml.append("<?xml version=\"1.0\"?>").append("\n")
			.append("<APWebService_Input>").append("\n")
			.append("<Option>WebService</Option>").append("\n")
			.append("<EngineName>" + DSCOPConfigurations.getInstance().CabinetName + "</EngineName>").append("\n")
			.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
			.append("<Calltype>DSCOP</Calltype>").append("\n")
			.append("<DSCOPCallType>InqCustomerInformation</DSCOPCallType>").append("\n")			
			.append("<WiName>" + wiName + "</WiName>").append("\n")
			.append("<REF_NO>" + refNo + "</REF_NO>").append("\n")
			.append("<OLDREF_NO>" + refNo + "</OLDREF_NO>").append("\n")
			.append("<senderId>" + senderID + "</senderId>").append("\n")
			.append("<CUST_ID>" + primaryCid + "</CUST_ID>").append("\n")			
			.append("<txnType>" + txnType + "</txnType>").append("\n")
			.append("</APWebService_Input>");
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, inputXml.toString());
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "DSCI100", e, sessionId);
		}
		return inputXml.toString();
	}

	@Override
	public String executeCall(HashMap<String, String> input) throws Exception {
		return call();
	}

	@Override
	public void executeDependencyCall() throws Exception {
		try {
			String outputXMLTemp = "";
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "DSCI004", "AS DependencyCall:"+ callEntity.getDependencyCallID(), sessionId);
			outputXMLTemp = CallHandler.getInstance().executeDependencyCall(callEntity, defaultMap, sessionId, wiName, prevStageNoGo);
			if(!outputXMLTemp.isEmpty()) {
				outputXml=outputXMLTemp;
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "DSCI100", e, sessionId);
		}
		
	}

	@Override
	public void responseHandler(String outputXML, String inputXml) throws Exception {
		String addressType = "";
		String emplEmirates= "";
		try {
			XMLParser xp = new XMLParser(outputXML);
			returnCode = Integer.parseInt(xp.getValueOf("returnCode", "1", true));
			errorDescription = xp.getValueOf("errorDescription", "", true);
			errorDetail = xp.getValueOf("errorDetail", "", true);
			status = xp.getValueOf("Status", "", true);
			reason = xp.getValueOf("Reason", "", true);
			if(returnCode == 0 || returnCode == 2){
				callStatus = "Y";
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "DSCI090", "DSCI Successfully Executed", sessionId);			
				int start = xp.getStartIndex("Addresses", 0, 0);
				int deadEnd = xp.getEndIndex("Addresses", start, 0); 
				int noOfFields = xp.getNoOfFields("Address", start,deadEnd);
				int end = 0;
				homeBranch=xp.getValueOf("domicileBranchCode");
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SuppCustomerInformation addressType "+homeBranch+" ===> "+homeBranch);
				for(int i = 0; i < noOfFields; ++i){
					start = xp.getStartIndex("Address", end, 0);
					end = xp.getEndIndex("Address", start, 0);
					addressType = xp.getValueOf("Type", start, end);
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SuppCustomerInformation addressType "+i+" ===> " + addressType);
					if(addressType.equalsIgnoreCase("Office Address")){
						emplCity = xp.getValueOf("city", start, end);
						emplEmirates = xp.getValueOf("state", start, end);
						emplCountryDesc = xp.getValueOf("country", start, end);
						emplPoBox = xp.getValueOf("POBox", start, end);
						emplPoBox = emplPoBox.replaceAll("\\D", "");
						emplAddLine2 = xp.getValueOf("addressLine_1", start, end);
						emplAddLine3 = xp.getValueOf("addressLine_2", start, end);

						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SuppCustomerInformation emplCity=" + emplCity +"|emplEmirates="+emplEmirates
								+"|emplCountryDesc="+emplCountryDesc+"|emplPoBox="+emplPoBox+"|emplAddLine2="+emplAddLine2+"|emplAddLine3="+emplAddLine3 );
						String outputXML2 = APCallCreateXML.APSelect("SELECT EMIRATE_CODE FROM USR_0_STATE_EMIRATE_MASTER "
								+ "WHERE UPPER(STATE) = UPPER('"+emplEmirates+"')");
						XMLParser xp2 = new XMLParser(outputXML2);
						int mainCode2 = Integer.parseInt(xp2.getValueOf("MainCode"));
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"SuppCustomerInformation emplEmirates DESC --> CODE mainCode2: "+mainCode2);
						if(mainCode2 == 0){
							DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"SuppCustomerInformation emplEmirates DESC --> CODE TotalRetrieved: "+Integer.parseInt(xp2.getValueOf("TotalRetrieved")));
							if(Integer.parseInt(xp2.getValueOf("TotalRetrieved")) > 0){
								emplEmiratesCode = xp2.getValueOf("EMIRATE_CODE");
								DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "DSCI002", customerId, sessionId);
							}
							DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"SuppCustomerInformation emplEmiratesCode: "+emplEmiratesCode);
						}
					} else if(addressType.equalsIgnoreCase("Permanent Address")){
						homeCity = xp.getValueOf("city", start, end);
						homeEmirates = xp.getValueOf("state", start, end);
						homeCountryDesc = xp.getValueOf("country", start, end);
						homePoBox = xp.getValueOf("POBox", start, end);
						homePoBox = homePoBox.replaceAll("\\D", "");
						homeAddLine2 = xp.getValueOf("addressLine_1", start, end);
						homeAddLine3 = xp.getValueOf("addressLine_2", start, end);

						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SuppCustomerInformation homeCity=" + homeCity +"|homeEmirates="+homeEmirates
								+"|homeCountryDesc="+homeCountryDesc+"|homePoBox="+homePoBox+"|homeAddLine2="+homeAddLine2+"|homeAddLine3="+homeAddLine3 );
					}  else if(addressType.equalsIgnoreCase("Residence Address")){
						resiCity = xp.getValueOf("city", start, end).replace("null", "");
						resiEmirates = xp.getValueOf("state", start, end).replace("null", "");
						resiCountryDesc = xp.getValueOf("country", start, end).replace("null", "");
						//COP-2922
						flatNo = xp.getValueOf("flatNo", start, end).replace("null", "");
						buildingName = xp.getValueOf("buildingName", start, end).replace("null", "");
						streetName = xp.getValueOf("streetName", start, end).replace("null", "");
						residenceArea = xp.getValueOf("ResidenceArea", start, end).replace("null", "");
						resPoBox = xp.getValueOf("POBox", start, end);
						resPoBox = resPoBox.replaceAll("\\D", ""); // Fetching only numeric value

						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SuppCustomerInformation resiCity=" + resiCity +"|resiEmirates="+resiEmirates
								+"|resiCountryDesc="+resiCountryDesc+"|flatNo="+flatNo+"|buildingName="+buildingName+"|streetName="+streetName+"|residenceArea="+residenceArea+"|resPoBox="+resPoBox );
					}
					else if(addressType.equalsIgnoreCase("Correspondence Address")){
						cdCity = xp.getValueOf("city", start, end).replace("null", "");
						cdState = xp.getValueOf("state", start, end).replace("null", "");
						cdCountry = xp.getValueOf("country", start, end).replace("null", "");
						cdflat = xp.getValueOf("addressLine_2", start, end).replace("null", "");
						cdstreet = xp.getValueOf("addressLine_1", start, end).replace("null", "");
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "COPCustomerInformation Correspondence Address=" + cdCity +"|cdCountry="+cdCountry
								+"|resiCountryDesc="+resiCountryDesc+"|cdflat="+cdflat+"|cdstreet="+cdstreet);

					}

				}
				/*DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "COPCustomerInformation Creditcard krishna1 ");
				int start1 = xp.getStartIndex("CreditCards", 0, 0);
				int deadEnd1 = xp.getEndIndex("CreditCards", start1, 0); 
				int noOfFields1 = xp.getNoOfFields("Creditcard", start1,deadEnd1);
				int end1 = 0;
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "COPCustomerInformation Creditcard krishna "+noOfFields1);
				for(int i = 0; i < noOfFields1; ++i){
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "COPCustomerInformation Creditcard "+noOfFields1);
					start1 = xp.getStartIndex("Creditcard", end1, 0);
					end1 = xp.getEndIndex("Creditcard", start1, 0);
					String cardnumber = xp.getValueOf("CardNumber", start1, end1);
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "COPCustomerInformation Creditcard "+cardnumber);
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "COPCustomerInformation primaryCard "+primaryCardNo);
					if(cardnumber.equalsIgnoreCase(primaryCardNo)){
					 String statusPrimaryCard = xp.getValueOf("status", start1, end1).replace("null", "");
					 APCallCreateXML.APUpdate("EXT_DSCOP_EXTENDED","STATUS_PRIMARY_CARD", "'"+statusPrimaryCard+"'", " WI_NAME = '"+wiName+"'", sessionId);
					 }	
				}*/
				String outputXML2 = APCallCreateXML.APSelect("SELECT (SELECT COUNTRY_CODE FROM USR_0_COUNTRY_MAST WHERE COUNTRY = UPPER('"+resiCountryDesc+"')) AS RESI_ADD_COUNTRY, "
						+ "(SELECT COUNTRY_CODE FROM USR_0_COUNTRY_MAST WHERE COUNTRY = UPPER('"+emplCountryDesc+"')) AS EMPLYR_ADD_COUNTRY, "
						+ "(SELECT COUNTRY_CODE FROM USR_0_COUNTRY_MAST WHERE COUNTRY = UPPER('"+homeCountryDesc+"')) AS PERM_ADD_COUNTRY FROM DUAL");
				XMLParser xp2 = new XMLParser(outputXML2);
				int mainCode2 = Integer.parseInt(xp2.getValueOf("MainCode"));
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"COPCustomerInformation COUNTRY_DESC --> COUNTRY_CODE mainCode2: "+mainCode2);
				if(mainCode2 == 0){
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"COPCustomerInformation COUNTRY_DESC --> COUNTRY_CODE TotalRetrieved: "+Integer.parseInt(xp2.getValueOf("TotalRetrieved")));
					if(Integer.parseInt(xp2.getValueOf("TotalRetrieved")) > 0){
						resiCountry = xp2.getValueOf("RESI_ADD_COUNTRY");		
						emplCountry = xp2.getValueOf("EMPLYR_ADD_COUNTRY");
						homeCountry = xp2.getValueOf("PERM_ADD_COUNTRY");
						DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "DSCI002", customerId, sessionId);
					}
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"COPCustomerInformation resiCountry: "+resiCountry +
							" emplCountry: "+emplCountry +" homeCountry: "+homeCountry);
				}
			} else {
				callStatus = "N";
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "DSCI090", "COPCustomerInformation fail  ", sessionId);
			}		 
			updateCallOutput(inputXml);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "DSCI100", e, sessionId);
		}		
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		try {
			String valList = "'"+ wiName +"',"+ stageID +", 'SuppCustomerInformation', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"'";
			APCallCreateXML.APInsert("USR_0_DSCOP_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
					+ "ERROR_DETAIL, STATUS, REASON", valList, sessionId);

			if(callStatus.equals("Y")){
				String columnList = "RESI_ADD_COUNTRY_DESC, RESI_ADD_COUNTRY, RESI_ADD_CITY, RESI_ADD_STATE_EMIRATE, RESI_ADD_ZIP, RESI_ADD_POBOX_VILA_FLATNO, RESI_ADD_FLOOR_BUILDINGNAME, RESI_ADD_STREET_NAME, RESI_ADD_LANDMARK,"
						+ " EMPLYR_ADD_CITY, EMPLYR_ADD_STATE_EMIRATE, EMPLYR_ADD_COUNTRY, EMPLYR_ADD_COUNTRY_DESC, EMPLYR_ADD_ZIP, EMPLYR_ADD_ADDRESSLINE2, EMPLYR_ADD_ADDRESSLINE3,"
						+ " PERM_ADD_COUNTRY_DESC, PERM_ADD_COUNTRY, PERM_ADD_CITY, PERM_ADD_STATE_EMIRATE, PERM_ADD_ZIP, PERM_ADD_ADDRESSLINE2, PERM_ADD_ADDRESSLINE3, CORRES_ADD_CITY, CORRES_ADD_COUNTRY, CORRES_ADD_POBOX_VILA_FLATNO, CORRES_ADD_STREET_NAME,"
						+ " CORRES_ADD_STATE_EMIRATE,HOME_BRANCH";
				
				String valList4 = "'"+resiCountryDesc+"','"+resiCountry+"','"+resiCity+"','"+resiEmirates+"','"+resPoBox+"','"+flatNo+"','"+buildingName+"','"+streetName+"','"+residenceArea+"',"
						+ "'"+emplCity+"','"+emplEmiratesCode+"','"+emplCountry+"','"+emplCountryDesc+"','"+emplPoBox+"','"+emplAddLine2+"','"+emplAddLine3+"',"
						+ "'"+homeCountryDesc+"','"+homeCountry+"','"+homeCity+"','"+homeEmirates+"','"+homePoBox+"','"+homeAddLine2+"','"+homeAddLine3+"','"+cdCity+"','"+cdCountry+"','"+cdflat+"','"+cdstreet+"','"+cdState+"','"+homeBranch+"'";
			
				APCallCreateXML.APUpdate("EXT_DSCOP", columnList, valList4, " WI_NAME = '"+wiName+"'", sessionId);


				String valList2 = "'"+ wiName +"',"+ stageID +", 'SuppCustomerInformation', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml +"', 0";
				APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON,"
						+ " RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);
				executeDependencyCall();
			}
			else {
				String valList2 = "'"+ wiName +"',"+ stageID +", 'SuppCustomerInformation', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml +"', 1";
				APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON, "
						+" RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "COPCI100", e, sessionId);
		}
		
	} 
}
