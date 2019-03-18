package dk.via.sales.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import dk.via.sales.client.OrderManagerStub;
import dk.via.sales.data.SalesPersistence;
import dk.via.sales.model.Customer;
import dk.via.sales.model.Item;
import dk.via.sales.model.Money;
import dk.via.sales.model.Order;
import dk.via.sales.model.OrderLine;

class SalesPersistenceStub implements SalesPersistence {
	private OrderManagerStub orderManager;
	private HashMap<String, Customer> customers;
	
	public SalesPersistenceStub() {
		new ArrayList<>();
		customers = new HashMap<>();
		new HashMap<>();
		orderManager = new OrderManagerStub();
	}
	
	@Override
	public void updateCustomer(Customer customer) {
		if (customers.containsKey(customer.getEmail())) {
			customers.put(customer.getEmail(), customer);
		}
		
	}

	@Override
	public List<Order> getOrdersForCustomer(Customer customer) {
		return orderManager.getOrdersFor(customer);
	}

	@Override
	public List<Item> getItems() {
		return orderManager.getItems();
	}

	@Override
	public List<Customer> getCustomers() {
		return new ArrayList<>(customers.values());
	}

	@Override
	public Customer getCustomerByEmail(String email) {
		return customers.get(email);
	}

	@Override
	public void deleteCustomer(Customer customer) {
		customers.remove(customer.getEmail());
	}

	@Override
	public Order createOrderForCustomer(Customer customer, String currency, Collection<OrderLine> lines) {
		return orderManager.createOrderForCustomer(customer, currency, lines);
	}

	@Override
	public Item createItem(String name, Money price) {
		return orderManager.createItem(name, price);
	}

	@Override
	public void createCustomer(Customer customer) {
		customers.put(customer.getEmail(), customer);
	}
}