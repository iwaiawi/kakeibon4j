package kakeibon4j.entity.booking;

import java.util.Date;

import kakeibon4j.entity.expense.Expense;
import kakeibon4j.entity.expense.detail.ExpenseDetail;

/**
 * An abstract class representing booking that registered to Kakeibon.
 * 
 * @author ero3
 */
public abstract class AbstractBooking extends AbstractBookingBase<Expense<? extends ExpenseDetail>, ExpenseDetail> implements Booking {
	
	/**
	 * 識別子ID
	 * 必須項目
	 */
	private final long id;
	
	/**
	 * 用途不明？(0or1？)
	 */
	private final long dataType;
	
	/**
	 * 情報取得元識別子
	 * nullable
	 * 初期値設定可能⇒ただし反映されない
	 * 変更不可
	 */
	private final String cpId;
	
	/**
	 * 情報取得元名称
	 * nullable
	 * 初期値設定可能⇒ただし反映されない
	 * 変更不可
	 */
	private final String cpName;
	
	/**
	 * 情報取得元の明細識別子？
	 * nullable
	 */
	private final String aggrId;
	
	/**
	 * 新着フラグ
	 * 基本的にfalseとなる？
	 * trueに設定して更新しても必ずfalseになるため有料会員専用？
	 * 初期値設定可能⇒ただし反映されない
	 * 変更不可
	 */
	private final boolean newFlag;
	
	/**
	 * 情報取得元のアカウントキー？
	 * nullable
	 * 初期値設定不可
	 * 変更不可
	 */
	private final String accountKey;
	
	/**
	 * 情報取得元のアカウントキー？
	 * nullable
	 * 初期値設定不可
	 * 変更不可
	 */
	private final String tankKey;
	
	/**
	 * 情報取得元のアカウントキー
	 * nullable
	 * 初期値設定不可
	 * 変更不可
	 */
	private final String shoKey;
	
	/**
	 * オプションデータ1
	 * nullable
	 * 初期値設定不可
	 * 変更不可
	 */
	private final String optionData1;
	
	/**
	 * オプションデータ2
	 * nullable
	 * 初期値設定不可
	 * 変更不可
	 */
	private final String optionData2;
	
	/**
	 * 情報取得元の明細種別
	 * nullable
	 * 初期値設定不可
	 * 変更不可
	 */
	private final String shoShubetuId;
	
