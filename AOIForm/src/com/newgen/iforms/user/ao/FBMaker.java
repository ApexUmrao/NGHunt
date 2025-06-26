package com.newgen.iforms.user.ao;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.newgen.iforms.EControl;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.iforms.user.ao.util.XMLParser;
import com.newgen.iforms.user.config.Constants;
import com.newgen.mvcbeans.model.WorkdeskModel;

public class FBMaker extends Common implements Constants, IFormServerEventHandler{
	boolean isNewFamily = false;
	
	HashMap<String, String> relationMap;

	public FBMaker(IFormReference formObject) {
		super(formObject);
	}

	@Override
	public void beforeFormLoad(FormDef arg0, IFormReference arg1) {
		log.info("Inside beforeFormLoad >>>");
		log.info("WorkItem Name: " + sWorkitemId);
	}

	@Override
	public String executeCustomService(FormDef arg0, IFormReference arg1,
			String arg2, String arg3, String arg4) {
		return null;
	}

	@Override
	public JSONArray executeEvent(FormDef arg0, IFormReference arg1,
			String arg2, String arg3) {
		return null;
	}

	@Override
	public String executeServerEvent(IFormReference formObject, String controlName, String eventType, String data){
		logInfo("executeServerEvent", "Inside FBMaker >>>");
		logInfo("executeServerEvent", "Event: " + eventType + ", Control Name: " + controlName + ", Data: " + data);
		sendMessageList.clear();
		String retMsg = getReturnMessage(true,controlName);
		try{
			if(eventType.equalsIgnoreCase(EVENT_TYPE_LOAD)) {
				
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)) {
				if(BTN_FETCH_FAMILY.equalsIgnoreCase(controlName)) {
					if(validateHOFCID()){
						formObject.clearTable(LVW_FAMILY_MEMBERS);
						relationMap = new HashMap<String, String>();
						String sCustID = formObject.getValue(CID_HOF).toString();
						String inputXml = getFamilyDetailsInputXML(sCustID);
						String inputXmlHOFCustInfo = getCustomerInfoInputXML();
						logInfo("BTN_FETCH_FAMILY","fetchFamilyDetails inputXml: "+inputXml);
						String outputXml = socket.connectToSocket(inputXml);
						String outputXmlHOFCustInfo = socket.connectToSocket(inputXmlHOFCustInfo);
						outputXmlHOFCustInfo = outputXmlHOFCustInfo.replaceAll("null", "");
						String rmName = getTagValues(outputXmlHOFCustInfo,"RMName");
						String rmcode = getTagValues(outputXmlHOFCustInfo,"RMCode");
						//added by ayush 25-11-2022
						String mobileNumber = getTagValue(outputXmlHOFCustInfo,"Mobile");
						logInfo("BTN_FETCH_FAMILY","mobileNumber: "+mobileNumber);
						formObject.setValue(RM_HOF, rmName);
						formObject.setValue("HOF_RM_CODE", rmcode);
						String sReturnCode = getTagValues(outputXml,"returnCode");
						logInfo("BTN_FETCH_FAMILY","outputXml: "+outputXml);
						int noOfFields = 0;
						String columns = "SNO,NEW_EXISTING,CID,NAME,CUSTOMER_CATEGORY,DOB,RELATIONSHIP,"
								+ "OLD_RELATIONSHIP,STATUS,MOBILE_NO"; 
						String hofValues = "";
						if(sReturnCode.equalsIgnoreCase("0")) {
							String sFamilyGroupid = getTagValues(outputXml,"familyGroupId");
							logInfo("BTN_FETCH_FAMILY","sFamilyGroupid: "+sFamilyGroupid);
							formObject.setValue(CID_FAMILY_GROUP,sFamilyGroupid);
							// removed VALIDATION_RESULT
							//String columns = "SNO,NEW_EXISTING,CID,NAME,CUSTOMER_CATEGORY,DOB,RELATIONSHIP,CUST_STATUS";
							outputXml = outputXml.replaceAll("null", "");
							//HOF DATA Addition
							/*String sInsertQuery ="INSERT INTO usr_0_family_member_details values('"+sWorkitemId+"','1','Existing','"+
									getTagValues(outputXmlHOFCustInfo,"FullName")+"','"+formObject.getValue(CID_HOF).toString()+"','"+
									getTagValues(outputXmlHOFCustInfo,"ProfitCenterName")+"','"+getTagValues(outputXmlHOFCustInfo,"DOB")+
									"','HEAD OF FAMILY','','','E'";
							formObject.saveDataInDB(sInsertQuery);*/
							String hofCategory = "";
							String sQuery = "SELECT CUST_SEGMENT FROM USR_0_CUST_SEGMENT WHERE UNIQUE_ID = "
									+ "(SELECT CUST_CATEGORY_CODE FROM USR_0_CUST_CAT WHERE "
									+ "UPPER(CUST_CATEGORY_DESC) = UPPER('"
									+getTagValue(outputXmlHOFCustInfo,"CustCategory")+"'))";
							logInfo("BTN_FETCH_FAMILY", "sQuery: "+sQuery);
							List<List<String>> sCatOutput = formObject.getDataFromDB(sQuery);
							logInfo("BTN_FETCH_FAMILY", "sCatOutput: "+sCatOutput);
							if(sCatOutput.size() > 0) {
								hofCategory = sCatOutput.get(0).get(0);
							}
							formObject.setValue("HOF_CUSTOMER_SEGMENT", hofCategory);
							formObject.setValue("HOF_NAME", getTagValues(outputXmlHOFCustInfo,"FullName"));
							hofValues = "1##Existing##"+formObject.getValue(CID_HOF).toString()
									+"##"+getTagValues(outputXmlHOFCustInfo,"FullName")+"##"
									+hofCategory+"##"+getTagValues(outputXmlHOFCustInfo,"DOB")+"##HEAD OF FAMILY##"
											+ "HEAD OF FAMILY##E##"+mobileNumber+"##";
							logInfo("BTN_FETCH_FAMILY","values: "+hofValues);
							LoadListViewWithHardCodeValues(LVW_FAMILY_MEMBERS, columns, hofValues);
							if(outputXml.indexOf("FamilyDetails") > -1) {
								XMLParser xmlResponse = new XMLParser(outputXml);
								int start = xmlResponse.getStartIndex("FamilyDetails", 0, 0);
								int deadEnd = xmlResponse.getEndIndex("FamilyDetails", start, 0);
								noOfFields = xmlResponse.getNoOfFields("FamilyMember", start, deadEnd);

								logInfo("BTN_FETCH_FAMILY","no. of Family Members: "+noOfFields);
								int end = 0;
								JSONArray jsonArray = new JSONArray();
								for (int i = 0; i < noOfFields; i++) {
									start = xmlResponse.getStartIndex("FamilyMember", end, 0);
									end = xmlResponse.getEndIndex("FamilyMember", start, 0);
									logInfo("BTN_FETCH_FAMILY","FOR FamilyMember: "
											+xmlResponse.getValueOf("FamilyMember", start, end));
									String cid = xmlResponse.getValueOf("customerId", start, end);
									String rltn = xmlResponse.getValueOf("relationship", start, end);
									if(cid.equalsIgnoreCase(sCustID) && 
											!"HEAD OF FAMILY".equalsIgnoreCase(rltn)) {
										sendMessageValuesList(CID_HOF, "Already a member of another family.");
										formObject.setValue(RM_HOF, "");
										formObject.setValue(CID_FAMILY_GROUP, "");
										formObject.clearTable(LVW_FAMILY_MEMBERS);
										noOfFields = -1;
										break;
									}
									String outputFamilyMember = fetchFCRCustomer(cid, "GetCustomerSummary");
									String name = "";
									String custCategory = "";
									String dob = "";
									logInfo("BTN_FETCH_FAMILY","outputFamilyMember: "+outputFamilyMember);
									
									if(getTagValues(outputFamilyMember,"returnCode").equalsIgnoreCase("0") || 
											getTagValues(outputFamilyMember,"returnCode").equalsIgnoreCase("2")) {
										
										name = getTagValue(outputFamilyMember,"FullName");
										String sQuerySeg = "SELECT CUST_SEGMENT FROM USR_0_CUST_SEGMENT WHERE UNIQUE_ID = "
												+ "(SELECT CUST_CATEGORY_CODE FROM USR_0_CUST_CAT WHERE "
												+ "UPPER(CUST_CATEGORY_DESC) = UPPER('"
												+getTagValue(outputFamilyMember,"CustCategory")+"'))";
										List<List<String>> sCatOutput1 = formObject.getDataFromDB(sQuerySeg);
										if(sCatOutput1.size() > 0) {
											custCategory = sCatOutput1.get(0).get(0);
										}
										dob = getTagValue(outputFamilyMember,"DOB");
										// added by ayush 25-11-2022
										mobileNumber = getTagValue(outputFamilyMember,"Mobile");
										logInfo("BTN_FETCH_FAMILY","mobileNumber: "+mobileNumber);
									}
									String values = (i+2)+"##Existing##"+cid+"##"+name+"##"+custCategory+"##"+dob+"##"
											+rltn+"##"+rltn+"##E##"+mobileNumber+"##";
									logInfo("BTN_FETCH_FAMILY","values: "+values);
									LoadListViewWithHardCodeValues(LVW_FAMILY_MEMBERS, columns, values);
//									sInsertQuery ="INSERT INTO usr_0_family_member_details values('"+sWorkitemId+"','"+(i+2)+"','Existing','"+name+
//											"','"+cid+"','"+custCategory+"','"+dob+"','"+rltn+"','','','E'";
//									formObject.saveDataInDB(sInsertQuery);
									relationMap.put(cid, rltn);
								}
								logInfo("BTN_FETCH_FAMILY", "relationMap: "+relationMap.toString());
							}							
						}
						/*String sQuery = "SELECT SNO,NEW_EXISTING,MEMBER_NAME,CID,CUST_CATEGORY,DOB,RELATIONSHIP,"+
								"VALIDATION_RESULT,INSERTIONORDERID,CUST_STATUS FROM usr_0_family_member_details where"+
								"WI_NAME = '"+sWorkitemId+"' ORDER BY ITEMINDEX";
						List<List<String>> list = formObject.getDataFromDB(sQuery);
						loadListView(list,"SNO,NEW_EXISTING,MEMBER_NAME,CID,CUST_CATEGORY,DOB,RELATIONSHIP,"+
								"VALIDATION_RESULT,INSERTIONORDERID,CUST_STATUS",LVW_FAMILY_MEMBERS);*/
						if(noOfFields == 0) {
//							LoadListViewWithHardCodeValues(LVW_FAMILY_MEMBERS, columns, hofValues);
							isNewFamily = true;
							formObject.setValue(IS_FAMILY_EXIST, "Y");
							formObject.setValue(CID_FAMILY_GROUP,"");
						} else {
							isNewFamily = false;
							formObject.setValue(IS_FAMILY_EXIST, "N");
						}
					}
				} else if(BTN_SUBMIT.equalsIgnoreCase(controlName)) {
					logInfo("BTN_SUBMIT "," FBMaker : INSIDE ");
					if(submitFBValidation()) {
						if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve") 
								&& submitFBValidations(data)) {
							createHistory();
							createAllHistory();
						}
					}
					if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")){  //shahbaz
						logInfo("BTN_SUBMIT ","check Dormant Customer: ");
						String ws_decision=formObject.getValue(CRO_DEC).toString();
						logInfo("BTN_SUBMIT ","ws_decision: "+ ws_decision);
						dormantCustAlert();
					}
				} else if("DELETE_EXISTING_MEMBER".equalsIgnoreCase(controlName)) {
					int selectedRow = Integer.parseInt(data);
					formObject.setTableCellValue(LVW_FAMILY_MEMBERS, selectedRow, 8, "D");
				} else if("DELETE_NEW_MEMBER".equalsIgnoreCase(controlName)) {
					int selectedRow = Integer.parseInt(data);
					int count = getGridCount(LVW_FAMILY_MEMBERS);
					for(int i=selectedRow+1; i<count; i++) {
						formObject.setTableCellValue(LVW_FAMILY_MEMBERS, i, 0, i+"");
					}
				}
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_CHANGE)) {
				if("table5_cid".equalsIgnoreCase(controlName)) {
					String cid = formObject.getValue("table5_cid").toString();
					String outputFamilyMember = fetchFCRCustomer(cid, "GetCustomerSummary");
					String columns = "SNO,NEW_EXISTING,CID,NAME,CUSTOMER_CATEGORY,DOB,RELATIONSHIP,"
							+ "VALIDATION_RESULT";
					outputFamilyMember = outputFamilyMember.replaceAll("null", "");
					if(getTagValues(outputFamilyMember,"returnCode").equalsIgnoreCase("0") || 
							getTagValues(outputFamilyMember,"returnCode").equalsIgnoreCase("2")) {
						String name = getTagValue(outputFamilyMember,"FullName");
						String custCategory = "";
						String sQuery = "SELECT CUST_SEGMENT FROM USR_0_CUST_SEGMENT WHERE UNIQUE_ID = "
								+ "(SELECT CUST_CATEGORY_CODE FROM USR_0_CUST_CAT WHERE "
								+ "UPPER(CUST_CATEGORY_DESC) = UPPER('"
								+getTagValue(outputFamilyMember,"CustCategory")+"'))";
						logInfo("table5_cid", "sQuery: "+sQuery);
						List<List<String>> sCatOutput = formObject.getDataFromDB(sQuery);
						logInfo("table5_cid", "sCatOutput: "+sCatOutput);
						if(sCatOutput.size() > 0) {
							custCategory = sCatOutput.get(0).get(0);
						}
						logInfo("table5_cid", "custCategory: "+custCategory);
						String dob = getTagValue(outputFamilyMember,"DOB");
						formObject.setValue("table5_name", name);
						formObject.setValue("table5_customerCategory", custCategory);
						formObject.setValue("table5_dob", dob);
						/*if(!custCategory.equalsIgnoreCase("Excellency") 
								&& !custCategory.equalsIgnoreCase("Private Clients")) {
							sendMessageValuesList("table5_cid", "Invalid CID");
							formObject.setValue("table5_cid", "");
						} else {
							
						}*/						
					}
				} else if("MODIFY_EXISTING_MEMBER".equalsIgnoreCase(controlName)){
					logInfo("EVENT_TYPE_CHANGE", "in Event- " + eventType + "control name- " +controlName+ ": ");
					modifyRelationCheck();
				}
			}
		} catch (Exception e) {
			logInfo("executeServerEvent", "Exception in Event- " + eventType + "control name- " +controlName+ ": ");
			logError("executeServerEvent", e);
		} finally {
			logInfo("executeServerEvent", sendMessageList.toString());
			if(!sendMessageList.isEmpty()) {
				if(sendMessageList.get(1).contains(" does not start with 971") ||
						sendMessageList.get(1).equalsIgnoreCase("Mobile number is not of 12 digits") || 
						sendMessageList.get(1).equalsIgnoreCase("Residence Telephone Number is not of 11 digits")) {
					return getReturnMessage(true,controlName,sendMessageList.get(0)+"###"
							+ sendMessageList.get(1));
				}
				return getReturnMessage(false,controlName,sendMessageList.get(0).toString()+"###"
						+ sendMessageList.get(1));
			}
		}
		return retMsg;
	}
	
	private boolean submitFBValidations(String data) {
		try {
			logInfo("submitFBValidations", "INSIDE, data: "+data);
			if(data.isEmpty() || Integer.parseInt(data) == 0) {
				sendMessageValuesList("", "Kindly attach Family Banking Form");
				return false;
			}
			int count = getGridCount(LVW_FAMILY_MEMBERS);
//			if(!isNewFamily && count == 0) {
			if(count == 0) {
				sendMessageValuesList("", "Please enter family members.");
				return false;
//			} else if(isNewFamily) {
			} else {
				int hofCount = 0;
				String hofCategory = "";
				for(int i=0; i<count; i++) {
					if(formObject.getTableCellValue(LVW_FAMILY_MEMBERS, i, 6).equalsIgnoreCase("HEAD OF FAMILY") && 
							!formObject.getTableCellValue(LVW_FAMILY_MEMBERS, i, 8).equalsIgnoreCase("D")) {
						logInfo("submitFBValidations", "hofCount++");
						hofCategory = formObject.getTableCellValue(LVW_FAMILY_MEMBERS, i, 4);
						hofCount++;
					}
				}
				logInfo("submitFBValidations", "hofCount: "+hofCount);//Added By Ayush
				String querytotalcount = "SELECT count(*) FROM usr_0_family_member_details WHERE wi_name='"+sWorkitemId
						   +"'";
	            List<List<String>> outputquerytotalcount = formObject.getDataFromDB(querytotalcount);
            	String total_count = outputquerytotalcount.get(0).get(0);
            	logInfo("submitFBValidations", "total_count"+total_count);
				String querydeletedtotalcount = "SELECT count(*) FROM usr_0_family_member_details WHERE wi_name='"+sWorkitemId
						   +"' AND CUST_STATUS = 'D'";
				List<List<String>> outputquerynotdeletedtotalcount = formObject.getDataFromDB(querydeletedtotalcount);
//				String total_count = outputquerytotalcount.get(0).get(0);
				String total_deleted_count = outputquerynotdeletedtotalcount.get(0).get(0);
				logInfo("submitFBValidations", "total_deleted_count"+total_deleted_count);
				if(!((total_deleted_count).equals(total_count))){
					logInfo("submitFBValidations", "Inside Family Tree Deletion");
				if(hofCount == 0) {
					sendMessageValuesList("", "Please add Head of Family");
					return false;
				} else if(hofCount>1) {
					sendMessageValuesList("", "There can be only one Head of Family");
					return false;
				}
				}
//				}
//				
				for(int i=0; i<count; i++) 
				{
					if(!formObject.getTableCellValue(LVW_FAMILY_MEMBERS, i, 6).equalsIgnoreCase("HEAD OF FAMILY")
							&& !formObject.getTableCellValue(LVW_FAMILY_MEMBERS, i, 8).equalsIgnoreCase("D")) {
						String memberCategory = formObject.getTableCellValue(LVW_FAMILY_MEMBERS, i, 4);
						logInfo("submitFBValidations", "memberCategory: "+memberCategory+", hofCategory: "+
								hofCategory);
						//Ketali change for CR
						//if(!memberCategory.equalsIgnoreCase(hofCategory))
						//{
						if(hofCategory.equalsIgnoreCase("Emirati Excellency")||hofCategory.equalsIgnoreCase("Excellency"))
						{
							if(memberCategory.equalsIgnoreCase("Emirati Excellency")|| memberCategory.equalsIgnoreCase("Excellency"))
							{
								logInfo("submitFBValidations", "category matched as per new CR");
							}
							else
							{
								logInfo("submitFBValidations", "category mismatch");
								//sendMessageValuesList(LVW_FAMILY_MEMBERS, "Family member category should be "+hofCategory );
								sendMessageValuesList(LVW_FAMILY_MEMBERS, "Family member category should be Emirati Excellency or Excellency." );
								return false;
							}
						}
						//Ketali change for CR END
					}
				}
			}
		}catch (NumberFormatException e) {
			logError("submitFBValidations", e);
		}		
		return true;
	}

	@Override
	public String generateHTML(EControl arg0) {
		return null;
	}

	@Override
	public String getCustomFilterXML(FormDef arg0, IFormReference arg1,
			String arg2) {
		return "";
	}

	@Override
	public String introduceWorkItemInWorkFlow(IFormReference arg0,
			HttpServletRequest arg1, HttpServletResponse arg2) {
		return null;
	}

	@Override
	public String introduceWorkItemInWorkFlow(IFormReference arg0,
			HttpServletRequest arg1, HttpServletResponse arg2,
			WorkdeskModel arg3) {
		return null;
	}

	@Override
	public String setMaskedValue(String arg0, String arg1) {
		return arg1;
	}

	@Override
	public void updateDataInWidget(IFormReference arg0, String arg1) {
	}

	@Override
	public String validateDocumentConfiguration(String arg0, String arg1,
			File arg2, Locale arg3) {
		return null;
	}

	@Override
	public JSONArray validateSubmittedForm(FormDef arg0, IFormReference arg1,
			String arg2) {
		return null;
	}

	private boolean validateHOFCID(){
		if(formObject.getValue(CID_HOF).toString().isEmpty()){
			   sendMessageValuesList(CID_HOF, "Please fill Head of Family CID");
			   return false;
		   }
		return true;
	}
	
