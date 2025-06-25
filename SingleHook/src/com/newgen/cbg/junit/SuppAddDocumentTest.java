package com.newgen.cbg.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import com.newgen.cbg.callhandler.CallEntity;
import com.newgen.cbg.calls.SuppAddDocument;
import com.newgen.cbg.logger.DSCOPDBLogMe;
import com.newgen.cbg.logger.DSCOPLogMe;
import com.newgen.cbg.utils.APCallCreateXML;
import com.newgen.cbg.utils.DSCOPConfigurations;
import com.newgen.cbg.utils.XMLParser;
@RunWith(PowerMockRunner.class)
@PrepareForTest({com.newgen.cbg.utils.APCallCreateXML.class,com.newgen.cbg.logger.DSCOPLogMe.class,com.newgen.cbg.logger.DSCOPDBLogMe.class})
@PowerMockIgnore({ "javax.net.ssl.*", "javax.crypto.*", "javax.management.*", "jdk.internal.reflect.*" })

public class SuppAddDocumentTest {
	String s1 = "";
	String s2 = "10";
	String s3 = "10";
	Boolean b = true;
	HashMap hm = Mockito.mock(HashMap.class);
	XMLParser mockXMLParser = Mockito.mock(XMLParser.class);

	CallEntity entity = Mockito.mock(CallEntity.class);
	private APCallCreateXML mockAPCallCreateXML = Mockito.mock(APCallCreateXML.class);
	
	@Test
	public void testHandleAddDocData_AE() throws Exception {
		try {
			PowerMockito.mockStatic(DSCOPLogMe.class);
			PowerMockito.mockStatic(APCallCreateXML.class);
			PowerMockito.mockStatic(DSCOPDBLogMe.class);
			PowerMockito.mockStatic(DSCOPConfigurations.class);
			String expectedXmlResponse = "<xml><MainCode>0</MainCode><TotalRetrieved>1</TotalRetrieved><NATIONALITY>AE</NATIONALITY><EIDA_DOC_NAME>EIDA</EIDA_DOC_NAME><EIDA_DOC_INDEX>2</EIDA_DOC_INDEX></xml>";
			Mockito.when(mockAPCallCreateXML.APSelect(Mockito.anyString())).thenReturn(expectedXmlResponse);
			when(mockXMLParser.getValueOf("MainCode")).thenReturn("0");
	        when(mockXMLParser.getValueOf("TotalRetrieved")).thenReturn("1");
	        when(mockXMLParser.getValueOf("NATIONALITY")).thenReturn("AE");
	        when(mockXMLParser.getValueOf("EIDA_DOC_NAME")).thenReturn("EIDA");
	        when(mockXMLParser.getValueOf("EIDA_DOC_INDEX")).thenReturn("2");
	        SuppAddDocument suppAddDocument = new SuppAddDocument(hm,s1,s2,s3,b,entity);
			String outputXml = suppAddDocument.fetchAddDocData();
			assertEquals(expectedXmlResponse, outputXml);
			System.out.println("pass");
			} catch (Exception e) {
			System.out.println("Fail");
			assertTrue(false);

		}
	}




}
