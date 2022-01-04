import java.time.LocalDate;

public class Payment extends Transaction{
	private PaymentType paymentType;
	private BankAccount account;
	
	public Payment(double amount, PaymentType paymentType, String bankName, String accountID) {
		super(LocalDate.now(), TransactionType.PAYMENT, amount);
		this.paymentType = paymentType;
		this.account = new BankAccount(bankName, accountID);
	}
	
	public Payment(Payment payment) {
		super(payment);
		this.paymentType = payment.paymentType;
		this.account = payment.account;
	}
	
	public PaymentType getPaymentType() {
		return paymentType;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString() + " Payment Type: " + paymentType);
		str.append("\nAccount Information: " + account);
		return str.toString();
	}
}
