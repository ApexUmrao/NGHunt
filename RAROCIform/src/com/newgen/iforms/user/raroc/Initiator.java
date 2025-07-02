/*
 * By Mohit	
 * 04-11-2024	prod_04112024_1	API can be called more than once if Cust/Group Raroc is empty (as call was not made or got empty value.)-NOT REQD NOW.
 * 05-11-2024	prod_05112024	CustRIM numbers handling if user changes the RIM completely.
 */
package com.newgen.iforms.user.raroc;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.simple.JSONArray;
import com.newgen.iforms.EControl;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.iforms.user.config.Constants;
import com.newgen.iforms.user.raroc.bean.FacilitiesByLimitIdResponse;
import com.newgen.mvcbeans.model.WorkdeskModel;

import Jdts.Parser.XMLParser;

public class Initiator extends Common implements Constants, IFormServerEventHandler {

	boolean sOnClick = false;
	boolean bSubmit = true;

	public Initiator(IFormReference formObject) {

		super(formObject);
	}

	@Override // Added by Shivanshu
	public void beforeFormLoad(FormDef arg0, IFormReference formObject) {
		log.info("WorkItem Name:  " + formObject.getObjGeneralData().getM_strProcessInstanceId());
	}
	
	
	

	@Override
	public String executeCustomService(FormDef arg0, IFormReference arg1, String arg2, String arg3, String arg4) {
		return null;
	}

	@Override
	public JSONArray executeEvent(FormDef arg0, IFormReference arg1, String arg2, String arg3) {
		return null;
	}

