package com.newgen.cbg.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.jsoup.Jsoup;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontProvider;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.ElementList;
import com.itextpdf.tool.xml.Pipeline;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFiles;
import com.itextpdf.tool.xml.css.CssFilesImpl;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.TagProcessor;
import com.itextpdf.tool.xml.html.TagProcessorFactory;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParserListener;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import com.newgen.cbg.logger.DSCOPLogMe;

public class ConvertHtmlToPdf {
	
	public static void pdfGen(String inputHmtl, String tempHmtlPath, String pdfPath, boolean headerFlg, boolean footerFlg, String logoPath, String wiName, String text1, String text2, String text3, String img, String img2) {
		String formattedData = readHTMLData(inputHmtl);
		int startindex = 0;
		int lastindex = 0;
		String strwidth = "";
		startindex = formattedData.indexOf("<img");
		while (startindex > 0) {
			String x = formattedData.substring(startindex, formattedData.indexOf(">", startindex) + 1);
			System.out.println(x);
			formattedData = formattedData.replace(x, x.replace("width", "").replace("WIDTH", ""));
			x = x.replace("width", "").replace("WIDTH", "");
			formattedData = formattedData.replace(x, x.replace("height", "").replace("HEIGHT", ""));
			startindex = formattedData.indexOf("<img", startindex + 1);
		} 
		formattedData = formattedData.replace("border-image: none;", "");
		formattedData = formattedData.replace("background-color: transparent;", "");
		formattedData = formattedData.replace("black 1.0pt", "black 0.3pt");
		formattedData = formattedData.replace("border:.5pt", "border: 1px");
		formattedData = formattedData.replace("windowtext", "");
		String convertedHtmlPath = String.valueOf(tempHmtlPath) + "/converted.html";
		writeHTMLData(formattedData, convertedHtmlPath);
		try {
			convertHtmlToPdf(convertedHtmlPath, pdfPath, headerFlg, footerFlg, wiName, logoPath, text1, text2, text3,img);
		} catch (IOException e) {
			e.printStackTrace();
		
		} 
	}
	
	//added by aditi
	public static void pdfGen(String inputHmtl, String tempHmtlPath, String pdfPath, boolean headerFlg, boolean footerFlg, String logoPath, String wiName, String text1, String text2, String text3, String img) {
		String formattedData = readHTMLData(inputHmtl);
		int startindex = 0;
		int lastindex = 0;
		String strwidth = "";
		startindex = formattedData.indexOf("<img");
		while (startindex > 0) {
			String x = formattedData.substring(startindex, formattedData.indexOf(">", startindex) + 1);
			System.out.println(x);
			formattedData = formattedData.replace(x, x.replace("width", "").replace("WIDTH", ""));
			x = x.replace("width", "").replace("WIDTH", "");
			formattedData = formattedData.replace(x, x.replace("height", "").replace("HEIGHT", ""));
			startindex = formattedData.indexOf("<img", startindex + 1);
		} 
		formattedData = formattedData.replace("border-image: none;", "");
		formattedData = formattedData.replace("background-color: transparent;", "");
		formattedData = formattedData.replace("black 1.0pt", "black 0.3pt");
		formattedData = formattedData.replace("border:.5pt", "border: 1px");
		formattedData = formattedData.replace("windowtext", "");
		String convertedHtmlPath = String.valueOf(tempHmtlPath) + "/converted.html";
		writeHTMLData(formattedData, convertedHtmlPath);
		try {
			convertHtmlToPdf(convertedHtmlPath, pdfPath, headerFlg, footerFlg, wiName, logoPath, text1, text2, text3,img);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	private static void manipulatePdf(String metaFile, String outputPdfFilePath,String t1,String t2,String t3,String img) throws IOException, DocumentException {
		PdfReader reader = new PdfReader(metaFile);
		int n = reader.getNumberOfPages();
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputPdfFilePath));
		PdfContentByte pagecontent;

		String imageUrl = img;//"C:\\Users\\Bikash\\Desktop\\logo-main.png";
		Image image = Image.getInstance(imageUrl);
		image = Image.getInstance(imageUrl);

		for (int i = 0; i < n;) {
			pagecontent = stamper.getOverContent(++i);
		
			pagecontent.addImage(image, 130, 0, 0, 40,3500, 770);
			//pagecontent.addImage(image, 500, 0, 0, 40,50,770);
		}
		stamper.close();
		reader.close();
	}
	
