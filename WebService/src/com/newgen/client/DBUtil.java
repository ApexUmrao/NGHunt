package com.newgen.client;

import java.io.IOException;
import com.newgen.wfdesktop.xmlapi.WFCallBroker;

public class DBUtil {
	
	
	
	GenerateXml objXmlGen = null;
	
	public DBUtil()
	{
		
		objXmlGen = new GenerateXml();
	}
	
	public String ExecuteCustomInsertQuery( String table,String Col,String ColValue,String cabinetName,String sessionId,String jtsIP,String jtsPort) throws NumberFormatException, IOException, Exception
	{
		try
		{
			String apInsert_InputXml =objXmlGen.PrepareQuery_APInsert(table,Col,ColValue,cabinetName,sessionId);
			LogGEN.writeTrace("Log", "in DUtil---"+apInsert_InputXml);

			String apInsert_OutputXml = WFCallBroker.execute(apInsert_InputXml,jtsIP,Integer.parseInt(jtsPort),1);
			LogGEN.writeTrace("Log", "in DUtil---"+apInsert_OutputXml);
			return apInsert_OutputXml;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "ashish";
	}
	
	public String ExecuteCustomSelectQuery(String Query,String cabinetName,String sessionId,String jtsIP,String jtsPort) throws NumberFormatException, IOException, Exception
	{
		String selectInputXml = objXmlGen.PrepareQuery_APSelect(Query,cabinetName,sessionId);


		String selectOutputXml = WFCallBroker.execute(selectInputXml,jtsIP,Integer.parseInt(jtsPort),1);

		
		return selectOutputXml;
	}
	
	public String WFUploadWorkItem(String processDefID,String WFAttributeList,String cabinetName,String sessionId,String jtsIP,String jtsPort) throws NumberFormatException, IOException, Exception
	{
		String wi_InputXml = objXmlGen.get_WFUploadWorkItem_Input(cabinetName, sessionId, processDefID, WFAttributeList.toString());
		
		String wi_OutputXml = WFCallBroker.execute(wi_InputXml,jtsIP,Integer.parseInt(jtsPort),1);
			
		return wi_OutputXml;
	}
	
	public String ExecuteCustomDeleteQuery(String podetailstableOP, String sWhere,String cabinetName,String sessionId,String jtsIP,String jtsPort) throws NumberFormatException, IOException, Exception
	{
		String selectInputXml = objXmlGen.XMLIn_APDelete(podetailstableOP,sWhere,cabinetName, sessionId);
		
		String selectOutputXml = WFCallBroker.execute(selectInputXml,jtsIP,Integer.parseInt(jtsPort),1);
			
		return selectOutputXml;
	}
	
	public String ExecuteCustomUpdateQuery(String extmaintableOP,String colmname,String colvalue ,String sWhere,String cabinetName,String sessionId,String jtsIP,String jtsPort) throws NumberFormatException, IOException, Exception
	{
		String apUpdate_CompleteInputXml = objXmlGen.XMLIn_APUpdate(extmaintableOP,colmname,colvalue,sWhere,cabinetName, sessionId);
		
//	}
	String apUpdate_CompleteOutputXml = WFCallBroker.execute(apUpdate_CompleteInputXml,jtsIP,Integer.parseInt(jtsPort),1);
			
		return apUpdate_CompleteOutputXml;
	}
	
	

}
