package kakeibon4j.internal.scraping.xml.parser;

import java.util.Collections;
import java.util.Set;

import kakeibon4j.entity.expense.Expense;
import kakeibon4j.entity.expense.ExpenseSet;
import kakeibon4j.entity.expense.detail.ExpenseDetail;
import kakeibon4j.entity.expense.detail.ExpenseDetailSet;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public abstract class ExpenseParser<T extends Expense<S>, S extends ExpenseDetail> extends NormalParser {
	
	public Set<T> parseExpenses(final Document xmlDoc) {
		final Elements expenses = xmlDoc.select(getExpenseQuery());
		if (expenses.size() == 0) {
			return Collections.emptySet();
		}
		
		final Set<T> list = new ExpenseSet<T>();
		for (final Element expense : expenses) {
			final T expenseObj = parseExpense(expense);
			if (expenseObj != null) {
				list.add(expenseObj);
			}
		}
		return list;
	}
	
	public abstract T parseExpense(final Element expense);
	
	public Set<S> parseExpenseDetails(final Element details) {
		if (details == null) {
			return Collections.emptySet();
		}
		
		final Set<S> set = new ExpenseDetailSet<S>();
		for (final Element detail : details.select("expense_detail")) {
			final S detailObj = parseExpenseDetail(detail);
			if (detailObj != null) {
				set.add(detailObj);
			}
		}
		return set;
	}
	
	public S parseExpenseDetail(final Element detail) {
		final Element code = detail.select("detail_code").first(); // mandatory
		final Element name = detail.select("detail_name").first(); // mandatory
		
		if (code == null || name == null) {
			throw new IllegalArgumentException("Argument " + detail + " must contains both <detail_code> and <detail_name>.");
		}
		
		return createExpenseDetailInstance(parseString(code.ownText()), parseString(name.ownText()));
	}
	
	protected abstract String getExpenseQuery();
	
	protected abstract S createExpenseDetailInstance(final String code, final String name);
}