<%@page import="java.util.*,java.text.*"%>
<%@page import="com.newgen.wfdesktop.xmlapi.*,java.io.*,java.sql.*,java.lang.*,java.util.*,com.newgen.wfdesktop.session.*"%>
<%@page import="java.text.*"%>
<%@page import="com.newgen.wms.edms.service.*"%>
<%@page import="com.newgen.omni.wf.util.app.*"%> 
<jsp:useBean id="customSession" class="com.newgen.custom.wfdesktop.session.WFCustomSession" scope="session"/>
<%!
String wiName= "";
String sCabname="";
String sSessionId="";
String sUserName="";
String sJtsIp="";
String iJtsPort="";
String sInputXml="";
String sOutputXml="";
String sResult="";
String docApiRes="";
%>
<%
String sStatus ="";
wiName = request.getParameter("pid");
String docIndex = request.getParameter("docindex");
String tempDoc = request.getParameter("docName");
String docName="";

sCabname=customSession.getEngineName();
sSessionId =customSession.getDMSSessionId();
sUserName = customSession.getUserName();
sJtsIp = customSession.getJtsIp();
iJtsPort = String.valueOf(customSession.getJtsPort());

String leadRefNo="";
String custName="";
String docMode="";
String custId="";
String count = "";
String entityType="";
String folderpath = "";
String dataclass = "";
String dataclassvalue = "";
String docdc = "";
String docName1=""; 
String accessType ="";
String documentType="" ;
String imageIndex ="";
String volumeId ="" ;
String noofPages ="" ;
String documentSize ="" ;
String appName ="" ;
String commnt ="";
String sQuery="";
String oldDocIndex="";
		try
		{
			docName=docNameCheck(tempDoc);
			if("Signature".equalsIgnoreCase(docName)){
				sInputXml=prepareAPProcInputXml("SP_WBG_IMPORT_DOC","'"+wiName+"','"+docIndex+"','"+docName+"'",sCabname,sSessionId);
				WriteToLog_showpage("Y",sInputXml);
				sOutputXml=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXml);
				WriteToLog_showpage("Y",sOutputXml);
				String str="<MainCode>";
				sResult=sOutputXml.substring(sOutputXml.indexOf("<MainCode>")+10,sOutputXml.indexOf("</MainCode>"));
				WriteToLog_showpage("Y",sResult);			
				if("0".equalsIgnoreCase(sResult))
					out.println("success");
				else{
					out.println("fail");
				}
			}else{
				sQuery = "SELECT LEAD_REF_NO,CUSTFULLNAME,CUSTID FROM EXT_WBG_AO WHERE WI_NAME =N'"+wiName+"'";
				sInputXml = prepareAPSelectInputXml( sQuery, sCabname, sSessionId );
				WriteToLog_showpage("Y",sInputXml);
				sOutputXml=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXml);
				WriteToLog_showpage("Y",sOutputXml);
				
				sResult=sOutputXml.substring(sOutputXml.indexOf("<MainCode>")+10,sOutputXml.indexOf("</MainCode>"));
				WriteToLog_showpage("Y",sResult);			
				if("0".equalsIgnoreCase(sResult)){
					
					leadRefNo = sOutputXml.substring(sOutputXml.indexOf("<LEAD_REF_NO>")+13,sOutputXml.indexOf("</LEAD_REF_NO>"));
					custName = sOutputXml.substring(sOutputXml.indexOf("<CUSTFULLNAME>")+14,sOutputXml.indexOf("</CUSTFULLNAME>"));
					custId = sOutputXml.substring(sOutputXml.indexOf("<CUSTID>")+8,sOutputXml.indexOf("</CUSTID>"));
					
					if(null != leadRefNo && !"".equalsIgnoreCase(leadRefNo))
					{
						sQuery = "SELECT COUNT(1) CNT  FROM USR_0_WBG_AO_DOC WHERE LEAD_REFNO =N'"+leadRefNo+"' AND DOCUMENTNAME=N'"+docName+"'";
						sInputXml = prepareAPSelectInputXml( sQuery, sCabname, sSessionId );
						WriteToLog_showpage("Y",sInputXml);
						sOutputXml=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXml);
						WriteToLog_showpage("Y",sOutputXml);
						
						sResult=sOutputXml.substring(sOutputXml.indexOf("<MainCode>")+10,sOutputXml.indexOf("</MainCode>"));
						WriteToLog_showpage("Y",sResult);
						if("0".equalsIgnoreCase(sResult))
						{
							//out.println("success");
							count = sOutputXml.substring(sOutputXml.indexOf("<CNT>")+5,sOutputXml.indexOf("</CNT>"));
							if(null != count && !"".equalsIgnoreCase(count))
							{
								if("0".equalsIgnoreCase(count))
								{
									docMode = "ADD";
								}
								else
								{								
									sQuery = "SELECT DOCUMENTINDEX FROM USR_0_WBG_AO_DOC WHERE LEAD_REFNO =N'"+leadRefNo+"' AND DOCUMENTNAME=N'"+docName+"'";
									WriteToLog_showpage("Y",sQuery);
									sInputXml = prepareAPSelectInputXml( sQuery, sCabname, sSessionId );
									WriteToLog_showpage("Y",sInputXml);
									sOutputXml=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXml);
									WriteToLog_showpage("Y",sOutputXml);								
									sResult=sOutputXml.substring(sOutputXml.indexOf("<MainCode>")+10,sOutputXml.indexOf("</MainCode>"));
									WriteToLog_showpage("Y",sResult);
									if("0".equalsIgnoreCase(sResult))
									{
										oldDocIndex=sOutputXml.substring(sOutputXml.indexOf("<DOCUMENTINDEX>")+15,sOutputXml.indexOf("</DOCUMENTINDEX>"));
									}
									docMode = "MODIFY";
								}
							}	
							
						}
						else
						{
							out.println("fail");
						}
					}
				}
				else
				{
					out.println("fail");
				}
				
				
				sQuery = "SELECT FOLDERPATH, DATACLASS, DATACLASSVALUE, DOCDC, ENTITY_TYPE FROM USR_0_WMS_EDMS_FOLD_STR WHERE DOC='"+docName+"' AND PROCESSNAME='WBG_AO'";
				sInputXml = prepareAPSelectInputXml( sQuery, sCabname, sSessionId );
				WriteToLog_showpage("Y",sInputXml);
				sOutputXml=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXml);
				WriteToLog_showpage("Y",sOutputXml);			
				sResult=sOutputXml.substring(sOutputXml.indexOf("<MainCode>")+10,sOutputXml.indexOf("</MainCode>"));
				WriteToLog_showpage("Y",sResult);
				if("0".equalsIgnoreCase(sResult))
				{
					entityType = sOutputXml.substring(sOutputXml.indexOf("<ENTITY_TYPE>")+13,sOutputXml.indexOf("</ENTITY_TYPE>"));
					folderpath = sOutputXml.substring(sOutputXml.indexOf("<FOLDERPATH>")+12,sOutputXml.indexOf("</FOLDERPATH>"));
					dataclass = sOutputXml.substring(sOutputXml.indexOf("<DATACLASS>")+11,sOutputXml.indexOf("</DATACLASS>"));
					dataclassvalue = sOutputXml.substring(sOutputXml.indexOf("<DATACLASSVALUE>")+16,sOutputXml.indexOf("</DATACLASSVALUE>"));
					docdc = sOutputXml.substring(sOutputXml.indexOf("<DOCDC>")+7,sOutputXml.indexOf("</DOCDC>"));
					Map<String,String>	mapDcValue = new HashMap<String,String>();
					mapDcValue = dcClassValues(dataclassvalue);			
					String[] folderStructureValue = getFolderStructureValue( folderpath,wiName,leadRefNo ).split("/");	
					String docQuery ="SELECT NAME DCONAME,ACCESSTYPE,DOCUMENTTYPE,IMAGEINDEX,VOLUMEID,NOOFPAGES,DOCUMENTSIZE,APPNAME,COMMNT FROM PDBDOCUMENT WHERE DOCUMENTINDEX=" + Integer.parseInt(docIndex);
					sInputXml = prepareAPSelectInputXml( docQuery, sCabname, sSessionId );
					WriteToLog_showpage("Y",sInputXml);
					sOutputXml=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXml);
					WriteToLog_showpage("Y",sOutputXml);
					if("0".equalsIgnoreCase(sOutputXml.substring(sOutputXml.indexOf("<MainCode>")+10,sOutputXml.indexOf("</MainCode>"))))
					{
						docName1=sOutputXml.substring(sOutputXml.indexOf("<DCONAME>")+9,sOutputXml.indexOf("</DCONAME>"));
						accessType=sOutputXml.substring(sOutputXml.indexOf("<ACCESSTYPE>")+12,sOutputXml.indexOf("</ACCESSTYPE>"));
						documentType= sOutputXml.substring(sOutputXml.indexOf("<DOCUMENTTYPE>")+14,sOutputXml.indexOf("</DOCUMENTTYPE>"));
						imageIndex=sOutputXml.substring(sOutputXml.indexOf("<IMAGEINDEX>")+12,sOutputXml.indexOf("</IMAGEINDEX>"));
						volumeId=sOutputXml.substring(sOutputXml.indexOf("<VOLUMEID>")+10,sOutputXml.indexOf("</VOLUMEID>"));
						noofPages=sOutputXml.substring(sOutputXml.indexOf("<NOOFPAGES>")+11,sOutputXml.indexOf("</NOOFPAGES>"));					
						documentSize=sOutputXml.substring(sOutputXml.indexOf("<DOCUMENTSIZE>")+14,sOutputXml.indexOf("</DOCUMENTSIZE>"));
						appName=sOutputXml.substring(sOutputXml.indexOf("<APPNAME>")+9,sOutputXml.indexOf("</APPNAME>"));
						commnt=sOutputXml.substring(sOutputXml.indexOf("<COMMNT>")+8,sOutputXml.indexOf("</COMMNT>"));
						
						WriteToLog_showpage("Y",docName1+"**"+accessType+"**"+documentType+"**"+imageIndex+"**"+volumeId+"**"+noofPages+"**"+documentSize+"**"+appName+"**"+commnt+"**"+folderStructureValue.toString()+"**"+sCabname+"**"+sSessionId+"**"+sUserName+"**"+leadRefNo+"**"+docMode+"**"+docdc+"**"+mapDcValue);
						PushToEDMS pushDoc = new PushToEDMS();
						docApiRes=pushDoc.addDocument( sCabname,sSessionId, sUserName, leadRefNo, custName, docMode,  folderStructureValue, mapDcValue,  docdc, noofPages,  docName1, documentType, documentSize, appName, imageIndex+"#"+volumeId+"#", commnt,oldDocIndex,entityType,custId,wiName );
						WriteToLog_showpage("Y",docApiRes);
					}
				}
				else
				{
					out.println("fail");
				}
				
				sInputXml=prepareAPProcInputXml("SP_WBG_IMPORT_DOC","'"+wiName+"','"+docIndex+"','"+docName+"'",sCabname,sSessionId);
				WriteToLog_showpage("Y",sInputXml);
				sOutputXml=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXml);
				WriteToLog_showpage("Y",sOutputXml);
				String str="<MainCode>";
				sResult=sOutputXml.substring(sOutputXml.indexOf("<MainCode>")+10,sOutputXml.indexOf("</MainCode>"));
				WriteToLog_showpage("Y",sResult);			
				if("0".equalsIgnoreCase(sResult)){
					if(null != docApiRes && !"".equalsIgnoreCase(docApiRes)){
						
						if("S".equalsIgnoreCase((docApiRes.trim()).split("##")[0])){
							String edmsDocIndex = (docApiRes.trim()).split("##")[2];
							sInputXml = prepareAPUpdateInputXml("USR_0_WBG_AO_DOC","DOCUMENTINDEX","'"+edmsDocIndex+"'","wi_name =N'"+wiName+"' AND wms_documentindex=N'"+docIndex+"'",sCabname,sSessionId);
							WriteToLog_showpage("Y",sInputXml);
							sOutputXml=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXml);
							WriteToLog_showpage("Y",sOutputXml);
							sResult=sOutputXml.substring(sOutputXml.indexOf("<MainCode>")+10,sOutputXml.indexOf("</MainCode>"));
							if("0".equalsIgnoreCase(sResult)){
								out.println("success");
							}
							else{
								out.println("fail");
							}	
											
						}else{
							out.println("fail");
						}
					
					}
					else{
						out.println("fail");
					}
				}
				else{
						out.println("fail");
				}
			}
		} 
