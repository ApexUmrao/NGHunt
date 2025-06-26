<%!
/**
----------------------------------------------------------------------------------------------------
				NEWGEN SOFTWARE TECHNOLOGIES LIMITED
	Group						:	AP2
	Product / Project			:	Trade Finance
	Module						:	Custom JSP
	File Name					:	displaySignatureAndMandate.jsp
	Author						:	Ashwani Kumar
	Date written (DD/MM/YYYY)	:	22-10-2018
	Description					:	This is the custom jsp to display Signature and Account mandate  fetched from the FCUBS system.
----------------------------------------------------------------------------------------------------
				CHANGE HISTORY
----------------------------------------------------------------------------------------------------
	Date(DD/MM/YYYY)		 Change By	 			Change ID/Bug Id				Change Description (Bug No. (If Any))
	10/12/2018				Ashwani Kumar			  Bug-US652 				    Special character handling for account mandate
 
----------------------------------------------------------------------------------------------------
*/


%>



<%@page  import="java.util.*"%>

<%@page import=" java.text.DateFormat"%>
<%@page import=" java.text.SimpleDateFormat"%>
<%@page import=" java.util.Calendar"%>	
<%@page import=" java.io.*"%>
<%@page import="com.newgen.omni.jts.cmgr.XMLParser"%>

<%@page import="java.util.*,java.text.*"%>
<%@page import="java.io.*,java.util.*,com.newgen.omni.wf.util.app.*"%>
<jsp:useBean id="customSession" class="com.newgen.custom.wfdesktop.session.WFCustomSession" scope="session"/>
<%@ page import=="com.newgen.iforms.user.tfo.util.ConnectSocket"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.Properties"%>
<%@page import="org.apache.log4j.PropertyConfigurator"%>
<%@page import ="org.apache.log4j.Logger"%>
<html>
<head>
<style>

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

