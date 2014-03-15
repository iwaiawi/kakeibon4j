package kakeibon4j.internal.scraping.xml;

public interface ErrorXml extends ResultXml {
	
	public abstract int getExceptionCode();
	
	public abstract String getExceptionMessage();
}