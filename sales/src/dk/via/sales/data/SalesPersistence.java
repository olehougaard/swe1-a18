package dk.via.sales.data;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import dk.via.sales.model.Customer;
import dk.via.sales.model.Item;
import dk.via.sales.model.Money;
import dk.via.sales.model.Order;
import dk.via.sales.model.OrderLine;

public interface SalesPersistence {

	List<Customer> getCustomers() throws SQLException;

	Customer getCustomerByEmail(String email) throws SQLException;

	void createCustomer(Customer customer) throws SQLException;

	void updateCustomer(Customer customer) throws SQLException;

	void deleteCustomer(Customer customer) throws SQLException;

	List<Order> getOrdersForCustomer(Customer customer) throws SQLException;

	Order createOrderForCustomer(Customer customer, String currency, Collection<OrderLine> lines) throws SQLException;

	Item createItem(String name, Money price) throws SQLException;

	List<Item> getItems() throws SQLException;

}