/*//ERROR MOKSH		catch(SQLException ex)
		{
			System.err.println(ex.getMessage());
		}*/
		catch(Exception e)
		{
			e.printStackTrace();
		}		

%>				
<%!
private String prepareAPProcInputXml(String ProcName,String ProcParams, String sCabname, String sSessionId){	
	return "<?xml version=\"1.0\"?><APProcedure_Input><Option>APProcedure</Option><ProcName>"+ProcName+"</ProcName><Params>"+ProcParams+"</Params><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APUpdate_Input>";
}
private String prepareAPSelectInputXml(String sQuery, String sCabname, String sSessionId){	
	return "<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+sQuery+"</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
}
private String prepareAPUpdateInputXml(String tableName,String colname,String sValues,String whrcls, String sCabname, String sSessionId){	
	return "<?xml version=\"1.0\"?><APUpdate_Input><Option>APUpdate</Option><TableName>"+tableName+"</TableName><ColName>"+colname+"</ColName><Values>"+sValues+"</Values><WhereClause>"+whrcls+"</WhereClause><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APUpdate_Input>";
}
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
			strFilePath.append("ImportDoc");
			strFilePath.append(File.separatorChar);
			File test1 = new File(strFilePath.toString());
			if (!(test1.exists())) {
				test1.mkdirs();
			}
			strFilePath.append("ImportDoc"+DtString+".xml");
			tmpFilePath = strFilePath.toString();			
			BufferedWriter out = new BufferedWriter(new FileWriter(tmpFilePath, true));
			out.write(str.toString());
			out.close();			
		}
    } catch (Exception exception) {
		StackTraceToString_showpage(exception);
    } finally {
        strFilePath = null;
    }   
}
private static String StackTraceToString_showpage(Exception e) {
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        e.printStackTrace(printWriter);
        return result.toString();
}
private String docNameCheck(String doc){        
            if(doc.indexOf("(") >-1){                 
                  return doc.substring(0,doc.indexOf("("));             
            }else{                  
                  return doc;
            }           
}

