package com.newgen.ao.laps.calls;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import com.newgen.omni.wf.util.excp.NGException;
import ISPack.CPISDocumentTxn;
import ISPack.ISUtil.JPDBRecoverDocData;
import ISPack.ISUtil.JPISException;
import ISPack.ISUtil.JPISIsIndex;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.ElementList;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.core.ICallExecutor;
import com.newgen.ao.laps.logger.LapsModifyDBLogger;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.aproj2.arabic.pdf.ConvertHtmlToPdf;
import com.newgen.omni.jts.cmgr.XMLParser;

public class SwiftDocumentGen implements ICallExecutor, Callable<String> {

	private CallEntity callEntity;
	Map<String, String> attributeMap ;
	private String sessionId;
	private String engineName;
	private String stageId;
	private String winame;
	private String correlationNo = "";
	private String sUserName;
	private String refNo;
	private String callStatus;
	private int returnCode = 0;
	private String errorDetail = "Success";
	private String errorDescription = "Documents linked successfully";	
	private String status;
	private String reason;
	private String auditCallName = "LD"; //Name to be changes as per swiftdocumentgen
	
	public String sJtsIp;
	public int iJtsPort;
	public int volumeID;
	String SwiftDocName ;
	String InitiateFromActivityName="";
	int InitiateFromActivityId;
	String cabinetName;
	
