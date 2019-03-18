package dk.via.sales.server;

import java.rmi.RemoteException;
import java.util.List;

import dk.via.sales.model.Customer;

public interface CustomerManager {

	List<Customer> getCustomers() throws RemoteException;

	Customer getCustomerByEmail(String email) throws RemoteException;

	void createCustomer(Customer customer) throws RemoteException;

	void updateCustomer(Customer customer) throws RemoteException;

	void deleteCustomer(Customer customer) throws RemoteException;
}