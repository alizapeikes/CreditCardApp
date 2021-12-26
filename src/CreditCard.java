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
	
	public CreditCard(String creditCardID, String issueDate, String creditCardType, double creditCardLimit) {
		this.creditCardID = creditCardID;
		this.issueDate = LocalDate.parse(issueDate);
		this.creditCardType = CreditCardType.valueOf(creditCardType);
		//Assuming you are only inserting credit cards that are active
		this.status = CreditCardStatus.ACTIVE;
		this.creditCardLimit = creditCardLimit;
		this.currentBalance = 0;
		this.availCredit = creditCardLimit;
		transactions = new ArrayList<>();

	}

	public void addPurchase(double amount, PurchaseType type, String vendorName, String street, String city, String state, String zipcode) {
		transactions.add(new Purchase(amount, type, vendorName, street, city, state, zipcode));
		update(amount);
	}
	
	public void addPayment(double amount, PaymentType paymentType, String bankName, String accountID) {
		transactions.add(new Payment(amount, paymentType, bankName, accountID));
		update(-amount);
	}
	
	public void addFee(FeeType feeType, double amount) {
		transactions.add(new Fee(feeType, amount));
		update(amount);
	}
	
	public void update(double amount) {
		currentBalance+=amount;
		availCredit-=amount;
	}
	
	public double getCurrentBalance() {
		return currentBalance;
	}
	
	public double getAvailCredit() {
		return availCredit;
	}
	
	public Purchase getLargestPurchase() { 
		double largest = 0;
		int largestIndex = -1;
		for(int i = 0; i < transactions.size(); i++) {
			if(transactions.get(i).getTransactionType() == TransactionType.PURCHASE) {
				if(transactions.get(i).getAmount() > largest) {
					largest = transactions.get(i).getAmount();
					largestIndex = i;
				}
			}
		}
		if(largestIndex == -1) {
			throw new NoSuchTransactionException();
		}else{
			return (Purchase)transactions.get(largestIndex);
		}
	}
	
	public double getTotalFees() {
		double sum = 0;
		for(Transaction transaction: transactions) {
			if(transaction.getTransactionType() == TransactionType.FEE) {
				sum+=transaction.getAmount();
			}
		}
		return sum;
	}
	
	public Purchase getMostRecentPurchase() {
		return (Purchase)getMostRecent(TransactionType.PURCHASE);
	}
	
	public Payment getMostRecentPayment() {
		return (Payment)getMostRecent(TransactionType.PAYMENT);
	}
	
	private Transaction getMostRecent(TransactionType transactionType) {
		LocalDate mostRecent = LocalDate.MIN;
		int recentIndex = - 1;
		for(int i = 1; i < transactions.size(); i++) {
			if(transactions.get(i).getTransactionType() == transactionType) {
				if(transactions.get(i).getTransactionDate().isAfter(mostRecent)) {
					mostRecent = transactions.get(i).getTransactionDate();
					recentIndex = i;
				}
			}
		}
		if(recentIndex == -1) {
			throw new NoSuchTransactionException();
		}else {
			return transactions.get(recentIndex);
		}
	}
	public double getTotalPerType(PurchaseType purchaseType) {
		double sum = 0;
		for(Transaction transaction: transactions) {
			if(transaction.getTransactionType() == TransactionType.PURCHASE && purchaseType == ((Purchase)transaction).getType()) {
				sum+=transaction.getAmount();
			}
		}
		return sum;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreditCard other = (CreditCard) obj;
		if (creditCardID == null) {
			if (other.creditCardID != null)
				return false;
		} else if (!creditCardID.equals(other.creditCardID))
			return false;
		return true;
	}
	
	public String getID() {
		return creditCardID;
	}

	public double getCreditCardLimit() {
		return creditCardLimit;
	}



	
	
}
