package com.newgen.iforms.user.raroc; 
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import org.apache.log4j.Logger;
import org.json.*;

import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.user.config.Constants;
import com.newgen.iforms.user.config.RarocLogger;

public class setT24CallData extends Common implements Constants {
	
	public static final Logger log = RarocLogger.getInstance().getLogger();
	private static final String RAROC_GROUP_REALIZED_COLUMN = null;
	protected static IFormReference formObject;
	//suraj
	public static String setRarocdetails(IFormReference iformreference, String wiName, String outputXml,
			String customerID,String inputXML, String ReqDataTime)throws Exception {
		String callType ="customerRealised";
		
		customXMLParser parser = new customXMLParser(outputXml);
		String statusCode = parser.getValueOf("STATUS_CODE");
		String status = parser.getValueOf("STATUS_DESC");
		String res = parser.getValueOf("rarocDetailsResponse");
		String str_res_bdcr="";//checking 20-09-2024
		String customerRarocPercent="";
		BigDecimal res_bdcr= new BigDecimal("0.0");
	    log.info("res>>>>"+res);
	    JSONArray rarocDetailsResponseArray =null;
	    try {
 		 rarocDetailsResponseArray = new JSONArray(res);
	    }catch (Exception e) {
			log.info("Exception rarocDetailsResponseArray.....: " + e);
		}
 		try {
 		Common Common=new Common(iformreference);
 		Common.insertIntoRarocIntegrationLogs(iformreference,callType, inputXML, outputXml, ReqDataTime, status);//prod_04112024
 		} catch (Exception e) {
			log.info("Exception insertIntoRarocIntegrationLogs: " + e);
		}
	if(status.equalsIgnoreCase("Success")){
		String insertQuery =  "UPDATE ALM_RAROC_EXT_TABLE SET CUSTOMER_REALISED_CALL ='Y' WHERE WI_NAME ='"+wiName+"'";
			log.info("Inside insertQuery >"+insertQuery);
			try {
				iformreference.saveDataInDB(insertQuery);
				log.info("maincode>>>>>>>>>"+iformreference.saveDataInDB(insertQuery));
			} catch (Exception e) {
				log.error(e.toString());
			}
		log.info(" T24CustID : "+ customerID);
		int rarocSize = rarocDetailsResponseArray.length();
		//start edit by mohit 30-06-2024 change status(Success to Partial Success) if rarocSize is zero
		log.info("setRarocfacilitydetails  rarocSize > "+rarocSize);
		if(rarocSize < 1) {
			//isRarocSizeZero = true;
			status = "Partial Success";
		}else {
		//end edit by mohit 30-06-2024
			for(int i = 0 ; i<rarocSize; i++){
				int sOutputUpdate = 0;
				JSONObject tempObj = (JSONObject) rarocDetailsResponseArray.get(i);
				log.info("tempObj is "+tempObj.toString() + "Initial sOutputUpdate : "+ sOutputUpdate);
				String businessSeg = getValue(tempObj, "customerSegment");
				String industrySegCust = getValue(tempObj, "customerIndustry");
				String counterpartyType = getValue(tempObj, "assetClass");
			//	String incomeIntFee = getValue(tempObj, "interestIncomeAed"); //Commented as karan said
				String comitmentFee = getValue(tempObj, "feeIncomeAed");
				String Customer_Name = getValue(tempObj, "customerName");
				String Risk_Country_Name = getValue(tempObj, "customerCountry");
				
				String customerInternalRating = getValue(tempObj, "customerInternalRating");
				String Total_facility_amount_Funded = getValue(tempObj, "totalFundedAed");
				String Total_facility_non_funded = getValue(tempObj, "totalNonFundedAed");
				String crossSellIncome = getValue(tempObj, "crossSellIncome");
				String customerRealizedRaroc = getValue(tempObj, "realizedRaroc");
				log.info("1111 mohit20 ...customerRealizedRaroc"+customerRealizedRaroc);
				String customerRaroc = "";
				if(customerRealizedRaroc!= null && customerRealizedRaroc != "" ) {
					 customerRarocPercent = (Double.parseDouble(customerRealizedRaroc)*100)+"";
					log.info("1111 mohit20 ...customerRaroc string "+customerRarocPercent);
					customerRaroc = customerRarocPercent.toString();
					log.info("111 mohit20 ...customerRaroc"+customerRaroc);
					
					//checking mohit 20-09-2024
					BigDecimal bdcr = new BigDecimal(customerRaroc);
					log.info("bdcr..."+bdcr);//2.166289E+7
					 res_bdcr = bdcr.divide(new BigDecimal(1), 2, RoundingMode.CEILING);
					log.info("res_bdcr.toPlainString().."+res_bdcr.toPlainString());//21662890.00
					double doub_res_bdcr = res_bdcr.doubleValue();
					
					 str_res_bdcr =Double.toString(doub_res_bdcr);
					log.info("str_res_bdcr.."+str_res_bdcr);//2.166289E7
					//end checking
				}
				
				String feeIncomeAed = getValue(tempObj, "feeIncomeAed");
				String rimNumber = getValue(tempObj, "customerId");
	
	//			String sWhere =  " WHERE RIM_NO ='"+rimNumber+"'"; 
	//
	//			String sValues = "'" + customerInternalRating + "'" + (char) 25 + "'" + Total_facility_amount_Funded + "'" + (char) 25 + "'"
	//					+ Total_facility_non_funded + "'" + (char) 25 + "'" + feeIncomeAed + "'" + (char) 25 + "'"
	//					+ feeIncomeAed + "'" + (char) 25 + "'" + crossSellIncome + "'" + (char) 25 + "'"
	//					+ customerRaroc + "'" ;
	//
	//			sOutputUpdate = updateDataInDB(RAROC_GROUP_DETAILS_TABLE, RAROC_GROUP_REALIZED_COLUMN,sValues,sWhere);
	//			log.info("sOutputUpdate : " + sOutputUpdate);
				log.info("before update query...");
				log.info("customerInternalRating="+customerInternalRating+".");
				log.info("Total_facility_amount_Funded="+Total_facility_amount_Funded+".");
				log.info("Total_facility_non_funded="+Total_facility_non_funded+".");
				log.info("feeIncomeAed="+feeIncomeAed+".");
				log.info("feeIncomeAed="+feeIncomeAed+".");
				log.info("crossSellIncome="+crossSellIncome+".");
				log.info("customerRaroc="+customerRaroc+".");
				log.info("rimNumber="+rimNumber+".");
				log.info("WI_NAME="+wiName+".");
				String updateQuery ="UPDATE NG_RAROC_GROUP_DETAILS SET BORR_RAT ='"+customerInternalRating+"',"
				           + "FUNDED_AMOUNT ='"+Total_facility_amount_Funded+"',NON_FUNDED_AMOUNT ='"+Total_facility_non_funded+"',"
						   + "COMMT_FEES ='"+feeIncomeAed+"',UPFR_FEES ='"+feeIncomeAed+"',CROS_SEL_INCOME ='"+crossSellIncome+"',"
						   + "CUSTOMER_LVL_RAROC ='"+res_bdcr.toPlainString()+"' WHERE RIM_NO ='"+rimNumber+"'"
						   + " AND WI_NAME = '"+wiName+"'";
	
				log.info("Update query is : "+updateQuery);
				sOutputUpdate = iformreference.saveDataInDB(updateQuery);
				log.info("Update query result is "+sOutputUpdate);
				
				if(customerID.equals(rimNumber)) {
					log.info("Inside Customer Data Set");
					iformreference.setValue("Q_NG_RAROC_CUSTOMER_Customer_RAROC_Realized",res_bdcr.toPlainString());
	// tanu 21-05				iformreference.setValue("Q_NG_RAROC_CUSTOMER_Customer_RAROC_Proposed",customerRaroc);
					iformreference.setValue("Q_NG_RAROC_CUSTOMER_Business_segment_Realized",businessSeg);
					iformreference.setValue("Q_NG_RAROC_CUSTOMER_Business_segment_Proposed",businessSeg);
					iformreference.setValue("Q_NG_RAROC_CUSTOMER_Industry_Segement_Realized",industrySegCust);
					iformreference.setValue("Q_NG_RAROC_CUSTOMER_Industry_Segement_Proposed",industrySegCust);
					iformreference.setValue("Q_NG_RAROC_CUSTOMER_Counterparty_Type_Realized",counterpartyType);
					iformreference.setValue("Q_NG_RAROC_CUSTOMER_Counterparty_Type_Proposed",counterpartyType);
		//			iformreference.setValue("Q_NG_RAROC_CUSTOMER_Income_CASA_Realized",incomeIntFee); //Commented as karan said
		//			iformreference.setValue("Q_NG_RAROC_CUSTOMER_Income_CASA_Proposed",incomeIntFee);
					iformreference.setValue("Q_NG_RAROC_CUSTOMER_cross_sell_incm_Realized",crossSellIncome);
			//		iformreference.setValue("Q_NG_RAROC_CUSTOMER_cross_sell_incm_Proposed",crossSellIncome);
					iformreference.setValue("Q_NG_RAROC_CUSTOMER_fee_incm_aed_Realized",comitmentFee);
					iformreference.setValue("Q_NG_RAROC_CUSTOMER_fee_incm_aed_Proposed",comitmentFee);
					iformreference.setValue("Q_NG_RAROC_CUSTOMER_customer_name_Realized",Customer_Name);
					iformreference.setValue("Q_NG_RAROC_CUSTOMER_customer_name_Proposed",Customer_Name);
					iformreference.setValue("Q_NG_RAROC_CUSTOMER_total_funded_aed_Realized",Total_facility_amount_Funded);
		//			iformreference.setValue("Q_NG_RAROC_CUSTOMER_total_funded_aed_Proposed",Total_facility_amount_Funded);
					iformreference.setValue("Q_NG_RAROC_CUSTOMER_total_nonfunded_aed_Realized",Total_facility_non_funded);
		//			iformreference.setValue("Q_NG_RAROC_CUSTOMER_total_nonfunded_aed_Proposed",Total_facility_non_funded);
					iformreference.setValue("Q_NG_RAROC_CUSTOMER_customer_country_Realized",Risk_Country_Name);
					iformreference.setValue("Q_NG_RAROC_CUSTOMER_customer_country_Proposed",Risk_Country_Name);
					iformreference.setValue("Q_NG_RAROC_CUSTOMER_cust_intrl_rating_Realized",customerInternalRating);
					iformreference.setValue("Q_NG_RAROC_CUSTOMER_cust_intrl_rating_Proposed",customerInternalRating);
	
				} if (sOutputUpdate == 0) {
					insertQuery = "INSERT INTO NG_RAROC_GROUP_DETAILS (WI_NAME, CUST_NAME, RIM_NO, BORR_RAT, FUNDED_AMOUNT,"
								+ " NON_FUNDED_AMOUNT, COMMT_FEES, UPFR_FEES, CROS_SEL_INCOME, CUSTOMER_LVL_RAROC) VALUES('"
								+ wiName+"','"+Customer_Name+"','"+rimNumber+"','"+customerInternalRating+"','"	+ Total_facility_amount_Funded+"','"
								+ Total_facility_non_funded+"','"+feeIncomeAed+"','"+feeIncomeAed+"' ,'"+crossSellIncome+"' ,'"+customerRaroc+"')";
					
					log.info("insert query is : "+insertQuery);
					int outputInsert = iformreference.saveDataInDB(insertQuery);
					log.info("insert query result is "+outputInsert);
				}
			}
		}
	}
	/*Common Common=new Common(iformreference);
	Common.insertIntoRarocIntegrationLogs(iformreference,callType, inputXML, outputXml, ReqDataTime, status);*///prod_04112024
    return status;
	}
	
