package se.skltp.patch.cxfrtbindingssoap.mule.common;

import javax.xml.stream.XMLStreamReader;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.util.XmlUtil;

public class PingProducer extends AbstractMessageTransformer {
	private static final Logger log = LoggerFactory
			.getLogger(PingProducer.class);
	private static final String RESPONSE_FILE = "ping-soap-response.xml";
	private static final String REQUEST_TEST_STRING = "Ping request to test producer";

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding)
			throws TransformerException {

		if (!(message.getPayload() instanceof XMLStreamReader)) {
			throw new RuntimeException("unexpected input type: "
					+ message.getPayload().getClass());
		}

		String request = XmlUtil.convertXMLStreamReaderToString(
				(XMLStreamReader) message.getPayload(), "UTF-8");

		if (!request.contains(REQUEST_TEST_STRING)) {
			throw new RuntimeException(
					"request does not contain expected string, request: "
							+ request);
		}

		String response = FileHelper.getFileFromClasspath(RESPONSE_FILE);
		log.debug("returning response: {}", response);
		return response;
	}

}
