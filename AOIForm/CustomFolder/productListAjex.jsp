<%@page import="java.util.*,java.text.*"%>
<%@page import="com.newgen.wfdesktop.xmlapi.*,java.io.*,java.util.*"%>
<%@ page import="com.newgen.omni.wf.util.app.*"%>
<jsp:useBean id="customSession" class="com.newgen.custom.wfdesktop.session.WFCustomSession" scope="session"/>


<%
	try
	{

		String sWIName = request.getParameter("WI_NAME");	
		String sProductCode = request.getParameter("productCode");
		String sProductDesc = request.getParameter("productDesc");
		String sCurrency = request.getParameter("currency");	
		String sAccClass=request.getParameter("ACC_CLASS");			
		String sTableName=request.getParameter("TABLE");	
			
		String sQuery="";
		String sInput="";
		String sOutput="";
		String sRecord = "";
		String prod_code ="";
		String prod_desc ="";
		String cur="";
		String sChequebook="";
		// Updated by Abhay after upgrade
		String sCabname= customSession.getEngineName();
		String sSessionId = customSession.getDMSSessionId();
		String sUserName =  customSession.getUserName();
		String sJtsIp = customSession.getJtsIp();
		//String wd_uid=request.getParameter("session");
		String iJtsPort = String.valueOf(customSession.getJtsPort());
		String returnStr="";
		
		if(sSessionId=="")
		{
			out.print("<script>alert('Session has expired!!');</script>");
			out.print("<script>window.close();</script>");
		}
		if(!sWIName.equals(""))
		{
			String whereCls="";
			if(!sProductCode.equals(""))
			{
				whereCls=" and upper(b.PRODUCT_CODE) like upper('"+sProductCode.replace("*","%")+"')";
			}
			if(!sProductDesc.equals(""))
			{
				whereCls=whereCls+" and upper(b.PRODUCT_DESC) like upper('"+sProductDesc.replace("*","%")+"')";
			}
			if(!sCurrency.equals(""))
			{
				whereCls=whereCls+" and upper(b.CURRENCY_DESC) like upper('"+sCurrency.replace("*","%")+"')";
			}
			//out.println("<script>alert(\""+whereCls+"\")</script>");
			
			sQuery= "SELECT PRODUCT_CODE,PRODUCT_DESC,CURRENCY_SHORT_NAME CURRENCY_SHORT_NAME,(SELECT DECODE(CHEQUE_BOOK_FAC,'Y','Yes','No') FROM USR_0_PRODUCT_MASTER A WHERE PRODUCT_CODE = B.PRODUCT_CODE) CHEQUE_BOOK,(SELECT CODE FROM USR_0_HOME_BRANCH WHERE CODE=(CASE WHEN (SELECT COD_PROD_TYPE FROM USR_0_PRODUCT_MASTER A WHERE A. PRODUCT_CODE= B.PRODUCT_CODE )='C' THEN ((SELECT TO_CHAR(CODE) FROM USR_0_HOME_BRANCH WHERE HOME_BRANCH='"+sAccClass+"')) ELSE (SELECT DECODE(TO_CHAR(EQV_ISLAMIC_BR_CODE),NULL,TO_CHAR(CODE),TO_CHAR(EQV_ISLAMIC_BR_CODE)) FROM USR_0_HOME_BRANCH WHERE CODE=(SELECT CODE FROM USR_0_HOME_BRANCH WHERE HOME_BRANCH='"+sAccClass+"')) END )) CODE FROM "+sTableName+" b WHERE UPPER(WI_NAME) = UPPER('"+sWIName+"') "+whereCls+" ORDER BY TO_NUMBER(PRODUCT_CODE)";
			//out.println("<script>alert(\""+sQuery+"\")</script>");
			
			sQuery="SELECT b.PRODUCT_CODE,b.PRODUCT_DESC,b.CURRENCY_SHORT_NAME,DECODE(a.CHEQUE_BOOK_FAC,'Y','Yes','No') CHEQUE_BOOK,(case when a.COD_PROD_TYPE='C' then TO_CHAR(c.CODE) else DECODE(TO_CHAR(c.EQV_ISLAMIC_BR_CODE),NULL,TO_CHAR(c.CODE),TO_CHAR(c.EQV_ISLAMIC_BR_CODE)) end) CODE from "+sTableName+" B,USR_0_PRODUCT_MASTER A,USR_0_HOME_BRANCH c where a.product_code= b.product_code and UPPER(b.WI_NAME) = UPPER('"+sWIName+"') and a.currency_code= b.currency_code and  c.HOME_BRANCH='"+sAccClass+"' "+whereCls+" ORDER BY TO_NUMBER(PRODUCT_CODE)";
			//out.println("<script>alert(\""+sQuery+"\")</script>");
		}
			
		sInput="<?xml version=1.0?><APSelectWithColumnNames_Input><Option>APSelectWithColumnNames</Option><QueryString>"+sQuery+"</QueryString><EngineName>"+sCabname+"</EngineName><SessionId>"+sSessionId+"</SessionId></APSelectWithColumnNames_Input>";
		
		if(!sInput.equals(""))
		{
			sOutput=NGEjbClient.getSharedInstance().makeCall(sJtsIp,iJtsPort,"WebSphere",sInput);
		}
		
		if(!sOutput.equals(""))
		{
			String sMainCode = sOutput.substring(sOutput.indexOf("<MainCode>")+10,sOutput.indexOf("</MainCode>"));
			
			if(sMainCode.equalsIgnoreCase("0"))
			{	
				int i=1;				
				while((sOutput.indexOf("<Record>")>-1))
				{
					sRecord = sOutput.substring(sOutput.indexOf("<Record>")+8,sOutput.indexOf("</Record>"));
					prod_code =sRecord.substring(sRecord.indexOf("<PRODUCT_CODE>")+14,sRecord.indexOf("</PRODUCT_CODE>"));
					prod_desc =sRecord.substring(sRecord.indexOf("<PRODUCT_DESC>")+14,sRecord.indexOf("</PRODUCT_DESC>"));
					cur =sRecord.substring(sRecord.indexOf("<CURRENCY_SHORT_NAME>")+21,sRecord.indexOf("</CURRENCY_SHORT_NAME>"));
					sChequebook =sRecord.substring(sRecord.indexOf("<CHEQUE_BOOK>")+13,sRecord.indexOf("</CHEQUE_BOOK>"));
					sAccClass=sRecord.substring(sRecord.indexOf("<CODE>")+6,sRecord.indexOf("</CODE>"));
					
					if(i==1)
					{
						returnStr=prod_code+"#"+prod_desc+"#"+cur+"#"+sChequebook+"#"+sAccClass;
					}
					else
					{

						returnStr=returnStr+"#"+prod_code+"#"+prod_desc+"#"+cur+"#"+sChequebook+"#"+sAccClass;
					}
					
					i++;
					sOutput=sOutput.substring(sOutput.indexOf("</Record>")+"</Record>".length());
					
				}
				out.println(returnStr);//to return
			}	
			else
			{
				out.println("1");
			}
			
		}
	}	
	catch(Exception e)
	{
		out.print("<script>alert('Exception occ');</script>");
	}
%>
