package com.newgen.cbg.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import com.newgen.cbg.callhandler.CallEntity;
import com.newgen.cbg.calls.SuppEidaDedupe;
import com.newgen.cbg.logger.DSCOPDBLogMe;
import com.newgen.cbg.logger.DSCOPLogMe;
import com.newgen.cbg.utils.APCallCreateXML;

public class SuppNameSplit {
	String s1 = "";
	String s2 = "10";
	String s3 = "10";
	Boolean b = true;
	HashMap hm = Mockito.mock(HashMap.class);

	CallEntity entity = Mockito.mock(CallEntity.class);
	private APCallCreateXML mockAPCallCreateXML = Mockito.mock(APCallCreateXML.class);
	@SuppressWarnings("static-access")
	@Test
	public void testNameSplit_Success() throws Exception {
		try {
			PowerMockito.mockStatic(DSCOPLogMe.class);
			PowerMockito.mockStatic(APCallCreateXML.class);
			PowerMockito.mockStatic(DSCOPDBLogMe.class);
			String expectedXmlResponse = "<xml><MainCode>0</MainCode><TotalRetrieved>1</TotalRetrieved><EIDA_NUMBER>123456</EIDA_NUMBER></xml>";
			Mockito.when(mockAPCallCreateXML.APSelect(Mockito.anyString())).thenReturn(expectedXmlResponse);
			SuppEidaDedupe suppEidaDedupe = new SuppEidaDedupe(hm,s1,s2,s3,b,entity);
			String outputXml = suppEidaDedupe.fetchEidaNo();
			assertEquals(expectedXmlResponse, outputXml);
			System.out.println("pass");
			} catch (Exception e) {
			System.out.println("Fail");
			assertTrue(false);

		}
	}


}