element.style {
    word-break: break-word;
    margin-top: 0px;
    margin-bottom: 0px;
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
.thStyle{
	 min-height: 26px;
     min-width: 128px;
	 background-color: #ba1b18;
}
.buttonStyle {
    color: #ffffff;
}
.button-viewer {
    min-height: 26px;
    min-width: 128px;
    /* max-width: 256px; */
}
</style>
</head>
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
<script>
function closeandReturn()
{
	
		
	window.parent.handleJSPResponsePPM('SIGNANDACC',"");	
	//setPMSFData('btnSubmit',val);
	
	//window.close();
}
</script>
<%!
	String custId = "";
	String accNum = "";
	String mandate = "";
	String sysrefno = "";
	String sInput="";
	String sOutput="";
	String senderId;
	String signatureBinary;
	String signatureName;
	String signatureID;
	String signatureType;
	boolean testingFlag=false;
	XMLParser xp;
	StringBuffer inputXml;
	boolean mandateFlag ;
	ConnectSocket socket;
%>

<%
       initializeLogger();
	   String sCabname=customSession.getEngineName();
		String sSessionId = customSession.getDMSSessionId();
		String sUserName = customSession.getUserName();
		String sJtsIp = customSession.getJtsIp();
		String iJtsPort = String.valueOf(customSession.getJtsPort());
		
	//socket = ConnectSocket.getReference("10.101.109.182", 4444);
    String sQueryBPM = "SELECT (SELECT VALUE FROM BPM_SERVICE_CONFIG WHERE KEY = 'SOCKETIP') IP,(SELECT VALUE FROM BPM_SERVICE_CONFIG WHERE KEY = 'SOCKETPORT') PORT FROM DUAL";
	String sInputBPM = prepareAPSelectInputXml(sQueryBPM,sCabname,sSessionId);
	WriteToLog_showpage("Y"," ***************Ref number Input "+sInputBPM+" *****************************");
	String sOutputBPM = NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInputBPM);
	WriteToLog_showpage("Y"," ***************Ref number output = "+sOutputBPM+"  *****************************");
	try
	{
		WriteToLog_showpage("Y","started");
		String ipBPM = sOutputBPM.substring(sOutputBPM.indexOf("<IP>")+4,sOutputBPM.indexOf("</IP>"));
		WriteToLog_showpage("Y","started  1");
		int portBPM = Integer.parseInt(sOutputBPM.substring(sOutputBPM.indexOf("<PORT>")+6,sOutputBPM.indexOf("</PORT>")));
		WriteToLog_showpage("Y","ipBPM : " +ipBPM);
		WriteToLog_showpage("Y"," portBPM : " +portBPM);
		socket = ConnectSocket.getReference(ipBPM, portBPM);
	}
	catch(Exception e)
	{
	}
	custId = request.getParameter("cust_id");
	accNum = request.getParameter("AccountNumber");
	mandate = request.getParameter("mandate");	
	String finalString = "";
	mandate.toString().replaceAll("(\r\n|\n\r|\n)", "<br />");
	String buttonHtml="";
	String head ="<head><style>.rotate270 {filter: progid:DXImageTransform.Microsoft.Matrix(M11=6.123233995736766e-17,M12=1,M21=-1,M22=6.123233995736766e-17,SizingMethod='auto expand');}					.rotate90 {filter: progid:DXImageTransform.Microsoft.Matrix(M11=-1.8369701987210297e-16,M12=-1,M21=1,M22=-1.8369701987210297e-16,SizingMethod='auto expand');}.rotate180 {filter: progid:DXImageTransform.Microsoft.Matrix(M11=-1,M12=1.2246467991473532e-16,M21=-1.2246467991473532e-16,M22=-1,SizingMethod='auto expand');}.rotate0 {filter: progid:DXImageTransform.Microsoft.Matrix(M11=1,M12=0,M21=0,M22=1,SizingMethod='auto expand');}</style></head>";
	//buttonHtml=head+buttonHtml;
	
	WriteToLog_showpage("Y","\n\n\nMandate before replace "  +mandate.toString());	
	
	mandate = mandate.replaceAll("~amp~","&");
	mandate = mandate.replaceAll("~perc~","%");
	mandate = mandate.replaceAll("~ques~","?");
	mandate = mandate.replaceAll("~hash~","#");
	mandate = mandate.replaceAll("~sp~","=");

	WriteToLog_showpage("Y","\n\n\nMandate after replace "  +mandate.toString());	
	
	if("".equalsIgnoreCase(custId)){
		WriteToLog_showpage("Y","\n\n\nMandate inside cid null");
		mandateFlag = true;
	}
	else{
		//buttonHtml=buttonHtml+"<div><button name='Rotate' onclick='rotate(\"L\");'>Rotate Left</button><button name='Rotate' onclick='rotate(\"R\");'>Rotate Right</button></div><br /><br />";
		mandateFlag = false;
	}
	WriteToLog_showpage("Y","\n\n\nMandate flag"+mandateFlag);
	WriteToLog_showpage("Y","testingFlag flag"+testingFlag);
	if(!mandateFlag){
			if(!testingFlag){
				sInput = prepareAPSelectInputXml("SELECT SEQ_WEBSERVICE.nextval as sysrefno from DUAL ", sCabname, sSessionId);
				WriteToLog_showpage("Y"," sInput="+sInput);
				sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
				WriteToLog_showpage("Y"," sOutput="+sOutput);
				xp = new XMLParser(sOutput);
				sysrefno = xp.getValueOf("sysrefno");
				WriteToLog_showpage("Y"," sysrefno"+sysrefno);
				sInput = prepareAPSelectInputXml("SELECT VALUE as senderId FROM USR_0_DEFAULTVALUE_FCR WHERE NAME ='TFO_SENDERID'", sCabname, sSessionId);
				sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
				xp = new XMLParser(sOutput);
				senderId = xp.getValueOf("senderId");
				
				sInput = prepareAPSelectInputXml("SELECT DEF_VALUE FROM TFO_DJ_DEFAULT_MAST WHERE DEF_NAME ='TFO_CUST_SIGNATURE_NEW'", sCabname, sSessionId);
				sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
				xp = new XMLParser(sOutput);				
				inputXml = new StringBuffer();
				WriteToLog_showpage("Y","DEF_VALUE "  +xp.getValueOf("DEF_VALUE"));	
				if(xp.getValueOf("DEF_VALUE").equalsIgnoreCase("true")){
					inputXml.append("<APWebService_Input>")
						.append("<Option>WebService</Option>")
						.append("<Calltype>WS-2.0</Calltype>")
						.append("<InnerCalltype>TFO_CustSignInfo</InnerCalltype>")
						.append("<EngineName>"+sCabname+"</EngineName>")
						.append("<SessionId>"+sSessionId+"</SessionId>")			
						.append("<sysRefNumber>" + sysrefno + "</sysRefNumber>")
						.append("<senderID>" + senderId + "</senderID>")
						.append("<ServiceName>InqCustSignatureInfo</ServiceName>")
						.append("<custSignatureInfoReqMsg>")
						.append("<customerID>" + custId + "</customerID>")
						//.append("<AccountNumber>"+ accNum +"</AccountNumber>")
						//.append("<customerID>663226</customerID>")
						//.append("<AccountNumber>663226920003</AccountNumber>")
						.append("<signatureType>1</signatureType>")
						.append("</custSignatureInfoReq>")
						.append("</custSignatureInfoReqMsg>")
						.append("</APWebService_Input>");
					WriteToLog_showpage("Y","\n\nSignature String inputXml.toString() "  +inputXml.toString());	
					sOutput=socket.connectToSocket(inputXml.toString());
					//sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",inputXml.toString());
					WriteToLog_showpage("Y","\n\nSignature String  sOutput"  +sOutput);
					xp = new XMLParser(sOutput);
					int start = xp.getStartIndex("custSignatureInfoRes", 0, 0);
					int deadEnd = xp.getEndIndex("custSignatureInfoRes", start, 0);
					int noOfFields = xp.getNoOfFields("signatures", start,deadEnd);
					int end = 0;
					if(noOfFields > 0){
						for(int i = 1; i <= noOfFields; ++i){
							start = xp.getStartIndex("signatures", end, 0);
							end = xp.getEndIndex("signatures", start, 0);										
							signatureBinary = xp.getValueOf("signature", start, end);
							signatureName = xp.getValueOf("signatureName", start, end);
							signatureID = xp.getValueOf("signatureID", start, end);
							signatureType = xp.getValueOf("signatureType", start, end);
							WriteToLog_showpage("Y","\n\nSignature String  "  + i + ": "+signatureBinary);
							if(i == 1){
								finalString = head + "<div style='margin: 10px;border: 2px solid black'><table style='width:90%'><tr><td style='background:#ba1b18'><label style='color: white;background: #ba1b18'><b>SIGNATURE NAME: </b></label><br/><label style='color: white;background: #ba1b18'><b>SIGNATURE ID: </b></label><br/><label style='color: white;background: #ba1b18'><b>SIGNATURE TYPE: </b></label></td><td style='background:#ba1b18; margin:5px;'><label style='color: white;background: #ba1b18'><b>"+ signatureName +"</b></label><br/><label style='color: white;background: #ba1b18'><b>"+ signatureID +"</b></label><br/><label style='color: white;background: #ba1b18'><b>"+ signatureType +"</b></label></td><td align=\"right\"><button style='margin:5px; color:white; background:#ba1b18' name='Rotate' onclick='initRotate(\"L\",\""+ (i) +"\","+noOfFields+");'><b>Rotate Left</b></button><button style='margin:5px; color:white; background:#ba1b18' name='Rotate' onclick='initRotate(\"R\",\""+ (i) +"\","+noOfFields+");'><b>Rotate Right</b></button></td></tr></table></div><div style='margin: 10px; text-align:center;' style='padding:50px; border: 2px solid black'><img id='image"+ (i) +"' style='margin:auto;' src='data:image/jpg;base64, "+ signatureBinary  + "'></div>";
							} else {
								finalString += "<div style='margin: 10px;border: 2px solid black'><table style='width:90%'><tr><td style='background:#ba1b18'><label style='color: white;background: #ba1b18'><b>SIGNATURE NAME: </b></label><br/><label style='color: white;background: #ba1b18'><b>SIGNATURE ID: </b></label><br/><label style='color: white;background: #ba1b18'><b>SIGNATURE TYPE: </b></label></td><td style='background:#ba1b18; margin:5px;'><label style='color: white;background: #ba1b18'><b>"+ signatureName +"</b></label><br/><label style='color: white;background: #ba1b18'><b>"+ signatureID +"</b></label><br/><label style='color: white;background: #ba1b18'><b>"+ signatureType +"</b></label></td><td align=\"right\"><button style='margin:5px; color:white; background:#ba1b18' name='Rotate' onclick='initRotate(\"L\",\""+ (i) +"\","+noOfFields+");'><b>Rotate Left</b></button><button style='margin:5px; color:white; background:#ba1b18' name='Rotate' onclick='initRotate(\"R\",\""+ (i) +"\","+noOfFields+");'><b>Rotate Right</b></button></td></tr></table></div><div style='margin: 10px; text-align:center;' style='padding:50px; border: 2px solid black'><img id='image"+ (i) +"' style='margin:auto;' src='data:image/jpg;base64, "+ signatureBinary  + "'></div>";
							}
						}
					} else {
						finalString = head + "<div style='margin: 10px;border: 2px solid black'><table style='width:90%'><tr><td style='background:#ba1b18'><label style='color: white;background: #ba1b18'><b>SIGNATURE NAME: </b></label><br/><label style='color: white;background: #ba1b18'><b>SIGNATURE ID: </b></label><br/><label style='color: white;background: #ba1b18'><b>SIGNATURE TYPE: </b></label></td><td style='background:#ba1b18; margin:5px;'><label style='color: white;background: #ba1b18'><b></b></label><br/><label style='color: white;background: #ba1b18'><b></b></label><br/><label style='color: white;background: #ba1b18'><b></b></label></td><td align=\"right\"><button style='margin:5px; color:white; background:#ba1b18' name='Rotate' ><b>Rotate Left</b></button><button style='margin:5px; color:white; background:#ba1b18' name='Rotate' ><b>Rotate Right</b></button></td></tr></table></div><div style='margin: 10px; text-align:center;' style='padding:50px; border: 2px solid black'><label style='color: white;background: #ba1b18'><b>NO SIGNATURE FOUND</b></label></div>";
					}
								
				
				} else {
					inputXml.append("<APWebService_Input>")
						.append("<Option>WebService</Option>")
						.append("<Calltype>TFO_CustSignInfo</Calltype>")
						.append("<EngineName>"+sCabname+"</EngineName>")
						.append("<SessionId>"+sSessionId+"</SessionId>")			
						.append("<sysRefNumber>"+ sysrefno +"</sysRefNumber>")
						.append("<senderID>" + senderId + "</senderID>")
						.append("<ServiceName>InqCustSignatureInfo</ServiceName>")
						.append("<custSignatureInfoReqMsg>")
						//.append("<custSignatureInfoReq>")
						.append("<customerID>" + custId + "</customerID>")
						//.append("<customerID>10027069</customerID>")
						.append("<signatureType>1</signatureType>")
						.append("</custSignatureInfoReq>")
						.append("</custSignatureInfoReqMsg>")
						.append("</APWebService_Input>");
					WriteToLog_showpage("Y","\n\nSignature String inputXml.toString() "  +inputXml.toString());	
				    sOutput=socket.connectToSocket(inputXml.toString());
					xp = new XMLParser(sOutput);
					signatureBinary = xp.getValueOf("signature");
				    //signatureBinary = "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";

					WriteToLog_showpage("Y","\n\nSignature String  "  +signatureBinary);
					
					finalString = head + "<div><button name='Rotate' onclick='rotate(\"L\",\"1\");'>Rotate Left</button><button name='Rotate' onclick='rotate(\"R\",\"1\");'>Rotate Right</button></div><br /><br /><div style='padding:90px'><img id='image1' style='margin:auto;height:60%;width:90%;' src='data:image/jpg;base64, "+ signatureBinary  + "'></div>" ;
				}
				
				
				
			}
			else{
				
				finalString = buttonHtml + "<div style='padding:90px'><img id='image' style='margin:auto;height:60%;width:90%;' src='data:image/jpg;base64, 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'></div>";
			}
			WriteToLog_showpage("Y","\n\nSignature String HTML "  +finalString);
			out.println(finalString);
	}
	else{
		
		WriteToLog_showpage("Y","\n\n\nMandate ");
		WriteToLog_showpage("Y","\n\n\nMandate value : "+mandate);
		out.println("<div><p>"+mandate+"</p></div>");
		WriteToLog_showpage("Y","\n\n\nMandate value after outprintln : "+mandate);
	}
	
	
