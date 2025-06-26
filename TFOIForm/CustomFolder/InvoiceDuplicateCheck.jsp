<%@page import="javax.print.DocFlavor.STRING"%>
<%@page import="java.util.*,java.text.*"%>
<%@page import="com.newgen.wfdesktop.xmlapi.*,java.io.*,java.util.*"%>
<%@page import="com.newgen.omni.wf.util.app.*"%>
<jsp:useBean id="customSession" class="com.newgen.custom.wfdesktop.session.WFCustomSession" scope="session"/>
<html>
<head>
<title>Duplicate Check</title>
<style>
th {
	border: 1px solid Gainsboro;
}
.buttonStyle {
    font-weight: normal;
    font-style: normal;
    padding: 0px 8px;
    border-radius: 3px;
    outline: none;
    font-size: 14px !important;
    color: #ffffff;
    background-color: #ba1b18;
    font-family: Calibri !important;
    border: 1px solid !important;
    border-color: #ccc !important;
    -webkit-transition-duration: 0.4s;
    transition-duration: 0.4s;
}
.button-viewer {
    min-height: 26px;
    min-width: 128px;
    /* max-width: 256px; */
}
</style>
</head>
<script>
function closeandReturn()
{
	
		
	window.parent.handleJSPResponsePPM('InvoiceDuplicateCheck',"");	
	//setPMSFData('btnSubmit',val);
	
	//window.close();
}
</script>
<table cellspacing="1" cellpadding="3" width="100%" >
			<tr>
				<td class="body2" valign="top">
					<table  cellpadding="3" cellspacing="0" width="100%">
						<tr>
							<td  align="left" width="30%" valign="bottom">
								 
								</font>
							</td>
							<td  align="right" width="70%" valign="bottom">								
							    <input Type="button" class="buttonStyle button-viewer" id="close" value="Close" onclick="closeandReturn();"/> 
							</td>							
						</tr>
					</table>
				</td>
			</tr>
</table>
<body>
	<FORM name="mainFrm">
		<div style="overflow: visible; align: center">
			</br>
			<div
				style="height: 100%; width: 100%; overflow: visible; position: absolute;top=30 px">
				<Center style="color :  #ba1b18;font-size: 20pt"><b>Details of workitem </b></Center>
				<table border=1 id="ReferToTable" align="center" >
					<tr bgColor=#ffffff> 
						<th
							style="FONT-FAMILY: Microsoft Sans Serif; color : white; FONT-WEIGHT: normal; font-size: 10pt; width: 80%" bgColor= "#ba1b18"><b>Workitem List</b></th>
					</tr>
						<%!
		private String prepareAPSelectInputXml(String sQuery, String sCabname, String sSessionId){	
				return "<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+sQuery+"</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
			}
		public ArrayList<ArrayList<String>> getTableData(String sOutputXML, String ...sParameters){
			ArrayList<ArrayList<String>> arrL = new ArrayList<ArrayList<String>>();
			ArrayList<String> ArrIntenal = null;
			//System.out.println("OutputXML  "+sOutputXML);
			String sRecord="";
			String sMainCode;
			if(!sOutputXML.equals(""))
			{
				sMainCode = sOutputXML.substring(sOutputXML.indexOf("<MainCode>")+10,sOutputXML.indexOf("</MainCode>"));
				//System.out.println("sMainCode  "+sMainCode);
				if(sMainCode.equalsIgnoreCase("0"))
				{		
						while((sOutputXML.indexOf("<Record>")>-1))
						{	
							ArrIntenal = new ArrayList<String>();
							sRecord = sOutputXML.substring(sOutputXML.indexOf("<Record>")+8,sOutputXML.indexOf("</Record>"));
							//System.out.println("sRecord  "+sRecord);
							for(String sParameter:sParameters){
								String sTempString = sRecord.substring(sRecord.indexOf("<"+sParameter+">")+sParameter.length()+2,sRecord.indexOf("</"+sParameter+">"));
								//System.out.println("sTempString  "+sTempString);
								sTempString=sTempString.replaceAll("\n","<br>");
								//System.out.println("sTempString  "+sTempString);
								ArrIntenal.add(sTempString);
							}
							//System.out.println("internalList  "+ArrIntenal);
							arrL.add(ArrIntenal);
							//System.out.println("MasterList  "+arrL);
							sOutputXML=sOutputXML.substring(sOutputXML.indexOf("</Record>")+"</Record>".length());
						}
					}
				
				}
			return arrL;
		}
		
		%>

						<%

		String sCustID = request.getParameter("sCustID");
		//String sTxnCrncy = request.getParameter("sTxnCrncy");
		//String sTxnAmnt =request.getParameter("sTxnAmnt");
		//String sReqCat =  request.getParameter("sReqCat");
		//String sReqType = request.getParameter("sReqType");
		String sWiName = request.getParameter("sWiName");
		
		//System.out.println("Dup Check Param list  "+sCustID+" "+sTxnCrncy+"	"+sTxnAmnt+"	"+sReqCat+"		"+sReqType+"	"+sWiName);
		
		System.out.println("sWiNAME "+sWiName);
		String sCabname=customSession.getEngineName();
		String sSessionId = customSession.getDMSSessionId();
		String sUserName = customSession.getUserName();
		String sJtsIp = customSession.getJtsIp();
		String wd_uid= customSession.getDMSSessionId();
		String iJtsPort = String.valueOf(customSession.getJtsPort());
		
		String sInputXML="";
		String sOutputXML="";
		ArrayList<ArrayList<String>> getDuplicateList = new ArrayList<ArrayList<String>>();
		
		String sQuery="";
		//sQuery="SELECT WI_NAME from EXT_TFO where CUSTOMER_ID='10006685'";		
		sQuery="select distinct ext.WI_NAME from EXT_TFO ext join TFO_DJ_TSLM_INVOICE_NO_STA_LOAN loan on ext.wi_name=loan.winame where ext.CUSTOMER_ID='"+sCustID+"' and loan.INVOICENO in(select INVOICENO from TFO_DJ_TSLM_INVOICE_NO_STA_LOAN where WINAME = '"+sWiName+"') and ext.wi_name<>'"+sWiName+"'";
		//sQuery = "SELECT WI_NAME FROM TFO_DJ_DEDUP_DTLS WHERE NEW_WI='"+sWiName+"'";	
		System.out.println("QUERY Invoice Duplicate Check "+sQuery );
		if(!(sQuery.isEmpty() || sQuery.equalsIgnoreCase(""))){
			sInputXML = prepareAPSelectInputXml(sQuery,sCabname, sSessionId);
			sOutputXML= NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXML);	
			getDuplicateList = getTableData(sOutputXML,"WI_NAME");
		}
			
		
		for(int cntr=0;cntr<getDuplicateList.size();cntr++){
			%>
						<TR>
						<TD
							style="FONT-FAMILY: Microsoft Sans Serif; FONT-WEIGHT: normal; font-size: 10pt"><%=getDuplicateList.get(cntr).get(0)%></TD>
						</TR>	
						<%
				}
		%>		
					<br>
				</table>
			</div>
		</div>
	</FORM>
</body>
</html>
