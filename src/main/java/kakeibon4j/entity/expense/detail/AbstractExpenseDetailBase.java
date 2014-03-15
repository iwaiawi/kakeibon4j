package kakeibon4j.entity.expense.detail;

/**
 * An abstract class representing expense's detail base.
 * 
 * @author ero3
 */
public abstract class AbstractExpenseDetailBase implements ExpenseDetailBase {
	
	/**
	 * 名称
	 * 必須項目のためnotnullかつ空文字列不可
	 */
	private final String name;
	
	public AbstractExpenseDetailBase(final String name) {
		if (name == null || "".equals(name)) {
			throw new IllegalArgumentException("Argument name must be not both null and empty string.");
		}
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public boolean equalsAsBase(final ExpenseDetailBase another) {
		if (name == null) {
			if (another.getName() != null) {
				return false;
			}
		} else if (!name.equals(another.getName())) {
			return false;
		} else if (isGlobal() != another.isGlobal()) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		return equalsAsBase((ExpenseDetailBase)obj);
	}
	
	@Override
	public int compareTo(final ExpenseDetailBase o) {
		if (name != null) {
			return this.name.compareTo(o.getName());
		}
		return 0;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "[name=" + name + "]";
	}
}