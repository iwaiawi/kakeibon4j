package kakeibon4j.internal.scraping.xml.parser;

import java.util.Date;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ResultParser extends Parser {
	
	public int parseResponseCode(final Document xmlDoc) {
		final Element responseCode = xmlDoc.select("RESPONSE_CD").first();
		if (responseCode == null) {
			throw new IllegalArgumentException("Argument " + xmlDoc + " must contains <RESPONSE_CD>.");
		}
		return parseIntValue(responseCode.ownText());
	}
	
	public Date parseResponseTime(final Document xmlDoc) {
		final Element responseTime = xmlDoc.select("RESPONSE_TIME").first();
		if (responseTime == null) {
			throw new IllegalArgumentException("Argument " + xmlDoc + " must contains <RESPONSE_TIME>.");
		}
		return parseDateTime(responseTime.ownText());
	}
	
}