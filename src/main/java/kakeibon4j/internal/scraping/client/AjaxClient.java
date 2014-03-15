package kakeibon4j.internal.scraping.client;

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
import kakeibon4j.internal.scraping.xml.BookingXml;
import kakeibon4j.internal.scraping.xml.DeleteBookingXml;
import kakeibon4j.internal.scraping.xml.GlobalSettingXml;
import kakeibon4j.internal.scraping.xml.NormalXml;
import kakeibon4j.internal.scraping.xml.UpdateBookingXml;
import kakeibon4j.internal.scraping.xml.UserExpenseXml;

public interface AjaxClient {
	
	public abstract GlobalSettingXml getGlobalSetting(final AuthorizationContext context) throws KakeibonException;
	
	public abstract UserExpenseXml addUserExpenses(final Collection<? extends UnregisteredUserExpense> targets, final AuthorizationContext context) throws KakeibonException;
	
	public abstract UserExpenseXml deleteUserExpenses(final Collection<? extends UserExpense> expenses, final AuthorizationContext context) throws KakeibonException;
	
	public abstract UserExpenseXml addUserExpenseDetails(final Expense<? extends ExpenseDetail> parent, final Collection<? extends UnregisteredUserExpenseDetail> targets, final AuthorizationContext context) throws KakeibonException;
	
	public abstract UserExpenseXml deleteUserExpenseDetails(final Expense<? extends ExpenseDetail> parent, final Collection<? extends UserExpenseDetail> details, final AuthorizationContext context) throws KakeibonException;
	
	public abstract BookingXml getBookingList(final Duration duration, final AuthorizationContext context) throws KakeibonException;
	
	public abstract NormalXml reloadBooking(final AuthorizationContext context) throws KakeibonException;
	
	public abstract UpdateBookingXml addBookings(final Collection<? extends UnregisteredBooking> bookings, final AuthorizationContext context) throws KakeibonException;
	
	public abstract UpdateBookingXml updateBookings(final Collection<? extends UpdatedBooking> bookings, final AuthorizationContext context) throws KakeibonException;
	
	public abstract DeleteBookingXml deleteBookings(final Collection<? extends Booking> bookings, final AuthorizationContext context) throws KakeibonException;
}