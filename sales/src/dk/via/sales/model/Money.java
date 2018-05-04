package dk.via.sales.model;

public class Money {
	public static final Money ZERO = new Money(0, "");
	private double amount;
	private String currency;

	public Money(double amount, String currency) {
		if (currency == null) {
			throw new IllegalArgumentException("Currency is required");
		}
		this.amount = amount;
		this.currency = currency;
	}

	public String getCurrency() {
		return currency;
	}

	public double getAmount() {
		return amount;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Money)) {
			return false;
		}
		Money other = (Money) obj;
		if (amount == 0) return other.amount == 0;
		return amount == other.amount && currency.equals(other.currency);
	}
	
	@Override
	public int hashCode() {
		return Double.hashCode(amount) ^ currency.hashCode();
	}

	public Money add(Money other) {
		if (other.equals(Money.ZERO)) {
			return this;
		} else if (this.equals(Money.ZERO)) {
			return other;
		} else if (!other.currency.equals(currency)) {
			throw new IllegalArgumentException("Incompatible currencies");
		} else {
			return new Money(amount + other.amount, currency);
		}
	}

	public Money multiply(double factor) {
		return new Money(factor * amount, currency);
	}
	
	@Override
	public String toString() {
		return String.format("%.2f %s", amount, currency);
	}

	public boolean isCompatible(String currency) {
		return this.currency.equals(currency);
	}
}
