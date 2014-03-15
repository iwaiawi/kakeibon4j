package kakeibon4j.internal.scraping.xml.parser;

import java.util.Collections;
import java.util.Set;

import kakeibon4j.entity.expense.UserExpense;
import kakeibon4j.entity.expense.detail.UserExpenseDetail;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class UserExpenseParser extends ExpenseParser<UserExpense, UserExpenseDetail> {
	
	@Override
	public UserExpense parseExpense(final Element expense) {
		final Element code = expense.select("code").first(); // mandatory
		final Element name = expense.select("name").first(); // mandatory
		final Element balanceType = expense.select("balance_type").first(); // mandatory
		final Element detailList = expense.select("detail_list").first(); // optional
		
		if (code == null || name == null || balanceType == null) {
			throw new IllegalArgumentException("Argument " + expense + " must contains both <code> and <name> and <balance_type>.");
		}
		
		// codeのみ値が存在し、その他が空の場合はGlobalExpenseのためスキップ
		if (!"".equals(code.ownText()) && "".equals(name.ownText()) && "".equals(balanceType.ownText())) {
			return null;
		}
		
		return new UserExpenseImpl(parseString(code.ownText()), parseString(name.ownText()), parseLongValue(balanceType.ownText()), parseExpenseDetails(detailList));
	}
	
	@Override
	protected String getExpenseQuery() {
		return "result_user_expense_list user_expenses user_expense_list expense_list expense";
	}
	
	@Override
	protected UserExpenseDetail createExpenseDetailInstance(final String code, final String name) {
		return new UserExpenseDetailImpl(code, name);
	}
	
	public Set<UserExpenseDetail> parseExpenseDetails(final Document xmlDoc, final String parentCode) {
		for (final Element expense : xmlDoc.select(getExpenseQuery())) {
			final Element code = expense.select("code").first();
			if (code != null && parentCode.equals(parseString(code.ownText()))) {
				return parseExpenseDetails(expense.select("detail_list").first());
			}
		}
		
		return Collections.emptySet();
	}
}