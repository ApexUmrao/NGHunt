package test.java.com.newgen.junit;

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


@RunWith(PowerMockRunner.class)
@PrepareForTest({})
@PowerMockIgnore({ "javax.net.ssl.*", "javax.crypto.*", "javax.management.*", "jdk.internal.reflect.*" })
public class Test1 {
	 
    String sOption = "WFUploadWorkItem";
    String sEngineName = "Engine1";
    String sSessionID = "Session123";
    String sProcessDefId = "Process1";
    String sValidationRequired = "";
    String sAttributes = "Attribute1";
    String sTemp = "Document1";
    String sIniActName = "Activity1";
    String sIniActId = "ActId1";
    String outputXml ="";
//	private Commons mockAPCallCreateXML = Mockito.mock(Commons.class);
	@Test
	public void testFetchCustomerData_Success() throws Exception {
		try {

		//	PowerMockito.mockStatic(Commons.class);
		//	Commons commons = new Commons();
			String expectedXml = "<?xml version=\"1.0\"?><WFUploadWorkItem_Input><Option>WFUploadWorkItem</Option><EngineName>Engine</EngineName><SessionId>Session123</SessionId><ValidationRequired></ValidationRequired><ProcessDefId>Process1</ProcessDefId><DataDefName></DataDefName><Documents>Document1</Documents><InitiateFromActivityId>ActId1</InitiateFromActivityId><InitiateFromActivityName>Activity1</InitiateFromActivityName><Attributes>Attribute1</Attributes></WFUploadWorkItem_Input>";
		//	Mockito.when(mockAPCallCreateXML.WFUploadWorkItem(sOption, sEngineName, sSessionID, sProcessDefId,
		//			sValidationRequired, sAttributes, sTemp, sIniActName, sIniActId)).thenReturn(expectedXml);
		//	 outputXml = commons.WFUploadWorkItem(sOption, sEngineName, sSessionID, sProcessDefId,
		//			sValidationRequired, sAttributes, sTemp, sIniActName, sIniActId);
			//assertEquals(expectedXml, outputXml);
			if (expectedXml.equals(outputXml)) {
				System.out.println("Pass");
			} else {
				System.out.println("Fail");
			}
		} catch (Exception e) {
			System.out.println("Fail");
			assertTrue(false);
		}
	}
}
