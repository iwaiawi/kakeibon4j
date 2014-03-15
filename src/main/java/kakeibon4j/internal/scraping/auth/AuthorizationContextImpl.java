package kakeibon4j.internal.scraping.auth;

import java.util.Date;

import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;

public class AuthorizationContextImpl implements java.io.Serializable, AuthorizationContext {
	private static final long EXPIRE_MILLISECONDS = 1000L * 60 * 10; // 10 minutes
	
	private final String aauuId;
	private final HttpClientContext httpClientContext;
	private final Date expire;
	
	public AuthorizationContextImpl(final HttpClientContext httpClientContext, final String aauuid) {
		if (httpClientContext == null) {
			throw new IllegalArgumentException("Argument httpClientContext must not be null.");
		}
		if (aauuid == null || "".equals(aauuid)) {
			throw new IllegalArgumentException("Argument aauuid must not be both null and empty string.");
		}
		this.httpClientContext = cloneHttpClientContext(httpClientContext);
		this.aauuId = aauuid;
		this.expire = new Date(System.currentTimeMillis() + EXPIRE_MILLISECONDS);
	}
	
	@Override
	public String getAauuId() {
		return aauuId;
	}
	
	@Override
	public boolean isExpired() {
		return System.currentTimeMillis() > expire.getTime();
	}
	
	@Override
	public HttpClientContext getHttpClientContext() {
		return cloneHttpClientContext(httpClientContext);
	}
	
	@Override
	public AuthorizationContext updateAauuid(final String aauuid) {
		if (this.aauuId.equals(aauuid)) {
			return this;
		}
		return new AuthorizationContextImpl(httpClientContext, aauuid);
	}
	
	private HttpClientContext cloneHttpClientContext(final HttpClientContext httpClientContext) {
		// Cookieのみコピーする
		final HttpClientContext clone = HttpClientContext.create();
		clone.setCookieStore(new BasicCookieStore());
		for (final Cookie cookie : httpClientContext.getCookieStore().getCookies()) {
			clone.getCookieStore().addCookie(cookie);
		}
		return clone;
	}
}