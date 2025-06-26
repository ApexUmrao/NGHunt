
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.io.IOException"%>
<%@page import="com.newgen.wfdesktop.xmlapi.WFAttribParser"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="com.newgen.iforms.util.IFormINIConfiguration"%>
<%@page import="com.newgen.iforms.controls.util.IFormConstants"%>
<%@page import="com.newgen.iforms.webapp.AppTasks"%>
<%@page import="com.newgen.iforms.nav.Menu"%>
<%@page import="com.newgen.iforms.util.CommonUtility"%>
<%@page import="com.newgen.iforms.nav.IFormFragInfo"%>
<%@page import="com.newgen.iforms.util.AESEncryption"%>
<%@page import="java.net.URLDecoder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.io.File"%>
<%@page import="com.newgen.iforms.custom.IFormContext"%>
<%@page import="com.newgen.iforms.custom.IFormServerEventHandler"%>
<%@page import="com.newgen.json.JSONArray"%>
<%@page import="com.newgen.iforms.custom.IFormAPIHandler"%>
<%@page import="com.newgen.iforms.custom.IFormReference"%>
<%@page import="com.newgen.iforms.controls.EButtonControl"%>
<%@page import="com.newgen.iforms.controls.ERootControl"%>
<%@page import="java.util.Locale"%>
<%@page import="com.newgen.iforms.util.IFormUtility"%>
<%@page import="com.newgen.iforms.controls.util.EControlHelper"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.newgen.mvcbeans.controller.workdesk.WDWorkitems"%>
<%@page import="com.newgen.mvcbeans.model.WorkdeskModel"%>
<%@page import="com.newgen.iforms.util.StringUtils"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="com.newgen.iforms.session.IFormSession"%>
<%@page import="com.newgen.iforms.viewer.IFormViewer"%>
<%@page import="java.util.ResourceBundle"%>
<%@page import="nu.xom.Node"%>
<%@page import="nu.xom.*"%>
<%@page import="com.newgen.commonlogger.*"%>
<%@ include file="header.jsp"%>
<%
ResourceBundle lbls = ResourceBundle.getBundle("ifgen",request.getLocale());
%>
<!DOCTYPE html>
<html dir=<%= lbls.getString("HTML_DIR") %> >
    <head>
        <%
            request.setCharacterEncoding("UTF-8");
            response.setHeader("Cache-Control", "no-store"); //HTTP 1.1
            response.setHeader("Pragma", "no-cache"); //HTTP 1.0
            response.setDateHeader("Expires", -1); //prevents caching at the proxy server
            boolean isPWA = "Y".equals(AppTasks.getValueFromINI("PWAEnabled", request));
            String mobileType = request.getParameter("mobileMode");
               if( mobileType == null || mobileType.equals("") )
                mobileType = "mobile";
               
            String ApplicationType = request.getParameter("FromApplication");
            if( ApplicationType == null || ApplicationType.equals("") )
                ApplicationType = "iforms";
             
            
            StringBuilder designMenu = new StringBuilder("");
            String callFrom = request.getParameter("callFrom");
            WorkdeskModel wdmodel = null;
            IFormSession formsession = null;
            String pid = ""; 
            String wid = null;
            String formBufferPath = null;
            String additionalReqParams = null;
            String extraParams="";
            if(ApplicationType.equals("pms") && request.getParameter("processInstanceId")!=null && request.getParameter("processInstanceId")!=""){
                 pid=IFormUtility.escapeHtml4(URLDecoder.decode(request.getParameter("processInstanceId"),"UTF-8"));
                 wid = (String)IFormUtility.escapeHtml4(request.getParameter("workItemId"));
                 additionalReqParams=(String)request.getParameter("additionalParams");
                 extraParams="";
                 if(additionalReqParams!=null){
                    extraParams=IFormUtility.populateAdditionalParams(additionalReqParams);
                 }
            }
            else if(IFormUtility.escapeHtml4(request.getParameter("pid"))!=null && IFormUtility.escapeHtml4(request.getParameter("pid"))!="") {
                pid= IFormUtility.escapeHtml4(URLDecoder.decode(request.getParameter("pid"),"UTF-8"));
                wid = (String) IFormUtility.escapeHtml4(request.getParameter("wid"));
            }
            
            
            String tid = IFormUtility.getIFormTaskId(request);   
            String fSessionId="";
            String pageformUID="";
            String fid = "";
            if(IFormUtility.escapeHtml4(request.getParameter("fid"))!=null)
                pageformUID= IFormUtility.escapeHtml4(URLDecoder.decode(request.getParameter("fid"),"UTF-8"));
            else
                pageformUID = "Form";
            
            //Bug 87917 
            if(pid == null || pid == ""){
                HttpSession session1=request.getSession(true);
            }
			if(ApplicationType.equals("pms") || mobileType.equals("android") || mobileType.equals("ios")){
                fid=pageformUID+"_"+pid+"_"+wid;
                if(!"".equals(tid))
                    fid=fid+"_"+tid;
                fSessionId="i"+fid+"Session";
            } else {
                pageformUID=pageformUID+"_"+pid+"_"+wid;
                if(!"".equals(tid))
                    pageformUID=pageformUID+"_"+tid;
                fSessionId="i"+pageformUID+"Session";
            }
            
            
            if (session.getAttribute(fSessionId) == null) {
                formsession = new IFormSession(request);
                session.setAttribute(fSessionId, formsession);
            } else {
                if(!ApplicationType.equals("pms") && (mobileType.equals("android") || mobileType.equals("ios")) && ("Form".equals(pageformUID) || "TemplateTaskForm".equals(pageformUID) || "CustomTemplateTaskForm".equals(pageformUID) || "DataTemplateTaskForm".equals(pageformUID)))
                    formsession = (IFormSession) session.getAttribute(IFormUtility.getIFormSessionUID(request));
                else
                    formsession = (IFormSession) session.getAttribute(fSessionId);
            }   
            if( !ApplicationType.equals("pms") && mobileType.equals("mobile")){
                if(request.getParameter("ExtendSession")!=null && !"".equalsIgnoreCase(request.getParameter("ExtendSession")) && "Y".equalsIgnoreCase(request.getParameter("ExtendSession"))){
                    CommonUtility.extendLogin(request,response);
                } 
                else if(callFrom != null && session.getAttribute("FormPath")==null){
                      CommonUtility.executeLogin(request, response);
                }
            }
            
            if(ApplicationType.equals("pms")){
                if(request.getParameter("SessionId")!=null)
                  formsession.setM_strSessionId(request.getParameter("SessionId"));
                else if(request.getParameter("authId")!=null) 
                    formsession.setM_strSessionId(request.getParameter("authId"));
                if(session.getAttribute("iFormWorkitemCount")==null){
                    session.setAttribute("iFormWorkitemCount",0);
                }
                else{
                    int updatedWICount = Integer.parseInt(session.getAttribute("iFormWorkitemCount").toString())+1;
                    session.setAttribute("iFormWorkitemCount",updatedWICount);
                 }
            }
            String alwaysCreateNew="";
            String applicationName="";
            String DeleteOldApplicationData="";
            String CreateTransactionOnLoad="";
            if( !ApplicationType.equals("pms") && mobileType.equals("mobile") )  {
                if(AppTasks.getINIMap(request) != null ){
                  applicationName= AppTasks.getValueFromINI("ApplicationName", request);
                  formsession.setM_strApplicationName(AppTasks.getValueFromINI("ApplicationName", request));
                } 
                if(AppTasks.getINIMap(request) != null ){
                    if(AppTasks.getINIMap(request).get("CreateNewApplication")!=null)
                       alwaysCreateNew=AppTasks.getValueFromINI("CreateNewApplication", request);
                    if(AppTasks.getINIMap(request).get("DeleteOldApplicationData")!=null)
                       DeleteOldApplicationData=AppTasks.getValueFromINI("DeleteOldApplicationData", request);
                    if(AppTasks.getINIMap(request).get("CreateTransactionOnLoad")!=null)
                       CreateTransactionOnLoad=AppTasks.getValueFromINI("CreateTransactionOnLoad", request);
                }    
            }
            
            boolean isEditableComboOnLoad=(IFormINIConfiguration.getM_hINIHashMap().get(IFormConstants.EDITABLE_COMBO_ON_LOAD)==null)||(IFormINIConfiguration.getM_hINIHashMap().get(IFormConstants.EDITABLE_COMBO_ON_LOAD) != null && "Y".equals(IFormINIConfiguration.getM_hINIHashMap().get(IFormConstants.EDITABLE_COMBO_ON_LOAD)));
            boolean isShowGridComboLabel = false;
            if(mobileType.equals("mobile"))
                isShowGridComboLabel=(IFormINIConfiguration.getM_hINIHashMap().get(IFormConstants.SHOW_GRID_COMBO_LABEL) != null && "Y".equals(IFormINIConfiguration.getM_hINIHashMap().get(IFormConstants.SHOW_GRID_COMBO_LABEL)));
            
            boolean autoIncrementLabelDisplay=(IFormINIConfiguration.getM_hINIHashMap().get(IFormConstants.SHOW_AUTO_INCREMENT_LABEL) != null && "Y".equals(IFormINIConfiguration.getM_hINIHashMap().get(IFormConstants.SHOW_AUTO_INCREMENT_LABEL)));
            String listViewCharacterLimit =IFormINIConfiguration.getM_hINIHashMap().get(IFormConstants.LISTVIEW_CHARACTER_LIMIT);
            boolean multipleRowDuplicate=(IFormINIConfiguration.getM_hINIHashMap().get(IFormConstants.MULTIPLE_ROW_DUPLICATE) != null && "Y".equals(IFormINIConfiguration.getM_hINIHashMap().get(IFormConstants.MULTIPLE_ROW_DUPLICATE)));
            boolean isGridBatchingEnabled = false;
            boolean isReadOnlyForm = false;
            boolean useCustomIdAsControlName=(IFormINIConfiguration.getM_hINIHashMap().get(IFormConstants.USE_CUSTOM_ID_AS_CONTROL_NAME) != null && "Y".equals(IFormINIConfiguration.getM_hINIHashMap().get(IFormConstants.USE_CUSTOM_ID_AS_CONTROL_NAME)));
            formBufferPath=(String) IFormUtility.escapeHtml4(request.getParameter("FormDir"));
            String dateFormat = "dd/MMM/yyyy";
            String dateDbFormat="";
            String dateSeparator="";
            String activityName="";
            String processName="";
            String processDefId="";		
            String processInstanceId = "";
            String cabinetName = "";
            String appServerIp = "";
            String appServerPort = "";
            String userName = "";
            String activityID = "";
            String userIndex = "";
            String sessionID = "";
            String mobileMode = "";
            String isDatePicker=IFormINIConfiguration.getM_hINIHashMap().get(IFormConstants.HIDE_DATEPICKER_ON_DISABLE);
            String isOverlayOpen=IFormINIConfiguration.getM_hINIHashMap().get(IFormConstants.RESTRICT_CLOSE_OVERLAY);
            String CleanMapOnCloseModal=IFormINIConfiguration.getM_hINIHashMap().get(IFormConstants.CLEAN_MAP_ON_CLOSE_MODAL);
            String disableFieldFontColor=IFormINIConfiguration.getM_hINIHashMap().get(IFormConstants.DISABLE_FIELD_FONT_COLOR);
            String subTaskId = "";
            String taskName = "";
            String calledForProcessReport=IFormUtility.escapeHtml4(request.getParameter("calledForProcessReport"));
            processDefId=request.getParameter("ProcessDefId");
            String registeredMode=request.getParameter("RegisteredMode");
            String mode=isReadOnlyForm?"R":"N";
            String formMode="W";//Bug 82321 
            String folderId = "";
            String formName=""; 
            String assignedTo = "";
            String priorityLevel = "";
            String lockedByName = "";
            String processedBy = "";
            String introductionDateTime = "";
