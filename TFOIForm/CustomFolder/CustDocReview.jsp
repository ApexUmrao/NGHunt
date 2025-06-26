<%
/**
----------------------------------------------------------------------------------------------------
				NEWGEN SOFTWARE TECHNOLOGIES LIMITED
	Group						:	AP2
	Product / Project			:	Trade Finance
	Module						:	Custom JSP
	File Name					:	CustDocReview.jsp
	Author						:	Amrendra Pratap Singh
	Date written (DD/MM/YYYY)	:	05-10-2018
	Description					:	This is the custom jsp to load Checklist for Document Review guideline.
----------------------------------------------------------------------------------------------------
				CHANGE HISTORY
----------------------------------------------------------------------------------------------------
	Date(DD/MM/YYYY)		 Change By	 			Change ID/Bug Id				Change Description (Bug No. (If Any))
	
 
----------------------------------------------------------------------------------------------------
*/


%>
<%@page import="javax.print.DocFlavor.STRING"%>
<%@page import="java.util.*,java.text.*"%>
<%@page import="java.io.*,java.util.*,com.newgen.omni.wf.util.app.*"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.Properties"%>
<%@page import="org.apache.log4j.PropertyConfigurator"%>
<%@page import ="org.apache.log4j.Logger"%>

<jsp:useBean id="customSession" class="com.newgen.custom.wfdesktop.session.WFCustomSession" scope="session"/>
<html>
<head>
<title>Document Review</title>
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
<body onbeforeunload="ConfirmClose()"  >
	<FORM name="mainFrm" action="javascript:selectandreturn()">
		<div style="overflow: visible; align: center">
			</br>
			<div
				style="height: 100%; width: 80%; overflow: visible; position: absolute;top=30 px">
				<Center style="color : #ba1b18;font-size: 20pt"><b>Customer Application Verification Form</b></Center>
				<table width=770px id="ReferToTable" align="center" >
					<tr bgColor=#ffffff> 
						<th width=600px
							style="FONT-FAMILY: Calibri; color : white; FONT-WEIGHT: normal; font-size: 10pt;" bgColor="#ba1b18"><b>Description</b></th>
						<th width=170px
							style="FONT-FAMILY: Calibri; color : white; FONT-WEIGHT: normal; font-size: 10pt;"  bgColor="#ba1b18"><b>Value</b></th>
					</tr>
						<%!
						
						public void initializeLogger(){
			try{
				Properties properties = new Properties();
				String log4JPropertyFile =  System.getProperty("user.dir") +  System.getProperty("file.separator") + "NGConfig" + System.getProperty("file.separator") + "TFO" +  System.getProperty("file.separator") +  "log4jJSP.properties"; 
				//String log4JPropertyFile =  System.getProperty("user.dir") +  System.getProperty("file.separator") + "ProcessLoggerConfig" +  System.getProperty("file.separator") +  "log4jJSP.properties";
				properties.load(new FileInputStream(log4JPropertyFile));
				PropertyConfigurator.configure(properties);
			}catch(Exception e){
				e.printStackTrace();
			}
		} 
		
	Logger log = Logger.getLogger(_CustDocReview.class);
	
	private void WriteToLog_showpage(String flag,String strOutput){
		log.info(strOutput);
	}	
						
	/*		
						private void WriteToLog_showpage(String flag,String strOutput)
{
    StringBuffer str = new StringBuffer();
    str.append(DateFormat.getDateTimeInstance(0,2).format(new java.util.Date()));
    str.append("\n");
    str.append(strOutput);
    str.append("\n");

    StringBuffer strFilePath = null;
    String tmpFilePath="";
    try 
    {
		
		if("Y".equalsIgnoreCase(flag) || "I".equalsIgnoreCase(flag)){
			Calendar calendar=new GregorianCalendar();
			String DtString=String.valueOf(""+calendar.get(Calendar.DAY_OF_MONTH) +(calendar.get(Calendar.MONTH) + 1) + calendar.get(Calendar.YEAR));
			strFilePath = new StringBuffer(50);
			strFilePath.append(System.getProperty("user.dir"));
			strFilePath.append(File.separatorChar);
			strFilePath.append("ApplicationLogs");
			strFilePath.append(File.separatorChar);
			strFilePath.append("ProcessLogs");
			strFilePath.append(File.separatorChar);
			strFilePath.append("TFO");
			strFilePath.append(File.separatorChar);
			strFilePath.append("JSPLogs");
			strFilePath.append(File.separatorChar);
			strFilePath.append("CustDocReview"+DtString+".xml");
			tmpFilePath = strFilePath.toString();
			
			//PrintStream out = new PrintStream(new FileOutputStream(tmpFilePath), true);
			BufferedWriter out = new BufferedWriter(new FileWriter(tmpFilePath, true));
			
			out.write(str.toString());
			out.close();
			
		}
    } catch (Exception exception) {
    } finally {
        strFilePath = null;
    }    
}*/

		private String prepareAPSelectInputXml(String sQuery, String sCabname, String sSessionId){	
				return "<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+sQuery+"</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
			}
		public HashMap<String, ArrayList<HashMap<String, String>>> getMasterTableLOV(String sOutPut){
			HashMap<String, ArrayList<HashMap<String, String>>> hmapOuter = new HashMap<String, ArrayList<HashMap<String,String>>>();
			HashMap<String, String>hmapInner;
			ArrayList<HashMap<String, String>> arrHmap;
			ArrayList<HashMap<String, String>> arrHmap1;
			String sMainCode;
			String sPrevKey="";
			String sRecord, sNKey, sCODE, sDescription;	
			if(!sOutPut.equals(""))
			{
				sMainCode = sOutPut.substring(sOutPut.indexOf("<MainCode>")+10,sOutPut.indexOf("</MainCode>"));

			if(sMainCode.equalsIgnoreCase("0"))
			{		
				int i=1;
				int masterCounter=1;
					while((sOutPut.indexOf("<Record>")>-1))	
					{	hmapInner = new HashMap<String, String>();
						arrHmap =  new ArrayList<HashMap<String, String>>();
						sRecord = sOutPut.substring(sOutPut.indexOf("<Record>")+8,sOutPut.indexOf("</Record>"));
						sNKey = sRecord.substring(sRecord.indexOf("<KEY>")+5,sRecord.indexOf("</KEY>"));
						sCODE = sRecord.substring(sRecord.indexOf("<CODE>")+6,sRecord.indexOf("</CODE>"));
						sDescription = sRecord.substring(sRecord.indexOf("<DESCRIPTION>")+13,sRecord.indexOf("</DESCRIPTION>"));
						hmapInner.put(sCODE, sDescription);
					
						if(i==1){							
							sPrevKey = sNKey;
						}
						else{
							if(!sPrevKey.equalsIgnoreCase(sNKey)){
							hmapOuter.put(sPrevKey, arrHmap);
							arrHmap =  new ArrayList<HashMap<String, String>>();
							arrHmap.add(hmapInner);	
							}
						}					
						sPrevKey = sNKey;
						i++;
						//hmapOuter.put(sNKey, hmapInner);
						sOutPut=sOutPut.substring(sOutPut.indexOf("</Record>")+"</Record>".length());
					}
				}
			
			}
			return hmapOuter;
		}
		/*
		 public String getGridData(String sQvarName){
              String res="";
              int gridLength=0;
              gridLength = formObject.getNGListCount(sQvarName);
              WriteToLog_showpage("Y","Grid Length "+gridLength);
              for(int Cntr = 0; Cntr< gridLength;Cntr++){
                     res += formObject.getNGValue(sQvarName, Cntr, 1)+"~"+formObject.getNGValue(sQvarName, Cntr, 2)+"~~";
              }
              if(res.contains("&"))
                     res =res.replace("&", "AmpersandSign");
              if(res.contains("%"))
                     res =res.replace("%", "PercentSign");
              if(res.contains("~~~"))
                     res =res.replace("~~~", "~blankValue~~");
 
              return res;
       }
	   	   */
		public ArrayList<ArrayList<String>> getGridData(String sOutputXML, String ...sParameters){
			ArrayList<ArrayList<String>> arrL = new ArrayList<ArrayList<String>>();
			ArrayList<String> ArrIntenal = null;
			WriteToLog_showpage("Y","OutputXML  "+sOutputXML);
			String sRecord="";
			String sMainCode;
			if(!sOutputXML.equals(""))
			{
				sMainCode = sOutputXML.substring(sOutputXML.indexOf("<MainCode>")+10,sOutputXML.indexOf("</MainCode>"));
				WriteToLog_showpage("Y","sMainCode  "+sMainCode);
				if(sMainCode.equalsIgnoreCase("0"))
				{		
						while((sOutputXML.indexOf("<Record>")>-1))
						{	
							ArrIntenal = new ArrayList<String>();
							sRecord = sOutputXML.substring(sOutputXML.indexOf("<Record>")+8,sOutputXML.indexOf("</Record>"));
							WriteToLog_showpage("Y","sRecord  "+sRecord);
							for(String sParameter:sParameters){
								String sTempString = sRecord.substring(sRecord.indexOf("<"+sParameter+">")+sParameter.length()+2,sRecord.indexOf("</"+sParameter+">"));
								WriteToLog_showpage("Y","sTempString  "+sTempString);
								ArrIntenal.add(sTempString);
							}
							WriteToLog_showpage("Y","internalList  "+ArrIntenal);
							arrL.add(ArrIntenal);
							WriteToLog_showpage("Y","MasterList  "+arrL);
							sOutputXML=sOutputXML.substring(sOutputXML.indexOf("</Record>")+"</Record>".length());
						}
					}
				
				}
			return arrL;
		}  
	   public HashMap<String, String> getFinalWMSMap(ArrayList<String> arrFinal,ArrayList<ArrayList<String>> arrMapName){
		  HashMap<String, String> resGridData = new HashMap<String, String>();
		  try{
		   WriteToLog_showpage("Y","ArrayMapName  "+arrMapName);
		   WriteToLog_showpage("Y","ArrayFinal "+arrFinal);
		   
			for(int i=0;i<arrMapName.size();i++){
				WriteToLog_showpage("Y","Val1 "+arrMapName.get(i).get(0) +" Val2 "+arrFinal.get(i));
				resGridData.put(arrMapName.get(i).get(0),arrFinal.get(i));
			}
			return resGridData;
		  }catch(Exception e){
			  return resGridData;
		  }
		   
	   }
	   	public String getQuery(ArrayList<ArrayList<String>> arrMappedControls, String sGtee_Number, String sRequestCategory){
			try{
				String sTableName="";
				if("GRNT".equalsIgnoreCase(sRequestCategory))
					sTableName = "TFO_WMS_MASTER";
				else if("ILC".equalsIgnoreCase(sRequestCategory) || "ELC".equalsIgnoreCase(sRequestCategory))
					sTableName = "TFO_WMS_MASTER_IELC";
				String sQuery="Select ";
				
				for(ArrayList<String> arrInt : arrMappedControls){
					sQuery+=arrInt.get(1)+",";
				}
				sQuery = sQuery.substring(0,sQuery.lastIndexOf(","));
				
				
				sQuery += " FROM "+sTableName+" where GTEE_NUMBER ='"+sGtee_Number+"'"; 
				return sQuery;
			}catch(Exception e){
				return "";
			}
			
		} 

		public String getQueryExt(ArrayList<ArrayList<String>> arrMappedControls, String wiName){
			try{
				String sQuery="Select ";
				for(ArrayList<String> arrInt : arrMappedControls){
					sQuery+=arrInt.get(1)+",";
				}
				sQuery = sQuery.substring(0,sQuery.lastIndexOf(","));
				sQuery += " FROM EXT_TFO where WI_NAME ='"+wiName+"'"; 
				return sQuery;
			}catch(Exception e){
				return "";
			}
			
		}	
		
		public ArrayList<String> getWMSMasterData(String sOutputXML,ArrayList<ArrayList<String>> arrMappedControl){
			//ArrayList<ArrayList<String>> arrL = new ArrayList<ArrayList<String>>();
			ArrayList<String> ArrIntenal = new ArrayList<String>();
			
			WriteToLog_showpage("Y","OutputXML  "+sOutputXML);
			String sRecord="";
			String sMainCode;
			if(!sOutputXML.equals(""))
			{
				sMainCode = sOutputXML.substring(sOutputXML.indexOf("<MainCode>")+10,sOutputXML.indexOf("</MainCode>"));
				WriteToLog_showpage("Y","sMainCode  "+sMainCode);
				if(sMainCode.equalsIgnoreCase("0"))
				{		
						while((sOutputXML.indexOf("<Record>")>-1))
						{	
							sRecord = sOutputXML.substring(sOutputXML.indexOf("<Record>")+8,sOutputXML.indexOf("</Record>"));
							WriteToLog_showpage("Y","sRecord  "+sRecord);
							
							
							for(int i=0;i<arrMappedControl.size();i++){
								String sTempString = sRecord.substring(sRecord.indexOf("<"+arrMappedControl.get(i).get(1)+">")+arrMappedControl.get(i).get(1).length()+2,sRecord.indexOf("</"+arrMappedControl.get(i).get(1)+">"));
								WriteToLog_showpage("Y","sTempString  "+sTempString);
								ArrIntenal.add(sTempString);
								
							}/*
							for(ArrayList<String> arrInt: arrMappedControl){
								for(String str : arrInt){
									String sTempString = sRecord.substring(sRecord.indexOf("<"+str+">")+str.length()+2,sRecord.indexOf("</"+str+">"));
									WriteToLog_showpage("Y","sTempString  "+sTempString);
									ArrIntenal.add(sTempString);	
								}
							}*/
							WriteToLog_showpage("Y","internalList  "+ArrIntenal);
							//arrL.add(ArrIntenal);
							//WriteToLog_showpage("Y","MasterList  "+arrL);
							sOutputXML=sOutputXML.substring(sOutputXML.indexOf("</Record>")+"</Record>".length());
						}
					}
				
				}
			return ArrIntenal;	
		}	
		public ArrayList<ArrayList<String>> getMasterDocumentTable(String sOutput){
			ArrayList<ArrayList<String>> ArrMaster = new ArrayList<ArrayList<String>>();
			ArrayList<String> ArrIntenal;
			String sInput;
			String sMainCode;
			String sRecord, sLabel, sLovStatus, sTableRef, sIsTP,sControlName, sLength, sDefValue, sEnableStatus;
			if(!sOutput.equals(""))
			{
				sMainCode = sOutput.substring(sOutput.indexOf("<MainCode>")+10,sOutput.indexOf("</MainCode>"));
			if(sMainCode.equalsIgnoreCase("0"))
			{		
				int i=0;
				int masterCounter=1;
					while((sOutput.indexOf("<Record>")>-1))
					{	
						ArrIntenal = new ArrayList<String>();
						sRecord = sOutput.substring(sOutput.indexOf("<Record>")+8,sOutput.indexOf("</Record>"));
						sLabel =sRecord.substring(sRecord.indexOf("<LABEL>")+7,sRecord.indexOf("</LABEL>"));
						sLovStatus =sRecord.substring(sRecord.indexOf("<LOV_STATUS>")+12,sRecord.indexOf("</LOV_STATUS>"));
						sTableRef =sRecord.substring(sRecord.indexOf("<TABLE_REF>")+11,sRecord.indexOf("</TABLE_REF>"));
						sIsTP = sRecord.substring(sRecord.indexOf("<IS_THIRD_PARTY>")+16,sRecord.indexOf("</IS_THIRD_PARTY>"));
						sControlName = sRecord.substring(sRecord.indexOf("<MAPPED_CONTROLNAME>")+20,sRecord.indexOf("</MAPPED_CONTROLNAME>"));
						sLength = sRecord.substring(sRecord.indexOf("<VAR_LENGTH>")+12,sRecord.indexOf("</VAR_LENGTH>"));
						sDefValue = sRecord.substring(sRecord.indexOf("<DEFAULTVALUE>")+14,sRecord.indexOf("</DEFAULTVALUE>"));
						sEnableStatus = sRecord.substring(sRecord.indexOf("<ENABLEDSTATUS>")+15,sRecord.indexOf("</ENABLEDSTATUS>"));
						ArrIntenal.add(sLabel);
						ArrIntenal.add(sLovStatus);
						ArrIntenal.add(sTableRef);
						ArrIntenal.add(sIsTP);
						ArrIntenal.add(sControlName);
						ArrIntenal.add(sLength);
						ArrIntenal.add(sDefValue);
						ArrIntenal.add(sEnableStatus);
						ArrMaster.add(ArrIntenal);
						sOutput=sOutput.substring(sOutput.indexOf("</Record>")+"</Record>".length());
					}
				}
			
			}
			return ArrMaster;
		}
		
		public HashMap<String, String> getDocGridData(String gridData){
			HashMap<String, String> resGridData = new HashMap<String, String>();
	
			String sArr[] = gridData.split("~~");
			for(int i=0;i<sArr.length;i++){
				String rowData[]=sArr[i].split("~");
				String sValue = "";
				if(!"blankValue".equalsIgnoreCase(rowData[1]))
					sValue = rowData[1];				
				resGridData.put(rowData[0], sValue);
			}
			
			return resGridData;
		}
	 public HashMap<String, String> arrListToHashMap(ArrayList<ArrayList<String>> arrL){
		   HashMap<String, String> resGridData = new HashMap<String, String>();
		   
		   for(ArrayList<String> arrInner : arrL ){
			WriteToLog_showpage("Y","Val1 "+arrInner.get(0) +" Val2 "+arrInner.get(1));
			   resGridData.put(arrInner.get(0),arrInner.get(1));
		   }		   
		   return resGridData;
	   }
		public HashMap<String, String> getMasterDoHashMap(String sKey,String sOutput){
			HashMap<String, String> ArrMaster = new HashMap<String, String>();
			ArrayList<String> ArrIntenal;
			String sInput;
			String sMainCode;
			String sRecord, sLabel, sLovStatus, sTableRef, sIsTP;
			String sKeyRec, sCode, sDescription;
			if(!sOutput.equals(""))
			{
				sMainCode = sOutput.substring(sOutput.indexOf("<MainCode>")+10,sOutput.indexOf("</MainCode>"));
			if(sMainCode.equalsIgnoreCase("0"))
			{
					while((sOutput.indexOf("<Record>")>-1))
					{	
						sRecord = sOutput.substring(sOutput.indexOf("<Record>")+8,sOutput.indexOf("</Record>"));
						sKeyRec =sRecord.substring(sRecord.indexOf("<KEY>")+5,sRecord.indexOf("</KEY>"));
						if(sKey.equalsIgnoreCase(sKeyRec)){
							sCode =sRecord.substring(sRecord.indexOf("<CODE>")+6,sRecord.indexOf("</CODE>"));
							sDescription =sRecord.substring(sRecord.indexOf("<DESCRIPTION>")+13,sRecord.indexOf("</DESCRIPTION>"));
							ArrMaster.put(sCode, sDescription);							
						}
						sOutput=sOutput.substring(sOutput.indexOf("</Record>")+"</Record>".length());
					}
				}
			}
			return ArrMaster;
		}
		
		public String getReturnStringDataFromOutputXml(String sOutput){
			
			String sMainCode="";
			String sRecord="";
			String dateReturn="";
			String totalRetrieved="";
			if(!sOutput.equals(""))
			{
				sMainCode = sOutput.substring(sOutput.indexOf("<MainCode>")+10,sOutput.indexOf("</MainCode>"));
				totalRetrieved =sOutput.substring(sOutput.indexOf("<TotalRetrieved>")+16,sOutput.indexOf("</TotalRetrieved>"));
			}
			if(sMainCode.equalsIgnoreCase("0") && !totalRetrieved.equalsIgnoreCase("0"))
			{		
				sRecord = sOutput.substring(sOutput.indexOf("<NORMALIZEDVALUE>")+17,sOutput.indexOf("</NORMALIZEDVALUE>"));	
				WriteToLog_showpage("Y","getReturnStringDataFromOutputXml   sRecord : " +sRecord);
				SimpleDateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");	
				try {
					  Date parsedDate = inputFormatter.parse(sRecord);
					  dateReturn= outputFormatter.format(parsedDate);
				} catch (Exception e) {
					 WriteToLog_showpage("Y","Error parsing date: " + e.getMessage());
				} 			  
			}
			return dateReturn;
		}
	
		%>

						<%
								initializeLogger();
		//String sTabName =request.getParameter("TabName");
		//String sThirdParty =request.getParameter("IsThirdParty");
		String sCustReqType = request.getParameter("sReqType");
		String sCustReqCat = request.getParameter("sReqCat");
		String sGridData=request.getParameter("gridData");
		String sLastWiName =  request.getParameter("Last_WI_Name");
		String sGTEENumber = request.getParameter("GTEE_NUMBER");
		String sProductType = request.getParameter("sProdType");
		String sReqCatCode = request.getParameter("sReqCatCode");
		String sReqTypeCode = request.getParameter("sReqTypeCode"); //New FIeld
		String sWINAME = request.getParameter("WI_NAME");
		String ElcbCustInst = request.getParameter("ElcbCustInst");
		String process_type=request.getParameter("Process_type");
		WriteToLog_showpage("Y","sGridData="+sGridData);
		
		String sAmendType = request.getParameter("sAmendType");
		String sSubmittedBy = request.getParameter("sSubmittedBy");
		String sSourceChannel = request.getParameter("sSourceChannel");
		String sLoanGroup = request.getParameter("sLoanGroup");

		
		
		boolean sSkipSourceChannel =false;
		boolean sSkipSourceChannel1 =false;
		boolean sSkpSubmittedBy=false;
		boolean sSkipAmendType = false;
		boolean bSkipProductType = false;
		boolean bSkipLoanGroup = false;
		
		if("IFS".equalsIgnoreCase(sReqCatCode) || "IFP".equalsIgnoreCase(sReqCatCode)){
			if(!(sSubmittedBy.equalsIgnoreCase("Customer"))){
				sSkpSubmittedBy = true;
			}
			if(!sAmendType.equalsIgnoreCase("APTP")){
				sSkipAmendType = true;
			}
			if(!(sSourceChannel.equalsIgnoreCase("IFSE") || sSourceChannel.equalsIgnoreCase("IFSF") 
				|| sSourceChannel.equalsIgnoreCase("IFPE") || sSourceChannel.equalsIgnoreCase("IFPF"))){
				sSkipSourceChannel = true;
			}
			
		}else if("ILC".equalsIgnoreCase(sReqCatCode)){
			if("ILC_P".equalsIgnoreCase(sSourceChannel)){
				sSkipSourceChannel = true;
			}
			if(!"ILC_E".equalsIgnoreCase(sSourceChannel)){
				sSkipSourceChannel1 = true;
			}
		}else if("IC".equalsIgnoreCase(sReqCatCode)){
			if(!(sProductType.contains("I3U2") || sProductType.contains("T3U2") || sProductType.contains("T3U5") || sProductType.contains("I3U5"))){
				bSkipProductType = true;
			}
		}else if("TL".equalsIgnoreCase(sReqCatCode)){
			if(!(sLoanGroup.equalsIgnoreCase("IC"))){
				bSkipLoanGroup = true;
			}
		}
		
		
		String sTable="";
		String sInputXML ="";
		String sOutputXML="";
		String sOutputMasterXML="";
		HashMap<String, String> gridValhMap = new HashMap<String, String>();
		HashMap<String, ArrayList<HashMap<String, String>>> hmapLOV = new HashMap<String, ArrayList<HashMap<String,String>>>();
		ArrayList<ArrayList<String>> arrDocMaster = new ArrayList<ArrayList<String>>();
		String sCabname=customSession.getEngineName();
		String sSessionId = customSession.getDMSSessionId();
		String sUserName = customSession.getUserName();
		String sJtsIp = customSession.getJtsIp();
		String wd_uid=request.getParameter("session");
		String iJtsPort = String.valueOf(customSession.getJtsPort());
		ArrayList<ArrayList<String>> arrDocRvw = new ArrayList<ArrayList<String>>();
		
		ArrayList<ArrayList<String>> arrlimitCredit = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> arrMappedControls = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> arrMappedControlsExt = new ArrayList<ArrayList<String>>();
		ArrayList<String> arrFinal = new ArrayList<String>();
		ArrayList<String> arrFinalExt = new ArrayList<String>();
		ArrayList<ArrayList<String>> arrMappedControlsMerged = new ArrayList<ArrayList<String>>();
		
		try{
			if(!(sReqTypeCode.equalsIgnoreCase("NI") ||sReqTypeCode.equalsIgnoreCase("ILC_NI") ||sReqTypeCode.equalsIgnoreCase("LD")|| sReqTypeCode.equalsIgnoreCase("STL")||("IFS".equalsIgnoreCase(sReqCatCode) && sReqTypeCode.equalsIgnoreCase("AM")) || ("IFP".equalsIgnoreCase(sReqCatCode) && sReqTypeCode.equalsIgnoreCase("AM")))){

				String sInputXMLMappedColumn="",sOutputMasterXMLMappedColumn="";
				String sQueryMappedcolumn = "Select DISTINCT DocR.DOC_RVW DOC_RVW, ChkMap.mapped_col_name COLUMN_NAME from TFO_DJ_DOC_RVW_MAST DocR,TFO_DJ_CHECKLIST_MAP_MAST ChkMap where  DocR.mapped_controlname = ChkMap.CHECKLIST_ID and DocR.TAB_NAME = 'DocRvw' and DocR.req_cat_code = '"+sReqCatCode+"' and ChkMap.req_cat_code != 'EXT'"; 
				
				sInputXMLMappedColumn=prepareAPSelectInputXml(sQueryMappedcolumn,sCabname, sSessionId);
				WriteToLog_showpage("Y","sOutputXMLLmtCrdt1 For JSP  "+sInputXMLMappedColumn);
				sOutputMasterXMLMappedColumn =NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXMLMappedColumn);
				WriteToLog_showpage("Y","sOutputXMLLmtCrdt2 For JSP  "+sOutputMasterXMLMappedColumn);
				
				arrMappedControls = getGridData(sOutputMasterXMLMappedColumn,"DOC_RVW","COLUMN_NAME");
				String sFinalQuery="";
				sFinalQuery = getQuery(arrMappedControls, sGTEENumber, sReqCatCode);
				WriteToLog_showpage("Y","Final Query  "+sFinalQuery);
				
				String sQueryMappedcolumnExt = "Select DISTINCT DocR.DOC_RVW DOC_RVW, ChkMap.mapped_col_name COLUMN_NAME from TFO_DJ_DOC_RVW_MAST DocR,TFO_DJ_CHECKLIST_MAP_MAST ChkMap where  DocR.mapped_controlname = ChkMap.CHECKLIST_ID and DocR.TAB_NAME = 'DocRvw' and ChkMap.req_cat_code = 'EXT'"; 
				
				sInputXMLMappedColumn = prepareAPSelectInputXml(sQueryMappedcolumnExt,sCabname, sSessionId);
				WriteToLog_showpage("Y","sOutputXMLExt For JSP  "+sInputXMLMappedColumn);
				sOutputMasterXMLMappedColumn = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXMLMappedColumn);
				WriteToLog_showpage("Y","sOutputXMLExt2 For JSP  "+sOutputMasterXMLMappedColumn);
				
				arrMappedControlsExt = getGridData(sOutputMasterXMLMappedColumn,"DOC_RVW","COLUMN_NAME");
				String sFinalQueryExt = "";
				sFinalQueryExt = getQueryExt(arrMappedControlsExt, sWINAME);
				WriteToLog_showpage("Y","Final Query EXT  "+sFinalQueryExt);
				
				String sInputXMLFinal="",sOutputMasterXMLFinal="";
				String sInputXMLFinalExt="",sOutputMasterXMLFinalExt="";
								
				if(!(sFinalQuery.isEmpty()|| sFinalQuery.equalsIgnoreCase("--Select--") || sFinalQuery.equalsIgnoreCase(" "))){
					sInputXMLFinal = prepareAPSelectInputXml(sFinalQuery,sCabname, sSessionId);
					WriteToLog_showpage("Y","sOutputXMLLmtCrdt1 For JSP  "+sInputXMLFinal);
					sOutputMasterXMLFinal =NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXMLFinal);
					WriteToLog_showpage("Y","sFInalXML For JSP  "+sOutputMasterXMLFinal);
					arrFinal = getWMSMasterData(sOutputMasterXMLFinal,arrMappedControls);
					WriteToLog_showpage("Y","Final Array  "+arrFinal);
					if(arrFinal.size()==0){
						arrMappedControls.clear();
					}
					WriteToLog_showpage("Y","Final Array  "+arrMappedControls.size());
				}
				
				if(!(sFinalQueryExt.isEmpty()|| sFinalQueryExt.equalsIgnoreCase("--Select--") || sFinalQueryExt.equalsIgnoreCase(" "))){
					sInputXMLFinalExt = prepareAPSelectInputXml(sFinalQueryExt,sCabname, sSessionId);
					WriteToLog_showpage("Y","sOutputXMLExt For JSP  "+sInputXMLFinalExt);
					sOutputMasterXMLFinalExt =NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXMLFinalExt);
					WriteToLog_showpage("Y","sFInalXMLExt For JSP  "+sOutputMasterXMLFinalExt);
					arrFinalExt = getWMSMasterData(sOutputMasterXMLFinalExt,arrMappedControlsExt);
					WriteToLog_showpage("Y","Final Array EXT  "+arrFinalExt);
				}
				
				arrFinal.addAll(arrFinalExt);
				arrMappedControls.addAll(arrMappedControlsExt);
							
			/*
			String sInputXMLDocRvw="",sOutputMasterXMLDocRvw="";
			
			
			String sQueryLmtCrdt = "Select DOC_RVW_GDLINES, RESPONSE from TFO_DJ_DOC_RVW_RECORDS where WI_NAME = '"+sLastWiName+"'";
			WriteToLog_showpage("Y","sOutputXMLDOc RVW  For JSP  "+sQueryLmtCrdt);

			sInputXMLDocRvw = prepareAPSelectInputXml(sQueryLmtCrdt,sCabname, sSessionId);
			WriteToLog_showpage("Y","sOutputXMLDOc RVW1 For JSP  "+sInputXMLDocRvw);
			sOutputMasterXMLDocRvw =NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXMLDocRvw);
			WriteToLog_showpage("Y","sOutputXMLDOc RVW2  For JSP  "+sOutputMasterXMLDocRvw);
			arrDocRvw = getGridData(sOutputMasterXMLDocRvw,"DOC_RVW_GDLINES","RESPONSE");
			WriteToLog_showpage("Y","sOutputXMLDOc RVW3  For JSP ArrayList  "+arrDocRvw);
			*/
		}
			
		}catch(Exception e){
			
			
		}
		
		String returnStr="";
		String aryCurr[]=null;
		String sQuery1="Select KEY, CODE,DESCRIPTION FROM TFO_DJ_LCP_MAST where req_cat_code ='"+sReqCatCode+"' UNION Select KEY, CODE,DESCRIPTION FROM TFO_DJ_BILLTYPE_MAST UNION Select KEY, CODE,DESCRIPTION FROM TFO_DJ_CUST_INSTR_MAST UNION Select KEY, CODE,DESCRIPTION FROM TFO_DJ_ALLOWED_MAST UNION Select KEY, CODE,DESCRIPTION FROM TFO_DJ_YES_NO_MAST UNION Select KEY, CODE,DESCRIPTION FROM TFO_DJ_YES_NO_NAP UNION Select KEY, CODE,DESCRIPTION FROM TFO_DJ_TENOR_TYPE where req_cat_code ='"+sReqCatCode+"' UNION Select KEY, CODE,DESCRIPTION FROM TFO_DJ_DRAFT_TYPE where req_cat_code ='"+sReqCatCode+"' UNION Select KEY, CODE,DESCRIPTION FROM TFO_DJ_YESNO_NA_CH UNION Select KEY, CODE,DESCRIPTION FROM TFO_DJ_YES_NA_MAST UNION Select KEY, CODE,DESCRIPTION FROM TFO_DJ_RELATED_PARTIES UNION Select KEY, CODE,DESCRIPTION FROM TFO_DJ_RELATED_PARTIES_NA UNION Select KEY, CODE,DESCRIPTION FROM TFO_DJ_GT_MOD_MAST UNION Select KEY, CODE,DESCRIPTION FROM TFO_DJ_TBML_MAST UNION Select KEY, CODE,DESCRIPTION FROM TFO_DJ_TBML_RED_MAST UNION Select KEY, CODE,DESCRIPTION FROM TFO_DJ_TBML_PC_MAST UNION Select KEY, CCY_CODE CODE,CCY_CODE||'-'||CCY_NAME DESCRIPTION from tfo_dj_curr_decimals UNION Select KEY, CODE,DESCRIPTION FROM TFO_DJ_LC_APPERING_MAST where req_cat_code ='"+sReqCatCode+"' UNION Select KEY, CODE,DESCRIPTION FROM TFO_DJ_DOC_RECEIVED_MAST where req_cat_code ='"+sReqCatCode+"' UNION Select KEY, CODE,DESCRIPTION FROM TFO_DJ_DOC_TRASPORT_INVOLVED_MAST where req_cat_code ='"+sReqCatCode+"'  ORDER BY DESCRIPTION";

		sInputXML = prepareAPSelectInputXml(sQuery1,sCabname, sSessionId);

		if(!sInputXML.isEmpty())
			sOutputMasterXML =NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXML);
		WriteToLog_showpage("Y","sOutputXML11 For JSP  "+sOutputMasterXML);
		
		String sQuery="SELECT REQ_CAT, REQ_TYPE,MAPPED_CONTROLNAME, DOC_RVW AS LABEL,CNTRL_TYPE AS LOV_STATUS,DOC_RVW_KEY AS TABLE_REF, TAB_NAME, IS_TP AS IS_THIRD_PARTY, DECODE(VAR_LENGTH, null, 0, VAR_LENGTH) AS VAR_LENGTH, DEFAULTVALUE, ENABLEDSTATUS FROM tfo_dj_doc_rvw_mast where TAB_NAME = 'DocRvw' and REQ_TYPE_CODE = '"+sReqTypeCode+"' and REQ_CAT_CODE='"+sReqCatCode+"'  order by SNO";
		WriteToLog_showpage("Y","sQuery   "+sQuery);
		sInputXML = prepareAPSelectInputXml(sQuery,sCabname, sSessionId);
		if(!sInputXML.isEmpty())
			sOutputXML =NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXML);
		WriteToLog_showpage("Y","sOutputXML   "+sOutputXML);
		if(!sOutputXML.isEmpty())
			arrDocMaster = getMasterDocumentTable(sOutputXML);
		
	
		//out.println("sOutputXML For JSP  "+arrDocMaster);
		//out.println("sGridData For JSP  "+sGridData);
		if(!(sGridData.isEmpty() || sGridData.equalsIgnoreCase(" "))){
			if(sGridData.contains("AmpersandSign"))
				sGridData = sGridData.replace("AmpersandSign","&");
			if(sGridData.contains("PercentSign"))
				sGridData = sGridData.replace("PercentSign","%");
			
			gridValhMap=getDocGridData(sGridData);				
		}else{
			//gridValhMap=arrListToHashMap(arrDocRvw);
			WriteToLog_showpage("Y","EMPTY CASE   ");
			if("ILCB_BL".equalsIgnoreCase(sCustReqType) || "ELCB_BL".equalsIgnoreCase(sCustReqType)){
				sQuery="SELECT NORMALIZEDVALUE FROM TFO_DJ_TS_ATTRIBUTES_DATA    WHERE WI_NAME ='"+sWINAME+"' and doc_code in ('BL','DN','AWB') and Atribute_key in ('ON_BRD_DT','SHP_DT','DE_DT','ISS_DT') and Verified ='True'";
			   sInputXML = prepareAPSelectInputXml(sQuery,sCabname, sSessionId);
		       if(!sInputXML.isEmpty()){
			      sOutputXML =NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXML);
		          WriteToLog_showpage("Y","sOutputXML   "+sOutputXML);
			   }
				WriteToLog_showpage("Y"," Reyaz ");
				arrFinal.add(getReturnStringDataFromOutputXml(sOutputXML));
				ArrayList<String> ArrIntenal1 = new ArrayList<String>();
				ArrIntenal1.add("Shipment Date");
				ArrIntenal1.add("ILCB_SHIPMENT_DATE");
				arrMappedControls.add(ArrIntenal1);
				
				sQuery="SELECT NORMALIZEDVALUE FROM TFO_DJ_TS_ATTRIBUTES_DATA    WHERE WI_NAME ='"+sWINAME+"' and Atribute_key ='presentationDate'";
			    sInputXML = prepareAPSelectInputXml(sQuery,sCabname, sSessionId);
			    if(!sInputXML.isEmpty()){
			      sOutputXML =NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXML);
		          WriteToLog_showpage("Y","sOutputXML   "+sOutputXML);
			    }
				WriteToLog_showpage("Y"," Reyaz ");
				arrFinal.add(getReturnStringDataFromOutputXml(sOutputXML));
				ArrayList<String> ArrIntenal2 = new ArrayList<String>();
				if("ILCB_BL".equalsIgnoreCase(sCustReqType)){
					ArrIntenal2.add("Date of Presentation");
				    ArrIntenal2.add("ILCB_DATE_PRES");
				    arrMappedControls.add(ArrIntenal2);
				} else if("ELCB_BL".equalsIgnoreCase(sCustReqType)){
					ArrIntenal2.add("Date of Presentation");
				    ArrIntenal2.add("ELCB_Date_of_prsnt");
				    arrMappedControls.add(ArrIntenal2);
				}	
			}
			WriteToLog_showpage("Y","ArrayMapName  "+arrMappedControls);
		   WriteToLog_showpage("Y","ArrayFinal "+arrFinal);
			gridValhMap=getFinalWMSMap(arrFinal,arrMappedControls);	
		}
		//out.println("gridValhMap For JSP  "+gridValhMap);
		String selString="";
		for(int cntr=0;cntr<arrDocMaster.size();cntr++){
			HashMap<String, String> hmaTemp;
			String sOutput="";
			String sSelecedvalue ="";
			String sControlID="";
			String sCntrlLength="";
			String sDefaultvalue = "";
			String sEnabledStatus="";
			//String sDisable=""; //String not overridden
			ArrayList<HashMap<String, String>> arrHmapTR;
			String sLabel="",sCODE="",sLOVStatus="",sMasterRef="",sIsThirdPatry="";
			sLabel = arrDocMaster.get(cntr).get(0);
			sLOVStatus =arrDocMaster.get(cntr).get(1);
			sMasterRef = arrDocMaster.get(cntr).get(2);
			sIsThirdPatry = arrDocMaster.get(cntr).get(3);
			sControlID = arrDocMaster.get(cntr).get(4);
			sCntrlLength = arrDocMaster.get(cntr).get(5);
			sDefaultvalue = arrDocMaster.get(cntr).get(6);
			sEnabledStatus = arrDocMaster.get(cntr).get(7);
			
			WriteToLog_showpage("Y","sLabel "+sLabel);
			WriteToLog_showpage("Y","sControlID "+sControlID);
			String name="";
			if(!sEnabledStatus.isEmpty()){
				if(sEnabledStatus.equalsIgnoreCase("N"))
					sEnabledStatus="disabled";
			}
			
			if(process_type.equalsIgnoreCase("TSLM Front End")){
				if(sControlID.equalsIgnoreCase("LnAppDocSubStatus"))
					continue;
			}
			if(process_type.equalsIgnoreCase("TSLM Front End") && sReqTypeCode.equalsIgnoreCase("IFA_CTP")){
				if(sControlID.equalsIgnoreCase("LnAppDocDtStatus"))
					continue;
			}
			
			if(sControlID.equalsIgnoreCase("FaxEmailStatus")){
				continue;
			}
			//Added for removing checklist from document tab for STL
			if(process_type.equalsIgnoreCase("TSLM Front End") && sReqTypeCode.equalsIgnoreCase("STL")){
				if(sControlID.equalsIgnoreCase("CustReqletterStatus"))
					continue;
			}
			
			if(sSkipSourceChannel){
				if(sControlID.equalsIgnoreCase("FaxEmailStatus")||sControlID.equalsIgnoreCase("ILC_PRtrd_Cust"))
					continue;
			}
			if(sSkipSourceChannel1){
				if(sControlID.equalsIgnoreCase("ILC_Cust_Reg_Email"))
					continue;
			}
			if(sSkpSubmittedBy){
				if(sControlID.equalsIgnoreCase("FaxEmailStatus")||sControlID.equalsIgnoreCase("CustReqletterStatus"))
					continue;
			}
			if(sSkipAmendType){
				if(sControlID.equalsIgnoreCase("CustInvoiceCustReqStatus")||sControlID.equalsIgnoreCase("TransDocCommInvoiceStatus"))
					continue;				
			}
			if(bSkipProductType){
				if(sControlID.equalsIgnoreCase("IC_Is_Drft_Drwee")||sControlID.equalsIgnoreCase("IC_Is_Drwee_Bill"))
					continue;				
			}
			if(bSkipLoanGroup){
				if(sControlID.equalsIgnoreCase("TL_Is_DAN_Signed")||sControlID.equalsIgnoreCase("TL_Is_Doc_Submit")
					||sControlID.equalsIgnoreCase("TL_Is_Details_match")||sControlID.equalsIgnoreCase("TL_Is_Details_Corrct")
					||sControlID.equalsIgnoreCase("TL_Is_No_Waive_Chrgs"))
					continue;				
			}
			
			
			
			if("No".equalsIgnoreCase(sIsThirdPatry)){
			%>
						<TR>
						<TD width=600px
							style="FONT-FAMILY: Calibri; FONT-WEIGHT: normal; font-size: 10pt"><%=sLabel%></TD>
						<%
				if("LOV".equalsIgnoreCase(sLOVStatus)){
					name = "sLOV"+cntr;
				%>
						<TD width=170px><Select name=<%=sControlID%> id=<%=sControlID%> onchange="onChange(this.value)" style="width: 170px">
								<%
						arrHmapTR = new ArrayList<HashMap<String, String>>(); 
						hmaTemp = new HashMap<String, String>();
								
						if(!hmapLOV.isEmpty())		
							arrHmapTR = hmapLOV.get(sMasterRef);
						if(!gridValhMap.isEmpty())
							if(gridValhMap.containsKey(sLabel))
								if(!gridValhMap.get(sLabel).isEmpty())
									sSelecedvalue = gridValhMap.get(sLabel);
						
						hmaTemp = getMasterDoHashMap(sMasterRef, sOutputMasterXML);
						
						WriteToLog_showpage("Y","getMasterDoHashMap For JSP  "+hmaTemp);
						WriteToLog_showpage("Y","gridValhMap For JSP  "+gridValhMap);
						
						int i = 1;
						
						for(String sCode1: hmaTemp.keySet()){
							WriteToLog_showpage("Y","sCode1= "+sCode1);
							WriteToLog_showpage("Y","sSelecedvalue= "+sSelecedvalue);
							WriteToLog_showpage("Y","sDefaultvalue= "+sDefaultvalue);
							if(hmaTemp.get(sCode1).equalsIgnoreCase(sSelecedvalue) || hmaTemp.get(sCode1).equalsIgnoreCase(sDefaultvalue) ){
								selString = "selected";		
								%>
								<option <%=selString%> value="<%=hmaTemp.get(sCode1)%>"><%=hmaTemp.get(sCode1)%></option>
								<%
								selString="";
								continue;
							}
															
							if(i==1){
								%>
								<option value="--Select--">--Select--</option>
								<option  value="<%=hmaTemp.get(sCode1)%>"><%=hmaTemp.get(sCode1)%></option>
								<%
							}else{
							%>
								<option value="<%=hmaTemp.get(sCode1)%>"><%=hmaTemp.get(sCode1)%></option>
								<%
								}
							i+=1;
							}
						
						%>
						</Select></TD>
						<%
						
				}else if("NumericTextbox".equalsIgnoreCase(sLOVStatus)){
					WriteToLog_showpage("Y","Final EnableStatus value "+sEnabledStatus);
					String sDisable=""; 
					if(sEnabledStatus.equalsIgnoreCase("N"))
						sDisable="disabled";
					WriteToLog_showpage("Y","Final DISABLE value "+sDisable);
					if(!gridValhMap.isEmpty() && gridValhMap.containsKey(sLabel) && !gridValhMap.get(sLabel).isEmpty()){
						String sExistingVal="";
						WriteToLog_showpage("Y","GridValue "+gridValhMap.get(sLabel));
						sExistingVal = gridValhMap.get(sLabel).replaceAll("\"","&quot;");
						WriteToLog_showpage("Y","sExistingVal "+gridValhMap.get(sLabel));
						WriteToLog_showpage("Y","sLabel "+sLabel);
						if("ILCB_PRSNT_DOC_AMOUNT".equalsIgnoreCase(sControlID) || "ELCB_Doc_Amnt".equalsIgnoreCase(sControlID)){
							sExistingVal = sExistingVal.replaceAll(",","");
						}
				%>
						<TD><input type="text"  maxlength=<%=sCntrlLength%> name=<%=sControlID%> id=<%=sControlID%> value="<%=sExistingVal%>"
							 style="width: 170px" onfocusout="checkNumericValidation(this.value,this.id)" ></TD>
							<%
						}else{
							%>
							
						<TD><input type="text"  maxlength=<%=sCntrlLength%> name=<%=sControlID%> id=<%=sControlID%> value="<%=sDefaultvalue%>"   style="width: 170px" onfocusout="checkNumericValidation(this.value,this.id)" ></TD>
						<%
						}
					
					
				}else if("DatePicker".equalsIgnoreCase(sLOVStatus)){
					if(!gridValhMap.isEmpty() && gridValhMap.containsKey(sLabel) && !gridValhMap.get(sLabel).isEmpty()){
						String sExistingVal="";
						WriteToLog_showpage("Y","GridValue "+gridValhMap.get(sLabel));
						sExistingVal = gridValhMap.get(sLabel).replaceAll("\"","&quot;");
				%>
						<TD><input type="text"  maxlength="10" name=<%=sControlID%> id=<%=sControlID%> value="<%=sExistingVal%>"
							style="width: 170px" onfocusin="applyDatePickerBox(this.id)" onchange="dateCalculation(this.id)" onkeydown="handleTabKey(event, this)" ></TD>
							<%
						}else{
							%>
							
						<TD><input type="text"  maxlength="10" name=<%=sControlID%> id=<%=sControlID%> style="width: 170px" onfocusin="applyDatePickerBox(this.id)" onchange="dateCalculation(this.id)" onkeydown="handleTabKey(event, this)" ></TD>
						<%
						}
					
					
				}
				else{							
					name="sTextBox"+cntr;
					WriteToLog_showpage("Y","Final EnableStatus value "+sEnabledStatus);
					String sDisable=""; 
					WriteToLog_showpage("Y","Final DISABLE value "+sDisable);
					if(sEnabledStatus.equalsIgnoreCase("disabled")){
						if(!gridValhMap.isEmpty() && gridValhMap.containsKey(sLabel) && !gridValhMap.get(sLabel).isEmpty()){
							WriteToLog_showpage("Y","Final DISABLE value "+sDisable);
						String sExistingVal="";
						sExistingVal = gridValhMap.get(sLabel).replaceAll("\"","&quot;");						
				%>
						<TD width=110px  ><input type="text" maxlength=<%=sCntrlLength%> name=<%=sControlID%> id=<%=sControlID%> value="<%=sExistingVal%>"
							  style="width: 170px" disabled></TD>
							<%
						}else{
							%>
							
						<TD width=110px  ><input type="text" maxlength=<%=sCntrlLength%> name=<%=sControlID%> id=<%=sControlID%> value="<%=sDefaultvalue%>"
							  style="width: 170px" disabled></TD>
						<%
						}
						}else{
						if(!gridValhMap.isEmpty() && gridValhMap.containsKey(sLabel) && !gridValhMap.get(sLabel).isEmpty()){
						String sExistingVal="";
						sExistingVal = gridValhMap.get(sLabel).replaceAll("\"","&quot;");						
				%>
						<TD width=110px ><input type="text" maxlength=<%=sCntrlLength%> name=<%=sControlID%> id=<%=sControlID%> value="<%=sExistingVal%>"
							  style="width: 170px" ></TD>
							<%
						}else{
							%>
							
						<TD width=110px ><input type="text" maxlength=<%=sCntrlLength%> name=<%=sControlID%> id=<%=sControlID%> value="<%=sDefaultvalue%>"
							  style="width: 170px" ></TD>
						<%
						}
						
					}
					
				}
			}
			
		}
			
		%>
					</TR>
					<br>
					<link href="css/jquery-ui.min.css" rel="stylesheet" type="text/css" />
					<script src="Scripts/jquery.min.js"></script>
					<script src="Scripts/jquery-ui.min.js"></script>
					<script>
						//window.onload=onChange("Demo");						
						var myclose = false;						
						function ConfirmClose()
						{
							if (event.clientY < 0)
							{
								event.returnValue = 'Unsave data will be lost';
								//setTimeout('myclose=false',10);
								myclose=true;
							}
						}
						
						function applyDatePickerBox(sControlName){
							var sCntrlName = "#"+sControlName;
							$(sCntrlName).datepicker({dateFormat: 'dd/mm/yy'});
						}
						
						function importData(){
							var sSelectedValue="";
							var sColString="";
							var ID="";
							var cols=document.getElementById('ReferToTable').getElementsByTagName('td'),colsLen=cols.length,i=-1;
							while(++i<colsLen){
									if(i%2==0){
										//alert("Content "+cols[i].innerHTML);
										if(i==0){
											sColString+=cols[i].innerHTML+"#";
										}
										else{
											sColString+=cols[i].innerHTML+"#";
										}
										sColString=sColString.replace("&amp;","&");
									}
									else{
										var sCellString=cols[i].innerHTML;
										var lengthTag = 0;
										lengthTag = sCellString.indexOf("id=");
										var tagnameStr=sCellString.substring(lengthTag);
										ID=tagnameStr.substring(4, tagnameStr.indexOf(" ")-1);
										var sTemp;
										sTemp = document.getElementById(ID).value
										//alert(ID);
										
										//alert(sTemp);
										if(sCellString.indexOf("<select")>-1){
											sTemp=document.getElementById(ID).options[document.getElementById(ID).selectedIndex].text;

										}		//alert(sTemp);
											if(sTemp == "--Select--"||sTemp == ""){
												sTemp=" ";
											sTemp=sTemp.replace("&amp;","&");
											sTemp=sTemp.replace("&quot;","\"");
										}
										
										//alert("CellString sTemp "+sTemp);
										
										var sControl ="";
										if(ID.indexOf("sLOV")>-1)
											sControl="select";
										else
											sControl="enter";
										
											if(sTemp =="" || sTemp == " "||sTemp =="--Select--")
											{
												if(sCellString.indexOf("disabled") < 0){
													alert("Please "+sControl+" mandatory details ");
													document.getElementById(ID).focus();
													return false;
												}
												else
													sColString+=sTemp+"##";
											}else if(sTemp.indexOf('#')>-1 || sTemp.indexOf('~')>-1){
												alert("'#' and '~' are not allowed");
												document.getElementById(ID).focus();
												return false;												
											}
											else{
												sColString+=sTemp+"##";
											}
										
									}										
								}
									/*alert(sColString);*/
									//sColString = sColString.replace("&amp;","&");
									//alert(sColString); 
									window.returnValue=sColString;
									//window.opener.getJSPDocData(sColString);
									//window.parent.handleJSPResponse('DocRvw',"sColString");
									window.parent.handleJSPResponsePPM('DocRvw',sColString);
									window.close();							
						}						
						
						function dateCalculation(ControlName){
					    var valueDate=document.getElementById(ControlName).value;
						var date_regex=/^(0[1-9]|1\d|2\d|3[01])\/(0[1-9]|1[0-2])\/(19|20)\d{2}$/;
						if(!(date_regex.test(valueDate))){
							alert('Please enter correct date dd/mm/yyyy');
							document.getElementById(ControlName).value="";
						}
							if("ILCB_SHIPMENT_DATE"==ControlName || "ILCB_LAST_DATE_SHPMNT"==ControlName){
								//dateDifference(dtInitialDate,dtFinalDate,"ILCB_DIFF_SHIPMENT_DATE");
								if(!(document.getElementById("ILCB_SHIPMENT_DATE").value==""||document.getElementById("ILCB_LAST_DATE_SHPMNT").value==""))
									document.getElementById("ILCB_DIFF_SHIPMENT_DATE").value = dateDiff(stringTodate(document.getElementById("ILCB_SHIPMENT_DATE").value), stringTodate(document.getElementById("ILCB_LAST_DATE_SHPMNT").value));
								else
									document.getElementById("ILCB_DIFF_SHIPMENT_DATE").value = "0";
							}
							if("ILCB_DATE_PRES"==ControlName || "ILCB_LC_EXPIRY"==ControlName){
								if(!(document.getElementById("ILCB_DATE_PRES").value==""||document.getElementById("ILCB_LC_EXPIRY").value==""))
									document.getElementById("ILCB_DIFF_LC_EXPIRY_DATE").value = dateDiff(stringTodate(document.getElementById("ILCB_DATE_PRES").value), stringTodate(document.getElementById("ILCB_LC_EXPIRY").value));
								else
									document.getElementById("ILCB_DIFF_LC_EXPIRY_DATE").value = "0";
								//dateDifference(dtInitialDate,dtFinalDate,"ILCB_DIFF_LC_EXPIRY_DATE");
								//document.getElementById(sResultControl).value
							} 
							try{
							if("ILCB_SHIPMENT_DATE"==ControlName || "ILCB_DATE_PRES"==ControlName){
								if(!(document.getElementById("ILCB_SHIPMENT_DATE").value==""||document.getElementById("ILCB_DATE_PRES").value==""))
									getDifference(document.getElementById("ILCB_PRES_TIME_PERIOD").value, dateDiff(stringTodate(document.getElementById("ILCB_SHIPMENT_DATE").value), stringTodate(document.getElementById("ILCB_DATE_PRES").value)), "ILCB_DIFF_PRES");
								else
									document.getElementById("ILCB_DIFF_PRES").value = "0";
							}
							}catch(e){};
							
							
							if("ELCB_Shipmnt_date"==ControlName || "ELCB_Last_Date_Of_Shpmnt"==ControlName){
								if(!(document.getElementById("ELCB_Shipmnt_date").value==""||document.getElementById("ELCB_Last_Date_Of_Shpmnt").value==""))
									document.getElementById("ELCB_Last_Ship_Date_Diff").value = dateDiff(stringTodate(document.getElementById("ELCB_Shipmnt_date").value), stringTodate(document.getElementById("ELCB_Last_Date_Of_Shpmnt").value));
								else
									document.getElementById("ELCB_Last_Ship_Date_Diff").value = "0";
							}
							if("ELCB_Date_of_prsnt"==ControlName || "ELCB_LC_Expiry"==ControlName){
								if(!(document.getElementById("ELCB_Date_of_prsnt").value==""||document.getElementById("ELCB_LC_Expiry").value==""))
									document.getElementById("ELCB_Expr_Date_Diff").value = dateDiff(stringTodate(document.getElementById("ELCB_Date_of_prsnt").value), stringTodate(document.getElementById("ELCB_LC_Expiry").value));
								else
									document.getElementById("ELCB_Expr_Date_Diff").value = "0";
							} 
							if("ELCB_Shipmnt_date"==ControlName || "ELCB_Date_of_prsnt"==ControlName){
								if(!(document.getElementById("ELCB_Shipmnt_date").value==""||document.getElementById("ELCB_Date_of_prsnt").value==""))
									getDifference(document.getElementById("ELCB_Prsntn_Per").value, dateDiff(stringTodate(document.getElementById("ELCB_Shipmnt_date").value), stringTodate(document.getElementById("ELCB_Date_of_prsnt").value)), "ELCB_Prsntn_time_per");
								else
									document.getElementById("ELCB_Prsntn_time_per").value = "0";
							}
						}						
						
						function getDifference(dFinalValue, dInitialValue, sControlName){
							if(!(dInitialValue=="" || dFinalValue=="")){
								var fInitVal = parseFloat(dInitialValue);
								var fFinalVal = parseFloat(dFinalValue);
								document.getElementById(sControlName).value = fFinalVal - fInitVal;
								
							}							
						}
						
						function checkNumericValidation(ControlValue, ControlName){
							try{
								var value1 = ControlValue;
								var digit=parseFloat(value1);
								if(!(ControlValue=="")){
									if(isNaN(digit)){
										alert("Not a valid number");
										document.getElementById(ControlName).value="";
									}
									else if(value1.match(/^\d+(?:\.\d{0,3})?$/)){
										document.getElementById(ControlName).value = digit;//.toFixed(3);
									}else{
										document.getElementById(ControlName).value = '';
										alert("precision limit exceeded more than 3");
									}
								}
							}
							catch(e){
								
							}
							
							if("ILCB_LC_AMOUNT"==ControlName || "ILCB_TOLERANCE_PERC"==ControlName){
								calculateToleranceAmount(document.getElementById("ILCB_LC_AMOUNT").value,document.getElementById("ILCB_TOLERANCE_PERC").value,"ILCB_TOLERANCE_AMOUNT");
							} 
							if("ILCB_LC_AMOUNT"==ControlName || "ILCB_TOLERANCE_AMOUNT"==ControlName
								||"ILCB_TOT_DOC_RCVD"==ControlName || "ILCB_PRSNT_DOC_AMOUNT"==ControlName){
								claculateAvailablebalance(document.getElementById("ILCB_LC_AMOUNT").value,document.getElementById("ILCB_TOLERANCE_AMOUNT").value,document.getElementById("ILCB_TOT_DOC_RCVD").value,document.getElementById("ILCB_PRSNT_DOC_AMOUNT").value,"ILCB_AVAILABLE_BAL");
								
							}
							if("ILCB_PRES_TIME_PERIOD"==ControlName){
								getDifference(document.getElementById("ILCB_PRES_TIME_PERIOD").value, dateDiff(stringTodate(document.getElementById("ILCB_SHIPMENT_DATE").value), stringTodate(document.getElementById("ILCB_DATE_PRES").value)), "ILCB_DIFF_PRES");
							}
							
							if("ELCB_LC_Amnt"==ControlName || "ELCB_Allwd_Tolrnce"==ControlName){
								calculateToleranceAmount(document.getElementById("ELCB_LC_Amnt").value,document.getElementById("ELCB_Allwd_Tolrnce").value,"ELCB_Tlrnc_Amnt");
							} 
							if("ELCB_LC_Amnt"==ControlName || "ELCB_Tlrnc_Amnt"==ControlName
								||"ELCB_Doc_Rcvd"==ControlName || "ELCB_Doc_Amnt"==ControlName){
								claculateAvailablebalance(document.getElementById("ELCB_LC_Amnt").value,document.getElementById("ELCB_Tlrnc_Amnt").value,document.getElementById("ELCB_Doc_Rcvd").value,document.getElementById("ELCB_Doc_Amnt").value,"ELCB_Avl_Bal");
								
							}
							if( "ELCB_Prsntn_Per"==ControlName){
								document.getElementById("ELCB_Prsntn_time_per").value = getDifference(document.getElementById("ELCB_Prsntn_Per").value, dateDiff(stringTodate(document.getElementById("ELCB_Shipmnt_date").value), stringTodate(document.getElementById("ELCB_Date_of_prsnt").value)), "ELCB_Prsntn_time_per");
							}
						}
						
						
						
						function calculateToleranceAmount(dLCAmount,dAlowedTolerance, sResultControl){
								var LCAmount,Allowetolerance;
								if(!(dLCAmount=="" || dAlowedTolerance=="")){
									LCAmount = parseFloat(dLCAmount);
									Allowetolerance=parseFloat(dAlowedTolerance)/100;
									document.getElementById(sResultControl).value = (LCAmount*Allowetolerance).toFixed(3);
								}
							
						}
						function claculateAvailablebalance(dLCAmount,dToleranceAmount,dTotalDocReceivd,dPresentDocAmount,sResultControl){
								var LCAmount, ToleranceAmount, totalDocRecieved, PresentDocAmnt;
								if(!(dLCAmount=="" || dToleranceAmount==""||dTotalDocReceivd==""||dPresentDocAmount=="")){
									LCAmount = parseFloat(dLCAmount);
									ToleranceAmount=parseFloat(dToleranceAmount);
									totalDocRecieved=parseFloat(dTotalDocReceivd);
									PresentDocAmnt=parseFloat(dPresentDocAmount);
									document.getElementById(sResultControl).value = ((LCAmount + ToleranceAmount)-(totalDocRecieved + PresentDocAmnt)).toFixed(3);
								}
							
						}
						function stringTodate(sDate){
							
								var dateString = sDate;
								var dataSplit = dateString.split('/');
								var dateConverted;
							
								if (dataSplit[2].split(" ").length > 1) {	
									var hora = dataSplit[2].split(" ")[1].split(':');
									dataSplit[2] = dataSplit[2].split(" ")[0];
									dateConverted = new Date(dataSplit[2], dataSplit[1], dataSplit[0] - 1, hora[0], hora[1]);
								} else {
									dateConverted = new Date(dataSplit[2], dataSplit[1], dataSplit[0] - 1);
								}
								return dateConverted;
							
						}
						
						function handleTabKey(event, inputElement) {
						 // Check if the pressed key is the Tab key (keyCode 9)
							if (event.key === "Tab") {
								// Optionally trigger your onchange logic or date calculation
								dateCalculation(inputElement.id);
							}
						}
						
						function dateDiff(initialDate, finalDate) {
							
								var one_day=1000*60*60*24;
								var initialDateInMS = initialDate.getTime();
								var finalDateInMS = finalDate.getTime();
								var differenceInMS = finalDateInMS - initialDateInMS;
								return Math.round(differenceInMS/one_day); 
							
						}
						
						
						
						function onChange(value){
							var cols=document.getElementById('ReferToTable').getElementsByTagName('td'),colsLen=cols.length
							if(colsLen == 0){
								alert("No Checklist found for this request");
								//window.opener.getJSPDocData("Empty");
								window.parent.handleJSPResponsePPM('DocRvw','Empty');
								window.close();	
							}
							try{
							if(!(document.getElementById("Mode_Of_Gtee").value == "Counter Guarantee" 
							|| document.getElementById("Mode_Of_Gtee").value == "Guarantee Advised Thru Other Bank")){
								document.getElementById("Is_FI_Trade").value = "--Select--";
								document.getElementById("Swift_Code_GTEE").value = "";
								document.getElementById("Is_FI_Trade").disabled = true;
								document.getElementById("Swift_Code_GTEE").disabled = true;
								
							}
							else{
								document.getElementById("Is_FI_Trade").disabled = false;
								document.getElementById("Swift_Code_GTEE").disabled = false;
							}
							}catch(e){}
							try{
							if(!(document.getElementById("ILC_Ins_Cvrd_By").value == "Applicant")){
								document.getElementById("ILC_Ins_Policy").value = "--Select--";
								document.getElementById("ILC_Ins_Policy").disabled = true;						
							}
							else{
								
								document.getElementById("ILC_Ins_Policy").disabled = false;	

							}
							}catch(e){}
							try{
							if(!(document.getElementById("ILC_Ins_Cvrd_By").value == "Beneficiary")){
								document.getElementById("ILC_Ins_Clause").value = "--Select--";
								document.getElementById("ILC_Ins_Clause").disabled = true;						
							}
							else{
								document.getElementById("ILC_Ins_Clause").disabled = false;	

							}
							}catch(e){}
							try{
							if(!(document.getElementById("IC_Is_Drft_Drwee").value == "No")){
								document.getElementById("IC_Is_Drwee_Bill").value = "--Select--";
								document.getElementById("IC_Is_Drwee_Bill").disabled = true;						
							}
							else{
								document.getElementById("IC_Is_Drwee_Bill").disabled = false;	

							}
							}catch(e){}
							try{
							if(!(document.getElementById("IC_Is_DAN_Draft_Prsntd").options[document.getElementById("IC_Is_DAN_Draft_Prsntd").selectedIndex].text == "DAN & WRITTEN UNDERTAKING")){
								document.getElementById("IC_Is_Agrreable").value = "--Select--";
								document.getElementById("IC_Is_Agrreable").disabled = true;						
							}
							else{
								document.getElementById("IC_Is_Agrreable").disabled = false;	

							}
							}catch(e){}
							try{
							if(!(document.getElementById("ELC_Cnfm_along_advising").value == "Yes")){
								document.getElementById("ELC_ADCB_Auth_add_cnfm").value = "--Select--";
								document.getElementById("ELC_ADCB_Auth_add_cnfm").disabled = true;
								document.getElementById("ELC_Cnfm_changes_borne_by").value = "--Select--";
								document.getElementById("ELC_Cnfm_changes_borne_by").disabled = true;
								document.getElementById("ELC_plc_expiry_UAE").value = "--Select--";
								document.getElementById("ELC_plc_expiry_UAE").disabled = true;
								document.getElementById("ELC_LC_Avl_at_counters").value = "--Select--";
								document.getElementById("ELC_LC_Avl_at_counters").disabled = true;
								
								document.getElementById("ELC_TT_Reimb").value = "--Select--";
								document.getElementById("ELC_TT_Reimb").disabled = true;
								document.getElementById("ELC_Goods_desc_amt_calc").value = "--Select--";
								document.getElementById("ELC_Goods_desc_amt_calc").disabled = true;
								document.getElementById("ELC_Ori_goods_Certi_for_LC").value = "--Select--";
								document.getElementById("ELC_Ori_goods_Certi_for_LC").disabled = true;
								document.getElementById("ELC_Orig_LC_Amend").value = "--Select--";
								document.getElementById("ELC_Orig_LC_Amend").disabled = true;
								
								document.getElementById("ELC_Non_Cust_sig_veri_msg").value = "--Select--";
								document.getElementById("ELC_Non_Cust_sig_veri_msg").disabled = true;
								document.getElementById("ELC_Rvw_LC_Amend_Cnfm").value = "--Select--";
								document.getElementById("ELC_Rvw_LC_Amend_Cnfm").disabled = true;
								document.getElementById("ELC_Msg_Issu_Bank").value = "--Select--";
								document.getElementById("ELC_Msg_Issu_Bank").disabled = true;
								
							}
							else{
								document.getElementById("ELC_ADCB_Auth_add_cnfm").disabled = false;	
								document.getElementById("ELC_Cnfm_changes_borne_by").disabled = false;
								document.getElementById("ELC_plc_expiry_UAE").disabled = false;	
								document.getElementById("ELC_LC_Avl_at_counters").disabled = false;
								document.getElementById("ELC_TT_Reimb").disabled = false;	
								document.getElementById("ELC_Goods_desc_amt_calc").disabled = false;
								document.getElementById("ELC_Ori_goods_Certi_for_LC").disabled = false;	
								document.getElementById("ELC_Orig_LC_Amend").disabled = false;
								document.getElementById("ELC_Non_Cust_sig_veri_msg").disabled = false;
								document.getElementById("ELC_Rvw_LC_Amend_Cnfm").disabled = false;	
								document.getElementById("ELC_Msg_Issu_Bank").disabled = false;


							}
							}catch(e){}
							
							try{
							if(!(document.getElementById("ELC_Cnfm_along_advising").value == "Yes")){
								document.getElementById("ELC_ADCB_Auth_add_cnfm").value = "--Select--";
								document.getElementById("ELC_ADCB_Auth_add_cnfm").disabled = true;
								document.getElementById("ELC_Cnfm_changes_borne_by").value = "--Select--";
								document.getElementById("ELC_Cnfm_changes_borne_by").disabled = true;
								//document.getElementById("ELC_plc_expiry_UAE").value = "--Select--";
								//document.getElementById("ELC_plc_expiry_UAE").disabled = true;
								document.getElementById("ELC_LC_Avl_at_counters").value = "--Select--";
								document.getElementById("ELC_LC_Avl_at_counters").disabled = true;
								
								document.getElementById("ELC_TT_Reimb").value = "--Select--";
								document.getElementById("ELC_TT_Reimb").disabled = true;
								document.getElementById("ELC_Goods_desc_amt_calc").value = "--Select--";
								document.getElementById("ELC_Goods_desc_amt_calc").disabled = true;
								document.getElementById("ELC_Ori_goods_Certi_for_LC").value = "--Select--";
								document.getElementById("ELC_Ori_goods_Certi_for_LC").disabled = true;
								document.getElementById("ELC_Orig_LC_Amend").value = "--Select--";
								document.getElementById("ELC_Orig_LC_Amend").disabled = true;
								
								document.getElementById("ELC_Non_Cust_sig_veri_msg").value = "--Select--";
								document.getElementById("ELC_Non_Cust_sig_veri_msg").disabled = true;
								document.getElementById("ELC_Rvw_LC_Amend_Cnfm").value = "--Select--";
								document.getElementById("ELC_Rvw_LC_Amend_Cnfm").disabled = true;
								document.getElementById("ELC_Msg_Issu_Bank").value = "--Select--";
								document.getElementById("ELC_Msg_Issu_Bank").disabled = true;
								
							}
							else{
								document.getElementById("ELC_ADCB_Auth_add_cnfm").disabled = false;	
								document.getElementById("ELC_Cnfm_changes_borne_by").disabled = false;
								//document.getElementById("ELC_plc_expiry_UAE").disabled = false;	
								document.getElementById("ELC_LC_Avl_at_counters").disabled = false;
								document.getElementById("ELC_TT_Reimb").disabled = false;	
								document.getElementById("ELC_Goods_desc_amt_calc").disabled = false;
								document.getElementById("ELC_Ori_goods_Certi_for_LC").disabled = false;	
								document.getElementById("ELC_Orig_LC_Amend").disabled = false;
								document.getElementById("ELC_Non_Cust_sig_veri_msg").disabled = false;
								document.getElementById("ELC_Rvw_LC_Amend_Cnfm").disabled = false;	
								document.getElementById("ELC_Msg_Issu_Bank").disabled = false;


							}
							}catch(e){}
							
							try{
							if(!(document.getElementById("ELC_Cnfm_changes_borne_by").value == "Beneficiary")){
									document.getElementById("ELC_Ben_req_STF_Txn").value = "--Select--";
									document.getElementById("ELC_Ben_req_STF_Txn").disabled = true;
								}
							else{
								document.getElementById("ELC_Ben_req_STF_Txn").disabled = false;	
								
								}
							}catch(e){}
							
							try{
								//alert(document.getElementById("ELCB_Cust_instr").value );
							if(!(document.getElementById("ELCB_Cust_instr").value == "Docs to be checked")){
								document.getElementById("ELCB_Bill_type").value = "--Select--";
								document.getElementById("ELCB_Bill_type").disabled = true;
								//document.getElementById("ELCB_LC_Amnt").value = "";
								document.getElementById("ELCB_LC_Amnt").disabled = true;	
								//document.getElementById("ELCB_Allwd_Tolrnce").value = "0";
								document.getElementById("ELCB_Allwd_Tolrnce").disabled = true;	
								document.getElementById("ELCB_Tlrnc_Amnt").value = "";
								document.getElementById("ELCB_Tlrnc_Amnt").disabled = true;	
								document.getElementById("ELCB_Doc_Rcvd").value = "";
								document.getElementById("ELCB_Doc_Rcvd").disabled = true;
								document.getElementById("ELCB_Doc_Amnt").value = "";
								document.getElementById("ELCB_Doc_Amnt").disabled = true;
								document.getElementById("ELCB_Avl_Bal").value = "";
								document.getElementById("ELCB_Avl_Bal").disabled = true;	
								document.getElementById("ELCB_Date_of_prsnt").value = "";
								document.getElementById("ELCB_Date_of_prsnt").disabled = true;	
								document.getElementById("ELCB_Shipmnt_date").value = "";
								document.getElementById("ELCB_Shipmnt_date").disabled = true;	
								//document.getElementById("ELCB_Last_Date_Of_Shpmnt").value = "";
								document.getElementById("ELCB_Last_Date_Of_Shpmnt").disabled = true;	
								//document.getElementById("ELCB_LC_Expiry").value = "";
								document.getElementById("ELCB_LC_Expiry").disabled = true;
								document.getElementById("ELCB_Prsntn_Per").value = "";
								document.getElementById("ELCB_Prsntn_Per").disabled = true;
								document.getElementById("ELCB_Last_Ship_Date_Diff").value = "";
								document.getElementById("ELCB_Last_Ship_Date_Diff").disabled = true;	
								document.getElementById("ELCB_Expr_Date_Diff").value = "";
								document.getElementById("ELCB_Expr_Date_Diff").disabled = true;	
								document.getElementById("ELCB_Prsntn_time_per").value = "";
								document.getElementById("ELCB_Prsntn_time_per").disabled = true;	
								document.getElementById("ELCB_Partial_Shpmnt").value = "--Select--";
								document.getElementById("ELCB_Partial_Shpmnt").disabled = true;	
								document.getElementById("ELCB_Transhipmnt").value = "--Select--";
								document.getElementById("ELCB_Transhipmnt").disabled = true;
								document.getElementById("ELCB_Doc_Crdt_Compiled").value = "--Select--";
								document.getElementById("ELCB_Doc_Crdt_Compiled").disabled = true;
								document.getElementById("ELCB_Is_MT742_Sent").value = "--Select--";
								document.getElementById("ELCB_Is_MT742_Sent").disabled = true;
								document.getElementById("ELCB_Is_LC_Cnfrmd").value = "--Select--";
								document.getElementById("ELCB_Is_LC_Cnfrmd").disabled = true;	
								document.getElementById("ELCB_Doc_To_be_Sent").value = "";
								document.getElementById("ELCB_Doc_To_be_Sent").disabled = true;	
								document.getElementById("ELCB_Drft_Count").value = "";
								document.getElementById("ELCB_Drft_Count").disabled = true;	
								document.getElementById("ELCB_invoice_Count").value = "";
								document.getElementById("ELCB_invoice_Count").disabled = true;	
								document.getElementById("ELCB_BL_Count").value = "";
								document.getElementById("ELCB_BL_Count").disabled = true;								
								document.getElementById("ELCB_PL_Count").value = "";
								document.getElementById("ELCB_PL_Count").disabled = true;	
								document.getElementById("ELCB_CO_Count").value = "";
								document.getElementById("ELCB_CO_Count").disabled = true;	
								document.getElementById("ELCB_SA_Count").value = "";
								document.getElementById("ELCB_SA_Count").disabled = true;	
								document.getElementById("ELCB_Ben_Cert_Count").value = "";
								document.getElementById("ELCB_Ben_Cert_Count").disabled = true;	
								document.getElementById("ELCB_Ins_Cert_Count").value = "";
								document.getElementById("ELCB_Ins_Cert_Count").disabled = true;
								document.getElementById("ELCB_Set_Count").value = "";
								document.getElementById("ELCB_Set_Count").disabled = true;	
								document.getElementById("ELCB_Cert_Count").value = "";
								document.getElementById("ELCB_Cert_Count").disabled = true;	
								document.getElementById("ELCB_Other_Count").value = "";
								document.getElementById("ELCB_Other_Count").disabled = true;
								
							}
							else{
								document.getElementById("ELCB_Bill_type").disabled = false;	
								//document.getElementById("ELCB_Ln_Crncy").disabled = false;	
								document.getElementById("ELCB_LC_Amnt").disabled = false;	
								document.getElementById("ELCB_Allwd_Tolrnce").disabled = false;	
								//document.getElementById("ELCB_Tlrnc_Amnt").disabled = false;	
								document.getElementById("ELCB_Doc_Rcvd").disabled = false;
								document.getElementById("ELCB_Doc_Amnt").disabled = false;	
								//document.getElementById("ELCB_Avl_Bal").disabled = false;	
								document.getElementById("ELCB_Date_of_prsnt").disabled = false;	
								document.getElementById("ELCB_Shipmnt_date").disabled = false;	
								document.getElementById("ELCB_Last_Date_Of_Shpmnt").disabled = false;	
								document.getElementById("ELCB_LC_Expiry").disabled = false;	
								document.getElementById("ELCB_Prsntn_Per").disabled = false;	
								//document.getElementById("ELCB_Last_Ship_Date_Diff").disabled = false;	
								//document.getElementById("ELCB_Expr_Date_Diff").disabled = false;	
								//document.getElementById("ELCB_Prsntn_time_per").disabled = false;	
								document.getElementById("ELCB_Partial_Shpmnt").disabled = false;	
								document.getElementById("ELCB_Transhipmnt").disabled = false;	
								document.getElementById("ELCB_Doc_Crdt_Compiled").disabled = false;	
								document.getElementById("ELCB_Is_MT742_Sent").disabled = false;	
								document.getElementById("ELCB_Is_LC_Cnfrmd").disabled = false;	
								document.getElementById("ELCB_Doc_To_be_Sent").disabled = false;	
								document.getElementById("ELCB_Drft_Count").disabled = false;	
								document.getElementById("ELCB_invoice_Count").disabled = false;	
								document.getElementById("ELCB_BL_Count").disabled = false;	
								document.getElementById("ELCB_PL_Count").disabled = false;	
								document.getElementById("ELCB_CO_Count").disabled = false;	
								document.getElementById("ELCB_SA_Count").disabled = false;	
								document.getElementById("ELCB_Ben_Cert_Count").disabled = false;	
								document.getElementById("ELCB_Ins_Cert_Count").disabled = false;
								document.getElementById("ELCB_Set_Count").disabled = false;	
								document.getElementById("ELCB_Cert_Count").disabled = false;
								document.getElementById("ELCB_Other_Count").disabled = false;
								document.getElementById("ELCB_Is_Doc_crdt_Compiled").disabled = false;

							}
							}catch(e){}
							
							try{
								if(!(document.getElementById("ELCB_Org_Req_Rcvd").options[document.getElementById("ELCB_Org_Req_Rcvd").selectedIndex].text == "Yes")){
									document.getElementById("ELCB_Is_Crrct_Dtls").value = "--Select--";
									document.getElementById("ELCB_Is_Crrct_Dtls").disabled = true;
								}
								else{
								document.getElementById("ELCB_Is_Crrct_Dtls").disabled = false;	
								
								}
							}catch(e){}
							
							try{
								if(!(document.getElementById("ELCB_Org_Req_Rcvd").options[document.getElementById("ELCB_Org_Req_Rcvd").selectedIndex].text == "No")){
									document.getElementById("ELCB_Ind_In_Place").value = "--Select--";
									document.getElementById("ELCB_Ind_In_Place").disabled = true;
								}
								else{
								document.getElementById("ELCB_Ind_In_Place").disabled = false;	
								
								}
							}catch(e){}
							
						}
						
					</script>
					</table>
					<table hidden>
					<!-- gridValhMap key=--Check the customer instructions value-from ext table-->
					<tr><TD><input type="text"  value="<%=ElcbCustInst%>"  id="ELCB_Cust_instr" style="width: 170px"   ></TD></tr>
					<script>
					onChange(document.getElementById("ELCB_Cust_instr").value);
					</script>
				</table>
								<Center>
								<button type="button"  class="buttonStyle button-viewer" onclick='importData();'><b>Import</b></button>
								</Center>
			</div>
		</div>
	</FORM>
</body>
</html>
