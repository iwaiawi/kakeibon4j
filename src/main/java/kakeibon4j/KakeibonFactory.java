package kakeibon4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import kakeibon4j.internal.scraping.KakeibonScrapingImpl;

/**
 * A factory class for Kakeibon.
 * 
 * @author ero3
 */
public final class KakeibonFactory implements java.io.Serializable {
	private static final long serialVersionUID = 5193900138477709155L;
	private final Properties props;
	private final String configTreePath;
	
	/**
	 * Creates a KakeibonFactory with a specified input stream of property and a specified config tree.
	 * 
	 * @param is Input stream of property file
	 * @param configTreePath the path string
	 */
	public KakeibonFactory(final InputStream is, final String configTreePath) throws IOException {
		this(createProperty(is), configTreePath);
	}
	
	/**
	 * Creates a KakeibonFactory with a specified property and a specified config tree.
	 * 
	 * @param props property
	 * @param configTreePath the path string
	 */
	public KakeibonFactory(final Properties props, final String configTreePath) {
		this.props = props;
		this.configTreePath = configTreePath;
	}
	
	/**
	 * Creates a KakeibonFactory with a specified property.
	 * 
	 * @param props property
	 */
	public KakeibonFactory(final Properties props) {
		this(props, null);
	}
	
	/**
	 * Creates a KakeibonFactory with a specified config tree.
	 * 
	 * @param configTreePath the path string
	 */
	public KakeibonFactory(final String configTreePath) {
		this.props = null;
		this.configTreePath = configTreePath;
	}
	
	/**
	 * Creates a KakeibonFactory with the default configuration.
	 */
	public KakeibonFactory() {
		this.props = null;
		this.configTreePath = null;
	}
	
	private static Properties createProperty(final InputStream is) throws IOException {
		final Properties prop = new Properties();
		
		try {
			prop.load(is);
		} finally {
			is.close();
		}
		
		return prop;
	}
	
	/**
	 * Returns a Kakeibon instance associated with the configuration bound to this factory.
	 * 
	 * @return an Kakeibon instance
	 */
	public Kakeibon getInstance() {
		if (props != null && configTreePath != null) {
			return new KakeibonScrapingImpl(props, configTreePath);
		} else if (props == null && configTreePath != null) {
			return new KakeibonScrapingImpl(configTreePath);
		} else if (props != null && configTreePath == null) {
			return new KakeibonScrapingImpl(props);
		} else { // prop == null && configTreePath == null
			return new KakeibonScrapingImpl();
		}
	}
}
