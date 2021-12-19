import java.time.LocalDate;

public class Fee extends Transaction{
	private FeeType feeType;
	public Fee(FeeType feeType, double amount) {
		super(LocalDate.now(), TransactionType.FEE, amount);
		this.feeType = feeType;
	}
}
