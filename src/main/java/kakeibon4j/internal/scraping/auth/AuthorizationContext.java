package kakeibon4j.internal.scraping.auth;

import org.apache.http.client.protocol.HttpClientContext;

public interface AuthorizationContext {
	
	public abstract String getAauuId();
	
	public abstract HttpClientContext getHttpClientContext();
	
	public abstract boolean isExpired();
	
	public abstract AuthorizationContext updateAauuid(final String aauuid);
	
}