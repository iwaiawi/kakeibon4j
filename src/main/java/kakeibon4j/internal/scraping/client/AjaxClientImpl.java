package kakeibon4j.internal.scraping.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;

import kakeibon4j.KakeibonException;
import kakeibon4j.entity.Duration;
import kakeibon4j.entity.booking.Booking;
import kakeibon4j.entity.booking.UnregisteredBooking;
import kakeibon4j.entity.booking.UpdatedBooking;
import kakeibon4j.entity.expense.Expense;
import kakeibon4j.entity.expense.UnregisteredUserExpense;
import kakeibon4j.entity.expense.UserExpense;
import kakeibon4j.entity.expense.detail.ExpenseDetail;
import kakeibon4j.entity.expense.detail.UnregisteredUserExpenseDetail;
import kakeibon4j.entity.expense.detail.UserExpenseDetail;
import kakeibon4j.internal.scraping.auth.AuthorizationContext;
import kakeibon4j.internal.scraping.client.query.RegisterBookingQuery;
import kakeibon4j.internal.scraping.client.query.RegisterUserExpenseDetailQuery;
import kakeibon4j.internal.scraping.client.query.RegisterUserExpenseQuery;
import kakeibon4j.internal.scraping.client.query.DeleteBookingQuery;
import kakeibon4j.internal.scraping.client.query.DeleteUserExpenseDetailQuery;
import kakeibon4j.internal.scraping.client.query.DeleteUserExpenseQuery;
import kakeibon4j.internal.scraping.client.query.DurationQuery;
import kakeibon4j.internal.scraping.client.query.UpdateBookingQuery;
import kakeibon4j.internal.scraping.conf.ScrapingConfiguration;
import kakeibon4j.internal.scraping.xml.BookingXml;
import kakeibon4j.internal.scraping.xml.BookingXmlImpl;
import kakeibon4j.internal.scraping.xml.DeleteBookingXml;
import kakeibon4j.internal.scraping.xml.DeleteBookingXmlImpl;
import kakeibon4j.internal.scraping.xml.GlobalSettingXml;
import kakeibon4j.internal.scraping.xml.GlobalSettingXmlImpl;
import kakeibon4j.internal.scraping.xml.NormalXml;
import kakeibon4j.internal.scraping.xml.NormalXmlImpl;
import kakeibon4j.internal.scraping.xml.UpdateBookingXml;
import kakeibon4j.internal.scraping.xml.UpdateBookingXmlImpl;
import kakeibon4j.internal.scraping.xml.UserExpenseXml;
import kakeibon4j.internal.scraping.xml.UserExpenseXmlImpl;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

public class AjaxClientImpl extends ScrapingClientBase implements AjaxClient {
	
	private static final String PROCESS_CODE = "processCode";
	private static final String AAUUID = "aauuid";
	private static final String FC_CD = "fc_cd";
	private static final String BATCHQUERY = "batchquery";
	
	public AjaxClientImpl(final ScrapingConfiguration conf) {
		super(conf);
	}
	
	@Override
	public GlobalSettingXml getGlobalSetting(final AuthorizationContext context) throws KakeibonException {
		if (context == null) {
			throw new IllegalArgumentException("Argument authorizationContext is null. Argument authorizationContext is required.");
		}
		
		final HttpPost req = new HttpPost(getDefaultUrl());
		
		try {
			req.setEntity(new UrlEncodedFormEntity(
					Arrays.<NameValuePair>asList(
							new BasicNameValuePair(PROCESS_CODE, "13BookingAjax"),
							new BasicNameValuePair(AAUUID, context.getAauuId())
							)
					));
		} catch (final UnsupportedEncodingException e) {
			throw new KakeibonException(e);
		}
		
		try {
			return new GlobalSettingXmlImpl(simpleExecuteRequest(req, context.getHttpClientContext()));
		} catch (final ParseException e) {
			throw new KakeibonException(e);
		} catch (final IOException e) {
			throw new KakeibonException(e);
		}
	}
	
	@Override
	public UserExpenseXml addUserExpenses(final Collection<? extends UnregisteredUserExpense> expenses, final AuthorizationContext context) throws KakeibonException {
		if (context == null) {
			throw new IllegalArgumentException("Argument authorizationContext is null. Argument authorizationContext is required.");
		}
		
		final HttpPost req = new HttpPost(getDefaultUrl());
		
		try {
			req.setEntity(new UrlEncodedFormEntity(
					Arrays.<NameValuePair>asList(
							new BasicNameValuePair(PROCESS_CODE, "51Expense"),
							new BasicNameValuePair(AAUUID, context.getAauuId()),
							new BasicNameValuePair(FC_CD, "2"),
							new BasicNameValuePair(BATCHQUERY, new RegisterUserExpenseQuery(expenses).toQueryString())
							),
					"UTF-8"
					));
		} catch (final UnsupportedEncodingException e) {
			throw new KakeibonException(e);
		}
		
		try {
			return new UserExpenseXmlImpl(simpleExecuteRequest(req, context.getHttpClientContext()));
		} catch (final ParseException e) {
			throw new KakeibonException(e);
		} catch (final IOException e) {
			throw new KakeibonException(e);
		}
	}
	
