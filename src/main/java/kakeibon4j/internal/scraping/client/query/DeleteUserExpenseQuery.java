package kakeibon4j.internal.scraping.client.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import kakeibon4j.entity.expense.UserExpense;

import org.jsoup.helper.StringUtil;

public class DeleteUserExpenseQuery extends UserExpenseQuery<UserExpense> {
	
	private static final String MODE = "D";
	
	public DeleteUserExpenseQuery(final Collection<? extends UserExpense> expenses) {
		super(expenses);
	}
	
	@Override
	protected String getExpenseListQueryString(final Collection<? extends UserExpense> expenses) {
		final List<String> queries = new ArrayList<String>();
		for (final UserExpense expense : expenses) {
			queries.add("{"
					+ "mode:\"" + MODE + "\""
					+ ",expense_code:\"" + expense.getCode() + "\""
					+ "}");
		}
		
		return StringUtil.join(queries, ", ");
	}
}
