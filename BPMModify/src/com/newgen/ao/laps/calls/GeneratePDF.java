package com.newgen.ao.laps.calls;

/**
 * Example written by Bruno Lowagie and Nishanthi Grashia in answer to the following question:
 * http://stackoverflow.com/questions/24359321/adding-row-to-a-table-in-pdf-using-itext
 */

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.LapsConfigurations;

//import javafx.scene.text.TextAlignment;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneratePDF {
	public static final String FONT = "/appl/IBM/WebSphere/AppServer/profiles/BPMProf01/DHLPDF/Font/txtarial.ttf";
	float fntSize = 10f;
	float lineSpacing = 5f;
	private String sessionId;
	private String engineName;
	public static final String DEST = "simple_table.pdf"; // Destination path of file with filename
// public void createPdf(String dest,String input) throws IOException, DocumentException -- pass input variable as String message for PDF
	public static String header = "header.JPG"; // give file location of header
	public static String footer = "footer.JPG"; // give file location of footer
	public static String relType = "Conventional"; // footer relationship type

	public static Map valueMap = new HashMap<>();
	static BaseFont bf;
	static Font font;

	static {

		try {
			bf = BaseFont.createFont(FONT, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
//font = new Font(bf, 10f, Font.BOLD);
			font = new Font(bf, 10f);

		} catch (DocumentException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}

		valueMap.put("Drawer           :", "Drawer           :");
		valueMap.put("Our Reference No.:", "Our Reference No.:");
		valueMap.put("Drawee           :", "Drawee           :");
		valueMap.put("Documents Value  :", "Documents Value  :");
		valueMap.put("Tenor (Days)     :", "Tenor (Days)     :");
		valueMap.put("Goods            :", "Goods            :");

	}

	public static String getText() throws IOException {
		BufferedReader br = null;
		int nextLinePosition = 100;
		int fontSize = 12;
//br = new BufferedReader(new FileReader("remit_4_oct.txt"));
//  br = new BufferedReader(new FileReader("REMITTANCE3.txt"));
		br = new BufferedReader(new FileReader("REMITTANCE2.txt"));
// br = new BufferedReader(new FileReader("REMITTANCE.txt"));

//br = new BufferedReader(new FileReader("REMITTANCE3.txt"));
//   br = new BufferedReader(new FileReader("Message_2oct.txt"));
		String line;
		String returnString = "";
		while ((line = br.readLine()) != null) {
			returnString = returnString + line + "\r\n";
//returnString = returnString + line ;
		}
		br.close();
		return returnString;
	}

	public static void main(String[] args) throws IOException, DocumentException {
		File file = new File(DEST);
// file.getParentFile().mkdirs();
//new FormatPDF2().createPdf(DEST,"");
	}

	public GeneratePDF(String header, String footer, String relType) {

		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside GeneratePDF");
		this.sessionId = sessionId;
		this.engineName = LapsConfigurations.getInstance().CabinetName;
		String sUserName = LapsConfigurations.getInstance().UserName;
		this.header = header;
		this.footer = footer;
		this.relType = relType;
	}

	public String[] getGeneratedString(String s) {
		String[] input = s.split("\r\n");
		return input;
	}

	public String[] getTableString(String s) {
		String[] splitVal = null;
		if (s.contains("Our Reference No") && s.contains(":")) {

			splitVal = s.split(":");

		}
//else if (s.contains("Drawer           :")) {
		else if (s.contains("Drawer") && s.contains(":")) {
			splitVal = s.split(":");

		}
//else if (s.contains("Drawee           :")) {
		else if (s.contains("Drawee") && s.contains(":")) {

			splitVal = s.split(":");

		}
//else if (s.contains("Documents Value  :")) {
		else if (s.contains("Documents Value") && s.contains(":")) {

			splitVal = s.split(":");

		}
// else if (s.contains("Tenor (Days)     :")) {
		else if (s.contains("Tenor (Days)") && s.contains(":")) {

			splitVal = s.split(":");

		}
//else if (s.contains("Goods                  :")) {
		else if (s.contains("Goods") && s.contains(":")) {

			splitVal = s.split(":");

		}
//else if (s.contains("Tenor Description:")) {
		else if (s.contains("Tenor Description") && s.contains(":")) {

			splitVal = s.split(":");

		} else if (s.contains("Maturity Date") && s.contains(":")) {

//else if (s.contains("Maturity Date    :")) {
			splitVal = s.split(":");

		} else if (s.contains("AWB/BL/TR Number") && s.contains(":")) {
			splitVal = s.split(":");
			String d = splitVal[1].replaceAll("\\s+", " ");
			d = d.trim();
			splitVal[1] = d;
		}

		else if (s.contains(":")) {
			if (!s.trim().contains("Goods")) {
				splitVal = s.split(":");
			}

		}

		return splitVal;
	}

	public static String getLongFormattedString(String input) {
		try {
			String tempInput = input.trim();
			int length = tempInput.length();
			String newString = "";

			if (length > 30) {
				char[] a = tempInput.toCharArray();
				int diff = 0;
				for (int i = 0; i < length; i++) {

					String sVal = String.valueOf(a[i]);
					if (i > 1 && i % 29 == 0) {
						if (diff == 0) {
							int last = newString.lastIndexOf(" ");
							diff = newString.length() - last;
							i = i - diff;
							String temp = newString.substring(0, i);

							newString = temp + "\r\n  ";
//i=i+diff;
						} else {
							newString = newString + sVal;
							diff = 0;
						}

					} else {
						newString = newString + sVal;

					}

				}

			}
			return newString;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean identifySecondTabel(String s) {
		if ((s.contains("Documents Presented") || (s.contains("Document Name"))) && s.contains("Original")
				&& s.contains("Duplicate")) {
			return true;
		}
		return false;
	}

	public void createPdf(String dest, String input) throws IOException, DocumentException {
		boolean tableTwo = false;
		boolean tableTwoEnd = false;
		boolean firstDate = false;

		int[] secondTableIndex = null;

		double maths = Math.random();
		maths = Math.round(maths * 100000);
		double randomNumber = maths;

		String path = "/appl/IBM/WebSphere/AppServer/profiles/BPMProf01/DHLPDF/TempTxt/" + randomNumber + ".txt";
		File f1 = new File("/appl/IBM/WebSphere/AppServer/profiles/BPMProf01/DHLPDF/TempTxt/" + randomNumber + ".txt");
		try {
			FileWriter mywrite = new FileWriter(
					"/appl/IBM/WebSphere/AppServer/profiles/BPMProf01/DHLPDF/TempTxt/" + randomNumber + ".txt");
			mywrite.write(input);
			mywrite.close();
		} catch (Exception e) {
		}

//Files.writeString(path, input,StandardCharsets.UTF_8);
//LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"randomNumber"+randomNumber);
		input = getText(path);
//Document document = new Document();
//Document document = new Document(PageSize.A4,50,20,90,54);
		Document document = new Document(PageSize.A4, 50, 0, 70, 84);

// Document document = new Document(PageSize.A4);

		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
		MyEvent event = new MyEvent();
		writer.setPageEvent(event);
		document.open();
		document.add(new Chunk(""));
		String prevLine = "";
		boolean prevLineflag = false;
		String[] tableArray;
		String[] inputArray = getGeneratedString(input);
		boolean tableComplete = false;
		System.out.println(inputArray.length);
		boolean lastKeyDrawee = false;
		boolean documentValueFound = false;
		boolean lastValGood = false;
		String lastValGoodString = "";
		String paraString = "";
		String prevline = "";
		for (String line : inputArray) {
			if (line.contains("Outward Documentary Collection Covering Schedule")) {
				line = line.trim();
				line = "                                           ".concat(line);
				Paragraph preface = new Paragraph(line, font);
//preface.setAlignment(Element.ALIGN_CENTER);

				document.add(preface);
				continue;
			} else if (line.contains("Export Documents Schedule")) {
				line = line.trim();
				line = "                                                            ".concat(line);
				Paragraph preface = new Paragraph(line, font);
//preface.setAlignment(Element.ALIGN_CENTER);

				document.add(preface);
				continue;
			}

//Newly Code Added for Lower Table  ##JIRA 181 and ##JIRA 243
			if (line.contains("to be borne by Applicant") || line.contains("to be borne by Drawee")) {
				prevLine = line;
				prevLineflag = true;
			}
			if (line.contains(":") && (prevLineflag)) {

//	firstDate=true;
				tableArray = getTableString(line);
				PdfPTable table3 = new PdfPTable(2);
//	tableArray =new String[2];
				// float[] columnWidths = new float[] {10f, 20f, 30f, 10f};
				// float[] columnWidths = new float[] {28f, 40f};
				float[] columnWidths = new float[] { 22f, 40f };

				table3.setWidths(columnWidths);
				// table3.setTotalWidth(new float[]{ 500, 500 });

				// table3.setLockedWidth(true);
				// table3.setWidthPercentage(100);
				table3.setHorizontalAlignment(Element.ALIGN_LEFT);
				// table3.setWidthPercentage(100);

				//
				// String tempInput=tableArray[1].trim();

				// tableArray[0]="Goods :";
//	tableArray[0]="DATE:";
//	tableArray[1]=p[1].trim();

				PdfPCell cellOne = new PdfPCell(
						new Phrase(lineSpacing, tableArray[0], FontFactory.getFont(FONT, fntSize)));

				PdfPCell cellTwo = new PdfPCell(
						new Phrase(new Phrase(lineSpacing, ": " + tableArray[1], FontFactory.getFont(FONT, fntSize))));
				cellTwo.setHorizontalAlignment(Element.ALIGN_LEFT);
				cellOne.setBorder(Rectangle.NO_BORDER);
				cellTwo.setBorder(Rectangle.NO_BORDER);
				if (tableArray[0].contains("Total amount to be remitted")) {
					PdfPTable table5 = new PdfPTable(1);
					table5.setWidthPercentage(100); //##JIRA 182
					PdfPCell cellZero = new PdfPCell(
							new Phrase("--------------------------------------------------------------------"));
					cellZero.setBorder(Rectangle.NO_BORDER);
					table5.addCell(cellZero);
					document.add(table5);
				table3.addCell(cellOne);
				table3.addCell(cellTwo);
				document.add(table3);
				document.add(table5);

			} else {
				table3.addCell(cellOne);
				table3.addCell(cellTwo);
				document.add(table3);

			}
			continue;
			}

//ADDED FOR DATE FIRST LINE  ##JIRA 181
			if (!firstDate && line.contains("Date") && (line.contains(":")) && !line.contains("AWB/BL/TR Number")) {
				String[] p = line.split(":");
				firstDate = true;
				PdfPTable table3 = new PdfPTable(2);
				tableArray = new String[2];
//float[] columnWidths = new float[] {10f, 20f, 30f, 10f};
//float[] columnWidths = new float[] {28f, 40f};
				float[] columnWidths = new float[] { 8f, 92f };

				table3.setWidths(columnWidths);
//table3.setTotalWidth(new float[]{ 500, 500 });

//table3.setLockedWidth(true);
//table3.setWidthPercentage(100);
				table3.setHorizontalAlignment(Element.ALIGN_LEFT);
//table3.setWidthPercentage(100);

//
//String tempInput=tableArray[1].trim();

//tableArray[0]="Goods                                                                     :";
				tableArray[0] = "DATE:";
				tableArray[1] = p[1].trim();

				PdfPCell cellOne = new PdfPCell(
						new Phrase(lineSpacing, tableArray[0], FontFactory.getFont(FONT, fntSize)));

				PdfPCell cellTwo = new PdfPCell(
						new Phrase(new Phrase(lineSpacing, tableArray[1], FontFactory.getFont(FONT, fntSize))));
				cellTwo.setHorizontalAlignment(Element.ALIGN_LEFT);
				cellOne.setBorder(Rectangle.NO_BORDER);
				cellTwo.setBorder(Rectangle.NO_BORDER);
				table3.addCell(cellOne);
				table3.addCell(cellTwo);
				document.add(table3);
				continue;

			}
			String tLine = new String(line.trim());
			if (lastValGood && (tLine.isEmpty())) {
				lastValGood = false;

				tableArray = new String[2];
				PdfPTable table3 = new PdfPTable(2);
//float[] columnWidths = new float[] {10f, 20f, 30f, 10f};
//float[] columnWidths = new float[] {28f, 40f};
				float[] columnWidths = new float[] { 16f, 42f };

				table3.setWidths(columnWidths);
//table3.setTotalWidth(new float[]{ 500, 500 });

//table3.setLockedWidth(true);
//table3.setWidthPercentage(100);
				table3.setHorizontalAlignment(Element.ALIGN_LEFT);
//table3.setWidthPercentage(100);

//
//String tempInput=tableArray[1].trim();
				String tempInput = lastValGoodString.trim();
//String tempInput=JustifyText.getJustifytext(lastValGoodString.trim());
				int length = tempInput.length();
//tableArray[0]="Goods                                                                     :";
				tableArray[0] = "Goods                              :";

//tableArray[1]=JustifyText.getJustifytext(tempInput).trim();
				tableArray[1] = tempInput.trim();
//System.out.println("line    "+line);
				/*
				 * if(length>28) { tableArray[1]= getLongFormattedString(tempInput); }
				 */
//PdfPCell cellOne = new PdfPCell(new Phrase(lineSpacing,tableArray[0],
// FontFactory.getFont(FontFactory.COURIER, fntSize)));
				PdfPCell cellOne = new PdfPCell(
						new Phrase(lineSpacing, tableArray[0], FontFactory.getFont(FONT, fntSize)));

				PdfPCell cellTwo = new PdfPCell(
						new Phrase(new Phrase(lineSpacing, tableArray[1], FontFactory.getFont(FONT, fntSize))));
				cellTwo.setHorizontalAlignment(Element.ALIGN_LEFT);
				cellOne.setBorder(Rectangle.NO_BORDER);
				cellTwo.setBorder(Rectangle.NO_BORDER);
				table3.addCell(cellOne);
				table3.addCell(cellTwo);
				document.add(table3);

			}

			if (line.contains("aete                            1")) {
				System.out.println("aete                            1");
			}

			if (line.contains("BILLS OF EXCHANGE")) {
				System.out.println("check");
			}
			if (line.contains("Documents Value  :")) {
				lastKeyDrawee = false;
				documentValueFound = true;
			}
			if (line.contains("--------------------")) {
				line = " ";
				prevline = line;
				continue;
			}
			tableArray = getTableString(line);
			if (prevline.contains("Drawee           :") && tableArray == null) {
				tableArray = new String[2];
				tableArray[0] = "";
				tableArray[1] = line.trim();
				lastKeyDrawee = true;
			} else {
				prevline = line;

			}
//s.contains("Goods") && s.contains(":" )
/// newly added 
			if (line.contains("Goods") && line.contains(":")) {

				lastValGood = true;
				String[] T = line.split(":");
				if (!T[1].isEmpty()) {
					lastValGoodString = lastValGoodString + T[1].trim() + "\r\n";
					lastValGoodString = lastValGoodString.trim();
				} else {
				}
			}
// newly added for Goods above

			if (tableArray != null && !lastValGood) {

				PdfPTable table = new PdfPTable(2);
				table.setHorizontalAlignment(Element.ALIGN_LEFT);

				float[] columnWidths = new float[] { 14f, 40f };

				table.setWidths(columnWidths);
				for (int aw = 0; aw < 1; aw++) {

// PdfPCell cellOne = new PdfPCell(new Phrase(tableArray[0]));
// PdfPCell cellTwo = new PdfPCell(new Phrase(": " + tableArray[1]));

					PdfPCell cellOne = new PdfPCell(new Phrase(lineSpacing, tableArray[0],
//  FontFactory.getFont(FONT, fntSize)));
							FontFactory.getFont(FONT, fntSize)));

					PdfPCell cellTwo = null;
					if (!lastKeyDrawee) {
						String tempInput = tableArray[1].trim();
						int length = tempInput.length();
						if (length > 30) {
							tableArray[1] = getLongFormattedString(tempInput);
						}
						if (tableArray[1].trim().isEmpty()) {

							cellTwo = new PdfPCell(new Phrase(new Phrase(lineSpacing, ": " + tableArray[1].trim(),
									FontFactory.getFont(FONT, fntSize))));
							cellTwo.setHorizontalAlignment(Element.ALIGN_LEFT);

						} else {
							cellTwo = new PdfPCell(new Phrase(new Phrase(lineSpacing, ": " + tableArray[1].trim(),
									FontFactory.getFont(FONT, fntSize))));
							cellTwo.setHorizontalAlignment(Element.ALIGN_LEFT);
						}
					} else {
						if (!documentValueFound)
//if(!documentValueFound || lastValGood)     // change made for Goods again commented
						{
							String tempInput = tableArray[1].trim();
							int length = tempInput.length();
							if (length > 30) {
								tableArray[1] = getLongFormattedString(tempInput);
							}
							cellTwo = new PdfPCell(new Phrase(new Phrase(lineSpacing, "  " + tableArray[1].trim(),
									FontFactory.getFont(FONT, fntSize))));
							cellTwo.setHorizontalAlignment(Element.ALIGN_LEFT);
						}
					}

					cellOne.setBorder(Rectangle.NO_BORDER);
					cellTwo.setBorder(Rectangle.NO_BORDER);

					table.addCell(cellOne);
					table.addCell(cellTwo);

				}
				document.add(table);
			} else if (identifySecondTabel(line) && !lastValGood) {
				secondTableIndex = get2ndTableIndex(line);
				PdfPTable table1 = new PdfPTable(1);
				table1.setWidthPercentage(100);

				table1.setHorizontalAlignment(Element.ALIGN_LEFT);
				tableArray = get2ndTableContentHeader(line);
				PdfPCell cellZero = new PdfPCell(new Phrase(
						"-------------------------------------------------------------------------------------------------------"));
				cellZero.setBorder(Rectangle.NO_BORDER);

				table1.addCell(cellZero);
				document.add(table1);

				for (int aw = 0; aw < 1; aw++) {
					PdfPTable table2 = new PdfPTable(3);

					table2.setHorizontalAlignment(Element.ALIGN_LEFT);

// PdfPCell cellOne = new PdfPCell(new Phrase(tableArray[0]));
// PdfPCell cellTwo = new PdfPCell(new Phrase("          "+tableArray[1]));
// PdfPCell cellThree = new PdfPCell(new Phrase("             "+tableArray[2]));

					PdfPCell cellOne = new PdfPCell(
							new Phrase(lineSpacing, tableArray[0].trim(), FontFactory.getFont(FONT, fntSize)));

//cellOne.setVerticalAlignment(Element.ALIGN_LEFT);
//cellOne.setBorderWidth(0);
//cellOne.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
					cellOne.setHorizontalAlignment(Element.ALIGN_LEFT);
					PdfPCell cellTwo = new PdfPCell(
							new Phrase(lineSpacing, "        " + tableArray[1], FontFactory.getFont(FONT, fntSize)));
					PdfPCell cellThree = new PdfPCell(new Phrase(lineSpacing, "                  " + tableArray[2],
							FontFactory.getFont(FONT, fntSize)));

					cellOne.setBorder(Rectangle.NO_BORDER);
					cellTwo.setBorder(Rectangle.NO_BORDER);
					cellThree.setBorder(Rectangle.NO_BORDER);
					table2.addCell(cellOne);
					table2.addCell(cellTwo);
					table2.addCell(cellThree);
					document.add(table2);

				}
				PdfPTable table3 = new PdfPTable(1);
				table3.setWidthPercentage(100);

				table3.setHorizontalAlignment(Element.ALIGN_LEFT);

				PdfPCell cellLast = new PdfPCell(new Phrase(
						"-------------------------------------------------------------------------------------------------------"));
				cellLast.setBorder(Rectangle.NO_BORDER);

				table3.addCell(cellLast);

				document.add(table3);
				tableTwo = true;

			} else {
				if (tableTwo) {

					String tempStr = line.trim();
					if (tempStr.isEmpty()) {
						tableTwo = false;
						tableTwoEnd = true;
					} else {
						tableArray = get2ndTableContentBody(line, secondTableIndex);

						for (int aw = 0; aw < 1; aw++) {
							PdfPTable table2 = new PdfPTable(3);
							table2.setWidthPercentage(300);

							table2.setHorizontalAlignment(Element.ALIGN_LEFT);
							table2.setWidthPercentage(100);

							if (tableArray[0].contains("CERTIFICATE OF ORIGI")) {
								System.out.println("check");
							}
							PdfPCell cellOne = new PdfPCell(
									new Phrase(lineSpacing, tableArray[0], FontFactory.getFont(FONT, fntSize)));
// PdfPCell cellOne = new PdfPCell(new Phrase(tableArray[0]));
// PdfPCell cellTwo = new PdfPCell(new Phrase("   "+tableArray[1]));
							PdfPCell cellTwo = new PdfPCell(new Phrase(
									new Phrase(lineSpacing, tableArray[1], FontFactory.getFont(FONT, fntSize))));
							PdfPCell cellThree = new PdfPCell(new Phrase(
									new Phrase(lineSpacing, tableArray[2], FontFactory.getFont(FONT, fntSize))));

//PdfPCell cellThree = new PdfPCell(new Phrase(tableArray[2]));

							cellOne.setBorder(Rectangle.NO_BORDER);
							cellTwo.setBorder(Rectangle.NO_BORDER);
							cellThree.setBorder(Rectangle.NO_BORDER);
							table2.addCell(cellOne);
							table2.addCell(cellTwo);
							table2.addCell(cellThree);
							document.add(table2);
//tableTwo=false;
						}

					}
				} else {
// below code added for Good
// above code added for Good

					if (lastValGood && !(line.contains("Goods") && (line.contains(":")))) {
						lastValGoodString = lastValGoodString + line.trim() + "\r\n";
						lastValGoodString = lastValGoodString.trim();
					}

					else {
						if (!lastValGood) {
							if (tableTwoEnd) {

								System.out.println(line);
								paraString = paraString + line.trim();
//line = JustifyText.getJustifytext(line);
							}

//Paragraph p = new Paragraph(line,font);
							line = line.trim() + "\n";
							Paragraph p = new Paragraph(line, font);
//p.setAlignment(Element.ALIGN_JUSTIFIED);
//p.setFont(font);
							document.add(p);

						}
					}
				}

			}

		}
// Paragraph p = new Paragraph(paraString);
// p.setAlignment(Element.ALIGN_JUSTIFIED);
// p.setFont(font);
// document.add(p);
		document.close();

	}

	public static int[] get2ndTableIndex(String s) {
		int[] indexArray = new int[4];
		if (!s.isEmpty()) {

			int index1 = s.indexOf("Original") - 2;
			int index2 = s.indexOf("Original") + "Original".length() + 2;

			int index3 = s.indexOf("Duplicate") - 2;
			int index4 = s.indexOf("Duplicate") + "Duplicate".length() + 2;
			indexArray[0] = index1;
			indexArray[1] = index2;
			indexArray[2] = index3;
			indexArray[3] = index4;

		}
		return indexArray;
	}

	public static String[] get2ndTableContentBody(String s, int[] secondTableIndex) {
		String sValue = s.trim().replaceAll("\\s+", " ");
		if (s.contains("CERTIFICATE OF ORIGI")) {
			System.out.println("check");
		}
		String[] splitVal = sValue.split(" ");
		boolean firstDigit = false;
		int counter = 0;
		int arrayCounter = 0;

		String[] returnArray = new String[3];
		String col1 = "";
		if (secondTableIndex != null) {

			col1 = s.substring(secondTableIndex[0], secondTableIndex[1]);
			col1 = col1.trim();

		}

//col1=s.substring(30, s.length());
//int index2=col1.indexOf(" ");
//col1=col1.substring(0, index2);

		String col2 = "";
		if (secondTableIndex != null) {
			if (secondTableIndex[3] >= s.length()) {
				secondTableIndex[3] = s.length() - 1;
			}
			col2 = s.substring(secondTableIndex[2], secondTableIndex[3]);
			col2 = col2.trim();

		}
//col2=s.substring(49, s.length());
//int index3=col2.indexOf(" ");
//col2=col2.substring(0, index3);

		for (String temp : splitVal) {
			if (temp.matches("\\d+")) {
				if (!firstDigit) {

					firstDigit = true;
//String sname=sValue.substring(0, counter+1);
					String sname = s.substring(0, counter + 2);

					sname = sname.trim();
					returnArray[0] = sname;
					if (!col1.isEmpty()) {// ##JIRA 247
						//returnArray[1] = splitVal[arrayCounter];  //Commented by reyaz 22-11-2023
						returnArray[1] = col1;     //Added by reyaz 22-11-2023
					} else if (col1.isEmpty()) {
						returnArray[1] = " ";
					}

					if (!col2.isEmpty()) {
						//returnArray[2] = splitVal[arrayCounter++];   //Commented by reyaz 22-11-2023
						returnArray[2] = col2;     //Added by reyaz 22-11-2023
					} else if (col2.isEmpty()) {
						returnArray[2] = " ";
					}
					break;
				}

			}
			counter = counter + temp.length();
			arrayCounter++;
			if (splitVal.length == arrayCounter) {
				if (col1.isEmpty()) {
					returnArray[1] = " ";
				}
				if (col2.isEmpty()) {
					returnArray[2] = " ";
				}
// String sname=sValue.substring(0, counter+1);
				String sname = s.substring(0, counter + 2);

				sname = sname.trim();
				returnArray[0] = sname;
			}

		}

		return returnArray;
	}

	public static String[] get2ndTableContentHeader(String s) {
		s = s.trim();
		String[] splitVal = s.split("          ");
		int counter = 0;
		for (String str : splitVal) {
			splitVal[counter] = splitVal[counter].trim();
			counter++;
		}
		/*
		 * char[] temp=s.toCharArray(); String cellVal=""; int contSpace=0; for(char
		 * a:temp) {
		 * 
		 * 
		 * 
		 * String eachChar=String.valueOf(a);
		 * if(eachChar.matches("\"abc.\".matches (\"\\\\w+\\\\.?\")")) {
		 * cellVal=cellVal+eachChar; contSpace=0; } else { contSpace++;
		 * if(!cellVal.isEmpty()) { if(contSpace==1) { cellVal=cellVal+" "; }
		 * 
		 * 
		 * }
		 * 
		 * } }
		 */
		return splitVal;
	}

	static class MyEvent extends PdfPageEventHelper {

		Image image;

//String header="header.JPG";
//String footer="footer.JPG";

		int totalNumber = 0;
		PdfTemplate total;

		public void onOpenDocument(PdfWriter writer, Document document) {

// total = writer.getDirectContent().createTemplate(30, 12);
// System.out.println("No : "+writer.getPageNumber());
// System.out.println("PDF Template : "+total);
		}

		public void onStartPage(PdfWriter writer, Document document) {

// //total = writer.getDirectContent().createTemplate(30, 12);
// writer.setPageCount(++totalNumber);
// System.out.println("Page No : "+writer.getPageNumber());
// System.out.println("Total No : "+totalNumber);
// //totalNumber++; 
		}

		public void onCloseDocument(PdfWriter writer, Document document) {

// Font ffont = new Font(Font.FontFamily.UNDEFINED, 10, Font.NORMAL);
// PdfContentByte cb = writer.getDirectContent();  
//
// ColumnText.showTextAligned(cb, Element.ALIGN_LEFT,
// new Phrase(String.valueOf(writer.getPageNumber() - 1), ffont),
// 2, 1, 0);
		}

		/*
		 * @Override public void onOpenDocument(PdfWriter writer, Document document) {
		 * // TODO Auto-generated method stub try { try { //document.add(new Chunk(""));
		 * } catch (Exception e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 * 
		 * //image = Image.getInstance("header.JPG"); image =
		 * Image.getInstance(getImage(new FileInputStream("header.JPG")));
		 * 
		 * } catch (BadElementException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (MalformedURLException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } catch (IOException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); }
		 * //image.setAbsolutePosition(12, 300);
		 * 
		 * }
		 */
		@Override
		public void onEndPage(PdfWriter writer, Document document) {
			PdfContentByte cb = writer.getDirectContent();
			String text = "";
			String text3 = "";
			String text0 = "";

			Font ffont = new Font(Font.FontFamily.UNDEFINED, 8, Font.NORMAL);

			createHeaderFooter(writer);
			addFooterImproved(writer);

			if ("Conventional".equalsIgnoreCase(relType)) {
				text0 = "Trade Finance Services located at,";
				text3 = "Fax: +971 26109896, Tel: 600 5 TRADE (600 5 87233), www.adcb.com. ";

			} else if ("Islamic".equalsIgnoreCase(relType)) {
				text0 = "Islamic Banking, Trade Finance Services located at,";
				text3 = "Fax: +971 26109896, Tel: 600 5 TRADE (600 5 87233), www.adcbislamic.com. ";
			}

			Phrase footerline = new Phrase(
					"------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------",
					ffont);
			ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, footerline, 50, document.bottom() - 10, 0);

			text = "Abu Dhabi Commercial Bank PJSC is licensed and regulated by the Central Bank of the United Arab Emirates";
			Phrase footer = new Phrase(text, ffont);
			ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, footer, 50, document.bottom() - 20, 0);

			Phrase footer0 = new Phrase(text0, ffont);
			ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, footer0, 50, document.bottom() - 30, 0);

			String text1 = "Abu Dhabi: 2nd Head Office building, Ground floor, PO Box 3865, Abu Dhabi, UAE ";
			Phrase footer1 = new Phrase(text1, ffont);
			ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, footer1, 50, document.bottom() - 40, 0);

			String text2 = "Dubai: Mezzanine Floor, Karama Branch, P.O Box 12808, Dubai, UAE";
			Phrase footer2 = new Phrase(text2, ffont);
			ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, footer2, 50, document.bottom() - 50, 0);

			Phrase footer3 = new Phrase(text3, ffont);
			ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, footer3, 50, document.bottom() - 60, 0);

		}

		public byte[] getImage(FileInputStream url) throws IOException {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//InputStream is = url.openStream ();
			InputStream is = url;
			byte[] b = new byte[4096];
			int n;
			while ((n = is.read(b)) > -1) {
				baos.write(b, 0, n);
			}
			return baos.toByteArray();
		}
	}

	private static void createHeaderFooter(PdfWriter writer) {

		PdfContentByte cb = writer.getDirectContent();
		try {
			Image imgSoc1 = Image.getInstance(header);

			imgSoc1.scaleToFit(130, 130);
			imgSoc1.setAbsolutePosition(440, 770);

			cb.addImage(imgSoc1);
//Image imgSoc2 = Image.getInstance(footer);
//
//PdfContentByte cb2 = writer.getDirectContent();  
//                 
//
////imgSoc2.scaleToFit(110,110);
//imgSoc2.setAbsolutePosition(0,10);
//imgSoc2.setAlignment(Element.ALIGN_LEFT);
//
//cb2.addImage(imgSoc2);

// TODO Auto-generated method stub
//image.scaleToFit(100,100);
			try {
//writer.add(image);
			} catch (Exception e) {
// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void addFooterImproved(PdfWriter writer) {
		PdfPTable footer = new PdfPTable(1);
		try {
// set defaults
			footer.setWidths(new int[] { 23 });
			footer.setWidthPercentage(50);

			footer.setTotalWidth(527);
// footer.setLockedWidth(true);
			footer.getDefaultCell().setFixedHeight(30);
			footer.getDefaultCell().setBorder(Rectangle.TOP);
			footer.getDefaultCell().setBorderColor(BaseColor.WHITE);
			footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
//footer.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			footer.addCell(new Phrase(String.format("Page %d of 2", writer.getPageNumber()),
					new Font(Font.FontFamily.HELVETICA, 8)));

// add placeholder for total page count
			PdfPCell totalPageCount = new PdfPCell(new Phrase(""));
			totalPageCount.setBorder(Rectangle.TOP);
			totalPageCount.setBorderColor(BaseColor.WHITE);
			footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
// footer.getDefaultCell().setBorder(Rectangle.NO_BORDER);

			footer.addCell(totalPageCount);

// write page
			PdfContentByte canvas = writer.getDirectContent();
			canvas.beginMarkedContentSequence(PdfName.ACTUALTEXT);
			footer.writeSelectedRows(0, -1, 34, 30, canvas);
// footer.writeSelectedRows(0, -1, 68, 20, canvas);
			canvas.endMarkedContentSequence();
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}
	}

	public static String getText(String Path) throws IOException {
		BufferedReader br = null;
		int nextLinePosition = 100;
		int fontSize = 12;
// LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO," Doc path"+Path);
		br = new BufferedReader(new FileReader(Path));
		String line;
		String returnString = "";
		while ((line = br.readLine()) != null) {
			returnString = returnString + line + "\r\n";
		}

		br.close();

		File f = new File(Path);
		if (f.exists()) {
			f.delete();
// LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO," Doc delte path"+Path);
		}

		return returnString;
	}

	public static String getJustifytext(String input) {
		String array[] = input.split(" ");
		List<String> list = fullJustify(array, 300);

		String returnVal = "";
		for (String temp : list) {
			returnVal = returnVal + temp + "\r\n";
		}
//System.out.println(returnVal);                                                                                                                                                                                                          

		return returnVal;

	}

	static class IntPair {
		// The cost or badness
		final int x;

		// The index of word at the beginning of a line
		final int y;

		IntPair(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public static List<String> fullJustify(String[] words, int L) {
		IntPair[] memo = new IntPair[words.length + 1];

		// Base case
		memo[words.length] = new IntPair(0, 0);

		for (int i = words.length - 1; i >= 0; i--) {
			int score = Integer.MAX_VALUE;
			int nextLineIndex = i + 1;
			for (int j = i + 1; j <= words.length; j++) {
				int badness = calcBadness(words, i, j, L);
				if (badness < 0 || badness == Integer.MAX_VALUE)
					break;
				int currScore = badness + memo[j].x;
				if (currScore < 0 || currScore == Integer.MAX_VALUE)
					break;
				if (score > currScore) {
					score = currScore;
					nextLineIndex = j;
				}
			}
			memo[i] = new IntPair(score, nextLineIndex);
		}

		List<String> result = new ArrayList<>();
		int i = 0;
		while (i < words.length) {
			String line = getLine(words, i, memo[i].y);
			result.add(line);
			i = memo[i].y;
		}
		return result;
	}

	public static int calcBadness(String[] words, int start, int end, int width) {
		int length = 0;
		for (int i = start; i < end; i++) {
			length += words[i].length();
			if (length > width)
				return Integer.MAX_VALUE;
			length++;
		}
		length--;
		int temp = width - length;
		return temp * temp;
	}

	public static String getLine(String[] words, int start, int end) {
		StringBuilder sb = new StringBuilder();
		for (int i = start; i < end - 1; i++) {
			sb.append(words[i] + " ");
		}
		sb.append(words[end - 1]);

		return sb.toString();
	}

}
