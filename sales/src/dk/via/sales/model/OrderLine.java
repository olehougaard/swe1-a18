package dk.via.sales.model;

import java.io.Serializable;

public class OrderLine implements Serializable {
	private static final long serialVersionUID = 1L;

	private int amount;
	private Item item;
	
	private static void checkNonNegative(int amount) {
		if (amount < 0) throw new IllegalArgumentException("Negative amounts not allowed");
	}
	
	public OrderLine(int amount, Item item) {
		checkNonNegative(amount);
		this.amount = amount;
		this.item = item;
	}

	public int getAmount() {
		return amount;
	}

	public Item getItem() {
		return item;
	}

	public Money getPrice() {
		return item.getPrice().multiply(amount);
	}

	public OrderLine add(int amount) {
		checkNonNegative(amount);
		return new OrderLine(this.amount + amount, item);
	}

	public OrderLine remove(int amount) {
		checkNonNegative(amount);
		return new OrderLine(this.amount - amount, item);
	}
}
