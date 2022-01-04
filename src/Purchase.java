import java.time.LocalDate;
import java.util.*;
public class Purchase extends Transaction {
	
	private PurchaseType purchaseType;
	private Vendor vendor;
	
	public Purchase(double amount, PurchaseType purchaseType, Vendor vendor) {
		super(LocalDate.now(), TransactionType.PURCHASE, amount);
		this.purchaseType = purchaseType;
		this.vendor = vendor;
	}
	
	public Purchase(Purchase purchase) {
		super(purchase);
		this.purchaseType = purchase.purchaseType;
		this.vendor = purchase.vendor;
	}
	
	public Purchase(double amount,PurchaseType type, String vendorName, String street, String city, String state, String zipcode) {
		super(LocalDate.now(), TransactionType.PURCHASE, amount);
		this.purchaseType=type;
		this.vendor=new Vendor(vendorName, new Address(street, city, state, zipcode));
	}

	public PurchaseType getType() {
		return purchaseType;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString() + " Purchase type: " + purchaseType + "\nVendor Information:\n" + vendor);
		return str.toString();
	}
}
