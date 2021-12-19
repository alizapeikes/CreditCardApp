import java.time.LocalDate;
import java.util.*;
public class Purchase extends Transaction{
	
	private PurchaseType purchaseType;
	private Vendor vendor;
	
	public Purchase(double amount, PurchaseType purchaseType, Vendor vendor) {
		super(LocalDate.now(), TransactionType.PURCHASE, amount);
		this.purchaseType = purchaseType;
		this.vendor = vendor;
	}
	//Should we allow the user to send in vendor informatin as well
	
	public PurchaseType getType() {
		return purchaseType;
	}
	
	public Vendor getVendor() {
		return vendor;
	}
}
