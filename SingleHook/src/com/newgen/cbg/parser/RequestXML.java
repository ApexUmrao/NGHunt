package com.newgen.cbg.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class RequestXML 
{
	private String requestXmlText;
	private RequestXMLNode requestXmlNode;

	public String getRequestXmlText()
	{
		return this.requestXmlText;
	}

	public void setRequestXmlText(String requestXmlText) {
		this.requestXmlText = requestXmlText;
	}


	public RequestXMLNode getRequestXmlNode()
	{
		return this.requestXmlNode;
	}

	public void setRequestXmlNode(RequestXMLNode requestXmlNode)
	{
		this.requestXmlNode = requestXmlNode;
	}


	public RequestXML requestXMLToRequest()
	{
		String requestText = getRequestXmlText();
		RequestXML toReturnRequest = null;
		if (requestText != null) {
			requestText = requestText.trim();
			if (!("".equals(requestText))) {
				ByteArrayInputStream byteArrayInputStream = null;
				StringBuffer parseXMLString = new StringBuffer("");
				try {
					parseXMLString.append(requestText.trim());
					byteArrayInputStream = new ByteArrayInputStream(parseXMLString.toString().getBytes());
					XMLHandler essentialMastersProviderXMLHandler = new XMLHandler();
					SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
					SAXParser saxParser = saxParserFactory.newSAXParser();

					saxParser.parse(byteArrayInputStream, essentialMastersProviderXMLHandler);
					toReturnRequest = essentialMastersProviderXMLHandler.getRequestXML();
				}
				catch (SAXException e) {
					parseXMLString = new StringBuffer("");
					parseXMLString.append("<Config>");
					parseXMLString.append(requestText.trim());
					parseXMLString.append("</Config>");
					byteArrayInputStream = new ByteArrayInputStream(parseXMLString.toString().getBytes());
					XMLHandler essentialMastersProviderXMLHandler = new XMLHandler();
					SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
					try
					{
						SAXParser saxParser = saxParserFactory.newSAXParser();
						saxParser.parse(byteArrayInputStream, essentialMastersProviderXMLHandler);
						toReturnRequest = essentialMastersProviderXMLHandler.getRequestXML();
					}
					catch (ParserConfigurationException e1)
					{
						e1.printStackTrace();
					}
					catch (SAXException e1) {
						e1.printStackTrace();
					}
					catch (IOException e1) {
						e1.printStackTrace();
					}

				}
				catch (Exception e)
				{
					e.printStackTrace();
				} finally {
					if (byteArrayInputStream != null) {
						try {
							byteArrayInputStream.close();
						}
						catch (IOException e) {
							e.printStackTrace();
						}
						byteArrayInputStream = null;
					}
					if (toReturnRequest == null)
						toReturnRequest = new RequestXML();
					toReturnRequest.setRequestXmlText(requestText);
				}
			}
			return this;
		}
		return toReturnRequest;
	}
}