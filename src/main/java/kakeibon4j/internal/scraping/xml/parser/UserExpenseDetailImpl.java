package kakeibon4j.internal.scraping.xml.parser;

import kakeibon4j.entity.expense.detail.AbstractExpenseDetail;
import kakeibon4j.entity.expense.detail.UserExpenseDetail;

/* package */class UserExpenseDetailImpl extends AbstractExpenseDetail implements UserExpenseDetail {
	
	/* package */UserExpenseDetailImpl(final String code, final String name) {
		super(code, name);
		if (name.length() > 10) {
			throw new IllegalArgumentException("Argument name:" + name + " is too long. Max length is 10 characters.");
		}
	}
	
	@Override
	public boolean isGlobal() {
		return false;
	}
}
