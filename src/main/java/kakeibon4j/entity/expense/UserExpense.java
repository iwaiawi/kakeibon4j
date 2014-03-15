package kakeibon4j.entity.expense;

import kakeibon4j.entity.expense.detail.UserExpenseDetail;

/**
 * A interface representing user defined expense that registered to Kakeibon.
 * 
 * @author ero3
 */
public interface UserExpense extends UserExpenseBase<UserExpenseDetail>, Expense<UserExpenseDetail> {
}