package dk.via.sales.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import dk.via.sales.model.Customer;
import dk.via.sales.model.Item;
import dk.via.sales.model.Money;
import dk.via.sales.model.Order;
import dk.via.sales.model.OrderLine;
import dk.via.sales.server.OrderManager;

public class OrderManagerStub implements OrderManager {
	private ArrayList<Item> items;
	private HashMap<String, List<Order>> orders;
	private int orderCount;
	
	public OrderManagerStub() {
		items = new ArrayList<>();
		orders = new HashMap<>();
		orderCount = 0;
	}
	
	public OrderManagerStub(Item... items) {
		this();
		this.items.addAll(Arrays.asList(items));
	}
	
	@Override
	public Item createItem(String name, Money price) {
		Item item = new Item(items.size()+1, name, price);
		items.add(item);
		return item;
	}

	@Override
	public List<Item> getItems() {
		return items;
	}

	@Override
	public List<Order> getOrdersFor(Customer customer) {
		String key = customer.getEmail();
		if (!orders.containsKey(key)) {
			orders.put(key, new ArrayList<>());
		}
		return orders.get(key);
	}

	@Override
	public Order createOrderForCustomer(Customer customer, String currency, Collection<OrderLine> lines) {
		Order order = new Order(++orderCount, currency);
		for(OrderLine line: lines) {
			order.add(line.getAmount(), line.getItem());
		}
		getOrdersFor(customer).add(order);
		return order;
	}

}
