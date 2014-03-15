package kakeibon4j.entity.expense;

import kakeibon4j.entity.expense.detail.ExpenseDetail;

/**
 * A interface representing global expense.
 * 
 * @author ero3
 */
public interface GlobalExpense extends Expense<ExpenseDetail> {
	
	/**
	 * Returns the distribute ratio.
	 * 
	 * @return the distribute ratio
	 */
	public abstract Long getDistributeRatio();
}
