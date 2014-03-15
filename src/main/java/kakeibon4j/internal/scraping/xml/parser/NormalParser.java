package kakeibon4j.internal.scraping.xml.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class NormalParser extends ResultParser {
	
	public String parseAauuid(final Document xmlDoc) {
		final Element aauuid = xmlDoc.select("AAUID").first();
		if (aauuid == null) {
			throw new IllegalArgumentException("Argument " + xmlDoc + " must contains <AAUID>.");
		}
		return parseString(aauuid.ownText());
	}
}