//            String introducedAt = "";
            String queueId = "";
            String lockStatus = "";
//            String userEmailId = "";
            String userPersonalName = "";
            String userFamilyName = "";
            String pmwebtemp="";
            if(calledForProcessReport!=null){
                if(request.getParameter("FormName")!=null)
                  formName=IFormUtility.escapeHtml4(URLDecoder.decode(request.getParameter("FormName"),"UTF-8"));
                formName=IFormUtility.sanitizeForProcess(formName,true);
                pmwebtemp=IFormUtility.escapeHtml4(request.getParameter("pmwebtemp"));
                pmwebtemp=IFormUtility.sanitizeForProcess(pmwebtemp,true);
                File finput = new File(pmwebtemp + File.separator + formsession.getM_strCabinetName() +File.separator+formsession.getM_strProcessDefId()+File.separator+formName+File.separator+formName+".xml");              
                formBufferPath=finput.getAbsolutePath();
            }
            String filterValue = "" , isNew = "";
            if(!ApplicationType.equals("pms") && mobileType.equals("mobile") ){
                applicationName= AppTasks.getValueFromINI("ApplicationName", request);
            }
            if( !ApplicationType.equals("pms") && mobileType.equals("mobile") )  {
                 filterValue = IFormUtility.escapeHtml4(request.getParameter("filterValue"));
                 isNew = IFormUtility.escapeHtml4(request.getParameter("isNew"));  
            }
            //filterValue = "1001";
     if( ApplicationType.equals("pms") ){
                FileOutputStream fosProcess=null;
            try {
                String processDataDir=request.getParameter("ProcessDataDir");
                String processvariablesxml ="";
                File f = new File(processDataDir);
                File processVarXMLFolder = f.getParentFile();
                    if (processVarXMLFolder != null && !processVarXMLFolder.exists()) {
                        processVarXMLFolder.mkdirs();
                    }
                if(f!=null && !f.exists()){
                        String fetchedProcessVariableXML=IFormUtility.getProcesVariableXML(request);
                        processvariablesxml=fetchedProcessVariableXML;
                        f.createNewFile();
                        fosProcess = new FileOutputStream(f);
                        fosProcess.write(fetchedProcessVariableXML.getBytes("UTF-8"));
                        
                }
                else if (f != null && f.exists()) {
                    Builder build = new Builder();
                    Document doc = null;
                    try
                    {
                        doc = build.build(f);
                    }
                    catch(Exception ex){    

                    }
                    if( doc == null )
                    return;
                    processvariablesxml=doc.toXML();
                 }
                String strAttributeDataXml = URLDecoder.decode(request.getParameter("AttributeData"),"UTF-8");
               
               String generalData=URLDecoder.decode(request.getParameter("generaldata"),"UTF-8");
               formsession.setWdtemptempPath(formBufferPath);
               WDWorkitems wisessionbean = (WDWorkitems) session.getAttribute("wDWorkitems");
               if(wisessionbean==null){
                    wisessionbean = new WDWorkitems();
                    session.setAttribute("wDWorkitems", wisessionbean);
                }
               if("Form".equals(pageformUID) || "TaskForm".equals(pageformUID)  ){
                    WFAttribParser.prepareWorkitemModel(request, processvariablesxml, strAttributeDataXml, generalData,formsession);                  
                
                   if (wisessionbean != null) {
                        LinkedHashMap workitemMap = wisessionbean.getWorkItems();  
                        if(tid==null || tid.isEmpty()){
                           wdmodel = (WorkdeskModel) workitemMap.get(pid + "_" + wid);
						   isReadOnlyForm = "R".equals(wdmodel.getM_objGeneralData().getM_strMode());
                       } else {
                            wdmodel = (WorkdeskModel) workitemMap.get(pid + "_" + wid + "_" + tid);
							if(wdmodel != null ){
								String strMode = wdmodel.getM_objGeneralData().getM_strMode();
								if(!"R".equals(strMode)){
									strMode = wdmodel.getWfForm().getFormViewModeForTask();   
									if("R".equals(strMode)){
										wdmodel.getM_objGeneralData().setM_strMode("R");
									}
								}
								isReadOnlyForm = "R".equals(strMode);							
							}
                        }                    
                        activityName=wdmodel.getM_objGeneralData().getM_strActivityName();
                        processName=wdmodel.getM_objGeneralData().getM_strProcessName();
                        processInstanceId = wdmodel.getM_objGeneralData().getM_strProcessInstanceId();
                        cabinetName = wdmodel.getM_objGeneralData().getM_strEngineName();
                        folderId = wdmodel.getM_objGeneralData().getM_strFolderId();
                        appServerIp = wdmodel.getM_objGeneralData().getM_strJTSIP();
                        appServerPort = wdmodel.getM_objGeneralData().getM_strJTSPORT();
                        userName =wdmodel.getM_objGeneralData().getM_strUserName();
                        activityID = wdmodel.getM_objGeneralData().getM_strActivityId();
                        sessionID = wdmodel.getM_objGeneralData().getM_strDMSSessionId();
                        subTaskId = wdmodel.getM_objGeneralData().getSubTaskId();
                        taskName = wdmodel.getM_objGeneralData().getTaskName();
                        isGridBatchingEnabled = (!"".equals(wdmodel.getM_objGeneralData().getNoOfRecordToFetch()));
                    }
               }
               
               if("TemplateTaskForm".equals(pageformUID) || "CustomTemplateTaskForm".equals(pageformUID) || "DataTemplateTaskForm".equals(pageformUID)) 
               {
                   WFAttribParser.prepareWorkitemModel(request, processvariablesxml, strAttributeDataXml, generalData,formsession); 
                   LinkedHashMap workitemMap = wisessionbean.getWorkItems();  
                   if(tid==null || tid.isEmpty()){
                       wdmodel = (WorkdeskModel) workitemMap.get(pid + "_" + wid);
                    } else {
                        wdmodel = (WorkdeskModel) workitemMap.get(pid + "_" + wid + "_" + tid);
                        if(wdmodel==null){
                            wdmodel = (WorkdeskModel) workitemMap.get(pid + "_" + wid);
                        }
                    }               
                   WFAttribParser.fetchProcessSpecificTaskTemplateData(wdmodel,formsession, request);
               }
               wdmodel.getM_objGeneralData().setM_strDateFormat(request.getParameter("DateFormat"));    
               wdmodel.getM_objGeneralData().setM_strServerTimeZone(request.getParameter("ServerTimeDiff"));     
               wdmodel.getM_objGeneralData().setM_strClientTimeZone(request.getParameter("ClientTimeDiff"));
               formMode=wdmodel.getM_objGeneralData().getM_strMode();//Bug 82321 Start
               if(IFormUtility.isTaskIForm(formsession)){
                   wdmodel.getM_objGeneralData().setM_strMode("W");
                   formMode="W";
               }
               if(isReadOnlyForm){
                   formMode="R";
                   mode=isReadOnlyForm?"R":"N";
               	wdmodel.getM_objGeneralData().setM_strMode(mode);
                }//Bug 82321 End
            } catch (Exception e) {
                
            }
            finally{
                if(fosProcess!=null){
                    try {
                        fosProcess.flush();
                        fosProcess.close();
                    } catch (IOException ex) {
                        NGUtil.writeErrorLog(wdmodel.getM_objGeneralData().getM_strEngineName(), IFormConstants.VIEWER_LOGGER_NAME, "Exception in creating process variable XML "+ex.getMessage(),ex);
                    }
                }
            }
          }
     else if (  mobileType.equals("android") || mobileType.equals("ios") ){
         try {
                WDWorkitems wisessionbean = (WDWorkitems) session.getAttribute("wDWorkitems");
                if(wisessionbean==null){
                    wisessionbean = new WDWorkitems();
                    session.setAttribute("wDWorkitems", wisessionbean);
                }
                if (wisessionbean != null) {
                    LinkedHashMap workitemMap = wisessionbean.getWorkItems();                    
                    if(tid==null || tid.isEmpty()){
                        wdmodel = (WorkdeskModel) workitemMap.get(pid + "_" + wid);
                    } else {
                        wdmodel = (WorkdeskModel) workitemMap.get(pid + "_" + wid + "_" + tid);
                    }
                    activityName=wdmodel.getM_objGeneralData().getM_strActivityName();
                    processName=wdmodel.getM_objGeneralData().getM_strProcessName();
                    processDefId=wdmodel.getM_objGeneralData().getM_strProcessDefId();
                    processInstanceId = wdmodel.getM_objGeneralData().getM_strProcessInstanceId();
                    cabinetName = wdmodel.getM_objGeneralData().getM_strEngineName();
                    appServerIp = wdmodel.getM_objGeneralData().getM_strJTSIP();
                    appServerPort = wdmodel.getM_objGeneralData().getM_strJTSPORT();
                    userName =wdmodel.getM_objGeneralData().getM_strUserName();
                    activityID = wdmodel.getM_objGeneralData().getM_strActivityId();
                    sessionID = wdmodel.getM_objGeneralData().getM_strDMSSessionId();
                    subTaskId = wdmodel.getM_objGeneralData().getSubTaskId();
                    taskName = wdmodel.getM_objGeneralData().getTaskName();
                    isReadOnlyForm = "R".equals(wdmodel.getM_objGeneralData().getM_strMode());
                    isGridBatchingEnabled = (!"".equals(wdmodel.getM_objGeneralData().getNoOfRecordToFetch()));
                }
                if("TemplateTaskForm".equals(pageformUID) || "CustomTemplateTaskForm".equals(pageformUID) || "DataTemplateTaskForm".equals(pageformUID)) 
               {
                   LinkedHashMap workitemMap = wisessionbean.getWorkItems();  
                   if(tid==null || tid.isEmpty()){
                       wdmodel = (WorkdeskModel) workitemMap.get(pid + "_" + wid);
                    } else {
                        wdmodel = (WorkdeskModel) workitemMap.get(pid + "_" + wid + "_" + tid);
                        if(wdmodel==null){
                            wdmodel = (WorkdeskModel) workitemMap.get(pid + "_" + wid);
                        }
                    }               
                   WFAttribParser.fetchTaskTemplateData(wdmodel,formsession, request);
               }
                
            } catch (Exception e) {
                
            }
     }
     else {
         try {
                WDWorkitems wisessionbean = (WDWorkitems) session.getAttribute("wDWorkitems");
                if (wisessionbean != null) {
                    LinkedHashMap workitemMap = wisessionbean.getWorkItems();                    
                    if(tid==null || tid.isEmpty()){
                        wdmodel = (WorkdeskModel) workitemMap.get(pid + "_" + wid);
                        isReadOnlyForm = "R".equals(wdmodel.getM_objGeneralData().getM_strMode());
                    } else {
                        wdmodel = (WorkdeskModel) workitemMap.get(pid + "_" + wid + "_" + tid);   
                        
                        String strMode = wdmodel.getM_objGeneralData().getM_strMode();
                            if(!"R".equals(strMode)){
                                strMode = wdmodel.getWfForm().getFormViewModeForTask();   
                                if("R".equals(strMode)){
                                    wdmodel.getM_objGeneralData().setM_strMode("R");
                                }
                            }
                            isReadOnlyForm = "R".equals(strMode);
                    }
                    
                    if(wdmodel != null){
                        activityName=wdmodel.getM_objGeneralData().getM_strActivityName();
                        processName=wdmodel.getM_objGeneralData().getM_strProcessName();
                        processDefId=wdmodel.getM_objGeneralData().getM_strProcessDefId();
                        processInstanceId = wdmodel.getM_objGeneralData().getM_strProcessInstanceId();
                        cabinetName = wdmodel.getM_objGeneralData().getM_strEngineName();
                        appServerIp = wdmodel.getM_objGeneralData().getM_strJTSIP();
                        appServerPort = wdmodel.getM_objGeneralData().getM_strJTSPORT();
                        userName =wdmodel.getM_objGeneralData().getM_strUserName();
                        activityID = wdmodel.getM_objGeneralData().getM_strActivityId();
                        sessionID = wdmodel.getM_objGeneralData().getM_strDMSSessionId();
                        subTaskId = wdmodel.getM_objGeneralData().getSubTaskId();
                        taskName = wdmodel.getM_objGeneralData().getTaskName();                    
                        isGridBatchingEnabled = (!"".equals(wdmodel.getM_objGeneralData().getNoOfRecordToFetch()));
                        assignedTo = wdmodel.getM_objGeneralData().getM_strAssignedTo();
                        priorityLevel = wdmodel.getM_objGeneralData().getM_strPriorityLevel();
                        lockedByName = wdmodel.getM_objGeneralData().getM_strLockedByName();
                        processedBy = wdmodel.getM_objGeneralData().getM_strProcessedBy();
//                        introducedAt = wdmodel.getM_objGeneralData().getM_strIntroducedAt();
                        introductionDateTime = wdmodel.getM_objGeneralData().getM_strIntroductionDateTime();
                        queueId = wdmodel.getM_objGeneralData().getM_strQueueId();
                        lockStatus = wdmodel.getM_objGeneralData().getM_strLockStatus();
//                        userEmailId = wdmodel.getM_objGeneralData().getM_strUserEmailId();
                        userPersonalName = wdmodel.getM_objGeneralData().getM_strUserPersonalName();
                        userFamilyName = wdmodel.getM_objGeneralData().getM_strUserFamilyName();
                        userIndex = wdmodel.getM_objGeneralData().getM_strUserIndex();
                        
                    }    
                }
            } catch (Exception e) {
                
            }
     }      
            IFormViewer objViewer = new IFormViewer();
           
            
            if( !ApplicationType.equals("pms") && mobileType.equals("mobile") )  {
                    cabinetName= AppTasks.getValueFromINI("CabinetName", request);
                    objViewer.init(request);
                    sessionID = String.valueOf(session.getAttribute("sessionId"));
                    formsession.setM_objFormViewer(objViewer);
                    try {
                       if(request.getParameter("callFrom") == null ){
                            if(IFormUtility.escapeHtml4(request.getParameter("UDB")) != null)
                                formsession.setM_strSessionId(AESEncryption.decrypt(IFormUtility.escapeHtml4(request.getParameter("UDB")),"UserCon@2020"));
                            else if(IFormUtility.escapeHtml4(request.getParameter("SessionId")) != null)
                                formsession.setM_strSessionId(IFormUtility.escapeHtml4(request.getParameter("SessionId")));
                             else if(IFormUtility.escapeHtml4(request.getParameter("authId")) != null)
                                formsession.setM_strSessionId(IFormUtility.escapeHtml4(request.getParameter("authId")));
                       }
                    }
                    catch(Exception e)
                    {
                        
                    }
                    if(IFormUtility.escapeHtml4(request.getParameter("userIndex"))!= null )
                        formsession.setM_strUserIndex(IFormUtility.escapeHtml4(request.getParameter("userIndex")));
                    if(IFormUtility.escapeHtml4(request.getParameter("userName")) != null )
                        formsession.setM_strUserName(IFormUtility.escapeHtml4(URLDecoder.decode(request.getParameter("userName"),"UTF-8")));
                    if(IFormUtility.escapeHtml4(request.getParameter("existingFid")) != null ){
                        session.setAttribute("IsPreview", "Y");
                        formsession.setM_strExistingFid(IFormUtility.escapeHtml4(request.getParameter("existingFid")));
                    }
                    else{
                        session.setAttribute("IsPreview", "N");
                    }
                    if( IFormUtility.escapeHtml4(request.getParameter("appName")) != null )
                        formsession.setM_strApplicationName(IFormUtility.escapeHtml4(request.getParameter("appName")));

            }
            String url="";
        if( ApplicationType.equals("pms") ) {
            cabinetName = formsession.getM_strCabinetName();
            try{ 
                    objViewer.init(request,formBufferPath);
                     formsession.setM_objFormViewer(objViewer);
                    session.setAttribute("obj"+fid+"Viewer", objViewer);
                    if (wdmodel != null) {
                        dateFormat = wdmodel.getM_objGeneralData().getM_strDateFormat();
                    }
                    mobileMode=objViewer.getM_objFormDef().getM_strMobileMode();
                    objViewer.getM_objFormDef().setM_strExtraParams(extraParams);
                    String buttonId = request.getParameter("buttonId");
                    if(buttonId!=null && !buttonId.isEmpty()){
                        EButtonControl buttonControl = (EButtonControl)objViewer.getM_objFormDef().getFormField(buttonId);
                        ((ERootControl)objViewer.getM_objFormDef().getM_objRootControl()).setBtnRef(buttonControl);
                    }
                    url=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
                    if(calledForProcessReport!=null){
                        pmwebtemp=IFormUtility.escapeHtml4(request.getParameter("pmwebtemp"));
                        pmwebtemp=IFormUtility.sanitizeForProcess(pmwebtemp,true);
                        objViewer.getM_objFormDef().dumpHTMLforProcessReport(url,formsession,wdmodel,pmwebtemp,formName);
                    }
            } catch(Exception ex){
                ex.printStackTrace();
            }
        }
        if(mobileType.equals("mobile")) {
            dateDbFormat=objViewer.getM_objFormDef().getM_objGlobalFormStyle().getM_objInputFormStyle().getM_strDateFormat();
            dateSeparator = objViewer.getM_objFormDef().getM_objGlobalFormStyle().getM_objInputFormStyle().getM_strDateSeparator();
            String ds="",df="";
            if(dateSeparator.equalsIgnoreCase("1"))
                ds = "/";
            else if(dateSeparator.equalsIgnoreCase("2"))
                ds = "-";
            else if(dateSeparator.equalsIgnoreCase("3"))
                ds = ".";
           
            if(dateDbFormat.equalsIgnoreCase("1"))
            {
                df = "dd"+ds+"MM"+ds+"yyyy";
            }
            if(dateDbFormat.equalsIgnoreCase("2"))
            {
                df = "MM"+ds+"dd"+ds+"yyyy";
            }
            if(dateDbFormat.equalsIgnoreCase("3"))
            {
                df = "yyyy"+ds+"MM"+ds+"dd";
            }
            if(dateDbFormat.equalsIgnoreCase("4"))
            {
                df = "dd"+ds+"MMM"+ds+"yyyy";
            }
            formsession.setM_strDateFormat(df);
        } 
            
    if( !ApplicationType.equals("pms") && mobileType.equals("mobile") ) {        
           
            CommonUtility.populateDataObjectInterfaces(formsession,objViewer.getM_objFormDef().getM_objMDMData().getSelectedTables());
            objViewer.retainMainTableData(objViewer.getM_objFormDef().getM_objMDMData().getSelectedTables());
            
            session.setAttribute("obj"+pageformUID+"Viewer", objViewer);
           
            if( isNew != null ){
                if("Y".equalsIgnoreCase(isNew)){                   
                    AppTasks.deleteExistingApplication(request, response);
                } 
            } 
            if( filterValue != null ){
                session.removeAttribute("isLogin");               
            } 
            if (wdmodel != null) {
                dateFormat = wdmodel.getM_objGeneralData().getM_strDateFormat();
            }
            
            mobileMode=objViewer.getM_objFormDef().getM_strMobileMode();
             String buttonId = IFormUtility.escapeHtml4(request.getParameter("buttonId"));
            if(buttonId!=null && !buttonId.isEmpty()){
                EButtonControl buttonControl = (EButtonControl)objViewer.getM_objFormDef().getFormField(buttonId);
                ((ERootControl)objViewer.getM_objFormDef().getM_objRootControl()).setBtnRef(buttonControl);
            }
           
                url=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
                if(calledForProcessReport!=null){
                    pmwebtemp=IFormUtility.escapeHtml4(request.getParameter("pmwebtemp"));
                    pmwebtemp=IFormUtility.sanitizeForProcess(pmwebtemp,true);
                    objViewer.getM_objFormDef().dumpHTMLforProcessReport(url,formsession,wdmodel,pmwebtemp,formName);
                }
            
                
    }    
            
            if( !ApplicationType.equals("pms") &&  (mobileType.equals("android") || mobileType.equals("ios"))  ){
                    objViewer.init(request);
                    formsession.setM_objFormViewer(objViewer);
                    session.setAttribute("obj"+fid+"Viewer", objViewer);
                    if (wdmodel != null) {
                        dateFormat = wdmodel.getM_objGeneralData().getM_strDateFormat();
                    }
                    mobileMode=objViewer.getM_objFormDef().getM_strMobileMode();
                     String buttonId = IFormUtility.escapeHtml4(request.getParameter("buttonId"));
                    if(buttonId!=null && !buttonId.isEmpty()){
                        EButtonControl buttonControl = (EButtonControl)objViewer.getM_objFormDef().getFormField(buttonId);
                        ((ERootControl)objViewer.getM_objFormDef().getM_objRootControl()).setBtnRef(buttonControl);
                    }
                    url=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
                    if(calledForProcessReport!=null){
                        objViewer.getM_objFormDef().dumpHTMLforProcessReport(url,formsession,wdmodel,request.getParameter("pmwebtemp"),formName);
                    }
            }
            String sid = (String) IFormUtility.escapeHtml4(request.getParameter("WD_SID"));
            String wd_sid = (String) IFormUtility.escapeHtml4(request.getParameter("WDESK_SID"));
            String reqTok = (String) IFormUtility.escapeHtml4(request.getParameter("WD_RID"));
            String rid_Action = IFormUtility.generateTokens(request,request.getContextPath() + "/components/viewer/action.jsp");
            String rid_ActionAPI = IFormUtility.generateTokens(request,request.getContextPath() + "/components/viewer/action_API.jsp");
            String rid_IfHandler = IFormUtility.generateTokens(request,request.getContextPath() + "/components/viewer/ifhandler.jsp"); 
            String rid_listviewmodal = IFormUtility.generateTokens(request,request.getContextPath() + "/components/viewer/listViewModal.jsp");
            String rid_advancelistviewmodal = IFormUtility.generateTokens(request,request.getContextPath() + "/components/viewer/advancedListViewModal.jsp");
            String rid_picklistview = IFormUtility.generateTokens(request,request.getContextPath() + "/components/viewer/picklistview.jsp");
            String rid_texteditor = IFormUtility.generateTokens(request,request.getContextPath() + "/components/viewer/texteditor.jsp");
            String rid_webservice = IFormUtility.generateTokens(request,request.getContextPath() + "/components/viewer/webservice.jsp");
            String rid_appTask = IFormUtility.generateTokens(request,request.getContextPath() + "/components/viewer/portal/appTask.jsp");
            IFormReference objFormReference1;
            if(request.getParameter("QueryString")!=null && !"".equalsIgnoreCase(request.getParameter("QueryString")))
                objFormReference1 = new IFormAPIHandler(request,response,pageformUID);
       
        %>
        <% if( ApplicationType.equals("pms")||  mobileType.equals("android") || mobileType.equals("ios")  )  { %>
        <title><%= lbls.getString("PREVIEW")%></title>
        <%} else {%>
        <title><%= AppTasks.getValueFromINI("ApplicationName", request)%></title>
        <%}%>
        <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8,EmulateIE9,EmulateIE10,EmulateIE11" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <script type="text/javascript" src="resources/bootstrap/js/jquery.js"></script>
        <link type="text/css" rel="stylesheet" href="resources/bootstrap/css/bootstrap.css">
        
        <link rel="stylesheet" href="resources/bootstrap/css/jquery-ui.css">
        <script type="text/javascript" src="resources/bootstrap/js/bootstrap.js"></script>
        <script type="text/javascript" src="resources/bootstrap/js/jquery-ui.js"></script>
        <script type="text/javascript" src="resources/bootstrap/js/jquery-editable-select.js"></script>        
        <script type="text/javascript" src="resources/bootstrap/js/jquery.formError.js"></script>  
        <script type="text/javascript" src="resources/<%= lbls.getString("Path")%>scripts/constants.js"></script>
        <script type="text/javascript" src="resources/bootstrap/js/moment.js"></script>      
        <script type="text/javascript" src="resources/bootstrap/js/bootstrap-datepicker.js"></script>
        <script type="text/javascript" src="resources/bootstrap/js/bootstrap-datepicker.min.js"></script>
        <script type="text/javascript" src="resources/bootstrap/js/bootstrap-multiselect.js"></script>
        <script type="text/javascript" src="resources/<%= lbls.getString("Path")%>scripts/iformCustomMsg.js"></script>
        <script type="text/javascript" src="resources/scripts/commonmethods.js"></script>
        <script type="text/javascript" src="resources/scripts/net.js"></script>
        <script type="text/javascript" src="resources/scripts/iformview.js"></script>
        <script type="text/javascript" src="resources/scripts/iformclient.js?v3.2"></script>
        <script type="text/javascript" src="resources/scripts/datevalidation.js"></script>
        <script type="text/javascript" src="resources/scripts/floating-labels.js"></script>
        <link type="text/css" rel="stylesheet" href="resources/bootstrap/css/bootstrap-datepicker.css">
        <link type="text/css" rel="stylesheet" href="resources/bootstrap/css/editable-select.css">
        <link type="text/css" rel="stylesheet" href="resources/<%= lbls.getString("Path")%>css/ifomstyle.css">
        <link type="text/css" rel="stylesheet" href="resources/bootstrap/css/bootstrap-multiselect.css">
        <link type="text/css" rel="stylesheet" href="resources/bootstrap/css/bootstrap-datepicker.min.css">
        <link type="text/css" rel="stylesheet" href="resources/bootstrap/css/navigation.css">
        <link type="text/css" rel="stylesheet" href="resources/<%= lbls.getString("Path")%>css/floating-labels.css">
        <link type="text/css" rel="stylesheet" href="resources/css/common.css">
        <link type="text/css" rel="stylesheet" href="resources/css/customcss.css">
       
        <script type="text/javascript" src="resources/bootstrap/js/jquery.floatThead.min.js"></script>
        <link type="text/css" rel="stylesheet" href="resources/bootstrap/css/bootstrap-datetimepicker.css">      
        <script type="text/javascript" src="resources/bootstrap/js/bootstrap-datetimepicker.js"></script>
        <script type="text/javascript" src="resources/bootstrap/js/jquery.mask.js"></script>
        <link type="text/css" rel="stylesheet" href="resources/bootstrap/css/jquery.datetimepicker.css">
        <script type="text/javascript" src="resources/bootstrap/js/jquery.datetimepicker.full.min.js"></script>
        <script type="text/javascript" src="resources/scripts/autoNumeric.js"></script>
        <link type="text/css" rel="stylesheet" href="resources/bootstrap/css/font-awesome.min.css">
	<!--		<link type="text/css" rel="stylesheet" href="resources/bootstrap/css/font.css">-->
        <script type="text/javascript" src="resources/scripts/securenumbermask.js"></script>
        <script type="text/javascript" src="resources/scripts/html2canvas.js"></script>
        <script type="text/javascript" src="resources/scripts/jquery.plugin.html2canvas.js"></script>
        <script type="text/javascript" src="resources/scripts/ajax.js"></script>
         <link type="text/css" rel="stylesheet" href="resources/<%= lbls.getString("Path")%>css/jquery-ui-1.8.21.custom.css">
         <script type="text/javascript" src="resources/bootstrap/js/bootbox.min.js"></script>
         <script type="text/javascript" src="resources/scripts/rte.js"></script>
         <link rel="stylesheet" href="resources/bootstrap/css/jquery.scrolling-tabs.css"/>
         <script type="text/javascript" src="resources/bootstrap/js/jquery.scrolling-tabs.js"></script>
         <script type="text/javascript" src="resources/scripts/tooltipster.bundle.min.js"></script>
         <link rel="stylesheet" href="resources/css/tooltipster.bundle.css"/>
         <link href="resources/scripts/froala_editor/css/froala_editor.pkgd.min.css" rel="stylesheet" type="text/css" />
         <link href="resources/scripts/froala_editor/css/froala_style.min.css" rel="stylesheet" type="text/css" />    
         <script type="text/javascript" src="resources/scripts/froala_editor/js/froala_editor.pkgd.min.js"></script>
         <script type="text/javascript" src="resources/scripts/rte.js"></script>
         <script type="text/javascript" src="resources/scripts/aes.js"></script>
         <script type="text/javascript" src="resources/scripts/pbkdf2.js"></script>
         <script type="text/javascript" src="resources/scripts/AesUtil.js"></script>
         <script type="text/javascript" src="resources/scripts/base64.js"></script>
         <script type="text/javascript" src="resources/scripts/iformapi.js"></script>
         <link type="text/css" rel="stylesheet" href="resources/css/nav.css">
         <script type="text/javascript" src="resources/scripts/blowfish.js"></script>
         <script type="text/javascript" src="resources/scripts/webcam.min.js"></script>
         <link href="resources/scripts/froala_editor/css/font-awesome.min.css" rel="stylesheet" type="text/css" ></link>
         <link href="resources/css/merriweather.css" rel="stylesheet" type="text/css">
         <link href="resources/css/openSans.css" rel="stylesheet" type="text/css">
         <%--    PWA Changes--%>
         <%if(isPWA){%>
         <link rel="manifest" href="../../manifest.json" type="application/json"></link>
         <meta name="apple-mobile-web-app-capable" content="yes"></meta>
         <meta name="apple-mobile-web-app-status-bar-style" content="default"></meta>
         <link rel="apple-touch-icon" sizes="180x180" href="../../pwalogo/LandMarkMobileApp-apple-touch-icon.png"></link>
         <%}%>
         <script>
			var contextPath = "<%= request.getContextPath() %>"; 
            var useCustomIdAsControlName = <%=useCustomIdAsControlName%>;
            var pid = "<%=pid%>";
            var wid = "<%=wid%>";
            var tid = "<%=tid%>";
            var dateFormat = "<%=dateFormat%>";
            var processName = "<%=processName%>";
            var processDefId = "<%=processDefId%>";
            var activityName = "<%=activityName%>";
            var fid = "";   
	    <%if( (!ApplicationType.equals("pms") && mobileType.equals("mobile")) || (!ApplicationType.equals("pms") && (mobileType.equals("android") || mobileType.equals("ios")) && ("TemplateTaskForm".equals(pageformUID) || "CustomTemplateTaskForm".equals(pageformUID) || "DataTemplateTaskForm".equals(pageformUID)) ) ) {%> 
                fid = "<%=pageformUID%>";
            <%} else {%>
                fid = "<%=fid%>";
             <%}%>
            var isReadOnlyForm = <%=isReadOnlyForm%>;
            var processInstanceId="<%=processInstanceId%>";
            var cabinetName="<%=cabinetName%>";
            var appServerIp="<%=appServerIp%>";
            var appServerPort="<%=appServerPort%>";
            var userName = "<%=userName%>";
            var fSessionId = "<%=fSessionId%>";
            var sessionId = "<%=sessionID%>";
            var activityID = "<%=activityID%>";
            var userIndex = "<%=userIndex%>";
            var mobileMode = "<%=mobileMode%>";
            var isDatePicker = "<%=isDatePicker%>";
            var isOverlayOpen = "<%=isOverlayOpen%>";
            var CleanMapOnCloseModal = "<%=CleanMapOnCloseModal%>";
            var disableFieldFontColor = "<%=disableFieldFontColor%>";        
            var subTaskId = "<%=subTaskId%>";
            var taskName = "<%=taskName%>";
            var registeredMode= "<%=registeredMode%>";
			
            var applicationName="<%=applicationName%>";
            var alwaysCreate="<%=alwaysCreateNew%>";
            var DeleteOldApplicationData="<%=DeleteOldApplicationData%>";
            var globalDateFormat="<%=objViewer.getM_objFormDef().getM_objGlobalFormStyle().getM_objInputFormStyle().getM_strDateFormat()%>";
            var globalDateSeparator="<%=objViewer.getM_objFormDef().getM_objGlobalFormStyle().getM_objInputFormStyle().getM_strDateSeparator()%>";
            var formName = "<%=objViewer.getM_objFormDef().getM_strFormAlias()%>";
            var formMode="W";//Bug 82321
            var iformLocale="<%=lbls.getString("Path")%>";
            var isEditableComboOnLoad = "<%=isEditableComboOnLoad%>";
            var isGridBatchingEnabled = "<%=isGridBatchingEnabled%>";
            var autoIncrementLabelDisplay = "<%=autoIncrementLabelDisplay%>";
            var listViewCharacterLimit = "<%=listViewCharacterLimit%>";
            var applicationName="<%=applicationName%>";
            var multipleRowDuplicate = "<%=multipleRowDuplicate%>";
            var isShowGridComboLabel = false;
            var isPreview="<%=request.getSession().getAttribute("IsPreview")%>";
            <% if( mobileType.equals("mobile") ) { %>
              isShowGridComboLabel = "<%=isShowGridComboLabel%>";
            <% } %>  
            <%if( !ApplicationType.equals("pms") && mobileType.equals("mobile") ) {%> 
                var assignedTo = "<%=assignedTo%>";
                var priorityLevel = "<%=priorityLevel%>";
                var lockedByName = "<%=lockedByName%>";
                var processedBy = "<%=processedBy%>";
                var introductionDateTime = "<%=introductionDateTime%>";
                var queueId = "<%=queueId%>";
                var lockStatus = "<%=lockStatus%>";
                var userPersonalName = "<%=userPersonalName%>";
                var userFamilyName = "<%=userFamilyName%>";
            <%}%> 
            <%
                if(request.getHeader("User-Agent")!=null){
                    if(request.getHeader("User-Agent").toLowerCase().contains("ipod")  || request.getHeader("User-Agent").toLowerCase().contains("iphone") || request.getHeader("User-Agent").toLowerCase().contains("ipad")){                                 
            %>     var iosDtype= true;
            <%
                    }
               }
            %>
            
           <%if( ApplicationType.equals("pms") ) {%> 
               
            var folderId="<%=folderId%>";
            var webdateFormat = "<%=dateFormat%>";
            var processDefId="<%=processDefId%>";
            var isProcessSpecific="Y";
            var additionalParams="<%=extraParams%>";
            <%}%> 
            
            var filterValue = "<%=filterValue%>";
            
            window.onresize = function (e) {
                var scrollerWidthofBrowser = getBrowserScrollSize().width;
                if(document.getElementById("headerDiv"))
                    document.getElementById("headerDiv").style.width=($(window).width() - scrollerWidthofBrowser )+"px";
                if(document.getElementById("affix_padding") && document.getElementById("headerDiv")){
                if(parseInt(window.innerHeight)>parseInt(document.getElementById("headerDiv").clientHeight*3)){
                
                    document.getElementById("affix_padding").style.paddingTop= (parseInt(document.getElementById("headerDiv").clientHeight))+"px";
                    if(document.getElementById("headerDiv").style.position!='fixed'){
                        document.getElementById("headerDiv").style.position='fixed';
                        document.getElementById("headerDiv").style.top='0';
                        document.getElementById("headerDiv").style.width=($(window).width() - scrollerWidthofBrowser )+"px";
                        }
                    
                    }
                else{
                    document.getElementById("affix_padding").style.paddingTop = "";
                    document.getElementById("headerDiv").style.position='';
                    document.getElementById("headerDiv").style.top='';
//                    document.getElementById("headerDiv").classList.remove("affix");
                   document.getElementById("headerDiv").style.width=($(window).width() - scrollerWidthofBrowser )+"px";
                    }
                }
                sectionAsFooter(footerFrameName);
                makeStickyTabs();
            }
        </script>  

        <style>
            .affix {
              top: 0;
              width: 100%;
              <%if(objViewer.getM_objFormDef().getM_objGlobalFormStyle().getM_objFormStyle().isM_bHideHeader()){%>
              padding-right: 17px;
              <%}%> 
            }
            .affix + .affix_padding {
                <%if(objViewer.getM_objFormDef().getM_objGlobalFormStyle().getM_objFormStyle().isM_bHideHeader()){%>
                    
                <%}
                else if(StringUtils.isEmpty(objViewer.getM_objFormDef().getM_objGlobalFormStyle().getM_objFormStyle().getM_strLogoPath())){
                     if(objViewer.getM_objFormDef().getM_objGlobalFormStyle().getM_objFormStyle().isM_bShowHeader()){
                %>
                  padding-top:<%=(Integer.parseInt(objViewer.getM_objFormDef().getM_objGlobalFormStyle().getM_objFormStyle().getM_strLogoHeight()))+52%>px;
                  <%}
                 else{%>
                  padding-top:<%=(Integer.parseInt(objViewer.getM_objFormDef().getM_objGlobalFormStyle().getM_objFormStyle().getM_strLogoHeight()))+25%>px;
                  <%}}
                    else{%>
                  padding-top:50px;
                  <%}%>
              }
            .affix-top {
              /*width: 100%;*/
            }

            .affix-bottom {
                /*top: 0;*/
              /*width: 100%;*/
              /*position: absolute;*/
            }
             <%objViewer.getM_objFormDef().setDeviceType(request.getHeader("User-Agent"));%>		
        </style>
        <!--Bug 65126-->
		<style>
                   
			html{
				height: 100%;
				overflow: auto;
				-webkit-overflow-scrolling: touch;
			}
			body{
				height: 100%;     
				/*overflow: auto;*/
				-webkit-overflow-scrolling: touch;
                                -ms-overflow-style: scrollbar;
			}
                       .squaredTwo input[type=checkbox]:checked + label{
                           background-color:#<%=objViewer.getM_objFormDef().getM_objGlobalFormStyle().getM_objFormStyle().getM_strThemeColor()%> !important;
                         }
                         .squaredTwo input[disabled=disabled][type=checkbox] + label{
                           background-color:#ccc !important;
                           border-color:#ccc !important;
                         }
                       .squaredTwo input[disabled=disabled][type=checkbox]:checked + label{
                           background-color:#ccc !important;
                           border-color:#ccc !important;
                         } 
                       .input-group input[controltype=date][disabled=disabled] + label span{
                             color:#ccc !important;
                         } 
                       .radioTwo input[type=radio]:checked + label{
                           background-color:#<%=objViewer.getM_objFormDef().getM_objGlobalFormStyle().getM_objFormStyle().getM_strThemeColor()%> !important;
                       }
                       .squaredOne label:after{
                           border-color:#<%=objViewer.getM_objFormDef().getM_objGlobalFormStyle().getM_objFormStyle().getM_strThemeColor()%> !important;
                       }
                       .radioOne label:after {
                             background-color: #<%=objViewer.getM_objFormDef().getM_objGlobalFormStyle().getM_objFormStyle().getM_strThemeColor()%> !important;
                       }
                       .squaredOne input[type=checkbox]:checked + label,.radioOne input[type=radio]:checked + label{
                           border: 2px solid #<%=objViewer.getM_objFormDef().getM_objGlobalFormStyle().getM_objFormStyle().getM_strThemeColor()%> !important;
                       }
                       .bootbox.modal {
                           z-index: 9999 !important;
                       }
                       .multiselect-container {
                            width: 100% !important;
                        }
                        .multiselect-container>li>a>label>input[type=checkbox] {
                            margin-left : 5px;
                            position : relative;
                        }
                        .multiselect-container>li>a>label {
                            padding: 3px 20px;
                        }
                        .disabledBGColor{
                            background-color: #e4e4e4 !important;
                            <%if(disableFieldFontColor != null && (!"".equals(disableFieldFontColor))){%>
                                color: #<%=disableFieldFontColor%> !important;
                            <%}
                            else{%>
                                color: #2f4f4f !important;
                            <%}%>
                        }
                        .disabledTableBGColor{
                            background-color: #e4e4e438 !important;
                            <%if(disableFieldFontColor != null && (!"".equals(disableFieldFontColor))){%>
                                color: #<%=disableFieldFontColor%> !important;
                            <%}
                            else{%>
                                color: #2f4f4f !important;
                            <%}%>
                        }
                        .disabledTableFont{
                            <%if(disableFieldFontColor != null && (!"".equals(disableFieldFontColor))){%>
                                color: #<%=disableFieldFontColor%> !important;
                            <%}
                            else{%>
                                color: #2f4f4f !important;
                            <%}%>
                        }
                        <%if(objViewer.getM_objFormDef().getM_objGlobalFormStyle().getM_objSectionFormStyle().isM_bRemoveMargins()){%>
                        .iform-table .radioTwo label:after{
                            top: 3px !important;
                            left: 3px !important;
                        }
                        .iform-table .radioOne label:after{
                            top: 1px !important;
                            left: 1px !important;
                        }
                        <%}%>
                        .fr-top{
                            border-top:0 !important;
                        }
                        <%
                          if(request.getHeader("User-Agent")!=null){
                              String deviceType=request.getHeader("User-Agent").toLowerCase();
                              if(deviceType.contains("ipod")  || deviceType.contains("iphone") || deviceType.contains("ipad")){                                 
                        %>
                            .modal-backdrop {
                                 z-index: -1;
                            }
                        <%
                           }
                          }
                        %>

        </style>
    </head>
    <body onload="doInit('form');" onunload="closeSubForm()" onkeydown="bodykeyDown(event);stopFormRefreshing(event);" onkeypress="stopFormRefreshing(event);" onbeforeunload="closeSubForm()" style="overflow:auto;overscroll-behavior:contain;" class="iBodyStyle">
        <div id="fade" class="black_overlay"></div>		
        <script>
            CreateIndicator("application");
            document.getElementById("fade").style.display="block";
        </script>
        <input type="hidden" id="sid" name="sid" value= "<%=sid%>" >
        <input type="hidden" id="rid_Action" name="rid_Action" value= "<%=rid_Action%>" >
	<input type="hidden" id="wd_sid" name="wd_sid" value= "<%=wd_sid%>" >
        <input type="hidden" id="rid_ActionAPI" name="rid_ActionAPI" value= "<%=rid_ActionAPI%>" >
        <input type="hidden" id="rid_IfHandler" name="rid_IfHandler" value= "<%=rid_IfHandler%>" >
        <input type="hidden" id="rid_listviewmodal" name="rid_listviewmodal" value= "<%=rid_listviewmodal%>" >
        <input type="hidden" id="rid_advancelistviewmodal" name="rid_advancelistviewmodal" value= "<%=rid_advancelistviewmodal%>" >
        <input type="hidden" id="rid_picklistview" name="rid_picklistview" value= "<%=rid_picklistview%>" >
        <input type="hidden" id="rid_texteditor" name="rid_texteditor" value= "<%=rid_texteditor%>" >
        <input type="hidden" id="rid_webservice" name="rid_webservice" value= "<%=rid_webservice%>" >
	<input type="hidden" id="rid_appTask" name="rid_appTask" value= "<%=rid_appTask%>" >
        <input type="hidden" id="mainStepNo" name="mainStepNo" value= "<%=formsession.getMainMenuData()%>" >
        <input type="hidden" id="substepNo" name="substepNo" value= "<%=formsession.getSubMenuData()%>" >
        <div id="oforms_iform" style="min-height:100%;height:auto;margin:auto;margin-top:0px;overflow-y: auto;overflow-x: hidden;" class="iFormStyle">
        
        <%if(!objViewer.getM_objFormDef().getM_objGlobalFormStyle().getM_objFormStyle().isM_bHideHeader()){%>
        <div style="width: 100%;text-align:center" class="affix_padding"></div>
        <%}%>
        <%if(objViewer!=null){%>
        <% try{
            String pagefid=fid;
            if(!ApplicationType.equals("pms") && mobileType.equals("mobile"))
                pagefid=pageformUID;
            IFormReference objFormReference = new IFormAPIHandler(request,response,pagefid);
            IFormServerEventHandler objCustomCodeInstance = IFormContext.getInstance(objFormReference);
            if( objCustomCodeInstance != null )        
            {  
                formsession.setObjServerEventHandler(objCustomCodeInstance);
                formsession.setObjFormReference(objFormReference);
                objViewer.getM_objFormDef().setListChanged(true); //To change only list
                objCustomCodeInstance.beforeFormLoad(objViewer.getM_objFormDef(), objFormReference);
            }
            }catch(Exception ex){}
            catch(Error ex){}
        if(objViewer.getM_objFormDef().isChangeList()){
             objViewer.getM_objFormDef().setListChanged(false);
        }
        List<Menu> menuList = new ArrayList(); 
        try{
            if(objViewer.getM_objFormDef().isChangeList() && objViewer.getM_objFormDef()!=null){
                menuList = objViewer.getM_objFormDef().getObjChangeList();
            }
            else if(objViewer.getM_objFormDef().getM_objNav() != null){
                menuList = objViewer.getM_objFormDef().getM_objNav().getObjMenuList();
            }
        } catch(Exception ex){
            
        }
       if( !ApplicationType.equals("pms") || (!ApplicationType.equals("pms") && (mobileType.equals("android") || mobileType.equals("ios"))) )  {   
            WDWorkitems wisessionbean = (WDWorkitems) session.getAttribute("wDWorkitems");
            if (wisessionbean == null) {
                wisessionbean = new WDWorkitems();
                session.setAttribute("wDWorkitems", wisessionbean);
            }
            
            
                if ( "Link".equals(callFrom) || "NewUser".equals(callFrom) || ( session.getAttribute("sessionId")!=null && !"".equals(session.getAttribute("sessionId")) ) ||  ( formsession.getM_strExistingFid() != null && !"".equals(formsession.getM_strExistingFid()) && objViewer.getM_objFormDef().isM_bNavigationAdded() )) {
                    if(session.getAttribute("isLogin") == null || ( formsession.getM_strExistingFid() != null && !"".equals(formsession.getM_strExistingFid()) )) {
                        if(formsession.getM_strApplicationName()!=null && !formsession.getM_strApplicationName().equals(""))    
                              session.setAttribute("isLogin", "false");
                        
                            IFormFragInfo fragmentObj=null;
                            if( objViewer.getM_objFormDef().getM_objNav() != null ){
                                if(!menuList.isEmpty()){
                                    Menu obj_menu=menuList.get(0);
                                    formsession.setM_strStage("0");
                                    formsession.setM_strSubStage("0");
                                    if(!formsession.getMainMenuData().isEmpty()){
                                        int mainstep=Integer.parseInt(formsession.getMainMenuData());
                                        formsession.setM_strStage(formsession.getMainMenuData());
                                        obj_menu=menuList.get(mainstep);
                                    } else if (objViewer.getM_objFormDef().isChangeList()){
                                        obj_menu = menuList.get(objViewer.getM_objFormDef().getCurrentStep());
                                    }
                                    if(obj_menu.getM_objSubMenuList().isEmpty())    
                                        fragmentObj = obj_menu.getObjFragment();
                                    else
                                    {
                                        if(!obj_menu.getM_objSubMenuList().isEmpty()){
                                            if(!formsession.getSubMenuData().isEmpty() && !"0".equalsIgnoreCase(formsession.getSubMenuData())){
                                                int stepnumber = Integer.parseInt(formsession.getSubMenuData());
                                                formsession.setM_strSubStage(formsession.getSubMenuData());
                                                fragmentObj = obj_menu.getM_objSubMenuList().get(stepnumber).getObjFragment();
                                            } else if(objViewer.getM_objFormDef().isChangeList() && objViewer.getM_objFormDef().getCurrentSubStep()!= -1){
                                                fragmentObj = obj_menu.getM_objSubMenuList().get(objViewer.getM_objFormDef().getCurrentSubStep()).getObjFragment();
                                            } else {
                                                fragmentObj = obj_menu.getM_objSubMenuList().get(0).getObjFragment();
                                            }
                                        }   
                                    }
                                    formsession.setM_strProcessDefId(processDefId);
                                    if( fragmentObj != null ){
                                        try{
                                            boolean isPreview = "Y".equals(request.getSession().getAttribute("IsPreview"));
                                            if( isPreview ){
                                                 IFormUtility.populateFragmentBuffer(request,objViewer.getM_objFormDef(),fragmentObj , formsession);
                                            }
                                            else
                                                CommonUtility.populateFormDefForFragment(request,objViewer.getM_objFormDef(), fragmentObj,formsession);
                                            if(!"N".equalsIgnoreCase(DeleteOldApplicationData))
                                                AppTasks.checkApplicationAlreadyFilled(request, response,false);
                                            CommonUtility.populateModelForFragment(request, objViewer.getM_objFormDef(), fragmentObj,formsession);
                                            if(!"N".equalsIgnoreCase(CreateTransactionOnLoad) && ("NewUser".equals(callFrom) || "Link".equals(callFrom)))
                                               AppTasks.createNewRecord(request, response);
                                            
                                        }
                                        catch(Exception ex){

                                        }
                                    }    else
                                        CommonUtility.populateModelForFragment(request, objViewer.getM_objFormDef(), null,formsession);
                                
                                }
                            }    else
                                CommonUtility.populateModelForFragment(request, objViewer.getM_objFormDef(), null,formsession);
                        
                        }
                        wdmodel=IFormUtility.getWDModel(request);
                        
                                designMenu = IFormUtility.createMenuHtml(objViewer.getM_objFormDef());
                            
                        }
            } else if(ApplicationType.equals("pms")) {
				IFormFragInfo fragmentObj = null;
                if (objViewer.getM_objFormDef().getM_objNav() != null) {
                    if (!menuList.isEmpty()) {
                        Menu obj_menu = menuList.get(0);
                        if (obj_menu.getM_objSubMenuList().isEmpty()) {
                            fragmentObj = obj_menu.getObjFragment();
                        } else if (!obj_menu.getM_objSubMenuList().isEmpty()) {
                            fragmentObj = obj_menu.getM_objSubMenuList().get(0).getObjFragment();
                        }
                        formsession.setM_strStage("0");
                        formsession.setM_strSubStage("0");
                        if (fragmentObj != null) {
                            try {
                                IFormUtility.populateFragmentBuffer(request, objViewer.getM_objFormDef(), fragmentObj, formsession);
                                CommonUtility.populateModelForFragment(request, objViewer.getM_objFormDef(), fragmentObj,formsession);
                                designMenu = IFormUtility.createMenuHtml(objViewer.getM_objFormDef());
                            } catch (Exception e) {
                            }
                        }
                    }
                }
			}
                           
        %>
        <%=  objViewer.getM_objFormDef().getRenderBlock(formsession, wdmodel)%>
        <%}%>
        <%       
                if(objViewer.getM_objFormDef().isChangeList()){
                    objViewer.getM_objFormDef().setListChanged(true);
                } 
        %>
        <% 
                String isShowHideBtn = "none";
                boolean MoveToTopButton=(IFormINIConfiguration.getM_hINIHashMap().get(IFormConstants.MOVE_TO_TOP_BUTTON) == null || "Y".equals(IFormINIConfiguration.getM_hINIHashMap().get(IFormConstants.MOVE_TO_TOP_BUTTON)));
                if(MoveToTopButton){
                    isShowHideBtn = "block";
                }else{
                    isShowHideBtn = "none";
                }
       %>
        <button id="moveToTopMainForm" style="background-color: rgb(0, 114, 198); display: <%=isShowHideBtn%>;float: right;"><svg width="18px" height="10px" viewBox="0 0 18 10" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"><title>Path 11 Copy</title><g id="Page-1" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd"><path d="M4,3 C4.51283584,3 4.93550716,3.38604019 4.99327227,3.88337887 L5,4 L5,13 L14,13 C14.5128358,13 14.9355072,13.3860402 14.9932723,13.8833789 L15,14 C15,14.5128358 14.6139598,14.9355072 14.1166211,14.9932723 L14,15 L4,15 C3.48716416,15 3.06449284,14.6139598 3.00672773,14.1166211 L3,14 L3,4 C3,3.44771525 3.44771525,3 4,3 Z" id="Path-11-Copy" fill="#FFFFFF" fill-rule="nonzero" transform="translate(9.000000, 9.000000) rotate(-225.000000) translate(-9.000000, -9.000000) "></path></g></svg></button>
        </div>
        <div class="modal"  id="menuModal" role="dialog" data-backdrop="static" style="z-index:2004;">
            <div class="modal-dialog" style="width:90%;margin:0px;top:50%;left:50%;transform:translate(-50%,-50%);">
                <div class="modal-content" style="height:400px;">
                    <div class="modal-header" style="padding: 5px 15px;"><%= lbls.getString("SELECT_STEPS")%></div>
                    <div  class="modal-body appendmenumodal" style="height:324px;overflow: auto;">
                        <%=designMenu%>

                    </div>                        
                    <div class="modal-footer table-responsive container-fluid" style="padding: 0px 12px 12px 0px; border: none;">
                        <button class="menuButton"><span class="menuButtonSpan" onclick="closeMenuModal();">Cancel</span></button>
                       </div>
                </div>
            </div>
        </div>
        <div class="modal"  id="searchModal" role="dialog" data-backdrop="static" style="z-index:2003;">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" id="closeButton" class="close" onclick="disablePrevious()" data-dismiss="modal">
                            <span class="glyphicon glyphicon-remove"></span>
                        </button>
                        <h4 class="modal-title" id="picklistHeader" style="word-break: break-word;"></h4>

                    </div>
                    <div  class="modal-body">

                        <div class="embed-responsive embed-responsive-16by9">
                            <iframe id="iFrameSearchModal" class="embed-responsive-item" src=""></iframe>
                        </div>

                    </div>                        
                    <div class="modal-footer table-responsive container-fluid">
                        <button type="button" onclick="showNextPreviousResult('previous')" id="picklistPrevious" class=" btn btn-primary btn-sm pull-left" disabled="true"> <%= lbls.getString("PREVIOUS")%></button>
                        <button style="width:70px;" type="button" onclick="showNextPreviousResult('next');" id="picklistNext" class="btn btn-primary pull-left btn-sm  "> <%= lbls.getString("NEXT")%></button>
                        <button style="width:70px;" type="button" onclick="setSelectedRow();" data-dismiss="modal" id="picklistOk" class=" btn btn-success btn-sm "> <%= lbls.getString("OK")%></button> 
                        <button style="width:70px;" type="button" id="picklistCancel" class=" btn btn-danger btn-default btn-sm" onclick="disablePrevious()" data-dismiss="modal"><span class="glyphicon "></span> <%= lbls.getString("CANCEL")%></button>
                    </div>
                </div>
            </div>
        </div>
                    
                    <div class="modal"  id="listViewModal" role="dialog" style="z-index:2002" data-backdrop="static" data-keyboard="false">
            <div id="iFrameListViewModal" class="modal-dialog modal-lg modal-md">