	@Override
	public String executeServerEvent(IFormReference formObject, String controlName, String eventType, String data) {
		String retMsg = "";
		String msg = "";
		Common Common = new Common(formObject);
		try {
			retMsg = Common.getReturnMessage(true);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		log.info("25-09-2024 executeServerEvent:::INSIDE ,controlName=" + controlName + ", eventType=" + eventType
				+ ", data=" + data + ".");

		try {

			if (eventType.equalsIgnoreCase(EVENT_TYPE_LOAD)) {
				updateStageID(formObject);
				getRimNumber(formObject);
				setParentData();
				CustomerValidation(formObject);
				setHeaderData(formObject);
				String parentWorkstep = formObject.getValue("CURRENT_WORKSTEPNAME").toString();
				log.info("  parentWorkstep: INSIDE--->>>" + parentWorkstep);
				String decisionParent = formObject.getValue("CA_DECISION").toString();
				log.info("decisionParent  : INSIDE--->>>" + decisionParent);
				if ((parentWorkstep.equalsIgnoreCase("CAD_Doc_Assign") || parentWorkstep.equalsIgnoreCase("Legal_Maker")
						|| parentWorkstep.equalsIgnoreCase("CAD_Doc_Verify")
						|| parentWorkstep.equalsIgnoreCase("CAD_CCU_Limit_Load")
						|| parentWorkstep.equalsIgnoreCase("CAD_CCU_Limit_Load_Review")
						|| parentWorkstep.equalsIgnoreCase("Exit")
						|| parentWorkstep.equalsIgnoreCase("CEO_Proposal_Review")
						|| parentWorkstep.equalsIgnoreCase("CAD_Doc_Print")
						|| parentWorkstep.equalsIgnoreCase("CAD_Doc_Prep") || parentWorkstep.equalsIgnoreCase("Board")
						|| parentWorkstep.equalsIgnoreCase("CAD_Doc_Review")
						|| parentWorkstep.equalsIgnoreCase("CAD_Doc_Approve")
						|| parentWorkstep.equalsIgnoreCase("Legal_Checker") || parentWorkstep.equalsIgnoreCase("TFS")
						|| parentWorkstep.equalsIgnoreCase("CAD_Def_waiver")
						|| parentWorkstep.equalsIgnoreCase("Query_Resolution")
						|| parentWorkstep.equalsIgnoreCase("Docs_Upload_Scan")
						|| parentWorkstep.equalsIgnoreCase("Hold") || parentWorkstep.equalsIgnoreCase("Query")
						|| parentWorkstep.equalsIgnoreCase("Discard")
						|| parentWorkstep.equalsIgnoreCase("Board_Discard")
						|| parentWorkstep.equalsIgnoreCase("Email_Submit")
						|| parentWorkstep.equalsIgnoreCase("Email_SendBack")
						|| parentWorkstep.equalsIgnoreCase("System stage")
						|| parentWorkstep.equalsIgnoreCase("Discard_CC")
						|| parentWorkstep.equalsIgnoreCase("CC_Discard")
						|| parentWorkstep.equalsIgnoreCase("Query_Resolution_Hold")
						|| parentWorkstep.equalsIgnoreCase("Recovery"))
						&& (decisionParent.equalsIgnoreCase("Approve"))) {
					log.info(" CAD_Doc_Assign : INSIDE--->>>");
					// getUpdateData(RAROC_FACILITY_DETAILS_TABLE,RAROC_FACILITY_APPROVE_UPDATE);//
					// by mohit 26-07-2024 should not be in load function
				}
				if ((parentWorkstep.equalsIgnoreCase("CAD_Doc_Assign") || parentWorkstep.equalsIgnoreCase("Legal_Maker")
						|| parentWorkstep.equalsIgnoreCase("CAD_Doc_Verify")
						|| parentWorkstep.equalsIgnoreCase("CAD_CCU_Limit_Load")
						|| parentWorkstep.equalsIgnoreCase("CAD_CCU_Limit_Load_Review")
						|| parentWorkstep.equalsIgnoreCase("Exit")
						|| parentWorkstep.equalsIgnoreCase("CEO_Proposal_Review")
						|| parentWorkstep.equalsIgnoreCase("CAD_Doc_Print")
						|| parentWorkstep.equalsIgnoreCase("CAD_Doc_Prep") || parentWorkstep.equalsIgnoreCase("Board")
						|| parentWorkstep.equalsIgnoreCase("CAD_Doc_Review")
						|| parentWorkstep.equalsIgnoreCase("CAD_Doc_Approve")
						|| parentWorkstep.equalsIgnoreCase("Legal_Checker") || parentWorkstep.equalsIgnoreCase("TFS")
						|| parentWorkstep.equalsIgnoreCase("CAD_Def_waiver")
						|| parentWorkstep.equalsIgnoreCase("Query_Resolution")
						|| parentWorkstep.equalsIgnoreCase("Docs_Upload_Scan")
						|| parentWorkstep.equalsIgnoreCase("Hold") || parentWorkstep.equalsIgnoreCase("Query")
						|| parentWorkstep.equalsIgnoreCase("Discard")
						|| parentWorkstep.equalsIgnoreCase("Board_Discard")
						|| parentWorkstep.equalsIgnoreCase("Email_Submit")
						|| parentWorkstep.equalsIgnoreCase("Email_SendBack")
						|| parentWorkstep.equalsIgnoreCase("System stage")
						|| parentWorkstep.equalsIgnoreCase("Discard_CC")
						|| parentWorkstep.equalsIgnoreCase("CC_Discard")
						|| parentWorkstep.equalsIgnoreCase("Query_Resolution_Hold")
						|| parentWorkstep.equalsIgnoreCase("Recovery"))) {

					// setCFMSensToApprov(); // by reshank on 05-09-24 sens to approv data from
					// database

				}
				if (parentWorkstep.equalsIgnoreCase("CA_Review_Analysis")
						|| parentWorkstep.equalsIgnoreCase("HOCC_Assign")
						|| parentWorkstep.equalsIgnoreCase("CA_Raise Queries to RM")
						|| parentWorkstep.equalsIgnoreCase("HOCC_Recommendation")
						|| parentWorkstep.equalsIgnoreCase("CCO_Recommendation")
						|| parentWorkstep.equalsIgnoreCase("CSC_MRA_Prep") || parentWorkstep.equalsIgnoreCase("CC")
						|| parentWorkstep.equalsIgnoreCase("CA_FRS_Prep")
						|| parentWorkstep.equalsIgnoreCase("HOCC_FRS_Review") || parentWorkstep.equalsIgnoreCase("BCIC")
						|| parentWorkstep.equalsIgnoreCase("HOCC_Def_Waive")
						|| parentWorkstep.equalsIgnoreCase("CCO_Def_Waiver")
						|| parentWorkstep.equalsIgnoreCase("CA_Def_Waiver")
						|| parentWorkstep.equalsIgnoreCase("CC_Def_Waiver") || parentWorkstep.equalsIgnoreCase("CSU")
						|| parentWorkstep.equalsIgnoreCase("Credit")) {
					log.info("Inside if current workstep on Load");
					// start by mohit 29-07-2024
					setCFMPropDisable(formObject);
					// end by mohit 29-07-2024
					// getUpdateData(RAROC_FACILITY_DETAILS_TABLE,RAROC_FACILITY_SENSITIZED_UPDATE);//
					// by mohit 26-07-2024 should not be in load function
				}

				/*
				 * if (!parentWorkstep.equalsIgnoreCase("Initiate_Proposal") &&
				 * !parentWorkstep.equalsIgnoreCase("RM_Proposal_Review") &&
				 * !parentWorkstep.equalsIgnoreCase("RO_Proposal_Modify") &&
				 * !parentWorkstep.equalsIgnoreCase("UH_TL_Proposal_Review") &&
				 * !parentWorkstep.equalsIgnoreCase("CBO_Proposal_Review") &&
				 * !parentWorkstep.equalsIgnoreCase("RM_Query Resolutions") &&
				 * !parentWorkstep.equalsIgnoreCase("RM_Doc_Execution") &&
				 * !parentWorkstep.equalsIgnoreCase("UH_TL_Def_Waiver") &&
				 * !parentWorkstep.equalsIgnoreCase("Head_Corporate_Banking") &&
				 * !parentWorkstep.equalsIgnoreCase("CA_Raise Queries to RM") &&
				 * !parentWorkstep.equalsIgnoreCase("HOCC_Recommendation") &&
				 * !parentWorkstep.equalsIgnoreCase("CCO_Recommendation") &&
				 * !parentWorkstep.equalsIgnoreCase("CSC_MRA_Prep") &&
				 * !parentWorkstep.equalsIgnoreCase("CC") &&
				 * !parentWorkstep.equalsIgnoreCase("CA_FRS_Prep") &&
				 * !parentWorkstep.equalsIgnoreCase("HOCC_FRS_Review") &&
				 * !parentWorkstep.equalsIgnoreCase("BCIC") &&
				 * !parentWorkstep.equalsIgnoreCase("HOCC_Def_Waive") &&
				 * !parentWorkstep.equalsIgnoreCase("CCO_Def_Waiver") &&
				 * !parentWorkstep.equalsIgnoreCase("CA_Def_Waiver") &&
				 * !parentWorkstep.equalsIgnoreCase("CC_Def_Waiver") &&
				 * !parentWorkstep.equalsIgnoreCase("CSU") &&
				 * !parentWorkstep.equalsIgnoreCase("Credit") &&
				 * !parentWorkstep.equalsIgnoreCase("CA_Review_Analysis") &&
				 * !parentWorkstep.equalsIgnoreCase("HOCC_Assign")) { readOnlyApply(formObject);
				 * }
				 */
				log.info("executeServerEvent : " + "EVENT_TYPE_LOAD");
			}

			if (eventType.equalsIgnoreCase(EVENT_TYPE_CHANGE)) {
				if (Constants.INDEX_TENOR_CHANGE.equalsIgnoreCase(controlName)) {
					log.info("INDEX_TENOR_CHANGE >>" + EVENT_TYPE_CHANGE + "....controlName" + controlName);
					indexTenorChange(formObject);
					// indexTenorChangeSens(formObject);
				}
				// Shikha 26-07-2024
				else if (Constants.INDEX_TENOR_CHANGE_SENS.equalsIgnoreCase(controlName)) {
					log.info("INDEX_TENOR_CHANGE_SENS >>" + EVENT_TYPE_CHANGE + "....controlName" + controlName);
					indexTenorChangeSens(formObject);
				}
				// Shikha 26-07-2024
				log.info("change");
				matchingRim(formObject);

			}

			if (eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)) {

				log.info("Inside on tab click ######");
				// added suraj
				if (Constants.CUSTOMER_REALISED_DETAILS.equalsIgnoreCase(controlName)) {
					String outputXML = getFlagValue(formObject);
					log.info("Inside on reaCall====>" + outputXML);
					String custReaCall = "";
					String groupReaCall = "";
					String facilityReaCall = "";
					String custMsg = "";
					String groupMsg = "";
					String facilityMsg = "";
					matchingRim(formObject);
					/*
					 * if (!outputXML.equalsIgnoreCase(null)) { XMLParser xmlParser = new
					 * XMLParser(outputXML); custReaCall =
					 * xmlParser.getValueOf("CUSTOMER_REALISED_CALL"); groupReaCall =
					 * xmlParser.getValueOf("GROUP_REALISED_CALL"); facilityReaCall =
					 * xmlParser.getValueOf("FACILITY_REALISED_CALL"); }
					 */// prod_04112024_1
					log.info(" custReaCall>>>>" + custReaCall + " groupReaCall>>>>" + groupReaCall
							+ " facilityReaCall>>>>" + facilityReaCall);
					Initiator Initiator = new Initiator(formObject);
					/*String sQueryfacilityReaCall="SELECT CASE WHEN COUNT(1) >0 THEN 'TRUE' ELSE 'FALSE' END 'FacRaroc' FROM NG_RAROC_FACILITY_DETAILS WHERE WI_NAME ='"+formObject.getObjGeneralData().getM_strProcessInstanceId()+"' AND FACILITY_RAROC_REALISED IS NULL"; 
					List<List<String>> sQueryfacilityReaCall_data = formObject.getDataFromDB(sQueryfacilityReaCall);
					log.info(" sQueryfacilityReaCall$$$$$ " + sQueryfacilityReaCall);
					log.info(" sQueryfacilityReaCall_data$$$$$ " + sQueryfacilityReaCall_data);
					facilityReaCall=sQueryfacilityReaCall_data.get(0).get(0);
					log.info(" facilityReaCall$$$$$ " + facilityReaCall);*/
					if (!custReaCall.equalsIgnoreCase("Y")) {
					//if(	formObject.getValue("Q_NG_RAROC_CUSTOMER_Customer_RAROC_Realized").equals("")) {
						log.info("Inside customer raroc is empty...");
						msg = Initiator.customerRealisedDetails(formObject);
						custMsg = msg;
						log.info("customerRealisedDetails msg return" + custMsg);
						// custMsg = "Customer Realized : " + msg;
					}
					if (!groupReaCall.equalsIgnoreCase("Y")) {
					//if(	formObject.getValue("Q_NG_RAROC_GROUP_Group_Realized").equals("")) {
						log.info("Inside group raroc is empty...");
						msg = Initiator.groupRealisedDetails(formObject);
						groupMsg = msg;
						log.info("groupRealisedDetails msg return" + groupMsg);
						// groupMsg = "Group Realized : " + msg;
					}
					if (!facilityReaCall.equalsIgnoreCase("Y")) {
					//if (facilityReaCall.equalsIgnoreCase("TRUE")) {
						log.info("Inside facility raroc is empty...");
						msg = Initiator.facilityRealisedDetails(formObject);
						facilityMsg = msg;
						log.info("facilityRealisedDetails msg return" + facilityMsg);
						// facilityMsg = "Facility Realized : " + msg;
					}
					// msg =custMsg;
					// start edit by mohit 30-06-2024 change status(Success to Partial Success) if
					// rarocSize is zero
					if (custMsg.equalsIgnoreCase("Partial Success") || groupMsg.equalsIgnoreCase("Partial Success")
							|| facilityMsg.equalsIgnoreCase("Partial Success")) {
						msg = "Partial Success";
					}
					// end edit by mohit 30-06-2024
					retMsg = getReturnMessage(true, controlName, msg);
					log.info("retMsg of click customer/group /facility realised..." + retMsg);
				}
				if (Constants.PROJECTED_DETAILS.equalsIgnoreCase(controlName)) {
					String projectedCall = "";
					String query = "SELECT PROJECTED_FACILITY_CALL FROM ALM_RAROC_EXT_TABLE WHERE WI_NAME='"
							+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "'";
					String outputXML = getDataFromDBWithColumns(query, "PROJECTED_FACILITY_CALL");
					XMLParser xmlParser = new XMLParser(outputXML);
					log.info("xmlParser" + xmlParser);
					int totalRetrieved = Integer.parseInt(xmlParser.getValueOf(Constants.TOTAL_RETRIEVED));
					if (totalRetrieved == 1) {
						projectedCall = xmlParser.getValueOf("PROJECTED_FACILITY_CALL");
					}
					// if (!projectedCall.equalsIgnoreCase("Y")) {
					Initiator Initiator = new Initiator(formObject);
					String stageId = formObject.getValue("CURRENT_WORKSTEPNAME").toString();
					if (stageId.equalsIgnoreCase("Initiate_Proposal") || stageId.equalsIgnoreCase("RM_Proposal_Review")
							|| stageId.equalsIgnoreCase("RO_Proposal_Modify")
							|| stageId.equalsIgnoreCase("UH_TL_Proposal_Review")
							|| stageId.equalsIgnoreCase("CBO_Proposal_Review")
							|| stageId.equalsIgnoreCase("RM_Query Resolutions")
							|| stageId.equalsIgnoreCase("RM_Doc_Execution")
							|| stageId.equalsIgnoreCase("UH_TL_Def_Waiver")
							|| stageId.equalsIgnoreCase("Head_Corporate_Banking")
							|| stageId.equalsIgnoreCase("CA_Raise Queries to RM")
							|| stageId.equalsIgnoreCase("HOCC_Recommendation")
							|| stageId.equalsIgnoreCase("CCO_Recommendation")
							|| stageId.equalsIgnoreCase("CSC_MRA_Prep") || stageId.equalsIgnoreCase("CC")
							|| stageId.equalsIgnoreCase("CA_FRS_Prep") || stageId.equalsIgnoreCase("HOCC_FRS_Review")
							|| stageId.equalsIgnoreCase("BCIC") || stageId.equalsIgnoreCase("HOCC_Def_Waive")
							|| stageId.equalsIgnoreCase("CCO_Def_Waiver") || stageId.equalsIgnoreCase("CA_Def_Waiver")
							|| stageId.equalsIgnoreCase("CC_Def_Waiver") || stageId.equalsIgnoreCase("CSU")
							|| stageId.equalsIgnoreCase("Credit") || stageId.equalsIgnoreCase("CA_Review_Analysis")
							|| stageId.equalsIgnoreCase("CA_HOCC_Analysis")) {
						log.info("Inside if condition to exec execCustomerProjected...");
						msg = Initiator.execCustomerProjected(formObject);
					} else if (stageId.equalsIgnoreCase("CA_Raise Queries to RM")
							|| stageId.equalsIgnoreCase("HOCC_Recommendation")
							|| stageId.equalsIgnoreCase("CCO_Recommendation")
							|| stageId.equalsIgnoreCase("CSC_MRA_Prep") || stageId.equalsIgnoreCase("CC")
							|| stageId.equalsIgnoreCase("CA_FRS_Prep") || stageId.equalsIgnoreCase("HOCC_FRS_Review")
							|| stageId.equalsIgnoreCase("BCIC") || stageId.equalsIgnoreCase("HOCC_Def_Waive")
							|| stageId.equalsIgnoreCase("CCO_Def_Waiver") || stageId.equalsIgnoreCase("CA_Def_Waiver")
							|| stageId.equalsIgnoreCase("CC_Def_Waiver") || stageId.equalsIgnoreCase("CSU")
							|| stageId.equalsIgnoreCase("Credit") || stageId.equalsIgnoreCase("CA_Review_Analysis")
							|| stageId.equalsIgnoreCase("HOCC_Assign")) {
						log.info("Inside if condition to exec execCustomerProjectedSens++++");
						msg = Initiator.execCustomerProjectedSens(formObject);
					}
					log.info("msg++++++" + msg);
					// }
					retMsg = getReturnMessage(true, controlName, msg);
					log.info("retMsg++++++" + retMsg);

					// Start changes by shikha 13-09-2024
					String parentWorkstep = formObject.getValue("CURRENT_WORKSTEPNAME").toString();
					if (parentWorkstep.equalsIgnoreCase("CA_Review_Analysis")
							|| parentWorkstep.equalsIgnoreCase("HOCC_Assign")
							|| parentWorkstep.equalsIgnoreCase("CA_Raise Queries to RM")
							|| parentWorkstep.equalsIgnoreCase("HOCC_Recommendation")
							|| parentWorkstep.equalsIgnoreCase("CCO_Recommendation")
							|| parentWorkstep.equalsIgnoreCase("CSC_MRA_Prep") || parentWorkstep.equalsIgnoreCase("CC")
							|| parentWorkstep.equalsIgnoreCase("CA_FRS_Prep")
							|| parentWorkstep.equalsIgnoreCase("HOCC_FRS_Review")
							|| parentWorkstep.equalsIgnoreCase("BCIC")
							|| parentWorkstep.equalsIgnoreCase("HOCC_Def_Waive")
							|| parentWorkstep.equalsIgnoreCase("CCO_Def_Waiver")
							|| parentWorkstep.equalsIgnoreCase("CA_Def_Waiver")
							|| parentWorkstep.equalsIgnoreCase("CC_Def_Waiver")
							|| parentWorkstep.equalsIgnoreCase("CSU") || parentWorkstep.equalsIgnoreCase("Credit")) {

						getUpdateData(RAROC_COLLATERAL_FACILITY_MAP, CFM_APPROVE_UPDATE); // COPY CFM FROM SENS to
																							// approved db to db ADDED
																							// BY Shikha 13-09-2024
						log.info("Inside RAROC_COLLATERAL_FACILITY_MAP $$$$$$");
					}
					// End changes by shikha 13-09-2024
				}
				// end
				// start edit by mohit 11-09-2024
				else if (controlName.equalsIgnoreCase(TAB_GROUP)) {
					log.info("Inside TAB_GROUP click event!!!!");
					updateDataInPreAppGroup();
					// if sens stage_ids
					String stageId = formObject.getValue("CURRENT_WORKSTEPNAME").toString();

					if (stageId.equalsIgnoreCase("CA_Raise Queries to RM")
							|| stageId.equalsIgnoreCase("HOCC_Recommendation")
							|| stageId.equalsIgnoreCase("CCO_Recommendation")
							|| stageId.equalsIgnoreCase("CSC_MRA_Prep") || stageId.equalsIgnoreCase("CC")
							|| stageId.equalsIgnoreCase("CA_FRS_Prep") || stageId.equalsIgnoreCase("HOCC_FRS_Review")
							|| stageId.equalsIgnoreCase("BCIC") || stageId.equalsIgnoreCase("HOCC_Def_Waive")
							|| stageId.equalsIgnoreCase("CCO_Def_Waiver") || stageId.equalsIgnoreCase("CA_Def_Waiver")
							|| stageId.equalsIgnoreCase("CC_Def_Waiver") || stageId.equalsIgnoreCase("CSU")
							|| stageId.equalsIgnoreCase("Credit") || stageId.equalsIgnoreCase("CA_Review_Analysis")
							|| stageId.equalsIgnoreCase("HOCC_Assign")) {
						log.info("Inside TAB_GROUP $$$$$$" + stageId);
						updateDataInGroupApproved();
						// updateDataInPreAppGroup();
					}
				}
				// end edit by mohit 11-09-2024
				else if (controlName.equalsIgnoreCase(TAB_CUSTOMER)) {

					String id = formObject.getValue(LEAD_REF_NO).toString();
					String queryApprov = "Select TOP 1 " + RAROC_CUSTOMER_APPROVED_COLUMN + " NOLOCK from "
							+ RAROC_CUSTOMER_TABLE
							+ " where wi_name= (SELECT TOP 1 a.wi_name FROM ALM_RAROC_EXT_TABLE a JOIN ALM_CA_EXT_TABLE b  ON a.ca_wi_name = b.WI_NAME  WHERE a.LEAD_REF_NO = '"
							+ id + "' and a.wi_name != '" + formObject.getObjGeneralData().getM_strProcessInstanceId()
							+ "' AND b.current_workstepname ='Archive' ORDER BY a.WI_Name DESC)";

					log.info("TAB_CUSTOMER : queryApprov : 18-09-2024" + queryApprov);
					List<List<String>> sOutput2 = formObject.getDataFromDB(queryApprov);
					if (sOutput2 != null && !sOutput2.isEmpty()) {
						formObject.setValue(RAROC_PREV_APPROV, sOutput2.get(0).get(0));
						formObject.setValue(BUSINESS_SEGMENT_PREV_APPROV, sOutput2.get(0).get(1));
						formObject.setValue(INDUSTRY_SEGEMENT_PREV_APPROV, sOutput2.get(0).get(2));
						formObject.setValue(IFRS_STAGING_PREV_APPROV, sOutput2.get(0).get(3));
						formObject.setValue(COUNTERPARTY_TYPE_PREV_APPROV, sOutput2.get(0).get(4));
						formObject.setValue(INCOME_CASA_PREV_APPROV, sOutput2.get(0).get(5));
						formObject.setValue(EXPORT_INCOME_PREV_APPROV, sOutput2.get(0).get(6));
						formObject.setValue(FX_INCOME_PREV_APPROV, sOutput2.get(0).get(7));
						formObject.setValue(OTHER_INCOME_PREV_APPROV, sOutput2.get(0).get(8));
						formObject.setValue(FEE_INCM_AED_PREVIOUSLY_APPR, sOutput2.get(0).get(9));
						formObject.setValue(CROSS_SELL_INCM_PREVIOUSLY_APPR, sOutput2.get(0).get(10));
						formObject.setValue(REALIZED_RAROC_PREVIOUSLY_APPR, sOutput2.get(0).get(11));// not being used
						formObject.setValue(CUSTOMER_NAME_PREVIOUSLY_APPR, sOutput2.get(0).get(12));
						formObject.setValue(CUST_INTRL_RATING_PREVIOUSLY_APPR, sOutput2.get(0).get(13));
						formObject.setValue(TOTAL_FUNDED_AED_PREVIOUSLY_APPR, sOutput2.get(0).get(14));
						formObject.setValue(TOTAL_NONFUNDED_AED_PREVIOUSLY_APPR, sOutput2.get(0).get(15));
						formObject.setValue(CUSTOMER_COUNTRY_PREVIOUSLY_APPR, sOutput2.get(0).get(16));
						formObject.setValue(CUSTOMER_ID_PREVIOUSLY_APPR, sOutput2.get(0).get(17));// not being used
						// formObject.setValue(FEE_INCM_AED_PREVIOUSLY_APPR, sOutput2.get(0).get(18));
						formObject.setValue(INTERNAL_RATING_PREVIOUSLY_APPR, sOutput2.get(0).get(18));
						formObject.setValue(EXTERNAL_RATING_PREVIOUSLY_APPR, sOutput2.get(0).get(19));
						formObject.setValue(SALES_TURNOVER_PREVIOUSLY_APPR, sOutput2.get(0).get(20));
						formObject.setValue(EXTERNAL_CODE_PREVIOUSLY_APPR, sOutput2.get(0).get(21));
					}

					retMsg = getReturnMessage(true, controlName, "");
				} else if (controlName.equalsIgnoreCase(TAB_FACILITY)) {

					String lead_ref_no = formObject.getValue(LEAD_REF_NO).toString();

					String Prev_Wi_Name = "SELECT TOP 1 ca_wi_name FROM ALM_RAROC_EXT_TABLE NOLOCK WHERE lead_Ref_No = '"
							+ lead_ref_no + "' and wi_name != '"
							+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "' ORDER BY WI_Name DESC";

					String Wi_Name = "SELECT  WI_NAME FROM ALM_RAROC_EXT_TABLE WHERE CA_WI_NAME = (" + Prev_Wi_Name
							+ ")";

					String Raroc_Facility_ID = "SELECT facility_id FROM NG_RAROC_FACILITY_DETAILS WHERE WI_NAME = ("
							+ Wi_Name + ")";

					log.info("Facilitytab : " + " queryApprov :" + Raroc_Facility_ID);
					List<List<String>> sOutput3 = formObject.getDataFromDB(Raroc_Facility_ID);

					for (int i = 0; i < sOutput3.size(); i++) {
						String Facility_IDs = sOutput3.get(i).get(0);

						// extra
						String Approved = "SELECT " + RAROC_FACILITY_APPROVE_COLUMN + " FROM "
								+ RAROC_FACILITY_DETAILS_TABLE + " WHERE FACILITY_ID = '" + Facility_IDs + "'";

						log.info("Facilitytab : " + " ApprovedQuery :" + Approved);

						List<List<String>> Approved_Data = formObject.getDataFromDB(Approved);
						for (int j = 0; j < Approved_Data.size(); j++) {

							String[] ColumnArray = RAROC_FACILITY_PREV_APPR_COLUMN.split(",");
							StringBuilder sb = new StringBuilder();
							for (int f = 0; f < ColumnArray.length; f++) {

								sb.append(ColumnArray[f] + "= '" + Approved_Data.get(0).get(f) + "',");

							}

							String UpdateData = sb.toString().substring(0, sb.length() - 1);

							log.info("Currency previously Appr" + UpdateData);

							String PrevAppr = "UPDATE " + RAROC_FACILITY_DETAILS_TABLE + " SET " + UpdateData
									+ " where FACILITY_ID = '" + Facility_IDs + "'";

							log.info("Facilitytab : PrevApprData 18-09-2024:" + PrevAppr);

						}

						// extra
					}

					retMsg = getReturnMessage(true, controlName, "");

				}
//				else if (controlName.equalsIgnoreCase(COMMENT_HISTORY)) {
//					log.info("COMMENT_HISTORY inside : ");
//
//					String userName = formObject.getValue("USER_ID").toString();
//					log.info("COMMENT_HISTORY inside1: "+ userName);
//
////					String userName = formObject.getValue(USER_NAME).toString();
//
//					/*SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_SQL);
//>>>>>>> .r275
//					LocalDate dates = LocalDate.now();
//<<<<<<< .mine
//					String dateString = date.toString();
//||||||| .r274
//=======
//					String requestDate = simpleDateFormat.format(dates);*/
//					
//					//log.info("requestDate="+requestDate);
//				
////					String userName = formObject.getValue("USER_ID").toString();
//					LocalDate dates = LocalDate.now();
//					log.info("userName.."+userName);
//					log.info("dates.."+dates);
//
//					JSONArray jsonArray = new JSONArray();
//					JSONObject jsonObject = new JSONObject();
//					jsonObject.put("UserName", userName);
//					log.info("COMMENT_HISTORY inside2: "+ userName);
//					jsonObject.put("Dates", dates);
//					jsonArray.add(jsonObject);
//					formObject.addDataToGrid("Comment_History", jsonArray);
//					log.info("jsonArray :" + jsonArray);
//				
//				}
				else if (controlName.equalsIgnoreCase(ROW_CLICK)) {

					log.info("20-09-2024 2222 ExecuteServerEvent :  data :" + data);
					String facilityID = data;

					// START edit by mohit 05-08-2024 to populate prev_approved fields based on
					// customer no and facility_ids
					populatePreApprovedFields(facilityID);
					String UpdateQuery = "UPDATE NG_RAROC_FACILITY_DETAILS SET INDEX_TENOR_UNIT_PROPOSED = 'M' , INDEX_TENOR_UNIT_SENSITIZED = 'M'"
							+ " where wi_name = '" + formObject.getObjGeneralData().getM_strProcessInstanceId()
							+ "' and facility_id='" + facilityID + "'";
					log.info(" INDEX_TENOR_UNIT_PROPOSED=M..... " + UpdateQuery);
					int output = formObject.saveDataInDB(UpdateQuery.toString());
					log.info(" output : INDEX_TENOR_UNIT_PROPOSED=M: " + output);

					// Start edit by shikha 12-09-2024
					String UpdateQueryTenor = "UPDATE NG_RAROC_FACILITY_DETAILS SET Tenor_Unit_Previously_Appr = 'Months' , Tenor_unit_Proposed = 'Months' ,Tenor_unit_Realized = 'Months', Tenor_unit_Sensitized = 'Months', Tenor_unit_Approved = 'Months'"
							+ " where wi_name = '" + formObject.getObjGeneralData().getM_strProcessInstanceId()
							+ "' and facility_id='" + facilityID + "'";
					log.info(" Tenor_unit_Proposed$$$$$ " + UpdateQueryTenor);
					int outputTenor = formObject.saveDataInDB(UpdateQueryTenor.toString());
					log.info(" output : Tenor_unit_Proposed$$$$$ " + outputTenor);
					// edit by shikha 12-09-2024

					String[] prevAppFieldValue = new String[] { CURRENCY_PREV_APPR, FACILITY_AMOUNT_PREV_APPR,
							TENOR_PREV_APPR, TENOR_UNIT_PREV_APPR, EXPIRY_DATE_PREV_APPR, CASH_MARGIN_PREV_APPR,
							OUTSTANDING_AMOUNT_PREV_APPR, PRICING_PREV_APPR, INDEX_PREV_APPR, INDEX_RATE_PREV_APPR,
							MARGIN_COMMISION_PREV_APPR, VALUE_PREV_APPR, MINIMUM_PREV_APPR, MAXIMUM_PREV_APPR,
							EXP_1_YEAR_RATE_PREV_APPR, MARKETING_RISK_PREV_APPR, COUNTERPARTY_INTERNAL_PREV_APPR,
							COUNTERPARTY_TYPE_PREV_APPR, REPRICING_FREQ_PREV_APPR, SPECIAL_COST_PREV_APPR,
							REPAYMENT_TYPE_PREV_APPR, MORATORIUM_PERIOD_PREV_APPR, REPAYMENT_FREQUENCY_PREV_APPR,
							CREDIT_LIMIT_PREV_APPR, EQV_AED_AMOUNT_PREV_APPR, ECL_PREV_APPR, RISK_CAPITAL_PREV_APPR,
							RISK_RETURN_PREV_APPR, FACILITY_RAROC_PREV_APPR, AVG_UTILISED_PREV_APPR,
							REMAINING_TERM_IN_MONTH_PREV_APPR, FTP_PREV_APPR, INTEREST_RATE_PREV_APPR,
							COLLATRAL_TYPE_PREV_APPR, COLLATRAL_NAME_PREV_APPR, COLLATRAL_AMOUNT_PREV_APPR,
							COLLATRAL_CURRENCY_PREV_APPR, COLLATRAL_LIEN_AMOUNT_PREV_APPR,
							COLLATRAL_LIEN_INTEREST_PREV_APPR, COLLATRAL_LIEN_TENURE_PREV_APPR,
							BORROWER_RATING_PREV_APPR, COMMITMENT_FEE_PREV_APPR, UPFRONT_FEE_PREV_APPR,
							COLLATERAL_NUMBER_PREVIOUSLY_APPR, ALLOCATION_PERCENTAGE_PREVIOUSLY_APPR,
							INDEX_KEY_PRE_APPROVED, INDEX_TENOR_PRE_APPROVED, INDEX_TENOR_UNIT_PRE_APPROVED,
							UTILIZATION_PREV_APPR, FTP_OVERRIDE_PRE_APPROVED };

					setFacilityData(RAROC_FACILITY_PREV_APPR_COLUMN, RAROC_FACILITY_DETAILS_TABLE, facilityID,
							prevAppFieldValue);// [db to form]
					// END edit by mohit 05-08-2024 to populate prev_approved fields based on
					// customer no and facility_ids
					repaymentTypeBulletProp(facilityID);// 06-09-2024 by mohit

					// change by reshank on 26-07-24
					String[] fieldValue = new String[] { FACILITY_TREASURY, FACILITY_TYPE, FACILITY_NAME, PURP_FACILITY,
							FACILITY_SENIOR, FACILITY_COMMITMENT_NO, FACILITY_EXPECTED, FACILITY_MAIN_LIMIT,
							FACILITY_GENERAL, FACILITY_PROJECT, FACILITY_COMIITED, FACILITY_REVOLVING, FACILITY_ID,
							MAIN_SUB_LIMIT, CURRENCY_REALIZED, CURRENCY_PROPOSED, FACILITY_AMOUNT_REALIZED,
							FACILITY_AMOUNT_PROPOSED, EQV_AED_AMOUNT_REALIZED, EQV_AED_AMOUNT_PROPOSED, TENOR_REALIZED,
							TENOR_PROPOSED, TENOR_UNIT_REALIZED, TENOR_UNIT_PROPOSED, EXPIRY_DATE_REALIZED,
							EXPIRY_DATE_PROPOSED, CASH_MARGIN_REALIZED, CASH_MARGIN_PROPOSED,
							OUTSTANDING_AMOUNT_REALIZED, OUTSTANDING_AMOUNT_PROPOSED, PRICING_REALIZED,
							PRICING_PROPOSED, INDEX_REALIZED, INDEX_PROPOSED, INDEX_RATE_REALIZED, INDEX_RATE_PROPOSED,
							MARGIN_COMMISION_REALIZED, MARGIN_COMMISION_PROPOSED, VALUE_REALIZED, VALUE_PROPOSED,
							MINIMUM_REALIZED, MINIMUM_PROPOSED, MAXIMUM_REALIZED, MAXIMUM_PROPOSED, ECL_REALISED,
							ECL_PROPOSED, RISK_CAPITAL_REALISED, RISK_CAPITAL_PROPOSED, RISK_ADJUSTED_RETURN_REALISED,
							RISK_ADJUSTED_RETURN_PROPOSED, FACILITY_RAROC_REALISED, FACILITY_RAROC_PROPOSED,
							AVERAGE_UTILISED_REALISED, AVERAGE_UTILISED_PROPOSED, FTP_RATE_REALISED, FTP_RATE_PROPOSED,
							INTEREST_RATE_APPLIED_REALISED, INTEREST_RATE_APPLIED_PROPOSED, BORROWER_RATING_PROPOSED,
							COMMITMENT_FEE_PROPOSED, UPFRONT_FEE_PROPOSED, COLLATERAL_NAME_PROPOSED,
							COLLATRAL_TYPE_PROPOSED, COLLATERAL_AMOUNT_PROPOSED, COLLATRAL_CURRENCY_PROPOSED,
							COLLATRAL_LIEN_AMOUNT_PROPOSED, COLLATRAL_LIEN_INTEREST_PROPOSED,
							COLLATERAL_LIAN_TENURE_PROPOSED, EXPECTED_YEAR_RETURN_PROPOSED,
							REPRICING_FREQUENCY_PROPOSED, REPAYMENT_TYPE_PROPOSED, REMAINING_TERMS_IN_MONTHS_PROPOSED,
							MARKETING_RISK_CAPITAL_PROPOSED, COUNTERPARTY_RATE_PROPOSED, COUNTERPARTY_TYP_PROPOSED,
							SPL_COST_PROPOSED, MORATORIUM_PERIOD_PROPOSED, REPAYMENT_FREQUENCY_PROPOSED,
							CREDIT_LIMIT_PROPOSED, FACILITY_INCOME_NAME, FACILITY_INCOME_TYPE,
							FACILITY_INCOME_PERCENTAGE, FACILITY_INCOME_ABSOLUTE, COLLATERAL_NUMBER_REALIZED,
							COLLATERAL_NUMBER_PROPOSED, ALLOCATION_PERCENTAGE_REALIZED, ALLOCATION_PERCENTAGE_PROPOSED,
							INDEX_KEY_REALIZED, INDEX_KEY_PROPOSED, INDEX_TENOR_REALIZED, INDEX_TENOR_PROPOSED,
							INDEX_TENOR_UNIT_REALIZED, INDEX_TENOR_UNIT_PROPOSED, COMMITMENT_FEE_REALIZED,
							UTILIZATION_REALIZED, UTILIZATION_PROPOSED, FTP_OVERRIDE_REALIZED, FTP_OVERRIDE_PROPOSED };// bp05
																														// //d

					setFacilityData(RAROC_FACILITY_DETAILS_COLUMN, RAROC_FACILITY_DETAILS_TABLE, facilityID,
							fieldValue);// REALIZED,PROPosed FIELDS [DB TO FORM]
					readOnlyFieldsTreasuryProd(formObject);// 01-07-2024 by mohit
					log.info(" CommentedTenor_unit_Proposed$$$$$ ");
//					formObject.setValue("F_Tenor_unit_Proposed", "Months");  
					formObject.setStyle("F_Tenor_unit_Proposed", "disable", "true");
					retMsg = getReturnMessage(true, controlName, msg);

					// Start Added by shikha 23/07/24 25-09-24
					String squery = "Select ftp_override_proposed from NG_RAROC_FACILITY_DETAILS where WI_Name='"
							+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "' and facility_id='"
							+ facilityID + "'";
					List<List<String>> ftp_override_data = formObject.getDataFromDB(squery);
					log.info(" squery$$$$$ " + squery);
					log.info(" ftp_override_data$$$$$ " + ftp_override_data);
					formObject.setValue("F_FTP_OVR_PROPOSED", ftp_override_data.get(0).get(0));
					// End Added by shikha 23/07/24
					// Start Added by shikha 19/09/24
//					formObject.setStyle("F_FTP_PROP","disable", "false");
//					formObject.setValue("F_FTP_PROP", "");
					// End Added by shikha 19/09/24

					setIndexDescProp(facilityID);// added by mohit 09-08-2024 for setting Index Description
					setFtpRateOverProp(facilityID); // edit by reshank 09-08-24

					// start edit by mohit 26-07-2024 to check if we need to copy paste the prop
					// values to sens fields
					String parentWorkstep = formObject.getValue("CURRENT_WORKSTEPNAME").toString();
					// edit by mohit 18-09-2024 sens +approved parentWorkstep/stageId condition
					if (parentWorkstep.equalsIgnoreCase("CA_Raise Queries to RM")
							|| parentWorkstep.equalsIgnoreCase("HOCC_Recommendation")
							|| parentWorkstep.equalsIgnoreCase("CCO_Recommendation")
							|| parentWorkstep.equalsIgnoreCase("CSC_MRA_Prep") || parentWorkstep.equalsIgnoreCase("CC")
							|| parentWorkstep.equalsIgnoreCase("CA_FRS_Prep")
							|| parentWorkstep.equalsIgnoreCase("HOCC_FRS_Review")
							|| parentWorkstep.equalsIgnoreCase("BCIC")
							|| parentWorkstep.equalsIgnoreCase("HOCC_Def_Waive")
							|| parentWorkstep.equalsIgnoreCase("CCO_Def_Waiver")
							|| parentWorkstep.equalsIgnoreCase("CA_Def_Waiver")
							|| parentWorkstep.equalsIgnoreCase("CC_Def_Waiver")
							|| parentWorkstep.equalsIgnoreCase("CSU") || parentWorkstep.equalsIgnoreCase("Credit")
							|| parentWorkstep.equalsIgnoreCase("CA_Review_Analysis")
							|| parentWorkstep.equalsIgnoreCase("HOCC_Assign")
							|| parentWorkstep.equalsIgnoreCase("CAD_Doc_Assign")
							|| parentWorkstep.equalsIgnoreCase("Legal_Maker")
							|| parentWorkstep.equalsIgnoreCase("CAD_Doc_Verify")
							|| parentWorkstep.equalsIgnoreCase("CAD_CCU_Limit_Load")
							|| parentWorkstep.equalsIgnoreCase("CAD_CCU_Limit_Load_Review")
							|| parentWorkstep.equalsIgnoreCase("Exit")
							|| parentWorkstep.equalsIgnoreCase("CEO_Proposal_Review")
							|| parentWorkstep.equalsIgnoreCase("CAD_Doc_Print")
							|| parentWorkstep.equalsIgnoreCase("CAD_Doc_Prep")
							|| parentWorkstep.equalsIgnoreCase("Board")
							|| parentWorkstep.equalsIgnoreCase("CAD_Doc_Review")
							|| parentWorkstep.equalsIgnoreCase("CAD_Doc_Approve")
							|| parentWorkstep.equalsIgnoreCase("Legal_Checker")
							|| parentWorkstep.equalsIgnoreCase("TFS")
							|| parentWorkstep.equalsIgnoreCase("CAD_Def_waiver")
							|| parentWorkstep.equalsIgnoreCase("Query_Resolution")
							|| parentWorkstep.equalsIgnoreCase("Docs_Upload_Scan")
							|| parentWorkstep.equalsIgnoreCase("Hold") || parentWorkstep.equalsIgnoreCase("Query")
							|| parentWorkstep.equalsIgnoreCase("Discard")
							|| parentWorkstep.equalsIgnoreCase("Board_Discard")
							|| parentWorkstep.equalsIgnoreCase("Email_Submit")
							|| parentWorkstep.equalsIgnoreCase("Email_SendBack")
							|| parentWorkstep.equalsIgnoreCase("System stage")
							|| parentWorkstep.equalsIgnoreCase("Discard_CC")
							|| parentWorkstep.equalsIgnoreCase("CC_Discard")
							|| parentWorkstep.equalsIgnoreCase("Query_Resolution_Hold")
							|| parentWorkstep.equalsIgnoreCase("Recovery")
							|| parentWorkstep.equalsIgnoreCase("Archive")) {
						log.info("Inside parentWorkstep of sens+ approved 18-09-2024....");
						String[] fieldValueSens = new String[] { CURRENCY_SENSITIZED, FACILITY_AMOUNT_SENSITIZED,
								TENOR_SENSITIZED, TENOR_UNIT_SENSITIZED, EXPIRY_DATE_SENSITIZED, CASH_MARGIN_SENSITIZED,
								OUTS_AMNT_SENSITIZED, PRICING_SENSITIZED, INDEX_SENSITIZED, INDEX_RATE_SENSITIZED,
								MARGIN_COMMISION_SENSITIZED, VALUE_SENSITIZED, MINIMUM_SENSITIZED, MAXIMUM_SENSITIZED,
								EXP_RET_SENSITIZED, MAR_RIS_SENSITIZED, COUNT_INT_SENSITIZED, COUNT_TYP_SENSITIZED,
								REP_FREQ_SENSITIZED, SPC_COST_SENSITIZED, REPAY_FREQ_SENSITIZED,
								REP_REVOLVING_SENSITIZED, REP_TYP_SENSITIZED, CREDIT_SENSITIZED,
								EQV_AED_AMOUNT_SENSITIZED, ECL_SENSITIZED, RISK_CAPITAL_SENSITIZED,
								RISK_RETURN_SENSITIZED, FACILITY_RAROC_SENSITIZED, AVG_SENSITIZED,
								REMAIN_TRM_SENSITIZED, FTP_SENSITIZED, INT_RATE_SENSITIZED, COLLATERAL_NAME_SENSITIZED,
								COLLATERAL_TYPE_SENSITIZED, COLLATERAL_AMOUNT_SENSITIZED, COLLATRAL_CURRENCY_SENSITIZED,
								COLLATRAL_LIEN_AMOUNT_SENSITIZED, COLLATRAL_LIEN_INTEREST_SENSITIZED,
								COLLATERAL_LIAN_TENURE_SENSITIZED, BORROWER_RATING_SENSITIZED,
								COMMITMENT_FEE_SENSITIZED, UPFRONT_FEE_SENSITIZED, INCOME_NAME, INCOME_TYPE,
								INCOME_PERCENTAGE, INCOME_ABSOLUTE, COLLATERAL_NUMBER_SENSITIZED,
								ALLOCATION_PERCENTAGE_SENSITIZED, INDEX_KEY_SENSITIZED, INDEX_TENOR_SENSITIZED,
								INDEX_TENOR_UNIT_SENSITIZED, UTILIZATION_SENSITIZED, FTP_OVERRIDE_SENSITIZED };
						String colSensCheck = "UTILIZATION_SENSITIZED";
						String querySensCheck = "SELECT " + colSensCheck
								+ " FROM NG_RAROC_FACILITY_DETAILS WHERE WI_NAME='"
								+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "' AND FACILITY_ID ='"
								+ facilityID + "'";
						log.info("querySensCheck###### : " + querySensCheck);
						String outputSensCheck = getDataFromDBWithColumns(querySensCheck, colSensCheck);
						XMLParser xmlParserSensCheck = new XMLParser(outputSensCheck);
						log.info("xmlParserSensCheck" + xmlParserSensCheck);
						int totalRetrievedSensCheck = Integer
								.parseInt(xmlParserSensCheck.getValueOf(Constants.TOTAL_RETRIEVED));
						log.info("totalRetrievedSensCheck######" + totalRetrievedSensCheck);

						if (totalRetrievedSensCheck != 0) {

							String rowSensCheck = "1";
							log.info("rowSensCheck####" + rowSensCheck);
							XMLParser rowParserSensCheck = new XMLParser(outputSensCheck);
							String sValueSensCheck = rowParserSensCheck.getValueOf("UTILIZATION_SENSITIZED");
							if (sValueSensCheck == null || sValueSensCheck.equals("") || sValueSensCheck.isEmpty()) {
								log.info("Inside if sValueSensCheck is!!!!!" + sValueSensCheck + "+++");
								getUpdateData(RAROC_FACILITY_DETAILS_TABLE, RAROC_FACILITY_SENSITIZED_UPDATE,
										facilityID);// COPY FROM PROP TO SENS
							}
							setFacilityData(RAROC_FACILITY_SENSITIZED_COLUMN, RAROC_FACILITY_DETAILS_TABLE, facilityID,
									fieldValueSens);// added by mohit 06-09-2024 as sens fields not populated on form
													// for 1st time
							// } else {
							// log.info("Inside else sValueSensCheck is not null#######");
							// setFacilityData(RAROC_FACILITY_SENSITIZED_COLUMN,
							// RAROC_FACILITY_DETAILS_TABLE, facilityID,
							// fieldValueSens);// for sens fields
							// Add by reshank 02-08-24
//								formObject.setValue("F_Tenor_unit_Sensitized", "Months");
							formObject.setStyle("F_Tenor_unit_Sensitized", "disable", "true");
							// Add by reshank on 5-08-24
							repaymentTypeBulletSens(facilityID);
							/*
							 * String checkRepaymentFreqSensQuery =
							 * "select REPAYMENT_FREQUENCY_SENSITIZED from NG_RAROC_FACILITY_DETAILS where wi_name='"
							 * + formObject.getObjGeneralData().getM_strProcessInstanceId() +
							 * "' and facility_id='"+facilityID+"'";//this is repayment type value
							 * List<List<String>> repaymentFreqSensOut =
							 * formObject.getDataFromDB(checkRepaymentFreqSensQuery); String
							 * repaymentFreqSens = repaymentFreqSensOut.get(0).get(0);
							 * 
							 * if (repaymentFreqSens.equalsIgnoreCase("Bullet")) {//this is repayment type
							 * value check formObject.addItemInCombo("Repay_Freq_Sensitized", "Bullet",
							 * "Bullet");//this is repayment freq value
							 * formObject.setValue("Repay_Freq_Sensitized", "Bullet");
							 * formObject.setStyle("Repay_Freq_Sensitized", "disable", "true"); }
							 */
							// start edit by mohit 09-08-2024 for setting Index Description
							setIndexDescSens(facilityID);
							// end edit by mohit 09-08-2024 for setting Index Description
							setFtpRateOverSens(facilityID); // edit by reshank 10-08-24
							// }

						}
						// end edit by mohit 26-07-2024 to check if we need to copy paste the prop
						// values to sens fields
						// start edit by mohit 02-08-2024 for copying sens to approved fields
						// String parentWorkstep =
						// formObject.getValue("CURRENT_WORKSTEPNAME").toString();
						log.info(" Before approved parentWorkstep...." + parentWorkstep);
						if (parentWorkstep.equalsIgnoreCase("CAD_Doc_Assign")
								|| parentWorkstep.equalsIgnoreCase("Legal_Maker")
								|| parentWorkstep.equalsIgnoreCase("CAD_Doc_Verify")
								|| parentWorkstep.equalsIgnoreCase("CAD_CCU_Limit_Load")
								|| parentWorkstep.equalsIgnoreCase("CAD_CCU_Limit_Load_Review")
								|| parentWorkstep.equalsIgnoreCase("Exit")
								|| parentWorkstep.equalsIgnoreCase("CEO_Proposal_Review")
								|| parentWorkstep.equalsIgnoreCase("CAD_Doc_Print")
								|| parentWorkstep.equalsIgnoreCase("CAD_Doc_Prep")
								|| parentWorkstep.equalsIgnoreCase("Board")
								|| parentWorkstep.equalsIgnoreCase("CAD_Doc_Review")
								|| parentWorkstep.equalsIgnoreCase("CAD_Doc_Approve")
								|| parentWorkstep.equalsIgnoreCase("Legal_Checker")
								|| parentWorkstep.equalsIgnoreCase("TFS")
								|| parentWorkstep.equalsIgnoreCase("CAD_Def_waiver")
								|| parentWorkstep.equalsIgnoreCase("Query_Resolution")
								|| parentWorkstep.equalsIgnoreCase("Docs_Upload_Scan")
								|| parentWorkstep.equalsIgnoreCase("Hold") || parentWorkstep.equalsIgnoreCase("Query")
								|| parentWorkstep.equalsIgnoreCase("Discard")
								|| parentWorkstep.equalsIgnoreCase("Board_Discard")
								|| parentWorkstep.equalsIgnoreCase("Email_Submit")
								|| parentWorkstep.equalsIgnoreCase("Email_SendBack")
								|| parentWorkstep.equalsIgnoreCase("System stage")
								|| parentWorkstep.equalsIgnoreCase("Discard_CC")
								|| parentWorkstep.equalsIgnoreCase("CC_Discard")
								|| parentWorkstep.equalsIgnoreCase("Query_Resolution_Hold")
								|| parentWorkstep.equalsIgnoreCase("Recovery")
								|| parentWorkstep.equalsIgnoreCase("Archive")) {
							// ON 10-09-2024 getUpdateData(RAROC_FACILITY_DETAILS_TABLE,
							// RAROC_FACILITY_APPROVE_UPDATE,facilityID);// COPY FROM SENS to approved db to
							// db

							String[] fieldValueApproved = new String[] { CURRENCY_APPROVED, FACILITY_AMOUNT_APPROVED,
									TENOR_APPROVED, TENOR_UNIT_APPROVED, EXPIRY_DATE_APPROVED, CASH_MARGIN_APPROVED,
									OUTSTANDING_AMOUNT_APPROVED, PRICING_APPROVED, INDEX_APPROVED, INDEX_RATE_APPROVED,
									MARGIN_COMMISION_APPROVED, VALUE_APPROVED, MINIMUM_APPROVED, MAXIMUM_APPROVED,
									EXP_1_YEAR_RATE_APPROVED, MARKETING_RISK_APPROVED, COUNTERPARTY_INTERNAL_APPROVED,
									COUNTERPARTY_TYPE_APPROVED, REPRICING_FREQ_APPROVED, SPECIAL_COST_APPROVED,
									REPAYMENT_TYPE_APPROVED, MORATORIUM_PERIOD_APPROVED, REPAYMENT_FREQUENCY_APPROVED,
									CREDIT_LIMIT_APPROVED, EQV_AED_AMOUNT_APPROVED, ECL_APPROVED, RISK_CAPITAL_APPROVED,
									RISK_RETURN_APPROVED, FACILITY_RAROC_APPROVED, AVG_UTILISED_APPROVED,
									REMAINING_TERM_IN_MONTH_APPROVED, FTP_APPROVED, INTEREST_RATE_APPROVED,
									COLLATRAL_NAME_APPROVED, COLLATRAL_TYPE_APPROVED, COLLATRAL_AMOUNT_APPROVED,
									COLLATRAL_CURRENCY_APPROVED, COLLATRAL_LIEN_AMOUNT_APPROVED,
									COLLATRAL_LIEN_INTEREST_APPROVED, COLLATRAL_LIEN_TENURE_APPROVED,
									BORROWER_RATING_APPROVED, COMMITMENT_FEE_APPROVED, UPFRONT_FEE_APPROVED,
									COLLATERAL_NUMBER_APPROVED, ALLOCATION_PERCENTAGE_APPROVED, INDEX_KEY_APPROVED,
									INDEX_TENOR_APPROVED, INDEX_TENOR_UNIT_APPROVED, UTILIZATION_APPROVED,
									FTP_OVERRIDE_APPROVED };
							setFacilityData(RAROC_FACILITY_APPROVE_COLUMN, RAROC_FACILITY_DETAILS_TABLE, facilityID,
									fieldValueApproved);// [db to form]
							// here
							// getUpdateApprovedData(facilityID, formObject);// approved fields on [form to
							// db]
						}
						// end edit by mohit 02-08-2024 for copying sens to approved fields
					}
					// Added by Shivanshu
				} else if (controlName.equalsIgnoreCase(FACILITY_MODIFY_BTN)) {
					String facilityID = data;
					log.info("ExecuteServerEnd facilityID:::: " + facilityID);
					/*
					 * String checkRepaymentFreqQuery =
					 * "select Repayment_Frequency_Proposed from NG_RAROC_FACILITY_DETAILS where wi_name='"
					 * + formObject.getObjGeneralData().getM_strProcessInstanceId() + "'";
					 * List<List<String>> repaymentFreqOut =
					 * formObject.getDataFromDB(checkRepaymentFreqQuery); String repaymentFreq =
					 * repaymentFreqOut.get(0).get(0);
					 */

					String parentWorkstep = formObject.getValue("CURRENT_WORKSTEPNAME").toString();

					/*
					 * if (parentWorkstep.equalsIgnoreCase("Initiate_Proposal") ||
					 * parentWorkstep.equalsIgnoreCase("RM_Proposal_Review") ||
					 * parentWorkstep.equalsIgnoreCase("RO_Proposal_Modify") ||
					 * parentWorkstep.equalsIgnoreCase("UH_TL_Proposal_Review") ||
					 * parentWorkstep.equalsIgnoreCase("CBO_Proposal_Review") ||
					 * parentWorkstep.equalsIgnoreCase("RM_Query_Resolutions") ||
					 * parentWorkstep.equalsIgnoreCase("RM_Doc_Execution") ||
					 * parentWorkstep.equalsIgnoreCase("UH_TL_Def_Waiver") ||
					 * parentWorkstep.equalsIgnoreCase("Head_Corporate_Banking")) as parentWorkstep
					 * is not complete
					 */
					if (parentWorkstep.equalsIgnoreCase("Initiate_Proposal")
							|| parentWorkstep.equalsIgnoreCase("RM_Proposal_Review")
							|| parentWorkstep.equalsIgnoreCase("RO_Proposal_Modify")
							|| parentWorkstep.equalsIgnoreCase("UH_TL_Proposal_Review")
							|| parentWorkstep.equalsIgnoreCase("CBO_Proposal_Review")
							|| parentWorkstep.equalsIgnoreCase("RM_Query Resolutions")
							|| parentWorkstep.equalsIgnoreCase("RM_Doc_Execution")
							|| parentWorkstep.equalsIgnoreCase("UH_TL_Def_Waiver")
							|| parentWorkstep.equalsIgnoreCase("Head_Corporate_Banking")
							|| parentWorkstep.equalsIgnoreCase("CA_Raise Queries to RM")
							|| parentWorkstep.equalsIgnoreCase("HOCC_Recommendation")
							|| parentWorkstep.equalsIgnoreCase("CCO_Recommendation")
							|| parentWorkstep.equalsIgnoreCase("CSC_MRA_Prep") || parentWorkstep.equalsIgnoreCase("CC")
							|| parentWorkstep.equalsIgnoreCase("CA_FRS_Prep")
							|| parentWorkstep.equalsIgnoreCase("HOCC_FRS_Review")
							|| parentWorkstep.equalsIgnoreCase("BCIC")
							|| parentWorkstep.equalsIgnoreCase("HOCC_Def_Waive")
							|| parentWorkstep.equalsIgnoreCase("CCO_Def_Waiver")
							|| parentWorkstep.equalsIgnoreCase("CA_Def_Waiver")
							|| parentWorkstep.equalsIgnoreCase("CC_Def_Waiver")
							|| parentWorkstep.equalsIgnoreCase("CSU") || parentWorkstep.equalsIgnoreCase("Credit")
							|| parentWorkstep.equalsIgnoreCase("CA_Review_Analysis")
							|| parentWorkstep.equalsIgnoreCase("CA_HOCC_Analysis")) {

						log.info("ExecuteServerEnd Inside Proposal : ");
						getUpdateFacilityData(facilityID, formObject);// for prop fields [form to db]
						updateFtpRate(formObject); // for prop field
						updateFtpRateSens(formObject); // for Sens field
						// 22M
						String UpdateQuery = "UPDATE NG_RAROC_FACILITY_DETAILS SET INDEX_KEY_PROPOSED = 'None' "
								+ "WHERE WI_NAME = '" + formObject.getObjGeneralData().getM_strProcessInstanceId()
								+ "'and facility_id='" + facilityID + "' AND upper(FACILITY_TYPE)='NON-FUNDED'";

						log.info("Update Query######" + UpdateQuery);
						formObject.getDataFromDB(UpdateQuery);

					} else if (parentWorkstep.equalsIgnoreCase("CA_Review_Analysis")
							|| parentWorkstep.equalsIgnoreCase("HOCC_Assign")
							|| parentWorkstep.equalsIgnoreCase("CA_Raise Queries to RM")
							|| parentWorkstep.equalsIgnoreCase("HOCC_Recommendation")
							|| parentWorkstep.equalsIgnoreCase("CCO_Recommendation")
							|| parentWorkstep.equalsIgnoreCase("CSC_MRA_Prep") || parentWorkstep.equalsIgnoreCase("CC")
							|| parentWorkstep.equalsIgnoreCase("CA_FRS_Prep")
							|| parentWorkstep.equalsIgnoreCase("HOCC_FRS_Review")
							|| parentWorkstep.equalsIgnoreCase("BCIC")
							|| parentWorkstep.equalsIgnoreCase("HOCC_Def_Waive")
							|| parentWorkstep.equalsIgnoreCase("CCO_Def_Waiver")
							|| parentWorkstep.equalsIgnoreCase("CA_Def_Waiver")
							|| parentWorkstep.equalsIgnoreCase("CC_Def_Waiver")
							|| parentWorkstep.equalsIgnoreCase("CSU") || parentWorkstep.equalsIgnoreCase("Credit")) {

						log.info("ExecuteServerEnd Inside Sensitized...: ");
						getUpdateSensitizedData(facilityID, formObject);// sens [form to db]
						// getUpdateApprovedData(facilityID, formObject);// approved fields on [form to
						// db]
						// 22M
						String UpdateQuery = "UPDATE NG_RAROC_FACILITY_DETAILS SET INDEX_KEY_SENSITIZED = 'None' "
								+ "WHERE WI_NAME = '" + formObject.getObjGeneralData().getM_strProcessInstanceId()
								+ "'and facility_id='" + facilityID + "'AND upper(FACILITY_TYPE)='NON-FUNDED'";

						log.info("Update Query...." + UpdateQuery);
						formObject.getDataFromDB(UpdateQuery);
						log.info("modify btm clicked...");
						getUpdateData(RAROC_FACILITY_DETAILS_TABLE, RAROC_FACILITY_APPROVE_UPDATE, facilityID);// COPY
																												// FACILTY
																												// FROM
																												// SENS
																												// to
																												// approved
																												// db to
																												// db
																												// ADDED
																												// BY
																												// MOHIT
																												// 10-09-2024
						getUpdateData(RAROC_CUSTOMER_TABLE, RAROC_CUSTOMER_APPROVE_UPDATE); // COPY customer FROM SENS
																							// to approved db to db
																							// ADDED BY MOHIT 10-09-2024
					}
					// retMsg = getReturnMessage(true, controlName, "FACILITY DATA MODIFIED
					// SUCCESSFULLY");

					return Common.getReturnMessage(true, "FACILITY DATA MODIFIED SUCCESSFULLY");
				}
				// TANU FACILITY APIs
				else if (controlName.equalsIgnoreCase(ON_BUTTON_CLICK)) {
					try {
						log.info("Inside on btn click #######");
						boolean Flag = true;
						String winame = formObject.getObjGeneralData().getM_strProcessInstanceId();
						String FACILITY_FLAG_Query = "SELECT FACILITY_FLAG FROM ALM_RAROC_EXT_TABLE WHERE WI_NAME = '"
								+ winame + "'";
						log.info("FACILITY_FLAG_Query..."+FACILITY_FLAG_Query);
						// TANU
						List<List<String>> facilityOutput = formObject.getDataFromDB(FACILITY_FLAG_Query);
						log.info("facilityOutput..."+facilityOutput);
						if (facilityOutput.get(0).get(0) == null || facilityOutput.get(0).get(0).equalsIgnoreCase("")) {
							log.info("inside if get0.0...");
							String limitidQuery = "Select Commitment_No from NG_RAROC_FACILITY_DETAILS where wi_name = '"
									+ winame + "'";
							log.info("limitidQuery..."+limitidQuery);
							List<List<String>> limitID = formObject.getDataFromDB(limitidQuery);
							log.info("limitID..."+limitID);
							for (int j = 0; j < limitID.size(); j++) {
								log.info("inside for loop j..."+j);
								if (limitID.get(j).get(0) == null || limitID.get(j).get(0).equalsIgnoreCase("")) {
									continue;
								}
								FacilitiesByLimitIdResponse[] facilitiesbylimitid;
								try {
									facilitiesbylimitid = executeIntFacility(formObject, data, limitID.get(j).get(0));
									log.info("@@ 63 executeServerEventForFacility : outputXML===>> "
											+ facilitiesbylimitid);

								} catch (Exception e) {
									Flag = false;
									continue;
								}
								if (facilitiesbylimitid != null) {
									for (int i = 0; i < facilitiesbylimitid.length; i++) {

										String loanaccountnumber = facilitiesbylimitid[i].getAcctNo();
										String arrangementid = facilitiesbylimitid[i].getArrangement();
										log.info("AccountNumber " + loanaccountnumber);
										log.info("ArrangementId" + arrangementid);
										if (loanaccountnumber == null || loanaccountnumber == "") {

											log.info("LoanAccountNumber Not Present");
											return "LoanAccountNumber Not Present";

										} else {
											String responseXML2 = executeIntCallLoan(formObject, data,
													loanaccountnumber, i);
											log.info("@@@77 executeServerEventLoanDetails : outputXML===>> "
													+ responseXML2);
										}
										if (arrangementid == null || arrangementid == "") {

											log.info("ArrangementId Not Present");
											return "ArrangementId Not Present";
										} else {
											String responseXML = executeRepaymentSchedule(formObject, data,
													arrangementid, i, limitID.get(j).get(0));
											log.info("executeServerEventRepaymentSchedule : outputXML===>> "
													+ responseXML);
										}

									}

								}
							}

							String Update_Facility = "UPDATE ALM_RAROC_EXT_TABLE SET FACILITY_FLAG = 'Y' WHERE WI_NAME = '"
									+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "'";

							log.info("Update Query >>" + Update_Facility);
							formObject.getDataFromDB(Update_Facility);

							// return "FACILITY DATA SUCCESSFULLY FETCHED";
							return Common.getReturnMessage(true, "SUCCESS");
						}
					} catch (Exception e) {
						e.printStackTrace();
						log.info("123---" + e.getMessage());
						return Common.getReturnMessage(false, "Error in Fetching Customer Data");
					}
				} else if (controlName.equalsIgnoreCase(COLLATERAL)) {
					log.info("method call");
					String coll_response = getCollatoralNo(formObject);
					formObject.setValue("Customer_Collateral_Number", coll_response);
				}
				// start edit by mohit 27-06-2024
				/*
				 * else if (controlName.equalsIgnoreCase(COLLATERAL_ON_ROW_CLICK)) {
				 * //collateral grid
				 * log.info("ExecuteServerEnd COLLATERAL_ON_ROW_CLICK case CollateralType : " );
				 * getCollateralGridData(formObject,data); }
				 */ // NOT NEEDED TO REMOVE

				// end edit by mohit 27-06-2024
				// END

			}

			// start edit by mohit 16-07-2024
			else if (controlName.equalsIgnoreCase(CFM_GRID)) {
				log.info("COLLATERAL_FACILITY_MAPPING_GRID ++++.... inside controlName ==" + controlName);
				String parentWorkstep = formObject.getValue("CURRENT_WORKSTEPNAME").toString();
				// start edit by mohit 29-07-2024
				if (eventType.equalsIgnoreCase(CFM_OnLoad)) {
					String query = "UPDATE NG_RAROC_COLLATERAL_FACILITY_MAP SET COLLATERAL_TYPE_SENS=collateral_type_prop,"
							+ "collateral_name_sens=collateral_name_prop,collateral_amount_sens=collateral_amount_prop,collateral_currency_sens=collateral_currency_prop,"
							+ " collateral_number_sens=collateral_number_prop,collateral_allocation_percentage_sens=collateral_allocation_percentage_prop WHERE WI_NAME = '"
							+ formObject.getObjGeneralData().getM_strProcessInstanceId()
							+ "' and collateral_name_sens is null";
					log.info("copy COLLATERAL_to sensitized" + query);
					formObject.saveDataInDB(query);
					log.info("COLLATERAL_FACILITY_MAPPING_GRID ++++.... inside CFM_OnLoad ==" + eventType);
					// String facilityID = data;
					// setCFMSensFields(formObject); to_check
				}
				// end edit by mohit 29-07-2024
				if (eventType.equalsIgnoreCase(CFM_ADD_ROW)) {
					log.info("COLLATERAL_FACILITY_MAPPING_GRID ++++....inside eventType==" + eventType);
					// String stageId = formObject.getValue("CURRENT_WORKSTEPNAME").toString();
					// msg = validateCFMGrid(formObject);
					// log.info("COLLATERAL_FACILITY_MAPPING_GRID ....msg=="+msg);
					// retMsg = getReturnMessage(true, controlName, msg);
					// start 09-09-2024
					/*
					 * msg = validateColNoNewColType(formObject);
					 * log.info("COLLATERAL_FACILITY_MAPPING_GRID !!!msg=="+msg); retMsg =
					 * getReturnMessage(true, controlName, msg);
					 */
					// start edit by mohit 11-09-2024 to move sens db columns to app db columns
					/*
					 * if(parentWorkstep.equalsIgnoreCase("CA_Review_Analysis") ||
					 * parentWorkstep.equalsIgnoreCase("HOCC_Assign") ||
					 * parentWorkstep.equalsIgnoreCase("CA_Raise Queries to RM") ||
					 * parentWorkstep.equalsIgnoreCase("HOCC_Recommendation") ||
					 * parentWorkstep.equalsIgnoreCase("CCO_Recommendation") ||
					 * parentWorkstep.equalsIgnoreCase("CSC_MRA_Prep") ||
					 * parentWorkstep.equalsIgnoreCase("CC") ||
					 * parentWorkstep.equalsIgnoreCase("CA_FRS_Prep") ||
					 * parentWorkstep.equalsIgnoreCase("HOCC_FRS_Review") ||
					 * parentWorkstep.equalsIgnoreCase("BCIC") ||
					 * parentWorkstep.equalsIgnoreCase("HOCC_Def_Waive") ||
					 * parentWorkstep.equalsIgnoreCase("CCO_Def_Waiver") ||
					 * parentWorkstep.equalsIgnoreCase("CA_Def_Waiver") ||
					 * parentWorkstep.equalsIgnoreCase("CC_Def_Waiver") ||
					 * parentWorkstep.equalsIgnoreCase("CSU") ||
					 * parentWorkstep.equalsIgnoreCase("Credit")) {
					 * log.info("Inside CFM_ADD_ROW sens stage_ids...."); setCFMSensToApprov(); }
					 */
					// end edit by mohit 11-09-2024 to move sens db columns to app db columns

				}
				if (parentWorkstep.equalsIgnoreCase("CA_Raise Queries to RM")
						|| parentWorkstep.equalsIgnoreCase("HOCC_Recommendation")
						|| parentWorkstep.equalsIgnoreCase("CCO_Recommendation")
						|| parentWorkstep.equalsIgnoreCase("CSC_MRA_Prep") || parentWorkstep.equalsIgnoreCase("CC")
						|| parentWorkstep.equalsIgnoreCase("CA_FRS_Prep")
						|| parentWorkstep.equalsIgnoreCase("HOCC_FRS_Review") || parentWorkstep.equalsIgnoreCase("BCIC")
						|| parentWorkstep.equalsIgnoreCase("HOCC_Def_Waive")
						|| parentWorkstep.equalsIgnoreCase("CCO_Def_Waiver")
						|| parentWorkstep.equalsIgnoreCase("CA_Def_Waiver")
						|| parentWorkstep.equalsIgnoreCase("CC_Def_Waiver") || parentWorkstep.equalsIgnoreCase("CSU")
						|| parentWorkstep.equalsIgnoreCase("Credit")
						|| parentWorkstep.equalsIgnoreCase("CA_Review_Analysis")
						|| parentWorkstep.equalsIgnoreCase("HOCC_Assign")) {
					log.info("Inside if condition of current_workstep....");
					setCFMPropDisable(formObject);

					// iFormReference.setStyle("projectedDetails", "disable", "true");

				}
			}

			// end edit by mohit 16-07-2024

		} catch (Exception e) {
			log.error("Exception in checkpreviousStage: ", e);
		}
		return retMsg;
	}

