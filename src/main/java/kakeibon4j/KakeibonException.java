package kakeibon4j;

/**
 * An exception class that will be thrown when Kakeibon accesses are failed.
 * 
 * @author ero3
 */
public class KakeibonException extends Exception {
	
	public KakeibonException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	public KakeibonException(final String message) {
		this(message, (Throwable)null);
	}
	
	public KakeibonException(final Exception cause) {
		this(cause.getMessage(), cause);
	}
}
