package kakeibon4j.entity.expense.detail;

/**
 * An abstract class representing expense's detail.
 * 
 * @author ero3
 */
public abstract class AbstractExpenseDetail extends AbstractExpenseDetailBase implements ExpenseDetail {
	
	/**
	 * 識別子コード
	 * 必須項目のためnotnullかつ空文字列不可
	 */
	private final String code;
	
	public AbstractExpenseDetail(final String code, final String name) {
		super(name);
		if (code == null || "".equals(code)) {
			throw new IllegalArgumentException("Argument code must be not both null and empty string.");
		}
		this.code = code;
	}
	
	@Override
	public String getCode() {
		return code;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		final AbstractExpenseDetail other = (AbstractExpenseDetail)obj;
		if (code == null) {
			if (other.code != null) {
				return false;
			}
		} else if (!code.equals(other.code)) {
			return false;
		}
		return true;
	}
	
	@Override
	public int compareTo(final ExpenseDetailBase o) {
		if (o instanceof ExpenseDetail) {
			return this.code.compareTo(((ExpenseDetail)o).getCode());
		}
		return super.compareTo(o);
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "[code=" + code + ", name=" + getName() + "]";
	}
}