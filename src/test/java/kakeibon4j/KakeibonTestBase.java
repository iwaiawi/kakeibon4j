package kakeibon4j;

import java.io.InputStream;
import java.util.Collection;
import java.util.Random;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;

public class KakeibonTestBase {
	
	protected Kakeibon kakeibon1;
	private final boolean DEBUG = true;
	
	@Before
	public void setUp() throws Exception {
		InputStream is = KakeibonTestBase.class.getResourceAsStream("/test.properties");
		if (is == null) {
			is = KakeibonTestBase.class.getResourceAsStream("/example.properties");
		}
		
		kakeibon1 = new KakeibonFactory(is, "/id1").getInstance();
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	protected String getRandomString(final int length) {
		return UUID.randomUUID().toString().substring(0, length);
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T getRandomOne(final Collection<T> collection) {
		final Random r = new Random();
		return (T)collection.toArray()[r.nextInt(collection.size())];
	}
	
	protected void debug(final Object obj) {
		if (DEBUG) {
			System.out.println(obj);
		}
	}
	
	protected void debug(final String message, final Object obj) {
		if (DEBUG) {
			System.out.println(message + " : " + obj);
		}
	}
}
