package kakeibon4j.internal.scraping.client.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import kakeibon4j.entity.expense.Expense;
import kakeibon4j.entity.expense.detail.ExpenseDetail;
import kakeibon4j.entity.expense.detail.UserExpenseDetail;

import org.jsoup.helper.StringUtil;

public class DeleteUserExpenseDetailQuery extends ExpenseDetailQuery<Expense<? extends ExpenseDetail>, UserExpenseDetail> {
	
	private static final String MODE = "D";
	
	public DeleteUserExpenseDetailQuery(final Expense<? extends ExpenseDetail> parent, final Collection<? extends UserExpenseDetail> details) {
		super(parent, details);
		if (!parent.getDetails().containsAll(details)) {
			throw new IllegalArgumentException("Argument parent:" + parent + " don't have argument details:" + details + ". Argument parent must have argument details.");
		}
	}
	
	@Override
	protected String getDetailsQueryString(final Collection<? extends UserExpenseDetail> details) {
		final List<String> queries = new ArrayList<String>();
		for (final UserExpenseDetail detail : details) {
			queries.add("{"
					+ "mode:\"" + MODE + "\""
					+ ",expense_detail_code:\"" + detail.getCode() + "\""
					+ ",expense_detail_name:\"" + "undefined" + "\""
					+ "}");
		}
		
		return StringUtil.join(queries, ", ");
	}
}