package com.newgen.cbg.service;

import java.util.HashMap;
import com.newgen.cbg.logger.DSCOPLogMe;
import com.newgen.cbg.request.ApplicationAttributes;
import com.newgen.cbg.request.BulkEnquiryApplicationAttributes;
import com.newgen.cbg.request.BulkEnquiryAtributes;
import com.newgen.cbg.request.CBGSingleHookRequest;
import com.newgen.cbg.response.BulkEnquiryResponse;
import com.newgen.cbg.utils.APCallCreateXML;
import com.newgen.cbg.utils.DSCOPUtils;
import com.newgen.omni.jts.cmgr.XMLParser;


public class COPDashBoardService {

	public BulkEnquiryResponse bulkEnquiry(CBGSingleHookRequest request) {
		ApplicationAttributes[] attributes = request.getApplicationAttributes();
		HashMap<String, String> attributeMap = DSCOPUtils.getInstance().requestToAttributeMap(attributes);
		String leadRefNo = request.getLeadNumber().trim().toUpperCase();
		BulkEnquiryResponse bulkResponse = new BulkEnquiryResponse();
		String outputXML = "";
		String totalRecordSelect = "";
		BulkEnquiryAtributes[] bulkAttributes = null;
		try
		{
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "BulkEnquiryResponse STATUS :" + attributeMap.get("STATUS"));
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "BulkEnquiryResponse DSA_CODE :" + attributeMap.get("DSA_CODE"));
			if(attributeMap.get("STATUS").equalsIgnoreCase("20")){  
				return leadOpportunityEnqiry(request);
			}
			else{
				String query = "SELECT WI_NAME, CUSTOMER_MOBILE_NO, PASSPORT_FULL_NAME, CUSTOMER_ID, PRODUCT_DESC, STATUS, INITIATION_DATETIME, STAGE_NAME, "
						+ "ADD_COMMTS, CUSTOMER_SEGMENT, REQUESTED_DATETIME FROM "
						+ "(SELECT WI_NAME, CUSTOMER_MOBILE_NO, NVL(PASSPORT_FULL_NAME,UPPER(CUSTOMER_FULL_NAME)) AS PASSPORT_FULL_NAME, CUSTOMER_ID, PRODUCT_DESC, WI_STATUS AS STATUS, STAGE_NAME, ADD_COMMTS, "
						+ "CUSTOMER_SEGMENT, REQUESTED_DATETIME, INITIATION_DATETIME, RANK() OVER (ORDER BY REQUESTED_DATETIME DESC) AS BATCH "
						+ "FROM EXT_CBG_CUST_ONBOARDING ";

				String whereClause = "";
				int batchFirst = 1;
				int batchLast = 1;
				String totalRecordQuery = "SELECT COUNT(1) AS TOTALCOUNT FROM EXT_CBG_CUST_ONBOARDING ";
				String totalCountWhere = "";

				if (attributeMap.isEmpty())
				{
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Complete query1 :" + query + " ORDER BY  REQUESTED_DATETIME DESC");
					outputXML = APCallCreateXML.APSelect(query + " ORDER BY  REQUESTED_DATETIME DESC");
					totalRecordSelect = APCallCreateXML.APSelect(totalRecordQuery );
				}
				else
				{
					String toDate = (String)attributeMap.get("TO_DATE");
					String fromDate = (String)attributeMap.get("FROM_DATE");
					int batchSize = Integer.parseInt((((String)attributeMap.get("BATCH_SIZE")).equals("")) ? "0" : (String)attributeMap.get("BATCH_SIZE"));
					int startFrom = Integer.parseInt((((String)attributeMap.get("START_FROM")).equals("")) ? "0" : (String)attributeMap.get("START_FROM"));
					String dsa = (String)attributeMap.get("DSA_CODE")!=null?(String)attributeMap.get("DSA_CODE").toUpperCase():"";
					String status = (String)attributeMap.get("STATUS");
					String orderBy = (String)attributeMap.get("ORDER_BY");

					if ((batchSize != 0) && (startFrom > 0))
					{
						batchLast = batchSize + startFrom;
						batchFirst = startFrom;
					}

					else if ((batchSize != 0) && (((startFrom == 0)))) {
						batchLast = batchSize;
						batchFirst = 0;
					}

					if(!leadRefNo.isEmpty())
					{
						if (leadRefNo.length()==15 && leadRefNo.startsWith("784"))
							status= "AND EIDA_NUMBER in ('"+leadRefNo+"') ";
						else if (leadRefNo.length()==12 && leadRefNo.startsWith("9715"))
							status= "AND CUSTOMER_MOBILE_NO in ('"+leadRefNo+"') ";
						else if (leadRefNo.length()==7)
							status= "AND LEAD_REF_NO in ('"+leadRefNo+"') ";
						else if (leadRefNo.contains("@"))
							status = "AND upper(CUSTOMER_EMAIL) in (upper('"+leadRefNo+"')) ";
						else 
							status = " AND ( EIDA_NUMBER in ('"+leadRefNo+"') OR (CUSTOMER_MOBILE_NO) in ('"+leadRefNo+"') OR LEAD_REF_NO in ('"+leadRefNo+"') OR upper(CUSTOMER_EMAIL) in upper(('"+leadRefNo+"')) ) ";
					}
					else
					{
						if (status.equalsIgnoreCase("1A"))
						{
							status = "AND ((WI_STATUS = '1') OR WI_STATUS = '2' OR WI_STATUS = '7' OR WI_STATUS = '22' OR WI_STATUS = '23') ";
							//Sanal Grover 22032023 COP 2863 Added 7 // Gaurav Berry 29032023 22 Added for Talabat // Sanal Grover 12042023 Added 23 for COP 2931 Medium Risk
						}
						else if (status.equalsIgnoreCase("4A"))
						{
							//COP-3981 20/06/2023 Shruti
//							status = "AND (WI_STATUS in ('4','5','15','19'))";
							status = "AND (WI_STATUS in ('4','5','15','19','26'))";
						}
						else if (status.equalsIgnoreCase("6A"))
						{
							status = "AND (WI_STATUS in ('6','21') AND (RETURN_JOURNEY IS NULL OR RETURN_JOURNEY = 'N')) "; //Sanal Grover 22032023 COP 2863 Removed 7
						}
						else if (status.equalsIgnoreCase("8A"))
						{
							status = "AND (WI_STATUS in ('8','9') AND (RETURN_JOURNEY IS NULL OR RETURN_JOURNEY = 'N')) ";
						}
						else if (status.equalsIgnoreCase("11A"))
						{
							status = " AND (WI_STATUS in ('11','12','13') AND (RETURN_JOURNEY IS NULL OR RETURN_JOURNEY = 'N'))";
						}
						else if (status.equalsIgnoreCase("1"))
						{
							status = " AND (WI_STATUS in ('"+status+"') OR RETURN_JOURNEY = 'Y') ";
						}
						else 
						{
							status = "AND WI_STATUS in ('"+status+"')";
						}
					}
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Attributes :" + toDate + ", " + fromDate + ", " + batchSize + ", " + startFrom + ", " + dsa + ", " + status + ", " + orderBy);

					if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())) && (!(orderBy.isEmpty())) && (!(status.isEmpty())) && (!(status.equalsIgnoreCase("ALL"))) 
							&& (!(dsa.isEmpty())) && (batchSize != 0) && (startFrom != 0))
					{
						whereClause = "WHERE (SOURCING_CHANNEL = 'ADCBCOP' or ((SOURCING_CHANNEL = 'ADCBCCSSO' or SOURCING_CHANNEL='CBGAPP') and WI_STATUS='22') or (SOURCING_CHANNEL = 'ADCBMIB' and WI_STATUS='22')) AND TRUNC(REQUESTED_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
								"AND TO_DATE('" + toDate + "','DD-MM-YYYY') AND UPPER(DSA_PROMO_CODE) = '" + dsa + "'   " + status  + 
								"ORDER BY REQUESTED_DATETIME " + orderBy + ") WHERE BATCH >= " + batchFirst + " AND BATCH < " + batchLast;
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE CLAUSE 1: " + whereClause);

						totalCountWhere = "WHERE (SOURCING_CHANNEL = 'ADCBCOP' or ((SOURCING_CHANNEL = 'ADCBCCSSO' or SOURCING_CHANNEL='CBGAPP') and WI_STATUS='22') or (SOURCING_CHANNEL = 'ADCBMIB' and WI_STATUS='22')) AND TRUNC(REQUESTED_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
								"AND TO_DATE('" + toDate + "','DD-MM-YYYY') AND UPPER(DSA_PROMO_CODE) = '" + dsa + "'   " + status  +  
								"ORDER BY REQUESTED_DATETIME " + orderBy;
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 1: " + totalCountWhere);
					}
					else if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())) && (!(orderBy.isEmpty())) && (!(status.isEmpty())) && (!(status.equalsIgnoreCase("ALL")))
							&& (!(dsa.isEmpty())) && (batchSize == 0) && (startFrom == 1))
					{
						whereClause = "WHERE (SOURCING_CHANNEL = 'ADCBCOP' or ((SOURCING_CHANNEL = 'ADCBCCSSO' or SOURCING_CHANNEL='CBGAPP') and WI_STATUS='22') or (SOURCING_CHANNEL = 'ADCBMIB' and WI_STATUS='22')) AND TRUNC(REQUESTED_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
								"AND TO_DATE('" + toDate + "','DD-MM-YYYY') AND UPPER(DSA_PROMO_CODE) = '" + dsa + "'   " + status  +  
								"ORDER BY REQUESTED_DATETIME " + orderBy;
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE CLAUSE 2: " + totalCountWhere);

						totalCountWhere = "WHERE (SOURCING_CHANNEL = 'ADCBCOP' or ((SOURCING_CHANNEL = 'ADCBCCSSO' or SOURCING_CHANNEL='CBGAPP') and WI_STATUS='22') or (SOURCING_CHANNEL = 'ADCBMIB' and WI_STATUS='22')) AND TRUNC(REQUESTED_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
								"AND TO_DATE('" + toDate + "','DD-MM-YYYY') AND UPPER(DSA_PROMO_CODE) = '" + dsa + "'   " + status  +   
								"ORDER BY REQUESTED_DATETIME " + orderBy ;
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 2: " + totalCountWhere);
					}
					else if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())) && (!(orderBy.isEmpty())) && (!(status.isEmpty())) && (!(status.equalsIgnoreCase("ALL"))) 
							&& (!(dsa.isEmpty())) && (batchSize != 0) && (startFrom == 0))
					{
						whereClause = "WHERE (SOURCING_CHANNEL = 'ADCBCOP' or ((SOURCING_CHANNEL = 'ADCBCCSSO' or SOURCING_CHANNEL='CBGAPP') and WI_STATUS='22') or (SOURCING_CHANNEL = 'ADCBMIB' and WI_STATUS='22')) AND TRUNC(REQUESTED_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
								"AND TO_DATE('" + toDate + "','DD-MM-YYYY') AND UPPER(DSA_PROMO_CODE) = '" + dsa + "'   " + status  + 
								"ORDER BY REQUESTED_DATETIME " + orderBy + ") WHERE BATCH >= " + batchFirst + " AND BATCH < " + batchLast;
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE CLAUSE 3: " + whereClause);

						totalCountWhere = "WHERE SOURCING_CHANNEL = 'ADCBCOP' AND TRUNC(REQUESTED_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') AND TO_DATE('" + toDate + "','DD-MM-YYYY')";
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 3: " + totalCountWhere);
					}
					else if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())) && (!(orderBy.isEmpty())) && (!(status.isEmpty())) && (!(status.equalsIgnoreCase("ALL"))) 
							&& (!(dsa.isEmpty())))
					{
						whereClause = "WHERE (SOURCING_CHANNEL = 'ADCBCOP' or ((SOURCING_CHANNEL = 'ADCBCCSSO' or SOURCING_CHANNEL='CBGAPP') and WI_STATUS='22') or (SOURCING_CHANNEL = 'ADCBMIB' and WI_STATUS='22')) AND TRUNC(REQUESTED_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
								"AND TO_DATE('" + toDate + "','DD-MM-YYYY') AND UPPER(DSA_PROMO_CODE) = '" + dsa + "'   " + status  + 
								"ORDER BY REQUESTED_DATETIME " + orderBy + " " ;
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE CLAUSE 4: " + whereClause);

						totalCountWhere = "WHERE (SOURCING_CHANNEL = 'ADCBCOP' or ((SOURCING_CHANNEL = 'ADCBCCSSO' or SOURCING_CHANNEL='CBGAPP') and WI_STATUS='22') or (SOURCING_CHANNEL = 'ADCBMIB' and WI_STATUS='22')) AND TRUNC(REQUESTED_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
								"AND TO_DATE('" + toDate + "','DD-MM-YYYY') " + 
								"   " + status  + " AND UPPER(DSA_PROMO_CODE) = '" + dsa + "'";
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 4: " + totalCountWhere);
					}
					else if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())) && (!(orderBy.isEmpty())) && (!(status.isEmpty())) && (!(status.equalsIgnoreCase("ALL"))) 
							&& (batchSize != 0) && (startFrom != 0))
					{
						whereClause = "WHERE (SOURCING_CHANNEL = 'ADCBCOP' or ((SOURCING_CHANNEL = 'ADCBCCSSO' or SOURCING_CHANNEL='CBGAPP') and WI_STATUS='22') or (SOURCING_CHANNEL = 'ADCBMIB' and WI_STATUS='22')) AND TRUNC(REQUESTED_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
								"AND TO_DATE('" + toDate + "','DD-MM-YYYY')   " + status  + " ORDER BY REQUESTED_DATETIME " + orderBy + ") " + 
								"WHERE BATCH >= " + batchFirst + " AND BATCH < " + batchLast;
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE 5: " + whereClause);

						totalCountWhere = "WHERE (SOURCING_CHANNEL = 'ADCBCOP' or ((SOURCING_CHANNEL = 'ADCBCCSSO' or SOURCING_CHANNEL='CBGAPP') and WI_STATUS='22') or (SOURCING_CHANNEL = 'ADCBMIB' and WI_STATUS='22')) AND TRUNC(REQUESTED_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY')" + 
								"AND TO_DATE('" + toDate + "','DD-MM-YYYY')";
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 5: " + totalCountWhere);
					}
					else if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())) && (!(status.isEmpty())) && (!(status.equalsIgnoreCase("ALL"))) && (!(dsa.isEmpty())))
					{
						whereClause = "WHERE (SOURCING_CHANNEL = 'ADCBCOP' or ((SOURCING_CHANNEL = 'ADCBCCSSO' or SOURCING_CHANNEL='CBGAPP') and WI_STATUS='22') or (SOURCING_CHANNEL = 'ADCBMIB' and WI_STATUS='22')) AND TRUNC(REQUESTED_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
								"AND TO_DATE('" + toDate + "','DD-MM-YYYY') AND UPPER(DSA_PROMO_CODE) = '" + dsa + "'   " + status  ;
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE CLAUSE 6:" + whereClause);

						totalCountWhere = "WHERE (SOURCING_CHANNEL = 'ADCBCOP' or ((SOURCING_CHANNEL = 'ADCBCCSSO' or SOURCING_CHANNEL='CBGAPP') and WI_STATUS='22') or (SOURCING_CHANNEL = 'ADCBMIB' and WI_STATUS='22')) AND TRUNC(REQUESTED_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') AND TO_DATE('" + toDate + "','DD-MM-YYYY') " + 
								"   " + status  +  " AND UPPER(DSA_PROMO_CODE) = '" + dsa + "'";
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 6: " + totalCountWhere);
					}
					else if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())) && (!(status.isEmpty())) && (!(status.equalsIgnoreCase("ALL"))))
					{
						whereClause = "WHERE (SOURCING_CHANNEL = 'ADCBCOP' or ((SOURCING_CHANNEL = 'ADCBCCSSO' or SOURCING_CHANNEL='CBGAPP') and WI_STATUS='22') or (SOURCING_CHANNEL = 'ADCBMIB' and WI_STATUS='22')) AND TRUNC(REQUESTED_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
								"AND TO_DATE('" + toDate + "','DD-MM-YYYY')    " + status  +  " )";
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE CLAUSE 7:" + whereClause);

						totalCountWhere = "WHERE (SOURCING_CHANNEL = 'ADCBCOP' or ((SOURCING_CHANNEL = 'ADCBCCSSO' or SOURCING_CHANNEL='CBGAPP') and WI_STATUS='22') or (SOURCING_CHANNEL = 'ADCBMIB' and WI_STATUS='22')) AND TRUNC(REQUESTED_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
								"AND TO_DATE('" + toDate + "','DD-MM-YYYY')    " + status  +  ")";
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 7: " + totalCountWhere);
					}
					else if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())) && (!(orderBy.isEmpty())) && (!(dsa.isEmpty())) && (batchSize != 0) && (startFrom != 0))
					{
						whereClause = "WHERE (SOURCING_CHANNEL = 'ADCBCOP' or ((SOURCING_CHANNEL = 'ADCBCCSSO' or SOURCING_CHANNEL='CBGAPP') and WI_STATUS='22') or (SOURCING_CHANNEL = 'ADCBMIB' and WI_STATUS='22')) AND TRUNC(REQUESTED_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
								"AND TO_DATE('" + toDate + "','DD-MM-YYYY') AND UPPER(DSA_PROMO_CODE) = '" + dsa + "' ORDER BY " + 
								"REQUESTED_DATETIME " + orderBy + ") WHERE BATCH >= " + batchFirst + " AND BATCH < " + batchLast;
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE 8: " + whereClause);
						

						totalCountWhere = "WHERE (SOURCING_CHANNEL = 'ADCBCOP' or ((SOURCING_CHANNEL = 'ADCBCCSSO' or SOURCING_CHANNEL='CBGAPP') and WI_STATUS='22') or (SOURCING_CHANNEL = 'ADCBMIB' and WI_STATUS='22')) AND TRUNC(REQUESTED_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
								"AND TO_DATE('" + toDate + "','DD-MM-YYYY') AND UPPER(DSA_PROMO_CODE) = '" + dsa + "'";
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 8: " + totalCountWhere);
					}
					else if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())) && (!(orderBy.isEmpty())) && (batchSize != 0) && (startFrom != 0))
					{
						whereClause = "WHERE (SOURCING_CHANNEL = 'ADCBCOP' or ((SOURCING_CHANNEL = 'ADCBCCSSO' or SOURCING_CHANNEL='CBGAPP') and WI_STATUS='22') or (SOURCING_CHANNEL = 'ADCBMIB' and WI_STATUS='22')) AND TRUNC(REQUESTED_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
								"AND TO_DATE('" + toDate + "','DD-MM-YYYY') ORDER BY REQUESTED_DATETIME " + orderBy + ") WHERE BATCH >= " + batchFirst + " " + 
								"AND BATCH < " + batchLast;
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE 9: " + whereClause);

						totalCountWhere = "WHERE (SOURCING_CHANNEL = 'ADCBCOP' or ((SOURCING_CHANNEL = 'ADCBCCSSO' or SOURCING_CHANNEL='CBGAPP') and WI_STATUS='22') or (SOURCING_CHANNEL = 'ADCBMIB' and WI_STATUS='22')) AND TRUNC(REQUESTED_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
								"AND TO_DATE('" + toDate + "','DD-MM-YYYY')";
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 9: " + totalCountWhere);
					}
					else if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())) && (!(orderBy.isEmpty())) && (batchSize != 0) && (startFrom == 0))
					{
						whereClause = "WHERE (SOURCING_CHANNEL = 'ADCBCOP' or ((SOURCING_CHANNEL = 'ADCBCCSSO' or SOURCING_CHANNEL='CBGAPP') and WI_STATUS='22') or (SOURCING_CHANNEL = 'ADCBMIB' and WI_STATUS='22')) AND TRUNC(REQUESTED_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
								"AND TO_DATE('" + toDate + "','DD-MM-YYYY') ORDER BY REQUESTED_DATETIME " + orderBy + ") WHERE BATCH >= " + batchFirst + " " + 
								"AND BATCH < " + batchLast;
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE 10: " + whereClause); 

						totalCountWhere = "WHERE (SOURCING_CHANNEL = 'ADCBCOP' or ((SOURCING_CHANNEL = 'ADCBCCSSO' or SOURCING_CHANNEL='CBGAPP') and WI_STATUS='22') or (SOURCING_CHANNEL = 'ADCBMIB' and WI_STATUS='22')) AND TRUNC(REQUESTED_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
								"AND TO_DATE('" + toDate + "','DD-MM-YYYY')";
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 10: " + totalCountWhere);
					}
					else if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())) && (!(dsa.isEmpty())))
					{
						whereClause = "WHERE (SOURCING_CHANNEL = 'ADCBCOP' or ((SOURCING_CHANNEL = 'ADCBCCSSO' or SOURCING_CHANNEL='CBGAPP') and WI_STATUS='22') or (SOURCING_CHANNEL = 'ADCBMIB' and WI_STATUS='22')) AND TRUNC(REQUESTED_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
								"AND TO_DATE('" + toDate + "','DD-MM-YYYY') AND UPPER(DSA_PROMO_CODE) = '" + dsa + "') ";
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE CLAUSE 11:" + whereClause);

						totalCountWhere = "WHERE (SOURCING_CHANNEL = 'ADCBCOP' or ((SOURCING_CHANNEL = 'ADCBCCSSO' or SOURCING_CHANNEL='CBGAPP') and WI_STATUS='22') or (SOURCING_CHANNEL = 'ADCBMIB' and WI_STATUS='22')) AND TRUNC(REQUESTED_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') AND TO_DATE('" + toDate + "','DD-MM-YYYY') " + 
								"AND UPPER(DSA_PROMO_CODE) = '" + dsa + "'";
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 11: " + totalCountWhere);
					}
					else if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())) && (!(orderBy.isEmpty())))
					{
						whereClause = "WHERE (SOURCING_CHANNEL = 'ADCBCOP' or ((SOURCING_CHANNEL = 'ADCBCCSSO' or SOURCING_CHANNEL='CBGAPP') and WI_STATUS='22') or (SOURCING_CHANNEL = 'ADCBMIB' and WI_STATUS='22')) AND TRUNC(REQUESTED_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
								"AND TO_DATE('" + toDate + "','DD-MM-YYYY') ORDER BY REQUESTED_DATETIME " + orderBy + ")";
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE CLAUSE 12:" + whereClause);

						totalCountWhere = "WHERE (SOURCING_CHANNEL = 'ADCBCOP' or ((SOURCING_CHANNEL = 'ADCBCCSSO' or SOURCING_CHANNEL='CBGAPP') and WI_STATUS='22') or (SOURCING_CHANNEL = 'ADCBMIB' and WI_STATUS='22')) AND TRUNC(REQUESTED_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
								"AND TO_DATE('" + toDate + "','DD-MM-YYYY')";
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 12: " + totalCountWhere);
					}
					else if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())))
					{
						whereClause = "WHERE (SOURCING_CHANNEL = 'ADCBCOP' or ((SOURCING_CHANNEL = 'ADCBCCSSO' or SOURCING_CHANNEL='CBGAPP') and WI_STATUS='22') or (SOURCING_CHANNEL = 'ADCBMIB' and WI_STATUS='22')) AND TRUNC(REQUESTED_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
								"AND TO_DATE('" + toDate + "','DD-MM-YYYY') ORDER BY REQUESTED_DATETIME " + orderBy + ")";
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE CLAUSE 13:" + whereClause);

						totalCountWhere = "WHERE (SOURCING_CHANNEL = 'ADCBCOP' or ((SOURCING_CHANNEL = 'ADCBCCSSO' or SOURCING_CHANNEL='CBGAPP') and WI_STATUS='22') or (SOURCING_CHANNEL = 'ADCBMIB' and WI_STATUS='22')) AND TRUNC(REQUESTED_DATETIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
								"AND TO_DATE('" + toDate + "','DD-MM-YYYY')";
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 13: " + totalCountWhere);
					}
					else
					{
						whereClause = "WHERE SOURCING_CHANNEL = 'ADCBCOP')";
						totalCountWhere = "WHERE SOURCING_CHANNEL = 'ADCBCOP'";
					}
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Complete query2 :" + query + whereClause);
					outputXML = APCallCreateXML.APSelect(query + whereClause);
					totalRecordSelect = APCallCreateXML.APSelect(totalRecordQuery + totalCountWhere);
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD Query: " + totalRecordSelect);
				}

				XMLParser xp = new XMLParser(outputXML);
				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				XMLParser xp2 = new XMLParser(totalRecordSelect);


				if (mainCode == 0) {
					int totalCount = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
					bulkAttributes = new BulkEnquiryAtributes[totalCount];
					if (totalCount > 0) {
						int start = xp.getStartIndex("Records", 0, 0);
						int deadEnd = xp.getEndIndex("Records", start, 0);
						int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
						int end = 0;
						for (int i = 0; i < noOfFields; ++i) {
							start = xp.getStartIndex("Record", end, 0);
							end = xp.getEndIndex("Record", start, 0);
							String wiName = xp.getValueOf("WI_NAME", start, end);
							String mobileNumber = xp.getValueOf("CUSTOMER_MOBILE_NO", start, end);
							String customerName = xp.getValueOf("PASSPORT_FULL_NAME", start, end);
							String customerID = xp.getValueOf("CUSTOMER_ID", start, end);
							String productName = xp.getValueOf("PRODUCT_DESC", start, end);
							String statusCode = xp.getValueOf("STATUS", start, end);
							String stageName = xp.getValueOf("STAGE_NAME", start, end);
							String submitDate = xp.getValueOf("INITIATION_DATETIME", start, end);
							String comments = xp.getValueOf("ADD_COMMTS", start, end);
							String segment = xp.getValueOf("CUSTOMER_SEGMENT", start, end);
							String remarksDateTime = xp.getValueOf("REQUESTED_DATETIME", start, end);

							BulkEnquiryAtributes attr = new BulkEnquiryAtributes();
							attr.setWiName(wiName);
							attr.setMobileNumber(mobileNumber);
							attr.setCustomerName(customerName);
							attr.setCustomerID(customerID);
							attr.setStatus(statusCode);
							attr.setSubmitDate(submitDate);
							attr.setProductName(productName);
							attr.setCurrentWorkStep(stageName);
							attr.setComments(comments);
							attr.setSegment(segment);
							attr.setRemarksDateTime(remarksDateTime);
							
							/*attr.setIban("AE020090004001079346500");
						attr.setAccountNumber("48766464544");
						attr.setBranchCode("101");
						attr.setBranchName("ABU DHABI MAIN");
						attr.setDashboardType("BCA");*/
							bulkAttributes[i] = attr;
						}
						String totalCountRecords = xp2.getValueOf("TOTALCOUNT");
						bulkResponse.setTOTALCOUNT(totalCountRecords);
						bulkResponse.setFETCHCOUNT(Integer.toString(noOfFields));
						bulkResponse.setStatusCode("0");
						bulkResponse.setStatusMessage("DATA FETCH SUCCESSFULLTY");
					}
					else {
						bulkResponse.setTOTALCOUNT(String.valueOf(0));
						bulkResponse.setFETCHCOUNT(String.valueOf(0));
						bulkResponse.setStatusCode("0");
						bulkResponse.setStatusMessage("NO DATA FOUND");
					}
				}
			}
			BulkEnquiryApplicationAttributes[] attrDetails = new BulkEnquiryApplicationAttributes[1];
			BulkEnquiryApplicationAttributes bulkattr = new BulkEnquiryApplicationAttributes();
			bulkattr.setBulkEnquiryDetails(bulkAttributes);

			attrDetails[0] = bulkattr;
			bulkResponse.setApplicationAttributes(attrDetails);
		}
		catch (Exception e)
		{
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}
		//    finally
		//    {
		//      this.mReqParamMap.clear();
		//    }
		return bulkResponse;
	}

	private BulkEnquiryResponse leadOpportunityEnqiry(CBGSingleHookRequest request){

		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Inside leadOpportunityEnqiry");
		ApplicationAttributes[] attributes = request.getApplicationAttributes();
		HashMap<String, String> attributeMap = DSCOPUtils.getInstance().requestToAttributeMap(attributes);
		BulkEnquiryResponse bulkResponse = new BulkEnquiryResponse();
		String outputXML = "";
		String totalRecordSelect = "";
		BulkEnquiryAtributes[] bulkAttributes = null;
		try
		{
			/*String query = "SELECT ENTRY_DATE_TIME, LEAD_REF_NO, EIDA_NUMBER, PRODUCT_JOURNEY, DSA_PROMO_CODE, CUSTOMER_FULL_NAME, CUSTOMER_MOBILE_NO, CUSTOMER_EMAIL, CUSTOMER_ID, "
					+ "CUSTOMER_EMPLOYER_NAME, CUSTOMER_MONTHLY_INCOME, PREFERRED_LANGUAGE, STATUS, PRODUCT, SOURCE FROM "
					+ "(SELECT ENTRY_DATE_TIME, LEAD_REF_NO, EIDA_NUMBER, PRODUCT_JOURNEY, DSA_PROMO_CODE, CUSTOMER_FULL_NAME, CUSTOMER_MOBILE_NO, CUSTOMER_EMAIL, CUSTOMER_ID, "
					+ "CUSTOMER_EMPLOYER_NAME, CUSTOMER_MONTHLY_INCOME, PREFERRED_LANGUAGE, STATUS, PRODUCT, SOURCE, RANK() OVER (ORDER BY ENTRY_DATE_TIME DESC) AS BATCH "
					+ "FROM  BPM_COP_LEAD_OPPORTUNITY ";*/
			//COP-1927 23/03/2023 Moksh
			String query = "SELECT ENTRY_DATE_TIME, LEAD_REF_NO, EIDA_NUMBER, PRODUCT_JOURNEY, DSA_PROMO_CODE, CUSTOMER_FULL_NAME, CUSTOMER_MOBILE_NO, CUSTOMER_EMAIL, CUSTOMER_ID, "
					+ "CUSTOMER_EMPLOYER_NAME, CUSTOMER_MONTHLY_INCOME, PREFERRED_LANGUAGE, STATUS, PRODUCT, SOURCE, PRODUCT_NAME FROM "
					+ "(SELECT ENTRY_DATE_TIME, LEAD_REF_NO, EIDA_NUMBER, PRODUCT_JOURNEY, DSA_PROMO_CODE, CUSTOMER_FULL_NAME, CUSTOMER_MOBILE_NO, CUSTOMER_EMAIL, CUSTOMER_ID, "
					+ "CUSTOMER_EMPLOYER_NAME, CUSTOMER_MONTHLY_INCOME, PREFERRED_LANGUAGE, STATUS, PRODUCT, SOURCE, PRODUCT_NAME, RANK() OVER (ORDER BY ENTRY_DATE_TIME DESC) AS BATCH "
					+ "FROM  BPM_COP_LEAD_OPPORTUNITY ";

			String whereClause = "";
			int batchFirst = 1;
			int batchLast = 1;
			String totalRecordQuery = "SELECT COUNT(1) AS TOTALCOUNT FROM BPM_COP_LEAD_OPPORTUNITY ";
			String totalCountWhere = "";

			if (attributeMap.isEmpty())
			{
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Complete query1 :" + query + " ORDER BY  ENTRY_DATE_TIME DESC");
				outputXML = APCallCreateXML.APSelect(query + " ORDER BY  ENTRY_DATE_TIME DESC");
				totalRecordSelect = APCallCreateXML.APSelect(totalRecordQuery );
			}
			else
			{
				String toDate = (String)attributeMap.get("TO_DATE");
				String fromDate = (String)attributeMap.get("FROM_DATE");
				int batchSize = Integer.parseInt((((String)attributeMap.get("BATCH_SIZE")).equals("")) ? "0" : (String)attributeMap.get("BATCH_SIZE"));
				int startFrom = Integer.parseInt((((String)attributeMap.get("START_FROM")).equals("")) ? "0" : (String)attributeMap.get("START_FROM"));
				String dsa = (String)attributeMap.get("DSA_CODE")!=null?(String)attributeMap.get("DSA_CODE").toUpperCase():"";
				String orderBy = (String)attributeMap.get("ORDER_BY");

				if ((batchSize != 0) && (startFrom > 0)) {
					batchLast = batchSize + startFrom;
					batchFirst = startFrom;
				}
				else if ((batchSize != 0) && (((startFrom == 0)))) {
					batchLast = batchSize;
					batchFirst = 0;
				}

				if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())) && (!(orderBy.isEmpty()))	&& (!(dsa.isEmpty())) && (batchSize != 0) && (startFrom != 0))
				{
					whereClause = "WHERE STATUS IN ('20','25') AND TRUNC(ENTRY_DATE_TIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY') AND UPPER(DSA_PROMO_CODE) = '" + dsa + "'" +
							"ORDER BY ENTRY_DATE_TIME " + orderBy + ") WHERE BATCH >= " + batchFirst + " AND BATCH < " + batchLast;
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE CLAUSE 1: " + whereClause);

					totalCountWhere = "WHERE STATUS IN ('20','25') AND TRUNC(ENTRY_DATE_TIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY') AND UPPER(DSA_PROMO_CODE) = '" + dsa + "'" +
							"ORDER BY ENTRY_DATE_TIME " + orderBy;
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 1: " + totalCountWhere);
				}
				else if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())) && (!(orderBy.isEmpty()))	&& (!(dsa.isEmpty())) && (batchSize == 0) && (startFrom == 1))
				{
					whereClause = "WHERE STATUS IN ('20','25') AND TRUNC(ENTRY_DATE_TIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY') AND UPPER(DSA_PROMO_CODE) = '" + dsa + "'" +
							"ORDER BY ENTRY_DATE_TIME " + orderBy;
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE CLAUSE 2: " + totalCountWhere);

					totalCountWhere = "WHERE STATUS IN ('20','25') AND TRUNC(ENTRY_DATE_TIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY') AND UPPER(DSA_PROMO_CODE) = '" + dsa + "'" +
							"ORDER BY ENTRY_DATE_TIME " + orderBy ;
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 2: " + totalCountWhere);
				}
				else if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())) && (!(orderBy.isEmpty()))	&& (!(dsa.isEmpty())) && (batchSize != 0) && (startFrom == 0))
				{
					whereClause = "WHERE STATUS IN ('20','25') AND TRUNC(ENTRY_DATE_TIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY') AND UPPER(DSA_PROMO_CODE) = '" + dsa + "'" +
							"ORDER BY ENTRY_DATE_TIME " + orderBy + ") WHERE BATCH >= " + batchFirst + " AND BATCH < " + batchLast;
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE CLAUSE 3: " + whereClause);

					totalCountWhere = "WHERE STATUS IN ('20','25') AND TRUNC(ENTRY_DATE_TIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') AND TO_DATE('" + toDate + "','DD-MM-YYYY')";
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 3: " + totalCountWhere);
				}
				else if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())) && (!(orderBy.isEmpty())) && (!(dsa.isEmpty())))
				{
					whereClause = "WHERE STATUS IN ('20','25') AND TRUNC(ENTRY_DATE_TIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY') AND UPPER(DSA_PROMO_CODE) = '" + dsa + "'" +
							"ORDER BY ENTRY_DATE_TIME " + orderBy + " " ;
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE CLAUSE 4: " + whereClause);

					totalCountWhere = "WHERE STATUS IN ('20','25') AND TRUNC(ENTRY_DATE_TIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY') AND UPPER(DSA_PROMO_CODE) = '" + dsa + "'";
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 4: " + totalCountWhere);
				}
				else if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())) && (!(orderBy.isEmpty())) && (batchSize != 0) && (startFrom != 0))
				{
					whereClause = "WHERE STATUS IN ('20','25') AND TRUNC(ENTRY_DATE_TIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY') ORDER BY ENTRY_DATE_TIME " + orderBy + ") " + 
							"WHERE BATCH >= " + batchFirst + " AND BATCH < " + batchLast;
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE 5: " + whereClause);

					totalCountWhere = "WHERE STATUS IN ('20','25') AND TRUNC(ENTRY_DATE_TIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY')" + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY')";
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 5: " + totalCountWhere);
				}
				else if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())) && (!(dsa.isEmpty())))
				{
					whereClause = "WHERE STATUS IN ('20','25') AND TRUNC(ENTRY_DATE_TIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY') AND UPPER(DSA_PROMO_CODE) = '" + dsa + "'";
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE CLAUSE 6:" + whereClause);

					totalCountWhere = "WHERE STATUS IN ('20','25') AND TRUNC(ENTRY_DATE_TIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') AND TO_DATE('" 
							+ toDate + "','DD-MM-YYYY') AND UPPER(DSA_PROMO_CODE) = '" + dsa + "'" ;
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 6: " + totalCountWhere);
				}
				else if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())))
				{
					whereClause = "WHERE STATUS IN ('20','25') AND TRUNC(ENTRY_DATE_TIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY'))";
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE CLAUSE 7:" + whereClause);

					totalCountWhere = "WHERE STATUS IN ('20','25') AND TRUNC(ENTRY_DATE_TIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY'))";
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 7: " + totalCountWhere);
				}
				else if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())) && (!(orderBy.isEmpty())) && (!(dsa.isEmpty())) && (batchSize != 0) && (startFrom != 0))
				{
					whereClause = "WHERE STATUS IN ('20','25') AND TRUNC(ENTRY_DATE_TIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY') AND UPPER(DSA_PROMO_CODE) = '" + dsa + "' ORDER BY " + 
							"ENTRY_DATE_TIME " + orderBy + ") WHERE BATCH >= " + batchFirst + " AND BATCH < " + batchLast;
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE 8: " + whereClause);

					totalCountWhere = "WHERE STATUS IN ('20','25') AND TRUNC(ENTRY_DATE_TIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY') AND UPPER(DSA_PROMO_CODE) = '" + dsa + "'";
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 8: " + totalCountWhere);
				}
				else if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())) && (!(orderBy.isEmpty())) && (batchSize != 0) && (startFrom != 0))
				{
					whereClause = "WHERE STATUS IN ('20','25') AND TRUNC(ENTRY_DATE_TIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY') ORDER BY ENTRY_DATE_TIME " + orderBy + ") WHERE BATCH >= " + batchFirst + " " + 
							"AND BATCH < " + batchLast;
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE 9: " + whereClause);

					totalCountWhere = "WHERE STATUS IN ('20','25') AND TRUNC(ENTRY_DATE_TIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY')";
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 9: " + totalCountWhere);
				}
				else if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())) && (!(orderBy.isEmpty())) && (batchSize != 0) && (startFrom == 0))
				{
					whereClause = "WHERE STATUS IN ('20','25') AND TRUNC(ENTRY_DATE_TIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY') ORDER BY ENTRY_DATE_TIME " + orderBy + ") WHERE BATCH >= " + batchFirst + " " + 
							"AND BATCH < " + batchLast;
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE 10: " + whereClause); 

					totalCountWhere = "WHERE STATUS IN ('20','25') AND TRUNC(ENTRY_DATE_TIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY')";
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 10: " + totalCountWhere);
				}
				else if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())) && (!(dsa.isEmpty())))
				{
					whereClause = "WHERE STATUS IN ('20','25') AND TRUNC(ENTRY_DATE_TIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY') AND UPPER(DSA_PROMO_CODE) = '" + dsa + "') ";
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE CLAUSE 11:" + whereClause);

					totalCountWhere = "WHERE STATUS IN ('20','25') AND TRUNC(ENTRY_DATE_TIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') AND TO_DATE('" + toDate + "','DD-MM-YYYY') " + 
							"AND UPPER(DSA_PROMO_CODE) = '" + dsa + "'";
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 11: " + totalCountWhere);
				}
				else if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())) && (!(orderBy.isEmpty())))
				{
					whereClause = "WHERE STATUS IN ('20','25') AND TRUNC(ENTRY_DATE_TIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY') ORDER BY ENTRY_DATE_TIME " + orderBy + ")";
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE CLAUSE 12:" + whereClause);

					totalCountWhere = "WHERE STATUS IN ('20','25') AND TRUNC(ENTRY_DATE_TIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY')";
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 12: " + totalCountWhere);
				}
				else if ((!(toDate.isEmpty())) && (!(fromDate.isEmpty())))
				{
					whereClause = "WHERE STATUS IN ('20','25') AND TRUNC(ENTRY_DATE_TIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY') ORDER BY ENTRY_DATE_TIME " + orderBy + ")";
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WHERE CLAUSE 13:" + whereClause);

					totalCountWhere = "WHERE STATUS IN ('20','25') AND TRUNC(ENTRY_DATE_TIME) BETWEEN TO_DATE('" + fromDate + "','DD-MM-YYYY') " + 
							"AND TO_DATE('" + toDate + "','DD-MM-YYYY')";
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD CLAUSE 13: " + totalCountWhere);
				}

				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Complete query2 :" + query + whereClause);
				outputXML = APCallCreateXML.APSelect(query + whereClause);
				totalRecordSelect = APCallCreateXML.APSelect(totalRecordQuery + totalCountWhere);
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "TOTAL RECORD Query: " + totalRecordSelect);
			}

			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			XMLParser xp2 = new XMLParser(totalRecordSelect);


			if (mainCode == 0) {
				int totalCount = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
				bulkAttributes = new BulkEnquiryAtributes[totalCount];
				if (totalCount > 0) {
					int start = xp.getStartIndex("Records", 0, 0);
					int deadEnd = xp.getEndIndex("Records", start, 0);
					int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
					int end = 0;
					for (int i = 0; i < noOfFields; ++i) {
						start = xp.getStartIndex("Record", end, 0);
						end = xp.getEndIndex("Record", start, 0);
						String leadRefNo = xp.getValueOf("LEAD_REF_NO", start, end);
						String mobileNumber = xp.getValueOf("CUSTOMER_MOBILE_NO", start, end);
						String customerName = xp.getValueOf("CUSTOMER_FULL_NAME", start, end);
						String customerID = xp.getValueOf("CUSTOMER_ID", start, end);
//						String productName = xp.getValueOf("PRODUCT", start, end); //COP-1927 23/03/2023 Moksh
						String productName = xp.getValueOf("PRODUCT_NAME", start, end);
						String statusCode = xp.getValueOf("STATUS", start, end);
//						String stageName = xp.getValueOf("STAGE_NAME", start, end);
						String submitDate = xp.getValueOf("ENTRY_DATE_TIME", start, end);
//						String comments = xp.getValueOf("ADD_COMMTS", start, end);
//						String segment = xp.getValueOf("CUSTOMER_SEGMENT", start, end);
//						String remarksDateTime = xp.getValueOf("REQUESTED_DATETIME", start, end);
						String source = xp.getValueOf("SOURCE", start, end);

						BulkEnquiryAtributes attr = new BulkEnquiryAtributes();
						attr.setWiName(leadRefNo);
						attr.setMobileNumber(mobileNumber);
						attr.setCustomerName(customerName);
						attr.setCustomerID(customerID);
						attr.setStatus(statusCode);
						attr.setSubmitDate(submitDate);
						attr.setProductName(productName);
						attr.setSubmitDate(submitDate);
//						attr.setCurrentWorkStep(stageName);
//						attr.setComments(comments);
						attr.setSegment(source);
//						attr.setRemarksDateTime(remarksDateTime);
						
						bulkAttributes[i] = attr;
					}
					String totalCountRecords = xp2.getValueOf("TOTALCOUNT");
					bulkResponse.setTOTALCOUNT(totalCountRecords);
					bulkResponse.setFETCHCOUNT(Integer.toString(noOfFields));
					bulkResponse.setStatusCode("0");
					bulkResponse.setStatusMessage("DATA FETCH SUCCESSFULLTY");
				}
				else {
					bulkResponse.setTOTALCOUNT(String.valueOf(0));
					bulkResponse.setFETCHCOUNT(String.valueOf(0));
					bulkResponse.setStatusCode("0");
					bulkResponse.setStatusMessage("NO DATA FOUND");
				}
			}
			BulkEnquiryApplicationAttributes[] attrDetails = new BulkEnquiryApplicationAttributes[1];
			BulkEnquiryApplicationAttributes bulkattr = new BulkEnquiryApplicationAttributes();
			bulkattr.setBulkEnquiryDetails(bulkAttributes);

			attrDetails[0] = bulkattr;
			bulkResponse.setApplicationAttributes(attrDetails);
		}
		catch (Exception e)
		{
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}
		return bulkResponse;
	}
}
