package kakeibon4j.internal.scraping.xml.parser;

import java.util.Collection;

import kakeibon4j.entity.expense.AbstractExpense;
import kakeibon4j.entity.expense.UserExpense;
import kakeibon4j.entity.expense.detail.UserExpenseDetail;

/* package */class UserExpenseImpl extends AbstractExpense<UserExpenseDetail> implements UserExpense {
	
	/* package */UserExpenseImpl(final String code, final String name, final long balanceType, final Collection<? extends UserExpenseDetail> details) {
		super(code, name, balanceType, details);
		if (name.length() > 10) {
			throw new IllegalArgumentException("Argument name:" + name + " is too long. Max length is 10 characters.");
		}
	}
	
	@Override
	public boolean isGlobal() {
		return false;
	}
}
