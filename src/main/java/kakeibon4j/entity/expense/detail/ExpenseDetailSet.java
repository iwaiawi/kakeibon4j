package kakeibon4j.entity.expense.detail;

import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * A class representing SortedSet of expense details for customizing order.
 * 
 * @param <T> the detail's type
 * @author ero3
 */
public class ExpenseDetailSet<T extends ExpenseDetailBase> extends TreeSet<T> {
	
	private static final Comparator<ExpenseDetailBase> DEFAULT_COMPARATOR = new Comparator<ExpenseDetailBase>() {
		
		/**
		 * Compare the expense details.
		 * 
		 * @param o1 the one
		 * @param o2 the another
		 * @return 1 if o1 is global expense detail and o2 is user defined expense detail,<br />
		 *         -1 if o1 is user defined expense detail and o2 is global expense detail,<br />
		 *         <code>o1.compareTo(o2)</code> if o1 and o2 are both global expense detail,<br />
		 *         <code>o2.compareTo(o1)</code> if o1 and o2 are both user defined expense detail.
		 */
		@Override
		public int compare(final ExpenseDetailBase o1, final ExpenseDetailBase o2) {
			if (o1.isGlobal()) {
				if (o2.isGlobal()) { // o1:global, o2:global => NormalOrder
					return o1.compareTo(o2);
				} else { // o1:global, o2:user => o2 -> o1
					return 1;
				}
			} else {
				if (o2.isGlobal()) { // o1:user, o2:global => o1 -> o2
					return -1;
				} else {// o1:user, o2:user => ReverseOrder
					return o2.compareTo(o1);
				}
			}
		}
	};
	
	public ExpenseDetailSet() {
		super(DEFAULT_COMPARATOR);
	}
	
	/**
	 * Creates a new expense detail set with an initial data collection.
	 * 
	 * @param c the initial data collection
	 */
	public ExpenseDetailSet(final Collection<? extends T> c) {
		super(DEFAULT_COMPARATOR);
		addAll(c);
	}
}
