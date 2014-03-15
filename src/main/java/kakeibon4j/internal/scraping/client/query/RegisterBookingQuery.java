package kakeibon4j.internal.scraping.client.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import kakeibon4j.entity.booking.UnregisteredBooking;

import org.jsoup.helper.StringUtil;

public class RegisterBookingQuery extends BookingQuery<UnregisteredBooking> {
	
	private static final String MODE = "A";
	
	public RegisterBookingQuery(final Collection<? extends UnregisteredBooking> bookings) {
		super(bookings);
	}
	
	@Override
	protected String getBookingListQueryString(final Collection<? extends UnregisteredBooking> bookings) {
		final List<String> queries = new ArrayList<String>();
		for (final UnregisteredBooking booking : bookings) {
			queries.add("{"
					+ "mode:\"" + MODE + "\""
					+ ",booking_id:\"" + getTempId() + "\"" // 登録時は仮IDで登録(登録後にIDが割り振られる)
					+ ",booking_type:\"" + UNDEFINED + "\"" // 登録時は一律undefineｄ
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
					+ ",cp_name:\"" + EMPTY + "\"" // 登録時は一律空文字列(仮に文字列を指定しても反映されない)
					+ ",cp_id:\"" + EMPTY + "\"" // 登録時は一律空文字列(仮に文字列を指定しても反映されない)
					+ ",new_flag:\"" + "0" + "\""// 登録時は一律0(=false)(仮に1(=true)を指定しても反映されない)
					+ ",expense_detail_code:\"" + (booking.getExpenseDetail() != null ? booking.getExpenseDetail().getCode() : EMPTY) + "\""
					+ ",expense_detail_name:\"" + (booking.getExpenseDetail() != null ? booking.getExpenseDetail().getName() : EMPTY) + "\""
					+ "}");
		}
		
		return StringUtil.join(queries, ", ");
	}
	
	private int getTempId() {
		return (int)(Math.random() * 999);
	}
}
