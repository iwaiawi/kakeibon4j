package kakeibon4j.internal.scraping.client.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import kakeibon4j.entity.expense.ExpenseBase;
import kakeibon4j.entity.expense.detail.ExpenseDetailBase;

public abstract class UserExpenseQuery<T extends ExpenseBase<? extends ExpenseDetailBase>> implements BatchQuery {
	
	private final List<T> expenses;
	
	public UserExpenseQuery(final Collection<? extends T> expenses) {
		if (expenses == null || expenses.isEmpty()) {
			throw new IllegalStateException("Argument expenses is null or empty list. Argument expenses is required.");
		}
		this.expenses = new ArrayList<T>(expenses);
	}
	
	@Override
	public String toQueryString() {
		return "{expense_list:[" + getExpenseListQueryString(expenses) + "]}";
	}
	
	protected abstract String getExpenseListQueryString(final Collection<? extends T> expenses);
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "[expenses=" + expenses + "]";
	}
}