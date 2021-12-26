import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;
public class ManageCards {

	CreditCards cards = new CreditCards();
	
	public void overallMenu() {
		Scanner keyboard = new Scanner(System.in);
		int choice = displayOverallMenu(keyboard);
		switch (choice) {
			case 1: 
				addCard(keyboard);
				break;
			case 2:
				removeCard(keyboard);
				break;
			case 3:
				System.out.println("Total balance: "+cards.totalBalance());
				break;
			case 4:
				System.out.println("Total available credit: "+cards.totalAvailCredit());
				break;
			case 5:
				System.out.println("Largest purchase: "+cards.getLargestPurchase());
				break;
			case 6:
				System.out.println("Most recent payment: "+cards.getMostRecentPayment());
				break;
			case 7:				
				PurchaseType purchaseType=getPurchaseType(keyboard);
				System.out.println("Total amount spent on " + purchaseType + ": " + cards.getTotalPerType(purchaseType));
				break;
			case 8:
				System.out.println("Category most spent on: " + cards.getMostExpType());
				break;
			case 9:
				cardMenu(keyboard);
				break;
			case 10: 
				System.exit(0);
				break;
		}
	}

	private int displayOverallMenu(Scanner keyboard) {
		System.out.println("Please choose one of the following options: ");
		System.out.println("1. Add new card \n"
				+ "2. Remove card \n"
				+ "3. Display total outstanding balances \n"
				+ "4. Display total available credit \n"
				+ "5. Display largest purchase \n"
				+ "6. Display most recent payment \n"
				+ "7. Display total spent on specific expense category \n"
				+ "8. Display purchase type for which the most money was spent \n"
				+ "9. Manage a specific credit card\n"
				+ "10. Exit");
		int choice = keyboard.nextInt();
		while(choice < 0 || choice > 10) {
			System.out.println("Invalid choice. Please enter a number from 1-10");
			choice = keyboard.nextInt();
		}
		return choice;
	}

	public void addCard(Scanner keyboard) {
		keyboard.nextLine();
		System.out.print("Please enter the credit card type: (VISA, MASTERCARD, AMEX, DISCOVER)");
		String creditCardType = keyboard.nextLine();
		while (!creditCardType.equalsIgnoreCase("VISA") && !creditCardType.equalsIgnoreCase("MASTERCARD") &&
						creditCardType.equalsIgnoreCase("DISCOVER") && creditCardType.equalsIgnoreCase("AMEX")) {
			System.out.println("Invalid Credit card type. ");
			System.out.println("Please enter VISA, MASTERCARD, AMEX, DISCOVER.");
			creditCardType = keyboard.nextLine();
		}
		
		System.out.print("Please enter your credit card number: ");
		String cardNumber = keyboard.nextLine();
		while(creditCardType.equalsIgnoreCase("AMEX") && cardNumber.length() != 15) {
			System.out.println("Invalid Card Number. Card number must have 15 digits.");
			System.out.println("Please enter a valid number");
			cardNumber = keyboard.nextLine();
		}
		while(!creditCardType.equalsIgnoreCase("AMEX") &&  cardNumber.length() != 16 ) {
			System.out.println("Invalid Card Number. Card number must have 16 digits.");
			System.out.println("Please enter a valid number");
			cardNumber = keyboard.nextLine();
		}
		
		String issueDate = getDate(keyboard);

		System.out.print("Please enter the credit card limit: ");
		double cardLimit = keyboard.nextDouble();
		while(cardLimit < 100) {
			System.out.println("Credit Limit cannot be less than 100");
		}
		
		cards.addCard(new CreditCard(cardNumber, issueDate, creditCardType, cardLimit));
	}
	
	private void removeCard(Scanner keyboard) {
		System.out.println("Please enter card number to remove");
		String cardNumber = keyboard.nextLine();
		try {
			cards.removeCard(cardNumber);
		}catch(NoSuchCardException e){
			e.getMessage();
			overallMenu();
		}
	}

