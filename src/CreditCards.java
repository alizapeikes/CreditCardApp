import java.time.LocalDate;
import java.util.*;
public class CreditCards {

	private ArrayList<CreditCard> cards;
	
	public CreditCards() {
		cards = new ArrayList<>();
	}
	
	public void activeCards() {
		
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
	
	public void addCard(CreditCard card) {
		cards.add(card);
	}
	
	public void addCard(String creditCardID,  String issueDate, String creditCardType, double creditCardLimit) {
		cards.add(new CreditCard(creditCardID, issueDate, creditCardType, creditCardLimit));
	}
	
	public void removeCard(String cardID) {
		cards.remove(findCard(cardID));
	}
	
	public CreditCard findCard(String cardID) {
		for(CreditCard card: cards) {
			if(card.getID().equals(cardID)) {
				return card;
			}
		}
		throw new NoSuchCardException();
	}
	
	public void addPurchase(String cardID, double amount, PurchaseType type, String vendorName, String street, String city, String state, String zipcode) {
		CreditCard card = findCard(cardID);
		card.addPurchase(amount, type, vendorName, street, city, state, zipcode);
	}
	
	public void addFee(String cardID, FeeType feeType, double amount) {
		CreditCard card = findCard(cardID);
		card.addFee(feeType, amount);
	}
	
	public void addPayment(String cardID, double amount, PaymentType paymentType, String bankName, String accountID) {
		CreditCard card = findCard(cardID);
		card.addPayment(amount, paymentType, bankName, accountID);
	}
	
	public Purchase getLargestPurchase() {
		Purchase largestPurchase = cards.get(0).getLargestPurchase();
		for(int i = 1; i < cards.size(); i++) {
			if(cards.get(i).getLargestPurchase().getAmount() > largestPurchase.getAmount()) {
				largestPurchase = cards.get(i).getLargestPurchase();
			}
		}
		return largestPurchase;
	}
	
	public Payment getMostRecentPayment() {
		Payment mostRecent = cards.get(0).getMostRecentPayment();
		for(int i = 1; i < cards.size(); i++) {
			if (cards.get(i).getMostRecentPayment().getTransactionDate().isAfter(mostRecent.getTransactionDate())) {
				mostRecent = cards.get(i).getMostRecentPayment();
			}
		}
		return mostRecent;
	}
	public double getTotalPerType(PurchaseType purchaseType) {
		double sum = 0;
		for(CreditCard card: cards) {
			sum+=card.getTotalPerType(purchaseType);
		}
		return sum;
	}
	
	public PurchaseType getMostExpType() {
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

}
