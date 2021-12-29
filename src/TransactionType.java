
public enum TransactionType {PURCHASE, PAYMENT,FEE;
	
	public static boolean contains(String type) {
	for (TransactionType t : TransactionType.values()) 
		if (t.toString().equals(type)) {
		return true; 
	} 
	return false;
}
}
