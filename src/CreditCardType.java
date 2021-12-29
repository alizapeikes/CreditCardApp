
public enum CreditCardType {VISA, MASTERCARD, AMEX, DISCOVER;
	
	public static boolean contains(String type) {
		for (CreditCardType t : CreditCardType.values()) {
			if (t.toString().equals(type)) {
				return true; 
			}
		} 
		return false;
	}
}
