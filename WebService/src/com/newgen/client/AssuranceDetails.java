package com.newgen.client;



public class AssuranceDetails extends WebServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;

	
	/**
	 * Function written to fetch Assurance details
	 * @author gupta.ashish
	 * @param sInputXML
	 * @return
	 */

		public String FetchAssuranceDetails(String sInputXML) 
		{
			LogGEN.writeTrace("Log", "Fuction called FetchCustomerMISInformation");
			LogGEN.writeTrace("Log", "sInputXML---"+sInputXML);
			XMLParser xmlDataParser = new XMLParser();
			xmlDataParser.setInputXML(sInputXML);
			
			WebServiceHandler sHandler= new WebServiceHandler();
			try
			{
				sHandler.readCabProperty("Customer_MIS_Information");
				
				sWSDLPath=(String)currentCabPropertyMap.get("WSDL_PATH");
				sCabinet=(String)currentCabPropertyMap.get("CABINET");
				sUser=(String)currentCabPropertyMap.get("USER");
				sLoginReq=Boolean.valueOf((String)currentCabPropertyMap.get("LOGIN_REQ"));
				sPassword=(String)currentCabPropertyMap.get("PASSWORD");
				
				LogGEN.writeTrace("Log", "read Property successfully");
				LogGEN.writeTrace("Log", "WSDL PATH---- "+(String)currentCabPropertyMap.get("WSDL_PATH"));
				LogGEN.writeTrace("Log", "CABINET---- "+(String)currentCabPropertyMap.get("CABINET"));
				LogGEN.writeTrace("Log", "USER---- "+(String)currentCabPropertyMap.get("USER"));
				LogGEN.writeTrace("Log", "PASSWORD---- "+(String)currentCabPropertyMap.get("PASSWORD"));
				LogGEN.writeTrace("Log", "LOGIN_REQ---- "+(String)currentCabPropertyMap.get("LOGIN_REQ"));
			}
			catch(Exception e)
			{
				LogGEN.writeTrace("Log", "IndividualRisk Exception--- "+e);
			}
			return "";
		}
}