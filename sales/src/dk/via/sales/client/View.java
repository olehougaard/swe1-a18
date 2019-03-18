package dk.via.sales.client;

import java.util.List;

import dk.via.sales.model.Customer;
import dk.via.sales.model.Item;
import dk.via.sales.model.OrderLine;

public interface View {

	void setController(Controller controller);

	void displayCustomer(Customer customer);

	void displayItems(List<Item> items);

	void displayOrderLines(List<OrderLine> lines);

	int getItemIndex();

	void displayException(Exception e);

}