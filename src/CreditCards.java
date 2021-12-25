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
	
	public void addCard(String creditCardID,  String issueDate, CreditCardType creditCardType, double creditCardLimit) {
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
	
	public void addPurchase(String cardID, double amount, PurchaseType purchaseType, Vendor vendor) {
		CreditCard card = findCard(cardID);
		card.addPurchase(new Purchase(amount, purchaseType, vendor));
	}
	
	public void addFee(String cardID, FeeType feeType, double amount) {
		CreditCard card = findCard(cardID);
		card.addFee(new Fee(feeType, amount));
	}
	
	public void addPayment(String cardID, double amount, PaymentType paymentType, BankAccount account) {
		CreditCard card = findCard(cardID);
		card.addPayment(new Payment(amount, paymentType, account));
	}

}
