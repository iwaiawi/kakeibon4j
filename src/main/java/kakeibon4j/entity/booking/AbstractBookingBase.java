package kakeibon4j.entity.booking;

import java.util.Calendar;
import java.util.Date;

import kakeibon4j.entity.expense.ExpenseBase;
import kakeibon4j.entity.expense.detail.ExpenseDetailBase;

/**
 * An abstract class representing booking base.
 * 
 * @param <T> the expense's type
 * @param <S> the expense detail's type
 * @author ero3
 */
public abstract class AbstractBookingBase<T extends ExpenseBase<? extends ExpenseDetailBase>, S extends ExpenseDetailBase> implements BookingBase<T, S> {
	
	/**
	 * 日付
	 * notnullかつ時刻部分は強制的に 00:00:00 0000となる。
	 */
	private final Date date;
	
	/**
	 * 収支
	 * ゼロ以外であること
	 */
	private final long sum;
	
	/**
	 * 購入物など
	 * notnull
	 */
	private final String item;
	
	/**
	 * 費目
	 * nullable
	 */
	private final T expense;
	
	/**
	 * 購入店等
	 * nullable
	 */
	private final String shop;
	
	/**
	 * 決済方法
	 * nullable
	 */
	private final String settlementWay;
	
	/**
	 * 単価
	 * nullable
	 * nullで無い場合はunitPrice*amount=abs(sum)を満たすこと
	 */
	private final Long unitPrice;
	
	/**
	 * 数量
	 * nullable
	 * nullで無い場合はunitPrice*amount=abs(sum)を満たすこと
	 */
	private final Long amount;
	
	/**
	 * メモ
	 * nullable
	 */
	private final String memo;
	
	/**
	 * 計算対象フラグ
	 */
	private final boolean totalingFlag;
	
	/**
	 * 詳細費目
	 * nullable
	 * nullでない場合はexpenseもnullでなく、かつexpense.getDetails()に含まれていること
	 */
	private final S expenseDetail;
	
	protected AbstractBookingBase(
			final Date date,
			final long sum,
			final String item,
			final T expense,
			final String shop,
			final String settlementWay,
			final Long unitPrice,
			final Long amount,
			final String memo,
			final boolean totalingFlag,
			final S expenseDetail) {
		super();
		
		if (date == null) {
			throw new IllegalArgumentException("Argument date must be not null.");
		}
		if (sum == 0) {
			throw new IllegalArgumentException("Argument sum must be not 0.");
		}
		if (item == null) {
			throw new IllegalArgumentException("Argument item must be not null.");
		}
		if (unitPrice != null && amount != null) {
			if (unitPrice.longValue() * amount.longValue() != Math.abs(sum)) {
				throw new IllegalArgumentException("Argument sum:" + sum + " must be (unitPrice:" + unitPrice + " * amount:" + amount + ").");
			}
		}
		
		if (expenseDetail != null) {
			if (expense != null) {
				if (!expense.getDetails().contains(expenseDetail)) {
					throw new IllegalArgumentException("Argument expense:" + expense + " must contain argument expenseDetail:" + expenseDetail + ".");
				}
			} else {
				throw new IllegalArgumentException("Argument expense must be not null when argument expenseDetail:" + expenseDetail + " is setted.");
			}
		}
		
		this.date = new Date(truncateDate(date).getTime());
		this.sum = sum;
		this.item = item;
		this.expense = expense;
		this.shop = shop;
		this.settlementWay = settlementWay;
		this.unitPrice = unitPrice;
		this.amount = amount;
		this.memo = memo;
		this.totalingFlag = totalingFlag;
		this.expenseDetail = expenseDetail;
	}
	