	public void matchingRim(IFormReference obj) {
		try {
			String workitemName = obj.getObjGeneralData().getM_strProcessInstanceId();
			log.info("workitemName: " + workitemName);
			// String rimtofetch = obj.getValue("LEAD_REF_NO").toString();
			String rimtofetch = obj.getValue("Q_NG_RAROC_CUSTOMER_DETAILS_CUSTOMER_T24_ID").toString();
			// obj.setStyle("Q_NG_RAROC_CUSTOMER_DETAILS_CUSTOMER_T24_ID", "disable",
			// "true");
			log.info("rimtofetch:::" + rimtofetch + "+++");
			log.info("Inside matchingRim...");
			if (!(rimtofetch.equalsIgnoreCase("") || rimtofetch.equalsIgnoreCase(null))) {// changes by mohit 17-06-2024
																							// for avoiding the whole
																							// facility table to be
																							// deleted.
				// Directly delete all records where RIM_NO does not match rimtofetch and
				// WI_NAME matches workitemName
				String deleteNonMatchingRecords = "DELETE FROM NG_RAROC_FACILITY_DETAILS WHERE RIM_NO != '" + rimtofetch
						+ "' AND WI_NAME='" + workitemName + "'";
				log.info("deleteNonMatchingRecords: " + deleteNonMatchingRecords);
				obj.saveDataInDB(deleteNonMatchingRecords);
				log.info("All records not matching rimtofetch have been deleted.");
			}

		} catch (Exception e) {
			log.error("Error occurred while performing delete operation: " + e.getMessage(), e);
		}
	}

