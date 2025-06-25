
package com.newgen.cbg.utils;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.newgen.cbg.logger.DSCOPLogMe;
import com.newgen.cbg.parser.RequestXMLNode;
import com.newgen.cbg.parser.XMLHandler;

public class EnquiryParser {

	public static HashMap<String,String> parseXML(String xml) throws ParserConfigurationException, SAXException, IOException{
		
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Parser Inside" + xml);
		HashMap<String,String> fieldVal = new HashMap<String,String>();
		try{
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			XMLHandler xmlhandler = new XMLHandler();
			saxParser.parse(new InputSource(new StringReader(xml)), xmlhandler); 
			Set<RequestXMLNode> aa = xmlhandler.getRequestXML().getRequestXmlNode().getChildXMLItems();
			//DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, xmlhandler.getRequestXML().getRequestXmlNode().getTextWithinNode());
			for (RequestXMLNode requestXMLNode4 : aa) {
				fieldVal.put(requestXMLNode4.getXmlNodeName(), requestXMLNode4.getTextWithinNode()+"");
			}
		}catch(Exception ex){
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, "EnquiryParser COP :"+ex);
		}
		
		return fieldVal;
	}
}
