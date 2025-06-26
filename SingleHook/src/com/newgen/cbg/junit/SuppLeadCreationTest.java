package com.newgen.cbg.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import com.newgen.cbg.callhandler.CallEntity;
import com.newgen.cbg.calls.SuppLeadCreation;
import com.newgen.cbg.logger.DSCOPDBLogMe;
import com.newgen.cbg.logger.DSCOPLogMe;
import com.newgen.cbg.utils.APCallCreateXML;
@RunWith(PowerMockRunner.class)
@PrepareForTest({com.newgen.cbg.utils.APCallCreateXML.class,com.newgen.cbg.logger.DSCOPLogMe.class,com.newgen.cbg.logger.DSCOPDBLogMe.class})
@PowerMockIgnore({ "javax.net.ssl.*", "javax.crypto.*", "javax.management.*", "jdk.internal.reflect.*" })

public class SuppLeadCreationTest {
	String s1 = "";
	String s2 = "10";
	String s3 = "10";
	Boolean b = true;
	HashMap hm = Mockito.mock(HashMap.class);

	CallEntity entity = Mockito.mock(CallEntity.class);
	private APCallCreateXML mockAPCallCreateXML = Mockito.mock(APCallCreateXML.class);
	@Test
	public void testFetchLeadCreation_Success() throws Exception {
		try {
			PowerMockito.mockStatic(DSCOPLogMe.class);
			PowerMockito.mockStatic(APCallCreateXML.class);
			PowerMockito.mockStatic(DSCOPDBLogMe.class);
			String expectedXmlResponse = "<xml><MainCode>0</MainCode><TotalRetrieved>1</TotalRetrieved><SUPP_CARDHOLDER_FULL_NAME>John Doe</SUPP_CARDHOLDER_FULL_NAME><CUSTOMER_EMAIL>john.doe@example.com</CUSTOMER_EMAIL><CUSTOMER_MOBILE_NO>+1234567890</CUSTOMER_MOBILE_NO><PASSPORT_NUMBER>123456789</PASSPORT_NUMBER><JOURNEY_TYPE>Business</JOURNEY_TYPE><CUSTOMER_ID>12345</CUSTOMER_ID><NATIONALITY>US</NATIONALITY></xml>";
			Mockito.when(APCallCreateXML.APSelect(Mockito.anyString())).thenReturn(expectedXmlResponse);
			SuppLeadCreation suppLeadCreation = new SuppLeadCreation(hm,s1,s2,s3,b,entity);
			String outputXml = suppLeadCreation.fetchLeadCreationData();
			assertEquals(expectedXmlResponse, outputXml);
			System.out.println("pass");
			} catch (Exception e) {
			System.out.println("Fail");
			assertTrue(false);

		}
	}




}
