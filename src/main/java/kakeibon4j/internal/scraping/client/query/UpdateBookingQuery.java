package kakeibon4j.internal.scraping.client.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import kakeibon4j.entity.booking.UpdatedBooking;

import org.jsoup.helper.StringUtil;

public class UpdateBookingQuery extends BookingQuery<UpdatedBooking> {
	
	private static final String MODE = "M";
	
	public UpdateBookingQuery(final Collection<? extends UpdatedBooking> bookings) {
		super(bookings);
	}
	
	@Override
	protected String getBookingListQueryString(final Collection<? extends UpdatedBooking> bookings) {
		final List<String> queries = new ArrayList<String>();
		for (final UpdatedBooking booking : bookings) {
			queries.add("{"
					+ "mode:\"" + MODE + "\""
					+ ",booking_id:\"" + booking.getId() + "\""
					+ ",booking_type:\"" + booking.getDataType() + "\""
					+ ",date:\"" + getDateString(booking.getDate()) + "\""
					+ ",shop:\"" + (booking.getShop() != null ? booking.getShop() : EMPTY) + "\""
					+ ",item:\"" + booking.getItem() + "\""
					+ ",product:\"" + UNDEFINED + "\"" // 未対応
					+ ",unit_price:\"" + (booking.getUnitPrice() != null ? booking.getUnitPrice() : EMPTY) + "\""
					+ ",amount:\"" + (booking.getAmount() != null ? booking.getAmount() : EMPTY) + "\""
					+ ",sum:\"" + booking.getSum() + "\""
					+ ",settlement_way:\"" + (booking.getSettlementWay() != null ? booking.getSettlementWay() : EMPTY) + "\""
					+ ",expense_code:\"" + (booking.getExpense() != null ? booking.getExpense().getCode() : EMPTY) + "\""
					+ ",expense_name:\"" + (booking.getExpense() != null ? booking.getExpense().getName() : EMPTY) + "\""
					+ ",expense_balance_type:\"" + (booking.getExpense() != null ? booking.getExpense().getBalanceType() : "2") + "\""
					+ ",tag_list:[]" // 未対応(有料会員専用?)
					+ ",jan_code:\"" + UNDEFINED + "\"" // 未対応
					+ ",memo:\"" + (booking.getMemo() != null ? booking.getMemo() : EMPTY) + "\""
					+ ",totaling_flag:\"" + (booking.isTotaling() ? "1" : "0") + "\""
					+ ",expense_detail_code:\"" + (booking.getExpenseDetail() != null ? booking.getExpenseDetail().getCode() : EMPTY) + "\""
					+ ",expense_detail_name:\"" + (booking.getExpenseDetail() != null ? booking.getExpenseDetail().getName() : EMPTY) + "\""
					+ "}");
		}
		
		return StringUtil.join(queries, ", ");
	}
}
