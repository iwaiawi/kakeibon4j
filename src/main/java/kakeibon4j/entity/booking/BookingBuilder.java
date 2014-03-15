package kakeibon4j.entity.booking;

import kakeibon4j.entity.expense.Expense;
import kakeibon4j.entity.expense.detail.ExpenseDetail;

/**
 * A interface representing builder of booking that registered to Kakeibon.
 * 
 * @param <T> the booking's type to build
 * @param <S> the self type
 * @author ero3
 */
public interface BookingBuilder<T extends Booking, S extends BookingBuilder<T, S>> extends BookingBuilderBase<T, Expense<? extends ExpenseDetail>, ExpenseDetail, S> {
	
	/**
	 * Returns the id.
	 * 
	 * @return the id
	 */
	public abstract long getId();
	
	/**
	 * Returns the data type.
	 * 
	 * @return the data type
	 */
	public abstract long getDataType();
	
	/**
	 * Returns the contents provider id.
	 * 
	 * @return the contents provider id
	 */
	public abstract String getCpId();
	
	/**
	 * Returns the contents provider name.
	 * 
	 * @return the contents provider name
	 */
	public abstract String getCpName();
	
	/**
	 * Returns the aggregator id.
	 * 
	 * @return the aggregator id
	 */
	public abstract String getAggrId();
	
	/**
	 * Tests if is new.
	 * 
	 * @return true, if is new
	 */
	public abstract boolean isNew();
	
	/**
	 * Returns the account key.
	 * 
	 * @return the account key
	 */
	public abstract String getAccountKey();
	
	/**
	 * Returns the tank key.
	 * 
	 * @return the tank key
	 */
	public abstract String getTankKey();
	
	/**
	 * Returns the sho key.
	 * 
	 * @return the sho key
	 */
	public abstract String getShoKey();
	
	/**
	 * Returns the option data1.
	 * 
	 * @return the option data1
	 */
	public abstract String getOptionData1();
	
	/**
	 * Returns the option data2.
	 * 
	 * @return the option data2
	 */
	public abstract String getOptionData2();
	
	/**
	 * Returns the sho shubetu id.
	 * 
	 * @return the sho shubetu id
	 */
	public abstract String getShoShubetuId();
}