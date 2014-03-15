package kakeibon4j.internal.scraping.client.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import kakeibon4j.entity.expense.Expense;
import kakeibon4j.entity.expense.detail.ExpenseDetail;
import kakeibon4j.entity.expense.detail.UnregisteredUserExpenseDetail;

import org.jsoup.helper.StringUtil;

public class RegisterUserExpenseDetailQuery extends ExpenseDetailQuery<Expense<? extends ExpenseDetail>, UnregisteredUserExpenseDetail> {
	
	private static final String MODE = "A";
	
	public RegisterUserExpenseDetailQuery(final Expense<? extends ExpenseDetail> parent, final Collection<? extends UnregisteredUserExpenseDetail> details) {
		super(parent, details);
	}
	
	@Override
	protected String getDetailsQueryString(final Collection<? extends UnregisteredUserExpenseDetail> details) {
		final List<String> queries = new ArrayList<String>();
		for (final UnregisteredUserExpenseDetail detail : details) {
			queries.add("{mode:\"" + MODE + "\",expense_detail_name:\"" + detail.getName() + "\"}");
		}
		
		return StringUtil.join(queries, ", ");
	}
}