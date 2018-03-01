package dk.via.health;

import java.util.ArrayList;

public class HealthRecord {
	private ArrayList<Measurement> measurements;
	
	public HealthRecord() {
		measurements = new ArrayList<>();
	}
	
	public void addMeasurement(Measurement measurement) {
		measurements.add(measurement);
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
