package kakeibon4j.internal.scraping.auth;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kakeibon4j.KakeibonException;
import kakeibon4j.internal.scraping.client.ScrapingClientBase;
import kakeibon4j.internal.scraping.conf.ScrapingConfiguration;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

public class AuthorizationClientImpl extends ScrapingClientBase implements AuthorizationClient {
	
	private static final long serialVersionUID = -5861104407848415060L;
	volatile private AuthorizationContext context;
	
	public AuthorizationClientImpl(final ScrapingConfiguration conf) {
		super(conf);
	}
	
	@Override
	public AuthorizationContext getContext() throws KakeibonException {
		if (isContextEnabled()) {
			return context;
		}
		
		login();
		return context;
	}
	
	@Override
	public AuthorizationContext updateContext(final AuthorizationContext context) {
		this.context = context;
		return context;
	}
	
	private void clearContext() {
		updateContext(null);
	}
	
	private boolean isContextEnabled() {
		return context != null && !context.isExpired();
	}
	
	@Override
	synchronized public AuthorizationContext login() throws KakeibonException {
		final HttpClientContext httpContext = HttpClientContext.create();
		final HttpClient httpClient = getDefaultHttpClient();
		
		try {
			/*
			 * 1. ログイン認証中画面(1)
			 */
			final HttpGet req1 = new HttpGet("https://app.kakeibo.ocn.ne.jp/ocn_p/aas?processCode=01Start&link_id=out_kakeibo_login");
			final HttpResponse res1 = httpClient.execute(req1, httpContext);
			
			/*
			 * 2. ログイン認証中画面(2)※リダイレクトあり
			 */
			final HttpPost req2 = createLoginReq2(req1, res1);
			final HttpResponse res2 = httpClient.execute(req2, httpContext);
			
			/*
			 * 3. ログイン画面※リダイレクトあり
			 */
			final HttpPost req3 = createLoginReq3(req2, res2);
			final HttpResponse res3 = httpClient.execute(req3, httpContext);
			
			/*
			 * 4. JavaScriptリダイレクト用画面(1)※リダイレクトあり
			 */
			final HttpPost req4 = createLoginReq4(req3, res3);
			final HttpResponse res4 = httpClient.execute(req4, httpContext);
			
			/*
			 * 5. 合言葉入力用画面
			 */
			final HttpPost req5 = createLoginReq5(req4, res4);
			final HttpResponse res5 = httpClient.execute(req5, httpContext);
			
			/*
			 * 6. 利用端末名登録用画面
			 */
			final HttpPost req6 = createLoginReq6(req5, res5);
			final HttpResponse res6 = httpClient.execute(req6, httpContext);
			
			/*
			 * 7. JavaScriptリダイレクト用画面(2)※リダイレクトあり
			 */
			final HttpPost req7 = createLoginReq7(req6, res6);
			final HttpResponse res7 = httpClient.execute(req7, httpContext);
			
			/*
			 * 8. メイン画面
			 */
			final HttpPost req8 = createLoginReq8(req7, res7);
			final HttpResponse res8 = httpClient.execute(req8, httpContext);
			
			return updateContext(createContext(req8, res8, httpContext));
		} catch (final ParseException e) {
			throw new KakeibonException("Login is failed.\n" + e.getMessage(), e);
		} catch (final IOException e) {
			throw new KakeibonException("Login is failed.\n" + e.getMessage(), e);
		} catch (final RuntimeException e) {
			throw new KakeibonException("Login is failed.\n" + e.getMessage(), e);
		}
	}
	
	@Override
	synchronized public void logout() throws KakeibonException {
		if (!isContextEnabled()) {
			return;
		}
		
		final HttpClientContext httpContext = getContext().getHttpClientContext();
		final HttpClient httpClient = getDefaultHttpClient();
		
		try {
			/*
			 * 1. ログアウト中画面
			 */
			final HttpPost req1 = new HttpPost("https://app.kakeibo.ocn.ne.jp/ocn/aas");
			req1.setEntity(new UrlEncodedFormEntity(
					Arrays.asList(
							new BasicNameValuePair("processCode", "21Logout"),
							new BasicNameValuePair("aauuid", getContext().getAauuId()),
							new BasicNameValuePair("accountTargets", ""),
							new BasicNameValuePair("cpId", ""),
							new BasicNameValuePair("accessCd", "")
							)
					));
			
			final HttpResponse res1 = httpClient.execute(req1, httpContext);
			
			/*
			 * 2. ログアウト完了画面※リダイレクトあり
			 */
			final HttpPost req2 = createLogoutReq2(req1, res1);
			simpleExecuteRequest(httpClient, req2, httpContext);
			clearContext();
		} catch (final ParseException e) {
			throw new KakeibonException("Logout is failed.\n" + e.getMessage(), e);
		} catch (final IOException e) {
			throw new KakeibonException("Logout is failed.\n" + e.getMessage(), e);
		} catch (final RuntimeException e) {
			throw new KakeibonException("Logout is failed.\n" + e.getMessage(), e);
		}
	}
	
