package kakeibon4j.entity.booking;

import java.util.Date;

import kakeibon4j.entity.expense.ExpenseBase;
import kakeibon4j.entity.expense.detail.ExpenseDetailBase;

/**
 * A interface representing booking builder base.
 * 
 * @param <T> the booking's type to build
 * @param <U> the expense's type that booking has
 * @param <V> the expense detail's type that booking has
 * @param <S> the self type
 * @author ero3
 */
public interface BookingBuilderBase<T extends BookingBase<U, V>, U extends ExpenseBase<? extends ExpenseDetailBase>, V extends ExpenseDetailBase, S extends BookingBuilderBase<T, U, V, S>> {
	
	/**
	 * Builds the booking.
	 * 
	 * @return the booking
	 */
	public abstract T build();
	
	/**
	 * Initialize build parameters.
	 * 
	 * @return self builder
	 */
	public abstract S initialize();
	
	/**
	 * Initialize build parameters by the booking's fields.
	 * 
	 * @param booking the booking
	 * @return self builder
	 * @throws IllegalArgumentException if {@code booking} is null
	 */
	public abstract S setBooking(final T booking);
	
	/**
	 * Returns the date.
	 * 
	 * @return the date
	 */
	public abstract Date getDate();
	
	/**
	 * Sets the date.
	 * 
	 * @param date the date
	 * @return self builder
	 */
	public abstract S setDate(final Date date);
	
	/**
	 * Returns the sum.
	 * 
	 * @return the sum
	 */
	public abstract long getSum();
	
	/**
	 * Sets the sum.
	 * 
	 * @param sum the sum
	 * @return self builder
	 */
	public abstract S setSum(final long sum);
	
	/**
	 * Returns the item.
	 * 
	 * @return the item
	 */
	public abstract String getItem();
	
	/**
	 * Sets the item.
	 * 
	 * @param item the item
	 * @return self builder
	 * @throws IllegalArgumentException if {@code item} is empty string
	 */
	public abstract S setItem(final String item);
	
	/**
	 * Returns the expense.
	 * 
	 * @return the expense
	 */
	public abstract U getExpense();
	
	/**
	 * Sets the expense.
	 * 
	 * @param expense the expense
	 * @return self builder
	 */
	public abstract S setExpense(final U expense);
	
	/**
	 * Returns the shop.
	 * 
	 * @return the shop
	 */
	public abstract String getShop();
	
	/**
	 * Sets the shop.
	 * 
	 * @param shop the shop
	 * @return self builder
	 * @throws IllegalArgumentException if {@code shop} is empty string
	 */
	public abstract S setShop(final String shop);
	
	/**
	 * Returns the settlement way.
	 * 
	 * @return the settlement way
	 */
	public abstract String getSettlementWay();
	
	/**
	 * Sets the settlement way.
	 * 
	 * @param settlementWay the settlement way
	 * @return self builder
	 * @throws IllegalArgumentException if {@code settlementWay} is empty string
	 */
	public abstract S setSettlementWay(final String settlementWay);
	
	/**
	 * Returns the unit price.
	 * 
	 * @return the unit price
	 */
	public abstract Long getUnitPrice();
	
	/**
	 * Sets the unit price.
	 * 
	 * @param unitPrice the unit price
	 * @return self builder
	 * @throws IllegalArgumentException if {@code unitPrice} is zero or negative long value
	 */
	public abstract S setUnitPrice(final Long unitPrice);
	
	/**
	 * Returns the amount.
	 * 
	 * @return the amount
	 */
	public abstract Long getAmount();
	
	/**
	 * Sets the amount.
	 * 
	 * @param amount the amount
	 * @return self builder
	 * @throws IllegalArgumentException if {@code ampount} is zero or negative long value
	 */
	public abstract S setAmount(final Long amount);
	
	/**
	 * Returns the memo.
	 * 
	 * @return the memo
	 */
	public abstract String getMemo();
	
	/**
	 * Sets the memo.
	 * 
	 * @param memo the memo
	 * @return self builder
	 * @throws IllegalArgumentException if {@code memo} is empty string
	 */
	public abstract S setMemo(final String memo);
	
	/**
	 * Tests if is totaling.
	 * 
	 * @return true, if is totaling
	 */
	public abstract boolean isTotaling();
	
	/**
	 * Sets the totaling flag.
	 * 
	 * @param totalingFlag the totaling flag
	 * @return self builder
	 */
	public abstract S setTotalingFlag(final boolean totalingFlag);
	
	/**
	 * Returns the expense detail.
	 * 
	 * @return the expense detail
	 */
	public abstract V getExpenseDetail();
	
	/**
	 * Sets the expense detail.
	 * 
	 * @param expenseDetail the expense detail
	 * @return self builder
	 */
	public abstract S setExpenseDetail(final V expenseDetail);
	
}