	public SwiftDocumentGen(Map<String, String> attributeMaps, String sessionId, String stageId, String wiNumber, 
			Boolean prevStageNoGo, CallEntity callEntity) {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, " Inside SwiftDocumentGen  "+sessionId); //25/11/21
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, " Inside SwiftDocumentGen  : 28/11/21 "); //28/11/21
		attributeMap = new HashMap<String, String>(); //28/11/21
		this.callEntity = callEntity;
		this.attributeMap = attributeMaps;
		this.sessionId = sessionId;
		this.engineName=LapsConfigurations.getInstance().CabinetName;
		this.stageId=stageId;
		this.winame=wiNumber;
		//this.correlationNo=attributeMap.get("correlationNo");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "SwiftDocumentGen PDF generation started for attributes :: " +attributeMap + "wino :" + winame);//25/11/21
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Pdf Creation started for WorkItem :: " +wiNumber);//28/11/21
		sUserName = LapsConfigurations.getInstance().UserName;
		InitiateFromActivityName = LapsConfigurations.getInstance().InitiateFromActivityName;
		SwiftDocName = LapsConfigurations.getInstance().SwiftDocName;
		InitiateFromActivityId = Integer.parseInt(LapsConfigurations.getInstance().InitiateFromActivityId);
		cabinetName = LapsConfigurations.getInstance().CabinetName;
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "SwiftDocumentGen cunstructor called map===> " +callEntity.toString()+" Session id ===>"+sessionId);
	}
	
	
	@Override
	public String call() throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside SwiftDocumentGen call: ");
		String pdfDetails="Document not generated";
		callStatus="Y";
		String inputXml = "" ;
		try {			
			//New change in code by kishan   (By DCN Message fetched from DB)
			/*String sQuery = "select message from TFO_DJ_SWIFT_TXN_DETAILS "
			+ " where dcn = '"+attributeMap.get("DCN")+"'";
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Query from TFO_DJ_SWIFT_TXN_DETAILS : "+ sQuery);
			String sOutputXML = APCallCreateXML.APSelect(sQuery);
			XMLParser sOuterParser = new XMLParser(sOutputXML);
			int sMainCode = Integer.parseInt(sOuterParser.getValueOf("MainCode"));*/
			if(!attributeMap.get("messageToPDF").equalsIgnoreCase("")){ //If message is empty then pdf generation execution proceed or not ? will have to add message not empty chaeck
				try {
					String messageToPDF = attributeMap.get("messageToPDF");
					String sDcn = attributeMap.get("DCN");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"call -- SwiftDocumentGen messageToPDF :" +messageToPDF);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"call -- SwiftDocumentGen DCN :" +sDcn);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"call -- SwiftDocumentGen attributeMap  :" +attributeMap); //28/11/21
					String pdfFinalPath = generatePDF(messageToPDF, attributeMap.get("DCN"),sessionId);
					pdfDetails = getDocPDFDtls(pdfFinalPath,sessionId);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"SwiftDocumentGen call: pdfDetails::"+pdfDetails);
					updateCallOutput(inputXml); //New code added  by kishan
				} catch (JPISException e) {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
					LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, winame, auditCallName, "", e.getMessage(), sessionId);
				}
				
			}else{ //New change in code by kishan (In case mesage not fetched from db then linking wont happen)
				callStatus="N";
				updateCallOutput(inputXml);
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			throw new Exception(e.getMessage());
		}
		String outputXml = "<returnCode>"+returnCode+"</returnCode><errorDescription>"+errorDescription+"</errorDescription>";
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXml: " +outputXml);
		return outputXml;
	}

	@Override
	public String createInputXML() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String executeCall(HashMap<String, String> input) throws Exception {
		return call();
	}

	@Override
	public void executeDependencyCall() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void responseHandler(String outputXML, String inputXml)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		try {
			if(callStatus == "N"){
				returnCode = -1 ;
				errorDetail = "Failed";
				errorDescription = "Document Linking failed";
			}
			String valList = "'"+ winame +"',"+ stageId +", 'SwiftDocumentGen', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"'";
			APCallCreateXML.APInsert("BPM_ORCHESTRATION_STATUS", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
					+ "ERROR_DETAIL, STATUS, REASON", valList, sessionId);
			//Added by SHivanshu ATP-458
			moveDataFromStagingToHistory();
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, winame, auditCallName, "LD100", e.getMessage(), sessionId);
		}	
	}

	@SuppressWarnings("null")
	public String generatePDF(String message, String DCN, String sessionId) throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"process starts for generate pdf");
		String htmlContent = "";
		String pdfFolderPath = "";
		String pdfPath = "";
		StringBuffer stringBuffer = null;
		String htmlGenerated = "";
		String passPdfPath = "";
		try {
			if (message.contains("<![CDATA[")) {
				message = message.substring(9, message.length());
				message = message.substring(0, message.length() - 3);
			}
			
			message = message.replaceAll("\\n","<br />");
			htmlContent = "<!DOCTYPE html><html><head><title>"+SwiftDocName+"</title></head><body><p>"
					+ message + "</p></body></html>";

			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "generate pdf html content"+ htmlContent);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "dirctry path : " + System.getProperty("user.dir"));

			pdfFolderPath =  File.separatorChar+LapsConfigurations.getInstance().SwiftDocFolderName
					+File.separatorChar +"SwiftPDF";//+ File.separatorChar+ "SwiftPDF";
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "pdfFolderPath  : "+ pdfFolderPath);
			File file = new File(pdfFolderPath);
			if (!file.exists()) {
				Boolean createFile = file.mkdir();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"main folder created : " + createFile);
			}
			pdfPath = pdfFolderPath + File.separatorChar + DCN;
			file = new File(pdfPath);
			if (!file.exists()) {
				Boolean createFile = file.mkdir();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "DCN folder created : "+ createFile);
			}else{  // 08/12/21 Duplicate attribute case handling (Reexecution of generatePDF Code with updated DCN and message from DB )
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Folder already exist For DCN Number :: "+DCN);//28/11/21
				
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Duplicate attribute Map Found , Execution started for Workitem  :: "+winame + " again");//08/12/21
				String query  = "Select DCN,MESSAGE from TFO_DJ_SWIFT_TXN_DETAILS  where WI_NAME='"+winame+"'"; //08/12/21
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, " Rexecute case Query   ::" + query );//08/12/21
				String outputXML = "";				
				try {//08/12/21
					outputXML = APCallCreateXML.APSelect(query);
				} catch (Exception e) {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
				}
				XMLParser valueParser = new XMLParser(outputXML);
				DCN = valueParser.getValueOf("DCN");
				message = valueParser.getValueOf("MESSAGE");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, " Rexecute case Updated DCN and Message :: "+DCN + " : " + message + " : " +winame);//08/12/21
				//String paths = generatePDF( message,  DCN, sessionId);
				try {
					if (message.contains("<![CDATA[")) {
						message = message.substring(9, message.length());
						message = message.substring(0, message.length() - 3);
					}
					
					message = message.replaceAll("\\n","<br />");
					htmlContent = "<!DOCTYPE html><html><head><title>"+SwiftDocName+"</title></head><body><p>"
							+ message + "</p></body></html>";

					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, " Rexecute case generate pdf html content"+ htmlContent);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, " Rexecute case dirctry path : " + System.getProperty("user.dir"));

					pdfFolderPath =  File.separatorChar+LapsConfigurations.getInstance().SwiftDocFolderName
							+File.separatorChar +"SwiftPDF";//+ File.separatorChar+ "SwiftPDF";
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, " Rexecute case pdfFolderPath  : "+ pdfFolderPath);
					file = new File(pdfFolderPath);
					if (!file.exists()) {
						Boolean createFile = file.mkdir();
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Rexecute case main folder created : " + createFile);
					}
					pdfPath = pdfFolderPath + File.separatorChar + DCN;
					file = new File(pdfPath);
					if (!file.exists()) {
						Boolean createFile = file.mkdir();
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Rexecute case DCN folder created : "+ createFile);
					}else{
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Rexecute case Folder already exist For DCN Number :: "+DCN);//28/11/21
					}
					htmlGenerated = pdfPath + File.separatorChar + ""+SwiftDocName+"Pdf.html";
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Rexecute case html path : "+ htmlGenerated);
					Writer fw = new OutputStreamWriter(new FileOutputStream(htmlGenerated), "UTF-8");
					fw.write(htmlContent);
					fw.close();
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Rexecute case html write success : "+ htmlGenerated);
					File fileObj = new File(htmlGenerated);
					long lFileLength = fileObj.length();
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Rexecute case generatePDF lFileLength : "+ lFileLength);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Rexecute case Pdf Path After html success : "+ pdfPath);
					stringBuffer = new StringBuffer();
					stringBuffer.append(pdfPath);
					stringBuffer.append(File.separatorChar);
					
					
					ConvertHtmlToPdf pdfconvertor = new ConvertHtmlToPdf();
					
					//New change in code by kishan (location accessed from configuration file also changed location)
					String Header = File.separatorChar+LapsConfigurations.getInstance().SwiftDocFolderName
							+File.separatorChar+ "Images" + File.separatorChar + "Header.png";

					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Rexecute case Header : " + Header);

					String Footer =  File.separatorChar+LapsConfigurations.getInstance().SwiftDocFolderName
							+File.separatorChar + "Images" + File.separatorChar + "Footer.png";
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Rexecute case Header : " + Footer);
					try {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Rexecute case pdf starts ");
						com.newgen.aproj2.arabic.pdf.ConvertHtmlToPdf.pdgGen(htmlGenerated, pdfPath,
								stringBuffer.toString() + ""+SwiftDocName+".pdf", false,
								false, "", "", "ABC", "ABC1", "ABC2", Header, Footer);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Rexecute case pdf ends ");
					} catch (Exception e) {
						
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "ConvertHtmlToPdf Exception : "+ e);
					}
					passPdfPath = pdfFolderPath + File.separatorChar + DCN + File.separatorChar + "Protected";
					file = new File(passPdfPath);
					if (!file.exists()) {
						Boolean createFile = file.mkdir();
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Rexecute case passPdfPath folder created : "+ createFile);
					}
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Rexecute case passPdfPath folder destination : "
							+ passPdfPath + File.separatorChar + ""+SwiftDocName+".pdf");
					encryptPdf(stringBuffer.toString() + ""+SwiftDocName+".pdf",
							passPdfPath + File.separatorChar + ""+SwiftDocName+".pdf");

					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Rexecute case process ends for generate pdf");
				} catch (Exception e ) {
					String inputXml = "";
					callStatus="N";
					try {
						updateCallOutput(inputXml);
					} catch (Exception e1) {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
					}
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
				} 
				return stringBuffer.toString() + ""+SwiftDocName+".pdf";
			}
			htmlGenerated = pdfPath + File.separatorChar + ""+SwiftDocName+"Pdf.html";
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "html path : "+ htmlGenerated);
			Writer fw = new OutputStreamWriter(new FileOutputStream(htmlGenerated), "UTF-8");
			fw.write(htmlContent);
			fw.close();
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "html write success : "+ htmlGenerated);
			File fileObj = new File(htmlGenerated);
			long lFileLength = fileObj.length();
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "generatePDF lFileLength : "+ lFileLength);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Pdf Path After html success : "+ pdfPath);
			stringBuffer = new StringBuffer();
			stringBuffer.append(pdfPath);
			stringBuffer.append(File.separatorChar);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Check 1Header : " );
			
			ConvertHtmlToPdf pdfconvertor = new ConvertHtmlToPdf();
			
			//New change in code by kishan (location accessed from configuration file, also changed location)
			String Header = File.separatorChar+LapsConfigurations.getInstance().SwiftDocFolderName
					+File.separatorChar+ "Images" + File.separatorChar + "Header.png";
					
					//System.getProperty("user.dir")
					//+"/installedApps/ADCU9627Cell01/SwiftRequestmessage_jar.ear" + File.separatorChar
					//+ "Images" + File.separatorChar + "Header.png";

			 //File.separatorChar+LapsConfigurations.getInstance().docFolderName
				//+File.separatorChar
			
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Header : " + Header);

			String Footer =  File.separatorChar+LapsConfigurations.getInstance().SwiftDocFolderName
					+File.separatorChar + "Images" + File.separatorChar + "Footer.png";
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Header : " + Footer);
			try {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "pdf starts ");
				com.newgen.aproj2.arabic.pdf.ConvertHtmlToPdf.pdgGen(htmlGenerated, pdfPath,
						stringBuffer.toString() + ""+SwiftDocName+".pdf", false,
						false, "", "", "ABC", "ABC1", "ABC2", Header, Footer);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "pdf ends ");
			} catch (Exception e) {
				
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "ConvertHtmlToPdf Exception : "+ e);
			}
			passPdfPath = pdfFolderPath + File.separatorChar + DCN + File.separatorChar + "Protected";
			file = new File(passPdfPath);
			if (!file.exists()) {
				Boolean createFile = file.mkdir();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "passPdfPath folder created : "+ createFile);
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "passPdfPath folder destination : "
					+ passPdfPath + File.separatorChar + ""+SwiftDocName+".pdf");
			encryptPdf(stringBuffer.toString() + ""+SwiftDocName+".pdf",
					passPdfPath + File.separatorChar + ""+SwiftDocName+".pdf");

			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"process ends for generate pdf");
		} catch (Exception e ) {
			String inputXml = "";
			callStatus="N";
			try {
				updateCallOutput(inputXml);
			} catch (Exception e1) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		} 
		return stringBuffer.toString() + ""+SwiftDocName+".pdf";
	}

	private String getDocPDFDtls(String pdfPath,String sessionId) throws JPISException {
		String docIndex = "";
		StringBuffer sBuffer = new StringBuffer();
		try {
			int i = 21;
			int j = 25;
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"pdfPath :: "+pdfPath);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"PDFdetails execution starts");
			JPISIsIndex ISINDEX = new JPISIsIndex();
			File fileObj = new File(pdfPath);
			long lFileLength = fileObj.length();
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "lFileLength : "+ lFileLength);
			JPDBRecoverDocData JPISDEC = new JPDBRecoverDocData();
			JPISDEC.m_cDocumentType = 'N';
			JPISDEC.m_nDocumentSize = (int) lFileLength;
			JPISDEC.m_sVolumeId = (short) (LapsConfigurations.getInstance().volumeID);;
			try {
				if (JPISDEC.m_nDocumentSize != 0) {
					sJtsIp = LapsConfigurations.getInstance().JTSIP;
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "sJtsIp : "
							+ sJtsIp);
					iJtsPort = LapsConfigurations.getInstance().JTSPort;
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "iJtsPort : "
							+ iJtsPort);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "pdfPath : "
							+ pdfPath);
					volumeID = LapsConfigurations.getInstance().volumeID;
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "volumeID : "
							+ volumeID);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"JPISDEC.m_nDocumentSize : "
									+ JPISDEC.m_nDocumentSize);
					CPISDocumentTxn.AddDocument_MT(null, sJtsIp,
							(short) iJtsPort, cabinetName, (short) (volumeID),
							pdfPath, JPISDEC, "", ISINDEX);
					docIndex = JPISDEC.m_nDocIndex + "#" + volumeID;
					String outputXML;		//New change in code by kishan
					try {
						outputXML = APCallCreateXML.APSelect("SELECT ITEMINDEX,CURR_WS FROM EXT_TFO WHERE WI_NAME = N'"
								+ winame + "'");
						XMLParser xp = new XMLParser(outputXML);
						int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
						String itemIndex = xp.getValueOf("ITEMINDEX");	
						if(mainCode == 0){
							String outputXml = ProdCreateXML.AddDocument(sessionId, itemIndex,
									LapsConfigurations.getInstance().SwiftDocName,
									"pdf","pdf",LapsConfigurations.getInstance().volumeID,
									docIndex, 1, "N", String.valueOf(lFileLength));
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Document Addition: "+outputXml);
						}		
					} catch (Exception e) {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
					}
				}
			} catch (Exception e) {
				String inputXml = "";
				callStatus="N";
				try {
					updateCallOutput(inputXml);
				} catch (Exception e1) {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
				}
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "In Catch Statement of getDocPDFDtls Method");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			}
			
			sBuffer.append(SwiftDocName);
			sBuffer.append((char) i);
			sBuffer.append(docIndex);
			sBuffer.append((char) i);
			sBuffer.append("1");
			sBuffer.append((char) i);
			sBuffer.append(lFileLength);
			sBuffer.append((char) i);
			sBuffer.append("pdf");
			sBuffer.append((char) i);
			sBuffer.append("");
			sBuffer.append((char) j);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"PDFdetails : " + sBuffer.toString());
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "PDFdetails execution ends");
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "Erro"+e);
		}
		return sBuffer.toString();
	}
	

	public void encryptPdf(String src, String dest) throws IOException,DocumentException {
		try {
			File file = new File(src);
			Boolean canRead = file.canRead();
			Boolean canWrite = file.canWrite();
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "canRead :" + canRead);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "canWrite :" + canWrite);

			PdfReader reader = new PdfReader(src);
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
			stamper.setEncryption("sahil".getBytes(), "khurana".getBytes(),
					PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128
							| PdfWriter.DO_NOT_ENCRYPT_METADATA);
			stamper.close();
			reader.close();
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "encryptPdf"+e);
		}
	}

	public void onEndPage(PdfWriter writer, Document document) {
		Font ffont = new Font(Font.FontFamily.UNDEFINED, 5, Font.ITALIC);

		PdfContentByte cb = writer.getDirectContent();
		Phrase header = new Phrase("this is a header", ffont);
		Phrase footer = new Phrase("this is a footer", ffont);
		ColumnText.showTextAligned(
				cb,
				Element.ALIGN_CENTER,
				header,
				(document.right() - document.left()) / 2
						+ document.leftMargin(), document.top() + 10, 0);
		ColumnText.showTextAligned(
				cb,
				Element.ALIGN_CENTER,
				footer,
				(document.right() - document.left()) / 2
						+ document.leftMargin(), document.bottom() - 10, 0);
	}

	public void onStartPage(PdfWriter writer, Document document)
			throws DocumentException, IOException {
		final String HEADER = "<table width=\"100%\" border=\"0\"><tr><td>Header</td><td align=\"right\">Some title</td></tr></table>";
		final String FOOTER = "<table width=\"100%\" border=\"0\"><tr><td>Footer</td><td align=\"right\">Some title</td></tr></table>";
		ElementList header;
		ElementList footer;
		PdfContentByte cb = writer.getDirectContent();
		header = XMLWorkerHelper.parseToElementList(HEADER, null);
		footer = XMLWorkerHelper.parseToElementList(FOOTER, null);

		ColumnText ct = new ColumnText(writer.getDirectContent());
		com.itextpdf.text.Rectangle rect = new com.itextpdf.text.Rectangle(36,
				10, 559, 32);
		ct.setSimpleColumn(rect);
		for (Element e : header) {
			ct.addElement(e);
		}
		ct.go();
		com.itextpdf.text.Rectangle rect1 = new com.itextpdf.text.Rectangle(36,
				10, 559, 810);
		ct.setSimpleColumn(rect1);
		for (Element e : footer) {
			ct.addElement(e);
		}
		ct.go();
	}
	
	//Added by Shivanshu ATP-458
		public void moveDataFromStagingToHistory() {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside moveDataFromStagingToHistory:");
			String outputXML = "";
			String outputXML1 = "";
			String txnRefNumber = "";
			String swiftChannel = "";
			int count = -1;
			try {
				outputXML = APCallCreateXML.APSelect("SELECT SWIFT_FIELD_21,SWIFT_CHANNEL FROM EXT_TFO WHERE WI_NAME = N'" + winame + "'");
				XMLParser xp = new XMLParser(outputXML);
				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				LapsModifyLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_INFO, "mainCode : " + mainCode);
				if(mainCode == 0){
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"TotalRetrieved: "+Integer.parseInt(xp.getValueOf("TotalRetrieved")));
					if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
						txnRefNumber = xp.getValueOf("SWIFT_FIELD_21");
						swiftChannel = xp.getValueOf("SWIFT_CHANNEL");
					}
				}
				outputXML1 = APCallCreateXML.APSelect("SELECT COUNT(DISTINCT TXN_REF_NO) AS COUNT FROM TFO_DJ_SWIFT_MT798_MQ_NOTIFICATION_AUDIT_HISTORY WHERE TXN_REF_NO = N'" + txnRefNumber + "'");
				XMLParser xp1 = new XMLParser(outputXML1);
				int mainCode1 = Integer.parseInt(xp1.getValueOf("MainCode"));
				LapsModifyLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_INFO, "mainCode1 : " + mainCode1);
				if(mainCode1 == 0){
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"TotalRetrieved: "+Integer.parseInt(xp1.getValueOf("TotalRetrieved")));
					if(Integer.parseInt(xp1.getValueOf("TotalRetrieved")) > 0){
						count = Integer.parseInt(xp1.getValueOf("COUNT"));
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "txnRefNumber : " + txnRefNumber + " and swiftChannel : " + swiftChannel + " and count " + count);
					}
				}
				if ("MT798".equalsIgnoreCase(swiftChannel) && count == 0) {
					String sParameter = "'" + txnRefNumber + "','" + winame + "'";
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "sParameter : " + sParameter);
					String output = APCallCreateXML.APProcedure(sessionId, "BPM_TFO_SWIFT_MOVE_DATA_TO_HISTORY", sParameter,2);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "sOutputXML : " + output);
				}
			} catch (NGException e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			}
		}
}
