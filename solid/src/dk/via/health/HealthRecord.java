package dk.via.health;

import java.util.ArrayList;

public class HealthRecord {
	public enum Type { TEMPERATURE, PULSE, BLOOD_PRESSURE, CHOLESTEROL };
	
	private ArrayList<Type> types;
	private ArrayList<Double> value1;
	private ArrayList<Double> value2;
	private ArrayList<Double> value3;
	
	public HealthRecord() {
		types = new ArrayList<>();
		value1 = new ArrayList<>();
		value2 = new ArrayList<>();
		value3 = new ArrayList<>();
	}
	
	public void addTemperature(double degrees) {
		types.add(Type.TEMPERATURE);
		value1.add(degrees);
	}
	
	public void addPulse(double bpm) {
		types.add(Type.PULSE);
		value1.add(bpm);
	}
	
	public void addBloodPressure(double systolic, double diastolic) {
		types.add(Type.BLOOD_PRESSURE);
		value1.add(systolic);
		value2.add(diastolic);
	}
	
	public void addCholesterol(double ldl, double hdl, double total) {
		types.add(Type.CHOLESTEROL);
		value1.add(ldl);
		value2.add(hdl);
		value3.add(total);
	}
	
	public double getAverageTemperature() {
		int count = 0;
		double total = 0;
		for(int i = 0; i < types.size(); i++) {
			if (types.get(i) == Type.TEMPERATURE) {
				count++;
				total += value1.get(i);
			}
		}
		if (count == 0) 
			return Double.NaN;
		else
			return total / count;
	}

	public double getAverageSystolic() {
		int count = 0;
		double total = 0;
		for(int i = 0; i < types.size(); i++) {
			if (types.get(i) == Type.BLOOD_PRESSURE) {
				count++;
				total += value1.get(i);
			}
		}
		if (count == 0) 
			return Double.NaN;
		else
			return total / count;
	}

	public double getAverageDiastolic() {
		int count = 0;
		double total = 0;
		for(int i = 0; i < types.size(); i++) {
			if (types.get(i) == Type.BLOOD_PRESSURE) {
				count++;
				total += value1.get(i);
			}
		}
		if (count == 0) 
			return Double.NaN;
		else
			return total / count;
	}
	
	public void printRecord() {
		for(int i = 0; i < types.size(); i++) {
			switch(types.get(i)) {
			case TEMPERATURE:
				System.out.println(value1.get(i) + " °C");
				break;
			case PULSE:
				System.out.println(value1.get(i) + " beats / minute");
				break;
			case BLOOD_PRESSURE:
				System.out.println(value1.get(i) + " / " + value2.get(i) + " mmHg");
				break;
			case CHOLESTEROL:
				System.out.println("LDL  : " + value1.get(i) + " mg / L");
				System.out.println("HDL  : " + value2.get(i) + " mg / L");
				System.out.println("Total: " + value3.get(i) + " mg / L");
				System.out.println();
			}
		}
	}
}
