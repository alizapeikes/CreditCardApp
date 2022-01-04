import java.time.LocalDate;

public class Fee extends Transaction{
	private FeeType feeType;
	public Fee(FeeType feeType, double amount) {
		super(LocalDate.now(), TransactionType.FEE, amount);
		this.feeType = feeType;
	}
	
	public Fee(Fee fee) {
		super(fee);
		this.feeType = fee.feeType;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString() + " Fee Type: " + feeType);
		return str.toString();
	}
}
