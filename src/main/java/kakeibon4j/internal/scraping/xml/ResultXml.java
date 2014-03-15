package kakeibon4j.internal.scraping.xml;

import java.util.Date;

import org.jsoup.nodes.Document;

public interface ResultXml {
	
	public abstract Date getResponseTime();
	
	public abstract int getResponseCode();
	
	public abstract Document toJsoup();
}