	private String getDate(Scanner keyboard) {
		System.out.print("Please enter the issue date of your credit card: (yyyy-mm-dd) ");
		String issueDate = keyboard.nextLine();
		
		Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
		int[] daysPerMonth = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		
		boolean check = false;
		while(check) {
			while(DATE_PATTERN.matcher(issueDate).matches()) {
				System.out.println("Please enter date in the correct format: yyyy-mm-dd");
				issueDate = keyboard.nextLine();
			}
			
			int month = Integer.parseInt(issueDate.substring(6,8));
			int day = Integer.parseInt(issueDate.substring(8,10));
			int year = Integer.parseInt(issueDate.substring(0, 4));
		    // check if month in range
		    if ( month <= 0 ||month > 12) {
		            System.out.println("Month must be 1-12");
		    }
		    // check if day in range for month
		    else if (day <= 0 || (day > daysPerMonth[month] && !(month == 2 && day == 29))) {
		         System.out.println("Day out-of-range for the specified month and year");
		    // check for leap year if month is 2 and day is 29     
		    }else if (month == 2 && day == 29 && !(year % 400 == 0 || (year % 4 == 0 && year % 100 != 0))) {
		         System.out.println("Day out-of-range for the specified month and year");
		    }else if(!LocalDate.parse(issueDate).isAfter(LocalDate.now())) {
					System.out.println("Invalid date! Date must be equal or before todays date.");
		    }else {
		    	check = true;
		    }
		    if(!check) {
			    System.out.print("Please re-enter date: ");
			    issueDate = keyboard.nextLine();
		    }
		} 
		

		return issueDate;
	}
	
	public PurchaseType getPurchaseType(Scanner keyboard) {
		System.out.println("Which expense category\n1.Car\n2.Clothing\n3.Food\n4.Groceries"
				+ "\n5.Lodging\n6.Restaurant\n7.Travel\n8.Utilities");
		int category = keyboard.nextInt();
		while(category<1||category>8) {
			System.out.println("Invalid entry. Please enter a number between 1 and 8.");
			category = keyboard.nextInt();
		}
		PurchaseType purchaseType=null;
		switch(category) {
		case 1:
			purchaseType=PurchaseType.CAR;
			break;
		case 2:
			purchaseType=PurchaseType.CLOTHING;
			break;
		case 3:
			purchaseType=PurchaseType.FOOD;
			break;
		case 4:
			purchaseType=PurchaseType.GROCERIES;
			break;
		case 5:
			purchaseType=PurchaseType.LODGING;
			break;
		case 6:
			purchaseType=PurchaseType.RESTAURANT;
			break;
		case 7:
			purchaseType=PurchaseType.TRAVEL;
			break;
		case 8:
			purchaseType=PurchaseType.UTILITES;
			break;
		}
		return purchaseType;
	}
	
	public int displayCardMenu(Scanner keyboard) {
		System.out.println("Please choose one of the following options: ");
		System.out.println("1. Display current balance \n"
				+ "2. Display current credit card limit \n"
				+ "3. Add purchase \n"
				+ "4. Add payment \n"
				+ "5. Add fee \n"
				+ "6. Display most recent purchase \n"
				+ "7. Display most recent payment");
		int choice = keyboard.nextInt();
		while(choice < 1 || choice > 7) {
			System.out.println("Invalid choice. Please enter a number from 1-7");
			choice = keyboard.nextInt();
		}
		return choice;
	}	

