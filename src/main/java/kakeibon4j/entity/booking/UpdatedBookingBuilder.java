package kakeibon4j.entity.booking;

/**
 * A interface representing builder of booking that registered and updated to Kakeibon.
 * 
 * @author ero3
 */
public interface UpdatedBookingBuilder extends BookingBuilder<UpdatedBooking, UpdatedBookingBuilder> {
	
	/**
	 * Initialize build parameters by the booking's fields.
	 * 
	 * @param booking the registered booking
	 * @return self builder
	 * @throws IllegalArgumentException if {@code updatedBooking} is null
	 */
	public abstract UpdatedBookingBuilder setBooking(final Booking booking);
}