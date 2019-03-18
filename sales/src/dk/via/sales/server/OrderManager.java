package dk.via.sales.server;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

import dk.via.sales.model.Customer;
import dk.via.sales.model.Item;
import dk.via.sales.model.Money;
import dk.via.sales.model.Order;
import dk.via.sales.model.OrderLine;

public interface OrderManager {
	Item createItem(String name, Money price) throws RemoteException;

	List<Item> getItems() throws RemoteException;

	Order createOrderForCustomer(Customer customer, String currency, Collection<OrderLine> lines)
			throws RemoteException;
	
	List<Order> getOrdersFor(Customer customer) throws RemoteException;
}