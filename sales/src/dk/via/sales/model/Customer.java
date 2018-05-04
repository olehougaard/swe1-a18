package dk.via.sales.model;

public class Customer {
	private String email;
	private String name;
	
	public Customer(String email, String name) {
		this.email = email;
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}
}
