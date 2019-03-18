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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderLine other = (OrderLine) obj;
		if (amount != other.amount)
			return false;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		return true;
	}
}
