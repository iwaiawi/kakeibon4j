package kakeibon4j.entity.booking;

import kakeibon4j.entity.expense.Expense;
import kakeibon4j.entity.expense.detail.ExpenseDetail;

/**
 * Default implementation of {@link UnregisteredBookingBuilder}.
 * 
 * @author ero3
 */
public class UnregisteredBookingBuilderImpl extends AbstractBookingBuilderBase<UnregisteredBooking, Expense<? extends ExpenseDetail>, ExpenseDetail, UnregisteredBookingBuilder> implements UnregisteredBookingBuilder {
	
	public UnregisteredBookingBuilderImpl() {
		super();
	}
	
	public UnregisteredBookingBuilderImpl(final Booking registeredBooking) {
		super();
		setBooking(registeredBooking);
	}
	
	public UnregisteredBookingBuilderImpl(final UpdatedBooking updatedBooking) {
		super();
		setBooking(updatedBooking);
	}
	
	public UnregisteredBookingBuilderImpl(final UnregisteredBooking booking) {
		super(booking);
	}
	
	@Override
	public UnregisteredBooking build() {
		return new UnregisteredBookingImpl(
				getDate(),
				getSum(),
				getItem(),
				getExpense(),
				getShop(),
				getSettlementWay(),
				getUnitPrice(),
				getAmount(),
				getMemo(),
				isTotaling(),
				getExpenseDetail());
	}
	
	@Override
	public UnregisteredBookingBuilder setBooking(final Booking registeredBooking) {
		setDate(registeredBooking.getDate());
		setSum(registeredBooking.getSum());
		setItem(registeredBooking.getItem());
		setExpense(registeredBooking.getExpense());
		setShop(registeredBooking.getShop());
		setSettlementWay(registeredBooking.getSettlementWay());
		setUnitPrice(registeredBooking.getUnitPrice());
		setAmount(registeredBooking.getAmount());
		setMemo(registeredBooking.getMemo());
		setTotalingFlag(registeredBooking.isTotaling());
		setExpenseDetail(registeredBooking.getExpenseDetail());
		return this;
	}
	
	@Override
	public UnregisteredBookingBuilder setBooking(final UpdatedBooking updatedBooking) {
		setDate(updatedBooking.getDate());
		setSum(updatedBooking.getSum());
		setItem(updatedBooking.getItem());
		setExpense(updatedBooking.getExpense());
		setShop(updatedBooking.getShop());
		setSettlementWay(updatedBooking.getSettlementWay());
		setUnitPrice(updatedBooking.getUnitPrice());
		setAmount(updatedBooking.getAmount());
		setMemo(updatedBooking.getMemo());
		setTotalingFlag(updatedBooking.isTotaling());
		setExpenseDetail(updatedBooking.getExpenseDetail());
		return this;
	}
}
