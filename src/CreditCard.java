import java.time.LocalDate;
import java.util.ArrayList;

public class CreditCard {
	private String creditCardID;
	private LocalDate issueDate;
	//private issueCompany
	private CreditCardType creditCardType;
	private CreditCardStatus status;
	private double creditCardLimit;
	private double currentBalance;
	private double availCredit;
	private ArrayList<Transaction> transactions;
	
	
	public void addPurchase(Purchase purchase) {
		transactions.add(purchase);
		currentBalance+=purchase.getAmount()//Do we have a getter for this? Does this get calculated here?
		availCredit-=purchase.getAmount()//same with this- should we make a method for these two?
	}
	
	public void addPayment(Payment payment) {
		transactions.add(payment);
		currentBalance-=payment.getAmount();//same comments as above
		availCredit+=payment.getAmount();
	}
	
	public void addFee(Fee fee) {
		transactions.add(purchase);
		currentBalance+=fee.getAmount()//same comments as above
		availCredit-=fee.getAmount()
	}
	
	public double getCurrentBalance() {
		return currentBalance;
	}
	
	public double getAvailCredit() {
		return availCredit;
	}
	
	public Purchase getLargestPurchase() {
		//make an iterator for just purchase and iterate through the purchases on the arrayList of transactions?
	}
	
	public double getTotalFees() {
		//make an iterator for the fees?
	}
	
	public Purchase getMostRecentPurchase() {
		//use the purchase iterator?
	}
	
	public Payment getMostRecentPayment() {
		//make at iterator for thee payments?
		
	}
	
}
