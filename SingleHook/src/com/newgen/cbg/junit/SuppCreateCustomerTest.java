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
import com.newgen.cbg.calls.SuppCreateCustomer;
import com.newgen.cbg.logger.DSCOPDBLogMe;
import com.newgen.cbg.logger.DSCOPLogMe;
import com.newgen.cbg.utils.APCallCreateXML;
@RunWith(PowerMockRunner.class)
@PrepareForTest({com.newgen.cbg.utils.APCallCreateXML.class,com.newgen.cbg.logger.DSCOPLogMe.class,com.newgen.cbg.logger.DSCOPDBLogMe.class})
@PowerMockIgnore({ "javax.net.ssl.*", "javax.crypto.*", "javax.management.*", "jdk.internal.reflect.*" })

public class SuppCreateCustomerTest {
	String s1 = "";
	String s2 = "10";
	String s3 = "10";
	Boolean b = true;
	HashMap hm = Mockito.mock(HashMap.class);

	CallEntity entity = Mockito.mock(CallEntity.class);
	private APCallCreateXML mockAPCallCreateXML = Mockito.mock(APCallCreateXML.class);
	@Test
	public void testFetchCustomerData_Success() throws Exception {
		try {
			PowerMockito.mockStatic(DSCOPLogMe.class);
			PowerMockito.mockStatic(APCallCreateXML.class);
			PowerMockito.mockStatic(DSCOPDBLogMe.class);
			String expectedXmlResponse = "<xml><MainCode>0</MainCode><TotalRetrieved>1</TotalRetrieved><CUSTOMER_ID>123456</CUSTOMER_ID></xml>";
			Mockito.when(mockAPCallCreateXML.APSelect(Mockito.anyString())).thenReturn(expectedXmlResponse);
			SuppCreateCustomer suppCreateCustomer = new SuppCreateCustomer(hm,s1,s2,s3,b,entity);
			String outputXml = suppCreateCustomer.fetchCreateCustomerData();
			assertEquals(expectedXmlResponse, outputXml);
			System.out.println("pass");
			} catch (Exception e) {
			System.out.println("Fail");
			assertTrue(false);

		}
	}
}
