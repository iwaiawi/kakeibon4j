package kakeibon4j.entity.expense.detail;

import kakeibon4j.entity.Registerable;

/**
 * A interface representing expense's detail that registered to Kakeibon.
 * 
 * @author ero3
 */
public interface ExpenseDetail extends ExpenseDetailBase, Registerable.Registered {
	
	/**
	 * Returns the code.
	 * 
	 * @return the code
	 */
	public abstract String getCode();
}