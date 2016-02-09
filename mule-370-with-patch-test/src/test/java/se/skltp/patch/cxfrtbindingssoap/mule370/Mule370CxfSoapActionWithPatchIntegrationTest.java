package se.skltp.patch.cxfrtbindingssoap.mule370;

import org.junit.BeforeClass;
import org.junit.Test;

import se.skltp.patch.cxfrtbindingssoap.mule.common.MuleCxfSoapActionBaseIntegrationTest;
import se.skltp.patch.cxfrtbindingssoap.mule.common.PatchClassLoaderHelper;

public class Mule370CxfSoapActionWithPatchIntegrationTest extends
		MuleCxfSoapActionBaseIntegrationTest {

	@BeforeClass
	public static void setupBeforeClass() {
		PatchClassLoaderHelper
				.loadPatchedClass(Mule370CxfSoapActionWithPatchIntegrationTest.class);
	}

	@Test
	@Override
	public void testSoapAction_sameAsInWsdl() throws Exception {
		setSoapAction(SOAP_ACTION_SAME_AS_IN_WSDL);
		verifyOkResponse(callService());
	}

	@Test
	@Override
	public void testSoapAction_empty() throws Exception {
		setSoapAction(SOAP_ACTION_EMPTY);
		verifyOkResponse(callService());
	}

	@Test
	@Override
	public void testSoapAction_completelyWrong() throws Exception {
		setSoapAction(SOAP_ACTION_COMPLETELY_WRONG);
		verifyOkResponse(callService());
	}
}
