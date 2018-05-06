package dk.via.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

import dk.via.sales.model.Customer;
import dk.via.sales.model.Item;
import dk.via.sales.model.Order;
import dk.via.sales.model.OrderLine;

public interface SalesModel extends Remote {
	List<Customer> getCustomers() throws RemoteException;
	Customer getCustomerByEmail(String email) throws RemoteException;
	void createCustomer(Customer customer) throws RemoteException;
	void updateCustomer(Customer customer) throws RemoteException;
	void deleteCustomer(Customer customer) throws RemoteException;
	
	List<Order> getOrdersFor(Customer customer) throws RemoteException;
	Order createOrderForCustomer(Customer customer, String currency, Collection<OrderLine> lines) throws RemoteException;
	
	List<Item> getItems() throws RemoteException;
}
