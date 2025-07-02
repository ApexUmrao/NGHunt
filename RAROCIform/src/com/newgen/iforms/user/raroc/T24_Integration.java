package com.newgen.iforms.user.raroc;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.newgen.iforms.user.config.IntegrationConstants;



public class T24_Integration implements IntegrationConstants{

	private Logger log = Logger.getLogger(T24_Integration.class);

	//private String jbossDSN_1;
	//private String jbossDSN_2;
	private String socketIP;
	private Integer socketPort;

	private String processName;
	private String workitemName;

	public T24_Integration(String processName, String workitemName) {
		System.out.println("Inside Finacle_Integration >");
		log.info("Inside Finacle_Integration > "  );
		this.processName = processName;
		this.workitemName = workitemName;

		log.debug("PROCESS NAME: " + processName);
		log.debug("WORKITEM NAME: " + workitemName);

		initializeConfiguration();
	}

	private void initializeConfiguration() {
		System.out.println("Inside initializeConfiguration >");
		try {
			Properties properties = new Properties();

			System.out.println("kamal cc >");
			log.info("initializeConfiguration...");
			String integrationConfigPropertyFile = new StringBuilder().append(System.getProperty("user.dir"))
					.append(System.getProperty("file.separator")).append(CONFIG_FOLDER)
					.append(System.getProperty("file.separator")).append(INTEGRATION_CONFIG_PROPERTIES_FILE).toString();
			System.out.println("Inside kamalcc1 >" +integrationConfigPropertyFile);
			log.info("initializeConfiguration...integrationConfigPropertyFile:"+integrationConfigPropertyFile+"++");
			properties.load(new FileInputStream(integrationConfigPropertyFile));
			System.out.println("kamal cc2 >");


			socketIP = properties.getProperty("EhixSocketServerIP");
			socketPort = Integer.parseInt(properties.getProperty("EthixSocketServerPort"));
			System.out.println("socketIP: " + socketIP + ", socketPort: " + socketPort);
			log.info("socketIP:" + socketIP + ",..socketPort: " + socketPort);
		} catch (IOException exception) {
			log.error("Exception in Initialization of Integration Configuration: "+ exception.getMessage());
			System.out.println("kamal exception " +exception);
		}
	}

	public String executeIntegrationCall(String inputXML) {
		log.info("Inside executeIntegrationCall >>>  ");
		log.info("Executing Integration Call for PROCESS: " + processName + ", WORKITEM NAME: " + workitemName);
		log.info("Inside executeIntegrationCall > processName : workitemName: " + processName + workitemName);
		log.info("INPUT XML: " + inputXML);

		String outputXML = "";

		// MAKE SOCKET CONNECTION WITH ISO LISTENER
		try (Socket clientSocket = new Socket(socketIP, socketPort);
				DataOutputStream outputStreamToServer = new DataOutputStream(clientSocket.getOutputStream());
				BufferedReader inputStreamFromServer = new BufferedReader(
						new InputStreamReader(clientSocket.getInputStream()));) {
			clientSocket.setSoTimeout(100000);
			outputStreamToServer.writeBytes(inputXML + "\n");
			log.info("INPUT XML PUSHED TO SOCKET");
			log.info("INPUT XML PUSHED TO SOCKET");
			outputXML = inputStreamFromServer.readLine();
			System.out.println("OUTPUT XML OBTAINED FROM SOCKET");
			log.info("OUTPUT XML PUSHED TO SOCKET");
			System.out.println("OUTPUT XML: " + outputXML);
			log.info("OUTPUT XML: " + outputXML);
			if (outputXML.isEmpty()) {
				throw new Exception(MESSAGE_ERROR_NO_OUTPUT);
			}

		} catch (Exception exception) {
			log.error("Exception in Executing Integration Call:: " + exception.getMessage());

			// ERROR OUTPUT XML FOR EXCEPTION
			outputXML = "<?xml version=\"1.0\"?>" + "<ISOResponseWrapper>" + "<CallType></CallType>" + "<ISOParams>"
					+ "<ResponseCode>" + EXCEPTION_RESPONSE_CODE + "</ResponseCode>" + "<ResponseDesc>"
					+ EXCEPTION_RESPONSE_DESCRIPTION + "</ResponseDesc>" + "<Exception>" + exception.getMessage()
					+ "</Exception>" + "<DMSRefNo>" + workitemName + "</DMSRefNo>" + "<cifid></cifid>"
					+ "<AcctId></AcctId>" + "</ISOParams>" + "</ISOResponseWrapper>";
		}

		return outputXML;
	}
	public String executeIntegrationCallProj(String inputXML) {
		log.info("Inside executeIntegrationCall >");
		log.info("Executing Integration Call for PROCESS: " + processName +", WORKITEM NAME: "+ workitemName);
			//logger.info("CALL TYPE: "+ callType);
		log.info("INPUT XML..." + inputXML+"+++");
			String outputXML = "";
			String finalResponse = "";
			String outputString = "";
			// MAKE SOCKET CONNECTION WITH ISO LISTENER
			try(Socket clientSocket = new Socket(socketIP, socketPort);
					DataOutputStream outputStreamToServer = new DataOutputStream(clientSocket.getOutputStream());
					BufferedReader inputStreamFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					) {
				clientSocket.setSoTimeout(100000);
				outputStreamToServer.writeBytes(inputXML + "\n");
				log.debug("INPUT XML PUSHED TO SOCKET IS"+inputXML);
				String line;
				 while ((line = inputStreamFromServer.readLine()) != null)
				 {
					 outputString = String.valueOf(outputString) + line;
				 }
//		         return outputString;
//				outputXML = inputStreamFromServer.readLine();
 
				/*StringBuilder output = new StringBuilder("{");
				  while(inputStreamFromServer.read() > -1)
					{
						output.append(inputStreamFromServer.readLine());
					}
					 finalResponse = output.toString();
					logger.info("finalResponse :::: "+finalResponse);
					if(finalResponse.contains("null"))
					{
						logger.info(" null Found");
						finalResponse = finalResponse.replace("null", "}");
					}
					else
					{
						logger.info("no null Found");
					}*/
				 log.info("OUTPUT XML..." + outputString+"+++");
				if (outputString.isEmpty()) {
					throw new Exception(MESSAGE_ERROR_NO_OUTPUT);
				}
			} catch (Exception exception) {
				log.error("Exception in Executing Integration Call:: " + exception.getMessage());
				System.out.println("Exception in Executing Integration Call:: " + exception.getMessage());
				// ERROR OUTPUT XML FOR EXCEPTION
				outputString = "<CBS_RESPONSE><STATUS_CODE>"+EXCEPTION_RESPONSE_CODE+"</STATUS_CODE><STATUS_DESC>"+EXCEPTION_RESPONSE_DESCRIPTION+"</STATUS_DESC><STATUS_REMARKS>"+exception.getMessage()+"</STATUS_REMARKS><RESPONSE></RESPONSE></CBS_RESPONSE>";		
			} 
			return outputString;
		}
}
