import java.time.LocalDate;

public class Payment extends Transaction{
	private PaymentType paymentType;
	private BankAccount account;
	
	public Payment(double amount, PaymentType paymentType, BankAccount account) {
		super(LocalDate.now(), TransactionType.PAYMENT, amount);
		this.paymentType = paymentType;
		this.account = account;
	}
	
	public PaymentType getPaymentType() {
		return paymentType;
	}
}
