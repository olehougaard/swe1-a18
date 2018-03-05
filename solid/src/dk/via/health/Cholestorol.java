package dk.via.health;

public class Cholestorol implements Measurement {
	private double ldl;
	private double hdl;
	private double total;

	public Cholestorol(double ldl, double hdl, double total) {
		this.ldl = ldl;
		this.hdl = hdl;
		this.total = total;
	}

	public double getLdl() {
		return ldl;
	}

	public double getHdl() {
		return hdl;
	}

	public double getTotal() {
		return total;
	}
	
	public Type getType() {
		return Type.CHOLESTEROL;
	}
}
