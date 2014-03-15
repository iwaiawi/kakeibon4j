package kakeibon4j.internal.scraping.xml;

import java.util.Set;

import kakeibon4j.entity.expense.Expense;
import kakeibon4j.entity.expense.GlobalExpense;
import kakeibon4j.entity.expense.detail.ExpenseDetail;

public interface GlobalSettingXml extends UserExpenseXml {
	
	public abstract Set<Expense<? extends ExpenseDetail>> getExpenses();
	
	public abstract Set<GlobalExpense> getGlobalExpenses();
	
	public abstract GlobalExpense getGlobalExpense(final String parentCode);
}
