package com.newgen.raroc.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.raroc.entities.RarocRequestAttrDetails;
import com.newgen.raroc.service.CreateNewWiService;
import com.newgen.raroc.util.APCallCreateXML;

@Component
public class CreateNewWiServiceImpl implements CreateNewWiService {
	private static final Logger logger = LogManager.getLogger(CreateNewWiServiceImpl.class);
	@Value("${NgEjbClientIp}")
	private String IP;

	@Value("${NgEjbClientPort}")
	private String port;

	@Value("${NgEjbClientappName}")
	private String appName;

	@Value("${cabinetName}")
	private String cabinetName;

	@Autowired
	APCallCreateXML apCallCreateXML;
	public String initiateAlso = "N";
	public HashMap<String, String> attribMap;
	public String processDefId;
	public String ReadMode;
	public HashMap<String, String> complexMap = new LinkedHashMap<>();
	Set<Entry<String, String>> entrySetComplex;
	Iterator<Entry<String, String>> entrySetIteratorComplex;

	@Override
	/* public String createNewWorkItem(String processDefID, String ReadMode, String
	 sourceWI, String sessionID) {*/
	public String createNewWorkItem(String processDefID, String sourceWI, String sessionID) {
		logger.info(IP);
		logger.info(port);
		logger.info(appName);
		logger.info(cabinetName);
		APCallCreateXML.init(this.IP, this.port, this.appName);
		String outputXML = "";
		String WIName = "";
		String outputChildWI = "";
		String parentWorkstepQuery = "";
		String parentWorkstep = "";
		String rarocChildWI = "";
		String outputProcUpdateXML = "";
		String inputProcUpdateXML = "";
		String outputUnlockXML = "";
		String readModeParentWi = ReadMode;
		// List<String> paramlist = new ArrayList<>();
		String sParams = "";
		try {
			logger.info("RAROC APP");
			logger.info("Welcome to RAROC");
			outputChildWI = APCallCreateXML.APSelect(
					"SELECT WI_NAME FROM ALM_RAROC_EXT_TABLE WHERE CA_WI_NAME = N'" + sourceWI + "'", this.cabinetName);
			XMLParser xpWI = new XMLParser(outputChildWI);
			int mainCodeWI = Integer.parseInt(xpWI.getValueOf("MainCode"));
			if (mainCodeWI == 0) {
				logger.info("RAROC CHILD WI TotalRetrieved: " + Integer.parseInt(xpWI.getValueOf("TotalRetrieved")));
				if (Integer.parseInt(xpWI.getValueOf("TotalRetrieved")) > 0) {
					rarocChildWI = xpWI.getValueOf("WI_NAME");
					logger.info("Existing RAROC Workitem:  " + rarocChildWI);
				}
			}
			// start edit by mohit 30-12-2024 IF credit team, child should not be created.
			// // m20250102

			parentWorkstepQuery = APCallCreateXML.APSelect(
					"SELECT CURRENT_WORKSTEPNAME FROM ALM_CA_EXT_TABLE WHERE wi_name= N'" + sourceWI + "'",
					this.cabinetName);
			XMLParser parentWorkstepXML = new XMLParser(parentWorkstepQuery);
			int mainCodeParentWorkstep = Integer.parseInt(parentWorkstepXML.getValueOf("MainCode"));
			if (mainCodeParentWorkstep == 0) {
				logger.info("RAROC ParentWorkstep TotalRetrieved: "
						+ Integer.parseInt(parentWorkstepXML.getValueOf("TotalRetrieved")));
				if (Integer.parseInt(parentWorkstepXML.getValueOf("TotalRetrieved")) > 0) {
					parentWorkstep = parentWorkstepXML.getValueOf("CURRENT_WORKSTEPNAME");
					logger.info(" RAROC parentWorkstep:  " + parentWorkstep);
				}
			}

			// end edit by mohit 30-12-2024 IF credit team, child should not be created.
			if (!"".equalsIgnoreCase(rarocChildWI) && rarocChildWI != null) {
				RarocRequestAttrDetails rarocRAD = this.apCallCreateXML.createRarocReqAttrDetails(this.cabinetName);
				logger.info("RarocRequestAttrDetails to rarocRAD..." + rarocRAD);
				/*
				 * this was duplicating the records //StringBuilder attrXML =
				 * this.apCallCreateXML.createAttributeXML(rarocRAD, sourceWI, WIName,
				 * this.cabinetName); StringBuilder attrXML =
				 * this.apCallCreateXML.createAttributeXML(rarocRAD, sourceWI, rarocChildWI,
				 * this.cabinetName); logger.info("RarocRequestAttrDetails to attrXML"+attrXML);
				 * // outputXML = this.apCallCreateXML.WFSetAttributes(sessionID, WIName, 1,
				 * attrXML.toString(), processDefID, this.cabinetName); outputXML =
				 * this.apCallCreateXML.WFSetAttributes(sessionID, rarocChildWI, 1,
				 * attrXML.toString(), processDefID, this.cabinetName);
				 */
				// start edit by mohit 23-6-2024 to update the tables when raroc button is
				// clicked.
				// paramlist.add("CA_WI_NAME:" + sourceWI);
				// paramlist.add("RAROC_WI_NAME:" + rarocChildWI);
				sParams = "'" + sourceWI + "','" + rarocChildWI + "'";
				outputProcUpdateXML = APCallCreateXML.createAP_Procedure_XML("NGP_RAROC_UTILITY_UPDATE", sParams,
						sessionID, this.cabinetName);
				logger.info("Proc NGP_RAROC_UTILITY_UPDATE called successfully!!!!! 02-01-2025" + outputProcUpdateXML);

				// outputProcUpdateXML=getOutputXML(sessionID,inputProcUpdateXML);
				// end edit by mohit 23-06-2024

				// start edit by mohit 02-01-2025 to close RAROC window m20250102
				
				  outputUnlockXML = APCallCreateXML.WMUnlockWorkItem(sessionID,
				 rarocChildWI, 1, this.cabinetName); XMLParser xp = new XMLParser(outputUnlockXML); 
				 int mainCodeUnlock =Integer.parseInt(xp.getValueOf("MainCode"));
				 logger.info("WMUnlockWorkItem....outputUnlockXML 02-01-2025!!!!!" +outputUnlockXML); 
				// if(mainCodeUnlock ==0) { //end edit by mohit 02-01-2025  to close RAROC window
				  return "Existing workitem " + rarocChildWI + " fetched Successfully";
				 //}
			}
			
			logger.info("readModeParentWi 06-01-2024..." + readModeParentWi);
			// if (readModeParentWi != "Y") {// m20250102
			outputXML = APCallCreateXML.WFUploadWorkItemNew(sessionID, this.initiateAlso, processDefID,
					this.cabinetName);
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			// added by mohit 20-11-2024 to restrict the parent wi in read only mode to
			// create child wi.

			if (parentWorkstep.equalsIgnoreCase("Initiate_Proposal")
					|| parentWorkstep.equalsIgnoreCase("RM_Proposal_Review")
					|| parentWorkstep.equalsIgnoreCase("RO_Proposal_Modify")
					|| parentWorkstep.equalsIgnoreCase("UH_TL_Proposal_Review")
					|| parentWorkstep.equalsIgnoreCase("RM_Doc_Execution")
					|| parentWorkstep.equalsIgnoreCase("UH_TL_Def_Waiver")) { // m20250102
				// logger.info("parentWorkstep...." + parentWorkstep);
				if (mainCode == 0) {
					logger.info("Inside check if readOnly is not...");
					WIName = xp.getValueOf("ProcessInstanceId");
					RarocRequestAttrDetails rarocRAD = this.apCallCreateXML.createRarocReqAttrDetails(this.cabinetName);
					logger.info("RarocRequestAttrDetails to rarocRAD" + rarocRAD);
					StringBuilder attrXML = this.apCallCreateXML.createAttributeXML(rarocRAD, sourceWI, WIName,
							this.cabinetName);
					logger.info("RarocRequestAttrDetails to attrXML" + attrXML);
					outputXML = this.apCallCreateXML.WFSetAttributes(sessionID, WIName, 1, attrXML.toString(),
							processDefID, this.cabinetName);

					logger.info(" to WFSetAttributes" + outputXML);
					xp = new XMLParser(outputXML);
					mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
					if (mainCode == 0) {
						APCallCreateXML.WMUnlockWorkItem(sessionID, WIName, 1, this.cabinetName);
					} else {
						APCallCreateXML.WMUnlockWorkItem(sessionID, WIName, 1, this.cabinetName);
					}
					return WIName + " Created Successfully";
				}
			} // m20250102 if parentWorkstep.equalsIgnoreCase brac

			// }//m20250102
		} catch (

		Exception e) {
			logger.info("Exception in Springboot " + e.getMessage());
			e.printStackTrace();
		}

		return "Error in Creating WI";
	}
}
