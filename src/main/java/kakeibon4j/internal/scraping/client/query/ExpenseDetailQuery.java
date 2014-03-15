package kakeibon4j.internal.scraping.client.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import kakeibon4j.entity.expense.Expense;
import kakeibon4j.entity.expense.detail.ExpenseDetail;
import kakeibon4j.entity.expense.detail.ExpenseDetailBase;

public abstract class ExpenseDetailQuery<T extends Expense<? extends ExpenseDetail>, S extends ExpenseDetailBase> implements BatchQuery {
	
	private final List<S> details;
	private final T parent;
	
	public ExpenseDetailQuery(final T parent, final Collection<? extends S> details) {
		if (details == null || details.isEmpty()) {
			throw new IllegalStateException("Argument details is null or empty list. Argument details is required.");
		}
		if (parent == null) {
			throw new IllegalStateException("Argument parent is null. Argument parent is required.");
		}
		
		this.details = new ArrayList<S>(details);
		this.parent = parent;
	}
	
	@Override
	public String toQueryString() {
		return "{"
				+ "expense_name:\"" + parent.getName() + "\""
				+ ",expense_code:\"" + parent.getCode() + "\""
				+ ",expense_balanceType:\"" + parent.getBalanceType() + "\""
				+ ",expense_detail_list:[" + getDetailsQueryString(details) + "]}";
	}
	
	protected abstract String getDetailsQueryString(final Collection<? extends S> details);
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "[details=" + details + ", parent=" + parent + "]";
	}
}