package kakeibon4j.entity.expense;

import java.util.Collection;
import java.util.Collections;

import kakeibon4j.entity.expense.detail.UnregisteredUserExpenseDetail;

/**
 * Default implementation of {@link UnregisteredUserExpense}.
 * 
 * @author ero3
 */
public class UnregisteredUserExpenseImpl extends AbstractExpenseBase<UnregisteredUserExpenseDetail> implements UnregisteredUserExpense {
	
	public UnregisteredUserExpenseImpl(final String name) {
		this(name, ExpenseBase.PAYMENT, Collections.<UnregisteredUserExpenseDetail>emptyList());
	}
	
	public UnregisteredUserExpenseImpl(final String name, final long balanceType, final Collection<? extends UnregisteredUserExpenseDetail> details) {
		super(name, balanceType, details);
		if (name.length() > 10) {
			throw new IllegalArgumentException("Argument name:" + name + " is too long. Max length is 10 characters.");
		}
	}
	
	@Override
	public boolean isGlobal() {
		return false;
	}
}
