package kakeibon4j.entity.booking;

/**
 * Default implementation of {@link UpdatedBookingBuilder}.
 * 
 * @author ero3
 */
public class UpdatedBookingBuilderImpl extends AbstractBookingBuilder<UpdatedBooking, UpdatedBookingBuilder> implements UpdatedBookingBuilder {
	
	public UpdatedBookingBuilderImpl(final UpdatedBooking booking) {
		super(booking);
	}
	
	public UpdatedBookingBuilderImpl(final Booking booking) {
		super();
		setBooking(booking);
	}
	
	@Override
	public UpdatedBooking build() {
		return new UpdatedBookingImpl(
				getId(),
				getDate(),
				getSum(),
				getItem(),
				getExpense(),
				getShop(),
				getCpId(),
				getCpName(),
				getSettlementWay(),
				getUnitPrice(),
				getAmount(),
				getMemo(),
				isTotaling(),
				getAggrId(),
				getDataType(),
				isNew(),
				getAccountKey(),
				getTankKey(),
				getShoKey(),
				getOptionData1(),
				getOptionData2(),
				getShoShubetuId(),
				getExpenseDetail());
	}
	
	@Override
	public UpdatedBookingBuilder setBooking(final Booking booking) {
		setId(booking.getId());
		setDataType(booking.getDataType());
		setDate(booking.getDate());
		setSum(booking.getSum());
		setItem(booking.getItem());
		setExpense(booking.getExpense());
		setShop(booking.getShop());
		setCpId(booking.getCpId());
		setCpName(booking.getCpName());
		setSettlementWay(booking.getSettlementWay());
		setUnitPrice(booking.getUnitPrice());
		setAmount(booking.getAmount());
		setMemo(booking.getMemo());
		setTotalingFlag(booking.isTotaling());
		setAggrId(booking.getAggrId());
		setNewFlag(booking.isNew());
		setAccountKey(booking.getAccountKey());
		setTankKey(booking.getTankKey());
		setShoKey(booking.getShoKey());
		setOptionData1(booking.getOptionData1());
		setOptionData2(booking.getOptionData2());
		setShoShubetuId(booking.getShoShubetuId());
		setExpenseDetail(booking.getExpenseDetail());
		return this;
	}
}
