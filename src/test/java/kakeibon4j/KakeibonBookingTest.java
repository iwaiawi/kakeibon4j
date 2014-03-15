package kakeibon4j;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

import kakeibon4j.entity.Duration;
import kakeibon4j.entity.booking.Booking;
import kakeibon4j.entity.booking.UnregisteredBooking;
import kakeibon4j.entity.booking.UnregisteredBookingBuilderImpl;
import kakeibon4j.entity.booking.UpdatedBooking;
import kakeibon4j.entity.booking.UpdatedBookingBuilderImpl;
import kakeibon4j.entity.expense.GlobalExpense;
import kakeibon4j.entity.expense.UserExpense;

import org.junit.Test;

public class KakeibonBookingTest extends KakeibonTestBase {
	
	@Test
	public void testGetBookingList() throws Exception {
		final List<Booking> bookingList1 = kakeibon1.getBookingList();
		assertNotNull(bookingList1);
		assertTrue(0 <= bookingList1.size());
		debug(bookingList1);
		
		final List<Booking> bookingList2 = kakeibon1.getBookingList(new Duration(31, new Date()));
		assertNotNull(bookingList2);
		assertTrue(0 <= bookingList2.size());
		debug(bookingList2);
	}
	
	@Test
	public void testReloadBooking() throws Exception {
		kakeibon1.reloadBooking();
	}
	
	@Test
	public void testReloadAndGetBookingList() throws Exception {
		kakeibon1.reloadBooking();
		
		final List<Booking> bookingList1 = kakeibon1.getBookingList();
		assertNotNull(bookingList1);
		assertTrue(0 <= bookingList1.size());
	}
	
	@Test
	public void testReloadAndGetExpenseList() throws Exception {
		kakeibon1.reloadBooking();
		
		final Set<GlobalExpense> globalList = kakeibon1.getGlobalExpenses();
		assertNotNull(globalList);
		assertTrue(1 <= globalList.size());
		
		final Set<UserExpense> userList = kakeibon1.getUserExpenses();
		assertNotNull(userList);
		assertTrue(0 <= userList.size());
	}
	
	@Test
	public void testAddAndUpdateAndDeleteBooking() throws Exception {
		
		final GlobalExpense expense1 = getRandomOne(kakeibon1.getGlobalExpenses());
		
		final UnregisteredBooking unregistered1 = new UnregisteredBookingBuilderImpl()
				.setDate(new Date())
				.setSum(-8181)
				.setItem("ダミー1_" + getRandomString(4))
				.setExpense(expense1)
				.setShop("ダミーショップ")
				.setSettlementWay("ダミー支払い")
				.setUnitPrice(8181L)
				.setAmount(1L)
				.setMemo("ダミーメモ")
				.setTotalingFlag(true)
				.setExpenseDetail(getRandomOne(expense1.getDetails()))
				.build();
		
		final Booking registered1 = kakeibon1.registerBooking(unregistered1);
		assertNotNull(registered1);
		assertNotNull(registered1.getId());
		assertTrue(kakeibon1.getBookingList().contains(registered1));
		debug(registered1);
		
		final GlobalExpense expense2 = getRandomOne(kakeibon1.getGlobalExpenses());
		final UpdatedBooking unregistered2 = new UpdatedBookingBuilderImpl(registered1)
				.setDate(new Date(System.currentTimeMillis() - 3 * 24 * 60 * 60 * 1000)) // 3日前
				.setSum(-16362)
				.setItem("ダミーupdated_" + getRandomString(4))
				.setExpense(expense2)
				.setShop("ダミーショップupdated")
				.setSettlementWay("ダミー支払いupdated")
				.setUnitPrice(8181L)
				.setAmount(2L)
				.setMemo("ダミーメモupdated")
				.setTotalingFlag(false)
				.setExpenseDetail(getRandomOne(expense2.getDetails()))
				.build();
		assertNotNull(unregistered2.getId());
		
		final Booking registered2 = kakeibon1.updateBooking(unregistered2);
		assertNotNull(registered2);
		assertNotNull(registered2.getId());
		assertTrue(kakeibon1.getBookingList().contains(registered2));
		debug(registered2);
		
		final UpdatedBooking unregistered3 = new UpdatedBookingBuilderImpl(registered2)
				.setExpenseDetail(null)
				.setShop(null)
				.build();
		assertNotNull(unregistered3.getId());
		
		final Booking registered3 = kakeibon1.updateBooking(unregistered3);
		assertNotNull(registered3);
		assertNotNull(registered3.getId());
		assertTrue(kakeibon1.getBookingList().contains(registered3));
		debug(registered3);
		
		kakeibon1.deleteBooking(registered3);
		assertFalse(kakeibon1.getBookingList().contains(registered3));
		
		// Re-Update deleted one => no error
		final UpdatedBooking unregistered4 = new UpdatedBookingBuilderImpl(registered3)
				.setExpense(null)
				.build();
		final Booking registered4 = kakeibon1.updateBooking(unregistered4);
		assertNull(registered4);
		debug(registered4);
		
		// Re-Delete Same one => no error
		kakeibon1.deleteBooking(registered3);
		
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
