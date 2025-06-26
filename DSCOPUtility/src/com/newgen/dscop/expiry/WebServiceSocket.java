package com.newgen.dscop.expiry;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class WebServiceSocket {

	private static WebServiceSocket instance = null;

	private WebServiceSocket() {
		
	}

	public static WebServiceSocket getInstance() {
		if (instance == null) {
			instance = new WebServiceSocket();
		}
		return instance;
	}

	public String connectToSocket(String inputXML, String socketIP, int socketPort)
	{
		String result="";
		try	{
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,"Connecting to socket... SOCKETIP:"+socketIP + " SOCKETPORT:"+socketPort);
			Socket s = new Socket(socketIP,socketPort);
			DataInputStream din=new DataInputStream(s.getInputStream());
			DataOutputStream dout=new DataOutputStream(s.getOutputStream());
			writeDataToSocket(dout,inputXML);
			result=readDataFromSocket(din);
			s.close();
		} catch (Exception e) {
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_ERROR,"Error in socket read/write "+ e.getMessage());
		}
		return result;
	}

	public boolean writeDataToSocket(DataOutputStream dataOutputStream, String data) {
		boolean bFlag = false;
		try {
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,"Write Data To Socket...");
			if (data != null && data.length() > 0) {
				dataOutputStream.write(data.getBytes(StandardCharsets.UTF_8));
				bFlag=true;
			}
		} catch (Exception e) {
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_ERROR,"Error in socket write "+ e.getMessage());
		}
		return bFlag;
	}

	public String readDataFromSocket(DataInputStream dataInputStream) {
		StringBuilder data = new StringBuilder();
		try {
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,"Read Data From Socket...");
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
				if (dataInputStream.available()<=0)
					break;
			}
		} catch (Exception e) {
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_ERROR,"Error in socket read "+ e.getMessage());
		}
		return data.toString();
	}
}