<!--                <div class="modal-content">
                    <div class="modal-header">
                        <button  type="button" id="closeButton" class="close" onclick="disablePrevious();removerowToModify();" data-dismiss="modal">
                            <span class="glyphicon glyphicon-remove"></span>
                        </button>
                        <h4 class="modal-title" id="listViewHeader" style="word-break: break-word;"></h4>

                    </div>
                    <div  class="modal-body">

                        <div class="embed-responsive embed-responsive-16by9">
                            <div id="iFrameListViewModal" ></div>
                        </div>
                        
                    </div>                        
                    
                </div>-->
            </div>
            <input id="table_id" style="display: none">
            <input id="rowCount" style="display: none">
        </div>
                    <div class="modal"  id="subFormModal" role="dialog" data-backdrop="static" style="z-index:2000">
                        <div id="iFrameSubFormModal" class="modal-dialog modal-lg modal-md">
                        </div>
                    </div>
                     <div  id="LinkModal" style="display:none;">
                         <iframe id="iFrameLinkModal" style="width:100%;height:100%"></iframe>
                    </div>
                    <div class="modal" id="advancedListViewModal" role="dialog" data-backdrop="static" data-keyboard="false" style="z-index:2001" >
                        <div id="iFrameAdvancedListViewModal" class="modal-dialog modal-lg">
                            
                        </div>
                        <input id="advancedListview_id" style="display: none">
                        <input id="advancedListviewRowCount" style="display: none">
                    </div>
                    <div id="cameraViewer" style="position: absolute;z-index: 199;background-color: #ffffff;border: 1px solid #d7d7d7;display: none">
                        <div id="cameraCloser" style="position: absolute;width: 20px;height: 20px;top: 5px;z-index: 200" title="Close" class="closeCamera" onclick="closeCameraViewer()"></div>
                        <div id="videoShower" style="position: absolute;padding-top: 25px;">
                            <video id="videoPlayer" autoplay=""></video>
                        </div>
                        <div id="imageClicker" style="position: absolute;width: 36px;height: 36px;bottom: 10px;" class="recapture" title="Capture Image" onclick="onTakePhotoButtonClick()"></div>
                        <div id="uploadImageCamera" style="position: absolute;width: 36px;height: 36px;bottom: 10px;" class="importImage" title="Upload Image" onclick="uploadImageFromCamera()"></div>
                        <div id="canvasShower" style="display: none;position: absolute;z-index: 199;">
