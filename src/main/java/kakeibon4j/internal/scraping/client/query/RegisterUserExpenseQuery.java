package kakeibon4j.internal.scraping.client.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import kakeibon4j.entity.expense.UnregisteredUserExpense;

import org.jsoup.helper.StringUtil;

public class RegisterUserExpenseQuery extends UserExpenseQuery<UnregisteredUserExpense> {
	
	private static final String MODE = "A";
	
	public RegisterUserExpenseQuery(final Collection<? extends UnregisteredUserExpense> expenses) {
		super(expenses);
	}
	
	@Override
	protected String getExpenseListQueryString(final Collection<? extends UnregisteredUserExpense> expenses) {
		final List<String> queries = new ArrayList<String>();
		for (final UnregisteredUserExpense expense : expenses) {
			queries.add("{"
					+ "mode:\"" + MODE + "\""
					+ ",expense_name:\"" + expense.getName() + "\""
					+ ",expense_balanceType:\"" + expense.getBalanceType() + "\""
					+ "}");
		}
		
		return StringUtil.join(queries, ", ");
	}
}
