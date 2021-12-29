import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

public class ManageCards {

	private CreditCards cards = new CreditCards();

	public void start() {
		overallMenu();
	}
	
	//add set status, get status, and get total fees
	private void overallMenu() {
		Scanner keyboard = new Scanner(System.in);
		int choice = 0;
		while (choice != 11) {
			choice = displayOverallMenu(keyboard);
			try {
				switch (choice) {
				case 1:
					addCard(keyboard);
					break;
				case 2:
					removeCard(keyboard);
					break;
				case 3:
					System.out.println("Total balance: " + cards.totalBalance());
					break;
				case 4:
					System.out.println("Total available credit: " + cards.totalAvailCredit());
					break;
				case 5:
					System.out.println("Largest purchase: " + cards.getLargestPurchase());
					break;
				case 6:
					System.out.println("Most recent payment: " + cards.getMostRecentPayment());
					break;
				case 7:
					if (cards.isEmpty()) {
						System.out.println("No cards are currently being managed.");
					}
					else {
						PurchaseType purchaseType = getPurchaseType(keyboard);
						System.out.println("Total amount spent on " + purchaseType + ": " + cards.getTotalPerType(purchaseType));
					}
					break;
				case 8:
					System.out.println("Category most spent on: " + cards.getMostExpType());
					break;
				case 9:
					System.out.println("Active cards:\n" + cards.activeCards());
				case 10:
					cardMenu(keyboard);
					break;
				case 11:
					System.exit(0);
					break;
				}
			} catch (NoCardsExistException | NoSuchCardException e) {
				System.out.println(e.getMessage());
				System.out.println("Returning to main menu...");
			} catch (NoSuchTransactionException e) {
				System.out.println(e.getMessage());
				System.out.println("Returning to main menu...");
			} catch (CardExistsException e) {
				System.out.println(e.getMessage());
				System.out.println("Returning to main menu...");
			} catch (InvalidAmountException e) {
				System.out.println(e.getMessage());
				System.out.println("Returning to main menu...");
			}
		}

	}

	private int displayOverallMenu(Scanner keyboard) {
		System.out.println("\nPlease choose one of the following options: ");
		System.out.println("1. Add new card \n" + "2. Remove card \n" + "3. Display total outstanding balances \n"
				+ "4. Display total available credit \n" + "5. Display largest purchase \n"
				+ "6. Display most recent payment \n" + "7. Display total spent on specific expense category \n"
				+ "8. Display purchase type for which the most money was spent \n"
				+ "9. Display a list of active credit cards\n10. Manage a specific credit card\n" + "11. Exit");
		int choice = keyboard.nextInt();
		while (choice < 0 || choice > 10) {
			System.out.println("Invalid choice. Please enter a number from 1-11");
			choice = keyboard.nextInt();
		}
		keyboard.nextLine(); // Clearing buffer
		return choice;
	}

	private void addCard(Scanner keyboard) {
		System.out.print("Please enter the credit card type: (VISA, MASTERCARD, AMEX, DISCOVER)");
		String creditCardType = keyboard.nextLine().toUpperCase();
		while (!CreditCardType.contains(creditCardType)) {
			System.out.println("Invalid Credit card type. ");
			System.out.print("Please enter VISA, MASTERCARD, AMEX, DISCOVER: ");
			creditCardType = keyboard.nextLine().toUpperCase();
		}

		System.out.print("Please enter your credit card number: ");
		String cardNumber = keyboard.nextLine();
		while (creditCardType.equalsIgnoreCase("AMEX") && cardNumber.length() != 15) {
			System.out.println("Invalid Card Number. Card number must have 15 digits.");
			System.out.print("Please enter a valid number: ");
			cardNumber = keyboard.nextLine();
		}
		while (!creditCardType.equalsIgnoreCase("AMEX") && cardNumber.length() != 16) {
			System.out.println("Invalid Card Number. Card number must have 16 digits.");
			System.out.print("Please enter a valid number: ");
			cardNumber = keyboard.nextLine();
		}

		String issueDate = getDate(keyboard);

		System.out.print("Please enter the credit card limit: ");
		double cardLimit = keyboard.nextDouble();
		while (cardLimit < 100) {
			System.out.print("Credit Limit cannot be less than 100. Please enter a valid credit limit: ");
			cardLimit = keyboard.nextDouble();
		}
		keyboard.nextLine();    //Clearing buffer
		cards.addCard(cardNumber, issueDate, creditCardType, cardLimit);
	}

