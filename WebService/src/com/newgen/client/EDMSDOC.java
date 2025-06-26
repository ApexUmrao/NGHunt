package com.newgen.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class EDMSDOC extends WebServiceHandler
{
	protected static String sCabinet="";
	protected static String IP="";
	protected static String Port="";
	
	public String GetEDMSQuery (String inputxml) 
	{
		WebServiceHandler sHandler= new WebServiceHandler();
		try 
		{
			sHandler.readCabProperty("EDMS");
			IP=(String)currentCabPropertyMap.get("IP");
			Port=(String)currentCabPropertyMap.get("PORT");
			sCabinet=(String)currentCabPropertyMap.get("CABINET");
			LogGEN.writeTrace("Log", "CABINET---- "+(String)currentCabPropertyMap.get("CABINET"));
		} 
		catch (Exception e1) 
		{
			e1.printStackTrace();
		}
		
		String custid=inputxml.substring(inputxml.indexOf("<CUST_ID>")+9,inputxml.indexOf("</CUST_ID>"));
		String docname=inputxml.substring(inputxml.indexOf("<Doc_Name>")+10,inputxml.indexOf("</Doc_Name>"));
		String output="";
		String ntquery="SELECT C1.FOLDERINDEX FI3, to_char(d.reviseddatetime,'dd/mm/yyyy') Rdate,  d.documentindex Docindex FROM PDBFOLDER P, PDBFOLDER C, PDBFOLDER C1, pdbdocument d, pdbdocumentcontent dc WHERE upper(P.NAME) ='CUSTOMER LIST' AND P.parentfolderindex=0 AND P.FOLDERINDEX=C.parentfolderindex AND C.FOLDERINDEX=C1.parentfolderindex AND upper(C1.NAME)='BANK WIDE DOCUMENTS' and d.documentindex=dc.documentindex and c1.folderindex= dc.parentfolderindex and UPPER(d.name) like UPPER('%"+ docname +"%') and c.name ='"+custid+"' and rownum =1 order by d.reviseddatetime";
		 		
		ntquery="<?xml version=\1.0\"?>"+
		"<APSelect_Input>"+ 
		"<Option>APSelect</Option>"+
		"<EngineName>"+sCabinet+"</EngineName> "+
		"<SessionId></SessionId>"+
		"<Query>" + ntquery + "</Query>"+
		"</APSelect_Input>";  
		LogGEN.writeTrace("Log", ntquery);
		try {
			if(!custid.equalsIgnoreCase(""))
			{
				
				output=execute(ntquery,IP,Integer.parseInt( Port),0);
				LogGEN.writeTrace("Log", output);
				String temp=output.substring(output.indexOf("<TotalRetrieved>")+16,output.indexOf("</TotalRetrieved>"));
				String FI3=output.substring(output.indexOf("<td>")+4,output.indexOf("</td>"));
				LogGEN.writeTrace("Log", "F13:"+FI3);
				String out1=output.substring(output.indexOf("</td>")+1);
				LogGEN.writeTrace("Log", out1);
				String Rdate=out1.substring(out1.indexOf("<td>")+4,out1.indexOf("</td>"));
				LogGEN.writeTrace("Log", "F13:"+Rdate);
				out1=out1.substring(out1.indexOf("</td>")+1);
				String Docindex=out1.substring(out1.indexOf("<td>")+4,out1.indexOf("</td>"));
				LogGEN.writeTrace("Log", "sdfsdfsd:"+Docindex);		
				LogGEN.writeTrace("Log", "TEMPPP:"+temp);
				if(temp.equalsIgnoreCase("0"))
					output="";
				else
				{
					output="<?xml version=\1.0\"?><Output>"+
					"<FI3>"+FI3+"</FI3><Rdate>"+Rdate+"</Rdate><Docindex>"+Docindex+"</Docindex></Output>";
				}
			}
			else
				output="";
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			output="";
			LogGEN.writeTrace("Log", e.getMessage());
		}
		
		LogGEN.writeTrace("Log", output);
		return output;
	}

	public static String execute(String inputXMLString, String jtsIP, int jtsPort, int debug)
		    throws IOException, Exception
		  {
		    String recieveBuffer = new String(new byte[0], "8859_1");
		    XMLParser parser=null;
		    Socket sock = null;
		   // while (i < 3)
		   // {
		      try
		      {
		        sock = new Socket();
		        sock.connect(new InetSocketAddress( jtsIP, jtsPort));
		      }
		      catch (Exception e)
		      {
		    	
		        
		        
		          throw new Exception("Workflow Server Down");		        
		        
		      }
		     
		   // }

		    long l1 = System.currentTimeMillis();
		    parser = new XMLParser();
		    parser.setInputXML(inputXMLString);
		    String tempName = parser.getValueOf("Option");

		    recieveBuffer = readWriteObject(inputXMLString, sock);
		    String strTemp = " Time Taken in " + tempName + " is  :   " + (System.currentTimeMillis() - l1);

		    if (debug == 0) {
		      System.out.println("\n Omniflow Web Client Call - " + strTemp + "\nData Sent in bytes= " + inputXMLString.length() + "   Data Received= " + recieveBuffer.length());
		    }
		    sock.close();

		    if (debug == 1)
		    {
		      System.out.println(strTemp + "Data Sent in bytes= " + inputXMLString.length() + "   Data Received= " + recieveBuffer.length() +  "\n" +inputXMLString + "\n" + recieveBuffer + "\r\n\r\n");
		    }
		    parser=null;
		    return recieveBuffer;
	}
	private static String readWriteObject(String inputXMLString, Socket sock)
			    throws IOException
	{
			    DataOutputStream oOut = new DataOutputStream(new BufferedOutputStream(sock.getOutputStream()));
			    DataInputStream oIn = new DataInputStream(new BufferedInputStream(sock.getInputStream()));
			    byte[] SendStream = inputXMLString.getBytes("8859_1");
			    int strLen = SendStream.length;
			    oOut.writeInt(strLen);
			    oOut.write(SendStream, 0, strLen);
			    oOut.flush();
			    int length = 0;
			    length = oIn.readInt();
			    byte[] readStream = new byte[length];

			    oIn.readFully(readStream);
			    String stroutputXML = new String(readStream, "8859_1");

			    return stroutputXML;
	}

}
