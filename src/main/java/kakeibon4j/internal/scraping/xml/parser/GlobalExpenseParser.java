package kakeibon4j.internal.scraping.xml.parser;

import java.util.Collections;
import java.util.Set;

import kakeibon4j.entity.expense.GlobalExpense;
import kakeibon4j.entity.expense.detail.ExpenseDetail;
import kakeibon4j.entity.expense.detail.ExpenseDetailBase;
import kakeibon4j.entity.expense.detail.ExpenseDetailSet;
import kakeibon4j.entity.expense.detail.GlobalExpenseDetail;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class GlobalExpenseParser extends ExpenseParser<GlobalExpense, ExpenseDetail> {
	
	@Override
	public GlobalExpense parseExpense(final Element expense) {
		final Element code = expense.select("code").first(); // mandatory
		final Element name = expense.select("name").first(); // mandatory
		final Element balanceType = expense.select("balance_type").first(); // mandatory
		final Element distributeRatio = expense.select("distribute_ratio").first(); // mandatory
		final Element detailList = expense.select("detail_list").first(); // mandatory
		
		if (code == null || name == null || balanceType == null || distributeRatio == null || detailList == null) {
			throw new IllegalArgumentException("Argument " + expense + " must contains both <code> and <name> <balance_type> <distribute_ratio> and <detail_list>.");
		}
		
		return new GlobalExpenseImpl(parseString(code.ownText()), parseString(name.ownText()), parseLongValue(balanceType.ownText()), parseLong(distributeRatio.ownText()), parseExpenseDetails(detailList));
	}
	
	@Override
	protected String getExpenseQuery() {
		return "global_expenses expense_list expense";
	}
	
	@Override
	protected ExpenseDetail createExpenseDetailInstance(final String code, final String name) {
		return new GlobalExpenseDetailImpl(code, name);
	}
	
	public Set<GlobalExpenseDetail> parseExpenseDetailList(final Document xmlDoc, final String parentCode) {
		for (final Element expense : xmlDoc.select(getExpenseQuery())) {
			final Element code = expense.select("code").first();
			if (code != null && parentCode.equals(parseString(code.ownText()))) {
				final Set<GlobalExpenseDetail> globals = new ExpenseDetailSet<GlobalExpenseDetail>();
				for (final ExpenseDetailBase detail : parseExpenseDetails(expense.select("detail_list").first())) {
					if (detail instanceof GlobalExpenseDetail) {
						globals.add((GlobalExpenseDetail)detail);
					}
				}
				return globals;
			}
		}
		
		return Collections.emptySet();
	}
}