	//Suraj
	public static String setRarocGroupdetails(IFormReference iformreference, String wiName, String outputXml,String inputXML, String ReqDataTime) throws Exception {
		String callType ="groupRealised";
		
		customXMLParser parser = new customXMLParser(outputXml);
		String statusCode = parser.getValueOf("STATUS_CODE");
		String status = parser.getValueOf("STATUS_DESC");
		String res = parser.getValueOf("rarocDetailsResponse");
		try {
			Common Common=new Common(iformreference);
			Common.insertIntoRarocIntegrationLogs(iformreference,callType, inputXML, outputXml, ReqDataTime, status);//prod_04112024
			}catch (Exception e) {
				log.info("Exception insertIntoRarocIntegrationLogs: " + e);
			}
		    log.info("res>>>>"+res);
	 		JSONArray rarocDetailsResponseArray = new JSONArray(res);
	 	
		if(status.equalsIgnoreCase("Success")){
			String insertQuery =  "UPDATE ALM_RAROC_EXT_TABLE SET GROUP_REALISED_CALL ='Y' WHERE WI_NAME ='"+wiName+"'";
				log.info("Inside insertQuery >"+insertQuery);
				try {
					iformreference.saveDataInDB(insertQuery);
					log.info("maincode>>>>>>>>>"+iformreference.saveDataInDB(insertQuery));
				} catch (Exception e) {
					// TODO: handle exception
					log.info(e.toString());
				}
			int rarocSize = rarocDetailsResponseArray.length();
			//start edit by mohit 30-06-2024 change status(Success to Partial Success) if rarocSize is zero
			log.info("setRarocfacilitydetails  rarocSize > "+rarocSize);
			if(rarocSize < 1) {
				//isRarocSizeZero = true;
				status = "Partial Success";
			}else {
			//end edit by mohit 30-06-2024
				for(int i = 0 ; i<rarocSize; i++){
					JSONObject tempObj = (JSONObject) rarocDetailsResponseArray.get(i);
					log.info("tempObj is "+tempObj);
					String realizedRarocPercentage = getValue(tempObj, "realizedRaroc");
					
					String realizedRaroc = "";
					if(realizedRarocPercentage!= null && realizedRarocPercentage != "" ) {
						Float groupRarocPercent = (Float.parseFloat(realizedRarocPercentage)*100);
						realizedRaroc = groupRarocPercent.toString();
					}
					log.info("realizedRaroc is "+realizedRaroc);
					iformreference.setValue("Q_NG_RAROC_GROUP_Group_Realized",realizedRaroc);
					
				}
			}
			//status = Initiator.execCustomerProjected(iformreference);
		}else{
		}
		/*Common Common=new Common(iformreference);
		Common.insertIntoRarocIntegrationLogs(iformreference,callType, inputXML, outputXml, ReqDataTime, status);*///prod_04112024
        return status;
		}
	//suraj
public static String setRarocfacilitydetails(IFormReference iformreference, String wiName, String outputXml, String inputXML, String ReqDataTime) throws Exception {
	    String callType ="facilityRealised";
		customXMLParser parser = new customXMLParser(outputXml);
		String statusCode = parser.getValueOf("STATUS_CODE");
		String status = parser.getValueOf("STATUS_DESC");
		String res = parser.getValueOf("rarocDetailsResponse");

		    log.info("res>>>>"+res);
		    JSONArray rarocDetailsResponseArray=null;
		    try {
	 		 rarocDetailsResponseArray = new JSONArray(res);
		    }catch (Exception e) {
				log.info("Exception rarocDetailsResponseArray!!!!!: " + e);
			}
	 		try {
	 		Common Common=new Common(iformreference);
			Common.insertIntoRarocIntegrationLogs(iformreference,callType, inputXML, outputXml, ReqDataTime, status);//prod_04112024
	 		}catch (Exception e) {
				log.info("Exception insertIntoRarocIntegrationLogs: " + e);
			}
		if(status.equalsIgnoreCase("Success")){
			String Query =  "UPDATE ALM_RAROC_EXT_TABLE SET FACILITY_REALISED_CALL ='Y' WHERE WI_NAME ='"+wiName+"'";
				log.info("Inside insertQuery >"+Query);
				try {
					iformreference.saveDataInDB(Query);
					log.info("maincode>>>>>>>>>"+iformreference.saveDataInDB(Query));
				} catch (Exception e) {
					// TODO: handle exception
					log.info(e.toString());
				}

			int rarocSize = rarocDetailsResponseArray.length();
			//start edit by mohit 30-06-2024 change status(Success to Partial Success) if rarocSize is zero
			log.info("setRarocfacilitydetails  rarocSize > "+rarocSize);
			if(rarocSize < 1) {
				//isRarocSizeZero = true;
				status = "Partial Success";
			}else {
			//end edit by mohit 30-06-2024
				for(int i = 0 ; i<rarocSize; i++){
					JSONObject tempObj = (JSONObject) rarocDetailsResponseArray.get(i);
					log.info("tempObj is "+tempObj);
					String facilityId = getValue(tempObj, "facilityId");//IN newgen Commitment number
					String facilityCurrency = getValue(tempObj, "facilityCurrency");
					String facilityType = getValue(tempObj, "facilityType");
					log.info("industrySeg is "+facilityCurrency);
					String feeIncome = getValue(tempObj, "feeIncome");
					String ecl = getValue(tempObj, "ecl");
					log.info("businessSeg is "+ecl);
					String totalCapital = getValue(tempObj, "totalCapital");
					String riskAdjustedReturn = getValue(tempObj, "riskAdjustedReturn");
					String FacilityrealizedRarocPercentage = getValue(tempObj, "realizedRaroc");
					String realizedRaroc = "";
					if(FacilityrealizedRarocPercentage!= null && FacilityrealizedRarocPercentage != "" ) {
						Float FacilityRarocPercent = (Float.parseFloat(FacilityrealizedRarocPercentage)*100);
						realizedRaroc = FacilityRarocPercent.toString();
					}
					String disbursedAmount = getValue(tempObj, "disbursedAmount");
					
					String product = getValue(tempObj, "product");
					String facilityTenor = getValue(tempObj, "facilityTenor");
					String facilityLimit = getValue(tempObj, "facilityLimit");
					String facilityFtpRate = getValue(tempObj, "facilityFtpRate");
					String facilityInterestRate = getValue(tempObj, "facilityInterestRate");
	
	
					String insertQuery ="UPDATE NG_RAROC_FACILITY_DETAILS SET Currency_Realized ='"+facilityCurrency+"',"
							           + "FACILITY_TYPE ='"+facilityType+"', Currency_Proposed ='"+facilityCurrency+"',COMMITMENT_FEE_REALISED ='"+feeIncome+"',  "
							           + "ECL_REALISED ='"+ecl+"',"
									   + "ECL_PROPOSED ='"+ecl+"',RISK_ADJUSTED_CAPITAL_REALISED ='"+totalCapital+"',RISK_ADJUSTED_CAPITAL_PROPOSED ='"+totalCapital+"',"
									   + "RISK_ADJUSTED_RETURN_REALISED ='"+riskAdjustedReturn+"',RISK_ADJUSTED_RETURN_PROPOSED ='"+riskAdjustedReturn+"',"
									   + "FACILITY_RAROC_REALISED ='"+realizedRaroc+"',"
									   + "AVERAGE_UTILISED_REALISED ='"+disbursedAmount+"',AVERAGE_UTILISED_PROPOSED ='"+disbursedAmount+"',"
									   + "REMAINING_TERMS_IN_MONTHS_REALISED ='"+facilityTenor+"',REMAINING_TERMS_IN_MONTHS_PROPOSED ='"+facilityTenor+"',"
									   + "Facility_Amount_Realized ='"+facilityLimit+"',Facility_Amount_Proposed ='"+facilityLimit+"',"
									   + "FTP_RATE_REALISED ='"+facilityFtpRate+"',FTP_RATE_PROPOSED ='"+facilityFtpRate+"',"
									   + "INTEREST_RATE_APPLIED_REALISED ='"+facilityInterestRate+"',INTEREST_RATE_APPLIED_PROPOSED ='"+facilityInterestRate+"' WHERE ( COMMITMENT_NO ='"+facilityId+"' or facility_id='"+facilityId+"') and wi_name='"+wiName+"'";
					iformreference.saveDataInDB(insertQuery);
					log.info("insertQuery is "+insertQuery);
					log.info("query result is "+iformreference.saveDataInDB(insertQuery));
	 
				 /* iformreference.setValue("Q_NG_RAROC_FACILITY_TAB_Currncy_Realized",facilityCurrency);
					iformreference.setValue("Q_NG_RAROC_FACILITY_TAB_Currncy_Proposed",facilityCurrency);
					iformreference.setValue("Q_NG_RAROC_FACILITY_TAB_ECL_Realized",ecl);
					iformreference.setValue("Q_NG_RAROC_FACILITY_TAB_ECL_Proposed",ecl);
					iformreference.setValue("Q_NG_RAROC_FACILITY_TAB_Risk_Adjst_CAP_Realized",totalCapital);
					iformreference.setValue("Q_NG_RAROC_FACILITY_TAB_Risk_Adjst_CAP_Proposed",totalCapital);
					iformreference.setValue("Q_NG_RAROC_FACILITY_TAB_Risk_Adjst_RET_Realized",riskAdjustedReturn);
					iformreference.setValue("Q_NG_RAROC_FACILITY_TAB_Risk_Adjst_RET_Proposed",riskAdjustedReturn);
					iformreference.setValue("Q_NG_RAROC_FACILITY_TAB_Facility_RAROC_Realized",realizedRaroc);
					iformreference.setValue("Q_NG_RAROC_FACILITY_TAB_Facility_RAROC_Proposed",realizedRaroc);
					iformreference.setValue("Q_NG_RAROC_FACILITY_TAB_Avg_Utilized_Realized",disbursedAmount);
					iformreference.setValue("Q_NG_RAROC_FACILITY_TAB_Avg_Utilized_Proposed",disbursedAmount);
					//iformreference.setValue("Q_NG_RAROC_FACILITY_TAB_Rem_Term_Month_Realized", "");
					//iformreference.setValue("Q_NG_RAROC_FACILITY_TAB_Rem_Term_Month_Proposed","");
					 														
					 */
				}
			}
		}else{
		}
		/*Common Common=new Common(iformreference);
		Common.insertIntoRarocIntegrationLogs(iformreference,callType, inputXML, outputXml, ReqDataTime, status);*///prod_04112024
		
         return status;
		}

