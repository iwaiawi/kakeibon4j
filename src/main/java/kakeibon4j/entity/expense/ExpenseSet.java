package kakeibon4j.entity.expense;

import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * A class representing SortedSet of expenses for customizing order.
 * 
 * @param <T> the expense's type
 * @author ero3
 */
public class ExpenseSet<T extends ExpenseBase<?>> extends TreeSet<T> {
	
	private static final Comparator<ExpenseBase<?>> DEFAULT_COMPARATOR = new Comparator<ExpenseBase<?>>() {
		/**
		 * Compare the expenses.
		 * 
		 * @param o1 the one
		 * @param o2 the another
		 * @return 1 if o1 is global expense detail and o2 is user defined expense detail,<br />
		 *         -1 if o1 is user defined expense detail and o2 is global expense detail,<br />
		 *         <code>o1.compareTo(o2)</code> if o1 and o2 are both global expense detail,<br />
		 *         <code>o2.compareTo(o1)</code> if o1 and o2 are both user defined expense detail.
		 */
		@Override
		public int compare(final ExpenseBase<?> o1, final ExpenseBase<?> o2) {
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
					return o1.compareTo(o2) * -1;
				}
			}
		}
	};
	
	public ExpenseSet() {
		super(DEFAULT_COMPARATOR);
	}
	
	/**
	 * Creates a new expense set with an initial data collection.
	 * 
	 * @param c the initial data collection
	 */
	public ExpenseSet(final Collection<? extends T> c) {
		super(DEFAULT_COMPARATOR);
		addAll(c);
	}
}
