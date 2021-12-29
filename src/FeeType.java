
public enum FeeType {LATE_PAYMENT, INTEREST;

	public static boolean contains(String type) {
		for (FeeType t : FeeType.values()) 
			if (t.toString().equals(type)) {
				return true; 
		} 
		return false;
	}
}
