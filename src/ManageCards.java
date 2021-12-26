import java.util.*;
public class ManageCards {

	CreditCards cards = new CreditCards();
	/*
	 * 1. Add a new CreditCard
2. Remove a CreditCard
3. Display total outstanding balances
4. Display total available credit
5. Display largest purchase 
6. Display most recent payment
7. Display total spent on certain category of expense
8. For which type of Purchase was the most money spent
9. Manage a specific Credit Card
a. Display current balance
b. Display current credit limit
c. Add a Purchase
d. Add a Payment
e. Add a Fee
f. Display most recent Purchase
g. Display most recent Payment
	 */
	
	public void overallMenu() {
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Please choose one of the following options: ");
		System.out.println("1. Add new card \n"
				+ "2. Remove card \n"
				+ "3. Display total outstanding balances \n"
				+ "4. Display total available credit \n"
				+ "5. Display largest purchase \n"
				+ "6. Display most recent payment \n"
				+ "7. Display total spent on specific expense category \n"
				+ "8. Display purchase type for which the most money was spent \n"
				+ "9. Manage a specific credit card");
		int choice = keyboard.nextInt();
		switch (choice) {
			case 1: 
				addCard(keyboard);
		}
	}
	
	public void addCard(Scanner keyboard) {
		keyboard.nextLine();
		System.out.print("Please enter your credit card number: ");
		String cardNumber = keyboard.nextLine();
		System.out.print("Please enter the issue date of your credit card: (yyyy-mm-dd) ");
		String issueDate = keyboard.nextLine();
		System.out.print("Please enter the credit card type: (VISA, MASTERCARD, AMEX, DISCOVER)");
		String creditCardType = keyboard.nextLine();
		while (!creditCardType.equals(CreditCardType.VISA) && !creditCardType.equals(CreditCardType.MASTERCARD) &&) {
			System.out.print("In")
		}
		System.out.print("Please enter the credit card limit: ");
		double cardLimit = keyboard.nextDouble();
		cards.addCard(new CreditCard(cardNumber, issueDate, CreditCardType.valueOf(creditCardType), cardLimit));
	}
	
	public void cardMenu() {
		System.out.println("Please choose one of the following options: ");
		System.out.println("1. Display current balance \n"
				+ "2. Display current credit card limit \n"
				+ "3. Add purchase \n"
				+ "4. Add payment \n"
				+ "5. Add fee \n"
				+ "6. Display most recent purchase \n"
				+ "7. Display most recent payment");
	}
}
