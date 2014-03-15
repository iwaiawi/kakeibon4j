package kakeibon4j.entity.expense;

import kakeibon4j.entity.Registerable;
import kakeibon4j.entity.expense.detail.UnregisteredUserExpenseDetail;

/**
 * A interface representing user defined expense that not registered to Kakeibon.
 * 
 * @author ero3
 */
public interface UnregisteredUserExpense extends UserExpenseBase<UnregisteredUserExpenseDetail>, Registerable.Unregistered {
}