	@Override
	public UserExpenseXml deleteUserExpenses(final Collection<? extends UserExpense> expenses, final AuthorizationContext context) throws KakeibonException {
		if (context == null) {
			throw new IllegalArgumentException("Argument authorizationContext is null. Argument authorizationContext is required.");
		}
		
		final HttpPost req = new HttpPost(getDefaultUrl());
		
		try {
			req.setEntity(new UrlEncodedFormEntity(
					Arrays.<NameValuePair>asList(
							new BasicNameValuePair(PROCESS_CODE, "51Expense"),
							new BasicNameValuePair(AAUUID, context.getAauuId()),
							new BasicNameValuePair(FC_CD, "2"),
							new BasicNameValuePair(BATCHQUERY, new DeleteUserExpenseQuery(expenses).toQueryString())
							),
					"UTF-8"
					));
		} catch (final UnsupportedEncodingException e) {
			throw new KakeibonException(e);
		}
		
		try {
			return new UserExpenseXmlImpl(simpleExecuteRequest(req, context.getHttpClientContext()));
		} catch (final ParseException e) {
			throw new KakeibonException(e);
		} catch (final IOException e) {
			throw new KakeibonException(e);
		}
	}
	
	@Override
	public UserExpenseXml addUserExpenseDetails(final Expense<? extends ExpenseDetail> parent, final Collection<? extends UnregisteredUserExpenseDetail> details, final AuthorizationContext context) throws KakeibonException {
		if (context == null) {
			throw new IllegalArgumentException("Argument authorizationContext is null. Argument authorizationContext is required.");
		}
		
		final HttpPost req = new HttpPost(getDefaultUrl());
		
		try {
			req.setEntity(new UrlEncodedFormEntity(
					Arrays.<NameValuePair>asList(
							new BasicNameValuePair(PROCESS_CODE, "51Expense"),
							new BasicNameValuePair(AAUUID, context.getAauuId()),
							new BasicNameValuePair(FC_CD, "2"),
							new BasicNameValuePair(BATCHQUERY, new RegisterUserExpenseDetailQuery(parent, details).toQueryString())
							),
					"UTF-8"
					));
		} catch (final UnsupportedEncodingException e) {
			throw new KakeibonException(e);
		}
		
		try {
			return new UserExpenseXmlImpl(simpleExecuteRequest(req, context.getHttpClientContext()));
		} catch (final ParseException e) {
			throw new KakeibonException(e);
		} catch (final IOException e) {
			throw new KakeibonException(e);
		}
	}
	
	@Override
	public UserExpenseXml deleteUserExpenseDetails(final Expense<? extends ExpenseDetail> parent, final Collection<? extends UserExpenseDetail> details, final AuthorizationContext context) throws KakeibonException {
		if (context == null) {
			throw new IllegalArgumentException("Argument authorizationContext is null. Argument authorizationContext is required.");
		}
		
		final HttpPost req = new HttpPost(getDefaultUrl());
		
		try {
			req.setEntity(new UrlEncodedFormEntity(
					Arrays.<NameValuePair>asList(
							new BasicNameValuePair(PROCESS_CODE, "51Expense"),
							new BasicNameValuePair(AAUUID, context.getAauuId()),
							new BasicNameValuePair(FC_CD, "2"),
							new BasicNameValuePair(BATCHQUERY, new DeleteUserExpenseDetailQuery(parent, details).toQueryString())
							),
					"UTF-8"
					));
		} catch (final UnsupportedEncodingException e) {
			throw new KakeibonException(e);
		}
		
		try {
			return new UserExpenseXmlImpl(simpleExecuteRequest(req, context.getHttpClientContext()));
		} catch (final ParseException e) {
			throw new KakeibonException(e);
		} catch (final IOException e) {
			throw new KakeibonException(e);
		}
	}
	
	@Override
	public BookingXml getBookingList(final Duration duration, final AuthorizationContext context) throws KakeibonException {
		if (context == null) {
			throw new IllegalArgumentException("Argument authorizationContext is null. Argument authorizationContext is required.");
		}
		
		final HttpPost req = new HttpPost(getDefaultUrl());
		
		try {
			req.setEntity(new UrlEncodedFormEntity(
					Arrays.<NameValuePair>asList(
							new BasicNameValuePair(PROCESS_CODE, "51Booking"),
							new BasicNameValuePair(AAUUID, context.getAauuId()),
							new BasicNameValuePair(FC_CD, "1"),
							new BasicNameValuePair(BATCHQUERY, new DurationQuery(duration).toQueryString())
							)
					));
		} catch (final UnsupportedEncodingException e) {
			throw new KakeibonException(e);
		}
		
		try {
			return new BookingXmlImpl(simpleExecuteRequest(req, context.getHttpClientContext()));
		} catch (final ParseException e) {
			throw new KakeibonException(e);
		} catch (final IOException e) {
			throw new KakeibonException(e);
		}
	}
	
