package dk.via.health;

public class BloodPressure implements Measurement {
	private double systolic;
	private double diastolic;

	public BloodPressure(double systolic, double diastolic) {
		this.systolic = systolic;
		this.diastolic = diastolic;
	}

	public double getSystolic() {
		return systolic;
	}

	public double getDiastolic() {
		return diastolic;
	}
	
	public Type getType() {
		return Type.BLOOD_PRESSURE;
	}
	
	@Override
	public String toString() {
		return diastolic + "/" + systolic + " mmHg";
	}
}
