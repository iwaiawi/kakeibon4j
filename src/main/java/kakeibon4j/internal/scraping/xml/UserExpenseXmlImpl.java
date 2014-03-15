package kakeibon4j.internal.scraping.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Set;

import kakeibon4j.KakeibonException;
import kakeibon4j.entity.expense.GlobalExpense;
import kakeibon4j.entity.expense.UserExpense;
import kakeibon4j.internal.scraping.xml.parser.GlobalExpenseContainsUserExpenseDetailParser;
import kakeibon4j.internal.scraping.xml.parser.UserExpenseParser;

public class UserExpenseXmlImpl extends NormalXmlImpl implements UserExpenseXml {
	
	private static final String SCHEMA_FILE_NAME = "/UserExpenseXml.xsd";
	
	public UserExpenseXmlImpl(final InputStream schema, final String rawXml) throws IOException, KakeibonException {
		super(schema, rawXml);
	}
	
	public UserExpenseXmlImpl(final String rawXml) throws IOException, KakeibonException {
		super(getSchemaInputStream(), rawXml);
	}
	
	@Override
	public UserExpense getUserExpense(final String parentCode) {
		for (final UserExpense expense : getUserExpenses()) {
			if (expense.getCode().equals(parentCode)) {
				return expense;
			}
		}
		return null;
	}
	
	@Override
	public Set<UserExpense> getUserExpenses() {
		return new UserExpenseParser().parseExpenses(toJsoup());
	}
	
	@Override
	public GlobalExpense getGlobalExpense(final String parentCode, final Collection<? extends GlobalExpense> originalGlobals) {
		for (final GlobalExpense expense : getGlobalExpenses(originalGlobals)) {
			if (expense.getCode().equals(parentCode)) {
				return expense;
			}
		}
		return null;
	}
	
	@Override
	public Set<GlobalExpense> getGlobalExpenses(final Collection<? extends GlobalExpense> originalGlobals) {
		return getGlobalExpenseContainsUserExpenseDetais(originalGlobals);
	}
	
	protected Set<GlobalExpense> getGlobalExpenseContainsUserExpenseDetais(final Collection<? extends GlobalExpense> originalGlobals) {
		return new GlobalExpenseContainsUserExpenseDetailParser(originalGlobals).parseExpenses(toJsoup());
	}
	
	private static InputStream getSchemaInputStream() {
		return UserExpenseXmlImpl.class.getResourceAsStream(SCHEMA_FILE_NAME);
	}
}
