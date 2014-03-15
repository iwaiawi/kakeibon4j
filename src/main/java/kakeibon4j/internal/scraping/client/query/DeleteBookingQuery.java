package kakeibon4j.internal.scraping.client.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import kakeibon4j.entity.booking.Booking;

import org.jsoup.helper.StringUtil;

public class DeleteBookingQuery extends BookingQuery<Booking> {
	
	private static final String MODE = "D";
	
	public DeleteBookingQuery(final Collection<? extends Booking> bookings) {
		super(bookings);
	}
	
	@Override
	protected String getBookingListQueryString(final Collection<? extends Booking> bookings) {
		final List<String> queries = new ArrayList<String>();
		for (final Booking booking : bookings) {
			queries.add("{"
					+ "mode:\"" + MODE + "\""
					+ ",booking_id:\"" + booking.getId() + "\""
					+ ",booking_type:\"" + booking.getDataType() + "\""
					+ "}");
		}
		
		return StringUtil.join(queries, ", ");
	}
}
