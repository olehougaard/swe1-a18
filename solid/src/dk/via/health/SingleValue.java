package dk.via.health;

public class SingleValue extends Measurement {
	private double value;
	private Type type;
	
	public SingleValue(double value, Type type) {
		this.value = value;
		this.type = type;
	}

	public double getValue() {
		return value;
	}

	public Type getType() {
		return type;
	}
}
