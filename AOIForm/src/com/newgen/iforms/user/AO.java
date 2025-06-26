package com.newgen.iforms.user;

import java.util.List;
import com.newgen.iforms.user.ao.DeliveryChecker;
import com.newgen.iforms.user.ao.CPDBulkEODChecker;
import com.newgen.iforms.custom.IFormListenerFactory;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.iforms.user.ao.AccountRelation;
import com.newgen.iforms.user.ao.ApplicationAssessment;
import com.newgen.iforms.user.ao.CPDBulkEODCheckerHistory;
import com.newgen.iforms.user.ao.CPDMakerFourStep;
import com.newgen.iforms.user.ao.CPDMakerThreeStep;
import com.newgen.iforms.user.ao.Common;
import com.newgen.iforms.user.ao.ContactCenterCPD;
import com.newgen.iforms.user.ao.ContactCenterTeam;
import com.newgen.iforms.user.ao.CustomerScreening;
import com.newgen.iforms.user.ao.DDEChecker;
import com.newgen.iforms.user.ao.DDECustAccountInfo;
import com.newgen.iforms.user.ao.Introduction;
import com.newgen.iforms.user.ao.Levels;
import com.newgen.iforms.user.ao.MailRoomOperation;
import com.newgen.iforms.user.ao.PhysicalReconciliation;
import com.newgen.iforms.user.ao.QDEChecker;
import com.newgen.iforms.user.ao.QDECustAccountInfo;
import com.newgen.iforms.user.ao.FBChecker;
import com.newgen.iforms.user.ao.FBMaker;
import com.newgen.iforms.user.ao.ReadOnly;
import com.newgen.iforms.user.ao.Referrals;
import com.newgen.iforms.user.ao.Repair;
import com.newgen.iforms.user.ao.DeliveryMaker;

public class AO extends Common implements IFormListenerFactory {
	@Override
	public IFormServerEventHandler getClassInstance(IFormReference iFormReference) {
		String processName = iFormReference.getProcessName();
		String activityName = iFormReference.getActivityName();
		String sMode = iFormReference.getObjGeneralData().getM_strMode();		
		log.info("ProcessName: "+processName);
		log.info("ActivityName: "+activityName);
		log.info("Mode: "+sMode);
		if("R".equalsIgnoreCase(sMode) || "Exit".equalsIgnoreCase(activityName)){
			log.info("ReadOnlyOK: ");
			return new ReadOnly(iFormReference);
		} else if ("Introduction".equalsIgnoreCase(activityName)) {
			return new Introduction(iFormReference);
		} else if("DDE_Cust_Info".equalsIgnoreCase(activityName) || "DDE_Account_Info".equalsIgnoreCase(activityName)){
			return new DDECustAccountInfo(iFormReference);
		} else if("Account_Relation".equalsIgnoreCase(activityName)){
			return new AccountRelation(iFormReference);
		} else if("Contact Center Team".equalsIgnoreCase(activityName)){
			return new ContactCenterTeam(iFormReference);
		} else if("Contact_Center_CPD".equalsIgnoreCase(activityName)){
			return new ContactCenterCPD(iFormReference);
		} else if("QDE_Cust_Info".equalsIgnoreCase(activityName) 
				|| "QDE_Account_Info".equalsIgnoreCase(activityName) 
				|| "QDE_ Account_Info".equalsIgnoreCase(activityName)){
			return new QDECustAccountInfo(iFormReference);
		} else if("DDE_Acc_Info_Chk".equalsIgnoreCase(activityName)){
			return new DDEChecker(iFormReference);
		} else if("Application_Assessment".equalsIgnoreCase(activityName)){
			return new ApplicationAssessment(iFormReference);
		}  else if("Customer_Screen".equalsIgnoreCase(activityName)){
			return new CustomerScreening(iFormReference);
		} else if("Level1 Approved".equalsIgnoreCase(activityName) 
				|| "Level2 Approved".equalsIgnoreCase(activityName) 
				|| "Level3 Approved".equalsIgnoreCase(activityName) 
				|| "Level4 Approved".equalsIgnoreCase(activityName)){
			return new Levels(iFormReference);
		} else if("Customer_Screen_QDE".equalsIgnoreCase(activityName)){
			return new CustomerScreening(iFormReference);
		} else if("Physical Reconciliation".equalsIgnoreCase(activityName)){
			return new PhysicalReconciliation(iFormReference);
		} else if("Compliance Approver".equalsIgnoreCase(activityName) || "PBG Vigilance".equalsIgnoreCase(activityName)){ // Gurwinder PBG Vigilance Chnage 27062023
			return new Referrals(iFormReference);
		} else if("QDE_Acc_Info_Chk".equalsIgnoreCase(activityName)){
			return new QDEChecker(iFormReference);
		} else if("MailRoomOperation".equalsIgnoreCase(activityName)){
			log.info("Calling Mailroom Operation classssss");
			return new MailRoomOperation(iFormReference);
		} else if(activityName.equalsIgnoreCase("CPD Checker")|| activityName.equalsIgnoreCase("RM")) {
            return new CPDBulkEODChecker(iFormReference);
        } else if(activityName.equalsIgnoreCase("CPD Maker")) {
        	 String sQuery1="";            
        	 String workitem_No = iFormReference.getObjGeneralData().getM_strProcessInstanceId();//formObject.getValue("").toString();
             sQuery1="SELECT SCAN_MODE from ext_ao where wi_name='"+workitem_No+"'";
             logInfo("AO",sQuery1);
             List<List<String>> output1=iFormReference.getDataFromDB(sQuery1);
             String scanMode = output1.get(0).get(0);    //getTagValues(output1,"SCAN_MODE");
             logInfo("AO", output1.get(0).toString());
             logInfo("AO","Scan Mode In User Factory == "+scanMode);
             if(scanMode.equalsIgnoreCase("New WMS ID")){
                 return new CPDMakerThreeStep(iFormReference);
             }else if(scanMode.equalsIgnoreCase("Existing WMS ID")){
            	 return new CPDMakerFourStep(iFormReference);
             }
            //
           // return new CPDMakerThreeStep(iFormReference);
        } else if("Repair".equalsIgnoreCase(activityName)){
			return new Repair(iFormReference);
		} else if("Bulk EOD Checker".equalsIgnoreCase(activityName)){
			return new CPDBulkEODCheckerHistory(iFormReference);
		} else if("Delivery_Maker".equalsIgnoreCase(activityName)) {
            return new DeliveryMaker(iFormReference);
        } else if("Delivery_Checker".equalsIgnoreCase(activityName)) {
            return new DeliveryChecker(iFormReference);
        } else if("FB_Maker".equalsIgnoreCase(activityName)){
			return new FBMaker(iFormReference);
		} else if("FB_Checker".equalsIgnoreCase(activityName)){
			return new FBChecker(iFormReference);
		}
		return null;
	}
}
