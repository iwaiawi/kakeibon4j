package kakeibon4j.entity;

/**
 * a interface representing updatrable.
 * 
 * @author ero3
 */
public interface Updatable {
	
	/**
	 * a interface representing already updated.
	 * 
	 * @author ero3
	 */
	public static interface Updated extends Updatable {
	}
	
	/**
	 * a interface representing not updated.
	 * 
	 * @author ero3
	 */
	public static interface Unupdated extends Updatable {
	}
}
