
public enum PaymentType {CHECK,ONLINE;
	
	public static boolean contains(String type) {
		for (PaymentType t : PaymentType.values()) 
			if (t.toString().equals(type)) {
				return true; 
		} 
		return false;
	}
}