public Map<String,String> dcClassValues(String dcValues)throws Exception{
		 Map<String,String> map=null;
		String query=dcClassQuery(dcValues);
		String sInputXml = prepareAPSelectInputXml( query, sCabname, sSessionId );
		WriteToLog_showpage("Y",sInputXml);
		String sOutputXml = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXml);
		WriteToLog_showpage("Y",sOutputXml);
		String DCVal=sOutputXml.substring(sOutputXml.indexOf("<DCVAL>")+7,sOutputXml.indexOf("</DCVAL>"));
		map =retMapDcVal(DCVal);		
		return map;
	}
	public String dcClassQuery(String dcValues){
		String[] DCval=dcValues.split("#");
		String DCQUERY="";
		for(int j=0;j<DCval.length;j++)	{
			if(DCval[j].contains("$"))	{
				String tmp=DCval[j].replace("=$", "='||");
				tmp=tmp.replace("$", "||'#'||");
				DCQUERY+="'"+tmp+"'";
			}else{
				String tmp=DCval[j]+"#'||";	    				
				DCQUERY+="'"+tmp+"'";
			}
		}	
		DCQUERY=DCQUERY.replace("''", "'");
		return "SELECT "+DCQUERY.substring(0,DCQUERY.length()-3)+" AS DCVAL FROM EXT_WBG_AO WHERE WI_NAME =N'"+wiName+"'";
		
	}
	public String getFolderStructureValue(String folderPath,String wi_name,String leadNo)throws Exception{
		WriteToLog_showpage("Y","************************************getFolderStructureValue******************************************");
		  String[] folder=folderPath.split("/");
            String folder_path="";
            for(int j=0;j<folder.length;j++){               
                  if(folder[j].contains("$")){                    
                        String tmp=folder[j].replace("=$", "'||");
                        tmp=tmp.replace("$", " ||'");                   
                        folder[j]=tmp;                      
                  }
                  folder[j]=folder[j].replaceAll("\n", "").trim();
                  if("".equalsIgnoreCase(folder[j])){
                        continue;
                  }
                  folder_path+=folder[j]+"/";
            }
        folder_path="'"+folder_path+"'";
		WriteToLog_showpage("Y",folder_path);
		String foldPath=getFolderValues(folder_path,wi_name);
		
		return foldPath;
	}
	private String getFolderValues(String val,String wi_name)throws Exception{
		WriteToLog_showpage("Y","************************************getFolderValues******************************************");
		String folderVal="";
		String query ="SELECT "+val+" as EXT_VAL FROM EXT_WBG_AO EXT_WBG_AO WHERE WI_NAME ='"+ wi_name +"'";	
		String sInputXml = prepareAPSelectInputXml( query, sCabname, sSessionId );
		WriteToLog_showpage("Y",sInputXml);
		String sOutputXml=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputXml);
		WriteToLog_showpage("Y",sOutputXml);
		if("0".equalsIgnoreCase(sOutputXml.substring(sOutputXml.indexOf("<MainCode>")+10,sOutputXml.indexOf("</MainCode>")))){
			folderVal=sOutputXml.substring(sOutputXml.indexOf("<EXT_VAL>")+9,sOutputXml.indexOf("</EXT_VAL>"));
		}
		WriteToLog_showpage("Y",folderVal);
		
		return folderVal;
	}
	
	public Map<String,String> retMapDcVal(String DCVal){		
		String[] dcvalues=DCVal.split("#");
		Map<String,String> dcValues = new HashMap<String, String>();
    	for(int i=0;i<dcvalues.length;i++){
    		
    		if(dcvalues[i].equalsIgnoreCase("")){
    			continue;
    		}else{
	    		String[] val=dcvalues[i].split("=");
	    		if(val.length==1){
	    			dcValues.put(val[0], "");
	    		}else{
	    			dcValues.put(val[0], val[1]);
	    		}
    		}
    	}
    	return dcValues;
	}

%>			