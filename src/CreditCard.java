import java.time.LocalDate;
import java.util.ArrayList;

public class CreditCard {
	private String creditCardID;
	private LocalDate issueDate;
	private LocalDate expDate;
	private String issueCompany;
	private CreditCardType creditCardType;
	private CreditCardStatus status;
	private double creditLimit;
	private double currentBalance;
	private double availCredit;
	private ArrayList<Transaction> transactions;
	
	public CreditCard(String creditCardID, String issueDate, String creditCardType, double creditLimit, String issueCompany) {
		this.creditCardID = creditCardID;
		this.issueDate = LocalDate.parse(issueDate);
		this.expDate = this.issueDate.plusYears(3);
		this.creditCardType = CreditCardType.valueOf(creditCardType);
		//Assuming you are only inserting credit cards that are active
		this.status = CreditCardStatus.ACTIVE;
		this.creditLimit = creditLimit;
		this.currentBalance = 0;
		this.availCredit = creditLimit;
		this.issueCompany = issueCompany;
		transactions = new ArrayList<>();
	}

	public void addPurchase(double amount, PurchaseType type, String vendorName, String street, String city, String state, String zipcode) {
		if (amount <= 0 || amount > availCredit) {
			throw new InvalidAmountException();
		}
		if (!this.status.equals(CreditCardStatus.ACTIVE)){
			throw new InactiveCardException();
		}
		transactions.add(new Purchase(amount, type, vendorName, street, city, state, zipcode));
		update(amount);
	}
	
	public void addPayment(double amount, PaymentType paymentType, String bankName, String accountID) {
		if (amount <= 0) {
			throw new InvalidAmountException();
		}
		if (!this.status.equals(CreditCardStatus.ACTIVE)){
			throw new InactiveCardException();
		}
		transactions.add(new Payment(amount, paymentType, bankName, accountID));
		update(-amount);
	}
	
	public void addFee(FeeType feeType, double amount) {
		if (amount <= 0) {
			throw new InvalidAmountException();
		}
		if (!this.status.equals(CreditCardStatus.ACTIVE)){
			throw new InactiveCardException();
		}
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
			return new Purchase((Purchase)transactions.get(largestIndex));
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
		return new Purchase((Purchase)getMostRecent(TransactionType.PURCHASE));
	}
	
	public Payment getMostRecentPayment() {
		return new Payment((Payment)getMostRecent(TransactionType.PAYMENT));
	}
	
	private Transaction getMostRecent(TransactionType transactionType) {
		LocalDate mostRecent = LocalDate.MIN;
		int recentIndex = - 1;
		for(int i = 0; i < transactions.size(); i++) {
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

	public double getCreditLimit() {
		return creditLimit;
	}
	
	public CreditCardStatus getStatus() {
		return status;
	}
	
	public void setStatus(CreditCardStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Credit Card Number: " + creditCardID + "\tCurrent Balance: " + currentBalance + "\tAvailable credit: " + availCredit +
					"\nExpiration Date: " + expDate + "\tCredit Card Type: " + creditCardType + "\tIssue Company: " + issueCompany);
		return str.toString();
	}

	
	
}
