<%
/**
----------------------------------------------------------------------------------------------------
				NEWGEN SOFTWARE TECHNOLOGIES LIMITED
	Group						:	AP2
	Product / Project			:	Trade Finance
	Module						:	Custom JSP
	File Name					:	LimitCreditReview.jsp
	Author						:	Amrendra Pratap Singh
	Date written (DD/MM/YYYY)	:	05-10-2018
	Description					:	This is the custom jsp to load Checklist for Limit Credit Review guideline.
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
<title>Limit Credit</title>
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
<body onbeforeunload="ConfirmClose()" onunload="HandleOnClose()">
	<FORM name="mainFrm" action="javascript:selectandreturn()">
		<div style="overflow: visible; align: center">
			</br>
			<div
				style="height: 100%; width: 100%; overflow: visible; position: absolute;top=30 px">
				<Center style="color : #ba1b18;font-size: 20pt"><b>Customer Application Verification Form</b></Center>
				<table id="ReferToTable" align="center" >
					<tr bgColor=#ffffff> 
						<th
							style="FONT-FAMILY: Calibri; color : white; FONT-WEIGHT: normal; font-size: 10pt; width: 80%" bgColor="#ba1b18"><b>Description</b></th>
						<th 
							style="FONT-FAMILY: Calibri; color : white; FONT-WEIGHT: normal; font-size: 10pt; width=20%" bgColor="#ba1b18"><b>Value</b></th>
					</tr>
						<%!
						
		private String prepareAPSelectInputXml(String sQuery, String sCabname, String sSessionId){	
				return "<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+sQuery+"</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
			}
		public TreeMap<String, ArrayList<TreeMap<String, String>>> getMasterTableLOV(String sOutPut){
			TreeMap<String, ArrayList<TreeMap<String, String>>> hmapOuter = new TreeMap<String, ArrayList<TreeMap<String,String>>>();
			TreeMap<String, String>hmapInner;
			ArrayList<TreeMap<String, String>> arrHmap;
			ArrayList<TreeMap<String, String>> arrHmap1;
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
					{	hmapInner = new TreeMap<String, String>();
						arrHmap =  new ArrayList<TreeMap<String, String>>();
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
							arrHmap =  new ArrayList<TreeMap<String, String>>();
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
		
		
		public ArrayList<ArrayList<String>> getMasterDocumentTable(String sOutput){
			ArrayList<ArrayList<String>> ArrMaster = new ArrayList<ArrayList<String>>();
			ArrayList<String> ArrIntenal;
			String sInput;
			String sMainCode;
			String sRecord, sLabel, sLovStatus, sTableRef, sIsTP, sControlName, sLength, sDefValue, sEnableStatus;
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
		
		public TreeMap<String, String> getDocGridData(String gridData){
			TreeMap<String, String> resGridData = new TreeMap<String, String>();
			WriteToLog_showpage("Y","in getDocGridData");
            WriteToLog_showpage("Y","gridData in function="+gridData);
			try{
			String sArr[] = gridData.split("~~");
			for(int i=0;i<sArr.length;i++){
				String rowData[]=sArr[i].split("~");
				//WriteToLog_showpage("Y","rowData="+rowData[0]);
				//WriteToLog_showpage("Y","rowData[1]="+rowData[1]);
                String sValue = "";
				
				if(!"blankValue".equalsIgnoreCase(rowData[1]))
					sValue = rowData[1];				
				   resGridData.put(rowData[0], sValue);
			
			}
			}catch(Exception e){
				e.printStackTrace();
				WriteToLog_showpage("Y","in Exception in getDocGridData");
			}
			return resGridData;
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
	   
	   public TreeMap<String, String> arrListToHashMap(ArrayList<ArrayList<String>> arrL){
		   TreeMap<String, String> resGridData = new TreeMap<String, String>();
		   
		   for(ArrayList<String> arrInner : arrL ){
			WriteToLog_showpage("Y","Val1 "+arrInner.get(0) +" Val2 "+arrInner.get(1));
			   resGridData.put(arrInner.get(0),arrInner.get(1));
		   }		   
		   return resGridData;
	   }
	   
	   public TreeMap<String, String> getFinalWMSMap(ArrayList<String> arrFinal,ArrayList<ArrayList<String>> arrMapName){
		  
		   TreeMap<String, String> resGridData = new TreeMap<String, String>();
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
		
		
		public void initializeLogger(){
			try{
				Properties properties = new Properties();
				String log4JPropertyFile =  System.getProperty("user.dir") +  System.getProperty("file.separator") + "NGConfig" + System.getProperty("file.separator") + "TFO" +  System.getProperty("file.separator") +  "log4jJSP.properties";
				properties.load(new FileInputStream(log4JPropertyFile));
				PropertyConfigurator.configure(properties);
			}catch(Exception e){
				e.printStackTrace();
			}
		} 
		
	Logger log = Logger.getLogger(_LimitCreditReview.class);
	
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
							strFilePath.append("Custom_Log");
							strFilePath.append(File.separatorChar);
							strFilePath.append("TFO");
							strFilePath.append(File.separatorChar);
							strFilePath.append("LimitCreditReview");
							File fBackup=new File(strFilePath.toString());
							if(fBackup == null || !fBackup.isDirectory())
							{
								fBackup.mkdirs();
							}
							strFilePath.append(File.separatorChar);
							strFilePath.append("TFO_LCR_"+DtString+".xml");
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
					
				}	*/
				
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
								WriteToLog_showpage("Y","SANAL sRecord  "+arrMappedControl.get(i));
								String sTempString = sRecord.substring(sRecord.indexOf("<"+arrMappedControl.get(i).get(1)+">")+arrMappedControl.get(i).get(1).length()+2,sRecord.indexOf("</"+arrMappedControl.get(i).get(1)+">"));
								WriteToLog_showpage("Y","GROVER sTempString  "+sTempString);
								ArrIntenal.add(sTempString);
								
							}/*
							for(ArrayList<String> arrInt: arrMappedControl){
								for(String str : arrInt){
									String sTempString = sRecord.substring(sRecord.indexOf("<"+str+">")+str.length()+2,sRecord.indexOf("</"+str+">"));
									WriteToLog_showpage("Y","sTempString  "+sTempString);
									ArrIntenal.add(sTempString);	
								}
							}*/
							//WriteToLog_showpage("Y","internalList  "+ArrIntenal);
							//arrL.add(ArrIntenal);
							//WriteToLog_showpage("Y","MasterList  "+arrL);
							sOutputXML=sOutputXML.substring(sOutputXML.indexOf("</Record>")+"</Record>".length());
						}
					}
				
				}
			return ArrIntenal;	
		}
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
		public TreeMap<String, String> getMasterDoHashMap(String sKey,String sOutput){
			TreeMap<String, String> ArrMaster = new TreeMap<String, String>();
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
	
		%>

						<%
								initializeLogger();	
		//String sTabName =request.getParameter("TabName");
		//String sThirdParty =request.getParameter("IsThirdParty");
		String sGridData=request.getParameter("gridData");
		String sCustReqType = request.getParameter("sReqType");
		String sCustReqCat = request.getParameter("sReqCat");
		String sProductType = request.getParameter("sProdType");
		String sIsThirdParty = request.getParameter("sIsThirdParty");
		String sSourceChannel = request.getParameter("sSourceChannel");
		String sWI_NAME = request.getParameter("WI_NAME");
		String sLastWiName =  request.getParameter("Last_WI_Name");
		String sGTEENumber = request.getParameter("GTEE_NUMBER");
		String sAmendType = request.getParameter("sAmendType");
		
		String sReqCatCode = request.getParameter("sReqCatCode");
	    String sReqTypeCode = request.getParameter("sReqTypeCode"); //New FIeld
		String sLoanGroup = request.getParameter("sLoanGroup");


		
		
		boolean sSkipSourceChannel =false;
		boolean sSkipIsTP=false;
		boolean sSkipStatus = false;
		boolean sSkipLoanGroupIC = false;
		boolean sSkipLoanGroupICILCB = false;
		boolean sSkipLoanGroupILCB = false;
		boolean sSkipLoanGroupICILCBAM = false;
		
		boolean sSkipAmendType=false;
		boolean sSkipAmendType1=false;
		if("GRNT".equalsIgnoreCase(sReqCatCode)){
			if(!(sProductType.contains("403") || sProductType.contains("404"))){
				sSkipStatus = true;
			}
			if(sIsThirdParty.contains("2")){
				sSkipIsTP = true;
			}
			if(sSourceChannel.equalsIgnoreCase("S") ||sSourceChannel.equalsIgnoreCase("ISA")){
				sSkipSourceChannel = true;
			}
		}
		else if("IFS".equalsIgnoreCase(sReqCatCode) || "IFP".equalsIgnoreCase(sReqCatCode)){
			if(sReqTypeCode.equalsIgnoreCase("AM") && (!"APTP".equalsIgnoreCase(sAmendType))){
				sSkipAmendType = true;
			}
			if(sReqTypeCode.equalsIgnoreCase("AM") && (!"MDC".equalsIgnoreCase(sAmendType))){
				sSkipAmendType1 = true;
			}
			if(sReqTypeCode.equalsIgnoreCase("LD")){

			}
		}else if("ILC".equalsIgnoreCase(sReqCatCode) ||"SBLC".equalsIgnoreCase(sReqCatCode)){
			if("2".equalsIgnoreCase(sIsThirdParty)){
				sSkipIsTP = true;
			}
			if(sReqTypeCode.equalsIgnoreCase("ILC_AM") && (!("ILC_IV".equalsIgnoreCase(sAmendType) || "ILC_EED".equalsIgnoreCase(sAmendType)))){
				sSkipAmendType = true;
			}
			if(sReqTypeCode.equalsIgnoreCase("ILC_AM") && (!"ILC_IV".equalsIgnoreCase(sAmendType))){
				sSkipAmendType1 = true;
			}
		}else if("IC".equalsIgnoreCase(sReqCatCode)){
			if(!(sProductType.contains("T3U5") || sProductType.contains("I3U5"))){
				sSkipStatus = true;
			}
		}else if("TL".equalsIgnoreCase(sReqCatCode)){
			if(!(sLoanGroup.equalsIgnoreCase("IC"))){
				sSkipLoanGroupIC = true;
			}
			if(!(sLoanGroup.equalsIgnoreCase("ILCB"))){
				sSkipLoanGroupILCB = true;
			}
			if(!(sLoanGroup.equalsIgnoreCase("IC") || sLoanGroup.equalsIgnoreCase("ILCB"))){
				sSkipLoanGroupICILCB = true;
			} else if(!(sReqTypeCode.equalsIgnoreCase("TL_AM") && "MDC".equalsIgnoreCase(sAmendType))){
				sSkipLoanGroupICILCBAM = true;
			}
			
		}
		String sTable="";
		String sInputXML ="";
		String sOutputXML="";
		String sOutputMasterXML="";
		
		TreeMap<String, String> gridValhMap = new TreeMap<String, String>();
		TreeMap<String, ArrayList<TreeMap<String, String>>> hmapLOV = new TreeMap<String, ArrayList<TreeMap<String,String>>>();
		ArrayList<ArrayList<String>> arrDocMaster = new ArrayList<ArrayList<String>>();
		
		String sCabname=customSession.getEngineName();
		String sSessionId = customSession.getDMSSessionId();
		String sUserName = customSession.getUserName();
		String sJtsIp = customSession.getJtsIp();
		String wd_uid=request.getParameter("session");
		String iJtsPort = String.valueOf(customSession.getJtsPort());
		ArrayList<ArrayList<String>> arrlimitCredit = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> arrMappedControls = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> arrMappedControlsExt = new ArrayList<ArrayList<String>>();
		ArrayList<String> arrFinal = new ArrayList<String>();
		ArrayList<String> arrFinalExt = new ArrayList<String>();
	//	

		try{
			if(!(sReqTypeCode.equalsIgnoreCase("NI") ||sReqTypeCode.equalsIgnoreCase("LD") || sReqTypeCode.equalsIgnoreCase("STL")
				||("IFS".equalsIgnoreCase(sReqCatCode) && sReqTypeCode.equalsIgnoreCase("AM")) || ("IFP".equalsIgnoreCase(sReqCatCode) && sReqTypeCode.equalsIgnoreCase("AM")))){
				String sInputXMLMappedColumn="",sOutputMasterXMLMappedColumn="";
				String sQueryMappedcolumn = "Select DISTINCT DocR.DOC_RVW DOC_RVW, ChkMap.mapped_col_name COLUMN_NAME from TFO_DJ_DOC_RVW_MAST DocR,TFO_DJ_CHECKLIST_MAP_MAST ChkMap where  DocR.mapped_controlname = ChkMap.CHECKLIST_ID and DocR.TAB_NAME = 'LmtCrdt' and DocR.req_cat_code = '"+sReqCatCode+"' and ChkMap.req_cat_code != 'EXT'"; 
				
				sInputXMLMappedColumn=prepareAPSelectInputXml(sQueryMappedcolumn,sCabname, sSessionId);
				WriteToLog_showpage("Y","sOutputXMLLmtCrdt1 For JSP  "+sInputXMLMappedColumn);
				sOutputMasterXMLMappedColumn =NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXMLMappedColumn);
				WriteToLog_showpage("Y","sOutputXMLLmtCrdt2 For JSP  "+sOutputMasterXMLMappedColumn);
				
				arrMappedControls = getGridData(sOutputMasterXMLMappedColumn,"DOC_RVW","COLUMN_NAME");
				
				String sFinalQuery = getQuery(arrMappedControls, sGTEENumber, sReqCatCode);
				WriteToLog_showpage("Y","Final Query  "+sFinalQuery);
				
				String sQueryMappedcolumnExt = "Select DISTINCT DocR.DOC_RVW DOC_RVW, ChkMap.mapped_col_name COLUMN_NAME from TFO_DJ_DOC_RVW_MAST DocR,TFO_DJ_CHECKLIST_MAP_MAST ChkMap where  DocR.mapped_controlname = ChkMap.CHECKLIST_ID and DocR.TAB_NAME = 'LmtCrdt' and ChkMap.req_cat_code = 'EXT'"; 
				
				sInputXMLMappedColumn = prepareAPSelectInputXml(sQueryMappedcolumnExt,sCabname, sSessionId);
				WriteToLog_showpage("Y","sOutputXMLExt For JSP  "+sInputXMLMappedColumn);
				sOutputMasterXMLMappedColumn = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXMLMappedColumn);
				WriteToLog_showpage("Y","sOutputXMLExt2 For JSP  "+sOutputMasterXMLMappedColumn);
				
				arrMappedControlsExt = getGridData(sOutputMasterXMLMappedColumn,"DOC_RVW","COLUMN_NAME");
				String sFinalQueryExt = "";
				sFinalQueryExt = getQueryExt(arrMappedControlsExt, sWI_NAME);
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
				
		}
			
		}catch(Exception e){
			
			
		}
		
		
		String returnStr="";
		String aryCurr[]=null;
		String sQuery1="Select KEY, CODE,DESCRIPTION FROM TFO_DJ_LCP_MAST where req_cat_code ='"+sReqCatCode+"' UNION Select KEY, CODE,DESCRIPTION FROM TFO_DJ_YES_NA_MAST UNION Select KEY, CODE,DESCRIPTION FROM TFO_DJ_RELATED_PARTIES UNION Select KEY, CODE,DESCRIPTION FROM TFO_DJ_DISC_LINE where req_cat_code ='"+sReqCatCode+"' UNION Select KEY, CODE,DESCRIPTION FROM TFO_DJ_YES_NO_MAST UNION Select KEY, CODE,DESCRIPTION FROM TFO_DJ_YES_NO_NAP UNION Select KEY, CODE,DESCRIPTION FROM TFO_DJ_LC_TRNSPRT_DOC UNION Select KEY, CODE,DESCRIPTION FROM TFO_DJ_YESNO_INDEMINITY UNION Select KEY, CODE,DESCRIPTION FROM TFO_DJ_YESNO_NA_CH UNION Select KEY, CODE,DESCRIPTION FROM TFO_DJ_ACC_NO_TYPE UNION Select KEY, CODE,DESCRIPTION FROM tfo_dj_ca_avl_mast UNION Select KEY, CODE,DESCRIPTION FROM tfo_dj_col_dtl_mast where req_cat_code ='"+sReqCatCode+"' UNION Select KEY, CODE,DESCRIPTION FROM TFO_DJ_COMMN_CHARGE_TYPE where req_cat_code ='"+sReqCatCode+"' UNION Select KEY, CODE,DESCRIPTION FROM tfo_dj_comm_prcng_mast where req_cat_code ='"+sReqCatCode+"' UNION Select KEY, CODE,DESCRIPTION FROM tfo_dj_comm_calc_mast where req_cat_code ='"+sReqCatCode+"' UNION Select KEY, CCY_CODE CODE,CCY_CODE||'-'||CCY_NAME DESCRIPTION from tfo_dj_curr_decimals  UNION Select KEY, CODE, DESCRIPTION FROM TFO_DJ_TSLM_PENAL_IR UNION SELECT KEY, CODE, DESCRIPTION FROM TFO_DJ_YES_NO_NL order by DESCRIPTION";

		WriteToLog_showpage("Y","New Query sQuery1  :"+sQuery1);
		sInputXML = prepareAPSelectInputXml(sQuery1,sCabname, sSessionId);

		if(!sInputXML.isEmpty())
			sOutputMasterXML =NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXML);
		WriteToLog_showpage("Y","sOutputXML11 For JSP  "+sOutputMasterXML);
		
		String sQuery="SELECT REQ_CAT, REQ_TYPE,MAPPED_CONTROLNAME, DOC_RVW AS LABEL,CNTRL_TYPE AS LOV_STATUS,DOC_RVW_KEY AS TABLE_REF, TAB_NAME, IS_TP AS IS_THIRD_PARTY , DECODE(VAR_LENGTH, null, 0, VAR_LENGTH) AS VAR_LENGTH, DEFAULTVALUE, ENABLEDSTATUS FROM tfo_dj_doc_rvw_mast where TAB_NAME = 'LmtCrdt' and REQ_TYPE_CODE = '"+sReqTypeCode+"' and REQ_CAT_CODE='"+sReqCatCode+"' order by SNO";
		sInputXML = prepareAPSelectInputXml(sQuery,sCabname, sSessionId);
		if(!sInputXML.isEmpty())
			sOutputXML =NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXML);
		if(!sOutputXML.isEmpty())
			arrDocMaster = getMasterDocumentTable(sOutputXML);
		
		
		
	
		WriteToLog_showpage("Y","sOutputXML For JSP  "+arrDocMaster);
		 WriteToLog_showpage("Y","sOutputXML For sGridData=  "+sGridData);
		try{
		if(!(sGridData.isEmpty() || sGridData.equalsIgnoreCase(" ") || sGridData.equalsIgnoreCase(""))){
			if(sGridData.contains("AmpersandSign"))
				sGridData = sGridData.replace("AmpersandSign","&");
			if(sGridData.contains("PercentSign"))
				sGridData = sGridData.replace("PercentSign","%");
			
			
			gridValhMap=getDocGridData(sGridData);
			
		}else{
			//gridValhMap=arrListToHashMap(arrFinal);

			WriteToLog_showpage("Y","EMPTY CASE   ");
			WriteToLog_showpage("Y","ArrayMapName  "+arrMappedControls);
		   WriteToLog_showpage("Y","ArrayFinal "+arrFinal);
			gridValhMap=getFinalWMSMap(arrFinal,arrMappedControls);	
			WriteToLog_showpage("Y","gridValhMap "+gridValhMap.toString());			

		}
		}catch(Exception e)
		{           e.printStackTrace();
				    WriteToLog_showpage("Y","sOutputXML For sGridData=  "+sGridData);
					WriteToLog_showpage("Y","sOutputXML For sGridData=  "+e);


		}		
		String selString="";
		for(int cntr=0;cntr<arrDocMaster.size();cntr++){
			TreeMap<String, String> hmaTemp;
			String sOutput="";
			String sSelecedvalue ="";
			String sControlID="";
			String sTempLabel="";
			String sCntrlLength="";
			String sDefaultvalue = "";
			String sEnabledStatus = "";
			
			ArrayList<TreeMap<String, String>> arrHmapTR;
			String sLabel="",sCODE="",sLOVStatus="",sMasterRef="",sIsThirdPatry="";
			sLabel = arrDocMaster.get(cntr).get(0);
			sLOVStatus =arrDocMaster.get(cntr).get(1);
			sMasterRef = arrDocMaster.get(cntr).get(2);
			sIsThirdPatry = arrDocMaster.get(cntr).get(3);
			sControlID = arrDocMaster.get(cntr).get(4);
			sCntrlLength = arrDocMaster.get(cntr).get(5);
			sDefaultvalue = arrDocMaster.get(cntr).get(6);
			sEnabledStatus = arrDocMaster.get(cntr).get(7);
			WriteToLog_showpage("Y","sLabel  "+sLabel);
			WriteToLog_showpage("Y","sLOVStatus  "+sLOVStatus);
			sTempLabel = sLabel;
			
		
			WriteToLog_showpage("Y","sTempLabel  "+sTempLabel);
			String name="";
			if(sSkipStatus){
				if(sControlID.equalsIgnoreCase("Is_OP_Clsd")||sControlID.equalsIgnoreCase("Is_Advnc_Pmnt_update")
					||sControlID.equalsIgnoreCase("IC_Is_Txn_Accd_Note")||sControlID.equalsIgnoreCase("IC_Is_txn_Within_Lmt")
					||sControlID.equalsIgnoreCase("IC_Limit_Line")||sControlID.equalsIgnoreCase("IC_Is_ICG_Approvals")
					||sControlID.equalsIgnoreCase("IC_Is_Comm_Chrgd"))
					continue;
			}
			
			if(sSkipIsTP){
				if(sControlID.equalsIgnoreCase("is_TP_Indmnty")||sControlID.equalsIgnoreCase("Is_TP_CA") || sControlID.equalsIgnoreCase("Is_RM_TP")
					||sControlID.equalsIgnoreCase("ILC_TP_Avl") || sControlID.equalsIgnoreCase("ILC_TP_Apprvd_CA")|| sControlID.equalsIgnoreCase("ILC_RM_Cnfrmtion"))
					continue;
			}
			if(sSkipSourceChannel){
				if(sControlID.equalsIgnoreCase("Is_tnr_CA")||sControlID.equalsIgnoreCase("Is_Valid_CA")
					||sControlID.equalsIgnoreCase("max_Tenor_Allowed")||sControlID.equalsIgnoreCase("prcssng_Charges"))
					continue;
			}
			if(sSkipAmendType){
				if(sControlID.equalsIgnoreCase("PrchsLmtDtls") || sControlID.equalsIgnoreCase("ILC_Is_CA_Avl"))
					continue;
			}
			if(sSkipAmendType1){
				if(sControlID.equalsIgnoreCase("CrdtAppAvail")||sControlID.equalsIgnoreCase("ILC_Crdt_Apprvl_SME") 
					|| sControlID.equalsIgnoreCase("ILC_Per_Party_Lmt"))
					continue;
			}if(sSkipLoanGroupIC){
				if(sControlID.equalsIgnoreCase("TL_Is_Txn_Accrdnce") || sControlID.equalsIgnoreCase("TL_MRA_Dtls")
					|| sControlID.equalsIgnoreCase("TL_PD_Dtls"))
					continue;
			}if(sSkipLoanGroupICILCB){
				if(sControlID.equalsIgnoreCase("TL_Valid_CA_Avl") || sControlID.equalsIgnoreCase("TL_Is_Within_Lmts")
					||sControlID.equalsIgnoreCase("TL_Lmt_Line") || sControlID.equalsIgnoreCase("TL_IS_Cust_Past_Txn")
					||sControlID.equalsIgnoreCase("TL_Colltrl_Dtls") || sControlID.equalsIgnoreCase("TL_Cash_Mrgn_Prcnt")
					||sControlID.equalsIgnoreCase("TL_Mrgn_Acc_Number") || sControlID.equalsIgnoreCase("TL_FD_Nmbr_Dtls")
					||sControlID.equalsIgnoreCase("TL_Client_Contr_Crncy") || sControlID.equalsIgnoreCase("TL_Client_Contr_Amnt")
					||sControlID.equalsIgnoreCase("TL_Client_Debit_Acc_Num") || sControlID.equalsIgnoreCase("TL_LIBOR_Dtls")
					||sControlID.equalsIgnoreCase("TL_Applicable_Spread") || sControlID.equalsIgnoreCase("TL_Floor_Rate")
					||sControlID.equalsIgnoreCase("TL_Crdt_Approval") || sControlID.equalsIgnoreCase("TL_Handling_Fee"))
					continue;
			}if(sSkipLoanGroupILCB){
				if(sControlID.equalsIgnoreCase("TL_Lglstn_Fee"))
					continue;
			}if(sSkipLoanGroupICILCBAM){
				if(sControlID.equalsIgnoreCase("TL_Crdt_Approval"))
					continue;
			}if(sReqTypeCode.equalsIgnoreCase("IFA_CTP") && sSourceChannel.equalsIgnoreCase("TSLM Front End")){
				if((sControlID.equalsIgnoreCase("Coll_Dtls"))||(sControlID.equalsIgnoreCase("ThrsExcRmrks"))||(sControlID.equalsIgnoreCase("Is_Req_lmts"))||(sControlID.equalsIgnoreCase("Limt_Line"))||(sControlID.equalsIgnoreCase("AppCntrParty"))||(sControlID.equalsIgnoreCase("TxnPPL"))||(sControlID.equalsIgnoreCase("Cash_mrgn_Perc"))||(sControlID.equalsIgnoreCase("MarginAccDtls"))||(sControlID.equalsIgnoreCase("FDNumDtls"))||(sControlID.equalsIgnoreCase("ClntContAmnt"))||(sControlID.equalsIgnoreCase("DbtAccNum"))||(sControlID.equalsIgnoreCase("EBORDtls"))||(sControlID.equalsIgnoreCase("SpreadDtls"))||(sControlID.equalsIgnoreCase("FloorRateDtls"))||(sControlID.equalsIgnoreCase("Penal_Int_Rate"))||(sControlID.equalsIgnoreCase("HandlingFee"))||(sControlID.equalsIgnoreCase("TxnCANote"))||(sControlID.equalsIgnoreCase("TxnAppDefPolicy"))||(sControlID.equalsIgnoreCase("LC_LIMIT_LINE")))
					continue;
				
			}
		
			
			
			
			if("No".equalsIgnoreCase(sIsThirdPatry)){ 
			%>
						<TR>
						<% if(!"HIDDEN".equalsIgnoreCase(sLOVStatus)){%>
						<TD
							style="FONT-FAMILY: Calibri; FONT-WEIGHT: normal; font-size: 10pt"><%=sLabel%></TD>
						<%
						}
				if("LOV".equalsIgnoreCase(sLOVStatus)){
					name = "sLOV"+cntr;
				%>
						<TD><Select name=<%=sControlID%> id=<%=sControlID%> onchange="onChange(this.value)" style="width: 110px">
								<%
						arrHmapTR = new ArrayList<TreeMap<String, String>>(); 
						hmaTemp = new TreeMap<String, String>();
						
						if(!hmapLOV.isEmpty())		
							arrHmapTR = hmapLOV.get(sMasterRef);
						WriteToLog_showpage("Y","Final Grid gridValhMap "+gridValhMap);
						if(!gridValhMap.isEmpty())
							if(gridValhMap.containsKey(sTempLabel))
								if(!gridValhMap.get(sTempLabel).isEmpty())
									sSelecedvalue = gridValhMap.get(sTempLabel);
						
												
						hmaTemp = getMasterDoHashMap(sMasterRef, sOutputMasterXML);
						WriteToLog_showpage("Y","getMasterDoHashMap For JSP  "+hmaTemp);
						int i = 1;
						
						for(String sCode1: hmaTemp.keySet()){
								if(hmaTemp.get(sCode1).equalsIgnoreCase(sSelecedvalue) || hmaTemp.get(sCode1).equalsIgnoreCase(sDefaultvalue)){
								selString = "selected";		
								%>
								<option <%=selString%> value=<%=hmaTemp.get(sCode1)%>><%=hmaTemp.get(sCode1)%></option>
								<%
								selString="";
								continue;
							}
															
							if(i==1){
								%>
								<option value="--Select--">--Select--</option>
								<option  value=<%=hmaTemp.get(sCode1)%>><%=hmaTemp.get(sCode1)%></option>
								<%
							}else{
							%>
								<option value=<%=hmaTemp.get(sCode1)%>><%=hmaTemp.get(sCode1)%></option>
								<%
								}
							i+=1;
							}
						
						%>
						</Select></TD>
						<%
						
				}
				else if("HIDDEN".equalsIgnoreCase(sLOVStatus)){
					if(!gridValhMap.isEmpty() && gridValhMap.containsKey(sLabel) && !gridValhMap.get(sLabel).isEmpty()){
						String sExistingVal="";
						WriteToLog_showpage("Y","GridValue "+gridValhMap.get(sLabel));
						sExistingVal = gridValhMap.get(sLabel).replaceAll("\"","&quot;");
					%>
					<input type="hidden" name=<%=sControlID%> id=<%=sControlID%> value="<%=sExistingVal%>">
					<%
					}
					else{
						%>
						<input type="hidden" name=<%=sControlID%> id=<%=sControlID%> value="<%=sDefaultvalue%>">
						<%
					}
				}
				else{
					name="sTextBox"+cntr;
									
					if((!gridValhMap.isEmpty() && gridValhMap.containsKey(sLabel) && !gridValhMap.get(sLabel).isEmpty())){
						
						String sExistingVal="";
											
						WriteToLog_showpage("Y","GridValue "+gridValhMap.get(sLabel));
						sExistingVal = gridValhMap.get(sLabel).replaceAll("\"","&quot;");
										
						
				%>
						<TD><input type="text"  maxlength=<%=sCntrlLength%> name=<%=sControlID%> id=<%=sControlID%> value="<%=sExistingVal%>"
							style="width: 110px" onfocusout="checkNumericValidation(this.value,this.id)"></TD>
							<%
						}else{
							%>
							
						<TD><input type="text"  maxlength=<%=sCntrlLength%> name=<%=sControlID%> id=<%=sControlID%> value="<%=sDefaultvalue%>" style="width: 110px" onfocusout="checkNumericValidation(this.value,this.id)"/></TD>
						<%
						}
					}	
			}	
		}
			sSkipStatus=false;
		%>
					</TR>
					<br>
					<script>
						window.onload=onChange("Demo");		
						var myclose = false;						
						function ConfirmClose()
						{
							if (event.clientY < 0)
							{
								event.returnValue = 'Unsave data will be lost';
								myclose=true;
							}
						}
						function validateData(value){							
							if(!isNaN(value)){
								if(parseFloat(value) > 999){
									alert("Value should be less than 999");
								}
								else if(parseFloat(value) < 0){
									alert("Value should be greater than 0");
								}
							}
							else{
								alert("Invalid Input - Please enter a number");
							}
							
						}
						function validateData1(val){
							var length = val.length;
							if(length>10){
								alert("Length Cannot Exceed 10");
							}
						}
						function onChange(value){
							var cols=document.getElementById('ReferToTable').getElementsByTagName('td'),colsLen=cols.length
							var requestType="<%=sReqTypeCode%>";
							var requestCat="<%=sReqCatCode%>";
							if(colsLen == 0){
								alert("No Checklist found for this request");
								window.parent.handleJSPResponsePPM('LmtCrdt',"Empty");
								//window.opener.getJSPLimitData("Empty");
								window.close();	
							}
							try{
								//alert(document.getElementById("Coll_Dtls").options[document.getElementById("Coll_Dtls").selectedIndex].text);
						   if(value=="Demo" && requestCat=="GRNT" && requestType=="AM" &&
						   document.getElementById("Coll_Dtls").options[document.getElementById("Coll_Dtls").selectedIndex].text=="--Select--"){
							 //  alert(document.getElementById("GRNT_Percent").value);
							if((document.getElementById("GRNT_Percent").value=="0")|| (document.getElementById("GRNT_Percent").value=="")) {
								document.getElementById("Coll_Dtls").value = "NIL";
															
							}
							else{
								
							    document.getElementById("Coll_Dtls").selectedIndex ="1" ;
							}
							}}catch(e){}
							try{
								//alert(document.getElementById("Coll_Dtls").value);
							if((document.getElementById("Coll_Dtls").options[document.getElementById("Coll_Dtls").selectedIndex].text == "CASH MARGIN")){								
								document.getElementById("Cash_mrgn_Perc").disabled = false;
								document.getElementById("Fxd_Dep_perc").value = "";
								document.getElementById("Fxd_Dep_perc").disabled = true;
								//alert('Checking client contribution data');
								//console.log('Checking client contribution data');	
														
							}
							else if((document.getElementById("Coll_Dtls").options[document.getElementById("Coll_Dtls").selectedIndex].text == "FIXED DEPOSIT")){
								document.getElementById("Fxd_Dep_perc").disabled = false;
								document.getElementById("Cash_mrgn_Perc").value = "";
								document.getElementById("Cash_mrgn_Perc").disabled = true;
							}
							else{
								document.getElementById("Cash_mrgn_Perc").disabled = true;
								document.getElementById("Fxd_Dep_perc").disabled = true;								
							}
							}catch(e){}
							
							try{
							if(!(document.getElementById("Comm_Prcng_type").value == "SPECIAL")){
								document.getElementById("Comm_Perc_PA").value = "";
								document.getElementById("Comm_Perc_PA").disabled = true;								
							}
							else{
								document.getElementById("Comm_Perc_PA").disabled = false;
							}
							}catch(e){}
							
							try{
							if(!(document.getElementById("ILC_TP_Apprvd_CA").options[document.getElementById("ILC_TP_Apprvd_CA").selectedIndex].text== "No")){
								document.getElementById("ILC_RM_Cnfrmtion").value = "--Select--";
								document.getElementById("ILC_RM_Cnfrmtion").disabled = true;								
							}
							else{
								document.getElementById("ILC_RM_Cnfrmtion").disabled = false;
							}
							}catch(e){}
							
							try{
							if(!(document.getElementById("ILC_Req_Lmts").value.equalsIgnoreCase("Yes"))){
								document.getElementById("ILC_Lmt_line").value = "";
								document.getElementById("ILC_Lmt_line").disabled = true;								
							}
							else{
								document.getElementById("ILC_Lmt_line").disabled = false;
							}
							}catch(e){}
							
							try{
							if(!(document.getElementById("ILC_Col_Det").options[document.getElementById("ILC_Col_Det").selectedIndex].text == "CASH MARGIN")){
								document.getElementById("ILC_Cash_Mrgn_Prcnt").value = "";
								document.getElementById("ILC_Cash_Mrgn_Prcnt").disabled = true;								
							}
							else{
								document.getElementById("ILC_Cash_Mrgn_Prcnt").disabled = false;
							}
							}catch(e){}
							try{
							if(value=="Demo" && requestCat=="ILC" && requestType=="ILC_AM" &&
							 document.getElementById("ILC_Cash_Mrgn_Prcnt").value==""){
							if((document.getElementById("ILC_Percent").value=="0")|| (document.getElementById("ILC_Percent").value=="")){
								document.getElementById("ILC_Cash_Mrgn_Prcnt").value = "NIL";
										
							}
							else{
								document.getElementById("ILC_Cash_Mrgn_Prcnt").value = document.getElementById("ILC_Percent").value;
								
							}
							}}catch(e){}
							
							try{
							if(!(document.getElementById("ILC_Col_Det").options[document.getElementById("ILC_Col_Det").selectedIndex].text == "FIXED DEPOSIT")){
								document.getElementById("ILC_Fxd_Dep_Percent").value = "";
								document.getElementById("ILC_Fxd_Dep_Percent").disabled = true;

								document.getElementById("ILC_Fxd_Dep_No").value = "";
								document.getElementById("ILC_Fxd_Dep_No").disabled = true;	

								document.getElementById("ILC_Markd_Fixed_Deposit").value = "--Select--";
								document.getElementById("ILC_Markd_Fixed_Deposit").disabled = true;								
							}
							else{
								document.getElementById("ILC_Fxd_Dep_Percent").disabled = false;
								document.getElementById("ILC_Fxd_Dep_No").disabled = false;
								document.getElementById("ILC_Markd_Fixed_Deposit").disabled = false;
							}
							}catch(e){}
							try{
							if(!(document.getElementById("ILC_Comm_Prcng_Type").options[document.getElementById("ILC_Comm_Prcng_Type").selectedIndex].text == "FLAT AMOUNT" 
								||document.getElementById("ILC_Comm_Prcng_Type").options[document.getElementById("ILC_Comm_Prcng_Type").selectedIndex].text == "SPECIAL")){
								document.getElementById("ILC_Comm_Percent").value = "";
								document.getElementById("ILC_Comm_Percent").disabled = true;								
							}
							else{
								document.getElementById("ILC_Comm_Percent").disabled = false;
							}
							}catch(e){}
							try{
							if(!(document.getElementById("ILC_Comm_Prcng_Type").options[document.getElementById("ILC_Comm_Prcng_Type").selectedIndex].text == "NIL COMMISSION" 
								||document.getElementById("ILC_Comm_Prcng_Type").options[document.getElementById("ILC_Comm_Prcng_Type").selectedIndex].text == "NOT AVAILABLE"
								||document.getElementById("ILC_Comm_Prcng_Type").options[document.getElementById("ILC_Comm_Prcng_Type").selectedIndex].text == "FLAT AMOUNT")){
								document.getElementById("ILC_Comm_Calc_method").disabled = false;	
																
							}
							else{
								document.getElementById("ILC_Comm_Calc_method").value = "--Select--";
								document.getElementById("ILC_Comm_Calc_method").disabled = true;
							}
							}catch(e){}
							
							try{
							if(!(document.getElementById("ILC_Comm_Calc_method").value == "PERIODIC")){
								document.getElementById("ILC_Comm_Collection_Period").value = "";
								document.getElementById("ILC_Comm_Collection_Period").disabled = true;
								
																
							}
							else{
								document.getElementById("ILC_Comm_Collection_Period").disabled = false;	
								
							}
							}catch(e){}
							
							try{
							if(!(document.getElementById("ILC_Pmnt_Clause").value == "Yes")){
								document.getElementById("ILC_Cust_Pmnt_ID").value = "--Select--";
								document.getElementById("ILC_Cust_Pmnt_ID").disabled = true;
								document.getElementById("ILC_Conf_Pmnt_Avl").value = "--Select--";
								document.getElementById("ILC_Conf_Pmnt_Avl").disabled = true;
								
																
							}
							else{
								document.getElementById("ILC_Cust_Pmnt_ID").disabled = false;
								document.getElementById("ILC_Conf_Pmnt_Avl").disabled = false;
								
							}
							}catch(e){}
							
							
							try{
							if(!(document.getElementById("Comm_calc_method").value == "PERIODIC")){
								document.getElementById("Comm_Coll_Period").value = "";
								document.getElementById("Comm_Coll_Period").disabled = true;
								
							}
							else{
								document.getElementById("Comm_Coll_Period").disabled = false;
							}
							}catch(e){}
							
							
							try{
							if(!(document.getElementById("TxnAppDefPolicy").value == "No")){
								document.getElementById("ThrsExcRmrks").value = "";
								document.getElementById("ThrsExcRmrks").disabled = true;
								
							}
							else{
								document.getElementById("ThrsExcRmrks").disabled = false;
							}
							}catch(e){}
							try{
							if(!(document.getElementById("AccNumCustInvoice").options[document.getElementById("AccNumCustInvoice").selectedIndex].text == "CUSTOMER CURRENT ACCOUNT")){
								document.getElementById("CustCurrAccApproval").value = "--Select--";
								document.getElementById("CustCurrAccApproval").disabled = true;
								
							}
							else{
								document.getElementById("CustCurrAccApproval").disabled = false;
							}
							}catch(e){}
							try{
							if(!(document.getElementById("Coll_Dtls").options[document.getElementById("Coll_Dtls").selectedIndex].text == "CASH MARGIN AND CLIENT CONTRIBUTION" 
							||document.getElementById("Coll_Dtls").options[document.getElementById("Coll_Dtls").selectedIndex].text == "CASH MARGIN" )){
								document.getElementById("Cash_mrgn_Perc").value = "";
								document.getElementById("Cash_mrgn_Perc").disabled = true;
								document.getElementById("MarginAccDtls").value = "";
								document.getElementById("MarginAccDtls").disabled = true;
								
							}
							else{
								document.getElementById("Cash_mrgn_Perc").disabled = false;
								document.getElementById("MarginAccDtls").disabled = false;
							}
							}catch(e){}
							try{
							if(value=="Demo" && requestCat=="GRNT" && requestType=="AM" &&
							 document.getElementById("Cash_mrgn_Perc").value==""){
							if( (document.getElementById("GRNT_Percent").value=="0")|| (document.getElementById("GRNT_Percent").value=="")){
								document.getElementById("Cash_mrgn_Perc").value = "NIL";
																
							}
							else{
								document.getElementById("Cash_mrgn_Perc").value = document.getElementById("GRNT_Percent").value;
				
							}
							}}catch(e){}
							try{
							if(!(document.getElementById("Coll_Dtls").options[document.getElementById("Coll_Dtls").selectedIndex].text == "FD UNDER LIEN" || 
							document.getElementById("Coll_Dtls").options[document.getElementById("Coll_Dtls").selectedIndex].text == "FD UNDER LIEN AND CLIENT CONTRIBUTION" )){
								document.getElementById("FDNumDtls").value = "";
								document.getElementById("FDNumDtls").disabled = true;
								
							}
							else{
								document.getElementById("FDNumDtls").disabled = false;
							}
							}catch(e){}
							try{
							if(!(document.getElementById("Coll_Dtls").options[document.getElementById("Coll_Dtls").selectedIndex].text == "CLIENT CONTRIBUTION" || 
							document.getElementById("Coll_Dtls").options[document.getElementById("Coll_Dtls").selectedIndex].text == "FD UNDER LIEN AND CLIENT CONTRIBUTION" 
								||document.getElementById("Coll_Dtls").options[document.getElementById("Coll_Dtls").selectedIndex].text == "CASH MARGIN AND CLIENT CONTRIBUTION")){
								document.getElementById("ClntContrCurr").value = "--Select--";
								document.getElementById("ClntContrCurr").disabled = true;
								document.getElementById("DbtAccNum").value = "";
								document.getElementById("DbtAccNum").disabled = true;
								document.getElementById("ClntContAmnt").value = "";
								document.getElementById("ClntContAmnt").disabled = true;
							}
							else{
								document.getElementById("ClntContrCurr").disabled = false;
								document.getElementById("DbtAccNum").disabled = false;
								document.getElementById("ClntContAmnt").disabled = false;
							}
							}catch(e){}
							try{
								if(!(document.getElementById("Coll_Dtls").options[document.getElementById("Coll_Dtls").selectedIndex].text == "FIXED DEPOSIT")){
								document.getElementById("GRNT_Fx_Dep_No").disabled = true;
								document.getElementById("GRNT_Fx_Dep_No").value = "";
								
								document.getElementById("GRNT_Lien_Mrkd_Fx_Dep").value = "--Select--";
								document.getElementById("GRNT_Lien_Mrkd_Fx_Dep").disabled = true;
								}
							else{
								document.getElementById("GRNT_Fx_Dep_No").disabled = false;
								document.getElementById("GRNT_Lien_Mrkd_Fx_Dep").disabled = false;								
							}
							}catch(e){}
							
							
							try{
								if(!(document.getElementById("TL_Colltrl_Dtls").options[document.getElementById("TL_Colltrl_Dtls").selectedIndex].text == "CASH MARGIN"
									||document.getElementById("TL_Colltrl_Dtls").options[document.getElementById("TL_Colltrl_Dtls").selectedIndex].text == "CASH MARGIN AND CLIENT CONTRIBUTION")){
								document.getElementById("TL_Cash_Mrgn_Prcnt").disabled = true;
								document.getElementById("TL_Cash_Mrgn_Prcnt").value = "";
								
								document.getElementById("TL_Mrgn_Acc_Number").value = "";
								document.getElementById("TL_Mrgn_Acc_Number").disabled = true;
								}
							else{
								document.getElementById("TL_Mrgn_Acc_Number").disabled = false;
								document.getElementById("TL_Cash_Mrgn_Prcnt").disabled = false;								
							}
							}catch(e){}
							try{
								if(!(document.getElementById("TL_Colltrl_Dtls").options[document.getElementById("TL_Colltrl_Dtls").selectedIndex].text == "FD UNDER LIEN"
									||document.getElementById("TL_Colltrl_Dtls").options[document.getElementById("TL_Colltrl_Dtls").selectedIndex].text == "FD UNDER LIEN AND CLIENT CONTRIBUTION")){
								document.getElementById("TL_FD_Nmbr_Dtls").disabled = true;
								document.getElementById("TL_FD_Nmbr_Dtls").value = "";
								}
							else{
								document.getElementById("TL_FD_Nmbr_Dtls").disabled = false;
							}
							}catch(e){}
							try{
								if(!(document.getElementById("TL_Colltrl_Dtls").options[document.getElementById("TL_Colltrl_Dtls").selectedIndex].text == "CLIENT CONTRIBUTION"
									||document.getElementById("TL_Colltrl_Dtls").options[document.getElementById("TL_Colltrl_Dtls").selectedIndex].text == "CASH MARGIN AND CLIENT CONTRIBUTION"
									||document.getElementById("TL_Colltrl_Dtls").options[document.getElementById("TL_Colltrl_Dtls").selectedIndex].text == "FD UNDER LIEN AND CLIENT CONTRIBUTION")){
								document.getElementById("TL_Client_Contr_Amnt").disabled = true;
								document.getElementById("TL_Client_Contr_Amnt").value = "";
								document.getElementById("TL_Client_Debit_Acc_Num").disabled = true;
								document.getElementById("TL_Client_Debit_Acc_Num").value = "";								
								document.getElementById("TL_Client_Contr_Crncy").value = "--Select--";
								document.getElementById("TL_Client_Contr_Crncy").disabled = true;
								}
							else{
								document.getElementById("TL_Client_Contr_Crncy").disabled = false;
								document.getElementById("TL_Client_Debit_Acc_Num").disabled = false;
								document.getElementById("TL_Client_Contr_Amnt").disabled = false;								
							}
							}catch(e){}
							
							try{
								if(!(document.getElementById("EC_Is_Disc_Under_bank").options[document.getElementById("EC_Is_Disc_Under_bank").selectedIndex].text=="Bank Line")){							
								document.getElementById("EC_Is_Accpt_msg_Auth").value = "--Select--";
								document.getElementById("EC_Is_Accpt_msg_Auth").disabled = true;
								}
							else{
								document.getElementById("EC_Is_Accpt_msg_Auth").disabled = false;								
								}
							}catch(e){}
							try{
								if(!(document.getElementById("EC_Is_Disc_Under_bank").options[document.getElementById("EC_Is_Disc_Under_bank").selectedIndex].text=="Customer Line")){							
								document.getElementById("EC_CA_prcng").value = "--Select--";
								document.getElementById("EC_CA_prcng").disabled = true;
								document.getElementById("EC_Is_TXN_Gt").value = "--Select--";
								document.getElementById("EC_Is_TXN_Gt").disabled = true;
								document.getElementById("EC_Is_Per_Party_Lmt").value = "--Select--";
								document.getElementById("EC_Is_Per_Party_Lmt").disabled = true;
								document.getElementById("EC_Is_Disc_As_Per_CA").value = "--Select--";
								document.getElementById("EC_Is_Disc_As_Per_CA").disabled = true;
								document.getElementById("EC_Is_Crdt_Avl").value = "--Select--";
								document.getElementById("EC_Is_Crdt_Avl").disabled = true;
								document.getElementById("EC_Buyer_Apprvd").value = "--Select--";
								document.getElementById("EC_Buyer_Apprvd").disabled = true;
								document.getElementById("EC_Is_Related_parties").value = "--Select--";
								document.getElementById("EC_Is_Related_parties").disabled = true;
								document.getElementById("EC_Is_Margin_Placd").value = "--Select--";
								document.getElementById("EC_Is_Margin_Placd").disabled = true;
								}
							else{
								document.getElementById("EC_CA_prcng").disabled = false;	
								document.getElementById("EC_Is_TXN_Gt").disabled = false;
								document.getElementById("EC_Is_Per_Party_Lmt").disabled = false;	
								document.getElementById("EC_Is_Disc_As_Per_CA").disabled = false;
								document.getElementById("EC_Is_Crdt_Avl").disabled = false;	
								document.getElementById("EC_Buyer_Apprvd").disabled = false;
								document.getElementById("EC_Is_Related_parties").disabled = false;	
								document.getElementById("EC_Is_Margin_Placd").disabled = false;
								
								}
							}catch(e){}
							
							try{
							if(!(document.getElementById("ELC_add_cnfm_along_adv").value == "Yes")){
								document.getElementById("ELC_tenor_not_exceeding").value = "--Select--";
								document.getElementById("ELC_tenor_not_exceeding").disabled = true;
								document.getElementById("ELC_spec_limit_line").value = "";
								document.getElementById("ELC_spec_limit_line").disabled = true;
								document.getElementById("ELC_req_within_limits").value = "--Select--";
								document.getElementById("ELC_req_within_limits").disabled = true;
								document.getElementById("ELC_parties_not_rel_ent").value = "--Select--";
								document.getElementById("ELC_parties_not_rel_ent").disabled = true;
								
								document.getElementById("ELC_chk_tolerance").value = "--Select--";
								document.getElementById("ELC_chk_tolerance").disabled = true;
								document.getElementById("ELC_send_TT_hilite_excep").value = "--Select--";
								document.getElementById("ELC_send_TT_hilite_excep").disabled = true;
								document.getElementById("ELC_Ensure_ICIEC_cred_appr_avl").value = "--Select--";
								document.getElementById("ELC_Ensure_ICIEC_cred_appr_avl").disabled = true;
								document.getElementById("ELC_Ens_ICIEC_cred_appr_cpy_inline").value = "--Select--";
								document.getElementById("ELC_Ens_ICIEC_cred_appr_cpy_inline").disabled = true;
								
								document.getElementById("ELC_Non_Cust_sig_veri_msg").value = "--Select--";
								document.getElementById("ELC_Non_Cust_sig_veri_msg").disabled = true;
								document.getElementById("ELC_Rvw_LC_Amend_Cnfm").value = "--Select--";
								document.getElementById("ELC_Rvw_LC_Amend_Cnfm").disabled = true;
								document.getElementById("ELC_Msg_Issu_Bank").value = "--Select--";
								document.getElementById("ELC_Msg_Issu_Bank").disabled = true;
								
							}
							else{
								document.getElementById("ELC_tenor_not_exceeding").disabled = false;	
								document.getElementById("ELC_spec_limit_line").disabled = false;
								document.getElementById("ELC_req_within_limits").disabled = false;	
								document.getElementById("ELC_parties_not_rel_ent").disabled = false;
								document.getElementById("ELC_chk_tolerance").disabled = false;	
								document.getElementById("ELC_send_TT_hilite_excep").disabled = false;
								document.getElementById("ELC_Ensure_ICIEC_cred_appr_avl").disabled = false;	
								document.getElementById("ELC_Ens_ICIEC_cred_appr_cpy_inline").disabled = false;
								document.getElementById("ELC_Non_Cust_sig_veri_msg").disabled = false;
								document.getElementById("ELC_Rvw_LC_Amend_Cnfm").disabled = false;	
								document.getElementById("ELC_Msg_Issu_Bank").disabled = false;


							}
							}catch(e){}
							
							try{
								if(!(document.getElementById("ELCB_Disc_Under").options[document.getElementById("ELCB_Disc_Under").selectedIndex].text == "Customer Line" || document.getElementById("ELCB_Disc_Under").options[document.getElementById("ELCB_Disc_Under").selectedIndex].text == "Clean Nego")){
									document.getElementById("ELCB_Trnsprt_Doc_Chck").value = "--Select--";
									document.getElementById("ELCB_Trnsprt_Doc_Chck").disabled = true;											
								} else {
									document.getElementById("ELCB_Trnsprt_Doc_Chck").disabled = false;
								}							
							}catch(e){}
							
							try{
								if(!(document.getElementById("ELCB_Disc_Under").options[document.getElementById("ELCB_Disc_Under").selectedIndex].text == "Bank Line")){
									document.getElementById("ELCB_Is_Auth_Message").value = "--Select--";
									document.getElementById("ELCB_Is_Auth_Message").disabled = true;											
								} else {
									document.getElementById("ELCB_Is_Auth_Message").disabled = false;
								}							
							}catch(e){}
							
							try{
								if(!(document.getElementById("ELCB_Disc_Under").options[document.getElementById("ELCB_Disc_Under").selectedIndex].text == "Bank Line")){
									document.getElementById("ELCB_Is_Auth_Message").value = "--Select--";
									document.getElementById("ELCB_Is_Auth_Message").disabled = true;											
								} else {
									document.getElementById("ELCB_Is_Auth_Message").disabled = false;
								}							
							}catch(e){}
							
							try{
								if(!(document.getElementById("ELCB_Disc_Under").options[document.getElementById("ELCB_Disc_Under").selectedIndex].text == "Customer Line")){
									document.getElementById("ELCB_Crdt_Insrnce").value = "--Select--";
									document.getElementById("ELCB_Crdt_Insrnce").disabled = true;
									document.getElementById("ELCB_Is_Req_lmt_CA").value = "--Select--";
									document.getElementById("ELCB_Is_Req_lmt_CA").disabled = true;
									document.getElementById("ELCB_Byer_Approvd").value = "--Select--";
									document.getElementById("ELCB_Byer_Approvd").disabled = true;										
									document.getElementById("ELCB_IS_related").value = "--Select--";
									document.getElementById("ELCB_IS_related").disabled = true;	
									document.getElementById("ELCB_Crdt_Rep_Rqrd").value = "--Select--";
									document.getElementById("ELCB_Crdt_Rep_Rqrd").disabled = true;	
								} else {
									document.getElementById("ELCB_Is_Req_lmt_CA").disabled = false;
									document.getElementById("ELCB_Byer_Approvd").disabled = false;
									document.getElementById("ELCB_IS_related").disabled = false;
									document.getElementById("ELCB_Crdt_Rep_Rqrd").disabled = false;
									document.getElementById("ELCB_Crdt_Insrnce").disabled = false;
								}							
							}catch(e){}
							try{
								if((document.getElementById("Coll_Dtls").options[document.getElementById("Coll_Dtls").selectedIndex].text == "CASH MARGIN AND CLIENT CONTRIBUTION")||(document.getElementById("Coll_Dtls").options[document.getElementById("Coll_Dtls").selectedIndex].text == "CLIENT CONTRIBUTION")){
									document.getElementById("ClntContAmnt").disabled = false;
										
								} else {
									document.getElementById("ClntContAmnt").value = "";
									document.getElementById("ClntContAmnt").disabled = true;
								}							
							}catch(e){}
							
							
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
											sColString += cols[i].innerHTML + "#";
										}
										else{
											sColString += cols[i].innerHTML + "#";
										}
										sColString=sColString.replace("&amp;", "&");
									}
									else{
										var sCellString=cols[i].innerHTML;
										console.log('sCellString='+sCellString);
										//alert('sCellString'+sCellString);
										var lengthTag = 0;
										lengthTag = sCellString.indexOf("id=");
										var tagnameStr=sCellString.substring(lengthTag);
										ID=tagnameStr.substring(4, tagnameStr.indexOf(" ")-1);
										//alert('ID'+tagnameStr.substring(3, tagnameStr.indexOf(" ")));
										var sTemp;
										var requestType="<%=sReqTypeCode%>";
							    var requestCat="<%=sReqCatCode%>";
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
													if(document.getElementById("Cash_mrgn_Perc").value >= 100 && requestCat=="GRNT" && requestType=="NI"){
													}
													else {
													alert("Please "+sControl+" mandatory details ");
													document.getElementById(ID).focus();
													return false;
													}
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
									
								/* var lmt_line=document.getElementById("Limt_Line").value
								if(requestCat=="GRNT" && requestType=="NI")
{
	if(document.getElementById("Cash_mrgn_Perc").value < 100 || document.getElementById("Cash_mrgn_Perc").value="" || document.getElementById("Fxd_Dep_perc").value < 100 || document.getElementById("Fxd_Dep_perc").value="")
		{
			if(lmt_line =="" || lmt_line == " "){
					if(sCellString.indexOf("disabled") < 0)
					{
				    alert("Please enter Limit Line ");
					document.getElementById(ID).focus();
					return false;
			        }
	        }
	    }
}*/
									window.returnValue=sColString;
									//window.opener.getJSPLimitData(sColString);
									window.parent.handleJSPResponsePPM('LmtCrdt',sColString);	
									window.close();							
						}
						
			//function escapeRegExp(str){}
						function checkNumericValidation(ControlValue, ControlName){
							try{
								var value1 = ControlValue;
								var digit=parseFloat(value1);
								var requestType="<%=sReqTypeCode%>";
							    var requestCat="<%=sReqCatCode%>";
								if(requestCat=="GRNT" && requestType=="NI" && "Cash_mrgn_Perc"==ControlName || "Fxd_Dep_perc"==ControlName){
									
									if(!(ControlValue=="")){
										if(isNaN(digit)){
										alert("Not a valid number");
										document.getElementById(ControlName).value="";
									}
									else if(value1>999 ){
									    document.getElementById(ControlName).value = "";
										alert("limit exceeded more than 999");
										document.getElementById("Limt_Line").disabled =false;
									}
									else if(value1 >= 100 && value1 <= 999){
										document.getElementById(ControlName).value = digit;
										//document.getElementById("Limt_Line").disabled = true;
									//	document.getElementById("Limt_Line").value = "";
									}
									else if(value1.match(/^\d+(?:\.\d{0,2})?$/) && value1 < 1000){
										document.getElementById(ControlName).value = digit;
										document.getElementById("Limt_Line").disabled =false;
									}
									
									else {
									    document.getElementById(ControlName).value = "";
										alert("precision limit exceeded more than 2");
									}
								
									
									}
								}
								else if("ILC_Cash_Mrgn_Prcnt"==ControlName ||"Cash_mrgn_Perc"==ControlName ){
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
								}}
							}
							catch(e){
								
							}
						}
					</script>
				</table>
											<Center><button type="button" class="buttonStyle button-viewer" onclick='importData();'><b>Import</b></button></Center>
			</div>
		</div>
	</FORM>
</body>
</html>
