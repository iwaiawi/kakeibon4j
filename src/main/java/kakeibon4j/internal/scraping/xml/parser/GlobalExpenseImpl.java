package kakeibon4j.internal.scraping.xml.parser;

import java.util.Collection;

import kakeibon4j.entity.expense.AbstractExpense;
import kakeibon4j.entity.expense.GlobalExpense;
import kakeibon4j.entity.expense.detail.ExpenseDetail;

/* package */class GlobalExpenseImpl extends AbstractExpense<ExpenseDetail> implements GlobalExpense {
	
	/**
	 * 用途不明
	 * balanceType=Expense.INCOMEの場合にnullとなるためnullable
	 */
	private final Long distributeRatio;
	
	/* package */GlobalExpenseImpl(final String code, final String name, final long balanceType, final Long distributeRatio, final Collection<? extends ExpenseDetail> details) {
		super(code, name, balanceType, details);
		this.distributeRatio = distributeRatio;
	}
	
	@Override
	public Long getDistributeRatio() {
		return distributeRatio;
	}
	
	@Override
	public boolean isGlobal() {
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((distributeRatio == null) ? 0 : distributeRatio.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final GlobalExpenseImpl other = (GlobalExpenseImpl)obj;
		if (distributeRatio == null) {
			if (other.distributeRatio != null) {
				return false;
			}
		} else if (!distributeRatio.equals(other.distributeRatio)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "[code=" + getCode() + ", name=" + getName() + ", balanceType=" + getBalanceType() + ", distributeRatio=" + distributeRatio + ", details=" + getDetails() + "]";
	}
}
