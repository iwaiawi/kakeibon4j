package kakeibon4j.internal.scraping.xml;

import java.util.Collection;
import java.util.List;

import kakeibon4j.KakeibonException;
import kakeibon4j.entity.booking.Booking;
import kakeibon4j.entity.expense.Expense;
import kakeibon4j.entity.expense.detail.ExpenseDetail;

public interface BookingXml extends NormalXml {
	
	public abstract List<Booking> getBookingList(final Collection<? extends Expense<? extends ExpenseDetail>> allExpenses) throws KakeibonException;
}
