package se.skltp.patch.cxfrtbindingssoap.mule.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;

import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class MuleCxfSoapActionBaseIntegrationTest extends FunctionalTestCase {
	protected static final String REQUEST_FILE = "ping-soap-request.xml";
	protected static final String RESPONSE_TEST_STRING = "Ping response from test producer";
	protected static final String URL_TEST_PRODUCER = "http://localhost:8081/ping/1/rivtabp20";
	// soapAction should be quoted for max interop
	// AND match namespace:operation in WSDL
	// NOTE: this soapAction is the configured one in the WSDL - but does NOT
	// adhere to the rules above!
	protected static final String SOAP_ACTION_SAME_AS_IN_WSDL = "\"urn:riv:test:PingResponder:1:ping\"";
	protected static final String SOAP_ACTION_EMPTY = "";
	protected static final String SOAP_ACTION_COMPLETELY_WRONG = "\"urn:this:is:not:a:valid:soapAction\"";

	protected MuleClient muleClient;
	protected HashMap<String, String> requestMsgProps;

	@Override
	protected String getConfigResources() {
		return "tp2-service-mule-descriptor.xml";
	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();
		muleClient = new MuleClient(muleContext);
		requestMsgProps = new HashMap();
		requestMsgProps.put("Content-Type", "text/xml");
		requestMsgProps.put("http.method", "POST");
	}

	@Ignore("run in subclass only")
	@Test
	public void testSoapAction_sameAsInWsdl() throws Exception {
		setSoapAction(SOAP_ACTION_SAME_AS_IN_WSDL);
		verifyOkResponse(callService());
	}

	@Ignore("run in subclass only")
	@Test
	public void testSoapAction_empty() throws Exception {
		setSoapAction(SOAP_ACTION_EMPTY);
		verifyOkResponse(callService());
	}

	@Ignore("run in subclass only")
	@Test
	public void testSoapAction_completelyWrong() throws Exception {
		setSoapAction(SOAP_ACTION_COMPLETELY_WRONG);
		verifyOkResponse(callService());
	}

	protected void setSoapAction(String soapAction) {
		requestMsgProps.put("SOAPAction", soapAction);
	}

	protected MuleMessage callService() throws Exception {
		int timeout = 3000;
		String payload = FileHelper.getFileFromClasspath(REQUEST_FILE);
		MuleMessage responseMsg = muleClient.send(URL_TEST_PRODUCER, payload,
				requestMsgProps, timeout);
		return responseMsg;
	}

	protected void verifyOkResponse(MuleMessage responseMsg) throws IOException {
		assertEquals("200", responseMsg.getInboundProperty("http.status"));

		String response = getPayloadAsString(responseMsg);

		assertTrue(response.contains(RESPONSE_TEST_STRING));
	}

	protected void verifyHttp500Response(MuleMessage responseMsg)
			throws IOException {
		assertEquals("500", responseMsg.getInboundProperty("http.status"));
	}

	protected String getPayloadAsString(MuleMessage message) throws IOException {
		StringWriter writer = new StringWriter();
		IOUtils.copy((InputStream) message.getPayload(), writer, "UTF-8");
		return writer.toString();
	}
}
