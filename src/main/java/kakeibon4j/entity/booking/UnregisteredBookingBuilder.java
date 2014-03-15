package kakeibon4j.entity.booking;

import kakeibon4j.entity.expense.Expense;
import kakeibon4j.entity.expense.detail.ExpenseDetail;

/**
 * A interface representing builder of booking that not registered to Kakeibon.
 * 
 * @author ero3
 */
public interface UnregisteredBookingBuilder extends BookingBuilderBase<UnregisteredBooking, Expense<? extends ExpenseDetail>, ExpenseDetail, UnregisteredBookingBuilder> {
	
	/**
	 * Initialize build parameters by the booking's fields.
	 * 
	 * @param registeredBooking the registered booking
	 * @return self builder
	 * @throws IllegalArgumentException if {@code registeredBooking} is null
	 */
	public abstract UnregisteredBookingBuilder setBooking(final Booking registeredBooking);
	
	/**
	 * Initialize build parameters by the booking's fields.
	 * 
	 * @param updatedBooking the updated booking
	 * @return self builder
	 * @throws IllegalArgumentException if {@code updatedBooking} is null
	 */
	public abstract UnregisteredBookingBuilder setBooking(final UpdatedBooking updatedBooking);
}