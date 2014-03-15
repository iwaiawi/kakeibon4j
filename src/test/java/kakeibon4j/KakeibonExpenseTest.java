package kakeibon4j;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import kakeibon4j.entity.expense.ExpenseBase;
import kakeibon4j.entity.expense.GlobalExpense;
import kakeibon4j.entity.expense.UnregisteredUserExpense;
import kakeibon4j.entity.expense.UnregisteredUserExpenseImpl;
import kakeibon4j.entity.expense.UserExpense;
import kakeibon4j.entity.expense.detail.ExpenseDetail;
import kakeibon4j.entity.expense.detail.ExpenseDetailBase;
import kakeibon4j.entity.expense.detail.UnregisteredUserExpenseDetail;
import kakeibon4j.entity.expense.detail.UnregisteredUserExpenseDetailImpl;
import kakeibon4j.entity.expense.detail.UserExpenseDetail;
import kakeibon4j.internal.scraping.KakeibonScrapingImpl;

import org.junit.Test;

public class KakeibonExpenseTest extends KakeibonTestBase {
	
	@Test
	public void testGetExpenseList() throws Exception {
		final Set<GlobalExpense> globalList = kakeibon1.getGlobalExpenses();
		assertNotNull(globalList);
		assertTrue(1 <= globalList.size());
		debug(globalList);
		
		final Set<UserExpense> userList = kakeibon1.getUserExpenses();
		assertNotNull(userList);
		assertTrue(0 <= userList.size());
		debug(userList);
	}
	
