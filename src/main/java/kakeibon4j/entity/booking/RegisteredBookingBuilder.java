package kakeibon4j.entity.booking;

/**
 * A interface representing builder of booking that registered and not updated to Kakeibon.
 * 
 * @author ero3
 */
public interface RegisteredBookingBuilder extends BookingBuilder<RegisteredBooking, RegisteredBookingBuilder> {
	
	/**
	 * Sets the id.
	 * 
	 * @param id the id
	 * @return self builder
	 */
	public abstract RegisteredBookingBuilder setId(final long id);
	
	/**
	 * Sets the data type.
	 * 
	 * @param dataType the data type
	 * @return self builder
	 */
	public abstract RegisteredBookingBuilder setDataType(final long dataType);
	
	/**
	 * Sets the contents provider id.
	 * 
	 * @param cpId the contents provider id
	 * @return self builder
	 */
	public abstract RegisteredBookingBuilder setCpId(final String cpId);
	
	/**
	 * Sets the contents provider name.
	 * 
	 * @param cpName the contents provider name
	 * @return self builder
	 */
	public abstract RegisteredBookingBuilder setCpName(final String cpName);
	
	/**
	 * Sets the aggregator id.
	 * 
	 * @param aggrId the aggregator id
	 * @return self builder
	 */
	public abstract RegisteredBookingBuilder setAggrId(final String aggrId);
	
	/**
	 * Sets the new flag.
	 * 
	 * @param newFlag the new flag
	 * @return self builder
	 */
	public abstract RegisteredBookingBuilder setNewFlag(final boolean newFlag);
	
	/**
	 * Sets the account key.
	 * 
	 * @param accountKey the account key
	 * @return self builder
	 */
	public abstract RegisteredBookingBuilder setAccountKey(final String accountKey);
	
	/**
	 * Sets the tank key.
	 * 
	 * @param tankKey the tank key
	 * @return self builder
	 */
	public abstract RegisteredBookingBuilder setTankKey(final String tankKey);
	
	/**
	 * Sets the shop key.
	 * 
	 * @param shoKey the shop key
	 * @return self builder
	 */
	public abstract RegisteredBookingBuilder setShoKey(final String shoKey);
	
	/**
	 * Sets the option data1.
	 * 
	 * @param optionData1 the option data1
	 * @return self builder
	 */
	public abstract RegisteredBookingBuilder setOptionData1(final String optionData1);
	
	/**
	 * Sets the option data2.
	 * 
	 * @param optionData2 the option data2
	 * @return self builder
	 */
	public abstract RegisteredBookingBuilder setOptionData2(final String optionData2);
	
	/**
	 * Sets the sho shubetu id.
	 * 
	 * @param shoShubetuId the sho shubetu id
	 * @return self builder
	 */
	public abstract RegisteredBookingBuilder setShoShubetuId(final String shoShubetuId);
}