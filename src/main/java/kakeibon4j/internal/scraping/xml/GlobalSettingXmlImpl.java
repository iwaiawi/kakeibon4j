package kakeibon4j.internal.scraping.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Set;

import kakeibon4j.KakeibonException;
import kakeibon4j.entity.expense.Expense;
import kakeibon4j.entity.expense.ExpenseSet;
import kakeibon4j.entity.expense.GlobalExpense;
import kakeibon4j.entity.expense.detail.ExpenseDetail;
import kakeibon4j.internal.scraping.xml.parser.GlobalExpenseParser;

public class GlobalSettingXmlImpl extends UserExpenseXmlImpl implements GlobalSettingXml {
	
	private static final String SCHEMA_FILE_NAME = "/GlobalSettingXml.xsd";
	
	public GlobalSettingXmlImpl(final InputStream schema, final String rawXml) throws IOException, KakeibonException {
		super(schema, rawXml);
	}
	
	public GlobalSettingXmlImpl(final String rawXml) throws IOException, KakeibonException {
		super(getSchemaInputStream(), rawXml);
	}
	
	@Override
	public Set<Expense<? extends ExpenseDetail>> getExpenses() {
		final Set<Expense<? extends ExpenseDetail>> all = new ExpenseSet<Expense<? extends ExpenseDetail>>();
		all.addAll(getUserExpenses());
		all.addAll(getGlobalExpenses());
		return all;
	}
	
	@Override
	public GlobalExpense getGlobalExpense(final String parentCode) {
		for (final GlobalExpense expense : getGlobalExpenses()) {
			if (expense.getCode().equals(parentCode)) {
				return expense;
			}
		}
		return null;
	}
	
	@Override
	public Set<GlobalExpense> getGlobalExpenses() {
		final Set<GlobalExpense> globalOnly = new GlobalExpenseParser().parseExpenses(toJsoup());
		final Set<GlobalExpense> containsUser = getGlobalExpenseContainsUserExpenseDetais(globalOnly);
		
		for (final GlobalExpense replace : containsUser) {
			final Iterator<GlobalExpense> it = globalOnly.iterator();
			while (it.hasNext()) {
				if (it.next().getCode().equals(replace.getCode())) {
					it.remove();
				}
			}
		}
		globalOnly.addAll(containsUser);
		
		return globalOnly;
	}
	
	private static InputStream getSchemaInputStream() {
		return GlobalSettingXmlImpl.class.getResourceAsStream(SCHEMA_FILE_NAME);
	}
}
