package kakeibon4j.internal.scraping.xml.parser;

import kakeibon4j.entity.expense.detail.AbstractExpenseDetail;
import kakeibon4j.entity.expense.detail.GlobalExpenseDetail;

/* package */class GlobalExpenseDetailImpl extends AbstractExpenseDetail implements GlobalExpenseDetail {
	
	/* package */GlobalExpenseDetailImpl(final String code, final String name) {
		super(code, name);
	}
	
	@Override
	public boolean isGlobal() {
		return true;
	}
}
