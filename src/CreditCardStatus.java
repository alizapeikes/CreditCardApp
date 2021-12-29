
public enum CreditCardStatus {ACTIVE, CANCELED, LOST, EXPIRED;

	public static boolean contains(String status) {
		for (CreditCardStatus s : CreditCardStatus.values()) 
			if (s.toString().equals(status)) {
				return true; 
		} 
		return false;
	}
}
