package kakeibon4j.entity.booking;

import java.util.Date;

import kakeibon4j.entity.Registerable;
import kakeibon4j.entity.expense.ExpenseBase;
import kakeibon4j.entity.expense.detail.ExpenseDetailBase;

/**
 * A interface representing booking base.
 * 
 * @param <T> the expense's type
 * @param <S> the expense detail's type
 * @author ero3
 */
public interface BookingBase<T extends ExpenseBase<? extends ExpenseDetailBase>, S extends ExpenseDetailBase> extends Comparable<BookingBase<T, S>>, Registerable {
	
	/**
	 * Returns the date.
	 * 
	 * @return the date
	 */
	public abstract Date getDate();
	
	/**
	 * Returns the sum.
	 * 
	 * @return the sum
	 */
	public abstract long getSum();
	
	/**
	 * Returns the item.
	 * 
	 * @return the item
	 */
	public abstract String getItem();
	
	/**
	 * Returns the expense.
	 * 
	 * @return the expense
	 */
	public abstract T getExpense();
	
	/**
	 * Returns the shop.
	 * 
	 * @return the shop
	 */
	public abstract String getShop();
	
	/**
	 * Returns the settlement way.
	 * 
	 * @return the settlement way
	 */
	public abstract String getSettlementWay();
	
	/**
	 * Returns the unit price.
	 * 
	 * @return the unit price
	 */
	public abstract Long getUnitPrice();
	
	/**
	 * Returns the amount.
	 * 
	 * @return the amount
	 */
	public abstract Long getAmount();
	
	/**
	 * Returns the memo.
	 * 
	 * @return the memo
	 */
	public abstract String getMemo();
	
	/**
	 * Tests if is totaling.
	 * 
	 * @return true, if is totaling
	 */
	public abstract boolean isTotaling();
	
	/**
	 * Returns the expense detail.
	 * 
	 * @return the expense detail
	 */
	public abstract S getExpenseDetail();
	
	/**
	 * Tests if this object has same values as {@code another}.
	 * Values are checked by {@link #getDate()}, {@link #getSum()}, {@link #getItem()}, {@link #getExpense()},
	 * {@link #getShop()}, {@link #getSettlementWay()}, {@link #getUnitPrice()}, {@link #getAmount()}, {@link #getMemo()},
	 * {@link #isTotaling()}, {@link #getExpenseDetail()}.
	 * 
	 * @param another the booking to be tested equivalence
	 * @return true, if this object has same values as {@code another}
	 */
	public abstract boolean equalsAsBookingBase(final BookingBase<T, S> another);
}
