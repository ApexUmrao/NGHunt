package com.newgen.ao.laps.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import com.newgen.ao.laps.logger.LapsModifyLogger;

public class ConnectSocket {
	private static String socketIP;
	private static int socketPort;
	private static ConnectSocket socket = null;

	private ConnectSocket(String socketIP, int socketPort){
		initSocket(socketIP, socketPort);
	}
	
	public static ConnectSocket getReference(String socketIP, int socketPort){
		if(socket == null){
			socket = new ConnectSocket(socketIP, socketPort);
		}
		return socket;
	}
	
	private void setSocketIP(String socketIP) {
		this.socketIP = socketIP;
	}

	private void setSocketPort(int socketPort) {
		this.socketPort = socketPort;
	}
	
	public static String connectToSocket(String inputXml){
		String response = "";
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Socket- socketIP: "+ socketIP +
				"; socketPort:"+ socketPort);
		try(Socket s = new Socket(socketIP, socketPort)){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside try block");
			DataInputStream din = new DataInputStream(s.getInputStream());
			DataOutputStream dout = new DataOutputStream(s.getOutputStream());
			writeDataToSocket(dout, inputXml);
			response = readDataFromSocket(din);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "connectToSocket response: "+response);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"Error in socket connection: ");	
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);			
		}
		return response;
	}
	
	private static boolean writeDataToSocket(DataOutputStream dataOutputStream, String data) {
		boolean bFlag = false;
		try {
			if (data != null && data.length() > 0) {
				dataOutputStream.write(data.getBytes(StandardCharsets.UTF_8));
				bFlag = true;
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"Error in socket read/write: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
		}
		return bFlag;
	}

	private static String readDataFromSocket(DataInputStream dataInputStream) {
		StringBuilder sb = new StringBuilder();
		BufferedReader br=null;
		try {
			br=new BufferedReader(new InputStreamReader(dataInputStream));
			String line;
			
			while((line=br.readLine()) !=null){
				sb.append(line);
			}
			/*int length = dataInputStream.read(buffer, 0, 999999999);
			byte[] arrayBytes = new byte[length];
			System.arraycopy(buffer, 0, arrayBytes, 0, length);
			data.append(new String(arrayBytes, StandardCharsets.UTF_8));
			int len = 0;
			while ((len = dataInputStream.read(buffer)) > 0) {
				arrayBytes = new byte[len];
				System.arraycopy(buffer, 0, arrayBytes, 0, len);
				data.append(new String(arrayBytes, StandardCharsets.UTF_8));
				if (dataInputStream.available() <= 0) {
					break;
				}				
			}*/
			
			
		}catch (IOException e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"Error in socket read/write: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"Error in socket read/write: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
		}finally{
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"Error in socket read/write: ");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
				}
			}
		}
		return sb.toString();
	}
	
	public String connectToSocket_New(String inputXml){
		String response = "";
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inputXml in Socket: "+ inputXml);	
		try(Socket s = new Socket(socketIP, socketPort)){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Socket: "+ socketIP +":"+ socketPort);
			//int count = s.getReceiveBufferSize();
			//LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"count : " +count);			
			DataInputStream din = new DataInputStream(s.getInputStream());
			DataOutputStream dout = new DataOutputStream(s.getOutputStream());
			writeDataToSocket(dout, inputXml);
			//s.setSoTimeout(60000);
			response = readDataFromSocket_New(din,s.getInputStream());
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);			
		}
		return response;
	}
	
	public String readDataFromSocket_New(DataInputStream dataInputStream,InputStream is) {
		StringBuilder data = new StringBuilder();
		//BufferedReader br=null;
		try {
			/*br=new BufferedReader(new InputStreamReader(dataInputStream));
			String line;
			
			while((line=br.readLine()) !=null){
				sb.append(line);
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Output from Socket "+sb.toString());*/
			byte[] buffer = new byte[4096];
			int length = dataInputStream.read(buffer, 0,4096);
			byte[] arrayBytes = new byte[length];
			System.arraycopy(buffer, 0, arrayBytes, 0, length);
			data.append(new String(arrayBytes, StandardCharsets.UTF_8));
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"loop length "+length+" loop data :  "+ data.toString());
			int len = 0;
			while ((len = dataInputStream.read(buffer)) > 0) {
				arrayBytes = new byte[len];
				System.arraycopy(buffer, 0, arrayBytes, 0, len);
				data.append(new String(arrayBytes, StandardCharsets.UTF_8));
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"loop length "+len+" loop data :  "+ data.toString());
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"dataInputStream.available() :  "+ dataInputStream.available());
				if (dataInputStream.available()<=0)
					break;
			} 
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"Error in socket read: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"Error in socket read "+ e.getMessage());
		}
	//	LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"final data :  "+ data.toString());
		return data.toString();
	}
	
	public void initSocket(String socketIP, int socketPort) {
		setSocketIP(socketIP);
		setSocketPort(socketPort);
	}

}