	private void removeCard(Scanner keyboard) {
		if (cards.isEmpty()) {
			System.out.println("No cards are currently being managed.");
			return;
		}
		System.out.print("Please enter card number to remove: ");
		String cardNumber = keyboard.nextLine();
		cards.removeCard(cardNumber);
	}

	private String getDate(Scanner keyboard) {
		System.out.print("Please enter the issue date of your credit card: (yyyy-mm-dd) ");
		String issueDate = keyboard.nextLine();

		Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
		int[] daysPerMonth = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

		boolean check = true;
		while (check) {
			while (!DATE_PATTERN.matcher(issueDate).matches()) {
				System.out.print("Please enter date in the correct format: yyyy-mm-dd: ");
				issueDate = keyboard.nextLine();
			}

			int month = Integer.parseInt(issueDate.substring(5, 7));
			int day = Integer.parseInt(issueDate.substring(8, 10));
			int year = Integer.parseInt(issueDate.substring(0, 4));
			// check if month in range
			if (month <= 0 || month > 12) {
				System.out.println("Month must be 1-12");
			}
			// check if day in range for month
			else if (day <= 0 || (day > daysPerMonth[month] && !(month == 2 && day == 29))) {
				System.out.println("Day out-of-range for the specified month and year");
				// check for leap year if month is 2 and day is 29
			} else if (month == 2 && day == 29 && !(year % 400 == 0 || (year % 4 == 0 && year % 100 != 0))) {
				System.out.println("Day out-of-range for the specified month and year");
			} else if (LocalDate.parse(issueDate).isAfter(LocalDate.now())) {
				System.out.println("Invalid date! Date must be equal or before todays date.");
			} else {
				check = false;
			}
			if (check) {
				System.out.print("Please re-enter date: ");
				issueDate = keyboard.nextLine();
			}
		}

		return issueDate;
	}

	private PurchaseType getPurchaseType(Scanner keyboard) {
		System.out.println("Which expense category:\n1.Car\n2.Clothing\n3.Food\n4.Groceries"
				+ "\n5.Lodging\n6.Restaurant\n7.Travel\n8.Utilities");
		int category = keyboard.nextInt();
		while (category < 1 || category > 8) {
			System.out.println("Invalid entry. Please enter a number between 1 and 8.");
			category = keyboard.nextInt();
		}
		keyboard.nextLine(); // Clearing buffer
		PurchaseType purchaseType = null;
		switch (category) {
		case 1:
			purchaseType = PurchaseType.CAR;
			break;
		case 2:
			purchaseType = PurchaseType.CLOTHING;
			break;
		case 3:
			purchaseType = PurchaseType.FOOD;
			break;
		case 4:
			purchaseType = PurchaseType.GROCERIES;
			break;
		case 5:
			purchaseType = PurchaseType.LODGING;
			break;
		case 6:
			purchaseType = PurchaseType.RESTAURANT;
			break;
		case 7:
			purchaseType = PurchaseType.TRAVEL;
			break;
		case 8:
			purchaseType = PurchaseType.UTILITES;
			break;
		}
		return purchaseType;
	}

	private int displayCardMenu(Scanner keyboard) {
		System.out.println("\nPlease choose one of the following options: ");
		System.out.println("1. Display current balance \n2. Display available credit \n"
				+ "3. Add purchase \n4. Add payment \n5. Add fee \n6. Display most recent purchase \n"
				+ "7. Display most recent payment \n8. Back to main menu");
		int choice = keyboard.nextInt();
		while (choice < 1 || choice > 8) {
			System.out.println("Invalid choice. Please enter a number from 1-8");
			choice = keyboard.nextInt();
		}
		keyboard.nextLine(); // Clearing buffer
		return choice;
	}

