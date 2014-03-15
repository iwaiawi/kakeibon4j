package kakeibon4j.internal.scraping.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import kakeibon4j.KakeibonException;
import kakeibon4j.internal.scraping.xml.parser.NormalParser;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class NormalXmlImpl extends ResultXmlBase implements NormalXml {
	
	private static final String SCHEMA_FILE_NAME = "/NormalXml.xsd";
	
	public NormalXmlImpl(final InputStream schema, final String rawXml) throws IOException, KakeibonException {
		super(rawXml);
		validateSchema(schema, rawXml);
	}
	
	public NormalXmlImpl(final String rawXml) throws IOException, KakeibonException {
		this(getSchemaInputStream(), rawXml);
	}
	
	protected void validateSchema(final InputStream schema, final String rawXml) throws IOException, KakeibonException {
		if (schema == null) {
			throw new IllegalArgumentException("Argument schema must not be null.");
		}
		if (rawXml == null) {
			throw new IllegalArgumentException("Argument rawXml must not be null.");
		}
		
		final SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		
		try {
			final Validator validator = sf.newSchema(new StreamSource(schema)).newValidator();
			validator.validate(new StreamSource(new StringReader(rawXml))); // ValidationError => SAXException
		} catch (final SAXException e) {
			try {
				final ErrorXml error = new ErrorXmlImpl(rawXml);
				
				throw new KakeibonException(
						"Kakeibon responsed error xml. ("
								+ "responseCode:" + error.getResponseCode()
								+ ", exceptionCode:" + error.getExceptionCode()
								+ ", exceptionMessage:\"" + error.getExceptionMessage() + "\""
								+ ")",
						e);
			} catch (final SAXException ignore) {
				if (e instanceof SAXParseException) {
					final SAXParseException spe = (SAXParseException)e;
					throw new KakeibonException(
							"Xml schema parse error. " + spe.getMessage() + " ("
									+ "publicId:" + spe.getPublicId()
									+ ", systemId:" + spe.getSystemId()
									+ ", line:" + spe.getLineNumber()
									+ ", column:" + spe.getColumnNumber()
									+ ")"
									+ " rawXml:" + rawXml,
							spe);
				}
				throw new KakeibonException(e);
			}
		}
	}
	
	@Override
	public String getAauuid() {
		return new NormalParser().parseAauuid(toJsoup());
	}
	
	private static InputStream getSchemaInputStream() {
		return NormalXmlImpl.class.getResourceAsStream(SCHEMA_FILE_NAME);
	}
}