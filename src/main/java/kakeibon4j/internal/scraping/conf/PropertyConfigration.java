package kakeibon4j.internal.scraping.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class PropertyConfigration extends ScrapingConfiguration {
	
	public static final String DEBUG = "debug";
	public static final String USER_ID = "userId";
	public static final String PASSWORD = "password";
	public static final String PHRASE_ANSWER1 = "phraseAnswer1";
	public static final String PHRASE_ANSWER2 = "phraseAnswer2";
	public static final String PHRASE_ANSWER3 = "phraseAnswer3";
	public static final String USER_AGENT = "userAgent";
	public static final String SOCKET_TIMEOUT = "socketTimeout";
	public static final String CONNECT_TIMEOUT = "connectTimeout";
	
	public PropertyConfigration(final Properties props) {
		this(props, "/");
	}
	
	public PropertyConfigration(final Properties props, final String treePath) {
		super();
		setFieldsWithTreePath(props, treePath);
	}
	
	public PropertyConfigration(final String treePath) {
		super();
		Properties props;
		// load from system properties
		try {
			props = (Properties)System.getProperties().clone();
			try {
				final Map<String, String> envMap = System.getenv();
				for (final String key : envMap.keySet()) {
					props.setProperty(key, envMap.get(key));
				}
			} catch (final SecurityException ignore) {
			}
			normalize(props);
		} catch (final SecurityException ignore) {
			// Unsigned applets are not allowed to access System properties
			props = new Properties();
		}
		final String KAKEIBON4J_PROPERTIES = "kakeibon4j.properties";
		// override System properties with ./kakeibon4j.properties in the classpath
		loadProperties(props, "." + File.separatorChar + KAKEIBON4J_PROPERTIES);
		// then, override with /kakeibon4j.properties in the classpath
		loadProperties(props, this.getClass().getResourceAsStream("/" + KAKEIBON4J_PROPERTIES));
		// then, override with /WEB/INF/kakeibon4j.properties in the classpath
		loadProperties(props, this.getClass().getResourceAsStream("/WEB-INF/" + KAKEIBON4J_PROPERTIES));
		// for Google App Engine
		try {
			loadProperties(props, new FileInputStream("WEB-INF/" + KAKEIBON4J_PROPERTIES));
		} catch (final SecurityException ignore) {
		} catch (final FileNotFoundException ignore) {
		}
		
		setFieldsWithTreePath(props, treePath);
	}
	
	/**
	 * Creates a root PropertyConfiguration. This constructor is equivalent to new PropertyConfiguration("/").
	 */
	public PropertyConfigration() {
		this("/");
	}
	
	protected boolean notNull(final Properties props, final String prefix, final String name) {
		return props.getProperty(prefix + name) != null;
	}
	
	protected boolean loadProperties(final Properties props, final String path) {
		FileInputStream fis = null;
		try {
			final File file = new File(path);
			if (file.exists() && file.isFile()) {
				fis = new FileInputStream(file);
				props.load(fis);
				normalize(props);
				return true;
			}
		} catch (final Exception ignore) {
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (final IOException ignore) {
				
			}
		}
		return false;
	}
	
	protected boolean loadProperties(final Properties props, final InputStream is) {
		try {
			props.load(is);
			normalize(props);
			return true;
		} catch (final Exception ignore) {
		}
		return false;
	}
	
	protected void normalize(final Properties props) {
		final Set keys = props.keySet();
		final ArrayList<String> toBeNormalized = new ArrayList<String>(10);
		for (final Object key : keys) {
			final String keyStr = (String)key;
			if (-1 != (keyStr.indexOf("kakeibon4j."))) {
				toBeNormalized.add(keyStr);
			}
		}
		for (final String keyStr : toBeNormalized) {
			final String property = props.getProperty(keyStr);
			final int index = keyStr.indexOf("kakeibon4j.");
			final String newKey = keyStr.substring(0, index) + keyStr.substring(index + 10);
			props.setProperty(newKey, property);
		}
	}
	
	protected void setFieldsWithTreePath(final Properties props, final String treePath) {
		setFieldsWithPrefix(props, "");
		final String[] splitArray = treePath.split("/");
		String prefix = null;
		for (final String split : splitArray) {
			if (!"".equals(split)) {
				if (null == prefix) {
					prefix = split + ".";
				} else {
					prefix += split + ".";
				}
				setFieldsWithPrefix(props, prefix);
			}
		}
	}
	
	protected boolean getBoolean(final Properties props, final String prefix, final String name) {
		final String value = props.getProperty(prefix + name);
		return Boolean.valueOf(value);
	}
	
	protected int getIntProperty(final Properties props, final String prefix, final String name) {
		final String value = props.getProperty(prefix + name);
		try {
			return Integer.parseInt(value);
		} catch (final NumberFormatException nfe) {
			return -1;
		}
	}
	
	protected long getLongProperty(final Properties props, final String prefix, final String name) {
		final String value = props.getProperty(prefix + name);
		try {
			return Long.parseLong(value);
		} catch (final NumberFormatException nfe) {
			return -1L;
		}
	}
	
	protected String getString(final Properties props, final String prefix, final String name) {
		return props.getProperty(prefix + name);
	}
	
	protected void setFieldsWithPrefix(final Properties props, final String prefix) {
		if (notNull(props, prefix, DEBUG)) {
			setDebug(getBoolean(props, prefix, DEBUG));
		}
		if (notNull(props, prefix, USER_ID)) {
			setUserId(getString(props, prefix, USER_ID));
		}
		if (notNull(props, prefix, PASSWORD)) {
			setPassword(getString(props, prefix, PASSWORD));
		}
		if (notNull(props, prefix, PHRASE_ANSWER1) && notNull(props, prefix, PHRASE_ANSWER2) && notNull(props, prefix, PHRASE_ANSWER3)) {
			final Map<String, String> map = new HashMap<String, String>();
			map.put(PHRASE_ANSWER_KEY1, getString(props, prefix, PHRASE_ANSWER1));
			map.put(PHRASE_ANSWER_KEY2, getString(props, prefix, PHRASE_ANSWER2));
			map.put(PHRASE_ANSWER_KEY3, getString(props, prefix, PHRASE_ANSWER3));
			setPhraseAnswer(map);
		}
		if (notNull(props, prefix, USER_AGENT)) {
			setUserAgent(getString(props, prefix, USER_AGENT));
		}
		if (notNull(props, prefix, SOCKET_TIMEOUT)) {
			setSocketTimeout(getIntProperty(props, prefix, SOCKET_TIMEOUT));
		}
		if (notNull(props, prefix, CONNECT_TIMEOUT)) {
			setConnectTimeout(getIntProperty(props, prefix, CONNECT_TIMEOUT));
		}
		cacheInstance();
	}
	
	@Override
	protected Object readResolve() throws ObjectStreamException {
		return super.readResolve();
	}
	
	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof PropertyConfigration)) {
			return false;
		}
		return super.equals(o);
	}
}