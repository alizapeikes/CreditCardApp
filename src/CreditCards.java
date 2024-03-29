import java.time.LocalDate;
import java.util.*;

public class CreditCards {

	private ArrayList<CreditCard> cards;

	public CreditCards() {
		cards = new ArrayList<>();
	}

	public String activeCards() {
		StringBuilder str = new StringBuilder();
		for (CreditCard c : cards) {
			if (c.getStatus() == CreditCardStatus.ACTIVE) {
				str.append(c.toString() + "\n");
			}
		}
		return str.toString();
	}

	public double totalBalance() {
		double total = 0;
		for (CreditCard card : cards) {
			total += card.getCurrentBalance();
		}
		return total;
	}

	public double totalAvailCredit() {
		double credit = 0;
		for (CreditCard card : cards) {
			credit += card.getAvailCredit();
		}
		return credit;
	}

	public void addCard(String creditCardID, String issueDate, String creditCardType, double creditLimit,
			String issueCompany) {
		CreditCard card = new CreditCard(creditCardID, issueDate, creditCardType, creditLimit, issueCompany);
		if (!cards.contains(card)) {
			cards.add(card);
		} else {
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
		for (CreditCard card : cards) {
			if (card.getID().equals(cardID)) {
				return card;
			}
		}
		throw new NoSuchCardException();
	}

	public void addPurchase(String cardID, double amount, PurchaseType type, String vendorName, String street,
			String city, String state, String zipcode) {
		if (cards.isEmpty()) {
			throw new NoCardsExistException();
		}
		CreditCard card = findCard(cardID);
		if (amount > card.getAvailCredit()) {
			throw new InvalidAmountException("Insufficient funds.");
		}
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
		card.addPayment(amount, paymentType, bankName, accountID);
	}

	public String getLargestPurchase() {
		if (cards.isEmpty()) {
			throw new NoCardsExistException();
		}
		Purchase largestPurchase = cards.get(0).getLargestPurchase();
		for (int i = 1; i < cards.size(); i++) {
			CreditCard temp = cards.get(i);
			if (temp.getLargestPurchase() == null) {
				continue;
			} else if (largestPurchase == null || temp.getLargestPurchase().getAmount() > largestPurchase.getAmount()) {
				largestPurchase = temp.getLargestPurchase();
			}
		}
		if (largestPurchase == null) {
			throw new NoSuchTransactionException();
		}
		return largestPurchase.toString();
	}

	public String getMostRecentPayment() {
		if (cards.isEmpty()) {
			throw new NoCardsExistException();
		}
		Payment mostRecent = null;
		for (int i = 0; i < cards.size(); i++) {
			CreditCard temp = cards.get(i);
			if (temp.getMostRecentPayment() == null) {
				continue;
			} else if (mostRecent == null || temp.getMostRecentPayment().getTransactionDate().isAfter(mostRecent.getTransactionDate())) {
				mostRecent = temp.getMostRecentPayment();
			}
		}
		if (mostRecent == null) {
			throw new NoSuchTransactionException();
		}
		return mostRecent.toString();
	}

	public double getTotalPerType(PurchaseType purchaseType) {
		if (cards.isEmpty()) {
			throw new NoCardsExistException();
		}
		double sum = 0;
		for (CreditCard card : cards) {
			sum += card.getTotalPerType(purchaseType);
		}
		return sum;
	}

	public PurchaseType getMostExpType() {
		if (cards.isEmpty()) {
			throw new NoCardsExistException();
		}
		PurchaseType[] purchaseType = { PurchaseType.CAR, PurchaseType.CLOTHING, PurchaseType.FOOD,
				PurchaseType.GROCERIES, PurchaseType.LODGING, PurchaseType.RESTAURANT, PurchaseType.TRAVEL,
				PurchaseType.UTILITES };
		double max = 0;
		double sum;
		PurchaseType pType = null;
		for (PurchaseType type : purchaseType) {
			sum = getTotalPerType(type);
			if (sum > max) {
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
		CreditCard temp = findCard(cardNumber);
		if (temp.getMostRecentPurchase() == null) {
			throw new NoSuchTransactionException();
		} else {
			return findCard(cardNumber).getMostRecentPurchase().toString();
		}
	}

	public String getMostRecentPayment(String cardNumber) {
		CreditCard temp = findCard(cardNumber);
		if (temp.getMostRecentPayment() == null) {
			throw new NoSuchTransactionException();
		} else {
			return findCard(cardNumber).getMostRecentPayment().toString();
		}
	}

	public void changeStatus(String cardNumber, CreditCardStatus status) {
		findCard(cardNumber).setStatus(status);
	}

	public CreditCardStatus getStatus(String cardNumber) {
		return findCard(cardNumber).getStatus();
	}

	public double getTotalFees(String cardNumber) {
		return findCard(cardNumber).getTotalFees();
	}

}
