package kakeibon4j.entity.expense;

import java.util.Collection;

import kakeibon4j.entity.expense.detail.ExpenseDetail;

/**
 * An abstract class representing expense that registered to Kakeibon.
 * 
 * @param <T> the detail's type
 * @author ero3
 */
public abstract class AbstractExpense<T extends ExpenseDetail> extends AbstractExpenseBase<T> implements Expense<T> {
	
	/**
	 * 識別子コード
	 * 必須項目のためnotnullかつ空文字列不可
	 */
	private final String code;
	
	public AbstractExpense(final String code, final String name, final long balanceType, final Collection<? extends T> details) {
		super(name, balanceType, details);
		if (code == null || "".equals(code)) {
			throw new IllegalArgumentException("Argument code must be not both null and empty string.");
		}
		this.code = code;
	}
	
	@Override
	public String getCode() {
		return code;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		final AbstractExpense<?> other = (AbstractExpense<?>)obj;
		if (code == null) {
			if (other.code != null) {
				return false;
			}
		} else if (!code.equals(other.code)) {
			return false;
		}
		return true;
	}
	
	@Override
	public int compareTo(final ExpenseBase<?> o) {
		if (o instanceof Expense<?>) {
			return this.code.compareTo(((Expense<?>)o).getCode());
		}
		return super.compareTo(o);
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "[code=" + code + ", name=" + getName() + ", balanceType=" + getBalanceType() + ", details=" + getDetails() + "]";
	}
}