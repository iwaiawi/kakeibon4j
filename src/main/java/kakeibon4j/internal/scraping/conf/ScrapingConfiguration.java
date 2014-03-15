package kakeibon4j.internal.scraping.conf;

import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kakeibon4j.internal.scraping.auth.AuthorizationConfiguration;

public abstract class ScrapingConfiguration implements AuthorizationConfiguration, java.io.Serializable {
	
	private static final long serialVersionUID = -6610497517837844232L;
	
	public static final String PHRASE_ANSWER_KEY1 = "phrase_answer1";
	public static final String PHRASE_ANSWER_KEY2 = "phrase_answer2";
	public static final String PHRASE_ANSWER_KEY3 = "phrase_answer3";
	
	private boolean debug;
	private String userId;
	private String password;
	private Map<String, String> phraseAnswerMap;
	private String userAgent;
	private int socketTimeout;
	private int connectTimeout;
	
	protected ScrapingConfiguration() {
		setDebug(false);
		setUserId(null);
		setPassword(null);
		setPhraseAnswer(new HashMap<String, String>());
		setUserAgent("Mozilla/5.0 (Linux; U; Android 2.3.3; en-us; GT-I9100 Build/GINGERBREAD) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
		setSocketTimeout(5000);
		setConnectTimeout(5000);
	}
	
	public final boolean isDebugEnabled() {
		return debug;
	}
	
	protected final void setDebug(final boolean debug) {
		this.debug = debug;
	}
	
	public final String getUserAgent() {
		return this.userAgent;
	}
	
	protected final void setUserAgent(final String userAgent) {
		this.userAgent = userAgent;
	}
	
	@Override
	public final String getUserId() {
		return userId;
	}
	
	protected final void setUserId(final String userId) {
		this.userId = userId;
	}
	
	@Override
	public final String getPassword() {
		return password;
	}
	
	protected final void setPassword(final String password) {
		this.password = password;
	}
	
	@Override
	public String getPhraseAnswer(final String key) {
		if (!phraseAnswerMap.containsKey(key)) {
			throw new IllegalArgumentException("Key \"" + key + "\" is not contained by phraseAnswerMap(" + phraseAnswerMap + ").");
		}
		
		return phraseAnswerMap.get(key);
	}
	
	protected final void setPhraseAnswer(final Map<String, String> map) {
		this.phraseAnswerMap = map;
	}
	
	public int getSocketTimeout() {
		return socketTimeout;
	}
	
	protected void setSocketTimeout(final int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}
	
	public int getConnectTimeout() {
		return connectTimeout;
	}
	
	protected void setConnectTimeout(final int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
	
	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		
		final ScrapingConfiguration that = (ScrapingConfiguration)o;
		
		if (debug != that.debug) {
			return false;
		}
		if (userId != null ? !userId.equals(that.userId) : that.userId != null) {
			return false;
		}
		if (password != null ? !password.equals(that.password) : that.password != null) {
			return false;
		}
		if (phraseAnswerMap != null ? !phraseAnswerMap.equals(that.phraseAnswerMap) : that.phraseAnswerMap != null) {
			return false;
		}
		if (userAgent != null ? !userAgent.equals(that.userAgent) : that.userAgent != null) {
			return false;
		}
		if (socketTimeout != that.socketTimeout) {
			return false;
		}
		if (connectTimeout != that.connectTimeout) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		int result = (debug ? 1 : 0);
		result = 31 * result + (userId != null ? userId.hashCode() : 0);
		result = 31 * result + (password != null ? password.hashCode() : 0);
		result = 31 * result + (phraseAnswerMap != null ? phraseAnswerMap.hashCode() : 0);
		result = 31 * result + (userAgent != null ? userAgent.hashCode() : 0);
		result = 31 * result + socketTimeout;
		result = 31 * result + connectTimeout;
		return result;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "{" +
				"debug=" + debug +
				", userId='" + userId + '\'' +
				", password='" + password + '\'' +
				", phraseAnswerMap='" + phraseAnswerMap + '\'' +
				", userAgent='" + userAgent + '\'' +
				", socketTimeout='" + socketTimeout + '\'' +
				", connectTimeout='" + connectTimeout + '\'' +
				'}';
	}
	
	private static final List<ScrapingConfiguration> instances = new ArrayList<ScrapingConfiguration>();
	
	private static void cacheInstance(final ScrapingConfiguration conf) {
		if (!instances.contains(conf)) {
			instances.add(conf);
		}
	}
	
	protected void cacheInstance() {
		cacheInstance(this);
	}
	
	private static ScrapingConfiguration getInstance(final ScrapingConfiguration configurationBase) {
		int index;
		if ((index = instances.indexOf(configurationBase)) == -1) {
			instances.add(configurationBase);
			return configurationBase;
		} else {
			return instances.get(index);
		}
	}
	
	// assures equality after deserializedation
	protected Object readResolve() throws ObjectStreamException {
		return getInstance(this);
	}
}
