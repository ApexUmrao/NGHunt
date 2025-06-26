package com.newgen.dscop.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LogGEN
{
	public LogGEN()
	{
	}
	public static String sUserDir = System.getProperty("user.dir");
	public static String sFileSep = System.getProperty("file.separator");
	
	public static File getLogFile(String sFileName,String sFilePath) throws Exception
	{
		File sLogFile ;
		try
		{
			String sNewFileName = ""; 
			for(int iSeqNum = 1;iSeqNum <= 10;iSeqNum++)
			{
				sNewFileName = sFileName + "_" + iSeqNum;
				sLogFile = new File(sFilePath + sFileSep +sNewFileName+".log"); 
				if(sLogFile.exists())
				{
					if( sLogFile.length()/(1024*1024) <= 2)
					{
						//System.out.println("using same file "+sLogFile.getName());
						return sLogFile;
					}
					else
						continue;
				}
				else
				{
					//System.out.println("creating new file"+sLogFile);
					sLogFile.createNewFile();
					return sLogFile;
					
				}
			}
			//System.out.println("All files used deleting files.");
			//Delete All
			for(int i = 1 ;i <= 10 ;i++ )
			{
				sNewFileName = sFileName + "_" + i;
				sLogFile = new File(sFilePath + sFileSep + sNewFileName+".log");
				sLogFile.delete();
				//System.out.println("Deleted"+sLogFile.getName());				
			}
			
			//Creating a new file with the 1st sequence.
			sNewFileName = sFileName + "_1";
			sLogFile = new File(sFilePath + sFileSep +sNewFileName+".log");
			sLogFile.createNewFile();
			return sLogFile;
		}catch(Exception ex)
		{
			throw ex;
		}
	}	
	
	public static void writeTrace(String sFileName,String sData) 
	{
		Calendar cal = Calendar.getInstance();
		File logFile;
		File logFolder; 
		String sLogFolderPath = "";
		
		try
		{	
			sLogFolderPath = DSCOPServiceHandler.currentCabPropertyMap.get("LogPath")+sFileSep+new SimpleDateFormat("ddMMyyyy").format(cal.getTime());
			logFolder = new File(sLogFolderPath);
			if (!logFolder.exists())
				logFolder.mkdirs();

			logFile = getLogFile(sFileName,sLogFolderPath);
			FileOutputStream fos = new FileOutputStream(logFile,true);
			String outData = (new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss")).format(cal.getTime())+"  "+sData+"\n";
			Writer out = new OutputStreamWriter(fos,"UTF8");
			out.write(outData);
			out.close();
		}
		catch(Exception ex)
		{
			System.out.println("unable to write");
		}
	}

}