package kakeibon4j.entity.booking;

import java.util.Date;

import kakeibon4j.entity.expense.ExpenseBase;
import kakeibon4j.entity.expense.detail.ExpenseDetailBase;

/**
 * An abstract class representing booking builder base.
 * 
 * @param <T> the booking's type to build
 * @param <U> the expense's type that booking has
 * @param <V> the expense detail's type that booking has
 * @param <S> the self type
 * @author ero3
 */
public abstract class AbstractBookingBuilderBase<T extends BookingBase<U, V>, U extends ExpenseBase<? extends ExpenseDetailBase>, V extends ExpenseDetailBase, S extends BookingBuilderBase<T, U, V, S>> implements BookingBuilderBase<T, U, V, S> {
	private Date date;
	private long sum;
	private String item;
	private U expense;
	private String shop;
	private String settlementWay;
	private Long unitPrice;
	private Long amount;
	private String memo;
	private boolean totalingFlag;
	private V expenseDetail;
	
	public AbstractBookingBuilderBase() {
		super();
		initialize();
	}
	
	public AbstractBookingBuilderBase(final T booking) {
		super();
		initialize();
		setBooking(booking);
	}
	
	@Override
	public S initialize() {
		this.date = null;
		this.sum = 0;
		this.item = null;
		this.expense = null;
		this.shop = null;
		this.settlementWay = null;
		this.unitPrice = null;
		this.amount = null;
		this.memo = null;
		this.totalingFlag = true;
		this.expenseDetail = null;
		return (S)this;
	}
	
	@Override
	public S setBooking(final T booking) {
		if (booking == null) {
			throw new IllegalArgumentException("Argument booking is null. Argument booking must be not null.");
		}
		setDate(booking.getDate());
		setSum(booking.getSum());
		setItem(booking.getItem());
		setExpense(booking.getExpense());
		setShop(booking.getShop());
		setSettlementWay(booking.getSettlementWay());
		setUnitPrice(booking.getUnitPrice());
		setAmount(booking.getAmount());
		setMemo(booking.getMemo());
		setTotalingFlag(booking.isTotaling());
		setExpenseDetail(booking.getExpenseDetail());
		return (S)this;
	}
	
	@Override
	public Date getDate() {
		return date;
	}
	
	@Override
	public S setDate(final Date date) {
		if (date == null) {
			this.date = null;
		} else {
			this.date = new Date(date.getTime());
		}
		return (S)this;
	}
	
	@Override
	public long getSum() {
		return sum;
	}
	
	@Override
	public S setSum(final long sum) {
		this.sum = sum;
		return (S)this;
	}
	
	@Override
	public String getItem() {
		return item;
	}
	
	@Override
	public S setItem(final String item) {
		if ("".equals(item)) {
			throw new IllegalArgumentException("Argument item is empty string. Argument item must be not empty string.");
		}
		this.item = item;
		return (S)this;
	}
	
	@Override
	public U getExpense() {
		return expense;
	}
	
	@Override
	public S setExpense(final U expense) {
		this.expense = expense;
		return (S)this;
	}
	
	@Override
	public String getShop() {
		return shop;
	}
	
	@Override
	public S setShop(final String shop) {
		if ("".equals(shop)) {
			throw new IllegalArgumentException("Argument shop is empty string. Argument shop must be not empty string.");
		}
		this.shop = shop;
		return (S)this;
	}
	
	@Override
	public String getSettlementWay() {
		return settlementWay;
	}
	
	@Override
	public S setSettlementWay(final String settlementWay) {
		if ("".equals(settlementWay)) {
			throw new IllegalArgumentException("Argument settlementWay is empty string. Argument settlementWay must be not empty string.");
		}
		this.settlementWay = settlementWay;
		return (S)this;
	}
	
	@Override
	public Long getUnitPrice() {
		return unitPrice;
	}
	
	@Override
	public S setUnitPrice(final Long unitPrice) {
		if (unitPrice != null && unitPrice <= 0) {
			throw new IllegalArgumentException("Argument unitPrice is " + unitPrice + ". Argument unitPrice must be positive long number.");
		}
		this.unitPrice = unitPrice;
		return (S)this;
	}
	
	@Override
	public Long getAmount() {
		return amount;
	}
	
	@Override
	public S setAmount(final Long amount) {
		if (amount != null && amount <= 0) {
			throw new IllegalArgumentException("Argument amount is " + amount + ". Argument amount must be positive long number.");
		}
		this.amount = amount;
		return (S)this;
	}
	
	@Override
	public String getMemo() {
		return memo;
	}
	
	@Override
	public S setMemo(final String memo) {
		if ("".equals(memo)) {
			throw new IllegalArgumentException("Argument memo is empty string. Argument memo must be not empty string.");
		}
		this.memo = memo;
		return (S)this;
	}
	
	@Override
	public boolean isTotaling() {
		return totalingFlag;
	}
	
	@Override
	public S setTotalingFlag(final boolean totalingFlag) {
		this.totalingFlag = totalingFlag;
		return (S)this;
	}
	
	@Override
	public V getExpenseDetail() {
		return expenseDetail;
	}
	
	@Override
	public S setExpenseDetail(final V expenseDetail) {
		this.expenseDetail = expenseDetail;
		return (S)this;
	}
}
