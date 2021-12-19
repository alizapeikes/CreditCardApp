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
	
	public CreditCard(String creditCardID, String issueDate, CreditCardType creditCardType, double creditCardLimit) {
		this.creditCardID = creditCardID;
		this.issueDate = LocalDate.parse(issueDate);
		this.creditCardType = creditCardType;
		//Assuming you are only inserting credit cards that are active
		this.status = CreditCardStatus.ACTIVE;
		this.creditCardLimit = creditCardLimit;
		this.currentBalance = 0;
		this.availCredit = creditCardLimit;
		transactions = new ArrayList<>();

	}
	
	public void addPurchase(Purchase purchase) {
		transactions.add(purchase);
		update(purchase.getAmount());
	}
	
	public void addPayment(Payment payment) {
		transactions.add(payment);
		update(-payment.getAmount());
	}
	
	public void addFee(Fee fee) {
		transactions.add(fee);
		update(fee.getAmount());
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
	
	public Transaction getMostRecent(TransactionType transactionType) {
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
	
}