	//start edit by mohit 26-07-2024

public static String setCustomerProjectedDataSen(IFormReference iformreference, String wiName, String outputXml, String inputXML, String ReqDataTime) throws Exception {
    String callType ="projectedRaroc";
    String status ="";
	log.info("inside setCustomerProjectedDatasen>>>>>>");
	
	//customXMLParser parser = new customXMLParser(outputXml);
	//String res = parser.getValueOf("projectedRarocResponse");
	try {//mohit 03-07-2024 for converting XML to Json and passing status desc as Success/Exception/Failue
	//JSONObject req = new JSONObject(outputXml);
		
		 /*JSONObject req = XML.toJSONObject(outputXml);	  
		log.info("JsonObject req..." + req);
		
		JSONObject exceptionObject = getJSONObject(req, "exceptionDetails");
		log.info("exceptionObject>>>>>>" + exceptionObject);
		 status = getValue(exceptionObject,"successIndicator");
		 log.info("status00000...." + status);
		 
		JSONObject reqObject = getJSONObject(req, "CBS_RESPONSE");
		log.info("reqObject>>>>>>" + reqObject);*///R_0907-2024
		//status = getValue(reqObject, "STATUS_DESC");
		status = setCustomerProjectedData1(outputXml);
		log.info("status...." + status);
		try {
		Common Common=new Common(iformreference);
		Common.insertIntoRarocIntegrationLogs(iformreference,callType, inputXML, outputXml, ReqDataTime, status);//prod_04112024
	}catch (Exception e) {
			log.info("Exception insertIntoRarocIntegrationLogs: " + e);
		}
	if(status.equalsIgnoreCase("Success")){
	String Query =   "UPDATE ALM_RAROC_EXT_TABLE SET PROJECTED_FACILITY_CALL ='Y' WHERE WI_NAME ='"+wiName+"'";
		log.info("Inside insertQuery...>"+Query);
		try {
			iformreference.saveDataInDB(Query);
			log.info("maincode>>>>>>>>>"+iformreference.saveDataInDB(Query));
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e.toString());
		}

	/*===================================*/
	org.json.simple.parser.JSONParser parser=new org.json.simple.parser.JSONParser();
	Object obj=parser.parse(outputXml);
	org.json.simple.JSONObject json= (org.json.simple.JSONObject)obj;
	org.json.simple.JSONObject body = (org.json.simple.JSONObject)json.get("body");
	log.info("body>>>>>>> "+body);
	org.json.simple.JSONObject projectedRarocDetailsResponse = (org.json.simple.JSONObject)body.get("projectedRarocDetailsResponse");
	String groupProjetedRarocPercentage = projectedRarocDetailsResponse.get("groupProjectedRaroc").toString();
	String groupProjetedRaroc = "";
	if(groupProjetedRarocPercentage!= null && groupProjetedRarocPercentage != "" ) {
		Float groupRarocPercent = (Float.parseFloat(groupProjetedRarocPercentage)*100);
		groupProjetedRaroc = groupRarocPercent.toString();
	}
	iformreference.setValue("Q_NG_RAROC_GROUP_Group_Sensitized",groupProjetedRaroc+"%");
	iformreference.setValue("Q_NG_RAROC_GROUP_Approved",groupProjetedRaroc+"%");
	org.json.simple.JSONObject customer = (org.json.simple.JSONObject)projectedRarocDetailsResponse.get("customer");
	log.info("customer tempObj is "+customer);
	String customerName = customer.get("customerName").toString().replace("\"", "").replace("[", "").replace("]", "");
	String customerCode = customer.get( "customerCode").toString().replace("\"", "").replace("[", "").replace("]", "");
	log.info("customer tempObj is "+customer.get( "customerCode").toString().replace("\"", "").replace("[", "").replace("]", ""));
	String crossSellIncome = customer.get( "crossSellIncome").toString().replace("\"", "").replace("[", "").replace("]", "");
	//String rarocWithCrossSellIncome = (String)customer.get( "rarocWithCrossSellIncome").toString().replace("\"", "").replace("[", "").replace("]", ""); on 18-08-2024
	Float rarocWithCrossSellIncomeFloat = Float.parseFloat(customer.get( "rarocWithCrossSellIncome").toString().replace("\"", "").replace("[", "").replace("]", ""))*100;
	log.info("rarocWithCrossSellIncomeFloat sens..."+rarocWithCrossSellIncomeFloat);
	String rarocWithCrossSellIncome =rarocWithCrossSellIncomeFloat.toString();
	String totalLimitFunded = customer.get( "totalLimitFunded").toString().replace("\"", "").replace("[", "").replace("]", "");
	String totalLimitNonFunded = customer.get( "totalLimitNonFunded").toString().replace("\"", "").replace("[", "").replace("]", "");
	log.info("customer tempObj is "+totalLimitNonFunded);
	
	iformreference.setValue("Q_NG_RAROC_CUSTOMER_customer_name_Sensitized",customerName);
	//iformreference.setValue("LEAD_REF_NO",customerCode);
	iformreference.setValue("Q_NG_RAROC_CUSTOMER_cross_sell_incm_Sensitized",crossSellIncome);
	iformreference.setValue("Q_NG_RAROC_CUSTOMER_Customer_RAROC_sensitized",rarocWithCrossSellIncome);//26-07-2024 by mohit adding % ; removed on 18-08-2024
	iformreference.setValue("Q_NG_RAROC_CUSTOMER_total_funded_aed_Sensitized",totalLimitFunded);
	iformreference.setValue("Q_NG_RAROC_CUSTOMER_total_nonfunded_aed_Sensitized",totalLimitNonFunded);
	//System.out.println(rarocDetailsResponseArray);
	org.json.simple.JSONArray facilities = (org.json.simple.JSONArray)projectedRarocDetailsResponse.get("facilities");
	System.out.println("facilities tempObj is "+facilities);
		System.out.println(facilities);
		int rarocSize = facilities.size();
		//start edit by mohit 30-06-2024 change status(Success to Partial Success) if rarocSize is zero
		log.info("setCustomerProjectedData  rarocSize..."+rarocSize);
		if(rarocSize < 1) {
			status = "Partial Success";
		}else {
		//end edit by mohit 30-06-2024
			String formFacilityId = iformreference.getValue("F_FACILITY_ID").toString();
			String formCommitmentNo = iformreference.getValue("F_COMMITMENT_NO").toString();
			log.info("formFacilityId.."+formFacilityId+"---formCommitmentNo.."+formCommitmentNo+"---");
			
			for(int i = 0 ; i<rarocSize; i++){
				log.info("Reshank...."+i);
				log.info("setCustomerProjectedData  rarocSize..."+rarocSize);
				org.json.simple.JSONObject tempObj = (org.json.simple.JSONObject) facilities.get(i);
				String facility = tempObj.get( "facility").toString().replace("\"", "").replace("[", "").replace("]", "");
				String country = tempObj.get("country").toString().replace("\"", "").replace("[", "").replace("]", "");//
				String orr = tempObj.get( "orr").toString().replace("\"", "").replace("[", "").replace("]", "");
				log.info("orr..."+orr);
				String tenure = tempObj.get( "tenure").toString().replace("\"", "").replace("[", "").replace("]", "");
				String product = tempObj.get( "product").toString().replace("\"", "").replace("[", "").replace("]", "");//
				String limit = tempObj.get("limit").toString();
				String utilisedAmount = tempObj.get( "utilisedAmount").toString().replace("\"", "").replace("[", "").replace("]", "");
				String interestCharge =tempObj.get( "interestCharge").toString().replace("\"", "").replace("[", "").replace("]", "");
				String ftpRate = tempObj.get("ftpRate").toString().replace("\"", "").replace("[", "").replace("]", "");
				String riskAdjustedCapitalAnnual = tempObj.get( "riskAdjustedCapitalAnnual").toString().replace("\"", "").replace("[", "").replace("]", "");
				String expectedCreditLossAnnual = tempObj.get("expectedCreditLossAnnual").toString().replace("\"", "").replace("[", "").replace("]", "");
				String riskAdjustedReturnAnnual = tempObj.get( "riskAdjustedReturnAnnual").toString().replace("\"", "").replace("[", "").replace("]", "");
				Float rarocAnnualFloat=Float.parseFloat( tempObj.get( "rarocAnnual").toString().replace("\"", "").replace("[", "").replace("]", ""))*100;
				String rarocAnnual = rarocAnnualFloat.toString();
			
				
				// start edit by mohit 02-08-2024 to check if facility_id/commitment on form matches with the response to set corresponding values on form.
				
				if(facility.equalsIgnoreCase(formFacilityId)|| facility.equalsIgnoreCase(formCommitmentNo)) {
					log.info("Inside facility matches formFacilityId..."+facility+"="+formFacilityId+"or"+formCommitmentNo);
					iformreference.setValue("F_BORROWER_RATING_SENSITIZED",orr);
					log.info("getting value..."+ iformreference.getValue("F_BORROWER_RATING_SENSITIZED"));
					iformreference.setValue("F_REMAIN_TRM_SENSITIZED",tenure);
					iformreference.setValue("F_AVG_SENSITIZED",utilisedAmount);
					iformreference.setValue("F_INT_RATE_SENSITIZED",interestCharge);
					iformreference.setValue("F_FTP_SENSITIZED",ftpRate);
					iformreference.setValue("F_RISK_CAPITAL_SENSITIZED",riskAdjustedCapitalAnnual);
					iformreference.setValue("F_ECL_SENSITIZED",expectedCreditLossAnnual);
					iformreference.setValue("F_RISK_RETURN_SENSITIZED",riskAdjustedReturnAnnual);
					iformreference.setValue("F_FACILITY_RAROC_SENSITIZED",rarocAnnual);
				}
				// end edit by mohit 02-08-2024 to check if facility_id/commitment on form matches with the response to set corresponding values on form.
			
				/*String insertQuery ="UPDATE NG_RAROC_FACILITY_DETAILS SET REMAINING_TERMS_IN_MONTHS_PROPOSED ='"+tenure+"',FACILITY_LIMIT_AMOUNT_PROPOSED = '"+limit+"', AVERAGE_UTILISED_PROPOSED ='"+utilisedAmount+"',"
							   +"INTEREST_RATE_APPLIED_PROPOSED ='"+interestCharge+"', FTP_RATE_PROPOSED ='"+ftpRate+"', RISK_ADJUSTED_CAPITAL_PROPOSED ='"+riskAdjustedCapitalAnnual+"',"
							   + "ECL_PROPOSED ='"+expectedCreditLossAnnual+"', RISK_ADJUSTED_RETURN_PROPOSED ='"+riskAdjustedReturnAnnual+"',"
							   +"FACILITY_RAROC_PROPOSED ='"+rarocAnnual+"',Cash_Margin_Proposed = '"+cashOrEquivalentValue+"' ,BORROWER_RATING_PROPOSED = '"+orr+"',"
							   +"COMMITMENT_FEE_PROPOSED ='"+commitmentFeeAnnual+"', UPFRONT_FEE_PROPOSED ='"+upfrontFeeAnnual+"' WHERE COMMITMENT_NO ='"+facility+"'";*/
			/*String insertQuery ="UPDATE NG_RAROC_FACILITY_DETAILS SET REMAINING_TERMS_IN_MONTHS_SENITISED ='"+tenure+"',FACILITY_LIMIT_AMOUNT_SENITISED = '"+limit+"', AVERAGE_UTILISED_SENITISED ='"+utilisedAmount+"',"
					   +"INTEREST_RATE_APPLIED_SENITISED ='"+interestCharge+"', FTP_RATE_SENITISED ='"+ftpRate+"', RISK_ADJUSTED_CAPITAL_SENITISED ='"+riskAdjustedCapitalAnnual+"',"
					   + "ECL_SENITISED ='"+expectedCreditLossAnnual+"', RISK_ADJUSTED_RETURN_SENITISED ='"+riskAdjustedReturnAnnual+"',"
					   +"FACILITY_RAROC_SENITISED ='"+rarocAnnual+"',Cash_Margin_Sensitized = '' ,BORROWER_RATING_SENITISED = '"+orr+"',"
					   +"COMMITMENT_FEE_SENITISED ='', UPFRONT_FEE_SENITISED ='' WHERE ( COMMITMENT_NO ='"+facility+"' or facility_id='"+facility+"') and wi_name='"+wiName+"'";*/
			
			String insertQuery ="UPDATE NG_RAROC_FACILITY_DETAILS SET REMAINING_TERMS_IN_MONTHS_SENITISED ='"+tenure+"',FACILITY_LIMIT_AMOUNT_SENITISED = '"+limit+"', AVERAGE_UTILISED_SENITISED ='"+utilisedAmount+"',"
					   +"INTEREST_RATE_APPLIED_SENITISED ='"+interestCharge+"', FTP_RATE_SENITISED ='"+ftpRate+"', RISK_ADJUSTED_CAPITAL_SENITISED ='"+riskAdjustedCapitalAnnual+"',"
					   + "ECL_SENITISED ='"+expectedCreditLossAnnual+"', RISK_ADJUSTED_RETURN_SENITISED ='"+riskAdjustedReturnAnnual+"',"
					   +"FACILITY_RAROC_SENITISED ='"+rarocAnnual+"',Cash_Margin_Sensitized = '' ,BORROWER_RATING_SENITISED = '"+orr+"',"
					   +"COMMITMENT_FEE_SENITISED ='', UPFRONT_FEE_SENITISED ='', "
					   +"REMAINING_TERMS_IN_MONTHS_approved= '"+tenure+"',FACILITY_LIMIT_AMOUNT_approved='"+limit+"',AVERAGE_UTILISED_approved='"+utilisedAmount+"',"
					   +"INTEREST_RATE_APPLIED_approved='"+interestCharge+"',FTP_RATE_approved='"+ftpRate+"',"
					   +"RISK_ADJUSTED_CAPITAL_approved='"+riskAdjustedCapitalAnnual+"',ECL_approved='"+expectedCreditLossAnnual+"',RISK_ADJUSTED_RETURN_approved='"+riskAdjustedReturnAnnual+"'"
					   +",FACILITY_RAROC_approved='"+rarocAnnual+"',Cash_Margin_approved='',BORROWER_RATING_approved= '"+orr+"',COMMITMENT_FEE_approved='',UPFRONT_FEE_approved='' "	
					   + "WHERE ( COMMITMENT_NO ='"+facility+"' or facility_id='"+facility+"') and wi_name='"+wiName+"'";
			
			
			
			log.info("orr before for loop end"+orr);
			iformreference.saveDataInDB(insertQuery);
			
			log.info("insertQuery is "+insertQuery);
			log.info("query result is >>>>>>>"+iformreference.saveDataInDB(insertQuery));
			
			
			
		}
			//start edit by mohit 02-08-2024
			//executeServerEvent( formObject,ROW_CLICK,EVENT_TYPE_CLICK, facility);
			//end edit by mohit 02-08-2024
	}
}else{
}
	/*Common Common=new Common(iformreference);
	Common.insertIntoRarocIntegrationLogs(iformreference,callType, inputXML, outputXml, ReqDataTime, status);*///prod_04112024
	
//return status;
	} catch (Exception e) {
		// TODO: handle exception
		log.info(e.toString());
		
	}return status;
}
//end 
  //Suraj
