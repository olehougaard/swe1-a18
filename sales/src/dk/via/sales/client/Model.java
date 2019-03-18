package dk.via.sales.client;

import java.rmi.RemoteException;
import java.util.List;

import dk.via.sales.model.Customer;
import dk.via.sales.model.Item;
import dk.via.sales.model.OrderLine;

public interface Model {

	List<Item> getItems();

	Customer getCustomer();

	void addItem(Item item);

	List<OrderLine> getOrderLines();

	void save() throws RemoteException;

	void clear() throws RemoteException;

}