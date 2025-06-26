package com.newgen.cbg.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import com.newgen.cbg.logger.DSCOPLogMe;
import com.newgen.omni.jts.cmgr.XMLParser;

public class WebServiceSocket {

	private String socketIP;
	private Integer socketPort;
	private static WebServiceSocket instance = null;
	
	private int connectionTimeout = 10000;  // 10 seconds
    private int readTimeout = 30000;  // 30 seconds

	private WebServiceSocket() {
		try {
			String outputXML = APCallCreateXML.APSelect("SELECT KEY, VALUE FROM BPM_SERVICE_CONFIG");
			XMLParser xp = new XMLParser(outputXML);
			int start = xp.getStartIndex("Records", 0, 0);
			int deadEnd = xp.getEndIndex("Records", start, 0);
			int noOfFields = xp.getNoOfFields("Record", start,deadEnd);
			int end = 0;
			for (int i = 0; i < noOfFields; ++i) {
				start = xp.getStartIndex("Record", end, 0);
				end = xp.getEndIndex("Record", start, 0);
				String key = xp.getValueOf("KEY", start, end);
				if ("SOCKETIP".equalsIgnoreCase(key)) {
					socketIP = xp.getValueOf("VALUE", start, end);
				} else if ("SOCKETPORT".equalsIgnoreCase(key)) {
					socketPort = Integer.parseInt(xp.getValueOf("VALUE", start, end));
				} 
				// Production optimization 09/01/2024 KRISHAN
				else if ("SOCKETCONNECTIONTIMEOUT".equalsIgnoreCase(key)) {
					connectionTimeout = Integer.parseInt(xp.getValueOf("VALUE", start, end));
				} else if ("SOCKETREADTIMEOUT".equalsIgnoreCase(key)) {
					readTimeout = Integer.parseInt(xp.getValueOf("VALUE", start, end));
				}
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,e);
			e.printStackTrace();
		}
	}

	public static WebServiceSocket getInstance() {
		if (instance == null) {
			instance = new WebServiceSocket();
		}
		return instance;
	}

	public String connectToSocket(String inputXML)
	{
		String result = "";
		try {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Connecting to socket... SOCKETIP:" + socketIP + " SOCKETPORT:" + socketPort);
			// Production optimization 09/01/2024 KRISHAN
			/*
			 * Socket s = new Socket(socketIP, socketPort); DataInputStream din = new
			 * DataInputStream(s.getInputStream()); DataOutputStream dout = new
			 * DataOutputStream(s.getOutputStream()); writeDataToSocket(dout, inputXML);
			 * result = readDataFromSocket(din); s.close();
			 */
			InetSocketAddress socketAddress = new InetSocketAddress(socketIP, socketPort);
			try (Socket s = new Socket()) {
				s.connect(socketAddress, connectionTimeout); // Connection timeout
				s.setSoTimeout(readTimeout); // Read timeout
				try (DataInputStream din = new DataInputStream(s.getInputStream());
						DataOutputStream dout = new DataOutputStream(s.getOutputStream())) {
					writeDataToSocket(dout, inputXML);
					result = readDataFromSocket(din);
				}
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, "Error in socket read/write " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	public boolean writeDataToSocket(DataOutputStream dataOutputStream, String data) {
		boolean bFlag = false;
		try {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Write Data To Socket...");
			if (data != null && data.length() > 0) {
                dataOutputStream.write(data.getBytes(StandardCharsets.UTF_8));
                bFlag = true;
            }
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,"Error in socket write "+ e.getMessage());
			e.printStackTrace();
		}
		return bFlag;
	}

	public String readDataFromSocket(DataInputStream dataInputStream) {
		StringBuilder data = new StringBuilder();
		try {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Read Data From Socket...");
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
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,"Error in socket read "+ e);
			e.printStackTrace();
		}
		return data.toString();
	}
	
	//BRMS_SERVER_FIX | MOKSH | 02022024
	public String connectToSocketWithTimeout(String inputXML)
	{
		String result = "";
		try {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Connecting to socket... SOCKETIP:" + socketIP + " SOCKETPORT:" + socketPort);
			InetSocketAddress socketAddress = new InetSocketAddress(socketIP, socketPort);
			try (Socket s = new Socket()) {
				s.connect(socketAddress, connectionTimeout); // Connection timeout
				s.setSoTimeout(readTimeout); // Read timeout
				try (DataInputStream din = new DataInputStream(s.getInputStream());
						DataOutputStream dout = new DataOutputStream(s.getOutputStream())) {
					writeDataToSocket(dout, inputXML);
					result = readDataFromSocket(din);
				}
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, "Error in socket read/write " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	//BRMS_SERVER_FIX | MOKSH | 02022024
}