public static String setCustomerProjectedData(IFormReference iformreference, String wiName, String outputXml, String inputXML, String ReqDataTime) throws Exception {
	     String callType ="projectedRaroc";
	     String status ="";
		log.info("inside setCustomerProjectedData>>>>>>");
		//customXMLParser parser = new customXMLParser(outputXml);
		//String res = parser.getValueOf("projectedRarocResponse");
		try {
			
			//mohit 03-07-2024 for converting XML to Json and passing status desc as Success/Exception/Failue
		//JSONObject req = new JSONObject(outputXml);
			
			 /*JSONObject req = XML.toJSONObject(outputXml);	  
			log.info("JsonObject req..." + req);
			
			JSONObject exceptionObject = getJSONObject(req, "exceptionDetails");
			log.info("exceptionObject>>>>>>" + exceptionObject);
			 status = getValue(exceptionObject,"successIndicator");
			 log.info("status00000...." + status);
			 
			JSONObject reqObject = getJSONObject(req, "CBS_RESPONSE");
			log.info("reqObject>>>>>>" + reqObject);*///R_0907-2024
			//status = getValue(reqObject, "STATUS_DESC");
			status = setCustomerProjectedData1(outputXml);
			log.info("status...." + status);
			//prod_04112024
			try {
			Common Common=new Common(iformreference);
			Common.insertIntoRarocIntegrationLogs(iformreference,callType, inputXML, outputXml, ReqDataTime, status);
			} catch (Exception e) {
				log.info("Exception insertIntoRarocIntegrationLogs: " + e);
			}
		if(status.equalsIgnoreCase("Success")){
		String Query =   "UPDATE ALM_RAROC_EXT_TABLE SET PROJECTED_FACILITY_CALL ='Y' WHERE WI_NAME ='"+wiName+"'";
			log.info("Inside insertQuery >"+Query);
			try {
				iformreference.saveDataInDB(Query);
				log.info("maincode>>>>>>>>>"+iformreference.saveDataInDB(Query));
			} catch (Exception e) {
				// TODO: handle exception
				log.info(e.toString());
			}

		/*===================================*/
		org.json.simple.parser.JSONParser parser=new org.json.simple.parser.JSONParser();
		Object obj=parser.parse(outputXml);
		org.json.simple.JSONObject json= (org.json.simple.JSONObject)obj;
		org.json.simple.JSONObject body = (org.json.simple.JSONObject)json.get("body");
		log.info("body>>>>>>> "+body);
		org.json.simple.JSONObject projectedRarocDetailsResponse = (org.json.simple.JSONObject)body.get("projectedRarocDetailsResponse");
		String groupProjetedRarocPercentage = projectedRarocDetailsResponse.get("groupProjectedRaroc").toString();
		String groupProjetedRaroc = "";
		if(groupProjetedRarocPercentage!= null && groupProjetedRarocPercentage != "" ) {
			Float groupRarocPercent = (Float.parseFloat(groupProjetedRarocPercentage)*100);
			groupProjetedRaroc = groupRarocPercent.toString();
		}
		iformreference.setValue("Q_NG_RAROC_GROUP_Group_Proposed",groupProjetedRaroc+"%");
		org.json.simple.JSONObject customer = (org.json.simple.JSONObject)projectedRarocDetailsResponse.get("customer");
		log.info("customer tempObj is "+customer);
		String customerName = customer.get("customerName").toString().replace("\"", "").replace("[", "").replace("]", "");
		String customerCode = customer.get( "customerCode").toString().replace("\"", "").replace("[", "").replace("]", "");
		log.info("customer tempObj is "+customer.get( "customerCode").toString().replace("\"", "").replace("[", "").replace("]", ""));
		String crossSellIncome = customer.get( "crossSellIncome").toString().replace("\"", "").replace("[", "").replace("]", "");
		//String rarocWithCrossSellIncome = (String)customer.get( "rarocWithCrossSellIncome").toString().replace("\"", "").replace("[", "").replace("]", ""); on 18-08-2024
		Float rarocWithCrossSellIncomeFloat = Float.parseFloat(customer.get( "rarocWithCrossSellIncome").toString().replace("\"", "").replace("[", "").replace("]", ""))*100;
		log.info("rarocWithCrossSellIncomeFloat..."+rarocWithCrossSellIncomeFloat);
		String rarocWithCrossSellIncome =rarocWithCrossSellIncomeFloat.toString();
		String totalLimitFunded = customer.get( "totalLimitFunded").toString().replace("\"", "").replace("[", "").replace("]", "");
		String totalLimitNonFunded = customer.get( "totalLimitNonFunded").toString().replace("\"", "").replace("[", "").replace("]", "");
		log.info("customer tempObj is "+totalLimitNonFunded);
		
		iformreference.setValue("Q_NG_RAROC_CUSTOMER_customer_name_Proposed",customerName);
		//iformreference.setValue("LEAD_REF_NO",customerCode);
		iformreference.setValue("Q_NG_RAROC_CUSTOMER_cross_sell_incm_Proposed",crossSellIncome);
		iformreference.setValue("Q_NG_RAROC_CUSTOMER_Customer_RAROC_Proposed",rarocWithCrossSellIncome);//26-07-2024 by mohit adding %; removed 18-08-2024
		iformreference.setValue("Q_NG_RAROC_CUSTOMER_total_funded_aed_Proposed",totalLimitFunded);
		iformreference.setValue("Q_NG_RAROC_CUSTOMER_total_nonfunded_aed_Proposed",totalLimitNonFunded);
		//System.out.println(rarocDetailsResponseArray);
		org.json.simple.JSONArray facilities = (org.json.simple.JSONArray)projectedRarocDetailsResponse.get("facilities");
		System.out.println("facilities tempObj is "+facilities);
			System.out.println(facilities);
			int rarocSize = facilities.size();
			//start edit by mohit 30-06-2024 change status(Success to Partial Success) if rarocSize is zero
			log.info("setCustomerProjectedData  rarocSize..."+rarocSize);
			if(rarocSize < 1) {
				status = "Partial Success";
			}else {
			//end edit by mohit 30-06-2024
				String formFacilityId = iformreference.getValue("F_FACILITY_ID").toString();
				String formCommitmentNo = iformreference.getValue("F_COMMITMENT_NO").toString();
				log.info("formFacilityId.."+formFacilityId+"---formCommitmentNo.."+formCommitmentNo+"---");
				for(int i = 0 ; i<rarocSize; i++){
					log.info("Reshank prop...."+i);
					log.info("setCustomerProjectedData  rarocSize...1111111"+rarocSize);
					org.json.simple.JSONObject tempObj = (org.json.simple.JSONObject) facilities.get(i);
					log.info("setCustomerProjectedData  rarocSize...2222222222"+rarocSize);
					//System.out.println("tempObj is "+tempObj);
					String country = tempObj.get("country").toString().replace("\"", "").replace("[", "").replace("]", "");//
					
					String orr = tempObj.get( "orr").toString().replace("\"", "").replace("[", "").replace("]", "");
					log.info("orr prop..."+orr);
					//iformreference.setValue("F_BORROWER_RATING_PROPOSED",orr);
					//log.info("orr is "+orr);
					String facility = tempObj.get( "facility").toString().replace("\"", "").replace("[", "").replace("]", "");
					String tenure = tempObj.get( "tenure").toString().replace("\"", "").replace("[", "").replace("]", "");
					//iformreference.setValue("F_REMAIN_TRM_PROP",tenure);
					String product = tempObj.get( "product").toString().replace("\"", "").replace("[", "").replace("]", "");//
					String limit = tempObj.get("limit").toString();
					String utilisedAmount = tempObj.get( "utilisedAmount").toString().replace("\"", "").replace("[", "").replace("]", "");
					//iformreference.setValue("F_AVG_PROP",utilisedAmount);
					String interestCharge =tempObj.get( "interestCharge").toString().replace("\"", "").replace("[", "").replace("]", "");
					//iformreference.setValue("F_INT_RATE_PROP",interestCharge);
					String ftpRate = tempObj.get("ftpRate").toString().replace("\"", "").replace("[", "").replace("]", "");
					//iformreference.setValue("F_FTP_PROP",ftpRate);
					//String commitmentFeeAnnual = tempObj.get( "commitmentFeeAnnual").toString(); Mohit to check
					//System.out.println("industrySeg is "+industrySeg);
					//String upfrontFeeAnnual = tempObj.get( "upfrontFeeAnnual").toString();
					String riskAdjustedCapitalAnnual = tempObj.get( "riskAdjustedCapitalAnnual").toString().replace("\"", "").replace("[", "").replace("]", "");
					//iformreference.setValue("F_RISK_CAPITAL_PROP",riskAdjustedCapitalAnnual);
					String expectedCreditLossAnnual = tempObj.get("expectedCreditLossAnnual").toString().replace("\"", "").replace("[", "").replace("]", "");
					//iformreference.setValue("F_ECL_PROP",expectedCreditLossAnnual);
					String riskAdjustedReturnAnnual = tempObj.get( "riskAdjustedReturnAnnual").toString().replace("\"", "").replace("[", "").replace("]", "");
					//iformreference.setValue("F_RISK_RETURN_PROP",riskAdjustedReturnAnnual);
					Float rarocAnnualFloat=Float.parseFloat( tempObj.get( "rarocAnnual").toString().replace("\"", "").replace("[", "").replace("]", ""))*100;
					String rarocAnnual = rarocAnnualFloat.toString();
					//iformreference.setValue("F_FACILITY_RAROC_PROP",rarocAnnual);
					
					// start edit by mohit 02-08-2024 to check if facility_id/commitment on form matches with the response to set corresponding values on form.
					if(facility.equalsIgnoreCase(formFacilityId)|| facility.equalsIgnoreCase(formCommitmentNo)) {
						iformreference.setValue("F_BORROWER_RATING_PROPOSED",orr);
						log.info("orr on form"+iformreference.getValue("F_BORROWER_RATING_PROPOSED"));
						iformreference.setValue("F_REMAIN_TRM_PROP",tenure);
						iformreference.setValue("F_AVG_PROP",utilisedAmount);
						iformreference.setValue("F_INT_RATE_PROP",interestCharge);
						iformreference.setValue("F_FTP_PROP",ftpRate);
						iformreference.setValue("F_RISK_CAPITAL_PROP",riskAdjustedCapitalAnnual);
						iformreference.setValue("F_ECL_PROP",expectedCreditLossAnnual);
						iformreference.setValue("F_RISK_RETURN_PROP",riskAdjustedReturnAnnual);
						iformreference.setValue("F_FACILITY_RAROC_PROP",rarocAnnual);
					}
					// end edit by mohit 02-08-2024 to check if facility_id/commitment on form matches with the response to set corresponding values on form.
				
					
					//String cashOrEquivalentValue = tempObj.get( "cashOrEquivalentValue").toString(); Mohit to check
					//String receivablesName = tempObj.get( "receivablesName").toString();  Mohit to check
					//log.info("receivablesName is "+receivablesName);
					//	iformrefrence.setValue("","");
				
				/*String insertQuery ="UPDATE NG_RAROC_FACILITY_DETAILS SET REMAINING_TERMS_IN_MONTHS_PROPOSED ='"+tenure+"',FACILITY_LIMIT_AMOUNT_PROPOSED = '"+limit+"', AVERAGE_UTILISED_PROPOSED ='"+utilisedAmount+"',"
								   +"INTEREST_RATE_APPLIED_PROPOSED ='"+interestCharge+"', FTP_RATE_PROPOSED ='"+ftpRate+"', RISK_ADJUSTED_CAPITAL_PROPOSED ='"+riskAdjustedCapitalAnnual+"',"
								   + "ECL_PROPOSED ='"+expectedCreditLossAnnual+"', RISK_ADJUSTED_RETURN_PROPOSED ='"+riskAdjustedReturnAnnual+"',"
								   +"FACILITY_RAROC_PROPOSED ='"+rarocAnnual+"',Cash_Margin_Proposed = '"+cashOrEquivalentValue+"' ,BORROWER_RATING_PROPOSED = '"+orr+"',"
								   +"COMMITMENT_FEE_PROPOSED ='"+commitmentFeeAnnual+"', UPFRONT_FEE_PROPOSED ='"+upfrontFeeAnnual+"' WHERE COMMITMENT_NO ='"+facility+"'";*/
				String insertQuery ="UPDATE NG_RAROC_FACILITY_DETAILS SET REMAINING_TERMS_IN_MONTHS_PROPOSED ='"+tenure+"',FACILITY_LIMIT_AMOUNT_PROPOSED = '"+limit+"', AVERAGE_UTILISED_PROPOSED ='"+utilisedAmount+"',"
						   +"INTEREST_RATE_APPLIED_PROPOSED ='"+interestCharge+"', FTP_RATE_PROPOSED ='"+ftpRate+"', RISK_ADJUSTED_CAPITAL_PROPOSED ='"+riskAdjustedCapitalAnnual+"',"
						   + "ECL_PROPOSED ='"+expectedCreditLossAnnual+"', RISK_ADJUSTED_RETURN_PROPOSED ='"+riskAdjustedReturnAnnual+"',"
						   +"FACILITY_RAROC_PROPOSED ='"+rarocAnnual+"',Cash_Margin_Proposed = '' ,BORROWER_RATING_PROPOSED = '"+orr+"',"
						   +"COMMITMENT_FEE_PROPOSED ='', UPFRONT_FEE_PROPOSED ='' WHERE ( COMMITMENT_NO ='"+facility+"' or facility_id='"+facility+"') and wi_name='"+wiName+"'";

				iformreference.saveDataInDB(insertQuery);
				
				log.info("insertQuery is "+insertQuery);
				log.info("query result is >>>>>>>"+iformreference.saveDataInDB(insertQuery));
				log.info("orr final check ..."+orr);
			}
		}
	}else{
	}
		/*Common Common=new Common(iformreference);
		Common.insertIntoRarocIntegrationLogs(iformreference,callType, inputXML, outputXml, ReqDataTime, status);*///prod_04112024
     //return status;
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e.toString());
			
		}return status;
	}
	public static  String getValue(JSONObject jbx, String columnname)
	{
		String  colmn = "NO_DATA";
		try {
			//		logger.info("Insode ifloop function with json "+jbx.toString(4)+" and column Name "+columnname);
			// LogGen.writeConsoleLog("Column name in First :"+columnname);
			// LogGen.writeConsoleLog("Column value type :"+jbx.get(columnname).getClass());
 
 
			if(jbx.has(columnname))
			{
				if(jbx.get(columnname) instanceof Integer)
				{
					columnname = Integer.toString(jbx.optInt(columnname));
				}
				else if(jbx.get(columnname) instanceof Long)
				{
					columnname = Long.toString(jbx.optLong(columnname));
				}
				else if(jbx.get(columnname) instanceof String)
				{
					columnname = jbx.optString(columnname);
				}
				else if(jbx.get(columnname) instanceof BigDecimal)
				{
					BigDecimal bd = new BigDecimal(jbx.opt(columnname).toString()).setScale(2, RoundingMode.HALF_UP);
					columnname =bd.toString();
				}
				else if(jbx.get(columnname) instanceof Double)
				{
					columnname = String.format("%.2f",(jbx.optDouble(columnname)));
				}
				else if(jbx.get(columnname) instanceof Boolean)
				{
					columnname = Boolean.toString(jbx.optBoolean(columnname));
				}
				else if(jbx.get(columnname) instanceof BigInteger)
				{
 
					BigInteger bd = new BigInteger(jbx.opt(columnname).toString());
					columnname =bd.toString();
					//				  columnname = Long.toString(jbx.getLong(columnname));
					//				  columnname = jbx.get(columnname).toString();
				}
				else if(jbx.get(columnname) instanceof JSONObject)
				{
					columnname = String.valueOf(jbx.optJSONObject(columnname));
				}
				else if(jbx.get(columnname) instanceof JSONArray)
				{
					columnname = String.valueOf(jbx.optJSONArray((columnname)));
				}
				else
				{
					columnname="";
				}
 
				// LogGen.writeConsoleLog("Column value in last ifloopfu..... "+columnname);
			}
			else
			{
				columnname="";
			}
		} catch (Exception e) {
			//LogGen.writeErrorLog("Exception in ifloopFunction[Configurations.class] "+columnname +" and exception is"+e);
		}
		if(colmn.equals(columnname))
			columnname="";
		return columnname;
	}// end of fun
 
 
	public static JSONObject getJSONObject(JSONObject jbx, String columnname)
	{
		JSONObject result = null;
		try {
			// LogGen.writeConsoleLog("Column value type :"+jbx.get(columnname).getClass());
			if(jbx.has(columnname))
			{
				if(jbx.get(columnname) instanceof JSONObject)
				{
					result = jbx.optJSONObject(columnname);
				}
				// LogGen.writeConsoleLog("Column value in last ifloopfu..... "+columnname);
			}
		} catch (Exception e) {
			//LogGen.writeErrorLog("inside getJSONObject catch block "+columnname+" and excpetion e"+e);
		}
		return result;
	}
 
 
	public static org.json.JSONArray getJSONArray(JSONObject jbx, String columnname)
	{
		org.json.JSONArray result = null;
		try {
			// LogGen.writeConsoleLog("Column value type :"+jbx.get(columnname).getClass());
			if(jbx.has(columnname))
			{
				if(jbx.get(columnname) instanceof JSONArray)
				{
					result = jbx.getJSONArray((columnname));
				}
				// LogGen.writeConsoleLog("Column value in last ifloopfu..... "+columnname);
			}
 
		} catch (Exception e) {
			//LogGen.writeErrorLog("inside getJSONArray catch block "+columnname+" and excpetion e"+e);
		}
		return result ;
	}
	
	public static String setCustomerProjectedData1(String outputJson) //throws JSONException
	{
		log.info("setCustomerProjectedData1########$$$$$$$$");
		log.info("outputJson!!!!!"+outputJson);
		JSONObject expenseArray =null;
		String successIndicatorValue="ERROR";
		String json ="";
		if(outputJson.contains("<STATUS_CODE>100</STATUS_CODE>")|| outputJson.contains("\"successIndicator\": \"Success\"")){
			log.info("Insdie 100 status code");
		try {
		 expenseArray = new JSONObject(outputJson);
		 
		}catch(Exception e) {
			log.info("setCustomerProjectedData1.."+e.getMessage());
		}
		
		 // String json="{  \"exceptionDetails\": {    \"successIndicator\": \"Success\"  },  \"body\": {    \"projectedRarocDetailsResponse\": {      \"facilities\": [        {          \"country\": \"United Arab Emirates\",          \"orr\": \"4\",          \"facility\": \"25434.0011011.01\",          \"tenure\": \"3\",          \"product\": \"Overdraft Corp Rev\",          \"limit\": \"40000000.0\",          \"utilisedAmount\": \"760000000000000.0\",          \"interestCharge\": \"0.06531826625\",          \"ftpRate\": \"0.2526733\",          \"riskAdjustedCapitalAnnual\": \"53332302284268.3\",          \"expectedCreditLossAnnual\": \"1270787061362.045\",          \"riskAdjustedReturnAnnual\": \"-81915145792211.19\",          \"rarocAnnual\": \"-1.535938676631519\"        }      ],      \"customer\": {        \"customerName\": [          \"C00022701 Customer Name\"        ],        \"customerCode\": [          \"25434\"        ],        \"crossSellIncome\": [          \"0.0\"        ],        \"rarocWithCrossSellIncome\": [          \"-1.535938676631519\"        ],        \"totalLimitFunded\": [          \"40000000.0\"        ],        \"totalLimitNonFunded\": [          \"0.0\"        ]      },      \"groupProjectedRaroc\": \"-1.5359386022865456\"    }  }}";

		//JSONObject req = new JSONObject(outputXml);
		org.json.simple.parser.JSONParser jsonParser1=new org.json.simple.parser.JSONParser();
		Object object1=null;
		try {
			 json = expenseArray.toString();
				log.info("json...."+json);
			object1 = jsonParser1.parse(json);
			log.info("try block ...."+object1);
			log.info("before import line  ...."+object1);
			org.json.simple.JSONObject jobj=( org.json.simple.JSONObject )object1;
			  System.out.println(jobj);
			  log.info("jobj..."+jobj);
			  org.json.simple.JSONObject  jobj1=(org.json.simple.JSONObject)jobj.get("exceptionDetails");
			  System.out.println(jobj1.get("successIndicator"));
			  log.info("jobj1..."+jobj1);
			  
			   successIndicatorValue = (String) (jobj1.get("successIndicator").equals(null)? "ERROR":jobj1.get("successIndicator"));
			  log.info("successIndicatorValue..."+successIndicatorValue);
			  
			  org.json.simple.JSONObject jobj2=(org.json.simple.JSONObject)jobj.get("body");
			  log.info("jobj2..."+jobj2);
			  
			  
			  
			  org.json.simple.JSONObject jobj3=(org.json.simple.JSONObject)jobj2.get("projectedRarocDetailsResponse");
			  log.info("jobj3..."+jobj3);
			 // jobj1=(JSONObject)jobj1.get("groupProjectedRaroc");
			  System.out.println(jobj3.get("groupProjectedRaroc"));

		} catch (org.json.simple.parser.ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
		return successIndicatorValue;
	}
}



