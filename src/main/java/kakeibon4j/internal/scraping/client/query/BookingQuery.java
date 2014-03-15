package kakeibon4j.internal.scraping.client.query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import kakeibon4j.entity.booking.BookingBase;
import kakeibon4j.entity.expense.ExpenseBase;
import kakeibon4j.entity.expense.detail.ExpenseDetailBase;

public abstract class BookingQuery<T extends BookingBase<? extends ExpenseBase<? extends ExpenseDetailBase>, ? extends ExpenseDetailBase>> implements BatchQuery {
	
	protected static final String UNDEFINED = "undefined";
	protected static final String EMPTY = "";
	
	private final List<T> bookings;
	
	public BookingQuery(final Collection<? extends T> bookings) {
		if (bookings == null || bookings.isEmpty()) {
			throw new IllegalStateException("Argument bookings is null or empty list. Argument bookings is required.");
		}
		
		this.bookings = new ArrayList<T>(bookings);
	}
	
	@Override
	public String toQueryString() {
		return "{booking_list:[" + getBookingListQueryString(bookings) + "]}";
	}
	
	protected abstract String getBookingListQueryString(final Collection<? extends T> bookings);
	
	protected String getDateString(final Date date) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		return String.format(
				"%04d%02d%02d",
				calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH) + 1,
				calendar.get(Calendar.DATE)
				);
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "[bookings=" + bookings + "]";
	}
}