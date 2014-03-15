package kakeibon4j.internal.scraping.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Date;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import kakeibon4j.internal.scraping.xml.parser.ResultParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xml.sax.SAXException;

public abstract class ResultXmlBase implements ResultXml {
	
	protected final String rawXml;
	
	public ResultXmlBase(final InputStream schema, final String rawXml) throws SAXException, IOException {
		this(rawXml);
		validateSchema(schema, rawXml);
	}
	
	public ResultXmlBase(final String rawXml) {
		super();
		
		if (rawXml == null) {
			throw new IllegalArgumentException("Argument rawXml must not be null.");
		}
		this.rawXml = rawXml;
	}
	
	@Override
	public Date getResponseTime() {
		return new ResultParser().parseResponseTime(toJsoup());
	}
	
	@Override
	public int getResponseCode() {
		return new ResultParser().parseResponseCode(toJsoup());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rawXml == null) ? 0 : rawXml.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final NormalXmlImpl other = (NormalXmlImpl)obj;
		if (rawXml == null) {
			if (other.rawXml != null) {
				return false;
			}
		} else if (!rawXml.equals(other.rawXml)) {
			return false;
		}
		return true;
	}
	
	@Override
	public Document toJsoup() {
		return Jsoup.parse(rawXml);
	}
	
	@Override
	public String toString() {
		return rawXml;
	}
	
	private void validateSchema(final InputStream schema, final String rawXml) throws SAXException, IOException {
		if (schema == null) {
			throw new IllegalArgumentException("Argument schema must not be null.");
		}
		if (rawXml == null) {
			throw new IllegalArgumentException("Argument rawXml must not be null.");
		}
		
		final SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		final Validator validator = sf.newSchema(new StreamSource(schema)).newValidator();
		
		validator.validate(new StreamSource(new StringReader(rawXml))); // ValidationError => SAXException
	}
}