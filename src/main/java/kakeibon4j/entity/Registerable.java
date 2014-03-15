package kakeibon4j.entity;

/**
 * a interface representing registerable.
 * 
 * @author ero3
 */
public interface Registerable {
	
	/**
	 * a interface representing already registered.
	 * 
	 * @author ero3
	 */
	public static interface Registered extends Registerable {
	}
	
	/**
	 * a interface representing not registered.
	 * 
	 * @author ero3
	 */
	public static interface Unregistered extends Registerable {
	}
}
