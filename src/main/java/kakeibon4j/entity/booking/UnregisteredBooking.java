package kakeibon4j.entity.booking;

import kakeibon4j.entity.Registerable;
import kakeibon4j.entity.expense.Expense;
import kakeibon4j.entity.expense.detail.ExpenseDetail;

/**
 * A interface representing booking that not registered to Kakeibon.
 * 
 * @author ero3
 */
public interface UnregisteredBooking extends BookingBase<Expense<? extends ExpenseDetail>, ExpenseDetail>, Registerable.Unregistered {
}