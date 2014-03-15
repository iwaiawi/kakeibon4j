package kakeibon4j.entity.expense.detail;

/**
 * Default implementation of {@link UnregisteredUserExpenseDetail}.
 * 
 * @author ero3
 */
public class UnregisteredUserExpenseDetailImpl extends AbstractExpenseDetailBase implements UnregisteredUserExpenseDetail {
	
	public UnregisteredUserExpenseDetailImpl(final String name) {
		super(name);
		if (name.length() > 10) {
			throw new IllegalArgumentException("Argument name:" + name + " is too long. Max length is 10 characters.");
		}
	}
	
	@Override
	public boolean isGlobal() {
		return false;
	}
}
