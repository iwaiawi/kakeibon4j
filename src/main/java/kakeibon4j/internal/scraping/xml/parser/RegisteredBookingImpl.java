package kakeibon4j.internal.scraping.xml.parser;

import java.util.Date;

import kakeibon4j.entity.booking.AbstractBooking;
import kakeibon4j.entity.booking.RegisteredBooking;
import kakeibon4j.entity.expense.Expense;
import kakeibon4j.entity.expense.detail.ExpenseDetail;

/* package */class RegisteredBookingImpl extends AbstractBooking implements RegisteredBooking {
	
	/* package */RegisteredBookingImpl(
			final long id,
			final Date date,
			final long sum,
			final String item,
			final Expense<? extends ExpenseDetail> expense,
			final String shop,
			final String cpId,
			final String cpName,
			final String settlementWay,
			final Long unitPrice,
			final Long amount,
			final String memo,
			final boolean totalingFlag,
			final String aggrId,
			final long dataType,
			final boolean newFlag,
			final String accountKey,
			final String tankKey,
			final String shoKey,
			final String optionData1,
			final String optionData2,
			final String shoShubetuId,
			final ExpenseDetail expenseDetail) {
		super(
				id,
				date,
				sum,
				item,
				expense,
				shop,
				cpId,
				cpName,
				settlementWay,
				unitPrice,
				amount,
				memo,
				totalingFlag,
				aggrId,
				dataType,
				newFlag,
				accountKey,
				tankKey,
				shoKey,
				optionData1,
				optionData2,
				shoShubetuId,
				expenseDetail);
	}
}