%>

<script language="javascript" type="text/javascript">
var rot=new Array();
//alert(rot.length);

function initRotate(direction,imageid,size){
	
	if(rot.length > 0){
		rotate(direction,imageid);
	} else {
		for(var i=0;i<size;++i){
			rot[i]=0;	
		}	
		//alert(rot.length);
		rotate(direction,imageid);
	}
}

function rotate(direction,imageid){
    var x = document.getElementById('image'+imageid);
    direction == 'L' ? rot[imageid-1] -= 90 :rot[imageid-1] += 90;        
    rot[imageid-1] == 360?rot[imageid-1]=0:rot[imageid-1]=rot[imageid-1];
    rot[imageid-1] == -360?rot[imageid-1]=0:rot[imageid-1]=rot[imageid-1];
	x.className="";	
	//alert(rot[imageid-1]);
	if(rot[imageid-1] == 90 || rot[imageid-1] == -270){
		x.className="rotate90";
	}
	if(rot[imageid-1] == -90 || rot[imageid-1] == 270){
		x.className="rotate270";
	}	
	if(rot[imageid-1] == 0 || rot[imageid-1] == 360 || rot[imageid-1] == 360){
		x.className="rotate0";
	}
	if(rot[imageid-1] == 180 || rot[imageid-1] == -180 ){
		x.className="rotate180";
	}
    
}
 
</script>

<!-- <img style="margin:auto;height:60%;width:90%;" src="data:image/jpg;base64, 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">  -->

</html>

<%!
	

private String prepareAPSelectInputXml(String sQuery, String sCabname, String sSessionId){	
	return "<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+sQuery+"</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
}
private String prepareAPUpdateInputXml(String tableName,String colname,String sValues,String whrcls, String sCabname, String sSessionId){	
	return "<?xml version=\"1.0\"?><APUpdate_Input><Option>APUpdate</Option><TableName>"+tableName+"</TableName><ColName>"+colname+"</ColName><Values>"+sValues+"</Values><WhereClause>"+whrcls+"</WhereClause><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APUpdate_Input>";
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
		
	Logger log = Logger.getLogger(_displaySignatureAndMandate.class);
	
	private void  WriteToLog_showpage(String flag,String strOutput){
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
			strFilePath.append("displaySignatureAndMandate"+DtString+".xml");
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
    
}
*/
private static String StackTraceToString_showpage(Exception e) {
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        e.printStackTrace(printWriter);
        return result.toString();
}
%>
