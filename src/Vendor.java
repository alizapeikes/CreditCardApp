
public class Vendor {
	private String name;
	private Address address;
	public Vendor(String name, Address address) {
		this.name = name;
		this.address = address;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		StringBuilder text = new StringBuilder();
		text.append(name + "\n" + address);
		return text.toString();
	}
}
