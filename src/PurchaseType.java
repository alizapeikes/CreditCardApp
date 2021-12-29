
public enum PurchaseType {
	CAR, CLOTHING, FOOD, GROCERIES, LODGING, RESTAURANT, TRAVEL, UTILITES;
	
	public static boolean contains(String type) {
		for (PurchaseType t : PurchaseType.values()) 
			if (t.toString().equals(type)) {
				return true; 
		} 
		return false;
	}
}