	public static void pdfGen(String inputHmtl, String tempHmtlPath, String pdfPath, boolean headerFlg, boolean footerFlg, String logoPath, String wiName, String text1, String text2, String text3) {
		String formattedData = readHTMLData(inputHmtl);
		int startindex = 0;
		int lastindex = 0;
		String strwidth = "";
		startindex = formattedData.indexOf("<img");
		while (startindex > 0) {
			String x = formattedData.substring(startindex, formattedData.indexOf(">", startindex) + 1);
			System.out.println(x);
			formattedData = formattedData.replace(x, x.replace("width", "").replace("WIDTH", ""));
			x = x.replace("width", "").replace("WIDTH", "");
			formattedData = formattedData.replace(x, x.replace("height", "").replace("HEIGHT", ""));
			startindex = formattedData.indexOf("<img", startindex + 1);
		} 
		formattedData = formattedData.replace("border-image: none;", "");
		formattedData = formattedData.replace("background-color: transparent;", "");
		formattedData = formattedData.replace("black 1.0pt", "black 0.3pt");
		formattedData = formattedData.replace("border:.5pt", "border: 1px");
		formattedData = formattedData.replace("windowtext", "");
		String convertedHtmlPath = String.valueOf(tempHmtlPath) + "/converted.html";
		writeHTMLData(formattedData, convertedHtmlPath);
		try {
			convertHtmlToPdf(convertedHtmlPath, pdfPath, headerFlg, footerFlg, wiName, logoPath, text1, text2, text3);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public static boolean convertHtmlToPdf(String inputFilePath, String outputPdfFilePath, boolean headerFlag, boolean footerFlag, String wiName, String logoPath, String text1, String text2, String text3, String img, String img2) throws IOException {
	    boolean conversionFlag = false;
	    Document document = null;
	    try {
	      document = new Document(PageSize.A4, 24.0F, 36.0F, 100.0F, 72.0F);
	      String metaFile = outputPdfFilePath.substring(0, outputPdfFilePath.indexOf(".")).concat("_meta.pdf");
	      PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(metaFile));
	      document.open();
	      TagProcessorFactory tagProcessorFactory = Tags.getHtmlTagProcessorFactory();
	      tagProcessorFactory.removeProcessor("img");
	      tagProcessorFactory.addProcessor((TagProcessor)new ImageTagProcessor(), new String[] { "img" });
	      CssFilesImpl cssFiles = new CssFilesImpl();
	      cssFiles.add(XMLWorkerHelper.getInstance().getDefaultCSS());
	      StyleAttrCSSResolver cssResolver = new StyleAttrCSSResolver((CssFiles)cssFiles);
	      XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider();
	      HtmlPipelineContext hpc = new HtmlPipelineContext((CssAppliers)new CssAppliersImpl((FontProvider)fontProvider));
	      //fontProvider.register("resources/NotoNaskhArabic-Regular.ttf");
	      hpc.setAcceptUnknown(true).autoBookmark(true).setTagFactory(tagProcessorFactory);
	      final HtmlPipeline htmlPipeline = new HtmlPipeline(hpc, (Pipeline)new PdfWriterPipeline(document, writer));
	      final CssResolverPipeline cssResolverPipeline = new CssResolverPipeline((CSSResolver)cssResolver, (Pipeline)htmlPipeline);
	      final XMLWorker worker = new XMLWorker((Pipeline)cssResolverPipeline, true);
	      final Charset charset = Charset.forName("UTF-8");
	      final com.itextpdf.tool.xml.parser.XMLParser xmlParser = new com.itextpdf.tool.xml.parser.XMLParser(true, 
	          (XMLParserListener)worker, charset);
	      final InputStream is = new FileInputStream(inputFilePath);
	      xmlParser.parse(is, charset);
	      document.close();
	      writer.close();
	      File tempFile = new File(metaFile);
	      if (tempFile.exists())
	        tempFile.delete(); 
	    } catch (Exception e) {
	      e.printStackTrace();
	    } 
	    return conversionFlag;
	  }
	
	//added by aditi for header
	public static boolean convertHtmlToPdf(String inputFilePath, String outputPdfFilePath, boolean headerFlag, boolean footerFlag, String wiName, String logoPath, String text1, String text2, String text3, String logoPathHeader) throws IOException {
	    boolean conversionFlag = false;
	    Document document = null;
	    try {
	      document = new Document(PageSize.A4, 24.0F, 36.0F, 100.0F, 72.0F);
	      String metaFile = outputPdfFilePath.substring(0, outputPdfFilePath.indexOf(".")).concat("_meta.pdf");
	      PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(metaFile));
	      document.open();
	      TagProcessorFactory tagProcessorFactory = Tags.getHtmlTagProcessorFactory();
	      tagProcessorFactory.removeProcessor("img");
	      tagProcessorFactory.addProcessor((TagProcessor)new ImageTagProcessor(), new String[] { "img" });
	      CssFilesImpl cssFiles = new CssFilesImpl();
	      cssFiles.add(XMLWorkerHelper.getInstance().getDefaultCSS());
	      StyleAttrCSSResolver cssResolver = new StyleAttrCSSResolver((CssFiles)cssFiles);
	      XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider();
	      HtmlPipelineContext hpc = new HtmlPipelineContext((CssAppliers)new CssAppliersImpl((FontProvider)fontProvider));
	      //fontProvider.register("resources/NotoNaskhArabic-Regular.ttf");
	      hpc.setAcceptUnknown(true).autoBookmark(true).setTagFactory(tagProcessorFactory);
	      final HtmlPipeline htmlPipeline = new HtmlPipeline(hpc, (Pipeline)new PdfWriterPipeline(document, writer));
	      final CssResolverPipeline cssResolverPipeline = new CssResolverPipeline((CSSResolver)cssResolver, (Pipeline)htmlPipeline);
	      final XMLWorker worker = new XMLWorker((Pipeline)cssResolverPipeline, true);
	      final Charset charset = Charset.forName("UTF-8");
	      final com.itextpdf.tool.xml.parser.XMLParser xmlParser = new com.itextpdf.tool.xml.parser.XMLParser(true, 
	          (XMLParserListener)worker, charset);
	      final InputStream is = new FileInputStream(inputFilePath);
	      xmlParser.parse(is, charset);
	      document.close();
	      writer.close();
	      //calling manipulatepdf()
	      manipulatePdf(metaFile, outputPdfFilePath, text1, text2, text3, logoPathHeader);
	      File tempFile = new File(metaFile);
	      if (tempFile.exists())
	        tempFile.delete(); 
	    } catch (Exception e) {
	      e.printStackTrace();
	    } 
	    return conversionFlag;
	  }
	
