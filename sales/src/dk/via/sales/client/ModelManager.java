package dk.via.sales.client;

import java.rmi.RemoteException;
import java.util.List;

import dk.via.sales.model.Customer;
import dk.via.sales.model.Item;
import dk.via.sales.model.Order;
import dk.via.sales.model.OrderLine;
import dk.via.sales.server.OrderManager;

public class ModelManager implements Model {
	private Customer customer;
	private Order order;
	private OrderManager model;
	private List<Item> items;
	
	public ModelManager(OrderManager model) throws RemoteException {
		customer = new Customer("oih@via.dk", "Ole Hougaard");
		order = new Order(-1, "USD");
		this.model = model;
		items = model.getItems();
	}
	
	@Override
	public List<Item> getItems() {
		return items;
	}
	
	@Override
	public Customer getCustomer() {
		return customer;
	}
	
	@Override
	public void addItem(Item item) {
		order.add(1, item);
	}
	
	@Override
	public List<OrderLine> getOrderLines() {
		return order.getLines();
	}
	
	@Override
	public void save() throws RemoteException {
		model.createOrderForCustomer(customer, order.getCurrency(), order.getLines());
	}

	@Override
	public void clear() throws RemoteException {
		items = model.getItems();
		order = new Order(-1, order.getCurrency());
	}
}
