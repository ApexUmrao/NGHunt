package com.newgen.iforms.user.tfo.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import org.apache.log4j.Logger;

import com.newgen.iforms.user.config.TFOLogger;

public class ConnectSocket {
	private String socketIP;
	private int socketPort;
	private static ConnectSocket socket = null; 
	static Logger tfoLogger = TFOLogger.getInstance().getLogger();

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
		try(
			Socket s = new Socket(socketIP, socketPort)){
			tfoLogger.info("Socket: "+ socketIP +":"+ socketPort);
			int count = s.getReceiveBufferSize();
			tfoLogger.info("count : " +count);			
			DataInputStream din = new DataInputStream(s.getInputStream());
			DataOutputStream dout = new DataOutputStream(s.getOutputStream());
			writeDataToSocket(dout, inputXml);
			response = readDataFromSocket(din,s.getInputStream());
		} catch (Exception e) {
			tfoLogger.error("Error in socket connection:", e);			
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
			tfoLogger.info("Error in socket read/write "+ e.getMessage());
			e.printStackTrace();
		}
		return bFlag;
	}

	public String readDataFromSocket(DataInputStream dataInputStream,InputStream is) {
		StringBuilder data = new StringBuilder();
		try 
		 {
		      ByteArrayOutputStream baos = new ByteArrayOutputStream();
		      byte[] buf = new byte[4096];
		      while (true) {
		        int n = is.read(buf);
		        if (n < 0) break;
		        baos.write(buf, 0, n);
			} 
		      byte[] data1 = baos.toByteArray();
		      String output = new String(data1);
		      data.append(output);
		      
	       }catch (Exception e)
		{
			tfoLogger.info("Error in socket read/write " ,e);
		}
		return data.toString();
	}

	public void initSocket(String socketIP, int socketPort) {
		setSocketIP(socketIP);
		setSocketPort(socketPort);
	}

}