	public static boolean convertHtmlToPdf(String inputFilePath, String outputPdfFilePath, boolean headerFlag, boolean footerFlag, String wiName, String logoPath, String text1, String text2, String text3) throws IOException {
	    boolean conversionFlag = false;
	    Document document = null;
	    try {
	      document = new Document(PageSize.A4, 24.0F, 36.0F, 100.0F, 72.0F);
	      String metaFile = outputPdfFilePath.substring(0, outputPdfFilePath.indexOf(".")).concat(".pdf");
	      DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"metaFile ===> "+ metaFile);
	      PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(metaFile));
	      document.open();
	      TagProcessorFactory tagProcessorFactory = Tags.getHtmlTagProcessorFactory();
	      tagProcessorFactory.removeProcessor("img");
	      tagProcessorFactory.addProcessor((TagProcessor)new ImageTagProcessor(), new String[] { "img" });
	      CssFilesImpl cssFiles = new CssFilesImpl();
	      cssFiles.add(XMLWorkerHelper.getInstance().getDefaultCSS());
	      StyleAttrCSSResolver cssResolver = new StyleAttrCSSResolver((CssFiles)cssFiles);
	      XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider();
	      HtmlPipelineContext hpc = new HtmlPipelineContext((CssAppliers)new CssAppliersImpl((FontProvider)fontProvider));
	      //fontProvider.register("resources/NotoNaskhArabic-Regular.ttf");
	      hpc.setAcceptUnknown(true).autoBookmark(true).setTagFactory(tagProcessorFactory);
	      final HtmlPipeline htmlPipeline = new HtmlPipeline(hpc, (Pipeline)new PdfWriterPipeline(document, writer));
	      final CssResolverPipeline cssResolverPipeline = new CssResolverPipeline((CSSResolver)cssResolver, (Pipeline)htmlPipeline);
	      final XMLWorker worker = new XMLWorker((Pipeline)cssResolverPipeline, true);
	      final Charset charset = Charset.forName("UTF-8");
	      final com.itextpdf.tool.xml.parser.XMLParser xmlParser = new com.itextpdf.tool.xml.parser.XMLParser(true, 
	          (XMLParserListener)worker, charset);
	      final InputStream is = new FileInputStream(inputFilePath);
	      xmlParser.parse(is, charset);
	      document.close();
	      writer.close();
	      File tempFile = new File(metaFile);
			/*
			 * if (tempFile.exists()) tempFile.delete();
			 */
	    } catch (Exception e) {
	      e.printStackTrace();
	      DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"metaFile Exception ===> "+ e);
	    } 
	    return conversionFlag;
	  }
	
	public static String readHTMLData(String inputFile) {
		String data = "";
		BufferedReader reader = null;
		String str = "";
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(inputFile)), "UTF-8"));
			while ((str = reader.readLine()) != null)
				data = String.valueOf(data) + str; 
		} catch (UnsupportedEncodingException|FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		} 
		return data;
	}

	public static void writeHTMLData(String inputData, String outputFilepath) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(new File(outputFilepath)), Charset.forName("UTF-8")));
			writer.write(getformattedXHTML(inputData));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		} 
	}

	private static String getformattedXHTML(String HTML) {
		String xhtml = "";
		org.jsoup.nodes.Document doc = Jsoup.parse(HTML, "UTF-8");
		try {
			doc.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);
			xhtml = doc.html();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return xhtml;
	}
}
