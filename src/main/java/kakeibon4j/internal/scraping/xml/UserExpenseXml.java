package kakeibon4j.internal.scraping.xml;

import java.util.Collection;
import java.util.Set;

import kakeibon4j.entity.expense.GlobalExpense;
import kakeibon4j.entity.expense.UserExpense;

public interface UserExpenseXml extends NormalXml {
	
	public abstract UserExpense getUserExpense(final String parentCode);
	
	public abstract Set<UserExpense> getUserExpenses();

	public abstract GlobalExpense getGlobalExpense(final String parentCode, final Collection<? extends GlobalExpense> globals);

	public abstract Set<GlobalExpense> getGlobalExpenses(final Collection<? extends GlobalExpense> globals);
}
