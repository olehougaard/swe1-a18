package dk.via.health;

import java.util.ArrayList;

public class HealthRecord {
	private ArrayList<Measurement> measurements;
	private ArrayList<MeasurementObserver> observers;
	
	public HealthRecord() {
		measurements = new ArrayList<>();
		observers = new ArrayList<>();
	}
	
	public void addObserver(MeasurementObserver observer) {
		observers.add(observer);
	}
	
	public void addMeasurement(Measurement measurement) {
		measurements.add(measurement);
		for (MeasurementObserver observer: observers)
			observer.notify(measurement);
	}
	
	public ArrayList<Measurement> getByType(Type type) {
		ArrayList<Measurement> result = new ArrayList<>();
		for(int i = 0; i < measurements.size(); i++) {
			if (measurements.get(i).getType() == type)
				result.add(measurements.get(i));
		}
		return result;
	}
	public void printRecord() {
		for(int i = 0; i < measurements.size(); i++) {
			System.out.println(measurements.toString());
		}
	}
}
