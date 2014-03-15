package kakeibon4j.internal.scraping.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import kakeibon4j.KakeibonException;
import kakeibon4j.entity.booking.Booking;
import kakeibon4j.entity.expense.Expense;
import kakeibon4j.entity.expense.detail.ExpenseDetail;
import kakeibon4j.internal.scraping.xml.parser.BookingParser;

public class BookingXmlImpl extends NormalXmlImpl implements BookingXml {
	
	private static final String SCHEMA_FILE_NAME = "/BookingXml.xsd";
	
	public BookingXmlImpl(final InputStream schema, final String rawXml) throws IOException, KakeibonException {
		super(schema, rawXml);
	}
	
	public BookingXmlImpl(final String rawXml) throws IOException, KakeibonException {
		super(getSchemaInputStream(), rawXml);
	}
	
	@Override
	public List<Booking> getBookingList(final Collection<? extends Expense<? extends ExpenseDetail>> allExpenses) throws KakeibonException {
		return new BookingParser(allExpenses).parseBookingList(toJsoup());
	}
	
	private static InputStream getSchemaInputStream() {
		return BookingXmlImpl.class.getResourceAsStream(SCHEMA_FILE_NAME);
	}
}
