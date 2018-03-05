package dk.via.health;

public class TemperatureAlarm implements MeasurementObserver {
	@Override
	public void notify(Measurement measurement) {
		if (measurement instanceof SingleValue && measurement.getType() == Type.TEMPERATURE) {
			SingleValue sv = (SingleValue) measurement;
			if (sv.getValue() >= 40) {
				System.out.println("Alarm");
			}
		}
	}
}