	@Test
	public void testLoginAndLogout() throws Exception {
		((KakeibonScrapingImpl)kakeibon1).login();
		((KakeibonScrapingImpl)kakeibon1).logout();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testTooLongUserExpenseName() throws Exception {
		new UnregisteredUserExpenseImpl("ダミー" + getRandomString(8));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testTooLongUserExpenseDetailName() throws Exception {
		new UnregisteredUserExpenseImpl("ダミー" + getRandomString(8));
	}
	
	@Test
	public void testAddAndDeleteUserExpense() throws Exception {
		final UnregisteredUserExpense unregistered1 = new UnregisteredUserExpenseImpl("ダミー1_" + getRandomString(4));
		assertEquals(unregistered1.getBalanceType(), ExpenseBase.PAYMENT);
		assertTrue(unregistered1.getDetails().isEmpty());
		
		final UserExpense registered1 = kakeibon1.registerUserExpense(unregistered1);
		assertNotNull(registered1);
		assertNotNull(registered1.getCode());
		assertEquals(registered1.getBalanceType(), ExpenseBase.PAYMENT);
		assertFalse(registered1.equals(unregistered1));
		assertTrue(registered1.getDetails().isEmpty());
		assertTrue(kakeibon1.getUserExpenses().contains(registered1));
		debug(registered1);
		
		// Re-Add Same one => success
		kakeibon1.deleteUserExpense(kakeibon1.registerUserExpense(unregistered1));
		
		kakeibon1.deleteUserExpense(registered1);
		assertFalse(kakeibon1.getUserExpenses().contains(registered1));
		
		// Re-Delete Same one => no error
		kakeibon1.deleteUserExpense(registered1);
		
		final List<? extends UnregisteredUserExpense> unregistered2 = Arrays.asList(
				new UnregisteredUserExpenseImpl(
						"ダミー2_" + getRandomString(4),
						ExpenseBase.INCOME,
						Arrays.asList(
								new UnregisteredUserExpenseDetailImpl("ダミー1_" + getRandomString(4)),
								new UnregisteredUserExpenseDetailImpl("ダミー2_" + getRandomString(4))
								)
				),
				new UnregisteredUserExpenseImpl(
						"ダミー3_" + getRandomString(4),
						ExpenseBase.PAYMENT,
						Arrays.asList(
								new UnregisteredUserExpenseDetailImpl("ダミー3_" + getRandomString(4))
								)
				)
				);
		
		final List<UserExpense> registered2 = kakeibon1.registerUserExpenses(unregistered2);
		assertNotNull(registered2);
		assertEquals(unregistered2.size(), registered2.size());
		assertTrue(kakeibon1.getUserExpenses().containsAll(registered2));
		
		final Iterator<? extends UnregisteredUserExpense> unregistered2It = unregistered2.iterator();
		final Iterator<UserExpense> registered2It = registered2.iterator();
		while (unregistered2It.hasNext() && registered2It.hasNext()) {
			final UnregisteredUserExpense unregistered = unregistered2It.next();
			final UserExpense registered = registered2It.next();
			assertNotNull(registered.getCode());
			assertEquals(unregistered.getBalanceType(), registered.getBalanceType());
			assertFalse(registered.equals(unregistered));
			assertEquals(unregistered.getDetails().size(), registered.getDetails().size());
		}
		debug(registered2);
		
		kakeibon1.deleteUserExpenses(registered2);
		assertFalse(kakeibon1.getUserExpenses().containsAll(registered2));
	}
	
	@Test
	public void testAddAndDeleteUserExpenseDetail() throws Exception {
		// Add UserExpenseDetail to GlobalExpense
		final Set<GlobalExpense> globalList = kakeibon1.getGlobalExpenses();
		final UnregisteredUserExpenseDetail unregistered1 = new UnregisteredUserExpenseDetailImpl("ダミー_" + getRandomString(4));
		
		GlobalExpense parent1 = getRandomOne(globalList);
		final GlobalExpense registered1 = kakeibon1.registerUserExpenseDetail(parent1, unregistered1);
		assertNotNull(registered1);
		assertFalse(new LinkedList<ExpenseDetailBase>(registered1.getDetails()).getFirst().isGlobal());
		assertTrue(new LinkedList<ExpenseDetailBase>(registered1.getDetails()).getLast().isGlobal());
		
		UserExpenseDetail registeredDetail1 = null;
		boolean checkContained1 = false;
		for (final ExpenseDetail detail : registered1.getDetails()) {
			if (detail.equalsAsBase(unregistered1)) {
				assertNotNull(detail.getCode());
				registeredDetail1 = (UserExpenseDetail)detail;
				checkContained1 = true;
				break;
			}
		}
		assertTrue(checkContained1);
		debug(registered1);
		debug(registeredDetail1);
		parent1 = registered1;
		
		// Re-Add Same one => exception
		kakeibon1.registerUserExpenseDetail(parent1, unregistered1);
		
		kakeibon1.deleteUserExpenseDetail(parent1, registeredDetail1);
		for (final GlobalExpense expense : kakeibon1.getGlobalExpenses()) { // reload
			if (expense.getCode().equals(parent1.getCode())) {
				if (!expense.getDetails().contains(registeredDetail1)) {
					checkContained1 = false;
					break;
				}
			}
		}
		assertFalse(checkContained1);
		
		// Re-Delete Same one => no error
		kakeibon1.deleteUserExpenseDetail(parent1, registeredDetail1);
		
		// Add UserExpenseDetail to UserExpense
		final Set<UserExpense> userList = kakeibon1.getUserExpenses();
		final UnregisteredUserExpenseDetail unregistered2 = new UnregisteredUserExpenseDetailImpl("ダミー2_" + getRandomString(4));
		
		UserExpense parent2 = getRandomOne(userList);
		final UserExpense registered2 = kakeibon1.registerUserExpenseDetails(parent2, Arrays.asList(unregistered2));
		assertNotNull(registered2);
		
		UserExpenseDetail registeredDetail2 = null;
		boolean checkContained2 = false;
		for (final UserExpenseDetail detail : registered2.getDetails()) {
			if (detail.equalsAsBase(unregistered2)) {
				assertNotNull(detail.getCode());
				registeredDetail2 = detail;
				checkContained2 = true;
				break;
			}
		}
		assertTrue(checkContained2);
		debug(registered2);
		debug(registeredDetail2);
		parent2 = registered2;
		
		kakeibon1.deleteUserExpenseDetails(parent2, Arrays.asList(registeredDetail2));
		for (final UserExpense expense : kakeibon1.getUserExpenses()) { // reload
			if (expense.getCode().equals(parent2.getCode())) {
				if (!expense.getDetails().contains(registeredDetail2)) {
					checkContained2 = false;
					break;
				}
			}
		}
		assertFalse(checkContained2);
	}
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
	}
	
	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}
}
