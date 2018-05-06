package dk.via.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import dk.via.sales.data.Persistence;
import dk.via.sales.model.Customer;
import dk.via.sales.model.Item;
import dk.via.sales.model.Order;
import dk.via.sales.model.OrderLine;

public class SalesModelManager extends UnicastRemoteObject implements SalesModel {
	private static final long serialVersionUID = 1L;
	private Persistence persistence;

	protected SalesModelManager() throws RemoteException {
		try {
			this.persistence = new Persistence();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public List<Customer> getCustomers() throws RemoteException {
		try {
			return persistence.getCustomers();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public Customer getCustomerByEmail(String email) throws RemoteException {
		try {
			return persistence.getCustomerByEmail(email);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public void createCustomer(Customer customer) throws RemoteException {
		try {
			persistence.createCustomer(customer);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public void updateCustomer(Customer customer) throws RemoteException {
		try {
			persistence.updateCustomer(customer);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public void deleteCustomer(Customer customer) throws RemoteException {
		try {
			persistence.deleteCustomer(customer);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public List<Order> getOrdersFor(Customer customer) throws RemoteException {
		try {
			return persistence.getOrdersForCustomer(customer);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public Order createOrderForCustomer(Customer customer, String currency, Collection<OrderLine> lines)
			throws RemoteException {
		try {
			return persistence.createOrderForCustomer(customer, currency, lines);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RemoteException(e.getMessage(), e);
		}
	}

	@Override
	public List<Item> getItems() throws RemoteException {
		try {
			return persistence.getItems();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RemoteException(e.getMessage(), e);
		}
	}

}
