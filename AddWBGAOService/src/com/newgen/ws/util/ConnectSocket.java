package com.newgen.ws.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
//import java.nio.charset.StandardCharsets;

import java.nio.charset.StandardCharsets;

import org.apache.log4j.Logger;

import com.newgen.ws.service.AddWBGAOService;

//import com.newgen.iforms.user.config.WBGLogger;


public class ConnectSocket {
	private String socketIP;
	private int socketPort;
	private static ConnectSocket socket = null; 
	//public static Logger wbgLogger = WBGLogger.getInstance().getLogger();
	protected static Logger log = Logger.getLogger(AddWBGAOService.class);

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

	public String connectToSocket(String inputXml)
	{
		String response = "";
		try{
			log.debug("Socket: "+ socketIP +":"+ socketPort);
			Socket s = new Socket(socketIP, socketPort);
			log.debug("Socket Object Created ");
			DataInputStream din = new DataInputStream(s.getInputStream());
			log.debug("din Object Created ");
			DataOutputStream dout = new DataOutputStream(s.getOutputStream());
			log.debug("dout Object Created ");
			writeDataToSocket(dout, inputXml);
			response = readDataFromSocket(din);
			log.debug("reponse from socket "+ response);
		} catch (Exception e) {
			log.debug("Error in socket read/write "+ e.getMessage());
			e.printStackTrace();
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
			log.debug("write done ");
		} catch (Exception e) {
			log.debug("Error in socket read/write "+ e.getMessage());
			e.printStackTrace();
		}
		return bFlag;
	}

	public String readDataFromSocket(DataInputStream dataInputStream) {
		StringBuilder data = new StringBuilder();
		try {
			byte[] buffer = new byte[99999];
			int length = dataInputStream.read(buffer, 0, 99999);
			log.debug("length"+length);
			byte[] arrayBytes = new byte[length];
			log.debug("byte array");
			System.arraycopy(buffer, 0, arrayBytes, 0, length);
			log.debug("sys array");
			data.append(new String(arrayBytes, StandardCharsets.UTF_8));
			log.debug("data append");
			int len = 0;
			while ((len = dataInputStream.read(buffer)) > 0) {
				arrayBytes = new byte[len];
				System.arraycopy(buffer, 0, arrayBytes, 0, len);
				data.append(new String(arrayBytes, StandardCharsets.UTF_8));
				if (dataInputStream.available()<=0)
				break;
			}
			log.debug("data final"+data.toString());
		} catch (Exception e) {
			log.debug("Error in socket read/write "+ e.getMessage());
			e.printStackTrace();
		}
		return data.toString();
	}


	public void initSocket(String socketIP, int socketPort) {
		setSocketIP(socketIP);
		setSocketPort(socketPort);
	}

}