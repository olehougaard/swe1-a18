package dk.via.sales.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Order {
	private String currency;
	private HashMap<Item, OrderLine> orderLines;
	private int id;
	
	public Order(int id, String currency) {
		this.id = id;
		this.currency = currency;
		this.orderLines = new HashMap<>();
	}
	
	public int getId() {
		return id;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	private void update(OrderLine orderLine) {
		orderLines.put(orderLine.getItem(), orderLine);
	}
	
	public Money getPrice() {
		Money price = Money.ZERO;
		for(OrderLine line: orderLines.values()) {
			price = price.add(line.getPrice());
		}
		return price;
	}

	public void add(int amount, Item item) {
		if (!item.getPrice().isCompatible(currency)) {
			throw new IllegalArgumentException("Incompatible currencies");
		}
		if (orderLines.containsKey(item)) {
			update(orderLines.get(item).add(amount));
		} else {
			orderLines.put(item, new OrderLine(amount, item));
		}
	}

	public void remove(int amount, Item item) {
		if (!orderLines.containsKey(item)) {
			throw new IllegalStateException("Item doesn't exist");
		}
		if (orderLines.get(item).getAmount() == amount) {
			orderLines.remove(item);
		} else {
			update(orderLines.get(item).remove(amount));
		}
	}
	
	public List<OrderLine> getLines() {
		return new ArrayList<>(orderLines.values());
	}
}
