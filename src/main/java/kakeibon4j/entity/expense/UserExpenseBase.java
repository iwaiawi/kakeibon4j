package kakeibon4j.entity.expense;

import kakeibon4j.entity.Registerable;
import kakeibon4j.entity.expense.detail.UserExpenseDetailBase;

/**
 * A interface representing user defined expense base.
 * 
 * @param <T> the detail's type
 * @author ero3
 */
public interface UserExpenseBase<T extends UserExpenseDetailBase> extends ExpenseBase<T>, Registerable {
}
