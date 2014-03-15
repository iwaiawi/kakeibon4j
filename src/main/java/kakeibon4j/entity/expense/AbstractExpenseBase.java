package kakeibon4j.entity.expense;

import java.util.Collection;
import java.util.Set;

import kakeibon4j.entity.expense.detail.ExpenseDetailBase;
import kakeibon4j.entity.expense.detail.ExpenseDetailSet;

/**
 * An abstract class representing expense base.
 * 
 * @param <T> the detail's type
 * @author ero3
 */
public abstract class AbstractExpenseBase<T extends ExpenseDetailBase> implements ExpenseBase<T> {
	
	/**
	 * 名称
	 * 必須項目のためnotnullかつ空文字列不可
	 */
	private final String name;
	
	/**
	 * 収支タイプ
	 * Expense.INCOMEもしくはExpense.PAYMENTのみ許容
	 */
	private final long balanceType;
	
	/**
	 * 配下のExpenseDetailBaseのリスト
	 * notnullかつゼロ以上のExpenseDetailBaseを含む。
	 */
	private final Set<T> details;
	
	public AbstractExpenseBase(final String name, final long balanceType, final Collection<? extends T> details) {
		if (name == null || "".equals(name)) {
			throw new IllegalArgumentException("Argument name must not be null and empty string.");
		}
		if (balanceType != INCOME && balanceType != PAYMENT) {
			throw new IllegalArgumentException("Argument balanceType must be INCOME(=1) or PAYMENT(=2).");
		}
		if (details == null) {
			throw new IllegalArgumentException("Argument details must not be null.");
		}
		
		this.name = name;
		this.balanceType = balanceType;
		this.details = new ExpenseDetailSet<T>(details);
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public long getBalanceType() {
		return balanceType;
	}
	
	@Override
	public Set<T> getDetails() {
		return new ExpenseDetailSet<T>(details);
	}
	
	@Override
	public boolean equalsAsBase(final ExpenseBase<T> another) {
		if (name == null) {
			if (another.getName() != null) {
				return false;
			}
		} else if (!name.equals(another.getName())) {
			return false;
		} else if (balanceType != another.getBalanceType()) {
			return false;
		} else if (details == null) {
			if (another.getDetails() != null) {
				return false;
			}
		} else if (!details.equals(another.getDetails())) {
			return false;
		} else if (isGlobal() != another.isGlobal()) {
			return false;
		}
		return true;
	}
	
	// @Override
	// public boolean equalsAsBaseExceptDetails(final ExpenseBase<?> another) {
	// if (name == null) {
	// if (another.getName() != null) {
	// return false;
	// }
	// } else if (!name.equals(another.getName())) {
	// return false;
	// } else if (balanceType != another.getBalanceType()) {
	// return false;
	// } else if (isGlobal() != another.isGlobal()) {
	// return false;
	// }
	// return true;
	// }
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int)(balanceType ^ (balanceType >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		return equalsAsBase((ExpenseBase<T>)obj);
	}
	
	@Override
	public int compareTo(final ExpenseBase<?> o) {
		if (name != null) {
			return this.name.compareTo(o.getName());
		}
		return 0;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "[name=" + name + ", balanceType=" + balanceType + ", details=" + details + "]";
	}
}