	private void cardMenu(Scanner keyboard) {
		System.out.print("Please enter the credit card number: ");
		String cardNumber = keyboard.nextLine();
		try {
			CreditCard creditCard = cards.findCard(cardNumber);
			int choice = displayCardMenu(keyboard);
			switch(choice) {
				case 1:
					System.out.println("Current Balance: " + creditCard.getCurrentBalance());
					break;
				case 2:
					System.out.println("Credit Card Limit: " + creditCard.getCreditCardLimit());
					break;
				case 3: 
					addPurchase(keyboard, creditCard);
					break;
				case 4:
					addPayment(keyboard, creditCard);
					break;
				case 5:
					addFee(keyboard, creditCard);
					break;
				case 6:
					System.out.println("Most recent purchase: " + creditCard.getMostRecentPurchase());
					break;
				case 7:
					System.out.println("Most recent payment: " + creditCard.getMostRecentPayment());
					break;
			}
		}catch(NoSuchCardException e) {
			e.getMessage();
			cardMenu(keyboard);
		}

	}


	private void addFee(Scanner keyboard, CreditCard creditCard) {
		System.out.print("Please enter fee amount: ");	
		double amount = keyboard.nextDouble();
		keyboard.nextLine();
		FeeType feeType = getFeeType(keyboard);
		creditCard.addFee(feeType, amount);
	}


	private FeeType getFeeType(Scanner keyboard) {
		System.out.print("Please enter a fee type");
		System.out.print("1. Late payment\n2. Interest\n");
		int choice = keyboard.nextInt();
		FeeType feeType = null;
		while(choice<1||choice>2) {
			System.out.print("Invalid entry. Please enter a number between 1 and 2");
			choice = keyboard.nextInt();
		}
		switch(choice) {
			case 1:
				feeType = FeeType.LATE_PAYMENT;
				break;
			case 2:
				feeType = FeeType.INTEREST;
				break;
		}
		return feeType;
	}

	private void addPayment(Scanner keyboard, CreditCard creditCard) {
		System.out.print("Please enter payment amount: ");
		double amount = keyboard.nextDouble();
		keyboard.nextLine();
		PaymentType paymentType = getPaymentType(keyboard);
		System.out.print("Please enter the bank name: ");
		String bankName = keyboard.nextLine();
		System.out.print("Please enter the account number: ");
		String accountID = keyboard.nextLine();
		creditCard.addPayment(amount, paymentType, bankName, accountID);
		
		
	}

	private PaymentType getPaymentType(Scanner keyboard) {
		System.out.print("Please enter a payment type");
		System.out.print("1. Check\n2. Online");
		int choice = keyboard.nextInt();
		PaymentType paymentType = null;
		while(choice<1||choice>2) {
			System.out.print("Invalid entry. Please enter a number between 1 and 2");
			choice = keyboard.nextInt();
		}
		switch(choice) {
			case 1:
				paymentType = PaymentType.CHECK;
				break;
			case 2:
				paymentType = PaymentType.ONLINE;
				break;
		}
		return paymentType;
	}

	private void addPurchase(Scanner keyboard, CreditCard creditCard) {
		PurchaseType type = getPurchaseType(keyboard);
		System.out.print("Please enter purchase amount: ");
		double amount = keyboard.nextDouble();
		while(amount <= 0) {
			System.out.print("Invalid purchase amount! Please re-enter amount: ");
			amount = keyboard.nextDouble();
		}
		keyboard.nextLine();
		System.out.print("Please enter vendor name: ");
		String vendorName = keyboard.nextLine();
		
		System.out.print("Please enter vendor street address");
		String street = keyboard.nextLine();
		System.out.print("Please enter vendor city: ");
		String city = keyboard.nextLine();
		System.out.print("Please enter vendor state: ");
		String state = keyboard.nextLine();
		while(state.length()!=2) {
			System.out.print("Invalid entry. Please enter a valid state: ");
			state = keyboard.nextLine();
		}
		System.out.print("Pleae enter vendor zip code(5 digits only): ");
		String zipcode = keyboard.nextLine();
		while(zipcode.length()!=5) {
			System.out.print("Invalid entry. Please only enter 5 digits: ");
			zipcode = keyboard.nextLine();
		}
		creditCard.addPurchase(amount, type, vendorName, street, city, state, zipcode);
	}
}
