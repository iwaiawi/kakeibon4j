package kakeibon4j.internal.scraping.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kakeibon4j.internal.scraping.conf.ScrapingConfiguration;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public abstract class ScrapingClientBase {
	
	private final ScrapingConfiguration conf;
	
	public ScrapingClientBase(final ScrapingConfiguration conf) {
		this.conf = conf;
	}
	
	protected ScrapingConfiguration getConfiguration() {
		return conf;
	}
	
	protected HttpClient getDefaultHttpClient() {
		return getDefaultHttpClient(null);
	}
	
	protected HttpClient getDefaultHttpClient(final CookieStore defaultCookieStore) {
		final HttpClientBuilder builder = HttpClientBuilder.create()
				.setDefaultRequestConfig(getDefaultRequestConfig())
				.setDefaultHeaders(getDefaultHeaders())
				.setRedirectStrategy(getDefaultRedirectStrategy());
		
		// builder.setProxy(new HttpHost("localhost", 8080));
		
		if (defaultCookieStore != null) {
			builder.setDefaultCookieStore(defaultCookieStore);
		}
		
		return builder.build();
	}
	
	protected RequestConfig getDefaultRequestConfig() {
		return RequestConfig.custom()
				.setConnectTimeout(conf.getConnectTimeout())
				.setSocketTimeout(conf.getSocketTimeout())
				.setRedirectsEnabled(true)
				.setRelativeRedirectsAllowed(true)
				.build();
	}
	
	protected List<Header> getDefaultHeaders() {
		final List<Header> headers = new ArrayList<Header>();
		headers.add(new BasicHeader("Accept-Language", "ja, en;q=0.8"));
		headers.add(new BasicHeader("Accept-Charset", "utf-8"));
		headers.add(new BasicHeader("User-Agent", conf.getUserAgent()));
		// headers.add(new BasicHeader("x-wap-profile", X_WAP_PROFILE));
		return headers;
	}
	
	protected RedirectStrategy getDefaultRedirectStrategy() {
		return getForceRedirectStrategy();
	}
	
	private RedirectStrategy getForceRedirectStrategy() {
		return new DefaultRedirectStrategy() {
			@Override
			public boolean isRedirected(final HttpRequest request, final HttpResponse response, final HttpContext context) throws ProtocolException {
				final boolean isRedirected = super.isRedirected(request, response, context);
				if (!isRedirected) {
					final int statusCode = response.getStatusLine().getStatusCode();
					if (statusCode == 301 || statusCode == 302) {
						return true;
					}
				}
				return isRedirected;
			}
		};
	}
	
	protected HttpResponse executeRequest(final HttpUriRequest req, final HttpClientContext context) throws IOException, ClientProtocolException {
		final HttpClient client;
		if (context != null) {
			client = getDefaultHttpClient(context.getCookieStore());
		} else {
			client = getDefaultHttpClient();
		}
		return executeRequest(client, req, context);
	}
	
	protected HttpResponse executeRequest(final HttpClient httpClient, final HttpUriRequest req, final HttpClientContext context) throws IOException, ClientProtocolException {
		if (httpClient == null) {
			throw new IllegalArgumentException("HttpClient is null. HttpClient is required.");
		}
		
		if (context != null) {
			return httpClient.execute(req, context);
		} else {
			return httpClient.execute(req);
		}
	}
	
	protected String simpleExecuteRequest(final HttpUriRequest req, final HttpClientContext context) throws IOException, ClientProtocolException {
		final HttpClient client;
		if (context != null) {
			client = getDefaultHttpClient(context.getCookieStore());
		} else {
			client = getDefaultHttpClient();
		}
		return simpleExecuteRequest(client, req, context);
	}
	
	protected String simpleExecuteRequest(final HttpClient httpClient, final HttpUriRequest req, final HttpClientContext context) throws IOException, ClientProtocolException {
		if (httpClient == null) {
			throw new IllegalArgumentException("HttpClient is null. HttpClient is required.");
		}
		
		final HttpResponse res;
		if (context != null) {
			res = httpClient.execute(req, context);
		} else {
			res = httpClient.execute(req);
		}
		
		return getResponseBody(res);
	}
	
	protected String getResponseBody(final HttpResponse res) throws IOException {
		final int statusCode = res.getStatusLine().getStatusCode();
		if (statusCode != 200) {
			throw new IllegalStateException("This HTTP status code is " + statusCode + ". HTTP status code must be 200.");
		}
		
		final String responseBody = EntityUtils.toString(res.getEntity(), "UTF-8");
		
		if (conf.isDebugEnabled()) {
			System.out.println(responseBody);
		}
		
		return responseBody;
	}
	
	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof ScrapingClientBase)) {
			return false;
		}
		
		final ScrapingClientBase that = (ScrapingClientBase)o;
		return conf.equals(that.getConfiguration());
	}
}
