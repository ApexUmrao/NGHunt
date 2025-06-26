package com.newgen;

import java.io.StringReader;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


//import com.newgen.wfdesktop.xmlapi.WFCallBroker;

public class Common 
{

	private static Logger logger = Logger.getLogger("consoleLogger");
	public static boolean searchWorkItem(String winame)
	{	
		try 
		{
			String sOutputXML="";
			String sInputXML=GenerateXml.getWFSearchWorkItemListXML(LoadConfiguration.cabinetName,LoadConfiguration.sessionID,winame,LoadConfiguration.processdefID);
			logger.info(sInputXML);
			sOutputXML = exceuteApi(sInputXML);
			logger.info(sOutputXML);
			return checkMainCode(sOutputXML);		
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.info("Error in executing WFCallBroker. (Refer Error logs for more details)");
			logger.info("Error in executing WFCallBroker:"+e.getStackTrace());
			return false;
		}
	}

	public static boolean getWorkItem(String winame)
	{		
		logger.info("Trying to Get WorkFlow...");		
		try 
		{
			String workItemId="";
			String sOutputXML="";
			String sInputXML=GenerateXml.getWMGetWorkItemXML(LoadConfiguration.cabinetName,LoadConfiguration.sessionID,winame,workItemId);			
			sOutputXML = exceuteApi(sInputXML);
			logger.info(sOutputXML);
			return checkMainCode(sOutputXML);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.info("Error in executing WFCallBroker. (Refer Error logs for more details)");
			logger.info("Error in executing WFCallBroker:"+e.getStackTrace());
			return false;
		}
	}
	
	public static boolean completeWorkitemCall(String winame)
	{
		System.out.println("In DisconnectToWorkFlow...");
		logger.info("In DisconnectToWorkFlow...");
		String sInputXML="";//GenerateXml.getWMGetWorkItemXML(LoadConfiguration.cabinetName, LoadConfiguration.sessionID, winame);
		String sOutputXML="";
		try 
		{
			if(searchWorkItem(winame))
			{
				if(getWorkItem(winame)){
					sInputXML=GenerateXml.getWMCompleteWorkItemXML(LoadConfiguration.cabinetName, LoadConfiguration.sessionID, winame,"1");
					sOutputXML = exceuteApi(sInputXML);
					return true;				
				}else{
					logger.info("Error in getWMGetWorkItemXML."+getTagValue(sOutputXML,"Description"));
					return false;
				}
			}else{
				logger.info("Error in searchWorkItem."+getTagValue(sOutputXML,"Description"));
				return false;
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.info("Error in executing WFCallBroker. (Refer Error logs for more details)");			
			return false;
		}	
		
	}

	public static String getTagValue(String xml, String tag)
	{
		Document doc = getDocument(xml);
		NodeList nodeList = doc.getElementsByTagName(tag);
		int length = nodeList.getLength();
		if (length > 0) 
		{
			Node node =  nodeList.item(0);
			if (node.getNodeType() == Node.ELEMENT_NODE) 
			{
				NodeList childNodes = node.getChildNodes();
				String value = "";
				int count = childNodes.getLength();
				for (int i = 0; i < count; i++) 
				{
					Node item = childNodes.item(i);
					if (item.getNodeType() == Node.TEXT_NODE) 
					{
						value += item.getNodeValue();
					}
				}
				return value;
			} 
			else if (node.getNodeType() == Node.TEXT_NODE) 
			{
				return node.getNodeValue();
			}
		}
		return "";
	}

	private static Document getDocument(String xml)
	{

		DocumentBuilderFactory dbf =DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try 
		{
			db = dbf.newDocumentBuilder();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		Document doc = null;
		try 
		{
			doc = db.parse(new InputSource(new StringReader(xml)));
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return doc;
	}
	 
	public static String exceuteApi(String sInputXML){
		try {
		//	return WFCallBroker.execute(sInputXML,LoadConfiguration.jtsIP,LoadConfiguration.jtsPort,1);
			return ExecuteXML.executeXML(sInputXML);
		} catch (Exception e) {
			logger.info("Exception while reading Config file."+e);
			return "";
		}
	}

	private static boolean checkMainCode(String sOutputXML){
		if(sOutputXML==null || "".equalsIgnoreCase(sOutputXML)){				
			logger.info("Problem in Connection. Output XML is Blank.");
			return false;
		}else if("0".equalsIgnoreCase(getTagValue(sOutputXML,"MainCode"))){
			LoadConfiguration.sessionID=getTagValue(sOutputXML,"SessionId");
			return true;
		}else{
			logger.info("Error in connecting to WorkFlow: "+getTagValue(sOutputXML,"Description"));
			return false;
		}
	}
	protected static void setCustomServiceStatus(String serviceStatus,String serviceStatusMsg,String workItemCount){
		try {
			logger.info("setCustomServiceStatus started");
			String strparamName ="'"+LoadConfiguration.pid+"','"+serviceStatus+"','"+serviceStatusMsg+"','"+workItemCount+"'";
			logger.info("setCustomServiceStatus params : "+strparamName);
			String sInputXML_APSelect = "<?xml version=\"1.0\"?>"
					+ "<WMTestProcedure_Input>"
					+ "<Option>APProcedure</Option>"
					+ "<SessionId>" + LoadConfiguration.sessionID + "</SessionId>"                   
					+ "<ProcName>WFSetCustomServiceStatus</ProcName>"
					+ "<Params>" + strparamName + "</Params>"                    
					+ "<EngineName>" + LoadConfiguration.cabinetName + "</EngineName>"
					+ "<WMTestProcedure_Input>";
			logger.info("sendEmailMulExcp Mailing-->" + sInputXML_APSelect);
			logger.info("setCustomServiceStatus input-->" + sInputXML_APSelect);
			String sOutputXML_apselect =  ExecuteXML.executeXML(sInputXML_APSelect);
			logger.info("Procedure setCustomServiceStatus Output "+sOutputXML_apselect);
			logger.info("setCustomServiceStatus ends");
		} catch (Exception e) {
			System.exit(0);
			e.printStackTrace();
		}
	}
}






