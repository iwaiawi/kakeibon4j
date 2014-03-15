package kakeibon4j.internal.scraping.xml.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ErrorParser extends Parser {
	
	public int parseExceptionCode(final Document xmlDoc) {
		final Element code = xmlDoc.select("EXCEPTION_CD").first();
		if (code == null) {
			throw new IllegalArgumentException("Argument " + xmlDoc + " must contains <EXCEPTION_CD>.");
		}
		return parseIntValue(code.ownText());
	}
	
	public String parseExceptionMessage(final Document xmlDoc) {
		final Element exceptionMessage = xmlDoc.select("EXCEPTION_MSG").first();
		if (exceptionMessage == null) {
			throw new IllegalArgumentException("Argument " + xmlDoc + " must contains <EXCEPTION_MSG>.");
		}
		return parseString(exceptionMessage.ownText());
	}
}
