package dk.via.health;

import java.util.ArrayList;
import java.util.List;

public class Extractor {
	private List<Measurement> measurements;

	public Extractor(List<Measurement> measurements) {
		this.measurements = measurements;
	}
	
	// Note the use of casting in this and the following methods.
	// This shows a violation of the Liskov Substitution principle.
	// It's also abundantly clear that there is a lot of repetition 
	// going on in violation of the DRY principle.
	// We'll fix that when we get proper tools.
	public List<Double> extractValues() {
		List<Double> values = new ArrayList<>();
		for(int i = 0; i < measurements.size(); i++)
			values.add(((SingleValue) measurements.get(i)).getValue());
		return values;
	}
	
	public List<Double> extractSystolic() {
		List<Double> values = new ArrayList<>();
		for(int i = 0; i < measurements.size(); i++)
			values.add(((BloodPressure) measurements.get(i)).getSystolic());
		return values;
	}
	
	public List<Double> extractDiastolic() {
		List<Double> values = new ArrayList<>();
		for(int i = 0; i < measurements.size(); i++)
			values.add(((BloodPressure) measurements.get(i)).getDiastolic());
		return values;
	}
	
	public List<Double> extractLdl() {
		List<Double> values = new ArrayList<>();
		for(int i = 0; i < measurements.size(); i++)
			values.add(((Cholestorol) measurements.get(i)).getLdl());
		return values;
	}
	
	public List<Double> extractHdl() {
		List<Double> values = new ArrayList<>();
		for(int i = 0; i < measurements.size(); i++)
			values.add(((Cholestorol) measurements.get(i)).getHdl());
		return values;
	}
	
	public List<Double> extractTotalCholesterol() {
		List<Double> values = new ArrayList<>();
		for(int i = 0; i < measurements.size(); i++)
			values.add(((Cholestorol) measurements.get(i)).getTotal());
		return values;
	}
}