<!--                            <canvas id="takePhotoCanvas"></canvas>-->
                        </div>
                        <div id="autoRotateOption" style="position: absolute;width: 36px;height: 36px;bottom: 10px;" class="switchcamera" title="Rotate Camera View" onclick="autoRotateCamera()"></div>
                        <div id="retakeOption" style="position: absolute;width: 36px;height: 36px;bottom: 10px;display:none" class="recapture" title="Capture Image" onclick="retakeImage()"></div>
                    </div>
                    <button type="button" style="display:none" onclick="saveForm(1)"/>
<!--                    <div id="pnlDialog" title="Alert" style="padding-top:10px;display: none;">                            
                        <br/>
                        <img src ="./resources/images/warning.png" style="margin:0 7px 20px 0;width:32px;height: 32px;"/>
                        <h:graphicImage id="dlgIcon" library="images" name="warning.png" style="margin:0 7px 20px 0;width:32px;height: 32px;" />
                        <div id="dlgContent" style="float:right;" layout="block" >
                        </div>                     
                    </div>-->

<!--                <div id="pnlDialog" style="width: auto; min-height: 0px; height: 93px;" class="ui-dialog-content ui-widget-content" scrolltop="0" scrollleft="0">                            
                <br>
                <img id="dlgIcon" src="./resources/images/warning.png" style="float:left; margin:0 7px 50px 0;width:32px;height: 32px;">
                <div id="dlgContent"></div>                            
            </div>-->
            <style>
                <%= objViewer.getM_objFormDef().getRenderCSS()%>
            </style>
            <script>
                $("#advancedListViewModal").on("hidden.bs.modal", function () {
                   <% if(mobileType.equals("mobile")) { %> 
                   var action=document.getElementById("advancedListViewModal").getAttribute("action");
                   var crossState=document.getElementById("closeButton").getAttribute("state");                                                                                               
                   clearAdvancedListviewMap(action,crossState);
                   removeAdvancedListviewrowToModify();
                   <% } else { %>
                       clearAdvancedListviewMap();
                   <% } %>    
                });
                $("#listViewModal").on("hidden.bs.modal", function () {                    
                    removerowToModify();
                });
                var transactionId = "<%=formsession.getM_strApplicationNo()%>";	
            </script>   
            <script type="text/javascript" src="resources/scripts/DocumentWidget.js"></script>
            <script type="text/javascript" src="resources/scripts/CameraUpload.js"></script>
            <%if(isPWA){%>
            <script src="https://www.gstatic.com/firebasejs/7.12.0/firebase-app.js"></script>
            <script src="https://www.gstatic.com/firebasejs/7.12.0/firebase-messaging.js"></script>

                    <%--    PWA Changes--%>
            <script type="text/javascript" src="../../pwa/configuration.js"></script>
            <script type="text/javascript" src="../../pwa/firebase-sw-init.js"></script>
            <%}%>
    </body>
   
</html>
