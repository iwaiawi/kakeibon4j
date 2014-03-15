package kakeibon4j.internal.scraping.xml.parser;

import kakeibon4j.entity.booking.AbstractBookingBuilder;
import kakeibon4j.entity.booking.RegisteredBooking;
import kakeibon4j.entity.booking.RegisteredBookingBuilder;

/* package */class RegisteredBookingBuilderImpl extends AbstractBookingBuilder<RegisteredBooking, RegisteredBookingBuilder> implements RegisteredBookingBuilder {
	
	/* package */RegisteredBookingBuilderImpl() {
		super();
	}
	
	/* package */RegisteredBookingBuilderImpl(final RegisteredBooking booking) {
		super(booking);
	}
	
	@Override
	public RegisteredBooking build() {
		return new RegisteredBookingImpl(
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
	public RegisteredBookingBuilder setId(final long id) {
		return super.setId(id);
	}
	
	@Override
	public RegisteredBookingBuilder setDataType(final long dataType) {
		return super.setDataType(dataType);
	}
	
	@Override
	public RegisteredBookingBuilder setCpId(final String cpId) {
		return super.setCpId(cpId);
	}
	
	@Override
	public RegisteredBookingBuilder setCpName(final String cpName) {
		return super.setCpName(cpName);
	}
	
	@Override
	public RegisteredBookingBuilder setAggrId(final String aggrId) {
		return super.setAggrId(aggrId);
	}
	
	@Override
	public RegisteredBookingBuilder setNewFlag(final boolean newFlag) {
		return super.setNewFlag(newFlag);
	}
	
	@Override
	public RegisteredBookingBuilder setAccountKey(final String accountKey) {
		return super.setAccountKey(accountKey);
	}
	
	@Override
	public RegisteredBookingBuilder setTankKey(final String tankKey) {
		return super.setTankKey(tankKey);
	}
	
	@Override
	public RegisteredBookingBuilder setShoKey(final String shoKey) {
		return super.setShoKey(shoKey);
	}
	
	@Override
	public RegisteredBookingBuilder setOptionData1(final String optionData1) {
		return super.setOptionData1(optionData1);
	}
	
	@Override
	public RegisteredBookingBuilder setOptionData2(final String optionData2) {
		return super.setOptionData2(optionData2);
	}
	
	@Override
	public RegisteredBookingBuilder setShoShubetuId(final String shoShubetuId) {
		return super.setShoShubetuId(shoShubetuId);
	}
	
}
