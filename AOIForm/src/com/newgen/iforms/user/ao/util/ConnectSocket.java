package com.newgen.iforms.user.ao.util;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import org.apache.log4j.Logger;

import com.newgen.iforms.user.config.AOLogger;

public class ConnectSocket {
	private String socketIP;
	private int socketPort;
	private static ConnectSocket socket = null; 
	static Logger aoLogger = AOLogger.getInstance().getLogger();

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

	public String connectToSocket(String inputXml){
		String response = "";
		try(Socket s = new Socket(socketIP, socketPort)){
			aoLogger.info("Socket: "+ socketIP +":"+ socketPort);			
			DataInputStream din = new DataInputStream(s.getInputStream());
			DataOutputStream dout = new DataOutputStream(s.getOutputStream());
			writeDataToSocket(dout, inputXml);
			response = readDataFromSocket(din, s.getInputStream());
			aoLogger.info("response: "+ response);			
		} catch (Exception e) {
			aoLogger.error("Error in socket connection:", e);			
		}
		return response;
	}
	
	public boolean writeDataToSocket(DataOutputStream dataOutputStream, String data) {
		boolean bFlag = false;
		try {
			if (data != null && data.length() > 0) {
				dataOutputStream.write(data.getBytes(StandardCharsets.UTF_8));
				bFlag = true;
			}
		} catch (Exception e) {
			aoLogger.info("Error in socket read/write "+ e.getStackTrace());			
		}
		return bFlag;
	}

	public String readDataFromSocket(DataInputStream dataInputStream,InputStream is) {
		/*StringBuilder data = new StringBuilder();
		try { 
			byte[] buffer = new byte[99999];
			int length = dataInputStream.read(buffer, 0, 99999);
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
			}
		} catch (Exception e) {
			aoLogger.info("Error in socket read/write ", e);			
		}
		return data.toString();*/
		StringBuilder data = new StringBuilder();
		String output;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buf = new byte[4096];
			while(true) {
			  int n = is.read(buf);
			  if( n < 0 ) break;
			  baos.write(buf,0,n);
			}
			byte data1[] = baos.toByteArray();
			output = new String(data1);
			data.append(output); 
		} catch (Exception e) {
			aoLogger.info("Error in socket read/write ",e);
		}
	//	tfoLogger.info("final data :  "+ data.toString());
		return data.toString();
	
	}

	public void initSocket(String socketIP, int socketPort) {
		setSocketIP(socketIP);
		setSocketPort(socketPort);
	}

}