//	public void modifyRelationCheck(Map relationMap){
//		logInfo("modifyRelationCheck", "in modifyRelationCheck");
//		String relation = formObject.getValue("table5_relation").toString();
//		String cid = formObject.getValue("table5_cid").toString();
//		String compRelation = (String) relationMap.get(Integer.parseInt(cid));
//		if(compRelation == null){
//			compRelation = "";
//		}
//		logInfo("modifyRelationCheck", "relation: "+relation+"  cid: "+cid+"  compRelation: "+compRelation);
//		if(!compRelation.equalsIgnoreCase(relation)){
//			formObject.setValue("table5_status", "M");
//		}
//	}
	
	private void modifyRelationCheck(){
		try {
			logInfo("modifyRelationCheck", "INSIDE");
			String relation = formObject.getValue("table5_relation").toString();
			String cid = formObject.getValue("table5_cid").toString();
			logInfo("modifyRelationCheck", "relation: "+relation+", cid: "+cid);
			/*String compRelation = "";
			String sQuery = "SELECT RELATIONSHIP FROM usr_0_family_member_details where WI_NAME = '"+sWorkitemId
					+"' AND CID = '"+cid+"'";
			logInfo("modifyRelationCheck", "sQuery: "+sQuery);
			List<List<String>> sOutput = formObject.getDataFromDB(sQuery);
			logInfo("modifyRelationCheck", "sOutput: "+sOutput);
			if(sOutput.size() > 0) {
				compRelation = sOutput.get(0).get(0);
			}
			if(compRelation == null	|| compRelation.isEmpty()){
				compRelation = "";
			}
			logInfo("modifyRelationCheck", "relation: "+relation+",  cid: "+cid+",  compRelation: "+compRelation);
			if(!compRelation.equalsIgnoreCase(relation)){
				formObject.setValue("table5_status", "M");
			}*/
//			logInfo("modifyRelationCheck", "relationMap: "+relationMap.toString());
//			String prevRelation = (relationMap.get(cid) != null? relationMap.get(cid): "");
			String prevRelation = formObject.getValue("table5_previousRelation").toString();
			logInfo("modifyRelationCheck", "prevRelation: "+prevRelation+", relation: "+relation);
			if(!prevRelation.trim().equalsIgnoreCase(relation.trim())) {
				logInfo("modifyRelationCheck", "when relation is not same as old");
				formObject.setValue("table5_status", "M");
			} else if(prevRelation.trim().equalsIgnoreCase(relation.trim())){
				logInfo("modifyRelationCheck", "when relation is same as old");
				formObject.setValue("table5_status", "E");
			}
		} catch (Exception e) {
			logError("modifyRelationCheck", e);
		}
	}
}