	public AbstractBooking(
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
				date,
				sum,
				item,
				expense,
				shop,
				settlementWay,
				unitPrice,
				amount,
				memo,
				totalingFlag,
				expenseDetail);
		this.id = id;
		this.dataType = dataType;
		this.cpId = cpId;
		this.cpName = cpName;
		this.aggrId = aggrId;
		this.newFlag = newFlag;
		this.accountKey = accountKey;
		this.tankKey = tankKey;
		this.shoKey = shoKey;
		this.optionData1 = optionData1;
		this.optionData2 = optionData2;
		this.shoShubetuId = shoShubetuId;
	}
	
	@Override
	public long getId() {
		return id;
	}
	
	@Override
	public long getDataType() {
		return dataType;
	}
	
	@Override
	public String getCpId() {
		return cpId;
	}
	
	@Override
	public String getCpName() {
		return cpName;
	}
	
	@Override
	public String getAggrId() {
		return aggrId;
	}
	
	@Override
	public boolean isNew() {
		return newFlag;
	}
	
	@Override
	public String getAccountKey() {
		return accountKey;
	}
	
	@Override
	public String getTankKey() {
		return tankKey;
	}
	
	@Override
	public String getShoKey() {
		return shoKey;
	}
	
	@Override
	public String getOptionData1() {
		return optionData1;
	}
	
	@Override
	public String getOptionData2() {
		return optionData2;
	}
	
	@Override
	public String getShoShubetuId() {
		return shoShubetuId;
	}
	
	@Override
	public boolean equalsAsBooking(final Booking other) {
		if (id != other.getId()) {
			return false;
		}
		if (dataType != other.getDataType()) {
			return false;
		}
		if (accountKey == null) {
			if (other.getAccountKey() != null) {
				return false;
			}
		} else if (!accountKey.equals(other.getAccountKey())) {
			return false;
		}
		if (aggrId == null) {
			if (other.getAggrId() != null) {
				return false;
			}
		} else if (!aggrId.equals(other.getAggrId())) {
			return false;
		}
		if (cpId == null) {
			if (other.getCpId() != null) {
				return false;
			}
		} else if (!cpId.equals(other.getCpId())) {
			return false;
		}
		if (cpName == null) {
			if (other.getCpName() != null) {
				return false;
			}
		} else if (!cpName.equals(other.getCpName())) {
			return false;
		}
		if (newFlag != other.isNew()) {
			return false;
		}
		if (optionData1 == null) {
			if (other.getOptionData1() != null) {
				return false;
			}
		} else if (!optionData1.equals(other.getOptionData1())) {
			return false;
		}
		if (optionData2 == null) {
			if (other.getOptionData2() != null) {
				return false;
			}
		} else if (!optionData2.equals(other.getOptionData2())) {
			return false;
		}
		if (shoKey == null) {
			if (other.getShoKey() != null) {
				return false;
			}
		} else if (!shoKey.equals(other.getShoKey())) {
			return false;
		}
		if (shoShubetuId == null) {
			if (other.getShoShubetuId() != null) {
				return false;
			}
		} else if (!shoShubetuId.equals(other.getShoShubetuId())) {
			return false;
		}
		if (tankKey == null) {
			if (other.getTankKey() != null) {
				return false;
			}
		} else if (!tankKey.equals(other.getTankKey())) {
			return false;
		}
		
		return equalsAsBookingBase(other);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((accountKey == null) ? 0 : accountKey.hashCode());
		result = prime * result + ((aggrId == null) ? 0 : aggrId.hashCode());
		result = prime * result + ((cpId == null) ? 0 : cpId.hashCode());
		result = prime * result + ((cpName == null) ? 0 : cpName.hashCode());
		result = prime * result + (int)(dataType ^ (dataType >>> 32));
		result = prime * result + (int)(id ^ (id >>> 32));
		result = prime * result + (newFlag ? 1231 : 1237);
		result = prime * result + ((optionData1 == null) ? 0 : optionData1.hashCode());
		result = prime * result + ((optionData2 == null) ? 0 : optionData2.hashCode());
		result = prime * result + ((shoKey == null) ? 0 : shoKey.hashCode());
		result = prime * result + ((shoShubetuId == null) ? 0 : shoShubetuId.hashCode());
		result = prime * result + ((tankKey == null) ? 0 : tankKey.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		return equalsAsBooking((AbstractBooking)obj);
	}
	
	@Override
	public int compareTo(final BookingBase<Expense<? extends ExpenseDetail>, ExpenseDetail> o) {
		if (o instanceof Booking) {
			return (this.id < ((Booking)o).getId() ? -1 : (this.id == ((Booking)o).getId() ? 0 : 1));
		}
		return super.compareTo(o);
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "["
				+ "id=" + getId()
				+ ", date=" + getDate()
				+ ", sum=" + getSum()
				+ ", item=" + getItem()
				+ ", expense=" + getExpense()
				+ ", shop=" + getShop()
				+ ", cpId=" + getCpId()
				+ ", cpName=" + getCpName()
				+ ", settlementWay=" + getSettlementWay()
				+ ", unitPrice=" + getUnitPrice()
				+ ", amount=" + getAmount()
				+ ", memo=" + getMemo()
				+ ", totalingFlag=" + isTotaling()
				+ ", aggrId=" + getAggrId()
				+ ", dataType=" + getDataType()
				+ ", newFlag=" + isNew()
				+ ", accountKey=" + getAccountKey()
				+ ", tankKey=" + getTankKey()
				+ ", shoKey=" + getShoKey()
				+ ", optionData1=" + getOptionData1()
				+ ", optionData2=" + getOptionData2()
				+ ", shoShubetuId=" + getShoShubetuId()
				+ ", expenseDetail=" + getExpenseDetail()
				+ "]";
	}
}