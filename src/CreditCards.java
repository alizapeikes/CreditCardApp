import java.time.LocalDate;
import java.util.*;
public class CreditCards {

	private ArrayList<CreditCard> cards;
	
	public CreditCards() {
		cards = new ArrayList<>();
	}
	
	public String activeCards() {
		StringBuilder str = new StringBuilder();
		for(CreditCard c : cards) {
			if(c.getStatus()==CreditCardStatus.ACTIVE) {
				str.append(c.toString()+"\n");
			}
		}
		return  str.toString();
	}
	
	public double totalBalance() {
		double total = 0;
		for(CreditCard card: cards) {
			total+=card.getCurrentBalance();
		}
		return total;
	}
	
	public double totalAvailCredit() {
		double credit = 0;
		for(CreditCard card: cards) {
			credit+=card.getAvailCredit();
		}
		return credit;
	}
	
	public void addCard(String creditCardID,  String issueDate, String creditCardType, double creditCardLimit) {
		CreditCard card = new CreditCard(creditCardID, issueDate, creditCardType, creditCardLimit);
		if (!cards.contains(card)) {
			cards.add(card);
		}
		else {
			throw new CardExistsException();
		}
	}
	
	public void removeCard(String cardID) {
		if (cards.isEmpty()) {
			throw new NoCardsExistException();
		}
		cards.remove(findCard(cardID));
	}
	
	public boolean isEmpty() {
		if (cards.isEmpty()) {
			return true;
		}
		return false;
	}
	
	private CreditCard findCard(String cardID) {
		if (cards.isEmpty()) {
			throw new NoCardsExistException();
		}
		for(CreditCard card: cards) {
			if(card.getID().equals(cardID)) {
				return card;
			}
		}
		throw new NoSuchCardException();
	}
	
	public void addPurchase(String cardID, double amount, PurchaseType type, String vendorName, String street, String city, String state, String zipcode) {
		if (cards.isEmpty()) {
			throw new NoCardsExistException();
		}
		CreditCard card = findCard(cardID);
		card.addPurchase(amount, type, vendorName, street, city, state, zipcode);
	}
	
	public void addFee(String cardID, FeeType feeType, double amount) {
		if (cards.isEmpty()) {
			throw new NoCardsExistException();
		}
		CreditCard card = findCard(cardID);
		card.addFee(feeType, amount);
	}
	
	public void addPayment(String cardID, double amount, PaymentType paymentType, String bankName, String accountID) {
		if (cards.isEmpty()) {
			throw new NoCardsExistException();
		}
		CreditCard card = findCard(cardID);
		if(amount>card.getAvailCredit()) {
			throw new InvalidAmountException("Insufficient funds");
		}
		card.addPayment(amount, paymentType, bankName, accountID);
	}
	
	public Purchase getLargestPurchase() {
		if (cards.isEmpty()) {
			throw new NoCardsExistException();
		}
		Purchase largestPurchase = cards.get(0).getLargestPurchase();
		for(int i = 1; i < cards.size(); i++) {
			if(cards.get(i).getLargestPurchase().getAmount() > largestPurchase.getAmount()) {
				largestPurchase = cards.get(i).getLargestPurchase();
			}
		}
		return largestPurchase;
	}
	
	public Payment getMostRecentPayment() {
		if (cards.isEmpty()) {
			throw new NoCardsExistException();
		}
		Payment mostRecent = cards.get(0).getMostRecentPayment();
		for(int i = 1; i < cards.size(); i++) {
			if (cards.get(i).getMostRecentPayment().getTransactionDate().isAfter(mostRecent.getTransactionDate())) {
				mostRecent = cards.get(i).getMostRecentPayment();
			}
		}
		return mostRecent;
	}
	public double getTotalPerType(PurchaseType purchaseType) {
		if (cards.isEmpty()) {
			throw new NoCardsExistException();
		}
		double sum = 0;
		for(CreditCard card: cards) {
			sum+=card.getTotalPerType(purchaseType);
		}
		return sum;
	}
	
	public PurchaseType getMostExpType() {
		if (cards.isEmpty()) {
			throw new NoCardsExistException();
		}
		PurchaseType[] purchaseType = {PurchaseType.CAR, PurchaseType.CLOTHING, PurchaseType.FOOD, PurchaseType.GROCERIES,
				PurchaseType.LODGING, PurchaseType.RESTAURANT, PurchaseType.TRAVEL, PurchaseType.UTILITES};
		double max = 0;
		double sum;
		PurchaseType pType = null;
		for(PurchaseType type: purchaseType) {
			sum = getTotalPerType(type);
			if(sum > max) {
				max = sum;
				pType = type;
			}
		}
		return pType;

	}

	public double getCurrentBalance(String cardNumber) {
		return findCard(cardNumber).getCurrentBalance();
	}

	public double getAvailCredit(String cardNumber) {
		return findCard(cardNumber).getAvailCredit();
	}

	public String getMostRecentPurchase(String cardNumber) {
		return findCard(cardNumber).getMostRecentPurchase().toString();
	}

	public String getMostRecentPayment(String cardNumber) {
		return findCard(cardNumber).getMostRecentPayment().toString();
	}
	
	public void setStatus(String cardNumber, String status) {
		findCard(cardNumber).setStatus(status);
	}
	
	public CreditCardStatus getStatus(String cardNumber) {
		return findCard(cardNumber).getStatus();
	}
	
	public double getTotalFees(String cardNumber) {
		return findCard(cardNumber).getTotalFees();
	}

}
