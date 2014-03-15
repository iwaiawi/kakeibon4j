package kakeibon4j.internal.scraping.xml.parser;

import java.util.Collection;
import java.util.Set;

import kakeibon4j.entity.expense.ExpenseSet;
import kakeibon4j.entity.expense.GlobalExpense;
import kakeibon4j.entity.expense.detail.ExpenseDetail;
import kakeibon4j.entity.expense.detail.ExpenseDetailSet;
import kakeibon4j.entity.expense.detail.UserExpenseDetail;

import org.jsoup.nodes.Element;

public class GlobalExpenseContainsUserExpenseDetailParser extends ExpenseParser<GlobalExpense, ExpenseDetail> {
	
	private final Set<GlobalExpense> globals;
	
	public GlobalExpenseContainsUserExpenseDetailParser(final Collection<? extends GlobalExpense> originalGlobals) {
		this.globals = new ExpenseSet<GlobalExpense>(originalGlobals);
	}
	
	@Override
	public GlobalExpense parseExpense(final Element expense) {
		final Element code = expense.select("code").first(); // mandatory
		final Element name = expense.select("name").first(); // mandatory
		final Element balanceType = expense.select("balance_type").first(); // mandatory
		final Element detailList = expense.select("detail_list").first(); // optional
		
		if (code == null || name == null || balanceType == null) {
			throw new IllegalArgumentException("Argument " + expense + " must contains both <code> and <name> and <balance_type>.");
		}
		
		// nameやbalanceTypeが空でない場合はUserExpenseのためスキップ
		if (!"".equals(name.ownText()) || !"".equals(balanceType.ownText())) {
			return null;
		}
		
		for (final GlobalExpense global : globals) {
			if (global.getCode().equals(parseString(code.ownText()))) {
				return new GlobalExpenseImpl(global.getCode(), global.getName(), global.getBalanceType(), global.getDistributeRatio(), parseExpenseDetails(detailList, global.getDetails()));
			}
		}
		
		return null;
	}
	
	private Set<ExpenseDetail> parseExpenseDetails(final Element details, final Collection<? extends ExpenseDetail> globalDetails) {
		final Set<ExpenseDetail> set = new ExpenseDetailSet<ExpenseDetail>(globalDetails);
		set.addAll(parseExpenseDetails(details));
		return set;
	}
	
	@Override
	protected String getExpenseQuery() {
		return "result_user_expense_list user_expenses user_expense_list expense_list expense";
	}
	
	@Override
	protected UserExpenseDetail createExpenseDetailInstance(final String code, final String name) {
		return new UserExpenseDetailImpl(code, name);
	}
}