	public void CustomerValidation(IFormReference obj) {
		try {

			String WorkitemId = obj.getObjGeneralData().getM_strProcessInstanceId();
			log.info("Inside CustomerValidation+++");
//            String queryReal = "Select " + RAROC_CUSTOMER_REALIZED_COLUMN + " from " + RAROC_CUSTOMER_TABLE
//                    + " where wi_name='" + WorkitemId + "'";
//            log.info("CustomerValidation : " + " queryReal :" + queryReal);

//            List<List<String>> sOutput1 = obj.getDataFromDB(queryReal);
//            if (sOutput1 != null && !sOutput1.isEmpty()) {
//                log.info("CustomerValidation : " + " Inside Proposed Data Set :" + sOutput1);
//
//                String[] fieldValue = new String[] { CUSTOMER_RAROC_PROPOSED, BUSINESS_SEGMENT_PROPOSED,
//                        INDUSTRY_SEGEMENT_PROPOSED, IFRS_STAGING_PROPOSED, COUNTERPARTY_TYPE_PROPOSED,
//                        INCOME_CASA_PROPOSED, EXPORT_INCOME_PROPOSED, FX_INCOME_PROPOSED, OTHER_INCOME_PROPOSED,
//                        FEE_INCM_AED_PROPOSED, CROSS_SELL_INCM_PROPOSED, REALIZED_RAROC_PROPOSED,
//                        CUSTOMER_NAME_PROPOSED, CUST_INTRL_RATING_PROPOSED, TOTAL_FUNDED_AED_PROPOSED,
//                        TOTAL_NONFUNDED_AED_PROPOSED, CUSTOMER_COUNTRY_PROPOSED, EXTERNAL_CODE_PROPOSED,
//                        INTERNAL_RATING_PROPOSED, SALES_TURNOVER_PROPOSED };
//
//                log.info("fieldValue : " + fieldValue.length);
//                setMultipleNullValues(fieldValue, sOutput1);
//            }
			String parentWorkstep = formObject.getValue("CURRENT_WORKSTEPNAME").toString();
			if (parentWorkstep.equalsIgnoreCase("CA_Raise Queries to RM")
					|| parentWorkstep.equalsIgnoreCase("HOCC_Recommendation")
					|| parentWorkstep.equalsIgnoreCase("CCO_Recommendation")
					|| parentWorkstep.equalsIgnoreCase("CSC_MRA_Prep") || parentWorkstep.equalsIgnoreCase("CC")
					|| parentWorkstep.equalsIgnoreCase("CA_FRS_Prep")
					|| parentWorkstep.equalsIgnoreCase("HOCC_FRS_Review") || parentWorkstep.equalsIgnoreCase("BCIC")
					|| parentWorkstep.equalsIgnoreCase("HOCC_Def_Waive")
					|| parentWorkstep.equalsIgnoreCase("CCO_Def_Waiver")
					|| parentWorkstep.equalsIgnoreCase("CA_Def_Waiver")
					|| parentWorkstep.equalsIgnoreCase("CC_Def_Waiver") || parentWorkstep.equalsIgnoreCase("CSU")
					|| parentWorkstep.equalsIgnoreCase("Credit")
					|| parentWorkstep.equalsIgnoreCase("CA_Review_Analysis")
					|| parentWorkstep.equalsIgnoreCase("HOCC_Assign")) {
				String queryProp = "Select " + RAROC_CUSTOMER_PROPOSED_COLUMN + " from " + RAROC_CUSTOMER_TABLE
						+ " where wi_name='" + WorkitemId + "'";
				log.info("CustomerValidation : " + " queryProp :" + queryProp);

				List<List<String>> sOutput = obj.getDataFromDB(queryProp);

				log.info("CustomerValidation :....Inside Sensitized Data Set :" + sOutput);

				String[] fieldValue = new String[] { BUSINESS_SEGMENT_SENSITIZED, INDUSTRY_SEGEMENT_SENSITIZED,
						IFRS_STAGING_SENSITIZED, COUNTERPARTY_TYPE_SENSITIZED, INCOME_CASA_SENSITIZED,
						EXPORT_INCOME_SENSITIZED, FX_INCOME_SENSITIZED, OTHER_INCOME_SENSITIZED,
						FEE_INCM_AED_SENSITIZED, CROSS_SELL_INCM_SENSITIZED, REALIZED_RAROC_SENSITIZED,
						CUSTOMER_NAME_SENSITIZED, CUST_INTRL_RATING_SENSITIZED, TOTAL_FUNDED_AED_SENSITIZED,
						TOTAL_NONFUNDED_AED_SENSITIZED, CUSTOMER_COUNTRY_SENSITIZED, INTERNAL_RATING_SENSITIZED,
						EXTERNAL_RATING_SENSITIZED, EXTERNAL_CODE_SENSITIZED, SALES_TURNOVER_SENSITIZED };// edit by
																											// mohit
																											// CUSTOMER_RAROC_SENSITIZED
																											// removed
																											// 04-09-2024

				log.info("fieldValue : " + fieldValue.length);
				setMultipleNullValues(fieldValue, sOutput);
			} else if (parentWorkstep.equalsIgnoreCase("CAD_Doc_Assign")
					|| parentWorkstep.equalsIgnoreCase("Legal_Maker")
					|| parentWorkstep.equalsIgnoreCase("CAD_Doc_Verify")
					|| parentWorkstep.equalsIgnoreCase("CAD_CCU_Limit_Load")
					|| parentWorkstep.equalsIgnoreCase("CAD_CCU_Limit_Load_Review")
					|| parentWorkstep.equalsIgnoreCase("Exit") || parentWorkstep.equalsIgnoreCase("CEO_Proposal_Review")
					|| parentWorkstep.equalsIgnoreCase("CAD_Doc_Print")
					|| parentWorkstep.equalsIgnoreCase("CAD_Doc_Prep") || parentWorkstep.equalsIgnoreCase("Board")
					|| parentWorkstep.equalsIgnoreCase("CAD_Doc_Review")
					|| parentWorkstep.equalsIgnoreCase("CAD_Doc_Approve")
					|| parentWorkstep.equalsIgnoreCase("Legal_Checker") || parentWorkstep.equalsIgnoreCase("TFS")
					|| parentWorkstep.equalsIgnoreCase("CAD_Def_waiver")
					|| parentWorkstep.equalsIgnoreCase("Query_Resolution")
					|| parentWorkstep.equalsIgnoreCase("Docs_Upload_Scan") || parentWorkstep.equalsIgnoreCase("Hold")
					|| parentWorkstep.equalsIgnoreCase("Query") || parentWorkstep.equalsIgnoreCase("Discard")
					|| parentWorkstep.equalsIgnoreCase("Board_Discard")
					|| parentWorkstep.equalsIgnoreCase("Email_Submit")
					|| parentWorkstep.equalsIgnoreCase("Email_SendBack")
					|| parentWorkstep.equalsIgnoreCase("System stage") || parentWorkstep.equalsIgnoreCase("Discard_CC")
					|| parentWorkstep.equalsIgnoreCase("CC_Discard")
					|| parentWorkstep.equalsIgnoreCase("Query_Resolution_Hold")
					|| parentWorkstep.equalsIgnoreCase("Recovery") || parentWorkstep.equalsIgnoreCase("Archive")) {
				String querySensitized = "Select " + RAROC_CUSTOMER_SENSITIZED_COLUMN + " from " + RAROC_CUSTOMER_TABLE
						+ " where wi_name='" + WorkitemId + "'";
				log.info("CustomerValidation ::: queryProp :" + querySensitized);

				List<List<String>> sOutput1 = obj.getDataFromDB(querySensitized);

				String[] fieldValue1 = new String[] { CUSTOMER_RAROC_APPROVED, BUSINESS_SEGMENT_APPROVED,
						INDUSTRY_SEGEMENT_APPROVED, IFRS_STAGING_APPROVED, COUNTERPARTY_TYPE_APPROVED1,
						INCOME_CASA_APPROVED, EXPORT_INCOME_APPROVED, FX_INCOME_APPROVED, OTHER_INCOME_APPROVED,
						FEE_INCM_AED_APPROVED, CROSS_SELL_INCM_APPROVED, CUSTOMER_NAME_APPROVED,
						CUST_INTRL_RATING_APPROVED, TOTAL_FUNDED_AED_APPROVED, TOTAL_NONFUNDED_AED_APPROVED,
						CUSTOMER_COUNTRY_APPROVED, INTERNAL_RATING_APPROVED, EXTERNAL_RATING_APPROVED,
						EXTERNAL_CODE_APPROVED, SALES_TURNOVER_APPROVED };

				log.info("fieldValue : " + fieldValue1.length);
				setMultipleNullValues(fieldValue1, sOutput1);
			}

		} catch (Exception e) {
			log.error("Exception in CustomerValidation: ", e);
		}
	}