	@Override
	public NormalXml reloadBooking(final AuthorizationContext context) throws KakeibonException {
		if (context == null) {
			throw new IllegalArgumentException("Argument authorizationContext is null. Argument authorizationContext is required.");
		}
		
		final HttpPost req = new HttpPost(getDefaultUrl());
		
		try {
			req.setEntity(new UrlEncodedFormEntity(
					Arrays.<NameValuePair>asList(
							new BasicNameValuePair(PROCESS_CODE, "14AllUpdateAjax"),
							new BasicNameValuePair(AAUUID, context.getAauuId())
							)
					));
		} catch (final UnsupportedEncodingException e) {
			throw new KakeibonException(e);
		}
		
		try {
			return new NormalXmlImpl(simpleExecuteRequest(req, context.getHttpClientContext()));
		} catch (final ParseException e) {
			throw new KakeibonException(e);
		} catch (final IOException e) {
			throw new KakeibonException(e);
		}
	}
	
	@Override
	public UpdateBookingXml addBookings(final Collection<? extends UnregisteredBooking> bookings, final AuthorizationContext context) throws KakeibonException {
		if (context == null) {
			throw new IllegalArgumentException("Argument authorizationContext is null. Argument authorizationContext is required.");
		}
		
		final HttpPost req = new HttpPost(getDefaultUrl());
		
		try {
			req.setEntity(new UrlEncodedFormEntity(
					Arrays.<NameValuePair>asList(
							new BasicNameValuePair(PROCESS_CODE, "51Booking"),
							new BasicNameValuePair(AAUUID, context.getAauuId()),
							new BasicNameValuePair(FC_CD, "2"),
							new BasicNameValuePair(BATCHQUERY, new RegisterBookingQuery(bookings).toQueryString())
							),
					"UTF-8"
					));
		} catch (final UnsupportedEncodingException e) {
			throw new KakeibonException(e);
		}
		
		try {
			return new UpdateBookingXmlImpl(simpleExecuteRequest(req, context.getHttpClientContext()));
		} catch (final ParseException e) {
			throw new KakeibonException(e);
		} catch (final IOException e) {
			throw new KakeibonException(e);
		}
	}
	
	@Override
	public UpdateBookingXml updateBookings(final Collection<? extends UpdatedBooking> bookings, final AuthorizationContext context) throws KakeibonException {
		if (context == null) {
			throw new IllegalArgumentException("Argument authorizationContext is null. Argument authorizationContext is required.");
		}
		
		final HttpPost req = new HttpPost(getDefaultUrl());
		
		try {
			req.setEntity(new UrlEncodedFormEntity(
					Arrays.<NameValuePair>asList(
							new BasicNameValuePair(PROCESS_CODE, "51Booking"),
							new BasicNameValuePair(AAUUID, context.getAauuId()),
							new BasicNameValuePair(FC_CD, "2"),
							new BasicNameValuePair(BATCHQUERY, new UpdateBookingQuery(bookings).toQueryString())
							),
					"UTF-8"
					));
		} catch (final UnsupportedEncodingException e) {
			throw new KakeibonException(e);
		}
		
		try {
			return new UpdateBookingXmlImpl(simpleExecuteRequest(req, context.getHttpClientContext()));
		} catch (final ParseException e) {
			throw new KakeibonException(e);
		} catch (final IOException e) {
			throw new KakeibonException(e);
		}
	}
	
	@Override
	public DeleteBookingXml deleteBookings(final Collection<? extends Booking> bookings, final AuthorizationContext context) throws KakeibonException {
		if (context == null) {
			throw new IllegalArgumentException("Argument authorizationContext is null. Argument authorizationContext is required.");
		}
		
		final HttpPost req = new HttpPost(getDefaultUrl());
		
		try {
			req.setEntity(new UrlEncodedFormEntity(
					Arrays.<NameValuePair>asList(
							new BasicNameValuePair(PROCESS_CODE, "51Booking"),
							new BasicNameValuePair(AAUUID, context.getAauuId()),
							new BasicNameValuePair(FC_CD, "2"),
							new BasicNameValuePair(BATCHQUERY, new DeleteBookingQuery(bookings).toQueryString())
							),
					"UTF-8"
					));
		} catch (final UnsupportedEncodingException e) {
			throw new KakeibonException(e);
		}
		
		try {
			return new DeleteBookingXmlImpl(simpleExecuteRequest(req, context.getHttpClientContext()));
		} catch (final ParseException e) {
			throw new KakeibonException(e);
		} catch (final IOException e) {
			throw new KakeibonException(e);
		}
	}
	
	private String getDefaultUrl() {
		return "https://app.kakeibo.ocn.ne.jp/ocn/aas?t=" + System.currentTimeMillis();
	}
}
