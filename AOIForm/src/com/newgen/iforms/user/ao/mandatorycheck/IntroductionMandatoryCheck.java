package com.newgen.iforms.user.ao.mandatorycheck;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.user.ao.Common;
import com.newgen.iforms.user.config.AOLogger;
import com.newgen.iforms.user.config.Constants;
import com.newgen.iforms.user.config.ConstantAlerts;

public class IntroductionMandatoryCheck implements Constants,ConstantAlerts {
	static Logger aoLogger = AOLogger.getInstance().getLogger();

	@SuppressWarnings("unchecked")
	public static List mandatoryAtIntroduction(IFormReference formObject)
	{
		List<String> sendMessageList = new ArrayList<>();
		if(formObject.getValue("SOURCING_CHANNEL").toString().equalsIgnoreCase("")) {
			sendMessageList = sendMessageMandatoryValuesList("SOURCING_CHANNEL",CA006);
			return sendMessageList;
		} else if(!(formObject.getValue("SOURCING_CHANNEL").toString().equalsIgnoreCase("DFC")
				|| formObject.getValue("SOURCING_CHANNEL").toString().equalsIgnoreCase("Branch-Excellency")
				|| formObject.getValue("SOURCING_CHANNEL").toString().equalsIgnoreCase("Private Center") 
				|| formObject.getValue("SOURCING_CHANNEL").toString().equalsIgnoreCase("sales") 
				|| formObject.getValue("SOURCING_CHANNEL").toString().equalsIgnoreCase("Branch") 
				|| formObject.getValue("SOURCING_CHANNEL").toString().equalsIgnoreCase("Emirati"))){
					sendMessageList = sendMessageMandatoryValuesList("",CA0900);
					return sendMessageList;
		} else if(formObject.getValue("SOURCING_CHANNEL").toString().equalsIgnoreCase("Branch-Excellency")
				&& (formObject.getValue("SOURCING_CENTER").toString().equalsIgnoreCase("") 
				|| formObject.getValue("SOURCING_CENTER").toString().equalsIgnoreCase(""))){
					sendMessageList = sendMessageMandatoryValuesList("SOURCING_CENTER",CA0141);
					return sendMessageList;
		} else if(formObject.getValue("SOURCING_CHANNEL").toString().equalsIgnoreCase("Private Center") 
				&& (formObject.getValue("SOURCING_CENTER").toString().equalsIgnoreCase("") 
				|| formObject.getValue("SOURCING_CENTER").toString().equalsIgnoreCase(""))){
					sendMessageList = sendMessageMandatoryValuesList("SOURCING_CENTER",CA0141);
					return sendMessageList;
		} else if(formObject.getValue("SOURCING_CHANNEL").toString().equalsIgnoreCase("sales")
				&& (formObject.getValue("SOURCING_CENTER").toString().equalsIgnoreCase("") 
				|| formObject.getValue("SOURCING_CENTER").toString().equalsIgnoreCase(""))){
					sendMessageList = sendMessageMandatoryValuesList("SOURCING_CENTER",CA0141);
					return sendMessageList;
		} else if(formObject.getValue("SOURCING_CHANNEL").toString().equalsIgnoreCase("DFC")
				&& (formObject.getValue("SOURCING_CENTER").toString().equalsIgnoreCase("") 
				|| formObject.getValue("SOURCING_CENTER").toString().equalsIgnoreCase(""))){
					sendMessageList = sendMessageMandatoryValuesList("SOURCING_CENTER",CA0141);
					return sendMessageList;
		} else if(formObject.getValue("REQUEST_TYPE").toString().equalsIgnoreCase("")){
					sendMessageList = sendMessageMandatoryValuesList("REQUEST_TYPE",CA003);
					return sendMessageList;
		} else if(formObject.getValue("DATA_ENTRY_MODE").toString().equalsIgnoreCase("")){
					sendMessageList = sendMessageMandatoryValuesList("DATA_ENTRY_MODE",CA002);
					aoLogger.info("send message list inside mand check : "+sendMessageList);
					return sendMessageList;
		} else if(formObject.getValue("ACC_HOME_BRANCH").toString().equalsIgnoreCase("") 
				&& (!formObject.getValue("REQUEST_TYPE").toString().equalsIgnoreCase("Category Change Only"))){
					sendMessageList = sendMessageMandatoryValuesList("ACC_HOME_BRANCH",CA004);
					return sendMessageList;
		} else if(formObject.getValue("ACC_OWN_TYPE").toString().equalsIgnoreCase("")
				&& (formObject.getValue("REQUEST_TYPE").toString().equalsIgnoreCase("New Account") 
				||formObject.getValue("REQUEST_TYPE").toString().equalsIgnoreCase("New Account with Category Change"))){
					sendMessageList = sendMessageMandatoryValuesList("ACC_OWN_TYPE",CA007);
					return sendMessageList;
		} else if(!formObject.getValue("REQUEST_TYPE").toString().equalsIgnoreCase("Category Change Only") 
				&& (formObject.getValue("ACC_CLASS").toString().equalsIgnoreCase("") 
				&& (formObject.getValue("REQUEST_TYPE").toString().equalsIgnoreCase("New Account")
				||formObject.getValue("REQUEST_TYPE").toString().equalsIgnoreCase("New Account with Category Change")))){
					sendMessageList = sendMessageMandatoryValuesList("ACC_CLASS",CA005);
					return sendMessageList;
		}	
		return sendMessageList;
	}
	public static List sendMessageMandatoryValuesList(String controlId,String Message) { 
		List<String> sendMandatoryMessageList = new ArrayList<>();
		try {
			aoLogger.info("in sendMessagePMHashMap");
			sendMandatoryMessageList.clear();
			sendMandatoryMessageList.add(controlId);
			sendMandatoryMessageList.add(Message);
			aoLogger.info("sendMandatoryMessageList: "+sendMandatoryMessageList);
			return sendMandatoryMessageList;
		} catch (Exception e) { 
			aoLogger.error("excpetion in sendMessageHashMap: "+e,e);
		}
		return sendMandatoryMessageList;
	}

}
