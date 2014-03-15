package kakeibon4j.entity.expense;

import java.util.Set;

import kakeibon4j.entity.expense.detail.ExpenseDetailBase;

/**
 * A interface representing expense base.
 * 
 * @param <T> the detail's type
 * @author ero3
 */
public interface ExpenseBase<T extends ExpenseDetailBase> extends Comparable<ExpenseBase<?>> {
	
	/**
	 * A balance type's value representing income.
	 */
	public static final long INCOME = 1L;
	
	/**
	 * A balance type's value representing payment.
	 */
	public static final long PAYMENT = 2L;
	
	/**
	 * Returns the name.
	 * 
	 * @return the name
	 */
	public abstract String getName();
	
	/**
	 * Returns the balance type.
	 * 
	 * @return the balance type
	 */
	public abstract long getBalanceType();
	
	/**
	 * Returns the details.
	 * 
	 * @return the details
	 */
	public abstract Set<T> getDetails();
	
	/**
	 * Tests if this is global expense.
	 * 
	 * @return true, if this is global expense
	 */
	public abstract boolean isGlobal();
	
	/**
	 * Tests if this object has same parameters as {@code another}.
	 * Parameters are tested by {@code getName(), getBalanceType(), getDetails(), isGlobal()}.
	 * 
	 * @param another the expense to be tested
	 * @return true, if this object has same parameters as {@code another}
	 */
	public abstract boolean equalsAsBase(final ExpenseBase<T> another);
	
	/**
	 * Tests if this object has same parameters excepted details as {@code another}.
	 * Parameters are tested by {@code getName(), getBalanceType(), isGlobal()}.
	 * 
	 * @param another the expense to be tested
	 * @return true, if this object has same parameters as {@code another}
	 */
	// public abstract boolean equalsAsBaseExceptDetails(final ExpenseBase<?> another);
}