	private HttpPost createLoginReq2(final HttpGet req1, final HttpResponse res1) throws ParseException, IOException {
		final String responseBody = getResponseBody(res1);
		
		final String nextUrl = Jsoup.parse(responseBody).select("form[name=acc]").attr("action");
		if ("".equals(nextUrl)) {
			throw new IllegalStateException("Res1 must contains <form name='acc' action='xxxx'>. Res1=" + responseBody + ".");
		}
		return new HttpPost(nextUrl);
	}
	
	private HttpPost createLoginReq3(final HttpPost req2, final HttpResponse res2) throws ParseException, IOException {
		final String responseBody = getResponseBody(res2);
		
		final String nextUrl = Jsoup.parse(responseBody).select("form[name=acc]").attr("action");
		if ("".equals(nextUrl)) {
			throw new IllegalStateException("Res2 must contains form[name=acc]. Res2=" + responseBody + ".");
		}
		return new HttpPost(nextUrl);
	}
	
	private HttpPost createLoginReq4(final HttpPost req3, final HttpResponse res3) throws ParseException, IOException {
		final String responseBody = getResponseBody(res3);
		
		final String nextUrl = Jsoup.parse(responseBody).select("form#AuthLoginDisplay").attr("action");
		if ("".equals(nextUrl)) {
			throw new IllegalStateException("Res3 must contains <form id='AuthLoginDisplay' action='xxxx'>. Res3=" + responseBody + ".");
		}
		
		final HttpPost req4 = new HttpPost(URIUtils.resolve(req3.getURI(), nextUrl));
		req4.setEntity(new UrlEncodedFormEntity(
				Arrays.asList(
						new BasicNameValuePair("comid", getConfiguration().getUserId()),
						new BasicNameValuePair("password", getConfiguration().getPassword()),
						new BasicNameValuePair("loginOption", "true"),
						new BasicNameValuePair("__checkbox__loginOption", "true"),
						new BasicNameValuePair("action:AuthLoginLogin.x", "0"),
						new BasicNameValuePair("action:AuthLoginLogin.y", "0")
						)
				));
		return req4;
	}
	
	private HttpPost createLoginReq5(final HttpPost req4, final HttpResponse res4) throws IOException {
		final String responseBody = getResponseBody(res4);
		
		final String nextUrl = Jsoup.parse(responseBody).select("form[name=acc]").attr("action");
		if ("".equals(nextUrl)) {
			throw new IllegalStateException("Res4 must contains <form name='acc' action='xxxx'>. Res4=" + responseBody + ".");
		}
		
		final NameValuePair aaValue = createNameValuePair(
				Jsoup.parse(responseBody).select("form[name=acc] input[type=hidden]").first(),
				"Res4 must contains <form name='acc'><input type='hidden'>. Res4=" + responseBody + "."
				);
		
		final HttpPost req5 = new HttpPost(nextUrl);
		req5.setEntity(new UrlEncodedFormEntity(
				Arrays.asList(aaValue)
				));
		return req5;
	}
	
	private HttpPost createLoginReq6(final HttpPost req5, final HttpResponse res5) throws IOException {
		final String responseBody = getResponseBody(res5);
		
		final String nextUrl = parseMainUrl(responseBody, "Res5 must contains JavaScript var \"MAIN_URL\". Res5=" + responseBody + ".", req5.getURI());
		final NameValuePair aauuid = createNameValuePair(
				Jsoup.parse(responseBody).select("form[name=F0] input[name=aauuid]").first(),
				"Res5 must contains <form name='F0'><input name='aauuid'>. Res5=" + responseBody + "."
				);
		final NameValuePair phraseAnswer1 = extractPhraseAnswer1FromRes5(responseBody);
		final NameValuePair phraseAnswer2 = extractPhraseAnswer2FromRes5(responseBody);
		
		final HttpPost req6 = new HttpPost(nextUrl);
		req6.setEntity(new UrlEncodedFormEntity(
				Arrays.asList(
						new BasicNameValuePair("processCode", "52KeyAuthSp"),
						aauuid,
						phraseAnswer1,
						phraseAnswer2
						),
				"SJIS"
				));
		
		// Content-Typeが自動的に"application/x-www-form-urlencoded; charset=Shift_JIS"となってしますため、強制的に上書き
		req6.setHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
		return req6;
	}
	
	private HttpPost createLoginReq7(final HttpPost req6, final HttpResponse res6) throws IOException {
		final String responseBody = getResponseBody(res6);
		
		final String nextUrl = parseMainUrl(responseBody, "Res6 must contains JavaScript var \"MAIN_URL\". Res6=" + responseBody + ".", req6.getURI());
		final NameValuePair aauuid = createNameValuePair(
				Jsoup.parse(responseBody).select("form[name=F0] input[name=aauuid]").first(),
				"Res6 must contains <form name='F0'><input name='aauuid'>. Res6=" + responseBody + "."
				);
		
		final HttpPost req7 = new HttpPost(nextUrl);
		req7.setEntity(new UrlEncodedFormEntity(
				Arrays.asList(
						new BasicNameValuePair("processCode", "52DeviceConfirmSp"),
						aauuid,
						new BasicNameValuePair("device_name", getRandomDeviceName())
						)
				));
		return req7;
	}
	
