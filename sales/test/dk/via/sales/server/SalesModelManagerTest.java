package dk.via.sales.server;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Arrays;

import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import dk.via.sales.data.SalesPersistence;
import dk.via.sales.model.Customer;
import dk.via.sales.model.Item;
import dk.via.sales.model.Money;

public class SalesModelManagerTest extends EasyMockSupport {
	private SalesPersistenceStub stub;
	@Rule public EasyMockRule rule = new EasyMockRule(this);
	@Mock private SalesPersistence mock;
	private SalesModelManager stubbedManager;
	private SalesModelManager mockedManager;

	@Before
	public void setUp() throws Exception {
		stub = new SalesPersistenceStub();
		stubbedManager = new SalesModelManager(stub);
		mockedManager = new SalesModelManager(mock);
	}
	
	@Test
	public void SalesModelManagerIsRemote() {
		assertTrue(stubbedManager instanceof Remote);
	}

	@Test
	public void testGetItems() throws RemoteException {
		assertEquals(0, stubbedManager.getItems().size());
	}
	
	@Test
	public void addingItem() throws RemoteException {
		Item item = stubbedManager.createItem("Screwdriver", new Money(8, "USD"));
		assertEquals(1, stubbedManager.getItems().size());
		assertEquals(item, stubbedManager.getItems().get(0));
	}
	
	@Test
	public void createCustomerMock() throws Exception {
		Customer customer = new Customer("abc@example.com", "A B C");
		mock.createCustomer(customer);
		replayAll();
		mockedManager.createCustomer(customer);
		verifyAll();
	}
	
	@Test
	public void addingItemMock() throws Exception {
		Item item = new Item(2, "Screwdriver", new Money(8, "USD"));
		expect(mock.createItem("Screwdriver", new Money(8, "USD")))
			.andReturn(item);
		expect(mock.getItems()).andReturn(Arrays.asList(new Item[] { item }));
		expect(mock.getItems()).andReturn(Arrays.asList(new Item[] { item }));
		replayAll();
		
		mockedManager.createItem("Screwdriver", new Money(8, "USD"));
		assertEquals(1, mockedManager.getItems().size());
		assertEquals(item, mockedManager.getItems().get(0));
		verifyAll();
	}
	
	@Test(expected=RemoteException.class)
	public void getItemsThrowsRemoteExceptionOnSQLException() throws Exception {
		expect(mock.getItems()).andThrow(new SQLException());
		replayAll();
		mockedManager.getItems();
		verifyAll();
	}
}
