package kakeibon4j.internal.scraping.xml;

import java.io.IOException;
import java.io.InputStream;

import kakeibon4j.KakeibonException;

public class DeleteBookingXmlImpl extends BookingXmlImpl implements DeleteBookingXml {
	
	private static final String SCHEMA_FILE_NAME = "/DeleteBookingXml.xsd";
	
	public DeleteBookingXmlImpl(final InputStream schema, final String rawXml) throws IOException, KakeibonException {
		super(schema, rawXml);
	}
	
	public DeleteBookingXmlImpl(final String rawXml) throws IOException, KakeibonException {
		super(getSchemaInputStream(), rawXml);
	}
	
	private static InputStream getSchemaInputStream() {
		return BookingXmlImpl.class.getResourceAsStream(SCHEMA_FILE_NAME);
	}
}
