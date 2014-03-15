package kakeibon4j.entity.booking;

import kakeibon4j.entity.expense.Expense;
import kakeibon4j.entity.expense.detail.ExpenseDetail;

/**
 * An abstract class representing booking builder.
 * 
 * @param <T> the booking's type to build
 * @param <S> the self type
 * @author ero3
 */
public abstract class AbstractBookingBuilder<T extends Booking, S extends BookingBuilder<T, S>> extends AbstractBookingBuilderBase<T, Expense<? extends ExpenseDetail>, ExpenseDetail, S> implements BookingBuilder<T, S> {
	
	private long id;
	private long dataType;
	private String cpId;
	private String cpName;
	private String aggrId;
	private boolean newFlag;
	private String accountKey;
	private String tankKey;
	private String shoKey;
	private String optionData1;
	private String optionData2;
	private String shoShubetuId;
	
	public AbstractBookingBuilder() {
		super();
	}
	
	public AbstractBookingBuilder(final T booking) {
		super(booking);
	}
	
	@Override
	public S initialize() {
		super.initialize();
		this.id = -1;
		this.dataType = 0;
		this.cpId = null;
		this.cpName = null;
		this.aggrId = null;
		this.newFlag = false;
		this.accountKey = null;
		this.tankKey = null;
		this.shoKey = null;
		this.optionData1 = null;
		this.optionData2 = null;
		this.shoShubetuId = null;
		return (S)this;
	}
	
	@Override
	public S setBooking(final T booking) {
		super.setBooking(booking);
		setId(booking.getId());
		setDataType(booking.getDataType());
		setCpId(booking.getCpId());
		setCpName(booking.getCpName());
		setAggrId(booking.getAggrId());
		setNewFlag(booking.isNew());
		setAccountKey(booking.getAccountKey());
		setTankKey(booking.getTankKey());
		setShoKey(booking.getShoKey());
		setOptionData1(booking.getOptionData1());
		setOptionData2(booking.getOptionData2());
		setShoShubetuId(booking.getShoShubetuId());
		return (S)this;
	}
	
	@Override
	public long getId() {
		return id;
	}
	
	// this method is not public because id is immutable.
	protected S setId(final long id) {
		this.id = id;
		return (S)this;
	}
	
	@Override
	public long getDataType() {
		return dataType;
	}
	
	// this method is not public because datatype is immutable.
	protected S setDataType(final long dataType) {
		this.dataType = dataType;
		return (S)this;
	}
	
	@Override
	public String getCpId() {
		return cpId;
	}
	
	// this method is not public because cpId is immutable.
	protected S setCpId(final String cpId) {
		this.cpId = cpId;
		return (S)this;
	}
	
	@Override
	public String getCpName() {
		return cpName;
	}
	
	// this method is not public because cpName is immutable.
	protected S setCpName(final String cpName) {
		this.cpName = cpName;
		return (S)this;
	}
	
	@Override
	public String getAggrId() {
		return aggrId;
	}
	
	// this method is not public because aggrId is immutable.
	protected S setAggrId(final String aggrId) {
		this.aggrId = aggrId;
		return (S)this;
	}
	
	@Override
	public boolean isNew() {
		return newFlag;
	}
	
	// this method is not public because newFlag is immutable.
	protected S setNewFlag(final boolean newFlag) {
		this.newFlag = newFlag;
		return (S)this;
	}
	
	@Override
	public String getAccountKey() {
		return accountKey;
	}
	
	// this method is not public because accountKey is immutable.
	protected S setAccountKey(final String accountKey) {
		this.accountKey = accountKey;
		return (S)this;
	}
	
	@Override
	public String getTankKey() {
		return tankKey;
	}
	
	// this method is not public because tankKey is immutable.
	protected S setTankKey(final String tankKey) {
		this.tankKey = tankKey;
		return (S)this;
	}
	
	@Override
	public String getShoKey() {
		return shoKey;
	}
	
	// this method is not public because shoKey is immutable.
	protected S setShoKey(final String shoKey) {
		this.shoKey = shoKey;
		return (S)this;
	}
	
	@Override
	public String getOptionData1() {
		return optionData1;
	}
	
	// this method is not public because optionData1 is immutable.
	protected S setOptionData1(final String optionData1) {
		this.optionData1 = optionData1;
		return (S)this;
	}
	
	@Override
	public String getOptionData2() {
		return optionData2;
	}
	
	// this method is not public because optionData2 is immutable.
	protected S setOptionData2(final String optionData2) {
		this.optionData2 = optionData2;
		return (S)this;
	}
	
	@Override
	public String getShoShubetuId() {
		return shoShubetuId;
	}
	
	// this method is not public because shoShubetuId is immutable.
	protected S setShoShubetuId(final String shoShubetuId) {
		this.shoShubetuId = shoShubetuId;
		return (S)this;
	}
	
}
