/*
 * Copyright (C) 2007 Yusuke Yamamoto
 * Copyright (C) 2011 Twitter, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kakeibon4j.internal.scraping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import kakeibon4j.Kakeibon;
import kakeibon4j.KakeibonException;
import kakeibon4j.entity.Duration;
import kakeibon4j.entity.booking.Booking;
import kakeibon4j.entity.booking.BookingBase;
import kakeibon4j.entity.booking.UnregisteredBooking;
import kakeibon4j.entity.booking.UpdatedBooking;
import kakeibon4j.entity.expense.Expense;
import kakeibon4j.entity.expense.ExpenseBase;
import kakeibon4j.entity.expense.GlobalExpense;
import kakeibon4j.entity.expense.UnregisteredUserExpense;
import kakeibon4j.entity.expense.UserExpense;
import kakeibon4j.entity.expense.detail.ExpenseDetail;
import kakeibon4j.entity.expense.detail.UnregisteredUserExpenseDetail;
import kakeibon4j.entity.expense.detail.UserExpenseDetail;
import kakeibon4j.internal.scraping.auth.AuthorizationClient;
import kakeibon4j.internal.scraping.auth.AuthorizationClientImpl;
import kakeibon4j.internal.scraping.client.AjaxClient;
import kakeibon4j.internal.scraping.client.AjaxClientImpl;
import kakeibon4j.internal.scraping.conf.PropertyConfigration;
import kakeibon4j.internal.scraping.conf.ScrapingConfiguration;
import kakeibon4j.internal.scraping.xml.BookingXml;
import kakeibon4j.internal.scraping.xml.DeleteBookingXml;
import kakeibon4j.internal.scraping.xml.GlobalSettingXml;
import kakeibon4j.internal.scraping.xml.NormalXml;
import kakeibon4j.internal.scraping.xml.UpdateBookingXml;
import kakeibon4j.internal.scraping.xml.UserExpenseXml;

public class KakeibonScrapingImpl implements Kakeibon {
	private static final long serialVersionUID = -1486360080128882456L;
	protected ScrapingConfiguration conf;
	protected AuthorizationClient auth;
	private AjaxClient client;
	
	public KakeibonScrapingImpl(final ScrapingConfiguration conf) {
		this.conf = conf;
		init();
	}
	
	public KakeibonScrapingImpl(final Properties props, final String configTreePath) {
		this(new PropertyConfigration(props, configTreePath));
	}
	
	public KakeibonScrapingImpl(final Properties props) {
		this(new PropertyConfigration(props));
	}
	
	public KakeibonScrapingImpl(final String configTreePath) {
		this(new PropertyConfigration(configTreePath));
	}
	
	public KakeibonScrapingImpl() {
		this(new PropertyConfigration());
	}
	
	private void init() {
		auth = new AuthorizationClientImpl(conf);
		client = new AjaxClientImpl(conf);
	}
	
	@Override
	public Set<GlobalExpense> getGlobalExpenses() throws KakeibonException {
		return getGlobalSetting().getGlobalExpenses();
	}
	
	@Override
	public Set<UserExpense> getUserExpenses() throws KakeibonException {
		return getGlobalSetting().getUserExpenses();
	}
	
	public Set<Expense<? extends ExpenseDetail>> getAllExpenses() throws KakeibonException {
		return getGlobalSetting().getExpenses();
	}
	
	synchronized private GlobalSettingXml getGlobalSetting() throws KakeibonException {
		final GlobalSettingXml xml = client.getGlobalSetting(auth.getContext());
		auth.updateContext(auth.getContext().updateAauuid(xml.getAauuid()));
		return xml;
	}
	
	@Override
	public UserExpense registerUserExpense(final UnregisteredUserExpense expense) throws KakeibonException {
		return registerUserExpenses(Arrays.asList(expense)).iterator().next();
	}
	
	@Override
	synchronized public List<UserExpense> registerUserExpenses(final Collection<? extends UnregisteredUserExpense> targets) throws KakeibonException {
		final UserExpenseXml xml = client.addUserExpenses(targets, auth.getContext());
		auth.updateContext(auth.getContext().updateAauuid(xml.getAauuid()));
		
		// extract registered UserExpense from all UserExpense
		final List<UserExpense> resultList = new ArrayList<UserExpense>();
		for (final UnregisteredUserExpense target : targets) {
			boolean isSuccess = false;
			for (final UserExpense expense : xml.getUserExpenses()) {
				if (equalsAsExpenseBaseExceptDetails(expense, target)) {
					if (target.getDetails().isEmpty()) {
						resultList.add(expense);
					} else { // register details if target has details
						resultList.add(registerUserExpenseDetails(expense, target.getDetails()));
					}
					isSuccess = true;
					break;
				}
			}
			if (!isSuccess) { // when not found registered
				resultList.add(null);
			}
		}
		
		return resultList;
	}
	
	private boolean equalsAsExpenseBaseExceptDetails(final ExpenseBase<?> e1, final ExpenseBase<?> e2) {
		if (e1.getName() == null) {
			if (e2.getName() != null) {
				return false;
			}
		} else if (!e1.getName().equals(e2.getName())) {
			return false;
		} else if (e1.getBalanceType() != e2.getBalanceType()) {
			return false;
		} else if (e1.isGlobal() != e2.isGlobal()) {
			return false;
		}
		return true;
	}
	
	@Override
	public void deleteUserExpense(final UserExpense expense) throws KakeibonException {
		deleteUserExpenses(Arrays.asList(expense));
	}
	
	@Override
	synchronized public void deleteUserExpenses(final Collection<? extends UserExpense> expenses) throws KakeibonException {
		final UserExpenseXml xml = client.deleteUserExpenses(expenses, auth.getContext());
		auth.updateContext(auth.getContext().updateAauuid(xml.getAauuid()));
	}
	
	@Override
	public GlobalExpense registerUserExpenseDetail(final GlobalExpense parent, final UnregisteredUserExpenseDetail detail) throws KakeibonException {
		return registerUserExpenseDetails(parent, Arrays.asList(detail));
	}
	
	@Override
	synchronized public GlobalExpense registerUserExpenseDetails(final GlobalExpense parent, final Collection<? extends UnregisteredUserExpenseDetail> targets) throws KakeibonException {
		final UserExpenseXml xml = client.addUserExpenseDetails(parent, targets, auth.getContext());
		auth.updateContext(auth.getContext().updateAauuid(xml.getAauuid()));
		
		return xml.getGlobalExpense(parent.getCode(), Arrays.asList(parent));
	}
	
	@Override
	public UserExpense registerUserExpenseDetail(final UserExpense parent, final UnregisteredUserExpenseDetail detail) throws KakeibonException {
		return registerUserExpenseDetails(parent, Arrays.asList(detail));
	}
	
	@Override
	synchronized public UserExpense registerUserExpenseDetails(final UserExpense parent, final Collection<? extends UnregisteredUserExpenseDetail> targets) throws KakeibonException {
		final UserExpenseXml xml = client.addUserExpenseDetails(parent, targets, auth.getContext());
		auth.updateContext(auth.getContext().updateAauuid(xml.getAauuid()));
		
		return xml.getUserExpense(parent.getCode());
	}
	
	@Override
	public void deleteUserExpenseDetail(final Expense<? extends ExpenseDetail> parent, final UserExpenseDetail detail) throws KakeibonException {
		deleteUserExpenseDetails(parent, Arrays.asList(detail));
	}
	
	@Override
	synchronized public void deleteUserExpenseDetails(final Expense<? extends ExpenseDetail> parent, final Collection<? extends UserExpenseDetail> details) throws KakeibonException {
		final UserExpenseXml xml = client.deleteUserExpenseDetails(parent, details, auth.getContext());
		auth.updateContext(auth.getContext().updateAauuid(xml.getAauuid()));
	}
	
	@Override
	public List<Booking> getBookingList() throws KakeibonException {
		return getBookingList(createDefaultDuration());
	}
	
	@Override
	synchronized public List<Booking> getBookingList(final Duration duration) throws KakeibonException {
		final BookingXml xml = client.getBookingList(duration, auth.getContext());
		auth.updateContext(auth.getContext().updateAauuid(xml.getAauuid()));
		
		return xml.getBookingList(getAllExpenses());
	}
	
	@Override
	synchronized public void reloadBooking() throws KakeibonException {
		final NormalXml xml = client.reloadBooking(auth.getContext());
		auth.updateContext(auth.getContext().updateAauuid(xml.getAauuid()));
	}
	
	@Override
	public Booking registerBooking(final UnregisteredBooking booking) throws KakeibonException {
		return registerBookings(Arrays.asList(booking)).iterator().next();
	}
	
	@Override
	synchronized public List<Booking> registerBookings(final Collection<? extends UnregisteredBooking> bookings) throws KakeibonException {
		final UpdateBookingXml xml = client.addBookings(bookings, auth.getContext());
		auth.updateContext(auth.getContext().updateAauuid(xml.getAauuid()));
		
		// extract register completed one in same order of argument bookings
		final List<Booking> resultList = new ArrayList<Booking>();
		for (final UnregisteredBooking unregistered : bookings) {
			boolean isSuccess = false;
			for (final Booking booking : xml.getBookingList(extractExpenses(bookings))) {
				if (booking.equalsAsBookingBase(unregistered)) {
					resultList.add(booking);
					isSuccess = true;
					break;
				}
			}
			if (!isSuccess) { // when not found registered
				resultList.add(null);
			}
		}
		
		return resultList;
	}
	
	@Override
	public Booking updateBooking(final UpdatedBooking booking) throws KakeibonException {
		return updateBookings(Arrays.asList(booking)).iterator().next();
	}
	
	@Override
	synchronized public List<Booking> updateBookings(final Collection<? extends UpdatedBooking> bookings) throws KakeibonException {
		final UpdateBookingXml xml = client.updateBookings(bookings, auth.getContext());
		auth.updateContext(auth.getContext().updateAauuid(xml.getAauuid()));
		
		// extract update completed one in same order of argument bookings
		final List<Booking> resultList = new ArrayList<Booking>();
		for (final UpdatedBooking updated : bookings) {
			boolean isSuccess = false;
			for (final Booking booking : xml.getBookingList(extractExpenses(bookings))) {
				if (booking.equalsAsBooking(updated)) {
					resultList.add(booking);
					isSuccess = true;
					break;
				}
			}
			if (!isSuccess) { // when not found regiatered
				resultList.add(null);
			}
		}
		
		return resultList;
	}
	
	@Override
	public void deleteBooking(final Booking booking) throws KakeibonException {
		deleteBookings(Arrays.asList(booking));
	}
	
	@Override
	synchronized public void deleteBookings(final Collection<? extends Booking> bookings) throws KakeibonException {
		final DeleteBookingXml xml = client.deleteBookings(bookings, auth.getContext());
		auth.updateContext(auth.getContext().updateAauuid(xml.getAauuid()));
	}
	
	private Set<Expense<? extends ExpenseDetail>> extractExpenses(final Collection<? extends BookingBase<Expense<? extends ExpenseDetail>, ExpenseDetail>> bookings) throws KakeibonException {
		final Set<Expense<? extends ExpenseDetail>> expenses = new HashSet<Expense<? extends ExpenseDetail>>();
		for (final BookingBase<Expense<? extends ExpenseDetail>, ExpenseDetail> booking : bookings) {
			if (booking.getExpense() != null) {
				expenses.add(booking.getExpense());
			}
		}
		return expenses;
	}
	
	synchronized public void login() throws KakeibonException {
		auth.login();
	}
	
	synchronized public void logout() throws KakeibonException {
		auth.logout();
	}
	
	private Duration createDefaultDuration() {
		final Calendar today = Calendar.getInstance();
		
		final Calendar startAt = Calendar.getInstance();
		startAt.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH), 1, 0, 0, 0); // 今月1日
		startAt.set(Calendar.MILLISECOND, 0); // きっちりミリ秒も0にする
		startAt.add(Calendar.MONTH, -1); // 先月1日
		
		final Calendar endAt = Calendar.getInstance();
		endAt.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH), 1, 0, 0, 0); // 今月1日
		endAt.set(Calendar.MILLISECOND, 0); // きっちりミリ秒も0にする
		endAt.add(Calendar.MONTH, 2); // 再来月1日
		endAt.add(Calendar.MILLISECOND, -1); // 来月月末
		
		return new Duration(startAt.getTime(), endAt.getTime());
	}
}
