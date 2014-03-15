package kakeibon4j.entity.expense;

import kakeibon4j.entity.Registerable;
import kakeibon4j.entity.expense.detail.ExpenseDetail;

/**
 * A interface representing expense that registered to Kakeibon.
 * 
 * @param <T> the detail's type
 * @author ero3
 */
public interface Expense<T extends ExpenseDetail> extends ExpenseBase<T>, Registerable.Registered {
	
	/**
	 * Returns the code.
	 * 
	 * @return the code
	 */
	public abstract String getCode();
}