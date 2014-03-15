package kakeibon4j.entity.expense.detail;

import kakeibon4j.entity.Registerable;

/**
 * A interface representing expense's detail base.
 * 
 * @author ero3
 */
public interface ExpenseDetailBase extends Comparable<ExpenseDetailBase>, Registerable {
	
	/**
	 * Returns the name.
	 * 
	 * @return the name
	 */
	public abstract String getName();
	
	/**
	 * Test if this is global expense's detail.
	 * 
	 * @return true if this is global expense's detail
	 */
	public abstract boolean isGlobal();
	
	/**
	 * Tests if this object has same parameters as {@code another}.
	 * Parameters are tested by {@code getName(), isGlobal()}.
	 * 
	 * @param another the expense detail to be tested
	 * @return true, if this object has same parameters as {@code another}
	 */
	public abstract boolean equalsAsBase(final ExpenseDetailBase another);
}