	public String setHeaderData(IFormReference iFormReference) throws JSONException {

		log.info("inside setHeaderData");
		String retMsg = getReturnMessage(true);
		String workitemName = iFormReference.getObjGeneralData().getM_strProcessInstanceId();
		log.info("Value of workitemName" + workitemName);
		iFormReference.setValue("WI_NAME", workitemName);
		return retMsg;
	}

	public String getFlagValue(IFormReference iFormReference) {
		String column = "CUSTOMER_REALISED_CALL,GROUP_REALISED_CALL,FACILITY_REALISED_CALL";
		String query = "SELECT " + column + " FROM ALM_RAROC_EXT_TABLE WHERE WI_NAME='"
				+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "'";
		String outputXML = getDataFromDBWithColumns(query, column);
		XMLParser xmlParser = new XMLParser(outputXML);
		log.info("xmlParser" + xmlParser);
		int totalRetrieved = Integer.parseInt(xmlParser.getValueOf(Constants.TOTAL_RETRIEVED));
		if (totalRetrieved == 1) {
			return outputXML;
			// custReaCall =
			// xmlParser.getValueOf("CUSTOMER_REALISED_CALL")+","+xmlParser.getValueOf("GROUP_REALISED_CALL")+","+xmlParser.getValueOf("FACILITY_REALISED_CALL");
		}
		return null;

	}
//public void updateStageID(IFormReference iFormReference) {
//  try {
//  String wiName = "SELECT CA_WI_NAME  FROM ALM_RAROC_EXT_TABLE WHERE WI_NAME='"+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "'";
//  List<List<String>>  sOutput = formObject.getDataFromDB(wiName);
//  wiName =  sOutput.get(0).get(0);
//  String query = "SELECT CURRENT_WORKSTEPNAME  FROM ALM_CA_EXT_TABLE WHERE WI_NAME='"+ wiName + "'";
//  List<List<String>>  output = formObject.getDataFromDB(query);
//  String stageId =  sOutput.get(0).get(0);
//  String Stage_ID = "UPDATE ALM_RAROC_EXT_TABLE SET CURRENT_WORKSTEPNAME = "+stageId+" WHERE WI_NAME = '"
//  + formObject.getObjGeneralData().getM_strProcessInstanceId() + "'";
//  log.info("Update Stage_ID >>" + Stage_ID);
//  formObject.getDataFromDB(Stage_ID);
//  }catch (Exception e){
//

//  }

	public void updateStageID(IFormReference iFormReference) {
		try {
			log.info("inside updateStageID >>");
			String wiName = "SELECT CA_WI_NAME FROM ALM_RAROC_EXT_TABLE   WHERE WI_NAME='"
					+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "'";
			log.info("inside wiName >>" + wiName);
			List<List<String>> sOutput = formObject.getDataFromDB(wiName);
			log.info("inside sOutput >>" + sOutput);
			wiName = sOutput.get(0).get(0);
			log.info("inside sOutput >>" + wiName);
			String query = "SELECT CURRENT_WORKSTEPNAME,DECISION FROM ALM_CA_EXT_TABLE   WHERE WI_NAME='" + wiName
					+ "'";
			log.info("inside second query >>" + query);
			List<List<String>> output = formObject.getDataFromDB(query);
			String stageId = output.get(0).get(0);
			String parentDecision = output.get(0).get(1);
			iFormReference.setValue("CURRENT_WORKSTEPNAME", stageId);
			iFormReference.setValue("CA_DECISION", parentDecision);
			log.info("inside second stageId >>" + stageId + " parentDecision : " + parentDecision);
			String queryUpdate = "UPDATE ALM_RAROC_EXT_TABLE SET CURRENT_WORKSTEPNAME = '" + stageId + "',"
					+ " CA_DECISION = '" + parentDecision + "'  WHERE WI_NAME = '"
					+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "'";
			log.info("queryUpdate : " + queryUpdate);
			formObject.getDataFromDB(queryUpdate);
//			if (!stageId.equalsIgnoreCase("Initiate_Proposal") && !stageId.equalsIgnoreCase("HOCC_Assign")) {
//				iFormReference.setStyle("frame14", "disable", "true");
//				iFormReference.setStyle("projectedDetails", "disable", "true"); 
//			}

			if (stageId.equalsIgnoreCase("Initiate_Proposal") || stageId.equalsIgnoreCase("RM_Proposal_Review")
					|| stageId.equalsIgnoreCase("RO_Proposal_Modify")
					|| stageId.equalsIgnoreCase("UH_TL_Proposal_Review")
					|| stageId.equalsIgnoreCase("CBO_Proposal_Review")
					|| stageId.equalsIgnoreCase("RM_Query Resolutions") || stageId.equalsIgnoreCase("RM_Doc_Execution")
					|| stageId.equalsIgnoreCase("UH_TL_Def_Waiver")
					|| stageId.equalsIgnoreCase("Head_Corporate_Banking")
					|| stageId.equalsIgnoreCase("CA_Raise Queries to RM")
					|| stageId.equalsIgnoreCase("HOCC_Recommendation") || stageId.equalsIgnoreCase("CCO_Recommendation")
					|| stageId.equalsIgnoreCase("CSC_MRA_Prep") || stageId.equalsIgnoreCase("CC")
					|| stageId.equalsIgnoreCase("CA_FRS_Prep") || stageId.equalsIgnoreCase("HOCC_FRS_Review")
					|| stageId.equalsIgnoreCase("BCIC") || stageId.equalsIgnoreCase("HOCC_Def_Waive")
					|| stageId.equalsIgnoreCase("CCO_Def_Waiver") || stageId.equalsIgnoreCase("CA_Def_Waiver")
					|| stageId.equalsIgnoreCase("CC_Def_Waiver") || stageId.equalsIgnoreCase("CSU")
					|| stageId.equalsIgnoreCase("Credit") || stageId.equalsIgnoreCase("CA_Review_Analysis")
					|| stageId.equalsIgnoreCase("CA_HOCC_Analysis")) {
				iFormReference.setStyle("frame14", "disable", "false");
				iFormReference.setStyle("projectedDetails", "disable", "false");

			} else if (stageId.equalsIgnoreCase("CAD_Doc_Assign") || stageId.equalsIgnoreCase("Legal_Maker")
					|| stageId.equalsIgnoreCase("CAD_Doc_Verify") || stageId.equalsIgnoreCase("CAD_CCU_Limit_Load")
					|| stageId.equalsIgnoreCase("CAD_CCU_Limit_Load_Review") || stageId.equalsIgnoreCase("Exit")
					|| stageId.equalsIgnoreCase("CEO_Proposal_Review") || stageId.equalsIgnoreCase("CAD_Doc_Print")
					|| stageId.equalsIgnoreCase("CAD_Doc_Prep") || stageId.equalsIgnoreCase("Board")
					|| stageId.equalsIgnoreCase("CAD_Doc_Review") || stageId.equalsIgnoreCase("CAD_Doc_Approve")
					|| stageId.equalsIgnoreCase("Legal_Checker") || stageId.equalsIgnoreCase("TFS")
					|| stageId.equalsIgnoreCase("CAD_Def_waiver") || stageId.equalsIgnoreCase("Query_Resolution")
					|| stageId.equalsIgnoreCase("Docs_Upload_Scan") || stageId.equalsIgnoreCase("Hold")
					|| stageId.equalsIgnoreCase("Query") || stageId.equalsIgnoreCase("Discard")
					|| stageId.equalsIgnoreCase("Board_Discard") || stageId.equalsIgnoreCase("Email_Submit")
					|| stageId.equalsIgnoreCase("Email_SendBack") || stageId.equalsIgnoreCase("System stage")
					|| stageId.equalsIgnoreCase("Discard_CC") || stageId.equalsIgnoreCase("CC_Discard")
					|| stageId.equalsIgnoreCase("Query_Resolution_Hold") || stageId.equalsIgnoreCase("Recovery")) {
				iFormReference.setStyle("frame14", "disable", "true");
				iFormReference.setStyle("projectedDetails", "disable", "true");
			}

		} catch (Exception e) {

		}
	}

