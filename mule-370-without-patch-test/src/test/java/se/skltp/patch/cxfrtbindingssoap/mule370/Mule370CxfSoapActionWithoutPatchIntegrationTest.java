package se.skltp.patch.cxfrtbindingssoap.mule370;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.mule.api.MuleMessage;

import se.skltp.patch.cxfrtbindingssoap.mule.common.MuleCxfSoapActionBaseIntegrationTest;

public class Mule370CxfSoapActionWithoutPatchIntegrationTest extends
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
		// verifyOkResponse(callService());
		// NOT BACKWARDS COMPATIBLE BEHAVIOUR !
		MuleMessage response = callService();
		verifyHttp500Response(response);
		assertTrue(getPayloadAsString(response)
				.contains(
						"<faultstring>The given SOAPAction urn:this:is:not:a:valid:soapAction does not match an operation"));
	}
}
