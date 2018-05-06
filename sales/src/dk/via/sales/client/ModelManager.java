package dk.via.sales.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import dk.via.sales.model.*;
import dk.via.server.SalesModel;

public class ModelManager {
	private Customer customer;
	private Order order;
	private SalesModel model;
	private List<Item> items;
	
	public ModelManager() throws RemoteException, NotBoundException {
		customer = new Customer("oih@via.dk", "Ole Hougaard");
		order = new Order(-1, "USD");
		Registry registry = LocateRegistry.getRegistry(1099);
		model = (SalesModel) registry.lookup("sales");
		items = model.getItems();
	}
	
	public List<Item> getItems() {
		return items;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	
	public void addItem(Item item) {
		order.add(1, item);
	}
	
	public List<OrderLine> getOrderLines() {
		return order.getLines();
	}
	
	public void save() throws RemoteException {
		model.createOrderForCustomer(customer, order.getCurrency(), order.getLines());
	}

	public void clear() throws RemoteException {
		items = model.getItems();
		order = new Order(-1, order.getCurrency());
	}
}