	@Override
	public String generateHTML(EControl arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCustomFilterXML(FormDef arg0, IFormReference arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String introduceWorkItemInWorkFlow(IFormReference arg0, HttpServletRequest arg1, HttpServletResponse arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String introduceWorkItemInWorkFlow(IFormReference arg0, HttpServletRequest arg1, HttpServletResponse arg2,
			WorkdeskModel arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setMaskedValue(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return arg1;
	}

	@Override
	public void updateDataInWidget(IFormReference arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public String validateDocumentConfiguration(String arg0, String arg1, File arg2, Locale arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray validateSubmittedForm(FormDef arg0, IFormReference arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setParentData() {
		log.info("Inside setParentData...");
		String ratingType = "";
		String internalRating = "";
		String salesTurnOver = "";
		try {
			List<List<String>> sOutputlist = getCADataWithColumn(NG_CA_COLUMN, CA_CUSTOMER_DETAILS);
			log.info("setParentData sOutputlist : " + sOutputlist);
			if (sOutputlist != null && !sOutputlist.isEmpty()) {
				String realSNP = sOutputlist.get(0).get(0);
				String propSNP = sOutputlist.get(0).get(1);
				String realFitch = sOutputlist.get(0).get(2);
				String propFitch = sOutputlist.get(0).get(3);
				String realMoody = sOutputlist.get(0).get(4);
				String propMoody = sOutputlist.get(0).get(5);
				if (!realSNP.isEmpty() || !propSNP.isEmpty()) {
					ratingType = "S&P";
					formObject.setValue("Q_NG_RAROC_CUSTOMER_external_rating_Realized", ratingType);
					formObject.setValue("Q_NG_RAROC_CUSTOMER_external_rating_Proposed", ratingType);
					formObject.setValue("Q_NG_RAROC_CUSTOMER_External_Code_Realized", realSNP);
					formObject.setValue("Q_NG_RAROC_CUSTOMER_External_Code_Proposed", propSNP);

				} else if (!realFitch.isEmpty() || !propFitch.isEmpty()) {
					ratingType = "Fitch";
					formObject.setValue("Q_NG_RAROC_CUSTOMER_external_rating_Realized", ratingType);
					formObject.setValue("Q_NG_RAROC_CUSTOMER_external_rating_Proposed", ratingType);
					formObject.setValue("Q_NG_RAROC_CUSTOMER_External_Code_Realized", realFitch);
					formObject.setValue("Q_NG_RAROC_CUSTOMER_External_Code_Proposed", propFitch);

				} else if (!realMoody.isEmpty() || !propMoody.isEmpty()) {
					ratingType = "Moody";
					formObject.setValue("Q_NG_RAROC_CUSTOMER_external_rating_Realized", ratingType);
					formObject.setValue("Q_NG_RAROC_CUSTOMER_external_rating_Proposed", ratingType);
					formObject.setValue("Q_NG_RAROC_CUSTOMER_External_Code_Realized", realMoody);
					formObject.setValue("Q_NG_RAROC_CUSTOMER_External_Code_Proposed", propMoody);
				} else {
					formObject.setValue("Q_NG_RAROC_CUSTOMER_external_rating_Realized", "NA");
					formObject.setValue("Q_NG_RAROC_CUSTOMER_external_rating_Proposed", "NA");
					formObject.setValue("Q_NG_RAROC_CUSTOMER_External_Code_Realized", "Unrated");
					formObject.setValue("Q_NG_RAROC_CUSTOMER_External_Code_Proposed", "Unrated");
				}
			} else {
				formObject.setValue("Q_NG_RAROC_CUSTOMER_external_rating_Realized", "NA");
				formObject.setValue("Q_NG_RAROC_CUSTOMER_external_rating_Proposed", "NA");
				formObject.setValue("Q_NG_RAROC_CUSTOMER_External_Code_Realized", "Unrated");
				formObject.setValue("Q_NG_RAROC_CUSTOMER_External_Code_Proposed", "Unrated");
			}
			String ProposedData = formObject.getValue("Q_NG_RAROC_CUSTOMER_External_Code_Realized").toString();
			formObject.setValue("Q_NG_RAROC_CUSTOMER_External_Code_Proposed", ProposedData);

			List<List<String>> sOutputlist1 = getCADataWithColumn(EXISTING_MRA, CA_MRA);
			log.info("setParentData sOutputlist1 : " + sOutputlist1);
			// if (sOutputlist1 != null && !sOutputlist1.isEmpty()) {
			internalRating = sOutputlist1.get(0).get(0);
			formObject.setValue("Q_NG_RAROC_CUSTOMER_internal_rating_Realized", internalRating);

			// start edit by mohit 02-09-2024 for setting value from raroc table once user
			// modifies /updates the values
			try {
				String checkInternalRatingPropQuery = "SELECT Internal_Rating_Proposed FROM NG_RAROC_CUSTOMER WHERE WI_Name ='"
						+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "'";
				log.info("checkInternalRatingPropQuery..." + checkInternalRatingPropQuery);

				List<List<String>> checkInternalRatingPropOut = formObject.getDataFromDB(checkInternalRatingPropQuery);
				String getInternalRatingProp = checkInternalRatingPropOut.get(0).get(0);
				log.info("getInternalRatingProp..." + getInternalRatingProp);

				if (getInternalRatingProp.equalsIgnoreCase("") || getInternalRatingProp.isEmpty()
						|| getInternalRatingProp.equalsIgnoreCase(null)) {
					log.info("inside if getInternalRatingProp is null or empty...");
					formObject.setValue("Q_NG_RAROC_CUSTOMER_internal_rating_Proposed", internalRating);
				} else {
					log.info("inside if getInternalRatingProp is not null or empty...");
					formObject.setValue("Q_NG_RAROC_CUSTOMER_internal_rating_Proposed", getInternalRatingProp);
				}
			} catch (Exception e) {
				log.error("Exception in checkInternalRatingPropQuery : " + e);
				formObject.setValue("Q_NG_RAROC_CUSTOMER_internal_rating_Proposed", internalRating);
			}

			// end edit by mohit 02-09-2024 for setting value from raroc table once user
			// modifies /updates the values
			// }
			List<List<String>> sOutputlist2 = getCADataWithColumn(REVENUE, CA_INHOUSE_FINANCIALS);
			log.info("setParentData sOutputlist2 : " + sOutputlist2);
			if (sOutputlist2 != null && !sOutputlist2.isEmpty()) {
				salesTurnOver = sOutputlist2.get(0).get(0);
				formObject.setValue("Q_NG_RAROC_CUSTOMER_sales_turnover_Realized", salesTurnOver);
				formObject.setValue("Q_NG_RAROC_CUSTOMER_sales_turnover_Proposed", salesTurnOver);
			}
			String UpdateQuery = "UPDATE NG_RAROC_CUSTOMER SET Internal_Rating_Realized = '" + internalRating + "' , "
					+ " Sales_Turnover_Realized = '" + salesTurnOver + "'" + "WHERE WI_NAME = '"
					+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "'";

			log.info("Update Query >>" + UpdateQuery);
			formObject.getDataFromDB(UpdateQuery);

		} catch (Exception e) {
			log.error("Exception : " + e);
		}
	}

	public static void getRimNumber(IFormReference iformrefrence) {
		String customerRim = iformrefrence.getValue("CUSTOMER_RIM").toString();
		String[] columnArray = customerRim.split(",");
		log.info("customerRim length@@@@ " + columnArray.length);
		// START prod_05112024
		
		/*String comboT24ID = iformrefrence.getValue("Q_NG_RAROC_CUSTOMER_DETAILS_CUSTOMER_T24_ID").toString();
		log.info("comboT24ID...###"+comboT24ID);
		Boolean checkflag= Arrays.stream(columnArray).anyMatch(comboT24ID::equals);
		log.info("checkflag...####"+checkflag);
		if (comboT24ID.equalsIgnoreCase("") || comboT24ID.equalsIgnoreCase(null)|| !(Arrays.stream(columnArray).anyMatch(comboT24ID::equals))) {
			log.info("Inside comboT24ID combobox is empty or doesnt match with all rims");
			//iformrefrence.setStyle("Q_NG_RAROC_CUSTOMER_DETAILS_CUSTOMER_T24_ID", "disable", "false");
			iformrefrence.clearCombo("Q_NG_RAROC_CUSTOMER_DETAILS_CUSTOMER_T24_ID");
			if (columnArray.length == 1) {
				log.info("Inside columnArray is equal to 1");
				iformrefrence.setValue("LEAD_REF_NO", customerRim);
				iformrefrence.addItemInCombo("Q_NG_RAROC_CUSTOMER_DETAILS_CUSTOMER_T24_ID", customerRim);
				iformrefrence.setValue("Q_NG_RAROC_CUSTOMER_DETAILS_CUSTOMER_T24_ID", customerRim);
				iformrefrence.setStyle("Q_NG_RAROC_CUSTOMER_DETAILS_CUSTOMER_T24_ID", "disable", "true");
			} else {
				log.info("Inside columnArray is not equal to 1");
				for (int i = 0; i < columnArray.length; i++) {
					String customerRimVal = columnArray[i];
					log.info("customerRimVal : " + customerRimVal);
					iformrefrence.addItemInCombo("Q_NG_RAROC_CUSTOMER_DETAILS_CUSTOMER_T24_ID", customerRimVal);	
				}
				iformrefrence.setStyle("Q_NG_RAROC_CUSTOMER_DETAILS_CUSTOMER_T24_ID", "disable", "false");
				log.info("combobox should be enabled....");
			}
			
		}*/
		// END prod_05112024
		if (columnArray.length == 1) {
			iformrefrence.setValue("LEAD_REF_NO", customerRim);
			iformrefrence.setStyle("Q_NG_RAROC_CUSTOMER_DETAILS_CUSTOMER_T24_ID", "disable", "true");
		} else {
			for (int i = 0; i < columnArray.length; i++) {
				String customerRimVal = columnArray[i];
				log.info("customerRimVal : " + customerRimVal);
				iformrefrence.addItemInCombo("Q_NG_RAROC_CUSTOMER_DETAILS_CUSTOMER_T24_ID", customerRimVal);
			}
		}

	}

	// start edit by mohit 16-07-2024 to validate if allocation percentage is more
	// than 100%
	public static String validateCFMGrid(IFormReference iformrefrence) {
		String retMsg = "";
		try {
			log.info("Inside validateCFMGrid....");
			JSONArray opt = iformrefrence.getDataFromGrid(CFM_GRID);
			int gridCount = opt == null ? 0 : opt.size();
			float allocationPercCount = 0;
			log.info("gridCount..." + gridCount);
			int rowIndex = gridCount - 1;

			log.info("rowIndex.." + rowIndex);
			// int rowIndices[] = new int[gridCount];
			List<Integer> deleteRows = new ArrayList<>();
			if (gridCount != 0) {
				for (int i = 0; i <= rowIndex; i++) {
					String sAllocationPerc = iformrefrence.getTableCellValue(CFM_GRID, i, 5);// allocation_perc
					log.info("sAllocationPerc==>" + sAllocationPerc);
					log.info("i..." + i + ",sAllocationPerc " + Float.parseFloat(sAllocationPerc));
					allocationPercCount = allocationPercCount + Float.parseFloat(sAllocationPerc);
					log.info("i is " + i + ",allocationPercCount " + sAllocationPerc);
					// rowIndices[i] = i;

				}

			}
			allocationPercCount = allocationPercCount
					+ Integer.parseInt(iformrefrence.getValue("CFM_COLLATERAL_ALLOCATION_PERCENTAGE_PROP").toString());

			if (allocationPercCount > 100) {
				log.info("inside if allocationPercCount greater than 100..." + allocationPercCount);
				// deleteRows.add(i);
				retMsg = "Allocation percentage more than 100. Please change.";
			}
			log.info("final allocationPercCount=" + allocationPercCount);
			// String result = deleteRowCFMGrid(formObject,deleteRows);//,rowIndices);
			// log.info("result msg..."+result);

		} catch (Exception e) {
			log.error(" validateCFMGrid Exception : ", e);
		}
		return retMsg;
	}

	// start edit by mohit 09-09-2024 for Collateral Number and New Collateral Type
	// mapping 1-1.
	public String validateColNoNewColType(IFormReference iformrefrence) {
		log.info("Inside validateColNoNewColType function#####");
		String retMsg = "";
		try {
			String parentWorkstep = formObject.getValue("CURRENT_WORKSTEPNAME").toString();
			JSONArray opt = iformrefrence.getDataFromGrid(CFM_GRID);
			int gridCount = opt == null ? 0 : opt.size();
			// float allocationPercCount = 0;
			log.info("gridCount..." + gridCount);
			int rowIndex = gridCount - 1;

			if (parentWorkstep.equalsIgnoreCase("Initiate_Proposal")
					|| parentWorkstep.equalsIgnoreCase("RM_Proposal_Review")
					|| parentWorkstep.equalsIgnoreCase("RO_Proposal_Modify")
					|| parentWorkstep.equalsIgnoreCase("UH_TL_Proposal_Review")
					|| parentWorkstep.equalsIgnoreCase("CBO_Proposal_Review")
					|| parentWorkstep.equalsIgnoreCase("RM_Query Resolutions")
					|| parentWorkstep.equalsIgnoreCase("RM_Doc_Execution")
					|| parentWorkstep.equalsIgnoreCase("UH_TL_Def_Waiver")
					|| parentWorkstep.equalsIgnoreCase("Head_Corporate_Banking")
					|| parentWorkstep.equalsIgnoreCase("CA_Raise Queries to RM")
					|| parentWorkstep.equalsIgnoreCase("HOCC_Recommendation")
					|| parentWorkstep.equalsIgnoreCase("CCO_Recommendation")
					|| parentWorkstep.equalsIgnoreCase("CSC_MRA_Prep") || parentWorkstep.equalsIgnoreCase("CC")
					|| parentWorkstep.equalsIgnoreCase("CA_FRS_Prep")
					|| parentWorkstep.equalsIgnoreCase("HOCC_FRS_Review") || parentWorkstep.equalsIgnoreCase("BCIC")
					|| parentWorkstep.equalsIgnoreCase("HOCC_Def_Waive")
					|| parentWorkstep.equalsIgnoreCase("CCO_Def_Waiver")
					|| parentWorkstep.equalsIgnoreCase("CA_Def_Waiver")
					|| parentWorkstep.equalsIgnoreCase("CC_Def_Waiver") || parentWorkstep.equalsIgnoreCase("CSU")
					|| parentWorkstep.equalsIgnoreCase("Credit")
					|| parentWorkstep.equalsIgnoreCase("CA_Review_Analysis")
					|| parentWorkstep.equalsIgnoreCase("CA_HOCC_Analysis")) {

				/*
				 * String validateColNoNewColTypeQuery =
				 * "select collateral_number_prop,new_collateral_type FROM NG_RAROC_COLLATERAL_FACILITY_MAP "
				 * + " where wi_name = '" +
				 * formObject.getObjGeneralData().getM_strProcessInstanceId() + "'";
				 * log.info("validateColNoNewColTypeQuery...." + validateColNoNewColTypeQuery);
				 * List<List<String>> ColNoNewColTypeOut =
				 * formObject.getDataFromDB(validateColNoNewColTypeQuery);
				 * log.info("ColNoNewColTypeOut.size..." + ColNoNewColTypeOut.size()); for (int
				 * i = 0; i < ColNoNewColTypeOut.size(); i++) {
				 * log.info("Inside 1st for loop i="+i); String sColNo =
				 * ColNoNewColTypeOut.get(i).get(0); log.info("sColNo ..." + sColNo); String
				 * sNewColType = ColNoNewColTypeOut.get(i).get(1); log.info("sNewColType ..." +
				 * sNewColType);
				 */
				if (gridCount != 0) {
					for (int j = 0; j <= rowIndex; j++) {
						log.info("Inside 2nd for loop j=" + j);
						String sColNoOnGrid = iformrefrence.getTableCellValue(CFM_GRID, j, 4);// 4is collateral_number
																								// prop
						String sNewColTypeOnGrid = iformrefrence.getTableCellValue(CFM_GRID, j, 74);// 74is
																									// new_collateral_type
						log.info("sColNoOnForm..." + sColNoOnGrid + "...sNewColTypeOnForm..." + sNewColTypeOnGrid
								+ "...");

						String sNewColType = formObject.getValue("New_Collateral_Type").toString();
						String sColNo = formObject.getValue("CFM_COLLATERAL_NUMBER_PROP").toString();
						log.info("sColNo..." + sColNo + "--sNewColType..." + sNewColType + "!");

						if (sColNoOnGrid.equalsIgnoreCase(sColNo) && !sNewColTypeOnGrid.equalsIgnoreCase(sNewColType)) {
							log.info("Inside if condition sNewColType and sNewColTypeOnForm does not match.");
							retMsg = "This " + sColNoOnGrid + " Collateral Number is already mapped to "
									+ sNewColTypeOnGrid;
							break;
						} else {
							retMsg = "Col No and New Col Type added";
						}
					}

				}
			}

			if (parentWorkstep.equalsIgnoreCase("CA_Raise Queries to RM")
					|| parentWorkstep.equalsIgnoreCase("HOCC_Recommendation")
					|| parentWorkstep.equalsIgnoreCase("CCO_Recommendation")
					|| parentWorkstep.equalsIgnoreCase("CSC_MRA_Prep") || parentWorkstep.equalsIgnoreCase("CC")
					|| parentWorkstep.equalsIgnoreCase("CA_FRS_Prep")
					|| parentWorkstep.equalsIgnoreCase("HOCC_FRS_Review") || parentWorkstep.equalsIgnoreCase("BCIC")
					|| parentWorkstep.equalsIgnoreCase("HOCC_Def_Waive")
					|| parentWorkstep.equalsIgnoreCase("CCO_Def_Waiver")
					|| parentWorkstep.equalsIgnoreCase("CA_Def_Waiver")
					|| parentWorkstep.equalsIgnoreCase("CC_Def_Waiver") || parentWorkstep.equalsIgnoreCase("CSU")
					|| parentWorkstep.equalsIgnoreCase("Credit")
					|| parentWorkstep.equalsIgnoreCase("CA_Review_Analysis")
					|| parentWorkstep.equalsIgnoreCase("HOCC_Assign")) {

			}

		} catch (Exception e) {
			log.error(" validateColNoNewColType Exception : ", e);
		}
		return retMsg;
	}

	// end edit by mohit 29-07-2024 for Collateral Number and New Collateral Type
	// mapping 1-1.
	public void setCFMPropDisable(IFormReference iformreference) {
		try {
			log.info("Inside setCFMPropDisable function ....");
			iformreference.setStyle("CFM_COLLATERAL_TYPE_PROP", "disable", "true");
			iformreference.setStyle("CFM_COLLATERAL_NAME_PROP", "disable", "true");
			iformreference.setStyle("CFM_COLLATERAL_NUMBER_PROP", "disable", "true");
			iformreference.setStyle("CFM_COLLATERAL_CURRENCY_PROP", "disable", "true");
			iformreference.setStyle("CFM_COLLATERAL_AMOUNT_PROP", "disable", "true");
			iformreference.setStyle("CFM_COLLATERAL_LIEN_AMOUNT_PROP", "disable", "true");
			iformreference.setStyle("CFM_COLLATERAL_LIEN_INTEREST_PROP", "disable", "true");
			iformreference.setStyle("CFM_COLLATERAL_LIEN_TENOR_PROP", "disable", "true");
			iformreference.setStyle("CFM_COLLATERAL_ALLOCATION_PERCENTAGE_PROP", "disable", "true");
			iformreference.setStyle("CFM_COLLATERAL_TOTAL_AMOUNT_PROP", "disable", "true");
			iformreference.setStyle("CFM_COLLATERAL_AMOUNT_UTILIZED_PROP", "disable", "true");
		} catch (Exception e) {
			log.error(" setCFMPropDisable Exception : ", e);
		}
	}

	// end edit by mohit 29-07-2024
	// start edit by mohit 29-07-2024 for CFM Grid Sens data to be copied from prop
	// fields
	public void setCFMSensFields(IFormReference iformreference) {
		try {
			log.info("Inside setCFMSensFields function ....");
			String[] fieldValueSens = new String[] { COLLATERAL_TYPE_SENS, COLLATERAL_NAME_SENS, COLLATERAL_NUMBER_SENS,
					COLLATERAL_CURRENCY_SENS, COLLATERAL_AMOUNT_SENS, COLLATERAL_LIEN_AMOUNT_SENS,
					COLLATERAL_LIEN_INTEREST_SENS, COLLATERAL_LIEN_TENOR_SENS, COLLATERAL_ALLOCATION_PERCENTAGE_SENS,
					COLLATERAL_TOTAL_AMOUNT_SENS, COLLATERAL_AMOUNT_UTILIZED_SENS };

			String colSensCheck = "COLLATERAL_AMOUNT_SENS";
			String querySensCheck = "SELECT " + colSensCheck + " FROM NG_RAROC_COLLATERAL_FACILITY_MAP WHERE WI_NAME='"
					+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "'";
			log.info("querySensCheck setCFMSensFields>>>>>> : " + querySensCheck);
			String outputSensCheck = getDataFromDBWithColumns(querySensCheck, colSensCheck);
			XMLParser xmlParserSensCheck = new XMLParser(outputSensCheck);
			log.info("setCFMSensFields xmlParserSensCheck " + xmlParserSensCheck);
			int totalRetrievedSensCheck = Integer.parseInt(xmlParserSensCheck.getValueOf(Constants.TOTAL_RETRIEVED));
			log.info(" setCFMSensFields totalRetrievedSensCheck..." + totalRetrievedSensCheck);

			JSONArray opt = iformreference.getDataFromGrid(CFM_GRID);
			int gridCount = opt == null ? 0 : opt.size();
			float allocationPercCount = 0;
			log.info("gridCount..." + gridCount);
			int rowIndex = gridCount - 1;

			if (totalRetrievedSensCheck != 0) {

				XMLParser rowParserSensCheck = new XMLParser(outputSensCheck);
				String sValueSensCheck = rowParserSensCheck.getValueOf("COLLATERAL_AMOUNT_SENS");
				if (sValueSensCheck == null || sValueSensCheck.equals("") || sValueSensCheck.isEmpty()) {
					log.info("Inside setCFMSensFields if sValueSensCheck is..." + sValueSensCheck + "+++");
					// getUpdateData(CFM_TABLE, CFM_SENSITIZED_UPDATE);
				} else {
					setFacilityDataCFM(CFM_SENSITIZED_COLUMN, CFM_TABLE, fieldValueSens);
				}
			}
		} catch (Exception e) {
			log.error(" setCFMSensFields Exception : ", e);
		}
	}

	// end edit by mohit 29-07-2024 for CFM Grid Sens data to be copied from prop
	// fields

	/*
	 * public static String deleteRowCFMGrid(IFormReference
	 * iformrefrence,List<Integer> deleteRows) {//,int[]rowIndices) { try {
	 * log.info("Inside deleteRowCFMGrid function ..."); log.info(
	 * "Inside deleteRowCFMGrid function....deleteRows= " + deleteRows); int[]
	 * intArray = deleteRows.stream().mapToInt(Integer::intValue).toArray(); //
	 * iformrefrence.deleteRowsFromGrid(CFM_GRID,intArray);
	 * 
	 * }catch (Exception e) { log.error(" validateCFMGrid Exception : ", e); }
	 * 
	 * return "Allocation percentage more than 100 is deleted"; }
	 */
	// end edit by mohit 16-07-2024 to validate if allocation percentage is more
	// than 100%

	// start changes by shikha 18-07-2024
	public static String getCollatoralNo(IFormReference ifr) {
		try {
			String prefix = "";
			String custID = (String) ifr.getValue("Q_NG_RAROC_CUSTOMER_DETAILS_Customer_ID");
			log.info(" Customer ID  = " + custID);
			String collatoralType = (String) ifr.getValue("Customer_Collateral_Type");
			// Determine the prefix based on the collateral type
			switch (collatoralType) {
			case "Aircraft Mortgage":
				prefix = "AC";
				break;
			case "Assignment":
				prefix = "AS";
				break;
			case "Assignment of Rights":
				prefix = "AR";
				break;
			case "Cash":
				prefix = "CA";
				break;
			case "Cheque":
				prefix = "CH";
				break;
			case "Commercial Mortgage":
				prefix = "CM";
				break;
			case "Financial Guarantee":
				prefix = "FG";
				break;
			case "Gold":
				prefix = "GO";
				break;
			case "Hypothecation":
				prefix = "HY";
				break;
			case "Insurance":
				prefix = "IN";
				break;
			case "Islamic Others":
				prefix = "IO";
				break;
			case "Legal Mortgage":
				prefix = "LM";
				break;
			case "Lien Deposit":
				prefix = "LD";
				break;
			case "Marketable Securities":
				prefix = "MS";
				break;
			case "Mortgage":
				prefix = "MO";
				break;
			case "Non-marketable Securities":
				prefix = "NS";
				break;
			case "Other Guarantees":
				prefix = "OG";
				break;
			case "Others":
				prefix = "OT";
				break;
			case "Pledge":
				prefix = "PL";
				break;
			case "Sovereign Guarantee":
				prefix = "SG";
				break;
			case "Stock":
				prefix = "ST";
				break;
			case "Vehicle Mortgage":
				prefix = "VM";
				break;
			case "Vessel Mortgage":
				prefix = "VL";
				break;
			default:
				log.info("Unknown Collateral Type");
				return "";
			}

			String sqlIdentifier = "SELECT VAL = NEXT VALUE FOR NG_SEQ_COLLATERAL_NO";
			List<List<String>> result1 = ifr.getDataFromDB(sqlIdentifier);
			log.info("result1  = " + result1);

			long sequenceValue = 0;
			if (result1 != null && !result1.isEmpty() && result1.get(0).get(0) != null
					&& !result1.get(0).get(0).isEmpty()) {
				sequenceValue = Long.parseLong(result1.get(0).get(0));
			}

			log.info("Sequence = " + sequenceValue);

			// Format the sequence value to be 10 digits long
			String val = String.format("%010d", sequenceValue);

			log.info("Returning = " + custID + "-" + prefix + "-" + val);

			return custID + "-" + prefix + "-" + val;
		} catch (Exception ex) {
			log.info("Exception in catch: " + ex.getMessage());
			return "";
		}
	}

	// end changes by shikha 18-07-2024
	// start edit by mohit 05-08-2024 to populate prev_approved fields based on
	// customer no and facility_ids
	public void populatePreApprovedFields(String selectedFacilityID) {
		log.info("Inside populatePreApprovedFields++++++");
		try {
			String lead_ref_no = formObject.getValue(LEAD_REF_NO).toString();

			/*
			 * by mohit 10-09-2024 to include the archive condition. String Prev_Wi_Name =
			 * "SELECT TOP 1 ca_wi_name FROM ALM_RAROC_EXT_TABLE NOLOCK WHERE lead_Ref_No = '"
			 * + lead_ref_no + "' and wi_name != '" +
			 * formObject.getObjGeneralData().getM_strProcessInstanceId() +
			 * "' ORDER BY WI_Name DESC";
			 */
			String Prev_Wi_Name = "SELECT TOP 1 a.ca_wi_name FROM ALM_RAROC_EXT_TABLE a JOIN ALM_CA_EXT_TABLE b ON a.ca_wi_name = b.WI_NAME WHERE a.lead_Ref_No = '"
					+ lead_ref_no + "' and a.wi_name != '" + formObject.getObjGeneralData().getM_strProcessInstanceId()
					+ "' " + " AND b.current_workstepname ='Archive' ORDER BY a.WI_Name DESC";
			String Wi_Name = "SELECT  WI_NAME FROM ALM_RAROC_EXT_TABLE WHERE CA_WI_NAME = (" + Prev_Wi_Name + ")";

			String Raroc_Facility_ID = "SELECT facility_id FROM NG_RAROC_FACILITY_DETAILS WHERE WI_NAME = (" + Wi_Name
					+ ")";

			log.info("iNSIDE Facilitytab queryApprov :" + Raroc_Facility_ID);
			List<List<String>> sOutput3 = formObject.getDataFromDB(Raroc_Facility_ID);

			for (int i = 0; i < sOutput3.size(); i++) {
				String Facility_IDs = sOutput3.get(i).get(0);
				log.info("selectedFacilityID..." + selectedFacilityID + "...i=" + i);
				if (selectedFacilityID.equalsIgnoreCase(Facility_IDs)) {
					// extra
					/*
					 * String Approved = "SELECT TOP 1 " + RAROC_FACILITY_APPROVE_COLUMN + " FROM "
					 * + RAROC_FACILITY_DETAILS_TABLE + " WHERE FACILITY_ID = '" + Facility_IDs +
					 * "'";
					 */ // by mohit 14-08-2024
					String Approved = "SELECT TOP 1 " + RAROC_FACILITY_APPROVE_COLUMN + " FROM "
							+ RAROC_FACILITY_DETAILS_TABLE + " WHERE FACILITY_ID = '" + Facility_IDs
							+ "' and WI_NAME =(" + Wi_Name + ")";

					log.info("INSIDE Facilitytab ApprovedQuery-->" + Approved);

					List<List<String>> Approved_Data = formObject.getDataFromDB(Approved);
					for (int j = 0; j < Approved_Data.size(); j++) {

						String[] ColumnArray = RAROC_FACILITY_PREV_APPR_COLUMN.split(",");
						StringBuilder sb = new StringBuilder();
						for (int f = 0; f < ColumnArray.length; f++) {

							sb.append(ColumnArray[f] + "= '" + Approved_Data.get(0).get(f) + "',");

						}

						String UpdateData = sb.toString().substring(0, sb.length() - 1);

						log.info("iNSIDE Currency previously Appr" + UpdateData);

						String PrevAppr = "UPDATE " + RAROC_FACILITY_DETAILS_TABLE + " SET " + UpdateData
								+ " where FACILITY_ID = '" + Facility_IDs + "' and wi_name = '"
								+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "'";

						log.info("iNSIDE Facilitytab PrevApprData :" + PrevAppr);
						formObject.saveDataInDB(PrevAppr);

					}

				}
			}
		} catch (Exception e) {
			log.error("Exception : " + e);
		}
	}
	// end edit by mohit 05-08-2024 to populate prev_approved fields based on

	// start edit by mohit 09-08-2024 for setting Index Description
	// Start change by reshank on 21-08-24
	public void setIndexDescSens(String facilityId) {
		log.info("Inside setIndexDescSens function!!!!!");
		try {
			String setIndexKeySensQuery = "select INDEX_KEY_SENSITIZED,INDEX_SENSITIZED,INDEX_TENOR_SENSITIZED,INDEX_TENOR_UNIT_SENSITIZED,INDEX_RATE_SENSITIZED,facility_type from NG_RAROC_FACILITY_DETAILS where wi_name='"
					+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "' and facility_id='" + facilityId
					+ "'";// this is repayment type value
			log.info("setIndexKeySensQuery..." + setIndexKeySensQuery);

			List<List<String>> setIndexKeySensOut = formObject.getDataFromDB(setIndexKeySensQuery);
			log.info("setIndexKeyPropOut...." + setIndexKeySensOut);

			String setIndexKeySens = setIndexKeySensOut.get(0).get(0);
			String setIndexSens = setIndexKeySensOut.get(0).get(1);
			String setIndexTenorSens = setIndexKeySensOut.get(0).get(2);
			String setIndexTenorUnitSens = setIndexKeySensOut.get(0).get(3);
			String setIndexRateSens = setIndexKeySensOut.get(0).get(4);
			String checkFacilityType = setIndexKeySensOut.get(0).get(5);
			log.info("setIndexKeyProp..." + setIndexKeySens);
			log.info("setIndexProp..." + setIndexSens);
			log.info("setIndexTenorProp..." + setIndexTenorSens);
			log.info("setIndexTenorUnitProp..." + setIndexTenorUnitSens);
			log.info("setIndexRateProp..." + setIndexRateSens);
			log.info("checkFacilityType..." + checkFacilityType);

			if (checkFacilityType.equalsIgnoreCase("Non-Funded")) {
				log.info("Inside checkFacilityType is non-funded!!!!");
				formObject.clearCombo(INDEX_KEY_SENSITIZED);
				formObject.addItemInCombo(INDEX_KEY_SENSITIZED, "None", "None");

				formObject.setValue(INDEX_KEY_SENSITIZED, "None");

				formObject.clearCombo(INDEX_SENSITIZED);
				formObject.setStyle(INDEX_SENSITIZED, "disable", "true");

				formObject.clearCombo(INDEX_TENOR_SENSITIZED);
				formObject.setStyle(INDEX_TENOR_SENSITIZED, "disable", "true");

				// formObject.clearCombo(INDEX_TENOR_UNIT_PROPOSED);//M

				formObject.setValue(INDEX_RATE_SENSITIZED, "");

			}
			if (checkFacilityType.equalsIgnoreCase("Funded")) {
				log.info("Inside checkFacilityType is funded!!!!!");
				formObject.clearCombo(INDEX_KEY_SENSITIZED);
				formObject.addItemInCombo(INDEX_KEY_SENSITIZED, "EIBOR", "EIBOR");
				formObject.addItemInCombo(INDEX_KEY_SENSITIZED, "SOFR", "SOFR");

				String parentWorkstep = formObject.getValue("CURRENT_WORKSTEPNAME").toString();
				if (parentWorkstep.equalsIgnoreCase("CA_Raise Queries to RM")
						|| parentWorkstep.equalsIgnoreCase("HOCC_Recommendation")
						|| parentWorkstep.equalsIgnoreCase("CCO_Recommendation")
						|| parentWorkstep.equalsIgnoreCase("CSC_MRA_Prep") || parentWorkstep.equalsIgnoreCase("CC")
						|| parentWorkstep.equalsIgnoreCase("CA_FRS_Prep")
						|| parentWorkstep.equalsIgnoreCase("HOCC_FRS_Review") || parentWorkstep.equalsIgnoreCase("BCIC")
						|| parentWorkstep.equalsIgnoreCase("HOCC_Def_Waive")
						|| parentWorkstep.equalsIgnoreCase("CCO_Def_Waiver")
						|| parentWorkstep.equalsIgnoreCase("CA_Def_Waiver")
						|| parentWorkstep.equalsIgnoreCase("CC_Def_Waiver") || parentWorkstep.equalsIgnoreCase("CSU")
						|| parentWorkstep.equalsIgnoreCase("Credit")
						|| parentWorkstep.equalsIgnoreCase("CA_Review_Analysis")
						|| parentWorkstep.equalsIgnoreCase("HOCC_Assign")) {
					log.info("inside this parentWorkstep condition not initiator.....");
					formObject.setStyle(INDEX_SENSITIZED, "disable", "false");
					formObject.setStyle(INDEX_TENOR_SENSITIZED, "disable", "false");
				} /*
					 * else { log.info("inside this parentWorkstep condition is initiator.....");
					 * formObject.setStyle(INDEX_SENSITIZED, "disable", "true");
					 * formObject.setStyle(INDEX_TENOR_SENSITIZED, "disable", "true"); }
					 */

				if (setIndexKeySens.equalsIgnoreCase("SOFR")) {
					log.info("Inside SOFR if ...");
					formObject.setValue(INDEX_KEY_SENSITIZED, "SOFR");

					formObject.clearCombo(INDEX_SENSITIZED);
					formObject.addItemInCombo(INDEX_SENSITIZED, "REF-SOFR", "REF-SOFR");
					formObject.setValue(INDEX_SENSITIZED, "REF-SOFR");

					formObject.clearCombo(INDEX_TENOR_SENSITIZED);
					formObject.addItemInCombo(INDEX_TENOR_SENSITIZED, "1", "1");
					formObject.addItemInCombo(INDEX_TENOR_SENSITIZED, "3", "3");
					formObject.addItemInCombo(INDEX_TENOR_SENSITIZED, "6", "6");
					formObject.addItemInCombo(INDEX_TENOR_SENSITIZED, "12", "12");
					formObject.setValue(INDEX_TENOR_SENSITIZED, setIndexTenorSens);

					// 22M
					/*
					 * formObject.setStyle(INDEX_TENOR_UNIT_PROPOSED, "disable", "false");
					 * formObject.setValue(INDEX_TENOR_UNIT_PROPOSED,"M");//M
					 * log.info("INDEX_TENOR_UNIT_PROPOSED " +setIndexTenorUnitProp);
					 * formObject.setStyle(INDEX_TENOR_UNIT_PROPOSED, "disable", "true");
					 */
					formObject.setValue(INDEX_RATE_SENSITIZED, setIndexRateSens);

				}
				if (setIndexKeySens.equalsIgnoreCase("EIBOR")) {
					log.info("Inside EIBOR if ...");
					formObject.setValue(INDEX_KEY_SENSITIZED, "EIBOR");

//					formObject.setStyle(INDEX_SENSITIZED, "disable", "false");
					formObject.clearCombo(INDEX_SENSITIZED);
					formObject.addItemInCombo(INDEX_SENSITIZED, "REF - CB EIBOR", "REF - CB EIBOR");
					formObject.setValue(INDEX_SENSITIZED, "REF - CB EIBOR");

//					formObject.setStyle(INDEX_TENOR_SENSITIZED, "disable", "false");
					formObject.clearCombo(INDEX_TENOR_SENSITIZED);
					formObject.addItemInCombo(INDEX_TENOR_SENSITIZED, "1", "1");
					formObject.addItemInCombo(INDEX_TENOR_SENSITIZED, "3", "3");
					formObject.addItemInCombo(INDEX_TENOR_SENSITIZED, "6", "6");
					formObject.addItemInCombo(INDEX_TENOR_SENSITIZED, "12", "12");
					formObject.setValue(INDEX_TENOR_SENSITIZED, setIndexTenorSens);
					// 22M
					/*
					 * formObject.setStyle(INDEX_TENOR_UNIT_PROPOSED, "disable", "false");
					 * formObject.setValue(INDEX_TENOR_UNIT_PROPOSED,"M");//M
					 * formObject.setStyle(INDEX_TENOR_UNIT_PROPOSED, "disable", "true");
					 */
					formObject.setValue(INDEX_RATE_SENSITIZED, setIndexRateSens);

				}
			}

		} catch (Exception e) {
			log.error("Exception : " + e);
		}
	}

	public void setIndexDescProp(String facilityId) {
		log.info("Inside setIndexDescProp function!!!!!");
		try {
			String setIndexKeyPropQuery = "select INDEX_KEY_PROPOSED,Index_Proposed,INDEX_TENOR_PROPOSED,INDEX_TENOR_UNIT_PROPOSED,Index_Rate_Proposed,facility_type  from NG_RAROC_FACILITY_DETAILS where wi_name='"
					+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "' and facility_id='" + facilityId
					+ "'";// this is repayment type value
			log.info("setIndexKeyPropQuery..." + setIndexKeyPropQuery);

			List<List<String>> setIndexKeyPropOut = formObject.getDataFromDB(setIndexKeyPropQuery);
			log.info("setIndexKeyPropOut...." + setIndexKeyPropOut);

			String setIndexKeyProp = setIndexKeyPropOut.get(0).get(0);
			String setIndexProp = setIndexKeyPropOut.get(0).get(1);
			String setIndexTenorProp = setIndexKeyPropOut.get(0).get(2);
			String setIndexTenorUnitProp = setIndexKeyPropOut.get(0).get(3);
			String setIndexRateProp = setIndexKeyPropOut.get(0).get(4);
			String checkFacilityType = setIndexKeyPropOut.get(0).get(5);
			log.info("setIndexKeyProp..." + setIndexKeyProp);
			log.info("setIndexProp..." + setIndexProp);
			log.info("setIndexTenorProp..." + setIndexTenorProp);
			log.info("setIndexTenorUnitProp..." + setIndexTenorUnitProp);
			log.info("setIndexRateProp..." + setIndexRateProp);
			log.info("checkFacilityType..." + checkFacilityType);

			if (checkFacilityType.equalsIgnoreCase("Non-Funded")) {
				log.info("Inside checkFacilityType is non-funded!!!!");
				formObject.clearCombo(INDEX_KEY_PROPOSED);
				formObject.addItemInCombo(INDEX_KEY_PROPOSED, "None", "None");

				formObject.setValue(INDEX_KEY_PROPOSED, "None");

				formObject.clearCombo(INDEX_PROPOSED);
				formObject.setStyle(INDEX_PROPOSED, "disable", "true");

				formObject.clearCombo(INDEX_TENOR_PROPOSED);
				formObject.setStyle(INDEX_TENOR_PROPOSED, "disable", "true");

				// formObject.clearCombo(INDEX_TENOR_UNIT_PROPOSED);//M

				formObject.setValue(INDEX_RATE_PROPOSED, "");

			}
			if (checkFacilityType.equalsIgnoreCase("Funded")) {
				log.info("Inside checkFacilityType is funded!!!!!");
				formObject.clearCombo(INDEX_KEY_PROPOSED);
				formObject.addItemInCombo(INDEX_KEY_PROPOSED, "EIBOR", "EIBOR");
				formObject.addItemInCombo(INDEX_KEY_PROPOSED, "SOFR", "SOFR");

				String parentWorkstep = formObject.getValue("CURRENT_WORKSTEPNAME").toString();
				log.info("parentWorkstep@@@@" + parentWorkstep);
				if (parentWorkstep.equalsIgnoreCase("Initiate_Proposal")
						|| parentWorkstep.equalsIgnoreCase("RM_Proposal_Review")
						|| parentWorkstep.equalsIgnoreCase("RO_Proposal_Modify")
						|| parentWorkstep.equalsIgnoreCase("UH_TL_Proposal_Review")
						|| parentWorkstep.equalsIgnoreCase("CBO_Proposal_Review")
						|| parentWorkstep.equalsIgnoreCase("RM_Query Resolutions")
						|| parentWorkstep.equalsIgnoreCase("RM_Doc_Execution")
						|| parentWorkstep.equalsIgnoreCase("UH_TL_Def_Waiver")
						|| parentWorkstep.equalsIgnoreCase("Head_Corporate_Banking")
						|| parentWorkstep.equalsIgnoreCase("CA_Raise Queries to RM")
						|| parentWorkstep.equalsIgnoreCase("HOCC_Recommendation")
						|| parentWorkstep.equalsIgnoreCase("CCO_Recommendation")
						|| parentWorkstep.equalsIgnoreCase("CSC_MRA_Prep") || parentWorkstep.equalsIgnoreCase("CC")
						|| parentWorkstep.equalsIgnoreCase("CA_FRS_Prep")
						|| parentWorkstep.equalsIgnoreCase("HOCC_FRS_Review") || parentWorkstep.equalsIgnoreCase("BCIC")
						|| parentWorkstep.equalsIgnoreCase("HOCC_Def_Waive")
						|| parentWorkstep.equalsIgnoreCase("CCO_Def_Waiver")
						|| parentWorkstep.equalsIgnoreCase("CA_Def_Waiver")
						|| parentWorkstep.equalsIgnoreCase("CC_Def_Waiver") || parentWorkstep.equalsIgnoreCase("CSU")
						|| parentWorkstep.equalsIgnoreCase("Credit")
						|| parentWorkstep.equalsIgnoreCase("CA_Review_Analysis")
						|| parentWorkstep.equalsIgnoreCase("CA_HOCC_Analysis")) {
					log.info("inside this parentWorkstep condition is initiator@@@@");
					formObject.setStyle(INDEX_PROPOSED, "disable", "false");
					formObject.setStyle(INDEX_TENOR_PROPOSED, "disable", "false");
				} /*
					 * else { log.info("inside this parentWorkstep condition not initiator@@@@");
					 * formObject.setStyle(INDEX_PROPOSED, "disable", "true");
					 * formObject.setStyle(INDEX_TENOR_PROPOSED, "disable", "true"); }
					 */

				if (setIndexKeyProp.equalsIgnoreCase("SOFR")) {
					log.info("Inside SOFR if ...");
					formObject.setValue(INDEX_KEY_PROPOSED, "SOFR");

					formObject.clearCombo(INDEX_PROPOSED);
					formObject.addItemInCombo(INDEX_PROPOSED, "REF-SOFR", "REF-SOFR");
					formObject.setValue(INDEX_PROPOSED, "REF-SOFR");

					formObject.clearCombo(INDEX_TENOR_PROPOSED);
					formObject.addItemInCombo(INDEX_TENOR_PROPOSED, "1", "1");
					formObject.addItemInCombo(INDEX_TENOR_PROPOSED, "3", "3");
					formObject.addItemInCombo(INDEX_TENOR_PROPOSED, "6", "6");
					formObject.addItemInCombo(INDEX_TENOR_PROPOSED, "12", "12");
					formObject.setValue(INDEX_TENOR_PROPOSED, setIndexTenorProp);

					// 22M
					/*
					 * formObject.setStyle(INDEX_TENOR_UNIT_PROPOSED, "disable", "false");
					 * formObject.setValue(INDEX_TENOR_UNIT_PROPOSED,"M");//M
					 * log.info("INDEX_TENOR_UNIT_PROPOSED " +setIndexTenorUnitProp);
					 * formObject.setStyle(INDEX_TENOR_UNIT_PROPOSED, "disable", "true");
					 */
					formObject.setValue(INDEX_RATE_PROPOSED, setIndexRateProp);

				}
				if (setIndexKeyProp.equalsIgnoreCase("EIBOR")) {
					log.info("Inside EIBOR if ...");
					formObject.setValue(INDEX_KEY_PROPOSED, "EIBOR");

					// formObject.setStyle(INDEX_PROPOSED, "disable", "false");
					formObject.clearCombo(INDEX_PROPOSED);
					formObject.addItemInCombo(INDEX_PROPOSED, "REF - CB EIBOR", "REF - CB EIBOR");
					formObject.setValue(INDEX_PROPOSED, "REF - CB EIBOR");

					// formObject.setStyle(INDEX_TENOR_PROPOSED, "disable", "false");
					formObject.clearCombo(INDEX_TENOR_PROPOSED);
					formObject.addItemInCombo(INDEX_TENOR_PROPOSED, "1", "1");
					formObject.addItemInCombo(INDEX_TENOR_PROPOSED, "3", "3");
					formObject.addItemInCombo(INDEX_TENOR_PROPOSED, "6", "6");
					formObject.addItemInCombo(INDEX_TENOR_PROPOSED, "12", "12");
					formObject.setValue(INDEX_TENOR_PROPOSED, setIndexTenorProp);
					// 22M
					/*
					 * formObject.setStyle(INDEX_TENOR_UNIT_PROPOSED, "disable", "false");
					 * formObject.setValue(INDEX_TENOR_UNIT_PROPOSED,"M");//M
					 * formObject.setStyle(INDEX_TENOR_UNIT_PROPOSED, "disable", "true");
					 */
					formObject.setValue(INDEX_RATE_PROPOSED, setIndexRateProp);

				}
			}

		} catch (Exception e) {
			log.error("Exception : " + e);
		}
	}

	// end edit by mohit 09-08-2024 for setting Index Description
	// customer no and facility_ids
	// start edit by reshank 09-08-24 for setting ftp_rate
	public void setFtpRateOverProp(String facilityId) {
		log.info("Inside setFtpRateOverProp function...");
		try {
			String stageId = formObject.getValue("CURRENT_WORKSTEPNAME").toString();
			String setFtpProposedQuery = "select Ftp_Override_Proposed,FTP_RATE_PROPOSED from NG_RAROC_FACILITY_DETAILS where wi_name='"
					+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "' and facility_id='" + facilityId
					+ "'";
			log.info("setFtpProposedQuery..." + setFtpProposedQuery);

			List<List<String>> setFtpProposedOut = formObject.getDataFromDB(setFtpProposedQuery);
			log.info("setFtpProposedOut%%%%" + setFtpProposedOut);
			String setFtpOverride = setFtpProposedOut.get(0).get(0);
			log.info("setFtpOverride*****" + setFtpOverride);
			String setFtpRate = setFtpProposedOut.get(0).get(1);
			log.info("setFtpRate&&&&" + setFtpRate);
			// if (setFtpOverride.equalsIgnoreCase("YES") && //Start Edit by 25-09-24
			if (stageId.equalsIgnoreCase("Initiate_Proposal") || stageId.equalsIgnoreCase("RM_Proposal_Review")
					|| stageId.equalsIgnoreCase("RO_Proposal_Modify")
					|| stageId.equalsIgnoreCase("UH_TL_Proposal_Review")
					|| stageId.equalsIgnoreCase("CBO_Proposal_Review")
					|| stageId.equalsIgnoreCase("RM_Query Resolutions") || stageId.equalsIgnoreCase("RM_Doc_Execution")
					|| stageId.equalsIgnoreCase("UH_TL_Def_Waiver")
					|| stageId.equalsIgnoreCase("Head_Corporate_Banking")
					|| stageId.equalsIgnoreCase("CA_Raise Queries to RM")
					|| stageId.equalsIgnoreCase("HOCC_Recommendation") || stageId.equalsIgnoreCase("CCO_Recommendation")
					|| stageId.equalsIgnoreCase("CSC_MRA_Prep") || stageId.equalsIgnoreCase("CC")
					|| stageId.equalsIgnoreCase("CA_FRS_Prep") || stageId.equalsIgnoreCase("HOCC_FRS_Review")
					|| stageId.equalsIgnoreCase("BCIC") || stageId.equalsIgnoreCase("HOCC_Def_Waive")
					|| stageId.equalsIgnoreCase("CCO_Def_Waiver") || stageId.equalsIgnoreCase("CA_Def_Waiver")
					|| stageId.equalsIgnoreCase("CC_Def_Waiver") || stageId.equalsIgnoreCase("CSU")
					|| stageId.equalsIgnoreCase("Credit") || stageId.equalsIgnoreCase("CA_Review_Analysis")
					|| stageId.equalsIgnoreCase("CA_HOCC_Analysis")) {

				log.info("Inside if setFtpOverride is///" + setFtpOverride);
				if (setFtpOverride.equalsIgnoreCase("YES")) {
					log.info("Inside if setFtpOverride is YES");
					formObject.setValue(FTP_OVERRIDE_PROPOSED, setFtpOverride);
					formObject.setStyle(FTP_RATE_PROPOSED, "disable", "false");
					formObject.setValue(FTP_RATE_PROPOSED, setFtpRate);
				} else if (setFtpOverride.equalsIgnoreCase("NO")) {
					log.info("Inside if setFtpOverride is NO**" + setFtpRate);
					formObject.setValue(FTP_OVERRIDE_PROPOSED, setFtpOverride);
					formObject.setValue(FTP_RATE_PROPOSED, setFtpRate);
					formObject.setStyle(FTP_RATE_PROPOSED, "disable", "true");
				} else {
					formObject.setValue(FTP_RATE_PROPOSED, "");
					log.info("else setFtpOverride is blank");
				}
				// End Edit by 25-09-24
//					if (setFtpOverride.equalsIgnoreCase("YES")) {
//				log.info("inside if setFtpOverride is Yes" + setFtpOverride);
//				formObject.setValue(FTP_OVERRIDE_PROPOSED, setFtpOverride);
//				formObject.setStyle(FTP_RATE_PROPOSED, "disable", "false");
//				formObject.setValue(FTP_RATE_PROPOSED, setFtpRate);
//			}else {
//				formObject.setValue(FTP_RATE_PROPOSED, "");
//				log.info("else setFtpOverride is blank");
//			}
//			if(setFtpOverride.equalsIgnoreCase("No")) {
//				log.info("inside if setFtpOverride is No");
//				formObject.setValue(FTP_OVERRIDE_PROPOSED, setFtpOverride);
////				formObject.setValue(FTP_RATE_PROPOSED, "");
//				formObject.setValue(FTP_RATE_PROPOSED, setFtpRate);
//				formObject.setStyle("F_FTP_PROP", "disable", "true");
//			}
			}
		} catch (Exception e) {
			log.error("Exception : " + e);
		}
	}
	// end edit by reshank 09-08-24 for setting ftp_rate_Prop

	public void updateFtpRate(IFormReference obj) {
		String updateFtp = formObject.getValue("F_FTP_OVR_PROPOSED").toString();
		if (updateFtp.equalsIgnoreCase("No")) {
			log.info("updateFtp" + updateFtp);
			String facilityId = formObject.getValue("F_FACILITY_ID").toString();
			log.info("facilityId" + facilityId);
			String Query = "UPDATE NG_RAROC_FACILITY_DETAILS SET FTP_RATE_PROPOSED ='" + "' where wi_name='"
					+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "'and facility_id='" + facilityId
					+ "'";
			log.info("Query" + Query);
			obj.saveDataInDB(Query);

		}

	}

	// start edit by reshank 10-08-24 for setting ftp_rate_sens
	public void setFtpRateOverSens(String facilityId) {
		log.info("Inside setFtpRateOverSens function+++");
		try {
			String stageId = formObject.getValue("CURRENT_WORKSTEPNAME").toString();
			String setFtpSensitizedQuery = "select FTP_OVERRIDE_SENSITIZED,FTP_RATE_SENITISED from NG_RAROC_FACILITY_DETAILS where wi_name='"
					+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "' and facility_id='" + facilityId
					+ "'";
			log.info("setFtpSensitizedQuery..." + setFtpSensitizedQuery);

			List<List<String>> setFtpSensitizedOut = formObject.getDataFromDB(setFtpSensitizedQuery);

			String setFtpOverride = setFtpSensitizedOut.get(0).get(0);
			String setFtpRate = setFtpSensitizedOut.get(0).get(1);
			// if (setFtpOverride.equalsIgnoreCase("YES") && //Start Edit by 25-09-24
			if (stageId.equalsIgnoreCase("CA_Raise Queries to RM") || stageId.equalsIgnoreCase("HOCC_Recommendation")
					|| stageId.equalsIgnoreCase("CCO_Recommendation") || stageId.equalsIgnoreCase("CSC_MRA_Prep")
					|| stageId.equalsIgnoreCase("CC") || stageId.equalsIgnoreCase("CA_FRS_Prep")
					|| stageId.equalsIgnoreCase("HOCC_FRS_Review") || stageId.equalsIgnoreCase("BCIC")
					|| stageId.equalsIgnoreCase("HOCC_Def_Waive") || stageId.equalsIgnoreCase("CCO_Def_Waiver")
					|| stageId.equalsIgnoreCase("CA_Def_Waiver") || stageId.equalsIgnoreCase("CC_Def_Waiver")
					|| stageId.equalsIgnoreCase("CSU") || stageId.equalsIgnoreCase("Credit")
					|| stageId.equalsIgnoreCase("CA_Review_Analysis") || stageId.equalsIgnoreCase("HOCC_Assign")) {
				if (setFtpOverride.equalsIgnoreCase("YES")) {
					log.info("inside if setFtpOverride is Yes");
					formObject.setValue(FTP_OVERRIDE_SENSITIZED, setFtpOverride);
					formObject.setStyle(FTP_SENSITIZED, "disable", "false");
					formObject.setValue(FTP_SENSITIZED, setFtpRate);

				} else if (setFtpOverride.equalsIgnoreCase("No")) {
					log.info("inside if setFtpOverride is No");
					formObject.setValue(FTP_OVERRIDE_SENSITIZED, setFtpOverride);
					// formObject.setValue(FTP_SENSITIZED, "");
					formObject.setValue(FTP_SENSITIZED, setFtpRate);
					formObject.setStyle("F_FTP_SENSITIZED", "disable", "true");
				} else {
					formObject.setValue(FTP_SENSITIZED, "");
					log.info("else F_FTP_SENSITIZED is blank");
				}
			}
		} catch (Exception e) {
			log.error("Exception : " + e);
		}
	}
	// end edit by reshank 10-08-24 for setting ftp_rate_Sens

	public void updateFtpRateSens(IFormReference obj) {
		String updateFtp = formObject.getValue("F_FTP_OVR_SENSITIZED").toString();
		if (updateFtp.equalsIgnoreCase("No")) {
			log.info("updateFtpSens" + updateFtp);
			String facilityId = formObject.getValue("F_FACILITY_ID").toString();
			log.info("facilityId" + facilityId);
			String QuerySens = "UPDATE NG_RAROC_FACILITY_DETAILS SET FTP_RATE_SENITISED ='" + "' where wi_name='"
					+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "'and facility_id='" + facilityId
					+ "'";
			log.info("Query" + QuerySens);
			obj.saveDataInDB(QuerySens);

		}

	}

	public void setCFMSensToApprov() {
		log.info("Inside setCFMSensToApprov function...");
		try {
			String setCFMSensitizedQuery = "select COLLATERAL_TYPE_SENS,COLLATERAL_NAME_SENS,COLLATERAL_NUMBER_SENS,COLLATERAL_CURRENCY_SENS,"
					+ "COLLATERAL_AMOUNT_SENS,COLLATERAL_LIEN_AMOUNT_SENS,COLLATERAL_LIEN_INTEREST_SENS,COLLATERAL_LIEN_TENOR_SENS,"
					+ "COLLATERAL_ALLOCATION_PERCENTAGE_SENS,COLLATERAL_TOTAL_AMOUNT_SENS,COLLATERAL_AMOUNT_UTILIZED_SENS,"
					+ " insertionorderid from NG_RAROC_COLLATERAL_FACILITY_MAP where wi_name='"
					+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "'";
			log.info("setCFMSensitizedQuery..." + setCFMSensitizedQuery);

			List<List<String>> setCFMSensitizedOut = formObject.getDataFromDB(setCFMSensitizedQuery);
			log.info("size of setCFMSensitizedOut is:::: " + setCFMSensitizedOut.size());
			log.info("vaue of setCFMSensitizedOut is:::: " + setCFMSensitizedOut);
			if (!setCFMSensitizedOut.isEmpty()) {

				for (int i = 0; i < setCFMSensitizedOut.size(); i++) {

					String setCFMCollateralTypeSens = setCFMSensitizedOut.get(i).get(0);
					String setCFMCollateralNameSens = setCFMSensitizedOut.get(i).get(1);
					String setCFMCollateralNumberSens = setCFMSensitizedOut.get(i).get(2);
					String setCFMCollateralCurrencySens = setCFMSensitizedOut.get(i).get(3);
					String setCFMCollateralAmountSens = setCFMSensitizedOut.get(i).get(4);
					String setCFMCollateralLienAmtSens = setCFMSensitizedOut.get(i).get(5);
					String setCFMCollateralLienIntSens = setCFMSensitizedOut.get(i).get(6);
					String setCFMCollateralLienTenorSens = setCFMSensitizedOut.get(i).get(7);
					String setCFMCollateralAlloPerSens = setCFMSensitizedOut.get(i).get(8);
					String setCFMCollateralTotAmtAmtSens = setCFMSensitizedOut.get(i).get(9);
					String setCFMCollateralAmtUtilizedSens = setCFMSensitizedOut.get(i).get(10);
					String setCFMInsertionOrderId = setCFMSensitizedOut.get(i).get(11);// added by mohit 11-09-2024
					log.info("setCFMCollateralTypeSens..." + setCFMCollateralTypeSens);
					log.info("setCFMCollateralLienTenorSens..." + setCFMCollateralLienTenorSens);
					log.info("setCFMInsertionOrderId..." + setCFMInsertionOrderId);

					/*
					 * String cFMCollateralTypeApproveQuery =
					 * "select top 1 COLLATERAL_TYPE_Approved from NG_RAROC_COLLATERAL_FACILITY_MAP where wi_name='"
					 * + formObject.getObjGeneralData().getM_strProcessInstanceId() + "'";
					 * 
					 * log.info("CFMCollateralTypeAppr query..." + cFMCollateralTypeApproveQuery);
					 * 
					 * List<List<String>> result =
					 * formObject.getDataFromDB(cFMCollateralTypeApproveQuery);
					 * log.info("result query..." + result);
					 * 
					 * String cFMCollateralTypeApprov = result.get(0).get(0);
					 * log.info("cFMCollateralTypeApprov..." + cFMCollateralTypeApprov);
					 */
					// if (cFMCollateralTypeApprov.isEmpty() || cFMCollateralTypeApprov == null ||
					// cFMCollateralTypeApprov == "") {
					// log.info("result is empty and create query");
					String updateCollateralApprQuery = "UPDATE NG_RAROC_COLLATERAL_FACILITY_MAP SET COLLATERAL_TYPE_Approved = '"
							+ setCFMCollateralTypeSens + "',COLLATERAL_NAME_APPROVED = '" + setCFMCollateralNameSens
							+ "',COLLATERAL_NUMBER_APPROVED = '" + setCFMCollateralNumberSens
							+ "',COLLATERAL_CURRENCY_APPROVED = '" + setCFMCollateralCurrencySens
							+ "',COLLATERAL_AMOUNT_APPROVED = '" + setCFMCollateralAmountSens
							+ "',COLLATERAL_LIEN_AMOUNT_APPROVED = '" + setCFMCollateralLienAmtSens
							+ "',COLLATERAL_LIEN_INTEREST_APPROVED = '" + setCFMCollateralLienIntSens
							+ "',COLLATERAL_LIEN_TENOR_APPROVED = '" + setCFMCollateralLienTenorSens
							+ "',COLLATERAL_ALLOCATION_PERCENTAGE_APPROVED = '" + setCFMCollateralAlloPerSens
							+ "',COLLATERAL_TOTAL_AMOUNT_APPROVED = '" + setCFMCollateralTotAmtAmtSens
							+ "',COLLATERAL_AMOUNT_UTILIZED_APPROVED = '" + setCFMCollateralAmtUtilizedSens
							+ "'WHERE WI_NAME='" + formObject.getObjGeneralData().getM_strProcessInstanceId()
							+ "' and insertionorderid='" + setCFMInsertionOrderId + "'";

					log.info("updateCollateralApprQuery check:::" + updateCollateralApprQuery);
					formObject.saveDataInDB(updateCollateralApprQuery);

					/*
					 * } else { log.info("some problem with if statement...plz check"); }
					 */
				}

			}
		} catch (Exception e) {
			log.error("Exception : " + e);
		}
	}

	// start edit by mohit 06-09-2024
	public void repaymentTypeBulletProp(String facilityId) {
		log.info("Inside repaymentTypeBulletProp function .....");
		try {
			String stageId = formObject.getValue("CURRENT_WORKSTEPNAME").toString();

			if (stageId.equalsIgnoreCase("Initiate_Proposal") || stageId.equalsIgnoreCase("RM_Proposal_Review")
					|| stageId.equalsIgnoreCase("RO_Proposal_Modify")
					|| stageId.equalsIgnoreCase("UH_TL_Proposal_Review")
					|| stageId.equalsIgnoreCase("CBO_Proposal_Review")
					|| stageId.equalsIgnoreCase("RM_Query Resolutions") || stageId.equalsIgnoreCase("RM_Doc_Execution")
					|| stageId.equalsIgnoreCase("UH_TL_Def_Waiver")
					|| stageId.equalsIgnoreCase("Head_Corporate_Banking")
					|| stageId.equalsIgnoreCase("CA_Raise Queries to RM")
					|| stageId.equalsIgnoreCase("HOCC_Recommendation") || stageId.equalsIgnoreCase("CCO_Recommendation")
					|| stageId.equalsIgnoreCase("CSC_MRA_Prep") || stageId.equalsIgnoreCase("CC")
					|| stageId.equalsIgnoreCase("CA_FRS_Prep") || stageId.equalsIgnoreCase("HOCC_FRS_Review")
					|| stageId.equalsIgnoreCase("BCIC") || stageId.equalsIgnoreCase("HOCC_Def_Waive")
					|| stageId.equalsIgnoreCase("CCO_Def_Waiver") || stageId.equalsIgnoreCase("CA_Def_Waiver")
					|| stageId.equalsIgnoreCase("CC_Def_Waiver") || stageId.equalsIgnoreCase("CSU")
					|| stageId.equalsIgnoreCase("Credit") || stageId.equalsIgnoreCase("CA_Review_Analysis")
					|| stageId.equalsIgnoreCase("CA_HOCC_Analysis")) {

				String checkRepaymentTypeQuery = "select REPAYMENT_TYPE_PROPOSED from NG_RAROC_FACILITY_DETAILS where wi_name='"
						+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "'and FACILITY_ID='" + facilityId
						+ "'";// bp05 //d
				log.info("checkRepaymentTypeQuery...." + checkRepaymentTypeQuery);
				List<List<String>> repaymentTypeOut = formObject.getDataFromDB(checkRepaymentTypeQuery);
				String repaymentType = repaymentTypeOut.get(0).get(0);
				log.info("repaymentType..." + repaymentType);
				if (repaymentType.equalsIgnoreCase("Bullet")) {
					log.info("Inside if repaymentType prop is bullet...");
					formObject.addItemInCombo(REPAYMENT_FREQUENCY_PROPOSED, "Bullet", "Bullet");
					formObject.setValue(REPAYMENT_FREQUENCY_PROPOSED, "Bullet");
					formObject.setStyle(REPAYMENT_FREQUENCY_PROPOSED, "disable", "true");
				} else {
					log.info("Inside else repaymentType prop is not bullet!!!!!");
					formObject.setStyle(REPAYMENT_FREQUENCY_PROPOSED, "disable", "false");
					formObject.removeItemFromCombo(REPAYMENT_FREQUENCY_PROPOSED, 5);// Bullet remove
				}
			}

		} catch (Exception e) {
			log.error("Exception : " + e);
		}
	}

	public void repaymentTypeBulletSens(String facilityId) {
		log.info("Inside repaymentTypeBulletSens function .....");
		try {
			String stageId = formObject.getValue("CURRENT_WORKSTEPNAME").toString();

			/*
			 * if (stageId.equalsIgnoreCase("CA_Raise Queries to RM") ||
			 * stageId.equalsIgnoreCase("HOCC_Recommendation") ||
			 * stageId.equalsIgnoreCase("CCO_Recommendation") ||
			 * stageId.equalsIgnoreCase("CSC_MRA_Prep") || stageId.equalsIgnoreCase("CC") ||
			 * stageId.equalsIgnoreCase("CA_FRS_Prep") ||
			 * stageId.equalsIgnoreCase("HOCC_FRS_Review") ||
			 * stageId.equalsIgnoreCase("BCIC") ||
			 * stageId.equalsIgnoreCase("HOCC_Def_Waive") ||
			 * stageId.equalsIgnoreCase("CCO_Def_Waiver") ||
			 * stageId.equalsIgnoreCase("CA_Def_Waiver") ||
			 * stageId.equalsIgnoreCase("CC_Def_Waiver") || stageId.equalsIgnoreCase("CSU")
			 * || stageId.equalsIgnoreCase("Credit") ||
			 * stageId.equalsIgnoreCase("CA_Review_Analysis") ||
			 * stageId.equalsIgnoreCase("HOCC_Assign")) {
			 */
			String checkRepaymentTypeQuery = "select REPAYMENT_TYPE_SENSITIZED,REPAYMENT_TYPE_PROPOSED from NG_RAROC_FACILITY_DETAILS where wi_name='"
					+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "'and FACILITY_ID='" + facilityId
					+ "'";// bp05 //d
			log.info("checkRepaymentTypeQuery sens...." + checkRepaymentTypeQuery);
			List<List<String>> repaymentTypeOut = formObject.getDataFromDB(checkRepaymentTypeQuery);
			String repaymentType = repaymentTypeOut.get(0).get(0);
			String repaymentTypeProp = repaymentTypeOut.get(0).get(1);
			log.info("repaymentType sens..." + repaymentType);
			log.info("repaymentTypeProp prop..." + repaymentTypeProp);

			if (repaymentTypeProp.equalsIgnoreCase("Bullet")) {
				log.info("Inside if repaymentType prop is bullet...");
				formObject.addItemInCombo(REPAYMENT_FREQUENCY_PROPOSED, "Bullet", "Bullet");
				formObject.setValue(REPAYMENT_FREQUENCY_PROPOSED, "Bullet");
				formObject.setStyle(REPAYMENT_FREQUENCY_PROPOSED, "disable", "true");

			}
			if (repaymentType.equalsIgnoreCase("Bullet")) {
				log.info("Inside if repaymentType sens is bullet...");
				formObject.addItemInCombo(REPAY_FREQ_SENSITIZED, "Bullet", "Bullet");
				formObject.setValue(REPAY_FREQ_SENSITIZED, "Bullet");
				formObject.setStyle(REPAY_FREQ_SENSITIZED, "disable", "true");
			} else {
				log.info("Inside else repaymentType sens is not bullet!!!!!");
				formObject.setStyle(REPAY_FREQ_SENSITIZED, "disable", "false");
				formObject.removeItemFromCombo(REPAY_FREQ_SENSITIZED, 5);// Bullet remove
			}
			// }

		} catch (Exception e) {
			log.error("Exception : " + e);
		}
	}
	// end edit by mohit 06-09-2024

	// start edit by mohit 11-09-2024
	public void updateDataInGroupApproved() {
		log.info("Inside updateDataInGroupApproved....");
		try {
			// sens db not null or not empty, copy value to approved as well in db.
			// 11-09-2024 added by shikha
			String groupSensitizedQuery = "select Group_sensitized from NG_RAROC_GROUP where wi_name='"
					+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "'";
			log.info("groupSensitizedQuery..." + groupSensitizedQuery);

			List<List<String>> groupSensitizedOut = formObject.getDataFromDB(groupSensitizedQuery);
			log.info("groupSensitizedOut is:::: " + groupSensitizedOut);

			String groupSens = groupSensitizedOut.get(0).get(0);
			log.info("groupSens..." + groupSens);

			String groupApprovedQuery = "select Group_Approved from NG_RAROC_GROUP where wi_name='"
					+ formObject.getObjGeneralData().getM_strProcessInstanceId() + "'";
			List<List<String>> result = formObject.getDataFromDB(groupApprovedQuery);
			log.info("result query..." + result);

			String groupApproved = result.get(0).get(0);

			// if (groupApproved.isEmpty() || groupApproved == null || groupApproved == "")
			// {
			log.info("result is empty and create query");
			String updateGroupApprQuery = "UPDATE NG_RAROC_GROUP SET Group_Approved= '" + groupSens + "'"
					+ " WHERE WI_NAME='" + formObject.getObjGeneralData().getM_strProcessInstanceId() + "'";
			log.info("updateGroupApprQuery check:::" + updateGroupApprQuery);
			formObject.saveDataInDB(updateGroupApprQuery);
			/*
			 * }else { log.info("some problem with if statement...plz check"); }
			 */
		} catch (Exception e) {
			log.error("Exception : " + e);
		}
	}
	// end edit by mohit 11-09-2024

	// start edit by mohit 11-09-2024 to update pre-approved db columns Collateral
	// Facility Mapping Grid
	public void updateDataInPreAppCFMGrid() {
		try {

		} catch (Exception e) {
			log.error("Exception : " + e);
		}
	}
	// end edit by mohit 11-09-2024 to update pre-approved db columns Collateral
	// Facility Mapping Grid

	// start edit by mohit 11-09-2024 to populate Previously Approved for group tab
	public void updateDataInPreAppGroup() {
		log.info("Inside updateDataInPreAppGroup!!!!!");
		try {
			String id = formObject.getValue(LEAD_REF_NO).toString();
			String queryApprov = "Select TOP 1 group_approved from NG_RAROC_GROUP "
					+ " where wi_name= (SELECT TOP 1 a.wi_name FROM ALM_RAROC_EXT_TABLE a JOIN ALM_CA_EXT_TABLE b  ON a.ca_wi_name = b.WI_NAME  WHERE a.LEAD_REF_NO = '"
					+ id + "' and a.wi_name != '" + formObject.getObjGeneralData().getM_strProcessInstanceId()
					+ "' AND b.current_workstepname ='Archive' ORDER BY a.WI_Name DESC)";

			log.info("updateDataInPreAppGroup : queryApprov :" + queryApprov);
			List<List<String>> sOutput = formObject.getDataFromDB(queryApprov);
			log.info("sOutput..." + sOutput);
			if (sOutput != null && !sOutput.isEmpty()) {
				log.info("Inside if not null");
				formObject.setValue("Q_NG_RAROC_GROUP_Previously_Approved", sOutput.get(0).get(0));
			}
		} catch (Exception e) {
			log.error("Exception : " + e);
		}
	}
	// end edit by mohit 11-09-2024 to populate Previously Approved for group tab
}