package se.skltp.patch.cxfrtbindingssoap.mule331;

import org.junit.Test;

import se.skltp.patch.cxfrtbindingssoap.mule.common.MuleCxfSoapActionBaseIntegrationTest;

public class Mule331CxfSoapActionIntegrationTest extends
		MuleCxfSoapActionBaseIntegrationTest {

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
