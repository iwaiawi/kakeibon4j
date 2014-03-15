package kakeibon4j.internal.scraping.xml;

import java.io.IOException;
import java.io.InputStream;

import kakeibon4j.internal.scraping.xml.parser.ErrorParser;

import org.xml.sax.SAXException;

class ErrorXmlImpl extends ResultXmlBase implements ErrorXml {
	private static final String SCHEMA_FILE_NAME = "/ErrorXml.xsd";
	
	public ErrorXmlImpl(final String rawXml) throws SAXException, IOException {
		super(getSchemaInputStream(), rawXml);
	}
	
	@Override
	public int getExceptionCode() {
		return new ErrorParser().parseExceptionCode(toJsoup());
	}
	
	@Override
	public String getExceptionMessage() {
		return new ErrorParser().parseExceptionMessage(toJsoup());
	}
	
	private static InputStream getSchemaInputStream() {
		return ErrorXmlImpl.class.getResourceAsStream(SCHEMA_FILE_NAME);
	}
}