package kakeibon4j;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import kakeibon4j.entity.Duration;
import kakeibon4j.entity.booking.Booking;
import kakeibon4j.entity.booking.UnregisteredBooking;
import kakeibon4j.entity.booking.UpdatedBooking;
import kakeibon4j.entity.expense.Expense;
import kakeibon4j.entity.expense.GlobalExpense;
import kakeibon4j.entity.expense.UnregisteredUserExpense;
import kakeibon4j.entity.expense.UserExpense;
import kakeibon4j.entity.expense.detail.ExpenseDetail;
import kakeibon4j.entity.expense.detail.UnregisteredUserExpenseDetail;
import kakeibon4j.entity.expense.detail.UserExpenseDetail;

/**
 * A java representation of the Kakeibon function.
 * 
 * @author ero3
 */
public interface Kakeibon extends java.io.Serializable {
	
	/**
	 * Returns set of all global expense.
	 * 
	 * @return set of all global expense
	 * @throws KakeibonException
	 */
	Set<GlobalExpense> getGlobalExpenses() throws KakeibonException;
	
	/**
	 * Returns set of all user expense.
	 * 
	 * @return set of all user expense
	 * @throws KakeibonException
	 */
	Set<UserExpense> getUserExpenses() throws KakeibonException;
	
	/**
	 * register an user expense.
	 * 
	 * @param expense Unregistered user expense.
	 * @return registered user expense
	 * @throws KakeibonException
	 */
	UserExpense registerUserExpense(final UnregisteredUserExpense expense) throws KakeibonException;
	
	/**
	 * register some user expenses.
	 * 
	 * @param expenses Collection of unregistered user expense.
	 * @return list of registered user expense in the same order as argument expenses
	 * @throws KakeibonException
	 */
	List<UserExpense> registerUserExpenses(final Collection<? extends UnregisteredUserExpense> expenses) throws KakeibonException;
	
	/**
	 * delete an user expenses.
	 * 
	 * @param expense registered user expense.
	 * @throws KakeibonException
	 */
	void deleteUserExpense(final UserExpense expense) throws KakeibonException;
	
	/**
	 * delete some user expenses.
	 * 
	 * @param expenses Collection of registered user expense.
	 * @throws KakeibonException
	 */
	void deleteUserExpenses(final Collection<? extends UserExpense> expenses) throws KakeibonException;
	
	/**
	 * register an user expense detail to global expense.
	 * 
	 * @param parent Target global expense for register an user expense detail
	 * @param detail Unregistered user expense detail
	 * @return global expense that registered an user expense detail
	 * @throws KakeibonException
	 */
	GlobalExpense registerUserExpenseDetail(final GlobalExpense parent, final UnregisteredUserExpenseDetail detail) throws KakeibonException;
	
	/**
	 * register some user expense detail to global expense.
	 * 
	 * @param parent Target global expense for register some user expense details
	 * @param details Collection of Unregistered user expense detail
	 * @return global expense that registered some user expense details
	 * @throws KakeibonException
	 */
	GlobalExpense registerUserExpenseDetails(final GlobalExpense parent, final Collection<? extends UnregisteredUserExpenseDetail> details) throws KakeibonException;
	
	/**
	 * register an user expense detail to user expense.
	 * 
	 * @param parent Target user expense for register an user expense detail
	 * @param detail Unregistered user expense detail
	 * @return user expense that registered an user expense detail
	 * @throws KakeibonException
	 */
	UserExpense registerUserExpenseDetail(final UserExpense parent, final UnregisteredUserExpenseDetail detail) throws KakeibonException;
	
	/**
	 * register some user expense detail to user expense.
	 * 
	 * @param parent Target user expense for register some user expense details
	 * @param details Collection of Unregistered user expense detail
	 * @return user expense that registered some user expense details
	 * @throws KakeibonException
	 */
	UserExpense registerUserExpenseDetails(final UserExpense parent, final Collection<? extends UnregisteredUserExpenseDetail> details) throws KakeibonException;
	
	/**
	 * delete an user expense detail from expense.
	 * 
	 * @param parent Target expense for delete an user expense
	 * @param detail Registered user expense detail
	 * @throws KakeibonException
	 */
	void deleteUserExpenseDetail(final Expense<? extends ExpenseDetail> parent, final UserExpenseDetail detail) throws KakeibonException;
	
	/**
	 * delete some user expense detail to expense.
	 * 
	 * @param parent Target expense for delete an user expense
	 * @param details Collection of registered user expense detail
	 * @throws KakeibonException
	 */
	void deleteUserExpenseDetails(final Expense<? extends ExpenseDetail> parent, final Collection<? extends UserExpenseDetail> details) throws KakeibonException;
	
	/**
	 * return list of booking for default duration.
	 * 
	 * @return list of booking for default duration
	 * @throws KakeibonException
	 */
	List<Booking> getBookingList() throws KakeibonException;
	
	/**
	 * return list of booking for specified duration.
	 * 
	 * @param duration Specified duration
	 * @return list of booking for specified duration.
	 * @throws KakeibonException
	 */
	List<Booking> getBookingList(final Duration duration) throws KakeibonException;
	
	/**
	 * Order reloading booking to Kakeibon.
	 * 
	 * @throws KakeibonException
	 */
	void reloadBooking() throws KakeibonException;
	
	/**
	 * register a booking.
	 * 
	 * @param booking Unregistered booking
	 * @return registered booking
	 * @throws KakeibonException
	 */
	Booking registerBooking(final UnregisteredBooking booking) throws KakeibonException;
	
	/**
	 * register some bookings.
	 * 
	 * @param bookings Collection of unregistered booking
	 * @return list of registered booking in the same order as argument bookings
	 * @throws KakeibonException
	 */
	List<Booking> registerBookings(final Collection<? extends UnregisteredBooking> bookings) throws KakeibonException;
	
	/**
	 * update a booking.
	 * 
	 * @param booking Updated booking
	 * @return registered booking
	 * @throws KakeibonException
	 */
	Booking updateBooking(final UpdatedBooking booking) throws KakeibonException;
	
	/**
	 * update some bookings.
	 * 
	 * @param bookings Collection of updated booking
	 * @return list of registered booking in the same order as argument bookings
	 * @throws KakeibonException
	 */
	List<Booking> updateBookings(final Collection<? extends UpdatedBooking> bookings) throws KakeibonException;
	
	/**
	 * delete a booking
	 * 
	 * @param booking Registered booking
	 * @throws KakeibonException
	 */
	void deleteBooking(final Booking booking) throws KakeibonException;
	
	/**
	 * delete some bookings
	 * 
	 * @param bookings Collection of registered booking
	 * @throws KakeibonException
	 */
	void deleteBookings(final Collection<? extends Booking> bookings) throws KakeibonException;
}
