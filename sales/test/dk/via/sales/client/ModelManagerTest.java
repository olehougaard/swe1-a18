package dk.via.sales.client;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dk.via.sales.model.Item;
import dk.via.sales.model.Money;
import dk.via.sales.model.OrderLine;

public class ModelManagerTest {
	private Model manager;
	private List<Item> items;
	private OrderManagerStub model;
	
	@Before
	public void setUp() throws Exception {
		model = new OrderManagerStub();
		manager = new ModelManager(model);
		items = Arrays.asList(
				new Item(1, "Screwdriver", new Money(8, "USD")),	
				new Item(2, "Hammer", new Money(12, "USD")),	
				new Item(3, "Drill", new Money(198, "USD")));
	}
	
	@Test
	public void initialManagerHasNoOrderLines() {
		assertEquals(0, manager.getOrderLines().size());
	}

	@Test
	public void testScenario() throws Exception {
		for(Item item: items) model.createItem(item.getName(), item.getPrice());
		manager.addItem(items.get(0));
		manager.addItem(items.get(0));
		manager.addItem(items.get(2));
		List<OrderLine> orderLines = manager.getOrderLines();
		Collections.sort(orderLines, new Comparator<OrderLine>() {
			@Override
			public int compare(OrderLine o1, OrderLine o2) {
			return Integer.compare(o1.getItem().getItemNumber(), o2.getItem().getItemNumber());
			}
		});
		assertEquals(2, orderLines.size());
		assertEquals(items.get(0), orderLines.get(0).getItem());
		assertEquals(2, orderLines.get(0).getAmount());
		assertEquals(items.get(2), orderLines.get(1).getItem());
		assertEquals(1, orderLines.get(1).getAmount());
	}
}