	private void cardMenu(Scanner keyboard) {
		if (cards.isEmpty()) {
			System.out.println("No cards are currently being managed.");
			return;
		}
		System.out.print("Please enter the credit card number: ");
		String cardNumber = keyboard.nextLine();
		//CreditCard creditCard = cards.findCard(cardNumber);
		int choice = 0;
		while (choice < 9) {
			choice = displayCardMenu(keyboard);
			switch (choice) {
			case 1:
				System.out.println("Current Balance: " + cards.getCurrentBalance(cardNumber));
				break;
			case 2:
				System.out.println("Available Credit: " + cards.getAvailCredit(cardNumber));
				break;
			case 3:
				addPurchase(keyboard, cardNumber);
				break;
			case 4:
				addPayment(keyboard, cardNumber);
				break;
			case 5:
				addFee(keyboard, cardNumber);
				break;
			case 6:
				System.out.println("Most recent purchase: " + cards.getMostRecentPurchase(cardNumber));
				break;
			case 7:
				System.out.println("Most recent payment: " + cards.getMostRecentPayment(cardNumber));
				break;
			case 8:
				System.out.println("Returning to main menu");
				break;
			}
		}

	}

	private void addFee(Scanner keyboard, String cardNumber) {
		System.out.print("Please enter fee amount: ");
		double amount = keyboard.nextDouble();
		keyboard.nextLine();
		FeeType feeType = getFeeType(keyboard);
		cards.addFee(cardNumber, feeType, amount);
	}

	private FeeType getFeeType(Scanner keyboard) {
		System.out.print("Please enter a fee type");
		System.out.print("\n1. Late payment\n2. Interest\n");
		int choice = keyboard.nextInt();
		FeeType feeType = null;
		while (choice < 1 || choice > 2) {
			System.out.print("Invalid entry. Please enter a number between 1 and 2: ");
			choice = keyboard.nextInt();
		}
		keyboard.nextLine(); // Clearing buffer
		switch (choice) {
		case 1:
			feeType = FeeType.LATE_PAYMENT;
			break;
		case 2:
			feeType = FeeType.INTEREST;
			break;
		}
		return feeType;
	}

	private void addPayment(Scanner keyboard, String cardNumber) {
		System.out.print("Please enter payment amount: ");
		double amount = keyboard.nextDouble();
		while (amount <= 0) {
			System.out.print("Invalid amount! Please enter a value greater than 0: ");
			amount = keyboard.nextDouble();
		}
		keyboard.nextLine();
		PaymentType paymentType = getPaymentType(keyboard);
		System.out.print("Please enter the bank name: ");
		String bankName = keyboard.nextLine();
		System.out.print("Please enter the account number: ");
		String accountID = keyboard.nextLine();
		while(accountID.length()<7||accountID.length()>17) {
			System.out.print("Invalid entry. Please enter a valid account number: ");
			accountID = keyboard.nextLine();
		}
		cards.addPayment(cardNumber, amount, paymentType, bankName, accountID);

	}

	private PaymentType getPaymentType(Scanner keyboard) {
		System.out.print("Please enter a payment type:");
		System.out.print("\n1. Check\n2. Online");
		int choice = keyboard.nextInt();
		PaymentType paymentType = null;
		while (choice < 1 || choice > 2) {
			System.out.print("Invalid entry. Please enter a number between 1 and 2");
			choice = keyboard.nextInt();
		}
		keyboard.nextLine(); // Clearing buffer
		switch (choice) {
		case 1:
			paymentType = PaymentType.CHECK;
			break;
		case 2:
			paymentType = PaymentType.ONLINE;
			break;
		}
		return paymentType;
	}

	private void addPurchase(Scanner keyboard, String cardNumber) {
		PurchaseType type = getPurchaseType(keyboard);
		System.out.print("Please enter purchase amount: ");
		double amount = keyboard.nextDouble();
		while (amount <= 0) {
			System.out.print("Invalid purchase amount! Please re-enter amount: ");
			amount = keyboard.nextDouble();
		}
		keyboard.nextLine();
		System.out.print("Please enter vendor name: ");
		String vendorName = keyboard.nextLine();

		System.out.print("Please enter vendor street address: ");
		String street = keyboard.nextLine();
		System.out.print("Please enter vendor city: ");
		String city = keyboard.nextLine();
		System.out.print("Please enter vendor state: ");
		String state = keyboard.nextLine();
		while (!USState.contains(state)) {
			System.out.print("Invalid entry. Please enter a valid state: ");
			state = keyboard.nextLine();
		}
		System.out.print("Pleae enter vendor zip code(5 digits only): ");
		String zipcode = keyboard.nextLine();
		while (zipcode.length() != 5) {
			System.out.print("Invalid entry. Please enter 5 digits: ");
			zipcode = keyboard.nextLine();
		}
		cards.addPurchase(cardNumber, amount, type, vendorName, street, city, state, zipcode);
	}
	
}
