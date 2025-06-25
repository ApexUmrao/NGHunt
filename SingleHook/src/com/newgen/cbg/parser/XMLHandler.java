package com.newgen.cbg.parser;

import java.util.EmptyStackException;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

public class XMLHandler extends DefaultHandler2
{
	private RequestXML requestXML;
	private StringBuilder textWithinNode = new StringBuilder();
	Stack<RequestXMLNode> stack;

	public void startDocument()	throws SAXException
	{
		super.startDocument();
		this.requestXML = new RequestXML();
		this.stack = new Stack();
		 textWithinNode = new StringBuilder();
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes)	throws SAXException, EmptyStackException
	{
		super.startElement(uri, localName, qName, attributes);
//		textWithinNode=null;
		textWithinNode.setLength(0);
		RequestXMLNode requestXMLNode = new RequestXMLNode();
		requestXMLNode.setXMLNodeName(qName);
		for (int i = 0; i < attributes.getLength(); ++i) {
			RequestXMLAttribute xmlAttribute = new RequestXMLAttribute();
			xmlAttribute.setAttributeName(attributes.getQName(i));
			xmlAttribute.setAttributeValue(attributes.getValue(i));
			requestXMLNode.addToAttributes(xmlAttribute);
		}
		if (!(this.stack.empty())) {
			RequestXMLNode parentNode = (RequestXMLNode)this.stack.peek();
			parentNode.addToChildConfigurationItems(requestXMLNode);
		} else {
			this.requestXML.setRequestXmlNode(requestXMLNode);
		}
		this.stack.push(requestXMLNode);
	}

	public void endElement(String uri, String localName, String qName) throws SAXException, EmptyStackException
	{
		super.endElement(uri, localName, qName);
		RequestXMLNode configurationNode = (RequestXMLNode)this.stack.pop();
		configurationNode.setTextWithinNode(this.textWithinNode.toString());
	}

	public void endDocument() throws SAXException
	{
		super.endDocument();
	}

	public void characters(char[] ch, int start, int length) throws SAXException
	{

		String tag = new String(ch, start, length);
		if(tag.trim().length()>0){
			textWithinNode.append(tag);
		}
//		super.characters(ch, start, length);
//		if(textWithinNode == null){
//			this.textWithinNode = new String(ch, start, length).trim();
//		}
//		else{
//			this.textWithinNode += new String(ch, start, length).trim();
//		}
	}

	public RequestXML getRequestXML()
	{
		return this.requestXML;
	}
}