	private HttpPost createLoginReq8(final HttpPost req7, final HttpResponse res7) throws IOException {
		final String responseBody = getResponseBody(res7);
		
		final String nextUrl = parseMainUrl(responseBody, "Res7 must contains JavaScript var \"MAIN_URL\". Res7=" + responseBody + ".", req7.getURI());
		final NameValuePair aauuid = createNameValuePair(
				Jsoup.parse(responseBody).select("form[name=F1] input[name=aauuid]").first(),
				"Res7 must contains <form name='F1'><input name='aauuid'>. Res7=" + responseBody + "."
				);
		
		final HttpPost req8 = new HttpPost(nextUrl);
		req8.setEntity(new UrlEncodedFormEntity(
				Arrays.asList(
						new BasicNameValuePair("processCode", "13SpRedirect"),
						aauuid
						)
				));
		return req8;
	}
	
	private AuthorizationContext createContext(final HttpPost req8, final HttpResponse res8, final HttpClientContext httpContext) throws IOException {
		final String responseBody = getResponseBody(res8);
		
		final String aauuid = parseJsStringVar("aauuid_g", responseBody);
		if (aauuid == null) {
			throw new IllegalStateException("Res8 must contains JavaScript var \"aauuid_g\". Res8=" + responseBody + ".");
		}
		
		return new AuthorizationContextImpl(httpContext, aauuid);
	}
	
	private NameValuePair extractPhraseAnswer1FromRes5(final String responseBody) {
		final Element inputElement = Jsoup.parse(responseBody).select("form[name=F1] td.input_ai input.ai_input2").first();
		
		if (inputElement == null) {
			throw new IllegalStateException("Res5 must contains <form name='F1'><td class='input_ai'><input class='ai_input2'>. Res5=" + responseBody + ".");
		}
		
		return new BasicNameValuePair(inputElement.attr("name"), getConfiguration().getPhraseAnswer(inputElement.attr("name")));
	}
	
	private NameValuePair extractPhraseAnswer2FromRes5(final String responseBody) {
		final Element inputElement = Jsoup.parse(responseBody).select("form[name=F1] td.input_ai input.ai_input2").last();
		
		if (inputElement == null) {
			throw new IllegalStateException("Res5 must contains <form name='F1'><td class='input_ai'><input class='ai_input2'>. Res5=" + responseBody + ".");
		}
		
		return new BasicNameValuePair(inputElement.attr("name"), getConfiguration().getPhraseAnswer(inputElement.attr("name")));
	}
	
	private HttpPost createLogoutReq2(final HttpPost req1, final HttpResponse res1) throws ParseException, IOException {
		final String responseBody = getResponseBody(res1);
		
		final String nextUrl = parseMainUrl(responseBody, "Res1 must contains JavaScript var \"MAIN_URL\". Res1=" + responseBody + ".");
		return new HttpPost(nextUrl);
	}
	
	private String parseMainUrl(final String responseBody, final String errorMessage) {
		return parseMainUrl(responseBody, errorMessage, null);
	}
	
	private String parseMainUrl(final String responseBody, final String errorMessage, final URI base) {
		final String mainUrl = parseJsStringVar("MAIN_URL", responseBody);
		if (mainUrl == null) {
			throw new IllegalStateException(errorMessage);
		}
		
		if (base == null) {
			return mainUrl;
		} else {
			return URIUtils.resolve(base, mainUrl).toString();
		}
	}
	
	private String parseJsStringVar(final String varName, final String html) {
		final String STRING_VAR_PATTERN = "(var\\s+?)?" + varName + "\\s*=\\s*['\"](.+?)['\"]";
		final Matcher m = Pattern.compile(STRING_VAR_PATTERN).matcher(html);
		if (m.find()) {
			return m.group(2);
		} else {
			return null;
		}
	}
	
	protected NameValuePair createNameValuePair(final Element inputElement, final String errorMessage) {
		if (inputElement == null) {
			throw new IllegalStateException(errorMessage);
		}
		
		return new BasicNameValuePair(inputElement.attr("name"), inputElement.attr("value"));
	}
	
	private String getRandomDeviceName() {
		return "kakeibon4j_" + UUID.randomUUID().toString().substring(0, 4);
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "{" +
				"userId='" + getConfiguration().getUserId() + '\'' +
				", password='**********'\'" +
				'}';
	}
	
	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof AuthorizationClientImpl)) {
			return false;
		}
		return super.equals(o);
	}
	
}