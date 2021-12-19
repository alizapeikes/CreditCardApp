
public class Address {
	private String street;
	private String city;
	private USState state;
	private String zipCode;
	
	public Address(String street, String city, USState state, String zipCode) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
	}
	
	@Override
	public String toString() {
		StringBuilder text = new StringBuilder();
		text.append(street + "\n" + city + ", " + state + " " + zipCode);
		return text.toString();
	}
}
