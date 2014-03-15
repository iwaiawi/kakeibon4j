package kakeibon4j.internal.scraping.xml.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import kakeibon4j.KakeibonException;
import kakeibon4j.entity.booking.Booking;
import kakeibon4j.entity.booking.RegisteredBookingBuilder;
import kakeibon4j.entity.expense.Expense;
import kakeibon4j.entity.expense.ExpenseSet;
import kakeibon4j.entity.expense.detail.ExpenseDetail;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BookingParser extends NormalParser {
	
	private final Set<Expense<? extends ExpenseDetail>> expenses;
	
	public BookingParser(final Collection<? extends Expense<? extends ExpenseDetail>> allExpenses) {
		this.expenses = new ExpenseSet<Expense<? extends ExpenseDetail>>(allExpenses);
	}
	
	public List<Booking> parseBookingList(final Document xmlDoc) throws KakeibonException {
		final Elements bookings = xmlDoc.select(getBookingQuery());
		if (bookings.size() == 0) {
			return Collections.emptyList();
		}
		
		final List<Booking> list = new ArrayList<Booking>();
		for (final Element booking : bookings) {
			list.add(parseBooking(booking));
		}
		return list;
	}
	
	private String getBookingQuery() {
		return "booking_result booking_list booking";
	}
	
	private Booking parseBooking(final Element booking) throws KakeibonException {
		final Element id = booking.select("booking_id").first(); // mandatory
		final Element date = booking.select("date").first(); // mandatory
		final Element sum = booking.select("sum").first(); // mandatory
		final Element item = booking.select("item").first(); // mandatory
		final Element expenseCode = booking.select("expense_code").first(); // mandatory
		final Element expenseName = booking.select("expense_name").first(); // mandatory
		final Element balanceType = booking.select("balance_type").first(); // mandatory
		final Element shop = booking.select("shop").first(); // mandatory
		final Element cpId = booking.select("cp_id").first(); // mandatory
		final Element cpName = booking.select("cp_name").first(); // mandatory
		final Element settlementWay = booking.select("settlement_way").first(); // mandatory
		final Element unitPrice = booking.select("unit_price").first(); // mandatory
		final Element amount = booking.select("amount").first(); // mandatory
		final Element memo = booking.select("memo").first(); // mandatory
		final Element totalingFlag = booking.select("totaling_flg").first(); // mandatory
		final Element aggrId = booking.select("aggr_id").first(); // mandatory
		final Element dataType = booking.select("dataType").first(); // mandatory
		final Element newFlag = booking.select("newFlg").first(); // mandatory
		final Element accountKey = booking.select("account_key").first(); // mandatory
		final Element tankKey = booking.select("tank_key").first(); // mandatory
		final Element shoKey = booking.select("sho_key").first(); // mandatory
		final Element optionData1 = booking.select("option_data_1").first(); // optional
		final Element optionData2 = booking.select("option_data_2").first(); // optional
		final Element shoShubetuId = booking.select("sho_syubetu_id").first(); // mandatory
		final Element expenseDetailCode = booking.select("expense_detail_code").first(); // optional
		final Element expenseDetailName = booking.select("expense_detail_name").first(); // optional
		
		if (id == null
				|| date == null
				|| sum == null
				|| item == null
				|| expenseCode == null
				|| expenseName == null
				|| balanceType == null
				|| shop == null
				|| cpId == null
				|| cpName == null
				|| settlementWay == null
				|| unitPrice == null
				|| amount == null
				|| memo == null
				|| totalingFlag == null
				|| aggrId == null
				|| dataType == null
				|| newFlag == null
				|| accountKey == null
				|| tankKey == null
				|| shoKey == null
				|| shoShubetuId == null) {
			throw new IllegalArgumentException("Argument " + booking + " must contains some mandatory tags.");
		}
		
		final RegisteredBookingBuilder builder = new RegisteredBookingBuilderImpl()
				.setId(parseLong(id.ownText()))
				.setDate(parseDate(date.ownText()))
				.setSum(parseLongValue(sum.ownText()))
				.setItem(parseString(item.ownText()))
				.setShop(parseString(shop.ownText()))
				.setCpId(parseString(cpId.ownText()))
				.setCpName(parseString(cpName.ownText()))
				.setSettlementWay(parseString(settlementWay.ownText()))
				.setUnitPrice(parseLong(unitPrice.ownText()))
				.setAmount(parseLong(amount.ownText()))
				.setMemo(parseString(memo.ownText()))
				.setTotalingFlag(parseBooleanValue(totalingFlag.ownText()))
				.setAggrId(parseString(aggrId.ownText()))
				.setDataType(parseLong(dataType.ownText()))
				.setNewFlag(parseBooleanValue(newFlag.ownText()))
				.setAccountKey(parseString(accountKey.ownText()))
				.setTankKey(parseString(tankKey.ownText()))
				.setShoKey(parseString(shoKey.ownText()))
				.setShoShubetuId(parseString(shoShubetuId.ownText()));
		
		if (!"".equals(expenseCode.ownText())) {
			builder.setExpense(getExpense(parseString(expenseCode.ownText()), parseString(expenseName.ownText()), parseLong(balanceType.ownText())));
		}
		if (optionData1 != null) {
			builder.setOptionData1(parseString(optionData1.ownText()));
		}
		if (optionData2 != null) {
			builder.setOptionData2(parseString(optionData2.ownText()));
		}
		if (!"".equals(expenseCode.ownText()) && expenseDetailCode != null && !"".equals(expenseDetailCode.ownText())) {
			builder.setExpenseDetail(getExpenseDetail(parseString(expenseDetailCode.ownText()), parseString(expenseDetailName.ownText())));
		}
		
		return builder.build();
	}
	
	private Expense<? extends ExpenseDetail> getExpense(final String code, final String name, final long balanceType) {
		if (code == null) {
			return null;
		}
		
		for (final Expense<? extends ExpenseDetail> expense : expenses) {
			if (expense.getCode().equals(code)) {
				if ((name == null || expense.getName().equals(name)) && expense.getBalanceType() == balanceType) {
					return expense;
				} else {
					throw new IllegalStateException(expense + " should be name:" + name + " and balanceType:" + balanceType + ".");
				}
			}
		}
		
		return null;
	}
	
	private ExpenseDetail getExpenseDetail(final String code, final String name) {
		if (code == null) {
			return null;
		}
		
		for (final Expense<? extends ExpenseDetail> expense : expenses) {
			for (final ExpenseDetail detail : expense.getDetails()) {
				if (detail.getCode().equals(code)) {
					if (name == null || detail.getName().equals(name)) {
						return detail;
					} else {
						throw new IllegalStateException(detail + " should be name:" + name + ".");
					}
				}
			}
		}
		
		return null;
	}
}
