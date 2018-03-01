package dk.via.health;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		HealthRecord hr = new HealthRecord();
		hr.addMeasurement(new BloodPressure(122, 77));
		ArrayList<Measurement> pressures = hr.getByType(Type.BLOOD_PRESSURE);
		Extractor extractor = new Extractor(pressures);
		Aggregator aggregator = new Aggregator(extractor.extractSystolic());
		System.out.println(aggregator.getAverage());
	}

}
