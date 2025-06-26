package com.newgen.iforms.user.tfo.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import org.apache.log4j.Logger;

import com.newgen.iforms.user.config.TFOLogger;
import com.newgen.iforms.user.tfo.util.XMLParser;

public class ConnectTemplateSocket {
	private String tempGenSocketIp;
	private int tempGenSocketPort;
	private static ConnectTemplateSocket socket = null; 
	static Logger tfoLogger = TFOLogger.getInstance().getLogger();

	private ConnectTemplateSocket(String tempGenSocketIp, int tempGenSocketPort){
		initSocket(tempGenSocketIp, tempGenSocketPort);
	}

	public static ConnectTemplateSocket getReference(String tempGenSocketIp, int tempGenSocketPort){
		if(socket == null){
			socket = new ConnectTemplateSocket(tempGenSocketIp, tempGenSocketPort);
		}
		return socket;
	}

	private void settempGenSocketIp(String tempGenSocketIp) {
		this.tempGenSocketIp = tempGenSocketIp;
	}

	private void settempGenSocketPort(int tempGenSocketPort) {
		this.tempGenSocketPort = tempGenSocketPort;
	}

	public String connectToSocket(String inputXml)
	{
		String response = "";
		try(Socket s = new Socket(tempGenSocketIp, tempGenSocketPort)){
			tfoLogger.info("Socket: "+ tempGenSocketIp +":"+ tempGenSocketPort);
			DataInputStream din = new DataInputStream(s.getInputStream());
			DataOutputStream dout = new DataOutputStream(s.getOutputStream());
			writeDataToSocket(dout, inputXml);
			response = readDataFromSocket(din);
		} catch (Exception e) {
			tfoLogger.info("Error in socket connection",e);
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
			tfoLogger.info("Error in socket read/write ", e);			
		}
		return bFlag;
	}

	public String readDataFromSocket(DataInputStream dataInputStream) {
		StringBuilder data = new StringBuilder();
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
				if (dataInputStream.available()<=0) {
					break;
				}					
			}
		} catch (Exception e) {
			tfoLogger.info("Error in socket read/write ", e);			
		}
		return data.toString();
	}

	public void initSocket(String tempGenSocketIp, int tempGenSocketPort) {
		settempGenSocketIp(tempGenSocketIp);
		settempGenSocketPort(tempGenSocketPort);
	}

}