	private Date truncateDate(final Date date) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		final Calendar truncated = Calendar.getInstance();
		truncated.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0); // 時間・分・秒を0にする
		truncated.set(Calendar.MILLISECOND, 0); // きっちりミリ秒も0にする
		return truncated.getTime();
	}
	
	@Override
	public Date getDate() {
		return new Date(date.getTime());
	}
	
	@Override
	public long getSum() {
		return sum;
	}
	
	@Override
	public String getItem() {
		return item;
	}
	
	@Override
	public T getExpense() {
		return expense;
	}
	
	@Override
	public String getShop() {
		return shop;
	}
	
	@Override
	public String getSettlementWay() {
		return settlementWay;
	}
	
	@Override
	public Long getUnitPrice() {
		return unitPrice;
	}
	
	@Override
	public Long getAmount() {
		return amount;
	}
	
	@Override
	public String getMemo() {
		return memo;
	}
	
	@Override
	public boolean isTotaling() {
		return totalingFlag;
	}
	
	@Override
	public S getExpenseDetail() {
		return expenseDetail;
	}
	
	@Override
	public boolean equalsAsBookingBase(final BookingBase<T, S> other) {
		if (amount == null) {
			if (other.getAmount() != null) {
				return false;
			}
		} else if (!amount.equals(other.getAmount())) {
			return false;
		}
		if (date == null) {
			if (other.getDate() != null) {
				return false;
			}
		} else if (!date.equals(other.getDate())) {
			return false;
		}
		if (expense == null) {
			if (other.getExpense() != null) {
				return false;
			}
		} else if (!expense.equals(other.getExpense())) {
			return false;
		}
		if (expenseDetail == null) {
			if (other.getExpenseDetail() != null) {
				return false;
			}
		} else if (!expenseDetail.equals(other.getExpenseDetail())) {
			return false;
		}
		if (item == null) {
			if (other.getItem() != null) {
				return false;
			}
		} else if (!item.equals(other.getItem())) {
			return false;
		}
		if (memo == null) {
			if (other.getMemo() != null) {
				return false;
			}
		} else if (!memo.equals(other.getMemo())) {
			return false;
		}
		if (settlementWay == null) {
			if (other.getSettlementWay() != null) {
				return false;
			}
		} else if (!settlementWay.equals(other.getSettlementWay())) {
			return false;
		}
		if (shop == null) {
			if (other.getShop() != null) {
				return false;
			}
		} else if (!shop.equals(other.getShop())) {
			return false;
		}
		if (sum != other.getSum()) {
			return false;
		}
		if (totalingFlag != other.isTotaling()) {
			return false;
		}
		if (unitPrice == null) {
			if (other.getUnitPrice() != null) {
				return false;
			}
		} else if (!unitPrice.equals(other.getUnitPrice())) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((expense == null) ? 0 : expense.hashCode());
		result = prime * result + ((expenseDetail == null) ? 0 : expenseDetail.hashCode());
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result + ((memo == null) ? 0 : memo.hashCode());
		result = prime * result + ((settlementWay == null) ? 0 : settlementWay.hashCode());
		result = prime * result + ((shop == null) ? 0 : shop.hashCode());
		result = prime * result + (int)(sum ^ (sum >>> 32));
		result = prime * result + (totalingFlag ? 1231 : 1237);
		result = prime * result + ((unitPrice == null) ? 0 : unitPrice.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		return equalsAsBookingBase((BookingBase<T, S>)obj);
	}
	
	@Override
	public int compareTo(final BookingBase<T, S> o) {
		if (this.equals(o)) {
			return 0;
		} else if (date.compareTo(o.getDate()) != 0) {
			return date.compareTo(o.getDate());
		} else if (sum - o.getSum() != 0L) {
			return (int)(sum - o.getSum());
		} else if (item.compareTo(o.getItem()) != 0) {
			return item.compareTo(o.getItem());
		}
		return 0;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "["
				+ "date=" + date
				+ ", sum=" + sum
				+ ", item=" + item
				+ ", expense=" + expense
				+ ", shop=" + shop
				+ ", settlementWay=" + settlementWay
				+ ", unitPrice=" + unitPrice
				+ ", amount=" + amount
				+ ", memo=" + memo
				+ ", totalingFlag=" + totalingFlag
				+ ", expenseDetail=" + expenseDetail
				+ "]";
	}
}