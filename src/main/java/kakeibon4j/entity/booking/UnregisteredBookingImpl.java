package kakeibon4j.entity.booking;

import java.util.Date;

import kakeibon4j.entity.expense.Expense;
import kakeibon4j.entity.expense.detail.ExpenseDetail;

/**
 * Default implementation of {@link UnregisteredBooking}.
 * 
 * @author ero3
 */
public class UnregisteredBookingImpl extends AbstractBookingBase<Expense<? extends ExpenseDetail>, ExpenseDetail> implements UnregisteredBooking {
	
	public UnregisteredBookingImpl(
			final Date date,
			final long sum,
			final String item,
			final Expense<? extends ExpenseDetail> expense,
			final String shop,
			final String settlementWay,
			final Long unitPrice,
			final Long amount,
			final String memo,
			final boolean totalingFlag,
			final ExpenseDetail expenseDetail) {
		super(
				date,
				sum,
				item,
				expense,
				shop,
				settlementWay,
				unitPrice,
				amount,
				memo,
				totalingFlag,
				expenseDetail);
	}
}
