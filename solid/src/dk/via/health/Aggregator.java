package dk.via.health;

import java.util.List;

public class Aggregator {
	private List<Double> values;

	public Aggregator(List<Double> values) {
		this.values = values;
	}
	
	public double getAverage() {
		if (values.size() == 0) 
			return Double.NaN;
		else
			return getSum() / values.size();
	}

	private double getSum() {
		double sum = 0;
		for(int i = 0; i < values.size(); i++) {
			sum += values.get(i);
		}
		return sum;
	}
}
