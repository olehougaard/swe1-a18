package dk.via.sales.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import dk.via.sales.model.Customer;
import dk.via.sales.model.Item;
import dk.via.sales.model.Money;
import dk.via.sales.model.Order;
import dk.via.sales.model.OrderLine;

public class Persistence extends DataAccessObject implements SalesPersistence {
	public Persistence() throws SQLException {
	}

	private List<Customer> getCustomers(String sql, Object... values) throws SQLException {
		List<Customer> customers = new ArrayList<>();
		try (Connection connection = getConnection()) {
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				for (int i = 0; i < values.length; i++) { 
					statement.setObject(i + 1, values[i]);
				}
				ResultSet rs = statement.executeQuery();
				while (rs.next()) {
					customers.add(new Customer(rs.getString("email"), rs.getString("name")));
				}
			}
		}
		return customers;
	}

	/* (non-Javadoc)
	 * @see dk.via.sales.data.SalesPersitence#getCustomers()
	 */
	@Override
	public List<Customer> getCustomers() throws SQLException {
		return getCustomers("SELECT * FROM Customer");
	}

	/* (non-Javadoc)
	 * @see dk.via.sales.data.SalesPersitence#getCustomerByEmail(java.lang.String)
	 */
	@Override
	public Customer getCustomerByEmail(String email) throws SQLException {
		List<Customer> customers = getCustomers("SELECT * FROM Customer WHERE email = ?", email);
		if (customers.isEmpty())
			return null;
		else
			return customers.get(0);
	}
	
	/* (non-Javadoc)
	 * @see dk.via.sales.data.SalesPersitence#createCustomer(dk.via.sales.model.Customer)
	 */
	@Override
	public void createCustomer(Customer customer) throws SQLException {
		try(Connection connection = getConnection()) {
			try(PreparedStatement statement = connection.prepareStatement("INSERT INTO Customer(email, name) VALUES (?, ?)")) {
				statement.setString(1, customer.getEmail());
				statement.setString(2, customer.getName());
				statement.executeUpdate();
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see dk.via.sales.data.SalesPersitence#updateCustomer(dk.via.sales.model.Customer)
	 */
	@Override
	public void updateCustomer(Customer customer) throws SQLException {
		try(Connection connection = getConnection()) {
			try(PreparedStatement statement = connection.prepareStatement("UPDATE Customer SET name = ? WHERE email = ?")) {
				statement.setString(1, customer.getName());
				statement.setString(2, customer.getEmail());
				statement.executeUpdate();
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see dk.via.sales.data.SalesPersitence#deleteCustomer(dk.via.sales.model.Customer)
	 */
	@Override
	public void deleteCustomer(Customer customer) throws SQLException {
		try(Connection connection = getConnection()) {
			try(PreparedStatement statement = connection.prepareStatement("DELETE FROM Customer WHERE email = ?")) {
				statement.setString(1, customer.getEmail());
				statement.executeUpdate();
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see dk.via.sales.data.SalesPersitence#getOrdersForCustomer(dk.via.sales.model.Customer)
	 */
	@Override
	public List<Order> getOrdersForCustomer(Customer customer) throws SQLException {
		HashMap<Integer, Order> orders = new HashMap<>();
		HashMap<Integer, Item> items = new HashMap<>();
		try(Connection connection = getConnection()) {
			try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM Orders JOIN Order_Line USING(order_number) JOIN Item USING(item_number) WHERE Orders.customer = ?")) {
				statement.setString(1, customer.getEmail());
				ResultSet rs = statement.executeQuery();
				while(rs.next()) {
					int orderNumber = rs.getInt("order_number");
					if (!orders.containsKey(orderNumber)) {
						orders.put(orderNumber, new Order(orderNumber, rs.getString("currency")));
					}
					int itemNumber = rs.getInt("item_number");
					if (!items.containsKey(itemNumber)) {
						Money price = new Money(rs.getDouble("price_amount"), rs.getString("price_currency"));
						items.put(itemNumber, new Item(itemNumber, rs.getString("name"), price));
					}
					Order order = orders.get(orderNumber);
					order.add(rs.getInt("amount"), items.get(itemNumber));
				}
			}
		}
		return new ArrayList<>(orders.values());
	}
	
	/* (non-Javadoc)
	 * @see dk.via.sales.data.SalesPersitence#createOrderForCustomer(dk.via.sales.model.Customer, java.lang.String, java.util.Collection)
	 */
	@Override
	public Order createOrderForCustomer(Customer customer, String currency, Collection<OrderLine> lines) throws SQLException {
		try(Connection connection = getConnection()) {
			// We're making a lot of (potentially, but at least 2) inserts into the database. These needs to be in a transaction, because they belong together.
			connection.setAutoCommit(false);
			// Note that the order_number of Orders is SERIAL. That is, it's auto-generated. In order to return the correct number to the client, 
			// we need to get it from the database. That's what the RETURN_GENERATED_KEYS is about.
			try(PreparedStatement statement = connection.prepareStatement("INSERT INTO Orders(currency, customer) VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
				statement.setString(1, currency);
				statement.setString(2, customer.getEmail());
				statement.executeUpdate();
				// This is where we actually get the generated key (there should be exactly one).
				ResultSet keys = statement.getGeneratedKeys();
				if (keys.next()) {
					// To allow the client to work with the correct id, we return a fully correct Order. 
					Order newOrder = new Order(keys.getInt(1), currency);
					// We prepare to insert all the order lines. The INSERT statement is the same, we just need to insert different values every time. 
					try(PreparedStatement orderLineStatement = connection.prepareStatement("INSERT INTO Order_Line(order_number, item_number, amount) VALUES (?, ?, ?)")) {
						for(OrderLine orderLine: lines) {
							orderLineStatement.setInt(1, newOrder.getId());
							orderLineStatement.setInt(2, orderLine.getItem().getItemNumber());
							orderLineStatement.setInt(3, orderLine.getAmount());
							// We could use orderLineStatement.executeUpdate() here, but it's more efficient to add every INSERT to a batch and execute the batch at the end.
							orderLineStatement.addBatch();
							newOrder.add(orderLine.getAmount(), orderLine.getItem());
						}
						// Here we execute all the INSERTs at once.
						orderLineStatement.executeBatch();
					}
					// If we get here, everything went well, so we commit.
					connection.commit();
					return newOrder;
				}
			}
		}
		// When the connection closes, everything is rolled back if we didn't commit. This means that we don't risk having part of an order in the system.
		return null;
	}
	
	/* (non-Javadoc)
	 * @see dk.via.sales.data.SalesPersitence#createItem(java.lang.String, dk.via.sales.model.Money)
	 */
	@Override
	public Item createItem(String name, Money price) throws SQLException {
		try(Connection connection = getConnection()) {
			try(PreparedStatement statement = connection.prepareStatement("INSERT INTO Item(name, price_amount, price_currency) VALUES (?, ?, ?)", 
					PreparedStatement.RETURN_GENERATED_KEYS)) {
				statement.setString(1, name);
				statement.setDouble(2, price.getAmount());
				statement.setString(3, price.getCurrency());
				statement.executeUpdate();
				// This is where we actually get the generated key (there should be exactly one).
				ResultSet keys = statement.getGeneratedKeys();
				if (keys.next()) {
					return new Item(keys.getInt(1), name, price);
				}
			}
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see dk.via.sales.data.SalesPersitence#getItems()
	 */
	@Override
	public List<Item> getItems() throws SQLException {
		List<Item> items = new ArrayList<Item>();
		try(Connection connection = getConnection()) {
			try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM Item")) {
				ResultSet rs = statement.executeQuery();
				while(rs.next()) {
					Money price = new Money(rs.getDouble("price_amount"), rs.getString("price_currency"));
					items.add(new Item(rs.getInt("item_number"), rs.getString("name"), price));
				}
			}
		}
		return items;
	}
}
