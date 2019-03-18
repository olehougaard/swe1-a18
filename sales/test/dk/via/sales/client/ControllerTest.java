package dk.via.sales.client;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;

import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import dk.via.sales.model.Item;
import dk.via.sales.model.Money;
import dk.via.sales.model.Order;
import dk.via.sales.model.OrderLine;

public class ControllerTest extends EasyMockSupport {
	@Rule public EasyMockRule rule = new EasyMockRule(this);
	@Mock private Model mockModel;
	@Mock private View mockView;
	private Model stubModel;
	private Controller controller;
	private Controller mockedController;
	private List<Item> items;
	private OrderManagerStub orderManager;

	@Before
	public void setUp() throws Exception {
		orderManager = new OrderManagerStub(
				new Item(1, "Screwdriver", new Money(8, "USD")),	
				new Item(1, "Hammer", new Money(12, "USD")),	
				new Item(1, "Drill", new Money(198, "USD")));
		stubModel = new ModelManager(orderManager);
		items = stubModel.getItems();
		controller = new Controller(mockView, stubModel);
		mockedController = new Controller(mockView, mockModel);
	}

	@Test
	public void initializationUsesItemsAndCustomerFromModel() {
		mockView.displayCustomer(stubModel.getCustomer());
		mockView.displayItems(stubModel.getItems());
		replayAll();
		controller.reset();
		verifyAll();
	} 
	
	@Test
	public void addItemAddsAnItemToTheModelAndUpdatesView() {
		List<OrderLine> orderLines = Arrays.asList(new OrderLine(1, items.get(0)));
		expect(mockView.getItemIndex()).andReturn(0);
		mockView.displayOrderLines(orderLines);
		replayAll();
		controller.addItemToOrder();
		assertEquals(orderLines, stubModel.getOrderLines());
		verifyAll();
	}
	
	@Test
	public void addItemDoesNothingIfNoItemIsSelected() {
		expect(mockView.getItemIndex()).andReturn(-1);
		replayAll();
		controller.addItemToOrder();
		assertTrue(stubModel.getOrderLines().isEmpty());
		verifyAll();
	}
	
	@Test
	public void addingTheSameItemTwiceUpdatesTheAmount() {
		List<OrderLine> orderLines1 = Arrays.asList(new OrderLine(1, items.get(0)));
		List<OrderLine> orderLines2 = Arrays.asList(new OrderLine(2, items.get(0)));
		expect(mockView.getItemIndex()).andReturn(0);
		mockView.displayOrderLines(orderLines1);
		expect(mockView.getItemIndex()).andReturn(0);
		mockView.displayOrderLines(orderLines2);
		replayAll();
		controller.addItemToOrder();
		controller.addItemToOrder();
		assertEquals(orderLines2, stubModel.getOrderLines());
		verifyAll();
	}
	
	@Test
	public void addingDifferentItemsCreatesMultipleOrderLines() {
		List<OrderLine> orderLines1 = Arrays.asList(new OrderLine(1, items.get(0)));
		List<OrderLine> orderLines2 = Arrays.asList(new OrderLine(1, items.get(2)), new OrderLine(1, items.get(0)));
		expect(mockView.getItemIndex()).andReturn(0);
		mockView.displayOrderLines(orderLines1);
		expect(mockView.getItemIndex()).andReturn(2);
		mockView.displayOrderLines(orderLines2);
		replayAll();
		controller.addItemToOrder();
		controller.addItemToOrder();
		assertEquals(orderLines2, stubModel.getOrderLines());
		verifyAll();
	}
	
	@Test
	public void addingOrderLinesDoesNotSaveTheOrder() {
		List<OrderLine> orderLines = Arrays.asList(new OrderLine(1, items.get(0)));
		expect(mockView.getItemIndex()).andReturn(0);
		mockView.displayOrderLines(orderLines);
		replayAll();
		controller.addItemToOrder();
		assertEquals(0, orderManager.getOrdersFor(stubModel.getCustomer()).size());
		verifyAll();
	}

	
	@Test
	public void confirmingOrderSavesTheOrderAndResetsTheView() {
		List<OrderLine> orderLines = Arrays.asList(new OrderLine(1, items.get(0)));
		expect(mockView.getItemIndex()).andReturn(0);
		mockView.displayOrderLines(orderLines);
		mockView.displayItems(items);
		mockView.displayOrderLines(Arrays.asList());
		replayAll();

		controller.addItemToOrder();
		controller.confirmOrder();
		List<Order> orders = orderManager.getOrdersFor(stubModel.getCustomer());
		assertEquals(1, orders.size());
		assertEquals(orderLines, orders.get(0).getLines());
		verifyAll();
	}
	
	@Test
	public void confirmingAnEmptyOrderDoesNothing() throws Exception {
		expect(mockModel.getOrderLines()).andReturn(Arrays.asList());
		replayAll();
		mockedController.confirmOrder();
		verifyAll();
	}
	
	@Test
	public void exceptionsFromTheModelIsShownInView() throws Exception {
		List<OrderLine> orderLines = Arrays.asList(new OrderLine(1, items.get(0)));
		expect(mockView.getItemIndex()).andReturn(0);
		expect(mockModel.getItems()).andReturn(items);
		mockModel.addItem(items.get(0));
		expect(mockModel.getOrderLines()).andReturn(orderLines);
		mockView.displayOrderLines(orderLines);
		RemoteException e = new RemoteException();
		expect(mockModel.getOrderLines()).andReturn(orderLines);
		mockModel.save();
		expectLastCall().andThrow(e);
		mockView.displayException(e);
		replayAll();

		mockedController.addItemToOrder();
		mockedController.confirmOrder();
		verifyAll();
	}

}
